<template src='./SPDA.html'></template>

<script>

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from '@/components/breadcrumb'
// import { getterName,actionName,statePropertyName,mutationName} from '../store/loaikhomakho'
import Vue from "vue";

import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { Query, Predicate } from "@syncfusion/ej2-data";

Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb},
  name: "popupSPDA",
  mounted() {
  },
  
  data() {
    return {
      DSMa_PDA: [],
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
    };
  },

  computed: {
    // ...mapState("loaikhomakho", statePropertyName),
    // ...mapGetters("loaikhomakho", getterName),
    
    
  },
  methods: {
    // ...mapActions("loaikhomakho", actionName),
    // ...mapMutations("loaikhomakho", mutationName),

    async OpenDialog(){
      try {
        this.loading(true)
        this.$refs.popupSPDA.show()
        
      } catch (error) {
        this.$toast.error("Xảy ra lỗi khi load form, vui lòng thử lại!");
      }finally{
        this.loading(false)
      }
      
    },
    
  },
  created: async function() {       
    
  }
 
};
</script>