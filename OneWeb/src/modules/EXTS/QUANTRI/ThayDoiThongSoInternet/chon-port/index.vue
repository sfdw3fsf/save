<template src="./index.template.html"></template>
<script>
import ThueBaoCungCap from '../ThueBaoCungCap'
import ChonThueBao from './chon-thuebao'
import BssErrorMarker from '@/components/BssErrorMarker'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import { required } from 'vuelidate/lib/validators'

export default {
  components: { ChonThueBao, BssErrorMarker, ThueBaoCungCap, SearchAccount },
  validations: {
    txtOnuPass: {
      Text: {
        required
      }
    }
  },
  data() {
    return {
      dsTraCuuTB: [],
      ChiCanChonDSLAM: false,
      ThueBaoCungCapData: null,
      madoicap: 0,
      tramvt_id: 0,
      tramtb_id: 0,
      tramql_id: 0,
      port_id: 0,
      vci_vpi_id: 0,
      kieu: 0,
      loaitb_id: 0,
      diachi_id: 0,
      vtham_so: '',
      dichvuvt_id: 0,
      hdtb_id: 0,
      thuebao_id: 0,
      _chedoChonSP: 0,
      ds_port_duoc_chon: [],
      port_td: 1,
      quytrinhId: 0,
      ten_congnghe: '',
      cboCongNghe: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboTrangthai: {
        Key: 1,
        DataSource: [],
        Value: null
      },
      cboDonViQLTK: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboTramVT: {
        Key: 1,
        Value: null,
        Text: null
      },
      cboToQL: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboPhuong: {
        DataSource: [],
        Value: null,
        Enabled: false,
        EnableChangeEvent: true
      },
      cboNgo: {
        DataSource: [],
        Value: null,
        Enabled: false,
        EnableChangeEvent: true
      },
      cboKhu: {
        DataSource: [],
        Value: null,
        Text: null,
        Enabled: false,
        EnableChangeEvent: true
      },
      cboDuongPho: {
        DataSource: [],
        Value: null,
        Text: null,
        Enabled: false,
        EnableChangeEvent: true
      },
      cboVetinh: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboDslam: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboDownUpLink: {
        DataSource: [],
        Value: null
      },
      cboKetCuoi: {
        DataSource: [],
        Value: null,
        Enabled: false,
        EnableChangeEvent: true
      },
      cboViTri: {
        Key: 1,
        DataSource: [],
        Value: null,
        Enabled: false
      },
      cboQuyHoach: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboLoaiQH: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboTTPortQH: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboTTVlanQH: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      cboMethod: {
        Key: 1,
        DataSource: [],
        Value: null,
        EnableChangeEvent: true
      },
      chkKetCuoi: {
        Checked: false,
        EnableChangeEvent: true
      },
      chkViTri: {
        Checked: false
      },
      chkPhuongXa: {
        Checked: false,
        EnableChangeEvent: true
      },
      chkAp: {
        Enabled: false,
        Checked: false,
        EnableChangeEvent: true
      },
      chkKhu: {
        Enabled: false,
        Checked: false,
        EnableChangeEvent: true
      },
      chkPho: {
        Enabled: false,
        Checked: false,
        EnableChangeEvent: true
      },
      txtPort: {
        Text: null
      },
      txtRack: {
        Text: null
      },
      txtShelf: {
        Text: null
      },
      txtSlot: {
        Text: null
      },
      txtSystem: {
        Text: null
      },
      txtVci: {
        Text: null
      },
      txtVpi: {
        Text: null
      },
      txtIP: {
        Text: null
      },
      txtOnuPass: {
        Text: null
      },
      txtAdsl_Port: {
        Text: null
      },
      txtTKVlan: {
        Text: null
      },
      txtThuebao: {
        Text: null
      },
      txtKetCuoi: {
        Text: null
      },
      gridDanhSach: {
        DataSource: [],
        DataSourceForFilter: []
      },
      gridDanhSach_Vci_Vpi: {
        DataSource: []
      },
      gridDanhSach_Port: {
        DataSource: [],
        DataSourceForFilter: []
      },
      tsbtnCapNhat: {
        Enabled: false
      },
      pnlPort_QuyHoach: {
        Visible: false
      },
      pnlPortFix: {
        Visible: true
      },
      lblThongBaoTmp: {
        Visible: false,
        Text: ''
      },
      palThongTinPort: {
        Visible: true
      },
      LOAI_DV: {
        DONVIQL_LD: 3,
        TRAM_VT: 5,
        TRAM_PORT: 39
      },
      LOAI_TBI: {
        DSLAM: 1
      },
      DichVuVienThong: {
        TSL: 9,
        ADSL: 4
      },
      CHEDO_CHON_SPLITTER: {
        KoBatBuoc: 1, //Không bắt buộc phải chọn splitter
        BatBuocKhiDaChonSP: 2, //Chỉ bắt buộc chọn vị trí trên sp khi người dùng tick vào ô mà quên chưa tick chọn vị trí
        BatBuoc: 3 //Bắt buộc phải chọn splitter và vị trí nếu port được gán SP.
      },
      NHOMPHO: {
        DUONGPHO: 1,
        AP: 2,
        KHU: 3
      }
    }
  },
  computed: {
    chedoChonSP() {
      if (parseInt(this._chedoChonSP) == 1 && !this.chkKetCuoi.Checked) {
        this.chkKetCuoi.Checked = true
        this.chkViTri.Checked = true
      }

      switch (parseInt(this._chedoChonSP)) {
        case 1:
          return this.CHEDO_CHON_SPLITTER.BatBuoc
        case 2:
          return this.CHEDO_CHON_SPLITTER.BatBuocKhiDaChonSP
      }

      return this.CHEDO_CHON_SPLITTER.KoBatBuoc
    },
    IS_AON_TINH() {
      return !this.palThongTinPort.Visible
    }
  },
  watch: {
    'chkKhu.Checked': function (value) {
      if (this.chkKhu.EnableChangeEvent) {
        this.chkKhu_CheckedChanged(value)
      } else {
        this.chkKhu.EnableChangeEvent = true
      }
    },
    'chkAp.Checked': function (value) {
      if (this.chkAp.EnableChangeEvent) {
        this.chkAp_CheckedChanged(value)
      } else {
        this.chkAp.EnableChangeEvent = true
      }
    },
    'chkPho.Checked': function (value) {
      if (this.chkPho.EnableChangeEvent) {
        this.chkPho_CheckedChanged(value)
      } else {
        this.chkPho.EnableChangeEvent = true
      }
    },
    'chkPhuongXa.Checked': function (value) {
      if (this.chkPhuongXa.EnableChangeEvent) {
        this.chkPhuongXa_CheckedChanged(value)
      } else {
        this.chkPhuongXa.EnableChangeEvent = true
      }
    },
    'cboPhuong.Value': function (value) {
      if (this.cboPhuong.EnableChangeEvent) {
        this.cboPhuong_EditValueChanged(value)
      } else {
        this.cboPhuong.EnableChangeEvent = true
      }
    },
    'cboDuongPho.Value': async function (value) {
      if (this.cboDuongPho.EnableChangeEvent) {
        await this.cboDuongPho_EditValueChanged(value)
      } else {
        this.cboDuongPho.EnableChangeEvent = true
      }
    },
    'cboNgo.Value': async function (value) {
      if (this.cboNgo.EnableChangeEvent) {
        await this.cboNgo_EditValueChanged(value)
      } else {
        this.cboNgo.EnableChangeEvent = true
      }
    },
    'cboKhu.Value': async function (value) {
      if (this.cboKhu.EnableChangeEvent) {
        await this.cboKhu_EditValueChanged(value)
      } else {
        this.cboKhu.EnableChangeEvent = true
      }
    },
    'cboDonViQLTK.Value': async function (value) {
      if (this.cboDonViQLTK.EnableChangeEvent) {
        await this.cboDonViQLTK_SelectedValueChanged(value)
      } else {
        this.cboDonViQLTK.EnableChangeEvent = true
      }
    },
    'cboToQL.Value': async function (value) {
      if (this.cboToQL.EnableChangeEvent) {
        await this.cboToQL_SelectedValueChanged(value)
      } else {
        this.cboToQL.EnableChangeEvent = true
      }
    },
    'cboVetinh.Value': async function (value) {
      if (this.cboVetinh.EnableChangeEvent) {
        await this.cboVetinh_SelectedValueChanged(value)
      } else {
        this.cboVetinh.EnableChangeEvent = true
        await this.cboVetinh_SelectedValueChanged(value)
      }
    },
    'cboCongNghe.Value': async function (value) {
      if (this.cboCongNghe.EnableChangeEvent) {
        let item = this.cboCongNghe.DataSource.find((item) => item.congnghe_id == value)
        if (!(item == undefined || item == null)) {
          this.ten_congnghe = this.cboCongNghe.DataSource.find((item) => item.congnghe_id == value).congnghe
          await this.cboCongNghe_EditValueChanged(value)
        }
      } else {
        this.cboCongNghe.EnableChangeEvent = true
      }
    },
    'cboDslam.Value': async function (value) {
      if (this.cboDslam.EnableChangeEvent) {
        await this.cboDslam_SelectedValueChanged(value)
      } else {
        this.cboDslam.EnableChangeEvent = true
      }
    },
    'cboQuyHoach.Value': async function (value) {
      if (this.cboQuyHoach.EnableChangeEvent) {
        await this.cboQuyHoach_SelectedValueChanged(value)
      } else {
        this.cboQuyHoach.EnableChangeEvent = true
      }
    },
    'cboLoaiQH.Value': async function (value) {
      if (this.cboLoaiQH.EnableChangeEvent) {
        await this.cboLoaiQH_SelectedValueChanged(value)
      } else {
        this.cboLoaiQH.EnableChangeEvent = true
      }
    },
    'cboTTPortQH.Value': async function (value) {
      if (this.cboTTPortQH.EnableChangeEvent) {
        await this.cboTTPortQH_SelectedValueChanged(value)
      } else {
        this.cboTTPortQH.EnableChangeEvent = true
      }
    },
    'cboTTVlanQH.Value': async function (value) {
      if (this.cboTTVlanQH.EnableChangeEvent) {
        await this.cboTTVlanQH_SelectedValueChanged(value)
      } else {
        this.cboTTVlanQH.EnableChangeEvent = true
      }
    },
    'cboMethod.Value': async function (value) {
      if (this.cboMethod.EnableChangeEvent) {
        await this.cboMethod_SelectedValueChanged(value)
      } else {
        this.cboMethod.EnableChangeEvent = true
      }
    },
    'chkKetCuoi.Checked': async function (value) {
      if (this.chkKetCuoi.EnableChangeEvent) {
        await this.chkKetCuoi_CheckedChanged(value)
      } else {
        this.chkKetCuoi.EnableChangeEvent = true
      }
    },
    'cboKetCuoi.Value': async function (value) {
      if (this.cboKetCuoi.EnableChangeEvent) {
        await this.cboKetCuoi_EditValueChanged(value)
      } else {
        this.cboKetCuoi.EnableChangeEvent = true
      }
    },
    'chkViTri.Checked': function (value) {
      this.chkViTri_CheckedChanged(value)
    }
  },
  methods: {
    show() {
      return this.$refs['ref-modal'].show()
    },
    hide() {
      this.$refs['ref-modal'].hide()
    },
    frmChonPort(_donvi_id, _vtramtb_id, _port_id, _kieu, _dichvuvt_id, _loaitb_id, _diachi_id, _thamso, _hdtb_id, _thuebao_id, _madoicap, _tramql_id, _quytrinh_id, _congnghe_id) {
      this.tramvt_id = _donvi_id
      this.tramtb_id = _vtramtb_id
      this.port_id = _port_id
      this.kieu = _kieu
      this.loaitb_id = _loaitb_id
      this.diachi_id = _diachi_id
      this.vtham_so = _thamso
      this.dichvuvt_id = _dichvuvt_id
      this.hdtb_id = _hdtb_id
      this.thuebao_id = _thuebao_id
      this.madoicap = _madoicap
      this.tramql_id = _tramql_id
      this.quytrinhId = _quytrinh_id
      this.cboCongNghe.Value = _congnghe_id
      this.ten_congnghe = ''
      if (!(this.cboCongNghe.DataSource == null || this.cboCongNghe.DataSource == undefined)) {
        let item = this.cboCongNghe.DataSource.find((item) => item.congnghe_id == this.cboCongNghe.Value)
        if (item != null) {
          this.ten_congnghe = item.congnghe
        }
      }
    },
    onModalHidden() {
      this.dsTraCuuTB = []
      this.ChiCanChonDSLAM = false
      this.port_td = 1
      this.tramvt_id = 0
      this.tramtb_id = 0
      this.tramql_id = 0
      this.port_id = 0
      this.kieu = 0
      this.loaitb_id = 0
      this.diachi_id = 0
      this.vtham_so = ''
      this.dichvuvt_id = 0
      this.hdtb_id = 0
      this.thuebao_id = 0
      this._chedoChonSP = 0
      this.ds_port_duoc_chon = []
      this.cboCongNghe.DataSource = []
      this.cboCongNghe.Value = null
      this.cboCongNghe.EnableChangeEvent = true
      this.cboTrangthai.DataSource = []
      this.cboTrangthai.Value = null
      this.cboDonViQLTK.DataSource = []
      this.cboDonViQLTK.Value = null
      this.cboDonViQLTK.EnableChangeEvent = true
      this.cboTramVT.Value = null
      this.cboTramVT.Text = null
      this.cboToQL.DataSource = []
      this.cboToQL.Value = null
      this.cboToQL.EnableChangeEvent = true
      this.cboPhuong.DataSource = []
      this.cboPhuong.Value = null
      this.cboPhuong.Enabled = false
      this.cboPhuong.EnableChangeEvent = true
      this.cboNgo.DataSource = []
      this.cboNgo.Value = null
      this.cboNgo.Enabled = false
      this.cboNgo.EnableChangeEvent = true
      this.cboKhu.DataSource = []
      this.cboKhu.Value = null
      this.cboKhu.Enabled = false
      this.cboKhu.EnableChangeEvent = true
      this.cboDuongPho.DataSource = []
      this.cboDuongPho.Value = null
      this.cboDuongPho.Enabled = false
      this.cboDuongPho.EnableChangeEvent = true
      this.cboVetinh.DataSource = []
      this.cboVetinh.Value = null
      this.cboVetinh.EnableChangeEvent = true
      this.cboDslam.DataSource = []
      this.cboDslam.Value = null
      this.cboDslam.EnableChangeEvent = true
      this.cboDownUpLink.DataSource = []
      this.cboDownUpLink.Value = null
      this.cboKetCuoi.DataSource = []
      this.cboKetCuoi.Value = null
      this.cboKetCuoi.Enabled = false
      this.cboKetCuoi.EnableChangeEvent = true
      this.cboViTri.DataSource = []
      this.cboViTri.Value = null
      this.cboViTri.Enabled = false
      this.cboQuyHoach.DataSource = []
      this.cboQuyHoach.Value = null
      this.cboQuyHoach.EnableChangeEvent = true
      this.cboLoaiQH.DataSource = []
      this.cboLoaiQH.Value = null
      this.cboLoaiQH.EnableChangeEvent = true
      this.cboTTPortQH.DataSource = []
      this.cboTTPortQH.Value = null
      this.cboTTPortQH.EnableChangeEvent = true
      this.cboTTVlanQH.DataSource = []
      this.cboTTVlanQH.Value = null
      this.cboTTVlanQH.EnableChangeEvent = true
      this.cboMethod.DataSource = []
      this.cboMethod.Value = null
      this.cboMethod.EnableChangeEvent = true
      this.chkKetCuoi.Checked = false
      this.chkKetCuoi.EnableChangeEvent = true
      this.chkViTri.Checked = false
      this.chkPhuongXa.Checked = false
      this.chkPhuongXa.EnableChangeEvent = true
      this.chkAp.Enabled = false
      this.chkAp.Checked = false
      this.chkAp.EnableChangeEvent = true
      this.chkKhu.Enabled = false
      this.chkKhu.Checked = false
      this.chkKhu.EnableChangeEvent = true
      this.chkPho.Enabled = false
      this.chkPho.Checked = false
      this.chkPho.EnableChangeEvent = true
      this.txtPort.Text = null
      this.txtRack.Text = null
      this.txtShelf.Text = null
      this.txtSlot.Text = null
      this.txtSystem.Text = null
      this.txtVci.Text = null
      this.txtVpi.Text = null
      this.txtIP.Text = null
      this.txtOnuPass.Text = null
      this.txtAdsl_Port.Text = null
      this.txtTKVlan.Text = null
      this.txtThuebao.Text = null
      this.txtKetCuoi.Text = null
      this.gridDanhSach.DataSource = []
      this.gridDanhSach.DataSourceForFilter = []
      this.gridDanhSach_Vci_Vpi.DataSource = []
      this.gridDanhSach_Port.DataSource = []
      this.gridDanhSach_Port.DataSourceForFilter = []
      this.tsbtnCapNhat.Enabled = false
      this.pnlPort_QuyHoach.Visible = false
      this.pnlPortFix.Visible = true
      this.lblThongBaoTmp.Visible = false
      this.lblThongBaoTmp.Text = ''
      this.palThongTinPort.Visible = true
      this.$v.$reset()
    },
    onModalShown() {
      ++this.cboDonViQLTK.Key
      ++this.cboToQL.Key
      ++this.cboTrangthai.Key
      ++this.cboVetinh.Key
      ++this.cboCongNghe.Key
      ++this.cboDslam.Key
      ++this.cboViTri.Key
      setTimeout(() => this.frmChonPort_FormLoad(), 300)
    },
    getColumnTemplateChon(parent) {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    :value="data.key"
                                    v-model="parent.ds_port_duoc_chon"/>
                                <span class="name"></span>
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
    async frmChonPort_FormLoad() {
      try {
        this.loading(true)
        let dsTS = await this.lay_ds_thamso_md_mats('CHON_VITRI_KETCUOI')

        if (dsTS.length > 0) {
          this._chedoChonSP = parseInt(dsTS[0]['ten_ts'])
        }

        this.cboCongNghe.DataSource = await this.lay_ds_congnghe()

        if (this.cboCongNghe.DataSource.length > 0) {
          let congNgheId = null

          if (this.hdtb_id != 0 && this.dichvuvt_id != 0) {
            congNgheId = await this.lay_congnghe_id_theo_hdtb(this.dichvuvt_id, this.hdtb_id)
          }

          if (!!congNgheId && this.cboCongNghe.DataSource.some((item) => item.congnghe_id == congNgheId)) {
            this.cboCongNghe.EnableChangeEvent = false
            this.cboCongNghe.Value = congNgheId
            this.ten_congnghe = this.cboCongNghe.DataSource.find((item) => item.congnghe_id == congNgheId).congnghe
          }
        }

        this.cboTrangthai.DataSource = await this.lay_ds_trangthai_port()

        if (this.cboTrangthai.DataSource.length > 0) {
          this.cboTrangthai.Value = 1 /*chưa sử dụng*/

          this.cboTTPortQH.DataSource = this.cboTrangthai.DataSource.map((item) => {
            return { ...item }
          })

          if (parseInt(this.cboTTPortQH.Value) != 1) {
            this.cboTTPortQH.EnableChangeEvent = false
            this.cboTTPortQH.Value = 1 /*chưa sử dụng*/
          }
        }

        this.cboTTVlanQH.DataSource = await this.lay_ds_trangthai_vlan_quyhoach()

        if (this.cboTTVlanQH.DataSource.length > 0) {
          if (parseInt(this.cboTTVlanQH.Value) != 1) {
            this.cboTTVlanQH.EnableChangeEvent = false
            this.cboTTVlanQH.Value = 1 /*chưa sử dụng*/
          }
        }

        this.HT_LoaiQuyHoach_Combobox()

        if (this.cboLoaiQH.DataSource[0].loaikn_id != parseInt(this.cboQuyHoach.Value)) {
          this.cboLoaiQH.EnableChangeEvent = false
          this.cboLoaiQH.Value = this.cboLoaiQH.DataSource[0].loaikn_id
        }

        this.HT_KieuQuyHoach_Combobox()

        if (this.cboQuyHoach.DataSource[0].quyhoach_id != parseInt(this.cboQuyHoach.Value)) {
          this.cboQuyHoach.EnableChangeEvent = false
          this.cboQuyHoach.Value = this.cboQuyHoach.DataSource[0].quyhoach_id
        }

        this.HT_PhuongThuc_Combobox()

        if (this.cboMethod.DataSource[0].method_id != parseInt(this.cboMethod.Value)) {
          this.cboMethod.EnableChangeEvent = false
          this.cboMethod.Value = this.cboMethod.DataSource[0].method_id
        }

        if (!!this.tramvt_id) {
          let donViInfo = await this.lay_donvi_theo_id(this.tramvt_id)

          if (!!donViInfo) {
            this.cboTramVT.Value = donViInfo.donvi_id
            this.cboTramVT.Text = donViInfo.ten_dv
          }
        }

        this.cboDonViQLTK.DataSource = await this.lay_donvi_theo_loaidv(this.LOAI_DV.DONVIQL_LD)

        if (!!this.tramql_id) {
          let iTramVT_TBi_ID = await this.lay_donvi_ql(this.tramql_id, this.LOAI_DV.TRAM_PORT, this.LOAI_DV.TRAM_VT)

          if (!!iTramVT_TBi_ID) {
            let in_dvql = await this.lay_dvcha_theo_dvcon(iTramVT_TBi_ID, this.LOAI_DV.DONVIQL_LD)

            if (!!in_dvql && this.cboDonViQLTK.DataSource.some((item) => item.donvi_id == in_dvql)) {
              this.cboDonViQLTK.EnableChangeEvent = false
              this.cboDonViQLTK.Value = in_dvql
              this.cboToQL.DataSource = await this.lay_dvcon_cua_dvcha_theo_nhieu_loai_dv(this.cboDonViQLTK.Value, this.LOAI_DV.TRAM_VT)

              if (this.cboToQL.DataSource.some((item) => item.donvi_id == iTramVT_TBi_ID)) {
                this.cboToQL.EnableChangeEvent = false
                this.cboToQL.Value = iTramVT_TBi_ID
              }
            }
          }
        } else if (!!this.tramvt_id) {
          let in_dvql = await this.lay_dvcha_theo_dvcon(this.tramvt_id, this.LOAI_DV.DONVIQL_LD)

          if (!!in_dvql && this.cboDonViQLTK.DataSource.some((item) => item.donvi_id == in_dvql)) {
            this.cboDonViQLTK.EnableChangeEvent = false
            this.cboDonViQLTK.Value = in_dvql
            this.cboToQL.DataSource = await this.lay_dvcon_cua_dvcha_theo_nhieu_loai_dv(this.cboDonViQLTK.Value, this.LOAI_DV.TRAM_VT)

            if (this.cboToQL.DataSource.some((item) => item.donvi_id == this.tramvt_id)) {
              this.cboToQL.EnableChangeEvent = false
              this.cboToQL.Value = this.tramvt_id
            }
          }
        }

        if (!this.cboDonViQLTK.Value) {
          this.cboDonViQLTK.EnableChangeEvent = false
          this.cboDonViQLTK.Value = this.cboDonViQLTK.DataSource[0].donvi_id

          this.cboToQL.DataSource = await this.lay_dvcon_cua_dvcha_theo_nhieu_loai_dv(this.cboDonViQLTK.Value, this.LOAI_DV.TRAM_VT)

          if (this.cboToQL.DataSource.length > 0) {
            this.cboToQL.EnableChangeEvent = false
            this.cboToQL.Value = this.cboToQL.DataSource[0].donvi_id
          }
        }

        if (!!this.diachi_id && this.diachi_id != -1 && this.diachi_id != 0) {
          let diachi = await this.lay_thongtin_diachi(this.diachi_id)

          if (!!diachi && !!diachi['quan_id']) {
            this.cboPhuong.DataSource = await this.lay_ds_phuong(diachi['quan_id'])

            if (!!diachi['phuong_id'] && this.cboPhuong.DataSource.some((item) => item.phuong_id == diachi['phuong_id'])) {
              this.chkPhuongXa.EnableChangeEvent = false
              this.chkPhuongXa.Checked = true
              this.cboPhuong.Enabled = true
              this.chkPho.Enabled = true
              this.chkAp.Enabled = true
              this.chkKhu.Enabled = true

              this.cboPhuong.EnableChangeEvent = false
              this.cboPhuong.Value = diachi['phuong_id']

              setTimeout(() => {
                this.$refs['ref-combo-phuong'].$children[0].selectRowByRowData(diachi['phuong_id'])
              }, 1000)

              this.cboDuongPho.DataSource = await this.lay_ds_pho(diachi['phuong_id'], this.NHOMPHO.DUONGPHO)

              if (!!diachi['pho_id'] && this.cboDuongPho.DataSource.some((item) => item.pho_id == diachi['pho_id'])) {
                this.chkPho.EnableChangeEvent = false
                this.chkPho.Checked = true
                this.cboDuongPho.Enabled = true
                this.cboDuongPho.EnableChangeEvent = false
                this.cboDuongPho.Value = diachi['pho_id']

                setTimeout(() => {
                  this.$refs['ref-combo-pho'].$children[0].selectRowByRowData(diachi['pho_id'])
                }, 1000)
              }

              this.cboNgo.DataSource = await this.lay_ds_pho(diachi['phuong_id'], this.NHOMPHO.AP)

              if (!!diachi['ap_id'] && this.cboNgo.DataSource.some((item) => item.pho_id == diachi['ap_id'])) {
                this.chkAp.EnableChangeEvent = false
                this.chkAp.Checked = true
                this.cboNgo.Enabled = true
                this.cboNgo.EnableChangeEvent = false
                this.cboNgo.Value = diachi['ap_id']

                setTimeout(() => {
                  this.$refs['ref-combo-ngo'].$children[0].selectRowByRowData(diachi['ap_id'])
                }, 1000)
              }

              this.cboKhu.DataSource = await this.lay_ds_pho(diachi['phuong_id'], this.NHOMPHO.KHU)

              if (!!diachi['khu_id'] && this.cboKhu.DataSource.some((item) => item.pho_id == diachi['khu_id'])) {
                this.chkKhu.EnableChangeEvent = false
                this.chkKhu.Checked = true
                this.cboKhu.Enabled = true
                this.cboKhu.EnableChangeEvent = false
                this.cboKhu.Value = diachi['khu_id']

                setTimeout(() => {
                  this.$refs['ref-combo-khu'].$children[0].selectRowByRowData(diachi['khu_id'])
                }, 1000)
              }
            }
          }
        }

        this.cboVetinh.EnableChangeEvent = false
        await this.ht_tram_thietbi()

        if (this.cboVetinh.DataSource.length > 0) {
          let veTinhId = this.tramtb_id
          let dslamtinhid = null

          if (!!this.port_id && this.port_id != 0) {
            dslamtinhid = await this.lay_dslam_id_theo_port(this.port_id)

            if (!!dslamtinhid) {
              let tramtbid = await this.lay_donvi_id_theo_dslam(dslamtinhid)

              if (!!tramtbid) {
                veTinhId = tramtbid
              }
            }
          }

          let valueForSelecting = null

          if (!!veTinhId && this.cboVetinh.DataSource.some((item) => item.donvi_id == veTinhId)) {
            valueForSelecting = veTinhId + ''
          } else {
            valueForSelecting = this.cboVetinh.DataSource[0].donvi_id + ''
          }

          if (!this.cboVetinh.Value || parseInt(this.cboVetinh.Value) != parseInt(valueForSelecting)) {
            this.cboVetinh.EnableChangeEvent = false
            this.cboVetinh.Value = valueForSelecting
          }

          if (this.port_td != 1) {
            if (this.dichvuvt_id == this.DichVuVienThong.ADSL) {
              if (parseInt(this.cboLoaiQH.Value) != 4) {
                this.cboLoaiQH.EnableChangeEvent = false
                this.cboLoaiQH.Value = 4 /*fiber*/
              }
            } else {
              if (parseInt(this.cboLoaiQH.Value) != 1) {
                this.cboLoaiQH.EnableChangeEvent = false
                this.cboLoaiQH.Value = 1 /*nội tỉnh*/
              }
            }
          }

          let congnghe_id = !this.cboCongNghe.Value ? 0 : this.cboCongNghe.Value
          this.cboDslam.DataSource = await this.ht_dslam_theo_tramtb_id(this.cboVetinh.Value, congnghe_id, this.vtham_so)

          if (this.cboDslam.DataSource.length > 0) {
            if (!!dslamtinhid && this.cboDslam.DataSource.some((item) => item.dslam_id == dslamtinhid)) {
              this.cboDslam.Value = dslamtinhid
            } else {
              this.cboDslam.Value = this.cboDslam.DataSource[0].dslam_id
            }
          }
        }
      } finally {
        this.loading(false)
      }
    },
    async HienThiDanhSach(dslam_id, port_id, kieu, loaitb_id) {
      try {
        this.pnlPort_QuyHoach.Visible = false
        this.pnlPortFix.Visible = true

        this.loading(true)

        let dsPort = await this.lay_ds_port(dslam_id, port_id, kieu, loaitb_id)

        if (dsPort.length > 0) {
          dsPort.forEach((item, index) => (item.key = index + 1))
        }

        this.ds_port_duoc_chon = []
        this.gridDanhSach.DataSource = dsPort
        this.gridDanhSach.DataSourceForFilter = dsPort.map((x) => {
          return { ...x }
        })

        if (this.gridDanhSach.DataSource.length > 0) {
          this.tsbtnCapNhat.Enabled = true
        } else {
          this.tsbtnCapNhat.Enabled = false
          this.txtIP.Text = ''
          this.txtOnuPass.Text = ''
          this.txtAdsl_Port.Text = ''
          this.txtPort.Text = ''
          this.txtRack.Text = ''
          this.txtShelf.Text = ''
          this.txtSlot.Text = ''
          this.txtSystem.Text = ''
          this.txtVci.Text = ''
          this.txtVpi.Text = ''
        }
      } finally {
        this.loading(false)
      }
    },
    gridviewDanhSach_QueryCellInfo(args) {
      if (args.column.field == 'trangthai_port') {
        args.cell.style.color = `red`
      } else if (args.column.field == 'port') {
        args.cell.style.color = `blue`
      }

      if (['slot', 'port', 'port_mdf', 'trangthai_port'].includes(args.column.field)) {
        args.cell.style.fontWeight = 'bold'
      }
    },
    async tsbtnCapNhan_Click() {
      if (this.ChiCanChonDSLAM) {
        if (!this.cboDslam.Value) {
          this.$root.toastError('Bạn chưa chọn DSLAM!')
          return
        }

        if (this.IS_AON_TINH) {
          let dslam_tinh_id = !this.cboDslam.Value ? 0 : parseInt(this.cboDslam.Value)
          this.$emit('select', { IsAONTinh: true, dslam_tinh_id: dslam_tinh_id })
          this.hide()
          return
        }
      }

      let selectedRecords = null

      if (this.dsTraCuuTB.length > 0) {
        //TH search theo thuê bao thì không quan tâm port quy hoạch hay không
        selectedRecords = this.$refs['ref-grid-danh-sach'].grid.getSelectedRecords()

        if (!selectedRecords || selectedRecords.length == 0) {
          this.$root.toastError('Bạn chưa chọn Port !')
          return
        }
      } else if (this.port_td == 1) {
        selectedRecords = this.$refs['ref-grid-danh-sach'].grid.getSelectedRecords()

        if (!selectedRecords || selectedRecords.length == 0) {
          this.$root.toastError('Bạn chưa chọn Port !')
          return
        }
      } else {
        selectedRecords = this.$refs['ref-grid-danh-sach-quy-hoach'].grid.getSelectedRecords()

        if (!selectedRecords || selectedRecords.length == 0) {
          this.$root.toastError('Bạn chưa chọn Port !')
          return
        }

        let vciVpiSelectedRecords = this.$refs['ref-grid-danh-sach-vci-vpi'].grid.getSelectedRecords()

        if (!vciVpiSelectedRecords || vciVpiSelectedRecords.length == 0) {
          this.$root.toastError('Bạn chưa chọn VCI/VPI !')
          return
        }

        selectedRecords[0].vci_vpi_id = vciVpiSelectedRecords[0].vci_vpi_id
        selectedRecords[0].vpi = this.txtVpi.Text
        selectedRecords[0].vci = this.txtVci.Text
      }

      if (this.hdtb_id != null && this.thuebao_id != null) {
        const dskt = await this.kiemtra_chon_port(this.hdtb_id, this.thuebao_id, this.port_id, this.vci_vpi_id)
        if (dskt.length > 0) {
          this.$root.toastError('Port có thuê bao của khách hàng khác đang dùng : ' + dskt[0].MA_TB)
          return
        }
      }

      if (this.dichvuvt_id == this.DichVuVienThong.ADSL) {
        let dtDSL = []
        if (this.hdtb_id != 0) {
          dtDSL = await this.getThongTinTocDoADSL(this.hdtb_id, 1)
        } else {
          dtDSL = await this.getThongTinTocDoADSL(this.thuebao_id, 2)
        }
        if (dtDSL.length > 0) {
          if (this.hdtb_id != 0) {
            dtDSL = await this.getThongTinTocDoADSLTheoDslam(this.hdtb_id, 1, this.cboDslam.Value)
          } else {
            dtDSL = await this.getThongTinTocDoADSLTheoDslam(this.thuebao_id, 2, this.cboDslam.Value)
          }
          if (dtDSL.length == 0) {
            let l_doituong_id = 0
            if (this.hdtb_id != 0) {
              l_doituong_id = await this.getDoiTuongID(this.hdtb_id, 1)
            } else {
              l_doituong_id = await this.getDoiTuongID(this.thuebao_id, 2)
            }
            if (l_doituong_id == null) l_doituong_id = 0
            if (l_doituong_id != 374) {
              this.$root.toastError('Thuê bao thuộc gói Home TV XS, chỉ được chọn những thiết bị OLT hiệu suất thấp (Y/c từ ban KHCN - VNP) !')
            }
          }
        }
      }

      let port = { ...selectedRecords[0] }
      port.vadsl_port = this.txtAdsl_Port.Text
      port.onu_password = this.txtOnuPass.Text
      port.tramtb_id = this.cboVetinh.Value
      port.vitri = 0

      if (!!port.ketcuoi_id) {
        if ((this.chkKetCuoi.Checked && this.chedoChonSP == this.CHEDO_CHON_SPLITTER.BatBuocKhiDaChonSP) || this.chedoChonSP == this.CHEDO_CHON_SPLITTER.BatBuoc) {
          if (!this.cboViTri.Value) {
            this.$root.toastError('Bạn chưa chọn vị trí trên splitter!')
            return
          }

          port.vitri = parseInt(this.cboViTri.Value)
        }
      }

      port.ten_dslam = ''
      port.Thongtin_CT_Dslam = ''
      port.ip_dslam_tinh = '-1'
      port.thamso_dslam = '-1'
      port.congnghe_id = this.cboCongNghe.Value
      port.congnghe = this.ten_congnghe
      if (this.cboDslam.DataSource.length > 0 && !!this.cboDslam.Value) {
        let dslam = this.cboDslam.DataSource.find((item) => item.dslam_id == parseInt(this.cboDslam.Value))

        if (!!dslam) {
          port.Thongtin_CT_Dslam = dslam.thongtin_chitiet
          port.ten_dslam = dslam.tendslam
        }

        let dslamInfo = await this.lay_dslam_theo_id(this.cboDslam.Value)
        port.ip_dslam_tinh = dslamInfo['ip']
        port.thamso_dslam = dslamInfo['thamso']
      }

      port.dslam_id_vdc = 0
      port.bras_id_vdc = 0

      if (this.dichvuvt_id == this.DichVuVienThong.ADSL) {
        let tocDoInfo = await this.lay_tt_tocdo_thuebao(this.hdtb_id, this.thuebao_id, port.thamso_dslam)

        if (!!tocDoInfo) {
          port.dslam_id_vdc = tocDoInfo['dslam_id']
          port.bras_id_vdc = tocDoInfo['bras_id']
        } else {
          port.dslam_id_vdc = port.dslam_vdc_id

          let dslamInfo = await this.lay_dslam_theo_id(port.dslam_id_vdc)

          if (!!dslamInfo) {
            port.bras_id_vdc = dslamInfo['bras_id']
          }
        }
      } else {
        port.dslam_id_vdc = port.dslam_vdc_id

        let dslamInfo = await this.lay_dslam_theo_id(port.dslam_id_vdc)

        if (!!dslamInfo) {
          port.bras_id_vdc = dslamInfo['bras_id']
        }
      }
      this.$emit('select', port)
      this.hide()
    },
    async lay_tt_tocdo_thuebao(hdtb_id, thuebao_id, thamso_dslam) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_tt_tocdo_thuebao`, { hdtb_id, thuebao_id, thamso_dslam })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async kiemtra_chon_port(hdtb_id, thuebao_id, port_id, vci_vpi_id) {
      try {
        let response = await this.$root.context.post('/web-ecms/thong-so-internet/kiemtra_chon_port', {
          hdtb_id: hdtb_id,
          thuebao_id: thuebao_id,
          port_id: port_id,
          vci_vpi_id: vci_vpi_id
        })

        return response.data
      } catch (err) {
        this.$root.toastError('Có lỗi trong quá trình kiểm tra chọn PORT')
      }
      return []
    },
    async getThongTinTocDoADSL(id, kieu) {
      //1: p_id = hdtb_id; else: p_id = thuebao_id;
      try {
        let response = await this.$root.context.post('/web-ecms/thong-so-internet/getThongTinTocDoADSL', {
          id: id,
          kieu: kieu
        })

        return response.data
      } catch (err) {
        this.$root.toastError('Có lỗi trong quá trình lấy thông tin tốc độ')
      }
      return []
    },
    async getThongTinTocDoADSLTheoDslam(id, kieu, dslam_id) {
      //1: p_id = hdtb_id; else: p_id = thuebao_id;
      try {
        let response = await this.$root.context.post('/web-ecms/thong-so-internet/getThongTinTocDoADSLTheoDslam', {
          id: id,
          kieu: kieu,
          dslam_id: dslam_id
        })

        return response.data
      } catch (err) {
        this.$root.toastError('Có lỗi trong quá trình lấy thông tin tốc độ')
      }
      return []
    },
    async getDoiTuongID(id, kieu) {
      //1: p_id = hdtb_id; else: p_id = thuebao_id;
      try {
        let response = await this.$root.context.post('/web-ecms/thong-so-internet/getDoiTuongID', {
          id: id,
          kieu: kieu
        })

        return response.data.doituong_id
      } catch (err) {
        this.$root.toastError('Có lỗi trong quá trình lấy thông tin đối tượng')
      }
      return null
    },
    async tsbtnCapNhat_Click() {
      if (!this.tsbtnCapNhat.Enabled) {
        return
      }

      let errorMessage = []

      if (parseInt(this.cboTrangthai.Value) == 0) {
        errorMessage.push("Bạn không được chọn trạng thái là 'Tất cả' !")
      }

      if (this.ds_port_duoc_chon.length <= 0) {
        errorMessage.push('Bạn chưa chọn port trong danh sách để cập nhật trạng thái !')
      }

      if (errorMessage.length > 0) {
        this.$root.toastError(errorMessage.join('\n'))
        return
      }

      try {
        this.loading(true)

        for (let i = 0; i < this.ds_port_duoc_chon.length; i++) {
          let port = this.gridDanhSach.DataSource.find((item) => item.key == this.ds_port_duoc_chon[i])
          await this.capnhat_trangthai_port(port.port_id, port.vci_vpi_id, this.cboTrangthai.Value)
        }

        this.$root.toastSuccess('Cập nhật trạng thái port thành công !')
        this.Clean()
        await this.TimKiem_Port(0)
      } catch (err) {
        this.$root.toastError(err)
        return
      } finally {
        this.loading(false)
      }
    },
    async capnhat_trangthai_port(port_id, vci_vpi, trangthai) {
      let errorMessage = null

      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/capnhat_trangthai_port`, { port_id, vci_vpi, trangthai })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          if (response.data.kq == 0) {
            errorMessage = response.data.message
          }
        } else {
          errorMessage = 'Có lỗi xảy ra khi cập nhật trạng thái port'
        }
      } catch (err) {
        errorMessage = 'Có lỗi xảy ra khi cập nhật trạng thái port'
      }

      if (!!errorMessage) {
        throw errorMessage
      }
    },
    tsbtnSinhPass_Click() {
      let maTinh = this.$root.token.getMaTinh()

      if (!['BDG', 'DNI'].includes(maTinh)) {
        this.$root.toastError('Chức năng không được sử dụng cho tỉnh')
        return
      }

      let messages = []

      this.$v.$touch()

      if (!this.$v.txtOnuPass.Text.required) {
        messages.push('Hãy nhập Passonu')
        this.$refs['ref-txt-onu-pass'].focus()
      }

      let selectedRecords = this.$refs['ref-grid-danh-sach'].grid.getSelectedRecords()

      if (!selectedRecords || selectedRecords.length == 0) {
        messages.push('Hãy chọn port')
      }

      if (messages.length > 0) {
        this.$root.toastError(messages.join('\n'))
        return
      }

      this.$refs['ref-modal-xacnhan-sinh-pass'].show()
    },
    async okSinhPass() {
      let gridDanhSach = this.$refs['ref-grid-danh-sach'].grid
      let selectedRecord = gridDanhSach.getSelectedRecords()[0]

      let vrp_id = selectedRecord.rp_id
      let vvpi = selectedRecord.vpi
      let vonupass = this.txtOnuPass.Text

      try {
        this.loading(true)
        await this.capnhat_pass_onu(vrp_id, vvpi, vonupass)
        this.$v.$reset()
        this.$refs['ref-modal-xacnhan-sinh-pass'].hide()
        this.$root.toastSuccess('Cập nhật Passonu thành công!')
        let port = { ...selectedRecord }
        port.onu_password = vonupass
        gridDanhSach.batchUpdate({ changedRecords: [port] })
      } catch (err) {
        this.$root.toastError(err)
      } finally {
        this.loading(false)
      }
    },
    async capnhat_pass_onu(rp_id, vpi, pass_onu) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/capnhat_pass_onu`, { rp_id, vpi, pass_onu })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          if (response.data.kq == 1) {
            return
          }

          throw 'Có lỗi xảy khi cập nhật pass onu'
        }

        throw 'Có lỗi xảy khi cập nhật pass onu'
      } catch (err) {
        throw err
      }
    },
    tsbtnTBCungDoiCap_Click() {
      this.ThueBaoCungCapData = {
        thuebao_id: this.thuebao_id,
        dichvuvt_id: this.dichvuvt_id,
        ma_tb: null,
        hdtb_id: this.hdtb_id,
        madoicap: this.madoicap
      }
      this.$bvModal.show('frm-chon-port-thue-bao-cung-cap')
    },
    async cboPhuong_EditValueChanged(phuongId) {
      if (!phuongId) {
        return
      }

      this.cboDuongPho.DataSource = await this.lay_ds_pho(phuongId, this.NHOMPHO.DUONGPHO)

      if (this.cboDuongPho.DataSource.length <= 0) {
        if (this.chkPho.Checked) {
          this.chkPho.EnableChangeEvent = false
          this.chkPho.Checked = false
          this.cboDuongPho.Enabled = false
        }
      } else if (this.cboDuongPho.Value != this.cboDuongPho.DataSource[0].pho_id) {
        this.cboDuongPho.EnableChangeEvent = false
        this.cboDuongPho.Value = this.cboDuongPho.DataSource[0].pho_id

        setTimeout(() => {
          this.$refs['ref-combo-pho'].$children[0].selectRowByRowData(this.cboDuongPho.Value)
        }, 300)
      }

      this.cboNgo.DataSource = await this.lay_ds_pho(phuongId, this.NHOMPHO.AP)

      if (this.cboNgo.DataSource.length <= 0) {
        if (this.chkAp.Checked) {
          this.chkAp.EnableChangeEvent = false
          this.chkAp.Checked = false
          this.cboNgo.Enabled = false
        }
      } else if (this.cboNgo.Value != this.cboNgo.DataSource[0].pho_id) {
        this.cboNgo.EnableChangeEvent = false
        this.cboNgo.Value = this.cboNgo.DataSource[0].pho_id

        setTimeout(() => {
          this.$refs['ref-combo-ngo'].$children[0].selectRowByRowData(this.cboNgo.Value)
        }, 300)
      }

      this.cboKhu.DataSource = await this.lay_ds_pho(phuongId, this.NHOMPHO.KHU)

      if (this.cboKhu.DataSource.length <= 0) {
        if (this.chkKhu.Checked) {
          this.chkKhu.EnableChangeEvent = false
          this.chkKhu.Checked = false
          this.cboKhu.Enabled = false
        }
      } else if (this.cboKhu.Value != this.cboKhu.DataSource[0].pho_id) {
        this.cboKhu.EnableChangeEvent = false
        this.cboKhu.Value = this.cboKhu.DataSource[0].pho_id

        setTimeout(() => {
          this.$refs['ref-combo-khu'].$children[0].selectRowByRowData(this.cboKhu.Value)
        }, 300)
      }

      await this.ht_tram_thietbi()
    },
    async cboDuongPho_EditValueChanged(duongPhoId) {
      await this.ht_tram_thietbi()
    },
    async cboNgo_EditValueChanged(ngoId) {
      await this.ht_tram_thietbi()
    },
    async cboKhu_EditValueChanged(khuId) {
      await this.ht_tram_thietbi()
    },
    async lay_ds_pho(phuong_id, nhompho_id) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_ds_pho`, { phuong_id, nhompho_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    chkViTri_CheckedChanged(isCheck) {
      this.cboViTri.Enabled = isCheck
    },
    async chkPhuongXa_CheckedChanged(isCheck) {
      if (isCheck) {
        this.cboPhuong.Enabled = true

        this.chkPho.Checked = false
        this.chkPho.Enabled = true
        this.cboDuongPho.Enabled = false

        this.chkAp.Checked = false
        this.chkAp.Enabled = true
        this.cboNgo.Enabled = false

        this.chkKhu.Checked = false
        this.chkKhu.Enabled = true
        this.cboKhu.Enabled = false
      } else {
        this.cboPhuong.Enabled = false

        this.chkPho.Checked = false
        this.chkPho.Enabled = false
        this.cboDuongPho.Enabled = false

        this.chkAp.Checked = false
        this.chkAp.Enabled = false
        this.cboNgo.Enabled = false

        this.chkKhu.Checked = false
        this.chkKhu.Enabled = false
        this.cboKhu.Enabled = false
      }

      await this.ht_tram_thietbi()
    },
    async chkPho_CheckedChanged(isCheck) {
      this.cboDuongPho.Enabled = isCheck
      await this.ht_tram_thietbi()
    },
    async chkAp_CheckedChanged(isCheck) {
      this.cboNgo.Enabled = isCheck
      await this.ht_tram_thietbi()
    },
    async chkKhu_CheckedChanged(isCheck) {
      this.cboKhu.Enabled = isCheck
      await this.ht_tram_thietbi()
    },
    async cboCongNghe_EditValueChanged(congNgheId) {
      if (!congNgheId || !this.cboVetinh.Value) {
        return
      }

      this.cboDslam.Value = null
      this.cboDslam.DataSource = await this.ht_dslam_theo_tramtb_id(this.cboVetinh.Value, congNgheId, this.vtham_so)

      if (this.cboDslam.DataSource.length <= 0) {
        this.txtPort.Text = ''
        this.txtRack.Text = ''
        this.txtShelf.Text = ''
        this.txtSlot.Text = ''
        this.txtSystem.Text = ''
        this.txtVci.Text = ''
        this.txtVpi.Text = ''
        this.txtIP.Text = ''
        this.ds_port_duoc_chon = []
        this.gridDanhSach.DataSource = []
        this.gridDanhSach.DataSourceForFilter = []
      } else {
        this.cboDslam.Value = this.cboDslam.DataSource[0].dslam_id
      }
    },
    async chkKetCuoi_CheckedChanged(isChecked) {
      this.cboKetCuoi.Enabled = isChecked

      if (!isChecked) {
        if (this.port_td == 1) {
          this.gridDanhSach.DataSource = this.gridDanhSach.DataSourceForFilter.map((x) => {
            return { ...x }
          })
        } else {
          this.gridDanhSach_Port.DataSource = this.gridDanhSach_Port.DataSourceForFilter.map((x) => {
            return { ...x }
          })
        }

        return
      }

      if (!!this.cboKetCuoi.Value) {
        await this.cboKetCuoi_EditValueChanged(this.cboKetCuoi.Value)
      }
    },
    async cboKetCuoi_EditValueChanged(ketCuoiId) {
      if (!!ketCuoiId) {
        this.cboViTri.DataSource = await this.lay_ds_vitri_nguon(ketCuoiId)

        if (this.cboViTri.DataSource.length > 0) {
          this.cboViTri.Value = this.cboViTri.DataSource[0].id
        }
      }

      if (this.port_td == 1) {
        this.gridDanhSach.DataSource = this.gridDanhSach.DataSourceForFilter.filter((x) => x.ketcuoi_id == ketCuoiId).map((x) => {
          return { ...x }
        })
      } else {
        this.gridDanhSach_Port.DataSource = this.gridDanhSach_Port.DataSourceForFilter.filter((x) => x.ketcuoi_id == ketCuoiId).map((x) => {
          return { ...x }
        })
      }
    },
    async gridviewDanhSach_FocusedRowChanged(data) {
      if (!data) {
        return
      }

      this.txtSystem.Text = data['system']
      this.txtRack.Text = data['rack']
      this.txtSlot.Text = data['slot']
      this.txtShelf.Text = data['shelf']
      this.txtPort.Text = data['port']
      this.txtVpi.Text = data['vpi']
      this.txtVci.Text = data['vci']
      this.txtAdsl_Port.Text = data['adsl_port']
      this.txtOnuPass.Text = data['onu_password']

      if (!!data['port_id']) {
        this.port_id = data['port_id']
      }
      if (!!data['vci_vpi_id']) {
        this.vci_vpi_id = data['vci_vpi_id']
      }

      if (this.chedoChonSP != this.CHEDO_CHON_SPLITTER.KoBatBuoc) {
        if (this.chkKetCuoi.Checked) {
          let gridKetCuoi = this.$refs['ref-combo-ketcuoi'].$children[0].grid

          if (!data['ketcuoi_id']) {
            gridKetCuoi.clearSelection()
            this.cboViTri.DataSource = []
          } else if (data['ketcuoi_id'] != this.cboKetCuoi.Value) {
            if (this.cboKetCuoi.DataSource.some((item) => item.ketcuoi_id == data['ketcuoi_id'])) {
              this.cboKetCuoi.EnableChangeEvent = false
              let gridRowIndex = gridKetCuoi.getRowIndexByPrimaryKey(data['ketcuoi_id'])
              gridKetCuoi.selectRow(gridRowIndex)

              this.cboViTri.DataSource = await this.lay_ds_vitri_nguon(data['ketcuoi_id'])

              if (this.cboViTri.DataSource.length > 0) {
                this.cboViTri.Value = this.cboViTri.DataSource[0].id
              }
            } else {
              gridKetCuoi.clearSelection()
              this.cboViTri.DataSource = []
            }
          }
        }
      }
    },
    async gridviewDanhSach_Port_FocusedRowChanged(data) {
      if (!data) return

      this.txtPort.Text = data['port']
      this.txtSystem.Text = data['system']
      this.txtSlot.Text = data['slot']
      this.txtRack.Text = data['rack']
      this.txtShelf.Text = data['shelf']

      if (!!data['port_id']) {
        this.port_id = data['port_id']
      }
      // if (!!data["vci_vpi_id"]) {
      //     this.vci_vpi_id = data["vci_vpi_id"]
      // }

      if (this.chedoChonSP != this.CHEDO_CHON_SPLITTER.KoBatBuoc) {
        if (this.chkKetCuoi.Checked) {
          let gridKetCuoi = this.$refs['ref-combo-ketcuoi'].$children[0].grid

          if (!data['ketcuoi_id']) {
            gridKetCuoi.clearSelection()
            this.cboViTri.DataSource = []
          } else if (data['ketcuoi_id'] != this.cboKetCuoi.Value) {
            if (this.cboKetCuoi.DataSource.some((item) => item.ketcuoi_id == data['ketcuoi_id'])) {
              this.cboKetCuoi.EnableChangeEvent = false
              let gridRowIndex = gridKetCuoi.getRowIndexByPrimaryKey(data['ketcuoi_id'])
              gridKetCuoi.selectRow(gridRowIndex)

              this.cboViTri.DataSource = await this.lay_ds_vitri_nguon(data['ketcuoi_id'])

              if (this.cboViTri.DataSource.length > 0) {
                this.cboViTri.Value = this.cboViTri.DataSource[0].id
              }
            } else {
              gridKetCuoi.clearSelection()
              this.cboViTri.DataSource = []
            }
          }
        }
      }
    },
    gridviewDanhSach_Vci_Vpi_FocusedRowChanged(data) {
      if (!data) return

      this.txtVci.Text = data['vci']
      this.txtVpi.Text = data['vpi']
      if (!!data['vci_vpi_id']) {
        this.vci_vpi_id = data['vci_vpi_id']
      }
    },
    async lay_ds_vitri_nguon(ketcuoi_id) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ds_vitri_nguon`, { ketcuoi_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    acceptSearchAccount(item) {
      this.$refs['popupSearchAccount'].hide()

      if (!!item && !!item.ma_tb) {
        this.txtThuebao.Text = item.ma_tb
        this.txtThuebao_KeyPress()
      }
    },
    btnThueBao_Click() {
      this.$refs['popupSearchAccount'].show()
    },
    async txtTKVlan_KeyPress() {
      let dsTraCuuTB = []

      try {
        this.loading(true)
        dsTraCuuTB = await this.lay_ds_thuebao_theo_ma_tb(this.txtTKVlan.Text, 0)
      } finally {
        this.loading(false)
      }

      if (dsTraCuuTB.length == 0) {
        this.$root.toastError('Không tìm thấy thông tin thuê bao ' + this.txtThuebao.Text + ' trong danh bạ !')
        return
      }

      if (dsTraCuuTB.length == 1) {
        await this.onSelectThueBaoVlan(dsTraCuuTB[0])
      } else {
        let modal = this.$refs['ref-chon-thue-bao-vlan']
        modal.setDsThueBao(dsTraCuuTB)
        modal.show()
      }
    },
    async onSelectThueBaoVlan(data) {
      if (!data) return

      let l_vci_vpi_id = 0

      if (data['port_id'] != null && `${data['port_id']}` != '') {
        l_vci_vpi_id = parseInt(data['vci_vpi_id'])
      }

      await this.HienThiDanhSach_QuyHoach_vci_vpi(parseInt(this.cboDslam.Value), parseInt(this.cboQuyHoach.Value), parseInt(this.cboLoaiQH.Value), parseInt(this.cboTTVlanQH.Value), l_vci_vpi_id)
    },
    async HienThiDanhSach_QuyHoach_vci_vpi(_dslam_id, _quyhoach_id, _kieukn, _vtrangthai_id, _vci_vpi_id) {
      this.gridDanhSach_Vci_Vpi.DataSource = await this.Lay_ds_vci_vpi_quyhoach_v4(_dslam_id, _quyhoach_id, _kieukn, _vtrangthai_id, parseInt(this.cboMethod.Value), _vci_vpi_id)

      if (this.gridDanhSach_Vci_Vpi.DataSource.length == 0) {
        this.txtIP.Text = ''
        this.txtOnuPass.Text = ''
        this.txtAdsl_Port.Text = ''
        this.txtPort.Text = ''
        this.txtRack.Text = ''
        this.txtShelf.Text = ''
        this.txtSlot.Text = ''
        this.txtSystem.Text = ''
        this.txtVci.Text = ''
        this.txtVpi.Text = ''
        this.tsbtnCapNhat.Enabled = false
      }
    },
    async Lay_ds_vci_vpi_quyhoach_v4(vdslam_id, vquyhoach_id, vkieukn, vtrangthai_id, vhinhthuc_id, vvci_vpi_id) {
      var param = { vdslam_id, vquyhoach_id, vkieukn, vtrangthai_id, vhinhthuc_id, vvci_vpi_id }

      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_ds_vci_vpi_quyhoach_v4`, param)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async HienThiDanhSach_QuyHoach(_dslam_id, _port_id, i_kieu_id, _quyhoach_id, vtranthai_id) {
      this.gridDanhSach_Port.DataSource = await this.Lay_ds_port_quyhoach_v2(_dslam_id, _port_id, i_kieu_id, this.loaitb_id, _quyhoach_id, vtranthai_id)

      this.gridDanhSach_Port.DataSourceForFilter = this.gridDanhSach_Port.DataSource.map((x) => {
        return { ...x }
      })

      if (this.gridDanhSach_Port.DataSource.length == 0) {
        this.txtIP.Text = ''
        this.txtOnuPass.Text = ''
        this.txtAdsl_Port.Text = ''
        this.txtPort.Text = ''
        this.txtRack.Text = ''
        this.txtShelf.Text = ''
        this.txtSlot.Text = ''
        this.txtSystem.Text = ''
        this.txtVci.Text = ''
        this.txtVpi.Text = ''
        this.tsbtnCapNhat.Enabled = false
      }
    },
    async Lay_ds_port_quyhoach_v2(vdslam_id, vport_id, vkieu, vloaitb_id, vquyhoach_id, vtrangthai_id) {
      var param = { vdslam_id, vport_id, vkieu, vloaitb_id, vquyhoach_id, vtrangthai_id }

      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_ds_port_quyhoach_v2`, param)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async txtThuebao_KeyPress() {
      this.dsTraCuuTB = []

      try {
        this.loading(true)
        this.dsTraCuuTB = await this.lay_ds_thuebao_theo_ma_tb(this.txtThuebao.Text, 0)
      } finally {
        this.loading(false)
      }

      if (this.dsTraCuuTB.length == 0) {
        this.$root.toastError('Không tìm thấy thông tin thuê bao ' + this.txtThuebao.Text + ' trong danh bạ !')
        return
      }

      if (this.dsTraCuuTB.length == 1) {
        this.onSelectThueBao(this.dsTraCuuTB[0])
      } else {
        let modal = this.$refs['ref-chon-thue-bao']
        modal.setDsThueBao(this.dsTraCuuTB)
        modal.show()
      }
    },
    async onSelectThueBao(thueBaoInfo) {
      if (!!thueBaoInfo.ketcuoi_id) {
        let ketCuoiInfo = await this.lay_ketcuoi_theo_id(thueBaoInfo.ketcuoi_id)
        this.txtKetCuoi.Text = !ketCuoiInfo ? null : ketCuoiInfo.ten_kc
      }

      let l_port_id = thueBaoInfo['port_id']

      if (thueBaoInfo['dichvuvt_id'] == this.DichVuVienThong.TSL) {
        let kieuDvVaPort = await this.lay_kieudv_va_port_cua_tb(thueBaoInfo.thuebao_id, this.LOAI_DV.TRAM_VT, this.cboTramVT.Value)

        if (!kieuDvVaPort['kieudv_id']) {
          this.$root.toastError('Thuê bao không thuộc tổ thi công ' + this.cboTramVT.Text + ' !')
          return
        }

        l_port_id = kieuDvVaPort['port_id']
      }

      if (!l_port_id || l_port_id == -1) {
        this.$root.toastError('Không tìm thấy thông tin port của thuê bao ' + this.txtThuebao.Text + ' !')
        return
      }

      let dslamtinhid = await this.lay_dslam_id_theo_port(l_port_id)

      if (!dslamtinhid) {
        await this.HienThiDanhSach(this.cboDslam.Value, l_port_id, 3, thueBaoInfo['loaitb_id'])
        return
      }

      let tramtbid = await this.lay_donvi_id_theo_dslam(dslamtinhid)

      if (!!tramtbid && this.cboVetinh.DataSource.some((item) => item.donvi_id == tramtbid)) {
        if (this.cboVetinh.Value != tramtbid) {
          this.cboVetinh.EnableChangeEvent = false
          this.cboVetinh.Value = tramtbid
          let congnghe_id = !this.cboCongNghe.Value ? 0 : this.cboCongNghe.Value
          this.cboDslam.DataSource = await this.ht_dslam_theo_tramtb_id(tramtbid, congnghe_id, this.vtham_so)
        }

        if (this.cboDslam.DataSource.length == 0) {
          return
        }

        if (this.cboDslam.DataSource.some((item) => item.dslam_id == dslamtinhid)) {
          if (this.cboDslam.Value != dslamtinhid) {
            this.cboDslam.EnableChangeEvent = false
            this.cboDslam.Value = dslamtinhid

            // Lấy thông số DSLAM
            let dslamInfo = await this.lay_dslam_theo_id(this.cboDslam.Value)
            this.txtIP.Text = !dslamInfo ? '' : dslamInfo['ip']

            // Hiển thị danh sách spliter
            this.cboKetCuoi.DataSource = await this.lay_ds_spliter_theo_dslam(this.cboDslam.Value)
            this.cboKetCuoi.Enabled = false
            this.chkKetCuoi.Checked = false
            this.chkViTri.Checked = false
            this.cboViTri.Enabled = false

            // Hiển thị danh sách Downlink Uplink
            this.cboDownUpLink.DataSource = await this.lay_ds_down_uplink(this.cboDslam.Value)
          }

          await this.HienThiDanhSach(this.cboDslam.Value, l_port_id, 3, thueBaoInfo['loaitb_id'])
        } else {
          setTimeout(() => {
            this.$refs['ref-cbo-dslam'].$children[0].select2.select2('open')
          }, 200)
        }
      }
    },
    async lay_kieudv_va_port_cua_tb(thuebao_id, loaidv_id, donvi_id) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_kieudv_va_port_cua_tb`, { thuebao_id, loaidv_id, donvi_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async lay_ketcuoi_theo_id(ketcuoi_id) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ketcuoi_theo_id`, { ketcuoi_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async lay_ds_thuebao_theo_ma_tb(ma_tb, dichvuvt_id) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_ds_thuebao_theo_ma_tb`, { ma_tb, dichvuvt_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async cboDonViQLTK_SelectedValueChanged(donViQlTkId) {
      if (!!donViQlTkId) {
        this.cboToQL.DataSource = await this.lay_dvcon_cua_dvcha_theo_nhieu_loai_dv(donViQlTkId, this.LOAI_DV.TRAM_VT)

        if (this.cboToQL.DataSource.length > 0) {
          this.cboToQL.Value = this.cboToQL.DataSource[0].donvi_id
        }
      }
    },
    async cboToQL_SelectedValueChanged(toQlId) {
      if (!!toQlId) {
        await this.ht_tram_thietbi()
      }
    },
    async cboDslam_SelectedValueChanged(dslamId) {
      console.log('dslamId', dslamId)
      this.Clean()
      if (dslamId && this.quytrinhId == 8751) {
        // đường có sẵn
        let dsLam = this.cboDslam.DataSource.find((e) => e.dslam_id == dslamId)
        console.log('dsLam')
        console.log(dsLam)
        if (dsLam.tendslam.includes('ATA')) {
          this.$toast.error('Chỉ cấp thiết bị ATA cho kiểu lắp đặt trên đường riêng')
          this.cboDslam.Value = null
          return
        }
      }
      if (!dslamId) {
        this.cboKetCuoi.DataSource = []
        this.cboDownUpLink.DataSource = []
        this.ds_port_duoc_chon = []
        this.gridDanhSach.DataSource = []
        this.gridDanhSach.DataSourceForFilter = []
        this.tsbtnCapNhat.Enabled = false
        return
      }

      // Lấy thông số DSLAM
      let dslamInfo = await this.lay_dslam_theo_id(dslamId)

      if (!!dslamInfo) {
        this.txtIP.Text = dslamInfo['ip']
        this.port_td = parseInt(dslamInfo['port_td'])
        let iloaitbi = parseInt(dslamInfo['loaitbi_id'])

        if (this.$root.token.getPhanVungID() == 21 /*HNI*/) {
          if (iloaitbi == this.LOAI_TBI.DSLAM) {
            this.port_td = 1
          } else if (this.loaitb_id != 58) {
            this.port_td = 0
          }
        }
      } else {
        this.txtIP.Text = ''
        this.port_td = 1
      }

      // Kiểm tra 1 số thiết lập riêng từng tỉnh
      if (this.ChiCanChonDSLAM && !!dslamInfo && (typeof dslamInfo['doitac_id'] == 'undefined' || dslamInfo['doitac_id'] == null) && ['1', '2'].includes(`${dslamInfo['loaitbi_id']}`)) {
        /*
         * Nếu chọn dslam là dslam của tỉnh và dslam là thiết bị dùng cho AON
         * => Ẩn hết thông tin port, người dùng chỉ cần chọn dslam rồi nhấn "Cập nhật"
         */
        let maTinh = this.$root.token.getMaTinh()
        this.lblThongBaoTmp.Text = '*Lưu ý: Đối với thuê bao AON VNPT ' + maTinh + ', tổ kỹ thuật chỉ cần chọn DSLAM(Không cần cấp port) rồi giao phiếu về OMC.'
        this.lblThongBaoTmp.Visible = true

        this.pnlPort_QuyHoach.Visible = false
        this.pnlPortFix.Visible = false

        this.palThongTinPort.Visible = false

        return
      }

      // Hiển thị danh sách spliter
      if (this.chkKetCuoi.Checked) {
        this.chkKetCuoi.EnableChangeEvent = false
      }

      if (!!this.cboKetCuoi.Value) {
        this.cboKetCuoi.EnableChangeEvent = false
      }

      this.chkKetCuoi.Checked = false
      this.cboKetCuoi.Enabled = false
      this.cboKetCuoi.DataSource = await this.lay_ds_spliter_theo_dslam(dslamId)
      this.cboKetCuoi.Value = null
      this.chkViTri.Checked = false
      this.cboViTri.Enabled = false
      this.cboViTri.DataSource = []

      this.palThongTinPort.Visible = true
      this.lblThongBaoTmp.Visible = false
      this.pnlPort_QuyHoach.Visible = false
      this.pnlPortFix.Visible = true

      if (this.port_td == 0 && parseInt(this.cboTrangthai.Value) != 3 /*đã sử dụng*/ && parseInt(this.cboTrangthai.Value) != 2 /*tạm cấp*/) {
        // Lấy port theo quy hoạch, chỉ hiển thị khi trạng thái port là đã sử dụng
        this.pnlPort_QuyHoach.Visible = true
        this.pnlPortFix.Visible = false
      }

      if (this.port_td == 0) {
        if (this.dichvuvt_id == this.DichVuVienThong.ADSL) {
          if (parseInt(this.cboLoaiQH.Value) != 4) {
            this.cboLoaiQH.EnableChangeEvent = false
            this.cboLoaiQH.Value = 4 /*fiber*/
          }
        } else {
          if (parseInt(this.cboLoaiQH.Value) != 1) {
            this.cboLoaiQH.EnableChangeEvent = false
            this.cboLoaiQH.Value = 1 /*nội tỉnh*/
          }
        }

        await this.HienThiDanhSach_QuyHoach(dslamId, 0, this.kieu, parseInt(this.cboQuyHoach.Value), parseInt(this.cboTTPortQH.Value))

        await this.HienThiDanhSach_QuyHoach_vci_vpi(dslamId, parseInt(this.cboQuyHoach.Value), parseInt(this.cboLoaiQH.Value), parseInt(this.cboTTVlanQH.Value), 0)
      } else {
        await this.HienThiDanhSach(dslamId, 0, this.kieu, this.loaitb_id)
      }

      // Hiển thị danh sách Downlink Uplink
      this.cboDownUpLink.DataSource = await this.lay_ds_down_uplink(dslamId)
    },
    async cboQuyHoach_SelectedValueChanged(quyhoach_id) {
      if (!quyhoach_id) return

      if (this.port_td == 0) {
        // Theo quy hoạch
        if (parseInt(this.cboTTVlanQH.Value) != 1) {
          this.cboTTVlanQH.EnableChangeEvent = false
          this.cboTTVlanQH.Value = 1
        }

        if (parseInt(this.cboTTPortQH.Value) != 1) {
          this.cboTTPortQH.EnableChangeEvent = false
          this.cboTTPortQH.Value = 1 /*chưa sử dụng*/
        }

        await this.HienThiDanhSach_QuyHoach(parseInt(this.cboDslam.Value), 0, 0, quyhoach_id, parseInt(this.cboTTPortQH.Value))

        await this.HienThiDanhSach_QuyHoach_vci_vpi(parseInt(this.cboDslam.Value), quyhoach_id, parseInt(this.cboLoaiQH.Value), parseInt(this.cboTTVlanQH.Value), 0)
      }
    },
    async cboLoaiQH_SelectedValueChanged(loaiqh_id) {
      if (!loaiqh_id) return

      if (this.port_td == 0) {
        // Theo quy hoạch
        let quyhoach_id = parseInt(this.cboQuyHoach.Value)

        await this.HienThiDanhSach_QuyHoach(parseInt(this.cboDslam.Value), 0, 0, quyhoach_id, parseInt(this.cboTTPortQH.Value))

        await this.HienThiDanhSach_QuyHoach_vci_vpi(parseInt(this.cboDslam.Value), quyhoach_id, parseInt(loaiqh_id), parseInt(this.cboTTVlanQH.Value), 0)
      }
    },
    async cboMethod_SelectedValueChanged(method_id) {
      if (!method_id) return

      if (this.port_td == 0) {
        // Theo quy hoạch
        await this.HienThiDanhSach_QuyHoach_vci_vpi(parseInt(this.cboDslam.Value), parseInt(this.cboQuyHoach.Value), parseInt(this.cboLoaiQH.Value), parseInt(this.cboTTVlanQH.Value), 0)
      }
    },
    async cboTTPortQH_SelectedValueChanged(ttportqh_id) {
      if (!ttportqh_id) return

      if (this.port_td == 0) {
        // Theo quy hoạch
        await this.HienThiDanhSach_QuyHoach(parseInt(this.cboDslam.Value), 0, 0, parseInt(this.cboQuyHoach.Value), parseInt(ttportqh_id))
      }
    },
    async cboTTVlanQH_SelectedValueChanged(ttvlanqh_id) {
      if (!ttvlanqh_id) return

      if (this.port_td == 0) {
        await this.HienThiDanhSach_QuyHoach_vci_vpi(parseInt(this.cboDslam.Value), parseInt(this.cboQuyHoach.Value), parseInt(this.cboLoaiQH.Value), parseInt(ttvlanqh_id), 0)
      }
    },
    async lay_ds_down_uplink(dslam_id) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ds_down_uplink`, { dslam_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async lay_ds_spliter_theo_dslam(dslam_id) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ds_spliter_theo_dslam`, { dslam_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async lay_dslam_theo_id(dslam_id) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_dslam_theo_id`, { dslam_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async cboVetinh_SelectedValueChanged(veTinhid) {
      if (!veTinhid) {
        this.cboDslam.DataSource = []
        return
      }
      let congnghe_id = !this.cboCongNghe.Value ? 0 : this.cboCongNghe.Value
      this.cboDslam.DataSource = await this.ht_dslam_theo_tramtb_id(veTinhid, congnghe_id, this.vtham_so)

      if (this.cboDslam.DataSource.length > 0) {
        setTimeout(() => {
          this.$refs['ref-cbo-dslam'].$children[0].select2.select2('open')
        }, 200)
      }
    },
    tsbtnNhapMoi_Click() {
      this.Clean()
    },
    Clean() {
      this.txtSystem.Text = ''
      this.txtRack.Text = ''
      this.txtSlot.Text = ''
      this.txtIP.Text = ''
      this.txtShelf.Text = ''
      this.txtPort.Text = ''
      this.txtVpi.Text = ''
      this.txtVci.Text = ''
      this.txtAdsl_Port.Text = ''
      this.txtOnuPass.Text = ''
      this.txtTKVlan.Text = ''
      this.txtThuebao.Text = ''
      this.dsTraCuuTB = []
      this.txtKetCuoi.Text = ''
    },
    async txtRack_KeyPress() {
      try {
        this.loading(true)
        await this.TimKiem_Port(0)
      } finally {
        this.loading(false)
      }
    },
    async txtShelf_KeyPress() {
      try {
        this.loading(true)
        await this.TimKiem_Port(0)
      } finally {
        this.loading(false)
      }
    },
    async txtSlot_KeyPress() {
      try {
        this.loading(true)
        await this.TimKiem_Port(0)
      } finally {
        this.loading(false)
      }
    },
    async txtSystem_KeyPress() {
      try {
        this.loading(true)
        await this.TimKiem_Port(0)
      } finally {
        this.loading(false)
      }
    },
    async btnLayTTMoi_Click() {
      try {
        this.loading(true)
        await this.TimKiem_Port(1)
      } finally {
        this.loading(false)
      }
    },
    async TimKiem_Port(kieu_tk) {
      if (this.port_td == 0 && parseInt(this.cboTrangthai.Value) != 3 /*đã sử dụng*/ && parseInt(this.cboTrangthai.Value) != 2 /*tạm cấp*/) {
        this.pnlPort_QuyHoach.Visible = true
        this.pnlPortFix.Visible = false
        return
      } else {
        this.pnlPort_QuyHoach.Visible = false
        this.pnlPortFix.Visible = true
      }

      if (this.cboDslam.DataSource.length <= 0 || !this.cboDslam.Value) {
        this.$root.toastError('Hãy chọn Dslam!')
        return
      }

      let vdslam_id = this.cboDslam.Value
      let vslot = -1
      let vport = -1
      let vsystem = 0
      let vrack = -1
      let vshelf = -1
      let vvci = -1
      let vvpi = -1
      let vthamso = 0
      let vtrangthai = this.cboTrangthai.Value

      if (kieu_tk != 1) {
        if (!!this.txtSlot.Text) {
          vslot = this.txtSlot.Text
        }

        if (!!this.txtPort.Text) {
          vport = this.txtPort.Text
        }

        if (!!this.txtSystem.Text) {
          vsystem = this.txtSystem.Text
        }

        if (!!this.txtRack.Text) {
          vrack = this.txtRack.Text
        }

        if (!!this.txtShelf.Text) {
          vshelf = this.txtShelf.Text
        }

        if (!!this.txtVci.Text) {
          vvci = this.txtVci.Text
        }

        if (!!this.txtVpi.Text) {
          vvpi = this.txtVpi.Text
        }
      }

      let dsPort = await this.tracuu_ds_port(vdslam_id, vslot, vport, vsystem, vrack, vshelf, vvci, vvpi, vthamso, vtrangthai, this.loaitb_id)

      if (dsPort.length > 0) {
        dsPort.forEach((item, index) => (item.key = index + 1))
      }

      this.ds_port_duoc_chon = []
      this.gridDanhSach.DataSource = dsPort
      this.gridDanhSach.DataSourceForFilter = dsPort.map((x) => {
        return { ...x }
      })

      if (this.gridDanhSach.DataSource.length > 0) {
        this.tsbtnCapNhat.Enabled = true
      } else {
        this.tsbtnCapNhat.Enabled = false
        this.Clean()
      }
    },
    async tracuu_ds_port(dslam_id, slot, port, system, rack, shelf, vci, vpi, thamso, trangthai, loaitb_id) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/tracuu_ds_port`, { dslam_id, slot, port, system, rack, shelf, vci, vpi, thamso, trangthai, loaitb_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async lay_ds_port(dslam_id, port_id, kieu_id, loaitb_id) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_ds_port`, { dslam_id, port_id, kieu_id, loaitb_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async ht_dslam_theo_tramtb_id(donvi_id, congnghe_id, thamso) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/ht_dslam_theo_tramtb_id`, { donvi_id, congnghe_id, thamso })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async lay_donvi_theo_id(donvi_id) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_donvi_theo_id`, { donvi_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async lay_donvi_id_theo_dslam(dslam_id) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_dslam_theo_id`, { dslam_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data.donvi_id
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async lay_dslam_id_theo_port(port_id) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_dslam_id_theo_port`, { port_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data.dslam_id
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async ht_tram_thietbi() {
      let vphuong_id = 0
      let vpho_id = 0
      let vap_id = 0
      let vkhu_id = 0

      if (this.chkPhuongXa.Checked && !!this.cboPhuong.Value) {
        vphuong_id = parseInt(this.cboPhuong.Value)
      }

      if (this.chkPho.Checked && !!this.cboDuongPho.Value) {
        vpho_id = parseInt(this.cboDuongPho.Value)
      }

      if (this.chkAp.Checked && !!this.cboNgo.Value) {
        vap_id = parseInt(this.cboNgo.Value)
      }

      if (this.chkKhu.Checked && !!this.cboKhu.Value) {
        vkhu_id = parseInt(this.cboKhu.Value)
      }

      let tramtc_id = 0

      if (!!this.cboToQL.Value) {
        tramtc_id = parseInt(this.cboToQL.Value)
      } else {
        tramtc_id = parseInt(this.cboTramVT.Value)
      }
      this.cboVetinh.DataSource = await this.lay_ds_vetinh(tramtc_id, this.LOAI_DV.TRAM_PORT, vphuong_id, vpho_id, vap_id, vkhu_id)
      if (this.cboVetinh.DataSource.length <= 0) {
        this.cboVetinh.Value = null
        this.cboDslam.DataSource = []
        this.ds_port_duoc_chon = []
        this.gridDanhSach.DataSource = []
        this.gridDanhSach.DataSourceForFilter = []
      } else {
        this.cboVetinh.Value = this.cboVetinh.DataSource[0].donvi_id
      }
    },
    onSelectDownUpLink(data) {},
    onSelectKetCuoi(data) {
      this.cboKetCuoi.Value = !data ? null : data.ketcuoi_id
    },
    onSelectPhuong(data) {
      this.cboPhuong.Value = !data ? null : data.phuong_id
    },
    onSelectPho(data) {
      this.cboDuongPho.Value = !data ? null : data.pho_id
    },
    onSelectNgo(data) {
      this.cboNgo.Value = !data ? null : data.pho_id
    },
    onSelectKhu(data) {
      this.cboKhu.Value = !data ? null : data.pho_id
    },
    HT_KieuQuyHoach_Combobox() {
      this.cboQuyHoach.DataSource = [
        { quyhoach_id: 2, quyhoach: '2 lớp' },
        { quyhoach_id: 1, quyhoach: '1 lớp' }
      ]
    },
    HT_LoaiQuyHoach_Combobox() {
      this.cboLoaiQH.DataSource = [
        { loaikn_id: 1, ketnoi: 'Nội tỉnh' },
        { loaikn_id: 2, ketnoi: 'Liên tỉnh' },
        { loaikn_id: 3, ketnoi: 'Internet trực tiếp' },
        { loaikn_id: 4, ketnoi: 'Fiber' }
      ]
    },
    HT_PhuongThuc_Combobox() {
      this.cboMethod.DataSource = [
        { method_id: 0, method: 'PPPoE' },
        { method_id: 1, method: 'IPoE' }
      ]
    },
    async lay_ds_vetinh(donvi_id, loaidv_id, phuong_id, pho_id, ap_id, khu_id) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_ds_vetinh`, { donvi_id, loaidv_id, phuong_id, pho_id, ap_id, khu_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async lay_ds_phuong(quan_id) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ds_phuong`, { quan_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async lay_thongtin_diachi(diachi_id) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_thongtin_diachi`, { diachi_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async lay_dvcon_cua_dvcha_theo_nhieu_loai_dv(donvi_id, loaidv_id) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_dvcon_cua_dvcha_theo_nhieu_loai_dv`, { donvi_id, loaidv_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async lay_donvi_ql(donvi_id, loaidv_id, loaidv_ql_id) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_donvi_ql`, { donvi_id, loaidv_id, loaidv_ql_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data.donvi_ql_id
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async lay_dvcha_theo_dvcon(donvi_id, loaidv_id) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_dvcha_theo_dvcon`, { donvi_id, loaidv_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data.donvi_id
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async lay_donvi_theo_loaidv(loai_dv) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_donvi_theo_loaidv?loai_dv=${loai_dv}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async lay_ds_trangthai_port() {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ds_trangthai_port`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return [{ tt_port_id: 0, trangthai_port: '-- Tất cả --' }].concat(response.data)
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async lay_ds_trangthai_vlan_quyhoach() {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ds_trangthai_vlan_quyhoach`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async lay_congnghe_id_theo_hdtb(dichvuvt_id, hdtb_id) {
      try {
        let response = await this.$root.context.post(`/web-quantri/chon-port/lay_congnghe_id_theo_hdtb`, { dichvuvt_id, hdtb_id })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data.congnghe_id
        }

        throw { response: { data: response } }
      } catch (err) {
        return null
      }
    },
    async lay_ds_congnghe() {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ds_congnghe`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data.length == 0 ? [] : [{ congnghe: ' ', congnghe_id: 0 }].concat(response.data)
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    },
    async lay_ds_thamso_md_mats(ma_ts) {
      try {
        let response = await this.$root.context.get(`/web-quantri/chon-port/lay_ds_thamso_md_mats?ma_ts=${ma_ts}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw { response: { data: response } }
      } catch (err) {
        return []
      }
    }
  }
}
</script>
<style>
.frm-chon-port .disable-btn:hover {
  background-color: white !important;
}

.frm-chon-port .disable-btn a {
  color: #6c757d !important;
}

.frm-chon-port .disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}

.frm-chon-port.modal-body .page-content {
  position: inherit !important;
}

.frm-chon-port .select2-container .select2-selection--single .select2-selection__rendered {
  white-space: initial;
  height: 28px;
}

.frm-chon-port #container-cong-nghe .select2-container--open ul.select2-results__options li:first-child {
  height: 30px;
}

.frm-chon-port .check-action:focus-within {
  border-width: 1px;
  border-color: #80bdff;
  -webkit-box-shadow: 0 0 0 3px rgb(0 123 255 / 25%);
  box-shadow: 0 0 0 3px rgb(0 123 255 / 25%);
}

@media (min-width: 1200px) {
  #id-frm-chon-port .modal-xl {
    max-width: 90% !important;
  }
}
</style>
