package com.hand.stock.util.exception;

import com.hand.stock.util.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockException extends RuntimeException {

    private ExceptionEnum exceptionEnums;

}