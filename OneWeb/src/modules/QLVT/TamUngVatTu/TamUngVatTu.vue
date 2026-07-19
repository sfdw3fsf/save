<template src='./TamUngVatTu.html'></template>
<style scoped src='./TamUngVatTu.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import TraCuuVatTuTrongKho from "../TraCuuVatTuTrongKho/TraCuuVatTuTrongKho.vue";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/tamungvattu";
import moment from "moment";
import Vue from "vue";
import ModalXuatDuLieu from '../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu'
import { Freeze, Edit, Group, Page, Filter, Sort, Resize,GridPlugin, Aggregate, Toolbar} from "@syncfusion/ej2-vue-grids";
import { L10n } from '@syncfusion/ej2-base'
import {LOAI_KHO} from "@/const/enums"
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { Query,Predicate } from '@syncfusion/ej2-data';

Vue.use(GridPlugin,DatePickerPlugin);
L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})
let selectedRecord = {};
export default {
  components: { ModalXuatDuLieu,TraCuuVatTuTrongKho,breadcrumb },
  name: "TamUngVatTu",

  data() {
    return {
      header: {
        title: "TẠM ỨNG VẬT TƯ CHO NHÂN VIÊN",

        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tạm ứng vật tư cho nhân viên",
            link: { name: "Ui.buttons" },
          },   
        ],
      },

      params: {
        ngayGiao: new Date(),
        nhanVienGiaoId: "",
        nhanVienNhanId: "",
        khoGiaoId: "",
        khoNhanId: "",
        serialKhoGiao: "",
        serialKhoNhan:""
      },
      rowsCheckedVTGiao:[],
      rowsCheckedVTNhan:[],
      Enabled:{
        nhanVienGiao: true,
        nhanVienNhan: true
      },
      thongTinNguoiDung: {        
        donViId:  this.$root.token.getDonViID(),
        nhanVienId : this.$root.token.getNhanVienID(),
        phanVungId: this.$root.token.getPhanVungID(),
        nguoiCN: this.$root.token.getUserName(), //this.$auth.MaNguoiDung
        mayCN: "DESKTOP-IDG67LD", //this.$auth.MAYCN
        ipCN: "10.59.90.128", //this.$auth.MAYCN
      },
      dataResponse:{
        DSNhanVien: [],
        DSKhoGiao: [],
        DSKhoNhan: [],
        DSVatTuGiao: [],
        DSVatTuNhan: [],
        DSNhanVienKhoGiao : [],
        DSNhanVienKhoNhan : [],
      },
      
      dsThuocTinh: [],
      showNVGiao: 'none',
      showNVNhan: 'none',
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      format:{ format:'N3' , useGrouping: true},
      cRowSelected: function() {
        return {
          template : Vue.component('columntemplate', {
            template : '<span class="fa"></span>'
          })  // end of template
        }
      },
      

      checkAllVTGiao: false,
      columnsCheckboxAllVTGiao: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllVTGiao" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },            
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      columnsCheckboxVTGiao: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.rowsCheckedVTGiao"
                                    :value="data.TUVT_ID"/>
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
                return this.$parent.$parent.$parent
              },
            }
          }
        }
      },

      checkAllVTNhan: false,
      columnsCheckboxAllVTNhan: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllVTNhan" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },            
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      columnsCheckboxVTNhan: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.rowsCheckedVTNhan"
                                    :value="data.TUVT_ID"/>
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
                return this.$parent.$parent.$parent
              },
            }
          }
        }
      },
      filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true, },
      templateOptions : {
        create: function (args) {
          return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
        },
        write: function (args) {
        }
      },
      editSettings: { allowEditing: true, mode:'Batch'},
      selectionSettings: {  type: 'Single', checkboxOnly: true },

      onFiltering: function(e) {
        var query = new Query();
        let predicate;  
        
        //frame the query based on search string with filter type.
        if (e.text !== ''){
          predicate  = new Predicate('TEN_K_NEW', 'contains', e.text.trim(), true);            
          query.where(predicate)         
        }

        //pass the filter data source, filter query to updateData method.
        e.updateData(this.dataSource, query);
      },
  
    };
  },

  computed: {
    ...mapState("tamungvattu", statePropertyName),
    ...mapGetters("tamungvattu", getterName),
  },

  methods: {
    ...mapActions("tamungvattu", actionName),
    ...mapMutations("tamungvattu", mutationName),
    
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    async loadDataNhanVien(){
      this.dataResponse.DSNhanVien = await this.getDSNhanVienGiaoNhan({
        donViId : this.thongTinNguoiDung.donViId,
        nhanVienId : this.thongTinNguoiDung.nhanVienId
      })
      
      var nhanVienIdHienTai = this.thongTinNguoiDung.nhanVienId

      if(this.dataResponse.DSNhanVien.length>0){
        var nhanVienTheoNVId =  this.dataResponse.DSNhanVien.filter(function (el) {
          return el.NHANVIEN_ID == nhanVienIdHienTai;
        });
     
        if(nhanVienTheoNVId.length > 0 ){
          this.params.nhanVienGiaoId =  nhanVienIdHienTai
          this.params.nhanVienNhanId =  nhanVienIdHienTai
          this.Enabled.nhanVienGiao = false
          this.Enabled.nhanVienNhan = false
        }else{
          this.params.nhanVienGiaoId =  this.dataResponse.DSNhanVien[0].NHANVIEN_ID
          this.params.nhanVienNhanId =  this.dataResponse.DSNhanVien[0].NHANVIEN_ID
          this.Enabled.nhanVienGiao = true
          this.Enabled.nhanVienNhan = true
        }

        await this.onChangeNVGiao()        
        if(this.params.khoGiaoId != ""){
          await this.onChangeKhoGiao()
        }
        await this.onChangeNVNhan()
        if(this.params.khoNhanId != ""){          
          await this.onChangeKhoNhan()          
        }
      }
    },


    async onClickRefreshKhoGiao(){
      try{
        this.loading(true)
        this.params.serialKhoGiao = ""
        this.rowsCheckedVTGiao = []
        await this.Load_DS(1,this.params.khoGiaoId)
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
      
    },

    async onClickReFreshKhoNhan(){
      try{
        this.loading(true)
        this.params.serialKhoNhan = ""
        this.rowsCheckedVTNhan = []
        await this.Load_DS(2,this.params.khoNhanId)
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
  
    
    async onClickInsert(){
      try{ 
        this.loading(true)
        if(this.rowsCheckedVTGiao.length == 0){
          this.$toast.error("Chưa chọn vật tư")
          return
        }
        let ds_tamung = []
        for(let e of this.dataResponse.DSVatTuGiao){
          if(this.rowsCheckedVTGiao.includes(e.TUVT_ID)){
            ds_tamung.push({
              luongcap: e.LUONGCAP,
              tuvt_id : e.TUVT_ID
            })
          }
        }
        let ketqua = await this.tamUngVatTuNhanVien({
          ds_tamung: ds_tamung,
          khogiao_id: this.params.khoGiaoId,
          khonhan_id: this.params.khoNhanId,
          ngaygiao: this.f_DateToString(this.params.ngayGiao),
          nhanvien_giao_id: this.params.nhanVienGiaoId,
          nhanvien_nhan_id:  this.params.nhanVienNhanId
        })
        this.$toast.success("Tạm ứng vật tư thành công")
        await this.Load_DS(0,-1)
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async onClickDelete(){
      try{ 
        this.loading(true)
        if(this.rowsCheckedVTNhan.length == 0){
          this.$toast.error("Chưa chọn vật tư")
          return
        }
        let ds_tamung = []
        for(let e of this.dataResponse.DSVatTuNhan){
          if(this.rowsCheckedVTNhan.includes(e.TUVT_ID)){
            ds_tamung.push({
              luongcap: e.LUONGCAP,
              tuvt_id : e.TUVT_ID
            })
          }
        }
        let ketqua = await this.tamUngVatTuNhanVien({
          ds_tamung: ds_tamung,
          khogiao_id: this.params.khoNhanId ,
          khonhan_id: this.params.khoGiaoId,
          ngaygiao: this.f_DateToString(this.params.ngayGiao),
          nhanvien_giao_id: this.params.nhanVienNhanId,
          nhanvien_nhan_id:  this.params.nhanVienGiaoId
        })
        this.$toast.success("Tạm ứng vật tư thành công")
        await this.Load_DS(0,-1)
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    // async onClickInsert(){
    //   try{
    //     this.loading(true)
    //     var NVGiaoHienTai = this.params.nhanVienGiaoId
    //     var NVNhanHienTai = this.params.nhanVienNhanId
    //     var DSNhanVienGiaoFilter =  this.dataResponse.DSNhanVien.filter(function (el) {
    //       return el.NHANVIEN_ID == NVGiaoHienTai;
    //     });

    //     var DSNhanVienNhanFilter =  this.dataResponse.DSNhanVien.filter(function (el) {
    //       return el.NHANVIEN_ID == NVNhanHienTai;
    //     });      
    //     if(DSNhanVienGiaoFilter.length > 0 && DSNhanVienNhanFilter.length > 0){
    //       if(DSNhanVienGiaoFilter[0].DONVI_ID != DSNhanVienNhanFilter[0].DONVI_ID ){
    //         this.$toast.error("Chỉ có thể tạm ứng vật tư giữa nhân viên trong cùng 1 đơn vị")
    //         return
    //       }

    //       if(this.params.khoGiaoId == this.params.khoNhanId){
    //         this.$toast.error("Không thể tạm ứng vật tư cùng 1 kho")
    //         return
    //       }

    //       var khoGiaoHienTai = this.params.khoGiaoId
    //       var khoNhanHienTai = this.params.khoNhanId

    //       var DSKhoGiaoFilter =  this.dataResponse.DSKhoGiao.filter(function (el) {
    //         return el.KHO_ID == khoGiaoHienTai;
    //       });
    //       var DSKhoNhanFilter =  this.dataResponse.DSKhoNhan.filter(function (el) {
    //         return el.KHO_ID == khoNhanHienTai;
    //       });

    //       if(DSKhoGiaoFilter.length > 0 && DSKhoNhanFilter.length > 0){
    //         if(this.thongTinNguoiDung.phanVungId != 50){
    //           if(DSKhoGiaoFilter[0].DONVI_ID != DSKhoNhanFilter[0].DONVI_ID ){
    //             this.$toast.error("Hai kho thuộc 2 đơn vị khác nhau không thể tạm ứng được")
    //             return
    //           }
    //         } 

    //         if(DSKhoGiaoFilter[0].LOAIKHO_ID == DSKhoNhanFilter[0].LOAIKHO_ID ){
    //           console.log(LOAI_KHO.KHO_TOTRUONG , " = KHO_TOTRUONG")
    //           if(DSKhoGiaoFilter[0].LOAIKHO_ID == LOAI_KHO.KHO_TOTRUONG){
    //             this.$toast.error("Hai kho tổ trưởng không thể tạm ứng vật tư cho nhau được")
    //             return
    //           }
    //         }
    //       }

    //       var rowsCheckedVTGiao = this.rowsCheckedVTGiao
    //       let rowCheckbox = this.dataResponse.DSVatTuGiao.filter(function (el) {
    //         return rowsCheckedVTGiao.includes(el.TUVT_ID) ;
    //       });
    //       console.log(rowCheckbox, " = rowCheckbox")
        
    //       if(rowCheckbox.length > 0){

    //           var KhoGiaoNhan = await this.getDSVatTuKhoGiaoNhan({
    //             khoId: this.params.khoNhanId,
    //             dk: 1,
    //             pageNum: 1,
    //             pageSize: 10,
    //           })
    //           console.log(KhoGiaoNhan , " = KhoGiaoNhan")
    //           var isError = false
    //           for(const i in rowCheckbox){              
    //             if(rowCheckbox[i].KHOTT_GOC != null && rowCheckbox[i].KHOTT_GOC != "" && this.thongTinNguoiDung.phanVungId != 50){

    //               var DSKhoCha =  KhoGiaoNhan.filter(function (el) {
    //                 return el.KHOTT_CHA != rowCheckbox[i].KHOTT_GOC;
    //               });

    //               var khoNhanId = this.params.khoNhanId
    //               var DSKhoNhan =  this.dataResponse.DSKhoNhan.filter(function (el) {
    //                 return el.KHO_ID == khoNhanId;
    //               });
    //               console.log(DSKhoCha.length , " = DSKhoCha")
    //               if(DSKhoCha.length > 0){  
    //                 isError = true                
    //                 this.$toast.error("Bạn hãy kiểm tra lại dữ liệu kho cha của " + DSKhoNhan[0].TEN_K_NEW + ". Thiết bị đang thuộc quyền quản lý của kho tổ trưởng khác")                  
    //                 return
    //               }else{
    //                 if(KhoGiaoNhan.length == 0){
                    
    //                   let DSKhoNhanId = await this.getIDKhoCha(this.params.khoNhanId)
                    
    //                   if(DSKhoNhanId.length == 0 ){
    //                     isError = true
    //                     this.$toast.error("Không lấy được thông tin kho của kho nhận")                      
    //                     return
    //                   } 
    //                   if(DSKhoNhanId[0] == "-1"){
    //                     isError = true
    //                     this.$toast.error("Không lấy được thông tin kho  cha của kho nhận")                      
    //                     return
    //                   }
                      
    //                   if(DSKhoNhanId[0] != rowCheckbox[i].KHOTT_GOC){ 
    //                     isError = true                     
    //                     this.$toast.error("Bạn hãy kiểm tra lại dữ liệu kho cha của " + DSKhoNhan[0].TEN_K_NEW + ". Thiết bị đang thuộc quyền quản lý của kho tổ trưởng khác")
    //                     return
    //                   }
    //                 }
    //               }
    //             }
    //             if(KhoGiaoNhan.length > 0 ){                
    //               var KhoGiaoNhanFilter =  KhoGiaoNhan.filter(function (el) {
    //                 return el.THIETBI_ID == rowCheckbox[i].THIETBI_ID &&
    //                       el.SO_PHIEU == rowCheckbox[i].SO_PHIEU;
    //               });                
    //               if(KhoGiaoNhanFilter.length == 0){
    //                 var ketQua =  await this.TaoDuLieu_TUVT(true,rowCheckbox[i],KhoGiaoNhanFilter,true)              
    //                 if(ketQua != "1"){
    //                   isError = true
    //                   this.$toast.error(ketQua)                   
    //                   return
    //                 }
    //               }else{
    //                 var ketQua =  await this.TaoDuLieu_TUVT(false,rowCheckbox[i],KhoGiaoNhanFilter,true)              
    //                 if(ketQua != "1"){
    //                   isError = true
    //                   this.$toast.error(ketQua)                
    //                   return
    //                 }
    //               }
    //             }else{
    //               var ketQua =  await this.TaoDuLieu_TUVT(true,rowCheckbox[i],null,true)             
    //               if(ketQua != "1"){    
    //                 isError = true              
    //                 this.$toast.error(ketQua)                 
    //                 return
    //               }
    //             }             
    //           }
    //           if(!isError){
    //             this.$toast.success("Tạm ứng vật tư thành công")
    //           }
    //           await this.Load_DS(0,-1)

    //       }else{
    //         this.$toast.error("Không có vật tư nào được chọn để tạm ứng")
    //       }
    //     }
    //   }catch(e){
    //     this.$toast.error(e.data.message)
    //   }finally{
    //     this.loading(false)
    //   }
    // },
    async TaoDuLieu_TUVT(themmoi,dataInsert,dataUpdate,insDel){
      dataUpdate = themmoi ? dataUpdate : dataUpdate[0] // nếu update thì set lại = phần tử đầu tên do tryền sai
      var ls_tuvt_id = 0;
      var ls_tb_id = 0;
      var ls_sl_goc = 0;
      var ls_sl_ton = 0;
      var ls_dongia = 0;
      var ls_tien = 0;
      var ls_vat = 0;
      var ls_serial = "";
      var ls_matbi = "";
      var ls_khogiao_id = 0;
      var ls_khonhan_id = 0;
      var ls_sl_gocgiao = 0;
      var ls_sl_tongiao = 0;
      var ls_sl_gocnhan = 0;
      var ls_sl_tonnhan = 0;
      var ls_nv_giao_id=0;
      var ls_nv_nhan_id=0;
      var kieu=0;
      var ngay_ht = "";
      var ctct_id = 0;

      if(themmoi){
        kieu = 1
        ls_tuvt_id = await this.getKey({
          keyName:"TUVT_ID",
          numBlockSize: 0,
          numRetry: 0,
        })         
        ls_tb_id = dataInsert.THIETBI_ID
        ls_sl_goc =  dataInsert.LUONGCAP
        ls_sl_ton = parseFloat(dataInsert.LUONGCAP)
        ls_dongia = dataInsert.DONGIA != null ? dataInsert.DONGIA : ls_dongia
        ls_tien = dataInsert.TIEN != null ? ls_dongia * ls_sl_ton : ls_tien
        ls_vat = dataInsert.VAT != null ? ls_tien * 10/100 : ls_vat
        ls_khogiao_id = dataInsert.KHO_NHAN_ID
        ls_khonhan_id = insDel ? this.params.khoNhanId : this.params.khoGiaoId
        ls_sl_gocgiao = dataInsert.SL_GOC
        ls_sl_gocnhan = 0
        ls_sl_tongiao = dataInsert.SL_TON
        ls_sl_tonnhan = 0
        ls_serial = dataInsert.SERIAL != null ? dataInsert.SERIAL : ls_serial
        ls_matbi = dataInsert.MA_TBI != null ? dataInsert.MA_TBI : ls_matbi
        ngay_ht = dataInsert.NGAY_HT != null ? dataInsert.NGAY_HT : ngay_ht
        ls_nv_giao_id = insDel ? this.params.nhanVienGiaoId : this.params.nhanVienNhanId
        ls_nv_nhan_id = insDel ? this.params.nhanVienNhanId : this.params.nhanVienGiaoId
        ctct_id = dataInsert.CTCT_ID != null ? dataInsert.CTCT_ID : 0

      }else{

        kieu = -1
        ls_tuvt_id = dataUpdate.TUVT_ID
        ls_tb_id = dataUpdate.THIETBI_ID
        ls_sl_goc = dataUpdate.SL_GOC
        ls_dongia = dataUpdate.DONGIA != null ? dataUpdate.DONGIA : ls_dongia
        ls_khogiao_id = dataInsert.KHO_NHAN_ID
        ls_khonhan_id = dataUpdate.KHO_NHAN_ID
        ls_sl_gocgiao = dataInsert.SL_GOC
        ls_sl_gocnhan = dataUpdate.SL_GOC
        ls_sl_tongiao = dataInsert.SL_TON
        ls_sl_tonnhan = dataUpdate.SL_TON
        ls_sl_ton = dataUpdate.MA_TBI != null || dataUpdate.MA_TBI == null && dataUpdate.SERIAL == null ?  parseFloat(dataInsert.LUONGCAP) +  parseFloat(dataUpdate.SL_TON) : parseFloat(dataInsert.LUONGCAP)
        ls_tien = dataUpdate.TIEN != null ? ls_dongia * ls_sl_ton : ls_tien
        ls_vat = dataUpdate.VAT != null ? ls_tien * 10/100 : ls_vat
        ls_serial = dataUpdate.SERIAL != null ? dataUpdate.SERIAL : ls_serial
        ls_matbi = dataUpdate.MA_TBI != null ? dataUpdate.MA_TBI : ls_matbi
        ls_nv_giao_id = insDel ? this.params.nhanVienGiaoId : this.params.nhanVienNhanId
        ls_nv_nhan_id = insDel ? this.params.nhanVienNhanId : this.params.nhanVienGiaoId
        ctct_id = dataInsert.CTCT_ID != null ? dataInsert.CTCT_ID : 0
      } 

      var kqTamUng = await this.addTamUngVatTu({
        tuVtId: ls_tuvt_id,
        lsTbId: ls_tb_id,
        lsSlGoc: ls_sl_goc,
        lsSlTon: ls_sl_ton,
        lsDonGia: ls_dongia,
        lsTien: ls_tien,
        lsVat: ls_vat,
        lsKhoGiaoId: ls_khogiao_id,
        lsKhoNhanId: ls_khonhan_id,
        lsGocGiao: ls_sl_gocgiao,
        lsGocNhan: ls_sl_gocnhan,
        lsTonGiao: ls_sl_tongiao,
        lsTonNhan: ls_sl_tonnhan,
        lsSerial: ls_serial,
        lsMatBi: ls_matbi,
        lsNgayGiao: this.f_DateToString(this.params.ngayGiao),
        lsTrangThai: 0,
        lsNvGiaoId: ls_nv_giao_id,
        lsNvNhanId: ls_nv_nhan_id,
        lsNvThId: this.thongTinNguoiDung.nhanVienId,
        nguoiCn: this.thongTinNguoiDung.nguoiCN ,
        ngayCn: this.f_DateToString(new Date()),
        mayCn: this.thongTinNguoiDung.mayCN,
        ipCn: this.thongTinNguoiDung.ipCN,
        kieu: kieu,
        giaoTuId: dataInsert.TUVT_ID,
        slTon: dataInsert.SL_TON - dataInsert.LUONGCAP,
        ctCtId: ctct_id
      })
      return kqTamUng
    },


    // async onClickDelete(){
    //   try{
    //     this.loading(true)
    //     var NVGiaoHienTai = this.params.nhanVienGiaoId
    //     var NVNhanHienTai = this.params.nhanVienNhanId
    //     var DSNhanVienGiaoFilter =  this.dataResponse.DSNhanVien.filter(function (el) {
    //       return el.NHANVIEN_ID == NVGiaoHienTai;
    //     });

    //     var DSNhanVienNhanFilter =  this.dataResponse.DSNhanVien.filter(function (el) {
    //       return el.NHANVIEN_ID == NVNhanHienTai;
    //     });

    //     if(DSNhanVienGiaoFilter.length > 0 && DSNhanVienNhanFilter.length > 0){
    //       if(DSNhanVienGiaoFilter[0].DONVI_ID != DSNhanVienNhanFilter[0].DONVI_ID ){
    //         this.$toast.error("Chỉ có thể tạm ứng vật tư giữa nhân viên trong cùng 1 đơn vị")
    //         return
    //       }

    //       if(this.params.khoGiaoId == this.params.khoNhanId){
    //         this.$toast.error("Không thể trả vật tư cùng 1 kho")
    //         return
    //       }

    //       var khoGiaoHienTai = this.params.khoGiaoId
    //       var khoNhanHienTai = this.params.khoNhanId

    //       var DSKhoGiaoFilter =  this.dataResponse.DSKhoGiao.filter(function (el) {
    //         return el.KHO_ID == khoGiaoHienTai;
    //       });
    //       var DSKhoNhanFilter =  this.dataResponse.DSKhoNhan.filter(function (el) {
    //         return el.KHO_ID == khoNhanHienTai;
    //       });

    //       if(DSKhoGiaoFilter.length > 0 && DSKhoNhanFilter.length > 0){
    //         if(this.thongTinNguoiDung.phanVungId != 50){
    //           if(DSKhoGiaoFilter[0].DONVI_ID != DSKhoNhanFilter[0].DONVI_ID ){
    //             this.$toast.error("Hai kho thuộc 2 đơn vị khác nhau không thể tạm ứng được")
    //             return
    //           }
    //         } 

    //         if(DSKhoGiaoFilter[0].LOAIKHO_ID == DSKhoNhanFilter[0].LOAIKHO_ID ){
    //           console.log(LOAI_KHO.KHO_TOTRUONG , " = KHO_TOTRUONG")
    //           if(DSKhoGiaoFilter[0].LOAIKHO_ID == LOAI_KHO.KHO_TOTRUONG){
    //             this.$toast.error("Hai kho tổ trưởng không thể tạm ứng vật tư cho nhau được")
    //             return
    //           }
    //         }
    //       }      
          
    //       var rowsCheckedVTNhan = this.rowsCheckedVTNhan
    //       let rowCheckbox = this.dataResponse.DSVatTuNhan.filter(function (el) {
    //         return rowsCheckedVTNhan.includes(el.TUVT_ID) ;
    //       });
    //       console.log(rowCheckbox, " = rowCheckbox")

    //       if(rowCheckbox.length > 0){

    //           var KhoGiaoNhan = await this.getDSVatTuKhoGiaoNhan({
    //             khoId: this.params.khoGiaoId,
    //             dk: 1,
    //             pageNum: 1,
    //             pageSize: 10,
    //           })
          
    //           var isError = false
    //           for(const i in rowCheckbox){
              
    //             if(rowCheckbox[i].KHOTT_GOC != null && rowCheckbox[i].KHOTT_GOC != "" && this.thongTinNguoiDung.phanVungId != 50){

    //               var DSKhoCha =  KhoGiaoNhan.filter(function (el) {
    //                 return el.KHOTT_CHA != rowCheckbox[i].KHOTT_GOC;
    //               });

    //               var khoGiaoId = this.params.khoGiaoId
    //               var DSKhoGiao =  this.dataResponse.DSKhoGiao.filter(function (el) {
    //                 return el.KHO_ID == khoGiaoId;
    //               });

    //               if(DSKhoCha.length > 0){                  
    //                 this.$toast.error("Bạn hãy kiểm tra lại dữ liệu kho cha của " + DSKhoGiao[0].TEN_K_NEW + ". Thiết bị đang thuộc quyền quản lý của kho tổ trưởng khác")
    //                 isError = true
    //                 return
    //               }else{
    //                 if(KhoGiaoNhan.length == 0){
                    
    //                   let DSKhoGiaoId = await this.getIDKhoCha(this.params.khoGiaoId)
                    
    //                   if(DSKhoGiaoId.length == 0 ){
    //                     this.$toast.error("Không lấy được thông tin kho của kho nhận")
    //                     isError = true
    //                     return
    //                   } 
    //                   if(DSKhoGiaoId[0] == "-1"){
    //                     this.$toast.error("Không lấy được thông tin kho  cha của kho nhận")
    //                     isError = true
    //                     return
    //                   }
                     
    //                   if(DSKhoGiaoId[0] != rowCheckbox[i].KHOTT_GOC){
    //                     isError = true
    //                     this.$toast.error("Bạn hãy kiểm tra lại dữ liệu kho cha của " + DSKhoGiao[0].TEN_K_NEW + ". Thiết bị đang thuộc quyền quản lý của kho tổ trưởng khác")
    //                     return
    //                   }
    //                 }
    //               }
    //             }
    //             if(KhoGiaoNhan.length > 0 ){
                  
    //               var KhoGiaoNhanFilter =  KhoGiaoNhan.filter(function (el) {
    //                 return el.THIETBI_ID == rowCheckbox[i].THIETBI_ID &&
    //                       el.SO_PHIEU == rowCheckbox[i].SO_PHIEU;
    //               });
    //               console.log(KhoGiaoNhanFilter , " = KhoGiaoNhanFilter")
    //               if(KhoGiaoNhanFilter.length == 0){
    //                 var ketQua =  await this.TaoDuLieu_TUVT(true,rowCheckbox[i],KhoGiaoNhanFilter,false)                   
    //                 if(ketQua != "1"){    
    //                   isError = true                
    //                   this.$toast.error(ketQua)                    
    //                   return
    //                 }
    //               }else{
    //                 var ketQua =  await this.TaoDuLieu_TUVT(false,rowCheckbox[i],KhoGiaoNhanFilter,false)                  
    //                 if(ketQua != "1"){                    
    //                   this.$toast.error(ketQua)   
    //                   isError = true
    //                   return
    //                 }
    //               }
    //             }else{
    //               var ketQua =  await this.TaoDuLieu_TUVT(true,rowCheckbox[i],null,false)                 
    //               if(ketQua != "1"){                  
    //                 this.$toast.error(ketQua)    
    //                 isError = true                
    //                 return
    //               }
    //             }             
    //           }
    //           if(!isError){
    //             this.$toast.success("Tạm ứng vật tư thành công");
    //           }             
    //           await this.Load_DS(0,-1)

    //       }else{
    //         this.$toast.error("Không có vật tư nào được chọn để trả")
    //       }
    //     }
    //   }catch(e){
    //     this.$toast.error(e.data.message)
    //   }finally{
    //     this.loading(false)
    //   }
    // },

    async onChangeNVGiao(){
      this.dataResponse.DSKhoGiao = await this.getDSKho({
        donViId : this.thongTinNguoiDung.donViId,
        nhanVienId : this.thongTinNguoiDung.nhanVienId
      }) 
      this.dataResponse.DSKhoGiao =  this.dataResponse.DSKhoGiao.filter(function (el) {
        return el.TRANGTHAI == "1";
      });
      
      if(this.dataResponse.DSKhoGiao.length > 0){
        this.params.khoGiaoId = this.dataResponse.DSKhoGiao[0].KHO_ID
      }
    },

    async onChangeNVNhan(){
      this.dataResponse.DSKhoNhan = await this.getDSKho({
        donViId : this.thongTinNguoiDung.donViId,
        nhanVienId : this.thongTinNguoiDung.nhanVienId
      }) 
      this.dataResponse.DSKhoNhan =  this.dataResponse.DSKhoNhan.filter(function (el) {
        return el.TRANGTHAI == "1";
      });
 
      if(this.dataResponse.DSKhoNhan.length > 0){
        this.params.khoNhanId = this.dataResponse.DSKhoNhan[0].KHO_ID
      }
    },

    async onChangeKhoGiao(){      
      this.Load_DS(1,this.params.khoGiaoId)
      this.dataResponse.DSNhanVienKhoGiao = await this.getDSNhanVien(this.params.khoGiaoId)
    },
    async onClickLayThongTin(){
      try{
        this.loading(true);
        this.loadDataNhanVien()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
    },
    async onChangeKhoNhan(){      
      this.Load_DS(2,this.params.khoNhanId)
      this.dataResponse.DSNhanVienKhoNhan = await this.getDSNhanVien(this.params.khoNhanId)      
    },
    async onClickTraCuuKho(){
      this.$refs.frmTraCuuVatTuTrongKho.dialogOpen()
    },
    onClickXuatExcel(){
    
      if (this.isDisableBtnXuatFile) return
      this.$root.showLoading(true);
      try {
        if (this.dataResponse.DSVatTuGiao) {        
          this.dsThuocTinh = [  
            "NGAY_HT",
            "NGAYGIAO",
            "TEN_VT",
            "SERIAL",
            "SERIAL_GP",
            "SL_TON",
            "DONGIA",
            "LUONGCAP",
            "LOHANG",
            "DVI_TINH",
            "MA_TBI",            
            "SO_PHIEU",
            "GHICHU",         
          ]
          // for (const i in this.dataResponse.DSVatTuGiao){
          //   this.dsThuocTinh.push(key)
          // }
          this.$bvModal.show('modal-xuat-dulieu')
        
        }else {
          this.$root.toastError("Danh sách không có dữ liệu để xuất!")
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    async Load_DS(kieu,KhoId){
      this.loading(true)
      console.log(kieu , " = kieu")
      if(kieu == 1){
        this.dataResponse.DSVatTuGiao = await this.getDSVatTu({
          khoId: KhoId,
          dk: 1,
          pageNum: 1,
          pageSize: 10,
        })
        this.dataResponse.DSVatTuGiao.forEach(e =>{     
          e.NGAY_HT = e.NGAY_HT.slice(0,10).replaceAll("-","/")
          e.NGAYGIAO = e.NGAYGIAO.slice(0,10).replaceAll("-","/")
          e.DONGIA = parseFloat(e.DONGIA)
          e.SL_TON = parseFloat(e.SL_TON)
          e.LUONGCAP = parseFloat(e.LUONGCAP)
        })
        this.rowsCheckedVTGiao = [] // reset lại checkbox
        this.checkAllVTGiao = false
      }else if(kieu == 2){

        this.dataResponse.DSVatTuNhan = await this.getDSVatTu({
          khoId: KhoId,
          dk: 1,
          pageNum: 1,
          pageSize: 10,
        })

        this.dataResponse.DSVatTuNhan.forEach(e =>{     
          e.NGAY_HT = e.NGAY_HT.slice(0,10).replaceAll("-","/")
          e.NGAYGIAO = e.NGAYGIAO.slice(0,10).replaceAll("-","/")
          e.DONGIA = parseFloat(e.DONGIA)
          e.SL_TON = parseFloat(e.SL_TON)
          e.LUONGCAP = parseFloat(e.LUONGCAP)
        })
        this.checkAllVTNhan = false
        this.rowsCheckedVTNhan = [] // reset lại checkbox
      }else if(kieu == 0){
        console.log("Load_DS Load_DS")
        this.dataResponse.DSVatTuGiao = await this.getDSVatTu({
          khoId: this.params.khoGiaoId,
          dk: 1,
          pageNum: 1,
          pageSize: 10,
        })
        this.dataResponse.DSVatTuGiao.forEach(e =>{     
          e.NGAY_HT = e.NGAY_HT.slice(0,10).replaceAll("-","/")
          e.NGAYGIAO = e.NGAYGIAO.slice(0,10).replaceAll("-","/")
          e.DONGIA = parseFloat(e.DONGIA)
          e.SL_TON = parseFloat(e.SL_TON)
          e.LUONGCAP = parseFloat(e.LUONGCAP)
        })

        this.dataResponse.DSVatTuNhan = await this.getDSVatTu({
          khoId: this.params.khoNhanId,
          dk: 1,
          pageNum: 1,
          pageSize: 10,
        })

        this.dataResponse.DSVatTuNhan.forEach(e =>{     
          e.NGAY_HT = e.NGAY_HT.slice(0,10).replaceAll("-","/")
          e.NGAYGIAO = e.NGAYGIAO.slice(0,10).replaceAll("-","/")
          e.DONGIA = parseFloat(e.DONGIA)
          e.SL_TON = parseFloat(e.SL_TON)
          e.LUONGCAP = parseFloat(e.LUONGCAP)
        })
        this.checkAllVTGiao = false
        this.checkAllVTNhan = false
        this.rowsCheckedVTGiao = [] // reset lại checkbox
        this.rowsCheckedVTNhan = [] // reset lại checkbox
      }
      this.loading(false)

    },
    async onChangeSerialKhoGiao(){
      var serial = this.params.serialKhoGiao.trim()
      var isExistSerial = false
      for(const i in this.dataResponse.DSVatTuGiao){
        if(this.dataResponse.DSVatTuGiao[i].SERIAL == serial){   
          isExistSerial = true
          if(this.dataResponse.DSVatTuGiao[i].SL_TON != 0){
            this.dataResponse.DSVatTuGiao[i].LUONGCAP = this.dataResponse.DSVatTuGiao[i].SL_TON
            this.rowsCheckedVTGiao.push(this.dataResponse.DSVatTuGiao[i].TUVT_ID)
            this.params.serialKhoGiao = ""
          }else{
            this.$toast.error("Số lượng tồn không có")
          }            
        }
      }
      if(!isExistSerial){
        this.$toast.error("Không tìm thấy Serial")
      }
      this.$refs.gridVatTuGiao.grid.refreshColumns()
     
    },
    async onChangeSerialKhoNhan(){
      var serial = this.params.serialKhoNhan.trim()
      var isExistSerial = false 
      for(const i in this.dataResponse.DSVatTuNhan){
        if(this.dataResponse.DSVatTuNhan[i].SERIAL == serial){   
          isExistSerial = true          
          if(this.dataResponse.DSVatTuNhan[i].SL_TON != 0){        
            this.dataResponse.DSVatTuNhan[i].LUONGCAP = this.dataResponse.DSVatTuNhan[i].SL_TON
            this.rowsCheckedVTNhan.push(this.dataResponse.DSVatTuNhan[i].TUVT_ID)
            this.params.serialKhoNhan = ""
          }else{
            this.$toast.error("Số lượng tồn không có")
          }            
        }
      }

      if(!isExistSerial){
        this.$toast.error("Không tìm thấy Serial")
      }
      this.$refs.gridVatTuNhan.grid.refreshColumns()
    },

    async editGridVatTuGiao(arg) {

      let gridVatTuGiao = this.$refs.gridVatTuGiao
      let changes = gridVatTuGiao.getBatchChanges()    

      if (changes.changedRecords.length == 0) { 
        return
      }
      if(arg.columnName == "LUONGCAP" ){

        if(changes.changedRecords[0].LUONGCAP > changes.changedRecords[0].SL_TON ){

          changes.changedRecords[0].LUONGCAP = 0 
          this.rowsCheckedVTGiao = this.removeElementInArray(this.rowsCheckedVTGiao , changes.changedRecords[0].TUVT_ID)          
          this.$toast.error("Số lượng cấp không được lớn hơn số lượng hiện tại!")

        }else if(changes.changedRecords[0].LUONGCAP < 0){

          changes.changedRecords[0].LUONGCAP = 0 
          this.rowsCheckedVTGiao = this.removeElementInArray(this.rowsCheckedVTGiao , changes.changedRecords[0].TUVT_ID)
          this.$toast.error("Số lượng cấp không được âm!")

        }else if(changes.changedRecords[0].LUONGCAP == 0){

          this.rowsCheckedVTGiao = this.removeElementInArray(this.rowsCheckedVTGiao , changes.changedRecords[0].TUVT_ID)

        }else{   
          if(!this.rowsCheckedVTGiao.includes(changes.changedRecords[0].TUVT_ID)){ // đã check rồi thì k thêm lại nữa
            this.rowsCheckedVTGiao.push(changes.changedRecords[0].TUVT_ID)            
          }        
        }
      }
     
      gridVatTuGiao.batchUpdate(changes)
    },

    async editGridVatTuNhan(arg) {

      let gridVatTuNhan = this.$refs.gridVatTuNhan
      let changes = gridVatTuNhan.getBatchChanges()    

      if (changes.changedRecords.length == 0) { 
        return
      }
      
      if(arg.columnName == "LUONGCAP" ){

        if(changes.changedRecords[0].LUONGCAP > changes.changedRecords[0].SL_TON ){

          changes.changedRecords[0].LUONGCAP = 0 
          this.rowsCheckedVTNhan = this.removeElementInArray(this.rowsCheckedVTNhan , changes.changedRecords[0].TUVT_ID)          
          this.$toast.error("Số lượng cấp không được lớn hơn số lượng hiện tại!")

        }else if(changes.changedRecords[0].LUONGCAP < 0){

          changes.changedRecords[0].LUONGCAP = 0 
          this.rowsCheckedVTNhan = this.removeElementInArray(this.rowsCheckedVTNhan , changes.changedRecords[0].TUVT_ID)
          this.$toast.error("Số lượng cấp không được âm!")

        }else if(changes.changedRecords[0].LUONGCAP == 0){

          this.rowsCheckedVTNhan = this.removeElementInArray(this.rowsCheckedVTNhan , changes.changedRecords[0].TUVT_ID)

        }else{   
          if(!this.rowsCheckedVTNhan.includes(changes.changedRecords[0].TUVT_ID)){ // đã check rồi thì k thêm nữa
            this.rowsCheckedVTNhan.push(changes.changedRecords[0].TUVT_ID)
          }          
        }
      }
     
      gridVatTuNhan.batchUpdate(changes)
    },
    removeElementInArray(array,element){
      const index = array.indexOf(element);
      if (index > -1) {
        array.splice(index, 1);
      }
      return array
    }
  },
  watch:{

    checkAllVTGiao: function (value) {
      this.rowsCheckedVTGiao = [];
      if (value) {
        for (let e of this.$refs.gridVatTuGiao.filteredDataSource) {
          this.rowsCheckedVTGiao.push(e.TUVT_ID);
          e.LUONGCAP = e.SL_TON
        }
      }else {        
        for (let e of this.dataResponse.DSVatTuGiao) {         
          e.LUONGCAP = 0
        }
      }   
      this.$refs.gridVatTuGiao.grid.refreshColumns()  
    },
    
    "rowsCheckedVTGiao": function(value){      
      this.rowsCheckedVTGiao =  value    
      for(let e of this.dataResponse.DSVatTuGiao){
        if(this.rowsCheckedVTGiao.includes(e.TUVT_ID) && e.LUONGCAP == 0 ){          
          e.LUONGCAP = e.SL_TON
        }else if(!this.rowsCheckedVTGiao.includes(e.TUVT_ID)){
          e.LUONGCAP = 0
        }
      }
      this.$refs.gridVatTuGiao.grid.refreshColumns()
    },
    
    checkAllVTNhan: function (value) {
      this.rowsCheckedVTNhan = [];
      if (value) {
        for (let e of this.$refs.gridVatTuNhan.filteredDataSource) {
          this.rowsCheckedVTNhan.push(e.TUVT_ID);
          e.LUONGCAP = e.SL_TON
        }
      }else {        
        for (let e of this.dataResponse.DSVatTuNhan) {         
          e.LUONGCAP = 0
        }
      }   
      this.$refs.gridVatTuNhan.grid.refreshColumns()  
    },
    "rowsCheckedVTNhan": function(value){      
      this.rowsCheckedVTNhan =  value    
      for(let e of this.dataResponse.DSVatTuNhan){
        if(this.rowsCheckedVTNhan.includes(e.TUVT_ID) && e.LUONGCAP == 0 ){          
          e.LUONGCAP = e.SL_TON
        }else if(!this.rowsCheckedVTNhan.includes(e.TUVT_ID)){
          e.LUONGCAP = 0
        }
      }
      this.$refs.gridVatTuNhan.grid.refreshColumns()
    },
  },
  mounted: async function () {
    document.addEventListener('click', event => {
      if (typeof (this.$refs["btn-nhanvien-giao"]) == 'undefined') {
          return
      }
      if (this.$refs["btn-nhanvien-giao"].contains(event.target)) {
          this.showNVGiao = this.showNVGiao == 'none' ? 'block' : 'none'
      } else if (this.$refs["body-show-nhanvien-giao"].contains(event.target)) {
          if (event.target.tagName.toLowerCase() == 'td') {
              this.showNVGiao = 'none'
          } else {
              this.showNVGiao = 'block'
          }
      } else {
          this.showNVGiao = 'none'
      }

      if (this.showNVGiao == 'block') {
          this.popper = new Popper(this.$refs["btn-nhanvien-giao"], this.$refs["body-show-nhanvien-giao"], {
              placement: 'bottom-start',
              modifiers: [{
                  name: 'offset',
                  options: {
                      offset: [0, 50]
                  },
              }]
          })
      } else {
          if (this.popper) {
              this.popper.destroy()
          }
          this.popper = null
      }
    }),
    document.addEventListener('click', event => {
      if (typeof (this.$refs["btn-nhanvien-nhan"]) == 'undefined') {
          return
      }
      if (this.$refs["btn-nhanvien-nhan"].contains(event.target)) {
          this.showNVNhan = this.showNVNhan == 'none' ? 'block' : 'none'
      } else if (this.$refs["body-show-nhanvien-nhan"].contains(event.target)) {
          if (event.target.tagName.toLowerCase() == 'td') {
              this.showNVNhan = 'none'
          } else {
              this.showNVNhan = 'block'
          }
      } else {
          this.showNVNhan = 'none'
      }

      if (this.showNVNhan == 'block') {
          this.popper = new Popper(this.$refs["btn-nhanvien-nhan"], this.$refs["body-show-nhanvien-nhan"], {
              placement: 'bottom-start',
              modifiers: [{
                  name: 'offset',
                  options: {
                      offset: [0, 50]
                  },
              }]
          })
      } else {
          if (this.popper) {
              this.popper.destroy()
          }
          this.popper = null
      }
    })
  },
  created: async function () {
    try{
      this.loading(true);
      this.loadDataNhanVien()
    }catch(e){
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false);
    }
  },
  provide: {
    grid: [ Freeze, Edit,Group, Page,Filter,Sort,Resize ,Aggregate,Toolbar]
  },
};
</script>
