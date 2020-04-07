<template>
  <div class="app-container">
    <transition name="fade">
      <router-view />
    </transition>
    <div class="bar-nav-container">
      <router-link
        v-for="(item,i) in tabs"
        :key="i"
        :to="item.path"
        :class="item.path == defult ? 'active' : ''"
        :click="changeHandle()"
        tag="div"
      >
        {{ item.lable }}
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      defult: '/register/personal',
      tabs: [{
        lable: '个人成绩查询',
        path: '/register/personal'
      }, {
        lable: '整体成绩查询',
        path: '/register/group'
      }]
    }
  },
  created() {
    this.$router.push({ path: this.defult })
  },
  methods: {
    changeHandle() {
      this.defult = this.$route.path
    }
  }
}
</script>
<style lang="scss" scoped>
.app-container {
  padding: 0;
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  .bar-nav-container {
    overflow: hidden;
    position: absolute;
    bottom: 0;
    width: 100%;
    > div {
      float: left;
      width: 50%;
      height: 60px;
      line-height: 60px;
      text-align: center;
      background: #f2f2f2;
      font-size: 14px;
      z-index: 1001;
    }
    > .active {
      background-color: #2879ff;
      color: #fff;
    }
  }
  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.5s;
  }
  .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
    opacity: 0;
  }
}
</style>
