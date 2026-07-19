<template src="./index.html"></template>

<script>
import ChiTietCongThietBi from "../Modal/chitiet-cong-thietbi";
import DanhSachPortThietBiTheoPhanLoai from "../Modal/danhsach-port-thietbi-theo-phanloai"
import ModalThemCard from '../../ThongTinThietBi/Modal/ModalThemCard/index.vue'

export default {
  name: 'TabConsole',
  props: {
    controllerItem: {
      type: Object
    },
    // dsPortConsoleFromParent: {
    //   type: [Array, null], // Cho phép cả Array và null
    //   default: () => [] // Mặc định là mảng rỗng nếu không có dữ liệu
    // },
    enableControl: false
  },
  data: function () {
    return {
      dsPortConsole: [],
      dsCardConsole: [],
      consoleItem: {
        thietbi_id: null,
        chungloaithietbi_id: null,
        bodieukhien_id: null,
        so_controlcard: this.controllerItem.so_controlcard,
        so_console: this.controllerItem.so_console,
        console_start: this.controllerItem.console_start,
        console_fname: this.controllerItem.console_fname,
        so_port_console: this.controllerItem.so_port_console
      },
      thongTinThietBi: {
        title: 'ControlCard',
        thietbi_id: null,
        phanloai: 3
      },
      thongtinCard: {
        title: 'ControlCard',
        id: null,
        phanloai: 3
      },
      portConsoleSelect: Object,
      selectionOptions: { mode: 'Row',  type: 'Single' },
      soPortConsoleBanDau: 0,
      tongsoPortThietbi: 0,
      controls: {
        txtSoControlCard: {
          enabled: true,
          invalid: false
        },
        txtSoConsole: {
          enabled: true,
          invalid: false
        },
        txtConsoleStart: {
          enabled: true,
          invalid: false
        },
        txtConsoleFname: {
          enabled: true,
          invalid: false
        },
        txtTongSoPortConsole: {
          enabled: true,
          invalid: false
        },
        btnCapNhat: {
          enabled: true
        }
      }
    };
  },
  components: {
    ChiTietCongThietBi,
    DanhSachPortThietBiTheoPhanLoai,
    ModalThemCard
  },
  computed: {
  },
  watch: {
    "enableControl": function (value) {
      this.enableControls(value);
    },
    "consoleItem.so_controlcard": function (value) {
      this.controls.txtSoControlCard.invalid = value == "";
      if (value > 0) {
        this.getDsPortControllerChungLoai();
        //this.getTongPortChungLoaiThietBiKhaiBao();
      } else {
        this.getdsPortThietBi();
      }
    },
    "consoleItem.so_console": function (value) {
      if (value > 0 && this.consoleItem.console_start != null && this.consoleItem.console_fname != '') {
        this.generateList(value, this.consoleItem.console_start, this.consoleItem.console_fname);
      }
      this.controls.txtSoConsole.invalid = value == "";
    },
    "consoleItem.console_start": function (value) {
      if (this.consoleItem.so_console > 0 && value >= 0 && this.consoleItem.console_fname != '') {
        this.generateList(this.consoleItem.so_console, value, this.consoleItem.console_fname);
      }
      this.controls.txtConsoleStart.invalid = value == "";
    },
    "consoleItem.console_fname": function (value) {
      if (this.consoleItem.so_console > 0 && this.consoleItem.console_start != null && value != '') {
        this.generateList(this.consoleItem.so_console, this.consoleItem.console_start, value);
      }
      this.controls.txtConsoleFname.invalid = value == "";
    },
  },

  created: async function () {
    try {
      this.setData(this.controllerItem);
      this.enableControls(this.enableControl);
      if (this.consoleItem.so_controlcard > 0) {
        this.getDsPortControllerChungLoai();
        //this.getTongPortChungLoaiThietBiKhaiBao();
      } else {
        this.getdsPortThietBi();
      }
    } catch (error) {
      console.error("Lỗi trong created():", error);
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

    getColumnCapNhatTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center; align-items: center;">
                <button class="btn btn-second btn-detail" style="width: 50%;" :disabled="!parent.controls.btnCapNhat.enabled" @click="parent.handleDetailCLick(data)">Cập nhật</button>
              </div>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },

    getColumnChiTietCardTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center; align-items: center;">
                <button class="btn btn-second btn-detail" style="width: 50%;" :disabled="!parent.controls.btnCapNhat.enabled" @click="parent.handleDetailCardCLick(data)">Chi tiết</button>
              </div>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },

    setData: async function (item) {
      this.consoleItem.so_controlcard = item.so_controlcard;
      if (item.so_controlcard === null) this.controls.txtSoControlCard.invalid = true;
      this.consoleItem.so_console = item.so_console;
      if (item.so_console === null) this.controls.txtSoConsole.invalid = true;
      this.consoleItem.console_start = item.console_start;
      if (item.console_start === null) this.controls.txtConsoleStart.invalid = true;
      this.consoleItem.console_fname = item.console_fname;
      if (item.console_fname === null) this.controls.txtConsoleFname.invalid = true;
      this.consoleItem.so_port_console = item.so_port_console;
      //this.dsPortConsole = [...this.dsPortConsoleFromParent];
      //this.soPortConsoleBanDau = this.dsPortConsoleFromParent.length;
      this.thongTinThietBi.thietbi_id = item.thietbi_id;
      this.thongTinThietBi.chungloaithietbi_id = item.chungloaithietbi_id;
      this.thongTinThietBi.bodieukhien_id = item.id;
    },

    enableControls: function (args) {
      this.controls.txtSoControlCard.enabled = args;
      this.controls.txtSoConsole.enabled = args;
      this.controls.txtConsoleStart.enabled = args;
      this.controls.txtConsoleFname.enabled = args;
      this.controls.txtTongSoPortConsole.enabled = args;
      this.controls.btnCapNhat.enabled = args;
    },

    generateList(n, batdau, dinhdang_ten) {
      const start = Number(batdau);
      if (this.soPortConsoleBanDau == 0 || n != this.soPortConsoleBanDau) {
        this.dsPortConsole = Array.from({ length: n }, (_, index) => ({
          ID: null,
          BODIEUKHIEN_ID: this.controllerItem.id,
          PHANLOAI: 3,
          TEN_PHANLOAI: 'Console',
          SOTHUTU: start + index,
          TEN: dinhdang_ten + `${start + index}`,
          LOAICONG_ID: null,
          TEN_LOAICONG: '',
          MUCDICHSUDUNG_CONG_ID: null,
          TEN_MUCDICHSUDUNG_CONG: '',
          BANGTHONGCONG_ID: null,
          TEN_BANGTHONGCONG: '',
          THONGTINMODULE_ID: null,
          TEN_THONGTINMODULE: '',
          KETNOI: null,
          TEN_KETNOI: '',
          TRANGTHAI_SD: 0,
          TEN_TRANGTHAI_SD: 'Không sử dụng',
          HIEU_LUC: 1,
          TEN_HIEU_LUC: this.getHieuLucText(1)
        }));
      }
      else {
        this.dsPortConsole.forEach((port, index) => {
          port.SOTHUTU = start + index;
          port.TEN = dinhdang_ten + `${start + index}`;
        });
      }
      this.dsPortConsole = [...this.dsPortConsole];
    },

    validateControls: function () {
      let msg = "";
      if (this.controls.txtSoControlCard.invalid || this.consoleItem.so_controlcard < 0) {
        msg = "Số Control Card phải lớn hơn hoặc bằng 0.";
      }

      if (this.consoleItem.so_controlcard == 0) {
        if (this.controls.txtSoConsole.invalid || this.consoleItem.so_console <= 0) {
          if (msg != "") msg += "\n\r";
          msg += "Số Console phải lớn hơn 0.";
        }
        if (this.controls.txtConsoleStart.invalid || this.consoleItem.console_start < 0) {
          if (msg != "") msg += "\n\r";
          msg += "Console bắt đầu từ phải lớn hơn hoặc bằng 0.";
        }
        if (this.controls.txtConsoleFname.invalid) {
          if (msg != "") msg += "\n\r";
          msg += "Console định dạng tên không được để trống.";
        }
      }
      return msg;
    },

    handleDetailCLick(data) {
      this.$refs.dialogChiTietCongThietBi.dialogOpen(data);
    },

    handleDetailCardCLick(data) {
      this.$refs.modalThemCard.callFromController({ id: this.thongtinCard.id, portType: 3, isController: true})
    },

    getdsPortThietBi: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/get-ds-port-theo-phanloai", {
          bodieukhien_id: this.thongTinThietBi.bodieukhien_id,
          phanloai: 3
        });
        this.dsPortConsole = rs.data;
        this.soPortConsoleBanDau = this.dsPortConsole.length;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    getDsPortControllerChungLoai: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/get-ds-port-bodieukhien-chungloai-theo-phanloai", {id: this.thongTinThietBi.bodieukhien_id,
          phanloai: 3
        });
        this.dsCardConsole = rs.data;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    getTongPortChungLoaiThietBiKhaiBao: async function (id, phanloai) {
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/get-tong-port-chungloai-khaibao-theo-phanloai",
        {
          id: id,
          phanloai: phanloai
        });
        this.consoleItem.so_port_console = rs.data[0].SO_PORT
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    chitietCongThietBiTraVe(data) {
      let selectedPort = this.dsPortConsole.find(item => item.SOTHUTU == data.no);
      if (selectedPort) {
        selectedPort.LOAICONG_ID = data.portType;
        selectedPort.TEN_LOAICONG = data.namePortType;
        selectedPort.MUCDICHSUDUNG_CONG_ID = data.portPurpose;
        selectedPort.TEN_MUCDICHSUDUNG_CONG = data.namePortPurpose;
        selectedPort.BANGTHONGCONG_ID = data.portBandWidth;
        selectedPort.TEN_BANGTHONGCONG = data.namePortBandWidth;
        selectedPort.THONGTINMODULE_ID = data.moduleInfo;
        selectedPort.TEN_THONGTINMODULE = data.nameModuleInfo;
        selectedPort.KETNOI = data.connection;
        selectedPort.TEN_KETNOI = data.nameConnection;
        selectedPort.TRANGTHAI_SD = data.usingState;
        selectedPort.TEN_TRANGTHAI_SD = data.nameUsingState;
        selectedPort.HIEU_LUC = data.validity;
        selectedPort.TEN_HIEU_LUC = this.getHieuLucText(data.validity);
      }
      this.dsPortConsole = [...this.dsPortConsole];
    },

    showThongTinThietBi() {
      this.$refs.modalThemCard.callFromController({ thietbicha_id: this.thongTinThietBi.bodieukhien_id, id: null, portType: 3, isController: true});
    },

    xemDanhSachPortThietBi() {
      this.$refs.dialogDanhSachPortThietBiTheoPhanLoai.dialogOpen();
    },
    rowCardSelected(args) {
      this.thongtinCard.id = args.data.THIETBICON_ID;
      this.getTongPortChungLoaiThietBiKhaiBao(args.data.THIETBICON_ID, 3);
    },
    handleModalThemCard(data) {
      this.getDsPortControllerChungLoai();
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
