<template src="./index.html"></template>


<script>
import DanhSachPortTheoLineCard from "../Modal/danhsach_port_theolinecard_thietbi";
import KhaiBaoCongInterface from "../Modal/khaibao_cong_interface";
export default {
  name: 'TabInterface',
  props: {
    thongTinCard: {
      type: Object,
    },
    listOfManufacturers: {
      type: Array,
      default: () => []
    },
    modelParent: {
      type: String,
      default: () => ''
    },
    chungLoaiThietBiId: {
      type: Number,
      default: 0
    },
    currentMode: {
      type: String,
      default: ''
    },
  },
  data: function () {
    return {
      listOfCards: [],
      currentItem: {
        tongSoPortTb: '',
        soInterface: '',
        batDauTu: '',
        dinhDangTen: '',

      },
      thongTinCong: {
        id: 0,
        phanloai: 1,
        chungloaiThietBiId: this.thongTinCard.id,
        loaicongId: 0,
        soCong: null,
        batdauTu: null,
        dinhdangTen: null,
        mucdichsudungCongId: null,
        bangthongcongId: null,
        thongtinModule: null,
        ketnoi: null,
      },
      congParam: {
        id: 0,
        phanloai: 1,
        chungloaiThietBiId: this.thongTinCard.id
      },
      controls: {
        txtSoLineCard: {
          enabled: true,
          invalid: false
        },
        txtTongSoPort: {
          enabled: true,
          invalid: false
        },
        txtSoInterface: {
          enabled: true,
          invalid: false
        },
        txtBatDauTu: {
          enabled: true,
          invalid: false
        },
        txtDinhDangTen: {
          enabled: true,
          invalid: false
        },
        txtGhiChu: {
          enabled: true,
        },
      },
      lichSuItemList: [],
      listOfInterfacePortTypes: [],
      listOfInterfacePorts: [],
      sumPorts: 0
    };
  },
  components: {
    DanhSachPortTheoLineCard,
    KhaiBaoCongInterface,
    ModalThemCard: () => import("./ModalThemCard"),
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems;
    },
    grdItems_haTangMang: function () {
      return this.$refs.grdItems_haTangMang;
    },
    grdItems_subnet: function () {
      return this.$refs.grdItems_subnet;
    },
    grdItems_history: function () {
      return this.$refs.grdItems_history;
    },
  },
  watch: {
    "thongTinCard.id": async function (newId) {
      if (newId) {
        // this.lichSuItemList = await this.getLichSuThayDoiCLTB(newId);
        this.thongTinCong.chungloaiThietBiId = this.thongTinCard.id;
        this.congParam.chungloaiThietBiId = this.thongTinCard.id;
        this.loadDsCongInterface();
        this.loadDsCards();
        this.loadTongSoPort();
      } else {
        this.listOfInterfacePortTypes = [];
        this.listOfInterfacePorts = [];
        this.listOfCards = [];
        this.sumPorts = 0;
      }
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      if (this.thongTinCard.id) {
        // this.lichSuItemList = await this.getLichSuThayDoiCLTB(newId);
        this.thongTinCong.chungloaiThietBiId = this.thongTinCard.id;
        this.congParam.chungloaiThietBiId = this.thongTinCard.id;
        this.loadDsCongInterface();
        this.loadDsCards();
        this.loadTongSoPort();
      } else {
        this.listOfInterfacePortTypes = [];
        this.listOfInterfacePorts = [];
        this.listOfCards = [];
        this.sumPorts = 0;
      }
      if (this.thongTinCard.soLineCard != 0) {
      } else {
      }
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {
    openModalThemCard: function () {
      if (this.currentMode != 'VIEW') {
        this.$root.toastError('Ghi lại thông tin chung của chủng thiết bị trước khi thêm Card.');
        return;
      }
      this.$refs.dialogInterfaceThemCard.openModalThemCard(1);
    },
    getStatusText: function (value) {
      return value == 1 ? "Đang sử dụng" : "Chưa sử dụng";
    },
    getColumnStatusTemplate: function (parent) {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return parent.getStatusText(this.data.trangthaisd);
              },
            },
          },
        };
      };
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
    getFullDepthText: function (value) {
      return value == 1 ? "Có" : "Không";
    },
    getColumnFullDepthTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getFullDepthText(this.data.FULLDEPTH);
              },
            },
          },
        };
      };
    },
    getManuFacturerText: function (value) {
      let result = this.listOfManufacturers.find((item) => item.ID == value);
      return result ? result.TEN : value;
    },
    getColumnManuFacturerTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getManuFacturerText(this.data.HANGSANXUAT_ID);
              },
            },
          },
        };
      };
    },
    viewDetailsCard: function (value) {
      this.$refs.dialogDanhSachPortTheoLineCard.dialogOpen(1, value.ID);
    },
    getColumnDetailsTemplate: function (parent) {
      return function () {
        return {
          template: {
            template: `<button class="btn btn-second" style="width: 120px" @click="detailsClick">
              Danh sách Port
            </button>`,
            methods: {
              detailsClick: function () {
                parent.viewDetailsCard(this.data);
              },
            },
          },
        };
      };
    },

    // Column template of Interface Port Types table.
    getLoaiCongText: function (value) {
      let result = null;
      if (this.$refs.dialogKhaiBaoCongInterface && this.$refs.dialogKhaiBaoCongInterface.loaiCongListItems) {
        result = this.$refs.dialogKhaiBaoCongInterface.loaiCongListItems.find((item) => item.ID == value);
      }
      return result ? result.TEN : value;
    },
    getColumnLoaiCongTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getLoaiCongText(this.data.loaicong_id);
              },
            },
          },
        };
      };
    },
    getMucDichSuDungCongText: function (value) {
      let result = null;
      if (this.$refs.dialogKhaiBaoCongInterface && this.$refs.dialogKhaiBaoCongInterface.mucDichSdCongListItems) {
        result = this.$refs.dialogKhaiBaoCongInterface.mucDichSdCongListItems.find((item) => item.ID == value);
      }
      return result ? result.TEN : value;
    },
    getColumnMucDichSuDungCongTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getMucDichSuDungCongText(this.data.mucdichsudung_cong_id);
              },
            },
          },
        };
      };
    },
    getBangThongText: function (value) {
      let result = null;
      if (this.$refs.dialogKhaiBaoCongInterface && this.$refs.dialogKhaiBaoCongInterface.bangThongListItems) {
        result = this.$refs.dialogKhaiBaoCongInterface.bangThongListItems.find((item) => item.ID == value);
      }
      return result ? result.TEN : value;
    },
    getColumnBangThongCongTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getBangThongText(this.data.bangthongcong_id);
              },
            },
          },
        };
      };
    },
    getThongTinModuleText: function (value) {
      let result = null;
      if (this.$refs.dialogKhaiBaoCongInterface && this.$refs.dialogKhaiBaoCongInterface.thongTinModuleListItems) {
        result = this.$refs.dialogKhaiBaoCongInterface.thongTinModuleListItems.find((item) => item.ID == value);
      }
      return result ? result.TEN : value;
    },
    getColumnThongTinModuleTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getThongTinModuleText(this.data.thongtinmodule_id);
              },
            },
          },
        };
      };
    },
    getKetNoiText: function (value) {
      let result = null;
      if (this.$refs.dialogKhaiBaoCongInterface && this.$refs.dialogKhaiBaoCongInterface.ketNoiListItems) {
        result = this.$refs.dialogKhaiBaoCongInterface.ketNoiListItems.find((item) => item.ID == value);
      }
      return result ? result.TEN : value;
    },
    getColumnKetNoiTemplate: function () {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return this.$parent.$parent.$parent.getKetNoiText(this.data.ketnoi);
              },
            },
          },
        };
      };
    },

    xemDanhSach() {
      // Handle the event when the button is clicked
      this.$refs.dialogDanhSachPortTheoLineCard.dialogOpen(1);
    },
    updateCong(data) {
      // Handle the event when the button is clicked
      this.$refs.dialogKhaiBaoCongInterface.dialogUpdateOpen(data, 1);
    },
    khaiBaoCong() {
      // Handle the event when the button is clicked
      // this.setThongTinCongNewItem();
      if (this.currentMode != 'VIEW') {
        this.$root.toastError('Ghi lại thông tin chung của chủng thiết bị trước khi khai báo cổng.');
        return;
      }
      this.$refs.dialogKhaiBaoCongInterface.dialogOpen(1);
    },
    deleteCongInterface: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/chungloai-thietbi/xoa-cong", { id: id });
        result = rs.data.result;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    getLichSuThayDoiCLTB: async function (id) {
      let result = false;
      try {
        let rs = await this.$root.context.post("/web-ecms/chungloai-thietbi/lay-lichsu-thaydoi-chungloai-thietbi", { id: id });
        result = rs.data;
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail);
          }
        }
      }
      return result;
    },
    enableControls: function (args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args;
        }
      }

      this.buttons.btnSave.enabled = args;
      this.buttons.btnAddNew.enabled = !args;
      this.buttons.btnDelete.enabled = !args;

      if (this.currentItem.id > 0)
        this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled;
    },
    setCurrentItem: async function (item) {
      this.currentItem.id = item.ID;
      this.currentItem.ten = item.TEN;
      this.currentItem.idcId = item.IDC_ID;
      this.currentItem.tenIDC = item.TEN_IDC;
      this.currentItem.thietBiId = item.THIETBI_ID;
      this.currentItem.tenThietBi = item.TEN_THIETBI;
      this.currentItem.vrfId = item.VRF_ID;
      this.currentItem.tenVRF = item.TEN_VRF;
      this.currentItem.zoneId = item.ZONE_ID;
      this.currentItem.tenZone = item.TEN_ZONE;
      this.currentItem.ghiChu = item.GHI_CHU;
      this.currentItem.hieuLuc = item.HIEU_LUC;
    },
    setThongTinCongItem: async function (item) {
      this.thongTinCong.id = item.ID;
      this.thongTinCong.loaicongId = item.LOAICONG_ID;
      this.thongTinCong.soCong = item.SO_CONG;
      this.thongTinCong.batdauTu = item.BAT_DAU;
      this.thongTinCong.dinhdangTen = item.DINHDANG_TEN;
      this.thongTinCong.mucdichsudungCongId = item.MUCDICHSUDUNG_CONG_ID;
      this.thongTinCong.bangthongcongId = item.BANGTHONGCONG_ID;
      this.thongTinCong.thongtinModule = item.THONGTIN_MODULE;
      this.thongTinCong.ketnoi = item.KETNOI;
    },
    setThongTinCongNewItem: async function () {
      this.thongTinCong.id = 0;
      this.thongTinCong.loaicongId = 0;
      this.thongTinCong.soCong = null;
      this.thongTinCong.batdauTu = null;
      this.thongTinCong.dinhdangTen = null;
      this.thongTinCong.mucdichsudungCongId = null;
      this.thongTinCong.bangthongcongId = null;
      this.thongTinCong.thongtinModule = null;
      this.thongTinCong.ketnoi = null;
    },
    setCurrentItemDefault: function () {
      this.setCurrentItem({
        ID: 0,
        TEN: '',
        IDC_ID: 0,
        TEN_IDC: '',
        THIETBI_ID: 0,
        TEN_THIETBI: '',
        VRF_ID: 0,
        TEN_VRF: '',
        ZONE_ID: 0,
        TEN_ZONE: '',
        GHI_CHU: '',
        HIEU_LUC: 1,
      });
    },
    validateControls: function () {
      let msg = "";
      if (this.controls.txtModelThietBi.invalid) {
        msg = "Tên không được để trống.";
      }
      if (this.controls.selectNhaSanXuat.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "IDC không được để trống.";
      }
      if (this.controls.selectChungLoaiTb.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "Zone không được để trống.";
      }
      if (this.controls.selectVRF.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "VRF không được để trống.";
      }
      if (this.controls.txtChieuCao.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "Thiết bị không được để trống.";
      }
      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },
    grid_onCommandClicked(name, dataItem) {
      if (name === 'SUA') {
        this.setThongTinCongItem(dataItem);
        this.updateCong(dataItem);
      }
      if (name === 'XOA') {
        this.onDeleteCongInterface(dataItem);
      }
    },
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

    onDeleteCongInterface: async function (args) {
      console.log('param', args);
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
          this.$root.showLoading(true);
          let rs = await this.deleteCongInterface(args.ID);
          if (rs) {
            // this.setCurrentItemDefault();
            await this.loadDsCongInterface();
            this.$root.toastSuccess("Xóa cổng thành công");
          } else {
            this.$root.toastError("Xóa cổng thất bại");
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
    },
    loadDsCongInterface: async function () {
      const result = await this.getDanhSachCongInterface();
      // DS khai bao
      this.listOfInterfacePortTypes = [];
      // DS cong
      this.listOfInterfacePorts = [];
      if (result && Array.isArray(result) && result.length > 0) {
        for (let i = 0; i < result.length; i++) {
          this.listOfInterfacePortTypes.push(result[i]);
          if (result[i].DS_CONG && Array.isArray(result[i].DS_CONG) && result[i].DS_CONG.length > 0) {
            for (let j = 0; j < result[i].DS_CONG.length; j++) {
              const port = result[i].DS_CONG[j];
              this.listOfInterfacePorts.push(port);
            }
          }
        }
      }
    },
    getDanhSachCongInterface: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/chungloai-thietbi/danhsach-loaicong", {
          id: this.congParam.id,
          phanloai: this.congParam.phanloai,
          chungloaiThietBiId: this.congParam.chungloaiThietBiId
        });
        result = rs.data.result;
      } catch (e) {
        // if (!(e.response == null || e.response == undefined)) {
        //   if (!(e.response.data == null || e.response.data == undefined)) {
        //     this.$root.toastError(e.response.data.message_detail);
        //   }
        // }
        console.warn(e.response.data.message_detail);
        throw e;
      }
      return result;
    },
    loadDsCards: async function () {
      this.listOfCards = await this.getDsCards();
    },
    getDsCards: async function () {
      try {
        let rs = await this.$root.context.get(`/web-ecms/chungloai-thietbi/ds-card` +
          `?chungLoaiThietBiId=${this.thongTinCard.id}&phanLoai=1`);
        if (rs) {
          return rs.data;
        }
      } catch (err) {
        console.warn(err.response.data.message_detail);
        throw err;
      }
    },
    loadTongSoPort: async function () {
      this.sumPorts = await this.getTongSoPort();
    },
    getTongSoPort: async function () {
      try {
        let rs = await this.$root.context.get(`/web-ecms/chungloai-thietbi/sum-ports` +
          `?chungLoaiThietBiId=${this.thongTinCard.id}&phanLoai=1`);
        if (rs && rs.data && Array.isArray(rs.data) && rs.data.length > 0) {
          return rs.data[0].item ?? 0;
        }
      } catch (err) {
        console.warn(err.response.data.message_detail);
        throw err;
      }
    }
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
