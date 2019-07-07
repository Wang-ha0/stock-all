package com.hand.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hand.stock.mapper.StockPriceMapper;
import com.hand.stock.pojo.Stock;
import com.hand.stock.pojo.StockPrice;
import com.hand.stock.service.StockPriceService;
import com.hand.stock.service.StockService;
import com.hand.stock.util.HttpUtils;
import com.hand.stock.util.enums.ExceptionEnum;
import com.hand.stock.util.exception.StockException;
import com.hand.stock.vo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author 王灏
 * @Date 2019/7/4 15:20
 * @Version 1.0.0
 */
@Service
@Slf4j
public class StockPriceServiceImpl implements StockPriceService {
    @Autowired
    private StockPriceMapper stockPriceMapper;
    @Autowired
    private StockService stockService;
    @Autowired
    private HttpUtils httpUtils;
    @Override
    public void updateStockPrice(List<Stock> stockList) {
        log.info("--》》爬取股票价格数据");
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);     // 当前年
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        for (Stock stock : stockList) {
            List<StockPrice> stockPriceList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                String url = "http://data.gtimg.cn/flashdata/hushen/daily/" + String.valueOf(currentYear - i).substring(2) + "/" + stock.getStockId() + ".js?visitDstTime=1";
                String html = this.httpUtils.doGetHtml(url).replaceAll("", "");
                html = html.replaceAll("\\\\n\\\\", "");
                if (StringUtils.isEmpty(html)) {
                    continue;
                }
                String[] priceStrings = html.substring(html.indexOf("\n") + 1, html.length() - 2).split("\n");
                StockPrice stockPrice = null;
                for (String priceStr : priceStrings) {
                    String[] priceAttr = priceStr.split(" ");
                    stockPrice = new StockPrice();
                    stockPrice.setBeginPrice(Double.parseDouble(priceAttr[1]));
                    stockPrice.setEndPrice(Double.parseDouble(priceAttr[2]));
                    stockPrice.setHighestPrice(Double.parseDouble(priceAttr[3]));
                    stockPrice.setLowestPrice(Double.parseDouble(priceAttr[4]));
                    stockPrice.setStockId(stock.getStockId());
                    try {
                        stockPrice.setStockDate(sdf.parse(priceAttr[0]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    stockPriceList.add(stockPrice);
                }
            }
            stockPriceMapper.insertList(stockPriceList);
        }
        log.info("更新股票价格数据完成");
    }

    @Override
    public List<StockPrice> listLatestStockPriceByStockId(String stockId) {
        Example example = new Example(StockPrice.class);
        Example.Criteria criteria = example.createCriteria();
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-30);
        Date beginDate = calendar.getTime();
        criteria.andEqualTo("stockId",stockId);
        criteria.andBetween("stockDate",beginDate,endDate);
        List<StockPrice> stockPriceList = stockPriceMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(stockPriceList)) {
            throw new StockException(ExceptionEnum.STOCK_PRICE_NOT_FOUND);
        }
        return stockPriceList;
    }

    @Override
    public PageResult<StockPrice> listStockPricesByStockId(Integer page, Integer size, String sortBy, boolean desc, String stockId) {
        PageHelper.startPage(page, size);
        Example example = new Example(StockPrice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("stockId",stockId);
        // 排序
        if (StringUtils.isNotBlank(sortBy)) {
            if (desc) {
                example.orderBy(sortBy).desc();
            } else {
                example.orderBy(sortBy).asc();
            }

        }
        List<StockPrice> stockPriceList = stockPriceMapper.selectByExample(example);
        // 封装结果
        PageInfo<StockPrice> stockPricePageInfo = new PageInfo<>(stockPriceList);
        return new PageResult<>(stockPricePageInfo.getTotal(),stockPricePageInfo.getPages(),stockPriceList);
    }

    @Override
    public List<StockPrice> getKchartByStockIdAndDate(String stockId, Integer time) {
        Example example = new Example(StockPrice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("stockId",stockId);
        Calendar calendar = Calendar.getInstance();
        // 1 月k; 2 1年; 3 3年; 4 5 年;
        if (time == 1) {
            calendar.add(Calendar.MONTH,-1);
        }
        if (time == 2) {
            calendar.add(Calendar.YEAR,-1);
        }
        if (time == 3) {
            calendar.add(Calendar.YEAR,-3);
        }
        if (time == 4) {
            calendar.add(Calendar.YEAR,-5);
        }
        criteria.andBetween("stockDate",calendar.getTime(),new Date());
        List<StockPrice> stockPriceList = stockPriceMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(stockPriceList)) {
            throw new StockException(ExceptionEnum.STOCK_PRICE_NOT_FOUND);
        }
        return stockPriceList;
    }

    @Override
    public void updateLatestStockPrice(List<Stock> stocks) {
        log.info("--》》爬取股票价格数据");
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);     // 当前年
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        for (Stock stock : stocks) {
            List<StockPrice> stockPriceList = new ArrayList<>();
                String url = "http://data.gtimg.cn/flashdata/hushen/daily/" + String.valueOf(currentYear).substring(2) + "/" + stock.getStockId() + ".js?visitDstTime=1";
                String html = this.httpUtils.doGetHtml(url).replaceAll("", "");
                html = html.replaceAll("\\\\n\\\\", "");
                if (StringUtils.isEmpty(html)) {
                    continue;
                }
                String[] priceStrings = html.substring(html.indexOf("\n") + 1, html.length() - 2).split("\n");
                StockPrice stockPrice = null;
                for (String priceStr : priceStrings) {
                    String[] priceAttr = priceStr.split(" ");
                    stockPrice = new StockPrice();
                    stockPrice.setBeginPrice(Double.parseDouble(priceAttr[1]));
                    stockPrice.setEndPrice(Double.parseDouble(priceAttr[2]));
                    stockPrice.setHighestPrice(Double.parseDouble(priceAttr[3]));
                    stockPrice.setLowestPrice(Double.parseDouble(priceAttr[4]));
                    stockPrice.setStockId(stock.getStockId());
                    try {
                        stockPrice.setStockDate(sdf.parse(priceAttr[0]));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    stockPriceList.add(stockPrice);
                }
            Example example = new Example(StockPrice.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("stockId",stock.getStockId());
            criteria.andCondition("YEAR(stock_date)=", currentYear);
            List<StockPrice> oldStockPriceList = stockPriceMapper.selectByExample(example);
            stockPriceList.removeAll(oldStockPriceList);
            if (!CollectionUtils.isEmpty(stockPriceList)) {
                stockPriceMapper.insertList(stockPriceList);
            }

        }
        log.info("更新股票价格数据完成");
    }
}
