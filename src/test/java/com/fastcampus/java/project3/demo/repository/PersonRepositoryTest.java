package com.fastcampus.java.project3.demo.repository;


import com.fastcampus.java.project3.demo.domain.Birthday;
import com.fastcampus.java.project3.demo.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    //이름을 찾는 것에 대해 Test
    @Test
    void findByName() {
        List<Person> people = personRepository.findByName("torny");
        assertThat(people.size()).isEqualTo(1);

        Person person = people.get(0);

        assertAll(
                () -> assertThat(person.getName()).isEqualTo("torny"),
                () -> assertThat(person.getHobby()).isEqualTo("reading"),
                () -> assertThat(person.getAddress()).isEqualTo("Seoul"),
                () -> assertThat(person.getBirthday()).isEqualTo(Birthday.of(LocalDate.of(1991, 7, 10))),
                () -> assertThat(person.getJob()).isEqualTo("officer"),
                () -> assertThat(person.getPhoneNumber()).isEqualTo("010-2222-3333"),
                () -> assertThat(person.isDeleted()).isEqualTo(false)
        );
    }


    @Test
    void findByNameIfDeleted() {
        List<Person> people = personRepository.findByName("andrew");
        //삭제 되었으니까 0
        assertThat(people.size()).isEqualTo(0);

    }

    //생일 month가 맞는 사람들만 뽑아냈는지
    @Test
    void findByMonthOfBirthday() {
        List<Person> people = personRepository.findByMonthOfBirthday(7);

        assertThat(people.size()).isEqualTo(3);

        assertAll(
                ()->assertThat(people.get(0).getName()).isEqualTo("david"),
                ()->assertThat(people.get(1).getName()).isEqualTo("lisa"),
                ()->assertThat(people.get(2).getName()).isEqualTo("torny")
        );
    }

    @Test
    void findByPeopleDeleted() {
        List<Person> people = personRepository.findPeopleDeleted();

        assertThat(people.size()).isEqualTo(1);

        assertAll(
                ()->assertThat(people.get(0).getName()).isEqualTo("andrew")
        );
    }
}