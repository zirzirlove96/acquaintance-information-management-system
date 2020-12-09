package com.fastcampus.java.project3.demo.service;


import com.fastcampus.java.project3.demo.domain.Birthday;
import com.fastcampus.java.project3.demo.domain.Block;
import com.fastcampus.java.project3.demo.domain.Person;

import com.fastcampus.java.project3.demo.repository.BlockRepository;
import com.fastcampus.java.project3.demo.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
@Transactional
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Test
    void getPeopleName() {
        List<Person> result = personService.getPeopleByName("martin");

        result.forEach(System.out::println);
    }


    @Test
    void getPeopleExcludeBlocks() {

        //givenBlocks();

        //차단할 수 있는 메서드를 불러온다.
        List<Person> result = personService.getPersonExcludeBlocks();

        //forEach를 사용하여 각 객체를 한 줄 씩 나타낼 수 있다.
        result.forEach(System.out::println);
        assertThat(result.size()).isEqualTo(3);
        //block되지 않은 사람의 수
  //      assertThat(result.get(0).getName()).isEqualTo("benny");
   //     assertThat(result.get(1).getName()).isEqualTo("lisa");
    //    assertThat(result.get(2).getName()).isEqualTo("lilly");
    }


    @Test
    void getPerson(){

        List<Person> person = personService.getPeopleByName("martin");
        System.out.println(person);

    }

}