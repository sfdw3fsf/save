<template src='./XacNhanDeNghi.html'></template>
<style scoped src='./XacNhanDeNghi.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/xacnhandenghi";
import moment from "moment";
import frmGiaoPhieu from "../frmGiaoPhieu/frmGiaoPhieu.vue";
import frmChonPhieuDeNghi from "../frmChonPhieuDeNghi/frmChonPhieuDeNghi.vue";
import frmTraPhieuVT from "../frmTraPhieuVT/frmTraPhieuVT.vue";
import {previewPrint} from "../../../utils/util";
import { currency } from "@/filters/currency";
import Vue from "vue";

export default {
  components: { 
    frmGiaoPhieu,
    frmChonPhieuDeNghi,
    frmTraPhieuVT
  },
  name: "XacNhanDeNghi",

  data() {
    return {
     
      DSLoaiPhieu: [],
      LoaiPhieuSelected: {},
      DSDeNghi: [],
      DSChiTiet: [],
      DSFile : [],
      DSChiTietChecked : [],
      txtMa: "",
      txtDonViGiao : "",
      txtNhanVienGiao : "",
      
      BOQUA_GIAOPHIEU_VT_TIEPTHEO : false,
      VisibleColGridDanhSach : true,
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
      
      vdenghi_id: 0,
      vloaiphieu_id: 0,
      vma_denghi : "",
      vloaiphieu_id_chon : "",
      mota: "",
      vquyettoan_id: 0,
      vphieu_id: 0,
      huonggiao_id: 0,
      phieu_cha_id: 0,
      ma_phieu: "",
      hdkh_id: 0,

      showGiaoPhieu : true,
      Enabled:{
        tsbtnGiaoPhieu : false,
        tsbtnXemPhieu :false,
        tsbtnTraPhieu : false,
        tsbtnTraCtTb: false,
        tsbtnBBBG : false,
        tsbtnHoanThanh: false,
        tsbtnPhieuDN : false
      },
      animationSettings: { effect: 'Zoom' },
      customTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },
    };
  },

  computed: {
    ...mapState("xacnhandenghi", statePropertyName),
    ...mapGetters("xacnhandenghi", getterName),
    PTAggregatesColumns() {
      return [
        {
          field: "THANHTIEN",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalTIEN,
        },
      ];
    },
  },
  methods: {
    ...mapActions("xacnhandenghi", actionName),
    ...mapMutations("xacnhandenghi", mutationName),

    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },

    getTotalTIEN() {
      var TongTien = 0;
      this.DSChiTiet.forEach((e) => {
        if(e.THANHTIEN !== undefined && e.THANHTIEN !== null){
          TongTien += e.THANHTIEN;
        }        
      });
      return currency(TongTien);
    },
    async handleDSLoaiPhieu(){
      var DSLoaiPhieu = await this.getDSLoaiPhieu({
        nhanVienId: this.$root.token.getNhanVienID(),
        kieu: 7,
        param1: "",
        param2: ""
      })
      if(DSLoaiPhieu.length > 0){
        this.DSLoaiPhieu = DSLoaiPhieu.slice(1); // remove danh sách loại phiếu
      }
    },
    async frmXacNhanDeNghi_Load(){
      await this.handleDSLoaiPhieu()
  
      var DSThamSoMacDinh = await this.getDSThamSoMacDinh(-1)
      
      if(DSThamSoMacDinh.length > 0){
        this.BOQUA_GIAOPHIEU_VT_TIEPTHEO = this.setThamSo("BOQUA_GIAOPHIEU_VT_TIEPTHEO",DSThamSoMacDinh)
      }

    },
    setThamSo(maTS,ds){
      let ds_filter = ds.filter(a=>a.maTS == maTS && a.tenTS == "1" )
      if (ds_filter.length > 0){
        return true
      }
      return false
    },
    async gridLoaiPhieuSelectRow(row){
      console.log(row , " = row")
      if(row){
        try{
           this.loading(true)
          this.LoaiPhieuSelected = row
          await this.LayDanhSachDeNghiQT()
        }catch(e){

        }finally{
          this.loading(false)
        }       
        
      }
    },
    async LayDanhSachDeNghiQT(){
      this.vloaiphieu_id = this.LoaiPhieuSelected.LOAIPHIEU_ID
      this.DSDeNghi = await this.getDSDeNghiQuyetToan({
        trangThaiQt : 2,
        loaiPhieuId : this.vloaiphieu_id
      })
      console.log(this.DSDeNghi , " - DSDeNghi")
      if(this.DSDeNghi.length == 0){
        this.DSChiTiet = []
        this.DSFile = []
        this.Setbutton(3)
        this.txtNhanVienGiao = ""
        this.txtDonViGiao = ""
        this.txtMa = ""
        this.mota = ""
        this.Enabled.tsbtnPhieuDN = false
      }
    },
    
    async gridViewDeNghi_FocusedRowChanged(row){
      console.log(row , " = row Đề Nghị")
      if(row == {}){
        this.DSChiTiet = []
        this.DSFile = []
        this.Setbutton(3)
        this.txtNhanVienGiao = ""
        this.txtDonViGiao = ""
        this.txtMa = ""
        this.mota = ""
        this.Enabled.tsbtnPhieuDN = false
      }else{

        this.vma_denghi = ""
        this.vloaiphieu_id_chon = row.LOAIPHIEU_ID == null  || row.LOAIPHIEU_ID == "" ? 0 :row.LOAIPHIEU_ID
        if(this.vloaiphieu_id_chon == this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_CHUYENKHO
            || this.vloaiphieu_id_chon == this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_XUATKHO
            || this.vloaiphieu_id_chon == this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_MUALE
        ){
          this.VisibleColGridDanhSach = false // visible DONGIA,THANHTIEN, SERIAL, MA_TBI , MA_TB
          this.vdenghi_id =  'DENGHI_ID' in row ?  row.DENGHI_ID : 0
          this.vma_denghi = row.MA_DN
          this.Enabled.tsbtnPhieuDN = true
          this.vquyettoan_id = 0
        }else{
          this.vdenghi_id = 0
          this.VisibleColGridDanhSach = true // visible DONGIA,THANHTIEN, SERIAL, MA_TBI , MA_TB
          this.vquyettoan_id = 'QUYETTOAN_ID' in row ?  row.QUYETTOAN_ID : 0
          this.Enabled.tsbtnPhieuDN = false
        }
        this.vphieu_id = row.PHIEU_ID
        this.vloaiphieu_id = row.LOAIPHIEU_ID
        this.huonggiao_id = row.HUONGGIAO_ID
        this.mota = row.MOTA
       
        this.phieu_cha_id = row.PHIEU_CHA_ID == null ? 0 : row.PHIEU_CHA_ID

        this.txtNhanVienGiao = row.TEN_NV
        this.txtDonViGiao = row.TEN_DV
        this.ma_phieu = row.MA_PHIEU
        this.txtMa = row.MA_PHIEU
        this.Enabled.tsbtnTraPhieu = row.TTPH_ID == 1 || row.TTPH_ID == 4
        this.Enabled.tsbtnXemPhieu = this.vquyettoan_id != 0

        if(this.vloaiphieu_id == 10 || this.vloaiphieu_id == 11){
          this.Enabled.tsbtnHoanThanh = true
          this.Enabled.tsbtnGiaoPhieu = this.BOQUA_GIAOPHIEU_VT_TIEPTHEO ? false : this.Enabled.tsbtnGiaoPhieu
        }else{
          this.Enabled.tsbtnHoanThanh = false
          this.Enabled.tsbtnGiaoPhieu = true
        }

        var KT_BOQUA_GIAO_LANHDAO = await this.KT_BOQUA_GIAO_LANHDAO(this.vloaiphieu_id)
        if(KT_BOQUA_GIAO_LANHDAO){
          this.showGiaoPhieu = false
          this.Enabled.tsbtnGiaoPhieu = true
        }
     
        await this.LayDanhSachCTDeNghiQT()
       
        if(this.mota != null && this.mota != ""){
          this.DSFile = JSON.parse(this.mota)
        }else{
          this.DSFile = []
        }
      }
    },
    async onClickDownFile(value){
      if(value) {
        var checkFile = await this.checkFileExist({fileSource : value.url})
        if(!checkFile.data){
          this.$toast.error("File không còn tồn tại!");
          return
        }
        const data = await this.downloadFile(value.url);
        if(data) {
          const filePath= value.split('/');
          const linkSource = `data:application/octet-stream;base64,${data.data.body.data}`;
          const downloadLink = document.createElement("a");
          const fileName = filePath[filePath.length - 1];
          downloadLink.href = linkSource;
          downloadLink.download = fileName;    
          downloadLink.click();
        }
      }
    },
    async grvDanhSach_FocusedRowChanged(row){
      if(this.vloaiphieu_id == this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_CHUYENKHO
          || this.vloaiphieu_id == this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_XUATKHO
          || this.vloaiphieu_id == this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_MUALE
      ){
        return;
      }
      if(!row){
        this.hdkh_id = 0
        this.Enabled.tsbtnTraCtTb = false
        this.Enabled.tsbtnBBBG = false
      }else{
        this.hdkh_id = row.HDKH_ID == null || row.HDKH_ID == "" ? 0 :row.HDKH_ID
        this.Enabled.tsbtnTraCtTb = true
        this.Enabled.tsbtnBBBG = this.hdkh_id != 0
      }
    },
    async KT_BOQUA_GIAO_LANHDAO(vloaiphieu_id){
      var DSTS = await this.getDSThamSoMacDinhTheoMaTS({
        maTS: "QLDN_BOQUA_GIAO_LANHDAO"
      })
  
      if(DSTS.length > 0 && DSTS[0].tenTS == "1" && DSTS[0].macDinh != null){
        var DSLoaiPhieuMacDinh = DSTS[0].macDinh.toString().split(';');
        if(DSLoaiPhieuMacDinh.includes(vloaiphieu_id)){
          return true;
        }
      }
      return false;
    },
    Setbutton(kieu){
      if(kieu == 1){

      }else if(kieu == 2){

      }else if(kieu == 3){
        this.Enabled.tsbtnGiaoPhieu = !this.showGiaoPhieu ? this.showGiaoPhieu : false
        this.Enabled.tsbtnXemPhieu = false
        this.Enabled.tsbtnTraPhieu = false
        this.Enabled.tsbtnTraCtTb = false
        this.Enabled.tsbtnBBBG =  false
      }
    },
    async LayDanhSachCTDeNghiQT(){
      this.DSChiTiet = []
      if(this.vloaiphieu_id_chon == this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_CHUYENKHO
            || this.vloaiphieu_id_chon == this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_XUATKHO
            || this.vloaiphieu_id_chon == this.LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_MUALE
      ){
        this.DSChiTiet = await this.getDSDeNghiChungTu(this.vdenghi_id)
        console.log(this.DSChiTiet , " = DSChiTiet DeNghi")
      }else{
        this.DSChiTiet = await this.getDSDeNghiQuyetToanChungTu(this.vquyettoan_id)
        console.log(this.DSChiTiet , " = DSChiTiet ChungTu")
      }
      
    },
    async btnLayDanhSach_Click(){
      if(this.LoaiPhieuSelected){
        this.vloaiphieu_id = this.LoaiPhieuSelected.LOAIPHIEU_ID
      }
      await this.handleDSLoaiPhieu()
      // focus DSLoaiPhieu theo vloaiphieu_id
      ////////////////////////////////
      // await this.LayDanhSachDeNghiQT() // rem dòng này vì auto focus đã gọi
    },
    async tsbtnGiaoPhieu_Click(){
      if(this.DSDeNghi.length == 0 || this.huonggiao_id == 0){
        this.$toast.error("Không có dữ liệu giao phiếu!");
        return
      }
      var DSHuongGiaoTiepTheo = await this.getHuongGiaoTiepTheo(this.huonggiao_id)
      console.log(DSHuongGiaoTiepTheo , " = DSHuongGiaoTiepTheo")
      if(DSHuongGiaoTiepTheo.length > 0){
        let huonggiao_tieptheo_id = parseInt(DSHuongGiaoTiepTheo[0]["HUONGGIAO_ID"])
        console.log(huonggiao_tieptheo_id , " = huonggiao_tieptheo_id")
        var frm = this.$refs.frmGiaoPhieu
        frm.frmGiaoPhieuHG(huonggiao_tieptheo_id)
        frm.parentForm = "XacNhanDeNghi"
        frm.openDialog()
      }
    },
    async closeGiaoPhieu(){
      if(this.LoaiPhieuSelected){
        this.vloaiphieu_id = this.LoaiPhieuSelected.LOAIPHIEU_ID
      }
      await this.handleDSLoaiPhieu()
    },
    async tsbtnTraCtTb_Click(){
      try{
        if(this.LoaiPhieuSelected == {}){
          this.$toast.error("Chưa chọn loại phiếu!");
          return
        }
        if(this.DSChiTietChecked.length == 0){
          this.$toast.error("Bạn chưa chọn danh sách chi tiết trả");
          return
        }
        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Bạn thật sự muốn trả lại chi tiết thuê bao đã nhận không ?",
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
          } else {
            return;
          }
        });

        if (!isBoxConfirm) {
          return
        }
    
        var dsChiTiet = []
        this.DSChiTietChecked.forEach(e=>{
          dsChiTiet.push({
            PHIEU_ID: e.PHIEU_ID,
            PHIEUVT_ID: e.PHIEUVT_ID,
            HDTB_ID: e.HDTB_ID,
            BAOHONG_ID: e.BAOHONG_ID
          })
        })
        var ketQua = await  this.traPhieuChiTiet({
          phieuId: this.vphieu_id,
          dsChiTiet: JSON.stringify(dsChiTiet),
          lyDoTraId: 0,
          ndTra: ""
        })
        if(ketQua == "OK"){
          var frm = this.$refs.frmGiaoPhieu
          frm.frmGiaoPhieuLP(this.vloaiphieu_id,this.vphieu_id, this.vquyettoan_id, this.ma_phieu)

          await frm.TaoMoiFileKySo(this.phieu_cha_id) // gọi đến ketQuaTaoMoiKySo
          await this.LayDanhSachDeNghiQT()
        }else{
          this.$toast.error(ketQua)
        }  
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    ketQuaTaoMoiKySo: async function(val){
      if(val == "OK"){
        this.$toast.success("Đã trả phiếu thành công")
      }      
    },
    grvDanhSach_CheckedRow: function (dataItem) {      
      this.DSChiTietChecked = dataItem
    },
    async tsbtnHoanThanh_Click(){
      try{
        if(this.DSDeNghi.length == 0){
          this.$toast.error("Không có dữ liệu đề nghị")
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Bạn thật sự muốn hoàn thành phiếu không ?",
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
          } else {
            return;
          }
        });

        if (!isBoxConfirm) {
          return
        }

        var ketQua = await this.hoanThanhPhieu(this.vphieu_id)
        if(ketQua == "OK"){
          this.$toast.success("Hoàn thành phiếu thành công!")
          await this.LayDanhSachDeNghiQT()
        }else{
          this.$toast.error(ketQua)
        }
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async tsbtnBBBG_Click(){
      try{
        this.loading(true)
        const baseUrl = process.env.API;
          await this.axios({
            url:
              `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-bien-ban-nghiem-thu?extend=pdf`,
            method: "POST",
            responseType: "blob",
            data: {
              hdKhId: this.hdkh_id,         
            }
          }).then((response) => {
            previewPrint(response.data);
          });
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async tsbnMenuTongHop_Click(){
      try{
        this.loading(true)
        const baseUrl = process.env.API;
        var url = `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-de-nghi-thanh-qt-vt?extend=pdf&quyetToanId=${this.vquyettoan_id}` // vloaiphieu_id = 9
        if(this.vloaiphieu_id == 10){
          url = `${baseUrl}/web-qlvt/api/de-nghi-quyet-toan/bc-de-nghi-nhap-kho-qt?extend=pdf&quyetToanId=${this.vquyettoan_id}`
        }
        
        await this.axios({
          url: url,
          method: "POST",
          responseType: "blob",
        }).then((response) => {
          previewPrint(response.data);
        });
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    async tsbnMenuChiTiet(){
      try{
        this.loading(true)
        const baseUrl = process.env.API;
        var url = `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-tong-hop-qt-vt-theo-dv?extend=pdf&quyetToanId=${this.vquyettoan_id}` // vloaiphieu_id = 9
        if(this.vloaiphieu_id == 10){
          url = `${baseUrl}/web-qlvt/api/de-nghi-quyet-toan/bc-chi-tiet-thu-hoi-nhap-kho?extend=pdf&quyetToanId=${this.vquyettoan_id}`
        }
        
        await this.axios({
          url: url,
          method: "POST",
          responseType: "blob",
        }).then((response) => {
          previewPrint(response.data);
        });
        this.loading(false)
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },

    async tsbtnTraPhieu_Click(){  
      try{  
        if(this.DSDeNghi.length == 0){
          this.$toast.error("Không có dữ liệu trả phiếu!");
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Bạn thật sự muốn trả lại phiếu đã nhận không ?",
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
          } else {
            return;
          }
        });

        if (!isBoxConfirm) {
          return
        }
        
        var f = this.$refs.frmTraPhieuVT
        f.phieuId = this.vphieu_id
        f.openDialog()
      }catch(e){
        this.$toast.error(e.data.message);
      }finally{
        this.loading(false);
      }
    },
    XacNhan_TraPhieu: async function(KtThucHien){
      if(KtThucHien){
        if(this.LoaiPhieuSelected){
          this.vloaiphieu_id = this.LoaiPhieuSelected.LOAIPHIEU_ID
        }
        await this.handleDSLoaiPhieu()
      }
    },
   
    async tsbtnPhieuDN_Click(){
      if(this.vma_denghi == ""){
        this.$toast.error("Không tìm thấy mã đề nghị!");
        return
      }
      var f = this.$refs.frmChonPhieuDeNghi
      f.ma_phieu = this.vma_denghi
      f.loaiphieu_id = this.vloaiphieu_id
      await f.frmChonPhieuDeNghi_Load()
      this.$refs.popup_frmChonPhieuDeNghi.show()
    },
    closeChonPhieuDeNghi(){
      this.$refs.popup_frmChonPhieuDeNghi.hide()
    }
  },
  

  created: async function () {
    try{
      this.loading(true)
      this.frmXacNhanDeNghi_Load()
      this.loading(false)
    }catch(e){
      this.$toast.error(e.data.message);
    }finally{
      this.loading(false);
    }
  },
  provide: {
   
  },
};
</script>
