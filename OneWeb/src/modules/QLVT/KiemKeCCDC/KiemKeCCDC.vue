<template src='./KiemKeCCDC.html'></template>
<style scoped src='./KiemKeCCDC.scss'></style>
<script>

import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from '@/components/breadcrumb'
import { getterName,actionName,statePropertyName,mutationName} from '../store/kiemkeccdc'
import { Query, Predicate } from "@syncfusion/ej2-data";
import EventBus from '@/utils/eventBus'
import Vue from "vue";
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import moment from "moment";

Vue.use(DatePickerPlugin)

export default {

  components: {breadcrumb },
  name: "KiemKeCCDC",
  mounted() {
  },
  
  data() {
    return {
      DSThietBi : [],
      DSDonVi: [],
      DSNhanVien: [],
      DSAllNhanVien: [],
      DSTrangThai : [],
      DSThietBiChecked : [],
      params:{
        nhanVienId: -1,
        donViId: null,
        tinhTrangId :  null,
        dtpNgayKT: new Date()
      },
      Enabled:{
        cboNhanVien : false
      },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      filterDonVi: function(e) {
        var query = new Query();
        let predicate;  
       
        if (e.text !== ''){
          predicate  = new Predicate('maTenDonVi', 'contains', e.text.trim(), true);              
          query.where(predicate)
        }
        e.updateData(this.dataSource, query);
      },
      filterNhanVien: function(e) {
        var query = new Query();
        let predicate;  
       
        if (e.text !== ''){
          predicate  = new Predicate('tenNv', 'contains', e.text.trim(), true);              
          query.where(predicate)
        }
        e.updateData(this.dataSource, query);
      },
      TrangThaiTemplate: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>                         
                          <select class="form-control"  v-model="tinhTrangId" @change="onChangeTinhTrang">
                              <option v-for="TrangThai in parent.DSTrangThai" v-bind:value="TrangThai.TINHTRANGTBI_ID">
                                  {{TrangThai.TINHTRANG_TBI}}</option>
                          </select>
                      </div>`,                        
            data () { 
              return { 
                data: {},
                tinhTrangId: 0,
              }; 
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              }
            },
            created:async function () {
              this.tinhTrangId = this.data.TINHTRANGTBI_ID 
						},
            methods: {
              onChangeTinhTrang() {
                this.data.TINHTRANGTBI_ID = this.tinhTrangId 
                EventBus.$emit("onChangeTinhTrang",this.data);
              },
            },
          })
        }
      },
    };
  },

  computed: {
    ...mapState("kiemkeccdc", statePropertyName),
    ...mapGetters("kiemkeccdc", getterName),

    
  },
  methods: {
    ...mapActions("kiemkeccdc", actionName),
    ...mapMutations("kiemkeccdc", mutationName),

    f_MonthYearToString: function (value, format = "MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },
    async frmKiemKeCCDC_Load(){
      await this.NAP_COMBO()
    },
    async NAP_COMBO(){
      this.DSDonVi = await this.getDSDonViTheoNhanVien(this.$root.token.getNhanVienID())

      this.DSAllNhanVien = await this.getDSAllNhanVien()
      this.DSNhanVien = this.DSAllNhanVien

      this.DSTrangThai = await this.getDSTrangThai()
      if(this.DSTrangThai.length > 0){
        this.params.tinhTrangId = this.DSTrangThai[0].TINHTRANGTBI_ID
      }
    },
    async cboTuDonVi_EditValueChanged(){
      this.params.nhanVienId =  null
      if(this.params.donViId != null){
        this.Enabled.cboNhanVien = true
        var donViId = this.params.donViId
        var nhanVienId = this.$root.token.getNhanVienID()
        var DSNhanVienFilter =  this.DSAllNhanVien.filter(function (el) {
          return el.donViId == donViId || el.donViId == -1 || el.nhanVienId == nhanVienId;
        });
        console.log(DSNhanVienFilter , " =  DSNhanVienFilter")
        if(DSNhanVienFilter.length  == 2){
          var ToTruongId = await this.getToTruongId(this.params.donViId)
          if(ToTruongId != 0){
            var DSNhanVienFilter =  this.DSAllNhanVien.filter(function (el) {
              return el.donViId == donViId || el.donViId == -1 || el.nhanVienId == ToTruongId;
            });
          }
        }
        this.DSNhanVien = DSNhanVienFilter
      }else{
        this.Enabled.cboNhanVien = false
      }
    },
    async LAY_DS_SUDUNGCC(kieu){
      this.DSThietBi =  await this.getDSThietBi({
        donViId: this.params.donViId == null ? -1 : this.params.donViId,
        nhanVienId: this.params.nhanVienId == null ? 0 : this.params.nhanVienId,
        dtp: this.f_MonthYearToString(this.params.dtpNgayKT),
        loai: kieu
      })
      console.log(this.DSThietBi  ,   " = DSThietBi ")
    },
    async cboTuDonVi_ButtonClick(){
      this.loading(true)
      await this.LAY_DS_SUDUNGCC(2);
      this.loading(false)
    },
    async cboTuNhanVien_ButtonClick(){
      this.loading(true)
      await this.LAY_DS_SUDUNGCC(1);
      this.loading(false)
    },
    async cboTrangThai_EditValueChanged(){
      if(this.DSThietBiChecked.length == 0 || this.params.tinhTrangId == 0){
        return
      }
      for(const i in this.DSThietBi){
        if(this.DSThietBiChecked.includes(this.DSThietBi[i].THIETBI_ID)){
          this.DSThietBi[i].TINHTRANGTBI_ID = this.params.tinhTrangId
        }
      }
      this.$refs.gridThietBi.grid.refreshColumns()
    },
    onChangeTinhTrangHandler(value){
      this.DSThietBi.forEach(element => {
        if(element.THIETBI_ID == value.THIETBI_ID) {
          element.TINHTRANGTBI_ID = value.TINHTRANGTBI_ID
          element.TINHTRANG_TBI = value.TINHTRANG_TBI
        }
      });  
    },
    DSThietBiCheckbox: function (dataItem) {
      this.DSThietBiChecked = dataItem;
    },
    async tsbtnGhiLai_Click(){
      try{
        if(this.DSThietBiChecked.length == 0 ){
          this.$toast.error("Chưa chọn thiết bị để cập nhật!")
          return
        }
        this.loading(true)
        var ds = []
        var currentDate = new Date()
        for(const i in this.DSThietBi){
          if(this.DSThietBiChecked.includes(this.DSThietBi[i].THIETBI_ID)){
            ds.push({
              thietBiId: this.DSThietBi[i].THIETBI_ID,
              tinhTrangThietBiId: this.DSThietBi[i].TINHTRANGTBI_ID,
              ngayKt: this.f_DateTimeToString(this.params.dtpNgayKT),
              ngayCn: this.f_DateTimeToString(currentDate),
              nguoiCn: "",
              mayCn: "",
              ipCn: ""
            })
          }
        }
        await this.capNhatTinhTrangTbi(ds)
        await this.themDSThietBi(ds)
        this.$toast.success("Cập nhật thành công!")
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    }
  },
  created: async function() {       
    try{
      this.loading(true)
      EventBus.$on('onChangeTinhTrang', this.onChangeTinhTrangHandler);
      await this.frmKiemKeCCDC_Load()
      this.loading(false)
    }catch(e){
      this.$toast.error(e.data.message);
    }finally{
      this.loading(false);
    }
  },
  destroyed() {
    EventBus.$off("onChangeTinhTrang", this.onChangeTinhTrangHandler);
   
  },
 
};
</script>