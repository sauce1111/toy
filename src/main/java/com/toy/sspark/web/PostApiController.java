package com.toy.sspark.web;

import com.toy.sspark.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/post")
    public Long save(@RequestBody PostSaveRequestVO requestVO) {
        return postService.save(requestVO);
    }

    @GetMapping("/api/post/{id}")
    public PostResponseVO getPost(@PathVariable Long id) {
        return postService.get(id);
    }

    @PutMapping("/api/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestVO requestVO) {
        return postService.update(id, requestVO);
    }
}
