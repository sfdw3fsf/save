<template src="./index.html"></template>
<script>
import Vue from 'vue';

export default {
  name: 'ModalTraPhieu',
  props: { modalID: null },
  components: {
    
  },
  created: async function () {
  },
  validations: {

  },
  watch: {

  },
  computed: {

  },
  methods: {
    closeModal: function () {
      this.$refs.ModalChuyenXuly.hide(this.modalID)
    },
    async show(item, mode, phieutcId) {
      this.currentMode = mode
      this.phieutcId = phieutcId
      this.thongTinPhieutc = item;
      this.donviId = this.$root.context.user.getProperty("donvi_id");

      // Reset form data
      this.resetForm();
      this.$refs.ModalChuyenXuly.show();
    },

    onSave: function () {
      if (this.validateForm()) {
        try {
          this.$root.showLoading(true);
          this.traPhieu();
        } catch (error) {
          this.handleError(error);
        } finally {
          this.$root.showLoading(false);
        }
      }
    },

    async traPhieu() {
      try {
        this.$root.showLoading(true);
        const payload = {
          "isHoanThanh": null,
          "lydoTraphieu": this.formData.lyDo.trim(),
          "nguoiChuyenXl": null,
          "phieutcId": this.phieutcId,
          "trangthai": "THICONG_TPHIEU",
        };

        const rs = await this.$root.context.post(
          `/web-ecms/idc/cap-phat-thu-hoi-ip/cap-nhat-thong-tin-phieu-tc`,
          payload
        );
        
        this.$emit('saved', rs.data);
        this.$toast.success("Trả phiếu thành công");
        this.closeModal();
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || "Trả phiếu thất bại");
      } finally {
        this.$root.showLoading(false);
      }
    },
    
    resetForm: function () {
      this.formData = {
        lyDo: ''
      };
      this.resetValidation();
    },

    resetValidation: function () {
      this.controls.txtLyDo.invalid = false;
    },

    validateForm: function () {
      let isValid = true;

      // Validate required fields
      if (!this.formData.lyDo || this.formData.lyDo.trim() === '') {
        isValid = false;
        this.controls.txtLyDo.invalid = true;
      } else {
        this.controls.txtLyDo.invalid = false;
      }

      if (!isValid) {
        this.$root.toastError('Vui lòng nhập lý do trả phiếu!');
      }

      return isValid;
    },

    handleError: function (error) {
      if (error.response && error.response.data && error.response.data.message_detail) {
        this.$root.toastError(error.response.data.message_detail);
      } else {
        this.$root.toastError('Đã xảy ra lỗi!');
      }
    }
  },
  data: function () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      thongTinPhieutc: {},
      donviId: 0,
      phieutcId: 0,
      
      // Form data
      formData: {
        lyDo: ''
      },
      
      // Form controls
      controls: {
        txtLyDo: { enabled: true, invalid: false }
      }
    }
  },
  mounted: function () { }
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
