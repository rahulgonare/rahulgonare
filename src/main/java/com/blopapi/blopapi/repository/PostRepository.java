package com.blopapi.blopapi.repository;

import com.blopapi.blopapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
