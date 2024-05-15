package com.study.springstudycrud.repository;

import com.study.springstudycrud.entity.Post;
import com.study.springstudycrud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);
}
