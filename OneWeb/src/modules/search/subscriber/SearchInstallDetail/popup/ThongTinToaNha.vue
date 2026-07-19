<template src="./ThongTinToaNha.html"></template>

<script>

import breadcrumb from '@/components/breadcrumb'
import API from './../API'
import gridview from '@/components/Shared/gridview.vue'

export default {
    components: { gridview },
  name: 'pop_toanha',
  props: 
    ['magd','sotb','tentb']
  ,

  mounted () {
    API.css_quan(this.axios,null).then(response =>{
      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
          this.listbox.list_quan.list = response.data.data;
      else
        this.listbox.list_quan.list = [];
    });

    
  },

  data () {
    return {
      listbox:{
        list_quan:{
          list:[],
          value:""
        },
        list_phuong:{
          list:[],
          value:""
        },
        list_toanha:{
          list:[],
          value:""
        }

      }
      
    }
  },

  methods: {
    quan_selected:function(){
      var data = {
        "ID": this.listbox.list_quan.value
      };

      API.css_phuong(this.axios,data).then(response =>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
          this.listbox.list_phuong.list = response.data.data;
        else
          this.listbox.list_phuong.list = [];
      });
    
    },


    phuong_selected:function(){
      var data = {
              "quan_id":this.listbox.list_quan.value,
              "phuong_id":this.listbox.list_phuong.value
          }

      API.sp_lay_ds_toanha_by_quanphuong(this.axios,data).then(response =>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
          this.listbox.list_toanha.list = response.data.data;
        else
          this.listbox.list_toanha.list = [];
      });
    }    ,


  }
}
</script>
