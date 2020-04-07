<template>
  <div class="wel-contailer">
    <div class="wel-contailer-head">
      <span class="head-text">学校名称: XX市第一高级中学</span>
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
            <span>{{ scope.row.className }}</span>
          </template>
        </el-table-column>
        <el-table-column label="教师名称" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.teacherName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
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
        :current-page="formData.current"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="formData.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    <classDialog />
  </div>
</template>

<script>
import { getClassList } from "@/api/class";
import classDialog from './components/classDialog'
import "@/styles/iconfont/iconfont.css";
export default {
  data() {
    return {
      search: "",
      tableData: [],
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
    };
  },
  created() {
    this.getClassList();
  },
  methods: {
    // 获取班级列表
    getClassList() {
      const params = new URLSearchParams()
      for(let key in this.formData){
        params.append(key,this.formData[key])
      }
      getClassList(params).then(res => {
        console.log(res);
      });
    },



    handleSizeChange(){

    },
    handleCurrentChange(){

    },
    handleClickCheck(obj) {
      console.log(obj);
    }
  },
  components:{
    classDialog
  }
};
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
