package com.hand.stock.service;

import com.hand.stock.pojo.Stock;
import com.hand.stock.vo.PageResult;

import java.util.List;

public interface StockService {

    /**
     * 更新股票数据
     */
    void updateStock();

    /**
     * 查询所有股票信息
     * @return
     */
    List<Stock> listStocks();
    /**
     * 分页查询股票列表
     * @param page
     * @param size
     * @param market
     * @return
     */
    PageResult<Stock> listStocksByPage(Integer page, Integer size, String market, String key);

    /**
     * 分页统计所有股票最近30天涨幅超过5%的次数
     * @param page
     * @param size
     * @return
     */
    PageResult<Stock> countTimes(Integer page, Integer size, String market, String key);
}
