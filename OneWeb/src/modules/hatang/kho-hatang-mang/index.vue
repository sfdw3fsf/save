<template src="./index.html"></template>

<script>
import { mapActions, mapState, mapGetters } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import ChonDonViNhanVien from "../ChonDonViNhanVien/index";
import DialogThietBi from '../ThietBiHinhThanhKhoHaTang/index.vue'

export default {
  components: {
    DialogThietBi
  },
  props: {
    modalItemID: 0,
  },
  data: function () {
    return {
      thietbi_daunoi_remove: [],
      thietbi_daunoi_items: [],
      dataSource: [],
      dsSelected: [],
      dsIDC: [],
      dsCongNghe: [],
      dsHaTang: [],
      colsHaTang: [
        { fieldName: "SOTHUTU", headerText: "STT", allowFiltering: true, width: 50 },
        { fieldName: "TEN", headerText: "Tên hạ tầng", allowFiltering: true },
        { fieldName: "VRAM", headerText: "vRAM", allowFiltering: true },
        { fieldName: "VCPU", headerText: "vCPU", allowFiltering: true },
        { fieldName: "VSTORAGE", headerText: "vStorage", allowFiltering: true },
        { fieldName: "TEN_IDC", headerText: "IDC", allowFiltering: true },
        { fieldName: "NGUOI_QTRI", headerText: "Người quản trị", allowFiltering: true },
        { fieldName: "HIEU_LUC", headerText: "Hiệu lực", allowFiltering: true },
      ],
      dsSmartCloud: [],
      colsSmartCloud: [
        { fieldName: "SOTHUTU", headerText: "STT", allowFiltering: true, width: 50 },
        { fieldName: "TEN", headerText: "Tên hạ tầng", allowFiltering: true },
        { fieldName: "VRAM", headerText: "vRAM", allowFiltering: true },
        { fieldName: "VCPU", headerText: "vCPU", allowFiltering: true },
        { fieldName: "VSTORAGE", headerText: "vStorage", allowFiltering: true },
        { fieldName: "TEN_IDC", headerText: "IDC", allowFiltering: true },
        { fieldName: "NGUOI_QTRI", headerText: "Người quản trị", allowFiltering: true },
        { fieldName: "HIEU_LUC", headerText: "Hiệu lực", allowFiltering: true },
      ],
      dsIDG: [],
      colsIDG: [
        { fieldName: "SOTHUTU", headerText: "STT", allowFiltering: true, width: 50 },
        { fieldName: "TEN", headerText: "Tên hạ tầng", allowFiltering: true },
        { fieldName: "LOAI_HATANG", headerText: "Loại hạ tầng", allowFiltering: true },
        { fieldName: "HATANG_AOHOA", headerText: "Hạ tầng ảo hóa", allowFiltering: true },
        { fieldName: "TEN_IDC", headerText: "IDC", allowFiltering: true },
        { fieldName: "NGUOI_QTRI", headerText: "Người quản trị", allowFiltering: true },
        { fieldName: "HIEU_LUC", headerText: "Hiệu lực", allowFiltering: true },
      ],
      dsSubnet: [],
      colsSubnet: [
        { fieldName: "SOTHUTU", headerText: "STT", allowFiltering: true, width: 50 },
        { fieldName: "SUBNET", headerText: "Subnet", allowFiltering: true },
        { fieldName: "SUBNETMASK", headerText: "Subnet mask", allowFiltering: true },
        { fieldName: "GATEWAY", headerText: "Gateway", allowFiltering: true },
        { fieldName: "TEN_VLAN", headerText: "VLAN", allowFiltering: true },
        { fieldName: "HIEU_LUC", headerText: "Hiệu lực", allowFiltering: true },
      ],
      dsIPAddress: [],
      colsIPAddress: [
        { fieldName: "SOTHUTU", headerText: "STT", allowFiltering: true, width: 50 },
        { fieldName: "TEN", headerText: "Tên", allowFiltering: true },
        { fieldName: "IP_PHANLOAI", headerText: "Phân loại", allowFiltering: true },
        { fieldName: "IP_STORE", headerText: "IP Store", allowFiltering: true },
        { fieldName: "SUBNET_QH", headerText: "Mục đích cấp", allowFiltering: true },
        { fieldName: "DONVI_CAP", headerText: "Đơn vị cấp", allowFiltering: true },
        { fieldName: "HIEU_LUC", headerText: "Hiệu lực", allowFiltering: true },
      ],
      dsThietBi: [],
      colsThietBi: [
        { fieldName: "SOTHUTU", headerText: "STT", allowFiltering: true, width: 50 },
        { fieldName: "MATHIETBI", headerText: "Mã thiết bị", allowFiltering: true },
        { fieldName: "TENTHIETBI", headerText: "Tên thiết bị", allowFiltering: true },
        { fieldName: "IP_ADDRESS", headerText: "IP Quản trị", allowFiltering: true },
        { fieldName: "VAI_TRO", headerText: "Vai trò thiết bị", allowFiltering: true },
        { fieldName: "NGAY_BS", headerText: "Ngày bổ sung", allowFiltering: true },
        { fieldName: "NGUOI_BS", headerText: "Người bổ sung", allowFiltering: true },
      ],
      dsHistory: [],
      colsHistory: [
        { fieldName: "SOTHUTU", headerText: "STT", allowFiltering: false, width: 50 },
        { fieldName: "TACDONG", headerText: "Tác động", allowFiltering: true },
        {
          fieldName: "FIELD",
          headerText: "Trường thông tin thay đổi",
          allowFiltering: true,
        },
        { fieldName: "OLD_VALUE", headerText: "Giá trị cũ", allowFiltering: true },
        { fieldName: "NEW_VALUE", headerText: "Giá trị mới", allowFiltering: true },
        {
          fieldName: "NGUOI_THUCHIEN",
          headerText: "Người thực hiện",
          allowFiltering: true,
        },
        { fieldName: "THOI_DIEM", headerText: "Thời điểm", allowFiltering: true },
      ],
      userInfo: {
        phanVungId: 0,
        nhanVienId: 0,
        nhanVienTen: "",
        donViId: 0,
        donViTen: "",
        donViChaId: 0,
        donViChaTen: "",
      },
      imageItem: "",
      currentTab: 1,
      currentItem: {
        id: 0,
        ten: null,
        maHaTang: null,
        IDCId: 0,
        congNgheId: 0,
        avatar: null,
        ghiChu: null,
        hieuLuc: 1,
        donViQuanLyId: 0,
        donViQuanLy: null,
        phongBanQuanLyId: 0,
        phongBanQuanLy: null,
        nguoiQuanLyId: 0,
        nguoiQuanLy: null,
      },
      controls: {
        txtTen: {
          enabled: false,
          invalid: false,
        },
        cboIDC: {
          enabled: false,
          invalid: false,
        },
        txtMaHaTang: {
          enabled: false,
          invalid: false,
        },
        cboCongNghe: {
          enabled: false,
          invalid: false,
        },
        txtGhiChu: {
          enabled: false,
        },
        chkHieuLuc: {
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
    modalThietBiRef: function () {
      return this.$refs.grdItems_thietbi
    }
  },
  watch: {
    "currentItem.ten": function (value) {
      this.controls.txtTen.invalid = value.trim() === "";
    },
    "currentItem.maHaTang": function (value) {
      this.controls.maHaTang.invalid = value.trim() === "";
    },
    "currentItem.id": function (value) {
      this.buttons.btnEdit.enabled = value > 0;
      this.buttons.btnDelete.enabled = value > 0;
    },
    "currentItem.idcId": function (value) {
      this.controls.cboIDC.invalid = value == 0;
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.dsIDC = await this.apiIDC();
      this.dsCongNghe = await this.apiCongNgheSuDung();
      this.dataSource = await this.apiListDanhSach();
      // init user Info
      //this.userInfo.phanVungId = parseInt(this.$auth.getPhanVungID());
      this.userInfo.nhanVienId = await this.$root.token.getNhanVienID();
      this.userInfo.nhanVienTen = this.$root.context.user.getProperty("ten_nv");
      this.userInfo.donViId = this.$root.context.user.getProperty("donvi_id");
      this.userInfo.donViTen = this.$root.context.user.getProperty("ten_dv");
      this.userInfo.donViChaId = this.userInfo.donViId;
      this.userInfo.donViChaTen = this.userInfo.donViTen;
      let ds = await this.apiGetDonViCha(this.userInfo.donViId);
      if (ds != null && ds.length > 0) {
        this.userInfo.donViChaId = ds[0].DONVI_ID;
        this.userInfo.donViChaTen = ds[0].TEN_DV;
      }
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    xacNhanThemThietBi: function (data) {
      data.data.forEach((item) => {
        const existingItem = this.thietbi_daunoi_items.find(existing => existing.ID === item.THIETBI_ID);

        if (!existingItem) {
          this.thietbi_daunoi_items.push({
            ID: item.THIETBI_ID,
            SOTHUTU: this.thietbi_daunoi_items.length + 1,
            TENTHIETBI: item.TEN,
            MATHIETBI: item.MATHIETBI,
            IP_ADDRESS: item.DS_IP,
            VAI_TRO: item.LOAIVAITRO_TEN,
            NGAY_BS: item.F_NGAY_CN,
            NGUOI_BS: this.$root.context.user.getTenNhanVien()
          });
        }
      });

      this.thietbi_daunoi_items.forEach((item, index) => {
        item.SOTHUTU = index + 1;
      });

      console.log("thietbi_daunoi_items", this.thietbi_daunoi_items);
      this.modalThietBiRef.hideModal();
    },
    grid_onCommandClicked_thietbi(name, dataItem) {
      if (this.buttons.btnAddNew.enabled) return
      if (name == 'XOA') {
        this.thietbi_daunoi_remove.push(dataItem.ID_THIETBI_DN);
        this.thietbi_daunoi_items = this.thietbi_daunoi_items.filter((item) => item.ID_THIETBI_DN
          != dataItem.ID_THIETBI_DN
        )
      }
    },
    openThietBiModal() {
      if (this.currentItem.IDCId) {
        this.modalThietBiRef.dialogOpen(
          'HATANGMANG_THIETBI',
          this.thietbi_daunoi_items.map((item) => {
            return item.thietbi_id
          })
        )
      } else {
        this.$toast.error('Thuộc IDC không được để trống!')
      }
    },
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

    apiIDC: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-idc");
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },
    apiCongNgheSuDung: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-cong-nghe");
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },

    apiHaTangMang: async function (pId) {
      let result = [];
      if (pId == null || pId < 1) {
        return result;
      }
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-mang/ao-hoa", {
          haTangMangId: pId,
        });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },

    apiSmartCloud: async function (pId) {
      let result = [];
      if (pId == null || pId < 1) {
        return result;
      }
      try {
        let rs = await this.$root.context.get(
          "/web-ecms/danhmuc/ha-tang-mang/smartcloud",
          { haTangMangId: pId }
        );
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },
    apiIDG: async function (pId) {
      let result = [];
      if (pId == null || pId < 1) {
        return result;
      }
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-mang/idg", {
          haTangMangId: pId,
        });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },
    apiSubnet: async function (pId) {
      let result = [];
      if (pId == null || pId < 1) {
        return result;
      }
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-mang/subnet", {
          haTangMangId: pId,
        });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },
    apiIPAddress: async function (pId) {
      let result = [];
      if (pId == null || pId < 1) {
        return result;
      }
      try {
        let rs = await this.$root.context.get(
          "/web-ecms/danhmuc/ha-tang-mang/ip-address",
          { haTangMangId: pId }
        );
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },
    apiThietBi: async function (pId) {
      let result = [];
      if (pId == null || pId < 1) {
        return result;
      }
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-mang/thiet-bi", {
          haTangMangId: pId,
        });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },

    apiHistory: async function (pId) {
      let result = [];
      if (pId == null || pId < 1) {
        return;
      }
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ha-tang-mang/history", {
          haTangMangId: pId,
        });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },

    apiGetDonViCha: async function (pId) {
      let result = [];
      if (pId == null || pId < 1) {
        return;
      }
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/don-vi-cha", {
          donViId: pId,
        });
        result = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },
    apiListDanhSach: async function () {
      let result = [];
      try {
        if (this.modalItemID > 0) {
          let rs = await this.$root.context.post(
            "/web-ecms/danhmuc/ha-tang-mang/get-hatangmang",
            { id: this.modalItemID }
          );
          if (rs.data != null) {
            result.push(rs.data);
          }
        } else {
          let rs = await this.$root.context.get(
            "/web-ecms/danhmuc/ha-tang-mang/danh-sach"
          );
          result = rs.data;
        }
      } catch (e) {
        this.$toast.error(e.response.data.message_detail);
      }
      return result;
    },
    apiSave: async function (para) {
      let result = false;
      try {
        let postData = {
          id: para.id,
          ten: para.ten.trim(),
          maHaTang: para.maHaTang.trim(),
          idcId: para.IDCId,
          congNgheId: para.congNgheId,
          avatar: para.avatar,
          ghiChu: para.ghiChu,
          hieuLuc: para.hieuLuc,
          nguoiQlyId: para.nguoiQuanLyId,
          phongBanQlyId: para.phongBanQuanLyId,
          donViQlyId: para.donViQuanlyId,
        };

        let rs = await this.$root.context.post(
          "/web-ecms/danhmuc/ha-tang-mang/cap-nhat",
          postData
        );
        result = rs.error_code == "BSS-00000000";
        if (result) {
          let dataThietBi = {
            thietBiHinhThanhList: this.thietbi_daunoi_items.map((item) => (
              {
                id: this.currentItem.id,
                thietbi_id: item.ID,
                ngay_cn: item.NGAY_BS
              }
            ))
          }
          if (dataThietBi.thietBiHinhThanhList && dataThietBi.thietBiHinhThanhList.length > 0) {
            const rsTB = await this.$root.context.post(
              `/web-ecms/hatang/thietbi-khohatang-mang/add-thiet-bi`,
              dataThietBi
            )
          }
          if (this.thietbi_daunoi_remove && this.thietbi_daunoi_remove.length > 0) {
            this.thietbi_daunoi_remove.forEach(async (item) => {
              await this.$root.context.post(
                `/web-ecms/hatang/thietbi-khohatang-mang/delete-thiet-bi`,
                { p_id: item }
              )
            })
          }
        }
      } catch (e) {
        let msgErr = "Lỗi : \n";
        if (e.response.data.message_detail == null) {
          if (e.response.data.data == null) {
            msgErr = e.response.data.message;
          } else {
            let jsonObj = e.response.data.data;
            for (var key in jsonObj) {
              msgErr += " - " + jsonObj[key] + "\n";
            }
          }
        } else {
          msgErr = e.response.data.message_detail;
        }
        this.$root.toastError(msgErr);
        result = false;
      }
      return result;
    },
    apiDelete: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/danhmuc/ha-tang-mang/xoa", {
          id: id,
        });
        result = rs.error_code == "BSS-00000000";
      } catch (e) {
        this.$root.toastError(e.response.data.message_detail);
        result = false;
      }
      return result;
    },
    enableControls: function (args) {
      this.controls.txtTen.enabled = args;
      this.controls.txtMaHaTang.enabled = args;
      this.controls.cboIDC.enabled = args;
      this.controls.cboCongNghe.enabled = args;
      this.controls.txtGhiChu.enabled = args;
      this.controls.chkHieuLuc.enabled = args;

      this.buttons.btnSave.enabled = args;
      this.buttons.btnAddNew.enabled = !args;
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled;
      this.buttons.btnDelete.enabled = !args;

      if (this.currentItem.id > 0)
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
    },

    onShowDataZone: async function (pId) {
      if (pId > 0) {
        try {
          this.$root.showLoading(true);
          this.dsHaTang = await this.apiHaTangMang(pId);
          this.dsSmartCloud = await this.apiSmartCloud(pId);
          this.dsIDG = await this.apiIDG(pId);
          this.dsSubnet = await this.apiSubnet(pId);
          this.dsIPAddress = await this.apiIPAddress(pId);
          this.dsThietBi = await this.apiThietBi(pId);
          this.dsHistory = await this.apiHistory(pId);
        } finally {
          this.$root.showLoading(false);
        }
      }
    },

    chooseFiles(event) {
      event.preventDefault();
      // document.getElementById("fileUpload").click();
      this.$refs.fileUpload.click();
    },
    onFileChanged: function (event) {
      const file = event.target.files[0];
      console.log("file :", file);
      if (file) {
        const reader = new FileReader();

        // Khi FileReader hoàn thành việc đọc file
        reader.onload = () => {
          const fullBase64 = reader.result;
          this.currentItem.avatar = fullBase64.split(",")[1]; // Chuỗi Base64 (bao gồm 'data:image/...;base64,')
          this.imageItem = fullBase64;
          //this.buttons.btnAddImage = 'hidden';
          this.buttons.imgAvatar = "img-fluid";
        };

        reader.onerror = (error) => {
          console.error("Lỗi khi đọc file:", error);
        };

        reader.readAsDataURL(file);
      }
    },
    apiListThietBiDauNoi: async function (id) {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thietbi-khohatang-mang/thiet-bi', {
          p_hatang_id: id
        })
        rs.data.forEach((item, index) => {
          result.push({
            ID_THIETBI_DN: item.ID,
            ID: item.THIETBI_ID,
            SOTHUTU: index + 1,
            TENTHIETBI: item.TEN,
            MATHIETBI: item.MATHIETBI,
            IP_ADDRESS: item.DS_IP,
            VAI_TRO: item.LOAIVAITRO_TEN,
            NGAY_BS: item.NGAY_CN,
            NGUOI_BS: item.NHANVIEN_NAME
          })
        })
      } catch (e) {
        console.error("Lỗi khi lấy danh sách thiết bị đầu nối:", e);
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    setCurrentItem: async function (item) {
      this.currentItem.id = item.ID;
      this.currentItem.ten = item.TEN;
      this.currentItem.maHaTang = item.MA_HATANG;
      this.currentItem.IDCId = item.IDC_ID;
      this.currentItem.congNgheId = item.CONGNGHE_ID;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
      this.currentItem.donViQuanlyId = item.DONVIQLY_ID;
      this.currentItem.phongBanQuanLyId = item.PHONGBANQLY_ID;
      this.currentItem.nguoiQuanLyId = item.NGUOIQLY_ID;
      this.currentItem.avatar = item.AVATAR;
      this.currentItem.donViQuanly = item.DONVI_QLY;
      this.currentItem.phongBanQuanLy = item.PHONGBAN_QLY;
      this.currentItem.nguoiQuanLy = item.NGUOI_QLY;
      this.onShowDataZone(item.ID);
      if (this.currentItem.avatar != null && this.currentItem.avatar != "") {
        this.imageItem = "data:image/*;base64," + this.currentItem.avatar;
        //this.buttons.btnAddImage = 'hidden';
        this.buttons.imgAvatar = "img-fluid";
      } else {
        //this.buttons.btnAddImage = '';
        this.buttons.imgAvatar = "hidden";
      }
      this.thietbi_daunoi_items = await this.apiListThietBiDauNoi(item.ID);
    },
    setCurrentItemDefault: function () {
      this.currentItem.id = 0;
      this.currentItem.ten = null;
      this.currentItem.maHaTang = null;
      this.currentItem.IDCId = 0;
      this.currentItem.congNgheId = 0;
      this.currentItem.ghiChu = null;
      this.currentItem.hieuLuc = 1;
      this.currentItem.avatar = "";
      this.currentItem.nguoiQuanLyId = this.userInfo.nhanVienId;
      this.currentItem.nguoiQuanLy = this.userInfo.nhanVienTen;
      this.currentItem.phongBanQuanLyId = this.userInfo.donViId;
      this.currentItem.phongBanQuanLy = this.userInfo.donViTen;
      this.currentItem.donViQuanlyId = this.userInfo.donViChaId;
      this.currentItem.donViQuanly = this.userInfo.donViChaTen;
      this.imageItem = null;
      this.dsHaTang = [];
      this.dsSmartCloud = [];
      this.dsIDG = []
      this.dsSubnet = [];
      this.dsIPAddress = [];
      this.dsThietBi = [];
      this.dsHistory = [];
      this.thietbi_daunoi_items = [];
      this.thietbi_daunoi_remove = [];
    },

    validateControls: function () {
      let msg = "";
      if (this.currentItem.ten == null || this.currentItem.ten.trim() == "") {
        msg = "Tên hạ tầng không được để trống.";
      }
      if (this.currentItem.maHaTang == null || this.currentItem.maHaTang.trim() == "") {
        if (msg != '') msg += '\n\r'
        msg += "Mã hạ tầng không được để trống.";
      }
      if (this.currentItem.IDCId < 1) {
        if (msg != "") msg += "\n\r";
        msg += "Chưa chọn IDC.";
      }
      if (this.currentItem.congNgheId < 1) {
        if (msg != "") msg += "\n\r";
        msg += "Chưa chọn công nghệ sử dụng.";
      }

      // if (msg != "") {
      //   msg = "Bạn chưa chọn : " + msg.substring(2) + "\n";
      // }

      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },

    grdItems_RowSelected: async function (args) {
      if (this.buttons.btnSave.enabled) {
        return;
      }
      this.setCurrentItem(args.data);
    },
    grdItems_RowDeselected: async function (args) {
      if (this.buttons.btnSave.enabled) {
        return;
      }
      let items = this.grdItems.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0]);
      } else {
        this.setCurrentItemDefault();
      }
    },

    onAddNew: async function (args) {
      this.enableControls(true);
      this.setCurrentItemDefault();
    },
    onSave: async function (args) {
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
            this.$root.showLoading(true);
            let rs = await this.apiSave(this.currentItem);
            if (rs) {
              this.dataSource = await this.apiListDanhSach();
              this.$root.toastSuccess("Cập nhật thông tin thành công ");
            }
            this.enableControls(!rs);
          } finally {
            this.$root.showLoading(false);
          }
        }
      }
    },

    onCancel: async function (args) {
      let items = this.grdItems.getSelectedRecords();
      let item = null;
      if (!(items == null || items.length == 0)) {
        if (this.currentItem.id == 0) {
          item = items[0];
        } else {
          item = items.find((x) => x.ID == this.currentItem.id);
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
      let msg = `Bạn có muốn xóa ${items.length} mẫu tin đã chọn không?`;
      let flag = await this.$confirm(msg, "Xác nhận", {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
        type: "warning",
        dangerouslyUseHTMLString: true,
      })
        .then((s) => true)
        .catch((e) => {
          return false;
        });
      if (flag) {
        try {
          this.$root.showLoading(true);
          let isSuccess = false;
          for (const item of items) {
            isSuccess = await this.apiDelete(item.ID);
            if (!isSuccess) {
              break;
            }
          }
          if (isSuccess) {
            this.setCurrentItemDefault();
            this.dataSource = await this.apiListDanhSach();
            this.enableControls(false);
            this.$root.toastSuccess("Xóa thông tin thành công");
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
  },
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
</style>
