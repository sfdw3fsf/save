<template src="./index.template.html"></template>
<script>
////import { Tooltip } from "@syncfusion/ej2-popups";
// import store from "../../store/index";
import Vue from "vue";
import { GridPlugin } from "@syncfusion/ej2-vue-grids";
import * as moment from "moment";
// import DataGridCustome from "../DataGrid/index.vue";
import popupDV from './popupDV.vue';
import store from '../../store/index.js';
import { mapState } from 'vuex'

Vue.use(GridPlugin);
Vue.prototype.$eventHub = new Vue();
export default {
  components: {
    popupDV
    // DataGridCustome,
  },
  watch: {
  },
  created() {
    this.initData();
    // this.initData();
  },
  data: function () {
    return {
      dsIdChon: [],
      rowIndex: -1,
      isDel_event: false,
      
      options: {
        donvi_apdung: store.state.JS_TINH
      },
      config: {
        donvi_apdung: [
          {
            headerText: "",
            fieldName: "name",
            // width: "400",
          },
          {
            textAlign: "center",
            headerText: "",
            fieldName: "",
            template: function () {
              return {
                template: Vue.component("columnTemplate", {
                  template: `
                        <row><a class="btn btn-huylydo lh14" @click="del"
                          style="background-color: #e03636 !important; padding: 5px 15px; color: white">
                          <span class="fa fa-close"></span></a>
                        </row>             
                        `,
                  data() {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                  },
                  methods: {
                    async del() {
                      // console.log(e);
                      this.$parent.$parent.$parent.del(this.data);
                      // this.$eventHub.$emit("detail", this.data);
                    },
                  },
                }),
              };
            },
            width: "50",
          },
        ],
        
      },
    };
  },
  watch: {
    JS_TINH(newval, oldval){
      this.options.donvi_apdung = newval;
    }
  },
  computed: {
    ...mapState['JS_TINH', 'JS_TINH_BK'],
    JS_TINH(){
      return store.state.JS_TINH;
    }
  },
  methods: {
    async btnLoadDV_Click(){
      console.log(store.state.JS_TINH_BK);
      this.options.donvi_apdung = store.state.JS_TINH_BK
    },
    async del(row){
      let idx = row.id;
      let tem = [...this.options.donvi_apdung];
      this.options.donvi_apdung = tem.filter(i => i.id != idx);
      await store.commit('SET_JS_TINH', this.options.donvi_apdung);
    },
    initData() {
      this.options.donvi_apdung = store.state.JS_TINH;
      // console.log('init tem1: ',store.state, this.JS_TINH);
    },
    async onDVChange(e){
      // console.log('tem1: ', e);
      let newls = [...this.options.donvi_apdung , ...e];
      // this.options.donvi_apdung = [...newls];
      await store.commit('SET_JS_TINH', newls);
    },
    selectingEventDVAD(){
    },
    async btnInsDV_Click() {
      try {
        // set don vi da ton tai ben ngoai truoc khi mo popup
        this.dsIdChon = this.options.donvi_apdung.map(i => i.id); 
        console.log(this.dsIdChon);
        this.$refs.popupDV.showModal();
      } catch (ex) {
        this.$toast.warning("Có lỗi xảy ra: " + ex); //Message_Box.ShowWarning("Gán danh sách có lỗi " + ex);
      }
    },
  },
};
</script>