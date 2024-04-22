package com.library.Library.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHander {


    @ExceptionHandler(NoHandlerFoundException.class)
    ResponseEntity<ErrorResposne> noHandlerFoundHandler(NoHandlerFoundException ex, WebRequest wr)
    {
         ErrorResposne resposne = new ErrorResposne(ex.getMessage(),wr.getDescription(false), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(resposne);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ErrorResposne> illegalArgumentExceptionHandler(IllegalArgumentException ex, WebRequest wr) {
        ErrorResposne resposne = new ErrorResposne(ex.getMessage(), wr.getDescription(false), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(resposne);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResposne> exceptionHandler(Exception ex, WebRequest wr) {
        ErrorResposne resposne = new ErrorResposne(ex.getMessage(), wr.getDescription(false), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(resposne);
    }
}
