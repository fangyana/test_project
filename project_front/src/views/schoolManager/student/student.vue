<template>
  <div class="wel-contailer">
    <div class="wel-contailer-head">
      <span class="head-text">学校搜索</span>
      <el-input v-model="search" style="width: 170px;" placeholder="请输入学校名称" />
      <el-button type="primary">搜索</el-button>
      <el-button type="danger" class="fr iconfont icon-lajitong">删除</el-button>
      <el-button type="primary" class="fr iconfont icon-jia" plain>新增</el-button>
    </div>
    <div class="wel-contailer-main">
      <el-table :data="tableData" :height="&quot;100%&quot;" tyle="width: 100%">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" width="60" align="center">
          <template slot-scope="scope">
            <span>{{ scope.$index+1 }}</span>
          </template>
        </el-table-column>
        <el-table-column label="班级名称" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.date }}</span>
          </template>
        </el-table-column>
        <el-table-column label="教师名称" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="详细信息" align="center">
          <template slot-scope="scope">
            <span class="check" @click="handleClickCheck(scope.row)">查看</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="wel-contailer-footer">
      <el-pagination
        :background="&quot;#1E88E5&quot;"
        layout="prev, pager, next"
        :total="1000"
      />
    </div>
  </div>
</template>

<script>
import '@/styles/iconfont/iconfont.css'
export default {
  data() {
    return {
      search: '',
      tableData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }
      ]
    }
  },
  methods: {
    handleClickCheck(obj) {
      console.log(obj)
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
    margin-right: 10px;
    display: inline-block;
    height: $headHeight;
    line-height: $headHeight;
    font-size: $fontSzie;
    vertical-align: middle;
    color: #333;
  }
}
/deep/.el {
  &-input--medium {
    margin-right: 15px;
  }
}
</style>
