<template src="./TraPhieu.html"></template>
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
          // this.closeModal()
        } catch (error) {
          this.handleError(error)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    async traPhieu() {
      if (!this.lyDoTra) {
        this.$toast.warning('Vui lòng nhập lý do trả phiếu.');
        return;
      }

      let flag = await this.$confirm(
        "Bạn có chắc chắn muốn trả phiếu không?",
        "Xác nhận trả phiếu",
        {
          confirmButtonText: "Xác nhận",
          cancelButtonText: "Huỷ",
          type: "warning",
          dangerouslyUseHTMLString: true,
        }
      )
        .then((s) => true)
        .catch((e) => {
          return false;
        });

      if (flag) {
        try {
          this.$root.showLoading(true)
          let response = await this.$root.context.post(`/web-ecms/idc/thi-cong/cap-phat-thu-hoi-u/tra-phieu/${this.phieutcId}`, {reason: this.lyDoTra});
          
          if (response && response.error && response.error == "200") {
            this.$toast.success('Trả phiếu thành công');
         
          } else {
            this.$toast.error('Trả phiếu thất bại');
          }
        } catch (error) {
          console.error('Error returning ticket:', error);
          this.$toast.error('Có lỗi xảy ra khi trả phiếu');
        } finally {
          this.closeModal()
          this.$root.showLoading(false)
        }
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
