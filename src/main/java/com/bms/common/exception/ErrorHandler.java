package com.bms.common.exception;

import com.bms.common.domain.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity handle(Exception ex) {
        if (ex instanceof BaseException) {
            BaseException bex = (BaseException) ex;
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Result.failure(bex.getCode(), bex.getMessage()));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }
}