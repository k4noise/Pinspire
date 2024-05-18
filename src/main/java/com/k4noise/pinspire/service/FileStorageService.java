package com.k4noise.pinspire.service;

import com.k4noise.pinspire.adapter.web.errors.FileStorageException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Log4j2
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileStorageService {
    Path fileStorageLocation;

    public FileStorageService(@Value("${app.images.dir}") String fileStorageDir) throws FileStorageException {
        this.fileStorageLocation = Paths.get(fileStorageDir)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception exception) {
            throw new FileStorageException("Could not create file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static boolean isUrlValid(String urlString) {
        try {
            UriComponentsBuilder.fromHttpUrl(urlString).build().toUri();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean urlExists(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException e) {
            return false;
        }
    }

    public String storeFile(MultipartFile file) throws FileStorageException {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";

        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex <= originalFileName.length() - 2) {
            fileExtension = originalFileName.substring(dotIndex);
        }

        if (originalFileName.contains("..")) {
            throw new FileStorageException("Filename contains invalid path sequence: " + originalFileName, HttpStatus.BAD_REQUEST);
        }

        String fileName = UUID.randomUUID() + fileExtension;

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Saved file with name {}", fileName);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file: " + fileName, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }

    public Resource loadFileAsResource(String fileName) throws FileStorageException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException("File not found: " + fileName, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new FileStorageException("File not found: " + fileName, HttpStatus.NOT_FOUND);
        }
    }
}