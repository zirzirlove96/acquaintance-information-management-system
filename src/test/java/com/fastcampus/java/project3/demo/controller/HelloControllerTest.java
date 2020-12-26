package com.fastcampus.java.project3.demo.controller;

import com.fastcampus.java.project3.demo.exception.handler.GlobalHandlerException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
class HelloControllerTest {

    @Autowired
    private HelloController helloController;

    @Autowired
    private GlobalHandlerException globalHandlerException;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                //.standaloneSetup(helloController)
                .alwaysDo(print())
                //.setControllerAdvice(globalHandlerException)
                .build();
    }



    //mockMvc는 Controller에서 수행한 Mapping주소에 따라 맞는지 확인하는 test이다.
    @Test
    void hello() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloWorld"))
                //.andDo(print())//테스트 값을 출력
                .andExpect(MockMvcResultMatchers.status().isOk());//값이 같은지 ok
    }

    @Test
    void helloException() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/helloException"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("This is not found Error"));
    }
}