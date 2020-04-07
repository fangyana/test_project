<template>
  <div v-if="isShow" class="wel-contailer">
    <div class="wel-contailer-head">
      <span class="head-text">日期：</span>
      <span class="head-text">2019/08/05~2019/08/07</span>
      <span class="head-text">姓名:</span>
      <span class="head-text">{{userData.studentName}}</span>
      <el-button type="primary" class="fr" @click="goback">返回</el-button>
    </div>
    <div class="wel-contailer-main">
      <div class="data">
        <el-row>
          <el-col :xs="24" :sm="6" :md="8" :lg="9" :xl="4">
            <span class="circle"></span><span>进球数：1个</span>
          </el-col>
          <el-col :xs="24" :sm="6" :md="8" :lg="9" :xl="4">
            <span class="circle"></span><span>进球数：1个</span>
          </el-col>
          <el-col :xs="24" :sm="6" :md="8" :lg="9" :xl="4">
            <span class="circle"></span><span>进球数：1个</span>
          </el-col>
        </el-row>
        <div class="text">
          本次训练结果为：共射门30次，共进球15次，其中命中指定区域5次，命中率为16.7%，本次射门的平均球速为70km/h，最高球速为80km/h，最低球速为65km/h
        </div>
      </div>

      <div class="goal-con">
        <goal :itemNumShow="true" :width="width" :height="height" />
      </div>
    </div>
  </div>
</template>

<script>
import { getAccuracyModelDetail } from "@/api/trainResults";
import goal from "@/components/goal";
import "@/styles/goal.scss";
export default {
  components: { goal },
  data() {
    return {
      width: "80%",
      height: "600px",
      isShow: false,
      userData: {
        studentId: 5,
        classId: 4,
        studentName: "小宇",
        shotNum: 3,
        hitNum: 0,
        hitRate: 0,
        rank: 2
      }
    };
  },
  created() {},
  methods: {
    initView(row) {
      this.isShow = true;
    },
    goback() {
      this.isShow = false;
    }
  }
};
</script>
<style lang="scss" scoped>
$fontSzie: 18px;
$headHeight: 38px;
.wel-contailer {
  z-index: 200;
  &-head {
    margin: 15px 0 0;
    font-size: 0;
    .head-text {
      margin: 0 10px;
      display: inline-block;
      height: $headHeight;
      line-height: $headHeight;
      font-size: $fontSzie;
      vertical-align: middle;
      color: #333;
    }
  }
  &-main {
    top: 95px;
    bottom: 20px;
    .data{
      padding:0 30px;
      .circle{
        display: inline-block;
        margin-right:20px;
        width:20px;
        height:20px;
        border-radius:50%;
        background: #ccc;
        vertical-align: middle;
      }
      .text{
        margin:30px 0;
      }
    }
  }
}
</style>
