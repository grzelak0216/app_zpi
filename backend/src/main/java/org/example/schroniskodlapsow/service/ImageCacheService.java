package org.example.schroniskodlapsow.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ImageCacheService {

    private final ConcurrentHashMap<String, byte[]> imageCache = new ConcurrentHashMap<>();

    public byte[] getImage(String imagePath) throws IOException {
        return imageCache.computeIfAbsent(imagePath, path -> {
            try {
                return Files.readAllBytes(Path.of(path));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load image: " + path, e);
            }
        });
    }

    public void clearCache() {
        imageCache.clear();
    }
}
