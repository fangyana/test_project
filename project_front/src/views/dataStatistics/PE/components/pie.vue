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
      default: '500px'
    },
    data:{
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
  watch:{
    data(){
      this.initChart()
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
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
      this.chart = this.$echarts.init(this.$el, 'macarons')
      this.chart.setOption({
        title: {
          text: '射门数据',
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
        color: ['#3797D8', '#f50', '#FAD349'],
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'horizontal',
          top: '12%',
          left: 'center',
          data: ['射正', '射偏', '射中门框']
        },
        series: [
          {
            name: '本次射门',
            type: 'pie',
            top: '12%',
            radius: ['35%', '50%'],
            labelLine: {
              show: true,
              length: 20,
              length2: 20
            },
            label: {
              formatter: '{per|{d}%} \n {b|{b}：}{c|{c}} \n{hr|}\n {a|{a}}{abg|}',
              borderColor: 'auto',
              padding: [5, 0],
              borderWidth: 1,
              distanceToLabelLine: 5,
              borderRadius: 10,
              labelLine: 10,
              position: 'outside',
              rich: {
                a: {
                  color: '#999',
                  lineHeight: 22,
                  fontSize: '16',
                  align: 'center'
                },
                hr: {
                  borderColor: 'auto',
                  width: '100%',
                  borderWidth: 0.5,
                  height: 0.5
                },
                b: {
                  padding: [5, 10],
                  fontSize: 16
                },
                c: {
                  fontSize: 16
                },
                per: {
                  align: 'center',
                  color: '#fff',
                  backgroundColor: 'auto',
                  fontSize: '14',
                  padding: [4, 4],
                  borderRadius: 10
                }
              }
            },
            data: this.data
          }
        ]
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.chart{
}
</style>
