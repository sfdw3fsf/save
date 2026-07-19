<template src="./TraCuuHddtDHSX.html"></template>
<style scoped src="./TraCuuHddtDHSX.scss"></style>


<script>
import Vue from 'vue'
import moment from 'moment'
import KyCuoc from '@/components/KyCuoc2'
import TraCuuHDDTAPI from '../api/TraCuuHDDTDHSX'

export default {
  components: { appKyCuoc : KyCuoc},
  computed : {

  },
  created () {

  },
  data() {
    return {
      configs :{

      },
      params: {
        p_kycuoc: null,
        p_loai_timkiem: 1,
        p_ma_tt : null,
        p_ma_kh: null,
        p_fkey: null,
        p_seri: null,
        p_so_hoadon : null,
        p_keyword: null,
      },
      dsHoaDon: [],
      pageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
      keyDSHoaDon: 1,

    }
  },
  methods : {
    validateSearchForm()
    {
      let errors = []

      if(this.params.p_loai_timkiem === 2)
      {
        if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '')
        {
          errors.push('Phải nhập Mã TT')
        } else
        {
          this.params.p_ma_tt = this.params.p_ma_tt.trim()
          this.params.p_keyword = this.params.p_ma_tt
        }

      } else if(this.params.p_loai_timkiem === 1)
      {
        if(this.params.p_ma_kh === null || this.params.p_ma_kh.trim() === '')
        {
          errors.push('Phải nhập Mã KH')
        } else
        {
          this.params.p_ma_kh = this.params.p_ma_kh.trim()
          this.params.p_keyword = this.params.p_ma_kh
        }
      } else if(this.params.p_loai_timkiem === 3)
      {
        if(this.params.p_fkey === null || this.params.p_fkey.trim() === '')
        {
          errors.push('Phải nhập FKey')
        } else
        {
          this.params.p_fkey = this.params.p_fkey.trim()
          this.params.p_keyword = this.params.p_fkey
        }
      } else if(this.params.p_loai_timkiem === 4)
      {
        if(this.params.p_seri === null || this.params.p_seri.trim() === '')
        {
          errors.push('Phải nhập Seri')
        } else
          this.params.p_seri = this.params.p_seri.trim()

        if(this.params.p_so_hoadon === null || this.params.p_so_hoadon.trim() === '')
        {
          errors.push('Phải nhập Số hóa đơn')
        } else
          this.params.p_so_hoadon = this.params.p_so_hoadon.trim()

        this.params.p_keyword = this.params.p_seri + '|' + this.params.p_so_hoadon
      }

      if(errors.length > 0)
      {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    doSearch()
    {
      if(!this.validateSearchForm())
      {
        return
      }

      let postData = {
        kyCuoc: this.params.p_kycuoc,
        value:this.params.p_keyword,
        kieu: this.params.p_loai_timkiem
      }

      this.loading(true)

      this.dsHoaDon = []
      this.pageInfo.totalElement = 0

      TraCuuHDDTAPI.tracuuHDDT(this.axios, postData)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0)
            {
              this.dsHoaDon = response.data.data
              this.pageInfo.totalElement = response.data.data.length
              this.keyDSHoaDon++
            } else
            {
              this.$toast.error('Không tìm thấy số liệu.')
            }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })

    },

    clearForm()
    {
      this.params.p_ma_kh = null
      this.params.p_ma_tt = null
      this.params.p_fkey = null
      this.params.p_seri = null
      this.params.p_so_hoadon = null
    },
    doChapNhan()
    {

    },

    RowChanged(args)
    {
      console.log(args)
      if(args === null)
      {
        this.clearForm()
        return
      }

      this.clearForm()

      this.params.p_ma_kh = args.MA_KH
      this.params.p_ma_tt = args.MA_TT
      this.params.p_fkey = args.FKEY
      this.params.p_seri = args.SO_SERI
      this.params.p_so_hoadon = args.SO_HOADON


    },
    chukycuocChangeHandler(e) {
      //console.log(e)
      if(e.p_chuky_no !== null)
      {
        this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      }
      else
        this.params.p_kycuoc = null
    },
  },
}
</script>
