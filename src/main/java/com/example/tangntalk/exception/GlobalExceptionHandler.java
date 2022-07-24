package com.example.tangntalk.exception;

import com.example.tangntalk.common.dto.Response;
import com.example.tangntalk.view.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionResponse> handleGlobalException(GlobalException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getBody());
    }


    @ExceptionHandler(value = {CommonException.class})
    protected ResponseEntity<ErrorResponse> handleCommonException(CommonException e) {
        log.error("handleCommonException throw CommonException : {}", e);
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        log.error(exceptionAsString);

        return ResponseEntity.status(500).body(
                new ErrorResponse(false, e.getCode())
        );
    }




    /*
        db 단에서의 에러를 임시러 처리하게 했다.
        추후에 validation 과정을 자세하게 구현해야한다.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected Response.Error handleDataIntegrityViolationException(DataIntegrityViolationException e){
        log.error("throw DataIntegrityViolation Exception : {}", e);
        return new Response.Error("DataIntegrityViolationException");
    }

    // TODO: 이러면 내부 에러까지 다 전달되니 커스텀 Exception을
    //  만들어서 처리하고 IllegalArgumentException은 따로 처리하는건 어떨까요
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected Response.Error handleIllegalArgumentException(IllegalArgumentException e){
        log.error("throw IllegalArgumentException Exception : {}", e);
        return new Response.Error(e.getMessage());
    }




}
