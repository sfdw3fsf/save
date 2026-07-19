<template src="./index.html"></template>

<script>
import ChiTietCongThietBi from "../Modal/chitiet-cong-thietbi";
import DanhSachPortThietBiTheoPhanLoai from "../Modal/danhsach-port-thietbi-theo-phanloai"
import axios from "axios";
//import ThongTinThietBi from '../../ThongTinThietBi/index.vue';
import ModalThemCard from '../../ThongTinThietBi/Modal/ModalThemCard/index.vue'
//import ChiTietCongThietBi from '../../ChiTietCongThietBi/index.vue';

export default {
  name: 'TabInterface',
  props: {
    controllerItem: {
      type: Object
    },
    // dsPortInterfaceFromParent: {
    //   type: [Array, null], // Cho phép cả Array và null
    //   default: () => [] // Mặc định là mảng rỗng nếu không có dữ liệu
    // },
    enableControl: false
  },
  data: function () {
    return {
      dsPortInterface: [],
      dsCardInterface: [],
      interfaceItem: {
        thietbi_id: null,
        chungloaithietbi_id: null,
        bodieukhien_id: null,
        so_linecard: this.controllerItem.so_linecard,
        so_interface: this.controllerItem.so_interface,
        interface_start: this.controllerItem.interface_start,
        interface_fname: this.controllerItem.interface_fname,
        so_port_interface: this.controllerItem.so_port_interface
      },
      thongTinThietBi: {
        title: 'LineCard',
        thietbi_id: null,
        phanloai: 1
      },
      thongtinCard: {
        title: 'LineCard',
        id: null,
        phanloai: 1
      },
      selectionOptions: { mode: 'Row',  type: 'Single' },
      soPortInterfaceBanDau: 0,
      tongsoPortThietbi: 0,
      portSelect: Object,
      controls: {
        txtSoLineCard: {
          enabled: true,
          invalid: false
        },
        txtSoInterface: {
          enabled: true,
          invalid: false
        },
        txtInterfaceStart: {
          enabled: true,
          invalid: false
        },
        txtInterfaceFname: {
          enabled: true,
          invalid: false
        },
        txtTongSoPort: {
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
    //ThongTinThietBi,
    ModalThemCard
  },
  computed: {

  },
  watch: {
    "enableControl": function (value) {
      this.enableControls(value);
    },
    "interfaceItem.so_linecard": function (value) {
      this.controls.txtSoLineCard.invalid = value === "";
      if (value > 0) {
        this.getDsPortControllerChungLoai();
        //this.getTongPortChungLoaiThietBiKhaiBao();
      } else {
        this.getdsPortThietBi();
      }
    },
    "interfaceItem.so_interface": function (value) {
      if (value > 0 && this.interfaceItem.interface_start != null && this.interfaceItem.interface_fname != '') {
        this.generateList(value, this.interfaceItem.interface_start, this.interfaceItem.interface_fname);

      }
      this.controls.txtSoInterface.invalid = value === "";
    },
    "interfaceItem.interface_start": function (value) {
      if (this.interfaceItem.so_interface > 0 && value >= 0 && this.interfaceItem.interface_fname != '') {
        this.generateList(this.interfaceItem.so_interface, value, this.interfaceItem.interface_fname);
      }
      this.controls.txtInterfaceStart.invalid = value === "";
    },
    "interfaceItem.interface_fname": function (value) {
      if (this.interfaceItem.so_interface > 0 && this.interfaceItem.interface_start != null && value != '') {
        this.generateList(this.interfaceItem.so_interface, this.interfaceItem.interface_start, value);
      }
      this.controls.txtInterfaceFname.invalid = value === "";
    },
  },

  created: async function () {
    try {
      this.setData(this.controllerItem);
      this.enableControls(this.enableControl);
      if (this.interfaceItem.so_linecard > 0) {
        this.getDsPortControllerChungLoai();
        //this.getTongPortChungLoaiThietBiKhaiBao();
      }
      else {
        this.getdsPortThietBi();
      }
    } finally {

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
      this.interfaceItem.thietbi_id = item.thietbi_id;
      this.interfaceItem.so_linecard = item.so_linecard;
      if (item.so_linecard === null) this.controls.txtSoLineCard.invalid = true;
      this.interfaceItem.so_interface = item.so_interface;
      if (item.so_interface === null) this.controls.txtSoInterface.invalid = true;
      this.interfaceItem.interface_start = item.interface_start;
      if (item.interface_start === null) this.controls.txtInterfaceStart.invalid = true;
      this.interfaceItem.interface_fname = item.interface_fname;
      if (item.interface_fname === null) this.controls.txtInterfaceFname.invalid = true;
      this.interfaceItem.so_port_interface = item.so_port_interface;
      //this.dsPortInterface = [...this.dsPortInterfaceFromParent];
      //this.soPortInterfaceBanDau = this.dsPortInterfaceFromParent.length;

      this.thongTinThietBi.thietbi_id = item.thietbi_id;
      this.thongTinThietBi.chungloaithietbi_id = item.chungloaithietbi_id;
      this.thongTinThietBi.bodieukhien_id = item.id;
    },

    enableControls: function (args) {
      this.controls.txtSoLineCard.enabled = args;
      this.controls.txtSoInterface.enabled = args;
      this.controls.txtInterfaceStart.enabled = args;
      this.controls.txtInterfaceFname.enabled = args;
      this.controls.txtTongSoPort.enabled = args;
      this.controls.btnCapNhat.enabled = args;
    },

    generateList(n, batdau, dinhdang_ten) {
      const start = Number(batdau);
      if (this.soPortInterfaceBanDau == 0 || n != this.soPortInterfaceBanDau) {
        this.dsPortInterface = Array.from({ length: n }, (_, index) => ({
          ID: null,
          BODIEUKHIEN_ID: this.controllerItem.id,
          MATHIETBI: this.controllerItem.mathietbi,
          PHANLOAI: 1,
          TEN_PHANLOAI: 'Interface',
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
        this.dsPortInterface.forEach((port, index) => {
          port.SOTHUTU = start + index;
          port.TEN = dinhdang_ten + `${start + index}`;
        });
      }
      this.dsPortInterface = [...this.dsPortInterface];

    },

    validateControls: function () {
      let msg = "";
      if (this.controls.txtSoLineCard.invalid || this.interfaceItem.so_linecard < 0) {
        msg = "Số LineCard phải là số nguyên lớn hơn hoặc bằng 0.";
      }

      if (this.interfaceItem.so_linecard == 0) {
        if (this.controls.txtSoInterface.invalid || this.interfaceItem.so_interface <= 0) {
          if (msg != "") msg += "\n\r";
          msg += "Số Interface phải lớn hơn 0.";
        }

        if (this.controls.txtInterfaceStart.invalid || this.interfaceItem.interface_start < 0) {
          if (msg != "") msg += "\n\r";
          msg += "Interface bắt đầu từ phải lớn hơn hoặc bằng 0.";
        }
        if (this.controls.txtInterfaceFname.invalid) {
          if (msg != "") msg += "\n\r";
          msg += "Interface định dạng tên không được để trống.";
        }
      }

      return msg;
    },

    handleDetailCLick(data) {
      console.log("handleDetailCLick", data);
      this.$refs.dialogChiTietCongThietBi.dialogOpen(data);
      //this.$refs.modalChiTietCongThietBi.show(data);
    },

    handleDetailCardCLick(data) {
      // this.$refs.modalThemCard.updateCardFromController({ id: this.thongtinCard.id, portType: 1})
      this.$refs.modalThemCard.callFromController({ id: this.thongtinCard.id, portType: 1, isController: true})
    },

    getdsPortThietBi: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/get-ds-port-theo-phanloai", {
          bodieukhien_id: this.thongTinThietBi.bodieukhien_id,
          phanloai: 1
        });
        this.dsPortInterface = rs.data;
        this.soPortInterfaceBanDau = this.dsPortInterface.length;
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    getDsPortControllerChungLoai: async function () {
      try {
        let rs = await this.$root.context.post("/web-ecms/hatang/controller-tudia/get-ds-port-bodieukhien-chungloai-theo-phanloai", {id: this.thongTinThietBi.bodieukhien_id,
          phanloai: 1
        });
        this.dsCardInterface = rs.data;
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
        this.tongsoPortThietbi = rs.data[0].SO_PORT
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
    },

    chitietCongThietBiTraVe(data) {
      let selectedPort = this.dsPortInterface.find(item => item.SOTHUTU == data.no);
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
      this.dsPortInterface = [...this.dsPortInterface];
    },

    showThongTinThietBi() {
      this.$refs.modalThemCard.callFromController({ thietbicha_id: this.thongTinThietBi.bodieukhien_id, id: null, portType: 1, isController: true});
    },

    xemDanhSachPortThietBi() {
      this.$refs.dialogDanhSachPortThietBiTheoPhanLoai.dialogOpen();
    },

    rowCardSelected(args) {
      this.thongtinCard.title ='LineCard';
      this.thongtinCard.id = args.data.THIETBICON_ID;
      this.getTongPortChungLoaiThietBiKhaiBao(args.data.THIETBICON_ID, 1);
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
