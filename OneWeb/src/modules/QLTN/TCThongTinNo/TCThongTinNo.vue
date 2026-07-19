<template src="./TCThongTinNo.html"></template>

<script>
import Vue from "vue"
import { mapActions, mapState } from "vuex"
import moment from 'moment'
import CommonsAPI from '../api/Commons'
import TCThongTinNoAPI from '../api/TCThongTinNo'
import Calculator from '@/components/Calculator'

export default {
  name: 'TCThongTinNo',
  components : {
    appCalculator: Calculator,
  },
  async created () {
    await this.getKyHoaDonHienTai()
    if (this.kyHoaDonHienTai !== undefined) {
      this.params.p_kyhoadon = moment(this.kyHoaDonHienTai.KYCUOC, 'YYYYMMDD').toDate()
    }
    this.updateChuKyHoaDon(moment(this.params.p_kyhoadon).format('YYYYMM'))
  },
  computed: {
    ...mapState("qltnCommon", [
      "kyHoaDonHienTai"
    ]),

  },
  data() {
    return {
      configs: {
        max_kyhoadon: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
        isTongNoCalShow: false
      },
      params:{
        p_kyhoadon: null,
        p_chuky_no: null,
        p_chuky_no_options: [],
        p_somay_acc: null,
        p_ma_tt: null,
        p_diachi_tt: null,
        p_ten_tt: null,
        p_tong_no: 0,
        p_kieu: null,
      },
      dsThongTinNo: [],
      dsThongTinTra: [],
      keyThongTinNo: 0,
      keyThongTinTra: 0,
      thongtinNoPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
      thongtinTraPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
    }
  },
  methods: {
    ...mapActions("qltnCommon", [
        "getKyHoaDonHienTai",
    ]),
    onKyHoaDonChange: function(args) {
      if(args.value === null){
        this.$toast.error('Vui lòng chọn Kỳ hóa đơn')
        return
      }
      this.updateChuKyHoaDon(moment(args.value).format('YYYYMM'))
    },
    updateChuKyHoaDon: function(pKyHoaDon) {
      //this.loading(true)
      this.params.p_chuky_no_options = []
      this.params.p_chuky_no = null

      CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon)
      .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0) {
                this.params.p_chuky_no_options = response.data.data
                this.params.p_chuky_no = this.params.p_chuky_no_options[0].CHUKY
            }
          }
        )
      .catch (error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        //this.loading(false)
      })
    },

    onTraCuu()
    {
      if((this.params.p_ma_tt === null || ('' + this.params.p_ma_tt).trim() === '')
        && (this.params.p_somay_acc === null || ('' + this.params.p_somay_acc).trim() === ''))
      {
        this.$toast.error('Bạn hãy nhập mã thuê bao hoặc mã thanh toán!')
        this.$refs.refSomayAcc.focus()
        return false
      }

      this.HienThi_DanhSach()

    },

    HienThi_DanhSach()
    {
      if(this.params.p_somay_acc !== null && ('' + this.params.p_somay_acc).trim() !== '')
      {
        this.params.p_somay_acc = this.params.p_somay_acc.trim()
        this.LAY_THONGTIN_TT_THEO_TB()
        this.params.p_kieu = 1
      } else if (this.params.p_ma_tt !== null && ('' + this.params.p_somay_acc).trim() !== '')
      {
        this.params.p_ma_tt = this.params.p_ma_tt.trim()
        this.LAY_THONGTIN_TT_TB_THEO_TT()
        this.params.p_kieu = 2
      }
    },
    LAY_THONGTIN_TT_THEO_TB()
    {
      let postData = {
        kyCuoc: moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        maTB: this.params.p_somay_acc
      }
      this.loading(true)

      this.clearResult()

      TCThongTinNoAPI.getThanhToanTheoThueBao(this.axios, postData)
        .then(res => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.params.p_ma_tt = res.data.data[0].MA_TT
            this.params.p_ten_tt = res.data.data[0].TEN_TT
            this.params.p_diachi_tt = res.data.data[0].DIACHI_TT

            this.GET_ALL()
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    clearResult()
    {
      this.params.p_ten_tt = null
      this.params.p_diachi_tt = null

      this.dsThongTinNo = []
      this.dsThongTinTra = []
    },
    LAY_THONGTIN_TT_TB_THEO_TT()
    {
      let postData = {
        kyCuoc: moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        maTB: null,
        maTT: this.params.p_ma_tt,
      }
      this.loading(true)

      this.clearResult()


      TCThongTinNoAPI.getThanhToanTheoThueBao(this.axios, postData)
        .then(res => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.params.p_ma_tt = res.data.data[0].MA_TT
            this.params.p_ten_tt = res.data.data[0].TEN_TT
            this.params.p_diachi_tt = res.data.data[0].DIACHI_TT


          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    GET_ALL()
    {
      this.LAY_THONGTIN_NO_THUEBAO()
      this.LAY_THONGTIN_TRA_THUEBAO()
      this.LAY_TONGNO_THUEBAO()
    },

    LAY_THONGTIN_NO_THUEBAO()
    {
      let postData = {
        kyCuoc: moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        maTB:   this.params.p_somay_acc,
        maTT: this.params.p_ma_tt,
        loaiTienId: 1,
        kieu: this.params.p_kieu
      }
      TCThongTinNoAPI.getThongTinNoThueBao(this.axios, postData)
        .then(res => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.dsThongTinNo = res.data.data
            this.thongtinNoPageInfo.totalElement = res.data.data.length
            this.thongtinNoPageInfo.pageIndex = 0
            this.keyThongTinNo++
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally()
    },

    LAY_THONGTIN_TRA_THUEBAO()
    {
      let postData = {
        kyCuoc: moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        maTB:   this.params.p_somay_acc,
        maTT: this.params.p_ma_tt,
        loaiTienId: 1,
        kieu: this.params.p_kieu
      }
      TCThongTinNoAPI.getThongTinTraThueBao(this.axios, postData)
        .then(res => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.dsThongTinTra = res.data.data
            this.thongtinTraPageInfo.totalElement = res.data.data.length
            this.thongtinTraPageInfo.pageIndex = 0
            this.keyThongTinTra++
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally()
    },

    LAY_TONGNO_THUEBAO()
    {
      let postData = {
        kyCuoc: moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        maTB:   this.params.p_somay_acc,
        maTT: this.params.p_ma_tt,
        loaiTienId: 1,
        kieu: this.params.p_kieu
      }
      TCThongTinNoAPI.getThongTinTongNoThueBao(this.axios, postData)
        .then(res => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.params.p_tong_no = res.data.data[0].TONGNO
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally()
    },

    getThongTinNoThueBao()
    {
      /*
      let postData = {
        kyCuoc: moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        maTB:
        String maTT;
        Long loaiTienId;
        Long kieu;
      }
      */
    },


  }
}
</script>
