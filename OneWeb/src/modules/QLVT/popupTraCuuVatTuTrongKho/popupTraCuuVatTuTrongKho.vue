<template src='./popupTraCuuVatTuTrongKho.html'></template>

<script>

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from '@/components/breadcrumb'
import Vue from "vue";

import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { Query, Predicate } from "@syncfusion/ej2-data";
import TraCuuVatTuTrongKho from '../TraCuuVatTuTrongKho'

Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb, TraCuuVatTuTrongKho},
  name: "popupTraCuuVatTuTrongKho",
  mounted() {
    var f = this.$refs.frmTraCuuVatTuTrongKho
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
      var f = this.$refs.frmTraCuuVatTuTrongKho
      f.openDialog()
    }
  },
  created: async function() {       
  
  }
 
};
</script>