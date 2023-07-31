package com.blopapi.blopapi.service.impl;
//200 to get all
//201 for creating
//404 if method for url is not found
//500 is internal server error
//401 un authorised acces

import com.blopapi.blopapi.entity.Comment;
import com.blopapi.blopapi.entity.Post;
import com.blopapi.blopapi.exception.ResourceNotFoundException;
import com.blopapi.blopapi.payload.CommentDto;
import com.blopapi.blopapi.repository.CommentRepository;
import com.blopapi.blopapi.repository.PostRepository;
import com.blopapi.blopapi.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepo;
    private CommentRepository commentRepo;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    @Override
    public CommentDto createComment(long postid, CommentDto commentDto) {
     Post post= postRepo.findById(postid).orElseThrow(
              ()->new ResourceNotFoundException(postid)
      );
      Comment comment= new Comment();
      comment.setName(commentDto.getName());
      comment.setEmail(commentDto.getEmail());
      comment.setBody(commentDto.getBody());
      comment.setPost(post);//this tell that above comment fot this post and it belongs to postid num
        Comment savedComment=commentRepo.save(comment);

        CommentDto dto= new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setEmail(savedComment.getEmail());
        dto.setBody(savedComment.getBody());

        return dto;
    }


    public List<CommentDto> findCommentByPostId(long postId){

        Post post =postRepo.findById(postId).orElseThrow(
                ()->  new ResourceNotFoundException(postId)
        );
        List<Comment> comments=commentRepo.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteCommentByid(long postId, long id) {

        Post post =postRepo.findById(postId).orElseThrow(
                ()->  new ResourceNotFoundException(postId)
        );

        Comment comment =commentRepo.findById(postId).orElseThrow(
                ()->  new ResourceNotFoundException(id)
        );
        commentRepo.deleteById(id);


    }

    @Override
    public CommentDto getCommentById(long postId, long id) {

        Post post =postRepo.findById(postId).orElseThrow(
                ()->  new ResourceNotFoundException(postId)
        );

        Comment comment =commentRepo.findById(postId).orElseThrow(
                ()->  new ResourceNotFoundException(id)
        );
        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

        Post post =postRepo.findById(postId).orElseThrow(
                ()->  new ResourceNotFoundException(postId)
        );

        Comment comment =commentRepo.findById(postId).orElseThrow(
                ()->  new ResourceNotFoundException(commentId)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepo.save(comment);
        return mapToDto(updatedComment);


    }

    CommentDto mapToDto(Comment Comment){
        CommentDto dto= new CommentDto();
        dto.setId(Comment.getId());
        dto.setName(Comment.getName());
        dto.setEmail(Comment.getEmail());
        dto.setBody(Comment.getBody());
        return dto;

    }
}


