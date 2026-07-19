<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    ref="popupUploadImage"
    :header='headerTemplate'
    :animationSettings='animationSettings'
    :showCloseIcon="true"
    width='40%'
    :target='target'
    @open="handleOpen"
    :isModal="true"
  >

    <div class="list-actions-top custom-actions-top">
      <ul class="list">
        <li>
          <a class="upload-button">
            <span class="icon one-upload"></span> Tải lên
            <input type="file" class="file" ref="file" accept="image/png, image/gif, image/jpeg"
                   @change="uploadImage($event)" multiple>
          </a>
        </li>
        <li>
          <a @click="deleteImage">
            <span class="icon one-trash"></span> Xóa
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body mart10">
      <b-row v-if="ds_hinhanh.length !== 0">
        <div v-for="(src, idx) in ds_hinhanh" :key="idx" class="custom-col">
          <el-checkbox v-model="src.check">{{ src.name }}</el-checkbox>
          <el-image
            class="w-100"
            :src="src.url"
            :preview-src-list="ds_hinhanh.map(item => item.url)">
          </el-image>
        </div>
      </b-row>
      <p v-else style="color: #1f7ed0">Thiết bị chưa có hình ảnh</p>
    </div>
  </ejs-dialog>
</template>

<script>
import Vue from 'vue'
import CapVatTuTBAPI from '../popups/CapVatTuTBAPI'

