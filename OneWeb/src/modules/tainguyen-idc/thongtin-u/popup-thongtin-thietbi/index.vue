<template src="./index.template.html"></template>
<script>
import Vue from 'vue';

export default {
  name: 'ModalThongtinThietBi',
  props: { modalID: null },
  components: {
    
  },
  created: async function () {
    await this.loadInitialData();
  },
  validations: {

  },
  watch: {

  },
  computed: {

  },
  methods: {
    closeModal: function () {
      this.$refs.ModalThongtinThietBi.hide()
    },
    show: function (item, mode) {
      if (item) {
        this.currentItem = { ...item };
        this.currentMode = mode;
      } else {
        this.resetCurrentItem();
        this.currentMode = mode;
      }
      this.item = item;
      console.log(this.item)
      this.$refs.ModalThongtinThietBi.show();
    },

    onAddNew: function () {
      this.resetCurrentItem();
      this.currentMode = 'ADD';
      this.resetValidation();
    },

    onSave: async function () {
      if (this.validateForm()) {
        try {
          this.$root.showLoading(true);
          if (this.currentMode === 'ADD') {
            await this.saveNewItem();
          } else {
            await this.updateItem();
          }
          this.$root.toastSuccess('Lưu thành công!');
          this.currentMode = 'VIEW';
        } catch (error) {
          this.handleError(error);
        } finally {
          this.$root.showLoading(false);
        }
      }
    },

    
    resetCurrentItem: function () {
      this.currentItem = {
        id: 0,
        phamViIdcId: 0,
        mangThietBiId: 0,
        loaiThietBiId: 0,
        loaiKhachHangId: 0,
        heThongId: 0,
        donViQuanLy: '',
        nguoiQuanLy: '',
        lapDatTaiIdcId: 0,
        tenThietBi: '',
        toaNhaId: 0,
        matSanId: 0,
        dongSanPham: '',
        phongZoneId: 0,
        rack: '',
        cauHinhChiTiet: '',
        uTrenId: 0,
        uDuoiId: 0,
        serialNumber: '',
        thoiDiemLapDat: '',
        canCuLapDatId: 0,
        tagNumber: '',
        maTheTaiSan: '',
        trangThaiTaiSanId: 0,
        congSuatThietKe: 0,
        trangThaiVanHanhId: 0,
        icons: '',
        soCongNguonDauVao: 0,
        hangSanXuatId: 0,
        namSanXuat: new Date().getFullYear(),
        soCongNguonDauRa: 0,
        ghiChu: ''
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
      if (!this.currentItem.tenThietBi) {
        this.controls.txtTenThietBi.invalid = true;
        isValid = false;
      }

      if (!this.currentItem.serialNumber) {
        this.controls.txtSerialNumber.invalid = true;
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
        await Promise.all([

        ]);
      } catch (error) {
        this.handleError(error);
      } finally {
        this.$root.showLoading(false);
      }
    },


    // CRUD operations
    saveNewItem: async function () {
      // API call to save new item
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
      item: null,
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      currentItem: {
        id: 0,

        ghiChu: ''
      },

      // Dropdown lists
      phamViIdcList: [],
      mangThietBiList: [],
      loaiThietBiList: [],
      loaiKhachHangList: [],
      heThongList: [],
      lapDatTaiIdcList: [],
      toaNhaList: [],
      matSanList: [],
      phongZoneList: [],
      uTrenList: [],
      uDuoiList: [],
      canCuLapDatList: [],
      trangThaiTaiSanList: [],
      trangThaiVanHanhList: [],
      hangSanXuatList: [],

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
