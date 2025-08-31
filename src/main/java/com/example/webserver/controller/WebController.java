package com.example.webserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@RestController
@RequestMapping("/api")
public class WebController {

    // Simple Hello
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Java WebServer 🚀";
    }

    // Upload file
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Path path = Paths.get("uploads/" + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return ResponseEntity.ok("File uploaded: " + path);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    // Download file
    @GetMapping("/download/{filename}")
    public ResponseEntity<String> downloadFile(@PathVariable String filename) {
        Path path = Paths.get("uploads/" + filename);
        if (Files.exists(path)) {
            return ResponseEntity.ok("Download available at: " + path.toAbsolutePath());
        }
        return ResponseEntity.notFound().build();
    }

    // Health check
    @GetMapping("/health")
    public String health() {
        return "UP";
    }
}
