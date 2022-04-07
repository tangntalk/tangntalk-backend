package com.example.tangntalk.config;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.exception.CommonException;
import com.example.tangntalk.exception.DuplicateAccountException;
import com.example.tangntalk.exception.NotFoundException;
import com.example.tangntalk.view.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RestControllerAdvice
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


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = {DuplicateAccountException.class})
    protected Response.Error handleDuplicateAccountException(DuplicateAccountException e){
        log.error("throw DuplicatedAccount Exception : {}", e.getCode());
        return new Response.Error(e.getCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {NotFoundException.class})
    protected Response.Error handleNotFoundException(NotFoundException e){
        log.error("throw NonFoundException : {}", "NotFoundException");
        return new Response.Error(e.getCode());
    }

    /*
        db 단에서의 에러를 임시러 처리하게 했다.
        추후에 validation 과정을 자세하게 구현해야한다.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected Response.Error handleDataIntegrityViolationException(DataIntegrityViolationException e){
        log.error("throw DataIntegrityViolation Exception : {}", "DataIntegrityViolationException");
        return new Response.Error("DataIntegrityViolationException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected Response.Error handleIllegalArgumentException(IllegalArgumentException e){
        return new Response.Error(e.getMessage());
    }




}
