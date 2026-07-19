<template src="./index.html"></template>
<script>
import frmDSTBCungDoiCap from '../Shared/frmDSTBCungDoiCap'
import frmDSTBLapKem from '../Shared/frmDSTBLapKem'
import Enum from '../../../Enum.js'

export default {
  components: {
    frmDSTBCungDoiCap,
    frmDSTBLapKem
  },
  props: ['parent'],
  data () {
    return {
      DichVuVienThong: {
        CO_DINH: 1
      },
      LOAI_DV: {
        TRAM_VT: 5
      },
      txtMaTN_CD: {
        Text: ''
      },
      txtMaDoiCap_CD: {
        Text: ''
      },
      txtSoMetDay: {
        Text: ''
      },
      cboTrangBiCD: {
        DataSource: [],
        Value: null
      },
      dsComboDonVi: []
    }
  },
  computed: {
    idDichVu () {
      return parseInt(this.parent.parent.modelCbo.cboDichVuVT)
    },
    isActive () {
      return this.idDichVu == this.DichVuVienThong.CO_DINH
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
    idKieuLd: async function () {
      if (this.isActive) {
        await this.HT_COMBO_DONVI()
      }
    },
    idLoaiHinhTb: async function () {
      if (this.isActive) {
        await this.HT_COMBO_DONVI()
      }
    },
    'cboTrangBiCD.Value': function (value) {
      this.cboTrangBiCD_SelectedValueChanged(value)
    }
  },
  async mounted () {
    this.cboTrangBiCD.DataSource = await this.lay_ds_trangbi()
    this.cboTrangBiCD.Value = this.cboTrangBiCD.DataSource[0].trangbi_id
  },
  methods: {
    async TaoDuLieu_HDBC_CD (themmoi) {
      let rowHDBCCD = {}
      let tinh_id = this.parent.parent.BanCheoData.Tinh_TC.tinhthicong_id

      if (!this.parent.parent.kenh_trang) {
        let ds = await this.BANCHEO_LAY_DS_DB_CD_THEO_TBID(tinh_id, this.parent.parent.thuebao_tc_id)
        let firstRow = {...ds[0]}
        Object.keys(firstRow).forEach(key => rowHDBCCD[key.toUpperCase()] = firstRow[key])
      }

      let thongso_tc = this.parent.parent.thongso_tc

      rowHDBCCD.HDTB_ID = this.parent.parent.hdtb_id
      rowHDBCCD.MATB_TN = thongso_tc.ma_tn

      let kieu = await this.lay_tt_kieu_ld(thongso_tc.kieuld_id)

      if (kieu == 1 || kieu == 2 || kieu == 4) {
        if (!thongso_tc.ma_doicap) {
          this.$root.toastError('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.')
          return
        }
        rowHDBCCD.MADOICAP = parseInt(thongso_tc.ma_doicap)
      } else // TH còn lại sinh mã đôi cáp
      {
        if (themmoi == true) {
          rowHDBCCD.MADOICAP = await this.get_key_madoicap(tinh_id)
        } else {
          rowHDBCCD.MADOICAP = parseInt(thongso_tc.ma_doicap)
        }
      }

      if (this.parent.parent.kieu_thueso == Enum.THUESO.TRUOTSO) {
        rowHDBCCD.TG_THUESO = this.parent.parent.sothang_thueso
        rowHDBCCD.SOTRUOT = this.parent.parent.noidung_thueso
        rowHDBCCD.NOIDUNG_TB = ''
      }

      if (this.parent.parent.kieu_thueso == Enum.THUESO.THONGBAO) {
        rowHDBCCD.TG_THUESO = this.parent.parent.sothang_thueso
        rowHDBCCD.SOTRUOT = ''
        rowHDBCCD.NOIDUNG_TB = this.parent.parent.noidung_thueso
      }

      rowHDBCCD.TRANGBI_ID = thongso_tc.trangbi_id

      let KetCuoi_ID = parseInt(this.parent.parent.KetCuoi_ID)

      if (KetCuoi_ID > 0) {
        rowHDBCCD.KETCUOI_ID = KetCuoi_ID
      }

      return { dsHDBCCD: [rowHDBCCD] }
    },
    async TaoDuLieu_HDTB_CD (themmoi) {
      let rowHDTB_CD = {
        HDTB_ID: this.parent.parent.hdtb_id,
        MATB_TN: this.txtMaTN_CD.Text,
        TRANGBI_ID: parseInt(this.cboTrangBiCD.Value)
      }

      let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

      if (kieu == 1 || kieu == 2 || kieu == 4) {
        if (!this.txtMaDoiCap_CD.Text) {
          this.$root.toastError('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.')
          return
        }
        rowHDTB_CD.MADOICAP = parseInt(this.txtMaDoiCap_CD.Text)
      } else // TH còn lại sinh mã đôi cáp
      {
        if (themmoi == true) {
          rowHDTB_CD.MADOICAP = await this.get_key_madoicap()
          this.txtMaDoiCap_CD.Text = rowHDTB_CD.MADOICAP.toString()
        } else {
          rowHDTB_CD.MADOICAP = parseInt(this.txtMaDoiCap_CD.Text)
        }
      }

      if (this.parent.parent.kieu_thueso == Enum.THUESO.TRUOTSO) {
        rowHDTB_CD.TG_THUESO = this.parent.parent.sothang_thueso
        rowHDTB_CD.SOTRUOT = this.parent.parent.noidung_thueso
        rowHDTB_CD.NOIDUNG_TB = ''
      }

      if (this.parent.parent.kieu_thueso == Enum.THUESO.THONGBAO) {
        rowHDTB_CD.TG_THUESO = this.parent.parent.sothang_thueso
        rowHDTB_CD.SOTRUOT = ''
        rowHDTB_CD.NOIDUNG_TB = this.parent.parent.noidung_thueso
      }

      let KetCuoi_ID = parseInt(this.parent.parent.KetCuoi_ID)

      if (KetCuoi_ID > 0) {
        rowHDTB_CD.KETCUOI_ID = KetCuoi_ID
      }

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
        dsHDTB_CD: [rowHDTB_CD],
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
    async BANCHEO_LAY_DS_DB_CD_THEO_TBID (tinh_id, thuebao_id) {
      try {
        var data = {tinhthicong_id: tinh_id, thuebao_id}
        var response = await this.$root.context.post('/web-bancheo/tracuu/lay_ds_db_cd_theo_tbid', data)
        return !response.data ? [] : response.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_DB_CD_THEO_TBID (thuebao_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_db_cd_theo_tbid?thuebao_id=${thuebao_id}`)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_isdn_theo_thuebao_id_bc (thuebao_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_isdn_theo_thuebao_id_bc?thuebao_id=${thuebao_id}`)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async LAY_DS_ISDN_THEO_THUEBAO_ID (vthuebao_id) {
      this.parent.parent.isdn_sochinh = false
      this.parent.parent.matb_isdn_chinh = ''
      this.parent.parent.ds_isdn = await this.lay_ds_isdn_theo_thuebao_id_bc(vthuebao_id)
      console.log('🚀 ~ file: index.vue:233 ~ LAY_DS_ISDN_THEO_THUEBAO_ID ~ this.parent.parent.ds_isdn:', this.parent.parent.ds_isdn)

      for (let i = 0; i < this.parent.parent.ds_isdn.length; i++) {
        let item = this.parent.parent.ds_isdn[i]

        if (parseInt(item['thuebao_id']) == parseInt(vthuebao_id)) {
          if (!item['thuebao_cha_id']) { this.parent.parent.isdn_sochinh = true }
        }

        if (!item['thuebao_cha_id']) {
          this.parent.parent.matb_isdn_chinh = item['ma_tb']
        }
      }
    },
    async LAY_DS_HDTB_CD_THEO_HDTB_ID (hdtb_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_hdtb_cd_theo_hdtb_id?hdtb_id=${hdtb_id}`)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async HienThiTTMoRongHD (hdtb_id) {
      let ds = await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id)

      if (ds.length <= 0) {
        return
      }

      this.txtSoMetDay.Text = ds[0]['culy']
      this.txtMaDoiCap_CD.Text = ds[0]['madoicap']
      this.txtMaTN_CD.Text = ds[0]['matb_tn']

      if (!!ds[0]['trangbi_id'] || parseInt(ds[0]['trangbi_id']) == 0) { this.cboTrangBiCD.Value = ds[0]['trangbi_id'] + '' }

      if (ds[0]['sotruot']) {
        this.parent.parent.kieu_thueso = Enum.THUESO.TRUOTSO
        this.parent.parent.sothang_thueso = parseInt(ds[0]['tg_thueso'])
        this.parent.parent.noidung_thueso = ds[0]['sotruot']
      }

      if (ds[0]['noidung_tb']) {
        this.parent.parent.kieu_thueso = Enum.THUESO.THONGBAO
        this.parent.parent.sothang_thueso = parseInt(ds[0]['tg_thueso'])
        this.parent.parent.noidung_thueso = ds[0]['noidung_tb']
      }

      if (!ds[0]['sotruot'] && !ds[0]['noidung_tb']) {
        this.parent.parent.kieu_thueso = 0
        this.parent.parent.sothang_thueso = 0
        this.parent.parent.noidung_thueso = ''
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
      let ds = await this.LAY_DS_DB_CD_THEO_TBID(thuebao_id)

      if (ds.length <= 0) {
        return
      }

      let data = ds[0]

      this.txtSoMetDay.Text = data.culy
      this.txtMaDoiCap_CD.Text = data.madoicap

      if (data.trangbi_id) {
        this.cboTrangBiCD.Value = data.trangbi_id + ''
      }

      if (this.idLoaihinhTb == Enum.LoaiHinhTB.ISDN2B_CD ||
                    this.idLoaihinhTb == Enum.LoaiHinhTB.ISDN2B_CQ ||
                    this.idLoaihinhTb == Enum.LoaiHinhTB.ISDN30B_CD ||
                    this.idLoaihinhTb == Enum.LoaiHinhTB.ISDN30B_CQ ||
                    this.idLoaihinhTb == Enum.LoaiHinhTB.TRUNGKE_2M ||
                    this.idLoaihinhTb == Enum.LoaiHinhTB.TRUNGKE_THUONG ||
                    this.idLoaihinhTb == Enum.LoaiHinhTB.TRUNGKE_TUONGTU) {
        await this.LAY_DS_ISDN_THEO_THUEBAO_ID(thuebao_id)
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
    async HT_COMBO_DONVI () {
      if (!this.idLoaiHinhTb && !this.idKieuLd) {
        this.dsComboDonVi = []
        return
      }

      let dsLoaiDonVi = await this.lay_ds_loai_donvi({
        dichvu_id: this.idDichVu,
        loaihinh_tb: this.idLoaiHinhTb,
        kieu_ld: this.idKieuLd
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
    cboTrangBiCD_SelectedValueChanged (value) {
      if (value) {
        this.$emit('cboTrangBiCD_SelectedValueChanged', { trangbi_id: value })
      }
    },
    async btnMaTN_CD_Click () {
      let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

      if (kieu == 2) { // Lắp mới trên đường có sẵn
        if (parseInt(this.khachHangId) > 0) {
          let frm = this.$refs['ref-ds-thue-bao-cung-cap']
          frm.khachhang_id = parseInt(this.khachHangId)
          frm.kieuld_id = parseInt(this.idKieuLd)
          frm.loaitb_id = parseInt(this.idLoaiHinhTb)
          let data = await frm.show()

          if (data.chapnhan) {
            this.txtMaTN_CD.Text = data.ma_tn
            this.txtMaDoiCap_CD.Text = data.ma_doicap
          }
        } else {
          this.$root.toastError('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !')
        }
      } else if (kieu == 1) { // Lắp kèm
        if (parseInt(this.hdkhId) > 0) {
          let frm = this.$refs['ref-ds-thue-bao-lap-kem']
          frm.hdkh_id = parseInt(this.hdkhId)
          frm.kieuld_id = parseInt(this.idKieuLd)
          let data = await frm.show()

          if (data.chapnhan) {
            this.txtMaTN_CD.Text = data.ma_tn
            this.txtMaDoiCap_CD.Text = data.ma_doicap
          }
        } else {
          this.$root.toastError('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !')
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
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_donvi?loaidv_id=${loaidv_id}`)
        return response.data
      } catch (err) {
        return []
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
