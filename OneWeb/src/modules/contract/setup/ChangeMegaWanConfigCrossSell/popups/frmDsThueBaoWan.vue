<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" ref="dialogObjDsThueBaoWan" :position="position" :close="closeDialogChuyenTo" :header="'Danh sách thuê bao Megawan'" showCloseIcon="false" width="80%" target="#app .main-wrapper">
    <div class="main-wrapper-popup main-wrapper-popup-chontinh">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:void(0)" @click.prevent="tsbtnGhiLai_Click"> <span class="icon one-file-attach"></span> Ghi lại </a>
          </li>
          <li>
            <a href="javascript:void(0)" @click.prevent="btnLayds_Click"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Lấy lại DS</a>
          </li>
        </ul>
      </div>
      <div class="popup-body popupChuyenTo">
        <div class="box-form">
          <div class="legend-title">Danh sách thuê bao Megawan</div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <DataGrid ref="ref_gridWan" v-bind:columns="gridWan.headers" v-bind:dataSource="gridWan.data" :showColumnCheckbox="true" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="false"> </DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import api2 from './PopUpApi.js'
export default {
  components: {
    api2
  },
  name: 'frmDsThueBaoWan',
  data() {
    return {
      Loading: false,
      position: { X: 'center', Y: 'top' },
      animationSettings: { effect: 'Zoom' },
      cbxChonCG: false,
      vkhachhang_id: 0,
      vma_tb: '',
      vhdtb_id: 0,
      vloaitb_id: 0,
      dtWan: [],
      gridWan: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Account', value: '' },
          { fieldName: 'tocdo', headerText: 'Tốc độ', value: '' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', value: '' },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', value: '' },
          { fieldName: 'loi', headerText: 'Lỗi', value: '' }
        ],
        data: []
      }
    }
  },
  methods: {
    async frmDsThueBaoWan_Load(khachhang_id, ma_tb, hdtb_id, loaitb_id) {
      this.vkhachhang_id = khachhang_id
      this.vma_tb = ma_tb
      this.vhdtb_id = hdtb_id
      this.vloaitb_id = loaitb_id
      try {
        this.loading(true)
        this.$refs.ref_gridWan.show()
        await this.hienthi()
      } catch (err) {
        this.ShowError(`${err}`)
      } finally {
        this.loading(false)
      }
    },
    async hienthi() {
      let data = await this.lay_ds_tb_wan()
      if (data == null || data.length == 0) {
        this.ShowError(`Không có thuê bao phù hợp`)
        this.Close()
        return
      }

      this.gridWan.data = data
    },
    async tsbtnGhiLai_Click() {
      try {
        let dt = this.gridWan.data
        if (dt == null || dt.length == 0) {
          this.ShowError(`Chưa có thuê bao để cập nhập`)
          return
        }
        let items = this.$ref_gridWan.getSelectedRecords()
        if (items == null || items.length == 0) {
          this.ShowError(`Chưa chọn thuê bao để cập nhập`)
          return
        }
        this.loading(true)
        this.dtWan = items
        let dt2 = await this.kt_thaydoi_thongtin_hangloat()
        if (dt2 != null && dt2.length > 0) {
          this.dtWan = null
          this.gridWan.data = dt2.Tables[0]
          this.ShowError('Thuê bao được chọn có lỗi vui lòng kiểm tra lại')
        } else {
          this.Close()
        }
      } catch (err) {
        this.ShowError(`${err}`)
      } finally {
        this.loading(false)
      }
    },
    async btnLayds_Click() {
      try {
        this.loading(true)
        await this.hienthi()
      } catch (err) {
        this.loading(false)
        this.ShowError(`${err}`)
      }
    },
    async lay_ds_tb_wan() {
      let input = { vkhachhang_id, vma_tb, vloaitb_id }
      let data = this.GetData(await api2.lay_ds_tb_wan(this.axios, input))
      return data
    },
    async kt_thaydoi_thongtin_hangloat() {
      let items = this.$ref_gridWan.getSelectedRecords()
      let vds_thuebao_id = items.map((x) => x.thuebao_id).join()
      let input = { vhdtb_id, vds_thuebao_id: vds_thuebao_id, vkieu: 1 }
      let data = this.GetData(await api2.kt_thaydoi_thongtin_hangloat(this.axios, input))
      return data
    },
    Close() {
      this.$refs.ref_gridWan.hide()
      this.$emit('close', this.dtWan)
    },
    GetData: function (response) {
      if (response.data.error == '200' && response.data.error_code == 'BSS-00000000') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    closeDialogChuyenTo(){
    },
    ShowAlert: function (message) {
      this.$toast.error(message)
    },
    ShowError: function (message) {
      this.$toast.error(message)
    },
    ShowSuccess: function (message) {
      this.$toast.success(message)
    }
  }
}
</script>