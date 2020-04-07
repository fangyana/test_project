<template>
  <el-dialog
    title="新增"
    :visible.sync="dialogVisible"
    width="40%"
    :before-close="handleClose"
    center
  >
    <el-form label-position="right" label-width="100px" :model="formData">
      <el-form-item label="学校/机构名称">
        <el-input v-model="formData.name" placeholder="请输入学校/机构名称" />
      </el-form-item>
      <el-form-item label="类型">
        <el-select v-model="formData.deptType" style="width:100%;" placeholder="请选择类型">
          <el-option label="机构" value="1" />
          <el-option label="学校" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="上级机构" prop="parentName">
        <popup-tree-input
          :data="popupTreeData"
          :props="popupTreeProps"
          :prop="parentName"
          :node-key="''+formData.parentId"
          :current-change-handle="handleTreeSelectChange"
        />
      </el-form-item>
      <el-form-item :xl="24" label="地址选择">
        <el-row>
          <el-col :span="8">
            <el-select
              v-model="formData.provinceId"
              placeholder="省"
              @change="getAreaList(1,formData.provinceId)"
            >
              <el-option
                v-for="item in provinceData"
                :key="item.areaId"
                :label="item.areaName"
                :value="item.areaId"
              />
            </el-select>
          </el-col>
          <el-col :span="8">
            <el-select
              v-model="formData.cityId"
              placeholder="市"
              @change="getAreaList(2,formData.cityId)"
            >
              <el-option
                v-for="item in cityData"
                :key="item.areaId"
                :label="item.areaName"
                :value="item.areaId"
              />
            </el-select>
          </el-col>
          <el-col :span="8">
            <el-select v-model="formData.countyId" placeholder="县">
              <el-option
                v-for="item in countyData"
                :key="item.areaId"
                :label="item.areaName"
                :value="item.areaId"
              />
            </el-select>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="formData.sort" placeholder="请输入排序" />
      </el-form-item>
      <el-form-item label="代码编号">
        <el-input v-model="formData.deptNo" placeholder="请输入学校/机构代码编号" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="submit">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import "element-ui/lib/theme-chalk/display.css";
import { getAreaList } from "@/api/basics";
import { saveDept, getDept } from "@/api/dept";
import PopupTreeInput from "@/components/PopupTreeInput";
export default {
  props: {
    popupTreeData: {
      type: Array,
      default() {
        return [];
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      parentName: "",
      formData: {
        parentId: "",
        name: "", // string	true	学校/机构名称
        sort: "", // int	true	排序
        provinceId: "", // string	true	省
        cityId: "", // string	true	市
        countyId: "", // string	true	区/县
        deptType: "", // string	true	类型（1：机构，2：学校）
        deptNo: "" // string	true	学校/机构代码编号
      },
      popupTreeProps: {
        label: "name",
        children: "children"
      },
      provinceData: [],
      cityData: [],
      countyData: [],
      loading: false
    };
  },
  methods: {
    initDialog() {
      this.formData = {
        name: "", // string	true	学校/机构名称
        parentId: "", // int	true	父机构id
        sort: "", // int	true	排序
        provinceId: "", // string	true	省
        cityId: "", // string	true	市
        countyId: "", // string	true	区/县
        deptType: "", // string	true	类型（1：机构，2：学校）
        deptNo: "" // string	true	学校/机构代码编号
      };
      this.dialogVisible = true;
      this.getAreaList(0, "");
    },
    // 获取省市区
    async getAreaList(type, parentId) {
      const parms = new URLSearchParams();
      parms.append("type", type);
      parms.append("parentId", parentId);
      await getAreaList(parms).then(res => {
        if (type === 0) {
          this.provinceData = res.data.data;
        } else if (type === 1) {
          this.cityData = res.data.data;
          this.formData.cityId = null;
          this.formData.countyId = null;
        } else {
          this.countyData = res.data.data;
        }
      });
    },
    // 机构树选中
    handleTreeSelectChange(data) {
      this.formData.parentId = data.deptId;
      this.parentName = data.name;
    },
    submit() {
      for (let key in this.formData) {
        if (!this.formData[key]) {
          return this.$message({
            message: "请检查是否全部输入",
            type: "error"
          });
        }
      }
      saveDept(this.formData)
        .then(res => {
          if (res.data.data === true) {
            this.$message({
              message: "保存成功",
              type: "success"
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    handleClose() {
      this.dialogVisible = false;
    }
  },
  components: {
    PopupTreeInput
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
