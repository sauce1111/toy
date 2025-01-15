package com.toy.sspark.domain.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @AfterEach
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    public void testSave() {
        // given
        String title = "제목";
        String content = "내용";

        Post post = Post.builder()
                .title(title)
                .content(content)
                .build();

        postRepository.save(post);

        // when
        List<Post> posts = postRepository.findAll();

        // then
        Post savedPost = posts.get(0);
        assertThat(savedPost.getTitle()).isEqualTo(title);
        assertThat(savedPost.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("상속받은 BaseEntity 의 생성/수정 시간이 입력된다.")
    public void testAddLocalDateTimeColumn() {
        // given
        LocalDateTime now = LocalDateTime.of(2025, Month.JANUARY, 1, 10, 30, 30);
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author("작성자")
                .build();
        postRepository.save(post);

        // when, then
        Post savedPost = postRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("not found entity..."));

        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getUpdatedDate()).isAfter(now);
    }
}