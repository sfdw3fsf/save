<template src="./ModalAnhChup.html"></template>
<script>
export default {
  name: 'ModalAnhChup',
  props: {
    ketcuoi: {
      type: Object,
      required: true
    },
    readOnly: {
      type: Boolean,
      required: true
    },
    modalId: String
  },
  data: function () {
    return {
      loaiKetCuoi: null, // "Kết cuối":vnpt, "Kết cuối đối thủ":đối thủ

      isDisableBtnGhiAnh: true,
      isDisableBtnSuaAnh: true,
      isDisableBtnXemAnh: true,

      // title_anh: "",
      // edit: false,
      // hasImage: true,
      // path: "",
      sources: [], // link anh lay tu server

      htmlItems: []
    }
  },
  methods: {
    focusMyElement: async function () {
      this.clear()
      this.ketcuoi.CHUQUAN_ID === 1 ? this.loaiKetCuoi = 'Kết cuối' : this.loaiKetCuoi = 'Kết cuối đối thủ'
      // this.edit = false;
      this.stateBtn(false, true, false)
      // getMota
      try {
        this.loading(true)
        await this.getMota()
      } catch (e) {} finally {
        this.loading(false)
      }
    },
    async getMota () {
      try {
        let isVnpt = this.ketcuoi.CHUQUAN_ID === 1 ? 1 : 0
        let rs = await this.$root.context.post(
          '/web-cabman/phantu_mang/lay_mota', {ketcuoi_id: this.ketcuoi.KETCUOI_ID, doituong: 0, isVnpt: isVnpt}
        )
        // get image
        if (rs !== null && rs.error === '200') {
          this.sources = await this.getThongTinImages(rs.data.MOTA)
          console.log('sources', this.sources)
          this.loadHtmlItems(this.sources)
        }
      } catch (error) {
        console.log(error)
        this.$root.toastError('Có lỗi xảy ra khi lấy thông tin ảnh')
      } finally {
      }
    },
    saveImage: async function (isXoa) {
      if (this.isDisableBtnGhiAnh) return
      let mota = null
      try {
        this.loading(true)
        if (this.sources.length > 0) {
          // Upload file lên server và nhận url tương ứng
          for (let i = 0; i < this.sources.length; i++) {
            if (this.sources[i].isNew && this.sources[i].image) {
              let formData = new FormData()
              formData.append('file', this.sources[i].image)
              await this.$root.context.post('/web-cabman/storage/uploadFile', formData)
                .then(res => {
                  if (res.data != null && res.data.status === true) {
                    // this.path= res.data.message.relativeUrl
                    this.sources[i].relativeUrl = res.data.message.relativeUrl
                    this.sources[i].presignedUrl = res.data.message.presignedUrl
                  }
                }).catch(e => {
                  this.$root.toastError('Có lỗi xảy ra trong quá trình upload file:', e.response.data.message_detail)
                  throw 1
                })
            }
          }
          // update mota db
          let image_count = this.sources.filter(item => item.relativeUrl).length
          if (image_count > 0) {
            let doc = document.implementation.createDocument('', '', null)
            let cabman_img_data = document.createElement('cabman_img_data')
            for (let item of this.sources) {
              let img = doc.createElement('img')
              img.setAttribute('id', '')
              img.setAttribute('title', item.title)
              img.setAttribute('url', item.relativeUrl)
              cabman_img_data.appendChild(img)
            }
            doc.appendChild(cabman_img_data)
            const serializer = new XMLSerializer()
            const xmlStr = serializer.serializeToString(doc)
            mota = xmlStr
          }
        }
        let isVnpt = this.ketcuoi.CHUQUAN_ID === 1 ? 1 : 0
        await this.$root.context.post(
          '/web-cabman/phantu_mang/capnhat_mota', {
            doituong: 0,
            ketcuoi_id: this.ketcuoi.KETCUOI_ID,
            isVnpt: isVnpt,
            mo_ta: mota
          })
          .then(res => {
            if (res.error === '200' && res.error_code === 'BSS-00000000') {
              if (!isXoa) this.$toast.success('Cập nhật thành công!')
              // cap nhat isNew
              this.sources = this.sources.map(item => ({...item, isNew: false}))
            }
          }).catch(e => {
            this.$toast.error('Cập nhật thất bại:', e.response.data.message_detail)
            throw 2
          })
      } catch (error) {
        if (error === 1) {
          // delete images isNew = true
          // call delete image minio
          for (let item of this.sources) {
            if (item.isNew && item.relativeUrl) {
              await this.$root.context.post('/web-cabman/storage/deleteFile', {fileSource: item.relativeUrl})
            }
          }
        } else {
          this.$toast.error('Lỗi khi cập nhật file: ' + error.message)
        }
      } finally {
        this.loading(false)
      }
    },
    chonImage: function (event) {
      console.log('🚀 ~ file: ModalAnhChup.vue:394 ~ event:', event)
      // document.getElementById("my-file").click();
      if (this.isDisableBtnSuaAnh) {
        event.target.previousElementSibling.click()
      }
    },
    loadHtmlItems (sources) {
      if (sources.length > 0) {
        sources.forEach(item => {
          let component = {
            template:
                  '                <div class="box-file-image-upload bss1833-box-file-image input-image" style="max-width: 600px;margin: auto;">\n' +
                  '                 <div class="row">\n' +
                  '                   <div class="input-more-button -right input-title">\n' +
                  '                     <button class="btn btn-main" @click="parent.xoaImage($event)" :hidden="parent.isDisableBtnXemAnh">\n' +
                  '                       <span class="one-trash" style="pointer-events: none;"></span>\n' +
                  '                     </button>\n' +
                  '                     <input type="text" class="form-control " placeholder="[Tiêu đề]" v-model="item.title" :disabled="!parent.isDisableBtnSuaAnh" :hidden="parent.isDisableBtnXemAnh">\n' +
                  '                   </div>\n' +
                  '                 </div>\n' +
                  '                 <div class="row">\n' +
                  '                  <div class="file-canvas" @dblclick="parent.chonImage($event)" :hidden="hidden">\n' +
                  '                    <input type="file" accept="image/*" @change="parent.previewImage($event,item)" class="form-control-file" hidden>\n' +
                  '                    <img :src="item.presignedUrl" alt="" id="output" @click="parent.preview($event)">\n' +
                  '                                            <span class="file-text" style="pointer-events: none;" v-show="!item.hasImage">Click double để chọn ảnh</span>\n' +
                  '                  </div>\n' +
                  '                 </div>\n' +
                  '                </div>',
            data () {
              return {
                item: item
              }
            },
            computed: {
              parent () {
                return this.$parent.$parent.$parent
              },
              hidden () {
                return this.$parent.$parent.$parent.isDisableBtnXemAnh ? false : !item.hasImage
              }
            }
          }
          this.htmlItems.push(component)
        })
      } else {
        this.addImage()
      }
    },
    addImage: function () {
      let item = {
        id: '',
        title: '',
        presignedUrl: require('../../../../../../assets/uploads/default-image.png'),
        relativeUrl: null,
        hasImage: false,
        isNew: true,
        image: null // file upload minio
      }
      let component = {
        template:
            '                <div class="box-file-image-upload bss1833-box-file-image input-image" style="max-width: 600px;margin: auto;">\n' +
            '                 <div class="row">\n' +
            '                  <div class="input-more-button -right input-title">\n' +
            '                    <button class="btn btn-main" @click="parent.xoaImage($event)" :hidden="parent.isDisableBtnXemAnh">\n' +
            '                      <span class="one-trash" style="pointer-events: none;"></span>\n' +
            '                    </button>\n' +
            '                    <input type="text" class="form-control " placeholder="[Tiêu đề]" v-model="item.title" :disabled="!parent.isDisableBtnSuaAnh" :hidden="parent.isDisableBtnXemAnh">\n' +
            '                  </div>\n' +
            '                 </div>\n' +
            '                 <div class="row">\n' +
            '                  <div class="file-canvas" @dblclick="parent.chonImage($event)" :hidden="hidden">\n' +
            '                    <input type="file" accept="image/*" @change="parent.previewImage($event,item)" class="form-control-file" id="my-file" hidden>\n' +
            '                    <img :src="item.presignedUrl" alt="" id="output" @click="parent.preview($event)">\n' +
            '                                            <span class="file-text" style="pointer-events: none;" v-show="!item.hasImage">Click double để chọn ảnh</span>\n' +
            '                  </div>\n' +
            '                 </div>\n' +
            '                </div>',
        data () {
          return {item: item}
        },
        computed: {
          parent () {
            return this.$parent.$parent.$parent
          },
          hidden () {
            return !this.$parent.$parent.$parent.isDisableBtnXemAnh ? false : !item.hasImage
          }
        }
      }
      this.htmlItems.push(component)
      console.log('🚀 ~ file: ModalAnhChup.vue:232 ~ this.htmlItems:', this.htmlItems)
      this.sources.push(item)
      console.log('🚀 ~ file: ModalAnhChup.vue:234 ~ this.sources:', this.sources)
      // this.title_anh = ""
      // this.src = require("../../../../../assets/uploads/avatar-default.png")
      // this.edit = true
      // this.hasImage = true
    },
    async previewImage (event, item) {
      console.log('🚀 ~ file: ModalAnhChup.vue:238 ~ previewImage ~ event:', event, item, event.composedPath())
      if (this.readOnly) {
        this.$root.toastError('Bạn không có quyền thêm ảnh')
        return
      }
      let input = event.target
      let index = 0
      try {
        // index = parseInt(event.path[3].id.split('_')[1], 10)
        // if (isNaN(index)) { // do mất 1 thẻ span.one-trash
        //   index = parseInt(event.path[2].id.split('_')[1], 10)
        // }
        let path = event.composedPath()
        index = parseInt(path[3].id.split('_')[1], 10)
        if (isNaN(index)) { // do mất 1 thẻ span.one-trash
          index = parseInt(path[2].id.split('_')[1], 10)
        }
      } catch (error) {
        console.log('🚀 ~ file: ModalAnhChup.vue:254 ~ previewImage ~ error:', error)
      }

      if (input.files) {
        let reader = new FileReader()
        reader.onload = (e) => {
          this.sources[index].presignedUrl = e.target.result
          this.sources[index].hasImage = true
          this.sources[index].image = input.files[0] // file upload minio

          // case: update img
          if (this.sources[index].relativeUrl) this.sources[index].isNew = true
        }
        reader.readAsDataURL(input.files[0])
      }
    },
    xoaImage: function (event) {
      let index = 0
      try {
        let path = event.composedPath()
        index = parseInt(path[3].id.split('_')[1], 10)
        if (isNaN(index)) { // do mất 1 thẻ span.one-trash
          index = parseInt(path[2].id.split('_')[1], 10)
        }
      } catch (error) {
        console.log('🚀 ~ file: ModalAnhChup.vue:283 ~ error:', error)
      }
      if (this.sources[index].hasImage) {
        this.$bvModal.msgBoxConfirm('Bạn có chắc muốn xóa ảnh này?',
          {
            title: 'Xác nhận hành động Xóa ảnh',
            centered: true,
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          }).then(async (value) => {
          if (value) {
            if (this.readOnly) {
              this.$root.toastError('Bạn không có quyền xóa')
              return
            }
            try {
              // xoa img server minio
              if (this.sources[index].isNew === false) {
                console.log('vao day', this.sources[index])
                await this.$root.context.post('/web-cabman/storage/deleteFile', {fileSource: this.sources[index].relativeUrl})
                  .then(res => {
                    if (res && res.data.status === true) {
                      // this.$root.toastSuccess("Xóa thành công")
                    }
                  }).catch(e => {
                    this.$root.toastError('Xóa thất bại')
                    throw e
                  })
              }
              // xoa img client
              this.sources = this.sources.filter((item, idx) => idx !== index)
              this.htmlItems = this.htmlItems.filter((item, idx) => idx !== index)
              await this.saveImage(true)
            } catch (e) {}
          } else {

          }
        })
      } else {
        // xoa khung img
        this.sources = this.sources.filter((item, idx) => idx !== index)
        this.htmlItems = this.htmlItems.filter((item, idx) => idx !== index)
      }
    },
    suaImage: function () {
      // this.edit = true
      // this.hasImage = true
      if (this.isDisableBtnSuaAnh) return
      this.stateBtn(false, true, false)
    },
    xemImage: function () {
      if (this.isDisableBtnXemAnh) return
      this.stateBtn(true, false, true)
    },
    preview: function (event) {
      // this.stateBtn.isDisableBtnXemAnh = false;
    },
    async getThongTinImages (mota) {
      console.log('🚀 ~ file: ModalAnhChup.vue:343 ~ getThongTinImages ~ mota:', mota)
      let images = []
      if (!mota) return images
      // read xml
      if (window.DOMParser) {
        let parser = new DOMParser()
        let xmlDoc = parser.parseFromString(mota.trim(), 'text/xml')
        let imgs = xmlDoc.getElementsByTagName('img')
        console.log('🚀 ~ file: ModalAnhChup.vue:351 ~ getThongTinImages ~ imgs:', imgs)
        if (imgs.length > 0) {
          // for img
          for (let i = 0; i < imgs.length; i++) {
            // get presignedUrl
            let fileSource = {fileSource: imgs[i].attributes['url'].value}
            console.log('🚀 ~ file: ModalAnhChup.vue:358 ~ getThongTinImages ~ url_image:', fileSource)
            await this.$root.context.post('/web-cabman/storage/getPresignedUrl', fileSource)
              .then(res => {
                if (res.error === '200' && res.error_code === 'BSS-00000000') {
                  images.push({
                    id: imgs[i].attributes['id'] ? imgs[i].attributes['id'].value : null,
                    title: imgs[i].attributes['title'] ? imgs[i].attributes['title'].value : null,
                    presignedUrl: res.data.result,
                    relativeUrl: imgs[i].attributes['url'].value,
                    hasImage: true,
                    isNew: false,
                    image: null // file upload minio
                  })
                }
              }).catch(e => this.$root.toastError('Có lỗi xảy ra khi lấy thông tin ảnh'))
          }
        }
      } else { // Internet Explorer
        let xmlDoc = new ActiveXObject('Microsoft.XMLDOM')
        xmlDoc.async = false
        xmlDoc.loadXML(mota.trim())
        let imgs = xmlDoc.getElementsByTagName('img')
        if (imgs.length > 0) {
          // for img
          for (let i = 0; i < imgs.length; i++) {
            // get presignedUrl
            await this.$root.context.post('/web-cabman/storage/getPresignedUrl', {fileSource: imgs[i].attributes['url'].value})
              .then(res => {
                if (res.error === '200' && res.error_code === 'BSS-00000000') {
                  images.push({
                    id: imgs[i].attributes['id'] ? imgs[i].attributes['id'].value : null,
                    title: imgs[i].attributes['title'] ? imgs[i].attributes['title'].value : null,
                    presignedUrl: res.data.result,
                    relativeUrl: imgs[i].attributes['url'].value,
                    hasImage: true,
                    isNew: false,
                    image: null // file upload minio
                  })
                }
              }).catch(e => this.$root.toastError('Có lỗi xảy ra khi lấy thông tin ảnh'))
          }
        }
      }
      return images
    },
    clear () {
      this.loaiKetCuoi = null // "Kết cuối":vnpt, "Kết cuối đối thủ":đối thủ
      this.isDisableBtnSuaAnh = true
      this.isDisableBtnXemAnh = true

      // this.edit = false
      this.sources = [] // link anh lay tu server

      this.htmlItems = []
    },
    stateBtn (btnGhiAnh, btnSuaAnh, btnXemAnh) {
      this.isDisableBtnGhiAnh = btnGhiAnh
      this.isDisableBtnSuaAnh = btnSuaAnh
      this.isDisableBtnXemAnh = btnXemAnh
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
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
.bss1833-box-file-image .input-more-button.-right.input-title {
  position: inherit;
  /*margin-bottom: 10px;*/
}
.box-file-image-upload .file-canvas .input-image{
  margin-bottom: 10px !important;
}
.input-more-button.-right > .btn {
  right: -6px !important;
}
</style>
