package com.agritrading.AgritradingApplication.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    public static String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new IOException("File is empty");
        }

        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDirectory, fileName);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, imageFile.getBytes());

        return "/images/products/" + fileName; // Return the accessible URL or path
    }
}
