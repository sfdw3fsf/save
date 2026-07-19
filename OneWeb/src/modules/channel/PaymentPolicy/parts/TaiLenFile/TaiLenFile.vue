<template src="./TailenFile.html"> </template>

<script>
import api from '../../API.js'
export default {
  data() {
    return {
      dsFile: [],
      dsLoaiFile: [],
      cbbLoaiFile: null,
      fileUpload: null,
      fileUploadName: '',
      file_id: null,
      txtGhiChu: '',
      disabledThemMoi: false,
      tbColumns: [
        // {
        //   fieldName: 'stt',
        //   headerText: 'STT',
        //   width: 60,
        //   textAlign: 'center'
        // },
        {
          fieldName: 'loai_file',
          headerText: 'Loại file'
        },
        {
          fieldName: 'ten_file',
          headerText: 'Tên file'
        },
        {
          fieldName: 'ghi_chu',
          headerText: 'Ghi chú'
        },
        {
          fieldName: 'thaotac',
          headerText: 'Thao tác',
          width: 100,
          textAlign: 'center',
          template: this.colThaoTac(this)
        }
      ]
    }
  },
  props: {
    coche_id: {
      type: Number,
      default: null
    }
  },
  methods: {
    rowSelected(row) {
      const dt = row.data
      this.disabledThemMoi = false
      this.cbbLoaiFile = dt.loaifile_id
      this.txtGhiChu = dt.ghi_chu
      this.fileUploadName = dt.ten_file
      this.file_id = dt.file_id
    },
    async handleShowModal() {
      if (!this.coche_id) {
        this.$toast.error('Không tìm thấy cơ chế !')
        this.hideModal()
      }
      let res = await api.getDSLoaiFile(this.axios, {})
      this.dsLoaiFile = res.data.data.map((item) => {
        return {
          id: item.loaifile_id,
          text: item.loai_file
        }
      })
      this.cbbLoaiFile = this.dsLoaiFile[0].id
      this.loadingFileHS()
    },
    async loadingFileHS() {
      let res = await api.chiTietCoChe(this.axios, { coche_id: this.coche_id })
      this.dsFile = []
      if (res.data.data.dsFiles && res.data.data.dsFiles.length > 0) {
        this.dsFile = res.data.data.dsFiles.map((item, index) => {
          const loaifile_id = this.dsLoaiFile.find((i) => i.text == item.loai_file).id
          return {
            stt: index + 1,
            ten_file: item.ten_file,
            loaifile_id: loaifile_id,
            loai_file: item.loai_file,
            ghi_chu: item.ghichu,
            allowChange: false,
            url: item.url,
            file_id: item.file_id
          }
        })
        
      }
    },
    colThaoTac(parent) {
      return function() {
        return {
          template: {
            template: `<div>
              <button class="btn btn-primary" @click="previewFile"><i class="icon fa fa-eye"></i></button>
              <button class="btn btn-danger" @click="removeFile"><i class="fa fa-trash"></i></button>
            </div>`,
            methods: {
              async removeFile() {
                let isConfirm = await this.$bvModal.msgBoxConfirm('Bạn có chắc chắn muốn xoá file ?', {
                  title: 'Xác nhận',
                  size: 'sm',
                  buttonSize: 'sm',
                  okVariant: 'danger',
                  okTitle: 'Đồng ý',
                  cancelTitle: 'Hủy bỏ',
                  footerClass: 'p-2',
                  hideHeaderClose: false,
                  centered: true
                })
                if (isConfirm) {
                  try {
                    this.loading(true)
                    let res = await api.deleteFile(parent.axios, {
                      coche_id: parent.coche_id,
                      filehs_id: this.data.file_id
                    })
                    if (res.data.error_code == 'BSS-00000000') {
                      parent.$toast.success('Xoá file thành công !')
                      parent.loadingFileHS()
                    } else parent.$toast.error(res.data.message)
                  } catch (error) {
                  } finally {
                    this.loading(false)
                  }
                }
              },
              async previewFile(){
                const res = await api.link_view_file(this.axios, { list_file: this.data.url })
                try {
                  var old_url = new URL(res.data.data[0].file_name)
                } catch (_) {
                  old_url = null
                }
                window.open(old_url ? old_url : res.data.data[0].file_url, '_blank')
              }
            },
            computed: {
              // disabled() {
              //   return !this.data.allowChange
              // }
            }
          }
        }
      }
    },
    hideModal() {
      this.$refs.TailenFileModal.hide()
      this.$emit('closeModal')
      this.dsFile = []
      this.dsLoaiFile = []
      this.cbbLoaiFile = null
      this.fileUpload = null
      this.fileUploadName = ''
      this.txtGhiChu = ''
      this.file_id = null
    },
    showModal() {
      this.dsFile = []
      this.$refs.TailenFileModal.show()
    },
    handleUploadFile() {
      this.$refs.fileInput.click()
    },
    uploadFile(event) {
      if (event.target.files.length == 0) return
      // this.dsFile = []
      if (this.cbbLoaiFile == null) {
        this.$toast.error('Chưa chọn loại file')
        return
      }
      const file = event.target.files[0]
      this.fileUpload = file
      this.fileUploadName = file.name
      event.target.value = ''
    },
    clearForm() {
      this.disabledThemMoi = true
      this.fileUpload = null
      this.fileUploadName = ''
      this.txtGhiChu = ''
      this.cbbLoaiFile = this.dsLoaiFile[0].id
      this.file_id = null
    },
    async saveNewFile() {
      if (!this.file_id) {
        //create new
        if (!this.fileUpload || !this.fileUploadName) {
          this.$toast.error('Chưa có file để tải lên')
          return
        }
        if (!this.coche_id) {
          this.$toast.error('Xảy ra lỗi, vui lòng thử lại sau')
          return
        }
        if (!this.cbbLoaiFile) {
          this.$toast.error('Chưa chọn loại file')
          return
        }
        try {
          this.loading(true)
          let formData = new FormData()
          formData.append('files', this.fileUpload)
          let res = await api.uploadFile(this.axios, formData)
          if (res.data.error_code == 'BSS-00000000') {
            const input = {
              coche_id: this.coche_id,
              files: [
                {
                  loaifile_id: this.cbbLoaiFile,
                  ghichu: this.txtGhiChu,
                  url: res.data.data[0],
                  ten_file: this.fileUploadName
                }
              ]
            }
            res = await api.capNhatFileCSTL(this.axios, input)
            if (res.data.error_code == 'BSS-00000000') {
              this.$toast.success(res.data.data)
              this.clearForm()
              this.loadingFileHS()
            } else {
              this.$toast.error(res.data.data)
            }
          } else {
            this.$toast.error('Lỗi trong quá trình upload file!')
          }
        } catch (error) {
        } finally {
          this.loading(false)
        }
      } else {
        //update
        const temp = this.dsFile.find((item) => item.file_id == this.file_id)
        console.log(temp);
        try {
          this.loading(true)
          let res = await api.deleteFile(this.axios, {
            coche_id: this.coche_id,
            filehs_id: this.file_id
          })
          if (res.data.error_code != 'BSS-00000000') {
            this.$toast.error('Xảy ra lỗi: ' + res.data.message)
            return
          }
          //save new data
          const input = {
            coche_id: this.coche_id,
            files: [
              {
                loaifile_id: this.cbbLoaiFile,
                ghichu: this.txtGhiChu,
                url: temp.url,
                ten_file: temp.ten_file
              }
            ]
          }
          res = await api.capNhatFileCSTL(this.axios, input)
          if (res.data.error_code == 'BSS-00000000') {
            this.$toast.success(res.data.data)
            this.loadingFileHS()
          } else {
            this.$toast.error(res.data.data)
          }
        } catch (error) {
        } finally {
          this.loading(false)
        }
      }
    },
    async saveAllFile() {
      if (this.dsFile.length == 0) {
        this.$toast.error('Chưa có file để tải lên')
        return
      }
      if (!this.coche_id) {
        this.$toast.error('Chưa có cơ chế để tải lên file')
        return
      }
      let formData = new FormData()
      for (let i = 0; i < this.dsFile.length; i++) {
        // if(!this.dsFile[i].loaifile_id){
        //   this.$toast.error('Chưa chọn loại file cho file thứ ' + this.dsFile[i].stt)
        //   return
        // }
        formData.append('files', this.dsFile[i].file)
      }
      try {
        this.loading(true)
        let res = await api.uploadFile(this.axios, formData)
        if (res.data.error_code == 'BSS-00000000') {
          let dsFile = []
          for (let i = 0; i < this.dsFile.length; i++) {
            dsFile.push({
              loaifile_id: this.dsFile[i].loaifile_id * 1,
              ghichu: this.dsFile[i].ghi_chu,
              url: res.data.data[i],
              ten_file: this.dsFile[i].ten_file
            })
          }
          let input = {
            coche_id: this.coche_id,
            files: dsFile
          }
          res = await api.capNhatFileCSTL(this.axios, input)
          if (res.data.error_code == 'BSS-00000000') {
            this.$toast.success(res.data.data)
            this.$emit('updatedFile')
            this.hideModal()
          } else {
            this.$toast.error(res.data.data)
          }
        }
        else {
          this.$toast.error('Lỗi trong quá trình upload file!')
        }
      } catch (error) {
      } finally {
        this.loading(false)
      }
    }
  },
  computed: {
    showRemind() {
      return this.dsFile.length == 0
    }
  }
}
</script>

<style lang="scss" scoped>
.remind-text {
  color: rgba(73, 80, 87, 0.7);
  text-align: center;
  margin-bottom: 10px;
}
</style>
