package com.cos.blog.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleArgumentException(Exception ex) {

        if (log.isErrorEnabled()) {
            log.error("=================================================");
            log.error("", ex);
            log.error("=================================================");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("<h1>"+ex.getMessage()+"</h1>");
    }
//    public String handleArgumentException(IllegalArgumentException e) {
//        return "<h1>"+e.getMessage()+"</h1>";
//    }
}
