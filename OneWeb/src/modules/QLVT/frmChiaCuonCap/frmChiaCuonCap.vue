<template src='./frmChiaCuonCap.html'></template>
<style scoped src='./frmChiaCuonCap.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/frmchiacuoncap";
import moment from "moment";
import Vue from "vue";
import {
  GridPlugin,
  Group,
  Page,
  Filter,
  Sort,
  Resize,
} from "@syncfusion/ej2-vue-grids";
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'

Vue.use(DatePickerPlugin)

Vue.use(GridPlugin);

export default {
  components: { breadcrumb },
  name: "NghiemThuDonHang",

  data() {
    return {
      
      vkieu : 0,
      dt_vattu : [],
      dt_serial: [],
      dt_cuoncap: [],
      chia:[],
      XacNhan: false,
      is_cc:false,
      xml_ds_vt_dk: "",
      DSVTDonHang: [],
      DSVTDangKy: [],
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      editSettings:{ allowEditing: true, mode:'Batch'},
    };
  },

  computed: {
    ...mapState("frmchiacuoncap", statePropertyName),
    ...mapGetters("frmchiacuoncap", getterName),
  },

  methods: {
    ...mapActions("frmchiacuoncap", actionName),
    ...mapMutations("frmchiacuoncap", mutationName),

    async onClickXacNhan(){
     
    },
    async frmChiaCuonCap_Load(){
      if(this.vkieu == 0){
        var serial = this.dt_vattu.filter(
          function (el) {
            return (
              el.LOAITBI_ID == "2"
            );
          }
        );
        if(serial.length > 0){
          this.dt_serial = serial
        }
        this.chia = this.dt_cuoncap

        this.dt_cuoncap.forEach(e=>{
          var sl = e.CHIA_SL
          if(sl > 0){
            for (let j = 0; j < sl; j++) {
              this.chia.push({
                CHON: "s0",
                PHANVUNG_ID: e.PHANVUNG_ID,
                CTDH_ID: e.CTDH_ID,
                DONHANG_ID: e.DONHANG_ID,
                VATTU_ID: e.VATTU_ID,
                SOLUONG: "",
                DONGIA: e.DONGIA,
                TIEN: "",
                SL_NT: "",
                SL_HONG: "",
                SL_CHUAN: "",
                VAT: "",
                HDVT_ID: e.HDVT_ID,
                HAN_BH: e.HAN_BH,
                MA_VT: e.MA_VT,
                TEN_VT: e.TEN_VT,
                DVITINH_ID: e.DVITINH_ID,
                DVI_TINH: e.DVI_TINH,
                SOTHANG_PB: e.SOTHANG_PB,
                LOAI_TBI: e.LOAI_TBI,
                LOAITBI_ID: e.LOAITBI_ID,
                SL_XUAT: 0
              })
            }
          }
        })
        this.DSVTDonHang = this.chia
      }else if(this.vkieu == 1){
        if(this.xml_ds_vt_dk != "" && this.xml_ds_vt_dk != null){
          this.DSVTDangKy = await this.getDSVatTuDangKy({ds: this.xml_ds_vt_dk })
        }
      }
    },
    async Open(){
      this.loading(true)
      await this.frmChiaCuonCap_Load()
      this.$refs.popupChiaCuonCap.show();
      this.loading(false)
    },
    async Close() {
      this.XacNhan = false
      this.$refs.popupChiaCuonCap.hide();
    },

    async editGridVatTuDonHang(arg) {

      let gridVatTuDonHang = this.$refs.gridVatTuDonHang
      let changes = gridVatTuDonHang.getBatchChanges()   
      if (changes.changedRecords.length > 0) {   
        if(arg.columnName == "SL_XUAT" ){
          if(changes.changedRecords[0].SL_XUAT < 0 ){
            changes.changedRecords[0].SL_XUAT = 0
          }
        }
      }
      gridVatTuDonHang.batchUpdate(changes)
  
    },

    async editGridVatTuDangKy(arg) {

      let gridVatTuDangKy = this.$refs.gridVatTuDangKy
      let changes = gridVatTuDangKy.getBatchChanges()
      if (changes.changedRecords.length > 0) {     
        if(arg.columnName == "SOLUONG_GROUP" ){
          if(changes.changedRecords[0].SOLUONG_GROUP < 0 ){
            changes.changedRecords[0].SOLUONG_GROUP = 0
          }
        }
      }
      gridVatTuDangKy.batchUpdate(changes)
  
    },

    async onClickXacNhan(){
      if(this.vkieu == 0){
        if(this.dt_serial.length > 0 && this.dt_serial != null ){
          for (const i in this.dt_cuoncap) {
            var sum = 0
            for(const j in this.chia){
              if(this.dt_cuoncap[i].MA_VT == this.chia[j].MA_VT){
                sum += this.chia[j].SL_XUAT
              }
            }
            if(sum != this.dt_cuoncap[i].SL_XUAT){              
              this.$toast.error("Mã VT: " + this.dt_cuoncap[i].MA_VT + " tổng số cuộn cáp [" + sum + "] không bằng [" + this.dt_cuoncap[i].SL_XUAT + "]");
              return
            }
          }

          this.dt_serial.forEach(e=>{
            this.chia.push(e)
          })
         
        }
        if(this.chia.length > 0){
          this.XacNhan = true
          this.is_cc = true
        }
      }else if(this.vkieu == 1){
        if(this.DSVTDangKy.length > 0 && this.DSVTDangKy != null){
          var VTKiemTra = this.DSVTDangKy.filter(
            function (el) {
              return (
                el.SOLUONG_GROUP == 0
              );
            }
          );
          if(VTKiemTra.length > 0){
            this.$toast.error("Bạn chưa chọn số lượng cho kho và vật tư không thể xác nhận");
            return
          }
          
          var ds_xacnhan = []
          this.DSVTDangKy.forEach(e=>{
            ds_xacnhan.push({
              VTDK_ID: e.VTDK_ID,
              KHO_ID: e.KHO_ID,
              VATTU_ID: e.VATTU_ID,
              SOLUONG_GROUP: e.SOLUONG_GROUP,             
              SL_KD: e.SL_KD
            })
          })

          var KTSoLuong = await this.kiemTraSoLuongLoHang({
            ds: JSON.stringify(ds_xacnhan)
          })
          if(KTSoLuong != "1"){
            this.$toast.error("Có lỗi khi kiểm tra vật tư :" + KTSoLuong);
            return
          }
          var xdoc_vtdk = []
          ds_xacnhan.forEach(e=>{
            xdoc_vtdk.push({
              vtid: e.VTDK_ID,
              soluong: 0,
              kho_id: e.KHO_ID,
              sl_duyet: e.SOLUONG_GROUP
            })
          })

          this.xml_ds_vt_dk = JSON.stringify(xdoc_vtdk)
          this.XacNhan = true
        }
      }

      this.$refs.popupChiaCuonCap.hide();
      await this.$parent.closeChiaCuonCap()
    }
   
  },
 

  created: async function () {
    
  },
  provide: {
    grid: [Group, Page, Filter, Sort, Resize],
  },
};
</script>
