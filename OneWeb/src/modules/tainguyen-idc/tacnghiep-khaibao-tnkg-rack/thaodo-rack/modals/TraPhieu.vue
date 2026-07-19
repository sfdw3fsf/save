<template src="./TraPhieu.html"></template>
<script>
import Vue from 'vue';

export default {
  name: 'ModalTraPhieu',
  props: {
    phieuThiCongId: {
    type: Number,
    default: null,
    },
  },
  data: function () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
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
  created: async function () {
  },
  methods: {
    closeModal: function () {
      this.$refs.ModalTraPhieu.hide()
    },
    async dialogOpen(thongtinPhieuTc, phieutcId) {
      try {
        this.$root.showLoading(true);
        this.phieutcId = phieutcId
        this.thongTinPhieutc = thongtinPhieuTc;
        this.donviId = this.$root.context.user.getProperty("donvi_id");

        // Reset form data
        this.resetForm();
        this.$refs.ModalTraPhieu.show();
      } catch (error) {
        this.handleError(error);
      } finally {
        this.$root.showLoading(false);
      }
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
      let flag = await this.$confirm(
        "Bạn có chắc chắn muốn trả phiếu này không?",
        "Xác nhận trả phiếu",
        {
          confirmButtonText: "Xác nhận",
          cancelButtonText: "Hủy",
          type: "info",
          dangerouslyUseHTMLString: true,
        }
      )
      .then((s) => true)
      .catch((e) => {
        return false;
      });
      if(flag) {
        try {
          this.$root.showLoading(true);
          let response = null;
          const payload = {
            "isHoanThanh": null,
            "lydoTraphieu": this.formData.lyDo.trim(),
            "nguoiChuyenXl": null,
            "phieutcId": this.phieutcId,
            "trangthai": "THICONG_TPHIEU",
          };
          response = await this.$root.context.post(
            '/web-ecms/thicong-rack/capnhat-trangthai-phieutc',
            payload
          );
          if (response ) {
            this.$toast.success("Trả phiếu thành công");
            this.$router.push({
              path: '/tainguyen-idc/ketqua-thaodo-rack'
            });
          } else {
            this.$toast.error("Trả phiếu thất bại");
          }
          this.closeModal();
        } catch (error) {
          this.$toast.error(error.response?.data?.message_detail || "Trả phiếu thất bại");
        } finally {
          this.$root.showLoading(false);
        }
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
