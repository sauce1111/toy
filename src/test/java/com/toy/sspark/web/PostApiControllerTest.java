package com.toy.sspark.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.sspark.domain.post.Post;
import com.toy.sspark.repository.PostRepository;
import com.toy.sspark.web.vo.PostSaveRequestVO;
import com.toy.sspark.web.vo.PostUpdateRequestVO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void cleanup() throws Exception {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시물을 저장 한다.")
    @WithMockUser(roles = "USER")
    public void testSave() throws Exception {
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
        String jsonContent = new ObjectMapper().writeValueAsString(requestVO);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        // then
        List<Post> posts = postRepository.findAll();
        assertThat(posts.get(0).getTitle()).isEqualTo(title);
        assertThat(posts.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("게시물을 수정 한다.")
    @WithMockUser
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

        // when
        String jsonContent = new ObjectMapper().writeValueAsString(requestVO);
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        //then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}




