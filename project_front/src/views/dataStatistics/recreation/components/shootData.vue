<template>
  <div class="shoot">
    <el-row>
      <el-col :span="12">
        <div class="title">本局射门数据</div>
        <ul class="list">
          <li class="item">
            <span class="text">命中一环：30</span>
            <span class="color">45%</span>
          </li>
          <li class="item">
            <span class="text">命中二环：14</span>
            <span class="color">45%</span>
          </li>
          <li class="item">
            <span class="text">命中三环：18</span>
            <span class="color">45%</span>
          </li>
          <li class="item">
            <span class="text">未中：26</span>
            <span class="color">45%</span>
          </li>
        </ul>
      </el-col>
      <el-col :span="12">
        <div ref="chart" class="chart" :style="{height:height,width:width}" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import resize from '@/utils/resize'
export default {
  components: {},
  mixins: [resize],
  props: {
    height: {
      type: String,
      default: '100%'
    },
    width: {
      type: String,
      default: '100%'
    }
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  methods: {
    initChart() {
      this.chart = this.$echarts.init(this.$refs.chart, 'macarons')
      this.chart.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'horizontal',
          top: '5%',
          left: 'center',
          data: ['命中一环', '命中二环', '命中三环', '未中'],
          textStyle: {
            colo: '#333',
            fontSize: 14
          }
        },
        color: ['#136CCF', '#16CA2F', '#963726', '#CBA59E'],
        series: [
          {
            name: '射门数据',
            type: 'pie',
            radius: ['40%', '60%'],
            label: {
              formatter: '{d}%',
              position: 'inner',
              fontSize: 18
            },
            data: [
              { value: 335, name: '命中一环' },
              { value: 310, name: '命中二环' },
              { value: 234, name: '命中三环' },
              { value: 1048, name: '未中' }
            ]
          }
        ]
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.shoot {
  height:100%;
  background-color: #fff;
  .title {
    margin-bottom:20px;
    height: 60px;
    line-height: 60px;
    color: #333;
    font-weight: 600;
    font-size: 22px;
    text-indent: 20px;
  }
  .list {
    > .item {
      height: 60px;
      color: #666;
      font-size:18px;
      text-indent: 80px;
      .color{
        margin-left:20px;
        color:#16CA2F;
        font-weight:600;
      }
    }
  }
}
</style>
