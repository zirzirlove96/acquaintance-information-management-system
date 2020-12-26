package com.fastcampus.java.project3.demo.controller;

import com.fastcampus.java.project3.demo.domain.Birthday;
import com.fastcampus.java.project3.demo.domain.Person;
import com.fastcampus.java.project3.demo.dto.PersonDto;
import com.fastcampus.java.project3.demo.exception.handler.GlobalHandlerException;
import com.fastcampus.java.project3.demo.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
class PersonControllerTest {

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    //JSON타입으로 변경하기 위해 Serializer.
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    @Autowired
    private GlobalHandlerException globalHandlerException;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    //@BeforeEach는 수행되기 전 한번 수행된다.
    //따라서 각 메서드에 있는 mockMvc를 지워줘도 된다.
    @BeforeEach
    void beforeEach() {
        //messageConverter를 주입해야 한다.
        mockMvc = MockMvcBuilders
                //.standaloneSetup(personController)
                //.setMessageConverters(messageConverter)
                //.setControllerAdvice(globalHandlerException)
                .webAppContextSetup(wac)
                .alwaysDo(print()) //andDo(print())를 항상 하기 위해
                .build();
    }

    @Test
    void getPerson() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andExpect(status().isOk())
                //jsonPath를 사용하여 json타입으로 지정된 리스트를 가져와
                //$->객체 , .->attribute 즉 json타입의 객체에서 name의 attribute에서 value값인 martin을 가져온다.
                .andExpect(jsonPath("$.name").value("martin"))
                .andExpect(jsonPath("$.hobby").isEmpty())
                .andExpect(jsonPath("$.address").isEmpty())
                .andExpect(jsonPath("$.birthday").value("1991-08-01"))
                .andExpect(jsonPath("$.job").isEmpty())
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.birthdayToday").isBoolean());

    }

    @Test
    void savePerson() throws Exception {

        PersonDto personDto = PersonDto.of("martin","programming","판교", LocalDate.now(),"Programmer","010-111-2222");

        //기본적인 RequestParam은 Param값에 넣어주는 것이고 MockMvcRequestBuilders.post("/api/person?name=martin2&age=20&bloodType=A")
        //JSON형식을 @RequestBody와 content를 사용해 준다.
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(personDto)))
                .andExpect(status().isCreated());

        //값이 저장되었는지 확인
        //맨 마지막 값을 확인
        Person result = personRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).get(0);

        assertAll(
                ()->assertThat(result.getName()).isEqualTo("martin"),
                ()->assertThat(result.getHobby()).isEqualTo("programming"),
                ()->assertThat(result.getAddress()).isEqualTo("판교"),
                ()->assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                ()->assertThat(result.getJob()).isEqualTo("Programmer"),
                ()->assertThat(result.getPhoneNumber()).isEqualTo("010-111-2222")
        );

    }

    @Test
    void savePersonIfNameIsNull() throws Exception {

        PersonDto personDto = new PersonDto(); //빈 객체를 생성해 저장하여 error exception을 일으키자.

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(personDto)))
        .andExpect(jsonPath("$.code").value(500))
        .andExpect(jsonPath("$.message").value("This is not found Error"));
    }

    @Test
    void modifyPerson() throws Exception {

        PersonDto personDto = PersonDto.of("martin","programming","판교", LocalDate.now(),"Programmer","010-111-1111");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(personDto)))
                .andExpect(status().isOk());

        Person result = personRepository.findById(1L).get();

        //변경된 내용이 맞는지 확인하는 과정이다.
        //실행 순서를 줄여준다.
        assertAll(
                ()->assertThat(result.getName()).isEqualTo("martin"),
                ()->assertThat(result.getHobby()).isEqualTo("programming"),
                ()->assertThat(result.getAddress()).isEqualTo("판교"),
                ()->assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                ()->assertThat(result.getJob()).isEqualTo("Programmer"),
                ()->assertThat(result.getPhoneNumber()).isEqualTo("010-111-1111")
        );

    }

    //위의 modifyPerson을 수행했을 경우 이름이 다른 경우 RuntimeError를 진행하게 했으므로
    //이름이 다른 경우의 modify메서드를 만들어 준다.
    @Test
    void modifyPersonDifferent() throws Exception {

        PersonDto personDto = PersonDto.of("James","programming","판교", LocalDate.now(),"Programmer","010-111-1111");


            mockMvc.perform(
                    MockMvcRequestBuilders.put("/api/person/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(personDto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(400))
                    .andExpect(jsonPath("$.message").value("이름 변경이 허용되지 않습니다"));

    }


    //id값이 존재하지 않는 경우
    @Test
    void modifyPersonIfPersonNotFound() throws Exception {
        PersonDto dto = PersonDto.of("martin", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/10")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Person Entity가 존재하지 않습니다"));
    }


    @Test //원래는 1번의 이름은 martin이였는데 이름만 martin22로 변경해준다.
    void modifyPersonName() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                        .param("name","martinModify"))
                .andExpect(status().isOk());

        //실제 DB의 내용이 변경되었는지 확인
        assertThat(personRepository.findById(1L).get().getName()).isEqualTo("martinModify");
    }

    @Test
    void deletePerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andExpect(status().isOk());
                //.andExpect(MockMvcResultMatchers.content().string("true"));

        //삭제된 정보를 확인
        //System.out.println("person deleted : "+personRepository.findPeopleDeleted());

        //assertTrue를 사용하여 테스트를 진행.
        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
    }

    //personDto를 JSON타입으로 serialized해준다.
    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }

}