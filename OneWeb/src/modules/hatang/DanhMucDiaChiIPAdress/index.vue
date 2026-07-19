<template src="./index.html"></template>

<script>
import { log } from 'pdfmake/build/pdfmake';

export default {
  props: {
    modalItemID: 0
  },
  data: function () {
    return {
      ipAddressList: [],
      ipAddressHistoryList:[],
      ipStoreList: [],
      nguoiDungCapList: [],
      nguoiDungSDList: [],
      donViCapList: [],
      donViSDList: [],
      subnetQuyHoachList: [],
      ipDoiTuongCapPhatList: [],
      thietBiMangList: [],
      paramSearchDonViSD: {
        thamSo1: 0,
        thamSo2: ''
      },
      paramSearchDonViCap: {
        thamSo1: 0,
        thamSo2: ''
      },
      tinhThanhList: [],
      userInfo: {
        nhanVienId: 0,
        mayCapNhap: '',
        nguoiCapNhap: '',
        ipCapNhap: '',
        phanVungID: 0,
        donViId: 0,
        donviName: ''
      },
      ipAddress: {
        id: 0,
        ip: '',                 //unique value
        hieuLuc: 1,
        ipStoreId: 0,
        ipStoreName: '',
        ipLoaiId: 0,
        ipLoaiName: '',
        ipSubnetId: 0,          //thuoc subnet
        ipSubnetName: '',
        haTangMangId: 0,        //thuoc hatang mang
        haTangMangName: '',
        subnetQuyHoachId: 0,     //muc dich su dung
        subnetQuyHoachName: '',
        doiTuongCapPhatId: 0,
        doiTuongCapPhatName: '',
        thietBiMangId: 0,       //thuoc thiet bi mang
        thietBiMangName: '',
        trangThaiCapPhat: 0,    //0- Chờ cấp phát, 2- Đã cấp phát
        nguoiCapId: 0,
        nguoiCapName: '',
        donViCapId: 0,
        donViCapName: '',
        nguoiSuDungId: 0,
        nguoiSuDungName: '',
        donViSuDungId: 0,
        donViSuDungName: '',
        ngayCap: '',
        ngayThuHoi: '',
        ngayHetHan: '',
        thamChieu: '',        //Sở cứ tham chiếu
        ghiChu: '',
        tacDong: '',
        nhanVienId: 0,
        mayCapNhap: '',
        nguoiCapNhap: '',
        ipCapNhap: '',
        phanVungNguoiCapID: 0,
        phanVungNguoiSDID: 0
      },
      controls: {
        txtIP: {
          enabled: false,
          invalid: false,
        },
        chkHieuLuc: {
          enabled: false,
        },
        selIpStore: {
          enabled: false,
          invalid: false,
        },
        selNguoiCapId: {
          enabled: false
        },
        selDonViCapId: {
          enabled: false
        },
        selNguoiSuDungId: {
          enabled: false
        },
        selDonViSuDungId: {
          enabled: false
        },
        selSubnetQuyHoachId: {
          enabled: false
        },
        selNgayCap: {
          enabled: false
        },
        selDoiTuongCapPhatId: {
          enabled: false
        },
        selNgayThuHoi: {
          enabled: false
        },
        selNgayHetHan: {
          enabled: false
        },
        selTrangThaiCapPhat: {
          enabled: false
        },
        selThamChieu: {
          enabled: false
        },
        txtGhiChu: {
          enabled: false,
        },
      },
      buttons: {
        btnAddNew: {
          enabled: true,
        },
        btnSave: {
          enabled: false,
        },
        btnCancel: {
          enabled: false,
        },
        btnEdit: {
          enabled: false,
        },
        btnDelete: {
          enabled: false,
        },
      },
    };
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems;
    },
    grdItems_history: function() {
      return this.$refs.grdItems_history;
    },
    grdItems_donvi_sd: function() {
      return this.$refs.grdItems_donvi_sd;
    },
    grdItems_nhanvien_sd: function() {
      return this.$refs.grdItems_nhanvien_sd;
    }
  },
  watch: {
    "ipAddress.id": function (value) {
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
    "ipAddress.ip": function (value) {
      this.controls.txtIP.invalid = value == "";
    },
    "ipAddress.ipStoreId": function (value) {
      this.controls.selIpStore.invalid = value > 0;
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.ipAddressList = await this.apiListDanhSach();

      this.apiGetSelectionItem();
      // init current user Info
      this.userInfo.nhanVienId = await this.$root.token.getNhanVienID();
      this.userInfo.nguoiCapNhap = await this.$root.context.user.getTenNhanVien();
      this.userInfo.mayCapNhap = await this.$root.token.getMachine();
      this.userInfo.ipCapNhap = await this.$root.token.getIP();
      this.userInfo.phanVungID = await this.$root.token.getPhanVungID();
      this.userInfo.donViId = await this.$root.token.getDonViID();
      this.userInfo.donviName = await this.$root.context.user.getTenDonVi();
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    getHieuLucText: function (value) {
      return value == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
    getColumnHieuLucTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getHieuLucText(this.data.HIEU_LUC);
              },
            },
          },
        };
      };
    },
    apiListDanhSach: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/quanly-diachiip/get-all");
        rs.data.forEach(item => {
          item.TRANGTHAICAPPHAT_NAME = item.TRANGTHAICAPPHAT == 0 ? "Chờ cấp phát" : "Đã cấp phát";
        });
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiListTinhThanh: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/tinh-thanh");
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiSearchDonVi: async function(param) {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms//danhmuc/find-don-vi", param);
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiSearchNhanVienByDonViID: async function(id) {
      let result = [];
      try {
        let rs = await this.$root.context.get(`/web-ecms/danhmuc/nhan-vien-don-vi?donViId=${id}`)
        result = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiGetSelectionItem: async function() {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/quanly-diachiip/selection-data");
        result = rs.data;
        this.ipStoreList = result.ipStoreList;
        this.subnetQuyHoachList = result.subnetQuyHoachList;
        this.ipDoiTuongCapPhatList = result.ipDoiTuongCapPhatList;
        this.thietBiMangList = result.thietBiMangList;

      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },
    apiSave: async function (args) {
      let result = 0;
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/quanly-diachiip/create-or-update", {
          id: args.id,
          ip: args.ip,                 //unique value
          hieuLuc: args.hieuLuc,
          ipStoreId: args.ipStoreId,
          ipStoreName: args.ipStoreName,
          ipLoaiId: args.ipLoaiId,
          ipLoaiName: args.ipLoaiName,
          ipSubnetId: args.ipSubnetId,          //thuoc subnet
          ipSubnetName: args.ipSubnetName,
          haTangMangId: args.haTangMangId,        //thuoc hatang mang
          haTangMangName: args.haTangMangName,
          subnetQuyHoachId: Number(args.subnetQuyHoachId),     //muc dich su dung
          subnetQuyHoachName: args.subnetQuyHoachName,
          doiTuongCapPhatId: args.doiTuongCapPhatId,
          doiTuongCapPhatName: args.doiTuongCapPhatName,
          thietBiMangId: args.thietBiMangId,       //thuoc thiet bi mang
          thietBiMangName: args.thietBiMangName,
          trangThaiCapPhat: args.trangThaiCapPhat,    //0- Chờ cấp phát, 2- Đã cấp phát
          nguoiCapId: args.nguoiCapId,
          nguoiCapName: args.nguoiCapName,
          donViCapId: args.donViCapId,
          donViCapName: args.donViCapName,
          nguoiSuDungId: args.nguoiSuDungId,
          nguoiSuDungName: args.nguoiSuDungName,
          donViSuDungId: args.donViSuDungId,
          donViSuDungName: args.donViSuDungName,
          ngayCap: args.ngayCap,      // Gửi trực tiếp giá trị đã format
          ngayThuHoi: args.ngayThuHoi,
          ngayHetHan: args.ngayHetHan,
          thamChieu: args.thamChieu,        //Sở cứ tham chiếu
          ghiChu: args.ghiChu,
          nhanVienId: args.nhanVienId,
          mayCapNhap: args.mayCapNhap,
          nguoiCapNhap: args.nguoiCapNhap,
          ipCapNhap: args.ipCapNhap
        });
        result = rs.data;
      } catch(e) {
        console.log('catch(e)',e);
        result = -1;
      }
      return result;
    },
    apiDelete: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/quanly-diachiip/delete", {id: id});
        result = rs.error_code=='BSS-00000000';
      } catch(e) {
        this.$root.toastError(e.response.data.message);
            result =false;
      }
      return result;
    },
    enableControls: function (args) {
      this.controls.selNgayHetHan.enabled = args;
      this.controls.selNgayThuHoi.enabled = args;
      this.controls.selNgayCap.enabled = args;
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args;
        }
      }

      this.buttons.btnSave.enabled = args;
      this.buttons.btnAddNew.enabled = !args;
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
      this.buttons.btnDelete.enabled = !args;

      if (this.ipAddress.id > 0)
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
    },
    setCurrentItem: async function (item) {
      this.ipAddress.id = item.ID;
      this.ipAddress.ip = item.IP;
      this.ipAddress.ipStoreId = item.IP_STORE_ID;
      this.ipAddress.ipStoreName = item.IP_STORE_IP;
      this.ipAddress.ipLoaiId = item.LOAI_IP_ID;
      this.ipAddress.ipLoaiName = item.LOAI_IP_NAME;
      this.ipAddress.ipSubnetId = item.SUBNET_MARK_ID;
      this.ipAddress.ipSubnetName = item.SUBNET_MARK_NAME;
      this.ipAddress.haTangMangId = item.HA_TANG_MANG_ID;
      this.ipAddress.haTangMangName = item.HA_TANG_MANG_NAME;
      this.ipAddress.subnetQuyHoachId = item.SUBNET_ID;
      this.ipAddress.subnetQuyHoachName = item.SUBNET_NAME;
      this.ipAddress.doiTuongCapPhatId = item.DOITUONGCAPHAT_ID;
      this.ipAddress.doiTuongCapPhatName = item.DOITUONGCAPPHAT_NAME;
      this.ipAddress.thietBiMangId = item.THIET_BI_MANG_ID;
      this.ipAddress.thietBiMangName = item.THIET_BI_MANG_NAME;
      this.ipAddress.trangThaiCapPhat = item.TRANGTHAICAPPHAT;
      this.ipAddress.nguoiCapId = item.NHANVIEN_CAP_ID;
      this.ipAddress.nguoiCapName = item.NHANVIEN_CAP_NAME;
      this.ipAddress.donViCapId = item.DONVI_CAP_ID;
      this.ipAddress.donViCapName = item.DONVI_CAP_NAME;
      this.ipAddress.nguoiSuDungId = item.NHANVIEN_SD_ID;
      this.ipAddress.nguoiSuDungName = item.NHANVIEN_SD_NAME;
      this.ipAddress.donViSuDungId = item.DONVI_SD_ID;
      this.ipAddress.donViSuDungName = item.DONVI_SD_NAME;
      this.ipAddress.ngayCap = item.NGAY_CAP;
      this.ipAddress.ngayThuHoi = item.NGAY_THUHOI;
      this.ipAddress.ngayHetHan = item.NGAY_HETHAN;
      this.ipAddress.thamChieu = item.THAMCHIEU;
      this.ipAddress.ghiChu = item.GHI_CHU;
      this.ipAddress.hieuLuc = item.HIEU_LUC;
      this.ipAddress.phanVungNguoiSDID = item.PHANVUNG_SD_ID;
      this.ipAddress.phanVungNguoiCapID = item.PHANVUNG_CAP_ID;

      this.ipAddress.nhanVienId = this.userInfo.nhanVienId;
      this.ipAddress.mayCapNhap = this.userInfo.mayCapNhap;
      this.ipAddress.nguoiCapNhap = this.userInfo.nguoiCapNhap;
      this.ipAddress.ipCapNhap = this.userInfo.ipCapNhap;

      try {
        let rs = await this.$root.context.get("/web-ecms/hatang/quanly-diachiip/get-change-history", {id: item.ID});
        this.ipAddressHistoryList = rs.data;
      } catch(e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        "ID": 0,
        "IP": "",
        "HIEU_LUC": 1,
        "TRANGTHAICAPPHAT": 0,
        "NGAY_CAP": "",
        "NGAY_THUHOI": "",
        "NGAY_HETHAN": "",
        "THAMCHIEU": "",
        "GHI_CHU": "",
        "IP_STORE_ID": 0,
        "IP_STORE_IP": "",
        "LOAI_IP_ID": 0,
        "LOAI_IP_NAME": "",
        "SUBNET_MARK_ID": 0,
        "SUBNET_MARK_NAME": "",
        "SUBNET_ID": 0,
        "SUBNET_NAME": "",
        "DOITUONGCAPHAT_ID": 0,
        "DOITUONGCAPPHAT_NAME": "",
        "NHANVIEN_SD_ID": null,
        "NHANVIEN_SD_NAME": "",
        "DONVI_SD_ID": null,
        "DONVI_SD_NAME": "",
        "HA_TANG_MANG_NAME": "",
        "HA_TANG_MANG_ID": 0,
        "THIET_BI_MANG_NAME": "",
        "THIET_BI_MANG_ID": 0,
        "PHANVUNG_SD_ID": 0,

        "NHANVIEN_CAP_ID": this.userInfo.nhanVienId,
        "NHANVIEN_CAP_NAME": this.userInfo.nguoiCapNhap,
        "DONVI_CAP_ID": this.userInfo.donViId,
        "DONVI_CAP_NAME": this.userInfo.donviName,
        "PHANVUNG_CAP_ID": this.userInfo.phanVungID
      });
    },
    validateControls: function () {
      let result = true;
      if (this.ipAddress.ip=='') {
        result = false;
        this.$root.toastError("IP không được để trống.");
      }
      //check ip format
      const ipv4Regex = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
      const ipv6Regex = /^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$/;

      if (!ipv4Regex.test(this.ipAddress.ip) && !ipv6Regex.test(this.ipAddress.ip)) {
        result = false; 
        this.$root.toastError("IP không đúng định dạng IPv4 hoặc IPv6.");
      }
      if (this.ipAddress.ipStoreId==0) {
        result = false;
        this.$root.toastError("IP Store không được để trống.");
      }
      // Validate định dạng ngày tháng
      try {
        // Parse string to Date object
        const parseDate = (dateStr) => {
          if (!dateStr) return null;
          if (dateStr instanceof Date) return dateStr;
          // Parse dd/MM/yyyy format
          const parts = dateStr.split('/');
          if (parts.length === 3) {
            const date = new Date(parts[2], parts[1] - 1, parts[0]);
            return date;
          }
          return new Date(dateStr);
        };

        const formatDate = (date) => {
          if (!date) return '';
          const day = String(date.getDate()).padStart(2, '0');
          const month = String(date.getMonth() + 1).padStart(2, '0');
          const year = date.getFullYear();
          return `${day}/${month}/${year}`;
        };

        const ngayCap = parseDate(this.ipAddress.ngayCap);
        const ngayThuHoi = parseDate(this.ipAddress.ngayThuHoi); 
        const ngayHetHan = parseDate(this.ipAddress.ngayHetHan);

        // Validate từng trường ngày
        if (ngayCap && isNaN(ngayCap.getTime())) {
          result = false;
          this.$root.toastError("Ngày cấp không đúng định dạng dd/MM/yyyy");
          return result;
        }

        if (ngayThuHoi && isNaN(ngayThuHoi.getTime())) {
          result = false;
          this.$root.toastError("Ngày thu hồi không đúng định dạng dd/MM/yyyy");
          return result;
        }

        if (ngayHetHan && isNaN(ngayHetHan.getTime())) {
          result = false;
          this.$root.toastError("Ngày hết hạn không đúng định dạng dd/MM/yyyy");
          return result;
        }

        // Validate logic ngày tháng
        if (ngayCap && ngayThuHoi && ngayCap > ngayThuHoi) {
          result = false;
          this.$root.toastError("Ngày thu hồi phải lớn hơn ngày cấp");
          return result;
        }

        if (ngayCap && ngayHetHan && ngayCap > ngayHetHan) {
          result = false;
          this.$root.toastError("Ngày hết hạn phải lớn hơn ngày cấp");
          return result;
        }

        // Format date back to string for API call
        if (result) {
          this.ipAddress.ngayCap = ngayCap ? formatDate(ngayCap) : null;
          this.ipAddress.ngayThuHoi = ngayThuHoi ? formatDate(ngayThuHoi) : null;
          this.ipAddress.ngayHetHan = ngayHetHan ? formatDate(ngayHetHan) : null;
        }

      } catch(e) {
        result = false;
        this.$root.toastError("Dữ liệu thời gian không đúng định dạng dd/MM/yyyy");
      }

      return result;
    },
    grdItems_RowSelected: async function (args) {
      this.setCurrentItem(args.data);
    },
    grdItems_RowSelected_DonViSD: async function(args) {
      this.nguoiDungSDList = await this.apiSearchNhanVienByDonViID(args.data.DONVI_ID);

      this.ipAddress.donViSuDungId = args.data.DONVI_ID;
      this.ipAddress.donViSuDungName = args.data.TEN_DV;
    },
    grdItems_RowSelected_DonViCap: async function(args) {
      this.nguoiDungCapList = await this.apiSearchNhanVienByDonViID(args.data.DONVI_ID);

      this.ipAddress.donViCapId = args.data.DONVI_ID;
      this.ipAddress.donViCapName = args.data.TEN_DV;
    },
    grdItems_RowSelected_NguoiSD: async function(args) {
      this.ipAddress.nguoiSuDungId = args.data.NHANVIEN_ID;
      this.ipAddress.nguoiSuDungName = args.data.TEN_NV;
    },
    grdItems_RowSelected_NguoiCap: async function(args) {
      this.ipAddress.nguoiCapId = args.data.NHANVIEN_ID;
      this.ipAddress.nguoiCapName = args.data.TEN_NV;
    },
    grdItems_RowDeselected: async function (args) {
      let items = this.grdItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0]);
      } else {
        this.setCurrentItemDefault();
      }
    },
    grdItems_RowSelected_History: async function (args) {

    },
    grdItems_RowDeselected_History: async function (args) {

    },
    onAddNew: async function () {
      this.setCurrentItemDefault();
      this.enableControls(true);
    },
    onSave: async function () {
      if (this.validateControls()) {
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
            let mess = this.ipAddress.id == 0 ? 'Thêm mới thông tin thành công' : 'Cập nhật thông tin thành công';
            this.$root.showLoading(true);
            let rs = await this.apiSave(this.ipAddress);
            console.log('await this.apiSave(this.ipAddress);', rs);
            if (rs > 0) {
                this.ipAddressList = await this.apiListDanhSach();
                this.$root.toastSuccess(mess);
              } else if (rs == 0) {
              this.$root.toastError("IP đã tồn tại.")
            } else {
              this.$root.toastError("Lỗi hệ thống.")
            }
            this.enableControls(!rs);
          } finally {
            this.$root.showLoading(false);
          }
        }
        return;
      }
    },
    onCancel: async function (args) {
      let items = this.grdItems.getSelectedRecords();
      let item = null;
      if (!(items == null || items.length == 0)) {
        if(this.ipAddress.id == 0){
          item = items[0];
        }
        else {
          item = items.find(x => x.ID == this.ipAddress.id);
        }
      }
      if (item != null) {
        this.setCurrentItem(item);
      } else {
        this.setCurrentItemDefault();
      }
      this.enableControls(false);
    },
    onEdit: async function (args) {
      this.enableControls(true);
    },
    onDelete: async function (args) {
      //this.dsSelected.length
      let items = this.grdItems.getSelectedRecords();
      let msg=`Bạn có muốn xóa ${items.length} mẫu tin đã chọn không?`
      let flag = await this.$confirm(msg,"Xác nhận",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
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
          this.$root.showLoading(true);
          let  isSuccess=false;
          for(const item of items){
            isSuccess = await this.apiDelete(item.ID);
            if (!isSuccess){ break;}
          }
          if (isSuccess) {
            this.setCurrentItemDefault();
            this.ipAddressList = await this.apiListDanhSach();
            this.enableControls(false);
            this.$root.toastSuccess("Xóa thông tin thành công");
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    onChangeIpStore: function(value) {
      console.log('onChangeIpStore', value);
      const rs = this.ipStoreList.find(item => item.ID == value);

      this.ipAddress.ipLoaiId = rs.IP_LOAI_ID;
      this.ipAddress.ipLoaiName = rs.IP_LOAI_NAME;
      this.ipAddress.ipSubnetId = rs.SUBNET_MARK_ID;
      this.ipAddress.ipSubnetName = rs.SUBNET_MARK_NAME;
      this.ipAddress.haTangMangId = rs.HA_TANG_MANG_ID;
      this.ipAddress.haTangMangName = rs.HA_TANG_MANG_NAME;
      this.ipAddress.thietBiMangId = rs.THIET_BI_MANG_ID;
      this.ipAddress.thietBiMangName = rs.THIET_BI_MANG_NAME;
    },
    hideModal: function() {
      this.$refs["popupChonDonViSuDung"].hide();
    },
    handleShowModal: function() {
      this.$refs["popupChonDonViSuDung"].show();
    },
    btnChonDonVi: async function() {
      this.$refs["popupChonDonViSuDung"].show();
      this.paramSearchDonViSD.thamSo1 = this.ipAddress.phanVungNguoiSDID;
      this.paramSearchDonViSD.thamSo2 = this.ipAddress.donViSuDungName;
      this.donViSDList = await this.apiSearchDonVi(this.paramSearchDonViSD);

      this.tinhThanhList = await this.apiListTinhThanh();
    },
    btnSearchDonVi: async function() {
      this.donViSDList = await this.apiSearchDonVi(this.paramSearchDonViSD);

    },
    btnXacNhan: async function() {
      if (this.ipAddress.donViSuDungId > 0
        && this.ipAddress.nguoiSuDungId > 0) {
        this.hideModal();
      }
    }
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
</style>
