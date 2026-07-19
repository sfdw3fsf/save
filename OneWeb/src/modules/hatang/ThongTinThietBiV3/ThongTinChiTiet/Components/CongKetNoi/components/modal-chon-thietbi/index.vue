<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import api from '../../api'

export default {
  name: 'ModalChonThietBi',
  components: {
    BssErrorMarker,
    BssRequiredMarker
  },
  data: function() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      item: null,
      idcList: [],
      ds_thietbi: [],
      searchForm: {
        idcId: 0,
        trangThaiRack: 0,
        trangthai_vh: 0,
        mangTB: 0,
        heThong: 0,
        loaiTB: 0,
        tenKhachHang: null,
        maThueBao: null,
        maHopDong: null,
        loaiRack: 0
      },
      loaiTBList: [],
      heThongList: [],
      mangTBList: [],
      enabledIDC: false,
      tab_index: 1,
      trangThaiVHList: [],
      trangThaiRackList: [],
      loaiRackList: [],
      ds_Rack: [],
      phanLoai: null,
      thongTinKheCam: null
    }
  },
  created: async function() {
    // await this.loadDanhMuc()
  },
  validations: {},
  watch: {},
  computed: {},
  methods: {
    closeModal: function() {
      this.$refs.ModalChonThietBi.hide()
    },
    show: async function(data, phanLoai) {
      this.phanLoai = phanLoai
      this.thongTinKheCam = data
      await this.loadDSThietBi()
      this.$refs.ModalChonThietBi.show()
    },
    // onChonThietBi: function() {
    //   this.$confirm('Bạn có chắc chắn muốn chọn thiết bị này không? ', 'Xác nhận', {
    //     confirmButtonText: 'Đồng ý',
    //     cancelButtonText: 'Huỷ'
    //   }).then(async () => {
    //     try {
    //       this.$root.showLoading(true)
    //       let response = await this.insertCardInLine()
    //       if (response && response.error_code === 'BSS-00000000') {
    //         this.$toast.success('Thêm card vào khe cắm thành công')
    //         await this.$parent.reLoadListKheCam()
    //       } else {
    //         this.$toast.error(response.message_detail)
    //       }
    //     } finally {
    //       this.$root.showLoading(false)
    //     }
    //     this.closeModal()
    //   })
    // },
    onChonThietBi: function() {
      this.$confirm('Bạn có chắc chắn muốn chọn thiết bị này không?', 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Huỷ'
      }).then(() => {
        try {
          if (!this.currentItem || !this.thongTinKheCam) {
            this.$toast.error('Không tìm thấy thông tin thiết bị hoặc khe cắm')
            return
          }
          // Tìm vị trí khe cắm trong component cha
          const arr =
            this.phanLoai === 1
              ? this.$parent.listLineCard
              : this.phanLoai === 2
              ? this.$parent.listPowerCard
              : this.phanLoai === 3
              ? this.$parent.listControlCard
              : []
          const idx = arr.findIndex((x) => x.PORT_ID == this.thongTinKheCam.PORT_ID)
          if (idx === -1) {
            this.$toast.error('Không tìm thấy khe cắm cần update!')
            return
          }
          // Cập nhật thông tin thiết bị vào line-card
          arr[idx] = {
            ...arr[idx],
            THIETBIKN_ID: this.currentItem.ID, // gắn ID thiết bị được chọn vào trường TB kết nối
            THIETBIKN: this.currentItem.TEN,
            SR_TB_KN: this.currentItem.SERIAL_NUMBER,
            IDC_TB_KN: this.currentItem.IDC_TEN,
            TOANHA_TB_KN: this.currentItem.TOANHA_TEN,
            RACK_TB_KN: this.currentItem.RACK_TEN,
            TTVH_TB_KN: this.currentItem.TRANGTHAI_VH,
            CQ_TB_KN: this.currentItem.SO_HUU,
            isEdited: true
          }
          // Lưu lại vào arr_line_card tổng
          if (this.phanLoai === 1) {
            this.$parent.currentItem.arr_line_card.ports = arr
            this.$parent.listLineCard = [...arr]
          } else if (this.phanLoai === 2) {
            this.$parent.currentItem.arr_power_card.ports = arr
            this.$parent.listPowerCard = [...arr]
          } else if (this.phanLoai === 3) {
            this.$parent.currentItem.arr_control_card.ports = arr
            this.$parent.listControlCard = [...arr]
          }
          this.$toast.success('Chọn thiết bị thành công!')
          this.closeModal()
        } catch (err) {
          console.error(err)
          this.$toast.error('Có lỗi xảy ra khi chọn thiết bị!')
        }
      })
    },
    onSelectedRowChanged: function(event) {
      this.currentItem = event || null
    },
    getDSThietBi: async function() {
      let result = []
      try {
        const params = { phanLoai: this.phanLoai }
        const rs = await api.getListThietBi(this.axios, params)
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    loadDSThietBi: async function() {
      try {
        this.$root.showLoading(true)
        let response = await this.getDSThietBi()
        console.log('response', response)
        if (response && response.error_code === 'BSS-00000000') {
          this.ds_thietbi = response.data || []
        } else {
          this.ds_thietbi = []
          this.$toast.error(response.message_detail)
        }
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    insertCardInLine: async function() {
      let result = []
      try {
        const params = { portId: this.thongTinKheCam.PORT_ID, thietBiKetNoiId: this.currentItem.ID }
        const rs = await api.insertThietBi(this.axios, params)
        result = rs
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    }
  },
  mounted: function() {}
}
</script>
<style>
.popup-box {
  max-height: 80vh !important;
  overflow-y: auto;
}
</style>
