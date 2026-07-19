<template src='./ChonVatTuCCDC.html'></template>
<style scoped src='./ChonVatTuCCDC.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/chonvattuccdc";
import moment from "moment";
export default {
  components: { breadcrumb },
  name: "ChonVatTuCCDC",

  data() {
    return {
     
      xacnhan : false,
      tenNhanVien: "",
      donvi_id: 0,
      nhanvien_id: 0,
      kieu:0,
      loai:0,
      ma_md:"",
      tongtien: 0,
      pb: "2",
      phanBo: "2",
      dt: [],
      luaChonId : 0,
      disabledRadio: true,
      CCDCID_Checked : [],
      dtp: new Date(),
      editSettings:{ allowEditing: true, mode:'Batch'},
      dataSource:{
        DSLuaChon:[
          {
            ID:0,
            TenLuaChon: "Đơn vị"
          },
          {
            ID:1,
            TenLuaChon: "Nhân viên"
          }
        ],
        DSNhanVien:[],
        DSCCDC:[]
      },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
     
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      
    };
  },
  
  computed: {
    ...mapState("chonvattuccdc", statePropertyName),
    ...mapGetters("chonvattuccdc", getterName),
   
  },
  watch: {
   
  },
  mounted: async function () {
     
  },
  methods: {
    ...mapActions("chonvattuccdc", actionName),
    ...mapMutations("chonvattuccdc", mutationName),

    f_MonthYearToString: function (value, format = "MM/YYYY") {
      return moment(value).format(format);
    },
    async openChonVatTuCCDC(){
      this.loading(true)
      await this.loadDataCCDC()
      this.$refs.popupChonVatTuCCDC.show();
      this.loading(false)
    },
    async closeChonVatTuCCDC(){ 
      this.$refs.popupChonVatTuCCDC.hide();
    },
    async loadDataCCDC(){
      this.luaChonId = this.kieu
      this.disabledRadio = this.ma_md != "CCDC.S"
      // this.dataSource.DSNhanVien = await this.getDSNhanVien()
      var DSCCDC = await this.getDSCCDCNVDV({
        donViId: this.donvi_id,
        nhanVienId: this.nhanvien_id,
        dtp: this.f_MonthYearToString(this.dtp),
        loai: this.loai
      })
      DSCCDC.forEach(e => {
        e.SL_TON_THAT =  e.SL_TON_THAT == "" || e.SL_TON_THAT == null ?  0 : parseInt(e.SL_TON_THAT)
        e.SL_XUAT =  e.SL_XUAT == "" || e.SL_XUAT == null ?  0 : parseInt(e.SL_XUAT)
        e.TIEN_SC =  e.TIEN_SC == "" || e.TIEN_SC == null ?  0 : parseInt(e.TIEN_SC)
        e.TIEN_PB =  e.TIEN_PB == "" || e.TIEN_PB == null ?  0 : parseInt(e.TIEN_PB)
        e.TIEN_CL =  e.TIEN_CL == "" || e.TIEN_CL == null ?  0 : parseInt(e.TIEN_CL)
        e.DONGIA =  e.DONGIA == "" || e.DONGIA == null ?  0 : parseInt(e.DONGIA)
      });
      this.dataSource.DSCCDC = DSCCDC
    },

    DSCCDCCheckox: function (dataItem) {
      this.CCDCID_Checked = dataItem;
    },

    async editGridCCDC(arg) {      
      let gridCCDC = this.$refs.gridCCDC
      let changes = gridCCDC.getBatchChanges() 
      if(changes.changedRecords.length == 0){
        return
      }
      if(arg.columnName == "SL_XUAT" ){
        console.log(changes , "= changes")
        if(changes.changedRecords[0].SL_XUAT > changes.changedRecords[0].SL_TON_THAT ){
          changes.changedRecords[0].SL_XUAT = changes.changedRecords[0].SL_TON_THAT      
          this.$toast.error("Không được xuất quá lượng tồn của vật tư !")
        }else if(changes.changedRecords[0].SL_XUAT <= 0){
          changes.changedRecords[0].SL_XUAT = changes.changedRecords[0].SL_TON_THAT  
          this.$toast.error("Không thể xuất số lượng bằng 0!")
        }
      }else if(arg.columnName == "TIEN_SC"){
        if(changes.changedRecords[0].TIEN_SC <= 0){
          changes.changedRecords[0].TIEN_SC = 0
          this.$toast.error("Tiền sữa chữa không được âm!")
        }
      }
      gridCCDC.batchUpdate(changes)
    },
    async onClickXacNhan(){
      if(this.CCDCID_Checked.length == 0){
        this.$toast.error("Chưa chọn vật tư nào!")
        this.xacnhan = false
        return
      }
      var CCDCID_Checked = this.CCDCID_Checked
      var DSCCDC_Checked = this.dataSource.DSCCDC.filter(function (el) {
        return  CCDCID_Checked.includes(el.CTCC_ID)                              
      })   

      if(this.ma_md == "CCDC.S"){
        var CheckTIEN_SC = DSCCDC_Checked.filter(function (el) {
          return  el.TIEN_SC <= 0                            
        })  
        if(CheckTIEN_SC.length > 0){
          this.$toast.error("Vật tư bạn chọn chưa điền tiền sửa chữa!")
          this.xacnhan = false
          return
        }
        var tongtien = 0
        DSCCDC_Checked.forEach(e=>{
          tongtien += e.TIEN_SC
        })
        if(tongtien != this.tongtien){
          this.$toast.error("Tiền sửa chữa không đúng. Bạn hãy kiểm tra lại!")
          this.xacnhan = false
          return
        }
      }

      this.xacnhan = true
      if(this.ma_md == "CCDC.S"){
        this.pb = this.phanBo
      }
      DSCCDC_Checked.forEach(e=>{
        if(CCDCID_Checked.includes(e.CTCC_ID)){
          e.SOLUONG = e.SL_XUAT
        }
      })
      this.dt = DSCCDC_Checked

      // gọi làm hàm cha
      if(this.loai == 2){
        await this.$parent.closeChonVatTuCCDCTuDonVi()
      }else{
        await this.$parent.closeChonVatTuCCDCTuNhanVien()
      }    
      this.closeChonVatTuCCDC()
    }
  },
 
 

  created: async function () {
   
  },
  
};
</script>
