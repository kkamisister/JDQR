package com.example.backend.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageS3Service {
    String uploadImageToS3(MultipartFile file);
}
