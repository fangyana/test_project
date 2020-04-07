<template>
  <!-- 添加或修改对话框 -->
  <el-dialog :title="operation?'新增用户':'编辑用户'" :visible.sync="dialogVisible" center>
    <el-form
      :model="formData"
      label-width="80px"
      size="small"
      label-position="right"
    >
      <el-form-item label="上课班级名称" :label-width="formLabelWidth" prop="className" required>
        <el-input v-model="formData.className" auto-complete="off" placeholder="请输入班级名称" />
      </el-form-item>

      <el-form-item label="上课教师" :label-width="formLabelWidth" prop="teacherName">
        <el-input v-model="formData.teacherName" auto-complete="off" placeholder="请输入姓名" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitForm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addClass,editClass } from '@/api/class'
import initDict from "@/mixins/initDict";
export default {
  mixins: [initDict],
  data() {
    return {
      dialogVisible: true,
      formLabelWidth: "120px",
      operation: true,
      formData: {
        deptId:'',	//int	true	普通参数		学校编号
        className:'',	//string	true	普通参数		班级名
        teacherName:''	//string	true	普通参数		上课老师名
      },
    };
  },
  methods: {
    initDialog(bool, obj) {
      this.dialogVisible = true;
      this.operation = bool;
      if (!this.operation) {
          this.formData = obj
      } else {
        this.formData = {
            deptId:1,	//int	true	普通参数		学校编号
            className:'',	//string	true	普通参数		班级名
            teacherName:''	//string	true	普通参数		上课老师名
        };
      }
    },
    submitForm() {
      debugger;
      // 添加用户
        const params = new URLSearchParams()
        for(let key in this.formData){
          params.append(key,this.formData[key])
        }
      if (!this.operation) {
        // 编辑用户
        editClass(params).then(response => {
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
        addClass(params).then(response => {
            console.log(response)
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
