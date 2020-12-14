package com.fastcampus.java.project3.demo.controller;

import com.fastcampus.java.project3.demo.domain.Person;
import com.fastcampus.java.project3.demo.repository.PersonRepository;
import com.fastcampus.java.project3.demo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value="/api/person")
@RestController
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    /*
    @GetMapping
    public Person getPerson(Long id){
        return personService.getPerson(id);
    }*/

    //query문으로 오는 값을 @PathVarible이 받아 온다.
    @GetMapping(value="/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)//결과값이 201번이 나오게끔
    public void savePerson(@RequestBody Person person){
        personService.save(person);
        log.info("person -> {}",personRepository.findAll());
    }
}
