<template src='./NhapVatTuMuaLe.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import NhapVatTuMuaLeAPI from  '../api/NhapVatTuMuaLeAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/nhapvattumuale'
import select2 from '@/components/Select2.vue'
import { Freeze, Group, Page, Filter, Sort, Resize, ForeignKey} from "@syncfusion/ej2-vue-grids";
import { Query,Predicate } from '@syncfusion/ej2-data';
import { L10n } from '@syncfusion/ej2-base'
import XLSX from "xlsx";
import frmGanSeri from '../frmGanSeri/frmGanSeri.vue'

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

export default {

  components: { frmGanSeri, XLSX, appSelect2: select2 },
  name: "NhapVatTuMuaLe",
  mounted() {
  },
  provide: {
    grid: [ Freeze, Group, Page,Filter,Sort,Resize, ForeignKey ]
  },
  computed: {
    ...mapState("nhapvattumuale", statePropertyName),
    ...mapGetters("nhapvattumuale", getterName),
    soph: {
      get() {
        return this.gridviewChungTu_FocusedRow ? this.gridviewChungTu_FocusedRow["SOPHIEU"] : ""
      }
    },
    ma_pda_chungtu: {
      get() {
        return this.gridviewChungTu_FocusedRow ? this.gridviewChungTu_FocusedRow["MA_PDA"] : ""
      }
    },
    nghiepvu_id_chungtu: {
      get() {
        return this.gridviewChungTu_FocusedRow ? this.gridviewChungTu_FocusedRow["NGHIEPVU_ID"] : "-1"
      }
    },
  },
  data() {
    return {
        header: {
            title: "NHẬP VẬT TƯ MUA LẺ",
            list: [
            { name: "Quản lý vật tư", link: { name: "Ui.cards" } },
            {
                name: "Nhập/ xuất/ điều chuyển vật tư",
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

        dtChungTu: [],
        dtTB: [],
        dtThietBi: [],
        dtVatTu: [],

        dtLuuVT: [],
        dtCopyCT: [],
        dtCopyCTCT: [],
        dtVatTuXuat: [],

        bbbgiao: [],
        duan_ims_clone: [],
        
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
        file: "",
        nddh: "",
        ttph_id: null,
        ngay_dh: new Date(),
        ds_md:  [],

        dtct: [],
        dtchitiet_ct: [],
        dt_dh_ct: [],
        dt_chia_sl: [],
        dt_temp: [],
        dt_file: [],

        dc_ct_noibo: false,
        dc_ct_ttvt: false,
        dc_ct_ldscdp: false,

        bqlda_nguonvon_dautu: false, // HCM
        bqlda_nguonvon_khac: false, // HCM
        tudong_hoanthanh: false,
        xacnhan_in_chungtu: false, //dùng cho NET
        xoa_ngaygui_theo_user: false,//dùng cho NET
        chon_kieu_qlts: false,//dùng cho NET
        gan_huy_duan: false,//dùng cho NET

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
          p_DuAn: ""
        },
        cboLoaiChungTu: [],
        cboKieuPhieu: [],
        cboMucDich: [],
        cboNghiepVu: [],
        cboMa_PDA: [],
        cboTuKho: [],
        cboDenKho: [],
        cboCongTrinh: [],
        CboVatTu: [],
        cboLoaiKho: [],

        gridChungTu: [],
        gridBienBanIMS: [],
        grcMa_PDA: [],
        gridVTGroup: [],
        gridVatTu: [],
        gridThietBi: [],
        
      tag: "",
      Tag: "1",
      kiemtra_quyen: false,
      boimau: false,
      qr_code: false,
      chonvattu_lohang: false,

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
        colSoVB2: false,
        colNgayVB2: false,
        colMaphieuDK: false,
        lblSoLanXemBC: true,
        colInPhieuCheck: false, //grid chungtu
        gridColumn68: false,//grid chungtu
        gridColumn69: false,//grid chungtu
        gridColumn83: false,//grid chungtu
        gridColumn84: false,//grid chungtu
        gridColumn70: false,//grid vật tư
        colDongTBI: false,//grid vật tư
        colHeThongID: false,//grid vật tư

        colTyLe: false,
        colMaKhoTD: false,
        colLoaiKho: false,

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

        xTabTongHop: false,
        colSL_CTBI: false,
        ThongTinHDDA: false,

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
      ds_Gan:[],
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

      filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true, },
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
      editSettings: { allowEditing: true},
      selectionSettings: {  type: 'Single', checkboxOnly: true },
      XoaVTTemplate: function () {
            return {
                template: Vue.component('XoaVTTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnXoaVT_ButtonClick">
                                  <span class="-ap icon-close"></span>
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
      XoatbITemplate: function () {
            return {
                template: Vue.component('XoatbITemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnXoatbI_Click">
                                  <span class="-ap icon-close"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnXoatbI_Click(){
                        await this.$parent.$parent.$parent.btnXoatbI_Click(this.data)
                      }

                    }
                })
            }
        },
      EditTemplate: function () {
            return {
                template: Vue.component('EditTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnXoatbI_Click">
                                  <span class="-ap icon-close"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnXoatbI_Click(){
                        await this.$parent.$parent.$parent.btnXoatbI_Click(this.data)
                      }

                    }
                })
            }
        },
      gridChungTuPage : {
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

      isTab2: true,
    };
  },
  methods: {
    ...mapActions("nhapvattumuale", actionName),
    ...mapMutations("nhapvattumuale", mutationName),
    
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
       if (moment(value, format, true).isValid()){
         return moment(value).format(format)
       }
       else{
         return ""
       }
       
      
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    gridChungTuPageHandler(e) {
      this.gridChungTuPage.page = e.pageIndex
      this.gridChungTuPage.maxSize = e.pageSize
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
    async Loc_Kho(){
      let loc_tukho = this.cboTuKho.filter(a=>a.trangThai.toString() == '1' && a.loaiKhoId && a.loaiKhoId.toString() != this.LOAI_KHO.KHO_NV.toString())
      if (this.params.p_ckbTuKho){
        if (this.cboTuKho.length && this.cboTuKho[0].hasOwnProperty('quyenKg') ){
          loc_tukho = loc_tukho.filter(b=>b.quyenKg == '1')
        }  
      }

      let loc_denkho = this.cboDenKho.filter(a=>a.trangThai.toString() == '1' && a.loaiKhoId && a.loaiKhoId.toString() != this.LOAI_KHO.KHO_NV.toString())
      if (this.params.p_ckbDenKho){
        if (this.cboDenKho.length && this.cboDenKho[0].hasOwnProperty('quyenKg') ){
          loc_denkho = loc_denkho.filter(b=>b.quyenKg == '1')
        }  
      }

      this.cboTuKho = loc_tukho
      this.loc_denkho = loc_denkho
    },
    setTag(cboName){
      if (this.tag == "1"){//nhap
        return 1
      }
      else if (this.tag == "2"){// nhap le
        return 99
      }
      else if (this.tag == "3"){// xuat
        return 2
      }
      else if (this.tag == "4"){//chuyen
        return 3
      }
      else{ //khac
        this.Enabled[cboName] = true;
        return ""
      }
    },
    async Load_Cbo (){
      this.cboLoaiChungTu = await this.getDanhMucLoaiCT({tag: this.tag})
      this.Enabled.cboLoaiChungTu = false

      this.cboTuKho = await this.getDanhMucKho({nhanVienId: this.$auth.getNhanVienID()})

      this.cboMucDich = await this.getDanhMucMucDich({nhomMdId: 3})
      this.ds_md = this.cboMucDich

      this.cboDenKho = await this.getDanhMucKho({nhanVienId: this.$auth.getNhanVienID()})

      this.cboCongTrinh = await this.getDSCongTrinh({
        "nhanVienId": this.$auth.getNhanVienID(),
        // "tenTat": this.$auth.getUserName(), // ttnd.ma_nd
        // "tagForm": 2,// API cần, ko biêt đào đâu ra
        "kieuId": 1,
        "maNguoiDung": this.$auth.getUserName()//ttnd.ma_nd
      })
      
      await this.Loc_Kho()
      this.params.p_VatTu = ""

      // this.ngay_dh = DateTime.MinValue
      let EditValue = this.setTag('cboLoaiChungTu')
      EditValue.toString() ? this.params.p_LoaiChungTu = EditValue : ""

      this.cboTinhTrang = await this.getDanhMucTinhTrangTB()

      this.cboChatLuongID = await this.getDanhMucChatLuong()

      this.cboNghiepVu = await this.getDanhMucNghiepVu({nhomMdId: 20})

    },
    async frmNhapXuatVT_v2_Load(){
      // this.$root.showLoading(true)
      await this.AN_GROUP(true)
      let diachi = "WinUI.WinUIQuanLyVatTu.frmNhapXuatVT_v2"


      if (this.Tag.toString()){
          this.tag = this.Tag.toString();
          diachi = diachi + "," + this.tag;
      }

      await this.Load_quyen()
      // Lấy TS mặc định
      let ds = await this.getThamSoMacDinh({kieuId: 0})
      if (ds.length){
        if (this.setThamSo("KIEMTRA_QUYENXN",ds)){
          this.kiemtra_quyen = true
        }
        
        if (this.setThamSo("MAU_CHUNGTU_QLVT",ds)){
          this.boimau = true
        }
        if (!this.boimau){
          // colDonGia.AppearanceCell.ForeColor = Color.Blue;
          // colSOLUONG.AppearanceCell.ForeColor = Color.Black;
          // gridViewVatTu.OptionsView.EnableAppearanceEvenRow = true;
          // gridViewVatTu.OptionsView.EnableAppearanceOddRow = true;
        }

        if (this.setThamSo("QR_CODE",ds)){
          this.qr_code = true
        }
        if (this.setThamSo("CHONLOHANG_VATTU",ds)){
          this.chonvattu_lohang = true
        }
        if (this.setThamSo("TUDONG_HT_NGAYNHAN",ds)){
          this.tudong_hoanthanh = true
        }
        if (this.setThamSo("XAC_NHAN_IN_CHUNGTU",ds)){
          this.xacnhan_in_chungtu = true
        }
        //Tham số chặn user nào cập nhật ngày gửi tt thì mới được clear - NET đang dùng
        if (this.setThamSo("XOA_NGAYGUI_THEO_USER",ds)){
          this.xoa_ngaygui_theo_user = true
        }
        if (this.setThamSo("CHON_KIEU_QLTS",ds)){
          this.chon_kieu_qlts = true
        }
        if (this.setThamSo("GAN_HUY_DUAN",ds)){
          this.gan_huy_duan = true
        }
      }
      await this.Load_Cbo()
      await this.Load_ds_chungtu()

      if (this.$auth.getMaTinh() == "NET"){
        // colChatluong_id.VisibleIndex = 15;
        // colDongTBI.VisibleIndex = 16;
        // colHeThongID.VisibleIndex = 17;
      }

      let kq = await this.getLayTenMenu({
          "tenDiaChi": diachi,//"WinUI.WinUIQuanLyVatTu.frmThuocTinh_LVT,PTTB.exe,ShowDialog",
          "thongTin": "frmNhapXuatVT_v2",//this.GetType().Name.toString()
          "pageNo": 0,
          "maxSize": 10
      })
      if (kq.length && kq[0].toString() != "-1"){
        this.Text.label7 =  kq.toString().toUpperCase();
      }
      else{
        if (this.tag == "1"){
          this.Text.label7 = "NHẬP VẬT TƯ"
        }
        else if (this.tag == "2"){
          this.Text.label7 = "NHẬP VẬT TƯ MUA LẺ"
        }
        else if (this.tag == "3"){
          this.Text.label7 = "XUẤT VẬT TƯ"
        }
        else if (this.tag == "4"){
          this.Text.label7 = "CHUYỂN VẬT TƯ"
        }
        this.header.title = this.Text.label7
      }

      if (this.tag == "3"){// xuất
        this.Visible.colSoVB2 = true
        this.Visible.colNgayVB2 = true
        this.Visible.colMaphieuDK = true
      }

      if (this.xacnhan_in_chungtu){
        this.Visible.lblSoLanXemBC = false
      }

      // Ẩn hiện cột
      var show_hide_col = await this.getAnHienCot({kieu: 1})
      if (show_hide_col.length){
        if (!show_hide_col.includes('TIEPNHAN')){
          // layoutControlItem38.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
          //btnTiepNhanPhieu
        }
        this.Visible.colInPhieuCheck = show_hide_col.includes('DAIN_PHIEU')
        this.Visible.gridColumn68 = show_hide_col.includes('DONVI_GIAO')
        this.Visible.gridColumn69 = show_hide_col.includes('DONVI_NHAN')
        this.Visible.gridColumn83 = show_hide_col.includes('NGAY_XD')
        this.Visible.gridColumn84 = show_hide_col.includes('NGUOI_XD')
        this.Visible.gridColumn70 = show_hide_col.includes('PN')
        this.Visible.colDongTBI = show_hide_col.includes('DONGTBI')
        this.Visible.colHeThongID = show_hide_col.includes('HETHONGID')
      }
      // this.$root.showLoading(false)
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
    // không cần xài vì scope
    load_cboRePo(cbo,field,dt,display,value){
    },
    async get_mamd(cbo){
      var d = await this.getTenTatMucDich({mucDichId: cbo})
      if (d.length){
        return d[0].toString()
      }
      return ""
    },
    async Load_ds_chungtu(){
      // try {
        // có điều kiên kieugoi_id == 1 nhưng func vẫn giống nhau nên xóa
        this.dtChungTu = await this.getDSChungTuTheoLoai({
          "tatCa": this.params.chkAll_Phieu ? 1 : 0,
          "donViId": 36522,//this.$auth.getDonViID(),
          "nhanVienId": 3945,//this.$auth.getNhanVienID(),
          "tuNgay": this.f_DateToString(this.params.p_dtpTuNgay,"DD/MM/yyyy"),
          "denNgay": this.f_DateToString(this.params.p_dtpDenNgay,"DD/MM/yyyy"),
          "loai": parseInt(this.tag) || 0,
          "pageNum": 1,
          "pageSize": 100
        })
        this.gridChungTu = this.dtChungTu
        this.convertData_gridChungTu()
        if (this.dtChungTu.length){
          if (this.gridChungTu.length){
            this.SetButton(3)
            // await this.gridviewChungTu_FocusedRowChanged(0)
          }
          else{
            this.SetButton(1)
          }
        }
        else{
          this.SetButton(1)
        }
        
        if (this.params.p_ckb_nd){
          if (this.dtChungTu.length){
            var q = this.dtChungTu.filter(a=>a.NGUOI_CN.toString() == this.$auth.getUserName()) //ttnd.ma_nd
            if (q.length){
              this.gridChungTu = q
              this.covertData_gridChungTu()
              if (this.dtChungTu.length){
                await this.gridviewChungTu_FocusedRowChanged(0)
                this.SetButton(3)
              }
              else{
                this.SetButton(1)
              }
            }
            else{
              this.gridChungTu = []
              this.SetButton(1)
            }
          }
        }
        
      // } catch (error) {
      //   this.$toast.error("Có lỗi xảy ra")
      // }
    },
    async Load_ds_vattu(){
      this.dtVatTu = await this.getDSVatTuChungTuV1({chungTuId: 49665})//this.vchungtu_id
      // if (!dtVatTu.Columns.Contains("ISCHECK"))
      // {
      //   DataColumn ischeck = new DataColumn("ISCHECK", typeof(System.String));
      //   ischeck.DefaultValue = "0";
      //   dtVatTu.Columns.Add(ischeck);
      // }
      //new change
      if (this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('TY_LE') && this.dtVatTu[0].hasOwnProperty('MATHETS')){
        let d = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
        if (d.length){
          if (d[0].toString() == "1.3"){
            this.Visible.colTyLe = true
          }
          else{
            this.Visible.colTyLe = false
          }
        }
      }

      if (this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty('MAKHOTD') && this.dtVatTu[0].hasOwnProperty('LOAIKHO')){
        this.Visible.colMaKhoTD = true
        this.Visible.colMaKhoTD = true
      }
      else{
        this.Visible.colMaKhoTD = false
        this.Visible.colMaKhoTD = false
      }

      // gridViewVatTu.ClearColumnsFilter();
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
      this.gridThietBi = this.dtThietBi
    },
    async  gridviewChungTu_FocusedRowChanged_sender(row){
      this.t_kho_id = 0
      this.t_kho = ""
      // ngay_dh = DateTime.MinValue;
      if (row){
        await this.gridviewChungTu_FocusedRowChanged(row)
        await this.cboKieuPhieu_EditValueChanged()
      }
      else{
        let EditValue = this.setTag('cboLoaiChungTu')
        EditValue.toString() ? this.params.p_LoaiChungTu = EditValue : ""
        this.Visible.tsbtnTraLai = false
        this.SetButton(1)
        this.Clear()
      }
    },
    async gridviewChungTu_FocusedRowChanged(row){
      this.gridviewChungTu_FocusedRow = row
      this.Visible.tsbtnTraLai = false
      this.kho_nhan_goc = 0
      this.vchungtu_id = parseInt(row.CHUNGTU_ID)
      this.dtLuuVT = await this.getDSVatTuChungTuV1({chungTuId: this.vchungtu_id})
      // this.dtVatTuXuat = JSON.parse(JSON.stringify(this.dtLuuVT))
      this.dtVatTuXuat = []

      this.ttph_id = parseInt(row.TTPH_ID)
      this.kieunhap = 0
      this.params.p_txtSoPhieu = row.SOPHIEU.toString()
      this.params.p_txtNoiDung = row.ND_GIAO.toString()
      this.kieu_td = row.KIEU.toString()

      // Ai có quyền 2501 mới hiển thị
      if(this.dsQuyen.includes(2501)){
        this.Visible.tsbtnSuaCT = true
      }
      else if (this.dsQuyen.includes(2507)){ //Thuộc TTVT và có quyền 2507 thì hiển thị sửa ct ở form nhập lẻ
        if (this.Tag.toString() == "2" && this.$auth.getDonViID != 46 ){ //ttnd.donvi_dl_id
          this.Visible.tsbtnSuaCT = true
        }
        else{
          this.Visible.tsbtnSuaCT = false
        }
      } 
      else{
        this.Visible.tsbtnSuaCT = false
      }

      this.params.p_CongTrinh =  ""
      if ( this.dtChungTu.length && this.dtChungTu[0].hasOwnProperty("CONGTRINH_ID")){
        this.params.p_CongTrinh = row.CONGTRINH_ID
      }
      if(row.LOAI.toString() == "1"){
        this.params.p_LoaiChungTu = parseInt(row.LOAICT_ID)
      }
      else{
        this.params.p_LoaiChungTu = 99
      }

      this.params.p_KieuPhieu = parseInt(row.LOAIPHIEU_ID)
      this.params.p_MucDich = parseInt(row.MUCDICH_ID)

      var d_s = await this.getThongTinNgayDH({chungTuId: this.vchungtu_id})
      if(d_s.length){
        this.ngay_dh = new Date(d_s[0].NgayNH)
      }
      var d_sngay = await this.getThongTinNgayChungTu({chungTuId: this.vchungtu_id})
      if (d_sngay.length){
        this.params.p_dtpNgayGuiTT = ""
        if (d_sngay[0].ngayGuiTT && d_sngay[0].ngayGuiTT.toString()){
          this.params.p_dtpNgayGuiTT = this.f_StringToDate(d_sngay[0].ngayGuiTT,'DD-MM-YYYY hh:mm:ss')
        }

        this.params.p_dtpNgayNhanTT = ""
        if (d_sngay[0].ngayNhanTT && d_sngay[0].ngayNhanTT.toString()){
          if (this.kiemtra_quyen){
            this.Visible.tsbtnTraLai = true
          }
          this.params.p_dtpNgayNhanTT = this.f_StringToDate(d_sngay[0].ngayNhanTT,'DD-MM-YYYY hh:mm:ss')
        }

        this.params.p_dtpNgayVanBan = ""
        if (d_sngay[0].ngayVb && d_sngay[0].ngayVb.toString()){
          this.params.p_dtpNgayVanBan = this.f_StringToDate(d_sngay[0].ngayVb,'DD-MM-YYYY hh:mm:ss')
        }

        this.params.p_dtpNgayChuyenKT = ""
        if (d_sngay[0].ngayCtKt && d_sngay[0].ngayCtKt.toString()){
          this.params.p_dtpNgayChuyenKT = this.f_StringToDate(d_sngay[0].ngayCtKt,'DD-MM-YYYY hh:mm:ss')
        }

        this.params.p_dtpNgayKy = ""
        if (d_sngay[0].ngayXd && d_sngay[0].ngayXd.toString()){
          this.params.p_dtpNgayKy = this.f_StringToDate(d_sngay[0].ngayXd,'DD-MM-YYYY hh:mm:ss')
        }
      }
      this.vma_md = ""
      this.dc_ct_noibo= false
      this.dc_ct_ttvt= false
      this.dc_ct_ldscdp= false
      this.bqlda_nguonvon_dautu= false
      this.bqlda_nguonvon_khac= false

      var  d = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
      if (d.length){
        this.vma_md = d[0].toString()
      }
      if (this.vma_md == "1.NBH"){
        this.splitContainer1.Panel2Collapsed = false
      }
      else if (this.vma_md == "DC_CT" && (this.params.p_LoaiChungTu.toString == "3" || this.params.p_LoaiChungTu.toString == "2")){
        this.dc_ct_noibo = true
        // layoutbtnTBi.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Always;
      }
      else if (this.vma_md == "DC_CT_TTVT" && (this.params.p_LoaiChungTu.toString == "3" || this.params.p_LoaiChungTu.toString == "2")){
        this.dc_ct_ttvt = true
        // layoutbtnTBi.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Always;
      }
      else if ((this.vma_md == "DC_CTDP" || this.vma_md == "DC_LDSC") && (this.params.p_LoaiChungTu.toString == "3" || this.params.p_LoaiChungTu.toString == "2")){
        this.dc_ct_ldscdp  = true
        // layoutbtnTBi.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Always;
      }
      else if (this.vma_md == "NV_DAUTU_BQLDA" && this.params.p_LoaiChungTu.toString == "2"){
        this.bqlda_nguonvon_dautu = true
        // layoutbtnTBi.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Always;
      }
      else if (this.vma_md == "NV_KHAC_BQLDA" && this.params.p_LoaiChungTu.toString == "2"){
        this.bqlda_nguonvon_khac = true
        // layoutbtnTBi.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Always;
      }
      else{
        this.splitContainer1.Panel2Collapsed = true
      }

      this.params.p_TuKho = parseInt(row.KHO_GIAO_ID)

      if(row.STATUS.toString() == "1"){
        this.status = 1
      }
      else{
        this.status = 0
      }

      this.t_kho_id = parseInt(row.KHO_GIAO_ID)
      this.t_kho = row.KHO_GIAO.toString()

      this.params.p_DenKho = parseInt(row.KHO_NHAN_ID)
      if(row.KHO_NHAN_ID.toString()){
        this.kho_nhan_goc = parseInt(row.KHO_NHAN_ID)
      }

      this.params.p_dtpNgayDK = this.f_StringToDate(row.NGAY_CT,'DD-MM-YYYY hh:mm:ss')
      let kt_kieu = await this.getKieuChungTu({chungTuId: this.vchungtu_id})
      this.Enabled.gridViewVatTu.SOLUONG = true
      if(kt_kieu.length && parseInt(kt_kieu[0]["COUNT(CTCT.CTCT_ID)"])){
        this.Enabled.gridViewVatTu.SOLUONG = false
      }
      this.grcFileCV = []
      let file_cv = row.SL_VANBAN.toString()
      if (file_cv.toString() != "0"){
        var sfile_data = await this.getDSVanBanChungTu({chungTuId: this.vchungtu_id})
        if (sfile_data.length){
          let dt_z = []
          for (let dr of sfile_data){
            var sfile = dr["FILE_CV"].toString()
            let words = sfile.split('|')
            for(let i in words){
              if(words[i].toString()){
                let t = words[i]
                let tenfile = i.split('/')
                let t_t = tenfile[tenfile.length - 1]
                t = t.replace("http://10.70.52.164:8081/FILES/HPG/VATTU/", "")
                dt_z.push({
                  colFile: t,
                  colFileName: t_t,
                  colLoaiVB: dr["LOAIVB_ID"].toString()
                })
              }
            }
          }
          this.grcFileCV = dt_z

        }
        else{
          this.grcFileCV = []
        }
      }
      else{
        this.grcFileCV = []
      }

      await this.Load_ds_vattu()
      await this.Load_ds_thietbi()

      this.params.p_txtNoiDung = row.ND_GIAO.toString()
      this.params.p_txtLenhVanBan = row.SO_VB.toString()
      this.params.p_NghiepVu = this.nghiepvu_id_chungtu
      // cboMa_PDA.Text = ma_pda_chungtu;
      
      this.SetButton(3)
      await this.LAY_DS_VTCT_SERIAL()

      if (this.Enabled.tsbtnNhapMoi && this.params.p_LoaiChungTu.toString() == "9" && this.ttph_id.toString() != "4"){
        await this.AN_GROUP(false)
      }
      else{
        await this.AN_GROUP(true)
      }

      var ketqua = await this.KiemTraChungTuDieuChinh({
          "chungTuId": this.vchungtu_id,
          "kieu": 1,
          "tuNgay": this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY'),
          "denNgay": this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY')
      })
      let res
      ketqua.length ? res = ketqua[0].toString() : res = ""
      if (res.toString() != "1" && res != "0" && res != "2" && res != "3"){
        this.$toast.error(res)
        return
      }
      if (res == "3" || res == "2"){
        this.ReadOnly.cboTuKho = true
        this.Enabled.tsbtnThemVT = false
      }

      if (!this.xacnhan_in_chungtu){
        if (!row.SOLAN_IN.toString()){
          this.Text.lblSoLanXemBC = "Số lần xem BC: 0"
        }
        else{
          this.Text.lblSoLanXemBC = "Số lần xem BC: " + row.SOLAN_IN.toString()
        }
      }

      this.Visible.xTabTongHop = false
      this.Visible.colSL_CTBI = false
      this.Visible.tsbtnUpPXK = false
      
      if (this.chonvattu_lohang){
        var ketquaz = await this.KiemTraTonTaiChiTietVatTu({chungTuId: this.vchungtu_id})
        if(ketquaz.toString() != "0"){
          this.Visible.xTabTongHop = true
          this.Visible.colSL_CTBI = true
          await this.NAP_DS_VT_GROUP()
          if (this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU) && this.vma_md == this.MUCDICH_VT.CHUYEN_CP ){
            this.Visible.tsbtnUpPXK
          }
        }
      }

      // NET
      if (row.SOPHIEU_IN.toString() &&  row.SOPHIEU_IN.toString() == this.$auth.getUserName() &&
        this.dsQuyen.includes(this.DS_QUYEN_VT.QUYET_XETDUYET)){ //ttnd.ma_nd
        this.Enabled.colTKDU = true
      }
      else{
        this.Enabled.colTKDU = false
      }

      // HCM
      if (this.$auth.getMaTinh() == "HCM"){
        // quyến sửa chứng từ cho ttvt ( sửa tài khoản kho, tk đối ứng)
        if(row.NGUOI_CN.toString() == this.$auth.getUserName() && this.dsQuyen.includes(2507)){//ttnd.ma_nd
          this.Enabled.colTKDU = true
        }
        else{
          this.Enabled.colTKDU = false
        }
      }
    },
    async NAP_DS_VT_GROUP(){
      this.gridVTGroup = await this.NapDSVatTu({chungTuId: this.chungTuId})
      this.Enabled.gridViewVatTu.SOLUONG = false
    },
    SetButton(kieu){
      this.copyct = false
      this.Enabled.tsbtnNhapMoi = false
      this.Enabled.tsbtnGhiLai = false
      this.Enabled.tsbtnXoa = false
      this.Enabled.tsbtnHuyBo = false
      this.Enabled.tsbtnHoanThanh = false
      this.Enabled.tstbtnDonHang = false
      this.Enabled.tsbtnThemVT = false
      this.Enabled.tsbtnSinhSr = false
      this.Enabled.tsbtnCopyCT = false
      this.Enabled.tsbtnDongBo = false

      if (kieu == -1){//Bat dau
        this.Enabled.tsbtnGhiLai = true
        this.Enabled.tsbtnHuyBo = true
      }
      //Bat dau
      if (kieu == 0){
        this.$refs.txtSoPhieu && this.$refs.txtSoPhieu.focus()
        this.Enabled.tsbtnNhapMoi = true
        this.Clear()
      }
      //Them moi
      if (kieu == 1){
        this.vchungtu_id = 0
        this.$refs.txtSoPhieu && this.$refs.txtSoPhieu.focus()
        this.Enabled.tsbtnGhiLai = true
        this.Enabled.tsbtnHuyBo = true
        this.Enabled.tstbtnDonHang = true
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
        this.Enabled.tstbtnDonHang = (this.params.p_LoaiChungTu && this.params.p_LoaiChungTu.toString() == "1" && (this.ttph_id != 4))
        this.Enabled.tsbtnHoanThanh = (this.ttph_id != 4)

        this.Enabled.tsbtnSinhSr = true
        this.Enabled.tsbtnCopyCT = true
        this.Enabled.tsbtnDongBo = true
        this.Enabled.tsbtnUpPXK = true
      }

      switch (this.params.p_LoaiChungTu){
        case 1:
          this.Enabled.tstbtnDonHang = true
          this.Enabled.tsbtnPhieuNhap = true
          this.Enabled.tsbtnBBGNNhap = true
          break;
        case 2:
          this.Enabled.tsbtnPhieuXuat = true
          this.Enabled.tstbtnDonHang = true
          this.Enabled.tsbtnBBGNXuat = true
          break;
        case 3:
          this.Enabled.tsbtnPhieuChuyen = true
          this.Enabled.tstPhieuTUVT = true
          this.Enabled.tstbtnDonHang = true
          this.Enabled.tsbtnInXuatDVTC = true
          break;
        case 99:
          this.Enabled.tsbtnBBGNNhap = true
          this.Enabled.tstbtnDonHang = true
          this.Enabled.tsbtnPhieuNhap = true
          break;
        default:
          break;
      }

      // Load quyền
      this.Visible.tsbtnNhapMoi = false
      this.Visible.tsbtnXoa = false
      this.Visible.tsbtnGhiLai = false
      this.Visible.tsbtnHuyBo = false
      this.Visible.tsbtnThemVT = false
      this.Visible.tstbtnDonHang = false
      this.Visible.tsbtnHoanThanh = false
      this.Visible.tsbtnSinhSr = false
      this.Visible.tsbtnCopyCT = false
      this.Visible.tsbtnDongBo = false // quyen dong bo
      this.Visible.tsbtnUpPXK = false
      this.Visible.btnXoaHetVT = false
      this.Visible.simpleButton1 = false

      if (this.dsQuyen.includes(this.DS_QUYEN_VT.CAPNHAT_CHUNGTU)){
        this.Visible.tsbtnNhapMoi = true
        this.Visible.tsbtnXoa = true
        this.Visible.tsbtnGhiLai = true
        this.Visible.tsbtnHuyBo = true
        this.Visible.tsbtnThemVT = true
        this.Visible.tstbtnDonHang = true
        this.Visible.tsbtnCopyCT = true
        this.Visible.tsbtnDongBo = true
        this.Visible.btnXoaHetVT = true
      }
      if (this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU)){
        this.Visible.tsbtnHoanThanh = true
        this.Visible.tsbtnSinhSr = true
        this.Visible.simpleButton1 = true
      }
    },
    Clear(){
      this.Visible.xTabTongHop = false
      this.Visible.colSL_CTBI = false
      this.ds_Gan = []

      this.dc_ct_noibo = false
      this.dc_ct_ttvt = false
      this.dc_ct_ldscdp = false
      this.bqlda_nguonvon_dautu = false
      this.bqlda_nguonvon_khac = false

      this.params.p_txtSoPhieu = ""
      this.params.p_MucDich = ""
      this.params.p_TuKho = ""
      this.params.p_DenKho = ""
      this.params.p_txtNoiDung = ""
      this.params.p_dtpNgayDK = new Date()
      
      this.gridVatTu = []
      this.gridThietBi = []
      this.dtLuuVT = []

      this.copyct = false
      this.params.p_dtpNgayGuiTT = ""
      this.params.p_dtpNgayNhanTT = ""
      this.params.p_dtpNgayChuyenKT = ""
      this.params.p_dtpNgayKy = ""

      this.vma_md = ""
      this.params.p_txtLenhVanBan = ""
      this.params.p_dtpNgayVanBan = ""
      this.params.p_KieuPhieu = ""
      this.params.p_CongTrinh = ""
      this.grcFileCV = []
      this.params.p_Ma_PDA = ""
      this.params.p_NghiepVu = -1
      // layoutbtnTBi.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
      // btnChonTamUng
    },
    async CapNhatChiTiet_CT(themmoi){
      if (this.kieunhap.toString() == "0" && this.kieugoi_id.toString() != "2"){
        let json = JSON.stringify(this.dtVatTu)
        if (this.Visible.xTabTongHop && this.chonvattu_lohang){
          json = JSON.stringify(this.dtVatTu)
          let dt_group = this.gridVTGroup
          let json_lohang = "0"
          if (this.ds_Gan.length){
            let ds_gan_copy = []
            this.ds_Gan.forEach(a=>{
              ds_gan_copy.push({
                VATTU_ID: a.VATTU_ID,
                LOHANG: a.LOHANG,
              })
            })
            json_lohang = JSON.stringify(ds_gan_copy)
          }

          var dt_group_copy = []
          this.dt_group.forEach(a=>{
            dt_group_copy.push({
              VATTU_ID: a.VATTU_ID,
              SL_XUAT: a.SL_XUAT
            })
          })
          let json_vattu = JSON.stringify(dt_group_copy)
          var action = await this.CapNhatChungTuV2({
              "data": json,
              "chungTuId": this.vchungtu_id,
              "kieuNhap": 0,
              "kieuGoiId": 1,
              "themMoi": themmoi ? 1 : 0,
              "dataDs": json_vattu,
              "dataLoHang": json_lohang
          })
          if (action.toString() == "1"){
            this.$toast.success("Cập nhật chứng từ thành công!!!")
          }
          else{
            this.$toast.error(action.toString())
          }
        }
        else{
          // optimize 
          let kieuNhap = (this.dsQuyen.includes(2510) && this.vma_md == "2.XCP" && this.params.p_LoaiChungTu.toString() == "2")
          var action = await this.CapNhatChungTu({
                "data": json,
                "chungTuId": this.vchungtu_id,
                "kieuNhap": kieuNhap ? 10 : 0,
                "kieuGoiId": 1,
                "themMoi": themmoi ? 1 : 0
            })
            if (action.toString() == "1"){
              this.$toast.success("Cập nhật chứng từ thành công!")
            }
            else{
              this.$toast.error(action.toString())
            }
        }
      }

      // Nhập từ đơn hàng
      if (this.kieunhap.toString() == "1" && this.kieugoi_id.toString() != "2"){
        let dr
        this.dt_dh_ct = []
        let ctct_id = await this.getKeys({
          // "phanVungId" : 26,
          "keyName" : "CTCT_ID",
          "numBlockSize" : 1,
          "numRetry":  10
        })
        for (var item of this.dtDonHang){
          dt_dh_ct.push({
            CTCT_ID: ctct_id,
            CTDH_ID: parseInt(item.CTDH_ID),
            MA_VT: item.MA_VT.toString(),
            LOHANG: item.LOHANG.toString(),

            SOLUONG: parseInt(item.SOLUONG),
            DONGIA: parseInt(item.DONGIA),
            TIEN: parseInt(item.TIEN),
            SOTHANG_PB: parseInt(item.SOTHANG_PB),

            NGUOI_CN: this.$auth.getUserName(),// ttnd.ma_nd;
            MAY_CN: "quy-test", // ttnd.may_cn;
            IP_CN: "10.59.90.123", //ttnd.ip;
            MA_VT: item.MA_VT.toString(),

            LOAIKHO: parseInt(item.LOAIKHO),
            MAKHOTD: parseInt(item.MAKHOTD),
            CHATLUONG_ID: parseInt(item.CHATLUONG_ID),
          })
          ctct_id++
        }

        let json = JSON.stringify(dt_dh_ct)

        var action = await this.CapNhatChungTu({
                "data": json,
                "chungTuId": this.vchungtu_id,
                "kieuNhap": 1,
                "kieuGoiId": 1,
                "themMoi": themmoi ? 1 : 0
            })
        if (action.toString() == "1"){
          this.$toast.success("Cập nhật chứng từ thành công!")
        }
        else{
          this.$toast.error(action.toString())
        }
      }
      // Nhập từ kho
      if (this.kieunhap.toString() == "2" && (this.kieugoi_id.toString() != "2" || this.dc_ct_noibo || this.dc_ct_ttvt || this.dc_ct_ldscdp 
                || this.bqlda_nguonvon_dautu || this.bqlda_nguonvon_khac)){
        if (!this.copyct){
          let json = JSON.stringify(dtThemVT)
          if (this.Visible.xTabTongHop && this.chonvattu_lohang){
            var dt_group = this.gridVTGroup
            var  ds_gan_copy = []
            dt_group.forEach(a=>
              ds_gan_copy.push({
                VATTU_ID: a.VATTU_ID,
                LOHANG: a.LOHANG,
              })
            )
            let json_lohang = JSON.stringify(ds_gan_copy)
            var dt_group_copy = []
            this.dt_group.forEach(a=>dt_group_copy.push({
              VATTU_ID: a.VATTU_ID,
              SL_XUAT: a.SL_XUAT,
            }))
            let json_vattu = JSON.stringify(dt_group_copy)
            var action = await this.CapNhatChungTuV2({
              "data": json,
              "chungTuId": this.vchungtu_id,
              "kieuNhap": 2,
              "kieuGoiId": 1,
              "themMoi": themmoi ? 1 : 0,
              "dataDs": json_vattu,
              "dataLoHang": json_lohang
            })
            if (action.toString() == "1"){
              this.$$toast.success("Cập nhật chứng từ thành công!!!")
            }
            else{
              this.$toast.error(action.toString())
            }
          }
          else{
            var action = await this.CapNhatChungTu({
              "data": json,
              "chungTuId": this.vchungtu_id,
              "kieuNhap": 2,
              "kieuGoiId": 1,
              "themMoi": themmoi ? 1 : 0
            })
            if (action.toString() == "1"){
              this.$toast.success("Cập nhật chứng từ thành công!")
            }
            else{
              this.$toast.error(action.toString())
            }
          }
        }
        else{
          var action = await this.CapNhatChungTu({
            "data": json,
            "chungTuId": this.vchungtu_id,
            "kieuNhap": 2,
            "kieuGoiId": 1,
            "themMoi": themmoi ? 1 : 0
          })
          if (action.toString() == "1"){
            this.$toast.success("Cập nhật chứng từ thành công!")
          }
          else{
            this.$toast.error(action.toString())
          }
        }
      }
      // Nhập lẻ
      if (this.kieugoi_id.toString() == "2"){
        if (this.kieunhap.toString() == "3"){
          var t = this.ds_md.filter(a=> a.MUCDICH_ID == this.params.p_MucDich)
          if (t.length && (t[0].MA_MD.toString() == "N-X.DCMSL" || t[0]["MA_MD"].toString() == "N.DCDG"  || t[0]["MA_MD"].toString() == "SC_BH_DONGIA")
           && this.params.p_LoaiChungTu.toString() == "99" && (await this.get_mamd(this.params.p_KieuPhieu)) == "10"){
             let ztemp = []
             if (t[0]["MA_MD"].toString() == "N-X.DCMSL"){
               ztemp = this.dtVT_LE
             }
             else{
               ztemp = this.dt_temp
             }
             var s = this.gridVatTu
             let json = JSON.stringify(ztemp)
             var action = await this.CapNhatChungTu({
                "data": json,
                "chungTuId": this.vchungtu_id,
                "kieuNhap": 99,
                "kieuGoiId": 2,
                "themMoi": themmoi ? 1 : 0
                })
            if (action.toString() == "1"){
              this.$toast.success("Cập nhật chứng từ thành công!!!!")
            }
            else{
              this.$toast.error(action.toString())
            }
          }
          else{
            this.dtchitiet_ct = []
            for(var item of this.dtVT_LE){
              this.dtchitiet_ct.push({
                CTCT_ID: item.CTCT_ID.toString(),
                VATTU_ID: item.VATTU_ID.toString(),
                SOLUONG: item.SOLUONG.toString(),
                DONGIA: item.DONGIA.toString(),
                TIEN: item.TIEN.toString(),

                NGUOI_CN: this.$auth.getUserName(), //ttnd.ma_nd
                MAY_CN: 'quy-test',//ttnd.may_cn
                IP_CN: "10.59.90.123",//ttnd.ip
                SOTHANG_PB: item.SOTHANG_PB.toString() ? item.SOTHANG_PB.toString() : 0,
                HAN_BH: item.HAN_BH.toString() ? item.HAN_BH.toString() : "",
                MAKHOTD: item.MAKHOTD.toString(),
                LOAIKHO: item.LOAIKHO.toString(),
                MATHUNG: item.MATHUNG.toString(),
                MATHETS: item.MATHETS.toString(),
                GHICHU: item.GHICHU.toString(),
                TY_LE: item.TY_LE.toString(),
                CHATLUONG: item.CHATLUONG.toString(),
                NGUON_GOC: item.NGUON_GOC.toString(),
                DONGTBI_ID: item.DONGTBI_ID.toString(),
                HETHONG_ID: item.HETHONG_ID.toString(),
                CHATLUONG_ID: item.CHATLUONG_ID.toString(),
                TINHTRANG: item.TINHTRANG.toString(),
              })
            }
            let json = JSON.stringify(this.dtchitiet_ct)
            var action = await this.CapNhatChungTu({
              "data": json,
              "chungTuId": this.vchungtu_id,
              "kieuNhap": 3,
              "kieuGoiId": 2,
              "themMoi": themmoi ? 1 : 0
            })
            if (action.toString() == "1"){
              this.$toast.success("Cập nhật chứng từ thành công!!!!")
            }
            else{
              this.$toast.error(action.toString())
            }
          }
        }
        else{
          var t = this.ds_md.filter(a=>a.MUCDICH_ID.toString() == this.params.p_MucDich.toString())
          if (t.length > 0 && (t[0]["MA_MD"].toString() == "N.DCDG" || t[0]["MA_MD"].toString() == "N.TTCL"
            || t[0]["MA_MD"].toString() == "SC_BH_DONGIA" || t[0]["MA_MD"].toString() == "NV_DAUTU_BQLDA"
            || t[0]["MA_MD"].toString() == "NV_KHAC_BQLDA") && this.params.p_LoaiChungTu.toString() == "99" 
            && (await this.get_mamd(this.params.p_KieuPhieu)) == "10"){
              let dtchitiet = this.gridVatTu
              let json = JSON.stringify(dtchitiet)
              var action = await this.CapNhatChungTu({
                "data": json,
                "chungTuId": this.vchungtu_id,
                "kieuNhap": 99,
                "kieuGoiId": 2,
                "themMoi": themmoi ? 1 : 0
              })
              if (action.toString() == "1"){
                this.$toast.success("Cập nhật chứng từ thành công!~")
              }
              else{
                this.$toast.error(action.toString())
              }
          }
          else{
            var dstb = this.gridVatTu
            if (dstb.length){
              for(let j = 0;j < dstb.length;j++){
                if (dstb[i].LOAITBI_ID.toString() != "2"){
                  this.dtVatTu[j].MATHUNG = dstb[j].MATHUNG.toString()
                }
              }
            }
            let json = JSON.stringify(this.dtVatTu)
            var action = await this.CapNhatChungTu({
                "data": json,
                "chungTuId": this.vchungtu_id,
                "kieuNhap": -1,
                "kieuGoiId": 2,
                "themMoi": themmoi ? 1 : 0
              })
              if (action.toString() == "1"){
                this.$toast.success("Cập nhật chứng từ thành công!~")
              }
              else{
                this.$toast.error(action.toString())
              }
          }
        }
      }
    },
    async tsbtnThoat_Click(){
      // Close()
    },
    async tsbtnNhapMoi_Click(){
      this.SetButton(1)
      let EditValue = this.setTag('cboLoaiChungTu')
      EditValue.toString() ? this.params.p_LoaiChungTu = EditValue : ""
    },
    async CapNhat_CT(kieu,themmoi,traphieu){
      let loaict_id
      let xuat_hoantra = false
      if (this.ds_md.length){
        var t = this.ds_md.filter(a=> a.MUCDICH_ID == this.params.p_MucDich)
        if (t.length > 0 && (t[0]["MA_MD"].toString() == "X.HTVT" || t[0]["MA_MD"].toString() == "X.TDVT")){
          if (this.params.p_LoaiChungTu.toString() == "2" || this.params.p_LoaiChungTu.toString() == "3"){
            this.xuat_hoantra = true
          }
        }
      }

      if (themmoi){
        this.vchungtu_id = await this.getKeys({
          "keyName" : "CHUNGTU_ID",
          "numBlockSize" : 1,
          "numRetry":  10
        })
        if (this.kieugoi_id == 1){
          this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV5({
            "khoGiaoId": parseInt(this.params.p_TuKho),
            "khoNhanId": parseInt(this.params.p_DenKho),
            "loaiCtId": parseInt(this.params.p_LoaiChungTu),
            "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
            "donViNdId": this.$auth.getDonViID(),
            "loaiPhieuId": parseInt(this.params.p_KieuPhieu),
            "mucDichId": parseInt(this.params.p_MucDich)
          })
          this.loaict_id = parseInt(this.params.p_LoaiChungTu)
        }
        else{
          this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV5({
            "khoGiaoId": parseInt(this.params.p_TuKho),
            "khoNhanId": parseInt(this.params.p_DenKho),
            "loaiCtId": 1,
            "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
            "donViNdId": this.$auth.getDonViID(),
            "loaiPhieuId": parseInt(this.params.p_KieuPhieu),
            "mucDichId": parseInt(this.params.p_MucDich)
          })
          this.loaict_id = 1
        } 
      }
      else{
        if (this.f_StringToDate(this.gridviewChungTu_FocusedRow.NGAY_CT,'DD-MM-YYYY').getFullYear() != this.params.p_dtpNgayDK.getFullYear() ){
          if (this.kieugoi_id == 1){
            this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV5({
              "khoGiaoId": parseInt(this.params.p_TuKho),
              "khoNhanId": parseInt(this.params.p_DenKho),
              "loaiCtId": parseInt(this.params.p_LoaiChungTu),
              "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
              "donViNdId": this.$auth.getDonViID(),
              "loaiPhieuId": parseInt(this.params.p_KieuPhieu),
              "mucDichId": parseInt(this.params.p_MucDich)
            })
          }
          else{
            this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV5({
              "khoGiaoId": parseInt(this.params.p_TuKho),
              "khoNhanId": parseInt(this.params.p_DenKho),
              "loaiCtId": 1,
              "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
              "donViNdId": this.$auth.getDonViID(),
              "loaiPhieuId": parseInt(this.params.p_KieuPhieu),
              "mucDichId": parseInt(this.params.p_MucDich)
            })
          }
        }
        else{
          // Nếu sửa kho đến thì sinh lại số phiếu
          if (!this.xuat_hoantra){
            if (this.gridviewChungTu_FocusedRow.KHO_NHAN_ID != this.params.p_DenKho){
              if (this.kieugoi_id == 1){
                this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV5({
                  "khoGiaoId": parseInt(this.params.p_TuKho),
                  "khoNhanId": parseInt(this.params.p_DenKho),
                  "loaiCtId": parseInt(this.params.p_LoaiChungTu),
                  "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
                  "donViNdId": this.$auth.getDonViID(),
                  "loaiPhieuId": parseInt(this.params.p_KieuPhieu),
                  "mucDichId": parseInt(this.params.p_MucDich)
                })
              }
              else{
                this.params.p_txtSoPhieu = await this.SinhSoPhieuVatTuV5({
                  "khoGiaoId": parseInt(this.params.p_TuKho),
                  "khoNhanId": parseInt(this.params.p_DenKho),
                  "loaiCtId": 1,
                  "namCt": this.params.p_dtpNgayDK.getFullYear().toString().substring(2),
                  "donViNdId": this.$auth.getDonViID(),
                  "loaiPhieuId": parseInt(this.params.p_KieuPhieu),
                  "mucDichId": parseInt(this.params.p_MucDich)
                })
              }
            }
          }
        }

        if (this.kieugoi_id == "2"){
          this.loaict_id = 1
        }
        else{
          this.loaict_id = parseInt(this.params.p_LoaiChungTu)
        }
      }
      let dr;
      this.dtct = []
      dr.CHUNGTU_ID = this.vchungtu_id
      dr.LOAICT_ID = this.loaict_id
      dr.SOPHIEU = this.params.p_txtSoPhieu
      dr.KHO_GIAO_ID = this.params.p_TuKho

      if (!this.xuat_hoantra){
        dr.KHO_NHAN_ID = this.params.p_DenKho
      }
      else{
        dr.KHO_NHAN_ID = ""
      }
      dr.KHO_GIAO_ID = this.params.p_txtNoiDung
      dr.MUCDICH_ID = this.params.p_MucDich
      dr.NGAYGUI_TT = this.params.p_dtpNgayGuiTT
      dr.NGAYNHAN_TT = this.params.p_dtpNgayNhanTT
      dr.NGAY_VB = this.params.p_dtpNgayVanBan
      dr.SO_VB = this.params.p_txtLenhVanBan
      dr.LOAIPHIEU_ID = this.params.p_KieuPhieu
      dr.NHANVIEN_GIAO_ID = this.$auth.getNhanVienID()
      dr.DONVI_NHAN_ID = this.$auth.getDonViID()
      dr.DONVI_GIAO_ID =  this.$auth.getDonViID()
      dr.NGUOI_CN = this.$auth.getUserName() //ttnd.ma_nd

      if (this.traphieu){
        dr.NGUOI_CN = this.gridviewChungTu_FocusedRow.NGUOI_CN ? this.gridviewChungTu_FocusedRow.NGUOI_CN.toString() : ""
        dr.NHANVIEN_GIAO_ID = this.gridviewChungTu_FocusedRow.NHANVIEN_GIAO_ID ? this.gridviewChungTu_FocusedRow.NHANVIEN_GIAO_ID.toString() : ""
        dr.DONVI_NHAN_ID = this.gridviewChungTu_FocusedRow.DONVI_NHAN_ID ? this.gridviewChungTu_FocusedRow.DONVI_NHAN_ID.toString() : ""
        dr.DONVI_GIAO_ID = this.gridviewChungTu_FocusedRow.DONVI_GIAO_ID ? this.gridviewChungTu_FocusedRow.DONVI_GIAO_ID.toString() : ""
      }

      if(this.params.p_dtpNgayDK.getHours() == 0 && this.params.p_dtpNgayDK.getMinutes() == 0){
        this.params.p_dtpNgayDK = moment(this.params.p_dtpNgayDK).add(1,'minutes').toDate()
      }
      if (this.params.p_CongTrinh.toString()){
        dr.CONGTRINH_ID = parseInt(this.params.p_CongTrinh)
      }
      else{
        dr.CONGTRINH_ID = ""
      }
      dr.NGAY_CT = this.params.p_dtpNgayDK
      dr.TTPH_ID = 1
      dr.LOAI = this.vloai
      dr.NGAY_CN = new Date()
      dr.IP_CN = '10.59.90.99' //ttnd.ip
      dr.MAY_CN = 'quy-test' //ttnd.may_cn

      //new la nhap le dieu chinh
      if (this.traphieu){
        if (this.Enabled.tsbtnNhapMoi){
          var result = await this.KiemTraChungTuDieuChinh({
              "chungTuId": this.vchungtu_id,
              "kieu": 1,
              "tuNgay": this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY'),
              "denNgay": this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY')
          })
          let res = result.toString();
          if (res.toString() != "1" && res != "0" && res != "2" && res != "3"){
            this.$toast.error(res)
            return
          }
          if (res == "2"){
            var sp = await this.getIDChungTuCha({chungTuId: this.vchungtu_id})
            if (!sp.length){
              this.$toast.error("Không tìm thấy chứng từ xuất điều chỉnh! Hãy xem lại!")
              return
            }
            dr.CHUNGTU_CHA_ID = parseInt(sp[0])
          }
        }
      }
      else{
        if (this.Enabled.tsbtnNhapMoi){
          var result = await this.KiemTraChungTuDieuChinh({
              "chungTuId": this.vchungtu_id,
              "kieu": 1,
              "tuNgay": this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY'),
              "denNgay": this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY')
          })
          let res = result.toString();
          if (res.toString() != "1" && res != "0" && res != "2" && res != "3"){
            this.$toast.error(res)
            return
          }
          if (res == "2"){
            var sp = await this.getIDChungTuCha({chungTuId: this.vchungtu_id})
            if (!sp.length){
              this.$toast.error("Không tìm thấy chứng từ xuất điều chỉnh! Hãy xem lại!")
              return
            }
            dr.CHUNGTU_CHA_ID = parseInt(sp[0])
          }
        }
        else{
          var d = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
          var kieuphieu = await this.getMaMucDichTheoID({mucDichId: this.params.p_KieuPhieu})
          let kieuphieu_tinh = this.$auth.getMaTinh() == 'HCM' ? "PN10" : "PNDC"
          let l = parseInt(this.params.p_LoaiChungTu)
          if (!d.length){
            this.$toast.error("Xem lại kiểu phiếu và mục đích !")
            return
          }
          if ((d[0].toString() == "N.DCDG" || d[0].toString() == "SC_BH_DONGIA"
            || d[0].toString() == "NV_DAUTU_BQLDA" || d[0].toString() == "NV_KHAC_BQLDA") && l == 99
            && kieuphieu[0].toString() == kieuphieu_tinh){
              var sp = await this.getIDChungTuTheoSoPhieu({soPhieu: this.sophieu_temp})
              if (!sp.length){
                this.$toast.error("Không tìm thấy chứng từ xuất điều chỉnh! Hãy xem lại!")
                return
              }
              dr.CHUNGTU_CHA_ID = parseInt(sp[0])
          }
        }
      }
      this.dtct.push(dr)
      let json = JSON.stringify(this.dtct)
      var func = await this.CapNhatChungTuTheoKieu({
        "data": json,
        "kieu": kieu
      })
      return func.toString()
    },
    async GHILAI_CT(vztag){
      let dtp = this.params.p_dtpNgayDK
      if (!this.Enabled.tsbtnNhapMoi){
        if (! await this.KiemTraDuLieu(true, false, vztag)){ return}
        this.params.p_dtpDenNgay = dtp
        this.params.p_dtpTuNgay = new Date(dtp.getTime() - 7*24*60*60*1000)
        if (this.kiemtra_quyen){
          if (! await this.KIEMTRA_QUYEN_XN(5,this.params.p_TuKho)){ return }
        }
        if (! await this.KiemTra_NhapLeDieuChinh(false)){ return }
        if (! await this.KiemTra_LoaiKho_MaKho()){ return }

        await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn ghi lại!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                let kq = ""
                kq = await this.CapNhat_CT(1, true, false)
                if (kq == "1"){
                  await this.CapNhatChiTiet_CT(true)
                }
                else{
                  this.toast.error("Có lỗi khi tạo chứng từ " + kq)
                }
                        
              }
          })
          .catch(err => {
              // An error occurred
          })
      }
      else{
        if (! await this.KiemTraDuLieu(false, false, vztag)){ return }
        if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id, vztag)){ return }
        if (this.kiemtra_quyen) {
          if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
        }

        if (! await this.KiemTra_NhapLeDieuChinh(false)){ return }
        if (! await this.KiemTra_LoaiKho_MaKho()) { return }

        let msg_add = this.dsQuyen.includes(2510) && this.vma_md == "2.XCP" ?  "\nPhiếu xuất 2XCP chỉ cho phép thay đổi kiểu phiếu, nội dung, kho đến, số lượng đặt ~" : ""

        await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn ghi lại!" + msg_add, {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                  let kq = await this.CapNhat_CT(2, false, false)
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
                      try {
                        await this.GuiSMSChungTu({
                          tuDong: 2,
                          chungTuId: this.vchungtu_id,
                          mayCn: 'quy-test',//ttnd.may_cn
                          nguoiCn: this.$auth.getUserName(),//ttnd.ma_nd
                          ipCn: '10.59.90.123'//ttnd.ip
                        })
                      } catch (error) {
                        this.$toast.error("Có lỗi khi send smsm tự động")
                      }
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

                  if (this.kieugoi_id == 2){
                    let mucdich_id = parseInt(this.params.p_MucDich)
                    var d = await this.getMaMucDichTheoID({mucDichId: mucdich_id})
                    // DHSX đang comment
                  }
                  if (kq == "1"){
                    await this.CapNhatChiTiet_CT(false)
                  }
                  else{
                    this.$toast.error("Có lỗi xảy ra: " + kq)
                  }

              }
          })
          .catch(err => {
              // An error occurred
          })
      }
      let vchungtu_tmp_id = vchungtu_id
      if (this.vtdk.length){
        await this.CAPNHAT_DANGKY(vchungtu_tmp_id)
      }
      await this.Load_ds_chungtu()
      // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", vchungtu_tmp_id)
      this.copyct = false
    },
    async tsbtnGhiLai_Click(){
      if (this.dsQuyen.includes(2510)){
        await this.GHILAI_CT("GHILAI_NUTGHILAI")
      }
      else{
        await this.GHILAI_CT("GHILAI")
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
      let EditValue = this.setTag('cboLoaiChungTu')
      EditValue.toString() ? this.params.p_LoaiChungTu = EditValue : ""
    },
    async xoa_con_hoan_tra(chungtu_id){
      var md = await this.getIDTrangThaiPhieu({chungTuId: chungtu_id})
      if (md.length){
        this.$toast.error("Có lỗi khi xóa phiếu hoàn trả xuất!")
        return false
      }
      if (md[0].toString() == "4"){
        let sss = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: chungtu_id})
        if (sss.toString() != "1"){
          this.$toast.error("[" + chungtu_id + "] " + sss)
          return false
        }
      }
      let sx = await this.XoaPhieuDaHoanThanh({
        "chungTuId": chungtu_id,
        "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
        "mayCn": "quy-test",//ttnd.may_cn
        "ipCn": "10.59.90.123"//ttnd.ip
      })
      if (sx != "1"){
        this.$toast.error("Có lỗi trong quá trình xóa phiếu cha! " + sx)
        return false
      }
      await this.XoaTatCaChiTietChungTu({chungTuId: chungtu_id})
      var t = await this.XoaThongTinLienQuanCT({chungTuId: chungtu_id})
      return true
    },
    async xoa_phieu_hoan_tra(){
      let result = ""
      let mucdich_id = this.gridviewChungTu_FocusedRow.MUCDICH_ID
      let loaict_id = this.gridviewChungTu_FocusedRow.LOAICT_ID
      if (mucdich_id.toString() != "234" && mucdich_id.toString() != "278"){
        result = "1"
        return result
      }
      if ((mucdich_id.toString() == "234" || mucdich_id.toString() == "278") && (loaict_id.toString() == "2" || loaict_id.toString() == "3")){
        result = "1"
        return result
      }
      let json = [{CHUNGTU_ID: this.vchungtu_id}]
      let json_list = JSON.stringify(json)
      kieu_th = this.$auth.getMaTinh() == "NET" ? 5 : 3 //string cut_db = DatabaseConstants.DB12.Substring(DatabaseConstants.DB12.Length - 3);

      var ketqua = await this.GiaoPhieuCTHoanTra({
        "kieuPhieu": 2,
        "ttPhId": "",
        "khoDenId": "",
        "tagFrm": 2,
        "kieuTh": kieu_th,
        "ghiChu": "",
        "donViDenId": 0,
        "ngayVb": "",
        "soVb": "",
        "fileCv": "",
        "ds": json_list,
        "dsCt": "0",
        "dsTbi": "0",
        "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
        "mayCn": "quy-test",//ttnd.may_cn
        "ipCn": "10.59.90.123"//ttnd.ip
      })
      if (ketqua.toString().include('ORA')){
        this.$toast.error("Lỗi khi xóa phiếu xuất cặp với phiếu nhập hoàn trả: " + ketqua)
        result = "0"
        return result
      }
      else if (ketqua.toString().include('chungtu_id')){
        let split = ketqua.toString().split('-')
        let chungtu = split[0]
        result = chungtu.toString()
      }
      else{
        this.$toast.error(ketqua.toString())
        result = "0"
        return result
      }
    },
    async tsbtnXoa_Click(){
      // try {
        if (this.vchungtu_id == 0){
          this.$toast.error("Bạn phải chọn chứng từ để xóa")
          return
        }
        if (! await this.KiemTraHanChotCT()){// Kiếm tra hạn chốt ok thì xóa
          return
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
            // Xóa toàn bộ 
              if (value.vkieu_luachon == "1"){
                  if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id, "")){ return }
                  if (this.kiemtra_quyen) {
                    var kt = await this.KiemTraXoaChungTu({chungTuId: this.vchungtu_id,maNd: this.$auth.getUserName()}) // ttnd.ma_nd
                    if (kt.toString() != "1"){
                      this.$toast.error(kt.toString())
                      return
                    }
                    if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
                  }
                  let mucdich_id = parseInt(this.gridviewChungTu_FocusedRow.MUCDICH_ID)
                  let loaict_id = parseInt(this.gridviewChungTu_FocusedRow.LOAICT_ID)
                  var md =  await this.KiemTraCTXuatHoanTraTheoSoPhieu({soPhieuGop: this.gridviewChungTu_FocusedRow.SOPHIEUGOP})
                  var dt_null = await this.KiemTraCTXuatHoanTraCoCTCon({chungTuChaId: this.vchungtu_id})
                  if ((mucdich_id.toString() == "234" || mucdich_id.toString() == "278") && (loaict_id.toString() == "2" || loaict_id.toString() == "3")){
                    if (md.length){
                      this.$toast.error("Chứng từ xuất hoàn trả đã cặp với chứng từ nhập hoàn trả! Hãy xóa phiếu nhập trước!")
                      return
                    }
                    if (dt_null.length){
                      this.$toast.error("Chứng từ xuất hoàn trả đã có chứng từ con! Không thể chỉnh sửa/xóa !")
                      return
                    }
                  }
                  let result = await this.xoa_phieu_hoan_tra()
                  if (result == "0") { return }
                  let s = ""
                  if (ttph_id == 4){
                    s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
                    if (s.toString() != "1"){
                      this.$toast.error(s)
                      return
                    }
                  }
                  // Xóa Đồng bộ
                  let db_kt = await this.KiemTraXoaDongBo({
                    chungTuId: this.vchungtu_id,
                    maNd: this.getUserName(),//ttnd.ma_nd
                    mayCn: 'quy-test',//ttnd.may_cn
                    kieu: value.vkieu_luachon
                  })
                  //Nếu tồn tại chứng từ tập đoàn thì phải xóa đồng bộ thành công
                  if (db_kt != "xoa"){ 
                    if (db_kt == "ok"){
                      this.$toast.success("Xóa chứng từ kế toán tập đoàn thành công!")
                    }
                    else{
                      this.$toast.error("Xóa chứng từ kế toán tập đoàn có lỗi:" + db_kt + "!")
                      if (this.vchungtu_id != 0){
                        // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", this.vchungtu_id)
                      }
                      await this.Load_ds_chungtu();
                    }
                  }

                  if(this.params.p_ckbSendSMS && this.ttph_id == 4){
                      try {
                        await this.GuiSMSChungTu({
                          tuDong: 2,
                          chungTuId: this.vchungtu_id,
                          mayCn: 'quy-test',//ttnd.may_cn
                          nguoiCn: this.$auth.getUserName(),//ttnd.ma_nd
                          ipCn: '10.59.90.123'//ttnd.ip
                        })
                      } catch (error) {
                        this.$toast.error("Có lỗi khi send smsm tự động")
                      }
                    }
                  if (this.grvFileVB.length){
                    try {
                      let dt = this.grcFileCV
                      for (var dr of  dt){
                        let t_y = dr["colFile"].toString()
                        t_y = t_y.replace("http://10.70.52.164:8081/", "")
                        let str_del = "http://10.70.52.164:8081/FILE/UploadFile.aspx?user=dauxanh&pass=rauma&action=delete&filePath=" + t_y;
                        // System.Net.HttpWebRequest request = (System.Net.HttpWebRequest)System.Net.WebRequest.Create(str_del);
                        // request.GetResponse();
                      }
                    } catch (error) {
                      
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
                    "mayCn": "quy-test",//ttnd.may_cn
                    "ipCn": "10.59.90.123"//ttnd.ip
                  })
                  if (s.toString() != "1"){
                    this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s + "")
                    return
                  }
                  await this.XoaTatCaChiTietChungTu({chungTuId: this.vchungtu_id})
                  var t = await this.XoaThongTinLienQuanCT({chungTuId: this.vchungtu_id})
                  if (result != "0" && result != "1" && result != ""){
                    if (! await this.xoa_con_hoan_tra(parseInt(result))){
                      return
                    }
                  }
                  this.$toast.success("Xóa thành công!")
                  if(this.vchungtu_id != 0){
                      // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", vchungtu_id);
                    }
                  await this.Load_ds_chungtu()

              }
              // Xóa Đồng bộ
              // Chỉ xóa tập đoàn
              if(value.vkieu_luachon == "2"){
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
                    maNd: this.getUserName(),//ttnd.ma_nd
                    mayCn: 'quy-test',//ttnd.may_cn
                    kieu: value.vkieu_luachon
                  })
                  //Nếu tồn tại chứng từ tập đoàn thì phải xóa đồng bộ thành công
                  if (db_kt != "xoa"){ 
                    if (db_kt == "ok"){
                      this.$toast.success("Xóa chứng từ kế toán tập đoàn thành công!")
                    }
                    else{
                      this.$toast.error("Xóa chứng từ kế toán tập đoàn có lỗi:" + db_kt + "!")
                      if (this.vchungtu_id != 0){
                        // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", this.vchungtu_id)
                      }
                      await this.Load_ds_chungtu();
                    }
                  }

              }
          })
          .catch(err => {
              // An error occurred
          })
      // } catch (error) {
      //   this.$toast.error("Lỗi hàm xóa chứng từ")
      // }
    },
    async KiemTraDieuPhoi(){
      var dieuphoi = await this.getIDNhomNguoiDung({maNd: this.$auth.getUserName()}) //ttnd.ma_nd
      if (dieuphoi.length){
        if (dieuphoi[0].toString() == "104" && this.ttph_id == 4){// Là điều phối và phiếu đã HT
          return
        }
      }
      return false
    },
    async KiemTraNguoiCN(){
      return this.gridviewChungTu_FocusedRow.NGUOI_CN == this.$auth.getUserName() //ttnd.ma_nd
    },
    async KiemTraDuLieu(themmoi,traphieu,tag){
      if (!themmoi){
        if (this.nghiepvu_id_chungtu != "" && ((this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != "" ) 
            || this.nghiepvu_id_chungtu == "288")){
            this.$toast.error("Chứng từ tạo từ dự án IMS không được sửa")
            return false
        }
        if (! await this.KiemTraNguoiCN() && !traphieu){
          if (this.chonvattu_lohang && this.Visible.xTabTongHop && this.tag == "UPSL" && this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU)  ){

          }
          else{
            this.$toast.error("Bạn không được sửa chứng từ của người khác!")
            this.$refs.cboLoaiChungTu.focusIn()
            return false
          }
        }

        let s = await this.KiemTraCapNhatChungTu({chungTuId: this.vchungtu_id})
        if (s.toString() != "1"){
          this.$toast.error(s)
          return false
        }
        if (this.chonvattu_lohang && this.Visible.xTabTongHop ){
          var dt_thietbi_group = this.gridThietBi
          if (dt_thietbi_group.length){
            for (var dr of dt_thietbi_group){
              if (dr["LOAITBI_ID"].toString() == "2" || dr["LOAITBI_ID"].toString() == "3"){ continue}
              var dr_rowvt = this.dtVatTu.filter(a=>a.CTCT_ID ==  dr["CTCT_ID"])
              if (dr_rowvt.length){
                dr_rowvt[0]["SOLUONG"] = dr["SOLUONG"]
                dr_rowvt[0]["TIEN"] = Math.round(parseInt(dr["SOLUONG"]) * parseInt(dr["DONGIA"]))
                dr_rowvt[0]["VAT"] = Math.round(parseInt(dr["TIEN"])*10/100)
              }
            }
          }

          var dt_kiemtra = []
          this.dtVatTu.forEach(a=> dt_kiemtra.push({
            VATTU_ID: a.VATTU_ID,
            LOHANG: a.LOHANG,
            SOLUONG: a.SOLUONG,
            CTCT_ID: a.CTCT_ID,
          }))
          let json = JSON.stringify(dt_kiemtra)
          if (! await this.KiemTraChiTiet_VT(json)){
            return false
          }

        }
      }
      if (themmoi){//&& ngay_dh != DateTime.MinValue
        if (this.ngay_dh > this.params.p_dtpNgayDK){
          this.$toast.error("Ngày chứng từ phải lớn hơn ngày yêu cầu nhập kho (" + ngay_dh + "")
          return false
        }
      }
      if (this.ttph_id == 4 && !themmoi){
        let s = await this.KiemTraXoaPhieuDaHoanThanh({chungTuId: this.vchungtu_id})
        if (s.toString() != "1"){
          this.$toast.error(s)
          this.$refs.cboLoaiChungTu.focusIn()
          return false
        }
        if (this.params.p_DenKho.toString() == this.kho_nhan_goc.toString()){
          this.$toast.error("Bạn ghi lại phải giữ nguyên đúng kho đến (trạng thái DD có thể sửa kho đến)")
          this.params.p_DenKho = this.kho_nhan_goc
          return false
        }
      }

      if (!this.params.p_LoaiChungTu.toString()){
        this.$toast.error("Bạn chưa chọn loại chứng từ!")
        this.$refs.cboLoaiChungTu.focusIn()
        return false
      }

      if (!this.params.p_MucDich.toString()){
        this.$toast.error("Bạn chưa chọn Mục đích!")
        this.$refs.cboMucDich.focusIn()
        return false
      }

      if (!this.params.p_TuKho.toString()){
        this.$toast.error("Bạn chưa chọn từ kho!")
        this.$refs.cboTuKho.focusIn()
        return false
      }
      // Bổ sung cho hoàn trả
      if  (themmoi){
        this.dc_ct_noibo = false 
        this.dc_ct_noibo = false 
        this.dc_ct_noibo = false
        
        this.bqlda_nguonvon_dautu = false
        this.bqlda_nguonvon_khac = false
      }
      else{
        var y =  this.ds_md.filter(a=>a.MUCDICH_ID == this.params.p_MucDich)
        if (y.length){
          this.dc_ct_noibo = false
        }
      }

      let xuat_hoantra = false
      if (this.ds_md.length){
        var t = ds_md.filter(a=>a.MUCDICH_ID == this.params.p_MucDich)
        if (t.length && (t[0]["MA_MD"].toString() == "X.HTVT" || t[0]["MA_MD"].toString() == "X.TDVT")){
          if (this.params.p_LoaiChungTu.toString() == "2" || this.params.p_LoaiChungTu.toString() == "3" ){
            xuat_hoantra = true
          }
        }

        if (t.length && t[0]["MA_MD"].toString() == "DC_CT"){
          if (this.params.p_LoaiChungTu.toString() == "3" || this.params.p_LoaiChungTu.toString() == "2"){
            this.dc_ct_noibo = true
            this.dc_ct_ttvt = false
            this.dc_ct_ldscdp = false
          }
        }

        if (t.length && t[0]["MA_MD"].toString() == "DC_CT_TTVT"){
          if (this.params.p_LoaiChungTu.toString() == "3" || this.params.p_LoaiChungTu.toString() == "2"){
            this.dc_ct_ttvt = true
            this.dc_ct_noibo = false
            this.dc_ct_ldscdp = false
          }
        }
        if(t.length && (t[0]["MA_MD"].toString() == "DC_LDSC" || t[0]["MA_MD"].toString() == "DC_CTDP")){
          if(this.params.p_LoaiChungTu.toString() == "3" || this.params.p_LoaiChungTu.toString() == "2"){
            this.dc_ct_ttvt = false
            this.dc_ct_noibo = false
            this.dc_ct_ldscdp = true
          }
        }

        var kphieu = await this.getMaMucDichTheoID({mucDichId: this.params.p_KieuPhieu})
        if(t.length && t[0]["MA_MD"].toString() == "NV_DAUTU_BQLDA"){
          if (this.params.p_LoaiChungTu.toString() == "2" && kphieu == "PX10" ){
            this.bqlda_nguonvon_dautu = true
            this.bqlda_nguonvon_khac = false
          }
        }
        if(t.length && t[0]["MA_MD"].toString() == "NV_KHAC_BQLDA" && kphieu == "PX10"){
          if (this.params.p_LoaiChungTu.toString() == "2"){
            this.bqlda_nguonvon_dautu = false
            this.bqlda_nguonvon_khac = true
          }
        }
      }
      if (!await this.KiemTra_XuatHoanTra(true)){ return false }
      
      if (!this.params.p_DenKho.toString()){
        this.$toast.error("Bạn chưa chọn đến kho!")
        this.$refs.cboDenKho.focusIn()
        return false
      }
      if (xuat_hoantra){
        if(this.params.p_DenKho.toString()){
          this.$toast.error("Xuất hoàn trả vật tư bạn không được chọn kho!")
          this.$refs.cboDenKho.focusIn()
          this.params.p_DenKho = ""
          return false
        }
      }

      if(!xuat_hoantra && !this.dc_ct_noibo && !this.dc_ct_ldscdp && this.params.p_TuKho.toString() == this.params.p_DenKho.toString()){
        this.$toast.error("Kho từ và kho đến không thể giống nhau!")
        this.$refs.cboTuKho.focusIn()
        return false
      }
      if (this.dc_ct_noibo || this.dc_ct_ldscdp || this.bqlda_nguonvon_dautu || this.bqlda_nguonvon_khac){
        if ((this.dc_ct_noibo || this.dc_ct_ldscdp) && this.params.p_TuKho.toString() != this.params.p_DenKho.toString()){
          this.$toast.error("Kho từ và kho đến phải giống nhau!")
          this.$refs.cboTuKho.focusIn()
          return false
        }
        let dt = this.cboTuKho
        if(dt.filter(a=>a.KHO_ID == this.params.p_TuKho.toString() && a.LOAIKHO_ID.toString() == "14").length == 0){
          this.$toast.error("Chỉ áp dụng mục đích này cho loại kho tổ trưởng")
          return false
        }
        if(this.bqlda_nguonvon_dautu || this.bqlda_nguonvon_khac){
          var ton_khooo = await this.KiemTraKhoDenCoTonKho({khoId: this.params.p_DenKho})
          if (!ton_khooo.length){
            this.$toast.error("Kho đến phải là kho không có tồn !")
            return false
          }
        }

        if (this.dc_ct_noibo || this.bqlda_nguonvon_dautu || this.bqlda_nguonvon_khac){
          if (!this.params.p_CongTrinh.toString()){
            this.$toast.error("Chưa chọn công trình")
            return false
          }
        }
        if (this.dc_ct_ldscdp){
          if(this.params.p_CongTrinh.toString()){
            this.$toast.error("Bạn không được phép chọn công trình")
            return false
          }
        }
      }

      if (this.dc_ct_ttvt){
        if(this.params.p_CongTrinh.toString()){
          this.$toast.error("Với chứng từ chuyển công trình cho TTVT bạn không thể chọn công trình")
          return false
        }
      }
      if (!await this.KiemTraHanChotCT()){
        return false
      }

      if(this.$auth.getMaTinh() == 'NET'){
        if(this.params.p_LoaiChungTu.toString() == "99"){
          if (this.params.p_txtNoiDung.toString()){
            this.$toast.error("Nội dung chứng từ không được để trống !")
            this.$refs.txtNoiDung.focus()
            return false
          }
        }
      }

      if (this.params.p_txtNoiDung.length > 500){
        this.$toast.error("Nội dung nhập quá dài (<500 kí tự) !")
        return false
      }
      if (tag != "GHILAI_PDA" && tag != "BBBG"){
        if (!this.$refs.gridViewVatTu.getSelectedRecords().length){
          this.$toast.error("Bạn cần phải chọn danh sách vật tư cho chứng từ!")
          return false
        }
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
      if (this.params.p_LoaiChungTu.toString() == "2"){
        if (await this.get_mamd(this.params.p_KieuPhieu) == "10"){
          var s = await this.getTonKhoTheoID({khoId: this.params.p_DenKho})
          if (s.toString() == "1" && !this.bqlda_nguonvon_khac && !this.bqlda_nguonvon_dautu){
            this.$toast.error("Bạn phải chọn kho đến không có tồn kho!")
            return false
          }
        }
      }
      return true
    },
    // Kiem tra nếu là nhập lẻ điều chỉnh - ma_md: N.DCDG thì ko cho chỉnh sửa, chỉ xóa hoặc trả phiếu
    async KiemTra_NhapLeDieuChinh(traphieu){
      if (traphieu == false){
        if (this.params.p_LoaiChungTu.toString() == "99"){
          if (this.Enabled.tsbtnNhapMoi){
            var ketqua = await this.KiemTraChungTuDieuChinh({
              "chungTuId": this.vchungtu_id,
              "kieu": 1,
              "tuNgay": this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY'),
              "denNgay": this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY')
            })
            let res = ketqua.toString()
            if (res.toString() != "1" && res != "0" && res != "2" && res != "3"){
              this.$toast.error(s)
              return false
            }
            if (res == "2"){
              this.$toast.error("Chứng từ nhập điều chỉnh đơn giá đã hoàn thiện! Bạn không thế chỉnh sửa ! ")
              return false
            }
          }
        }
      }
      return true
    },
    // Xuat hoan tra - ttph = 4  && sophieugop not null => ko cho chinh sua
    async KiemTra_XuatHoanTra(enable_lk_mk){
      let loaict = parseInt(this.gridviewChungTu_FocusedRow.LOAICT_ID)
      let ttphid = parseInt(this.gridviewChungTu_FocusedRow.TTPH_ID)
      let mucdichid = parseInt(this.gridviewChungTu_FocusedRow.MUCDICH_ID)
      var md = await this.getThongTinChungTuTheoPhieuGop({soPhieuGop: this.gridviewChungTu_FocusedRow.SOPHIEUGOP })
      var ko_vt = await this.getThongTinChungTuTheoCTCha({chungTuChaId: this.vchungtu_id})

      if(this.Enabled.tsbtnNhapMoi){
        if((loaict == 2 || loaict == 3) && (mucdichid == 234 || mucdichid == 278) && ko_vt.length){
          let s = ""
          for(var item of ko_vt){
            if (item["LOAICT_ID"].toString() == "2" || item["LOAICT_ID"].toString() == "3"){
              s += ",[" + item["SOPHIEU"] + "]"
            }
          }
          this.$toast.error("Chứng từ xuất hoàn trả đã chia thành phiếu xuất: " + s.substring(1) + " ! Bạn không thể chỉnh sửa/xóa phiếu này!")
          return false
        }
        if (this.ttph_id == 4 && (loaict == 2 || loaict == 3) && md.length){
          this.$toast.error("Chứng từ xuất hoàn trả đi kèm với phiếu nhập " + md[0]["SOPHIEU"].toString() + "! Bạn không thể chỉnh sửa/xóa phiếu náy! ")
          return false
        }
        if (loaict == 1 && md.length && !enable_lk_mk){
          this.$toast.error("Chứng từ nhập hoàn trả đi kèm với chứng từ xuất hoàn trả! Bạn không thể thêm VT/ chỉnh sửa chứng từ!")
          return false
        }
      }
      return true
    },
    // Kiểm tra nhập đủ loại kho - mã kho kế toán
    async KiemTra_LoaiKho_MaKho(){
      if(!this.params.p_KieuPhieu.toString()){
        this.$toast.error("Bạn phải chọn kiểu phiếu! ")
        return false
      }
      if(this.params.p_LoaiChungTu.toString() == "2" || this.params.p_LoaiChungTu.toString() == "3"){
        return true
      }
      var lkmk = this.gridVatTu
      if (!lkmk.length){ return false }
      let loaimakho = []
      lkmk.forEach(a=>loaimakho.push({
        LOAIKHO: a.LOAIKHO,
        MAKHOTD: a.MAKHOTD,
        CTCT_ID: a.CTCT_ID,
        CHATLUONG_ID: a.CHATLUONG_ID,
        DONGTBI_ID: a.DONGTBI_ID,
        HETHONG_ID: a.HETHONG_ID,
      }))
      let json = JSON.stringify(loaimakho)
      var kq = await this.KiemTraLoaiKhoMaKho({
        "dataDs": json,
        "kieu": 0
      })
      if (kq.toString() == "loaikho"){
          this.$toast.error("Không thể ghi lại. Bạn phải chọn hết loại kho ở danh sách vật tư trước!");
          return false;
      }
      if (kq.toString() == "makhotd"){
          this.$toast.error("Không thể ghi lại. Bạn phải chọn hết mã kho ở danh sách vật tư trước!");
          return false;
      }
      if (kq.toString() == "chatluong_id"){
          this.$toast.error("Không thể ghi lại. Bạn phải chọn hết chất lượng ở danh sách vật tư trước!");
          return false;
      }
      if (kq.toString() == "dongtbi_id"){
          this.$toast.error("Không thể ghi lại. Bạn phải chọn hết dòng thiết bị ở danh sách vật tư trước!");
          return false;
      }
      if (kq.toString() == "hethong_id"){
          this.$toast.error("Không thể ghi lại. Bạn phải chọn hết hệ thống ở danh sách vật tư trước!");
          return false;
      }
      return true
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
    async KiemTraHanChotCT2(ngay_ct,ngay){
      let vkho_nhan_id = 0
      let vkho_giao_id = 0
      if (this.params.p_TuKho.toString()){
        vkho_giao_id = parseInt(this.params.p_TuKho)
      }
      if (this.params.p_DenKho.toString()){
        vkho_nhan_id = parseInt(this.params.p_DenKho)
      }
      if (!ngay){
        var yo = await this.KiemTraHanChungTuV2({
          "chungTuId": this.vchungtu_id,
          "khoGiaoId": vkho_giao_id,
          "khoNhanId": vkho_nhan_id,
          "ngayCt": ""// ko cần
        })
        if (yo.toString() != "1"){
          this.$toast.error("Đã quá hạn sửa chứng từ!")
          return false
        }
      }
      else{
        let s = await this.KiemTraHanChungTu({
          "chungTuId": 0,
          "khoGiaoId": vkho_giao_id,
          "khoNhanId": vkho_nhan_id,
          "ngayCt": ngay_ct
        })
        if (s.toString() != "1"){
          this.$toast.error("Đã quá hạn sửa chứng từ!")
          return false
        }
      }
      return true
    },
    async KiemTraChiTiet_VT(vds){
      // try {
        var kt = await this.KiemTraChiTietVatTu({
          "dsVatTu": vds,
          "chungTuId": this.vchungtu_id
        })
        if (kt.toString() != "1"){
          this.$toast.error(kt.toString())
          return false
        }
        return true
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi kiểm tra chi tiết vật tư ")
      //   return false
      // }
    },
    async tsbtnThemVT_Click(){
      if (!this.params.p_Ma_PDA.toString()){
        this.$toast.error("Bạn không được chọn vật tư với thông tin từ dự án/ số phiếu")
        return
      }
      if(!this.params.p_MucDich.toString()){
        this.$toast.error("Chưa chọn mục đích !")
        return 
      }
      if(! this.Enabled.tsbtnNhapMoi){
        var d = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
        if (d.length){
          this.vma_md = d[0].toString()
        }
      }
      if (this.vma_md == this.MUCDICH_VT.MUASAM){
        this.$toast.error("Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép chọn vật tư")
        return
      }
      if (this.vma_md == this.MUCDICH_VT.CHUYEN_CP){
        this.$toast.error("Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép chọn vật tư")
        return
      }
      if (! await this.kiemtra_thaydoi_ctct()){
        return
      }
      if (! await this.KiemTra_XuatHoanTra(false)){
        return
      }
      if (this.kieugoi_id == 1){
        let kieu = -1
        if(this.chonvattu_lohang){
          if(this.vma_md == "DC_CT" && ( this.params.p_LoaiChungTu.toString() == "3" || this.params.p_LoaiChungTu.toString() == "2")){
            kieu = -1
          }
          else if (this.vma_md == "DC_CT_TTVT" && ( this.params.p_LoaiChungTu.toString() == "3" || this.params.p_LoaiChungTu.toString() == "2")){
            kieu = -1
          }
          else if ((this.vma_md == "DC_CTDP" && this.vma_md == "DC_LDSC") && ( this.params.p_LoaiChungTu.toString() == "3" || this.params.p_LoaiChungTu.toString() == "2")){
            kieu = -1
          }
          else if ((this.vma_md == "X.HTVT" || this.vma_md == "X.TDVT" || this.vma_md == "N.DCDG" || this.vma_md == "N.TTCL"
            || this.vma_md == "N-X.DCMSL" || this.vma_md == "SC_BH_DONGIA") && this.params.p_LoaiChungTu.toString() == "2"){
              kieu = -1
          }
          else{
            // var frm = new frmNhapText("Bạn hãy lựa chọn kiểu chọn vật tư", 1);
            // frm.rdioCKb.Properties.Items[0].Description = "Vật tư - lô hàng";
            // frm.rdioCKb.Properties.Items[1].Description = "Vật tư";
            // kieu = parseInt(frm.vkieu_luachon);
          }
        }
        if (kieu == -1 || kieu == 1){
          // CHọn vật tư lô hàng
          if (this.Visible.xTabTongHop){
            this.dtLuuVT = []
          }
          let mamd = ""
          let mucdich_id = parseInt(this.params.p_MucDich)
          var d = await this.getMaMucDichTheoID({mucDichId: mucdich_id})
          if (d.length){
            mamd = d[0].toString()
          }
          if (mamd == "CCDC.TL") {}
          else{
            this.vtdk = []
            var f = this.$refs.frmVatTuTrongKho
            f.ShowDialog()
            f.Visible.tsbtnXuatExcelTbi = false
            f.vkho_id = parseInt(this.params.p_TuKho)
            f.tt = 0
            try {
              var a = await this.getIDLoaiKho({khoId: parseInt(this.params.p_TuKho)})
              if (a.length && a[0].toString() && a[0].toString() == this.LOAI_KHO.KHO_TOTRUONG.toString()){
                f.tt = 1
              }
            } catch (error) {
              f.tt = 0
            }
            f.kieu = 1
            f.dtLuuVT = this.dtLuuVT
            this.ten_khotu = this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO
            f.vngay_ct = this.params.p_dtpNgayDK
            if (!this.Enabled.tsbtnNhapMoi){
              f.vchungtu_id = 0
            }
            else{
              f.vchungtu_id = this.vchungtu_id
            }
            f.vkho_den_id = 0
            if(this.params.p_DenKho.toString()){
              f.vkho_den_id = parseInt(this.params.p_DenKho)
            }
            // this.XacNhan_frmVatTuTrongKho
          }
        }
        else{
          // Chỉ chọn Vật tư
          var f = this.$refs.frmVatTuTrongKho_V2
          f.ShowDialog()
          f.vkho_id = parseInt(this.params.p_TuKho)
          f.vngay_ct = this.params.p_dtpNgayDK
          if(!this.Visible.xTabTongHop){
            this.dtLuuVT = []
          }
          f.dtLuuVT = this.dtLuuVT
          if(!this.Enabled.tsbtnNhapMoi){
            f.vchungtu_id = 0
          }
          else{
            f.vchungtu_id = this.vchungtu_id
          }
          // XacNhan_frmVatTuTrongKho_V2()
        }
      }
      else{
        var f = this.$refs.frmDSVatTu()
        f.ShowDialog()
        f.thamso_sinhlohang = true
        let mucdich_id = this.params.p_MucDich
        let zvma_md = ""
        var d = await this.getMaMucDichTheoID({mucDichId: mucdich_id})
        if (d.length){
          zvma_md = d[0].toString()
        }
        f.ma_md = zvma_md
        f.kieu = 1
        f.dtLuuVT = this.dtLuuVT
        if (this.dtLuuVT.length && this.dtLuuVT[0].hasOwnProperty('MATHETS')){
          f.quyen = true
        }
        // XacNhan_frmDSVatTu()
      }

    },
    async XacNhan_frmVatTuTrongKho(){
      let f = this.$refs.frmVatTuTrongKho
      if (f.XacNhan){
        this.Visible.xTabTongHop = false
        this.ds_Gan = []
        if (!this.Enabled.tsbtnNhapMoi){
          this.gridVTGroup = []
        }
        this.dtLuuVT = f.dt
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
          r.KHO_ID = item.KHO_ID
          r.MAY_CN = 'quy-test'//ttnd.may_cn
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
          r.CHATLUONG_ID = item.CHATLUONG_ID_TEMP.toString() || ""
          r.DONGTBI_ID = item.DONGTBI_ID_TEMP.toString() || ""
          r.HETHONG_ID = item.HETHONG_ID_TEMP.toString() || ""
          if (f.dt.length && f.dt[0].hasOwnProperty('MA_DUAN')){
            if (item.MA_DUAN.toString()){
              da = item["MA_DUAN"].toString().split('-') 
              r.DUAN = da[0].trimEnd()
            }
            this.dtThemVT.push(r)
          }
        }
        this.gridVatTu = this.dtThemVT
        this.khotu_id = f.vkho_id
        this.Enabled.gridViewVatTu.SOLUONG = true
        this.kieunhap = 2
        if (f.coDS){
          this.vtdk = f.ds_dangky
        }
      }
      
    },
    async XacNhan_frmVatTuTrongKho_V2(){
      var f = this.$refs.frmVatTuTrongKho_V2
      if (f.xacnhan){
        this.Visible.xTabTongHop = true
        // xtraTabControl1.SelectedTabPage = xTabTongHop;
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
          r.MAY_CN = 'quy-test'//ttnd.may_cn
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
        var d = await this.getMaMucDichTheoID({mucDichId: mucdich_id})
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
        else{
          lohang = seq_LH
        }

        for(var item of f.dt){
          let r = {}
          let lohang2 = ""
          let lohang_temp = ""
          var q = f.dt.filter(a=>a.VATTU_ID.toString() == item["VATTU_ID"].toString() && (a.LOHANG == item["LOHANG"] || !a.LOHANG)).length
          if (q > 1){
            lohang2 = await this.getKeys({
                "keyName" : "LOHANG",
                "numBlockSize" : 1,
                "numRetry":  10
            })
          }
          lohang_temp = lohang
          if (lohang2){
            lohang_temp = lohang2
          }
          if  (item["LOHANG"].toString().trim() && zvma_md != "N.DK" && zvma_md != "N.KDV"){
            r.LOHANG = await this.tao_lohang(item["LOHANG"].toString(),lohang_temp)
          }
          else{
            r.LOHANG = lohang_temp
          }
          r.LOHANG = 0
          r.CTCT_ID = 0
          r.MA_VT = item.MA_VT.toString()
          r.VATTU_ID = parseInt(item.VATTU_ID.toString())
          r.TEN_VT = item.TEN_VT.toString()
          r.DVI_TINH = item.DVI_TINH.toString()
          r.SOLUONG= parseInt(item.SOLUONG)
          r.DONGIA = parseInt(item.DONGIA)
          r.TIEN = parseInt(item.TIEN)
          
          r.LOAI_TBI = item.LOAI_TBI.toString()
          r.LOAITBI_ID = item.LOAITBI_ID
          r.GHICHU = item.GHICHU
          r.TY_LE = ""
          r.CHATLUONG = item.CHATLUONG.toString()
          r.NGUON_GOC = item.NGUON_GOC.toString()
          r.CHATLUONG_ID = item.CHATLUONG_ID
          r.HETHONG_ID = item.HETHONG_ID
          r.DONGTBI_ID = item.DONGTBI_ID
          r.TINHTRANG = ""
          if (item.HAN_BH){
            r.HAN_BH = item.HAN_BH//dd/MM/yyyy
          }
          if (this.dtLuuVT.length && this.dtLuuVT[0].hasOwnProperty('MATHUNG') && this.dtLuuVT[0].hasOwnProperty('MAKHOTD')
          && this.dtLuuVT[0].hasOwnProperty('LOAIKHO')){
            r.MATHUNG = item.MATHUNG.toString()
            r.MAKHOTD = item.MAKHOTD.toString()
            r.LOAIKHO = item.LOAIKHO.toString()
          } 
          if (this.dtLuuVT.length && this.dtLuuVT[0].hasOwnProperty('MATHETS')){
            r.MATHETS = item.MATHETS.toString()
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
      if (!this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENLAYVT_DH)){
        this.$toast.error("Bạn không có quyền lấy vật tư từ đơn hàng")
        return
      }
      var f = this.$refs.frmDonHangTT
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
          r.CTCT_ID = 0
          r.CTDH_ID = parseInt(item.CTDH_ID)
          r.LOHANG = lohang
          r.VATTU_ID = parseInt(item.VATTU_ID.toString())
          r.MA_VT = item.MA_VT.toString()
          r.TEN_VT = item.TEN_VT.toString()
          r.DVI_TINH = item.DVI_TINH.toString()
          r.SOLUONG= parseInt(item.SL_XUAT)
          r.DONGIA = parseInt(item.DONGIA)
          r.TIEN = Math.round(parseInt(item.SL_XUAT)*parseInt(item.DONGIA))
          r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
          r.LOAI_TBI = item.LOAI_TBI.toString()
          r.LOAITBI_ID = item.LOAITBI_ID
          r.LOAIKHO = ""
          r.MAKHOTD = ""
          r.TY_LE = ""
          r.MATHUNG = ""
          r.MATHETS = ""
          r.CHATLUONG_ID = ""
          r.DONGTBI_ID = ""
          r.HETHONG_ID = ""
          this.dtDonHang.push(r)
        }
        this.gridVatTu = this.dtDonHang
        this.params.p_TuKho = f.kho_id
        this.kieunhap = 1; //Nhap tu don hang
        this.Enabled.gridViewVatTu.SOLUONG = false
        this.ngay_dh = f.ngay_nhap
        this.params.p_dtpNgayDK = new Date()
        this.params.p_txtNoiDung = ""
      }
    },
    async btnXoaVT_Click(){
      // DHSX đang comment
    },
    async btnXoaVT_ButtonClick(){
      await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa vật tư của chứng từ này!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                let gridViewVatTu_FocusedRow = this.$refs.gridViewVatTu.getSelectedIndexs()
                this.$refs.gridViewVatTu.deleteRecord(gridViewVatTu_FocusedRow)
                let id = gridViewVatTu_FocusedRow.CTCT_ID
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
      let dt = []
      if (this.vchungtu_id > 0 && this.dtVatTu.length){
        let a = this.dtVatTu.filter(p=>p.LOAITBI_ID.toString() == "2")
        for (var item in a){
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
        if (this.nghiepvu_id_chungtu.toString() && this.nghiepvu_id_chungtu.toString() == "280" && this.ma_pda_chungtu.toString()){
          this.$toast.error("Chứng từ tạo từ dự án IMS không được sửa")
          return 
        }
        if (soph.startsWith("QT")){
            this.$toast.error("Chứng từ quyết toán không thể sửa!")
            return
        }
        if(await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id}).includes("ko")){
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
        this.$root.showLoading(true)
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
            
            
            await this.CapNhatExcelTbi_v2(JSON.parse(JSON.stringify(result))) 
            if (this.chonvattu_lohang && this.Visible.xTabTongHop){
              await this.Load_ds_vattu()
              await this.NAP_DS_VT_GROUP()
            }
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
          
        }
      } catch (error)  {
        this.$toast.error("Có lỗi xảy ra: " + error)
        return
      } finally {
        this.$root.showLoading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    async CapNhatExcelTbi_v2(dt){
      this.$root.showLoading(true)
      if (this.qr_code){
        let row_count = ""
        let dem = 0
        for (let i =0;i < dt.length;i++){
          let seri_file = dt[i].SERIAL.toString().trim()
          var seri_generate = seri_file.split('|')
          if (seri_generate.length > 1 && seri_generate.length != 3){
            row_count += ", " + (i + 2)
            dem++
          }
        }
        if (dem > 0){
          this.$toast.error("Có " + dem + " dòng dữ liệu định dạng QR không đúng:\nDòng " + row_count.substring(2))
          return
        }

        //Sau khi kiểm tra định dạng QR Code xong
        for(var j of dt){
          let seri_file = j["SERIAL"].toString().trim()
          let seri_generate = seri_file.Split('|')
          if (seri_generate.length > 1 && seri_generate.length == 3){
            j["SERIAL"] = seri_generate[2]
          }
        }  
      }

      for (var item of tb){
        await this.CapNhatSerialV2({
          "loHang": item.LOHANG,
          "maVatTu": item.MA_VT,
          "tenVatTu": item.TEN_VT,
          "donViTinh": item.DVI_TINH,
          "maThietBi": item.MA_TBI,
          "serial": item.SERIAL,
          "soLuong": 1,
          "ghiChu": item.GHICHU,
          //"serialGp": "abc-12345", ko thấy DHSX có thông số này
          //"mac": "1234-3423-123123",
          "maThung": item.MATHUNG
        }) 
      }
       
      let dt1 = await this.CapNhatSerialV2_1({
        "chungTuId": this.vchungtu_id,
        "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
        "mayCn": this.$auth.getUserName(),//ttnd.may_cn
        "ipCn": "10.59.90.123"//ttnd.ip
      })
      if (dt1.length){
        this.$toast.error("Có " + dt1.length + " bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!")
        await this.SaveXlsAndOpenFile("fileSaiDuLieu.xlsx", "sheet1", dt1)
      }
      else{
        await this.Load_ds_thietbi()
      }
    },
    async SaveXlsAndOpenFile(fileName,sheetName,datta){
      let data1 = XLSX.utils.json_to_sheet(datta);
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
            "mayCn": "quy-test",//ttnd.may_cn
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
        this.$root.showLoading(true)
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
        this.$root.showLoading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    async btnXoaHetVT_Click(){
      if(!this.gridVatTu.length){
        this.$toast.error("Không có dữ liệu vật tư để xóa")
        return
      }
      if (this.Enabled.tsbtnNhapMoi){
        if (this.nghiepvu_id_chungtu != "" && this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != ""){
          this.$toast.error("Chứng từ tạo từ dự án IMS không được sửa")
          return
        }

        if (this.Visible.xTabTongHop && this.chonvattu_lohang){
          this.$toast.error("Với chứng từ chọn số lượng bạn không được phép xoá")
          return
        }
        if (! await this.KiemTraNguoiCN()){
          this.$toast.error("Bạn không được sửa chứng từ của người khác!")
          this.$refs.cboLoaiChungTu.focusIn()
          return
        }
        if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id,"GHILAI")){ return }
        if (! await this.KiemTraHanChotCT()){ return }
        if (! await this.KiemTra_XuatHoanTra(false)){ return }
        if (! await this.KiemTra_NhapLeDieuChinh(false)) {return }
      }

      if (this.Enabled.tsbtnNhapMoi){
        if(this.kiemtra_quyen){
          if (! await this.kiemtra_thaydoi_ctct()){ return }
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
                    // gridViewVatTu.DeleteRow(i);
                  // this.gridViewVatTu = []
                  if(!this.gridViewVatTu.length){
                    this.ReadOnly.cboTuKho = false
                  }
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
                    "mayCn": "quy-test",//ttnd.may_cn
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
                // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", cttemp_id)
              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async KIEMTRA_THAYDOI(vct_id,tag){
      // try {
          var xttph = await this.getThongTinTrangThaiPhieu({chungTuId: vct_id})
          if (!xttph.length){
            this.$toast.error("Không lấy được thông tin trạng thái phiếu")
            return false
          }
          if (xttph.length){
            if(this.ttph_id.toString() == xttph[0].ttphId.toString()){
              this.$toast.error("Thông tin trạng thái phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin")
              return false
            }
            if (tag == "GHILAI" || tag == "UPSL"){
              if(xttph[0].maMd.toString() == this.MUCDICH_VT.MUASAM.toString()){
                this.$toast.error("Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép sửa")
                return false
              }
              if(xttph[0].maMd.toString() == this.MUCDICH_VT.CHUYEN_CP.toString() && tag == "GHILAI"){
                this.$toast.error("Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép sửa")
                return false
              }
            }
            if (tag == "GHILAI_NUTGHILAI"){
              if(xttph[0].maMd.toString() == this.MUCDICH_VT.MUASAM.toString()){
                this.$toast.error("Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép sửa")
                return false
              }
            }
            return true
          }
          return true

      // } catch (error) {
      //   this.$toast.error("Có lỗi khi lấy thông tin trạng thái phiếu")
      //   return false
      // }
    },
    // Nếu nhập lẻ + kiểu là điều chỉnh ==> tự động hoàn thiện phiếu xuất (là phiếu cha) trước, sau đó hoàn thiện phiếu nhập
    async KiemTraHanChotCT_V2(){
      let vkho_nhan_id = 0
      let vkho_giao_id = 0
      let ngay_ct = this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY')

      if(this.params.p_TuKho.toString()){
        vkho_giao_id = parseInt(this.params.p_TuKho)
      }
      if(this.params.p_DenKho.toString()){
        vkho_nhan_id = parseInt(this.params.p_DenKho)
      }

      let s = await this.KiemTraHanChungTu({
        "chungTuId": this.vchungtu_id,
        "khoGiaoId": vkho_giao_id,
        "khoNhanId": vkho_nhan_id,
        "ngayCt": ngay_ct
      })
      if (s != "1"){
        this.$toast.error("Đã quá hạn sửa chứng từ " + soph + " !")
        return false
      }
      return true
    },
    async KIEMTRA_QUYEN_XN_V2(kieu,kho_id){
      // try {
        var dt = await this.getThongTinQuyenTrenKho({nhanVienId: this.$auth.getNhanVienID(),khoId: kho_id})
        if (!dt.length){
          this.$toast.error("Không lấy được thông tin quyền xác nhận trên kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO + " của chứng từ " + this.soph)
          return false
        }
        if (kieu == 1){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận ngày gửi trên kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO + " của chứng từ " + this.soph)
            return false
          }
        }
        if (kieu == 2){
          if(dt[0].quyenKn.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận ngày nhận trên kho " + this.$refs.cboDenKho.getDataByValue(this.params.p_DenKho).TEN_KHO + " của chứng từ " + this.soph)
            return false
          }
        }

        if (kieu == 3){
          if(dt[0].quyenKn.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận trên kho " + this.$refs.cboDenKho.getDataByValue(this.params.p_DenKho).TEN_KHO + " của chứng từ " + this.soph + ".Bạn không thể hoàn công.")
            return false
          }
        }

        if (kieu == 4){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền tạo thiết bị trên kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO + " của chứng từ " + this.soph + ".Bạn không thể hoàn công.")
            return false
          }
        }
        if (kieu == 5){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận trên kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO + " của chứng từ " + this.soph + ".Bạn không thể xóa/sửa chứng từ.")
            return false
          }
        }
        return true
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi kiểm tra quyền xác nhận chứng từ " + soph)
      //   return false
      // }
    },
    async KIEMTRA_THAYDOI_V2(vct_id,cap){
      // try {
        var xttph = await this.getThongTinTrangThaiPhieu({chungTuId: vct_id})
        if(!xttph.length){
          this.$toast.error("Không lấy được thông tin trạng thái phiếu" + " - chúng từ " + this.soph)
          return false
        }
        if(xttph.length){
          if(this.ttph_id.toString() != xttph[0].ttphId.toString()){
            this.$toast.error("Thông tin trạng thái phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin" + " - chúng từ " + this.soph)
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
    async hoanthienv2(){
      var ds_dt = await this.getThongTinPhieuTheoCTCha({chungTuId: this.vchungtu_id})
      if(!ds_dt.length){
        this.$toast.error("Không có dữ liệu ngày nhận không thể cập nhật ngày gửi của kho" + " - chứng từ " + this.soph)
        return 
      }
      else{
        if(ds_dt[0].ngayNhanTt.toString()){
          this.$toast.error("Chưa có ngày nhận thực tế vật tư không thể hoàn thành" + " - chứng từ " + ds_dt[0]["SOPHIEU"].toString())
          return false
        }
      }
      let chungtu_cha = parseInt(ds_dt[0][0])
      let s = await this.KiemTraHoanThienCT({chungTuId: chungtu_cha})
      if (s == "1"){
        await this.HoanThienChungTu({
          "chungTuId" : chungtu_cha,
          "nhanVienId" : this.$auth.getNhanVienID(),
          "nguoiCN" : this.$auth.getUserName(),//ttnd.ma_nd
          "mayCN" : "quy-test",//ttnd.may_cn
          "ipCN" : "10.59.90.123"//ttnd.ip
        })
      }
    },
    async tsbtnHoanThanh_Click(){
      // Nếu là N.DCDG thì hoàn thiện chứng từ cha trước
      var ketqua = await this.KiemTraChungTuDieuChinh({
        "chungTuId": this.vchungtu_id,
        "kieu": 0,
        "tuNgay": this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY'),
        "denNgay": this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY')
      })
      var  res = ketqua.toString()
      if (res != "1" && res != "0" && res != "2"){
        this.$toast.error(res)
        return
      }
      if (res == "0"){
        var ds_dt = await this.getThongTinPhieuTheoCTCha({chungTuId: this.vchungtu_id})
        if (!ds_dt.length){
          this.$toast.error("Không tìm thấy chứng từ cha của chứng từ [" + this.soph + "]")
          return
        }
        else{
          if(ds_dt[0].ngayNhanTt.toString()){
            this.$toast.error("Chưa có ngày nhận thực tế vật tư không thể hoàn thành" + " - chứng từ [" + ds_dt[0]["SOPHIEU"].toString() + "]")
            return 
          }
        }
        let chungtu_cha = parseInt(ds_dt[0][0])
        let sss = await this.KiemTraHoanThienCT({chungTuId:chungtu_cha})
        if (sss == "1"){
          await this.HoanThienChungTu({
            "chungTuId" : chungtu_cha,
            "nhanVienId" : this.$auth.getNhanVienID(),
            "nguoiCN" : this.$auth.getUserName(),//ttnd.ma_nd
            "mayCN" : "quy-test",//ttnd.may_cn
            "ipCN" : "10.59.90.123"//ttnd.ip
          })
        }

      }

      if (! await this.KiemTraHanChotCT()){ return }

      if(!this.gridViewVatTu.length){
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

      if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id, "")){ return }

      var kiemtra_qtth = await this.KiemTraHoanThanhCT({chungTuId: this.vchungtu_id})
      if (kiemtra_qtth.toString() != "1"){
        this.$toast.error(kiemtra_qtth.toString())
        return
      }
      let s = await this.KiemTraHoanThienCT({chungTuId: this.vchungtu_id})
      if (s == "1"){
        if(this.chon_kieu_qlts){
          let error_api = ""
          var table = await this.KiemTraIDChiTietQLTS({chungTuId: this.vchungtu_id})
          if(table.length){ 
            // for (int i = 0; i < table_.Columns.Count; i++)
            // {
            //     table_.Columns[i].ColumnName = table_.Columns[i].ColumnName.ToLower();
            // }
            let json_api = JSON.stringify(table)
            let kq_api = await this.CapNhatThongTinQLTS({list: json_api})
            // http://10.70.122.49:6969/api/CAP_NHAT_TS
            if(kq_api.toUpperCase().includes('FALSE') || kq_api.toUpperCase().includes('DOCTYPE')){
              kq_api = kq_api.replace("\\\\", "\\").replace("\\\"", "\"")
              kq_api = kq_api.replace("\"{", "{").replace("}\"", "}")
              let myDeserializedClass = JSON.parse(kq_api)
              var notifi = myDeserializedClass.message
              this.$toast.error("Cập nhật lên QLTS không thành công: " + notifi)
              return
            }
          }
        }

        await this.HoanThienChungTu({
          "chungTuId" : this.vchungtu_id,
          "nhanVienId" : this.$auth.getNhanVienID(),
          "nguoiCN" : this.$auth.getUserName(),//ttnd.ma_nd
          "mayCN" : "quy-test",//ttnd.may_cn
          "ipCN" : "10.59.90.123"//ttnd.ip
          })
        this.$toast.error("Đã hoàn thiện chứng từ thành công!")
        if (this.params.p_ckbSendSMS){
          try {
            await this.GuiSMSChungTu({
              vtudong: 1,
              vchungtu_id: this.vchungtu_id,
              vmay_cn: 'quy-test',//ttnd.may_cn
              vnguoi_cn: this.$auth.getUserName(),//ttnd.ma_nd
              ip_cn:  '10.59.90.123'//ttnd.ip
            })
          } catch (error) {
            this.$toast.error("Có lỗi khi send smsm tự động")
          }
        }
        let vchungtu_tmp_id = vchungtu_id
        await this.Load_ds_chungtu()
        FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", vchungtu_tmp_id)


      }
      else{
        this.$toast.error(s)
        var log = await this.CapNhatLog({
          "vlog":'Ko tu dong hoan thien chung tu khi co ngay nhan',
          "chungTuId" : this.vchungtu_id,
          "nguoiCN" : this.$auth.getUserName(),//ttnd.ma_nd
          "mayCN" : "quy-test",//ttnd.may_cn
          "ipCN" : "10.59.90.123"//ttnd.ip
        })
      }
    },
    async simpleButton1_Click(){
      if(this.Enabled.tsbtnNhapMoi){
        if (this.nghiepvu_id_chungtu != "" && this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != ""){
          this.$toast.error("Chứng từ tạo từ dự án IMS không được sửa")
          return
        }
        if (! await this.KiemTra_XuatHoanTra(false)){ return }
        if (! await this.KiemTra_NhapLeDieuChinh(false)){ return }
        if (this.kiemtra_quyen){
          if (! await this.kiemtra_thaydoi_ctct()){
            return
          }
        }
      }

       await this.$bvModal.msgBoxConfirm("Bạn có chắc chán muốn xóa hết thiết bị của chứng từ này!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                if (! await this.KiemTraHanChotCT()){ return }
                if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id, "")){ return }
                if (this.kiemtra_quyen){
                  var ds_dt = await this.getThongTinNgayNhanCT({chungTuId: this.vchungtu_id})
                  if(!ds_dt.length){
                    this.$toast.error("Không có dữ liệu ngày nhận không thể xóa")
                    return
                  }
                  else{
                    if(ds_dt[0].ngayNhanTt.toString()){
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
                    "mayCn": "quyPc",//ttnd.may_cn
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
                await this.CHUANHOA_CT_TBI(0,0)
                let cttemp_id = vchungtu_id
                await this.Load_ds_chungtu()
                // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", cttemp_id)
                
              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async btnXuatExcelVT_Click(){
      await this.SaveXlsAndOpenFile("VatTu-" + this.params.p_txtSoPhieu.replace("/", "-") + ".xlsx", "sheet1", this.dtVatTu)
    },
    async simpleButton2_Click(){
      await this.SaveXlsAndOpenFile("ThietBi-" + this.params.p_txtSoPhieu.replace("/", "-") + ".xls", "sheet1", this.dtThietBi)
    },
    async tsbtnSinhSerial_Click(){
      if (!this.gridViewVatTu_FocusedRow){ return }
      var f = this.$refs.frmTaoSerial
      f.ShowDialog()
      f.vctct_id = vctct_id
    },
    cboDonViNhan_EditValueChanged(){

    },
    async Update_SoLanIn(){
      if (xacnhan_in_chungtu){
        if(!this.dsQuyen.includes(this.DS_QUYEN_VT.QUYET_XETDUYET)){ return }
        await this.$bvModal.msgBoxConfirm("Bạn có xác nhận đã in chứng từ không ???", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                await this.CapNhatSoLanInCT({chungTuId: this.vchungtu_id,maNd: this.$auth.getUserName()})//ttnd.ma_nd
                this.$toast.success("Xác nhận in thành công !!!")
                await this.Load_ds_chungtu()
                // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", vchungtu_id)
              }
          })
          .catch(err => {
              // An error occurred
          })

      } 
      else{
        var res = await this.CapNhatSoLanIn({chungTuId: this.vchungtu_id})
        if (res.toString() != "1"){
          this.$toast.error("Có lỗi khi cập nhật số lần xem báo cáo: " + res.toString())
        }
      }     
    },
    async tsbtnPhieuNhap_Click(){
      // var frmBC = new frmXemBaoCao("PNK", new[] { vchungtu_id.toString() });
      // frmBC.XemNgay();
      await Update_SoLanIn()
    },
    async tsbtnPhieuXuat_Click(){
      if(this.$auth.getPhanVungID().toString() == "54" || this.$auth.getMaTinh().toString() == "TGG"){
        // var frm = new frmNhapText("Nhập tên người nhận ");
        // if (frm.ShowDialog() == DialogResult.OK)
        // {
        //     string nv = "";
        //     nv = frm.Output;
        //     var frmBC = new frmXemBaoCao("PXK", new[] { vchungtu_id.toString(), nv });
        //     frmBC.XemNgay();
        // }
      }
      else{
        // var frmBC = new frmXemBaoCao("PXK", new[] { vchungtu_id.toString() });
        // frmBC.XemNgay();
        // await this.Update_SoLanIn();
      }
    },
    async txtQuetSerial_KeyPress(){
      if (this.Enabled.tsbtnNhapMoi){
        if (this.nghiepvu_id_chungtu != "" && this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != ""){
          this.$toast.error("Chứng từ tạo từ dự án IMS không được điều chỉnh")
          return 
        }
      }
      let seri = this.params.p_txtQuetSerial.trim().toUpperCase()
      var sl = seri.split('|')
      if (this.qr_code && sl.length > 1 && sl.length != 3){
        this.$toast.error("Dữ liệu định dạng QR không đúng")
        return
      }
      if (this.qr_code && sl.length > 1 && sl.length == 3){
        seri = seri.split('|')[2]
      }
      let vkho_id = parseInt(this.params.p_TuKho)
      // Kiểm tra seri có tồn tại trong hệ thống không.
      let dt = await this.KiemTraSerialTonTai({serial: seri})
      if(!dt.length){
        this.$toast.error("Serial không tồn tại trong hệ thống!")
        return
      }
      if(!parseInt(dt[0].slTon || 0) ){
        this.$toast.error("Serial đã được cấp cho thuê bao!")
        return
      }
      if(dt[0].khoId.toString() != vkho_id){
        this.$toast.error("Serial không nằm trong kho: " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO)
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
      try {
        await this.CapNhatChungTuThietBiV2({
          "ctctId": dt2[0].chiTietCtId,
          "thietBiId": dt[0].thietBiId || 0,
          "thietBiChaId": dt[0].thietBiChaId || 0,
          "serial": seri,
          "soLuong": 1,
          "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
          "mayCn": "quy-test",//ttnd.may_cn
          "ipCn": "10.59.90.123"//ttnd.ip
        })
      } catch (error) {
        this.$toast.error("Có lỗi khi cập nhật thiết bị")
      }
      await this.Load_ds_thietbi()
      if(this.Visible.xTabTongHop && this.chonvattu_lohang){
        await this.Load_ds_vattu()
      }
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
                  await this.XoaChungTuThietBiTheoTBI({ctctId: ct_id,thietBiId: id})
                }
              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async gridViewVatTu_CustomSummaryCalculate(){
      // làm sau
    },
    async KhoiTaoNoiDung(){
      if (this.Enabled.tsbtnNhapMoi){ return }
      let nd = ""
      if (this.kieunhap == 1){
        this.params.p_txtNoiDung = ""
      }
      else{
        // DHSX đang comment
        this.params.p_txtNoiDung = nd
      }
    },
    async KiemTraKho(){
      if (!this.Enabled.tsbtnNhapMoi && !this.params.p_TuKho.toString()){
        k_goc = parseInt(this.params.p_TuKho)
        if(this.$refs.gridViewVatTu.getDataRows().length){
          if(this.params.p_TuKho.toString() != this.khotu_id.toString()){
            await this.$bvModal.msgBoxConfirm(" Nếu bạn thay đổi sang kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO + " danh sách vật tư đã chọn sẽ được xóa", {
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
            await this.$bvModal.msgBoxConfirm("Nếu bạn thay đổi sang kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO + " danh sách vật tư đã chọn sẽ được xóa", {
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
    async btnXoatbI_Click(){
      // DHSX chưa có code
    },
    async btnXoatbI_MouseUp(){
      // DHSX chưa có code
    },
    async gridViewVatTu_SelectionChanged(){
      // DHSX chưa có code
    },
    // cần debugger thêm
    async gridViewVatTu_ShownEditor(row){
      if (row.column.field == 'colXoaVT'){
        if (this.copyct == false){
          if(this.Enabled.tsbtnNhapMoi){
            if (! await this.KiemTraNguoiCN()){
              this.$toast.error("Bạn không được sửa chứng từ của người khác!")
              this.$refs.cboLoaiChungTu.focusIn()
              return 
            }
            if(this.Visible.xTabTongHop && this.chonvattu_lohang){
              this.$toast.error("Với chứng từ chọn số lượng bạn không được phép xoá")
              return
            }
          }

          if(this.Enabled.tsbtnNhapMoi){
            if (this.nghiepvu_id_chungtu != "" && this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != ""){
              this.$toast.error("Chứng từ tạo từ dự án IMS không được sửa")
              return
            }
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
                      let r = this.gridViewVatTu_FocusedRow
                      this.$refs.gridViewVatTu.deleteRecord(r)
                      if (!this.$refs.gridViewVatTu.getDataRows().length && this.ReadOnly.cboTuKho) {
                        this.ReadOnly.cboTuKho = false
                      }
                      return
                    }
                    if (!this.dsQuyen.includes(this.DS_QUYEN_VT.CAPNHAT_CHUNGTU)){
                      this.$toast.error("Bạn không có quyền thực hiện chức năng này!")
                      return
                    }
                    if (this.kiemtra_quyen){
                      if (! await this.kiemtra_thaydoi_ctct()){return}
                      if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
                    }
                    if (! await this.KiemTraHanChotCT()){ return }
                    if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id, "GHILAI")){ return}
                    if (! await this.KiemTra_XuatHoanTra(false)){ return}
                    if (! await this.KiemTra_NhapLeDieuChinh(false)){  return }
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

                    let row = this.gridViewVatTu_FocusedRow
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
                    this.$refs.gridViewVatTu.deleteRecord(row)
                    if(id > 0){ 
                      await this.XoaChiTietChungTu({ctctId: id})
                      await this.Load_ds_vattu()
                      await this.Load_ds_thietbi()
                    }
                    // view.FocusedColumn = view.Columns[0];
                    if(this.Enabled.tsbtnNhapMoi){
                      let ctemp_id = vchungtu_id
                      await this.Load_ds_chungtu()
                      // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", ctemp_id)
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
          if (!await this.KiemTra_XuatHoanTra(false)){ return }
          this.$refs.gridViewVatTu.deleteRecord(row)
          // gridViewVatTu.FocusedColumn = gridViewVatTu.Columns["LOHANG"]
          this.$refs.gridViewVatTu.refreshColumns()
        }
      } 

      // Tach Cap
      if (row.column.field == 'TachCap' && this.Enabled.tsbtnNhapMoi){
        //Kiem tra de bat form
        if (this.dc_ct_noibo || this.dc_ct_ttvt || this.dc_ct_ldscdp){
          this.$toast.error("Điều chuyển nội bộ hoặc ttvt công trình không thể tách chọn cuộn cáp")
          return
        }
        if (!this.dsQuyen.includes(this.DS_QUYEN_VT.CAPNHAT_CHUNGTU) && !this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU)){
          this.$toast.error("Bạn không có quyền thực hiện chức năng này!")
          return
        }
        if (! await this.KiemTraHanChotCT()){ return}
        if (! await this.KIEMTRA_THAYDOI(vchungtu_id, "")){ return }
        if (this.Enabled.tsbtnNhapMoi){
          if (this.kiemtra_quyen){
            if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
          }
          if (this.nghiepvu_id_chungtu != "" && this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != ""){
            this.$toast.error("Chứng từ tạo từ dự án IMS không được sửa")
            return
          }
          if (soph.startsWith("QT")){
            this.$toast.error("Chứng từ quyết toán không thể sửa!")
            return
          }
          if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
            this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
            return false
          }
        }
        if(this.gridViewVatTu_FocusedRow.LOAITBI_ID.toString() != "3"){
          this.$toast.error("Chức năng này chỉ dành cho loại thiết bị là cuộn cáp")
          return
        }
        let s = await this.getKieuNhapCap({vatTuId: this.gridViewVatTu_FocusedRow.VATTU_ID.toString(), 
          khoId: this.params.p_TuKho.toString(),
          loHang: this.gridViewVatTu_FocusedRow.LOHANG })
        if (s == "1"){
          // Nhập cáp
          let vctct_id = parseInt(this.gridViewVatTu_FocusedRow.CTCT_ID)
          let d = await this.getDanhSachCap({ctctId: vctct_id})
          if (d.toString() != "0"){
            await this.$bvModal.msgBoxConfirm("Cáp này đã được tách. Bạn có muốn tiếp tục tách lại số lượng cuộn không?", {
              "okVariant": 'success',
              "okTitle": 'Đồng ý',
              "cancelTitle": 'Hủy',
              "centered": true,
              })
              .then(async(value) => {
                  if (!value){
                    return
                  }
              })
              .catch(err => {
                  // An error occurred
              })
          }
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
          frm.ShowDialog()
          frm.tong_sl = this.gridViewVatTu_FocusedRow.SOLUONG
          frm.ten_vt = this.gridViewVatTu_FocusedRow.MA_VT + " - " + this.gridViewVatTu_FocusedRow.TEN_VT
          // XacNhan_frmNhapCap()
        }
        else{
          // Chọn cáp
          var frm = this.$refs.frmGanSeri(1)
          frm.ShowDialog()
          frm.Text = "Chọn/Cắt cuộn cáp"
          frm.qr_code = this.qr_code
          frm.Text.txtTenKho = this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO
          frm.vkho_id = parseInt(this.params.p_TuKho)
          frm.vkho_nhan_id = parseInt(this.params.p_DenKho) || 0
          frm.vctct_id = parseInt(this.gridViewVatTu_FocusedRow.CTCT_ID)
          frm.soluong_tong = parseInt(this.gridViewVatTu_FocusedRow.SOLUONG)
          frm.vattu_id = parseInt(this.gridViewVatTu_FocusedRow.VATTU_ID)
          frm.lohang = this.gridViewVatTu_FocusedRow.LOHANG
          frm.soluong_ton = await this.getTongSoLuongTonTheoCTCT({ctctId:this.gridViewVatTu_FocusedRow.CTCT_ID })
          // XacNhan_frmGanSeri
        }
      }
    },
    async XacNhan_frmNhapCap(){
      var frm = this.$refs.frmNhapCap
      if (frm.xacnhan){
        kq = await this.TachCuonCap({
          "kieu" : 1,
          "khoId" : this.params.p_TuKho,
          "khoNhanId" : this.params.p_DenKho,
          "ctctId" : vctct_id,
          "nguoiCN" : this.$auth.getUserName(),//ttnd.ma_nd
          "mayCN" : "quy-test",//ttnd.may_cn
          "ipCN" : "10.59.90.123",//ttnd.ip
          "data" : frm.data
        })
        if (kq != "1"){
          this.$toast.error(kq)
        }
        if(this.Enabled.tsbtnNhapMoi){
          let ctemp_id = vchungtu_id
          await this.Load_ds_chungtu()
          // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", ctemp_id)
        }
        // xtraTabControl1.SelectedTabPage = xtraTabPage2;
        // xtraTabControl1.SelectedTabPage.Select();
      }
    },
    async XacNhan_frmGanSeri(){
       var frm = this.$refs.frmGanSeri()
       if (frm.xacnhan){
         if (this.Enabled.tsbtnNhapMoi){
            let ctemp_id = vchungtu_id
            await this.Load_ds_chungtu()
            //  FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", ctemp_id)
         }
        //  xtraTabControl1.SelectedTabPage = xtraTabPage2;
        //   xtraTabControl1.SelectedTabPage.Select();
       }
    },
    async  gridViewThietBi_ShownEditor(row){
      // Xoa ThietBi
      if (row.column.field == 'colXoaTbi'){
        if (!this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU)){
          this.$toast.error("Bạn không có quyền thực hiện chức năng này!")
          return
        }
        if (! await this.KiemTraHanChotCT()){ return }
        if (! await this.KIEMTRA_THAYDOI(vchungtu_id, "GHILAI")){ return }
        if (! await this.kiemtra_thaydoi_ctct()){ return }
        if (! await this.KiemTra_XuatHoanTra(false)) { return }
        if (! await this.KiemTra_NhapLeDieuChinh(false)){ return }
        if (this.Enabled.tsbtnNhapMoi){
          if (this.nghiepvu_id_chungtu != "" && this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != ""){
            this.$toast.error("Chứng từ tạo từ dự án IMS không được sửa")
            return
          }
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
            if(ds_dt[0].ngayNhanTt.toString()){
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
                  "mayCn": "quyPc",//ttnd.may_cn
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
                let row = this.gridViewThietBi_FocusedRow
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
                this.$refs.gridViewThietBi.deleteRecord(row)
                if (id > 0){
                  await this.XoaChungTuThietBiTheoTBI({ctctId: ct_id,thietBiId: id})
                  await this.XoaChiTietThietBi({thietBiId: id})
                  await this.CHUANHOA_CT_TBI(ct_id, id)
                  await this.Load_ds_thietbi()
                  
                }
                // view.FocusedColumn = view.Columns[0]
                let ctemp_id = vchungtu_id
                await this.Load_ds_chungtu()
                // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", ctemp_id)
              }
          })
          .catch(err => {
              // An error occurred
          })

      }
    },
    async CHUANHOA_CT_TBI(ctct_id,thietbi_id){
      if (this.ctct_id > 0 && this.thietbi_id > 0){
        if(this.Visible.xTabTongHop && this.chonvattu_lohang){
          var kiemtra = await this.KiemTraTonTaiChiTietVatTu({chungTuId: this.vchungtu_id})
          if(kiemtra.toString() != 0){
            var thucthi = await this.CapNhatSLChungTuVatTu({
              chungTuId: this.vchungtu_id,
              ds: "0",
              ctct_id: ctct_id,
              kieu: 0
            })
          }
        }
      }
      else{
        if(this.Visible.xTabTongHop && this.chonvattu_lohang){
          var kiemtra = await this.KiemTraTonTaiChiTietVatTu({chungTuId: this.vchungtu_id})
          if(kiemtra.toString() != 0){
            var thucthi = await this.CapNhatSLChungTuVatTu({
              chungTuId: this.vchungtu_id,
              ds: "0",
              ctct_id: 0,
              kieu: 0
            })
          }
        }
      }
    },
    async tsbtnCopyCT_Click(){
      // try {
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

      // } catch (error) {
      //   this.$toast.error("Có lỗi:")
      // }
    },
    async load_copy_ctv(dt){
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
      if(!this.params.p_LoaiChungTu.toString()){ return }
      if(this.params.p_LoaiChungTu.toString() == "99"){
        this.vloai = 2
        this.Text.label7 = "NHẬP VẬT TƯ MUA LẺ"
        this.kieugoi_id = 2
        let dt = await this.getDSKhoVatTuLe({nhanVienId: this.$auth.getNhanVienID(),donViId: this.$auth.getDonViID()})
        this.cboTuKho = dt
        this.cboKieuPhieu = await this.getDSKieuPhieu({loaiCtId: 1})
        this.Enabled.gridViewVatTu.MATHUNG = true
        // this.ReadOnly.gridViewVatTu.MATHUNG = false
      }
      else{
        this.vloai = 1
        this.kieugoi_id = 1
        this.cboTuKho = await this.getDanhMucKho({nhanVienId: this.$auth.getNhanVienID()})
        this.cboKieuPhieu = await this.getDSKieuPhieu({loaiCtId: this.params.p_LoaiChungTu})
        this.Enabled.gridViewVatTu.MATHUNG = false
        // this.ReadOnly.gridViewVatTu.MATHUNG = true
      }
      await this.Loc_Kho()
      this.Enabled.tstbtnDonHang = false
      this.Enabled.tsbtnPhieuNhap = false
      this.Enabled.tsbtnPhieuXuat = false
      this.Enabled.tsbtnPhieuChuyen = false
      this.Enabled.tstPhieuTUVT = false
      this.Enabled.tsbtnBBGNNhap = false
      this.Enabled.tsbtnBBGNXuat = false
      this.Enabled.tsbtnInXuatDVTC = false
      switch (parseInt(this.params.p_LoaiChungTu)){
        case 1: 
          this.Enabled.tstbtnDonHang = true
          this.Enabled.tsbtnPhieuNhap = true
          this.Enabled.tsbtnBBGNNhap = true
          break;
        case 2:
          this.Enabled.tsbtnPhieuXuat = true
          this.Enabled.tstbtnDonHang = false
          this.Enabled.tsbtnBBGNXuat = true
          break;
        case 3:
          this.Enabled.tsbtnPhieuXuat = true
          this.Enabled.tstPhieuTUVT = true
          this.Enabled.tstbtnDonHang = false
          this.Enabled.tsbtnInXuatDVTC = true
          break;
        case 99:
          this.Enabled.tsbtnBBGNNhap = true
          this.Enabled.tsbtnPhieuNhap = true
          break;
        default:
          break;
      }
    },
    async tsbtnPhieuChuyen_Click(){
      if(this.$auth.getMaTinh == "TGG"){
        // var frm = new frmNhapText("Nhập tên người nhận ");
        // if (frm.ShowDialog() == DialogResult.OK)
        // {
        //     string nv = "";
        //     nv = frm.Output;
        //     var frmBC = new frmXemBaoCao("PCK", new[] { vchungtu_id.toString(), nv });
        //     frmBC.XemNgay();
        // }
      }
      else{
        // var frmBC = new frmXemBaoCao("PCK", new[] { vchungtu_id.toString() });
        // frmBC.XemNgay();
        // Update_SoLanIn();
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
      await this.LamMoi()
    },
    async LamMoi(){
      await this.Load_ds_chungtu()
    },
    async btnFilter_Click(){
      if (!this.grcVT.length){
        this.$toast.error("Không có vật tư để chọn")
        this.params.p_VatTu = ""
        return
      }
      else{
        if (this.dc_ct_noibo || this.dc_ct_ttvt || this.dc_ct_ldscdp){
          this.$toast.error("Điều chuyển nội bộ không thể chọn serial ở đây")
          return
        }
        if (! await this.KiemTra_XuatHoanTra(false)){ return }
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
          if (! await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id)){ return }
        }

        let vt = ""
        let lh = ""
        let dem = 0
        // for (let i = 0; i < grvVT.DataRowCount; i++){
        //   let dr = grvVT[i]
        //   if (dr["ISCHECK"].toString() == "1"){
        //     dem += 1;
        //     vt += dr["VATTU_ID"] + ",";
        //     lh += "'" + dr["LOHANG"].toString().replace("'", "''") + "'" + ",";
        //   }
        // }
        dem = this.$refs.grvVT.selectedValues.length
        vt = this.$refs.grvVT.selectedValues.join(",")
        lh = this.grcVT.filter(a=>this.$refs.grvVT.selectedValues.includes(a.VATTU_ID)).map(b=>b.LOHANG || "").join("','")
        if (dem > 0){
          // if (vt.length > 1){
          //   vt = vt.trimEnd(", ");//.ToCharArray()
          //   lh = lh.trimEnd(", ");//.ToArray()
          // }
          this.svattu_id = vt;
          this.slohang = lh;

          this.$refs.popup_frmGanSeri.show()
          var f = this.$refs.frmGanSeri
          f.vlohang = this.slohang;
          f.vvattu_id = this.svattu_id;
          f.vkho_id = this.t_kho_id;
          f.vchungtu_id = this.vchungtu_id;
          // XacNhan_frmGanSeri_V2
        }
      }
    },
    async XacNhan_frmGanSeri_V2(){
      await this.Load_ds_thietbi()
      if (this.Visible.xTabTongHop && this.chonvattu_lohang){
        await this.Load_ds_vattu()
        await this.NAP_DS_VT_GROUP()
      }
    },
    async ckbCTCT_CheckedChanged(){
      // grvVT.CloseEditor()
    },
    async toolStripButton1_Click(){
      // DHSX chua co action
    },
    async vbtnRefresh_MouseClick(){
      await this.Load_ds_chungtu()
    },
    async gridVatTu_DataSourceChanged(){
      if(!this.Visible.xTabTongHop){
        if (this.kieugoi_id != 2){
          this.ReadOnly.cboLoaiChungTu = this.gridViewVatTu.length != 0
          this.ReadOnly.cboTuKho = this.gridViewVatTu.length != 0
        }
        else{
          this.ReadOnly.cboTuKho = false
          this.ReadOnly.cboLoaiChungTu = false
        }
      }
    },
    async tsbtnDongBo_Click(){
      if (this.vchungtu_id > 0){
        var d = await this.getChungTuTapDoan({chungTuId: this.vchungtu_id})
        if (d.toString() != "-1"){
          await this.$bvModal.msgBoxConfirm("Chứng từ này đã đồng bộ với tđ rồi. Bạn có chắc chắn muốn đồng bộ lại dữ liệu với tập đoàn không!", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (!value){
                return
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
                if (!value){
                  return
                }
            })
            .catch(err => {
                // An error occurred
            })
        }
        let db_kt = await this.KiemTraDongBo({
          chungTuId: this.vchungtu_id,
          maNd: this.getUserName(),//ttnd.ma_nd
          mayCn: 'quy-test',//ttnd.may_cn
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
                        "mayCn": "quy-test",//ttnd.may_cn
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
                  let vchungtu_tmp_id = vchungtu_id
                  await this.Load_ds_chungtu()
                  // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", vchungtu_tmp_id)
                  
              })
              .catch(err => {
                  // An error occurred
              })
          }
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
      if (this.gridViewVatTu_FocusedRow){
        return
      }
      let vctct_id = this.gridViewVatTu_FocusedRow.CTCT_ID
      var f = this.$refs.frmTaoSerial
      f.vctct_id = vctct_id
      f.ShowDialog()
    },
    async toolStripMenuItem3_Click(){
      if(!this.$refs.gridViewThietBi.getDataRows().length){
        this.$toast.error("Chứng từ này ko có thiết bị")
        return
      }
      let ds = this.gridThietBi
      var q = ds.filter(a=>a.SERIAL.toString())
      if(q.length ){
        var f = this.$refs.frmTaoSerial
        f.kieu = 1;
        f.dsCT_ChungTu = q
        f.ShowDialog()
      }
    },
    async btnTB_ButtonClick(){
      var frm =  this.$refs.frmChungTu_TBao
      if(parseInt(this.gridviewChungTu_FocusedRow.SPBH) > 0){
        frm.kieu = 1
      }
      frm.chungtu_id = this.vchungtu_id
      frm.ShowDialog()
    },
    async chkAll_Phieu_CheckedChanged(){
      await this.LamMoi()
    },
    async gridviewChungTu_CustomRowCellEdit(){
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
      if (this.$auth.getMaTinh() == "TGG"){
        // var frm = new frmNhapText("Nhập tên người nhận ");
        // if (frm.ShowDialog() == DialogResult.OK)
        // {
        //     string nv = "";
        //     nv = frm.Output;
        //     var frmBC = new frmXemBaoCao("PTU", new[] { vchungtu_id.toString(), nv });
        //     frmBC.XemNgay();
        // }
      }
      else{
        // var frmBC = new frmXemBaoCao("PTU", new[] { vchungtu_id.toString() });
        // frmBC.XemNgay();
      }
    },
    async btnTaoTbi_Click(){
      if (this.vchungtu_id == 0){
        this.$toast.error("Bạn chưa nhập chứng từ! Hãy nhập thông tin chứng từ và ghi lại, trước khi nhập thiết bị!")
        return 
      }
      if(this.Enabled.tsbtnNhapMoi){
        if (this.nghiepvu_id_chungtu != "" && this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != ""){
          this.$toast.error("Chứng từ tạo từ dự án IMS không được sửa")
          return
        }
        if (soph.startsWith("QT")){
          this.$toast.error("Chứng từ quyết toán không thể sửa!")
          return
        }
        if((await this.KiemTraSuaThuHoi({chungTuId: this.vchungtu_id})).includes("ko") ){
          this.$toast.error("Chứng từ thu hồi nhập kho tổ trưởng không thể sửa")
          return false
        }
        let s = await this.KiemTraCapNhatChungTu({chungTuId: this.vchungtu_id})
        if (s.toString() != "1"){
          this.$toast.error(s)
          return false
        }
        if (this.ttph_id == 4){
          this.$toast.error("Chứng từ đã hoàn thành không thể tạo thiết bị")
          return
        }
      }

      if (!this.dtVatTu.length){
        this.$toast.error("Bạn chưa nhập vật tư chứng từ! Hãy nhập vật tư chứng từ và ghi lại, trước khi nhập thiết bị!")
        return
      }
      let kieu = ""
      if (this.chon_kieu_qlts){
        // var frm = new frmNhapText("Bạn hãy lựa chọn kiểu nhập serial", 1);
        // frm.Text = "Danh sách kiểu nhập serial";
        // frm.rdioCKb.Properties.Items[0].Description = "Bình thường";
        // frm.rdioCKb.Properties.Items[1].Description = "QLTS";
        // var kq = frm.ShowDialog();
        // switch (kq)
        // {
        //     case DialogResult.Cancel:
        //         return;
        //         break;

        //     case DialogResult.OK:
        //         kieu = frm.vkieu_luachon;
        //         break;
        // }
      }
      if (kieu != "" && kieu != "1"){
        var qlts = this.$refs.frmQLTS
        let dt_ctct = this.gridVatTu
        var loai_serial = dt_ctct.filter(a=>a.LOAITBI_ID.toString() == "2")
        if(!loai_serial.length){
          this.$toast.error("Không có thiết bị là loại có serial !")
          return
        }
        var sl_con_ = await this.getTongSoLuongChuaGan({chungTuId: this.vchungtu_id})
        if(sl_con_ && sl_con_.toString() != ""){
          qlts.sl_cothe_gan = parseInt(sl_con_)
        }
        qlts.id_chungtu = this.vchungtu_id
        qlts.Tag = "1"
        qlts.ShowDialog()
        // XacNhan_frmQLTS
      }
      else{
        // Code chọn serial cũ - thu gọn
        let ds_gan = []
        if(this.Enabled.tsbtnNhapMoi){
          if (this.kiemtra_quyen){
            if (! await this.KIEMTRA_QUYEN_XN(4, this.t_kho_id)){return }
          }
          if(this.gridThietBi.length){
            let d_s = this.gridThietBi
            var q = d_s.filter(a=>a.SERIAL.toString())
            if(q.length){
              ds_gan = q
            }
          }
          let mucdich_id = parseInt(this.params.p_MucDich)
          let vma_md = ""
          var d = await this.getMaMucDichTheoID({mucDichId: mucdich_id})
          if(d.length){
            vma_md = d[0].toString()
          }
          if(this.gridVatTu.length){
            this.$toast.error("Danh sách vật tư chưa có. Không thể tạo được thiết bị")
            return
          }
          let dt = []
          if(this.vchungtu_id > 0 && this.dtVatTu.length){
            let a = dtVatTu.filter(p=>p.THIETBI_ID.toString() == "2")
            for(let item of a){
              let s1 = parseInt(item["SOLUONG"])
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
            for(let d_ of dt){
              for(let index in ds_gan ){
                let d_2 = ds_gan[index]
                if(d_["SERIAL"].toString() == "" &&
                  d_2["LOHANG"].toString() == d_["LOHANG"].toString() &&
                  d_2["MA_VT"].toString() == d_["MA_VT"].toString() &&
                  d_2["DONGIA"].toString() == d_["DONGIA"].toString()){

                    d_["SERIAL"] = d_2["SERIAL"].toString();
                    d_["MATHUNG"] = d_2["MATHUNG"].toString();
                    ds_gan.splice(index,1)

                  }
              }

            }
          }

          var frm = this.$refs.GanSeri_Tbi
          frm.dt = dt
          frm.dt_luu = dt
          frm.ds_ = ds_gan
          frm.ShowDialog()
          // XacNhan_GanSeri_Tbi
        }
      }
    },
    async XacNhan_frmQLTS(){
      var qlts = this.$refs.frmQLTS
      if(qlts.xacnhan){
        await this.Load_ds_vattu()
        await this.Load_ds_thietbi()
      }
    },
    async XacNhan_GanSeri_Tbi(){
      var frm = this.$refs.GanSeri_Tbi
      if (frm.xacnhan){
        await this.CapNhatExcelTbi_v2(frm.kq)
      }
    },
    async cboTuKho_ButtonClick(){
      if (this.params.p_LoaiChungTu.toString() == "99" && (this.get_mamd(parseInt(this.params.p_KieuPhieu)) == "10" ||
        this.get_mamd(parseInt(this.params.p_KieuPhieu)) == "23" )){
          var mucdich = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
          if(!this.Enabled.tsbtnNhapMoi && mucdich[0].toString() == "N-X.DCMSL"){
            this.$toast.error("Bạn phải có chứng từ nhập rồi mới có thể chọn để gán chứng từ xuất")
            return
          }
          var f = this.$refs.frmNhapDieuChinh
          if(mucdich.length){
            if (mucdich[0].toString() == "N-X.DCMSL"){
              f.Tag = 1
              f.vchungtu_gan = this.vchungtu_id
            }
            else if (mucdich[0].toString() == "N.DCDG" || mucdich[0].toString() == "N.TTCL"
              || mucdich[0].toString() == "SC_BH_DONGIA"){
              f.Tag = 2
              f.vchungtu_id = f.vchungtu_id_v2 = this.Enabled.tsbtnNhapMoi ? this.vchungtu_id_temp : 0
            }
            else if (mucdich[0].toString() == "NV_DAUTU_BQLDA" || mucdich[0].toString() == "NV_KHAC_BQLDA"){
              f.Tag = 3 
            }
            else{
              this.$toast.error("Bạn chưa chọn đúng mục đích chứng từ")
              return
            }
            f.vma_md = mucdich[0].toString()
          }
          f.dt = this.dt_chia_sl
          f.kho_nhan = parseInt(this.params.p_TuKho)
          f.kho_giao = parseInt(this.params.p_DenKho)
          f.ShowDialog()
          // XacNhan_frmNhapDieuChinh
      }
      else{
        if(!this.params.p_MucDich.toString()){
          this.$toast.error("Bạn phải chọn mục đích")
          return
        }
        var d = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
        if (d.length > 0){
          if (d[0].toString() == "1.NBH" && this.dsQuyen.includes(99)){
            await this.LAY_CHUNGTU()
          }
          else{
            this.$toast.error("Bạn phải chọn mục đích là 1.NBH hoặc bạn không có quyền thiết bị bảo hành")
            return
          }
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
            i["MAY_CN"] = 'quy-test'//ttnd.may_cn;
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
          // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", id_temp)
        }
      }
    },
    async LAY_CHUNGTU(){
      try {
        var f = this.$refs.frmVatTuTrongKho
        f.vkho_id = parseInt(this.params.p_TuKho)
        f.kieu = 2
        f.vngay_ct = this.params.p_dtpNgayDK
        f.dtLuuVT = this.dtLuuVT
        this.ten_khotu = this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO
        f.vngay_ct = this.params.p_dtpNgayDK
        if (!this.Enabled.tsbtnNhapMoi){
          this.vchungtu_id = 0
        }
        else{
          f.vchungtu_id = this.vchungtu_id
        }
        f.ShowDialog()
        // XacNhan_frmVatTuTrongKho
      } catch (error) {
        this.$toast.error("Lỗi lấy chứng từ và vật tư")
      }
    },
    async XacNhan_frmVatTuTrongKho(){
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
        r["TY_LE"] = item["TY_LE"].toString();
        r["MAKHOTD"] = item["MAKHOTD"].toString();
        r["LOAIKHO"] = item["LOAIKHO"].toString();
        r["MATHETS"] = item["MATHETS"].toString();
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
    },
    async tsbtnTroGiup_Click(){

    },
    async ckb_nd_CheckedChanged(){
      await this.LamMoi()
    },
    async tsbtnChiTietSR_Click(){
      // var frmBC = new frmXemBaoCao("CT_SRVT", new[] { vchungtu_id.toString() });
      // frmBC.XemNgay();
    },
    async tsbtnSMS_Click(){
      // try {
        if (!this.gridChungTu.length){
          this.$toast.error("Không có danh sách chứng từ để send sms")
          return
        }
        if (this.gridviewChungTu_FocusedRow){
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
                  chungTuId: this.vchungtu_id,
                  mayCn: 'quy-test',//ttnd.may_cn
                  nguoiCn: this.$auth.getUserName(),//ttnd.ma_nd
                  ipCn: '10.59.90.123'//ttnd.ip
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
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi send sms ")
      // }
    },
    async tsbtnInPhieu_Click(){

    },
    async KIEMTRA_QUYEN_XN(kieu,kho_id){
      // try {
        //Nếu là 1 là kho giao
        var dt = await this.getThongTinQuyenTrenKho({nhanVienId: this.$auth.getNhanVienID(),khoId: kho_id})
        if (!dt.length){
          this.$toast.error("Không lấy được thông tin quyền xác nhận trên kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO)
          return false
        }
        if (kieu == 1){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận ngày gửi trên kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO)
            return false
          }
        }
        if (kieu == 2){
          if(dt[0].quyenKn.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận ngày nhận trên kho " + this.$refs.cboDenKho.getDataByValue(this.params.p_DenKho).TEN_KHO)
            return false
          }
        }

        if (kieu == 3){
          if(dt[0].quyenKn.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận trên kho " + this.$refs.cboDenKho.getDataByValue(this.params.p_DenKho).TEN_KHO + ".Bạn không thể hoàn công.")
            return false
          }
        }

        if (kieu == 4){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền tạo thiết bị trên kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO + ".Bạn không thể hoàn công.")
            return false
          }
        }
        if (kieu == 5){
          if(dt[0].quyenKg.toString() == "0"){
            this.$toast.error("Bạn không có quyền xác nhận trên kho " + this.$refs.cboTuKho.getDataByValue(this.params.p_TuKho).TEN_KHO + ".Bạn không thể xóa/sửa chứng từ.")
            return false
          }
        }
        return true
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi kiểm tra quyền xác nhận ")
      // }
    },
    async chan_ngay_nhangui(){
      if (this.dsQuyen.includes(2502)){
        return false
      }
      return true
    },
    async dtpNgayGuiTT_ButtonClick(){
      try {
        if(!this.Enabled.tsbtnNhapMoi){
          this.$toast.error("Chỉ được phép cập nhật ngày gửi khi đã có chứng từ")
          return
        }
        if(! await this.KiemTraHanChotCT(this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY'), this.params.p_dtpNgayGuiTT)){
          return
        }
        if (! await this.chan_ngay_nhangui()){
          this.$toast.error("Điều phối không có quyền sửa ngày gửi/ngày nhận !")
          return
        }
        if(!this.gridViewVatTu.length){
          this.$toast.error("Trong chứng từ chưa có kèm vật tư nên không thể hoàn thiện chứng từ.")
          return
        }
        if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id, "")){ return }
        if (! await this.KIEMTRA_QUYEN_XN(1, this.t_kho_id)){ return }
        if (this.ttph_id.toString() == "4"){
          this.$toast.error("Chứng từ đã hoàn thiện không thể cập nhật ngày gửi")
          return
        }
        var ds_dt = await this.getThongTinNgayNhanNguoiNhan({chungTuId: this.vchungtu_id})
        if (!ds_dt.length){
          this.$toast.error("Không có dữ liệu ngày nhận không thể cập nhật ngày gửi của kho")
          return
        }
        else{
          if(ds_dt[0].ngayNhanTt.toString()){
            this.$toast.error("Đã có dữ liệu ngày nhận bạn không thể cập nhật ngày gửi đi")
            return
          }
          if (this.xoa_ngaygui_theo_user){
            if(ds_dt[0].nguoiXn.toString() && ds_dt[0].nguoiXn.toString() != this.$auth.getUserName()){ //ttnd.ma_nd

              this.$toast.error("User " + ds_dt[0]["nguoiXn"].toString() + " đã xác nhận ngày xuất. Bạn không thể cập nhật ngày xuất !")
              return
            }
          }
        }

        var kiemtra_ct = await this.KiemTraXacNhanChungTu({
          "kieu": 1,
          "chungTuId": this.vchungtu_id,
          "ngayXuat": this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY'),
          "ngayNhan": this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY'),
          "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
        })
        let kq = kiemtra_ct.toString()
        if (kq.toString() == "1"){
          var z = await this.CapNhatNgayGuiCT({
            "ngayXN": this.f_DateToString(new Date(),'DD/MM/YYYY hh:mm:ss'),
            "ngayGuiTT": this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY hh:mm:ss'),
            "nguoiXn": !this.params.p_dtpNgayGuiTT.toString() ? "" : this.$auth.getUserName(), //ttnd.ma_nd
            "chungTuId": this.vchungtu_id
          })
          if (parseInt(z)){
            this.$toast.success("Cập nhật ngày gửi thành công")
            //set tạm để không phải load lại toàn bộ grid Chứng từ, tăng tốc ctrinh
            // gridviewChungTu.SetRowCellValue(gridviewChungTu.FocusedRowHandle, "NGAYGUI_TT", dtpNgayGuiTT.EditValue);
            // gridviewChungTu.SetRowCellValue(gridviewChungTu.FocusedRowHandle, "NGUOI_XN", ttnd.ma_nd);
            if(this.$auth.getMaTinh() == "HCM"){
              if(this.params.p_dtpNgayGuiTT.toString()){
                var upp = await this.CapNhatNgayChungTuHCM({
                  "ngayGuiTT": this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY hh:mm:ss'),
                  "chungTuId": this.vchungtu_id
                })
              }
            }
            //Load phieu_kt 
            try {
              var dt = await this.getIDPhieuKT({chungTuId: this.vchungtu_id})
              if(dt.length){
                // gridviewChungTu.SetRowCellValue(gridviewChungTu.FocusedRowHandle, "PHIEU_KT_ID", dt.Rows[0][0]);
              }
            } catch (error) {
              
            }
            // gridviewChungTu_FocusedRowChanged(gridviewChungTu.FocusedRowHandle);
          }
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
      if(! await this.KiemTraHanChotCT(this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY'), this.params.p_dtpNgayNhanTT)){
        return
      }
      if (!await this.chan_ngay_nhangui()){
        this.$toast.error("Điều phối không có quyền sửa ngày gửi/ngày nhận !")
        return
      }
      if(!this.gridViewVatTu.length){
        this.$toast.error("Trong chứng từ chưa có kèm vật tư nên không thể hoàn thiện chứng từ.")
        return
      }
      if (! await this.KIEMTRA_THAYDOI(this.vchungtu_id, "")){return }
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
      var kiemtra_ct = await this.KiemTraXacNhanChungTu({
          "kieu": 2,
          "chungTuId": this.vchungtu_id,
          "ngayXuat": this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY'),
          "ngayNhan": this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY'),
          "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
        })
        let kq = kiemtra_ct.toString()
        if (kq.toString() == "1"){
          var z = await this.CapNhatNgayNhanCT({
            "ngayNhanTT": this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY'),
            "nguoiTh": this.$auth.getUserName(),//ttnd.ma_nd
            "chungTuId": this.vchungtu_id
          })
          if (parseInt(z)){
            this.$toast.success("Cập nhật ngày nhận thành công")
            if (!this.tudong_hoanthanh){
              //set tạm để không phải load lại toàn bộ grid Chứng từ, tăng tốc ctrinh
              // gridviewChungTu.SetRowCellValue(gridviewChungTu.FocusedRowHandle, "NGAYNHAN_TT", dtpNgayNhanTT.EditValue);
              // gridviewChungTu.SetRowCellValue(gridviewChungTu.FocusedRowHandle, "NGUOI_TH", ttnd.ma_nd);
              try {
                var dt = await this.getIDPhieuKT({chungTuId: this.vchungtu_id})
                if(dt.length){
                  // gridviewChungTu.SetRowCellValue(gridviewChungTu.FocusedRowHandle, "PHIEU_KT_ID", dt.Rows[0][0]);
                }
              } catch (error) {
                
              }
              // gridviewChungTu_FocusedRowChanged(gridviewChungTu.FocusedRowHandle);
            }
            else{
              // tsbtnHoanThanh.PerformClick()
            }
          }
          else{
            this.$toast.error("Cập nhật ngày nhận thất bại " + z)
          }
        }
        else{
          this.$toast.error(kq)
        }
    },
    async tsbtnTraLai_Click(){
      // try {
        if (this.vchungtu_id == 0){
            this.$toast.error("Bạn phải chọn chứng từ để xóa")
            return
        }
        if (! await this.KiemTraHanChotCT()){ return }
        if (! await KIEMTRA_THAYDOI(this.vchungtu_id, "")){
            return;
        }
        if (this.kiemtra_quyen){
          if (! await this.KIEMTRA_QUYEN_XN(2, this.kho_nhan_goc)){ return }
          if (this.ttph_id != 4){
            this.$toast.error("Chứng từ chưa được hoàn thành không thể trả phiếu.")
            return
          }

          if (! await this.KiemTraDuLieu(false, true, "TRALAI")){ return }
          if (! await this.KiemTra_NhapLeDieuChinh(true)){ return }
          await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn trả phiếu không!", {
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
                  if (this.params.p_ckbSendSMS){
                    try {
                      await this.GuiSMSChungTu({
                        vtudong: 2,
                        vchungtu_id: this.vchungtu_id,
                        vmay_cn: 'quy-test',//ttnd.may_cn
                        vnguoi_cn: this.$auth.getUserName(),//ttnd.ma_nd
                        ip_cn:  '10.59.90.123'//ttnd.ip
                      })
                    } catch (error) {
                      this.$toast.error("Có lỗi khi send smsm tự động")
                    }
                  }
                  s = await this.XoaPhieuDaHoanThanh({
                    "chungTuId": this.vchungtu_id,
                    "nguoiCn": this.$auth.getUserName(),//ttnd.ma_nd
                    "mayCn": "quy-test",//ttnd.may_cn
                    "ipCn": "10.59.90.123"//ttnd.ip
                  })
                  if (s.toString() != "1"){
                    this.$toast.error("Có lỗi trong quá trình xóa phiếu!" + s)
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
                let kq = await this.CapNhat_CT(2, false, true)
                try {
                  await this.CapNhatNgayNhanTTChungTu({
                      "ngayNhanTT": "",
                      "nguoiTh": "",
                      "chungTuId": this.vchungtu_id
                  })  
                } catch (error) {
                  
                }
                await this.CapNhatChiTiet_CT(false)
                let vchungtu_tmp_id = this.vchungtu_id
                if(this.vtdk.length){
                  await this.CAPNHAT_DANGKY(vchungtu_tmp_id)
                }
                await this.Load_ds_chungtu()
                // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", vchungtu_tmp_id)
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
    async inKimPhiếuToolStripMenuItem_Click(){
      // chịu, làm sau
    },
    async BtnOkOnClick(){
      // ((sender as Button).Parent as Form).DialogResult = DialogResult.OK;
    },
    async FrmOnShown(){
      // (sender as Form).Size = new Size(300, 170);
    },
    async CkbIn_CheckedChanged(){
      // gridviewChungTu.CloseEditor();
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
        // this.Visible.layoutControlItem26 = false // btnRef
        // this.Visible.splitterItem1 = false
        // this.Visible.emptySpaceItem3 = false
        // this.Visible.layoutControlItem23 = false
        // this.Visible.emptySpaceItem2 = false
        // this.Visible.layoutControlItem24 = false
        // this.Visible.layoutControlItem18 = false
        // this.Visible.layoutControlItem21 = false
        // this.Visible.layoutControlItem20 = false
        // this.Visible.layoutControlItem19 = false
        // this.Visible.layoutControlItem22 = false
        // this.Visible.layoutControlItem25 = false
      }
      else{
        this.Visible.ThongTinHDDA = true
        // this.Visible.layoutControlItem26 = true //btnRef
        // this.Visible.splitterItem1 = true
        // this.Visible.emptySpaceItem3 = true
        // this.Visible.emptySpaceItem2 = true 
        // this.Visible.layoutControlItem23 = true //btn_go
        // this.Visible.layoutControlItem24 = true // btnGan
        // this.Visible.layoutControlItem18 = true //thông tin hd dự án
        // this.Visible.layoutControlItem21 = true //hợp đồng
        // this.Visible.layoutControlItem20 = true //ckbDuAn
        // this.Visible.layoutControlItem19 = true // dự án
        // this.Visible.layoutControlItem22 = true // Ghi chú
        // this.Visible.layoutControlItem25 = true // btnDelete_VTDK
        // try {
          if(this.cboHopDong.length){
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

        // } catch (error) {
          
        // }
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
        var da = await this.getDSDuAnV2({chkDuAn: this.params.p_ckbDuAn ? 1 : 0})
        this.cbo_DuAn = da
      }
      else{
        var da = await this.getDSDuAnV2({chkDuAn: this.params.p_ckbDuAn ? 1 : 0})
        this.cbo_DuAn = da
      }
    },
    async KIEMTRA(kieu){
      // try {
        if(!this.Enabled.tsbtnNhapMoi){
          this.$toast.error("Bạn chỉ có thể gán gỡ khi đang ở trạng thái cập nhật chứng từ")
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
        if (this.gan_huy_duan){
          for(var i of t){
            if (i["DUAN"].toString() != ""){
              this.$toast.error("Vật tư lô hàng " + i["LOHANG"] + " đã có mã dự án. Không thế gán đè !")
              return
            }
          }
        }
        let json_list = JSON.stringify(t)
        await this.GanHDDuAnChiTietCT({
          "hdmsId" : this.params.p_HopDong,
          "duAnId" : this.params.p_DuAn,
          "ghiChu" : this.params.txtGhiChuHD,
          "data" : json_list
        })
        if (this.qr_code){
          var z = await this.SinhQRCode({
            "ds": json_list,
            "duAnId": this.params.p_DuAn,
            "kieu": 0
          })  
        }
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
        if (this.gan_huy_duan){
          for(var i of t){
            var check = await this.KiemTraHuyGanMaDuAn({soHieuCt: i["DUAN"]})
            if (check.length){
              this.$toast.error("Lô hàng: " + i["LOHANG"] + " - Mã dự án tự động đồng bộ trên qlts. Không thể hủy gán !")
              return
            }
          }
        }
        let json_list = JSON.stringify(t)
        await this.GoGanHDDuAnCTCT({ "data" : json_list})
        if (this.qr_code){
          var z = await this.SinhQRCode({
            "ds": json_list,
            "duAnId": "",
            "kieu": 1
          })  
        }
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
    async cboKieuPhieu_EditValueChanged(){
      this.params.p_KieuPhieu = 10104
      var t = await this.getDSLoaiKhoTruyenDan({mucDichId: this.params.p_KieuPhieu})
      this.cboLoaiKho = t
      if(!this.Enabled.tsbtnNhapMoi){
        var s = await this.getIDLoaiChungTu({mucDichId: this.params.p_KieuPhieu})
        if(await this.get_mamd(parseInt(this.params.p_KieuPhieu)) == "4" && s[0].toString() == "1"){
          this.Visible.colTyLe = true
        }
        else{
          this.Visible.colTyLe = false
        }
        if (this.params.p_LoaiChungTu.toString() == "99"){  
          if ((await this.get_mamd(parseInt(this.params.p_KieuPhieu))).toString() ==  "10"){
            this.Enabled.btnSeri_ChiaSL = true
          }
          else{
            this.ReadOnly.cboDenKho = false
            this.ReadOnly.cboTuKho = false
            this.Enabled.btnSeri_ChiaSL = false

          }
        }
        else{
          this.Enabled.btnSeri_ChiaSL = false
        }
      }
      else{
        if(this.params.p_LoaiChungTu.toString() == "99"){
          if(await this.get_mamd(parseInt(this.params.p_KieuPhieu)) == "10"){
            this.Enabled.btnTaoTbi = false
            this.Enabled.btnSeri_ChiaSL = true
          }
          else{
            this.Enabled.btnTaoTbi = true
            this.Enabled.btnSeri_ChiaSL = false

          }
        }
        else{
          this.Enabled.btnSeri_ChiaSL = false
        }
      }
    },
    async btnSeri_ChiaSL_Click(){
      var t = this.ds_md.filter(a=>a.MUCDICH_ID.toString() == this.params.p_MucDich.toString())
      if (t.length > 0 && (t[0]["MA_MD"].toString() == "N.DCDG" || t[0]["MA_MD"].toString() == "N.TTCL"
      || t[0]["MA_MD"].toString() == "SC_BH_DONGIA" || t[0]["MA_MD"].toString() == "NV_DAUTU_BQLDA"
      || t[0]["MA_MD"].toString() == "NV_KHAC_BQLDA")){
        var f = this.$refs.frmGanSeri_v2
        f.sophieu = this.soph
        f.vchungtu_id = this.vchungtu_id
        f.vma_md = t[0]["MA_MD"].toString()
        f.dt_vt = this.gridVatTu
        f.ShowDialog()
        // XacNhan_frmGanSeri_v2
      }
      else{
        this.$toast.error("Không đúng mục đích. Bạn hãy kiểm tra lại")
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
    async bBToolStripMenuItem_Click(){
      // var frmBC = new frmXemBaoCao("BBGNNK", new[] { vchungtu_id.ToString() });
      // frmBC.XemNgay()
    },
    async tsbtnBBGNXuat_Click(){
      // var frmBC = new frmXemBaoCao("BBGNXK", new[] { vchungtu_id.ToString() });
      // frmBC.XemNgay();
    },
    async btnChonTamUng_Click(){
      if (this.Enabled.tsbtnNhapMoi && (this.dc_ct_noibo || this.dc_ct_ttvt || this.dc_ct_ldscdp 
                || this.bqlda_nguonvon_dautu || this.bqlda_nguonvon_khac)){
        var frm = this.$refs.frmChonTBi_CT
        if (this.dc_ct_noibo || this.bqlda_nguonvon_khac || this.bqlda_nguonvon_dautu){
          frm.vcongtrinh_id = parseInt(this.params.p_CongTrinh)
        }
        // bqlda HCM đổi tkdu mà giữ nguyên công trình ...
        if (bqlda_nguonvon_khac || bqlda_nguonvon_dautu){
          if(await this.get_mamd(parseInt(this.params.p_KieuPhieu)) == "10"){
            frm.bqlda = true
          }
        }
        frm.vchungtu_id = vchungtu_id
        frm.vkho_id = parseInt(this.params.p_TuKho)
        frm.ShowDialog()
        // XacNhan_frmChonTBi_CT
      }
    },
    async XacNhan_frmChonTBi_CT(){
      var frm = this.$refs.frmChonTBi_CT
      if (frm.xacnhan){
        var nd = await this.getNoiDungGiaoCT({chungTuId: this.vchungtu_id})
        if (nd.toString()){
          this.params.p_txtNoiDung = nd.toString()
        }
        await this.Load_ds_thietbi()
      }
    },
    async dtpNgayChuyenKT_ButtonClick(){
      // try {
        if (this.gridVatTu.length){
          this.$toast.error("Trong chứng từ chưa có kèm vật tư nên không thể hoàn thiện chứng từ.")
          return
        }
        var z = await this.CapNhatNgayChuyenCT({
          "chungTuId": this.vchungtu_id,
          "ngayCtKt": this.f_DateToString(this.params.dtpNgayChuyenKT,'DD/MM/YYYY'),
          "nguoiCtKt": this.$auth.getUserName()//ttnd.ma_nd
        })
        if(parseInt(z)){
          this.$toast.error("Cập nhật ngày chuyển thành công")
          //set tạm để không phải load lại toàn bộ grid Chứng từ, tăng tốc ctrinh
          // gridviewChungTu.SetRowCellValue(gridviewChungTu.FocusedRowHandle, "NGAYCT_KT", dtpNgayChuyenKT.EditValue);
          // gridviewChungTu.SetRowCellValue(gridviewChungTu.FocusedRowHandle, "NGUOI_CTKT", ttnd.ma_nd);
          // gridviewChungTu_FocusedRowChanged(gridviewChungTu.FocusedRowHandle)
        }
        else{
          this.$toast.error("Cập nhật ngày chuyển thất bại ")
        }

      // } catch (error) {
      //   this.$toast.error("Cập nhật ngày chuyển thất bại ")
      // }
    },
    async tsbtnSuaCT_Click(){
      if (this.ttph_id.toString() == "4" ){
        var f = this.$refs.frmSuaCT
        f.dtChungTu = dtChungTu
        f.kieuphieu = parseInt(this.gridviewChungTu_FocusedRow.LOAIPHIEU_ID)
        f.vchungtu_id = this.vchungtu_id
        f.loaict_id = this.params.p_LoaiChungTu.toString() == "99" ? 1 : parseInt(this.params.p_LoaiChungTu)
        f.ShowDialog()
        // XacNhan_frmSuaCT
      }
    },
    async XacNhan_frmSuaCT(){
      var f = this.$refs.frmSuaCT
      if (f.xacnhan == true || f.load_file_again == true){
        await this.Load_ds_chungtu()
        // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", f.vchungtu_id)
      }
    },
    async dtpNgayDK_ButtonClick(){
      if (this.vchungtu_id <= 0 || !this.Enabled.tsbtnNhapMoi){
        this.$toast.error("Không có chứng từ không thể cập nhật")
        return
      }
      if (! await this.KiemTraNguoiCN()){
        this.$toast.eror("Chỉ có người lập chứng từ mới có thể cập nhật ngày chứng từ")
        return
      }
      var kq = await this.CapNhatNgayChungTu({
        "chungTuId": this.vchungtu_id,
        "ngayCt": this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY')
      })
      if (kq.length && kq[0].toString() != "1"){
        this.$toast.error(kq[0].toString())
      }
      await this.CapNhatNgayChungTuV2({
        "chungTuId": this.vchungtu_id,
        "ngayCt": this.f_DateToString(this.params.dtpNgayDK,'DD/MM/YYYY')
      })
      this.$toast.error("Cập nhật ngày chứng từ thành công")
      // gridviewChungTu.SetRowCellValue(gridviewChungTu.FocusedRowHandle, "NGAY_CT", dtpNgayDK.EditValue)
    },
    async gridViewVatTu_CustomDrawCell(){
      // làm sau
    },
    async cboMucDich_EditValueChanged(){
      if (!this.params.p_LoaiChungTu.toString()){ return }
      if (!this.params.p_MucDich.toString()){ return }
      if(this.params.p_LoaiChungTu.toString() == "99"){
        let mdcheck = await this.getMaMucDichTheoID({mucDichId: this.params.p_MucDich})
        if (!this.Enabled.tsbtnNhapMoi && (mdcheck[0].toString() == "N.DCDG" 
        || mdcheck[0].toString() == "SC_BH_DONGIA"
        || mdcheck[0].toString() == "NV_DAUTU_BQLDA"
        || mdcheck[0].toString() == "NV_KHAC_BQLDA")
        && this.params.p_KieuPhieu.toString() == "10119"){
          this.ReadOnly.cboTuKho = true
          this.ReadOnly.cboDenKho = true
          this.Enabled.tsbtnThemVT = false
        }
        else{
          this.ReadOnly.cboTuKho = false
          this.ReadOnly.cboDenKho = false
          this.Enabled.tsbtnThemVT = true
        }
      }
    },
    async kiemtra_thaydoi_ctct(){
      await this.KiemTraThayDoiChiTietCT({chungTuId: this.vchungtu_id,themMoi: this.Enabled.tsbtnNhapMoi ? 1 : 0})
      let result = kt.toString()
      if (result != "1"){
        this.$toast.error(result)
        return false
      }
      return true
    },
    async tsbtnChonCTCT_Click(){
      if (this.vchungtu_id == 0){
        this.$toast.error("Bạn chưa nhập chứng từ! Hãy nhập thông tin chứng từ và ghi lại, trước khi nhập thiết bị!")
        return
      }
      if(this.Enabled.tsbtnNhapMoi){
        var f = this.$refs.frmChonVT_CTCT
        f.vchungtu_id = vchungtu_id
        f.ShowDialog()
      }
    },
    async btnEditLKMK_Click(){
      if (this.ttph_id.toString() == "4"){ return }
      var f = this.$refs.frmLoaiKho_MaKho
      f.Tag = 2
      f.loaict = parseInt(this.params.p_LoaiChungTu)
      f.kieuphieu = this.params.p_KieuPhieu
      f.dt_dasua = this.gridVatTu
      f.ShowDialog()
      // XacNhan_frmLoaiKho_MaKho
    },
    async XacNhan_frmLoaiKho_MaKho(){
      var f = this.$refs.frmLoaiKho_MaKho
      if (f.xacnhan){
        if (this.Enabled.tsbtnNhapMoi && this.nghiepvu_id_chungtu != ""
          && this.nghiepvu_id_chungtu == "280" && this.ma_pda_chungtu != ""){
            for(var  z of f.dt_dasua){
              if (z["LOAIKHO"].toString() != ""){
                await this.CapNhatLoaiKhoTapDoan({loaiKhoTdId: z["LOAIKHO"], ctctId: parseInt(z["CTCT_ID"])})
              }
              if (z["MAKHOTD"].toString() != ""){
                await this.CapNhatKhoTapDoan({khoTdId: z["MAKHOTD"],ctctId: parseInt(z["CTCT_ID"])})
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
    async tsbtnExcelLoaiKho_Click(){
      await this.dieuchinh_vt_excel(1)
    },
    async tsbtnExcelChatLuong_Click(){
      await this.dieuchinh_vt_excel(2)
    },
    async tsbtnExcelGhiChu_Click(){
      await this.dieuchinh_vt_excel(3)
    },
    async dieuchinh_vt_excel(kieu){
      // try {
        if (this.vchungtu_id <= 0){ return }
        if(this.gridVatTu.length){ return }
        if (! await this.KiemTra_XuatHoanTra(false)) { return }
        if (! await this.KiemTra_NhapLeDieuChinh(false)){ return }
        if (this.ttph_id == 4){
          this.$toast.error("Chứng từ đã hoàn thiện ! Không thể dùng chức năng này !")
          return
        }
        // OpenFileDialog fdg = new OpenFileDialog();
        // fdg.InitialDirectory = @"C:\";
        // fdg.Multiselect = false;
        // fdg.Filter = "Excel Files(*.XLS;*.XLSX)|*.XLS;*.XLSX";
        // fdg.FilterIndex = 1;
        // if (kieu == 1)
        // {
        //     fdg.Title = "Cấu trúc file gồm sheet1:  {CTCT_ID, LOAI_KHO_MOI}";
        // }
        // else if (kieu == 2)
        // {
        //     fdg.Title = "Cấu trúc file gồm sheet1:  {CTCT_ID, CHAT_LUONG_MOI}";
        // }
        // else
        // {
        //     fdg.Title = "Cấu trúc file gồm sheet1:  {CTCT_ID, GHI_CHU_MOI}";
        // }

        // if (fdg.ShowDialog() == DialogResult.OK)
        // {
        //     string path = fdg.FileNames[0];
        //     string exten = Path.GetExtension(path);
        //     dt_file = bangts.DocFile_Excel(exten, path);
        //     KiemTraDuLieu_Excel(dt_file, kieu);
        // }
      // } catch (error) {
      //   this.$toast.error("Lỗi định dạng file excel, bạn xem lại! ")
      // }
    },
    async KiemTraDuLieu_Excel(dt, kieu){
      this.$root.showLoading(true)
      if (dt.length && Object.keys(dt[0]).length){
        this.$root.showLoading(false)
        this.$toast.error("Số lượng cột phải = 2")
        await this.TaiFileMau(kieu)
        return
      }
      if(!dt[0].hasOwnProperty('CTCT_ID')){
        this.$root.showLoading(false)
        this.$toast.error("Tên cột đầu tiên trong file không đúng định dạng : CTCT_ID")
        await this.TaiFileMau(kieu)
        return
      }
      if (kieu == 1){
        if(!dt[0].hasOwnProperty('LOAI_KHO_MOI')){
          this.$root.showLoading(false)
          this.$toast.error("Tên cột thứ 2 trong file không đúng định dạng : LOAI_KHO_MOI")
          await this.TaiFileMau(kieu)
          return
        }
      }
      else if (kieu == 2){
        if(!dt[0].hasOwnProperty('CHAT_LUONG_MOI')){
          this.$root.showLoading(false)
          this.$toast.error("Tên cột thứ 2 trong file không đúng định dạng : CHAT_LUONG_MOI")
          await this.TaiFileMau(kieu)
          return
        }
      }
      else{
        if(!dt[0].hasOwnProperty('GHI_CHU_MOI')){
          this.$root.showLoading(false)
          this.$toast.error("Tên cột thứ 2 trong file không đúng định dạng : GHI_CHU_MOI")
          await this.TaiFileMau(kieu)
          return
        }
      }
      let json = JSON.stringify(this.dt_file)
      var kq_kt = await this.KiemTraVatTuDocfile({
        "data": json,
        "chungTuId": this.vchungtu_id,
        "kieu": kieu
      })
      if (kq_kt.toString().includes("OK~")){
        let tet = (kieu == 1) ? "loại kho" : ((kieu == 2) ? "chất lượng" : "ghi chú")
        this.$toast.error("Đã cập nhật " + tet + " các phiếu: " + kq_kt.ToString().Split('~')[1])
        await this.Load_ds_vattu
      }
      else{
        this.$toast.error(kq_kt.toString())
        return
      }
    },
    async TaiFileMau(){
      await this.$bvModal.msgBoxConfirm("Bạn có muốn tải file mẫu về không ?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                // var f = new frmDownloadDialog()
                if (kieu == 1){
                  // f.fURL = "http://10.70.52.164:8081/FILES/HCM/VATTU/LOAIKHO.xlsx"
                }
                else if (kieu == 2){
                  // f.fURL = "http://10.70.52.164:8081/FILES/HCM/VATTU/CHATLUONG.xlsx"
                }
                else{
                  // f.fURL = "http://10.70.52.164:8081/FILES/HCM/VATTU/GHICHU.xlsx"
                }
                // Uri uri = new Uri(f.fURL);
                // string filename = System.IO.Path.GetFileName(uri.LocalPath);
                // string sFileExt = Path.GetExtension(filename);

                // SaveFileDialog odlg = new SaveFileDialog();

                // if (odlg.ShowDialog() == DialogResult.OK)
                // {
                //     if (!odlg.FileName.EndsWith(sFileExt))
                //     {
                //         f.fDes = odlg.FileName + sFileExt;
                //         f.fName = odlg.FileName + sFileExt;
                //     }
                //     else
                //     {
                //         f.fDes = odlg.FileName;
                //         f.fName = odlg.FileName;
                //     }
                //     f.DownloadCompleted += (o, v) =>
                //     {
                //         if (f.ThanhCong)
                //         {
                //             Message_Box.Show("Tải file thành công");
                //             try
                //             {
                //                 if (Message_Box.ShowQuestion("Bạn muốn mở file vừa tải về không ?") == DialogResult.Yes)
                //                     try
                //                     {
                //                         var prc = new Process();
                //                         prc.StartInfo.FileName = f.fName;
                //                         prc.StartInfo.Verb = "Open";
                //                         prc.StartInfo.WindowStyle = ProcessWindowStyle.Maximized;
                //                         prc.Start();
                //                     }
                //                     catch
                //                     {
                //                         Message_Box.ShowError("Không tìm thấy ứng dụng để mở file vừa tải !");
                //                     }
                //                 System.Diagnostics.Process.Start(Path.GetDirectoryName(f.fName));
                //             }
                //             catch
                //             {
                //             }
                //         }
                //     };
                //     f.Start();
                // }
              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async gridViewVatTu_MouseUp(){
      // try
      // {
      //     if (e.Button == MouseButtons.Left)
      //     {
      //         gridViewVatTu.UpdateTotalSummary();
      //     }
      // }
      // catch (Exception ex)
      // {
      //     Message_Box.ShowWarning("Lỗi chọn dòng: " + ex);
      // }
    },
    async gridViewVatTu_FocusedRowChanged(){
      // if (e.FocusedRowHandle < 0)
      //   gridViewVatTu.UpdateTotalSummary();
    },
    async gridViewThietBi_CustomSummaryCalculate(){
      // làm sau
    },
    async gridViewThietBi_MouseUp(){
      // try
      // {
      //     if (e.Button == MouseButtons.Left)
      //     {
      //         gridViewThietBi.UpdateTotalSummary();
      //     }
      // }
      // catch (Exception ex)
      // {
      //     Message_Box.ShowWarning("Lỗi chọn dòng: " + ex);
      // }
    },
    async gridVTGroup_DataSourceChanged(){
      if (this.Visible.xTabTongHop){
        this.ReadOnly.cboLoaiChungTu = this.gridVTGroup.length != 0
        this.ReadOnly.cboTuKho = this.gridVTGroup.length != 0
      }
      else{
        this.ReadOnly.cboLoaiChungTu = false
        this.ReadOnly.cboTuKho = false
      }
    },
    async tsbtnUpPXK_Click(){
      await this.GHILAI_CT("UPSL")
    },
    async txtNoiDung_DoubleClick(){
      var frmNhap = this.$refs.frmNoiDungTT
      frmNhap.tag = "2"
      frmNhap.Text = "Nội dung chứng từ"
      frmNhap.vnoidung = txtNoiDung.Text
      // if (DialogResult.OK == frmNhap.ShowDialog())
      if (this.Enabled.tsbtnNhapMoi){
        if(this.dsQuyen.includes(2514)){
          await this.CapNhatNoiDungGiaoSuaCT({chungTuId: this.vchungtu_id, ndGiao: frmNhap.vnoidung.toString()})
          this.$toast.success("Sửa nội dung chứng từ thành công !")
        }
        this.params.p_txtNoiDung = frmNhap.vnoidung.toString()
      }
    },
    async btnFilePath_Click(){
      // lam sau
    },
    async fu_UploadCompleted(){
      // ...
    },
    async btnDelete_ButtonClick(){

    },
    async btnDowload_ButtonClick(){
      await DOWLOAD_OPEN(false)
    },
    async btnXem_ButtonClick(){
      await this.DOWLOAD_OPEN(true)
    },
    async DOWLOAD_OPEN(xemFile){
      //...
    },
    async dtpNgayKy_ButtonClick(){
      // try {
      if (!this.gridVatTu.length){
        this.$toast.error("Trong chứng từ chưa có kèm vật tư nên không thể ký chứng từ.")
        return
      }
      if(!this.Enabled.tsbtnNhapMoi){
        this.$toast.error("Chứng từ phải được ghi lại mới có thể cập nhật ngày ký")
        return
      }
      if (!dsQuyen.includes(DS_QUYEN_VT.QUYET_XETDUYET)){
        this.$toast.error("Bạn phải có quyền xét duyệt chứng từ mới có thể thực hiện")
        return
      }
      var xttph = await this.getThongTInTrangThaiPhieuV2({chungTuId: this.vchungtu_id})
      if(!xttph.length){
        this.$toast.error("Không lấy được thông tin trạng thái phiếu")
        return
      }
      if(xttph.length){
        if (xttph[0].toString() == "4"){
          this.$toast.error("Bạn không được cập nhật ngày ký khi chứng từ đã hoàn thành");
          return;
        }
        if (xttph[0]["ngayXd"].toString() != ""){
          if (xttph[0]["nguoiXd"].toString() != ""){
            if (xttph[0]["nguoiXd"].toString() != this.$auth.getUserName()){//ttnd.ma_nd
              this.$toast.error("User " + xttph[0]["nguoiXd"].ToString()
                                        + " đã xác nhận ngày ký. Bạn không thể cập nhật ngày ký !")
              return
            }
          }
        }
        if (xttph.Rows[0]["soPhieuIn"].toString() != ""){
          if (xttph.Rows[0]["soPhieuIn"].toString() != ttnd.ma_nd){
            this.$toast.error("Chứng từ " + this.soph + " đã được tiếp nhận bởi user " +
              xttph.Rows[0]["soPhieuIn"].toString() + " ! Bạn không thế tác động vào chứng từ !")
            return
          }
        }
      }
      var z = await this.CapNhatNgayKyCT({
        "chungTuId": this.vchungtu_id,
        "ngayXd": this.f_DateToString(this.params.p_dtpNgayKy,'DD/MM/YYYY'),
        "nguoiXd": !this.params.p_dtpNgayKy.toString() ? "" : this.$auth.getUserName() //ttnd.ma_nd
      })
      if (parseInt(z)){
        if(this.params.p_dtpNgayKy.toString()){
          var zok = await this.CapNhatSoPhieuIn({chungTuId: this.vchungtu_id, soPhieuIn: ""})
        }
        this.$toast.success("Cập nhật ngày ký thành công")
        let vchungtu_tmp_id = vchungtu_id
        await this.Load_ds_chungtu()
        // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", vchungtu_tmp_id)
      }
      // } catch (error) {
      //   this.$toast.error("Cập nhật ngày ký thất bại ")
      // }
    },
    async btnTiepNhanPhieu_Click(){
      // try {
        if(!this.gridChungTu.length){
          this.$toast.error("Không có dữ liệu chứng từ")
          return
        }
        if(this.vchungtu_id <= 0){
          this.$toast.error("Chứng từ không tồn tại !")
          return
        }
        if(this.gridviewChungTu_FocusedRow){
          this.$toast.error("Hãy click vào dòng chứng từ !")
          return
        }
        if (!dsQuyen.includes(DS_QUYEN_VT.QUYET_XETDUYET)){
          this.$toast.error("Bạn phải có quyền xét duyệt chứng từ mới có thể thực hiện")
          return
        }
        var xttph = await this.getThongTinTrangThaiPhieuV3({chungTuId: this.vchungtu_id})
        if(!xttph.length){
          this.toast.error("Không lấy được thông tin trạng thái phiếu")
          return
        }
        if(xttph.length){
          if (xttph[0]["ttPhId"].toString() == "4"){
            this.$toast.error("Bạn không được tiếp nhận khi chứng từ đã hoàn thành")
            return
          }
          if (xttph[0]["ngayXd"].toString() != ""){
            this.$toast.error("Chứng từ " + this.soph + " đã có ngày ký! Không thế tiếp nhận chứng từ !")
            return
          }
          if (xttph[0]["soPhieuIn"].toString() != ""){
            if (xttph[0]["soPhieuIn"].toString() != this.$auth.getUserName()){//ttnd.ma_nd
              this.$toast.error("Chứng từ " + soph + " đã được tiếp nhận bởi user " +
                xttph[0]["soPhieuIn"].toString() + " ! Bạn không thế tác động vào chứng từ !")
              return
            }
          }
        }
        /* Dùng tạm trường sophieu_in để lưu người tiếp nhận
          - Nếu có thay đổi chỗ lứu thì phải sửa đoạn này 
        *  + sửa trạng thái hiển thị chứng từ [... theoloai]
          + sửa dòng update gần cuối hàm hoanthien_chungtu, line ~~ 520 ~ 600*/
          var z = await this.CapNhatNguoiTiepNhanCT({chungTuId: this.vchungtu_id,soPhieuIn: this.$auth.getUserName()})//ttnd.ma_nd
          if(parseInt(z)){
            let ki = this.params.p_LoaiChungTu.toString() == "2" ? 1 : 2
            var kiemtra_ct = await this.KiemTraXacNhanChungTu({
              "kieu": ki,
              "chungTuId": this.vchungtu_id,
              "ngayXuat": "",
              "ngayNhan": "",
              "nguoiCn": this.$auth.getUserName() // ttnd.ma_nd
            })
            let vchungtu_tmp_id = vchungtu_id
            await this.Load_ds_chungtu()
            // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", vchungtu_tmp_id)
            this.$toast.success("Tiếp nhận chứng từ thành công! Bạn có thể sửa tài khoản đối ứng !" +
                        "\nĐể hoàn thành tiếp nhận, hãy cập nhật ngày ký !")
          }
          else{
            this.$toast.error("Tiếp nhận chứng từ thất bại: " + z)
          }
      // } catch (error) {
      //   this.$toast.error("Tiếp nhận chứng từ có lỗi: ")
      // }
    },
    async tsbtnInXuatDVTC_Click(){
      // var frmBC = new frmXemBaoCao("XDVTC", new[] { vchungtu_id.ToString() });
      // frmBC.XemNgay();
    },
    async cboNghiepVu_EditValueChanged(){
      this.params.p_Ma_PDA = ""
      this.grcMa_PDA = []
      this.cboHDPOIMS = []
      this.cboDuanIMS = []
      this.gridBienBanIMS = []
      var nghiepvu_ = await this.getMaMucDichTheoID({mucDichId: this.params.p_NghiepVu})
      if(!nghiepvu_.length){ return }
      let ma_nghiepvu = nghiepvu_[0].ToString()
      if (ma_nghiepvu == "NV.BBBGIMS"){
        this.Visible.layoutControlDuAn = false
        this.Visible.layoutControlBBBG = true
      }
      else if (ma_nghiepvu == "NV.DUAN"){
        this.Visible.layoutControlDuAn = true
        this.Visible.layoutControlBBBG = false
      }
    },
    async v_btnRef_Click(){
      // try {
        this.grcMa_PDA = []
        this.cboHDPOIMS = []
        this.cboDuanIMS = []
        this.gridBienBanIMS = []
        this.duan_ims_clone = []
        if(!this.params.p_NghiepVu.toString()){
          return
        }
        var nghiepvu_ = await this.getMaMucDichTheoID({mucDichId: this.params.p_NghiepVu})
        if (!nghiepvu_.length){ return }
        let ma_nghiepvu = nghiepvu_[0].ToString()
        if (ma_nghiepvu == "NV.DUAN" && this.kieugoi_id == 2){
          // var dt = ims.Get_ListProject("", "", DanhMuc.GET_MATINH(ttnd.tentat));
          // grcMa_PDA.DataSource = dt;
        }
        if (ma_nghiepvu == "NV.BBBGIMS" && this.kieugoi_id == 2){
          if(!this.params.p_KieuPhieu.toString()){
            this.$toast.error("Bạn chưa chọn kiểu phiếu !")
            return
          }
          if(!this.params.p_MucDich.toString()){
            this.$toast.error("Bạn chưa chọn mục đích !")
            return
          }
          if(!this.params.p_TuKho.toString()){
            this.$toast.error("Bạn chưa chọn từ kho !")
            return
          }
          if(!this.params.p_DenKho.toString()){
            this.$toast.error("Bạn chưa chọn đến kho !")
            return
          }
          var find_csht = await this.getMaCoSoHaTang({khoId: this.params.p_DenKho})
          if(!find_csht.length){
            this.$toast.error("Kho đến không có mã CSHT ! Hãy kiểm tra lại !")
            return
          }
          // var dt_duan = ims.Get_ListProject("", "", DanhMuc.GET_MATINH(ttnd.tentat));
          // duan_ims_clone = dt_duan // Lưu vào dt mới, để khi insert ko phai goi lai api
          // this.cboDuanIMS = dt_duan
        }
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi tìm kiếm dự án ")
      // }
    },
    async btnChon_PDA_ButtonClick(){
      // try {
        if(!this.params.p_NghiepVu.toString()){
          return
        }
        if (this.params.p_NghiepVu.toString() == "280" && this.kieugoi_id == 2){
          if(this.Enabled.tsbtnNhapMoi){
            this.$toast.error("Bạn chưa nhập mới không thể chọn dự án")
            return
          }
          if (! await this.KiemTraDuLieu(true, false, "GHILAI_PDA")){ return }
          await this.$bvModal.msgBoxConfirm("Ban có chắc chắn muốn chọn mã dự án " + this.grvMa_PDA_FocusedRow.ProjectCode + " không? ", {
            "okVariant": 'success',
            "okTitle": 'Đồng ý',
            "cancelTitle": 'Hủy',
            "centered": true,
            })
            .then(async(value) => {
                if (value){
                  this.params.p_Ma_PDA = this.grvMa_PDA_FocusedRow.ProjectCode
                  var dt_temp = []
                  // var dt_temp = ims.Get_Asset_byProjectID("", "", grvMa_PDA.GetFocusedRowCellValue("ProjectCode").ToString());
                  if(dt_temp.length){
                    let t_ct = []
                    t_ct.push({
                      NGAY_CT: this.f_DateToString(this.params.p_dtpNgayDK,'DD/MM/YYYY hh:mm:ss'),
                      ND_GIAO: this.params.p_txtNoiDung,
                      MUCDICH_ID: this.params.p_MucDich,
                      KIEUPHIEU_ID: this.params.p_KieuPhieu
                    })
                    let t_ctct = []
                    let t_ctbi = []
                    for(var dr in dt_temp ){
                      if (dr["SerialNumberOffice"].toString() != ""){
                        var z = t_ctct.filter(l=>l.MA_VT == dr["AssetCodeType"].toString() && l.DONGIA.toString() == dr["PriceForeign"].toString())
                        if(z.length){
                          z = z[0]
                          z.SL_NHAP = z.SL_NHAP + parseInt(dr["AssetNumber"].ToString())
                        }
                        else{
                          t_ctct.push({
                            MA_VT: dr["AssetCodeType"].toString(),
                            DONGIA: parseInt(dr["PriceForeign"].toString()),
                            SL_NHAP: parseInt(dr["AssetNumber"].toString()),
                            MA_PDA: this.grvMa_PDA_FocusedRow.ProjectCode,
                            TEN_PDA: this.grvMa_PDA_FocusedRow.ProjectName,
                            PDA_ID: this.grvMa_PDA_FocusedRow.ProjectID,
                          })
                        }
                        t_ctbi.push({
                          MA_VT: dr["AssetCodeType"].toString(),
                          SOLUONG: 1,
                          ID_CHITIET: dr["AssetCode"].toString(),
                          SERIAL: dr["SerialNumberOffice"].toString()
                        })
                      }
                      else{
                        t_ctct.push({
                          MA_VT: dr["AssetCodeType"].toString(),
                          DONGIA: parseInt(dr["PriceForeign"].toString()),
                          SL_NHAP: parseInt(dr["AssetNumber"].toString()),
                          MA_PDA: this.grvMa_PDA_FocusedRow.ProjectCode,
                          TEN_PDA: this.grvMa_PDA_FocusedRow.ProjectName,
                          PDA_ID: this.grvMa_PDA_FocusedRow.ProjectID,
                        })
                        t_ctbi.push({
                          MA_VT: dr["AssetCodeType"].toString(),
                          SOLUONG: -1,
                          ID_CHITIET: dr["AssetCode"].toString(),
                          SERIAL: ""
                        })
                      }
                    }
                    let ds_vt = JSON.stringify(t_ctct)
                    let ds_tbi = JSON.stringify(t_ctbi)
                    var ketqua_ = await this.SinhCTQuanLyTaiSan({
                      "kieu": 1,
                      "khoNhanId": this.params.p_DenKho,
                      "ngayCt": this.f_DateToString(this.params.dtpNgayDK,'DD/MM/YYYY hh:mm:ss'),
                      "ndGiao": this.params.p_txtNoiDung,
                      "nghiepVuId": this.params.p_NghiepVu,
                      "maPda": this.grvMa_PDA_FocusedRow.ProjectCode,
                      "dsVt": ds_vt,
                      "dsChiTiet": ds_tbi,
                      "nguoiCn": this.$auth.getUserName(),//ttnd.may_cn
                      "mayCn": this.$auth.getUserName(),//ttnd.may_cn
                      "ipCn": "10.59.90.123",//ttnd.ip
                      "khoGiaoId": this.params.p_TuKho,
                      "loaiCtId": 1,
                      "donViGiaoId": this.$auth.getDonViID(),
                      "donViNhanId": this.$auth.getDonViID(),
                      "loaiPhieuId": this.params.p_KieuPhieu,
                      "mucDichId": this.params.p_MucDich
                    })
                    if (ketqua_.toString().startsWith("OK")){
                      this.$toast.success("Tạo chứng từ thành công")
                      vchungtu_tmp_id = Convert.ToInt64(ketqua_.ToString().Split('-')[1])
                      await this.Load_ds_chungtu()
                      // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", vchungtu_tmp_id)
                    }
                    else{
                      this.$toast.error("Tạo chứng từ thất bại " + ketqua_.ToString())
                    }
                  }
                  else{
                    this.$toast.error("Không có dữ liệu tài sản ngắn hạn")
                  }
                }
            })
            .catch(err => {
                // An error occurred
            })
        }
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi chọn PDA ")  
      // }
    },
    async cboMa_PDA_ButtonClick(){
      this.params.p_Ma_PDA = ""
    },
    tsbtnInTemSR_Click(){
      if(!this.gridThietBi.length){
        this.$toast.error("Không có dòng thiết bị nào để in !")
        return
      }
      var dz = this.gridVatTu
      var loai_serial = dz.filter(a=>a.LOAITBI_ID.toString() == "2")
      if (loai_serial.Count() == 0){
        this.$toast.error("Không có thiết bị là loại có serial !");
        return;
      }
      var f = this.$refs.frmQLTS
      f.id_chungtu = this.vchungtu_id;
      f.Tag = "3";
      f.ShowDialog();
    },
    async tsbtnInPhieuNKKT_Click(){
      // var frmBC = new frmXemBaoCao("PNKKT", new[] { vchungtu_id.ToString() });
      // frmBC.XemNgay();
    },
    async dtpNgayKy_Properties_EditValueChanged(){
      if (this.params.p_dtpNgayKy && this.f_DateToString(this.params.p_dtpNgayKy,'DD/MM/YYYY hh:mm:ss').endsWith("00:00:00")){
        let dt = new Date()
        let h = this.params.p_dtpNgayKy.replace("00:00:00", dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds())
        this.params.p_dtpNgayKy = h
      }
    },
    async dtpNgayGuiTT_Properties_EditValueChanged(){
      if (this.params.p_dtpNgayGuiTT && this.f_DateToString(this.params.p_dtpNgayGuiTT,'DD/MM/YYYY hh:mm:ss').endsWith("00:00:00")){
        let dt = new Date()
        let h = this.params.p_dtpNgayGuiTT.replace("00:00:00", dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds())
        this.params.p_dtpNgayGuiTT = h
      }
    },
    async dtpNgayNhanTT_Properties_EditValueChanged(){
      if (this.params.p_dtpNgayNhanTT && this.f_DateToString(this.params.p_dtpNgayNhanTT,'DD/MM/YYYY hh:mm:ss').endsWith("00:00:00")){
        let dt = new Date()
        let h = this.params.p_dtpNgayNhanTT.replace("00:00:00", dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds())
        this.params.p_dtpNgayNhanTT = h
      }
    },
    async cboDuanIMS_EditValueChanged(){
      if(!this.params.p_DuanIMS.length){ return }
      var find_csht = await this.getMaCoSoHaTang({khoId: this.params.p_DenKho})
      var dt_hdpo = []
      // var dt_hdpo = ims.Get_ContractInfo_byProjectCode_Station(cboDuanIMS.EditValue.ToString(), find_csht.Rows[0][0].ToString());
      if (!dt_hdpo.length){
        this.$toast.error("Không có thông tin hợp đồng - PO")
        this.cboHDPOIMS = []
        this.gridBienBanIMS = []
        return
      }
      let temp = []
      temp.push(...dt_hdpo)
      this.cboHDPOIMS= temp
    },
    async cboHDPOIMS_EditValueChanged(){
      if(this.params.p_HDPOIMS.length){ return }
      if (this.params.p_HDPOIMS == ""){
        this.gridBienBanIMS = []
        return
      }
      var find_csht = await this.getMaCoSoHaTang({khoId: this.params.p_DenKho})
      var dt_keys = []
      // var dt_keys = ims.Get_Asset_byKeys(cboDuanIMS.EditValue.ToString(), cboHDPOIMS.Text, cboHDPOIMS.EditValue.ToString(), 
                // find_csht.Rows[0][0].ToString());
      if (!dt_keys){
        this.gridBienBanIMS = []
        return
      }
      this.bbbgiao = dt_keys
      let distinctValues = new Set(...dt_keys.map(a=>a.ACCEPTANCEGENERALNAME))
      this.gridBienBanIMS = []
    },
    async btn_chonBBBG_ButtonClick(){
      // try {
        if(this.Enabled.tsbtnNhapMoi){
          this.$toast.error("Bạn chưa nhập mới không thể chọn BBBG !")
          return
        }
        if (! await this.KiemTraDuLieu(true, false, "BBBG")) { return }
        let ma_bbbg = this.gridviewBienBanIMS_FocusedRow.ACCEPTANCEGENERALNAME.ToString() 
        await this.$bvModal.msgBoxConfirm("Ban có chắc chắn muốn chọn biên bản bàn giao " + ma_bbbg + " không? ", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                if (bbbgiao == null){
                 this.$toast.error("Không có dữ liệu BBBG (null)") 
                 return
                }
                if(!bbbgiao.length){
                  this.$toast.error("Không có dữ liệu BBBG (row)")
                  return
                }
                var loc_dl = bbbgiao.filter(a=>a.AcceptanceGeneralName == ma_bbbg)
                if (loc_dl.length){
                  var project = duan_ims_clone.filter(a=>a.ProjectCode == this.params.p_DuanIMS)
                  var project_temp = project
                  let t_ctct  = []
                  let t_ctbi  = []
                  for (let dr of loc_dl){
                    if (dr["SerialNumberOffice"].toString() != ""){
                      var z = t_ctct.filter(l=>l.MA_VT == dr["AssetCodeType"].toString() && l.DONGIA.toString() == dr["Unit"].toString())
                      if (z.length && z[0] != null){
                          z.SL_NHAP = z.SL_NHAP + parseInt(dr["AssetNumber"].toString());
                      }
                      else{
                        t_ctct.push({
                            MA_VT: dr["AssetCodeType"].toString(),
                            DONGIA: parseInt(dr["PriceVND"].toString()),
                            SL_NHAP: parseInt(dr["AssetNumber"].toString()),
                            MA_PDA: project_temp[0].ProjectCode,
                            TEN_PDA: project_temp[0].ProjectName,
                            PDA_ID: project_temp[0].ProjectID,
                          })
                      }
                      t_ctbi.push({
                          MA_VT: dr["AssetCodeType"].toString(),
                          SOLUONG: 1,
                          ID_CHITIET: dr["AssetCode"].toString(),
                          SERIAL: dr["SerialNumberOffice"].toString()
                        })
                    }
                    else{
                      t_ctct.push({
                        MA_VT: dr["AssetCodeType"].toString(),
                        DONGIA: parseInt(dr["PriceVND"].toString()),
                        SL_NHAP: parseInt(dr["AssetNumber"].toString()),
                        MA_PDA: project_temp[0].ProjectCode,
                        TEN_PDA: project_temp[0].ProjectName,
                        PDA_ID: project_temp[0].ProjectID,
                      })
                      t_ctbi.push({
                          MA_VT: dr["AssetCodeType"].toString(),
                          SOLUONG: -1,
                          ID_CHITIET: dr["AssetCode"].toString(),
                          SERIAL: ""
                        })
                    }
                  }
                  let json_ds_vt = JSON.stringify(t_ctct)
                  let json_ds_tbi = JSON.stringify(t_ctbi)
                  var ketqua_ = await this.SinhChungTuIMSBienBan({
                    "kieu": 1,
                    "khoNhanId": this.params.p_DenKho,
                    "ngayCt": this.f_DateToString(this.params.dtpNgayDK,'DD/MM/YYYY hh:mm:ss'),
                    "ndGiao": this.params.p_txtNoiDung,
                    "nghiepVuId": this.params.p_NghiepVu,
                    "maPda": this.params.p_DuanIMS,
                    "maHd": this.$refs.cboHDPOIMS.getDataByValue(this.params.p_HDPOIMS).ContractNumber ,
                    "maPo": this.params.p_HDPOIMS,
                    "bbBg": ma_bbbg,
                    "dsVt": json_ds_vt,
                    "dsChiTiet": json_ds_tbi,
                    "nguoiCn": this.$auth.getUserName(),//ttnd.may_cn
                    "mayCn": this.$auth.getUserName(),//ttnd.may_cn
                    "ipCn": "10.59.90.123",//ttnd.ip
                    "khoGiaoId": this.params.p_TuKho,
                    "loaiCtId": 1,
                    "donViGiaoId": this.$auth.getDonViID(),
                    "donViNhanId": this.$auth.getDonViID(),
                    "loaiPhieuId": this.params.p_KieuPhieu,
                    "mucDichId": this.params.p_MucDich
                  })
                  if (ketqua_.toString().startsWith("OK")){
                    this.$toast.success("Tạo chứng từ thành công")
                    let vchungtu_tmp_id = parseInt(ketqua_.ToString().Split('-')[1])
                    await this.Load_ds_chungtu()
                    // FocusGridViewRow(gridviewChungTu, "CHUNGTU_ID", vchungtu_tmp_id)
                  }
                  else{
                    this.$toast.error("Tạo chứng từ thất bại " + ketqua_.toString())
                  }

                }
                else{
                  this.$toast.error("Không tìm thấy biên bản bàn giao: " + ma_bbbg)
                }
              }
          })
          .catch(err => {
              // An error occurred
          })
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi chọn BBBG: ")  
      // }
      
    },
    convertData_gridChungTu(){
      this.gridChungTu.forEach(a=>{
        a.TIEN.toString() ? a.TIEN = parseInt(a.TIEN) : ''
      })
    }















  },
created: async function() {
    this.Tag = this.$route.query.Tag || "1" 
    this.clearData();
    await this.frmNhapXuatVT_v2_Load()
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>
  .nhapvattumuale .e-grid tr.e-emptyrow td {
      text-align: center !important;
    }

  .vattu_box {
    position: relative;
  }
  
  .vattu_table {
      position: absolute;
      z-index: 1;
      max-height: 200px;
      background: aliceblue;
      overflow-y: scroll;
      right: 0;
  }
</style>