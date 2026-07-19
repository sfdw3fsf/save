<template>
  <b-modal
    ref="popupChonTinhTSL"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chọn tỉnh TLS
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="popup-body">
        <div class="info-row">
          <div class="key w90">Chọn tỉnh đầu:</div>
          <div class="value">
            <Select2 ref="cboTinh" :disabled='false' v-model="cboTinh.value"
                     :options="cboTinh.list" :settings="settingSelect2"/>
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Chọn tỉnh cuối:</div>
          <div class="value">
            <Select2 ref="cboTinhCuoi" :disabled='false' v-model="cboTinhCuoi.value"
                     :options="cboTinhCuoi.list" :settings="settingSelect2"/>
          </div>
        </div>
        <div class="info-row">
          <div class="key w90"></div>
          <div class="value">
            <b-button class="btn btn-primary" @click="btnChapNhan_Click"><span class="icon one-save"></span> Chấp nhận</b-button>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import API from '../API'

export default {
  name: 'ChonTinhTLS',
  data () {
    return {
      settingSelect2: {
        language: 'vi'
      },
      cboTinhCuoi: {
        list: [],
        value: 0
      },
      cboTinh: {
        list: [],
        value: 0
      },
      tinhdau: null,
      tinhcuoi: null
    }
  },
  methods: {
    showModal () {
      this.$refs.popupChonTinhTSL.show()
    },
    async btnChapNhan_Click () {
      if (this.cboTinh.value == 0) {
        this.$root.$toast.error('Chưa chọn tỉnh đầu')
        return
      }
      if (this.cboTinhCuoi.value == 0) {
        this.$root.$toast.error('Chưa chọn tỉnh cuối')
        return
      }
      this.tinhdau = await this.GetTinhTC(this.cboTinh.value)
      this.tinhcuoi = await this.GetTinhTC(this.cboTinhCuoi.value)
      this.tinhdau.tentinh = this.cboTinh.list.filter(item => item.id == this.cboTinh.value)[0].text
      this.tinhcuoi.tentinh = this.cboTinhCuoi.list.filter(item => item.id == this.cboTinhCuoi.value)[0].text
      this.$emit('chonTinhTLSAccept', this.tinhdau, this.tinhcuoi)
      this.hideModal()
    },
    async handleShowModal () {
      let dataTinh = await this.SP_HIENTHI_TINH_KN()
      if (dataTinh.length > 0) {
        let newData = dataTinh.map(item => {
          return {
            id: item.tinh_id,
            text: item.tentinh
          }
        })
        this.cboTinh.list = newData
        this.cboTinh.value = this.cboTinh.list[0].id
        this.cboTinhCuoi.list = newData
        this.cboTinhCuoi.value = this.cboTinhCuoi.list[0].id
      }
    },
    async GetTinhTC (tinhid) {
      let data = this.GetDataList(await API.TINH_THICONG(this.axios, {'tinhthicong_id': tinhid}))
      let tinh = {}
      if (data.length > 0) {
        let item = data[0]
        tinh.user = item['ten_truycap']
        tinh.pass = item['mat_khau']
        tinh.nhanvien_id = Number(item['nhanvien_id'])
        tinh.donvi_id = Number(item['donvi_id'])
        tinh.matinh = item['matinh']
        tinh.TNS = item['tns_string']
        tinh.tinhthicong_id = Number(item['tinh_id'])
        tinh.mavung = item['ma_vung']
        tinh.ip_dll = item['ip_dll']
      } else {
        tinh.user = ''
        tinh.pass = ''
        tinh.nhanvien_id = 0
        tinh.donvi_id = 0
        tinh.matinh = ''
        tinh.TNS = ''
        tinh.tinhthicong_id = 0
        tinh.ip_dll = ''
      }
      return tinh
    },
    async SP_HIENTHI_TINH_KN () {
      return this.GetDataList(await API.SP_HIENTHI_TINH_KN(this.axios))
    },
    hideModal () {
      this.$refs.popupChonTinhTSL.hide()
    },
    GetDataList: function (response) {
      console.log(response)
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    }
  }
}
</script>

<style scoped>

</style>
