<template src="./DiaChiLD.html"></template>

<script>

import breadcrumb from '@/components/breadcrumb'
import API from './../API'

export default {
    components: {  },
  name: 'DiaChiLD',
  props: [
    'hdtb_id', 
    'ma_tb' ,'loai_hd' ,'ma_gd' ,'dichvuvt_id','diachi'],

  mounted () {
  
      API.dichvu_vt(this.axios,null).then(response=>{
      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        this.listbox.dichvu_vt.list = response.data.data;
        this.listbox.dichvu_vt.value = this.dichvuvt_id;

      }
      else
        this.listbox.dichvu_vt.list = [];

      var data={
        "hdtb_id":632743,
        "dichvu_id":9
        }
      
        this.dc_cu= this.diachi.dc_cu;
        this.dc_moi= this.diachi.dc_moi;
        this.dc_dau_cu= this.diachi.dc_dau_cu;
        this.dc_dau_moi= this.diachi.dc_dau_moi;
        this.dc_cuoi_cu= this.diachi.dc_cuoi_cu;
        this.dc_cuoi_moi= this.diachi.dc_cuoi_moi;
        this.totc_cu= this.diachi.totc_cu;
        this.totc_moi=this.diachi.totc_moi;
        this.totc_dau_cu= this.diachi.totc_dau_cu;
        this.totc_dau_moi= this.diachi.totc_dau_moi;
        this.totc_cuoi_cu= this.diachi.totc_cuoi_cu;
        this.totc_cuoi_moi= this.diachi.totc_cuoi_moi;
        this.tocdo= this.diachi.tocdo;
        this.sdt_dau= this.diachi.sdt_dau;
        this.sdt_cuoi= this.diachi.sdt_cuoi;
    })
      
    
  },

  data () {
    return {
      listbox:{
         dichvu_vt:{
          list:[],
          value: "",
        },
        dc_cu: "",
        dc_moi: "",
        dc_dau_cu: "",
        dc_dau_moi: "",
        dc_cuoi_cu: "",
        dc_cuoi_moi: "",
        totc_cu: "",
        totc_moi: "",
        totc_dau_cu: "",
        totc_dau_moi: "",
        totc_cuoi_cu: "",
        totc_cuoi_moi: "",
        tocdo: "",
        sdt_dau: "",
        sdt_cuoi:"",
      }
      
    }
  },

  methods: {
    
    
  }
}
</script>
