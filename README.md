# stock-all
# stock 后台系统
> a Spring Boot Project

## 启动
``` bash
# server port 8080
run StockApplication

## 数据库 
url: jdbc:mysql://127.0.0.1:3306/stock
ursername : root
password : root
建表
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `stock_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '股票代码',
  `stock_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `stock_market` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '市场(sh 上海 sz 深圳) ',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `stock_id`(`stock_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3635 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for stock_price
-- ----------------------------
DROP TABLE IF EXISTS `stock_price`;
CREATE TABLE `stock_price`  (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `stock_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '股票代码',
  `stock_date` date DEFAULT NULL COMMENT '股票日期',
  `begin_price` double(10, 2) DEFAULT NULL COMMENT '开盘价',
  `end_price` double(10, 2) DEFAULT NULL COMMENT '收盘价',
  `lowest_price` double(10, 2) DEFAULT NULL COMMENT '最低价',
  `highest_price` double(10, 2) DEFAULT NULL COMMENT '最高价',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id-data`(`stock_id`, `stock_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5820676 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
```

# stock-web 前端界面
> A Vue.js project

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:9001
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```
