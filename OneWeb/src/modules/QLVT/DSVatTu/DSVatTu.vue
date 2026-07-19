<template src='./DSVatTu.html'></template>
<style scoped src='./DSVatTu.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import DSVatTuAPI from  '../api/DSVatTuAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/dsvattu'
import select2 from '@/components/Select2.vue'
import { Freeze, Edit, Group, Page, Filter, Sort, Resize} from "@syncfusion/ej2-vue-grids";
import { Query,Predicate } from '@syncfusion/ej2-data';
import EventBus from '@/utils/eventBus'
import XLSX from "xlsx";
export default {

  components: {breadcrumb, appSelect2: select2,XLSX },
  name: "DSVatTu",
  mounted() {
  },
  provide: {
    grid: [ Freeze, Edit,Group, Page,Filter,Sort,Resize ]
  },
  computed: {
    ...mapState("dsvattu", statePropertyName),
    ...mapGetters("dsvattu", getterName),
    
  },
  data() {
    return {
        header: {
            title: "Danh sách vật tư",
            list: [
            { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
            {
                name: "Lắp đặt mới",
                link: { name: "Ui.buttons" },
            },
            ],
        },

        arrayImportFile: {
            filexcel:null,
            ok: false,
            dsImportExcel: [],
            dataExcel: [],
            ds_file: []
        },
        animationSettings: { effect: 'Zoom' },
        target: '.main-wrapper',
        filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
        format:{ format:'N3' , useGrouping: true},
        KEY_INDEX : 0,
        templateOptions : {
          create: function (args) {
            if(['STT','btnXoaVT','btnEditTbi'].includes(args.column.field)){
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
        MA_VT_Header: function() {
          return {
            template : Vue.component('MA_VT_Header', {
              template : `
                 <div class="check-action" @click.prevent="btnCheckAll_MA_VT($event)">
                    <input type="checkbox" class="check">
                    <span class="name">Mã vật tư</span>
                </div>`,
              data: function () { return { data: {checkAll: false} }; },
              methods:{
                      btnCheckAll_MA_VT($event){
                        //   $event.stopPropagation()
                          $event.preventDefault()
                        // EventBus.emit('btnCheckAll_MA_VT',this.data)
                      }

                    }
            })  // end of template
          }
        },
        btnThemVTTemplate: function () {
            return {
                template: Vue.component('GoSeriTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" style="background-color: blue !important" @click="btnThemVT_ButtonClick">
                                  <span class="fa fa-plus"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnThemVT_ButtonClick(){
                        // EventBus.emit('btnThemVT_ButtonClick',this.data)                        
                        await this.$parent.$parent.$parent.$parent.btnThemVT_ButtonClick(this.data)
                      }

                    }
                })
            }
        },
        btnXoaVTTemplate: function () {
            return {
                template: Vue.component('btnXoaVTTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" style="background-color: blue !important" @click="btnXoaVT_ButtonClick">
                                  <span class="fa fa-minus"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnXoaVT_ButtonClick(){
                        await this.$parent.$parent.$parent.btnXoaVT_ButtonClick(this.data)
                      }

                    }
                })
            }
        },
        pageSettings: { pageSizes: ['10','20','50','100','All'], pageSize: 10 },
        dtVatTu: [], //gridDS
        dtThietBi: [],
        dtLuuVT: [],

        tagForm: "",
        XacNhan: false,
        dt: [],
        vkho_id: 0,
        checkAll: false,
        kieu:0,
        ma_md: "",
        _kt:0,
        kieu_loc:0,
        vma_md: "",

        mavt:"",
        tenvt:"",
        loaitbi:"",
        loaitbi_id:"",

        quyen: false,
        dt_file: [],
        thamso_sinhlohang: false,
        vchungtu_id: "",
        
        ds_lohang:[], // cboLoHang
        cboLoHang: [],
        Tag: "",
        cboChatLuong_ID: [],

        visible: {
            LOHANG: true,
            tsbtnDocFile: true,
            tsbtnFileMau: true,
            colMaTheTS: true,
            colGhiChu: true,
            colNguon_goc: true,
            colDongTbi: true,
            colHeThong: true,
            colChatLuong_ID: true,
            layoutControlItem3: true
        },
        MUCDICH_CCDC:{
            NHAP: "CCDC.N",
            CHUYEN: "CCDC.C",
            SUACHUA: "CCDC.S",
            THUHOI: "CCDC.T",
            THANHLY: "CCDC.TL",
            DAUKY: "CCDC.DK"
        },

        gridControl1: [],
        gridVatTu: [],
        gridDS: [],
        indexVatTu: -1,
        LoHangTemplate: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>                         
                          <input type="text" class="form-control" v-model="LOHANG" @click="SelectLoHang" readonly>
                      </div>`,                        
            data () { 
              return { 
                data: {},
                LOHANG: ""
              }; 
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              }
            },
            created:async function () {
              this.LOHANG = this.data.LOHANG    
			},
            methods: {
              SelectLoHang() {               
                EventBus.$emit("onChangeLoHang",this.data);
              },
            },
          })
        }
      },      

    };
  },
  methods: {
    ...mapActions("dsvattu", actionName),
    ...mapMutations("dsvattu", mutationName),
    
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    resetParams(){
        this.dtVatTu = []
        this.dtLuuVT = []
        this.gridControl1 = []
        this.gridVatTu = []
        this.gridDS = []
    },
    async Open(){
      this.resetParams()
      this.$refs.popup_frmDSVatTu.show();
    },
    async Close(){
        await this.$parent.XacNhan_frmDSVatTu()
        this.$refs.popup_frmDSVatTu.hide();
    },
    async frmDSVatTu_Load(){
        this.loading(true)
        this.Tag ? this.tagForm = "" : this.tagForm = this.Tag
        if(this.tagForm.toString() == "10"){
            this.dtVatTu = await this.getDSVatTuTheoThamSo({thamSo: 1})
        }
        else{
            this.ds_lohang = await this.getDSLoHang()
            this.ds_lohang.shift();
            this.ds_lohang.unshift({loHang: "", prefix: "1", donGia:"0"})
            this.cboLoHang = this.ds_lohang
            console.log(this.cboLoHang , " = this.cboLoHang")
            await this.load_grid()
            if (this.ma_md.toString() == "1.N" || this.ma_md.toString() == "N.VT"){
                this.visible.LOHANG = true
            }
            else{
                this.visible.LOHANG = false
            }
        
            this.visible.layoutControlItem3 = false
            if (this.ma_md == "1.N" || this.ma_md == "1.NL" || this.ma_md == "N.KDV" || this.ma_md == "N.VT" || this.ma_md == "1.3"
                    || this.ma_md == "1.1.5" || this.ma_md == "1.4" || this.ma_md == "1.5" || this.ma_md == "1.NBH"){
                this.visible.tsbtnDocFile = true
                this.visible.tsbtnFileMau = true
            }

            if(this.quyen){
                this.visible.colMaTheTS = true
                this.visible.colGhiChu = true
                this.visible.colNguon_goc = true
                this.visible.colDongTbi = true
                this.visible.colHeThong = true
                this.visible.colChatLuong_ID = true
            }
            
            this.cboChatLuong_ID = await this.getDanhMucChatLuong()
        }
        this.loading(false)
    },
    async load_grid(){
        if (this.dtLuuVT.length){
            if(this.kieu_loc == 1){
                this.dtVatTu = await this.getDSVatTuTheoThamSo({thamSo: 2})
                this.gridDS = this.dtVatTu
            }
            else{
                if (!this.dtVatTu.length){
                    this.dtVatTu = await this.getDSVatTuTheoThamSo({thamSo: 1})
                }
            }

            if (this.kieu_loc == 1 && this.ma_md != this.MUCDICH_CCDC.DAUKY){
                var q = this.dtVatTu.filter(a=>parseFloat(a.SL) > 0)
                if (q.length){
                    this.dtVatTu = q
                }
                else{
                    this.dtVatTu = []
                }
            }
            else if (this.kieu_loc == 1 && this.ma_md == this.MUCDICH_CCDC.DAUKY){
                var q = this.dtVatTu.filter(a=>parseFloat(a.SL) > 0 || a.HIENTHI.toString() == "0")
                if (q.length){
                    this.dtVatTu = q
                }
                else{
                    this.dtVatTu = []
                }
            }

            for(let i in this.dtVatTu){
                var r = this.dtVatTu[i]
                for(let j in this.dtLuuVT){
                    var rt = this.dtLuuVT[j]
                    if (rt.VATTU_ID.toString() == r.VATTU_ID.toString()){
                        this.dtVatTu.splice(i,1)
                        break;
                    }
                }
            }
            this.gridControl1 = this.dtVatTu
            for(let e of this.dtLuuVT){
                e.KEYROW = this.KEY_INDEX
                this.KEY_INDEX += 1
            }  
            this.gridVatTu = this.dtLuuVT          
            this.showData_gridVatTu()

        }
        else{
            if(this.kieu_loc == 1){
                this.dtVatTu = await this.getDSVatTuTheoThamSo({thamSo: 2}) 
            }
            else{
                this.dtVatTu = await this.getDSVatTuTheoThamSo({thamSo: 1}) 
            }

            if (this.kieu_loc == 1 && this.ma_md != this.MUCDICH_CCDC.DAUKY){
                let q = this.dtVatTu.filter(a=>parseFloat(a.SL) > 0)
                if(q.length){
                    this.dtVatTu = q
                }
                else{
                    this.dtVatTu = []
                }
            }
            else if(this.kieu_loc == 1 && this.ma_md == this.MUCDICH_CCDC.DAUKY){
                let q = this.dtVatTu.filter(a=>parseFloat(a.SL) > 0 || a.HIENTHI.toString() == "0")
                if(q.length){
                    this.dtVatTu = q
                }
                else{
                    this.dtVatTu = []
                }
            }
            this.gridControl1 = this.dtVatTu
            this.dtLuuVT = []
            // this.dtLuuVT = JSON.parse(JSON.stringify(this.dtVatTu))
        }
        

        if(this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('LOAI_TBI')){
            this.visible.colLoaiTBI = true
        }
        if(this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('TEN_VT_CHA')){
            this.visible.colVTCha = true
        }
        if(this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('GHICHU')){
            this.visible.colGhichu_VT = true
        }
        if(this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('DONGIA_VT')){
            this.visible.colDongia = true
        }
        if(this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('CHATLUONG')){
            this.visible.colCL = true
        }
    },
    showData_gridVatTu(){
        this.gridVatTu.forEach(a=>{            
            a.DONGIA = a.DONGIA !== undefined && a.DONGIA !== null ? parseFloat(a.DONGIA) : 0
            a.SOLUONG = a.SOLUONG !== undefined && a.SOLUONG !== null  ? parseFloat(a.SOLUONG) : 0
            a.TIEN = a.TIEN !== undefined && a.TIEN !== null ? parseFloat(a.TIEN) : 0
            
            if (a.hasOwnProperty('SL')){
                a.SL = a.SL !== undefined && a.SL !== null  ? parseFloat(a.SL) : 0
               
            }
            else{
                a.SL = 0
            }
            a.SOTHANG_PB = a.SOTHANG_PB !== undefined && a.SOTHANG_PB !== null ? parseInt(a.SOTHANG_PB) : 0
           
        })
    },
    async KiemTra_VT(dt){
        if(this.kieu_loc!=1){
            for( let i in dt){
                let dr = dt[i]
                let d =  await this.getDSLoHangTheoMa({loHang: dr.LOHANG})
                
                if (d.length){
                    if(d[0].toString() != "1"){
                        let q = dt.filter(a=>a.VATTU_ID == dr.VATTU_ID && a.LOHANG == dr.LOHANG )                   
                        if(q.length > 1){
                            return "Với lô hàng " + dr.LOHANG + " không tự sinh ko thể chọn nhiều vật tư trong 1 chứng từ"
                        }
                    }
                }
                else{
                    if (this.ma_md == "1.N" || this.ma_md == "1.NL" || this.ma_md == "N.KDV" || this.ma_md == "N.VT" ||  this.ma_md == "N.DK" ||
                        ((this.ma_md == "1.3" || this.ma_md == "N-X.DCMSL" || this.ma_md == "1.4") && this.quyen))// CƯờng thêm 1.3
                    {
                        if (!this.thamso_sinhlohang){
                            let q = dt.filter(a=>a.VATTU_ID == dr.VATTU_ID && a.DONGIA == dr.DONGIA )
                            if (q.length > 1){
                                return "Đang có " + q.length.toString() + " mã vật tư  giống nhau cùng đơn giá trong chứng từ"
                            }
                        }
                    }
                    else{
                        let q = dt.filter(a=>a.VATTU_ID == dr.VATTU_ID )
                        if (q.length > 1){
                            return "Số lượng vật tư trong chứng từ chỉ được phép có 1 mã vật tư"
                        }
                    }
                }
            }  
        }

        if(this.visible.LOHANG){
            let lengt = this.dt.filter(a=>a.CHON == 's1')          
            
            if (lengt.length){
                for(let dr of lengt){   
                    console.log()               
                    if (dr.LOHANG == null || dr.LOHANG == ""){
                        continue
                    }                    
                    let check = this.ds_lohang.filter(a=>a.loHang == dr.LOHANG && a.prefix.toString() == "0")                  
                    if (check.length == 0){
                        continue
                    }
                    if (check[0].donGia.toString() != dr.DONGIA.toString()){
                        return "Lô hàng " + dr.LOHANG + " phải có đơn giá là " + check[0].donGia
                    }
                }
            }
        }
        return "1"
    },
    async tsbtnHoanThanh_Click(){
        if (this.tagForm == "10"){
            if(!this.gridDS.length){
                this.$toast.error("Danh sách vật tư rỗng !")
                return
            }

            // let a = this.$refs.TableGridDS.getSelectedRecords()
            let a = this.gridVatTu
            if (a.length == 0 ){
                this.$toast.error("Chưa chọn vật tư nào!")
                return
            }
            this.mavt = a[0].MA_VT
            this.tenvt = a[0].TEN_VT
            this.loaitbi = a[0].LOAI_TBI
            this.loaitbi_id = a[0].LOAITBI_ID
        }
        else{
            // tlstpTop.Focus()
            // let a = this.$refs.TableGridVatTu.grid.getSelectedRecords()
            let a = this.gridVatTu
            if (a.length == 0){
                this.$toast.error("Chưa chọn vật tư nào!")
                this.XacNhan = false
                return
            }
            this.dt = a

            // Kiểm tra
            let kq = await this.KiemTra_VT(this.dt)
            if (kq.toString() != "1"){
                this.$toast.error(kq)
                this.XacNhan = false;
                return;
            }

            let kt = this.dt.filter(p=>parseFloat(p.SOLUONG) > parseFloat(p.SOLUONG)) // không hiểu, nhưng server đang là như vậy
            let tongtien = this.dt.reduce((sum,b)=>sum + parseFloat(b.TIEN),0)
            if (kt.length){
                this.$toast.error("Không được xuất quá lượng tồn của vật tư !")
                this.XacNhan = false
                return
            }
            if (this.ma_md != "1.N" && this.kieu_loc != 1 && this.ma_md != "1.NBH" && this.ma_md != "N.DK" && this.ma_md != "1.NL" && this.ma_md != "1.3" && this.ma_md != "N.KDV"
                    && this.ma_md != "N.VT" && this.ma_md != "N_MS" && this.ma_md != "N.KDV_DG"){
                /* tăng từ 50 => 100m --- 132 / QĐ-VNPT-KHĐT
                * lấy file văn bản ở Jira https://cntt.vnpt.vn/browse/IT360-274840
                */
                if (tongtien > 100000000){
                    if(this.$auth.getPhanVungID().toString() != '28'){ //ttnd.tentat != "HCM"
                        this.$toast.error("Đơn hàng lẻ không được nhập quá 100,000,000")
                        this.XacNhan = false
                        return
                    }
                }
            }
            if (this.kieu_loc == 1){
                if (this.ma_md != this.MUCDICH_CCDC.DAUKY){
                    let k = this.dt.filter(z=>z.DONGIA > 30000000 && z.SOLUONG > 1)
                    if (k.length){
                        this.$toast.error("Những công cụ có đơn giá >= 30,000,000 phải có số lượng =1")
                        this.XacNhan = false
                        return
                    }
                }
            }

            let s = this.dt.filter(z=>parseFloat(z.SOLUONG) <= 0)
            if (s.length){
                this.$toast.error("Số lượng không thể <= 0. Bạn hãy kiểm tra lại!")
                this.XacNhan = false
                return
            }
        }
        this.XacNhan = true
        this.Close()
    },
    async gridViewVatTu_ValidateRow(arg){
        //modify cho phù hợp vs web
     
        let gridViewVatTu_EditRow = this.gridVatTu.find(a=>a.KEYROW == arg.rowData.KEYROW )        
        if (arg.columnName == 'SOLUONG'){
            if (arg.value < 0){                
                this.$toast.error("Số lượng cấp phải lớn hơn 0")
                this.$refs.TableGridVatTu.grid.closeEdit()
                return
            }
            else if (arg.value == 0){
                
                gridViewVatTu_EditRow.TIEN = 0
                gridViewVatTu_EditRow.DONGIA = 0
                gridViewVatTu_EditRow.SOLUONG = 0 // bổ sung thêm, cellSave ko save tới datasource
                gridViewVatTu_EditRow.CHON = 's0'
                this.dtVatTu.push(gridViewVatTu_EditRow)
                this.gridControl1.push(gridViewVatTu_EditRow)               
                this.dtLuuVT_temp = []
                for(let e of this.dtLuuVT){
                    if(!(e.KEYROW == gridViewVatTu_EditRow.KEYROW)){
                        this.dtLuuVT_temp.push(e)
                    }
                }
                for(let e of this.dtLuuVT_temp){
                    e.KEYROW = this.KEY_INDEX
                    this.KEY_INDEX += 1
                }  
                this.dtLuuVT = this.dtLuuVT_temp
                this.gridVatTu = this.dtLuuVT_temp             
                await this.load_grid()
            }
        }
        await this.gridViewVatTu_CellValueChanged(arg,gridViewVatTu_EditRow)
    },
   
    async gridViewVatTu_CellValueChanged(arg,gridViewVatTu_EditRow){
        
        let soluong = parseFloat(arg.rowData.SOLUONG)
        let dongia = parseFloat(arg.rowData.DONGIA)
        let tien = parseFloat(arg.rowData.TIEN)
        let vattu_id = arg.rowData.VATTU_ID
        
        // if (arg.columnName == 'LOHANG'){
            
        // }
        // else{

        // }

        if (arg.columnName == 'SOLUONG'){
            tien = arg.value * dongia // arg.value new edited value 
            gridViewVatTu_EditRow.TIEN = tien
            gridViewVatTu_EditRow.SOLUONG = arg.value//
            gridViewVatTu_EditRow.CHON = 's1'
        }
        if (arg.columnName == 'DONGIA'){
            tien = arg.value * soluong // arg.value new edited value 
            gridViewVatTu_EditRow.TIEN = tien
            gridViewVatTu_EditRow.DONGIA = arg.value//
            gridViewVatTu_EditRow.CHON = 's1'
        }
        if (arg.columnName == "HAN_BH"){
            gridViewVatTu_EditRow.HAN_BH = arg.value ? this.f_DateToString(arg.value) : ""
        }
        this.$refs.TableGridVatTu.grid.refreshColumns()
        

    },
    gridView1_CellValueChanged(){

    },
    gridView1_ValidateRow(){

    },
    async gridView1_MouseDown(row){
        row.data.CHON = 's1'
        this.dtLuuVT.push(row.data)
        for(let e of this.dtLuuVT){
            e.KEYROW = this.KEY_INDEX
            this.KEY_INDEX += 1
        }        
        this.gridVatTu = this.dtLuuVT
        this.dtVatTu.splice(row.rowIndex,1)
        this.gridControl1 = this.dtVatTu
        await this.load_grid()
        this.$refs.TableGridVatTu.grid.refreshColumns()
        // this.$refs.TableGridControl1.refreshColumns()
    },
    async btnThemVT_ButtonClick(row){
        // try {
            if (this.ma_md == "1.N" || this.ma_md == "1.NL" || this.ma_md == "N.KDV" || this.ma_md == "N.VT" || this.ma_md == "N.DK" || 
                ((this.ma_md == "1.3" || this.ma_md == "N-X.DCMSL" || this.ma_md == "1.4") && this.quyen)){
                row.index = this.gridVatTu.length
                this.dtLuuVT.push(row)
                await this.load_grid()
            }
            else{
                this.$toast.error("Mã mục đích bạn chọn không có quyền thêm vật tư")
            }
        // } catch (error) {
        //     this.$toast.error("Mã mục đích bạn chọn không có quyền thêm vật tư")
        // }
    },

   
    async ShowDialog(){
        this.$refs.popup_frmDSVatTu.show()
    },
    async OpenDialog(){
        // this.resetParams()
        await this.frmDSVatTu_Load()
    },
    async btnXoaVT_ButtonClick(row){
        this.dtLuuVT.splice(parseInt(row.index),1)
        for(let e of this.dtLuuVT){
            e.KEYROW = this.KEY_INDEX
            this.KEY_INDEX += 1
        }  
        this.gridVatTu = this.dtLuuVT

        this.dtVatTu.push(row)
        this.gridControl1 = this.dtVatTu

        this.$refs.TableGridVatTu.grid.refreshColumns()
        this.$refs.TableGridControl1.grid.refreshColumns()
    },
    onChangeLoHangHandler(value){   
        console.log(value , " = value") 
      this.indexVatTu = value.index
      this.$bvModal.show('popupLoHang');
    },
    gridLoHang_FocusedRowChanged_sender(row){   
        console.log(this.indexVatTu , " = this.indexVatTu")     
        if(this.indexVatTu > -1 && this.gridVatTu.length > this.indexVatTu ){           
            this.gridVatTu[this.indexVatTu].LOHANG = row.loHang           
        }   
        this.$refs.TableGridVatTu.grid.refreshColumns()        
    },
    // tsbtnFileMau_Click(){
    //   const downloadLink = document.createElement("a");     
    //   downloadLink.href =  this.$auth.getMaTinh() == "HCM" ? "http://10.70.52.164:8081/FILES/NET/VATTU/Mau_nhap_le_vattu.xls" : "http://10.70.52.164:8081/FILES/HCM/VATTU/Mau_NhapLeExcel_637189102398256929.xls"
    //   downloadLink.download = 'Mau_nhap_le_vattu.xls';    
    //   downloadLink.click();    
    // },
    // Hard code từ link file mẫu do Chrome chặn download file
    async tsbtnFileMau_Click(){

        let dt = [
            {
                'MA_VT': "11.01.01.11.00.30",
                'SOLUONG': 5,
                'DONGIA': 11111,          
            },
            {
                'MA_VT': "BTS.HCM154.81.2011.2",
                'SOLUONG': 20,
                'DONGIA': 23456,          
            },
        ]
        
        let dt_hcm = [
            {
                'MA_VT': "'01514063001314.001",
                'SOLUONG': 2,
                'DONGIA': 0, 
                'MATHETS' :  "'1514063001314",
                'GHICHU' : '2; Nhập Thu hồi theo VB: 3028/QLDA_20/08/2020 & TB: 470/TB-VTTB-TH_09/07/2020 & QĐ: 110/QĐ-TTVTTB ngày 09/07/2020 V/v thu hồi thiết bị tại trạm BTS Q10072; Thẻ TS: 1514063001314',  
                'CHATLUONG' :  'TH_vụn'  
            },
            {
                'MA_VT': "'01514063001314.002",
                'SOLUONG': 26,
                'DONGIA': 0, 
                'MATHETS' :  "'1514063001314",
                'GHICHU' : '26; Nhập Thu hồi theo VB: 3028/QLDA_20/08/2020 & TB: 470/TB-VTTB-TH_09/07/2020 & QĐ: 110/QĐ-TTVTTB ngày 09/07/2020 V/v thu hồi thiết bị tại trạm BTS Q10072; Thẻ TS: 1514063001314',  
                'CHATLUONG' :  'TH_vụn'  
            },
            {
                'MA_VT': "'01514063001314.003",
                'SOLUONG': 22,
                'DONGIA': 0, 
                'MATHETS' :  "'1514063001314",
                'GHICHU' : '23; Nhập Thu hồi theo VB: 3028/QLDA_20/08/2020 & TB: 470/TB-VTTB-TH_09/07/2020 & QĐ: 110/QĐ-TTVTTB ngày 09/07/2020 V/v thu hồi thiết bị tại trạm BTS Q10072; Thẻ TS: 1514063001314',  
                'CHATLUONG' :  'TH_vụn'  
            },
            {
                'MA_VT': "'01514063001314.004",
                'SOLUONG': 2,
                'DONGIA': 0, 
                'MATHETS' :  "'1514063001314",
                'GHICHU' : '2; Nhập Thu hồi theo VB: 3028/QLDA_20/08/2020 & TB: 470/TB-VTTB-TH_09/07/2020 & QĐ: 110/QĐ-TTVTTB ngày 09/07/2020 V/v thu hồi thiết bị tại trạm BTS Q10072; Thẻ TS: 1514063001314',  
                'CHATLUONG' :  'TH_vụn'  
            },
            {
                'MA_VT': "'01514063001314.005",
                'SOLUONG': 1,
                'DONGIA': 0, 
                'MATHETS' :  "'1514063001314",
                'GHICHU' : '1; Nhập Thu hồi theo VB: 3028/QLDA_20/08/2020 & TB: 470/TB-VTTB-TH_09/07/2020 & QĐ: 110/QĐ-TTVTTB ngày 09/07/2020 V/v thu hồi thiết bị tại trạm BTS Q10072; Thẻ TS: 1514063001314',  
                'CHATLUONG' :  'TH_vụn'  
            },
        ]
        let dt_excel = this.$auth.getMaTinh() == "HCM" ? dt_hcm : dt
        let data1 = XLSX.utils.json_to_sheet(dt_excel);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data1, "Sheet1"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "Mau_nhap_le_vattu.xls");
    },
    async tsbtnDocFile_Click(){
      document.getElementById('fileUploadDSVT').click()
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error("Chưa chọn file nào!")
        return false
      }
    
      if (
        this.arrayImportFile.filexcel.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" &&
        this.arrayImportFile.filexcel.type != "application/vnd.ms-excel"
      ) {
        this.$toast.error("File không phải định dạng xlsx hoặc xls!")
        return false
      }
      return true
    },
    async onChangeDSVT() {
      this.arrayImportFile.filexcel = this.$refs.fileUploadDSVT.$refs.input.files[0]
      
      try {
        this.loading(true)
        if (this.checkfile()) {       
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: "binary" })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            // retrieve headers (i.e. remove first row)
            let headers  = data.shift() // bỏ dòng header đầu tiên trong data
            
            // then build the json for each row
            var result = data.map(function(a) {
                var jsonRow = {};
                a.forEach(function(cellValue, cellIndex) { 
                    jsonRow[headers[cellIndex]] = cellValue;
                });
                headers.forEach(b=> {
                  if (!Object.keys(jsonRow).includes(b)){
                    jsonRow[b] = ''
                  }
                })
                return jsonRow;
            });
            
            console.log(result , " = result")
            if(result.length == 0){
              this.$toast.error("File không có dữ liệu")
              return
            }else{
                if(this.$auth.getMaTinh() == "HCM"){
                    if(!("MA_VT" in result[0] && "SOLUONG" in result[0] && "DONGIA" in result[0] && "MATHETS" in result[0] && "GHICHU" in result[0] && "CHATLUONG" in result[0])){
                        this.$toast.error("Cấu trúc file phải có 6 cột MA_VT, SOLUONG, DONGIA, MATHETS (Có thể để trống dữ liệu), GHICHU, CHATLUONG")
                        return
                    }
                }else{
                    if(!("MA_VT" in result[0] && "SOLUONG" in result[0] && "DONGIA" in result[0] )){
                        this.$toast.error("Cấu trúc file phải có 3 cột MA_VT, SOLUONG, DONGIA!")
                        return
                    }
                }
              
            }
            this.KiemTraDuLieu(result)
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
          
        }
      } catch (error)  {
        this.$toast.error("Có lỗi xảy ra khi upload file!")
      } finally {
        this.$refs.fileUploadDSVT.reset()
        this.loading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    KiemTraDuLieu(dt_file){
        for(let e of dt_file){
            if(e.MA_VT.trim() == ""){
                continue
            }
            let vattu = this.dtVatTu.filter(a=> a.MA_VT == e.MA_VT) 
           
            if(vattu.length == 0){
                this.$toast.error("Có vật tư trong file không có trong danh sách vật tư !")
                return
            }
            let tien = Math.round(parseFloat(e.SOLUONG) * parseFloat(e.DONGIA) * 1000)/1000 // làm tròn 3 số thập phân
            if(this.$auth.getMaTinh() == "HCM"){
                this.dtLuuVT.push({
                    CHON: "s1",
                    VATTU_ID: vattu[0].VATTU_ID,
                    LOHANG : vattu[0].LOHANG,
                    MA_VT : e.MA_VT,
                    TEN_VT: vattu[0].TEN_VT,
                    DVI_TINH: vattu[0].DVI_TINH,
                    SOLUONG : parseFloat(e.SOLUONG),
                    DONGIA : parseFloat(e.DONGIA),
                    TIEN : parseFloat(tien),
                    LOAI_TBI : vattu[0].LOAI_TBI,
                    HAN_BH : vattu[0].HAN_BH,
                    LOAITBI_ID : vattu[0].LOAITBI_ID,
                    SOTHANG_PB : vattu[0].SOTHANG_PB,
                    LOAIVT_ID : vattu[0].LOAIVT_ID,
                    SL : vattu[0].SL,
                    HIENTHI : vattu[0].HIENTHI,
                    MATHETS: e.MATHETS,
                    GHICHU: e.GHICHU,
                    CHATLUONG: e.CHATLUONG,
                })
            }else{
                this.dtLuuVT.push({
                    CHON: "s1",
                    VATTU_ID: vattu[0].VATTU_ID,
                    LOHANG : vattu[0].LOHANG,
                    MA_VT : e.MA_VT,
                    TEN_VT: vattu[0].TEN_VT,
                    DVI_TINH: vattu[0].DVI_TINH,
                    SOLUONG : parseFloat(e.SOLUONG),
                    DONGIA : parseFloat(e.DONGIA),
                    TIEN : parseFloat(tien),
                    LOAI_TBI : vattu[0].LOAI_TBI,
                    HAN_BH : vattu[0].HAN_BH,
                    LOAITBI_ID : vattu[0].LOAITBI_ID,
                    SOTHANG_PB : vattu[0].SOTHANG_PB,
                    LOAIVT_ID : vattu[0].LOAIVT_ID,
                    SL : vattu[0].SL,
                    HIENTHI : vattu[0].HIENTHI
                })
            }
        }
        let listVattuExcel = []
        let newDSVatTu = []
        for(let e of this.dtLuuVT){
            listVattuExcel.push(e.MA_VT)
        }
       
        for(let e of this.dtVatTu){
            if(!listVattuExcel.includes(e.MA_VT)){
                newDSVatTu.push(e)
            }
        }
        this.dtVatTu = newDSVatTu
        this.gridControl1 = this.dtVatTu
        this.gridVatTu = this.dtLuuVT
    }
  },
created: async function() {
    this.clearData();
    EventBus.$on('onChangeLoHang', this.onChangeLoHangHandler);
    // this.resetParams()
    // await this.frmDSVatTu_Load()
  },
  watch: {
    
  },
  destroyed () {
    EventBus.$off("onChangeLoHang", this.onChangeLoHangHandler);
  }
};
</script>