<template src="./index.html"></template>

<script>
import ChiTietCongThietBi from "../Modal/chitiet-cong-thietbi";
import DanhSachPortThietBiTheoPhanLoai from "../Modal/danhsach-port-thietbi-theo-phanloai"
import ModalThemCard from '../../ThongTinThietBi/Modal/ModalThemCard/index.vue'
import axios from "axios";

export default {
  name: 'TabPower',
  props: {
    controllerItem: {
      type: Object
    },
    // dsPortPowerFromParent: {
    //   type: [Array, null], // Cho phép cả Array và null
    //   default: () => [] // Mặc định là mảng rỗng nếu không có dữ liệu
    // },
    enableControl: false
  },
  data: function () {
    return {
      dsPortPower: [],
      dsModule: [],
      powerItem: {
        thietbi_id: null,
        chungloaithietbi_id: null,
        bodieukhien_id: null,
        so_power: this.controllerItem.so_power,
        so_powerslot: this.controllerItem.so_powerslot,
        power_start: this.controllerItem.power_start,
        power_fname: this.controllerItem.power_fname,
        so_port_power: this.controllerItem.so_port_power
      },
      thongTinThietBi: {
        title: 'Module',
        thietbi_id: null,
        phanloai: 2
      },
      thongtinCard: {
        title: 'Module',
        id: null,
        phanloai: 2
      },
      selectionOptions: { mode: 'Row',  type: 'Single' },
      portPowerSelect: Object,
      soPortPowerBanDau: 0,
      tongsoPortModuleThietbi: 0,
      controls: {
        txtSoPower: {
          enabled: true,
          invalid: false
        },
        txtSoPowerSlot: {
          enabled: true,
          invalid: false
        },
        txtPowerStart: {
          enabled: true,
          invalid: false
        },
        txtPowerFname: {
          enabled: true,
          invalid: false
        },
        txtTongSoPortPower: {
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
    "powerItem.so_power": function (value) {
      this.controls.txtSoPower.invalid = value == "";
      if (value > 0) {
        this.getDsPortControllerChungLoai();
        //this.getTongPortChungLoaiThietBiKhaiBao();
      } else {
        this.getdsPortThietBiPower();
      }
    },
    "powerItem.so_powerslot": function (value) {
      if (value > 0 && this.powerItem.power_start != null && this.powerItem.power_fname != '') {
        this.generateList(value, this.powerItem.power_start, this.powerItem.power_fname);
      }
      this.controls.txtSoPowerSlot.invalid = value == "";
    },
    "powerItem.power_start": function (value) {
      if (this.powerItem.so_powerslot > 0 && value >= 0 && this.powerItem.power_fname != '') {
        this.generateList(this.powerItem.so_powerslot, value, this.powerItem.power_fname);
      }
      this.controls.txtPowerStart.invalid = value == "";
    },
    "powerItem.power_fname": function (value) {
      if (this.powerItem.so_powerslot > 0 && this.powerItem.power_start != null && value != '') {
        this.generateList(this.powerItem.so_powerslot, this.powerItem.power_start, value);
      }
      this.controls.txtPowerFname.invalid = value == "";
    },
  },
  created: async function () {
    try {
      this.setData(this.controllerItem);
      this.enableControls(this.enableControl);
      if (this.powerItem.so_power > 0) {
        this.getDsPortControllerChungLoai();
        //this.getTongPortChungLoaiThietBiKhaiBao();
      } else {
        this.getdsPortThietBiPower();
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
      this.powerItem.so_power = item.so_power;
      if (item.so_power === null) this.controls.txtSoPower.invalid = true;
      this.powerItem.so_powerslot = item.so_powerslot;
      if (item.so_powerslot === null) this.controls.txtSoPowerSlot.invalid = true;
      this.powerItem.power_start = item.power_start;
      if (item.power_start === null) this.controls.txtPowerStart.invalid = true;
      this.powerItem.power_fname = item.power_fname;
      if (item.power_fname === null) this.controls.txtPowerFname.invalid = true;
      this.powerItem.so_port_power = item.so_port_power;
      //this.dsPortPower = [...this.dsPortPowerFromParent];
      //this.soPortPowerBanDau = this.dsPortPowerFromParent.length;
      this.thongTinThietBi.thietbi_id = item.thietbi_id;
      this.thongTinThietBi.chungloaithietbi_id = item.chungloaithietbi_id;
      this.thongTinThietBi.bodieukhien_id = item.id;
    },

    enableControls: function (args) {
      this.controls.txtSoPower.enabled = args;
      this.controls.txtSoPowerSlot.enabled = args;
      this.controls.txtPowerStart.enabled = args;
      this.controls.txtPowerFname.enabled = args;
      this.controls.txtTongSoPortPower.enabled = args;
      this.controls.btnCapNhat.enabled = args;
    },

    generateList(n, batdau, dinhdang_ten) {
      const start = Number(batdau);
      if (this.soPortPowerBanDau == 0 || n != this.soPortPowerBanDau) {
        this.dsPortPower = Array.from({ length: n }, (_, index) => ({
          ID: null,
          BODIEUKHIEN_ID: this.controllerItem.id,
          PHANLOAI: 2,
          TEN_PHANLOAI: 'Power',
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
        this.dsPortPower.forEach((port, index) => {
          port.SOTHUTU = start + index;
          port.TEN = dinhdang_ten + `${start + index}`;
        });
      }
      this.dsPortPower = [...this.dsPortPower];
    },

    validateControls: function () {
      let msg = "";
      if (this.controls.txtSoPower.invalid || this.powerItem.so_power < 0) {
        msg = "Số PowerModule phải lớn hơn hoặc bằng 0.";
      }

      if (this.powerItem.so_power == 0) {
        if (this.controls.txtSoPowerSlot.invalid || this.powerItem.so_powerslot <= 0) {
          if (msg != "") msg += "\n\r";
          msg += "Số PowerSlot phải lớn hơn 0.";
        }
        if (this.controls.txtPowerStart.invalid || this.powerItem.power_start < 0) {
          if (msg != "") msg += "\n\r";
          msg += "Power bắt đầu từ phải lớn hơn hoặc bằng 0.";
        }
        if (this.controls.txtPowerFname.invalid) {
          if (msg != "") msg += "\n\r";
          msg += "Power định dạng tên không được để trống.";
        }
      }
      return msg;
    },

    handleDetailCLick(data) {
      this.$refs.dialogChiTietCongThietBi.dialogOpen(data);
    },

    handleDetailCardCLick(data) {
      this.$refs.modalThemCard.callFromController({ id: this.thongtinCard.id, portType: 2, isController: true})
    },

    getdsPortThietBiPower: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/get-ds-port-theo-phanloai",
        {
          bodieukhien_id: this.thongTinThietBi.bodieukhien_id,
          phanloai: 2
        });
        this.dsPortPower = rs.data;
        this.soPortPowerBanDau = this.dsPortPower.length;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    getDsPortControllerChungLoai: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/get-ds-port-bodieukhien-chungloai-theo-phanloai",
        {
          id: this.thongTinThietBi.bodieukhien_id,
          phanloai: 2
        });
        this.dsModule = rs.data;
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
        this.tongsoPortModuleThietbi = rs.data[0].SO_PORT
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    chitietCongThietBiTraVe(data) {
      let selectedPort = this.dsPortPower.find(item => item.SOTHUTU == data.no);
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
      this.dsPortPower = [...this.dsPortPower];
    },

    showThongTinThietBi() {
      this.$refs.modalThemCard.callFromController({ thietbicha_id: this.thongTinThietBi.bodieukhien_id, id: null, portType: 2, isController: true});
    },

    xemDanhSachPortThietBi() {
      this.$refs.dialogDanhSachPortThietBiTheoPhanLoai.dialogOpen();
    },

    rowCardSelected(args) {
      this.thongtinCard.id = args.data.THIETBICON_ID;
      this.getTongPortChungLoaiThietBiKhaiBao(args.data.THIETBICON_ID, 2);
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
