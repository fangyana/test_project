<template>
  <div>
    <div
      id="line-chart"
      ref="lineChart"
      :key="Math.random()"
      style="margin:auto;width:750px; height:385px;"
    />
  </div>
</template>

<script>
export default {
  name: 'LineChart',
  props: {
    title: {
      type: String,
      default: 'title'
    },
    legend: {
      type: String,
      default: 'legend'
    },
    xAxisData: {
      type: Array,
      default() {
        return []
      }
    },
    seriesData: {
      type: Array,
      default() {
        return []
      }
    }
  },
  watch: {
    xAxisData: function(indexVal, oldVal) {
      this.coinConsumeChart()
    }
  },
  methods: {
    coinConsumeChart() {
      // 指定图表的配置项和数据
      var myChart = this.$echarts.init(this.$refs.lineChart)
      var option = {
        title: {
          text: this.title,
          x: 'center', // 主标题居中
          subtextStyle: {
            color: '#2299EE',
            fontSize: 15
          }
        },
        dataZoom: [
          {
            show: true,
            realtime: true,
            start: 0,
            end: 100
          }
        ],
        color: ['#3398DB'],
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: [this.legend],
          top: 30,
          left: 35 // 图示显示在左边
        },
        toolbox: {
          show: true
        },
        calculable: true,
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: this.xAxisData
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: this.legend,
            type: 'line',
            data: this.seriesData
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
