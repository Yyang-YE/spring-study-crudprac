package com.study.springstudycrud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUpdateRequestDto {
    private final String title;
    private final String content;
}
