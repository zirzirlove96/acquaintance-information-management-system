package com.fastcampus.java.project3.demo.controller;

import com.fastcampus.java.project3.demo.domain.Person;
import com.fastcampus.java.project3.demo.dto.PersonDto;
import com.fastcampus.java.project3.demo.exception.PersonNotFoundException;
import com.fastcampus.java.project3.demo.exception.RenameIsNotPermittedException;
import com.fastcampus.java.project3.demo.exception.dto.ErrorResponse;
import com.fastcampus.java.project3.demo.repository.PersonRepository;
import com.fastcampus.java.project3.demo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void savePerson(@RequestBody PersonDto personDto){
        personService.save(personDto);
        //log.info("person -> {}",personRepository.findAll());
    }

    //수정
    @PutMapping(value="/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto personDto){
        personService.modify(id, personDto);
        //log.info("person -> {}",personRepository.findAll());
    }

    @PatchMapping(value="/{id}")//PatchMapping은 일부 데이터만 고친다는 뜻으로 사용한다.
    public void modifyPerson(@PathVariable Long id, String name){
        personService.modify(id,name);
        //log.info("person -> {}",personRepository.findAll());
    }

    /*delete*/
    @DeleteMapping(value="/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);

        //log.info("person -> {}",personRepository.findAll());

        //personService.delete이 이루어지면 true값이 나온다.
        //return personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(id));
    }

    //이름 변경에 대한 에러문으로 status 400 runtimeerror가 아닌 서버 내부의 에러이기 때문에.
    @ExceptionHandler(value = RenameIsNotPermittedException.class)
    public ResponseEntity<ErrorResponse> handleRenameNoPermittedException(RenameIsNotPermittedException ex) {
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST,ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    //person entity가 없는 경우 400
    @ExceptionHandler(value = PersonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonNotFoundException(PersonNotFoundException ex){
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    //위 두개의 exception이 아닌 경우 모두 다 여기로 온다.
    //500이라는 에러가 client에게 알려지면 해커에 의해 위험한 상황이 발생할 수 있으므로
    //ex.getMeessage()를 log에 나타내 주고, 일반적인 메세지 문구를 client에게 보여준다.
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex){
        log.error("server error: ",ex.getMessage());
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "This is not found Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
