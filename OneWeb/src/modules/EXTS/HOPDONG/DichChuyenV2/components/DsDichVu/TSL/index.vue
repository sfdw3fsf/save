<template src="./index.html"></template>
<script>
import ModalChonDiaChi from '@/modules/EXTS/HOPDONG/ChuanHoaDLKhachHang/popupChonDiaChi'
import ChonDonVi from '@/modules/EXTS/HOPDONG/DichChuyenNew/components/ThongTinKhachHang/ChonDonVi'
import Enum from '../../../Enum.js'
export default {
  props: ['idKieuLd', 'parent'],
  components: {
    ModalChonDiaChi,
    ChonDonVi
  },
  data () {
    return {
      LOAI_DV: {
        TRAM_VT: 5
      },
      cboTocDoKenhTSL: {
        DataSource: [],
        Value: null
      },
      cboTrangBi: {
        DataSource: [],
        Value: null
      },
      cboChuQuan: {
        DataSource: [],
        Value: null
      },
      txtMaHD_TTKD_TSL: {
        Text: null
      },
      txtSoAoTSL_Dau: {
        Text: null
      },
      txtSoAoTSL_Cuoi: {
        Text: null
      },
      cboLoaiTBTSL_Dau: {
        DataSource: [],
        Value: null
      },
      cboLoaiTBTSL_Cuoi: {
        DataSource: [],
        Value: null
      },
      txtCuocTK_TSL: {
        Text: 0
      },
      txtCuocTC_TSL: {
        Text: 0
      },
      chkDoiTac_TSL: {
        Checked: false
      },
      cboDoiTac_TSL: {
        DataSource: [],
        Value: null,
        Enabled: false
      },
      txtCuocTKDT_TSL: {
        Text: 0,
        Enabled: false
      },
      txtCuocHT_TSL: {
        Text: 0
      },
      txtMa_HDDT_TSL: {
        Text: null,
        Enabled: false
      },
      txtCuocTCDT_TSL: {
        Text: 0,
        Enabled: false
      },
      txtCuocTBI_TSL: {
        Text: 0
      },
      chkDC_Dau: {
        Checked: false
      },
      chkDC_Cuoi: {
        Checked: false
      },
      txtDiaChiDau: {
        Text: null,
        Enabled: false
      },
      txtDiaChiCuoi: {
        Text: null,
        Enabled: false
      },
      cboTramVTDau: {
        DataSource: [],
        Value: null,
        Enabled: false
      },
      cboTramVTCuoi: {
        DataSource: [],
        Value: null,
        Enabled: false
      },
      cboLoaiCap_Dau: {
        DataSource: [],
        Value: null
      },
      cboLoaiCap_Cuoi: {
        DataSource: [],
        Value: null
      },
      btnChonDCDau: {
        Enabled: false
      },
      btnChonDCCuoi: {
        Enabled: false
      },
      diaChiDau: {
        tieuDe: 'Địa chỉ',
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        so_nha: '',
        diachi: null,
        lat: 0,
        lng: 0
      },
      diaChiDauDuocChon: null,
      diaChiCuoi: {
        tieuDe: 'Địa chỉ',
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        so_nha: '',
        diachi: null,
        lat: 0,
        lng: 0
      },
      diaChiCuoiDuocChon: null,
      dsComboDonVi: [],
      _tocdo_id: null,
      vloaikenh_id: 0,
      diachidau_id: null,
      diachicuoi_id: null,
      Lng_DD: null,
      Lat_DD: null,
      Lng_DC: null,
      Lat_DC: null,
      tinhdau_id: null,
      quandau_id: null,
      phuongdau_id: null,
      phodau_id: null,
      apdau_id: null,
      khudau_id: null,
      sonhadau: null,
      tinhcuoi_id: null,
      quancuoi_id: null,
      phuongcuoi_id: null,
      phocuoi_id: null,
      apcuoi_id: null,
      khucuoi_id: null,
      sonhacuoi: null,
      donvi_dau_id: null,
      donvi_cuoi_id: null,
      ma_tb_dau: null,
      ma_tb_cuoi: null
    }
  },
  computed: {
    idDaiVt () {
      return this.parent.modelCbo.cboDaiVT
    },
    idLoaihinhTb() {
        return this.parent.modelCbo.cboLoaihinhTB
    },
    phuongld_id() {
        return this.parent.inputValue.phuongld_id
    },
    phold_id() {
        return this.parent.inputValue.phold_id 
    },
    apld_id() {
        return this.parent.inputValue.apld_id 
    },
    khuld_id() {
        return this.parent.inputValue.khuld_id 
    },
    dacdiemld_id() {
        return this.parent.inputValue.dacdiemld_id 
    },
    ht_loc_cbo_tram() {
        return this.parent.inputValue.ht_loc_cbo_tram
    }
  },
  async mounted () {
    this.cboTrangBi.DataSource = await this.lay_ds_trangbi()
    this.cboTrangBi.Value = this.cboTrangBi.DataSource[0].trangbi_id

    this.cboTocDoKenhTSL.DataSource = await this.lay_ds_tocdo()
    this.cboTocDoKenhTSL.Value = this.cboTocDoKenhTSL.DataSource[0].tocdo_id

    this.cboDoiTac_TSL.DataSource = await this.lay_ds_doitac()
    this.cboDoiTac_TSL.Value = this.cboDoiTac_TSL.DataSource[0].doitac_id

    this.cboLoaiCap_Dau.DataSource = await this.lay_ds_loai_cap()
    this.cboLoaiCap_Dau.Value = this.cboLoaiCap_Dau.DataSource[0].loaicap_id

    this.cboLoaiCap_Cuoi.DataSource = [...this.cboLoaiCap_Dau.DataSource]
    this.cboLoaiCap_Cuoi.Value = this.cboLoaiCap_Cuoi.DataSource[0].loaicap_id

    this.cboChuQuan.DataSource = await this.lay_ds_chuquan()
    this.cboChuQuan.Value = this.cboChuQuan.DataSource[0].chuquan_id

    this.cboLoaiTBTSL_Dau.DataSource = await this.lay_ds_loai_tb()
    this.cboLoaiTBTSL_Dau.Value = this.cboLoaiTBTSL_Dau.DataSource[0].thietbidc_id

    this.cboLoaiTBTSL_Cuoi.DataSource = [...this.cboLoaiTBTSL_Dau.DataSource]
    this.cboLoaiTBTSL_Cuoi.Value = this.cboLoaiTBTSL_Cuoi.DataSource[0].thietbidc_id
  },
  watch: {
    'chkDC_Cuoi.Checked': function (value) {
      this.chkDC_Cuoi_CheckedChanged(value)
    },
    'chkDC_Dau.Checked': function (value) {
      this.chkDC_Dau_CheckedChanged(value)
    },
    'chkDoiTac_TSL.Checked': function (value) {
      this.chkDoiTac_TSL_CheckedChanged(value)
    },
    'cboTrangBi.Value': function (value) {
      this.cboTrangBi_SelectedValueChanged(value)
    },
    'cboTocDoKenhTSL.Value': function (value) {
      this.cboTocDoKenhTSL_EditValueChanged(value)
    },
    idKieuLd: async function (value) {
      if (!value) {
        this.dsComboDonVi = []
        return
      }

      await this.HT_COMBO_DONVI()
    },
    idDaiVt: async function (value) {
      if (!value) {
        this.dsComboDonVi = []
        return
      }

      await this.HT_COMBO_DONVI()
    },
    donvi_cuoi_id (val) {
      this.parent.inputValue.donvi_cuoi_id = val
    },
    donvi_dau_id (val) {
      this.parent.inputValue.donvi_dau_id = val
    }
  },
  methods: {
    async HienThiTTMoRongHD (hdtb_id) {

      let ds = await this.LAY_DS_HDTB_TSL_THEO_HDTB_ID(hdtb_id)

      if (ds.length <= 0) {
        return
      }

      this.cboTocDoKenhTSL.Value = parseInt(ds[0]['tocdo_id']) + ''
      this._tocdo_id = parseInt(ds[0]['tocdo_id'])
      this.vloaikenh_id = parseInt(ds[0]['loaikenh_id'])
      this.cboTrangBi.Value = parseInt(ds[0]['trangbi_id']) + ''
      this.cboChuQuan.Value = parseInt(ds[0]['chuquan_id']) + ''
      this.txtMaHD_TTKD_TSL.Text = ds[0]['ma_pl']

      this.parent.modelCkeckBox.chkdisableCongVan = false

      if (ds[0]['ma_cv']) {
        this.parent.modelCkeckBox.chkCongVan = true
        this.parent.modelInput.txtCongVan = ds[0]['ma_cv']
        this.parent.modelDateTime.dtpNgayCV = new Date(ds[0]['ngay_cv'])
      }

      this.txtCuocTK_TSL.Text = ds[0]['cuoc_tk']
      this.txtCuocTC_TSL.Text = ds[0]['cuoc_tc']
      this.txtCuocHT_TSL.Text = ds[0]['cuoc_ht']
      this.txtCuocTBI_TSL.Text = ds[0]['cuoc_tbi']

      let dc_daucuoi = parseInt(ds[0]['dc_daucuoi'])

      if (dc_daucuoi == 1) {
        this.chkDC_Dau.Checked = true
        this.btnChonDCDau.Enabled = true
        this.txtDiaChiDau.Enabled = true
        this.cboTramVTDau.Enabled = true
      }

      if (dc_daucuoi == 2) {
        this.chkDC_Cuoi.Checked = true
        this.btnChonDCCuoi.Enabled = true
        this.txtDiaChiCuoi.Enabled = true
        this.cboTramVTCuoi.Enabled = true
      }

      if (dc_daucuoi == 3) {
        this.chkDC_Dau.Checked = true
        this.btnChonDCDau.Enabled = true
        this.txtDiaChiDau.Enabled = true
        this.cboTramVTDau.Enabled = true

        this.chkDC_Cuoi.Checked = true
        this.btnChonDCCuoi.Enabled = true
        this.txtDiaChiCuoi.Enabled = true
        this.cboTramVTCuoi.Enabled = true
      }

      if (ds[0]['doitac_id']) {
        this.chkDoiTac_TSL.Checked = true
        this.cboDoiTac_TSL.Value = parseInt(ds[0]['doitac_id']) + ''

        if (ds[0]['cuoc_tcdt']) { this.txtCuocTCDT_TSL.Text = ds[0]['cuoc_tcdt'] } else { this.txtCuocTCDT_TSL.Text = '0' }

        if (ds[0]['cuoc_tkdt']) { this.txtCuocTKDT_TSL.Text = ds[0]['cuoc_tkdt'] } else { this.txtCuocTKDT_TSL.Text = '0' }

        this.txtMa_HDDT_TSL.Text = ds[0]['mahd_dt']
      } else {
        this.chkDoiTac_TSL.Checked = false
        this.txtCuocTCDT_TSL.Text = '0'
        this.txtCuocTKDT_TSL.Text = '0'
        this.txtMa_HDDT_TSL.Text = ''
      }

      this.parent.modelInput.txtMaBSS = ds[0]['ma_bss']
      this.parent.modelInput.txtMaERP = ds[0]['id_erp']

      for (let i = 0; i < ds.length; i++) {
        if (parseInt(ds[i]['daucuoi_id']) == 1) // Nếu là điểm đầu
        {
          this.diachidau_id = parseInt(ds[i]['diachi_id'])

          if (ds[i]['kinhdo_ld']) { this.Lng_DD = parseFloat(ds[i]['kinhdo_ld']) } else { this.Lng_DD = 0 }

          if (ds[i]['vido_ld']) { this.Lat_DD = parseFloat(ds[i]['vido_ld']) } else { this.Lat_DD = 0 }

          let thueBaoInfo = await this.lay_tt_hd_thuebao(hdtb_id)
          this.donvi_dau_id = thueBaoInfo.donvi_dau_id

          this.cboTramVTDau.DataSource = await this.lay_donvi_loaidv({
            donvi_id: this.donvi_dau_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT
          })

          this.txtDiaChiDau.Text = ds[i]['diachi']
          this.ma_tb_dau = ds[i]['ma_tb_sub']
          this.cboLoaiTBTSL_Dau.Value = parseInt(ds[i]['thietbidc_id']) + ''
          this.txtSoAoTSL_Dau.Text = ds[i]['ma_lt']

          let donViInfo = await this.lay_tt_donvi_cua_hdtb({
            hdtb_id: hdtb_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
            kieudv_id: 1
          })

          if (donViInfo) {
            this.cboTramVTDau.Value = donViInfo['donvi_id'] + ''
          }

          this.cboLoaiCap_Dau.Value = parseInt(ds[i]['loaicap_id']) + ''
        } else {
          this.diachicuoi_id = parseInt(ds[i]['diachi_id'])

          if (ds[i]['kinhdo_ld']) { this.Lng_DC = parseFloat(ds[i]['kinhdo_ld']) } else { this.Lng_DC = 0 }

          if (ds[i]['vido_ld']) { this.Lat_DC = parseFloat(ds[i]['vido_ld']) } else { this.Lat_DC = 0 }

          let thueBaoInfo = await this.lay_tt_hd_thuebao(hdtb_id)
          this.donvi_cuoi_id = thueBaoInfo.donvi_cuoi_id

          this.cboTramVTCuoi.DataSource = await this.lay_donvi_loaidv({
            donvi_id: this.donvi_cuoi_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT
          })

          this.txtDiaChiCuoi.Text = ds[i]['diachi']
          this.ma_tb_cuoi = ds[i]['ma_tb_sub']
          this.cboLoaiTBTSL_Cuoi.Value = parseInt(ds[i]['thietbidc_id']) + ''
          this.txtSoAoTSL_Cuoi.Text = ds[i]['ma_lt']

          let donViInfo = await this.lay_tt_donvi_cua_hdtb({
            hdtb_id: hdtb_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
            kieudv_id: 2
          })

          if (donViInfo) {
            this.cboTramVTCuoi.Value = donViInfo['donvi_id'] + ''
          }

          this.cboLoaiCap_Cuoi.Value = parseInt(ds[i]['loaicap_id']) + ''
        }
      }

      await this.HienThiTTDiaChi_TSL()

      await this.HT_COMBO_DONVI();

      //Lấy ra các đơn vị được chọn
      let dsDonVi = await this.lay_ds_donvi_cua_hopdong(hdtb_id);

      setTimeout(() => {
          if (dsDonVi.length > 0)
          {
              for (let i = 0; i < this.dsComboDonVi.length; i++)
              {
                  let cbo = this.dsComboDonVi[i]
                  let donVi = dsDonVi.find(item => item.loaidv_id == parseInt(cbo.loaiDonVi));

                  if (donVi)
                  {
                      this.$refs[cbo.key][0].value = donVi["donvi_id"];
                  }
              }
          }
      }, 300)
    },
    async lay_ds_donvi_cua_hopdong(hdtb_id) {
        try {
            let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_donvi_cua_hopdong`, { hdtb_id })
            return response.data
        } catch (err) {
            return []
        }
    },
    async LAY_DS_HDTB_TSL_THEO_HDTB_ID (hdtb_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_ds_hdtb_tsl_theo_hdtb_id?hdtb_id=${hdtb_id}`)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async HienThiTTMoRongTB (thuebao_id) {
      let ds = await this.LAY_DS_DB_TSL_THEO_TBID(thuebao_id)

      if (ds.length <= 0) {
        return
      }

      let data = ds[0]

      this.cboTocDoKenhTSL.Value = parseInt(data['tocdo_id'])
      this._tocdo_id = parseInt(data['tocdo_id'])
      this.vloaikenh_id = parseInt(data['loaikenh_id'])
      this.cboTrangBi.Value = parseInt(data['trangbi_id'])
      this.cboChuQuan.Value = parseInt(data['chuquan_id'])
      this.txtMaHD_TTKD_TSL.Text = data['ma_pl']

      this.txtCuocTK_TSL.Text = data['cuoc_tk']
      this.txtCuocTC_TSL.Text = data['cuoc_tc']
      this.txtCuocHT_TSL.Text = data['cuoc_ht']
      this.txtCuocTBI_TSL.Text = data['cuoc_tbi']

      if (data['doitac_id']) {
        this.chkDoiTac_TSL.Checked = true
        this.cboDoiTac_TSL.Value = parseInt(data['doitac_id'])

        if (data['cuoc_tcdt']) {
          this.txtCuocTCDT_TSL.Text = data['cuoc_tcdt']
        } else {
          this.txtCuocTCDT_TSL.Text = '0'
        }

        if (data['cuoc_tkdt']) {
          this.txtCuocTKDT_TSL.Text = data['cuoc_tkdt']
        } else {
          this.txtCuocTKDT_TSL.Text = '0'
        }

        this.txtMa_HDDT_TSL.Text = data['mahd_dt']
      } else {
        this.chkDoiTac_TSL.Checked = false
        this.txtCuocTCDT_TSL.Text = '0'
        this.txtCuocTKDT_TSL.Text = '0'
        this.txtMa_HDDT_TSL.Text = ''
      }

      for (let i = 0; i < ds.length; i++) {
        if (parseInt(ds[i]['daucuoi_id']) == 1)// Nếu là điểm đầu
        {
          this.diachidau_id = parseInt(ds[i]['diachi_id'])

          if (ds[i]['kinhdo_ld']) {
            this.Lng_DD = ds[i]['kinhdo_ld']
          } else {
            this.Lng_DD = 0
          }

          if (ds[i]['vido_ld']) {
            this.Lat_DD = ds[i]['vido_ld']
          } else {
            this.Lat_DD = 0
          }

          this.cboLoaiTBTSL_Dau.Value = parseInt(ds[i]['thietbidc_id'])
          this.txtSoAoTSL_Dau.Text = ds[i]['ma_lt']
          this.cboLoaiCap_Dau.Value = ds[i]['loaicap_id'] + ''
        } else {
          this.diachicuoi_id = ds[i]['diachi_id']

          if (ds[i]['kinhdo_ld']) {
            this.Lng_DC = ds[i]['kinhdo_ld']
          } else {
            this.Lng_DC = 0
          }

          if (ds[i]['vido_ld']) {
            this.Lat_DC = ds[i]['vido_ld']
          } else {
            this.Lat_DC = 0
          }

          this.cboLoaiTBTSL_Cuoi.Value = ds[i]['thietbidc_id']
          this.txtSoAoTSL_Cuoi.Text = ds[i]['ma_lt']
          this.cboLoaiCap_Cuoi.Value = ds[i]['loaicap_id']
        }
      }

      await this.HienThiTTDiaChi_TSL()

      for (let i = 0; i < ds.length; i++) {
        if (parseInt(ds[i]['daucuoi_id']) == 1) // Nếu là điểm đầu
        {
          let thueBaoInfo = await this.lay_tt_thuebao(thuebao_id)
          this.donvi_dau_id = thueBaoInfo.donvi_dau_id

          this.cboTramVTDau.DataSource = await this.lay_donvi_loaidv({
            donvi_id: this.donvi_dau_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT
          })

          this.txtDiaChiDau.Text = ds[i]['diachi']
          this.ma_tb_dau = ds[i]['ma_tb_sub']

          let donViInfo = await this.lay_tt_donvi_cua_tbao({
            thuebao_id: thuebao_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
            kieudv_id: 1
          })

          if (donViInfo) {
            this.cboTramVTDau.Value = donViInfo['donvi_id']
          }
        } else {
          let thueBaoInfo = await this.lay_tt_thuebao(thuebao_id)
          this.donvi_cuoi_id = thueBaoInfo.donvi_cuoi_id

          this.cboTramVTCuoi.DataSource = await this.lay_donvi_loaidv({
            donvi_id: this.donvi_cuoi_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT
          })

          this.txtDiaChiCuoi.Text = ds[i]['diachi']
          this.ma_tb_cuoi = ds[i]['ma_tb_sub']

          let donViInfo = await this.lay_tt_donvi_cua_tbao({
            thuebao_id: thuebao_id,
            loaidv_id: Enum.LOAI_DV.TRAM_VT,
            kieudv_id: 2
          })

          if (donViInfo) {
            this.cboTramVTCuoi.Value = donViInfo['donvi_id']
          }
        }
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
    async lay_donvi_loaidv (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-tsl/lay_donvi_loaidv`, data)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_tt_donvi_cua_tbao (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-tsl/lay_tt_donvi_cua_tbao`, data)
        return response.data
      } catch (err) {
        return null
      }
    },
    async lay_tt_donvi_cua_hdtb (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-tsl/lay_tt_donvi_cua_hdtb`, data)
        return response.data
      } catch (err) {
        return null
      }
    },
    async lay_tt_hd_thuebao (hdtb_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_tt_hd_thuebao?hdtb_id=${hdtb_id}`)
        return response.data
      } catch (err) {
        return null
      }
    },
    async lay_tt_thuebao (thuebao_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_tt_thuebao?thuebao_id=${thuebao_id}`)
        return response.data
      } catch (err) {
        return null
      }
    },
    async HienThiTTDiaChi_TSL () {
      let diaChiDauInfo = await this.LAY_THONGTIN_DIACHI(this.diachidau_id)

      if (diaChiDauInfo) {
        this.diachidau_id = diaChiDauInfo['diachi_id']
        this.tinhdau_id = diaChiDauInfo['tinh_id']
        this.quandau_id = diaChiDauInfo['quan_id']
        this.phuongdau_id = diaChiDauInfo['phuong_id']

        if (diaChiDauInfo['pho_id']) {
          this.phodau_id = diaChiDauInfo['pho_id']
        } else {
          this.phodau_id = 0
        }

        if (diaChiDauInfo['ap_id']) {
          this.apdau_id = diaChiDauInfo['ap_id']
        } else {
          this.apdau_id = 0
        }

        if (diaChiDauInfo['khu_id']) {
          this.khudau_id = diaChiDauInfo['khu_id']
        } else {
          this.khudau_id = 0
        }

        this.sonhadau = diaChiDauInfo['sonha']
      }

      let diaChiCuoiInfo = await this.LAY_THONGTIN_DIACHI(this.diachicuoi_id)

      if (diaChiCuoiInfo) {
        this.diachicuoi_id = diaChiCuoiInfo['diachi_id']
        this.tinhcuoi_id = diaChiCuoiInfo['tinh_id']
        this.quancuoi_id = diaChiCuoiInfo['quan_id']
        this.phuongcuoi_id = diaChiCuoiInfo['phuong_id']

        if (diaChiCuoiInfo['pho_id']) {
          this.phocuoi_id = diaChiCuoiInfo['pho_id']
        } else {
          this.phocuoi_id = 0
        }

        if (diaChiCuoiInfo['ap_id']) {
          this.apcuoi_id = diaChiCuoiInfo['ap_id']
        } else {
          this.apcuoi_id = 0
        }

        if (diaChiCuoiInfo['khu_id']) {
          this.khucuoi_id = diaChiCuoiInfo['khu_id']
        } else {
          this.khucuoi_id = 0
        }

        this.sonhacuoi = diaChiCuoiInfo['sonha']
      }
    },
    async LAY_THONGTIN_DIACHI (diachi_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_thongtin_diachi?diachi_id=${diachi_id}`)
        return response.data
      } catch (err) {
        return null
      }
    },
    async LAY_DS_DB_TSL_THEO_TBID (thuebao_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_ds_db_tsl_theo_tbid?thuebao_id=${thuebao_id}`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async TaoDuLieu_HDTB_TSL (themmoi) {
      let dsHDTB_TSL = []
      let rowHDTB_TSL = null
      let dsHDTBDV = []
      let row = null

      let dc_daucuoi = 0

      if (this.chkDC_Dau.Checked && this.chkDC_Cuoi.Checked) {
        dc_daucuoi = 3
      } else {
        if (this.chkDC_Dau.Checked) {
          dc_daucuoi = 1
        }

        if (this.chkDC_Cuoi.Checked) {
          dc_daucuoi = 2
        }
      }

      let ds = await this.LAY_DS_DB_TSL_THEO_TBID(this.parent.inputValue.thuebao_id)

      for (let i = 0; i < 2; i++) {
        rowHDTB_TSL = {}
        rowHDTB_TSL.HDTB_ID = this.parent.inputValue.hdtb_id
        rowHDTB_TSL.DAUCUOI_ID = i + 1
        rowHDTB_TSL.TOCDO_ID = this._tocdo_id
        rowHDTB_TSL.TRANGBI_ID = parseInt(this.cboTrangBi.Value)
        rowHDTB_TSL.CHUQUAN_ID = parseInt(this.cboChuQuan.Value)
        rowHDTB_TSL.MA_PL = this.txtMaHD_TTKD_TSL.Text
        rowHDTB_TSL.DC_DAUCUOI = dc_daucuoi

        if (this.parent.inputValue.KetCuoi_ID > 0) {
          rowHDTB_TSL.KETCUOI_ID = this.parent.inputValue.KetCuoi_ID
        }

        if (this.parent.modelCkeckBox.chkCongVan) {
          rowHDTB_TSL.MA_CV = this.parent.modelInput.txtCongVan
          rowHDTB_TSL.NGAY_CV = this.parent.modelDateTime.dtpNgayCV
        }

        rowHDTB_TSL.CUOC_TC = !this.txtCuocTC_TSL.Text ? 0 : this.txtCuocTC_TSL.Text
        rowHDTB_TSL.CUOC_TK = !this.txtCuocTC_TSL.Text ? 0 : this.txtCuocTC_TSL.Text
        rowHDTB_TSL.CUOC_HT = !this.txtCuocHT_TSL.Text ? 0 : this.txtCuocHT_TSL.Text
        rowHDTB_TSL.CUOC_TBI = !this.txtCuocTBI_TSL.Text ? 0 : this.txtCuocTBI_TSL.Text

        if (this.chkDoiTac_TSL.Checked) {
          rowHDTB_TSL.DOITAC_ID = parseInt(this.cboDoiTac_TSL.Value)
          rowHDTB_TSL.CUOC_TKDT = !this.txtCuocTKDT_TSL.Text ? 0 : this.txtCuocTKDT_TSL.Text
          rowHDTB_TSL.CUOC_TCDT = !this.txtCuocTCDT_TSL.Text ? 0 : this.txtCuocTCDT_TSL.Text
          rowHDTB_TSL.MAHD_DT = this.txtMa_HDDT_TSL.Text
        }

        rowHDTB_TSL.MA_BSS = this.parent.modelInput.txtMaBSS
        rowHDTB_TSL.ID_ERP = this.parent.modelInput.txtMaERP

        row = {}
        row.HDTB_ID = this.parent.inputValue.hdtb_id
        row.LOAIDV_ID = Enum.LOAI_DV.TRAM_VT
        row.KIEUDV_ID = i + 1

        if (i == 0) // Nếu là điểm đầu
        {
          rowHDTB_TSL.DIACHI = this.txtDiaChiDau.Text
          rowHDTB_TSL.DIACHI_ID = this.diachidau_id
          rowHDTB_TSL.KINHDO_LD = !this.Lng_DD ? 0 : this.Lng_DD,
          rowHDTB_TSL.VIDO_LD = !this.Lat_DD ? 0 : this.Lat_DD,
          rowHDTB_TSL.MA_TB_SUB = this.ma_tb_dau
          rowHDTB_TSL.MA_LT = this.txtSoAoTSL_Dau.Text
          rowHDTB_TSL.THIETBIDC_ID = parseInt(this.cboLoaiTBTSL_Dau.Value)
          row.DONVI_ID = parseInt(this.cboTramVTDau.Value)

          if (ds.length > 0) {
            if (ds[0]['daucuoi_id'] == 1) // điểm đầu
            {
              rowHDTB_TSL.LOAIKENH_ID = parseInt(ds[0]['loaikenh_id'])

              if (dc_daucuoi == 2) {
                rowHDTB_TSL.LOAICAP_ID = parseInt(ds[0]['loaicap_id'])
                rowHDTB_TSL.TEN_TBI = ds[0]['ten_tbi']
                rowHDTB_TSL.SERIAL = ds[0]['serial']
                rowHDTB_TSL.IP_WAN = ds[0]['ip_wan']
                rowHDTB_TSL.LINK = ds[0]['link']
                rowHDTB_TSL.TIMESLOT = ds[0]['timeslot']

                let l_doicap_ngon = -1
                let l_doicap_goc = -1
                let l_loaicap_id = 0
                let l_culy = 0

                if (ds[0]['doicap_p'] != null && `${ds[0]['doicap_p']}` != '') { l_doicap_ngon = parseInt(ds[0]['doicap_p']) }

                if (ds[0]['doicap_goc'] != null && `${ds[0]['doicap_goc']}` != '') { l_doicap_goc = parseInt(ds[0]['doicap_goc']) }

                if (ds[0]['loaicap_id'] != null && `${ds[0]['loaicap_id']}` != '') { l_loaicap_id = parseInt(ds[0]['loaicap_id']) }

                if (ds[0]['culy'] != null && `${ds[0]['culy']}` != '') { l_culy = parseInt(ds[0]['culy']) }

                rowHDTB_TSL.TUYENCAP = ds[0]['tuyencap']
                rowHDTB_TSL.LIENTU = ds[0]['lientu']
                rowHDTB_TSL.HOPCAP_P = ds[0]['hopcap_p']
                rowHDTB_TSL.TUCAP_P = ds[0]['tucap_p']
                rowHDTB_TSL.CAP_GOC = ds[0]['cap_goc']
                rowHDTB_TSL.CAP_P = ds[0]['cap_p']
                rowHDTB_TSL.TUCAP_GOC = ds[0]['tucap_goc']
                rowHDTB_TSL.DOICAP_P = l_doicap_ngon
                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id
                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc
                rowHDTB_TSL.CULY = l_culy

                if (ds[0]['tramtb_id'] != null && `${ds[0]['tramtb_id']}` != '') { rowHDTB_TSL.TRAMTB_ID = parseInt(ds[0]['tramtb_id']) }

                if (ds[0]['bras_id'] != null && `${ds[0]['bras_id']}` != '') { rowHDTB_TSL.BRAS_ID = parseInt(ds[0]['bras_id']) }

                if (ds[0]['dslam_id'] != null && `${ds[0]['dslam_id']}` != '') { rowHDTB_TSL.DSLAM_ID = parseInt(ds[0]['dslam_id']) }

                if (ds[0]['port_id'] != null && `${ds[0]['port_id']}` != '') { rowHDTB_TSL.PORT_ID = parseInt(ds[0]['port_id']) }

                if (ds[0]['vci_vpi_id'] != null && `${ds[0]['vci_vpi_id']}` != '') { rowHDTB_TSL.VCI_VPI_ID = parseInt(ds[0]['vci_vpi_id']) }
              } else { rowHDTB_TSL.LOAICAP_ID = parseInt(this.cboLoaiCap_Dau.Value) }
            } else {
              rowHDTB_TSL.LOAIKENH_ID = parseInt(ds[1]['loaikenh_id'])

              if (dc_daucuoi == 2) {
                rowHDTB_TSL.LOAICAP_ID = parseInt(ds[1]['loaicap_id'])
                rowHDTB_TSL.TEN_TBI = ds[1]['ten_tbi']
                rowHDTB_TSL.SERIAL = ds[1]['serial']
                rowHDTB_TSL.IP_WAN = ds[1]['ip_wan']
                rowHDTB_TSL.LINK = ds[1]['link']
                rowHDTB_TSL.TIMESLOT = ds[1]['timeslot']

                let l_doicap_ngon = -1
                let l_doicap_goc = -1
                let l_loaicap_id = 0
                let l_culy = 0

                if (ds[1]['doicap_p'] != null && `${ds[1]['doicap_p']}` != '') { l_doicap_ngon = parseInt(ds[1]['doicap_p']) }

                if (ds[1]['doicap_goc'] != null && `${ds[1]['doicap_goc']}` != '') { l_doicap_goc = parseInt(ds[1]['doicap_goc']) }

                if (ds[1]['loaicap_id'] != null && `${ds[1]['loaicap_id']}` != '') { l_loaicap_id = parseInt(ds[1]['loaicap_id']) }

                if (ds[1]['culy'] != null && `${ds[1]['culy']}` != '') { l_culy = parseInt(ds[1]['culy']) }

                rowHDTB_TSL.TUYENCAP = ds[1]['tuyencap']
                rowHDTB_TSL.LIENTU = ds[1]['lientu']
                rowHDTB_TSL.HOPCAP_P = ds[1]['hopcap_p']
                rowHDTB_TSL.TUCAP_P = ds[1]['tucap_p']
                rowHDTB_TSL.CAP_GOC = ds[1]['cap_goc']
                rowHDTB_TSL.CAP_P = ds[1]['cap_p']
                rowHDTB_TSL.TUCAP_GOC = ds[1]['tucap_goc']
                rowHDTB_TSL.DOICAP_P = l_doicap_ngon
                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id
                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc
                rowHDTB_TSL.CULY = l_culy

                if (ds[1]['tramtb_id'] != null && `${ds[1]['tramtb_id']}` != '') { rowHDTB_TSL.TRAMTB_ID = parseInt(ds[1]['tramtb_id']) }

                if (ds[1]['bras_id'] != null && `${ds[1]['bras_id']}` != '') { rowHDTB_TSL.BRAS_ID = parseInt(ds[1]['bras_id']) }

                if (ds[1]['dslam_id'] != null && `${ds[1]['dslam_id']}` != '') { rowHDTB_TSL.DSLAM_ID = parseInt(ds[1]['dslam_id']) }

                if (ds[1]['port_id'] != null && `${ds[1]['port_id']}` != '') { rowHDTB_TSL.PORT_ID = parseInt(ds[1]['port_id']) }

                if (ds[1]['vci_vpi_id'] != null && `${ds[1]['vci_vpi_id']}` != '') { rowHDTB_TSL.VCI_VPI_ID = parseInt(ds[1]['vci_vpi_id']) }
              } else { rowHDTB_TSL.LOAICAP_ID = parseInt(this.cboLoaiCap_Dau.Value) }
            }
          }
        } else// Điểm cuối
        {
          rowHDTB_TSL.DIACHI = this.txtDiaChiCuoi.Text
          rowHDTB_TSL.DIACHI_ID = this.diachicuoi_id
          rowHDTB_TSL.KINHDO_LD = !this.Lng_DC ? 0 : this.Lng_DC
          rowHDTB_TSL.VIDO_LD = !this.Lat_DC ? 0 : this.Lat_DC
          rowHDTB_TSL.MA_TB_SUB = this.ma_tb_cuoi
          rowHDTB_TSL.MA_LT = this.txtSoAoTSL_Cuoi.Text
          rowHDTB_TSL.THIETBIDC_ID = parseInt(this.cboLoaiTBTSL_Cuoi.Value)
          row.DONVI_ID = parseInt(this.cboTramVTCuoi.Value)

          if (ds.length > 0) {
            if (parseInt(ds[0]['daucuoi_id']) == 2) // điểm cuối
            {
              rowHDTB_TSL.LOAIKENH_ID = parseInt(ds[0]['loaikenh_id'])

              if (dc_daucuoi == 1) {
                rowHDTB_TSL.LOAICAP_ID = parseInt(ds[0]['loaicap_id'])
                rowHDTB_TSL.TEN_TBI = ds[0]['ten_tbi']
                rowHDTB_TSL.SERIAL = ds[0]['serial']
                rowHDTB_TSL.IP_WAN = ds[0]['ip_wan']
                rowHDTB_TSL.LINK = ds[0]['link']
                rowHDTB_TSL.TIMESLOT = ds[0]['timeslot']

                let l_doicap_ngon = -1
                let l_doicap_goc = -1
                let l_loaicap_id = 0
                let l_culy = 0

                if (ds[0]['doicap_p'] != null && `${ds[0]['doicap_p']}` != '') { l_doicap_ngon = parseInt(ds[0]['doicap_p']) }

                if (ds[0]['doicap_goc'] != null && `${ds[0]['doicap_goc']}` != '') { l_doicap_goc = parseInt(ds[0]['doicap_goc']) }

                if (ds[0]['loaicap_id'] != null && `${ds[0]['loaicap_id']}` != '') { l_loaicap_id = parseInt(ds[0]['loaicap_id']) }

                if (ds[0]['culy'] != null && `${ds[0]['culy']}` != '') { l_culy = parseInt(ds[0]['culy']) }

                rowHDTB_TSL.TUYENCAP = ds[0]['tuyencap']
                rowHDTB_TSL.LIENTU = ds[0]['lientu']
                rowHDTB_TSL.HOPCAP_P = ds[0]['hopcap_p']
                rowHDTB_TSL.TUCAP_P = ds[0]['tucap_p']
                rowHDTB_TSL.CAP_GOC = ds[0]['cap_goc']
                rowHDTB_TSL.CAP_P = ds[0]['cap_p']
                rowHDTB_TSL.TUCAP_GOC = ds[0]['tucap_goc']
                rowHDTB_TSL.DOICAP_P = l_doicap_ngon
                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id
                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc
                rowHDTB_TSL.CULY = l_culy

                if (ds[0]['tramtb_id'] != null && `${ds[0]['tramtb_id']}` != '') { rowHDTB_TSL.TRAMTB_ID = parseInt(ds[0]['tramtb_id']) }

                if (ds[0]['bras_id'] != null && `${ds[0]['bras_id']}` != '') { rowHDTB_TSL.BRAS_ID = parseInt(ds[0]['bras_id']) }

                if (ds[0]['dslam_id'] != null && `${ds[0]['dslam_id']}` != '') { rowHDTB_TSL.DSLAM_ID = parseInt(ds[0]['dslam_id']) }

                if (ds[0]['port_id'] != null && `${ds[0]['port_id']}` != '') { rowHDTB_TSL.PORT_ID = parseInt(ds[0]['port_id']) }

                if (ds[0]['vci_vpi_id'] != null && `${ds[0]['vci_vpi_id']}` != '') { rowHDTB_TSL.VCI_VPI_ID = parseInt(ds[0]['vci_vpi_id']) }
              } else { rowHDTB_TSL.LOAICAP_ID = parseInt(this.cboLoaiCap_Cuoi.Value) }
            } else {
              rowHDTB_TSL.LOAIKENH_ID = parseInt(ds[1]['loaikenh_id'])

              if (dc_daucuoi == 1) {
                rowHDTB_TSL.LOAICAP_ID = parseInt(ds[1]['loaicap_id'])
                rowHDTB_TSL.TEN_TBI = ds[1]['ten_tbi']
                rowHDTB_TSL.SERIAL = ds[1]['serial']
                rowHDTB_TSL.IP_WAN = ds[1]['ip_wan']
                rowHDTB_TSL.LINK = ds[1]['link']
                rowHDTB_TSL.TIMESLOT = ds[1]['timeslot']

                let l_doicap_ngon = -1
                let l_doicap_goc = -1
                let l_loaicap_id = 0
                let l_culy = 0

                if (ds[1]['doicap_p'] != null && `${ds[1]['doicap_p']}` != '') { l_doicap_ngon = parseInt(ds[1]['doicap_p']) }

                if (ds[1]['doicap_goc'] != null && `${ds[1]['doicap_goc']}` != '') { l_doicap_goc = parseInt(ds[1]['doicap_goc']) }

                if (ds[1]['loaicap_id'] != null && `${ds[1]['loaicap_id']}` != '') { l_loaicap_id = parseInt(ds[1]['loaicap_id']) }

                if (ds[1]['culy'] != null && `${ds[1]['culy']}` != '') { l_culy = parseInt(ds[1]['culy']) }

                rowHDTB_TSL.TUYENCAP = ds[1]['tuyencap']
                rowHDTB_TSL.LIENTU = ds[1]['lientu']
                rowHDTB_TSL.HOPCAP_P = ds[1]['hopcap_p']
                rowHDTB_TSL.TUCAP_P = ds[1]['tucap_p']
                rowHDTB_TSL.CAP_GOC = ds[1]['cap_goc']
                rowHDTB_TSL.CAP_P = ds[1]['cap_p']
                rowHDTB_TSL.TUCAP_GOC = ds[1]['tucap_goc']
                rowHDTB_TSL.DOICAP_P = l_doicap_ngon
                rowHDTB_TSL.LOAICAP_ID = l_loaicap_id
                rowHDTB_TSL.DOICAP_GOC = l_doicap_goc
                rowHDTB_TSL.CULY = l_culy

                if (ds[1]['tramtb_id'] != null && `${ds[1]['tramtb_id']}` != '') { rowHDTB_TSL.TRAMTB_ID = parseInt(ds[1]['tramtb_id']) }

                if (ds[1]['bras_id'] != null && `${ds[1]['bras_id']}` != '') { rowHDTB_TSL.BRAS_ID = parseInt(ds[1]['bras_id']) }

                if (ds[1]['dslam_id'] != null && `${ds[1]['dslam_id']}` != '') { rowHDTB_TSL.DSLAM_ID = parseInt(ds[1]['dslam_id']) }

                if (ds[1]['port_id'] != null && `${ds[1]['port_id']}` != '') { rowHDTB_TSL.PORT_ID = parseInt(ds[1]['port_id']) }

                if (ds[1]['vci_vpi_id'] != null && `${ds[1]['vci_vpi_id']}` != '') { rowHDTB_TSL.VCI_VPI_ID = parseInt(ds[1]['vci_vpi_id']) }
              } else { rowHDTB_TSL.LOAICAP_ID = parseInt(this.cboLoaiCap_Cuoi.Value) }
            }
          }
        }

        rowHDTB_TSL.DAUCUOI_ID = i + 1
        dsHDTB_TSL.push(rowHDTB_TSL)
        dsHDTBDV.push(row)
      }

      // Tạo cho bảng hdtb_dv
      // Với Truyền số liệu thì tự thêm các dòng # trạm viễn thông
      for (let i = 0; i < this.dsComboDonVi.length; i++) {
        let item = this.dsComboDonVi[i]
        let row = {
          HDTB_ID: this.parent.inputValue.hdtb_id,
          LOAIDV_ID: parseInt(item.loaiDonVi),
          DONVI_ID: parseInt(this.$refs[item.key][0].value),
          KIEUDV_ID: 2
        }
        dsHDTBDV.push(row)
      }

      return { dsHDTB_TSL, dsHDTBDV }
    },
    chkDC_Cuoi_CheckedChanged (value) {
      if (value) {
        this.txtDiaChiCuoi.Enabled = true
        this.btnChonDCCuoi.Enabled = true
        this.cboTramVTCuoi.Enabled = true
      } else {
        this.txtDiaChiCuoi.Enabled = false
        this.btnChonDCCuoi.Enabled = false
        this.cboTramVTCuoi.Enabled = false
      }
    },
    chkDC_Dau_CheckedChanged (value) {
      if (value) {
        this.txtDiaChiDau.Enabled = true
        this.btnChonDCDau.Enabled = true
        this.cboTramVTDau.Enabled = true
      } else {
        this.txtDiaChiDau.Enabled = false
        this.btnChonDCDau.Enabled = false
        this.cboTramVTDau.Enabled = false
      }
    },
    chkDoiTac_TSL_CheckedChanged (value) {
      if (value) {
        this.cboDoiTac_TSL.Enabled = true
        this.txtCuocTKDT_TSL.Enabled = true
        this.txtCuocTCDT_TSL.Enabled = true
        this.txtMa_HDDT_TSL.Enabled = true
      } else {
        this.cboDoiTac_TSL.Enabled = false
        this.txtCuocTKDT_TSL.Enabled = false
        this.txtCuocTCDT_TSL.Enabled = false
        this.txtMa_HDDT_TSL.Enabled = false
        this.txtCuocTKDT_TSL.Text = 0
        this.txtCuocTCDT_TSL.Text = 0
      }
    },
    cboTrangBi_SelectedValueChanged (value) {
      if (value) {
        this.$emit('cboTocDoKenhTSL_EditValueChanged', { trangbi_id: value })
      }
    },
    cboTocDoKenhTSL_EditValueChanged (value) {
      if (!value) {
        return
      }

      let itocdo_id = parseInt(value)
      let trangbi_id = parseInt(this.cboTrangBi.Value)
      this.$emit('cboTocDoKenhTSL_EditValueChanged', { itocdo_id, trangbi_id })

      this.HT_COMBO_DONVI()
    },
    async HT_COMBO_DONVI () {
      let dsLoaiDonVi = await this.lay_ds_loai_donvi({
        loaihinh_tb: this.idLoaihinhTb,
        kieu_ld: this.idKieuLd,
        tocdo_id: this.cboTocDoKenhTSL.Value
      })

      let dsComboDonVi = []

      for (let i = 0; i < dsLoaiDonVi.length; i++) {

        let loaiDonVi = dsLoaiDonVi[i]

        if (loaiDonVi["loaidv_dich_id"] == Enum.LOAI_DV.TRAM_VT || loaiDonVi["loaidv_dich_id"] == Enum.LOAI_DV.TRAM_CAP_PORT) {

            let dsDonVi = []

            if (loaiDonVi["loaidv_dich_id"] == Enum.LOAI_DV.TRAM_VT) {
                dsDonVi = await this.lay_ds_donvi_tramtc({
                    loaidv_id: loaiDonVi["loaidv_dich_id"],
                    donvi_ql_id: this.idDaiVt
                })
            }

            if (!!this.phuongld_id && this.phuongld_id != 0) {

                let ds = await this.lay_donvi_theo_loaidv_diachi_ld_v2(Enum.LOAI_DV.TRAM_VT, this.phuongld_id, this.phold_id, this.apld_id, this.khuld_id, this.dacdiemld_id)

                if (ds.length <= 0)
                {
                    ds = await this.lay_donvi_theo_loaidv_diachi_ld_v2(Enum.LOAI_DV.TRAM_VT, this.phuongld_id, this.phold_id, this.apld_id, 0, this.dacdiemld_id)
                    
                    if (ds.length <= 0)
                    {
                        ds = await this.lay_donvi_theo_loaidv_diachi_ld_v2(Enum.LOAI_DV.TRAM_VT, this.phuongld_id, this.phold_id, 0, 0, this.dacdiemld_id);
                        
                        if (ds.length <= 0)
                        {
                            this.$root.toastError("Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!");
                            
                            if (!!this.ht_loc_cbo_tram)
                            {
                                dsDonVi = []
                            }
                        }
                    }
                }
                
                if (ds.length > 0)
                {
                    if (!!this.ht_loc_cbo_tram)
                    {
                        dsDonVi = await this.lay_ds_donvi_tramtc({
                            loaidv_id: 0,
                            donvi_ql_id: parseInt(ds[0]["donvi_id"])
                        })
                    }
                    else
                    {
                        loaiDonVi.donvi_id = parseInt(ds[0]["donvi_id"])
                    }
                }

                loaiDonVi.dsDonVi = dsDonVi
            }
        } else {

            loaiDonVi.dsDonVi = await this.lay_ds_donvi(loaiDonVi["loaidv_dich_id"])

            if (loaiDonVi["loaidv_dich_id"] != Enum.LOAI_DV.TTKD_NGHIEMTHU) {

                if (this.phuongld_id != 0) {

                    let ds = await this.lay_donvi_theo_loaidv_diachi_ld_v2(loaiDonVi["loaidv_dich_id"], this.phuongld_id, this.phold_id, this.apld_id, this.khuld_id, this.dacdiemld_id)

                    if (ds.length > 0) {
                        loaiDonVi.donvi_id = ds[0].donvi_id
                    }
                }
            } else {
                loaiDonVi.donvi_id = this.$root.token.getDonViID()
            }
        }

        dsComboDonVi.push(this.newComboDonVi(loaiDonVi))
      }

      this.dsComboDonVi = dsComboDonVi
    },
    async lay_donvi_theo_loaidv_diachi_ld_v2(loaidv_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id) {
        try {
            var data = {loaidv_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id}
            var response = await this.$root.context.post('/web-hopdong/chuyendich-codinh/lay_donvi_theo_loaidv_diachi_ld_v2', data)
            return response.data
        } catch (error) {
            return []
        }
    },
    async onChonDiaChiCuoi (data) {
      if (!data) return

      this.diaChiCuoiDuocChon = data

      if (parseInt(data.quan_id) > 0 && parseInt(data.phuong_id) > 0) {
        let ds = []

        if (this.parent.inputValue.DONVIQL_HTTT == 1) {
          ds = await this.Lay_ds_donviql_v2({
            phuong_id: data.phuong_id,
            pho_id: !data.pho_id ? 0 : data.pho_id,
            ap_id: !data.ap_id ? 0 : data.ap_id,
            khu_id: !data.khu_id ? 0 : data.khu_id,
            kieu: 2,
            loaikh_id: 0,
            httt_id: 0
          })
        } else {
          ds = await this.LAY_DS_DONVIQL({
            vphuong_id: data.phuong_id,
            vpho_id: !data.pho_id ? 0 : data.pho_id,
            vap_id: !data.ap_id ? 0 : data.ap_id,
            vkhu_id: !data.khu_id ? 0 : data.khu_id,
            vkieu: 2,
            vloaikh_id: 0
          })
        }

        if (ds.length <= 0) {
          this.$root.toastError('Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!')
          return
        } else if (ds.length == 1) {
          this.donvi_cuoi_id = parseInt(ds[0]['donvi_id'])
        } else {
          let data = await this.$refs.popupChonDonVi.show(null, null, this.donvi_cuoi_id, null, ds)

          if (!data.ok) return

          this.donvi_cuoi_id = data.data.donviql_id
        }

        let donViPxInfo = await this.lay_tt_donvi_px({
          phuong_id: data.phuong_id,
          pho_id: data.pho_id,
          loaidv_id: Enum.LOAI_DV.TRAM_VT
        })

        let vetinh_id = !donViPxInfo ? null : donViPxInfo.donvi_id

        this.cboTramVTCuoi.DataSource = await this.lay_donvi_loaidv({
          donvi_id: this.donvi_cuoi_id,
          loaidv_id: Enum.LOAI_DV.TRAM_VT
        })

        this.cboTramVTCuoi.Value = vetinh_id
      }

      this.tinhcuoi_id = data.tinh_id
      this.quancuoi_id = data.quan_id
      this.phuongcuoi_id = data.phuong_id
      this.phocuoi_id = data.pho_id
      this.apcuoi_id = data.ap_id
      this.khucuoi_id = data.khu_id
      this.Lat_DC = data.lat
      this.Lng_DC = data.lng
      this.sonhacuoi = data.sonha
      this.txtDiaChiCuoi.Text = data.diachimoi

      setTimeout(() => this.$refs['txt-diachi-cuoi'].focus(), 300)
    },
    btnChonDCCuoi_Click () {
      if (this.diaChiCuoiDuocChon) {
        this.diaChiCuoi = {
          tieuDe: 'Địa chỉ',
          tinh_id: this.diaChiCuoiDuocChon.tinh_id,
          quan_id: this.diaChiCuoiDuocChon.quan_id,
          phuong_id: this.diaChiCuoiDuocChon.phuong_id,
          pho_id: this.diaChiCuoiDuocChon.pho_id,
          ap_id: this.diaChiCuoiDuocChon.ap_id,
          khu_id: this.diaChiCuoiDuocChon.khu_id,
          so_nha: this.diaChiCuoiDuocChon.so_nha,
          diachi: this.diaChiCuoiDuocChon.diachimoi,
          lat: this.diaChiCuoiDuocChon.lat,
          lng: this.diaChiCuoiDuocChon.lng
        }
      } else {
        this.diaChiCuoi = {
          tieuDe: 'Địa chỉ',
          tinh_id: this.$root.token.getPhanVungID(),
          quan_id: 0,
          phuong_id: 0,
          pho_id: 0,
          ap_id: 0,
          khu_id: 0,
          so_nha: '',
          diachi: null,
          lat: 0,
          lng: 0
        }
      }

      this.$bvModal.show('frm-chuyendich-tsl-modal-chon-diachi-cuoi')
    },
    async Lay_ds_donviql_v2 (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/tachnhapthuebao/lay_ds_donviql_v2`, data)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async LAY_DS_DONVIQL (data) {
      try {
        let response = await this.$root.context.post(`/web-ccdv/tiepnhan_yc_ks_lapdat/lay_ds_donviql`, data)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async lay_tt_donvi_px (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-tsl/lay_tt_donvi_px`, data)
        return !response.data ? null : response.data[0]
      } catch (err) {
        return null
      }
    },
    async onChonDiaChiDau (data) {
      if (!data) return

      this.diaChiDauDuocChon = data

      if (parseInt(data.quan_id) > 0 && parseInt(data.phuong_id) > 0) {
        let ds = []

        if (this.parent.inputValue.DONVIQL_HTTT == 1) {
          ds = await this.Lay_ds_donviql_v2({
            phuong_id: data.phuong_id,
            pho_id: !data.pho_id ? 0 : data.pho_id,
            ap_id: !data.ap_id ? 0 : data.ap_id,
            khu_id: !data.khu_id ? 0 : data.khu_id,
            kieu: 2,
            loaikh_id: 0,
            httt_id: 0
          })
        } else {
          ds = await this.LAY_DS_DONVIQL({
            vphuong_id: data.phuong_id,
            vpho_id: !data.pho_id ? 0 : data.pho_id,
            vap_id: !data.ap_id ? 0 : data.ap_id,
            vkhu_id: !data.khu_id ? 0 : data.khu_id,
            vkieu: 2,
            vloaikh_id: 0
          })
        }

        if (ds.length <= 0) {
          this.$root.toastError('Địa danh này chưa được gán đơn vị quản lý địa chỉ lắp đặt. Hãy liên hệ với Admin để xử lý!')
          return
        } else if (ds.length == 1) {
          this.donvi_dau_id = parseInt(ds[0]['donvi_id'])
        } else {
          let data = await this.$refs.popupChonDonVi.show(null, null, this.donvi_dau_id, null, ds)

          if (!data.ok) return

          this.donvi_dau_id = data.data.donviql_id
        }

        let donViPxInfo = await this.lay_tt_donvi_px({
          phuong_id: data.phuong_id,
          pho_id: data.pho_id,
          loaidv_id: Enum.LOAI_DV.TRAM_VT
        })

        let vetinh_id = !donViPxInfo ? null : donViPxInfo.donvi_id

        this.cboTramVTDau.DataSource = await this.lay_donvi_loaidv({
          donvi_id: this.donvi_dau_id,
          loaidv_id: Enum.LOAI_DV.TRAM_VT
        })

        this.cboTramVTDau.Value = vetinh_id
      }

      this.txtDiaChiDau.Text = data.diachimoi
      this.tinhdau_id = data.tinh_id
      this.quandau_id = data.quan_id
      this.phuongdau_id = data.phuong_id
      this.phodau_id = data.pho_id
      this.apdau_id = data.ap_id
      this.khudau_id = data.khu_id
      this.Lat_DD = data.lat
      this.Lng_DD = data.lng
      this.sonhadau = data.so_nha

      setTimeout(() => this.$refs['txt-diachi-dau'].focus(), 300)
    },
    btnChonDCDau_Click () {
      if (this.diaChiDauDuocChon) {
        this.diaChiDau = {
          tieuDe: 'Địa chỉ',
          tinh_id: this.diaChiDauDuocChon.tinh_id,
          quan_id: this.diaChiDauDuocChon.quan_id,
          phuong_id: this.diaChiDauDuocChon.phuong_id,
          pho_id: this.diaChiDauDuocChon.pho_id,
          ap_id: this.diaChiDauDuocChon.ap_id,
          khu_id: this.diaChiDauDuocChon.khu_id,
          so_nha: this.diaChiDauDuocChon.so_nha,
          diachi: this.diaChiDauDuocChon.diachimoi,
          lat: this.diaChiDauDuocChon.lat,
          lng: this.diaChiDauDuocChon.lng
        }
      } else {
        this.diaChiDau = {
          tieuDe: 'Địa chỉ',
          tinh_id: this.$root.token.getPhanVungID(),
          quan_id: 0,
          phuong_id: 0,
          pho_id: 0,
          ap_id: 0,
          khu_id: 0,
          so_nha: '',
          diachi: null,
          lat: 0,
          lng: 0
        }
      }

      this.$bvModal.show('frm-chuyendich-tsl-modal-chon-diachi-dau')
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

            if (!!loaiDonVi.donvi_id && this.dataSource.some(x => x.donvi_id == loaiDonVi.donvi_id)) {
                this.value = loaiDonVi.donvi_id
            } else {
                this.value = this.dataSource[0].donvi_id
            }
          }
        }
      }
    },
    async lay_ds_loai_tb () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-adsl/lay_ds_loai_tb`)
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
    async lay_ds_loai_cap () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_ds_loai_cap`)
        return response.data
      } catch (err) {
        return []
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
    async lay_ds_tocdo () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-tsl/lay_ds_tocdo`)
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
    async lay_ds_loai_donvi (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-tsl/lay_ds_loai_donvi`, data)
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
    async HT_DonVi_TRAMTC_Combobox(cbo, DONVI_ID) {
                
      cbo = this.$refs[cbo.key][0];

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
    async HT_DonVi_TRAMTC_Combobox2(cbo, LOAIDV_ID, DONVI_QL_ID) {

      cbo = this.$refs[cbo.key][0];

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
    }
  }
}
</script>
