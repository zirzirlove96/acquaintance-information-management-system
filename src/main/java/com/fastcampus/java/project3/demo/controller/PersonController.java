package com.fastcampus.java.project3.demo.controller;

import com.fastcampus.java.project3.demo.domain.Person;
import com.fastcampus.java.project3.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value="/api/person")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

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

}
