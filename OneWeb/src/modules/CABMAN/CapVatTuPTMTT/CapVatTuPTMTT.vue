<template src='./CapVatTuPTMTT.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import CapVatTuPTMTTAPI from  '../api/CapVatTuPTMTTAPI'
import { actionName,statePropertyName } from '../store/CapVatTuPTMTT'
import XLSX from 'xlsx'
import frmNhapText from './frmNhapText'
import ComboboxGrid  from "../../../components/Controls/ComboboxGrid"
import FormAnhChup from "./AnhChup";
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
Vue.use(DialogPlugin)

var DialogResultObj = {
  None: 0,
  OK: 1,
  Cancel: 2,
  Abort: 3,
  Retry: 4,
  Ignore: 5,
  Yes: 6,
  No: 7,
};


export default {

  components: { XLSX,frmNhapText, ComboboxGrid, FormAnhChup },
  name: "CapVatTuPTMTT",
  mounted() {
  },
  provide: {
  },
  computed: {
    ...mapState("capvattuptmtt", statePropertyName),
    vmatinh: {
      get (){
        return this.$auth.getMaTinh()
      }
    },
    vphieu_cha_id: {
      get (){
        return parseInt(this.gridviewDanhSach_FocusedRow["PHIEU_CHA_ID"] || 0)
      }
    },
    vphieu_id: {
      get (){
        return parseInt(this.gridviewDanhSach_FocusedRow["PHIEUCC_ID"] || 0)
      }
    },
    vtrangbi: {
      get (){
        return parseInt(this.params.p_KieuTrangBi)
      }
    },
    vkieutbi: {
      get (){
        return parseInt(this.params.p_KieuTb)
      }
    },
    ld_to: {
      get (){
        return parseInt(this.params.p_Droplydocap)
      }
    },


  },
  data() {
    return {
        params: {
          p_ckbDaCap: false,
          p_cbxTuNgay: false,
          p_dtpTuNgay: new Date(),
          p_dtpDenNgay: new Date(),
          p_NhanVien: "",
          p_Kho: "",

          p_KieuTb: "",
          p_NgayCap: new Date(),
          p_KetQuaXL: "",
          p_LyDoTon: "",
          p_Droplydocap: "",
          p_TinhTrangCap: "",
          p_LyDoCap: "",
          p_cboKieuTrangBi: "",

          p_VatTu: "",
          p_soluong_cu: 0,
          p_txtSerialCu: "",
          p_TinhTrangTbi: "",
          p_txtGhiChu: "",

          p_rdioCKb: 1,
          p_ckbLocVT: true,
          p_txtSerial: "",
          

        },

        cboNhanVien: [],
        cboKho: [],

        cboKieuTb: [],
        cboKetQuaXL: [],
        cboLyDoTon: [],
        Droplydocap: [],
        cboTinhTrangCap: [],
        CboLyDoCap: [],
        cboKieuTrangBi: [],
        
        cboVatTu: [],
        cboTinhTrangTbi: [],
        

        gridGiaoviec: [],
        gridDanhSach: [],
        gridLichSuVatTu: [],
        grcDS_VT_Giao: [],
        gridVatTuSD: [],


        Visible: {
          NN_HONG: false,
          GhiChuXL: false,
          ckbDaCap: true,
          grcHuy: true,
          XOA: true,
          tsbtnHuyBo: true,
          btnNhapMoi: true,
          btnCapNhat: true,
          btnHoanCong: true,
          ckbLocVT: true,
          xtraTabPage1: true,
          xtraTabPage2: true,
        },

        ReadOnly: {
          cboVatTu: false,
          cboNhanVien: false,
          txtSerialCu: false,
          txtSerial: false,
          soluong_cu: false,
        },

        Enabled:{
          btnNhapMoi: true,
          btnCapNhat: true,
          pnlThuHoi: false,

          cboTinhTrangTbi: true,
          cboVatTu: true,
          cboLyDoTon: true,

          txtSerialCu: true,
          soluong_cu: true,
          txtGhiChu: true,
          
          dtpTuNgay: false,
          dtpDenNgay: false,
        },

        Text: {
          rdioCKb_0: "Chờ duyệt",
          rdioCKb_1: "Trả",
          rdioCKb_2: "Cấp bổ sung",
          rdioCKb_3: "Đã giao",
        },

        dtVatTu: [], 
        dtVatTuCap: [], 
        dtthuebao: [],
        dtXml1: [], 
        dtXml2: [],
        ngay_nt: new Date(),
        UPDATE_CTS: false,
        dston: [],

        ttdv_id: 34, // code C# 
        dslh_md: [],
        ds_lydoTC: [],
        vt_dccap: [],
        lydo_temp: [],
        ds_error: [],

        ds_lydo: [],
        phieucc_id: "",
        phieucc_con_id: "",
        chitietcc_id: "",
        hscc_id: "",
        loaitbi_ecms_id: 0,
        ptm_id: 0,

        imageDir: "",
        vsdvt_id: 0,
        kt_load: false,
        
        dsQuyen: [],

        ben_ngoai: 0,
        tb_benngoai: "",

        TRANGBI: {
          VIENTHONG_TRANGBI: 4
        },

        gridViewLichSuVatTu_FocusedRow: {},
        ngay_ht: new Date(),
        loaitbi_id: 0,
        check_serial: "^[a-zA-Z0-9]*$",
        kt_dinhmuc:false,
        vphieuvt_id: 0,
        sl_goc: 0,
        tuday_id: 0,

        vt_id: 0,
        tudaycu_id: 0,
        phvt_id: 0,
        soluong: 0,
        max: 0,
        sDuongDanDinhKemFTP: "",
        sum_day: 0,
        sum_thu: 0,
        dsLoaiHD: [],
        dsLoaiTB: [],
        ngay_hc: new Date(),
        i: 0,
        j: 0,

        // css
        row1: true,
        row2: true,

        showVatTu: false,
        selectedVatTu: "",

        btnHuyGiao: function () {
          return {
            template: Vue.component("btnHuyGiao", {
              template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnHuyGiao_ButtonClick">
                                    <span class="-ap icon-close"></span>
                                </button>`,
              data: function () {
                return { data: {} };
              },
              methods: {
                async btnHuyGiao_ButtonClick() {
                  await this.$parent.$parent.$parent.btnHuyGiao_ButtonClick(this.data);
                },
              },
            }),
          };
        },
        btnXoaLSVT: function () {
          return {
            template: Vue.component("btnXoaLSVT", {
              template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" v-if="XoaLSVT" @click="btnXoaLSVT_ButtonClick">
                                    <span class="-ap icon-close"></span>
                                </button>`,
              data: function () {
                return { data: {} };
              },
              computed: {
                XoaLSVT: {
                  get(){
                    if (this.data["STATUS"].toString() == "1"){
                      return true
                    }
                    return false // true =>test
                  }
                }
              },
              methods: {
                async btnXoaLSVT_ButtonClick() {
                  await this.$parent.$parent.$parent.btnXoaLSVT_ButtonClick(this.data);
                },
              },
            }),
          };
        },
        
        btnThuHoi: function () {
          return {
            template: Vue.component("btnThuHoi", {
              template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnThuHoi_ButtonClick">
                            <span class="-ap icon-plus"></span>
                          </button>`,
              data: function () {
                return { data: {} };
              },
              methods: {
                async btnThuHoi_ButtonClick() {
                  await this.$parent.$parent.$parent.btnThuHoi_ButtonClick(this.data);
                },
              },
            }),
          };
        },
        btnEdit: function () {
          return {
            template: Vue.component("btnEdit", {
              template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnEdit_ButtonClick">
                            <span class="-ap icon-edit"></span>
                          </button>`,
              data: function () {
                return { data: {} };
              },
              methods: {
                async btnEdit_ButtonClick() {
                  await this.$parent.$parent.$parent.btnEdit_ButtonClick(this.data);
                },
              },
            }),
          };
        },
        SERVER_IMAGES:{
          IP: "10.70.52.164:8081",
          BASE_URL: "http://10.70.52.164:8081/FILE/",
          NO_IMAGE_URL: "http://10.70.52.164:8081/FILE/no-image-available.jpg",
          URL: ""
        },
        arrayImportFile: {
          filexcel:null,
          ok: false,
          dsImportExcel: [],
          dataExcel: [],
          ds_file: []
        },
        images: [],

        
    };
  },
  methods: {
    ...mapActions("cabman/capvattuptmtt", actionName),
    
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    IsValidFileName(fname){
      return false // fname.ToCharArray().Any(x => Path.GetInvalidFileNameChars().Contains(x));
    },
    async cboVatTu_selected(data){
      if (data != "" && data != null && Object.keys(data).length){
        this.selectedVatTu = data.TEN_VT
        this.params.p_VatTu = data.VATTU_ID;
      }
    },

    async Load_quyen(){
      this.dsQuyen = await this.getDsQuyenND({nguoiDungId : this.$auth.getNguoiDungID()})
    },
    async Load_Cbo(){
      // try {
        this.Visible.NN_HONG = false
        this.Visible.GhiChuXL = false

        this.params.p_dtpTuNgay = new Date()
        this.params.p_dtpDenNgay = new Date()
        this.params.p_dtpNgayCap = new Date()
        // popupContainerControl1.Width = 300;
        // popupContainerControl1.Height = 400;
        let res = await this.getDanhMucCapVatTuPTMTT()

        this.dslh_md = res.loaiHD || []
        // TRANG THAI FILTER
        this.cboTrangThaiFilter = res.trangThai || []
        // Kiểu Tbi
        this.cboKieuTb = res.kieuTbi || []
        this.cboKieuTb.length ? this.params.p_KieuTb = this.cboKieuTb[0].KIEUTBI_ID : ''

        // Combo nhân viên theo đơn vị
        this.ds_nv = await this.getDSNhanVienGiaoNhan({
          donViId: this.ttdv_id,
          nhanVienId: parseInt(this.$auth.getNhanVienID())
        })

        if (this.ds_nv.length){
          this.cboNhanVien = this.ds_nv
          var q = this.ds_nv.filter(l=> l["NHANVIEN_ID"].toString() == this.$auth.getNhanVienID().toString())
          if(q.length){
            this.params.p_NhanVien = this.$auth.getNhanVienID()
            this.ReadOnly.cboNhanVien = true
          }
          else{
            this.params.p_NhanVien = this.ds_nv[0].NHANVIEN_ID
            this.ReadOnly.cboNhanVien = false
          }
        }
        // Table mục đích, lý do
        this.ds_lydoTC = await this.getDSMucDichLyDo({nhomMdId: 22})
        this.Droplydocap = this.ds_lydoTC

        this.ds_lydo = res.mucDichLyDo || []
        this.CboLyDoCap = this.ds_lydo
        this.ds_lydo.length ? this.params.p_LyDoCap = this.ds_lydo[0].MUCDICH_ID : ''

        this.ds_ktbi = res.kieuTrangBi || []
        this.cboKieuTrangBi = this.ds_ktbi
        this.params.p_KieuTrangBi = this.TRANGBI.VIENTHONG_TRANGBI

        // Nạp combobox vật tư
        this.dtVatTu = res.vtDVT || []
        this.cboVatTu = this.dtVatTu.filter(a=>a.HIENTHI.toString() == "1")

        // Nạp combobox Kết quả thu hồi
        this.dslh_md = res.kqThuHoi || []
        this.cboKetQuaXL = this.dslh_md
        this.params.p_KetQuaXL = 1
      // } catch (error) {
      //   this.$toast.error("Lỗi lấy danh sách combo: ", error)
      // }
    },
    async LayTT_PVT(row){
      if (this.params.p_KieuTb.toString() == "3" && row["KIEUTBI_ID"].toString() == "3"){
          this.Enabled.btnNhapMoi = true;
          this.Enabled.pnlThuHoi = true;

          this.phvt_id = parseInt(row["PHIEUVT_ID"].toString());
          if ((row["SERIAL"] == null || row["SERIAL"] == '') &&
              (row["MA_TBI"] == null || row["MA_TBI"] == '')){
              this.params.p_txtSerialCu = "";
              this.ReadOnly.soluong_cu = false;
              this.ReadOnly.cboVatTu = true;
              this.ReadOnly.txtSerialCu = true;
          }
          else if ((row["SERIAL"] != null && row["SERIAL"] != '') &&
                    (row["MA_TBI"] == null || row["MA_TBI"] == '')){
              this.params.p_txtSerialCu = row["SERIAL"].toString();
              this.ReadOnly.soluong_cu = true;
              this.ReadOnly.cboVatTu = true;
              this.ReadOnly.txtSerialCu = true;
          }
          else if ((row["SERIAL"] == null || row["SERIAL"] == '') &&
                    (row["MA_TBI"] != null && row["MA_TBI"] != '')){
              this.params.p_txtSerialCu = row["MA_TBI"].toString();
              this.ReadOnly.soluong_cu = false;
              this.ReadOnly.cboVatTu = true;
              this.ReadOnly.txtSerialCu = false;
          }

          this.params.p_VatTu = row["VATTU_ID"].toString()
          this.params.p_soluong_cu = row["SOLUONG"].toString()
          this.params.p_TinhTrangTbi = row["TINHTRANGTBI_ID"].toString()
          this.params.p_KieuTrangBi = row["TRANGBI_ID"].toString()
          this.params.p_LyDoCap = row["MUCDICH_ID"].toString()
          this.params.p_txtGhiChu = row["GHICHU"].toString();
      }
      else{
        await this.Clear();
      }
    },
    async LoadThongTinThueBao (ten){
      // try {
        if (this.params.p_rdioCKb == null || this.params.p_rdioCKb == ''){ return; }

        let sDATE = "0";
        let eDATE = "0";
        if (this.params.p_bxTuNgay == true){
            sDATE = this.f_DateToString(this.params.p_dtpTuNgay,'dd/MM/yyyy')
            eDATE = this.f_DateToString(this.params.p_dtpDenNgay,'dd/MM/yyyy')
        }
        this.Visible.ckbDaCap = true;
        this.Visible.grcHuy = false;
        this.Visible.XOA = true;
        this.Visible.tsbtnHuyBo = this.params.p_KieuTb.toString() == "3";
        this.Visible.btnNhapMoi = this.params.p_KieuTb.toString() == "3";
        this.Visible.btnCapNhat = true;
        this.Visible.btnHoanCong = true;
        if (this.params.p_rdioCKb.toString() == "6"){
            this.Visible.ckbDaCap = false;
            this.Visible.XOA = false;
            this.Visible.grcHuy = true;
            
            this.Visible.tsbtnHuyBo = false;
            this.Visible.btnNhapMoi = false;
            this.Visible.btnCapNhat = false;
            this.Visible.btnHoanCong = false;
        }
        // bangts.showLoading(this, 0);

        this.dtthuebao = await this.getDSHsccPTM({
          ma_hscc: "0",
          tungay: sDATE,
          denngay: eDATE,
          nhanvien_id: parseInt(this.$auth.getNhanVienID()),
          kieu: parseInt(this.params.p_rdioCKb),
          dacap: this.params.p_ckbDaCap ? 1 : 0,
        })

        this.gridVatTuSD = [];
        this.gridLichSuVatTu = [];

        this.gridDanhSach = [];
        this.gridDanhSach = this.dtthuebao;

        if (this.gridDanhSach.length){
          let self = this
          setTimeout(() => {
            self.$refs.gridviewDanhSach.grid.groupCollapseAll()
          }, 1000);
        }
      // } catch (error) {
      //   this.gridDanhSach = []
      //   this.$toast.error(error)
      // }
    },
    async gridviewDanhSach_FocusedRowChanged(row){
      if (row["NGAY_HT"] != null && row["NGAY_HT"] != ''){
        this.ngay_ht = row["NGAY_HT"] != '' ? this.f_StringToDate(row["NGAY_HT"].toString(), 'YYYY-MM-DD hh:mm:ss') : this.f_StringToDate(row["NGAY_YC"].toString(), 'YYYY-MM-DD hh:mm:ss');
      }
      else if (row["NGAY_YC"] != null && row["NGAY_YC"] != ''){
        this.ngay_ht = this.f_StringToDate(row["NGAY_YC"].toString(), 'YYYY-MM-DD hh:mm:ss')
      }
      else {
        this.ngay_ht = new Date()
      }

      this.hscc_id = parseInt(row["HSCC_ID"].toString());
      this.chitietcc_id = parseInt(row["CHITIETCC_ID"].toString());
      this.phieucc_con_id = parseInt(row["PHIEUCC_ID"].toString());
      this.loaitbi_ecms_id = parseInt(row["LOAITBI_ID"].toString());
      this.ptm_id = parseInt(row["PTM_ID"].toString());

      if (this.params.p_rdioCKb.toString() == "4"){
          this.phieucc_id = parseInt(row["PHIEUCC_ID"].toString());
      }
      else if (this.params.p_rdioCKb.toString() == "2"){
          this.phieucc_id = parseInt(row["PHIEU_CHA_ID"].toString());
      }
      else if (this.params.p_rdioCKb.toString() != "6"){
          if (parseInt(row["HUONGGIAO_ID"].toString()) == 3004 ||
              parseInt(row["HUONGGIAO_ID"].toString()) == 3007 ||
              parseInt(row["HUONGGIAO_ID"].toString()) == 20008 ||
              parseInt(row["HUONGGIAO_ID"].toString()) == 20010){
              this.phieucc_id = parseInt(row["PHIEU_CHA_ID"].toString());
          }
          else{
            this.phieucc_id = parseInt(row["PHIEUCC_ID"].toString());
          }              
      }
      else if (this.params.p_rdioCKb.toString() == "6"){
          phieucc_id = parseInt(row["PHIEUCC_ID"].toString());
      }

      this.gridGiaoviec = await this.getDSGVCaiTaoBaoDuong({
        chitietcc_id: this.chitietcc_id,
        phieucc_id: this.phieucc_id
      })

      await this.Load_DS_PhieuVT(this.phieucc_id);
      if (this.params.p_KieuTb.toString() == "1"){
        await this.Load_DS_VTTU();
      }
      else{
        await this.LoadGrid_VatTuSD();
      }
    },
    async Load_DS_VTTU(){
      this.grcDS_VT_Giao = []
      let dt_vt = []
      dt_vt = await this.getDSTamUngVT({
        kho_id: parseInt(this.params.p_Kho),
        loaitbi_ecms_id: this.loaitbi_ecms_id
      })
      if (dt_vt.length && !Object.keys(dt_vt[0]).includes("SL_KD")){
        colSLKD.Visible = false;
      }
      if (dt_vt.length && !Object.keys(dt_vt[0]).includes("CHATLUONG")){
        colChatluong.Visible = false;
      }   

      this.grcDS_VT_Giao = dt_vt;
    },
    SortGridView(){

    },
    async LoadGrid_VatTuSD(){
      this.gridVatTuSD = []
      this.gridVatTuSD = await this.getDSVatTuSDPTM({
        ptm_id: this.ptm_id,
        loaitbi_ecms_id: this.loaitbi_ecms_id
      })
    },
    async KiemTraDuLieu(){
      let ok = true;
      if (this.params.p_TinhTrangCap == null || this.params.p_TinhTrangCap == ''){
          this.$toast.error("Bạn chưa chọn tình trạng thiết bị");
          return false;
      }
      if (this.ptm_id == -1 || this.ptm_id == 0){
          this.$toast.error("Bạn chưa chọn Phần tử mạng !");
          return false;
      }

      let dskt = this.CboLyDoCap
      var r = dskt.filter(l => l["MUCDICH_ID"].toString() == this.params.p_LyDoCap.toString())
      if (r.length){
          if (this.params.p_dtpNgayCap < this.ngay_ht){
              this.$toast.error("Ngày cấp phải lớn hơn hoặc bằng ngày hoàn thành");
              return false;
          }

          if (this.grcDS_VT_Giao.length == 0 ){
              this.$toast.error("Không có vật tư để cấp");
              return false;
          }

          let vt = this.grcDS_VT_Giao;
          if (vt.length){
              var q = this.$refs.grvDS_VT_Giao.getSelectedRecords();
              if (q.length == 0){
                  this.$toast.error("Bạn chưa chọn vật tư để cấp");
                  this.vt_dccap = [];
                  return false;
              }

              var q_1 = this.$refs.grvDS_VT_Giao.getSelectedRecords().filter(l => l["MA_VT"].toString() != '' && l["MA_VT"].toString().startsWith("19"));
              if (q_1.length != 0 && r[0]["MA_MD"].toString() == "LD.3.1"){
                  this.$toast.error("Lý do bạn chọn không được cấp thiết bị đầu cuối");
                  return false;
              }

              // let json = JSON.stringify(q)
              // quy move API to FE 
              // tinhtrangtbi_id = 5 return 0 else 1
              // var kq = tdan.GET_VALUE_FUNC("{?DB12}.chan_tinhtrang_vattu", "vds", json);
              // await this.KiemTraTinhTrangHongVT({listTinhTrang: json})
              if (q.fitler(item=> item.TINHTRANGTBI_ID == 5).length){
                this.$toast.error("Danh sách chọn có vật tư đang có tình trạng hỏng! Không thể cấp cho khách hàng!");
                  return false;
              }
              this.vt_dccap = []
              this.vt_dccap = q
          }
      }
      return true;
    },
    async KiemTraDuLieu_Th(){
      if (this.params.p_VatTu == null || this.params.p_VatTu == ''){
          this.$toast.error("Bạn phải chọn vật tư");
          return false;
      }
      if (this.params.p_soluong_cu == 0){
          this.$toast.error("Bạn phải nhập số lượng");
          return false;
      }
      if (this.params.p_TinhTrangTbi == null || this.params.p_TinhTrangTbi == ''){
          this.$toast.error("Bạn chưa chọn tình trạng thiết bị");
          return false;
      }
      
      if (this.ngay_ht > this.params.p_dtpNgayCap){
          this.$toast.error("Ngày thu hồi phải lớn hơn ngày hoàn thành");
          return false;
      }
      this.loaitbi_id = 0;
      let vt = this.dtVatTu.filter(l => l["VATTU_ID"] && l["VATTU_ID"].toString() == this.params.p_VatTu.toString())
      if (vt.length){
        let r = vt[0]
        this.loaitbi_id = parseInt(r["LOAITBI_ID"]);
        if (r["LOAITBI_ID"].toString().trim() == "2"){
          if (this.params.p_txtSerialCu.trim() == ""){
              this.$toast.error("Loại vật tư này thuộc loại phải có seri. Ban hãy nhập serial cho thiết bị");
              this.$refs.txtSerialCu.focus();
              return false;
          }
          if (this.params.p_txtSerialCu.trim() != ""){
              if (this.params.p_soluong_cu != 1){
                  this.$toast.error("Vật tư này có seri, số lượng phải nhập = 1");
                  this.$refs.soluong_cu.focus();
                  return false;
              }
              let serialPatterns = this.check_serial;
              //"^[a-zA-Z0-9]*$";
              if (this.check_serial != "0" && !this.params.p_txtSerialCu.match(serialPatterns)){
                  this.$toast.error("Serial có ký tự không hợp lệ. Các ký tự cho phép " + this.check_serial + "");
                  this.$refs.txtSerialCu.focus();
                  return false;
              }
          }
        }
        else if (r["LOAITBI_ID"].toString().Trim() == "3"){
            return true;
        }
        else{
          if (this.params.p_txtSerialCu.trim() != ""){
              this.$toast.error("Loại vật tư này thuộc loại phải không có seri. Ban hãy xóa dữ liệu serial");
              this.$refs.txtSerial.focus();
              // txtSerial.SelectAll();
              return false;
          }
        }
      }
      else{
          this.$toast.error("Không lấy được vật tư thu hồi ! (loaitbi_id:" + this.loaitbi_id + ")");
          return false;
      }
      return true;
    },
    async KiemTraSerial(serial, serial_cu){
      // Kiem tra serial thay đổi có trùng không
      if (serial.trim().toUpperCase() == serial_cu.trim().toUpperCase()){
          this.$toast.error("Serial thay đổi trùng với serial thu hồi ");
          return -1;
      }
      let dt_ckb = await this.getDSThietBiDaCap({serial: serial.trim().toUpperCase()}) // dư vsdvt_id: 0
      // HD.getThietBi_DaCap(serial.trim().toUpperCase(), 0);

      // Lấy danh sách thiết bị đã cấp theo serial mới 
      if (dt_ckb.length > 0){
          this.$toast.error(`Serial ${serial.trim().toUpperCase()} đã được thu hồi vào ngày ${dt_ckb[0]["NgayCap"]} bởi user ${dt_ckb[0]["NguoiCn"]}`);
          return -1;
      }
      let dt_ = await this.getDSThietBi({serial: serial.trim().toUpperCase()})
      // HD.getThietBi(serial.trim().toUpperCase()); //Lấy trong ds thiet bị
      if (dt_.length > 0 && dt_[0]["slTon"].toString() == "1" && dt_[0]["tonKho"].toString() == "1"){
          this.$toast.error("Serial này đang ở kho " + dt_[0]["t"] + "");
          return -1;
      }

      if (dt_.length > 0 && dt_[0]["slTon"].toString() == "0" && dt_[0]["tonKho"].toString() == "1"
          || (dt_.length > 0 && dt_[0]["tonKho"].toString() == "0")) //neuton tại trong thiet bi
      {
          //vkieu = 2;
          return parseInt(dt_[0]["thietBiId"].toString());
      }
      else if (dt_.length > 0 && dt_[0]["slTon"].toString() == "1" && dt_[0]["tonKho"].toString() == "1"){
          this.$toast.error("Serial này đang ở kho " + dt_[0]["t"] + "");
          return -1;
      }
      else{
        return 0;
      }
    },
    async TaoDL_Ton_Khong_ThuHoi_Duoc(themmoi){
      this.dston = []
      let row = {}
      row.PHIEU_ID = this.phieucc_id;
      row.LYDOTONTC_ID = parseInt(this.params.p_LyDoTon);
      row.NGAY_BT = this.f_DateToString(new Date(),'DD/MM/YYYY')
      row.NHANVIEN_BT_ID = this.$auth.getNhanVienID();
      row.DONVI_BT_ID = this.$auth.getDonViID()
      // row.NGUOI_CN = tt_nd.ma_nd; // BE get 
      // row.NGAY_CN = tt_nd.ngay_cn;
      // row.MAY_CN = tt_nd.may_cn;
      // row.IP_CN = tt_nd.ip;
      row.GHICHU = "";
      this.dston.push(row)
    },
    async XacNhan_frmNhapText() {
      var frm = this.$refs.frmNhapText;
      if (frm.functionName == "ThuHoiVatTu_V2") {
        // nếu thay đổi serial
        if (frm.DialogResult == DialogResultObj.OK){
          let vvattu_id = frm.CboOutPut;
          let vserial = frm.Output.trim().toUpperCase();
          this.vsdvt_id = 0;
          let kieu_pvt = 1; // Đổi serial -- 1 2 cua thue bao khac
          await this.KiemTra(vserial, frm.dt, vvattu_id, kieu_pvt);
        }
        //Nếu không đổi
        if (frm.DialogResult == DialogResultObj.Cancel){
          let vvattu_id = frm.CboOutPut;
          let vserial = this.params.p_txtSerialCu.toUpperCase();
          this.vsdvt_id = 0;
          let kieu_pvt = 2; //Không đổi
          await this.KiemTraVT(vserial, vvattu_id, kieu_pvt);
        }
      }

      if (frm.functionName == "btnEdit_ButtonClick"){
        if (frm.DialogResult == DialogResultObj.OK){
          let isAsync = true
          let vvattu_id_new = frm.CboOutPut
          let seri = frm.seri
          let seri_new = frm.Output.trim().toUpperCase()
          if (seri_new == seri){
            this.$toast.error("Seri cần thay đổi trùng với seri gốc")
            return
          }

          let dtsd = await this.KiemTraPTMSuDungVT({serial: seri_new})
          // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
          if (dtsd.length > 1){
            await this.$bvModal.msgBoxConfirm("Hiện đang có " + dt.length + " thuê bao đang sử dụng " + seri_new + ". Bạn có muốn cập nhật lại seri cho thuê bao này không?", {
              "okVariant": 'success',
              "okTitle": 'Đồng ý',
              "cancelTitle": 'Hủy',
              "centered": true,
              })
              .then(async(value) => {
                  if (value){
                    isAsync = true
                  }
                  else {
                    isAsync = false
                  }
              })
              .catch(err => {
                  // An error occurred
              })
          }
          // Nếu có 1 thuê bao thì kiểm tra tiếp
          if(dtsd.length == 1){
            //Nếu thuê bao tìm thấy khác với thuê bao đang chọn
            if(parseInt(dtsd[0]["PTM_ID"]) != parseInt(this.ptm_id) || parseInt(dtsd[0]["LOAITBI_ECMS_ID"]) != parseInt(this.loaitbi_ecms_id)){
              
              // Kiemes tra duoc phep sua khong
              if(parseInt(dtsd[0]["PHIEUVT_ID"]) != 0){
                this.$toast.error("Serial " + seri_new + " đã được sử dụng bởi thuê bao " + dtsd[0]["MA_TB"] + ". Bạn không có quyền được thu hồi")
                return
              }

              await this.$bvModal.msgBoxConfirm("Serial " + seri_new + " đã được sử dụng bởi thuê bao " + dtsd[0]["MA_TB"] + ". Bạn có chắc chắn muốn tiếp tục sửa không?", {
                "okVariant": 'success',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy',
                "centered": true,
                })
                .then(async(value) => {
                    if (value){
                      isAsync = true
                    }
                    else{
                      isAsync = false
                    }
                })
                .catch(err => {
                    // An error occurred
                })

            }
          }

          if (isAsync){
            let s = await this.CapNhatSDVT({
              kieu: 1,
              sdvt_id: this.vsdvt_id,
              vattu_id: vvattu_id_new,
              xoavt: 0,
              kieu_pvt: 0,
              lydocaptbi_id: 0,
              tinhtrangtbi_id: 0,
              ptm_id: this.ptm_id,
              loaitbi_ecms_id: this.loaitbi_ecms_id,
              hscc_id: this.hscc_id,
              chitietcc_id: this.chitietcc_id,
              phieucc_id: this.phieucc_id,
              donvi_id: this.$auth.getDonViID(),
              nhanvien_id: this.$auth.getNhanVienID(),
              soluong: 0,
              trangbi_id: 0,
              ngay_th: this.f_DateToString(this.params.p_dtpNgayCap),
              ghichu: this.params.p_txtGhiChu,
              serial: seri,
              serial_new: seri_new
            })
            if (s != "1"){
              this.$toast.error(s)
            }
            await this.LoadGrid_VatTuSD();
          }
        }
      }
    },
    async ThuHoiVatTu_V2(){
      //Chia 2 truong hop thu hoi moi va sua phieu_vt
      //Truong hop 1 thu hoi moi
      let vseria_goc = this.params.p_txtSerialCu;
      let vserial = "";
      let vvattu_id = 0;
      let kieu_pvt = 0;
      let phieu_vt_id = 0;
      phieu_vt_id = this.phvt_id;
      let xoavt = 0;
      let nhanvien_id_temp = this.$auth.getNhanVienID();
      if (parseInt(this.params.p_NhanVien.toString()) != this.$auth.getNhanVienID()){
          nhanvien_id_temp = parseInt(this.params.p_NhanVien);
      }
      // Thu hồi mới
      if (this.Enabled.btnNhapMoi == false){
        // Đoạn này kiểm tra các điều kiện cần thiết
        if (await this.KiemTraDuLieu_Th()){

          if(this.loaitbi_id == 2){
            // Thu hoi serial
            // Nếu là serial
            if (this.params.p_txtSerialCu.trim() != ""){
              var checklogic = await this.KiemTraThuHoi({ptm_id: this.ptm_id, serial: this.params.p_txtSerialCu.trim().toUpperCase()})
              if (checklogic.toString() != "1"){
                this.$toast.error(checklogic)
                return
              }
              var dt = await this.KiemTraPTMSuDungVT({serial: this.params.p_txtSerialCu.trim().toUpperCase()})
              let dt_tbidacap = await this.getDSThietBiDaCap({serial: this.params.p_txtSerialCu.trim().toUpperCase()})
              // Kiểm tra xem đã từng có ai thu hồi serial này chưa
              if (dt_tbidacap.length){
                this.$toast.error("Serial " + this.params.p_txtSerialCu + " đã được thu hồi vào  ngày " + dt_tbidacap[0]["NgayCap"] + " bởi user " + dt_tbidacap[0]["NguoiCn"] + "")
                return
              }
              // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
              if(dt.length > 1){
                this.$toast.error("Bạn hãy kiểm tra lại " + this.params.p_txtSerialCu + ". Hiện đang có " + dt.length + " PTM đang sử dụng.")
                return
              }
              // Nếu có 1 thuê bao thì kiểm tra tiếp
              if (dt.length == 1){
                // Thu hồi của thuê bao khác
                // Nếu thuê bao tìm thấy khác với thuê bao đang chọn
                if (dt[0]["PTM_ID"].toString() != this.ptm_id.toString() || dt[0]["LOAITBI_ECMS_ID"].toString() != this.loaitbi_ecms_id){
                  // Kiemes tra duoc phep sua khong
                  if (parseInt(dt[0]["PHIEUVT_ID"].toString()) != 0){
                    this.$toast.error("Serial " + this.params.p_txtSerialCu + " đã được sử dụng bởi PTM " + dt[0]["TEN_TBI"] + ". Bạn không có quyền được thu hồi")
                    return
                  }

                  await this.$bvModal.msgBoxConfirm("Serial " + this.params.p_txtSerialCu + " đã được sử dụng bởi PTM " + dt[0]["TEN_TBI"] 
                    + ". Bạn có chắc chắn muốn tiếp tục thu hồi không?", {
                    "okVariant": 'success',
                    "okTitle": 'Đồng ý',
                    "cancelTitle": 'Hủy',
                    "centered": true,
                    })
                    .then(async(value) => {
                      if (value){
                        //Tiếp tục thu hồi thì sẽ có 2 trường hợp
                        var ds = await this.getDSVatTu()
                        var f_rm = this.$refs.frmNhapText;
                        await f_rm.Load_frmNhapText();
                        await f_rm.frmNhapText_4(
                          "Serial thu hồi: " + this.params.p_txtSerialCu + ". Nhập serial mới nếu serial thu hồi sai",
                          "Tên vật tư",
                          ds
                        );
                        f_rm.params.p_VatTu = this.params.p_VatTu;
                        f_rm.Text.title = "Đổi serial";
                        f_rm.Text.btnXacNhan = "Đổi";
                        f_rm.Text.btnHuyBo = "Không đổi";
                        f_rm.functionName = "ThuHoiVatTu_V2";
                        this.$refs.popup_frmNhapText.show();
                        f_rm.dt = dt
                        //Đổi serial
                        //Lại có 2 trường hợp 
                        //1 Giữ nguyễn serial
                        //2 Thay đổi serial
                      }
                    })
                    .catch(err => {
                        // An error occurred
                    })

                }
                this.vsdvt_id = parseInt(dt[0]["SDVT_ID"].toString());
                vserial = this.params.p_txtSerialCu.toUpperCase();
                kieu_pvt = 4; //Không đổi
                await this.KiemTraVT(vserial, vvattu_id, kieu_pvt);
              }
              //Khong tim thay cua thue bao nao
              if (dt.length <= 0){
                kieu_pvt = 3; 
                this.vsdvt_id = 0;
                vvattu_id = parseInt(this.params.p_VatTu.toString());
                vserial = this.params.p_txtSerialCu.toUpperCase();
                await this.KiemTraVT(vserial, vvattu_id, kieu_pvt);
              }
            }
          }


          if (this.loaitbi_id == 3){
            if (this.vsdvt_id > 0){
              let ktsl = await this.SoLuongThuHoiVTTheoPhieu({sdVtId: this.vsdvt_id})
              if(ktsl.toString() != "0"){
                let sl_treo = parseInt(ktsl)
                if (this.sl_goc - sl_treo <= parseInt(this.params.p_soluong_cu) || (this.sl_goc - sl_treo <= 0)){
                  this.$toast.error("Hiện số lượng cấp thu hồi của thuê chưa QT là " + sl_treo + "/" + this.sl_goc + ". Bạn hãy kiểm tra lại số lượng thu hồi")
                  return
                }
              }
              else{
                let kt_sl = await this.SoLuongThuHoiVT({sdVtId: this.vsdvt_id})
                if (parseInt(kt_sl) < parseInt(this.params.p_soluong_cu)){
                    this.$toast.error("Bạn thu hồi quá số lượng hiện tại của PTM");
                    return;
                }
              }
              // Insert PVT
              // 1 0 0 1
              if (!this.Enabled.btnNhapMoi){
                await this.CAPNHAT_PHIEUVT_THUHOI_PTM(this.vsdvt_id, 1, 0, this.chitietcc_id, this.phieucc_id, this.ptm_id, this.loaitbi_ecms_id, 
                this.$auth.getDonViID, nhanvien_id_temp, parseInt(this.params.p_TinhTrangTbi), "CUONG1094", this.f_DateToString(new Date()),
                parseInt(this.params.p_VatTu), 0, parseInt(this.params.p_soluong_cu), parseInt(this.params.p_Droplydocap), parseInt(this.params.p_LyDoCap), 
                this.TRANGBI.VIENTHONG_TRANGBI, 1, this.params.p_txtGhiChu)

                this.$toast.success("Thu hồi thành công")
              }
              // 2 0 1
              if (this.Enabled.btnNhapMoi){
                await this.$bvModal.msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                  "okVariant": 'success',
                  "okTitle": 'Đồng ý',
                  "cancelTitle": 'Hủy',
                  "centered": true,
                  })
                  .then(async(value) => {
                      if (value){
                        await this.CAPNHAT_PHIEUVT_THUHOI_PTM(this.vsdvt_id, 2, this.phieu_vt_id, this.chitietcc_id, this.phieucc_id, this.ptm_id, this.loaitbi_ecms_id, 
                          this.$auth.getDonViID, nhanvien_id_temp, parseInt(this.params.p_TinhTrangTbi), "CUONG1094", this.f_DateToString(new Date()),
                          parseInt(this.params.p_VatTu), 0, parseInt(this.params.p_soluong_cu), parseInt(this.params.p_Droplydocap), parseInt(this.params.p_LyDoCap), 
                          this.TRANGBI.VIENTHONG_TRANGBI, 1, this.params.p_txtGhiChu)
                        this.$toast.success("Cập nhật dữ liệu thành công")
                      }
                  })
                  .catch(err => {
                      // An error occurred
                  })
              }
            }
            else{
              // Nhap cap khong co trong kho
              // 1 0 0 3
              if (!this.Enabled.btnNhapMoi){
                await this.CAPNHAT_PHIEUVT_THUHOI_PTM(this.vsdvt_id, 1, 0, this.chitietcc_id, this.phieucc_id, this.ptm_id, this.loaitbi_ecms_id, 
                this.$auth.getDonViID, nhanvien_id_temp, parseInt(this.params.p_TinhTrangTbi), "CUONG1094", this.f_DateToString(new Date()),
                parseInt(this.params.p_VatTu), 0, parseInt(this.params.p_soluong_cu), parseInt(this.params.p_Droplydocap), parseInt(this.params.p_LyDoCap), 
                this.TRANGBI.VIENTHONG_TRANGBI, 3, this.params.p_txtGhiChu)

                this.$toast.success("Thu hồi thành công")
              }
              // 2 0 3
              if (this.Enabled.btnNhapMoi){
                await this.$bvModal.msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                "okVariant": 'success',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy',
                "centered": true,
                })
                .then(async(value) => {
                    if (value){
                      await this.CAPNHAT_PHIEUVT_THUHOI_PTM(this.vsdvt_id, 2, this.phieu_vt_id, this.chitietcc_id, this.phieucc_id, this.ptm_id, this.loaitbi_ecms_id, 
                        this.$auth.getDonViID, nhanvien_id_temp, parseInt(this.params.p_TinhTrangTbi), "CUONG1094", this.f_DateToString(new Date()),
                        parseInt(this.params.p_VatTu), 0, parseInt(this.params.p_soluong_cu), parseInt(this.params.p_Droplydocap), parseInt(this.params.p_LyDoCap), 
                        this.TRANGBI.VIENTHONG_TRANGBI, 3, this.params.p_txtGhiChu)
                      this.$toast.success("Cập nhật dữ liệu thành công")
                    }
                })
                .catch(err => {
                    // An error occurred
                })
              }
            }
            await this.LoadDS()
          }


          if (this.loaitbi_id != 3 && this.loaitbi_id != 2){
            if (this.vsdvt_id > 0){
              let ktsl = await this.SoLuongThuHoiVTTheoPhieu({sdVtId: this.vsdvt_id})
              if(ktsl.toString() != "0"){
                let sl_treo = parseInt(ktsl)
                if (this.sl_goc - sl_treo <= parseInt(this.params.p_soluong_cu) || (this.sl_goc - sl_treo <= 0)){
                  this.$toast.error("Hiện số lượng cấp thu hồi của thuê chưa QT là " + sl_treo + "/" + this.sl_goc + ". Bạn hãy kiểm tra lại số lượng thu hồi")
                  return
                }
              }
              // Insert PVT
              // 1 0 0 1
              if (!this.Enabled.btnNhapMoi){
                await this.CAPNHAT_PHIEUVT_THUHOI_PTM(this.vsdvt_id, 1, 0, this.chitietcc_id, this.phieucc_id, this.ptm_id, this.loaitbi_ecms_id, 
                this.$auth.getDonViID, nhanvien_id_temp, parseInt(this.params.p_TinhTrangTbi), "CUONG1094", this.f_DateToString(new Date()),
                parseInt(this.params.p_VatTu), 0, parseInt(this.params.p_soluong_cu), parseInt(this.params.p_Droplydocap), parseInt(this.params.p_LyDoCap), 
                this.TRANGBI.VIENTHONG_TRANGBI, 1, this.params.p_txtGhiChu)

                this.$toast.success("Thu hồi thành công")
              }
              // 2 0 1
              if (this.Enabled.btnNhapMoi){
                await this.$bvModal.msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                  "okVariant": 'success',
                  "okTitle": 'Đồng ý',
                  "cancelTitle": 'Hủy',
                  "centered": true,
                  })
                  .then(async(value) => {
                      if (value){
                        await this.CAPNHAT_PHIEUVT_THUHOI_PTM(this.vsdvt_id, 2, this.phieu_vt_id, this.chitietcc_id, this.phieucc_id, this.ptm_id, this.loaitbi_ecms_id, 
                          this.$auth.getDonViID, nhanvien_id_temp, parseInt(this.params.p_TinhTrangTbi), "DAYTB", this.f_DateToString(new Date()),
                          parseInt(this.params.p_VatTu), 0, parseInt(this.params.p_soluong_cu), parseInt(this.params.p_Droplydocap), parseInt(this.params.p_LyDoCap), 
                          this.TRANGBI.VIENTHONG_TRANGBI, 1, this.params.p_txtGhiChu)
                        this.$toast.success("Cập nhật dữ liệu thành công")
                      }
                  })
                  .catch(err => {
                      // An error occurred
                  })
              }
            }
            else{
              // Nhap cap khong co trong kho
              // 1 0 0 1
              if (!this.Enabled.btnNhapMoi){
                await this.CAPNHAT_PHIEUVT_THUHOI_PTM(this.vsdvt_id, 1, 0, this.chitietcc_id, this.phieucc_id, this.ptm_id, this.loaitbi_ecms_id, 
                this.$auth.getDonViID, nhanvien_id_temp, parseInt(this.params.p_TinhTrangTbi), "DAYTB", this.f_DateToString(new Date()),
                parseInt(this.params.p_VatTu), 0, parseInt(this.params.p_soluong_cu), parseInt(this.params.p_Droplydocap), parseInt(this.params.p_LyDoCap), 
                this.TRANGBI.VIENTHONG_TRANGBI, 1, this.params.p_txtGhiChu)

                this.$toast.success("Thu hồi thành công")
              }
              // 2 0 1
              if (this.Enabled.btnNhapMoi){
                await this.$bvModal.msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                "okVariant": 'success',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy',
                "centered": true,
                })
                .then(async(value) => {
                    if (value){
                      await this.CAPNHAT_PHIEUVT_THUHOI_PTM(this.vsdvt_id, 2, this.phieu_vt_id, this.chitietcc_id, this.phieucc_id, this.ptm_id, this.loaitbi_ecms_id, 
                        this.$auth.getDonViID, nhanvien_id_temp, parseInt(this.params.p_TinhTrangTbi), "DAYTB", this.f_DateToString(new Date()),
                        parseInt(this.params.p_VatTu), 0, parseInt(this.params.p_soluong_cu), parseInt(this.params.p_Droplydocap), parseInt(this.params.p_LyDoCap), 
                        this.TRANGBI.VIENTHONG_TRANGBI, 1, this.params.p_txtGhiChu)
                      this.$toast.success("Cập nhật dữ liệu thành công")
                    }
                })
                .catch(err => {
                    // An error occurred
                })
              }
            }
            await this.LoadDS()
          }
        }
      }
      else{
        if (btnNhapMoi.Enabled){
            if (this.phieu_vt_id.toString() == "0"){
                this.$toast.error("Chưa có thông tin về phiếu vật tư! ");
                return;
            }
        }
        // Đoạn này kiểm tra các điều kiện cần thiết
        if (await this.KiemTraDuLieu_Th()) {
            if (this.loaitbi_id == 2){
              await this.CAPNHAT_PHIEUVT_THUHOI_PTM(this.vsdvt_id, 2, this.phieu_vt_id, this.chitietcc_id, this.phieucc_id, this.ptm_id, this.loaitbi_ecms_id, 
                          this.$auth.getDonViID, nhanvien_id_temp, parseInt(this.params.p_TinhTrangTbi), vseria_goc, this.f_DateToString(new Date()),
                          vvattu_id, 0, parseInt(this.params.p_soluong_cu), parseInt(this.params.p_Droplydocap), parseInt(this.params.p_LyDoCap), 
                          this.TRANGBI.VIENTHONG_TRANGBI, 1, this.params.p_txtGhiChu)
              this.$toast.success("Cập nhật dữ liệu thành công")
            }

        }
      }
      
    },
    async LoadDS(){
      await this.Load_DS_PhieuVT(this.phieucc_id)
      await this.LoadGrid_VatTuSD()
      // ChucNang.FocusGridViewRow(gridViewLichSuVatTu, "PHIEUVT_ID", phieu_vt_id); // phvt_id
    },
    async CAPNHAT_PHIEUVT_THUHOI_PTM(sdvt_id, insert, phieuvt_id, chitietcc_id, phieucc_id, ptm_id, loaitbi_ecms_id, 
    donvi_id, nhanvien_id, tinhtrangtbi_id, serial, ngay_th, 
    vattu_id, thietbi_id, soluong, lydocap_id, lydocaptbi_id, 
    trangbi_id, kieu, ghichu){
      let res = await this.CapNhatPhieuVTThuHoi({
        sdvt_id: sdvt_id,
        insert: insert,
        phieuvt_id: phieuvt_id,
        chitietcc_id: chitietcc_id,
        phieucc_id: phieucc_id,
        ptm_id: ptm_id,
        loaitbi_ecms_id: loaitbi_ecms_id,
        donvi_id: donvi_id,
        nhanvien_id: nhanvien_id,
        tinhtrangtbi_id: tinhtrangtbi_id,
        serial: serial,
        ngay_th: ngay_th,
        vattu_id: vattu_id,
        thietbi_id: thietbi_id,
        soluong: soluong,
        lydocap_id: lydocap_id,
        lydocaptbi_id: lydocaptbi_id,
        trangbi_id: trangbi_id,
        kieu: kieu,
        ghichu: ghichu,
      })
      return res
    },
    async KiemTra(vserial, dt, vvattu_id, kieu_pvt){
      let dt_sd = await this.KiemTraPTMSuDungVT({serial: vserial.trim().toUpperCase()})
      let dt_tbidacap_sd = await this.getDSThietBiDaCap({serial: vserial.trim().toUpperCase()})
      // Kiểm tra xem đã từng có ai thu hồi serial này chưa
      if (dt_tbidacap_sd.length){
        this.$toast.error("Serial " + vserial + " đã được thu hồi vào  ngày " + dt_tbidacap_sd[0]["NgayCap"].toString() + " bởi user " + dt_tbidacap_sd[0]["NguoiCn"].toString() + "")
        return
      }
      // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
      if(dt_sd.length){
        this.$toast.error("Bạn hãy kiểm tra lại " + vserial + ". Hiện đang có " + dt_sd.length + " PTM đang sử dụng.")
        return
      }
      // Nếu có 1 thuê bao thì kiểm tra tiếp
      if(dt_sd.length == 1){
        // Kiemes tra duoc phep sua khong
        if (dt[0]["PTM_ID"].toString() != this.ptm_id || dt[0]["LOAITBI_ECMS_ID"].toString() != this.loaitbi_ecms_id){
          this.$toast.error("Serial " + vserial + " đã được sử dụng bởi PTM " + dt_sd[0]["ten_tbi"] + ". Bạn hãy chọn serial khác")
          return
        }
      }
      await this.KiemTraVT(vserial, vvattu_id, kieu_pvt)

    },
    async KiemTraVT(vserial, vvattu_id, kieu_pvt){
      let isPopup = false
      let vseria_goc = this.params.p_txtSerialCu
      let slvt = await this.SoLuongVatTuPTM({vattu_id: vvattu_id, ptm_id: this.ptm_id, loaitbi_ecms_id: this.loaitbi_ecms_id})
      let xoavt = 0
      console.log('A')
      //Nếu là trường hợp đổi serial thì không cho
      if (slvt.length && vserial != ""){
        this.$toast.error("Mã vật tư bạn chọn PTM đang sử nhiều hơn 1")
        return
      }
      else if (slvt.length > 1 && kieu_pvt == 4){
        isPopup = true
        await this.$bvModal.msgBoxConfirm("PTM này đang sử dụng hơn 2 thiết bị cùng mã vật tư. Bạn có chắc chắn muốn thu hồi cả hai không?. Nếu bạn chọn không thì sẽ xóa vật tư còn lại.", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              console.log('C')
              if (value){
                xoavt = 0
                await this.ThuHoi_VT(vvattu_id, xoavt, kieu_pvt, vseria_goc, vserial)
              }
              else{
                xoavt = 1
                await this.ThuHoi_VT(vvattu_id, xoavt, kieu_pvt, vseria_goc, vserial)
              }
          })
          .catch(err => {
              // An error occurred
          })
      } 
      console.log('D')
      !isPopup ? await this.ThuHoi_VT(vvattu_id, xoavt, kieu_pvt, vseria_goc, vserial) : ''
    },
    async ThuHoi_VT(vvattu_id, xoavt, kieu_pvt, vseria_goc, vserial  ){
      let s = await this.CapNhatSDVT({
        kieu: 2,
        sdvt_id: this.vsdvt_id,
        vattu_id: vvattu_id,
        xoavt: xoavt,
        kieu_pvt: kieu_pvt,
        lydocaptbi_id: parseInt(this.params.p_LyDoCap),
        tinhtrangtbi_id: parseInt(this.params.p_TinhTrangTbi),
        ptm_id: this.ptm_id,
        loaitbi_ecms_id: this.loaitbi_ecms_id,
        hscc_id: this.hscc_id,
        chitietcc_id: this.chitietcc_id,
        phieucc_id: this.phieucc_id,
        donvi_id: this.$auth.getDonViID(),
        nhanvien_id: this.$auth.getNhanVienID(),
        soluong: this.params.p_soluong_cu,
        trangbi_id: parseInt(this.params.p_KieuTrangBi),
        ngay_th: this.f_DateToString(this.params.p_dtpNgayCap),
        ghichu: this.params.p_txtGhiChu,
        serial: vseria_goc,
        serial_new: vserial
      })
      console.log(s)
      if (s != "1"){
        this.$toast.error(s)
      }
      else{
        this.$toast.success("Cập nhật thành công")
      }
      await this.LoadDS()
    },
    async CapVatTu_Click(){

      await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn cập nhật vật tư?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                let kt = 0;
                let loi = "";
                let loi_cap = "";
                let nhanvien_id_temp = parseInt(this.$auth.getNhanVienID());
                if (parseInt(this.params.p_NhanVien.toString()) != parseInt(this.$auth.getNhanVienID())){
                    nhanvien_id_temp = parseInt(this.params.p_NhanVien);
                }

                if (await this.KiemTraDuLieu()){
                  let g = 0;
                  this.i = 0;
                  let soluong = 0;

                  for (let dr of this.vt_dccap){
                    //  Kiểm tra kho đơn vị cấp ra
                    try{
                      var kt_kq = await this.KiemTraVTCapRa({
                        phieucc_id: this.phieucc_id,
                        chitietcc_id: this.chitietcc_id,
                        tuvt_id: parseInt(dr["TUVT_ID"] || 0)
                      })

                      if (kt_kq.toString() != "1" || kt_kq == null){
                        this.$toast.error(kt_kq.toString());
                        return;
                      }
                    }
                    catch (error){
                      if (error.includes("ORA-00904")){
                        this.$toast.error(" Hàm kiemtra_vtcapra_ptm không được tìm thấy. Bạn hãy liên hệ admin");
                        return;
                      }
                      else{
                        this.$toast.error(" Có lỗi " + error);
                        return;
                      }
                    }

                    //  INSERT DAY
                    if (dr["SERIAL"] == null || dr["SERIAL"] == ""){
                        let s = await this.KiemTraDayCTBD({
                            phieucc_id: this.phieucc_id,
                            chitietcc_id: this.chitietcc_id,
                            tuvt_id: parseInt(dr["TUVT_ID"]),
                          })
                        if (s.toString() != "0"){
                            loi += dr["TEN_VT"].toString() + " đã được cấp bạn ko thể cấp tiếp đc \r\n";
                            continue;
                        }

                        kt += 1;
                        let v_phieuvt_id = ""
                        // let v_phieuvt_id = getkey.GetKey("phieuvt_id");
                        let v_ngay_cap = this.params.p_dtpNgayCap;
                        let v_kieutbi = vkieutbi;
                        let v_trangbi = vtrangbi;
                        let v_tuvt_id = parseInt(dr["TUVT_ID"].toString());
                        let v_thietbi_id = parseInt(dr["THIETBI_ID"].toString());
                        let v_soluong = parseInt(dr["LUONGCAP"].toString());
                        let serial = "";
                        if (dr["SERIAL"] != null && dr["SERIAL"] != ""){ 
                          serial = dr["SERIAL"].toString();
                        }
                            
                        let ma_tbi = "";
                        if (dr["MA_TBI"] != null && dr["SERIMA_TBIL"] != ""){
                          ma_tbi = dr["MA_TBI"].toString();
                        }
                            
                        let lydocap = parseInt(this.params.p_LyDoCap);
                        let tinhtrang_tbi = parseInt(this.params.p_TinhTrangCap);

                        loi_cap = await this.InsertPhieuVT({
                          sdvt_id: 0,
                          phieuvt_id: v_phieuvt_id,
                          phieucc_id: this.phieucc_id,
                          ngay_cap: this.f_DateToString(v_ngay_cap),
                          kieutbi_id: v_kieutbi,
                          trangbi_id: v_trangbi,
                          tuvt_id: v_tuvt_id,
                          thietbi_id: v_thietbi_id,
                          soluong: v_soluong,
                          serial: "",
                          ma_tbi: ma_tbi,
                          nhanvien_id: nhanvien_id_temp,
                          donvi_id: parseInt(this.$auth.getDonViID()),
                          lydocap_id: lydocap,
                          lydocapto_id: this.ld_to,
                          chitietcc_id: this.chitietcc_id,
                          ptm_id: this.ptm_id,
                          loaitbi_ecms_id: this.loaitbi_ecms_id,
                          ttpk: 1,
                          tinhtrangbi_id: tinhtrang_tbi,
                          ghichu: ""
                        })

                        if (loi_cap != "1"){
                            this.$toast.error(loi_cap);
                            return;
                        }
                    }

                  //  Insert SERIAL
                    if (dr["SERIAL"] != null && dr["SERIAL"] != ""){
                      kt += 1;
                      let v_phieuvt_id = ""
                      // let v_phieuvt_id = getkey.GetKey("phieuvt_id");
                      let v_ngay_cap = this.params.p_dtpNgayCap;
                      let v_kieutbi = vkieutbi;
                      let v_trangbi = vtrangbi;
                      let v_tuvt_id = parseInt(dr["TUVT_ID"].toString());
                      let v_thietbi_id = parseInt(dr["THIETBI_ID"].toString());
                      let v_soluong = parseInt(dr["LUONGCAP"].toString());
                      let serial = "";
                      if (dr["SERIAL"] != null && dr["SERIAL"] != ""){
                        serial = dr["SERIAL"].toString();
                      }
                      let ma_tbi = "";
                      if (dr["MA_TBI"] != null && dr["MA_TBI"] != ""){
                        ma_tbi = dr["MA_TBI"].toString();
                      }

                      let lydocap = parseInt(CboLyDoCap.EditValue.toString());
                      let tinhtrang_tbi = parseInt(cboTinhTrangCap.EditValue.toString());

                      loi_cap = await this.InsertPhieuVT({
                        sdvt_id: 0,
                        phieuvt_id: v_phieuvt_id,
                        phieucc_id: this.phieucc_id,
                        ngay_cap: this.f_DateToString(v_ngay_cap),
                        kieutbi_id: v_kieutbi,
                        trangbi_id: v_trangbi,
                        tuvt_id: v_tuvt_id,
                        thietbi_id: v_thietbi_id,
                        soluong: v_soluong,
                        serial: serial,
                        ma_tbi: ma_tbi,
                        nhanvien_id: nhanvien_id_temp,
                        donvi_id: parseInt(this.$auth.getDonViID()),
                        lydocap_id: lydocap,
                        lydocapto_id: this.ld_to,
                        chitietcc_id: this.chitietcc_id,
                        ptm_id: this.ptm_id,
                        loaitbi_ecms_id: this.loaitbi_ecms_id,
                        ttpk: 1,
                        tinhtrangbi_id: tinhtrang_tbi,
                        ghichu: ""
                      })

                      if (loi_cap != "1"){
                        this.$toast.error(loi_cap);
                        return;
                      }
                    }
                  }

                  if (this.i == 0 && this.j == 0){
                      this.$toast.error("Cấp " + kt + " thiết bị thành công \r\n" + loi);
                  }
                  await this.Load_DS_PhieuVT(this.phieucc_id);
                  await this.Load_DS_VTTU();
                }
              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    async Load_DS_PhieuVT(_phieucc_id){
      var ds_LSVT = []
      ds_LSVT = await this.getDSPhieuVTPTM({
        phieucc_id: -1,
        chitietcc_id: this.chitietcc_id,
        kieutbi: 0,
        status: 0
      })
      if (this.params.p_rdioCKb.toString() == "6" && this.ds_LSVT.length == 0){
        ds_LSVT = await this.getDSPhieuVTPTM({
          phieucc_id: this.vphieu_cha_id,
          chitietcc_id: this.chitietcc_id,
          kieutbi: 0,
          status: 0
        })
      }

      if (ds_LSVT.length && Object.keys(ds_LSVT[0]).includes("DM_SL")){
          kt_dinhmuc = true;
      }
      this.gridLichSuVatTu = ds_LSVT;
    },
    async LayTT_VT(row){
      this.sl_goc = 0;
      
      var seri = row["SERIAL"] || ''
      var matbi = row["MA_TBI"] || ''
      this.Enabled.btnNhapMoi = false;
      if (seri == '' && matbi == ''){
          this.params.p_txtSerialCu = "";
          this.ReadOnly.soluong_cu = false;
          this.ReadOnly.cboVatTu = false;
          this.ReadOnly.txtSerialCu = false;
      }
      else if (seri != '' && matbi == ''){
          this.params.p_txtSerialCu = row["SERIAL"].toString();
          this.ReadOnly.soluong_cu = true;
          this.ReadOnly.cboVatTu = true;
          this.ReadOnly.txtSerialCu = true;
      }
      else if (seri == '' && matbi != ''){
          this.params.p_txtSerialCu = row["MA_TBI"].toString();
          this.ReadOnly.soluong_cu = false;
          this.ReadOnly.cboVatTu = false;
          this.ReadOnly.txtSerialCu = false;
      }
      this.params.p_VatTu = row["VATTU_ID"];
      var a = parseInt(row["SOLUONG"].toString());
      this.params.p_soluong_cu = a;
      this.sl_goc = a;
      this.vsdvt_id = parseInt(row["SDVT_ID"].toString());
      this.vphieuvt_id = parseInt(row["PHIEUVT_ID"].toString());
    },
    async frmCapVatTu_Load(){
      this.params.p_rdioCKb = "";

      await this.Load_Cbo();
      // ds_error.Columns.Add("Error");

      this.params.p_ckbLocVT = false;
      // Lấy TS mặc định
      let ds = await this.getThamSoMacDinh({kieuId: 0})
      
      if (ds.length > 0){
        let str_vtemp = "KIEMTRA_KYTU_SERIAL";
        let det = ds.filter(x => x["maTS"].toString() == str_vtemp)
        if (det.length){
          let row = det[0];
          if (row["tenTS"].toString().trim() != "0"){
              this.check_serial = row["tenTS"].toString().trim();
          }
        }

        str_vtemp = "CAPNHAT_CTS_MODEM";
        det = ds.filter(x => x["maTS"].toString() == str_vtemp)
        if (det.length > 0){
            let row = det[0];
            if (row["tenTS"].toString().trim() == "1"){ this.UPDATE_CTS = true; }
        }
      }

      // emptyEditor = new RepositoryItem();
      // gridLichSuVatTu.RepositoryItems.Add(emptyEditor);
      this.kt_load = true;

      await this.Load_quyen();

      //Nếu form được gọi từ form thống kê phiếu thi công
      if (this.ben_ngoai != 0){
        this.params.p_rdioCKb = this.ben_ngoai;
      }
    },
    async frmCapVatTu_PTM_TT(){
      this.ttdv_id = this.$auth.getDonViID()
      this.$refs.CboLyDoCap ? this.$refs.CboLyDoCap.focusIn() : ''
      // imageDir = "FILE/" + tt_nd.tentat_ccbs.ToUpper() + "_VT";
    },
    async btnChiTiet_Click(){
      if (this.params.p_rdioCKb == null || this.params.p_rdioCKb == ''){ return }
          
      if (this.params.p_KieuTb.toString() == "1"){
          this.Enabled.pnlThuHoi = false;
          this.ReadOnly.txtSerial = false;
          this.Visible.xtraTabPage1 = true;
          this.Visible.xtraTabPage2 = false;
          this.Visible.btnNhapMoi = false;
          this.Visible.tsbtnHuyBo = false;
          await this.Clear();
      }
      else{
          this.Enabled.pnlThuHoi = true;
          this.ReadOnly.txtSerial = true;
          this.Visible.xtraTabPage1 = false;
          this.Visible.xtraTabPage2 = true;
          this.Visible.btnNhapMoi = true;
          this.Enabled.btnNhapMoi = false;
          this.params.p_txtSerialCu = "";
          this.ReadOnly.soluong_cu = false;
          this.ReadOnly.cboVatTu = false;
          this.Visible.tsbtnHuyBo = true;
          this.ReadOnly.txtSerialCu = false;
          await this.Clear();
      }

      if (this.params.p_Kho == null || this.params.p_Kho == ''){
          this.$toast.error("Không có dữ liệu kho được quản lý");
          return;
      }
      await this.Load_SoPhieu("");
      await this.LoadThongTinThueBao("");
    },
    async Load_SoPhieu(ten){
      let sDATE = "0";
      let eDATE = "0";
      if (this.params.p_cbxTuNgay == true){
        sDATE = this.f_DateToString(this.params.p_dtpTuNgay,'DD/MM/YYYY')
        eDATE = this.f_DateToString(this.params.p_dtpDenNgay,'DD/MM/YYYY')
      }

      let DaGiao = []
      let ChoDuyet = []
      let TraLai = []
      let BoSung = []
      if (this.params.p_ckbDaCap){
        DaGiao = await this.getDSHsccPTM({
          ma_hscc: "0",
          tungay: sDATE,
          denngay: eDATE,
          nhanvien_id: parseInt(this.$auth.getNhanVienID()),
          kieu: 6,
          dacap: 1,
        })
        

        ChoDuyet = await this.getDSHsccPTM({
          ma_hscc: "0",
          tungay: sDATE,
          denngay: eDATE,
          nhanvien_id: parseInt(this.$auth.getNhanVienID()),
          kieu: 2,
          dacap: 1
        })

        TraLai = await this.getDSHsccPTM({
          ma_hscc: "0",
          tungay: eDATE,
          denngay: eDATE,
          nhanvien_id: parseInt(this.$auth.getNhanVienID()),
          kieu: 3,
          dacap: 1
        })

        BoSung = await this.getDSHsccPTM({
          ma_hscc: "0",
          tungay: eDATE,
          denngay: eDATE,
          nhanvien_id: parseInt(this.$auth.getNhanVienID()),
          kieu: 4,
          dacap: 1
        })
      }

      else{
        DaGiao = await this.getDSHsccPTM({
          ma_hscc: "0",
          tungay: eDATE,
          denngay: eDATE,
          nhanvien_id: parseInt(this.$auth.getNhanVienID()),
          kieu: 6,
          dacap: 0
        })

        ChoDuyet = await this.getDSHsccPTM({
          ma_hscc: "0",
          tungay: eDATE,
          denngay: eDATE,
          nhanvien_id: parseInt(this.$auth.getNhanVienID()),
          kieu: 2,
          dacap: 0
        })

        TraLai = await this.getDSHsccPTM({
          ma_hscc: "0",
          tungay: eDATE,
          denngay: eDATE,
          nhanvien_id: parseInt(this.$auth.getNhanVienID()),
          kieu: 3,
          dacap: 0
        })

        BoSung = await this.getDSHsccPTM({
          ma_hscc: "0",
          tungay: eDATE,
          denngay: eDATE,
          nhanvien_id: parseInt(this.$auth.getNhanVienID()),
          kieu: 4,
          dacap: 0
        })
      }

      let sl_dg = 0;
      let sl_cd = 0;
      let sl_tl = 0;
      let sl_bs = 0;

      sl_dg = DaGiao.length;
      sl_cd = ChoDuyet.length;
      sl_tl = TraLai.length;
      sl_bs = BoSung.length;

      let bs = "";
      let dg = "";

      if (sl_bs == 1000) { bs = "..."; }
          
      if (sl_dg == 1000) { dg = "..."; }
          

      this.Text.rdioCKb_0 = "Chờ duyệt (" + sl_cd + ")";
      this.Text.rdioCKb_1 = "Trả lại (" + sl_tl + ")";
      if (bs != ""){
        this.Text.rdioCKb_2 = "Cấp bổ sung (" + bs + ")";
      }
      else{
        this.Text.rdioCKb_2 = "Cấp bổ sung (" + sl_bs + ")";
      }
          

      if (dg != ""){
        this.Text.rdioCKb_3 = "Đã giao (" + dg + ")";
      }
      else{
        this.Text.rdioCKb_3 = "Đã giao (" + sl_dg + ")";
      }
    },
    async cboNhanVien_EditValueChanged(){
      if (this.params.p_NhanVien != null && this.params.p_NhanVien != ''){
        let dt = await this.getDSKhoTheoNV({
          nhanVienId: this.params.p_NhanVien,
          donViId: this.ttdv_id
        })
        this.cboKho = dt
        await this.Loc_Kho()
        dt.length ? this.params.p_Kho = dt[0]["KHO_ID"] : ''
      }
    },
    async Loc_Kho(){
      this.cboKho = this.cboKho.filter(item=>item.TRANGTHAI.toString() == "1")
    },
    async Droplydocaptb_EditValueChanged(){
      let r = this.$refs.CboLyDoCap.find(a=>a.MUCDICH_ID == this.params.p_LyDoCap)
      if (r["MA_MD"].toString() == "LD.3.2" || r["MA_MD"].toString() == "LD.3.1"){
          this.Enabled.pnlThuHoi = false;
      }
      else{
          this.Enabled.pnlThuHoi = true;
          await this.LayTT_VT(this.gridViewLichSuVatTu_FocusedRow);
      }
    },
    async grvDS_VT_Giao_CellValueChanged(){
      // lam sau
    },
    async grvDS_VT_Giao_RowStyle(){
      // lam sau
    },
    async grvDS_VT_Giao_ShowingEditor(){
      // lam sau
    },
    async grvDS_VT_Giao_FocusedRowChanged(row){
      this.grcDS_VT_Giao_FocusedRow = row
      // spSL_Giao.MaxValue = parseInt(row["SL_TON"]);
    },
    async spSL_Giao_Leave(){
      // grvDS_VT_Giao.CloseEditor();
    },
    async vbtnRefresh_MouseClick(){
      await this.Load_DS_VTTU()
    },
    // Keys.Enter 
    async txtSerial_KeyDown(){
      if (this.phieucc_id == 0){ this.$toast.error("Bạn chưa chọn thuê bao để cấp") }

      if (this.params.p_txtSerial != "" && this.params.p_KieuTb.toString() == "1"){
        let rowHandle = this.grcDS_VT_Giao.find(a=>a.SERIAL == this.params.p_txtSerial.trim())

        if (Object.keys(rowHandle)){

          let slton = parseInt(rowHandle["SL_TON"].toString());
          if (slton != 0){
            let dr = rowHandle
            // let v_phieuvt_id = getkey.GetKey("phieuvt_id");
            let v_phieuvt_id = ""
            let v_ngay_cap = this.params.p_dtpNgayCap;
            let v_kieutbi = this.vkieutbi;
            let v_trangbi = this.vtrangbi;
            let v_tuvt_id = parseInt(dr["TUVT_ID"].toString());
            let v_thietbi_id = parseInt(dr["THIETBI_ID"].toString());
            let v_soluong = 1;
            let ma_tbi = "";
            let lydocap = parseInt(this.params.p_LyDoCap.toString());
            let tinhtrang_tbi = parseInt(dr["TINHTRANGTBI_ID"].toString());
            let thongbao = "";

              // Kiểm tra kho đơn vị cấp ra
              try{
                var kt_kq = await this.KiemTraVTCapRa({
                  phieucc_id: this.phieucc_id,
                  chitietcc_id: this.chitietcc_id,
                  tuvt_id: parseInt(dr["TUVT_ID"]),
                })
                
                if (kt_kq.toString() != "1" || kt_kq == null){
                    this.$toast.error(kt_kq.toString());
                    return;
                }
              }
              catch (error){
                  if (error.includes("ORA-00904")){
                      this.$toast.error(" Hàm kiemtra_vtcapra_ptm không được tìm thấy. Bạn hãy liên hệ admin");
                      return;
                  }
                  else{
                      this.$toast.error(" Có lỗi " + error);
                      return;
                  }
              }

              let loi_cap = await this.InsertPhieuVT({
                sdvt_id: 0,
                phieuvt_id: v_phieuvt_id,
                phieucc_id: this.phieucc_id,
                ngay_cap: v_ngay_cap,
                kieutbi_id: v_kieutbi,
                trangbi_id: v_trangbi,
                tuvt_id: v_tuvt_id,
                thietbi_id: v_thietbi_id,
                soluong: v_soluong,
                serial: this.params.p_txtSerial.trim().toUpperCase(),
                ma_tbi: ma_tbi,
                nhanvien_id: parseInt(this.$auth.getNhanVienID()),
                donvi_id: this.$auth.getDonViID(),
                lydocap_id: lydocap,
                lydocapto_id: this.ld_to,
                chitietcc_id: this.chitietcc_id,
                ptm_id: this.ptm_id,
                loaitbi_ecms_id: this.loaitbi_ecms_id,
                ttpk: 1,
                tinhtrangbi_id: tinhtrang_tbi,
                ghichu: this.params.p_txtGhiChu,
              })

              if (loi_cap != "1"){
                  this.$toast.error(loi_cap);
                  return;
              }

              this.params.p_txtSerial = "";

              await this.Load_DS_PhieuVT(this.phieucc_id);
              await this.Load_DS_VTTU();
          }
          else{
            this.$toast.error("Số lượng tồn không có");
          }
          this.grcDS_VT_Giao_FocusedRow = rowHandle;
        }
        else{
          this.$toast.error("Không tìm thấy Serial");
        }
      }
      else if (this.params.p_txtSerial == ""){
        this.$toast.error("Bạn chưa nhập Serial");
      }
    },
    async gridviewDanhSach_FocusedRowChanged_sender(row){
      if (Object.keys(row).length == 0){
        this.gridLichSuVatTu = [];
        this.tudaycu_id = 0;
        this.params.p_KetQuaXL = 0;
        return
      }
      else{
        this.tudaycu_id = 0
        await this.gridviewDanhSach_FocusedRowChanged(row)
      }
    },
    async cboKho_EditValueChanged(){
      await this.Load_DS_VTTU()
    },
    async btnCapNhat_Click(){
      if (this.params.p_KieuTb.toString() == "1"){
        await this.CapVatTu_Click();
      }      
      else{
        await this.ThuHoiVatTu_V2();
      }
    },
    async tsbtnXuatExcel_Click(){
      if (this.gridLichSuVatTu.length <= 0) { return; }
      await this.HT_DLG_XUAT_EXCEL("DanhSachVatTu.xls", "sheet1",this.gridLichSuVatTu)
    },
    async HT_DLG_XUAT_EXCEL(fileName, sheetName, exportData, ){
      let data1 = XLSX.utils.json_to_sheet(exportData);
      let wb = XLSX.utils.book_new(); // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, sheetName); // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, fileName);
    },
    async lnkXuatExcel_ProgressChanged(){
      // this.Update();
    },
    async frmCapVatTu_PTM_TT_KeyDown(){
      // if (e.Control && e.KeyCode == Keys.F)
      // {
        await this.LoadThongTinThueBao("");
      // }
    },
    async load_img(fileanh){
      this.s = ""
      this.result = ""
      this.listImage = []
      if (fileanh != "" && fileanh != null && fileanh != []){
        this.s = fileanh
        let words = this.s.split('|')
        for (let i in words){
          if (i != "" && i != null){
            var imgLink = i
            if (!i.includes(this.SERVER_IMAGES.IP)){
              imgLink = SERVER_IMAGES.BASE_URL + this.$auth.getMaTinh() + "_VT/"
            }
            this.listImage.push(imgLink)
          }
        }
        

      }

    },
    async gridViewLichSuVatTu_FocusedRowChanged(row){
      if (Object.keys(row).length){
        this.gridViewLichSuVatTu_FocusedRow = row
        this.vt_id = row.PHIEUVT_ID
        let link = await this.getDSHinhAnh({phieuVtId: this.vt_id})
        await this.load_img(link)
        await this.LayTT_PVT(row)
      }
      else{
        this.listImage = [];
        phvt_id = 0;
      }
    },
    async btnUploadImage_Click(){
      document.getElementById('fileUpload').click()
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

    async UploadImagesChange(args){
      let uploadedFiles = [];
      let files = args.target.files;
      
      if (files.length > 0) {
        try {
          this.$root.showLoading(true);
          for (let i = 0; i < files.length; i++) {
            let formData = new FormData();
            formData.append("file", files[i]);
            let uploadedFile = await this.UploadFiles(formData);
            if (uploadedFile.status) {
              let urls = {
                presignedUrl: uploadedFile.message.presignedUrl,
                relativeUrl: uploadedFile.message.relativeUrl,
              };
              this.images.push(urls);
              uploadedFiles.push(urls);
            }
          }
        } finally {
          this.$root.showLoading(false);
        }
      }
      if (uploadedFiles.length > 0) {
        this.$root.toastSuccess(
          "Đã thêm được " + uploadedFiles.length + " hình ảnh mới"
        );
        this.$emit("filesUploaded", {
          files: this.images,
          uploadedFiles: uploadedFiles,
        });
        this.$emit("filesChanged", this.images);
      }
    
    },
    // async fu_UploadCompleted(){
    //   // lam sau
    // },
    // async listImage_ItemMouseHover(){
    //   // lam sau
    // },
    // async listImage_MouseLeave(){
    //   // lam sau
    // },
    // async DelFile(){
    //   // lam sau
    // },
    // async btnXoaAnh_Click(){

    // },
    async v_btnRef_MouseClick(){
      await this.Load_DS_PhieuVT()
    },
    async radioCheck_CheckedChanged(){
      // lam sau
    },
    async sodaycu_EditValueChanged(){
      // lam sau
    },
    async grvDS_VT_Giao_CustomSummaryCalculate(){
      // lam sau
    },
    async gridViewLichSuVatTu_CellValueChanged(){
      // lam sau
    },
    async gridViewLichSuVatTu_RowStyle(){
      // lam sau
    },
    async gridViewLichSuVatTu_CustomSummaryCalculate(){
      // lam sau
    },
    async btnXoaLSVT_ButtonClick(row){
      await this.$bvModal.msgBoxConfirm("Bạn có thực sự muốn xóa phiếu vật tư đang chọn không?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                var vphieuvt_id = row["PHIEUVT_ID"];
                var vtuvt_id = row["TUVT_ID"];
                var vsoluong = row["SOLUONG"];
                let sdvt_xoa = parseInt(row["SDVT_ID"].toString());
                var ktbi = row["KIEUTBI_ID"];
                let s = "";
                s = await this.CapNhatThuHoiVatTuV2({
                  "serial": "",
                  "sdVtId": sdvt_xoa,
                  "kieu": ktbi.toString() == "1" ? 3 : 2,
                  "phieuVtId": parseInt(vphieuvt_id),
                  "nguoiCn": this.$auth.getUserName(),
                  "mayCn": this.$auth.getUserName(),
                  "ipCn": "10.59.90.123"
                })
                if (s == "1"){
                  this.$toast.success("Xóa phiếu vật tư thành công")
                }
                else{
                  this.$toast.error("Lỗi xóa phiếu " + s)
                }
                await this.Load_DS_PhieuVT(this.phieucc_id);
                await this.Load_DS_VTTU();
                let q = this.params.p_TinhTrangTbi
                await this.Clear()
                q ? this.params.p_TinhTrangTbi = q : ''
                this.Enabled.btnNhapMoi = false

              }
          })
          .catch(err => {
              // An error occurred
          })
    },
    gridViewLichSuVatTu_CustomRowCellEdit(){
      // lam sau
    },
    async btnHoanCong_Click(){
      let flag = true
      ngay_hc = "";
      let s = await this.getPhieuVTHscc({
                hscc_id: this.hscc_id,
                kieutbi: 0,
                status: 0})
      if (s.length == 0){
        if (this.params.p_rdioCKb.toString() == "4" || this.params.p_rdioCKb.toString() == "6"){
          this.$toast.error("PTM chưa được cấp vật tư không thể hoàn thành");
          return;
        }
        await this.$bvModal.msgBoxConfirm("Bạn có muốn hoàn thành hồ sơ cải tạo/bảo dưỡng với nội dung không cập hoặc thu hồi vật tư không?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
        })
        .then(async(value) => {
            if (value){
              flag = false
              var kq = await this.getPhieuKhongVT({hscc_id: this.hscc_id})
              if (kq == null || kq == ""){
                  this.$toast.error("Không hoàn thiện được phiếu");
                  return;
              }
              if (kq.toString() != "1"){
                  this.$toast.error("Không hoàn thiện được phiếu do " + kq);
                  return;
              }
              this.$toast.error("Hoàn thiện phiếu thành công");
              await this.LoadThongTinThueBao("");
              return;
            }
        })
        .catch(err => {
            // An error occurred
        })
      }
      // Xử lý bất đồng bộ // quý
      if (flag){
        let kt = 0;
        await this.$bvModal.msgBoxConfirm("Bạn có muốn hoàn thành phiếu này không với mục đích cấp là " + this.Droplydocap.find(a=>a.MUCDICH_ID == this.params.p_Droplydocap).MUCDICH || '' + "?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                let s = await this.DelUpdateLyDo({
                  phieucc_id: this.phieucc_id,
                  mucdich_id: this.ld_to,
                  hscc_id: this.hscc_id
                })
                //CÒn các TH khác thz
                let vkieu = 0 
                if (this.params.p_rdioCKb.toString() == "4"){
                  vkieu = 1;
                }
                await this.GiaoPhieuTdQTVT({
                  kieu: vkieu,
                  hscc_id: this.hscc_id,
                  phieucc_id: this.phieucc_con_id,
                  donvi_giao_id: this.$auth.getDonViID(),
                  nhanvien_id: parseInt(this.$auth.getNhanVienID())
                })
                this.$toast.success("Hoàn công thành công")
                await this.LoadThongTinThueBao()
              }
          })
          .catch(err => {
              // An error occurred
          })
      }
    },
    async ckb_CheckedChanged(){
      // let i = int.Parse(this.grcDS_VT_Giao_FocusedRow["ISCHECK"].toString());
      // var serial = this.grcDS_VT_Giao_FocusedRow["SERIAL"];
      // var matbi = this.grcDS_VT_Giao_FocusedRow["MA_TBI"];
      // var ten_tbi = this.grcDS_VT_Giao_FocusedRow["TEN_VT"];
      // let slton = parseInt(this.grcDS_VT_Giao_FocusedRow["SL_TON"].toString());

      // if (i == 0){
      //     grcDS_VT_Giao.SetFocusedRowCellValue(grcDS_VT_Giao.Columns["LUONGCAP"], 0);
      // }
      // // nếu check mà chauw có nt
      // else if (i == 1){
      //     grcDS_VT_Giao.SetFocusedRowCellValue(grcDS_VT_Giao.Columns["LUONGCAP"], slton);
      // }
    },
    async grcDS_VT_Giao_KeyDown(){

    },
    async Clear(){
      this.params.p_VatTu = "";
      this.params.p_TinhTrangTbi = "";
      this.params.p_txtSerialCu = "";
      this.params.p_soluong_cu = 0;
      this.params.p_txtGhiChu = "";
      this.phvt_id = 0;
      this.vsdvt_id = 0;
    },
    async cboKieuTb_EditValueChanged(){
      if (this.params.p_KieuTb.toString() == "1"){
        this.Enabled.pnlThuHoi = false;
        this.Enabled.cboTinhTrangTbi = true;
        this.Visible.ckbLocVT = true;
        this.Enabled.cboVatTu = false;
        this.Enabled.txtSerialCu = false;
        this.Enabled.soluong_cu = false;
        this.Enabled.txtGhiChu = false;

        this.ReadOnly.txtSerial = false;
        this.Visible.xtraTabPage1 = true;
        this.Visible.xtraTabPage2 = false;
        this.Visible.btnNhapMoi = false;
        this.Visible.tsbtnHuyBo = false;
        this.cboTinhTrangTbi = await this.getDSTinhTrangThietBiVT()
        this.cboTinhTrangTbi.length ? this.params.p_TinhTrangTbi = this.cboTinhTrangTbi[0].tinhTrangTBiId : ''

        // await this.getDSTinhTrangThietBiVT()
        this.cboTinhTrangCap = this.cboTinhTrangTbi
        this.cboTinhTrangCap.length ? this.params.p_TinhTrangCap = this.cboTinhTrangCap[0].tinhTrangTBiId : ''
        await this.Clear();
      }
      else{
        this.Visible.ckbLocVT = false;
        this.Enabled.pnlThuHoi = true;
        await this.LoadGrid_VatTuSD();
        this.ReadOnly.txtSerial = true;
        this.Visible.xtraTabPage1 = false;
        this.Visible.xtraTabPage2 = true;
        this.Visible.btnNhapMoi = true;
        this.Enabled.btnNhapMoi = false;
        this.Enabled.cboVatTu = true;
        this.Enabled.txtSerialCu = true;
        this.Enabled.soluong_cu = true;
        this.Enabled.txtGhiChu = true;
        this.params.p_txtSerialCu = "";

        this.cboTinhTrangTbi = await this.getDSTinhTrangVT()
        this.cboTinhTrangTbi.length ? this.params.p_TinhTrangTbi = this.cboTinhTrangTbi[0].tinhTrangTBiId : ''
        this.ReadOnly.soluong_cu = false;
        this.ReadOnly.cboVatTu = false;
        this.Visible.tsbtnHuyBo = true;
        this.ReadOnly.txtSerialCu = false;
      }
    },
    async gridViewVatTuSD_FocusedRowChanged(){
      // DHSX empty
    },
    async btnThuHoi_ButtonClick(row){
      await this.LayTT_VT(row)
    },
    async btnNhapMoi_Click(){
      await this.Clear();
      this.ReadOnly.txtSerial = true;
      this.Enabled.btnCapNhat = true;
      this.Visible.btnNhapMoi = true;
      this.Enabled.btnNhapMoi = false;
      this.params.p_txtSerialCu = "";
      this.ReadOnly.soluong_cu = false;
      this.ReadOnly.cboVatTu = false;
      this.ReadOnly.txtSerialCu = false;
    },
    async rdioCKb_EditValueChanged(){
      this.loading(true)
      if (this.params.p_rdioCKb == null || this.params.p_rdioCKb == ''){ return }
      if (!this.Text.rdioCKb_0.includes("(")){
        await this.Load_SoPhieu("");
      }
      if (this.ben_ngoai != 0){
        await this.LoadThongTinThueBao(this.tb_benngoai);
      } 
      else{
        await this.LoadThongTinThueBao("");
      }
      this.loading(false)
    },
    async listImage_MouseDoubleClick(){
      // lam sau
    },
    async xtraTabControl1_SelectedPageChanged(){
      // lam sau
      // if (e.Page != null)
      //     lblHeader2.Text = e.Page.Text;
      // else
      //     lblHeader2.Text = "";
    },
    async TRACUU_VATTU(){
      let dt = await this.getVaTuTheoSerial({serial: this.params.p_txtSerialCu})
      if(dt.length){
        this.params.p_VatTu = dt[0]["VATTU_ID"]
        this.Enabled.cboVatTu = false;
        this.params.p_soluong_cu = 1;
        return
      }
      this.Enabled.cboVatTu = true;
    },
    async txtSerialCu_KeyPress(){
      if (this.params.p_txtSerialCu){
        await this.TRACUU_VATTU(true);
        this.$refs.txtSerialCu.focus();
      }
    },
    async tsbtnHuyBo_Click(){
      await this.Clear();
      this.ReadOnly.txtSerial = true;
      this.Enabled.btnCapNhat = true;
      this.Visible.btnNhapMoi = true;
      this.Enabled.btnNhapMoi = false;
      this.params.p_txtSerialCu = "";
      this.ReadOnly.soluong_cu = false;
      this.ReadOnly.cboVatTu = false;
      this.Enabled.cboVatTu = true;
      this.ReadOnly.txtSerialCu = false;
    },
    async btnHuyGiao_ButtonClick(row){
      if (this.vphieu_cha_id != 0 && this.phieucc_id != 0){
          let ma_hs_cc = "";
          ma_hs_cc = row["MA_HS_CC"]

          await this.$bvModal.msgBoxConfirm("Bạn có muốn hủy giao phiếu của hồ sơ " + ma_hs_cc + " không?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                let s = await this.HuyGiaoHsccPTM({
                    phieucc_id: this.phieucc_id,
                    phieu_cha_id: this.vphieu_cha_id,
                    hscc_id: this.hscc_id
                })
                if (s == "1"){
                    this.$toast.error("Hủy giao phiếu của Hồ sơ PTM " + ma_hs_cc + " thành công");
                    await this.LoadThongTinThueBao("");
                    return;
                }
                this.$toast.error("Có lỗi khi hủy giao phiếu " + s);
                await this.LoadThongTinThueBao("");
                //Kiem tra co the huy giao duoc khong 
              }
          })
          .catch(err => {
              // An error occurred
          })
      }
    },
    async gridViewVatTuSD_CustomRowCellEdit(){
      // lam sau
      // if (e.Column == colEdit && e.RowHandle >= 0)
      // {
      //     if ((gridViewVatTuSD.GetRowCellValue(e.RowHandle, "PHIEUVT_ID") == DBNull.Value || gridViewVatTuSD.GetRowCellValue(e.RowHandle, "PHIEUVT_ID").toString() == "0")
      //         && (gridViewVatTuSD.GetRowCellValue(e.RowHandle, "SERIAL") != DBNull.Value || gridViewVatTuSD.GetRowCellValue(e.RowHandle, "SERIAL").toString() != ""))
      //         e.RepositoryItem = btnEdit;
      //     else
      //         e.RepositoryItem = emptyEditor;
      // }
    },
    async btnEdit_ButtonClick(row){
      let isAsync = true // thêm biến xử lý bdb

      let seri = row["SERIAL"].trim().toUpperCase()
      let seri_new = ""
      this.vsdvt_id = parseInt(row["SDVT_ID"])
      let vvattu_id = row["VATTU_ID"]
      let vvattu_id_new = 0
      let dt = await this.KiemTraPTMSuDungVT({serial: seri})
      let dt_tbidacap = await this.getDSThietBiDaCap({serial: seri})
      // Kiểm tra xem đã từng có ai thu hồi serial này chưa
      if (dt_tbidacap.length){
        this.$toast.error("Serial " + this.params.p_txtSerialCu + " đã được thu hồi vào  ngày " + dt_tbidacap[0]["NgayCap"].toString() + " bởi user " +
                          dt_tbidacap[0]["NguoiCn"].toString() + "")
        return
      }
      // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
      if (dt.length > 1){
        await this.$bvModal.msgBoxConfirm("Hiện đang có " + dt.length + " PTM đang sử dụng " + seri + ". Bạn có muốn cập nhật lại seri cho thuê bao này không?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                isAsync = true
              }
              else{
                isAsync = false
              }
          })
          .catch(err => {
              // An error occurred
          })
      }

      // Nếu có 1 thuê bao thì kiểm tra tiếp
      if (dt.length == 1){
        //Nếu thuê bao tìm thấy khác với thuê bao đang chọn
        if(parseInt(dt[0]["PTM_ID"]) != parseInt(this.ptm_id) || parseInt(dt[0]["LOAITBI_ECMS_ID"]) != parseInt(this.loaitbi_ecms_id) ){
          // Kiemes tra duoc phep sua khong
          if (parseInt(dt[0]["PHIEUVT_ID"]) != 0){
            this.$toast.error("Serial " + seri + " đã được sử dụng bởi thuê bao " + dt[0]["MA_TB"] + ". Bạn không có quyền được thu hồi")
            return
          }

          await this.$bvModal.msgBoxConfirm("Serial " + seri + " đã được sử dụng bởi thuê bao " + dt[0]["MA_TB"] + ". Bạn có chắc chắn muốn tiếp tục sửa không?", {
          "okVariant": 'success',
          "okTitle": 'Đồng ý',
          "cancelTitle": 'Hủy',
          "centered": true,
          })
          .then(async(value) => {
              if (value){
                //Tiếp tục thu hồi thì sẽ có 2 trường hợp
                isAsync = true
              }
              else{
                isAsync = false
              }
          })
          .catch(err => {
              // An error occurred
          })

        }
      }
      
      if (isAsync){
        let ds = await this.getDSVatTu()
        var f_rm = this.$refs.frmNhapText;
        await f_rm.Load_frmNhapText();
        await f_rm.frmNhapText_4(
          "Serial thu hồi: " + seri + ". Nhập serial bạn muốn thay đổi",
          "Tên vật tư",
          ds
        );
        f_rm.params.p_VatTu = vvattu_id
        f_rm.Text.title = "Đổi serial";
        f_rm.Text.btnXacNhan = "Đổi";
        f_rm.Text.btnHuyBo = "Hủy";
        f_rm.functionName = "btnEdit_ButtonClick";
        f_rm.seri = seri
        this.$refs.popup_frmNhapText.show();
        //Đổi serial
        //Lại có 2 trường hợp 
        //1 Giữ nguyễn serial
        //2 Thay đổi serial
      }
    },
    tsbtnInBienBan_Click(){

    },
    async cboKetQuaXL_EditValueChanged(){
      if (this.params.p_KetQuaXL == null) { return; }
      let kq_id = parseInt(this.params.p_KetQuaXL);
      let vnhom_ton = 0;

      if (kq_id == 2){
        this.Enabled.cboLyDoTon = false;
      }
      else if (kq_id == 3){
        vnhom_ton = 9;
        this.Enabled.cboLyDoTon = true;
      }
      else{
        this.Enabled.cboLyDoTon = false;
      }
      //  Nạp combobox Lý do tồn
      this.cboLyDoTon = await this.getDSLyDoTon({nhomTonId: vnhom_ton})
    },
    async excelTBToolStripMenuItem_Click(){
      // gridviewDanhSach.ExportExcel();
    },
    async ckbLocVT_CheckedChanged(){
      await this.Load_DS_VTTU()
    },
    async ckbLocVT_EditValueChanged(){

    },
    async tsbtUpHS_Click(){
      this.$router.push('/contract/UploadEProfile')
      // var f = this.$refs.frmHoSo_KH
      // // f.MdiParent = this.MdiParent;
      // f.Tag = "1"
      // let magd_text = this.gridviewDanhSach_FocusedRow.MA_GD || ""
      // f.txtMaGD = magd_text
      // magd_text ? f.TIM_GD(null, 1) : ""
      // f.show()
    },
    tsbtnXemHSDT_Click(){
      // DHSX comment
    },
    async cbxTuNgay_CheckedChanged(){
      this.Enabled.dtpTuNgay = this.params.p_cbxTuNgay;
      this.Enabled.dtpDenNgay = this.params.p_cbxTuNgay;
    },
    MOTA_XEMANH_Click(args){
      if (args.column.field == "MOTA"){
        this.$refs.popup_XemAnh.show()


      }
    },
    












  },
created: async function() {
    await this.frmCapVatTu_PTM_TT()
    await this.frmCapVatTu_Load()
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>
  .disabled a {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }

  .disabled a:hover {
    cursor: default;
    color: #6c757d;
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