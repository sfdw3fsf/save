<template src="./index.template.html"></template>
<script>
////import { Tooltip } from "@syncfusion/ej2-popups";
// import store from "../../store/index";
import Vue from "vue";
import { GridPlugin } from "@syncfusion/ej2-vue-grids";
import * as moment from "moment";
// import DataGridCustome from "../DataGrid/index.vue";
import popupKenh from './popupKenh.vue';
import store from "../../store/index";
import { mapState } from 'vuex'

Vue.use(GridPlugin);
Vue.prototype.$eventHub = new Vue();
export default {
  components: {
    popupKenh
  },
  watch: {

  },
  created() {
    console.log('create ', store.state);
    this.initData();
  },
  data: function () {
    return {
      ls_kenh_id: [],
      options: {
        kenh: [
          
        ],
        kenh_backup: []
      },
      config: {
        kenh: [
          {
            headerText: "",
            fieldName: "NAME",
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
    JS_KENH(newval, oldval){
      this.options.kenh = newval;
    }
  },
  computed: {
    ...mapState['JS_KENH', 'JS_KENH_BK'],
    JS_KENH(){
      return store.state.JS_KENH;
    }
  },
  methods: {
    btnLoadKenhTV_Click(){
      this.options.kenh = store.state.JS_KENH_BK;
    },
    async del(row){
      let idx = row.id;
      let tem = [...this.options.kenh];
      this.options.kenh = tem.filter(i => i.id != idx);
      await store.commit('SET_JS_KENH', this.options.kenh);
    },
    async initData() {
      console.log(store);
      this.options.kenh = store.state.JS_KENH;
    },
    async Kenhchange(e){
      try {
        console.log(e, this.options.kenh);
        let newls = [...this.options.kenh , ...e];
        this.options.kenh = [...newls] 
        await store.commit('SET_JS_KENH', this.options.kenh);
      } catch (error) {
        console.log(error);
      }
    },
    selectingEventDVAD(){

    },
    btnInsKenhTV_Click(){
      this.ls_kenh_id = store.state.JS_KENH.map(i => i.id);
      this.$refs.popupKenh.showModal();
    }
  },
};
</script>