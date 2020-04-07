<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import resize from '@/utils/resize'
export default {
  components: {},
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '550px'
    },
    hitRateData:{
      type:Array,
      default(){
        return []
      }
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    hitRateData(){
      this.initChart()
    }
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      const axisLine = {
        lineStyle: {
          color: "#E7E7E7"
        }
      };
      const axisLabel = {
        fontSize: 16,
        color:'#333'
      };
      this.chart = this.$echarts.init(this.$el, 'macarons')
      this.chart.setOption({
        title: {
          text: '射门速度',
          color: '#333',
          top: 20,
          left: 'center',
          textStyle: {
            fontSize: 22,
            color:'#333',
            fontWeight:600
          }
        },
        backgroundColor: '#fff',
        color: ['#03A9F4', '#FAD349', '#f50'],
        legend: {
          top: '12%',
          icon: 'circle',
          textStyle:{
            fontSize:16
          }
        },
        tooltip: {},
        grid: {
          top: '20%',
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        dataset: {
          source:this.hitRateData
        },
        xAxis: { 
          type: 'category',
          axisLine: axisLine,
          axisLabel:axisLabel
        },
        yAxis: {
          name: '（km/）',
          axisLine: axisLine,
          axisLabel:axisLabel,
          nameTextStyle: {
            fontSize:17,
            color:'#333',
            fontWeight:600
          }
        },
        series: [{ type: 'bar' }, { type: 'bar' }, { type: 'bar' }]
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.chart{
}
</style>
