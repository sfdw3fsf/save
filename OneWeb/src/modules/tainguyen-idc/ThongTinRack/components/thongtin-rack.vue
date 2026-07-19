<template src="./thongtin-rack.html"></template>

<script>

import DateTimeLib from '../../../../utils/DateTimeLib.js';

export default {
  props: {
    parentId: {
      type: Number,
      default: null
    },
    parentType: {
      type: String,
      default: ''
    },
    itemId: {
      type: Number,
      default: null
    },
    itemType: {
      type: String,
      default: 'RACK'
    },
    mode: {
      type: String,
      default: 'VIEW', // 'ADD', 'EDIT', 'VIEW'
      validator: (value) => ['ADD', 'EDIT', 'VIEW'].includes(value)
    }
  },
  data: function () {
    return {
      selectedRackId: 0, // ID rack được chọn để truyền vào modal
      dataItem: {
        RACK_ID: 0,
        TEN_RACK: '',
        LOAIRACK_ID: null,
        HANGSX_ID: null,
        CHUQUAN_ID: null,
        NGAY_SD: null,
        CAMERA: 1,
        CHIEUDAI: null,
        CHIEURONG: null,
        CHIEUCAO: null,
        VANHANH: null,
        QUYHOACH: null,
        CCDV: null,
        HINHANH: null,
        TEN_DV: null,
        TEN_IDC: '',
        IDC_ID: null,
        TEN_TOANHA: '',
        TEN_MATSAN: '',
        TEN_PHONG: '',
        VITRI_RACK: '',
        SUDUNG: 1,
        GHI_CHU: '',
        DONVIQL_ID: null,
        PHONGQL_ID: null,
        MA_DUAN: null,
        TEN_DUAN: null,
        QLTS_ID: null,
        THE_TS: null,
        DS_NGUOIQL: [],
        DS_NGUOIQL_WITH_ROLE: [],
      },

      hangSXSelectList: [],
      chuQuanSelectList: [],
      loaiRackSelectList: [],
      quyHoachSelectList: [],
      vanHanhSelectList: [],
      ccdvSelectList: [],
      donviQLSelectList: [],
      phongQLSelectList: [],
      nguoiQLSelectList: [],


      // Controls
      controls: {
        enable:{
          enabled: false
        },
        txtIcon: {
          enabled: false,
          invalid: false
        },
      },

      // Buttons
      buttons: {
        btnNhapMoi: {
          enabled: true,
        },
        btnGhiLai: {
          enabled: false,
        },
        btnHuy: {
          enabled: false,
        },
        btnSua: {
          enabled: false,
        },
      },

      // Ảnh icon
      displayIconUrl: '',
      previousIconRelative: '',
      pendingIconFile: null,
      pendingIconPreviewUrl: '',
      pendingRemoveIcon: false,
      uploadingIcon: false,
      loadingImageUrl: false,

      // Backup data for cancel
      backupDataItem: null,
    };
  },
  mounted() {
    // Xử lý enable date picker và select người quản lý
    this.setDatePickerEnabled(this.controls.enable.enabled);
  },
  computed: {
    tenIDCHienThi() {
      const toanha = this.toanhaList.find(t => t.IDC_ID === this.dataItem.IDC_ID);
      return toanha ? toanha.TEN_IDC : '';
    },
    nguoiQLTreeSelectList() {
      return this.nguoiQLSelectList.map(item => ({
        id: item.NHANVIEN_ID,
        label: item.TEN_NV
      }));
    },
  },
  watch: {
    'dataItem.LOAIRACK_ID': {
      handler(newId) {
        if (!newId) {
          this.dataItem.CHIEUDAI = this.dataItem.CHIEURONG = this.dataItem.CHIEUCAO = null;
          return;
        }
        const loai = this.loaiRackSelectList.find(x => x.ID == newId);
        if (loai) {
          this.dataItem.CHIEUDAI   = loai.CHIEUDAI;
          this.dataItem.CHIEURONG  = loai.CHIEURONG;
          this.dataItem.CHIEUCAO   = loai.CHIEUCAO;
        }
      },
      immediate: true
    },
    // Thêm watcher này để bắt cả trường hợp load dữ liệu cũ
    'dataItem.DS_NGUOIQL': {
      handler(newVal) {
        if (!newVal || newVal.length === 0) {
          this.dataItem.DS_NGUOIQL_WITH_ROLE = [];
          return;
        }

        // Tạo lại danh sách có vai trò: người đầu = 1, các người sau = 2
        const withRole = newVal.map((id, index) => ({
          NHANVIEN_ID: id,
          NHIEMVU_ID: index === 0 ? 1 : 2
        }));

        this.dataItem.DS_NGUOIQL_WITH_ROLE = withRole;
      },
      deep: true,
      immediate: true   // Quan trọng: chạy ngay khi load dữ liệu cũ
    },
    'controls.enable.enabled': {
      handler(newVal) {
        this.setDatePickerEnabled(newVal);
        this.setNguoiQLSelectEnabled(newVal);
      },
      immediate: true
    },
    mode: {
      handler(newMode) {
        this.initializeMode();
      },
      immediate: true
    }

  },
  created: async function () {
    try {
      this.$root.showLoading(true);

      const [
        loaiRackSelectList,
        hangSXSelectList,
        chuQuanSelectList,
        ccdvSelectList,
        vanHanhSelectList,
        quyHoachSelectList,
        donviQLSelectList,
        phongQLSelectList,
        nguoiQLSelectList
      ] = await Promise.all([
        this.getLoaiRackItems(),
        this.getHangSXItems(),
        this.getChuQuanItems(),
        this.getCCDVItems(),
        this.getVanHanhItems(),
        this.getQuyHoachItems(),
        this.getDonViQLItems(),
        this.getPhongQLItems(),
        this.getNguoiQLItems()
      ]);

      this.loaiRackSelectList = loaiRackSelectList;
      this.hangSXSelectList = hangSXSelectList;
      this.chuQuanSelectList = chuQuanSelectList;
      this.ccdvSelectList = ccdvSelectList;
      this.vanHanhSelectList = vanHanhSelectList;
      this.quyHoachSelectList = quyHoachSelectList;
      this.donviQLSelectList = donviQLSelectList;
      this.phongQLSelectList = phongQLSelectList;
      this.nguoiQLSelectList = nguoiQLSelectList;

      // Initialize mode
      await this.initializeMode();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    // ========== INITIALIZATION ==========
    async initializeMode() {
      if (this.mode === 'ADD') {
        await this.initAddMode();
      } else if (this.mode === 'EDIT') {
        await this.initEditMode();
      } else if (this.mode === 'VIEW') {
        await this.initViewMode();
      }
    },

    async initAddMode() {
      this.setCurrentItemDefault();

      // Load parent info để fill IDC, Tòa nhà, Mặt sàn, Phòng
      if (this.parentId) {
        await this.loadParentInfo();
      }

      this.enableControls(true);
      this.buttons.btnNhapMoi.enabled = true;
      this.buttons.btnGhiLai.enabled = true;
      this.buttons.btnHuy.enabled = true;
      this.buttons.btnSua.enabled = false;
    },

    async initEditMode() {
      if (this.itemId) {
        await this.loadItemDetail();
        this.backupDataItem = JSON.parse(JSON.stringify(this.dataItem));
      }
      this.enableControls(false);
      this.buttons.btnNhapMoi.enabled = false;
      this.buttons.btnGhiLai.enabled = false;
      this.buttons.btnHuy.enabled = false;
      this.buttons.btnSua.enabled = true;
    },

    async initViewMode() {
      if (this.itemId) {
        await this.loadItemDetail();
      }
      this.enableControls(false);
      this.buttons.btnNhapMoi.enabled = false;
      this.buttons.btnGhiLai.enabled = false;
      this.buttons.btnHuy.enabled = false;
      this.buttons.btnSua.enabled = false;
    },

    // ========== DATA LOADING ==========
    async loadItemDetail() {
      try {
        const detail = await this.getChiTietRack(this.itemId);
        if (detail && detail.length > 0) {
          this.setCurrentItem(detail[0]);
        }
      } catch (e) {
        this.$root.toastError('Không thể tải thông tin Rack');
      }
    },

    async loadParentInfo() {
      try {
        const parentInfo = await this.getViTriRack(this.parentId);
        if (parentInfo && parentInfo.length > 0) {
          const info = parentInfo[0];
          this.dataItem.TEN_DV = info.TEN_DV || '';
          this.dataItem.TEN_IDC = info.TEN_IDC || '';
          this.dataItem.IDC_ID = info.IDC_ID || null;
          this.dataItem.TEN_TOANHA = info.TEN_TOANHA || '';
          this.dataItem.TEN_MATSAN = info.TEN_MATSAN || '';
          this.dataItem.TEN_PHONG = info.TEN_PHONG || '';
        }
      } catch (e) {
        console.error('Không thể tải thông tin parent:', e);
      }
    },

    // ========== UI HELPERS ==========
    setDatePickerEnabled(value) {
      const picker = this.$refs.ngaySDPicker;
      if (picker && picker.ej2Instances) {
        picker.ej2Instances.enabled = value;
        picker.ej2Instances.dataBind();
      }
    },

    // ========== API CALLS ==========
    getChiTietRack: async function(id) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "CHITIET-RACK",
          thamSo1: id
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getViTriRack: async function (phongId) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "CHITIET-VITRI",
          thamSo1: phongId
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getLoaiRackItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "LOAIRACK",
          thamSo1:null
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
        getChuQuanItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "CHUQUAN",
          thamSo1:null
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getHangSXItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "HANGSX",
          thamSo1:null
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getCCDVItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "CCDV",
          thamSo1:null
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getQuyHoachItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "QUYHOACH",
          thamSo1:null
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getVanHanhItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "VANHANH",
          thamSo1:null
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getDonViQLItems: async function() {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "DONVI-QL",
          thamSo1:this.$root.token.getDonViID()
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getPhongQLItems: async function() {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "DONVI-QL",
          thamSo1:this.$root.token.getDonViID()
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    getNguoiQLItems: async function() {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "NGUOI-QL",
          thamSo1:this.$root.token.getDonViID()
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    upsertRack: async function (args) {
      let result = false;
      try {

        // let nguoiQLIds = Array.isArray(args.DS_NGUOIQL)
        //   ? args.DS_NGUOIQL.filter(x => !!x).join(',')
        //   : '';

        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/upsert", {
          rackId: args.RACK_ID === 0 ? null : args.RACK_ID,
          loaiRackId: args.LOAIRACK_ID,
          tenRack: args.TEN_RACK,
          camera: args.CAMERA,
          hinhAnh: args.HINHANH,
          //maTaiSan: args.MA_TS,
          quyHoach: args.QUYHOACH,
          vanHanh: args.VANHANH,
          ccdv: args.CCDV,
          suDung: args.SUDUNG,
          ghiChu: args.GHI_CHU,
          hangSXId: args.HANGSX_ID,
          chuQuanId: args.CHUQUAN_ID,
          camera: args.CAMERA ? args.CAMERA : null,
          ngaySD: args.NGAY_SD ? DateTimeLib.toDateDisplay(args.NGAY_SD) : null,
          hinhAnh: args.HINHANH ? args.HINHANH : null,
          donviQl: Number(args.DONVIQL_ID) ? Number(args.DONVIQL_ID) : null,
          phongQl: Number(args.PHONGQL_ID) ? Number(args.PHONGQL_ID) : null,
          nguoiQlIds: JSON.stringify(args.DS_NGUOIQL_WITH_ROLE || []),
          maDuAn: args.MA_DUAN ? args.MA_DUAN : null,
          tenDuAn: args.TEN_DUAN ? args.TEN_DUAN : null,
          qltsId: args.QLTS_ID ? args.QLTS_ID : null,
          theTaisan: args.THE_TS ? args.THE_TS : null,
          vitriRack: args.VITRI_RACK ? args.VITRI_RACK : null,
          phongId: this.parentId ? this.parentId : null,
          idcId: args.IDC_ID ? args.IDC_ID : null,
        });
        result = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },




    // Phần điều khiển controls, Validation
    enableControls: function (args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args;
        }
      }
    },

    validateForm() {
      let errors = [];
      if (!this.dataItem.TEN_RACK) errors.push('Tên Rack không được để trống');
      if (!this.dataItem.LOAIRACK_ID) errors.push('Loại Rack không được để trống');
      if (!this.dataItem.HANGSX_ID) errors.push('Hãng sản xuất không được để trống');
      if (!this.dataItem.CHUQUAN_ID) errors.push('Đơn vị sở hữu không được để trống');
      if (!this.dataItem.CAMERA) errors.push('Trạng thái camera không được để trống');
      if (!this.dataItem.NGAY_SD) errors.push('Ngày sử dụng không được để trống');
      if (!this.dataItem.VANHANH) errors.push('Trạng thái vận hành không được để trống');
      if (!this.dataItem.CCDV) errors.push('Trạng thái Rack CCDV không được để trống');
      if (!this.dataItem.QUYHOACH) errors.push('Trạng thái Rack theo quy hoạch không được để trống');
      return errors;
    },

    // ========== DATA ITEM MANAGEMENT ==========
    setCurrentItem: async function (item) {
      this.dataItem.RACK_ID = item.RACK_ID;
      this.dataItem.TEN_RACK = item.TEN_RACK;
      this.dataItem.LOAIRACK_ID = item.LOAIRACK_ID;
      this.dataItem.HANGSX_ID = item.HANGSX_ID;
      this.dataItem.CHUQUAN_ID = item.CHUQUAN_ID;
      this.dataItem.NGAY_SD = item.NGAY_SD;
      this.dataItem.CAMERA = item.CAMERA;
      this.dataItem.CHIEUDAI = item.CHIEUDAI;
      this.dataItem.CHIEURONG = item.CHIEURONG;
      this.dataItem.CHIEUCAO = item.CHIEUCAO;
      this.dataItem.VANHANH = item.VANHANH;
      this.dataItem.QUYHOACH = item.QUYHOACH;
      this.dataItem.CCDV = item.CCDV;
      this.dataItem.HINHANH = item.HINHANH;
      this.dataItem.SUDUNG = item.SUDUNG;
      this.dataItem.GHI_CHU = item.GHI_CHU;
      this.dataItem.TEN_DV = item.TEN_DV;
      this.dataItem.TEN_IDC = item.TEN_IDC;
      this.dataItem.IDC_ID = item.IDC_ID;
      this.dataItem.TEN_TOANHA = item.TEN_TOANHA;
      this.dataItem.TEN_MATSAN = item.TEN_MATSAN;
      this.dataItem.TEN_PHONG = item.TEN_PHONG;
      this.dataItem.VITRI_RACK = item.VITRI_RACK;
      this.dataItem.DONVIQL_ID = item.DONVIQL_ID;
      this.dataItem.PHONGQL_ID = item.PHONGQL_ID;
      this.dataItem.MA_DUAN = item.MA_DUAN;
      this.dataItem.TEN_DUAN = item.TEN_DUAN;
      this.dataItem.QLTS_ID = item.QLTS_ID;
      this.dataItem.THE_TS = item.THE_TS;
      // Xử lý DS_NGUOIQL
      if (typeof item.DS_NGUOIQL === 'string') {
        try {
          const arr = JSON.parse(item.DS_NGUOIQL);
          this.dataItem.DS_NGUOIQL = arr.map(c => c.id || c.NHANVIEN_ID);
        } catch {
          this.dataItem.DS_NGUOIQL = [];
        }
      } else if (Array.isArray(item.DS_NGUOIQL)) {
        // Nếu là mảng object
        if (item.DS_NGUOIQL.length && typeof item.DS_NGUOIQL[0] === 'object') {
          this.dataItem.DS_NGUOIQL = item.DS_NGUOIQL.map(c => c.id || c.NHANVIEN_ID);
        } else {
          this.dataItem.DS_NGUOIQL = item.DS_NGUOIQL;
        }
      } else {
        this.dataItem.DS_NGUOIQL = [];
      }
      // Lưu lại giá trị cũ để tham khảo
      this.previousIconRelative = this.dataItem.HINHANH;
      if (this.dataItem.HINHANH) {
        try {
          this.loadingImageUrl = true
          const url = await this.getPresignedUrlFromMinio(this.dataItem.HINHANH)
          this.displayIconUrl = url || ''
        } catch (e) {
          this.displayIconUrl = ''
        } finally {
          this.loadingImageUrl = false
        }
      } else {
        this.displayIconUrl = ''
      }
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        RACK_ID: 0,
        TEN_RACK: '',
        LOAIRACK_ID: null,
        HANGSX_ID: null,
        CHUQUAN_ID: null,
        NGAY_SD: null,
        CAMERA: 1,
        CHIEUDAI: null,
        CHIEURONG: null,
        CHIEUCAO: null,
        VANHANH: null,
        QUYHOACH: null,
        CCDV: null,
        HINHANH: null,
        TEN_DV: null,
        TEN_IDC: '',
        IDC_ID: null,
        TEN_TOANHA: '',
        TEN_MATSAN: '',
        TEN_PHONG: '',
        VITRI_RACK: '',
        SUDUNG: 1,
        GHI_CHU: '',
        DONVIQL_ID: null,
        PHONGQL_ID: null,
        DS_NGUOIQL: [],
        MA_DUAN: '',
        TEN_DUAN: '',
        QLTS_ID: '',
        THE_TS: '',
      });

      this.pendingIconFile = null
      if (this.pendingIconPreviewUrl) {
        try {
          URL.revokeObjectURL(this.pendingIconPreviewUrl)
        } catch (e) {}
      }
      this.pendingIconPreviewUrl = ''
      this.pendingRemoveIcon = false
      this.displayIconUrl = ''
    },


    // ========== MINIO FILE HELPERS ==========
    async getPresignedUrlFromMinio(src) {
      try {
        let url = `/web-ecms/storage/getPresignedUrl`
        let response = await this.$root.context.post(url, { fileSource: src })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          return response.data.result
        } else return null
      } catch (error) {
        console.log(error)
        return null
      }
    },
    async uploadFileToMinio(item) {
      try {
        this.$root.showLoading(true)
        let formData = new FormData()
        formData.append('file', item)
        let url = `/web-ecms/storage/uploadFile`
        let response = await this.$root.context.post(url, formData)
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          if (response.data.status) {
            return response.data.message
          } else return null
        } else return null
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi xảy ra trong quá trình upload file!')
        return null
      } finally {
        this.$root.showLoading(false)
      }
    },
    async deleteFileFromMinio(src) {
      try {
        this.$root.showLoading(true)
        let url = `/web-ecms/storage/deleteFile`
        let response = await this.$root.context.post(url, { fileSource: src })
        if (response.error_code === 'BSS-00000000' && response.data != undefined) {
          if (!response.data.status) this.$toast.error(response.data.message)
          return response.data.status
        } else return false
      } catch (error) {
        console.log(error)
        return false
      } finally {
        this.$root.showLoading(false)
      }
    },

    // ========== IMAGE UPLOAD ==========
    onClickUploadIcon: function() {
      if (this.$refs.fileInputIcon) {
        this.$refs.fileInputIcon.value = null
        this.$refs.fileInputIcon.click()
      }
    },
    onRemoveIcon: function() {
      this.pendingRemoveIcon = true
      this.pendingIconFile = null
      this.pendingIconPreviewUrl = ''
      this.displayIconUrl = ''
    },
    onChangeIconFile: async function(event) {
      try {
        const files = event.target.files
        if (!files || files.length === 0) {
          return
        }
        const file = files[0]
        // Chỉ lưu tạm thời file và hiển thị preview local, không gọi API ngay
        this.pendingRemoveIcon = false
        this.pendingIconFile = file
        // Tạo URL preview tạm thời
        if (this.pendingIconPreviewUrl) {
          try {
            URL.revokeObjectURL(this.pendingIconPreviewUrl)
          } catch (e) {}
        }
        this.pendingIconPreviewUrl = URL.createObjectURL(file)
        this.displayIconUrl = this.pendingIconPreviewUrl
      } catch (e) {
        this.$root.toastError('Có lỗi khi tải ảnh')
      } finally {
        this.uploadingIcon = false
      }
    },

    // ========== BUTTON HANDLERS ==========
    btnNhapMoi_Click: async function () {
      this.setCurrentItemDefault();
      if (this.parentId) {
        await this.loadParentInfo();
      }
      this.enableControls(true);
      this.buttons.btnGhiLai.enabled = true;
      this.buttons.btnHuy.enabled = true;
    },

    btnGhiLai_Click: async function () {
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

            // 1) Xóa ảnh icon cũ nếu có
            if (this.pendingRemoveIcon && this.dataItem.HINHANH) {
              await this.deleteFileFromMinio(this.dataItem.HINHANH)
              this.dataItem.HINHANH = ''
            }
            // 2) Nếu có chọn ảnh mới
            if (this.pendingIconFile) {
              if (this.dataItem.HINHANH) {
                await this.deleteFileFromMinio(this.dataItem.HINHANH)
              }
              const uploadRs = await this.uploadFileToMinio(this.pendingIconFile)
              if (uploadRs && uploadRs.relativeUrl) {
                this.dataItem.HINHANH = uploadRs.relativeUrl
                const viewUrl = await this.getPresignedUrlFromMinio(this.dataItem.HINHANH)
                this.displayIconUrl = viewUrl || ''
              } else {
                this.$root.toastError('Tải ảnh thất bại')
                return
              }
            }
            // Debug trước khi save
            console.log('💾 BEFORE SAVE:');
            console.log('DS_NGUOIQL:', this.dataItem.DS_NGUOIQL);
            console.log('DS_NGUOIQL_WITH_ROLE:', this.dataItem.DS_NGUOIQL_WITH_ROLE);
            console.log('JSON.stringify:', JSON.stringify(this.dataItem.DS_NGUOIQL_WITH_ROLE || []));

            // Đảm bảo DS_NGUOIQL_WITH_ROLE luôn hợp lệ
            if (!this.dataItem.DS_NGUOIQL_WITH_ROLE || !Array.isArray(this.dataItem.DS_NGUOIQL_WITH_ROLE)) {
              if (this.dataItem.DS_NGUOIQL && this.dataItem.DS_NGUOIQL.length > 0) {
                this.dataItem.DS_NGUOIQL_WITH_ROLE = this.dataItem.DS_NGUOIQL.map((id, index) => ({
                  NHANVIEN_ID: id,
                  NHIEMVU_ID: index === 0 ? 1 : 2
                }));
              } else {
                this.dataItem.DS_NGUOIQL_WITH_ROLE = [];
              }
            }
            // 3) Cập nhật thông tin Rack
            let rs = await this.upsertRack(this.dataItem);
            if (rs) {
              if (this.dataItem.RACK_ID == 0 || this.dataItem.RACK_ID == null) {
                this.$root.toastSuccess("Thêm Rack thành công");
              }
              else  {
                this.$root.toastSuccess("Cập nhật Rack thành công");
              }
              this.pendingIconFile = null
              if (this.pendingIconPreviewUrl) {
                try {
                  URL.revokeObjectURL(this.pendingIconPreviewUrl)
                } catch (e) {}
              }
              this.pendingIconPreviewUrl = ''
              this.pendingRemoveIcon = false
              this.previousIconRelative = this.dataItem.HINHANH

              this.$emit('saveSuccessed', true);

              // Disable controls after save
              if (this.mode === 'ADD') {
                this.buttons.btnGhiLai.enabled = true;
                this.buttons.btnHuy.enabled = true;
              } else if (this.mode === 'EDIT') {
                this.enableControls(false);
                this.buttons.btnSua.enabled = true;
                this.buttons.btnGhiLai.enabled = false;
                this.buttons.btnHuy.enabled = false;
              }
            }
            else {
              if (this.dataItem.RACK_ID === 0) {
                this.$root.toastError("Thêm Rack thất bại");
              }
              else {
                this.$root.toastError("Cập nhật Rack thất bại");
              }
              this.$emit('saveSuccessed', false);
            }
          } catch (error) {
            console.error('Error saving rack:', error);
            this.$emit('saveSuccessed', false);
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnSua_Click: async function () {
      this.backupDataItem = JSON.parse(JSON.stringify(this.dataItem));
      this.enableControls(true);
      this.buttons.btnSua.enabled = false;
      this.buttons.btnGhiLai.enabled = true;
      this.buttons.btnHuy.enabled = true;
    },
    btnHuy_Click: async function () {
      if (this.mode === 'EDIT' && this.backupDataItem) {
        // Restore backup data
        this.dataItem = JSON.parse(JSON.stringify(this.backupDataItem));

        // Restore icon
        this.pendingIconFile = null
        if (this.pendingIconPreviewUrl) {
          try {
            URL.revokeObjectURL(this.pendingIconPreviewUrl)
          } catch (e) {}
        }
        this.pendingIconPreviewUrl = ''
        this.pendingRemoveIcon = false

        if (this.previousIconRelative) {
          try {
            this.loadingImageUrl = true
            const url = await this.getPresignedUrlFromMinio(this.previousIconRelative)
            this.displayIconUrl = url || ''
          } catch (e) {
            this.displayIconUrl = ''
          } finally {
            this.loadingImageUrl = false
          }
        } else {
          this.displayIconUrl = ''
        }

        this.enableControls(false);
        this.buttons.btnSua.enabled = true;
        this.buttons.btnGhiLai.enabled = false;
        this.buttons.btnHuy.enabled = false;
      } else if (this.mode === 'ADD') {
        // Reset to default for ADD mode
        this.setCurrentItemDefault();
        if (this.parentId) {
          await this.loadParentInfo();
        }
        this.enableControls(true);
        this.buttons.btnGhiLai.enabled = true;
        this.buttons.btnHuy.enabled = true;
      }
    },



  }
};
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type="checkbox"]:checked:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type="checkbox"]:disabled+.name:before {
  background: #fff;
  border-color: #0176ff;
}
.radio-item {
  width: 150px; /* 👈 chỉnh theo ý bạn, ví dụ 100px hoặc 150px */
}

.preview img {
  max-width: 150px;
  border: 1px solid #ddd;
  padding: 2px;
  display: block;
}
/* Loading state for image */
.image-loading {
  border-radius: 4px;
}

.image-loading .spinner-border-sm {
  width: 1.5rem;
  height: 1.5rem;
}

.fixed-icon-box {
  width: 100%;
  max-height: 130px;
  height: 100%;
  overflow: hidden !important;
}
</style>
