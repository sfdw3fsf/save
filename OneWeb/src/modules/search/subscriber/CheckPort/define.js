export default {
  header: {
    title: 'KIỂM TRA PORT THUÊ BAO',
    list: [
      {
        name: 'Tra cứu',
        link: {
          name: 'Ui.cards'
        }
      },
      {
        name: 'Test port',
        link: '/search/checkport'
      }
    ]

    // list: [{
    //     name: "Tra cứu",
    //     link: {
    //       name: "Ui.cards"
    //     }
    //   },
    //   {
    //     name: "Test port",
    //     link: {
    //       name: "Ui.cards"
    //     }
    //   },
    // ],
  },
  cboTocDo: {
    DataSource: [],
    value: null
  },
  txtSoMayAcc: '',
  chkMacAddress: null,
  txtUserONT: null,
  txtPassONT: null,
  txtInfoPort: null,
  // vuser_alt:"",
  // vpass_alt:"",
  // vuser_xtest:"",
  // vpass_xtest:"",
  // TN "server_id": "5","server_name": "Ctslinetest"
  iServer_DK: 1,
  _show_SERVER_DK: 0,
  // server_id:"",
  // server_name:"",
  // vInput:"",
  // vip:"",
  // vport_adsl:"",
  // vport_visa:"",
  // vport_bnm:"",
  vtbid: 0,
  //vthuebao_id:"456722",
  vthuebao_id: 0,
  vhdtb_id: 0,
  vma_tb: '',
  // vloaitbi_id:"1",
  // thamso:"7",
  // v_linetestUser:"",
  // v_linetestPassword:"",
  // vIP_xtest:"",
  // vportVS_xtest:"",
  //vkieu  0: Đo kiểm, 1: reset port, 2: set profile, 3 showIP,4 show bras
  //vkieu:0,
  p_hdtb_id: 0,
  p_thuebao_id: 0,
  // ipBras:null,
  // username:null,
  target: '.main-wrapper',

  button: {
    tracuuport: true,
    resetport: true,
    setprofile: true,
    resetpassigate040H: true,
    resetpassONT: true,
    wifiinfo: true,
    checkAccOnline: true,
    khoacong: true,
    mocong: true
    //thoat:true
  },
  UiState: {
    popup: {
      CongCuHoTro: false
    }
  },
  dsChucnang: {
    TRACUU_PORT: 'view',
    TRACUU_TT_BRAS: 'bras_status',
    KHOA_PORT: 'khoa_cong',
    MO_PORT: 'mo_cong',
    RESET_PORT: 'reset',
    SET_PORT_PROFILE: 'set',
    THONGTIN_WIFI: 'wifi',
    DOIPASS_WIFI: 'updatewlan',
    DOIPASS_ONT: 'update_ont',
    XOA_FILE_BACKUP_ONU: 'delbak',
    BACKUP_ONU: 'bak',
    THONGTIN_ONU: 'onuinfo',
    TRACUU_PASS_ONU: 'getweblogin',
    THAYDOI_STB: 'doistb',
    TRACUU_VISA_VASC: 'tracuu_visa',
    DOI_PASS_ADSL_FIBER_MYTV: 'doipass',
    TRACUU_APP_MYTV: 'tracuu_mytvapp',
    THONGTIN_ADVANCE_WIFI: 'advance_wifi',
    RESET_BACKUP: 'reset_backup',
    THAYDOI_CHANEL_BANDWIDTH: 'thaydoi_chanel_bandwidth',
    REBOOT_ONT: 'reboot_ont',
    ADD_BRIDGE_WAN: 'add_bridge_wan',
    GET_DEVICE_CONNECT: 'getdeviceconnect',
    MAP_PORT: 'map_port'
  }
}
