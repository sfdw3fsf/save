<template src="./thicong-thaodo-rack-modal.html"></template>

<script>

export default {
  props: {
    phieuThiCongId: {
     type: Number,
     default: null,
    },
  },
  data: function() {
    return {
      // Biến lưu dữ liệu
      dataRack: {},
      dataThicongRack: {},
      rackId: null,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
    }
  },

  methods: {
    // Phần gọi các API
    getChiTietRack: async function(rackId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "CHITIET-RACK",
          thamSo1:rackId
        });
        result = rs.data || []; // Lấy bản ghi đầu tiên từ mảng data
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getThicongRack: async function(rackId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "THICONG-RACK-THEO-PHIEUTC",
          thamSo1:this.phieuThiCongId,
          thamSo2:rackId
        });
        result = rs.data || []; // Lấy bản ghi đầu tiên từ mảng data
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    thiCongThaoDoRack: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/thicong-rack/thicong-thaodo-rack", {
          tcRackId: args.THICONGRACK_ID,
          rackId: args.RACK_ID,
          liDo: args.NOIDUNG,
          ghiChu: args.GHICHU || '',
        });
        result = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    clearAll() {
      this.dataRack = {};
    },
    closeDialog() {
      this.clearAll();
      this.$refs.ModalThaoDoRack.hide();
    },
    async dialogOpen(rackId) {
      try {
        this.$root.showLoading(true);
        this.isEditMode = false;
        this.clearAll();
        this.rackId = rackId;
        if (this.$refs.ModalThaoDoRack) {
          if (rackId > 0) {
            let rs1 = await this.getChiTietRack(this.rackId);
            this.dataRack = rs1[0] || {};
            let rs2 = await this.getThicongRack(this.rackId);
            this.dataThicongRack = rs2[0] || {};
            this.$refs.ModalThaoDoRack.show();
          } else {
            this.$toast.error('Không tìm thấy ID rack!');
          }
        } else {
          this.$toast.error('Dialog không tồn tại hoặc không được tham chiếu đúng');
        }
      } catch (e) {
        console.log('Lỗi:', e);
        this.$toast.error('Đã xảy ra lỗi khi mở dialog');
      } finally {
        this.$root.showLoading(false);
      }
    },

    // Validate form
    validateForm() {
      let errors = [];
      if (!this.dataThicongRack.NOIDUNG) errors.push('Lí do tháo dỡ không được để trống');
      return errors;
    },

    // Xử lý sự kiện nút bấm
    btnGhiLai_Click: async function () {
      if (!this.phieuThiCongId) {
        this.$toast.warning('Không có thông tin phiếu thi công');
        return;
      }
      const errors = this.validateForm();
      if (errors.length) {
        this.$toast.error(errors.join('\n'));
        return;
      } else {
        let flag = await this.$confirm(
          "Bạn có muốn lưu thông tin này không ?",
          "Xác nhận",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "info",
            dangerouslyUseHTMLString: true,
          }
        )
          .then((s) => true)
          .catch((e) => {
            return false;
          });
        if (flag) {
          try {
            this.$root.showLoading(true);
            let rs = await this.thiCongThaoDoRack(this.dataThicongRack);
            if (rs) {
              this.$root.toastSuccess("Tháo dỡ Rack thành công");
              this.$emit('rackAllocated', this.dataRack);
              this.$parent.loadHistoryList();
              this.closeDialog();
            }
            else {
              this.$root.toastError("Tháo dỡ Rack thất bại");
            }
            this.$refs.ModalThaoDoRack.hide();
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    onOverlayClick: function() {
      this.clearAll();
      this.$refs.ModalThaoDoRack.hide();  // hoặc xử lý khác
    },
  }
}
</script>
