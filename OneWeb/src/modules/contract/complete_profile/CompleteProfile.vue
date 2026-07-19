<template src="./CompleteProfile.html"></template>

 
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import DatePicker from "vue2-datepicker";
import { KIEU_DL, TRANGTHAI_PH } from "./enum";
import CompleteProfileAPI from "./API/CompleteProfileAPI";
import CommonAPI from "./API/CommonAPI";
import gridview from "@/components/Shared/gridview";
import uploadEProfile from "../profile/UploadEProfile";
import giaophieunhanvien from "../setup/DeclareLandline/popup/giaophieunhanvien";
import ModalChonDiaChi from "../setup/InstallNewSubs/Popups/popupChonDiaChi";
import BssErrorMarker from "@/components/BssErrorMarker";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import ThongTinLienHe from "../setup/ChangeContractOwner/popup/ThongTinLienHe.vue";
import traphieu from "../setup/ReturnInstallTicket/components/traphieu.vue";
import ChuyenTo from "../profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue";
import TonPhieu from "../setup/Payment/components/TonPhieu.vue";
import SearchEProfile from "../../search/subscriber/SearchEProfile"; 
import GiaoPhieuPhoiHop from "./popups/popupGiaoPhieuPhoiHop/GiaoPhieuPhoiHop.vue"; 
import LayDuLieuBC from "./popups/popupLayDuLieuBC/LayDuLieuBC.vue";

import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
//CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
//JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";
import { required, minValue, maxValue } from "vuelidate/lib/validators";
import {
  DichVuVienThong,
  TRANGBI,
  DUNG_THU,
  LoaiHopDong,
  TrangThaiHD,
  LoaiHinhTB,
  ThoiHan,
  KHOANMUC_TT,
  KieuCaiDatDVGT,
  THUESO,
  TRANGTHAI_DONGBO,
  KieuLapDat,
  LOAI_DV,
  Loai_KH,
  HINHTHUC_TC,
  KIEU_HD,
  KIEU_KH,
  PHANLOAI_KH,
  Loai_NV,
  LOAISO,
  LOAI_KV,
} from "../setup/InstallNewSubs/Enums";
import treemenuVue from "../../quantri/components/UploadFileHoSoDienTu/treemenu.vue";

