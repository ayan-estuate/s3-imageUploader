package com.estuate.image_uploader.service;

import com.estuate.image_uploader.exception.FileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        log.info("Uploading file '{}' to bucket '{}'", fileName, bucketName);

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

            log.info("File '{}' uploaded successfully", fileName);
            return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;

        } catch (IOException e) {
            log.error("File upload failed for '{}'", fileName, e);
            throw new FileUploadException("File upload failed for " + file.getOriginalFilename(), e);
        }
    }

    public String uploadFileFromPath(String imagePath) {
        log.info("Uploading image from path '{}' to bucket '{}'", imagePath, bucketName);

        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(imagePath));
            String fileName = UUID.randomUUID() + "_" + Paths.get(imagePath).getFileName().toString();

            // Optional: You can detect content type from file extension or use a default value
            String contentType = Files.probeContentType(Paths.get(imagePath));
            if (contentType == null) {
                contentType = "application/octet-stream"; // fallback
            }

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(fileBytes));

            log.info("File '{}' uploaded successfully from path", fileName);
            return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;

        } catch (IOException e) {
            log.error("File upload failed from path '{}'", imagePath, e);
            throw new FileUploadException("File upload failed from path " + imagePath, e);
        }
    }
}
