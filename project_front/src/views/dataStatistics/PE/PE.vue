<template>
  <div class="wel-contailer wap-centainer">
    <div class="head">
      <el-row>
        <el-col :xs="24" :sm="8" :md="8" :lg="5" :xl="5">
          <span class="head-text">日期</span>
          <el-date-picker
            v-model="formData.queryTime"
            type="date"
            style="width:180px;"
            placeholder="选择日期"
          />
        </el-col>
        <el-col :xs="24" :sm="16" :md="16" :lg="19" :xl="19">
          <span class="head-text">班级</span>
          <el-select v-model="formData.classId" style="width:180px;" placeholder="请选择类型">
            <el-option
              :label="item.className"
              :value="item.classId"
              :key="index"
              v-for="(item,index) in classList"
            />
          </el-select>
          <el-button type="primary" class="fr" @click="getData">查询</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="main">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <shootChart :data='shootData' :height="height"/>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <hitRateChart :height="height"/>
        </el-col>
        <el-col :span="24">
          <div class="goal-con">
            <div class="title">热门球位</div>
            <goal :itemNumShow="true" :width="width" :height="height" />
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { getList } from "@/api/class";
import { getREModelTrainData } from "@/api/trainResults";
import { handleDate } from '@/utils/handleTime'
import shootChart from "./components/pie";
import hitRateChart from "./components/line";
import goal from "@/components/goal";
import "@/styles/goal.scss";
import "@/styles/iconfont/iconfont.css";
export default {
  components: {
    shootChart,
    hitRateChart,
    goal
  },
  data() {
    return {
      width: "80%",
      height: "600px",
      formData: {
        queryTime: "",
        classId: ""
      },
      classList: [],
      shootData: [
        { value: 10, name: "射正" },
        { value: 10, name: "射偏" },
        { value: 10, name: "射中门框" }
      ],
      hitRateData:{
        trainTime:[],
        speed:[],
      },
      speedData:{
        maxSpeed:'',	//double	true	最大速度
        minSpeed:'',	//double	true	最小速度
        avgSpeed:''  //double	true	平均速度
      },
      positionList:[]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleDate,
    getData() {
      const formData = new FormData();
      this.formData.queryTime = handleDate(this.formData.queryTime);
      for (const key in this.formData) {
        formData.append(key, this.formData[key]);
      }
      getREModelTrainData(formData).then(res => {
        const data = res.data.data
        const shootData = []
        const speedList = data.speedList
        const trainTime = []
        const speed = []
        shootData.push({ value: data.shotIntoNum, name: "射正" })
        shootData.push({ value: data.shotWideNum, name: "射偏" })
        shootData.push({ value: data.shotTheWoodworkNum, name: "射中门框" })
        this.shootData = shootData
        speedList.map(item => {
          trainTime.push(item.trainTime)
          speed.push(item.speed)
        })
        this.hitRateData.trainTime = trainTime
        this.hitRateData.speed = speed
        for(let key in this.speedData){
          this.speedData[key] = data[key]
        }
        this.positionList = data.positionList
      });
    },
    async getList() {
      await getList().then(res => {
        this.classList = res.data.data;
      });
    },
    handleSelectionChange(val) {
      console.log(val);
    },
    handleSizeChange() {},
    handleCurrentChange() {},
    handleAdd(obj) {
      this.$refs.add.initDialog(obj);
    },
    handleAdd(obj) {}
  }
};
</script>
<style lang="scss" scoped>
$fontSzie: 18px;
$headHeight: 38px;
@media screen and (max-width: 1200px) {
  .main {
    margin: 0 10px;
    bottom: 10px;
  }
  .head {
    margin: 10px;
    padding: 11px 10px;
    background-color: #fff;
    font-size: 0;
    &-text {
      margin: 0 10px;
      display: inline-block;
      height: $headHeight;
      line-height: $headHeight;
      font-size: $fontSzie;
      color: #333;
    }
  }
}
@media (min-width: 1200px) and (max-width: 1920px) {
  .head {
    margin: 20px;
    padding: 21px 20px;
    background-color: #fff;
    font-size: 0;
    &-text {
      margin: 0 10px;
      display: inline-block;
      height: $headHeight;
      line-height: $headHeight;
      font-size: $fontSzie;
      color: #333;
    }
  }
  .main {
    margin: 0 20px;
    bottom: 20px;
  }
}
</style>
