<template src="./ThongTinDonHang.html"> </template>

<script>
import api from '../../API.js'
export default {
  data() {
    return {
      hdkh_id: null,
      phanvung_id: null,
      parent: null,
      order_detail: null,
      trangthaitt_options: [],
      loaihinh_options: [],
      dichvuvt_id: null,
      hinhthuctt_options: [],
      ds_goi_dadv: [],
      tinh_id: null,
      ghichudon: '',
  }
},
  props: {
    donhang_id: {
      type: String,
      default: ''
    },
    dhData: {
      type: Object,
      default: null
    }
  },
  methods: {
    async handleShowModal() {
      
      console.log(this.dhData);
    },
    hideModal() {
      this.$refs.ThongTinDonHangModal.hide()
    },
    async showModal() {
      if(!this.hdkh_id) {
        this.$toast.error('Không có thông tin đơn hàng')
        return
      }
      await this.getThongTin()
      this.$refs.ThongTinDonHangModal.show()
    },
    async getThongTin(){
      try {
        this.loading(true)
        let res = await api.getChiTietDonHang(this.axios, {
          p_hdkh_id: this.hdkh_id,
          p_phanvung_id: this.phanvung_id
        })
        if(res.data.error_code == 'BSS-00000000')
        {
          this.order_detail = res.data.data[0]
          console.log(this.order_detail);
          this.dichvuvt_id = this.order_detail.dichvuvt_id
        }
        let res1 = await api.getDSTrangThaiTT(this.axios,{})
        this.trangthaitt_options = res1.data.data.map(item => {
          return {
            id: item.trangthai_id,
            text: item.ten_tt
          }
        })
        let res2 = await api.getDSHinhThucTT(this.axios,{})
        this.hinhthuctt_options = res2.data.data.map(item => {
          return {
            id: item.ma_httt,
            text: item.hinhthuc_tt
          }
        })
        let res3 = await api.lay_ds_goi_cv_vnp_v1(this.axios,
        {
          tinh_id: this.tinh_id,
          loaitb_id: this.order_detail.loaitb_id,
        })
        this.ds_goi_dadv = res3.data.data.map(item => {
          return {
            id: item.goi_id,
            text: item.ten_goi
          }
        })
      } catch (error) {
        
      } finally {
        this.loading(false)
      }
    },
  },
  computed: {
  },
  async mounted() {
    this.tinh_id = (await this.$root.token.getPhanVungID()) * 1
  },
  watch: {
    dichvuvt_id: {
      handler: async function (val, oldVal) {
        let res = await api.getDSLoaiHinh(this.axios,val);
        this.loaihinh_options = res.data.data.map((item) => {
          return {
            id: item.loaitb_id,
            text: item.loaihinh_tb
          }
        })
      },
      deep: true
    },
  },
  watch: {
    dichvuvt_id: {
      handler: async function (val, oldVal) {
        let res = await api.getDSLoaiHinh(this.axios,val);
        this.loaihinh_options = res.data.data.map((item) => {
          return {
            id: item.loaitb_id,
            text: item.loaihinh_tb
          }
        })
      },
      deep: true
    },
  },
}
</script>

<style lang="scss" scoped>
</style>
