<template src="./SearchAccount.html"></template>
<style scoped src="./SearchAccount.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import SearchAccountAPI from '../../api/SearchAccountAPI'
export default {
  components: { breadcrumb },
  name: 'SearchAccount',
  mounted () {
    //let getData = {};
    SearchAccountAPI.get_dichvu_vienthong(this.axios, {}).then(
        (response) => {
          if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
            this.listbox.dichvu_vienthong.list = response.data.data;
            this.filterbox.dichvu_vienthong.list = response.data.data;
          }
        }
      );
    SearchAccountAPI.get_kieu_taikhoan(this.axios, {params:{nhomId:2}}).then(
        (response) => {
          if(response && response.data && response.data.data && response.data.error && response.data.error === 'BSS-00000000' && response.data.data) {
            console.log(response.data);
            this.listbox.dk_tracuu.list = response.data.data;
            //this.filterbox.dichvu_vienthong.list = response.data.data;
          }
        }
      );
  },
  destroyed () {},
  data () {
    return {
      header: {
        title: 'TRA CỨU DANH BẠ',
        list: [
          { name: 'Tra cứu danh bạ', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu phiếu thanh toán theo ngày',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      listbox: {
        dichvu_vienthong:{
          list:[],
          value: "",
          isEnabled:false,
        },
        dk_tracuu: {
          list:[],
          dk1: {
            value:"",
            isEnabled:true
          },
          dk2: {
            value:"",
            isEnabled:false
          },
          dk3: {
            value:"",
            isEnabled:false
          }
        }
      },
      searchbox: {
        isService:false,
        isUnit:false,
        is2ndCond: false,
        is3rdCond: false,
      },
      filterbox: {
        dichvu_vienthong:{
          list:[],
          value: "",
          isEnabled:false,
        },
        isType:false,
      },
      fn_chukyno_kyhoadon_sel_val: null,
      sel_lay_thang_no: [],
      fn_chukyno_kyhoadon_sel: [],
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date()
    }
  },
  methods: {
    alert: function () {
      alert('Hello')
    }
  }
}
</script>
