package com.hand.stock.web.controller;

import com.hand.stock.pojo.Stock;
import com.hand.stock.service.StockService;
import com.hand.stock.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("stock")
public class StockController {

    @Autowired
    private StockService stockService;


    /**
     * 分页查询股票列表
     * @param page
     * @param size
     * @param market
     * @return
     */
    @GetMapping("all")
    public ResponseEntity<PageResult<Stock>> listStocksByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "market", required = false) String market
    ) {
        return ResponseEntity.ok(stockService.listStocksByPage(page, size, market,key));
    }

    /**
     * 分页统计所有股票最近30天涨幅超过5%的次数
     * @param page
     * @param size
     * @return
     */
    @GetMapping("count")
    public ResponseEntity<PageResult<Stock>> countTimes(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "market", required = false) String market
    ) {
        return ResponseEntity.ok(stockService.countTimes(page, size, market,key));
    }
}
