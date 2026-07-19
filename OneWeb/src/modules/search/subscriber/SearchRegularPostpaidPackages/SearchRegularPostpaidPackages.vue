<template src="./SearchRegularPostpaidPackages.html"></template>
<style src="./SearchRegularPostpaidPackages.scss"></style>
<script>
import XLSX from 'xlsx'
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import api from './api.js'
export default {
  components: {
    XLSX,
    moment,
    breadcrumb
  },
  name: 'SearchRegularPostpaidPackages',
  data() {
    return {
      loading: false,
      inptimKiem:"",
      header: {
        title: 'Tra cứu thông tin gói cước thuê bao trả sau thường',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      headerGridbox: [
        { fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: false, textAlign: "center", width: 130},
        { fieldName: 'SO_TB', headerText: 'Số máy', allowFiltering: true, allowSorting: true, textAlign: "center" },
        { fieldName: 'PACKAGE_NAME', headerText: 'Gói cước', allowFiltering: true, allowSorting: true, textAlign: "center" },
        { fieldName: 'DISPATCH_NAME', headerText: 'Công văn', allowFiltering: true, allowSorting: true },
        { fieldName: 'DATE_FROM', headerText: 'Ngày bắt đầu', allowFiltering: true, allowSorting: true },
        { fieldName: 'DATE_TO', headerText: 'Ngày kết thúc', allowFiltering: true, allowSorting: true }, // KO BT
        { fieldName: 'THANG', headerText: 'Chu kỳ', allowFiltering: true, allowSorting: true, textAlign: "center" }
      ],
      gridBox: {
        data: [],
        selected: null
      },
    }
  },
  mounted() {
  },
  created() {},
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  methods: {
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async btnSearch_Click(){
      try
      {
          var soTB=""
          if (this.inptimKiem){
              soTB = "84" + this.inptimKiem;
          }
          await this.lay_ds_tb(soTB);
      }
      catch (error) {
        this.$toast.error('lỗi: ' + error.data.message_detail)
      }
    },
    lay_ds_tb: async function (soTB) {
      var data = []
      this.loading = true
      //var vma_tinh = this.$root.token.getMaTinh()
      //var user_nd = this.$root.token.getMaNhanVien()
      try {
        data = this.GetData(await api.tim_kiem(this.axios, {
          "so_tb": soTB
         }))
      } catch (error) {
        this.$toast.error('lỗi tìm kiếm: ' + error.data.message_detail)
      }
      this.loading = false
      if(data.length> 0){
        let ds = []
        data.forEach((element, index) => {
            element.STT = index + 1
            ds.push(element)
        })
        this.gridBox.data = ds
      }else{
          this.gridBox.data = data
      }
    },
  }
}
</script>
