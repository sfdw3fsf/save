<template src='./LenhDieuChuyen.html'></template>
<style scoped src='./LenhDieuChuyen.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import Vue from "vue";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/nhapdenghivattu";
import select2 from "@/components/Select2.vue";
import { Query,Predicate } from '@syncfusion/ej2-data';
import {previewPrint} from "../../../utils/util";
import EventBus from '@/utils/eventBus'
import {
  TreeGridPlugin,
  Page,
  Resize,
  Edit,
} from "@syncfusion/ej2-vue-treegrid";
import frmGiaoPhieu from "../frmGiaoPhieu/frmGiaoPhieu.vue";
import ChonChungTuQT from "../ChonChungTuQT/ChonChungTuQT.vue";
import { L10n } from "@syncfusion/ej2-base";
import frmChungTu_TBao from '../frmChungTu_TBao'
L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
      Item: "Bản ghi",
      Items: "Bản ghi",
    },
    pager: {
      currentPageInfo: "{0} trên {1} trang",
      totalItemsInfo: "({0} Bản ghi)",
      pagerDropDown: "Bản ghi trên trang",
    },
  },
});
Vue.use(TreeGridPlugin);
export default {
  components: { breadcrumb, appSelect2: select2, frmGiaoPhieu,ChonChungTuQT,frmChungTu_TBao },
  name: "NhapDeNghiVatTu",
  mounted() {},

  computed: {
    ...mapState("nhapdenghivattu", statePropertyName),
    ...mapGetters("nhapdenghivattu", getterName),
  },
  provide: {
    treegrid: [ Edit, Page,Resize],
  },
  data() {
    return {
      

      params: {
        cboTimKiem : null,
        txtSoPhieu: "",
        txtGhiChu: "",
        txtND_DeNghi: "",
        cboTrangThai: null,
        cboTuKho: null,
        cboDenKho: null,
        dtpNgayYC: new Date(),
        cboLoaiPhieu: "0",
        daNop: false,
        chuaNop: true,
        hoanThanh:false,
        dtpDenNgay: new Date(),
        dtpTuNgay: new Date((new Date()).getTime() - 7*24*60*60*1000), //ngày yêu cầu từ
        p_DonViFMIS:0,
        donViGiao: null,
        donViNhan: null,
        tuTinh: null,
        denTinh: null
      },

      thongTinNguoiDung: {
        nhanVienId: this.$root.token.getNhanVienID(),
        nguoiCN: this.$root.token.getUserName(),
        mayCN: "", //this.$auth.MAYCN
        ipCN: "", //this.$auth.IPCN
        donViId: this.$root.token.getDonViID(),
        phanVungId: this.$root.token.getPhanVungID(),
        maNguoiDung: this.$root.token.getNguoiDungID(),
        MaTinh: this.$root.token.getMaTinh(),
      },
      animationSettings: { effect: 'Zoom' },
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      Visible: {
        tsbtnNhapMoi : true,
        tsbtnGhiLai: true,
        tsbtnXoa: true,
        tsbtnHuyBo: true,
        gcSelection: true,
        gcDelete: true,
        tsbtnChonChungTu: true,
        tsbtnXoaChungTu : true
      },
      Enabled: {
        tsbtnNhapMoi : false,
        tsbtnGhiLai: false,
        tsbtnXoa: false,
        tsbtnHuyBo: false,
        cboTuKho: true,
        cboDenKho: true,
        cboDonViGiao: true,
        cboDonViNhan: true,
        grpDSVTCDK: false,
        btnDelete_VTDK: true,
        tsbtnDongBoFMIS:false,
        tsbtnXoaDongBoFMIS: false,
        tsbtnXoaChungTu: false,
        tsbtnChonChungTu: false
      },
      AllowEdit:{
        colSLLD: true,
        colGHICHU: true
      },
      DSTinh: [],
      DSDonViDeNghi: [],
      DSFile: [],
      listChungTuChecked : [],
      cboDonvi_FMIS: [],
      DSChungTu: [],
      DSDangKy: [],
      DSVatTu_DaDK: [],
      DSVatTu_DaDK_Checked: [],
      DSVatTu: [],
      DSVatTuOrigin : [],
      cboTimKiem: [{
        TEN_VT : "",
        VATTU_ID : null
      }],
      cboTuKho: [],
      cboDenKho: [],
      cboKho_DVVT: [],
      cboLoaiPhieu : [],
      cboTrangThai: [],

      chkBoChonKhoTu : false,
      chkBoChonKhoDen: false,
      chkBoChonDVGiao : false,
      chkBoChonDVNhan: false,
      tabDK_DeNghi:true,
      denghi_id : 0,
      quytrinh_id : 0,
      huonggiao_id: 0,
      ma_denghi: "",
      loaiphieu_id : 0,
      mucdich_id : 0,
      vattu_id: 0,
      ten_vt: "",
      dvitinh_id: 0,
      vtdk_id: 0,
      tagForm: "",
      s : "",
      // dsVATTUDK : [], // Đức bỏ bớt biến này
      isFirstRow: true,

      vchungtu_td_id: 0,
      ttdb_id:0,
      vloaiphieu_id: 0,
      vphieu_dn_id: 0,
      nhomphieu_id: 0,
      URL : "http://10.70.52.164:8081/FILE/",
      khoto: false,
      kiemtra_quyen: false,
      copy_dk: false,
      chondvi_fmis: false,
      DangKySelected: {},
      ChungTuSelected: {},
      editSettings: { 
        allowEditing: true,
        mode: "Batch"
      },
      selectionSettings: {
        type: "Multiple",
        checkboxOnly: true,
      },
      pageSettings: { pageSizes: ['10','20','50','100','All'], pageSize: 10 },
      onFilterCboKho: function(e) {
        var query = new Query();
        let predicate;  
        if (e.text !== ''){
          predicate  = new Predicate('tentinh', 'contains', e.text.trim(), true);              
          query.where(predicate)
        }
        e.updateData(this.dataSource, query);
      },
      onFilterCboDvi: function(e) {
        var query = new Query();
        let predicate;  
        if (e.text !== ''){
          predicate  = new Predicate('TEN_DV', 'contains', e.text.trim(), true);              
          query.where(predicate)
        }
        e.updateData(this.dataSource, query);
      },
      onFilterCboTimKiem: function(e) {
        var query = new Query();
        let predicate;  
        if (e.text !== ''){
          predicate  = new Predicate('TEN_VT', 'contains', e.text.trim(), true);              
          query.where(predicate)
        }
        e.updateData(this.dataSource, query);
      },
      deleteTemplate: function () {
        return { template : Vue.component('columnTemplate', {
            template: `<div>
                          <button class="btn btn-danger lh14 pad3" @click="onDeleteRow" title="Xóa vật tư">
                            <span class="nc-icon-outline ui-1_trash-simple"></span>
                          </button>
                      </div>`,                        
            data () { 
              return { data: {} }; 
            },
            methods: {
              onDeleteRow() {                  
                  EventBus.$emit('onDeleteRow', this.data);
              },              
            }
          })
        }
      },
      btnTBTemplate: function () {
        return {
          template: Vue.component('btnTBTemplate', {
              template: `<button class="btn btn-main pad4 lh14 w60 btn-primary"  @click="btnTB_ButtonClick">
                            <span class="act -ap icon-chevron-thin-down"></span>
                        </button>`,
              data: function () { return { data: {} }; },
              
              methods:{
                async btnTB_ButtonClick(){
                  await this.$parent.$parent.$parent.btnTB_ButtonClick(this.data)
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
    };
  },
  methods: {
    ...mapActions("nhapdenghivattu", actionName),
    ...mapMutations("nhapdenghivattu", mutationName),

    // chuyển Date thành type string
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    async frmNhapDeNghiVT_Load(){
      let diachi = "WinUI.WinUIQuanLyVatTu.frmNhapDeNghiVT"
      // Lấy tagForm and tên label

      await this.SetButton(0)
      await this.Load_cbo()
      this.cboDonvi_FMIS = await this.getDSDonViFMIS()      
      await this.HienThiDSDK()
    },
    gridviewChungTu_FocusedRow(row){
      this.ChungTuSelected = row
    },
    async btnTB_ButtonClick(data){
      var frm =  this.$refs.frmChungTu_TBao
      if(parseInt(this.ChungTuSelected.SPBH) > 0){
        frm.kieu = 1
      }  
      frm.chungtu_id = data.CHUNGTU_ID //233254
      await frm.frmChungTu_TBao_Load()
      frm.ShowDialog()
    },
    async Load_cbo(){
      // let DSKho = await this.getDSKhoDenNghiVT()      
      // this.cboTuKho = DSKho
      // this.cboDenKho = DSKho

      // this.cboKho_DVVT = await this.getDSKhoDonViVT() 
    
      this.DSTinh = await this.getDSTinh()
      console.log(this.DSTinh , " = DSTinh")
      this.DSVatTuOrigin = await this.getDSVatTuMucDich()
      this.DSVatTuOrigin.forEach(e=>{
        this.cboTimKiem.push({
          TEN_VT : e.MA_VT + ' - ' + e.TEN_VT,
          VATTU_ID : e.VATTU_ID
        })
      })
     
      this.DSVatTu = this.createTree(this.DSVatTuOrigin.slice(0,100)) // ĐỨc hardcode giảm thời gian loading grid
     
      let DSThamSoMacDinh = await this.getDSThamSoMacDinh()
   
      if(DSThamSoMacDinh.length > 0){
        this.khoto = this.setThamSo("LAYKHO_KT_TT",DSThamSoMacDinh)
        this.kiemtra_quyen = this.setThamSo("KIEMTRA_QUYENXN",DSThamSoMacDinh)
        this.ten_ts = this.setThamSo("QLVT_COPY_DK",DSThamSoMacDinh)
        this.chondvi_fmis = this.setThamSo("CHONDVI_FMIS",DSThamSoMacDinh)
      }

      this.cboLoaiPhieu = await this.getDSLoaiPhieu({
        nhanVienId: this.thongTinNguoiDung.nhanVienId,
        kieu: 10, // Lấy loại phiếu = 5
        param1: "",
        param2: ""
      })  
      
      // this.DSDonViDeNghi = await this.getDSDonViDeNghi()
    },
    setThamSo(maTS,ds){
      let ds_filter = ds.filter(a=>a.maTS == maTS && a.tenTS == "1" )
      if (ds_filter.length > 0){
        return true
      }
      return false
    },
    async SetButton(kieu){
      this.Enabled.tsbtnNhapMoi = false;
      this.Enabled.tsbtnGhiLai = false;
      this.Enabled.tsbtnXoa = false;
      this.Enabled.tsbtnHuyBo = false;  
      if(kieu == -1){
        this.Enabled.tsbtnGhiLai = true;
        this.Enabled.tsbtnHuyBo = true;
      }else if(kieu == 0){
        this.Enabled.tsbtnNhapMoi = true;
        this.Enabled.tsbtnXoa = true;
        this.Clear()
        if(this.DSDangKy.length > 0){
          await this.FocusGridViewRow(this.DSDangKy[0].DENGHI_ID)
        }
      }else if(kieu == 1){
        this.denghi_id = 0
        this.Enabled.tsbtnGhiLai = true;
        this.Enabled.tsbtnHuyBo = true;
        this.Clear()
        this.Enabled.tsbtnXoaChungTu = false
        this.Enabled.tsbtnChonChungTu = this.nhomphieu_id == 4        
      }
    },
    async FocusGridViewRow(value){
      let indexs = this.DSDangKy.findIndex(a=>a.DENGHI_ID != null && a.DENGHI_ID.toString() == value.toString())
      if (indexs != -1) {
        if (!(
            (indexs + 1) > (this.$refs['grvDSDangKy'].pageIndexData * this.$refs['grvDSDangKy'].pageSizeData) &&
            ((indexs + 1) <= ((this.$refs['grvDSDangKy'].pageIndexData + 1 ) * this.$refs['grvDSDangKy'].pageSizeData))
            )){
            this.$refs['grvDSDangKy'].$refs.pagination.gotoPage(parseInt(indexs /this.$refs['grvDSDangKy'].pageSizeData ))
        }
        let self = this
        let ind = parseInt(indexs%this.$refs['grvDSDangKy'].pageSizeData)
        setTimeout(() => {
          self.$refs['grvDSDangKy'].grid.selectRow(ind,true)
        }, 1000);
      }
    },
    Clear(){
      this.params.tuTinh = null
      this.params.denTinh = null
      this.denghi_id = 0
      this.params.cboTimKiem = null,
      this.params.txtSoPhieu = "",
      this.params.txtGhiChu = "",
      this.params.txtND_DeNghi = "",
      this.params.cboTrangThai = "1",
      this.params.cboTuKho = null,
      this.params.cboDenKho = null,
      this.params.donViGiao = null,
      this.params.donViNhan = null,
      this.params.dtpNgayYC = new Date()
      // this.dsVATTUDK = []
      this.DSVatTu_DaDK = []
      this.DSChungTu = []
      this.s = ""
      this.DSFile = []
    },
    async HienThiDSDK(){
      try{
        var loaiPhieuId = this.params.hoanThanh ? this.params.cboLoaiPhieu : 0
        var ttDnId = 0 // chưa nộp
        if(this.params.daNop){
          ttDnId = 1
        }
        if(this.params.hoanThanh){
          ttDnId = 2
        }
        this.DSDangKy = await this.getDSDeNghiLuanChuyen({
          loaiPhieuId: loaiPhieuId,
          ttDnId: ttDnId,
          tuNgay: this.f_DateToString(this.params.dtpTuNgay),
          denNgay: this.f_DateToString(this.params.dtpDenNgay)
        })

        if(this.DSDangKy.length == 0){
          this.DangKySelected = {}
          this.DSChungTu = []
          this.ChungTuSelected = {}
          this.Enabled.tsbtnChonChungTu = false
          this.Enabled.tsbtnXoaChungTu = false
          this.SetButton(-1)
        }else{
          await this.FocusGridViewRow(this.DSDangKy[0].DENGHI_ID)
          this.Enabled.tsbtnChonChungTu = true
          this.Enabled.tsbtnXoaChungTu = true
        }
      }catch(e){        
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async grvDSDangKySelectRow(row){
      try{
        if(row){
          this.loading(true)
          this.DangKySelected = row
          this.Enabled.tsbtnNhapMoi = true
          await this.HienThiDeNghi_CT()    
          await this.getDSFileCV(row.DENGHI_ID)      
        }else{
          this.Clear()
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async getDSFileCV(denghi_id){
      this.DSFile = []
      let DSFile_response = await this.getDSFile(denghi_id)

      if(DSFile_response == null || DSFile_response == ""){
        return
      }
      DSFile_response = JSON.parse(DSFile_response);     
      if(DSFile_response.length > 0){
        var urlSplit = DSFile_response[0].url.split("/")
        this.DSFile.push({
          fileName:urlSplit[urlSplit.length - 1],
          urlFile: DSFile_response[0].url
        })
      }       
    },
    async HienThiDeNghi_CT(){
      this.Clear()
      if(this.DangKySelected != {}){
        this.DSVatTu_DaDK = []
        this.Enabled.tsbtnGhiLai = true
        this.denghi_id = this.DangKySelected.DENGHI_ID
        this.params.p_DonViFMIS = this.DangKySelected.DONVI_ID
        this.vchungtu_td_id = this.DangKySelected.CHUNGTU_TD_ID
        this.ttdb_id = this.DangKySelected.STATUS
        this.vloaiphieu_id = this.DangKySelected.LOAIPHIEU_ID
        this.vphieu_dn_id = this.DangKySelected.DENGHI_ID
        this.params.tuTinh = parseInt(this.DangKySelected.TINH_GIAO_ID)
        this.params.denTinh = parseInt(this.DangKySelected.TINH_NHAN_ID)
        this.params.txtSoPhieu = this.DangKySelected.MA_DN

        if(this.DangKySelected.NHOMPHIEU_ID == "4"){
          this.params.cboTuKho = null
          this.params.cboDenKho = null
          this.chkBoChonKhoTu = true
          this.chkBoChonKhoDen = true
        }else{
          if(this.DangKySelected.KHO_GIAO_ID == null || this.DangKySelected.KHO_GIAO_ID == "-1"){
            this.params.cboTuKho = null
            this.Enabled.cboTuKho = false
            this.chkBoChonKhoTu = true
          }else{
            this.params.cboTuKho = this.DangKySelected.KHO_GIAO_ID
            this.Enabled.cboTuKho = true
            this.chkBoChonKhoTu = false
          }

          if(this.DangKySelected.KHO_NHAN_ID == null || this.DangKySelected.KHO_NHAN_ID == "-1"){
            this.params.cboDenKho = null
            this.Enabled.cboDenKho = false
            this.chkBoChonKhoDen = true
          }else{
            this.params.cboDenKho = this.DangKySelected.KHO_NHAN_ID
            this.Enabled.cboDenKho = true
            this.chkBoChonKhoDen = false
          }
        }
       
        this.params.txtND_DeNghi = this.DangKySelected.ND_DENGHI
        this.params.txtGhiChu = this.DangKySelected.GHICHU      
        this.params.cboLoaiPhieu = this.DangKySelected.LOAIPHIEU_ID == null ? "0" : this.DangKySelected.LOAIPHIEU_ID.toString()
        this.params.cboTrangThai = this.DangKySelected.TTDN_ID == null ? "1" : this.DangKySelected.TTDN_ID.toString()    
        this.params.donViGiao = this.DangKySelected.DONVI_GIAO_ID == "" ? null : parseInt(this.DangKySelected.DONVI_GIAO_ID) 
        this.params.donViNhan = this.DangKySelected.DONVI_NHAN_ID == "" ? null : parseInt(this.DangKySelected.DONVI_NHAN_ID)   
        this.params.dtpNgayYC = new Date(this.convertDateTime_DDMMYYYY_to_YYYYMMDD(this.DangKySelected.NGAY_YC))
        if(this.DangKySelected.NHOMPHIEU_ID == "4"){
          await this.NAP_DS_DENGHI_CHUNGTU()
        }else{
          this.DSVatTu_DaDK = await this.getDSVatTuDaDk(this.denghi_id)
        }
        
      }
    },
    async NAP_DS_DENGHI_CHUNGTU(){
      this.DSChungTu = await this.getDSChungTuHoanUng({
        kieu: 2,
        donViId: 0,
        tuNgay: "",
        denNgay: "",
        deNghiId: this.denghi_id,
        dsCt: [{}]
      })
      if(this.DSChungTu.length == 0){
        this.ChungTuSelected = {}
      }      
    },
    async cboLoaiPhieu_EditValueChanged(){
      try{       
        if(this.params.cboLoaiPhieu == "0"){
          return
        }
        this.nhomphieu_id = 0
        this.Enabled.tsbtnChonChungTu = true
        this.Enabled.tsbtnXoaChungTu = true
        if(this.params.hoanThanh){
          this.DSVatTu_DaDK = []
          this.HienThiDSDK();
        }
      
        var checkTab = this.cboLoaiPhieu.filter(a=> a.LOAIPHIEU_ID == this.params.cboLoaiPhieu && a.NHOMPHIEU_ID == 4)     
        this.tabDK_DeNghi = checkTab.length == 0
        this.Visible.tsbtnChonChungTu = checkTab.length > 0
        this.Visible.tsbtnXoaChungTu = checkTab.length > 0
        this.nhomphieu_id = checkTab.length == 0 ? this.nhomphieu_id : 4
        
        this.cboTrangThai = await this.getDSLoaiPhieu({
          nhanVienId: this.thongTinNguoiDung.nhanVienId,
          kieu: 3,
          param1: this.params.cboLoaiPhieu,
          param2: ""
        })
      }catch(e){        
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    convertDateTime_DDMMYYYY_to_YYYYMMDD(datetime){
      datetime = datetime.split(" ")
      var thisDate = datetime[0].includes("/") ? datetime[0].split('/') : datetime[0].split('-')
      var newDate = [thisDate[2],thisDate[1],thisDate[0]].join("/") ;
      if(datetime.length == 2){ // có thời gian
        newDate = newDate + " " + datetime[1]
      }    
      return newDate
    },
    async rdgDaNop_SelectedIndexChanged(kieu){  
      try{
        this.loading(true)
        this.params.chuaNop = kieu == 0
        this.params.daNop = kieu == 1
        this.params.hoanThanh = kieu == 2

        if(kieu == 2){
          this.Enabled.tsbtnGiaoPhieu = false
          this.Enabled.tsbtnXemDN = false
          this.Enabled.tsbtnChonChungTu = false
          this.Enabled.tsbtnXoaChungTu = false
          this.Enabled.tsbtnDongBoFMIS = true
          this.Enabled.tsbtnXoaDongBoFMIS = true
        }else{
          this.Enabled.tsbtnGiaoPhieu = true
          this.Enabled.tsbtnXemDN = true
          this.Enabled.tsbtnChonChungTu = true
          this.Enabled.tsbtnXoaChungTu = true
          this.Enabled.tsbtnDongBoFMIS = false
          this.Enabled.tsbtnXoaDongBoFMIS = false
        }
        this.Clear()
        this.isFirstRow = true
        var ht = kieu == 0 
        this.Visible.tsbtnNhapMoi =  ht  
        this.Visible.tsbtnGhiLai =  ht
        this.Visible.tsbtnHuyBo =  ht
        this.Visible.tsbtnXoa =  ht

        this.Enabled.grpDSVTCDK =  ht
        this.Visible.gcSelection =  ht
        this.Visible.gcDelete =  ht
        
        this.AllowEdit.colSLLD =  ht
        this.AllowEdit.colGHICHU =  ht
        this.Enabled.btnDelete_VTDK =  ht

        this.DSVatTu_DaDK = []
        await this.HienThiDSDK()
        
      }catch(e){        
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    chkBoChonKhoTu_CheckedChanged(){
      this.Enabled.cboTuKho = !this.chkBoChonKhoTu
      this.params.cboTuKho = this.chkBoChonKhoTu ? null :  this.params.cboTuKho
    },
    chkBoChonDenTu_CheckedChanged(){
      this.Enabled.cboDenKho = !this.chkBoChonKhoDen
      this.params.cboDenKho = this.chkBoChonKhoDen ? null :  this.params.cboDenKho
    },
    chkBoChonDviGiao_CheckedChanged(){
      this.Enabled.cboDonViGiao = !this.chkBoChonDVGiao
      this.params.donViGiao = this.chkBoChonDVGiao ? null :  this.params.donViGiao
    },
    chkBoChonDviNhan_CheckedChanged(){
      this.Enabled.cboDonViNhan = !this.chkBoChonDVNhan
      this.params.donViNhan = this.chkBoChonDVNhan ? null :  this.params.donViNhan
    },
   
    async LayThongTin_Click(){
      try{
        this.loading(true)
        await this.HienThiDSDK()
      }catch(e){        
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbtnNhapMoi_Click(){
      this.SetButton(1);
    },
    tsbtnHuyBo_Click(){
      this.loading(true)
      this.SetButton(0);
      this.loading(false)
    },
    async tsbtnXoa_Click(){
      try{
        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Bạn có muốn xóa dữ liệu không ?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
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
        var ds_vattu = []
       
        this.DSVatTu_DaDK.forEach(e=>{
          ds_vattu.push({
            DENGHI_ID : this.denghi_id,
            VATTU_ID : e.VATTU_ID,
            VAT : e.VAT,
            TIEN : e.TIEN,
            SOLUONG : e.SOLUONG,
            DONGIA : e.DONGIA,
          })
        })
      
        
        let ds_dn_luanchuyen = [{
          DENGHI_ID : this.Enabled.tsbtnNhapMoi ? this.denghi_id : 0,
          MA_DN: this.params.txtSoPhieu,
          ND_DENGHI: this.params.txtND_DeNghi,
          TTDN_ID: this.params.cboTrangThai,
          GHICHU: this.params.txtGhiChu,
          QUYTRINH_ID: this.quytrinh_id,
          NGAY_CN: this.f_DateToString(new Date(), 'DD/MM/YYYY HH:mm:ss'),
          NGUOI_CN : this.$auth.getUserName(),          
          LOAIPHIEU_ID  : this.params.cboLoaiPhieu,
          NGAY_YC  : this.f_DateToString(this.params.dtpNgayYC, 'DD/MM/YYYY HH:mm:ss'),
          TINH_GIAO_ID  : this.params.tuTinh,
          TINH_NHAN_ID  : this.params.denTinh,
        }]

        var ketQua = await this.themSuaXoaDieuChuyen({
            kieu : 3, // 3 = Xóa
            ds_dn_luanchuyen : ds_dn_luanchuyen,
            ds_ct_vattu: ds_vattu,
            nhanvien_id: this.$auth.getNhanVienID(),
            donvi_id: this.$auth.getDonViID(),           
        })    
     
        this.$toast.success("Xóa dữ liệu đề nghị thành công !")
        this.SetButton(0)
        await this.HienThiDSDK()
        if(this.DSDangKy.length > 0){
          await this.FocusGridViewRow(this.DSDangKy[0].DENGHI_ID)
        }
              
      }catch(e){        
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbtnGiaoPhieu_Click(){     
      if(this.denghi_id > 0 ){
        let DSHuongGiaoTiepTheo = await this.getHuongGiaoTiepTheo(this.denghi_id)      
        if(DSHuongGiaoTiepTheo.length > 0){         
          let frm = this.$refs.frmGiaoPhieu
          frm.frmGiaoPhieuHG(DSHuongGiaoTiepTheo[0].HUONGGIAO_ID)
          frm.parentForm = "NhapDeNghiVatTu"
          frm.openDialog()
          
        }else{
          this.$toast.error("Quy trình chưa được thiết lập")
        }        
      }else{
        this.$toast.error("Chưa chọn phiếu đề nghị!")
      }
    },
    async closeGiaoPhieu(){
      await this.HienThiDSDK();
      await this.HienThiDeNghi_CT();
    },
    async tsbtnXemDN_Click(){
      
      if(this.denghi_id == 0){
        this.$toast.error("Chưa chọn đề nghị");
        return
      }
      var maBc = ""
      if(this.params.cboLoaiPhieu == 3){
        maBc = "02-QLVT-ML"
      }else if(this.params.cboLoaiPhieu == 7){
        maBc = "03-QLVT-C"
      }else{
        this.$toast.error("không có phiếu in cho loại phiếu này!")
        return
      }
      this.loading(true)
      const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-qlvt/api/bao-cao/bc-de-nghi?extend=pdf`,
          method: "POST",
          responseType: "blob",
          data: {
            chungTuId: this.denghi_id,    
            maBc: maBc,       
          }
        }).then((response) => {
          previewPrint(response.data);
        });
      this.loading(false)
    },
    async tsbtnGhiLai_Click(){
      try{
        if(this.KiemTra_DuLieu() == false){
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
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
        var currentDate = new Date()
        if(this.params.dtpNgayYC > currentDate){
          this.$toast.error("Ngày yêu cầu không được lớn hơn ngày hiện tại!");
          return;
        }

       
        var ds_vattu = []
        if(this.nhomphieu_id != 4){
          this.DSVatTu_DaDK.forEach(e=>{
            ds_vattu.push({
              DENGHI_ID : this.denghi_id,
              VATTU_ID : e.VATTU_ID,
              VAT : e.VAT,
              TIEN : e.TIEN,
              SOLUONG : e.SOLUONG,
              DONGIA : e.DONGIA,
            })
          })
        }else{
          this.DSChungTu.forEach(e=>{
            ds_vattu.push({
              CHUNGTU_ID : e.CHUNGTU_ID  
            })
          })
        }
        
        let ds_dn_luanchuyen = [{
          DENGHI_ID : this.Enabled.tsbtnNhapMoi ? this.denghi_id : 0,
          MA_DN: this.params.txtSoPhieu,
          ND_DENGHI: this.params.txtND_DeNghi,
          TTDN_ID: this.params.cboTrangThai,
          GHICHU: this.params.txtGhiChu,
          QUYTRINH_ID: this.quytrinh_id,
          NGAY_CN: this.f_DateToString(new Date(), 'DD/MM/YYYY HH:mm:ss'),
          NGUOI_CN : this.$auth.getUserName(),          
          LOAIPHIEU_ID  : this.params.cboLoaiPhieu,
          NGAY_YC  : this.f_DateToString(this.params.dtpNgayYC, 'DD/MM/YYYY HH:mm:ss'),
          TINH_GIAO_ID  : this.params.tuTinh,
          TINH_NHAN_ID  : this.params.denTinh,
        }]

        var ketQua = await this.themSuaXoaDieuChuyen({
            kieu : this.Enabled.tsbtnNhapMoi ? 2 : 1, // 1 nhập mới, 2 update
            ds_dn_luanchuyen : ds_dn_luanchuyen,
            ds_ct_vattu: ds_vattu,
            nhanvien_id: this.$auth.getNhanVienID(),
            donvi_id: this.$auth.getDonViID(),           
        })
        this.SetButton(0)
        if(this.Enabled.tsbtnNhapMoi){          
          this.$toast.success("Cập nhật đề nghị thành công !");
        }else{
          this.$toast.success("Thêm mới đề nghị thành công !");
        }
        await this.HienThiDSDK();
        await this.FocusGridViewRow(ketQua.DENGHI_ID)
      }catch(e){        
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
  
    KiemTra_DuLieu(){
      var mess_err = ""
      if(this.params.cboLoaiPhieu == null || this.params.cboLoaiPhieu == "0"){
        mess_err += "Chưa chọn loại phiếu \n"
      }
      if(this.nhomphieu_id != 4){
        if(this.DSVatTu_DaDK.length == 0){
          mess_err += "Chưa chọn vật tư đề nghị\n"
        }
      }else{
        if(this.DSChungTu.length == 0){
          mess_err += "Chưa chọn chứng từ tổng hợp\n"
        }
      }
      if(this.params.tuTinh == null || this.params.denTinh == null){
        mess_err += "Bạn phải chọn tỉnh giao, tỉnh nhận"
      }
      if(mess_err != ""){
        this.$toast.error(mess_err)
        return false
      }else{
        return true
      }
    },
  
    btnDelete_VTDK_Click(){
      if(this.DSVatTu_DaDK_Checked.length == 0){
        this.$toast.error("Vui lòng chọn các vật tư cần xóa!")
        return
      }
      var NewDSVatTu_DaDK = []
      this.DSVatTu_DaDK.forEach(e=>{
        if(!this.DSVatTu_DaDK_Checked.includes(e.VATTU_ID)){
          NewDSVatTu_DaDK.push(e)
        }
      })
      this.DSVatTu_DaDK = NewDSVatTu_DaDK
    },
    grvDSVatTu_DaDK_Checked(item){
      this.DSVatTu_DaDK_Checked = item
    },
    editGridDSVatTu_DaDK(arg){
      let gridDSVatTu_DaDK = this.$refs.grvDSVatTu_DaDK;
      let changes = gridDSVatTu_DaDK.getBatchChanges();
      if (changes.changedRecords.length > 0) {
        if (arg.columnName == "SOLUONG") {
          if (changes.changedRecords[0].SOLUONG < 0) {
            this.$toast.error("Số lượng không được âm !");
            changes.changedRecords[0].SOLUONG = 0;
          } 
        }
        gridDSVatTu_DaDK.batchUpdate(changes);
        this.$refs.grvDSVatTu_DaDK.grid.refreshColumns();
      }
    },
    onDeleteRowHandler(item){ 
      var NewDSVatTu_DaDK = []
      this.DSVatTu_DaDK.forEach(e=>{
        if(item.VATTU_ID != e.VATTU_ID){
          NewDSVatTu_DaDK.push(e)
        }
      })
      this.DSVatTu_DaDK = NewDSVatTu_DaDK
    },
    createTree(list) {
      var list_origin = [];
      for (const i in list) {
        var VATTU_CHA_ID = list[i].VATTU_CHA_ID;
        var listNodeParent = list.filter(function (el) {
          return el.VATTU_ID == VATTU_CHA_ID;
        });
        if (listNodeParent.length == 0) {
          list_origin.push(list[i].VATTU_CHA_ID);
        }
      }
      var map = {},
        node,
        roots = [],
        i;
      for (i = 0; i < list.length; i += 1) {
        map[list[i].VATTU_ID] = i; // initialize the map
        list[i].children = []; // initialize the children
      }
      for (i = 0; i < list.length; i += 1) {
        node = list[i];
        if (!list_origin.includes(node.VATTU_CHA_ID)) {
          list[map[node.VATTU_CHA_ID]].children.push(node);
        } else {
          roots.push(node);
        }
      }
      return roots;
    },
    async findTreeVatTu(){
      console.log(this.params.cboTimKiem , " = cboTimKiem")
      var VATTU_ID = this.params.cboTimKiem;
      var nodeFocus = this.DSVatTuOrigin.filter(function(e){
        return e.VATTU_ID == VATTU_ID
      })
      var VATTU_CHA_ID = ""
      if(nodeFocus.length > 0){
        VATTU_CHA_ID = nodeFocus[0].VATTU_CHA_ID;
      }
     
      var dataFilter = [];

      // nếu focus vào gốc
      if (VATTU_CHA_ID == "") {
        dataFilter = this.DSVatTuOrigin.slice(0,100); // ĐỨc hardcode giảm thời gian loading grid
      } else {
        // Lấy node focus
        var nodeFocus = this.DSVatTuOrigin.filter(function (el) {
          return el.VATTU_ID == VATTU_ID;
        });
        if (nodeFocus.length > 0) {
          dataFilter.push(nodeFocus[0]);
        }

        // tìm node cha của node focus
        var isNodeOrigin = false;
        var VATTU_CHA_ID_Filter = VATTU_CHA_ID;
        while (!isNodeOrigin) {
          var listNodeParent = this.DSVatTuOrigin.filter(function (el) {
            return el.VATTU_ID == VATTU_CHA_ID_Filter;
          });
          if (listNodeParent.length > 0) {
            dataFilter.push(listNodeParent[0]);
            VATTU_CHA_ID_Filter = listNodeParent[0].VATTU_CHA_ID;
          } else {
            isNodeOrigin = true;
          }
          // đã tìm thấy node gốc
          // if (VATTU_CHA_ID_Filter == "") {
          //   isNodeOrigin = true;
          // }
        }
        // tìm node con của node focus
        var listNodeChildID = [VATTU_ID];
        var isNodeChildExist = true;
        while (isNodeChildExist) {
          var listNodeChildId = [];
          for (const i in listNodeChildID) {
            //Lấy data node con
            var listNodeChild = this.DSVatTuOrigin.filter(function (el) {
              return el.VATTU_CHA_ID == listNodeChildID[i];
            });
            // thêm vào node con vào true và lấy ID của node con

            for (const i in listNodeChild) {
              dataFilter.push(listNodeChild[i]);
              listNodeChildId.push(listNodeChild[i].VATTU_ID);
            }
          }
          //gán lại node con để tìm tiếp
          listNodeChildID = listNodeChildId;
          //hết node con
          if (listNodeChildId.length == 0) {
            isNodeChildExist = false;
          }
        }
      }
      if(dataFilter.length > 0){
        this.DSVatTu = this.createTree(dataFilter)
      }else(
        this.DSVatTu = []
      )
    },
    editGridVatTu(arg){
      console.log(arg , " = arg")
      var gridDSVatTu = this.$refs.gridDSVatTu
      var changes = gridDSVatTu.getBatchChanges()
      let VATTU_ID = arg.rowData.VATTU_ID;

      if(changes.changedRecords.length == 0){
        return
      }
  
      if (arg.columnName == "SOLUONG") {
        var DSRowChanges = changes.changedRecords.filter(function (el) {
          return el.VATTU_ID ==  VATTU_ID;
        });
        var rowChanges = DSRowChanges[0]
      
        if (rowChanges.SOLUONG <= 0) {  
          this.$root.toastError("Số lượng đăng ký phải lớn hơn 0");
        } else if (rowChanges.DVITINH_ID == 15 || rowChanges.DVITINH_ID == null) {
          this.$root.toastError('Bạn không thể đăng ký vật tư này do đơn vị tính là "Không xác định"');
        }else{
          var NewDSVatTu_DaDK = []
          var isVatTuExist = false
          
          this.DSVatTu_DaDK.forEach(e=>{ // loại bỏ vật tư đã tồn tại nếu có
            if(VATTU_ID != e.VATTU_ID){
              NewDSVatTu_DaDK.push(e)
            }else{
              isVatTuExist = true
            }
          })

          NewDSVatTu_DaDK.push({
            DENGHI_ID : this.denghi_id,
            VATTU_ID: rowChanges.VATTU_ID,
            MA_VT: rowChanges.MA_VT,
            TEN_VT: rowChanges.TEN_VT,
            SOLUONG: rowChanges.SOLUONG,
            DONGIA: isVatTuExist ? 0 : rowChanges.DONGIA,
            TIEN: isVatTuExist ? 0 : rowChanges.TIEN,
            VAT: isVatTuExist ? 0 : rowChanges.VAT,
            DVI_TINH: rowChanges.DVI_TINH,
            GHICHU: rowChanges.GHICHU,
          })
          // this.dsVATTUDK = NewDSVatTu_DaDK
          this.DSVatTu_DaDK = NewDSVatTu_DaDK
        }
        // this.$refs.gridDSVatTu.refreshColumns();
      }
    
    },
    async tsbtnChonChungTu_Click(){
      var f = this.$refs.frmChonChungTuQT
      f.nhapmoi = !this.Enabled.tsbtnNhapMoi
      f.vdenghi_id = this.denghi_id
      var json_chungtu_denghi = []
      for(const e of this.DSChungTu){
        json_chungtu_denghi.push({CHUNGTU_ID : e.CHUNGTU_ID})
      }
      f.json_chungtu_denghi = json_chungtu_denghi
      f.Open()
    },
    async XacNhan_frmChonChungTuQT(){
      var f = this.$refs.frmChonChungTuQT
      let chungTuCheck = []
      this.DSChungTu.forEach(e=> chungTuCheck.push(e.CHUNGTU_ID))
      if(f.xacnhan){
        if(!this.Enabled.tsbtnNhapMoi){
          if(f.dt_chungtu_.length == 0){
            this.$toast.error("Không nhận được thông tin")
            return
          }else if(this.DSChungTu.length == 0){
            this.DSChungTu = f.dt_chungtu_
          }else{
            for(var e of f.dt_chungtu_){
              if(!chungTuCheck.includes(e.CHUNGTU_ID)){
                this.DSChungTu.push(e)
              }              
            }
          }
          this.$refs.grvDSChungTu.grid.refreshColumns()   
        }else{
          this.NAP_DS_DENGHI_CHUNGTU()
        }
      }
    },
    async tsbtnXoaChungTu_Click(){
      try{
        
        if(this.denghi_id == 0){
          this.$toast.error("Chưa chọn đề nghị!")
          return
        }

        if(this.DSChungTu.length == 0){
          this.$toast.error("Không có thông tin để xoá")
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có muốn gỡ chứng từ khỏi đề nghị không? ", {
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
        if(this.listChungTuChecked.length == 0){
          this.$toast.error("Chưa chọn chứng từ để xóa!")
          return
        }
        var ds = []
        this.listChungTuChecked.forEach(e=>{
          ds.push({CHUNGTU_ID:e})
        })
        var kt_thucthi = await this.xoaDeNghiChungTu({
          deNghiId: this.denghi_id,
          ds: ds
        })
        if(kt_thucthi == "OK"){
          this.$toast.success("Đã gỡ chứng từ thành công")
          this.NAP_DS_DENGHI_CHUNGTU()
        }else{
          this.$toast.error("Có lỗi khi gỡ " + kt_thucthi)
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    gridDSChungTuCheckBox: function (dataItem) {    
      this.listChungTuChecked = dataItem   
    },
    async tsbtnDongBoFMIS_Click(){
      try{
        if(this.denghi_id == 0){
          this.$toast.error("Chưa có phiếu đề nghị nào được chọn. Vui lòng chọn 1 phiếu đề nghị để thực hiện!")
          return
        }
        if(this.ttdb_id == 1 ){
          this.$toast.error("Dữ liệu đã được đồng bộ lên FMIS trước đó. Vui lòng kiểm tra lại hoặc chọn phiếu chưa đồng bộ để thực hiện!")
          return
        }
        if(this.vloaiphieu_id != 39){
          this.$toast.error("Không thực hiện được chức năng đồng bộ FMIS với loại phiếu này! Chỉ đồng bộ với loại phiếu tổng hợp")
          return
        }
        if(this.DSChungTu.length == 0){
          this.$toast.error("Không có chứng từ để đồng bộ!")
          return
        }
        var dtChungTu = []
        this.DSChungTu.forEach(e=>{
          dtChungTu.push({CHUNGTU_ID : e.CHUNGTU_ID})
        })
       
        var s = await this.kiemTraHanChungTuV3({
          ds: dtChungTu,
          khoGiaoId: 0,
          khoNhanId: 0,
          ngayCt: this.f_DateToString(new Date())
        })
        if(s != "1"){
          this.$toast.error("Danh sách có tồn tại ít nhất một chứng từ đã quá hạn. Không thể thực hiện đồng bộ FMIS. Vui lòng kiểm tra lại!")
          return
        }
        if(this.chondvi_fmis && this.params.p_DonViFMIS == 0){
          this.$toast.error("Bạn hãy chọn đơn vị FMIS cần đồng bộ")
          return
        }
     
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có chắc chắn muốn thực hiện đồng bộ FMIS không?", {
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
        var DV_Chon = this.cboDonvi_FMIS.filter(a=> a.MA_DV == this.params.p_DonViFMIS)
        var ma_dv = DV_Chon.length > 0 ? DV_Chon[0].TEN_DV : ""
        var kq_db = await this.dongBoDeNghi({
          deNghiId: this.denghi_id,
          tenDv: ma_dv
        })
        if(kq_db == "ok"){
          this.HienThiDSDK();
          this.$toast.success("Cập nhật chứng từ và đồng bộ kế toán tập đoàn thành công!")
        }else{
          if(kq_db.startsWith('ERROR_UPDATE_STATUS')){
            var chungtu_td_id = kq_db.split("|")[1]
            var kt_thucthi = await this.capNhatDongBoChungTu({
              kieu: 1,
              chungTuTdId: chungtu_td_id,
              deNghiId: this.denghi_id
            })
            if(kt_thucthi == "OK"){
              this.HienThiDSDK();
              this.$toast.success("Cập nhật chứng từ và đồng bộ kế toán tập đoàn thành công!")
            }else{
              this.$toast.error(kt_thucthi)
            }
          }else{
            this.$toast.error("Đồng bộ FMIS lỗi: " + kq_db)
          }
        }

      }catch(e){        
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbtnXoaDongBoFMIS_Click(){
      try{
        if(this.denghi_id == 0){
          this.$toast.error("Chưa có phiếu đề nghị nào được chọn. Vui lòng chọn 1 phiếu đề nghị để thực hiện!")
          return
        }
        if(this.ttdb_id == 0 || this.vchungtu_td_id == 0){
          this.$toast.error("Phiếu chưa đồng bộ FMIS. Bạn không thể xóa đồng bộ")
          return
        }
        if(this.vloaiphieu_id != 39){
          this.$toast.error("Không thực hiện được chức năng xóa đồng bộ FMIS với loại phiếu này! Chỉ đồng bộ với loại phiếu tổng hợp")
          return
        }

        if(this.DSChungTu.length == 0){
          this.$toast.error("Không có chứng từ để đồng bộ!")
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn có chắc chắn muốn xóa đồng bộ FMIS với phiếu đề nghị này không?", {
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
        var DV_Chon = this.cboDonvi_FMIS.filter(a=> a.MA_DV == this.params.p_DonViFMIS)
        var ma_dv = DV_Chon.length > 0 ? DV_Chon[0].TEN_DV : ""
        var kq_db = await this.xoaChungTuDongBo({
          deNghiId: this.denghi_id,
          tenDv: ma_dv
        })
        if(kq_db == "ok"){
          this.HienThiDSDK();
          this.$toast.success("Cập nhật chứng từ và xóa đồng bộ thành công!")
        }else{
          if(kq_db.startsWith('ERROR_UPDATE_STATUS')){
            var chungtu_td_id = kq_db.split("|")[1]
            var kt_thucthi = await this.capNhatDongBoChungTu({
              kieu: 1,
              chungTuTdId: chungtu_td_id,
              deNghiId: this.denghi_id
            })
            if(kt_thucthi == "OK"){
              this.HienThiDSDK();
              this.$toast.success("Cập nhật chứng từ và xóa đồng bộ thành công!")
            }else{
              this.$toast.error("Xóa đồng bộ FMIS lỗi: " + kt_thucthi)
            }
          }else{
            this.$toast.error("Xóa đồng bộ FMIS lỗi:  " + kq_db)
          }
        }

      }catch(e){        
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async onClickDownFile(value) {
      if(value) {           
        var checkFile = await this.checkFileExist({fileSource : value.urlFile})        
        var linkFile = ""
        if(checkFile.message == "Success" && checkFile.data.data){
          let getLink = await this.getLinkFile({fileSource : value.urlFile}) 
          linkFile = getLink.data.name                 
        }else{  
          linkFile  = this.URL + value.urlFile     
        }
        const downloadLink = document.createElement("a");      
        downloadLink.href = linkFile;        
        downloadLink.download = value.fileName;    
        downloadLink.target = "_blank";
        downloadLink.click();   
        
      }
    },
  },
  
  created: async function () {
    try{
      this.loading(true)
      EventBus.$on('onDeleteRow', this.onDeleteRowHandler);
      await this.frmNhapDeNghiVT_Load()
    }catch(e){        
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false)
    }
  },
  watch: {},
  destroyed() {
    EventBus.$off('onDeleteRow', this.onDeleteRowHandler); 
  },
 
};
</script>