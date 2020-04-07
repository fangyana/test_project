<template>
  <div class="wel-contailer">
    <div class="wel-contailer-head">
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
            <el-option label="机构" value="1" />
            <el-option label="学校" value="2" />
          </el-select>
        </el-col>
        <el-col :xs="24" :sm="8" :md="8" :lg="14" :xl="14">
          <span class="head-text">姓名</span>
          <el-input v-model="formData.studentName" style="width:180px;" placeholder="请输入姓名" />
          <el-button type="primary" class="fr" @click="handleCheck">查询</el-button>
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
        @selection-change="handleSelectionChange"
      >
        <el-table-column label="学生姓名" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.studentName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="总射门次数" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.shotNum }}</span>
          </template>
        </el-table-column>
        <el-table-column label="命中目标数（次）" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.hitNum }}</span>
          </template>
        </el-table-column>
        <el-table-column label="命中率（%）" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.hitRate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="排名" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.rank }}</span>
          </template>
        </el-table-column>
        <el-table-column label="详情" align="center">
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
import { getAccuracyModelPage } from '@/api/trainResults'
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
        queryTime: '', // object	false	普通参数		查询日期 date ,格式yyyy-MM-dd
        classId: '', // int	false	普通参数		上课班级id
        studentName: '' //	string	false	普通参数		学员名
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
      await getAccuracyModelPage(this.formData).then(res => {
        this.tableData = res.data.data.records
        this.pagination.total = res.data.total
        this.pagination.pages = res.data.pages
        console.log(res)
        this.loading = false
      })
    },
    handleSelectionChange(val) {},
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
