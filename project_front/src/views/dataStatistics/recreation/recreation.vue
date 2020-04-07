<template>
  <div class="wel-contailer">
    <div class="wel-contailer-head">
      <el-row>
        <el-col :xs="16" :sm="10" :md="8" :lg="5" :xl="5">
          <span class="head-text">日期</span>
          <el-date-picker
            v-model="formData.queryTime"
            type="date"
            style="width:180px;"
            placeholder="选择日期"
          />
        </el-col>
        <el-col :xs="8" :sm="14" :md="16" :lg="19" :xl="19">
          <el-button type="primary" class="fr" @click="getData">查询</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="wel-contailer-main">
      <el-table
        ref="multipleTable"
        v-loading="loading"
        tooltip-effect="dark"
        :data="tableData"
        height="100%"
        style="width: 100%"
      >
        <el-table-column label="日期" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.time }}</span>
          </template>
        </el-table-column>
        <el-table-column label="GAME" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.gameName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="射门人数" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.personNum }}</span>
          </template>
        </el-table-column>
        <el-table-column label="整体命中率（%）" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.hitRate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="最高得分" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.maxScore }}</span>
          </template>
        </el-table-column>
        <el-table-column label="最高得分" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.rank }}</span>
          </template>
        </el-table-column>
        <el-table-column label="最低得分" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.minScore }}</span>
          </template>
        </el-table-column>
        <el-table-column label="平均得分" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.avgScore }}</span>
          </template>
        </el-table-column>
        <el-table-column label="平均得分" align="center">
          <template slot-scope="scope">
            <span class="check" @click="handleCheck(scope.row)">查看</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="wel-contailer-footer">
      <el-pagination
        background
        :current-page="formData.current"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="formData.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    <transition name="fade-transform" mode="out-in">
      <user-Data ref="userData" />
    </transition>
  </div>
</template>

<script>
import { getENTModelPage } from '@/api/trainResults'
import userData from './components/userData'
import '@/styles/iconfont/iconfont.css'
export default {
  components: {
    userData
  },
  data() {
    return {
      formData: {
        current: 1, // int	true	普通参数		当前页
        size: 10, // int	true	普通参数		每页显示条数
        queryTime: '' // object	false	普通参数		查询日期 date ,格式yyyy-MM-dd
      },
      pagination: {
        total: 0, // 总条数
        pages: 1 // 总页数
      },
      loading: false,
      tableData: []
    }
  },
  created() {
    this.getData()
  },
  methods: {
    async getData() {
      this.loading = true
      await getENTModelPage(this.formData).then(res => {
        this.tableData = res.data.data.records
        this.pagination.total = res.data.total
        this.pagination.pages = res.data.pages
        this.loading = false
      })
    },
    handleSizeChange(val) {
      this.formData.size = val
      this.getData()
    },
    handleCurrentChange(val) {
      this.formData.current = val
      this.getData()
    },
    handleCheck(obj) {
      this.$refs.userData.initView(obj)
    }
  }
}
</script>
<style lang="scss" scoped>
$fontSzie: 18px;
$headHeight: 38px;
.wel-contailer-head {
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
</style>
