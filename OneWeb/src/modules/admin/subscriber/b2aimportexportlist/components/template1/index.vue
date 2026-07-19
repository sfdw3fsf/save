<template src="./index.template.html"></template>
<script>
////import { Tooltip } from "@syncfusion/ej2-popups";
// import store from "../../store/index";
import Vue from "vue";
import { GridPlugin } from "@syncfusion/ej2-vue-grids";
import * as moment from "moment";
// import DataGridCustome from "../DataGrid/index.vue";
import popupDVVT from './popupDVVT.vue';
import popupTB from './popupTB.vue';
import store from '../../store/index';
import { mapState } from 'vuex'

Vue.use(GridPlugin);
Vue.prototype.$eventHub = new Vue();
export default {
  components: {
    popupDVVT,
    popupTB
  },
  watch: {

  },
  created() {
    console.log('create', store.state);
    this.initData();
  },
  data: function () {
    return {
      vdichvu_id: 0,
      ds_tttb_id:[],
      options: {
        LHTB: [
          
        ]
      },
      config: {
        LHTB: [
          {
            headerText: "",
            allowSorting: true,
            fieldName: "NAME",
            // width: "400",
          },
          // {
          //   textAlign: "center",
          //   headerText: "",
          //   allowSorting: true,
          //   fieldName: "",
          //   template: function () {
          //     return {
          //       template: Vue.component("columnTemplate", {
          //         template: `
          //               <row><a class="btn btn-huylydo lh14" @click="del"
          //                 style="background-color: #e03636 !important; padding: 5px 15px; color: white">
          //                 <span class="fa fa-close"></span></a>
          //               </row>             
          //               `,
          //         data() {
          //           return {
          //             data: {},
          //           };
          //         },
          //         computed: {
          //           parent() {
          //             return this.$parent.$parent.$parent;
          //           },
          //         },
          //         methods: {
          //           async del() {
          //             // console.log(e);
          //             this.$parent.$parent.$parent.del(this.data);
          //             // this.$eventHub.$emit("detail", this.data);
          //           },
          //         },
          //       }),
          //     };
          //   },
          //   width: "50",
          // },
        ],
        
      },
    };
  },
  watch: {
    JS_LHTB(newval, oldval){
      this.options.LHTB = newval;
    }
  },
  computed: {
    ...mapState['JS_LHTB', 'JS_LHTB_BK'],
    JS_LHTB(){
      return store.state.JS_LHTB;
    }
  },
  methods: {
    async btnLoadLHTB_Click(){
      this.options.donvi_apdung = store.state.JS_LHTB_BK;
    },
    async del(row){
      let idx = row.id;
      let tem = [...this.options.LHTB];
      this.options.LHTB = tem.filter(i => i.id != idx);
      await store.commit('SET_JS_LHTB', this.options.LHTB);
    },
    async initData() {
      this.options.LHTB = store.state.JS_LHTB;
    },
    getItemsDVVT(itemDVVT){
      console.log('tem1',itemDVVT);
      this.vdichvu_id = itemDVVT.id;
      // ds id thue bao da ton tai
      this.ds_tttb_id = this.options.LHTB.map(i => i.id);
      this.$refs.popupTraLHTB.showModal();
    },
    async getItemsTB(e){
      console.log('tem1',e);
      let newls = [...this.options.LHTB , ...e];
      this.options.LHTB = newls;
      await store.commit('SET_JS_LHTB', this.options.LHTB);
    },
    selectingEventLHTB(e){

    },
    btnInsLHTB_Click(){
      this.$refs.popupDVVT.showModal();
    }
  },
};
</script>