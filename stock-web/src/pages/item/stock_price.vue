<template>
  <v-card>
    <v-toolbar class="elevation-0" style="color: #409EFF">
      <v-spacer/>
      <v-toolbar-title v-text="stock.stockId"/>
      <v-toolbar-title v-text="stock.stockName"/>
      <v-spacer/>
      <v-btn color="primary" @click="showChart">K线图</v-btn>
    </v-toolbar>
    <v-divider/>
    <v-data-table
      :headers="headers"
      :items="StockPriceList"
      :pagination.sync="pagination"
      :total-items="totalStockPrice"
      :loading="loading"
      class="elevation-1"
    >
      <template slot="items" slot-scope="props">
        <td class="text-xs-center">{{ props.item.id }}</td>
        <td class="text-xs-center">{{ props.item.stockDate }}</td>
        <td class="text-xs-center">{{ props.item.beginPrice }}</td>
        <td class="text-xs-center">{{props.item.endPrice}}</td>
        <td class="text-xs-center">{{ props.item.lowestPrice }}</td>
        <td class="text-xs-center">{{ props.item.highestPrice }}</td>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>

  export default {
    name: "StockPrice",
    data() {
      return {
        stock:{
          stockId:'',
          stockName:"",
          stockMarket:""
        },
        totalStockPrice: 0, // 总条数
        StockPriceList: [],
        loading: true, // 是否在加载中
        pagination: {}, // 分页信息
        headers: [
          {text: '编号', align: 'center', sortable: false},
          {text: '日期', align: 'center', sortable: true, value: 'stockDate'},
          {text: '开盘价', align: 'center', sortable: true, value: 'beginPrice'},
          {text: '收盘价', align: 'center', value: 'endPrice', sortable: true},
          {text: '最低价', align: 'center', value: 'lowestPrice', sortable: true},
          {text: '最高价', align: 'center', value: 'highestPrice', sortable: true},
        ]
      }
    },
    mounted() { // 渲染后执行
      // 查询数据
      this.getDataFromServer();
    },
    watch: {
      pagination: { // 监视pagination属性的变化
        deep: true, // deep为true，会监视pagination的属性及属性中的对象属性变化
        handler() {
          // 变化后的回调函数，这里我们再次调用getDataFromServer即可
          this.getDataFromServer();
        }
      }
    },
    methods: {
      getDataFromServer() { // 从服务的加载数的方法。
        // 发起请求
        this.stock = JSON.parse(localStorage.getItem("stock"));
        this.$http.get("/price/all/" + this.stock.stockId, {
          params: {
            page: this.pagination.page,// 当前页
            size: this.pagination.rowsPerPage,// 每页大小
            sortBy: this.pagination.sortBy,// 排序字段
            desc: this.pagination.descending// 是否降序
          }
        }).then(resp => { // 这里使用箭头函数
          this.StockPriceList = resp.data.items;
          this.totalStockPrice = resp.data.total;
          // 完成赋值后，把加载状态赋值为false
          this.loading = false;
        })
      },
      showChart() {
        this.$router.push("/item/stock_chart");
      }
    },
  }
</script>

<style scoped>

</style>
