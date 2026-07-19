<template src="./ProcessPotentialCustomers.html"></template>
<style scoped src="./ProcessPotentialCustomers.scss"></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import moment from "moment";
import "@/assets/vendor/jquery/split";
import ChiTietNganhNgheModal from "@/modules/contract/setup/SurveyRequest/popups/ChiTietNganhNghe/ChiTietNganhNgheModal.vue";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
import ModalChonDiaChi from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi";
import BssErrorMarker from "@/components/BssErrorMarker";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import HandoverTicket from "@/modules/contract/setup/HandoverTicket/HandoverTicket.vue";
import Vue from "vue";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import {
  LoaiHopDong,
  KieuLapDat,
  TrangThaiHD,
  NHOM_QT,
  DoiTuong,
} from "./ThamSo";
Vue.use(DialogPlugin);
export default {
  components: {
    breadcrumb,
    ChiTietNganhNgheModal,
    SearchContractModal,
    SearchAccount,
    moment,
    DialogPlugin,
    ModalChonDiaChi,
    BssErrorMarker,
    BssRequiredMarker,
    HandoverTicket,
  },
  data: function () {
    return {
      header: {
        title: "",
      },
      list: [
        { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
        {
          name: "Lắp đặt mới",
          link: { name: "Ui.buttons" },
        },
      ],
      dateconfig: {
        altFormat: "d/m/Y",
        altInput: true,
        dateFormat: "d/m/Y",
        allowInput: true,
      },
      datetimeconfig: {
        altFormat: "d/m/Y H:i:S",
        altInput: true,
        dateFormat: "d/m/Y H:i:S",
        allowInput: true,
        enableTime: true,
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentEvents: {
        "form-close": "popupClosed",
      },
      list_TTThuebao: {
        dichvuvt_options: [],
        dichvuvt: null,
        loaihinh_options: [],
        loaihinh: null,
        doituong_options: [],
        doituong: null,
        kieuld_options: [],
        kieuld: 0,
        ngaysinh: new Date(),
        ten_tb: "",
        thuebao_id: 0,
        ma_tb: "",
        ghichu: "",
        hdtb_id: 0,
      },
      list_TTKhachHang: {
        quoctich_options: [],
        quoctich_id: null,
        nganhnghe_options: [],
        nganhnghe: null,
        loaigiayto_options: [],
        loaigiayto: null,
        ngay_yc: new Date(),
        ma_gd: "",
        ma_kh: "",
        ngaycap: new Date(),
        ngayhethan: new Date(),
        ngaysinh: new Date(),
        gioitinh: 0,
        sodienthoai: "",
        ten_kh: "",
        khachhhang_id: 0,
        noicap: "",
        hdkh_id: 0,
        sogiayto: "",
      },
      dataTieuNganh: {
        TNC1_ID: 0,
        TNC2_ID: 0,
        TNC3_ID: 0,
        TNC4_ID: 0,
        nganhnghe_id: 0,
      },
      diachiKH: {
        DIACHI_ID: null,
        DIACHI: null,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: null,
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
      },
      diachiTT: {
        DIACHI_ID: null,
        DIACHI: null,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: null,
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
      },
      diachiTB: {
        DIACHI_ID: null,
        DIACHI: null,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: null,
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
      },
      diachiLD: {
        DIACHI_ID: null,
        DIACHI: null,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: null,
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
      },
      params: {
        today: new Date(),
        loaihd_id: 0,
        tthd_id: 0,
        quytrinh_id: 0,
      },
      button: {
        ghilai: true,
        giaophieu: true,
        huyyc: true,
      },
      ds_PhieuYC: {
        list: [],
        data: [],
      },
       tag: 0,
    };
  },
  created() {
    this.loadDSDichVu();
    this.loadDoiTuong();
    this.loadQuocTich();
    this.loadNganhNghe();
    this.loadLoaiGiayTo();
    this.params.loaihd_id = LoaiHopDong.TUVAN_DV; //phối hợp tư vấn dịch vụ giữa NVKT và TTKD
    if (
      this.$route.params &&
      this.$route.params.tag !== null &&
      this.$route.params.tag !== undefined
    ) {
      this.tag = this.$route.params.tag;
    }
    if(this.tag != 0){
      this.params.tthd_id =this.tag
    }else{
      this.params.tthd_id = TrangThaiHD.DANG_THI_CONG;
    }
    
    this.HienThiDanhSacHDTB();
    this.SetButton(-1);
  },
  methods: {
    loadDSDichVu() {
      API.getDSDichVu(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({
                  id: item.DICHVUVT_ID,
                  text: item.TEN_DVVT,
                });
              });
              this.list_TTThuebao.dichvuvt_options = items.sort(function (a, b) {
                return a.id - b.id;
              });;
              this.list_TTThuebao.dichvuvt = items[0].id;
              this.loadLoaiHinh(items[0].id, 0);
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadLoaiHinh(dvvtid, loaihinh) {
      this.list_TTThuebao.loaihinh_options = [];
      this.list_TTThuebao.loaihinh = null;
      API.getLoaiHinhTB(this.axios, dvvtid)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.ID, text: item.NAME });
            });
            this.list_TTThuebao.loaihinh_options = items;
            this.list_TTThuebao.loaihinh = items[0].id;
            if (loaihinh != 0) {
              this.list_TTThuebao.loaihinh = loaihinh;
            }
            this.loadKieuLD(items[0].id, this.list_TTThuebao.kieuld);
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadDoiTuong() {
      this.list_TTThuebao.doituong_options = [];
      this.list_TTThuebao.doituong = null;
      API.getDoiTuong(this.axios, {
        nguoidung_id: this.$root.token.getNguoiDungID(),
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.doituong_id, text: item.ten_dt });
            });
            this.list_TTThuebao.doituong_options = items;
            this.list_TTThuebao.doituong = items[0].id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadQuocTich() {
      this.list_TTKhachHang.quoctich_options = [];
      this.list_TTKhachHang.quoctich = null;
      API.getQuocTich(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.QUOCTICH_ID, text: item.TENQUOCTICH });
            });
            this.list_TTKhachHang.quoctich_options = items;
            this.list_TTKhachHang.quoctich = items[0].id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadLoaiGiayTo() {
      this.list_TTKhachHang.loaigiayto_options = [];
      API.getLoaiGiayTo(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.LOAIGT_ID, text: item.LOAI_GT });
            });
            this.list_TTKhachHang.loaigiayto_options = items;
            this.list_TTKhachHang.loaigiayto = items[0].id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadKieuLD(loaihinhtb, kieuld) {
      this.list_TTThuebao.kieuld_options = [];
      this.list_TTThuebao.kieuld = null;
      API.getDSKieuLD(this.axios, {
        loaihd_id: this.params.loaihd_id,
        loaitb_id: loaihinhtb,
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.kieuld_id, text: item.ten_kieuld });
            });
            this.list_TTThuebao.kieuld_options = items.filter(x =>x.id == 900);
            this.list_TTThuebao.kieuld = 900;
            if (kieuld != 0) {
              this.list_TTThuebao.kieuld = kieuld;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadNganhNghe() {
      this.list_TTKhachHang.listnganhnghe = [];
      API.getNganhNghe(this.axios, { vma: "NGANHNGHE" })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.list_TTKhachHang.nganhnghe_options = response.data.data.map((x) => ({
            id: x.nganhnghe_id,
            text: x.tennganhnghe,
          }));;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    changeNganhNghe() {
      if (this.list_TTKhachHang.nganhnghe != this.dataTieuNganh.nganhnghe_id) {
        this.dataTieuNganh = {
          TNC1_ID: 0,
          TNC2_ID: 0,
          TNC3_ID: 0,
          TNC4_ID: 0,
          nganhnghe_id:
            this.list_TTKhachHang.nganhnghe != null
              ? this.list_TTKhachHang.nganhnghe
              : 0,
        };
     }
      this.clickButtonNganhNghe();
    },
    clickButtonNganhNghe() {
      if (this.list_TTKhachHang.nganhnghe != null) {
        this.$refs.chiTietNganhNgheModal.showModal();
      } else {
        this.$toast.error("Bạn chưa chọn ngành nghề!");
      }
    },
    acceptTieuNganh(data) {
      Object.assign(this.dataTieuNganh, data);
    },
    async kiemtra_dl_khachhang() {
      if (this.list_TTKhachHang.sodienthoai.trim() == "") {
        this.$toast.error(
          "Bạn chưa nhập số điện thoại liên hệ của khách hàng ! "
        );
        this.$refs.inputSDT.focus();
        return false;
      }
      let maTinh = this.$root.token.getMaTinh();
      if (maTinh == "HPG") {
        if (this.list_TTKhachHang.ten_kh.trim() == "") {
          this.$toast.error("Hãy nhập tên khách hàng !");
          this.$refs.inputTenKH.focus();
          return false;
        }
        //Kiểm tra địa chỉ KH
        if (
          this.diachiKH.DIACHI == "" ||
          this.diachiKH.QUAN_ID == 0 ||
          this.diachiKH.PHUONG_ID == 0
        ) {
          this.$toast.error("Hãy nhập địa chỉ khách hàng!");
          this.$refs.inputDiaChiKH.focus();
          return false;
        }
        if (
          this.params.loaihd_id != LoaiHopDong.KHAOSATDATMOI &&
          this.list_TTKhachHang.nganhnghe == null
        ) {
          this.$toast.error("Bạn chưa nhập ngành nghề ! ");
          this.$refs.inputNganhNghe.focus();
          return false;
        }
        if (
          this.params.loaihd_id != LoaiHopDong.KHAOSATDATMOI &&
          (this.dataTieuNganh.TNC1_ID == 0 ||
            this.dataTieuNganh.TNC2_ID == 0 ||
            this.dataTieuNganh.TNC3_ID == 0)
        ) {
          this.$toast.error(
            "Bạn chưa chọn chi tiết ngành nghề.Hãy kiểm tra lại ! "
          );
          this.$refs.inputNganhNghe.focus();
          return false;
        }
        if (this.list_TTKhachHang.ma_gd.trim() != "") {
          try {
            let response = await API.kiemtra_makh_matt_magd(this.axios, {
              ma_kh: this.list_TTKhachHang.ma_gd,
              kieu: 3,
              loaihd_id: this.params.loaihd_id,
            });
            if (
              response &&
              response.data &&
              response.data.error_code &&
              response.data.error_code == "BSS-00000000" &&
              response.data.data != null &&
              response.data.data == 0
            ) {
              this.$toast.error(
                "Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh. Bạn hãy kiểm tra lại!"
              );
              this.$refs.inputMaGD.focus();
              return false;
            }
          } catch (e) {
            console.log("sp_themmoi_hopdong_ks" + e);
            this.$toast.error(
              "Đã xảy ra lỗi trong quá trình kiễm tra dữ liệu."
            );
            return false;
          }
        }
      }
      return true;
    },
    async kiemtra_dl_thuebao() {
      if (this.list_TTThuebao.ten_tb.trim() == "") {
        this.$toast.error("Hãy nhập tên thuê bao !");
        this.$refs.inputTenTB.focus();
        return false;
      }
      //Kiểm tra địa chỉ
      if (
        this.diachiLD.DIACHI == "" ||
        this.diachiLD.QUAN_ID == 0 ||
        this.diachiLD.PHUONG_ID == 0
      ) {
        this.$toast.error("Hãy nhập địa chỉ lắp đặt!");
        this.$refs.inputDiaChiLD.focus();
        return false;
      }
      if (
        this.diachiTB.DIACHI == "" ||
        this.diachiTB.QUAN_ID == 0 ||
        this.diachiTB.PHUONG_ID == 0
      ) {
        this.$toast.error("Hãy nhập địa chỉ thuê bao!");
        this.$refs.inputDiaChiTB.focus();
        return false;
      }
      if (this.list_TTThuebao.kieuld == KieuLapDat.KHAOSATCDLH) {
        try {
          let response = await API.fn_kiemtra_hd_chuahoanthien(
            this.axios,
            this.list_TTThuebao.thuebao_id
          );
          if (
            response &&
            response.data &&
            response.data.error_code &&
            response.data.error_code == "BSS-00000000" &&
            response.data.data
          ) {
            let result = Number(response.data.data);
            if (result > 0) {
              this.$toast.error(
                "Thuê bao đang có hợp đồng khác chưa hoàn thiện, bạn không thể lập hợp đồng!"
              );
              return false;
            }
          }
        } catch (e) {
          this.$toast.error("Đã xảy ra lỗi trong quá trình kiễm tra dữ liệu.");
          return false;
        }
      }
      return true;
    },
    Clear() {
      this.list_TTThuebao.ten_tb = "";
      this.list_TTKhachHang.ma_kh = "";
      this.list_TTKhachHang.khachhhang_id = 0;
      this.list_TTKhachHang.noicap = "";
      this.list_TTKhachHang.hdkh_id = 0;
      this.list_TTKhachHang.ma_gd = "";
      this.list_TTKhachHang.khachhang_id = 0;
      this.list_TTKhachHang.ten_kh = "";
      this.diachiKH.DIACHI = "";
      this.diachiKH.TINH_ID = 0;
      this.diachiKH.QUAN_ID = 0;
      this.diachiKH.PHUONG_ID = 0;
      this.diachiKH.PHO_ID = 0;
      this.diachiKH.AP_ID = 0;
      this.list_TTKhachHang.ngaysinh = "";
      this.list_TTKhachHang.sogiayto = "";
      this.list_TTKhachHang.ngaycap = "";
      this.list_TTKhachHang.gioitinh = 0;
      this.list_TTKhachHang.loaigiayto = 0;
      this.list_TTKhachHang.ngayhethan = "";
      this.list_TTKhachHang.quoctich = 0;
      this.list_TTThuebao.thuebao_id = 0;
      this.list_TTThuebao.ten_tb = "";
      this.list_TTThuebao.ngaysinh = "";
      this.diachiTB.DIACHI = "";
      this.diachiTB.TINH_ID = 0;
      this.diachiTB.QUAN_ID = 0;
      this.diachiTB.PHUONG_ID = 0;
      this.diachiTB.PHO_ID = 0;
      this.diachiTB.AP_ID = 0;
      this.diachiLD.DIACHI = "";
      this.diachiLD.TINH_ID = 0;
      this.diachiLD.QUAN_ID = 0;
      this.diachiLD.PHUONG_ID = 0;
      this.diachiLD.PHO_ID = 0;
      this.diachiLD.AP_ID = 0;
      this.list_TTThuebao.doituong = DoiTuong.TU_NHAN;
      this.list_TTThuebao.ghichu = "";
      this.dataTieuNganh.TNC1_ID = 0;
      this.dataTieuNganh.TNC2_ID = 0;
      this.dataTieuNganh.TNC3_ID = 0;
      this.dataTieuNganh.TNC4_ID = 0;
      this.dataTieuNganh.nganhnghe_id = 0;
      this.list_TTKhachHang.sodienthoai = "";
      this.button.ghilai = true;
    },
    SetButton(kieu) {
      this.button.ghilai = false;
      this.button.huyyc = false;
      if (kieu == -1) {
        //Bat dau
        this.button.ghilai = true;
      }
      if (kieu == 0) {
        //Bat dau
        this.Clear();
        this.button.ghilai = false;
      }
      if (kieu == 2) {
        //Huy
        this.button.huyyc = true;
        this.Clear();
      }
      if (kieu == 3) {
        //Edit
        this.button.ghilai = true;
        this.button.huyyc = true;
      }
    },
    ShowSearchAccount() {
      this.popupComponent = SearchAccount;
      SearchAccount.data.isPopup = true;
      this.Popup("popupComponents");
      this.popupComponentName = "popupSearchAccount";
    },
    HienThiTTHopDongKH(ds) {
      if (ds.length > 0) {
        this.list_TTKhachHang.ma_kh = ds[0].ma_kh;
        this.list_TTKhachHang.hdkh_id = ds[0].hdkh_id;
        this.list_TTKhachHang.ma_gd = ds[0].ma_gd;
        this.list_TTKhachHang.khachhhang_id =
          ds[0].khachhang_id != "" ? ds[0].khachhang_id : 0;
        this.list_TTKhachHang.ten_kh = ds[0].ten_kh;
        this.diachiKH.DIACHI = ds[0].diachi_kh;
        this.list_TTKhachHang.ngaysinh = ds[0].ngay_sn != "" ? ds[0].ngay_sn : "";
        this.list_TTKhachHang.ngay_yc =ds[0].ngay_yc;
        this.list_TTKhachHang.gioitinh = ds[0].gioitinh;
        this.list_TTKhachHang.quoctich = ds[0].quoctich_id;
        API.lay_ds_diachi_theo_hdkh(
          this.axios,
          this.list_TTKhachHang.hdkh_id
        ).then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.HienThiTTDiaChi(response.data.data, 1);
          }
        });
        this.list_TTKhachHang.sodienthoai = ds[0].so_dt;
        this.list_TTKhachHang.nganhnghe = ds[0].nganhnghe_id;
        this.dataTieuNganh.TNC1_ID = ds[0].TNC1_ID != "" ? ds[0].TNC1_ID : 0;
        this.dataTieuNganh.TNC2_ID = ds[0].TNC2_ID != "" ? ds[0].TNC2_ID : 0;
        this.dataTieuNganh.TNC3_ID = ds[0].TNC3_ID != "" ? ds[0].TNC3_ID : 0;
        this.dataTieuNganh.TNC4_ID = ds[0].TNC4_ID != "" ? ds[0].TNC4_ID : 0;
        this.list_TTKhachHang.ngayhethan =
          ds[0].ngayhethan_gt != "" ? ds[0].ngayhethan_gt : "";
        this.list_TTKhachHang.sogiayto = ds[0].so_gt;
        this.list_TTKhachHang.ngaycap =
          ds[0].ngaycap != "" ? ds[0].ngaycap : "";
        this.list_TTKhachHang.loaigiayto = ds[0].loaigt_id;
        this.list_TTKhachHang.noicap = ds[0].noicap;
      } else {
        this.SetButton(0);
      }
    },
    HienThiTTDiaChi(ds, kieu) {
      if (ds.length > 0) {
        if (kieu == 1) {
          this.diachiKH.DIACHI_ID = ds[0].diachi_id;
          this.diachiKH.TINH_ID = ds[0].tinh_id;
          this.diachiKH.QUAN_ID = ds[0].quan_id;
          this.diachiKH.PHUONG_ID = ds[0].phuong_id != "" ? ds[0].phuong_id : 0;
          this.diachiKH.PHO_ID = ds[0].pho_id != "" ? ds[0].pho_id : 0;
          this.diachiKH.AP_ID = ds[0].ap_id != "" ? ds[0].ap_id : 0;
          this.diachiKH.KHU_ID = ds[0].khu_id != "" ? ds[0].khu_id : 0;
          this.diachiKH.SO_NHA = ds[0].sonha;
        }
        if (kieu == 2) {
          this.diachiTT.DIACHI_ID = ds[0].diachi_id;
          this.diachiTT.TINH_ID = ds[0].tinh_id;
          this.diachiTT.QUAN_ID = ds[0].quan_id;
          this.diachiTT.PHUONG_ID = ds[0].phuong_id != "" ? ds[0].phuong_id : 0;
          this.diachiTT.PHO_ID = ds[0].pho_id != "" ? ds[0].pho_id : 0;
          this.diachiTT.AP_ID = ds[0].ap_id != "" ? ds[0].ap_id : 0;
          this.diachiTT.KHU_ID = ds[0].khu_id != "" ? ds[0].khu_id : 0;
          this.diachiTT.SO_NHA = ds[0].sonha;
        }
        if (kieu == 3) {
          //Hiếu- thêm phần địa chỉ thuê bao + địa chỉ lắp đặt 21/07/2010
          //Địa chỉ thuê bao
          this.diachiTB.DIACHI_ID = ds[0].diachi_id;
          this.diachiTB.TINH_ID = ds[0].tinh_id;
          this.diachiTB.QUAN_ID = ds[0].quan_id;
          this.diachiTB.PHUONG_ID = ds[0].phuong_id != "" ? ds[0].phuong_id : 0;
          this.diachiTB.PHO_ID = ds[0].pho_id != "" ? ds[0].pho_id : 0;
          this.diachiTB.AP_ID = ds[0].ap_id != "" ? ds[0].ap_id : 0;
          this.diachiTB.KHU_ID = ds[0].khu_id != "" ? ds[0].khu_id : 0;
          this.diachiTB.SO_NHA = ds[0].sonha;
          //địa chỉ lắp đặt
          this.diachiLD.DIACHI_ID = ds[0].diachild_id;
          this.diachiLD.TINH_ID = ds[0].tinhld_id;
          this.diachiLD.QUAN_ID = ds[0].quanld_id;
          this.diachiLD.PHUONG_ID =
            ds[0].phuongld_id != "" ? ds[0].phuongld_id : 0;
          this.diachiLD.PHO_ID = ds[0].phold_id != "" ? ds[0].phold_id : 0;
          this.diachiLD.AP_ID = ds[0].apld_id != "" ? ds[0].apld_id : 0;
          this.diachiLD.KHU_ID = ds[0].khuld_id != "" ? ds[0].khuld_id : 0;
          this.diachiLD.SO_NHA = ds[0].sonha_ld;
        }
      }
    },
    HienThiTT_DanhBa(ds) {
      if (ds.length > 0) {
        this.list_TTKhachHang.ma_kh = ds[0].ma_kh;
        this.list_TTKhachHang.khachhang_id =
          ds[0].khachhang_id != "" ? ds[0].khachhang_id : 0;
        this.list_TTKhachHang.ten_kh = ds[0].ten_kh;
        this.diachiKH.DIACHI = ds[0].diachi_kh;
        API.lay_ds_diachi_theo_khachhang(
          this.axios,
          this.list_TTKhachHang.khachhang_id
        ).then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.HienThiTTDiaChi(response.data.data, 1);
          }
        });
        this.list_TTKhachHang.ngaysinh =
          ds[0].ngay_sn != "" ? ds[0].ngay_sn : "";
        //this.list_TTKhachHang.ngay_yc = tt_nd.ngay_cn
        this.list_TTKhachHang.gioitinh = ds[0].gioitinh;
        this.list_TTKhachHang.quoctich = ds[0].quoctich_id;
        this.list_TTKhachHang.sodienthoai = ds[0].so_dt;
        this.list_TTKhachHang.nganhnghe = ds[0].nganhnghe_id;
        this.dataTieuNganh.TNC1_ID = ds[0].TNC1_ID != "" ? ds[0].TNC1_ID : 0;
        this.dataTieuNganh.TNC2_ID = ds[0].TNC2_ID != "" ? ds[0].TNC2_ID : 0;
        this.dataTieuNganh.TNC3_ID = ds[0].TNC3_ID != "" ? ds[0].TNC3_ID : 0;
        this.dataTieuNganh.TNC4_ID = ds[0].TNC4_ID != "" ? ds[0].TNC4_ID : 0;
        this.list_TTKhachHang.ngayhethan =
          ds[0].ngayhethan_gt != "" ? ds[0].ngayhethan_gt : "";
        this.list_TTKhachHang.sogiayto = ds[0].so_gt;
        this.list_TTKhachHang.ngaycap =
          ds[0].ngaycap != "" ? ds[0].ngaycap : "";
        this.list_TTKhachHang.loaigiayto = ds[0].loaigt_id;
        this.list_TTKhachHang.noicap = ds[0].noicap;
        if (this.params.loaihd_id != LoaiHopDong.TIEPNHAN_DATMOI) {
          this.list_TTThuebao.thuebao_id = ds[0].thuebao_id;
          this.list_TTThuebao.ma_tb = ds[0].ma_tb;
          this.list_TTThuebao.loaihinh = ds[0].loaitb_id;
        } else {
          this.list_TTThuebao.thuebao_id = 0;
          this.list_TTThuebao.ma_tb = "";
        }
        this.list_TTThuebao.ten_tb = ds[0].ten_tb;
        this.diachiTB.DIACHI = ds[0].diachi_tb;
        this.diachiLD.DIACHI = ds[0].diachi_ld;
        API.lay_ds_diachi_theo_dbtbid(this.axios, {
          in_thuebao_id: this.list_TTThuebao.thuebao_id,
        }).then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.HienThiTTDiaChi(response.data.data, 3);
          }
        });
        this.list_TTThuebao.ngaysinh =
          ds[0].ngay_sn_tb != "" ? ds[0].ngay_sn_tb : "";
        this.list_TTThuebao.doituong = ds[0].doituong_id;
      } else {
        this.SetButton(0);
      }
    },
    HienThiTTHopDongTB(dr) {
      if (dr != null) {
        this.list_TTThuebao.hdtb_id = dr.hdtb_id;
        this.params.quytrinh_id = dr.quytrinh_id;
        this.list_TTThuebao.thuebao_id =
          dr.thuebao_id != "" ? dr.thuebao_id : 0;
        this.list_TTThuebao.ma_tb = dr.ma_tb;
        this.list_TTKhachHang.ma_gd = dr.ma_gd;
        this.list_TTKhachHang.hdkh_id = dr.hdkh_id;
        API.lay_hdkh_theo_hdkhid(this.axios, {
          hdkh_id: this.list_TTKhachHang.hdkh_id,
        }).then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.HienThiTTHopDongKH(response.data.data);
          }
        });
        this.list_TTThuebao.ten_tb = dr.ten_tb;
        this.diachiTB.DIACHI = dr.diachi_tb;
        this.diachiLD.DIACHI = dr.diachi_ld;
        API.lay_ds_diachi_theo_hdtb_id(this.axios, {
          in_hdtb_id: this.list_TTThuebao.hdtb_id,
        }).then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.HienThiTTDiaChi(response.data.data, 3);
          }
        });
        this.list_TTThuebao.ghichu = dr.ghichu;
        this.list_TTThuebao.ngaysinh = dr.ngay_sn != "" ? dr.ngay_sn : "";
        this.list_TTThuebao.dichvuvt = dr.dichvuvt_id;
        this.list_TTThuebao.loaihinh = dr.loaitb_id;
        this.loadLoaiHinh(dr.dichvuvt_id, this.list_TTThuebao.loaihinh);
        this.list_TTThuebao.kieuld =
          dr.kieuld_id != "" ? dr.kieuld_id : this.list_TTThuebao.kieuld;
        this.list_TTThuebao.doituong = dr.doituong_id;
        this.SetButton(3);
      }
    },
    popupClosed(val) {
      switch (this.popupComponentName) {
        case "popupSearchAccount":
          this.list_TTKhachHang.ma_kh = val.ma_kh;
          break;
      }
      this.ClosePopup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    onDichVuChange: function (agrs) {
      this.loadLoaiHinh(agrs.id, 0);
    },
    onLoaiHinhChange: function (agrs) {
      this.loadKieuLD(agrs.id, 0);
    },
    //#region Popup mã giao dịch
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal();
    },
    acceptSearchContract(item) {
      this.list_TTKhachHang.ma_gd = item.ma_gd;
      if (
        this.list_TTKhachHang.ma_gd &&
        this.list_TTKhachHang.ma_gd.trim() != ""
      )
        this.MaGDChanged();
    },
    MaGDChanged() {
      API.lay_ds_hopdong_theo_ma_gd(this.axios, {
        in_ma_gd: this.list_TTKhachHang.ma_gd,
        in_loaihd_id: this.params.loaihd_id,
        in_tthd_id: TrangThaiHD.MOI,
        in_donvi_dl_id: 0, //fix theo code cũ
      }).then((response) => {
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined &&
          response.data.data.length > 0
        ) {
          this.HienThiTTHopDongKH(response.data.data);
        } else {
          this.$toast.error("Không tìm thấy thông tin khách hàng!");
        }
      });
    },
    txtMaGD_KeyPress() {
      if (this.list_TTKhachHang.ma_gd != "") {
        this.MaGDChanged();
      } else {
        this.SetButton(-1);
      }
    },
    //#endregion
    txtTenKH_KeyPress() {
      this.showpopupDiaChiKH();
    },
    txtDiaChiKH_KeyPress() {
      this.showpopupDiaChiKH();
    },
    showpopupDiaChiKH() {
      this.$bvModal.show("popupDiaChiKH");
    },
    showpopupDiaChiTB() {
      this.$bvModal.show("popupDiaChiTB");
    },
    txtDiaChiTB_KeyPress() {
      this.showpopupDiaChiTB();
    },
    showpopupDiaChiLD() {
      this.$bvModal.show("popupDiaChiLD");
    },
    txtDiaChiLD_KeyPress() {
      this.showpopupDiaChiLD();
    },
    async Giao_Phieu() {
      let vquyen_huonggiao= 0;
      await API.dk_theo_bang_HUONGGIAO(this.axios,this.$root.token.getNguoiDungID()).then((response) => { 
          if(response.data.error_code === 'BSS-00000000' && response.data.data != null) {
            vquyen_huonggiao = response.data.data.replace(/\s/g, '');
          }
        }).catch((error) => {
          console.log(error);
        }).finally(() => {})
      await API.lay_ds_huonggiao(this.axios, {
        quyen_huonggiao: vquyen_huonggiao,
        nhom_qt_id: NHOM_QT.DIEUHANH_THICONG,
        quytrinh_id: 0,//fix theo code cu /// this.params.quytrinh_id,
        loaihd_id: 0, // this.params.loaihd_id,
        dichvuvt_id: 0 // this.list_TTThuebao.dichvuvt,
      }).then((response) => {
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined &&
          response.data.data.length > 0
        ) {
          var result = [];
          for(var i in  response.data.data){
            result.push( response.data.data[i].huonggiao_id);
          }
          let vhuonggiao_id =JSON.stringify(result)
          this.$router.push({
            name: "HandoverTicket",
            query: {
              trangthaiphieu: 0,
              madv: this.list_TTThuebao.dichvuvt,
              magd: this.list_TTKhachHang.ma_gd,
              huonggiao: vhuonggiao_id.toString(),
            },
          });
        } else {
          this.$router.push({
            name: "HandoverTicket"
          });
        }
      });
    },
    txtMaKH_KeyPress() {
      if (this.list_TTKhachHang.ma_kh != "") {
        API.lay_db_theo_matb(this.axios, {
          dichvuvt_id: 0,
          donvi_dl_id: 0,
          ma_tb: this.list_TTKhachHang.ma_kh,
          phanvung_id: 26,
        }).then((response) => {
          this.Clear();
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.HienThiTT_DanhBa(response.data.data);
          } else {
            this.$toast.error("Không tìm thấy thông tin khách hàng!");
          }
        });
      }
    },
    txtMaTB_KeyPress() {
      this.list_TTThuebao.ma_tb = this.list_TTThuebao.ma_tb
        .toLowerCase()
        .trim();
      if (this.list_TTThuebao.ma_tb != "") {
        API.lay_ds_hopdong_theo_ma_gd(this.axios, {
          in_ma_gd: this.list_TTThuebao.ma_tb,
          in_loaihd_id: this.params.loaihd_id,
          in_tthd_id: TrangThaiHD.MOI,
          in_donvi_dl_id: 0,
        }).then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data != null
          ) {
            this.HienThiTTHopDongKH(response.data.data);
          } else {
            API.lay_danhba_theo_matb(this.axios, {
              maTb: this.list_TTThuebao.ma_tb,
              dichVuVtId: this.list_TTThuebao.dichvuvt,
              donViDlId: 0,
            }).then((response) => {
              if (
                response.data.error_code === "BSS-00000000" &&
                response.data.data !== undefined &&
                response.data.data != null
              ) {
                this.HienThiTT_DanhBa(response.data.data);
              } else {
                this.$toast.error(
                  "Không tìm thấy thông tin về số máy/acc " +
                    this.list_TTThuebao.ma_tb +
                    " đối với dịch vụ " +
                    this.list_TTThuebao.dichvuvt_options.filter(
                      (x) => x.id == this.list_TTThuebao.dichvuvt
                    )[0].text
                );
                this.SetButton(0);
              }
            });
          }
        });
      }
    },
    getDiaChi: function (data, datatype) {
      this[datatype].DIACHI = data.diachimoi;
      this[datatype].TINH_ID = data.tinh_id;
      this[datatype].QUAN_ID = data.quan_id;
      this[datatype].PHUONG_ID = data.phuong_id;
      this[datatype].PHO_ID = data.pho_id;
      this[datatype].AP_ID = data.ap_id;
      this[datatype].KHU_ID = data.khu_id;
      this[datatype].DACDIEM_ID = data.dacdiem_id;
      this[datatype].SO_NHA = data.so_nha;
      this[datatype].Lat = data.lat;
      this[datatype].Lng = data.lng;
    },
    getDiaChiKH: function (data) {
      this.getDiaChi(data, "diachiKH");
    },
    diachiKHChanged() {},
    getDiaChiTB: function (data) {
      this.getDiaChi(data, "diachiTB");
    },
    diachiTBChanged() {},
    getDiaChiLD: function (data) {
      this.getDiaChi(data, "diachiLD");
    },
    diachiLDChanged() {},
    async HienThiDanhSacHDTB() {
      try {
        this.loading(true);
        const response = await API.lay_ds_hdtb_tuvan_datmoi(this.axios, {
          vhdkh_id: 0,
          vloaihd_id: this.params.loaihd_id,
          vtthd_id: this.params.tthd_id,
        });
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.ds_PhieuYC.list = response.data.data;
          this.button.huyyc = true;
        } else {
          this.button.huyyc = false;
          this.Clear();
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loading(false);
      }
    },
    onDSPhieuYCChange(data) {
      this.list_TTThuebao.hdtb_id = data.hdtb_id;
      this.ds_PhieuYC.data = data;
      this.HienThiTTHopDongTB(data);
    },
    taodulieu_hdkh() {
      let dshdkh = [
        {
          HDKH_ID: this.list_TTKhachHang.hdkh_id,
          MA_GD: this.list_TTKhachHang.ma_gd,
          MA_HD: "",
          MA_KH: this.list_TTKhachHang.ma_kh,
          KHACHHANG_ID: this.list_TTKhachHang.khachhang_id,
          TEN_KH: this.list_TTKhachHang.ten_kh,
          DIACHI_KH: this.diachiKH.DIACHI,
          NGUOI_DD: "",
          CHUCDANH_DD: "",
          SO_DT: this.list_TTKhachHang.sodienthoai,
          SO_FAX: "",
          WEBSITE: "",
          NGAY_SN: moment(this.list_TTKhachHang.ngaysinh).format(
            "DD/MM/YYYY"
          ),
          NGAYLAP_HD: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          SO_GT: this.list_TTKhachHang.sogiayto,
          NGAYCAP: moment(this.list_TTKhachHang.ngaycap).format(
            "DD/MM/YYYY HH:mm:ss"
          ),
          NOICAP: this.list_TTKhachHang.noicap,
          GHICHU: this.list_TTThuebao.ghichu,
          DONVI_ID: this.$root.token.getDonViID(),
          LOAIGT_ID: this.list_TTKhachHang.loaigiayto,
          NHANVIEN_ID: this.$root.token.getNhanVienID(),
          KHLON_ID: 0,
          LOAIHD_ID: this.params.loaihd_id,
          BOSUNGTB_ID: 0,
          MAY_CN: "admin",
          IP_CN: "127.0.0.1",
          NGAY_CN: moment(this.list_TTKhachHang.ngay_yc).format(
            "DD/MM/YYYY HH:mm:ss"
          ),
          NGUOI_CN: this.$root.token.getUserName(),
          KH_KT3: 0,
          MST_KH: "",
          HOKHAU: "",
          LOAIKH_ID: "",
          NOICAP_HK: "",
          NGAY_HK: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          NGAY_YC: moment(this.list_TTKhachHang.ngay_yc).format(
            "DD/MM/YYYY HH:mm:ss"
          ),
          GIOITINH: this.list_TTKhachHang.gioitinh,
          DANTOC_ID: 0,
          QUOCTICH_ID: this.list_TTKhachHang.quoctich,
          NGANHNGHE_ID: this.dataTieuNganh.nganhnghe_id,
          DIACHI_NDD: "",
          NGAYHETHAN_GT: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          MST: "",
          STK: "",
          NGANHANG_ID: "",
          CTV_ID: 0,
          TNC1_ID: this.dataTieuNganh.TNC1_ID,
          TNC2_ID: this.dataTieuNganh.TNC2_ID,
          TNC3_ID: this.dataTieuNganh.TNC3_ID,
          DACTRUNG: "",
          KIEUHD_ID: 0,
          HTTTHI_ID: 0,
          HDKH_CHA_ID: 0,
          PHANLOAIKH_ID: 0,
          NHANVIENGT_ID: 0,
          KENHTN_ID: 0,
          MA_DUAN: "",
          EMAIL: "",
          PHIEUCSKH_ID: 0,
          TNC4_ID: this.dataTieuNganh.TNC4_ID,
        },
      ];
      return dshdkh;
    },
    taodulieu_hdtb() {
      let dshdtb = [
        {
          HDTB_ID: this.list_TTThuebao.hdtb_id,
          THUEBAO_ID: this.list_TTThuebao.thuebao_id,
          MA_TB: this.list_TTThuebao.ma_tb,
          TEN_TB: this.list_TTThuebao.ten_tb,
          DIACHI_TB: this.diachiTB.DIACHI,
          GHICHU: this.list_TTThuebao.ghichu,
          HDTB_CHA_ID: 0,
          HDKH_ID: this.list_TTKhachHang.hdkh_id,
          HDTT_ID: 0,
          KIEULD_ID: this.list_TTThuebao.kieuld,
          TTHD_ID: this.params.tthd_id,
          LOAITB_ID: this.list_TTThuebao.loaihinh,
          DOITUONG_ID: this.list_TTThuebao.doituong,
          DICHVUVT_ID: this.list_TTThuebao.dichvuvt,
          DONVI_ID: 0,
          NGAY_HT: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          MAY_CN: "admin",
          IP_CN: "127.0.0.1",
          NGAY_CN: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          NGUOI_CN: this.$root.token.getUserName(),
          SOLUONG: 0,
          TBHD_PL: 0,
          DIACHI_LD: this.diachiLD.DIACHI,
          NGAY_SN: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          KIEU_TB: "",
          STATUS: 0,
          DONVI_DAU_ID: 0,
          DONVI_CUOI_ID: 0,
          MUCUOCTB_ID: 0,
          MUCUOC_TB: 0,
          THUEBAO_CHA_ID: 0,
          QUYTRINH_ID: this.params.quytrinh_id,
          CHUYENMANG: 0,
          TG_THUE_TU: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          TG_THUE_DEN: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          GOIKT_ID: 0,
          SOTHANG_CK: 0,
          NGAY_HH_TD: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          NHACC_CU_ID: 0,
          DOITUONG_CT_ID: 0,
          HTCM_ID: 0,
          TRANGTHAIYC_ID: 0,
        },
      ];
      return dshdtb;
    },
    taodulieu_diachi() {
      let dsdiachi = [
        {
          DIACHI_ID: 0,
          PHO_ID: 0,
          PHUONG_ID: 0,
          QUAN_ID: 0,
          SONHA: 0,
          AP_ID: 0,
          DIACHI: 0,
          TINH_ID: 0,
          KHU_ID: 0,
          DACDIEM_ID: 0,
          VIDO: 0,
          KINHDO: 0,
          BLOCK: 0,
          TANG: 0,
          PHONG: 0,
          MOTA: 0,
          HDTT_ID: 0,
        },
      ];
      return dsdiachi;
    },
    taodulieu_diachi_hdtb() {
      let dsdiachi_hdtb = [
        {
          HDTB_ID: this.list_TTThuebao.hdtb_id,
          DIACHI_ID: this.diachiTB.DIACHI_ID,
          DIACHILD_ID: this.diachiLD.DIACHI_ID,
          KINHDO: this.diachiTB.Lng,
          VIDO: this.diachiTB.Lat,
          KINHDO_LD: this.diachiLD.Lng,
          VIDO_LD: this.diachiLD.Lat,
          NGUOI_CN: this.$root.token.getUserName(),
          NGAY_CN: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          MAY_CN: "admin",
          IP_CN: "127.0.0.1",
        },
      ];
      return dsdiachi_hdtb;
    },
    taodulieu_diachi_hdkh() {
      let dsdiachi_hdkh = [
        {
          HDKH_ID: 0,
          DIACHI_ID: 0,
          KINHDO: 0,
          VIDO: 0,
          NGUOI_CN: 0,
          NGAY_CN: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
          MAY_CN: "admin",
          IP_CN: "127.0.0.1",
        },
      ];
      return dsdiachi_hdkh;
    },
    async CapNhat() {
      if (!(await this.kiemtra_dl_khachhang())) return;
      if (!(await this.kiemtra_dl_thuebao())) return;
      let js_hd_kh = await this.taodulieu_hdkh();
      let js_hd_tb = await this.taodulieu_hdtb();
      let diachi = await this.taodulieu_diachi();
      let diachi_hdtb = await this.taodulieu_diachi_hdtb();
      let diachi_hdkh = await this.taodulieu_diachi_hdkh();

      let data = {
        dsdiachi: JSON.stringify(diachi).toString(),
        dsdiachi_hdtb: JSON.stringify(diachi_hdtb).toString(),
        dsdiachi_khkh: JSON.stringify(diachi_hdkh),
        dshdkh: JSON.stringify(js_hd_kh),
        dshdtb: JSON.stringify(js_hd_tb),
        dshdtb_tv: "",
        vcapnhat: 1, //1: cập nhật, 0 thêm mới
      };
      console.log(data);
      this.loading(true);
      API.fn_them_hd_xlyc_tuvan(this.axios, data)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.$toast.success(
              "Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!"
            );
            this.HienThiDanhSacHDTB();
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    clickLayDS() {
      this.HienThiDanhSacHDTB();
    },
    clickGhiLai() {
      this.CapNhat();
    },
    clickGiaoPhieu() {
      this.Giao_Phieu();
    },
    clickHuyYC() {
      this.$alert("Bạn thật sự muốn hủy yêu cầu không?", "Thông báo", {
        dangerouslyUseHTMLString: true,
        showCancelButton: true,
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Hủy bỏ",
        showCloseButton: true,
        type: "error",
      }).then(async (v) => {
        if (!v) {
          return;
        }
        this.loading(true)
        API.huy_yc_tuvan(this.axios, {
          hdkh_id: this.list_TTKhachHang.hdkh_id,
        }).then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.message ==="Success"
          ) {
            this.$toast.success(
              "Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!"
            );
            this.HienThiDanhSacHDTB();
            this.SetButton(0);
            this.Clear();
            this.loading(false)
          }
        });
      });
    },
    async KiemTraSoGT() {
      let kt = /^[a-zA-Z0-9]*$/.test(this.list_TTKhachHang.sogiayto);
      if (!kt) {
        this.$toast.error(
          "Số giấy tờ không hợp lệ.Số giấy tờ gồm các ký tự từ 0->9a->z. Hãy kiểm tra lại"
        );
        this.$refs.inputSoGT.focus();
        return false;
      }
      let ds_maKH = [];
      await API.lay_ds_makh_theo_sogt(this.axios, {
        vso_gt:this.list_TTKhachHang.sogiayto,
        vhdkh_id: this.list_TTKhachHang.hdkh_id,
        vloaihd_id: 1,
        so_gt: this.list_TTKhachHang.sogiayto,
        hdkh_id: this.list_TTKhachHang.hdkh_id,
      })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            ds_maKH = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
      if (ds_maKH.length == 1) {
        if (this.list_TTKhachHang.ma_kh != ds_maKH[0]["ma_kh"]) {
          this.$alert(
            "Số giấy tờ " +
              this.list_TTKhachHang.sogiayto +
              " đang được sử dụng bởi mã khách hàng: " +
              ds_maKH[0]["ma_kh"] +
              "\nBạn có muốn sử dụng thông tin khách hàng cũ không?",
            "Thông báo",
            {
              dangerouslyUseHTMLString: true,
              showCancelButton: true,
              confirmButtonText: "Đồng ý",
              cancelButtonText: "Hủy bỏ",
              showCloseButton: true,
              type: "warning",
            }
          ).then(async (v) => {
            if (!v) {
              return;
            }
            //Lấy thông tin cũ của khách hàng khi chưa lập hợp đồng -
            if (this.list_TTKhachHang.hdkh_id == 0) {
              this.list_TTKhachHang.ma_kh = ds_maKH[0]["ma_kh"];
              API.lay_db_theo_matb(this.axios, {
                dichvuvt_id: 0,
                donvi_dl_id: 0,
                ma_tb: this.list_TTKhachHang.ma_kh,
                phanvung_id: 26,
              }).then((response) => {
                this.Clear();
                if (
                  response.data.error_code === "BSS-00000000" &&
                  response.data.data !== undefined &&
                  response.data.data.length > 0
                ) {
                  this.HienThiTT_DanhBa(response.data.data);
                } else {
                  this.$toast.error("Không tìm thấy thông tin khách hàng!");
                }
              });
            } else {
              this.$toast.error(
                "Hợp đồng/phụ lục đã được lập, bạn không được sử dụng số giấy tờ của khách hàng khác!"
              );
            }
          });
        }
      } else if (ds_maKH.length > 1) {
        let str = "";
        for (let i = 0; i < ds_maKH.length; i++) {
          str = str + ds_maKH[i]["ma_kh"] + ",";
        }
        this.$toast.error(
          "Số giấy tờ " +
            this.list_TTKhachHang.sogiayto +
            " đang được sử dụng bởi các mã khách hàng: " +
            str.substring(0, str.length - 1) +
            "."
        ); // Đề nghị bạn ký lại hợp đồng gốc trước khi lập phụ lục.
      }
    },
    tenKHLeave(){
      this.list_TTThuebao.ten_tb = this.list_TTKhachHang.ten_kh;
    },
    ngaySinhLeave(){
      this.list_TTKhachHang.ngaysinh = this.list_TTThuebao.ngaysinh
    }
  },
};
</script>