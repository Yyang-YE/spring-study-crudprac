package com.study.springstudycrud.controller;

import com.study.springstudycrud.dto.PostRequestDto;
import com.study.springstudycrud.dto.PostResponseDto;
import com.study.springstudycrud.dto.PostUpdateRequestDto;
import com.study.springstudycrud.entity.Post;
import com.study.springstudycrud.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //post 추가
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestParam String title, @RequestParam String content) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        PostRequestDto postRequestDto = new PostRequestDto(username, title, content);
        return postService.createPost(postRequestDto);
    }

    //post 전체 조회(남의 것도 조회 가능)
    @GetMapping("/post")
    public List<PostResponseDto> getAllPost() {
        return postService.getAllPost();
    }

    //username 으로 조회
    @GetMapping("/post/search")
    public List<PostResponseDto> getPostByUsername(@RequestParam String username) {
        return postService.getPostByUsername(username);
    }

    //post 수정(본인 것만 가능하게)
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostUpdateRequestDto updateRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return postService.updatePost(id, updateRequestDto.getTitle(), updateRequestDto.getContent(), username);
    }

    //post 삭제(본인 것만 가능하게
    @DeleteMapping("/post/{id}")
    public String deletePost(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return postService.deletePost(id, username);
    }
}