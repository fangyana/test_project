<template>
  <div class="wel-contailer">
    <el-row :gutter="20">
      <!-- 查询和其他操作 -->
      <div class="wel-contailer-head">
        <el-input
          v-model="keyword"
          clearable
          class="filter-item"
          size="small"
          style="width: 200px;"
          placeholder="请输入操作人名称"
          @keyup.enter.native="handleFind"
        />
        <el-button class="filter-item" type="primary" icon="el-icon-search" size="mini" @click="handleFind">查找</el-button>
      </div>
      <div class="wel-contailer-main">
        <el-table v-loading="loading" :data="tableData" :height="&quot;100%&quot;" style="width: 100%" size="mini">
          <el-table-column type="selection" />

          <el-table-column label="序号" width="60" align="center">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>

          <el-table-column label="操作人" width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.userName }}</span>
            </template>
          </el-table-column>

          <el-table-column label="类型" width="80" align="center">
            <template slot-scope="scope">
              <template v-if="scope.row.type === 1">
                <span>正常</span>
              </template>
              <template v-if="scope.row.type === 2">
                <span>异常</span>
              </template>
            </template>
          </el-table-column>

          <el-table-column label="描述" width="200" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.description }}</span>
            </template>
          </el-table-column>

          <el-table-column label="IP" width="160" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.requestIp }}</span>
            </template>
          </el-table-column>

          <el-table-column label="请求方式" width="90" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.requestMethod }}</span>
            </template>
          </el-table-column>
          <el-table-column label="请求URL" width="160" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.actionUrl }}</span>
            </template>
          </el-table-column>
          <el-table-column label="请求时间(毫秒)" width="200" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.consumingTime }}</span>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" width="200" align="center">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.startTime) }}</span>
            </template>
          </el-table-column>

          <el-table-column label="操作" fixed="right" header-align="center" min-width="185" align="center">
            <template slot-scope="scope">
              <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!--分页-->
      <div class="wel-contailer-footer">
        <el-pagination
          style="text-align:center;"
          :current-page.sync="currentPage"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="pageSize"
          layout="total, prev, pager, next,sizes, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-row>
  </div>
</template>

<script>
import { getLogList, deleteLog } from '@/api/log'
import { formatData } from '@/utils/webUtils'
import { parseTime } from '@/utils/index'

export default {

  data() {
    return {
      // 日志列表
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0, // 总数量
      keyword: '',
      options: [{
        value: 1,
        label: '正常'
      }, {
        value: 2,
        label: '异常'
      }],
      errorInfo: '',
      dialog: false,
      loading: false
    }
  },
  created() {
    this.logList()
  },
  methods: {
    parseTime,
    logList: function() {
      this.loading = true
      const params = new URLSearchParams()
      params.append('page', this.currentPage)
      params.append('pageSize', this.pageSize)
      params.append('type', 1)
      params.append('userName', this.keyword)
      getLogList(params).then(response => {
        this.loading = false
        this.tableData = response.data.data.records
        this.total = response.data.data.total
      })
    },
    // 搜索
    handleFind: function() {
      this.logList()
    },

    // 查看
    handleView: function(row) {
      this.errorInfo = row.exDetail
      this.dialog = true
    },

    /**
       * 删除日志
       * @param row
       */
    handleDelete: function(row) {
      const that = this
      this.$confirm('此操作将该日志删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deleteLog(row.id).then(response => {
            if (response.data.code === 200) {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
              that.logList()
            } else {
              this.$message({
                type: 'error',
                message: response.data.msg
              })
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    // 换页
    handleCurrentChange: function(val) {
      this.currentPage = val
      this.logList()
    },
    // 换页数
    handleSizeChange(val) {
      this.pageSize = val
      this.logList()
    }
  }
}
</script>

<style scoped="scoped" lang="scss">
  .uploadImgBody :hover {
    border: dashed 1px #00ccff;
  }
  img {
    width: 100px;
    height: 100px;
  }
</style>
