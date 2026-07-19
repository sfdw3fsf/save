<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import api from '../api'
import {
  LOAI_DANH_MUC,
  LOAI_KETNOI,
  TRANG_THAI_PHIEU,
  port_type_options,
  trang_thai_kn_vl_options,
  trang_thai_kn_vh_options
} from '../constant.js'
export default {
  name: 'ModalChonKetNoi',
  props: { modalID: null, loaicv: 0, isKhachHang: false },
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
      ds_ketnoi: [],
      searchForm: {
        idcId: 0,
        trangthai_vl: -1,
        trangthai_vh: -1,
        maTB: null
      },
      trang_thai_kn_vl_options: [{ id: -1, text: '-- Tất cả --' }, ...trang_thai_kn_vl_options],
      trang_thai_kn_vh_options: [{ id: -1, text: '-- Tất cả --' }, ...trang_thai_kn_vh_options],
      ketNoiSelecteds: []
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
      this.$refs.ModalChonKetNoi.hide(this.modalID)
    },
    show: async function (data) {
      this.onClearSearch()
      await this.loadDanhMuc()
      this.ds_ketnoi = data || []
      if (this.ds_ketnoi.length > 0) {
        this.ds_ketnoi = this.ds_ketnoi.map((item) => {
          return {
            ...item,
            // TRANGTHAI_VL_TEN: item.TRANGTHAI_VL_ID === 1 ? 'Đã kết nối' : 'Đã loại bỏ',
            // TRANGTHAI_VH_TEN: item.TRANGTHAI_VH_ID === 1 ? 'Up' : 'Down',
            F_KETNOI_ID:
              this.loaicv == 30 || this.loaicv == 65 || this.loaicv == 66
                ? this.isKhachHang
                  ? `KND_KH_${item.KETNOI_ID}`
                  : `KND_IDC_${item.KETNOI_ID}`
                : this.isKhachHang
                ? `KNM_KH_${item.KETNOI_ID}`
                : `KNM_IDC_${item.KETNOI_ID}` // thêm tiền tố để phân biệt kết nối điện và mạng của IDC và khách hàng
          }
        })
      }
      this.$refs.ModalChonKetNoi.show()
    },
    onChonKetNoi: function() {
      if (this.ketNoiSelecteds.length == 0) {
        this.$toast.error('Vui lòng chọn kết nối')
        return
      } else {
        this.$refs.ModalChonKetNoi.hide()
        this.$emit('chonKetNoi', this.ketNoiSelecteds)
      }
      // let msg =
      //   this.loaicv == 65 || this.loaicv == 68
      //     ? 'tháo gỡ'
      //     : this.loaicv == 66 || this.loaicv == 67
      //     ? 'thay đổi thông tin'
      //     : ''
      // this.$confirm('Bạn có chắc chắn muốn thực hiện  ' + msg + ' kết nối này? ', 'Xác nhận', {
      //   confirmButtonText: 'Xác nhận',
      //   cancelButtonText: 'Huỷ bỏ'
      // }).then(async () => {
      //   this.$emit('chonKetNoi', this.currentItem)
      //   this.closeModal()
      // })
    },
    async loadDanhMuc() {
      try {
        this.idcList = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.ALL_IDC)
        this.idcList = [
          { id: 0, text: '-- Tất cả --' },
          ...this.idcList.map((item) => ({
            id: item.DONVI_ID,
            text: item.TEN_DV
          }))
        ]
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi lấy danh mục')
      }
    },
    onLoadDanhMucWithParams: async function(ten, thamSo1, thamSo2) {
      const response = await api.getDanhMuc(this.axios, { loaiDanhMuc: ten, thamSo1: thamSo1, thamSo2: thamSo2 })
      if (response && response.data && response.data.error == 200 && response.data.data) {
        return response.data.data.map((item) => ({
          id: item.ID,
          text: item.TEN,
          ...item
        }))
      }
      return []
    },
    onSelectedRowChanged: function(event) {
      this.ketNoiSelecteds = this.$refs.grdItems.getSelectedRecords() || []
    },
    async onSearch() {
      try {
        this.loading(true)
        // this.searchForm.trangthai_vl = this.loaicv == 65 || this.loaicv == 68 ? 1 : this.searchForm.trangthai_vl
        // this.searchForm.trangthai_vh = this.loaicv == 65 || this.loaicv == 68 ? 0 : this.searchForm.trangthai_vh
        let params = {
          donViId: Number(this.searchForm.idcId) > 0 ? Number(this.searchForm.idcId) : null,
          trangThaiVlId: Number(this.searchForm.trangthai_vl) != -1 ? Number(this.searchForm.trangthai_vl) : null,
          trangThaiVhId: Number(this.searchForm.trangthai_vh) != -1 ? Number(this.searchForm.trangthai_vh) : null,
          idLoaiCv: this.loaicv == 65 || this.loaicv == 66 ? 30 : this.loaicv == 67 || this.loaicv == 68 ? 56 : null,
          maTb: this.searchForm.maTB ? this.searchForm.maTB.trim() : null,
          phuongThuc: (this.loaicv == 65 || this.loaicv == 68) ? 'thaogo' : (this.loaicv == 66 || this.loaicv == 67) ? 'thaydoi' : null
          // trangThaiThiCongKn: 24
        }
        console.log('params', params)
        let response = await this.seachDSKetNoi(params)
        if (response.error_code === 'BSS-00000000') {
          this.ds_ketnoi = response.data
          this.ds_ketnoi = this.ds_ketnoi.map((item, index) => ({
            ...item,
            F_KETNOI_ID:
              this.loaicv == 30 || this.loaicv == 65 || this.loaicv == 66
                ? this.isKhachHang
                  ? `KND_KH_${item.KETNOI_ID}`
                  : `KND_IDC_${item.KETNOI_ID}`
                : this.isKhachHang
                ? `KNM_KH_${item.KETNOI_ID}`
                : `KNM_IDC_${item.KETNOI_ID}` // thêm tiền tố để phân biệt kết nối điện và mạng của IDC và khách hàng
          }))
        } else {
          this.$toast.error(response?.data?.message_detail)
        }
      } catch (error) {
        console.log(' error', error)
        this.$toast.error(error?.data?.message_detail)
      } finally {
        this.loading(false)
      }
    },
    seachDSKetNoi: async function(data) {
      const rs = await api.seachDSKetNoi(this.axios, data)
      return rs.data
    },
    mapToTrangThaiVL: function(field, data, column) {
      if (field === 'TRANGTHAI_VL_ID') {
        const item = trang_thai_kn_vl_options.find((x) => x.id === data.TRANGTHAI_VL_ID)
        return item ? item.text : ''
      }
      return data[field]
    },
    mapToTrangThaiVH: function(field, data, column) {
      if (field === 'TRANGTHAI_VH_ID') {
        const item = trang_thai_kn_vh_options.find((x) => x.id === data.TRANGTHAI_VH_ID)
        return item ? item.text : ''
      }
      return data[field]
    },
    onClearSearch: function() {
      this.searchForm={
        idcId: 0,
        trangthai_vl: -1,
        trangthai_vh: -1,
        maTB: null
      }
      this.ds_ketnoi = []
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
