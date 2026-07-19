<template src="./EditPaymentInvoice.html"></template>
<style src="./EditPaymentInvoice.scss"></style>
<script>
import XLSX from 'xlsx'
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import api from './api.js'
import DatePicker from 'vue2-datepicker'
import { DocSo } from "./DocSo"
export default {
  components: {
    XLSX,
    moment,
    breadcrumb,
    DatePicker
  },
  name: 'EditPaymentInvoice',
  data() {
    return {
      loading: false,
      nhanhvien_id: this.$root.token.getNhanVienID(),
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      showTimePanel: false,
      inputTuNgay: {
        checkbox: false,
        val: moment(new Date()).format('DD/MM/YYYY'),
        Enabled: true
      },
      inputDenNgay: {
        val: moment(new Date()).format('DD/MM/YYYY'),
        Enabled: true
      },
      sysdate: {
         val: moment(new Date()).format('DD/MM/YYYY')
      },
      phieuTT:"",
      hoaDon_DT: 0,
      infoPhieu: {
        maGD: "",
        loaiHD: "",
        tenKH: "",
        diaChiKH: "",
        diaChiCT: "",
        nguoiDD: "",
        dienThoai: "",
        taiKhoan: "",
        maST: "",
        ngayYC:moment(new Date()).format('DD/MM/YYYY'),
        tongTien: 0,
        bangChu: "Không đồng",
        ghiChu: "",
      },
      infoTT: {
        hinhThucTra: {
          checked: 0,
          list:[]
        },
        kenhThu:{
          checked: 0,
          list:[]
        },
        nguoiBL:"",
        trangThai:{
          checked: 0,
          list:[]
        },
        nganHang: {
          checkbox: true,
          checked: 0,
          list:[]
        },
        ngayNH: moment(new Date()).format('DD/MM/YYYY'),
        chungTu: ""
      },
      infoHD: {
        loaiHD: {
          checkbox: true,
          checked: 0,
          list:[]
        },
        ngayHD: moment(new Date()).format('DD/MM/YYYY'),
        seri:{
                  checked: 0,
                  list:[]
                },
        hoaDon:"",
        phieuThu:"",
        loaiTien:{
          checked: 0,
          list:[]
        },
        tyGia: 1,
        ngayTT: moment(new Date()).format('DD/MM/YYYY'),
        thuNgan:{
          checked: 0,
          list:[]
        },
        thongTinPT:"",
      },
      header: {
        title: 'SỬA HOÁ ĐƠN THANH TOÁN',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      headerGridbox: [
        { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowSorting: true, textAlign: "center", width: 120},
        { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, allowSorting: true, customAttributes: { class: "font-weight-bold" }},
        { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: true },
        { fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: true },
        { fieldName: 'diachi_kh', headerText: 'Địa chỉ khách hàng', allowFiltering: true, allowSorting: true },
        { fieldName: 'tongtien', headerText: 'Tổng tiền', allowFiltering: true, allowSorting: true, textAlign: "center"}
      ],
      gridBox: {
        data: [],
        selected: null
      },
      checkedList:{
        trangthai: 0,
        loaihoadon: 0,
        hinhthuctra: 0,
        loaitien:0,
        seri:0,
      },
      selectedList:{
        loaihoadon_id: 0,
        loaihd_id: "",
        seri_id: 0,
        soseri: "",
        trangthai: 0,
        ngaytt:"",
        ngay_cn:""
      },
      enabled:false,
      daxuathoadon: false,
      kt_sua: true,
      nhanvien_id: 0,
      thungan_hd_id: 0
    }
  },
  mounted() {
     this.load_form()
  },
  created() {
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  methods: {
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async btnSearch_Click(){
      this.loading = true
      this.clear()
      let res = []
      if(this.inputTuNgay.checkbox){
         res = await this.lay_danh_sach_phieu_TT(this.inputTuNgay.val, this.inputDenNgay.val, this.infoPhieu.maGD,0,0,"0")
      }else{
         if(this.infoPhieu.maGD){
          res = await this.lay_danh_sach_phieu_TT("0","0",this.infoPhieu.maGD, 0, 0, "0")
         }else{
           this.$toast.error('Bạn chưa nhập hóa đơn cần tìm kiếm !')
         }
      }
      if(res.length> 0){
        let ds = []
        res.forEach((element, index) => {
            element.tongtien = element.tongtien.toLocaleString()
            ds.push(element)
        })
        this.gridBox.data = ds
      }else{
        if(this.infoPhieu.maGD){
          this.$toast.error("Không tìm thấy phiếu thanh toán của " + this.infoPhieu.maGD);
        }
        this.gridBox.data = res
      }
      this.loading = false
    },
    btnUpdate_Click(){
      let validate = this.checkValidation();
      if(validate){
          this.$bvModal
            .msgBoxConfirm('Bạn có muốn sửa hóa đơn này hay không?', {
              title: 'Thông báo',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Không đồng ý'
            })
            .then(async (yes) => {
              if (yes) {
                 // capnhat
                this.seData()
                this.update(this.infoHD.loaiHD.checked,this.infoHD.seri.checked,this.infoHD.hoaDon,this.infoPhieu.maGD, this.phieuTT)
              }
            })
      }
    },
    clear(){
      this.infoPhieu.tenKH =""
      this.infoPhieu.diaChiKH =""
      this.infoPhieu.nguoiDD = ""
      this.infoPhieu.dienThoai = ""
      this.infoPhieu.ngayYC = this.sysdate.val
      this.infoPhieu.loaiHD =""
      this.infoPhieu.taiKhoan = ""
      this.infoPhieu.maST = ""
      this.infoPhieu.diaChiCT = ""
      this.infoTT.nganHang.checkbox = true
      this.infoTT.nganHang.checked = 0
      this.phieuTT = []
      this.infoTT.hinhThucTra.checked = this.checkedList.hinhthuctra
      this.infoTT.kenhThu.checked = 0
      this.infoTT.nguoiBL = ""
      this.infoTT.trangThai.checked = this.checkedList.trangthai
      this.infoPhieu.ghiChu = ""
      this.infoTT.ngayNH= this.sysdate.val
      this.infoHD.loaiHD.checkbox = true
      this.infoHD.loaiHD.checked = this.checkedList.loaihoadon
      this.infoHD.seri.checked = this.checkedList.seri
      this.infoHD.hoaDon = ""
      this.infoHD.ngayHD= this.sysdate.val
      this.infoHD.thuNgan.checked = 0
      this.infoHD.thuNgan.list = []
      this.infoHD.phieuThu = ""
      this.infoHD.ngayTT= this.sysdate.val
      this.infoHD.thongTinPT = ""
      this.hoaDon_DT = 0
      var tongtientra = 0;
      this.infoPhieu.tongTien = tongtientra.toLocaleString()
      this.infoPhieu.bangChu = "Không đồng"
    },
    async checkInvoice(){
        let res = await this.lay_danh_sach_phieu_TT("0","0","", parseInt(this.infoHD.loaiHD.checked), parseInt(this.infoHD.seri.checked), this.infoHD.hoaDon)
        if(res.length > 0 ){
          if (res[0].ma_gd != this.infoPhieu.maGD.trim())
            {
                let pr={ma_gd: res[0].ma_gd}
                let ds = await this.item_selected_change(pr)
                if(ds){
                    var dsPhieu_tt = []
                    if(ds.DS_PHIEUTT){
                      dsPhieu_tt = ds.DS_PHIEUTT
                    }
                    console.log(dsPhieu_tt[0].NGUOI_CN)
                    let nguoi_cn = dsPhieu_tt[0].NGUOI_CN
                    let ngay_cn = moment(new Date(dsPhieu_tt[0].NGAY_CN)).format('DD/MM/YYYY')
                    let donvi_cn = dsPhieu_tt[0].DONVI_ID
                    let data = await this.fnmap(donvi_cn)
                    this.$toast.error("Hóa đơn đã được sử dụng. Người sử dụng : '" + nguoi_cn + "', đơn vị '" + data + "', ngày '" + ngay_cn + "'");
                    this.$refs.inthoadon.focus();
                    return false;
                }
            }
        }
    },
  checkValidation(){
      try
            {
               this.checkInvoice()
               if (this.kt_sua == false)
                {
                    this.$toast.error("Hồ sơ chưa được xuất hóa đơn hoặc chưa thu tiền. Bạn không thể sửa hóa đơn !");
                    return false;
                }
                if (!this.infoPhieu.tenKH)
                {
                    this.$toast.error("Tên khách hàng không được phép để trống !");
                    return false;
                }
                if (!this.infoPhieu.maGD)
                {
                    this.$toast.error("Mã giao dịch không được phép để trống !");
                    return false;
                }
                if (!this.infoPhieu.diaChiKH)
                {
                    this.$toast.error("Địa chỉ khách hàng không được phép để trống !");
                    return false;
                }
                if (this.infoHD.loaiHD.checked == 0)
                {
                    this.$toast.error("Bạn chưa chọn Loại hóa đơn !");
                    return false;
                }
                if (this.infoHD.seri.checked== 0)
                {
                    this.$toast.error("Bạn chưa chọn Seri !");
                    return false;
                }
                if (!this.infoHD.hoaDon && !this.infoHD.phieuThu)
                {
                    this.$toast.error("Bạn chưa nhập hóa đơn hoặc phiếu thu !");
                    return false;
                }
                if (this.infoHD.hoaDon && this.infoHD.phieuThu)
                {
                    this.$toast.error("Bạn không được nhập cả Hóa đơn và Phiếu thu !");
                    return false;
                }
                if (this.infoHD.thuNgan.checked == 0)
                {
                    this.$toast.error("Chưa thiết lập thu ngân. Liên hệ admin để xử lý !");
                    return false;
                }
                //Loại hợp đồng: 32 - THAYDOI_DATCOC
                if (this.selectedList.loaihd_id != 32)
                {
                    if (parseInt(this.infoPhieu.tongTien) < 0)
                    {
                        this.$toast.error("Tổng tiền trả nhỏ hơn 0. Bạn hãy kiểm tra lại !");
                        return false;
                    }
                }
                if(parseInt(this.selectedList.trangthai) == 1){
                   if (this.selectedList.ngaytt != "")
                    {
                      let ngaytt = moment(this.selectedList.ngaytt,'DD/MM/YYYY').format('MM/YYYY')
                      let ngayht = moment(this.$auth.getNgayCapNhat(),'DD/MM/YYYY').format('MM/YYYY')
                      let startDate = moment(ngaytt, 'MM/YYYY');
                      let endDate = moment(ngayht, 'MM/YYYY');
                      let monthDiff = endDate.diff(startDate, 'months');
                      if(monthDiff > 0)
                      {
                          this.$toast.error("Hợp đồng đã thanh toán, tháng thanh toán " + this.selectedList.ngaytt + " nhỏ hơn tháng hiện tại. Bạn không được sửa !");
                          return false;
                      }
                    }
                    else
                    {
                      let ngaycn = moment(this.selectedList.ngay_cn,'DD/MM/YYYY').format('MM/YYYY')
                      let ngayht = moment(this.$auth.getNgayCapNhat(),'DD/MM/YYYY').format('MM/YYYY')
                      let startDate = moment(ngaycn, 'MM/YYYY');
                      let endDate = moment(ngayht, 'MM/YYYY');
                      let monthDiff = endDate.diff(startDate, 'months');
                        if(monthDiff> 0)
                        {
                             this.$toast.error("Hợp đồng đã thanh toán, tháng cập nhật " + this.selectedList.ngay_cn + " nhỏ hơn tháng hiện tại. Bạn không được sửa !");
                            return false;
                        }
                    }
                }
                if (parseInt(this.infoTT.kenhThu.checked) == 4 || parseInt(this.infoTT.kenhThu.checked) == 5)
                {
                    if (!this.infoTT.nguoiBL)
                    {
                        this.$toast.error("Bạn phải nhập tên người bảo lãnh khi chọn kênh thu : Bảo lãnh hoặc Chuyển khoản");
                        this.$refs.ngbl.focus()
                        return false;
                    }
                }
                if (this.daxuathoadon) // Đã xuất hóa đơn điện tử thì ko cho sửa thông tin loại hóa đơn, seri, số hóa đơn
                {
                    if (parseInt(this.selectedList.loaihoadon_id) != parseInt(this.infoHD.loaiHD.checked)
                        || parseInt(this.selectedList.seri_id) != parseInt(this.infoHD.seri.checked)
                        || this.selectedList.soseri != this.infoHD.hoaDon)
                    {
                        this.$toast.error("Bạn không được sửa thông tin loại hóa đơn, seri, hóa đơn của hóa đơn điện tử !");
                        return false;
                    }
                }
                if (this.getSelectedHTTra() == "Ủy nhiệm chi" || this.getSelectedHTTra() == "Ủy nhiệm thu")
                {
                    if (!this.infoPhieu.taiKhoan)
                    {
                        this.$toast.error("Bạn chưa nhập Tài khoản !");
                        return false;
                    }
                    if (!this.infoPhieu.maST)
                    {
                        this.$toast.error("Bạn chưa nhập Mã số thuế !");
                        return false;
                    }
                }
                if(this.infoHD.thuNgan.checked == 0){
                   this.$toast.error("Chưa thiết lập thu ngân. Liên hệ admin để xử lý !");
                  return false;
                }
                if (this.thungan_hd_id != this.$root.token.getNhanVienID()
                    && this.nhanvien_id != this.$root.token.getNhanVienID())
                {
                    this.$toast.error("Bạn không được sửa hóa đơn của thu ngân khác !");
                    return false;
                }
                return true;
            }
            catch (error)
            {
                 this.$toast.error("Có lỗi : " + error);
                 return false;
            }
    },
    getSelectedHTTra(){
      let checkedText = ""
      let itemtchecked = this.infoTT.hinhThucTra.list.filter( item => {
        return item.id == this.infoTT.hinhThucTra.checked;
      })
      if(itemtchecked && itemtchecked.length > 0){
          checkedText = itemtchecked[0].text
      }
      return checkedText
    },
    seData(){
        this.phieuTT.TEN_KH = this.infoPhieu.tenKH
        this.phieuTT.DIACHI_KH = this.infoPhieu.diaChiKH
        this.phieuTT.MST = this.infoPhieu.maST
        this.phieuTT.STK = this.infoPhieu.taiKhoan
        this.phieuTT.SOSERI = this.infoHD.hoaDon
        this.phieuTT.SOPHIEU = this.infoHD.phieuThu
        this.phieuTT.HT_TRA_ID = this.infoTT.hinhThucTra.checked
        this.phieuTT.KENHTHU_ID = this.infoTT.kenhThu.checked
        this.phieuTT.NGUOI_BL = this.infoTT.nguoiBL
        this.phieuTT.TRANGTHAI =  this.infoTT.trangThai.checked
        this.phieuTT.GHICHU = this.infoPhieu.ghiChu
        this.phieuTT.NGAY_NH =  this.infoTT.ngayNH
        this.phieuTT.LOAIHOADON_ID = this.infoHD.loaiHD.checked
        this.phieuTT.SERI_ID =  this.infoHD.seri.checked
        this.phieuTT.NGAY_HD =  this.infoHD.ngayHD
        this.phieuTT.THUNGAN_HD_ID =  this.infoHD.thuNgan.checked
        this.phieuTT.NGAY_TT = this.infoHD.ngayTT
        var textSeri = this.infoHD.seri.list
          .filter(item => item.id == this.infoHD.seri.checked).map(el => {
            return el.text
          })
        this.phieuTT.SERI =  textSeri[0]
        this.phieuTT.NGUOI_CN = this.$root.token.getUserName()
        this.phieuTT.NGAY_CN = this.$auth.getNgayCapNhat()
        this.phieuTT.MAY_CN = "localhost";
    },
    async selectedItemsChangedGridbox(value){
        this.loading = true
          if(value && value.length>0){
          var item = value[0]
          this.infoPhieu.maGD = item.ma_gd
          this.nhanvien_id = item.nhanvien_id
          this.thungan_hd_id = item.thungan_hd_id
          let pr={ma_gd: this.infoPhieu.maGD}
          let ds = await this.item_selected_change(pr)
          if(ds){
              var dsHD_theo_maGD = []
              if(ds.DS_HOPDONG_THEO_MA_GD){
                dsHD_theo_maGD = ds.DS_HOPDONG_THEO_MA_GD
              }
              if(dsHD_theo_maGD.length>0){
                this.infoPhieu.tenKH =dsHD_theo_maGD[0].TEN_KH
                this.infoPhieu.diaChiKH =dsHD_theo_maGD[0].DIACHI_KH
                this.infoPhieu.nguoiDD =dsHD_theo_maGD[0].NGUOI_DD
                this.infoPhieu.dienThoai =dsHD_theo_maGD[0].SO_DT
                this.infoPhieu.ngayYC = moment(new Date(dsHD_theo_maGD[0].NGAY_YC)).format('DD/MM/YYYY')
                this.infoPhieu.loaiHD =dsHD_theo_maGD[0].TEN_LOAIHD
                this.selectedList.loaihd_id = dsHD_theo_maGD[0].LOAIHD_ID
              }
              var dsHDTT_theo_hdKH = []
              if(ds.DS_HOPDONG_TT_THEO_HDKH_ID){
                dsHDTT_theo_hdKH = ds.DS_HOPDONG_TT_THEO_HDKH_ID
              }
              if(dsHDTT_theo_hdKH.length>0){
                  this.infoPhieu.taiKhoan = dsHDTT_theo_hdKH[0].STK
                  this.infoPhieu.maST = dsHDTT_theo_hdKH[0].MST
                  this.infoPhieu.diaChiCT = dsHDTT_theo_hdKH[0].DIACHI_CT
                  if(dsHDTT_theo_hdKH[0].NGANHANG_ID != null && dsHDTT_theo_hdKH[0].NGANHANG_ID != ""){
                    this.infoTT.nganHang.checkbox = true
                    this.infoTT.nganHang.checked = dsHDTT_theo_hdKH[0].NGANHANG_ID
                  }else{
                    this.infoTT.nganHang.checkbox = false
                    this.infoTT.nganHang.checked = 0
                  }
              }
              var dsPhieu_tt = []
              if(ds.DS_PHIEUTT){
                dsPhieu_tt = ds.DS_PHIEUTT
              }
              var dsNhan_vien = []
              if(ds.DS_NHANVIEN_DV){
                   dsNhan_vien = ds.DS_NHANVIEN_DV
              }
              if(dsPhieu_tt.length>0){
                this.phieuTT = dsPhieu_tt[0]
                this.infoTT.hinhThucTra.checked = dsPhieu_tt[0].HT_TRA_ID
                this.infoTT.kenhThu.checked = dsPhieu_tt[0].KENHTHU_ID
                this.infoTT.nguoiBL = dsPhieu_tt[0].NGUOI_BL
                this.infoTT.trangThai.checked = dsPhieu_tt[0].TRANGTHAI
                this.selectedList.trangthai = dsPhieu_tt[0].TRANGTHAI
                this.selectedList.ngay_cn = moment(new Date(dsPhieu_tt[0].NGAY_CN)).format('DD/MM/YYYY')
                this.infoPhieu.maST = dsPhieu_tt[0].MST
                this.infoTT.chungTu = dsPhieu_tt[0].SO_CT
                this.infoPhieu.ghiChu = dsPhieu_tt[0].GHICHU
                this.enabled = true;
                await this.onChangeHTTra()
                // this.onChangeKenhThu()
                if(dsPhieu_tt[0].HDDT_FKEY != null && dsPhieu_tt[0].HDDT_FKEY != ""){
                  this.daxuathoadon = true;
                }
                if(dsPhieu_tt[0].NGANHANG_ID != null && dsPhieu_tt[0].NGANHANG_ID != ""){
                    this.infoTT.nganHang.checkbox = true
                    this.infoTT.nganHang.checked = dsPhieu_tt[0].NGANHANG_ID
                  }else{
                    this.infoTT.nganHang.checkbox = false
                    this.infoTT.nganHang.checked = 0
                  }
                if(dsPhieu_tt[0].NGAY_NH !=""){
                  this.infoTT.ngayNH= moment(new Date(dsPhieu_tt[0].NGAY_NH)).format('DD/MM/YYYY')
                }else{
                  this.infoTT.ngayNH= this.$auth.getNgayCapNhat()
                }
                if(dsPhieu_tt[0].LOAIHOADON_ID != null && dsPhieu_tt[0].LOAIHOADON_ID != ""){
                    this.infoHD.loaiHD.checkbox = true
                    this.infoHD.loaiHD.checked = dsPhieu_tt[0].LOAIHOADON_ID
                    this.selectedList.loaihoadon_id = dsPhieu_tt[0].LOAIHOADON_ID
                }else{
                  this.infoHD.loaiHD.checkbox = false
                  this.infoHD.loaiHD.checked = 0
                }
                if( dsPhieu_tt[0].SERI_ID != null && dsPhieu_tt[0].SERI_ID !=""){
                  this.infoHD.seri.checked = dsPhieu_tt[0].SERI_ID
                  this.selectedList.seri_id = dsPhieu_tt[0].SERI_ID
                }else{
                this.infoHD.seri.checked = 0
                }
                if(dsPhieu_tt[0].SOSERI != null && dsPhieu_tt[0].SOSERI !=""){
                  this.infoHD.hoaDon = dsPhieu_tt[0].SOSERI.toString()
                  this.selectedList.soseri = dsPhieu_tt[0].SOSERI.toString()
                }else{
                  this.infoHD.hoaDon = ""
                }
                if(dsPhieu_tt[0].NGAY_HD !=""){
                  this.infoHD.ngayHD= moment(new Date(dsPhieu_tt[0].NGAY_HD)).format('DD/MM/YYYY')
                }else{
                  this.infoHD.ngayHD= this.$auth.getNgayCapNhat()
                }
                if(dsPhieu_tt[0].THUNGAN_HD_ID !=""){
                  if(dsNhan_vien.length > 0){
                    this.infoHD.thuNgan.list = dsNhan_vien.map((x) => ({
                      id: x.NHANVIEN_ID,
                      text: x.TEN_NV
                    }))
                    this.infoHD.thuNgan.checked = dsNhan_vien[0].NHANVIEN_ID
                  }else{
                    this.infoHD.thuNgan.checked = 0
                    this.infoHD.thuNgan.list = []
                  }
                }else{
                  this.infoHD.thuNgan.checked = 0
                  this.infoHD.thuNgan.list = []
                }
                this.infoHD.phieuThu = dsPhieu_tt[0].SOPHIEU
                this.kt_sua = true
                if(!dsPhieu_tt[0].LOAIHOADON_ID|| this.infoTT.trangThai.checked == 0){
                    this.kt_sua = false;
                }
                console.log('dsPhieu_tt > kt_sua, LOAIHOADON_ID, trangThai',  this.kt_sua, dsPhieu_tt[0].LOAIHOADON_ID, this.infoTT.trangThai.checked)
                if(dsPhieu_tt[0].NGAY_TT !=""){
                  this.infoHD.ngayTT= moment(new Date(dsPhieu_tt[0].NGAY_TT)).format('DD/MM/YYYY')
                  this.infoHD.thongTinPT = dsPhieu_tt[0].THONGTIN_PT
                  this.selectedList.ngaytt = moment(new Date(dsPhieu_tt[0].NGAY_TT)).format('DD/MM/YYYY')
                  this.enabled = true
                }else{
                  this.infoHD.ngayTT= this.$auth.getNgayCapNhat()
                  this.infoHD.thongTinPT = ""
                  if (this.infoTT.trangThai.checked == 0)
                      {
                          this.enabled = true
                      }
                      else
                      {
                          this.enabled = false
                      }
                }
                if(dsPhieu_tt[0].HDDT_FKEY !="" && dsPhieu_tt[0].HDDT_FKEY != null){
                    this.hoaDon_DT = 1
                }else{
                  this.hoaDon_DT = 0
                }
              }
              var tongtientra = 0;
              var tongtien = 0;
              var tien = 0;
              var tien_km = 0;
              var dsTienHD_theo_hdKH = []
              if(ds.DS_TIENHD_TB_THEO_HDKH){
                 dsTienHD_theo_hdKH =ds.DS_TIENHD_TB_THEO_HDKH
              }
              if(dsTienHD_theo_hdKH.length>0){
                  dsTienHD_theo_hdKH.forEach((item,index ) => {
                    tien = parseFloat(item.TIEN_GOC);
                    tien_km = parseFloat(item.TIEN_KMAI);
                    tongtien += tien + tien_km;
                    tongtientra = tongtien;
                });
              }
              if (tongtientra < 0){
                tongtientra = Math.abs(tongtientra).toLocaleString()
                this.infoPhieu.tongTien = "-" + tongtientra
                this.infoPhieu.bangChu = "Âm " + DocSo(tongtientra);
              }
              else {
                this.infoPhieu.tongTien = tongtientra.toLocaleString()
                this.infoPhieu.bangChu = DocSo(tongtientra);
              }
          }
        }
      this.loading = false
    },
    disabledRange: function (date) {
      return date > new Date()
    },
    toggleTimePanel() {
      this.showTimePanel = !this.showTimePanel
    },
    handleOpenChange() {
      this.showTimePanel = false
    },
    // UR2.2.008_001
    load_form: async function () {
      this.loading = true
      try{
        let data = this.GetData(await api.form_load(this.axios, {}))
        var dattaObj = JSON.parse(data);
        // if (process.env.NODE_ENV == 'testing') {
          let ds_httra = await this.getDmHinhThucTra()
          if (data && data.length > 0) {
            this.infoTT.hinhThucTra.list = ds_httra.map((x) => ({
              id: x?.HT_TRA_ID || x.ht_tra_id,
              text: x?.HT_TRA || x.ht_tra
            }))
            this.checkedList.hinhthuctra = ds_httra[0]?.HT_TRA_ID || ds_httra[0]?.ht_tra_id || null
            this.infoTT.hinhThucTra.checked = this.checkedList.hinhthuctra

            let ds_kenhthu = await this.getDmKenhThu()
            if(ds_kenhthu && ds_kenhthu.length > 0){
              this.infoTT.kenhThu.list = ds_kenhthu.filter(e => e?.TRANGTHAI || e.trangthai == 1).map((x) => ({
                id: x?.KENHTHU_ID || x.kenhthu_id,
                text: x?.KENHTHU || x.kenhthu,
                trangthai: x?.TRANGTHAI || x?.trangthai || 1,
              }))
            }
          }
        // } else {
        //   if(dattaObj.DS_HINHTHUC_TRA && dattaObj.DS_HINHTHUC_TRA.length > 0){
        //     this.infoTT.hinhThucTra.list = dattaObj.DS_HINHTHUC_TRA.map((x) => ({
        //       id: x.HT_TRA_ID,
        //       text: x.HT_TRA
        //     }))
        //     this.checkedList.hinhthuctra = dattaObj.DS_HINHTHUC_TRA[0].HT_TRA_ID
        //     this.infoTT.hinhThucTra.checked = this.checkedList.hinhthuctra
        //   }
        //   if(dattaObj.DS_KENHTHU && dattaObj.DS_KENHTHU.length > 0){
        //     this.infoTT.kenhThu.list = dattaObj.DS_KENHTHU.map((x) => ({
        //       id: x.KENHTHU_ID,
        //       text: x.KENHTHU
        //     }))
        //   }
        // }
        
        if(dattaObj.DS_LOAITIEN && dattaObj.DS_LOAITIEN.length > 0){
           this.infoHD.loaiTien.list = dattaObj.DS_LOAITIEN.map((x) => ({
              id: x.LOAITIEN_ID,
              text: x.MA_LOAITIEN
            }))
             this.checkedList.loaitien = dattaObj.DS_LOAITIEN[0].LOAITIEN_ID
             this.infoHD.loaiTien.checked = this.checkedList.loaitien
        }
        if(dattaObj.DS_LOAI_HOADON && dattaObj.DS_LOAI_HOADON.length > 0){
           this.infoHD.loaiHD.list = dattaObj.DS_LOAI_HOADON.map((x) => ({
              id: x.LOAIHOADON_ID,
              text: x.TENTAT
            }))
            this.checkedList.loaihoadon = dattaObj.DS_LOAI_HOADON[0].LOAIHOADON_ID
            this.infoHD.loaiHD.checked = this.checkedList.loaihoadon
        }
        if(dattaObj.DS_NGANHANG_DV && dattaObj.DS_NGANHANG_DV.length > 0){
           console.log(dattaObj.DS_NGANHANG_DV)
           this.infoTT.nganHang.list = dattaObj.DS_NGANHANG_DV
          //  this.infoTT.nganHang.list  = dattaObj.DS_NGANHANG_DV.map((x) => ({
          //     value: x.NGANHANG_ID,
          //     text: x.TEN_NH
          //   }))
        }
        if(dattaObj.DS_SERI && dattaObj.DS_SERI.length > 0){
           this.infoHD.seri.list = dattaObj.DS_SERI.map((x) => ({
              id: x.SERI_ID,
              text: x.SERI
            }))
            this.checkedList.seri = dattaObj.DS_SERI[0].SERI_ID
            this.infoHD.seri.checked = this.checkedList.seri
        }
        if(dattaObj.DS_TRANGTHAI_TT && dattaObj.DS_TRANGTHAI_TT.length > 0){
           this.infoTT.trangThai.list = dattaObj.DS_TRANGTHAI_TT.map((x) => ({
              id: x.TRANGTHAI_ID,
              text: x.TEN_TT
            }))
            let itemtchecked = this.infoTT.trangThai.list.filter( item => {
              return item.text === "Đã thu tiền";
            })
            this.checkedList.trangthai =  itemtchecked[0].id
            this.infoTT.trangThai.checked = this.checkedList.trangthai
        }
      }catch (error) {
        this.$toast.error('Load dữ liệu lỗi' + error)
      }
       this.loading = false
    },
    getDmHinhThucTra: async function() {
      let data = []
      var input = { nghiepvu_id: 3 }
      try {
        data = this.GetData(await api.getDmHinhThucTraV3(this.axios, input))
        return data
      } catch (ex) {
        console.error(`Có lỗi lấy danh mục hình thức trả`, ex)
        return []
      }
    },
    onChangeHTTra: async function () {
      // if (process.env.NODE_ENV == 'testing') {
        let ds_kenhthu = await this.getDmKenhThu()
        if(ds_kenhthu && ds_kenhthu.length > 0){
          this.infoTT.kenhThu.list = ds_kenhthu.filter(e => e?.TRANGTHAI || e.trangthai == 1).map((x) => ({
            id: x?.KENHTHU_ID || x.kenhthu_id,
            text: x?.KENHTHU || x.kenhthu,
            trangthai: x?.TRANGTHAI || x?.trangthai || 1,
          }))
        }
      // }
    },
    getDmKenhThu: async function() {
      let data = []
      var input = { nghiepvu_id: 3, ht_tra_id: this.infoTT.hinhThucTra.checked }
      try {
        data = this.GetData(await api.getDmKenhThuV3(this.axios, input))
        return data
      } catch (ex) {
        console.error(`Có lỗi lấy danh mục kênh thu`, ex)
        return []
      }      
    },
    onChangeKenhThu: function () {
      // if (process.env.NODE_ENV == 'testing') {
        let itemtchecked = this.infoTT.kenhThu.list.find(e => Number(e.id) == Number(this.infoTT.kenhThu.checked))
        this.checkedList.trangthai =  itemtchecked?.trangthai || itemtchecked?.TRANGTHAI || 1
        this.infoTT.trangThai.checked = this.checkedList.trangthai
      // }
    },
    // UR2.2.008_002 - UR2.2.008_003
    lay_danh_sach_phieu_TT: async function (ngaybd, ngaykt, magd, loaihd,seri,hoadon) {
      var params = {
          vngay_bd: ngaybd,
          vngay_kt: ngaykt,
          vma_gd: magd,
          vloaihd_id: loaihd,
          vseri_id: seri,
          vhoadon: hoadon
      }
      try{
        let res = this.GetData(await api.lay_danh_sach(this.axios, params))
        console.log(res)
        if(!res){
          return []
        }
        return res
      }catch (error) {
        this.$toast.error('Load dữ liệu lỗi' + error)
      }
    },
    // UR2.2.008_004
    item_selected_change: async function(para){
      var params ={
        p_ds_para: JSON.stringify(para)
      }
      try{
      let res = this.GetData(await api.item_selected_change(this.axios, params))
      return JSON.parse(res.p_js_returnds)
      }catch (error) {
        this.$toast.error('lấy dữ liệu lỗi' + error)
      }
    },
    // UR2.2.008_004
    update: async function(loaihd_id,seri_id,soseri,ma_gd,js_phieutt_hd){
      var para ={
        capnhat:1,
        loaihd_id:loaihd_id,
        seri_id:seri_id,
        soseri:soseri,
        ma_gd:ma_gd,
        js_phieutt_hd:js_phieutt_hd
      }
      var params ={
        p_ds_para: JSON.stringify(para)
      }
      try{
        this.GetData(await api.update_data(this.axios, params))
        this.$toast.success('cập nhật thành công')
        await this.load_form()
        await this.btnSearch_Click()
      }catch (error) {
        this.$toast.error('cập nhật lỗi' + error)
      }
    },
    async fnmap(donvi_id){
       let data = this.GetData(await api.fn_tt_donvi(this.axios,
       {
           "param": donvi_id,
           "type": 2
        }
      ))
      return data
    }
  }
}
</script>
