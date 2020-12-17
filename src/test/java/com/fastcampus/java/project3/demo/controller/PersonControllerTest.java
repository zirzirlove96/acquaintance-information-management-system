package com.fastcampus.java.project3.demo.controller;

import com.fastcampus.java.project3.demo.repository.PersonRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class PersonControllerTest {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    private MockMvc mockMvc;

    //@BeforeEach는 수행되기 전 한번 수행된다.
    //따라서 각 메서드에 있는 mockMvc를 지워줘도 된다.
    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    void getPerson() throws Exception {


        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void savePerson() throws Exception {

        //기본적인 RequestParam은 Param값에 넣어주는 것이고 MockMvcRequestBuilders.post("/api/person?name=martin2&age=20&bloodType=A")
        //JSON형식을 @RequestBody와 content를 사용해 준다.
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "\"name\":\"martin2\",\n" +
                                "  \"age\":20,\n" +
                                "  \"bloodType\":\"A\"\n" +
                                "}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void modifyPerson() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"name\":\"martin\",\n" +
                        "  \"age\":20,\n" +
                        "  \"bloodType\":\"AB\"\n" +
                        "}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test //원래는 1번의 이름은 martin이였는데 이름만 martin22로 변경해준다.
    void modifyPersonName() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                        .param("name","martin222"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deletePerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        //삭제된 정보를 확인
        System.out.println("person deleted : "+personRepository.findPeopleDeleted());
    }


}