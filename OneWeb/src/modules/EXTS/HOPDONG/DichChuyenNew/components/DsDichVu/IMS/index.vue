<template src="./index.html"></template>
<script>
 import frmDSTBCungDoiCap from '../../../../ChuyenDichBC/Components/DsDichVu/Shared/frmDSTBCungDoiCap'
 import frmDSTBLapKem from '../../../../ChuyenDichBC/Components/DsDichVu/Shared/frmDSTBLapKem'
 import frmDSTBCungDoiCap_KH_Khac from '../../../../ChuyenDichBC/Components/DsDichVu/Shared/frmDSTBCungDoiCap_KH_Khac/frmDSTBCungDoiCap_KH_Khac'

export default {
  components: {
     frmDSTBCungDoiCap,
     frmDSTBLapKem,
     frmDSTBCungDoiCap_KH_Khac
  },
  props: {
    parent: null,
    dataSource: {
      idLoaihinhTb: String,
      idKieuLd: String,
      idDaiVt: String
    }
  },
  data () {
    return {
      LOAI_DV: {
        TRAM_VT: 5
      },
      txtMATN_IMS: {
        Text: null
      },
      btnMaTN_Ims: {
        Enabled: true
      },
      txtMaDoiCap_IMS: {
        Text: null
      },
      cboTrangBiIMS: {
        DataSource: [],
        Value: null
      },
      dsComboDonVi: [],
      dsDBIMS: []
    }
  },
  computed: {
    thueBaoId() {
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
  watch: {
    'dataSource': async function (value) {
      if (!value.idKieuLd) {
        this.dsComboDonVi = []
        return
      }
      await this.HT_COMBO_DONVI()
    }
  },
  async mounted () {
    this.cboTrangBiIMS.DataSource = await this.lay_ds_trangbi()
    this.cboTrangBiIMS.Value = this.cboTrangBiIMS.DataSource[0].trangbi_id

    // watch mouted
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
    async HT_COMBO_DONVI () {
      if (!this.dataSource.idKieuLd) {
        this.dsComboDonVi = []
        return
      }

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
    async btnMaTN_Ims_Click () {
      let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)

      if (kieu == 2) { // Lắp mới trên đường có sẵn
        if (parseInt(this.khachHangId) > 0) {
          let frm = this.$refs.frmDSTBCungDoiCap
          frm.khachhang_id = parseInt(this.khachHangId)
          frm.kieuld_id = parseInt(this.idKieuLd)
          frm.loaitb_id = parseInt(this.idLoaihinhTb)
          let data = await frm.show()

          if (data.chapnhan) {
            this.txtMATN_IMS.Text = data.ma_tn
            this.txtMaDoiCap_IMS.Text = data.ma_doicap
          }
        } else {
          this.$root.toastError('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !')
        }
      } else if (kieu == 1) { // Lắp kèm
        if (parseInt(this.hdkhId) > 0) {
          let frm = this.$refs.frmDSTBLapKem
          frm.hdkh_id = parseInt(this.hdkhId)
          frm.kieuld_id = parseInt(this.idKieuLd)
          let data = await frm.show()

          if (data.chapnhan) {
            this.txtMATN_IMS.Text = data.ma_tn
            this.txtMaDoiCap_IMS.Text = data.ma_doicap
          }
        } else {
          this.$root.toastError('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !')
        }
      } else if (kieu == 4) { // Lắp trên đường có sẵn của khách hàng khác
        // todo:
        let frm = this.$refs.frmDSTBCungDoiCap_KH_Khac
        frm.kieuld_id = parseInt(this.idKieuLd)
        frm.loaitb_id = parseInt(this.idLoaiHinhTb)
        let data = await frm.show()

        if (data.chapnhan) {
          this.txtMATN_IMS.Text = data.ma_tn
          this.txtMaDoiCap_IMS.Text = data.ma_doicap
        }
      }
    },
    async TaoDuLieu_HDTB_IMS (themmoi) {
      console.log("this.dsDBIMS[0] tạo ",this.dsDBIMS[0])
      let ds =  await this.LAY_DS_DB_IMS_THEO_TBID(this.thueBaoId)
      let firstRow = {...ds[0]}
      let rowHDTB_IMS = {}
      Object.keys(firstRow).forEach(key => rowHDTB_IMS[key.toUpperCase()] = firstRow[key])
      console.log("this.dsDBIMS[0] tạo 222 ",ds)
      console.log("this.dsDBIMS[0] tạo 222 ",rowHDTB_IMS)

      // Object.assign(rowHDTB_IMS, ds[0])
      if(this.dsDBIMS[0] == null){
        rowHDTB_IMS.HDTB_ID= this.hdtb_id
          rowHDTB_IMS.MATB_TN= this.txtMATN_IMS.Text
          rowHDTB_IMS.TRANGBI_ID= parseInt(this.cboTrangBiIMS.Value)
          rowHDTB_IMS.MADOICAP= null
      }else {
        rowHDTB_IMS.HDTB_ID= this.hdtb_id
        rowHDTB_IMS.MATB_TN= this.txtMATN_IMS.Text
        rowHDTB_IMS.TRANGBI_ID= parseInt(this.cboTrangBiIMS.Value)
        rowHDTB_IMS.MADOICAP= null
        rowHDTB_IMS.BRAS_ID= this.dsDBIMS[0].bras_id
        rowHDTB_IMS.DSLAM_ID= this.dsDBIMS[0].dslam_id
        rowHDTB_IMS.USERNAME= this.dsDBIMS[0].username
        rowHDTB_IMS.PASSWORD= this.dsDBIMS[0].password
        rowHDTB_IMS.THIETBI_ID= this.dsDBIMS[0].thietbi_id
      }
      debugger

      let kieu = await this.lay_tt_kieu_ld(this.idKieuLd)
      // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
      // Hiện tại đối với dịch chuyển cố định kiểu =0, bổ sung kieu=1,2 sau

      if (kieu == 1 || kieu == 2 || kieu == 4) {
        if (!this.txtMaDoiCap_IMS.Text) {
          this.$root.toastError('Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.')
          return
        }

        rowHDTB_IMS.MADOICAP = parseInt(this.txtMaDoiCap_IMS.Text)
      } else // TH còn lại sinh mã đôi cáp
      {
        if (themmoi == true) {
          rowHDTB_IMS.MADOICAP = await this.get_key_madoicap()
          this.txtMaDoiCap_IMS.Text = rowHDTB_IMS.MADOICAP
        } else {
          rowHDTB_IMS.MADOICAP = parseInt(this.txtMaDoiCap_IMS.Text)
        }
      }
      rowHDTB_IMS.TRANGBI_ID = this.cboTrangBiIMS.Value
      rowHDTB_IMS.TUYENCAP = ''
      rowHDTB_IMS.LIENTU = ''
      rowHDTB_IMS.DOICAP_P = 0
      rowHDTB_IMS.HOPCAP_P = ''
      rowHDTB_IMS.TUCAP_P = ''
      rowHDTB_IMS.CAP_GOC = ''
      rowHDTB_IMS.CAP_P = ''
      rowHDTB_IMS.DOICAP_GOC = 0
      rowHDTB_IMS.TUCAP_GOC = ''
      rowHDTB_IMS.CULY = '0'

      let dsHDTBDV = []

      if (this.KetCuoi_ID > 0) {
        rowHDTB_IMS.KETCUOI_ID = this.KetCuoi_ID
      }
      for (let i = 0; i < this.dsComboDonVi.length; i++) {
        let item = this.dsComboDonVi[i]
        console.log('🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀 ~~~ item.data', this.$refs[item.key][0])

        let row = {
          LOAIDV_ID: parseInt(item.loaiDonVi),
          DONVI_ID: parseInt(this.$refs[item.key][0].value),
          KIEUDV_ID: 2
        }
        dsHDTBDV.push(row)
      }
      console.log('hiihiiii')
      console.log('🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀 ~~~rowHDTB_IMS', rowHDTB_IMS)
      console.log('🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀 ~~~dsHDTBDV', dsHDTBDV)
      return { rowHDTB_IMS, dsHDTBDV }
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
    async HienThiTTMoRongHD (hdtbId) {
      let ds = await this.lay_ds_hopdong_thuebao_ims(hdtbId)
      console.log('HienThiTTMoRongHD 1 ', ds)
      this.dsDBIMS = ds
      if (ds.length <= 0) {
        return
      }
      this.txtMATN_IMS.Text = ds[0]['matb_tn']
      this.txtMaDoiCap_IMS.Text = ds[0]['madoicap']
      this.cboTrangBiIMS.Value = ds[0]['trangbi_id']
      console.log('HienThiTTMoRongHD', ds)
      await this.HT_COMBO_DONVI()
      // Lấy ra các đơn vị được chọn
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
      console.log('this.dsDBIMS dô đây',this.dsDBIMS)
      let ds = await this.LAY_DS_DB_IMS_THEO_TBID(thuebao_id)
      this.dsDBIMS = ds
      console.log('this.dsDBIMS',ds)
      console.log('this.dsDBIMS',thuebao_id)
      console.log('this.dsDBIMS',this.dsDBIMS)
      if (ds.length <= 0) {
        return
      }
      this.txtMATN_IMS.Text = ''
      this.txtMaDoiCap_IMS.Text = ds[0]['madoicap']
      this.cboTrangBiIMS.Value = ds[0]['trangbi_id']
      if (ds[0]['trangbi_id']) {
        this.txtMaDoiCap_IMS.Text = ds[0]['madoicap']
        this.cboTrangBiIMS.Value = ds[0]['trangbi_id']
      }
      console.log('HienThiTTMoRongTB', ds)
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
    async lay_ds_hopdong_thuebao_ims (hdtbId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_hopdong_thuebao_ims?hdtbId=${hdtbId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }

        throw {response: { data: response}}
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
    async lay_ds_loai_donvi (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich-v2/lay_ds_loai_donvi`, data)
        return response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_trangbi () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-v2/lay_ds_trangbi`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async LAY_DS_DB_IMS_THEO_TBID (thueBaoId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_danhba_ims?thueBaoId=${thueBaoId}`)
        return response.data
      } catch (err) {
        return []
      }
    },
    async get_key_madoicap () {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/get_key_madoicap`)
        return response.data.key
      } catch (err) {
        return null
      }
    }
  }
}
</script>
