package com.blog.server.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.blog.server.config.MinioConfig;
import com.blog.server.exception.CommonException;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import lombok.Data;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Data
@Component
public class MinioUtils {

    @Autowired
    private MinioConfig prop;

    @Resource
    private MinioClient minioClient;

    public Boolean isBucketExists(String bucketName){
        Boolean found;

        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return found;
    }

    public String upload(MultipartFile file){
        String originalFileName = file.getOriginalFilename();

        if (StringUtils.isBlank(originalFileName)){
            throw new CommonException(400, "文件为空");
        }

        String objectName = generateNewFileName(originalFileName);
        try {

            InputStream inputStream = file.getInputStream();

            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(prop.getMinioBucketName())
                    .object(objectName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();

            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return objectName;
    }

    public String handleBase64Image(String base64Image){

        String objectName = generateNewFileName("avatar.jpg");

        try {
            String[] parts = base64Image.split(",");
            String imageDataBytes = parts[1];

            byte[] bytes = Base64.getDecoder().decode(imageDataBytes);

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(prop.getMinioBucketName())
                    .object(objectName)
                    .stream(bais, bais.available(), -1)
                    .contentType(MediaType.IMAGE_JPEG_VALUE)
                    .build();
            minioClient.putObject(objectArgs);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return objectName;
    }

    public List<Item> listObjects(){
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(prop.getMinioBucketName())
                        .build()
        );
        List<Item> items = new ArrayList<>();

        try {
            for (Result<Item> result : results){
                items.add(result.get());
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return items;
    }

    public String preview(String fileName){

        GetPresignedObjectUrlArgs build = new GetPresignedObjectUrlArgs().builder()
                .bucket(prop.getMinioBucketName())
                .object(fileName)
                .method(Method.GET)
                .build();

        try {
            String url = minioClient.getPresignedObjectUrl(build);
            return url;
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    public boolean removeFile(String fileName){
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(prop.getMinioBucketName())
                            .object(fileName)
                            .build());
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private String generateNewFileName(String originalFileName) {
        String suffix = StrUtil.subAfter(originalFileName, ".", true);

        return UUID.randomUUID().toString(true) + "." +suffix;
    }
}
