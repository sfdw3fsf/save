<template>
  <div class="main-wrapper">
    <breadcrumb :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tsbtnCapNhat.visible" :class="{ disabledInput: !tsbtnCapNhat.enabled }" @click="tsbtnCapNhat_Click">
          <a> <span class="icon one-save1"></span>Cập nhật</a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="title-bg-main">
          <span class="title">Thông tin người dùng</span>
        </div>
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w100">Tài khoản</div>
              <div class="value">
                <input :disabled="!txtMaND.enable" v-model="txtMaND.value" type="text" class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-9 col-12">
            <div class="info-row">
              <div class="key w100">Tên người dùng</div>
              <div class="value">
                <input :disabled="!txtTenND.enable" v-model="txtTenND.value" type="text" class="form-control" />
              </div>
            </div>
          </div>
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key w100">Quầy hiện tại</div>
              <div class="value">
                <input :disabled="!txtQuayHienTai.enable" v-model="txtQuayHienTai.value" type="text" class="form-control" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="title-bg-main">
          <span class="title">Thông tin quầy mới</span>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row">
              <div class="key w100">Quầy thu</div>
              <div class="value">
                <SelectExt ref="cboQuayMoi" v-model="cboQuayMoi.value" :dataSource="cboQuayMoi.list" dataTextField="ten_dv" dataValueField="donvi_id" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from 'vue'
import api from './api'
import breadcrumb from '@/components/breadcrumb'

export default {
  components: {
    breadcrumb
  },
  name: 'frmChonQuay',
  data: () => ({
    customerSearchType: 'pbh',
    header: {
      title: 'Gán quầy mới',
      list: [
        { name: 'Trang chủ', link: { name: 'Ui.cards' } },
        { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
        { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
      ]
    },
    tsbtnCapNhat: { enabled: true, visible: true },
    txtMaND: { enabled: true, value: null },
    txtTenND: { enabled: true, value: null },
    txtQuayHienTai: { enabled: true, value: null },
    cboQuayMoi: { enabled: true, value: null }
  }),
  computed: {},
  async mounted() {
    await this.frmPhanQuayChoUser_Load()
  },
  watch: {},
  methods: {
    async frmPhanQuayChoUser_Load() {
      this.loading(true)
      try {
        this.txtMaND.value = this.$root.token.getMaNhanVien()
        this.txtTenND.value = this.$root.token.getUserName()
        this.txtQuayHienTai.value = (await api.getTenDonVi(this.axios, { id: this.$root.token.getDonViID() })).data.data.TEN_DV
        await this.HienThiDanhSachQuay()
      } catch (ex) {
        this.$toast.error(`${ex}`)
      } finally {
        this.loading(false)
      }
    },
    async HienThiDanhSachQuay() {
      this.loading(true)
      this.cboQuayMoi.list = (await api.lay_ds_quaythu_gan_nguoidung(this.axios, {})).data.data
      this.loading(false)
      // this.cboQuayMoi.ValueMember = 'donvi_id'
      // this.cboQuayMoi.DisplayMember = 'ten_dv'
      try {
        console.log('this.$root.token.getDonViID()')
        console.log(this.$root.token.getDonViID())
        this.cboQuayMoi.value = this.$root.token.getDonViID()
      } catch (ex) {}
    },
    async tsbtnCapNhat_Click() {
      try {
        if (['', null].includes(this.cboQuayMoi.value)) {
          this.$toast.warning('Hãy chọn Quầy mới')
          return
        }
        if (this.$root.token.getDonViID() == this.cboQuayMoi.value) {
          this.$toast.warning('Quầy thu mới không được trùng với quầy thu hiện tại')
          return
        }
        if (!confirm('Bạn thật sự muốn chọn quầy thu mới không?')) return
        this.loading(true)
        await api.capnhat_quaythu_nhanvien(this.axios, {
          donvi_id: this.cboQuayMoi.value
        })
        this.loading(false)
        // tt_nd.donvi_id = this.cboQuayMoi.value;
        // tt_nd.ten_dv = this.cboQuayMoi.value;
        // txtQuayHienTai.Text = tt_nd.ten_dv;

        this.$toast.success('Cập nhật thành công bạn sẽ phải đăng nhập lại!')

        setTimeout(async () => {
          try {
            await this.$root.context.post('/quantri/user/logout', null)
          } catch (error) {
          } finally {
            this.$root.context.logOut()
            if (this.$route.name != 'Login') this.$router.push({ name: 'Login' })
          }
        }, 3000)

        // var frmmain = this.MdiParent;
        // if (frmmain is frmFormMain)
        //     (frmmain as frmFormMain).Logout_V2();
      } catch (ex) {
        this.$toast.error('' + ex)
      }
    },
    // async tsbtnThoat_Click() {
    //   this.Close()
    // },
    // async txtMaND_KeyPress() {
    //   e.Handled = true
    // },
    // async txtTenND_KeyPress() {
    //   e.Handled = true
    // },
    // async txtQuayHienTai_KeyPress() {
    //   e.Handled = true
    // }
  }
}
</script>
<style scoped></style>
