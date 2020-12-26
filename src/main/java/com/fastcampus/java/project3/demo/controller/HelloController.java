package com.fastcampus.java.project3.demo.controller;

import com.fastcampus.java.project3.demo.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/helloWorld")
    public String show(){
        return "Hello World!!";
    }

    @GetMapping("/api/helloException")
    public String helloException() {
        throw new RuntimeException("hello All Exception");
    }

    /*
    //helloException에 대한 오류에 대한 exception handler
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleRuntimeError(RuntimeException ex){

        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,"알 수 없는 에러가 발생했습니다."),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }*/


}
