package com.blopapi.blopapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;

    @NotEmpty
    @Size(min=2,message = "Title Should at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min=4,message = "Description Should at least  4 characters")
    private String description;
    @NotEmpty

    private String content;







}