//
export default {
  components: {
    breadcrumb,
    gridview,
    uploadEProfile,
    giaophieunhanvien,
    ModalChonDiaChi,
    DatePicker,
    BssErrorMarker,
    BssRequiredMarker,
    ThongTinLienHe,
    traphieu,
    ChuyenTo,
    TonPhieu,
    SearchEProfile,
    GiaoPhieuPhoiHop,
    LayDuLieuBC
  },
  name: "CompleteProfile",
  props: ["Tag"],
  validations: {
    diachiLD: {
      DIACHI: {
        required,
      },
    },
  },
  mounted() {
    CommonAPI.setAxios(this.axios);
    this.state.donvi_id = this.$root.token.getDonViID() * 1;
    this.state.nhanvien_id = this.$root.token.getNhanVienID() * 1;

    this.pageLoad(); 
  },

  data() {
    //var Tag = "5+1;41+15;14";
    var Tag = '4+1;41+15;14';
    if (this.Tag) Tag = this.Tag;
    if (this.$route.query.tag)
      Tag = decodeURI(this.$route.query.tag)
        .replaceAll("-", "+")
        .replaceAll("-", "+"); //'5-1;41-15;14'
    //var Tag = '3+1+15;14;16';

    var tthd_id = 4;
    var dsloaihd_id = 1;
    var dsdichvuvt_id = 4;
    var _flagTSKT = "";
    if (Tag != "") {
      var str = Tag.toString();
      var words = str.split("+");
      if (words.length >= 1) tthd_id = words[0];

      if (words.length >= 2) {
        dsloaihd_id = words[1];
        dsloaihd_id = dsloaihd_id.replaceAll(";", ",");
      }
      if (words.length >= 3) {
        dsdichvuvt_id = words[2];
        dsdichvuvt_id = dsdichvuvt_id.replaceAll(";", ",");
      }
      if (words.length >= 4) {
        _flagTSKT = words[3];
        _flagTSKT = _flagTSKT.replaceAll(";", ",");
      }
      if (dsdichvuvt_id == "") {
        dsdichvuvt_id = "4"; //TuanNA: Thêm code này trong trường hợp Lam ko truyền dịch vụ vt vào chức năng(Tag)
      }
    } else {
      tthd_id = TrangThaiHD.DANG_THI_CONG;
      dsloaihd_id = "1";
      dsdichvuvt_id = "4";
    }

    console.log("TAG", Tag);
    console.log("tthd_id", tthd_id);
    console.log("dsloaihd_id", dsloaihd_id);
    console.log("dsdichvuvt_id", dsdichvuvt_id);

    var currDate = new Date();

    return {
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentEvents: {
        "form-close": "popupClosed",
        acceptChangeCTV: "popupClosed",
        acceptChangeNGT: "popupClosed",
      },
      header: {
        title: "THI CÔNG DỊCH VỤ CNTT",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" },
          },
        ],
      },

      selectionOptions: { type: "Multiple" },
      target: "#app",
      dateFormat: "DD/MM/YYYY",
      datetimeFormat: "DD/MM/YYYY HH:mm",
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        ngay_cn: moment(currDate).format("DD/MM/YYYY"),
        ngay_bg: moment(currDate.setSeconds(currDate.getSeconds() + 30)).format(
          "DD/MM/YYYY"
        ),
      },
      controls: {
        tsbtnGiaoViec: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnChapNhan: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnTraPhieu: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnGiaoPhieu: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbUpHoSo: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnHuyKH: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnKichHoat: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnHoanCong: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnGiaoPhoiHop: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnHoanThienHS: {
          visible: false,
          enabled: true,
          type: "button",
        },

        tsbtnTraPhieu: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtn_KichHoat_CA: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnVatTu: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnGiaoViec_KBB: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnCreatIssue: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbThuHoiCTS: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tbs_giahan_ca: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbXemCMS: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbXemHS: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtn_Duyet_CA: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtn_khong_duyet_CA: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbTaoToken: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbThuHoiCTS: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnGiaoPhieuPhoiHop: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnHoanThanh: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnChuyenTo: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnGiaoPhieu_Ton: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnNVKT: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnXuatFile: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsddbIn: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnHen: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnFile_Upload: {
          visible: true,
          enabled: true,
          type: "button",
        },
        tsbtnNhapMoiTT: {
          visible: true,
          enabled: true,
          type: "button",
        }, 
 
      },
      state: {
        phieu_id: 0,
        isdemo: false,
        isdebug: true,
        tthd_id: tthd_id, //TrangThaiHD.DA_THI_CONG
        dsloaihd_id: dsloaihd_id,
        dsdichvuvt_id: dsdichvuvt_id,
        donvi_id: this.$root.token.getDonViID(),
        nhanvien_id: 0,
        lay_phieu_moi: true,
        loc_theo_nv: false,
        tongtien: 0,
        nhanvien: "",
        nhanvien_tiepthi: "",
        checkbox_hen: false,
        luong_id: 0,
        loaihd_id: 0,
        dichvuvt_id: 0,
        selectedPhieu: {
          ma_gd: "",
          ma_tb: "",
          loaitb_id: 89,
          phieu_id: 0,
          hdtb_id: 111111,
          hdkh_id: 0,
          nhanvien_id: 0,
          ngaygiao: ''
        },
        dtpNgayBG: {
          enabled: true,
          value: moment(new Date() + 30),
        },
        dtpNgayGV: {
          enabled: true,
          value: moment(new Date()),
        },
      },
      listbox: {
        ds_kieuhd: {
          list: [],
          value: 0,
        },
        ds_kenhthu: {
          list: [],
          value: 0,
        },
        ds_loaihd: {
          list: [],
          value: 0,
        },
        ds_quytrinh: {
          list: [],
          value: 0,
        },
        ds_trangthai_jira: {
          list: [],
          value: 0,
        },
      },
      diachiLD: {
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
      datagrid: {
        ds_hdtb: {
          list: [],
          selected_row: {},
        },
      },
      tab_dvcntt: {
        list: [],
        tt_giaoviec: {
          check_ngaygv: false,
          check_ngayht: false,
          ngay_gv: null,
          ngay_ht: null,
          ds_nguoigv: {
            list: [],
            value: 0,
          },
          tt_hdlapdat: {},
          grid_nhanvien: { list: [] },
        },
      },
      tab_it360: {
        list: [],
      },
      tab_ttgoicuoc: {
        list: [],
      },
      tab_dvgiatang: {
        list: [],
      },
      tab_tthicong: {
        list: [],
      },
      tab_ctthicong: {
        list: [],
      },
      CAConstanst: {
        isCADev: false,
        isShowLineWarning: false,
      },
    };
  },
  
  computed: {},
  validations: {
    check_ngaygv: {
      required(value) {
        return value;
      },
    },
  },  
  methods: {
    async pageLoad() {
      //Combobox Kênh Thu
      CompleteProfileAPI.CSS_KENHTHU(this.axios, {}).then((res) => {
        //console.log("CSS_KIEU_HD", response);
        this.listbox.ds_kenhthu.list = res.data.data.map((x) => ({
          id: x.KENHTHU_ID,
          text: x.KENHTHU,
        }));
        if (
          this.listbox.ds_kenhthu.list &&
          this.listbox.ds_kenhthu.list.length > 0
        )
          this.listbox.ds_kenhthu.value = this.listbox.ds_kenhthu.list[0].id;
      });

      //Load ListBox
      CompleteProfileAPI.CSS_KIEU_HD(this.axios, {}).then((res) => {
        //console.log("CSS_KIEU_HD", response);
        this.listbox.ds_kieuhd.list = res.data.data.map((x) => ({
          id: x.KIEUHD_ID,
          text: x.KIEU_HD,
        }));
        if (
          this.listbox.ds_kieuhd.list &&
          this.listbox.ds_kieuhd.list.length > 0
        )
          this.listbox.ds_kieuhd.value = this.listbox.ds_kieuhd.list[0].id;
      });

      CompleteProfileAPI.lay_ds_loai_hd(this.axios, {
        loaihd_id: this.state.dsloaihd_id,
      }).then((res) => {
        //console.log("CSS_KIEU_HD", response);
        this.listbox.ds_loaihd.list = res.data.data.map((x) => ({
          id: x.loaihd_id,
          text: x.ten_loaihd,
        }));
        if (
          this.listbox.ds_loaihd.list &&
          this.listbox.ds_loaihd.list.length > 0
        ) {
          this.listbox.ds_loaihd.value = this.listbox.ds_loaihd.list[0].id;
          this.onChange_loaihd(this.listbox.ds_loaihd.value);
        }
      });

      CompleteProfileAPI.lay_ds_nhanvien_theo_donvi(this.axios, {
        donvi_id: this.state.donvi_id,
      }).then((res) => {
        this.tab_dvcntt.tt_giaoviec.ds_nguoigv.list = res.data.data.map(
          (x) => ({ id: x.nhanvien_id, text: x.ten_nv })
        );
      });

      this.state.dtpNgayGV.enabled = false;

      await this.HienThiDanhSachHDTB();

      if (this.state.tthd_id == TrangThaiHD.DA_THI_CONG_XONG) {
        this.controls.tsbtnHoanThienHS.visible = true;
      }

      this.CREATE_TABLE_CMT();
      this.LOAD_COMBO();

      this.kt_load = true;

      if (this.state.tthd_id != 25) {
        this.controls.tsbtnGiaoPhoiHop.visible = false;
      }
    },
    CREATE_TABLE_CMT() {
      //TODO
    },
    async LOAD_COMBO() {
      await CompleteProfileAPI.lay_ds_trangthai_jira(this.axios, {
        donvi_id: this.state.donvi_id,
      }).then((res) => {
        if (
          res &&
          res.data &&
          res.data.data &&
          res.data.error_code &&
          res.data.error_code === "BSS-00000000"
        ) {
          this.listbox.ds_trangthai_jira.list = res.data.data
            .filter((x) => x.ttjr_id > 0)
            .map((x) => ({ id: x.ttjr_id, text: x.trangthai_jira }));
        }
      });
    },
    getDiaChi: function (data, datatype) {
      this[datatype].DIACHI = data.diachimoi;
      this[datatype].TINH_ID = data.tinh_id;
      this[datatype].QUAN_ID = data.quan_id;
      this[datatype].PHUONG_ID = data.phuong_id;
      this[datatype].PHO_ID = data.pho_id;
      this[datatype].AP_ID = data.ap_id;
      this[datatype].KHU_ID = data.khu_id;
      this[datatype].DAC_DIEM_ID = data.dacdiem_id;
      this[datatype].SO_NHA = data.so_nha;
      if (this.state.isdebug) console.log(this.state[datatype]);
    },
    getDiaChiLD: function (data) {
      this.getDiaChi(data, "diachiLD");
    },
    async HienThiTTHopDongTB(ds) {
      if (!ds || ds.length == 0) return;
      var ngay_cn = this.tt_nd.ngay_cn;
      var ngay_bg = this.tt_nd.ngay_bg;
      var dr = this.state.selectedPhieu;

      this.controls.tsbtnChapNhan.enabled = true;
      this.controls.tsbtnTraPhieu.enabled = true;
      this.state.hdtb_id = dr["hdtb_id"] * 1;
      this.state.hdkh_id = dr["hdkh_id"] * 1;
      this.state.loaihd_id = dr["loaihd_id"]*1;
      this.state.dichvuvt_id = dr["dichvuvt_id"]*1;
      this.state.ngaygiao = dr["ngaygiao"];
      this.state.ngaytra = this.tt_nd.ngay_cn;

      console.log('state.loaihd_id', this.state.loaihd_id);
      console.log('state.dichvuvt_id', this.state.dichvuvt_id);

      if(this.listbox.ds_quytrinh.list.includes(dr["quytrinh_id"] * 1)) 
        this.listbox.ds_quytrinh.value = dr["quytrinh_id"] * 1;

      // txtMaGD.Text = dr["ma_gd"].ToString();
      // txtSoPL.Text = dr["so_pl"].ToString();
      this.state.ma_gd = dr["ma_gd"];
      // txtMaTB.Text = dr["ma_tb"].ToString();
      // txtTenTB.Text = CommonFunction.ChuanHoaTen(dr["ten_tb"].ToString());
      this.diachiLD.DIACHI = this.ChuanHoaTen(dr["diachi_ld"]); // txtDiaChiLD.Text = CommonFunction.ChuanHoaTen(dr["diachi_ld"].ToString());
      // txtGhiChu.Text = dr["ghichu"].ToString();
      // txtNDGiao.Text = dr["nd_giao"].ToString();
      // txtTenKieuLD.Text = dr["ten_kieuld"].ToString();
      this.state.passwordMegaVNN = dr["password"];
      // txtLoaiHinh.Text = dr["loaihinh_tb"].ToString();
      // txtKhuVuc.Text = dr["ten_kv"].ToString();
      this.state.kieuld_id = dr["kieuld_id"] * 1;
      this.state.ma_tb = dr["ma_tb"];

      //Lấy thông tin liên hệ lắp đặt thuê bao
      this.tab_dvcntt.tt_giaoviec.tt_hdlapdat =
        "Liên hệ : " +
        (await CommonAPI.lay_thongtin_lh_lapdat(
          this.axios,
          this.state.hdkh_id
        ));
      if (this.tab_dvcntt.tt_giaoviec.tt_hdlapdat == "Liên hệ : ")
        this.tab_dvcntt.tt_giaoviec.tt_hdlapdat = "";

      //Tổng tiền
      CompleteProfileAPI.lay_tongtien_theo_hdkh_id(this.axios, {
        vhdkh_id: dr.hdkh_id,
      }).then((res) => {
        this.state.tongtien = 0;
        if (res.data.data.length > 0 && res.data.data[0].tongtien) {
          this.state.tongtien = res.data.data[0].tongtien;
        }
      });

      //txtNhanVien
      CompleteProfileAPI.lay_thongtin_nhanvien(this.axios, {
        nhanvien_id: this.state.selectedPhieu.nhanvien_id,
      }).then((res) => {
        this.state.nhanvien = res.data.data;
      });

      //NV Tiếp thị
      if (this.state.selectedPhieu.ctv_id) {
        CompleteProfileAPI.lay_thongtin_nhanvien(this.axios, {
          nhanvien_id: this.state.selectedPhieu.ctv_id,
        }).then((res) => {
          this.state.nhanvien_tiepthi = res.data.data;
        });
      }

      this.state.vloaitb_id = dr["loaitb_id"] * 1;

      if (
        this.listbox.ds_loaihd.value == LoaiHopDong.DI_CHUYEN ||
        this.listbox.ds_loaihd.value == LoaiHopDong.KHOIPHUC_SD ||
        (this.listbox.ds_loaihd.value == LoaiHopDong.CHUYENDOI_LH &&
          vloaitb_id != LoaiHinhTB.FSECURE)
      ) {
        //Lấy thuê bao ID đối với loại hợp đồng dịch chuyển
        this.state.thuebao_id = dr["thuebao_id"];
      }

      this.state.phieu_id = dr["phieu_id"] * 1;

      //set index combobox kênh thu
      CompleteProfileAPI.sp_lay_kenhthu_theo_hdkh(this.axios, {
        hdkh_id: this.state.selectedPhieu.hdkh_id,
      }).then((res) => {
        if (res.data.data.length > 0)
          this.listbox.ds_kenhthu.value = res.data.data[0].kenhthu_id;
      });

      //set index combobox Kiểu HĐ
      if (this.state.selectedPhieu.kieuhd_id)
        this.listbox.ds_kieuhd.value = this.state.selectedPhieu.kieuhd_id;
      else this.listbox.ds_kieuhd.value = KIEU_HD.TAI_GD;

      //checkbox_hen
      CompleteProfileAPI.kiemtra_hen_hdtb(this.axios, {
        hdtb_id: this.state.selectedPhieu.hdtb_id,
      }).then((res) => {
        if (res.data.data.length > 0)
          this.state.checkbox_hen = res.data.data[0].kq > 0;
        else this.state.checkbox_hen = false;
      });

      //Lấy thông tin trả
      CompleteProfileAPI.sp_ht_phieu_tra(this.axios, {
        hdtb_id: this.state.selectedPhieu.hdtb_id,
      }).then((res) => {
        if (res.data.data.length > 0) {
          this.state.nd_tra = res.data.data[0].nd_tra;
        }
      });

 
      //TODO:
      //txtSoDTLH.Text = dr["so_dt_lh"].ToString();
      //txtNoiDungTH.Text = dr["nd_thuchien"].ToString(); đã lấy thẳng từ selectedPhieu

      //Lấy đơn vị ID qua hợp đồng thuê bao đơn vị
      CompleteProfileAPI.lay_ds_hdtb_dv(this.axios, {
        hdtb_id: this.state.hdtb_id,
        loaidv_id: LOAI_DV.TRAM_VT,
      }).then((res) => {
        if (res.data.data.length > 0) {
          this.state.donvi_id = res.data.data[0].donvi_id;
        }
      });

      //Lấy thông tin KH Lớn
      if (dr["khlon_id"] && dr["khlon_id"].toString() != "") {
        var loaikhlon_id = dr["khlon_id"] * 1;
        this.state.loaikhlon_id = loaikhlon_id;
        var tenKhLon = "";
        await CompleteProfileAPI.lay_ten_loai_khl(this.axios, {
          lkhl_id: loaikhlon_id,
        }).then((res) => {
          tenKhLon = res.data.data;
        });

        this.state.khachhanglon = tenKhLon;
        //TODO:
        // if (loaikhlon_id != 7)
        //     txtKHLon.ForeColor = Color.Red;
        // else
        //     txtKHLon.ForeColor = Color.Black;
      } else this.state.khachhanglon = "";

      this.state.kt_status = dr["status"];

      this.controls.tsbtnHuyKH.visible = false;
      if (await this.PHAILAM(this.state.luong_id, "TSBTNHUYKH")) {
        this.controls.tsbtnHuyKH.visible = treemenuVue;
      }

      if (dr["status"] && dr["status"].toString() != "") {
        if (dr["status"] * 1 == TRANGTHAI_DONGBO.DONGBO_CM)
          this.controls.tsbtnHuyKH.enabled = true;
        else this.controls.tsbtnHuyKH.enabled = false;
      }

      //#region Phải làm
      //#START CDLH
      if (this.listbox.ds_loaihd.value == LoaiHopDong.CHUYENDOI_LH) {
        //LoaiHopDong.CHUYENDOI_LH = 6
        if ((await this.PHAILAM(this.state.luong_id, "KICHHOAT_TD")) == true) {
          if (this.state.selectedPhieu.ngay_ht) {
            this.state.dtpNgayBG.value = this.state.selectedPhieu.ngay_ht;
            this.state.dtpNgayBG.enabled = true;
            if (this.state.selectedPhieu.status == 5)
              //TRANGTHAI_DONGBO.DONGBO_CM = 5
              this.controls.tsbtnGiaoPhieu = true;
            else this.controls.tsbtnGiaoPhieu = false;
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
              //this.state.dtpNgayGV.value =  ;
            } else {
              this.state.dtpNgayBG.enabled = false;
            }
            this.controls.tsbtnGiaoPhieu = false;
          }
        }

        //Nếu phải giao phiếu
        if ((await this.PHAILAM(this.state.luong_id, "GIAOPHIEU")) == true) {
          if (dr["ngay_ht_gp"]) {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = Convert.ToDateTime(dr["ngay_ht_gp"]);

            if (
              (await this.PHAILAM(this.state.luong_id, "KICHHOAT_TD")) == true
            ) {
              if (this.state.kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString())
                this.controls.tsbtnGiaoPhieu.enabled = true;
              else this.controls.tsbtnGiaoPhieu.enabled = false;
            } else this.controls.tsbtnGiaoPhieu.enabled = true;
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = ngay_bg; //tt_nd.ngay_cn.AddSeconds(30);
            } else {
              this.state.dtpNgayBG.enabled = false;
              this.state.dtpNgayBG.value = ngay_bg; //tt_nd.ngay_cn.AddSeconds(30);
            }
            this.controls.tsbtnGiaoPhieu.enabled = false;
          }
        }

        //Nếu phải hoàn công
        if ((await this.PHAILAM(this.state.luong_id, "HOANCONG")) == false) {
          if (this.state.selectedPhieu.ngay_ht) {
            this.state.dtpNgayBG.value = this.state.selectedPhieu.ngay_ht;
            this.state.dtpNgayBG.enabled = true;
            if (this.state.selectedPhieu.status == TRANGTHAI_DONGBO.DONGBO_CM)
              //TRANGTHAI_DONGBO.DONGBO_CM = 5
              this.controls.tsbtnHoanCong.enabled = true;
            else this.controls.tsbtnHoanCong.enabled = false;
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
            } else {
              this.state.dtpNgayBG.enabled = false;
            }
            this.controls.tsbtnHoanCong.enabled = false;
          }
        }
      }
      //#END CDLH

      //#START Đặt mới + Khôi phục
      if (
        this.listbox.ds_loaihd.value == LoaiHopDong.DAT_MOI ||
        this.listbox.ds_loaihd.value == LoaiHopDong.KHOIPHUC_SD ||
        this.listbox.ds_loaihd.value == LoaiHopDong.GIAHAN
      ) {
        if ((await this.PHAILAM(this.state.luong_id, "RA_KICH_HOAT")) == true) {
          if (this.state.vloaitb_id == LoaiHinhTB.VNPT_SLL) {
            if (
              this.state.kt_status == TRANGTHAI_DONGBO.KICH_HOAT_CA.toString()
            ) {
              this.controls.tsbtnChapNhan.enabled = true;
            } else this.controls.tsbtnChapNhan.enabled = false;
          }
        }
        //-------
        if ((await this.PHAILAM(this.state.luong_id, "KICHHOAT_TD")) == true) {
          if (dr["ngay_ht_gp"] && dr["ngay_ht_gp"].toString() != "") {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = moment(dr["ngay_ht_gp"]).format(
              "DD/MM/YYYY"
            );
            if (this.state.kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString())
              this.controls.tsbtnGiaoPhieu.enabled = true;
            else this.controls.tsbtnGiaoPhieu.enabled = false;
          }
        } else {
          if ((await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false) {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = ngay_bg; //tt_nd.ngay_cn.AddSeconds(30);
          } else {
            this.state.dtpNgayBG.enabled = false;
            this.state.dtpNgayGV.value = ngay_cn;
            this.state.dtpNgayBG.value = ngay_bg; //tt_nd.ngay_cn.AddSeconds(30);
          }
          this.controls.tsbtnGiaoPhieu.enabled = false;
        }
        //-------
        //Nếu phải giao phiếu
        if ((await this.PHAILAM(this.state.luong_id, "GIAOPHIEU")) == true) {
          if (dr["ngay_ht_gp"] && dr["ngay_ht_gp"].toString() != "") {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = moment(dr["ngay_ht_gp"]).format(
              "DD/MM/YYYY"
            );
            this.controls.tsbtnGiaoPhieu.enabled = false;
            if (
              (await this.PHAILAM(this.state.luong_id, "KICHHOAT_TD")) == true
            ) {
              if (
                this.state.kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString()
              ) {
                this.controls.tsbtnGiaoPhieu.enabled = true;
              } else this.controls.tsbtnGiaoPhieu.enabled = false;
            } else {
              this.controls.tsbtnGiaoPhieu.enabled = true;
            }

            if (
              (await this.PHAILAM(this.state.luong_id, "RA_KICH_HOAT")) == true
            ) {
              if (this.state.vloaitb_id == LoaiHinhTB.CA) {
                if (
                  this.state.kt_status ==
                  TRANGTHAI_DONGBO.KICH_HOAT_CA.toString()
                ) {
                  this.controls.tsbtnGiaoPhieu.enabled = true;
                } else this.controls.tsbtnGiaoPhieu.enabled = false;
              } else {
                this.controls.tsbtnGiaoPhieu.Enabled = false;
              }
            }

            if (
              (await this.PHAILAM(this.state.luong_id, "GIA_HAN_CA")) == true
            ) {
              if (
                this.state.kt_status == TRANGTHAI_DONGBO.KICH_HOAT_CA.toString()
              ) {
                this.controls.tsbtnGiaoPhieu.enabled = true;
              } else this.controls.tsbtnGiaoPhieu.enabled = false;
            }

            if (
              (await this.PHAILAM(
                this.state.luong_id,
                "CA_DUYET_TAO_THUC_THE"
              )) == true
            ) {
              if (
                this.state.kt_status ==
                TRANGTHAI_DONGBO.DONGBO_SERVICE.toString()
              ) {
                this.controls.tsbtnGiaoPhieu.enabled = true;
              } else this.controls.tsbtnGiaoPhieu.enabled = false;
            }
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = ngay_cn;
            } else {
              this.state.dtpNgayBG.enabled = false;
              this.state.dtpNgayGV.value = ngay_cn;
              this.state.dtpNgayBG.value = ngay_bg;
            }
            this.controls.tsbtnGiaoPhieu.enabled = false;
          }
        }

        //Nếu phải hoàn công
        if ((await this.PHAILAM(this.state.luong_id, "HOANCONG")) == true) {
          var trangthai_db = "0";

          if (this.state.vloaitb_id == LoaiHinhTB.FSECURE)
            trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM.toString();

          if (
            this.state.vloaitb_id == LoaiHinhTB.CA &&
            CAConstanst.isCADev == true
          ) {
            trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM.toString();
          }

          if (
            dr["ngay_ht_gp"] &&
            dr["ngay_ht_gp"].toString() != "" &&
            this.state.kt_status == trangthai_db
          ) {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = moment(dr["ngay_ht_gp"]).format(
              "DD/MM/YYYY"
            );
            this.controls.tsbtnHoanCong.enabled = true;
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = ngay_bg;
            } else {
              this.state.dtpNgayBG.enabled = false;
              this.state.dtpNgayGV.value = ngay_cn;
              this.state.dtpNgayBG.value = ngay_bg;
            }
            this.controls.tsbtnHoanCong.enabled = false;
          }
          //if (kieuld_id == 13177)
          //{
          //    tsbtnHoanCong.Enabled = true;
          //}
        }

        if (await this.PHAILAM(this.state.luong_id, "HOANTHIENHOSO") == true) {
          if (dr["ngay_ht_gp"] && dr["ngay_ht_gp"].toString() != "") {
            this.state.dtpNgayBG.Enabled = true;
            this.state.dtpNgayBG.Value = moment(dr["ngay_ht_gp"]).format(
              "DD/MM/YYYY"
            );
            this.state.tsbtnHoanThienHS.enabled = true;
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = ngay_bg;
            } else {
              this.state.dtpNgayBG.enabled = false;
              this.state.dtpNgayGV.value = ngay_cn;
              this.state.dtpNgayBG.value = ngay_bg;
            }
            this.controls.tsbtnHoanThienHS.enabled = false;
          }
        }
      }
      //#END Đặt mới + Khôi phục

      //#region Di Chuyển
      else if (this.listbox.ds_loaihd.value == LoaiHopDong.DI_CHUYEN) {
        if ((await this.PHAILAM(this.state.luong_id, "KICHHOAT_TD")) == true) {
          if (dr["ngay_ht_gp"] && dr["ngay_ht_gp"].toString() != "") {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = moment(dr["ngay_ht_gp"]).format(
              "DD/MM/YYYY"
            );
            if (this.state.kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString())
              this.controls.tsbtnGiaoPhieu.enabled = true;
            else this.controls.tsbtnGiaoPhieu.enabled = false;
          }
        } else {
          if ((await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false) {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = ngay_bg;
          } else {
            this.state.dtpNgayBG.enabled = false;
            this.state.dtpNgayGV.value = ngay_cn;
            this.state.dtpNgayBG.value = ngay_bg;
          }
          this.controls.tsbtnGiaoPhieu.enabled = false;
        }

        //Nếu phải giao phiếu
        if ((await this.PHAILAM(this.state.luong_id, "GIAOPHIEU")) == true) {
          if (dr["ngay_ht_gp"]) {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = Convert.ToDateTime(dr["ngay_ht_gp"]);

            if (
              (await this.PHAILAM(this.state.luong_id, "KICHHOAT_TD")) == true
            ) {
              if (this.state.kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString())
                this.controls.tsbtnGiaoPhieu.enabled = true;
              else this.controls.tsbtnGiaoPhieu.enabled = false;
            } else this.controls.tsbtnGiaoPhieu.enabled = true;
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = ngay_cn; //tt_nd.ngay_cn.AddSeconds(30);
            } else {
              this.state.dtpNgayBG.enabled = false;
              this.state.dtpNgayBG.value = ngay_bg; //tt_nd.ngay_cn.AddSeconds(30);
            }
            this.controls.tsbtnGiaoPhieu.enabled = false;
          }
        }

        //Nếu phải hoàn công
        if ((await this.PHAILAM(this.state.luong_id, "HOANCONG")) == true) {
          var trangthai_db = "";
          if (dichvuvt_id == DichVuVienThong.MEGA_EYES) trangthai_db = "";
          else if (this.state.vloaitb_id == LoaiHinhTB.IOFFICE)
            trangthai_db = "0";
          else trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM.toString();

          if (
            this.state.vloaitb_id == LoaiHinhTB.CA &&
            CAConstanst.isCADev == true
          ) {
            trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM.toString();
          }

          if (
            dr["ngay_ht_gp"] &&
            dr["ngay_ht_gp"].toString() != "" &&
            this.state.kt_status == trangthai_db
          ) {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = moment(dr["ngay_ht_gp"]).format(
              "DD/MM/YYYY"
            );
            this.controls.tsbtnHoanCong.enabled = true;
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = ngay_bg;
            } else {
              this.state.dtpNgayBG.enabled = false;
              this.state.dtpNgayGV.value = ngay_cn;
              this.state.dtpNgayBG.value = ngay_bg;
            }
            this.controls.tsbtnHoanCong.enabled = false;
          }
        }

        if (
          (await this.PHAILAM(this.state.luong_id, "HOANTHIENHOSO")) == true
        ) {
          if (dr["ngay_ht_gp"] && dr["ngay_ht_gp"].toString() != "") {
            this.state.dtpNgayBG.Enabled = true;
            this.state.dtpNgayBG.Value = moment(dr["ngay_ht_gp"]).format(
              "DD/MM/YYYY"
            );
            this.state.tsbtnHoanThienHS.enabled = true;
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = ngay_bg;
            } else {
              this.state.dtpNgayBG.enabled = false;
              this.state.dtpNgayGV.value = ngay_cn;
              this.state.dtpNgayBG.value = ngay_bg;
            }
            this.controls.tsbtnHoanThienHS.enabled = false;
          }
        }
      }
      //#endregion Di chuyển

      //#region thu_hoi_ca
      else if (
        this.listbox.ds_loaihd.value == LoaiHopDong.CHAMDUT_SD ||
        this.listbox.ds_loaihd.value == LoaiHopDong.THAY_DOI_DV
      ) {
        if ((await this.PHAILAM(this.state.luong_id, "KICHHOAT_TD")) == true) {
          if (dr["ngay_ht_gp"] && dr["ngay_ht_gp"].toString() != "") {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = moment(dr["ngay_ht_gp"]).format(
              "DD/MM/YYYY"
            );
            if (this.state.kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString())
              this.controls.tsbtnGiaoPhieu.enabled = true;
            else this.controls.tsbtnGiaoPhieu.enabled = false;
          }
        } else {
          if ((await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false) {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = ngay_bg;
          } else {
            this.state.dtpNgayBG.enabled = false;
            this.state.dtpNgayGV.value = ngay_cn;
            this.state.dtpNgayBG.value = ngay_bg;
          }
          this.controls.tsbtnGiaoPhieu.enabled = false;
        }

        //Nếu phải giao phiếu
        if ((await this.PHAILAM(this.state.luong_id, "GIAOPHIEU")) == true) {
          if (dr["ngay_ht_gp"]) {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = Convert.ToDateTime(dr["ngay_ht_gp"]);

            if (
              (await this.PHAILAM(this.state.luong_id, "KICHHOAT_TD")) == true
            ) {
              if (this.state.kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString())
                this.controls.tsbtnGiaoPhieu.enabled = true;
              else this.controls.tsbtnGiaoPhieu.enabled = false;
            } else this.controls.tsbtnGiaoPhieu.enabled = true;
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = ngay_cn; //tt_nd.ngay_cn.AddSeconds(30);
            } else {
              this.state.dtpNgayBG.enabled = false;
              this.state.dtpNgayBG.value = ngay_bg; //tt_nd.ngay_cn.AddSeconds(30);
            }
            this.controls.tsbtnGiaoPhieu.enabled = false;
          }
        }

        //Nếu phải hoàn công
        if ((await this.PHAILAM(this.state.luong_id, "HOANCONG")) == true) {
          var trangthai_db = "";
          if (dichvuvt_id == DichVuVienThong.MEGA_EYES) trangthai_db = "";
          else if (this.state.vloaitb_id == LoaiHinhTB.IOFFICE)
            trangthai_db = "0";
          else trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM.toString();

          if (
            dr["ngay_ht_gp"] &&
            dr["ngay_ht_gp"].toString() != "" &&
            this.state.kt_status == trangthai_db
          ) {
            this.state.dtpNgayBG.enabled = true;
            this.state.dtpNgayBG.value = moment(dr["ngay_ht_gp"]).format(
              "DD/MM/YYYY"
            );
            this.controls.tsbtnHoanCong.enabled = true;
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = ngay_bg;
            } else {
              this.state.dtpNgayBG.enabled = false;
              this.state.dtpNgayGV.value = ngay_cn;
              this.state.dtpNgayBG.value = ngay_bg;
            }
            this.controls.tsbtnHoanCong.enabled = false;
          }
        }

        if (
          (await this.PHAILAM(this.state.luong_id, "HOANTHIENHOSO")) == true
        ) {
          if (dr["ngay_ht_gp"] && dr["ngay_ht_gp"].toString() != "") {
            this.state.dtpNgayBG.Enabled = true;
            this.state.dtpNgayBG.Value = moment(dr["ngay_ht_gp"]).format(
              "DD/MM/YYYY"
            );
            this.state.tsbtnHoanThienHS.enabled = true;
          } else {
            if (
              (await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false
            ) {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = ngay_bg;
            } else {
              this.state.dtpNgayBG.enabled = false;
              this.state.dtpNgayGV.value = ngay_cn;
              this.state.dtpNgayBG.value = ngay_bg;
            }
            this.controls.tsbtnHoanThienHS.enabled = false;
          }
        }
      }

      //#endregion thu_hoi_ca

      if ((await this.PHAILAM(this.state.luong_id, "KICHHOAT_TD")) == true) {
        if (this.state.kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString())
          this.controls.tsbtnKichHoat.enabled = false;
        else this.controls.tsbtnKichHoat.enabled = true;
      } else this.controls.tsbtnKichHoat.enabled = false;

      if ((await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false) {
        if (
          (await this.PHAILAM(this.state.luong_id, "HT_NGAY_GV_NGAY_HT")) ==
          false
        ) {
          this.state.dtpNgayBG.enabled = false;
          this.state.dtpNgayGV.enabled = false;
        } else {
          this.state.dtpNgayBG.enabled = true;
          this.state.dtpNgayGV.enabled = true;
        }
      } else {
        this.state.dtpNgayBG.enabled = true;
        this.state.dtpNgayGV.enabled = true;
      }
      //#end region Phải làm

      //#region chuan_hoa_ca
      if (this.state.loaihd_id == LoaiHopDong.KY_LAI_HD_GOC) {
        if (this.listbox.ds_quytrinh.value * 1 == 8806) {
          if (
            (await this.PHAILAM(this.state.luong_id, "HOANTHIENHOSO")) == true
          ) {
            if (dr["ngay_ht_gp"] && dr["ngay_ht_gp"].toString() != "") {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = moment(dr["ngay_ht_gp"]).format(
                "DD/MM/YYYY"
              );
              this.controls.tsbtnHoanThienHS.enabled = true;
            }
          }
          //neu phai giao phieu
          if ((await this.PHAILAM(this.state.luong_id, "GIAOPHIEU")) == true) {
            if (dr["ngay_ht_gp"] && dr["ngay_ht_gp"].toString() != "") {
              this.state.dtpNgayBG.enabled = true;
              this.state.dtpNgayBG.value = moment(dr["ngay_ht_gp"]).format(
                "DD/MM/YYYY"
              );
              if (
                (await this.PHAILAM(
                  this.state.luong_id,
                  "CA_DUYET_TAO_THUC_THE"
                )) == true
              ) {
                if (
                  this.state.kt_status ==
                  TRANGTHAI_DONGBO.DONGBO_SERVICE.toString()
                )
                  this.controls.tsbtnGiaoPhieu.enabled = true;
                else this.controls.tsbtnGiaoPhieu.enabled = false;
              } else this.controls.tsbtnGiaoPhieu.enabled = true;
            } else {
              this.controls.tsbtnGiaoPhieu.enabled = false;
            }
          }
        }
      }

      //#region Ban_ThietBi_CNTT
      if ((await this.PHAILAM(this.state.luong_id, "HOANTHIENHOSO")) == true) {
        if (dr["ngay_ht_gp"] && dr["ngay_ht_gp"].toString() != "") {
          this.state.dtpNgayBG.enabled = true;
          this.state.dtpNgayBG.value = moment(dr["ngay_ht_gp"]).format(
            "DD/MM/YYYY"
          );
          this.controls.tsbtnHoanThienHS.enabled = true;
        }
      }
      //#endregion Ban_ThietBi_CNTT
      //#endregion chuan_hoa_ca

      // hoangpkn : 05/08/2015 : Bổ sung chặn theo yc a Thắng
      if (
        this.state.loaihd_id == LoaiHopDong.DAT_MOI ||
        this.state.loaihd_id == LoaiHopDong.DI_CHUYEN
      ) {
        if (
          (await this.PHAILAM(this.state.luong_id, "KICHHOAT_TD")) &&
          (await this.PHAILAM(this.state.luong_id, "CAPNHAT_CAP")) == true
        ) {
          if (dr["chan"] && dr["chan"].toString() == "1")
            this.controls.tsbtnKichHoat.enabled = false;
          else {
            if (this.state.kt_status == TRANGTHAI_DONGBO.DONGBO_CM.toString())
              this.controls.tsbtnKichHoat.enabled = false;
            else this.controls.tsbtnKichHoat.enabled = true;
          }
        }
      }
      // end hoàng

      // hoangpkn : 08/04/2021. Kiểm tra xem có phải phiếu phối hợp không ? Phiếu phối hợp thì chỉ được ấn nút Hoàn thành. update ttph_id = 4
      if (
        (await this.PHAILAM(this.state.luong_id, "HOANTHANH_PHIEU_CNTT")) ==
        true
      ) {
        var cnt =
          (await this.kt_phieu_phoihop(
            this.state.phieu_id,
            this.state.hdtb_id
          )) * 1;
        if (cnt == 0) {
          // ko phải phiếu phối hợp --> ẩn nút Hoàn thành đi
          this.controls.tsbtnHoanThanh.enabled = false;
        } // Nếu là phiếu phối hợp: hiển thị nút Hoàn thành + ẩn nút Giao phiếu, Hoàn Công, Hoàn thiện
        else {
          this.controls.tsbtnGiaoPhieu.enabled = false;
          this.controls.tsbtnHoanCong.enabled = false;
          this.controls.tsbtnHoanThienHS.enabled = false;
          if (dr["ngay_ht_gp"] && dr["ngay_ht_gp"].toString() != "")
            this.controls.tsbtnHoanThanh.enabled = true;
          else this.controls.tsbtnHoanThanh.enabled = false;
        }
      }
      // end hoàng

      //Lấy danh sách thuê bao lắp kèm
      if (this.state.selectedPhieu.madoicap) {
        var dataPost = {
          vhdtb_id: this.state.hdtb_id,
          vmadoicap: this.state.selectedPhieu.madoicap,
        };
        CompleteProfileAPI.lay_thuebao_lapkem(this.axios, dataPost).then(
          (res) => {
            this.state.thuebao_lapkem = res.data.data;
          }
        );
      }

      //#region Hiển thị thông tin ra
      this.hienthi_tab_dvcntt();
      //this.hienthi_tab_it360();
      //this.hienthi_tab_ttgoicuoc();
      //this.hienthi_tab_tthicong();
      //this.hienthi_tab_ctiet_dhthicong();
      //#end region Hiển thị thông tin ra

      await this.HienThiDSNV(
        await CommonAPI.LAY_DS_NHANVIEN_THEO_PHIEU_ID(
          this.state.phieu_id,
          this.state.nhanvien_id,
          2
        )
      );
      await this.HienThiDVGT(
        await CommonAPI.LAY_DS_DANGKY_DVGT(this.state.hdtb_id)
      );
    },

    async HienThiDSNV(ds) {
      if (ds.length > 0) {
        this.state.dtpNgayGV.enabled = true;
        this.state.dtpNgayGV.value = ds[0]["ngaygiao"].toString();

        this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list = ds.map((x) => ({
          id: x.nhanvien_id,
          text: x.ten_nv,
        }));
        this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value = 0;
        if(ds[0]["nhanvien_giao_id"])
         this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value =  ds[0]["nhanvien_giao_id"].toString() * 1;

        this.state.nhanvien_tc_id = 0;
        if(ds[0]["nhanvien_th_id"])
          this.state.nhanvien_tc_id = ds[0]["nhanvien_th_id"].toString() * 1;
        this.state.congviec_th = 0;
        if(ds[0]["nhiemvu"])
          this.state.congviec_th = ds[0]["nhiemvu"].toString();
      } else {
        this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list = null;
        if ((await this.PHAILAM(this.state.luong_id, "GIAOVIEC")) == false) {
          this.state.dtpNgayGV.enabled = true;
          this.state.dtpNgayGV.value = this.tt_nd.ngay_cn;
        } else {
          this.state.dtpNgayGV.enabled = false;
          this.state.dtpNgayGV.value = this.tt_nd.ngay_cn;
        }
        this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value = this.tt_nd.nhanvien_id;
        this.state.nhanvien_tc_id = -1;
        this.state.congviec_th = "";
      }
    },

    async HienThiDVGT(ds) {
      if (ds.length > 0) {
        this.tab_dvgiatang.list = ds;
      } else {
        this.tab_dvgiatang.list = null;
      }
    },

    async PHAILAM(luong_id, code) {
      var phailam = false;
      await CompleteProfileAPI.phailam(this.axios, {
        hdtb_id: luong_id,
        code: code,
      }).then((res) => {
        phailam = res.data.data == "1";
      });

      return phailam;
    },

    //Kiểm tra xem có phải phiếu phối hợp không
    async kt_phieu_phoihop(phieu_id, hdtb_id) {
      var kieuphoihop = 0;
      await CompleteProfileAPI.kt_phieu_phoihop(this.axios, {
        vphieu_id: phieu_id,
        vhdtb_id: hdtb_id,
      }).then((res) => {
        kieuphoihop = res.data.data;
      });
      return kieuphoihop;
    },

    async HienThiDanhSachHDTB() {
      //this.loading(true);
      var dataPost = {
        vtthd_id: 5,
        vnhanvien_id: 6958,
        vma_gd: 0,
        vphieu_id: 0,
        vloaihd_id: 1,
        vkieu_id: 1,
        vhuonggiao_id: 0,
        vloc_nv: 0,
        vloai_id: 0,
      };

      this.state.loc_nv = 0;
      if ((await this.PHAILAM(this.state.luong_id, "LOCTHEO_NV")) == true)
        this.state.loc_nv = 1;

      dataPost.vtthd_id = this.state.tthd_id * 1;
      dataPost.vnhanvien_id = this.state.nhanvien_id * 1;
      dataPost.vma_gd = 0; //this.state.ma_gd;
      dataPost.vloaihd_id = this.listbox.ds_loaihd.value * 1;
      dataPost.vkieu_id = -1;
      dataPost.vloai_id =
        String(this.state.lay_phieu_moi).toLowerCase() === "true" ? 1 : 0;
      dataPost.vloc_nv =
        String(this.state.loc_nv).toLowerCase() === "true" ? 1 : 0;

      // if (await this.PHAILAM(this.state.luong_id, "GIAOVIEC"))
      // {
      //     this.state.dtpNgayBG.enabled = true;
      //     this.state.dtpNgayGV.enabled = true;
      //     //dtpNgayBG.Value = tt_nd.ngay_cn.AddSeconds(30);
      //     //dtpNgayGV.Value = tt_nd.ngay_cn;
      // }

      CompleteProfileAPI.lay_ds_phieu_hc_dv_cntt_v2(this.axios, dataPost).then(
        (res) => {
          this.loading(false);
          this.clearData();
          this.datagrid.ds_hdtb.list = res.data.data.returnds;

          // if(this.datagrid.ds_hdtb.list.length > 0)
          // {
          //   this.controls.tsbtnGiaoViec.enabled = true;
          //   this.controls.tsbtnChapNhan.enabled = true;
          //   this.controls.tsbtnGiaoPhieu.enabled = true;
          //   this.controls.tsbUpHoSo.enabled = true;
          //   //this.controls.tsbtnKichHoatVisa.enabled = true;
          //   this.controls.tsbtnHoanCong.enabled = true;
          // }
        }
      );

      //this.loading(false);
    },
    clearData: function () {
      this.state.phieu_id = 0;
      this.state.selectedPhieu = {};
      this.state.tongtien = 0;
      this.state.nhanvien = "";
      this.diachiLD = {};
      this.tab_dvcntt.tt_giaoviec.tt_hdlapdat = null;

      this.tab_dvcntt.list = [];
      this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list = [];
      this.state.dtpNgayGV.enabled = false;
      this.state.dtpNgayBG.enabled = false;
      this.state.dtpNgayGV.value = null;
      this.state.dtpNgayBG.value = null;

      this.tab_it360.list = [];
      this.tab_ttgoicuoc.list = [];
      this.tab_dvgiatang.list = [];
      this.tab_tthicong.list = [];
      this.tab_ctthicong.list = [];

      this.state.khachhanglon = "";
    },
    hienthi_tab_dvcntt: function () {
      var dataPost = {
        vloaitb_id: this.state.selectedPhieu.loaitb_id,
        vten_bang: "HDTB_CNTT",
        vopt_list: "00000",
      }; 
      CompleteProfileAPI.lay_danhsach_thuoctinh_v3_tichhop_sql(
        this.axios,
        dataPost
      ).then((res) => {
        this.tab_dvcntt.list = res.data.data.filter(
          (v, i, a) => a.findIndex((t) => t.field_name === v.field_name) === i
        );
        this.tab_dvcntt.list.forEach((item) => {
          if (item.result_sql && item.result_sql.length > 0)
            item.result_sql = item.result_sql.map((x) => ({
              id: x[item.parent_keyfield],
              text: x[item.parent_descfield],
            }));
        });

        //Lấy danh sách HDTB CNTT theo hdtb_id
        CompleteProfileAPI.lay_ds_hdtb_cntt_theo_hdtb_id(this.axios, {
          in_hdtb_id: this.state.selectedPhieu.hdtb_id,
        }).then((res) => {
          //this.tab_dvcntt.list

          if (
            this.tab_dvcntt.list &&
            this.tab_dvcntt.list.length > 0 &&
            res.data.message == "Success" &&
            res.data.data.length > 0
          )
            this.tab_dvcntt.list.forEach((item) => {
              if (item.field_name.toLowerCase() == "ma_tb") {
                item.field_value = this.state.selectedPhieu.ma_tb;
              } else {
                item.field_value = res.data.data[0][item.field_name.toLowerCase()];
                if(item.data_type == 2)
                  item.field_value = moment(item.field_value, 'YYYY-MM-DD').format('DD/MM/YYYY');
              }
            });
        });

        if (this.state.isdebug)
          console.log("tab_dvcntt.list", this.tab_dvcntt.list);
      });

      dataPost = {
        phieu_id: this.state.selectedPhieu.phieu_id,
        nhanvien_id: this.state.nhanvien_id,
        kieu_id: 2,
      };

      //  CompleteProfileAPI.lay_ds_nhanvien_theo_phieu_id(this.axios, dataPost).then(res=>{
      //    this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list = res.data.data;
      //     if(this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list && this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list.length > 0){
      //       this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value = this.tab_dvcntt.tt_giaoviec.grid_nhanvien.list[0].nhanvien_giao_id;
      //     }
      //  });

      //  dataPost = {
      //       "hdkh_id": this.state.selectedPhieu.hdkh_id
      //   }
      //  CompleteProfileAPI.lay_thongtin_lh_lapdat(this.axios, dataPost).then(res=>{
      //    this.tab_dvcntt.tt_giaoviec.tt_hdlapdat = res.data.data;
      //    if(this.tab_dvcntt.tt_giaoviec.tt_hdlapdat && this.tab_dvcntt.tt_giaoviec.tt_hdlapdat != '{}')
      //     this.tab_dvcntt.tt_giaoviec.tt_hdlapdat = "Liên hệ : " + this.tab_dvcntt.tt_giaoviec.tt_hdlapdat;
      //  });
    },

    hienthi_tab_dvgiatang: function () {
      var dataPost = {
        vhdtb_id: this.state.selectedPhieu.hdtb_id,
      };
      CompleteProfileAPI.lay_ds_dangky_dvgt(this.axios, dataPost).then(
        (res) => {
          this.tab_dvgiatang.list = res.data.data;
          if (this.state.isdebug)
            console.log("tab_ttgoicuoc.list", this.tab_dvgiatang.list);
        }
      );
    },

    async HienThiGiaoDien(luong_id) {

      return;//tạm disable chờ dữ liệu theo luồng thật mới test dc
      this.controls.tsbtnTraPhieu.visible = false;
      this.controls.tsbtnHoanCong.visible = false;
      this.controls.tsbtnHoanThienHS.visible = false;
      this.controls.tsbtnGiaoPhieu.visible = false;
      this.controls.tsbtnGiaoViec.visible = false;
      this.controls.tsbtnKichHoat.visible = false;
      this.controls.tsbtn_KichHoat_CA.visible = false;
      this.controls.tsbtnVatTu.visible = false;
      this.controls.tsbtnGiaoViec_KBB.visible = false;
      this.controls.tsbtnCreatIssue.visible = false;
      this.controls.tsbThuHoiCTS.visible = false;
      this.controls.tbs_giahan_ca.visible = false;
      this.controls.tsbXemCMS.visible = false;
      this.controls.tsbXemHS.visible = false;
      this.controls.tsbtn_Duyet_CA.visible = false;
      this.controls.tsbtn_khong_duyet_CA.visible = false;
      this.controls.tsbtnGiaoPhieuPhoiHop.visible = false;
      this.controls.tsbtnHoanThanh.visible = false;

      await CompleteProfileAPI.lay_luong_thaotac(this.axios, {
        luong_id: luong_id,
      }).then((res) => {
        this.state.luong_thaotac = res.data.data;
      });

      if (this.state.luong_thaotac && this.state.luong_thaotac.length > 0) {
        this.state.luong_thaotac.forEach((item) => {
          CompleteProfileAPI.lay_ct_thaotac_control(this.axios, {
            thaotac_id: item.thaotac_id,
          }).then((res) => {
            res.data.data.forEach((row) => {
              this.controls[row.control_name].visible = (row.enable == 1);
              console.log(row.control_name, this.controls[row.control_name]);
            });
          });
        });
      }
    },

    //CÁC SỰ KIỆN
    onChange_loaihd(e) {
      //console.log(e);
      var loaihd_id = parseInt(e);
      var postData = {
        dsloaihd_id: loaihd_id,
        dsdichvuvt_id: this.state.dsdichvuvt_id,
        dstthd_id: this.state.tthd_id,
        kieu: 0,
      };

      if (
        loaihd_id == LoaiHopDong.DAT_MOI ||
        loaihd_id == LoaiHopDong.DI_CHUYEN ||
        loaihd_id == LoaiHopDong.CHUYENDOI_LH
      ) {
        this.controls.tsbtnHuyKH.visible = true;
        this.controls.tsbtnHuyKH.enabled = true;
      }

      this.loading(true);
      CompleteProfileAPI.ds_quytrinh(this.axios, postData).then((res) => {
        this.loading(false);
        this.listbox.ds_quytrinh.list = res.data.data.map((x) => ({
          id: x.quytrinh_id,
          text: x.quytrinh,
        }));
        if (
          this.listbox.ds_quytrinh.list &&
          this.listbox.ds_quytrinh.list.length > 0
        ) {
          this.listbox.ds_quytrinh.value = this.listbox.ds_quytrinh.list[0].id;
          this.onChange_quytrinh(this.listbox.ds_quytrinh.value);
        }
        this.HienThiDanhSachHDTB();
      });
    },
    async onChange_quytrinh(e) {
      if (
        !this.listbox.ds_quytrinh.list ||
        this.listbox.ds_quytrinh.list.length <= 0
      )
        return;
      if (
        !this.listbox.ds_quytrinh.value ||
        this.listbox.ds_quytrinh.value * 1 <= 0
      )
        return;

      this.state.quytrinh_id = this.listbox.ds_quytrinh.value * 1;

      var postData = {
        quytrinh_id: e,
        tthd_id: this.state.tthd_id,
      };

      this.loading(true);
      await CompleteProfileAPI.sp_lay_huonggiao_theo_quytrinh(
        this.axios,
        postData
      ).then((res) => {
        this.loading(false);
        this.state.ds_huonggiao = res.data.data;
      });

      if (this.state.ds_huonggiao && this.state.ds_huonggiao.length > 0) {
        this.state.luong_id = this.state.ds_huonggiao[0].luong_id;
        this.state.huonggiao_id = this.state.ds_huonggiao[0].huonggiao_id;
        var tenhg = this.state.ds_huonggiao[0].huonggiao;
        this.header.title = tenhg;
        this.HienThiGiaoDien(this.state.luong_id);

        this.state.loc_nv = 0;
        if (await this.PHAILAM(this.state.luong_id, "LOCTHEO_NV" == true)) {
          this.state.loc_nv = 1;
        }
        if (await this.PHAILAM(this.state.luong_id, "GIAOVIEC" == false)) {
          this.state.dtpNgayBG.enabled = true;
          this.state.dtpNgayGV.enabled = true;
          this.state.dtpNgayBG.value = this.tt_nd.ngay_bg;
          this.state.dtpNgayGV.value = this.tt_nd.ngay_cn;
        }
      } else {
        this.$toast.error(
          "Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại"
        );
        return;
      }
    },
    onChange_phieumoi: function (e) {
      this.loading(true);
      this.HienThiDanhSachHDTB();
    },
    onChange_loctheonv: function (e) {
      console.log(e);
    },
    btnLayTTMoi_Click: function (e) {
      this.loading(true);
      this.HienThiDanhSachHDTB();
    },
    async KiemTra_DL(luong_id) {
      if (this.datagrid.ds_hdtb.list < 0) {
        this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ");
        return false;
      }

      var grid = this.$refs.gv_DanhSach;
      var seletedRows = grid.getSelectedRecords();

      if (seletedRows.length <= 0) {
        this.$toast.error("Bạn chưa chọn thuê bao! ");
        return false;
      }

      this.$v.$touch();
      let errorMessage = [];

      if (
        this.state.dtpNgayGV.enabled == false ||
        this.state.dtpNgayGV.value == null
      ) {
        //this.$refs.loaiCard.focus()
        this.$toast.error("Hãy chọn ngày giao việc !");
        return false;
      }

      if (
        this.state.dtpNgayBG.enabled == false ||
        this.state.dtpNgayBG.value == null
      ) {
        //this.$refs.loaiCard.focus()
        this.$toast.error("Hãy chọn ngày hoàn thành !");
        return false;
      }

      if (String(this.state.dtpNgayBG.enabled).toLowerCase() == "true") {
        var diff = this.state.dtpNgayBG.value - this.state.dtpNgayGV.value;
        if (diff <= 0) {
          this.$toast.error(
            "Ngày giao việc không được lớn hơn ngày hoàn thành!"
          );
          return false;
        }
      }

      var thangnamsys = moment(
        this.state.selectedPhieu.thangnam_sys,
        "DD/MM/YYYY"
      );
      if (this.listbox.ds_loaihd.value != LoaiHopDong.KHOIPHUC_SD) {
        if (
          parseInt(moment(this.state.dtpNgayBG.value).format("yyyyMM")) <
          parseInt(moment(thangnamsys).format("yyyyMM"))
        ) {
          this.$toast.error(
            "Tháng hoàn thành không được nhỏ hơn tháng hiện tại !"
          );
          return false;
        }
      }

      if (
        parseInt(moment(this.state.dtpNgayBG.value).format("yyyyMMdd")) <
        parseInt(moment(thangnamsys).format("yyyyMMdd"))
      ) {
        this.$toast.error(
          "Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại"
        );
        return false;
      }

      // diff = new Date() - this.state.dtpNgayGV.value;
      // if(diff <= 0){
      //   errorMessage.push("Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại!")
      // }
      // diff = new Date() - this.state.dtpNgayBG.value;
      // if(diff <= 0){
      //   errorMessage.push("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại!")
      // }

      // if (errorMessage.length > 0) {
      //     this.$toast.error(errorMessage.join('\n'))
      //     return false
      // }else {
      //     return true
      // }

      return true;
    },
    CheckDataError(NetItems, ItemCount, sTableName, sKeyField, sMaDoiTuong) {
      try {
        var flag = false;
        var i = 0;
        var TempNum = 0;
        for (var k = 0; k < ItemCount; k++) {
          switch (NetItems[k].iDataType) {
            case KIEU_DL.CST_TEXT:
              if (NetItems[k].bIsNotNull && NetItems[k].sValue == "") {
                this.$toast.error("Phải nhập " + NetItems[k].sCaption + ".");
                return true;
              }
              i = NetItems[k].sValue.Length;
              if (NetItems[k].bIsNotNull) {
                if (i == 0) {
                  this.$toast.error("Phải nhập " + NetItems[k].sCaption + ".");
                  return true;
                }
              }
              if (!(!NetItems[k].sMaxValue && !NetItems[k].sMinvalue)) {
                if (
                  i > parseInt(NetItems[k].sMaxValue) ||
                  i < parseInt(NetItems[k].sMinvalue)
                ) {
                  if (
                    parseInt(NetItems[k].sMaxValue) > 0 &&
                    parseInt(NetItems[k].sMinvalue) > 0
                  )
                    if (parseInt(NetItems[k].sMaxValue) > 0) {
                      this.$toast.error(
                        NetItems[k].sCaption +
                          " không thể lớn hơn " +
                          NetItems[k].sMaxValue +
                          " ký tự."
                      );
                      return true;
                    } else {
                      this.$toast.error(
                        "Phải nhập " + NetItems[k].sCaption + "."
                      );
                      return true;
                    }
                }
              }
              break;
            case KIEU_DL.CST_NUMBER:
              if (
                NetItems[k].sValue.Length > 0 &&
                !this.CheckIsNumber(NetItems[k].sValue)
              ) {
                this.$toast.error(NetItems[k].sCaption + " phải là kiểu số.");
                return true;
              }
              TempNum = parseFloat(
                !NetItems[k].sValue ? "-1" : NetItems[k].sValue
              );

              if (!NetItems[k].bIsNotNull && !NetItems[k].sValue) {
                this.$toast.error("Phải nhập " + NetItems[k].sCaption + ".");
                return true;
              }
              i = NetItems[k].sValue.Length;
              if (!(!NetItems[k].sMaxValue && !NetItems[k].sMinvalue)) {
                if (
                  i > parseInt(NetItems[k].sMaxValue) ||
                  i > parseInt(NetItems[k].sMinvalue)
                ) {
                  if (
                    parseInt(NetItems[k].sMaxValue) > 0 &&
                    parseInt(NetItems[k].sMinvalue) > 0
                  )
                    if (parseInt(NetItems[k].sMaxValue) > 0) {
                      this.$toast.error(
                        NetItems[k].sCaption +
                          " không thể lớn hơn " +
                          NetItems[k].sMaxValue +
                          " ký tự."
                      );
                      return true;
                    }
                }
              }
              break;
            case KIEU_DL.CST_DATE:
              if (!NetItems[k].sValue && NetItems[k].bIsNotNull) {
                this.$toast.error(NetItems[k].sCaption + " phải có giá trị.");
                return true;
              }
              break;
            default:
              flag = false;
              break;
          }
          if (flag) return flag;
        }
        return false;
      } catch (e) {
        console.log(e);
        //this.$toast.error("Có lỗi: " + e);
        return true;
      }
    },
    CheckIsNumber: function (input) {
      return !isNaN(input);
    },
    async CapNhat() {
      var postData = {
        phieu_id: 12045046,
        hdtb_id: 6058534,
        loaihd_id: 1,
        luong_id: 414,
        json_ttdv_cntt: [
          {
            sCaption: "Mã thuê bao (*)",
            sFieldName: "MA_TB",
            nFieldLength: 30,
            sValue: "hpg_eng_00000187",
            iAtt: 0,
            iDataType: 0,
            sMaxValue: 5,
            sMinvalue: 0,
            sLookUpTableName: "",
            sLookUpKeyField: "",
            sLookUpValueField: "",
            StrSql: "",
            strDepandField: "",
          },
        ],
        json_giaophieu_nv: [
          {
            PHIEU_ID: 12045046,
            NHANVIEN_TH_ID: 12461,
            NHIEMVU: "Đào tạo, hướng dẫn",
            GHICHU: "ERP TEST, không thực hiện",
            NHANVIEN_GIAO_ID: 452.0,
            NGAYGIAO: "05/08/2021 16:22:09",
            SO_DT: "0913200119",
            STATUS: 0,
            NHIEMVU_ID: 367,
            KQXL_ID: null,
          },
        ],
        nhanvien_hc_id: 456,
        nhanvien_gv_id: 456,
        ngay_ht: "24/09/2021",
        ngay_gv: "24/09/2021",
      };

      var phieuId = 12045046;

      phieuId = this.state.phieu_id;
      postData.phieu_id = phieuId;
      postData.hdtb_id = this.state.hdtb_id;
      postData.loaihd_id = this.state.loaihd_id;
      postData.luong_id = this.state.luong_id;
      postData.nhanvien_hc_id = this.state.nhanvien_id * 1;
      postData.nhanvien_gv_id =
        this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value * 1;
      postData.ngay_ht = this.state.dtpNgayBG.value;
      postData.ngay_gv = this.state.dtpNgayGV.value;

      postData.json_ttdv_cntt = this.tab_dvcntt.list.map((x) => ({
        sCaption: x.caption,
        sFieldName: x.field_name,
        nFieldLength: x.field_length,
        sValue: x.field_value,
        // sValue:
        //   x.data_type == 2
        //     ? moment(x.field_value, "MM-DD-YYYY").format("DD/MM/YYYY")
        //     : x.field_value,
        iAtt: x.att,
        iDataType: x.data_type,
        sMaxValue: x.max_value,
        sMinvalue: x.min_value,
        sLookUpTableName: "",
        sLookUpKeyField: "",
        sLookUpValueField: "",
        StrSql: "",
        strDepandField: "",
      }));

      postData.json_giaophieu_nv = postData.json_giaophieu_nv.map((x) => ({
        PHIEU_ID: phieuId,
        NHANVIEN_TH_ID: this.state.nhanvien_id * 1, //this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value,
        NHIEMVU: "Khai báo thông số!",
        GHICHU: "",
        NHANVIEN_GIAO_ID: this.state.nhanvien_id,
        NGAYGIAO: moment(this.state.dtpNgayGV.value).format(
          "DD/MM/YYYY HH:mm:ss"
        ),
        SO_DT: "",
        STATUS: 0,
        NHIEMVU_ID: 7,
        KQXL_ID: null,
      }));

      var kiemTraDL = await this.KiemTra_DL(this.state.luong_id);
      console.log("KiemTra_DL!", kiemTraDL);
      if (kiemTraDL != true) {
        return false;
      }
      console.log("postData", postData);

      //kiểm tra dữ liệu
      var ItemList = this.tab_dvcntt.list;
      console.log("ItemList", ItemList);
      if (
        this.CheckDataError(
          ItemList,
          ItemList.Length,
          "HD_CNTT",
          "HDTB_ID",
          this.state.selectedPhieu.hdtb_id
        )
      ) {
        this.$toast.error("Lỗi CheckDataError");
        return false;
      }

      this.loading(true);

      //TODO: Tự động giao phiếu
      // if(await this.PHAILAM(this.state.luong_id, 'GIAOVIEC')){
      //   console.log("TODO: tự động giao phiếu");
      // }

      //  var ngay_sys = dr["thangnam_sys"].ToString().Trim();
      //  var ngay_tt = dr["thangnam_tt"].ToString().Trim();
      //  var ngay_ht = dtpNgayBG.Value.ToString("dd/MM/yyyy");

      //   if (Convert.ToInt32(cboLoaiHD.SelectedValue) != LoaiHopDong.KHOIPHUC_SD)
      //   {
      //       if (Convert.ToInt32(dtpNgayBG.Value.ToString("yyyyMM")) < Convert.ToInt32(tt_nd.ngay_cn.ToString("yyyyMM")))
      //       {
      //           Message_Box.ShowWarning("Tháng hoàn thành không được nhỏ hơn tháng hiện tại !");
      //           return false;
      //       }
      //   }

      await CompleteProfileAPI.fn_an_capnhat_hoancong_dvcntt(
        this.axios,
        postData
      ).then((res) => {
        this.loading(false);
        if (
          res &&
          res.data &&
          res.data.data &&
          res.data.error_code &&
          res.data.error_code === "BSS-00000000"
        ) {
          this.$toast.success("Cập nhật dữ liệu thành công!");
          this.HienThiDanhSachHDTB();
        } else
          this.$toast.error("Lỗi cập nhật dữ liệu: " + res.data.message_detail);
      });

      //TODO: Tự động giao phiếu
      // if(await this.PHAILAM(this.state.luong_id, 'UPDATE_DV_CNTT')){
      //   console.log("TODO: tự động giao phiếu");
      // }

      return true;
    },

    async tsbtnChapNhan_Click(e) {
      await this.CapNhat();
    }, 

    async tsbtnGiaoPhieu_Click(e) {
      if ((await this.KiemTra_DL(this.state.luong_id)) == false) return;
      if (!(await this.CapNhat()) == false) return;
      if (!this.state.selectedPhieu.ma_gd) return;

      //TODO: lấy hướng giao id làm sau do thiếu api
      var huongGiao = await CommonAPI.lay_huonggiao_theo_luong_id(
        this.axios,
        this.state.luong_id
      );
      if (!huongGiao) {
        this.$toast.error("Không tìm thấy hướng giao");
        return;
      }

      var grid = this.$refs.grid_hdtb;
      var seletedRows = grid.getSelectedRecords();
      var dsphieu = seletedRows.map((x) => x.phieu_id);
      var dsma_gd = seletedRows.map((x) => x.ma_gd);
      var dshdtb_id = seletedRows.map((x) => x.hdtb_id);

      this.state.dsphieu = dsphieu[0];
      this.state.dsma_gd = dsma_gd[0];
      this.state.dshdtb_id = dshdtb_id[0];
      this.state.huonggiao_id = huongGiao.huonggiao_id;
      this.$router.push({
        path: "HandoverTicket",
        query: {
          magd: this.state.dsma_gd,
          madv: this.state.donvi_id,
          huonggiao_id: huongGiao.huonggiao_id,
          hdtb_id: this.state.dshdtb_id,
          luong_id: this.state.luong_id,
        },
      });
    },
    tsbtnGiaoPhoiHop_Click(e) {
      this.$toast.success("Form có hàm bán chéo PENDING");
    },

    tsbUpHoSo_Click: function (e) {
      //this.$toast.success("Chưa thực hiện do chưa có thiết kế HTML")
      this.$refs.popupUploadHoSo.show();
    },

    tsbtnKichHoat_Click: function () {
      this.$toast.success("Chờ nghiệp vụ mới, chưa có DB và API");
    },

    tsbtnKichHoatVisa_Click: function (e) {
      var postData = {
        vhdtb_id: 6075362,
        vstatus: 5,
      };

      postData.vhdtb_id = this.listbox.ds_hdtb.value;
      postData.vstatus = 5;

      this.loading(true);
      CompleteProfileAPI.capnhat_ngaykh_status_v2(this.axios, postData).then(
        (res) => {
          this.loading(false);
          if (res.data.error == "200") {
            this.$toast.success("Đồng bộ dữ liệu lên công ty dọc thành công !");
            this.HienThiDanhSachHDTB();
          } else
            this.$toast.error("Lỗi kích hoạt VISA: " + res.data.message_detail);
        }
      );
    },
    getDiaChi: function (data, datatype) {
      this[datatype].DIACHI = data.diachimoi;
      this[datatype].TINH_ID = data.tinh_id;
      this[datatype].QUAN_ID = data.quan_id;
      this[datatype].PHUONG_ID = data.phuong_id;
      this[datatype].PHO_ID = data.pho_id;
      this[datatype].AP_ID = data.ap_id;
      this[datatype].KHU_ID = data.khu_id;
      this[datatype].DAC_DIEM_ID = data.dacdiem_id;
      this[datatype].SO_NHA = data.so_nha;
      this[datatype].Lat = data.lat;
      this[datatype].Lng = data.lng;
    },
    getDiaChiLD: function (data) {
      this.getDiaChi(data, "diachiLD");
    },
    gv_DanhSach_FocusedRowChanged(e) {
      this.state.selectedPhieu = e;
      this.controls.tsbtnChapNhan.enabled = false;
      this.controls.tsbtnTraPhieu.enabled = false;
      if (this.state.isdebug)
        console.log("selectedPhieu", this.state.selectedPhieu);

      if (this.datagrid.ds_hdtb.list && this.datagrid.ds_hdtb.list.length > 0) {
        this.state.phieu_id = this.state.selectedPhieu.phieu_id;

        this.HienThiTTHopDongTB(this.datagrid.ds_hdtb.list);

        this.NAP_DS_ISSUES();
        this.NAP_DS_TSKT(); //Thông tin gói cước
        this.NAP_GRD_TIENTRINH();
        this.HienThiDS_PhieuTH(this.state.phieu_id);
      }
    },
    async NAP_DS_ISSUES() {
      //TODO: Anh Luật bảo liên quan IT360 sẽ làm sau
      return;
      var dataPost = {
        hdtb_id: 111111,
      };
      CompleteProfileAPI.lay_ds_issue_cua_hopdongtb(this.axios, dataPost).then(
        (res) => {
          this.tab_it360.list = res.data.data;
          if (this.state.isdebug)
            console.log("tab_it360.list", this.tab_it360.list);
        }
      );
    },
    async NAP_DS_TSKT() {
      var dataPost = {
        hdtb_id: this.state.selectedPhieu.hdtb_id,
      };
      await CompleteProfileAPI.lay_ds_thamso_tskt(this.axios, dataPost).then(
        (res) => {
          this.tab_ttgoicuoc.list = res.data.data;
          if (this.state.isdebug)
            console.log("tab_ttgoicuoc.list", this.tab_dvgiatang.list);
        }
      );
    },
    async NAP_GRD_TIENTRINH() {
      var dataPost = {
        tinhtc_id: 1,
        vhdtb_id: this.state.selectedPhieu.hdtb_id,
      };

      await CompleteProfileAPI.lay_tientrinh_tinh_tc(this.axios, dataPost).then(
        (res) => {
          this.tab_tthicong.list = res.data.data;
          if (this.state.isdebug)
            console.log("tab_tthicong.list", this.tab_tthicong.list);
        }
      );
    },
    async HienThiDS_PhieuTH(phieu_id) {
      var dataPost = {
        vphieu_cha_id: phieu_id,
      };

      CompleteProfileAPI.lay_danhsach_phieu_net_con(this.axios, dataPost).then(
        (res) => {
          this.tab_ctthicong.list = res.data.data;
          if (this.state.isdebug)
            console.log("tab_tthicong.list", this.tab_ctthicong.list);
        }
      );
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    async popupClosed(val) {},
    async tsbtnHoanCong_Click() {
      var postData = {
        vhdtb_id: 6058534,
        vhdkh_id: 5422987,
        vphieu_id: 12045046,
        vdonvi_giao_id: 900,
        vkyhoadon: 202101,
        vnhanvien_hc_id: 452,
        vnhanvien_gv_id: 452,
        vngayht: "24/09/2021 00:00:00",
        vngaygv: "24/09/2021 00:00:00",
        vjson_ttdv_cntt: [
          {
            sCaption: "Mã thuê bao (*)",
            sFieldName: "MA_TB",
            nFieldLength: 30,
            sValue: "hpg_eng_00000187",
            iAtt: 0,
            iDataType: 0,
            sMaxValue: 5,
            sMinvalue: 0,
            sLookUpTableName: "",
            sLookUpKeyField: "",
            sLookUpValueField: "",
            StrSql: "",
            strDepandField: "",
          },
        ],
        vjson_giaophieu_nv: [
          {
            PHIEU_ID: 12045046,
            NHANVIEN_TH_ID: 12461,
            NHIEMVU: "Đào tạo, hướng dẫn",
            GHICHU: "ERP TEST, không thực hiện",
            NHANVIEN_GIAO_ID: 452.0,
            NGAYGIAO: "05/08/2021 16:22:09",
            SO_DT: "0913200119",
            STATUS: 0,
            NHIEMVU_ID: 367,
            KQXL_ID: null,
          },
        ],
      };

      var phieuId = 12045046;
      if (this.state.isdemo != true) {
        phieuId = this.state.phieu_id;
        postData.vhdtb_id = this.state.hdtb_id;
        postData.vhdkh_id = this.state.hdkh_id;
        postData.vphieu_id = phieuId;
        postData.vdonvi_giao_id = this.state.donvi_id;
        postData.vkyhoadon = 202101;
        postData.vnhanvien_hc_id = this.state.nhanvien_id * 1;
        postData.vnhanvien_gv_id =
          this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value * 1;

        postData.vngayht = moment(this.state.dtpNgayBG.value).format(
          "DD/MM/YYYY HH:mm:ss"
        );
        postData.vngaygv = moment(this.state.dtpNgayGV.value).format(
          "DD/MM/YYYY HH:mm:ss"
        );

        postData.vjson_ttdv_cntt = this.tab_dvcntt.list.map((x) => ({
          sCaption: x.caption,
          sFieldName: x.field_name,
          nFieldLength: x.field_length,
          sValue: x.field_value,
          iAtt: x.att,
          iDataType: x.data_type,
          sMaxValue: x.max_value,
          sMinvalue: x.min_value,
          sLookUpTableName: "",
          sLookUpKeyField: "",
          sLookUpValueField: "",
          StrSql: "",
          strDepandField: "",
        }));

        postData.vjson_giaophieu_nv = postData.vjson_giaophieu_nv.map((x) => ({
          PHIEU_ID: phieuId,
          NHANVIEN_TH_ID: this.state.nhanvien_id, //this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value,
          NHIEMVU: "Khai báo thông số!",
          GHICHU: "",
          NHANVIEN_GIAO_ID: this.state.nhanvien_id,
          NGAYGIAO: moment(this.state.dtpNgayGV.value).format(
            "DD/MM/YYYY HH:mm:ss"
          ),
          SO_DT: "",
          STATUS: 0,
          NHIEMVU_ID: 7,
          KQXL_ID: null,
        }));
      }

      if (await this.KiemTra_DL(this.state.luong_id) != true) return;

      var thangnamsys = moment(
        this.state.selectedPhieu.thangnam_sys,
        "DD/MM/YYYY"
      );
      if (this.listbox.ds_loaihd.value != LoaiHopDong.KHOIPHUC_SD) {
        if (
          parseInt(moment(this.state.dtpNgayBG.value).format("yyyyMM")) <
          parseInt(moment(thangnamsys).format("yyyyMM"))
        ) {
          this.$toast.error(
            "Tháng hoàn thành không được nhỏ hơn tháng hiện tại !"
          );
          return false;
        }
      }

      if (
        parseInt(moment(this.state.dtpNgayBG.value).format("yyyyMMdd")) <
        parseInt(moment(thangnamsys).format("yyyyMMdd"))
      ) {
        this.$toast.error(
          "Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại"
        );
        return false;
      }

      if (this.state.isdebug) console.log("postData", postData);

      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn hoàn công không ?", {
          title: "",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (!v) {
            return;
          }

          this.loading(true);
          CompleteProfileAPI.fn_an_hoancong_dvcntt(this.axios, postData).then(
            (res) => {
              this.loading(false);
              if (res.data.error == "200") this.$toast.success(res.data.data);
              else
                this.$toast.error(
                  "Lỗi cập nhật dữ liệu: " + res.data.message_detail
                );
            }
          );
        });
    },

    async btnNDTH_Click() {
      if (!this.state.phieu_id || this.state.phieu_id == 0 || !this.state.selectedPhieu) {
        this.$toast.warning("Hãy chọn phiếu trước để nhập lý do tồn!");
        return;
      }

      await CompleteProfileAPI.update_nd_thuchien(this.axios, {
        phieu_id: this.state.phieu_id,
        nd_thuchien: this.state.selectedPhieu.nd_thuchien,
      }).then((res) => {
        this.loading(false);
        if (
          res &&
          res.data && 
          res.data.error_code &&
          res.data.error_code === "BSS-00000000"
        )
          this.$toast.success("Cập nhật thành công!");
        else
          this.$toast.error("Lỗi cập nhật dữ liệu: " + res.data.message_detail);
      });

      await this.HienThiDanhSachHDTB();
    },

    async tsbtnGiaoViec_Click() {
      if (this.datagrid.ds_hdtb.list < 0) {
        this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ");
        return;
      }
      console.log("nguoigv_id", this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value);
      if (
        this.tab_dvcntt.tt_giaoviec.ds_nguoigv.list.length <= 0 &&
        this.tab_dvcntt.tt_giaoviec.ds_nguoigv.value == 0
      ) {
        this.$toast.error("Hãy nhập nhân viên giao việc!");
        return;
      }
      //if (String(this.state.dtpNgayGV.enabled).toLowerCase() == 'false')
      //{
      //    this.$toast.error("Hãy nhập ngày giao việc!");
      //    return;
      //}
      // if (String(this.state.dtpNgayBG.enabled).toLowerCase() == 'true')
      // {
      //     System.TimeSpan diffResult = dtpNgayBG.Value.Subtract(dtpNgayGV.Value);
      //     if (diffResult.Seconds < 0)
      //     {
      //         Message_Box.ShowWarning("Ngày giao việc không được lớn hơn ngày hoàn thành!");
      //         return;
      //     }
      // }

      if (await this.KiemTra_DL(this.state.luong_id) != true) return false;

      var grid = this.$refs.gv_DanhSach;
      var seletedRows = grid.getSelectedRecords();

      if (seletedRows.length <= 0) {
        this.$toast.error("Bạn chưa chọn thuê bao! ");
        return;
      } 
      var dsphieu = seletedRows.map((x) => x.phieu_id);
      var dsma_tb = seletedRows.map((x) => x.ma_tb);
      var dshdtb_id = seletedRows.map((x) => x.hdtb_id);

      this.state.dsphieu = dsphieu[0];
      this.state.dsma_tb = dsma_tb[0];
      this.state.dshdtb_id = dshdtb_id[0];
      this.state.huonggiao_id = seletedRows[0].huonggiao_id;
      console.log("this.state.dsphieu", this.state.dsphieu);
      console.log("this.state.dsma_tb", this.state.dsma_tb);
      console.log("this.state.dshdtb_id", this.state.dshdtb_id);
      console.log("this.state.huonggiao_id", this.state.huonggiao_id);
      this.$refs.popupGiaoPhieuNV.show();
    },
    tsbtnNhapMoiTT_Click: function (e) {
      this.$refs.popupNhapNoiDungTienTrinhThiCong.show();
    },
    btnGhiLai_Click: function (e) {
      this.$toast.success("PENDING do form có hàm bán chéo");
    },

    tsddbIn_Click: function (e) {
      this.$toast.success("Chưa có ks/api");
    },

    tsbtnChuyenTo_Click: function (e) { 
       this.$refs.popupChuyenTo.openDialog(this.state.phieu_id, this.state.hdtb_id)
    },
    tsbtnGiaoPhieuPhoiHop_Click(e) {
      if (!this.state.phieu_id || this.state.phieu_id == 0)
      { 
          this.$toast.error("Hãy chọn phiếu trước!");
          return;
      }
      this.$refs.dialogGiaoPhieuPhoiHop.openDialog(this.state.phieu_id, 1);
    },
    async tsbtnTraPhieu_Click() {
      this.$refs.popupTraPhieu.show();
    },


    tsbtnXuatFile_Click: function (e) {
      if (!this.datagrid.ds_hdtb.list || !this.datagrid.ds_hdtb.list.length > 0) {
        this.$toast.error("Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !");
        return;
      } 
      var dsbaoCao = this.datagrid.ds_hdtb.list;
      this.$refs.dialogLayDuLieuBC.openDialog(dsbaoCao);
    },

    tsbtnGiaoPhieu_Ton_Click(e) {
      if (!this.state.phieu_id || this.state.phieu_id == 0)
      { 
          this.$toast.error("Hãy chọn phiếu trước để nhập lý do tồn!");
          return;
      }
      this.$refs.dialogObjPhieuTon.showPhieuTon(this.state.phieu_id, this.state.tthd_id,[])
    },

    //Gán KV
    tsbtnNVKT_Click: function (e) {
      this.popupComponent = ()=>import('../setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao');
        this.popupComponentName = "popupChonKhuVuc";
        var data = {diachi: this.diachiLD, khuvuc_id: 0, hdtb_id: this.state.hdtb_id, thuebao_id: 0, donvi_id: 0 };
 
        console.log('popupChonKhuVuc DATA', data);

        this.popupComponentData = data;
        //if(this.dsCTM_TBI && this.dsCTM_TBI.length>0) this.popupComponentData.ds = this.dsCTM_TBI;
        this.Popup('popupComponents');
    },
    tsbtnHoanThienHS_Click() {
       //TODO: Không có trong URD, làm sau
    },
    DangXayDung() {
      this.$toast.error("Chức năng đang được xây dựng và hoàn thiện!");
    },
    tsbtnFile_Upload_Click: function (e) { 
      if (!this.state.phieu_id || this.state.phieu_id == 0)
      { 
          this.$toast.error("Hãy chọn phiếu trước!");
          return;
      }
      // var ma_tb = this.state.selectedPhieu.ma_tb;
      // this.$router.push({
      //   path: "search/SearchEProfile",
      //   query: {
      //     kieu: 1,
      //     ma_tb: ma_tb
      //   },
      // });
    },
    tsbtn_KichHoat_CA_Click: function (e) {
      this.$toast.success("Phần liên quan đến CA, đang chờ nghiệp vụ mới");
    }, 
    tsbtnHoanThanh_Click() { 
      var grid = this.$refs.gv_DanhSach;
      var seletedRows = grid.getSelectedRecords();
      if(!seletedRows || seletedRows.length <= 0)
      {
        this.$toast.error("Bạn chưa chọn thuê bao!");
        return;
      }

      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn hoàn thành không ?", {
          title: "",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (!v) {
            return;
          }

          var postData = {
              "vphieu_id": this.state.phieu_id,
              "vhdtb_id": this.state.hdtb_id,
              "vngay_th": moment(this.tt_nd.ngay_cn).format("DD/MM/YYYY HH:mm:ss"),
              "vttph_id": TRANGTHAI_PH.DATHUCHIEN
          }

          this.loading(true);
           CompleteProfileAPI.fn_an_hoanthanh(this.axios, postData).then(
            (res) => {
              this.loading(false);
              if (res.data.error == "200" && res.data.error_code == "BSS-00000000") {
                this.$toast.success(res.data.data);
                this.HienThiDanhSachHDTB();
              } 
              else
                this.$toast.error(
                  "Lỗi cập nhật dữ liệu: " + res.data.message_detail
                );
            }
          );
        });
 
    },

    
    ChuanHoaTen(ten) {
      if (!ten) return "";
      var ten_new = "";
      var str_con = ten.trim();
      var str_xl = "";
      var k = 0;
      var found = true;
      if (str_con.length < 1) return;
      str_con = str_con.replaceAll(/\s+/g, " ");
      try {
        while (found) {
          k = str_con.indexOf(" ");
          if (k > 0) {
            str_xl = str_con.substring(0, k);
            str_con = str_con.substring(k + 1);
            str_xl = this.ChuyenChuHoa(str_xl);
            if (ten_new != "") ten_new += " " + str_xl;
            else ten_new = str_xl;
            found = true;
          } else found = false;
        }
        if (str_con != "") {
          str_xl = str_con;
          str_xl = this.ChuyenChuHoa(str_xl);
          if (ten_new != "") ten_new = ten_new + " " + str_xl;
          else ten_new = str_xl;
        }
        return ten_new;
      } catch (e) {
        return ten;
      }
    },
    //end methods
  },
};
</script>
