<template src="./index.html"></template>

<script>
import moment from 'moment'

export default {
  data: function() {
    return {
      currentItem: {
        id: 0,
        SoPhieu: '',
        TapTrung: false,
        Kho: '',
        NguonYeuCau: '',
        PhieuYeuCau: '',
        TanXuat: '',
        TrangThai: '',
        DungLuong: '',
        SPDV: '',
        DungLuongTangTruong: '',
        HaTangMang: '',
        ViTriDuLieu: '',
        MucDichSuDung: '',
        SoBanCanLuu: '',
        NgayTao: '',
        NgayHetHan: '',
        LuuTruNgoai: false,
        NguoiCapNhat: '',
        NgayCapNhat: '',
        GhiChu: '',
        DonViVanHanh: '',
        DonViDuocCap: '',
        PhongVanHanh: '',
        NguoiSuDungId: '',
        CaNhan: '',
        urlFile: null,
        idFile: null,
        trangThaiId: 0,
        DonViVanHanhId: null,
        PhongVanHanhId: null,
        CaNhanId: null,
        DonViDuocCapId: null
      },
      DSPhieu: [],
      dsNguoiSuDung: [],
      dsKhoBackup: [],
      dsXacNhan: [],
      files: null,
      fileName: '', // Lưu tên file
      id_taptin: '', // Lưu id file
      url_taptin: null,
      file_yc: null,
      isDeleteFile: false,
      buttons: {
        btnSave: {
          enabled: false
        },
        btnEdit: {
          enabled: true
        },
        btnXacNhan: {
          enabled: false
        }
      },
      controls: {
        GhiChu: {
          enabled: false,
          invalid: true
        },
        NgayHetHan: {
          enabled: false,
          invalid: true
        },
        NgayTao: {
          enabled: false,
          invalid: true
        },
        NgayCapNhat: {
          enabled: false,
          invalid: true
        },
        cboNguoiSuDung: {
          enabled: false,
          invalid: true
        },
        cboKhoBackup: {
          enabled: false,
          invalid: true
        }
      }
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    }
  },
  watch: {},
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.DSPhieu = await this.apiDanhSachCapPhatBackUp()
      this.dsNguoiSuDung = await this.getDmNguoiSuDung()
      this.dsKhoBackup = await this.getDmKhoBackup()
      this.dsNguoiSuDung.forEach((item) => {
        item.label = item.TENND
        item.value = item.ID
      })
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    setCurrentItem: async function(item) {
      this.currentItem.id = item.ID
      this.currentItem.SoPhieu = item.SOPHIEU
      this.currentItem.Kho = item.KHOHATANG
      this.currentItem.NguonYeuCau = item.NGUONYEUCAU
      if (item.NGAY_TAO != null) this.currentItem.NgayTao = item.NGAY_TAO
      else this.currentItem.NgayTao = null
      if (item.NGAY_HETHAN != null) this.currentItem.NgayHetHan = item.NGAY_HETHAN
      else this.currentItem.NgayHetHan = null
      this.currentItem.PhieuYeuCau = item.PHIEUYEUCAU
      this.currentItem.TanXuat = item.TANXUAT
      this.currentItem.TrangThai = item.TT_CAPPHAT
      this.currentItem.trangThaiId = item.TT_CAPPHAT_ID
      this.currentItem.DungLuong = item.DUNGLUONG
      this.currentItem.SPDV = item.SPDV
      this.currentItem.DungLuongTangTruong = item.DUNGLUONGTANGTRUONG
      this.currentItem.HaTangMang = item.HATANGMANG
      this.currentItem.ViTriDuLieu = item.VITRIDULIEU
      this.currentItem.MucDichSuDung = item.MUCDICH
      this.currentItem.SoBanCanLuu = item.SOBANLUU
      this.currentItem.LuuTruNgoai = item.LUUTRUNGOAI
      this.currentItem.NguoiCapNhat = item.NGUOICAPNHAT
      this.currentItem.GhiChu = item.GHICHU
      this.currentItem.DonViVanHanh = item.DONVIVANHANH
      this.currentItem.DonViDuocCap = item.DONVIDUOCCAP
      this.currentItem.PhongVanHanh = item.PHONGVANHANH
      this.currentItem.NguoiSuDungId = item.NGUOISUDUNG
      this.currentItem.CaNhan = item.CANHAN
      this.currentItem.TapTrung = item.TAPTRUNG
      this.currentItem.DonViVanHanhId = item.DONVIVH_ID
      this.currentItem.PhongVanHanhId = item.PHONGBAN_ID
      this.currentItem.CaNhanId = item.CANHAN_ID
      this.currentItem.DonViDuocCapId = item.DONVIDUOCCAP_ID
      if (item.NGAYCAPNHAT != null) this.currentItem.NgayCapNhat = item.NGAYCAPNHAT
      else this.currentItem.NgayCapNhat = null
      this.currentItem.urlFile = item.URL_FILE
      this.currentItem.idFile = item.ID_FILE
      this.buttons.btnEdit.enabled = item.TT_CAPPHAT_ID == 2 ? false : true
      this.buttons.btnSave.enabled = false
    },
    setCurrentItemDefault: function() {
      this.setCurrentItem({
        ID: 0,
        SOPHIEU: 0,
        KHOHATANG: '',
        NGUONYEUCAU: '',
        NGAYTAO: '',
        PHIEUYEUCAU: '',
        TANXUAT: '',
        TT_CAPPHAT: '',
        DUNGLUONG: '',
        SPDV: '',
        DUNGLUONGTANGTRUONG: '',
        HATANGMANG: '',
        VITRIDULIEU: '',
        MUCDICH: '',
        SOBANLUU: '',
        NGAYTAO: '',
        NGAYHETHAN: '',
        LUUTRUNGOAI: false,
        NGUOICAPNHAT: '',
        NGAYCAPNHAT: '',
        GHICHU: '',
        DONVIVANHANH: '',
        DONVIDUOCCAP: '',
        PHONGVANHANH: '',
        NGUOISUDUNG: '',
        CANHAN: '',
        TAPTRUNG: false,
        URL_FILE: null,
        ID_FILE: null,
        TT_CAPPHAT_ID: 0,
        DONVIVH_ID: null,
        PHONGBAN_ID: null,
        CANHAN_ID: null,
        DONVIDUOCCAP_ID: null
      })
    },
    grdItems_RowDeselected: async function() {
      if (this.buttons.btnSave.enabled) {
        return
      }
      let items = this.grdItems.getSelectedRecords()
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0])
      } else {
        this.setCurrentItemDefault()
      }
    },
    grdItems_RowSelected: async function(args) {
      this.setCurrentItem(args.data)
      this.enableControls(false)
      this.fileName = ''
    },
    enableControls: function(args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = this.currentItem.trangThaiId == 2 ? false : args
        }
      }
      // this.buttons.btnSave.enabled = args
      // this.buttons.btnEdit.enabled = !args
    },
    validateControls: function() {
      let msg = ''
      if (this.currentItem.NgayHetHan == null) {
        msg += ', Ngày hết hạn'
      }
      // if (this.currentItem.NgayCapNhat == null) {
      //   msg += ', Ngày cập nhật'
      // }
      if (this.currentItem.NguoiSuDungId < 1) {
        msg += ', Người sử dụng'
      }
      if (this.currentItem.Kho < 1) {
        msg += ', Kho hạ tầng backup'
      }
      if (msg != '') {
        msg = 'Bạn chưa chọn : ' + msg.substring(2) + '\n'
      }

      if (this.currentItem.NgayHetHan < new Date()) {
        msg += 'Ngày hết hạn phải lớn hơn ngày hiện tại'
      }
      if (msg != '') this.$root.toastError(msg)
      return msg == ''
    },
    GhiLai: async function() {
      const confirmed = await this.$confirm('Bạn có muốn lưu thông tin này không ?', 'Xác nhận', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info',
        dangerouslyUseHTMLString: true
      })
        .then(() => true)
        .catch(() => false)

      if (!confirmed) return

      if (!this.validateControls()) return

      this.$root.showLoading(true)

      try {
        // Xóa file cũ nếu cần
        if (this.isDeleteFile) {
          const deleteResult = await this.deleteFileFromMinio()
          if (deleteResult) {
            this.currentItem.urlFile = null
            this.currentItem.idFile = null
            this.id_taptin = null
            this.isDeleteFile = false
          } else {
            this.$root.toastError('Có lỗi xảy ra khi xóa file cũ.')
            return
          }
        }

        // Upload file mới nếu có
        let uploadSuccess = true
        if (this.files != null) {
          uploadSuccess = await this.uploadFile()
        }

        if (!uploadSuccess) {
          this.$root.toastError('Có lỗi xảy ra trong quá trình upload file!')
          return
        }

        // Gọi API cập nhật dữ liệu
        const updateResult = await this.apiCapNhatCapPhatBackUp(this.currentItem)
        if (updateResult?.result) {
          this.DSPhieu = await this.apiDanhSachCapPhatBackUp()
          this.enableControls(false)
          this.$root.toastSuccess('Cập nhật thông tin thành công')
        } else {
          this.$root.toastError('Có lỗi xảy ra trong quá trình cập nhật thông tin!')
        }
      } catch (error) {
        this.$toast.error(error?.response?.data?.message_detail || 'Đã xảy ra lỗi.')
      } finally {
        this.$root.showLoading(false)
      }
    },
    Sua() {
      this.enableControls(true)
      this.buttons.btnSave.enabled = true
      this.buttons.btnEdit.enabled = false
    },
    XacNhan: async function() {
      // if (this.dsXacNhan.length <= 0) {
      //   this.$toast.error('Chưa chọn phiếu cần xác nhận.');
      //   return;
      // }
      let flag = await this.$confirm('Bạn có chắc chắn xác nhận cấp phát phiếu hạ tầng backup này không?', 'Xác nhận', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.$root.showLoading(true)
          if (!this.validateControls()) return
          let rs = await this.apiXacNhanCapPhatBackUp()
          if (rs.result) {
            this.enableControls(false)
            this.DSPhieu = await this.apiDanhSachCapPhatBackUp()
            this.$root.toastSuccess('Xác nhận cấp phát thành công!')
          }
          // else  {
          //     this.$root.toastError("Trạng thái phiếu không hợp lệ! Vui lòng kiểm tra lại.");
          // }
        } catch (e) {
          this.$toast.error(e.response.data.message_detail)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    apiDanhSachCapPhatBackUp: async function() {
      let result = []
      try {
        const rs = await this.$root.context.post(`/web-ecms/hatang/capphat-backup/danhsach-capphat-backup`)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiCapNhatCapPhatBackUp: async function(params) {
      let postData = {
        id: params.id,
        ngayHethan: params.NgayHetHan ? moment(params.NgayHetHan).format('DD/MM/YYYY') : '',
        ngayCapnhat: params.NgayCapNhat ? moment(params.NgayCapNhat).format('DD/MM/YYYY') : '',
        dvVanhanhId: Number(params.DonViVanHanhId),
        nguoiCapnhatId: Number(this.$auth.getNhanVienID()),
        ghiChu: params.GhiChu,
        nguoiSudungId: params.NguoiSuDungId,
        fileId: this.id_taptin,
        khoBackupId: Number(params.Kho),
        phongbanId: Number(params.PhongVanHanhId),
        nguoiQuanlyId: Number(params.CaNhanId)
      }
      let result = []
      try {
        const rs = await this.$root.context.post(`/web-ecms/hatang/capphat-backup/capnhat-capphat`, postData)
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiXacNhanCapPhatBackUp: async function() {
      let result = []
      try {
        const rs = await this.$root.context.get('/web-ecms/hatang/capphat-backup/xacnhan-capphat-backup', {
          id: this.currentItem.id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },

    // Danh mục Người sử dụng
    getDmNguoiSuDung: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/hatang/capphat-backup/ds-nguoisudung', {
          phanvungId: this.$auth.getPhanVungID()
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    triggerFileInput() {
      if (!this.buttons.btnEdit.enabled) {
        this.$refs.fileInput.click()
      }
    },
    onChangeFile(e) {
      const selectedFiles = Array.from(e.target.files)
      if (!selectedFiles || selectedFiles.length === 0) {
        this.files = null
        this.fileName = ''
        return
      }
      // Check file extension
      const invalidExtensions = ['exe', 'msi', 'bat', 'js']
      for (let file of selectedFiles) {
        const extension = file.name
          .split('.')
          .pop()
          .toLowerCase()
        if (invalidExtensions.includes(extension)) {
          this.files = null
          this.fileName = ''
          return this.$toast.error('Vui lòng không upload file có định dạng .exe/.msi/.bat/.js!')
        }
      }
      // Nếu có file cũ đã lưu, đánh dấu để xóa
      if (this.currentItem.idFile != null && this.currentItem.idFile > 0) {
        this.deleteFile()
      }
      this.files = selectedFiles
      this.fileName = selectedFiles[0].name
      e.target.value = null
    },
    async uploadFile() {
      if (this.files != null) {
        let formData = new FormData()
        formData.append('p_module', 'HATANGCNTT')
        formData.append('p_file', this.files[0])
        let rs = await this.uploadFileToMinio(formData)
        if (rs == null) {
          return false
        } else {
          // await this.deleteFileFromMinio(this.relativeUrl);
          // this.relativeUrl = url.relativeUrl;
          this.id_taptin = rs.id_taptin
          this.fileName = ''
          return true
        }
      }
    },
    async uploadFileToMinio(formData) {
      try {
        this.loading(true)
        let headers = {
          'App-secret':
            'eyJkZXZpY2VfaWQiOiI2MUVDNDg2RS02RUE2LTQ0OTUtQkRBRS0xQkFDMDExRjQzMDAiLCJkZXZpY2VfaXAiOiJlcnJvciIsImRldmljZV9uYW1lIjoiaVBob25lIC0gaVBob25lIDEyIFBybyBNYXgiLCJtYWNfYWRkcmVzcyI6IlVua25vdyIsIm1vYmlsZV9pZCI6IlVua25vdyIsImFwcF9pZCI6IjIiLCJhcHBfdmVyc2lvbiI6IjIuNC4xLjQwIiwib3NfdmVyc2lvbiI6ImlPUyAtIDE0LjUifQ=='
        }
        let url = `/app-banhang/quanlyfile/upload_file`
        let response = await this.axios.post(url, formData, { headers })
        return response.data.data
      } catch (error) {
        console.log(error)
        // this.$toast.error('Có lỗi xảy ra trong quá trình upload file!');
        return null
      } finally {
        this.loading(false)
      }
    },
    async getFile() {
      try {
        this.loading(true)
        if (this.currentItem.urlFile) {
          let headers = {
            'App-secret':
              'eyJkZXZpY2VfaWQiOiI2MUVDNDg2RS02RUE2LTQ0OTUtQkRBRS0xQkFDMDExRjQzMDAiLCJkZXZpY2VfaXAiOiJlcnJvciIsImRldmljZV9uYW1lIjoiaVBob25lIC0gaVBob25lIDEyIFBybyBNYXgiLCJtYWNfYWRkcmVzcyI6IlVua25vdyIsIm1vYmlsZV9pZCI6IlVua25vdyIsImFwcF9pZCI6IjIiLCJhcHBfdmVyc2lvbiI6IjIuNC4xLjQwIiwib3NfdmVyc2lvbiI6ImlPUyAtIDE0LjUifQ=='
          }
          let url = `/app-banhang/quanlyfile/get_file`
          let response = await this.axios.post(url, { p_path: this.currentItem.urlFile }, { headers })
          if (response.data.data != undefined) {
            this.url_taptin = response.data.data.url_taptin
            this.fileName = response.data.data.ten_taptin
            return true
          } else {
            this.url_taptin = null
            return false
          }
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    async downloadFile() {
      if (this.currentItem.urlFile && this.files == null) {
        let rs = await this.getFile()
        if (rs) {
          const response = await fetch(this.currentItem.urlFile)
          const blob = await response.blob()
          const blobUrl = URL.createObjectURL(blob)
          const a = document.createElement('a')
          a.href = blobUrl
          a.download = this.fileName
          document.body.appendChild(a)
          a.click()
          document.body.removeChild(a)
        } else {
          this.$toast.error('Không tìm thấy file!')
        }
      } else {
        if (this.files) {
          const file = this.files[0]
          const url = URL.createObjectURL(file)
          const a = document.createElement('a')
          a.href = url
          a.download = file.name
          document.body.appendChild(a)
          a.click()
          document.body.removeChild(a)
          URL.revokeObjectURL(url)
        } else {
          this.$toast.error('Không tìm thấy file!')
        }
      }
    },
    deleteFile() {
      this.isDeleteFile = this.currentItem.idFile != null && this.currentItem.idFile > 0 ? true : false
      this.currentItem.urlFile = null
      this.fileName = null
      this.files = null
    },
    async deleteFileFromMinio() {
      try {
        this.loading(true)
        let headers = {
          'App-secret':
            'eyJkZXZpY2VfaWQiOiI2MUVDNDg2RS02RUE2LTQ0OTUtQkRBRS0xQkFDMDExRjQzMDAiLCJkZXZpY2VfaXAiOiJlcnJvciIsImRldmljZV9uYW1lIjoiaVBob25lIC0gaVBob25lIDEyIFBybyBNYXgiLCJtYWNfYWRkcmVzcyI6IlVua25vdyIsIm1vYmlsZV9pZCI6IlVua25vdyIsImFwcF9pZCI6IjIiLCJhcHBfdmVyc2lvbiI6IjIuNC4xLjQwIiwib3NfdmVyc2lvbiI6ImlPUyAtIDE0LjUifQ=='
        }
        let url = `/app-banhang/quanlyfile/delete_file`
        let response = await this.axios.post(url, { p_id_taptin: this.currentItem.idFile }, { headers })
        if (response.data.error_code === 'BSS-00000000') {
          return true
        } else return false
      } catch (error) {
        console.log(error)
        return false
      } finally {
        this.loading(false)
      }
    },
    onCancel: async function(args) {
      // Load lại thông tin thiết bị
      let items = this.grdItems.getSelectedRecords()
      let item = null
      console.log('items', items)
      if (items && items.length > 0) {
        if (this.currentItem.id == 0) {
          item = items[0]
        } else {
          item = items.find((x) => x.ID == this.currentItem.id)
        }
      }
      if (item != null) {
        this.setCurrentItem(item)
      } else {
        this.setCurrentItemDefault()
      }
      this.enableControls(false)
    },
    // Danh mục Kho hạ tầng backup
    getDmKhoBackup: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/capphat-backup/ds-kho-hatang-backup')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    onSelectKhoBackup(khoId) {
      console.log(' this.dsKhoBackup:', khoId)
      const kho = this.dsKhoBackup.find((item) => item.ID == khoId)
      if (kho) {
        this.currentItem.Kho = kho.ID
        this.currentItem.DonViVanHanh = kho.DONVI
        this.currentItem.DonViVanHanhId = kho.DONVI_ID
        this.currentItem.PhongVanHanh = kho.PHONGBAN
        this.currentItem.PhongVanHanhId = kho.PHONGBAN_ID
        this.currentItem.CaNhanId = kho.NGUOI_QLY_ID
        this.currentItem.CaNhan = kho.NGUOI_QL
        console.log('Kho:', this.currentItem)
      } else {
        this.currentItem.DonViVanHanh = ''
        this.currentItem.PhongVanHanh = ''
        this.currentItem.CaNhan = ''
        this.currentItem.DonViVanHanhId = null
        this.currentItem.PhongVanHanhId = null
        this.currentItem.CaNhanId = null
      }
    }
  }
}
</script>
<style>
.info-row .key {
  width: 150px !important;
}
</style>
