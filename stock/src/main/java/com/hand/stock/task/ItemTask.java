package com.hand.stock.task;

import com.hand.stock.service.StockPriceService;
import com.hand.stock.service.StockService;
import com.hand.stock.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author 王灏
 * @Date 2019/7/4 22:53
 * @Version 1.0.0
 */
@Component
@Slf4j
public class ItemTask {
    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private StockService stockService;
    @Autowired
    private StockPriceService stockPriceService;

    /**
     * 每天定时更新股票数据
     *
     * @throws Exception
     */
    @Scheduled(fixedDelay = 24*60*60)
    public void itemTask() throws Exception {
        // 下载股票数据
        stockService.updateStock();
    }
}
