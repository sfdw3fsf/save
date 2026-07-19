<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import api from './api'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
export default {
  components: { breadcrumb },
  name: 'PopupImageUpload',
  props: {},
  data() {
    return { data: {}, images: [] }
  },
  computed: {},
  methods: {
    GetData(response) {
      console.log(response)
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    showModal(data) {
      this.loadData(data).then(() => {
        this.$refs.modalWarpper.show()
      })
    },
    async loadData(data) {
      this.data = data
      if (this.data.giatri) {
        try {
          this.loading(true)
          this.images = this.GetData(await api.link_view_file(this.axios, { list_file: this.data.giatri }))
        } catch (e) {
          console.log(e)
        } finally {
          this.loading(false)
        }
      } else {
        this.images = []
      }
    },
    async uploadImage(event) {
      const formData = new FormData()
      let count = 0
      for (const file of event.target.files) {
        if (file.type.includes('image')) {
          formData.append('files', file)
          count = count + 1
        }
      }
      if (count > 0) {
        try {
          this.loading(true)
          const response = await api.upload(this.axios, formData)
          console.log(response)
          if (response.data.error_code === 'BSS-00000000') {
            const uploadedImages = response.data.data
            const images = this.GetData(await api.link_view_file(this.axios, { list_file: uploadedImages.join(',') }))
            this.images = this.images.concat(images)
          }
        } catch (e) {
          console.log(e)
          this.$toast.error('Upload hình ảnh không thành công')
        } finally {
          this.loading(false)
        }
      }
    },
    removeImage(i) {
      this.images.splice(i, 1)
    },
    resizeImg(img, height, width) {
      img.height = height
      img.width = width
    },
    CapNhatBnt() {
      if (this.images.map((item) => item.file_name).join(',').length > 200) {
        this.$toast.info('Vượt quá giới hạn số hình ảnh')
      } else if (this.data.thuoctinh_id) {
        this.data.giatri = this.images.map((item) => item.file_name).join(',')
        this.$store.dispatch('updateDeviceType/setValueThuocTinh', { id: this.data.thuoctinh_id, gttt_id: null, giatri: this.data.giatri })
        this.$refs.modalWarpper.hide()
      }
    },
    XoaBnt() {},
    HuyBnt() {
      this.$refs.modalWarpper.hide()
    }
  }
}
</script>
