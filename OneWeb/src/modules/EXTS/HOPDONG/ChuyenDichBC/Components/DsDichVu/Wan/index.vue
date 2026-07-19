<template src="./index.html"></template>
<script>
import DateTimeLib from '@/utils/DateTimeLib'
import Enum from '../../../Enum.js'
import frmDSTBCungDoiCap from '../Shared/frmDSTBCungDoiCap'
import frmDSTBLapKem from '../Shared/frmDSTBLapKem'
import frmDSTBCungDoiCap_KH_Khac from '../Shared/frmDSTBCungDoiCap_KH_Khac/frmDSTBCungDoiCap_KH_Khac.vue'

export default {
  components: {
    frmDSTBCungDoiCap,
    frmDSTBLapKem,
    frmDSTBCungDoiCap_KH_Khac
  },
  props: ['parent'],
  data () {
    return {
      DichVuVienThong: {
        MEGAWAN: 8,
        METRONET: 7
      },
      btnMaTN_Wan: {
        Enabled: true
      },
      cboTocDo: {
        DataSource: [],
        Value: null
      },
      cboLoaiTBiWan: {
        DataSource: [],
        Value: null
      },
      cboTocDo_PIR: {
        DataSource: [],
        Value: null
      },
      cboTrangBiWan: {
        DataSource: [],
        Value: null
      },
      cboLoaiModem: {
        DataSource: [],
        Value: null
      },
      cboChuQuanWan: {
        DataSource: [],
        Value: null
      },
      cboLoaiCap: {
        DataSource: [],
        Value: null
      },
      cboDoiTacWan: {
        DataSource: [],
        Value: null,
        Enabled: false
      },
      txtMaTN_Wan: {
        Text: ''
      },
      txtSoAoWan: {
        Text: ''
      },
      txtMaHD_TTKD_MGWAN: {
        Text: ''
      },
      txtMaDoiCap_Wan: {
        Text: ''
      },
      txtSite_ID: {
        Text: ''
      },
      txtHuongKNWan: {
        Text: ''
      },
      txtMaHD_DT: {
        Text: '',
        Enabled: false
      },
      txtCuocTK: {
        Text: '0'
      },
      txtCuocTC: {
        Text: '0'
      },
      txtCuocTC_DT: {
        Text: '0',
        Enabled: false
      },
      txtCuocHTWan: {
        Text: '0'
      },
      txtCuocTK_DT: {
        Text: '0',
        Enabled: false
      },
      txtCuocTBiWan: {
        Text: '0'
      },
      chkTocDo_PIR: {
        Checked: false
      },
      chkDoiTacWan: {
        Checked: false
      },
      dsComboDonVi: []
    }
  },
  computed: {
    idDichVu () {
      return parseInt(this.parent.parent.modelCbo.cboDichVuVT)
    },
    isActive () {
      return this.idDichVu == this.DichVuVienThong.MEGAWAN || this.idDichVu == this.DichVuVienThong.METRONET
    },
    idLoaiHinhTb () {
      return parseInt(this.parent.parent.modelCbo.cboLoaihinhTB)
    },
    idKieuLd () {
      return parseInt(this.parent.parent.modelCbo.cboKieuLD)
    },
    vloaikenh_id () {
      return this.parent.parent.vloaikenh_id
    },
    idDaiVt () {
      return this.parent.parent.modelCbo.cboDaiVT
    },
    khachHangId () {
      return this.parent.parent.khachhang_id
    },
    hdkhId () {
      return this.parent.parent.hdkh_id
    }
  },
  watch: {
    idKieuLd: async function () {
      if (this.isActive) {
        await this.cboKieuLD_SelectedValueChanged()
      }
    },
    idLoaiHinhTb: async function () {
      if (this.isActive) {
        await this.HT_COMBO_DONVI()
      }
    },
    'cboTocDo.Value': async function () {
      await this.cboTocDo_SelectedValueChanged()
    },
    'cboLoaiTBiWan.Value': function () {
      this.cboLoaiTBiWan_SelectedValueChanged()
    },
    'cboTrangBiWan.Value': function () {
      this.cboTrangBiWan_SelectedValueChanged()
    },
    'chkDoiTacWan.Checked': function () {
      this.chkDoiTacWan_CheckedChanged()
    }
  },
  async mounted () {
    let dsTocDo = await this.lay_ds_tocdo()
    this.cboTocDo.DataSource = dsTocDo.map(item => { return {...item} })
    this.cboTocDo.Value = this.cboTocDo.DataSource[0].tocdo_id

    this.cboTocDo_PIR.DataSource = dsTocDo.map(item => { return {...item} })
    this.cboTocDo_PIR.Value = this.cboTocDo_PIR.DataSource[0].tocdo_id

    this.cboLoaiTBiWan.DataSource = await this.lay_ds_loai_tb()
    this.cboLoaiTBiWan.Value = this.cboLoaiTBiWan.DataSource[0].thietbidc_id

    this.cboTrangBiWan.DataSource = await this.lay_ds_trangbi()
    this.cboTrangBiWan.Value = this.cboTrangBiWan.DataSource[0].trangbi_id

    this.cboLoaiModem.DataSource = await this.lay_ds_loai_modem()
    this.cboLoaiModem.Value = this.cboLoaiModem.DataSource[0].loaimd_id

    this.cboChuQuanWan.DataSource = await this.lay_ds_chuquan()
    this.cboChuQuanWan.Value = this.cboChuQuanWan.DataSource[0].chuquan_id

    this.cboLoaiCap.DataSource = await this.lay_ds_loai_cap()
    this.cboLoaiCap.Value = this.cboLoaiCap.DataSource[0].loaicap_id

    this.cboDoiTacWan.DataSource = await this.lay_ds_doitac()
    this.cboDoiTacWan.Value = this.cboDoiTacWan.DataSource[0].doitac_id
  },
  methods: {
    async TaoDuLieu_HDBC_MGWAN (themmoi) {
      let rowHDBCMGWAN = {}
      let tinh_id = this.parent.parent.BanCheoData.Tinh_TC.tinhthicong_id.toString()
      let thuebao_tc_id = this.parent.parent.thuebao_tc_id
      let thongso_tc = this.parent.parent.thongso_tc
      let kenh_trang = this.parent.parent.kenh_trang
      console.log('🚀 ~ file: index.vue:193 ~ TaoDuLieu_HDBC_MGWAN ~ kenh_trang:', kenh_trang)
      let chi_giao_net = this.parent.parent.chi_giao_net
      console.log('🚀 ~ file: index.vue:195 ~ TaoDuLieu_HDBC_MGWAN ~ chi_giao_net:', chi_giao_net)

      let ds = []

      if (!kenh_trang && !chi_giao_net) {
        ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID_BC({tinhthicong_id: tinh_id, thuebao_id: thuebao_tc_id})
        console.log('🚀 ~ file: index.vue:200 ~ TaoDuLieu_HDBC_MGWAN ~ ds:', ds)
        let firstRow = {...ds[0]}
        Object.keys(firstRow).forEach(key => rowHDBCMGWAN[key.toUpperCase()] = firstRow[key])
      }

      rowHDBCMGWAN.HDTB_ID = this.parent.parent.hdtb_id
      rowHDBCMGWAN.TOCDO_ID = thongso_tc.tocdo_id
      rowHDBCMGWAN.LOAIMD_ID = thongso_tc.loaimd_id
      rowHDBCMGWAN.LOAIKENH_ID = thongso_tc.loaikenh_id
      rowHDBCMGWAN.LOAICAP_ID = parseInt(this.cboLoaiCap.Value)

      if (this.chkTocDo_PIR.Checked) { rowHDBCMGWAN.TOCDO_PIR_ID = thongso_tc.tocdo_pir_id }

      if (this.parent.parent.modelCkeckBox.chkCongVan) {
        rowHDBCMGWAN.MA_CV = this.parent.parent.modelInput.txtCongVan
        rowHDBCMGWAN.NGAY_CV = DateTimeLib.toDateDisplay(this.parent.parent.modelDateTime.dtpNgayCV)
      } else {
        rowHDBCMGWAN.MA_CV = null
        rowHDBCMGWAN.NGAY_CV = null
      }

      if (this.parent.parent.kt_kenhphu_ao == true) {
        rowHDBCMGWAN.KP_AO = 1
      }

      let KetCuoi_ID = parseInt(this.parent.parent.KetCuoi_ID)

      if (KetCuoi_ID > 0) {
        rowHDBCMGWAN.KETCUOI_ID = KetCuoi_ID
      }

      rowHDBCMGWAN.THIETBIDC_ID = thongso_tc.thietbidc_id

      if (!kenh_trang && !chi_giao_net) {
        rowHDBCMGWAN.HUONG_KN = ds[0]['huong_kn']

        if (ds[0]['tinh_kn_id']) { rowHDBCMGWAN.TINH_KN_ID = parseInt(ds[0]['tinh_kn_id']) }

        rowHDBCMGWAN.THOIHAN_ID = parseInt(ds[0]['thoihan_id'])

        if (ds[0]['loainode_id']) { rowHDBCMGWAN.LOAINODE_ID = parseInt(ds[0]['loainode_id']) }

        rowHDBCMGWAN.MA_LT = ds[0]['ma_lt']
      }

      rowHDBCMGWAN.TRANGBI_ID = thongso_tc.trangbi_id
      rowHDBCMGWAN.CHUQUAN_ID = thongso_tc.chuquan_id
      rowHDBCMGWAN.MA_PL = this.txtMaHD_TTKD_MGWAN.Text
      rowHDBCMGWAN.SITE_ID = this.txtSite_ID.Text
      rowHDBCMGWAN.THOIHAN_ID = thongso_tc.thoihan_id
      rowHDBCMGWAN.ID_ERP = this.parent.parent.modelInput.txtMaERP
      rowHDBCMGWAN.MA_BSS = this.parent.parent.modelInput.txtMaBSS

      if (!this.txtCuocTC.Text) { rowHDBCMGWAN.CUOC_TC = 0 } else { rowHDBCMGWAN.CUOC_TC = parseFloat(this.txtCuocTC.Text) }

      if (!this.txtCuocTK.Text) { rowHDBCMGWAN.CUOC_TK = 0 } else { rowHDBCMGWAN.CUOC_TK = parseFloat(this.txtCuocTK.Text) }

      if (!this.txtCuocHTWan.Text) { rowHDBCMGWAN.CUOC_HT = 0 } else { rowHDBCMGWAN.CUOC_HT = parseFloat(this.txtCuocHTWan.Text) }

      if (!this.txtCuocTBiWan.Text) { rowHDBCMGWAN.CUOC_TBI = 0 } else { rowHDBCMGWAN.CUOC_TBI = parseFloat(this.txtCuocTBiWan.Text) }

      if (this.chkDoiTacWan.Checked) {
        rowHDBCMGWAN.DOITAC_ID = parseInt(this.cboDoiTacWan.Value)

        if (this.txtCuocTK_DT.Text) { rowHDBCMGWAN.CUOC_TKDT = parseFloat(this.txtCuocTK_DT.Text) } else { rowHDBCMGWAN.CUOC_TKDT = 0 }

        if (this.txtCuocTC_DT.Text) { rowHDBCMGWAN.CUOC_TCDT = parseFloat(this.txtCuocTC_DT.Text) } else { rowHDBCMGWAN.CUOC_TCDT = 0 }

        rowHDBCMGWAN.MAHD_DT = this.txtMaHD_DT.Text
      }

      rowHDBCMGWAN.CHUQUANLD_ID = thongso_tc.chuquanld_id

      // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
      if (kieu == Enum.KIEULAP.LAP_KEM || kieu == Enum.KIEULAP.LAP_LINE_SAN) {
        if (!this.txtMaDoiCap_Wan.Text) {
          this.$root.toastError('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.')
          return
        }
        rowHDBCMGWAN.MADOICAP = parseInt(this.txtMaDoiCap_Wan.Text)
      } else // TH còn lại sinh mã đôi cáp
      {
        // Hiếu bổ sung chỉ sinh mã đôi cáp đối với TH thêm mới hợp đồng, phụ lục
        if (themmoi == true) {
          rowHDBCMGWAN.MADOICAP = await this.get_key_madoicap()
          this.txtMaDoiCap_Wan.Text = rowHDBCMGWAN.MADOICAP
        } else {
          rowHDBCMGWAN.MADOICAP = parseInt(this.txtMaDoiCap_Wan.Text)
        }
      }

      let kieu = await this.lay_tt_kieu_ld(thongso_tc.kieuld_id)

      // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
      if (kieu == Enum.KIEULAP.LAP_KEM || kieu == Enum.KIEULAP.LAP_LINE_SAN) {
        if (!thongso_tc.ma_doicap) {
          this.$root.toastError('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.')
          return
        }
        rowHDBCMGWAN.MADOICAP = parseInt(thongso_tc.ma_doicap)
      } else // TH còn lại sinh mã đôi cáp
      {
        // Hiếu bổ sung chỉ sinh mã đôi cáp đối với TH thêm mới hợp đồng, phụ lục
        if (themmoi == true) {
          rowHDBCMGWAN.MADOICAP = await this.get_key_madoicap(tinh_id)
        } else if (thongso_tc.ma_doicap) {
          rowHDBCMGWAN.MADOICAP = parseInt(thongso_tc.ma_doicap)
        }
      }

      rowHDBCMGWAN.MATB_TN = thongso_tc.ma_tn

      return {
        dsHDBCMGWAN: [rowHDBCMGWAN]
      }
    },
    async TaoDuLieu_HDTB_MGWAN (themmoi) {
      let rowHDTB_MGWAN = {}
      let thuebao_id = this.parent.parent.thuebao_id
      let ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID({thuebao_id})
      let thongso_ban = this.parent.parent.thongso_ban

      let firstRow = {...ds[0]}
      Object.keys(firstRow).forEach(key => rowHDTB_MGWAN[key.toUpperCase()] = firstRow[key])

      rowHDTB_MGWAN.HDTB_ID = this.parent.parent.hdtb_id
      rowHDTB_MGWAN.TOCDO_ID = parseInt(this.cboTocDo.Value)
      rowHDTB_MGWAN.LOAIMD_ID = parseInt(this.cboLoaiModem.Value)
      // rowHDTB_MGWAN.LOAIKENH_ID = parseInt(ds[0]['loaikenh_id'])
      rowHDTB_MGWAN.LOAIKENH_ID = thongso_ban.loaikenh_id
      rowHDTB_MGWAN.LOAICAP_ID = parseInt(this.cboLoaiCap.Value)
      rowHDTB_MGWAN.ID_ERP = this.parent.parent.modelInput.txtMaERP
      rowHDTB_MGWAN.MA_BSS = this.parent.parent.modelInput.txtMaBSS

      if (this.chkTocDo_PIR.Checked) {
        rowHDTB_MGWAN.TOCDO_PIR_ID = parseInt(this.cboTocDo_PIR.Value)
      }

      if (this.parent.parent.modelCkeckBox.chkCongVan) {
        rowHDTB_MGWAN.MA_CV = this.parent.parent.modelInput.txtCongVan
        rowHDTB_MGWAN.NGAY_CV = DateTimeLib.toDateDisplay(this.parent.parent.modelDateTime.dtpNgayCV)
      } else {
        rowHDTB_MGWAN.MA_CV = null
        rowHDTB_MGWAN.NGAY_CV = null
      }

      if (this.parent.parent.kt_kenhphu_ao == true) {
        rowHDTB_MGWAN.KP_AO = 1
      }

      let KetCuoi_ID = parseInt(this.parent.parent.KetCuoi_ID)

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

      rowHDTB_MGWAN.CHUQUANLD_ID = this.parent.parent.thongso_ban.chuquanld_id
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
          HDTB_ID: this.parent.parent.hdtb_id,
          LOAIDV_ID: parseInt(item.loaiDonVi),
          DONVI_ID: parseInt(this.$refs[item.key][0].value),
          KIEUDV_ID: 2
        }
        dsHDTBDV.push(row)
      }

      return {
        dsHDTB_MEWAN: [rowHDTB_MGWAN],
        dsHDTBDV: dsHDTBDV
      }
    },
    async get_key_madoicap (tinh_id) {
      try {
        tinh_id = !tinh_id ? this.$root.token.getPhanVungID() : tinh_id
        let response = await this.$root.context.get(`/web-bancheo/chuyendich-bancheo-dichvu/get_key_madoicap?tinh_id=${tinh_id}`)
        return !response.data ? null : parseInt(response.data)
      } catch (err) {
        return null
      }
    },
    async LAY_DS_DB_MEGAWAN_THEO_TBID (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_db_megawan_theo_tbid`, data)
        return !response.data ? [] : response.data
      } catch (err) {
        console.log('🚀 ~ file: index.vue:450 ~ LAY_DS_DB_MEGAWAN_THEO_TBID ~ err:', err)
        return []
      }
    },
    async LAY_DS_DB_MEGAWAN_THEO_TBID_BC (data) {
      try {
        // let response = await this.$root.context.post(`/web-bancheo/chuyendich-bancheo-dichvu/lay_ds_db_megawan_theo_tbid_bc`, data)
        let response = await this.$root.context.post(`/web-bancheo/tracuu/lay_ds_db_megawan_theo_tbid`, data)
        return !response.data ? [] : response.data
      } catch (err) {
        console.log('🚀 ~ file: index.vue:450 ~ LAY_DS_DB_MEGAWAN_THEO_TBID_BC ~ err:', err)
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
    async LAY_DS_HDTB_MGWAN_THEO_HDTB_ID (hdtb_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_hdtb_mgwan_theo_hdtb_id?hdtb_id=${hdtb_id}`)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async HienThiTTMoRongHD (hdtb_id) {
      let ds = await this.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(hdtb_id)

      if (ds.length <= 0) {
        return
      }

      this.cboTocDo.Value = parseInt(ds[0]['tocdo_id']) + ''
      this.parent.parent._tocdo_id = parseInt(ds[0]['tocdo_id'])
      this.cboLoaiModem.Value = parseInt(ds[0]['loaimd_id']) + ''
      this.cboLoaiCap.Value = parseInt(ds[0]['loaicap_id']) + ''
      this.parent.parent.vloaikenh_id = parseInt(ds[0]['loaikenh_id'])

      if (parseInt(ds[0]['kp_ao']) == 1) { this.parent.parent.kt_kenhphu_ao = true } else { this.parent.parent.kt_kenhphu_ao = false }

      if (ds[0]['tocdo_pir_id']) {
        this.chkTocDo_PIR.Checked = true
        this.cboTocDo_PIR.Value = parseInt(ds[0]['tocdo_pir_id']) + ''
      } else {
        this.chkTocDo_PIR.Checked = false
        this.cboTocDo_PIR.Value = parseInt(ds[0]['tocdo_id']) + ''
      }

      this.parent.parent.modelCkeckBox.chkdisableCongVan = false

      if (ds[0]['ma_cv']) {
        this.parent.parent.modelCkeckBox.chkCongVan = true
        this.parent.parent.modelInput.txtCongVan = ds[0]['ma_cv']
        this.parent.parent.modelDateTime.dtpNgayCV = new Date(ds[0]['ngay_cv'])
      }

      this.cboLoaiTBiWan.Value = parseInt(ds[0]['thietbidc_id']) + ''
      this.txtMaTN_Wan.Text = ds[0]['matb_tn']
      this.txtMaDoiCap_Wan.Text = ds[0]['madoicap']
      this.txtSite_ID.Text = ds[0]['site_id']
      this.txtMaHD_TTKD_MGWAN.Text = ds[0]['ma_pl']
      this.txtSoAoWan.Text = ds[0]['ma_lt']

      let vhuong_kn = ds[0]['huong_kn']
      this.parent.parent.vhuong_kn = vhuong_kn

      if (vhuong_kn) {
        this.txtHuongKNWan.Text = await this.Lay_text_huongketnoi(vhuong_kn)
      } else {
        this.txtHuongKNWan.Text = ''
      }

      if (ds[0]['trangbi_id']) { this.cboTrangBiWan.Value = parseInt(ds[0]['trangbi_id']) + '' } else { this.cboTrangBiWan.Value = null }

      if (ds[0]['chuquan_id']) { this.cboChuQuanWan.Value = parseInt(ds[0]['chuquan_id']) } else { this.cboChuQuanWan.Value = null }

      this.parent.parent.cuoc_tc = parseFloat(ds[0]['cuoc_tc'])
      this.parent.parent.cuoc_tk = parseFloat(ds[0]['cuoc_tk'])

      this.txtCuocTK.Text = ds[0]['cuoc_tk']
      this.txtCuocTC.Text = ds[0]['cuoc_tc']
      this.txtCuocHTWan.Text = ds[0]['cuoc_ht']
      this.txtCuocTBiWan.Text = ds[0]['cuoc_tbi']

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

      let dsDonVi = await this.lay_ds_donvi_cua_hopdong(hdtb_id)

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
      this.parent.parent._tocdo_id = ds[0]['tocdo_id']
      this.cboLoaiModem.Value = ds[0]['loaimd_id'] + ''
      this.cboLoaiCap.Value = ds[0]['loaicap_id'] + ''
      this.parent.parent.vloaikenh_id = ds[0]['loaikenh_id']

      if (parseInt(ds[0]['kp_ao']) == 1) {
        this.parent.parent.kt_kenhphu_ao = true
      } else {
        this.parent.parent.kt_kenhphu_ao = false
      }

      if (ds[0]['tocdo_pir_id']) {
        this.chkTocDo_PIR.Checked = true
        this.cboTocDo_PIR.Value = ds[0]['tocdo_pir_id'] + ''
      } else {
        this.chkTocDo_PIR.Checked = false
      }

      this.txtSite_ID.Text = ds[0]['site_id']
      this.parent.parent.modelCkeckBox.chkdisableCongVan = false
      this.parent.parent.modelDateTime.dtpNgayCV = new Date()
      this.txtSoAoWan.Text = ds[0]['ma_lt']

      let vhuong_kn = ds[0]['huong_kn']
      this.parent.parent.vhuong_kn = vhuong_kn

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
        this.parent.parent.modelCbo.cboMucCuocTB = ds[0]['mucuoctb_id'] + ''
      }

      this.parent.parent.cuoc_tc = parseFloat(ds[0]['cuoc_tc'])
      this.parent.parent.cuoc_tk = parseFloat(ds[0]['cuoc_tk'])

      this.txtCuocTK.Text = ds[0]['cuoc_tk']
      this.txtCuocTC.Text = ds[0]['cuoc_tc']
      this.txtCuocHTWan.Text = ds[0]['cuoc_ht']
      this.txtCuocTBiWan.Text = ds[0]['cuoc_tbi']

      this.parent.parent.modelInput.txtMaERP = ds[0]['id_erp']
      this.parent.parent.modelInput.txtMaBSS = ds[0]['ma_bss']

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
    chkDoiTacWan_CheckedChanged () {
      if (this.chkDoiTacWan.Checked) {
        this.cboDoiTacWan.Enabled = true
        this.txtCuocTK_DT.Enabled = true
        this.txtCuocTC_DT.Enabled = true
        this.txtMaHD_DT.Enabled = true
      } else {
        this.cboDoiTacWan.Enabled = false
        this.txtCuocTK_DT.Enabled = false
        this.txtCuocTC_DT.Enabled = false
        this.txtMaHD_DT.Enabled = false
        this.txtCuocTK_DT.Text = '0'
        this.txtCuocTC_DT.Text = '0'
      }
    },
    cboTrangBiWan_SelectedValueChanged () {
      if (this.cboTrangBiWan.Value) {
        this.$emit('cboTrangBiWan_SelectedValueChanged', { trangbi_id: this.cboTrangBiWan.Value })
      }
    },
    cboLoaiTBiWan_SelectedValueChanged () {
      if (this.cboLoaiTBiWan.Value) {
        this.$emit('cboLoaiTBiWan_SelectedValueChanged', { loaitbi_id: this.cboLoaiTBiWan.Value })
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
    async cboKieuLD_SelectedValueChanged () {
      await this.HT_COMBO_DONVI()

      if (!this.idKieuLd) {
        return
      }

      let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

      if (kieu == 1 || kieu == 2 || kieu == 4) {
        this.btnMaTN_Wan.Enabled = true
        this.txtMaTN_Wan.Text = ''
      } else {
        this.btnMaTN_Wan.Enabled = false
        this.txtMaTN_Wan.Text = ''
      }
    },
    async cboTocDo_SelectedValueChanged () {
      if (this.cboTocDo.Value) {
        await this.HT_COMBO_DONVI()
        this.$emit('cboTocDo_SelectedValueChanged', { tocdo_id: parseInt(this.cboTocDo.Value) })
      }
    },
    async lay_ds_doitac () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_ds_doitac`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_loai_cap () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_ds_loai_cap`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_chuquan () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_ds_chuquan`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_loai_modem () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_loai_modem`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_trangbi () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_trangbi`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_loai_tb () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_loai_tb_theo_dichvu?dichvu_id=8`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_tocdo () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_ds_tocdo`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async HT_COMBO_DONVI () {
      if (!this.idLoaiHinhTb && !this.idKieuLd) {
        this.dsComboDonVi = []
        return
      }

      let dsLoaiDonVi = await this.lay_ds_loai_donvi({
        dichvu_id: this.idDichVu,
        loaihinh_tb: this.idLoaiHinhTb,
        kieu_ld: this.idKieuLd,
        tocdotn_id: this.cboTocDo.Value,
        loaikenh_id: this.vloaikenh_id
      })

      let dsComboDonVi = []

      for (let i = 0; i < dsLoaiDonVi.length; i++) {
        let loaiDonVi = dsLoaiDonVi[i]

        if (loaiDonVi['loaidv_dich_id'] == Enum.LOAI_DV.TRAM_VT) {
          loaiDonVi.dsDonVi = await this.lay_ds_donvi_tramtc({
            loaidv_id: loaiDonVi['loaidv_dich_id'],
            donvi_ql_id: this.idDaiVt
          })
        } else {
          loaiDonVi.dsDonVi = await this.lay_ds_donvi(loaiDonVi['loaidv_dich_id'])
        }

        dsComboDonVi.push(this.newComboDonVi(loaiDonVi))
      }

      this.dsComboDonVi = dsComboDonVi
    },
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
    async lay_ds_donvi_tramtc (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-codinh/lay_ds_donvi_tramtc`, data)
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
    async lay_ds_loai_donvi (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_loai_donvi_1`, data)
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
    }
  }
}
</script>
