<template>
  <div v-if="isShow" class="wel-contailer">
    <div class="wel-contailer-head">
      <span class="head-text">姓名：</span>
      <span class="head-text">日期：</span>
      <el-button type="primary" class="fr" @click="goback">返回</el-button>
    </div>
    <div class="wel-contailer-main">
      <el-table :data="tableData" :height="&quot;100%&quot;" tyle="width: 100%">
        <!-- <el-table-column type="selection" width="55" align="center" /> -->
        <!-- <el-table-column label="序号" width="60" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index+1 }}</span>
          </template>
        </el-table-column> -->
        <el-table-column label="射门次数" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.className }}</span>
          </template>
        </el-table-column>
        <el-table-column label="消耗时间（秒）" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.teacherName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="消耗时间（秒）" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否进门" align="center">
          <template slot-scope="scope">
            <span class="check" @click="handleClickCheck(scope.row)">查看</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="wel-contailer-footer">
      <el-pagination
        :current-page="formData.current"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="formData.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
import { getAccuracyModelDetail } from '@/api/trainResults'
export default {
  components: {},
  data() {
    return {
      isShow: false,
      row: {},
      tableData:[],
      formData: {
        current: 1, // int	true	普通参数		当前页
        size: 10, // int	true	普通参数		每页显示条数
        deptId:'',	//int	false	普通参数		学校/机构id
        className:'',	//string	false	普通参数		上课班级名
        teacherName:''	//string	false	普通参数		上课老师名
      },
      pagination: {
        total: 0, // 总条数
        pages: 1 // 总页数
      }
    }
  },
  created() {},
  methods: {
    initView(row) {
      this.isShow = true
    },
    handleSizeChange(){

    },
    handleCurrentChange(){

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
}
.wel-contailer-head {
  font-size: 0;
  .head-text {
    margin:0 10px;
    display: inline-block;
    height: $headHeight;
    line-height: $headHeight;
    font-size: $fontSzie;
    vertical-align: middle;
    color: #333;
  }
}
</style>
