package com.codingshuttle.linkedInProject.postsService.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class PersonDto {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private String name;
}
