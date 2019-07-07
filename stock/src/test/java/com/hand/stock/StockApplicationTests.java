package com.hand.stock;

import com.hand.stock.task.ItemTask;
import com.hand.stock.util.HttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockApplicationTests {
    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private ItemTask itemTask;

    @Test
    public void test1() throws Exception {
        itemTask.itemTask();
    }
}
