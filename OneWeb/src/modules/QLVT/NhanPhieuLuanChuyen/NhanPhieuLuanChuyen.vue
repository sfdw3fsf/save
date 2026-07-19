<template src='./NhanPhieuLuanChuyen.html'></template>
<style scoped src='./NhanPhieuLuanChuyen.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/duyetnhucaudangkyvattu";
import moment from "moment";
import Vue from "vue";
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import XLSX from "xlsx";
Vue.use(DatePickerPlugin)
export default {
  components: { breadcrumb },
  name: "NhanPhieuLuanChuyen",

  data() {
    return {
      

      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        formatMonth: "MM/yyyy",
        waterMark: "Chọn ngày",
      },
      params: {
        noiDung: "",
        maDN: "",
        nguoiLap: "",
        huongGiaoId: null,
        quyTrinhId: null,
        ngayYC: null,
        DenNgay: new Date(),
        TuNgay: new Date((new Date()).getTime() - 15*24*60*60*1000), //ngày yêu cầu từ   ,
        trangThaiId : 1,
        noiDungTra: "", 
      },
      Visible:{
        XuatKho: false,
        NhapKho: false,
        XacNhan: true,
        HoanThanh: true,
        TraPhieu: false
      },    
      parentForm: "",
      loaiChungTuId: null,  
      phieu_id : null,
      denghi_id : null,
      xacnhan : false,
      lenhchuyen_id : null,
      DSLuanChuyen : [],
      DSVatTu_DaDK: [],
      DSQuyTrinh : [],
      DSHuongGiao: [],
      DSTrangThai : [
        {
          trangThaiId : 1,
          trangThai: "Mới"
        },
        {
          trangThaiId : 4,
          trangThai: "Hoàn thành"
        },
      ]
    };
  },

  mounted: async function () {
   
  },
  watch: {
    
  },
  computed: {
    ...mapState("duyetnhucaudangkyvattu", statePropertyName),
    ...mapGetters("duyetnhucaudangkyvattu", getterName),

   
  },

  methods: {
    ...mapActions("duyetnhucaudangkyvattu", actionName),
    ...mapMutations("duyetnhucaudangkyvattu", mutationName),

    
    f_MonthToString: function (value, format = "YYYYMM") {
      return moment(value).format(format);
    },
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },

    async QuyTrinhSelected(){
      this.DSHuongGiao = await this.getDsQuyTrinh({
        nhanVienId: this.$auth.getNhanVienID(),
        kieu: 12,
        param1: this.params.quyTrinhId,
        param2: this.loaiChungTuId
      })
      if(this.DSHuongGiao.length > 0){
        this.params.huongGiaoId = this.DSHuongGiao[0].HUONGGIAO_ID        
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
    async HoanThanh(){
      try{
        if(this.denghi_id == null || this.phieu_id == null){
          this.$toast.error("Chưa chọn phiếu đề nghị!")
          return
        }
        let ketQua = await this.hoanThanhGiaoPhieuLuanChuyen({
          denghi_id: this.denghi_id,
          huonggiao_id: this.params.huongGiaoId,
          nhanvien_id: this.$auth.getNhanVienID(),
          phieu_id: this.phieu_id,
          quytrinh_id: this.params.quyTrinhId,
        })
        await this.loadDSLuanChuyen()
        this.$toast.success("Hoành thành thành công!")        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async grvDSLuanChuyenSelectRow(row){
      try{
        if(row){
          console.log(row , " = row")
          this.loading(true)         
          this.params.noiDung = row.NOIDUNG
          this.params.nguoiLap = row.NGUOI_CN
          this.params.maDN = row.MA_DN
          this.denghi_id = row.DENGHI_ID
          this.phieu_id = row.PHIEU_ID
          this.lenhchuyen_id = row.LENHCHUYEN_ID
          this.params.ngayYC = new Date(this.convertDateTime_DDMMYYYY_to_YYYYMMDD(row.NGAY_YC))
          this.DSVatTu_DaDK = await this.getDsChiTietLuanChuyen({
            kieu: row.KIEU,
            deNghiId: row.DENGHI_ID
          })         
        }else{
          this.Clear()
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    Clear(){
      this.denghi_id = null
      this.phieu_id = null
      this.params.noiDung = ""
      this.params.nguoiLap = ""
      this.params.maDN = ""
      this.lenhchuyen_id = null
      // this.params.DenNgay = new Date()
      // this.params.TuNgay = new Date((new Date()).getTime() - 15*24*60*60*1000) //ngày yêu cầu từ       
      this.DSVatTu_DaDK = []
    },
    async HuongGiaoSelected(){
      let HuongGiaoSelected = this.DSHuongGiao.filter(e=> e.HUONGGIAO_ID == this.params.huongGiaoId)
     
      this.Visible.XuatKho = HuongGiaoSelected.length > 0 ? HuongGiaoSelected[0].MA_HG == "LC_TINH_GIAO" : false
      this.Visible.NhapKho = HuongGiaoSelected.length > 0 ? HuongGiaoSelected[0].MA_HG == "LC_TINH_NHAN" : false
      await this.loadDSLuanChuyen()
    },
    async TrangThaiSelected(){
      this.Visible.HoanThanh = this.params.trangThaiId == 1
      this.Visible.XuatKho = this.params.trangThaiId == 1
      this.Visible.NhapKho = this.params.trangThaiId == 1
      this.Visible.XacNhan = this.params.trangThaiId == 1 && this.parentForm == "NhapXuat"
      this.Visible.TraPhieu = this.params.trangThaiId == 4
      await this.loadDSLuanChuyen()
    },
    async loadDSLuanChuyen(){
      this.DSLuanChuyen = await this.getDsLuanChuyen({
        quyTrinhId: this.params.quyTrinhId,
        huongGiaoId: this.params.huongGiaoId,
        maDN: this.params.maDN,
        tuNgay: this.f_DateToString(this.params.TuNgay),
        denNgay: this.f_DateToString(this.params.DenNgay),
        trangThaiId: this.params.trangThaiId
      })
      console.log(this.DSLuanChuyen , " = this.DSLuanChuyen")
    },
    
    XuatNhapKho(){
      let Link = this.$router.resolve({path: 'NhapXuatVT', query: { Tag: 1 }})
      window.open(Link.href, '_blank');
    },
    async XuatExcel(){
      try{
        if(this.denghi_id == null){
          this.$toast.error("Chưa chọn phiếu đề nghị!")
          return
        }
        let dataExcel = await this.layDSSerialExcel(this.denghi_id)

        if(dataExcel.length == 0){
          this.$toast.error("Không có dữ liệu xuất excel")
          return
        }
        let data1 = XLSX.utils.json_to_sheet(dataExcel);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data1, "Sheet1"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "fileSerialTinhGiao.xlsx");
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async XuatExcelChiTiet(){
      try{
        if(this.DSVatTu_DaDK.length == 0){
          this.$toast.error("Không có dữ liệu chi tiết vật tư luân chuyển!")
          return
        }
       
        let data1 = XLSX.utils.json_to_sheet(this.DSVatTu_DaDK);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data1, "Sheet1"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "fileChiTietTinhGiao.xlsx");
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async TraPhieu(){
      try{
        if(this.phieu_id == null || this.denghi_id == null){
          this.$toast.error("Chưa chọn đề nghị để trả phiếu!")
          return
        }
        this.$bvModal.show("popupNhapNDTra");      
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async XacNhanTraPhieu(){
      try{
        this.$bvModal.hide("popupNhapNDTra");
        
        let KetQua = await this.traPhieuLuanChuyen({
          quyTrinhId: this.params.quyTrinhId,
          huongGiaoId: this.params.huongGiaoId,
          deNghiId: this.denghi_id,
          phieuId: this.phieu_id,
          ndTra: this.params.noiDungTra
        })
        this.params.noiDungTra = ""
        await this.loadDSLuanChuyen()
        this.$toast.success("Trả phiếu thành công!")        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async LoadForm(){
      try{
        this.Visible.XacNhan = this.parentForm == "NhapXuat"
        this.DSQuyTrinh = await this.getDsQuyTrinh({
          nhanVienId: this.$auth.getNhanVienID(),
          kieu: 11,
          param1: "",
          param2: ""
        })
        console.log(this.DSQuyTrinh , " = this.DSQuyTrinh")
        if(this.DSQuyTrinh.length > 0){
          this.params.quyTrinhId = this.DSQuyTrinh[0].QUYTRINH_ID
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async TimKiem(){
      try{
        if(this.params.quyTrinhId == null || this.params.huongGiaoId == null){
          this.$toast.error("Bạn chưa chọn quy trình và hướng giao!")
          return
        }
        await this.loadDSLuanChuyen()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async XacNhan(){
      try{
        if(this.denghi_id == null || this.denghi_id == ""){
          this.$toast.error("Bạn chưa chọn đề nghị!")
          return
        }
        this.xacnhan = true
        this.$parent.$parent.CloseLuanChuyen()
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    }
    
  },
  created: async function () {
    try{
      await this.LoadForm()
    }catch(e){
      this.$toast.error(e.data.message)
    }finally{
      this.loading(false)
    }
  },
};
</script>
