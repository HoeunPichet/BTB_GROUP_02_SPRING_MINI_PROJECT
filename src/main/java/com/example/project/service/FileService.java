package com.example.project.service;

import com.example.project.model.entity.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileMetadata uploadFile(MultipartFile file);
}
