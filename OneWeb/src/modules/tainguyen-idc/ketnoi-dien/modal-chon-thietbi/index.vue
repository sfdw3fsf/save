<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import api from '../api'
import { LOAI_DANH_MUC, trang_thai_kn_vl_options, trang_thai_kn_vh_options } from '../constant.js'
export default {
  name: 'ModalChonThietBi',
  props: { modalID: null, loaicv: 0, isKhachHang: false, phieucv: null },
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
      type: null, // 1: nguồn, 2: đích
      tab_index: 1,
      trangThaiVHList: [],
      trangThaiRackList: [],
      loaiRackList: [],
      ds_Rack: [],
      typeHeThong: null // 1: hệ thống nguồn, 2: hệ thống đích
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
      this.$refs.ModalChonThietBi.hide(this.modalID)
    },
    show: async function(data, type, typeHeThong) {
      this.tab_index = 1
      this.onClearSearch()
      this.searchForm.idcId = data?.idc || 0
      //     this.searchForm.maThueBao = maThueBao || null
      this.enabledIDC = data?.idc > 0 ? true : false
      this.type = type
      this.typeHeThong = typeHeThong || null
      await this.loadDanhMuc()
      await this.onSearch()
      this.$refs.ModalChonThietBi.show()
    },
    onChonThietBi: function() {
      this.$confirm('Bạn có chắc chắn muốn chọn thiết bị này? ', 'Xác nhận', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Huỷ'
      }).then(async () => {
        this.$emit('chonThietBi', this.currentItem)
        this.closeModal()
      })
    },
    async loadDanhMuc() {
      try {
        this.idcList = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.IDC_BY_DONVI, this.$root.token.getDonViID())
        this.idcList = [{ id: 0, text: '-- Tất cả --' }, ...this.idcList]
        this.mangTBList = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.IDC_MANG_TBI)
        this.mangTBList = [{ id: 0, text: '-- Tất cả --' }, ...this.mangTBList]
        this.heThongList = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.IDC_HE_THONG, this.$root.token.getDonViID())
        this.heThongList = [{ id: 0, text: '-- Tất cả --' }, ...this.heThongList]
        if (this.searchForm.mangTB > 0) {
          await this.getLoaiThietBi()
        }
        this.loaiRackList = await this.getLoaiRackItems()
        this.loaiRackList = [{ ID: 0, TEN: '-- Tất cả --' }, ...this.loaiRackList]
        this.trangThaiRackList = await this.getCCDVItems()
        this.trangThaiRackList = [{ TRANGTHAI_ID: 0, TEN: '-- Tất cả --' }, ...this.trangThaiRackList]
        this.trangThaiVHList = await this.getVanHanhItems()
        if (this.typeHeThong == 1) {
          this.trangThaiVHList = this.trangThaiVHList.filter((x) => x.TTVH_ID === 4) // nếu là hệ thống nguồn =>lấy ra ds rack có trạng thái vận hành là đang hoạt động.
          this.searchForm.trangthai_vh = 4
        } else if (this.typeHeThong == 2) {
          this.trangThaiVHList = this.trangThaiVHList.filter(
            (x) => x.TTVH_ID === 1 || x.TTVH_ID === 2 || x.TTVH_ID === 3
          ) // nếu là hệ thống đích =>lấy ra ds rack có trạng thái vận hành là  chưa sử dụng, chư kết nối điện/mạng, chưa kết nối điện
          this.trangThaiVHList = [{ TTVH_ID: 0, TRANGTHAI_VH: '-- Tất cả --' }, ...this.trangThaiVHList]
        }
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
      this.currentItem = event || null
    },
    onTimKiem: function() {
      if (this.tab_index === 1) {
        this.onSearch()
      } else {
        this.onSearchRack()
      }
    },
    // tìm kiếm thiết bị
    async onSearch() {
      try {
        this.loading(true)
        let params = {
          idIdc: Number(this.searchForm.idcId) > 0 ? Number(this.searchForm.idcId) : null,
          mangThietBi: Number(this.searchForm.mangTB) > 0 ? Number(this.searchForm.mangTB) : null,
          loaiThietBi: Number(this.searchForm.loaiTB) > 0 ? Number(this.searchForm.loaiTB) : null,
          phieucv: this.phieucv,
          tenKhachHang: this.searchForm.tenKhachHang ? this.searchForm.tenKhachHang.trim() : null,
          maThueBao: this.searchForm.maThueBao ? this.searchForm.maThueBao.trim() : null,
          maHD: this.searchForm.maHopDong ? this.searchForm.maHopDong.trim() : null,
          idDonVi: Number(this.$root.token.getDonViID()),
          heThongTbi: Number(this.searchForm.heThong) > 0 ? Number(this.searchForm.heThong) : null,
          loaicv: this.loaicv,
          type: this.type, // nếu là khách hàng thì truyền thêm type để lấy đúng ds thiết bị nguồn/đích; nếu là IDC (type = null) thì ds auto lấy đủ
          ispatchpannel: 0
        }
        let response = await this.getDSThietBi(params)
        if (response.data.error_code === 'BSS-00000000') {
          this.ds_thietbi = response.data.data
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
    // tìm kiếm rack
    async onSearchRack() {
      try {
        this.loading(true)
        let params = {
          idIdc: Number(this.searchForm.idcId) > 0 ? Number(this.searchForm.idcId) : null,
          loaiRack: Number(this.searchForm.loaiRack) > 0 ? Number(this.searchForm.loaiRack) : null,
          trangthaiVh: Number(this.searchForm.trangthai_vh) > 0 ? Number(this.searchForm.trangthai_vh) : null,
          trangthaiCcdv: Number(this.searchForm.trangThaiRack) > 0 ? Number(this.searchForm.trangThaiRack) : null,
          heThong: Number(this.typeHeThong), // 1: hệ thống nguồn, 2: hệ thống đích
          phieucv: this.phieucv,
          // isKhachHang: this.isKhachHang,
          tenKhachHang: this.searchForm.tenKhachHang ? this.searchForm.tenKhachHang.trim() : null,
          maThueBao: this.searchForm.maThueBao ? this.searchForm.maThueBao.trim() : null,
          maHD: this.searchForm.maHopDong ? this.searchForm.maHopDong.trim() : null,
          // loai cv get from props
          loaicv: this.loaicv
          // idDonVi: Number(this.$root.token.getDonViID()),
          // type: this.type // nếu là khách hàng thì truyền thêm type để lấy đúng ds thiết bị nguồn/đích; nếu là IDC (type = null) thì ds auto lấy đủ
        }
        let response = await this.getDSRack(params)
        if (response.data.error_code === 'BSS-00000000') {
          this.ds_Rack = response.data.data
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
      console.log('data', data)
      const rs = await api.seachDSKetNoi(this.axios, data)
      return rs.data
    },
    async getDSThietBi(data) {
      const result = await api.getDSThietBi(this.axios, data)
      return result
    },
    async getDSRack(data) {
      const result = await api.getDSRack(this.axios, data)
      return result
    },
    getLoaiThietBi: async function() {
      if (this.searchForm.mangTB > 0) {
        this.loaiTBList = await this.onLoadDanhMucWithParams(LOAI_DANH_MUC.IDC_LOAI_TBI, this.searchForm.mangTB)
        this.loaiTBList = [{ id: 0, text: '-- Tất cả --' }, ...this.loaiTBList]
        this.searchForm.loaiTB = 0
      } else {
        this.loaiTBList = []
        this.searchForm.loaiTB = null
      }
    },
    onChangeMangTB: async function() {
      await this.getLoaiThietBi()
    },
    onChangeTab: async function(index) {
      this.tab_index = index
      if (index === 1) {
        this.onClearSearch()
        await this.onSearch()
      } else {
        this.onClearSearch()
        this.onSearchRack()
      }
    },
    getLoaiRackItems: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-danhmuc-rack', {
          loaiDanhMuc: 'LOAIRACK',
          thamSo1: null
        })
        result = rs.data || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getCCDVItems: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-danhmuc-rack', {
          loaiDanhMuc: 'CCDV',
          thamSo1: null
        })
        result = rs.data || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    getVanHanhItems: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-danhmuc-rack', {
          loaiDanhMuc: 'VANHANH',
          thamSo1: null
        })
        result = rs.data || []
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail)
        }
      }
      return result
    },
    onClearSearch: function() {
      this.searchForm = {
        idcId: this.enabledIDC && this.searchForm.idcId > 0 ? this.searchForm.idcId : 0,
        trangThaiRack: 0,
        trangthai_vh: this.typeHeThong == 1 ? 4 : 0,
        mangTB: 0,
        heThong: 0,
        loaiTB: 0,
        tenKhachHang: null,
        maThueBao: this.searchForm.maThueBao,
        maHopDong: null,
        loaiRack: 0
      }
      this.loaiTBList = []
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
