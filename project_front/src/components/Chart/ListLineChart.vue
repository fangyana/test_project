<template>
  <div style="margin:auto;">
    <div
      id="list-line-chart"
      ref="listLineChart"
      :key="Math.random()"
      style="margin:auto;width:750px; height:450px;"
    />
  </div>
</template>

<script>
// import { formatter } from "@/utils/handleTime";
export default {
  name: 'ListLineChart',
  props: {
    legendData: {
      type: Array,
      default() {
        return []
      }
    },
    avgscoreList: {
      type: Array,
      default() {
        return []
      }
    },
    avgscorehistoryList: {
      type: Array,
      default() {
        return []
      }
    }
  },
  watch: {
    legendData: function(indexVal, oldVal) {
      this.coinConsumeChart()
    }
  },
  methods: {
    coinConsumeChart() {
      const avgscoreList = []
      this.avgscoreList.forEach(item => {
        avgscoreList.push((item + '').substring(0, 2) * 1)
      })
      const avgscorehistoryList = []
      this.avgscorehistoryList.forEach(item => {
        avgscorehistoryList.push((item + '').substring(0, 2) * 1)
      })
      // 指定图表的配置项和数据
      var myChart = this.$echarts.init(this.$refs.listLineChart)
      var option = {
        title: {
          text: '平均分对比'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: this.legendData
        },
        dataZoom: [
          {
            show: true,
            realtime: true,
            start: 0,
            end: 100
          }
        ],
        xAxis: {
          // axisLabel: {
          //   formatter: function(value) {
          //     console.log(value)
          //     return formatter(value);
          //   }
          // },
          type: 'category',
          boundaryGap: false,
          data: this.legendData
        },
        yAxis: {
          type: 'value',
          max: 100
        },
        series: [
          {
            name: '本次项目平均分',
            type: 'line',
            stack: '总量',
            data: this.avgscoreList
          }
        ]
      }
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option, true)
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
