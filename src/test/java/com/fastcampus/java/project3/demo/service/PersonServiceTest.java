package com.fastcampus.java.project3.demo.service;


import com.fastcampus.java.project3.demo.domain.Person;

import com.fastcampus.java.project3.demo.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

//ExtendWith는 //Runwith를 대체하고 springbootTest대신 사용한다.
@ExtendWith(MockitoExtension.class)
@Transactional
class PersonServiceTest {

    //테스트의 주체가 되는 클래스
    //Autowired의 역할
    @InjectMocks
    private PersonService personService;

    //실제 빈이 아닌 가짜 빈을 만들어 대체한다.
    //PersonService에 주입시켜 준다.
    @Mock
    private PersonRepository personRepository;


    //Mock테스트는 가짜 객체를 사용하여 테스트를 한다. Mock을 사용하면 빠르게, 디테일 하게 사용할 수 있기 때문에 사용한다.
    @Test
    void getPeopleByName() {

        //빈 껍데기인 Mock을 사용하여 검사를 해주기 때문에 DB에서 가져오는 것이 아닌
        //직접 입력해주어 테스트를 진행해야 한다.
        when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));
        //when...thenReturn의 역할은 Mock의 어떤 메소드와 파라미터가 매핑되는 경우, 결과값에 대해서 지정해줄 수 있음

        List<Person> result = personService.getPeopleByName("martin");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("martin");
    }



}