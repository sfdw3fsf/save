<template src="./frmBNMTools.html">

</template>

<script>
import Const, {MyFunctionController, TableFunc} from './const';
import SearchAccount from '@/modules/search/subscriber/SearchAccount';
import API from "../../../incident/subscriber/ReceiveIncident/API";
import {DBConstants} from "../../../../const/enums";

export default {
  name: "frmBNMTools",
  components: {
    SearchAccount
  },
  data() {
    return {
      MyFunctionEnum: {
        TRACUU_PORT: "view",
        TRACUU_TT_BRAS: "bras_status",
        KHOA_PORT: "khoa_cong",
        MO_PORT: "mo_cong",
        RESET_PORT: "reset",
        SET_PORT_PROFILE: "set",
        THONGTIN_WIFI: "wifi",
        DOIPASS_WIFI: "updatewlan",
        DOIPASS_ONT: "update_ont",
        XOA_FILE_BACKUP_ONU: "delbak",
        BACKUP_ONU: "bak",
        THONGTIN_ONU: "onuinfo",
        TRACUU_PASS_ONU: "getweblogin",
        THAYDOI_STB: "doistb",
        TRACUU_VISA_VASC: "tracuu_visa",
        DOI_PASS_ADSL_FIBER_MYTV: "doipass",
        TRACUU_APP_MYTV: "tracuu_mytvapp",

        THONGTIN_ADVANCE_WIFI: "advance_wifi",
        RESET_BACKUP: "reset_backup",
        THAYDOI_CHANEL_BANDWIDTH: "thaydoi_chanel_bandwidth",
        REBOOT_ONT: "reboot_ont",
        ADD_BRIDGE_WAN: "add_bridge_wan",
        GET_DEVICE_CONNECT: "getdeviceconnect",

        MAP_PORT: "map_port",
      },
      funcs: [],
      srv_gnms: 'https://vnptcab.vn:8888/api/Langpack/DoKiemV2',
      MaTB: '',
      service_type: "add_bridge",
      serial_num: "",
      indexInterfaceGrouping: 0,
      r_index_grvInterfaceGrouping: null,
      wbrKQ: '',
      txtTraCuu: '',
      txtPPPUser: '',
      txtPPPPass: '',
      txtVlanMux802_1Priority: -1,
      txtVlanMuxID: -1,
      rdogrIPv4: '',
      txtExternalIPAdress: '',
      txtSubnetMask: '',
      txtDefaultGateway: '',
      txtDnsIfName: '',
      txtDnsServers: '',
      cboChucNang: 0,
      btnTraCuuText: 'Tra cứu',
      txtTraCuu_PlaceHolder: 'Nhập số máy/Account',
      btnTraCuu: {
        Enabled: true,
        Visible: true
      },
      cboKieuXacThuc: {
        value: 0,
        source: []
      },
      cboKieuMaHoa: {
        value: 0,
        source: []
      },
      txtTenWifi: '',
      txtPwd: '',
      cboBoundIfName: {
        value: 0,
        source: []
      },
      txtChannel: 0,
      txtBandwidth: 0,
      txtTransmitPower: 100,
      currentTab: 1,
      gridWanService: [],
      colsWanSetup: Const.colsWanSetup,
      txtWanPortSelected: 'eth0',
      cboNetworkProtocol: 'IPv4 Only',
      txtServiceDescription: 'br_eth0',
      grdoAddType: 2,
      rdogrIPv6: 0,
      chkNAT: false,
      chkFirewall: false,
      chkIGMPEnabled: false,
      cboNetworkProtocol_Enabled: false,

      //grouping
      gridInterfaceGrouping: [],
      colsInterfaceGroup: Const.colsInterfaceGroup,
      txtGroupName: '',
      cboLanInterfacesList: {
        value: 0,
        source: []
      },
      cboWanInterfacesList: {
        value: 0,
        source: []
      },

      //voice
      cboIpAddressFamily: '1',
      cboRegion: {
        value: '',
        source: [
          {id: "AU", name: "AU"},
          {id: "BE", name: "BE"},
          {id: "BR", name: "BR"},
          {id: "CL", name: "CL"},
          {id: "CN", name: "CN"},
          {id: "CZ", name: "CZ"},
          {id: "DK", name: "DK"},
          {id: "XE", name: "XE"},
          {id: "FI", name: "FI"},
          {id: "FR", name: "FR"},
          {id: "DE", name: "DE"},
          {id: "HU", name: "HU"},
          {id: "IN", name: "IN"},
          {id: "IT", name: "IT"},
          {id: "JP", name: "JP"},
          {id: "MX", name: "MX"},
          {id: "NL", name: "NL"},
          {id: "NZ", name: "NZ"},
          {id: "US", name: "US"},
          {id: "PL", name: "PL"},
          {id: "ES", name: "ES"},
          {id: "SE", name: "SE"},
          {id: "NO", name: "NO"},
          {id: "CH", name: "CH"},
          {id: "XT", name: "XT"},
          {id: "GB", name: "GB"},
          {id: "TW", name: "TW"},
          {id: "AE", name: "AE"},
          {id: "CY", name: "CY"}
        ]
      },
      txtUserAgentDomain: '',
      txtDigitMap: '',
      chkProxy: false,
      txtProxyServer: '',
      txtProxyServerPort: '',
      chkOutboundProxy: false,
      txtOutBoundProxy: '',
      txtOutBoundProxyPort: '',
      chkRegistrar: false,
      txtRegistrarServer: '',
      txtRegistrarServerPort: '',
      //sip parameter
      chkAccountEnable1: false,
      chkAccountEnable2: false,
      lblStatus1: '-',
      lblStatus2: '-',
      lblCallState1: '-',
      lblCallState2: '-',
      txtSipUri1: '',
      txtSipUri2: '',
      txtCallIDNamer1: '',
      txtCallIDNamer2: '',
      txtSipAuthUserName1: '',
      txtSipAuthUserName2: '',
      txtSipAuthUserPass1: '',
      txtSipAuthUserPass2: '',
    }
  },
  computed: {
    DefaultFunc() {
      return this.MyFunctionEnum.TRACUU_PORT
    }
  },
  watch: {
    grdoAddType: function () {
      this.grdoAddType_SelectedIndexChanged();
    }
  },
  async mounted() {
    let tableFuncs = [];
    tableFuncs.push(new TableFunc("TRA CỨU PORT", this.MyFunctionEnum.TRACUU_PORT, "Tra cứu", 0));
    tableFuncs.push(new TableFunc("[GNMS]-XEM THÔNG TIN WIFI", this.MyFunctionEnum.THONGTIN_WIFI, "Tra cứu", 3));
    tableFuncs.push(new TableFunc("[GNMS]-ĐỔI PASS WIFI", this.MyFunctionEnum.DOIPASS_WIFI, "Thực thi", 7));
    tableFuncs.push(new TableFunc("[GNMS]-THÔNG TIN ONU", this.MyFunctionEnum.THONGTIN_ONU, "Tra cứu", 6));
    tableFuncs.push(new TableFunc("[GNMS]-XEM PASS LOGIN ONU", this.MyFunctionEnum.TRACUU_PASS_ONU, "Tra cứu", 8));
    tableFuncs.push(new TableFunc("[GNMS]-ĐỔI PASS LOGIN ONU", this.MyFunctionEnum.DOIPASS_ONT, "Thực thi", 9));
    tableFuncs.push(new TableFunc("RESET PORT", this.MyFunctionEnum.RESET_PORT, "Thực thi", 61));
    tableFuncs.push(new TableFunc("[GNMS]- LẤY THÔNG TIN CẤU HÌNH ADVANCE CỦA WIFI", this.MyFunctionEnum.THONGTIN_ADVANCE_WIFI, "Tra cứu", 18));
    tableFuncs.push(new TableFunc("[GNMS]- KHÔI PHỤC CÀI ĐẶT GỐC THIẾT BỊ", this.MyFunctionEnum.RESET_BACKUP, "Thực thi", 19));
    tableFuncs.push(new TableFunc("[GNMS]- THAY ĐỔI CHANEL, BANDWIDTH", this.MyFunctionEnum.THAYDOI_CHANEL_BANDWIDTH, "Thực thi", 20));
    tableFuncs.push(new TableFunc("[GNMS]- RESET THIẾT BỊ", this.MyFunctionEnum.REBOOT_ONT, "Thực thi", 21));
    tableFuncs.push(new TableFunc("[GNMS]- CÀI ĐẶT WAN BRIDGE", this.MyFunctionEnum.ADD_BRIDGE_WAN, "Thực thi", 22));
    tableFuncs.push(new TableFunc("[GNMS]- Lấy danh sách MAC thiết bị kết nối wifi tới ONT", this.MyFunctionEnum.GET_DEVICE_CONNECT, "Tra cứu", 23));
    tableFuncs.push(new TableFunc("[GNMS]- MAPPORT FTTH, MYTV, IMS", this.MyFunctionEnum.MAP_PORT, "Thực thi", 24));
    tableFuncs.push(new TableFunc("TRA CỨU VISA, VASC, VTN", this.MyFunctionEnum.TRACUU_VISA_VASC, "Tra cứu", 10));
    tableFuncs.push(new TableFunc("TRA CỨU THÔNG TIN HỖ TRỢ CÀI APP MYTV", this.MyFunctionEnum.TRACUU_APP_MYTV, "Tra cứu", 15));

    for (let dr of tableFuncs) {
      let code = dr["value"] + "";
      let action = dr["shortname"] + "";
      let caption = dr["name"] + "";
      let id = parseInt(dr["id"]);

      let func = new MyFunctionController(id, code, caption)
      func.ActionName = action;
      func.PlaceHolder = code === this.MyFunctionEnum.TRACUU_APP_MYTV ? "Nhập vào serial TV cần tra cứu" : "Nhập số máy/Account"
      this.funcs.push(func);
    }

    this.cboChucNang = this.DefaultFunc;

    await this.LAY_THONGTIN_KIEUXT();
    await this.LAY_THONGTIN_KIEUMH();
  },
  methods: {
    HIEN_THI_KET_QUA(strTB) {
      this.wbrKQ = strTB;
    },

    Clear() {
      this.txtPPPUser = "";
      this.txtPPPPass = "";
      this.txtVlanMux802_1Priority = "-1";
      this.txtVlanMuxID = "-1";

      this.rdogrIPv4 = 0;
      this.txtExternalIPAdress = "";
      this.txtSubnetMask = "";
      this.txtDefaultGateway = "";
      this.txtDnsIfName = "";
      this.txtDnsServers = "";
    },

    async func_Executed(sender) {
      if (sender == null) {
        return;
      }

      this.HIEN_THI_KET_QUA("");

      let parars = {
        maTb: this.txtTraCuu,
        kieu: sender.ID,
        profile: null,
        wifis: null,
        input: null,
      };

      switch (sender.Code) {
        case this.MyFunctionEnum.RESET_PORT:
          await this.resetPort(this.txtTraCuu)
          return;
        case this.MyFunctionEnum.SET_PORT_PROFILE:
          // parars["vprofile"] = cbo1.EditValue;
          break;
        case this.MyFunctionEnum.DOIPASS_WIFI:
          let Regex = new RegExp("^[a-z](?:_?[a-z0-9._ -]+)*$")
          let tenWifi = this.txtTenWifi;
          let matKhau = this.txtPwd;
          let kieu_xt = this.cboKieuXacThuc.value;
          let kieu_mh = this.cboKieuMaHoa.value;

          if (tenWifi == null || tenWifi == "") {
            this.$root.$toast.error("Chưa nhập tên wifi ");
            $("#txtTenWifi").focus();
            return;
          } else if (!Regex.test(tenWifi)) {
            this.$root.$toast.error("Tên wifi chứa ký tự không hợp lệ ");
            $("#txtTenWifi").focus();
            return;
          } else if (tenWifi.length > 30) {
            this.$root.$toast.error("Tên wifi không được quá 30 ký tự ");
            $("#txtTenWifi").focus();
            return;
          }

          if (matKhau == null || matKhau == "") {
            $("#txtPwd").focus();
            this.$root.$toast.error("Chưa nhập mật khẩu wifi ");
            return;
          } else if (matKhau.length < 8 || matKhau.length > 30) {
            $("#txtPwd").focus();
            this.$root.$toast.error("Mật khẩu wifi phải có tối thiểu 8 ký tự và tối đa 30 ký tự");
            return;
          }

          if (kieu_xt == null || kieu_xt == "") {
            this.$root.$toast.error("Chưa chọn kiểu xác thực ");
            return;
          }
          if (kieu_mh == null || kieu_mh == "") {
            this.$root.$toast.error("Chưa chọn kiểu mã hóa ");
            return;
          }

          let wifis = [{
            "authMode": kieu_xt,
            "enable": true,
            "encrypt": kieu_mh,
            "index": 0,
            "password": matKhau,
            "ssid": tenWifi,
            "type": "main_wifi"
          }];
          parars.wifis = wifis;
          break;
        case this.MyFunctionEnum.THAYDOI_STB:
          // profile = "{\"date\": \"" + DateTime.Now.ToString("dd/MM/yyyy") + "\",\"time\": \"" + DateTime.Now.ToString("dd/MM/yyyy") + "\",\"lydo\": \"" + txt3 + "\"}";
          break;
        case this.MyFunctionEnum.THAYDOI_CHANEL_BANDWIDTH:
          parars.input = {
            channel: this.txtChannel.trim(),
            bandwidth: this.txtBandwidth.trim(),
            country: 'US/0',
            autoChannelTimer: 900,
            transmitPower: this.txtTransmitPower.trim()
          }

          break;
        case this.MyFunctionEnum.ADD_BRIDGE_WAN:
          parars.input = {
            serviceDescription: 'br_eth0',
            vlanMux802_1Priority: this.txtVlanMux802_1Priority.trim(),
            vlanMuxID: this.txtVlanMuxID.trim(),
            enableWan: true,
            optical: 'VNPT00A968D0'
          }
          break;
        case this.MyFunctionEnum.REBOOT_ONT:
          break;
      }

      await this.CallGetMethod(parars);
    },

    async CallGetMethod(params) {
      try {
        this.loading(true);
        let rs = await this.axios.post('/app-thicong/baoHong/doKiem', params)
        let data = rs.data.data || rs.data.message;
        data = data.replaceAll('|', '<br/>')
          .replaceAll('\n', '<br/>');
        this.wbrKQ = data;

        return data;
      } catch (e) {
        console.log(e);
        this.wbrKQ = "Có lỗi xảy ra: " + e.getMessage();
      } finally {
        this.loading(false);
      }

      return '';
    },

    showFormTraCuuDanhBa() {
      this.$refs['popupSearchAccount'].show()
    },

    acceptSearchAccount(item) {
      this.$refs['popupSearchAccount'].hide();
      if (item === undefined || item === null) {
        return;
      }

      this.txtTraCuu = item.ma_tb;
    },

    getFuncByCode: function (funcCode) {
      let filter = this.funcs.filter(item => {
          return item.Code === funcCode
        }
      );

      if (filter.length > 0) {
        return filter[0];
      }

      return null;
    },

    async btnTraCuu_Click() {
      if (this.txtTraCuu == null || this.txtTraCuu.length <= 0)
        return;
      let func = this.cboChucNang;
      if (func == null) return;

      if (this.btnTraCuuText === "Thực thi") {
        this.$confirm(
          `Bạn có muốn thực hiện chức năng này không?`,
          "Thông báo",
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "warning",
          }
        ).then(async () => {
          await this.func_Executed(this.getFuncByCode(this.cboChucNang));
        });
      } else {
        await this.func_Executed(this.getFuncByCode(this.cboChucNang));
      }
    },

    async cboChucNang_EditValueChanged(val) {
      let func = this.getFuncByCode(val);
      if (func == null) {
        this.btnTraCuu.Enabled = false;
        return;
      } else {
        this.btnTraCuu.Enabled = true;
      }

      this.AnControl();
      this.txtTraCuu_PlaceHolder = func.PlaceHolder;
      this.btnTraCuuText = func.ActionName;

      switch (func.Code) {
        case this.MyFunctionEnum.TRACUU_PORT://TRA CỨU PORT
        case this.MyFunctionEnum.TRACUU_TT_BRAS://TRẠNG THÁI BRAS
        case this.MyFunctionEnum.KHOA_PORT://KHÓA CỔNG GPON
        case this.MyFunctionEnum.MO_PORT://MỞ CỔNG GPON
        case this.MyFunctionEnum.THONGTIN_WIFI://[GNNMS]- XEM THÔNG TIN WIFI
        case this.MyFunctionEnum.THONGTIN_ONU://[GNNMS]- THÔNG TIN ONU
        case this.MyFunctionEnum.TRACUU_PASS_ONU://[GNNMS]- XEM PASS LOGIN ONU
        case this.MyFunctionEnum.TRACUU_VISA_VASC://TRA CỨU VISA, VASC, VTN
        case this.MyFunctionEnum.THONGTIN_ADVANCE_WIFI://[GNNMS]- LẤY THÔNG TIN CẤU HÌNH ADVANCE CỦA WIFI
          this.btnTraCuuText = "Tra cứu";
          break;
        case "6"://TRA CỨU THÔNG TIN HỖ TRỢ CÀI APP MYTV
          this.btnTraCuuText = "Tra cứu";
          this.txtTraCuu_PlaceHolder = "Nhập serial TV cần tra cứu";

          break;
        case this.MyFunctionEnum.DOI_PASS_ADSL_FIBER_MYTV://ĐỔI PASS ADSL, FTTH, MYTV
          this.btnTraCuuText = "Thực thi";
          break;
        case this.MyFunctionEnum.SET_PORT_PROFILE://SET PORT PROFILE
          this.btnTraCuuText = "Thực thi";
          break;
        case this.MyFunctionEnum.THAYDOI_STB://THAY ĐỔI SETTOPBOX
          this.btnTraCuuText = "Thực thi";
          break;
        case this.MyFunctionEnum.RESET_PORT://RESET PORT
        case this.MyFunctionEnum.XOA_FILE_BACKUP_ONU://[GNNMS]- XÓA FILE BACKUP CẤU HÌNH
        case this.MyFunctionEnum.BACKUP_ONU://[GNNMS]- BACKUP CẤU HÌNH
          this.btnTraCuuText = "Thực thi";
          break;
        case this.MyFunctionEnum.DOIPASS_WIFI://[GNNMS]-ĐỔI PASS WIFI
          this.btnTraCuuText = "Thực thi";
          break;
        case this.MyFunctionEnum.DOIPASS_ONT://[GNNMS]-ĐỔI PASS ONT
          this.btnTraCuuText = "Thực thi";
          break;
        case this.MyFunctionEnum.THAYDOI_CHANEL_BANDWIDTH:
          this.btnTraCuuText = "Thực thi";
          break;
        case this.MyFunctionEnum.ADD_BRIDGE_WAN:
          this.btnTraCuuText = "Thực thi";
          break;
      }
    },

    AnControl() {
      // pal1.Visible = pal2.Visible = pal3.Visible = pal4.Visible = pal5.Visible = false;
      // cbo1.Properties.DataSource = cbo2.Properties.DataSource = null;
      this.wbrKQ = "";
      // dtp1.DateTime = DateTime.Now;
      // cbo1.Properties.DataSource = null;
      // cbo2.Properties.DataSource = null;
    },

    async LAY_THONGTIN_KIEUXT() {
      let rs = await this.axios.get('/web-quantri/danhmuc-chung/ECMS_KIEU_XT');
      this.cboKieuXacThuc.source = rs.data.data == null ? [] : rs.data.data;
      if (this.cboKieuXacThuc.source.length > 0) {
        this.cboKieuXacThuc.value = this.cboKieuXacThuc.source[0].KIEU_XT;
      }
    },

    async LAY_THONGTIN_KIEUMH() {
      let rs = await this.axios.get('/web-quantri/danhmuc-chung/ECMS_KIEU_MH');
      this.cboKieuMaHoa.source = rs.data.data == null ? [] : rs.data.data;
      if (this.cboKieuMaHoa.source.length > 0) {
        this.cboKieuMaHoa.value = this.cboKieuMaHoa.source[0].KIEU_MH;
      }
    },

    txtTraCuu_KeyDown() {
      if (this.cboChucNang === this.MyFunctionEnum.MAP_PORT || this.cboChucNang === this.MyFunctionEnum.DOIPASS_ONT) {
        this.getSerial(this.txtTraCuu);
        return;
      }

      this.btnTraCuu_Click();
    },

    async getSerial(ma_tb) {
      this.loading(true);
      let params = {
        maTb: ma_tb,
        kieu: 0
      }

      let rs = await this.CallGetMethod(params);
      this.wbrKQ = '';

      await this.FinishGetSerial(rs);
    },

    async FinishGetSerial(res) {
      try {
        if (res == null || res == '') {
          return;
        }

        if (res.indexOf("VNPT") !== -1) {
          let inx = res.indexOf("VNPT") + 4;
          this.serial_num = "VNPT" + res.substr(inx, 8).toLowerCase();

          let rs = await this.axios.get(`/tichhop/nms/rest/device/getBoundInterfacesList?optical=${this.serial_num}`);
          let {boundInterfacesList} = rs.data;
          if (boundInterfacesList != null && boundInterfacesList.length > 0) {
            this.cboBoundIfName.source = boundInterfacesList;
          }
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false);
      }
    },

    async btnDoiPassONT_Click() {
      this.loading(true);
      let Regex = new RegExp("^[a-z](?:_?[a-z0-9._ -]+)*$")
      let tenWifi = this.txtTenWifi;
      let matKhau = this.txtPwd;
      if (tenWifi == "" || matKhau == "") {
        this.$root.$toast.error("Chưa nhập thông tin user, password ONT");
        return;
      }

      if (!Regex.test(tenWifi)) {
        this.$root.$toast.error("User ONT chứa ký tự không hợp lệ ");
        return;
      } else if (tenWifi.length > 30) {
        this.$root.$toast.error("User ONT không được quá 30 ký tự ");
        return;
      } else if (matKhau.length < 8 || matKhau.length > 30) {
        this.$root.$toast.error("Mật khẩu ONT phải có tối thiểu 8 ký tự và tối đa 30 ký tự");
        return;
      }

      try {
        let bodyFormData = new FormData();
        bodyFormData.append("serialNumber", this.serial_num);
        bodyFormData.append("password", matKhau);
        bodyFormData.append("username", tenWifi);
        // let rs = await this.axios.post('/tichhop/nms/rest/user/configNewPassWord', {
        //   "password": matKhau,
        //   "serialNumber": this.serial_num,
        //   "username": tenWifi
        // });
        let rs = await this.axios.post(
          '/tichhop/nms/rest/user/configNewPassWord',
          bodyFormData,
          {
            headers: {"Content-Type": "multipart/form-data"},
          }
        )

        this.HIEN_THI_KET_QUA(rs.data.data);
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },

    async btnAction2_Click() {
      if (this.txtTraCuu == null || this.txtTraCuu == '')
        return;

      let func = this.getFuncByCode(this.cboChucNang);
      if (func == null) return;

      await this.func_Executed(func);
    },

    setActiveTab(tabIndex) {
      this.currentTab = tabIndex
    },

    grdoAddType_SelectedIndexChanged() {
      this.AnHienWanControl(parseInt(this.grdoAddType));
    },

    AnHienWanControl(wan_type) {
      if (wan_type == 2) {
        this.cboNetworkProtocol_Enabled = false;
        this.txtServiceDescription = "br_eth0";
        this.service_type = "add_bridge";
      } else {
        if (wan_type == 0) {
          this.txtServiceDescription = "pppoe_eth0";
          this.service_type = "add_pppoe";
        } else {
          this.txtServiceDescription = "ipoe_eth0";
          this.service_type = "add_ipoe";
        }

        this.cboNetworkProtocol_Enabled = true;
      }

      this.Clear();
    },

    async btnGetWanList_Click() {
      this.loading(true);
      try {
        let rs = await this.axios.get(`/tichhop/nms/rest/device/getListWan?optical=${this.serial_num}`);
        this.gridWanService = rs.data.data || [];
      } catch (e) {

      } finally {
        this.loading(false);
      }
    },

    btnApply_Click() {
      this.$confirm(
        `Bạn có muốn thực hiện chức năng này không?`,
        "Thông báo",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }
      ).then(async () => {
        this.loading(true);
        // this.serial_num = 'VNPT008b8e3b';
        let {service_type} = this;
        if (service_type == "add_bridge") {
          let query = `?enableWan=true` +
            `&optical=${this.serial_num}` +
            `&serviceDescription=${this.txtServiceDescription}` +
            `&vlanMux802_1Priority=${this.txtVlanMux802_1Priority}` +
            `&vlanMuxID=${this.txtVlanMuxID}`;

          this.axios.post(`/tichhop/nms/rest/device/addBridgeWan${query}`)
            .then(rs => {
              rs.data.error_code == 0 ? this.$root.$toast.success('Thành công') : this.$root.$toast.error(rs.data.message);
            })
            .catch(err => {
              console.log(err);
              this.$root.$toast.error("Lỗi API: " + err.data.error);
            })
            .finally(() => {
              this.loading(false);
            });
        } else if (service_type == "add_ipoe") {
          let _objWan = Const.objectWan();
          if (this.rdogrIPv4 == 0) {
            _objWan.wanType = "WANIPConnection";
            _objWan.connectionType = "IP_Routed";
            _objWan.wanName = this.txtServiceDescription;
            _objWan.ipv4Enable = true;
            _objWan.ipv6Enable = false;
            _objWan.natEnable = this.chkNAT;
            _objWan.firewallEnable = this.chkFirewall;
            _objWan.vlanPriority = parseInt(this.txtVlanMux802_1Priority);
            _objWan.vlanMuxID = parseInt(this.txtVlanMuxID);
            _objWan.wanIndex = 5;
            _objWan.igmpEnabled = this.chkIGMPEnabled;
            _objWan.addressingType = "DHCP";
            _objWan.connectionID = 5;
          } else {
            _objWan.wanType = "WANIPConnection";
            _objWan.connectionType = "IP_Routed";
            _objWan.wanName = this.txtServiceDescription;
            _objWan.ipv4Enable = true;
            _objWan.ipv6Enable = false;
            _objWan.natEnable = this.chkNAT;
            _objWan.firewallEnable = this.chkFirewall;
            _objWan.vlanPriority = parseInt(this.txtVlanMux802_1Priority);
            _objWan.vlanMuxID = parseInt(this.txtVlanMuxID);
            _objWan.wanIndex = 7;
            _objWan.externalIPAdress = this.txtExternalIPAdress;
            _objWan.subnetMask = this.txtSubnetMask;
            _objWan.defaultGateway = this.txtDefaultGateway;
            _objWan.dnsIfName = this.txtDnsIfName;
            _objWan.dnsServers = this.txtDnsServers;
            _objWan.igmpEnabled = true;
            _objWan.addressingType = "Static";
            _objWan.connectionID = 7;
          }

          this.addWan(_objWan);
        } else if (service_type == "add_pppoe") {
          let _objWan = Const.objectWan();
          _objWan.wanType = "WANPPPConnection";
          _objWan.connectionType = "IP_Routed";
          _objWan.wanName = this.txtServiceDescription;
          _objWan.userName = this.txtPPPUser;
          _objWan.password = this.txtPPPPass;
          _objWan.ipv4Enable = true;
          _objWan.ipv6Enable = false;
          _objWan.natEnable = this.chkNAT;
          _objWan.firewallEnable = this.chkFirewall;
          _objWan.vlanPriority = parseInt(this.txtVlanMux802_1Priority);
          _objWan.vlanMuxID = parseInt(this.txtVlanMuxID);
          _objWan.connectionID = 7;
          _objWan.wanIndex = 5;
          _objWan.unnumberedModel = false;
          _objWan.addressingType = "Static";

          this.addWan(_objWan);
        }
      });
    },

    addWan(_objWan) {
      this.loading(true);
      this.axios.post(`/tichhop/nms/rest/device/addWan?optical=${this.serial_num}`, _objWan)
        .then(rs => {
          rs.data.error_code == 0 ? this.$root.$toast.success('Thành công') : this.$root.$toast.error(rs.data.message);
        })
        .catch(err => {
          console.log(err);
          this.$root.$toast.error("Lỗi API: " + err.data.error);
        })
        .finally(() => {
          this.loading(false);
        });
    },

    grvInterfaceGrouping_FocusedRowChanged(row) {
      this.r_index_grvInterfaceGrouping = row;

      if (row == null) {
        return;
      }

      this.indexInterfaceGrouping = row.index;
    },

    async btnGetInterfaceGrouping_Click() {
      this.loading(true);
      let params = {
        maTb: this.txtTraCuu,
        kieu: 0
      }

      let rs = await this.CallGetMethod(params);
      this.wbrKQ = '';

      await this.FinishGetInterfaceGrouping(rs);
    },

    async FinishGetInterfaceGrouping(res) {
      try {
        this.loading(true);
        if (res == null || res == '') {
          return;
        }

        if (res.indexOf("VNPT") !== -1) {
          let inx = res.indexOf("VNPT") + 4;
          this.serial_num = "VNPT" + res.substr(inx, 8).toLowerCase();
          // this.serial_num = 'VNPT008b8e3b';

          let rs = await this.axios.get(`/tichhop/nms/rest/device/getInterfaceGroupingList?serialNumber=${this.serial_num}`)
          this.gridInterfaceGrouping = rs.data.data || [];

          rs = await this.axios.get(`/tichhop/nms/rest/device/getWanInterfacesList?serialNumber=${this.serial_num}`)
          this.cboWanInterfacesList.source = rs.data.data || []
          if (this.cboWanInterfacesList.source.length > 0) {
            this.cboWanInterfacesList.value = this.cboWanInterfacesList.source[0].id;
          }

          rs = await this.axios.get(`/tichhop/nms/rest/device/getLanInterfacesList?serialNumber=${this.serial_num}`)
          this.cboLanInterfacesList.source = rs.data.data || []
          if (this.cboLanInterfacesList.source.length > 0) {
            this.cboLanInterfacesList.value = this.cboLanInterfacesList.source[0].id;
          }
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false);
      }
    },

    btnAddInterfaceGrouping_Click_1() {

    },

    btnDelInterfaceGrouping_Click_1() {

    },

    async btnGetVoice_Click() {
      try {
        this.loading(true);
        // this.serial_num = 'VNPT01bbfe05';
        let rs = await this.axios.get(`/tichhop/nms/rest/device/getVoice?optical=${this.serial_num}`);
        if (rs.data.error_code == 0) {
          let {SIPObject, LineObject} = rs.data;
          this.cboIpAddressFamily = '1';
          if (SIPObject != null) {
            this.txtUserAgentDomain = SIPObject.userAgentDomain;
            this.txtDigitMap = SIPObject.digitMap;
            this.txtProxyServer = SIPObject.proxyServer;
            this.txtProxyServerPort = SIPObject.proxyServerPort;
            if (SIPObject.proxyServer != "") {
              this.chkProxy = true;
            }

            this.txtRegistrarServer = SIPObject.registrarServer;
            this.txtRegistrarServerPort = SIPObject.registrarServerPort;
            if (SIPObject.registrarServer != "") {
              this.chkRegistrar = true;
            }

            this.txtOutBoundProxy = SIPObject.outBoundProxy;
            this.txtOutBoundProxyPort = SIPObject.outBoundProxyPort;
            if (SIPObject.registrarServer != "") {
              this.chkOutboundProxy = true;
            }

            if (SIPObject.BoundIfName != null && SIPObject.BoundIfName.length > 0) {
              let BoundIfName = SIPObject.BoundIfName;
              let source = [];
              if (BoundIfName != null && BoundIfName.length > 0) {
                BoundIfName.forEach(item => {
                  source.push({boundIfName: item});
                });
              }
              this.cboBoundIfName.source = source;
              this.cboBoundIfName.value = SIPObject.boundIfName;
            }

            this.cboRegion = SIPObject.region
          }

          if (LineObject != null && LineObject.length > 0) {
            if (LineObject[0] != null) {
              this.chkAccountEnable1 = LineObject[0].enable === "Enabled"
              this.lblStatus1 = LineObject[0].status;
              this.lblCallState1 = LineObject[0].callState;
              this.txtCallIDNamer1 = LineObject[0].callIDNamer;
              this.txtSipAuthUserName1 = LineObject[0].sipAuthUserName;
              this.txtSipAuthUserPass1 = LineObject[0].sipAuthUserPass;
              this.txtSipUri1 = LineObject[0].sipUri;
            }

            if (LineObject[1] != null) {
              this.chkAccountEnable2 = LineObject[1].enable === "Enabled"
              this.lblStatus2 = LineObject[1].status;
              this.lblCallState2 = LineObject[1].callState;
              this.txtCallIDNamer2 = LineObject[1].callIDNamer;
              this.txtSipAuthUserName2 = LineObject[1].sipAuthUserName;
              this.txtSipAuthUserPass2 = LineObject[1].sipAuthUserPass;
              this.txtSipUri2 = LineObject[1].sipUri;
            }
          }
        } else {
          this.$root.$toast.error("Lỗi API: " + rs.data.message);
        }
      } catch (ex) {
        console.log(ex);
        this.$root.$toast.error("Có lỗi: " + ex);
      } finally {
        this.loading(false);
      }
    },

    btnSetVoice_Click() {
      this.$confirm(
        `Bạn có muốn thực hiện chức năng này không?`,
        "Thông báo",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }
      ).then(async () => {
        this.loading(true);
        // this.serial_num = 'VNPT008b8e3b';
        try {
          let _voiceIms = Const.ResVoice();
          let _SIPObj = Const.ResSIPObject();
          let _LINEObj = [];
          let _LINEObj_1 = Const.ResLineObject();
          let _LINEObj_2 = Const.ResLineObject();

          _SIPObj.enableVoiceService = true;
          _SIPObj.boundIfName = this.cboBoundIfName.value;
          _SIPObj.ipAddressFamily = "1";
          _SIPObj.region = this.cboRegion.value;
          _SIPObj.digitMap = this.txtDigitMap;
          _SIPObj.userAgentDomain = this.txtUserAgentDomain;
          _SIPObj.proxyServer = this.txtProxyServer;
          _SIPObj.proxyServerPort = this.txtProxyServerPort;
          _SIPObj.registrarServer = this.txtRegistrarServer;
          _SIPObj.registrarServerPort = this.txtRegistrarServerPort;
          _SIPObj.outBoundProxy = this.txtOutBoundProxy;
          _SIPObj.outBoundProxyPort = this.txtOutBoundProxyPort;

          _LINEObj_1.id = 1;
          _LINEObj_1.callIDNamer = this.txtCallIDNamer1;
          _LINEObj_1.sipAuthUserName = this.txtSipAuthUserName1;
          _LINEObj_1.sipAuthUserPass = this.txtSipAuthUserPass1;
          _LINEObj_1.sipUri = this.txtSipUri1;
          _LINEObj.push(_LINEObj_1);

          _LINEObj_2.id = 2;
          _LINEObj_2.callIDNamer = this.txtCallIDNamer2;
          _LINEObj_2.sipAuthUserName = this.txtSipAuthUserName2;
          _LINEObj_2.sipAuthUserPass = this.txtSipAuthUserPass2;
          _LINEObj_2.sipUri = this.txtSipUri2;
          _LINEObj.push(_LINEObj_2);

          _voiceIms.SIPObject = _SIPObj;
          _voiceIms.LineObject = _LINEObj;

          this.axios.post(`/tichhop/nms/rest/device/setVoice?optical=${this.serial_num}`, _voiceIms)
            .then(rs => {
              rs.data.error_code == 0 ? this.$root.$toast.success('Thành công') : this.$root.$toast.error(rs.data.message);
            })
            .catch(err => {
              console.log(err);
              this.$root.$toast.error("Lỗi API: " + err.data.error);
            })
            .finally(() => {
              this.loading(false);
            });
        } catch (ex) {
          console.log(ex);
          this.$root.$toast.error("Có lỗi: " + ex);
        } finally {
          this.loading(false);
        }
      });
    },

    async mapData(id_neo, table, dk) {
      try {
        let rs = await this.axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', {id_neo, table, dk});
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Khong load duoc ky hoa don');
      }

      return null;
    },

    async LAY_THAMSO_MACDINH(vma_ts) {
      try {
        let rs = await API.lay_thamso_macdinh(this.axios, {
          vma_ts,
          vnguoidung_id: this.$root.token.getNguoiDungID()
        });

        return this.getDataFromAPI(rs);
      } catch (e) {

      }
      return null;
    },

    async getPortByMaTB(ma_tb) {
      let rs = await this.axios.post('/web-ccdv/TestPortV2/lay_port_theomatb', {
        ma_tb,
        "kieu": 2
      })

      return this.getDataFromAPI(rs);
    },

    getDataFromAPI(rs) {
      if (rs.data != null && rs.data.data != null && (typeof rs.data.data == 'number' || rs.data.data.length > 0)) {
        return rs.data.data;
      }

      return null;
    },

    async resetPort(txtTraCuu) {
      this.loading(true);
      try {
        let tinh_id = this.$root.token.getPhanVungID();
        let vuser_alt = await this.mapData("user_alt", DBConstants.DB2 + ".tinh", "where tinh_id = " + tinh_id);
        let vpass_alt = await this.mapData("pass_alt", DBConstants.DB2 + ".tinh", "where tinh_id = " + tinh_id);
        let vuser_xtest = await this.mapData("user_xtest", DBConstants.DB2 + ".tinh", "where tinh_id = " + tinh_id);
        let vpass_xtest = await this.mapData("pass_xtest", DBConstants.DB2 + ".tinh", "where tinh_id = " + tinh_id);

        if (vuser_alt == "" || vuser_alt == "-1") vuser_alt = "";
        if (vpass_alt == "" || vpass_alt == "-1") vpass_alt = "";
        if (vuser_xtest == "" || vuser_xtest == "-1") {
          vuser_xtest = "";
        }

        if (vpass_xtest == "" || vpass_xtest == "-1") {
          vpass_xtest = "";
        }

        let _show_SERVER_DK = await this.LAY_THAMSO_MACDINH('SHOW_SERVER_DK');
        let iServer_DK = 0;
        try {
          _show_SERVER_DK = parseInt(_show_SERVER_DK);
        } catch (e) {
          _show_SERVER_DK = 0;
        }

        if (_show_SERVER_DK === 1) {
          // iServer_DK = Convert.ToInt32((tscboSRV_DoKiem.ComboBox.SelectedItem as DataRowView).Row["server_id"]);
          iServer_DK = 1;
        } else {
          try {
            iServer_DK = parseInt(await this.LAY_THAMSO_MACDINH('SERVER_DK'));
          } catch (e) {
            iServer_DK = 1;
          }
        }

        if (iServer_DK == 1) {
          if (vuser_alt == "" || vuser_alt == "-1" || vpass_alt == "" || vpass_alt == "-1") {
            this.$root.$toast.error("Không có thông tin user kết nối");
            return;
          }
        } else {
          if (vpass_xtest == "" || vuser_xtest == "-1" || vuser_xtest == "" || vuser_xtest == "-1") {
            this.$root.$toast.error("Không có thông tin user kết nối");
            return;
          }
        }


        let vip = "";
        let vport_visa = "";
        let vma_tb = txtTraCuu;
        let vInput = "";
        let vloaitbi_id = "1";
        let thamso = "7";
        let vonu_id = '';
        let vdoitac_id = '';
        let vloai_gpon = '';
        let vport_bnm = '';

        let dtPort = await this.getPortByMaTB(vma_tb);
        if (dtPort != null && dtPort.length > 0) {
          vip = dtPort[0]["ip"];
          vport_visa = dtPort[0]["port_visa"];
          vonu_id = dtPort[0]["onu_id"];
          vInput = vip + ":" + vport_visa;
          vloaitbi_id = dtPort[0]["loaitbi_id"];
          thamso = dtPort[0]["thamso"];

          if (('doitac_id' in dtPort[0])) {
            vdoitac_id = dtPort[0]["doitac_id"];
          }

          if (vonu_id != "") {
            vInput += "/" + vonu_id;
          }

          if (iServer_DK == 3 && ('loai_gpon' in dtPort[0])) {
            vloai_gpon = dtPort[0]["loai_gpon"];
            vport_bnm = dtPort[0]["port_bnm"];
            vInput = vip + ":" + vport_bnm + ":" + vloai_gpon + ":" + vloaitbi_id + ":" + vdoitac_id;
          }

          if (vip == "") {
            this.$root.$toast.error("Thuê bao chưa được cấp port");
            return;
          }
        } else {
          this.$root.$toast.error("Thuê bao chưa được cấp port");
          return;
        }


        if (vInput == "") {
          this.$root.$toast.error("Không có account hoặc port để thực hiện");
          return;
        }

        let rs = await this.axios.post('/tichhop/bnm/toolResetOnePort', {
          "input": vInput,
          "linetestPassword": iServer_DK === 1 ? vpass_alt : vpass_xtest,
          "linetestUser": (iServer_DK === 1 ? vuser_alt : vuser_xtest) + "'" + iServer_DK + "'" + vloaitbi_id + "'" + thamso
        });

        if (rs.data.errorCode === 0) {
          this.$root.$toast.success('Thành công');
        } else {
          this.$root.$toast.error(rs.data.faultString || rs.data.data);
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    }
  }
}
</script>

<style scoped>
/deep/ .modal-body .page-content {
  position: inherit;
}

.result {
  border: 1px solid darkgray;
  border-radius: 5px;
  margin-top: 10px;
  height: 300px;
  overflow: scroll;
  padding: 10px
}

.w200 {
  width: 200px
}

.w250 {
  width: 250px
}

.w-48 {
  width: 48% !important;
}
</style>
