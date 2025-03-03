package com.blog.server.controller;

import com.blog.server.aspect.ApiOperationLog;
import com.blog.server.entity.ImageInfo;
import com.blog.server.entity.Result;
import com.blog.server.entity.vo.FileInfoVO;
import com.blog.server.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/protected")
@Tag(name = "图片接口")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Resource
    private FileService fileService;

    @ApiOperationLog(description = "图片上传")
    @Operation(summary = "图片上传")
    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam MultipartFile file){
        String previewUrl = fileService.uploadAndPreview(file);
        log.info("图片预览链接: " + previewUrl);
        if (previewUrl == null) return Result.failure (400, "图片上传失败。");
        return Result.success(previewUrl);
    }

    @ApiOperationLog(description = "图片删除")
    @Operation(summary = "图片删除")
    @DeleteMapping("/delImg")
    public Result<Void> deleteFiles(@RequestBody List<String> fileNames){

        boolean areAllFilesDeleted = fileService.removeImagesByUrl(fileNames);
        if (areAllFilesDeleted){
            return Result.success();
        } else {
            return Result.failure(500, "部分或全部文件删除失败。");
        }
    }

    @ApiOperationLog(description = "获取所有图片")
    @Operation(summary = "获取所有图片")
    @GetMapping("/images")
    public Result<List<ImageInfo>> getImages(){
        List<ImageInfo> imageList = fileService.getAllImages();
        log.info("图片列表: " + imageList);
        return Result.success(imageList);
    }

    @ApiOperationLog(description = "获取所有文件信息")
    @Operation(summary = "获取所有文件信息")
    @GetMapping("/list-file-info")
    public String listFileInfo(){
        return fileService.listFileInfo();
    }

    @ApiOperationLog(description = "删除文件")
    @Operation(summary = "删除文件")
    @DeleteMapping("/del-file")
    public String deleteFile(@RequestBody List<FileInfoVO> fileInfoVOList){
        return fileService.removeFilesByName(fileInfoVOList);
    }

}
