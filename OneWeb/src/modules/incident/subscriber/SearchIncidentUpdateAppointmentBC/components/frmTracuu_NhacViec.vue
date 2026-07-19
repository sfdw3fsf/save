<template>
  <div>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjTraCuuNhacViec" :position="position" :close="closeDialog" :header="'LỊCH SỬ NHẮC PHIẾU'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
      <div>
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="#" @click.prevent="Thoat"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát</a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <!-- <div class="legend-title">LỊCH SỬ NHẮC PHIẾU</div>  -->
            <div class="row">
              <DataGrid v-bind:columns="dgvTienTrinhSMS.headers" :enabledSelectFirstRow="true" v-bind:dataSource="dgvTienTrinhSMS.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true"> </DataGrid>
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
import frmApi from './PopUpApi.js'
export default {
  name: 'frmTraCuuNhacViec',
  components: { api, frmApi },
  data() {
    return {
      Loading: false,
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      baohongId: 0,
      dgvTienTrinhSMS: {
        headers: [
          { fieldName: 'nguoi_cn', headerText: 'Người gửi' },
          { fieldName: 'ngay_cn', headerText: 'Ngày gửi' },
          { fieldName: 'so_dt', headerText: 'Số điện thoại' },
          { fieldName: 'noidung', headerText: 'Nội dung' }
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
    openDialog(vbaohongId) {
      this.baohongId = vbaohongId
      try {
        this.Loading = true
        Promise.all([this.LoadTienTrinhSMS()])
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
        this.$refs.dialogObjTraCuuNhacViec.show()
      }
    },
    closeDialog() {
      this.Thoat()
    },
    async LoadTienTrinhSMS() {
      let input = { baohong_id: this.baohongId }
      let data = this.GetData(await frmApi.lay_ds_tientrinh_sms(this.axios, input))
      this.dgvTienTrinhSMS.data = data
    },
    Thoat() {
      this.$emit('dialogObjTraCuuNhacViec_Close', true)
      this.$refs.dialogObjTraCuuNhacViec.hide()
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
