<template>
  <div v-if="isShow" class="wel-contailer">
    <div class="head">
      <span class="head-text">日期：{{ row.time }}</span>
      <el-button type="primary" class="fr" @click="goback">返回</el-button>
    </div>
    <div v-loading="loading" class="main">
      <div class="rank-con">
        <el-row>
          <el-col :span="12">
            <div class="left">
              <rankTable />
            </div>
          </el-col>
          <el-col :span="12">
            <div class="right">
              <shootData />
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="goal-con">
        <div class="title">本局射门进球热力图</div>
        <goal :item-num-show="false" :width="width" :height="height" />
      </div>
    </div>
  </div>
</template>

<script>
import { getENTModelDetail } from '@/api/trainResults'
import goal from '@/components/goal'
import rankTable from './rankTable'
import shootData from './shootData'
import '@/styles/goal.scss'
export default {
  components: {
    goal,
    rankTable,
    shootData
  },
  data() {
    return {
      width: '80%',
      height: '500px',
      isShow: false,
      row: {},
      loading: false
    }
  },
  created() {},
  methods: {
    initView(row) {
      this.row = {}
      this.row = row
      this.getENTModelDetail()
      this.isShow = true
    },
    getENTModelDetail() {
      this.loading = true
      getENTModelDetail(this.row.configId).then(res => {
        console.log(res)
        this.loading = false
      })
    },
    goback() {
      this.isShow = false
    }
  }
}
</script>
<style lang="scss" scoped>
$fontSzie: 18px;
$headHeight: 38px;
.wel-contailer {
  z-index: 1000;
  overflow: auto;
  .main {
    margin-bottom: 20px;
    .rank-con {
      height: 352px;
      /deep/.el {
        &-col {
          height: 100%;
        }
      }
      .left{
        margin-right:10px;
      }
      .right{
        margin-left:10px;
        height:100%;
      }
    }
    .goal-con {
      padding-bottom: 20px;
    }
  }
}
.title {
  color: #333;
  font-weight: 600;
}
</style>
