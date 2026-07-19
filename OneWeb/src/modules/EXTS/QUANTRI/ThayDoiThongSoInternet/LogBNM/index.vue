<template src="./index.html"></template>

<script>
import Vue from "vue";
import { GridPlugin, Group, Page, Sort, Resize } from "@syncfusion/ej2-vue-grids";
Vue.use(GridPlugin);
export default {
  name: "LogBNM",
  props:{
    id:String,
    ma_tb:String,
  },
  provide: {
    grid: [Group, Page, Sort, Resize]
  },
  data:function (){
    return{
      groupOptions:{columns:[], },
      dtHistory:[],
    }
  },
  methods:{
    async onShownModal() {
      try {
        this.loading(true)
        let rs = await this.$root.context.get("web-quantri/thaydoi-thongso-internet/get-log-action-bnm",{ma_tb:this.ma_tb})
        // this.dtHistory = [
        //   {
        //     ma_tb: "213123",
        //     may_cn: "213123",
        //     value: "213123",
        //     action_type: "213123",
        //     ngay_cn: "213123",
        //     nguoi_cn: "213123",
        //   }
        // ]
        this.dtHistory = rs.data
      }catch (e) {

      }finally {
        this.loading(false)
      }
    },
  }
}
</script>

<style scoped>

</style>
