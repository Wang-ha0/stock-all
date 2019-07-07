package com.hand.stock.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hand.stock.mapper.StockMapper;
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
import java.util.*;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author 王灏
 * @Date 2019/7/4 11:03
 * @Version 1.0.0
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StockPriceService stockPriceService;
    @Autowired
    private HttpUtils httpUtils;

    @Override
    public void updateStock() {
        log.info("--》》开始更新股票数据");
        log.info("--》》爬取股票数据");
        List<Stock> stockList = new ArrayList<>();
        String content = httpUtils.doGetHtml("http://stock.gtimg.cn/data/index.php?appn=rank&t=ranka/chr&p=46&o=0&l=100000&v=list_data");
        List<String> stockIdList = Arrays.asList(content.substring(content.indexOf("data:'") + 6, content.length() - 3).split(","));
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < stockIdList.size(); i++) {
            temp.add(stockIdList.get(i));
            if (temp.size() == 50 || i == (stockIdList.size() - 1)) {
                String[] ids = new String[temp.size()];
                temp.toArray(ids);
                temp.clear();
                String stockIds = StringUtils.join(ids, ",");
                String url = "http://qt.gtimg.cn/q=" + stockIds;
                String html = this.httpUtils.doGetHtml(url);
                String[] stockInfos = html.substring(0, html.length() - 1).split("\\;");
                Stock stock = null;
                for (String stockInfo : stockInfos) {
                    stock = new Stock();
                    // 格式化数据
                    String stockId = stockInfo.split("=")[0].replaceAll("v_", "").trim().replaceAll("", "");
                    String stockName = stockInfo.split("=")[1].split("~")[1];
                    // 封装对象
                    stock.setStockId(stockId);
                    stock.setStockName(stockName);
                    if (stockId.startsWith("sz")) {
                        stock.setStockMarket("sz");
                    } else if (stockId.startsWith("sh")) {
                        stock.setStockMarket("sh");
                    }
                    stockList.add(stock);
                }
            }
        }
        log.info("爬取股票数据完成--《《");
        List<Stock> stocks = stockMapper.selectAll();
        // stock为空则是第一次爬取，爬取所有数据，否则，对于爬取到的新股票爬取10年数据，
        // 存在的股票更新新数据
        if (!CollectionUtils.isEmpty(stocks)) {
            // 删除重复数据
            stockList.removeAll(stocks);
            // 更新原有股票的最新价格数据
            stockPriceService.updateLatestStockPrice(stocks);
        }
        if (!CollectionUtils.isEmpty(stockList)) {
            stockMapper.insertList(stockList);
            // 下载股票十年价格数据
            stockPriceService.updateStockPrice(stockList);
        }

        log.info("更新股票数据完成--《《");
    }

    @Override
    public List<Stock> listStocks() {
        List<Stock> stockList = stockMapper.selectAll();
        if (CollectionUtils.isEmpty(stockList)) {
            throw new StockException(ExceptionEnum.STOCK_NOT_FOUND);
        }
        return stockList;
    }

    @Override
    public PageResult<Stock> listStocksByPage(Integer page, Integer size, String market,String key) {
        PageHelper.startPage(page,size);

        Example example = new Example(Stock.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(market)) {
            criteria.andEqualTo("stockMarket", market);
        }
        // 根据股票代码或名称模糊查询股票信息
        if (StringUtils.isNotBlank(key)) {
            criteria.orLike("stockId", "%" + key + "%").orLike("stockName", "%" + key + "%");
        }
        List<Stock> stockList = stockMapper.selectByExample(example);
        PageInfo<Stock> stockPageInfo = new PageInfo<>(stockList);
        return new PageResult<>(stockPageInfo.getTotal(), stockPageInfo.getPages(), stockList);
    }

    @Override
    public PageResult<Stock> countTimes(Integer page, Integer size, String market, String key) {
        PageHelper.startPage(page,size);
        Example example = new Example(Stock.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(market)) {
            criteria.andEqualTo("stockMarket", market);
        }
        // 根据股票代码或名称模糊查询股票信息
        if (StringUtils.isNotBlank(key)) {
            criteria.orLike("stockId", "%" + key + "%").orLike("stockName", "%" + key + "%");
        }
        List<Stock> stockList = stockMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(stockList)) {
            throw new StockException(ExceptionEnum.STOCK_NOT_FOUND);
        }

        for (Stock stock : stockList) {
            int times = 0;
            List<StockPrice> stockPriceList = stockPriceService.listLatestStockPriceByStockId(stock.getStockId());
            if (stockPriceList.size() < 2) {
                stock.setTimes(times);
                continue;

            }
            Collections.sort(stockPriceList);
            for (int i = 0; i < stockPriceList.size() - 1; i++) {
                StockPrice stockPrice = stockPriceList.get(i);
                StockPrice stockPrice1 = stockPriceList.get(i + 1);
                if ((stockPrice.getEndPrice() - stockPrice1.getEndPrice()) / stockPrice1.getEndPrice() > 0.05) {
                    times++;
                }
            }
            stock.setTimes(times);
        }
        PageInfo<Stock> stockPageInfo = new PageInfo<>(stockList);
        return new PageResult<>(stockPageInfo.getTotal(),stockPageInfo.getPages(),stockList);
    }
}
