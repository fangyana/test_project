<template>
  <div class="group-container">
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
              :placeholder="$t('login.username')"
              name="username"
              type="text"
            >
              <i slot="prefix" class="">
                <svg-icon icon-class="user1" />
              </i>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              :placeholder="$t('login.password')"
              name="password"
              type="password"
            >
              <i slot="prefix" class="">
                <svg-icon icon-class="密码" />
              </i>
            </el-input>
          </el-form-item>
          <p>
            <img height="12" src="@/assets/mobile/hint-icon.png" alt="">
            温馨提示： 请使用【训练机构登记】的用户名登录
          </p>
          <el-button
            :loading="loading"
            type="primary"
            style="width:100%;"
            @click.native.prevent="handleLogin"
          >
            {{ $t('login.logIn') }}
          </el-button>
        </el-form>
      </el-tabs>
    </el-tabs>
  </div>
</template>

<script>

export default {
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value.length < 2) {
        callback(new Error('The password can not be less than 6 digits'))
      } else {
        callback()
      }
    }
    return {
      loading: false,
      activeName: 'loginForm',
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  created() {

  },
  methods: {
    handleClick(tab, event) {
      this.$refs[tab.paneName].resetFields()
    },
    // 用户名 密码登录
    handleLogin() {
      // console.log(!!this.loginForm.username)
      // if (this.loginForm.username) {
      //   callback(new Error('请填写用户名'))
      //   return
      // } else if (this.loginForm.password) {
      //   callback(new Error('请填写密码'))
      //   return
      // }
      // if (this.loginForm.username === 'admin' && this.loginForm.password === '123456') {

      // }
    }
  }
}
</script>
<style lang="scss" scoped>
.group-container{
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
        font-size:16px;
        text-align: center;
    }
    p{
      margin:0;
      height: 38px;
      font-size: 12px;
      font-weight: 400;
      color: rgba(154, 172, 201, 1);
    }
}
</style>
