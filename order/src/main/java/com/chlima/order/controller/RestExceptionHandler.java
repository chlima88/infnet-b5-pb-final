package com.chlima.order.controller;

import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UnsupportedOperationException.class})
    private ResponseEntity<Object> badRequestException(Exception exception){
        return handler(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    private ResponseEntity<Object> notFoundException(Exception exception){
        return handler(exception, HttpStatus.NOT_FOUND);
    }


    private ResponseEntity<Object> handler(Exception exception, HttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(new RestErrorMessage(
                        httpStatus,
                        exception.getClass().getSimpleName() + ": " + exception.getMessage()
                ));

    }



}
