<template src="./index.template.html"> </template>

<script>
import api from '../../api'

export default {
  name: 'ThongTinCongNguon',
  inject: ['isNumber', 'handlePasteNumber', 'checkPortStatus'],
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      loaiCongListItems: [],
      listChuanDauNoiItems: [],
      trangThaiPortListItems: [],
      trangThaiKetNoiListItems: [],
      thietBiNoiDenListItems: [],
      listLoaiNguonDienItems: [],
      thongTinCong: {
        id: null,
        dong_dinh_danh: 0,
        dien_ap_dinh_danh: 0,
        loaiNguonDienId: null,
        chuanDauNoiId: null,
        trangThaiPortId: null,
        trangThaiKetNoiId: null,
        thietBiNoiDenId: null,
        thiBiGocId: null
      },
      phanLoai: null,
      previousTrangThaiPortId: null
    }
  },
  methods: {
    async showDialogCongNguon() {
      try {
        this.loading(true)
        // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
        if (this.$refs.dialogKhaiBaoCongNguon) {
          this.previousTrangThaiPortId = this.thongTinCong.trangThaiPortId
          this.listLoaiNguonDienItems = await this.getDmLoaiCong()
          this.trangThaiPortListItems = await this.getDmTrangThaiPort()
          this.trangThaiKetNoiListItems = await this.getDmTrangThaiKN()
          this.listChuanDauNoiItems = await this.getDmChuanDauNoi()
          this.thietBiNoiDenListItems = await this.getDmThietBiKN()
          this.$refs.dialogKhaiBaoCongNguon.show() // Hiển thị dialog
        } else {
          this.$toast.error('Dialog không tồn tại hoặc không được tham chiếu đúng')
        }
      } catch (e) {
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi khi mở dialog')
        }
      } finally {
        this.loading(false) // Tắt trạng thái loading
      }
    },
    getDmThietBiKN: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'TBI_CDN',
          thamSo1: this.thongTinCong.thiBiGocId
        })
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
    getDmTrangThaiKN: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'TRANGTHAI_KN'
        })
        result = rs.data
        console.log('rs trang thai port', rs)
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    getDmChuanDauNoi: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'CHUAN_DAUNOI'
        })
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
    clearAll() {
      this.thongTinCong = {
        id: null,
        dong_dinh_danh: 0,
        dien_ap_dinh_danh: 0,
        loaiNguonDienId: null,
        chuanDauNoiId: null,
        trangThaiPortId: null,
        trangThaiKetNoiId: null,
        thietBiNoiDenId: null
      }
    },
    closeDialog() {
      this.clearAll()
      this.$refs.dialogKhaiBaoCongNguon.hide()
    },
    getDmTrangThaiPort: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'TRANGTHAI_PORT'
        })
        result = rs.data
        console.log('rs trang thai port', rs)
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    getDmLoaiCong: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'IDC-HINHTHUC-BY-LOAIKN',
          thamSo1: 1
        })
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
    // capNhatCong: async function() {
    //   let result = null
    //   try {
    //     this.loading(true)
    //     const params = {
    //       portId: this.thongTinCong.id,
    //       dongDinhDanh: Number(this.thongTinCong.dong_dinh_danh),
    //       trangThaiPort: Number(this.thongTinCong.trangThaiPortId),
    //       trangThaiKetNoi: Number(this.thongTinCong.trangThaiKetNoiId),
    //       thietBiKetNoiId: Number(this.thongTinCong.thietBiNoiDenId),
    //       dienApDinhDanh: this.thongTinCong.dien_ap_dinh_danh,
    //       loaiNguonDien: this.thongTinCong.loaiNguonDienId,
    //       chuanDauNoi: Number(this.thongTinCong.chuanDauNoiId)
    //     }
    //     const response = await api.updatePort(this.$root.context, params)
    //     result = response || []
    //   } catch (e) {
    //     if (e.response?.data?.message_detail) {
    //       this.$root.toastError('Có lỗi xảy ra trong quá trình thực hiện: ' + e.response.data.message_detail)
    //     }
    //   } finally {
    //     this.loading(false)
    //   }
    //   return result
    // },
    // onSave: async function() {
    //   if (this.validateControls()) {
    //     let flag = await this.$confirm('Bạn có chắc chắn muốn lưu thông tin này không ?', 'Xác nhận', {
    //       confirmButtonText: 'Đồng ý',
    //       cancelButtonText: 'Hủy',
    //       type: 'info',
    //       dangerouslyUseHTMLString: true
    //     })
    //       .then((s) => true)
    //       .catch((e) => {
    //         return false
    //       })
    //     if (flag) {
    //       try {
    //         this.$root.showLoading(true)
    //         let rs = await this.capNhatCong()
    //         console.log('rs cap nhat cong', rs)
    //         if (rs && rs.error_code === 'BSS-00000000') {
    //           this.$root.toastSuccess('Cập nhật thông tin cổng nguồn thành công')
    //           this.clearAll()
    //           this.$refs.dialogKhaiBaoCongNguon.hide()
    //           this.$parent.reLoadListPort()
    //         }
    //       } catch (err) {
    //         console.log('err cap nhat cong', err)
    //         this.$root.toastError('Cập nhật thông tin cổng nguồn thất bại')
    //       } finally {
    //         // this.$refs.dialogKhaiBaoCongInterface.hide()
    //         this.$root.showLoading(false)
    //       }
    //     }
    //   }
    // },
    onSave: async function() {
      try {
        if (!this.validateControls()) return
        const flag = await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn lưu thông tin cổng nguồn này không?', {
          title: 'Xác nhận',
          size: 'md',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
          centered: true
        })
        if (!flag) return
        // Lấy PORT_ID đang chỉnh sửa
        const portId = this.thongTinCong.id
        const arrPorts = this.$parent.listCongNguon
        // Tìm PORT cần update
        const idxPort = arrPorts.findIndex((x) => x.PORT_ID == portId)

        if (idxPort === -1) {
          this.$root.toastError('Không tìm thấy cổng nguồn phù hợp để cập nhật')
          return
        }
        // Lấy id, tên các danh mục đã chọn
        const loaiNguonDienTen = this.listLoaiNguonDienItems.find((x) => x.ID == this.thongTinCong.loaiNguonDienId)
        const trangThaiPort = this.trangThaiPortListItems.find((x) => x.ID == this.thongTinCong.trangThaiPortId)
        const trangThaiKN = this.trangThaiKetNoiListItems.find((x) => x.ID == this.thongTinCong.trangThaiKetNoiId)
        const thietBiNoiDen = this.thietBiNoiDenListItems.find((x) => x.ID == this.thongTinCong.thietBiNoiDenId)
        const chuanDauNoi = this.listChuanDauNoiItems.find((x) => x.ID == this.thongTinCong.chuanDauNoiId)
        // CẬP NHẬT GIÁ TRỊ PORT VÀO arr_cong_dulieu
        arrPorts[idxPort] = {
          ...arrPorts[idxPort],
          TT_PORT_ID: Number(this.thongTinCong.trangThaiPortId),
          TRANGTHAIKN_ID: Number(this.thongTinCong.trangThaiKetNoiId),
          THIETBIKN_ID: Number(this.thongTinCong.thietBiNoiDenId),
          TRANGTHAI_PORT: trangThaiPort?.TEN || null,
          TRANGTHAI_KN: trangThaiKN?.TEN || null,
          THIETBIKN: thietBiNoiDen?.TEN || null,
          LOAI_NDIEN_ID: Number(this.thongTinCong.loaiNguonDienId) || null,
          DA_DINHDANH: this.thongTinCong.dien_ap_dinh_danh || null,
          DD_DINHDANH: this.thongTinCong.dong_dinh_danh || null,
          CHUANDN_ID: Number(this.thongTinCong.chuanDauNoiId) || null,
          LOAI_NDIEN: loaiNguonDienTen?.TEN || null,
          CHUAN_DN: chuanDauNoi?.TEN || null,
          isEdited: true
        }
        this.$root.toastSuccess('Lưu thông tin cổng nguồn thành công')
        this.closeDialog()
        // Reload grid
        this.$parent.allPorts = [...arrPorts]
        this.$parent.listCongNguon = this.$parent.allPorts.filter(
          (x) =>
            x.THIETBI_ID == this.$parent.currentItem.id || x.THIETBI_ID == Number(this.$parent.dataKheCam.THIETBIKN_ID)
        )
      } catch (err) {
        console.error('onSave error:', err)
        this.$root.toastError('Có lỗi xảy ra trong quá trình lưu dữ liệu')
      }
    },
    validateControls: function() {
      let msg = ''
      if (this.thongTinCong.trangThaiPortId == null) {
        msg = 'Trạng thái port không được để trống.'
      }
      if (this.thongTinCong.trangThaiKetNoiId == null) {
        msg = 'Trạng thái kết nối không được để trống.'
      }
      if (this.thongTinCong.chuanDauNoiId == null) {
        msg = 'Địa chỉ IP không được để trống.'
      }
      // if (this.thongTinCong.thietBiNoiDenId == null) {
      //   msg = 'Thiết bị nối đến không được để trống.'
      // }
      if (msg == '') return true

      this.$root.toastError(msg)
      return false
    },
    onChangeTrangThaiPort: function(value) {
      const result = this.checkPortStatus(this.thongTinCong.id, value, this.thongTinCong.trangThaiKetNoiId)
      console.log('result', result)
      if (!result.isValid) {
        this.$root.toastError(result.message)
        this.$nextTick(() => {
          this.thongTinCong.trangThaiPortId = this.previousTrangThaiPortId
        })
      } else {
        this.previousTrangThaiPortId = value
        this.thongTinCong.trangThaiPortId = value
      }
    }
  }
}
</script>
