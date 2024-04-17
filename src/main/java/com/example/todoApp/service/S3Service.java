package com.example.todoApp.service;

import com.example.todoApp.entities.UserAccount;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

@Service
public class S3Service {
    @Autowired
    private S3Template s3Template;
    @Autowired
    private S3Client s3Client;
    @Value("#{environment.AWS_BUCKET_NAME}")
    private String bucketName;

    public String uploadObject(MultipartFile file) throws IOException {
        if (file == null || file.getOriginalFilename() == null) return null;
        UserAccount userAccount = getUserDetails();
        var s3Resource = s3Template.upload(
                bucketName,
                String.format("%s/", userAccount.getId()) + file.getOriginalFilename(),
                file.getInputStream(),
                ObjectMetadata.builder().contentType(file.getContentType()).build()
        );

        return s3Client.utilities().getUrl(
                builder -> builder.bucket(bucketName).key(s3Resource.getLocation().getObject())
        ).toExternalForm();
    }

    private UserAccount getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserAccount) authentication.getPrincipal();
    }
}
