package com.example.biol.controllers;

import com.example.biol.models.Image;
import com.example.biol.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image != null) {
            try {
                FileSystemResource resource = new FileSystemResource(image.getFilePath()); // Используйте поле, которое содержит путь к файлу изображения
                if (resource.exists()) {
                    return ResponseEntity.ok()
                            .header("fileName", image.getOriginalFileName())
                            .contentType(MediaType.valueOf(image.getContentType()))
                            .contentLength(resource.contentLength())
                            .body(resource);
                }
            } catch (IOException e) {
                // Обработка ошибки чтения файла
            }
        }
        return ResponseEntity.notFound().build();
    }
}
