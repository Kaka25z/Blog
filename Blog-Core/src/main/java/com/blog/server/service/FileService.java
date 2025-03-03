package com.blog.server.service;

import com.blog.server.entity.ImageInfo;
import com.blog.server.entity.vo.FileInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    String uploadAndPreview(MultipartFile file);

    boolean removeImagesByUrl(List<String> fileName);

    List<ImageInfo> getAllImages();

    String handleBase64Img(String base64Img);

    String listFileInfo();

    String removeFilesByName(List<FileInfoVO> fileList);
}
