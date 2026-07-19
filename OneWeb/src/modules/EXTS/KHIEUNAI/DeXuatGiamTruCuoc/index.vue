<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import API from "../api/DeXuatGiamTruCuocAPI"
import frmHoSoKhieuNai from "@/modules/complaint/SearchComplainDetail/popups/frmHoSoKhieuNai.vue";
import frmTraCuuTBKhieuNai from "@/modules/complaint/SearchComplainDetail/popups/frmTraCuuTBKhieuNai.vue"
import ChenNo from '@/modules/QLTN/GachNo/components/ChenNo.vue'
import moment from 'moment';
export default {
  data() {
    return {
      dichVu:{
        dataSource: [],
        ID: null,
        text: "",
      },
      loaiHinh:{
        dataSource: [],
        ID: null,
        text: "",
      },
      dieuChinhHD:{
        dataSource: [
          {
            id: 1,
            text: "Bù trừ vào hóa đơn tháng hoàn thành GQKN"
          },
          {
            id: 2,
            text: "Điều chỉnh hóa đơn"
          },
          {
            id: 3,
            text: "Thay thế hóa đơn"
          }
        ],
        ID: null,
        text: "",
      },

      xuLyDuLieu:{
        dataSource: [
          {
            id: 1,
            text: "Truy thu"
          },
          {
            id: 2,
            text: "Giảm trừ"
          },
          {
            id: 3,
            text: "Bù trừ"
          }
        ],
        ID: null,
        text: "",
      },
      maKN: "",
      khieuNaiID : null,
      phieuKNID : null,
      chuDeKN: "",
      noiDungKN: "",
      ngayKN: null,
      maKhachHang : "",
      maThueBao : "",
      tenKhachHang : "",
      maThanhToan : "",
      maHopDong : "",
      ngayLapHD : null,
      tenThanhToan : "",
      maSoThue : "",
      giaTriHD: 0,
      soHopDong: "",
      dsTienTrinh:[],
      dsThongTinGiamTru: [],
      dsPhieuMoi: [],
      dsPhieuDaGui: [],
      ketQuaXacMinh: "",
      TTHopDong:"",
      TTHoaDon:"",
      TTCuoc: "",
      noiDungDeXuat: "",
      Enabled:{
        NhapMoi: true,
      },
      lyDoTra: "",
      PhieuMoiSelected : {},
      ChenNo: { ma_tt: null, kycuoc: null, ma_tb: null },
      editSettings: { allowEditing: true, mode: "Batch" },
    }
  },
  components: {
    frmHoSoKhieuNai,
    frmTraCuuTBKhieuNai,
    ChenNo
  },
  mounted: async function () {
    let ma_kn = this.$route.query.MaKN != null ? this.$route.query.MaKN : ""
    // this.dichVu.dataSource = await this.getDsDichVu();
    this.dieuChinhHD.dataSource = await this.getDSLoaiPAGC();
    this.xuLyDuLieu.dataSource = await this.getDSKieuPAGC();
    console.log(ma_kn , " = ma_kn")

    if(ma_kn != ""){
      this.maKN = ma_kn
      this.maKN_keyPress();
    }else{
      this.dsPhieuMoi = await this.getDSKhieuNaiTheoTrangThai(16);
      this.dsPhieuDaGui = await this.getDSKhieuNaiTheoTrangThai(17);
    }

  },

  methods: {
    async btnChenNo_Click(){
      this.ChenNo.ma_tt = this.maThanhToan
      this.ChenNo.ma_tb = this.maThueBao
      this.$refs.refChenNo.openDialog()
    },
    async btnThemGiamTru_Click(){

      if(this.khieuNaiID == null){
        this.$toast.error("Không có thông tin khiếu nại!")
        return
      }

      if(this.maThueBao == null || this.maThueBao.trim() == ""){
        this.$toast.error("Chưa có mã thuê bao")
        return
      }
      let checkTaoMoi = this.dsThongTinGiamTru.filter(e=> e.TAO_MOI)
      if(checkTaoMoi.length){
        this.$toast.error("Đã có tạo mới giảm trừ chưa được lưu!")
        return
      }
      // let DSKyCuoc = await this.getDSKyCuoc(this.maThueBao)

      this.dsThongTinGiamTru.unshift({
        MA_TB: this.maThueBao,
        KY_GC: null,
        VAT: null,
        TEN_CHITIEU: null,
        THANG_GC: null,
        VAT_GC: null,
        TIEN_GC: null,
        MA_DOANHTHU: null,
        SO_KH: null,
        MA_TT: null,
        TIEN: null,
        SO_HOADON: null,
        TAO_MOI: true
      })
    },
    validateDate(date) {
      const regex = /^(0[1-9]|1[0-2])\/\d{4}$/; // Kiểm tra MM/YYYY
      return regex.test(date);
    },
    getNumberTime(date){ // MM/YYYY
      const [month, year] = date.split("/");
        // Chuyển đổi sang YYYYMM01
      return parseInt(`${year}${month}01`);
    },
    getCurrentMonth() {
      const now = new Date(); // Lấy ngày hiện tại
      const year = now.getFullYear(); // Lấy năm
      const month = String(now.getMonth() + 1).padStart(2, "0"); // Lấy tháng (thêm 0 nếu cần)
      return parseInt(`${year}${month}01`);; // Kết hợp thành YYYYMM01
    },
    validateChangeGrid(arg){

      if (arg.columnName == "KY_GC" ) {
        if(!this.validateDate(arg.value)){
          this.$toast.error("Ngày tháng không đúng định dạng MM/YYYY")
          return false
        }
      }else if(arg.columnName == "THANG_GC") {

        if(!this.validateDate(arg.value)){
          this.$toast.error("Ngày tháng không đúng định dạng MM/YYYY")
          return false
        }
        // Tách MM và YYYY
        if(this.getCurrentMonth() < this.getNumberTime(arg.value)){
          this.$toast.error("Tháng giảm cước không được lớn hơn tháng hiện tại")
          return false
        }

      }else if(arg.columnName == "TIEN_GC" || arg.columnName == "VAT") {

        if(arg.value < 0){
          this.$toast.error("Không được nhập giá trị âm!")
          return false
        }
      }
      return true
    },
    validateThemGiamTru(){

      if(this.khieuNaiID == null){
        this.$toast.error("Chưa có phiếu khiếu nại!")
        return false
      }

      let dataTaoMoi = this.dsThongTinGiamTru.filter(e=> e.TAO_MOI)
      if(dataTaoMoi.length == 0) {
        this.$toast.error("Chưa có dữ liệu cần thêm mới!")
        return false
      }

      if(dataTaoMoi[0].THANG_GC == null){
        this.$toast.error("Chưa nhập tháng giảm trừ!")
        return false
      }
      if(dataTaoMoi[0].KY_GC == null){
        this.$toast.error("Chưa nhập kỳ giảm trừ!")
        return false
      }
      if(dataTaoMoi[0].TIEN_GC == null || dataTaoMoi.TIEN_GC <= 0){
        this.$toast.error("Chưa nhập tiền giảm trừ!")
        return false
      }
      if(dataTaoMoi[0].VAT == null || dataTaoMoi.TIEN_GC < 0){
        this.$toast.error("Chưa nhập VAT!")
        return false
      }
      return dataTaoMoi[0]
    },
    async btnLuuGiamTru_Click(){
      let data = this.validateThemGiamTru()
      if(!data){
        return
      }
      console.log(data , " = data")
      let ketQua = await this.themThongTinGiamCuoc({
        khieuNaiID:this.khieuNaiID,
        thangGC: this.getNumberTime(data.THANG_GC),
        kyGC: this.getNumberTime(data.KY_GC),
        tienGC: data.TIEN_GC,
        vatGC: data.VAT_GC,
        maDoanhThu: data.MA_DOANHTHU,
        tenChiTieu: data.TEN_CHITIEU
      })
      this.$toast.success("Thêm thông tin giảm cước thành công!")
      this.btnLamMoi_Click()
    },
    async btnLamMoi_Click(){
      if(this.khieuNaiID == null){
        this.dsThongTinGiamTru = []
      }
      this.getGiamCuocKhieuNai(this.khieuNaiID)
    },
    editGridGiamCuoc(arg) {
      let gridGiamCuoc = this.$refs.refdsThongTinGiamTru;
      let changes = gridGiamCuoc.getBatchChanges();

      if (changes.changedRecords.length == 0) {
        return;
      }
      if(!this.validateChangeGrid(arg)){
        this.$refs.refdsThongTinGiamTru.grid.refreshColumns()
        return
      }
      if(changes.changedRecords[0].TIEN_GC != null && changes.changedRecords[0].TIEN_GC > 0 &&
        changes.changedRecords[0].VAT != null && changes.changedRecords[0].VAT
      ){
        changes.changedRecords[0].VAT_GC = parseInt(changes.changedRecords[0].TIEN_GC) * parseInt(changes.changedRecords[0].VAT) / 100
        changes.changedRecords[0].TIEN = changes.changedRecords[0].TIEN_GC + changes.changedRecords[0].VAT_GC
      }
      gridGiamCuoc.batchUpdate(changes);
      this.$refs.refdsThongTinGiamTru.grid.refreshColumns();
    },
    async GhiLai(){
      try{
        this.loading(true)
        if(this.khieuNaiID == null){
          this.$toast.error("Không có thông tin khiếu nại!")
          return
        }
        // if(this.Enabled.NhapMoi) { // Update
        //   if(this.phieuKNID == null){
        //     this.$toast.error("Không có thông tin phiếu khiếu nại!")
        //     return
        //   }
        // }
        if(this.ketQuaXacMinh == null || this.ketQuaXacMinh.trim() == null){
          this.$toast.error("Chưa điền kết quả xác minh!")
          return
        }

        if(this.dieuChinhHD.ID == null){
          this.$toast.error("Chưa chọn điều chỉnh hợp đồng!")
          return
        }

        if(this.xuLyDuLieu.ID == null){
          this.$toast.error("Chưa chọn xử lý dữ liệu!")
          return
        }

        if(this.noiDungDeXuat == null || this.noiDungDeXuat.trim() == ""){
          this.$toast.error("Chưa điền nội dung đề xuất!")
          return
        }
        let ketQua = await this.taoMoiPhieuGiamTruCuoc({
          khieuNaiID: this.khieuNaiID,
          phieuKNID: -1,
          ketQua: this.ketQuaXacMinh,
          TTHopDong: this.TTHopDong,
          TTHoaDon: this.TTHoaDon,
          TTCuoc: this.TTCuoc,
          dchdID: this.dieuChinhHD.ID,
          xldlID: this.xuLyDuLieu.ID,
          noiDung: this.noiDungDeXuat,
        })
        this.$toast.success("Thêm mới thành công!")
        // if(!this.Enabled.NhapMoi){ // Nhập mới
        //   let ketQua = await this.taoMoiPhieuGiamTruCuoc({
        //     khieuNaiID: this.khieuNaiID,
        //     phieuKNID: -1,
        //     ketQua: this.ketQuaXacMinh,
        //     TTHopDong: this.TTHopDong,
        //     TTHoaDon: this.TTHoaDon,
        //     TTCuoc: this.TTCuoc,
        //     dchdID: this.dieuChinhHD.ID,
        //     xldlID: this.xuLyDuLieu.ID,
        //     noiDung: this.noiDungDeXuat,
        //   })
        //   this.$toast.success("Thêm mới thành công!")
        // }else{
        //   let ketQua = await this.capNhatPhieuGiamTruCuoc({
        //     khieuNaiID: this.khieuNaiID,
        //     phieuKNID: this.phieuKNID,
        //     ketQua: this.ketQuaXacMinh,
        //     TTHopDong: this.TTHopDong,
        //     TTHoaDon: this.TTHoaDon,
        //     TTCuoc: this.TTCuoc,
        //     dchdID: this.dieuChinhHD.ID,
        //     xldlID: this.xuLyDuLieu.ID,
        //     noiDung: this.noiDungDeXuat,
        //   })
        //   this.$toast.success("Cập nhật thành công!")
        // }

        await this.LayDuLieu()
      }catch(e){
        this.$toast.error(e.data.message_detail)
      }finally{
        this.Enabled.NhapMoi = true
        this.loading(false)
      }

    },
    async CapNhat(){
      try{
        this.loading(true)
        if(this.khieuNaiID == null){
          this.$toast.error("Không có thông tin khiếu nại!")
          return
        }

        if(this.phieuKNID == null){
          this.$toast.error("Không có thông tin phiếu khiếu nại!")
          return
        }

        if(this.ketQuaXacMinh == null || this.ketQuaXacMinh.trim() == null){
          this.$toast.error("Chưa điền kết quả xác minh!")
          return
        }

        if(this.dieuChinhHD.ID == null){
          this.$toast.error("Chưa chọn điều chỉnh hợp đồng!")
          return
        }

        if(this.xuLyDuLieu.ID == null){
          this.$toast.error("Chưa chọn xử lý dữ liệu!")
          return
        }

        if(this.noiDungDeXuat == null || this.noiDungDeXuat.trim() == ""){
          this.$toast.error("Chưa điền nội dung đề xuất!")
          return
        }
        let ketQua = await this.capNhatPhieuGiamTruCuoc({
          khieuNaiID: this.khieuNaiID,
          phieuKNID: this.phieuKNID,
          ketQua: this.ketQuaXacMinh,
          TTHopDong: this.TTHopDong,
          TTHoaDon: this.TTHoaDon,
          TTCuoc: this.TTCuoc,
          dchdID: this.dieuChinhHD.ID,
          xldlID: this.xuLyDuLieu.ID,
          noiDung: this.noiDungDeXuat,
        })
        this.$toast.success("Cập nhật thành công!")


        await this.LayDuLieu()
      }catch(e){
        this.$toast.error(e.data.message_detail)
      }finally{
        this.Enabled.NhapMoi = true
        this.loading(false)
      }

    },
    async TraCuuKhieuNai(){
      try {
        this.loading(true)

        // this.$refs.frmHoSoKhieuNai.khieunai_id = this.phieuKNID;
        this.$refs.frmTraCuuTBKhieuNai.show();
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async HoSo(){
      try {
        this.loading(true)
        if (this.khieuNaiID === null) return;
        this.$refs.frmHoSoKhieuNai.khieunai_id = this.khieuNaiID;
        this.$refs.frmHoSoKhieuNai.show();
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async Huy(){
      this.Enabled.NhapMoi = true
      this.clearAll()
      await this.LayDuLieu()
    },
    async XoaPhieu(){
      try{
        // if(this.dsTienTrinh.length > 1){
        //   this.$toast.error("Phiếu đã trả lại không thể xóa, hãy khóa phiếu!")
        //   return
        // }
        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Bạn có muốn xóa phiếu không!", {
          title: "Thông báo",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          isBoxConfirm = value
        });
        if (!isBoxConfirm) {
          return;
        }
        let ketQua = await this.xoaPhieuGiamTruCuoc({
          khieuNaiID: this.khieuNaiID,
          phieuKNID :  this.phieuKNID
        })
        this.dsPhieuMoi = await this.getDSKhieuNaiTheoTrangThai(16);
        this.$toast.success("Xóa phiếu thành công!")
        await this.LayDuLieu()
      }catch(e){
        this.$toast.error(e.data.message_detail)
      }finally{
        this.loading(false)
      }

    },
    async KhoaPhieu(){
      this.lyDoTra = ""
      if(this.phieuKNID == null){
        this.$toast.error("Chưa chọn phiếu để trả!")
        return
      }
      if(this.dsTienTrinh.length == 1){
        this.$toast.error("Phiếu mới không thể khóa!")
        return
      }
      this.$bvModal.show("popupLyDoTra");

    },
    async XacNhanKhoaPhieu() {
      try {
        this.$bvModal.hide("popupLyDoTra");
        let ketQua = await this.khoaPhieuGiamTruCuoc({
          khieuNaiID: this.khieuNaiID,
          phieuKNID :  this.phieuKNID,
          lyDoTra: this.lyDoTra
        })
        this.$toast.success("Khóa phiếu thành công!")
      }catch(e){
      this.$toast.error(e.data.message_detail)
      }finally{
        this.loading(false)
      }
    },
    async maKN_keyPress() {
      // await this.LayKetQuaXacMinh({
      //   khieuNaiID: 10411,
      //   phieuKNID: 1,
      // })
      await this.getThongTinTheoMaKN(this.maKN);
    },
    ChangedPhieuMoi: async function (item) {
      console.log(item , " = item")
      this.PhieuMoiSelected = item
      this.phieuKNID = item.PHIEUKN_ID
      this.maKN = item.MA_KN
      await this.getThongTinTheoMaKN(item.MA_KN)
      await this.LayKetQuaXacMinh({
        khieuNaiID: item.KHIEUNAI_ID,
        phieuKNID: item.PHIEUKN_ID,
      })
      this.ketQuaXacMinh = item.ND_XACMINH
      this.noiDungDeXuat = item.ND_GIAO
    },
    async LayDuLieu(){
      try{
        this.loading(true)
        this.dsPhieuMoi = await this.getDSKhieuNaiTheoTrangThai(16);
        if(this.dsPhieuMoi.length == 0){
          this.clearAll()
        }
        this.phieuDaGui = await this.getDSKhieuNaiTheoTrangThai(17);
      }catch(e){
        this.$toast.error(e.data.message_detail)
      }finally{
        this.loading(false)
      }
    },
    async NhapMoi(){
      this.Enabled.NhapMoi = false
      this.maKN = ""
      this.clearAll()
      this.dsTienTrinh = []
      this.dsThongTinGiamTru = []

    },
    f_StringToDateTime(value,format='DD/MM/YYYY HH:mm:ss'){
      return moment(value,format).toDate();
    },
    clearKetQuaXacMinh(){
      this.ketQuaXacMinh = ""
      this.TTHopDong = ""
      this.TTHoaDon = ""
      this.TTCuoc = ""
      this.noiDungDeXuat = ""
      this.dieuChinhHD.ID = null
      this.xuLyDuLieu.ID = null
    },
    clearThongTinKhieuNai(){
      this.dichVu.text = "";
      this.loaiHinh.text = "";
      this.chuDeKN = "";
      this.noiDungKN = "";
      this.ngayKN = null;
    },
    clearThongTinKhachHang(){
      this.maKhachHang = ""
      this.maThueBao = ""
      this.tenKhachHang = ""
      this.maThanhToan = ""
      this.maHopDong = ""
      this.ngayLapHD = null
      this.tenThanhToan = ""
      this.maSoThue = ""
    },
    clearAll(){
      this.clearKetQuaXacMinh();
      this.clearThongTinKhieuNai();
      this.clearThongTinKhachHang();
      // this.dsTienTrinh = []
      // this.dsThongTinGiamTru = []
      // this.dsPhieuMoi = []
      // this.dsPhieuDaGui = []
    },
    async GiaoPhieu(){
      try {
        if(this.PhieuMoiSelected == ""){
          this.$toast.error("Chưa chọn phiếu đề giao!")
          return
        }
        if(this.PhieuMoiSelected.KHIEUNAI_ID == "" || this.PhieuMoiSelected.KHIEUNAI_ID == null){
          this.$toast.error("Không tìm thấy thông tin khiếu nại!")
          return
        }
        if(this.PhieuMoiSelected.PHIEUKN_ID == "" || this.PhieuMoiSelected.PHIEUKN_ID == null){
          this.$toast.error("Không tìm thấy phiếu khiếu nại!")
          return
        }
        if(this.PhieuMoiSelected.TTKN_ID == "" || this.PhieuMoiSelected.TTKN_ID == null){
          this.$toast.error("Không tìm thấy trạng thái khiếu nại!")
          return
        }
        if(this.PhieuMoiSelected.TTKN_ID == "" || this.PhieuMoiSelected.TTKN_ID == null){
          this.$toast.error("Không tìm thấy trạng thái khiếu nại!")
          return
        }
        if(this.noiDungDeXuat == "" || this.noiDungDeXuat == null){
          this.$toast.error("Chưa nhập nội dung đề xuất!")
          return
        }

        var isBoxConfirm = false;
        await this.$bvModal.msgBoxConfirm("Bạn có muốn gửi duyệt không!", {
          title: "Thông báo",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          isBoxConfirm = value
        });
        if (!isBoxConfirm) {
          return;
        }

        let ketQua = await this.giaoPhieuGiamTruCuoc({
          khieuNaiID : this.PhieuMoiSelected.KHIEUNAI_ID,
          phieuKNID : this.PhieuMoiSelected.PHIEUKN_ID,
          trangTKNID : this.PhieuMoiSelected.TTKN_ID,
          noiDung : this.noiDungDeXuat,
        });
        this.$toast.success("Giao phiếu thành công!")
        await this.LayDuLieu()
      }catch(e){

      }finally{
        this.loading(false);
      }
    },
    async getThongTinTheoMaKN(maKN){
      try {
        this.loading(true);
        this.clearAll();
        let dataKN = await this.getDSKhieuNaiTheoMaKN(maKN);
        if(dataKN.length == 0){
          this.$toast.error("Không tìm thấy mã khiếu nại!")
          this.clearThongTinKhieuNai()
          return
        }
        this.khieuNaiID = dataKN[0].KHIEUNAI_ID;
        this.dichVu.text = dataKN[0].DICHVU_VT;
        this.loaiHinh.text = dataKN[0].LOAIHINH_TB;
        this.chuDeKN = dataKN[0].TENCHUDE;
        this.noiDungKN = dataKN[0].NOI_DUNG;
        this.ngayKN = this.f_StringToDateTime(dataKN[0].NGAY_KN, "YYYY-MM-DD HH:mm:ss");
        this.getThongTinKhachHang(dataKN[0].MA_TB)
        this.getTienTrinhKhieuNai(dataKN[0].KHIEUNAI_ID)
        this.getGiamCuocKhieuNai(dataKN[0].KHIEUNAI_ID)

      }catch(e){

      }finally{
        this.loading(false);
      }
    },

    async getThongTinKhachHang(pMaTB) {
      let data = await this.getThongTinKhachHangTHeoMaTB(pMaTB);
      if(data.length == 0){
        this.$toast.error("Không tìm dữ liệu khách hàng!")
        return
      }

      this.maKhachHang = data[0].MA_KH
      this.maThueBao = data[0].MA_TB
      this.tenKhachHang = data[0].TEN_KH
      this.maThanhToan = data[0].MA_TT
      this.maHopDong = data[0].MA_HD
      this.ngayLapHD = data[0].NGAYLAP_HD
      this.tenThanhToan = data[0].TEN_TT
      this.maSoThue = data[0].MST_TT

    },
    getDsDichVu: async function () {
      const rs = await API.getDSDichVuVT(this.axios);
      let result = [];
      if (rs.data.data.length != 0)
        result = rs.data.data;
      return result;
    },
    getDSKhieuNaiTheoMaKN: async function (maKN) {
      const rs = await API.getDSKhieuNaiTheoMaKN(this.axios,maKN);
      let result = [];
      if (rs.data.data.length != 0)
        result = rs.data.data;
      return result;
    },

    async getThongTinKhachHangTHeoMaTB(pMaTB) {
      const rs = await API.getDanhBa(this.axios, pMaTB);
      let result = [];
      if (rs.data.data.length != 0)
        result = rs.data.data;
      return result;
    },

    getDsLoaiHinhTB: async function (dichvuvt_id) {
      const rs = await API.getDSLoaiHinhTB(this.axios, dichvuvt_id);
      let result = [];
      if (rs.data.data.length != 0)
        result = rs.data.data;
      return result;
    },
    async getTienTrinhKhieuNai(khieuNaiID) {
        this.dsTienTrinh = []
        let data = await API.getTienTrinhKhieuNai(this.axios,khieuNaiID);
        if (data.data.data.length != 0) {
          this.dsTienTrinh = data.data.data;
        }
    },
    async getGiamCuocKhieuNai(khieuNaiID) {
      this.dsThongTinGiamTru = []
      let data = await API.getGiamCuocKhieuNai(this.axios, khieuNaiID);
      if (data.data.data.length != 0) {
        this.dsThongTinGiamTru = data.data.data;
      }
    },
    async getDSKhieuNaiTheoTrangThai(tTKNID) {
      const data = await API.getDSKhieuNaiTheoTrangThai(this.axios, tTKNID);
      let rs = [];
      if (data.data.data.length != 0) {
        rs = data.data.data;
      }
      return rs;
    },
    async giaoPhieuGiamTruCuoc(datagd) {

      const data = await API.giaoPhieuGiamTruCuoc(this.axios, datagd);
      let rs = [];
      if (data.data.data.length != 0) {
        rs = data.data.data;
      }
      return rs;

    },
    getDSLoaiPAGC: async function () {
      const rs = await API.getLoaiPAGC(this.axios);
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      return result;
    },
    getDSKieuPAGC: async function () {
      const rs = await API.getKieuPAGC(this.axios);
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      return result;
    },
    async taoMoiPhieuGiamTruCuoc(data){
      const rs = await API.ghiLai(this.axios,data);
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      return result;
    },
    async capNhatPhieuGiamTruCuoc(data){
      const rs = await API.capNhat(this.axios,data);
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      return result;
    },
    async xoaPhieuGiamTruCuoc(data){
      const rs = await API.xoa(this.axios,data);
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      return result;
    },
    async khoaPhieuGiamTruCuoc(data){
      const rs = await API.khoaPhieu(this.axios,data);
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      return result;
    },
    async getDSKyCuoc(maTB){
      const rs = await API.getDSKyCuoc(this.axios,maTB);
      console.log(rs , " = rs")
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      return result;
    },
    async themThongTinGiamCuoc(data){
      const rs = await API.themThongTinGiamCuoc(this.axios,data);
      console.log(rs , " = rs")
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      return result;
    },
    async LayKetQuaXacMinh(data){
      this.clearKetQuaXacMinh()
      const rs = await API.getThongTinKetQua(this.axios,data);
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      console.log(result , " = result")
      if(result.length > 0){
        this.ketQuaXacMinh = result[0].KETQUA
        // this.TTHopDong = result[0].TT_HOPDONG
        // this.TTHoaDon = result[0].TT_HOADON
        // this.TTCuoc = result[0].TT_CUOC
        this.dieuChinhHD.ID = parseInt(result[0].DCHD_ID)
        this.xuLyDuLieu.ID = parseInt(result[0].XLDL_ID)
      }
    },
  },
  watch: {
    // "dichVu.ID": async function (newVal, oldVal) {
    //   this.loaiHinh.dataSource = await this.getDsLoaiHinhTB(newVal);
    // }
  }

};
</script>
