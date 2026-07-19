<template src="./index.template.html"></template>
<script>
import Vue from 'vue';

export default {
  name: 'ModalChuyenXuly',
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
      this.nguoiCn = this.$root.context.user.getProperty("ten_nv");
      await this.loadInitialData();
      this.$refs.ModalChuyenXuly.show();
    },

    onAddNew: function () {
      this.resetCurrentItem();
      this.currentMode = 'ADD';
      this.resetValidation();
    },

    onSave: function () {
      if (this.validateForm()) {
        try {
          this.$root.showLoading(true);
          this.chuyenXuLy();
          this.closeModal()
        } catch (error) {
          this.handleError(error);
        } finally {
          this.$root.showLoading(false);
        }
      }
    },

    async chuyenXuLy() {
      try {
        this.$root.showLoading(true);
        const payload = {
          phieutcId: this.phieutcId,  // id phiếu
          nguoiXl: this.currentPhieuItem.nhanVienTHId,      // người xử lý mới
          nguoiCn: this.nguoiCn
        };

        const rs = await this.$root.context.post(
          `/web-ecms/ha-tang/thongtin-u/phieutc/${payload.phieutcId}/nguoi-xuly`,
          payload
        );
        this.$emit('saved', this.result);      

        this.$toast.success("Chuyển xử lý thành công");
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || "Chuyển xử lý thất bại");
      } finally {
        this.$root.showLoading(false);
      }
    },
    
    resetCurrentItem: function () {
      this.currentItem = {
      };
    },

    resetValidation: function () {
      Object.keys(this.controls).forEach(key => {
        this.controls[key].invalid = false;
      });
    },

    validateForm: function () {
      let isValid = true;

      // Validate required fields
      if (!this.currentPhieuItem.nhanVienTHId) {
        isValid = false;
      }

      // Add more validation rules as needed

      if (!isValid) {
        this.$root.toastError('Vui lòng điền đầy đủ thông tin bắt buộc!');
      }

      return isValid;
    },

    // API methods
    loadInitialData: async function () {
      try {
        this.$root.showLoading(true);

        this.dsNhanvien = await this.loadNhanVien();

      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    async loadNhanVien() {
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

    setCurrentItem: async function (item) {
      this.currentItem = {
        idcId: item.IDC_ID,
        matSanId: item.MATSAN_ID,
        goiCuocMangId: item.GOICUOC_ID || 0,
        quocTe: item.QUOCTE || '',
        trongNuoc: item.TRONGNUOC || ''
      }
    },

    updateItem: async function () {
      // API call to update item
    },

    deleteItem: async function () {
      // API call to delete item
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
      item: null,
      nguoiCn: '',
      currentMode: 'VIEW', // VIEW, ADD, EDIT,
      thongTinPhieutc: {},
      donViId: 0,
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

      // Dropdown lists
      dsNhanvien: [],
      
      // Form controls
      controls: {
        selectPhamViIdc: { enabled: false, invalid: false },
        selectMangThietBi: { enabled: false, invalid: false },
        selectLoaiThietBi: { enabled: false, invalid: false },
        selectLoaiKhachHang: { enabled: false, invalid: false },
        selectHeThong: { enabled: false, invalid: false },
        txtDonViQuanLy: { enabled: false, invalid: false },
        txtNguoiQuanLy: { enabled: false, invalid: false },
        selectLapDatTaiIdc: { enabled: false, invalid: false },
        txtTenThietBi: { enabled: false, invalid: false },
        selectToaNha: { enabled: false, invalid: false },
        selectMatSan: { enabled: false, invalid: false },
        txtDongSanPham: { enabled: false, invalid: false },
        selectPhongZone: { enabled: false, invalid: false },
        txtRack: { enabled: false, invalid: false },
        txtCauHinhChiTiet: { enabled: false, invalid: false },
        selectUTren: { enabled: false, invalid: false },
        selectUDuoi: { enabled: false, invalid: false },
        txtSerialNumber: { enabled: false, invalid: false },
        txtThoiDiemLapDat: { enabled: false, invalid: false },
        selectCanCuLapDat: { enabled: false, invalid: false },
        txtTagNumber: { enabled: false, invalid: false },
        txtMaTheTaiSan: { enabled: false, invalid: false },
        selectTrangThaiTaiSan: { enabled: false, invalid: false },
        txtCongSuatThietKe: { enabled: false, invalid: false },
        selectTrangThaiVanHanh: { enabled: false, invalid: false },
        txtIcons: { enabled: false, invalid: false },
        txtSoCongNguonDauVao: { enabled: false, invalid: false },
        selectHangSanXuat: { enabled: false, invalid: false },
        txtNamSanXuat: { enabled: false, invalid: false },
        txtSoCongNguonDauRa: { enabled: false, invalid: false },
        txtGhiChu: { enabled: false, invalid: false }
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
</style>
