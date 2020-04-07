<template>
  <div class="wel-contailer">
    <div class="wel-contailer-head">
      
      <el-button type="primary" class="fr iconfont icon-jia" plain @click="handleAdd()">新增</el-button>
    </div>
    <div class="wel-contailer-main">
      <!--表格树内容栏-->
      <tree-table
        :height='"100%"'
        :key="key"
        :default-expand-all="defaultExpandAll"
        :data="popupTreeData"
        :columns="columns"
        size="small"
      >
        <template slot="createTime" slot-scope="{scope}">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
        <template slot="operation" slot-scope="{scope}">
          <span class="check" @click="handleCheck(scope.row)">查看</span>
        </template>
        <template slot="handle" slot-scope="{scope}">
          <el-button type="danger">删除</el-button>
        </template>
      </tree-table>
    </div>

    <!-- 新增修改界面 -->
    <add-User :popupTreeData='popupTreeData' ref="addDom" />
  </div>
</template>

<script>
import PopupTreeInput from '@/components/PopupTreeInput'
import treeTable from '@/components/TreeTable'
import { saveDept, getDept, updateDept, deleteDept, getSchoolPage } from '@/api/dept'
import { parseTime } from '@/utils/index'
import addUser from './components/add'
export default {
  components: { PopupTreeInput, treeTable, addUser },
  data() {
    return {
      formLabelWidth: '120px',
      loading: false,
      dialogVisible: false,
      defaultExpandAll: true,
      key: 1,
      columns: [
        {
          label: '序号',
          key: '_id',
          width: 200,
          align: 'left',
          expand: true
        },
        {
          label: '机构名称',
          key: 'name'
        },
        {
          label: '上级机构',
          key: 'parentName',
          align: 'center'
        },
        {
          label: '创建时间',
          key: 'createTime',
          align: 'center'
        },
        {
          label: '学生信息',
          key: 'operation'
        },
        {
          label: '操作',
          key: 'handle'
        }
      ],
      popupTreeData: []
    }
  },
  created() {
    this.findTreeData()
  },
  methods: {
    parseTime,
    handleFind: function() {
      this.findTreeData()
    },
    handleAdd() {
      this.$refs.addDom.initDialog()
    },
    // 显示新增界面
    handleCheck(row) {
      this.$refs.addDom.initDialog(row)
    },
    // 获取数据
    findTreeData: function() {
      this.loading = true
      getDept().then(res => {
        this.popupTreeData = res.data.data
        this.loading = false
      })
    },
  }
}
</script>

<style lang="scss" scoped>
$fontSzie: 18px;
$headHeight: 38px;
.wel-contailer {
  &-head {
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
  &-main {
    bottom: 20px !important;
    /deep/tbody {
      .is-left {
        .cell {
          text-indent: 45px;
        }
      }
    }
  }
}
.lable {
  width: 100%;
  text-align: center;
}
/deep/.el {
  &-input--medium {
    margin-right: 15px;
  }
}
</style>
