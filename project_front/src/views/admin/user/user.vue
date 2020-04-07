<template>
  <div class="wel-contailer">
    <div class="wel-contailer-head">
      <!-- <el-input
        v-model="filterText"
        placeholder="输入部门名称搜索"
        size="small"
        prefix-icon="el-icon-search"
        style="margin-bottom: 20px;width:200px;"
      />-->
      <el-input
        v-model="keyword"
        clearable
        size="small"
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入用户名"
        @keyup.enter.native="handleFind"
      />
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        size="mini"
        @click="handleFind"
      >查找</el-button>
      <el-button
        class="filter-item"
        type="primary"
        size="mini"
        icon="el-icon-plus"
        @click="handleUser(true)"
      >添加</el-button>
    </div>
    <div class="wel-contailer-main">
      <el-row>
        <el-col class="left-con" :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
          <div class="left">
            <el-tree
              :data="deptData"
              :props="deptTreeProps"
              :expand-on-click-node="false"
              default-expand-all
              @node-click="handleNodeClick"
            />
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :md="20" :lg="20" :xl="20" class="right">
          <div class="top">
            <el-table v-loading="loading" :data="tableData" style="width: 100%" size="mini">
              <el-table-column label="序号" width="60" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.$index + 1 }}</span>
                </template>
              </el-table-column>

              <el-table-column label="账号名称" width="" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.jobName }}</span>
                </template>
              </el-table-column>

              <el-table-column label="用户名" width="" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.username }}</span>
                </template>
              </el-table-column>

              <el-table-column label="所属学校" width="" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.deptName }}</span>
                </template>
              </el-table-column>

              <el-table-column label="创建时间" width="" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.createTime }}</span>
                </template>
              </el-table-column>

              <!-- <el-table-column label="部门 / 岗位" width="200" align="center">
                <template slot-scope="scope">
                  <div>{{ scope.row.deptName }} / {{ scope.row.jobName }}</div>
                </template>
              </el-table-column> -->

              <!-- <el-table-column label="拥有角色" width="200" align="center">
                <template slot-scope="scope">
                  <el-tag
                    v-for="item in scope.row.roleList"
                    :key="item.roleId"
                    type="success"
                    style="margin-right: 5px;"
                  >{{ item.roleName }}</el-tag>
                </template>
              </el-table-column> -->

              <!-- <el-table-column label="状态" width="70" align="center">
                <template slot-scope="scope">
                  <div v-for="item in dicts" :key="item.id">
                    <el-tag
                      v-if="scope.row.lockFlag.toString() === item.value"
                      :type="scope.row.lockFlag ? '' : 'info'"
                    >
                      {{
                      item.label }}
                    </el-tag>
                  </div>
                </template>
              </el-table-column> -->

              <el-table-column label="操作" fixed="right" min-width="120" align="center">
                <template slot-scope="scope">
                  <!--<el-button @click="handRest(scope.row)" type="warning" size="small">重置密码</el-button>-->
                  <span class="edit" @click="handleUser(false,scope.row)">编辑</span>
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <span class='delete' @click="handleDelete(scope.row)">
                    删除
                  </span>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <div class="wel-contailer-footer">
            <el-pagination
              background
              :current-page.sync="currentPage"
              :page-size="pageSize"
              layout="total, prev, pager, next, jumper"
              :total="total"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-col>
      </el-row>
    </div>
    <userDialog
      :roles="roles"
      :jobs="jobs"
      :deptData="deptData"
      :deptTreeProps="deptTreeProps"
      @adminList="adminList"
      ref="userDialog"
    />
  </div>
</template>

