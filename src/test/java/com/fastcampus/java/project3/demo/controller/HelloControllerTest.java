package com.fastcampus.java.project3.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloControllerTest {

    @Autowired
    private HelloController helloController;

    private MockMvc mockMvc;

    @Test
    void show() {
//        System.out.println(helloController.show());
        assertThat(helloController.show()).isEqualTo("Hello World!!");//내용이 맞는지 확인
    }

    //mockMvc는 Controller에서 수행한 Mapping주소에 따라 맞는지 확인하는 test이다.
    @Test
    void movkMvcTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloWorld")
        ).andDo(MockMvcResultHandlers.print())//테스트 값을 출력
                .andExpect(MockMvcResultMatchers.status().isOk());//값이 같은지 ok
    }

}