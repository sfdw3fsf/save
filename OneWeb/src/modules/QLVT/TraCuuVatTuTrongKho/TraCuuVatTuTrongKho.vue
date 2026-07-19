<template src='./TraCuuVatTuTrongKho.html'></template>
<style scoped src='./TraCuuVatTuTrongKho.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import TraCuuVatTuTrongKhoAPI from  '../api/TraCuuVatTuTrongKhoAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/tracuuvattutrongkho'
import select2 from '@/components/Select2.vue'
import { Group, Page, Filter, Sort, Resize} from "@syncfusion/ej2-vue-grids";
import { Query,Predicate } from '@syncfusion/ej2-data';
import XLSX from "xlsx";
import ClickOutside from "vue-click-outside";
// Vue.prototype.$eventHub = new Vue();
export default {

  components: { XLSX, breadcrumb, appSelect2: select2 },
  name: "TraCuuVatTuTrongKho",
  mounted() {
  },
  provide: {
    grid: [ Group, Page,Filter,Sort,Resize ]
  },
  directives: {
    ClickOutside,
  },
  computed: {
    ...mapState("tracuuvattutrongkho", statePropertyName),
    ...mapGetters("tracuuvattutrongkho", getterName),
    p_kho_id: {
      get() {
        return this.params.p_kho_id
      },
      set(value) {
        return this.params.p_kho_id = value
      }
    },
    p_tatca_kho: {
      get() {
        return this.params.p_tatca_kho
      },
      set(value) {
        return this.params.p_tatca_kho = value
      }
    },
    
    p_donvi_id: {
      get() {
        return this.params.p_donvi_id
      },
      set(value) {
        return this.params.p_donvi_id = value
      }
    },
    p_denngay: {
      get() {
        return this.params.p_denngay
      },
      set(value) {
        return this.params.p_denngay = value
      }
    }
  },
  data() {
    return {
        header: {
            title: "TRA CỨU THIẾT BỊ TỒN KHO",
            list: [
           
            ],
        },
        params: {
            p_kho_id: 0,
            p_donvi_id: 0,
            p_tatca_kho: false,
            p_kho_obj: {},
            p_denngay: ""
        },
        dateConfig: {
          default: new Date(),
          max:  new Date(),
          format: 'dd/MM/yyyy',
          waterMark : 'Chọn ngày',
        },
        visible: {
          colChatLuong: true,
          colKieuPhieu: true,
          colNgayNhap: true,
        },
      templateOptions : {
          create: function (args) {
            if(args.column.field === 'STT'){
              return '<span class="fa input-icon-right"></span>'
            } 
            return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
          },
          write: function (args) {
          }
        },
      cRowSelected: function() {
        return {
          template : Vue.component('columntemplate', {
            template : '<span class="fa"></span>'
          })  // end of template
        }
      },
      iTemplate: function(e) {
                return {
                    template: Vue.component("itemTemplate", {
                      template: ` <div class="row">
                                    <div class="col-sm-2 border">
                                      <span>{{data.MA_KHO}}</span>
                                    </div>
                                    <div class="col-sm-10 border">
                                      <span>{{data.TEN_KHO}}</span>
                                    </div>
                                  </div>`,
                      data() {
                        return {
                          data: {}
                        };
                      }
                    })
                };
            },
      onFiltering: function(e) {
            var query = new Query();
            let predicate;  
            
            //frame the query based on search string with filter type.
            if (e.text !== ''){
              predicate  = new Predicate('MA_KHO', 'Contains', e.text);
              predicate = predicate.or('TEN_KHO', 'contains', e.text);
              query.where(predicate)
            }

            //pass the filter data source, filter query to updateData method.
            e.updateData(this.dataSource, query);
        },  
      pageSettings: { pageSizes: ['10','20','50','100','All'], pageSize: 10 },
      filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
      selectionOptions: {
        type: 'Single'
      },
      tabTongHop: false,
      tabThongTin: false,
      tabThietBi: false,
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      comboKho: false,
      selectedKho: "",

      TongHop_Columns: [
        {fieldName: 'STT', headerText: 'Các DS', textAlign: 'Center', allowFiltering: true, template: function () {
            return {
                template: Vue.component('DSTemplate', {
                    template: `<div class="selected center">
                                <button class="btn btn-main pad4 lh16 w50" @click="btnTimKiem_ButtonClick">
                                    <span class="-ap icon-more_horiz"></span>
                                </button>
                                <button class="btn btn-main pad4 lh16 w50" @click="vbtnClose_Click">
                                    <span class="-ap icon-close"></span>
                                </button>
                              </div>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnTimKiem_ButtonClick(){
                        // this.$eventHub.$emit('btnTimKiem_ButtonClick', this.data);
                        await this.$parent.$parent.$parent.$parent.btnTimKiem_ButtonClick(this.data)
                      },
                      async vbtnClose_Click(){
                        // this.$eventHub.$emit('vbtnClose_Click', true);
                        await this.$parent.$parent.$parent.$parent.vbtnClose_Click()
                      }
                    }
                })
            }
        }},
        {fieldName: 'LOHANG', headerText: 'Lô hàng' , textAlign: 'Left', allowFiltering: true},
        {fieldName: 'MA_VT', headerText: 'Mã vật tư' , textAlign: 'Left', allowFiltering: true},
        {fieldName: 'TEN_VT', headerText: 'Tên vật tư' , textAlign: 'Left', allowFiltering: true},
        {fieldName: 'SOLUONG' , headerText: 'Số lượng' , textAlign: 'Right', type:'number', format:'n3', allowFiltering: true},
        {fieldName: 'DACAP', headerText: 'Đã cấp' , textAlign: 'Right', type:'number', format:'n3', allowFiltering: true},
        {fieldName: 'CONLAI', headerText: 'Còn lại' , textAlign: 'Right', type:'number', format:'n3', allowFiltering: true},
        {fieldName: 'DVI_TINH', headerText: 'Đơn vị tính', textAlign: 'Left', allowFiltering: true},
      ]
    };
  },
  methods: {
    ...mapActions("tracuuvattutrongkho", actionName),
    ...mapMutations("tracuuvattutrongkho", mutationName),
    async gridDenDonVi_selectedRowChanged(data) {
      if (data){
        this.selectedKho = data.TEN_K_NEW
        this.p_kho_id = data.KHO_ID;
        await this.hideComboKho()
      }
      
    },
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    // btnTraCuu_Click
    async btnTraCuu_Click(){
        await this.NAP_DS_THIETBI()
    },
    // btnXuatExcel_Click
    btnXuatExcel_Click(){
      if (!this.tabTongHop){
        var ExportData  = []
        this.DSChiTietTB.forEach(o=>{
          var newObj = {}
          Object.assign(newObj,{['Lô hàng']: o['LOHANG']})
          Object.assign(newObj,{['Mã vật tư']: o['MA_VT']})
          Object.assign(newObj,{['Tên vật tư']: o['TEN_VT']})
          Object.assign(newObj,{['Mã thiết bị']: o['MA_TBI']})
          Object.assign(newObj,{['Serial']: o['SERIAL']})
          Object.assign(newObj,{['Serial GP']: o['SERIAL_GP']})
          Object.assign(newObj,{['Đơn vị tính']: o['DVI_TINH']})
          Object.assign(newObj,{['Đơn giá']: o['DONGIA']})
          Object.assign(newObj,{['Số lượng']: o['SL_TON']})
          Object.assign(newObj,{['Tình trạng']: o['TINHTRANGTBI_ID']})
          ExportData.push(newObj)
        })
        this.ExportExcel(ExportData)
      }
      else {
        var ExportData  = []
        this.DSTongHopTB.forEach(o=>{
          var newObj = {}
          Object.assign(newObj,{['Lô hàng']: o['LOHANG']})
          Object.assign(newObj,{['Mã vật tư']: o['MA_VT']})
          Object.assign(newObj,{['Tên vật tư']: o['TEN_VT']})
          Object.assign(newObj,{['Đơn vị tính']: o['DVI_TINH']})
          Object.assign(newObj,{['Số lượng']: o['SOLUONG']})
          Object.assign(newObj,{['Đã cấp']: o['DACAP']})
          Object.assign(newObj,{['Còn lại']: o['CONLAI']})
          Object.assign(newObj,{['Các DS']: ""})
          ExportData.push(newObj)
        })
        
        this.ExportExcel(ExportData)
      }
    },
    async ExportExcel(grid){
      try {
        if(!grid.length){
          this.$toast.error("Không có dữ liệu để xuất")
          return
        }

        let data1 = XLSX.utils.json_to_sheet(grid);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data1, "Sheet1"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, !this.tabTongHop ? "ChiTiet.xlsx" : "TongHop.xlsx" );

      } catch (error) {
        this.$toast.error("Lỗi khi xuất dữ liệu\r\n")
      }
    },
    dialogClose(){

    },
    dialogOpen (){
      this.$refs.popup_tracuuvattu_trongkho.show();
    },
    async NAP_DS_THIETBI(){
      this.loading(true)
      let vdonvi_id = 0
      if  (this.p_tatca_kho){
        var d = this.DSKhoTheoNV.filter(a=>a.KHO_ID == this.p_kho_id)
        if (d.length){
          vdonvi_id = d[0].DONVI_ID.toString() || 0 // trường hợp dvi_id null
          this.p_donvi_id = vdonvi_id
        }
      }
      // if (this.p_kho_id){
      //   this.params.p_kho_obj = this.DSKhoTheoNV.find(a=>a.KHO_ID == this.p_kho_id)
      //   this.p_donvi_id = this.params.p_kho_obj.DONVI_ID ? this.params.p_kho_obj.DONVI_ID : 0

      await this.getDSTongHopTB({
        "phanVungId": this.$auth.getPhanVungID(),
        "idKho": this.p_kho_id,
        "denNgay": this.f_DateToString(this.p_denngay,'DD/MM/YYYY'),
        "idDonVi": vdonvi_id,
        "idKhoThat": this.p_kho_id,
        "checkKho": this.p_tatca_kho ? 1 : 0,
      })

      await this.getDSChiTietTB({
          "phanVungId": this.$auth.getPhanVungID(),
          "idKho": this.p_kho_id,
          "denNgay": this.f_DateToString(this.p_denngay,'DD/MM/YYYY'),
          "idDonVi": vdonvi_id,
          "idKhoThat": this.p_kho_id,
          "checkKho": this.p_tatca_kho ? 1 : 0,
      })
      this.loading(false)

      this.visible.colChatLuong = this.DSChiTietTB.length && this.DSChiTietTB[0].hasOwnProperty('CHATLUONG') ? true : false
      this.visible.colKieuPhieu = this.DSChiTietTB.length && this.DSChiTietTB[0].hasOwnProperty('MUCDICH') ? true : false
      this.visible.colNgayNhap = this.DSChiTietTB.length && this.DSChiTietTB[0].hasOwnProperty('NGAYNHAP') ? true : false
      // }
      
    },
    // LAY_DS_THUEBAO
    async LAY_DS_THUEBAO(data){
      await this.getDSThueBaoDangCap({
        "phanVungId": this.$auth.getPhanVungID(),
        "idKho":  this.p_kho_id,
        "denNgay": this.f_DateToString(this.p_denngay),
        "idDonVi": this.p_donvi_id,
        "idKhoThat": this.p_kho_id,
        "checkKho": this.p_tatca_kho ? 1 : 0,
        "maVatTu": data.MA_VT,
        "loHang": data.LOHANG
      })
    },
    rowDataBound(args){
      let soLuong = parseInt(args.data.SOLUONG);
      let daCap = parseInt(args.data.DACAP)
      let conLai = parseInt(args.data.CONLAI)
      
      if ((soLuong - daCap) != conLai){
        args.row.bgColor = '#ED5050'
      }
    },
    async btnTimKiem_ButtonClick(data){
      this.setDSThueBaoDangCap([])
      this.tabThongTin = true
      await this.LAY_DS_THUEBAO(data)
    },
    async vbtnClose_Click(){
      this.tabThongTin = false
    },
    async openDialog(){
      this.$refs.popup_tracuuvattu_trongkho.show();
      await this.frmTraCuuKho_Load()
    },
    async frmTraCuuKho_Load(){
      this.clearData();
      this.selectedKho = ""
      await this.getDSKhoTheoNV({
        nhanVienId: this.$auth.getNhanVienID(),
        donViId: this.$auth.getDonViID(),
      })
              
      this.p_kho_id = this.DSKhoTheoNV.length ? this.DSKhoTheoNV[0].KHO_ID : 0
      this.p_donvi_id = this.DSKhoTheoNV.length ? this.DSKhoTheoNV[0].DONVI_ID : 0
      this.p_denngay = new Date();
    },
    async hideComboKho(){
      this.comboKho = false
    }
  },
created: async function() {
    this.clearData();
    await this.frmTraCuuKho_Load()
    // NAP_DS_KHO
    
    // this.$eventHub.$on('btnTimKiem_ButtonClick',async (data)=>{
    //     this.tabThongTin = true
    //     await this.LAY_DS_THUEBAO(data)
    // })
    // this.$eventHub.$on('vbtnClose_Click',(e)=>{
    //    this.tabThongTin = false
    // })

    // ĐỨC comment tự động show popup khi created
    //this.$refs.popup_tracuuvattu_trongkho.show()
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>