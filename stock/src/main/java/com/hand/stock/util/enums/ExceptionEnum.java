package com.hand.stock.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 异常枚举类型
 *
 * @Getter 编译的时候自动生成getter方法
 * @NoArgsConstructor 编译的时候自动生成无参构造
 * @AllArgsConstructor 编译的时候自动生成全参构造
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    STOCK_NOT_FOUND(404, "股票列表为空"),
    STOCK_PRICE_NOT_FOUND(404, "价格列表为空");
    private int code;   // HttpStatus状态码
    private String msg;     // 错误消息


}
