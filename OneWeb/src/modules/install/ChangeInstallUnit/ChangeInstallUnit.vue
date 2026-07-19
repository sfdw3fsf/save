<template src="./ChangeInstallUnit.html"></template>

<style src="./ChangeInstallUnit.scss"></style>
<script>

import breadcrumb from '@/components/breadcrumb'
import API from '../../admin/API/API_Admin'
import gridview from './gridview.vue'

export default {
  components: { breadcrumb,gridview },
  name: 'ChangeInstallUnit',
  mounted () {
    API.get_ds_kieu_donvi(this.axios, null).then(
        (response) => {
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            //console.log(response.data);
            this.listbox.kieu_donvi.list = response.data.data;
          }
        }
      );

     API.get_ds_quan(this.axios, null).then(
        (response) => {
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' ) {
            console.log(response.data);
            this.listbox.quan.list = response.data.data;
                      
          }
        }
      );


  },
  //end mounted
  
  destroyed () {},
  data () {
    return {
      listbox: {
        kieu_donvi:{
          list:[],
          value: "",
          isEnabled:false,
        },
        quan:{
          list:[],
          value: "",
          isEnabled:false,
        },
        phuong: {
            list: [],
            header:[],
            value: "",
            isEnabled: false,
        },
        pho: {
            list: [],
            header:[],
            value: "",
            isEnabled: false,
        },
        ap: {
            list: [],
            header:[],
            value: "",
            isEnabled: false,
        },
        khu: {
            list: [],
            header:[],
            value: "",
            isEnabled: false,
        },
        đacdiem: {
            list: [],
            header:[],
            value: "",
            isEnabled: false,
        },
      }
    }
  },

  methods: {
   

    get_phuong: function (event) {
      console.log('this.listbox.quan.value '+this.listbox.quan.value);
       API.get_ds_phuong(this.axios,{params:{quanId:this.listbox.quan.value}}).then((response) => {
              if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            console.log(response);
            this.listbox.phuong.list = response.data.data;
        }
            
        else {
            this.listbox.phuong.list = [];
            //this.so_tbao_thanhcong=0;
        }
        });
    },

    filterMedia: function (event) {
        if (!this.date_enable){
          this.date_enable = true;
           $(".datepicker").removeAttr('disabled');
           this.tuNgay = "";
           this.denNgay = "";
        }
        else{
           this.date_enable = false;
          $(".datepicker").attr('disabled','disabled');
          this.tuNgay = "";
           this.denNgay = "";
        }
     
    },

  }
}
</script>
