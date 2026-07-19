<template src='./frmTongHopThuHoi_New.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import TongHopDanhSachThuHoiAPI from  '../api/TongHopDanhSachThuHoiAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/tonghopdanhsachthuhoi'
import { Page as TreePage, 
         Filter as TreeFilter,
         Sort as TreeSort,
         Resize as TreeResize,
         Freeze as TreeFreeze } from "@syncfusion/ej2-vue-treegrid";
import { L10n } from '@syncfusion/ej2-base'
import EventBus from '@/utils/eventBus'
import XLSX from "xlsx";
import frmNhapKhoTH from '../frmNhapKhoTH'
// import CapVatTuThueBao from '../CapVatTuThueBao'

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

export default {

  components: { frmNhapKhoTH, XLSX },
  name: "TongHopDanhSachThuHoi_New",
  mounted() {
  },
  provide: {
    treegrid: [TreePage, TreeFilter, TreeSort, TreeResize, TreeFreeze]
  },
  computed: {
    ...mapState("tonghopdanhsachthuhoi", statePropertyName),
    ...mapGetters("tonghopdanhsachthuhoi", getterName),
    vnhom_id: {
      get() {
        return parseInt(this.params.p_LoaiNhom)
      }
    },
    vloaihd_id: {
      get() {
        return parseInt(this.params.p_LoaiHD)
      }
    },
    vttvt_id: {
      get() {
        return parseInt(this.params.p_TrangThai)
      }
    },
    phieu_cha_id: {
      get() {
        return  ( this.gridviewDanhSach_FocusedRow && parseInt(this.gridviewDanhSach_FocusedRow.PHIEU_CHA_ID)) || 0
      }
    },
    hdtb_id: {
      get() {
        return ( this.gridviewDanhSach_FocusedRow && parseInt(this.gridviewDanhSach_FocusedRow.HDTB_ID)) || 0
      }
    },
    baohong_id: {
      get() {
        return ( this.gridviewDanhSach_FocusedRow && parseInt(this.gridviewDanhSach_FocusedRow.BAOHONG_ID)) || 0
      }
    },
  },
  data() {
    return {
        header: {
            title: "TỔNG HỢP DANH SÁCH THU HỒI",
            list: [
            { name: "Quản lý vật tư", link: { name: "Ui.cards" } },
            {
                name: "Vật tư thuê bao",
                link: { name: "Ui.buttons" },
            },
            {
                name: "Tổng hợp danh sách thu hồi",
                link: { name: "Ui.buttons" },
            },
            ],
        },
        params: {
          p_TrangThai: "",
          p_LoaiNhom: "",
          p_txtSoThueBao: "",
          p_DichVuVT: "",
          p_LoaiHD: "",
          p_cbxTuNgay: false,
          p_dtpTuNgay: new Date(),
          p_dtpDenNgay: new Date(),
          p_txtGhiChu: "",
          p_dtpQuyetToan: new Date(),
          p_DonVi: "",
          p_Kho: "",
          p_KhoKH: "",
          p_DeNghi: ""

        },

        cboTrangThai: [],
        cboLoaiNhom: [],
        cboDichVuVT: [],
        cboLoaiHD: [],
        cboDonVi: [],
        cboKho: [],
        cboKhoKH: [],
        cboDeNghi: [],

        gridLichSuVatTu: [],
        grcDS_VT_DQT: [],
        grcChuaDuyet: [],
        gridDanhSach: [],
        grpVT: [],
        cboTimKiem: [],

        dtthuebao: [],
        DS: [],
        dvi: 0,
        ktbi: 0,
        kho_kh: "",
        ttvt_goc: "",
        vattu_id: "",
        dvitinh_id: "",
        ten_vt: "",
        tagForm: "0",
        tk: "",
        DS_nhapk: [],
        goc: [],
        ctid: 0,
        tk: "",
        thamso_md : [],
        quyettoan_id: 0,

      LOAI_DV: {
        TRAM_VT: 5,
        PHONG_BANHANG_KV: 46,
      },
      LOAI_KHO: {
        KHO_TOTRUONG: 14,
      },
      Enabled: {
        cboKho: false,
        tsbtnThemVT: true
      },
      Visible: {
        tsbtnVatTu: false,
        grpVT: false,
        TabCapMa: false,
        TabNhapKho: false,
        TabThuHoi: false,
        tsbtnNhapKho: false,
        tsbtnGhiLai: false,
        tsbtnTraLai: false,
        tsbtnThemVT: false,
      },
      file: "",
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
      
      ChonTemplate: function () {
            return {
                template: Vue.component('ChonTemplate', {
                    template: `<button class="btn pad4 lh14 w60 btn-outline-primary" @click="ChonVT_Click">
                                  <span class="nc-icon-outline ui-1_circle-add" style='color: green'></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async ChonVT_Click(){
                        
                        // await this.$parent.$parent.$parent.btnChon_ButtonClick(this.data)
                        EventBus.$emit('btnChon_ButtonClick', this.data)
                      }

                    }
                })
            }
        },
      editSettings: { allowEditing: true, mode: 'Batch'},
      selectionSettings: {  type: 'Single', checkboxOnly: true },
      Tag: "0",
      Text: {
        tsbtnTraLai: "Trả phiếu"
      },
      ReadOnly: {
        cbxTuNgay: false
      },
      gridDanhSachPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
      gridLichSuVatTuPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
      grcDS_VT_DQTPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
      grcChuaDuyetPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
      arrayImportFile: {
          filexcel:null,
          ok: false,
          dsImportExcel: [],
          dataExcel: [],
          ds_file: []
        },
        
        
      toggleThongTin: false,
      tvVatTu: []
    };
  },
  methods: {
    ...mapActions("tonghopdanhsachthuhoi", actionName),
    ...mapMutations("tonghopdanhsachthuhoi", mutationName),
    
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    gridDanhSachPageHandler(e){
      this.gridDanhSachPage.page = e.pageIndex
      this.gridDanhSachPage.maxSize = e.pageSize
    },
    gridLichSuVatTuPageHandler(e){
      this.gridLichSuVatTuPage.page = e.pageIndex
      this.gridLichSuVatTuPage.maxSize = e.pageSize
    },
    grcDS_VT_DQTPageHandler(e){
      this.grcDS_VT_DQTPage.page = e.pageIndex
      this.grcDS_VT_DQTPage.maxSize = e.pageSize
    },
    grcChuaDuyetPageHandler(e){
      this.grcChuaDuyetPage.page = e.pageIndex
      this.grcChuaDuyetPage.maxSize = e.pageSize
    },
    
    
    
    list_to_tree(list) {
      var map = {}, node, roots = [], i;
      
      for (i = 0; i < list.length; i += 1) {
        map[list[i].VATTU_ID] = i; // initialize the map
        list[i].children = []; // initialize the children
      }
      
      for (i = 0; i < list.length; i += 1) {
        node = list[i];
        if (node.VATTU_CHA_ID !== "") {
          // if you have dangling branches check that map[node.parentId] exists
          list[map[node.VATTU_CHA_ID]] != null ? list[map[node.VATTU_CHA_ID]].children.push(node) : ''
        } else {
          roots.push(node);
        }
      }
      return roots;
    },
    async Load_Cbo(){
      // try
      // {
          // emptyEditor = new RepositoryItem();
          // grcDS_VT_DQT.RepositoryItems.Add(emptyEditor);
          this.Enabled.cboKho = false

          // Combo đơn vị
          if (this.tagForm == "0"){
              this.cboDonVi = await this.getDSDonVi({
                "nhanVienId" : this.$auth.getNhanVienID(),
                "listDVId" : [this.LOAI_DV.TRAM_VT]
              })
          }

          if (this.tagForm == "2"){
              this.cboDonVi = await this.getDSDonVi({
                "nhanVienId" : this.$auth.getNhanVienID(),
                "listDVId" : [this.LOAI_DV.PHONG_BANHANG_KV]
              })
          }

          // Combo kho
          let q = await this.getDSKhoTheoNV({
            nhanVienId: this.$auth.getNhanVienID(),
            donViId: this.$auth.getDonViID()
          })
          if (q.length > 0){
              var kho = q.filter(l => l["LOAIKHO_ID"].toString() == this.LOAI_KHO.KHO_TOTRUONG.toString());
              if (kho.length > 0){
                this.cboKho = kho
                if (q.length != 0){
                    this.params.p_Kho = "";
                }
              }
          }

          let kh = await this.getDSKhoKhachHang({nhanVienId: this.$auth.getNhanVienID()})
          if (kh.length != 0){
            this.cboKhoKH = kh
            this.params.p_KhoKH = this.cboKhoKH[0].khoId 
          }

          // Combo Loại nhóm
          let str = [
            {NHOM_ID: 0, TEN_NHOM: "Tất cả"},
            {NHOM_ID: 1, TEN_NHOM: "Thi công"},
            {NHOM_ID: 2, TEN_NHOM: "Báo hỏng"},
            {NHOM_ID: 3, TEN_NHOM: "Cải tạo bảo dưỡng PTM"},
          ]
          this.cboLoaiNhom = str
          
          // Combo dịch vụ vật tư
          this.cboDichVuVT = await this.getDSDichVuVienThong({})
          
          // COmbo trạng thái
          let str_tt = [
            {STATUS: 1,TRANG_THAI: "Chờ duyệt mã"},
            {STATUS: 2,TRANG_THAI: "Đã duyệt"},
            {STATUS: 4,TRANG_THAI: "Hoàn thành"},
          ]
          this.cboTrangThai = str_tt

          // Tìm kiếm VT
          this.cboTimKiem = await this.getDSVatTuTimKiem({vatTuId: -1})
      // }
      // catch (error){
      //     this.$toast.error("Lỗi khi lấy danh sách Combo: " );
      // }
    },
    async Load_Cbo_HDPL(){
      // Lấy hợp đồng thi công
      if (this.vnhom_id == 0){
        this.params.p_DichVuVT = 0;
        let str = [
          {LOAIHD_ID: 0, TEN_LOAIHD: "Tất cả"}
        ]
        this.cboLoaiHD = str
        this.params.p_LoaiHD = 0;
      }
      if (this.vnhom_id == 1){
        this.cboLoaiHD = await this.getDanhMucHopDong({})
        this.params.p_LoaiHD = 0;
      }

      // Lấy hợp đồng báo hỏng
      if (this.vnhom_id == 2){
        let str = [
          {LOAIHD_ID: 100, TEN_LOAIHD: "Xử lý sự cố"}
        ]
        this.cboLoaiHD = str
        this.params.p_LoaiHD = 100
      }
    },
    //setbutton Cap mã VT
    async SetButton(kieu) {
      this.Enabled.tsbtnThemVT = false;
      //Bat dau
      if (kieu == -1) {
          this.Enabled.tsbtnThemVT = false;
      }
      // Hủy
      if (kieu == 3) {
          // tvVatTu.ExpandAll();
          // tvVatTu.CollapseAll();
          this.Enabled.tsbtnThemVT = true;
      }
    },
    async Hien_cot(gridcol, ten_col){
      if (this.dtthuebao.length && this.dtthuebao[0].hasOwnProperty(ten_col)){
          this.Visible[gridcol] = true;
          // gridcol.VisibleIndex = vis_col;
      }
    },
    async LoadThongTinThueBao(kieu){
        // try
        // {
          this.loading(true)
            let dv = parseInt(this.params.p_DichVuVT);
            let sSomay = "";
            if (this.tk == ""){
              sSomay = "" 
            }
            else{
              sSomay = this.params.p_txtSoThueBao
            }

            let sDATE = "";
            let eDATE = "";
            if (this.params.p_cbxTuNgay){
                sDATE = this.f_DateToString(this.params.p_dtpTuNgay,'DD/MM/YYYY')
                eDATE = this.f_DateToString(this.params.p_dtpDenNgay,'DD/MM/YYYY')
            }

            this.grcDS_VT_DQT = [];

            if (this.tagForm == "2"){
              this.dtthuebao = await this.getDSThueBaoThuHoi({
                "nhomId": this.vnhom_id,
                "dichVuVtId": dv,
                "loaiHdId": this.vloaihd_id,
                "maTb": sSomay,
                "tuNgay": sDATE,
                "denNgay": eDATE,
                "nhanVienId": this.$auth.getNhanVienID(),
                "status": 1,
                "donViId": this.$auth.getDonViID(),
                "kieu": this.vttvt_id                            
              })
            }
            else{
              this.dtthuebao = await this.LAY_DS_THUHOI_QT({
                 "nhomId": this.vnhom_id,
                "dichVuVtId": dv,
                "loaiHdId": this.vloaihd_id,
                "maTb": sSomay,
                "tuNgay": sDATE,
                "denNgay": eDATE,
                "nhanVienId": this.$auth.getNhanVienID(),
                "status": 1,
                "donViId": this.$auth.getDonViID(),
                "kieu": this.vttvt_id,
                "quyetToanId": this.quyettoan_id                 
              })
            }

            // Hiện cột từng tỉnh
            await this.Hien_cot('colGhiChu', "GHICHU")
            await this.Hien_cot('colTenTrangBi', "TENTRANGBI")
            await this.Hien_cot('colMucuoc', "MUCUOC")
            await this.Hien_cot('colNN', "NGUYEN_NHAN")
            
            if (this.params.p_DonVi != null && this.params.p_DonVi != "" ){
                let q = this.dtthuebao.filter(l => l["DONVI_GIAO_ID"].toString() == this.params.p_DonVi.toString());
                if (q.length != 0){
                  // gridviewDanhSach.FocusedRowHandle = GridControl.AutoFilterRowHandle;
                  // gridviewDanhSach.ClearColumnsFilter();
                  this.gridDanhSach = q

                  // Hiển thị ds vật tư
                  await this.LAY_DS_NHAPKHO()
                  await this.LAY_DS_VTKHONGMA()
                }
                else{
                  // tìm kiếm;
                  if (kieu == 1){
                      this.$toast.error("Không tìm thấy kết quả");
                      this.gridDanhSach = []
                      this.gridLichSuVatTu = []
                      this.grcChuaDuyet = []
                  }
                  else{
                      this.gridDanhSach = []
                      this.gridLichSuVatTu = []
                      this.grcChuaDuyet = []
                  }
                }
            }
            else{
              if (this.dtthuebao.length != 0){
                  // gridviewDanhSach.FocusedRowHandle = GridControl.AutoFilterRowHandle;
                  // gridviewDanhSach.ClearColumnsFilter();
                  this.gridDanhSach = this.dtthuebao;

                  // Hiển thị ds vật tư
                  await this.LAY_DS_NHAPKHO();
                  await this.LAY_DS_VTKHONGMA();

              }
              else{
                  this.gridDanhSach = []
                  this.gridLichSuVatTu = []
                  this.grcChuaDuyet = []
              }
            }
            this.loading(false)
        // }
        // catch (error)
        // {
        //     if (error.includes("find"))
        //         this.$toast.error("Không tim thấy kết quả");
        //     else
        //         this.$toast.error(error);
        //     this.gridDanhSach = [];
        //     this.gridLichSuVatTu = [];
        //     this.grcChuaDuyet = [];
        // }
    },
    async TimKiem(){
        this.ttvt_goc = parseInt(this.params.p_TrangThai)
        await this.LoadThongTinThueBao(1);
    },
    async LayDataRow(){
        await this.Lay_DS_TBICAP();
    },
    async Lay_DS_TBICAP(){
      this.loading(true)
        let kieu = 1;
        if(this.vttvt_id == 4) {
          kieu=4;
        }
        // hardcode fake data
        let dsLsvt = await this.getDSLichSuVatTuThuHoi({
          "phieuId": this.phieu_cha_id, //210890272,
          "baoHongId": this.baohong_id,//0,
          "hdThueBao": this.hdtb_id,//2015103,
          "kieuThietBi": 3,///3 la thu hoi
          "status": kieu //2 // DHSX đang để vậy
        })
        
        this.DS = [] //dsLsvt.Clone();
        this.gridLichSuVatTu = dsLsvt
        this.gridLichSuVatTu.forEach(a=>{
          a.SOLUONG.toString() ? a.SOLUONG = parseInt(a.SOLUONG) : a.SOLUONG = 0
        })
      this.loading(false)
    },
    async LAY_ALL_DS(kieu){
        // lay danh sach duyet
        if (kieu == 1) {
            if (this.gridDanhSach.length == 0){ return }
            
            var xdoc = []
            // var ds = xdoc.CreateElement("ds");
            // xdoc.AppendChild(ds);

            for (var item of this.gridDanhSach){
                let ds = {}
                // gridviewDanhSach.SetRowCellValue(i, gridviewDanhSach.Columns["ISCHECK"], 1);
                ds.PHIEU_ID = item["PHIEU_CHA_ID"]
                ds.HDTB_ID = item["HDTB_ID"]
                ds.BAOHONG_ID = item["BAOHONG_ID"]
                xdoc.push(ds)
            }

            this.grcChuaDuyet = await this.ThemMoiPhieuVatTu({
              "data" : JSON.stringify(xdoc),//"{'PHIEU_ID':1778915,'HDTB_ID':2313138,'BAOHONG_ID':0}]",
              "status" : this.vttvt_id, //4,
              "kieuTBi": 3,//1
            })
            this.convertData_grcChuaDuyet()
            
            this.DS_nhapk = []
            // grvChuaDuyet.Columns["TEN_VT"].SortOrder = ColumnSortOrder.Ascending;
        }
        else{
            if (this.gridDanhSach.length == 0){
              return;
            }
                
            var xdoc = []
            
            let ds_th = this.gridDanhSach

            for (let i = 0; i < this.gridDanhSach.length; i++){
              let dr = ds_th[i];
              let ds = {}
              // gridviewDanhSach.SetRowCellValue(i, gridviewDanhSach.Columns["ISCHECK"], 1);
              ds.PHIEU_ID = dr["PHIEU_CHA_ID"]
              ds.HDTB_ID = dr["HDTB_ID"]
              ds.BAOHONG_ID = dr["BAOHONG_ID"]
              xdoc.push(ds)                
            }
            this.DS_nhapk = await this.ThemMoiPhieuVatTu({
              "data" : JSON.stringify(xdoc),
              "status" : 1,
              "kieuTBi": 3
            })

            this.grcDS_VT_DQT = this.DS_nhapk
            // grvDS_VT_DQT.Columns["TEN_VT"].SortOrder = ColumnSortOrder.Ascending;
        }
    },
    async LAY_DS_VTKHONGMA(){
        // try{
          this.DS = []
          let dem = 0;
          if (this.gridDanhSach.length == 0 || this.params.p_TrangThai.toString() != "1"){ return }    

          // DS.PrimaryKey = new[] { DS.Columns["PHIEUVT_ID"] };

          let ds_th = this.gridDanhSach;
          var xdoc = [];
          // var ds = xdoc.CreateElement("ds");
          // xdoc.AppendChild(ds);
          for (let item of this.$refs.gridviewDanhSach.getSelectedRecords()){
            let ds = {}
            ds.PHIEU_ID = item["PHIEU_CHA_ID"]
            ds.HDTB_ID = item["HDTB_ID"]
            ds.BAOHONG_ID = item["BAOHONG_ID"]
            xdoc.push(ds)
          }

          this.grcChuaDuyet = await this.ThemMoiPhieuVatTu({
              "data" : JSON.stringify(xdoc),//"{'PHIEU_ID':1778915,'HDTB_ID':2313138,'BAOHONG_ID':0}]",
              "status" : 1,//4,
              "kieuTBi": 3,//1
            })
          this.convertData_grcChuaDuyet()
          // grvChuaDuyet.Columns["TEN_VT"].SortOrder = ColumnSortOrder.Ascending;
          this.DS_nhapk = [];
      // }
      // catch (error){
      //     this.$toast.error(ex.Message);
      // }
    },
    async LAY_DS_NHAPKHO(){
        // try
        // {
            if (this.gridDanhSach.length == 0 || this.params.p_TrangThai.toString() != "2"){ return }

            let dt = this.gridDanhSach;

            var xdoc = [];
                        
            for (let dr of this.$refs.gridviewDanhSach.getSelectedRecords()){
                let ds = {}
                ds.PHIEU_ID = dr["PHIEU_CHA_ID"]
                ds.HDTB_ID = dr["HDTB_ID"]
                ds.BAOHONG_ID = dr["BAOHONG_ID"]
                xdoc.push(ds)
            }

            let temp = await this.ThemMoiPhieuVatTu({
              "data" : JSON.stringify(xdoc),
              "status" : 1,
              "kieuTBi": 3
            })
            this.goc = xdoc;
            this.grcDS_VT_DQT = temp;
            this.DS_nhapk = temp;
        // }
        // catch (error){
        //     this.$toast.error(error);
        // }
    },
    async frmTongHopThuHoi_New_Load(){
        this.Visible.tsbtnVatTu = false
        this.tagForm = "0"
        if (this.Tag != null && this.Tag.toString())
        {
            this.tagForm = this.Tag.toString();
            this.Visible.tsbtnVatTu = true;
            this.Text.tsbtnTraLai = "Hủy phiếu";
        }
        await this.Load_Cbo();
        this.params.p_LoaiNhom = 0;
        await this.cboLoaiNhom_EditValueChanged()
        this.params.p_TrangThai = 1;
        await this.cboTrangThai_EditValueChanged()

        this.Visible.grpVT = false;
        
        // await this.TimKiem();
    },
    async tsbtnTimKiem_Click(){
      try{
        this.loading(true)
        await this.TimKiem();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbtnTraLai_Click(){
      try{

        if (this.gridDanhSach.length == 0)
        {
            this.$toast.error("Không có thuê bao được chọn");
            return;
        }
        let kt = this.gridDanhSach
        var z = this.$refs.gridviewDanhSach.getSelectedRecords()
        if (z.length != 0)
        {
          var xdoc = []

          for (let dr of z){
              let ds = {}
              ds.PHIEU_ID = dr["PHIEU_CHA_ID"]
              ds.HDTB_ID = dr["HDTB_ID"]
              ds.BAOHONG_ID = dr["BAOHONG_ID"]
              ds.KIEUTBI_ID = 1
              xdoc.push(ds)
          }

          let kq_ = await this.KiemTraTraPhieu({
            "data" : JSON.stringify(xdoc)
          })
          if (kq_ == "fail"){
              this.$toast.error("Không thể trả phiếu khi đã có vật tư được quyết toàn hoặc tạm ứng");
              return;
          }
        }

        let hoi = "Bạn chắc chắn muốn trả lại không ?";
        if (this.tagForm == "2"){
          hoi = "Bạn chắc chắn muốn hủy phiếu  không ?";
        }

        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm(hoi, {
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

        if (this.params.p_txtGhiChu == "" ){
            if (this.tagForm != "2"){
                this.$toast.error("Bạn hãy nhập lý do trả lại");
                return;
            }
            else{
                this.$toast.error("Bạn hãy nhập lý do hủy phiếu");
                return;
            }
          }
          if (this.ttvt_goc == parseInt(this.params.p_TrangThai)){
            let thongbao = "";
            // gridviewDanhSach.ClearColumnsFilter();
            var dt = this.gridDanhSach;
            let q = this.$refs.gridviewDanhSach.getSelectedRecords()
            if (q.length != 0){
              if (this.tagForm != "2"){
                  // "Trả phiếu "
                  let dem = 0;
                  for (let dr of q){
                      let s = "";
                      let p_id = parseInt(dr["PHIEU_ID"].toString());
                      let hdtb = parseInt(dr["HDTB_ID"].toString());
                      let baohong = parseInt(dr["BAOHONG_ID"].toString());
                      s = await this.TraPhieuQuyetToanVatTu({
                        "phieuId": p_id,
                        "hdTbId": hdtb,
                        "baoHongId": baohong,
                        "ndTra": this.params.p_txtGhiChu,
                        "nhanVienTraId": this.$auth.getNhanVienID(),
                        "nguoiCn": this.$auth.getUserName(),
                        "mayCn": this.$auth.getUserName(),
                        "ipCn": "10.59.90.123"//ttnd.ip
                      })
                      if (s == "ok"){
                        dem += 1;
                      }
                      else{
                          thongbao += s +"\r\n";
                      }
                  }

                  this.$toast.success("Trả lại thành công " + dem + "/" + q.length + " bản ghi \r\n "+thongbao);
                  await this.LoadThongTinThueBao(-1);
                  this.params.p_txtGhiChu = "";
                  
              }
              else if(this.tagForm =="2"){
                  let tdan = []
                  let dem = 0;
                  let tong = 0;
                  var xdoc = []
                  
                  for (let dr of q){
                      let ds = {}
                      ds.PHIEU_ID = dr["PHIEU_ID"]
                      ds.PHIEU_CHA_ID = dr["PHIEU_CHA_ID"]
                      ds.HDTB_ID = dr["HDTB_ID"]
                      ds.BAOHONG_ID = dr["BAOHONG_ID"]
                      xdoc.push(ds)
                  }

                  try
                  {
                      var kq = await this.HuyPhieuQuyetToan({
                        "data" : JSON.stringify(xdoc),
                        "nguoiCN": this.$auth.getUserName(),
                        "mayCN": this.$auth.getUserName(),//ttnd.may_cn
                        "ipCN": "10.59.90.123",//ttnd.ip
                        "noiDungTra" : "",
                        "nhanVienTraId": this.$auth.getNhanVienID(),
                        "tagForm": "2"
                      })

                      if (!kq.toString()){
                        this.$toast.error("Không lấy dược dữ liệu trả ra");
                      }
                      else if (kq.toString() == "ok"){
                        this.$toast.success("Hủy " + q.length + " phiếu thành công.");
                      }
                      else{
                          this.$toast.error(kq.toString());
                      }
                  }
                  catch (error){
                      this.$toast.error("Có lỗi khi hủy phiếu " + error);
                  }
              }
            }
            else{
                this.$toast.error("Bạn chưa chọn thuê bao để trả lại");
            }
          }
          else{
              this.$toast.error("Bạn đã thay đổi giá trị khác so với thông tin tìm kiếm ban đầu");
          }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
        
        
    },
    async KiemTra_VT(d){
        // try{
            let dston = [];
            for (let dr of d){
                if (dr["SERIAL"] != ""){
                  let s = await this.KiemTraVatTuNhapKho({
                    serial: dr["SERIAL"],
                    vatTuId: dr["VATTU_ID"]
                  })
                  if (s.toString() != "1"){
                      dston.push(s)
                  }
                }
            }

            if (dston.length > 0){
              await this.$bvModal.msgBoxConfirm("Có " + dston.length+ " serial đang sai mã vật tư so với chứng từ.Bạn có muốn xuất file kiểm tra không?", {
                "okVariant": 'success',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy',
                "centered": true,
                })
                .then(async(value) => {
                    if (value){
                      await this.SaveXlsAndOpenFile("Serilsaima", "", "", dston);
                      return "0"
                    }
                    else{
                      return "0"
                    }
                })
                .catch(err => {
                    // An error occurred
                })
            }
            return "1";
        // }
        // catch (error){
        //     return error.toString();
        // }
    },
    async tsbtnNhapKho_Click(){
      try{
        this.loading(true)
        // Lấy mục đích & nội dung
        if (this.DS_nhapk.length == 0){
          this.$toast.error("Chưa có thuê bao nào được chọn");
          return;
        }
        var md = await this.getDSMucDichBaoHong({maMucDich: "1.4"})
        if (md.length == 0){
          this.$toast.error("Mục đích 1.4 không có");
          return;
        }
        
        var mucdich_id = parseInt(md[0].mucDichId.toString());
        // quy cmt vi chua xai
        // var noidung = md[0].mucDich.toString();
        
        // Lấy kho trung tâm
        if (this.params.p_Kho == null || this.params.p_Kho == ''){
          this.$toast.error("Bạn phải chọn kho để nhập");
          return;
        }
        // quy cmt vi chua xai
        // var kho_tt = parseInt(this.params.p_Kho.toString());

        // Lấy kho khách hàng

        if (this.params.p_KhoKH == null || this.params.p_KhoKH == ''){
          this.$toast.error("Bạn phải chọn kho KH");
          return;
        }
      
        var kho_kh_ = parseInt(this.params.p_KhoKH.toString());
        
        let s = await this.KiemTra_VT(this.DS_nhapk);
        if (s=="0") { return }     
        if(s != "1" && s!="0"){
          this.$toast.error(s);
          return;
        }
        
        let dt = [];
        if (this.gridDanhSach.length != 0){
          dt = this.gridDanhSach;

          var q = this.$refs.gridviewDanhSach.getSelectedRecords()
          if (q.length != 0){
            let ds_chon_phieu  = q
            let js_vtquyettoan  = JSON.stringify(this.DS_nhapk)
            let js_phieuquyettoan  = JSON.stringify(ds_chon_phieu)

            this.thamso_md = await this.CHECK_IS_EXIST({maThamSo: "TS_KIEMTRA_SL_PHIEUVT_QUYETTOAN"})
            if ( this.thamso_md.length){

              var kqkt  = await this.KT_SL_PHIEUVT_QUYETTOAN({
                quyetToanId: this.quyettoan_id,
                dsVatTu: this.DS_nhapk,
                dsPhieu: ds_chon_phieu
              })

              if (kqkt.toString() != "1"){
                this.$toast.error(kqkt)
                var dtb = await this.DS_LECH_PHIEUVT_QUYETTOAN({
                  quyetToanId: this.quyettoan_id,
                  dsVatTu: this.DS_nhapk,
                  dsPhieu: ds_chon_phieu
                })

                // var fr = frmDSLechSLDeNghi
                // fr.dt = dtb
                // fr.ShowDialog
                return

              }

            }

            var frm = this.$refs.frmNhapKhoTH
            this.loading(true)
            frm.giaophieu = 0;
            frm.ten_kho = this.cboKho.find(a=>a.KHO_ID && a.KHO_ID.toString() == this.params.p_Kho.toString()).TEN_K_NEW || ''
            frm.vkho_id = parseInt(this.params.p_Kho.toString());
            frm.kho_kh = kho_kh_;
            frm.dt_kho = this.DS_nhapk;
            console.log('==>', this.DS_nhapk)
            frm.dstb = q;
            frm.xdoc_goc = this.goc;
            frm.mucdich_id = mucdich_id;
            await frm.frmNhapKhoTH()
            await frm.frmNhapKhoTH_Load()
            await frm.ShowDialog();
            this.loading(false)
          }
        }else{
          this.$toast.error("Không có thuê bao được chọn");
        }
      }catch(e){
      this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async XacNhan_frmNhapKhoTH(){
      var frm = this.$refs.frmNhapKhoTH
      if (frm.xn == "1"){
          await this.LoadThongTinThueBao(-1);
          this.$toast.success("Nhập kho thành công");
          this.ctid = frm.ctid;
          if (this.$auth.getMaTinh() == "HCM"){
              // var fr_m = this.$refs.frmNhapXuatVT_v2;
              // fr_m.Tag = 1;
              // fr_m.Shown += frmNhapXuatVT_Shown;
              // fr_m.ShowDialog();
              this.$router.push({path: 'NhapXuatVT_v2', query: { Tag: 1 }})
          }
          else{
              // var fr_m = this.$refs.frmNhapXuatVT;
              // fr_m.Tag = 1;
              // fr_m.Shown += frmNhapXuatVT_Shown;
              // fr_m.ShowDialog();
              this.$router.push({path: 'NhapXuatVT', query: { Tag: 1 }})
          }
      }
    },
    async frmNhapXuatVT_Shown(){
        if (this.$auth.getMaTinh() == "HCM"){
            var frm = this.$refs.frmNhapXuatVT_v2;
            if (frm){
                // var ctrls = frm.Controls.Find("gridChungTu", true);
                // if (ctrls.Length != 0){
                //     var grcChungTu = ctrls[0] as GridControl;
                //     if (grcChungTu != null)
                //     {

                //         var grvChungTu = grcChungTu.MainView as GridView;
                //         if (grvChungTu != null)
                //         {
                //             for (var i = 0; i < grvChungTu.RowCount; i++)
                //             {
                //                 var sophieu_2 = grvChungTu.GetRowCellValue(i, "CHUNGTU_ID");
                //                 if (Equals(ctid, sophieu_2))
                //                 {
                //                     grvChungTu.FocusedRowHandle = i;
                //                     break;
                //                 }
                //             }
                //         }
                //     }
                // }
            }
        }
        else{
            var frm = this.$refs.frmNhapXuatVT;
            if (frm != null){
                // var ctrls = frm.Controls.Find("gridChungTu", true);
                // if (ctrls.Length != 0){
                //     var grcChungTu = ctrls[0] as GridControl;
                //     if (grcChungTu != null)
                //     {

                //         var grvChungTu = grcChungTu.MainView as GridView;
                //         if (grvChungTu != null)
                //         {
                //             for (var i = 0; i < grvChungTu.RowCount; i++)
                //             {
                //                 var sophieu_2 = grvChungTu.GetRowCellValue(i, "CHUNGTU_ID");
                //                 if (Equals(ctid, sophieu_2))
                //                 {
                //                     grvChungTu.FocusedRowHandle = i;
                //                     break;
                //                 }
                //             }
                //         }
                //     }
                // }
            }
        }
    },
    // async tsbtnThoat_Click(){
    //     Close();
    // }
    async tsbtnGhiLai_Click(){
      try{
        if (this.params.p_KhoKH == null || this.params.p_KhoKH == ''){
            this.$toast.error("Bạn phải chọn kho KH");
            return;
        }
        
        this.kho_kh = parseInt(this.params.p_KhoKH.toString());
        
        if (this.vttvt_id == 1){
            // Duyet chua ma

            if (this.grcChuaDuyet.length != 0){
                var dt = this.grcChuaDuyet;

                let DSloi = [];
                if (this.grcChuaDuyet.length != 0){
                    if (dt.length != 0){
                      var q = dt.filter(l => l["VATTU_ID"] != null && l["VATTU_ID"].toString());
                      if (q.length != 0){
                        dt = q;
                      }

                      var isBoxConfirm = false;
                      await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn duyệt mã các vật tư này không?", {
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

                      let xdoc = []
                              
                      for (let item of dt){
                        let ds = {}
                        ds.PHIEUVT_ID = item["PHIEUVT_ID"]
                        ds.VATTU_ID = item["VATTU_ID"]
                        ds.KHO_KH = this.kho_kh.toString();
                        xdoc.push(ds)
                      }
                      var s = await this.CapMaVatTuPhieu_V2({
                        "data": JSON.stringify(xdoc),
                        "nguoiCN": this.$auth.getUserName(),
                        "mayCN": this.$auth.getUserName(),//ttnd.may_cn
                        "ipCN": "10.59.90.123"//ttnd.ip
                      })

                      if (s.toString() != "1"){
                        this.$toast.error(s);
                      }
                      this.grcChuaDuyet = [];
                      this.gridLichSuVatTu = [];
                      await this.LoadThongTinThueBao(1);
                        
                    }
                }
                else{
                    this.$toast.error("Không có vật tư đề duyệt mã");
                }
            }
            else{
              this.$toast.error("Chưa chọn thuê bao!");
            }
        }
        if (this.vttvt_id == 2){
          // Update
          var isBoxConfirm = false;
          await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn cập nhật mã các vật tư này không?", {
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

          if (this.grcDS_VT_DQT.length != 0){
            var xdoc = []
            for (let dr of this.grcDS_VT_DQT){
                // if (dr["ISUPDATE"].toString() == "1"){
                  let ds = {}
                  ds.PHIEUVT_ID = dr["PHIEUVT_ID"]
                  ds.THIETBI_ID = dr["THIETBI_ID"]
                  ds.VATTU_ID = dr["VATTU_ID"]
                // }
            }
            // bangts.showLoading(this, 0);
            await this.CapNhatMaPhieuVatTu({data: JSON.stringify(xdoc)}).then(response => {
              // message thông báo cập nhập mvt thành công
              if (response == 1) {
                this.$toast.success("Cập nhật mã vật tư thành công");  
              }
            })
            await this.LAY_DS_NHAPKHO();
          
          }
          else{
              this.$toast.error("Không có danh sách vật tư để cập nhật mã vật tư");
          }                

        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    hierarhySort(hashArr, key, result) {
    
      if (hashArr[key] == undefined) return;
      var arr = hashArr[key].sort(this.hierarchySortFunc);
      for (var i=0; i<arr.length; i++) {
        result.push(arr[i]);
        this.hierarhySort(hashArr, arr[i].VATTU_ID, result);
      }
    
      return result;
    },
    async processTreeGrid(){
      // xử lí VATTUID = VATTU_CHA_ID = "" => cycle
      await this.preProcessTreeGrid()

      // Sắp xếp dataSource = array sibling thay vì list tree
      function hierarhySort(hashArr, key, result) {
        if (hashArr[key] == undefined) return;
        let arr = hashArr[key]
        for (let i=0; i<arr.length; i++) {
          result.push(arr[i]);
          hierarhySort(hashArr, arr[i].VATTU_ID, result);
        }
      
        return result;
      }

      let arr = this.tvVatTu
      let hashArr = {};

      for (let i=0; i<arr.length; i++) {
        if (hashArr[arr[i].VATTU_CHA_ID] == undefined || hashArr[arr[i].VATTU_CHA_ID] == "") hashArr[arr[i].VATTU_CHA_ID] = [];
        hashArr[arr[i].VATTU_CHA_ID].push(arr[i]);
      }
      
      let result = hierarhySort(hashArr, "", []);
      return result
    },
    async tsbtnThemVT_Click(){
      if (this.Visible.grpVT == false){
          // Resize(1);
          this.loading(true)
          this.tvVatTu = await this.processTreeGrid()
          console.log(this.tvVatTu)
          // grpVT.Width = Screen.PrimaryScreen.Bounds.Width/2-100;
          this.Visible.grpVT = true;
          this.SetButton(-1);
          this.loading(false)
      }
    },
    async preProcessTreeGrid (){
      this.tvVatTu = await this.getDSVatTuDanhMuc()
      this.tvVatTu.forEach(item => {
        if (item.VATTU_CHA_ID === "" && item.VATTU_ID !== ""){
          item['VATTU_CHA_ID'] = -1
        }
        if (item.VATTU_ID === ""){
          item.VATTU_ID = -1
        }

      })
    },
    async vbtnClose_Click(){
      if (this.Visible.grpVT){
          this.Visible.grpVT = false;
          this.SetButton(3);
      }
      else{
          this.SetButton(0);
      }
    },
    async vbtn_Ref_MouseClick(){
        this.cboTimKiem = await this.getDSVatTuTimKiem({vatTuId: -1})
        // this.tvVatTu = await this.getDSVatTuDanhMuc({})
        this.tvVatTu = await this.processTreeGrid()
    },
    async cboLoaiNhom_EditValueChanged(){
        await this.Load_Cbo_HDPL();
    },
    async cboTrangThai_EditValueChanged(){
      try{
        this.loading(true)
        this.params.p_cbxTuNgay = false;
        //Thiết bị thu hồi chưa có trong kho cần duyệt lại
        if (this.params.p_TrangThai.toString() == "1") {
            this.Visible.TabCapMa = true;
            this.Visible.TabNhapKho = false;
            this.Visible.TabThuHoi = true;
            this.Visible.tsbtnNhapKho = false;
            this.Visible.tsbtnGhiLai = true;
            this.Visible.tsbtnTraLai = true;
            this.Visible.tsbtnThemVT = true;
        }
        //Nếu có rồi 
        else if (this.params.p_TrangThai.toString() == "2"){
            this.Visible.TabCapMa = false;
            this.Visible.TabNhapKho = true;
            this.Visible.TabThuHoi = true;
            this.Visible.tsbtnNhapKho = true;
            this.Visible.tsbtnGhiLai = true;
            this.Visible.tsbtnTraLai = true;
            this.Visible.tsbtnThemVT = true;
        }
        else{
            this.params.p_cbxTuNgay = true;
            this.ReadOnly.cbxTuNgay = true;
            this.params.p_dtpTuNgay = new Date((new Date()).getTime() - 3*24*60*60*1000)
            this.Visible.TabCapMa = false;
            this.Visible.TabNhapKho = true;
            this.Visible.TabThuHoi = true;
            this.Visible.tsbtnNhapKho = false;
            this.Visible.tsbtnGhiLai = false;
            this.Visible.tsbtnTraLai = false;
            this.Visible.tsbtnThemVT = false;
        }
        await this.TimKiem();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async gridviewDanhSach_FocusedRowChanged(row){
      try{
        this.loading(true)
        if (!row){
            this.gridLichSuVatTu = [];
            this.params.p_txtSoThueBao = "";
            return;
        }
        if (row){
            this.gridviewDanhSach_FocusedRow = row
            await this.LayDataRow();
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async ckbCheck_CheckedChanged(){
        await this.LAY_DS_NHAPKHO();
        await this.LAY_DS_VTKHONGMA();
    },
    async splitterControl1_MouseUp(){
        // Rectangle ManHinh = Screen.PrimaryScreen.Bounds;
        // grpVT.Width = ManHinh.Width - Cursor.Position.X;
        // if (grpVT.Width < 500)
        // {
        //     grpVT.Width = grpVT.Width;
        // }
        // if (grpVT.Width >= ManHinh.Width / 2)
        // {
        //     grpVT.Width = ManHinh.Width / 2;
        // }
    },
    async ckbBox_CheckedChanged(){
        // try
        // {
            var dt = this.grcChuaDuyet
            var a = this.$refs.grcChuaDuyet.getSelectedRecords()
            if (a.length != 0 && this.Visible.grpVT == false){
                this.SetButton(3);
            }
            else if (a.length != 0 && this.Visible.grpVT){
                this.SetButton(-1);
            }
            else if (a.length == 0 && this.Visible.grpVT){
                this.Visible.grpVT = false;
                this.Enabled.tsbtnThemVT = true;
            }
            else if (a.length == 0){
                this.Enabled.tsbtnThemVT = true;
            }
        // }
        // catch (error){
        //     this.$toast.error(error);
        // }
    },
  async btnChon_ButtonClick(node){
      // try{
        this.tvVatTu_FocusedNode = node
          if (!this.tvVatTu_FocusedNode["DVITINH_ID"].toString() ){
              this.$toast.error("Không thể chọn vật tư không có đơn vị tính");
              return;
          }
          if (this.tvVatTu_FocusedNode["DVITINH_ID"].toString() == "15"){
              this.$toast.error("Đơn vị tính của vật tư này không thể chọn");
              return;
          }

          this.vattu_id = parseInt(this.tvVatTu_FocusedNode["VATTU_ID"]);


          this.ten_vt = this.tvVatTu_FocusedNode["TEN_VT"].toString();
          let ma_vt = this.tvVatTu_FocusedNode["MA_VT"].toString();
          if (this.vttvt_id == 1){
              if (this.grcChuaDuyet.length != 0){
                  var dt = this.$refs.grvChuaDuyet.getSelectedRecords()

                  var q = this.$refs.grvChuaDuyet.getSelectedRecords()
                  if (q.length != 0){
                      await this.$bvModal.msgBoxConfirm("Bạn có chọn vật tư này không ?", {
                        "okVariant": 'success',
                        "okTitle": 'Đồng ý',
                        "cancelTitle": 'Hủy',
                        "centered": true,
                        })
                        .then(async(value) => {
                            if (value){
                              for (let index in this.grcChuaDuyet){
                                let item = this.grcChuaDuyet[index]
                                // edit datasource
                                if (q.map(a=>a.VATTU_ID).includes(item.VATTU_ID)){
                                  if (this.grcChuaDuyet[index]["SERIAL"].toString()){
                                    if (this.tvVatTu_FocusedNode["LOAITBI_ID"].toString() != "2"){
                                        this.$toast.error("Vật tư này co serial bạn không thể thay thế vật tư không có serial được");
                                        return;
                                    }
                                  }
                                  if (!this.grcChuaDuyet[index]["SERIAL"].toString()){
                                      if (this.tvVatTu_FocusedNode["LOAITBI_ID"].toString() == "2"){
                                          this.$toast.error("Vật tư này không có serial bạn không thể thay thế bằng vật tư có serial được");
                                          return;
                                      }
                                  }
                                  this.grcChuaDuyet[index]["TEN_VT"] = ma_vt + " - " + this.ten_vt;
                                  this.grcChuaDuyet[index]["VATTU_ID"] = this.vattu_id;

                                  if (this.tvVatTu_FocusedNode["DVITINH_ID"].toString()){
                                      this.dvitinh_id = parseInt(this.tvVatTu_FocusedNode["DVITINH_ID"]);
                                      this.grcChuaDuyet[index]["DVI_TINH"] = this.tvVatTu_FocusedNode["DVITINH"].toString();
                                      this.grcChuaDuyet[index]["DVITINH_ID"] = this.dvitinh_id;
                                  }
                                  else{
                                    this.grcChuaDuyet[index]["DVI_TINH"] = "";
                                  }
                                  this.grcChuaDuyet[index]["ISUPDATE"] = "1";
                                  this.grcChuaDuyet[index]["ISCHECK"] = "0";
                                  this.$refs.grvChuaDuyet.grid.refreshColumns()
                                  console.log('BBB',this.grcChuaDuyet)
                                }
                                
                                
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
                      this.$toast.error("Chưa chọn vật tư để thay đổi mã vật tư");
                  }
              }
              else{
                this.$toast.error("Không có vật tư trong danh sách");
              }
          }
          else{
              if (this.grcDS_VT_DQT.length != 0){
                  var dt = this.$refs.grvDS_VT_DQT

                  var q = this.$refs.grvDS_VT_DQT.getSelectedRecords()
                  if (q.length!= 0){
                      await this.$bvModal.msgBoxConfirm("Bạn có chọn vật tư này không ?", {
                        "okVariant": 'success',
                        "okTitle": 'Đồng ý',
                        "cancelTitle": 'Hủy',
                        "centered": true,
                        })
                        .then(async(value) => {
                            if (value){
                              for (let index in this.grcChuaDuyet){
                                let item = this.grcChuaDuyet[index]
                                // edit datasource
                                if (q.map(a=>a.VATTU_ID).includes(item.VATTU_ID)){
                                   this.grcChuaDuyet[index]["TEN_VT"] = ma_vt + " - " + this.ten_vt
                                   this.grcChuaDuyet[index]["VATTU_ID"] = this.vattu_id
                                  if (this.tvVatTu_FocusedNode["DVITINH_ID"].toString()){
                                      dvitinh_id = parseInt(this.tvVatTu_FocusedNode["DVITINH_ID"]);
                                       this.grcChuaDuyet[index]["DVI_TINH"] = this.tvVatTu_FocusedNode["DVITINH"].toString();
                                       this.grcChuaDuyet[index]["DVITINH_ID"] = this.dvitinh_id;
                                  }
                                  else{
                                     this.grcChuaDuyet[index]["DVI_TINH"] = ""
                                  }
                                   this.grcChuaDuyet[index]["ISUPDATE"] = "1"
                                   this.grcChuaDuyet[index]["ISCHECK"] = "0"
                                   this.$refs.grvChuaDuyet.grid.refreshColumns()
                                   console.log('AAA',this.grcChuaDuyet)
                                }
                                
                                
                              }
                            }
                        })
                        .catch(err => {
                            // An error occurred
                        })
                  }
                  else{
                      this.$toast.error("Chưa chọn vật tư để thay đổi mã vật tư");
                  }
              }
              else{
                this.$toast.error("Không có vật tư trong danh sách");
              }
          }
      // }
      // catch (error)
      // {
      //     this.$toast.error(ex.Message);
      // }
  },
  async grvChuaDuyet_RowStyle(){
    // let check = grvChuaDuyet.GetRowCellValue(e.RowHandle, "ISUPDATE").toString();
    // if (check == "1"){
    //     e.Appearance.BackColor = Color.LightYellow;
    //     e.Appearance.BackColor2 = Color.LightYellow;
    // }
      
  },
  async vbtn_Ref_Click(){
  },
  async tsbtnHuyQT_Click(){
      //Kiểm tra xem có tồn tại phieuvt_ nào đã gán vơi
  },
  async gridDanhSach_KeyDown(e){
      // try{
          if (e.KeyCode == Keys.Delete){
              let rows = []
              // Add the selected rows to the list.
              // for (let i = 0; i < gridviewDanhSach.DataRowCount; i++){
              //     rows.Add(gridviewDanhSach.GetDataRow(i));
              // }
              rows = this.gridDanhSach
              // try{
                  for (let i = 0; i < rows.Count; i++){
                      let row = rows[i]
                      if (row["ISCHECK"].toString() == "1"){
                          row["ISCHECK"] = 0;

                      }
                      else{
                          row["ISCHECK"] = 0;
                      }

                  }
              // }
              // finally{
              //     gridviewDanhSach.EndUpdate();
              // }
              this.DS_nhapk = [];
              this.grcDS_VT_DQT = [];
              this.grcChuaDuyet = [];

          }
          if (e.KeyCode == Keys.Insert){
              await this.LAY_ALL_DS(vttvt_id);
          }
          if (e.KeyCode == Keys.Space){
              // Create an empty list.
              let rows = []
              // Add the selected rows to the list.
              // for (let i = 0; i < gridviewDanhSach.SelectedRowsCount; i++){
              //     if (gridviewDanhSach.GetSelectedRows()[i] >= 0)
              //         rows.Add(gridviewDanhSach.GetDataRow(gridviewDanhSach.GetSelectedRows()[i]));
              // }
              rows = this.$refs.gridviewDanhSach.getSelectedRecords()
              // try{
                  // gridviewDanhSach.BeginUpdate();
                  for (let i = 0; i < rows; i++){
                      let row = rows[i]
                      if (row["ISCHECK"].toString() == "1"){
                          row["ISCHECK"] = 0;
                      }
                      else{
                          row["ISCHECK"] = 1;
                      }

                  }
              // }
              // finally{
              //     gridviewDanhSach.EndUpdate();
              // }
              this.DS_nhapk = [];
              // gridviewDanhSach.CloseEditor();
              await this.LAY_DS_NHAPKHO()
              await this.LAY_DS_VTKHONGMA()
          }
      // }
      // catch (error){
      //     // bangts.closeLoading(this);
      //     this.$toast.error(error);
      //     ttvt_goc = parseInt(this.params.p_TrangThai.toString());
      //     await this.LoadThongTinThueBao(1);
      // }
  },
  async tsbtnSuaPVT_Click(){

  },
  async cboDonVi_EditValueChanged(){
    try{
      this.loading(true)
      if (!this.params.p_DonVi.toString()){

          if (this.$auth.getMaTinh() == "TNH"){
            let q = await this.getDSKhoTheoNV({
              nhanVienId: this.$auth.getNhanVienID(),
              donViId: this.$auth.getDonViID()
            })
            if (q.length > 0){
              this.Enabled.cboKho = true;
              var kho = q.filter(l => l["LOAIKHO_ID"].toString() == this.LOAI_KHO.KHO_TOTRUONG.toString() && l["TRANGTHAI"].toString() == "1");
              if (kho.length > 0){
                this.cboKho = kho
                this.params.p_Kho = parseInt(kho[0]["KHO_ID"].toString());

              }
            }
          }
          else{
            this.params.p_Kho = null;
            cboKho.Enabled = false;
          }
      }
      else{
          // await this.LoadThongTinThueBao(-1);
          this.Enabled.cboKho = true;
          // Combo kho
          let q = await this.getDSKhoTheoNV({
              nhanVienId: this.$auth.getNhanVienID(),
              donViId: this.$auth.getDonViID()
            })
          if (q.length > 0){
              if (this.$auth.getMaTinh() != "HNI"){
                  var kho = q.filter(l => l["LOAIKHO_ID"].toString() == this.LOAI_KHO.KHO_TOTRUONG.toString() 
                  && l["DONVI_ID"].toString() == this.params.p_DonVi.toString() && l["TRANGTHAI"].toString() == "1");
                  if (kho.length > 0){
                    this.cboKho = kho
                    this.params.p_Kho = parseInt(kho[0]["KHO_ID"].toString());

                    // Combo de nghi
                    var denghi = await this.LAY_DS_DENGHI_HT({
                      donViId: this.params.p_DonVi,
                      nhanVienId: this.$auth.getNhanVienID(),
                      loaiPhieuId: 10
                    })
                    if (denghi.length){
                      this.cboDeNghi = denghi
                      this.params.p_DeNghi = denghi[0].QUYETTOAN_ID
                    }
                    else{
                      this.quyettoan_id = 0
                    }
                    await this.LoadThongTinThueBao(-1);
                    return
                  }
                  else{
                    this.params.p_Kho = "";
                  }
              }
              else
              {
                  var kho = q.filter(l => l["LOAIKHO_ID"].toString() == this.LOAI_KHO.KHO_TOTRUONG.toString() 
                  && l["DONVI_ID"].toString() == this.params.p_DonVi.toString() && l["TRANGTHAI"].toString() == "1");

                  if (kho.length > 0  && this.$auth.getMaTinh() != "HNI"){
                      this.cboKho = kho
                      this.params.p_Kho = parseInt(kho[0]["KHO_ID"].toString());
                      return;
                  }
                  else{
                      var k = await this.getDSKhoDonVi({
                        nhanVienId: this.$auth.getNhanVienID(),
                        donViId: this.$auth.getDonViID()
                      })
                      if (k.length){
                        this.cboKho = k
                        this.params.p_Kho = parseInt(k[0]["KHO_ID"].toString());
                        return;
                      }
                      else{
                        this.cboKho = [];
                      }
                  }
              }
          }
          this.params.p_Kho = "";
      }
    }catch(e){
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false)
    }
  },
  async Resize(i){
    // TableLayoutColumnStyleCollection columnStyles = tableMain.ColumnStyles;
    // if (columnStyles[0].Width == 0 && i==0)
    // {
    //     columnStyles[1].SizeType = SizeType.Percent;
    //     columnStyles[1].Width = 50;
    //     columnStyles[0].SizeType = SizeType.Percent;
    //     columnStyles[0].Width = 50;

    //     vbtnResize.Image = WinUI.Properties.Resources.old_go_previous;
    // }
    // if (columnStyles[0].Width != 0 && i==1)
    // {
    //     columnStyles[1].SizeType = SizeType.Percent;
    //     columnStyles[1].Width = 100;
    //     columnStyles[0].SizeType = SizeType.Percent;
    //     columnStyles[0].Width = 0;
    //     vbtnResize.Image = WinUI.Properties.Resources.new_go_next;

    // }
  },
  async vbtnResize_Click(){
      // TableLayoutColumnStyleCollection columnStyles = tableMain.ColumnStyles;
      // if (columnStyles[0].Width == 0)
      // {
      //     columnStyles[1].SizeType = SizeType.Percent;
      //     columnStyles[1].Width = 50;
      //     columnStyles[0].SizeType = SizeType.Percent;
      //     columnStyles[0].Width = 50;

      //     vbtnResize.Image = WinUI.Properties.Resources.old_go_previous;
      // }
      // else if (columnStyles[0].Width != 0)
      // {
      //     columnStyles[1].SizeType = SizeType.Percent;
      //     columnStyles[1].Width = 100;
      //     columnStyles[0].SizeType = SizeType.Percent;
      //     columnStyles[0].Width = 0;
      //     vbtnResize.Image = WinUI.Properties.Resources.new_go_next;

      // }
  },
  async cboTimKiem_EditValueChanged(){
      let pageSize = this.$refs.Tree_tvVatTu.ej2Instances.pageSettings.pageSize
      // let currentPage = this.$refs['Tree_tvVatTu'].ej2Instances.pageSettings.currentPage 
      let indexs = this.tvVatTu.findIndex(a=>a.VATTU_ID == this.params.p_TimKiem)
      if (indexs != -1) {
        this.$refs.Tree_tvVatTu.expandAll()
        setTimeout(() => {
          this.$refs.Tree_tvVatTu.ej2Instances.pageSettings.currentPage =  parseInt(indexs /pageSize ) + 1
          setTimeout(() => {
            this.$refs.Tree_tvVatTu.selectRow(parseInt(indexs%pageSize) -1 )
          }, 1000);
        }, 300);
      }
  },
  async grvDS_VT_DQT_ShownEditor(){

  },
  async grvDS_VT_DQT_RowStyle(){
      // if (e.RowHandle < 0)
      // {
      //     return;
      // }
      // var a = grvDS_VT_DQT.GetRowCellValue(e.RowHandle, "ISUPDATE");
      // if (a.toString() == "1")
      // {
      //     e.Appearance.BackColor = Color.LightYellow;
      // }
  },
  async grvDS_VT_DQT_CustomRowCellEdit(){
      // if (e.Column.FieldName == "ISCHECK" && e.RowHandle >= 0){
      //     var kt = grvDS_VT_DQT.GetRowCellValue(e.RowHandle, "KTVT");
      //     if (kt != DBNull.Value)
      //         e.RepositoryItem = ckb;
      //     else
      //         e.RepositoryItem = emptyEditor;
      // }
  },
  //bổ sung
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
  //bổ sung
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
          
          await this.Chon_DSTB(JSON.parse(JSON.stringify(result))) 
          
          
          
        }
        reader.readAsBinaryString(this.arrayImportFile.filexcel)
        
      }
    } catch (error)  {
      this.$toast.error("Có lỗi xảy ra: " + error)
      return
    } finally {
      this.$root.showLoading(false)
      this.$refs.fileUpload.reset()
    }
    this.arrayImportFile.filexcel = null
  },
  async tsbtnXuatExcel_Click(){
    this.$refs.fileUpload.$el.click()
  },
  async Chon_DSTB(dt){
      // try{
          let ds = this.gridDanhSach;
          var gridviewDanhSach = this.$refs.gridviewDanhSach.$children[0]
          for (let r of dt){
            if (r && r.hasOwnProperty('MA_TB') && r["MA_TB"].toString()){
              let selectIndexs = []
              ds.filter((a,index)=>{
                a.MA_TB.toString() == r["MA_TB"].toString() ? selectIndexs.push(index) : ''
              })
              
              selectIndexs.length ? gridviewDanhSach.selectRows(selectIndexs) : ''
            }
            else if (r && r.hasOwnProperty('MA_LT')){
              let selectIndexs = []
              ds.filter((a,index)=>{
                a.MA_LT.toString() == r["MA_TB"].toString() ? selectIndexs.push(index) : ''
              })
              selectIndexs.length ? gridviewDanhSach.selectRows(selectIndexs) : ''
            }
          }
          await this.LAY_DS_VTKHONGMA();
          await this.LAY_DS_NHAPKHO();
      // }
      // catch (error){
      //     this.$toast.error(ex.toString());
      // }
  },
  async tvVatTu_CustomNodeCellEdit(){

  },
  async tsbtnVatTu_Click(){
      // if (gridviewDanhSach.FocusedRowHandle < 0){
      //   return;
      // }
      // try{
          // let dong = this.gridviewDanhSach_FocusedRow;
          // var f = this.$refs.popup_frmCapVatTuTB;
          // var frm = this.$refs.frmCapVatTuTB
          // frm.hdtb_id = parseInt(dong["HDTB_ID"].toString());
          // frm.baohong_id = parseInt(dong["BAOHONG_ID"].toString());
          // frm.phieu_id = parseInt(dong["PHIEU_CHA_ID"].toString());
          // frm.loaihd_id = parseInt(dong["LOAIHD_ID"].toString());
          // frm.loaitb_id = parseInt(dong["LOAITB_ID"].toString());
          // f.loaitb_id = parseInt(dong["LOAITB_ID"])
          // frm.donvi_id = parseInt(dong["DONVI_GIAO_ID"].toString());
          // frm.kieu_f = 1;
          // frm.tagfrm = "2";
          // f.ShowDialog();
          // f.show()
          this.$router.push('CapVatTuThueBao')
        
          await this.TimKiem();

      // }
      // catch (Exception){

      // }

  },
  async tsbtnExcelMau_Click(){
      // try{
          let cotnull = true;
          // gridviewDanhSach.ClearColumnsFilter();
          if (this.gridDanhSach.length == 0){
              this.$toast.error("Không có dữ liệu để xuất");
              return;
          }
          let exportData = []
          this.gridDanhSach.map(a=>
            exportData.push({
              MA_TB: a.MA_TB,
              LOAIHINH_TB: a.LOAIHINH_TB,
              MA_LT: a.MA_LT,
              TEN_TB: a.TEN_TB,
              TEN_KIEULD: a.TEN_KIEULD,
              DIACHI_LD: a.DIACHI_LD,
              NGAY_YC: a.NGAY_YC,
              NGAY_HT: a.NGAY_HT,
              NGAY_CAP: a.NGAY_CAP,
              LYDOCAP: a.LYDOCAP,
              DONVI_CAP: a.DONVI_CAP,
              TEN_NV: a.TEN_NV || "",
              DIABAN: a.DIABAN || "",
            }))
          let data = XLSX.utils.json_to_sheet(exportData)
            

          
          
          let wb = XLSX.utils.book_new(); // make Workbook of Excel
          XLSX.utils.book_append_sheet(wb, data, "TS"); // sheetAName is name of Worksheet
          // export Excel file
          XLSX.writeFile(wb, "DanhSachThueBao.xlsx");

      // }
      // catch (error){
      //     Message_Box.ShowError("Lỗi khi xuất dữ liệu\r\n" );
      // }
  },
  async txtSoThueBao_KeyPress(){  
    this.tk = this.params.p_txtSoThueBao;
    await this.LoadThongTinThueBao(1);
    this.tk = "";
      
    },
  convertData_grcChuaDuyet(){
    this.grcChuaDuyet.forEach(a=>{
      a.SOLUONG ? a.SOLUONG = parseInt(a.SOLUONG) : ''
      // a.NGAY_CAP ? a.NGAY_CAP = moment(a.NGAY_CAP,'DD/MM/YYYY hh:mm:ss').toISOString() : ""
    })
  },
  async cboDeNghi_EditValueChanged(){
    this.quyettoan_id = this.params.p_DeNghi
    await this.LoadThongTinThueBao(1)
    }
  },
  created: async function() {
    try{
      this.loading(true)
      this.Tag = this.$route.query.Tag || "0" 
      await this.frmTongHopThuHoi_New_Load()
      EventBus.$on('btnChon_ButtonClick', this.btnChon_ButtonClick)
    }catch(e){
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false)
    }
  },
  watch: {
    
  },
  destroyed () {
    EventBus.$off('btnChon_ButtonClick',this.btnChon_ButtonClick)
  }
};
</script>
<style>

  #tonghop_danhsachthuhoi_new .e-treegrid .e-rowcell.e-active {
    background-color: #F9E1A9 !important;
  }
</style>