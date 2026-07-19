<template src="./ChuyenXuLy.html"></template>
<script>
import Vue from 'vue';

export default {
  name: 'ModalChuyenXuLy',
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
      donviId: null,
      khachHang: "Nội bộ IDC",
      thongTinPhieutc: {},
      // Dropdown lists
      dsNhanvien: [],

      currentPhieuItem: {
        id: 0,
        maCv: '',
        tenDv: '',
        nhanVienTH:'',
        nhanVienTHId: 0,
        ngayTao: '',
        tenCv: '',
        trangThai: '',
        maCvCha: '',
        tenKhachHang: '',
        maThueBao: '',
        ghiChu: '',
        hieuLuc: 1,
      },

      // Form controls
      controls: {
        selectNhanVienTH: { enabled: true, invalid: false },
      }

    }
  },
  created: async function () {
  },
  methods: {
    closeModal: function () {
      this.$refs.ModalChuyenXuly.hide()
    },
    async dialogOpen(thongtinPhieuTc, phieutcId) {
      try {
        this.$root.showLoading(true);
        //this.currentMode = mode
        this.phieutcId = phieutcId
        console.log('phieutcId:', this.phieutcId);
        this.thongTinPhieutc = thongtinPhieuTc;
        this.donviId = this.$root.context.user.getProperty("donvi_id");
        console.log('donvi',this.donviId);
        this.dsNhanvien = await this.loadNhanVien();
        this.$refs.ModalChuyenXuly.show();
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || "Lỗi mở dialog");
      } finally {
        this.$root.showLoading(false);
      }
    },


    onSave: async function () {
      console.log('onSave called');
      const valid = this.validateForm();
      console.log('validateForm result:', valid);
      if (valid) {
        try {
          this.$root.showLoading(true);
          await this.chuyenXuLy();

        } catch (error) {
          console.log(error);
          this.$toast.error(error.response?.data?.message_detail || "Lỗi lưu dữ liệu");
        } finally {
          this.$root.showLoading(false);
          this.closeModal()
        }
      }
    },

    async chuyenXuLy() {
      let flag = await this.$confirm(
        "Bạn có chắc chắn muốn chuyển xử lý phiếu này không?",
        "Xác nhận chuyển xử lý",
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

      if (flag) {
        try {
          let response = null;
          const payload = {
            isHoanThanh: null,
            lydoTraphieu: null,
            nguoiChuyenXl: Number(this.currentPhieuItem.nhanVienTHId),
            phieutcId: this.phieutcId,
            trangthai: "THICONG_DTH",
          };
          console.log('Chuyen xu ly payload:', payload);
          this.$root.showLoading(true);
          response = await this.$root.context.post(
            '/web-ecms/thicong-rack/capnhat-trangthai-phieutc',
            payload
          );
          console.log('Chuyen xu ly response:', response);
          if (response ) {
            this.$toast.success("Chuyển xử lý thành công");
            this.$router.push({
              path: '/tainguyen-idc/ketqua-lapdat-rack'
            });
          } else {
            this.$toast.error("Chuyển xử lý thất bại");
          }
          this.closeModal();
        } catch (error) {
          this.$toast.error(error.response?.data?.message_detail || "Chuyển xử lý thất bại");
        } finally {
          this.$root.showLoading(false);
        }
      }
    },

    // API methods
    loadInitialData: async function () {
      try {
        this.$root.showLoading(true);

      } finally {
        this.$root.showLoading(false)
      }
    },
    loadNhanVien: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-ecms/ha-tang/thongtin-u/danhsach-nhanvien/${this.donviId}`
        );
        return rs.data;
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || "Lỗi tải nhân viên");
      } finally {
        this.$root.showLoading(false);
      }
    },


    validateForm: function () {
      let isValid = true;
      // Validate required fields
      if (!this.currentPhieuItem.nhanVienTHId) {
        isValid = false;
        this.controls.selectNhanVienTH.invalid = true;
      } else {
        this.controls.selectNhanVienTH.invalid = false;
      }
      if (!isValid) {
        this.$root.toastError('Vui lòng điền đầy đủ thông tin bắt buộc!');
      }
        return isValid;
    },
    onOverlayClick: function() {
      this.$refs.ModalChuyenXuly.hide();
    },

  },
}
</script>
<style>

.error-border {
  border: 2px solid #dc3545 !important;
  border-radius: 4px;
}

.error-border:focus {
  border-color: #dc3545 !important;
  box-shadow: 0 0 0 0.2rem rgba(220, 53, 69, 0.25) !important;
}
</style>
