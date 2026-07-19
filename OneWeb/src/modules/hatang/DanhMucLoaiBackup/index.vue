<template src="./index.template.html"></template>
<script>
export default {
  name: 'DanhMucLoaiBackup',
  data() {
    return {
      isDisableSave: true,
      isDisableNew: false,
      isDisableUpdate: false,
      isDisableDelete: false,
      dsSelected: [],
      dsLoaiBackup: [],
      form: {
        id: 0,
        ten: '',
        ghichu: '',
        hieuluc: 1
      }
    }
  },
  mounted: async function() {
    await this.getDanhSach()
  },

  methods: {
    getDanhSach: async function() {
      try {
        const rs = await this.$root.context.post(`/web-ecms/danhmuc-loai-backup/get-all-loai-backup`)
        if (rs.error == '200' && rs.error_code == 'BSS-00000000') this.dsLoaiBackup = rs.data.data
        this.dsLoaiBackup.forEach((item) => {
          item.HIEU_LUC_TEXT = item.HIEU_LUC == 1 ? 'Hiệu lực' : 'Không hiệu lực'
        })
      } catch (error) {}
    },
    checkForm: function() {
      var kq = true
      if (this.form.ten.trim().length == 0) {
        this.$root.toastError('Không được để trống tên')
        kq = false
      }
      return kq
    },
    setDanhSach: async function() {},
    updateLoaiBackup: async function() {
      if (this.checkForm() && !this.isDisableUpdate) {
        try {
          this.form.hieuluc = this.form.hieuluc ? 1 : 0
          this.form.ten = this.form.ten.trim()
          this.form.ghichu = this.form.ghichu.trim()
          const rs = await this.$root.context.post(`/web-ecms/danhmuc-loai-backup/update-loai-backup`, this.form)
          if (rs.error == '200' && rs.error_code == 'BSS-00000000') {
            this.$toast.success('Cập nhật thành công')
          }
          await this.getDanhSach()
        } catch (error) {
          this.$toast.error(error.response?.data?.message_detail || error.message_detail || 'Có lỗi xảy ra')
        }
      }
    },
    createLoaiBackup: async function() {
      if (this.checkForm() && !this.isDisableSave) {
        try {
          delete this.form.id
          this.form.hieuluc = this.form.hieuluc ? 1 : 0
          this.form.ten = this.form.ten.trim()
          this.form.ghichu = this.form.ghichu.trim()
          const rs = await this.$root.context.post(`/web-ecms/danhmuc-loai-backup/insert-loai-backup`, this.form)
          if (rs.error == '200' && rs.error_code == 'BSS-00000000') {
            this.$toast.success('Thêm mới thành công')
          }
          await this.getDanhSach()
        } catch (error) {
          this.$toast.error(error.response?.data?.message_detail || error.message_detail || 'Có lỗi xảy ra')
        }
      }
    },
    deleteDanhSach: async function() {
      try {
        if (!this.isDisableDelete) {
          const confirmed = await this.$confirm('Bạn có chắc chắn muốn xóa không?', {
            title: 'Xác nhận xóa',
            type: 'warning'
          })

          if (!confirmed) return

          let count = 0
          for (let item of this.dsSelected) {
            const rs = await this.$root.context.post(`/web-ecms/danhmuc-loai-backup/delete-loai-backup`, { id: item })
            if (rs.error == '200' && rs.error_code == 'BSS-00000000') count++
          }

          this.$toast.success('Xóa phiếu thành công ' + count + ' phiếu')
        }
      } catch (error) {
        if (error.message_detail) this.$toast.error(error.message_detail)
      } finally {
        await this.getDanhSach()
      }
    },
    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
    },
    onNew() {
      if (!this.isDisableNew) {
        this.form.id = 0
        this.form.ten = ''
        this.form.ghichu = ''
        this.form.hieuluc = 1
        this.isDisableNew = true
        this.isDisableDelete = true
        this.isDisableSave = false
        this.isDisableUpdate = true
      }
    },

    onSelectRowChanged(selected) {
      this.isDisableSave = true
      this.isDisableNew = false
      this.isDisableUpdate = false
      this.isDisableDelete = false
      this.form.id = selected.data.ID
      this.form.ten = selected.data.TEN
      this.form.ghichu = selected.data.GHI_CHU
      this.form.hieuluc = selected.data.HIEU_LUC
    }
  },
  watch: {}
}
</script>
<style>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>