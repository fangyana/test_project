<template>
  <div class="personal-container">
    <img src="@/assets/logo/logo-big.png" alt="">
    <h2>消防体质测试健康管理系统</h2>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tabs name="loginForm">
        <el-form
          ref="loginForm"
          :model="loginForm"
          class="login-form"
          label-position="left"
        >
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="输入胸牌号"
              name="username"
              type="text"
            >
              <i slot="prefix" class="">
                <svg-icon icon-class="user1" />
              </i>
            </el-input>
          </el-form-item>

          <el-button
            :loading="loading"
            type="primary"
            style="width:100%;"
            @click.native.prevent="searchGrade"
          >
            查询
          </el-button>
        </el-form>
      </el-tabs>
    </el-tabs>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      activeName: 'loginForm',
      loginForm: {
        username: 'admin'
      }
    }
  },
  created() {},
  methods: {
    handleClick(tab, event) {
      this.$refs[tab.paneName].resetFields()
    },
    searchGrade() {
      console.log(1111)
    }
  }
}
</script>
<style lang="scss" scoped>
.personal-container {
    position: fixed;
    top: 10%;
    left:0;
    right:0;
    margin-left:auto;
    margin-right:auto;
    width:90%;
  >img{
      display:block;
      width:60px;
      margin:auto;
  }
  >h2{
    text-align: center;
    font-size:16px;
}
}
</style>
