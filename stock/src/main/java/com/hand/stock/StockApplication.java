package com.hand.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.hand.stock.mapper")
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

}
