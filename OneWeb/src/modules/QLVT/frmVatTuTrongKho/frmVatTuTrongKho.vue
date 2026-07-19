<template src='./frmVatTuTrongKho.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import { getterName,actionName,statePropertyName,mutationName} from '../store/frmvattutrongkho'
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import frmVatTuTrongKhoAPI from  '../api/frmVatTuTrongKhoAPI'
import select2 from '@/components/Select2.vue'
import { L10n } from '@syncfusion/ej2-base'
import XLSX from "xlsx";
import { Query } from '@syncfusion/ej2-data';

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

export default {

  components: { XLSX,breadcrumb, appSelect2: select2 },
  name: "frmVatTuTrongKho",
  mounted() {
  },
  provide: {
  },
  computed: {

    aggregates_gridXuat () {
      return [
        { field: 'SOLUONG', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'LOHANG', type: 'Count', format: 'N0', footerTemplate: this.countTemplate },
      ]
    },
    aggregates_gridVatTu () {
      return [
        { field: 'LOHANG', type: 'Count', format: 'N0', footerTemplate: this.countTemplate },
      ]
    },
  },
  data() {
    return {
        header: {
            title: "TRA CỨU THIẾT BỊ TỒN KHO",
            list: [
            { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
            {
                name: "Lắp đặt mới",
                link: { name: "Ui.buttons" },
            },
            ],
        },

        chungtu_id: 0,
        ctct_id: 0,

        dtVT: [],
        dtVT_Xuat: [],
        dtVatTu: [],
        dtThietBi:[],
        
        DSVatTuChecked: [],
        XacNhan: false,
        dt:[],
        vkho_id: 0,
        checkAll: false,
        kieu:0,
        DS_Xuat: [],
        vngay_ct: new Date(),
        vchungtu_id: '',
        dsQuyen: [],
        dtLuuVT: [],

        vkho_den_id: '',
        tt: 0,
        ds_dangky: 0,
        coDS: false,

        IS_VIEWING_COL_DONGIA: false,

        Visible: {
          tsbtnXuatExcelTbi : true,
          popupFilter: false,
          xtraTabPage2: true,
          label1: false,
          dtpNgayDK: false,
          tsbDanhSach: false,
          ckbAllTbi: true,
          splitContainer2: {
            Panel2Collapsed: true,
            ShowDKTT: false,
          },
          colSlTamUng: false,
        },
        ReadOnly: {
          dtpNgayDK: false
        },
        Enabled: {
          cboTuKho: true,
          tsbDanhSach: true,
          grvDSVatTu_DaDK: {
            SL_XUAT: true,
            BODUYET: true
          },
          cboLinhVuc_TK: false,
          cboHeThong_TK: false,
          cboHangSX_TK: false,
          cboDongTbi_TK: false,
        },
        params: {
          p_TuKho: '',
          p_dtpNgayDK: new Date(),
          p_ckbAllTbi: false,

          p_ckbLinhVuc_TK: false,
          p_ckbHangSX_TK: false,
          p_LinhVuc_TK: '',
          p_HeThong_TK: '',
          p_HangSX_TK: '',
          p_DongTbi_TK: '',
        },
        cboTuKho: [],
        gridThietBi: [],
        gridVatTu: [],
        gridXuat: [],

        grcDSDangKy: [],
        grcDSVatTu_DaDK: [],

        grcDK_TT: [],
        grcCT_DK: [],

        repoLoaiKho: [],


        isShow: false,


        gridVatTu_Columns: [
          // list cột cố định
          { fieldName: 'KEY_ROW', headerText:'', visible:false, isPrimaryKey:true},
          { fieldName: 'LOHANG', headerText: 'Lô hàng',  allowFiltering: true , freeze: 'Left',},
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư',  allowFiltering: true, freeze: 'Left',},
          { fieldName: 'MA_VT', headerText: 'Mã vật tư',  allowFiltering: true , },
          { fieldName: 'DVI_TINH', headerText: 'ĐVT',  allowFiltering: true},
          { fieldName: 'DONGIA', headerText: 'Đơn giá', format: 'N3', type: 'number', allowFiltering: true},
          { fieldName: 'SL_XUAT', headerText: 'SL xuất',  editType: 'numericedit',allowEditing: true ,  format: 'N3', type: 'number', allowFiltering: true },
          { fieldName: 'SL_TON', headerText: 'SL tồn',  format: 'N3', type: 'number', allowFiltering: true},
          { fieldName: 'SL_TREO', headerText: 'SL treo',  format: 'N3', type: 'number', allowFiltering: true},
          { fieldName: 'SOLUONG', headerText: 'Số lượng',  format: 'N3', type: 'number', allowFiltering: true},    
          { fieldName: 'SL_TONTHUC', headerText: 'SL tồn thực',  format: 'N3', type: 'number', allowFiltering: true ,visible:false},  
          { fieldName: 'SL_C_DD', headerText: 'SL DD',  format: 'N3', type: 'number', allowFiltering: true ,visible:false},    
          { fieldName: 'LOAI_TBI', headerText: 'Loại thiết bị',  allowFiltering: true},
          { fieldName: 'SOTHANG_PB', headerText: 'Số tháng phân bổ',  allowFiltering: true},
          { fieldName: 'THANHTIEN', headerText: 'Thành tiền',  format: 'N3', type: 'number', allowFiltering: true},
          { fieldName: 'VAT', headerText: 'Thành tiền',  format: 'N3', type: 'number', allowFiltering: true},
          { fieldName: 'NGAYNHAP', headerText: 'Ngày nhập',  allowFiltering: true},
          { fieldName: 'MA_DUAN', headerText: 'Dự án',  allowFiltering: true },
          { fieldName: 'MA_HD_PSIS', headerText: 'Mã HD PSIS',  allowFiltering: true },
          { fieldName: 'MA_DH_PSIS', headerText: 'Mã DH PSIS',  allowFiltering: true },
          { fieldName: 'MA_GH', headerText: 'Mã GH', allowFiltering: true },


          // list cột chỉ hiển thị cho HCM,HNI,NET

          { fieldName: 'SL_TAMUNG', headerText: 'SL tạm ứng',  format: 'N3', type: 'number', allowFiltering: true ,visible:false},
          { fieldName: 'CHATLUONG_TEXT', headerText: 'Chất lượng', allowFiltering: true ,visible:false},
          { fieldName: 'GHICHU', headerText: 'Ghi chú',  allowFiltering: true ,visible:false},
          { fieldName: 'SO_HD', headerText: 'Số HĐ',  allowFiltering: true ,visible:false}, 
          { fieldName: 'NOIDUNG', headerText: 'Nội dung',  allowFiltering: true ,visible:false},
          { fieldName: 'DIENGIAI', headerText: 'Diễn giải',  allowFiltering: true ,visible:false},
          { fieldName: 'ID_NHAPGOC', headerText: 'Id nhập gốc',  allowFiltering: true ,visible:false},
          { fieldName: 'NGUOI_QL', headerText: 'Người QL',  allowFiltering: true ,visible:false},
          { fieldName: 'TY_LE', headerText: 'Tỷ lệ',  allowFiltering: true ,visible:false},
          { fieldName: 'IDG', headerText: 'IDG',  allowFiltering: true ,visible:false},
          // { fieldName: 'LOAIKHO', headerText: 'Loại kho',  allowFiltering: true ,visible:false},
          // { fieldName: 'MAKHOTD', headerText: 'Mã Kho TD',  allowFiltering: true ,visible:false},
          { fieldName: 'LOAIKHO_TEXT', headerText: 'Loại kho',  allowFiltering: true ,visible:false},
          { fieldName: 'MAKHOTD_TEXT', headerText: 'Mã Kho TD',  allowFiltering: true ,visible:false},
          { fieldName: 'NGUON_GOC', headerText: 'Nguồn gốc',  allowFiltering: true ,visible:false},
          
          { fieldName: 'MATHETS', headerText: 'MATHETS',  allowFiltering: true ,visible:false},
          { fieldName: 'MATHUNG', headerText: 'Mã thùng',  allowFiltering: true ,visible:false},
          { fieldName: 'DS_CTR', headerText: 'Ds công trình',  allowFiltering: true ,visible:false},          
          
        ],
        mapping_gridVatTuCol: {
          colSLDD: 'SL_C_DD',
          colChatluong_id: 'CHATLUONG_ID',
          colCL: 'CHATLUONG_TEXT',
          colSHD: 'SO_HD',
          colMaDA: 'MA_DUAN',
          colIDNG: 'ID_NHAPGOC',
          colNguoiQL: 'NGUOI_QL',
          colDienGiai: 'DIENGIAI',
          colNoiDUng: 'NOIDUNG',
          colGC: 'GHICHU',
          colCongTrinh: 'DS_CTR',
          colIDG: 'IDG',
          colTrangThaiVT: 'TRANGTHAI',
          colTamUng:'SL_TAMUNG'
        },

        gridXuat_Columns: [
          {fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', allowFiltering: true,freeze: 'Left', visible: true},
          {fieldName: 'MA_VT', headerText: 'Mã vật tư', textAlign: 'Left', allowFiltering: true, visible: true, freeze: 'Left', },
          {fieldName: 'TEN_VT', headerText: 'Tên vật tư', textAlign: 'Left', allowFiltering: true, visible: true, },
          {fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', allowFiltering: true, visible: true },
          {fieldName: 'SOLUONG', headerText: 'SL tồn', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: true },
          {fieldName: 'SL_XUAT', headerText: 'SL xuất', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: true },
          {fieldName: 'DONGIA', headerText: 'Đơn giá', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: true },
          {fieldName: 'THANHTIEN', headerText: 'Thành tiền', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: true },
        ],

        gridThietBi_Columns: [
        {fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', allowFiltering: true },
        {fieldName: 'MA_VT', headerText: 'Mã vật tư', textAlign: 'Left', allowFiltering: true },
        {fieldName: 'TEN_VT', headerText: 'Tên vật tư', textAlign: 'Left', allowFiltering: true },
        {fieldName: 'MA_TBI', headerText: 'Mã thiết bị', textAlign: 'Left', allowFiltering: true },

        {fieldName: 'SERIAL', headerText: 'Serial', textAlign: 'Left', allowFiltering: true },
        {fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', allowFiltering: true },
        {fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        {fieldName: 'SL_DD', headerText: 'Số lượng DD', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: false },

        {fieldName: 'SL_KD', headerText: 'SL Khả dụng', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, visible: false },
        {fieldName: 'DONGIA', headerText: 'Đơn giá', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        {fieldName: 'THANHTIEN', headerText: 'Thành tiền', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true },
        {fieldName: 'SERIAL_GP', headerText: 'Serial Gp', textAlign: 'Left', allowFiltering: true, freeze: 'Right' },
        ],

        grcDSDangKy_Columns: [
          {fieldName: 'CHON', headerText: '', type: 'checkbox',textAlign: 'Center', allowFiltering: true, width: '60', freeze: 'Left'},
          {fieldName: 'SO_CV', headerText: 'Số công văn', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'TEN_TG', headerText: 'Thời gian ĐK', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'MA_CT', headerText: 'Dự án', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'NGAY_DK', headerText: 'Ngày yêu cầu', textAlign: 'Right', allowFiltering: true, },

          {fieldName: 'DOT', headerText: 'Đợt', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'CONLAI', headerText: 'Số tháng ĐK', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, },
          {fieldName: 'TEN_DV', headerText: 'Đơn vị ĐK', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'SODANGKY', headerText: 'SL Đăng ký', textAlign: 'Left', allowFiltering: true, },

          {fieldName: 'NHOM', headerText: 'Nhóm', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'NGAYDUYET', headerText: 'Ngày duyệt', textAlign: 'Right', allowFiltering: true, },
          {fieldName: 'NGUOI_CN', headerText: 'Người duyệt', textAlign: 'Left', allowFiltering: true, },
          
        ],

        grcDSVatTu_DaDK_Columns: [
            {fieldName: 'MA_VT', headerText: 'Mã VT', textAlign: 'Left', allowFiltering: true, },
            {fieldName: 'TEN_VT', headerText: 'Tên vật tư', textAlign: 'Left', allowFiltering: true, },
            {fieldName: 'MUCDICH', headerText: 'Mục đích', textAlign: 'Left', allowFiltering: true, },
            {fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', allowFiltering: true, },

            {fieldName: 'SL_TON_DK', headerText: 'SL Tồn ĐK', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, },
            {fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, },
            {fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true, },
            {fieldName: 'SL_XUAT', headerText: 'SL Xuất', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, },
            {fieldName: 'SL_DUYET', headerText: 'SL Duyệt', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, },
            {fieldName: 'BODUYET', headerText: 'Bỏ duyệt', textAlign: 'Left', allowFiltering: true, },
        ],

        grcDK_TT_Columns: [
          {fieldName: 'CHON', headerText: '', type: 'checkbox',textAlign: 'Left', allowFiltering: true, width: '60'},
          {fieldName: 'SO_CV', headerText: 'Số công văn', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'TEN_TG', headerText: 'Thời gian ĐK', textAlign: 'Left', allowFiltering: true, },
          // {fieldName: 'MA_CT', headerText: 'Dự án', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'NGAY_DK', headerText: 'Ngày yêu cầu', textAlign: 'Right', allowFiltering: true, },

          {fieldName: 'DOT', headerText: 'Đợt', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'CONLAI', headerText: 'Số tháng ĐK', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, },
          {fieldName: 'TEN_DV', headerText: 'Đơn vị ĐK', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'SODANGKY', headerText: 'SL Đăng ký', textAlign: 'Left', allowFiltering: true, },

          {fieldName: 'NHOM', headerText: 'Nhóm', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'NGAYDUYET', headerText: 'Ngày duyệt', textAlign: 'Right', allowFiltering: true, },
          {fieldName: 'NGUOI_CN', headerText: 'Người duyệt', textAlign: 'Left', allowFiltering: true, },
          {fieldName: 'btnTraPhieu', headerText: 'Trả phiếu', textAlign: 'Left', allowFiltering: true, freeze: 'Right'},
        ],

        grcCT_DK_Columns: [
            {fieldName: 'MA_VT', headerText: 'Mã VT', textAlign: 'Left', allowFiltering: true, },
            {fieldName: 'TEN_VT', headerText: 'Tên vật tư', textAlign: 'Left', allowFiltering: true, },
            {fieldName: 'MUCDICH', headerText: 'Mục đích', textAlign: 'Left', allowFiltering: true, },
            {fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', allowFiltering: true, },

            {fieldName: 'SL_TON_DK', headerText: 'SL Tồn ĐK', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, },
            {fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, },
            {fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true, },
            {fieldName: 'SL_XUAT', headerText: 'SL Xuất', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, },
            {fieldName: 'SL_DUYET', headerText: 'SL Duyệt', textAlign: 'Right', format: 'N3', type: 'number', allowFiltering: true, },
        ],






        gridVatTuPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
        indexArray: [],
        functionName: "", // quý ,bổ sung khi 1 component có nhiều func gọi

        // Tiền Giang, Thái Nguyên
        tinh: ["TGG", "TNN"],

        cboLinhVuc_TK: [],
        cboHeThong_TK: [],
        cboHangSX_TK: [],
        cboDongTbi_TK: [],

        DS_QUYEN_VT: {
          HOANTHIEN_CHUNGTU: 1794
        },
        tt: 0,

        sumTemplate: function() {
          return { template: Vue.component('sumTemplate', {
              template: `
                <div class="input-group-prepend w-100">
                  <div class="input-group-text  w-100 d-flex justify-content-between">
                    <div>Tổng:</div>
                    <div class="tright pr-2">{{data.Sum}}</div>
                  </div>
                </div>
              `,
              // <input type="text" class="form-control right" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;"/>
              data: function () {return {data: {data: {}}};}
            })
          }
        },
        countTemplate: function() {
        return { template: Vue.component('countTemplate', {
            template: `
            <div class="input-group-prepend w-100">
              <div class="input-group-text  w-100 d-flex justify-content-between">
                <div>Đếm:</div>
                <div class="tright pr-2">{{data.Count}}</div>
              </div>
            </div>`,
            // <input type="text" class="form-control right" :value="data.Count" :disabled="true" style="margin: 7px 0px 7px 0px;" />
            data: function () {return {data: {data: {}}};}
          })
        }
      },


    };  
  },
  methods: {
    ...mapActions("frmvattutrongkho", actionName),
    ...mapMutations("frmvattutrongkho", mutationName),
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    gridVatTuPageHandler(e) {
      this.gridVatTuPage.page = e.pageIndex
      this.gridVatTuPage.maxSize = e.pageSize
    },
    async Load_quyen(){
      this.dsQuyen =  await this.getDSQuyenND({nguoiDungId: this.$auth.getNguoiDungID()})
      // this.dsQuyen = dtQuyen.map(a=>a.QUYEN_ID) || []
    },
    async ChinhFont(){
      //lam sau
    },
    onFilteringTuKho(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('tenKho', 'contains', e.text, true) : query;
      e.updateData(this.cboTuKho, query);
    },
    async func_IS_VIEWING_COL_DONGIA(){
      let s = await this.KiemTraThamSoMucDich({maThamSo: 'QLVT_VIEWTON_COLDG'}) 
      if (s == "1" ){
        this.IS_VIEWING_COL_DONGIA = true
      }
      else{
        this.IS_VIEWING_COL_DONGIA = false
      }
    },
   
    VisibleCols_gridVatTu(cols, value){
      for(let e of this.gridVatTu_Columns){
        if(cols.includes(e.fieldName)){
          e.visible = value  
        }
      }
    },
    async frmVatTuTrongKho_Load(){
      try{
        this.loading(true)
        
        if (['HNI','HCM','NET'].includes(this.$auth.getMaTinh())){
          if(this.tt == 1){
            this.VisibleCols_gridVatTu(['SL_TAMUNG','CHATLUONG_TEXT','GHICHU','SO_HD',
            'MA_DUAN','NOIDUNG','DIENGIAI','ID_NHAPGOC','NGUOI_QL','TY_LE','IDG',
            'LOAIKHO_TEXT','MAKHOTD_TEXT','NGUON_GOC','SL_TONTHUC','MATHETS','MATHUNG','DS_CTR','SL_C_DD'], true)
          }else{
            this.VisibleCols_gridVatTu(['TY_LE','CHATLUONG_TEXT','GHICHU','SO_HD','ID_NHAPGOC','IDG',
            'LOAIKHO_TEXT','MAKHOTD_TEXT','NOIDUNG','NGUON_GOC','SL_TONTHUC','MATHETS','MATHUNG','DS_CTR','SL_C_DD'], true)                   
          }
        }
      

        await this.Load_quyen()
        if (this.kieu == 1){       
        }
        this.cboTuKho = await this.getDSKhoGanTheoNV({nhanVienId: this.$auth.getNhanVienID()})
      
        let khoID_temp = null // tranh gán nhiều lần load đè dữ liẹu
        // this.cboTuKho.length ? this.params.p_TuKho = this.cboTuKho[0].khoID : ''
        if(this.cboTuKho.length > 0){
          khoID_temp = this.cboTuKho[0].khoID
        }
        
        if (this.kieu == 1){
            // this.params.p_TuKho = null;          
            // this.params.p_TuKho = this.vkho_id;
            khoID_temp = this.vkho_id         
            // await this.cboTuKho_EditValueChanged()
            this.Enabled.cboTuKho = false;
            this.Visible.xtraTabPage2= false;
            this.Visible.label1= false;
            this.Visible.dtpNgayDK= false;
            this.Visible.tsbDanhSach= true;
        }
        else if (this.kieu == 2){
            this.cboTuKho = await this.getDSKhoNhaCC({nhanVienId: this.$auth.getNhanVienID()})
            // this.params.p_TuKho = this.vkho_id;
            khoID_temp = this.vkho_id           
            this.Enabled.cboTuKho = false;
            this.Visible.ckbAllTbi= false;
            this.Visible.xtraTabPage2= false;
            this.Visible.label1= false;
            this.Visible.dtpNgayDK= false;
            this.Visible.tsbDanhSach= true;
        }
        else{
            this.params.p_dtpNgayDK = new Date()
            this.dtThietBi = await this.getDSThietBiTonKho({khoId: this.vkho_id})
            this.gridThietBi = this.dtThietBi;
            this.convertData_gridThietBi()

            this.Visible.ckbAllTbi= false;
            this.Visible.label1= true;
            this.Visible.dtpNgayDK= true;
            this.ReadOnly.dtpNgayDK = true;
            this.Visible.tsbDanhSach= false;
        }
        this.params.p_TuKho = khoID_temp
        await this.ds_vt_xuat()
      
        // HANG_SX - LV - DONGTBI - he thong
        this.cboHangSX_TK = await this.getDSHangSanXuat()
        this.cboHangSX_TK.length ? this.params.p_HangSX_TK = this.cboHangSX_TK[0].hangSxId : ''
       

        this.cboLinhVuc_TK = await this.getDSLinhVuc()
        this.cboLinhVuc_TK.length ? this.params.p_LinhVuc_TK = this.cboLinhVuc_TK[0].heThongId : ''
       
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async ds_vt_xuat(){
      let dt = []
      let dt_2 = []     
      console.log('482 - ds_vt_xuat')
      console.log(this.dtLuuVT) 
      for (let item of this.dtLuuVT){
        let _k = 0;
        for (let vt of this.dtVatTu){    
            if (item["VATTU_ID"] == vt["VATTU_ID"] && item["LOHANG"] == vt["LOHANG"]){
      
                let tien = Math.round(parseFloat(item["SOLUONG"].toString()) * parseFloat(vt["DONGIA"].toString()));
                let vat = tien / 10;
                if (this.dtVatTu[0].hasOwnProperty("TRANGTHAI")){
                    vt["TRANGTHAI"] =  item["TRANGTHAI"];
                }
                else{
                    dt.push({
                      CHON: item["CHON"],
                      LOHANG: vt["LOHANG"],
                      KHO_ID: vt["KHO_ID"],
                      VATTU_ID: vt["VATTU_ID"],
                      DONGIA: vt["DONGIA"],
                      DVITINH_ID: vt["DVITINH_ID"],
                      MA_VT: vt["MA_VT"],
                      TEN_VT: vt["TEN_VT"],
                      DVI_TINH: vt["DVI_TINH"],
                      SL_TON: vt["SL_TON"],
                      LOAITBI_ID: vt["LOAITBI_ID"],
                      SL_TREO: vt["SL_TREO"],
                      LOAI_TBI: vt["LOAI_TBI"],
                      SOTHANG_PB:  vt["SOTHANG_PB"],
                      SL_XUAT: vt["SL_TON"],
                      SOLUONG: item["SOLUONG"],
                      THANHTIEN: tien,
                      VAT: vat
                    })
                }
                //new change
                if (this.dtLuuVT[0].hasOwnProperty("TY_LE") && this.dtLuuVT[0].hasOwnProperty("MAKHOTD")
                    && this.dtLuuVT[0].hasOwnProperty("LOAIKHO") && this.dtLuuVT[0].hasOwnProperty("MATHETS")
                    && this.dtLuuVT[0].hasOwnProperty("MATHUNG"))
                {
                    dt[i]["TY_LE"] = item["TY_LE"].toString();
                    dt[i]["MAKHOTD"] = !item["MAKHOTD"] || item["MAKHOTD"].toString() == "" 
                        ? 0 : parseInt(item["MAKHOTD"]);
                    dt[i]["LOAIKHO"] = !item["LOAIKHO"]  || item["LOAIKHO"].toString() == ""
                        ? 0 : parseInt(item["LOAIKHO"]);
                    dt[i]["MATHETS"] = item["MATHETS"].toString();
                    dt[i]["MATHUNG"] = item["MATHUNG"].toString();
                }
                  //

                vt["SL_XUAT"] = item["SOLUONG"];
                vt["CHON"] = "s1";
                //new change
                if (this.dtLuuVT[0].hasOwnProperty("TY_LE") && this.dtLuuVT[0].hasOwnProperty("MAKHOTD")
                    && this.dtLuuVT[0].hasOwnProperty("LOAIKHO") && this.dtLuuVT[0].hasOwnProperty("MATHETS")
                    && this.dtLuuVT[0].hasOwnProperty("MATHUNG"))
                {
                    if (vt["CHON"].toString() == "s1"){
                        vt["TY_LE"] = item["TY_LE"].toString();
                        vt["MAKHOTD"] = !item["MAKHOTD"] || item["MAKHOTD"].toString() == ""
                            ? 0 : parseInt(item["MAKHOTD"]);
                        vt["LOAIKHO"] = !item["LOAIKHO"] || item["LOAIKHO"].toString() == ""
                            ? 0 : parseInt(item["LOAIKHO"]);
                        vt["MATHETS"] = item["MATHETS"].toString();
                        vt["MATHUNG"] = item["MATHUNG"].toString();
                    }
                }
                //
                _k++;
            }
        }
        if (_k == 0)
        {
            let tien = Math.round(parseFloat(item["SOLUONG"].toString()) * parseFloat(item["DONGIA"].toString()));
            let vat = tien / 10;
            let loaikho = !item["LOAIKHO"] || item["LOAIKHO"].toString() == "" ? 0 :
                parseInt(item["LOAIKHO"]);
            let makho = !item["MAKHOTD"] || item["MAKHOTD"].toString() == ""  ? 0 :
                parseInt(item["MAKHOTD"]);
            if (this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty("TRANGTHAI")){
                dt_2.push({
                  CHON: item["CHON"],
                  LOHANG: item["LOHANG"],
                  KHO_ID: this.vkho_id,
                  VATTU_ID: item["VATTU_ID"],
                  DONGIA: item["DONGIA"],
                  DVITINH_ID: '',
                  MA_VT: item["MA_VT"],
                  TEN_VT: item["TEN_VT"],
                  DVI_TINH: item["DVI_TINH"],
                  SOLUONG: item["SOLUONG"],
                  LOAITBI_ID: item["LOAITBI_ID"],
                  SL_TREO:  0,
                  LOAI_TBI: item["LOAI_TBI"],
                  SOTHANG_PB: item["SOTHANG_PB"],
                  SL_XUAT: item["SOLUONG"],
                  SOLUONG: item["SOLUONG"],
                  THANHTIEN: tien,
                  VAT: vat,
                  TRANGTHAI:  item["TRANGTHAI"],
                  // : '',
                  // '',
                  LOAIKHO: loaikho,
                  MAKHO: makho
                })
                    
            }
            else{
                dt_2.push({
                  CHON: item["CHON"],
                  LOHANG: item["LOHANG"],
                  KHO_ID: this.vkho_id,
                  VATTU_ID: item["VATTU_ID"],
                  DONGIA: item["DONGIA"],
                  DVITINH_ID: '',
                  MA_VT: item["MA_VT"],
                  TEN_VT: item["TEN_VT"],
                  DVI_TINH: item["DVI_TINH"],
                  SOLUONG: item["SOLUONG"],
                  LOAITBI_ID: item["LOAITBI_ID"],
                  SL_TREO:  0,
                  LOAI_TBI: item["LOAI_TBI"],
                  SOTHANG_PB: item["SOTHANG_PB"],
                  SL_XUAT: item["SOLUONG"],
                  SOLUONG: item["SOLUONG"],
                  THANHTIEN: tien,
                  VAT: vat,
                  TRANGTHAI:  "",
                  // : '',
                  // '',
                  LOAIKHO: loaikho,
                  MAKHO: makho
                })
            }
        }
      }
      dt_2.length ? this.dtVatTu.push(...dt_2) : ''
      dt.length ? this.dtVT_Xuat.push(...dt) : ''

      // Đức tự sinh Key cho lưới vì bảng này trùng VATTU_ID và LOHANG
      for(const i in this.dtVatTu){
        this.dtVatTu[i].KEY_ROW = i        
      }
      this.gridVatTu = this.dtVatTu;    
      this.convertData_gridVatTu()

      var p = this.dtVatTu.filter(a=>a.CHON == 's1')
      let vatTuIndexs = []
      this.dtVatTu.filter((b,index)=>{
        if (b.CHON == 's1'){
          vatTuIndexs.push(index)
        }
      })
      //this.FocusGridViewRow(vatTuIndexs)  // fix tạm thêm vật tư k checkbox
     
      if (p.length >= 0){
        this.gridXuat = p;
        this.convertData_gridXuat()
      }
    },
    async FocusGridViewRow(listIndex){
        for(let indexs of listIndex ){
          if (indexs != -1) {
            if (!(
                (indexs + 1) > (this.$refs['gridViewVatTu'].pageIndexData * this.$refs['gridViewVatTu'].pageSizeData) &&
                ((indexs + 1) <= ((this.$refs['gridViewVatTu'].pageIndexData + 1 ) * this.$refs['gridViewVatTu'].pageSizeData))
                )){
                this.$refs['gridViewVatTu'].$refs.pagination.gotoPage(parseInt(indexs /this.$refs['gridViewVatTu'].pageSizeData ))
            }        
            let ind = parseInt(indexs%this.$refs['gridViewVatTu'].pageSizeData)
            setTimeout(() => {
              this.$refs['gridViewVatTu'].grid.selectRow(ind,true)
            }, 1000);
          }
        }         
    },
    async tsbtnHoanThanh_Click(){   
      let a = this.gridVatTu.filter(a=>this.DSVatTuChecked.includes(a.KEY_ROW))    
      if (!a.length){
        this.$toast.error("Chưa chọn vật tư nào!")
        this.XacNhan = false
        return
      }
      // Kiểm tra
      var check = this.gridXuat
      console.log('662 - this.gridXuat')
      console.log(this.gridXuat)
      if(check.length && check[0].hasOwnProperty('TRANGTHAI')){
        let kq = await this.kiemTraTrangThaiVatTu({
          dsVatTu: check
        })
        if (kq.toString() == "0"){
          this.$toast.error("Vật tư có trạng thái 'Chưa có đầu vào' không được chọn chung với các vật tư khác !")
          return
        }
      }

      // this.dt = a 
      this.dt = this.gridXuat
      for (let item of this.dt){
        if(parseFloat(item["SL_XUAT"] || 0) <= 0){
          this.$toast.error("Số lượng xuất phải lớn hơn 0!")
          return
        }
      }

      var kt = this.dt.filter(p=>parseFloat(p["SL_XUAT"]) > parseFloat(p["SOLUONG"]))
      if(kt.length){
        let message = ""
        for(let e of kt){
          message = message + " ("+ e.LOHANG + " - " + e.MA_VT +")"
        }
        this.$toast.error("Không được xuất quá lượng tồn của vật tư !" + message)
        this.XacNhan = false
        return
      }
      this.XacNhan = true;
      this.checkAll = this.params.p_ckbAllTbi
      if(this.dt.length){
        for(let item of this.dt){
          item["SOLUONG"] = item["SL_XUAT"]
        }
      }
      if (this.coDS){
        this.ds_dangky = this.grcCT_DK
      }
      await this.Close()


    },

    async tsbtnHuyBo_Click(){
      this.XacNhan = false
    },
    
    async btnXuatExcel_Click(){
      var dt = this.$refs.gridViewVatTu.getSelectedRecords()
      if (dt.length){
          await this.SaveXlsAndOpenFile("VatTuKho-" + this.vkho_id.toString() + ".xlsx","sheet1", dt)
      }
      else{
        this.$toast.error("Không có vật tư nào được chọn để xuất excel!")
      }
    },
    async btnXuatExcelAll_Click(){
      var dt = this.gridVatTu
      if (dt.length){
          await this.SaveXlsAndOpenFile("VatTuKho-" + this.vkho_id.toString() + ".xlsx","sheet1", dt)
      }else{
        this.$toast.error("Không có vật tư nào được chọn để xuất excel!")
      }
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
    async tsbtnXuatExcelTbi_Click(){
      var ds = this.$refs.gridViewVatTu.getSelectedRecords()

      let dt = []
      for (let rtbi of this.dtThietBi){
        for (let item of ds){
          if (rtbi["LOHANG"] == item.LOHANG && rtbi["VATTU_ID"] == item.VATTU_ID){
            let dr = {}
            dr["LOHANG"] = item.LOHANG;
            dr["MA_VT"] = rtbi["MA_VT"].toString();
            dr["TEN_VT"] = rtbi["TEN_VT"].toString();
            dr["DVI_TINH"] = rtbi["DVI_TINH"].toString();
            dr["MA_TBI"] = rtbi["MA_TBI"].toString();
            dr["SERIAL"] = rtbi["SERIAL"].toString();
            dr["SOLUONG"] = parseInt(rtbi["SOLUONG"]);
            dr["DONGIA"] = parseInt(rtbi["DONGIA"]);
            dr["SERIAL_GP"] = rtbi["SERIAL_GP"].toString();
            
            dt.push(dr)
          }
        }
      }
      if (dt.length == 0){
        dt.push({
          "LOHANG" : '',
          "MA_VT": '',
          "TEN_VT": '',
          "DVI_TINH": '',
          "MA_TBI":'', 
          "SERIAL": '',
          "SOLUONG": 0,
          "DONGIA": 0,
          "SERIAL_GP": ''
        })
      }
      await this.SaveXlsAndOpenFile("ThietBiKho-" + this.vkho_id.toString() + ".xlsx", "sheet1", dt)
    },
    async tsbtnXuatExcelTbiAll_Click(){    
      let dt = this.dtThietBi
      if (dt.length == 0){
        dt.push({
          "LOHANG" : '',
          "MA_VT": '',
          "TEN_VT": '',
          "DVI_TINH": '',
          "MA_TBI":'', 
          "SERIAL": '',
          "SOLUONG": 0,
          "DONGIA": 0,
          "SERIAL_GP": ''
        })
      }
      await this.SaveXlsAndOpenFile("ThietBiKho-" + this.vkho_id.toString() + ".xlsx", "sheet1", dt)
    },
    async cboTuKho_EditValueChanged(){
      try{
        this.loading(true)
        await this.LOAD_DS_TK()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
      
    },
    async load_cboRePo (){

    },
    async LOAD_DS_TK(){
      // try {
        if (this.kieu == 0){
          if (this.$auth.getMaTinh() == "NET"){
            let table = [{
              IS_LINHVUC: this.params.p_ckbLinhVuc_TK ? 1 : 0,
              IS_HANGSX: this.params.p_ckbHangSX_TK ? 1 : 0,
              LINHVUC_ID: this.params.p_LinhVuc_TK,
              HETHONG_ID: this.params.p_HeThong_TK,
              HANGSX_ID: this.params.p_HangSX_TK,
              DONGTBI_ID: this.params.p_DongTbi_TK
            }]
            this.dtVatTu = await this.getDSVatTuTonKho_V3({
              // "phanVungId": 26,
              "khoId": this.params.p_TuKho,
              "ngayCT": this.f_DateToString(this.params.p_dtpNgayDK, 'DD/MM/YYYY HH:mm:ss'),
              "chungTuId": 0,
              "kieu": 0,
              "tinhHienTai": this.$auth.getMaTinh(),
              "dataTonKho": JSON.stringify(table)
            })
          }
          else{
            this.dtVatTu = await this.getDSVatTuTonKho_V2({
              // "phanVungId": 26,
              "khoId": this.params.p_TuKho,
              "ngayCT": this.f_DateToString(this.params.p_dtpNgayDK, 'DD/MM/YYYY HH:mm:ss'),
              "chungTuId": 0,
              "kieu": this.kieu,
              "tinhHienTai": this.$auth.getMaTinh()
            })
          }
        }
        else if (this.kieu == 1){
          this.dtVatTu = await this.getDSVatTuTonKho_V2({
            // "phanVungId": 26,
            "khoId": this.params.p_TuKho,
            "ngayCT": this.f_DateToString(this.vngay_ct, 'DD/MM/YYYY HH:mm:ss'),
            "chungTuId": 0,
            "kieu": this.kieu,
            "tinhHienTai": this.$auth.getMaTinh()
          })
        }
        else if (this.kieu == 2){
          this.dtVatTu = await this.getDSTonKhoNhaCC({
            //  "phanVungId": 26,
            "khoId": this.params.p_TuKho,
            "ngayCT": this.f_DateToString(this.vngay_ct, 'DD/MM/YYYY HH:mm:ss'),
            "chungTuId": 0,
            "kieu": this.kieu,
            "tinhHienTai": this.$auth.getMaTinh()
          })
        }

        this.DS_Xuat = []
        if (this.dtVatTu.length){
          this.DS_Xuat = [] //dtVatTu.Clone() = []
          this.dtVT_Xuat = []
        }
        
        // Đức tự sinh Key cho lưới vì bảng này trùng VATTU_ID và LOHANG
        for(const i in this.dtVatTu){
          this.dtVatTu[i].KEY_ROW = i        
        }
        this.gridVatTu = this.dtVatTu
        this.convertData_gridVatTu()
        if (this.dtVatTu.length && this.dtVatTu[0].hasOwnProperty("MAKHOTD") && this.dtVatTu[0].hasOwnProperty("LOAIKHO")){
          this.repoLoaiKho = await this.getDSLoaiKho 
        }

        // this.VisibleCols_gridVatTu(['colSL_DD','colSL_KD'],false)
        // colSL_DD -> SL_DD
        // colSL_KD -> SL_KD
        this.gridThietBi_Columns.forEach(a=>{
          if ( ['SL_DD','SL_KD'].includes(a.fieldName) ){
            a.visible = false
          }
        })
        if (this.kieu != 1 && this.kieu != 2){
          this.dtThietBi = await this.getDSThietBiTonKho({khoId: this.params.p_TuKho})
          if (this.dtThietBi.length){
            if (this.dtThietBi[0].hasOwnProperty('SL_DD')){
              // this.VisibleCols_gridVatTu(['colSL_DD','colSL_KD'],true)
              this.gridThietBi_Columns.forEach(a=>{
                if ( ['SL_DD','SL_KD'].includes(a.fieldName) ){
                  a.visible = true
                }
              })
            }
          }
          this.gridThietBi = this.dtThietBi
          this.convertData_gridThietBi()
        }
      // } catch (error) {
      //   this.$toast.error(error)
      // }
    },
    async chkChonVT_CheckedChanged(){
      // label3.Focus();
    },
    tsbDanhSach_Click(){
      this.Enabled.tsbDanhSach = false
    },
    async gridViewVatTu_CellValueChanged(args){
      let gridViewVatTu = this.$refs.gridViewVatTu
      let changes = gridViewVatTu.getBatchChanges() 
      gridViewVatTu.batchUpdate(changes)
      // if (args.columnName == 'SL_XUAT'){
        // if (parseInt(args.value) > 0){
        //   let index = this.gridVatTu.findIndex(a=>a.VATTU_ID == args.rowData.VATTU_ID)
        //   this.indexArray.push(index)
        //   this.$refs.gridViewVatTu.grid.selectRows(indexArray,true)
        // }
        // else{
        //   let index = this.gridVatTu.findIndex(a=>a.VATTU_ID == args.rowData.VATTU_ID)
        //   this.indexArray.push(index)
        //   this.$refs.gridViewVatTu.grid.selectRows(indexArray,true)
        // }
      // }
      await this.LAY_DS_VTXUAT()
      
    },
    LAY_DS_VTXUAT(){
      this.DS_Xuat = []
      let DS = []
      let dem = 0
      let ds_check = this.gridVatTu
      
      let RowVatTuChecked = this.gridVatTu.filter(a=>this.DSVatTuChecked.includes(a.KEY_ROW))     
      // getSelectedRecords chưa update giá trị, nhưng datasource đã update
      for(let dr of RowVatTuChecked){
        // var dr = this.gridVatTu[i]
        let tien = parseFloat(dr["SL_XUAT"]) * parseFloat(dr["DONGIA"])
        let vat = tien / 10
        var vatTu = {}
       
        if (dr.hasOwnProperty('TRANGTHAI')){
          vatTu = {
            KEY_ROW: dr.KEY_ROW,
            CHON: dr["CHON"],
            LOHANG: dr["LOHANG"], 
            KHO_ID: dr["KHO_ID"], 
            VATTU_ID: dr["VATTU_ID"], 
            DONGIA: dr["DONGIA"], 
            DVITINH_ID: dr["DVITINH_ID"], 
            MA_VT: dr["MA_VT"],
            TEN_VT: dr["TEN_VT"], 
            DVI_TINH: dr["DVI_TINH"], 
            SL_TON: dr["SL_TON"], 
            LOAITBI_ID: dr["LOAITBI_ID"], 
            SL_TREO: dr["SL_TREO"], 
            LOAI_TBI: dr["LOAI_TBI"], 
            SOTHANG_PB: dr["SOTHANG_PB"], 
            SOLUONG: dr["SOLUONG"], 
            SL_XUAT: dr["SL_XUAT"], 
            THANHTIEN: tien, 
            VAT: vat, 
            TRANGTHAI: dr["TRANGTHAI"],
            LOAIKHO: dr["LOAIKHO"], 
            MAKHOTD: dr["MAKHOTD"], 
          }
        }
        else{
          vatTu =  {
            KEY_ROW: dr.KEY_ROW,
            CHON: dr["CHON"],
            LOHANG: dr["LOHANG"], 
            KHO_ID: dr["KHO_ID"], 
            VATTU_ID: dr["VATTU_ID"], 
            DONGIA: dr["DONGIA"], 
            DVITINH_ID: dr["DVITINH_ID"], 
            MA_VT: dr["MA_VT"],
            TEN_VT: dr["TEN_VT"], 
            DVI_TINH: dr["DVI_TINH"], 
            SL_TON: dr["SL_TON"], 
            LOAITBI_ID: dr["LOAITBI_ID"], 
            SL_TREO: dr["SL_TREO"], 
            LOAI_TBI: dr["LOAI_TBI"], 
            SOTHANG_PB: dr["SOTHANG_PB"], 
            SOLUONG: dr["SOLUONG"], 
            SL_XUAT: dr["SL_XUAT"], 
            THANHTIEN: tien, 
            VAT: vat, 
            LOAIKHO: dr["LOAIKHO"], 
            MAKHOTD: dr["MAKHOTD"], 
          }
        }
        let check =  this.gridXuat.filter(a=> a.KEY_ROW == dr.KEY_ROW)
        if(check.length == 0){
          this.gridXuat.push(vatTu)  
          this.DS_Xuat.push(vatTu)
        }else{
          for(let e of this.gridXuat){
            if(e.KEY_ROW == vatTu.KEY_ROW){
              e.SL_XUAT = vatTu.SL_XUAT
            }
          }
        }
        if(vatTu.SL_XUAT == 0){ // = 0 thì xóa
          let gridXuat_temp = []
          for(let e of this.gridXuat){

            if(e.KEY_ROW != vatTu.KEY_ROW){
              gridXuat_temp.push(e)
            }
          }
          this.gridXuat = gridXuat_temp
        }
      }
      // dem += 1
      this.$refs.gridviewXuat.grid.refreshColumns()
      this.convertData_gridXuat()
    },
    gridviewXuat_CellValueChanged(){

    },
    gridviewXuat_MouseDown(){

    },
    gridviewXuat_ValidateRow(){

    },
    async gridViewVatTu_MouseDown(args){
      
      this.DSVatTuChecked = args
      await this.LAY_DS_VTXUAT()
    },
    async tsbtnDangKy_Click(){
      // tableLayoutPanel1
      // try {
        if (this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU) && (this.dsQuyen.includes(916) || this.dsQuyen.includes(917))){
          this.Visible.splitContainer2.Panel2Collapsed = false
          // quy them
          this.Visible.ShowDKTT = false
          let bien = 100
          if(this.tinh.includes(this.$auth.getMaTinh())){
            bien = 101
          }
          var ds_dk = await this.getDSDangKy({
            "tuThangDk": "0",
            "denThangDk": "0",
            "ttdkId": bien,
            "donViId": -1,
            "tuNgay": this.f_DateToString(this.vngay_ct,'DD/MM/YYYY'),
            "denNgay": this.f_DateToString(new Date(this.vngay_ct.getFullYear() + 1, this.vngay_ct.getMonth(), this.vngay_ct.getDate()) ,'DD/MM/YYYY')
          })

          if (ds_dk.length ){
            let s = "Đăng ký đã tổng hợp"
            if(this.tinh.includes(this.$auth.getMaTinh())){
              s = "Đăng ký chưa tổng hợp";
            }
            var q = ds_dk.filter(l=> l["NHOM"].includes(s))
            this.grcDSDangKy = []
            this.grcDSVatTu_DaDK = []
            if ( q.length ){
              this.grcDSDangKy = q
              this.convertData_grcDSDangKy()

              if(this.tinh.includes(this.$auth.getMaTinh())){
                this.Visible.btnXoa = true
                this.Enabled.grvDSVatTu_DaDK.SL_XUAT = true
                this.Enabled.grvDSVatTu_DaDK.BODUYET = true
              }
              else{
                this.Visible.btnXoa = false
                this.Enabled.grvDSVatTu_DaDK.SL_XUAT = false
                this.Enabled.grvDSVatTu_DaDK.BODUYET = false
              }

            }
            else{
              this.grcDSVatTu_DaDK = []
              this.grcDSDangKy = []
            }

          }
        }
        else{
          this.$toast.error("Bạn không có quyền thuộc VT để dùng chức năng này")
        }
      // } catch (error) {
      //   "Có lỗi lấy danh sách đăng ký"
      // }
    },
    async picClose_Click(){
      this.Visible.splitContainer2.Panel2Collapsed = true
    },
    async HienThiDS_VTDK(){
      var ds_th = await this.getDSVatTuDK({dangKyId: this.grvDSDangKy_FocusedRow.DANGKY_ID})
      if (ds_th.length){
        this.grcDSVatTu_DaDK = ds_th
        this.convertData_grcDSVatTu_DaDK()
      }
      else{
        this.grcDSVatTu_DaDK = []
      }
    },
    async HienThiDS_VTTT(){
      var ds_th = await this.getDSVatTuDK({dangKyId: this.grvDK_TT_FocusedRow.DANGKY_ID})
      if (ds_th.length){
        this.grcCT_DK = ds_th
        this.convertData_grcCT_DK()
      }
      else{
        this.grcCT_DK = []
      }
    },
    grvDSVatTu_DaDK_FocusedRowChanged(){

    },
    async btnXoa_Click(){
      if(this.tinh.includes(this.$auth.getMaTinh())){
        await this.XoaDuLieuSLDuyetVT({dangKyId: this.grvDSDangKy_FocusedRow.DANGKY_ID})
      }
      await this.HienThiDS_VTDK()
      return
    },
    async grvDSVatTu_DaDK_CellValueChanged(){
      // lam sau
    },
    async grvDSDangKy_FocusedRowChanged(row){
      if (row){
        this.grvDSDangKy_FocusedRow = row
      }
      this.grcDSVatTu_DaDK = []
      
      await this.HienThiDS_VTDK()
    },
    async btnDel_ButtonClick(){
      // lam sau
    },
    grcDSVatTu_DaDK_KeyDown(){

    },
    async tsbtnPhieuTT_Click(){
      if (this.vkho_den_id != 0){
        if (this.dsQuyen.includes(this.DS_QUYEN_VT.HOANTHIEN_CHUNGTU) && (this.dsQuyen.includes(916) || this.dsQuyen.includes(917) || this.dsQuyen.includes(914))){
          this.Visible.splitContainer2.Panel2Collapsed = false
          // quy them
          this.Visible.ShowDKTT = true
        }
        let bien = 100;
        if(this.tinh.includes(this.$auth.getMaTinh())){
          bien = 101
        }
        let ds_dk = []
        if (!this.vchungtu_id){
          ds_dk = await this.getDSDangKyTheoDKTT({
            "tuThangDk": "0",
            "denThangDk": "0",
            "ttdkId": this.vkho_den_id,
            "donViId": -1,
            "tuNgay": this.f_DateToString(this.vngay_ct, 'DD/MM/YYYY'),
            "denNgay": this.f_DateToString(new Date(this.vngay_ct.getFullYear() + 1, this.vngay_ct.getMonth(), this.vngay_ct.getDate()) ,'DD/MM/YYYY')
          })
        }
        else{ 
          ds_dk = await this.getDSDangKyTheoDKTT({
            "tuThangDk": "1",
            "denThangDk": "0",
            "ttdkId": this.vkho_den_id,
            "donViId": -1,
            "tuNgay": this.f_DateToString(this.vchungtu_id, 'DD/MM/YYYY'),
            "denNgay": this.f_DateToString(new Date(this.vngay_ct.getFullYear() + 1, this.vngay_ct.getMonth(), this.vngay_ct.getDate()) ,'DD/MM/YYYY')
          })
        }

        if (ds_dk.length){
          this.grcDK_TT = ds_dk
          this.convertData_grcDK_TT()
          this.Visible.btnXoa = false
        }
        else{
          this.grcDK_TT = []
          this.grcCT_DK = []
        }
      }      
    },
    async repositoryItemCheckEdit4_CheckedChanged(){
      await this.HienThiDS_VTTT()
    },
    async pictureEdit1_Click(){
      this.Visible.splitContainer2.Panel2Collapsed = true
      this.coDS = false
    },
    async repositoryItemButtonEdit1_ButtonClick(){
      // try {
        await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn trả phiếu không?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                var q = this.grvDK_TT_FocusedRow.DANGKY_ID
                await this.CapNhatTraPhieu({dangKyId : q})

                this.$toast.success("Trả phiếu thành công")
                var ds_dk = []
                if (this.vchungtu_id == 0){
                  await this.getDSDangKyTheoDKTT({
                    "tuThangDk": "0",
                    "denThangDk": "0",
                    "ttdkId": this.vkho_den_id,
                    "donViId": -1,
                    "tuNgay": this.f_DateToString(this.vngay_ct, 'DD/MM/YYYY'),
                    "denNgay": this.f_DateToString(new Date(this.vngay_ct.getFullYear() + 1, this.vngay_ct.getMonth(), this.vngay_ct.getDate()) ,'DD/MM/YYYY')
                  })
                }
                else{
                  await this.getDSDangKyTheoDKTT({
                    "tuThangDk": "1",
                    "denThangDk": "0",
                    "ttdkId": this.vchungtu_id,
                    "donViId": -1,
                    "tuNgay": this.f_DateToString(this.vngay_ct, 'DD/MM/YYYY'),
                    "denNgay": this.f_DateToString(new Date(this.vngay_ct.getFullYear() + 1, this.vngay_ct.getMonth(), this.vngay_ct.getDate()) ,'DD/MM/YYYY')
                  })
                }

                if (this.ds_dk.length){
                  this.grcDK_TT = ds_dk
                  this.convertData_grcDK_TT()
                  this.Visible.btnXoa = false
                }
                else{
                  this.grcDK_TT = []
                  this.grcCT_DK = []

                }
              }
          })
          .catch(err => {
              // An error occurred
          })

      // } catch (error) {
        
      // }
    },
    async cboTuKho_ButtonClick(){
      await this.LOAD_DS_TK()
    },
    gridViewVatTu_CustomSummaryCalculate(){

    },
    gridViewVatTu_MouseUp(){

    },
    gridviewXuat_CustomSummaryCalculate(){

    },
    gridviewXuat_MouseUp(){

    },
    gridViewThietBi_CustomRowCellEdit(){

    },
    async repositoryItemPopupContainerEdit1_Click(){
      // try {
        this.grcTT = await this.getDSChuaHoanTamUng({khoId: this.params.p_TuKho, thietBiId: this.gridViewThietBi_FocusedRow.THIETBI_ID})
      // } catch (error) {
      //   this.$toast.error()
      // }
    },
    async ckbLinhVuc_TK_CheckedChanged(){
      this.Enabled.cboLinhVuc_TK = this.params.p_ckbLinhVuc_TK
      this.Enabled.cboHeThong_TK = this.params.p_ckbLinhVuc_TK
      if (this.params.p_ckbLinhVuc_TK){
        await this.NAP_DS_DONG_TBI()
      }
    },
    async ckbHangSX_TK_CheckedChanged(){
      this.Enabled.cboHangSX_TK = this.params.p_ckbHangSX_TK
      this.Enabled.cboDongTbi_TK = this.params.p_ckbHangSX_TK
      if (this.params.p_ckbHangSX_TK){
        await this.NAP_DS_DONG_TBI()
      }
    },
    async cboLinhVuc_TK_EditValueChanged(){
      // try {
        this.cboHeThong_TK = []
        if (this.params.p_LinhVuc_TK){
          this.cboHeThong_TK = await this.getDSHeThongVatTu({linhVucId: this.params.p_LinhVuc_TK})
          this.cboHeThong_TK.length ? this.params.p_HeThong_TK = this.cboHeThong_TK[0].heThongId : ''
        }
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi nạp danh sách hệ thống")
      // }
    },
    async cboHangSX_TK_EditValueChanged(){
      await this.NAP_DS_DONG_TBI()
    },
    async NAP_DS_DONG_TBI(){
      // try {
        this.cboDongTbi_TK = []
        if (this.params.p_HangSX_TK){
          this.cboDongTbi_TK = await this.getDSDongThietBi({
            "hangSxId": this.params.p_HangSX_TK,
            "heThongId": this.params.p_HeThong_TK,
            "checkLinhVuc": this.params.p_ckbLinhVuc_TK ? 1 : 0
          })
          this.cboDongTbi_TK.length ? this.params.p_cboDongTbi_TK = this.cboDongTbi_TK[0].dongTbiId : ''
        }
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi nạp danh sách hệ thống")
      // }
    },
    async cboHeThong_TK_EditValueChanged(){
      await this.NAP_DS_DONG_TBI()
    },
    async btnFilter_Click(){
      await this.LOAD_DS_TK()
    },

    async Close(){
      this.$parent.hasOwnProperty('XacNhan_frmVatTuTrongKho') ? await this.$parent.XacNhan_frmVatTuTrongKho() : await this.$parent.$parent.XacNhan_frmVatTuTrongKho()
      this.$refs.popup_frmVatTuTrongKho.hide()
    },

    async ShowDialog(){
      this.$refs.gridViewVatTu.grid.clearSelection() // clear lưới vật tư đã chọn
      this.$refs.popup_frmVatTuTrongKho.show()
    },

    async Clear(){
      this.gridVatTu = []
      this.gridXuat = []
    },
    openDialog(){
      this.$refs.popup_frmVatTuTrongKho.show()
    },
    convertData_gridVatTu(){
      this.gridVatTu.forEach(item=>{
        item.SL_TREO != null && item.SL_TREO.toString() ? item.SL_TREO = parseInt(item.SL_TREO) : 0
        item.SL_TON != null && item.SL_TON.toString() ? item.SL_TON = parseFloat(item.SL_TON) : 0
        item.DONGIA != null && item.DONGIA.toString() ? item.DONGIA = parseFloat(item.DONGIA) : 0
        item.SL_TAMUNG != null && item.SL_TAMUNG.toString() ? item.SL_TAMUNG = parseInt(item.SL_TAMUNG) : 0
        item.SOLUONG != null && item.SOLUONG.toString() ? item.SOLUONG = parseFloat(item.SOLUONG) : 0
        item.SL_C_DD != null && item.SL_C_DD.toString() ? item.SL_C_DD = parseFloat(item.SL_C_DD) : 0
        item.SL_XUAT != null && item.SL_XUAT.toString() ? item.SL_XUAT = parseFloat(item.SL_XUAT) : 0
        item.SL_TONTHUC != null && item.SL_TONTHUC.toString() ? item.SL_TONTHUC = parseFloat(item.SL_TONTHUC) : 0
        item.VAT != null && item.VAT.toString() ? item.VAT = parseFloat(item.VAT) : 0
        item.THANHTIEN != null && item.THANHTIEN.toString() ? item.THANHTIEN = parseFloat(item.THANHTIEN) : 0
      })
      console.log(this.gridVatTu)
    },
    convertData_gridThietBi(){
      this.gridThietBi.forEach(item=>{
        item.SOLUONG != null && item.SOLUONG.toString() ? item.SOLUONG = parseFloat(item.SOLUONG) : ''
        item.SL_DD != null && item.SL_DD.toString() ? item.SL_DD = parseFloat(item.SL_DD) : ''
        item.SL_KD != null && item.SL_KD.toString() ? item.SL_KD = parseFloat(item.SL_KD) : ''
        item.DONGIA != null && item.DONGIA.toString() ? item.DONGIA = parseFloat(item.DONGIA) : ''
        item.THANHTIEN != null && item.THANHTIEN.toString() ? item.THANHTIEN = parseFloat(item.THANHTIEN) : ''
      })
    },
    
    convertData_gridXuat(){
      this.gridXuat.forEach(item=>{
        item.SOLUONG != null && item.SOLUONG.toString() ? item.SOLUONG = parseFloat(item.SOLUONG) : ''
        item.SL_XUAT != null && item.SL_XUAT.toString() ? item.SL_XUAT = parseFloat(item.SL_XUAT) : ''
        item.DONGIA != null && item.DONGIA.toString() ? item.DONGIA = parseFloat(item.DONGIA) : ''
        item.THANHTIEN != null && item.THANHTIEN.toString() ? item.THANHTIEN = parseFloat(item.THANHTIEN) : ''
      })
    },
    convertData_grcDSDangKy(){
      this.grcDSDangKy.forEach(item=>{
        item.CONLAI != null && item.CONLAI.toString() ? item.CONLAI = parseFloat(item.CONLAI) : ''
        
      })
    },
    convertData_grcDSVatTu_DaDK(){
      this.grcDSVatTu_DaDK.forEach(item=>{
        item.SL_TON_DK != null && item.SL_TON_DK.toString() ? item.SL_TON_DK = parseFloat(item.SL_TON_DK) : ''
        item.SOLUONG != null && item.SOLUONG.toString() ? item.SOLUONG = parseFloat(item.SOLUONG) : ''
        item.SL_XUAT != null && item.SL_XUAT.toString() ? item.SL_XUAT = parseFloat(item.SL_XUAT) : ''
        item.SL_DUYET != null && item.SL_DUYET.toString() ? item.SL_DUYET = parseFloat(item.SL_DUYET) : ''
      })
    },

    convertData_grcDK_TT(){
      this.grcDK_TT.forEach(item=>{
        item.CONLAI != null && item.CONLAI.toString() ? item.CONLAI = parseFloat(item.CONLAI) : ''
      })
    },
    convertData_grcCT_DK(){
      this.grcCT_DK.forEach(item=>{
        item.SL_TON_DK != null && item.SL_TON_DK.toString() ? item.SL_TON_DK = parseFloat(item.SL_TON_DK) : ''
        item.SOLUONG != null && item.SOLUONG.toString() ? item.SOLUONG = parseFloat(item.SOLUONG) : ''
        item.SL_XUAT != null && item.SL_XUAT.toString() ? item.SL_XUAT = parseFloat(item.SL_XUAT) : ''
        item.SL_DUYET != null && item.SL_DUYET.toString() ? item.SL_DUYET = parseFloat(item.SL_DUYET) : ''
      })
    },
    

  },
  created: async function() {
    // await this.frmVatTuTrongKho_Load() 
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>
  .xuly-thietbibaohanh .e-grid tr.e-emptyrow td {
      text-align: center !important;
    }
  .vattu_box {
    position: relative;
  }
  
  .vattu_table {
      position: absolute;
      z-index: 2;
      max-height: 300px;
      background: aliceblue;
      overflow-y: scroll;
      right: 0;
  }
  #tabA .e-grid .e-content {
    max-height: 500px !important;
    display: block !important;
  }
</style>