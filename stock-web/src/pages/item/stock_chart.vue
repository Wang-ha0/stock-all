<template>
  <v-container fluid grid-list-md>
    <v-toolbar class="elevation-0" style="color: #409EFF">
      <v-spacer/>
      <v-toolbar-title v-text="stock.stockId"/>
      <v-toolbar-title v-text="stock.stockName"/>
      <v-spacer/>
    </v-toolbar>
    <v-layout row wrap>
      <v-flex xs10 md6>
        <v-card>
          <v-card-text class="px2">
            <div ref="mouthLie" style="width: 100%;height:400px"></div>
          </v-card-text>
        </v-card>
      </v-flex>

      <v-flex xs10 md6>
        <v-card>
          <v-card-text class="px2">
            <div ref="oneYearLine" style="width: 100%;height:400px"></div>
          </v-card-text>
        </v-card>
      </v-flex>
      <v-flex xs10 md6>
        <v-card>
          <v-card-text class="px2">
            <div ref="threeYearLine" style="width: 100%;height:400px"></div>
          </v-card-text>
        </v-card>
      </v-flex>

      <v-flex xs10 md6>
        <v-card>
          <v-card-text class="px2">
            <div ref="fiveYearLine" style="width: 100%;height:400px"></div>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  export default {
    name: "dashboard",
    data() {
      return {
        mouthLieOption : {
          title: {
            text: '月K',
            left: 'left'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data:['开盘价','收盘价','最低价','最高价']
          },
          xAxis: {
            type: 'category',
            data: [],
            name: '日期'
          },
          yAxis: {
            type: 'value',
            name: '数量',
          },
          series: [
            {
              name: '开盘价',
              data: [],
              type: 'line'
            },
            {
              name: '收盘价',
              data: [],
              type: 'line'
            },
            {
              name: '最低价',
              data: [],
              type: 'line'
            },
            {
              name: '最高价',
              data: [],
              type: 'line'
            }
          ]
        },
        oneYearOption : {
          title: {
            text: '1年',
            left: 'left'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data:['开盘价','收盘价','最低价','最高价']
          },
          xAxis: {
            type: 'category',
            data: [],
            name: '日期'
          },
          yAxis: {
            type: 'value',
            name: '数量',
          },
          series: [
            {
              name: '开盘价',
              data: [],
              type: 'line'
            },
            {
              name: '收盘价',
              data: [],
              type: 'line'
            },
            {
              name: '最低价',
              data: [],
              type: 'line'
            },
            {
              name: '最高价',
              data: [],
              type: 'line'
            }
          ]
        },
        threeYearOption : {
          title: {
            text: '3年',
            left: 'left'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data:['开盘价','收盘价','最低价','最高价']
          },
          xAxis: {
            type: 'category',
            data: [],
            name: '日期'
          },
          yAxis: {
            type: 'value',
            name: '数量',
          },
          series: [
            {
              name: '开盘价',
              data: [],
              type: 'line'
            },
            {
              name: '收盘价',
              data: [],
              type: 'line'
            },
            {
              name: '最低价',
              data: [],
              type: 'line'
            },
            {
              name: '最高价',
              data: [],
              type: 'line'
            }
          ]
        },
        fiveYearOption : {
          title: {
            text: '5年',
            left: 'left'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data:['开盘价','收盘价','最低价','最高价']
          },
          xAxis: {
            type: 'category',
            data: [],
            name: '日期'
          },
          yAxis: {
            type: 'value',
            name: '数量',
          },
          series: [
            {
              name: '开盘价',
              data: [],
              type: 'line'
            },
            {
              name: '收盘价',
              data: [],
              type: 'line'
            },
            {
              name: '最低价',
              data: [],
              type: 'line'
            },
            {
              name: '最高价',
              data: [],
              type: 'line'
            }
          ]
        },
        stock:''
      }
    },
    created() {
      this.stock = JSON.parse(localStorage.getItem("stock"));
      this.getMouthKchartByStockIdAndDate();
      this.getOneYearKchartByStockIdAndDate();
      this.getThreeYearKchartByStockIdAndDate();
      this.getFiveYearKchartByStockIdAndDate();

    },
    methods: {
      getMouthKchartByStockIdAndDate() {
        this.$http.get("/price/kchart/"+ this.stock.stockId + "/1").then(resp => {
          console.log(resp);
          for (let i in resp.data) {
            this.mouthLieOption.xAxis.data.push(resp.data[i].stockDate);
            this.mouthLieOption.series[0].data.push(resp.data[i].beginPrice);
            this.mouthLieOption.series[1].data.push(resp.data[i].endPrice);
            this.mouthLieOption.series[2].data.push(resp.data[i].lowestPrice);
            this.mouthLieOption.series[3].data.push(resp.data[i].highestPrice);
          }
          this.drawMouthLieOptionLie();
        })
      },
      getOneYearKchartByStockIdAndDate() {
        this.$http.get("/price/kchart/"+ this.stock.stockId + "/2").then(resp => {
          console.log(resp);
          for (let i in resp.data) {
            this.oneYearOption.xAxis.data.push(resp.data[i].stockDate);
            this.oneYearOption.series[0].data.push(resp.data[i].beginPrice);
            this.oneYearOption.series[1].data.push(resp.data[i].endPrice);
            this.oneYearOption.series[2].data.push(resp.data[i].lowestPrice);
            this.oneYearOption.series[3].data.push(resp.data[i].highestPrice);
          }
          this.drawOneYearOptionLie();
        })
      },
      getThreeYearKchartByStockIdAndDate() {
        this.$http.get("/price/kchart/"+ this.stock.stockId + "/3").then(resp => {
          console.log(resp);
          for (let i in resp.data) {
            this.threeYearOption.xAxis.data.push(resp.data[i].stockDate);
            this.threeYearOption.series[0].data.push(resp.data[i].beginPrice);
            this.threeYearOption.series[1].data.push(resp.data[i].endPrice);
            this.threeYearOption.series[2].data.push(resp.data[i].lowestPrice);
            this.threeYearOption.series[3].data.push(resp.data[i].highestPrice);
          }
          this.drawThreeYearOptionLie();
        })
      },
      getFiveYearKchartByStockIdAndDate() {
        this.$http.get("/price/kchart/"+ this.stock.stockId + "/4").then(resp => {
          console.log(resp);
          for (let i in resp.data) {
            this.fiveYearOption.xAxis.data.push(resp.data[i].stockDate);
            this.fiveYearOption.series[0].data.push(resp.data[i].beginPrice);
            this.fiveYearOption.series[1].data.push(resp.data[i].endPrice);
            this.fiveYearOption.series[2].data.push(resp.data[i].lowestPrice);
            this.fiveYearOption.series[3].data.push(resp.data[i].highestPrice);
          }
          this.drawFiveYearOptionLie();
        })
      },
      drawMouthLieOptionLie() {
        //基于准本好的DOM，初始化echarts实例
        let lie = this.$echarts.init(this.$refs.mouthLie);
        //绘制图表
        lie.setOption(this.mouthLieOption);
      },
      drawOneYearOptionLie() {
        //基于准本好的DOM，初始化echarts实例
        let lie = this.$echarts.init(this.$refs.oneYearLine);
        //绘制图表
        lie.setOption(this.oneYearOption);
      },
      drawThreeYearOptionLie() {
        //基于准本好的DOM，初始化echarts实例
        let lie = this.$echarts.init(this.$refs.threeYearLine);
        //绘制图表
        lie.setOption(this.threeYearOption);
      },
      drawFiveYearOptionLie() {
        //基于准本好的DOM，初始化echarts实例
        let lie = this.$echarts.init(this.$refs.fiveYearLine);
        //绘制图表
        lie.setOption(this.fiveYearOption);
      },
    }
  }
</script>

<style scoped>

</style>
