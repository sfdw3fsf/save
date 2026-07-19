<template src='./DeNghiVT.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import NhapXuatVTAPI from  '../api/NhapXuatVTAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/nhapxuatvt'
import select2 from '@/components/Select2.vue'
import { Query } from '@syncfusion/ej2-data';

import XLSX from "xlsx";
import frmGanSeri from '../frmGanSeri/frmGanSeri.vue'
import frmChungTu_TBao from '../frmChungTu_TBao'
import frmTaoSerial from '../frmTaoSerial'
import DSVatTu from '../DSVatTu'

import TraCuuDonHangConHan from '../TraCuuDonHangConHan'
import frmVatTuTrongKho from '../frmVatTuTrongKho'
import frmNhapText from '../frmNhapText/frmNhapText.vue'
import frmNhapCap from '../frmNhapCap'
import frmChonThietBiCT from "../frmChonTBi_CT/ChonThietBiCT.vue";
import {previewPrint} from "../../../utils/util";
import { currency } from '@/filters/currency'
import frmGanSeri_Tbi from '../frmGanSeri_Tbi/frmGanSeri_Tbi.vue'
import frmGiaoPhieu from "../frmGiaoPhieu/frmGiaoPhieu.vue";
import ChonThietBiCT from "../frmChonTBi_CT/ChonThietBiCT.vue";
var DialogResultObj = {
    None: 0,
    OK: 1,
    Cancel: 2,
    Abort: 3,
    Retry: 4,
    Ignore: 5,
    Yes: 6,
    No: 7,
}

