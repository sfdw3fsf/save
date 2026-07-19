<template src="./index.template.html"> </template>

<script>
import api from '../../api'
import { PHANLOAI_CONG } from '../../config.js'
export default {
  name: 'ThongTinCongDuLieu',
  inject: ['isNumber', 'handlePasteNumber', 'checkPortStatus'],

  data() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      loaiCongListItems: [],
      bangThongListItems: [],
      thongTinModuleListItems: [],
      ketNoiListItems: [],
      mucDichSdCongListItems: [],
      trangThaiPortListItems: [],
      trangThaiKetNoiListItems: [],
      thietBiNoiDenListItems: [],
      diaChiIPListItems: [],
      interfacePortType: {
        id: null,
        loaiCong: null,
        mucDichId: null,
        bangThong: null,
        thongTinModuleId: null,
        ketNoiId: null,
        trangThaiPortId: null,
        trangThaiKetNoiId: null,
        thietBiNoiDenId: null,
        diaChiIP: null,
        thietBiGocId: null
      },
      phanLoai: null,
      previousTrangThaiPortId: null
    }
  },
  methods: {
    clearAll() {
      this.interfacePortType = {
        id: null,
        loaiCong: null,
        mucDichId: null,
        bangThong: null,
        thongTinModuleId: null,
        ketNoiId: null,
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
        if (this.$refs.dialogKhaiBaoCongInterface) {
          this.loaiCongListItems = await this.getDmLoaiCong()
          this.thongTinModuleListItems = await this.getDmThongTinModule()
          this.trangThaiKetNoiListItems = await this.getDmTrangThaiKN()
          this.mucDichSdCongListItems = await this.getDmMucDichSDCong()
          this.trangThaiPortListItems = await this.getDmTrangThaiPort()
          this.thietBiNoiDenListItems = await this.getDmThietBiKN()
          this.diaChiIPListItems = await this.getIPAddressList()
          this.previousTrangThaiPortId = this.interfacePortType.trangThaiPortId
          this.$refs.dialogKhaiBaoCongInterface.show()
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
        this.loading(false)
      }
    },
    closeDialog() {
      this.clearAll()
      this.$refs.dialogKhaiBaoCongInterface.hide()
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
          thamSo1: PHANLOAI_CONG.CONG_DU_LIEU,
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
    getDmThongTinModule: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/danh-muc/common', {
          loaiDanhMuc: 'IDC-THONGTIN-MODULE',
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
          thamSo1: this.interfacePortType.thietBiGocId
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
    //       portId: this.interfacePortType.id,
    //       mucDichSuDung: Number(this.interfacePortType.mucDichId),
    //       trangThaiPort: Number(this.interfacePortType.trangThaiPortId),
    //       trangThaiKetNoi: Number(this.interfacePortType.trangThaiKetNoiId),
    //       thietBiKetNoiId: Number(this.interfacePortType.thietBiNoiDenId),
    //       ip: this.interfacePortType.diaChiIP,
    //       bangThong: this.interfacePortType.bangThong,
    //       module: Number(this.interfacePortType.thongTinModuleId),
    //       loaiCong: Number(this.interfacePortType.loaiCongId)
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
    //           this.$root.toastSuccess('Cập nhật thông tin cổng dữ liệu thành công')
    //           this.clearAll()
    //           this.$refs.dialogKhaiBaoCongInterface.hide()
    //           this.$parent.reLoadListPort()
    //         }
    //       } catch (err) {
    //         console.log('err cap nhat cong', err)
    //         this.$root.toastError('Cập nhật thông tin cổng dữ liệu thất bại')
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
        const flag = await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn lưu thông tin cổng dữ liệu này không?', {
          title: 'Xác nhận',
          size: 'md',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
          centered: true
        })
        if (!flag) return
        // Lấy PORT_ID đang chỉnh sửa
        const portId = this.interfacePortType.id
        const arrPorts = this.$parent.allPorts
        // Tìm PORT cần update
        const idxPort = arrPorts.findIndex((x) => x.PORT_ID == portId)

        if (idxPort === -1) {
          this.$root.toastError('Không tìm thấy cổng dữ liệu phù hợp để cập nhật')
          return
        }
        // Lấy id, tên các danh mục đã chọn
        const loaiCong = this.loaiCongListItems.find((x) => x.ID == this.interfacePortType.loaiCongId)
        const mucDich = this.mucDichSdCongListItems.find((x) => x.ID == this.interfacePortType.mucDichId)
        const trangThaiPort = this.trangThaiPortListItems.find((x) => x.ID == this.interfacePortType.trangThaiPortId)
        const trangThaiKN = this.trangThaiKetNoiListItems.find((x) => x.ID == this.interfacePortType.trangThaiKetNoiId)
        const thietBiNoiDen = this.thietBiNoiDenListItems.find((x) => x.ID == this.interfacePortType.thietBiNoiDenId)
        const moduleInfo = this.thongTinModuleListItems.find((x) => x.ID == this.interfacePortType.thongTinModuleId)
        const ipInfo = this.diaChiIPListItems.find((x) => x.ID == this.interfacePortType.diaChiIP)
        // CẬP NHẬT GIÁ TRỊ PORT VÀO arr_cong_dulieu
        arrPorts[idxPort] = {
          ...arrPorts[idxPort],
          MUCDICHSD_ID: Number(this.interfacePortType.mucDichId),
          TT_PORT_ID: Number(this.interfacePortType.trangThaiPortId),
          TRANGTHAIKN_ID: Number(this.interfacePortType.trangThaiKetNoiId),
          THIETBIKN_ID: Number(this.interfacePortType.thietBiNoiDenId),
          DIACHI_IP_ID: Number(this.interfacePortType.diaChiIP),
          DIACHI_IP: ipInfo?.IP || null,
          LOAICONG_ID: Number(this.interfacePortType.loaiCongId),
          MODULE_ID: Number(this.interfacePortType.thongTinModuleId),
          BANGTHONG_TN: this.interfacePortType.bangThong,
          LOAICONG: loaiCong?.TEN || null,
          MUCDICHSD: mucDich?.TEN || null,
          TRANGTHAI_PORT: trangThaiPort?.TEN || null,
          TRANGTHAI_KN: trangThaiKN?.TEN || null,
          THIETBIKN: thietBiNoiDen?.TEN || null,
          MODULE: moduleInfo?.TEN || null,
          isEdited: true
        }
        this.$root.toastSuccess('Lưu thông tin cổng dữ liệu thành công')
        this.closeDialog()
        // Reload grid
        // this.$parent.listCongDuLieu = [...arrPorts]
        this.$parent.allPorts = [...arrPorts]
        this.$parent.listCongDuLieu = this.$parent.allPorts.filter(
          (x) =>
            x.THIETBI_ID == this.$parent.currentItem.id || x.THIETBI_ID == Number(this.$parent.dataKheCam.THIETBIKN_ID)
        )
        console.log('this.$parent.listCongDuLieu after save:', this.$parent.listCongDuLieu)
      } catch (err) {
        console.error('onSave error:', err)
        this.$root.toastError('Có lỗi xảy ra trong quá trình lưu dữ liệu')
      }
    },

    validateControls: function() {
      let msg = ''
      if (this.interfacePortType.trangThaiPortId == null) {
        msg = 'Trạng thái port không được để trống.'
      }
      if (this.interfacePortType.trangThaiKetNoiId == null) {
        msg = 'Trạng thái kết nối không được để trống.'
      }
      if (this.interfacePortType.diaChiIP == null) {
        msg = 'Địa chỉ IP không được để trống.'
      }
      // if (this.interfacePortType.thietBiNoiDenId == null) {
      //   msg = 'Thiết bị nối đến không được để trống.'
      // }
      if (msg == '') return true

      this.$root.toastError(msg)
      return false
    },
    onChangeTrangThaiPort: function(value) {
      const result = this.checkPortStatus(this.interfacePortType.id, value, this.interfacePortType.trangThaiKetNoiId)
      if (!result.isValid) {
        this.$root.toastError(result.message)
        this.$nextTick(() => {
          this.interfacePortType.trangThaiPortId = this.previousTrangThaiPortId
        })
      } else {
        this.previousTrangThaiPortId = value
        this.interfacePortType.trangThaiPortId = value
      }
    }
  },

  created: async function() {}
}
</script>
