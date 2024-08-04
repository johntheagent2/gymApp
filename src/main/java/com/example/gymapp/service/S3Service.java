package com.example.gymapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadFile(MultipartFile file);

    void removeFile(String fileName);
}
