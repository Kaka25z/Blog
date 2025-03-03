package com.blog.server.service.impl;

import com.blog.server.entity.ImageInfo;
import com.blog.server.entity.vo.FileInfoVO;
import com.blog.server.exception.CommonException;
import com.blog.server.mapper.ImageMapper;
import com.blog.server.service.FileService;
import com.blog.server.utils.MinioUtils;
import io.minio.*;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private MinioUtils minioUtils;

    @Resource
    private MinioClient minioClient;

    @Autowired
    private ImageMapper imageMapper;

    @Value("${minio.endpoint}")
    private String minioServerUrl;

    @Value("${minio.bucketName}")
    private String minioBucketName;

    @Override
    public String uploadAndPreview(MultipartFile file) {

        String uploadFileName = minioUtils.upload(file);
        if (uploadFileName == null){
            throw new CommonException(500, "文件上传失败");
        }

        String previewUrl = minioServerUrl + "/" + minioBucketName +"/" +uploadFileName;
        if (previewUrl == null){
            throw new CommonException(500, "获取文件预览URL失败");
        }

        imageMapper.previewUrl(previewUrl);

        return previewUrl;
    }

    @Override
    public boolean removeImagesByUrl(List<String> imageUrlList) {

        boolean allDeleted = true;

        for (String imageUrl : imageUrlList) {
            try {

                URL url = new URL(imageUrl);
                String path = url.getPath();
                String[] pathSegments = path.split("/");
                String fileName = pathSegments[pathSegments.length - 1];

                log.info("文件名：{}", fileName);

                boolean isFileDelete =  minioUtils.removeFile(fileName);
                if (isFileDelete){
                    log.info("文件删除成功: {}", fileName);
                } else {
                    log.error("文件删除失败: {}", fileName);
                   allDeleted = false;
                }
            } catch (Exception e){
                log.error("删除文件时发生异常: " + e);
                allDeleted = false;
            }
        }

        imageMapper.delete(imageUrlList);

        return allDeleted;
    }

    @Override
    public List<ImageInfo> getAllImages() {

        return imageMapper.getImages();
    }

    @Override
    public String handleBase64Img(String base64Img) {

        String uploadFileName = minioUtils.handleBase64Image(base64Img);
        if (uploadFileName == null){
            throw new CommonException(500, "文件上传失败");
        }

        String previewUrl = minioServerUrl + "/" + minioBucketName +"/" +uploadFileName;
        if (previewUrl == null){
            throw new CommonException(500, "获取文件预览URL失败");
        }

        imageMapper.previewUrl(previewUrl);

        return previewUrl;
    }

    @Override
    public String listFileInfo() {

        ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder().bucket(minioBucketName).build();
        Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgs);

        List<FileInfoVO> fileInfoList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            for (Result<Item> result : results){
                Item item = result.get();

                FileInfoVO fileInfo = new FileInfoVO();
                fileInfo.setFileName(item.objectName());
                fileInfo.setLastModifiedTime(item.lastModified().format(formatter));
                fileInfo.setFileSize(readableFileSize(item.size()));

                StatObjectArgs statObjectArgs = StatObjectArgs.builder().bucket(minioBucketName).object(item.objectName()).build();
                StatObjectResponse statObjectResponse = minioClient.statObject(statObjectArgs);

                fileInfo.setContentType(statObjectResponse.contentType());

                fileInfoList.add(fileInfo);
            }
        } catch (Exception e) {
            e.getMessage();
            return com.blog.server.entity.Result.failure(400, "获取文件信息失败").asJsonString();
        }

        return com.blog.server.entity.Result.success(fileInfoList).asJsonString();
    }

    @Override
    public String removeFilesByName(List<FileInfoVO> fileInfoVOList) {

            boolean allDeleted = true;

            for (FileInfoVO fileInfo : fileInfoVOList) {
                try {
                    boolean isFileDelete =  minioUtils.removeFile(fileInfo.getFileName());
                    if (isFileDelete){
                        log.info("文件删除成功: {}", fileInfo.getFileName());
                    } else {
                        log.error("文件删除失败: {}", fileInfo.getFileName());
                        allDeleted = false;
                    }

                    if (fileInfo.getContentType().equals("image/jpeg") || fileInfo.getContentType().equals("image/png")){
                        String fileUrl = minioServerUrl + "/" + minioBucketName +"/" + fileInfo.getFileName();
                        imageMapper.delete(Collections.singletonList(fileUrl));
                    }
                } catch (Exception e){
                    log.error("删除文件时发生异常: " + e);
                    allDeleted = false;
                }
            }

            if (allDeleted){
                return com.blog.server.entity.Result.success().asJsonString();
            } else {
                return com.blog.server.entity.Result.failure(400, "文件删除失败").asJsonString();
            }
    }

    private static String readableFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private boolean isImageFile(String fileName) {
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        for (String extension : imageExtensions){
            if (fileName.toLowerCase().endsWith(extension)){
                return true;
            }
        }
        return false;
    }
}
