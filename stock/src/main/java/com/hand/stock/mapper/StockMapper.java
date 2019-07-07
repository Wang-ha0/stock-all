package com.hand.stock.mapper;

import com.hand.stock.pojo.Stock;
import org.apache.ibatis.annotations.Update;

public interface StockMapper extends BaseMapper<Stock> {
    @Update("truncate table stock")
    void truncateTable();
}
