package com.study.springstudycrud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostResponseDto {
    private final String username;
    private final String title;
    private final String content;
}
