package com.codingshuttle.linkedInProject.uploder_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploderService {

    String upload(MultipartFile file);
}
