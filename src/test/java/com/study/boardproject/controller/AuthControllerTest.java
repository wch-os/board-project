package com.study.boardproject.controller;

import com.study.boardproject.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DisplayName("View 컨트롤러 - 인증")
@Import(SecurityConfig.class)
@WebMvcTest
public class AuthControllerTest {

    @Autowired private MockMvc mvc;


    @DisplayName("[view][GET] 로그인 페이지 - 정상 호출")
    @Test
    public void view_articles() throws Exception {
        //given: Nothing

        //when: Trying Login
        //then: Return Login View
        mvc.perform(MockMvcRequestBuilders.get("/login")) // Spring Security가 자동으로 생성함
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}
