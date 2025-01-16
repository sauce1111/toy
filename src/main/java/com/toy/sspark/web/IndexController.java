package com.toy.sspark.web;

import com.toy.sspark.service.post.PostService;
import com.toy.sspark.web.vo.PostListResponseVO;
import com.toy.sspark.web.vo.PostResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;

    @GetMapping("/")
    public String index(Model model) {
        List<PostListResponseVO> postListResponseVOS = postService.findAllDesc();
        model.addAttribute("posts", postListResponseVOS);

        return "index";
    }

    @GetMapping("/post/save")
    public String postSave() {
        return "post-save";
    }

    @GetMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostResponseVO responseVO = postService.get(id);
        model.addAttribute("post", responseVO);

        return "post-update";
    }

    @DeleteMapping("/api/post/{id}")
    public ResponseEntity<Long> postDelete(@PathVariable Long id) {
        postService.delete(id);

        return ResponseEntity.ok(id);
    }
}
