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
        <el-col :xs="24" :sm="8" :md="8" :lg="5" :xl="5">
          <span class="head-text">班级</span>
          <el-select v-model="formData.classId" style="width:180px;" placeholder="请选择类型">
            <el-option
              :label="item.className"
              :value="item.classId"
              :key="index"
              v-for="(item,index) in classList"
            />
          </el-select>
        </el-col>
        <el-col :xs="24" :sm="8" :md="8" :lg="14" :xl="14">
          <span class="head-text">姓名</span>
          <el-input v-model="formData.studentName" style="width:180px;" placeholder="请输入姓名" />
          <el-button type="primary" class="fr" @click="getFreeModelTrainData">查询</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="main">
      <shootChart :hitRateData='hitRateData'/>
      <shootTable :resultsSpeedList="resultsSpeedList" />
      <hitRateChart
        :studentName="hitRateList.studentName"
        :avgHitRate="hitRateList.avgHitRate"
        :hitRate="hitRateList.hitRate"
      />
      <div class="goal-con">
        <div class="title">热门球位</div>
        <goal :itemNumShow="true" :width="width" :height="height" />
      </div>
    </div>
  </div>
</template>

<script>
import { getFreeModelTrainData } from "@/api/trainResults";
import { getList } from "@/api/class";
import shootChart from "./components/bar";
import shootTable from "./components/shootTable";
import hitRateChart from "./components/line";
import goal from "@/components/goal";
import "@/styles/iconfont/iconfont.css";
import "@/styles/goal.scss";
export default {
  components: {
    shootChart,
    shootTable,
    hitRateChart,
    goal
  },
  data() {
    return {
      width: "80%",
      height: "600px",
      formData: {
        queryTime: "", //object	true	普通参数		查询日期 date ,格式yyyy-MM-dd
        classId: "", //int	true	普通参数		上课班级id
        studentName: "" //string	true	普通参数		学员名
      },
      classList: [],
      resultsSpeedList: [
        {
          studentName: "01",
          avgSpeed: 150,
          maxSpeed: 180,
          minSpeed: 90
        },
        {
          studentName: "02",
          avgSpeed: 150,
          maxSpeed: 180,
          minSpeed: 90
        },
        {
          studentName: "03",
          avgSpeed: 150,
          maxSpeed: 180,
          minSpeed: 90
        },
        {
          studentName: "04",
          avgSpeed: 150,
          maxSpeed: 180,
          minSpeed: 90
        }
      ],
      hitRateList: {
        studentName: [],
        avgHitRate: [],
        hitRate: []
      },
      hitRateData:[],
      positionList:[]
    };
  },
  created() {
    this.getFreeModelTrainData();
    this.getList();
  },
  methods: {
    async getFreeModelTrainData() {
      const parms = new URLSearchParams();
      await getFreeModelTrainData(parms).then(res => {
        const defaultItem = ['product', '平均球速', '最高球速', '最低球速']
        const hitRateData = []
        const data = res.data.data;
        const studentName = [];
        const avgHitRate = [];
        const hitRate = [];
        this.resultsSpeedList = data.resultsSpeedList;
        hitRateData.push(defaultItem)
        this.resultsSpeedList.map(item => {
          const arr = []
          for(let key in item){
            arr.push(item[key])
          }
          hitRateData.push(arr)
        })
        this.hitRateData = hitRateData
        data.hitRateList.map(item => {
          studentName.push(item.studentName);
          avgHitRate.push(item.avgHitRate);
          hitRate.push(item.hitRate);
        });
        this.hitRateList.studentName = studentName;
        this.hitRateList.avgHitRate = avgHitRate;
        this.hitRateList.hitRate = hitRate;
        this.positionList = data.positionList
      });
    },
    async getList() {
      await getList().then(res => {
        this.classList = res.data.data;
      });
    },
  }
};
</script>