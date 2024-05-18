package com.k4noise.pinspire.adapter.web;

import com.k4noise.pinspire.adapter.web.dto.response.FileUploadResponseDto;
import com.k4noise.pinspire.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/files")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FileStorageController {
    static String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    static String FILE_BASE_PATH = "/api/v1/files/images/";

    FileStorageService fileStorageService;

    @PostMapping("/upload")
    public FileUploadResponseDto uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(FILE_BASE_PATH)
                .path(fileName)
                .toUriString();

        return new FileUploadResponseDto(fileDownloadUri);
    }

    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = Optional.ofNullable(request.getServletContext().getMimeType(resource.getFile().getAbsolutePath()))
                .orElse(DEFAULT_CONTENT_TYPE);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}