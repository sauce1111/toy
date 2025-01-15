package com.toy.sspark.service.post;

import com.toy.sspark.domain.post.Post;
import com.toy.sspark.domain.post.PostRepository;
import com.toy.sspark.web.PostResponseVO;
import com.toy.sspark.web.PostSaveRequestVO;
import com.toy.sspark.web.PostUpdateRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public PostResponseVO get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There are no such posts. id = " + id));

        return new PostResponseVO(post);
    }
}
