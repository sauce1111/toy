package com.toy.sspark.web.vo;

import com.toy.sspark.domain.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseVO {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime updateDate;

    public PostListResponseVO(Post paramPost) {
        this.id = paramPost.getId();
        this.title = paramPost.getTitle();
        this.author = paramPost.getAuthor();
        this.updateDate = paramPost.getUpdatedDate();
    }
}
