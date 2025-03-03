package com.blog.server;

import com.alibaba.fastjson2.JSONObject;
import com.blog.server.config.MinioConfig;

import com.blog.server.entity.vo.FileInfoVO;
import com.blog.server.utils.JsonUtil;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.messages.Item;
import jakarta.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class BlogCoreApplicationTests {

    @Resource
    private MinioConfig prop;

    @Resource
    private MinioClient minioClient;

    public static String readableFileSize(long size) {
    if(size <= 0) return "0";
    final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    @Test
    void contextLoads() throws Exception{
        ListObjectsArgs args = ListObjectsArgs.builder().bucket(prop.getMinioBucketName()).build();
        Iterable<Result<Item>> results = minioClient.listObjects(args);

        List<FileInfoVO> fileInfoVO = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Result<Item> result : results){
            Item item = result.get();

            FileInfoVO fileInfo = new FileInfoVO();
            fileInfo.setFileName(item.objectName());
            fileInfo.setLastModifiedTime(item.lastModified().format(formatter));
            fileInfo.setFileSize(readableFileSize(item.size()));

            StatObjectArgs statObjectArgs = StatObjectArgs.builder().bucket(prop.getMinioBucketName()).object(item.objectName()).build();
            StatObjectResponse statObjectResponse = minioClient.statObject(statObjectArgs);

            fileInfo.setContentType(statObjectResponse.contentType());

            fileInfoVO.add(fileInfo);
        }

        fileInfoVO.forEach(System.out::println);

        System.out.println(JSONObject.toJSONString(fileInfoVO));
    }


}
