<template>
  <div class="table-con">
    <div class="title">
      <span class="text">本局排名</span>
      <span class="fr">
        查看更多
        <i class="iconfont icon-jiantou_you" />
      </span>
    </div>
    <div>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="排名" width align="center">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column label="玩家" width align="center">
          <template slot-scope="scope">
            <el-tag size="medium">{{ scope.row.name }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="得分" align="center">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      tableData: [
        {
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
  created() {},
  methods: {
    handleEdit(index, row) {
      console.log(index, row)
    },
    handleDelete(index, row) {
      console.log(index, row)
    }
  }
}
</script>
<style lang="scss" scoped>
.table-con {
  background-color: #fff;
  .title {
    height: 60px;
    line-height: 60px;
    .text {
      margin-left: 20px;
      font-size: 22px;
      color: #333;
      font-weight: 600;
    }
    .fr {
      margin: 18px 20px 0 0;
      height: 30px;
      line-height: 30px;
      color: #1e88e5;
      font-weight: 600;
      cursor: pointer;
      .icon-jiantou_you {
        font-size: 12px;
      }
    }
  }
  /deep/.el {
    &-table {
      th {
        background-color: #f8f8f8;
      }
      td{
        padding:10px 0;
      }
    }
  }

}
</style>
