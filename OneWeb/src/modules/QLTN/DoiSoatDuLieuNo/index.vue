<template src="./template.html"></template>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import api from './API.js'
import moment from 'moment'
import {Icon} from '@iconify/vue2'
import xlsx from 'xlsx'
export default {
  components: {
    breadcrumb,
    Icon
  },
  mounted() {
    //this.load_data()
    this.loadcombo()
  },
  data() {
    return {
      kieudoisoat_options: [],
      kieudoisoat_value: null,
      chukyno_options: [],
      bangdoisoat_options: [],
      bangdoisoat_value: null,  
      chukyno_value: null,
      user_thuchien: null,
      tbThongtinno_columns: [
        { headerText: 'Mã Thanh toán', fieldName: 'ma_tt', width: '100' },
        { headerText: 'Mã Thanh toán CCBS', fieldName: 'ma_tt_neo', width: '100' },
        { headerText: 'Tổng nợ', fieldName: 'tongno_onebss', width: '70px' ,textAlign: 'right' },
        { headerText: 'Tổng nợ CCBS', fieldName: 'tongno_ccbs', width: '70px' ,textAlign: 'right' },
        { headerText: 'Tiền lệch', fieldName: 'tien_lech', width: '70px' ,textAlign: 'right' }
      ],
      tbThongtinno_data: []
    }
  },
  methods: {
    tbtnXuatExcel_click(){
      let data = this.tbThongtinno_data.map((item) => {
        return {
          'Kỳ cước': item.kyhoadon,
          'Mã Thanh toán': item.ma_tt,
          'Mã Thanh toán CCBS': item.ma_tt_neo,
          'Tổng nợ': item.tongno_onebss,
          'Tổng nợ CCBS': item.tongno_ccbs,
          'Tiền lệch': item.tien_lech
        }
      })
      let ws = xlsx.utils.json_to_sheet(data)
      let wb = xlsx.utils.book_new()
      xlsx.utils.book_append_sheet(wb, ws, 'Sheet1')
      xlsx.writeFile(wb, 'DoiSoatDuLieuNo.xlsx')
      
    },
    async loadcombo(){
      try {
        this.loading(true)
        this.user_thuchien = this.$root.token.getUserName()
        let res2 = await api.lay_danh_muc(this.axios, {
          p_tt_bien: { DANHMUC: 'KIEU_TK' }
        })
        // console.log(res2.data.data);
        if (res2.data.data && res2.data.data.length > 0) {
          this.kieudoisoat_options = res2.data.data.map((item) => {
            return {
              id: item.id,
              text: item.ten_id,
              ds_cot: item.ds_cot
            }
          })
          this.kieudoisoat_value = this.kieudoisoat_options[0].id
        }
        let bangdoisoat = await api.lay_danh_muc(this.axios, {
          p_tt_bien: { DANHMUC: 'BANG_DOISOAT' }
        })
        if (bangdoisoat.data.data && bangdoisoat.data.data.length > 0) {
          this.bangdoisoat_options = bangdoisoat.data.data.map((item) => {
            return {
              id: item.id,
              text: item.ten_id
            }
          })
          this.bangdoisoat_value = this.bangdoisoat_options[0].id
        }
        let currentMonth = new Date().getMonth() - 1
        // return month in format MM
        currentMonth = currentMonth < 10 ? `0${currentMonth}` : currentMonth
        let currentYear = new Date().getFullYear()
        let res3 = await api.get_ds_chuky_no(this.axios)
        if (res3.data.data && res3.data.data.length > 0) {
          this.chukyno_options = res3.data.data.map((item) => {
            return {
              id: item.CHUKYNO,
              text: item.DANGCHU
            }
          })
          this.chukyno_value = `${currentYear}${currentMonth}01`
        }
      } catch (error) {
        
      } finally {
        this.loading(false)
      }
    },
    async load_data() {
      try {
        this.loading(true)
        let res = await api.lay_ds_doi_soat(this.axios, {
          p_tt_bien: { KYHOADON: this.chukyno_value, KIEUDS_ID: this.kieudoisoat_value, BANGDS_ID: this.bangdoisoat_value}
        })
        if (res.data.data && res.data.data.length > 0) {
          this.tbThongtinno_data = res.data.data.map((item, index) => {
            return {
              ...item,
              stt: index + 1,
              ma_tt: item.ma_tt,
              ma_tt_neo: item.ma_tt_neo,
              tongno_onebss: this.currentcyFormat(item.tongno_onebss),
              tongno_ccbs: this.currentcyFormat(item.tongno_ccbs),
              tien_lech: this.currentcyFormat(item.tongno_onebss - item.tongno_ccbs),

            }
          })
        }
        else{
          this.tbThongtinno_data = []
          this.$toast.error(res.data.message_detail.split(":")[1].trim())
        }
      
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    currentcyFormat(value) {
      return value.toLocaleString('de-DE');
    },
  },
  watch: {
    kieudoisoat_value: function(val) {
      if (val) {
        this.tbThongtinno_data = []
        let kieudoisoat = this.kieudoisoat_options.find((item) => item.id == val)
        const showColumns = kieudoisoat.ds_cot ? kieudoisoat.ds_cot : ''
        console.log(showColumns)
        if (showColumns.includes('CHUKYNO')) {
          this.tbThongtinno_columns = this.tbThongtinno_columns.filter((item) => item.fieldName != 'chukyno')
          this.tbThongtinno_columns = [{ headerText: 'Tháng nợ', fieldName: 'chukyno', width: '100' }].concat(this.tbThongtinno_columns)
        }
        if (showColumns.includes('KYHOADON')) {
          this.tbThongtinno_columns = this.tbThongtinno_columns.filter((item) => item.fieldName != 'kyhoadon')
          this.tbThongtinno_columns = [{ headerText: 'Kỳ cước', fieldName: 'kyhoadon', width: '100' }].concat(this.tbThongtinno_columns)
        }
        if(!showColumns.includes('KYHOADON')) 
        {
          console.log('remove ky_hoadon');
          this.tbThongtinno_columns = this.tbThongtinno_columns.filter((item) => item.fieldName != 'kyhoadon')
        }
        if (!showColumns.includes('CHUKYNO'))
        {
          console.log('remove chukyno');
          this.tbThongtinno_columns = this.tbThongtinno_columns.filter((item) => item.fieldName != 'chukyno')
        }
        console.log(this.tbThongtinno_columns);
      }
    }
  }
}
</script>

<style lang="scss" scoped>
ul{
  li{
    cursor: pointer;
  }
}
</style>
