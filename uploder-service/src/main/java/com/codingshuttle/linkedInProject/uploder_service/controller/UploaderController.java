package com.codingshuttle.linkedInProject.uploder_service.controller;

import com.codingshuttle.linkedInProject.uploder_service.service.UploderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class UploaderController {
    private final UploderService uploaderService;

    @PostMapping
    public ResponseEntity<String> uploaderFile(@RequestParam MultipartFile file) {
        String url = uploaderService.upload(file);
        return ResponseEntity.ok(url);
    }
}
