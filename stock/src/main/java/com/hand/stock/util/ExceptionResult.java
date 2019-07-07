package com.hand.stock.util;


import com.hand.stock.util.enums.ExceptionEnum;
import lombok.Data;

/**
 * 通用异常展示对象
 */
@Data
public class ExceptionResult {
    private int status;
    private String msg;
    private Long timestamp;
    public ExceptionResult(ExceptionEnum exceptionEnums) {
        this.status = exceptionEnums.getCode();
        this.msg = exceptionEnums.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
