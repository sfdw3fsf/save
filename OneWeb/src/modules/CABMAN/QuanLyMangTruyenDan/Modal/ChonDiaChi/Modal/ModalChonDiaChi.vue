<template src="./ModalChonDiaChi.html"></template>
<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import {
  required
} from 'vuelidate/lib/validators'
export default {
  name: 'ModalChonDiaChi',
  components: {BssErrorMarker, BssRequiredMarker },
  props: ['modalId', 'data', 'isStrictMode'],
  created: async function () {
    this.dsTinh = []
    this.diachi.tinh_id = null
  },
  computed: {
    thaydoiDiaChi: function () {
      if (this.tentinh !== '') {
        this.diachi.diachimoi = this.tentinh
        this.diachi.diachimoi = this.tenquan + ', ' + this.diachi.diachimoi
        this.diachi.diachimoi = this.tenphuong + ', ' + this.diachi.diachimoi
        if (this.isPho && this.diachi.pho_id !== 0) { this.diachi.diachimoi = this.tenpho + ', ' + this.diachi.diachimoi }
        if (this.isAp && this.diachi.ap_id !== 0) { this.diachi.diachimoi = this.tenap + ', ' + this.diachi.diachimoi }
        if (this.isKhu && this.diachi.khu_id !== 0) { this.diachi.diachimoi = this.tenkhu + ', ' + this.diachi.diachimoi }
        if (this.diachi.so_nha != null && this.diachi.so_nha !== '') { this.diachi.diachimoi = this.diachi.so_nha + ', ' + this.diachi.diachimoi }
        return this.diachi.diachimoi
      }
      return ''
    }
  },
  watch: {
    isPho: function (val) {
      if (!val) {
        this.diachi.pho_id = null
        this.tenpho = ''
      }
    },
    isAp: function (val) {
      if (!val) {
        this.diachi.ap_id = null
        this.tenap = ''
      }
    },
    isKhu: function (val) {
      if (!val) {
        this.diachi.khu_id = null
        this.tenkhu = ''
      }
    }
  },
  methods: {
    onModalHidden () {
      // this.$v.$reset()
      // this.dsTinh = []
      // this.diachi.tinh_id = null
      // this.diachi.quan_id = null
      // this.diachi.phuong_id = null
      // this.diachi.pho_id = null
      // this.diachi.ap_id = null
      // this.diachi.khu_id = null
      // this.diachi.dac_diem_id = null
      // this.diachi.so_nha = ''
      // this.diachi.diachimoi = ''
      // this.diachi.daydiachi = ''
    },
    focusMyElement: async function () {
      await this.getTinh()
      console.log('🚀 ~ file: ModalChonDiaChi.vue:70 ~ this.data:', this.data)
      if (this.data != null) {
        Object.keys(this.data).map(key => {
          if (key.toLowerCase() !== key) {
            this.data[key.toLowerCase()] = this.data[key]
            delete this.data[key]
          }
        })
        console.log('🚀 ~ file: ModalChonDiaChi.vue:70 ~ this.data:', this.data)
        if (this.data.diachi_id !== -1 && this.data.diachi_id !== null) {
          var rs = await this.$root.context.get(
            '/web-cabman/ban-do-mang-cap/danh-muc',
            { loai: 'DIACHI_MNV', option: this.data.diachi_id }
          )
          console.log('🚀 ~ file: ModalChonDiaChi.vue:82 ~ rs:', rs)
          if (rs.data && rs.data.length > 0) {
            this.diachi.tinh_id = rs.data[0].TINH_ID
            this.tentinh = this.dsTinh.filter(
              (x) => x.TINH_ID === this.diachi.tinh_id
            )[0].TENTINH
            this.diachi.quan_id = rs.data[0].QUAN_ID
            await this.getQuan()
            this.diachi.phuong_id = rs.data[0].PHUONG_ID
            await this.getPhuong()
            if (this.diachi.phuong_id !== null) {
              if (rs.data[0].PHO_ID != null && rs.data[0].PHO_ID !== 0) {
                this.diachi.pho_id = rs.data[0].PHO_ID
                this.isPho = true
              }
              if (rs.data[0].AP_ID != null && rs.data[0].AP_ID !== 0) {
                this.diachi.ap_id = rs.data[0].AP_ID
                this.isAp = true
              }
              if (rs.data[0].KHU_ID != null && rs.data[0].KHU_ID !== 0) {
                this.diachi.khu_id = rs.data[0].KHU_ID
                this.isKhu = true
              }
              await this.getPhoApKhu()
              if (rs.data[0].DACDIEM_ID != null && rs.data[0].DACDIEM_ID !== 0) {
                this.diachi.dacdiem_id = rs.data[0].DACDIEM_ID
                this.isDacDiem = true
              }
            }
            this.diachi.so_nha = this.data.so_nha
            this.diachi.daydiachi = this.data.diachi
            this.diachi.diachimoi = this.data.diachi
          }
        } else {
          if (this.dsTinh != null && this.dsTinh.length > 0) { this.diachi.tinh_id = this.dsTinh[0].TINH_ID }
          this.tentinh = this.dsTinh.filter(
            (x) => x.TINH_ID === this.diachi.tinh_id
          )[0].TENTINH
          await this.getQuan()
        }
      } else {
        if (this.dsTinh != null && this.dsTinh.length > 0) { this.diachi.tinh_id = this.dsTinh[0].TINH_ID }
        this.tentinh = this.dsTinh.filter(
          (x) => x.TINH_ID == this.diachi.tinh_id
        )[0].TENTINH
        await this.getQuan()
      }
    },

    getTinh: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
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
        var rs = await this.$root.context.get(
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
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_ds_phuong_xa/' + this.diachi.quan_id
        )
        this.dsPhuong = rs.data
        if (this.diachi.phuong_id != null) {
          this.tenphuong = this.dsPhuong.filter(
            x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    getPhoApKhu: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
          '/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/' +
            this.diachi.phuong_id
        )
        if (rs.data != null) {
          this.dsPho = rs.data.dsPho
          if (this.diachi.pho_id != null) {
            this.tenpho = this.dsPho.filter(
              (x) => x.PHO_ID == this.diachi.pho_id
            )[0].TEN_PHO
          }
          this.dsAp = rs.data.dsAp
          if (this.diachi.ap_id != null) {
            this.tenap = this.dsAp.filter(
              (x) => x.PHO_ID == this.diachi.ap_id
            )[0].TEN_PHO
          }
          this.dsKhu = rs.data.dsKhu
          if (this.diachi.khu_id != null) {
            this.tenkhu = this.dsKhu.filter(
              (x) => x.PHO_ID == this.diachi.khu_id
            )[0].TEN_PHO
          }
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    getDacDiem: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get(
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
      this.$v.$reset()
      await this.getPhuong()
      this.tenquan = this.dsQuan.filter(
        (x) => x.QUAN_ID == this.diachi.quan_id
      )[0].TEN_QUAN
    },
    onChangePhuong: async function () {
      this.$v.$reset()
      await this.getPhoApKhu()
      this.tenphuong = this.dsPhuong.filter(
        x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG
    },
    onChangePho: async function () {
      this.$v.$reset()
      await this.getDacDiem()
      this.tenpho = this.dsPho.filter(
        (x) => x.PHO_ID == this.diachi.pho_id
      )[0].TEN_PHO
    },
    onChangeKhu: async function () {
      this.$v.$reset()
      await this.getDacDiem()
      this.tenkhu = this.dsKhu.filter(
        (x) => x.PHO_ID == this.diachi.khu_id
      )[0].TEN_PHO
    },
    onChangeAp: async function () {
      this.$v.$reset()
      await this.getDacDiem()
      this.tenap = this.dsAp.filter(
        (x) => x.PHO_ID == this.diachi.ap_id
      )[0].TEN_PHO
    },
    chapnhan: async function () {
      if (
        this.isStrictMode &&
        ((!this.isPho && !this.isAp && !this.isKhu) ||
          (this.isPho && this.tenpho == '') ||
          (this.isAp && this.tenap == '') ||
          (this.isKhu && this.tenkhu == ''))
      ) {
        this.validateData()
        return
      }
      if (this.data.diachi_id !== -1 && this.data.diachi_id !== null) {
        try {
          let data = {
            VDIACHI_ID: this.data.diachi_id,
            VTINH_ID: this.diachi.tinh_id,
            VQUAN_ID: this.diachi.quan_id,
            VPHUONG_ID: this.diachi.phuong_id,
            VPHO_ID: this.diachi.pho_id,
            VAP_ID: this.diachi.ap_id,
            VKHU_ID: this.diachi.khu_id,
            VDACDIEM_ID: this.diachi.dacdiem_id,
            VDIACHI: this.diachi.diachimoi
          }
          var rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/capnhat_diachi_mnv', data)
          console.log('🚀 ~ file: ModalChonDiaChi.vue:301 ~ rs:', rs)
        } catch (error) {
          console.log('🚀 ~ file: ModalChonDiaChi.vue:304 ~ error:', error)
          this.$root.$toast.error('Có lỗi trong quá trình lưu địa chỉ:\r\n' + error)
        }
      }
      console.log('🚀 ~ file: ModalChonDiaChi.vue:309 ~ this.diachi:', this.diachi)
      this.$emit('xacnhan', this.diachi)
      this.$bvModal.hide(this.modalId)
    },
    validateData (data) {
      this.$v.$touch()
      let flag = false
      let errorMessage = []
      if (this.$v.diachi.tinh_id.$error) {
        errorMessage.push('Trường Tỉnh/TP  bắt buộc nhập dữ liệu.')
        if (!flag) { this.$refs.tinh.focus() }
        flag = true
      }
      if (this.$v.diachi.quan_id.$error) {
        errorMessage.push('Trường Quận/huyện  bắt buộc nhập dữ liệu.')
        if (!flag) { this.$refs.quan.focus() }
        flag = true
      }
      if (this.$v.diachi.phuong_id.$error) {
        errorMessage.push('Trường Phường xã bắt buộc nhập dữ liệu.')
        if (!flag) { this.$refs.phuong.focus() }
        flag = true
      }
      if (this.$v.diachi.pho_id.$error && this.$v.diachi.khu_id.$error && this.$v.diachi.ap_id.$error) {
        if (!flag) { this.$refs.pho.focus() }
        flag = true
        errorMessage.push('Hãy chọn Phố / Ấp / Khu.')
      }
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
      }
    }
  },
  data: function () {
    return {
      diachi: {
        diachi_id: null,
        tinh_id: null,
        quan_id: null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id: null,
        dac_diem_id: null,
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
      tentinh: '',
      tenquan: '',
      tenphuong: '',
      tenpho: '',
      tenap: '',
      tenkhu: ''
    }
  },
  validations: {
    diachi: {
      tinh_id: { required },
      quan_id: { required },
      phuong_id: { required },
      pho_id: { required },
      ap_id: { required },
      khu_id: { required }
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
