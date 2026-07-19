<template src="./index.html"></template>
<script>
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
        ADSL: 4,
        MEGA_EYES: 12
      },
      LOAI_DV: {
        TRAM_VT: 5
      },
      LoaiHinhTB: {
        INTERNET_MYTV: 61,
        INTERNET_SGTV: 65,
        HTVC_FIBERVNN_ANALOG: 18,
        HTVC_FIBERVNN_DIGITAL: 19
      },
      btnMaTN_ADSL: {
        Enabled: true
      },
      txtMaTN_ADSL: {
        Text: '',
        Enabled: true
      },
      cboTrangBiADSL: {
        DataSource: [],
        Value: null
      },
      cboGoiCuocAdsl: {
        DataSource: [],
        Value: null
      },
      cboMucCuoc: {
        DataSource: [],
        Value: null
      },
      cboLoaiTB: {
        DataSource: [],
        Value: null
      },
      txtEmailAdsl: {
        Text: ''
      },
      txtMaDoiCap_ADSL: {
        Text: ''
      },
      ckTuCaiADSL: {
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
      return this.idDichVu == this.DichVuVienThong.ADSL || this.idDichVu == this.DichVuVienThong.MEGA_EYES
    },
    idLoaiHinhTb () {
      return parseInt(this.parent.parent.modelCbo.cboLoaihinhTB)
    },
    idKieuLd () {
      return parseInt(this.parent.parent.modelCbo.cboKieuLD)
    },
    khachHangId () {
      return this.parent.parent.khachhang_id
    },
    hdkhId () {
      return this.parent.parent.hdkh_id
    },
    idDaiVt () {
      return this.parent.parent.modelCbo.cboDaiVT
    }
  },
  watch: {
    idLoaiHinhTb: async function () {
      if (this.isActive) {
        await this.cboLoaihinhTB_SelectedValueChanged()
      }
    },
    idKieuLd: async function () {
      if (this.isActive) {
        await this.cboKieuLD_SelectedValueChanged()
      }
    },
    'cboGoiCuocAdsl.Value': async function () {
      await this.cboGoiCuocAdsl_SelectedValueChanged()
    },
    'cboMucCuoc.Value': async function () {
      this.cboMucCuoc_SelectedValueChanged()
    },
    'cboTrangBiADSL.Value': function () {
      this.cboTrangBiADSL_SelectedValueChanged()
    }
  },
  async mounted () {
    this.cboTrangBiADSL.DataSource = await this.lay_ds_trangbi()
    this.cboTrangBiADSL.Value = this.cboTrangBiADSL.DataSource[0].trangbi_id

    this.cboMucCuoc.DataSource = await this.lay_ds_muccuoc()
    this.cboMucCuoc.Value = this.cboMucCuoc.DataSource[0].muccuoc_id

    this.cboLoaiTB.DataSource = await this.lay_ds_loai_tb()
    this.cboLoaiTB.Value = this.cboLoaiTB.DataSource[0].thietbidc_id
  },
  methods: {
    async TaoDuLieu_HDBC_ADSL (themmoi) {
      let rowHDBCADSL = {}
      let tinh_id = this.parent.parent.BanCheoData.Tinh_TC.tinhthicong_id
      let thuebao_tc_id = this.parent.parent.thuebao_tc_id

      if (!this.parent.parent.kenh_trang) {
        let ds = await this.LAY_DS_DB_ADSL_THEO_TBID(tinh_id, thuebao_tc_id)
        let firstRow = {...ds[0]}
        Object.keys(firstRow).forEach(key => rowHDBCADSL[key.toUpperCase()] = firstRow[key])
      }

      rowHDBCADSL.HDTB_ID = this.parent.parent.hdtb_id
      rowHDBCADSL.EMAIL = this.txtEmailAdsl.Text
      rowHDBCADSL.SO_DT = this.txtMaTN_ADSL.Text

      if (this.ckTuCaiADSL.Checked) { rowHDBCADSL.KH_CD = 1 } else { rowHDBCADSL.KH_CD = 0 }

      let thongso_tc = this.parent.parent.thongso_tc

      rowHDBCADSL.TOCDO_ID = thongso_tc.tocdo_id
      rowHDBCADSL.MUCCUOC_ID = thongso_tc.muccuoc_id
      rowHDBCADSL.CHUQUAN_ID = thongso_tc.chuquan_id
      rowHDBCADSL.CHUQUANLD_ID = thongso_tc.chuquanld_id
      rowHDBCADSL.THIETBIDC_ID = thongso_tc.thietbidc_id

      let KetCuoi_ID = parseInt(this.parent.parent.KetCuoi_ID)

      if (KetCuoi_ID > 0) {
        rowHDBCADSL.KETCUOI_ID = KetCuoi_ID
      }

      let tocDoInfo = await this.lay_tt_tocdo_adsl(thongso_tc.tocdo_id)
      rowHDBCADSL.PROFILEDSL_ID = !tocDoInfo ? null : tocDoInfo.profiledsl_id

      rowHDBCADSL.MATB_TN = thongso_tc.ma_tn

      let thueBaoInfo = await this.lay_tt_thuebao_adsl({tinh_id, thuebao_id: thuebao_tc_id})

      if (thueBaoInfo) {
        rowHDBCADSL.MA_LT = thueBaoInfo.ma_lt
        rowHDBCADSL.PASSWORD = thueBaoInfo.password
      } else {
        rowHDBCADSL.MA_LT = null
        rowHDBCADSL.PASSWORD = null
      }

      let kieu = await this.lay_tt_kieu_ld(thongso_tc.kieuld_id)

      // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
      if (kieu == 1 || kieu == 2 || kieu == 4) {
        if (!thongso_tc.ma_doicap) {
          this.$root.toastError('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập')
          this.parent.parent.kt_adsl = false
          return
        }
        rowHDBCADSL.MADOICAP = parseInt(thongso_tc.ma_doicap)
      } else // TH còn lại sinh mã đôi cáp
      {
        if (themmoi == true) {
          rowHDBCADSL.MADOICAP = await this.get_key_madoicap(tinh_id)
        } else {
          rowHDBCADSL.MADOICAP = parseInt(thongso_tc.ma_doicap)
        }
      }

      rowHDBCADSL.TRANGBI_ID = thongso_tc.trangbi_id

      console.log('rowHDBCADSL', rowHDBCADSL)

      return { dsHDBCADSL: [rowHDBCADSL]}
    },
    async TaoDuLieu_HDTB_ADSL (themmoi) {
      let rowHDTB_ADSL = {}
      let thuebao_id = parseInt(this.parent.parent.thuebao_id)
      let ds = await this.LAY_DS_DB_ADSL_THEO_TBID(this.$root.token.getPhanVungID(), thuebao_id)

      let firstRow = {...ds[0]}
      Object.keys(firstRow).forEach(key => rowHDTB_ADSL[key.toUpperCase()] = firstRow[key])

      rowHDTB_ADSL.HDTB_ID = this.parent.parent.hdtb_id
      rowHDTB_ADSL.EMAIL = this.txtEmailAdsl.Text
      rowHDTB_ADSL.SO_DT = this.txtMaTN_ADSL.Text

      if (this.ckTuCaiADSL.Checked) {
        rowHDTB_ADSL.KH_CD = 1
      } else {
        rowHDTB_ADSL.KH_CD = 0
      }

      rowHDTB_ADSL.TOCDO_ID = this.tocdo_id // this.parent.parent._tocdo_id
      rowHDTB_ADSL.MUCCUOC_ID = parseInt(this.cboMucCuoc.Value)
      rowHDTB_ADSL.THIETBIDC_ID = parseInt(this.cboLoaiTB.Value)
      rowHDTB_ADSL.CHUQUAN_ID = this.parent.parent.thongso_ban.chuquan_id
      rowHDTB_ADSL.CHUQUANLD_ID = this.parent.parent.thongso_ban.chuquanld_id

      let KetCuoi_ID = parseInt(this.parent.parent.KetCuoi_ID)

      if (KetCuoi_ID > 0) {
        rowHDTB_ADSL.KETCUOI_ID = KetCuoi_ID
      }

      let tocDoInfo = await this.lay_tt_tocdo_adsl(this.cboGoiCuocAdsl.Value)
      rowHDTB_ADSL.PROFILEDSL_ID = !tocDoInfo ? null : tocDoInfo.profiledsl_id

      rowHDTB_ADSL.MATB_TN = this.txtMaTN_ADSL.Text

      if (thuebao_id > 0) {
        let thueBaoInfo = await this.lay_tt_thuebao_adsl({tinh_id: this.$root.token.getPhanVungID(), thuebao_id})

        if (thueBaoInfo) {
          rowHDTB_ADSL.MA_LT = thueBaoInfo.ma_lt
          rowHDTB_ADSL.PASSWORD = thueBaoInfo.password
        } else {
          rowHDTB_ADSL.MA_LT = null
          rowHDTB_ADSL.PASSWORD = null
        }
      }

      let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

      // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
      if (kieu == 1 || kieu == 2 || kieu == 4) {
        if (!this.txtMaDoiCap_ADSL.Text) {
          this.$root.toastError('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập')
          this.parent.parent.kt_adsl = false
          return
        }
        rowHDTB_ADSL.MADOICAP = parseInt(this.txtMaDoiCap_ADSL.Text)
      } else // TH còn lại sinh mã đôi cáp
      {
        if (themmoi == true) {
          rowHDTB_ADSL.MADOICAP = await this.get_key_madoicap()
          this.txtMaDoiCap_ADSL.Text = rowHDTB_ADSL.MADOICAP
        } else {
          rowHDTB_ADSL.MADOICAP = parseInt(this.txtMaDoiCap_ADSL.Text)
        }
      }

      rowHDTB_ADSL.TRANGBI_ID = parseInt(this.cboTrangBiADSL.Value)

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
        dsHDTB_ADSL: [rowHDTB_ADSL],
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
    async lay_tt_thuebao_adsl (data) {
      try {
        data = { tinhthicong_id: data.tinh_id, thuebao_id: data.thuebao_id }
        let response = await this.$root.context.post(`/web-bancheo/chuyendich-bancheo-dichvu/lay_tt_thuebao_adsl`, data)
        return !response.data ? null : response.data[0]
      } catch (err) {
        return null
      }
    },
    async lay_tt_tocdo_adsl (tocdo_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_tt_tocdo_adsl`, {tocdo_id})
        return response.data
      } catch (err) {
        return null
      }
    },
    async LAY_DS_DB_ADSL_THEO_TBID (tinh_id, thueBaoId) {
      try {
        let data = { tinhthicong_id: tinh_id, thuebao_id: thueBaoId }
        let response = await this.$root.context.post(`/web-hopdong/chuyen-dich-ban-cheo/lay_ds_db_adsl_theo_tbid`, data)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async LAY_DS_HDTB_ADSL_THEO_HDTB_ID (hdtb_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_ds_hdtb_adsl_theo_hdtb_id?hdtb_id=${hdtb_id}`)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async HienThiTTMoRongHD (hdtb_id) {
      let ds = await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id)

      if (ds.length <= 0) {
        return
      }

      this.txtMaTN_ADSL.Text = ds[0]['matb_tn']
      this.parent.parent.SoMayTN = this.txtMaTN_ADSL.Text
      this.txtEmailAdsl.Text = ds[0]['email']

      if (parseInt(ds[0]['kh_cd']) == 1) { this.ckTuCaiADSL.Checked = true } else { this.ckTuCaiADSL.Checked = false }

      this.cboGoiCuocAdsl.Value = parseInt(ds[0]['tocdo_id']) + ''
      this.parent.parent._tocdo_id = parseInt(ds[0]['tocdo_id'])
      this.cboMucCuoc.Value = parseInt(ds[0]['muccuoc_id']) + ''
      this.cboLoaiTB.Value = parseInt(ds[0]['thietbidc_id']) + ''
      this.txtMaDoiCap_ADSL.Text = ds[0]['madoicap']

      if (ds[0]['trangbi_id']) { this.cboTrangBiADSL.Value = ds[0]['trangbi_id'] + '' }

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
      let ds = await this.LAY_DS_DB_ADSL_THEO_TBID(this.$root.token.getPhanVungID(), thuebao_id)

      if (ds.length <= 0) {
        return
      }

      this.txtEmailAdsl.Text = ds[0]['email']
      this.cboGoiCuocAdsl.Value = parseInt(ds[0]['tocdo_id']) + ''
      this.parent.parent._tocdo_id = parseInt(ds[0]['tocdo_id'])
      this.cboMucCuoc.Value = ds[0]['muccuoc_id'] + ''
      this.txtMaDoiCap_ADSL.Text = ds[0]['madoicap']

      if (ds[0]['thietbidc_id']) {
        this.cboLoaiTB.Value = ds[0]['thietbidc_id'] + ''
      } else {
        this.cboLoaiTB.Value = null
      }

      if (ds[0]['trangbi_id']) {
        this.cboTrangBiADSL.Value = ds[0]['trangbi_id'] + ''
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
    async cboLoaihinhTB_SelectedValueChanged () {
      await this.HT_Tocdo_Adsl_Combobox_ByLoaiTB()
      await this.HT_COMBO_DONVI()
    },
    async cboKieuLD_SelectedValueChanged () {
      await this.HT_COMBO_DONVI()

      if (!this.idKieuLd) {
        return
      }

      let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

      if (kieu == 1 || kieu == 2 || kieu == 4) {
        this.btnMaTN_ADSL.Enabled = true
        this.txtMaTN_ADSL.Text = ''
      } else {
        this.btnMaTN_ADSL.Enabled = false
        this.txtMaTN_ADSL.Text = ''
      }
    },
    async HT_Tocdo_Adsl_Combobox_ByLoaiTB () {
      if (!this.idLoaiHinhTb) {
        this.cboGoiCuocAdsl.DataSource = []
        return
      }
      this.cboGoiCuocAdsl.DataSource = await this.lay_ds_tocdo_adsl(this.idLoaiHinhTb)

      if (this.cboGoiCuocAdsl.DataSource.length > 0) {
        // this.cboGoiCuocAdsl.Value = this.cboGoiCuocAdsl.DataSource[0].tocdo_id
      }
    },
    async cboMucCuoc_SelectedValueChanged () {
      if (this.cboMucCuoc.Value) {
        await this.HT_COMBO_DONVI()
      }
    },
    async cboGoiCuocAdsl_SelectedValueChanged () {
      if (this.cboGoiCuocAdsl.Value) {
        await this.HT_COMBO_DONVI()
      }
    },
    cboTrangBiADSL_SelectedValueChanged () {
      if (this.cboTrangBiADSL.Value) {
        this.$emit('cboTrangBiADSL_SelectedValueChanged', { trangbi_id: this.cboTrangBiADSL.Value })
      }
    },
    async btnMaTN_ADSL_Click () {
      let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

      if (kieu == 2) { // Dịch chuyển trên đường có sẵn
        if (parseInt(this.khachHangId) > 0) {
          let frm = this.$refs.frmDSTBCungDoiCap
          frm.khachhang_id = parseInt(this.khachHangId)
          frm.kieuld_id = parseInt(this.idKieuLd)
          frm.loaitb_id = parseInt(this.idLoaiHinhTb)
          let data = await frm.show()

          if (data.chapnhan) {
            this.txtMaTN_ADSL.Text = data.ma_tn
            this.txtMaDoiCap_ADSL.Text = data.ma_doicap
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
            this.txtMaTN_ADSL.Text = data.ma_tn
            this.txtMaDoiCap_ADSL.Text = data.ma_doicap
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
          this.txtMaTN_ADSL.Text = data.ma_tn
          this.txtMaDoiCap_ADSL.Text = data.ma_doicap
        }
      }
    },
    async HT_COMBO_DONVI () {
      if (!this.idLoaiHinhTb && !this.idKieuLd) {
        this.dsComboDonVi = []
        return
      }

      let muccuoctn_id = 0

      if (parseInt(this.idLoaiHinhTb) != this.LoaiHinhTB.INTERNET_MYTV &&
                    parseInt(this.idLoaiHinhTb) != this.LoaiHinhTB.INTERNET_SGTV &&
                    parseInt(this.idLoaiHinhTb) != this.LoaiHinhTB.HTVC_FIBERVNN_ANALOG &&
                    parseInt(this.idLoaiHinhTb) != this.LoaiHinhTB.HTVC_FIBERVNN_DIGITAL) {
        muccuoctn_id = parseInt(this.cboMucCuoc.Value)
      }

      let dsLoaiDonVi = await this.lay_ds_loai_donvi({
        dichvu_id: this.idDichVu,
        loaihinh_tb: this.idLoaiHinhTb,
        kieu_ld: this.idKieuLd,
        tocdotn_id: this.cboGoiCuocAdsl.Value, 
        muccuoctn_id: muccuoctn_id
      })

      let dsComboDonVi = []

      for (let i = 0; i < dsLoaiDonVi.length; i++) {
        let loaiDonVi = dsLoaiDonVi[i]

        if (loaiDonVi['loaidv_dich_id'] == this.LOAI_DV.TRAM_VT) {
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
    async lay_ds_loai_donvi (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_loai_donvi`, data)
        return response.data
      } catch (err) {
        return []
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
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_donvi?loaidv_id=${loaidv_id}`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_loai_tb () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_loai_tb_theo_dichvu?dichvu_id=4`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_tocdo_adsl (loaitb_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_ds_tocdo_adsl?loaitb_id=${loaitb_id}`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_muccuoc () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_ds_muccuoc`)
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
