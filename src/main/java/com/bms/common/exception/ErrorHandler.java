package com.bms.common.exception;

import com.bms.common.domain.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 统一异常处理.
 *
 * @author luojimeng
 * @date 2020/3/9
 */
@RestControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler
    public ResponseEntity handle(Exception ex) {
        logger.error("error handler", ex);
        if (ex instanceof BaseException) {
            BaseException bex = (BaseException) ex;
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Result.failure(bex.getCode(), bex.getMessage()));
        }

        if (ex instanceof HttpRequestMethodNotSupportedException
                || ex instanceof ConstraintViolationException
                || ex instanceof MethodArgumentNotValidException
                || ex instanceof BindException) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Result.failure(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }
}
