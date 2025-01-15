package com.toy.sspark.web;

import com.toy.sspark.domain.post.Post;
import lombok.Getter;

@Getter
public class PostResponseVO {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostResponseVO(Post paramPost) {
        this.id = paramPost.getId();
        this.title = paramPost.getTitle();
        this.content = paramPost.getContent();
        this.author = paramPost.getAuthor();
    }
}
