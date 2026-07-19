<template src="./index.html"></template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
export default {
  name: 'DanhMucCapDoHaTang',
  data() {
    return {
      isDisableSave: true,
      isDisableNew: false,
      isDisableUpdate: false,
      isDisableDelete: false,
      dsSelected: [],
      dsCapDoHaTang: [],
      form: {
        id: 0,
        ten: '',
        ghiChu: '',
        hieuLuc: 1
      }
    }
  },
  mounted: async function() {
    await this.getDanhSach()
  },

  methods: {
    getDanhSach: async function() {
      try {
        const rs = await this.$root.context.post(`/web-ecms/hatang/hatang_capdo/getall`)
        if (rs.error == '200' && rs.error_code == 'BSS-00000000') this.dsCapDoHaTang = rs.data
        this.dsCapDoHaTang.forEach((item) => {
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
    updateCapDoHaTang: async function() {
      if (this.checkForm() && !this.isDisableUpdate) {
        try {
          this.form.hieuLuc = this.form.hieuLuc ? 1 : 0
          const rs = await this.$root.context.post(`/web-ecms/hatang/hatang_capdo/update`, this.form)
          if (rs.error == '200' && rs.error_code == 'BSS-00000000') {
            this.$toast.success('Cập nhật thành công')
          }
          await this.getDanhSach()
        } catch (error) {
          this.$toast.error(error.response?.data?.message_detail || error.message_detail || 'Có lỗi xảy ra')
        }
      }
    },
    createCapDoHaTang: async function() {
      if (this.checkForm() && !this.isDisableSave) {
        try {
          delete this.form.id
          this.form.hieuLuc = this.form.hieuLuc ? 1 : 0
          const rs = await this.$root.context.post(`/web-ecms/hatang/hatang_capdo/update`, this.form)
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
          let count = 0

          for (let item of this.dsSelected) {
            const rs = await this.$root.context.post(`/web-ecms/hatang/hatang_capdo/delete`, { id: item })
            if (rs.error == '200' && rs.error_code == 'BSS-00000000') count++
          }

          this.$toast.success('Xóa phiếu thành công ' + count + ' phiếu')
        }
      } catch (error) {
        const errorMessage = error.response?.data?.message_detail || error.message_detail || error.message || 'Có lỗi xảy ra khi xóa phân loại hạ tầng'
        this.$toast.error(errorMessage)
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
        this.form.ghiChu = ''
        this.form.hieuLuc = 1
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
      this.form.ghiChu = selected.data.GHI_CHU
      this.form.hieuLuc = selected.data.HIEU_LUC
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
