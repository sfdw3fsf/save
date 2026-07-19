<template>
  <b-modal hide-footer title="Đổi mật khẩu account MyTV" ref="dialogChangePassword" class="modal-content popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="tsbtnGhiLai_Click" style="cursor: pointer">
            <span class="icon one-reload1"></span>Cập nhật
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
        <div class="title fw6 upper">ĐỔI MẬT KHẨU ACCOUNT MYTV</div>
      </div>
      <div class="box-form">
        <div class="legend-title">Thông tin tài khoản MyTV</div>
        <div class="info-row">
          <div class="key w90">Tài khoản</div>
          <div class="value">
            <input v-model="txtAccount" type="text" class="form-control highlight"
                   v-on:keyup.enter="txtAccount_KeyPress" autofocus>
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Trạng thái</div>
          <div class="value">
            <input v-model="txtTrangThai" type="text" class="form-control" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Mật khẩu cũ</div>
          <div class="value">
            <input v-model="txtMatKhau" type="text" class="form-control" readonly>
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Mật khẩu mới</div>
          <div class="value">
            <input v-model="txtMatKhauMoi" type="text" class="form-control">
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import api from "../api";

export default {
  name: "ChangePassword",
  methods: {
    async showModal(data) {
      this.mytvACC = data;
      this.txtAccount = this.mytvACC;
      await this.tsbtnTraCuu_Click();
      this.$refs['dialogChangePassword'].show()
    },
    async frmTraCuuVASC_Load() {

    },
    async txtAccount_KeyPress(e) {
      if (e.keyCode === 13) {
        if (this.txtAccount !== "") {
          await this.tsbtnTraCuu_Click();
        }
      } else {
      }
    },
    Clear() {
      this.txtTrangThai = "";
      this.txtMatKhauMoi = "";
      this.txtMatKhau = "";
    },
    async tsbtnTraCuu_Click() {
      this.loading(true);
      await this.TraCuu();
      this.loading(false);
    },
    async TraCuu() {
      try {
        this.Clear();
        // GetSubscriberInfoVO info = vasc.GetSubscriberInfo(txtAccount.Text.Trim());
        //

        const response = await api.getSubscriberInfo(this.axios, {
          "iptvaccount": this.txtAccount
        })
        const data = await response.data
        if (data.errorCode === 0) {
          if (data.data !== null) {
            let info = data.data;
            if (info.idno === null) {
              this.$toast.error(info.Errordesc);
              return;
            }
            //
            this.txtTrangThai = info.status;

            //txtMatKhauMoi.Text = info.STBID;
            this.txtMatKhau = info.iptvpw;
            this.txtMatKhauMoi = "";
          }
        }


      } catch (ex) {
        // Message_Box.ShowError("Có lỗi: \n" + ex.message());
        this.$toast.error("Có lỗi: \n" + ex.message())
      }
    },

    tsbtnThoat_Click() {
      this.$refs['dialogChangePassword'].hide();
      this.$emit('form-close', {taikhoan: this.txtAccount});
    },
    accept() {
      this.tsbtnGhiLai_Click();
    },
    async tsbtnGhiLai_Click() {
      this.loading(true);
      try {
        if (this.txtMatKhauMoi === "") {
          this.$toast.error("Mật khẩu mới đang trống!")
          // Message_Box.ShowError("Mật khẩu mới đang trống!");
          this.loading(false);
          return;
        }

        let post = {
          "iptvaccount": this.txtAccount,
          "newPassword": this.txtMatKhauMoi,
          "oldPassword": this.txtMatKhau
        }
        try {
          const response = await api.capnhatMatKhau(this.axios, post)
          const data = await response.data
          if (data.errorCode === 0) {
            //
            // this.$refs['dialogChangePassword'].hide()
            let strGiaTri = this.txtAccount.trim() + ";MK_CU:" + this.txtMatKhau.trim() +
              ",MK_MOI:" + this.txtMatKhauMoi.trim();
            // console.log(strGiaTri)
            this.$toast.success('Cập nhật mật khẩu thành công')
          } else {
            this.$toast.error('Cập nhật mật khẩu không thành công')
          }
        } catch (e) {
          console.log('Cập nhật mật khẩu không thành công')
        } finally {
          this.loading(false);
        }
        //
        // vasc.ChangePassword(txtAccount.Text.Trim(), txtMatKhau.Text.Trim(), txtMatKhauMoi.Text.Trim());
        //
        // string strGiaTri = txtAccount.Text.Trim() + ";MK_CU:" + txtMatKhau.Text.Trim() +
        // ",MK_MOI:" + txtMatKhauMoi.Text.Trim();
        // BangTuDienFacade btd = new BangTuDienFacade();
        // btd.Capnhat_Log_TD_MyTV(KIEU_TD_MYTV.TD_MATKHAU, strGiaTri, tt_nd.ip, tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ma_nd);
      } catch (e) {
      }
    }
  },
  data() {
    return {
      mytvACC: "",
      txtTrangThai: '',
      txtAccount: '',
      txtMatKhauMoi: '',
      txtMatKhau: '',
      tsbtnGhiLai: true,
      tsbtnThoat: true,
    }
  },
  created() {
  }
}
</script>

<style scoped>
input[readonly] {
  background-color: white !important;
}
</style>
