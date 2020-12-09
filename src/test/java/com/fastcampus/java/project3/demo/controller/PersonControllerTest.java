package com.fastcampus.java.project3.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonControllerTest {

    @Autowired
    private PersonController personController;

    private MockMvc mockMvc;
    /*
    @Test
    void getPerson() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();

        mockMvc.perform(
                MockMvcRequestBuilders.get("api/person/1")
        ).andDo()

    }*/

}