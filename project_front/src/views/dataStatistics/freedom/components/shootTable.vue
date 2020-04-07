<template>
  <div class='tab-con'>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column label="" width="150" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column 
        :label="item.studentName" 
        :key='index'
        align="center" 
        v-for='(item,index) in resultsSpeedList'>
        <template slot-scope="scope">
          <span>{{ resultsSpeedList[index][scope.row.key] }}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  props:{
    resultsSpeedList:{
      type:Array,
      default(){
        return []
      }
    }
  },
  data() {
    return {
      tableData: [
        {
          name: "平均球速（km/）",
          key:'avgSpeed'
        },
        {
          name: "最高球速（km/）",
          key:'maxSpeed'
        },
        {
          name: "最低球速（km/）",
          key:'minSpeed'
        },
      ],
      
    };
  }
};
</script>
<style lang="scss" scoped>
.tab-con{
  padding:15px 6% 40px;
  background: #fff;
}
/deep/.el-table{
  // &__footer-wrapper,&__header-wrapper{
  //   display: none;
  // }
  .el-table_1_column_1  {
    color:#333;
    font-size:16px;
    font-weight:600;
  }
}
</style>