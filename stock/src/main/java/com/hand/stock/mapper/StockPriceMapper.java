package com.hand.stock.mapper;

import com.hand.stock.pojo.StockPrice;
import org.apache.ibatis.annotations.Update;

public interface StockPriceMapper extends BaseMapper<StockPrice> {

    @Update("truncate table stock_price")
    void truncateTable();
}
