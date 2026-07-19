<template src="./edit-soluong-ocung-modal.html"></template>
<script>
import danhMucApi from '../../../../../api/DanhMuc.api'

export default {
  data() {
    return {
      dsDmTrangThaiVH: [],
      dsDmLoaiOCung: [],
      currentOCung: {}
    }
  },
  methods: {
    openModal: async function(data) {
      this.currentOCung = data
      await this.$refs.modalEditOCung.show()
      await this.apiDmTrangThaiVH()
      await this.apiDmLoaiOCung()
    },
    closeModal() {
      this.$refs.modalEditOCung.hide()
    },
    onSave: async function() {
      if (!this.validate()) return
      this.$parent.localDsOCung = this.$parent.localDsOCung.map((oCung) => {
        if (oCung.STT === this.currentOCung.STT) {
          return { ...oCung, ...this.currentOCung }
        }
        return oCung
      })
      await this.$parent.loadThongTinDaCam()
      this.closeModal()
    },
    apiDmTrangThaiVH: async function() {
      try {
        this.$root.showLoading(true)
        const response = await danhMucApi.getTrangThaiVH(this.$root.context)
        this.dsDmTrangThaiVH = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu trạng thái vận hành')
      } finally {
        this.$root.showLoading(false)
      }
    },
    apiDmLoaiOCung: async function() {
      try {
        this.$root.showLoading(true)
        const response = await danhMucApi.getLoaiOCung(this.$root.context)
        this.dsDmLoaiOCung = response || []
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi tải dữ liệu loại ổ cứng')
      } finally {
        this.$root.showLoading(false)
      }
    },
    validate() {
      let msg = ''
      if (!this.currentOCung.TEN_VITRI || this.currentOCung.TEN_VITRI.trim() == '') {
        msg = 'Tên khe không được để trống.'
      } else if (!this.currentOCung.TEN_OCUNG || this.currentOCung.TEN_OCUNG.trim() == '') {
        msg = 'Tên ổ cứng không được để trống.'
      } else if (!this.currentOCung.KYHIEU || this.currentOCung.KYHIEU.trim() == '') {
        msg = 'Ký hiệu không được để trống.'
      } else if (isNaN(this.currentOCung.LOAIOCUNG_ID) || Number(this.currentOCung.LOAIOCUNG_ID) <= 0) {
        msg = 'Loại ổ cứng không được để trống.'
      } else if (isNaN(this.currentOCung.TOCDO) || Number(this.currentOCung.TOCDO) <= 0) {
        msg = 'Tốc độ là số lớn hơn 0.'
      } else if (isNaN(this.currentOCung.KICHTHUOC) || Number(this.currentOCung.KICHTHUOC) <= 0) {
        msg = 'Kích thước không được để trống.'
      } else if (isNaN(this.currentOCung.DUNGLUONG) || Number(this.currentOCung.DUNGLUONG) <= 0) {
        msg = 'Dung lượng là số lớn hơn 0.'
      } else if (!this.currentOCung.PART_NUMBER || this.currentOCung.PART_NUMBER.trim() == '') {
        msg = 'Part Number không được để trống.'
      } else if (!this.currentOCung.TRANGTHAIVH_ID) {
        msg = 'Trạng thái không được để trống.'
      }

      if (msg == '') return true
      this.$root.toastError(msg)
      return false
    },
    loaiOCungChanged: function(id) {
      const loai = this.dsDmLoaiOCung.find((item) => item.ID == id)
      this.currentOCung.LOAIOCUNG = loai ? loai.TEN : ''
    },
    loaiTTVHChanged: function(id) {
      const tt = this.dsDmTrangThaiVH.find((item) => item.ID == id)
      this.currentOCung.TRANGTHAI_VH = tt ? tt.TEN : ''
    },
  }
}
</script>
