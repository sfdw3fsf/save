<template src="./index.template.html"> </template>

<script>
import api from '../../api'
import { PHANLOAI_CONG } from '../../config.js'

export default {
  name: 'ThongTinCongDieuKhien',
  inject: ['isNumber', 'handlePasteNumber', 'checkPortStatus'],
  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      loaiCongListItems: [],
      mucDichSdCongListItems: [],
      trangThaiPortListItems: [],
      trangThaiKetNoiListItems: [],
      thietBiNoiDenListItems: [],
      diaChiIPListItems: [],
      thongTinCongDieuKhien: {
        id: null,
        loaiCong: null,
        mucDichId: null,
        trangThaiPortId: null,
        trangThaiKetNoiId: null,
        thietBiNoiDenId: null,
        diaChiIP: null,
        thiBiGocId: null
      },
      phanLoai: null,
      previousTrangThaiPortId: null
    }
  },
  methods: {
    clearAll() {
      this.thongTinCongDieuKhien = {
        id: null,
        loaiCong: null,
        mucDichId: null,
        trangThaiPortId: null,
        trangThaiKetNoiId: null,
        thietBiNoiDenId: null,
        diaChiIP: null
      }
    },
    async showDialog() {
      try {
        this.loading(true)
        // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
        if (this.$refs.dialogKhaiBaoCongDieuKhien) {
          this.previousTrangThaiPortId = this.thongTinCongDieuKhien.trangThaiPortId
          this.diaChiIPListItems = await this.getIPAddressList()
          this.loaiCongListItems = await this.getDmLoaiCong()
          this.mucDichSdCongListItems = await this.getDmMucDichSDCong()
          this.trangThaiPortListItems = await this.getDmTrangThaiPort()
          this.trangThaiKetNoiListItems = await this.getDmTrangThaiKN()
          this.thietBiNoiDenListItems = await this.getDmThietBiKN()
          this.$refs.dialogKhaiBaoCongDieuKhien.show() // Hiển thị dialog
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

    closeDialog() {
      this.clearAll()
      this.$refs.dialogKhaiBaoCongDieuKhien.hide()
    },
    // Gọi API lấy danh sách IP Address
    async getIPAddressList() {
      try {
        let result = []
        const rs = await this.$root.context.get('/web-ecms/thong-tin-thiet-bi/0/thiet-bi-ip')
        result = (rs && rs.data ? rs.data : []).map((item) => ({
          ID: item.ID,
          IP: item.IP
        }))
        return result
      } catch (error) {
        console.error('Error loading IP Address:', error)
        return []
      }
    },
    getDmLoaiCong: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'LOAICONG_PHANLOAI',
          thamSo1: PHANLOAI_CONG.CONG_DIEU_KHIEN,
          thamSo2: null
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
    getDmMucDichSDCong: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'IDC-MUCDICH-SD-PHONGZONE',
          thamSo1: null,
          thamSo2: null
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
    getDmThietBiKN: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'TBI_CDN',
          thamSo1: this.thongTinCongDieuKhien.thiBiGocId
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
    //       portId: this.thongTinCongDieuKhien.id,
    //       mucDichSuDung: Number(this.thongTinCongDieuKhien.mucDichId),
    //       trangThaiPort: Number(this.thongTinCongDieuKhien.trangThaiPortId),
    //       trangThaiKetNoi: Number(this.thongTinCongDieuKhien.trangThaiKetNoiId),
    //       thietBiKetNoiId: Number(this.thongTinCongDieuKhien.thietBiNoiDenId),
    //       ip: this.thongTinCongDieuKhien.diaChiIP,
    //       loaiCong: Number(this.thongTinCongDieuKhien.loaiCongId)
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
    //           this.$root.toastSuccess('Cập nhật thông tin cổng điều khiển thành công')
    //           this.clearAll()
    //           this.$refs.dialogKhaiBaoCongDieuKhien.hide()
    //           this.$parent.reLoadListPort()
    //         }
    //       } catch (err) {
    //         console.log('err cap nhat cong', err)
    //         this.$root.toastError('Cập nhật thông tin cổng điều khiển thất bại')
    //       } finally {
    //         this.$root.showLoading(false)
    //       }
    //     }
    //   }
    // },
    onSave: async function() {
      try {
        if (!this.validateControls()) return
        const flag = await this.$bvModal.msgBoxConfirm(
          'Bạn có chắc chắn muốn lưu thông tin cổng điều khiển này không?',
          {
            title: 'Xác nhận',
            size: 'md',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
            centered: true
          }
        )
        if (!flag) return
        // Lấy PORT_ID đang chỉnh sửa
        const portId = this.thongTinCongDieuKhien.id
        const arrPorts = this.$parent.listCongDieuKhien
        // Tìm PORT cần update
        const idxPort = arrPorts.findIndex((x) => x.PORT_ID == portId)

        if (idxPort === -1) {
          this.$root.toastError('Không tìm thấy cổng điều khiển phù hợp để cập nhật')
          return
        }
        // Lấy id, tên các danh mục đã chọn
        const loaiCong = this.loaiCongListItems.find((x) => x.ID == this.thongTinCongDieuKhien.loaiCongId)
        const mucDich = this.mucDichSdCongListItems.find((x) => x.ID == this.thongTinCongDieuKhien.mucDichId)
        const trangThaiPort = this.trangThaiPortListItems.find(
          (x) => x.ID == this.thongTinCongDieuKhien.trangThaiPortId
        )
        const trangThaiKN = this.trangThaiKetNoiListItems.find(
          (x) => x.ID == this.thongTinCongDieuKhien.trangThaiKetNoiId
        )
        const thietBiNoiDen = this.thietBiNoiDenListItems.find(
          (x) => x.ID == this.thongTinCongDieuKhien.thietBiNoiDenId
        )
        const ipInfo = this.diaChiIPListItems.find((x) => x.ID == this.thongTinCongDieuKhien.diaChiIP)

        // CẬP NHẬT GIÁ TRỊ PORT VÀO arr_cong_dulieu
        arrPorts[idxPort] = {
          ...arrPorts[idxPort],
          MUCDICHSD_ID: Number(this.thongTinCongDieuKhien.mucDichId),
          TT_PORT_ID: Number(this.thongTinCongDieuKhien.trangThaiPortId),
          TRANGTHAIKN_ID: Number(this.thongTinCongDieuKhien.trangThaiKetNoiId),
          THIETBIKN_ID: Number(this.thongTinCongDieuKhien.thietBiNoiDenId),
          DIACHI_IP_ID: Number(this.thongTinCongDieuKhien.diaChiIP),
          DIACHI_IP: ipInfo?.IP || null,
          LOAICONG_ID: Number(this.thongTinCongDieuKhien.loaiCongId),
          BANGTHONG_TN: this.thongTinCongDieuKhien.bangThong,
          LOAICONG: loaiCong?.TEN || null,
          MUCDICHSD: mucDich?.TEN || null,
          TRANGTHAI_PORT: trangThaiPort?.TEN || null,
          TRANGTHAI_KN: trangThaiKN?.TEN || null,
          THIETBIKN: thietBiNoiDen?.TEN || null,
          isEdited: true
        }
        this.$root.toastSuccess('Lưu thông tin cổng điều khiển thành công')
        this.closeDialog()
        // Reload grid
        this.$parent.allPorts = [...arrPorts]
        this.$parent.listCongDieuKhien = this.$parent.allPorts.filter(
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
      if (this.thongTinCongDieuKhien.trangThaiPortId == null) {
        msg = 'Trạng thái port không được để trống.'
      }
      if (this.thongTinCongDieuKhien.trangThaiKetNoiId == null) {
        msg = 'Trạng thái kết nối không được để trống.'
      }
      if (this.thongTinCongDieuKhien.diaChiIP == null) {
        msg = 'Địa chỉ IP không được để trống.'
      }
      // if (this.thongTinCongDieuKhien.thietBiNoiDenId == null) {
      //   msg = 'Thiết bị nối đến không được để trống.'
      // }
      if (msg == '') return true

      this.$root.toastError(msg)
      return false
    },
    onChangeTrangThaiPort: function(value) {
      const result = this.checkPortStatus(
        this.thongTinCongDieuKhien.id,
        value,
        this.thongTinCongDieuKhien.trangThaiKetNoiId
      )
      if (!result.isValid) {
        this.$root.toastError(result.message)
        this.$nextTick(() => {
          this.thongTinCongDieuKhien.trangThaiPortId = this.previousTrangThaiPortId
        })
      } else {
        this.previousTrangThaiPortId = value
        this.thongTinCongDieuKhien.trangThaiPortId = value
      }
    }
  },
  created: async function() {}
}
</script>
