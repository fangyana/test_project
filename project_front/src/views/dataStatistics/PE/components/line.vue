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
      default: "500px"
    },
    trainTime:{
      type:Array,
      default(){
        return []
      }
    },
    speed:{
      type:Array,
      default(){
        return []
      }
    }
  },
  data() {
    return {
      chart: null,
      time: ["11/12", "11/13", "11/14", "11/15", "11/16", "11/17", "11/18"],
      data: [980, 1000, 760, 560, 800, 700, 20],
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
      const axisLabel = {
        show: true,
        color: "#333",
        fontSize:14
      };
      this.chart = this.$echarts.init(this.$el, "macarons");
      this.chart.setOption({
        title: {
          text: "射门速度",
          top: 20,
          textStyle: {
            fontSize: 22,
            color: "#000",
            fontWeight: 600
          },
          left: "center"
        },
        backgroundColor: "#fff",
        legend: {
          top: 10,
          left: "center",
          data: ["0-100", "100-120", "120-140", "140-160", "160"]
        },
        tooltip: {
          formatter: "日期：{c} <br/>时长： {b}",
          trigger: "axis",
          backgroundColor: "#fff",
          padding: 10,
          borderColor: "#2C6BED",
          borderWidth: 1,
          textStyle: {
            color: "#2C6BED",
            fontSize: "14",
            fontWeight: 600
          }
        },
        grid: {
          top: "25%",
          left: "10%",
          right: "5%"
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          name: "日期",
          width: "1500",
          nameLocation: "start",
          nameTextStyle: {
            color: "#666",
            fontSize: "16",
            padding: [80, -45, 0, 0]
          },
          data: this.time,
          axisLine: {
            lineStyle: {
              color: "#999"
            }
          },
          axisLabel: axisLabel,
          axisTick: {
            show: false
          }
        },
        yAxis: {
          name: "(km/h)",
          max: 1000,
          axisLine: {
            show: false
          },
          nameTextStyle: {
            color: "#333",
            fontSize: "16"
          },
          axisTick: {
            show: false
          },
          // splitLine: {
          //   show: false
          // },
          axisLabel: axisLabel
        },
        series: {
          name: "射门速度",
          type: "line",
          smooth: true,
          lineStyle: {
            color: "#1E88E5"
          },
          itemStyle: {
            color: "#1E88E5"
          },
          areaStyle: {
            color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0.5,
                color: "#2C6BED"
              },
              {
                offset: 1,
                color: "#fff"
              }
            ])
          },
          data: this.data
        }
      });
    }
  }
};
</script>
