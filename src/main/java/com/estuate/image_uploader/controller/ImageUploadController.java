package com.estuate.image_uploader.controller;

import com.estuate.image_uploader.dto.FileUploadRequest;
import com.estuate.image_uploader.dto.ImagePathRequest;
import com.estuate.image_uploader.service.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageUploadController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@Valid FileUploadRequest request) {
        String url = s3Service.uploadFile(request.getFile());
        return ResponseEntity.ok(url);
    }

    // New endpoint to upload by file path
    @PostMapping("/upload-by-path")
    public ResponseEntity<String> uploadImageByPath(@RequestBody ImagePathRequest request) {
        String url = s3Service.uploadFileFromPath(request.getImagePath());
        return ResponseEntity.ok(url);
    }



}
