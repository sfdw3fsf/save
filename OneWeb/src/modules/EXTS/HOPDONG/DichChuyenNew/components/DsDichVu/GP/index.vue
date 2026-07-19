<template src="./index.html"></template>
<script>
export default {
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
      txtSoSimGphone: {
        Text: ''
      },
      checkVSVX: false,
      LOAI_DV: {
        TRAM_VT: 5
      },
      txtMaTN_CD: {
        Text: null
      },
      cboTrangBiGP: {
        DataSource: [],
        Value: null
      },
      dsComboDonVi: []
    }
  },
  computed: {
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
    this.cboTrangBiGP.DataSource = await this.lay_ds_trangbi()
    this.cboTrangBiGP.Value = this.cboTrangBiGP.DataSource[0].trangbi_id

    // watch mouted
  },
  methods: {
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
                dataTextField="ten_dv"/>
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
    TaoDuLieu_HDTB_GP (themmoi) {
      let rowHDTB_GP = {
        HDTB_ID: this.hdtb_id,
        SIMCARD: this.txtSoSimGphone.Text,
        TRANGBI_ID: parseInt(this.cboTrangBiGP.Value)
      }

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
      console.log('🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀 ~~~rowHDTB_IMS', rowHDTB_GP)
      console.log('🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀🚀 ~~~rowHDTB_IMS', dsHDTBDV)

      return {
        dsHDTB_GP: [rowHDTB_GP],
        dsHDTBDV: dsHDTBDV
      }
    },
    async LAY_DS_DB_GP_THEO_TBID (thuebao_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_db_gp_theo_tbid?thuebao_id=${thuebao_id}`)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async lay_ds_hdtb_GP_theo_hdtb_id (hdtb_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-bancheo-dichvu/lay_ds_hdtb_gp_theo_hdtb_id?hdtb_id=${hdtb_id}`)
        return !response.data ? [] : response.data
      } catch (err) {
        return []
      }
    },
    async HienThiTTMoRongHD (hdtb_id) {
      let ds = await this.lay_ds_hdtb_GP_theo_hdtb_id(hdtb_id)

      if (ds.length <= 0) {
        return
      }

      this.txtSoSimGphone.Text = ds[0]['simcard']

      if (ds[0]['trangbi_id']) {
        this.cboTrangBiGP.Value = ds[0]['trangbi_id'] + ''
      }

      await this.HT_COMBO_DONVI()
      // Lấy ra các đơn vị được chọn
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
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_donvi_cua_hopdong`, {hdtb_id})
        return response.data
      } catch (err) {
        return []
      }
    },
    async HienThiTTMoRongTB (thuebao_id) {
      let ds = await this.LAY_DS_DB_GP_THEO_TBID(thuebao_id)

      if (ds.length <= 0) {
        return
      }

      this.txtSoSimGphone.Text = ds[0]['simcard']

      if (ds[0]['trangbi_id']) {
        this.cboTrangBiGP.Value = ds[0]['trangbi_id'] + ''
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
    }
  }

}
</script>
