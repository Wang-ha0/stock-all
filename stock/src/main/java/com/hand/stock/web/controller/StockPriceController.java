package com.hand.stock.web.controller;

import com.hand.stock.pojo.StockPrice;
import com.hand.stock.service.StockPriceService;
import com.hand.stock.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈功能简述〉
 * 〈股票价格控制器〉
 *
 * @author 王灏
 * @Date 2019/7/6 8:56
 * @Version 1.0.0
 */
@RestController
@RequestMapping("price")
public class StockPriceController {

    @Autowired
    private StockPriceService stockPriceService;

    /**
     * 根据股票id分页查询股票价格信息
     * @param page
     * @param size
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("all/{stockId}")
    public ResponseEntity<PageResult<StockPrice>> listStockPricesByStockId(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @PathVariable(value = "stockId") String stockId
    ) {
        return ResponseEntity.ok(stockPriceService.listStockPricesByStockId(page, size, sortBy,desc,stockId));
    }

    /**
     * 根据股票代码，按月、1年、3年、5年返回股票价格数据
     * @param stockId
     * @param time  1 月，2 1年， 3 3年，4 5年
     * @return
     */
    @GetMapping("kchart/{stockId}/{time}")
    public ResponseEntity<List<StockPrice>> getKchartByStockIdAndDate(
            @PathVariable(value = "stockId") String stockId,
            @PathVariable(value = "time") Integer time
    ) {
        return ResponseEntity.ok(stockPriceService.getKchartByStockIdAndDate(stockId, time));
    }
}
