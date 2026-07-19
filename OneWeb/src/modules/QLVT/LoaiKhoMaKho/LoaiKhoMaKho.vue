<template src='./LoaiKhoMaKho.html'></template>

<script>

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from '@/components/breadcrumb'
import Vue from "vue";

import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { Query, Predicate } from "@syncfusion/ej2-data";
import popupLoaiKhoMaKho from '../popupLoaiKhoMaKho'

Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb, popupLoaiKhoMaKho},
  name: "LoaiKhoMaKho",
  mounted() {
    var f = this.$refs.LoaiKhoMaKho
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
      var f = this.$refs.LoaiKhoMaKho
      f.OpenDialog()
    }
  },
  created: async function() {       
  
  }
 
};
</script>