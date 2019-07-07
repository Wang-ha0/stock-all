package com.hand.stock.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * 〈功能简述〉
 * 〈股票价格实体类〉
 *
 * @author 王灏
 * @Date 2019/7/4 13:28
 * @Version 1.0.0
 */
@Data
@Table(name = "stock_price")
public class StockPrice implements Comparable<StockPrice>{
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String stockId;
    private Double beginPrice;  // 开盘价
    private Double endPrice;  // 收盘价
    private Double lowestPrice;  // 最低价
    private Double highestPrice;  // 最高价
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date stockDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockPrice that = (StockPrice) o;
        return Objects.equals(stockId, that.stockId) &&
                stockDate.getTime() == that.stockDate.getTime();
    }

    @Override
    public int hashCode() {

        return Objects.hash(stockId, stockDate);
    }

    @Override
    public int compareTo(StockPrice o) {
        if (this.getStockDate().getTime() - o.getStockDate().getTime() > 0) {
            return -1;
        } else if (this.getStockDate().getTime() - o.getStockDate().getTime() == 0) {
            return 0;
        }
        return 1;
    }
}
