package com.study.springstudycrud.service;

import com.study.springstudycrud.dto.PostRequestDto;
import com.study.springstudycrud.dto.PostResponseDto;
import com.study.springstudycrud.entity.Post;
import com.study.springstudycrud.entity.User;
import com.study.springstudycrud.repository.PostRepository;
import com.study.springstudycrud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    //Post 추가
    public PostResponseDto createPost(PostRequestDto requestDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(requestDto.getUsername()));

        if(user.isPresent()) {
            Post post = new Post(user.get(), requestDto.getTitle(), requestDto.getContent());
            postRepository.save(post);
            return new PostResponseDto(post.getUser().getUsername(), post.getTitle(), post.getContent());
        }
        throw new IllegalArgumentException("User with Username " + requestDto.getUsername() + " not found");
    }

    //Post 전체 조회
    public List<PostResponseDto> getAllPost() {
        return postRepository.findAll().stream()
            .map(post -> {
                String username = userRepository.findById(post.getUser().getId())
                    .map(User::getUsername)
                    .orElse("Unknown"); // or handle as needed if user not found
                return new PostResponseDto(username, post.getTitle(), post.getContent());
            })
            .collect(Collectors.toList());
    }

    //username 으로 조회
    public List<PostResponseDto> getPostByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new IllegalArgumentException("User with username " + username + " not found");
        }

        return postRepository.findAllByUser(user).stream()
            .map(post -> {
                String uname = userRepository.findById(post.getUser().getId())
                    .map(User::getUsername)
                    .orElse("Unknown"); // or handle as needed if user not found
                return new PostResponseDto(uname, post.getTitle(), post.getContent());
            })
            .collect(Collectors.toList());
    }

    //Post 수정
    public PostResponseDto updatePost(Long postId, String title, String content, String username) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getUser().getUsername().equals(username)) {
                post.update(title, content);
                postRepository.save(post);
                return new PostResponseDto(post.getUser().getUsername(), post.getTitle(), post.getContent());
            } else {
                throw new IllegalArgumentException("User " + username + " is not authorized to update this post");
            }
        } else {
            throw new IllegalArgumentException("Post with Id " + postId + " not found");
        }
    }

    //Post 삭제
    public String deletePost(Long id, String username) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if(Objects.equals(post.getUser().getUsername(), username)) {
                postRepository.deleteById(id);
                return "삭제 성공!";
            }
            return "삭제 실패: 자신의 메모만 삭제 가능합니다";
        }
        return "삭제 실패";
    }


}
