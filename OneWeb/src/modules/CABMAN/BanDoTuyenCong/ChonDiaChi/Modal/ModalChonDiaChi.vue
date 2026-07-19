<template src="./ModalChonDiaChi.html"></template>
<script>
export default {
  name: 'ModalChonDiaChi',
  props: ['modalId', 'data'],
  created: async function () {
    this.dsTinh = []
    this.diachi.tinh_id = null
  },
  computed: {
    thaydoiDiaChi: function () {
      console.log('🚀 ~ file: ModalChonDiaChi.vue:14 ~ this.tentinh:', this.tentinh)
      if (this.tentinh != '') {
        this.diachi.diachimoi = this.tentinh
        this.diachi.diachimoi = this.tenquan + ', ' + this.diachi.diachimoi
        this.diachi.diachimoi = this.tenphuong + ', ' + this.diachi.diachimoi
        if (this.isPho && this.diachi.pho_id != 0) { this.diachi.diachimoi = this.tenpho + ', ' + this.diachi.diachimoi }
        if (this.isAp && this.diachi.ap_id != 0) { this.diachi.diachimoi = this.tenap + ', ' + this.diachi.diachimoi }
        if (this.isKhu && this.diachi.khu_id != 0) { this.diachi.diachimoi = this.tenkhu + ', ' + this.diachi.diachimoi }
        if (this.diachi.so_nha != null && this.diachi.so_nha != '') { this.diachi.diachimoi = this.diachi.so_nha + ', ' + this.diachi.diachimoi }
        return this.diachi.diachimoi
      }
      return ''
    }
  },
  watch: {
  },
  methods: {
    onModalHidden () {
      // this.dsTinh = []
      // this.diachi.tinh_id = null
      // this.diachi.quan_id = null
      // this.diachi.phuong_id = null
      // this.diachi.pho_id = null
      // this.diachi.ap_id = null
      // this.diachi.khu_id = null
      // this.diachi.dacdiem_id = null
      // this.diachi.so_nha = ''
    },
    focusMyElement: async function () {
      await this.getTinh()

      if (this.data.tinhId != null) {
        console.log(this.data)
        this.diachi.tinh_id = this.data.tinhId
        this.tentinh = this.dsTinh.filter(
          (x) => x.TINH_ID == this.diachi.tinh_id
        )[0].TENTINH
        await this.getQuan()
        this.diachi.quan_id = this.data.quanId

        await this.getPhuong()
        this.diachi.phuong_id = this.data.phuongId

        await this.getPhoApKhu()
        this.diachi.so_nha = this.data.soNha
        this.diachi.daydiachi = this.data.diaChi
        this.diachi.diachimoi = this.data.diaChi
      } else {
        if (this.dsTinh != null && this.dsTinh.length > 0) { this.diachi.tinh_id = this.dsTinh[0].TINH_ID }
        this.tentinh = this.dsTinh.filter(
          (x) => x.TINH_ID == this.diachi.tinh_id
        )[0].TENTINH
        await this.getQuan()
      }
      console.log('🚀 ~ file: ModalChonDiaChi.vue:73 ~ this.diachi:', this.diachi)
    },

    getTinh: async function () {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_thongtin_tinh'
        )
        this.dsTinh.push(rs.data)
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getQuan: async function () {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_ds_quan_huyen/' + this.diachi.tinh_id
        )
        this.dsQuan = rs.data
        if (this.diachi.quan_id != null) {
          this.tenquan = this.dsQuan.filter(
            (x) => x.QUAN_ID == this.diachi.quan_id
          )[0].TEN_QUAN
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    getPhuong: async function () {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_ds_phuong_xa/' + this.diachi.quan_id
        )
        // console.log(rs.data)
        this.dsPhuong = rs.data
        // if(this.diachi.phuong_id!=null)
        //     this.tenphuong = this.dsPhuong.filter(
        //         x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getPhoApKhu: async function () {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/' +
                        this.diachi.phuong_id
        )
        if (rs.data != null) {
          this.dsPho = rs.data.dsPho
          if (this.data.phoId != null) {
            let _tenpho = this.dsPho.filter((x) => x.PHO_ID == this.data.phoId)
            if (_tenpho.length > 0) {
              this.tenpho = _tenpho[0].TEN_PHO
              this.diachi.pho_id = this.data.phoId
              this.isPho = true
            } else {
              this.isPho = false
              this.diachi.pho_id = null
            }
          }
          this.dsAp = rs.data.dsAp
          if (this.data.apId != null) {
            let _tenap = this.dsAp.filter(
              (x) => x.PHO_ID == this.data.apId
            )
            if (_tenap.length > 0) {
              this.tenap = _tenap[0].TEN_PHO
              this.diachi.ap_id = this.data.apId
              this.isAp = true
            } else {
              this.isAp = false
              this.diachi.ap_id = null
            }
          }
          this.dsKhu = rs.data.dsKhu
          if (this.data.khuId != null) {
            let _tenkhu = this.dsKhu.filter(
              (x) => x.PHO_ID == this.data.khuId
            )
            if (_tenkhu.length > 0) {
              this.tenkhu = _tenkhu[0].TEN_PHO
              this.diachi.khu_id = this.data.khuId
              this.isKhu = true
            } else {
              this.isKhu = false
              this.diachi.khu_id = null
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: ModalChonDiaChi.vue:164 ~ error:', error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getDacDiem: async function () {
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_ds_dacdiem',
          {
            phuong_id: this.phuong_id == null ? 0 : this.phuong_id,
            pho_id: this.pho_id == null ? 0 : this.pho_id,
            ap_id: this.ap_id == null ? 0 : this.ap_id,
            khu_id: this.khu_id == null ? 0 : this.khu_id
          }
        )
        this.dsDacDiem = rs.data
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    saveDiachi: async function () {
      try {
        this.$root.showLoading(true)
      } catch (error) {
        this.$toast.error('Lưu ảnh thất bại')
      } finally {
        this.$root.showLoading(false)
      }
    },
    onChangeTinh: async function () {
      await this.getQuan()
      this.tentinh = this.dsTinh.filter(
        (x) => x.TINH_ID == this.diachi.tinh_id
      )[0].TENTINH
    },
    onChangeQuan: async function () {
      await this.getPhuong()
      this.tenquan = this.dsQuan.filter(
        (x) => x.QUAN_ID == this.diachi.quan_id
      )[0].TEN_QUAN
    },
    onChangePhuong: async function () {
      await this.getPhoApKhu()
      this.tenphuong = this.dsPhuong.filter(
        x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG
    },
    onChangePho: async function () {
      await this.getDacDiem()
      this.tenpho = this.dsPho.filter(
        (x) => x.PHO_ID == this.diachi.pho_id
      )[0].TEN_PHO
    },
    onChangeKhu: async function () {
      await this.getDacDiem()
      this.tenkhu = this.dsKhu.filter(
        (x) => x.PHO_ID == this.diachi.khu_id
      )[0].TEN_PHO
    },
    onChangeAp: async function () {
      await this.getDacDiem()
      this.tenap = this.dsAp.filter(
        (x) => x.PHO_ID == this.diachi.ap_id
      )[0].TEN_PHO
      console.log('🚀 ~ file: ModalChonDiaChi.vue:248 ~ this.tenap:', this.tenap)
    },
    chapnhan: function () {
      if (
        this.isStrictMode &&
                    ((!this.isPho && !this.isAp && !this.isKhu) ||
                        (this.isPho && this.tenpho == '') ||
                        (this.isAp && this.tenap == '') ||
                        (this.isKhu && this.tenkhu == ''))
      ) {
        this.$toast.error('Hãy chọn Phố / Ấp / Khu')
        return
      }
      console.log('🚀 ~ file: ModalChonDiaChi.vue:261 ~ this.diachi:', this.diachi)
      this.$emit('xacnhan', this.diachi)
      this.$bvModal.hide(this.modalId)
    }
  },
  data: function () {
    return {
      diachi: {
        diachi_id: 0,
        tinh_id: 26,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dac_diem_id: 0,
        so_nha: '',
        daydiachi: '',
        diachimoi: ''
      },
      dsTinh: [],
      dsQuan: [],
      dsPhuong: [],
      dsPho: [],
      dsAp: [],
      dsKhu: [],
      dsDacDiem: [],
      isPho: false,
      isAp: false,
      isKhu: false,
      isDacDiem: false,
      isStrictMode: true,
      tentinh: '',
      tenquan: '',
      tenphuong: '',
      tenpho: '',
      tenap: '',
      tenkhu: ''
    }
  }
}
</script>
<style>
  div#popupTCTTTB___BV_modal_outer_ {
    z-index: 10 !important;
    margin-top: 100px;
  }
  .dashboard-header {
    z-index: 9 !important;
  }
  a.disabled {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }
</style>
