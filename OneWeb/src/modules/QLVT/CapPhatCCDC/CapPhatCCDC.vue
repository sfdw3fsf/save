<template src='./CapPhatCCDC.html'></template>
<style scoped src='./CapPhatCCDC.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import CapPhatCCDCAPI from  '../api/CapPhatCCDCAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/capphatccdc'
import {} from '../store/chonnhanvienccdc'
import select2 from '@/components/Select2.vue'
import { Group, Page, Filter, Sort, Resize} from "@syncfusion/ej2-vue-grids";
import { Query,Predicate } from '@syncfusion/ej2-data';
import ChonNhanVienCCDC from '../ChonNhanVienCCDC'
import ChonChungTuCCDC from '../ChonChungTuCCDC'
import DSVatTu from '../DSVatTu/DSVatTu.vue'
import EventBus from '@/utils/eventBus'
import frmGanThuocTinh_LVT from '../frmGanThuocTinh_LVT'
import {previewPrint} from "@/utils/util"
import XLSX from "xlsx";
export default {

  components: {
    frmGanThuocTinh_LVT,
     breadcrumb, appSelect2: select2,
  ChonNhanVienCCDC,
  ChonChungTuCCDC,
  DSVatTu
   },
  name: "CapPhatCCDC",
  mounted() {
  },
  provide: {
    grid: [ Group, Page,Filter,Sort,Resize ]
  },
  computed: {
    ...mapState("capphatccdc", statePropertyName),
    ...mapGetters("capphatccdc", getterName),
    p_sophieu: {
      get() {
        return this.params.p_sophieu
      },
      set(value) {
        return this.params.p_sophieu = value
      }
    },
    p_ngay_yeucau: {
      get() {
        return this.params.p_ngay_yeucau
      },
      set(value) {
        return this.params.p_ngay_yeucau = value
      }
    },
    p_mucdich_id: {
      get() {
        return this.params.p_mucdich_id
      },
      set(value) {
        return this.params.p_mucdich_id = value
      }
    },
    p_so_chungtu: {
      get() {
        return this.params.p_so_chungtu
      },
      set(value) {
        return this.params.p_so_chungtu = value
      }
    },
    p_ngay_ct: {
      get() {
        return this.params.p_ngay_ct
      },
      set(value) {
        return this.params.p_ngay_ct = value
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
    p_nhanvien_id: {
      get() {
        return this.params.p_nhanvien_id
      },
      set(value) {
        return this.params.p_nhanvien_id = value
      }
    },
    p_radio_capden: {
      get() {
        return this.params.p_radio_capden
      },
      set(value) {
        return this.params.p_radio_capden = value
      }
    },
    p_noidung: {
      get() {
        return this.params.p_noidung
      },
      set(value) {
        return this.params.p_noidung = value
      }
    },
    p_tungay: {
      get() {
        return this.params.p_tungay
      },
      set(value) {
        return this.params.p_tungay = value
      }
    },
    p_denngay: {
      get() {
        return this.params.p_denngay
      },
      set(value) {
        return this.params.p_denngay = value
      }
    },
    p_check_allphieu: {
      get() {
        return this.params.p_check_allphieu
      },
      set(value) {
        return this.params.p_check_allphieu = value
      }
    },
    ma_md:{
      get() {
        let mucDich = this.DSMucDichPhanBo.find(a=>a.mucDichId.toString() == this.params.p_mucdich_id.toString())
        return  mucDich ? mucDich.mucDich.split('-')[0].trim() : ""
      },
    }
  },
  data() {
    return {
        header: {
            title: "CẤP PHÁT CÔNG CỤ DỤNG CỤ",
            list: [
            { name: "Quản lý vật tư", link: { name: "Ui.cards" } },
            {
                name: "Nhập/ xuất/ điều chuyển vật tư",
                link: { name: "Ui.buttons" },
            },
            ],
        },
        MUCDICH_CCDC: {
          NHAP: "CCDC.N",
          CHUYEN: "CCDC.C",
          SUACHUA: "CCDC.S",
          THUHOI: "CCDC.T",
          THANHLY: "CCDC.TL",
          DAUKY: "CCDC.DK"
        },
        params: {
            p_sophieu: "",
            p_ngay_yeucau: new Date(),
            p_mucdich_id: "",
            p_so_chungtu: "",
            p_ngay_ct: new Date(),
            p_donvi_id: "",
            p_nhanvien_id: "",
            p_radio_capden: 1,
            p_noidung: "",

            p_tungay: new Date((new Date()).getTime() - 7*24*60*60*1000),
            p_denngay: new Date(),
            p_check_allphieu: false,

            p_nxcc_id: 0,

        },
        DSNhanVien: [],
        DSDonVi: [],

        grcTongHop: [], // DSTongHopCCDC
        gridVatTu: [], // DSChiTietCCDC
        gridChungTu: [], // DSChungTu

        vnxcc_id: 0,
        filterDonVi: "",
        dtThemVT: [],
        dtThemCTVT: [],
        dtChungTuVT: [],
        dtLuuVT: [],
        dtLuuSr: [],
        dtLuuNotSr: [],
        dtVatTu_Le:[],

        vchon_sp: "",
        vchungtu_goc: 0,
        vchonct_id: 0,
        vttph_id: 0,
        vkho_giao_id: 0,
        vloai_goc: 0,

        readOnly: {
            soPhieu: true,
            soChungTu: true
        },
        enable:{
            NhapMoi: true,
            HuyBo: false,
            XoaVT: false,
            GhiLai: false,
            Xoa: false,
            ChonVT: false,
            HoanThanh: false,
            NhanVien: false,
            NgayCT: true

        },
        dateConfig: {
            default: new Date(),
            format: 'dd/MM/yyyy',
            waterMark : 'Chọn ngày',
            },
        filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
        format:{ format:'N3' , useGrouping: true},
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

        XacNhan: false,
        // vchungtu_id: "",
        pageSettings: { pageSizes: ['10','20','50','100','All'], pageSize: 10 },
        gridVatTu_Columns: [
          {fieldName: 'btnXoaVT',width:'70', headerText: 'Xóa' , textAlign: 'Left', allowFiltering: true, template: function () {
            return {
                template: Vue.component('XoaVTTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click='btnTimKiem_ButtonClick'>
                                  <span class="fa fa-close"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnTimKiem_ButtonClick(){
                        await this.$parent.$parent.$parent.btnXoaVT_ButtonClick(this.data)
                      }

                    }
                })
            }
        }},
          {fieldName: 'MA_VT', headerText: 'Mã vật tư' , textAlign: 'Left', allowFiltering: true},
          {fieldName: 'TEN_VT', headerText: 'Tên vật tư' , textAlign: 'Left', allowFiltering: true},
          {fieldName: 'DONVI_NHAN', headerText: 'Đến đơn vị' , textAlign: 'Left', allowFiltering: true},

          {fieldName: 'DVI_TINH' , headerText: 'ĐVT' , textAlign: 'Left', allowFiltering: true},
          {fieldName: 'NHANVIEN_NHAN', headerText: 'Đến nhân viên' , textAlign: 'Left', allowFiltering: true},
          {fieldName: 'SERIAL' , headerText: 'SERIAL' , textAlign: 'Left', allowFiltering: true},
          {fieldName: 'MA_TBI' , headerText: 'Tài sản cố định', textAlign: 'Left', allowFiltering: true},

          {fieldName: 'DONGIA' , headerText: 'Đơn giá' , textAlign: 'Right',format: 'N3', type: 'number', allowFiltering: true},
          {fieldName: 'SOTHANG_PB' , headerText: 'Phân bổ(tháng)' , textAlign: 'Right', allowFiltering: true},
          {fieldName: 'TIEN_PB' , headerText: 'Tiền phân bổ' , textAlign: 'Right',format: 'N3', type: 'number', allowFiltering: true},
          {fieldName: 'btnEditTbi' ,width: '70', headerText: 'Thuộc tính' , textAlign: 'Left', allowFiltering: true, template: function () {
            return {
                template: Vue.component('EditTbiTemplate', {
                    template: `<button class="btn pad4 lh14 w60 btn-outline-primary" @click="btnTimKiem_ButtonClick">
                                  <span class="icon fa fa-check"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnTimKiem_ButtonClick(){
                        await this.$parent.$parent.$parent.btnEditTbi_ButtonClick(this.data)
                      }

                    }
                })
            }
        }}, 
        ],
        arrayImportFile: {
          filexcel:null,
          ok: false,
          dsImportExcel: [],
          dataExcel: [],
          ds_file: []
        },
        gridviewChungTu_FocusedRow: {}
    };
  },
  methods: {
    ...mapActions("capphatccdc", actionName),
    ...mapMutations("capphatccdc", mutationName),
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY hh:mm:ss'){
      return moment(value,format).toDate();
    },
    f_AddMonth(value,number,format){
      return moment(value).add(number,'months').format(format)
    },
    async btnChonNV_Click(){
      await this.CHONVATTU_LE()
    },
    //hot key
    ProcessCmdKey(key){
        
    },
    // tách 2 func, 1 -> Open popup frmChonNV_CCDC, 2-> func xử lý khi xác nhận
    // async Open_frmChonNV_CCDC(){
      // Open popup and set data of popup
      
    // },
    async XacNhan_frmChonNV_CCDC(){
      // try {
        var frm = this.$refs.chonNhanVienCCDC
        
        if (frm.xacnhan){
          this.dtThemVT = []
          this.dtThemCTVT = []
          
          for(var i in frm.ds_capphat){
            var item = frm.ds_capphat[i]

            var thietbi_id = parseInt(item.THIETBI_ID)
            this.vchonct_id.toString() == "0" ? thietbi_id = await this.getKey({
                  "keyName" : "THIETBI_ID",
                  "numBlockSize" : 1,
                  "numRetry":  10
                }) : ""
            var tien = parseInt(item.DONGIA)*parseInt(item.SOLUONG)
            var tien_pb = tien / parseInt(item.SOTHANG_PB) / parseInt(item.SOLUONG)

            // Gộp setup row với item
            var row = {}
            row.DONVI_ID = item.DONVI_ID
            row.DONVI_NHAN = item.DONVI_NHAN
            row.NHANVIEN_ID = item.NHANVIEN_ID
            row.NHANVIEN_NHAN = item.NHANVIEN_NHAN

            row.THIETBI_ID = thietbi_id
            row.VATTU_ID = parseInt(item.VATTU_ID)
            row.MA_VT = item.MA_VT
            row.TEN_VT = item.TEN_VT
            row.DVI_TINH = item.DVI_TINH
            row.SOLUONG = parseInt(item.SOLUONG)
            row.DONGIA = parseInt(item.DONGIA)
            row.TIEN_PB = tien_pb

            row.LOAI_TBI = item.LOAI_TBI
            row.LOAITBI_ID = item.LOAITBI_ID
            row.LOAI_VT = item.LOAI_VT
            
            row.GHICHU = item.GHICHU
            row.SERIAL = item.SERIAL
            row.MA_TBI = item.MA_TBI
            row.TINHTRANGTBI_ID = item.TINHTRANGTBI_ID

            

            this.vchonct_id.toString() == "0" ? row.CHUNGTU_ID = "" : row.CHUNGTU_ID = this.vchonct_id
            // Phan bo deu
            if (frm.kieu_pb.toString() == "2"){
              // Phan bo deu

              var vk = 0

              row.THANG_BD = item.THANG_BD

              var tbd = this.f_StringToDate(item.THANG_BD,'MM/YYYY')
              var dttemp = this.f_StringToDate(this.f_DateToString(this.params.p_ngay_yeucau,'MM/YYYY'),'MM/YYYY')
              var kc = (tbd.getFullYear() - dttemp.getFullYear())*12 + tbd.getMonth() - dttemp.getMonth()
              var sothang_pb = item.SOTHANG_PB

              if (kc == 0){
                row.SOTHANG_PB = parseInt(item.SOTHANG_PB)              
                row.THANG_KT = this.f_AddMonth(this.params.p_ngay_yeucau,item.SOTHANG_PB -1,'MM/YYYY')  
                row.TIEN = tien_pb* parseInt(item.SOTHANG_PB)
                this.vchonct_id == 0 ? this.dtThemVT.push(row) : this.dtThemCTVT.push(row)
                vk = 0
              }
              else{
                if (kc > 0){
                  if (this.params.p_ngay_yeucau.getDate() < 15){
                    kc = kc
                    row.SOTHANG_PB = kc
                    row.TIEN = tien_pb * parseInt(item.SOTHANG_PB)
                    row.THANG_KT =  this.f_AddMonth(this.f_StringToDate(this.p_ngay_yeucau,'MM/YYYY'),-1,'MM/YYYY')

                    this.vchonct_id == 0 ? this.dtThemVT.push(row) : this.dtThemCTVT.push(row)
                    vk = 2  
                  }
                  else{
                    kc = kc + 1;
                    row.SOTHANG_PB = kc
                    row.TIEN = tien_pb * parseInt(item.SOTHANG_PB)
                    row.THANG_KT = this.f_DateToString(this.params.p_ngay_yeucau,'MM/YYYY')

                    this.vchonct_id == 0 ? this.dtThemVT.push(row) : this.dtThemCTVT.push(row)
                    vk = 2;
                  }
                }
                if (kc < 0){
                  row.SOTHANG_PB = parseInt(item.SOTHANG_PB)
                  row.TIEN = tien_pb * parseInt(item.SOTHANG_PB)
                  row.THANG_KT = item.THANG_KT

                  this.vchonct_id == 0 ? this.dtThemVT.push(row) : this.dtThemCTVT.push(row)
                  vk = 0 
                }
              }

              if (vk == 2) // 2 row
              {
                var row2 = Object.assign({},row)
                var tien2 = parseInt(item.DONGIA) * parseInt(item.SOLUONG)
                var thang_bd2 = item.THANG_BD
                row2.THANG_BD = this.f_AddMonth(this.f_StringToDate(row.THANG_KT,'MM/YYYY'),1,'MM/YYYY')

                sothang_pb2 = parseInt(item.SOTHANG_PB) - kc
                row2.THANG_KT = this.f_AddMonth(this.f_StringToDate(row2.THANG_BD,'MM/YYYY'),sothang_pb2 - 1,'MM/YYYY')

                row2.SOTHANG_PB = sothang_pb2
                row2.TIEN = tien_pb * (parseInt(item.SOTHANG_PB) - kc)

                this.vchonct_id == 0 ? this.dtThemVT.push(row2) : this.dtThemCTVT.push(row2)

              }
            }

            // phan bo 1 thang
            if(frm.kieu_pb.toString() == "1"){
              
              var thang_bd = item.THANG_BD
              var tbd = this.f_StringToDate(item.THANG_BD,'MM/YYYY')
              var dttemp = this.f_StringToDate(this.f_DateToString(this.params.p_ngay_yeucau,'MM/YYYY'),'MM/YYYY')
              var kc = (tbd.getFullYear() - dttemp.getFullYear())*12 + tbd.getMonth() - dttemp.getMonth()
              var sothang_pb = item.SOTHANG_PB

              let r = Object.assign({},row)
              if (kc == 0){
                r.THANG_BD = item.THANG_BD
                r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
                r.THANG_KT = this.f_AddMonth(this.params.p_ngay_yeucau,item.SOTHANG_PB -1,'MM/YYYY')                
                r.TIEN_PB = tien_pb
                r.TIEN = tien_pb* parseInt(item.SOTHANG_PB)
                
                this.vchonct_id == 0 ? this.dtThemVT.push(r) : this.dtThemCTVT.push(r)
                vk = 0
              }
              else{
                if(kc > 0){
                  if (this.params.p_ngay_yeucau.getDate() < 15){
                    // neu kc>0 thang bd< ngay chung tu thi phan bo thang vao thang 6
                    kc = kc;
                    r.SOTHANG_PB = kc
                    // kc >0 vs case == 2, ko có thang_bd
                    r.THANG_BD = item.THANG_BD
                    r.THANG_KT =  this.f_AddMonth(this.params.p_ngay_yeucau,-1,'MM/YYYY')
                    r.TIEN_PB = 0
                    r.TIEN = tien_pb * parseInt(item.SOTHANG_PB)

                    this.vchonct_id == 0 ? this.dtThemVT.push(r) : this.dtThemCTVT.push(r)
                    vk = 2  
                  }
                  else{
                    kc = kc + 1;
                    r.SOTHANG_PB = kc
                    r.THANG_BD = item.THANG_BD
                    r.THANG_KT = this.f_DateToString(this.params.p_ngay_yeucau,'MM/YYYY')
                    r.TIEN_PB = 0
                    r.TIEN = iten_pb * parseInt(item.SOTHANG_PB)

                    this.vchonct_id == 0 ? this.dtThemVT.push(r) : this.dtThemCTVT.push(r)
                    vk = 2 
                  }
                }

                if (kc < 0){
                  row.SOTHANG_PB = item.SOTHANG_PB
                  row.THANG_KT = item.THANG_KT
                  row.TIEN_PB = tien_pb
                  row.TIEN = tien_pb* parseInt(item.SOTHANG_PB)

                  this.vchonct_id == 0 ? this.dtThemVT.push(row) : this.dtThemCTVT.push(row)
                  vk = 0
                }
              }

              if (vk == 2){
                var r2 = Object.assign({},row)
                r2.TIEN_PB = tien_pb * (kc + 1)
                var tien2 = parseInt(item.DONGIA) * parseInt(item.SOLUONG)
                var thang_bd2 = item.THANG_BD
                r2.THANG_BD = this.f_AddMonth(this.f_StringToDate(row.THANG_KT,'MM/YYYY'),1,'MM/YYYY')
                var sothang_pb2 = 1

                r2.THANG_KT = this.f_AddMonth(row.THANG_BD,parseInt(sothang_pb2)-1,'MM/YYYY')
                r2.SOTHANG_PB = 1
                r2.TIEN = tien_pb * parseInt(item.SOTHANG_PB)

                this.vchonct_id == 0 ? this.dtThemVT.push(r2) : this.dtThemCTVT.push(r2)

                var sothang_pb3 = parseInt(item.SOTHANG_PB) -kc -1 
                if (sothang_pb3 >0){
                  var r3 = Object.assign({},row) // clone 1 object, ko bị dính reference
                  r3.DONVI_ID = item.DONVI_ID
                  r3.DONVI_NHAN = item.DONVI_NHAN
                  r3.NHANVIEN_ID = item.NHANVIEN_ID
                  r3.NHANVIEN_NHAN = item.NHANVIEN_NHAN
                  r3.THIETBI_ID = thietbi_id

                  r3.VATTU_ID = parseInt(item.VATTU_ID)
                  r3.MA_VT = item.MA_VT
                  r3.TEN_VT = item.TEN_VT
                  r3.MA_VT = item.MA_VT
                  r3.DVI_TINH = item.DVI_TINH
                  r3.SOLUONG = item.SOLUONG
                  r3.DONGIA = item.DONGIA
                  r3.TIEN_PB = tien_pb
                  r3.TINHTRANGTBI_ID = item.TINHTRANGTBI_ID
                  var tien3 = parseInt(item.DONGIA) * parseInt(item.SOLUONG)
                  var thang_bd3 = item.THANG_BD
                  r3.THANG_BD = this.f_AddMonth(this.f_StringToDate(r2.THANG_KT,'MM/YYYY'),1,'MM/YYYY')
                  
                  r3.THANG_KT = this.f_AddMonth(this.f_StringToDate(r3.THANG_KT,'MM/YYYY').THANG_BD,parseInt(sothang_pb3) -1, 'MM/YYYY')

                  r3.SOTHANG_PB = sothang_pb3
                  r3.TIEN = tien_pb * (parseInt(SOTHANG_PB) - kc - 1)

                  this.vchonct_id == 0 ? this.dtThemVT.push(r3) : this.dtThemCTVT.push(r3)

                }
              }
            }
          }
          
          this.vchonct_id == 0 ? this.gridVatTu = this.dtThemVT : this.gridVatTu = this.dtThemCTVT
          // await frm.Close()
        }
        
        
      // }
      // catch (error) {
        // this.$toast.error("Lỗi hàm cấp phát CCDC nhân viên")
      // }
    },
    async CHONVATTU_LE(value){
        // try {
      if (this.p_mucdich_id == null || this.p_mucdich_id.toString() == "0"){
        this.$toast.error("Bạn chưa chọn mục đích")
        return
      }

      let ds = this.grcTongHop
      if (ds.length){
        var frm = this.$refs.chonNhanVienCCDC
        await frm.frmChonNV_CCDC()
        
        frm.ds_VT = ds
        await frm.NAP_DS_VATTU()

        if (this.dtLuuSr.length){
          frm.ds_serial = this.dtLuuSr
          frm.GridDS_SERIAL = this.dtLuuSr

        }
        
        if (this.gridVatTu.length){
            var ds_temp = []
            var dsluu = this.gridVatTu

            for(var j in dsluu){
              var dr = dsluu[j]
              var newRow = {
                  CHON: "0",
                  THIETBI_ID: dr.THIETBI_ID,
                  DONVI_ID: dr.DONVI_ID,
                  DONVI_NHAN: dr.DONVI_NHAN,
                  NHANVIEN_ID: dr.NHANVIEN_ID,
                  NHANVIEN_NHAN: dr.NHANVIEN_NHAN,
                  VATTU_ID: dr.VATTU_ID,
                  MA_VT: dr.MA_VT,
                  TEN_VT: dr.TEN_VT,
                  DVI_TINH: dr.DVI_TINH,
                  SOLUONG: dr.SOLUONG,
                  SL_XUAT: dr.SL_XUAT,
                  SERIAL: dr.SERIAL,
                  MA_TBI: dr.MA_TBI,
                  TIEN_PB: 0,
                  DONGIA: dr.DONGIA,
                  SOTHANG_PB: dr.SOTHANG_PB,
                  LOAI_TBI: dr.LOAI_TBI,
                  LOAI_VT: dr.LOAI_VT,
                  THANG_BD: dr.THANG_BD,
                  THANG_KT: dr.THANG_KT,
                  CHUNGTU_ID: dr.CHUNGTU_ID,
                  GHICHU: dr.GHICHU,
                  LOAITBI_ID: dr.LOAITBI_ID,
                  TINHTRANGTBI_ID: dr.TINHTRANGTBI_ID,
                }
              // ADD new
              if (ds_temp.length == 0){
                ds_temp.push(newRow)
              }
              else{
                // lặp
                var i = 0
                for (var k in ds_temp){
                  var dr_temp = ds_temp[k]

                  if (dr_temp.VATTU_ID.toString() == dr.VATTU_ID.toString()
                    && dr_temp.THIETBI_ID.toString() == dr.THIETBI_ID.toString()
                    && dr_temp.SOLUONG.toString() == dr.SOLUONG.toString()
                    && dr_temp.DONVI_ID.toString() == dr.DONVI_ID.toString()
                    && dr_temp.NHANVIEN_ID.toString() == dr.NHANVIEN_ID.toString()
                    && dr_temp.DONGIA.toString() == dr.DONGIA.toString()
                  ){
                    var sothang_pb = parseInt(dr_temp.SOTHANG_PB) || 0
                    var sothang_pb_dr = parseInt(dr.SOTHANG_PB) || 0
                    dr_temp.SOTHANG_PB = sothang_pb + sothang_pb_dr
                    var thangbd_1 = parseInt(dr.THANG_BD) || 0
                    var thangbd_2 = parseInt(dr_temp.THANG_BD) || 0
                    
                    if ( thangbd_1 < thangbd_2){
                      dr_temp.THANG_BD = dr.THANG_BD
                    }

                    var thangkt_1 = parseInt(dr.THANG_KT) || 0
                    var thangkt_2 = parseInt(dr_temp.THANG_KT) || 0
                    if (thangkt_1 > thangkt_2){
                      dr_temp.THANG_KT = dr.THANG_KT
                    }
                    i = 1
                  }
                }
                // Add
                if (i == 0){
                  ds_temp.push(newRow)
                }

                
              }
            }
            frm.ds_luuCP  = ds_temp

          }
        frm.capnv = this.p_radio_capden
        frm.p_ngay_ct =  new Date(this.p_ngay_yeucau.getFullYear(), this.p_ngay_yeucau.getMonth(), this.p_ngay_yeucau.getDate()) 
        await frm.ShowDialog();
        // frm.XacNhan -> tạo func xử lí , XacNhan_frmChonNV_CCDC
      }


      // await this.Open_frmChonNV_CCDC()
      // var frm = value
      // await this.$refs.chonNhanVienCCDC.frmChonNV_CCDC_Load()
            
            
        // } catch (error) {
        //     this.$toast.error("Lỗi hàm cấp phát CCDC nhân viên")
        // }
    },
    async NAP_COMBO(){    
      await this.getTinhTrangTBi()
      await this.getMucDichPhanBo({nhomMDId:4}) // DKSX đang hardcode 4
      this.DSDonVi = await this.getDSDonVi({nhanVienId: this.$auth.getNhanVienID()}) // 4044
      // await this.getChuoiDonVi({nhanVienId: 4044})// this.$auth.getNhanVienID()
      // this.DSNhanVien = await CapPhatCCDCAPI.getDSNhanVien(this.axios,)
      
    },
    // CLEAR
    resetParams(){
        this.p_nxcc_id = 0
        this.p_ngay_yeucau = new Date();
        this.p_mucdich_id = this.DSMucDichPhanBo.length ?  this.DSMucDichPhanBo[0].mucDichId : '';
        this.p_donvi_id = this.$auth.getDonViID().toString()
        this.p_nhanvien_id = this.$auth.getNhanVienID().toString()
        this.p_noidung = ""
        this.p_sophieu = ""
        this.readOnly.soPhieu = true
        this.p_so_chungtu = ""
        this.readOnly.soChungTu = false
        // dtThemVT.Clear();
        this.grcTongHop= []
        this.gridVatTu= []
        this.gridChungTu = []
    },
    SETBUTTON(kieu){
        this.enable.NhapMoi = false
        this.enable.HuyBo = false
        this.enable.XoaVT = false
        this.enable.GhiLai = false
        this.enable.Xoa = false
        this.enable.ChonVT = false
        this.enable.HoanThanh = false
        if (kieu == 1){ // Nhập mới
            this.resetParams()
            this.$refs.txtSoPhieu.focus()
            // gridviewChungTu.FocusedRowHandle
            this.enable.GhiLai = true
            this.enable.HuyBo = true
            this.enable.ChonVT = true
        }
        else if (kieu == 0) { // Hủy
            this.resetParams()
            this.$refs.txtSoPhieu.focus()
            // gridviewChungTu.FocusedRowHandle
            this.enable.NhapMoi = true
        }
        else if (kieu == 3){
            this.enable.NhapMoi = true
            this.enable.HuyBo = true
            this.enable.GhiLai = true
            this.enable.Xoa = true
            this.enable.HoanThanh = true
            this.enable.NhanVien = true
            this.enable.ChonVT = true
        }
    },
    async LAY_VATTU_LE(){
      // try {
        
        if (!this.p_mucdich_id){
          this.$toast.error("Bạn phải chọn mục đích trước")
        }
        var f = this.$refs.frmDSVatTu
        await f.Open()
        // await f.resetParams() // bổ sung thêm
        // await f.frmDSVatTu_Load()// bổ sung thêm
        f.kieu_loc = 1
        f.ma_md = this.ma_md
        if (this.grcTongHop.length){
          var b = JSON.parse(JSON.stringify(this.grcTongHop))
          b.forEach(a=>{
            delete a.THIETBI_ID
            delete a.SL_TREO
            delete a.SERIAL
            delete a.MA_TBI
            delete a.TIEN_PB
            delete a.CHUNGTU_ID
            delete a.GHICHU
            delete a.THANG_BD
            delete a.THANG_KT
            delete a.SL_XUAT
            a.CHON = "s1";
          })
          f.dtLuuVT = b
        }
      // } catch (error) {
      //   this.$toast.error("Lỗi hàm lấy danh sách vật tư lẻ")
      // }
    },
    async XacNhan_frmDSVatTu(){
      var f = this.$refs.frmDSVatTu
      if (f.XacNhan){
        this.dtLuuVT = f.dt
        this.dtThemVT = []
        this.dtVatTu_Le = []
        for(var i in f.dt){
          var item = f.dt[i]
          if (item.LOAITBI_ID.toString() != "2"){
            var r = {}
            r.CHON = "0"
            r.THIETBI_ID = parseInt(item.VATTU_ID)
            r.VATTU_ID = parseInt(item.VATTU_ID)
            r.MA_VT = item.MA_VT
            r.TEN_VT = item.TEN_VT
            r.DVI_TINH = item.DVI_TINH
            r.SOLUONG = parseInt(item.SOLUONG)
            r.DONGIA = parseInt(item.DONGIA)
            if (parseInt(item.SOTHANG_PB) > 0){
              r.TIEN_PB = Math.round(parseInt(item.TIEN) / parseInt(item.SOTHANG_PB))
            }
            else{
              r.TIEN_PB = item.TIEN
            }
            r.TIEN = Math.round(parseInt(item.DONGIA)* parseInt(item.SOLUONG))
            r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
            r.LOAI_TBI = item.LOAI_TBI
            r.LOAI_VT = item.LOAI_VT
            var bd = this.f_DateToString(this.p_ngay_yeucau,'MM/YYYY')
            r.THANG_BD = bd
            r.THANG_KT = this.f_AddMonth(this.p_ngay_yeucau,parseInt(item.SOTHANG_PB),'MM/YYYY')
            r.CHUNGTU_ID = null
            r.GHICHU = ""
            r.SERIAL = ""
            r.MA_TBI = ""
            r.SL_XUAT = 1
            r.LOAITBI_ID = item.LOAITBI_ID
            r.SL_TREO = item.SOLUONG
            this.dtVatTu_Le.push(r)
          }
        }
        for(var i in f.dt){
          var soluong_tbi = 0
          var item = f.dt[i]
          if(!!parseInt(item.SOLUONG)){
            if (item.LOAITBI_ID.toString() == "2"){
              var r = {}
              r.CHON = "0"
              r.THIETBI_ID = parseInt(item.VATTU_ID)
              r.VATTU_ID = parseInt(item.VATTU_ID)
              r.MA_VT = item.MA_VT
              r.TEN_VT = item.TEN_VT
              r.DVI_TINH = item.DVI_TINH
              r.SOLUONG = parseInt(item.SOLUONG)
              r.DONGIA = parseInt(item.DONGIA)
              if (parseInt(item.SOTHANG_PB)>0){
                r.TIEN_PB = Math.round(parseInt(item.DONGIA)*1/parseInt(item.SOTHANG_PB))
              }
              else{
                r.TIEN_PB = Math.round(parseInt(item.DONGIA)*1)
              }
              r.TIEN = Math.round(parseInt(item.DONGIA)* parseInt(item.SOLUONG))
              r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
              r.LOAI_TBI = item.LOAI_TBI
              r.LOAI_VT = item.LOAI_VT
              var bd = this.f_DateToString(this.p_ngay_yeucau,'MM/YYYY')
              r.THANG_BD = bd
              r.THANG_KT = this.f_AddMonth(this.p_ngay_yeucau,parseInt(item.SOTHANG_PB),'MM/YYYY')
              r.CHUNGTU_ID = null
              r.GHICHU = ""
              r.SERIAL = ""
              r.MA_TBI = ""
              r.SL_XUAT = 1
              r.LOAITBI_ID = item.LOAITBI_ID
              r.SL_TREO = item.SOLUONG
              this.dtVatTu_Le.push(r)
            }
          }
          else{
            this.$toast("Số lượng thiết bị có serial phải là số nguyên")
            this.grcTongHop = []
            return
          }
        }
        this.grcTongHop = this.dtVatTu_Le
        this.vchonct_id = 0
        this.vchon_sp = ""
        this.enable.tsbtnNhapMoi ? this.vchungtu_goc = 0 : ""
        await this.CHONVATTU_LE()
      }
    },
    async KIEMTRA_CAPNHAT(){       
        if (this.vchonct_id != 0 && this.p_so_chungtu != this.vchon_sp){
          this.$toast.error("Bạn đã thay đổi số chứng từ")
          return false
        }
        if(!this.p_so_chungtu){
          this.$toast.error("Bạn phải nhập số chứng từ")
          return false
        }
        if(!this.p_mucdich_id.toString()){
          this.$toast.error("Bạn phải chọn mục đích cho chứng từ")
          return false
        }
        if(!this.p_donvi_id.toString()){
          this.$toast.error("Bạn phải chọn từ đơn vị nào")
          return false
        }
        if(this.p_ngay_ct == null){
          this.$toast.error("Bạn chưa chọn ngày chứng từ")
          return false
        }
        if(this.p_ngay_yeucau == null){
          this.$toast.error("Bạn chưa chọn ngày yêu cầu")
          return false
        }
        if(this.p_ngay_yeucau < this.p_ngay_ct){
          this.$toast.error("Bạn phải chọn ngày yêu cầu lớn hơn hoặc bằng ngày chứng từ")
          return false
        }
        if(!this.p_nhanvien_id.toString()){
          this.$toast.error("Bạn phải chọn từ nhân viên nào")
          return false
        }
        if(this.enable.NhapMoi){
          if(this.vchungtu_goc != 0 ){
            if(this.vchonct_id == 0){
              this.$toast.error("Bạn không thể sửa từ chứng từ kế toán sang chứng từ mua trực tiếp")
              return false
            }
          }
          else{
            if (this.vchonct_id != 0 ){
              this.$toast.error("Bạn không thể sửa chứng từ từ mua trực tiếp sang chứng từ kế toán")
              return false
            }
          }
        }
        if(!this.gridVatTu.length){
          this.$toast.error("Danh sách công cụ chưa có vật tư")
          return false
        }
        return true
    },
    async btnTimKiem_Click(){
      try{
        this.loading(true)
        this.resetParams()
        this.vnxcc_id = 0
        this.grcTongHop= []
        this.gridVatTu= []
        
        this.enable.NhapMoi = false;
        this.enable.HuyBo = false;
        this.enable.XoaVT = false;
        this.enable.GhiLai = false;
        this.enable.Xoa = false;
        this.enable.ChonVT = false;
        this.enable.HoanThanh = false;
        if (!this.gridChungTu.length){
          this.enable.NhapMoi = true
        }
        await this.LAY_DS_CHUNGTU_CCDC()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async LAY_DS_CHUNGTU_CCDC(){
    
      this.gridChungTu = await this.getDSCapCCDC( {
        "tatCa" : this.p_check_allphieu ? 1 : 0,
        "donViId" : this.$auth.getDonViID(),
        "nhanVienId" : this.$auth.getNhanVienID(),
        "tuNgay" :  this.f_DateToString(this.p_tungay),
        "denNgay": this.f_DateToString(this.p_denngay)
      })
      this.gridChungTu.forEach(a=>a.TIEN = parseInt(a.TIEN) || 0)
      if(this.gridChungTu.length == 0){
        this.resetParams()
      }
    },
    async LAY_DS_VATTU_CCDC(){
      // try {
        this.gridVatTu = await this.getDSChiTietCCDC({NXCCId: this.vnxcc_id})
        this.showData_DSChiTietNXCC()
        
      // } catch (error) {
      //   this.$toast.error("Lỗi hàm lấy danh sách vật tư chứng từ")
      // }
    },
    showData_DSChiTietNXCC(){
      this.gridVatTu.forEach(a=>{
            a.DONGIA = parseInt(a.DONGIA) || 0
            a.SOTHANG_PB = parseInt(a.SOTHANG_PB) || 0
            a.TIEN_PB = parseInt(a.TIEN_PB) || 0

        })
      // this.DSChiTietCCDC.length ? this.$refs.TableChiTiet.selectRow(0) : ''
    },
    async LAY_DS_VATTU_TH(){
      // try {
        this.grcTongHop = await this.getDSTongHopNXCC({NXCCId: this.vnxcc_id})
        this.showData_DSTongHopCCDC()
        
      // } catch (error) {
      //   this.$toast.error("Lỗi hàm lấy danh sách vật tư chứng từ")
      // }
    },
    showData_DSTongHopCCDC(){
      this.grcTongHop.forEach(a=>{
            a.TIEN = parseInt(a.TIEN) || 0;
            a.SOLUONG = parseInt(a.SOLUONG) || 0;
            a.DONGIA = parseInt(a.DONGIA) || 0;
        })
      // this.DSTongHopCCDC.length ? this.$refs.TableTongHop.selectRow(0) : ''
    },
    // async click_popup_DSChungTu(){
    //   await this.$refs.chonChungTuCCDC.Open()
    // },
    // CHON_CCDC_NV(){},
    // GOI_DL_CAPPHAT(){},
    async GHILAI(){
      try {
        if(!(await this.KIEMTRA_CAPNHAT())){ return }
       
        if(!this.enable.NhapMoi){
          if(!this.dtThemVT.length && !this.dtThemCTVT.length){
            this.$toast.error("Bạn chưa có danh sách vật tư thiết bị")
            return
          }
          
          var isBoxConfirm = false;
          await this.$bvModal.msgBoxConfirm("Bạn có muốn thêm mới chứng từ công cụ dụng cụ này không?",
            {
              title: "Xác nhận hành động",
              centered: true,
              size: "md",
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
            }
          )
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } 
          });

          if (!isBoxConfirm) {
            return
          }
          
          this.loading(true)
          let vkieu = 0
          let dt_temp = []
          
          if((this.ma_md.toString() == this.MUCDICH_CCDC.NHAP.toString() || this.ma_md.toString() == this.MUCDICH_CCDC.DAUKY.toString())
          && this.vchonct_id && this.dtThemCTVT.length ){
            vkieu = 1;
            dt_temp = this.dtThemCTVT;
          }
          else if ((this.ma_md.toString() == this.MUCDICH_CCDC.NHAP.toString() || this.ma_md.toString() == this.MUCDICH_CCDC.DAUKY.toString())
            && this.vchonct_id == 0 && this.dtThemVT.length
          ){
            vkieu = 2
            dt_temp = this.dtThemVT
          }
          else{
            return
          }
          
          let vdata = []
          dt_temp.forEach(a=>{
            vdata.push({
              tbi: a.THIETBI_ID,
              vtt: a.VATTU_ID,
              sl: a.SOLUONG,
              tpb: a.TIEN_PB,
              st: a.SOTHANG_PB,
              tbd: a.THANG_BD,
              tkt: a.THANG_KT,
              gc: a.GHICHU, 
              sr: a.SERIAL, // bổ sung, API thiếu
              mtb: a.MA_TBI, // bổ sung, API thiếu
              dg: a.DONGIA, // bổ sung, API thiếu
              dv: a.DONVI_ID,
              nv: a.NHANVIEN_ID,
              ti: a.TIEN,
              tt: a.TINHTRANGTBI_ID })
          })

          let s  = await this.ThemMoiChungTuCCDC({
          "soct" :  this.p_so_chungtu,
          "chungTuId": this.vchonct_id == 0 ? null : this.vchonct_id,
          "ngayCT": this.f_DateToString(this.p_ngay_ct,'DD/MM/YYYY'),
          "ngayLap": this.f_DateToString(this.p_ngay_yeucau,'DD/MM/YYYY') ,
          "mucDichId": this.p_mucdich_id,
          "donViGiaoId": this.p_donvi_id || 0,
          "nhanVienGiaoId" : this.p_nhanvien_id || 0,
          "khoGiaoId" : this.vkho_giao_id,
          "nhanVienId" : this.$auth.getNhanVienID(),
          "donViId" : this.$auth.getDonViID(),
          "ttphId" :  1,
          "nguoiCN" : this.$auth.getUserName(), //ttnd.ma_nd
          "mayCN" : this.$auth.getUserName(), // ttnd.may_cn
          "ipcn" : "10.59.90.123", // ttnd.ip
          "noiDung" : this.p_noidung,
          "data" : JSON.stringify(vdata),
          "dataTT" : 0,
          "kieu" : vkieu,
          "loai" : this.p_radio_capden,
          "donViNhanId" : 0,
          "tien" : 0
          })

          console.log('=========>>', s)
          if (isNaN(parseInt(s))){
            this.$toast.error(s)
            return
          }
          // quy them vao
          else{
            this.$toast.success('Thêm mới chứng từ thành công')
            this.vnxcc_id = parseInt(s)
            await Final_GHILAI.call(this)
          }               
             
        }
        else{
          if(!this.gridVatTu.length){
            this.$toast.error("Chưa có vật tư nào được đính kèm với chừng từ")
            return
          }


          var isBoxConfirm = false;
          await this.$bvModal.msgBoxConfirm("Bạn có muốn cập nhật chứng từ công cụ dụng cụ này không?",
            {
              title: "Xác nhận hành động",
              centered: true,
              size: "md",
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
            }
          )
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } 
          });

          if (!isBoxConfirm) {
            return
          }
          
          this.loading(true)
          let vkieu = 0
          let dt_temp = []
          if((this.ma_md.toString() == this.MUCDICH_CCDC.NHAP.toString() || this.ma_md.toString() == this.MUCDICH_CCDC.DAUKY.toString())
          && this.vchonct_id && this.dtThemCTVT.length ){
            this.vkieu = 1;
            dt_temp = this.dtThemCTVT;
          } 
          else if ((this.ma_md.toString() == this.MUCDICH_CCDC.NHAP.toString() || this.ma_md.toString() == this.MUCDICH_CCDC.DAUKY.toString())
          && this.vchonct_id == 0 && this.dtThemVT.length){
            vkieu = 2;
            dt_temp = this.dtThemVT
          }
          else if (this.dtThemVT.length == 0 && this.dtThemCTVT.length == 0){
            vkieu = 0
            if (this.gridVatTu.length){
              dt_temp = this.gridVatTu
            }
            else{
              this.$toast.error("Danh sách thiết bị không có bản ghi nào")
              return
            }
          }
          else{
            return
          }

          this.vchonct_id ? this.vchungtu_goc = this.vchonct_id : ""          
          let vdata = []
          dt_temp.forEach(a=>{
            vdata.push({
              tbi: a.THIETBI_ID,
              vtt: a.VATTU_ID,
              sl: a.SOLUONG,
              tpb: a.TIEN_PB,
              st: a.SOTHANG_PB,
              tbd: a.THANG_BD,
              tkt: a.THANG_KT,
              gc: a.GHICHU, 
              sr: a.SERIAL, // bổ sung, API thiếu
              mtb: a.MA_TBI, // bổ sung, API thiếu
              dg: a.DONGIA, // bổ sung, API thiếu
              dv: a.DONVI_ID,
              nv: a.NHANVIEN_ID,
              ti: a.TIEN,
              tt: a.TINHTRANGTBI_ID })
            })
            
          let s  = await this.CapNhatChungTuCCDC({
            "nxccId": this.vnxcc_id,
            "soct" :  this.p_so_chungtu,
            "chungTuId": this.vchungtu_goc == 0 ? null : this.vchungtu_goc,
            "ngayCT": this.f_DateToString(this.p_ngay_ct,'DD/MM/YYYY'),
            "ngayLap": this.f_DateToString(this.p_ngay_yeucau,'DD/MM/YYYY') ,
            "mucDichId": this.p_mucdich_id,
            "donViGiaoId": this.p_donvi_id || 0,
            "nhanVienGiaoId" : this.p_nhanvien_id || 0,
            "khoGiaoId" : this.vkho_giao_id,
            "nhanVienId" : this.$auth.getNhanVienID(),
            "donViId" : this.$auth.getDonViID(),
            "ttphId" :  1,
            "nguoiCN" : this.$auth.getUserName(), //ttnd.ma_nd
            "mayCN" : this.$auth.getUserName(), // ttnd.may_cn
            "ipcn" : "10.35.20.156", // ttnd.ip
            "noiDung" : this.p_noidung,
            "data" : JSON.stringify(vdata),
            "kieu" : vkieu,
            "loai" : this.p_radio_capden,
            "tien" : 0
            })
            
          console.log('=========>>>>', s)
          if (s.toString() != "1"){
            this.$toast.error(s)
            return
          }
          // quy them vao
          else{
            this.$toast.success('Ghi lại chứng từ thành công')
            await Final_GHILAI.call(this)
          }
                
             
        }
        async function Final_GHILAI(){
          let vchungtu_tmp_id = this.vnxcc_id
          this.p_denngay = this.p_ngay_yeucau
          this.p_tungay = new Date(this.p_ngay_yeucau.getTime() - 7*24*60*60*1000)
          await this.LAY_DS_CHUNGTU_CCDC()
          this.dtThemVT = []
          this.dtThemCTVT = []
          this.dtVatTu_Le = []
          await this.gridviewChungTu_FocusedRowChanged()
          // FocusGridViewRow(gridviewChungTu, "NXCC_ID", vchungtu_tmp_id)
        }

      } catch (e) {
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async LAY_CHUNGTU(){
      var frm = this.$refs.chonChungTuCCDC
      frm.vchungtu_ccdc = this.vchungtu_goc
      await frm.frmChonChungTu_CCDC()
      await frm.Open()
    },
    async XacNhan_frmChonChungTu_CCDC(){
      // try {
        // await this.click_popup_DSChungTu()
        let frm = this.$refs.chonChungTuCCDC
        if (frm.XacNhan){
          this.dtLuuSr = [] 
          this.dtLuuNotSr = []
          this.dtChungTuVT = []

          var q = frm.dt.filter(l=> l["SERIAL"] != null && l["SERIAL"] != '')
          for (let dataRow of q){
            this.dtLuuSr.push({
                THIETBI_ID: dataRow.THIETBI_ID,
                SERIAL: dataRow.SERIAL,
                VATTU_ID: dataRow.VATTU_ID,
                ISCHOOSE: "0",
              })
          }

          var q_not = frm.dt.filter(l => l["SERIAL"] == null || l["SERIAL"] == '')
          for (let dataRow of q_not){
            this.dtLuuNotSr.push({
              THIETBI_ID: dataRow.THIETBI_ID,
              LOHANG: dataRow.LOHANG,
              VATTU_ID: dataRow.VATTU_ID,
              ISCHOOSE: "0",
            })
          }
          
          let dv = JSON.parse(JSON.stringify(frm.dt))// phải new obj để ko lỗi 'Do not mutate vuex store state outside mutation handlers'
          let ds = [] // dv.ToTable
          dv.filter(function(item){
            var i = ds.findIndex(x => (
              x.VATTU_ID == item.VATTU_ID && 
              x.MA_VT == item.MA_VT && 
              x.TEN_VT == item.TEN_VT &&
              x.DVI_TINH == item.DVI_TINH &&
              x.LOAI_TBI == item.LOAI_TBI &&
              x.LOAI_VT == item.LOAI_VT &&
              x.CHUNGTU_ID == item.CHUNGTU_ID &&
              x.DONGIA == item.DONGIA &&
              x.LOAITBI_ID == item.LOAITBI_ID &&
              x.LOAIVT_ID == item.LOAIVT_ID &&
              x.LOHANG == item.LOHANG
              ));
            if(i <= -1){
                  ds.push({
                    VATTU_ID: item.VATTU_ID,
                    MA_VT: item.MA_VT,
                    TEN_VT: item.TEN_VT,
                    DVI_TINH: item.DVI_TINH,
                    LOAI_TBI: item.LOAI_TBI,
                    LOAI_VT: item.LOAI_VT,
                    CHUNGTU_ID: item.CHUNGTU_ID,
                    DONGIA: item.DONGIA,
                    LOAITBI_ID: item.LOAITBI_ID,
                    LOAIVT_ID: item.LOAIVT_ID,
                    LOAIVT_ID: item.LOAIVT_ID,
                    LOHANG: item.LOHANG
                  });
            }
            return null;
          });
          
          for(let i =0;i < ds.length;i++){
            if (ds[i].LOAITBI_ID.toString() == "2" || ds[i].LOAITBI_ID.toString() == "3" ){
              ds[i]["SOLUONG"] = frm.dt.filter(a=>a.VATTU_ID.toString() == ds[i].VATTU_ID.toString()).length
            }
            else{
              ds[i]["SOLUONG"] = frm.dt.filter(a=>a.VATTU_ID.toString() == ds[i].VATTU_ID.toString() 
              && a.LOHANG == ds[i].LOHANG ).reduce((sum,b)=> sum + b.SOLUONG, 0)
            }
          }

          for (let item of ds){
            if(item.LOAITBI_ID.toString() != "2"){
              let r = {}

              let t = this.dtLuuNotSr.filter(l=>l.VATTU_ID.toString() == item.VATTU_ID.toString()
                && l.LOHANG.toString() == item.LOHANG.toString())

              r.CHON = "0"
              t.length ? r.THIETBI_ID = t[0].THIETBI_ID : r.THIETBI_ID = '' 

              r.VATTU_ID = parseInt(item.VATTU_ID) || 0
              r.MA_VT = item.MA_VT.toString()
              r.TEN_VT = item.TEN_VT.toString()
              r.DVI_TINH = item.DVI_TINH.toString()

              r.SOLUONG = parseInt(item.SOLUONG) || 0
              r.DONGIA = parseInt(item.DONGIA) || 0

              r.TIEN_PB = 0
              r.TIEN = Math.round((parseInt(item.DONGIA) || 0) * (parseInt(item.SOLUONG) || 0) )

              r.SOTHANG_PB = 0
              r.LOAI_VT = item.LOAI_VT
              r.LOAI_TBI = item.LOAI_TBI
              r.THANG_BD = this.f_DateToString(this.p_ngay_yeucau,'MM/YYYY')
              r.THANG_KT = this.f_DateToString(this.p_ngay_yeucau,'MM/YYYY')
              r.CHUNGTU_ID = frm.vchungtu_id
              r.GHICHU = ""
              r.SERIAL =  ""
              r.MA_TBI = ""
              r.SL_XUAT = 1
              r.LOAITBI_ID = item.LOAITBI_ID
              r.SL_TREO = item.SOLUONG
              this.dtChungTuVT.push(r)
            }
          }

          for (let item of ds){
            let soluong_tbi = 0;
            if(!isNaN(item.SOLUONG) && Number.isInteger(parseFloat(item["SOLUONG"]))){
              if(item.LOAITBI_ID.toString() == "2"){
                
                let r = {}
                r.CHON = "0"
                r.THIETBI_ID = parseInt(item.THIETBI_ID) || 0 // nó nên là thietbi_id nhưng C# ĐHSX là vattu_id
                r.VATTU_ID = parseInt(item.VATTU_ID) || 0
                r.MA_VT = item.MA_VT.toString()
                r.TEN_VT = item.TEN_VT.toString()
                r.DVI_TINH = item.DVI_TINH.toString()
                r.SOLUONG = parseInt(item.SOLUONG) || 0
                r.DONGIA = parseInt(item.DONGIA) || 0

                r.TIEN_PB = 0
                r.TIEN = Math.round((parseInt(item.DONGIA) || 0) * (parseInt(item.SOLUONG) || 0) )
                r.SOTHANG_PB = 0

                r.LOAI_VT = item.LOAI_VT
                
                r.THANG_BD = this.f_DateToString(this.p_ngay_yeucau, 'MM/YYYY')
                r.THANG_KT = this.f_DateToString(this.p_ngay_yeucau, 'MM/YYYY')

                r.CHUNGTU_ID = frm.vchungtu_id
                r.GHICHU = ""
                r.SERIAL =  ""
                r.MA_TBI = ""
                r.SL_XUAT = 1
                r.LOAI_TBI = item.LOAI_TBI
                item.LOAITBI_ID != null && item.LOAITBI_ID != '' ? r.LOAITBI_ID = item.LOAITBI_ID  : r.LOAITBI_ID = 0

                r.SL_TREO = item.SOLUONG
                this.dtChungTuVT.push(r)
              }
            }
            else{
              this.$toast.error("Số lượng thiết bị có serial phải là số nguyên")
              return 
            }
          }
          // this.setDSTongHopCCDC(this.dtChungTuVT)
          this.grcTongHop = this.dtChungTuVT
          this.showData_DSTongHopCCDC()

          this.vchonct_id = frm.vchungtu_id
          this.p_so_chungtu = frm.sophieu

          this.readOnly.soChungTu = true
          this.vchon_sp = frm.sophieu
          this.vkho_giao_id = frm.kho_id
          this.enable.NgayCT = false
          let z = await this.getNgayChungTu({chungTuId: frm.vchungtu_id})
          
          if(z.length){
            this.p_ngay_ct = new Date(this.convertDateTime_DDMMYYYY_to_YYYYMMDD(z[0].ngayCT.replaceAll("/", "-")));
          }

        }
      // } catch (error) {
      //   this.$toast.error("Lỗi lấy chứng từ và vật tư")
      // }
    },
    convertDateTime_DDMMYYYY_to_YYYYMMDD(datetime) {
      datetime = datetime.split(" ");
      var thisDate = datetime[0].split("-");
      var newDate = [thisDate[2], thisDate[1], thisDate[0]].join("/") + " " + datetime[1];
      return newDate;
    },
    async HOANTHIEN_CHUNGTU(){
      return await this.HoanThienCCDC({
      "nxccId" :  this.vnxcc_id,
      "nhanVienId": this.$auth.getNhanVienID(),
      "nguoiCN": this.$auth.getUserName(), // this.$auth.ma_nd nó nên là mã người dùng nhưng token hiện chưa có
      "mayCN": this.$auth.getUserName(), // this.$auth.may_cn
      "ipCN": "10.59.90.123" , // this.$auth.getIP()
      })
    },
    async LAY_TT_CHUNGTU_CCDC(row){
      // try {
        this.vnxcc_id = parseInt(row.data.NXCC_ID)
        this.p_sophieu = row.data.SOPHIEU
        this.p_mucdich_id = row.data.MUCDICH_ID
        this.p_so_chungtu = row.data.SO_CT
        this.p_donvi_id = row.data.DONVI_ID // should be DONVI_GIAO_ID, quy 
        this.p_noidung = row.data.ND_GIAO || ''
        this.p_ngay_yeucau =  row.data.NGAYLAP == "" || row.data.NGAYLAP == null ? null : this.f_StringToDate(row.data.NGAYLAP)//NGAY_CT code C# như vậy
        this.p_ngay_ct = row.data.NGAY_CT == "" || row.data.NGAY_CT == null ? null : this.f_StringToDate(row.data.NGAY_CT)//NGAYLAP
        this.vttph_id = parseInt(row.data.TTPH_ID)
        this.vloai_goc = parseInt(row.data.LOAI)
        this.p_radio_capden = this.vloai_goc
        if(row.data.CHUNGTU_ID != null && row.data.CHUNGTU_ID != ''){
          this.vchungtu_goc = parseInt(row.data.CHUNGTU_ID)
        }
        else{
          this.vchungtu_goc = 0
        }
        
        
        await this.LAY_DS_VATTU_CCDC()
        if (this.gridVatTu.length){
          this.p_donvi_id = this.gridVatTu[0].DONVI_GIAO_ID
          this.p_nhanvien_id = this.gridVatTu[0].NHANVIEN_GIAO_ID
        }
        await this.LAY_DS_VATTU_TH()
        this.dtThemVT = []
        this.dtVatTu_Le = []
        this.SETBUTTON(3)
        this.enable.HoanThanh = this.vttph_id.toString() != "4"


      // } catch (error) {
      //   this.$toast.error("Lỗi khi lấy thông tin chứng từ CCDC")
      // }
    },
    tsbtnNhapMoi_Click(){
      this.SETBUTTON(1)
    },
    async tsbtnGhiLai_Click(){
      try{
        await this.GHILAI()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    KiemTraNguoiCN(NGUOI_CN){
      return true
      // return NGUOI_CN == this.$auth.getUserName() //this.$auth.ma_nd
    },
    async tsbtnXoa_Click(){
      try {
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có chắc chắn muốn xóa chứng từ không? ", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } 
          });

        if (!isBoxConfirm) {
          return;
        }
        this.loading(true)
        let s = await this.XoaChungTuCCDC({nxccId: this.vnxcc_id})
        if (s != "1"){
          this.$toast.error("Lôi hàm xóa chứng từ")
        }
        else{
          this.$toast.success("Xóa chứng từ thành công")
          let vchungtu_tmp_id = this.vnxcc_id
          await this.LAY_DS_CHUNGTU_CCDC()
          // this.FocusGridViewRow(gridviewChungTu,this.vnxcc_id)
          this.dtThemVT = []
          this.dtVatTu_Le = []                
        }
      } catch (error) {
        this.$toast.error(error.data.message)
      }finally{
        this.loading(false)
      }
    },
    tsbtnHuyBo_Click(){
      this.SETBUTTON(0)
    },
    async cboTuDonVi_EditValueChanged(){
      try{
        this.loading(true)
        if(this.p_donvi_id != null || this.p_donvi_id != ""){
          this.p_nhanvien_id = ''
          this.enable.NhanVien = true
          this.DSNhanVien = await this.getDSNhanVienTheoDonVi(this.p_donvi_id)
        }else{
          this.p_nhanvien_id = ''
          this.enable.NhanVien = false
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tstbtnChonVT_Click(){
      await this.LAY_VATTU_LE()
    },
    async gridviewChungTu_FocusedRowChanged(row){
      try{
        this.loading(true)
        if(!this.gridChungTu.length){
          this.resetParams()
          this.vnxcc_id = 0
          this.grcTongHop= []
          this.gridVatTu= []
          this.enable.NhapMoi = true
          this.enable.HuyBo = false
          this.enable.XoaVT = false
          this.enable.GhiLai = false
          this.enable.Xoa = false
          this.enable.ChonVT = false
          this.enable.HoanThanh = false
          this.gridChungTu.length == 0 ? this.enable.NhapMoi = true : ''
          return 
        }
        this.gridviewChungTu_FocusedRow = row.data
        await this.LAY_TT_CHUNGTU_CCDC(row)
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async txtSoChungTu_ButtonClick(){
      await this.LAY_CHUNGTU()
    },
    async txtSoChungTu_ButtonClick2(){
      this.vchonct_id = 0;
      this.vchon_sp = "";
      this.params.p_so_chungtu = "";
      this.readOnly.soChungTu = false
      this.enable.NgayCT = true
    },
    async tsbtnHoanThanh_Click(){
      try {
        if (!this.vnxcc_id){
          this.$toast.error("Bạn chưa chọn chứng từ để hoàn thiện")
          return
        }
        this.loading(true)
        let s = await this.HOANTHIEN_CHUNGTU()
        if (s.toString() != "1"){
          this.$toast.error(s)
        }
        else{
          this.$toast.success("Hoàn thiện chứng từ thành công")
        }
        await this.LAY_DS_CHUNGTU_CCDC()
        // FocusGridViewRow(gridviewChungTu, "NXCC_ID", vchungtu_tmp_id);
      } catch (error) {
        this.$toast.error(error.data.message)
      }finally{
        this.loading(false)
      }
    },
    btnXoaVT_ButtonClick(row){
      this.gridVatTu.splice(row.index,1)
    },
    gridVatTu_DataSourceChanged(){
      if(!this.gridVatTu.length){
        this.readOnly.soChungTu = false
      }
      else{
        let q = this.gridVatTu.some(a=>!a.CHUNGTU_ID)
        if(q){
          this.readOnly.soChungTu = false
        }
        else{
          this.readOnly.soChungTu = true
        }
      }
    },
    async btnEditTbi_ButtonClick(data){
      if(this.enable.NhapMoi){
        var f = this.$refs.frmGanThuocTinh_LVT
        f.vattu_id = parseInt(data["VATTU_ID"] || 0)
        f.thietbi_id = parseInt(data["THIETBI_ID"] || 0)
        await f.frmGanThuocTinh_LVT_Load()
        this.$refs.popup_frmGanThuocTinh_LVT.show()
      }
    },
    XacNhan_frmGanThuocTinh(){

    },
    btnDeleteAlll_Click(){
      this.gridVatTu = []
    },
    gridViewVatTu_CellValueChanged(){
      // if (e.Column.FieldName == "GHICHU"){
        // lam sau
      // }
    },
    tsbtnExcelDK_Click(){
      document.getElementById('fileUpload').click()
    },
    LAY_TT_EXCEL(){

    },
    async CAP_NHAT_TBI_EXCEL(ds){
      console.log(ds , " = ds")
      for( let i in ds){
        let e = ds[i]
        let dt_tt = await this.getMaDVTheoMaNV(e.MA_NVIEN_NHAN)
        if(dt_tt.length == 0 || dt_tt[0].maDV == null || dt_tt[0].maDV == ""){
          this.$toast.error(e.MA_NVIEN_NHAN + " Không đúng. Dòng " + i)
          return
        }

        
        if(e.MA_DONVI_NHAN == null || e.MA_DONVI_NHAN == ""){          
          e.MA_DONVI_NHAN = dt_tt[0].maDV          
        }else if(dt_tt[0].maDV != e.MA_DONVI_NHAN){
          this.$toast.error(e.MA_NVIEN_NHAN + " không đúng mã đơn vị trong excel. Dòng " + i)
          return
        }
      }
    },
    async cboTrangThaiFilter_EditValueChanged(){
      await this.tsbtnPhieuNhap_Click()
    },
    async tsbtnPhieuNhap_Click(){
      if (this.gridChungTu.length == 0){
        return
      }
      await this.frmXemBaoCao(this.gridviewChungTu_FocusedRow.CHUNGTU_ID)
    },
    async tsbtnPhieuXuat_Click(){
      if (this.gridChungTu.length == 0){
        return
      }
      await this.frmXemBaoCao(this.gridviewChungTu_FocusedRow.CHUNGTU_ID)
    },
    async tsbtnPhieuChuyen_Click(){
      if (this.gridChungTu.length == 0){
        return
      }
      await this.frmXemBaoCao(this.gridviewChungTu_FocusedRow.CHUNGTU_ID)
    },
    async frmXemBaoCao(chungTuId){
      this.$root.showLoading(true);
        let res = await this.xemBaoCao({chungTuId: chungTuId})
        this.$root.showLoading(false);
        res ? previewPrint(res.data) : ''
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error("Chưa chọn file nào!")
        return false
      }
      if (
        this.arrayImportFile.filexcel.type !=
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" 
        &&
        this.arrayImportFile.filexcel.type != 'application/vnd.ms-excel'

      ) {
        this.$toast.error("File không phải định dạng xlsx hoặc xls!")
        return false
      }
      return true
    },
    async onChange() {
      this.arrayImportFile.filexcel = this.$refs.fileUpload.$refs.input.files[0]
      
      let json_list = []
      try {
        this.loading(true)
        if (this.checkfile()) {
          let tencot
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
            
            await this.CAP_NHAT_TBI_EXCEL(result)
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
          
        }
      } catch (error)  {
        this.$toast.error("Có lỗi xảy ra: " + error)
        return
      } finally {
        this.$refs.fileUpload.reset()
        this.loading(false)
      }
      this.arrayImportFile.filexcel = null
    },
  },
  // frmCapPhatCCDC_Load
created: async function() {
   
    try{
      this.loading(true)
      this.clearData();
      await this.NAP_COMBO()
      this.resetParams()
      await this.LAY_DS_CHUNGTU_CCDC()      
      EventBus.$on('XacNhan_frmChonChungTu', async (value)=> { 
        await this.LAY_CHUNGTU(value)
      });
      EventBus.$on('XacNhan_frmChonNV_CCDC', async (value)=> { 
        await this.CHONVATTU_LE(value)
      });
    }catch(e){   
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false)
    }
    
    
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>