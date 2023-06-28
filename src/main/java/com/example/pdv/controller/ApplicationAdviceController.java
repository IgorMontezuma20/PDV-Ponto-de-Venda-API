package com.example.pdv.controller;

import com.example.pdv.dto.ResponseDTO;
import com.example.pdv.exceptions.InvalidOperationException;
import com.example.pdv.exceptions.NoItemException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationAdviceController {

    @ExceptionHandler(NoItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerNoItemException(NoItemException exception){
        String errorMessage = exception.getMessage();
        return new ResponseDTO(errorMessage);
    }

    @ExceptionHandler(InvalidOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDTO handlerInvalidOperationException(InvalidOperationException exception){
        String errorMessage = exception.getMessage();
        return new ResponseDTO(errorMessage);
    }

}
