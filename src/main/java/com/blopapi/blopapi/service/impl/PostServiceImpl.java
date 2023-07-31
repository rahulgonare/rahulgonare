package com.blopapi.blopapi.service.impl;

import com.blopapi.blopapi.entity.Post;
import com.blopapi.blopapi.exception.ResourceNotFoundException;
import com.blopapi.blopapi.payload.CommentDto;
import com.blopapi.blopapi.payload.PostDto;
import com.blopapi.blopapi.repository.PostRepository;
import com.blopapi.blopapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);// it will take dto object
        Post savedPost = postRepo.save(post);

        PostDto dto = mapToDto(savedPost);

        return dto;
    }

    @Override

    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        PostDto dto = mapToDto(post);

        return dto;
    }


   // http://localhost:8080/api/posts?pageNo=0&pageSize=8
    @Override

    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageble = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));


        Page<Post> posts = postRepo.findAll(pageble);
       List<Post> content=  posts.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public void deletePost(long id) {
        postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id)
        );
        postRepo.deleteById(id);

    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {

      Post post =  postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id)
        );

        Post updatedContent= mapToEntity(postDto);
        updatedContent.setId(post.getId());
        Post updatedPostInfo = postRepo.save(updatedContent);

        return mapToDto(updatedPostInfo);

    }

    PostDto mapToDto(Post post) {

       // PostDto dto = new PostDto();
      PostDto dto=  modelMapper.map(post, PostDto.class);
       // dto.setId(post.getId());
       // dto.setTitle(post.getTitle());
        //dto.setDescription(post.getDescription());
       // dto.setContent(post.getContent());
        return dto;

    }

    Post mapToEntity(PostDto postDto) {// it will copy the content of Dto object and convert that to entity
       // Post post = new Post();//  this is object of class Entity page
        Post post= modelMapper.map(postDto,Post.class);
        //post.setTitle(postDto.getTitle());
        //post.setDescription(postDto.getDescription());
        //post.setContent(postDto.getContent());
        return post;

    }

}
