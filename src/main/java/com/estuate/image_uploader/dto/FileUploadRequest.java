package com.estuate.image_uploader.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadRequest {

    @NotNull(message = "File must not be null")
    private MultipartFile file;


}
