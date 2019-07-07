package com.hand.stock.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;


@Data
@Table(name = "stock")
public class Stock {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String stockId;
    private String stockName;
    private String stockMarket;

   @Transient
   private Integer times;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(stockId, stock.stockId) &&
                Objects.equals(stockMarket, stock.stockMarket);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stockId, stockMarket);
    }
}
