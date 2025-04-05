package com.example.project.controller;

import com.example.project.model.dto.response.ApiResponse;
import com.example.project.model.entity.FileMetadata;
import com.example.project.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@RestController
@Tag(name = "file-controller")
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileMetadata>> uploadFile(@RequestParam MultipartFile file) {
        FileMetadata fileMetadata = fileService.uploadFile(file);

        ApiResponse<FileMetadata> apiResponse = ApiResponse.<FileMetadata>builder()
                .success(true)
                .message("File uploaded successfully! Metadata of the upload file is returned.")
                .payload(fileMetadata)
                .status(HttpStatus.CREATED)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/preview-file/{file-name}")
    public ResponseEntity<?> getFileByFileName(@PathVariable("file-name") String fileName) throws IOException {
        InputStream inputStream = fileService.getFileByFileName(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(inputStream.readAllBytes());
    }

}
