<template src='./index.html'></template>
<style scoped src='./index.scss'></style>
<script>

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from '@/components/breadcrumb'

import Vue from "vue";

import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { Query, Predicate } from "@syncfusion/ej2-data";

Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb},
  name: "popupLoaiKhoMaKho",
  mounted() {
  },
  
  data() {
    return {
      
      tagForm: "",
      Tag: "",
      DSLoaiKho: [],

      xtraTabControl1: 0,
      Enabled:{
        tsbtnNew: true,
        tsbtnSave : true,
        tsbtnDel: true,
        tsbtnCancel: true,
        tabLoaiKho: true,
        tabMaKho: true,
      },
      tabLoaiKho:{
        txtMaLK: "",
        txtTenLK: "",
        txtGhiChuLK: "",
        DSLoaiKho: [],
        DSLKMD: [],
        DSMucDich: []
      },
      tabMaKho:{
        txtMaKhoKT: "",
        txtTenKhoKT: "",
        txtGhiChuLK: "",
        txtTaiKhoan: "",
        DSKhoKT: [],
        DSKTLKLeft: [],
        DSKTLK: [],
      },
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
    };
  },

  computed: {
    
    
    
  },
  methods: {
   
    async OpenDialog(){
      this.loading(true)
      await this.frmLoaiKho_MaKho_Load()
      this.$refs.popupLoaiKhoMaKho.show()
      this.loading(false)
    },
    async frmLoaiKho_MaKho_Load(){
      this.tagForm = this.Tag
      this.setButton(0)
    },
    setButton(kieu){
      if(kieu == 0){//Khoi tao luc load form
        this.Enabled.tsbtnSave =  false
        this.Enabled.tsbtnDel =  false

        this.tabLoaiKho.DSLoaiKho = []
        this.tabLoaiKho.DSMucDich = []
        this.tabLoaiKho.DSLKMD = []

        this.tabMaKho.DSKhoKT = []
        this.tabMaKho.DSKTLKLeft = []
        this.tabMaKho.DSKTLK = []

      }else if(kieu == 1){ //Bam them moi

        this.Enabled.tsbtnNew =  false
        this.Enabled.tsbtnDel =  false
        this.Enabled.tsbtnSave =  true
        this.Enabled.tsbtnCancel =  true

        if(xtraTabControl1 == 0){
          this.tabLoaiKho.txtMaLK = ""
          this.tabLoaiKho.txtTenLK = "",
          this.tabLoaiKho.txtGhiChuLK = ""
          this.Enabled.tabLoaiKho = true
          this.Enabled.tabMaKho = false
        }else{
          this.tabMaKho.txtMaKhoKT = ""
          this.tabMaKho.txtTenKhoKT = "",
          this.tabMaKho.txtGhiChuLK = ""
          this.tabMaKho.txtTaiKhoan = ""
          this.Enabled.tabLoaiKho = false
          this.Enabled.tabMaKho = true
        }
      }else if(kieu == 2){//Bam Huy
        this.Enabled.tsbtnNew =  true
        this.Enabled.tsbtnDel =  true
        this.Enabled.tsbtnSave =  false
        this.Enabled.tsbtnCancel =  false
        this.Enabled.tabLoaiKho = true
        this.Enabled.tabMaKho = true
      }else if(kieu == 3){
        this.Enabled.tsbtnNew =  true
        this.Enabled.tsbtnDel =  true
        this.Enabled.tsbtnSave =  true
        this.Enabled.tsbtnCancel =  false
        this.Enabled.tabLoaiKho = true
        this.Enabled.tabMaKho = true
      }
    },
    onClickTab(kieu){
      this.xtraTabControl1 = kieu
    }
  },
  created: async function() {       
    
  }
 
};
</script>