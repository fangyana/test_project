<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import resize from "@/utils/resize";
export default {
  components: {},
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart"
    },
    width: {
      type: String,
      default: "100%"
    },
    height: {
      type: String,
      default: "600px"
    },
    studentName: {
      type: Array,
      default() {
        return [];
      }
    },
    avgHitRate: {
      type: Array,
      default() {
        return [];
      }
    },
    hitRate: {
      type: Array,
      default() {
        return [];
      }
    }
  },
  watch: {
    studentName(arr) {
      this.initChart();
    }
  },
  data() {
    return {
      chart: null
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      const seriesLable = {
        show: true,
        color: "#fff",
        backgroundColor: "auto",
        padding: 5,
        borderRadius: 15
      };
      const axisLine = {
        lineStyle: {
          color: "#E7E7E7"
        }
      };
      const axisLabel = {
        fontSize: 16,
        color:'#333'
      };
      this.chart = this.$echarts.init(this.$el, "macarons");
      this.chart.setOption({
        title: {
          text: "命中率对比",
          top: 20,
          left: "center",
          textStyle: {
            fontSize: 22,
            color: "#333",
            fontWeight: 600
          }
        },
        backgroundColor: "#fff",
        tooltip: {
          trigger: "axis"
        },
        color: ["#f50", "#1E88E5"],
        legend: {
          top: "12%",
          data: ["本次命中率", "平均命中率"],
          icon: "circle",
          textStyle:{
            fontSize:16
          }
        },
        grid: {
          top: "22%",
          left: "3%",
          right: "4%",
          bottom: "6%",
          containLabel: true
        },
        xAxis: {
          type: "category",
          boundaryGap: true,
          data: this.studentName,
          axisLine: axisLine,
          axisLabel: axisLabel
        },
        yAxis: {
          name: "(%)",
          type: "value",
          axisLine: axisLine,
          axisLabel: axisLabel,
          nameTextStyle: {
            fontSize:17,
            color:'#333',
            fontWeight:600
          }
        },
        series: [
          {
            name: "本次命中率",
            type: "line",
            label: seriesLable,
            data: this.hitRate
          },
          {
            name: "平均命中率",
            type: "line",
            label: seriesLable,
            data: this.avgHitRate
          }
        ]
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.chart {
  margin-top: 20px;
}
</style>
