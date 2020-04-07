<template>
  <div>
    <div id="bar-chart" style="margin:auto;width:750px; height:450px;" />
  </div>
</template>

<script>
export default {
  name: 'BarChart',
  props: {
    title: {
      type: String,
      default: 'title'
    },
    legend: {
      type: String,
      default: 'legend'
    }
  },
  mounted() {
    this.coinConsumeChart()
  },
  methods: {
    coinConsumeChart() {
    // 指定图表的配置项和数据
      var myChart = this.$echarts.init(document.getElementById('bar-chart'))
      var option = {
        title: {
          text: this.title,
          x: 'center',	// 主标题居中
          subtextStyle: {
            color: '#2299EE',
            fontSize: 15
          }
        },
        color: ['#3398DB'],
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: [this.legend],
          top: 30,
          left: 35	// 图示显示在左边
        },
        toolbox: {
          show: true,
          feature: {
          }
        },
        calculable: true,
        dataZoom: {
          show: true,
          realtime: true,
          startValue: this.startTime, 	// 拖拽条开始时间
          endValue: this.endTime			// 拖拽条结束时间
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: true,
            // data: ['2018-07-26', '2018-07-27', '2018-07-28', '2018-07-29', '2018-07-30', '2018-07-31', '2018-08-01', '2018-08-02', '2018-08-03', '2018-08-04', '2018-08-05', '2018-08-06', '2018-08-07', '2018-08-08', '2018-08-09', '2018-08-10', '2018-08-11', '2018-08-12', '2018-08-13', '2018-08-14']
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
            type: 'bar',
            // data: [100, 10, 20, 30, 60, 800, 700, 300, 1000, 100, 100, 360, 900, 180, 120, 150, 600, 140, 1200, 800]
            data: this.seriesData
          }
        ]
      }
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option)
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
