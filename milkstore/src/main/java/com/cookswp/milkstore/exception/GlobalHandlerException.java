package com.cookswp.milkstore.exception;

import com.cookswp.milkstore.response.ResponseError;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Objects;


@ControllerAdvice
public class GlobalHandlerException extends RuntimeException {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseError> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        System.out.println("App Exception Handler");
        ResponseError responseError = new ResponseError();
        responseError.setCode(errorCode.getCode());
        responseError.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError>handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumKey = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        System.out.println("METHOD_ARGUMENT_NOT_VALID_EXCEPTION");
        ResponseError respError = new ResponseError();
        respError.setCode(errorCode.getCode());
        respError.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(respError);
    }


}