export default {
  name: 'VatTuImage',
  props: {
    phieuvt_id: {
      type: String,
      default: function () {
        return '0'
      }
    }
  },
  data () {
    return {
      target: '#app',
      animationSettings: {effect: 'Zoom'},
      headerTemplate: function () {
        return {
          template: Vue.component('headerTp', {
            template: '<div class="title" style="font-size: 20px;"><span class="icon one-notepad"></span> Hình ảnh thiết bị</div>',
            data () {
              return {data: {data: {}}}
            }
          })
        }
      },
      SERVER_IMAGES: {
        IP: '10.70.52.164:8081',
        BASE_URL: 'http://10.70.52.164:8081/FILE/',
        NO_IMAGE_URL: 'http://10.70.52.164:8081/FILE/no-image-available.jpg',
        URL: 'http://10.70.52.164:8081/FILE/UploadFile.aspx?version=v2'
      },
      ds_hinhanh: [],
      tt_nd: null,
      setImg: 0
    }
  },
  methods: {
    async uploadImage (event) {
      try {
        this.loading(true)
        event.preventDefault()
        let dsFiles = event.target.files
        let formData = new FormData()
        let numberImgs = 0
        for (let file of dsFiles) {
          formData.append('files', file)
          numberImgs++
        }
        let result = await this.UPLOAD_IMAGE(formData, {headers: {'Content-Type': 'multipart/form-data'}})
        if (result.length > 0) {
          let imgsTemp = []
          let imagesNew = await this.link_view_file(result.join(','))
          for (let i = 0; i < imagesNew.length; i++) {
            imgsTemp.push({
              name: imagesNew[i].file_name,
              url: imagesNew[i].file_url,
              check: false
            })
          }
          let index = this.ds_hinhanh.length
          // cập nhật lại hình ảnh vào vật tư
          let imgs = this.ds_hinhanh.concat(imgsTemp.map((x, i) => Object.assign(x, {id: index + i + 1}))).map(x => x.name).join('|')
          let resp = await this.update_delete_img(imgs)
          if (resp) {
            this.ds_hinhanh = this.ds_hinhanh.concat(imgsTemp.map((x, i) => Object.assign(x, {id: index + i + 1}))).sort((a, b) => b.id - a.id)
            this.$root.$toast.success('Đã upload được ' + numberImgs + ' files')
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    showModal () {
      this.$refs.popupUploadImage.show()
    },
    hideModal () {
      this.setImg += 1
      this.$refs.popupUploadImage.hide()
    },
    async handleOpen () {
      this.tt_nd = await this.thongtin_nguoidung()
      let listImgs = await this.SP_GET_DSHINHANH(this.phieuvt_id)
      let imagesLink = listImgs[0].mota
      await this.loadImage(imagesLink)
    },
    async thongtin_nguoidung () {
      return this.GetData(await CapVatTuTBAPI.thongtin_nguoidung(this.axios))
    },
    async UPLOAD_IMAGE (data, header) {
      try {
        this.loading(true)
        let response = await CapVatTuTBAPI.upload_file(this.axios, data, header)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async update_delete_img (imgs) {
      let result = false
      await CapVatTuTBAPI.update_delete_img(this.axios, {
        'v_linkimg': imgs,
        'v_id': this.phieuvt_id
      }).then(response => {
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          result = true
        } else {
          this.$root.$toast.error('Cập nhật ảnh thiết bị thất bại: ' + response.data.message_detail)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi:' + error.data.message_detail)
      })
      return result
    },
    async link_view_file (listFile) {
      return this.GetDataList(await CapVatTuTBAPI.link_view_file(this.axios, listFile))
    },
    async deleteImage () {
      console.log(this.ds_hinhanh)
      try {
        this.loading(true)
        let imagesNotRemove = this.ds_hinhanh.filter(item => !item.check)
        let listFiles = imagesNotRemove.length > 0 ? imagesNotRemove.map(item => item.name).join('|') : ''
        let removeImgs = this.ds_hinhanh.filter(item => item.check)
        let listFilesRm = removeImgs.map(item => item.name).join('|')
        console.log('List file not delete ' + listFiles)
        let updateDB = await this.update_delete_img(listFiles)
        if (updateDB) {
          let updateCloud = await this.delete_file(listFilesRm)
          if (updateCloud) {
            this.ds_hinhanh = this.ds_hinhanh.filter(item => !item.check)
          }
          let listImgs = await this.SP_GET_DSHINHANH(this.phieuvt_id)
          if (listImgs.length > 0) {
            let imagesLink = listImgs[0].mota
            await this.loadImage(imagesLink)
          } else {
            let imagesLink = '|'
            await this.loadImage(imagesLink)
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async delete_file (listFiles) {
      let result = false
      await CapVatTuTBAPI.delete_file(this.axios, listFiles).then(response => {
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          result = true
        } else {
          result = false
          this.$root.$toast.error('Xóa ảnh thiết bị thất bại: ' + response.data.message_detail)
        }
      }).catch(error => {
        console.log(error)
        result = false
        this.$root.$toast.error('Có lỗi: ' + error.data.message_detail)
      })
      return result
    },
    async loadImage (imageStr) {
      try {
        let imagesTemp = []
        let newImage = []
        if (imageStr) {
          let words = imageStr.split('|')
          words = words.filter(item => item != '')
          this.ds_hinhanh = []
          for (let i = 0; i < words.length; i++) {
            let item = words[i]
            let imgLink = item
            // Hình ảnh từ hệ thống mới
            if (item.includes('DHSX/')) {
              newImage.push(item)
            } else {
              // Hình ảnh hệ thống cũ
              if (!imgLink.includes(this.SERVER_IMAGES.IP)) {
                imgLink = this.SERVER_IMAGES.BASE_URL + (this.tt_nd.tentat_ccbs ? this.tt_nd.tentat_ccbs : '') + '_VT/' + item
              }
              imagesTemp.push({
                name: item,
                url: imgLink,
                check: false
              })
            }
          }
          if (newImage.length > 0) {
            let imageNews = await this.link_view_file(newImage.join(','))
            imageNews.forEach(item => {
              imagesTemp.push({
                name: item.file_name,
                url: item.file_url,
                check: false
              })
            })
          }
        }
        this.ds_hinhanh = imagesTemp.map((x, index) => Object.assign(x, {id: index + 1}))
      } catch (error) {
        console.log(error)
      }
      console.log('ds_hinhanh', this.ds_hinhanh)
    },
    async SP_GET_DSHINHANH (phieuvtid) {
      return this.GetDataList(await CapVatTuTBAPI.SP_GET_DSHINHANH(this.axios, {
        'p_phieuvt_id': phieuvtid
      }))
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code == 'BSS-00000204') {
        return []
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetDataList: function (response) {
      console.log(response)
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    }
  }
}
</script>
<style>
.custom-actions-top {
  position: sticky;
  top: 0
}
</style>
