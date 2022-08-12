package com.example.moviegallery.controller;

import com.example.moviegallery.config.MinIOConfig;
import com.example.moviegallery.controller.model.RequestTokenResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class OssAclController {

    public static final String BUCKET_NAME_MOVIE_GALLERY = "movie-gallery";
    @Autowired
    private MinioClient minioClient;

    @GetMapping("/OssAcl/requestToken")
    @SneakyThrows
    public ResponseEntity<RequestTokenResponse> requestToken()  {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("response-content-type", "application/json");
        String objectName= UUID.randomUUID().toString().replace("-","");
        String getUrl =
                minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(BUCKET_NAME_MOVIE_GALLERY)
                                .object(objectName)
                                .extraQueryParams(reqParams)
                                .build());
        System.out.println(getUrl);
        String putUrl =
                minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.PUT)
                                .bucket(BUCKET_NAME_MOVIE_GALLERY)
                                .object(objectName)
                                .expiry(5, TimeUnit.MINUTES)
                                .build());
        System.out.println(putUrl);

        RequestTokenResponse requestTokenResponse = new RequestTokenResponse();
        requestTokenResponse.setGetUrl(getUrl);
        requestTokenResponse.setPutUrl(putUrl);

        return ResponseEntity.ok(requestTokenResponse);
    }

    public static void main(String[] args) {
        OssAclController ossAclController = new OssAclController();
        MinIOConfig minIOConfig = new MinIOConfig();
        ossAclController.minioClient = minIOConfig.minIOConfig();
        ResponseEntity<RequestTokenResponse> response = ossAclController.requestToken();
        System.out.println("response = " + response);
    }
}
