package com.toy.sspark.web;

import com.toy.sspark.domain.post.Post;
import com.toy.sspark.domain.post.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    public void cleanup() throws Exception {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물을 저장 한다.")
    public void testSave() {
        // given
        String title = "제목";
        String content = "내용";
        String author = "작성자";
        String url = "http://localhost:" + port + "/api/post";

        PostSaveRequestVO requestVO = PostSaveRequestVO.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestVO, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> posts = postRepository.findAll();
        assertThat(posts.get(0).getTitle()).isEqualTo(title);
        assertThat(posts.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("게시물을 수정 한다.")
    public void testUpdate() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author("작성자")
                .build();
        Post savedPost = postRepository.save(post);

        Long postId = savedPost.getId();
        String expectedTitle = "제목2";
        String expectedContent = "내용2";
        PostUpdateRequestVO requestVO = PostUpdateRequestVO.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/post/" + postId;
        HttpEntity<PostUpdateRequestVO> requestVOHttpEntity = new HttpEntity<>(requestVO);

        // when
        ResponseEntity<Long> responseEntity =
                restTemplate.exchange(url, HttpMethod.PUT, requestVOHttpEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> posts = postRepository.findAll();
        assertThat(posts.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(posts.get(0).getContent()).isEqualTo(expectedContent);
    }
}




