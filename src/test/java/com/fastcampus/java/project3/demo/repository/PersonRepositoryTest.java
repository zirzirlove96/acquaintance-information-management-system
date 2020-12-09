package com.fastcampus.java.project3.demo.repository;


import com.fastcampus.java.project3.demo.domain.Birthday;
import com.fastcampus.java.project3.demo.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;


    @Test
    void crud() {
        Person person = new Person();
        person.setName("Kim");
        person.setAge(25);
        person.setBloodType("A");

        personRepository.save(person);

        //동명이인이 있을 수도 있기 때문에 List
        List<Person> people = personRepository.findByName("Kim");

        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("Kim");
        assertThat(people.get(0).getAge()).isEqualTo(25);
        assertThat(people.get(0).getBloodType()).isEqualTo("A");

        personRepository.deleteAll();
        //System.out.print(people.toString());


    }

    @Test
    void findByBloodType() {
        /*
        givenPerson("martin", 10, "A");
        givenPerson("david", 9, "B");
        givenPerson("dennis", 8, "O");
        givenPerson("sophia", 7, "AB");
        givenPerson("benny", 6, "A");
        givenPerson("john", 5, "A");*/

        List<Person> result = personRepository.findByBloodType("A");

        result.forEach(System.out::println);
    }

    @Test
    void findByBirthdayBetween() {
        /*
        givenPerson("martin", 10, "A", LocalDate.of(1991, 8, 15));
        givenPerson("david", 9, "B", LocalDate.of(1992, 7, 10));
        givenPerson("dennis", 8, "O", LocalDate.of(1993, 1, 5));
        givenPerson("sophia", 7, "AB", LocalDate.of(1994, 6, 30));
        givenPerson("benny", 6, "A", LocalDate.of(1995, 8, 30));
         */

        List<Person> result = personRepository.findByMonthBirthday(1);

        result.forEach(System.out::println);
    }

    /*
    data.sql를 사용하므로 쓸 필요가 없음
    private void givenPerson(String name, int age, String bloodType) {
        givenPerson(name, age, bloodType, null);
    }

    private void givenPerson(String name, int age, String bloodType, LocalDate birthday) {
        Person person = new Person(name, age, bloodType);
        person.setBirthday(new Birthday(birthday));

        personRepository.save(person);
    }*/
}