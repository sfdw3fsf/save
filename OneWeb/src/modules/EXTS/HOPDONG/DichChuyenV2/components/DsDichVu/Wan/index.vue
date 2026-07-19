<template src="./index.html"></template>
<script>
import frmDSTBCungDoiCap from '../IMS/frmDSTBCungDoiCap'
import frmDSTBLapKem from '../IMS/frmDSTBLapKem'
import frmDSTBCungDoiCapKhac from '../IMS/frmDSTBCungDoiCap_KH_Khac/frmDSTBCungDoiCap_KH_Khac'
import Enum from '../../../../ChuyenDichBC/Enum'
import DateTimeLib from '@/utils/DateTimeLib'
export default {
  components: {
    frmDSTBCungDoiCap,
    frmDSTBLapKem,
    frmDSTBCungDoiCapKhac
  },
  props: {
    parent: null,
    dataSource: {
      idLoaihinhTb: String,
      idKieuLd: String,
      idDaiVt: String
    }
  },
  computed: {
    dsDanhMuc () {
      return this.parent.dsDanhMuc
    },
    thuebao_id () {
      return this.parent.inputValue.thuebao_id
    },
    khachHangId () {
      return this.parent.inputValue.khachhang_id
    },
    hdtb_id () {
      return this.parent.inputValue.hdtb_id
    },
    KetCuoi_ID () {
      return this.parent.inputValue.KetCuoi_ID
    },
    hdkhId () {
      return this.parent.inputValue.hdkh_id
    },
    idDaiVt () {
      return this.parent.modelCbo.cboDaiVT
    },
    idLoaihinhTb () {
      return parseInt(this.parent.modelCbo.cboLoaihinhTB)
    },
    idKieuLd () {
      return parseInt(this.parent.modelCbo.cboKieuLD)
    }
  },
  data () {
    return {
      btnMaTN_Wan: {
        Enabled: true
      },
      txtMaTN_Wan: {
        Text: ''
      },
      txtMaDoiCap_Wan: {
        Text: ''
      },
      soSim: '',
      checkVSVX: false,
      LOAI_DV: {
        TRAM_VT: 5
      },
      txtMaTN_CD: {
        Text: null
      },
      cboTrangBiWan: {
        DataSource: [],
        Value: null
      },
      cboTocDo: {
        DataSource: [],
        Value: null
      },
      cboTocDo_PIR: {
        DataSource: [],
        Value: null
      },
      cboLoaiModem: {
        DataSource: [],
        Value: null
      },
      txtCuocTC: {
        Text: '0'
      },
      cboDoiTac: {
        DataSource: [],
        Value: null
      },
      txtCuocTK: {
        Text: '0'
      },
      txtHuongKNWan: {
        Text: ''
      },
      cboChuQuanWan: {
        DataSource: [],
        Value: null
      },
      cboLoaiCap: {
        DataSource: [],
        Value: null
      },
      txtSite_ID: {
        Text: ''
      },
      cboHuongKn: '',
      cboLoaiTBiWan: {
        DataSource: [],
        Value: null
      },
      chkTocDo_PIR: {
        Checked: false
      },
      txtCuocHTWan: {
        Text: '0'
      },
      txtCuocTBiWan: {
        Text: '0'
      },
      cboDoiTacWan: {
        DataSource: [],
        Value: null,
        Enabled: false
      },
      txtMaHD_DT: {
        Text: '',
        Enabled: false
      },
      txtCuocTK_DT: {
        Text: '0',
        Enabled: false
      },
      chkDoiTacWan: {
        Checked: false
      },
      txtCuocTC_DT: {
        Text: '0',
        Enabled: false
      },
      txtSoAoWan: {
        Text: ''
      },
      txtMaHD_TTKD_MGWAN: {
        Text: ''
      },
      dsComboDonVi: []
    }
  },
  watch: {
    'dataSource': async function (value) {
      if (!value.idKieuLd) {
        this.dsComboDonVi = []
        return
      }
      await this.init_data()
    },
    'chkDoiTacWan.Checked': function (value) {
      this.cboDoiTacWan.Enabled = value
      this.txtCuocTC_DT.Enabled = value
      this.txtMaHD_DT.Enabled = value
      this.txtCuocTK_DT.Enabled = value
    },
    'chkTocDo_PIR.Checked': function (value) {
      this.chkTocDo_PIR.Checked = value
    }
  },
  async mounted () {
    // watch mouted
    await this.init_data()
  },
  methods: {
    newComboDonVi (loaiDonVi) {
      return {
        key: `combo-id-${loaiDonVi.loaidv_dich_id}`,
        loaiDonVi: loaiDonVi.loaidv_dich_id,
        template: `
                        <div class="info-row">
                            <div class="key">${loaiDonVi.ten_loaidv}</div>
                            <div class="value">
                                <SelectExt
                                    v-model="value"
                                    :dataSource="dataSource"
                                    dataValueField="donvi_id"
                                    dataTextField="ten_dv" />
                            </div>
                        </div>
                    `,
        data () {
          return {
            value: null,
            dataSource: []
          }
        },
        mounted () {
          if (loaiDonVi.dsDonVi.length > 0) {
            this.dataSource = [...loaiDonVi.dsDonVi]
            this.value = this.dataSource[0].donvi_id
          }
        }
      }
    },
    async btnMaTN_Wan_Click () {
      let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

      if (kieu == 2) { // Dịch chuyển trên đường có sẵn
        if (parseInt(this.khachHangId) > 0) {
          let frm = this.$refs.frmDSTBCungDoiCap
          frm.khachhang_id = parseInt(this.khachHangId)
          frm.kieuld_id = parseInt(this.idKieuLd)
          frm.loaitb_id = parseInt(this.idLoaiHinhTb)
          let data = await frm.show()

          if (data.chapnhan) {
            this.txtMaTN_Wan.Text = data.ma_tn
            this.txtMaDoiCap_Wan.Text = data.ma_doicap
          }
        } else {
          this.$root.toastError('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !')
        }
      } else if (kieu == 1) { // Lắp kèm
        if (parseInt(this.hdkhId) > 0) {
          let frm = this.$refs.frmDSTBLapKem
          frm.kieuld_id = parseInt(this.idKieuLd)
          frm.hdkh_id = parseInt(this.hdkhId)
          let data = await frm.show()

          if (data.chapnhan) {
            this.txtMaTN_Wan.Text = data.ma_tn
            this.txtMaDoiCap_Wan.Text = data.ma_doicap
          }
        } else {
          this.$root.toastError('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !')
        }
      } else if (kieu == 4) { // Lắp trên đường có sẵn của khách hàng khác
        let frm = this.$refs.frmDSTBCungDoiCap_KH_Khac
        frm.loaitb_id = parseInt(this.idLoaiHinhTb)
        frm.kieuld_id = parseInt(this.idKieuLd)
        let data = await frm.show()

        if (data.chapnhan) {
          this.txtMaTN_Wan.Text = data.ma_tn
          this.txtMaDoiCap_Wan.Text = data.ma_doicap
        }
      }
    },
    async init_data () {
      if (!this.dataSource.idKieuLd) {
        this.dsComboDonVi = []
        return
      }
      let args = 'TRANG_BI|LOAI_TB|CHU_QUAN|TIEN_KM|LOAI_GT|PHANLOAI_KH|LOAI_KH|NGANHANG|GOI_KT|DONVI_QL|KIEU_LD|THOIHAN|DOITAC'
      await this.$root.context.post('web-hopdong/chuyendich/lay_danhmuc_chuyendich_tt_chung_v2', {vchuoi: args}).then((res) => {
        this.cboChuQuanWan.DataSource = res.data.chu_quan
        if (this.cboTrangBiWan.DataSource && this.cboChuQuanWan.DataSource.length > 0  && !this.cboChuQuanWan.Value) {
          this.cboChuQuanWan.Value = this.cboChuQuanWan.DataSource[0]['chuquan_id']
        }
        this.cboLoaiTBiWan.DataSource = res.data.loai_tb
        if (this.cboLoaiTBiWan.DataSource && this.cboLoaiTBiWan.DataSource.length > 0 && !this.cboLoaiTBiWan.Value) {
          this.cboLoaiTBiWan.Value = this.cboLoaiTBiWan.DataSource[0]['thietbidc_id']
        }

        this.cboTrangBiWan.DataSource = res.data.trangbi
        if (this.cboTrangBiWan.DataSource && this.cboTrangBiWan.DataSource.length > 0 && !this.cboTrangBiWan.Value) {
          this.cboTrangBiWan.Value = this.cboTrangBiWan.DataSource[0]['trangbi_id']
        }
      })
      await this.lay_danhmuc_chuyendich_mgw_tsl({
        vchuoi: 'LOAI_KENH|TOCDO_KENH|LOAI_CAP|THOIHAN|DOITAC|TINH_KN|LOAI_MD|TRAM_VT_DAU-donvi_id = ' + this.dataSource.idDaiVt + '|TRAM_VT_CUOI-donvi_id = ' + this.dataSource.idDaiVt
      }).then((data) => {
        console.log(data)
        this.cboTocDo.DataSource = data.tocdo_kenh
        if (this.cboTocDo.DataSource && this.cboTocDo.DataSource.length > 0 && !this.cboTocDo.Value) {
          this.cboTocDo.Value = this.cboTocDo.DataSource[0]['tocdo_id']
        }

        this.cboLoaiModem.DataSource = data.loai_md
        if (this.cboLoaiModem.DataSource && this.cboLoaiModem.DataSource.length > 0 && !this.cboLoaiModem.Value) {
          this.cboLoaiModem.Value = this.cboLoaiModem.DataSource[0]['loaimd_id']
        }

        this.cboTocDo_PIR.DataSource = data.tocdo_kenh
        if (this.cboTocDo_PIR.DataSource && this.cboTocDo_PIR.DataSource.length > 0 && !this.cboTocDo_PIR.Value) {
          this.cboTocDo_PIR.Value = this.cboTocDo_PIR.DataSource[0]['tocdo_id']
        }

        this.cboLoaiCap.DataSource = data.loai_cap
        if (this.cboLoaiCap.DataSource && this.cboLoaiCap.DataSource.length > 0 && !this.cboLoaiCap.Value) {
          this.cboLoaiCap.Value = this.cboLoaiCap.DataSource[0]['loaicap_id']
        }

        this.cboDoiTacWan.DataSource = data.doitac
        if (this.cboDoiTacWan.DataSource && this.cboDoiTacWan.DataSource.length > 0 && !this.cboDoiTacWan.Value) {
          this.cboDoiTacWan.Value = this.cboDoiTacWan.DataSource[0]['doitac_id']
        }
      })
      await this.HT_COMBO_DONVI()
    },
    async HT_COMBO_DONVI () {
      let dsLoaiDonVi = await this.lay_ds_loai_donvi({
        dichvu_cd_id: this.dataSource.idDichVu,
        loaihinh_tb: this.dataSource.idLoaihinhTb,
        kieu_ld: this.dataSource.idKieuLd
      })

      let dsComboDonVi = []

      for (let i = 0; i < dsLoaiDonVi.length; i++) {
        let loaiDonVi = dsLoaiDonVi[i]

        if (loaiDonVi['loaidv_dich_id'] == this.LOAI_DV.TRAM_VT) {
          loaiDonVi.dsDonVi = await this.lay_ds_donvi_tramtc({
            loaidv_id: loaiDonVi['loaidv_dich_id'],
            donvi_ql_id: this.dataSource.idDaiVt
          })
        } else {
          loaiDonVi.dsDonVi = await this.lay_ds_donvi(loaiDonVi['loaidv_dich_id'])
        }

        dsComboDonVi.push(this.newComboDonVi(loaiDonVi))
      }

      this.dsComboDonVi = dsComboDonVi
    },
    async TaoDuLieu_HDTB_MGWAN (themmoi) {
      let rowHDTB_MGWAN = {
        HDTB_ID: null,
        MA_VPN: null,
        CULY: null,
        THIETBIDC_ID: null,
        GHICHU: null,
        TOCDO_ID: null,
        LOAIMD_ID: null,
        LOAIKENH_ID: null,
        TRANGBI_ID: null,
        CHUQUAN_ID: null,
        THOIHAN_ID: null,
        HUONG_KN: null,
        PORT_ID: null,
        TEN_TBI: null,
        MATB_TN: null,
        MADOICAP: null,
        MA_LT: null,
        WAN_IP: null,
        LAN_IP: null,
        CUOC_TC: null,
        CUOC_TK: null,
        PE_ID: null,
        PORT_PE: null,
        SLOT_PE: null,
        VCI_VPI_ID: null,
        TUYENCAP: null,
        LIENTU: null,
        DOICAP_P: null,
        HOPCAP_P: null,
        TUCAP_P: null,
        LOAICAP_ID: null,
        CAP_GOC: null,
        CAP_P: null,
        DOICAP_GOC: null,
        TUCAP_GOC: null,
        DSLAM: null,
        VRF: null,
        LINK: null,
        SERIAL: null,
        ROUTE: null,
        DOITAC_ID: null,
        CUOC_HT: null,
        CUOC_TBI: null,
        SVLAN: null,
        CVLAN: null,
        CUOC_TKDT: null,
        CUOC_TCDT: null,
        MAHD_DT: null,
        TOCDO_PIR_ID: null,
        MA_PL: null,
        SITE_ID: null,
        GIUKENH: null,
        LOAINODE_ID: null,
        DIACHI_KN: null,
        KP_AO: null,
        MA_CV: null,
        NGAY_CV: null,
        NGAY_YCHT: null,
        MA_CRM: null,
        ID_ERP: null,
        MAKENH_VTN: null,
        KETCUOI_ID: null,
        TINH_KN_ID: null,
        SL_IP: null,
        CUOC_IP: null,
        TOCDO_NIX: null,
        CUOC_NIX: null,
        TOCDO_ISP: null,
        CUOC_ISP: null,
        CONGNGHE_ID: null,
        CHUQUANLD_ID: null,
        TINHKHAC: null,
        MA_VNPT: null,
        KENH_TONG: null,
        DIACHI_POP: null,
        QUOCGIA_POP: null,
        HUONGLUONG_UUTIEN: null,
        CAPNGOAI_VNPT: null,
        PHANLOAI_ID: null,
        PHANTACH_TD: null,
        MA_BSS: null,
        TINHCUOC: null,
        TOCDO_POP: null,
        DIEM_POP: null,
        MHKN_ID: null,
        GDKN_POP_ID: null,
        GDKN_KH_ID: null,
        TEN_TRUNK_NNI: null,
        CE_IP: null,
        SO_AS: null,
        CAUHINH_DT_ID: null,
        KIEU_DUPLEX_ID: null,
        MTU_SIZE: null,
        HQOS: null,
        COS: null,
        KIEUKN_ID: null,
        VLAN_TRANSPARENT: null,
        PORT_POP: null,
        IP_POP: null,
        SD_TBI_NET: null
      }
      let thuebao_id = this.thuebao_id
      let ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID({ thuebao_id})

      let firstRow = {...ds[0]}
      // Object.keys(firstRow).forEach(key => rowHDTB_MGWAN[key.toUpperCase()] = firstRow[key])
      Object.keys(firstRow).forEach(key => rowHDTB_MGWAN[key.toUpperCase()] ? rowHDTB_MGWAN[key.toUpperCase()] = firstRow[key] : null)

      rowHDTB_MGWAN.HDTB_ID = this.hdtb_id
      rowHDTB_MGWAN.TOCDO_ID = parseInt(this.cboTocDo.Value)
      rowHDTB_MGWAN.LOAIMD_ID = parseInt(this.cboLoaiModem.Value)
      rowHDTB_MGWAN.LOAIKENH_ID = parseInt(ds[0]['loaikenh_id'])
      rowHDTB_MGWAN.LOAICAP_ID = parseInt(this.cboLoaiCap.Value)

      if (this.chkTocDo_PIR.Checked) {
        rowHDTB_MGWAN.TOCDO_PIR_ID = parseInt(this.cboTocDo_PIR.Value)
      }

      if (this.parent.modelCkeckBox.chkCongVan) {
        rowHDTB_MGWAN.MA_CV = this.parent.modelInput.txtCongVan
        rowHDTB_MGWAN.NGAY_CV = DateTimeLib.toDateDisplay(this.parent.modelDateTime.dtpNgayCV)
      }

      if (this.parent.inputValue.kt_kenhphu_ao == true) {
        rowHDTB_MGWAN.KP_AO = 1
      }

      let KetCuoi_ID = parseInt(this.parent.inputValue.KetCuoi_ID)

      if (KetCuoi_ID > 0) {
        rowHDTB_MGWAN.KETCUOI_ID = KetCuoi_ID
      }

      rowHDTB_MGWAN.THIETBIDC_ID = parseInt(this.cboLoaiTBiWan.Value)
      rowHDTB_MGWAN.HUONG_KN = ds[0]['huong_kn']

      if (ds[0]['tinh_kn_id']) {
        rowHDTB_MGWAN.TINH_KN_ID = parseInt(ds[0]['tinh_kn_id'])
      } else {
        rowHDTB_MGWAN.TINH_KN_ID = null
      }

      rowHDTB_MGWAN.THOIHAN_ID = parseInt(ds[0]['thoihan_id'])
      rowHDTB_MGWAN.TRANGBI_ID = parseInt(this.cboTrangBiWan.Value)
      rowHDTB_MGWAN.CHUQUAN_ID = parseInt(this.cboChuQuanWan.Value)
      rowHDTB_MGWAN.MA_PL = this.txtMaHD_TTKD_MGWAN.Text
      rowHDTB_MGWAN.SITE_ID = this.txtSite_ID.Text

      rowHDTB_MGWAN.VRF = ds[0]['vrf']
      rowHDTB_MGWAN.LAN_IP = ds[0]['lan_ip']
      rowHDTB_MGWAN.WAN_IP = ds[0]['wan_ip']

      if (ds[0]['loainode_id']) {
        rowHDTB_MGWAN.LOAINODE_ID = parseInt(ds[0]['loainode_id'])
      } else {
        rowHDTB_MGWAN.LOAINODE_ID = null
      }

      if (!this.txtCuocTC.Text) { rowHDTB_MGWAN.CUOC_TC = 0 } else { rowHDTB_MGWAN.CUOC_TC = parseFloat(this.txtCuocTC.Text) }

      if (!this.txtCuocTK.Text) { rowHDTB_MGWAN.CUOC_TK = 0 } else { rowHDTB_MGWAN.CUOC_TK = parseFloat(this.txtCuocTK.Text) }

      if (!this.txtCuocHTWan.Text) { rowHDTB_MGWAN.CUOC_HT = 0 } else { rowHDTB_MGWAN.CUOC_HT = parseFloat(this.txtCuocHTWan.Text) }

      if (!this.txtCuocTBiWan.Text) { rowHDTB_MGWAN.CUOC_TBI = 0 } else { rowHDTB_MGWAN.CUOC_TBI = parseFloat(this.txtCuocTBiWan.Text) }

      if (this.chkDoiTacWan.Checked) {
        rowHDTB_MGWAN.DOITAC_ID = parseInt(this.cboDoiTacWan.Value)

        if (this.txtCuocTK_DT.Text) { rowHDTB_MGWAN.CUOC_TKDT = parseFloat(this.txtCuocTK_DT.Text) } else { rowHDTB_MGWAN.CUOC_TKDT = 0 }

        if (this.txtCuocTC_DT.Text) { rowHDTB_MGWAN.CUOC_TCDT = parseFloat(this.txtCuocTC_DT.Text) } else { rowHDTB_MGWAN.CUOC_TCDT = 0 }

        rowHDTB_MGWAN.MAHD_DT = this.txtMaHD_DT.Text
      }

      rowHDTB_MGWAN.ID_ERP = this.parent.modelInput.txtMaERP
      rowHDTB_MGWAN.MA_BSS = this.parent.modelInput.txtMaBSS

      rowHDTB_MGWAN.MA_LT = ds[0]['ma_lt']

      let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

      // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
      if (kieu == Enum.KIEULAP.LAP_KEM || kieu == Enum.KIEULAP.LAP_LINE_SAN) {
        if (!this.txtMaDoiCap_Wan.Text) {
          this.$root.toastError('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.')
          return
        }
        rowHDTB_MGWAN.MADOICAP = parseInt(this.txtMaDoiCap_Wan.Text)
      } else // TH còn lại sinh mã đôi cáp
      {
        // Hiếu bổ sung chỉ sinh mã đôi cáp đối với TH thêm mới hợp đồng, phụ lục
        if (themmoi == true) {
          rowHDTB_MGWAN.MADOICAP = await this.get_key_madoicap()
          this.txtMaDoiCap_Wan.Text = rowHDTB_MGWAN.MADOICAP
        } else {
          rowHDTB_MGWAN.MADOICAP = parseInt(this.txtMaDoiCap_Wan.Text)
        }
      }

      rowHDTB_MGWAN.MATB_TN = this.txtMaTN_Wan.Text

      let dsHDTBDV = []

      for (let i = 0; i < this.dsComboDonVi.length; i++) {
        let item = this.dsComboDonVi[i]
        let row = {
          HDTB_ID: this.hdtb_id,
          LOAIDV_ID: parseInt(item.loaiDonVi),
          DONVI_ID: parseInt(this.$refs[item.key][0].value),
          KIEUDV_ID: 2
        }
        dsHDTBDV.push(row)
      }
      console.log('🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀 ~~~rowHDTB_WAN', rowHDTB_MGWAN)
      console.log('🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀 ~~~rowHDTB_WAN', dsHDTBDV)
      return {
        dsHDTB_MEWAN: [rowHDTB_MGWAN],
        dsHDTBDV: dsHDTBDV
      }
    },
    async get_key_madoicap (tinh_id) {
      try {
        tinh_id = !tinh_id ? '' : tinh_id
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/get_key_madoicap_wan?tinh_id=${tinh_id}`)
        return !response.data ? null : response.data[0].key
      } catch (err) {
        return null
      }
    },
    async HienThiTTMoRongHD (hdtb_id) {
      let ds = await this.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(hdtb_id)

      if (ds.length <= 0) {
        return
      }

      this.cboTocDo.Value = parseInt(ds[0]['tocdo_id']) + ''
      this.parent.inputValue._tocdo_id = parseInt(ds[0]['tocdo_id'])
      this.cboLoaiModem.Value = parseInt(ds[0]['loaimd_id']) + ''
      this.cboLoaiCap.Value = parseInt(ds[0]['loaicap_id']) + ''
      this.parent.inputValue.vloaikenh_id = parseInt(ds[0]['loaikenh_id'])

      if (parseInt(ds[0]['kp_ao']) == 1) { this.parent.inputValue.kt_kenhphu_ao = true } else { this.parent.inputValue.kt_kenhphu_ao = false }

      if (ds[0]['tocdo_pir_id']) {
        this.chkTocDo_PIR.Checked = true
        this.cboTocDo_PIR.Value = parseInt(ds[0]['tocdo_pir_id']) + ''
      } else {
        this.chkTocDo_PIR.Checked = false
        this.cboTocDo_PIR.Value = parseInt(ds[0]['tocdo_id']) + ''
      }

      this.parent.modelCkeckBox.chkdisableCongVan = false

      if (ds[0]['ma_cv']) {
        this.parent.modelCkeckBox.chkCongVan = true
        this.parent.modelInput.txtCongVan = ds[0]['ma_cv']
        this.parent.modelDateTime.dtpNgayCV = new Date(ds[0]['ngay_cv'])
      }

      this.cboLoaiTBiWan.Value = parseInt(ds[0]['thietbidc_id']) + ''
      this.txtMaTN_Wan.Text = ds[0]['matb_tn']
      this.txtMaDoiCap_Wan.Text = ds[0]['madoicap']
      this.txtSite_ID.Text = ds[0]['site_id']
      this.txtMaHD_TTKD_MGWAN.Text = ds[0]['ma_pl']
      this.txtSoAoWan.Text = ds[0]['ma_lt']

      let vhuong_kn = ds[0]['huong_kn']
      this.parent.inputValue.vhuong_kn = vhuong_kn

      if (vhuong_kn) {
        this.txtHuongKNWan.Text = await this.Lay_text_huongketnoi(vhuong_kn)
      } else {
        this.txtHuongKNWan.Text = ''
      }

      if (ds[0]['trangbi_id']) { this.cboTrangBiWan.Value = parseInt(ds[0]['trangbi_id']) + '' } else { this.cboTrangBiWan.Value = null }

      if (ds[0]['chuquan_id']) { this.cboChuQuanWan.Value = parseInt(ds[0]['chuquan_id']) } else { this.cboChuQuanWan.Value = null }

      this.parent.inputValue.cuoc_tc = parseFloat(ds[0]['cuoc_tc'])
      this.parent.inputValue.cuoc_tk = parseFloat(ds[0]['cuoc_tk'])

      this.txtCuocTK.Text = ds[0]['cuoc_tk']
      this.txtCuocTC.Text = ds[0]['cuoc_tc']
      this.txtCuocHTWan.Text = ds[0]['cuoc_ht']
      this.txtCuocTBiWan.Text = ds[0]['cuoc_tbi']
      this.parent.modelInput.txtMaBSS = ds[0]['ma_bss']
      this.parent.modelInput.txtMaERP = ds[0]['id_erp']
      if (ds[0]['doitac_id']) {
        this.chkDoiTacWan.Checked = true
        this.cboDoiTacWan.Value = ds[0]['doitac_id'] + ''

        if (ds[0]['cuoc_tcdt']) {
          this.txtCuocTC_DT.Text = parseFloat(ds[0]['cuoc_tcdt'])
        } else {
          this.txtCuocTC_DT.Text = '0'
        }

        if (ds[0]['cuoc_tkdt']) {
          this.txtCuocTK_DT.Text = parseFloat(ds[0]['cuoc_tkdt'])
        } else {
          this.txtCuocTK_DT.Text = '0'
        }

        this.txtMaHD_DT.Text = ds[0]['mahd_dt']
      } else {
        this.chkDoiTacWan.Checked = false
        this.txtCuocTC_DT.Text = '0'
        this.txtCuocTK_DT.Text = '0'
        this.txtMaHD_DT.Text = ''
      }

      await this.HT_COMBO_DONVI()

      let dsDonVi = await this.lay_ds_donvi_cua_hopdong(this.hdtb_id)

      setTimeout(() => {
        if (dsDonVi.length > 0) {
          for (let i = 0; i < this.dsComboDonVi.length; i++) {
            let cbo = this.dsComboDonVi[i]
            let donVi = dsDonVi.find(item => item.loaidv_id == parseInt(cbo.loaiDonVi))

            if (donVi) {
              this.$refs[cbo.key][0].value = donVi['donvi_id']
            }
          }
        }
      }, 300)
    },
    async lay_ds_donvi_cua_hopdong (hdtb_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_donvi_cua_hopdong`, { hdtb_id })
        return response.data
      } catch (err) {
        return []
      }
    },
    async HienThiTTMoRongTB (thuebao_id) {
      let ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID({thuebao_id})

      if (ds.length <= 0) {
        return
      }

      this.cboTocDo.Value = ds[0]['tocdo_id'] + ''
      this.parent.inputValue._tocdo_id = ds[0]['tocdo_id']
      this.cboLoaiModem.Value = ds[0]['loaimd_id'] + ''
      this.cboLoaiCap.Value = ds[0]['loaicap_id'] + ''
      this.parent.inputValue.vloaikenh_id = ds[0]['loaikenh_id']

      if (parseInt(ds[0]['kp_ao']) == 1) {
        this.parent.inputValue.kt_kenhphu_ao = true
      } else {
        this.parent.inputValue.kt_kenhphu_ao = false
      }

      if (ds[0]['tocdo_pir_id']) {
        this.chkTocDo_PIR.Checked = true
        this.cboTocDo_PIR.Value = ds[0]['tocdo_pir_id'] + ''
      } else {
        this.chkTocDo_PIR.Checked = false
      }

      this.txtSite_ID.Text = ds[0]['site_id']
      this.parent.modelCkeckBox.chkdisableCongVan = false
      this.parent.modelDateTime.dtpNgayCV = new Date()
      this.txtSoAoWan.Text = ds[0]['ma_lt']

      let vhuong_kn = ds[0]['huong_kn']
      this.parent.inputValue.vhuong_kn = vhuong_kn

      if (vhuong_kn) {
        this.txtHuongKNWan.Text = await this.Lay_text_huongketnoi(vhuong_kn)
      } else {
        this.txtHuongKNWan.Text = ''
      }

      if (ds[0]['trangbi_id']) {
        this.cboTrangBiWan.Value = ds[0]['trangbi_id'] + ''
      } else {
        this.cboTrangBiWan.Value = null
      }

      if (ds[0]['chuquan_id']) {
        this.cboChuQuanWan.Value = ds[0]['chuquan_id'] + ''
      } else {
        this.cboChuQuanWan.Value = null
      }

      this.txtMaDoiCap_Wan.Text = ds[0]['madoicap']

      if (ds[0]['thietbidc_id']) {
        this.cboLoaiTBiWan.Value = ds[0]['thietbidc_id'] + ''
      }

      if (ds[0]['mucuoctb_id']) {
        this.parent.modelCbo.cboMucCuocTB = ds[0]['mucuoctb_id'] + ''
      }

      this.parent.inputValue.cuoc_tc = parseFloat(ds[0]['cuoc_tc'])
      this.parent.inputValue.cuoc_tk = parseFloat(ds[0]['cuoc_tk'])

      this.txtCuocTK.Text = ds[0]['cuoc_tk']
      this.txtCuocTC.Text = ds[0]['cuoc_tc']
      this.txtCuocHTWan.Text = ds[0]['cuoc_ht']
      this.txtCuocTBiWan.Text = ds[0]['cuoc_tbi']

      this.parent.modelInput.txtMaERP = ds[0]['id_erp']
      this.parent.modelInput.txtMaBSS = ds[0]['ma_bss']

      if (ds[0]['doitac_id']) {
        this.chkDoiTacWan.Checked = true
        this.cboDoiTacWan.Value = ds[0]['doitac_id'] + ''

        if (ds[0]['cuoc_tcdt']) {
          this.txtCuocTC_DT.Text = parseFloat(ds[0]['cuoc_tcdt'])
        } else {
          this.txtCuocTC_DT.Text = '0'
        }

        if (ds[0]['cuoc_tkdt']) {
          this.txtCuocTK_DT.Text = parseFloat(ds[0]['cuoc_tkdt'])
        } else {
          this.txtCuocTK_DT.Text = '0'
        }

        this.txtMaHD_DT.Text = ds[0]['mahd_dt']
      } else {
        this.chkDoiTacWan.Checked = false
        this.txtCuocTC_DT.Text = '0'
        this.txtCuocTK_DT.Text = '0'
        this.txtMaHD_DT.Text = ''
      }

      // Lấy ra các đơn vị được chọn
      let dsDonVi = await this.lay_ds_donvi_cua_tbao(thuebao_id)

      setTimeout(() => {
        if (dsDonVi.length > 0) {
          for (let i = 0; i < this.dsComboDonVi.length; i++) {
            let cbo = this.dsComboDonVi[i]
            let donVi = dsDonVi.find(item => item.loaidv_id == parseInt(cbo.loaiDonVi))

            if (donVi) {
              this.$refs[cbo.key][0].value = donVi['donvi_id']
            }
          }
        }
      }, 300)
    },
    async lay_ds_donvi_cua_tbao (thuebao_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_donvi_cua_tbao`, { thuebao_id })
        return response.data
      } catch (err) {
        return []
      }
    },
    async LAY_DS_HDTB_MGWAN_THEO_HDTB_ID (hdtb_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_hdtb_mgwan_theo_hdtb_id?hdtb_id=${hdtb_id}`)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async Lay_text_huongketnoi (huong_kn) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_text_huongketnoi?huong_kn=${huong_kn}`)
        return !response.data ? '' : response.data[0]['huong_kn']
      } catch (err) {
        return ''
      }
    },
    async lay_danhmuc_chuyendich_mgw_tsl (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich/lay_danhmuc_chuyendich_mgw_tsl`, data)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_tt_kieu_ld (kieuld_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_tt_kieu_ld?kieuld_id=${kieuld_id}`)
        return response.data.kieu
      } catch (err) {
        return null
      }
    },
    async lay_ds_donvi_tramtc (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-v2/lay_ds_donvi_tramtc`, data)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_donvi (loaidv_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-v2/lay_ds_donvi?loaidv_id=${loaidv_id}`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async LAY_DS_DB_MEGAWAN_THEO_TBID (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_db_megawan_theo_tbid`, data)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_loai_donvi (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-v2/lay_ds_loai_donvi`, data)
        return response.data
      } catch (err) {
        return []
      }
    },
    async HT_DonVi_TRAMTC_Combobox (cbo, DONVI_ID) {
      cbo = this.$refs[cbo.key][0]

      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/ht_donvi_tramtc_combobox', {DONVI_ID})
        cbo.dataSource = !rs.data ? [] : rs.data
      } catch (error) {
        cbo.dataSource = []
      }

      if (cbo.dataSource.length > 0) {
        cbo.value = cbo.dataSource[0].donvi_id
      } else {
        cbo.value = null
      }
    },
    async HT_DonVi_TRAMTC_Combobox2 (cbo, LOAIDV_ID, DONVI_QL_ID) {
      cbo = this.$refs[cbo.key][0]

      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/ht_donvi_tramtc_combobox_2', {LOAIDV_ID, DONVI_QL_ID})
        cbo.dataSource = !rs.data ? [] : rs.data
      } catch (error) {
        cbo.dataSource = []
      }

      if (cbo.dataSource.length > 0) {
        cbo.value = cbo.dataSource[0].donvi_id
      } else {
        cbo.value = null
      }
    },
    async lay_ds_trangbi () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-v2/lay_ds_trangbi`)
        return response.data
      } catch (err) {
        return []
      }
    }
  }
}
</script>
