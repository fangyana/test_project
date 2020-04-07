<template>
  <div style="margin:auto;">
    <div
      id="stacked-bar-chart"
      ref="stackedBarChart"
      style="margin:auto;width:750px; height:450px;"
    />
  </div>
</template>

<script>
import { formatter } from '@/utils/handleTime'
export default {
  name: 'StackedBarChart',
  props: {
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
    },
    legendData: {
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
      var myChart = this.$echarts.init(this.$refs.stackedBarChart)
      var option = {
        title: {
          text: '各项目优良率'
        },
        dataZoom: [
          {
            show: true,
            realtime: true,
            start: 0,
            end: 100
          }
        ],
        backgroundColor: '#ffffff',
        color: ['#c23531', '#2f4554', '#61a0a8', '#d48265'],
        legend: {
          data: this.legendData
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: this.xAxisData,
            axisLabel: {
              formatter: function(value) {
                console.log(value)
                return formatter(value)
              }
            },
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            name: '百分比（%）',
            type: 'value',
            max: 100,
            axisLabel: {
              show: true,
              interval: 'auto'
            },
            show: true
          }
        ],
        series: [
          {
            name: this.legendData[0],
            type: 'bar',
            stack: '总量',
            barWidth: '20%',
            data: this.seriesData[0]
          },
          {
            name: this.legendData[1],
            type: 'bar',
            stack: '总量',
            barWidth: '20%',
            data: this.seriesData[1]
          },
          {
            name: this.legendData[2],
            type: 'bar',
            stack: '总量',
            barWidth: '20%',
            data: this.seriesData[2]
          },
          {
            name: this.legendData[3],
            type: 'bar',
            stack: '总量',
            barWidth: '20%',
            data: this.seriesData[3]
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
