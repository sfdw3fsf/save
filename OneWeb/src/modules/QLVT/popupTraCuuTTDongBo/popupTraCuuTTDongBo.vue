<template src='./popupTraCuuTTDongBo.html'></template>

<script>


import breadcrumb from '@/components/breadcrumb'
import Vue from "vue";
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import TraCuuTTDongBo from '../TraCuuTTDongBo'

Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb, TraCuuTTDongBo},
  name: "popupTraCuuVatTuThueBao",
  mounted() {
    var f = this.$refs.frmTraCuuTTDongBo
    f.OpenDialog()
  },
  
  data() {
    return {
      
    };
  },

  computed: {
  

    
  },
  methods: {
    
    openForm(){
      var f = this.$refs.frmTraCuuTTDongBo
      f.OpenDialog()
    }
  },
  created: async function() {       
  
  }
 
};
</script>