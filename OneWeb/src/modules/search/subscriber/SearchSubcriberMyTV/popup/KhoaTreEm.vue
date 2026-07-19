<template>
  <b-modal hide-footer title="Đặt/Reset mật khẩu khóa trẻ em" ref="dialogKhoaTreEm" class="modal-content popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li @click="tsbtnDatMatKhau_Click">
          <a style="cursor: pointer">
            <span class="icon one-reload1"></span>Đặt mật khẩu
          </a>
        </li>
        <li @click="tsbtnResetMatKhau_Click">
          <a style="cursor: pointer">
            <span class="icon one-reload1"></span>Reset mật khẩu
          </a>
        </li>
        <li>
          <a @click.prevent="tsbtnThoat_Click" style="cursor: pointer">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="title fw6 upper">Đặt/Reset mật khẩu khóa trẻ em</div>
      </div>
      <div class="box-form">
        <!--        <div class="legend-title">Thông tin tài khoản MyTV</div>-->
        <div class="info-row">
          <div class="key w90">Tên đăng nhập</div>
          <div class="value">
            <input v-model="txtTenDangNhap" type="text" class="form-control">
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Mật khẩu</div>
          <div class="value">
            <input v-model="txtMatKhau" type="text" class="form-control">
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import api from "../api";

export default {
  name: "KhoaTreEm",
  data() {
    return {
      txtTenDangNhap: '',
      txtMatKhau: ''
    }
  },
  methods: {
    async showModal() {
      this.$refs['dialogKhoaTreEm'].show()
    },
    async tsbtnThoat_Click() {
      this.$refs['dialogKhoaTreEm'].hide();
    },
    async tsbtnDatMatKhau_Click() {
      await this.resetPasword(1);
    },
    async tsbtnResetMatKhau_Click() {
      await this.resetPasword(2);
    },
    async resetPasword(kieu) {
      try {
        let tenDangNhap = this.txtTenDangNhap.toString().trim();
        let matKhau = this.txtMatKhau.toString();
        if (kieu === 2) {
          matKhau = "";
        }

        if (tenDangNhap === "") {
          this.$toast.error('Bạn chưa nhập tên đăng nhập')
          return;
        }

        if (kieu === 1 && this.txtMatKhau.toString() === "") {
          this.$toast.error('Bạn chưa nhập mật khẩu')
          return;
        }
        this.$confirm(``,
          'Bạn chắc chắn muốn thay đổi?',
          {
            confirmButtonText: 'Đồng ý',
            cancelButtonText: 'Không đồng ý',
            type: 'warning'
          }).then(async () => {
          // console.log(this.txtTenDangNhap, this.txtMatKhau)

          const response = await api.resetPasswordLockChildren(this.axios, {
            "username": tenDangNhap,
            "lock_password": matKhau
          });
          const data = await response.data
          if (data.errorCode === 0 && data.data === 'ok') {
            if (kieu === 1) {
              this.$root.$toast.success("Đổi mật khẩu thành công!");
            } else {
              this.$root.$toast.success("Reset mật khẩu thành công!");
            }
          } else {
            this.$root.$toast.error(data.faultString);
          }
        }).catch((e) => {
          console.log(e)
        })
      } catch (ex) {
        this.$toast.error(ex.toString())
      }
    }
  }
}
</script>

<style scoped>

</style>
