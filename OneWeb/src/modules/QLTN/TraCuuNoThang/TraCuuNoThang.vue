<template src="./TraCuuNoThang.html"></template>

<script>
import Vue from 'vue'
import { mapActions, mapState } from "vuex"
import moment from 'moment'
import TraCuuPhieuTraVinaPhoneAPI from '../api/TraCuuPhieuTraVinaPhone.js'
import GachNoAPI from '../api/GachNoAPI'


export default {
  async created () {
    await this.getKyHoaDonHienTai()
    if (this.kyHoaDonHienTai !== undefined) {
      this.configs.p_kyhd_hientai = this.kyHoaDonHienTai.KYCUOC
    }
  },
  computed: {
    ...mapState("qltnCommon", [
      "kyHoaDonHienTai"
    ]),
  },
  data () {
    return {
      configs: {
        p_kyhd_hientai: null
      },
      params: {
        p_ma_tt_neo: null,
        p_ma_tb: null,
        p_ten_kh: null,
        p_diachi_kh: null,
        p_mst: null,
        p_manv_tc: null,
        p_matb_dd: null,
        p_ma_tuyen: null
      },
      dsThongTinNo: [],
      keyThongTinNo: 0
    }
  },
  methods: {
    ...mapActions("qltnCommon", [
        "getKyHoaDonHienTai",
    ]),
    doSearch()
    {
      let postData = {
        kyhoadon: this.configs.p_kyhd_hientai,
        maKH: this.params.p_ma_tt_neo,
        maTB: this.params.p_ma_tb
      }




/*    CCBSFacade().ts_tracuu_ls_notra(kyhoadon, ma_tt_neo, txtMaTB.Text, "pgdien_hpg", tt_nd.tentat_ccbs);

      this.loading(true)
      TraCuuPhieuTraVinaPhoneAPI.getMaTTNeoVNP(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0)
          {
            this.params.p_ma_tb = res.data.data[0].MATB_DD
            this.Fill_Info(this.params.p_ma_tt_neo)
          } else
          {
            this.$toast.error('Không tìm thấy mã thanh toán này')
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false)})

*/
    },
    GetMa_TT_Neo(type)
    {
      if(type === 1)  // search by Ma Neo
      {
        if(this.params.p_ma_tt_neo === null
          || ('' + this.params.p_ma_tt_neo).trim() === '')
          {
            this.$toast.error('Chưa nhập mã khách hàng để tra cứu')
            return
          }
        this.params.p_ma_tt_neo = this.params.p_ma_tt_neo.trim()
      } else if(type === 2) // search by so may
      {
        if(this.params.p_ma_tb === null
          || ('' + this.params.p_ma_tb).trim() === '')
          {
            this.$toast.error('Chưa nhập số máy để tra cứu')
            return
          }
      }

      let postData = {
        kyCuoc: this.configs.p_kyhd_hientai,
        ma: this.params.p_ma_tt_neo,
        chiTiet: 1  // tổng hợp
      }

      this.loading(true)


        TraCuuPhieuTraVinaPhoneAPI.getMaTTNeoVNP(this.axios, postData)
          .then((res) => {
            if(res.data.error_code === 'BSS-00000000'
              && res.data.data !== undefined
              && res.data.data.length > 0)
            {
              this.params.p_ma_tb = res.data.data[0].MATB_DD
              this.Fill_Info(this.params.p_ma_tt_neo)
            } else
            {
              this.$toast.error('Không tìm thấy mã thanh toán này')
            }
          })
          .catch(error => {
            this.$toast.error(error.data.message_detail)
          })
          .finally(() => { this.loading(false)})
    },
    async Fill_Info(keyword)
    {
      let response = await this.getPayment_Info_ForPay(keyword);
      if(response.data.error_code === "BSS-00000000" && response.data.data !== undefined)
      {
        console.log(response)
        this.params.p_matb_dd = response.data.data[0].MATB_DD
        this.params.p_ma_tuyen = response.data.data[0].MA_TUYENTHU
        this.params.p_mst = response.data.data[0].MST
        this.params.p_manv_tc = response.data.data[0].MANV_TC
        this.params.p_diachi_kh = response.data.data[0].DIACHI_TT
        this.params.p_ten_kh = response.data.data[0].TEN_TT
      }

    },
    async getPayment_Info_ForPay(paycode) {
      this.loading(true)
      try{
        const results = await GachNoAPI.getPayment_Info_ForPay(this.axios, {pKyCuoc: this.configs.p_kyhd_hientai, pPaymentCode: paycode});
        return results
      }catch(err) {
        console.log(err.data.message_detail)
      }finally{
        this.loading(false)
      }
    },
  }
}
</script>
