<template src='./ChonVatTuCTCT.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/chonvattuctct";
import moment from "moment";
export default {
  components: { breadcrumb },
  name: "ChonVatTuCTCT",

  data() {
    return {
      vchungtu_id : 0,
      DSVatTu: [],
      DS_DaGan: [],
      DS_ChuaGan : [],
      DSVatTuSelected : {},
      DSDaGanChecked: [],
      DSChuaGanChecked: [],
      checkAll: false,
      Visible:{
        colMaKhoTD: false,
        colLoaiKho:false,
      },
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      editSettings:{ allowEditing: true, mode:'Batch'},
      columnsCheckbox: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.DSChuaGanChecked"
                                    :value="data.CTCT_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: { }
              }
            },
            computed: {
              parent() {              
                return this.$parent.$parent.$parent.$parent
              },
            }
          }
        }
      },
      columnsCheckboxAll: function() {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAll" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return { }
            },
            computed: {
              parent() {            
                return this.$parent.$parent.$parent.$parent
              }
            }
          }
        }
      },
    };
  },
  
  computed: {
    ...mapState("chonvattuctct", statePropertyName),
    ...mapGetters("chonvattuctct", getterName),
   
  },
  watch: {
    "DSChuaGanChecked": function(value){    
      this.DSChuaGanChecked = value   
      this.DS_ChuaGan.forEach(e=>{
        if(value.includes(e.CTCT_ID)){
          e.SL_XUAT = e.SOLUONG
        }else{
          e.SL_XUAT = 0
        }
      })
      this.$refs.gridDS_ChuaGan.grid.refreshColumns()
    },
    "checkAll": function (value){
      this.DSChuaGanChecked = []
      if(value){
        this.DS_ChuaGan.forEach(e=>{
          e.SL_XUAT = e.SOLUONG
          this.DSChuaGanChecked.push(e.CTCT_ID)
        })
      }else{
        this.DS_ChuaGan.forEach(e=>{
          e.SL_XUAT = 0
        })
      }
      this.$refs.gridDS_ChuaGan.grid.refreshColumns()
    },
  },
  mounted: async function () {
     
  },
  methods: {
    ...mapActions("chonvattuctct", actionName),
    ...mapMutations("chonvattuctct", mutationName),

    f_MonthYearToString: function (value, format = "MM/YYYY") {
      return moment(value).format(format);
    },
    async openChonVatTuCTCT(){
      this.loading(true)
      await this.TIMKIEM()
      this.$refs.popupChonVatTuCTCT.show();
      this.loading(false)
    },
    async closeChonVatTuCTCT(){ 
      this.$refs.popupChonVatTuCTCT.hide();
    },
    async TIMKIEM(){
      this.DSVatTu = await this.getDSVatTuChungTu(this.vchungtu_id)
      if(this.DSVatTu.length > 0){
        this.Visible.colMaKhoTD = 'MAKHOTD' in this.DSVatTu[0] && 'LOAIKHO' in this.DSVatTu[0]
        this.Visible.colLoaiKho = 'MAKHOTD' in this.DSVatTu[0] && 'LOAIKHO' in this.DSVatTu[0]
      }
      console.log(this.DSVatTu  , " = DSVatTu")
    },
    async gridVatTuFocus(row){
      this.CLEAR()
      if(row){
        console.log("gridVatTuFocus")
        this.loading(true)
        this.DSVatTuSelected = row
        await this.NAP_DS_CHUAGAN()
        await this.NAP_DS_DAGAN()
        this.loading(false)
      }
    },
    async NAP_DS_CHUAGAN(){
      if(this.DSVatTuSelected == {}){
        return
      }
      this.DS_ChuaGan = await this.getDSChiTietVTCT({
        kieu: 1,
        chungTuId: this.DSVatTuSelected.CTCT_ID
      })
      console.log(this.DS_ChuaGan , " = DS_ChuaGan")
    },
    async NAP_DS_DAGAN(){
      if(this.DSVatTuSelected == {}){
        return
      }
      this.DS_DaGan = await this.getDSChiTietVTCT({
        kieu: 2,
        chungTuId: this.DSVatTuSelected.CTCT_ID
      })
      console.log(this.DS_DaGan , " = DS_DaGan")
    },
    async btnGan_Click(){
      if(this.DS_ChuaGan.length == 0){
        this.$toast.error("Không có danh sách để thao tác")
        return
      }
      if(this.DSChuaGanChecked.length == 0){
        this.$toast.error("Bạn phải chọn danh sách chi tiết để gán")
        return
      }
      var DSChuaGanChecked = this.DSChuaGanChecked
      var DSItemChuaGanChecked =  this.DS_ChuaGan.filter(function (el) {
        return DSChuaGanChecked.includes(el.CTCT_ID);
      });
      this.loading(true)
      var ds = []
      DSItemChuaGanChecked.forEach(e=>{
        ds.push({
          CTCT_ID : e.CTCT_ID,
          SL_XUAT: e.SL_XUAT
        })
      })
      var ketqua = await this.ganGoDSChiTietVT({
        ctctId: this.DSVatTuSelected.CTCT_ID,
        ds: ds,
        kieu: 1
      })
      if(ketqua != "1"){
        this.$toast.error(ketqua)
      }else{
        this.$toast.success("Đã thực hiện gán thành công");
        this.CLEAR();
        this.NAP_DS_CHUAGAN();
        this.NAP_DS_DAGAN();
      }
      this.loading(false)
    },
    async btnGo_Click(){
      if(this.DS_DaGan.length == 0){
        this.$toast.error("Không có danh sách để thao tác")
        return
      }
      if(this.DSDaGanChecked.length == 0){
        this.$toast.error("Bạn phải chọn danh sách chi tiết để gỡ")
        return
      }
      this.loading(true)
      var ds = []
      this.DSDaGanChecked.forEach(e=>{
        ds.push({
          CTCT_ID : e.CTCT_ID,
          SL_XUAT: e.SL_XUAT
        })
      })
      var ketqua = await this.ganGoDSChiTietVT({
        ctctId: this.DSVatTuSelected.CTCT_ID,
        ds: ds,
        kieu: 2
      })
      if(ketqua != "1"){
        this.$toast.error(ketqua)
      }else{
        this.$toast.success("Đã thực hiện gỡ thành công");
        this.CLEAR();
        this.NAP_DS_CHUAGAN();
        this.NAP_DS_DAGAN();
      }
      this.loading(false)
    },
   
    gridDS_DaGanChecked: function (dataItem) {
      this.DSDaGanChecked = dataItem
    },

    CLEAR(){
      this.DS_DaGan = []
      this.DS_ChuaGan = []
    },
    editGridChuaGan(arg){
      let gridDS_ChuaGan = this.$refs.gridDS_ChuaGan
      let changes = gridDS_ChuaGan.getBatchChanges()
  
      if (changes.changedRecords.length > 0) { 
        if(arg.columnName == "SL_XUAT" ){
          if(changes.changedRecords[0].SL_XUAT <= 0 ){
            changes.changedRecords[0].SL_XUAT = 0
            this.DSChuaGanChecked = this.removeElementInArray(this.DSChuaGanChecked , changes.changedRecords[0].CTCT_ID)
          }else{   
            this.DSChuaGanChecked.push(changes.changedRecords[0].CTCT_ID)
          }
        }

        gridDS_ChuaGan.batchUpdate(changes)
        this.$refs.gridDS_ChuaGan.grid.refreshColumns()
      }
    },
    removeElementInArray(array,element){
      const index = array.indexOf(element);
      if (index > -1) {
        array.splice(index, 1);
      }
      return array
    },
    async tsbtnLayTT_Click(){
      this.loading(true)
      await this.TIMKIEM()
      this.loading(false)
    }
  },
 

  created: async function () {
   
  },
  
};
</script>
