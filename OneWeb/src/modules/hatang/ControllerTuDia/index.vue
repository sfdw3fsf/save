<template src="./index.template.html"></template>

<script>
import InterfaceComponent from './TabInterface/index.vue'; // import component Interface
import PowerComponent from './TabPower/index.vue';
import ConsoleComponent from './TabConsole/index.vue';
import axios from "axios";
export default {
  components: {
    InterfaceComponent,
    PowerComponent,
    ConsoleComponent
  },
  // props: {
  //   bodieukhien_id: 25
  // },
  data: function () {
    return {
      dsIP: [],
      dsLichSuThayDoi: [],
      dsPortInterface: [],
      dsPortPower: [],
      dsPortConsole: [],
      dsPort: [],
      tabDsPortSelected: {
        Inteface: true,
        Power: false,
        Console: false
      },
      dataItems: [],
      dataDelete: [],
      dsTTSuDung: [],
      currentItem: {
        id: this.$route.params.id,
        matudia: "",
        chungloaithietbi_id: null,
        thietbi_id: null,
        mathietbi: "",
        sothutu: null,
        ten: "",
        kyhieu: "",
        serial_number: "",
        ghi_chu: "",
        hieu_luc: 1,
        trangthai_sd: 0,
        phanloai: null,
        so_linecard: null,
        so_interface: null,
        interface_start: null,
        interface_fname: "",
        so_port_interface: null,
        so_power: null,
        so_powerslot: null,
        power_start: null,
        power_fname: "",
        so_port_power: null,
        so_controlcard: null,
        so_console: null,
        console_start: null,
        console_fname: "",
        so_port_console: null,
        ip_mgmt: null,
        ip_controller: null
      },
      controls: {
        txtMaTuDia: {
          enabled: false,
          invalid: false,
        },
        txtMaThietBi: {
          enabled: false,
          invalid: false
        },
        txtTenThietBi: {
          enabled: false,
          invalid: false
        },
        txtKyHieuTB: {
          enabled: false,
          invalid: false
        },
        txtSTT: {
          enabled: false,
        },
        txtSerialNumber: {
          enabled: false,
        },
        checBoxHieuLuc: {
          enabled: false,
        },
        comboboxIpMgmt: {
          enabled: false,
        },
        comboboxIpController: {
          enabled: false,
        },
        comboboxTTSuDung: {
          enabled: false,
        },
        txtGhiChu: {
          enabled: false,
        },
      },
      buttons: {
        // btnNhapMoi: {
        //   enabled: true,
        // },
        btnGhiLai: {
          enabled: false,
        },
        btnHuyBo: {
          enabled: false,
        },
        btnSua: {
          enabled: true,
        },
        btnXoa: {
          enabled: false,
        },
      },
    };
  },
  computed: {
    gridItemsController: function () {
      return this.$refs.gridItemsController;
    },
  },
  watch: {
    "currentItem.ten": function (value) {
      if (this.buttons.btnGhiLai.enabled) {
        this.controls.txtTenThietBi.invalid = value == "";
      } else {
        this.controls.txtTenThietBi.invalid = false;
      }
    },
    "currentItem.kyhieu": function (value) {
      if (this.buttons.btnGhiLai.enabled) {
        this.controls.txtKyHieuTB.invalid = value == "";
      } else {
        this.controls.txtKyHieuTB.invalid = false;
      }
    },
  },
  created: async function () {
    try {
      this.$root.showLoading(true);
      this.dsIP = await this.getDsIp();
      this.dsTTSuDung = [{
        id: 0,
        ten: 'Chưa sử dụng'
      },
      {
        id: 1,
        ten: 'Đang sử dụng'
      },
      {
        id: -1,
        ten: 'Hỏng'
      }];
      //this.getdsPort();
      this.getThongTinController();
      this.getHistoryController();

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
    getDsIp: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.get("/web-ecms/danhmuc/ip-cungcapdv");
        result = rs.data;
        result.forEach(element => {
          element.id = element.ID;
          element.label = element.TEN;
        });
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result;
    },

    enableControls: function (args) {
      this.controls.txtMaTuDia.enabled = args;
      this.controls.txtMaThietBi.enabled = args;
      this.controls.txtTenThietBi.enabled = args;
      this.controls.txtKyHieuTB.enabled = args;
      this.controls.txtSTT.enabled = args;
      this.controls.txtSerialNumber.enabled = args;
      this.controls.checBoxHieuLuc.enabled = args;
      this.controls.comboboxIpMgmt.enabled = args;
      this.controls.comboboxIpController.enabled = args;
      this.controls.comboboxTTSuDung.enabled = args;
      this.controls.txtGhiChu.enabled = args;

      this.buttons.btnGhiLai.enabled = args;
      this.buttons.btnSua.enabled = !args;
      this.buttons.btnHuyBo.enabled = this.buttons.btnGhiLai.enabled;
      if (this.currentItem.id > 0)
        this.buttons.btnSua.enabled = !this.buttons.btnGhiLai.enabled;
    },

    getThongTinController: async function () {
      let result = [];
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/get-thongtin", { id: this.$route.params.id });
        result = rs.data;

        this.setCurrentItem(result[0]);
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    setCurrentItem: function (item) {
      this.currentItem.id = item.ID;
      this.currentItem.matudia = item.MATUDIA;
      this.currentItem.chungloaithietbi_id = item.CHUNGLOAITHIETBI_ID;
      this.currentItem.thietbi_id = item.THIETBI_ID;
      this.currentItem.mathietbi = item.MATHIETBI;
      this.currentItem.sothutu = item.SOTHUTU;
      this.currentItem.hieu_luc = item.HIEU_LUC;
      this.currentItem.ten = item.TEN;
      this.currentItem.kyhieu = item.KYHIEU;
      this.currentItem.serial_number = item.SERIAL_NUMBER;
      this.currentItem.ghi_chu = item.GHI_CHU;
      this.currentItem.trangthai_sd = item.TRANGTHAI_SD;
      this.currentItem.so_linecard = item.SO_LINECARD === null ? 0 : item.SO_LINECARD;
      this.currentItem.so_interface = item.SO_INTERFACE;
      this.currentItem.interface_start = item.INTERFACE_START;
      this.currentItem.interface_fname = item.INTERFACE_FNAME;
      this.currentItem.so_port_interface = item.SO_PORT_INTERFACE;
      this.currentItem.so_power = item.SO_POWER === null ? 0 : item.SO_POWER;
      this.currentItem.so_powerslot = item.SO_POWERSLOT;
      this.currentItem.power_start = item.POWER_START;
      this.currentItem.power_fname = item.POWER_FNAME;
      this.currentItem.so_port_power = item.SO_PORT_POWER;
      this.currentItem.so_controlcard = item.SO_CONTROLCARD === null ? 0 : item.SO_CONTROLCARD;
      this.currentItem.so_console = item.SO_CONSOLE;
      this.currentItem.console_start = item.CONSOLE_START;
      this.currentItem.console_fname = item.CONSOLE_FNAME;
      this.currentItem.so_port_console = item.SO_PORT_CONSOLE;
      this.currentItem.ip_mgmt = this.convertStringToArray(item.IP_MGMT);
      this.currentItem.ip_controller = this.convertStringToArray(item.IP_CONTROLLER);
    },

    convertStringToArray(arr) {
      if (arr != null && arr.length > 0) {
        return arr.split(',').map(num => parseInt(num, 10));
      }
      else {
        return [];
      }
    },

    validateControls: function () {
      let msg = "";
      if (this.controls.txtTenThietBi.invalid) {
        msg = "Tên thiết bị không được để trống.";
      }
      if (this.controls.txtKyHieuTB.invalid) {
        if (msg != "") msg += "\n\r";
        msg += "Ký hiệu thiết bị không được để trống.";
      }

      if (msg != "") msg += "\n\r";
      msg += this.$refs.interfaceComponent.validateControls();

      if (msg != "") msg += "\n\r";
      msg += this.$refs.powerComponent.validateControls();

      if (msg != "") msg += "\n\r";
      msg += this.$refs.consoleComponent.validateControls();

      if (msg != "") this.$root.toastError(msg);
      return msg == "";
    },

    gridItemsController_RowSelected: async function (args) {
      this.setCurrentItem(args.data);
    },

    gridItemsController_RowDeselected: async function (args) {
      let items = this.gridItemsController.getSelectedRecords();
      if (!(items == null || items.length == 0)) {
        this.setCurrentItem(items[0]);
      } else {
        this.setCurrentItemDefault();
      }
    },

    tabDsPort_Click: async function (args) {
      if (args.target.hash == "#tabInterface") {
        this.tabDsPortSelected.Inteface = true;
        this.tabDsPortSelected.Power = !this.tabDsPortSelected.Inteface;
        this.tabDsPortSelected.Console = !this.tabDsPortSelected.Inteface;
      } else if (args.target.hash == "#tabPower") {
        this.tabDsPortSelected.Power = true;
        this.tabDsPortSelected.Inteface = !this.tabDsPortSelected.Power;
        this.tabDsPortSelected.Console = !this.tabDsPortSelected.Power;
      }
      else {
        this.tabDsPortSelected.Console = true;
        this.tabDsPortSelected.Inteface = !this.tabDsPortSelected.Console;
        this.tabDsPortSelected.Power = !this.tabDsPortSelected.Console;
      }
    },

    btnGhiLai_Click: async function (args) {
      try {
        if (this.validateControls()) {
          let dsMapInterface = this.mapDsPort(this.$refs.interfaceComponent.dsPortInterface);
          let dsMapPower = this.mapDsPort(this.$refs.powerComponent.dsPortPower);
          let dsMapConsole = this.mapDsPort(this.$refs.consoleComponent.dsPortConsole);
          let dsPortTotal = [...dsMapInterface, ...dsMapPower, ...dsMapConsole];
          let dsPortJson = null;
          if (dsPortTotal != null && dsPortTotal.length > 0) {
            dsPortJson = JSON.stringify(dsPortTotal, null, 2);
          }

          const body = {
            bodieukhien_id: this.currentItem.id,
            ten: this.currentItem.ten,
            kyhieu: this.currentItem.kyhieu,
            serial_number: this.currentItem.serial_number,
            ghi_chu: this.currentItem.ghi_chu,
            hieu_luc: this.currentItem.hieu_luc,
            trangthai_sd: this.currentItem.trangthai_sd,
            ip_mgmt: this.currentItem.ip_mgmt ? this.currentItem.ip_mgmt.join(",") : null,
            ip_controller: this.currentItem.ip_controller ? this.currentItem.ip_controller.join(",") : null,
            so_linecard: this.$refs.interfaceComponent.interfaceItem.so_linecard,
            so_interface: this.$refs.interfaceComponent.interfaceItem.so_interface,
            interface_start: this.$refs.interfaceComponent.interfaceItem.interface_start,
            interface_fname: this.$refs.interfaceComponent.interfaceItem.interface_fname,
            so_port_interface: this.$refs.interfaceComponent.interfaceItem.so_port_interface,

            so_power: this.$refs.powerComponent.powerItem.so_power,
            so_powerslot: this.$refs.powerComponent.powerItem.so_powerslot,
            power_start: this.$refs.powerComponent.powerItem.power_start,
            power_fname: this.$refs.powerComponent.powerItem.power_fname,
            so_port_power: this.$refs.powerComponent.powerItem.so_port_power,

            so_controlcard: this.$refs.consoleComponent.consoleItem.so_controlcard,
            so_console: this.$refs.consoleComponent.consoleItem.so_console,
            console_start: this.$refs.consoleComponent.consoleItem.console_start,
            console_fname: this.$refs.consoleComponent.consoleItem.console_fname,
            so_port_console: this.$refs.consoleComponent.consoleItem.so_port_console,
            ds_port: dsPortJson
          };

          //let rs = await axios.post("http://localhost:10990/hatang/controller-tudia/capnhat-controller", body);
          let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/capnhat-controller", body);
          //result = rs.data.data;
          this.$root.toastSuccess("Cập nhật controller thành công");
          this.enableControls(false);
          this.getHistoryController();
        }
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    mapDsPort(dsPort) {
      if (dsPort != null && dsPort.length > 0) {
        return dsPort.map(({ ID, PHANLOAI, SOTHUTU, TEN, LOAICONG_ID, MUCDICHSUDUNG_CONG_ID, BANGTHONGCONG_ID, THONGTINMODULE_ID, KETNOI, TRANGTHAI_SD, HIEU_LUC }) => ({ ID, PHANLOAI, SOTHUTU, TEN, LOAICONG_ID, MUCDICHSUDUNG_CONG_ID, BANGTHONGCONG_ID, THONGTINMODULE_ID, KETNOI, TRANGTHAI_SD, HIEU_LUC }));
      }
      else return [];
    },

    getdsPort: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/get-ds-port-theo-phanloai", {
          bodieukhien_id: this.currentItem.id,
          phanloai: null
        });
        this.dsPort = rs.data;
        this.dsPortInterface = this.filterDsPortTheoPhanLoai(1);
        this.dsPortPower = this.filterDsPortTheoPhanLoai(2);
        this.dsPortConsole = this.filterDsPortTheoPhanLoai(3);
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    filterDsPortTheoPhanLoai(phanloai) {
      return this.dsPort.filter(item => item.PHANLOAI == phanloai);
    },

    getHistoryController: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/get-thongtin-lichsu", {
          id: this.currentItem.id
        });
        this.dsLichSuThayDoi = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    // btnNhapMoi_Click: async function (args) {
    //   this.enableControls(true);
    //   this.setCurrentItemDefault();
    // },

    btnHuyBo_Click: async function (args) {
      // let items = this.gridItemsController.getSelectedRecords();
      // let item = null;
      // if (!(items == null || items.length == 0)) {
      //   if (this.currentItem.id == 0) {
      //     item = items[0];
      //   }
      //   else {
      //     item = items.find(x => x.ID == this.currentItem.id);
      //   }
      // }
      // if (item != null) {
      //   this.setCurrentItem(item);
      // } else {
      //   this.setCurrentItemDefault();
      // }
      this.enableControls(false);
    },

    btnSua_Click: async function (args) {
      this.enableControls(true);
    },

    btnXoa_Click: async function (args) {

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
