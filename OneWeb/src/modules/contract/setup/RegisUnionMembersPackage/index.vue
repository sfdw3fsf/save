<template src='./index.html'></template>
<script>
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import DichVuVienThong from "@/const/enums/DichVuVienThong";
import TrangThaiHD from "@/const/enums/TrangThaiHD";
import LoaiHopDong from "@/const/enums/LoaiHopDong";
import api from "./api/index.js";
import moment from "moment";
import BssErrorMarker from '@/components/BssErrorMarker'
import { required, minValue, maxValue, maxLength } from 'vuelidate/lib/validators'

export default {
  components: { breadcrumb, gridview, moment, BssErrorMarker },
  name: "",
  validations: {
    thongTinCaNhan:
    {
      loaiGiayToSelected: {
        required
      }
    }
  },
  data() {
    return {
      header: {
        title: "Đăng ký gói đoàn viên",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Đăng ký gói đoàn viên",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      hasErrorNgaySinh: false,
      may_cn: "",
      nguoi_cn: "",
      ip_cn: "",
      p_json_hdkh: "",
      p_json_hdtb: "",
      p_json_hdtb_dd: "",
      lblNgayHH: "Ngày hết hạn gói cước:",
      dulieu: [],
      activeNhapMoi: false,
      activeHuyGoi: false,
      activeGhiLai: false,
      activeHoanThien: true,
      activeTaiAnh: false,
      activeGuiXacNhan: false,
      huonggiao_id: 5600,
      dsHDKH: [],
      dsHDTB: [],
      dsHDTB_DD: [],
      dsDiaChi: [],
      dsDiaChiHDTB: [],
      tinhkhac: 0,
      donvi_id: "",
      thuebao_id: "",
      doituong_id: "",
      doituong_id_neo: "",
      khachHang_id: "",
      hdkh_id: "",
      khlon_id: "",
      hdtb_id: 0,
      maGiaoDich: "",
      maHopDong: "",
      ma_kh: "",
      anh1: "Chọn ảnh 1",
      anh2: "Chọn ảnh 2",
      anh3: "Chọn ảnh 3",
      anh4: "Chọn ảnh 4",
      urlAnh1: "",
      urlAnh2: "",
      urlAnh3: "",
      urlAnh4: "",
      urlImg1: "",
      urlImg2: "",
      urlImg3: "",
      urlImg4: "",
      typeCurrrentImg: "",
      currentImg: "",
      ngayYeuCau: moment(new Date()).format("DD/MM/YYYY hh:mm:ss"),
      ngayLapHD: moment(new Date()).format("DD/MM/YYYY hh:mm:ss"),
      thongTinCaNhan: {
        soMay: "",
        tenKH: "",
        doiTuongSD: [],
        doiTuongSDSelected: "",
        ngaySinh: "",
        diaChi: "",
        loaiGiayTo: [],
        loaiGiayToSelected: "",
        soGiayTo: "",
        noiCap: [],
        noiCapSelected: "",
        ngayCap: "",
        loaiKH: [],
        loaiKHSelected: "",
        email: "",
        ngheNghiep: "",
        congTy: "",
        diaChiCongTy: "",
        gioiTinh: [],
        gioiTinhSelected: "",
        quocTich: [],
        quocTichSelected: "",
      },
      thongTinGoiCuoc: {
        doiTuongTB: [],
        doiTuongTBSelected: "",
        loaiXacNhan: [],
        loaiXacNhanSelected: "",
        maXacThuc: "",
        soXacNhan: "",
        ghiChu: "",
        donVi: "",
        donviArr: [],
        donViId: "",
        diaChiPGD: "",
        dienThoaiPGD: "",
        anhGiayTo: "",
      },

      tableConfig: {
        tableCollumns: [
          {
            fieldName: "ID",
            headerText: "ID",
            allowFiltering: true,
            allowSorting: false,
            textAlign: "",
          },
          {
            fieldName: "TEN_KH",
            headerText: "Tên khách hàng",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGAYSINH",
            headerText: "Ngày sinh",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "SO_GIAY_TO",
            headerText: "Số giấy tờ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGAYCAP",
            headerText: "Ngày cấp",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NOICAP",
            headerText: "Nơi cấp",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "DIACHI",
            headerText: "Địa chỉ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "LOAIGOI",
            headerText: "Loại gói",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "MA_DV",
            headerText: "Mã đơn vị",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "SOCHUNGMINH",
            headerText: "Giấy xác thực",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "LOGUSER",
            headerText: "User TH",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGAYTH",
            headerText: "Ngày TH",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "LOGIP",
            headerText: "IP",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
        tableData: [],
      },

      tableDSDoanConfig: {
        tableCollumns: [
          {
            fieldName: "ID",
            headerText: "ID",
            allowFiltering: true,
            allowSorting: false,
            textAlign: "",
            width: 50,
          },
          {
            fieldName: "NAME",
            headerText: "Tên đơn vị",
            allowFiltering: true,
            allowSorting: false,
            textAlign: "",
          },
        ],
        tableData: [],
      },
    };
  },
  async created() {
    // /console.log(await this.$root.token.getMachine());
    this.may_cn = await this.$root.token.getMachine();
    this.ip_cn = await this.$root.token.getIP();
    this.nguoi_cn = await this.$root.token.getNguoiDungID()
    console.log(this.nguoi_cn)
    let res = await api.getDSDoiTuongSD(this.axios)
    this.thongTinCaNhan.doiTuongSD = res.data.data ? res.data.data : [];
    this.thongTinCaNhan.gioiTinh = [
      {
        id: 0,
        ten: "Nam",
      },
      {
        id: 1,
        ten: "Nữ",
      },
      {
        id: -1,
        ten: "Khác",
      },
    ];

    // lấy danh sách loại giấy tờ
    let res1 = await api.getDSLoaiGT(this.axios)
    console.log(res1.data.data);
    this.thongTinCaNhan.loaiGiayTo = res1.data.data ? res1.data.data : [];

    // lấy danh sách loại khách hàng
    let res2 = await api.getDSLoaiKH(this.axios)
    console.log( res2.data.data);
    this.thongTinCaNhan.loaiKH = res2.data.data ? res2.data.data : [];

    // lấy danh sách nơi cấp
    let res3 = await api.getDSTinhNeo(this.axios)
    console.log(res3.data.data);
    this.thongTinCaNhan.noiCap = res3.data.data ? res3.data.data : [];

    // lấy ds quốc tịch các nước
    let res4 = await api.layDSQuocTich(this.axios)
    console.log(res4.data.data);
    this.thongTinCaNhan.quocTich = res4.data.data ? res4.data.data : [];

    // lấy đối tượng thuê bao
    let res5 = await api.getDSDTTB(this.axios)
    this.thongTinGoiCuoc.doiTuongTB = res5.data.data ? res5.data.data : [];
    this.thongTinGoiCuoc.doiTuongTBSelected = res5.data.data ? res5.data.data[0].ID : ""
    // lay danh sách đơn vị đoàn
    let res6 = await api.getDSDonViD(this.axios)
    console.log(res6.data.data);
    this.tableDSDoanConfig.tableData = res6.data.data ? res6.data.data : [];

    // lấy danh sách giấy xác nhận
    let res7 =  await api.getDSGiayXN(this.axios)
    console.log(res7.data.data);
    this.thongTinGoiCuoc.loaiXacNhan = res7.data.data ? res7.data.data : [];
  },
  mounted() {},
  computed: {},
  watch: {},
  methods: {
    validateData () {
      this.$v.$touch()
      if (this.$v.$invalid && !this.$v.thongTinCaNhan.loaiGiayToSelected.required) {
        this.$root.$toast.error('Loại giấy tờ không được để trống!')
        return false
      }
      return true
    },
    checkValidNgayCap(value){
      if (!moment(value).isValid()) {
                this.thongTinCaNhan.ngayCap = ""
        }
    },
    checkValidNgaySinh(value){
      if (!moment(value).isValid()) {
                this.thongTinCaNhan.ngaySinh = ""
        }
    },
    checkValidNgayYC(value){
      if (!moment(value).isValid()) {
                this.ngayYeuCau = ""
        }
    },
    checkValidNgayHD(value){
      if (!moment(value).isValid()) {
                this.ngayLapHD = ""
        }
    },
    
    nhapMoi() {
      this.setButton(0);
      this.resetForm();
    },

    async TaoDuLieu_HDKH(themmoi) {
      let vmagd_temp = "";
      // check KHLON_ID = new CheckDataFacade().MAP_ID("khlon_id", DatabaseConstants.DB2 + ".db_khachhang", "where khachhang_id = " + khachhang_id);
      // if(themmoi){
      //   let res = await api.GetKey(this.axios, "HD_KHACHHANG")
      //   this.hdkh_id = res.data.data ? res.data.data : ""
      // }
      let itemHDKH = {
        KHACHHANG_ID: this.khachhang_id,
        MA_GD: this.maGiaoDich,
        MA_HD: this.maHopDong,
        MA_KH: this.ma_kh,
        TEN_KH: this.thongTinCaNhan.tenKH,
        DIACHI_KH: this.thongTinCaNhan.diaChi,
        NGAY_SN: this.thongTinCaNhan.ngaySinh,
        NGAYLAP_HD: this.ngayLapHD,
        SO_GT: this.thongTinCaNhan.soGiayTo,
        NGAYCAP: this.thongTinCaNhan.ngayCap,
        NOICAP: this.thongTinCaNhan.noiCapSelected,
        DONVI_ID: this.$root.token.getDonViID(),
        NHANVIEN_ID: this.$root.token.getNhanVienID(),
        KHLON_ID: this.khlon_id,
        MAY_CN: this.may_cn,
        NGUOI_CN: this.nguoi_cn,
        IP_CN: this.ip_cn,
        HDKH_ID: this.hdkh_id,
        LOAIHD_ID: LoaiHopDong.DD_DOANVIEN
      };
      this.p_json_hdkh = JSON.stringify(itemHDKH);
    },

    async TaoDuLieu_HDTB(themmoi) {
      //  if(themmoi){
      //   let res = await api.GetKey(this.axios, "HD_THUEBAO")
      //   this.hdtb_id = res.data.data ? res.data.data : ""
      // }
      let itemHDTB = {
        MA_TB: "84" + this.thongTinCaNhan.soMay,
        TEN_TB: this.thongTinCaNhan.tenKH,
        GHICHU: this.thongTinGoiCuoc.ghiChu,
        HDKH_ID: this.hdkh_id,
        HDTB_ID: this.hdtb_id ? this.hdtb_id : 0,
        KIEULD_ID: "75", //KieuLapDat.THAYDOI_TT_THUEBAO
        TTHD_ID: TrangThaiHD.MOI,
        LOAITB_ID: "21", //LoaiHinhTB.DIDONGTRATRUOC
        DOITUONG_ID: this.thongTinGoiCuoc.doiTuongTBSelected,
        DICHVUVT_ID: "2", //DichVuVienThong.DI_DONG,
        DONVI_ID: this.donvi_id,
        MAY_CN: this.may_cn,
        NGAY_CN: this.ngayLapHD,
        NGUOI_CN: this.nguoi_cn,
        IP_CN: this.ip_cn,
        STATUS: "0",
      };
      this.p_json_hdtb = JSON.stringify(itemHDTB);
    },

    TaoDuLieu_HDTB_DD(themmoi) {},

    async tsbtnGhiLai_Click() {
      // if(this.thongTinCaNhan.soMay == ""){
      //   this.$toast.warning(
      //     "Vui lòng điển thông tin số máy!"
      //   );
      //   this.$refs.soMayRef.focus();
      //   return ;
      // }
      //if(!this.validateData()) return false
      if(!this.kiemTraGhiLai()) return false;
      
      let themmoi = this.activeNhapMoi;

      if (themmoi) {
        //kiem tra lap hop dong khac
        let dataSend = {
          vma_tb: this.thongTinCaNhan.soMay,
          vma_gd: 0,
          vdichvuvt_id: DichVuVienThong.DI_DONG,
          vloaihd_id: LoaiHopDong.DD_DOANVIEN,
        };

        let check = await api.KTMaTBLapHDKhac(this.axios, dataSend);
        if (check.data.error_code == "BSS-00000000") {
          return false;
        }
      }
      

      this.TaoDuLieu_HDKH(themmoi);
      this.TaoDuLieu_HDTB(themmoi);
      this.TaoDuLieu_HDTB_DD(themmoi);
      let dataS = {
        p_json_hdkh: this.p_json_hdkh,
        p_json_hdtb: this.p_json_hdtb,
        p_json_hdtb_dd: "",
        p_insert: themmoi ? "1" : "0",
      };
      console.log(dataS);
      let kq = await api.capNhatDoanVien(this.axios, dataS);
      console.log(kq.data)
      this.setButton(1);
      this.$toast.success(
        "Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!"
      );
    },

    async btnDangky_Click() {
      // if (!this.kiemTraNhap()) return false;
      if (!this.kiemTraHoanThien()) return false;
      await this.DangKyCCBS();
      try {
        console.log("hdtb_id = " + this.hdtb_id)
        let res = await api.hths_dk_goicuoc_sv(this.axios, this.hdtb_id);
        if(res.data.error_code == "BSS-00000000"){
          this.$toast.success("Đăng ký gói đoàn viên thành công!");
          this.setButton(0);
          this.resetForm();
        }   
      } catch (error) {
        // this.$toast.error("Có lỗi xẩy ra!");
      }
    },

    kiemTraGhiLai(){
      this.hasErrorNgaySinh = false
      let check = true
      let listLoi = []
      if (this.thongTinCaNhan.soMay == "") {
        this.$toast.error("Chưa có thông tin số máy đăng ký !");
        
        this.$refs.soMayRef.style.borderColor = "red"
        listLoi.push(1);
        check = false
      }else{
        let  vnf_regex = /^((9|3|7|8|5)+([0-9]{8,9})\b)/g;
        let validPhone = vnf_regex.test(this.thongTinCaNhan.soMay)
        console.log(validPhone);
        if (!validPhone) 
        {       
            this.$toast.error("Số máy không hợp lệ");
            listLoi.push(1);
            this.$refs.soMayRef.style.borderColor = "red"
            check = false
        }else{
            this.$refs.soMayRef.style.borderColor = "#ced4da"
            listLoi = this.removeElement(listLoi, 1)
        }
      }

      if(this.thongTinCaNhan.email.trim().length > 200){
        this.$toast.warning("Email không được lớn hơn 200 ký tự!");
        listLoi.push(4);
        this.$refs.emailRef.style.borderColor = "red"
        check = false
      }else{
        this.$refs.emailRef.style.borderColor = "#ced4da"
        listLoi = this.removeElement(listLoi, 4)
      }

      if(this.thongTinCaNhan.tenKH.trim().length > 200){
        this.$toast.warning("Tên khách hàng không được lớn hơn 200 ký tự!");
        listLoi.push(5);
        this.$refs.tenKHRef.style.borderColor = "red"
        check = false
      }else{
        this.$refs.tenKHRef.style.borderColor = "#ced4da"
        listLoi = this.removeElement(listLoi, 5)
      }


      if (this.thongTinCaNhan.ngaySinh == "") {
        this.$toast.error("Chưa có thông tin ngày sinh !");
        this.hasErrorNgaySinh = true
        check = false
      }

      
      if(this.thongTinCaNhan.ngheNghiep.trim().length > 100){
        this.$toast.warning("Nghề nghiệp không được lớn hơn 100 ký tự!");
        listLoi.push(7);
        check = false
        this.$refs.ngheNghiepRef.style.borderColor = "red"
      }else{
        this.$refs.ngheNghiepRef.style.borderColor = "#ced4da"
        listLoi = this.removeElement(listLoi, 7)
      }

      if (this.thongTinCaNhan.diaChi == "") {
        this.$toast.error("Chưa có thông tin địa chỉ !");
        this.$refs.diaChiRef.style.borderColor = "red"
        listLoi.push(8);
        check = false
      }else{
        if(this.thongTinCaNhan.diaChi.trim().length > 500){
          this.$toast.warning("Địa chỉ khách hàng không được lớn hơn 500 ký tự!");
          listLoi.push(8);
          this.$refs.diaChiRef.style.borderColor = "#red"
          check = false
        }else{
          this.$refs.diaChiRef.style.borderColor = "#ced4da"
          listLoi = this.removeElement(listLoi, 8)
        }
        
      }

      if(this.thongTinCaNhan.congTy.trim().length > 500){
        this.$toast.warning("Tên công ty không được lớn hơn 500 ký tự!");
        listLoi.push(9);
        this.$refs.congTyRef.style.borderColor = "red"
        check = false
      }else{
        this.$refs.congTyRef.style.borderColor = "#ced4da"
        listLoi = this.removeElement(listLoi, 9)
      }

      if(!this.validateData()){
        check = false
      };

      if (this.thongTinCaNhan.soGiayTo == "") {
        this.$toast.error("Chưa có thông tin số giấy tờ !");
        this.$refs.soGiayToRef.style.borderColor = "red"
        listLoi.push(11);
        check = false
      }else{
        if(this.thongTinCaNhan.soGiayTo.trim().length > 200){
          this.$toast.warning("Số giấy tờ không được lớn hơn 200 ký tự!");
          this.$refs.soGiayToRef.style.borderColor = "red"
          listLoi.push(11);
          check = false
        }else{
          this.$refs.soGiayToRef.style.borderColor = "#ced4da"
          listLoi = this.removeElement(listLoi, 11)
        }
      }

      if(this.thongTinCaNhan.diaChiCongTy.trim().length > 500){
        this.$toast.warning("Địa chỉ công ty không được lớn hơn 500 ký tự!");
        listLoi.push(12);
        this.$refs.diaChiCTyRef.style.borderColor = "red"
        check = false
      }else{
        this.$refs.diaChiCTyRef.style.borderColor = "#ced4da"
        listLoi = this.removeElement(listLoi, 12)
      }

     
      switch(Math.min(...listLoi)) {
        case 1:
          // số máy
          this.$refs.soMayRef.focus()
          break;
        case 4:
          // code block
          this.$refs.emailRef.focus()
          break;
        case 5:
          // code block
          this.$refs.tenKHRef.focus()
          break;
        case 7:
          // code block
          this.$refs.ngheNghiepRef.focus()
          break;
        case 8:
          // code block
          this.$refs.diaChiRef.focus()
          break;
        case 9:
          // code block
          this.$refs.congTyRef.focus()
          break;
        case 11:
          // code block
          this.$refs.soGiayToRef.focus()
          break;
        case 12:
          // code block
          this.$refs.diaChiCTyRef.focus()
          break;
      }

      return check;
    },

    removeElement(array, elem) {
        let index = array.indexOf(elem);
        if (index > -1) {
            array.splice(index, 1);
        }
        return array

    },

    kiemTraHoanThien(){
      let temp = true
      let dsLoi = [];
      if(this.thongTinGoiCuoc.maXacThuc.trim() == ""){
        this.$toast.warning("Chưa có thông tin mã xác thực");
        dsLoi.push(2);
        temp = false
      }else{
        if(this.thongTinGoiCuoc.maXacThuc.trim().length > 20){
          this.$toast.warning("Mã xác thực không được lớn hơn 20 ký tự!");
          dsLoi.push(2);
          temp = false
          this.$refs.maXacThucRef.style.borderColor = "red"
        }else{
          this.$refs.maXacThucRef.style.borderColor = "#ced4da"
          dsLoi = this.removeElement(dsLoi, 2)
        }
      }

      if(this.thongTinGoiCuoc.donVi == ""){
        this.$toast.warning("Chưa có thông đơn vị");
        dsLoi.push(3);
        temp = false
        this.$refs.donViRef.style.borderColor = "red"
      }else{
        this.$refs.donViRef.style.borderColor = "#ced4da"
        dsLoi = this.removeElement(dsLoi, 3)
      }
      
      if(this.thongTinGoiCuoc.soXacNhan.trim() == ""){
        this.$toast.warning("Chưa có thông tin số xác nhận");
        dsLoi.push(5);
        temp = false
      }else{
        if(this.thongTinGoiCuoc.soXacNhan.trim().length > 100){
          this.$toast.warning("Số xác nhận không được lớn hơn 100 ký tự!");
          dsLoi.push(5);
          temp = false
          this.$refs.soXacNhanRef.style.borderColor = "red"
        }else{
          this.$refs.soXacNhanRef.style.borderColor = "#ced4da"
          dsLoi = this.removeElement(dsLoi, 5)
        }
      }
      

      if(this.thongTinGoiCuoc.diaChiPGD.trim().length > 500){
        this.$toast.warning("Địa chỉ PGD không được lớn hơn 500 ký tự!");
        dsLoi.push(6);
        temp = false
        this.$refs.diaChiPGDRef.style.borderColor = "red"
      }else{
        this.$refs.diaChiPGDRef.style.borderColor = "#ced4da"
        dsLoi = this.removeElement(dsLoi, 6)
      }

      if(this.thongTinGoiCuoc.ghiChu.trim().length > 500){
        this.$toast.warning("Ghi chú không được lớn hơn 500 ký tự!");
        dsLoi.push(7);
        temp = false
        this.$refs.ghiChuRef.style.borderColor = "red"
      }else{
        this.$refs.ghiChuRef.style.borderColor = "#ced4da"
        dsLoi = this.removeElement(dsLoi, 7)
      }

      if(this.thongTinGoiCuoc.dienThoaiPGD.trim().length > 12){
        this.$toast.warning("Điện thoại PGD không được lớn hơn 12 ký tự!");
        dsLoi.push(8);
        temp = false
        this.$refs.dienThoaiPGDRef.style.borderColor = "red"
      }else{
        this.$refs.dienThoaiPGDRef.style.borderColor = "#ced4da"
        dsLoi = this.removeElement(dsLoi, 8)  
      }

      switch(Math.min(...dsLoi)) {
        case 2:
          // số máy
          this.$refs.maXacThucRef.focus()
          break;
        case 3:
          // số máy
          this.$refs.donViRef.focus()
          break;
        case 5:
          // code block
          this.$refs.soXacNhanRef.focus()
          break;
        case 6:
          // code block
          this.$refs.diaChiPGDRef.focus()
          break;
        case 7:
          // code block
          this.$refs.ghiChuRef.focus()
          break;
        case 8:
          // code block
          this.$refs.dienThoaiPGDRef.focus()
          break;
      }

      return temp;

    },

    kiemTraMaxLeng(){
      let temp = true
      if(this.thongTinCaNhan.tenKH.trim().length > 200){
        this.$toast.warning("Tên khách hàng không được lớn hơn 200 ký tự!");
        temp = false
      }

      if(this.thongTinCaNhan.diaChi.trim().length > 500){
        this.$toast.warning("Địa chỉ khách hàng không được lớn hơn 500 ký tự!");
        temp = false
      }

      if(this.thongTinCaNhan.soGiayTo.trim().length > 200){
        this.$toast.warning("Số giấy tờ không được lớn hơn 200 ký tự!");
        temp = false
      }

      if(this.thongTinCaNhan.email.trim().length > 200){
        this.$toast.warning("Email không được lớn hơn 200 ký tự!");
        temp = false
      }

      if(this.thongTinCaNhan.ngheNghiep.trim().length > 100){
        this.$toast.warning("Nghề nghiệp không được lớn hơn 100 ký tự!");
        temp = false
      }

      if(this.thongTinCaNhan.congTy.trim().length > 500){
        this.$toast.warning("Tên công ty không được lớn hơn 500 ký tự!");
        temp = false
      }

      if(this.thongTinCaNhan.diaChiCongTy.trim().length > 500){
        this.$toast.warning("Địa chỉ công ty không được lớn hơn 500 ký tự!");
        temp = false
      }

      if(this.thongTinGoiCuoc.maXacThuc.trim().length > 20){
        this.$toast.warning("Mã xác thực không được lớn hơn 20 ký tự!");
        temp = false
      }

      if(this.thongTinGoiCuoc.soXacNhan.trim().length > 100){
        this.$toast.warning("Số xác nhận không được lớn hơn 100 ký tự!");
        temp = false
      }

      if(this.thongTinGoiCuoc.ghiChu.trim().length > 500){
        this.$toast.warning("Ghi chú không được lớn hơn 500 ký tự!");
        temp = false
      }

      if(this.thongTinGoiCuoc.diaChiPGD.trim().length > 500){
        this.$toast.warning("Địa chỉ PGD không được lớn hơn 500 ký tự!");
        temp = false
      }

      if(this.thongTinGoiCuoc.dienThoaiPGD.trim().length > 12){
        this.$toast.warning("Điện thoại PGD không được lớn hơn 12 ký tự!");
        temp = false
      }
      return temp;

    },

    kiemTraNhap() {
      if (this.thongTinCaNhan.soMay == "") {
        this.$toast.error("Chưa có thông tin số máy đăng ký !");
        this.$refs.soMayRef.focus()
        return false;
      }

      if (this.thongTinGoiCuoc.maXacThuc == "") {
        this.$toast.error("Chưa có thông tin mã xác thực !");
        this.$refs.maXacThucRef.focus()
        return false;
      }

      if (this.thongTinGoiCuoc.loaiXacNhanSelected == "") {
        this.$toast.error("Chưa có thông tin loại xác nhận !");
        return false;
      }

      if (this.thongTinCaNhan.soGiayTo == "") {
        this.$toast.error("Chưa nhập số giấy xác nhận !");
        this.$refs.soGiayToRef.focus()
        return false;
      }

      if (this.thongTinGoiCuoc.donViId == "") {
        this.$toast.error("Bạn chưa chọn đơn vị đoàn !");
        this.$refs.donViRef.focus()
        return false;
      }
      return true;
    },

    async DangKyCCBS() {
      let dataSend = {
        loai_giay_to: this.thongTinCaNhan.loaiGiayToSelected,
        dienthoai_lh_phong_giao_dich:
          this.thongTinGoiCuoc.dienThoaiPGD.toString(),
        ma_tinh: "",
        doituong: this.thongTinCaNhan.doiTuongSDSelected.toString(),
        gioi_tinh: this.thongTinCaNhan.gioiTinhSelected.toString(),
        userid: "",
        noi_cap: this.thongTinCaNhan.noiCapSelected.toString(),
        ma_xac_thuc: this.thongTinGoiCuoc.maXacThuc.toString(),
        passport: "",
        sub_type: this.thongTinGoiCuoc.doiTuongTBSelected.toString(),
        ghi_chu: this.thongTinGoiCuoc.ghiChu.toString(),
        dia_chi_cong_ty: this.thongTinCaNhan.diaChiCongTy.toString(),
        so_cmnd: this.thongTinCaNhan.soGiayTo,
        email: this.thongTinCaNhan.email,
        loai_giay_xac_nhan: this.thongTinGoiCuoc.loaiXacNhanSelected,
        ma_don_vi: this.$root.token.getDonViID().toString(),
        ip: "",
        ten_khach_hang: this.thongTinCaNhan.tenKH.toString(),
        cong_ty: this.thongTinCaNhan.congTy.toString(),
        ngay_sinh: this.thongTinCaNhan.ngaySinh,
        nghe_nghiep: this.thongTinCaNhan.ngheNghiep.toString(),
        so_giay_xac_nhan: this.thongTinGoiCuoc.soXacNhan.toString(),
        so_tb: "84" + this.thongTinCaNhan.soMay.toString(),
        ngay_cap: this.thongTinCaNhan.ngayCap.toString(),
        dia_chi_diem_giao_dich: this.thongTinGoiCuoc.diaChiPGD.toString(),
        dia_chi: this.thongTinCaNhan.diaChi.toString(),
      };
      console.log(dataSend);
      let res = await api.dangKyCCBS(this.axios, dataSend);
      if (res.data.error_code == "BSS-00000000") {
        this.$toast.success("Đăng ký thông tin gói đoàn viên thành công");
        await this.LayThongTinThueBao("84" + this.thongTinCaNhan.soMay);
      }else{
        this.$toast.error("Có lỗi xẩy ra khi đăng ký thông tin, " + res.data.message_detail);
        return false;
      }
    },

    async LayThongTinThueBao(ma_tb) {
      // var rs = new CCBSFacade().tt_8_dk_doanvien_lay_thongtin(ma_tb, tt_nd.USER_NEO, tt_nd.ip);
      let dataSend = {
        so_tb: ma_tb,
      };

      let res = await api.tt_8_dk_doanvien_lay_thongtin(this.axios, dataSend);
      if (res.data.data.length == 0) {
        this.$toast.error("Không tìm thấy thông tin yêu cầu !");
        return false;
      }
      let obj = res.data.data[0];
      this.thongTinCaNhan.tenKH = obj.TEN_KH;
      this.thongTinCaNhan.ngaySinh = this.formatDateNew(obj.NGAYSINH);
      this.thongTinCaNhan.loaiGiayToSelected = obj.LOAIGT;
      this.thongTinCaNhan.noiCapSelected = obj.NOICAP;
      this.thongTinCaNhan.diaChi = obj.DIACHI;
      this.thongTinCaNhan.loaiKHSelected = obj.loaikh_id;
      this.thongTinCaNhan.ngheNghiep = obj.NGHENGHIEP;
      this.thongTinCaNhan.diaChiCongTy = obj.DIACHICTY;
      this.thongTinCaNhan.congTy = obj.CONGTY;
      this.thongTinCaNhan.gioiTinhSelected = obj.GIOITINH;
      this.thongTinCaNhan.quocTichSelected = obj.NUOC;
      this.thongTinCaNhan.email = obj.EMAIL;
      this.thongTinCaNhan.doiTuongSDSelected = obj.NOICAP;
      this.thongTinGoiCuoc.dienThoaiPGD = obj.transaction_phone;
      this.thongTinGoiCuoc.donViId = obj.unit_id;
      this.thongTinGoiCuoc.donVi = obj.unit_name;
      this.thongTinGoiCuoc.ghiChu = obj.NOTE;
      this.thongTinGoiCuoc.loaiXacNhanSelected = obj.ref_type;
      this.thongTinGoiCuoc.soXacNhan = obj.ref_number;
      this.lblNgayHH = "Ngày hết hạn gói cước: " + obj.hethan;
      
      if (obj.unit_id != "") {
        this.setButton(3);
      } else {
        this.setButton(2);
      }

      //Lấy lịch sử đk gói
      this.tableConfig.tableData = [];
      let dataSend1 = {
        ip: "",
        so_tb: ma_tb,
        userid: "",
      };
      let res1 = await api.tt_8_dk_doanvien_lichsu_sub(this.axios, dataSend1);
      this.tableConfig.tableData = res1.data.data ? res1.data.data : [];
      

    },



    layDVDoan(data) {
      console.log(data.rowData);
      if (data.rowData) {
        this.thongTinGoiCuoc.donVi = data.rowData.NAME;
        this.thongTinGoiCuoc.donViId = data.rowData.ID;
        // this.donvi_id = data.rowData.ID;

        this.$refs["ref-modal"].hide();
      }
    },

    showPopupDSDoan() {
      // console.log('xxx');
      this.$refs['ref-modal'].show()
    },

    onModalHidden(){

    },

    formatDate(value_) {
      if (moment(value_).isValid()) {
        return moment(value_).format("DD/MM/YYYY HH:mm:ss");
      } else {
        return value_;
      }
    },

    formatDateNew(value_){
      if (moment(value_).isValid()) {
        return moment(value_).format("DD/MM/YYYY");
      } else {
        return value_;
      }
    },

    async timKiemDanhBaTheoTB() {
      this.$refs.soMayRef.style.borderColor = "#ced4da"
      let  vnf_regex = /^((9|3|7|8|5)+([0-9]{8,9})\b)/g;
      let validPhone = vnf_regex.test(this.thongTinCaNhan.soMay)
      console.log(validPhone);
      if (!validPhone) 
      {       
          this.$toast.error("Số máy không hợp lệ");
          this.$refs.soMayRef.focus()
          this.$refs.soMayRef.style.borderColor = "red"
          return false
      }

      let dataSendGD = {
        in_ma_gd: "84" + this.thongTinCaNhan.soMay.trim(),
        in_loaihd_id: 102,
        in_tthd_id: 1 , //TrangThaiHD.HOAN_THANH
        in_donvi_dl_id: 0,
      };
      
      let temp = await api.layDSHopDongTheoMGD(this.axios, dataSendGD)
      if(temp.data.data && temp.data.data.length > 0){
        let data = temp.data.data[0];
        await this.HienThiTTHopDongKH(data);
         // lay thông tin ảnh theo thuê bao
        await this.layThongTinAnhTheoThueBao(this.thongTinCaNhan.soMay.trim())
      }else{
        let dataSend = {
          dichvuvt_id: DichVuVienThong.DI_DONG,
          ma_tb: this.thongTinCaNhan.soMay.trim(),
          donvi_id: 0 // this.$root.token.getDonViID(),
        };

        let res = await api.layDanhBaTheoTB(this.axios, dataSend)
        if (res.data.data && res.data.data.length > 0) {
            let data = res.data.data[0];
            this.thongTinCaNhan.tenKH = data.ten_kh;
            this.maHopDong = data.ma_hd;
            this.thongTinCaNhan.ngayCap = this.formatDateNew(data.ngaycap);
            this.ngayLapHD = this.formatDate(data.ngaylap_hd);
            this.thongTinCaNhan.diaChi = data.diachi_tb;
            this.thongTinCaNhan.ngaySinh = this.formatDateNew(data.ngay_sn_tb);
            this.doituong_id = data.doituong_id;
            this.thongTinCaNhan.gioiTinhSelected = data.gioitinh;
            this.thongTinCaNhan.quocTichSelected = data.quoctich_neo_id;
            this.thongTinCaNhan.loaiGiayToSelected = data.loaigt_id;
            this.thongTinCaNhan.noiCapSelected = data.noicap;
            this.thongTinCaNhan.soGiayTo = data.so_gt;
            this.thongTinCaNhan.ngheNghiep = data.tennganhnghe;
            this.thongTinCaNhan.loaiKHSelected = data.loaikh_id;
            this.khlon_id = data.khlon_id;
            this.donvi_id = data.donviql_id;
            this.khachhang_id = data.khachhang_id;
            this.ma_kh = data.ma_kh;
            this.tinhkhac = 0;
            this.doituong_id_neo = data.doituong_id_neo == "" ? 1 : data.doituong_id_neo;
            // lay thông tin ảnh theo thuê bao
            await this.layThongTinAnhTheoThueBao(this.thongTinCaNhan.soMay.trim())
            
        } else {
          this.tinhkhac = 1;
          this.$toast.warning("Không lấy được thông tin theo thuê bao!")

        }
      }

    },

    async layThongTinAnhTheoThueBao(matb){
      let dataSendImg = {
        "so_tb": "84" + matb
      }
      let resImg = await api.app_tb_image_dktt(this.axios, dataSendImg);
      console.log(resImg);
      if(resImg.data.error_code == "BSS-00000000"){
          let result =  resImg.data.data;
          for(let i = 0; i < result.length; i++){
            if(result[i].type == "1" && result[i].image_base != ""){
              this.urlImg1 = result[i].image_base;
              this.anh1 = "Xem ảnh 1";
            }
            if(result[i].type == "2" && result[i].image_base != ""){
              this.urlImg2 = result[i].image_base;
              this.anh2 = "Xem ảnh 2"
            }
            if(result[i].type == "3" && result[i].image_base != ""){
              this.urlImg3 = result[i].image_base
              this.anh3 = "Xem ảnh 3"
            }
            if(result[i].type == "4" && result[i].image_base != ""){
              this.urlImg4 = result[i].image_base
              this.anh4 = "Xem ảnh 4"
            }
          }
      }else{
        this.$toast.error(res.data.message)
      }
    },

    
    async timKiemTheoMaGD() {
      let dataSend = {
        in_ma_gd: this.maGiaoDich,
        in_loaihd_id: 102,
        in_tthd_id: 1 , //TrangThaiHD.MOI
        in_donvi_dl_id: 0,
      };

      let res = await api.layDSHopDongTheoMGD(this.axios, dataSend)
      console.log(res.data);
      if (res.data.data && res.data.data.length > 0) {
        let data = res.data.data[0]
        await this.HienThiTTHopDongKH(data);
      }
    },

    async HienThiTTHopDongKH(data){
        console.log(data);
        if (data.status == "5" || data.status == "7") {
            this.$toast.error("Thuê bao đã được kích hoạt!");
            return;
        }
        this.thongTinCaNhan.tenKH = data.ten_kh;
        console.log(data.ma_tb)
        if(data.ma_tb.includes("84")){
            this.thongTinCaNhan.soMay = data.ma_tb.substring(2, data.ma_tb.length);
        }else{
          this.thongTinCaNhan.soMay = data.ma_tb
        }
        this.maGiaoDich = data.ma_gd;
        this.hdkh_id = data.hdkh_id;
        this.hdtb_id = data.hdtb_id;
        this.maHopDong = data.ma_hd;
        this.ngayLapHD = this.formatDate(data.ngaylap_hd);
        if (data.ngay_yc != "" || data.ngay_yc != null) {
          this.ngayYeuCau = this.formatDate(data.ngay_yc);
        }
        this.thongTinCaNhan.diaChi = data.diachi_kh;
        this.thongTinCaNhan.ngaySinh = this.formatDateNew(data.ngay_sn);
        this.thongTinCaNhan.loaiKHSelected = data.loaikh_id
        this.thongTinCaNhan.loaiGiayToSelected = data.loaigt_id
        this.thongTinCaNhan.quocTichSelected = data.quoctich_id
        this.thongTinCaNhan.soGiayTo = data.so_gt
        this.thongTinCaNhan.noiCapSelected = data.noicap
        this.thongTinCaNhan.ngayCap =  this.formatDateNew(data.ngaycap);
        // this.thongTinCaNhan.soMay  = data.so_dt
        let dataS = {
          in_hdkh_id: this.hdkh_id,
          in_tthd_id: 1 //TrangThaiHD.MOI,
        };
        this.setButton(1);
        let res1 = await api.layDSHDTBTheohdkh_tthd(this.axios, dataS)
        console.log(res1.data);
        if (res1.data.data && res1.data.data.length > 0) {
            let data1 = res1.data.data[0];
            await this.HienThiTTHopDongTB(data1);
        }
    },

    async HienThiTTHopDongTB(data1) {
      this.hdtb_id = parseInt(data1.hdtb_ID);
      // this.thongTinCaNhan.soMay = data1.ma_TB;
      this.doituong_id = data1.doituong_ID;
      this.donvi_id = data1.donvi_ID;
      if (data1.thuebao_id != "") {
        this.thuebao_id = data1.thuebao_ID;
      }
      if(data1.thuebao_ID){
        let dataS2 = {
          in_thuebao_id: data1.thuebao_ID,
        };

        let res2 = await api.LAY_DS_DB_DD_THEO_TBID(this.axios, dataS2)
        console.log('LAY_DS_DB_DD_THEO_TBID');
        console.log(res2.data);
        if (res2.data.data && res2.data.data.length > 0) {
          let data2 = res2.data.data[0];
          this.doiTuongSDSelected = data2.doituong_id_neo;
        }
      }
      
    },

    tsbtnHuy_Click() {
      if (confirm("Bạn thật sự muốn xóa hợp đồng không ?")) {
        let list = [];
        let item = {
          hdkh_id: this.hdkh_id,
          loaihd_id: 102, //LoaiHopDong.DD_DOANVIEN
        };
        list.push(item);
        let dataSend = {
          list: list,
        };
        api.xoaHDKHTheoLoaiHD(this.axios, dataSend).then((res) => {
          console.log(res.data);
          if(res.data.error_code == "BSS-00000000"){
            this.$toast.success("Xóa hợp đồng thành công!")
            this.setButton(0);
            this.resetForm();
          }else{
            this.$toast.error("Có lỗi xẩy ra!")
          }
          
        });
      }
    },

    tsbtnGuiMaXacNhan_Click() {
      if (this.thongTinCaNhan.soMay == ""){
        this.$toast.warning("Chưa có thông tin số máy")
        return;
      }
      
      let dataSend = {
        so_tb: this.thongTinCaNhan.soMay,
      };
      api.guiMaXacThuc(this.axios, dataSend).then((res) => {
        console.log(res.api);
        if (res.data.error_code == "BSS-00000000") {
          this.$toast.success(
            "Đã gửi mã xác nhận đến thuê bao: " +
              "84" +
              this.thongTinCaNhan.soMay
          );
        } else {
          this.$toast.error(
            "Gửi mã xác thực đến thuê bao " +
              "84" +
              this.thongTinCaNhan.soMay +
              " thất bại"
          );
        }
      });
    },
    uploadFile() {
      this.$refs.file1.click();
    },

    uploadFile2() {
      this.$refs.file2.click();
    },
    uploadFile3() {
      this.$refs.file3.click();
    },
    uploadFile4() {
      this.$refs.file4.click();
    },

    uploadFileUpdate(){
      this.$refs.uploadUpdate.click();
    },

    async importFile(e) {
      const files = e.target.files;
      console.log(files[0]);
      if(!files[0].type.includes("image")){
        this.$toast.warning("Vui lòng chọn file định dạng là ảnh!");
        return false;
      }
      let formData = new FormData();
      formData.append("files", files[0]);
      console.log(formData);
      api
        .upload_file(this.axios, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          if (res.data.error_code == "BSS-00000000") {
            this.urlAnh1 = res.data.data[0];
            console.log(this.urlAnh1);
            this.anh1 = "Xem ảnh 1";
            let dataGetImg = {
              list_file: res.data.data[0]
            }
            api.layAnhTheoId(this.axios, dataGetImg).then(res1 => {     
                console.log(res1.data);   
                this.urlImg1 = res1.data.data[0].file_url
            })
          }
        });
    },

    ClickAnh2(e) {
      const files = e.target.files;
      if(!files[0].type.includes("image")){
        this.$toast.warning("Vui lòng chọn file định dạng là ảnh!");
        return false;
      }
      let formData = new FormData();
      formData.append("files", files[0]);
      api
        .upload_file(this.axios, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          console.log(res.data);
          if (res.data.error_code == "BSS-00000000") {
            this.urlAnh2 = res.data.data[0];
            console.log(this.urlAnh2);
            this.anh2 = "Xem ảnh 2";
            let dataGetImg = {
              list_file: res.data.data[0]
            }
            api.layAnhTheoId(this.axios, dataGetImg).then(res1 => {     
                console.log(res1.data);   
                this.urlImg2 = res1.data.data[0].file_url
            })
          }
        });
    },

    ClickAnh3(e) {
      const files = e.target.files;
      if(!files[0].type.includes("image")){
        this.$toast.warning("Vui lòng chọn file định dạng là ảnh!");
        return false;
      }
      let formData = new FormData();
      formData.append("files", files[0]);
      api
        .upload_file(this.axios, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          console.log(res.data);
          if (res.data.error_code == "BSS-00000000") {
            this.urlAnh3 = res.data.data[0];
            console.log(this.urlAnh3);
            this.anh3 = "Xem ảnh 3";

            let dataGetImg = {
              list_file: res.data.data[0]
            }
            api.layAnhTheoId(this.axios, dataGetImg).then(res1 => {     
                console.log(res1.data);   
                this.urlImg3 = res1.data.data[0].file_url
            })
          }
        });
    },

    ClickAnh4(e) {
      const files = e.target.files;
      if(!files[0].type.includes("image")){
        this.$toast.warning("Vui lòng chọn file định dạng là ảnh!");
        return false;
      }
      let formData = new FormData();
      formData.append("files", files[0]);
      api
        .upload_file(this.axios, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          console.log(res.data);
          if (res.data.error_code == "BSS-00000000") {
            this.urlAnh4 = res.data.data[0];
            console.log(this.urlAnh4);
            this.anh4 = "Xem ảnh 4";
            let dataGetImg = {
              list_file: res.data.data[0]
            }
            api.layAnhTheoId(this.axios, dataGetImg).then(res1 => {     
                console.log(res1.data);   
                this.urlImg4 = res1.data.data[0].file_url
            })
          }
        });
    },

    importFileUpdate(e){
      const files = e.target.files;
      if(!files[0].type.includes("image")){
        this.$toast.warning("Vui lòng chọn file định dạng là ảnh!");
        return false;
      }
      let formData = new FormData();
      formData.append("files", files[0]);
      api
        .upload_file(this.axios, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          console.log(res.data);
          if (res.data.error_code == "BSS-00000000") {
            let dataGetImg = {
              list_file: res.data.data[0]
            }

            api.layAnhTheoId(this.axios, dataGetImg).then(res1 => {     
                console.log(res1.data);     
                this.currentImg = res1.data.data[0].file_url
                console.log(this.currentImg);
                if(this.typeCurrrentImg == "1"){
                  this.urlAnh1 = res.data.data[0];
                  this.urlImg1 = this.currentImg
                }
                if(this.typeCurrrentImg == "2"){
                  this.urlAnh2 = res.data.data[0];
                  this.urlImg2 = this.currentImg
                }
                if(this.typeCurrrentImg == "3"){
                  this.urlAnh3 = res.data.data[0];
                  this.urlImg3 = this.currentImg
                }
                if(this.typeCurrrentImg == "4"){
                  this.urlAnh4 = res.data.data[0];
                  this.urlImg4 = this.currentImg
                }
            })
            
            
          }
        });
    },

    XemAnh(type){
      this.typeCurrrentImg = type
      if(type == "1"){
          if(this.urlImg1.includes("DHSX/QUANTRI")){
            console.log("không vào đây à")
          this.currentImg = this.urlImg1
        }else{
          this.currentImg = "data:image/png;base64, " + this.urlImg1
        }
      }

      if(type == "2"){
          
        if(this.urlImg2.includes("DHSX/QUANTRI")){
          this.currentImg = this.urlImg2
        }else{
          this.currentImg = "data:image/png;base64, " + this.urlImg2
        }
      }

      if(type == "3"){
        
        if(this.urlImg3.includes("DHSX/QUANTRI")){
          this.currentImg = this.urlImg3
        }else{
          this.currentImg = "data:image/png;base64, " + this.urlImg3
        }
      }

      if(type == "4"){
        if(this.urlImg4.includes("DHSX/QUANTRI")){
          this.currentImg = this.urlImg4
        }else{
          this.currentImg = "data:image/png;base64, " + this.urlImg4
        }
        
      }
  
      this.$refs['ref-modal-img'].show()
    },

    btnTaiAnh_Click() {
      if (
        this.urlImg1 != "" &&
        this.urlImg1 != "" &&
        this.urlImg1 != "" &&
        this.urlImg1 != ""
      ) {
        let lydo = "";
        if(this.urlAnh3 == ""){
          lydo = 4
        }else{
          lydo = ""
        }
        let dataSend = {
          gt_img_1: this.urlAnh1,
          gt_img_2: this.urlAnh2,
          gt_img_3: this.urlAnh3,
          gt_img_4: this.urlAnh4,
          ly_do: lydo,
          so_tb: "84" + this.thongTinCaNhan.soMay,
        };
        console.log(dataSend);
        api.upload_img_dktt(this.axios, dataSend).then((res) => {
          console.log(res.data);
          if(res.data.error_code == "BSS-00000000"){
            this.$toast.success("Tải ảnh lên thành công")
          }else{
            this.$toast.error("Tải ảnh lên thất bại! " + res.data.message)
          }
        });
      } else {
        this.$toast.error("phải chọn đủ 4 ảnh để tải lên!");
      }
    },

    setButton(trangThai) {
      this.activeNhapMoi = true;
      this.activeHuyGoi = true;
      this.activeGhiLai = true;
      (this.activeHoanThien = true),
        (this.activeTaiAnh = true),
        (this.activeGuiXacNhan = true);
      switch (trangThai) {
        case 0:
          this.activeNhapMoi = true;
          this.activeGhiLai = false;
          this.activeHuyGoi = false;
          break;
        case 1:
          this.activeNhapMoi = false;
          this.activeGhiLai = false;
          this.activeGuiXacNhan = false;
          this.activeHoanThien = false;
          this.activeTaiAnh = false;
          this.activeHuyGoi = false;
          break;
        case 2:
          this.activeGuiXacNhan = false;
          this.activeHoanThien = false;
          this.activeTaiAnh = false;
          break;
        case 3:
          this.activeHuyGoi = true;
          this.activeHoanThien = false;
          break;
      }
    },

    resetForm() {
      this.maGiaoDich = ""

      //reset thong tin ca nhan
      this.thongTinCaNhan.soMay = "";
      this.thongTinCaNhan.tenKH = "";
      this.thongTinCaNhan.doiTuongSDSelected = "";
      this.thongTinCaNhan.ngaySinh = "";
      this.thongTinCaNhan.diaChi = "";
      this.thongTinCaNhan.loaiGiayToSelected = "";
      this.thongTinCaNhan.soGiayTo = "";
      this.thongTinCaNhan.noiCapSelected = "";
      this.thongTinCaNhan.ngayCap = "";
      this.thongTinCaNhan.loaiKHSelected = "";
      this.thongTinCaNhan.email = "";
      this.thongTinCaNhan.ngheNghiep = "";
      this.thongTinCaNhan.congTy = "";
      this.thongTinCaNhan.diaChiCongTy = "";
      this.thongTinCaNhan.gioiTinhSelected = "";
      this.thongTinCaNhan.quocTichSelected = "";

      //reset thong tin goi cuoc
      this.thongTinGoiCuoc.doiTuongTBSelected = this.thongTinGoiCuoc.doiTuongTB[0].ID;
      this.thongTinGoiCuoc.loaiXacNhanSelected = "";
      this.thongTinGoiCuoc.maXacThuc = "";
      this.thongTinGoiCuoc.soXacNhan = "";
      this.thongTinGoiCuoc.ghiChu = "";
      this.thongTinGoiCuoc.donVi = "";
      this.thongTinGoiCuoc.diaChiPGD = "";
      this.thongTinGoiCuoc.dienThoaiPGD = "";
      this.thongTinGoiCuoc.anhGiayTo = "";

      this.donvi_id = "";
    },
  },
};
</script>

<style>
.disabled {
  /* pointer-events: none; */
  cursor: no-drop;
  opacity: 0.7;
}
.borderRed input{
  border-color: red;
}
.disabled-color {
  color: gray !important;
}
</style>