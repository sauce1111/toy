package com.toy.sspark.web;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestVO {

    private String title;
    private String content;

    @Builder
    public PostUpdateRequestVO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
