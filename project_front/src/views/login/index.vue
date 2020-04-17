<template>
  <div class="login-bkg-con" :style="note">
    <div
      class="login-container"
    >
      <div class="login">
        <div class="title-container">
          <h3 class="title">{{ '登录' }}</h3>
        </div>
        <el-form
          ref="loginForm"
          :model="loginForm"
          :rules="loginRules"
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
              <i slot="prefix" class>
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
              <i slot="prefix" class>
                <svg-icon icon-class="密码" />
              </i>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-col :span="12" style="overflow:hidden">
              <el-form-item prop="captcha">
                <el-input
                  v-model="loginForm.code"
                  type="test"
                  auto-complete="off"
                  placeholder="验证码, 单击图片刷新"
                  style="width: 100%;"
                  @keyup.enter.native="handleLogin"
                />
              </el-form-item>
            </el-col>
            <el-col class="line" :span="1">&nbsp;</el-col>
            <el-col :span="11">
              <el-form-item>
                <img
                  style="width: 85%;height: 35px;float: right;"
                  class="pointer"
                  :src="src"
                  alt
                  @click="refreshCaptcha"
                >
              </el-form-item>
            </el-col>
          </el-form-item>

          <el-button
            :loading="loading"
            type="primary"
            style="width:100%;"
            @click.native.prevent="handleLogin"
          >{{ $t('login.logIn') }}</el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value.length < 2) {
        callback(new Error('密码不能少于六位数'))
      } else {
        callback()
      }
    }
    return {
      note: {
        backgroundImage:
          'url(' + require('../../assets/login/login_bkg.png') + ')',
        backgroundRepeat: 'no-repeat',
        backgroundPosition: 'center'
      },
      loginForm: {
        username: '',
        password: '',
        code: '',
        token: '',
        t: ''
      },
      src: '',
      loginRules: {
        username: [{ required: true, trigger: 'blur' }],
        password: [
          { required: true, trigger: 'blur', validator: validatePassword }
        ]
      },
      loading: false,
      redirect: undefined,
      token: '',
      time: 60
    }
  },
  created() {
    this.refreshCaptcha()
  },
  methods: {
    // 用户名 密码登录
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store
            .dispatch('LoginByUsername', this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || '/' })
            })
            .catch(() => {
              this.loading = false
              this.refreshCaptcha()
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
      // if (!this.loginForm.code) {
      //   this.$message({
      //       message: '请输入验证码',
      //       type: 'error'
      //   })
      // } else {
      //   this.$refs.loginForm.validate(valid => {
      //     if (valid) {
      //       this.loading = true;
      //       this.$store
      //         .dispatch("LoginByUsername", this.loginForm)
      //         .then(() => {
      //           this.$router.push({ path: this.redirect || "/" });
      //         })
      //         .catch(() => {
      //           this.loading = false;
      //           this.refreshCaptcha();
      //         });
      //     } else {
      //       console.log("error submit!!");
      //       return false;
      //     }
      //   });
      // }
    },
    refreshCaptcha: function() {
      this.loginForm.code = ''
      this.loginForm.t = new Date().getTime()
      // 正式地址
      // this.src = 'http://***/captcha.jpg?t=' + this.loginForm.t
      // 测试地址
      this.src = 'http://127.0.0.1:8008/project/captcha.jpg?t=' + this.loginForm.t
    },
    handleClick(tab, event) {
      this.$refs[tab.paneName].resetFields()
    },
    handleTenant(tenantId) {
      setTenant(tenantId)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.login-bkg-con {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}
.login-container {
  display: flex;
  overflow: hidden;
  background:rgba(44,48,76,0.7);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  .login {
    padding: 35px;

    .title {
      margin: 0 auto 30px auto;
      text-align: center;
      color: #fff;
    }

    .el-form-item {
      // border: 1px solid rgba(255, 255, 255, 0.1);
      border-radius: 5px;
      color: #454545;
    }
  }
}
</style>
