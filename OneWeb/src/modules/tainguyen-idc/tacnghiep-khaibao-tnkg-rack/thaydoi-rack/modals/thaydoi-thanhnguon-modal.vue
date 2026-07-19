<template src="./thaydoi-thanhnguon-modal.html"></template>

<script>
export default {

  data: function () {
    return {
      currentItem: {},
      thanhNguonId: 0,
      buttons: {
        btnGhiLai: {
          enabled: false,
        },
        btnHuy: {
          enabled: false,
        },
        btnSua: {
          enabled: true,
        },
      },
      loaiThanhNguonList: [],
      kieuLapDatList: [],
      trangThaiList: [],
      viTriUnitList: [],
      isEditMode: false,
      vitriLapDatNew: null,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
    };
  },

  mounted: function () {
    this.kieuLapDatList = [
      { KIEU_LD: 1, KIEU_LAPDAT: "Dọc trái" },
      { KIEU_LD: 2, KIEU_LAPDAT: "Dọc phải" },
      { KIEU_LD: 3, KIEU_LAPDAT: "Ngang trên" },
      { KIEU_LD: 4, KIEU_LAPDAT: "Ngang dưới" }
    ];
    this.trangThaiList = [
      { TRANGTHAI_ID: 1, TRANGTHAI_SUDUNG: "On" },
      { TRANGTHAI_ID: 2, TRANGTHAI_SUDUNG: "Off" }
    ];
  },
  methods: {
    getThanhNguonByID: async function (tnId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: 'THANHNGUON-BY-ID',
          thamSo1: tnId
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getData: async function(tnId) {
      let rs = await this.getThanhNguonByID(tnId);
      if (rs) {
        this.clearAll();
        this.currentItem = rs[0] || {}; // Giả sử API trả về mảng, lấy phần tử đầu tiên
      } else {
        this.$toast.error('Tải dữ liệu không thành công!');
      }
    },
    updateItem: async function (args) {
      let result = false;
      try {
        let rs = await this.$root.context.post('/web-ecms/idc/thanhnguon-trenrack', {
          thanhNguonId: this.thanhNguonId || 0,
          loaiTnId: args.LOAITN_ID,
          rackId: args.RACK_ID,
          ten: args.TEN_TN,
          moTa: args.MOTA,
          trangThaiId: args.TRANGTHAI_ID,
          viTriLd: this.vitriLapDatNew,
          kieuLd: args.KIEU_LD,
          ghiChu: args.GHICHU,
          suDung: args.SUDUNG ? 1 : 0,
        });
        result = rs.data;
      } catch(e) {
        if(!(e.result == null || e.result == undefined)){
          if (e.result?.data?.message_detail) {
          this.$root.toastError(e.result.data.message_detail);
          }
        }
      }
      return result;
    },
    getRackData: async function () {
      let result = [];
      try {
        let response = await this.$root.context.post("/web-ecms/thongtin-rack/get-tt-rack");
        result = response.data || [];
      } catch (error) {
        if (error.response?.data?.message_detail) {
          this.$root.toastError(error.response.data.message_detail);
        }
      }
      return result;
    },
    getVitriUData: async function () {
      let result = [];
      try {
        let response = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "VITRI-U-TREN-THANHNGUON",
          thamSo1:this.currentItem.RACK_ID
        });
        result = response.data || [];
      } catch (error) {
        if (error.response?.data?.message_detail) {
          this.$root.toastError(error.response.data.message_detail);
        }
      }
      return result;
    },


    clearAll() {
      this.currentItem = {};
    },
    closeDialog() {
      this.clearAll();
      this.enableControls(false);
      this.$refs.ModalThayDoiThanhNguon.hide();
    },
    async dialogOpen(tnId) {
      try {
        this.$root.showLoading(true);
        this.isEditMode = false;
        this.clearAll();
        this.thanhNguonId = tnId;
        if (this.$refs.ModalThayDoiThanhNguon) {
          if (tnId > 0) {
            await this.getData(tnId);
            this.viTriUnitList = await this.getVitriUData();
            this.$refs.ModalThayDoiThanhNguon.show();
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
    enableControls: function (args) {
      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnHuy.enabled = this.buttons.btnGhiLai.enabled;
      this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },

    // Validate form
    validateForm() {
      let errors = [];
      if (!this.currentItem.TEN_TN) errors.push('Tên Thanh Nguồn không được để trống');
      if (!this.currentItem.TRANGTHAI_ID) errors.push('Trạng thái không được để trống');
      if (!this.currentItem.LOAITN_ID) errors.push('Loại thanh nguồn không được để trống');
      if (!this.currentItem.KIEU_LD) errors.push('Kiểu lắp đặt không được để trống');
      if (!this.vitriLapDatNew) errors.push('Vị trí không được để trống');

      return errors;
    },
    btnGhiLai_Click: async function (args) {
      const errors = this.validateForm();
      if (errors.length) {
        this.$toast.error(errors.join('\n'));
        return;
      } else{
        let flag = await this.$confirm(
          "Có chắc bạn muốn lưu lại thông tin này?",
          "Xác nhận thực hiện",
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
            let rs = await this.updateItem(this.currentItem);
            if (rs) {
              this.$root.toastSuccess("Cập nhật thanh nguồn trên rack thành công");
              this.$emit('thanhNguonAllocated');
              this.closeDialog();
            }
            else{
              this.$root.toastError("Cập nhật thanh nguồn trên rack thất bại");
            }
            this.enableControls(!rs);
            this.isEditMode = !rs;
            this.$refs.ModalThayDoiRack.hide();
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnHuy_Click: async function (args) {
      try {
        this.$root.showLoading(true);
        let rs = await this.getThanhNguonByID(this.thanhNguonId);
        this.currentItem = rs[0];
        this.isEditMode = false;
        this.enableControls(false);
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnSua_Click: async function (args) {
      this.isEditMode = true;
      this.enableControls(true);
    },
    onOverlayClick: function() {
      this.clearAll();
      this.$refs.ModalThayDoiThanhNguon.hide();  // hoặc xử lý khác
    },
  },
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}

/* CSS cho validation error */
.error-input {
  border: 1px solid #f57f6c !important;
  animation-name: bounce;
  animation-duration: 0.5s;
  animation-delay: 0.25s;
}

@keyframes bounce {
  0% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
  37% {
    transform: translateX(5px);
    timing-function: ease-out;
  }
  55% {
    transform: translateX(-5px);
    timing-function: ease-in;
  }
  73% {
    transform: translateX(4px);
    timing-function: ease-out;
  }
  82% {
    transform: translateX(-4px);
    timing-function: ease-in;
  }
  91% {
    transform: translateX(2px);
    timing-function: ease-out;
  }
  96% {
    transform: translateX(-2px);
    timing-function: ease-in;
  }
  100% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
}
</style>
