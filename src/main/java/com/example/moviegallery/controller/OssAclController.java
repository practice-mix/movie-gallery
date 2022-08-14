package com.example.moviegallery.controller;

import com.example.moviegallery.config.MinIOConfig;
import com.example.moviegallery.controller.model.RequestTokenResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
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
    public ResponseEntity<RequestTokenResponse> requestToken() {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("response-content-type", "application/json");
        String objectName = UUID.randomUUID().toString().replace("-", "");
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

    private static final OkHttpClient okHttpClient;

    static {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        System.out.println("intercept request = " + request);
                        Response response = chain.proceed(request);
                        System.out.println("intercept response = " + response);

                        return response;
                    }
                })
                .build();

    }


    public static void main(String[] args) {
        OssAclController ossAclController = new OssAclController();
        MinIOConfig minIOConfig = new MinIOConfig();
        ossAclController.minioClient = minIOConfig.minioClient();

        ResponseEntity<RequestTokenResponse> response = ossAclController.requestToken();
        System.out.println("requestToken response = " + response);
        RequestTokenResponse body = response.getBody();
        String putUrl = body.getPutUrl();

        uploadByOkHttp(putUrl);

    }


    @SneakyThrows
    private static void uploadByOkHttp(String putUrl) {
        okhttp3.MediaType fileContentType = okhttp3.MediaType.parse("File/*");
//        okhttp3.MediaType fileContentType = okhttp3.MediaType.parse("text/*");

        // Create request body.
        File file = new File("D:\\ProgramData\\minio\\mylog.log");
        RequestBody requestBody = RequestBody.create(file, fileContentType);

        // Create request builder.
        Request.Builder builder = new Request.Builder();
        // Set url.
        builder = builder.url(putUrl);
        // set method and request body.
        builder.method("PUT", requestBody);
//        builder.addHeader("Content-Type", "File/*");
//        builder.addHeader("Content-Type","text/*");

        // Create request object.
        Request request = builder.build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("upload response = " + response);
            }
        });
    }

}