<script>
import {
  addUser,
  getUserList,
  editUser,
  deleteUser,
  restPass,
  registerUser
} from "@/api/user";
import { getJobListByDeptId } from "@/api/job";
import { getRoleList } from "@/api/roles";
import { getDept } from "@/api/dept";
import { formatData } from "@/utils/webUtils";
import PopupTreeInput from "@/components/PopupTreeInput";
import initDict from "@/mixins/initDict";
import userDialog from "./components/userDialog";
export default {
  components: {
    PopupTreeInput,
    userDialog
  },
  mixins: [initDict],
  data() {
    return {
      // 用户列表
      tableData: [],
      roles: [], // 角色列表
      jobs: [], // 岗位列表
      keyword: "",
      currentPage: 1,
      pageSize: 10,
      total: 0, // 总数量
      deptId: 0,
      deptData: [],
      deptTreeProps: {
        label: "name",
        children: "children"
      },
      value5: "100",
      filterText: "",
      loading: false,
      jobName: "",
      deptName:''
    };
  },
  created() {
    this.adminList();
    this.findUserRoles();
    this.findDeptTree();
    // 加载数据字典
    this.getDict("user_status");
  },
  methods: {
    // 加载用户角色信息
    findUserRoles() {
      const params = new URLSearchParams();
      params.append("roleName", "");
      getRoleList(params).then(res => {
        this.roles = res.data.data;
      });
    },
    // 加载部门列表
    findDeptTree() {
      getDept().then(res => {
        this.deptData = res.data.data;
      });
    },
    // 部门菜单树选中
    deptTreeCurrentChangeHandle(data) {
      this.dataForm.deptId = data.deptId;
      this.dataForm.deptName = data.name;
      this.getJobs(data.deptId);
    },
    // 加载岗位列表
    getJobs(id) {
      getJobListByDeptId(id).then(res => {
        this.jobs = res.data.data;
      });
    },
    // 加载用户列表
    adminList() {
      this.loading = true;
      const params = new URLSearchParams();
      params.append("current", this.currentPage);
      params.append("size", this.pageSize);
      params.append("deptId", this.deptId);
      params.append("username", this.keyword);
      getUserList(params).then(response => {
        this.loading = false;
        let records = response.data.data.records
        this.tableData = records
        this.total = response.data.data.total;
      });
    },
    handleFind() {
      this.adminList();
    },
    // 密码重置 todo
    handRest(row) {
      const that = this;
      this.$confirm("此操作将会将该用户密码重置, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          restPass(row.userId).then(response => {
            if (response.data.code === 200) {
              that.$message({
                type: "success",
                message: "重置密码成功"
              });
            } else {
              that.$message({
                type: "error",
                message: response.data.msg
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    // 删除用户
    handleDelete(row) {
      const that = this;
      this.$confirm("此操作将该管理员删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          deleteUser(row.userId).then(response => {
            if (response.data.code === 200) {
              this.$message({
                type: "success",
                message: "操作成功"
              });
              that.adminList();
            } else {
              this.$message({
                type: "error",
                message: response.data.msg
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.adminList();
    },
    handleNodeClick(data) {
      this.deptId = data.deptId === 0 ? 0 : data.deptId;
      this.adminList();
    },
    handleUser(bool, obj) {
      this.$refs.userDialog.initDialog(bool, obj);
    },
  }
};
</script>

<style lang='scss' scoped>
.wel-contailer {
  &-main {
    bottom: 20px;
    .left-con {
      height: 100%;
      background: #ecf0f3;
      .left {
        margin-right: 10px;
        height: 100%;
        background-color: #fff;
      }
    }

    .right {
      position: relative;
      height: 100%;
      .top {
        position: absolute;
        top: 0;
        bottom: 50px;
        left: 0;
        right: 0;
      }
    }
  }
  &-footer {
    bottom: 0;
  }
}
@media (min-width: 990px) {
  .left {
    margin-right: 0;
  }
}
/deep/.el {
  $fontSize: 16px;
  &-tree-node {
    &__content {
      height: 40px;
      font-size: $fontSize;
      line-height: 40px;
    }
    &__label {
      font-size: $fontSize;
    }
    &__expand-icon {
      font-size: $fontSize;
    }
  }
}
</style>
