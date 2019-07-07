package com.hand.stock.web.advice;

import com.hand.stock.util.ExceptionResult;
import com.hand.stock.util.exception.StockException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 通用异常处理，捕获所有抛出的AnaException异常，并返回给客户端
 */
@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * 捕获并处理所有已知异常
     * @param e
     * @return
     */
    @ExceptionHandler(StockException.class)
    public ResponseEntity<ExceptionResult> handleException(StockException e) {
        log.error(e.getMessage());  // 记录日志
        return ResponseEntity.status(e.getExceptionEnums().getCode()).body(new ExceptionResult(e.getExceptionEnums()));
    }
}
