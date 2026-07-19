<template src="./index.html"></template>

<script>
import KhaiBaoCongPower from "../Modal/khaibao_cong_power";
import KhaiBaoCongInterface from "../Modal/khaibao_cong_interface";
import DanhSachPortTheoLineCard from "../Modal/danhsach_port_theolinecard_thietbi";

export default {
  name: 'TabConsole',
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
      listOfControlCards: [],
      listOfConsolePortTypes: [],
      listOfConsolePorts: [],
      sumPorts: 0,
      congParam: {
        id: 0,
        phanloai: 3,
        chungloaiThietBiId: this.thongTinCard.id
      },
    };
  },
  components: {
    DanhSachPortTheoLineCard,
    KhaiBaoCongPower,
    KhaiBaoCongInterface,
    ModalThemCard: () => import("../TabInterface/ModalThemCard"),
  },
  computed: {
    grdItems: function () {
      return this.$refs.grdItems;
    },
  },
  watch: {
    "thongTinCard.id": async function (newId) {
      if (newId) {
        // this.thongTinCong.chungloaiThietBiId = this.thongTinCard.id;
        this.congParam.chungloaiThietBiId = this.thongTinCard.id;
        this.loadDsCongConsole();
        this.loadDsControlCards();
        this.loadTongSoPort();
      } else {
        this.listOfConsolePortTypes = [];
        this.listOfConsolePorts = [];
        this.listOfControlCards = [];
        this.sumPorts = 0;
      }
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      if (this.thongTinCard.id) {
        // this.thongTinCong.chungloaiThietBiId = this.thongTinCard.id;
        this.congParam.chungloaiThietBiId = this.thongTinCard.id;
        this.loadDsCongConsole();
        this.loadDsControlCards();
        this.loadTongSoPort();
      } else {
        this.listOfConsolePortTypes = [];
        this.listOfConsolePorts = [];
        this.listOfControlCards = [];
        this.sumPorts = 0;
      }
    } finally {
      this.$root.showLoading(false);
    }
  },
  methods: {

    // Load DS khai bao cong & DS cong
    loadDsCongConsole: async function () {
      const result = await this.getDanhSachCongConsole();
      // DS khai bao
      this.listOfConsolePortTypes = [];
      // DS cong
      this.listOfConsolePorts = [];
      if (result && Array.isArray(result) && result.length > 0) {
        for (let i = 0; i < result.length; i++) {
          this.listOfConsolePortTypes.push(result[i]);
          if (result[i].DS_CONG && Array.isArray(result[i].DS_CONG) && result[i].DS_CONG.length > 0) {
            for (let j = 0; j < result[i].DS_CONG.length; j++) {
              const port = result[i].DS_CONG[j];
              this.listOfConsolePorts.push(port);
            }
          }
        }
      }
    },
    getDanhSachCongConsole: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/chungloai-thietbi/danhsach-loaicong", {
          id: this.congParam.id,
          phanloai: this.congParam.phanloai,
          chungloaiThietBiId: this.congParam.chungloaiThietBiId
        });
        result = rs.data.result;
      } catch (e) {
        console.warn(e.response.data.message_detail);
        throw e;
      }
      return result;
    },

    // Load DS Control Cards
    loadDsControlCards: async function () {
      this.listOfControlCards = await this.getDsControlCards();
    },
    getDsControlCards: async function () {
      try {
        let rs = await this.$root.context.get(`/web-ecms/chungloai-thietbi/ds-card` +
          `?chungLoaiThietBiId=${this.thongTinCard.id}&phanLoai=3`);
        if (rs) {
          return rs.data;
        }
      } catch (err) {
        console.warn(err.response.data.message_detail);
        throw err;
      }
    },

    // Tinh tong so port
    loadTongSoPort: async function () {
      this.sumPorts = await this.getTongSoPort();
    },
    getTongSoPort: async function () {
      try {
        let rs = await this.$root.context.get(`/web-ecms/chungloai-thietbi/sum-ports` +
          `?chungLoaiThietBiId=${this.thongTinCard.id}&phanLoai=3`);
        if (rs && rs.data && Array.isArray(rs.data) && rs.data.length > 0) {
          return rs.data[0].item ?? 0;
        }
      } catch (err) {
        console.warn(err.response.data.message_detail);
        throw err;
      }
    },

    openModalThemCard: function () {
      if (this.currentMode != 'VIEW') {
        this.$root.toastError('Ghi lại thông tin chung của chủng thiết bị trước khi thêm Card.');
        return;
      }
      this.$refs.dialogInterfaceThemCard.openModalThemCard(3);
    },
    xemDanhSach() {
      this.$refs.dialogDanhSachPortTheoLineCard.dialogOpen(3);
    },
    updateCong(data) {
      this.$refs.dialogKhaiBaoCongInterface.dialogUpdateOpen(data, 3);
    },
    khaiBaoCong() {
      if (this.currentMode != 'VIEW') {
        this.$root.toastError('Ghi lại thông tin chung của chủng thiết bị trước khi khai báo cổng.');
        return;
      }
      this.$refs.dialogKhaiBaoCongInterface.dialogOpen(3);
    },

    // Sua, xoa Cong
    grid_onCommandClicked(name, dataItem) {
      if (name === 'SUA') {
        // this.setThongTinCongItem(dataItem);
        this.updateCong(dataItem);
      }
      if (name === 'XOA') {
        this.onDeleteCongInterface(dataItem);
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
            await this.loadDsCongConsole();
            this.$root.toastSuccess("Xóa cổng thành công");
          } else {
            this.$root.toastError("Xóa cổng thất bại");
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
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

    // Comlumn templates
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
    viewDetailsCard: function (value) {
      console.log(value);
    },
    getColumnDetailsTemplate: function (parent) {
      return function () {
        return {
          template: {
            template: `<button class="btn btn-second" style="width: 100px" @click="detailsClick">
              Click
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
