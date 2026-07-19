<template src="./index.template.html"></template>
<script>
import Vue from 'vue';

export default {
  name: 'ModalThongtinBangthongMang',
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
      this.$refs.ModalThongtinBangthongMang.hide(this.modalID)
    },
    async show(item, mode, phieutcId) {
      this.currentMode = mode
      this.phieutcId = phieutcId
      this.item = item
      this.oldItem = item
    
      // load dữ liệu ngay khi mở
      if (this.currentMode == 'MINUS') {
        await this.loadInitialDataThuhoi()
      } else {
        await this.loadInitialDataCapphat()
      }

      this.$refs.ModalThongtinBangthongMang.show();

    },  
    onSave: function () {
      if (this.validateForm()) {
        try {
          this.$root.showLoading(true);
          if (this.currentMode == 'MINUS') {
            this.saveItem();
          } else {
            this.saveNewItem();
          }
          this.closeModal()
        } catch (error) {
          this.handleError(error);
        } finally {
          this.$root.showLoading(false);
        }
      }
    },

    saveNewItem: function () {
      try {
        const tbMang = (this.thietbiMangList || []).find(
          x => Number(x.ID) === Number(this.currentItem.thietBiMangId) // B hoa
        );

        const port = (this.thietbiPortList || []).find(
          x => Number(x.ID) === Number(this.currentItem.thietbiPortId)
        );

        this.result = {
          THICONGBT_ID: 'Tự sinh',         
          GOICUOC_ID: this.currentItem.goiCuocMangId,
          QUOCTE: this.currentItem.quocTe,
          TRONGNUOC: this.currentItem.trongNuoc,
          THIETBIMANG_ID: this.currentItem.thietBiMangId,
          THIETBIMANG: tbMang.TEN,
          PORT_ID: this.currentItem.thietbiPortId,
          PORT: port.TEN,
          TRANGTHAI: 'Chờ cấp phát',
          PORT_ID_OLD: Number(this.item.PORT_ID) ? Number(this.item.PORT_ID) : null,
        }
        console.log(this.result)

        this.$emit('saved', this.result);      
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || 'Lưu thất bại')
      } finally {
        this.$root.showLoading(false)
      }
    },

    saveItem: function () {
      try {
        this.$emit('saved', this.dsBangthongThuhoi);      
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || 'Lưu thất bại')
      } finally {
        this.$root.showLoading(false)
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
      if (this.currentMode == 'MINUS') {
        if (!(this.dsBangthongThuhoi?.length > 0)) {
          isValid = false;
        } 
      } else {
        // Validate required fields
        if (!this.currentItem.thietBiMangId) {
          isValid = false;
        }

        if (!this.currentItem.thietbiPortId) {
          isValid = false;
        }
      }

      // Add more validation rules as needed
      if (!isValid) {
        this.$root.toastError('Vui lòng điền đầy đủ thông tin bắt buộc!');
      }

      return isValid;
    },

    // API methods
    loadInitialDataCapphat: async function (item) {
      try {
        this.$root.showLoading(true);

        const [thongTinPhieuChiTiet, thietbiMangList, matSanList, idcList] = await Promise.all([
          this.getThongTinPhieuChiTiet(this.phieutcId),
          this.getThietbiMang(),
          this.getMatSan(),
          this.getIDC()
        ])

        await this.setCurrentItem(thongTinPhieuChiTiet)

        this.thietbiMangList = thietbiMangList
        this.matSanList = matSanList
        this.idcList = idcList
        this.currentItem.thietBiMangId = item?.THIETBIMANG_ID ? Number(item.THIETBIMANG_ID) : 0
        this.item.PORT_ID_OLD = item?.PORT_ID ? Number(item.PORT_ID) : 0

        await this.loadDsTrangThai()

      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    loadInitialDataThuhoi: async function () {
      try {
        this.$root.showLoading(true);

        const [dsBangthong] = await Promise.all([
          this.getDsBangthongThuhoi()
        ])

        this.dsBangthong = dsBangthong
        this.dsBangthongThuhoi = []

      } catch (error) {
        this.handleError(error)
      } finally {
        this.$root.showLoading(false)
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

    getThietbiMang: async function () {
      try {
        const rs = await this.$root.context.get(`/web-ecms/ha-tang/thongtin-u/thietbi/${this.phieutcId}`)
        return rs.data
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || 'Không lấy được thiết bị')
        return []
      }
    },

    getDsBangthongThuhoi: async function () {
      try {
        const rs = await this.$root.context.get(`/web-ecms/ha-tang/thongtin-u/thicong-bangthong-thuhoi/1`)
        return rs.data
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || 'Không lấy được port')
        return []
      }
    },

    onChangeThietBiMang: async function () {
      this.thietbiPortList = await this.getThietbiPort();
    },

    getThietbiPort: async function () {
      if (!this.currentItem.thietBiMangId) {
        this.$toast.warning('Vui lòng chọn thiết bị mạng trước');
      } else {
        try {
          const rs = await this.$root.context.get(`/web-ecms/ha-tang/thongtin-u/thietbi-port/${this.currentItem.thietBiMangId}`)
          return rs.data
        } catch (error) {
          this.$toast.error(error.response?.data?.message_detail || 'Không lấy được port')
          return []
        }
      }
      
    },

    getIDC: async function () {
      try {
        const rs = await this.$root.context.get(`/web-ecms/ha-tang/thongtin-u/idc`)
        return rs.data
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || 'Không lấy được port')
        return []
      }
    },

    getMatSan: async function () {
      try {
        const rs = await this.$root.context.get(`/web-ecms/ha-tang/thongtin-u/mat-san`)
        return rs.data
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || 'Không lấy được port')
        return []
      }
    },

    getThongTinPhieuChiTiet: async function (id) {
      try {
        const rs = await this.$root.context.get(`/web-ecms/ha-tang/thongtin-u/phieutc-kenhban/${id}`)
        return rs.data
      } catch (error) {
        this.$toast.error(error.response?.data?.message_detail || 'Không lấy được port')
        return []
      }
    },
    loadDsTrangThai: async function() {
      this.dsTrangthai = [{
          ID: 0,
          TEN: 'Chờ thu hồi'
        },
        {
          ID: 1,
          TEN: 'Đã cấp phát'
        }]
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
    },
    grd1_RowSelected: function (args) {
      if (Array.isArray(args.data) && args.data.length > 0) {
        args.data.forEach(item => {
          let row = { ...item };
          row.TRANGTHAI = "Chờ thu hồi";
          this.dsBangthongThuhoi.push(row);
        });
      } else if (args.data) {
        let row = { ...args.data };
        row.TRANGTHAI = "Chờ thu hồi";
        this.dsBangthongThuhoi.push(row);
      }
    },

    grd1_RowDeselected: function (args) {
      if (args.data) {
        // Xóa phần tử có cùng THICONGBT_ID khỏi danh sách
        this.dsBangthongThuhoi = this.dsBangthongThuhoi.filter(
          x => x.THICONGBT_ID !== args.data.THICONGBT_ID
        );
      } else {
        // nếu deselect hết thì clear toàn bộ
        this.dsBangthongThuhoi = [];
      }
    }
  },
  data: function () {
    return {
      dsBangthongThuhoi: [],

      dataGrid1Columns: [
        {fieldName: 'THICONGBT_ID', headerText: 'ID cấp băng thông', textAlign: 'Left', width: 80},
        {fieldName: 'GOICUOC_ID', headerText: 'Gói cước mạng', textAlign: 'Left', width: 100},
        {fieldName: 'QUOCTE', headerText: 'Quốc tế', textAlign: 'Left', width: 90},
        {fieldName: 'TRONGNUOC', headerText: 'Trong nước', textAlign: 'Left', width: 110},
        {fieldName: 'THIETBIMANG', headerText: 'Thiết bị mạng(Switch Access)', textAlign: 'Left', width: 110},
        {fieldName: 'PORT', headerText: 'ID Port', textAlign: 'Left', width: 120},
        {fieldName: 'TRANGTHAI', headerText: 'Trạng thái CCDV', textAlign: 'Left', width: 170}
      ],
      dsBangthong: [],
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      item: null,
      currentMode: 'VIEW', // VIEW, ADD, EDIT
      currentItem: {
        idcId: null,
        matSanId: null,
        goiCuocMangId: 0,
        quocTe: '',
        trongNuoc: '',
        trangthai: 0,
        thietbiPortId: 0,
        thietBiMangId: 0
      },

      // Dropdown lists
      idcList: [],
      thietbiMangList: [],
      thietbiPortList: [],
      matSanList: [],
      thongTinPhieuChiTiet: {},
      dsTrangthai: [],
      
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
