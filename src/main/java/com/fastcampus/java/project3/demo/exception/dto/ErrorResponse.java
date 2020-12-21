package com.fastcampus.java.project3.demo.exception.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private int code;
    private String message;

    //이렇게 해주면 PersonController에서 handle메서드의 value값을 안해줘도 여기에서 알아서 value값을 넣어준다.
    public static ErrorResponse of(HttpStatus httpStatus, String message) {
        return new ErrorResponse(httpStatus.value(), message);
    }
}