export default {

  components: { frmNhapCap,
                frmNhapText, 
                TraCuuDonHangConHan, 
                frmVatTuTrongKho, 
                DSVatTu,
                frmTaoSerial, 
                frmChungTu_TBao,
                frmGanSeri, 
                XLSX, 
                breadcrumb, 
                appSelect2: select2,
                frmChonThietBiCT,
                frmGiaoPhieu,
                ChonThietBiCT,
                frmGanSeri_Tbi},
  name: "DeNghiVT",
  mounted() {
  }, 
  computed: {
    ...mapState("nhapxuatvt", statePropertyName),
    ...mapGetters("nhapxuatvt", getterName),
    soph: {
      get() {
        return this.gridviewChungTu_FocusedRow ? this.gridviewChungTu_FocusedRow["SOPHIEU"] : ""
      }
    },
    ma_pda_chungtu: {
      get() {
        return this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["MA_PDA"] ? this.gridviewChungTu_FocusedRow["MA_PDA"] : ""
      }
    },
    nghiepvu_id_chungtu: {
      get() {
        return this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["NGHIEPVU_ID"] ? this.gridviewChungTu_FocusedRow["NGHIEPVU_ID"] : "-1"
      }
    },
    PTAggregatesColumns () {
      return [        
        { field: 'TIEN', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.SUMGiaTriConLai },
      
      ]
    },
  },
  data() {
    return {
        header: {
            title: "NHẬP, XUẤT, ĐIỀU CHUYỂN VẬT TƯ",
            list: [
            { name: "Quản lý vật tư", link: { name: "Ui.cards" } },
            {
                name: "Nhập, xuất, điều chuyển vật tư",
                link: { name: "Ui.buttons" },
            },
            ],
        },
        _vloai: 1,
        chitietCTData_insert: [],
        chitietCTData_update: [],
        chungtuData: [],
        chungtu_id: -0,
        chungtu_tbiData: [],
        ctct_id: null,
        dsQuyen: [],

        tenNguoiNhan: "",
        maBC: "",

        dtChungTu: [],
        dtTB: [],
        dtThietBi: [],
        dtVatTu: [],

        dtLuuVT: [],
        dtCopyCT: [],
        dtCopyCTCT: [],
        dtVatTuXuat: [],
        
        khotu_id: 0,
        kieugoi_id: null,
        copyct: false,

        kieunhap: null,
        slohang: "",
        svattu_id: "",
        t_kho: "",
        t_kho_id: 0,

        ten_khotu: "",
        vchungtu_id: "",

        vttnx_id: 0,
        donhang_ids:[],
        dtDonHang:[],
        dtDulieu: [],
        dtNotInsert: [],
        dtThemVT: [],
        dtVT_LE: [],
        cbo_tinhtrang: [],
        file: "",
        nddh: "",
        ttph_id: null,
        ngay_dh: new Date(),

        dc_ct_noibo: false,
        ds_loc_vattu: [],
        ds_md: [],

        params:{
          p_txtSoPhieu: "",
          p_dtpNgayKy: new Date(),
          p_ckbSendSMS: true,
          p_LoaiChungTu: "",
          p_dtpNgayDK: new Date(),
          p_KieuPhieu: "",
          p_MucDich: "",
          p_NghiepVu: "",
          p_Ma_PDA: "",

          p_ckbTuKho: false,
          p_TuKho: "",
          p_ckbDenKho: false,
          p_DenKho: "",
          p_dtpNgayGuiTT: new Date(),
          p_dtpNgayNhanTT: new Date(),
          p_txtLenhVanBan: "",
          p_dtpNgayVanBan: new Date(),

          p_CongTrinh: "",
          p_dtpNgayChuyenKT: new Date(),
          p_txtNoiDung: "",
          p_grcFileCV:"",

          p_dtpTuNgay: new Date((new Date()).getTime() - 7*24*60*60*1000), //ngày yêu cầu từ
          p_dtpDenNgay: new Date(), // đến ngày
          p_chkAll_Phieu: false,
          p_ckb_nd: false,

          p_VatTu: "",
          p_txtQuetSerial: "",

          p_HopDong: "",
          p_DuAn: "",
          p_ckbDuAn: true,

        },
        cboLoaiChungTu: [],
        cboKieuPhieu: [],
        cboMucDich: [],
        cboNghiepVu: [],
        cboMa_PDA: [],
        cboTuKho: [], // value global
        cboDenKho: [],
        cboCongTrinh: [],
        CboVatTu: [],
        cboLoaiKho: [],
        cboLocVattu: [],
        cboHopDong: [],
        cbo_DuAn: [],

        gridChungTu: [],
        gridBienBanIMS: [],
        grcMa_PDA: [],
        gridVTGroup: [],
        gridVatTu: [],
        gridThietBi: [],
        grcLocVattu: [],
        
        quytrinh_id: 0,
        huonggiao_id: 0,
        
      tag: "",
      Tag: "1",
      kiemtra_quyen: false,
      boimau: false,
      qr_code: false,
      chonvattu_lohang: false,
      diachi_mac: false,
      b_mathung: false,

     
      LOAIPHIEU_DENGHI_VATTU:{
        PHIEU_DANGKY_NHUCAU : 1,
        PHIEU_DENGHI_NHAPKHO_TT : 2,
        PHIEU_DENGHI_MUALE : 3,
        PHIEU_NHAPKHO : 4,
        PHIEU_DENGHI_CHUYENKHO : 5,
        PHIEU_CHUYENKHO : 6,
        PHIEU_DENGHI_XUATKHO : 7,
        PHIEU_XUATKHO : 8,
        PHIEU_DENGHI_QUYETTOAN : 9,
        PHIEU_DENGHI_THUHOI_TB : 10,
        PHIEU_DENGHI_THUHOI_CT : 11,
      },
      // Danh mục loại kho
      LOAI_KHO: {
        KHO_THUHOI: 15,
        KHO_BAOHANH: 6,
        KHO_TOTRUONG: 14,
        KHO_GUI_BAOHANH: 16,
        KHO_TRA_BAOHANH: 17,
        KHO_NHA_CC: 2,
        KHO_NV: 5,

        KHO_VATTU_SUACHUA: 530,
        KHO_VATTU_BAOHANH: 540,
      },
      // Danh mục quyền QLVT
      DS_QUYEN_VT: {
        HOANTHIEN_CHUNGTU: 1794,
        CAPNHAT_CHUNGTU: 1795,
        QUYENDUYET_VT: 2080,
        QUYENDUYET_TTVT: 2700,
        QUYENDUYET_TTCU: 2701,
        QUYENXUAT_KHO: 2081,
        QUYENLAYVT_DH: 2505,
        QUYET_DOIMACT: 3501,
        QUYET_XETDUYET: 3505,
      },
      // Danh mục loại kho
      MUCDICH_VT: {
        NHAP_BAOHANH: "1.4",
        XUAT_BAOHANH: "2.4",
        MUASAM: "N_MS",
        CHUYEN_CP: "2.XCP",
        KDV_DONGIA: "N.KDV_DG",
      },
      Enabled: {
        cboLoaiChungTu: false,

        tsbtnNhapMoi: false,
        tsbtnGhiLai: false,
        tsbtnXoa: false,
        tsbtnHuyBo: false,
        tsbtnHoanThanh: false,
        tstbtnDonHang: false,
        tsbtnThemVT: false,
        tsbtnSinhSr: false,
        tsbtnCopyCT: false,
        tsbtnDongBo: false,
        tsbtnBBGNNhap: false,
        tsbtnPhieuNhap: false,
        tsbtnPhieuXuat: false,
        tsbtnBBGNXuat: false,
        tsbtnPhieuChuyen: false,
        tstPhieuTUVT: false,
        tsbtnInXuatDVTC: false,


        gridViewVatTu: {
          SOLUONG: true
        },
        btnSeri_ChiaSL: false,
        btnTaoTbi: false,

        colTKDU: false,

      },
      ReadOnly: {
        CboVatTu: false,
        cboDenKho: false,
        cboTuKho: false,

      },

      Visible: {

        tsbtnNhapMoi: true,
        tsbtnXoa: true,
        tsbtnGhiLai: true,
        tsbtnHuyBo: true,
        tsbtnThemVT: true,
        tstbtnDonHang: true,
        tsbtnHoanThanh: true,
        tsbtnSinhSr: true,
        tsbtnCopyCT: true,
        tsbtnDongBo: true,
        tsbtnUpPXK: true,
        btnXoaHetVT: true,
        simpleButton1: true,

        ThongTinHDDA: true,
        colMac: false,
        colDGSua: false,
        colMaThung: false,

        layoutbtnTBi: false, // visible btnChonTamUng
        layoutControlItem29: false,// visbile btnChuyenTT
        layoutControlItem13: false,// visible txtNDDK_ButtonClick

        layoutCtrlPhieuDN: false,
        layoutBtnChonPhieuDN: false,
        colMac: false,
      },

      Text: {
        label7: "NHẬP VẬT TƯ"
      },

      status: 0,
      kieu_td: 0,
      kho_nhan_goc: 0,
      vma_md: "",

      splitContainer1: {
        Panel2Collapsed: false
      },
      vtdk: [],
      check_ct: true,

      arrayImportFile: {
          filexcel:null,
          ok: false,
          dsImportExcel: [],
          dataExcel: [],
          ds_file: []
        },
      SumChon1: 0,
      sl1: 0,
      vchungtu_id_temp: 0,
      sophieu_temp: "",
      SumChon: 0,
      sl0: 0,
      // ------------------ bổ sung code 12/12/2021  -------------------
      // checkData: 
      bbbgiao: [],
      duan_ims_clone: [],
      dieuchuyen_clone: [],
      sohoa_qlvt: false,
      sohoa_qlvt_dual: false,
      vattu_ims: false,
      batbuoc_chst: false,
      check_user_tao_chungtu: false, // LDG dùng đầu tiên
      doi_mucdich_chungtu: false,
      cboNghiepVu: [],
      cboTinh: [],
      cboMa_DV_SD: [],
      cboMa_DV_QL: [],
      // ------------------- -------------------

      filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true, },
      customTemplate: function() {
        return  { template : Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data () { return { data: {data: {}}}; }
          })
        }
      },
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
      editSettings: { allowEditing: true, mode: 'Batch'},
      selectionSettings: {  type: 'Single', checkboxOnly: true },
      XoaVTTemplate: function () {
            return {
                template: Vue.component('XoaVTTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="gridviewVatTu_btnXoaVT">
                                  <span class="-ap icon-close"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async gridviewVatTu_btnXoaVT(){
                        await this.$parent.$parent.$parent.gridviewVatTu_btnXoaVT(this.data)
                      }

                    }
                })
            }
        },
      XoatbITemplate: function () {
            return {
                template: Vue.component('XoatbITemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnXoatbI_Click">
                                  <span class="-ap icon-close"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnXoatbI_Click(){
                        await this.$parent.$parent.$parent.gridViewThietBi_ShownEditor(this.data)
                      }

                    }
                })
            }
        },
     
      btnTBTemplate: function () {
            return {
                template: Vue.component('btnTBTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" v-if="btnTB" @click="btnTB_ButtonClick">
                                  <span class="act -ap icon-chevron-thin-down"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    computed: {
                      btnTB: {
                        get(){
                          if (parseInt(this.data["SPVT"] || 0) > 0 || parseInt(this.data["SPBH"] || 0) > 0){
                            return true
                          }
                          return false // true =>test
                        }
                      }
                    },
                    methods:{
                      async btnTB_ButtonClick(){
                        await this.$parent.$parent.$parent.btnTB_ButtonClick(this.data)
                      }
                    }

                })
            }
        },
      btnEdit_Template: function () {
            return {
                template: Vue.component('btnEdit_Template', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" v-if="btnEdit" @click="btnEdit_ButtonClick">
                                  <span class="act -ap icon-chevron-thin-right"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    computed: {
                      btnEdit: {
                        get(){
                          if ( this.data["LOAITBI_ID"] && this.data["LOAITBI_ID"].toString() == "3" ){
                            return true
                          }
                          return false // true =>test
                        }
                      }
                    },
                    methods:{
                      async btnEdit_ButtonClick(){
                        await this.$parent.$parent.$parent.gridviewVatTu_TachCap(this.data)
                      }
                    }

                })
            }
        },
        
      iconStatus: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<span :class="icon" v-bind:style="{ color: colorText }"></span>`,                        
            data () { 
              return { 
                data: {},
                icon: "",
                colorText: ""
              }; 
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              }
            },
            created:async function () {          
              this.icon = this.data.STATUS == "1" ? "act -ap  icon-tick f30" : "text-warning one-alert f20"
              this.colorText = this.data.STATUS == "1" ? "green" : "orange"
						},
            methods: {
              
            },
          })
        }
      },
      isTab1: true,
      gridChungTuPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
      gridTinhTrangPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
      gridVatTuPage : {
              page: 0,
              maxSize: 10,
              totalElement: 0,
              totalPages: 0,
              sort: null,
              propertiesSort: null,
              currentRow: 0
          },
      gridThietBiPage : {
              page: 0,
              maxSize: 10,
              totalElement: 0,
              totalPages: 0,
              sort: null,
              propertiesSort: null,
              currentRow: 0
          },

        
      showLocVT: false,
      selectedLocVT: "",
      showDSVatTu: false,
      grcVT: [],
      animationSettings: { effect: 'Zoom' },

      showHopDong: false,
      selectedHopDong: '',
      gridviewChungTu_FocusedRow: {},
      gridViewThietBi_FocusedRow: {},
      gridViewVatTu_FocusedRow: {},
      DSKho: [],

    };
  },
  methods: {
    ...mapActions("nhapxuatvt", actionName),
    ...mapMutations("nhapxuatvt", mutationName),

    SUMGiaTriConLai() {
      var TongTienPB = 0
      this.gridVatTu.forEach(e => {
        TongTienPB += parseInt(e.TIEN)
      })
      return currency(TongTienPB);
    },
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    async gridChungTuPageHandler(e) {
      this.gridChungTuPage.page = e.pageIndex
      this.gridChungTuPage.maxSize = e.pageSize
      await this.Load_ds_chungtu()
    },
    gridTinhTrangPageHandler(e) {
      this.gridChungTuPage.page = e.pageIndex
      this.gridChungTuPage.maxSize = e.pageSize
    },
    gridVatTuPageHandler(e) {
      this.gridVatTuPage.page = e.pageIndex
      this.gridVatTuPage.maxSize = e.pageSize
    },
    async gridThietBiPageHandler(e) {
      this.gridThietBiPage.page = e.pageIndex
      this.gridThietBiPage.maxSize = e.pageSize
      await this.Load_ds_thietbi()
    },
    grcVT_selectedRow(data){
      if (data){
        this.params.p_VatTu = data.VATTU_ID
      }
    },
    cboHopDong_selectedRow(data){
      if (data){
        this.params.p_HopDong = data.hdmsId
      }
    },
    async cboLocVattu_selected(data){
      if (data){
        this.selectedLocVT = data.tenVatTu
        this.params.p_LocVattu = data.vatTuID;
      }
    },
    async Load_quyen(){
      this.dsQuyen = await this.getDSQuyenNguoiDung({nguoiDungId : this.$auth.getNguoiDungID()})
    },
    // Combo vật tư
    async LOAD_POPUP(){
      this.ds_loc_vattu = await this.getDSVatTu({vatTuId: 26})
      this.grcLocVattu = this.ds_loc_vattu
      this.cboLocVattu = this.ds_loc_vattu
    },
    async Loc_Kho(){
      let loc_tukho = this.DSKho.filter(a=>a.trangThai.toString() == '1' && a.loaiKhoId &&a.loaiKhoId.toString() != this.LOAI_KHO.KHO_NV.toString())
      if (this.params.p_ckbTuKho){
        if (this.cboTuKho.length && this.cboTuKho[0].hasOwnProperty('quyenKg') ){
          loc_tukho = loc_tukho.filter(b=>b.quyenKg == '1')
        }  
      }

      let loc_denkho = this.DSKho.filter(a=>a.trangThai.toString() == '1' && a.loaiKhoId && a.loaiKhoId.toString() != this.LOAI_KHO.KHO_NV.toString())
      if (this.params.p_ckbDenKho){
        if (this.cboDenKho.length && this.cboDenKho[0].hasOwnProperty('quyenKg') ){
          loc_denkho = loc_denkho.filter(b=>b.quyenKg == '1')
        }  
      }

      this.cboTuKho = loc_tukho
      this.cboDenKho = loc_denkho
    },
    async Load_Cbo (){
    
      if (this.tag == "3"){
        this.cboLoaiChungTu = await this.getDanhMucLoaiCT_V2({})
      }else{
        this.cboLoaiChungTu = await this.getDanhMucLoaiCT({})
      }
 
      this.DSKho = await this.getDanhMucKho({nhanVienId: this.$auth.getNhanVienID()})
      this.cboTuKho = this.DSKho
      this.cboDenKho = this.DSKho

      this.cboMucDich = await this.getDanhMucMucDich({nhomMdId: 3})
      this.ds_md = this.cboMucDich
     
      this.cboCongTrinh = await this.getDSCongTrinh({})
      await this.Loc_Kho()

      this.CboVatTu = []
      this.gridChungTu = []
    
    },

    async frmDeNghiVT_Load(){
  
      await this.AN_GROUP(true)

      if (this.Tag){
          this.tag = this.Tag;
      }

      await this.Load_quyen()
      await this.Load_Cbo()
      await this.Load_ds_chungtu()
      await this.LOAD_POPUP()
      
      // Lấy TS mặc định
      this.Visible.colMac = false
      let ds = await this.getThamSoMacDinh({kieuId: 0})
      if (ds.length){
        if (this.setThamSo("KIEMTRA_QUYENXN",ds)){
          this.kiemtra_quyen = true
        }
        
        if (this.setThamSo("MAU_CHUNGTU_QLVT",ds)){
          this.boimau = true
        }

        if (this.setThamSo("QLVT_MAC",ds)){
          this.diachi_mac = true
          this.Visible.colMac = true
        }
        if (this.setThamSo("QLVT_MATHUNG",ds)){
          this.b_mathung = true
        }
        if (this.setThamSo("KDV_SUA_DG",ds)){
          this.sua_dg_kdv = true
        }
        
      }

      if (this.$auth.getMaTinh() == "HNI"){
        this.Visible.layoutControlItem13 = true
        this.ReadOnly.txtNDDK = true
        this.Visible.xtraTabPage3 = true
        this.Visible.layoutControlItem29 = true
        this.cbo_tinhtrang = [
          {TINHTRANG_ID: 0, TINHTRANG: ''},
          {TINHTRANG_ID: 1, TINHTRANG: 'Mới'},
          {TINHTRANG_ID: 2, TINHTRANG: 'Cũ - Hỏng'},
          {TINHTRANG_ID: 3, TINHTRANG: 'Cũ - Sử dụng được'},
        ]
        this.cboTinhTrang = this.cbo_tinhtrang
      }
      else{
        this.Visible.layoutControlItem13 = false
        this.Visible.xtraTabPage3 = false
        this.Visible.layoutControlItem29 = false
      }

    },
    setThamSo(str_vtemp,ds){
      let det = ds.filter(a=>a.maTS == str_vtemp)
        if (det.length){
          if (det[0].tenTS.trim() == "1"){
            return true
          }
        }
      return false
    },
    
    async Load_ds_chungtu(){
      var res = await this.getDSChungTuDeNghi({
        "tatCa": this.params.p_chkAll_Phieu ? 1 : 0,
        "tuNgay": this.f_DateToString(this.params.p_dtpTuNgay,"DD/MM/yyyy"),
        "denNgay": this.f_DateToString(this.params.p_dtpDenNgay,"DD/MM/yyyy"),          
        "pageNum": this.gridChungTuPage.page,
        "pageSize": this.gridChungTuPage.maxSize,
        "totalRow": 1 
      })
      console.log(res , " = res")
      this.dtChungTu = []   
      var DSChungTu = []  
      if (Object.keys(res).length){   
        this.dtChungTu = res.data
        DSChungTu = res.data
        this.gridChungTuPage.page = res.page
        this.gridChungTuPage.maxSize = res.maxSize
        this.gridChungTuPage.totalElement = res.totalElement
        this.gridChungTuPage.totalPages = res.totalPages
      }

      
      if (this.params.p_ckb_nd && this.dtChungTu.length > 0){
          
        var q = this.dtChungTu.filter(a=>a.NGUOI_CN == this.$auth.getUserName()) 
        if (q.length){
          DSChungTu = q
          this.gridChungTuPage.totalElement = q.length
        }else{
          DSChungTu = []
        }        
      }

      if (DSChungTu.length){        
        this.SetButton(3)
      }else{      
        this.SetButton(1)
      }
      this.gridChungTu = DSChungTu
      console.log(this.gridChungTu , " = gridChungTu")
    },
    async Load_ds_vattu(){
      this.dtVatTu = await this.getDSVatTuChungTu({chungTuId: this.vchungtu_id})
      this.gridVatTu = this.dtVatTu
    },
    async LAY_DS_VTCT_SERIAL(){
      let ds = await this.getDSVatTuChungTuSerial({chungTuId: this.vchungtu_id})
      if (ds.length && this.Enabled.tsbtnNhapMoi && this.Enabled.tsbtnGhiLai){
        this.grcVT = ds
        if (this.ttph_id.toString() == "4"){
          this.ReadOnly.CboVatTu = true
        }
        else{
          this.ReadOnly.CboVatTu = false
        }
      }
      else if (!this.Enabled.tsbtnNhapMoi || (!ds.length && this.Enabled.tsbtnNhapMoi && this.Enabled.tsbtnGhiLai)){
        this.ReadOnly.CboVatTu = true
        this.params.p_VatTu = ""
      }
    },
    async Load_ds_thietbi(){
      let res = await this.getDSThietBiChungTu({chungTuId: this.vchungtu_id})
      // gridViewThietBi.ClearColumnsFilter();
      this.dtThietBi = res.data
      this.gridThietBiPage.page = res.page
      this.gridThietBiPage.maxSize = res.maxSize
      this.gridThietBiPage.totalElement = res.totalElement
      this.gridThietBiPage.totalPages = res.totalPages

      this.gridThietBi = this.dtThietBi
      if (this.dtThietBi.length && this.dtThietBi[0].hasOwnProperty('MATHUNG')){
        this.Visible.colMaThung = true
      }
      else{
        this.Visible.colMaThung = false
      }
    },
    async load_tinhtrang(){
      var dttemp = this.kieu == 0 ? await this.getDSThietBiChungTu({chungTuId: this.vchungtu_id}) : this.dtThietBi
      if (dttemp.length && dttemp[0].hasOwnProperty('TINHTRANG')){
        dttemp.forEach(a=>{
          if (!a.TINHTRANG.toString()){
            a.TINHTRANG = 0
          }
        })
      }
      // gridviewTinhTrang.ClearColumnsFilter();
      this.gridTinhTrang = dttemp
    },
    async  gridviewChungTu_FocusedRowChanged_sender(row){
      this.loading(true)
      this.t_kho_id = 0
      this.t_kho = ""
      this.Visible.layoutbtnTBi = false
      if (row){
        await this.gridviewChungTu_FocusedRowChanged(row)
      }else{
        this.Visible.tsbtnTraLai = false
        this.SetButton(1)
        this.Clear()
      }
      this.loading(false)
    },
    async gridviewChungTu_FocusedRowChanged(row){
      this.gridviewChungTu_FocusedRow = row
      this.Visible.tsbtnTraLai = false
      this.kho_nhan_goc = 0
      this.vchungtu_id = row.CHUNGTU_ID == null || row.CHUNGTU_ID == "" ? 0 : parseInt(row.CHUNGTU_ID)
      this.dtLuuVT = await this.getDSVatTuChungTuV1({chungTuId: this.vchungtu_id})
      this.dtVatTuXuat = this.dtLuuVT

      this.ttph_id = row.TTPH_ID == null || row.TTPH_ID == "" ? 0 : parseInt(row.TTPH_ID) 
      this.kieunhap = 0
      this.params.p_txtSoPhieu = row.SOPHIEU == null ? "" :  row.SOPHIEU
      this.params.p_txtNoiDung = row.ND_GIAO == null ? "" : row.ND_GIAO
      this.kieu_td = row.KIEU == null ? "" : row.KIEU

      this.params.p_CongTrinh =  null
      if ( this.dtChungTu.length && this.dtChungTu[0].hasOwnProperty("CONGTRINH_ID")){
        this.params.p_CongTrinh = row.CONGTRINH_ID
      }

      if(row.LOAI == "1"){
        this.params.p_LoaiChungTu = row.LOAICT_ID == null || row.LOAICT_ID == "" ? 0 : parseInt(row.LOAICT_ID)
      }else{
        this.params.p_LoaiChungTu = 99
      }

      if (this.$auth.getMaTinh() == 'HNI'){
        this.params.p_txtNDDK = row.NDDK
      }

      this.params.p_MucDich = row.MUCDICH_ID == null || row.MUCDICH_ID == "" ? 0 : parseInt(row.MUCDICH_ID)

      var d_s = await this.getThongTinNgayDH({chungTuId: this.vchungtu_id})
      if(d_s.length > 0 ){
        this.ngay_dh = new Date(d_s[0].NgayNH)
      }
      var d_sngay = await this.getThongTinNgayXuat({chungTuId: this.vchungtu_id})
      if (d_sngay.length > 0){
        this.params.p_dtpNgayGuiTT = ""
        if (d_sngay[0].ngayGuiTT != "" && d_sngay[0].ngayGuiTT != null){
          this.params.p_dtpNgayGuiTT = this.f_StringToDate(d_sngay[0].ngayGuiTT,'DD-MM-YYYY hh:mm:ss')
        }

        this.params.p_dtpNgayNhanTT = ""
        if (d_sngay[0].ngayNhanTT != "" && d_sngay[0].ngayNhanTT != null){
          if (this.kiemtra_quyen){
            this.Visible.tsbtnTraLai = true
          }
          this.params.p_dtpNgayNhanTT = this.f_StringToDate(d_sngay[0].ngayNhanTT,'DD-MM-YYYY hh:mm:ss')
        }

      }
      this.vma_md = ""
      this.dc_ct_noibo= false

      var  d = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
      if (d.length > 0){
        this.vma_md = d[0]
      }

      if (this.vma_md == "1.NBH"){
        this.splitContainer1.Panel2Collapsed = false
      }
      else if (this.vma_md == "DC_CT" && (this.params.p_LoaiChungTu == "3" || this.params.p_LoaiChungTu == "2")){
        this.dc_ct_noibo = true
        this.Visible.layoutbtnTBi = true
      }else{
        this.splitContainer1.Panel2Collapsed = true
      }

      this.params.p_TuKho = row.KHO_GIAO_ID == null || row.KHO_GIAO_ID == "" ? 0 : parseInt(row.KHO_GIAO_ID)

      this.status = row.STATUS == "1" ? 1: 0
      
      this.t_kho_id = row.KHO_GIAO_ID == null ? 0 : parseInt(row.KHO_GIAO_ID)
      this.t_kho = row.KHO_GIAO_ID == null ? "" : row.KHO_GIAO

      this.params.p_DenKho = row.KHO_NHAN_ID == null ? 0 : parseInt(row.KHO_NHAN_ID)
      if(row.KHO_NHAN_ID != null && row.KHO_NHAN_ID != ""){
        this.kho_nhan_goc = parseInt(row.KHO_NHAN_ID)
      }

      this.params.p_dtpNgayDK = this.f_StringToDate(row.NGAY_CT,'DD-MM-YYYY hh:mm:ss')
      let kt_kieu = await this.getKieuChungTu({chungTuId: this.vchungtu_id})
      this.Enabled.gridViewVatTu.SOLUONG = true
      if(kt_kieu.length > 0 && parseInt(kt_kieu[0]["COUNT(CTCT.CTCT_ID)"])){
        this.Enabled.gridViewVatTu.SOLUONG = false
      }

      await this.Load_ds_vattu()
      await this.Load_ds_thietbi()
      await this.load_tinhtrang(1)

      this.params.p_txtNoiDung = row.ND_GIAO == null ? "" : row.ND_GIAO
      
      this.SetButton(3)
      await this.LAY_DS_VTCT_SERIAL()

      this.Visible.colDGSua = false
      if (this.sua_dg_kdv && this.vma_md == "N.KDV_DG"){        
        this.Visible.colDGSua = true
      }


      if (this.Enabled.tsbtnNhapMoi && this.params.p_LoaiChungTu == "99"){
        await this.AN_GROUP(false)
      }else{
        await this.AN_GROUP(true)
      } 
    },
    async SetButton(kieu){
      this.copyct = false
      this.Enabled.tsbtnNhapMoi = false
      this.Enabled.tsbtnGhiLai = false
      this.Enabled.tsbtnXoa = false
      this.Enabled.tsbtnHuyBo = false
      this.Enabled.tsbtnThemVT = false
      this.Enabled.tsbtnSinhSr = false
      this.Enabled.tsbtnCopyCT = false


      if (kieu == -1){//Bat dau
        this.Enabled.tsbtnGhiLai = true
        this.Enabled.tsbtnHuyBo = true
      }
      //Bat dau
      if (kieu == 0){
        this.$refs.txtSoPhieu.focus()
        this.Enabled.tsbtnNhapMoi = true
        this.Clear()
      }
      //Them moi
      if (kieu == 1){
        this.vchungtu_id = 0
        this.$refs.txtSoPhieu.focus()
        this.Enabled.tsbtnGhiLai = true
        this.Enabled.tsbtnHuyBo = true   
        this.Enabled.tsbtnThemVT = true
        this.Clear()
        this.dtVatTu = []
        this.dtThietBi = []
        this.ttph_id = 1
        this.ReadOnly.CboVatTu = true
      }
      //Huy
      if (kieu == 2){
        this.Enabled.tsbtnNhapMoi = true
        this.Enabled.tsbtnXoa = true
        this.Clear()
      }
      //Edit
      if (kieu == 3){
        this.Enabled.tsbtnNhapMoi = true
        this.Enabled.tsbtnXoa = true
        this.Enabled.tsbtnGhiLai = true
        this.Enabled.tsbtnHuyBo = true
        this.Enabled.tsbtnThemVT = (this.ttph_id != 4)
        this.Enabled.tsbtnSinhSr = true
        this.Enabled.tsbtnCopyCT = true
  
      }

      // Load quyền
      this.Visible.tsbtnNhapMoi = true
      this.Visible.tsbtnXoa = true
      this.Visible.tsbtnGhiLai = true
      this.Visible.tsbtnHuyBo = true
      this.Visible.tsbtnThemVT = true
  
      this.Visible.tsbtnSinhSr = false
      this.Visible.tsbtnCopyCT = true
     
      this.Visible.btnXoaHetVT = true
      this.Visible.simpleButton1 = false

      if (this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU)){
        this.Visible.tsbtnSinhSr = true
        this.Visible.simpleButton1 = true
      }
    },
    Clear(){
      
      this.dc_ct_noibo = false

      this.params.p_txtSoPhieu = ""
      this.params.p_LoaiChungTu = null
      this.params.p_MucDich = null
      this.params.p_TuKho = null
      this.params.p_DenKho = null
      this.params.p_txtNoiDung = ""
      this.params.p_dtpNgayDK = new Date()
      
      this.gridVatTu = []
      this.gridThietBi = []
      this.dtLuuVT = []

      this.copyct = false
      this.params.p_dtpNgayGuiTT = ""
      this.params.p_dtpNgayNhanTT = ""
      this.params.p_txtNDDK = ""

      this.vma_md = ""
      this.gridTinhTrang = []
      this.Visible.layoutbtnTBi = false
      // btnChonTamUng
    },
    async CapNhatChiTiet_CT(themmoi){
      if (this.kieunhap.toString() == "0" && this.kieugoi_id.toString() != "2"){
        await this.TaoDuLieu_CT()
        // Tạo func để covert variable phù hợp API
        
        await this.InsertUpdateChiTietChungTu(this.chitietCTData_insert)
        
        await this.InsertUpdateChiTietChungTu(this.chitietCTData_update)
        
        let vkden_id = !this.params.p_DenKho.toString() ? 0 : parseInt(this.params.p_DenKho)
        if (!themmoi){
          await this.CapNhatDuLieuChungTuTBi({
            khoDenId: vkden_id,
            chungTuId: this.vchungtu_id
          })
        }
      }

      // Nhập từ đơn hàng
      if (this.kieunhap.toString() == "1" && this.kieugoi_id.toString() != "2"){
        await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
        this.dtDonHang.forEach(async (item)=>{
          let body = {
            "chungTuId": this.vchungtu_id,
            "ctdhId": item.CTDH_ID,
            "loHang": item.LOHANG.toString() || '',
            "nguoiCn": this.$auth.getUserName(),
            "mayCn": this.$auth.getUserName(),
            "ipCn": "10.59.90.123"//ttnd.ip
          }
          
          await this.CapNhatDonHangChungTu(body)
        })
      }
      // Nhập từ kho
      if ((this.kieunhap.toString() == "2" && this.kieugoi_id.toString() != "2") || this.dc_ct_noibo){
        if (!this.copyct){
          await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
          this.dtThemVT.forEach(async (item)=>{
            await this.CapNhatVatTuChungTu({
              "khoId": this.params.p_TuKho,
              "vatTuId": item.VATTU_ID,
              "soLuong": item.SOLUONG,
              "allVatTu": item.TATCA,
              "chungTuId": this.vchungtu_id,
              "loHang": item.LOHANG,
              "nguoiCn": this.$auth.getUserName(),
              "mayCn": this.$auth.getUserName(),
              "ipCn": "10.59.90.123"//ttnd.ip
            })
            if (item.hasOwnProperty('TRANGTHAI')){
              let state = item["TRANGTHAI"] == "" ? "" : (item["TRANGTHAI"].toString() == "Đã có đầu vào" ? "1" : "0")
              console.log('====> TRANG THAI', item["TRANGTHAI"])
              await this.CapNhatChiTietChungTu({
                "trangThai": state,
                "vatTuId": item["VATTU_ID"],
                "chungTuId": this.vchungtu_id,
                "loHang": item["LOHANG"]
              })
            }
          })
        }
        else{
          await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
          this.dtCopyCTCT.forEach(async (item)=>{
            await this.CapNhatVatTuChungTu({
              "khoId": this.params.p_TuKho,
              "vatTuId": item.VATTU_ID,
              "soLuong": item.SOLUONG,
              "allVatTu": 0,
              "chungTuId": this.vchungtu_id,
              "loHang": item.LOHANG,
              "nguoiCn": this.$auth.getUserName(),
              "mayCn": this.$auth.getUserName(),
              "ipCn": "10.59.90.123"//ttnd.ip
            })
          })
        }
      }
      // Nhập lẻ
      if (this.kieugoi_id.toString() == "2"){
        if (this.kieunhap.toString() == "3"){
          await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
          this.dtVT_LE.forEach(async (item)=>{
            await this.CapNhatVatTuMuaLe({
              "vatTuId": parseInt(item["VATTU_ID"]),
              "chungTuId": this.vchungtu_id,
              "loHang": item["LOHANG"] || "",
              "donGia": parseInt(item["DONGIA"]),
              "tien": parseInt(item["TIEN"]),
              "soLuong": parseInt(item["SOLUONG"]),
              "hanBH": item["HAN_BH"] || "",
              "nguoiCn": this.$auth.getUserName(),
              "mayCn": this.$auth.getUserName(),
              "ipCn": "10.59.90.123"//ttnd.ip
            })
          })
        }
        else{
          await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
          this.dtVatTu.forEach(async (item)=>{
            await this.CapNhatVatTuMuaLe({
              "vatTuId": parseInt(item["VATTU_ID"]),
              "chungTuId": this.vchungtu_id,
              "loHang": item["LOHANG"] || "",
              "donGia": parseInt(item["DONGIA"]),
              "tien": parseInt(item["TIEN"]),
              "soLuong": parseInt(item["SOLUONG"]),
              "hanBH": item["HAN_BH"] || "",
              "nguoiCn": this.$auth.getUserName(),
              "mayCn": this.$auth.getUserName(),
              "ipCn": "10.59.90.123"//ttnd.ip
            })
          })
        }
      }
    },
    async tsbtnThoat_Click(){
      // Close()
    },
    async tsbtnNhapMoi_Click(){
      this.SetButton(1)
    },
    async tsbtnGhiLai_Click(){
      // Tạm thời bỏ try catch để debug
      try{
        this.loading(true)
        let dtp = this.params.p_dtpNgayDK
        if (!this.Enabled.tsbtnNhapMoi){
          if (! await this.KiemTraDuLieu(true, false)){ return}
          this.params.p_dtpDenNgay = dtp
          this.params.p_dtpTuNgay = new Date(dtp.getTime() - 7*24*60*60*1000)
          if (this.kiemtra_quyen){
            if (! await this.KIEMTRA_QUYEN_XN(5,this.params.p_TuKho)){ return }
          }
          this.loading(false)
          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Bạn có chắc chắn muốn ghi lại!", {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            })
            .then((value) => {
              if (value) {
                isBoxConfirm = true;
              } else {
                return;
              }
            });

          if (!isBoxConfirm) {        
            return;
          }
          this.loading(true)
          var vloaiphieu_id = 0
          if(this.params.p_LoaiChungTu == "1"){
            vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_NHAPKHO_TT
          }else if(this.params.p_LoaiChungTu == "2"){
            vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_XUATKHO
          }else if(this.params.p_LoaiChungTu == "3"){
            vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_CHUYENKHO
          }else if(this.params.p_LoaiChungTu == "99"){
            vloaiphieu_id = this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_MUALE
          }
          await this.TaoDuLieu(true, false)
                  
          await this.InsertUpdateChungTu(1,this.chungtuData)

          await this.CapNhatChiTiet_CT(true) 

          var vdenghi_id = await this.getKeys({
            keyName : "DENGHI",
            numBlockSize : 1,
            numRetry:  10
          })    
        
          var ketQua = await this.capNhatDeNghiChungTu({
            deNghiId: vdenghi_id,
            loaiPhieuId: vloaiphieu_id,
            chungTuId: this.vchungtu_id
          })
          console.log(ketQua , " = ketQua Thêm mới")
          if(ketQua == "1"){
            this.$toast.success("Thêm mới thành công")
          }else{
            this.$toast.error(ketQua)
          }          
        }
        else{
          if (! await this.KiemTraDuLieu(false, false)){ return }
          
          if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id)){ return }
          if (this.kiemtra_quyen) {
            if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }

            if (this.params.p_TuKho != this.t_kho_id){
              if (! await this.KIEMTRA_QUYEN_XN(5, parseInt(this.params.p_TuKho))){ return }
            }
          }
          this.loading(false)
          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm("Bạn có chắc chắn muốn ghi lại!", {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            })
            .then((value) => {
              if (value) {
                isBoxConfirm = true;
              } else {
                return;
              }
            });

          if (!isBoxConfirm) {        
            return;
          }
          this.loading(true)
          await this.TaoDuLieu(false, false)                  
          let k = await this.XoaChungTuBaoHanh({
            "kieu": 2,
            "chungTuId": this.vchungtu_id,
            "chungTuCtId": 0,
            "thietBiId": 0
          })
          if (k != "1"){
            this.$toast.error(k)
            return
          }
          if (this.ttph_id  == 4){
            let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
            if (s.toString() != "1"){
              this.$toast.error(s)
              return
            }

            if(this.params.p_ckbSendSMS){            
              await this.GuiSMSChungTu({
                tuDong: 2,
                chungTuId: this.vchungtu_id,                         
              })            
            }

            s = await this.XoaPhieuDaHoanThanh({
                  "chungTuId": this.vchungtu_id,
                  "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                  "mayCn": "quyPc",//ttnd.may_cn
                  "ipCn": "10.59.90.123",//ttnd.ip
              })
            if (s.toString() != "1"){
              this.$toast.error("Có lỗi trong quá trình xóa phiếu!")
              return
            }
          }
          
          await this.InsertUpdateChungTu(0, this.chungtuData)
        
          if (this.kieugoi_id == 2){
            let mucdich_id = parseInt(this.params.p_MucDich)
            var d = []
            this.params.p_MucDich ? d = await this.getMaMucDichTheoID({mucDichId: mucdich_id}) : ''
            if (d.length){
              if (d[0].toString() != "1.N" && d[0].toString() != "N.DK" && d[0].toString() != "1.NBH" && d[0].toString() != "1.3" && d[0].toString() != "1.NL"
                && d[0].toString() != "N.VT"){
                  var tongtien = this.dtVatTu.map(p=>parseInt(p.TIEN) || 0 ).reduce((sum,b)=>sum + parseInt(b.TIEN),0)
                  if (tongtien > 100000000){
                    await this.Load_ds_vattu()
                    this.$toast.error("Đơn hàng lẻ không được nhập quá 100,000,000")
                    return
                  }
                }
            }
          }
          await this.CapNhatChiTiet_CT(false)

          this.$toast.success("Cập nhật thành công")
        }
        
      
        let vchungtu_tmp_id = this.vchungtu_id
        if (this.vtdk.length){
          await this.CAPNHAT_DANGKY(vchungtu_tmp_id)
        }
        await this.Load_ds_chungtu()
        await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)
        this.copyct = false

        var dtable = await this.getDSChiTietChungTu({})
        if (dtable.length && dtable[0].hasOwnProperty('TRANGTHAI')){
          var d = await this.getMaMucDichTheoChungTu({chungTuId: this.vchungtu_id})
          if (this.params.p_LoaiChungTu.toString() == "99" && d != null && (d.toString() == "N.KDV" || d.toString() == this.MUCDICH_VT.KDV_DONGIA)){
            await this.CapNhatTrangThaiChiTietCT({chungTuId: this.vchungtu_id})
            await this.Load_ds_vattu()
          }
        }
        this.loading(false)
      }catch(e){
        this.$toast.error("Đã có lỗi xảy ra khi ghi lại!" + e)
      }finally{
        this.loading(false)
      }
    },
    async CAPNHAT_DANGKY(chungtu){
      var q = this.vtdk[0].DANGKY_ID.toString()
      await this.CapNhatDangKy({chungTuId: this.vchungtu_id,  dangKyId: q})
      let xdoc = []
      this.vtdk.forEach(a=>xdoc.push({
        VTDK_ID:  a.VTDK_ID,
        SL_DUYET: a.SL_DUYET
      }))
      await this.CapNhatVatTuDangKy({
          "dataDangky": JSON.stringify(xdoc)
      })
    },
    async tsbtnHuyBo_Click(){
      this.SetButton(0)
    },
    async tsbtnXoa_Click(){
      try {
      
        if (this.vchungtu_id == 0){
          this.$toast.error("Bạn phải chọn chứng từ để xóa")
          return
        }
        if (! await this.KiemTraHanChotCT()){// Kiếm tra hạn chốt ok thì xóa
          return
        }
        if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){ return }
        if (this.kiemtra_quyen){
          if (!await this.KiemTraNguoiCN()){
            this.$toast.error("Bạn không được xóa chứng từ của " + this.gridviewChungTu_FocusedRow["NGUOI_CN"] + " !")
            return
          }
          if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)) { return }
        }

        var str = ""
        var d = await this.getSoPhieu({chungTuId: this.vchungtu_id})
        if (d.length){
          str = d[0].toString() + " "
        }
        await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn xóa chứng từ " + str + "không!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
            if(value){
              var frm = this.$refs.frmNhapText
              await frm.Load_frmNhapText()
              await frm.frmNhapText_3("Bạn hãy lựa chọn kiểu xóa chứng từ", 1)
              frm.Text.title = "Danh sách kiểu xóa chứng từ"
              frm.functionName = 'tsbtnXoa_Click'
              this.$refs.popup_frmNhapText.show()

            }
          })
          .catch(err => {
              // An error occurred
          })
      }catch(e){
        this.$toast.error("Đã có lỗi xảy ra khi xóa!")
      }finally{

      }
    },
    async XacNhan_frmNhapText(){
      try{
     
        var frm = this.$refs.frmNhapText
        if (frm.DialogResult == DialogResultObj.OK && frm.functionName == 'tsbtnXoa_Click'){
          // Xóa toàn bộ 
              if (frm.vkieu_luachon == "1"){
                  let s = ""
                  if (this.ttph_id == 4){
                    s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
                    if (s.toString() != "1"){
                      this.$toast.error(s)
                      return
                    }
                  }
                  // Xóa Đồng bộ
                  let db_kt = await this.KiemTraXoaDongBo({
                    chungTuId: this.vchungtu_id,
                    nguoiCN: "",//ttnd.ma_nd
                    mayCN: "",//ttnd.may_cn
                    ipCN: frm.vkieu_luachon
                  })
                  //Nếu tồn tại chứng từ tập đoàn thì phải xóa đồng bộ thành công
                  if (db_kt != "xoa"){ 
                    if (db_kt == "ok"){
                      this.$toast.success("Xóa chứng từ kế toán tập đoàn thành công!")
                    }
                    else{
                      this.$toast.error("Xóa chứng từ kế toán tập đoàn có lỗi:" + db_kt + "!")
                      if (this.vchungtu_id != 0){
                        // this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.vchungtu_id)
                      }
                      await this.Load_ds_chungtu();
                    }
                  }

                  if(this.params.p_ckbSendSMS && this.ttph_id == 4){
                      try {
                        await this.GuiSMSChungTu({
                          tuDong: 2,
                          chungTuId: this.vchungtu_id,                        
                        })
                      } catch (error) {
                        this.$toast.error("Có lỗi khi send smsm tự động")
                      }
                  }
                  
                  s = await this.XoaChungTuBaoHanh({
                    "kieu": 1,
                    "chungTuId": this.vchungtu_id,
                    "chungTuCtId": 0,
                    "thietBiId": 0
                  })
                  if (s.toString() != "1"){
                    this.$toast.error(s)
                    return
                  }
                  s = await this.XoaPhieuDaHoanThanh({
                    "chungTuId": this.vchungtu_id,
                    "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                    "mayCn": this.$auth.getUserName(),//ttnd.may_cn
                    "ipCn": "10.59.90.123"//ttnd.ip
                  })
                  if (s.toString() != "1"){
                    this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s + "")
                    return
                  }
                  await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
                  var t = await this.XoaChungTu({chungTuId: this.vchungtu_id})
                  this.$toast.success("Xóa thành công!")
                  if(this.vchungtu_id != 0){
                    await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.vchungtu_id);
                  }
                  await this.Load_ds_chungtu()
                  if (!this.gridChungTu.length){
                    return
                  }
                  else{
                    await this.Load_ds_vattu()
                    await this.Load_ds_thietbi()
                  }
              }
              // Xóa Đồng bộ
              // Chỉ xóa tập đoàn
              if(frm.vkieu_luachon == "2"){
                let s = ""
                if (this.ttph_id == 4){
                  s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
                  if (s.toString() != "1"){
                    this.$toast.error(s)
                    return
                  }
                }
                let db_kt = await this.KiemTraXoaDongBo({
                    chungTuId: this.vchungtu_id,
                    nguoiCN: "",//ttnd.ma_nd
                    mayCN: "",//ttnd.may_cn
                    ipCN: frm.vkieu_luachon
                  })
                  //Nếu tồn tại chứng từ tập đoàn thì phải xóa đồng bộ thành công
                  if (db_kt != "xoa"){ 
                    if (db_kt == "ok"){
                      this.$toast.success("Xóa chứng từ kế toán tập đoàn thành công!")
                    }
                    else{
                      this.$toast.error("Xóa chứng từ kế toán tập đoàn có lỗi:" + db_kt + "!")
                      if(this.vchungtu_id != 0){
                        // await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.vchungtu_id);
                      }
                      await this.Load_ds_chungtu()
                      if (!this.gridChungTu.length){
                        return
                      }
                      else{
                        await this.Load_ds_vattu()
                        await this.Load_ds_thietbi()
                      }
                    }
                  }

              }
          
        }
      }catch(e){
        this.$toast.error("Đã có lỗi xảy ra khi xóa!")
      }finally{
        
      }
    },
    async KiemTraNguoiCN(){
      // return this.gridviewChungTu_FocusedRow.NGUOI_CN == this.$auth.getUserName() //ttnd.ma_nd
      return true
    },
    async KiemTraDuLieu(themmoi,traphieu){
      if (!themmoi){
        if (! await this.KiemTraNguoiCN() && !traphieu){
          this.$toast.error("Bạn không được sửa chứng từ của người khác!")
          this.$refs.cboLoaiChungTu.focusIn()
          return false
        }
        let s  = await this.KiemTraCapNhatChungTu({chungTuId: this.vchungtu_id})
        if (s.toString() != "1"){
          this.$toast.error(s)
          return false
        }
      }
      if (themmoi && this.f_StringToDate(this.ngay_dh,'DD/MM/YYYY') > this.params.p_dtpNgayDK){//&& ngay_dh != DateTime.MinValue     
        this.$toast.error("Ngày chứng từ phải lớn hơn ngày yêu cầu nhập kho (" + this.ngay_dh  + "")
        return false        
      }

      if (this.ttph_id == 4 && !themmoi){
        let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
        if (s != "1"){
          this.$toast.error(s)
          this.$refs.cboLoaiChungTu.focusIn()
          return false
        }
        if (this.params.p_DenKho == this.kho_nhan_goc){
          this.$toast.error("Bạn ghi lại phải giữ nguyên đúng kho đến (trạng thái DD có thể sửa kho đến)")
          this.params.p_DenKho = this.kho_nhan_goc
          return false
        }
      }

      if (this.params.p_LoaiChungTu == null || this.params.p_LoaiChungTu == 0){
        this.$toast.error("Bạn chưa chọn loại chứng từ!")
        this.$refs.cboLoaiChungTu.focusIn()
        return false
      }

      if (this.params.p_MucDich == null || this.params.p_MucDich == 0){
        this.$toast.error("Bạn chưa chọn Mục đích!")
        this.$refs.cboMucDich.focusIn()
        return false
      }

      if (this.params.p_TuKho == null || this.params.p_TuKho == 0){
        this.$toast.error("Bạn chưa chọn từ kho!")
        this.$refs.cboTuKho.focusIn()
        return false
      }
      // Bổ sung cho hoàn trả

      let xuat_hoantra = false
      this.dc_ct_noibo  = false
      if (this.ds_md.length){
        var t = this.ds_md.filter(a=>a.MUCDICH_ID == this.params.p_MucDich)
        if (t.length && t[0]["maMd"].toString() == "X.HTVT"){
          if (this.params.p_LoaiChungTu.toString() == "2" || this.params.p_LoaiChungTu.toString() == "3" ){
            xuat_hoantra = true
          }
        }

        if (t.length && t[0]["maMd"].toString() == "DC_CT"){
          if (!!this.params.p_LoaiChungTu.toString() == "3" || this.params.p_LoaiChungTu.toString() == "2"){
            this.dc_ct_noibo = true
          }
        }

      }
      
      if ((this.params.p_DenKho == null || this.params.p_DenKho == 0) && !xuat_hoantra){
        this.$toast.error("Bạn chưa chọn đến kho!")
        this.$refs.cboDenKho.focusIn()
        return false
      }
      if (xuat_hoantra && this.params.p_DenKho != null && this.params.p_DenKho != nu0ll){
        this.$toast.error("Xuất hoàn trả vật tư bạn không được chọn kho!")
        this.$refs.cboDenKho.focusIn()
        this.params.p_DenKho = ""
        return false        
      }

      if(!xuat_hoantra && !this.dc_ct_noibo && this.params.p_TuKho == this.params.p_DenKho){
        this.$toast.error("Kho từ và kho đến không thể giống nhau!")
        this.$refs.cboTuKho.focusIn()
        return false
      }
      if (this.dc_ct_noibo){
        if (this.params.p_TuKho != this.params.p_DenKho){
          this.$toast.error("Kho từ và kho đến phải giống nhau!")
          this.$refs.cboTuKho.focusIn()
          return false
        }
        let dt = this.cboTuKho
        if(dt.filter(a=>a.KHO_ID == this.params.p_TuKho.toString() && a.LOAIKHO_ID == "14").length == 0){
          this.$toast.error("Chỉ áp dụng mục đích này cho loại kho tổ trưởng")
          return false
        }
        if (this.params.p_CongTrinh == null || this.params.p_CongTrinh == 0){
          this.$toast.error("Chưa chọn công trình")
          return false
        }
      }

      
      if (!await this.KiemTraHanChotCT()){
        return false
      }

      if (this.params.p_txtNoiDung.length > 500){
        this.$toast.error("Nội dung nhập quá dài (<500 kí tự) !")
        return false
      }

      if (!this.gridVatTu.length){
        this.$toast.error("Bạn cần phải chọn danh sách vật tư cho chứng từ!")
        return false
      }
      
      if(this.Enabled.tsbtnNhapMoi){
        if (this.soph.startsWith("QT")){
          this.$toast.error("Chứng từ quyết toán không thể sửa!")
          return false
        }
        if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
          this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
          return false
        }
      }
      return true
    },
    async TaoDuLieu(themmoi,traphieu){
      this.chungtuData = []
      let row = {}
      if (themmoi){
        // this.vchungtu_id = await this.getKeys({
        //     "keyName" : "CHUNGTU_ID",
        //     "numBlockSize" : 1,
        //     "numRetry":  10
        //   })
        this.vchungtu_id = 0
      }
      row.CHUNGTU_ID = this.vchungtu_id
      if (themmoi){
        if (this.kieugoi_id == 1){
          this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV3({
            "khoGiaoId": parseInt(this.params.p_TuKho),
            "khoNhanId": parseInt(this.params.p_DenKho),
            "loaiCtId": parseInt(this.params.p_LoaiChungTu),
            "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
            "donViNdId": this.$auth.getDonViID(),
            "maNd": this.$auth.getUserName(),//ttnd.ma_nd
          })
          row.LOAICT_ID = parseInt(this.params.p_LoaiChungTu)
        }
        else{
          this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV3({
            "khoGiaoId": parseInt(this.params.p_TuKho),
            "khoNhanId": parseInt(this.params.p_DenKho),
            "loaiCtId": 1,
            "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
            "donViNdId": this.$auth.getDonViID(),
            "maNd": this.$auth.getUserName(),//ttnd.ma_nd
          })
          row.LOAICT_ID = 1
        }
      }
      else{
        if (this.f_StringToDate(this.gridviewChungTu_FocusedRow["NGAY_CT"],'DD-MM-YYYY hh:mm:ss').getFullYear().toString() != this.params.p_dtpNgayDK.getFullYear().toString()){
          if (this.kieugoi_id == 1){
            this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV3({
              "khoGiaoId": parseInt(this.params.p_TuKho),
              "khoNhanId": parseInt(this.params.p_DenKho),
              "loaiCtId": parseInt(this.params.p_LoaiChungTu),
              "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
              "donViNdId": this.$auth.getDonViID(),
              "maNd": this.$auth.getUserName(),//ttnd.ma_nd
            })
          }
          else{
            this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV3({
              "khoGiaoId": parseInt(this.params.p_TuKho),
              "khoNhanId": parseInt(this.params.p_DenKho),
              "loaiCtId": 1,
              "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
              "donViNdId": this.$auth.getDonViID(),
              "maNd": this.$auth.getUserName(),//ttnd.ma_nd
            })
          }
        }
        if (this.kieugoi_id == 2){
          row.LOAICT_ID = 1
        }
        else{
          row.LOAICT_ID = this.params.p_LoaiChungTu
        }
      }
      row.SOPHIEU = this.params.p_txtSoPhieu
      row.MUCDICH_ID = parseInt(this.params.p_MucDich)
      row.KHO_GIAO_ID = parseInt(this.params.p_TuKho)
      let xuat_hoantra = false
      if (this.ds_md.length){
        var t = this.ds_md.filter(a=>a.mucDichId == this.params.p_MucDich)
        if (t.length > 0 && t[0]["maMd"] == "X.HTVT"){
          if(this.params.p_LoaiChungTu == "2" || this.params.p_LoaiChungTu == "3"){
            xuat_hoantra = true
          }
        }
      }
      if (!xuat_hoantra){
        row.KHO_NHAN_ID = parseInt(this.params.p_DenKho)
      }
      row.NHANVIEN_GIAO_ID = this.$auth.getNhanVienID()
      row.DONVI_NHAN_ID = this.$auth.getDonViID()
      row.DONVI_GIAO_ID = this.$auth.getDonViID()
      row.NGUOI_CN = this.$auth.getUserName()
      if (traphieu){
        row.NGUOI_CN = this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["NGUOI_CN"] || ""
        row.NHANVIEN_GIAO_ID = this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["NHANVIEN_GIAO_ID"] || this.$auth.getNhanVienID()
        row.DONVI_NHAN_ID = this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["DONVI_NHAN_ID"] || this.$auth.getDonViID()
        row.DONVI_GIAO_ID = this.gridviewChungTu_FocusedRow && this.gridviewChungTu_FocusedRow["DONVI_GIAO_ID"] || this.$auth.getDonViID()
      }
      if (this.params.p_CongTrinh != null && this.params.p_CongTrinh != 0){
        row.CONGTRINH_ID = parseInt(this.params.p_CongTrinh)
      }
      row.ND_GIAO = this.params.p_txtNoiDung
      if (this.params.p_dtpNgayDK.getHours() == 0 && this.params.p_dtpNgayDK.getMinutes() == 0){
        this.params.p_dtpNgayDK = this.params.p_dtpNgayDK.setMinutes(this.params.p_dtpNgayDK.getMinutes() + 1)
      }
      row.NGAY_CT = this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY hh:mm:ss')
      row.TTPH_ID = 1
      row.LOAI = this._vloai
      row.NGAY_CN = this.f_DateToString(new Date(),'DD/MM/YYYY')
      row.IP_CN = '10.59.90.123'//ttnd.ip
      row.MAY_CN = this.$auth.getUserName()//ttnd.may_cn;

      this.chungtuData.push(row)
    },
    async TaoDuLieu_CT(){
      this.chitietCTData_insert = []
      this.chitietCTData_update = []
      for (let dr of this.dtVatTu){
        if (dr["CTCT_ID"].toString() == "0"){
          let ri = {}
          // this.ctct_id = await this.getKeys({
          //   "keyName" : "CTCT_ID",
          //   "numBlockSize" : 1,
          //   "numRetry":  10
          // })
          this.ctct_id = 0
          ri.CTCT_ID = this.ctct_id
          ri.VATTU_ID = parseInt(dr["VATTU_ID"])
          ri.CHUNGTU_ID = this.vchungtu_id
          ri.LOHANG = dr["LOHANG"]
          ri.DONGIA = parseInt(dr["DONGIA"])
          ri.TIEN = Math.round(parseInt(dr["TIEN"]))
          ri.VAT = parseInt(dr["VAT"])
          ri.SOLUONG = parseInt(dr["SOLUONG"])
          if(dr["SOTHANG_PB"].toString()){
            ri.SOTHANG_PB = parseInt(dr["SOTHANG_PB"])
          }
          ri.NGUOI_CN = this.$auth.getUserName()
          ri.NGAY_CN = this.f_DateToString(new Date(),'DD/MM/YYYY hh:mm:ss')
          ri.MAY_CN = this.$auth.getUserName //ttnd.may_cn
          ri.IP_CN = '10.59.90.123' //ttnd.ip
          this.chitietCTData_insert.push(ri)
        }
        else{
          let ru = {}
          ctct_id = parseInt(dr["CTCT_ID"])
          ru.CTCT_ID = this.ctct_id
          ru.VATTU_ID = parseInt(dr["VATTU_ID"])
          ru.CHUNGTU_ID = this.vchungtu_id
          ru.LOHANG = dr["LOHANG"].toString()
          ru.DONGIA = parseInt(dr["DONGIA"])
          ru.TIEN = Math.round(parseInt(dr["TIEN"]))
          ru.VAT = parseInt(dr["VAT"])
          ru.SOLUONG = parseInt(dr["SOLUONG"])
          if(dr["SOTHANG_PB"].toString()){
            ru.SOTHANG_PB = parseInt(dr["SOTHANG_PB"])
          }
          ru.NGUOI_CN = this.$auth.getUserName()
          ru.NGAY_CN = this.f_DateToString(new Date(),'DD/MM/YYYY hh:mm:ss')
          ru.MAY_CN = this.$auth.getUserName //ttnd.may_cn
          ru.IP_CN = '10.59.90.123' //ttnd.ip
          this.chitietCTData_update.push(ru)
        }
      }
      

    },
    async TaoDuLieu_Tbi(){
      let chungtu_tbiData = []
      for (var item of this.dtThietBi){
        let ri = {}
        let vthietbi_id = 0

        if(item["THIETBI_ID"].toString() != "0"){
          vthietbi_id = parseInt(item["THIETBI_ID"])
        }
        else{
          vthietbi_id = await this.getKeys({
            "keyName" : "THIETBI_ID",
            "numBlockSize" : 1,
            "numRetry":  10
          })
        }

        ri.THIETBI_ID = vthietbi_id
        ri.SERIAL = item["SERIAL"].toString()
        ri.MA_TBI = item["MA_TBI"].toString()
        ri.DONGIA = parseInt(item["DONGIA"])
        let vctct_id = 0

        let lohang = item["LOHANG"].toString()
        let vattu_id = item["VATTU_ID"].toString()

        var a = this.chitietCTData_insert.filter(p=>p.LOHANG.toString() == lohang && p.VATTU_ID.toString() == vattu_id)
        if (a.length){
          vctct_id = parseInt(a[0]["CTCT_ID"])
        }
        else{
          var b = this.chitietCTData_update.filter(p=>p.LOHANG.toString() == lohang && p.VATTU_ID.toString() == vattu_id)
          if (b.length){
            vctct_id = parseInt(b[0]["CTCT_ID"])
          }
        }
        ri.CTCT_ID = vctct_id
        chungtu_tbiData.push(ri)
      }
      return chungtu_tbiData
    },
    async KiemTraHanChotCT(){
      let vkho_nhan_id = 0
      let vkho_giao_id = 0
      let ngay_ct = this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY')

      if (this.params.p_TuKho.toString()){
        vkho_giao_id = parseInt(this.params.p_TuKho)
      }
      if (this.params.p_DenKho.toString()){
        vkho_nhan_id = parseInt(this.params.p_DenKho)
      }
      let s = await this.KiemTraHanChungTu({
        "chungTuId": this.vchungtu_id,
        "khoGiaoId": vkho_giao_id,
        "khoNhanId": vkho_nhan_id,
        "ngayCt": ngay_ct
      })
      
      if (s.toString() != "1"){
        this.$toast.error("Đã quá hạn sửa chứng từ!")
        return false
      }
      return true
    },
    async tsbtnThemVT_Click(){
      if (this.vma_md == this.MUCDICH_VT.MUASAM){
        this.$toast.error("Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép chọn vật tư")
        return
      }
      if (this.vma_md == this.MUCDICH_VT.CHUYEN_CP){
        this.$toast.error("Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép chọn vật tư")
        return
      }

      // quy bo sung thong bao cho muc dich
      if (this.params.p_MucDich == null || this.params.p_MucDich == ''){
        this.$toast.error("Bạn chưa chọn mục đích!")
        return
      }
      
      if (this.kieugoi_id == 1){
        let mamd = ""
        let mucdich_id = this.params.p_MucDich || 0
        var d = []
        this.params.p_MucDich ? d = await this.getMaMucDichTheoID({mucDichId: mucdich_id}) : ''
        if (d.length){
          mamd = d[0].toString()
        }
        if (mamd == "CCDC.TL"){}
        else{
          // quy bo sung thong bao cho kho
          if (this.params.p_TuKho == null || this.params.p_TuKho == ''){
            this.$toast.error("Bạn chưa chọn từ kho!")
            return
          }

          this.vtdk = []
          var f = this.$refs.frmVatTuTrongKho
          await f.Clear()
          f.Visible.tsbtnXuatExcelTbi = false
          f.vkho_id = parseInt(this.params.p_TuKho || 0) 
          f.tt = 0
          try {
            var a = await this.getIDLoaiKho({khoId: parseInt(this.params.p_TuKho || 0)})
            if (a.length){ 
              if (a[0].toString() == this.LOAI_KHO.KHO_TOTRUONG.toString()){
                f.tt = 1
              } 
            }  
          } catch (error) {
            f.tt = 0
          }
          f.kieu = 1
          f.dtLuuVT = this.dtLuuVT
          this.ten_khotu = this.TenTuKho(this.params.p_TuKho)
          f.vngay_ct = this.params.p_dtpNgayDK
          if (!this.Enabled.tsbtnNhapMoi){
            f.vchungtu_id = 0
          }
          else{
            f.vchungtu_id = this.vchungtu_id
          }
          f.vkho_den_id = 0
          if (this.params.p_DenKho != null){
            f.vkho_den_id = parseInt(this.params.p_DenKho)
          }
          f.functionName = 'tsbtnThemVT_Click'
          await f.ShowDialog()
          // XacNhan_frmVatTuTrongKho
        }

      }
      else{
        // Chỉ chọn Vật tư
        var f = this.$refs.frmDSVatTu
        await f.resetParams()
        let mucdich_id = parseInt(this.params.p_MucDich || 0)
        let zvma_md = ""
        var d = []
        this.params.p_MucDich ? d = await this.getMaMucDichTheoID({mucDichId: mucdich_id}) : ''
        if (d.length){
          zvma_md = d[0].toString()
        }
        f.ma_md = zvma_md
        f.kieu = 1
        f.dtLuuVT = this.dtLuuVT
        await f.ShowDialog()
      }

    },
    async XacNhan_frmVatTuTrongKho(){
      let f = this.$refs.frmVatTuTrongKho
      if (f.XacNhan && f.functionName == 'tsbtnThemVT_Click'){

        this.dtLuuVT = f.dt
        this.dtThemVT = []
        for (var item of f.dt){
          let r = {}
          r.TATCA = f.checkAll ? 1: 0
          r.CTCT_ID = 0
          r.LOHANG = item.LOHANG.toString()
          r.VATTU_ID = parseInt(item.VATTU_ID)
          r.MA_VT = item.MA_VT.toString()
          r.TEN_VT = item.TEN_VT.toString()
          r.DVI_TINH = item.DVI_TINH.toString()
          r.SOLUONG= parseInt(item.SL_XUAT)
          r.DONGIA = parseInt(item.DONGIA)
          r.TIEN = parseInt(item.SL_XUAT)* parseInt(item.DONGIA)
          r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
          r.LOAI_TBI = item.LOAI_TBI.toString()
          r.LOAITBI_ID = item.LOAITBI_ID
          r.TRANGTHAI = item.hasOwnProperty('TRANGTHAI') ? item["TRANGTHAI"] : ""
          this.dtThemVT.push(r)
        }
        this.gridVatTu = this.dtThemVT
        this.khotu_id = f.vkho_id
        this.Enabled.gridViewVatTu.SOLUONG = true
        this.kieunhap = 2
        if (f.coDS){
          this.vtdk = f.ds_dangky
        }
      }
      if (f.XacNhan && f.functionName == 'LAY_CHUNGTU'){
        this.dtLuuVT = f.dt
      let dtThemVT_v2 = []
      for (var item of f.dt){
        let r = {}
        r["TATCA"] = f.checkAll ? 1 : 0;
        r["CTCT_ID"] = 0;
        r["LOHANG"] = (item["LOHANG"]).toString();
        r["VATTU_ID"] = Convert.ToInt64((item["VATTU_ID"]).toString());
        r["MA_VT"] = (item["MA_VT"]).toString();
        r["TEN_VT"] = (item["TEN_VT"]).toString();
        r["DVI_TINH"] = (item["DVI_TINH"]).toString();
        r["SOLUONG"] = Convert.ToDecimal(item["SL_XUAT"]);
        r["DONGIA"] = Convert.ToDecimal(item["DONGIA"]);
        r["TIEN"] = Convert.ToDecimal(item["SL_XUAT"]) * Convert.ToDecimal(item["DONGIA"]);
        r["SOTHANG_PB"] = Convert.ToDecimal(item["SOTHANG_PB"]);
        r["LOAI_TBI"] = item["LOAI_TBI"].toString();
        r["LOAITBI_ID"] = item["LOAITBI_ID"];
        r["HAN_BH"] = "";
        
        dtThemVT_v2.push(r)
        }
        for (var dr of dtThemVT_v2){
          dr["CTCT_ID"] = 0
        }
        this.gridVatTu = dtThemVT_v2
        this.dtVatTu = dtThemVT_v2
        this.khotu_id = f.vkho_id
        this.params.p_TuKho = this.khotu_id
        this.Enabled.gridViewVatTu.SOLUONG = true
        this.kieunhap = 4
        this.kieugoi_id = 2
      }
    },
    async XacNhan_frmVatTuTrongKho_V2(){
      var f = this.$refs.frmVatTuTrongKho_V2
      if (f.xacnhan){
        this.gridVTGroup = f.ds_VTGroup
        this.ds_Gan = f.ds_Gan
        this.dtLuuVT = f.dt_VATTU
        for(var item of f.dt_VATTU){
          let r = {}
          r.TATCA = 0
          r.CTCT_ID = 0
          r.LOHANG = item.LOHANG.toString()
          r.VATTU_ID = parseInt(item.VATTU_ID)
          r.MA_VT = item.MA_VT.toString()
          r.TEN_VT = item.TEN_VT.toString()
          r.DVI_TINH = item.DVI_TINH.toString()

          r.SOLUONG= 0
          r.DONGIA = parseInt(item.DONGIA)
          r.TIEN = parseInt(item.DONGIA)
          r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
          r.LOAI_TBI = item.LOAI_TBI.toString()
          r.LOAITBI_ID = item.LOAITBI_ID
          r.KHO_ID = item.KHO_ID
          r.MAY_CN = this.$auth.getUserName()//ttnd.may_cn
          r.NGUOI_CN = this.$auth.getUserName()//ttnd.ma_nd
          r.IP_CN = '10.59.90.123' //ttnd.ip
          r.TY_LE = item.TY_LE.toString()
          r.MAKHOTD = item.MAKHOTD.toString()
          r.LOAIKHO = item.LOAIKHO.toString()
          r.MATHETS = item.MATHETS.toString()
          r.MATHUNG = item.MATHUNG.toString()
          r.GHICHU_HD = item.GHICHU.toString()
          r.CHATLUONG = item.CHATLUONG.toString()
          r.NGUON_GOC = item.NGUON_GOC.toString()
          r.CHATLUONG_ID = ""
          r.DONGTBI_ID = ""
          r.HETHONG_ID = ""
          this.dtThemVT.push(r)
        }
        this.gridVatTu = this.dtThemVT
        this.khotu_id = f.vkho_id
        this.kieunhap = 2
        this.Enabled.gridViewVatTu.SOLUONG = true
      }
    },
    async XacNhan_frmDSVatTu(){
      var f = this.$refs.frmDSVatTu
      if (f.XacNhan){
        this.dtLuuVT = f.dt
        this.dtVT_LE = []
        let lohang = ""
        let zvma_md = ""
        var d = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
        if (d.length){
          zvma_md = d[0].toString()
        }
        let seq_LH = await this.getKeys({
            "keyName" : "LOHANG",
            "numBlockSize" : 1,
            "numRetry":  10
        })
        if (zvma_md == "N.DK"){
          lohang = "DK" + seq_LH
        }
        else if (zvma_md == "N.KDV"){
          lohang = "KDV" + seq_LH
        }
        else if (zvma_md == "N.KDV_DG"){
          lohang = "KDV"
        }
        else{
          lohang = seq_LH
        }

        for(var item of f.dt){
          let r = {}
          let lohang2 = ""
          let lohang_temp = ""
          if (zvma_md != "N.KDV_DG"){
            var q = f.dt.filter(a=>a.VATTU_ID.toString() == item["VATTU_ID"].toString() && (a.LOHANG == item["LOHANG"] || !a.LOHANG)).length
            if (q > 1){
              lohang2 = await this.getKeys({
                  "keyName" : "LOHANG",
                  "numBlockSize" : 1,
                  "numRetry":  10
              })
            }
          }

          r.CTCT_ID = 0
          
          lohang_temp = lohang
          if (lohang2){
            lohang_temp = lohang2
          }
          if  (item["LOHANG"].toString().trim() && zvma_md != "N.DK" && zvma_md != "N.KDV" && zvma_md != "N.KDV_DG"){
            r.LOHANG = await this.tao_lohang(item["LOHANG"].toString(),lohang_temp)
          }
          else if (zvma_md == "N.KDV_DG"){
            r.LOHANG = lohang_temp + await this.getKeys({
                  "keyName" : "LOHANG",
                  "numBlockSize" : 1,
                  "numRetry":  10
              })
          }
          else{
            r.LOHANG = lohang_temp
          }          
          r.MA_VT = item.MA_VT.toString()
          r.VATTU_ID = parseInt(item.VATTU_ID.toString())
          r.TEN_VT = item.TEN_VT.toString()
          r.DVI_TINH = item.DVI_TINH.toString()
          r.SOLUONG= parseInt(item.SOLUONG)
          r.DONGIA = parseInt(item.DONGIA)
          r.TIEN = parseInt(item.TIEN)
          
          r.LOAI_TBI = item.LOAI_TBI.toString()
          r.LOAITBI_ID = item.LOAITBI_ID

          if (item.HAN_BH){
            r.HAN_BH = item.HAN_BH//dd/MM/yyyy
          }
          
          this.dtVT_LE.push(r)
        }
        this.gridVatTu = this.dtVT_LE
        this.kieunhap = 3; //Nhap lẻ
      }
    },
    async tao_lohang(prefix,lohang){
      var d = await this.getMaPrefixLoHang({loHang: prefix})
      if (d.length){
        if (d[0][0].toString() == "1"){
          return prefix.trim() + lohang
        }
        else{
          return prefix.trim()
        }
      }
      return lohang
    },
    async tstbtnDonHang_Click(){
      if (this.vma_md.toString() == this.MUCDICH_VT.MUASAM.toString()){
        this.$toast.error("Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép chọn từ đơn hàng")
        return 
      }
      if (this.vma_md.toString() == this.MUCDICH_VT.CHUYEN_CP.toString()){
        this.$toast.error("Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép chọn từ đơn hàng")
        return
      }
      var f = this.$refs.frmDonHangTT
      f.dataResponse.DSVatTuChiTiet = []
      await f.loadDataDonHang()
      this.$refs.popup_frmDonHangTT.show()
      
      
      // XacNhan_frmDonHangTT()
    },
    async XacNhan_frmDonHangTT(){
      var f = this.$refs.frmDonHangTT
      if (f.XacNhan){
        this.dtDonHang = []
        let lohang = await this.getKeys({
            "keyName" : "LOHANG",
            "numBlockSize" : 1,
            "numRetry":  10
        })
        for(var item of f.dt){
          let r = {}
          r.CTCT_ID = 0
          r.CTDH_ID = parseInt(item.CTDH_ID)
          r.LOHANG = lohang
          r.MA_VT = item.MA_VT.toString()
          r.TEN_VT = item.TEN_VT.toString()
          r.DVI_TINH = item.DVI_TINH.toString()
          r.SOLUONG= parseInt(item.SL_CHUAN)
          r.DONGIA = parseInt(item.DONGIA)
          r.TIEN = Math.round(parseInt(item.SL_CHUAN)*parseInt(item.DONGIA))
          r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
          r.LOAI_TBI = item.LOAI_TBI.toString()
          r.LOAITBI_ID = item.LOAITBI_ID
          
          this.dtDonHang.push(r)
        }
        this.gridVatTu = this.dtDonHang
        this.params.p_TuKho = f.kho_id
        this.kieunhap = 1; //Nhap tu don hang
        this.Enabled.gridViewVatTu.SOLUONG = false
        this.ngay_dh = f.ngay_nhap
        this.params.p_dtpNgayDK = new Date()
        let nk = ""
        if (f.ngay_ky != ""){
          nk = " ngày " + f.ngay_ky
        }
        let tenTuKho = ''
        if (this.params.p_TuKho){
          if (this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho)){
            tenTuKho = this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).tenKho
          }
        }
        this.nddh = "Nhập vật tư hợp đồng : " + f.ma_hd + ", " + nk + ", số hóa đơn " + f.so_hd + " , từ " + tenTuKho
        
        await this.KhoiTaoNoiDung()
      }
    },
    async btnXoaVT_Click(){
      // DHSX đang comment
    },
    async btnXoaVT_ButtonClick(row){
      await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa vật tư của chứng từ này!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                // let gridViewVatTu_FocusedRow = this.$refs.gridViewVatTu.getSelectedIndexs()
                // this.$refs.gridViewVatTu.deleteRecord(gridViewVatTu_FocusedRow)
                let deletedRow = this.gridVatTu.splice(row.index,1)
                let id = deletedRow.CTCT_ID
                if (id.toString()){
                  await this.XoaChiTietChungTu({ctctId: id})
                }
              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async txtExcelTbiMau_Click(){

      let dt = [{
          'LOHANG': "",
          'MA_VT': "",
          'TEN_VT': "",
          'DVI_TINH': "",
          'MA_TBI': "",
          'SERIAL': "",
          'SOLUONG': "",
          'DONGIA': "",
          'GHICHU': "",
          'SERIAL_GP': "",
        }]
      if (this.diachi_mac){
        dt[0].MAC = ''
      }
      if (this.b_mathung){
        dt[0].MATHUNG = ''
      }

      if (this.vchungtu_id > 0 && this.dtVatTu.length){
        let a = this.dtVatTu.filter(p=>p.LOAITBI_ID.toString() == "2")
        // quy bo sung, neu co du lieu thi xoa header mac dinh
        a.length ? dt = [] : ''
        for (var item of a){
          let sl = parseInt(item.SOLUONG)
          for (let i = 0; i < sl; i++){
            let r = {
              LOHANG: item.LOHANG.toString(),
              MA_VT: item.MA_VT.toString(),
              TEN_VT: item.TEN_VT.toString(),
              DVI_TINH: item.DVI_TINH.toString(),
              MA_TBI: "",
              SERIAL: "", 
              SOLUONG: 1,
              DONGIA: parseInt(item.DONGIA),
              GHICHU: "",
              SERIAL_GP: "",
            }
            if (this.diachi_mac){
              r.MAC = ""
            }
            if (this.b_mathung){
              r.MATHUNG = ""
            }
            dt.push(r)
          }
        }
      }
      
      let data1 = XLSX.utils.json_to_sheet(dt);
      let wb = XLSX.utils.book_new(); // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, "ThietBi"); // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, "fileNhapTbi.xlsx");
    },
    async btnExcelTbi_Click(){
      if (this.vchungtu_id == 0){
        this.$toast.error("Bạn chưa nhập chứng từ! Hãy nhập thông tin chứng từ và ghi lại, trước khi nhập thiết bị!")
        return
      }
      if(this.Enabled.tsbtnNhapMoi){
        if (this.soph.startsWith("QT")){
            this.$toast.error("Chứng từ quyết toán không thể sửa!")
            return
        }
        let KTra_SuaThuHoi = await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})
        if(KTra_SuaThuHoi.includes("ko")){
          this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
          return
        }
        if (this.kiemtra_quyen){
          var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
          if (!ds_dt.length){
            this.$toast.error("Không có dữ liệu ngày nhận không thể xóa")
            return
          }
          else{
            if(!ds_dt[0].ngayNhanTt){
              this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể nhập thiết bị")
              return
            }
          }
          if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
        }
      }

      if (!this.dtVatTu.length){
        this.$toast.error("Bạn chưa nhập vật tư chứng từ! Hãy nhập vật tư chứng từ và ghi lại, trước khi nhập thiết bị!") 
        return
      }
      // this.$refs.fileUpload.$el.click()
      document.getElementById('fileUpload').click()
      // đọc file
      // OpenFileDialog
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error("Chưa chọn file nào!")
        return false
      }
      if (
        this.arrayImportFile.filexcel.type !=
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
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
            
            
            await this.CapNhatExcelTbi_v2(JSON.parse(JSON.stringify(result))) 
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
    async CapNhatExcelTbi_v2(dt){
      
      if (this.diachi_mac && dt.length && !dt[0].hasOwnProperty("MAC")){
        this.$toast.error("Không tìm thấy cột địa chỉ MAC. Bạn hãy lấy lại file mẫu")
        return
      }
      this.loading(true)
      let loi = ""
      
      let updateSerialArray = []
      for (var item of dt){
        let data = {
            "loHang": item.LOHANG,
            "maVatTu": item.MA_VT,
            "tenVatTu": item.TEN_VT,
            "donViTinh": item.DVI_TINH,
            "maThietBi": item.MA_TBI,
            "serial": item.SERIAL,
            "soLuong": 1,
            "ghiChu": item.GHICHU,
            "serialGp": item.SERIAL_GP,
            // "mac": "",
            // "maThung": item.MATHUNG
          }
        this.diachi_mac ? data.mac = item["MAC"] : ""
        this.b_mathung ? data.maThung = item["MATHUNG"] : ""

        updateSerialArray.push(data)
      }
      
      // try {
      //   await this.CapNhatSerialV2_1(updateSerialArray) 
      //   } catch (error) {
      //   loi = error
      // }
      
       
      let dt1 = []
      try {
        dt1 = await this.CapNhatSerialV2({
          "chungTuId": this.vchungtu_id,
          "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
          "mayCn": this.$auth.getUserName(),//ttnd.may_cn
          "ipCn": "10.59.90.123",//ttnd.ip
          "listSerialInfo": updateSerialArray
        })
      } catch (error) {
        loi = error
      }
      if (loi != ""){
        this.$toast.error("File excel đọc vào bị lỗi " + loi)
        return
      }
      
      if (dt1.length){
        this.$toast.error("Có " + dt1.length + " bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!")
        await this.SaveXlsAndOpenFile("fileSaiDuLieu.xlsx", "sheet1", dt1)
      }
      else{
        await this.Load_ds_thietbi()
        await this.load_tinhtrang(1)
      }
      this.loading(false)
    },
    async SaveXlsAndOpenFile(fileName,sheetName,exportData){
      if (!exportData.length){
        this.$toast.error("Không có bản ghi nào để xuất!")
        return 
      }
      let data1 = XLSX.utils.json_to_sheet(exportData);
      let wb = XLSX.utils.book_new(); // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, sheetName); // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, fileName);
    },
    async CapNhatExcelTbi(dt){
      // try {
        //Kiểm tra dữ liệu thiết bị
        let dtSai = []
        let dt1 = await this.getDSThietBiChungTu({chungTuId: 0})
        for(var item of dt){
          let ghichu = ""
          let vattu_id = 0
          let dvitinh_id = 0
          let vctct_id = 0
          let vthietbi_id = 0
          let vthietbi_cha_id = 0
          let ma_vt = item["MA_VT"].toString().trim()
          let lohang = item["LOHANG"].toString().trim()
          let ma_tbi = item["MA_TBI"].toString().trim()
          let serial = item["SERIAL"].toString().trim()
          let dvi_tinh = item["DVI_TINH"].toString().trim()
          let soluong = 0
          let dongia = 0
          let ten_vt = item["TEN_VT"].toString().trim()

          if (item["SOLUONG"].toString()){
            soluong = parseInt(item["SOLUONG"]) 
          }
          //Kiểm tra mã vật tư có tồn tại trong db không
          let dtkt = await this.KiemTraMaVatTu({maVt: ma_vt})
          if (!dtkt.length){
            ghichu = "Mã vật tư không tồn tại; "
          }
          else{
            vattu_id = parseInt(dtkt[0]["vatTuID"])
            ten_vt = dtkt[0]["tenVatTu"]
          }
          // Kiểm tra đơn vị tính
          dtkt = await this.KiemTraDonViTinh({donViTinh: dvi_tinh.toUpperCase().trim()})
          if (!dtkt.length){
            ghichu = "Đơn vị tính không tồn tại; "
          }
          else{
            dvitinh_id = dtkt[0]["dviTinhId"]
            dvi_tinh = dtkt[0]["dviTinh"]
          }
          //Kiểm tra mã vật tư, lô hàng có tồn tại trong chứng từ không
          dtkt = await this.KiemTraTonVatTuCT({
            "chungTuId": this.vchungtu_id,
            "vatTuId": vattu_id,
            "loHang": lohang
          })
          if (!dtkt.length){
            ghichu = "Vật tư và lô hàng không tồn tại trong chứng từ; "
          }
          else{
            vctct_id = dtkt[0]["chiTietCtId"]
            dongia = dtkt[0]["donGia"]
          }
          if (ghichu){
            let rsai = []
            for(var i = 0; i < dt.length;i++){
              rsai[i] = item[i]
            }
            // rsai["GHICHU_LOI"] = ghichu
            dtSai.push(...rsai)
          }
          else{
            let r = {}
            r["CTCT_ID"] = vctct_id
            r["LOHANG"] = lohang
            r["VATTU_ID"] = vattu_id
            r["TEN_VT"] = ten_vt
            r["DVITINH_ID"] = dvitinh_id
            r["DVI_TINH"] = dvi_tinh
            r["MA_TBI"] = ma_tbi
            r["SERIAL"] = serial
            r["SOLUONG"] = soluong
            r["DONGIA"] = dongia
            dt1.push(r)
          }
        }
        for (var table of dt1){
          await this.CapNhatChungTuThietBi({
            "ctctId": table.CTCT_ID,
            "thietBiId": table.THIETBI_ID || 0,
            "thietBiChaId": table.THIETBI_CHA_ID || 0,
            "maThietBi": table.MA_TBI,
            "serial": table.SERIAL,
            "soLuong": table.SOLUONG,
            "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
            "mayCn": this.$auth.getUserName(),//ttnd.may_cn
            "ipCn": "10.59.90.123"//ttnd.ip
          })
        }
        await this.Load_ds_thietbi()
        if (dtSai.length){
          this.$toast.error("Có " + dtSai.length + " bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!")
          await this.SaveXlsAndOpenFile('fileSaiDuLieuThietBi.xlsx',"sheet1",dtSai)
        }
        
      // } catch (error) {
      //   this.$toast.error("File excel không đúng định dạng!: ")
      // }
    },
    async btnFileExelMauVT_Click(){
      let dt= [{
        MA_DH: '',
        LOHANG: '',
        MA_VT: '',
        TEN_VT: '',
        DVI_TINH: '',
        MA_TBI: '',
        SERIAL: '',
        SOLUONG: 0,
        DONGIA: 0,
        SOTHANG_PB: 0,
      }]
      await this.SaveXlsAndOpenFile("fileNhapVtu.xlsx","ThietBi",dt)
    },
    async btnCapNhatExcelVtu(dt){
      // try {
        //Kiểm tra dữ liệu thiết bị
        let dtSai = []
        for(var item of dt){
          let ghichu = ""
          let vattu_id = 0
          let dvitinh_id = 0
          let vctct_id = 0
          let thietbi_id = 0
          if(item.hasOwnProperty('THIETBI_ID')){
            thietbi_id = parseInt(item.THIETBI_ID)
          }
          let ma_vt = item["MA_VT"].toString().trim()
          let lohang = item["LOHANG"].toString().trim()
          let ma_tbi = item["MA_TBI"].toString().trim()
          let serial = item["SERIAL"].toString().trim()
          let dvi_tinh = item["DVI_TINH"].toString().trim()
          let soluong = 0
          let dongia = 0
          let ten_vt = item["TEN_VT"].toString().trim()

          if (item["SOLUONG"].toString()){
            soluong = parseInt(item["SOLUONG"]) 
          }
          if (item["DONGIA"].toString()){
            soluong = parseInt(item["DONGIA"]) 
          }
          //Kiểm tra mã vật tư có tồn tại trong db không
          let dtkt = await this.KiemTraMaVatTu({maVt: ma_vt})
          if (!dtkt.length){
            ghichu = "Mã vật tư không tồn tại; "
          }
          else{
            vattu_id = parseInt(dtkt[0]["vatTuID"])
            ten_vt = dtkt[0]["tenVatTu"]
          }
          // Kiểm tra đơn vị tính
          dtkt = await this.KiemTraDonViTinh({donViTinh: dvi_tinh.toUpperCase().trim()})
          if (!dtkt.length){
            ghichu = "Đơn vị tính không tồn tại; "
          }
          else{
            dvitinh_id = dtkt[0]["dviTinhId"]
            dvi_tinh = dtkt[0]["dviTinh"]
          }
          if (ghichu){
            let rsai = []
            for(var i = 0; i < dt.length;i++){
              rsai[i] = item[i]
            }
            // rsai["GHICHU"] = ghichu
            dtSai.push(...rsai)
          }
          else{
            //Nhập thiết bị
            if(!this.dtThietBi.length){
              this.dtThietBi = await this.getDSThietBiChungTu({chungTuId: 0})
            }
            let r = {}
            r["CTCT_ID"] = vctct_id
            r["THIETBI_ID"] = thietbi_id
            r["LOHANG"] = lohang
            r["VATTU_ID"] = vattu_id
            r["TEN_VT"] = ten_vt
            r["DVITINH_ID"] = dvitinh_id
            r["DVI_TINH"] = dvi_tinh
            r["MA_TBI"] = ma_tbi
            r["SERIAL"] = serial
            r["SOLUONG"] = soluong
            r["DONGIA"] = dongia
            this.dtThietBi.push(r)
          }
        }
        // Cập nhật vật tư
         var vt = dtThietBi.filter(p=>
         p["LOHANG"].toString() == lohang.toString() && 
         p["VATTU_ID"].toString() == vattu_id.toString() && 
         p["MA_VT"].toString() == ma_vt.toString() && 
         p["TEN_VT"].toString() == ten_vt.toString() && 
         p["DVITINH_ID"].toString() == dvitinh_id.toString() && 
         p["DVI_TINH"].toString() == dvi_tinh.toString() && 
         p["MA_TBI"].toString() == ma_tbi.toString() &&
         p["SERIAL"].toString() == serial.toString() &&
         p["SOLUONG"].toString() == soluong.toString() &&
         p["DONGIA"].toString() == dongia.toString()
         )
         for (var item of vt){
            if (this.dtVatTu.length){
              this.dtVatTu = getDSTraCuuVatTuChungTu({chungTuId: 0})
            }
            let r = {}
            r["CTCT_ID"] = 0
            r["LOHANG"] = item.LOHANG
            r["VATTU_ID"] = item.VATTU_ID
            r["SOLUONG"] = item.SOLUONG
            r["DONGIA"] = item.DONGIA
            r["TIEN"] = parseInt(item.DONGIA)*parseInt(item.SOLUONG)
            r["VAT"] = parseInt(item.DONGIA)*parseInt(item.SOLUONG)*0.1
            r["MA_VT"] = item.MA_VT
            r["TEN_VT"] = item.TEN_VT
            r["DVITINH_ID"] = item.DVITINH_ID
            r["DVI_TINH"] = item.DVI_TINH
            this.dtVatTu.push(r)  
         }

         if(this.dtSai.length){
           this.$toast.error("Có " + dtSai.length + " bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!")
           await this.SaveXlsAndOpenFile("fileSaiDuLieuThietBi.xlsx","sheet1",dtSai)
         }
         this.gridVatTu = this.dtVatTu
         this.gridThietBi = this.dtThietBi

      // } catch (error) {
      //   this.$toast.error("File excel không đúng định dạng!:")
      // }
    },
    async btnNhapExcelVT_Click() {
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
                return jsonRow;
            });
            
            await this.btnCapNhatExcelVtu(result)
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
          
        }
      } catch (error)  {
        this.$toast.error("Có lỗi xảy ra: " + error)
        return
      } finally {
        this.loading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    async btnXoaHetVT_Click(){
      if(!this.gridVatTu.length){
        this.$toast.error("Không có dữ liệu vật tư để xóa")
        return
      }
      if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id)){ return }
      if (! await this.KiemTraHanChotCT()){ return }
      
      if (this.Enabled.tsbtnNhapMoi){
        if (! await this.KiemTraNguoiCN()){
          this.$toast.error("Bạn không được sửa chứng từ của người khác!")
          this.$refs.cboLoaiChungTu.focusIn()
          return
        }
      }
    
      if (this.Enabled.tsbtnNhapMoi){
        if(this.kiemtra_quyen){
          var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
          if (!ds_dt.length){
            this.$toast.error("Không có dữ liệu ngày nhận không thể xóa")
            return
          }
          else{
            if(ds_dt[0].toString()){
              this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị")
              return
            }
          }
          if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
        }
        if (this.soph.startsWith("QT")){
          this.$toast.error("Chứng từ quyết toán không thể sửa!")
          return
        }
        if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko")){
          this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
          return
        }
        let s = await this.KiemTraCapNhatChungTu({chungTuId: this.vchungtu_id})
        if (s != "1"){
          this.$toast.error(s)
          return
        }
      }

      await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa hết vật tư của chứng từ này!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                if (!this.Enabled.tsbtnNhapMoi){
                  // for (int i = 0; i < gridViewVatTu.RowCount; )
                  //   gridViewVatTu.DeleteRow(i);
                  this.gridVatTu = []
                  return
                }

                if (this.ttph_id == 4){
                  let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
                  if (s != "1"){
                    this.$toast.error(s)
                    return
                  }
                  s = await this.XoaPhieuDaHoanThanh({
                    "chungTuId": this.vchungtu_id,
                    "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                    "mayCn": this.$auth.getUserName(),//ttnd.may_cn
                    "ipCn": "10.59.90.123"//ttnd.ip
                  })
                  if (s != "1"){
                    this.$toast.error("Có lỗi trong quá trình xóa phiếu!")
                    return
                  }
                }

                let k = await this.XoaChungTuBaoHanh({
                  "kieu": 2,
                  "chungTuId": this.vchungtu_id,
                  "chungTuCtId": 0,
                  "thietBiId": 0
                })
                if (k != "1"){
                  this.$toast.error(k)
                  return
                }
                let id = this.gridviewChungTu_FocusedRow.CTCT_ID
                this.dtVatTu = []
                this.dtThietBi = []

                await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
                let cttemp_id = this.vchungtu_id
                await this.Load_ds_chungtu()
                await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", cttemp_id)
              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async KIEMTRA_THAYDOI(vct_id){
      // try {
          var xttph = await this.getThongTinTrangThaiPhieu({chungTuId: vct_id})
          if (!xttph.length){
            this.$toast.error("Không lấy được thông tin trạng thái phiếu")
            return false
          }
          if (xttph.length){
            if(this.ttph_id.toString() != xttph[0].ttphId.toString()){
              this.$toast.error("Thông tin trạng thái phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin")
              return false
            }
            
            if(xttph[0].maMd.toString() == this.MUCDICH_VT.MUASAM.toString()){
              this.$toast.error("Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép sửa")
              return false
            }
            if(xttph[0].maMd.toString() == this.MUCDICH_VT.CHUYEN_CP.toString()){
              this.$toast.error("Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép sửa")
              return false
            }
                        
            return true
          }
          return true

      // } catch (error) {
      //   this.$toast.error("Có lỗi khi lấy thông tin trạng thái phiếu")
      //   return false
      // }
    },
    async KIEMTRA_THAYDOI_V2(vct_id,cap){
      // try {
        var xttph = await this.getThongTinTrangThaiPhieu({chungTuId: vct_id})
        if(!xttph.length){
          this.$toast.error("Không lấy được thông tin trạng thái phiếu")
          return false
        }
        if(xttph.length){
          if(this.ttph_id.toString() != xttph[0].ttphId.toString()){
            this.$toast.error("Thông tin trạng thái phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin")
            return false
          }
          return true
        }
        return true
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi lấy thông tin trạng thái phiếu" + " - chúng từ " + this.soph)
      //   return false
      // }
    },
    // Nếu nhập lẻ + kiểu là điều chỉnh ==> tự động hoàn thiện phiếu xuất (là phiếu cha) trước, sau đó hoàn thiện phiếu nhập
    
    async tsbtnHoanThanh_Click(){
      if (! await this.KiemTraHanChotCT()){ return }
      if(!this.params.p_DenKho.toString()){
        this.$toast.error("Không có kho đến không thể hoàn thành")
        return
      }

      if(!this.gridVatTu.length){
        this.$toast.error("Trong chứng từ chưa có kèm vật tư nên không thể hoàn thiện chứng từ.")
        return 
      }
      if (this.kiemtra_quyen){
        if (! await this.KIEMTRA_QUYEN_XN(3, this.kho_nhan_goc)){ return }
        var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
        if(!ds_dt.length){
          this.$toast.error("Không có dữ liệu ngày nhận không thể cập nhật ngày gửi của kho")
          return
        }
        else{
          if(ds_dt[0].ngayNhanTt.toString()){
            this.$toast.error("Chưa có ngày nhận thực tế vật tư không thể hoàn thành")
            return
          }
        }
        let nv_th = this.gridviewChungTu_FocusedRow.NGUOI_TH.toString() || ""
        if (nv_th != "" && nv_th != this.$auth.getUserName()){//ttnd.ma_nd
          this.$toast.error("Bạn không được quyền hoàn thiện chứng từ do người khác xác nhận")
          return
        }
      }

      if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){ return }

      var t = await this.getIDTrangThaiPhieu({chungTuId: this.vchungtu_id})
      if(!t.length){
        this.$toast.error("Không lấy được thông tin chứng từ để hoàn thành")
        return
      }
      if (t[0].toString() == "4"){
        this.$toast.error("Chứng từ đã hoàn thành không thể hoàn thành nữa. Bạn hãy lấy lại thông tin")
      }
      // bổ sung để callback => quy
      let isContinue = true
      var kiemtra_canhbao = await this.KiemTraCanhBaoHoanThienCT({chungTuId: this.vchungtu_id})
      if (kiemtra_canhbao != "1"){
        if (kiemtra_canhbao == "QUES"){
          await this.$bvModal.msgBoxConfirm("Có vật tư chưa gán hợp đồng. Bạn có muốn tiếp tục hoàn thiện chứng từ ?", {
            "okVariant": 'success',
            "okTitle": 'Đồng ý',
            "cancelTitle": 'Hủy',
            "centered": true,
            })
            .then(async(value) => {
                if (!value){
                  isContinue = false
                  return
                }
            })
            .catch(err => {
                // An error occurred
            })
            
        }
        else if (kiemtra_canhbao == "NOTI"){
          isContinue = false
          this.$toast.error("Có vật tư chưa gán hợp đồng. Không thể hoàn thiện chứng từ !")
          return
        }
        else{
          isContinue = false
          this.$toast.error(kiemtra_canhbao)
          return
        }
      }
      if (isContinue){
        var kiemtra_qtth = await this.KiemTraHoanThanhCT({chungTuId: this.vchungtu_id})
        if (kiemtra_qtth.toString() != "1"){
          this.$toast.error(kiemtra_qtth.toString())
          return
        }
        let s = await this.KiemTraHoanThienCT({chungTuId: this.vchungtu_id})
        
        if (s == "1"){
          
          let a = await this.HoanThienChungTu({
            "chungTuId" : this.vchungtu_id,
            "nhanVienId" : this.$auth.getNhanVienID(),
            "nguoiCN" : this.$auth.getUserName(),//ttnd.ma_nd
            "mayCN" : this.$auth.getUserName(),//ttnd.may_cn
            "ipCN" : "10.59.90.123"//ttnd.ip
            })
          
          this.$toast.success("Đã hoàn thiện chứng từ thành công!")
          if (this.params.p_ckbSendSMS){
            try {
              await this.GuiSMSChungTu({
                tuDong: 1,
                chungTuId: this.vchungtu_id,               
              })
            } catch (error) {
              this.$toast.error("Có lỗi khi send smsm tự động")
            }
          }
          let vchungtu_tmp_id = this.vchungtu_id
          await this.Load_ds_chungtu()
          await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)
          await this.Load_ds_vattu()
          await this.Load_ds_thietbi()

        }
        else{
          this.$toast.error(s)
        }
      }
      
    },
    async simpleButton1_Click(){
      
       await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa hết thiết bị của chứng từ này!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                if (! await this.KiemTraHanChotCT()){ return }
                if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){ return }
                if (this.kiemtra_quyen){
                  var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
                  if(!ds_dt.length){
                    this.$toast.error("Không có dữ liệu ngày nhận không thể xóa")
                    return
                  }
                  else{
                    if(ds_dt[0].ngayNhanTt && ds_dt[0].ngayNhanTt.toString()){
                      this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị")
                      return
                    }
                  }
                  if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return}

                }

                if (this.ttph_id == 4){
                  let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
                  if (s != "1"){
                    this.$toast.error(s)
                    return
                  }
                  s = await this.XoaPhieuDaHoanThanh({
                    "chungTuId": this.vchungtu_id,
                    "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                    "mayCn": this.$auth.getUserName(),//ttnd.may_cn
                    "ipCn": "10.59.90.123",//ttnd.ip
                  })
                  if (s != "1"){
                    this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s)
                    return
                  }
                }

                let k = await this.XoaChungTuBaoHanh({
                    "kieu": 3,
                    "chungTuId": this.vchungtu_id,
                    "chungTuCtId": 0,
                    "thietBiId": 0
                  })
                if (k.toString() != "1"){
                  this.$toast.error(k)
                  return
                }
                this.dtThietBi = []
                await this.XoaTatCaChungTuThietBi({chungTuId: this.vchungtu_id})
                
                // quy sửa
                await this.Load_ds_thietbi()
                let cttemp_id = this.vchungtu_id
                await this.Load_ds_chungtu()
                await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", cttemp_id)
                
              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async btnXuatExcelVT_Click(){
      await this.SaveXlsAndOpenFile("VatTu-" + this.params.p_txtSoPhieu + ".xlsx", "sheet1", this.dtVatTu)
    },
    async simpleButton2_Click(){
      await this.SaveXlsAndOpenFile("ThietBi-" + this.params.p_txtSoPhieu.replace("/", "-") + ".xls", "sheet1", this.dtThietBi)
    },
    
    async tsbtnPhieuNhap_Click(){      
      if(this.vchungtu_id == 0 || this.vchungtu_id == null){
        this.$toast.error("Không có dữ liệu in phiếu");
        return
      }
      this.loading(true)
      const baseUrl = process.env.API;
      await this.axios({
        url:
          `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
        method: "POST",
        responseType: "blob",
        data: {
          chungTuId: this.vchungtu_id,
          nhanVien: "",
          donVi: "",
          maBc: "PNK"     
        }
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false)
    },
    async onClickXacNhanInPhieu(){
      this.$bvModal.hide('popupTenNguoiNhan');
      if(this.maBC == "PXK"){

      }else if(this.maBC == "PCK"){

      }
      else if(this.maBC == "PTU"){

      }
      this.loading(true)
      const baseUrl = process.env.API;
      await this.axios({
        url:
          `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
        method: "POST",
        responseType: "blob",
        data: {
          chungTuId: this.vchungtu_id,
          nhanVien: "",
          donVi: "" ,
          maBc: this.maBC  
        }
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false)
    },
    async tsbtnPhieuXuat_Click(){
      if(this.vchungtu_id == 0 || this.vchungtu_id == null){
        this.$toast.error("Không có dữ liệu in phiếu");
        return
      }
     
      if(this.$auth.getPhanVungID().toString() == "54" || this.$auth.getMaTinh().toString() == "TGG"){
        this.tenNguoiNhan = ""
        this.maBC = "PXK"
        this.$bvModal.show('popupTenNguoiNhan')
      }
      else{
        this.loading(true)
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            chungTuId: this.vchungtu_id,
            nhanVien: "",
            donVi: "",
            maBc: "PXK"  
          }
        }).then((response) => {
          previewPrint(response.data);
        });
        this.loading(false)
      }
    },
    async txtQuetSerial_KeyPress(){
      
      let seri = this.params.p_txtQuetSerial.trim().toUpperCase()
      
      let vkho_id = parseInt(this.params.p_TuKho)
      // Kiểm tra seri có tồn tại trong hệ thống không.
      let dt = await this.KiemTraSerialTonTai({serial: seri})
      if(dt.length == 0){
        this.$toast.error("Serial không tồn tại trong hệ thống!")
        return
      }
      if(parseInt(dt[0].slTon) == 0 ){
        this.$toast.error("Serial đã được cấp cho thuê bao!")
        return
      }
      if(dt[0].khoId.toString() != vkho_id){
        this.$toast.error("Serial không nằm trong kho: " + this.TenTuKho(this.params.p_TuKho))
        return
      }
      //Kiểm tra serial có đang đc cấp ở chứng từ nào đang dang dở không
      let dt1 = await this.KiemTraSerialDangCap({serial: seri})
      if(dt1.length){
        this.$toast.error("Trạng thái của serial đang được sử dụng ở chứng từ: " + dt1[0]["soPhieu"] + " ngày " + dt1[0]["ngayCT"])
        return
      }
      // Kiểm tra lô hàng của serial có tồn tại trong chứng từ này không
      let dt2 = await this.KiemTraLoHangCuaSerial({serial: seri,chungTuId: this.vchungtu_id})
      if(!dt2.length){
        this.$toast.error("Lô hàng " + dt[0]["loHang"] + " của Serial không tồn tại trong chứng từ này! ")
        return
      }
      await this.CapNhatChungTuThietBi({
        "ctctId": dt2[0].chiTietCtId,
        "thietBiId": dt[0].thietBiId || 0,
        "thietBiChaId": dt[0].thietBiChaId || 0,
        "maThietBi": dt[0].maTbi,
        "serial": seri,
        "soLuong": 1,
        "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
        "mayCn": this.$auth.getUserName(),//ttnd.may_cn
        "ipCn": "10.59.90.123"//ttnd.ip
      })
      
      await this.Load_ds_thietbi()
      
      this.params.p_txtQuetSerial = ""
    },
    async repositoryItemButtonEdit3_ButtonClick(){
      await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa thiết bị này!", {
        "okVariant": 'success',
        "okTitle": 'Đồng ý',
        "cancelTitle": 'Hủy',
        "centered": true,
        })
        .then(async(value) => {
            if (value){
              this.$refs.gridViewThietBi.deleteRecord(this.gridViewThietBi_FocusedRow)
              let id = this.gridViewThietBi_FocusedRow.THIETBI_ID
              let ct_id = this.gridViewThietBi_FocusedRow.CTCT_ID
              if (id > 0){
                await this.XoaCTThietBiTheoTBi({ctctId: ct_id,thietBiId: id})
              }
            }
        })
        .catch(err => {
            // An error occurred
        })
    },
    
    async KhoiTaoNoiDung(){
      
      if (this.Enabled.tsbtnNhapMoi){ return }
      let nd = ""
      if (this.kieunhap == 1){
        this.params.p_txtNoiDung = this.nddh
      }
      else{
        switch (parseInt(this.params.p_LoaiChungTu)){
          case 1:
              nd = "Nhập từ " + this.TenTuKho(this.params.p_TuKho)
              break;
          case 2:
              nd = "Xuất đến " + this.TenDenKho(this.params.p_DenKho)
              break;
          case 3:
              nd = "Chuyển đến " + this.TenDenKho(this.params.p_DenKho)
              break;
          case 4:
              nd = "Nhập CCDC từ " + this.TenTuKho(this.params.p_TuKho) + " đến " + this.TenDenKho(this.params.p_DenKho)
              break;
          case 99:
              nd = "Nhập lẻ từ " + this.TenTuKho(this.params.p_TuKho) + " đến " + this.TenDenKho(this.params.p_DenKho)
              break;
          default:
              break;
        }
        this.params.p_txtNoiDung = nd
      }
    },
    async KiemTraKho(){
      if (!this.Enabled.tsbtnNhapMoi && !this.params.p_TuKho.toString()){
        k_goc = parseInt(this.params.p_TuKho)
        if(this.$refs.gridViewVatTu.getDataRows().length){
          if(this.params.p_TuKho.toString() != this.khotu_id.toString()){
            await this.$bvModal.msgBoxConfirm(" Nếu bạn thay đổi sang kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).tenKho + " danh sách vật tư đã chọn sẽ được xóa", {
              "okVariant": 'success',
              "okTitle": 'Đồng ý',
              "cancelTitle": 'Hủy',
              "centered": true,
              })
              .then(async(value) => {
                  if (value){
                    this.dtThemVT = []
                    this.gridVatTu = []
                  }
                  else{
                    this.params.p_TuKho = k_goc
                  }
              })
              .catch(err => {
                  // An error occurred
              })
          }
        }
      }

      if(this.Enabled.tsbtnNhapMoi && this.Enabled.tsbtnGhiLai){
        if(this.$refs.gridViewVatTu.getDataRows().length){
          if(this.params.p_TuKho.toString() != this.t_kho_id.toString() && this.t_kho_id != 0){
            await this.$bvModal.msgBoxConfirm("Nếu bạn thay đổi sang kho " + this.TenTuKho(this.params.p_TuKho) + " danh sách vật tư đã chọn sẽ được xóa", {
              "okVariant": 'success',
              "okTitle": 'Đồng ý',
              "cancelTitle": 'Hủy',
              "centered": true,
              })
              .then(async(value) => {
                  if (value){
                    this.dtThemVT = []
                    this.gridVatTu = []
                  }
                  else{
                    this.params.p_TuKho = this.t_kho_id
                  }
              })
              .catch(err => {
                  // An error occurred
              })
          }
        }
      }
    },
    async cboTuKho_EditValueChanged(){
      await this.KhoiTaoNoiDung()
    },
    async cboDenKho_EditValueChanged(){
      await this.KhoiTaoNoiDung()
    },
   
    async gridviewVatTu_btnXoaVT(row){
      if (row){
        this.gridViewVatTu_FocusedRow = row
        
        if (this.copyct == false){
          if (!this.dsQuyen.includes(this.DS_QUYEN_VT.CAPNHAT_CHUNGTU)){
            this.$toast.error("Bạn không có quyền thực hiện chức năng này!")
            return
          }
          if (this.kiemtra_quyen){
            var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
            if(!ds_dt.length){
              this.$toast.error("Không có dữ liệu ngày nhận không thể xóa")
              return
            }
            else{
              if(ds_dt[0].ngayNhanTt != null && ds_dt[0].ngayNhanTt != ''){
                this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị")
                return
              }
            }
            if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
          }
          if (! await this.KiemTraHanChotCT()) { return }
          if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id)) { return }

          if(this.Enabled.tsbtnNhapMoi){
            if (! await this.KiemTraNguoiCN()){
              this.$toast.error("Bạn không được sửa chứng từ của người khác!")
              this.$refs.cboLoaiChungTu.focusIn()
              return
            }
          }


          if(this.Enabled.tsbtnNhapMoi){
            
            if (this.soph.startsWith("QT")){
              this.$toast.error("Chứng từ quyết toán không thể sửa!")
              return
            }
            if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
              this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
              return
            }
            let z = await this.KiemTraCapNhatChungTu({chungTuId: this.vchungtu_id})
            if (z.length && z[0].toString() != "1"){
              this.$toast.error(z)
              return
            }
          }

          await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa vật tư của chứng từ này!", {
              "okVariant": 'success',
              "okTitle": 'Đồng ý',
              "cancelTitle": 'Hủy',
              "centered": true,
              })
              .then(async(value) => {
                  if (value){
                    
                    if(!this.Enabled.tsbtnNhapMoi){
                      this.gridVatTu.splice(this.gridViewVatTu_FocusedRow.index,1)
                      return
                    }
                    
                    if (this.ttph_id == 4){
                      let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
                      if (s.toString() != "1"){
                        this.$toast.error(s)
                        return
                      }
                      s = await this.XoaPhieuDaHoanThanh({
                          "chungTuId": this.vchungtu_id,
                          "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                          "mayCn": "quyPc",//ttnd.may_cn
                          "ipCn": "10.59.90.123",//ttnd.ip
                      })
                      if (s != "1"){
                        this.$toast.error("Có lỗi trong quá trình xóa phiếu! " + s)
                        return
                      }
                    }

                    let index = this.gridViewVatTu_FocusedRow
                    let id = this.gridViewVatTu_FocusedRow.CTCT_ID
                    let k = await this.XoaChungTuBaoHanh({
                      "kieu": 2,
                      "chungTuId": this.vchungtu_id,
                      "chungTuCtId": id,
                      "thietBiId": 0
                    })
                    if (k != "1"){
                      this.$toast.error(k)
                      return
                    }
                    // this.$refs.gridViewVatTu.deleteRecord(row)
                    this.gridVatTu.splice(index,1)
                    if(id > 0){ 
                      await this.XoaChiTietChungTu({ctctId: id})
                      await this.Load_ds_vattu()
                      await this.Load_ds_thietbi()
                    }
                    // view.FocusedColumn = view.Columns[0];
                    if(this.Enabled.tsbtnNhapMoi){
                      let ctemp_id = this.vchungtu_id
                      await this.Load_ds_chungtu()
                      await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", ctemp_id)
                    }


                  }
                  else{
                    return
                  }
              })
              .catch(err => {
                  // An error occurred
              })
        }
        else{
          this.$refs.gridViewVatTu.deleteRecord(row)
          // gridViewVatTu.FocusedColumn = gridViewVatTu.Columns["LOHANG"]
          this.$refs.gridViewVatTu.refreshColumns()
        }
      }
    },
    async FocusGridViewRow(grid, field, value){
      if (grid == 'gridviewChungTu'){
        let indexs = this.gridChungTu.findIndex(a=>a[field] != null && a[field].toString() == value.toString())
        if (indexs != -1) {
          this.$refs[grid].grid.selectRows([indexs])
          this.$refs[grid].grid.flagSelectedRowIndexes = [indexs]
        }
        
      }
    },
    async gridviewVatTu_TachCap(row){
      // Tach Cap khi edit row (da tao chung tu)
      if (row && this.Enabled.tsbtnNhapMoi){
        //Kiem tra de bat form
        if (this.dc_ct_noibo){
          this.$toast.error("Điều chuyển nội bộ công trình không thể tách chọn cuộn cáp")
          return
        }
        if (!this.dsQuyen.includes(this.DS_QUYEN_VT.CAPNHAT_CHUNGTU) && !this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU)){
          this.$toast.error("Bạn không có quyền thực hiện chức năng này!")
          return
        }
        if (! await this.KiemTraHanChotCT()){ return}
        if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){ return }
        if (this.Enabled.tsbtnNhapMoi){
          if (this.kiemtra_quyen){
            if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
          }
          
          if (this.soph.startsWith("QT")){
            this.$toast.error("Chứng từ quyết toán không thể sửa!")
            return
          }
          if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
            this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
            return false
          }
        }
        if(row.LOAITBI_ID.toString() != "3"){
          this.$toast.error("Chức năng này chỉ dành cho loại thiết bị là cuộn cáp")
          return
        }
        let s = await this.getKieuNhapCap({vatTuId: row.VATTU_ID.toString(), 
          khoId: this.params.p_TuKho.toString(),
          loHang: row.LOHANG })
        if (s == "1"){

          // quy them
          let isCallback = true
          // Nhập cáp
          let vctct_id = parseInt(row.CTCT_ID)
          let d = await this.getDanhSachCap({ctctId: vctct_id})
          if (d.toString() != "0"){
            await this.$bvModal.msgBoxConfirm("Cáp này đã được tách. Bạn có muốn tiếp tục tách lại số lượng cuộn không?", {
              "okVariant": 'success',
              "okTitle": 'Đồng ý',
              "cancelTitle": 'Hủy',
              "centered": true,
              })
              .then(async(value) => {
                  // No
                  if (!value){
                    isCallback = false
                  }
              })
              .catch(err => {
                  // An error occurred
              })
          }

          if (isCallback){
            if (this.ttph_id.toString() == "4"){
              let k = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
              if (k.toString() != "1"){
                this.$toast.error(k)
                return
              }
              k = await this.XoaPhieuDaHoanThanh({
                    "chungTuId": this.vchungtu_id,
                    "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                    "mayCn": "quyPc",//ttnd.may_cn
                    "ipCn": "10.59.90.123",//ttnd.ip
                })
              if (k.toString() != "1"){
                this.$toast.error("Có lỗi trong quá trình xóa phiếu!")
                return
              }
            }

            var frm = this.$refs.frmNhapCap
            frm.vctct_id = vctct_id
            frm.tong_sl = row.SOLUONG
            frm.ten_vt = row.MA_VT + " - " + row.TEN_VT
            frm.Open()
          }
        }
        else{
          // Chọn cáp
          var frm = this.$refs.frmGanSeri
          await frm.frmGanSeri_2() // frmGanSeri(1)
          frm.functionName = 'gridViewVatTu_ShownEditor'
          
          frm.Text.title = "Chọn/Cắt cuộn cáp"
          frm.params.p_txtTenKho = this.TenTuKho(this.params.p_TuKho)
          frm.vkho_id = parseInt(this.params.p_TuKho)
          frm.vkho_nhan_id = parseInt(this.params.p_DenKho) || 0
          frm.vctct_id = parseInt(this.gridViewVatTu_FocusedRow.CTCT_ID)
          frm.soluong_tong = parseInt(this.gridViewVatTu_FocusedRow.SOLUONG)
          frm.vattu_id = parseInt(this.gridViewVatTu_FocusedRow.VATTU_ID)
          frm.lohang = this.gridViewVatTu_FocusedRow.LOHANG
          frm.soluong_ton = await this.getTongSoLuongTonTheoCTCT({ctctId:this.gridViewVatTu_FocusedRow.CTCT_ID })
          // this.$refs.popup_frmGanSeri.show()
          await frm.ShowDialog()
          // XacNhan_frmGanSeri
        }
      }
    },
    async gridViewVatTu_ShownEditor(row){
      // Tach 2 func 
      // gridviewVatTu_btnXoaVT
      // gridviewVatTu_TachCap
    },
    async XacNhan_frmNhapCap(){
      var frm = this.$refs.frmNhapCap
      if (frm.xacnhan){
        let kq = await this.TachCuonCap({
          "kieu" : 1,
          "khoId" : this.params.p_TuKho,
          "khoNhanId" : this.params.p_DenKho,
          "ctctId" : frm.vctct_id,
          "nguoiCN" : this.$auth.getUserName(),//ttnd.ma_nd
          "mayCN" : this.$auth.getUserName(),//ttnd.may_cn
          "ipCN" : "10.59.90.123",//ttnd.ip
          "data" : JSON.stringify(frm.data)
        })
        if (kq != "1"){
          this.$toast.error(kq)
        }
        else{
          this.$toast.success("Tách cuộn cáp thành công")
        }

        if(this.Enabled.tsbtnNhapMoi){
          let ctemp_id = this.vchungtu_id
          await this.Load_ds_chungtu()
          await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", ctemp_id)
        }
        // xtraTabControl1.SelectedTabPage = xtraTabPage2;
        // xtraTabControl1.SelectedTabPage.Select();
      }
    },
    async XacNhan_frmGanSeri(){
       var frm = this.$refs.frmGanSeri
       if (frm.xacnhan && frm.functionName == 'gridViewVatTu_ShownEditor'){
         if (this.Enabled.tsbtnNhapMoi){
            let ctemp_id = this.vchungtu_id
            await this.Load_ds_chungtu()
            await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", ctemp_id)
         }
        //  xtraTabControl1.SelectedTabPage = xtraTabPage2;
        //   xtraTabControl1.SelectedTabPage.Select();
       }
       if (frm.xacnhan && frm.functionName == 'btnFilter_Click'){
         await this.Load_ds_thietbi()
       }
    },
    async  gridViewThietBi_ShownEditor(row){
      // Xoa ThietBi
      if (row){
        this.gridViewThietBi_FocusedRow = row
        if (!this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU)){
          this.$toast.error("Bạn không có quyền thực hiện chức năng này!")
          return
        }
        if (! await this.KiemTraHanChotCT()){ return }
        if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id)){ return }
        
        if (this.Enabled.tsbtnNhapMoi){
          
          if (this.soph.startsWith("QT")){
            this.$toast.error("Chứng từ quyết toán không thể sửa!")
            return
          }
          if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
            this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
            return false
          }
          if (this.kiemtra_quyen){
            var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
            if (!ds_dt.length){
              this.$toast.error("Không có dữ liệu ngày nhận không thể xóa")
              return
            }
            if(ds_dt[0].ngayNhanTt != null && ds_dt[0].ngayNhanTt != ''){
              this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị")
              return
            }
            if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
          }
        }

        if (this.ttph_id == 4){
            let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
            if (s.toString() != "1"){
              this.$toast.error(s)
              return
            }
            s = await this.XoaPhieuDaHoanThanh({
                  "chungTuId": this.vchungtu_id,
                  "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                  "mayCn": this.$auth.getUserName(),//ttnd.may_cn
                  "ipCn": "10.59.90.123",//ttnd.ip
              })
            if (s.toString() != "1"){
              this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s)
              return
            }
        }

        await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa thiết bị này!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                // let row = this.gridViewThietBi_FocusedRow
                let id = parseInt(this.gridViewThietBi_FocusedRow.THIETBI_ID)
                let ct_id = parseInt(this.gridViewThietBi_FocusedRow.CTCT_ID)
                let k = await this.XoaChungTuBaoHanh({
                  "kieu": 4,
                  "chungTuId": this.vchungtu_id,
                  "chungTuCtId": ct_id,
                  "thietBiId": id
                })
                if (k != "1"){
                  this.$toast.error(k)
                  return
                }
                // this.$refs.gridViewThietBi.deleteRecord(row)
                // this.gridThietBi.splice(this.gridViewThietBi_FocusedRow.index,1)
                if (id > 0){
                  await this.XoaCTThietBiTheoTBi({ctctId: ct_id,thietBiId: id})
                                    
                  await this.Load_ds_thietbi()
                  
                }
                // view.FocusedColumn = view.Columns[0]
                let ctemp_id = this.vchungtu_id
                await this.Load_ds_chungtu()
                await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", ctemp_id)
              }
          })
          .catch(err => {
              // An error occurred
          })

      }
    },
    async tsbtnCopyCT_Click(){
      try {
        this.loading(true)
        if (this.vma_md == this.MUCDICH_VT.MUASAM){
          this.$toast.error("Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép cóp")
          return
        }
        if (this.vma_md == this.MUCDICH_VT.CHUYEN_CP){
          this.$toast.error("Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép cóp")
          return
        }
        let cp_chungtu_id = 0
        cp_chungtu_id = this.vchungtu_id
        this.Clear()
        this.SetButton(1)
        this.dtCopyCT = await this.CopyChungTuV2({chungTuId: cp_chungtu_id,kieu: 1})
        this.dtCopyCTCT = await this.CopyChungTuV2({chungTuId: cp_chungtu_id,kieu: 2})
        if(this.dtCopyCT.length){
          await this.load_copy_ct(this.dtCopyCT)
        }
        if(this.dtCopyCTCT.length){
          this.dtVatTu = this.dtCopyCTCT
          this.gridVatTu = this.dtVatTu
        }
        this.copyct = true

      }catch(e){
        this.$toast.error("Đã có lỗi xảy ra, vui lòng thử lại!")
      }finally{
        this.loading(false)
      }
    },
    async load_copy_ct(dt){
      for(var r of dt){
        if(r["LOAI"].toString() == "2"){
          this.params.p_LoaiChungTu = 99
        }
        else{
          this.params.p_LoaiChungTu = parseInt(r["LOAICT_ID"].toString())
        }

        if (r["LOAICT_ID"].toString() == "99"){
          this.kieunhap = 3
        }
        else{
          this.kieunhap = 2
        }
        this.params.p_MucDich = parseInt(r["MUCDICH_ID"].toString())
        if(r["LOAICT_ID"].toString() == "2"){
          this.params.p_TuKho = parseInt(r["KHO_GIAO_ID"].toString())
          this.params.p_DenKho = parseInt(r["KHO_NHAN_ID"].toString())
        }
        else{
          // điều kiện như có như không - Hiền hồ
          this.params.p_TuKho = parseInt(r["KHO_GIAO_ID"].toString())
          this.params.p_DenKho = parseInt(r["KHO_NHAN_ID"].toString())
        }
        this.params.p_txtNoiDung = r["ND_GIAO"].toString()
      }
    },
    async cboLoaiChungTu_EditValueChanged(){
      if(this.params.p_LoaiChungTu == null || this.params.p_LoaiChungTu == ''){ return }
      if(this.params.p_LoaiChungTu.toString() == "99"){
        this._vloai = 2
        this.Text.label7 = "NHẬP VẬT TƯ MUA LẺ"
        this.kieugoi_id = 2
        this.cboTuKho = await this.getDSKhoVatTuLe({nhanVienId: this.$auth.getNhanVienID(),donViId: this.$auth.getDonViID()})
        
  
        if (this.$auth.getMaTinh() == 'HNI'){
          this.cbo_tinhtrang = [
            {TINHTRANG_ID: 0, TINHTRANG: ''},
            {TINHTRANG_ID: 1, TINHTRANG: 'Mới'},
            {TINHTRANG_ID: 2, TINHTRANG: 'Cũ - Hỏng'},
            {TINHTRANG_ID: 3, TINHTRANG: 'Cũ - Sử dụng được'},
          ]
          this.repoTinhTrang = this.cbo_tinhtrang
          this.Enabled.xtraTabPage3 = true
        }
        
      }
      else{
        this._vloai = 1
        this.Text.label7 = "NHẬP, XUẤT, ĐIỀU CHUYỂN VẬT TƯ"
        this.kieugoi_id = 1

        this.cboTuKho = await this.getDanhMucKho({nhanVienId: this.$auth.getNhanVienID()})
        if (this.$auth.getMaTinh() == 'HNI'){
          this.Enabled.xtraTabPage3 = false
        }
      }
      await this.Loc_Kho()
      this.Enabled.tsbtnPhieuNhap = false
      this.Enabled.tsbtnPhieuXuat = false
      this.Enabled.tsbtnPhieuChuyen = false
      this.Enabled.tstPhieuTUVT = false
      
      switch (parseInt(this.params.p_LoaiChungTu)){
        case 1: 
          this.Enabled.tsbtnPhieuNhap = true
          break;
        case 2:
          this.Enabled.tsbtnPhieuXuat = true
          break;
        case 3:
          this.Enabled.tsbtnPhieuChuyen = true
          this.Enabled.tstPhieuTUVT = true
          break;
        case 99:
          this.Enabled.tsbtnPhieuNhap = true
          break;
        default:
          break;
      }
      
    },
    async tsbtnPhieuChuyen_Click(){
      if(this.vchungtu_id == 0 || this.vchungtu_id == null){
        this.$toast.error("Không có dữ liệu in phiếu");
        return
      }
     
      if(this.$auth.getPhanVungID().toString() == "54" || this.$auth.getMaTinh().toString() == "TGG"){
        this.tenNguoiNhan = ""
        this.maBC = "PCK"
        this.$bvModal.show('popupTenNguoiNhan')
      }
      else{
        this.loading(true)
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            chungTuId: this.vchungtu_id,
            nhanVien: "",
            donVi: "", 
            maBc : "PCK"   

          }
        }).then((response) => {
          previewPrint(response.data);
        });
        this.loading(false)
      }
    },
    async gridViewVatTu_CellValueChanged(){
      // làm sau
    },
    async gridViewVatTu_ValidateRow(){
      // làm sau
    },
    async gridViewVatTu_ValidatingEditor(){
      // DHSX chưa có action
    },
    async gridViewVatTu_InvalidRowException(){
      // e.ExceptionMode = ExceptionMode.Ignore;
    },
    async btnXuatTonTbi_Click(){
      if(!this.dtVatTu.length){
        this.$toast.error("Chứng từ được tạo chưa có vật tư!")
        return 
      }
      let dt = await this.getDSTonKhoTheoCT({chungTuId: this.vchungtu_id})
      await this.SaveXlsAndOpenFile("Thietbi-" + this.vchungtu_id + ".xlsx", "sheet1", dt)
    },
    async gridViewThietBi_CellValueChanged(){
      // làm sau
    },
    async btnTimKiem_Click(){
      try{
        this.loading(true)
        await this.LamMoi()
      }catch(err){
        this.$toast.error("Đã có lỗi xãy ra, vui lòng thử lại!")
      }finally{
        this.loading(false)
      }
      
    },
    async LamMoi(){
      this.loading(true)
      await this.Load_ds_chungtu()
      if (!this.gridChungTu.length){
        this.loading(false)
        return
      }
      await this.Load_ds_vattu()
      await this.Load_ds_thietbi()
      await this.LAY_DS_VTCT_SERIAL()
      this.loading(false)

    },
    async btnFilter_Click(){
      
      if (!this.grcVT.length){
        this.$toast.error("Không có vật tư để chọn")
        this.params.p_VatTu = ""
        return
      }
      else{
        if (this.dc_ct_noibo){
          this.$toast.error("Điều chuyển nội bộ không thể chọn serial ở đây")
          return
        }
        
        if (this.kiemtra_quyen){
          var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
          if (ds_dt.length){
            this.$toast.error("Không có dữ liệu ngày nhận không thể  lấy serial")
            return
          }
          else{
            if(ds_dt[0].ngayNhanTt.toString()){
              this.$toast.error("Đã có dữ liệu ngày nhận kho bạn không thể lấy serial")
              return
            }
          }
          if (! await this.KIEMTRA_QUYEN_XN(5, t_kho_id)){ return }
        }

        let vt = ""
        let lh = ""
        let dem = 0

        // thay thế với code mới
        // for (let i = 0; i < this.grcVT.DataRowCount; i++){
        //   let dr = grvVT[i]
        //   if (dr["ISCHECK"].toString() == "1"){
        //     dem += 1;
        //     vt += dr["VATTU_ID"] + ",";
        //     lh += "'" + dr["LOHANG"].toString().replace("'", "''") + "'" + ",";
        //   }
        // }
        dem = this.$refs.grvVT.getSelectedRecords().length
        vt = this.$refs.grvVT.getSelectedRecords().map(a=>a.VATTU_ID).join(",")
        lh = this.$refs.grvVT.getSelectedRecords().map(a=>a.LOHANG).join("','")
        if (dem > 0){
          // if (vt.length > 1){
          //   vt = vt.trimEnd(", ");//.ToCharArray()
          //   lh = lh.trimEnd(", ");//.ToArray()
          // }
          this.svattu_id = vt;
          this.slohang = lh;

          this.showDSVatTu = false

          var f = this.$refs.frmGanSeri
          await f.frmGanSeri()
          this.loading(true)
          f.functionName = 'btnFilter_Click'
          f.vlohang = this.slohang;
          f.vvattu_id = this.svattu_id;
          f.vkho_id = this.t_kho_id;
          f.vchungtu_id = this.vchungtu_id;
          // await f.frmGanSeri_Load()
          this.$refs.popup_frmGanSeri.show()
          this.loading(false)
          // XacNhan_frmGanSeri
        }
        else{
          this.$toast.error("Không có vật tư nào được chọn")
        }
      }
      
    },
    // async XacNhan_frmGanSeri_V2(){
    //   await this.Load_ds_thietbi()
    //   if (this.Visible.xTabTongHop && this.chonvattu_lohang){
    //     await this.Load_ds_vattu()
    //     await this.NAP_DS_VT_GROUP()
    //   }
    // },
    async ckbCTCT_CheckedChanged(){
      // grcVT.CloseEditor()
    },
    async toolStripButton1_Click(){
      // DHSX chua co action
    },
    async vbtnRefresh_MouseClick(){
      this.loading(true)
      await this.Load_ds_chungtu()
      await this.Load_ds_vattu()
      await this.Load_ds_thietbi()
      this.loading(false)
    },
    async gridVatTu_DataSourceChanged(){
      if (this.kieugoi_id != 2){
        this.ReadOnly.cboLoaiChungTu = this.gridViewVatTu.length != 0
        this.ReadOnly.cboTuKho = this.gridViewVatTu.length != 0
      }
      else{
        this.ReadOnly.cboTuKho = false
        this.ReadOnly.cboLoaiChungTu = false
      }
      
    },
    async tsbtnDongBo_Click(){
      this.$toast.error("Tạm thời pending do gọi đến service ngoài!")
      return
      if (this.vchungtu_id > 0){
        var d = await this.getChungTuTapDoan({chungTuId: this.vchungtu_id})
        async function DongBo(){
          
          let db_kt = await this.KiemTraDongBo({
            chungTuId: this.vchungtu_id,
            maNd: this.$auth.getUserName(),//ttnd.ma_nd
            mayCn: this.$auth.getUserName(),//ttnd.may_cn
            ip: '10.59.90.123'//ttnd.ip
          })
          if (db_kt == "ok"){
            this.$toast.success("Cập nhật chứng từ và đồng bộ kế toán tập đoàn thành công!")
          }
          else{
            if(!this.Enabled.tsbtnNhapMoi){
              await this.$bvModal.msgBoxConfirm("Việc đồng bộ dữ liệu với kế toán tập đoàn có lỗi: " + db_kt + " Bạn có muốn lưu lại chứng từ này không !", {
              "okVariant": 'success',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy',
                "centered": true,
                })
                .then(async(value) => {
                    if (value){
                      if (this.ttph_id == 4){
                        let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
                        if (s != "1"){
                          this.$toast.error(s)
                          return
                        }
                        s = await this.XoaPhieuDaHoanThanh({
                          "chungTuId": this.vchungtu_id,
                          "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                          "mayCn": this.$auth.getUserName(),//ttnd.may_cn
                          "ipCn": "10.59.90.123"//ttnd.ip
                        })
                        if (s != "1"){
                          this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s)
                          return
                        }
                      }
                      await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
                      this.vchungtu_id = 0
                      await this.XoaDuLieuChungTu({chungTuId: this.vchungtu_id})
                      this.$toast.success("Xóa thành công!")
                    }
                    let vchungtu_tmp_id = this.vchungtu_id
                    await this.Load_ds_chungtu()
                    await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)
                    
                })
                .catch(err => {
                    // An error occurred
                })
            }
          }
        }
        if (d.toString() != "-1"){
          await this.$bvModal.msgBoxConfirm("Chứng từ này đã đồng bộ với tđ rồi. Bạn có chắc chắn muốn đồng bộ lại dữ liệu với tập đoàn không!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                await DongBo().call(this)
              }
          })
          .catch(err => {
              // An error occurred
          })
        }
        else{
          await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn đồng bộ chứng từ này dữ liệu với tập đoàn không!", {
           "okVariant": 'success',
            "okTitle": 'Đồng ý',
            "cancelTitle": 'Hủy',
            "centered": true,
            })
            .then(async(value) => {
                if (value){
                  
                  await DongBo.call(this)
                }
            })
            .catch(err => {
                // An error occurred
            })
        }
        
      }
      else{
        this.$toast.error("Bạn cần chọn 1 chứng từ để đồng bộ")
      }
    },
    async gridviewChungTu_CustomDrawCell(){
      // chịu ,làm sau
    },
    async tsbtnSinhSr_Click(){
      
      if (!Object.keys(this.gridViewVatTu_FocusedRow).length){
        return
      }
      let vctct_id =  this.gridViewVatTu_FocusedRow.CTCT_ID //491585
      var f = this.$refs.frmTaoSerial
      f.vctct_id = vctct_id
      await f.frmTaoSerial_Load()
      await f.ShowDialog()
    },
    async toolStripMenuItem3_Click(){
      if(!this.gridThietBi.length){
        this.$toast.error("Chứng từ này ko có thiết bị")
        return
      }
      let ds = this.gridThietBi
      var q = ds.filter(a=>a.SERIAL != null && a.SERIAL.toString())
      if(q.length ){
        var f = this.$refs.frmTaoSerial
        f.kieu = 1;
        f.dsCT_ChungTu = q
        f.ShowDialog()
      }
    },
    async btnTB_ButtonClick(data){
      var frm =  this.$refs.frmChungTu_TBao
      console.log(this.gridviewChungTu_FocusedRow.SPBH , " = SPBH")
      if(parseInt(this.gridviewChungTu_FocusedRow.SPBH) > 0){
        frm.kieu = 1
      }
      console.log(data.CHUNGTU_ID , " = CHUNGTU_ID")
      frm.chungtu_id = data.CHUNGTU_ID //233254
      await frm.frmChungTu_TBao_Load()
      frm.ShowDialog()
    },
    async chkAll_Phieu_CheckedChanged(){
      await this.LamMoi()
    },
    async gridviewChungTu_CustomRowCellEdit(args){
      // if (e.Column.Name == "DSTB" && e.RowHandle >= 0)
      // {
      //     if (Convert.ToInt64(gridviewChungTu.GetRowCellValue(e.RowHandle, "SPVT").toString()) > 0 ||
      //         Convert.ToInt64(gridviewChungTu.GetRowCellValue(e.RowHandle, "SPBH").toString()) > 0)
      //         e.RepositoryItem = btnTB;
      //     else
      //         e.RepositoryItem = emptyEditor;
      // }
    },
    async tstPhieuTUVT_Click(){
      if(this.vchungtu_id == 0 || this.vchungtu_id == null){
        this.$toast.error("Không có dữ liệu in phiếu");
        return
      }
     
      if(this.$auth.getPhanVungID().toString() == "54" || this.$auth.getMaTinh().toString() == "TGG"){
        this.tenNguoiNhan = ""
        this.maBC = "PTU"
        this.$bvModal.show('popupTenNguoiNhan')
      }
      else{
        this.loading(true)
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            chungTuId: this.vchungtu_id,
            nhanVien: "",
            donVi: "",
            maBc: "PTU"
          }
        }).then((response) => {
          previewPrint(response.data);
        });
        this.loading(false)
      }
    },
  
    async btnTaoTbi_Click(){
      if (this.vchungtu_id == 0){
        this.$toast.error("Bạn chưa nhập chứng từ! Hãy nhập thông tin chứng từ và ghi lại, trước khi nhập thiết bị!")
        return 
      }
      if(this.Enabled.tsbtnNhapMoi){        
        
        if (this.soph.startsWith("QT")){
          this.$toast.error("Chứng từ quyết toán không thể sửa!")
          return
        }
        if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
          this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
          return false
        }
        let s = await this.KiemTraCapNhatChungTu({chungTuId: this.vchungtu_id})
        
        if (s.toString() != "1"){
          this.$toast.error(s.toString())
          return false
        }
        if (this.ttph_id == 4){
          this.$toast.error("Chứng từ đã hoàn thành không thể tạo thiết bị")
          return
        }
      }

      if (this.dtVatTu.length == 0){
        this.$toast.error("Bạn chưa nhập vật tư chứng từ! Hãy nhập vật tư chứng từ và ghi lại, trước khi nhập thiết bị!")
        return
      }
      
      let ds_gan = []
      if (this.Enabled.tsbtnNhapMoi){
        if (this.kiemtra_quyen){
          if (! await this.KIEMTRA_QUYEN_XN(4, this.t_kho_id)){ return }
        }
        if(this.gridThietBi.length){
          var d_s = this.gridThietBi
          var q = d_s.filter(l=>l["SERIAL"].toString())
          if (q.length){
            ds_gan = q
          }
        }
        let mucdich_id = parseInt(this.params.p_MucDich)
        let vma_md = ""
        var d = []
        this.params.p_MucDich ? d = await this.getMaMucDichTheoID({mucDichId: mucdich_id}) : ''
        if (d.length){
          vma_md = d[0]
        }
        if(!this.gridVatTu.length){
          this.$toast.error("Danh sách vật tư chưa có. Không thể tạo được thiết bị")
          return
        }
        let dt = []
        if (this.vchungtu_id > 0 && this.dtVatTu.length){
          var a = this.dtVatTu.filter(p=>p["LOAITBI_ID"].toString() == "2")
          for (var item of a){
            var sl = parseInt(item["SOLUONG"])
            for (let i = 0; i < sl; i++){
              let r = {}
              r["LOHANG"] = item["LOHANG"].toString()
              r["MA_VT"] = item["MA_VT"].toString()
              r["TEN_VT"] = item["TEN_VT"].toString()
              r["DVI_TINH"] = item["DVI_TINH"].toString()
              r["SERIAL"] = ""
              r["SOLUONG"] = 1
              r["DONGIA"] = parseInt(item["DONGIA"])
              dt.push(r)
            }
          }
          let k = 0;

          var checkDel = false
          for(let d_ of dt){
            for(let i in ds_gan ){
              if(d_["SERIAL"].toString() == "" &&
                ds_gan[i]["LOHANG"].toString() == d_["LOHANG"].toString() &&
                ds_gan[i]["MA_VT"].toString() == d_["MA_VT"].toString() &&
                ds_gan[i]["DONGIA"].toString() == d_["DONGIA"].toString()){
                  d_["SERIAL"] = ds_gan[i]["SERIAL"].toString();
                  if (this.b_mathung){
                    d_["MATHUNG"] = ds_gan[i]["MATHUNG"].toString();
                  }  
                  console.log(ds_gan[i] , " = d_2")
                  console.log(ds_gan , " = ds_gan")
                  ds_gan.splice(i,1)
                  console.log(ds_gan , " = ds_gan remove")
                  checkDel = true
                  break;
                }
            }
            if(checkDel){
              break
            }
          }

        }
        var frm = this.$refs.GanSeri_Tbi
        frm.dt = dt
        frm.dt_luu = dt
        frm.open()

      }
    },
    async XacNhan_frmQLTS(){
      var qlts = this.$refs.frmQLTS
      if(qlts.xacnhan){
        await this.Load_ds_vattu()
        await this.Load_ds_thietbi()
      }
    },
    async closeGanSeri_Tbi(){
      var frm = this.$refs.GanSeri_Tbi
      if (frm.xacnhan){
        await this.CapNhatExcelTbi_v2(frm.kq)
      }
    },
    async cboTuKho_ButtonClick(){
      if (this.params.p_MucDich ==  null || this.params.p_MucDich == 0){
        this.$toast.error("Bạn phải chọn mục đích")
        return
      }
      var d = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
      if(d.length){
        if(d[0].toString() == "1.NBH" && this.dsQuyen.includes(99)){
          await this.LAY_CHUNGTU()
        }
        else{
          this.$toast.error("Bạn phải chọn mục đích là 1.NBH hoặc bạn không có quyền thiết bị bảo hành")
          return
        }
      }
    },
    async XacNhan_frmNhapDieuChinh(){
      var f = this.$refs.frmNhapDieuChinh
      if (f.xacnhan){
        var mucdich = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
        if (mucdich[0].toString() == "N.DCDG" || mucdich[0].toString() == "N.TTCL"
            || mucdich[0].toString() == "SC_BH_DONGIA" 
            || mucdich[0].toString() == "NV_DAUTU_BQLDA" 
            || mucdich[0].toString() == "NV_KHAC_BQLDA"){
          this.params.p_TuKho = f.kho_nhan
          this.params.p_DenKho = f.kho_giao
          this.dt_chia_sl = f.dt
          this.vchungtu_id_temp = f.vchungtu_id
          this.sophieu_temp = f.sophieu
          this.dt_temp = []
          for (var r of this.dt_chia_sl){
            let i = {}
            i["CTCT_ID"] = r["CTCT_ID"].toString();
            i["LOHANG"] = "";
            i["LOHANG_CHA"] = r["LOHANG_CHA"].toString();
            i["MA_VT"] = r["MA_VT"].toString();
            i["TEN_VT"] = r["TEN_VT"].toString();
            i["SOLUONG"] = parseInt(r["SOLUONG"]);
            i["DONGIA"] = parseInt(r["DONGIA"]);
            i["TIEN"] = parseInt(r["TIEN"]);
            i["NGUOI_CN"] = this.$auth.getUserName()//ttnd.ma_nd;
            i["IP_CN"] = '10.59.90.123'//ttnd.ip;
            i["MAY_CN"] = this.$auth.getUserName()//ttnd.may_cn;
            i["LOAI_TBI"] = r["LOAI_TBI"].toString();
            i["DVI_TINH"] = r["DVI_TINH"].toString();
            i["MAKHOTD"] = r["MAKHOTD"].toString();
            i["LOAIKHO"] = r["LOAIKHO"].toString();
            i["LOAITBI_ID"] = parseInt(r["LOAITBI_ID"]);
            if (r["TINHTRANG"].toString())
                i["TINHTRANG"] = parseInt(r["TINHTRANG"]);
            i["CHATLUONG_ID"] = r["CHATLUONG_ID"].toString();
            i["HETHONG_ID"] = r["HETHONG_ID"].toString();
            i["DONGTBI_ID"] = r["DONGTBI_ID"].toString();
            i["VATTU_ID"] = parseInt(r["VATTU_ID"]);
            this.dt_temp.push(i)
          }
          this.gridVatTu = this.dt_temp
          if (mucdich[0].toString() == "NV_DAUTU_BQLDA"
          || mucdich[0].toString() == "NV_KHAC_BQLDA"){
            var congtrinh_value = await this.getIDCongTrinh({chungTuId: this.vchungtu_id_temp})
            if (congtrinh_value.length > 0){
              if (congtrinh_value[0].toString()){
                this.params.p_CongTrinh = congtrinh_value[0].toString();
              }
            }
          }
        }
        else if (mucdich[0].toString() == "N-X.DCMSL"){
          let id_temp = this.vchungtu_id
          await this.Load_ds_chungtu()
          await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", id_temp)
        }
      }
    },
    async LAY_CHUNGTU(){
      try {
        var f = this.$refs.frmVatTuTrongKho
        await f.Clear()
        f.vkho_id = parseInt(this.params.p_TuKho)
        f.kieu = 2
        f.vngay_ct = this.params.p_dtpNgayDK
        f.dtLuuVT = this.dtLuuVT
        this.ten_khotu = this.TenTuKho(this.params.p_TuKho)
        f.vngay_ct = this.params.p_dtpNgayDK
        if (!this.Enabled.tsbtnNhapMoi){
          this.vchungtu_id = 0
        }
        else{
          f.vchungtu_id = this.vchungtu_id
        }
        f.functionName = 'LAY_CHUNGTU'
        f.ShowDialog()
        // XacNhan_frmVatTuTrongKho
      } catch (error) {
        this.$toast.error("Lỗi lấy chứng từ và vật tư")
      }
    },
    async tsbtnTroGiup_Click(){

    },
    async ckb_nd_CheckedChanged(){
      await this.LamMoi()
    },
    async tsbtnChiTietSR_Click(){
      if(this.vchungtu_id == 0 || this.vchungtu_id == null){
        this.$toast.error("Không có dữ liệu chi tiết!");
        return
      }
      this.loading(true)
      const baseUrl = process.env.API;
      await this.axios({
        url:
          `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
        method: "POST",
        responseType: "blob",
        data: {
          chungTuId: this.vchungtu_id,
          nhanVien: "",
          donVi: "",
          maBc: "CT_SRVT"   
        }
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false)
    },
    async tsbtnSMS_Click(){
      try {
    
        if (this.gridChungTu.length == 0){
          this.$toast.error("Không có danh sách chứng từ để send sms")
          return
        }        
        
        if (this.gridviewChungTu_FocusedRow == {}){
          this.$toast.error("Bạn chưa chọn chứng từ cần send sms")
          return
        }
        await this.$bvModal.msgBoxConfirm("Bạn có muốn send sms cho người quản lý kho nhận của chứng từ " + this.params.p_txtSoPhieu + " không", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                var ct_id = parseInt(this.gridviewChungTu_FocusedRow.CHUNGTU_ID)
                var kq = await this.GuiSMSChungTu({
                  tuDong: 0,
                  chungTuId: ct_id                 
                })
                if (kq.toString().includes("OK")){
                  this.$toast.success("Send sms thành công")
                }
                else{
                  this.$toast.error("Send sms thất bại " + kq)
                }
              }
          })
          .catch(err => {
              // An error occurred
          })
      } catch (error) {
        this.$toast.error("Có lỗi khi send sms ")
      }finally{
      
      }
    },
    async tsbtnInPhieu_Click(){

    },
    async KIEMTRA_QUYEN_XN(kieu,kho_id){
      // try {
        //Nếu là 1 là kho giao
        var dt = await this.getThongTinQuyenTrenKho({nhanVienId: this.$auth.getNhanVienID(),khoId: kho_id})
        if (!dt.length){
          this.$toast.error("Không lấy được thông tin quyền xác nhận trên kho " + this.TenTuKho(this.params.p_TuKho))
          return false
        }
        if (kieu == 1){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận ngày gửi trên kho " + this.TenTuKho(this.params.p_TuKho))
            return false
          }
        }
        if (kieu == 2){
          if(dt[0].quyenKn.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận ngày nhận trên kho " + this.TenDenKho(this.params.p_DenKho))
            return false
          }
        }

        if (kieu == 3){
          if(dt[0].quyenKn.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận trên kho " + this.TenDenKho(this.params.p_DenKho)+ ".Bạn không thể hoàn công.")
            return false
          }
        }

        if (kieu == 4){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền tạo thiết bị trên kho " + this.TenTuKho(this.params.p_TuKho) + ".Bạn không thể hoàn công.")
            return false
          }
        }
        if (kieu == 5){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận trên kho " + this.TenTuKho(this.params.p_TuKho) + ".Bạn không thể xóa/sửa chứng từ.")
            return false
          }
        }
        return true
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi kiểm tra quyền xác nhận ")
      // }
    },
    async dtpNgayGuiTT_ButtonClick(){
      try {
        if(!this.Enabled.tsbtnNhapMoi){
          this.$toast.error("Chỉ được phép cập nhật ngày gửi khi đã có chứng từ")
          return
        }
        if(! await this.KiemTraHanChotCT()){
          return
        }
        
        if(!this.gridViewVatTu.length){
          this.$toast.error("Trong chứng từ chưa có kèm vật tư nên không thể hoàn thiện chứng từ.")
          return
        }
        if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){ return }
        if (! await this.KIEMTRA_QUYEN_XN(1, this.t_kho_id)){ return }
        if (this.ttph_id.toString() == "4"){
          this.$toast.error("Chứng từ đã hoàn thiện không thể cập nhật ngày gửi")
          return
        }
        var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
        if (!ds_dt.length){
          this.$toast.error("Không có dữ liệu ngày nhận không thể cập nhật ngày gửi của kho")
          return
        }
        else{
          if(ds_dt[0].ngayNhanTt.toString()){
            this.$toast.error("Đã có dữ liệu ngày nhận bạn không thể cập nhật ngày gửi đi")
            return
          }
        }
        var s = await this.KiemTraHoanThienCT({chungTuId: this.vchungtu_id})
        if (s == "1"){
          var z = await this.CapNhatNgayXacNhanCT({
            "ngayXN" : this.f_DateToString(new Date(),'DD/MM/YYYY'),
            "ngayGuiTT" : this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY'),
            "chungTuId" : this.vchungtu_id
          })
          if (parseInt(z)){
            this.$toast.success("Cập nhật ngày gửi thành công")
            // gridviewChungTu_FocusedRowChanged(gridviewChungTu.FocusedRowHandle);
          }
          else{
            this.$toast.error("Cập nhật ngày gửi thất bại " + z)
          }
        }
        else{
          this.$toast.error(s)
        }
      } catch (error) {
        this.$toast.error("Cập nhật ngày gửi thất bại ")
      }
    },
    async dtpNgayNhanTT_ButtonClick(){
      if(!this.Enabled.tsbtnNhapMoi){
        this.$toast.error("Chỉ được phép cập nhật ngày nhận khi đã có chứng từ")
        return
      }
      if(! await this.KiemTraHanChotCT()){
        return
      }
      
      if(!this.gridViewVatTu.length){
        this.$toast.error("Trong chứng từ chưa có kèm vật tư nên không thể hoàn thiện chứng từ.")
        return
      }
      if (! await this.KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){return }
      if (this.ttph_id.toString() == "4"){
          this.$toast.error("Chứng từ đã hoàn thiện không thể cập nhật ngày nhận")
          return
      }
      if (! await this.KIEMTRA_QUYEN_XN(2, this.kho_nhan_goc)){
          return
      }
      var ds_dt = await this.getThongTinNgayGui({chungTuId: this.vchungtu_id})
      if(ds_dt.length){
        this.$toast.error("Không có dữ liệu ngày gửi không thể cập nhật ngày nhận của kho")
        return
      }
      else{
        if(ds_dt[0].toString()){
          this.$toast.error("Không có dữ liệu ngày gửi không thể cập nhật ngày nhận của kho")
          return
        }
        else{
          if(this.params.p_dtpNgayNhanTT.toString()){
            if(ds_dt[0] > this.f_DateToString(this.params.p_dtpNgayNhanTT,'YYYY-MM-DD hh:mm:ss')){
              this.$toast.error("Ngày nhận phải lớn hơn hoặc bằng ngày gửi thực tế")
              return
            }
          }
        }
      }

      let s = await this.KiemTraHoanThienCT({chungTuId: this.vchungtu_id})
      if (s.toString() == "1"){
        let z = await this.CapNhatNVThucHienCT({
          "nhanVienTHId" : this.nhanvien_id,
          "nguoiTH" : this.$auth.getUserName(),
          "ngayNhanTT" : this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY'),
          "chungTuId" : this.vchungtu_id
        })
        if (parseInt(z)){
          this.$toast.success("Cập nhật ngày nhận thành công")
          // this.gridviewChungTu_FocusedRowChanged(gridviewChungTu.FocusedRowHandle);
        }
        else{
          this.$toast.error("Cập nhật ngày nhận thất bại " + z)
        }
      }
      else{
        this.$toast.error(s)
      }
    },
    async tsbtnTraLai_Click(){
      // try {
        if (this.vchungtu_id == 0){
            this.$toast.error("Bạn phải chọn chứng từ để xóa")
            return
        }
        if (! await this.KiemTraHanChotCT()){ return }
        if (! await KIEMTRA_THAYDOI_V2(this.vchungtu_id, "")){
            return;
        }
        if (this.kiemtra_quyen){
          if (! await this.KIEMTRA_QUYEN_XN(2, this.kho_nhan_goc)){ return }
          if (this.ttph_id != 4){
            this.$toast.error("Chứng từ chưa được hoàn thành không thể trả phiếu.")
            return
          }

          if (! await this.KiemTraDuLieu(false, true)){ return }
          
          await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn trả phiếu không!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                try {
                  await this.CapNhatXoaNgayNhanCT({chungTuId: this.vchungtu_id})  
                } catch (error) {
                  this.$toast.error("Có lỗi khi cập ngật ngày nhận")
                  return
                }
                await this.TaoDuLieu(false, true)
                let k = await this.XoaChungTuBaoHanh({
                  "kieu": 2,
                  "chungTuId": this.vchungtu_id,
                  "chungTuCtId": 0,
                  "thietBiId": 0
                })
                if (k != "1"){
                  this.$toast.error(k)
                  return
                }

                if (this.ttph_id == 4){
                  let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
                  if (s != "1"){
                    this.$toast.error(s)
                    return
                  }
                  if (this.params.p_ckbSendSMS){
                    try {
                      await this.GuiSMSChungTu({
                        tuDong: 2,
                        chungTuId: this.vchungtu_id,
                      
                      })
                    } catch (error) {
                      this.$toast.error("Có lỗi khi send smsm tự động")
                    }
                  }
                  s = await this.XoaPhieuDaHoanThanh({
                    "chungTuId": this.vchungtu_id,
                    "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                    "mayCn": this.$auth.getUserName(),//ttnd.may_cn
                    "ipCn": "10.59.90.123"//ttnd.ip
                  })
                  if (s.toString() != "1"){
                    this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s)
                    return
                  }
                }
                await this.CapNhatChungTu(this.chungtuData)
                
                await this.CapNhatChiTiet_CT(false)
                let vchungtu_tmp_id = this.vchungtu_id
                if(this.vtdk.length){
                  await this.CAPNHAT_DANGKY(vchungtu_tmp_id)
                }
                await this.Load_ds_chungtu()
                await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", vchungtu_tmp_id)
                this.copyct = false
              }
          })
          .catch(err => {
              // An error occurred
          })
        }
      // } catch (error) {
      //   this.$toast.error("Thực hiện trả phiếu thất bại ")
      // }
    },
    
    async ckbTuKho_CheckedChanged(){
      await this.Loc_Kho()
    },
    async ckbDenKho_CheckedChanged(){
      await this.Loc_Kho()
    },
   
    async btnDelete_VTDK_Click(){
      await this.AN_GROUP(true)
    },
    async AN_GROUP(mo){
      if(mo){
        this.Visible.ThongTinHDDA = false
      }
      else{
        this.Visible.ThongTinHDDA = true      
      
        if(!this.cboHopDong.length){
          this.cboHopDong = await this.getDSHopDongMuaSam({})
          if (this.params.p_ckbDuAn){
            var da = await this.getDSDuAn({chkDuAn: this.params.p_ckbDuAn ? 1 : 0})
            this.cbo_DuAn = da
          }
          else{
            var da = await this.getDSDuAn({chkDuAn: this.params.p_ckbDuAn ? 1 : 0})
            this.cbo_DuAn = da
          }            
        }
        this.params.p_txtGhiChuHD = ""
      }
    },
    async splitterItem1_DoubleClick(){
      if(this.params.p_LoaiChungTu){
        if(this.params.p_LoaiChungTu.toString() == "99"){
          await this.AN_GROUP(false)
        }
      }
    },
    async ckbDuAn_CheckedChanged(){
      if(this.params.p_ckbDuAn){
        var da = await this.getDSDuAn({chkDuAn: this.params.p_ckbDuAn ? 1 : 0})
        this.cbo_DuAn = da
      }
      else{
        var da = await this.getDSDuAnTheoDonVi({chkDuAn: this.params.p_ckbDuAn ? 1 : 0})
        this.cbo_DuAn = da
      }
    },
    async KIEMTRA(kieu){
      // try {
        if(!this.Enabled.tsbtnNhapMoi){
          this.$toast.error("Bạn chỉ có thể gán gỡ khi đang ở trạng thái cập nhật chứng từ")
          return false
        }
        if (! await this.KiemTraNguoiCN()){
          this.$toast.error("Bạn không được cập nhật chứng từ của người khác")
          return false
        }
        if(!this.gridVatTu.length){
          this.$toast.error("Không có thông tin chi tiết vật tư")
          return false
        }
        var ds = this.gridVatTu
        if(!ds.length){
          this.$toast.error("Không có thông tin chi tiết vật tư")
          return false
        }
        if(this.$refs.gridViewVatTu.getSelectedRecords()){
          this.$toast.error("Bạn chưa chọn vật tư cần gán")
          return false
        }
        if (kieu == 1){
          if(!this.params.p_HopDong.toString()){
            this.$toast.error("Bạn chưa chọn thông tin hợp đồng")
            return false
          }
          if(!this.params.p_DuAn.toString()){
            this.$toast.error("Bạn chưa chọn thông tin dự án")
            return false
          }
        }
        return true
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi kiểm tra gán/ gỡ HĐ ")
      // }
    },
    async btnGan_Click(){
      // try {
        if (! await this.KIEMTRA(1)){ return }
        var ds = this.gridVatTu
        var t = this.$refs.gridViewVatTu.getSelectedRecords()
        
        let json_list = JSON.stringify(t)
        await this.GanHDDuAnChiTietCT({
          "hdmsId" : this.params.p_HopDong,
          "duAnId" : this.params.p_DuAn,
          "ghiChu" : this.params.txtGhiChuHD,
          "data" : json_list
        })
        
        this.$toast.success("Cập nhập thông tin thành công")
        await this.Load_ds_vattu()
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi gán Hợp đồng dự án ")
      // }
    },
    async btnGo_Click(){
      // try {
        if (! await this.KIEMTRA(0)){ return }
        var ds = this.gridVatTu
        var t = this.$refs.gridViewVatTu.getSelectedRecords()
        
        let json_list = JSON.stringify(t)
        if (this.vma_md == MUCDICH_VT.KDV_DONGIA){
          var kt = await this.KiemTraChiTietVatTuCoDauVao({
            "data" : json_list
          })
          if (kt.toString() != "0"){
            this.$toast.error("Chi tiết vật tư đã có đầu vào không thể gỡ thông tin hợp đồng dự án")
            return
          }
        }
        await this.GoGanHDDuAnCTCT({ "data" : json_list})
        
        this.$toast.success("Xóa thông tin hợp đồng dự án thành công")
        await this.Load_ds_vattu()
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi gỡ Hợp đồng dự án ")
      // }
    },
    async btnRef_Click(){
      if(this.params.p_ckbDuAn){
        var da = this.getDSDuAnV2({chkDuAn: this.params.p_ckbDuAn ? 1 : 0})
        this.cbo_DuAn = da
      }
      else{
        var da = this.getDSDuAnV2({chkDuAn: this.params.p_ckbDuAn ? 1 : 0})
        this.cbo_DuAn = da
      }
    },
    async gridChungTu_Click(){
      // DHSX đang empty
    },
    async gridviewTinhTrang_CellValueChanged(){
      // await this.CapNhatTinhTrangCTTBi({
      //   "tinhTrang" : 1,
      //   "thietBiId" : 1195932,
      //   "ctctId" : 855235
      // })
    },
    async ckbTTALL_CheckedChanged(){
      if (this.params.p_ckbTTALL){
        if (this.params.p_TinhTrang.toString()){
          // for (int i = 0; i < gridviewTinhTrang.RowCount; i++)
          // {
          //     int tt = (gridviewTinhTrang.GetRowCellValue(i, "TINHTRANG").toString() == "" ||
          //         gridviewTinhTrang.GetRowCellValue(i, "TINHTRANG") == null) ? 0 :
          //         Convert.ToInt32(gridviewTinhTrang.GetRowCellValue(i, "TINHTRANG"));
          //     if (tt == 0)
          //     {
          //         gridviewTinhTrang.SetRowCellValue(i, "TINHTRANG", cboTinhTrang.EditValue);
          //     }
          // }
          this.params.p_ckbTTALL = false
        }
        else{
          this.params.p_ckbTTALL = false
        }
      }
    },
    
    async XacNhan_frmGanSeri_v2(){
      var f = this.$refs.frmGanSeri_v2
      if (f.xacnhan){
        await this.Load_ds_thietbi()
      }
    },
    async gridViewVatTu_CustomRowCellEdit(){
      // làm sau
    },
    async btnChonTamUng_Click(){
      if (this.Enabled.tsbtnNhapMoi && this.dc_ct_noibo){
        var frm = this.$refs.frmChonTBi_CT
        frm.vcongtrinh_id = parseInt(this.params.p_CongTrinh)
        
        frm.vchungtu_id = this.vchungtu_id
        frm.vkho_id = parseInt(this.params.p_TuKho)
        frm.openDialog()
      }
    },
    async XacNhan_frmChonTBi_CT(value){
      var frm = this.$refs.frmChonTBi_CT;
      if (value) {
        await this.Load_ds_thietbi();
      }
    },
    async btnChuyenTT_Click(){
      if (! await this.KiemTraNguoiCN()){
        this.$toast.error("Bạn không phải người lập chứng từ, không thể sử dụng chức năng này!")
        return
      }
      let loai_chungtu = this.gridviewChungTu_FocusedRow["LOAI"]
      if ((this.vma_md == "" || (this.vma_md != this.MUCDICH_VT.KDV_DONGIA && this.vma_md != "N.KDV")) && loai_chungtu == "2"){
        this.$toast.error("Mục đích không phải nhập không đầu vào ! Không thể sử dụng chức năng !")
        return
      }
      if (!this.gridVatTu.length){
        this.$toast.error("Không có vật tư ! Không thể sử dụng chức năng !")
        return
      }
      let vattu_ischeck = this.gridVatTu
      var kiem_tra = this.$refs.gridViewVatTu.getSelectedRecords()
      if (!kiem_tra.length){
        this.$toast.error("Bạn chưa tích chọn dòng vật tư nào !")
        return
      }
      // Match a string that starts with abc, similar to LIKE 'KDV%'
      kiem_tra = this.$refs.gridViewVatTu.getSelectedRecords().filter(a=> a.LOHANG.match(/^KDV.*$/))
      if (!kiem_tra.length){
        this.$toast.error("Bạn chỉ có thể chọn lô hàng KDV !")
        return
      }
      
      //-- ban đầu khi update sẽ cho sửa đơn giá lần đầu, sau có đầu vào thì chặn ko cho update tiếp
      var codauvao_kiemtra = this.$refs.gridViewVatTu.getSelectedRecords().filter(a=> a.LOHANG.match(/^KDV.*$/) && a.TRANGTHAI_SO.toString() == "1")
      if (codauvao_kiemtra.length){
        this.$toast.error("Danh sách chọn chứa vật tư có đầu vào. Hãy kiểm tra lại !")
        return
      }
      // nhập lẻ
      if (loai_chungtu == "2"){
        let note_dg = this.vma_md == this.MUCDICH_VT.KDV_DONGIA ? "(và đơn giá)" : ""
        await this.$bvModal.msgBoxConfirm("Chuyển trạng thái vật tư ở nhập lẻ sẽ chuyển trạng "
              + "thái vật tư " + note_dg + " ở tất cả phiếu xuất sau nó!\r\n Bạn có thật sự muốn chuyển trạng thái của "
              + kiem_tra.length + " vật tư không?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                let data = []
                kiemtra.forEach(a=>data.push({
                  "ctctId" : a.CTCT_ID,
                  "loHang" : a.LOHANG,
                  "donGia" : a.DONGIA,
                  "vatTuId" : a.VATTU_ID
                }))
                await this.CapNhatLoHang({
                    "checkMucDich" : this.vma_md == MUCDICH_VT.KDV_DONGIA ? 1 : 0,
                    "listLoHangIn" : JSON.stringify(data)
                  })
                this.$toast.success("Chuyển trạng thái thành công !")
                await this.Load_ds_vattu()
                await this.Load_ds_thietbi()
              }
              else{
                return
              }
          })
          .catch(err => {
              // An error occurred
          })
      }
      // xuất - chuyển - nhập đơn hàng
      else{
        let data = []
        kiem_tra.forEach(a=>data.push({
          VATTU_ID: a.VATTU_ID,
          LOHANG: a.LOHANG
        }))
        let json_list = JSON.stringify(data)
        var res = await this.getDSChungTuNhapLe({
          "data" : json_list
        })
        this.$toast.error("Chứng từ xuất/chuyển không thể chuyển trạng thái vật tư ! Hãy tìm chứng từ nhập lẻ:\r\n" + res[0].SOPHIEU )
      }
    },
    async grvLocVattu_CellValueChanged(){
      let tt = "";
      for (let dr of this.$refs.grvLocVattu.getSelectedRecords()){
          tt += dr["TEN_VT"] + ",";
          
      }
      tt = tt.trimEnd(",".toCharArray());

      if (tt != ""){
          cboLocVattu.Text = tt;
      }
      else{
          cboLocVattu.Text = tt;
      }
    },
    async ckbHTTT__CheckedChanged(){

    },
    async gridViewVatTu_CustomDrawCell(){
      // làm sau
    },
    
    async XacNhan_frmLoaiKho_MaKho(){
      var f = this.$refs.frmLoaiKho_MaKho
      if (f.xacnhan){
        if (this.Enabled.tsbtnNhapMoi && this.nghiepvu_id_chungtu != ""
          && this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != ""){
            for(var  z of f.dt_dasua){
              if (z.LOAIKHO != "" && z.LOAIKHO != null){
                await this.CapNhatLoaiKhoTapDoan({loaiKhoTdId: z.LOAIKHO, ctctId: parseInt(z.CTCT_ID)})
              }
              if (z.MAKHOTD != "" && z.MAKHOTD != null){
                await this.CapNhatKhoTapDoan({khoTdId: z.MAKHOTD,ctctId: parseInt(z.CTCT_ID)})
              }
            }
            this.$toast.success("Cập nhật loại kho - mã kho thành công !")
            await this.Load_ds_vattu()
          }
        else{
          this.gridVatTu = f.dt_dasua
        }
      }
    },
    async simpleButton3_Click(){
      // try {
        let json_list = "0"
        if (this.ds_loc_vattu.length){
          var length = this.$refs.grvLocVattu.getSelectedRecords()
          if (length.length){
            this.$toast.error("Chưa chọn vật tư nào để tìm kiếm!")
            this.$refs.cboLocVattu.focusIn()
            return
          }
          else{
            this.params.p_ckb_nd = false
            this.params.p_chkAll_Phieu = false
            var json_vattu = []
            length.forEach(a=>json_vattu.push({
              VATTU_ID: a.VATTU_ID
            }))
            json_list = JSON.stringify(json_vattu)
            this.dtChungTu = await this.getDSChungTuTheoVatTu({
              "donViId" : this.$auth.getDonViID(),
              "nhanVienId" : this.$auth.getNhanVienID(),
              "dsVatTu": json_list,
              "tuNgay" : this.f_DateToString(this.params.p_dtpTuNgay,'DD/MM/YYYY'),
              "denNgay" : this.f_DateToString(this.params.p_dtpDenNgay,'DD/MM/YYYY')
            })
            this.gridChungTu = this.dtChungTu
            this.gridChungTuPage.totalElement = this.dtChungTu.length

            var kieu = this.dtChungTu.length > 0 ? 3 : 1
            this.SetButton(kieu);
            
          }
        }
      
    },
    async tsbtnGiaoPhieu_Click(){
      if (this.vchungtu_id > 0){
        var qt = await this.getQuyTrinhHuongGiaoTheoDeNghiId(this.vchungtu_id)   
        if(qt.length == 0){
          this.$toast.error("Quy trình chưa được thiết lập")
        }
        else{
          let huonggiao_tieptheo_id = qt[0].HUONGGIAO_ID
          var frm = this.$refs.frmGiaoPhieu
          frm.frmGiaoPhieuHG(huonggiao_tieptheo_id)
          frm.parentForm = "NhapXuatVT"
          frm.openDialog()         
        }
      }else{
        this.$toast.error("Chưa chọn chứng từ!")
      }
    },
    async closeGiaoPhieu(){
      await this.Load_ds_chungtu()
    },
    
    onFilteringTuKho(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('tenKho', 'contains', e.text, true) : query;
      e.updateData(this.cboTuKho, query);
    },
    onFilteringDenKho(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('tenKho', 'contains', e.text, true) : query;
      e.updateData(this.cboDenKho, query);
    },
    async InsertUpdateChungTu(kieu, chungTuData){
      let listChiTietCt = []      
      chungTuData.forEach(item=>{
        listChiTietCt.push({
          "chungTuId": item.CHUNGTU_ID,
          "loaiCtId": item.LOAICT_ID,
          "soPhieu": item.SOPHIEU,
          "mucDichId": item.MUCDICH_ID,
          "khoGIaoId": item.KHO_GIAO_ID,
          "khoNhanId": item.KHO_NHAN_ID,
          "nhanVienGiaoId": item.NHANVIEN_GIAO_ID,
          "donViNhanId": item.DONVI_GIAO_ID,
          "donViGiaoId": item.DONVI_NHAN_ID,
          "nguoiCn": item.NGUOI_CN,
          "congTrinhId": item.CONGTRINH_ID || '',
          "noiDungGiao": item.ND_GIAO,
          "ngayCt": item.NGAY_CT,
          "ttPhId": item.TTPH_ID,
          "loai": item.LOAI
        })
      })
      let body = {
        // "nguoiCN": chungTuData.NGUOI_CN,
        "mayCN": chungTuData[0].MAY_CN,
        "ngayCN": chungTuData[0].NGAY_CN,
        "ipCN": chungTuData[0].IP_CN,
        "listChiTietCt": listChiTietCt,
        "kieu": kieu
      }
      // tao chung tu => gan chung tu id = 0
      // API se tra ve chungTuId
      let newChungTuId = await this.ThemMoiChungTu(body)
      if (kieu == 1){
        this.vchungtu_id = newChungTuId
      }
      console.log('AAAAA',this.vchungtu_id)
      
    },
    async InsertUpdateChiTietChungTu(chiTietCTData){
      let listChiTietCt = []
      chiTietCTData.forEach(item=>{
        listChiTietCt.push({
          "ctctId": item.CTCT_ID,
          "vatTuId": item.VATTU_ID,
          "chungTuId": item.CHUNGTU_ID,
          "loHang": item.LOHANG,
          "donGia": item.DONGIA,
          "tien": item.TIEN,
          "vat": item.VAT,
          "soLuong": item.SOLUONG,
          "soThangPB": item.SOTHANG_PB
        })
      })
      let body = {
        "nguoiCN": chiTietCTData[0].NGUOI_CN,
        "mayCN": chiTietCTData[0].MAY_CN,
        "ngayCN": chiTietCTData[0].NGAY_CN,
        "ipCN": chiTietCTData[0].IP_CN,
        "listChiTietCt": listChiTietCt
      }
      console.log('BBBBB',this.vchungtu_id)
      await this.ThemMoiDuLieuChiTietCT(body)
    },
    TenTuKho(value){
      let obj = this.$refs.cboTuKho.getDataByValue(value)
        if (!obj){
          return ''
        }
        return obj.tenKho
    },
    TenDenKho(value){
      let obj = this.$refs.cboDenKho.getDataByValue(value)
        if (!obj){
          return ''
        }
        return obj.tenKho
    },
    async selected_gridVatTuRow(data){
      if(data){
        this.gridViewVatTu_FocusedRow = data.data
      }
    }
    
  },
  created: async function() {
    // this.Tag = this.$route.query.Tag || "1"    
    this.Tag = ["1","2"].includes(this.$route.query.Tag) ? this.$route.query.Tag : "1"
    //nếu query sai tag thì redirect sang tag = 1
    // if(!["1","2"].includes(this.$route.query.Tag)){
    //   window.location.href = `${window.location.origin}#/qlvt/NhapXuatVT?Tag=1`;
    // }  
    
    // this.Tag = this.$route.query.Tag || "1" 
    // this.clearData();
    this.loading(true)
    await this.frmDeNghiVT_Load()
    this.loading(false)
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>

  .e-grid .e-content {
    height: auto !important;
  } 
</style>