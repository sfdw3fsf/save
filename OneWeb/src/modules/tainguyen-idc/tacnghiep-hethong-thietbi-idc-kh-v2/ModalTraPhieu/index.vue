<template src="./index.html"></template>
<script>
import Vue from 'vue'
import api from '../api'

export default {
  name: 'ModalTraPhieu',
  props: { modalID: null },
  components: {},
  created: async function() {},
  validations: {},
  watch: {},
  computed: {},
  methods: {
    closeModal: function() {
      this.$refs.ModalTraPhieu.hide(this.modalID)
    },
    async show(phieutcId) {
      this.phieutcId = phieutcId
      this.lyDoTra = ''
      this.resetValidation()
      this.$refs.ModalTraPhieu.show()
    },

    onSave: function() {
      if (this.validateForm()) {
        try {
          this.$root.showLoading(true)
          this.traPhieu()
          this.closeModal()
        } catch (error) {
          this.handleError(error)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    async traPhieu() {
      try {
        this.$root.showLoading(true)
        const rs = await api.traPhieuThiCong(this.axios, {
          phieuThiCongId: this.phieutcId,
          trangThaiId: 23,
          nhanVienId: Number(this.$root.token.getNhanVienID()),
          lyDoTra: this.lyDoTra
        })
        
        if (rs && rs.error_code === 'BSS-00000000') {
          this.$emit('saved', rs)
          this.$root.toastSuccess('Trả phiếu thi công thành công!')
        } else {
          this.$root.toastError(rs?.message_detail || 'Có lỗi xảy ra khi trả phiếu thi công')
        }
      } catch (error) {
        this.$root.toastError(error?.message || 'Có lỗi xảy ra khi trả phiếu thi công')
      } finally {
        this.$root.showLoading(false)
      }
    },

    resetValidation: function() {
      this.controls.txtLyDoTra.invalid = false
    },

    validateForm: function() {
      let isValid = true

      // Validate lyDoTra field
      if (!this.lyDoTra || this.lyDoTra.trim() === '') {
        isValid = false
        this.controls.txtLyDoTra.invalid = true
      } else {
        this.controls.txtLyDoTra.invalid = false
      }

      if (!isValid) {
        this.$root.toastError('Vui lòng nhập lý do trả phiếu!')
      }

      return isValid
    },

    handleError: function(error) {
      if (error.response && error.response.data && error.response.data.message_detail) {
        this.$root.toastError(error.response.data.message_detail)
      } else {
        this.$root.toastError('Đã xảy ra lỗi!')
      }
    }
  },
  data: function() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      phieutcId: null,
      lyDoTra: '',

      // Form controls
      controls: {
        txtLyDoTra: { enabled: true, invalid: false }
      }
    }
  },
  mounted: function() {}
}
</script>
<style>
.popup-box {
  max-height: 90vh !important;
  overflow-y: auto;
}

.error-border {
  border: 2px solid #dc3545 !important;
  border-radius: 4px;
}

.error-border:focus {
  border-color: #dc3545 !important;
  box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.25) !important;
}
</style>
