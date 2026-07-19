<template>
  <div class="dashboard-header">
    <div class="nav-toggle">
      <span class="fa fa-bars"></span>
    </div>
    <div class="pull-left">
      <div class="logo">
        <a class="img">
          <img src="@/assets/img/logo.svg" />
        </a>
        <span class="text">TẬP ĐOÀN BƯU CHÍNH VIỄN THÔNG VIỆT NAM</span>
      </div>
    </div>

    <div class="pull-right menus-right">
      <div class="item notify">
        <img src="@/assets/img/Noti24px.svg" />
        <span class="number">72</span>
      </div>
      <div class="user item dropdown">
        <div class="user-image" data-toggle="dropdown">
          <div
            class="avatar"
            :style="{
              backgroundImage:
                'url(' + require('@/assets/uploads/avatar-default.png') + ')',
            }"
          ></div>
          <div class="username">Lã Thái Dương - lathaiduong</div>
          <div class="pos">Ver 1.0.9.10</div>
        </div>

        <div class="dropdown-menu">
          <a href="#" class="dropdown-item">Thông tin tài khoản</a>
          <a href="#" class="dropdown-item">Đăng xuất</a>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
export default {
  name: 'HeaderStyle1',
  computed: {
    ...mapGetters({
      appName: 'appName',
      logo: 'logo',
      name: 'name',
      image1: 'image1'
    })
  },
  methods: {
    logout: function () {
      localStorage.clear()
      sessionStorage.clear();
      this.$router.push({ name: 'auth.login' })
    }
  }
}
</script>
