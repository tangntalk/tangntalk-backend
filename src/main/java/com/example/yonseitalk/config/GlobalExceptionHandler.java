package com.example.yonseitalk.config;

import com.example.yonseitalk.exception.CommonException;
import com.example.yonseitalk.exception.DuplicateAccountException;
import com.example.yonseitalk.view.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CommonException.class})
    protected ResponseEntity<ErrorResponse> handleCommonException(CommonException e) {
        log.error("handleCommonException throw CommonException : {}", e.getCode());

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        log.error(exceptionAsString);

        return ResponseEntity.status(500).body(
                new ErrorResponse(false, e.getCode())
        );
    }

    @ExceptionHandler(value = {DuplicateAccountException.class})
    protected ResponseEntity<ErrorResponse> handleDuplicateAccountException(DuplicateAccountException e){
        log.error("throw DuplicatedAccount Exception : {}", e.getCode());
        return ResponseEntity.status(401).body(
                new ErrorResponse(false, e.getCode())
        );
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        log.error("throw DataIntegrityViolation Exception : {}", "DataIntegrityViolationException");
        return ResponseEntity.status(401).body(
                new ErrorResponse(false, "DataIntegrityViolationException")
        );
    }


}
