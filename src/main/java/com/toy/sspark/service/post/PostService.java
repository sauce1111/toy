package com.toy.sspark.service.post;

import com.toy.sspark.domain.post.Post;
import com.toy.sspark.domain.post.PostRepository;
import com.toy.sspark.web.vo.PostListResponseVO;
import com.toy.sspark.web.vo.PostResponseVO;
import com.toy.sspark.web.vo.PostSaveRequestVO;
import com.toy.sspark.web.vo.PostUpdateRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(PostSaveRequestVO requestVO) {
        return postRepository.save(requestVO.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestVO requestVO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There are no such posts. id = " + id));

        post.update(requestVO.getTitle(), requestVO.getContent());

        return id;
    }

    @Transactional
    public void delete(Long id) {
        Post posts = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There are no such posts. id = " + id));

        postRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostResponseVO get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There are no such posts. id = " + id));

        return new PostResponseVO(post);
    }

    @Transactional(readOnly = true)
    public List<PostListResponseVO> findAllDesc() {
        return postRepository.findAllDesc().stream()
                .map(PostListResponseVO::new)
                .collect(Collectors.toList());
    }
}
