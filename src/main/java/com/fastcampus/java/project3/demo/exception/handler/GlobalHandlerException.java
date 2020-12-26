package com.fastcampus.java.project3.demo.exception.handler;

import com.fastcampus.java.project3.demo.exception.PersonNotFoundException;
import com.fastcampus.java.project3.demo.exception.RenameIsNotPermittedException;
import com.fastcampus.java.project3.demo.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice //RestController에 전역적으로 적용되게 하는 어노테이션
public class GlobalHandlerException {

    //이름 변경에 대한 에러문으로 status 400 runtimeerror가 아닌 서버 내부의 에러이기 때문에.
    @ExceptionHandler(RenameIsNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRenameNoPermittedException(RenameIsNotPermittedException ex) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    //person entity가 없는 경우 400
    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePersonNotFoundException(PersonNotFoundException ex){
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    //위 두개의 exception이 아닌 경우 모두 다 여기로 온다.
    //500이라는 에러가 client에게 알려지면 해커에 의해 위험한 상황이 발생할 수 있으므로
    //ex.getMeessage()를 log에 나타내 주고, 일반적인 메세지 문구를 client에게 보여준다.
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(RuntimeException ex){
        log.error("server error: ",ex.getMessage());
        return ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "This is not found Error");
    }


}
