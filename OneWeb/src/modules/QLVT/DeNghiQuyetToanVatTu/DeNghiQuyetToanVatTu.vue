<template src='./DeNghiQuyetToanVatTu.html'></template>
<style scoped src='./DeNghiQuyetToanVatTu.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import { TreeViewPlugin } from "@syncfusion/ej2-vue-navigations";
import xlsx from "xlsx";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/denghiquyettoanvattu";
import moment from "moment";
import Vue from "vue";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import { currency } from "@/filters/currency";
import { Query, Predicate } from "@syncfusion/ej2-data";
import { DropDownListPlugin,MultiSelectPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns';
import {previewPrint} from "../../../utils/util";
import frmGiaoPhieu from "../frmGiaoPhieu/frmGiaoPhieu.vue";

MultiSelect.Inject(CheckBoxSelection)
Vue.use(DatePickerPlugin,DropDownListPlugin,MultiSelectPlugin);

Vue.use(TreeViewPlugin);

export default {
  components: {
    breadcrumb,
    frmGiaoPhieu
  },
  name: "DeNghiQuyetToanVatTu",

  data() {
    return {
     
      dateConfig: {
        default: new Date(),
        formatDay: "dd/MM/yyyy",
        waterMark: "Chọn ngày",
      },
      isBoxTieuChi: true,
      classSpanTieuChi : "icon fa fa-angle-up",
      
      thongTinNguoiDung: {
        nguoiCN: this.$root.token.getUserName(), //this.$auth.MaNguoiDung
        donViId: this.$root.token.getDonViID(), // 882
        phanVungId: this.$root.token.getPhanVungID(),
        nhanVienId: this.$root.token.getNhanVienID(),
        nguoiDungId: this.$root.token.getNguoiDungID(),
        donViDLId : this.$root.context.user.getProperty('donvi_dl_id')
      },
      params: {
        ngayYC: new Date(),
        tuNgay: new Date(),
        denNgay: new Date(),
        maPhieu: "",
        ghiChu:"",
        hinhThucId : "0",
        dichVuId: 0,
        nhanVienId : "0",
        loaiPhieuId: 9,
        trangThaiId : ["1"],
        kieuLapDatId: 0,
        khoId: "0",
        lyDoCapId: [],
        loaiHinhId : 0,
        chuaGiao : true,
        theoNgayNT: true,
        theoNgayCap:false,
        theoNgayGiao:false,
        isQuyetToan:true,
        txtChiTietInPhieu: "Chi tiết vật tư quyết toán",
        txtTongHopInPhieu: "Tổng hợp đề nghị quyết toán"
      },
      vquyettoan_id : 0,
      DSHinhThuc: [
        {
          NHOM_ID: "0",
          TEN_NHOM: "Tất cả"
        },
        {
          NHOM_ID: "1",
          TEN_NHOM: "Thi công"
        },
        {
          NHOM_ID: "2",
          TEN_NHOM: "Báo hỏng"
        },
        {
          NHOM_ID: "3",
          TEN_NHOM: "Cải tạo/bảo dưỡng PTM"
        },        
      ],
      DSLoaiPhieu: [
        {
          PhieuID: 9,
          TenPhieu: "Đề nghị quyết toán"
        },
        {
          PhieuID: 10,
          TenPhieu: "Đề nghị thu hồi"
        },
        {
          PhieuID: 11,
          TenPhieu: "Đề nghị thu hồi từ công trình"
        },
      ],
      DSTrangThai: [
          {
            TRANGTHAI_ID: "1",
            TRANGTHAI: "Chưa quyết toán"
          },         
      ],    
      dataSource: {
        DSDichVu : [],
        DSNhanVien: [],
        DSKieuLapDat: [],
        DSKho: [],
        DSLyDoCap: [],
        DSLoaiHinh : [],
        DSThueBao: [],
        DSDeNghi:[],
        DSChiTietVatTu: []
      },
      Enabled:{
        nhapMoi: true,
        ghiLai: true,
        xoa:true,
        huyBo:true,
        taoDuLieuCT:true,
        giaoPhieu:true
      },
      fieldsCheckBox: {
        trangThai: { text: "TRANGTHAI", value: "TRANGTHAI_ID" },
        lyDoCap: { text: "mdNew", value: "mucDichId" },
      },
     
      
      onFilteringDichVu: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("tenDVVT", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
      
      onFilteringKieuLapDat: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("tenKieuLd", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
  
      onFilteringNhanVien: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("TEN_NEW", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },

      onFilteringKho: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("TEN_K_NEW", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },

      onFilteringLoaiHinh: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("loaiHinhTB", 'contains', e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
    };
  },

  mounted: async function () {
    
  },
  watch: {
    
  },
  computed: {
    ...mapState("denghiquyettoanvattu", statePropertyName),
    ...mapGetters("denghiquyettoanvattu", getterName),
    
  },
  methods: {
    ...mapActions("denghiquyettoanvattu", actionName),
    ...mapMutations("denghiquyettoanvattu", mutationName),

    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
    },
    f_convertDateTime_DDMMYYYY_to_YYYYMMDD(datetime) {
      datetime = datetime.split(" ");
      var thisDate = datetime[0].split("/");
      var newDate = [thisDate[2], thisDate[1], thisDate[0]].join("/");
      return newDate;
    },
    hiddenBoxTieuChi(){
      this.isBoxTieuChi =  !this.isBoxTieuChi
      this.classSpanTieuChi = this.isBoxTieuChi ? "icon fa fa-angle-up" :  "icon fa fa-angle-down"
    },
    onChangeTheoNgay(kieu){
      if(kieu == "NT"){
        this.params.theoNgayNT = true
        this.params.theoNgayCap = false
        this.params.theoNgayGiao = false
      }else if(kieu == "CAP"){
        this.params.theoNgayNT = false
        this.params.theoNgayCap = true
        this.params.theoNgayGiao = false
      }else if(kieu == "GIAO"){
        this.params.theoNgayNT = false
        this.params.theoNgayCap = false
        this.params.theoNgayGiao = true
      }
    },
    async NAP_COMBOBOX(){
      this.dataSource.DSDichVu =  await this.getDSDichVu()
       // load danh sách nhân viên
      this.dataSource.DSNhanVien = await this.getDSNhanVien({
        donViId:  this.thongTinNguoiDung.donViId,
        nhanVienId : this.thongTinNguoiDung.nhanVienId
      })
      if(this.dataSource.DSNhanVien.length > 0){
        this.dataSource.DSNhanVien.unshift({
          CHUCDANH: "",
          DIACHI_NV: "",
          DONVI_ID: "",
          GIOITINH: "",
          MA_NV: "",
          NHANVIEN_ID: "0",
          TEN_DV: "",
          TEN_NEW: "Tất cả nhân viên",
          TEN_NV: ""
        })
      }
      // Load Danh sách kiểu lắp đặt
      this.dataSource.DSKieuLapDat = await this.getDSKieuLapDat()

      await this.getDSDeNghi()
      //load DS Lý do cấp
      await this.getDSHinhThuc()
     
    },

    async onChangeNhanVien(){
      this.loading(true)
      if(this.params.nhanVienId == 0){
        this.dataSource.DSKho = [
          {
            KHO_ID: "0",
            MA_KHO: "",
            TEN_KHO: "",
            TEN_K_NEW: "Tất cả kho"
          }
        ]
      }else{
        this.dataSource.DSKho = await this.getDSKhoTheoNV({
          donViId:  this.thongTinNguoiDung.donViId,
          nhanVienId : this.thongTinNguoiDung.nhanVienId
        })
        this.dataSource.DSKho.unshift({
          KHO_ID: "0",
          MA_KHO: "",
          TEN_KHO: "",
          TEN_K_NEW: "Tất cả kho"
        })
        this.params.khoId = "0"
      }
      this.loading(false)
    },
    async onChangeDichVu(){
      this.loading(true)
      this.dataSource.DSLoaiHinh = await this.getDSLoaiHinhThueBao(this.params.dichVuId)
      this.loading(false)
    },
    async onChangeHinhThuc(){
      this.loading(true)
      await this.getDSHinhThuc()
      this.loading(false)
    },
    async getDSHinhThuc(){
      if(this.params.hinhThucId == 0){
        this.dataSource.DSLyDoCap = await this.getDSLyDoCap({nhomMdIdArr : [5,6,22,23]})
      }else if(this.params.hinhThucId == 1){
        this.dataSource.DSLyDoCap = await this.getDSLyDoCap({nhomMdIdArr : [6]})
      }else if(this.params.hinhThucId == 2){
        this.dataSource.DSLyDoCap = await this.getDSLyDoCap({nhomMdIdArr : [5]})
      }else if(this.params.hinhThucId == 3){
        this.dataSource.DSLyDoCap = await this.getDSLyDoCap({nhomMdIdArr : [22,23]})
      }
      // remove phần tử TẤT CẢ ra khỏi DS
      this.dataSource.DSLyDoCap.pop();
    },
    onChangeLoaiPhieu(){
      if(this.params.loaiPhieuId == 9) {
        this.params.txtChiTietInPhieu = "Chi tiết vật tư quyết toán"
        this.params.txtTongHopInPhieu = "Tổng hợp đề nghị quyết toán"
      }else if(this.params.loaiPhieuId == 10) {
        this.params.txtChiTietInPhieu = "Chi tiết thu hồi"
        this.params.txtTongHopInPhieu = "Đề nghị nhập kho"
      }
    },
    Clear(){
      this.params.ghiChu = ""
      this.params.maPhieu = ""
      this.params.ngayYC =  new Date()
      this.params.tuNgay =  new Date()
      this.params.denNgay =  new Date()
      this.dataSource.DSChiTietVatTu = []
    },
    SetButton(kieu){
      this.Enabled.nhapMoi = false
      this.Enabled.ghiLai = false
      this.Enabled.xoa = false
      this.Enabled.huyBo = false
      this.Enabled.taoDuLieuCT = false
      this.Enabled.giaoPhieu = false
      if(kieu == -1){// Bắt đầu
        this.Enabled.ghiLai = true
        this.Enabled.huyBo = true
      }else if(kieu == 0){// Bắt đầu
        this.Enabled.nhapMoi = true
        this.Clear()
      }else if(kieu == 1){// Thêm mới
        this.Enabled.ghiLai = true
        this.Enabled.huyBo = true
        this.Clear()
      }else if(kieu == 2){// Hủy
        this.Enabled.nhapMoi = true
        this.Enabled.xoa = true
        this.Clear()
      }else if(kieu == 3){
        this.Enabled.nhapMoi = true
        this.Enabled.ghiLai = true
        this.Enabled.xoa = true
        this.Enabled.huyBo = true
        this.Enabled.taoDuLieuCT = true
        this.Enabled.giaoPhieu = true
        this.dataSource.DSChiTietVatTu = []
      }
    },
    async onClickLayChiTiet(){
      this.loading(true)
      if(this.params.lyDoCapId.length == 0){
        this.$toast.error("Hãy chọn ít nhất 1 lý do");
        this.loading(false)
        return
      }
    
      if(this.params.trangThaiId.length == 0){
        this.$toast.error("Hãy chọn ít nhất 1 trạng thái");
        this.loading(false)
        return
      }

      var nv_all = this.thongTinNguoiDung.nhanVienId
      var all = 0
      if(this.params.nhanVienId != "0"){
        nv_all = this.params.nhanVienId
        all = 1
      }

      var ttph = "1"
      if(this.params.trangThaiId.includes("4")){
        ttph = "1,4"
      }

      var tt_giao = "1"
      
      var timNgay = "1"
      
      if(this.params.theoNgayCap){
        timNgay = "0"
      }

      if(this.params.theoNgayGiao){
        timNgay = "2"
      }
      
      var dsLyDo = "0" // tất cả
      if(this.params.lyDoCapId.length != this.dataSource.DSLyDoCap.length){
        dsLyDo =this.params.lyDoCapId.toString()
      }
      if(this.params.loaiPhieuId == 9){
        
        // this.params.isQuyetToan = true
        this.dataSource.DSChiTietVatTu = await this.getDSBaoCaoQuyetToan({
          quyetToanId: this.vquyettoan_id,
          nhomId: this.params.hinhThucId,
          dichVuVtId: this.params.dichVuId,
          loaiHdId: this.params.loaiHinhId,
          tuNgay: this.f_DateToString(this.params.tuNgay),
          denNgay: this.f_DateToString(this.params.denNgay),
          nhanVienId: nv_all,
          timNgay: timNgay,
          timKhoAll: this.params.khoId,
          timNvAll: all,
          donViId: this.thongTinNguoiDung.donViId,
          lyDo: dsLyDo,
          tt: this.params.trangThaiId.toString(),
          ttPh: ttph,
          kieuLdId: this.params.kieuLapDatId,
          ttGiao: tt_giao
        })

      }else if( this.params.loaiPhieuId == 10){
        // this.params.isQuyetToan = false

        this.dataSource.DSChiTietVatTu = await this.getDSBaoCaoThuHoi({
          quyetToanId: this.vquyettoan_id,
          nhomId: this.params.hinhThucId,
          dichVuVtId: this.params.dichVuId,
          loaiHdId: this.params.loaiHinhId,
          tuNgay: this.f_DateToString(this.params.tuNgay),
          denNgay: this.f_DateToString(this.params.denNgay),
          nhanVienId: nv_all,
          timNgay: timNgay,
          timKhoAll: this.params.khoId,
          timNvAll: all,
          donViId: this.thongTinNguoiDung.donViId,
          lyDo: dsLyDo,
          tt: this.params.trangThaiId.toString(),
          ttPh: ttph,
          kieuLdId: this.params.kieuLapDatId,
          ttGiao: tt_giao
        })

      }
      console.log(this.dataSource.DSChiTietVatTu , " = DSChiTietVatTu")
      if(this.dataSource.DSChiTietVatTu.length == 0){
        this.$toast.error("Không có dữ liệu!")        
      }else{
        this.Enabled.taoDuLieuCT = true
      }
      this.loading(false)
    },
    async onClickNhapMoi(){
      this.SetButton(1)
      this.$refs.txtMaPhieu.focus()
    },
    async onClickGhiLai(){
      var error_message = ""
      if(this.params.loaiPhieuId == 0 || this.params.loaiPhieuId == null){
        error_message += "Hãy chọn loại phiếu! \n"
        
      }
      var currentDate = new Date()
      if(this.params.ngayYC > currentDate){
        error_message += "Ngày yêu cầu quyết toán không được lớn hơn ngày hiện tại!\n"        
      }
      if(this.params.tuNgay > this.params.ngayYC){
        error_message += "Từ ngày không được lớn hơn ngày yêu cầu quyết toán!\n"  
        
      }
      if(this.params.denNgay > this.params.ngayYC){
        error_message += "Từ ngày không được lớn hơn ngày yêu cầu quyết toán!\n"  
        
      }
      if(this.params.tuNgay > this.params.denNgay){
        error_message += "Từ ngày không được lớn hơn đến ngày!\n"          
      }
      if(error_message != ""){
        this.$toast.error(error_message);
        return
      }
      this.loading(true)
      var ketQua = await this.ghiLaiDeNghiQuyetToan({
        loaiPhieuId: this.params.loaiPhieuId,
        quyetToanId: this.vquyettoan_id,
        flagThem: this.Enabled.nhapMoi ? 0:1, // Enabled.nhapMoi ==  false là nhập mới, flagThem = 1
        tuNgay: this.f_DateToString(this.params.tuNgay),
        denNgay: this.f_DateToString(this.params.denNgay),
        ngayYc: this.f_DateToString(this.params.ngayYC),
        ghiChu: this.params.ghiChu,
        maQt: this.params.maPhieu
      })
      if(ketQua == 1){
        this.$toast.success("Cập nhật dữ liệu thành công!");
      }else{
        this.$toast.error(ketQua);
        this.loading(false)
        return
      }

      await this.getDSDeNghi()
      this.loading(false)
    },
    async onClickHuy(){
      this.SetButton(0)
    },
    async onClickXoa(){
      var confirm = await this.callBoxConfirmXoa()
      if(confirm){
        this.loading(true)
        await this.xoaDeNghiQuyetToan(this.vquyettoan_id)
        await this.getDSDeNghi()
        this.loading(false)
      }
    },
    async callBoxConfirmXoa(){
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn có muốn xóa dữ liệu không ?", {
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

      return isBoxConfirm
    },
    async InPhieuTongHop(){
      this.loading(true)
      // Loại Phiếu  = 9
      const baseUrl = process.env.API;
      var url = `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-de-nghi-thanh-qt-vt?extend=pdf&quyetToanId=${this.vquyettoan_id}` 
      if(this.params.loaiPhieuId == 10){
        var url = `${baseUrl}/web-qlvt/api/de-nghi-quyet-toan/bc-de-nghi-nhap-kho-qt?extend=pdf&quyetToanId=${this.vquyettoan_id}`
      }      
      await this.axios({
        url:url,
        method: "POST",
        responseType: "blob",         
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false)
    },
    async InPhieuChiTiet(){
      this.loading(true)
      // Loại Phiếu  = 9
      const baseUrl = process.env.API;
      var url = `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-tong-hop-qt-vt-theo-dv?extend=pdf&quyetToanId=${this.vquyettoan_id}` 
      if(this.params.loaiPhieuId == 10){
        var url = `${baseUrl}/web-qlvt/api/de-nghi-quyet-toan/bc-chi-tiet-thu-hoi-nhap-kho?extend=pdf&quyetToanId=${this.vquyettoan_id}`
      }      
      await this.axios({
        url:url,
        method: "POST",
        responseType: "blob",         
      }).then((response) => {
        previewPrint(response.data);
      });
      this.loading(false)
    },
    async onClickTaoDuLieu(){
      
      if(this.vquyettoan_id == 0){
        this.$toast.error("Bạn chưa có đề nghị nào!");
        return
      }
      if(this.dataSource.DSChiTietVatTu.length == 0){
        this.$toast.error("Bạn chưa lấy ds chi tiết!");
        return
      }
      this.loading(true)      
      
      var ketQua = await this.capNhatQuyetToanChungTu({
        loaiPhieuId: this.params.loaiPhieuId,
        quyetToanId: this.vquyettoan_id,
        dsChiTiet: JSON.stringify(this.dataSource.DSChiTietVatTu)
      })
      if(ketQua == "OK"){
        this.$toast.success("Tạo dữ liệu chi tiết thành công!");
        await this.getDSDeNghi()
      }else{
        this.$toast.error("Có lỗi xảy ra trong quá trình thực hiện: ", ketQua);
      }
      this.loading(false)
    },
    async getDSDeNghi(){
      var DSDeNghi = await this.getDSDeNghiQuyetToan({
        trangThaiQt: 1,
        loaiPhieuId: 0
      })
      DSDeNghi.forEach(e=>{
        e.TUNGAY = e.TUNGAY.replaceAll("-", "/")
        e.DENNGAY = e.DENNGAY.replaceAll("-", "/")
        e.NGAY_YC = e.NGAY_YC.replaceAll("-", "/")
      })
      this.dataSource.DSDeNghi = DSDeNghi
      if(this.dataSource.DSDeNghi.length == 0){
        this.Clear()
      }
      console.log(this.dataSource.DSDeNghi , " = DSDeNghi")
    },
    async onClickExcel(){
      if(this.dataSource.DSChiTietVatTu.length == 0){
        this.$toast.error("Không có dữ liệu để xuất Excel!");
        return
      }
      this.loading(true)
      var NameFile = "QLVT - Danh sách chi tiết vật tư.xls"
      var DSChiTietVatTu = xlsx.utils.json_to_sheet(this.dataSource.DSChiTietVatTu);  
      var wb = xlsx.utils.book_new();
      xlsx.utils.book_append_sheet(wb, DSChiTietVatTu, "Sheet1");
      xlsx.writeFile(wb, NameFile);
      this.loading(false)
    },
    async onClickGiaoPhieu(){
      var kiemTra = await this.kiemTraQuyetToanChungTu(this.vquyettoan_id)      
      if(kiemTra != 1){
        this.$toast.error("Bạn chưa tạo danh sách chi tiết cho mã phiếu " + this.params.maPhieu + " !");
        return
      }
      var DSQuyTrinhHG = await this.layQuyTrinhHuongGiao(this.params.loaiPhieuId)
      if(DSQuyTrinhHG.length > 0){
        var DSHuongGiaoTiepTheo = await this.layHuongGiaoTiepTheo(DSQuyTrinhHG[0].HUONGGIAO_ID)
        if(DSHuongGiaoTiepTheo.length > 0){
          var huongGiaoTiepTheoId = DSHuongGiaoTiepTheo[0].HUONGGIAO_ID
          var frm = this.$refs.frmGiaoPhieu
          frm.frmGiaoPhieuHG(huongGiaoTiepTheoId)
          frm.parentForm = "DeNghiQuyetToanVatTu"
          frm.openDialog()
        }
      }
    },
    async closeGiaoPhieu(){
      await this.getDSDeNghi();
    },
    async DSDeNghiSelected(row) { 
      if(row){
        this.vquyettoan_id = row.QUYETTOAN_ID    
        this.params.maPhieu = row.MA_QT
        this.params.ghiChu = row.GHICHU      
        this.params.tuNgay = new Date(this.f_convertDateTime_DDMMYYYY_to_YYYYMMDD(row.TUNGAY))     
        this.params.denNgay = new Date(this.f_convertDateTime_DDMMYYYY_to_YYYYMMDD(row.DENNGAY))      
        this.params.ngayYC = new Date(this.f_convertDateTime_DDMMYYYY_to_YYYYMMDD(row.NGAY_YC))  
        this.params.loaiPhieuId = row.LOAIPHIEU_ID
        this.SetButton(3)
      }
    },
  },
  
  created: async function () {
    this.loading(true)
    await this.NAP_COMBOBOX()
    this.loading(false)
  },
};
</script>
