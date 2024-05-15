package com.study.springstudycrud.dto;

import com.study.springstudycrud.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostRequestDto {
    private String username;
    private String title;
    private String content;
}
