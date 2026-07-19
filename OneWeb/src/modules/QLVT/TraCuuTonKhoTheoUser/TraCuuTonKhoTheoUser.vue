<template src='./TraCuuTonKhoTheoUser.html'></template>

<script>

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from '@/components/breadcrumb'
import Vue from "vue";

import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { Query, Predicate } from "@syncfusion/ej2-data";
import frmVatTuTrongKho from '../frmVatTuTrongKho'

Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb, frmVatTuTrongKho},
  name: "TraCuuTonKhoTheoUser",
  mounted() {
    var f = this.$refs.frmVatTuTrongKho
    f.openDialog()
  },
  
  data() {
    return {
      
    };
  },

  computed: {
  

    
  },
  methods: {
    
    openForm(){
      var f = this.$refs.frmVatTuTrongKho
      f.ShowDialog()
    }
  },
  created: async function() {       
  
  }
 
};
</script>