<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="operation?'新增用户':'编辑用户'" :visible.sync="dialogVisible" center>
    <el-form
      :model="formData"
      :rules="rules2"
      label-width="80px"
      size="small"
      label-position="right"
    >
      <el-form-item label="用户名" :label-width="formLabelWidth" prop="username" required>
        <el-input v-model="formData.username" auto-complete="off" placeholder="请输入用户名" />
      </el-form-item>

      <el-form-item label="密码" :label-width="formLabelWidth" prop="password" required>
        <el-input
          type="password"
          v-model="formData.password"
          auto-complete="off"
          placeholder="请输入密码"
        />
      </el-form-item>

      <el-form-item label="所属机构" :label-width="formLabelWidth">
        <popup-tree-input
          :data="deptData"
          :props="deptTreeProps"
          :prop="deptName"
          :node-key="''+formData.deptId"
          :current-change-handle="deptTreeCurrentChangeHandle"
        />
      </el-form-item>

      <el-form-item label="手机号" :label-width="formLabelWidth" prop="phone" required>
        <el-input v-model="formData.phone" auto-complete="off" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item label="角色" prop="userRoles" :label-width="formLabelWidth">
        <el-select v-model="formData.roleList" multiple placeholder="请选择" style="width: 100%;">
          <el-option
            v-for="item in roles"
            :key="item.id"
            :label="item.roleName"
            :value="item.roleId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="姓名" :label-width="formLabelWidth" prop="realName">
        <el-input v-model="formData.realName" auto-complete="off" placeholder="请输入姓名" />
      </el-form-item>
      <el-form-item label="岗位" :label-width="formLabelWidth">
        <el-select v-model="formData.jobId" placeholder="请先选择所属机构" style="width: 100%">
          <el-option
            v-for="(item,index) in jobs"
            :key="''+ index"
            :label="item.jobName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email" required>
        <el-input v-model="formData.email" auto-complete="off" placeholder="请输入邮箱" />
      </el-form-item>
      <el-form-item label="状态" prop="lockFlag" label-width="120px">
        <el-switch
          v-model="formData.lockFlag"
          active-color="#13ce66"
          inactive-color="#ff4949"
          active-text="正常"
          active-value="0"
          inactive-text="锁定"
          inactive-value="9"
        />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addUser, editUser, restPass, registerUser } from "@/api/user";
import { getJobListByDeptId } from "@/api/job";
import { getRoleList } from "@/api/roles";
import { getDept } from "@/api/dept";
import PopupTreeInput from "@/components/PopupTreeInput";
import initDict from "@/mixins/initDict";
export default {
  components: {
    PopupTreeInput
  },
  mixins: [initDict],
  props: {
    roles: {
      type: Array,
      default() {
        return [];
      }
    },
    deptData: {
      type: Array,
      default() {
        return [];
      }
    },
    deptTreeProps: {
      type: Object,
      default() {
        return {};
      }
    }
  },
  data() {
    // 验证手机号是否合法
    const checkTel = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入手机号码"));
      } else if (!this.checkMobile(value)) {
        callback(new Error("手机号码不合法"));
      } else {
        callback();
      }
    };
    return {
      dialogVisible: false,
      formLabelWidth: "120px",
      operation: true,
      deptName: "",
      jobs: [],
      formData: {
        username: "", //true	普通参数	string		登录用户名
        password: "", //false	普通参数	string		为空时，系统默认设置 123456
        deptId: "", //true	普通参数	int		所属单位id
        phone: "", //true	普通参数	string		手机号
        roleList: [], //true	普通参数	array[int]		角色id数组
        realName: "", //true	普通参数	string		实名
        jobId: "", //true	普通参数	string		岗位id
        email: "", //true	普通参数	string		邮箱
        avatar: "", //false	普通参数	string		头像路径
        lockFlag: "", //true	普通参数	string		0-正常，1-锁定
        file: "" //false	普通参数	file		头像文件
      },
      rules2: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            pattern: /^[a-zA-Z0-9_]{4,8}$/,
            message: "以字母开头，长度在4-8之间， 只能包含字符、数字和下划线"
          }
        ],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { validator: checkTel, trigger: "change" }
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            pattern: /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/,
            message: "输入邮箱不合法"
          }
        ]
      }
    };
  },
  methods: {
    initDialog(bool, obj) {
      this.dialogVisible = true;
      this.operation = bool;
      if (!this.operation) {
        const arr = []
        obj.roleList.map(item => {
          arr.push(item.roleId)
        })
        obj.roleList = arr
        this.formData = obj;
        this.selectDeptName(obj.deptId)
      } else {
        this.formData = {
          username: "test001", //true	普通参数	string		登录用户名
          password: "qweasdzxc", //false	普通参数	string		为空时，系统默认设置 123456
          deptId: "", //true	普通参数	int		所属单位id
          phone: "15824223890", //true	普通参数	string		手机号
          roleList: [], //true	普通参数	array[int]		角色id数组
          realName: "测试校长", //true	普通参数	string		实名
          jobId: "", //true	普通参数	string		岗位id
          email: "965001935@qq.com", //true	普通参数	string		邮箱
          avatar: "", //false	普通参数	string		头像路径
          lockFlag: "0", //true	普通参数	string		0-正常，1-锁定
          file: "" //false	普通参数	file		头像文件
        };
      }
    },
    // 编辑时展示机构名称
    selectDeptName(deptId,arr){
      let data = !arr ? this.deptData : arr
      data.map(item => {
        if(item.deptId === deptId){
          this.deptName = item.name
        }else{
          this.selectDeptName(deptId,item.children)
        }
      })
    },
    // 部门菜单树选中
    deptTreeCurrentChangeHandle(data) {
      this.formData.deptId = data.deptId;
      this.deptName = data.name;
      this.getJobs(data.deptId);
    },
    // 加载岗位列表
    getJobs(id) {
      getJobListByDeptId(id).then(res => {
        this.jobs = res.data.data;
      });
    },
    submitForm() {
      const userRoles = [];
      for (let i = 0, len = this.formData.roleList.length; i < len; i++) {
        userRoles.push(this.formData.roleList[i]);
      }
      this.formData.roleList = userRoles;

      debugger;
      // 添加用户
        const formData = new FormData()
        for(let key in this.formData){
          formData.append(key,this.formData[key])
        }
      if (!this.operation) {
        // 编辑用户
        console.log(this.formData)
        editUser(formData).then(response => {
          if (response.data.code === 200) {
            this.$message({
              type: "success",
              message: "操作成功"
            });
            this.handleClose()
            this.$emit('adminList')
          } else {
            this.$message({
              type: "error",
              message: response.data.msg
            });
          }
        });
      } else {
        addUser(formData).then(response => {
          if (response.data.code === 200) {
            this.$message({
              type: "success",
              message: "操作成功"
            });
            this.handleClose()
            this.$emit('adminList')
          } else {
            this.$message({
              type: "error",
              message: response.data.msg
            });
          }
        });
      }
    },
    handleClose() {
      this.dialogVisible = false;
    }
  }
};
</script>
<style lang="scss" scoped>
</style>
