package com.example.project.service;

import com.example.project.model.entity.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {

    FileMetadata uploadFile(MultipartFile file);
    InputStream getFileByFileName(String filename);
}
