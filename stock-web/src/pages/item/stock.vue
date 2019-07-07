<template>
  <v-card>
    <v-card-title>
      <v-flex xs2>
        <v-btn-toggle mandatory v-model.lazy="market">
          <v-btn flat value="">
            全部
          </v-btn>
          <v-btn flat value="sh">
            上海
          </v-btn>
          <v-btn flat value="sz">
            深圳
          </v-btn>
        </v-btn-toggle>
      </v-flex>
      <v-spacer/>
      <v-flex xs3>
      <v-text-field label="股票代码/名称" v-model.lazy="search" append-icon="search" hide-details/>
      </v-flex>
    </v-card-title>
    <v-divider/>
    <v-data-table
      :headers="headers"
      :items="stocks"
      :pagination.sync="pagination"
      :total-items="totalstocks"
      :loading="loading"
      class="elevation-1"
    >
      <template slot="items" slot-scope="props">
        <td class="text-xs-center">{{ props.item.id }}</td>
        <td class="text-xs-center" >
          <span v-if="props.item.stockMarket == 'sh'"> 上海</span>
          <span v-if="props.item.stockMarket == 'sz'"> 深圳</span>

        </td>
        <td class="text-xs-center">{{ props.item.stockId }}</td>

        <td class="text-xs-center">{{ props.item.stockName }}</td>
        <td class="justify-center layout px-0">
          <v-btn icon @click="showDetial(props.item)">
            <span style="color: #409EFF">详情</span>
          </v-btn>
        </td>
      </template>
    </v-data-table>
  </v-card>
</template>

<script>
  export default {
    name: "stock",
    data() {
      return {
        search: '', // 搜索过滤字段
        totalstocks: 0, // 总条数
        market:'',
        stocks: [], // 当前页品牌数据
        loading: true, // 是否在加载中
        pagination: {}, // 分页信息
        headers: [
          {text: 'id', align: 'center', value: 'id', sortable: false},
          {text: '市场', align: 'center', sortable: false},
          {text: '股票代码', align: 'center', sortable: false},
          {text: '名称', align: 'center', value: 'letter', sortable: false},
          {text: '操作', align: 'center', value: 'id', sortable: false}
        ],
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
      },
      search: { // 监视搜索字段
        handler() {
          this.getDataFromServer();
        }
      },
      market: { // 监视搜索字段
        handler() {
          this.getDataFromServer();
        }
      }
    },
    methods: {
      getDataFromServer() { // 从服务的加载数的方法。
        // 发起请求
        this.$http.get("/stock/all", {
          params: {
            key: this.search, // 搜索条件
            page: this.pagination.page,// 当前页
            size: this.pagination.rowsPerPage,// 每页大小
            market: this.market,
            desc: this.pagination.descending// 是否降序
          }
        }).then(resp => { // 这里使用箭头函数
          this.stocks = resp.data.items;
          this.totalstocks = resp.data.total;
          // 完成赋值后，把加载状态赋值为false
          this.loading = false;
        })
      },
      showDetial(stock) {
        localStorage.setItem("stock", JSON.stringify(stock));
        this.$router.push("/item/stock_price");
      },
    },
  }
</script>

<style scoped>

</style>
