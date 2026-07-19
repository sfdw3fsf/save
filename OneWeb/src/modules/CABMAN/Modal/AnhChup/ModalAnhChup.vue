<template src="./ModalAnhChup.html"></template>
<script>
export default {
  name: 'ModalAnhChup',
  props: ['modalId'],
  created: async function () {
  },

  computed: {},
  methods: {
    show (doituong) {
      return new Promise((resolve, reject) => {
        this.doituong = doituong
        this.$bvModal.show(this.modalId)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    closeModal: function () {
      this.resolvePromise({ok: false, data: null})
      this.$bvModal.hide(this.modalId)
    },
    shownModal: async function () {
      this.edit = false
      this.stateBtn.isDisableBtnXemAnh = true
      this.stateBtn.isDisableBtnSuaAnh = true
      this.getMota()
    },
    hiddenModal () {
      this.doituong = {
        doituong_id: null,
        doituong_mc: null
      }
    },
    getMota: async function () {
      try {
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/execute1', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_DOITUONG: this.doituong.doituong_mc, P_DOITUONG_ID: this.doituong.doituong_id, PROCEDURE_NAME: 'CABMAN.PKG_GIANDO_CAP.SP_LAY_MOTA_DOITUONG_MANGCAP'}
        )
        let src = ''
        console.log('getMota', rs.data)
        if (rs.data != null && rs.data.length > 0) {
          this.ten_doituong = rs.data[0].TEN_DT
          this.ten_loai = rs.data[0].TENLOAI_DT
          if (rs.data[0].MOTA_DT === null || rs.data[0].MOTA_DT.trim() === '') return
          if (rs.data[0].MOTA_DT.split(';').length > 1) {
            this.title_anh = rs.data[0].MOTA_DT.split(';')[0]
            src = rs.data[0].MOTA_DT.split(';')[1]
          } else { src = rs.data[0].MOTA_DT.split(';')[0] }
          if (src !== '') { this.viewImage(src) }
        }
      } catch (error) {
        console.log('getMota', error)
        this.$toast.error('Hệ thống chưa được nâng cấp cho chức năng này!\r\nLiên hệ ERP để được hỗ trợ!')
      } finally {
      }
    },
    ghiLai: async function () {
      await this.uploadFile()
    },
    uploadFile: async function () {
      try {
        let rs = await this.$root.context.post(
          '/web-cabman/storage/uploadFile', this.formData)
        if (rs.data != null && rs.data.status) {
          this.path = rs.data.message.relativeUrl
          await this.saveImage()
        } else {
          this.$root.toastError('Có lỗi xảy ra trong quá trình upload file')
        }
      } catch (e) {
        console.log(e)
        this.$root.toastError('Có lỗi xảy ra trong quá trình upload file')
      }
    },
    viewImage: async function (src) {
      try {
        let rs = await this.$root.context.post(
          '/web-cabman/storage/getPresignedUrl', {fileSource: src})
        if (rs.data != null) {
          this.src = rs.data.result
        } else {
          this.$root.toastError('Có lỗi xảy ra trong quá trình lấy đường dẫn file')
        }
      } catch (e) {
        console.log(e)
        this.$root.toastError('Có lỗi xảy ra trong quá trình lấy đường dẫn file')
      }
    },
    saveImage: async function () {
      try {
        this.$root.showLoading(true)
        let mota = this.title_anh + ';' + this.path
        console.log('saveImage', mota)
        var rs = await this.$root.context.post(
          '/web-cabman/ban-do-mang-cap/execute1', {P_PHANVUNG_ID: this.$root.token.getPhanVungID(), P_DOITUONG: this.doituong.doituong_mc, P_DOITUONG_ID: this.doituong.doituong_id, P_MOTA: mota, PROCEDURE_NAME: 'CABMAN.PKG_GIANDO_CAP.SP_CN_MOTA_DOITUONG_MANGCAP'}
        )
        if (rs.data != null) { this.$toast.success('Cập nhật ảnh đối tượng mạng cáp thành công!') } else { this.$toast.error('Cập nhật ảnh đối tượng mạng cáp thất bại!') }
      } catch (error) {
        this.$toast.error('Lỗi khi upload file: ' + error.messeage)
      } finally {
        this.$root.showLoading(false)
      }
    },
    chonImage: function () {
      if (this.edit) { document.getElementById('my-file').click() }
    },
    addImage: function () {
      this.title_anh = ''
      this.src = require('@/assets/uploads/avatar-default.png')
      this.edit = true
      this.hasImage = true
    },
    previewImage: function (event) {
      var input = event.target
      if (input.files) {
        var reader = new FileReader()
        reader.onload = (e) => {
          this.src = e.target.result
          this.hasImage = false
        }
        this.image = input.files[0]
        this.formData = new FormData()
        this.formData.append('file', input.files[0])
        reader.readAsDataURL(input.files[0])
      }
      // Upload file lên server và nhận url tương ứng
      this.path = ''// Lưu đường dẫn ảnh
      console.log(this.image)
      // this.src=getPath(this.file)
    },
    xoaImage: function () {
      this.src = require('@/assets/uploads/avatar-default.png')
    },
    suaImage: function () {
      this.edit = true
      this.hasImage = true
    },
    xemImage: function () {
      this.stateBtn.isDisableBtnSuaAnh = false
    },
    preview: function () {
      this.stateBtn.isDisableBtnXemAnh = false
    }
  },
  data: function () {
    return {
      src: null,
      file: null,
      stateBtn: {
        isDisableBtnSuaAnh: true,
        isDisableBtnXemAnh: true
      },
      title_anh: '',
      edit: false,
      image: null,
      hasImage: true,
      path: '',
      resolvePromise: null,
      rejectPromise: null,
      ten_doituong: null,
      ten_loai: null,
      doituong: {
        doituong_id: null,
        doituong_mc: null
      },
      formData: null
    }
  }
}
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
