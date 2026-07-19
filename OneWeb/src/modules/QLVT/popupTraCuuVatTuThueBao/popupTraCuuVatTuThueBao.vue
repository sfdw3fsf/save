<template src='./popupTraCuuVatTuThueBao.html'></template>

<script>

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from '@/components/breadcrumb'
import Vue from "vue";

import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { Query, Predicate } from "@syncfusion/ej2-data";
import TraCuuVatTuThueBao from '../components/TraCuuVatTuThueBao/TraCuuVatTuThueBao'

Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb, TraCuuVatTuThueBao},
  name: "popupTraCuuVatTuThueBao",
  mounted() {
    var f = this.$refs.frmTraCuuVatTuThueBao
    f.dialogOpen()
  },
  
  data() {
    return {
      
    };
  },

  computed: {
  

    
  },
  methods: {
    
    openForm(){
      var f = this.$refs.frmTraCuuVatTuThueBao
      f.dialogOpen()
    }
  },
  created: async function() {       
  
  }
 
};
</script>