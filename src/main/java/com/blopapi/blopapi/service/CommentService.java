package com.blopapi.blopapi.service;

import com.blopapi.blopapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postid,CommentDto commentDto);

    List<CommentDto> findCommentByPostId(long postId);

    void deleteCommentByid(long postId, long id);

    CommentDto getCommentById(long postId, long id);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
}
