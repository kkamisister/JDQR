package com.example.backend.test;

import com.example.backend.common.service.ImageS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final ImageS3Service imageS3Service;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return imageS3Service.uploadImageToS3(file);
    }
}
