<template src="./ThongTinKT2.html">
</template>

<script>
import PanelADSL from './components/PanelADSL'
import PanelIMS from './components/PanelIMS'
import PanelCoDinh from './components/PanelCoDinh'
import PanelTSL from './components/PanelTSL'
import PanelWan from './components/PanelWan'
import PanelGphone from './components/PanelGphone'

export default {
  name: 'ThongTinKT2',
  components: {
    PanelADSL,
    PanelCoDinh,
    PanelIMS,
    PanelTSL,
    PanelWan,
    PanelGphone
  },
  data () {
    return {
      actions: [
        {
          id: 'tsbTimKiem',
          name: 'Tìm kiếm',
          active: true,
          icon_class: 'one-search',
          visible: true
        },
        {
          id: 'tsbtnVatTu',
          name: 'Vật tư',
          active: true,
          icon_class: 'one-vt',
          visible: true
        }
      ],
      cboTocDo_TSL: {
        list: [],
        value: 0,
        disabled: false
      },
      cboDichVuVT: {
        list: [],
        value: 0,
        disabled: false
      },
      frmThongTinTC: {
        txtMaTB: {
          value: '',
          disable: false
        },
        txtSoAo: {
          value: '',
          disable: false
        },
        rdoLoaiTC: 'rdoHopDong'
      },
      cboThietBiDau: {
        list: [],
        value: 0,
        disabled: false
      },
      cboThietBiCuoi: {
        list: [],
        value: 0,
        disabled: false
      },
      cboTocDo_TSL_Cuoi: {
        list: [],
        value: 0,
        disabled: false
      },
      cboTocDo: {
        list: [],
        value: 0,
        disabled: false
      },
      chkTocDo_PIR: {
        checked: false
      },
      cboTocDo_Pir: {
        list: [],
        value: 0,
        disabled: false
      },
      cboThietBi_Mgwan: {
        list: [],
        value: 0,
        disabled: false
      },
      pnlTSL: {
        visible: false,
        config: {
          lblLienHe_Dau: '',
          lblLienHe_Cuoi: '',
          txtSoAo_dauTSL: '',
          txtSvlan_Dau: '',
          txtAdsl_cap_Dau: '',
          txtNasport_id_Dau: '',
          txtIpDslam_Dau: '',
          txtTbiTruyenDan_dauTSL: '',
          txtTimeSlot_dauTSL: '',
          txtSlotOLT_Dau: '',
          txtCvlan_Dau: '',
          txtAdsl_port_Dau: '',
          txtVrf_Dau: '',
          txtIpwan_Dau: '',
          txtLink_dau_TSL: '',
          txtPortOLT_Dau: '',
          txtSoAoCuoiL: '',
          txtSvlan_Cuoi: '',
          txtAdsl_cap_Cuoi: '',
          txtNasport_id_Cuoi: '',
          txtIpDslam_Cuoi: '',
          txtTbi_Cuoi: '',
          txtTimeSlot_Cuoi: '',
          txtSlotOLT_Cuoi: '',
          txtCvlan_Cuoi: '',
          txtAdsl_port_Cuoi: '',
          txtVrf_Cuoi: '',
          txtIpwan_Cuoi: '',
          txtLink_Cuoi: '',
          txtPortOLT_Cuoi: '',
          txtSerial_dauTSL: '',
          txtCapGoc_dauTSL: '',
          txtTuCapGoc_dau_TSL: '',
          txtDoiCapGoc_dauTSL: '',
          txtLienTu_dauTSL: '',
          txtCuLy_dauTSL: '',
          txtDiaChiLD_Dau: '',
          txtLoaiCap_dau_TSL: '',
          txtCapNgon_dauTSL: '',
          txtTuCapNgon_dauTSL: '',
          txtDoiCapNgon_dau_TSL: '',
          txtHopCapNgon_dau_TSL: '',
          txtTuyenCap_dauTSL: '',
          txtLoaiCap_Cuoi: '',
          txtCapNgon_Cuoi: '',
          txtTuCapNgon_Cuoi: '',
          txtDoiCapNgon_Cuoi: '',
          txtHopCapNgon_Cuoi: '',
          txtTuyenCap_Cuoi: '',
          txtDiaChiLD_Cuoi: ''
        }
      },
      pnlWan: {
        visiable: false,
        config: {
          txtSiteID: '',
          txtRouter: '',
          txtSlotPE: '',
          txtPortPE: '',
          txtTramTbiWan: '',
          txtHuongKN: '',
          txtDSLAMWan: '',
          txtAdsl_Port_MGW: '',
          txtNastPortID_MGW: '',
          txtIpDslam_Wan: '',
          txtWAN_IP: '',
          txtLAN_IP: '',
          txtLink: '',
          txtTenTbi: '',
          txtLoaiCapWan: '',
          txttuCapGocWan: '',
          txtDoiCapGocWan: '',
          txtCapGocWAN: '',
          txtSlotOLT_MGW: '',
          txtTuCapNgonWan: '',
          txtDoiCapNgonWAN: '',
          txtCapNgonWan: '',
          txtPortOLT_MGW: '',
          txtPort_MDF_Wan: '',
          txtSvLan: '',
          txtCvLan: '',
          txtVRF: '',
          txtRoute: '',
          txtSerial: '',
          txtLienTuWan: '',
          txtHopCapNgonWan: '',
          txtTuyenCapWan: '',
          txtCuLyWan: 0
        }
      },
      frmThongSoKT: {
        txtSoAo_dauTSL: '',
        txtSvlan_Dau: '',
        txtCvlan_Dau: '',
        txtSerial_dauTSL: '',
        txtLoaiCap_dau_TSL: '',
        txtCapGoc_dauTSL: '',
        txtCapNgon_dauTSL: ''

      }
    }
  },
  methods: {
    handleShowModal () {

    },
    hideModal () {
      this.$refs.popupThongTinKT2.hide()
    },
    showModal () {
      this.$refs.popupThongTinKT2.show()
    },
    onChange () {
    },
    onActionClick (action) {
      if (action.id === 'tsbtnNhapMoi') {
        this.Clean()
        this.setActiveActions(1)
      } else if (action.id === 'tsbtnGhiLai') {
        this.CapNhat()
      } else if (action.id === 'tsbtnHuyBo') {
        this.setActiveActions(0)
      } else if (action.id === 'tsbtnXoa') {
        this.Xoa_Vattu()
      } else if (action.id === 'tsbtnExcel') {

      }
    }
  }
}
</script>

<style scoped>

</style>
