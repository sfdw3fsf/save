<template src="./DiaChi.html"></template>
<script>
import TraCuuBanDoMangCap from '../../../../../ECMS/tracuu-bando-mangcap/Modal'
import API from './API'

export default {
  name: 'DiaChi',
  props: {
    modalId: String,
    showThongTinThem: {
      type: Boolean,
      default: false
    },
    isStrictMode: {
      type: Boolean,
      default: true
    },
    data: Object
  },
  components: {TraCuuBanDoMangCap},
  data: function () {
    return {
      diachi: {
        diachi_id: null,
        tinh_id: this.$root.token.getPhanVungID(),
        quan_id: null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id: null,
        dac_diem_id: null,
        so_nha: '',
        daydiachi: '',
        diachimoi: '',
        block: '',
        tang: '',
        sophong: '',
        motathem: ''
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
      tenkhu: '',
      isEnableThongTinThem: false,
      showModalViTri: false,
      popupTraCuuBanDoMangCap: 'popupTraCuuBanDoMangCap'
    }
  },
  created: async function () {
    this.dsTinh = []
    this.diachi.tinh_id = null
  },
  computed: {
    thaydoiDiaChi: function () {
      if (this.tentinh != '') {
        this.diachi.diachimoi = this.tentinh
        this.diachi.diachimoi = this.tenquan + ', ' + this.diachi.diachimoi
        this.diachi.diachimoi = this.tenphuong + ', ' + this.diachi.diachimoi
        if (this.isPho && this.diachi.pho_id != 0) {
          this.diachi.diachimoi = this.tenpho + ', ' + this.diachi.diachimoi
        }
        if (this.isAp && this.diachi.ap_id != 0) {
          this.diachi.diachimoi = this.tenap + ', ' + this.diachi.diachimoi
        }
        if (this.isKhu && this.diachi.khu_id != 0) {
          this.diachi.diachimoi = this.tenkhu + ', ' + this.diachi.diachimoi
        }
        if (this.diachi.so_nha != null && this.diachi.so_nha != '') {
          this.diachi.diachimoi = this.diachi.so_nha + ', ' + this.diachi.diachimoi
        }
        return this.diachi.diachimoi
      }
      return ''
    },
    getDiaChi () {
      return {...this.diachi.diachimoi}
    }
  },
  watch: {},
  methods: {
    onModalHidden () {
      this.dsTinh = []
      this.diachi.tinh_id = null
      this.diachi.quan_id = null
      this.diachi.phuong_id = null
      this.diachi.pho_id = null
      this.diachi.ap_id = null
      this.diachi.khu_id = null
      this.diachi.dacdiem_id = null
      this.diachi.so_nha = ''
      this.diachi.diachimoi = ''
      this.diachi.daydiachi = ''
    },
    focusMyElement: async function () {
      try {
        this.loading(true)
        console.log(22222222222)
        console.log(this.data)
         this.getTinh()
        if (await this.IS_CHON_DIACHI_CHITIET()) {
          this.isEnableThongTinThem = true
        }
        console.log(33333)
        console.log(this.dsTinh)
        if (this.data != null) {
          this.diachi.daydiachi = this.data.diachi
          if (this.data.tinh_id != null && this.data.tinh_id != 0) {
            this.diachi.tinh_id = this.data.tinh_id
            this.tentinh = this.dsTinh.filter(
              (x) => x.tinh_id == this.diachi.tinh_id
            )[0].tentinh

            this.diachi.quan_id = this.data.quan_id
            await this.getQuan()

            this.diachi.phuong_id = this.data.phuong_id
            await this.getPhuong()
            if (this.data.phuong_id != null) {
              if (this.data.pho_id != null && this.data.pho_id != 0) {
                this.diachi.pho_id = this.data.pho_id
                this.isPho = true
              }

              if (this.data.ap_id != null && this.data.ap_id != 0) {
                this.diachi.ap_id = this.data.ap_id
                this.isAp = true
              }

              if (this.data.khu_id != null && this.data.khu_id != 0) {
                this.diachi.khu_id = this.data.khu_id
                this.isKhu = true
              }
              await this.getPhoApKhu()
            }
            this.diachi.so_nha = this.data.so_nha
            this.diachi.daydiachi = this.data.diachi
            this.diachi.diachimoi = this.data.diachi

            this.diachi.block = this.data.block ? this.data.block : ''
            this.diachi.tang = this.data.tang ? this.data.tang : ''
            this.diachi.sophong = this.data.sophong ? this.data.sophong : ''
            this.diachi.motathem = this.data.motathem ? this.data.motathem : ''
          } else {
            if (this.dsTinh != null && this.dsTinh.length > 0) {
              this.diachi.tinh_id = this.dsTinh[0].tinh_id
            }
            this.tentinh = this.dsTinh.filter(
              (x) => x.tinh_id == this.diachi.tinh_id
            )[0].tentinh
            await this.getQuan()
          }
        } else {
          if (this.dsTinh != null && this.dsTinh.length > 0) {
            this.diachi.tinh_id = this.dsTinh[0].tinh_id
          }
          this.tentinh = this.dsTinh.filter(
            (x) => x.tinh_id == this.diachi.tinh_id
          )[0].tentinh
          await this.getQuan()
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    GetDataList: function (response) {
      console.log(response)
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    getTinh: async function () {
      let data = this.GetDataList(await API.lay_thongtin_tinh(this.axios))
      this.dsTinh = data
    },
    getQuan: async function () {
      this.dsQuan = this.GetDataList(await API.lay_ds_quan_huyen(this.axios, this.diachi.tinh_id))
      if (this.diachi.quan_id != null) {
        let filter = this.dsQuan.filter(x => x.QUAN_ID == this.diachi.quan_id)
        if (filter.length > 0) {
          this.tenquan = filter[0].TEN_QUAN
        } else {
          this.tenquan = ''
        }
      }
    },
    getPhuong: async function () {
      this.dsPhuong = this.GetDataList(await API.lay_ds_phuong_xa(this.axios, this.diachi.quan_id))
      if (this.diachi.phuong_id != null) {
        let phuong = this.dsPhuong.filter(x => x.PHUONG_ID == this.diachi.phuong_id)
        if (phuong.length > 0) {
          this.tenphuong = phuong[0].TEN_PHUONG
        } else {
          this.tenphuong = ''
        }
      }
    },
    getPhoApKhu: async function () {
      let data = this.GetData(await API.lay_ds_pho_ap_khu(this.axios, this.diachi.phuong_id))
      if (data != null) {
        this.dsPho = data.dsPho
        if (this.diachi.pho_id != null) {
          let pho = this.dsPho.filter(x => x.PHO_ID == this.diachi.pho_id)
          if (pho.length > 0) {
            this.tenpho = pho[0].TEN_PHO
          } else {
            this.tenpho = ''
          }
        }
        this.dsAp = data.dsAp
        if (this.diachi.ap_id != null) {
          let ap = this.dsAp.filter(
            (x) => x.PHO_ID == this.diachi.ap_id
          )
          if (ap.length > 0) {
            this.tenap = [0].TEN_AP
          } else {
            this.tenap = ''
          }
        }
        this.dsKhu = data.dsKhu
        if (this.diachi.khu_id != null) {
          let khu = this.dsKhu.filter(
            (x) => x.PHO_ID == this.diachi.khu_id
          )
          if (khu.length > 0) {
            this.tenkhu = khu[0].TEN_KHU
          } else {
            this.tenkhu = ''
          }
        }
      }
    },
    getDacDiem: async function () {
      let params = {
        phuong_id: this.phuong_id == null ? 0 : this.phuong_id,
        pho_id: this.pho_id == null ? 0 : this.pho_id,
        ap_id: this.ap_id == null ? 0 : this.ap_id,
        khu_id: this.khu_id == null ? 0 : this.khu_id
      }
      this.dsDacDiem = this.GetDataList(await API.lay_ds_dacdiem(this.axios, params))
    },
    onChangeTinh: async function () {
      try {
        this.loading(true)
        await this.getQuan()
        this.tentinh = this.dsTinh.filter(
          (x) => x.tinh_id == this.diachi.tinh_id
        )[0].tentinh
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    onChangeQuan: async function () {
      try {
        this.loading(true)
        await this.getPhuong()
        this.tenquan = this.dsQuan.filter(
          (x) => x.QUAN_ID == this.diachi.quan_id
        )[0].TEN_QUAN
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    onChangePhuong: async function () {
      try {
        this.loading(true)
        await this.getPhoApKhu()
        this.tenphuong = this.dsPhuong.filter(
          x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    onChangePho: async function () {
      try {
        this.loading(true)
        await this.getDacDiem()
        this.tenpho = this.dsPho.filter(
          (x) => x.PHO_ID == this.diachi.pho_id
        )[0].TEN_PHO
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    onChangeKhu: async function () {
      try {
        this.loading(true)
        await this.getDacDiem()
        this.tenkhu = this.dsKhu.filter(
          (x) => x.PHO_ID == this.diachi.khu_id
        )[0].TEN_PHO
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    onChangeAp: async function () {
      try {
        this.loading(true)
        await this.getDacDiem()
        this.tenap = this.dsAp.filter(
          (x) => x.PHO_ID == this.diachi.ap_id
        )[0].TEN_PHO
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    chapnhan: function () {
      if (this.isStrictMode && ((!this.isPho && !this.isAp && !this.isKhu) || (this.isPho && this.tenpho == '') ||
        (this.isAp && this.tenap == '') || (this.isKhu && this.tenkhu == ''))) {
        this.$root.$toast.error('Hãy chọn Phố / Ấp / Khu')
        return
      }
      this.$emit('xacnhan', {...this.diachi})
      this.$bvModal.hide(this.modalId)
    },
    btnTraCuuBanDoMangCap () {
      if (this.diachi.diachimoi) {
        this.showModalViTri = true
        console.log('data', this.getDiaChi)
        this.$bvModal.show(this.popupTraCuuBanDoMangCap)
      } else {
        this.showModalViTri = false
        this.$root.$toast.error('Chưa có thông tin về bản đồ khách hàng')
      }
    },
    async IS_CHON_DIACHI_CHITIET () {
      let data = this.GetData(await API.isExistMaTS(this.axios, {ma_ts: 'IS_CHON_DIACHI_CHITIET'}))
      if (data) {
        return data.result
      } else {
        return false
      }
    }
  }
}
</script>
<style>
</style>
