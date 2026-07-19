<template src="./index.template.html"></template>

<script>
import axios from 'axios';
import XLSX from 'xlsx';
import DateTimeLib from '../../../utils/DateTimeLib.js';
import DanhSachThietbi from './Modal/DanhSachThietbi.vue';
import DanhSachU from './Modal/DanhSachU.vue';
import DanhSachThanhNguon from './Modal/DanhSachThanhNguon.vue';
import ModalImportExcel from '../components/Modals/ModalImportExcel';

export default {
  components: {
    DanhSachThietbi,
    DanhSachU,
    DanhSachThanhNguon,
    ModalImportExcel
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
      currentTab: 1, // Tab hiện tại, mặc định là tab 1

      // Selected items
      dsSelected: [],
      thongtinRackList: [],
      historyRackList: [],
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
        btnXoa: {
          enabled: false,
        },
        btnImport: {
          enabled: true,
        },
        btnExport: {
          enabled: true,
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

      collapsedBlocks: {
        thongTinRack: false,
        thongTinQL: true,
        thongTinDuAn: true,
        viTriRack: true
      }, // trạng thái thu gọn / mở rộng

      // Cấu hình cho modal import
      importConfig: {
        title: 'Import dữ liệu',
        templateFileName: 'Template_Import_Rack.xlsx',
        tableColumns: [
          { key: 'tenRack', label: 'Tên Rack' },
          { key: 'loaiRackId', label: 'Loại Rack ID' },
          { key: 'maTaiSan', label: 'Mã tài sản' },
          { key: 'vanHanh', label: 'Vận hành ID (1 - 8)' },
          { key: 'quyHoach', label: 'Quy hoạch ID (1 - 7)' },
          { key: 'ccdv', label: 'CCDV ID (8 - 10)' },
          { key: 'soHuuRack', label: 'Sở hữu Rack (1: IDC, 0: Khách hàng)' },
          { key: 'suDung', label: 'Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)' }
        ],
        // Header cho data grid preview (nếu không truyền thì sẽ dùng tableColumns)
        gridColumns: [
          { key: 'tenRack', label: 'Tên Rack' },
          { key: 'loaiRackId', label: 'Loại Rack ID' },
          { key: 'maTaiSan', label: 'Mã tài sản' },
          { key: 'vanHanh', label: 'Vận hành ID (1 - 8)' },
          { key: 'quyHoach', label: 'Quy hoạch ID (1 - 7)' },
          { key: 'ccdv', label: 'CCDV ID (8 - 10)' },
          { key: 'soHuuRack', label: 'Sở hữu Rack (1: IDC, 0: Khách hàng)' },
          { key: 'suDung', label: 'Hiệu lực (1: Hiệu lực, 0: Không hiệu lực)' }
        ],
        validationRules: [
          { field: 'tenRack', required: true, message: 'Tên Rack không được để trống' },
          { field: 'tenRack', maxLength: 100, message: 'Tên Rack không được vượt quá 100 ký tự' },
          { field: 'loaiRackId', required: true, message: 'Loại Rack ID không được để trống' },
          { field: 'loaiRackId', type: 'number', message: 'Loại Rack ID phải nhập số' },
          { field: 'maTaiSan', required: true, maxLength: 50, message: 'Mã tài sản không được để trống' },
          { field: 'vanHanh', required: true, type: 'number', enum: [1, 2, 3, 4, 5, 6, 7, 8], message: 'Vận hành chỉ nhận số từ 1 đến 8 hoặc để trống' },
          { field: 'vanHanh', type: 'number', message: 'Vận hành phải nhập số' },
          { field: 'quyHoach', required: true, type: 'number', enum: [1, 2, 3, 4, 5, 6, 7], message: 'Quy hoạch chỉ nhận số từ 1 đến 7 hoặc để trống' },
          { field: 'quyHoach', type: 'number', message: 'Quy hoạch phải nhập số' },
          { field: 'ccdv', required: true, type: 'number', enum: [8, 9, 10], message: 'CCDV chỉ nhận số từ 8 đến 10 hoặc để trống' },
          { field: 'ccdv', type: 'number', message: 'CCDV phải nhập số' },
          { field: 'soHuuRack', required: true, type: 'number', message: 'Sở hữu Rack phải nhập số' },
          { field: 'soHuuRack', type: 'number', enum: [1, 0], message: 'Sở hữu Rack chỉ nhận số 0 hoặc 1' },
          { field: 'ghiChu', maxLength: 500, message: 'Ghi chú không được vượt quá 500 ký tự' },
          { field: 'suDung', type: 'number', enum: [1, 0], message: 'Hiệu lực chỉ nhận số 0 hoặc 1 hoặc để trống' }
        ],
        checkDataResult: true,
      }
    };
  },
  computed: {
    // Transform data từ API sang format của vue-treeselect
    nguoiQLTreeSelectList() {
      return this.nguoiQLSelectList.map(item => ({
        id: item.NHANVIEN_ID,
        label: item.TEN_NV
      }));
    },
    grdItems: function () {
      return this.$refs.grdItems;
    },
    historyItems: function () {
      return this.$refs.historyItems;
    },
  },
  mounted() {
    // Xử lý enable date picker và select người quản lý
    this.setDatePickerEnabled(this.controls.enable.enabled);
    //this.setNguoiQLSelectEnabled(this.controls.enable.enabled);

  },
  watch: {
    "dataItem.RACK_ID": function (value) {
      this.buttons.btnSua.enabled = value > 0;
      this.buttons.btnXoa.enabled = value > 0;
    },
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
    'controls.enable.enabled': {
      handler(newVal) {
        this.setDatePickerEnabled(newVal);
        //this.setNguoiQLSelectEnabled(newVal);
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
    }

  },
  created: async function () {
    try {
      const [
        thongtinRackList,
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
        this.getTTRackItems(),
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

      this.thongtinRackList = thongtinRackList;
      this.loaiRackSelectList = loaiRackSelectList;
      this.hangSXSelectList = hangSXSelectList;
      this.chuQuanSelectList = chuQuanSelectList;
      this.ccdvSelectList = ccdvSelectList;
      this.vanHanhSelectList = vanHanhSelectList;
      this.quyHoachSelectList = quyHoachSelectList;
      this.donviQLSelectList = donviQLSelectList;
      this.phongQLSelectList = phongQLSelectList;
      this.nguoiQLSelectList = nguoiQLSelectList;
      await this.getHistoryList();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {

    // ========== UI HELPERS ==========
    setDatePickerEnabled(value) {
      const picker = this.$refs.ngaySDPicker;
      if (picker && picker.ej2Instances) {
        picker.ej2Instances.enabled = value;
        picker.ej2Instances.dataBind();
      }
    },
    // setNguoiQLSelectEnabled(value) {
    //   const select = this.$refs.nguoiQLSelect;
    //   if (select && select.ej2Instances) {
    //     select.ej2Instances.enabled = value;
    //     select.ej2Instances.dataBind();
    //   }
    // },
    getHieuLucText: function (value) {
      return value == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
    getColumnHieuLucTemplate: function (value) {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return value.getHieuLucText(this.data.SUDUNG);
              },
            },
          },
        };
      };
    },
    // onNguoiQLChange(args) {
    //   this.$set(this.dataItem, 'DS_NGUOIQL', value ? [...value] : []);

    //   if (!value || value.length === 0) {
    //     this.$set(this.dataItem, 'DS_NGUOIQL_WITH_ROLE', []);
    //     return;
    //   }

    //   const withRole = value.map((id, index) => ({
    //     NHANVIEN_ID: id,
    //     NHIEMVU_ID: index === 0 ? 1 : 2
    //   }));

    //   this.$set(this.dataItem, 'DS_NGUOIQL_WITH_ROLE', withRole);
    // },

    // Xử lý đóng mở Toggle Block
    toggleBlock(name) {
      this.collapsedBlocks[name] = !this.collapsedBlocks[name];
    },
    expandAllBlocks() {
      for (const key in this.collapsedBlocks) {
        this.collapsedBlocks[key] = false; // mở hết
      }
    },
    collapseAllBlocks() {
      for (const key in this.collapsedBlocks) {
        if (key !== 'thongTinRack') {
          this.collapsedBlocks[key] = true; // thu hết trừ thongTinRack
        }
      }
    },

    // ========== API CALLS ==========
    getTTRackItems: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-tt-rack");
        const rawList = rs.data || [];
        //Xử lý thêm DS_NGUOIQL_DISPLAY
        result = rawList.map(rack => {
          let racks = [];
          try {
            racks = JSON.parse(rack.DS_NGUOIQL || '[]');
          } catch (e) {
            console.warn('Lỗi khi parse DS_NGUOIQL:', e);
          }

          return {
            ...rack, // Giữ nguyên các trường gốc
            DS_NGUOIQL: racks.map(c => c.id),
            DS_NGUOIQL_DISPLAY: racks.map(c => c.name).join(', ')
          };
        });
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
    getHistoryList: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/thongtin-rack/get-danhmuc-rack", {
          loaiDanhMuc: "LSTD-RACK",
          thamSo1:null
        });
        this.historyRackList = rs.data || [];
        this.historyRackList.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
    },
    upsertRack: async function (args) {
      let result = false;
      try {
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
        });
        result = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },
    deleteRack: async function (rackId) {
      let result = false;
      try {
        let rs = await this.$root.context.post('/web-ecms/thongtin-rack/delete', {
          rackId: rackId
        });
        result = rs.data;
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    },

    // Reload danh sách
    reloadRacks: async function () {
      this.thongtinRackList = await this.getTTRackItems();
    },
    reloadHistory: async function () {
      await this.getHistoryList();
    },

    // ========== DATA ITEM MANAGEMENT ==========
    setCurrentItem: async function (item) {
      this.dataItem.RACK_ID = item.RACK_ID;
      this.dataItem.TEN_RACK = item.TEN_RACK;
      this.dataItem.LOAIRACK_ID = item.LOAIRACK_ID;
      this.dataItem.HANGSX_ID = item.HANGSX_ID;
      this.dataItem.CHUQUAN_ID = item.CHUQUAN_ID;
      this.dataItem.TS_NOIBO = item.TS_NOIBO;
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

      this.dataItem.DS_NGUOIQL = [];   // reset trước để vue-treeselect quên hoàn toàn giá trị cũ

      this.$nextTick(() => {
        const raw = item.DS_NGUOIQL || [];
        const ids = Array.isArray(raw)
          ? raw.map(x => (typeof x === 'object' ? x.id || x.NHANVIEN_ID || x : x)).filter(Boolean)
          : [];
        this.dataItem.DS_NGUOIQL = ids.map(id => Number(id));
      });
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
        TS_NOIBO: 1,
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

    // ========== Điều khiển Controls, Validation ==========
    enableControls: function (args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args;
        }
      }
      this.buttons.btnNhapMoi.enabled = !args;
      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnHuy.enabled = this.buttons.btnGhiLai.enabled;
      this.buttons.btnXoa.enabled = !args;
      this.buttons.btnImport.enabled = !args;
      this.buttons.btnExport.enabled = !args;

      if (this.dataItem.RACK_ID > 0)
        this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },

    validateForm() {
      let errors = [];
      if (!this.dataItem.TEN_RACK) errors.push('Tên Rack không được để trống');
      if (!this.dataItem.CHUQUAN_ID) errors.push('Đơn vị sở hữu không được để trống');
      if (!this.dataItem.LOAIRACK_ID) errors.push('Loại Rack không được để trống');
      if (!this.dataItem.HANGSX_ID) errors.push('Hãng sản xuất không được để trống');
      if (!this.dataItem.CAMERA) errors.push('Trạng thái camera không được để trống');
      if (!this.dataItem.NGAY_SD) errors.push('Ngày sử dụng không được để trống');
      if (!this.dataItem.VANHANH) errors.push('Trạng thái vận hành không được để trống');
      // if (this.dataItem.TS_NOIBO == 1) {
      //   if (!this.dataItem.MA_TS) errors.push('Mã tài sản không được để trống');
      // }
      if (!this.dataItem.CCDV) errors.push('Trạng thái Rack CCDV không được để trống');
      if (!this.dataItem.QUYHOACH) errors.push('Trạng thái Rack theo quy hoạch không được để trống');
      return errors;
    },


    // ========== API Minio Helpers ==========
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
      this.enableControls(true);
      this.expandAllBlocks();
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

            // 3) Cập nhật thông tin Rack
            let rs = await this.upsertRack(this.dataItem);
            // 🟡 1) Nếu SP trả lỗi
            if (rs?.STATUS === "ERROR") {
              this.$root.toastError(rs.MESSAGE || "Lỗi chưa xác định");
              return; // DỪNG ở đây
            }
            if (rs?.RACK_ID) {
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

            }
            else {
              if (this.dataItem.RACK_ID === 0) {
                this.$root.toastError("Thêm Rack thất bại");
              }
              else {
                this.$root.toastError("Cập nhật Rack thất bại");
              }
            }
            this.enableControls(!rs);
          } finally {
            this.reloadRacks();
            this.reloadHistory();
            this.$root.showLoading(false);
          }
        }
      }
    },
    btnSua_Click: async function () {
      this.enableControls(true);
      this.expandAllBlocks();
    },
    btnHuy_Click: async function () {
      let items = this.grdItems.getSelectedRecords();
      let item = null;
      if (!(items == null || items.length == 0)) {
        if (this.dataItem.RACK_ID == 0) {
          item = items[0];
        }
        else {
          item = items.find(x => x.RACK_ID == this.dataItem.RACK_ID);
        }
      }
      if (item != null) {
        this.setCurrentItem(item);
      } else {
        this.setCurrentItemDefault();
      }
      // Hủy thay đổi icon tạm thời
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
      this.collapseAllBlocks();
    },

    btnXoa_Click: async function () {
      if (this.dsSelected.length <= 0) {
        this.$toast.error('Chưa chọn danh mục cần xoá.')
        return
      }
      let items = this.grdItems.getSelectedRecords();
      let msg = `Bạn có muốn xóa dữ liệu không?`
      let flag = await this.$confirm(msg, "Xác nhận",
        {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Không đồng ý",
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
          let promises = []
          for (let item of this.dsSelected) {
            promises.push(await this.deleteRack(item.RACK_ID))
          }

          if ((await Promise.all(promises)).some(x => x == false)) {
            this.$toast.error(`Xoá không thành công`)
            return
          } else {
            this.$toast.success(`Đã xoá thành công`)
          }
          for (const item of items) {
            if (item.HINHANH) {
              try {
                await this.deleteFileFromMinio(item.HINHANH)
              } catch (e) {
                console.warn('Failed to delete icon:', item.HINHANH, e)
              }
            }
          }
          await this.reloadRacks();
          await this.reloadHistory();
          this.enableControls(false)
        } finally {
          this.$root.showLoading(false);
        }
      }
    },


    // ========== Sự kiện DataGrid ==========
    grdItems_RowSelected: async function (args) {
      try {
        this.$root.showLoading(true);
        this.setCurrentItem(args.data);
      } finally {
        this.$root.showLoading(false);
      }
    },
    grdItems_RowDeselected: async function () {
      let items = this.grdItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        try {
          this.$root.showLoading(true);
          this.setCurrentItem(items[0]);
        } finally {
          this.$root.showLoading(false);
        }
      } else {
        this.setCurrentItemDefault();
      }
    },

    onSelectRowCheckbox(itemsCheck) {
      this.dsSelected = itemsCheck
    },

    grid_onCommandClicked: function (name, dataItem) {

      if (dataItem.RACK_ID > 0 && name == "THIETBI") {
        this.$refs.popupDanhSachThietbi.showModal(dataItem.RACK_ID, dataItem.TS_NOIBO);
      } else if (name == "THONGTIN-U") {
        this.$refs.popupDanhSachU.showModal(dataItem.RACK_ID);
      } else if (name == "THANHNGUON") {
        this.$refs.popupDanhSachThanhNguon.showModal(dataItem.RACK_ID);
      } else {
        this.$toast.warning("Vui lòng chọn Rack trước khi xem thiết bị!");
      }

    },

    // onRackRowDoubleClick: async function (args) {
    //   this.selectedRackId = args.rowData.RACK_ID; // Lưu rackId khi double-click
    //   this.$refs.ModalThongTinRack.dialogOpen(this.selectedRackId);
    // },

    // ========== Sự kiện Import/Export Excel ==========
    btnImport_Click: async function() {
      try {
        const result = await this.$refs.modalImportExcel.showModal();

        if (result.success && result.data && result.data.length > 0) {
          // Xác nhận import
          const confirmFlag = await this.$confirm(
            `Bạn có chắc muốn import ${result.data.length} Rack từ file Excel?`,
            "Xác nhận import",
            {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
              type: "warning",
              dangerouslyUseHTMLString: true,
            }
          ).then((s) => true).catch((e) => false);

          if (confirmFlag) {
            await this.processImportData(result.data);
          }
        }
      } catch (error) {
        console.error('Lỗi khi import Excel:', error);
        this.$root.toastError('Có lỗi khi import Excel: ' + error.message);
      }
    },
    bulkCreateItems: async function (items) {
      let result = false;
      try {
        let rs = await this.$root.context.post('/web-ecms/thongtin-rack/bulk-create', items);
        result = rs.data;
        if (result.successCount > 0) {
          this.$root.toastSuccess(`Import thành công ${result.successCount} Rack.`);
        }
        if (result.failedCount > 0) {
          this.$root.toastError(`Bỏ qua ${result.failedCount} Rack.`);
        }
      } catch(e) {
        if(!(e.response == null || e.response == undefined)){
          if(!(e.response.data == null || e.response.data == undefined)){
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    processRackExcelData(dataRows) {
      return dataRows.map((row, index) => {
        return {
          loaiRackId: row[1] || null,
          tenRack: row[0] || '',
          maTaiSan: row[2] || '',
          quyHoach: row[4] || null,
          vanHanh: row[3] || null,
          ccdv: row[5] || null,
          soHuuRack: row[6] === 1 ? 0 : 1, // Mặc định là 1 (IDC) nếu không đúng định dạng
          suDung: row[7] === 1 ? 1 : 0, // Mặc định là 0 nếu không đúng định dạng
        };
      })
    },
    // Hàm xử lý dữ liệu Excel
     processImportData: async function (importData) {
      try {
        this.$root.showLoading(true);
        let successCount = 0;
        let errorCount = 0;
        const errors = [];
        const validItems = [];

        for (let i = 0; i < importData.length; i++) {
          const item = importData[i];

          try {
            let pSuDung = 1; // Default value
            if (item.suDung !== undefined && item.suDung !== null) {
              // Nếu là string, convert sang number
              if (typeof item.suDung === 'string') {
                pSuDung = item.suDung.toLowerCase() === 'hiệu lực' ? 1 : 0;
              } else {
                // Nếu là number, sử dụng trực tiếp
                pSuDung = item.suDung ? 1 : 0;
              }
            }

            // Chuẩn bị dữ liệu theo format API bulk-create
            const importItem = {
              loaiRackId: item.loaiRackId || null,
              tenRack: item.tenRack.trim() || null,
              maTaiSan: item.maTaiSan || null,
              quyHoach: item.quyHoach || null,
              vanHanh: item.vanHanh || null,
              ccdv: item.ccdv || null,
              suDung: item.suDung || 1,
              ghiChu: item.ghiChu || null,
              soHuuRack: item.soHuuRack || 1
            };

            validItems.push(importItem);

          } catch (error) {
            errorCount++;
            errors.push(`Dòng ${i + 1}: ${error.message}`);
          }
        }

        // Nếu có dữ liệu hợp lệ, gọi bulk API
        if (validItems.length > 0) {
          try {
            const result = await this.bulkCreateItems(validItems);
            if (result) {
              successCount = validItems.length;
            } else {
              errorCount += validItems.length;
              errors.push(`Lỗi khi lưu dữ liệu hàng loạt`);
            }
          } catch (error) {
            errorCount += validItems.length;
            errors.push(`Lỗi khi gọi API bulk-create: ${error.message}`);
          }
        }

        // Reload data sau khi import
        await this.reloadRacks();
        await this.reloadHistory();
      } finally {
        this.$root.showLoading(false);
      }
    },
    async handleCheckData(checkDataInfo) {
      try {
        // Chuẩn bị dữ liệu theo format API validate-batch
        const validateData = checkDataInfo.data.map(item => ({
          loaiRackId: item.loaiRackId || null,
          tenRack: item.tenRack.trim() || null,
          maTaiSan: item.maTaiSan || null,
          quyHoach: item.quyHoach || null,
          vanHanh: item.vanHanh || null,
          ccdv: item.ccdv || null,
          suDung: item.suDung || 1,
          ghiChu: item.ghiChu || null,
          soHuuRack: item.soHuuRack || 1
        }));

        // Gọi API validate-batch
        const response = await this.$root.context.post('/web-ecms/thongtin-rack/validate-batch', validateData);
        if (response.data) {
          const result = response.data;

          if (result.isValid) {
            this.importConfig.checkDataResult = false; // Không có lỗi -> enable button "Ghi lại"
            this.$root.toastSuccess('Dữ liệu hợp lệ!');
          } else {
            this.importConfig.checkDataResult = true; // Có lỗi -> disable button "Ghi lại"
            const errorMessage = 'Có tổng ' + result.errorCount + ' dòng dữ liệu lỗi. \n' + result.errorMessage;
            this.$root.toastError(errorMessage);
            // emit cho modalImportExcel để hiển thị lỗi lên grid
            this.$refs.modalImportExcel.handleCheckDataResult({
              isValid: false,
              errors: result.errorMessage,
            })
          }
        } else {
          this.importConfig.checkDataResult = true; // Có lỗi -> disable button "Ghi lại"
          this.$root.toastError('Lỗi khi nhận phản hồi từ server');
        }
      } catch (error) {
        this.importConfig.checkDataResult = true; // Có lỗi -> disable button "Ghi lại"
        console.error('Lỗi khi validate batch:', error);

        if (error.response && error.response.data && error.response.data.message_detail) {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.response.data.message_detail);
        } else {
          this.$root.toastError('Lỗi khi kiểm tra dữ liệu: ' + error.message);
        }
      }
    },
    formatRackCellValue(value, fieldName = null) {
      if (value === null || value === undefined || value === '') return '';

      // Chỉ format cho trường hiệu lực (suDung)
      if (fieldName === 'suDung' && typeof value === 'number' && (value === 1 || value === 0)) {
        return value === 1 ? 'Hiệu lực' : 'Không hiệu lực';
      }
      return value;
    },

    btnExport_Click: async function () {
      try {
        if (this.thongtinRackList == []) {
          this.$toast.warning("Dữ liệu trống!");
        } else {
          let data = []
          this.thongtinRackList.forEach(item => {
            data.push({
              'ID Rack': item.RACK_ID,
              'Tên Rack': item.TEN_RACK,
              'Loại Rack': item.TEN_LOAIRACK,
              'Vị trí Rack': item.VITRI_RACK,
              'Mã tài sản': item.MA_TS,
              'Trạng thái vận hành': item.VANHANH,
              'Trạng thái quy hoạch': item.QUYHOACH,
              'Trạng thái Rack CCDV': item.CCDV,
              'IDC': item.TEN_IDC,
              'Mặt sàn': item.TEN_MATSAN,
              'Phòng/Zone': item.TEN_PHONG,
              'Sở hữu': item.TS_NOIBO,
              'Hiệu Lực': item.SUDUNG,
            })
          })

          let worksheet = XLSX.utils.json_to_sheet(data);
          worksheet['!cols'] = [
            { wch: 12 }, { wch: 25 }, { wch: 25 }, { wch: 25 }, { wch: 25 },
            { wch: 25 }, { wch: 20 }, { wch: 15 }, { wch: 25 }, { wch: 25 },
            { wch: 20 }, { wch: 12 }
          ];
          let workbook = XLSX.utils.book_new()
          XLSX.utils.book_append_sheet(workbook, worksheet, "Danh sách Rack")
          XLSX.writeFile(workbook, "Báo cáo Thông tin Rack.xlsx")
          this.$toast.success("Xuất file Excel thành công!");
        }
      } catch (ex) {
        this.$toast.error("Có lỗi trong quá trình xuất file Excel !" + ex)
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
  overflow: hidden !important;
}

.fade-enter-active, .fade-leave-active {
  transition: all 0.1s ease;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
  height: 0;
  overflow: hidden;
}
.toggle-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #0176FF;
  cursor: pointer;
}
.toggle-header:hover {
  background-color: #d8c4c4;
}
.toggle-icon {
  font-size: 14px;
  transition: transform 0.3s;
}
.toggle-icon.rotate {
  transform: rotate(180deg);
}
/* Tùy chỉnh vue-treeselect */
.vue-treeselect__multi-value-item:first-child {
  background-color: #1e40af !important;
  color: #ffffff !important;
  border: 2px solid #1e40af !important;
  font-weight: 600 !important;
}

.vue-treeselect__multi-value-item:not(:first-child) {
  background-color: #f3f4f6 !important;
  color: #374151 !important;
  border: 1px solid #d1d5db !important;
}
</style>
