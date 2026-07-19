// namespace WinUI.WinUIQLDHXLSuCo
// {
//     public partial class frmLichSu_HenBH : DevExpress.XtraEditors.XtraForm
<style>
</style>
<template>
  <div>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjLsHenBH" :position="position" :close="closeDialog" :header="'Lịch sử hẹn báo hỏng'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
      <div>
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="#" @click.prevent="ThoatLsBH"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát</a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <!-- <div class="legend-title">Thông số kỹ thuật thuê bao</div> -->
            <div class="row">
              <DataGrid v-bind:columns="DsLsPhieuBaoHong.headers" :enabledSelectFirstRow="true" v-bind:dataSource="DsLsPhieuBaoHong.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="gridDsLsPhieuBaoHong"> </DataGrid>
              <div class="col-sm-12 col-12"></div>
            </div>
          </div>
        </div>
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import api from '../Api'
export default {
  name: 'frmLichSu_HenBH',
  components: { api },
  data() {
    return {
      Loading: false,
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      kieu: 2,
      baohongId: 0,
      hdtb_id: 0,
      DsLsPhieuBaoHong: {
        headers: [
          { fieldName: 'giohen_tu', headerText: 'Ngày hẹn từ' },
          { fieldName: 'giohen_den', headerText: 'Ngày hẹn đến' },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN' },
          { fieldName: 'nguoi_cn', headerText: 'Người CN' },
          { fieldName: 'noidung', headerText: 'Nội dung' },
          { fieldName: 'lydohen', headerText: 'Lý do hẹn' },
          { fieldName: 'dv_hen', headerText: 'ĐV hẹn' },
          { fieldName: 'kieu_hen', headerText: 'Kiểu hẹn' },
          { fieldName: 'lydo_xm', headerText: 'KQ xác minh' },
          { fieldName: 'nguoi_xm', headerText: 'NV xác minh' }
        ],
        data: []
      }
    }
  },
  watch: {
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  methods: {
    openDiaLog(vbaohongId, vkieu) {
      console.log('openDiaLog:', vbaohongId, vkieu)
      try {
        this.Loading = true
        this.kieu = vkieu
        if (vkieu == 2) this.baohongId = vbaohongId
        else this.hdtb_id = vbaohongId
        Promise.all([this.HienThiDanhSach()])
          .then((r) => {
            console.log(r)
          })
          .finally((x) => {
            this.Loading = false
          })
      } catch (ex) {
        this.Loading = false
      } finally {
        this.Loading = false
        this.$refs.dialogObjLsHenBH.show()
      }
    },
    closeDialog(){
      this.$emit('frmLichSu_HenBH_Close', false)
    },
    async HienThiDanhSach() {
      if (this.kieu == 2) {
        await this.getDsLsPhieuBaoHong()
      } else {
        await this.getDsLsHenThiCong()
      }
    },
    getDsLsPhieuBaoHong: async function () {
      var input = { p_baohong_id: this.baohongId }
      this.DsLsPhieuBaoHong.data = this.GetData(await api.getDsLsPhieuBaoHong(this.axios, input))
    },
    getDsLsHenThiCong: async function () {
      var input = { p_hdtb_id: this.hdtb_id }
      this.DsLsPhieuBaoHong.data = this.GetData(await api.getDsLsHenThiCong(this.axios, input))
    },
    ThoatLsBH() {
      this.$emit('frmLichSu_HenBH_Close', true)
      this.$refs.dialogObjLsHenBH.hide()
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>