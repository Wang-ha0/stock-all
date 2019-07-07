package com.hand.stock.service;

import com.hand.stock.pojo.Stock;
import com.hand.stock.pojo.StockPrice;
import com.hand.stock.vo.PageResult;

import java.util.List;

public interface StockPriceService {

    /**
     * 更新股票十年价格数据
     */
    void updateStockPrice(List<Stock> stocks);

    /**
     * 查询股票最近30天的价格信息
     * @param stockId
     * @return
     */
    List<StockPrice> listLatestStockPriceByStockId(String stockId);
    /**
     * 根据股票id分页查询股票价格信息
     * @param page
     * @param size
     * @param sortBy
     * @param desc
     * @return
     */
    PageResult<StockPrice> listStockPricesByStockId(Integer page, Integer size, String sortBy, boolean desc, String stockId);
    /**
     * 根据股票代码，按月、1年、3年、5年返回股票价格数据
     * @param stockId
     * @param time  1 月，2 1年， 3 3年，4 5年
     * @return
     */
    List<StockPrice> getKchartByStockIdAndDate(String stockId, Integer time);
    /**
     * 更新股票最新价格数据
     */
    void updateLatestStockPrice(List<Stock> stocks);
}
