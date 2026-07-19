
<template src="./ChangeLandlineIMSType.html"></template>

<style scoped src="./ChangeLandlineIMSType.scss"></style>

<script>
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import "@/assets/vendor/jquery/split";
import API from "./ChangeLandlineIMSTypeAPI";
import ModalChonDiaChi from "../InstallNewSubs/Popups/popupChonDiaChi";
import BssErrorMarker from "@/components/BssErrorMarker";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import ThongTinLienHe from "../ChangeContractOwner/popup/ThongTinLienHe.vue";
import { required, minValue, maxValue } from "vuelidate/lib/validators";
// import TimNhanVienThuCuoc from '@/modules/QLTN/GachNo/components/TimNhanVienThuCuoc'
//import PopupTimKiemNhanVienThuCuoc from '../InstallNewSubs/Popups/PopupTimKiemNhanVienThuCuoc'
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import DataGrid2 from "@/components/Controls/DataGrid2";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";
import cache from '@/modules/contract/setup/InstallNewSubs/cache';
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
  CAConstanst,
  DatabaseConstants
} from "../InstallNewSubs/Enums";

import { tracuuThueBaoChuaNhapCap } from "../../../CABMAN/store/TraCuuThueBaoChuaNhapCap";
//import EventBus from '@/utils/eventBus'
export default {
  components: {
    breadcrumb,
    gridview,
    SearchContractModal,
    DatePicker,
    ModalChonDiaChi,
    BssErrorMarker,
    BssRequiredMarker,
    ThongTinLienHe,
  },
  //directives: { mask:VueMaskDirective },
  name: "ChangeLandlineIMSType",
  validations: {
    diachiKH: {
      DIACHI: {
        required,
      },
    },
    diachiHK: {
      DIACHI: {
        required,
      },
    },
    diachiNDD: {
      DIACHI: {
        required,
      },
    },
    diachiTT: {
      DIACHI: {
        required,
      },
    },
    diachiCT: {
      DIACHI: {
        required,
      },
    },
    diachiBC: {
      DIACHI: {
        required,
      },
    },
    diachiTB: {
      DIACHI: {
        required,
      },
    },
    diachiLD: {
      DIACHI: {
        required,
      },
    },
    diachiKN: {
      DIACHI: {
        required,
      },
    },
    diachiDau: {
      DIACHI: {
        required,
      },
    },
    diachiCuoi: {
      DIACHI: {
        required,
      },
    },
  },
  created() {},
  destroyed() {},
  props: ["PHIEUHS_ID"],
  mounted() {
    //WinUI.WinUILapHopDong.frmChuyenDoiCDIMS,PTTB.exe
    try {
      this.pageLoad();
      this.buttonSet = 1;
      Split(["#boxLeft", "#boxRight"], {
        sizes: [50, 50],
        gutterSize: 16,
        onDragEnd: function (sizes) {},
      });
      this.state.txt_KhuVucEnabled = true;
      this.loading(true);
      var api1 = API.post_lay_danhmuc_lapmoi_tt_chung_v8(this.axios, {
        vchuoi:
          "TRANG_BI|LOAI_TB|CHU_QUAN|TIEN_KM|DOITUONG|DICHVU_VT|LOAIHINH_TB|LOAI_KHL|LOAI_GT|PHANLOAI_KH|LOAI_KH|NGANHANG|HINHTHUC_TT|HINHTHUC_TC|DANTOC|NGANHNGHE|QUOCTICH|HINHTHUC_TTHI|KIEU_HD|GOI_KT|DONVI_QL|KIEU_LD|NHACC_CU|CONGNGHE|HINHTHUC_CM|THOIHAN|LOAI_CSHT|DOITAC|LOAI_IP|PHANLOAI_DV",
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.listbox.trangbi.list = response.data.data["trangbi"];
          this.listbox.loai_tb.list = response.data.data["loai_tb"];
          this.listbox.chu_quan.list = response.data.data["chu_quan"];
          // FIXME tam khoa de su dung gia tri mac dinh theo yeu cau demo
          // if(this.listbox.chu_quan.list && this.listbox.chu_quan.list.length>0) {
          //   this.listbox.chu_quan.valueldcd = this.listbox.chu_quan.list[0].chuquan_id;
          //   this.listbox.chu_quan.valuecd = this.listbox.chu_quan.list[0].chuquan_id;
          // }
          this.listbox.tien_km.list = response.data.data["tien_km"];
          this.ds_tien_khoanmuc = this.listbox.tien_km.list;
          this.listbox.doituong.list = response.data.data["doituong"];
          if (
            this.listbox.doituong.list &&
            this.listbox.doituong.list.length > 0
          )
            this.listbox.doituong.value =
              this.listbox.doituong.list[0].doituong_id;
          this.listbox.dichvu_vt.list = response.data.data["dichvu_vt"].filter(
            (x) =>
              x.dichvuvt_id == DichVuVienThong.CO_DINH ||
              x.dichvuvt_id == DichVuVienThong.IMS ||
              x.dichvuvt_id == DichVuVienThong.DICHVU_CNTT || 
              x.dichvuvt_id == DichVuVienThong.DI_DONG
          );
          this.listbox.dichvu_vt_moi.list = this.listbox.dichvu_vt.list;

          var listLoaiHinhTB = response.data.data["loaihinh_tb"]
            .filter(
              (x) =>
                x.dichvuvt_id == DichVuVienThong.CO_DINH ||
                x.dichvuvt_id == DichVuVienThong.IMS ||
                x.dichvuvt_id == DichVuVienThong.DICHVU_CNTT ||
                x.dichvuvt_id == DichVuVienThong.DI_DONG

            )
            .sort(function (a, b) {
              return parseFloat(a.loaitb_id) - parseFloat(b.loaitb_id);
            });

          this.listbox.loaihinh_tb.list = listLoaiHinhTB;
          this.listbox.loaihinh_tb.value =
            this.listbox.loaihinh_tb.list.filter(
              (x) => x.dichvuvt_id == this.listbox.dichvu_vt.value
            )[0].loaitb_id * 1;

          // console.log(
          //   "this.listbox.loaihinh_tb.list",
          //   this.listbox.loaihinh_tb.list
          // );

          this.listbox.loaihinh_tb_moi.list = listLoaiHinhTB;
          this.listbox.loaihinh_tb_moi.value =
            this.listbox.loaihinh_tb_moi.list.filter(
              (x) => x.dichvuvt_id == this.listbox.dichvu_vt_moi.value
            )[0].loaitb_id * 1;

          this.listbox.loai_khl.list = response.data.data["loai_khl"];
          this.dtPhanLoaiKH = this.listbox.loai_khl.list.filter(
            (x) => x.nhom_pl == "1"
          );
          this.listbox.loai_gt.list = response.data.data["loai_gt"];
          if (this.listbox.loai_gt.list && this.listbox.loai_gt.list.length > 0)
            this.listbox.loai_gt.value = this.listbox.loai_gt.list[0].loaigt_id;
          this.listbox.phanloai_kh.list = response.data.data["phanloai_kh"];
          this.listbox.loai_kh.list = response.data.data["loai_kh"];
          this.listbox.nganhang.list = response.data.data["nganhang"];
          this.listbox.hinhthuc_tt.list = response.data.data["hinhthuc_tt"];
          if (
            this.listbox.hinhthuc_tt.list &&
            this.listbox.hinhthuc_tt.list.length > 0
          )
            this.listbox.hinhthuc_tt.value =
              this.listbox.hinhthuc_tt.list[0].httt_id;
          this.listbox.hinhthuc_tc.list = response.data.data[
            "hinhthuc_tc"
            ].filter((x) => x.httc_id == 2 || x.httc_id == 3);
          if (
            this.listbox.hinhthuc_tc.list &&
            this.listbox.hinhthuc_tc.list.length > 0
          )
            this.listbox.hinhthuc_tc.value =
              this.listbox.hinhthuc_tc.list[0].httc_id;
          this.listbox.dantoc.list = response.data.data["dantoc"];
          if (this.listbox.dantoc.list && this.listbox.dantoc.list.length > 0)
            this.listbox.dantoc.value = this.listbox.dantoc.list[0].dantoc_id;
          this.listbox.nganhnghe.list = response.data.data["nganhnghe"];
          //if(this.listbox.nganhnghe.list && this.listbox.nganhnghe.list.length>0) this.listbox.nganhnghe.value = this.listbox.nganhnghe.list[0].nganhnghe_id;
          this.listbox.quoctich.list = response.data.data["quoctich"];
          if (
            this.listbox.quoctich.list &&
            this.listbox.quoctich.list.length > 0
          )
            this.listbox.quoctich.value =
              this.listbox.quoctich.list[0].quoctich_id;
          this.listbox.hinhthuc_tthi.list = response.data.data["hinhthuc_tthi"];
          this.listbox.kieu_hd.list = response.data.data["kieu_hd"];
          if (this.listbox.kieu_hd.list && this.listbox.kieu_hd.list.length > 0)
            this.listbox.kieu_hd.value = this.listbox.kieu_hd.list[0].kieuhd_id;
          this.listbox.goi_kt.list = response.data.data["goi_kt"];

          //if(this.listbox.donvi_ql.list && this.listbox.donvi_ql.list.filter(dv=>(dv.loaidv_id==2))) this.listbox.donvi_ql.valueql = this.listbox.donvi_ql.list.filter(dv=>(dv.loaidv_id==2))[0].donvi_id;
          //this.listbox.kieu_ld.list = response.data.data['kieu_ld'];
          this.listbox.nhacc_cu.list = response.data.data["nhacc_cu"];
          this.listbox.congnghe.list = response.data.data["congnghe"];
          //this.defaultCongNghe(this.listbox.kieu_ld.value);
          this.listbox.hinhthuc_cm.list = response.data.data["hinhthuc_cm"];
          this.listbox.thoihan.list = response.data.data["thoihan"];
          if (this.listbox.thoihan.list && this.listbox.thoihan.list.length > 0)
            this.listbox.thoihan.valuecd =
              this.listbox.thoihan.list[0].thoihan_id;
          this.listbox.loai_csht.list = response.data.data["loai_csht"];
          this.listbox.doitac.list = response.data.data["doitac"];
          this.listbox.loaiip.list = response.data.data["loaiip"];
          this.listbox.phanloai_dv.list = response.data.data["phanloai_dv"];
        }
      });

      var api3 = API.post_sp_ht_chitietdv_ims(this.axios, {
        kieu: "1",
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.listbox.ht_chitietdv_ims.list = response.data.data;
          if (
            this.listbox.ht_chitietdv_ims.list &&
            this.listbox.ht_chitietdv_ims.list.length > 0
          )
            this.listbox.ht_chitietdv_ims.value =
              this.listbox.ht_chitietdv_ims.list[0].ctdv_id;
        }
      });

      //load đơn vị QL
      var api4 = API.sp_lay_ds_donvi_ql(this.axios, {
        loaidv_id: LOAI_DV.DONVIQL_LD,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.listbox.donvi_ql.list = response.data.data;
          if (
            this.listbox.donvi_ql.list &&
            this.listbox.donvi_ql.list.length > 0
          )
            this.listbox.donvi_ql.value =
              this.listbox.donvi_ql.list[0].donvi_id;
        }
      });

      //Lấy các tham số mặc định

      var api6 = API.post_lay_thamso_md(this.axios, { kieu_id: 0 }).then(
        (response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            this.thamsomacdinh = response.data.data.reduce(function (
              result,
              item,
              index,
              array
              ) {
                result[item["ma_ts"]] = item["ten_ts"]; //a, b, c
                return result;
              },
              {}); //.map(x => ({ [x['ma_ts']]:x['ten_ts'] }));

            if (this.thamsomacdinh["TUSINH_PASS_IMS"])
              this.state.tusinh_pass_ims =
                this.thamsomacdinh["TUSINH_PASS_IMS"] * 1;
            if (
              this.thamsomacdinh["KHUVUC_THEO_KETCUOI"] &&
              this.thamsomacdinh["KHUVUC_THEO_KETCUOI"] == 1
            )
              this.state.KHUVUC_THEO_KETCUOI = 1;
            if (this.thamsomacdinh["MACDINH_MATKHAU_IMS"])
              this.state.matkhau_md_ims =
                this.thamsomacdinh["KHUVUC_THEO_KETCUOI"];
            if (
              this.thamsomacdinh["KIEMTRA_IMS_CD_CDLH"] &&
              this.thamsomacdinh["KIEMTRA_IMS_CD_CDLH"] == 1
            )
              this.state.kt_ims = true;
            if (
              this.thamsomacdinh["KIEMTRA_KHOSO_CD"] &&
              this.thamsomacdinh["KIEMTRA_KHOSO_CD"] == 1
            )
              this.state.kt_khoso_cd = true;
            if (
              this.thamsomacdinh["KHONG_HIENTHI_TRACUU_DB_CDLH"] &&
              this.thamsomacdinh["KHONG_HIENTHI_TRACUU_DB_CDLH"] == 1
            )
              this.state.khong_ht_tracuudb = 1;
            if (this.thamsomacdinh["SINH_MA_GD_THEO_DV"])
              this.state.ts_sinhma_gd_theo_donvi = true;
            if (
              this.thamsomacdinh["IP_SIPTRUNKING"] &&
              this.thamsomacdinh["IP_SIPTRUNKING"] == 1
            )
              this.state.phainhap_ip_siptrunking = true;
            if (this.thamsomacdinh["THU_LAY_KHUVUC_THEO_DIADANH"])
              this.state.__THU_LAY_KHUVUC_THEO_DIADANH = true;
            if (this.thamsomacdinh["KHONGCHO_CHON_KHUVUC"])
              this.state.txt_KhuVucEnabled = false;
          }
        }
      );

      Promise.all([api1, api3, api4, api6])
        .then(() => {
          //,api2,api3,api4,api5,api6,api7,api8
          //this.HT_GIAODIEN_DONVI();
          this.kt_load = false;
          this.LoaiHinhTB_moi_Changed();
          this.LayTienTheoKhoanMuc();
        })
        .catch(() => {})
        .then(() => {
          //this.HT_GIAODIEN_DONVI();
          //this.LayTienTheoKhoanMuc();
        })
        .finally(() => {
          this.loading(false);
        });

      if (
        this.$route.query.tag != null &&
        this.$route.params.tag != "" &&
        this.$route.query.tag.length > 0
      ) {
        this.Tag = this.$route.query.tag;
      }

      if (this.Tag != null && this.Tag.toString() != "") {
        var str = this.Tag.toString();
        var words = str.split("+");
        if (words.length >= 1) this.iKenhTN = words[0] * 1;
        else this.iKenhTN = 1;
      } else {
        this.iKenhTN = 1;
      }
      if (
        this.$route.query.hdkh_cha_id != null &&
        this.$route.query.hdkh_cha_id != "" &&
        this.$route.query.hdkh_cha_id.length > 0
      ) {
        // console.log("LoadTuHopDongTuVan", this.$route.query.hdkh_cha_id);
        this.LoadTuHopDongTuVan(this.$route.query.hdkh_cha_id);
      }
    } catch (ex) {
      commonFn.showException(this, ex);
    }
  },
  destroyed() {},
  computed: {
    DiaChiLDCLONE() {
      return JSON.parse(JSON.stringify(this.diachiLD));
    },
    kieu_ld_kieu() {
      try {
        return (
          this.listbox.kieu_ld.list.filter(
            (x) => x.kieuld_id == this.listbox.kieu_ld.value
          )[0]["kieu"] * 1
        );
      } catch (ex) {
        return 0;
      }
    },
  },
  watch: {
    "listbox.kieu_ld.value": function () {
      if (this.kt_load == false) {
        this.LayTienTheoKhoanMuc();
      }
    },

    "listbox.dichvu_vt_moi.value": function () {
      this.cboDichVuVTMoi_SelectedValueChanged();
    },

    "listbox.loaihinh_tb.value": function () {
      this.LoaiHinhTB_Changed();
    },

    "listbox.donvi_ql.value": function () {
      if (!this.listbox.donvi_ql.value) return;
      API.post_sp_lay_ds_donvi_theo_donvi_ql(this.axios, {
        loaidv_id: LOAI_DV.TRAM_VT,
        donvi_id: this.listbox.donvi_ql.value,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.listbox.tram_tc.list = response.data.data;
          if (this.listbox.tram_tc.list && this.listbox.tram_tc.list.length > 0)
            if (!this.listbox.tram_tc.value) this.listbox.tram_tc.value = this.listbox.tram_tc.list[0].donvi_id;
        }
      });
    },
    "listbox.trunk.checkbox_value": function () {
      this.listbox.hinhthuc_tc.enabled =
        this.listbox.trunk.checkbox_value == false;
    },
    "listbox.trunk.value": function () {
      var httc_id = listbox.trunk.list
        .filter((x) => x.trunkid == listbox.trunk.value)
        ["loai"].toString();
      if (!httc_id)
        this.$toast.warning(
          "Thuê bao đại diện đường trunk chưa có thông tin về hình thức tính cước !"
        );
      else this.listbox.hinhthuc_tc.value = httc_id * 1;
    },
    dtienld: function () {
      // console.log("dtienld changed", "this.LayTongTien_HDTB()");
      //this.LayTongTien_HDTB();
    },
    buttonSet: function (oldval, newval) {
      switch (newval) {
        case -1:
          this.filterbox.ctv.ischecked = false;
          this.filterbox.nguoigt.ischecked = false;
          //TODO clear Mã đại diện
          break;
        case 0:
          //TODO clear form - Clear function 18265
          break;
        case 1:
          this.filterbox.ctv.ischecked = false;
          this.filterbox.nguoigt.ischecked = false;
          //TODO clear Mã đại diện
          break;
        case 2:
          break;
        case 3:
          break;
        case 4:
          break;
      }
    },
  },
  filters: {},
  data() {
    return {
      dialogComponent: null,
      dialogEvts: {
        close: this.popupClosed,
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentAttr: {},
      popupComponentEvts: {
        "form-close": this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        PopUpCongTacVien_Thoat: this.popupClosed,
      },
      position: { X: "center", Y: "top" },
      kt_load: true,
      ma_tb: "",
      hdkhtn_id: 0,
      may_cn: this.$root.token.getMachine(),
      ip_cn: this.$root.token.getIP(),
      nguoi_cn: this.$root.token.getUserName(),
      Tag: "",
      iKieu: 0, //0: Lắp mới, 1: Sửa khuyến mại
      header: {
        title: "Thay đổi loại hình thuê bao (cố định <-> IMS)",
        list: [
          //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
          {
            name: "Thay đổi loại hình thuê bao (cố định <-> IMS)",
            link: {
              name: "Ui.cards",
            },
          },
          //{ name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
        ],
      },
      thamsomacdinh: {},
      state: {
        ds_lay_tien_khoanmuc_ld: null
      },
      NgayYeuCau: moment(new Date()).format("DD/MM/YYYY"),
      NgayLapHD: moment(new Date()).format("DD/MM/YYYY"),
      dateFormat: "DD/MM/YYYY",
      datetimeFormat: "DD/MM/YYYY HH:mm",
      maduan: "",
      VPHIEUCSKH_ID: 0,
      IS_HD_TUVAN: false,
      dshd: [],
      txtMaDoiCap_ADSL: 0,
      tongtienhd: 0,
      tongtienkm: 0,
      tongvatkm: 0,
      dtienld: 0,
      dvatld: 0,
      dtiencd: 0,
      dvatcd: 0,
      iKenhTN: "",
      hdtb_id: null,
      vtyle_vat_id_dnhm: 0,
      vtyle_vat_dnhm: 0,
      nhanvien_ql_am_id: 0,
      buttonSet: null,
      lvwThueBao: [],
      hdkh_cha_id: 0,

      trangbi_id: 3,
      tien_tb: 0,
      vat_tb: 0,
      tien_dv: 0,
      vat_dv: 0,

      ChiTietDV_IMS: {
        didong: 1, // Triển khai trên nền di động
        internet: 2, // Triển khai trên nền internet
        duongrieng: 3, // Sử dụng đường riêng
      },
      lvDvgt: {
        list: [],
        value: null,
      },
      lvMuaThietbi: {
        list: [],
        value: null,
      },
      dvgt: {
        tien_dv: 0,
        vat_dv: 0,
        tien_km_dvgt: 0,
        vat_km_dvgt: 0,
      },
      dichvu: {
        dtien_dv: 0,
        dvat_dv: 0,
      },
      dsCTM_TBI: [],
      buttons: {
        tsbtnXoaTB: false,
        tsbtnThemTB: false,
        tsbtnThemTT: false,
        tsbtnXoaTT: false,
        btnChonSM: true,
        chkLapDatBS: false,
        btnThongTinKhac: false,
      },
      controls: {
        lblThietBiIMS: { Visible: true },
        cboThietBiIMS: { Visible: true },
        lbSoLuong: { Visible: true },
        txtSoLuong: { Visible: true },
        txtIp_IMS: { Visible: true },
        cboChiTietDVMoi: { Visible: true },
      },
      dsHDKH: [],
      dsHDTB: [],
      dsHDTB_IMS: [],
      dsHDTT: [],
      dsHDTB_CD: [],
      dsTTTT: [],
      dsCTTHD: [],
      dsDiaChi: [],
      dsDiaChiHDKH: [],
      dsDiaChiHDTT: [],
      dsDiaChiHDTB: [],
      dsHDTBDV: [],
      dsDK_DVGT: [],
      dsHDTB_KV: [],
      gridEditors: [],
      ItemList:[],
      diachitb_id: 0,
      diachild_id: 0,
      diachitt_id: 0,
      diachikh_id: 0,
      vchuquan_id: 0,
      vchuquanld_id: 0,
      txtSoLuong: 0,
      ip_ims: "",
      khachhang: {
        makh: "",
        sogt: "",
        magd: "",
        mahd: "",
        khachhang_id: 0,
        tenkh: "",
        nguoidd: "",
        dienthoai: "",
        fax: "",
        chucvu: "",
        noicaphk: "",
        noicap: "",
        gioitinh: -1,
        masothue: "",
        ghichu: "",
        dactrung: "",
        PHANLOAIKH_ID: -1,
        hdkh_id: 0,
        plkh: "",
        nhom_plkh: 0,
      },
      thuebao: {
        //###thuebao
        codinh: {
          somay: "",
          sochinh: "",
          dgvTienCK: {
            list: [],
            headers: [
              {
                fieldName: "tuthang",
                headerText: "Từ tháng",
                allowFiltering: true,
              },
              {
                fieldName: "denthang",
                headerText: "Đến tháng",
                allowFiltering: true,
              },
              {
                fieldName: "tien_ck",
                headerText: "Tiền cam kết",
                allowFiltering: true,
                allowHtml: true,
              },
            ],
          },
          arraySoMay: [],
        },
        thuebao_id: 0,
        tentb: "",
        ghichu: "",
        tbhd_pl: 0,
        thuebao_id_cha: 0,
        loaisochinh: 0,
        hdtb_id_cha: 0,
        thangck: 0,
        doituong_ct_id: 0, // TODO tim hieu xem load doituong_ct_id nhu the nao????
        tgdungthu: 0,
        tinhkhac: 0,
        khuvuc: "",
        khuvuc_id: 0,
        check_tb_adsl: 0,
        cvlct: false,
        congvan: "",
        NgayCVChecked: false,
        NgayCV: moment(new Date()).format("DD/MM/YYYY"),
        crm: "",
        erp: "",
        vtn: "",
      },
      thuebaocd: {
        matn: "",
        sothang_thueso: 0,
        noidung_thueso: "",
        kieu_thueso: 0,
        madoicap: null,
        cuocdoitac: 0,
        ma_vnpt: -1,
      },
      diachiKH: {
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
      diachiHK: {
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
      diachiNDD: {
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
      diachiCT: {
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
      diachiBC: {
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
      diachiKN: {
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
      diachiDau: {
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
      diachiCuoi: {
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
      thietbi: {
        tien_tbi: 0,
        vat_tbi: 0,
        tien_kmtbi: 0,
        vat_kmtbi: 0,
        tien_tgtb_tragop: 0,
        vat_tgtb_tragop: 0,
        tien_tgtb_tratruoc: 0,
        vat_tgtb_tratruoc: 0,
        dtien_kmtb: 0,
        dvat_kmtb: 0,
        dtientbi: 0,
        dvattbi: 0,
      },
      thanhtoan: {
        matt: "",
        tentt: "",
        thanhtoan_id: 0,
        masothue: "",
        sodienthoai: "",
        email: "",
        chutk: "",
        tuyenthu_id: 0,
        matuyen: "",
        manvtc: "",
        tuyenthu_httt: 0,
        nhanvientc_id: 0,
        ghichu: "",
        hdmig_id: 0,
        mig_id: 0,
        mainghep: "",
        matb_dd: 0,
        manvql: "",
      },
      listbox: {
        trangbi: {
          list: [],
          valuewan: TRANGBI.VIENTHONG_TRANGBI,
          valuetsl: 0,
          valuecd: 3,
          valueims: 3,
          valueadsl: 0,
          valuedd: 0,
          valuegp: 0,
        },
        loai_tb: {
          list: [],
          value: 0,
          valuedau: 0,
          valuecuoi: 0,
          valuemgwan: 0,
        },
        chu_quan: {
          list: [],
          value: 0,
          valuewan: 0,
          valueldmgwan: 0,
          valuetsl: 0,
          valuecd: 143,
          valueldcd: 9,
          valueims: 0,
          valueldims: 0,
          valueadsl: 0,
          valueldadsl: 0,
          valuedd: 0,
          valuegp: 0,
        },
        tien_km: {
          list: [],
          value: 0,
        },
        doituong: {
          list: [],
          value: "",
        },
        dichvu_vt: {
          list: [],
          value: DichVuVienThong.CO_DINH,
        },
        dichvu_vt_moi: {
          list: [],
          value: DichVuVienThong.IMS,
        },
        loaihinh_tb: {
          list: [],
          value: 0,
        },
        loaihinh_tb_moi: {
          list: [],
          value: 0,
        },
        loai_khl: {
          list: [],
          value: 0,
        },
        loai_gt: {
          list: [],
          value: 0,
        },
        phanloai_kh: {
          list: [],
          value: 0,
        },
        loai_kh: {
          list: [],
          value: null,
        },
        nganhang: {
          list: [],
          value: 0,
          valuekh: 0,
          sotaikhoankh: "",
          sotaikhoan: "",
          ischecked: false,
          ischeckedkh: false,
          isnganhangnn: false,
        },
        hinhthuc_tt: {
          list: [],
          value: 0,
        },
        hinhthuc_tc: {
          list: [],
          value: 0,
          valueims: 0,
          enabled: false,
        },
        dantoc: {
          list: [],
          value: 0,
        },
        nganhnghe: {
          list: [],
          value: 0,
        },
        nganhngheloc: {
          list: [],
          value: 0,
        },
        quoctich: {
          list: [],
          value: 0,
        },
        hinhthuc_tthi: {
          list: [],
          value: 0,
        },
        kieu_hd: {
          list: [],
          value: 0,
        },
        goi_kt: {
          list: [],
          value: 0,
        },
        donvi_ql: {
          list: [],
          value: 0,
          valueql: 0,
        },
        kieu_ld: {
          list: [],
          value: KieuLapDat.DM_CODINH,
        },
        nhacc_cu: {
          list: [],
          value: 0,
          ischecked: false,
        },
        congnghe: {
          list: [],
          value: 0,
        },
        hinhthuc_cm: {
          list: [],
          value: 0,
        },
        thoihan: {
          list: [],
          value: 0,
          valuetsl: 0,
          valuewan: 0,
          valuecd: 0,
          valueadsl: 0,
          valueims: 0,
        },
        loai_csht: {
          list: [],
          value: 0,
        },
        doitac: {
          list: [],
          valueims: 0,
          valuecsht: 0,
          valueinternet: 0,
          valuecd: 0,
          ischeckedcd: false,
        },
        loaiip: {
          list: [],
          value: 0,
        },
        phanloai_dv: {
          list: [],
          value: 0,
        },
        ht_chitietdv_ims: {
          list: [],
          value: 0,
        },
        ht_phantach_tocdo: {
          list: [],
          value: 0,
        },
        trangthai_yc: {
          list: [],
          value: 0,
        },
        donvi_dau: {
          list: [],
          value: 0,
        },
        donvi_cuoi: {
          list: [],
          value: 0,
        },
        tinh_kn: {
          list: [],
          value: 0,
        },
        loaikenh: {
          list: [],
          value: 0,
          valuetsl: 0,
        },
        tocdokenh: {
          list: [],
          valuecir: 0,
          valuepir: 0,
          valuetsl: 0,
          valueisp: 0,
          valuenix: 0,
          ischeckedpir: false,
          ischeckedisp: false,
          ischeckednix: false,
        },
        doitactsl: {
          list: [],
          value: 0,
        },
        loaimodem: {
          list: [],
          value: 0,
        },
        loaicap: {
          list: [],
          value: 0,
          valuedau: 0,
          valuecuoi: 0,
        },
        kieutra: {
          list: [],
          value: 0,
        },
        thangtra: {
          list: [],
          value: 0,
        },
        muccuoc: {
          list: [],
          value: 0,
        },
        muccuoc_tb: {
          list: [],
          value: 0,
        },
        thanhtoan: {
          list: [],
          value: "",
        },
        thanhtoancbo: {
          list: [],
          value: "",
        },
        danhba: {
          list: [],
          value: 0,
        },
        dshopdong_chuaht: {
          list: [],
          header: [],
          value: 0,
        },
        madaidien: {
          list: [],
          value: null,
        },
        vetinhcd: {
          list: [],
          value: 0,
        },
        tramtb_ims: {
          list: [],
          value: 0,
        },
        thietbi_ims: {
          value: 1,
          list: [
            {
              THIETBI_ID: 1,
              TEN_TBI: "DSLAM",
              MA_TBI: "DSLAM",
            },
            {
              THIETBI_ID: 2,
              TEN_TBI: "SWITCH L2",
              MA_TBI: "SWITCH L2",
            },
            {
              THIETBI_ID: 4,
              TEN_TBI: "MXU",
              MA_TBI: "MXU",
            },
            {
              THIETBI_ID: 6,
              TEN_TBI: "LITESPAN",
              MA_TBI: "LITESPAN",
            },
            {
              THIETBI_ID: 7,
              TEN_TBI: "GPON",
              MA_TBI: "GPON",
            },
            {
              THIETBI_ID: 8,
              TEN_TBI: "MAN E ",
              MA_TBI: "MAN E",
            },
            {
              THIETBI_ID: 12,
              TEN_TBI: "IAD",
              MA_TBI: "IAD",
            },
            {
              THIETBI_ID: 13,
              TEN_TBI: "MODEM ADSL",
              MA_TBI: "MADSL",
            },
          ],
        },
        tram_tc: {
          list: [],
          value: 0,
        },
        trunk: {
          list: [],
          value: 0,
        },
      },
      filterbox: {
        ctv: {
          value: "",
          ischecked: false,
          ma_ctv: "",
        },
        nguoigt: {
          value: "",
          ischecked: false,
          nguoigt_id: "",
        },
        khuyenmai: {
          list: [],
          value: "",
          ischecked: false,
        },
        tratruoc: {
          list: [],
          value: "",
          ischecked: false,
        },
      },
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
      ts_sinhma_gd_theo_donvi: false,
      TUDONG_SINH_SOAO: false, //false,
      TD_GANTHEM_HDTB_DV: false,
      bDuLieuKS: false, // chi bang true khi lay du lieu tu btnLayDulieuKS_Click
      phieucskhdv_id: 0,
      loi_sinh_magd: false,
      KetCuoi_ID: -1,
      loainv_ctv_hrm: 0,
      loainv_ngt_hrm: 0,
      ts_kt_tthdkhdn: 0,
      ts_kt_ltb_hrm: false,
      huonggiao_tn: 0,
      quytrinh_tn: 0,
      words: "",
      vkieuhd_id: 0,
    };
  },
  methods: {
    async pageLoad() {
      this.may_cn = await this.$root.token.getMachine();
      this.ip_cn = await this.$root.token.getIP();
    },
    ShowSearchAccount() {
      this.popupComponent = () =>
        import("@/modules/search/subscriber/SearchAccount");
      this.popupComponentName = "SearchAccount";
      try {
        this.popupComponent.data.isPopup = true;
      } catch (ex) {}
      this.Popup("popupComponents");
    },
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal();
    },
    acceptSearchContract(item) {
      this.khachhang.magd = item.ma_gd;
      if (this.khachhang.magd && this.khachhang.magd.trim() != "")
        this.MaGDChanged();
    },
    async MaGDChanged() {
      try {
        this.loading(true)
        this.lvwThueBao = await this.LAY_DS_HOPDONG_THEO_MA_GD();
        if (this.lvwThueBao.length <= 0) {
          this.$toast.warning("Mã giao dịch này không tồn tại!");
          return;
        }

        await this.HienThiTTHopDongKH(this.lvwThueBao);
      } catch (e) {} finally { this.loading(false) }
    },
    async LAY_DS_HOPDONG_THEO_MA_GD() {
      if (!this.khachhang.magd) return;

      var ds_hopdong = [];
      var pdata = {
        in_donvi_dl_id: 0,
        in_donvi_id: this.$root.token.getDonViID() * 1,
        in_loaihd_id: LoaiHopDong.CHUYENDOI_LH, // Test data 1, default code 0
        in_ma_gd: this.khachhang.magd,
        in_nhanvien_id: this.$root.token.getNhanVienID() * 1,
        in_tthd_id: TrangThaiHD.MOI,
      };
      if (this.iKieu != 0) {
        if (!this.$root.token.getDonViID()) pdata.in_donvi_id = 0;
        pdata.in_tthd_id = 0;
        pdata.in_nhanvien_id = 0;
      }

      await API.post_lay_ds_hopdong_theo_ma_gd(this.axios, pdata).then(
        (response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            ds_hopdong = response.data.data;
          }
        }
      );
      return ds_hopdong;
    },
    async txtMaTB_KeyPress() {
      this.loading(true);      
      this.state.ds_lay_tien_khoanmuc_ld = null;
      var ma_tb = this.thuebao.codinh.somay;
      var ds = [];
      await API.post_lay_ds_hopdong_theo_ma_tb(this.axios, {
        ma_tb: ma_tb,
        in_dichvuvt_id: this.listbox.dichvu_vt_moi.value * 1,
        in_donvi_dl_id: 0,
        in_donvi_id: this.$root.token.getDonViID(),
        in_loaihd_id: LoaiHopDong.CHUYENDOI_LH,
        in_loaitb_id: 0,
        in_nhanvien_id: this.$root.token.getNhanVienID(),
        in_tthd_id: TrangThaiHD.MOI,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });
      // this.clear(); bỏ clear form theo ý 2 của bug https://cntt.vnpt.vn/browse/BSS-92633
      this.thuebao.codinh.somay = ma_tb;
      if (ds.length > 0) {
        // console.log("HienThiTTHopDongKH");
        await this.HienThiTTHopDongKH(ds);
      } else {
        // console.log("HienThiTT_DanhBa");
        await this.HienThiTT_DanhBa(ma_tb);
      }

      this.loading(false);
    },
    async HT_TT() {
      await API.post_lay_ds_hdtb_doi_lh_tb(this.axios, {
        hdkh_id: this.khachhang.hdkh_id,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.HienThiDanhSacHDTB(response.data.data);
        } else {
          this.HienThiDanhSacHDTB([]);
        }
      });
    },
    async MaKHChanged() {},
    async HienThiTT_DanhBa(ma_tb) {
      var ds = [];
      await API.post_lay_danhba_theo_matb(this.axios, {
        in_ma_tb: ma_tb,
        in_dichvuvt_id: this.listbox.dichvu_vt.value,
        in_donvi_dl_id: 0,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });
      await this.LAY_DS_DVGT_MACDINH(0, this.listbox.loaihinh_tb_moi.value)
      if (ds && ds.length > 0) {
        this.thuebao.tbhd_pl = 0;
        this.NgayYeuCau = moment(new Date()).format("DD/MM/YYYY");
        if (ds[0]["ngaylap_hd"])
          this.NgayLapHD = moment(
            ds[0]["ngaylap_hd"],
            "YYYY-MM-DD HH:mm:ss"
          ).format("DD/MM/YYYY");
        this.khachhang.makh = ds[0]["ma_kh"];
        this.khachhang.mahd = ds[0]["ma_hd"];
        this.khachhang.khachhang_id = ds[0]["khachhang_id"];
        this.khachhang.tenkh = this.ChuanHoaTen(ds[0]["ten_kh"]);
        this.diachiKH.DIACHI = this.ChuanHoaTen(ds[0]["diachi_kh"]);
        this.khachhang.so_gt = ds[0]["so_gt"];
        this.khachhang.dienthoai = ds[0]["so_dt"];
        if (!ds[0]["diachi_ndd"] || ds[0]["diachi_ndd"].length == 0)
          this.diachiNDD.DIACHI = this.ChuanHoaTen(ds[0]["diachi_kh"]);
        else this.diachiNDD.DIACHI = this.ChuanHoaTen(ds[0]["diachi_ndd"]);

        if (ds[0]["nganhnghe_id"]) {
          this.listbox.nganhngheloc.value = ds[0]["nganhnghe_id"];
          this.nganhnghe_id = ds[0]["nganhnghe_id"];
        }
        //Thông tin thuê bao
        this.thuebao.tentb = ds[0]["ten_tb"];
        this.thuebao.thuebao_id = ds[0]["thuebao_id"];
        this.thanhtoan.thanhtoan_id = ds[0]["thanhtoan_id"];

        this.state.loaitb_id = ds[0]["loaitb_id"].toString() * 1;
        this.listbox.loaihinh_tb.value = this.state.loaitb_id;
        this.listbox.doituong.value = ds[0]["doituong_id"].toString() * 1;
        this.listbox.donvi_ql.value = this.state.donviql_id =
          ds[0]["donviql_id"].toString() * 1;
        //donviql_id
        // if (this.listbox.tram_tc && this.listbox.tram_tc.list.length > 0){
        let dt =  commonFn.GetData(
          await API.sp_tt_dbtb_dv(this.axios, {
            p_param: ds[0]["thuebao_id"],
            p_type: 2,
          })
        );
        if (dt && dt.length > 0){
          this.listbox.tram_tc.value = dt[0].donvi_id*1;
        }
        // }

        this.thuebao.ghichu = ds[0]["ghichu_tb"];

        this.diachiLD.DIACHI = this.ChuanHoaTen(ds[0]["diachi_ld"]);
        this.diachiTB.DIACHI = this.ChuanHoaTen(ds[0]["diachi_tb"]);

        //TODO
        //bangts.HT_DonVi_loaidv_Combobox(cboVeTinhCD, tt_nd.donvi_dl_id, LOAI_DV.TRAM_VT);

        await this.HienThiTTDiaChi(
          await this.LAY_DS_DIACHI_THEO_DBKHID(this.khachhang.khachhang_id),
          1
        );
        await this.HienThiTTDiaChi(
          await this.LAY_DS_DIACHI_THEO_DBTTID(this.thanhtoan.thanhtoan_id),
          2
        );
        await this.HienThiTTDiaChi(
          await this.LAY_DS_DIACHI_THEO_DBTBID(this.thuebao.thuebao_id),
          3
        );

        this.HienThiTTMoRong_DB(
          this.thuebao.thuebao_id,
          this.listbox.dichvu_vt.value
        );
        await this.LoadMucCuocTB();
        this.HienThiThongTinKhuVucDanhBa(this.thuebao.thuebao_id);

        if (this.state.khong_ht_tracuudb == 0) {
          //TODO:
          // frmTraCuuCTDanhBa frmTCCTDB = new frmTraCuuCTDanhBa(dichvuvt_id, txtMaTB.Text);
          // frmTCCTDB.ShowDialog();
        }
      } else {
        //this.thuebao.tbhd_pl = 1;
        //this.buttons.chkLapDatBS = false;

        var dv = this.listbox.dichvu_vt.list.filter(
          (x) => x.dichvuvt_id == this.listbox.dichvu_vt.value
        )[0];
        var ten_dvvt = dv.ten_dvvt;
        this.$toast.error(
          "Không tìm thấy thông tin về thuê bao " +
          this.thuebao.codinh.somay +
          " đối với dịch vụ " +
          ten_dvvt
        );
      }
    },
    async HienThiThongTinKhuVucDanhBa(thuebao_id) {
      let dtKhuVuc = commonFn.GetData(await API.lay_tt_dbtb_kv_theo_thuebao_id(this.axios, {params:{thuebao_id: thuebao_id}}))
      if(dtKhuVuc && dtKhuVuc.length > 0) {
        this.thuebao.khuvuc = dtKhuVuc[0]["ten_kv"];
        this.thuebao.khuvuc_id = dtKhuVuc[0]["khuvuc_id"]*1;
      }
    },
    async HienThiThongTinKhuVucHopDong(hdtb_id) {
      let dtKhuVuc = commonFn.GetData(await API.lay_tt_hdtb_kv_theo_hdtb_id(this.axios, {params: { hdtb_id: hdtb_id }}))
      if(dtKhuVuc && dtKhuVuc.length > 0) {
        this.thuebao.khuvuc = dtKhuVuc[0]["ten_kv"];
        this.thuebao.khuvuc_id = dtKhuVuc[0]["khuvuc_id"]*1;
      }
    },
    async LAY_DS_HDTB_IMS_THEO_HDTB_ID(hdtb_id) {
      var ds = [];
      await API.lay_ds_hopdong_thuebao_ims(this.axios, {
        params: { hdtbId: hdtb_id },
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });
      return ds;
    },

    async LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id) {
      var ds = [];
      await API.lay_ds_hopdong_thuebao_codinh(this.axios, {
        params: { hdtbId: hdtb_id },
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });
      return ds;
    },

    async LAY_DS_DIACHI_THEO_HDTT_ID(hdtt_id) {
      var ds = [];
      await API.lay_ds_diachi_theo_hdtt(this.axios, {
        params: { hdttId: hdtt_id },
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });
      return ds;
    },
    async LAY_DS_DIACHI_THEO_HDKH_ID(hdkh_id) {
      var ds = [];
      await API.lay_ds_diachi_theo_hdkh(this.axios, {
        params: { hdKhId: hdkh_id },
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });
      return ds;
    },
    async LAY_DS_HOPDONG_TT_THEO_HDKH_ID(hdkh_id) {
      var ds = [];
      await API.lay_ds_hopdong_thanhtoan_theo_hdkh(this.axios, {
        params: { hdKhId: hdkh_id },
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });
      return ds;
    },
    async LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdkh_id) {
      var ds = [];
      await API.lay_ds_hopdong_thuebao_theo_hdkh(this.axios, {
        params: { hdKhId: hdkh_id },
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });
      return ds;
    },
    async LAY_DS_DIACHI_THEO_DBKHID(khachhang_id) {
      var ds = [];
      await API.get_lay_ds_diachi_theo_khachhang(this.axios, {
        params: { khachHangId: khachhang_id },
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });
      return ds;
    },
    async LAY_DS_DIACHI_THEO_DBTTID(thanhtoan_id) {
      var ds = [];
      await API.get_lay_ds_diachi_theo_dbttid(this.axios, {
        params: { dbttid: thanhtoan_id },
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });
      return ds;
    },
    async LAY_DS_DIACHI_THEO_DBTBID(thuebao_id) {
      var ds = [];
      await API.post_lay_ds_diachi_theo_dbtbid(this.axios, {
        in_thuebao_id: thuebao_id,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });
      return ds;
    },
    async layds_hdtbdv(insert) {
      var pdata = {
        insert: insert,
        thuebao_id: this.thuebao.thuebao_id,
        hdtb_id: this.hdtb_id,
        quytrinh_id: this.quytrinh_id,
      };
      var ds = [];
      await API.post_sp_chuyendoicdims_layds_hdtbdv(this.axios, pdata).then(
        (response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            ds = response.data.data;
          }
        }
      );

      return ds;
    },
    HT_DonVi_loaidv_Combobox: function () {
      //TODO
    },
    HT_LoaiHinh_TB_ThayDoiLHTB_Combobox: function () {
      //TODO
    },
    LoaiHinhTB_Changed: function () {
      //TODO
      this.LoaiHinhTB_moi_Changed();
    },
    async LoaiHinhTB_moi_Changed() {
      this.loading(true);
      var loaihinhtbmoi_id = this.listbox.loaihinh_tb_moi.value * 1;
      if (this.kt_load == false) {
        var loaihinhtbcu_id = this.listbox.loaihinh_tb.value * 1;
        var api2 = await API.post_lay_ds_kieuld_chuyendoi(this.axios, {
          loaihd_id: LoaiHopDong.CHUYENDOI_LH,
          loaitb_id_cu: loaihinhtbcu_id,
          loaitb_id_moi: loaihinhtbmoi_id,
        }).then((response) => {
          this.loading(false);
          this.listbox.kieu_ld.list = response.data.data.filter(
            (x) => x.kieuld_id > 0
          );

          if (
            this.listbox.kieu_ld.list &&
            this.listbox.kieu_ld.list.length > 0
          ) {
            this.listbox.kieu_ld.value =
              this.listbox.kieu_ld.list[0].kieuld_id * 1;
          }
        });
        if(this.listbox.dichvu_vt_moi.value.toString() ==
          DichVuVienThong.DICHVU_CNTT.toString() && this.listbox.loaihinh_tb_moi.value != undefined && this.listbox.loaihinh_tb_moi.value != null){
          document.getElementById("tabThueBao").click();
          //await this.Init_Properties(this.listbox.loaihinh_tb_moi.value, 'HDTB_CNTT', '00000', this.ItemList)
          this.LoadThongTin_DV_CNTT(this.listbox.loaihinh_tb_moi.value);
        }
        await this.LoadMucCuocTB(); //this.Lay_DS_MucCuoc_TB();

        if (loaihinhtbmoi_id == LoaiHinhTB.IMS_SIPTRUNKING) {
          this.controls.txtSoLuong.enabled = true;
          this.HT_DS_TRUNK_IMS();
        }
      }
      await this.LayTienTheoKhoanMuc();
      await this.LAY_DS_DVGT_MACDINH(this.hdtb_id, this.listbox.loaihinh_tb_moi.value * 1);
    },
    async LoadMucCuocTB() {
      //if(!this.diachiLD.DIACHI) return;
      var pdata = {
        apld_id: 0,
        doituong_id: this.listbox.doituong.value
          ? this.listbox.doituong.value
          : this.listbox.doituong.list[0].doituong_id,
        khuld_id: 0,
        loaitb_id: this.listbox.loaihinh_tb_moi.value,
        muccuoc_id:
          this.listbox.muccuoc.value && this.listbox.muccuoc.value > 0
            ? this.listbox.muccuoc.value
            : 0,
        ngay: this.NgayYeuCau,
        phold_id: this.diachiLD.PHO_ID ? this.diachiLD.PHO_ID : 0,
        phuongld_id: this.diachiLD.PHUONG_ID ? this.diachiLD.PHUONG_ID : 0,
        tocdo_id: 0,
        trangbi_id: this.listbox.trangbi.valuecd, //default=3
      };
      API.post_lay_ds_mucuoc_tb_v2(this.axios, pdata).then((response) => {
        this.listbox.muccuoc_tb.list = [];
        this.listbox.muccuoc_tb.value = 0;
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.listbox.muccuoc_tb.list = response.data.data;
          if (
            this.listbox.muccuoc_tb.list &&
            this.listbox.muccuoc_tb.list.length > 0
          )
            this.listbox.muccuoc_tb.value =
              this.listbox.muccuoc_tb.list[0].muccuoctb_id;
        }
      });
    },
    async Lay_DS_MucCuoc_TB() {
      this.LoadMucCuocTB();
    },
    async LAY_DS_DVGT_MACDINH(hdtb_id, loaitb_id) {
      this.m_dsThueBao_DichVu = await this.LAY_DS_THUEBAO_DICHVU(
        hdtb_id,
        0,
        loaitb_id,
        1
      );
      this.HT_DichVuGT();
    },
    async HT_DS_TRUNK_IMS() {
      API.post_ht_ds_tb_chinh_ims(this.axios, {
        khachhang_id: this.khachhang.khachhang_id,
        hdkh_id: this.khachhang.hdkh_id,
      }).then((response) => {
        this.loading(false);
        this.listbox.trunk.list = response.data.data;
      });
    },
    popupDVCongThem() {
      this.popupComponent = () =>
        import("../ExtenChangeInformationITVATServices/popupServiceAdds.vue");
      this.popupComponentName = "popupServiceAdds";
      this.state.modalTitle = "Dịch vụ cộng thêm";
      var listGVGT = [];
      if (this.m_dsThueBao_DichVu) listGVGT = this.m_dsThueBao_DichVu.filter((x) => x.dangky);
      console.log('popupDVCongThem', listGVGT)
      this.popupComponentData = {
        _loaitb_id: this.listbox.loaihinh_tb_moi.value,
        _hdtb_id: this.hdtb_id,
        _linhvuc_id: 0,
        _tocdo_id: this.tocdo_id,
        lvDvgt: listGVGT,
      };
      this.Popup("popupComponents");
    },
    popupMuaTB() {
      this.popupComponent = () =>
        import("../InstallNewSubs/Popups/popupBuyDevices");
      this.popupComponentName = "popupBuyDevices";
      this.state.modalTitle = "Mua thiết bị";
      this.popupComponentData = { _loaitb_id: this.listbox.loaihinh_tb.value };
      if (this.dsCTM_TBI && this.dsCTM_TBI.length > 0)
        this.popupComponentData.ds = this.dsCTM_TBI;
      this.Popup("popupComponents");
    },
    popupMaTN() {
      var dulieu = {};
      //var kieu = this.listbox.kieu_ld.value;
      var kieu = this.kieu_ld_kieu;
      console.log(kieu);
      switch (kieu * 1) {
        case 2:
          console.log("Vào case 2");
          if (this.khachhang.khachhang_id && this.khachhang.khachhang_id > 0) {
            dulieu.khachhang_id = this.khachhang.khachhang_id;
            dulieu.kieuld_id = this.listbox.kieu_ld.value;
            dulieu.loaitb_id = this.listbox.loaihinh_tb.value;
            dulieu.dichvuvt_id = this.listbox.dichvu_vt.value;
            this.popupComponentData = dulieu;
            this.popupComponent = () =>
              import(
                "@/modules/contract/setup/InstallNewSubs/Popups/popupDSTBCungDoiCap"
                );
            this.popupComponentName = "popupDSTBCungDoiCap";
          } else {
            this.$toast.error(
              "Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !"
            );
            return;
          }
          break;
        case 1:
          console.log("Vào case 1");
          if (this.khachhang.hdkh_id && this.khachhang.hdkh_id > 0) {
            dulieu.hdkh_id = this.khachhang.hdkh_id;
            dulieu.vkhachhang_id = this.khachhang.khachhang_id;
            dulieu.vkieuld_id = this.listbox.kieu_ld.value;
            // dulieu.loaitb_id = this.listbox.loaihinh_tb.value;
            this.popupComponentData = dulieu;
            this.popupComponent = () =>
              import("../InstallNewSubs/Popups/popupDSTBLapKem");
            this.popupComponentName = "popupDSTBLapKem";
          } else {
            this.$toast.error(
              "Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !"
            );
            return;
          }
          break;
        case 4:
          console.log("Vào case 4");
          dulieu.loaitb_id = this.listbox.loaihinh_tb.value;
          dulieu.kieuld_id = this.listbox.kieu_ld.value;
          this.popupComponentData = dulieu;
          this.popupComponent = () =>
            import("../InstallNewSubs/Popups/popupDSTBCungDoiCap_KH_Khac");
          this.popupComponentName = "popupDSTBCungDoiCap_KH_Khac";
          break;
      }
      // console.log(this.popupComponentName, dulieu);
      this.Popup("popupComponents");
    },

    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    async popupClosed(val) {
      // console.log("popupClosed", val);
      this.loading(true);
      try {
        if (!val) val = {};
        switch (this.popupComponentName) {
          case "SearchAccount":
            val = this.LowerCasePropertyList([val])[0];

            if (val.ma_kh) this.khachhang.makh = val.ma_kh;
            if (val.ma_tt) this.thanhtoan.matt = val.ma_tt;

            //ma_tb
            if (this.khachhang.makh && this.khachhang.makh.trim() != "")
              this.MaKHChanged();
            break;

          case "popupDSTBCungDoiCap":
          case "popupDSTBLapKem":
          case "popupDSTBCungDoiCap_KH_Khac":
            val = this.LowerCasePropertyList([val])[0];
            if (val && val.ma_tb) {
              this.thuebaocd.matn = val.matb_tn;
              this.thuebaocd.matn = val.ma_tb;
            }
            if (val && val.madoicap) {
              this.thuebaocd.madoicap = val.madoicap;
              this.txtMaDoiCap_ADSL = val.madoicap;
            }
            if (val && val.matb) {
              this.thuebaocd.matn = val.matb;
              this.thuebaocd.matn = val.matb;
            }

            break;

          case "popupChonMaTT":
            if (val.ma_tt) this.thanhtoan.matt = val.ma_tt;
            break;
          case "popupChonSM": // Case chon so may them moi CO_DINH
            if (!val || !val.somay) return;
            this.thuebao.codinh.somay = val.somay;
            this.thuebao.codinh.arraySoMay = val.arraySoMay
              ? val.arraySoMay
              : [];
            this.loaisoisdn = val.loaiso;
            API.post_sp_lay_danhsach_tiencamket(this.axios, val.nhomso).then(
              (response) => {
                if (
                  response &&
                  response.data &&
                  response.data.data &&
                  response.data.error_code &&
                  response.data.error_code === "BSS-00000000"
                ) {
                  this.thuebao.codinh.dgvTienCK.list = response.data.data;
                }
              }
            );
            var vtien_sodep = val.tiensodep ? val.tiensodep : 0;
            for (var item of this.ds_tien_khoanmuc)
              if (item["khoanmuctt_id"] == 23) {
                item["tien"] = vtien_sodep;
                item["vat"] = vtien_sodep * 0.01;
                this.LayTongTien_HDTB();
                break;
              }
            break;
          case "popupChonDVQL":
            if (val && val.DONVIQL_ID) {
              this.listbox.donvi_ql.valueql = val.DONVIQL_ID;
              break;
            } else {
              this.$toast.error(
                "Bắt buộc phải chọn đơn vị quản lý cho khu vực giáp ranh!"
              );
              return;
            }
          case "popupChonDVLD":
            // console.log(val);
            if (val && val.DONVIQL_ID) {
              this.listbox.donvi_ql.value = val.DONVIQL_ID;
              //this.HT_GIAODIEN_DONVI();
              break;
            } else {
              this.$toast.error(
                "Bắt buộc phải chọn đơn vị lắp đặt cho khu vực giáp ranh!"
              );
              return;
            }
          case "popupServiceAdds":
            this.m_dsThueBao_DichVu = this.LowerCasePropertyList(val);
            console.log("Đóng popup Dịch vụ cộng thêm", val);
            this.HT_DichVuGT();
            break;
          case "popupServiceOthers":
            this.dsDK_DVK_TB = val;
            this.HT_DichVuKhac(this.LowerCasePropertyList(this.dsDK_DVK_TB), 1); //default 1 tren code cu
            break;
          case "popupServiceOthersTT":
            this.dsDK_DVK_TT = val;
            this.HT_DichVuKhac(this.LowerCasePropertyList(this.dsDK_DVK_TT), 2); //default 2 tren code cu
            break;
          case "popupBuyDevices":
            this.dsCTM_TBI = val;
            this.HT_ThietBi();
            break;
          case "popupCTV":
            this.filterbox.ctv.value = val.ten_ht;
            this.filterbox.ctv.ma_ctv = val.nhanvien_id;
            break;
          case "popupNGT":
            this.filterbox.nguoigt.value = val.ten_ht;
            this.filterbox.nguoigt.nguoigt_id = val.nhanvien_id;
            break;
          case "popupChonKhuVuc":
            this.thuebao.khuvuc = val.ma_kv;
            this.dsHDTB_KV = val.hdtb_kv_data;
            this.thuebao.khuvuc_id = val.khuvuc_id;
            break;
          case "popupTienKM":
            // var kmtt_id = -1;
            // var tien_kmtt = 0;
            // var tien_vat = 0;
            this.ds_tien_khoanmuc = val;
            if (this.ds_tien_khoanmuc.length > 0) {
              for (var item of this.ds_tien_khoanmuc) {
                if (item["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DATMOI) {
                  this.dtienld = item["tien"];
                  this.dvatld = item["vat"];
                } else if (item["khoanmuctt_id"] == KHOANMUC_TT.KMTT_CAIDAT) {
                  this.dtiencd = item["tien"];
                  this.dvatcd = item["vat"];
                } else if (item["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DICHVU) {
                  this.dichvu.dtien_dv = item["tien"];
                  this.dichvu.dvat_dv = item["vat"];
                } else if (
                  item["khoanmuctt_id"] == KHOANMUC_TT.KMTT_MUATHIETBI
                ) {
                  this.dtientbi = item["tien"];
                  this.dvattbi = item["vat"];
                } else if (
                  item["khoanmuctt_id"] == KHOANMUC_TT.KMTT_KM_LAPDAT
                ) {
                  this.kmld.dtienkmld = item["tien"];
                  this.kmld.dvatkmld = item["vat"];
                } else if (
                  item["khoanmuctt_id"] == KHOANMUC_TT.KMTT_KM_DATCOC
                ) {
                  this.kmld.dtienkmdc = item["tien"];
                  this.kmld.dvatkmdc = item["vat"];
                  if (this.kmld.dtienkmdc > dtienkmld) {
                    this.kmld.dtienkmld = this.kmld.dtienkmdc;
                    this.kmld.dvatkmld = this.kmld.dvatkmdc;
                  }
                } else if (
                  item["khoanmuctt_id"] == KHOANMUC_TT.KMTT_KM_THIETBI
                ) {
                  this.thietbi.dtien_kmtb = item["tien"];
                  this.thietbi.dvat_kmtb = item["vat"];
                } else if (item["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DATCOC) {
                  this.kmld.dtiendatcoc_tt = item["tien"];
                  this.kmld.dvatdatcoc_tt = item["vat"];
                } else if (
                  item["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DATCOC_KM
                ) {
                  this.kmld.dtiendatcoc_km = item["tien"];
                  this.kmld.dvatdatcoc_km = item["vat"];
                }
                // else
                // {
                //     kmtt_id = item["khoanmuctt_id"];
                //     tien_kmtt = item["tien"];
                //     tien_vat = item["vat"];
                // }
                //this.HienThiTienKhuyenMaiLapDat(dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt, kmtt_id, tien_kmtt, tien_vat);
                this.HienThiTienKhuyenMaiLapDat();
                this.LayTongTien_HDTB();
              }
            }
            break;
          case "popup-ChonKhuyenMai":
            if (val.loaikm == Loai_KM.KhuyenMai)
              this.filterbox.khuyenmai.value = val.khuyenmai.khuyenmai_id;
            else this.filterbox.tratruoc.value = val.khuyenmai.khuyenmai_id;
            break;
          case "popup-DangkychitietkhuyenmaiTT":
            //TODO RECENT chua thuc hien
            await this.LaySothang_SD();
            this.dsDC_HDTB = val.dsDC_HDTB;
            this.kmld.dtienkmdc = val.tien_kmld_tt;
            this.kmld.dvatkmdc = val.vat_kmld_tt;
            this.kmld.tyle_kmld_tt = val.tyle_kmld_tt;
            this.kmld.dtiendatcoc_tt = Math.round(
              (val.tien_datcoc_tt * 10) / 11
            );
            this.kmld.dvatdatcoc_tt =
              val.tien_datcoc_tt - this.kmld.dtiendatcoc_tt;
            //Kiểm tra xem nếu không có tiền khuyến mại thì ưu tiên khuyến mại theo tỷ lệ
            if (this.kmld.dtienkmdc == 0) {
              if (this.kmld.tyle_kmld_tt > 0) {
                this.kmld.dtienkmdc = Math.round(
                  (this.dtienld * this.kmld.tyle_kmld_tt) / 100
                );
                this.kmld.dvatkmdc =
                  ((this.dtienld + this.dvatld) * this.kmld.tyle_kmld_tt) /
                  100 -
                  this.kmld.dtienkmdc;
              }
            }
            this.kmld.tong_kmld_tt = this.kmld.dtienkmdc + this.kmld.dvatkmdc;

            //Hiếu bổ sung hàm kiểm tra xem user đã chọn chi tiết khuyến mại nào chưa - 23.09.2010
            if (this.dsDC_HDTB.length == 0) {
              this.kmld.tien_kmld_tt = 0;
              this.kmld.vat_kmld_tt = 0;
              this.kmld.tyle_kmld = 0;
              this.kmld.tong_kmld_tt = 0;
              this.kmld.dtiendatcoc_tt = 0;
              this.kmld.dvatdatcoc_tt = 0;
            }
            await this.HT_ThietBi();
            await this.HT_DichVuGT();
            await this.HienThiTienKhuyenMaiLapDat(); //dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
            await this.LayTongTien_HDTB();
            break;
          case "popup-Dangkychitietkhuyenmai":
            this.LaySothang_SD();

            this.dsKM_HDTB = val.dsKM_HDTB;
            this.kmld.dtienkmld = val.tien_kmld;
            this.kmld.dvatkmld = val.vat_kmld;
            this.kmld.tyle_kmld = val.tyle_kmld;
            this.kmld.tien_datcoc = val.tien_datcoc;
            //Kiểm tra xem nếu không có tiền khuyến mại thì ưu tiên khuyến mại theo tỷ lệ
            if (this.kmld.dtienkmld == 0) {
              if (this.kmld.tyle_kmld > 0) {
                this.kmld.dtienkmld = Math.round(
                  (this.dtienld * this.kmld.tyle_kmld) / 100
                );
                this.kmld.dvatkmld =
                  ((this.dtienld + this.dvatld) * this.kmld.tyle_kmld) / 100 -
                  this.kmld.dtienkmld;
              }
            }
            this.kmld.tong_kmld = this.kmld.dtienkmld + this.kmld.dvatkmld;

            //Hiếu bổ sung hàm kiểm tra xem user đã chọn chi tiết khuyến mại nào chưa - 23.09.2010
            if (this.dsKM_HDTB.length > 0) {
            } else {
              this.kmld.dtienkmld = 0;
              this.kmld.dvatkmld = 0;
              this.kmld.tong_kmld = 0;
              this.kmld.tien_datcoc = 0;
              this.kmld.tyle_kmld = 0;
            }
            //HienThiTienKhuyenMaiLapDat(dtienkmld, dvatkmld, tien_datcoc);
            this.HT_ThietBi();
            this.HT_DichVuGT();
            this.HienThiTienKhuyenMaiLapDat(); //dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
            this.LayTongTien_HDTB();
            break;
          case "popupChonMaInGhep":
            if (val && val.hdmig_id) {
              this.thanhtoan.hdmig_id = val.hdmig_id;
              this.thanhtoan.mainghep = val.main_ghep;
            } else {
              if (this.hdtt_id && this.hdtt_id > 0)
                await API.post_lay_ds_hopdong_tt_theo_hdtt_id(this.axios, {
                  hdtt_id: this.hdtt_id,
                }).then(async (response) => {
                  if (
                    response &&
                    response.data &&
                    response.data.data &&
                    response.data.error_code &&
                    response.data.error_code === "BSS-00000000"
                  ) {
                    await this.HienThiTTHopDongTT(response.data.data);
                  }
                });
            }
            break;
          case "popupDSSoChinh":
            this.thuebao.codinh.sochinh = val["ma_tb"];
            this.thuebao.loaisochinh = val["loai"];
            var iid_sochinh = val["id_sochinh"];
            if (this.thuebao.loaisochinh == 1)
              this.thuebao.thuebao_id_cha = iid_sochinh;
            else this.thuebao.hdtb_id_cha = iid_sochinh;
            break;
          case "popupDSThueSo":
            this.thuebao.codinh.sochinh = val["vma_tb"];
            this.loaisoisdn = LOAISO.THUONG;
            this.thuebaocd.kieu_thueso = val["kieu_thueso"];
            this.thuebaocd.noidung_thueso = val["noidung_thueso"];
            this.thuebaocd.sothang_thueso = val["sothang_thueso"];
            var vtongdai_id = val["vdonvi_id"];
            this.thuebao.thuebao_id = val["thuebao_id"];
            await API.post_fn_lay_tt_chon_sm(this.axios, {
              ds_para: JSON.stringify({
                DICHVUVT_ID: this.listbox.dichvu_vt.value,
                KIEULD_ID: this.listbox.kieu_ld.value,
                KHACHHANG_ID: this.khachhang.khachhang_id,
                TONGDAI_ID: vtongdai_id,
                NHOMSO_ID: val["vnhomso_id"],
                CHAPNHAN: 1,
                QUANLD_ID: this.diachiLD.QUAN_ID,
                PHUONGLD_ID: this.diachiLD.PHUONG_ID,
              }),
            }).then(async (response) => {
              if (
                response &&
                response.data &&
                response.data.data &&
                response.data.error_code &&
                response.data.error_code === "BSS-00000000"
              ) {
                var apidata = JSON.parse(response.data.data);
                if (apidata["ERROR_CODE"] != 1) {
                  this.$toast.error(apidata["MESSAGE"]);
                  return;
                }
                this.thuebao.codinh.dgvTienCK.list =
                  apidata["RESULT"]["DS_TIENCK"];
                if (apidata["RESULT"]["DONVI_QL_ID"])
                  for (var i = 0; i < this.DT_CONTROLS.list.length; i++) {
                    if (
                      this.DT_CONTROLS.list[i]["LOAIDV_DICH_ID"] ==
                      LOAI_DV.TRAM_VT ||
                      this.DT_CONTROLS.list[i]["LOAIDV_DICH_ID"] == LOAI_DV.HOST
                    ) {
                      var dl = this.DT_DV(i).filter(
                        (x) => x.id == apidata["RESULT"]["DONVI_QL_ID"]
                      );
                      if (dl && dl.length > 0)
                        this.DT_CONTROLS.value[i] =
                          apidata["RESULT"]["DONVI_QL_ID"];
                    }
                  }
              }
            });
            break;
          case "popupChonSMThue":
            break;
        }
      } catch (ex) {
        this.$toast.error("" + ex);
      } finally {
        this.ClosePopup("popupComponents");
        this.loading(false);
      }
    },
    ShowPopupChonSM() {
      this.popupComponent = () =>
        import("../InstallNewSubs/Popups/popupChonSM");
      this.popupComponentName = "popupChonSM";
      this.popupComponentData = {
        _quan_id: this.diachiLD.QUAN_ID,
        _phuong_id: this.diachiLD.PHUONG_ID,
        _loaiso_id: LOAISO.THUONG,
        _thueso: false,
        loaitb_id: this.listbox.loaihinh_tb.value,
        dichvuvt_id: 0,
        hdkh_id: this.khachhang.hdkh_id,
        donvi_ql_id: this.listbox.donvi_ql.value, //ql (lay field don vi lap dat.)
      };
      this.Popup("popupComponents");
    },
    ShowPopupCTV() {
      if (this.filterbox.ctv.ischecked) {
        this.popupComponent = () =>
          import(
            "@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien"
            );
        this.popupComponentName = "popupCTV";
        // this.popupComponentData = {
        //   loainv_id: 903,
        // };
        // SearchAccount.data.isPopup = true;
        this.Popup("popupComponents");
      }
    },
    ShowPopupNguoiGT() {
      if (this.filterbox.nguoigt.ischecked) {
        this.popupComponent = () =>
          import(
            "@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu"
            );
        this.popupComponentName = "popupNGT";
        // SearchAccount.data.isPopup = true;
        this.Popup("popupComponents");
      }
    },
    async popupChonKhuVuc() {
      this.popupComponent = () =>
        import("../InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao");
      this.popupComponentName = "popupChonKhuVuc";
      var data = {
        diachi: this.diachiLD,
        khuvuc_id: this.thuebao.khuvuc_id,
        hdtb_id: this.hdtb_id,
        thuebao_id: this.thuebao.thuebao_id,
        donvi_id: this.listbox.donvi_ql.value,
      };
      data.hdtb_id = 0;
      if (this.hdtb_id && this.hdtb_id != 0) data.hdtb_id = this.hdtb_id;
      else data.thuebao_id = this.thuebao.thuebao_id;

      this.state.modalTitle = "Gán khu vực quản lý thuê bao";
      // console.log("popupChonKhuVuc DATA", data);
      this.popupComponentData = data;
      //if(this.dsCTM_TBI && this.dsCTM_TBI.length>0) this.popupComponentData.ds = this.dsCTM_TBI;
      this.Popup("popupComponents");

      //   let data = {
      //     hdtb_id: this.hdtb_id,
      //     donviql_id: 0
      //   }

      //   this.state.modalTitle = "Gán khu vực quản lý thuê bao";
      //   this.dialogComponent = () => import("@/modules/contract/setup/SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue");
      //   this.popupComponentName = "popupChonKhuVuc";
      //   this.popupComponentData = data;
      //   this.popupComponentEvts.accept = this.popupClosed;
      //   await this.dialogComponent().then((x) => {});
      //   this.$nextTick(() => {
      //     this.$refs.generalComponent.showModal();
      //   });
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

      // console.log("getDiaChi: " + datatype, this[datatype]);
    },
    getDiaChiKH: function (data) {
      this.getDiaChi(data, "diachiKH");
      //TODO tim don vi quanly cboDonViQL
      //this.listbox.donvi_ql.value="";
      if (!this.diachiTT.DIACHI)
        this.diachiTT = Object.assign({}, this.diachiKH);
      if (!this.diachiCT.DIACHI)
        this.diachiCT = Object.assign({}, this.diachiKH);
      if (!this.diachiLD.DIACHI)
        this.diachiLD = Object.assign({}, this.diachiKH);
      if (!this.diachiTB.DIACHI)
        this.diachiTB = Object.assign({}, this.diachiKH);
      if (!this.diachiHK.DIACHI)
        this.diachiHK = Object.assign({}, this.diachiKH);
      if (!this.diachiNDD.DIACHI)
        this.diachiNDD = Object.assign({}, this.diachiKH);
    },
    getDiaChiHK: function (data) {
      this.getDiaChi(data, "diachiHK");
    },
    getDiaChiNDD: function (data) {
      this.getDiaChi(data, "diachiNDD");
    },
    getDiaChiTT: function (data) {
      this.getDiaChi(data, "diachiTT");
    },
    getDiaChiCT: function (data) {
      this.getDiaChi(data, "diachiCT");
    },
    getDiaChiBC: function (data) {
      this.getDiaChi(data, "diachiBC");
    },
    getDiaChiTB: function (data) {
      this.getDiaChi(data, "diachiTB");
      if (
        !this.diachiLD.DIACHI ||
        (this.diachiLD.DIACHI == this.diachiKH.DIACHI &&
          this.diachiLD.DIACHI != this.diachiTB.DIACHI)
      )
        this.diachiLD = Object.assign({}, this.diachiTB);
    },
    getDiaChiLD: function (data) {
      this.getDiaChi(data, "diachiLD");
    },
    getDiaChiKN: function (data) {
      this.getDiaChi(data, "diachiKN");
    },
    getDiaChiDau: function (data) {
      this.getDiaChi(data, "diachiDau");
    },
    getDiaChiCuoi: function (data) {
      this.getDiaChi(data, "diachiCuoi");
    },
    popupTimNVThuCuoc: function () {
      //this.$refs.refTimNVThuCuoc.openDialog()
      this.$refs.dlgTimKiemNVTC.openDialog();
      //this.$toast.error('Chức năng chưa được phát triển')
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
    SetButton(kieu) {},
    HienThiTTDiaChi(ds, kieu) {
      ds = this.LowerCasePropertyList(ds);
      // console.log("HienThiTTDiaChi: kieu= " + kieu, ds);
      if (ds.length > 0) {
        switch (kieu) {
          case 1:
            this.diachikh_id = ds[0]["diachi_id"]
              ? ds[0]["diachi_id"]
              : ds[0]["diachiid"];
            this.diachiKH.TINH_ID = ds[0]["tinh_id"]
              ? ds[0]["tinh_id"]
              : ds[0]["tinhid"];
            this.diachiKH.QUAN_ID = ds[0]["quan_id"]
              ? ds[0]["quan_id"]
              : ds[0]["quanid"];
            this.diachiKH.PHUONG_ID = ds[0]["phuong_id"]
              ? ds[0]["phuong_id"]
              : ds[0]["phuongid"];
            if (ds[0]["pho_id"] || ds[0]["phoid"])
              this.diachiKH.PHO_ID = ds[0]["pho_id"]
                ? ds[0]["pho_id"]
                : ds[0]["phoid"];
            else this.diachiKH.PHO_ID = 0;
            if (ds[0]["ap_id"] || ds[0]["apid"])
              this.diachiKH.AP_ID = ds[0]["ap_id"]
                ? ds[0]["ap_id"]
                : ds[0]["apid"];
            else this.diachiKH.AP_ID = 0;
            if (ds[0]["khu_id"] || ds[0]["khuid"])
              this.diachiKH.KHU_ID = ds[0]["khu_id"]
                ? ds[0]["khu_id"]
                : ds[0]["khuid"];
            else this.diachiKH.KHU_ID = 0;
            if (ds[0]["dacdiem_id"] || ds[0]["dacdiemid"])
              this.diachiKH.DACDIEM_ID = ds[0]["dacdiem_id"]
                ? ds[0]["dacdiem_id"]
                : ds[0]["dacdiemid"];
            else this.diachiKH.DACDIEM_ID = 0;
            this.diachiKH.SO_NHA = this.ChuanHoaTen(ds[0]["sonha"]);
            if (ds[0]["vido"] && ds[0]["vido"])
              this.diachiKH.Lat = ds[0]["vido"];
            else this.diachiKH.Lat = 0;
            if (ds[0]["kinhdo"] && ds[0]["kinhdo"])
              this.diachiKH.Lng = ds[0]["kinhdo"];
            else this.diachiKH.Lng = 0;
            break;
          case 2:
            this.diachitt_id = ds[0]["diachi_id"]
              ? ds[0]["diachi_id"]
              : ds[0]["diachiid"];
            this.diachiTT.TINH_ID = ds[0]["tinh_id"]
              ? ds[0]["tinh_id"]
              : ds[0]["tinhid"];
            this.diachiTT.QUAN_ID = ds[0]["quan_id"]
              ? ds[0]["quan_id"]
              : ds[0]["quanid"];
            this.diachiTT.PHUONG_ID = ds[0]["phuong_id"]
              ? ds[0]["phuong_id"]
              : ds[0]["phuongid"];
            if (ds[0]["pho_id"] || ds[0]["phoid"])
              this.diachiTT.PHO_ID = ds[0]["pho_id"]
                ? ds[0]["pho_id"]
                : ds[0]["phoid"];
            else this.diachiTT.PHO_ID = 0;
            if (ds[0]["ap_id"] && ds[0]["apid"])
              this.diachiTT.AP_ID = ds[0]["ap_id"]
                ? ds[0]["ap_id"]
                : ds[0]["apid"];
            else this.diachiTT.AP_ID = 0;
            if (ds[0]["khu_id"] || ds[0]["khuid"])
              this.diachiTT.KHU_ID = ds[0]["khu_id"]
                ? ds[0]["khu_id"]
                : ds[0]["khuid"];
            else this.diachiTT.KHU_ID = 0;
            if (ds[0]["dacdiem_id"] || ds[0]["dacdiemid"])
              this.diachiTT.DACDIEM_ID = ds[0]["dacdiem_id"]
                ? ds[0]["dacdiem_id"]
                : ds[0]["dacdiemid"];
            else this.diachiTT.DACDIEM_ID = 0;
            this.diachiTT.SO_NHA = this.ChuanHoaTen(ds[0]["sonha"]);
            if (ds[0]["vido"] && ds[0]["vido"])
              this.diachiTT.Lat = ds[0]["vido"];
            else this.diachiTT.Lat = 0;
            if (ds[0]["kinhdo"] && ds[0]["kinhdo"])
              this.diachiTT.Lng = ds[0]["kinhdo"];
            else this.diachiTT.Lng = 0;

            if (ds[0]["diachibc_id"] || ds[0]["diachibcid"])
              this.diachibc_id = ds[0]["diachibc_id"]
                ? ds[0]["diachibc_id"]
                : ds[0]["diachibcid"];
            else this.diachibc_id = 0;
            if (ds[0]["tinhbc_id"] || ds[0]["tinhbcid"])
              this.diachiBC.TINH_ID = ds[0]["tinhbc_id"]
                ? ds[0]["tinhbc_id"]
                : ds[0]["tinhbcid"];
            else this.diachiBC.TINH_ID = 0;
            if (ds[0]["quanbc_id"] || ds[0]["quanbcid"])
              this.diachiBC.QUAN_ID = ds[0]["quanbc_id"]
                ? ds[0]["quanbc_id"]
                : ds[0]["quanbcid"];
            else this.diachiBC.QUAN_ID = 0;
            if (ds[0]["phuongbc_id"] || ds[0]["phuongbcid"])
              this.diachiBC.PHUONG_ID = ds[0]["phuongbc_id"]
                ? ds[0]["phuongbc_id"]
                : ds[0]["phuongbcid"];
            else this.diachiBC.PHUONG_ID = 0;
            if (ds[0]["phobc_id"] || ds[0]["phobcid"])
              this.diachiBC.PHO_ID = ds[0]["phobc_id"]
                ? ds[0]["phobc_id"]
                : ds[0]["phobcid"];
            else this.diachiBC.PHO_ID = 0;
            if (ds[0]["apbc_id"] || ds[0]["apbcid"])
              this.diachiBC.AP_ID = ds[0]["apbc_id"]
                ? ds[0]["apbc_id"]
                : ds[0]["apbcid"];
            else this.diachiBC.AP_ID = 0;
            if (ds[0]["khubc_id"] || ds[0]["khubcid"])
              this.diachiBC.KHU_ID = ds[0]["khubc_id"]
                ? ds[0]["khubc_id"]
                : ds[0]["khubcid"];
            else this.diachiBC.KHU_ID = 0;
            if (ds[0]["dacdiembc_id"] || ds[0]["dacdiembcid"])
              this.diachiBC.DACDIEM_ID = ds[0]["dacdiembc_id"]
                ? ds[0]["dacdiembc_id"]
                : ds[0]["dacdiembcid"];
            else this.diachiBC.DACDIEM_ID = 0;
            if (ds[0]["sonha_bc"] || ds[0]["sonhabc"])
              this.diachiBC.SO_NHA = this.ChuanHoaTen(
                ds[0]["sonha_bc"] ? ds[0]["sonha_bc"] : ds[0]["sonhabc"]
              );
            else this.diachiBC.SO_NHA = "";
            if (ds[0]["vido_bc"] || ds[0]["vidobc"])
              this.diachiBC.Lat = ds[0]["vido_bc"]
                ? ds[0]["vido_bc"]
                : ds[0]["vidobc"];
            else this.diachiBC.Lat = 0;
            if (ds[0]["kinhdo_bc"] || ds[0]["kinhdo_bc"])
              this.diachiBC.Lng = ds[0]["kinhdo_bc"]
                ? ds[0]["kinhdo_bc"]
                : ds[0]["kinhdobc"];
            else this.diachiBC.Lng = 0;
            break;
          case 3:
            this.diachitb_id = ds[0]["diachi_id"]
              ? ds[0]["diachi_id"]
              : ds[0]["diachiid"];
            this.diachild_id = ds[0]["diachild_id"]
              ? ds[0]["diachild_id"]
              : ds[0]["diachildid"];
            this.diachiTB.TINH_ID = ds[0]["tinh_id"]
              ? ds[0]["tinh_id"]
              : ds[0]["tinhid"];
            this.diachiTB.QUAN_ID = ds[0]["quan_id"]
              ? ds[0]["quan_id"]
              : ds[0]["quanid"];
            this.diachiTB.PHUONG_ID = ds[0]["phuong_id"]
              ? ds[0]["phuong_id"]
              : ds[0]["phuongid"];
            if (ds[0]["pho_id"] || ds[0]["phoid"])
              this.diachiTB.PHO_ID = ds[0]["pho_id"]
                ? ds[0]["pho_id"]
                : ds[0]["phoid"];
            else this.diachiTB.PHO_ID = 0;
            if (ds[0]["ap_id"] || ds[0]["apid"])
              this.diachiTB.AP_ID = ds[0]["ap_id"]
                ? ds[0]["ap_id"]
                : ds[0]["apid"];
            else this.diachiTB.AP_ID = 0;
            if (ds[0]["khu_id"] || ds[0]["khuid"])
              this.diachiTB.KHU_ID = ds[0]["khu_id"]
                ? ds[0]["khu_id"]
                : ds[0]["khuid"];
            else this.diachiTB.KHU_ID = 0;
            if (ds[0]["dacdiem_id"] || ds[0]["dacdiemid"])
              this.diachiTB.DACDIEM_ID = ds[0]["dacdiem_id"]
                ? ds[0]["dacdiem_id"]
                : ds[0]["dacdiemid"];
            else this.diachiTB.DACDIEM_ID = 0;
            this.diachiTB.SO_NHA = this.ChuanHoaTen(ds[0]["sonha"]);
            if (ds[0]["vido"] && ds[0]["vido"])
              this.diachiTB.Lat = ds[0]["vido"];
            else this.diachiTB.Lat = 0;
            if (ds[0]["kinhdo"] && ds[0]["kinhdo"])
              this.diachiTB.Lng = ds[0]["kinhdo"];
            else this.diachiTB.Lng = 0;

            //Địa chỉ lắp đặt )
            if (ds[0]["tinhld_id"] || ds[0]["tinhldid"])
              this.diachiLD.TINH_ID = ds[0]["tinhld_id"]
                ? ds[0]["tinhld_id"]
                : ds[0]["tinhldid"];
            else this.diachiLD.TINH_ID = 0;
            if (ds[0]["quanld_id"] || ds[0]["quanld_id"])
              this.diachiLD.QUAN_ID = ds[0]["quanld_id"]
                ? ds[0]["quanld_id"]
                : ds[0]["quanldid"];
            else this.diachiLD.QUAN_ID = 0;
            if (ds[0]["phuongld_id"] || ds[0]["phuongldid"])
              this.diachiLD.PHUONG_ID = ds[0]["phuongld_id"]
                ? ds[0]["phuongld_id"]
                : ds[0]["phuongldid"];
            else this.diachiLD.PHUONG_ID = 0;
            if (ds[0]["phold_id"] || ds[0]["pholdid"])
              this.diachiLD.PHO_ID = ds[0]["phold_id"]
                ? ds[0]["phold_id"]
                : ds[0]["pholdid"];
            else this.diachiLD.PHO_ID = 0;
            if (ds[0]["apld_id"] || ds[0]["apldid"])
              this.diachiLD.AP_ID = ds[0]["apld_id"]
                ? ds[0]["apld_id"]
                : ds[0]["apldid"];
            else this.diachiLD.AP_ID = 0;
            if (ds[0]["khuld_id"] || ds[0]["khuldid"])
              this.diachiLD.KHU_ID = ds[0]["khuld_id"]
                ? ds[0]["khuld_id"]
                : ds[0]["khuldid"];
            else this.diachiLD.KHU_ID = 0;
            if (ds[0]["dacdiemld_id"] || ds[0]["dacdiemldid"])
              this.diachiLD.DACDIEM_ID = ds[0]["dacdiemld_id"]
                ? ds[0]["dacdiemld_id"]
                : ds[0]["dacdiemldid"];
            else this.diachiLD.DACDIEM_ID = 0;
            if (ds[0]["sonha_ld"] || ds[0]["sonhald"])
              this.diachiLD.SO_NHA = this.ChuanHoaTen(
                ds[0]["sonha_ld"] ? ds[0]["sonha_ld"] : ds[0]["sonhald"]
              );
            else this.diachiLD.SO_NHA = "";
            if (ds[0]["vido_ld"] || ds[0]["vidold"])
              this.diachiLD.Lat = ds[0]["vido_ld"]
                ? ds[0]["vido_ld"]
                : ds[0]["vidold"];
            else this.diachiLD.Lat = 0;
            if (ds[0]["kinhdo_ld"] || ds[0]["kinhdo_ld"])
              this.diachiLD.Lng = ds[0]["kinhdo_ld"]
                ? ds[0]["kinhdo_ld"]
                : ds[0]["kinhdold"];
            else this.diachiLD.Lng = 0;

            if (this.listbox.kieu_ld.value == KieuLapDat.TACH_VLAN) {
              this.diachiTB.DIACHI = ds[0]["diachi"];
              this.diachiLD.DIACHI = ds[0]["diachi_ld"]
                ? ds[0]["diachi_ld"]
                : ds[0]["diachild"];
            }

            // if (ds[0]["diachiid"]) this.diachitb_id = ds[0]["diachiid"];
            // if (ds[0]['diachildid']) this.diachild_id = ds[0]['diachildid'];
            // if (ds[0]["tinhid"]) this.diachiTB.TINH_ID = ds[0]["tinhid"];
            // if (ds[0]["quanid"]) this.diachiTB.QUAN_ID = ds[0]["quanid"];
            // if (ds[0]["phuongid"]) this.diachiTB.PHUONG_ID = ds[0]["phuongid"];
            // if (ds[0]["phoid"])
            //     this.diachiTB.PHO_ID = ds[0]["phoid"];
            // else
            //     this.diachiTB.PHO_ID = 0;
            // if (ds[0]["apid"])
            //     this.diachiTB.AP_ID = ds[0]["apid"];
            // else
            //     this.diachiTB.AP_ID = 0;
            // if (ds[0]["khuid"])
            //     this.diachiTB.KHU_ID = ds[0]["khuid"];
            // else
            //     this.diachiTB.KHU_ID = 0;
            // if (ds[0]["dacdiemid"])
            //     this.diachiTB.DACDIEM_ID = ds[0]["dacdiemid"];
            // else
            //     this.diachiTB.DACDIEM_ID = 0;

            // console.log(this.diachiLD, ds[0]);
            // TODO LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(phuongld_id, phold_id, apld_id, khuld_id, dacdiemld_id); LINE 15387
            break;
        }
      }
    },
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    async HienThiTTHopDongTT(ds) {
      console.log("HienThiTTHopDongTT", ds);

      if (ds.length > 0) {
        this.hdtt_id = ds[0]["hdtt_id"] * 1;
        this.thanhtoan.thanhtoan_id = this.thanhtoan_id =
          ds[0]["thanhtoan_id"] * 1;

        this.HienThiTTDiaChi(
          await this.LAY_DS_DIACHI_THEO_HDTT_ID(this.hdtt_id),
          2
        );
      }
    },
    async HienThiTTHopDongTB(ds) {
      // console.log("HienThiTTHopDongTB", ds);
      if (ds.length <= 0) return;
      var data = ds[0];
      //this.ma_tb = data.ma_tb;
      this.thuebao.codinh.somay = data.ma_tb;
      this.thuebao.tentb = this.ChuanHoaTen(data.ten_tb);
      this.diachiLD.DIACHI = this.ChuanHoaTen(data.diachi_ld);
      this.diachiTB.DIACHI = this.ChuanHoaTen(data.diachi_tb);

      this.huonggiao_tn = ds[0]["huonggiao_id"];
      this.quytrinh_id = ds[0]["quytrinh_id"];
      this.luong_id = ds[0]["luong_id"];
      this.listbox.loaihinh_tb.value =
        commonFn.GetData(
          await API.fn_tt_db_thuebao(this.axios, {
            param: data.thuebao_id * 1,
            type: 8,
          })
        ) * 1;

      let loaitb_id = ds[0]["loaitb_id"];
      this.listbox.loaihinh_tb_moi.value = loaitb_id;

      this.listbox.kieu_ld.value = data.kieuld_id * 1;
      this.listbox.doituong.value = data.doituong_id * 1;
      this.listbox.donvi_ql.value = data.donviql_id * 1;
      this.thuebao.ghichu = data.ghichu;

      await API.get_lay_ds_diachi_theo_hdtbid(this.axios, {
        params: {
          hdtbId: this.hdtb_id,
        },
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.HienThiTTDiaChi(response.data.data, 3); //LINE 13862
        }
      });

      //cmUlt.CopyDataSet(tchopdong.LAY_DS_MUA_TBI(hdtb_id), dsCTM_TBI);
      await API.post_lay_ds_mua_tbi(this.axios, {
        hdtb_id: this.hdtb_id,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.dsCTM_TBI = response.data.data;
        }
      });

      await this.HT_ThietBi();

      this.m_dsThueBao_DichVu = await this.LAY_DS_THUEBAO_DICHVU(
        this.hdtb_id,
        0,
        this.listbox.loaihinh_tb_moi.value,
        0
      );

      await this.HT_DichVuGT();

      //LayTongTien_HDTB(tchopdong.LAY_TIEN_HD_KM_THEO_HDTB(hdtb_id));
      await API.post_lay_tien_hd_km_theo_hdtb(this.axios, {
        hdtb_id: this.hdtb_id,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          this.ds_tien_khoanmuc = response.data.data;
        }
      });
      await this.LayTongTien_HDTB();

      await this.HienThiTTMoRong_DB(
        this.thuebao.thuebao_id,
        this.listbox.dichvu_vt.value
      );

      await this.HienThiTTMoRong_HD(
        this.hdtb_id,
        this.listbox.dichvu_vt_moi.value
      );
      await this.HienThiTTMoRong(this.hdtb_id,
        this.listbox.dichvu_vt_moi.value)
      await this.HienThiThongTinKhuVucHopDong(this.thuebao.thuebao_id);
    },
    async LAY_DS_THUEBAO_DICHVU(hdtb_id, thuebao_id, loaitb_id, kieu_id) {
      // if (!this.hdtb_id || this.hdtb_id <= 0 || !loaitb_id || loaitb_id <= 0)
      // if (!this.hdtb_id || this.hdtb_id <= 0 || !loaitb_id || loaitb_id <= 0)
      //   return [];

      var ds = [];
      await API.post_lay_ds_thuebao_dichvu(this.axios, {
        hdtb_id: hdtb_id?hdtb_id * 1:0,
        thuebao_id: thuebao_id?thuebao_id:0,
        loaitb_id: loaitb_id,
        kieu_id: kieu_id?kieu_id:0,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          ds = response.data.data;
        }
      });

      return ds;
    },
    async HienThiTTMoRong_HD(hdtb_id, dichvuvt_id) {
      this.txtSoLuong = "";
      var ds = [];
      // switch (dichvuvt_id) {
      //   case DichVuVienThong.IMS:
      //     ds = await this.LAY_DS_HDTB_IMS_THEO_HDTB_ID(hdtb_id);
      //     break;
      //   case DichVuVienThong.CO_DINH:
      //     ds = await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id);
      //     break;
      //   default:
      //     ds = [];
      //     break;
      // }
      if(dichvuvt_id == DichVuVienThong.IMS)
        ds = await this.LAY_DS_HDTB_IMS_THEO_HDTB_ID(hdtb_id);
      if(dichvuvt_id == DichVuVienThong.CO_DINH)
        ds = await this.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id);
      if (!ds || ds.length <= 0) return;

      // switch (dichvuvt_id) {
      //   case DichVuVienThong.IMS:
      //     if (ds[0]["trangbi_id"])
      //       this.listbox.trangbi.valueims = ds[0]["trangbi_id"] * 1;
      //     if (ds[0]["madoicap"]) {
      //       this.thuebaocd.madoicap = ds[0]["madoicap"] * 1;
      //       this.txtMaDoiCap_ADSL = this.thuebaocd.madoicap;
      //     } else {
      //       this.thuebaocd.madoicap = 0;
      //       this.txtMaDoiCap_ADSL = "";
      //     }
      //     this.thuebaocd.matn = ds[0]["matb_tn"];
      //     this.pass_ims = ds[0]["password"];
      //     this.txtSoLuong = ds[0]["sl_cuocgoi"];
      //     this.ip_ims = ds[0]["ip"];
      //     if (ds[0]["ctdv_id"])
      //       this.listbox.ht_chitietdv_ims.value = ds[0]["ctdv_id"] * 1;
      //     else this.listbox.ht_chitietdv_ims.value = 0;
      //
      //     if (ds[0]["chuquan_id"]) this.vchuquan_id = ds[0]["chuquan_id"] * 1;
      //     else this.vchuquan_id = 0;
      //
      //     if (ds[0]["chuquanld_id"]) this.vchuquanld_id = ds[0]["chuquanld_id"];
      //     else this.vchuquanld_id = 0;
      //
      //     try {
      //       this.listbox.hinhthuc_tc.value = ds[0]["HTTC_ID"] * 1;
      //       //cboHinhThucTC_IMS.ItemIndex = cboHinhThucTC_IMS.Properties.GetDataSourceRowIndex("HTTC_ID", ds.Tables[0].Rows[0]["HTTC_ID"]);
      //     } catch (ex) {}
      //
      //     break;
      //   case DichVuVienThong.CO_DINH:
      //     if (ds[0]["chuquan_id"]) this.vchuquan_id = ds[0]["chuquan_id"] * 1;
      //     else this.vchuquan_id = 0;
      //
      //     if (ds[0]["chuquanld_id"])
      //       this.vchuquanld_id = ds[0]["chuquanld_id"] * 1;
      //     else this.vchuquanld_id = 0;
      //     break;
      //   default:
      //     break;
      // }
      if(dichvuvt_id == DichVuVienThong.IMS){
        if (ds[0]["trangbi_id"])
          this.listbox.trangbi.valueims = ds[0]["trangbi_id"] * 1;
        if (ds[0]["madoicap"]) {
          this.thuebaocd.madoicap = ds[0]["madoicap"] * 1;
          this.txtMaDoiCap_ADSL = this.thuebaocd.madoicap;
        } else {
          this.thuebaocd.madoicap = 0;
          this.txtMaDoiCap_ADSL = "";
        }
        this.thuebaocd.matn = ds[0]["matb_tn"];
        this.pass_ims = ds[0]["password"];
        this.txtSoLuong = ds[0]["sl_cuocgoi"];
        this.ip_ims = ds[0]["ip"];
        if (ds[0]["ctdv_id"])
          this.listbox.ht_chitietdv_ims.value = ds[0]["ctdv_id"] * 1;
        else this.listbox.ht_chitietdv_ims.value = 0;

        if (ds[0]["chuquan_id"]) this.vchuquan_id = ds[0]["chuquan_id"] * 1;
        else this.vchuquan_id = 0;

        if (ds[0]["chuquanld_id"]) this.vchuquanld_id = ds[0]["chuquanld_id"];
        else this.vchuquanld_id = 0;

        try {
          this.listbox.hinhthuc_tc.value = ds[0]["HTTC_ID"] * 1;
          //cboHinhThucTC_IMS.ItemIndex = cboHinhThucTC_IMS.Properties.GetDataSourceRowIndex("HTTC_ID", ds.Tables[0].Rows[0]["HTTC_ID"]);
        } catch (ex) {}
      }
      if(dichvuvt_id == DichVuVienThong.IMS){
        if (ds[0]["chuquan_id"]) this.vchuquan_id = ds[0]["chuquan_id"] * 1;
        else this.vchuquan_id = 0;

        if (ds[0]["chuquanld_id"])
          this.vchuquanld_id = ds[0]["chuquanld_id"] * 1;
        else this.vchuquanld_id = 0;
      }
    },
    async HienThiTTMoRong_DB(thuebao_id, dichvuvt_id) {
      var ds = [];
      if(dichvuvt_id == DichVuVienThong.CO_DINH){
        await API.post_lay_ds_db_cd_theo_tbid(this.axios, {
          thuebao_id: thuebao_id,
        }).then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            ds = response.data.data;
          }
        });
      }
      if(dichvuvt_id == DichVuVienThong.IMS){
        await API.post_lay_ds_db_ims_theo_tbid(this.axios, {
          thuebao_id: thuebao_id,
        }).then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            ds = response.data.data;
            console.log(ds);
          }
        });
      }

      // switch (dichvuvt_id) {
      //   case DichVuVienThong.CO_DINH:
      //     await API.post_lay_ds_db_cd_theo_tbid(this.axios, {
      //       thuebao_id: thuebao_id,
      //     }).then((response) => {
      //       if (
      //         response &&
      //         response.data &&
      //         response.data.data &&
      //         response.data.error_code &&
      //         response.data.error_code === "BSS-00000000"
      //       ) {
      //         ds = response.data.data;
      //       }
      //     });
      //     break;
      //   case DichVuVienThong.IMS:
      //     await API.post_lay_ds_db_ims_theo_tbid(this.axios, {
      //       thuebao_id: thuebao_id,
      //     }).then((response) => {
      //       if (
      //         response &&
      //         response.data &&
      //         response.data.data &&
      //         response.data.error_code &&
      //         response.data.error_code === "BSS-00000000"
      //       ) {
      //         ds = response.data.data;
      //       }
      //     });
      //     break;
      //   default:
      //     ds = [];
      //     break;
      // }
      if (ds && ds.length > 0) {
        switch (dichvuvt_id * 1) {
          case DichVuVienThong.CO_DINH:
            if (ds[0]["donvi_id"])
              this.listbox.vetinhcd.value = ds[0]["donvi_id"] * 1;
            if (ds[0]["tramtb_id"])
              this.listbox.tramtb_ims.value = ds[0]["tramtb_id"] * 1;
            if (ds[0]["trangbi_id"])
              this.listbox.trangbi.valuecd = ds[0]["trangbi_id"] * 1;
            if (ds[0]["chuquan_id"]) this.vchuquan_id = ds[0]["chuquan_id"] * 1;
            else this.vchuquan_id = 0;
            if (ds[0]["chuquanld_id"])
              this.vchuquanld_id = ds[0]["chuquanld_id"] * 1;
            else this.vchuquanld_id = 0;
            break;
          case DichVuVienThong.IMS:
            if (ds[0]["chuquan_id"]) this.vchuquan_id = ds[0]["chuquan_id"] * 1;
            else this.vchuquan_id = 0;

            if (ds[0]["chuquanld_id"])
              this.vchuquanld_id = ds[0]["chuquanld_id"] * 1;
            else this.vchuquanld_id = 0;
            break;
          default:
            break;
        }
      }
    },
    async dgrThueBao_SelectionChanged(idx, item) {
      // console.log("dgrThueBao_SelectionChanged", item);
      try {
        this.loading(true)
        console.log('dgrThueBao_SelectionChanged', item, this.lvwThueBao)
        if (idx >= 0 && this.lvwThueBao && this.lvwThueBao.length > 0) {
          this.hdtb_id = item.hdtb_id;
          this.thuebao.thuebao_id = item.thuebao_id
          var ds = this.lvwThueBao.filter((x) => x.hdtb_id * 1 == this.hdtb_id * 1);          
          await this.HienThiTTHopDongTB(ds);
        }
      } catch (ex) {
        this.$toast.error("" + ex);
      } finally {
        this.loading(false);
      }
    },
    async HT_ThietBi() {
      try {
        this.dsTRAGOP_TBI_HDTB = [];
        var kt = true; //Kiểm tra xem biến
        this.thietbi.tien_tbi = 0;
        this.thietbi.vat_tbi = 0;
        var soluong = 1;
        this.thietbi.tien_kmtbi = 0;
        this.thietbi.vat_kmtbi = 0;
        this.thietbi.tien_tgtb_tragop = 0;
        this.thietbi.vat_tgtb_tragop = 0;
        this.thietbi.tien_tgtb_tratruoc = 0;
        this.thietbi.vat_tgtb_tratruoc = 0;

        var dTyLe_KMTB = 0;
        var dtien_kmtbi_tmp = 0;
        var dvat_kmtbi_tmp = 0;
        var dtien_tbi_tmp = 0;
        var dvat_tbi_tmp = 0;
        var dtien_tgtb_tratruoc_tmp = 0;
        var dvat_tgtb_tratruoc_tmp = 0;
        var dtien_tgtb_tragop_tmp = 0;
        var dvat_tgtb_tragop_tmp = 0;
        var str = "";
        this.lvMuaThietbi.list = [];
        // console.log("this.dsCTM_TBI", this.dsCTM_TBI);
        for (var i = 0; i < this.dsCTM_TBI.length; i++) {
          var apidata = {};
          await API.post_fn_lay_cho_ht_thietbi(this.axios, {
            ds_para: JSON.stringify({
              LOAITBI_ID: this.dsCTM_TBI[i]["loaitbi_id"],
              DS_CT_KM_ID: "" + this.ds_CTKM(),
            }),
          }).then((response) => {
            if (
              response &&
              response.data &&
              response.data.data &&
              response.data.error_code &&
              response.data.error_code === "BSS-00000000"
            ) {
              apidata = JSON.parse(response.data.data);
              if (apidata["RESULT"]) apidata = apidata["RESULT"];
            }
          });
          if (apidata["LOAI_TBI"]) str = apidata["LOAI_TBI"];
          this.lvMuaThietbi.list.push(str);

          //Hiếu bổ sung thêm tiền mua thiết bị ngày 14/09/2010
          //Hiếu bổ sung code tính tiền mua thiết bị
          kt = false;
          soluong = this.dsCTM_TBI[i]["soluong"];
          if (this.dsCTM_TBI[i]["tien"]) {
            dtien_tbi_tmp = this.dsCTM_TBI[i]["tien"];
            this.thietbi.tien_tbi =
              this.thietbi.tien_tbi + dtien_tbi_tmp * soluong;
          }
          if (this.dsCTM_TBI[i]["vat"]) {
            dvat_tbi_tmp = this.dsCTM_TBI[i]["vat"];
            this.thietbi.vat_tbi =
              this.thietbi.vat_tbi + dvat_tbi_tmp * soluong;
          }
        }

        if (kt) {
          this.thietbi.tien_tbi = 0;
          this.thietbi.vat_tbi = 0;
          this.thietbi.tien_kmtbi = 0;
          this.thietbi.vat_kmtbi = 0;
          this.thietbi.tien_tgtb_tratruoc = 0;
          this.thietbi.vat_tgtb_tratruoc = 0;
          this.thietbi.tien_tgtb_tragop = 0;
          this.thietbi.vat_tgtb_tragop = 0;
        }

        this.thietbi.dtien_kmtb = this.thietbi.tien_kmtbi;
        this.thietbi.dvat_kmtb = this.thietbi.vat_kmtbi;

        this.HienThiTienMuaThietBi();
        //this.HienThiTienKhuyenMai_ThietBi();
        //this.HienThiTienTraTruocThietBi();
        //this.HienThiTienTraGopThietBi();
      } catch (ex) {
        this.$toast.warning("" + ex);
        throw ex;
      }
    },
    async HienThiTienMuaThietBi() {
      //thietbi.dtientbi+thietbi.dvattbi
      this.thietbi.dtientbi = this.thietbi.tien_tbi;
      this.thietbi.dvattbi = this.thietbi.vat_tbi;
    },
    // async HienThiTTMoRong(hdtb_id, dichvuvt_id) {
    //   var ds = [];
    //   var dt = [];
    //   switch (dichvuvt_id) {
    //     case DichVuVienThong.CO_DINH:
    //       if (this.ma_tb) this.thuebao.codinh.somay = this.ma_tb;
    //       await API.post_fn_hienthi_tt_morong(this.axios, {
    //         ds_para: JSON.stringify({
    //           HDTB_ID: hdtb_id,
    //           DICHVUVT_ID: dichvuvt_id,
    //           LOAITB_ID: 0,
    //         }),
    //       }).then((response) => {
    //         if (
    //           response &&
    //           response.data &&
    //           response.data.data &&
    //           response.data.error_code &&
    //           response.data.error_code === "BSS-00000000"
    //         ) {
    //           var apidata = JSON.parse(response.data.data);
    //           if (apidata["RESULT"]) apidata = apidata["RESULT"];
    //           if (apidata["DS_HDTB"]) ds = apidata["DS_HDTB"];
    //           if (apidata["DS_TIEN_CAMKET"]) dt = apidata["DS_TIEN_CAMKET"];
    //         }
    //       });
    //       ds = this.LowerCasePropertyList(ds);
    //       dt = this.LowerCasePropertyList(dt);
    //       break;
    //   }
    //   if (ds.length > 0) {
    //     this.ma_tb_ao = "";
    //     this.thuebao.cvlct = false;
    //     this.thuebao.congvan = "";
    //     this.thuebao.NgayCV = moment(new Date()).format("DD/MM/YYYY");
    //     this.thuebao.crm = "";
    //     this.thuebao.erp = "";
    //     this.thuebao.vtn = "";
    //     if (ds[0]["ma_vnpt"]) this.thuebaocd.ma_vnpt = ds["ma_vnpt"];
    //     else this.thuebaocd.ma_vnpt = -1;
    //
    //     switch (dichvuvt_id) {
    //       case DichVuVienThong.CO_DINH:
    //         this.chkVSVX = ds[0]["mc_tbcd_id"] == "1";
    //         this.chkMienCuocTB = ds[0]["miencuoc_tb"] == "1";
    //         this.thuebaocd.madoicap = ds[0]["madoicap"];
    //         this.thuebaocd.matn = ds[0]["matb_tn"];
    //         if (ds[0]["sotruot"]) {
    //           this.thuebaocd.kieu_thueso = THUESO.TRUOTSO;
    //           this.thuebaocd.sothang_thueso = ds[0]["tg_thueso"];
    //           this.thuebaocd.noidung_thueso = ds[0]["sotruot"];
    //         }
    //         if (ds[0]["noidung_tb"] != "") {
    //           this.thuebaocd.kieu_thueso = THUESO.THONGBAO;
    //           this.thuebaocd.sothang_thueso = ds[0]["tg_thueso"];
    //           this.thuebaocd.noidung_thueso = ds[0]["noidung_tb"];
    //         }
    //         if (ds[0]["sotruot"] == "" && ds[0]["noidung_tb"] == "") {
    //           this.thuebaocd.kieu_thueso = 0;
    //           this.thuebaocd.sothang_thueso = 0;
    //           this.thuebaocd.noidung_thueso = "";
    //         }
    //
    //         if (ds[0]["doitac_id"]) {
    //           this.listbox.doitac.ischeckedcd = true;
    //           this.listbox.doitac.valuecd = ds[0]["doitac_id"];
    //           this.thuebaocd.cuocdoitac = ds[0]["cuoc_doitac"];
    //         } else {
    //           this.listbox.doitac.ischeckedcd = false;
    //           this.thuebaocd.cuocdoitac = "0";
    //         }
    //
    //         if (ds[0]["congnghe_id"])
    //           this.listbox.congnghe.value = ds[0]["congnghe_id"];
    //         else this.listbox.congnghe.value = 0;
    //
    //         if (ds[0]["trangbi_id"])
    //           this.listbox.trangbi.valuecd = ds[0]["trangbi_id"];
    //         if (ds[0]["chuquan_id"])
    //           this.listbox.chu_quan.valuecd = ds[0]["chuquan_id"];
    //         if (ds[0]["chuquanld_id"])
    //           this.listbox.chu_quan.valueldcd = ds[0]["chuquanld_id"];
    //
    //         this.thuebao.codinh.dgvTienCK.list = dt;
    //         break;
    //     }
    //   }
    // },
    async LayTienTheoKhoanMuc() {
      if (!this.listbox.loaihinh_tb_moi.value) return;
      if (!this.listbox.kieu_ld.value) return;

      //UR 2.1.4.068
      var pdata = {
        apld_id: this.diachiLD.AP_ID,
        dichvuvt_id: this.listbox.dichvu_vt_moi.value,
        doituong_id: this.listbox.doituong.value,
        kh_cd: 0,
        khuld_id: this.diachiLD.KHU_ID,
        kieuld_id: this.listbox.kieu_ld.value,
        loaihd_id: LoaiHopDong.CHUYENDOI_LH,
        loaitb_id: this.listbox.loaihinh_tb_moi.value,
        ltb_adsl_cu_id: 0,
        ltb_adsl_id: 0,
        ltb_adsl_moi_id: 0,
        muccuoc_id: 0,
        ngay_yc: this.NgayYeuCau,
        phanvung_id: this.$root.token.getDonViID(),
        phold_id: this.diachiLD.PHO_ID,
        phuongld_id: this.diachiLD.PHUONG_ID,
        tocdo_cu_id: 0,
        tocdo_id: 0,
        tocdo_moi_id: 0,
        trangbi_id: this.listbox.trangbi.valuecd, //BUG can xem lai cho nay khi khong dung cho co dinh
      };
      if (this.state.ds_lay_tien_khoanmuc_ld && this.state.ds_lay_tien_khoanmuc_ld.length > 0) {
        this.HienThiTienLapDat(this.state.ds_lay_tien_khoanmuc_ld);
        return;
      } else {
        //post_lay_tien_khoanmuc_ld_v2
        await API.lay_tien_khoanmuc_ld(this.axios, pdata).then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            var ds = response.data.data;
            if (ds.length > 0) {
              this.state.ds_lay_tien_khoanmuc_ld = ds;
              this.HienThiTienLapDat(ds);
              //await this.LayTongTien_HDTB(); không cần gọi hàm này vì đã tính toán bên html
            } else {
              this.dtienld = 0;
              this.dtiencd = 0;
            }
          }
        });
      }
    },
    async HienThiTienLapDat(ds) {
      var kt_cd = true;
      var kt_lm = true;
      if (ds && ds.length > 0)
        for (var item of ds) {
          item.tien = item.tien * 1;
          item.vat = item.vat * 1;
          item.tyle_vat = item.tyle_vat * 1;
        }

      if (ds[0]["tien"] != -1) {
        for (var i = 0; i < ds.length; i++) {
          if (item["khoanmuctt_id"] == KHOANMUC_TT.KMTT_TDLOAIHINHTB) {
            this.dtienld = item["tien"] + item["vat"];
          }
        }
      } else {
        this.dtienld = 0;
        this.dvatld = 0;
        this.dtiencd = 0;
        this.dvatcd = 0;
        this.vtyle_vat_id_dnhm = 1;
        this.vtyle_vat_dnhm = 10;
        this.vtyle_vat_id_cd = 1;
        this.vtyle_vat_cd = 10;
      }
    },

    async LayTongTien_HDTB() {
      try {
        this.dtienld = 0;
        this.dvatld = 0;
        this.dichvu.dtien_dv = 0;
        this.dichvu.dvat_dv = 0;
        this.thietbi.dtientbi = 0;
        this.thietbi.dvattbi = 0;

        if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0)
          for (var item of this.ds_tien_khoanmuc) {
            item.tien = item.tien * 1;
            item.vat = item.vat * 1;
            item.tyle_vat = item.tyle_vat * 1;
          }
        for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] * 1 ==
            KHOANMUC_TT.KMTT_TDLOAIHINHTB * 1
          ) {
            this.dvatld = this.ds_tien_khoanmuc[i]["vat"];
            this.dtienld = this.ds_tien_khoanmuc[i]["tien"] + this.dvatld;
          }

          if (
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] * 1 ==
            KHOANMUC_TT.KMTT_DICHVU * 1
          ) {
            this.dichvu.dtien_dv = this.ds_tien_khoanmuc[i]["tien"];
            this.dichvu.dvat_dv = this.ds_tien_khoanmuc[i]["vat"];
          }

          if (
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] * 1 ==
            KHOANMUC_TT.KMTT_DICHVU * 1
          ) {
            this.thietbi.dtientbi = this.ds_tien_khoanmuc[i]["tien"];
            this.thietbi.dvattbi = this.ds_tien_khoanmuc[i]["vat"];
          }
        }
      } catch (ex) {
        this.$toast.error("" + ex);
        throw ex;
      }
    },
    HienThiDanhSacHDTB(ds) {
      var tongtienhd = 0;
      var tongtientb = 0;
      this.lvwThueBao = [];
      console.log('HienThiDanhSacHDTB', ds)
      var i = 1;
      for (var myRow of ds) {
        tongtientb = myRow["tongtien"];
        myRow.stt = i++
        myRow.thuebao_id = myRow["thuebao_id"] ? Number(myRow["thuebao_id"]) : 0
        myRow.ten_tb = this.ChuanHoaTen(myRow["ten_tb"]);
        myRow.diachi_ld = this.ChuanHoaTen(myRow["diachi_tb"]);
        myRow.kieuld_id = Number(myRow["kieuld_id"]);
        myRow.doituong_id = Number(myRow["doituong_id"]);

        tongtienhd = tongtienhd + tongtientb;
        this.lvwThueBao.push(myRow);
      }
      this.tongtienhd = tongtienhd;
      if (i >= 2) {
        this.buttons.tsbtnThemTT = true;
        this.buttons.tsbtnXoaTB = i != 2;
      } else {
        this.buttons.tsbtnXoaTB = false;
        this.buttons.tsbtnThemTB = false;
      }
      // console.log("HienThiDanhSacHDTB lvwThueBao");
      // console.log(ds, this.lvwThueBao);
    },
    async HienThiTTHopDongKH(ds) {
      // console.log("HienThiTTHopDongKH", ds);
      try {
        if (ds && ds.length > 0) {
          this.buttonSet = 3;
          this.selectedRow = ds[0];
          this.thuebao.thuebao_id = ds[0]["thuebao_id"];
          this.khachhang.hdkh_id = ds[0]["hdkh_id"];
          this.hdtb_id = ds[0]["hdtb_id"];
          this.NgayYeuCau = moment(ds[0]["ngay_yc"]).format("DD/MM/YYYY");
          this.NgayLapHD = moment(ds[0]["ngaylap_hd"]).format("DD/MM/YYYY");
          this.khachhang.magd = ds[0]["ma_gd"];
          this.khachhang.mahd = ds[0]["ma_hd"];
          this.khachhang.makh = ds[0]["ma_kh"];
          this.hdtt_id = ds[0]["hdtt_id"];

          if (ds[0]["khachhang_id"])
            this.khachhang.khachhang_id = ds[0]["khachhang_id"];
          else this.khachhang.khachhang_id = 0;
          if (ds[0]["loaikh_id"] && ds[0]["loaikh_id"])
            this.listbox.loai_kh.value = ds[0]["loaikh_id"];

          if (ds[0]["kieuhd_id"])
            this.listbox.kieu_hd.value = ds[0]["kieuhd_id"];
          else this.listbox.kieu_hd.value = KIEU_HD.TAI_GD;
          this.vkieuhd_id = this.listbox.kieu_hd.value;

          this.khachhang.tenkh = this.ChuanHoaTen(ds[0]["ten_kh"]);

          this.diachiKH.DIACHI = ds[0]["diachi_kh"];

          this.khachhang.dienthoai = ds[0]["so_dt"];
          this.maduan = ds[0]["ma_duan"];

          this.khachhang.hdkh_id = ds[0]["hdkh_id"];

          await this.HienThiTTDiaChi(
            await this.LAY_DS_DIACHI_THEO_HDKH_ID(this.khachhang.hdkh_id),
            1
          );
          await this.HienThiTTHopDongTT(
            await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.khachhang.hdkh_id)
          );
          await this.HienThiTTHopDongTB(
            await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.khachhang.hdkh_id)
          );

          await this.HT_TT();

          await this.LoadMucCuocTB();

          this.tongtienhd = ds[0]["tongtien"] * 1;

          //thông tin khu vực
          await this.HienThiThongTinKhuVuc()

          this.LayTienTheoKhoanMuc();

          await this.HienThiTTHopDongTB(ds);

          if (this.lvwThueBao.length > 1) this.buttons.tsbtnXoaTB = true;
          else this.buttons.tsbtnXoaTB = false;
        } else {
          this.buttonSet = 1;
        }
      } catch (exp) {
        this.$toast.error("" + exp);
        throw exp;
      } finally {
      }
    },
    async HienThiThongTinKhuVuc() {
      // console.log("HienThiThongTinKhuVuc");
      var pdata = "";
      var apidata = {};
      this.thuebao.khuvuc = "";
      await API.post_sp_chuyendoicdims_layds_kv_hdtb_id(this.axios, {
        hdtb_id: this.hdtb_id,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          apidata = response.data.data;
          this.thuebao.khuvuc = apidata[0]["ten_kv"];
          this.thuebao.khuvuc_id = apidata[0]["khuvuc_id"];
        }
      });
    },
    async HT_DichVuGT() {
      this.lvDvgt.list = [];
      if (!this.m_dsThueBao_DichVu) return;
      this.m_dsThueBao_DichVu = this.LowerCasePropertyList(
        this.m_dsThueBao_DichVu
      );
      var kt = true;
      this.dvgt.tien_dv = 0;
      this.dvgt.vat_dv = 0;

      var str = "";
      for (var i = 0; i < this.m_dsThueBao_DichVu.length; i++) {
        if (
          // this.m_dsThueBao_DichVu[i]["huy"] == "1" ||
          this.m_dsThueBao_DichVu[i]["dangky"] == "1"
        ) {
          str = this.m_dsThueBao_DichVu[i]["ten_dvgt"];
          if (
            this.m_dsThueBao_DichVu[i]["dangky"] == "1"
            // this.m_dsThueBao_DichVu[i]["huy"] == "0"
          ) {
            kt = false;
            if (this.m_dsThueBao_DichVu[i]["cuoc_ld"]) {
              this.dvgt.tien_dv =
                this.dvgt.tien_dv + this.m_dsThueBao_DichVu[i]["cuoc_ld"] * 1;
            }
            if (this.m_dsThueBao_DichVu[i]["vat_ld"]) {
              this.dvgt.vat_dv =
                this.dvgt.vat_dv + this.m_dsThueBao_DichVu[i]["vat_ld"] * 1;
            }

            str = str + " (ĐK)";
          }
          // if (this.m_dsThueBao_DichVu[i]["huy"] == "1") str = str + " (Hủy)";
          this.lvDvgt.list.push(str);
        }
      }
      if (kt == true) {
        this.dvgt.tien_dv = 0;
        this.dvgt.vat_dv = 0;
      }
      // console.log("this.m_dsThueBao_DichVu", this.m_dsThueBao_DichVu);
      // console.log("HT_DichVuGT", this.lvDvgt.list);
      // console.log("this.dvgt.tien_dv", this.dvgt.tien_dv);
      this.HienThiTienDichVu(this.dvgt.tien_dv, this.dvgt.vat_dv);
    },
    HienThiTienDichVu(tien_dv, vat_dv) {
      this.dichvu.dtien_dv = tien_dv;
      this.dichvu.dvat_dv = vat_dv;
      // for (var j = 0; j < this.ds_tien_khoanmuc.length; j++)
      // {
      //     if (this.ds_tien_khoanmuc[j]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DICHVU)
      //     {
      //         this.ds_tien_khoanmuc[j]["tien"] = tien_dv;
      //         this.ds_tien_khoanmuc[j]["vat"] = vat_dv;
      //     }
      // }
      this.LayTongTien_HDTB();
    },
    async SaveClicked() {
      //test
      this.loading(true);
      try {
        var isvalid = true;
        document.getElementsByTagName("input").forEach((item) => {
          if (isvalid && !item.validity.valid) {
            isvalid = false;
            this.$toast.error(item.getAttribute("title"));
            item.focus();
          }
        });
        if (!isvalid) return;

        if (this.listbox.dichvu_vt.value == DichVuVienThong.CO_DINH) {
          //TODO line 17410
          //17406 chọn vị trí trên bản đồ???
        }

        //validate so CMT:
        // if(!this.khachhang.sogt.match(/^[a-zA-Z0-9\-\_\s]+$/g)) {
        //   this.$toast.error("Số giấy tờ không hợp lệ.Số giấy tờ gồm các ký tự từ 0->9a->z. Hãy kiểm tra lại");
        //   return;
        // }
        //TODO validate khac line 17432 - 17499

        await this.CapNhat();
      } catch (ex) {
        console.error(ex)
        commonFn.showException(this, ex);
      } finally {
        this.loading(false);
      }
    },

    async CapNhat() {
      var themmoi = this.buttonSet == 1;

      if ((await this.KiemTraDL_KhachHang()) == false) return;

      if ((await this.KIEMTRA_CDLH()) == false)
        // Dũng PA thêm
        return;

      this.ma_tb = this.thuebao.codinh.somay;
      let reqHuongGiao = {
        loaiHdId: LoaiHopDong.CHUYENDOI_LH,
        loaiTbId: this.listbox.loaihinh_tb_moi.value * 1,
        tocDoId: -1,
        mucCuocId: -1,
        kieuLdId: this.listbox.kieu_ld.value * 1,
      };
      let ds = commonFn.GetData(
        await API.post_lay_huonggiao_tiepnhan(this.axios, reqHuongGiao)
      );
      console.log("dshuonggiao_tiepnhan", ds);
      this.huonggiao_tn = 0;
      this.luong_id = 0;
      if (!ds || !ds.quytrinh_id) {
        throw "Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!";
      }

      this.huonggiao_tn = ds["huonggiao_id"];
      this.quytrinh_id = ds["quytrinh_id"];
      this.luong_id = ds["luong_id"];
      if ((await this.TaoDuLieu(themmoi, false)) == false) return;
      console.log("TaoDuLieu done");
      try{
        if(this.dsHDTB_IMS.length > 0){
          if(this.dsHDTB_IMS.length > 0 && (this.dsHDTB_IMS[0].CHUQUAN_ID == null || this.dsHDTB_IMS[0].CHUQUAN_ID == undefined)){
            this.$toast.error("Kiểm tra lại dữ liệu chuquan_id");
            return;
          }
        }
      }
      catch(e){}
      if ((await this.KiemTraDL_ThueBao()) == false) return;
      await API.post_lay_danhba_theo_matb(this.axios, {
        in_ma_tb: this.ma_tb,
        in_dichvuvt_id: this.listbox.dichvu_vt.value,
        in_donvi_dl_id: 0,
      }).then((response) => {
        if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000" &&
          response.data.data && response.data.data.length > 0)
        {
          if (Number(this.thuebao.thuebao_id) != Number(response.data.data[0]["thuebao_id"])) {
            this.$toast.error("Kiểm tra lại dữ liệu ma_tb và thuebao_id");
            return;
          }
        }
        else{
          this.$toast.error("Kiểm tra lại dữ liệu ma_tb và thuebao_id");
          return;
        }
      });
      if(this.listbox.dichvu_vt_moi.value != DichVuVienThong.DICHVU_CNTT){
        var pdata = {
          themmoi: themmoi == true ? 1 : 0,
          quytrinh_id: this.quytrinh_id,
          luong_id: this.luong_id,
          huonggiao_tn: this.huonggiao_tn,
          dichvu_id: this.listbox.dichvu_vt.value,
          dichvu_id_moi: this.listbox.dichvu_vt_moi.value,
          loaitb_id:
            themmoi == true
              ? this.listbox.loaihinh_tb_moi.value * 1
              : this.listbox.loaihinh_tb.value * 1,
          ma_tb: this.ma_tb,
          thuebao_id: this.thuebao.thuebao_id,
          matn_adsl: this.thuebaocd.matn,
          kieuld_id: this.listbox.kieu_ld.value,
          khachhang_id: this.khachhang.khachhang_id,
          //  ma_gd: this.khachhang.magd, // cũ
          ma_gd: themmoi == true ? 0 : this.khachhang.magd, //  17/07/2024 update lại theo đề xuất của a Lam
          loaihd_id: LoaiHopDong.CHUYENDOI_LH,
          hdtb_id: this.hdtb_id,
          hdkh_id: this.khachhang.hdkh_id,
          ma_nd: this.$root.token.getUserName(),
          may_cn: this.may_cn,
          ip_cn: this.ip_cn,
          nguoi_cn: this.nguoi_cn,
          js_hd_thuebao: this.UpperCasePropertyList(this.dsHDTB),
          js_hdtb_ims: this.UpperCasePropertyList(this.dsHDTB_IMS),
          js_thongtin_tt: this.UpperCasePropertyList(this.dsTTTT),
          js_ct_tienhd: this.UpperCasePropertyList(this.dsCTTHD),
          js_ct_mua_tbi: this.UpperCasePropertyList(this.dsCTM_TBI),
          js_diachi: this.UpperCasePropertyList(this.dsDiaChi),
          js_diachi_hdtb: this.UpperCasePropertyList(this.dsDiaChiHDTB),
          js_hdtb_dv: this.UpperCasePropertyList(this.dsHDTBDV),
          js_dangky_dvgt: this.UpperCasePropertyList(this.dsDK_DVGT),
          js_hdtb_kv: this.UpperCasePropertyList(this.dsHDTB_KV),
          js_hdtb_cd: this.UpperCasePropertyList(this.dsHDTB_CD),
          js_hd_khachhang: this.UpperCasePropertyList(this.dsHDKH),
          js_hd_thanhtoan: this.UpperCasePropertyList(this.dsHDTT),
          js_diachi_hdkh: this.UpperCasePropertyList(this.dsDiaChiHDKH),
          js_diachi_hdtt: this.UpperCasePropertyList(this.dsDiaChiHDTT),
        };

        console.log("pdata", pdata);

        var themthanhcong = false;

        await API.post_fn_chuyendoicdims_capnhat(this.axios, {
          p_ds_para: JSON.stringify(pdata),
        }).then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            var apidata = JSON.parse(response.data.data.p_js_returnds);
            console.log("API.post_fn_chuyendoicdims_capnhat", apidata);

            if (apidata["ERROR_CODE"] != 1) {
              this.$toast.error(apidata["MESSAGE"]);
              return;
            }
            this.$toast.success("Cập nhật thành công!");
            themthanhcong = true;
          } else {
            commonFn.showException(this, response.data);
            return;
          }
        });
        if (themthanhcong == true)
          await this.HienThiTTHopDongKH(await this.LAY_DS_HOPDONG_THEO_MA_GD());
      }
      else{
        console.log(this.dsHDTB_CNTT);
        var pdata = {
          // themmoi: themmoi == true ? 1 : 0,
          // quytrinh_id: this.quytrinh_id,
          // luong_id: this.luong_id,
          p_huonggiao_id : this.huonggiao_tn,
          // dichvu_id: this.listbox.dichvu_vt.value,
          // dichvu_id_moi: this.listbox.dichvu_vt_moi.value,
          // loaitb_id:
          //   themmoi == true
          //     ? this.listbox.loaihinh_tb_moi.value * 1
          //     : this.listbox.loaihinh_tb.value * 1,
          // ma_tb: this.ma_tb,
          // thuebao_id: this.thuebao.thuebao_id,
          // matn_adsl: this.thuebaocd.matn,
          // kieuld_id: this.listbox.kieu_ld.value,
          // khachhang_id: this.khachhang.khachhang_id,
          // ma_gd: this.khachhang.magd,
          // loaihd_id: LoaiHopDong.CHUYENDOI_LH,
          // hdtb_id: this.hdtb_id,
          // hdkh_id: this.khachhang.hdkh_id,
          // ma_nd: this.$root.token.getUserName(),
          // may_cn: this.may_cn,
          // ip_cn: this.ip_cn,
          // nguoi_cn: this.nguoi_cn,
          p_js_hd_thuebao: JSON.stringify(this.UpperCasePropertyList(this.dsHDTB)),
          p_js_hdtb_cntt: JSON.stringify(this.dsHDTB_CNTT),
          p_js_thongtin_tt: JSON.stringify(this.UpperCasePropertyList(this.dsTTTT)),
          p_js_ct_tienhd: JSON.stringify(this.UpperCasePropertyList(this.dsCTTHD)),
          p_js_ct_mua_tbi: JSON.stringify(this.UpperCasePropertyList(this.dsCTM_TBI)),
          p_js_diachi: JSON.stringify(this.UpperCasePropertyList(this.dsDiaChi)),
          p_js_diachi_hdtb: JSON.stringify(this.UpperCasePropertyList(this.dsDiaChiHDTB)),
          p_js_hdtb_dv: JSON.stringify(this.UpperCasePropertyList(this.dsHDTBDV)),
          p_js_dangky_dvgt: JSON.stringify(this.UpperCasePropertyList(this.dsDK_DVGT)),
          p_js_hdtb_kv: JSON.stringify(this.UpperCasePropertyList(this.dsHDTB_KV)),
          // js_hdtb_cd: this.UpperCasePropertyList(this.dsHDTB_CD),
          p_js_hd_khachhang: JSON.stringify(this.UpperCasePropertyList(this.dsHDKH)),
          p_js_hd_thanhtoan: JSON.stringify(this.UpperCasePropertyList(this.dsHDTT)),
          p_js_diachi_hdkh: JSON.stringify(this.UpperCasePropertyList(this.dsDiaChiHDKH)),
          p_js_diachi_hdtt: JSON.stringify(this.UpperCasePropertyList(this.dsDiaChiHDTT)),
        };

        console.log("pdata", pdata);

        var themthanhcong = false;

        await API.sp_themmoi_hopdongtbcdvcc(this.axios, pdata).then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {

            //var apidata = JSON.parse(response.data.data.p_js_returnds);
            console.log("API.post_fn_chuyendoicdims_capnhat", response);

            //cuongpq thêm hàm hủy VCC
            if (this.listbox.loaihinh_tb_moi.value == 369) { 
  
              API.ts_huy_dichvu(this.axios, { "so_tb": "84" +this.ma_tb });
            }
            // if (apidata["ERROR_CODE"] != 1) {
            //   this.$toast.error(apidata["MESSAGE"]);
            //   return;
            // }
            this.$toast.success("Cập nhật thành công!");
            themthanhcong = true;
          } else {
            commonFn.showException(this, response.data);
            return;
          }
        });
        if (themthanhcong == true)
          await this.HienThiTTHopDongKH(await this.LAY_DS_HOPDONG_THEO_MA_GD());
      }

    },

    async TaoDuLieu(themmoi, themTB) {
      this.ma_tb = this.thuebao.codinh.somay;
      console.log(themTB);
      if (!themTB) {
        console.log(themTB);
        await this.TaoDuLieu_HDKH(themmoi);
        await this.TaoDuLieu_HDTT(themmoi);
        await this.TaoDuLieu_HDTB(themmoi);
        await this.TaoDuLieu_DiaChi(themmoi, 4);
      } else {
        await this.TaoDuLieu_HDTB(themmoi);
        await this.TaoDuLieu_DiaChi(themmoi, 3);
      }


      await this.LoadDL_KV(this.thuebao.khuvuc_id);
      await this.TaoDuLieu_THONGTIN_TT(themmoi);
      await this.TaoDuLieu_CT_TIENHD(themmoi);

      await this.TaoDuLieu_HDTBDV(themmoi);

      for (var i = 0; i < this.dsCTM_TBI.length; i++) {
        this.dsCTM_TBI[i]["hdtb_id"] = this.hdtb_id;
      }
      if ((await this.TaoDuLieu_HDTB_CON(themmoi)) == false) return false;
      await this.TaoDuLieu_DKDV(); //nhapt
      if(this.listbox.dichvu_vt_moi.value == DichVuVienThong.DICHVU_CNTT){
        for (var item of this.ItemList) {
          let guiItem = this.gridEditors.filter((x) => x.ID == item.sFieldName)
          if (guiItem && guiItem.length > 0) item.sValue = guiItem[0].Value ? guiItem[0].Value + '' : guiItem[0].Value
        }
        this.dsHDTB_CNTT = this.ItemList
      }
      return true;
    },
    async LoadDL_KV(_khuvuc_id) {
      console.log("LoadDL_KV");
      this.dsHDTB_KV = [];
      if(!_khuvuc_id && _khuvuc_id == 0) {
        console.error("LoadDL_KV _khuvuc_id = 0")
        return;
      }

      var dr = {};
      dr.HDTB_ID = this.hdtb_id;
      dr.KHUVUC_ID = _khuvuc_id;
      dr.LOAIKV_ID = LOAI_KV.KHUVUC_DIABAN;
      dr.NGUOI_CN = this.nguoi_cn;
      dr.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      dr.MAY_CN = this.may_cn;
      this.dsHDTB_KV.push(dr);

      console.log("LoadDL_KV DONE", this.dsHDTB_KV);
    },
    async TaoDuLieu_DKDV() {
      this.dsDK_DVGT = [];
      if (!this.m_dsThueBao_DichVu) return;

      console.log("TaoDuLieu_DKDV");
      this.m_dsThueBao_DichVu = this.LowerCasePropertyList(
        this.m_dsThueBao_DichVu
      );
      var kieu_dk_dvgt = 0;
      console.log("this.m_dsThueBao_DichVu", this.m_dsThueBao_DichVu);
      for (var dr of this.m_dsThueBao_DichVu) {
        if (dr["dangky"].toString() == "1")
          kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT;
        else if (dr["huy"].toString() == "1")
          kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT;
        else {
          continue;
        }

        var rowHDTDDV = {};
        rowHDTDDV.HDTB_ID = this.hdtb_id;
        rowHDTDDV.NGAY_YC = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
        rowHDTDDV.DICHVUGT_ID = dr["dichvugt_id"]
          ? dr["dichvugt_id"] * 1
          : dr["dichvugtid"] * 1;
        rowHDTDDV.NOIDUNG = dr["noidung"];
        rowHDTDDV.KIEU_YC = kieu_dk_dvgt;
        rowHDTDDV.TTTH = 1;

        this.dsDK_DVGT.push(Object.assign({}, rowHDTDDV));
      }

      console.log("TaoDuLieu_DKDV DONE", this.dsDK_DVGT);
    },
    //Tao du lieu cho bang HDTB_IMS hoặc HDTB_CD
    async TaoDuLieu_HDTB_CON(themmoi) {
      console.log("TaoDuLieu_HDTB_CON");
      this.dsHDTB_IMS = [];
      this.dsHDTB_CD = [];
      var rowHDTB_IMS = {
        // "phanvung_id": 9,
        // "hdtb_id": 1680730,
        // "madoicap": 368173,
        // "trangbi_id": 4,
        // "username": "843660018",
        // "password": "dongxoai123",
        // "matb_tn": "3540955",
        // "chuquan_id": 126,
        // "ketcuoi_id": 96966,
        // "thoihan_id": 2,
        // "tinhkhac": 0
      };
      if (this.listbox.dichvu_vt_moi.value * 1 == DichVuVienThong.IMS) {
        rowHDTB_IMS.HDTB_ID = this.hdtb_id;
        rowHDTB_IMS.THIETBI_ID = this.listbox.thietbi_ims.value;

        if (this.vchuquan_id) rowHDTB_IMS.CHUQUAN_ID = this.vchuquan_id;
        else rowHDTB_IMS.CHUQUAN_ID = null;

        if (this.vchuquanld_id != 0)
          rowHDTB_IMS.CHUQUANLD_ID = this.vchuquanld_id;
        else rowHDTB_IMS.vchuquanld_id = null;

        // hoangpkn : 26/08/2016
        var kieu = this.kieu_ld_kieu;
        if ([1, 2, 3, 4].includes(kieu * 1)) {
          if (!this.txtMaDoiCap_ADSL) {
            this.$toast.warning(
              "Đối với kiểu yêu cầu là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống. \n Hãy chọn lại mã truy nhập"
            );
            return false;
          }
          this.thuebaocd.madoicap = this.txtMaDoiCap_ADSL * 1;
          rowHDTB_IMS.MADOICAP = this.thuebaocd.madoicap * 1;
        } //TH còn lại sinh mã đôi cáp
        else {
          if (themmoi == true) {
            rowHDTB_IMS.MADOICAP = await this.GetKey("MADOICAP");
          } else rowHDTB_IMS.MADOICAP = this.thuebaocd.madoicap * 1;
        }
        // end hoang

        if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.IMS_SIPTRUNKING)
          rowHDTB_IMS.SL_CUOCGOI = this.txtSoLuong * 1;
        else rowHDTB_IMS.SL_CUOCGOI = null;

        rowHDTB_IMS.MATB_TN = this.thuebaocd.matn;
        rowHDTB_IMS.TRANGBI_ID = this.listbox.trangbi.valueims;
        var dauso_ims = "";
        //TODO
        //BangThamSo bts = new BangThamSo();
        //DataTable _dt = bts.getDataFromSQL("select dauso_ims from " + DatabaseConstants.DB2 + ".tinh where tentat = '" + DatabaseConstants.DB2.Substring(4) + "'", "tinh").Tables[0];
        var data = await API.sp_tt_tinh(this.axios, {p_type: 4})
        if(data.data.data.length > 0) {
          dauso_ims = data.data.data.find(e => e.phanvung_id == this.$root.token.getPhanVungID()).dauso_ims
        }
        rowHDTB_IMS.USERNAME = "84" + dauso_ims + this.ma_tb;
        if (themmoi == true || !this.pass_ims || this.pass_ims == "") {
          this.pass_ims = this.GetRandomText(6);
        }
        if (
          this.listbox.loaihinh_tb_moi.value * 1 ==
          LoaiHinhTB.IMS_SIPTRUNKING
        ) {
          rowHDTB_IMS.SL_CUOCGOI = this.txtSoLuong * 1;
          rowHDTB_IMS.CTDV_ID = this.listbox.ht_chitietdv_ims.value * 1;
          if (rowHDTB_IMS.CTDV_ID == this.ChiTietDV_IMS.internet)
            rowHDTB_IMS.IP = this.ip_ims;

          if (this.listbox.trunk.checkbox_value == true)
            rowHDTB_IMS.TRUNKID = this.listbox.trunk.value;
          rowHDTB_IMS.HTTC_ID = this.listbox.hinhthuc_tc.value * 1;
        }
        rowHDTB_IMS.PASSWORD = this.pass_ims;
        this.dsHDTB_IMS.push(Object.assign({}, rowHDTB_IMS));
        if (Number(this.listbox.dichvu_vt_moi.value) == DichVuVienThong.IMS) {
          if (!this.dsHDTB_IMS || this.dsHDTB_IMS.length == 0) {
            this.$toast.error('Lỗi tạo dữ liệu HDTB_IMS!')
            return false
          }
        }
        //TODO: UPDATE
        // if (themmoi == false)
        // {
        //     dsHDTB_IMS.AcceptChanges();
        //     dsHDTB_IMS.HDTB_IMS.Rows[0].SetModified();
        // }
      } else {
        let rowHDTB_CD = {};
        rowHDTB_CD.HDTB_ID = this.hdtb_id;
        if (themmoi == true)
          rowHDTB_CD.MADOICAP = await this.GetKey("MADOICAP");
        else rowHDTB_CD.MADOICAP = this.thuebaocd.madoicap * 1;

        rowHDTB_CD.TRANGBI_ID = this.listbox.trangbi.valueims * 1;
        if (this.vchuquan_id) rowHDTB_CD.CHUQUAN_ID = this.vchuquan_id;
        else rowHDTB_CD.CHUQUAN_ID = null;

        if (this.vchuquanld_id != 0)
          rowHDTB_CD.CHUQUANLD_ID = this.vchuquanld_id;
        else rowHDTB_CD.CHUQUANLD_ID = null;

        this.dsHDTB_CD.push(Object.assign({}, rowHDTB_CD));
        if (Number(this.listbox.dichvu_vt_moi.value) == DichVuVienThong.CO_DINH) {
          if (!this.dsHDTB_CD || this.dsHDTB_CD.length == 0) {
            this.$toast.error('Lỗi tạo dữ liệu HDTB_CD!')
            return false
          }
        }
        //TODO: ....
        // if (themmoi == false)
        // {
        //     dsHDTB_CD.AcceptChanges();
        //     dsHDTB_CD.HDTB_CD.Rows[0].SetModified();
        // }
      }

      console.log("TaoDuLieu_HDTB_CON DONE", this.dsHDTB_CD);
    },
    async TaoDuLieu_HDTBDV(themmoi) {
      console.log("TaoDuLieu_HDTBDV");
      this.dsHDTBDV = [];
      var row = {};
      var ds_hdtbdv = await this.layds_hdtbdv(themmoi == true ? 1 : 0);
      console.log("ds_hdtbdv", ds_hdtbdv);

      const index=ds_hdtbdv.findIndex(x=>x.donvi_id==0)
      if(index>-1){
        //this.$toast.error('Thông tin đơn vị không hợp lệ (donvi_id=0)')
        throw 'Thông tin đơn vị không hợp lệ(donvi_id=0)'
      }
      ds_hdtbdv.forEach((item) => {
        if (item.loaidv_id == LOAI_DV.TRAM_VT) {
          row.HDTB_ID = this.hdtb_id;
          row.DONVI_ID = this.listbox.tram_tc.value;
          row.LOAIDV_ID = item.loaidv_id;
          row.KIEUDV_ID = 2;
        }
          // Chuyển từ Cố định -> IMS : phải thêm 1 row băng rộng để giao được phiếu
          //Lamnt: dành cho siptrunk (trong TH dbtb_dv không có trạm cấp port)
          // else if(item.loaidv_id == LOAI_DV.DAI_BR || item.loaidv_id == LOAI_DV.TRAM_CAP_PORT){
          //   row.HDTB_ID = this.hdtb_id;
          //   row.DONVI_ID = item.donvi_id,
          //   row.LOAIDV_ID = item.loaidv_id,
          //   row.KIEUDV_ID = 2;
        // }
        else {
          row.HDTB_ID = this.hdtb_id;
          row.DONVI_ID = item.donvi_id;
          row.LOAIDV_ID = item.loaidv_id;
          row.KIEUDV_ID = 2;
        }
        this.dsHDTBDV.push(Object.assign({}, row));
      });

      //remove duplicate data
      this.dsHDTBDV = [...new Set(this.dsHDTBDV)];

      console.log("TaoDuLieu_HDTBDV DONE", this.dsHDTBDV);
    },
    async TaoDuLieu_DiaChi(themmoi, kieu) {
      console.log("TaoDuLieu_DiaChi");
      this.dsDiaChi = [];
      this.dsDiaChiHDKH = [];
      this.dsDiaChiHDTT = [];
      this.dsDiaChiHDTB = [];

      if (kieu == 1 || kieu == 4) {
        var rowDiaChiKH = {};
        if (themmoi) {
          this.diachikh_id = (await this.GetKey("DIACHI")) * 1;
        }

        rowDiaChiKH.DIACHI_ID = this.diachikh_id;
        if (this.diachiKH.TINH_ID && this.diachiKH.TINH_ID != 0)
          rowDiaChiKH.TINH_ID = this.diachiKH.TINH_ID;
        if (this.diachiKH.QUAN_ID && this.diachiKH.QUAN_ID != 0)
          rowDiaChiKH.QUAN_ID = this.diachiKH.QUAN_ID;
        if (this.diachiKH.PHUONG_ID && this.diachiKH.PHUONG_ID != 0)
          rowDiaChiKH.PHUONG_ID = this.diachiKH.PHUONG_ID;
        if (this.diachiKH.PHO_ID && this.diachiKH.PHO_ID != 0)
          rowDiaChiKH.PHO_ID = this.diachiKH.PHO_ID;

        rowDiaChiKH.SONHA = this.diachiKH.SONHA
          ? this.diachiKH.SONHA.trim()
          : "";
        rowDiaChiKH.DIACHI = this.diachiKH.DIACHI
          ? this.diachiKH.DIACHI.trim()
          : "";
        console.log("rowDiaChiKH", rowDiaChiKH);
        this.dsDiaChi.push(rowDiaChiKH);

        if (themmoi == true) {
          var rowDiaChiHDKH = {};
          rowDiaChiHDKH.DIACHI_ID = this.diachikh_id;
          rowDiaChiHDKH.HDKH_ID = this.khachhang.hdkh_id;
          this.dsDiaChiHDKH.push(rowDiaChiHDKH);
        }
      }

      if (kieu == 2 || kieu == 4) {
        var rowDiaChiTT = {};
        if (themmoi) {
          this.diachitt_id = (await this.GetKey("DIACHI")) * 1;
        }

        //DiachiTT
        rowDiaChiTT.DIACHI_ID = this.diachitt_id;
        if (!this.diachiTT || !this.diachiTT.TINH_ID)
          this.diachiTT = this.diachiKH;

        rowDiaChiTT.TINH_ID = this.diachiTT.TINH_ID;
        rowDiaChiTT.QUAN_ID = this.diachiTT.QUAN_ID;
        rowDiaChiTT.PHUONG_ID = this.diachiTT.PHUONG_ID;
        if (this.diachiTT.PHO_ID && this.diachiTT.PHO_ID != 0)
          rowDiaChiTT.PHO_ID = this.diachiTT.PHO_ID;

        rowDiaChiTT.SONHA = this.diachiTT.SONHA
          ? this.diachiTT.SONHA.trim()
          : "";
        rowDiaChiTT.DIACHI = " ";
        console.log("rowDiaChiTT", this.diachiTT);
        this.dsDiaChi.push(rowDiaChiTT);

        //if(!this.diachiBC) this.diachiBC = this.diachiKH;
        if (themmoi) {
          var rowDiaChiHDTT = {};
          rowDiaChiHDTT.DIACHI_ID = this.diachitt_id;
          rowDiaChiHDTT.HDTT_ID = this.hdtt_id;
          this.dsDiaChiHDTT.push(rowDiaChiHDTT);
        }
      }

      if (kieu == 3 || kieu == 4) {
        //Create by Hiếu  - 14/7/2010 - Địa chỉ lắp đặt & địa chỉ thuê bao
        var rowDiaChiTB = {};
        var rowDiachiLD = {};
        if (themmoi) {
          this.diachitb_id = (await this.GetKey("DIACHI")) * 1;
          this.diachild_id = (await this.GetKey("DIACHI")) * 1;
        }
        //Địa chỉ lắp đặt --> dataset dsdiachi
        rowDiachiLD.DIACHI_ID = this.diachild_id;
        rowDiachiLD.TINH_ID = this.diachiLD.TINH_ID;
        rowDiachiLD.QUAN_ID = this.diachiLD.QUAN_ID;
        rowDiachiLD.PHUONG_ID = this.diachiLD.PHUONG_ID;
        if (this.diachiLD.PHO_ID && this.diachiLD.PHO_ID != 0)
          rowDiachiLD.PHO_ID = this.diachiLD.PHO_ID;
        if (this.diachiLD.AP_ID && this.diachiLD.AP_ID != 0)
          rowDiachiLD.AP_ID = this.diachiLD.AP_ID;
        if (this.diachiLD.KHU_ID && this.diachiLD.KHU_ID != 0)
          rowDiachiLD.KHU_ID = this.diachiLD.KHU_ID;
        if (this.diachiLD.DACDIEM_ID && this.diachiLD.DACDIEM_ID != 0)
          rowDiachiLD.DACDIEM_ID = this.diachiLD.DACDIEM_ID;

        rowDiachiLD.SONHA = this.diachiLD.SONHA
          ? this.diachiLD.SONHA.trim()
          : "";
        rowDiachiLD.DIACHI = this.diachiLD.DIACHI
          ? this.diachiLD.DIACHI.trim()
          : "";

        // Kieun v
        // rowDiachiLD.BLOCK = this.diachiLD.BLOCK ? this.diachiLD.BLOCK.trim() : '';
        // rowDiachiLD.TANG = this.diachiLD.TANG ? this.diachiLD.TANG.trim() : '';
        // rowDiachiLD.PHONG = this.diachiLD.PHONG ? this.diachiLD.PHONG.trim() : '';
        // rowDiachiLD.MOTA = this.diachiLD.MOTA ? this.diachiLD.MOTA.trim() : '';
        console.log("rowDiachiLD", rowDiachiLD);
        this.dsDiaChi.push(rowDiachiLD);

        //Địa chỉ thuê bao --> dataset dsdiachi
        rowDiaChiTB.DIACHI_ID = this.diachitb_id;
        rowDiaChiTB.TINH_ID = this.diachiTB.TINH_ID;
        rowDiaChiTB.QUAN_ID = this.diachiTB.QUAN_ID;
        rowDiaChiTB.PHUONG_ID = this.diachiTB.PHUONG_ID;
        if (this.diachiTB.PHO_ID && this.diachiTB.PHO_ID != 0)
          rowDiaChiTB.PHO_ID = this.diachiTB.PHO_ID;
        if (this.diachiTB.AP_ID && this.diachiTB.AP_ID != 0)
          rowDiaChiTB.AP_ID = this.diachiTB.AP_ID;
        if (this.diachiTB.KHU_ID && this.diachiTB.KHU_ID != 0)
          rowDiaChiTB.KHU_ID = this.diachiTB.KHU_ID;
        if (this.diachiTB.DACDIEM_ID && this.diachiTB.DACDIEM_ID != 0)
          rowDiaChiTB.DACDIEM_ID = this.diachiTB.DACDIEM_ID;

        rowDiaChiTB.SONHA = this.diachiTB.SONHA
          ? this.diachiTB.SONHA.trim()
          : "";
        rowDiaChiTB.DIACHI = this.diachiTB.DIACHI
          ? this.diachiTB.DIACHI.trim()
          : "";

        // Kieu nv
        // rowDiaChiTB.BLOCK = this.diachiTB.BLOCK ? this.diachiTB.BLOCK.trim() : '';
        // rowDiaChiTB.TANG = this.diachiTB.TANG ? this.diachiTB.TANG.trim() : '';
        // rowDiaChiTB.PHONG = this.diachiTB.PHONG ? this.diachiTB.PHONG.trim() : '';
        // rowDiaChiTB.MOTA = this.diachiTB.MOTA ? this.diachiTB.MOTA.trim() : '';
        console.log("rowDiaChiTB", rowDiaChiTB);
        this.dsDiaChi.push(rowDiaChiTB);

        if (themmoi) {
          var rowDiaChiHDTB = {};
          rowDiaChiHDTB.DIACHI_ID = this.diachitb_id;
          rowDiaChiHDTB.HDTB_ID = this.hdtb_id;
          rowDiaChiHDTB.DIACHILD_ID = this.diachild_id;
          rowDiaChiHDTB.KINHDO = this.diachiTB.Lng;
          rowDiaChiHDTB.VIDO = this.diachiTB.Lat;
          rowDiaChiHDTB.KINHDO_LD = this.diachiLD.Lng;
          rowDiaChiHDTB.VIDO_LD = this.diachiLD.Lat;
          this.dsDiaChiHDTB.push(rowDiaChiHDTB);
        }
      }

      console.log("TaoDuLieu_DiaChi DONE", this.dsDiaChi);
    },
    async Lay_Ma_GD_V2() {
      var magd = "";
      await API.post_sinh_magd_v2(this.axios, {
        donvi_id: this.$root.token.getDonViID(),
        loaihd_id: LoaiHopDong.CHUYENDOI_LH,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          magd = response.data.data;
        }
      });
      return magd;
    },
    async Lay_Ma_GD() {
      var magd = "";
      await API.post_sinh_magd(this.axios, {
        loaihd_id: LoaiHopDong.CHUYENDOI_LH,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          magd = response.data.data;
        }
      });
      return magd;
    },
    async LAY_DS_DBKH_THEO_KHID(khachhang_id) {
      var kh = {};
      await API.post_lay_ds_dbkh_theo_khid(this.axios, {
        in_khachhang_id: 903443,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          kh = response.data.data[0];
        }
      });
      return kh;
    },
    async GetKey(keyname) {
      var key = 0;
      await API.post_get_keys(this.axios, { keyname: keyname }).then(
        (response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            key = response.data.data;
          }
        }
      );

      return key;
    },
    async TaoDuLieu_HDKH(themmoi) {
      console.log("TaoDuLieu_HDKH ThemMoi:", themmoi);
      this.dsHDKH = [];
      var rowHDKH = {};
      var vmagd_temp = "";
      if (themmoi == true) {
        this.khachhang.hdkh_id = await this.GetKey("HD_KHACHHANG");
        this.khachhang.hdkh_id = this.khachhang.hdkh_id * 1;
      }

      rowHDKH = Object.assign(
        {},
        await this.LAY_DS_DBKH_THEO_KHID(this.khachhang.khachhang_id)
      );
      rowHDKH = this.UpperCasePropertyList([rowHDKH])[0];
      if (rowHDKH.GHICHU)
        rowHDKH.GHICHU = rowHDKH.GHICHU.replace(/update/gi, "updat");
      rowHDKH.HDKH_ID = this.khachhang.hdkh_id;
      if (themmoi == true) {
        if (this.state.ts_sinhma_gd_theo_donvi)
          vmagd_temp = await this.Lay_Ma_GD_V2();
        else vmagd_temp = await this.Lay_Ma_GD();

        if (vmagd_temp.indexOf("ERROR:") > 0) {
          this.$toast.error(vmagd_temp.split(":")[1]);
          this.loi_sinh_magd = true;
        }

        this.khachhang.magd = vmagd_temp;
        rowHDKH.MA_GD = this.khachhang.magd;
        rowHDKH.MA_HD = this.khachhang.mahd;
        rowHDKH.NGAY_YC = moment(new Date()).format("DD/MM/YYYY HH:mm:ss"); //TODO can doi thanh lay sysdate tu server thong qua ngay_cn cua user login, la goi ham sysdate tu server
      } else {
        rowHDKH.MA_GD = this.khachhang.magd;
        rowHDKH.MA_HD = this.khachhang.mahd;
        rowHDKH.NGAY_YC = moment(new Date()).format("DD/MM/YYYY HH:mm:ss"); //TODO can doi thanh lay sysdate tu server thong qua ngay_cn cua user login, la goi ham sysdate tu server
      }

      if (!rowHDKH.HDKH_ID || rowHDKH.HDKH_ID == 0) {
        if (this.khachhang.hdkh_id && this.khachhang.hdkh_id > 0)
          rowHDKH.HDKH_ID = this.khachhang.hdkh_id;
        else
          await API.post_get_keys(this.axios, { keyname: "HD_KHACHHANG" }).then(
            (response) => {
              if (
                response &&
                response.data &&
                response.data.data &&
                response.data.error_code &&
                response.data.error_code === "BSS-00000000"
              ) {
                rowHDKH.HDKH_ID = response.data.data; // 4039
                this.khachhang.hdkh_id = rowHDKH.HDKH_ID;
              }
            }
          );
      }

      if (!this.khachhang.makh) {
        //TODO sinh ma khach hang
        //this.khachhang.makh = new HoanThienHoSoFacade().SINH_MAKH(Convert.ToInt32(cboDonViQL.SelectedValue));
        this.khachhang.khachhang_id = 0;
      }
      rowHDKH.MA_KH = this.khachhang.makh;
      if (this.khachhang.khachhang_id > 0) {
        rowHDKH.KHACHHANG_ID = this.khachhang.khachhang_id;
        rowHDKH.NGAYLAP_HD = moment(this.NgayLapHD, "DD/MM/YYYY").format(
          "DD/MM/YYYY HH:mm:ss"
        );
      } else {
        if (!this.khachhang.hdkh_id || this.khachhang.hdkh_id == 0)
          rowHDKH.NGAYLAP_HD = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
        else
          rowHDKH.NGAYLAP_HD = moment(this.NgayLapHD, "DD/MM/YYYY").format(
            "DD/MM/YYYY HH:mm:ss"
          );
        // rowHDKH.NGAYLAP_HD = this.NgayLapHD;
      }

      rowHDKH.KHACHHANG_ID = this.khachhang.khachhang_id;
      rowHDKH.TEN_KH = this.khachhang.tenkh;
      rowHDKH.DIACHI_KH = this.diachiKH.DIACHI;

      rowHDKH.SO_DT = this.khachhang.dienthoai;
      rowHDKH.SO_GT = this.khachhang.so_gt;

      if (rowHDKH.NGAYCAP) {
        rowHDKH.NGAYCAP = moment(rowHDKH.NGAYCAP, "YYYY-MM-DD HH:mm:ss").format(
          "DD/MM/YYYY HH:mm:ss"
        );
      }

      if (rowHDKH.NGAY_SN)
        rowHDKH.NGAY_SN = moment(rowHDKH.NGAY_SN, "YYYY-MM-DD HH:mm:ss").format(
          "DD/MM/YYYY HH:mm:ss"
        );

      if (this.hdkh_cha_id > 0) {
        rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id;
      }

      rowHDKH.DONVI_ID = this.$root.token.getDonViID();
      rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID();
      rowHDKH.LOAIHD_ID = LoaiHopDong.CHUYENDOI_LH;

      if (this.filterbox.ctv.ischecked && this.filterbox.ctv.value) {
        rowHDKH.CTV_ID = this.filterbox.ctv.ma_ctv; //ctv_id;
        if (
          this.listbox.hinhthuc_tthi.list &&
          this.listbox.hinhthuc_tthi.list.length > 0
        )
          if (this.listbox.hinhthuc_tthi.value)
            rowHDKH.HTTTHI_ID = this.listbox.hinhthuc_tthi.value; //Convert.ToInt32(cboHinhThucTThi.SelectedValue);
      }
      if (this.filterbox.nguoigt.ischecked && this.filterbox.nguoigt.value) {
        rowHDKH.NHANVIENGT_ID = this.filterbox.nguoigt.nguoigt_id; //nguoigt_id;
      }

      rowHDKH.KENHTN_ID = this.iKenhTN;
      rowHDKH.MA_DUAN = this.maduan;

      rowHDKH.NGUOI_CN = this.nguoi_cn;
      rowHDKH.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");

      rowHDKH.MAY_CN = this.may_cn;

      this.dsHDKH.push(Object.assign({}, rowHDKH));

      // console.log("TaoDuLieu_HDKH DONE ", this.dsHDKH);
    },
    MapDataTT_Json(ds) {
      // console.log("MapDataTT_Json ds", ds);
      if (!ds || ds.length <= 0) return [];
      var array = [];
      ds.forEach((data, index0) => {
        var row = {};
        ds.columns.forEach((col, index) => {
          row[col.column_name] = data[index];
        });
        array.push(Object.assign({}, row));
      });

      return array;
    },
    async TaoDuLieu_HDTT(themmoi) {
      console.log("TaoDuLieu_HDTT");
      this.dsHDTT = [];
      var rowHDTT = {};
      var mapDataTT_Json = [];
      await API.post_lay_ds_dbtt_theo_ttid(this.axios, {
        thanhtoan_id: this.thanhtoan.thanhtoan_id,
      }).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === "BSS-00000000") {
          mapDataTT_Json = response.data.data;
        } else if (response.data.data.length === 0) {
            return this.$toast.error('Không có dữ liệu hóa đơn thanh toán')
        }
      });
      // console.log("rmapDataTT_Json", mapDataTT_Json);
      rowHDTT = Object.assign({}, mapDataTT_Json[0]);
      rowHDTT = this.UpperCasePropertyList([rowHDTT])[0];
      if (rowHDTT.GHICHU)
        rowHDTT.GHICHU = rowHDTT.GHICHU.replace(/update/gi, "updat");
      delete rowHDTT.SYS_NC00038$;
      if (themmoi == true) {
        this.hdtt_id = await this.GetKey("HD_THANHTOAN");
        this.hdtt_id = this.hdtt_id * 1;
      }

      rowHDTT.HDTT_ID = this.hdtt_id;
      rowHDTT.HDKH_ID = this.khachhang.hdkh_id;

      rowHDTT.NGUOI_CN = this.nguoi_cn;
      rowHDTT.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      rowHDTT.MAY_CN = this.may_cn;

      this.dsHDTT.push(Object.assign({}, rowHDTT));

      console.log("TaoDuLieu_HDTT DONE", this.dsHDTT);
    },
    async TaoDuLieu_HDTB(themmoi) {
      // console.log("TaoDuLieu_HDTB");
      this.dsHDTB = [];
      var rowHDTB = {};

      if (themmoi == true) {
        this.hdtb_id = await this.GetKey("HD_THUEBAO");
        this.hdtb_id = this.hdtb_id * 1;
      }

      rowHDTB.HDTB_ID = this.hdtb_id;
      rowHDTB.HDKH_ID = this.khachhang.hdkh_id;
      rowHDTB.HDTT_ID = this.hdtt_id;
      if (this.thuebao.thuebao_id > 0)
        rowHDTB.THUEBAO_ID = this.thuebao.thuebao_id;

      rowHDTB.MA_TB = this.ma_tb;
      rowHDTB.TEN_TB = this.thuebao.tentb ? this.thuebao.tentb.trim() : null;
      rowHDTB.DIACHI_TB = this.diachiTB.DIACHI;
      rowHDTB.DIACHI_LD = this.diachiLD.DIACHI;
      rowHDTB.DIACHI_TT = this.diachiTB.DIACHI;
      rowHDTB.GHICHU = this.thuebao.ghichu;
      rowHDTB.KIEULD_ID = this.listbox.kieu_ld.value;
      rowHDTB.LOAITB_ID = this.listbox.loaihinh_tb_moi.value;
      rowHDTB.DOITUONG_ID = this.listbox.doituong.value;
      rowHDTB.DONVI_ID = this.listbox.donvi_ql.value;
      rowHDTB.TTHD_ID = TrangThaiHD.MOI;
      rowHDTB.DICHVUVT_ID = this.listbox.dichvu_vt_moi.value;

      rowHDTB.QUYTRINH_ID = this.quytrinh_id;
      rowHDTB.MUCCUOCTB_ID = this.listbox.muccuoc_tb.value;
      let mc = this.listbox.muccuoc_tb.list.find(
        (x) => (x.id = rowHDTB.MUCCUOCTB_ID)
      );
      if (mc) {
        rowHDTB.MUCCUOC_TB = mc.tenmuccuoc;
      }

      rowHDTB.MUCUOC_TB = rowHDTB.MUCCUOC_TB;
      rowHDTB.MUCUOCTB_ID = rowHDTB.MUCCUOCTB_ID;

      rowHDTB.NGUOI_CN = this.nguoi_cn;
      rowHDTB.NGAY_CN = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      rowHDTB.MAY_CN = this.may_cn;

      this.dsHDTB.push(Object.assign({}, rowHDTB));

      // console.log("TaoDuLieu_HDTB DONE", this.dsHDTB);
    },
    async TaoDuLieu_THONGTIN_TT(themmoi) {
      this.dsTTTT = [];
      //code C# đang bị comment lại
    },
    async TaoDuLieu_CT_TIENHD(themmoi) {
      // console.log("TaoDuLieu_CT_TIENHD");
      this.dsCTTHD = [];
      var rowCTTHD = {};
      //Inset, 4 loại tiền vào dabase
      //1: Tiền khoản mục (theo loại hợp đồng: đặt mới, chuyển quyền, chuyển dịch)
      //2: Tiền cài đặt (Áp dụng cho dịch vụ INTERNET,
      //3: tien dịch vụ
      //4: tiền mua thiết bị
      var soluong = 1;
      // console.log("this.dsCTM_TBI", this.dsCTM_TBI);
      for (var i = 0; i < 3; i++) {
        //tiền lắp mới =1 + khuyến mại lắp mới
        if (i == 0) {
          rowCTTHD = {};
          rowCTTHD.HDTB_ID = this.hdtb_id;
          // if (themmoi == true) {
          //     rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
          // }
          rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD")) * 1;
          var tien = 0; //Tiền LĐ chưa vat
          var vat = 0; //Vat
          rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_TDLOAIHINHTB;

          var tienLd = this.dtienld;
          vat = Math.round(tienLd / 11, 0);
          tien = tienLd - vat;
          rowCTTHD.TIEN = tien;
          rowCTTHD.VAT = vat;
          // end hoàng
          var vat_tt = 0;
          rowCTTHD.LOAI_ID = 1;
          rowCTTHD.ID = KHOANMUC_TT.KMTT_TDLOAIHINHTB;
          this.dsCTTHD.push(Object.assign({}, rowCTTHD));
        }
        if (i == 1) {
          //Tiền mua thiết bị =5 + tiền khuyến mại thiết bị
          for (var j = 0; j < this.dsCTM_TBI.length; j++) {
            rowCTTHD = {};
            rowCTTHD.HDTB_ID = this.hdtb_id * 1;
            //rowCTTHD.CT_TIENHD_ID = this.dsCTM_TBI[j]["soluong"]
            // if (themmoi == true) {
            //     rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
            // }
            rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD")) * 1;
            rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_MUATHIETBI;
            var tien = 0;
            var vat = 0;

            soluong = this.dsCTM_TBI[j]["soluong"] * 1;
            tien = this.dsCTM_TBI[j]["tien"] * soluong;
            vat = this.dsCTM_TBI[j]["vat"] * soluong;

            rowCTTHD.TIEN = tien;
            rowCTTHD.VAT = vat;
            rowCTTHD.ID = this.dsCTM_TBI[j]["loaitbi_id"] * 1;

            var tien_tt = 0;
            tien_tt = tien;
            rowCTTHD.TIEN = tien_tt;
            var vat_tt = 0;
            rowCTTHD.LOAI_ID = 3;
            this.dsCTTHD.push(Object.assign({}, rowCTTHD));
          }

          for (var k = 0; k < this.dsCTM_TBI.length; k++) {
            rowCTTHD = {};
            //rowCTTHD = dsCTTHD.CT_TIENHD.NewCT_TIENHDRow();
            rowCTTHD.HDTB_ID = this.hdtb_id;
            // if (themmoi == true) {
            //     rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
            // }
            rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD")) * 1;
            rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_TRATRUOC_THIETBI;
            var tien = 0;
            var vat = 0;
            soluong = this.dsCTM_TBI[k]["soluong"] * 1;
            tien = this.dsCTM_TBI[k]["tien"] * soluong;
            vat = this.dsCTM_TBI[k]["vat"] * soluong;

            rowCTTHD.TIEN = tien;
            rowCTTHD.VAT = vat;
            rowCTTHD.ID = this.dsCTM_TBI[k]["loaitbi_id"] * 1;

            var tien_tt = 0;
            tien_tt = tien;
            rowCTTHD.TIEN = tien_tt;
            var vat_tt = 0;
            rowCTTHD.LOAI_ID = 3;
            this.dsCTTHD.push(Object.assign({}, rowCTTHD));
          }
        }

        //nhapt thêm gvgt 18082017
        if (i == 2) {
          rowCTTHD = {};
          rowCTTHD.HDTB_ID = this.hdtb_id;
          // if (themmoi == true) {
          //     rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD"))*1;
          // }
          rowCTTHD.CT_TIENHD_ID = (await this.GetKey("CT_TIENHD")) * 1;
          var tien = 0; //Tiền LĐ chưa vat
          var vat = 0; //Vat
          rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DICHVU;

          var tienDV = this.dichvu.dtien_dv * 1 + this.dichvu.dvat_dv * 1;
          vat = Math.round(tienDV / 11, 0);
          tien = tienDV - vat;
          rowCTTHD.TIEN = tien;
          rowCTTHD.VAT = vat;
          // end hoàng
          rowCTTHD.LOAI_ID = 1;
          rowCTTHD.ID = KHOANMUC_TT.KMTT_DICHVU;
          this.dsCTTHD.push(Object.assign({}, rowCTTHD));
        }
      }

      // console.log("TaoDuLieu_CT_TIENHD DONE", this.dsCTTHD);
    },
    async KiemTraDL_KhachHang() {
      return true;
    },
    async KiemTraDL_ThueBao() {
      if (this.khachhang.mahd == "" && this.khachhang.khachhang_id != 0) {
        this.$toast.error("Hãy nhập mã hợp đồng !");
        this.$refs.txtMaHD.focus();
        return false;
      }
      if (!this.listbox.muccuoc_tb.list || this.listbox.muccuoc_tb.list.length <= 0) {
        this.$toast.error("Hãy nhập mức cước thuê bao!");
        return false;
      }
      if (this.thuebao.tentb == "") {
        this.$toast.error("Hãy nhập tên thuê bao!");
        this.$refs.txtTenTB.focus();
        return false;
      }

      // if (this.kt_khoso_cd && this.kt_khoso_cd == true)
      // {
      //     if (this.listbox.dichvu_vt_moi.value*1 == DichVuVienThong.CO_DINH
      //         && this.listbox.dichvu_vt.value*1 == DichVuVienThong.IMS)
      //     {
      //         // Chuyển đổi từ IMS -> Cố định. cần kiểm tra xem số IMS đấy có trong kho số cố định không ? Nếu ko --> ko cho làm
      //         var kt_cd = bangts.kiemtra_cdlh_ims_cd(Convert.ToInt32(cboLoaiHinhTBMoi.SelectedValue), txtMaTB.Text.Trim());
      //         if (kt_cd == false)
      //         {
      //             this.$toast.error("Số máy IMS không có trong kho số Cố định. Bạn không được thực hiện !"); //=> chuyển kiểm tra trong package
      //             return false;
      //         }
      //     }
      // }

      if (
        this.listbox.loaihinh_tb_moi.value * 1 ==
        LoaiHinhTB.IMS_SIPTRUNKING
      ) {
        if (
          this.txtSoLuong.toString().trim() == "" ||
          this.txtSoLuong.toString().trim() == "0"
        ) {
          this.$toast.error(
            "Bạn chưa nhập số lượng cuộc gọi cho thuê bao Sip trunking!"
          );
          this.$refs.txtSoLuong.focus();
          return false;
        }
        if (this.CheckIsNumber(this.txtSoLuong.toString().trim()) == false) {
          this.$toast.error("Số lượng cuộc gọi phải là kiểu số!");
          this.$refs.txtSoLuong.focus();
          return false;
        }

        if (this.listbox.ht_chitietdv_ims.value * 1 == 0) {
          this.$toast.error("Hãy chọn chi tiết DV");
          //this.$refs.cboChiTietDVMoi.focus();
          return false;
        }
        if (
          this.listbox.ht_chitietdv_ims.value * 1 ==
          this.ChiTietDV_IMS.internet
        ) {
          if (
            this.state.phainhap_ip_siptrunking &&
            this.state.phainhap_ip_siptrunking == true &&
            this.ip_ims.trim() == ""
          ) {
            this.$toast.error("Hãy nhập địa chỉ IP !");
            this.$refs.txtIp_IMS.focus();
            //txtIp_IMS.BackColor = Color.Azure;
            return false;
          }
        }
      }

      // hoangpkn : 31/10/2013
      // thêm kiểm tra gói trước khi lập phụ lục thay đổi LHTB
      // if (Convert.ToInt32(cboDichVuVT.SelectedValue) == DichVuVienThong.CO_DINH || Convert.ToInt32(cboDichVuVT.SelectedValue) == DichVuVienThong.IMS)
      // {
      //     string kq = "";
      //     int vtd_id = -1;
      //     int vmc_id = 0;
      //     kq = laphd.Kiemtra_goidadv_cd_ims(thuebao_id, Convert.ToInt32(cboLoaiHinhTBMoi.SelectedValue), vtd_id, vmc_id); //=> chuyển db check
      //     if (kq != "1")
      //     {
      //         Message_Box.ShowWarning(kq);
      //         return false;
      //     }
      // }

      // var kieu =  await this.MAP_ID("kieu", "css.kieu_ld", "where kieuld_id =" + this.listbox.kieu_ld.value*1);
      let kieu = commonFn.GetData(
        await API.fn_tt_kieu_ld(this.axios, {
          param: this.listbox.kieu_ld.value * 1,
          type: 1,
        })
      );
      if ((kieu == 1 || kieu == 2) && (!this.thuebaocd.matn || this.thuebaocd.matn == "")) {
        this.$toast.error(
          "Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!"
        );
        this.$refs.txtMaTN_ADSL.focus();
        return false;
      }
      // end hoàng thêm

      //SonNMP: Không có khu vực không có tạo hợp đồng
      if (!this.dsHDTB_KV || this.dsHDTB_KV.length == 0 || !this.dsHDTB_KV[0]["KHUVUC_ID"])
      {
        this.$toast.error("Cần chọn khu vực");
        this.$refs.txt_KhuVuc.focus();
        return false;
      }

      return true;
    },
    async KIEMTRA_CDLH() {
      return true;
    },
    ds_CTKM() {
      if (!this.dsKM_HDTB) return "";
      var sDS = "-1";
      for (var km of this.dsKM_HDTB) {
        if (km["chitietkm_id"]) sDS = sDS + "," + km["chitietkm_id"];
      }
      for (var km of this.dsDC_HDTB) {
        if (km["chitietkm_id"]) sDS = sDS + "," + km["chitietkm_id"];
      }

      return "" + sDS;
    },
    async MAP_ID(in_id_neo, in_table, in_dk) {
      var data = 0;
      await API.post_check_map_id(this.axios, {
        in_dk: in_dk,
        in_id_neo: in_id_neo,
        in_table: in_table,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          data = response.data.data * 1;
        }
      });
      return data;
    },
    tsbtnXoa_Click() {
      this.$bvModal
        .msgBoxConfirm("Xác nhận hành động", {
          title: "Bạn thật sự muốn xóa hợp đồng không ?",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            try {
              var DS_HDTB = this.lvwThueBao.map((x) => ({
                HDTB_ID: x.hdtb_id,
                LOAITB_ID: x.loaitb_id,
              }));
              var pdata = {
                vhdkh_id: this.khachhang.hdkh_id,
                vloaihd_id: LoaiHopDong.CHUYENDOI_LH,
              };
              this.loading(true);
              await API.post_sp_xoa_hdkh_v2(this.axios, pdata).then(
                (response) => {
                  this.loading(false);
                  if (
                    response &&
                    response.data &&
                    response.data.error_code &&
                    response.data.error_code === "BSS-00000000"
                  ) {
                    //var data = this.LowerCasePropertyList([JSON.parse(response.data.data)])[0];
                    this.$toast.success(
                      "Đã xóa hợp đồng chuyển đổi loại hình thành công trên hệ thống của viễn thông tỉnh"
                    );
                    this.clear();
                    this.buttonSet = 3;
                  }
                }
              );
            } catch (ex) {
              this.$toast.error("" + ex);
              this.loading(false);
            }
          }
        });
    },
    async tsbtnThemTB_Click() {
      try {
        this.loading(true)
        var check_thuebao_id = -1
        if (this.lvwThueBao && this.lvwThueBao.length > 0) {
          check_thuebao_id = this.lvwThueBao.findIndex(e => Number(e.thuebao_id) == Number(this.thuebao.thuebao_id))
        }   
        if (check_thuebao_id >= 0) {
          this.$toast.error(`Thuê bao ID ${this.thuebao.thuebao_id} đang bị trùng với thuê bao hiện có trong hợp đồng, bạn hãy thao tác lại!`)
          this.loading(false)
          return
        }   
        let reqHuongGiao = {
          loaiHdId: LoaiHopDong.CHUYENDOI_LH,
          loaiTbId: this.listbox.loaihinh_tb_moi.value * 1,
          tocDoId: -1,
          mucCuocId: -1,
          kieuLdId: this.listbox.kieu_ld.value * 1,
        };
        let ds = commonFn.GetData(
          await API.post_lay_huonggiao_tiepnhan(this.axios, reqHuongGiao)
        );
        // console.log("dshuonggiao_tiepnhan", ds);
        this.huonggiao_tn = 0;
        this.luong_id = 0;
        if (!ds || !ds.quytrinh_id) {
          this.$toast.error(
            "Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!"
          );
          return;
        }

        this.huonggiao_tn = ds["huonggiao_id"];
        this.quytrinh_id = ds["quytrinh_id"];
        this.luong_id = ds["luong_id"];

        if ((await this.TaoDuLieu(true, true)) == false) return;
        await API.post_lay_danhba_theo_matb(this.axios, {
          in_ma_tb: this.ma_tb,
          in_dichvuvt_id: this.listbox.dichvu_vt.value,
          in_donvi_dl_id: 0,
        }).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000" &&
            response.data.data && response.data.data.length > 0)
          {
            if (this.thuebao.thuebao_id != response.data.data[0]["thuebao_id"]) {
              this.$toast.error("Kiểm tra lại dữ liệu ma_tb và thuebao_id");
              return;
            }
          }
          else{
            this.$toast.error("Kiểm tra lại dữ liệu ma_tb và thuebao_id");
            return;
          }
        });

        var pdata = {
          dichvu_id_moi: this.listbox.dichvu_vt_moi.value * 1,
          loaitb_id_moi: this.listbox.loaihinh_tb_moi.value * 1,
          quytrinh_id: this.quytrinh_id,
          luong_id: this.luong_id,
          huonggiao_tn: this.huonggiao_tn,
          dichvu_id: this.listbox.dichvu_vt.value * 1,
          loaitb_id: this.listbox.loaihinh_tb.value * 1,
          ma_tb: this.ma_tb,
          thuebao_id: this.thuebao.thuebao_id,
          matn_adsl: this.thuebaocd.matn,
          kieuld_id: this.listbox.kieu_ld.value,
          khachhang_id: this.khachhang.khachhang_id,
          ma_gd: this.khachhang.magd,
          loaihd_id: LoaiHopDong.CHUYENDOI_LH,
          hdtb_id: this.hdtb_id,
          hdkh_id: this.khachhang.hdkh_id,
          js_hd_thuebao: this.UpperCasePropertyList(this.dsHDTB),
          js_hdtb_ims: this.UpperCasePropertyList(this.dsHDTB_IMS),
          js_thongtin_tt: this.UpperCasePropertyList(this.dsTTTT),
          js_ct_tienhd: this.UpperCasePropertyList(this.dsCTTHD),
          js_ct_mua_tbi: this.UpperCasePropertyList(this.dsCTM_TBI),
          js_diachi: this.UpperCasePropertyList(this.dsDiaChi),
          js_diachi_hdtb: this.UpperCasePropertyList(this.dsDiaChiHDTB),
          js_hdtb_dv: this.UpperCasePropertyList(this.dsHDTBDV),
          js_dangky_dvgt: this.UpperCasePropertyList(this.dsDK_DVGT),
          js_hdtb_kv: this.UpperCasePropertyList(this.dsHDTB_KV),
          js_hdtb_cd: this.UpperCasePropertyList(this.dsHDTB_CD),
          js_hd_khachhang: this.UpperCasePropertyList(this.dsHDKH),
          js_hd_thanhtoan: this.UpperCasePropertyList(this.dsHDTT),
          js_diachi_hdkh: this.UpperCasePropertyList(this.dsDiaChiHDKH),
          js_diachi_hdtt: this.UpperCasePropertyList(this.dsDiaChiHDTT),
        };

        // console.log("pdata", pdata);

        await API.post_fn_chuyendoicdims_themhdthuebao(this.axios, {
          p_ds_para: JSON.stringify(pdata),
        }).then(async (response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === "BSS-00000000"
          ) {
            var apidata = JSON.parse(response.data.data.p_js_returnds);
            // console.log("API.post_fn_chuyendoicdims_themhdthuebao", apidata);

            if (apidata["ERROR_CODE"] != 1) {
              this.$toast.error(apidata["MESSAGE"]);
              return;
            }
            this.buttonSet = 3;
            this.$toast.success("Thêm mới thuê bao thành công!");
            await this.txtMaTB_KeyPress()
          }
        });
      } catch (ex) {
        commonFn.showException(this, ex);
      } finally {
        this.loading(false)
      }
    },
    tsbtnXoaTB_Click() {
      if (!this.hdtb_id || this.hdtb_id <= 0) {
        this.$toast.error("Bạn chưa chọn thue bao!");
        return;
      }

      this.$bvModal
        .msgBoxConfirm("Xác nhận hành động", {
          title: "Bạn thật sự muốn xóa thuê bao không ?",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            try {
              this.loading(true);
              await this.XOA_HDTB(this.hdtb_id);
              await this.HT_TT();
              this.loading(false);
            } catch (ex) {
              this.$toast.error("" + ex);
            }
          }
        });
    },

    async XOA_HDTB(hdtb_id) {
      await API.xoa_hdtb(this.axios, { hdtb_id: hdtb_id }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
        }
      });
    },
    ChuyenTrang(page, param) {
      if (!this.lvwThueBao || this.lvwThueBao.length == 0) {
        this.$toast.error('Bạn chưa tạo hợp đồng!')
        return
      }
      var ds_thuebao_id = this.lvwThueBao.map(e => e.thuebao_id)
      var duplicatedArray = this.findDuplicates(ds_thuebao_id)
      if (duplicatedArray && duplicatedArray.length > 0) {
        this.$toast.error('Danh sách thuê bao có id bị trùng: ' + duplicatedArray.toString())
        return
      }
      this.$router.push({ name: page, params: param });
    },
    findDuplicates(arr) {
      let sorted_arr = arr.slice().sort(); // You can define the comparing function here. 
      // JS by default uses a crappy string compare.
      // (we use slice to clone the array so the
      // original array won't be modified)
      let results = [];
      for (let i = 0; i < sorted_arr.length - 1; i++) {
        if (sorted_arr[i + 1] == sorted_arr[i]) {
          results.push(sorted_arr[i]);
        }
      }
      return results;
    },
    async tsbtnThongTinLH_Click() {
      this.$refs.popupThongTinLienHe.show();
      // this.loading(true);
      // this.state.modalTitle = "Thông tin liên hệ";
      // this.popupComponent = () => import("@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue");
      // this.popupComponentName = "popupLienHe";
      // this.popupComponentAttr = {hdkh_id: this.khachhang.hdkh_id, khachhang_id: this.khachhang.khachhang_id}
      // await this.popupComponent().then((x) => {});
      // this.loading(false);
      this.Popup("popupComponents");
    },
    tsbtnNhapMoi_Click() {
      this.buttonSet = 1;
      this.clear();
    },
    async LoadTuHopDongTuVan(phdkh_cha_id) {
      let req = {
        hdkh_cha_id: phdkh_cha_id,
        kieu: 0,
      };

      await API.sp_lay_thongtin_hdkh_tuvan(this.axios, req).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          let jsonData = JSON.parse(response.data.data);
          // console.log("jsonData", jsonData);
          if (jsonData && jsonData.RESULT.KHACHHANG_ID != 0) {
            this.state.khachhang_id = Number(jsonData.RESULT.KHACHHANG_ID);
            this.khachhang.magd = jsonData.RESULT.MA_KH;
          }
        }
      });
    },
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },

    clear() {
      this.maduan = "";
      this.vchuquan_id = 0;
      this.vchuquanld_id = 0;
      this.pass_ims = "";
      //txtMaDoiCap_ADSL.Text = "";
      this.thuebaocd.matn = "";
      this.thuebao.codinh.somay = "";
      this.ip_ims = "";
      this.listbox.ht_chitietdv_ims.value = 0;
      //doituong_id = 1;//Defaul: tư nhân
      this.khachhang.magd = "";
      this.khachhang.mahd = "";
      this.khachhang.makh = "";
      this.khachhang.dienthoai = "";
      this.diachiKH.DIACHI = "";
      this.diachiTB.DIACHI = "";
      this.diachiLD.DIACHI = "";
      this.lvwThueBao = [];
      this.thuebao.ghichu = "";
      this.khachhang.tenkh = "";
      this.khachhang.diachiKH = "";
      //dptNgayYC.Value = tt_nd.ngay_cn;
      //dtpNgayLapHD.Value = tt_nd.ngay_cn;
      this.hdtb_id = 0;
      this.thuebao.thuebao_id = 0;

      this.thuebao.tentb = "";
      this.thuebao.diachiLD = "";

      this.thuebao.diachiTB = "";

      //this.listbox.loaihinh_tb_moi.value = 0

      this.ghichu = "";
      this.ma_tb = "";
      //Hiếu bổ sung clear form ngày 04/09/2010
      this.lvwThueBao = [];
      ///TuấnNA thêm code clear các biến trên form -- Ngày 26/09/2010
      this.tongtienhd = 0;
      //Lam khởi tạo lại các biến lấy tiền 17/09/2010
      this.trangbi_id = 3;
      this.tien_tb = 0;
      this.vat_tb = 0;
      this.tien_dv = 0;
      this.vat_dv = 0;
      this.dsCTM_TBI = [];
      this.HienThiTienDichVu(this.tien_dv, this.vat_dv);
      this.HT_ThietBi();
      this.LayTienTheoKhoanMuc();
      this.LAY_DS_DVGT_MACDINH(this.hdtb_id, this.listbox.loaihinh_tb_moi.value);
      //cbo_TramTC.EditValue = cbo_DonViQuanLy.EditValue = null;
      this.thuebao.khuvuc = "";
      this.thuebao.khuvuc_id = 0;
    },
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    GetRandomText(length) {
      const characters =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
      let result = "";
      const charactersLength = characters.length;
      for (let i = 0; i < length; i++) {
        result += characters.charAt(
          Math.floor(Math.random() * charactersLength)
        );
      }
      return result;
    },
    async cboDichVuVT_SelectedValueChanged() {
      if (
        this.listbox.dichvu_vt.value.toString() ==
        DichVuVienThong.CO_DINH.toString()
      ) {
        this.listbox.dichvu_vt_moi.value = DichVuVienThong.IMS;
      } else {
        this.listbox.dichvu_vt_moi.value = DichVuVienThong.CO_DINH;
      }

      this.state.dichvuvt_id = this.listbox.dichvu_vt.value * 1;

      //bangts.HT_DonVi_loaidv_Combobox(cboVeTinhCD, tt_nd.donvi_dl_id, LOAI_DV.TRAM_VT);
      //bangts.HT_LoaiHinh_TB_ThayDoiLHTB_Combobox(cboLoaihinhTB, dichvuvt_id, 0);
      this.listbox.loaihinh_tb.value =
        this.listbox.loaihinh_tb.list.filter(
          (x) => x.dichvuvt_id == this.listbox.dichvu_vt.value
        )[0].loaitb_id * 1;
      this.listbox.loaihinh_tb_moi.value =
        this.listbox.loaihinh_tb_moi.list.filter(
          (x) => x.dichvuvt_id == this.listbox.dichvu_vt_moi.value
        )[0].loaitb_id * 1;
    },

    async cboDichVuVTMoi_SelectedValueChanged() {
      // console.log("cboDichVuVTMoi_SelectedValueChanged");
      if (
        this.listbox.dichvu_vt_moi.value.toString() ==
        DichVuVienThong.IMS.toString()
      ) {
        document.getElementById("tabThongTin").click();
        this.controls.lblThietBiIMS.Visible = true;
        this.controls.cboThietBiIMS.Visible = true;
        this.controls.lbSoLuong.Visible = true;
        this.controls.txtSoLuong.Visible = true;
        this.controls.txtIp_IMS.Visible = true;
        this.controls.cboChiTietDVMoi.Visible = true;
      } else if(this.listbox.dichvu_vt_moi.value.toString() ==
        DichVuVienThong.CO_DINH.toString()){
        document.getElementById("tabThongTin").click();
        this.controls.lblThietBiIMS.Visible = false;
        this.controls.cboThietBiIMS.Visible = false;
        this.controls.lbSoLuong.Visible = false;
        this.controls.txtSoLuong.Visible = false;
        this.controls.txtIp_IMS.Visible = false;
        this.controls.cboChiTietDVMoi.Visible = false;
      }
      else if(this.listbox.dichvu_vt_moi.value.toString() ==
        DichVuVienThong.DICHVU_CNTT.toString()){
        document.getElementById("tabThueBao").click();
        if(this.listbox.loaihinh_tb.value != undefined && this.listbox.loaihinh_tb.value != null)
          await this.Init_Properties(this.listbox.loaihinh_tb.value, 'HDTB_CNTT', '00000', this.ItemList)
      }
      //this.listbox.loaihinh_tb_moi.value = this.listbox.loaihinh_tb.list.filter(lh=>(lh.dichvuvt_id==listbox.dichvu_vt_moi.value))[0].loaitb_id*1;
      //bangts.HT_LoaiHinh_TB_ThayDoiLHTB_Combobox(cboLoaiHinhTBMoi, Convert.ToInt32(cboDichVuVTMoi.SelectedValue), 1);
    },
    CheckIsNumber(str1) {
      return isNaN(str1) == false;
    },
    async Init_Properties(vloaitb_id, table_name, opt_list,ItemList) {
      await API.post_lay_danhsach_thuoctinh_datmoi(this.axios, {
        vloaitb_id: vloaitb_id,
        vopt_list: opt_list,
        vten_bang: table_name
      }).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          let ds = response.data.data
          this.ItemList = []
          if (ds.length <= 0) return
          for (var i = 0; i < ds.length; i++) {
            // if(!this.ItemList[i]) continue;
            let row = ds[i]
            if (!this.ItemList[i]) this.ItemList[i] = {}
            this.ItemList[i].sFieldName = row['field_name']
            this.ItemList[i].sCaption = row['ten_hienthi']
            this.ItemList[i].iAtt = row['att']
            this.ItemList[i].iDataType = row['data_type']
            this.ItemList[i].sMaxValue = row['max_value']
            this.ItemList[i].sMinValue = row['min_value']
            this.ItemList[i].sValue = row['field_value']
            this.ItemList[i].nFieldLength = row['field_length'] ? 0 : row['field_length']
            this.ItemList[i].bIsNotNull = row['field_nullable'] ? false : row['field_nullable'] == '0' ? true : false
            this.ItemList[i].bIsUnique = row['field_unique'] ? false : row['field_unique'] == '0' ? false : true
            this.ItemList[i].sLookUpTableName = row['parent_table']
            this.ItemList[i].sLookUpKeyField = row['parent_keyfield']
            this.ItemList[i].sLookUpValueField = row['parent_descfield']
            this.ItemList[i].strDepandField = row['depend_field']
            this.ItemList[i].StrSql = row['lookup_sql']
            if (row['lookup_sql_response']) this.ItemList[i].SqlData = this.UpperCasePropertyList(row['lookup_sql_response'])
          }
        }
      })
    },
    async HienThiTTMoRong(hdtb_id, dichvuvt_id) {
      let ds = []
      let dt = []
      let apidata = {}
      await API.post_fn_hienthi_tt_morong(this.axios, { ds_para: JSON.stringify({ HDTB_ID: hdtb_id, DICHVUVT_ID: dichvuvt_id, LOAITB_ID: this.listbox.loaihinh_tb.value }) }).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          apidata = JSON.parse(response.data.data)
          if (apidata['RESULT']) apidata = apidata['RESULT']
          if (apidata['DS_HDTB']) ds = apidata['DS_HDTB']
          if (apidata['DS_TIEN_CAMKET']) dt = apidata['DS_TIEN_CAMKET']
        }
      })
      ds = this.LowerCasePropertyList(ds)
      dt = this.LowerCasePropertyList(dt)
      if (ds.length > 0) {

        switch (dichvuvt_id * 1) {
          case DichVuVienThong.ANTOAN_BAOMAT_TT:
          case DichVuVienThong.HOINGHI_TRUYENHINH:
          case DichVuVienThong.DICHVU_CNTT:
          case DichVuVienThong.TRUNGTAM_DULIEU:
            // #region Hiển thị thông tin ra
            // this.grcMacdinh.DataSource = (object)null;
            await this.Init_Properties(this.listbox.loaihinh_tb.value, 'HDTB_CNTT', '00000', this.ItemList) //Init_Properties(loaihinh_tb, "HDTB_CNTT", "00000", ref ItemList);

            this.gridEditors = []
            for (var index1 = 0; index1 < this.ItemList.length; ++index1) {
              if (!this.ItemList[index1]) continue
              if (this.ItemList[index1].sFieldName == 'MA_TB') this.ItemList[index1].sValue = this.ma_tb
              else this.ItemList[index1].sValue = ds[0][this.ItemList[index1].sFieldName.toLowerCase()] ? ds[0][this.ItemList[index1].sFieldName.toLowerCase()] : ds[0][this.ItemList[index1].sFieldName]

              if (this.ItemList[index1].iAtt == 0) {
                let repositoryItemTextEdit = { Type: 'TextEdit' }
                repositoryItemTextEdit.Tag = this.ItemList[index1].sFieldName
                repositoryItemTextEdit.ReadOnly = this.ItemList[index1].sReadOnly == 1
                repositoryItemTextEdit.EditValueChanged = this.repositoryItemTextEdit_EditValueChanged
                repositoryItemTextEdit.Leave = this.repositoryItemTextEdit_Leave // chua code nen comment tam lai

                repositoryItemTextEdit.DataType = this.ItemList[index1].iDataType
                repositoryItemTextEdit.MaxValue = this.ItemList[index1].sMaxValue
                repositoryItemTextEdit.MinValue = this.ItemList[index1].sMinValue
                repositoryItemTextEdit.Name = this.ItemList[index1].sCaption
                repositoryItemTextEdit.Value = repositoryItemTextEdit.DataType == 1 ? this.ItemList[index1].sValue * 1 : this.ItemList[index1].sValue
                repositoryItemTextEdit.Group = 'Tham số báo cáo'
                repositoryItemTextEdit.ID = this.ItemList[index1].sFieldName
                // repositoryItemTextEdit.RepositoryItem = repositoryItemTextEdit
                this.gridEditors.push(repositoryItemTextEdit)
                //this.gridEditors.push((RepositoryItem)repositoryItemTextEdit, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
              }
              if (this.ItemList[index1].iAtt == 1) {
                let repositoryItemCalcEdit = { Type: 'CalcEdit' }
                repositoryItemCalcEdit.MaxValue = this.ItemList[index1].sMaxValue
                repositoryItemCalcEdit.MinValue = this.ItemList[index1].sMinValue
                repositoryItemCalcEdit.Tag = this.ItemList[index1].sFieldName
                repositoryItemCalcEdit.ReadOnly = this.ItemList[index1].sReadOnly == 1
                repositoryItemCalcEdit.Name = this.ItemList[index1].sCaption
                repositoryItemCalcEdit.Group = 'Tham số báo cáo'
                repositoryItemCalcEdit.ID = this.ItemList[index1].sFieldName
                repositoryItemCalcEdit.Value = this.ItemList[index1].sValue
                // repositoryItemCalcEdit.RepositoryItem = repositoryItemCalcEdit
                this.gridEditors.push(repositoryItemCalcEdit)
                // repositoryItemCalcEdit.Tag = ItemList[index1].sFieldName;
                // repositoryItemCalcEdit.ReadOnly = (ItemList[index1].sReadOnly == 1);
                // this.gridEditors.Add((RepositoryItem)this.repositoryItemCalcEdit, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
              }
              if (this.ItemList[index1].iAtt == 2) {
                let repositoryItemDateEdit = { Type: 'DateEdit' }
                repositoryItemDateEdit.ReadOnly = this.ItemList[index1].sReadOnly == 1
                repositoryItemDateEdit.Tag = this.ItemList[index1].sFieldName
                repositoryItemDateEdit.FormatString = 'DD/MM/YYYY'
                repositoryItemDateEdit.EditMask = 'DD/MM/YYYY'
                repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged
                repositoryItemDateEdit.Name = this.ItemList[index1].sCaption
                repositoryItemDateEdit.Group = 'Tham số báo cáo'
                repositoryItemDateEdit.ID = this.ItemList[index1].sFieldName
                repositoryItemDateEdit.Value = moment(this.ItemList[index1].sValue).format('DD/MM/YYYY') //this.ItemList[index1].sValue;
                // repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit
                this.gridEditors.push(repositoryItemDateEdit)

                // RepositoryItemDateEdit repositoryItemDateEdit = new RepositoryItemDateEdit();
                // repositoryItemDateEdit.Tag = ItemList[index1].sFieldName;
                // repositoryItemDateEdit.ReadOnly = (ItemList[index1].sReadOnly == 1);
                // repositoryItemDateEdit.DisplayFormat.FormatString = "dd/MM/yyyy";
                // repositoryItemDateEdit.EditFormat.FormatString = "dd/MM/yyyy";
                // repositoryItemDateEdit.EditMask = "dd/MM/yyyy";
                // repositoryItemDateEdit.Mask.UseMaskAsDisplayFormat = true;
                // repositoryItemDateEdit.EditValueChanged += new System.EventHandler(this.repositoryItemDateEdit_EditValueChanged);
                // this.gridEditors.Add((RepositoryItem)repositoryItemDateEdit, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
              }

              if (this.ItemList[index1].iAtt == 9) {
                let repositoryItemDateEdit = { Type: 'DateEdit' }
                repositoryItemDateEdit.ReadOnly = this.ItemList[index1].sReadOnly == 1
                repositoryItemDateEdit.Tag = this.ItemList[index1].sFieldName
                repositoryItemDateEdit.FormatString = 'DD/MM/YYYY HH:mm:ss'
                repositoryItemDateEdit.EditMask = 'DD/MM/YYYY HH:mm:ss'
                repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged
                repositoryItemDateEdit.Name = this.ItemList[index1].sCaption
                repositoryItemDateEdit.Group = 'Tham số báo cáo'
                repositoryItemDateEdit.ID = this.ItemList[index1].sFieldName
                repositoryItemDateEdit.Value = moment(this.ItemList[index1].sValue).format('DD/MM/YYYY HH:mm:ss') //this.ItemList[index1].sValue;
                // repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit
                this.gridEditors.push(repositoryItemDateEdit)

                // RepositoryItemDateEdit repositoryItemDateEdit = new RepositoryItemDateEdit();
                // repositoryItemDateEdit.Tag = ItemList[index1].sFieldName;
                // repositoryItemDateEdit.ReadOnly = (ItemList[index1].sReadOnly == 1);
                // repositoryItemDateEdit.DisplayFormat.FormatString = "dd/MM/yyyy HH:mm:ss";
                // repositoryItemDateEdit.EditFormat.FormatString = "dd/MM/yyyy HH:mm:ss";
                // repositoryItemDateEdit.EditMask = "dd/MM/yyyy HH:mm:ss";
                // repositoryItemDateEdit.Mask.UseMaskAsDisplayFormat = true;
                // repositoryItemDateEdit.EditValueChanged += new System.EventHandler(this.repositoryItemDateEdit_EditValueChanged);
                // this.gridEditors.Add((RepositoryItem)repositoryItemDateEdit, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
              }
              //duong them
              if (this.ItemList[index1].iAtt == 10) {
                let repositoryItemCheckedComboBoxEdit1 = { Type: 'CheckedComboBoxEdit1' }
                repositoryItemCheckedComboBoxEdit1.AutoHeight = false
                repositoryItemCheckedComboBoxEdit1.Name = 'repositoryItemCheckedComboBoxEdit1'

                repositoryItemCheckedComboBoxEdit1.ReadOnly = this.ItemList[index1].sReadOnly == 1
                let SQL_CheckListBox = this.ItemList[index1].StrSql
                let data = this.ItemList[index1].SqlData
                repositoryItemCheckedComboBoxEdit1.ValueMember = this.ItemList[index1].sLookUpKeyField
                repositoryItemCheckedComboBoxEdit1.DisplayMember = this.ItemList[index1].sLookUpValueField
                repositoryItemCheckedComboBoxEdit1.DataSource = data

                repositoryItemCheckedComboBoxEdit1.Name = this.ItemList[index1].sCaption
                repositoryItemCheckedComboBoxEdit1.Group = 'Tham số báo cáo'
                repositoryItemCheckedComboBoxEdit1.ID = this.ItemList[index1].sFieldName
                repositoryItemCheckedComboBoxEdit1.Value = this.ItemList[index1].sValue ? this.ItemList[index1].sValue.split(',') : this.ItemList[index1].sValue

                // repositoryItemCheckedComboBoxEdit1.RepositoryItem = repositoryItemCheckedComboBoxEdit1
                this.gridEditors.push(repositoryItemCheckedComboBoxEdit1)

                // InitialItemCheckedRepository();

                // string SQL_CheckListBox = ItemList[index1].StrSql;
                // DataTable data = this.objCheckData.GET_DATA_SQL(SQL_CheckListBox, "");
                // repositoryItemCheckedComboBoxEdit1.ReadOnly = (ItemList[index1].sReadOnly == 1);
                // repositoryItemCheckedComboBoxEdit1.ValueMember = ItemList[index1].sLookUpKeyField;
                // repositoryItemCheckedComboBoxEdit1.DisplayMember = ItemList[index1].sLookUpValueField;
                // repositoryItemCheckedComboBoxEdit1.DataSource = (object)data;

                // this.gridEditors.Add((RepositoryItem)repositoryItemCheckedComboBoxEdit1, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
              }

              if (this.ItemList[index1].iAtt == 4) {
                let repositoryItemLookUpEdit = { Type: 'LookUpEdit' }
                repositoryItemLookUpEdit.ReadOnly = this.ItemList[index1].sReadOnly == 1
                repositoryItemLookUpEdit.Tag = this.ItemList[index1].sFieldName
                let SQL = this.ItemList[index1].StrSql
                if (SQL && SQL != '' && SQL.indexOf('{') < 0) {
                  let data = this.ItemList[index1].SqlData
                  if (!data || data.length <= 0) {
                    this.$toast.error('Không lấy được dữ liệu cho ' + repositoryItemLookUpEdit.Tag)

                    continue
                  }
                  repositoryItemLookUpEdit.ValueMember = this.ItemList[index1].sLookUpKeyField
                  repositoryItemLookUpEdit.DisplayMember = this.ItemList[index1].sLookUpValueField
                  if (this.DS_CHUQUAN_THEO_QUYEN != null && this.DS_CHUQUAN_THEO_QUYEN != '' && this.ItemList[index1].sLookUpKeyField.toUpperCase() == 'CHUQUAN_ID') {
                    repositoryItemLookUpEdit.DataSource = data.filter((x) => this.DS_CHUQUAN_THEO_QUYEN.indexOf(x.CHUQUAN_ID) >= 0) //(object)data.Tables[0].Select("CHUQUAN_ID in " + this.DS_CHUQUAN_THEO_QUYEN).CopyToDataTable();
                  }
                  // #region cuongpq them load lai cbo huyen ivan
                  else if (this.ItemList[index1].sLookUpKeyField && this.ItemList[index1].sLookUpKeyField.toUpperCase() == 'CAPDO_ID' && this.listbox.loaihinh_tb.value == LoaiHinhTB.IVAN) {
                    let tinh_ivan = await API.post_getivan_tinh(this.axios, { tinh_id: this.diachiTB.TINH_ID }).then((response) => {
                      //.post_sp_huonggiao_runsql(this.axios, { "command": this.ReplaceSQL(`select tinh_ivan_id from ${DatabaseConstants.DB2}.tinh where tinh_id = ${this.diachiTB.TINH_ID}`) }).then((response) => {
                      if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        return response.data.data //.data_json;
                      } else return []
                    }) //bangts.getDataFromSQL(ReplaceSQL("select tinh_ivan_id from {?DB2}.tinh where tinh_id = " + tinhtb_id), "tinh_ivan").Tables[0];
                    if (tinh_ivan.length > 0) {
                      if (tinh_ivan[0].tinh_ivan_id != null) {
                        let vtinh_ivan = tinh_ivan[0].tinh_ivan_id
                        repositoryItemLookUpEdit.DataSource = data.filter((x) => x.MAP_ID == vtinh_ivan) //(object)data.Tables[0].Select("MAP_ID = " + vtinh_ivan).CopyToDataTable();
                      } else throw 'Địa chỉ thuê bao tỉnh chưa có trên danh mục dịch vụ IVAN. Liên hệ admin để được hỗ trợ' //this.$toast.error("Địa chỉ thuê bao tỉnh chưa có trên danh mục dịch vụ IVAN. Liên hệ admin để được hỗ trợ");
                    } else throw 'Hãy chọn địa chỉ thuê bao' //this.$toast.error("Hãy chọn địa chỉ thuê bao");
                  }
                  // #endregion
                  else repositoryItemLookUpEdit.DataSource = data
                  repositoryItemLookUpEdit.NullText = ''
                } else {
                  SQL = await this.ReturnSqlStr(this.ItemList, index1)
                  let data = this.ItemList[index1].SqlData
                  if (!data || data.length <= 0) {
                    this.$toast.error('Không lấy được dữ liệu cho ' + repositoryItemLookUpEdit.Tag)

                    continue
                  }
                  repositoryItemLookUpEdit.ValueMember = this.ItemList[index1].sLookUpKeyField
                  repositoryItemLookUpEdit.DisplayMember = this.ItemList[index1].sLookUpValueField
                  if (this.DS_CHUQUAN_THEO_QUYEN != null && this.DS_CHUQUAN_THEO_QUYEN != '' && this.ItemList[index1].sLookUpKeyField.toUpperCase() == 'CHUQUAN_ID') {
                    repositoryItemLookUpEdit.DataSource = data.filter((x) => this.DS_CHUQUAN_THEO_QUYEN.indexOf(x.CHUQUAN_ID) >= 0) //(object)data.Tables[0].Select("CHUQUAN_ID in " + this.DS_CHUQUAN_THEO_QUYEN).CopyToDataTable();
                  } else repositoryItemLookUpEdit.DataSource = data //(object)data.Tables[0];

                  repositoryItemLookUpEdit.NullText = ''
                }
                repositoryItemLookUpEdit.EditValueChanged = this.repositoryItemLookUpEdit_EditValueChanged
                repositoryItemLookUpEdit.Leave = this.repositoryItemLookUpEdit_leave
                repositoryItemLookUpEdit.EditValueChanging = this.repositoryItemLookUpEdit_EditValueChanging
                //  this.gridEditors.Add((RepositoryItem)repositoryItemLookUpEdit, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
                repositoryItemLookUpEdit.Name = this.ItemList[index1].sCaption
                repositoryItemLookUpEdit.Group = 'Tham số báo cáo'
                repositoryItemLookUpEdit.ID = this.ItemList[index1].sFieldName
                repositoryItemLookUpEdit.Value = this.ItemList[index1].sValue
                if (!repositoryItemLookUpEdit.Value && repositoryItemLookUpEdit.DataSource && repositoryItemLookUpEdit.DataSource.length > 0) repositoryItemLookUpEdit.Value = repositoryItemLookUpEdit.DataSource[0][repositoryItemLookUpEdit.ValueMember]
                if (repositoryItemLookUpEdit.DataSource && repositoryItemLookUpEdit.DataSource.length > 0) {
                  repositoryItemLookUpEdit.DataSource = repositoryItemLookUpEdit.DataSource.map((x) => ({ text: x[this.ItemList[index1].sLookUpValueField], id: x[this.ItemList[index1].sLookUpKeyField] }))
                }
                // repositoryItemLookUpEdit.RepositoryItem = repositoryItemLookUpEdit
                this.gridEditors.push(repositoryItemLookUpEdit)
                //await this.repositoryItemLookUpEdit_EditValueChanged(repositoryItemLookUpEdit)
              }
              if (this.ItemList[index1].iAtt == 5) {
                //Button Edit
                let repositoryItemButtonEdit = { Type: 'ButtonEdit' }
                repositoryItemButtonEdit.ReadOnly = this.ItemList[index1].sReadOnly == 1
                repositoryItemButtonEdit.Tag = this.ItemList[index1].sFieldName
                repositoryItemButtonEdit.ReadOnly = this.ItemList[index1].sReadOnly == 1

                // repositoryItemButtonEdit.ButtonClick -= new DevExpress.XtraEditors.Controls.ButtonPressedEventHandler(this.repositoryItemButtonEdit_ButtonClick);
                repositoryItemButtonEdit.ButtonClick = this.repositoryItemButtonEdit_ButtonClick
                repositoryItemButtonEdit.Name = this.ItemList[index1].sCaption
                repositoryItemButtonEdit.Group = 'Tham số báo cáo'
                repositoryItemButtonEdit.ID = this.ItemList[index1].sFieldName
                repositoryItemButtonEdit.Value = this.ItemList[index1].sValue
                // repositoryItemButtonEdit.RepositoryItem = repositoryItemButtonEdit
                this.gridEditors.push(repositoryItemButtonEdit)
                // RepositoryItemButtonEdit repositoryItemButtonEdit = new RepositoryItemButtonEdit();
                // repositoryItemButtonEdit.Tag = ItemList[index1].sFieldName;
                // repositoryItemButtonEdit.ReadOnly = (ItemList[index1].sReadOnly == 1);
                // repositoryItemButtonEdit.ButtonClick -= new DevExpress.XtraEditors.Controls.ButtonPressedEventHandler(this.repositoryItemButtonEdit_ButtonClick);
                // repositoryItemButtonEdit.ButtonClick += new DevExpress.XtraEditors.Controls.ButtonPressedEventHandler(this.repositoryItemButtonEdit_ButtonClick);
                // this.gridEditors.Add((RepositoryItem)repositoryItemButtonEdit, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
              }
              if (this.ItemList[index1].iAtt == 7) {
                let repositoryItemCheckEdit = { Type: 'CheckEdit' }
                repositoryItemCheckEdit.Tag = this.ItemList[index1].sFieldName
                repositoryItemCheckEdit.ReadOnly = this.ItemList[index1].sReadOnly == 1
                repositoryItemCheckEdit.ValueChecked = 1
                repositoryItemCheckEdit.ValueUnchecked = 0
                repositoryItemCheckEdit.Name = this.ItemList[index1].sCaption
                repositoryItemCheckEdit.Group = 'Tham số báo cáo'
                repositoryItemCheckEdit.ID = this.ItemList[index1].sFieldName
                repositoryItemCheckEdit.Value = this.ItemList[index1].sValue
                // repositoryItemCheckEdit.RepositoryItem = repositoryItemCheckEdit
                this.gridEditors.push(repositoryItemCheckEdit)

                // repositoryItemCheckEdit.Tag = ItemList[index1].sFieldName;
                // repositoryItemCheckEdit.ReadOnly = (ItemList[index1].sReadOnly == 1);
                // this.repositoryItemCheckEdit.ValueChecked = (object)"1";
                // this.repositoryItemCheckEdit.ValueUnchecked = (object)"0";
                // this.gridEditors.Add((RepositoryItem)this.repositoryItemCheckEdit, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
              }
            }
            for (var index = 0; index < this.gridEditors.length; index++) {
              if (this.gridEditors[index].Type == 'LookUpEdit') {
                await this.repositoryItemLookUpEdit_EditValueChanged(this.gridEditors[index])
              }
              if (this.gridEditors[index].ID == 'MA_TB') {
                this.gridEditors[index].Value = this.ma_tb
                if (this.ItemList[index]) this.ItemList[index].sValue = this.ma_tb
              } else {
                if (this.gridEditors[index].ID == 'TOCDO_ID') {
                  if (this.ItemList[index]) {
                    //this.listbox.cboGoiCuocAdsl.value = this.ItemList[index].sValue
                    //await this.HT_GIAODIEN_DONVI(); // Them vao vi khong hieu sao no ko trigger su kien nay!
                  }
                }
                if (this.gridEditors[index].Type == 'DateEdit') {
                  //EditMask
                  this.gridEditors[index].Value = moment(ds[0][this.gridEditors[index].ID.toLowerCase()]).format(this.gridEditors[index].EditMask)
                  if (this.ItemList[index]) this.ItemList[index].sValue = moment(ds[0][this.gridEditors[index].ID.toLowerCase()]).format(this.gridEditors[index].EditMask)
                } else if (this.gridEditors[index].Type == 'CheckedComboBoxEdit1') {
                  this.gridEditors[index].Value = ds[0][this.gridEditors[index].ID.toLowerCase()] ? ds[0][this.gridEditors[index].ID.toLowerCase()].split(',') : ds[0][this.gridEditors[index].ID.toLowerCase()]
                  if (this.ItemList[index]) this.ItemList[index].sValue = ds[0][this.gridEditors[index].ID.toLowerCase()] + ''
                } else {
                  this.gridEditors[index].Value = ds[0][this.gridEditors[index].ID.toLowerCase()]
                  if (this.ItemList[index]) this.ItemList[index].sValue = ds[0][this.gridEditors[index].ID.toLowerCase()] + ''
                }
              }
            }
            // this.grcMacdinh.DataSource = (object)this.gridEditors;
            // this.grvMacdinh.BestFitColumns();
            //duongpd lay thong tin HD_THUEBAO_CT
            if (apidata['DS_HDTB_CT']) this.dsHDTB_CT = apidata['DS_HDTB_CT']
            //(tdan.GET_DATA_PROC("{?DB2}.LAY_DS_HD_THUEBAO_CT_COMMON", "vhdtb_id", hdtb_id)).DataTableToList<HD_THUEBAO_CT>();
            else this.dsHDTB_CT = []
            // #endregion
            break
          default:
            break
        }

        for (let i = 0; i < this.gridEditors.length; i++) {
          if (!(this.ItemList[i].sFieldName == "DUNGTHU"
            || this.ItemList[i].sFieldName == "THIETBI"
            || this.ItemList[i].sFieldName == "MA_DV"
            || this.ItemList[i].sFieldName == "DINHTUYEN")) {
          } else if (this.listbox.kieu_ld.value == 14052 || this.listbox.kieu_ld.value  == 14051 || this.listbox.kieu_ld.value == 793)/*đối tượng 1, 2*/ {
            this.gridEditors[i].show = false;
            // this.ItemList[i].hidden = true;
          }

        }
      }
      this.asyncLock = false;
    },
    async LoadThongTin_DV_CNTT(vloaitb_id) {
      try {
        await this.Init_Properties(vloaitb_id, 'HDTB_CNTT', '00000')
        let ItemList = this.ItemList;
        // let ItemList = await API.post_lay_danhsach_thuoctinh_datmoi(this.axios, { "ds_para": JSON.stringify(pdata) }).then(async (response) => {
        //   if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
        //     return response.data.data;
        //   }
        //   else return [];
        // });
        this.gridEditors = []
        for (var index1 = 0; index1 < ItemList.length; ++index1) {
          if (!ItemList[index1]) continue
          if (ItemList[index1].iAtt == 0) {
            let repositoryItemTextEdit = { Type: 'TextEdit' }
            repositoryItemTextEdit.Tag = ItemList[index1].sFieldName
            repositoryItemTextEdit.ReadOnly = ItemList[index1].sReadOnly == 1
            repositoryItemTextEdit.DataType = ItemList[index1].iDataType
            repositoryItemTextEdit.MaxValue = ItemList[index1].sMaxValue
            repositoryItemTextEdit.MinValue = ItemList[index1].sMinValue
            repositoryItemTextEdit.EditValueChanged = this.repositoryItemTextEdit_EditValueChanged
            repositoryItemTextEdit.EditValueChanging += this.repositoryItemTextEdit_EditValueChanging
            repositoryItemTextEdit.Leave += this.repositoryItemTextEdit_Leave

            if (CAConstanst.isCADev) {
              if (this.listbox.loaihinh_tb.value == 116) {
                if (ItemList[index1].sFieldName == 'SO_THANG') {
                  repositoryItemTextEdit.ReadOnly = true
                }
              }

              let default_serial_value = ItemList[index1].sValue
              if (ItemList[index1].sFieldName != 'SERIAL') {
                default_serial_value = ItemList[index1].sValue
              } else {
                default_serial_value = this.generateUUID()
              }
              repositoryItemTextEdit.Value = default_serial_value
            } else {
              repositoryItemTextEdit.Value = ItemList[index1].sValue
            }
            repositoryItemTextEdit.Name = ItemList[index1].sCaption
            repositoryItemTextEdit.Group = 'Tham số báo cáo'
            repositoryItemTextEdit.ID = ItemList[index1].sFieldName
            // repositoryItemTextEdit.RepositoryItem = repositoryItemTextEdit
            this.gridEditors.push(repositoryItemTextEdit)
          }
          if (ItemList[index1].iAtt == 1) {
            let repositoryItemCalcEdit = { Type: 'CalcEdit' }
            repositoryItemCalcEdit.MaxValue = ItemList[index1].sMaxValue
            repositoryItemCalcEdit.MinValue = ItemList[index1].sMinValue
            repositoryItemCalcEdit.Tag = ItemList[index1].sFieldName
            repositoryItemCalcEdit.ReadOnly = ItemList[index1].sReadOnly == 1
            repositoryItemCalcEdit.Name = ItemList[index1].sCaption
            repositoryItemCalcEdit.Group = 'Tham số báo cáo'
            repositoryItemCalcEdit.ID = ItemList[index1].sFieldName
            repositoryItemCalcEdit.Value = ItemList[index1].sValue
            // repositoryItemCalcEdit.RepositoryItem = repositoryItemCalcEdit
            this.gridEditors.push(repositoryItemCalcEdit)
            //this.gridEditors.Add((RepositoryItem)this.repositoryItemCalcEdit, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
          }
          if (ItemList[index1].iAtt == 2) {
            ItemList[index1].sValue = moment(new Date()).format('DD/MM/YYYY')
            let repositoryItemDateEdit = { Type: 'DateEdit' }
            repositoryItemDateEdit.ReadOnly = ItemList[index1].sReadOnly == 1
            repositoryItemDateEdit.Tag = ItemList[index1].sFieldName
            ItemList[index1].sValue = moment(new Date()).format('DD/MM/YYYY')
            // repositoryItemDateEdit.DisplayFormat.FormatString = "DD/MM/YYYY";
            // repositoryItemDateEdit.EditFormat.FormatString = "DD/MM/YYYY";
            repositoryItemDateEdit.FormatString = 'DD/MM/YYYY'
            repositoryItemDateEdit.EditMask = 'DD/MM/YYYY'
            // repositoryItemDateEdit.Mask.UseMaskAsDisplayFormat = true;
            repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged
            repositoryItemDateEdit.Name = ItemList[index1].sCaption
            repositoryItemDateEdit.Group = 'Tham số báo cáo'
            repositoryItemDateEdit.ID = ItemList[index1].sFieldName
            repositoryItemDateEdit.Value = moment(new Date()).format('DD/MM/YYYY')
            // repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit
            this.gridEditors.push(repositoryItemDateEdit)
            // this.gridEditors.Add((RepositoryItem)repositoryItemDateEdit, ItemList[index1].sCaption, DateTime.Now, "Tham số báo cáo", ItemList[index1].sFieldName);
          }

          if (ItemList[index1].iAtt == 9) {
            ItemList[index1].sValue = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            let repositoryItemDateEdit = { Type: 'DateEdit' }
            repositoryItemDateEdit.ReadOnly = ItemList[index1].sReadOnly == 1
            repositoryItemDateEdit.Tag = ItemList[index1].sFieldName
            ItemList[index1].sValue = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            // repositoryItemDateEdit.DisplayFormat.FormatString = "DD/MM/YYYY HH:mm:ss";
            // repositoryItemDateEdit.EditFormat.FormatString = "DD/MM/YYYY HH:mm:ss";
            repositoryItemDateEdit.FormatString = 'DD/MM/YYYY HH:mm:ss'
            repositoryItemDateEdit.EditMask = 'DD/MM/YYYY HH:mm:ss'
            // repositoryItemDateEdit.Mask.UseMaskAsDisplayFormat = true;
            repositoryItemDateEdit.EditValueChanged = this.repositoryItemDateEdit_EditValueChanged
            repositoryItemDateEdit.Name = ItemList[index1].sCaption
            repositoryItemDateEdit.Group = 'Tham số báo cáo'
            repositoryItemDateEdit.ID = ItemList[index1].sFieldName
            repositoryItemDateEdit.Value = moment(new Date()).format('DD/MM/YYYY')
            // repositoryItemDateEdit.RepositoryItem = repositoryItemDateEdit
            this.gridEditors.push(repositoryItemDateEdit)
            // this.gridEditors.Add((RepositoryItem)repositoryItemDateEdit, ItemList[index1].sCaption, DateTime.Now, "Tham số báo cáo", ItemList[index1].sFieldName);
          }

          // let str1;
          // let dataSet;
          if (ItemList[index1].iAtt == 4) {
            let repositoryItemLookUpEdit = { Type: 'LookUpEdit' }
            repositoryItemLookUpEdit.ReadOnly = ItemList[index1].sReadOnly == 1
            // repositoryItemLookUpEdit.View.OptionsView.ShowAutoFilterRow = true;
            repositoryItemLookUpEdit.Tag = ItemList[index1].sFieldName
            // str1 = "";
            let SQL = ItemList[index1].StrSql
            if (SQL && SQL != '' && SQL.indexOf('{') < 0) {
              // dataSet = new DataSet();
              let data = ItemList[index1].SqlData
              if (!data || data.length <= 0)
              {
                this.$toast.error('Không lấy được dữ liệu cho ' + repositoryItemLookUpEdit.Tag)

              }
              // let data = await API.post_sp_huonggiao_runsql(this.axios, { "command": SQL }).then((response) => {
              //   if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              //     return response.data.data.data_json;
              //   }
              //   else return [];
              // }).catch(err=> { throw err.data.message+': '+err.data.message_detail; }); //this.bangts.GetSQL(SQL, "");
              repositoryItemLookUpEdit.ValueMember = ItemList[index1].sLookUpKeyField
              repositoryItemLookUpEdit.DisplayMember = ItemList[index1].sLookUpValueField
              if (this.DS_CHUQUAN_THEO_QUYEN != null && this.DS_CHUQUAN_THEO_QUYEN != '' && ItemList[index1].sLookUpKeyField.toUpperCase() == 'CHUQUAN_ID') {
                repositoryItemLookUpEdit.DataSource = data.filter((x) => this.DS_CHUQUAN_THEO_QUYEN.indexOf(x.CHUQUAN_ID) >= 0) //(object)data.Tables[0].Select("CHUQUAN_ID in " + this.DS_CHUQUAN_THEO_QUYEN).CopyToDataTable();
              }
              // #region cuongpq them load lai cbo huyen ivan
              else if (ItemList[index1].sLookUpKeyField && ItemList[index1].sLookUpKeyField.toUpperCase() == 'CAPDO_ID' && this.listbox.loaihinh_tb.value == LoaiHinhTB.IVAN) {
                let tinh_ivan = await API.post_getivan_tinh(this.axios, { tinh_id: this.diachiTB.TINH_ID }).then((response) => {
                  //.post_sp_huonggiao_runsql(this.axios, { "command": this.ReplaceSQL(`select tinh_ivan_id from ${DatabaseConstants.DB2}.tinh where tinh_id = ${this.diachiTB.TINH_ID}`) }).then((response) => {
                  if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    return response.data.data //.data_json;
                  } else return []
                }) //bangts.getDataFromSQL(ReplaceSQL("select tinh_ivan_id from {?DB2}.tinh where tinh_id = " + tinhtb_id), "tinh_ivan").Tables[0];
                if (tinh_ivan.length > 0) {
                  if (tinh_ivan[0].tinh_ivan_id != null) {
                    let vtinh_ivan = tinh_ivan[0].tinh_ivan_id
                    repositoryItemLookUpEdit.DataSource = data.filter((x) => x.MAP_ID == vtinh_ivan) //(object)data.Tables[0].Select("MAP_ID = " + vtinh_ivan).CopyToDataTable();
                  } else throw 'Địa chỉ thuê bao tỉnh chưa có trên danh mục dịch vụ IVAN. Liên hệ admin để được hỗ trợ' //this.$toast.error("Địa chỉ thuê bao tỉnh chưa có trên danh mục dịch vụ IVAN. Liên hệ admin để được hỗ trợ");
                } else this.$toast.error('Hãy chọn địa chỉ thuê bao') //this.$toast.error("Hãy chọn địa chỉ thuê bao");
              }
              // #endregion
              else repositoryItemLookUpEdit.DataSource = data
              // repositoryItemLookUpEdit.View.OptionsView.ShowColumnHeaders = false;
              //if (repositoryItemLookUpEdit.View.Columns.Count > 1)
              // if(Object.keys(data).length > 1) //Doan nay khong can vi combobox da hoat dong dung nhu the nay roi!!!
              // {
              //     for (var ic = 0; ic < Object.keys(data).length; ic++)
              //     {
              //         repositoryItemLookUpEdit.View.Columns[ic].Visible = false;
              //     }
              //     repositoryItemLookUpEdit.View.Columns[1].Visible = true;
              // }
              repositoryItemLookUpEdit.NullText = ''
            } else {
              SQL = await this.ReturnSqlStr(ItemList, index1)
              // dataSet = new DataSet();
              // DataSet data = this.bangts.GetSQL(SQL, "");
              let data = ItemList[index1].SqlData
              if (!data || data.length <= 0){
                this.$toast.error('Không lấy được dữ liệu cho ' + repositoryItemLookUpEdit.Tag)
                console.log(ItemList[index1]);
              }
              // let data = await API.post_sp_huonggiao_runsql(this.axios, { "command": SQL }).then(async (response) => {
              //   if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              //     return response.data.data.data_json;
              //   }
              //   else return [];
              // }).catch(err=> { throw err.data.message+': '+err.data.message_detail; });;
              repositoryItemLookUpEdit.ValueMember = ItemList[index1].sLookUpKeyField
              repositoryItemLookUpEdit.DisplayMember = ItemList[index1].sLookUpValueField
              if (this.DS_CHUQUAN_THEO_QUYEN != null && this.DS_CHUQUAN_THEO_QUYEN != '' && ItemList[index1].sLookUpKeyField.toUpperCase() == 'CHUQUAN_ID') {
                repositoryItemLookUpEdit.DataSource = data.filter((x) => this.DS_CHUQUAN_THEO_QUYEN.indexOf(x.CHUQUAN_ID) >= 0) //(object)data.Tables[0].Select("CHUQUAN_ID in " + this.DS_CHUQUAN_THEO_QUYEN).CopyToDataTable();
              } else repositoryItemLookUpEdit.DataSource = data //(object)data.Tables[0];
              // repositoryItemLookUpEdit.View.OptionsView.ShowColumnHeaders = false;
              // if (Object.keys(data).length > 1)
              // {
              //     for (var ic = 0; ic < Object.keys(data).length; ic++)
              //     {
              //         repositoryItemLookUpEdit.View.Columns[ic].Visible = false;
              //     }
              //     repositoryItemLookUpEdit.View.Columns[1].Visible = true;
              // }

              repositoryItemLookUpEdit.NullText = ''
            }
            repositoryItemLookUpEdit.EditValueChanged = this.repositoryItemLookUpEdit_EditValueChanged
            repositoryItemLookUpEdit.Leave = this.repositoryItemLookUpEdit_leave
            repositoryItemLookUpEdit.EditValueChanging = this.repositoryItemLookUpEdit_EditValueChanging
            //  this.gridEditors.Add((RepositoryItem)repositoryItemLookUpEdit, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
            repositoryItemLookUpEdit.Name = ItemList[index1].sCaption
            repositoryItemLookUpEdit.Group = 'Tham số báo cáo'
            repositoryItemLookUpEdit.ID = ItemList[index1].sFieldName
            repositoryItemLookUpEdit.Value = ItemList[index1].sValue
            if (!repositoryItemLookUpEdit.Value && repositoryItemLookUpEdit.DataSource && repositoryItemLookUpEdit.DataSource.length > 0) repositoryItemLookUpEdit.Value = repositoryItemLookUpEdit.DataSource[0][repositoryItemLookUpEdit.ValueMember]
            if (repositoryItemLookUpEdit.DataSource && repositoryItemLookUpEdit.DataSource.length > 0) {
              repositoryItemLookUpEdit.DataSource = repositoryItemLookUpEdit.DataSource.map((x) => ({ text: x[ItemList[index1].sLookUpValueField], id: x[ItemList[index1].sLookUpKeyField] }))
            }
            // repositoryItemLookUpEdit.RepositoryItem = repositoryItemLookUpEdit
            this.gridEditors.push(repositoryItemLookUpEdit)
            // await this.repositoryItemLookUpEdit_EditValueChanged(repositoryItemLookUpEdit)
          }
          if (ItemList[index1].iAtt == 5) {
            //Button Edit
            let repositoryItemButtonEdit = { Type: 'ButtonEdit' }
            repositoryItemButtonEdit.ReadOnly = ItemList[index1].sReadOnly == 1
            repositoryItemButtonEdit.Tag = ItemList[index1].sFieldName
            if (ItemList[index1].sFieldName == 'MATB_TN') {
              repositoryItemButtonEdit.ReadOnly = true
            }

            // repositoryItemButtonEdit.ButtonClick -= new DevExpress.XtraEditors.Controls.ButtonPressedEventHandler(this.repositoryItemButtonEdit_ButtonClick);
            repositoryItemButtonEdit.ButtonClick = this.repositoryItemButtonEdit_ButtonClick
            repositoryItemButtonEdit.Name = ItemList[index1].sCaption
            repositoryItemButtonEdit.Group = 'Tham số báo cáo'
            repositoryItemButtonEdit.ID = ItemList[index1].sFieldName
            repositoryItemButtonEdit.Value = ItemList[index1].sValue
            // repositoryItemButtonEdit.RepositoryItem = repositoryItemButtonEdit
            this.gridEditors.push(repositoryItemButtonEdit)
            // this.gridEditors.Add((RepositoryItem)repositoryItemButtonEdit, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
          }
          if (ItemList[index1].iAtt == 7) {
            let repositoryItemCheckEdit = { Type: 'CheckEdit' }
            repositoryItemCheckEdit.Tag = ItemList[index1].sFieldName
            repositoryItemCheckEdit.ReadOnly = ItemList[index1].sReadOnly == 1
            repositoryItemCheckEdit.ValueChecked = 1
            repositoryItemCheckEdit.ValueUnchecked = 0
            repositoryItemCheckEdit.Name = ItemList[index1].sCaption
            repositoryItemCheckEdit.Group = 'Tham số báo cáo'
            repositoryItemCheckEdit.ID = ItemList[index1].sFieldName
            repositoryItemCheckEdit.Value = ItemList[index1].sValue
            // repositoryItemCheckEdit.RepositoryItem = repositoryItemCheckEdit
            this.gridEditors.push(repositoryItemCheckEdit)
            // this.gridEditors.Add((RepositoryItem)this.repositoryItemCheckEdit, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
          }
          if (ItemList[index1].iAtt == 10) {
            //InitialItemCheckedRepository();// Thay bang doan code ben duoi
            let repositoryItemCheckedComboBoxEdit1 = { Type: 'CheckedComboBoxEdit1' }
            repositoryItemCheckedComboBoxEdit1.AutoHeight = false
            repositoryItemCheckedComboBoxEdit1.Name = 'repositoryItemCheckedComboBoxEdit1'

            repositoryItemCheckedComboBoxEdit1.ReadOnly = ItemList[index1].sReadOnly == 1
            let SQL_CheckListBox = ItemList[index1].StrSql
            let data = ItemList[index1].SqlData
            // let data = await API.post_sp_huonggiao_runsql(this.axios, { "command": SQL_CheckListBox }).then(async (response) => {
            //     if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            //       return response.data.data.data_json;
            //     }
            //     else return [];
            //   });//this.objCheckData.GET_DATA_SQL(SQL_CheckListBox, "");
            repositoryItemCheckedComboBoxEdit1.ValueMember = ItemList[index1].sLookUpKeyField
            repositoryItemCheckedComboBoxEdit1.DisplayMember = ItemList[index1].sLookUpValueField
            repositoryItemCheckedComboBoxEdit1.DataSource = data

            repositoryItemCheckedComboBoxEdit1.Name = ItemList[index1].sCaption
            repositoryItemCheckedComboBoxEdit1.Group = 'Tham số báo cáo'
            repositoryItemCheckedComboBoxEdit1.ID = ItemList[index1].sFieldName
            repositoryItemCheckedComboBoxEdit1.Value = ItemList[index1].sValue ? ItemList[index1].sValue.split(',') : ItemList[index1].sValue
            // repositoryItemCheckedComboBoxEdit1.RepositoryItem = repositoryItemCheckedComboBoxEdit1
            this.gridEditors.push(repositoryItemCheckedComboBoxEdit1)

            // this.gridEditors.Add((RepositoryItem)repositoryItemCheckedComboBoxEdit1, ItemList[index1].sCaption, (object)ItemList[index1].sValue, "Tham số báo cáo", ItemList[index1].sFieldName);
          }
        }
        for (let index = 0; index < this.gridEditors.length; index++)
          if (this.gridEditors[index].Type == 'LookUpEdit') {
            await this.repositoryItemLookUpEdit_EditValueChanged(this.gridEditors[index])
          }

        // let source_hotline = "";
        // for (var i = ItemList.length - 1; i >= 0; i--) {
        //   if (ItemList[i].sFieldName == "DONVIKG_ID") {
        //     source_hotline = this.gridEditors[i].Value;
        //   }
        // }
        //region cuongpq them code an thuoc tinh cntt
        for (let i = 0; i < this.gridEditors.length; i++) {
          if (!(this.ItemList[i].sFieldName == "DUNGTHU"
            || this.ItemList[i].sFieldName == "THIETBI"
            || this.ItemList[i].sFieldName == "MA_DV"
            || this.ItemList[i].sFieldName == "DINHTUYEN")) {
          } else if (this.listbox.kieu_ld.value == 14052 || this.listbox.kieu_ld.value == 14051 ||
            (this.listbox.kieu_ld.value == 793 ) )/*đối tượng 1, 2*/ {
            this.gridEditors[i].show = false;
            // this.ItemList[i].hidden = true;
          }

        }     //end region
        //
        // this.grcMacdinh.DataSource = this.gridEditors;
      } catch (ex) {
        this.$toast.error('' + ex)
        console.log(ex)
      }
    },
    async repositoryItemLookUpEdit_EditValueChanged(repositoryItemLookUpEdit) {
      if (!(await this.repositoryItemLookUpEdit_EditValueChanging(repositoryItemLookUpEdit))) return
      //Neu la linhvuc_id thi reset lai thong tin dich vu gia tang dang ky (Neu co) Do DVGT phu thuoc linh vuc
      try {
        let ItemList = this.ItemList
        // RepositoryItemGridLookUpEdit repositoryItemLookUpEdit = !(sender is GridLookUpEdit) ? sender as RepositoryItemGridLookUpEdit : (sender as GridLookUpEdit).Properties;
        this.PhanLoai_ID = ',' + repositoryItemLookUpEdit.Value + ',' //PhanLoai_ID = "," + ((GridLookUpEdit)sender).EditValue.ToString() + ",";
        //if (repositoryItemLookUpEdit.ValueMember == "LINHVUC_ID")
        if (repositoryItemLookUpEdit.ID == 'LINHVUC_ID') {
          //FIXME can test ky lai cho nay, ValueMember co kha nang ~ ID, neu khong phai tim cach khac xu ly!!!
          await this.LAY_DS_DVGT_MACDINH(this.hdtb_id, this.listbox.loaihinh_tb.value)
        }
        if (repositoryItemLookUpEdit.ID == 'THOIHAN_ID') {
          //FIXME can test ky lai cho nay, ValueMember co kha nang ~ ID, neu khong phai tim cach khac xu ly!!!
          let thoihan = repositoryItemLookUpEdit.Value //((GridLookUpEdit)sender).EditValue.ToString();
          if (thoihan == '2') {
            for (var ii = 0; ii < this.ItemList.length; ii++) {
              if (!this.gridEditors[ii] || !this.ItemList[ii]) continue
              if (this.ItemList[ii].sFieldName == 'SO_THANG' && (!this.ItemList[ii].sValue || this.ItemList[ii].sValue <= 0)) {
                this.ItemList[ii].sValue = '1200'
                this.gridEditors[ii].Value = 1200
              }
              if (this.ItemList[ii].sFieldName == 'NGAY_KT' && !this.ItemList[ii].sValue) {
                let ngaycn = new Date()
                ngaycn = new Date(ngaycn.setMonth(ngaycn.getMonth() + 1200))
                this.ItemList[ii].sValue = moment(ngaycn).format('DD/MM/YYYY')
                this.gridEditors[ii].Value = moment(ngaycn).format('DD/MM/YYYY')
              }
            }
          } else {
            for (var ii = 0; ii < this.ItemList.length; ii++) {
              if (this.ItemList[ii].sFieldName == 'SO_THANG' && (!this.ItemList[ii].sValue || this.ItemList[ii].sValue <= 0)) {
                this.ItemList[ii].sValue = '0'
                this.gridEditors[ii].Value = 0
              }
              if (this.ItemList[ii].sFieldName == 'NGAY_KT' && !this.ItemList[ii].sValue) {
                let ngaycn = new Date()
                this.ItemList[ii].sValue = moment(ngaycn).format('DD/MM/YYYY')
                this.gridEditors[ii].Value = moment(ngaycn).format('DD/MM/YYYY')
                // this.ItemList[ii].sValue = tt_nd.ngay_cn.ToString("dd/MM/yyyy");
                // this.gridEditors[ii].Value = tt_nd.ngay_cn;
              }
            }
          }
        }
        if (repositoryItemLookUpEdit.ID == 'PHANLOAI_ID' || (repositoryItemLookUpEdit.ID == 'LOAICTS_ID' && this.listbox.loaihinh_tb.value == LoaiHinhTB.SMART_CA)) {
          ////FIXME can test ky lai cho nay, ValueMember co kha nang ~ ID, neu khong phai tim cach khac xu ly!!!
          //Nếu dịch vụ là 5002 thì lấy lại thông tin combo box tốc độ
          for (var i = 0; i < ItemList.length; i++) {
            if (this.listbox.loaihinh_tb.value == LoaiHinhTB.BRANDNAME && repositoryItemLookUpEdit.Value && repositoryItemLookUpEdit.Value != '0')
              if (ItemList[i].sFieldName.indexOf('LINHVUC_') == 0 && ItemList[i].sFieldName.indexOf('_ID') == ItemList[i].sFieldName.length - 3)
                if (ItemList[i].SqlData && ItemList[i].SqlData.length > 0) {
                  let newds = ItemList[i].SqlData.filter((x) => x.PHANLOAI_ID == repositoryItemLookUpEdit.Value)
                  if (newds && newds.length > 0) {
                    newds.unshift({ LINHVUC_ID: 0, LABEL_TYPE_NAME: '', PHANLOAI_ID: 0 })
                    this.gridEditors[i].DataSource = newds.map((x) => ({ text: x[ItemList[i].sLookUpValueField], id: x[ItemList[i].sLookUpKeyField] }))
                    if (!this.gridEditors[i].DataSource.find((x) => x.id == this.gridEditors[i].Value)) {
                      this.gridEditors[i].Value = this.gridEditors[i].DataSource[0].id
                      ItemList[i].sValue = this.gridEditors[i].DataSource[0].id
                      ItemList[i].Value = this.gridEditors[i].DataSource[0].id
                    }
                  } else {
                    this.gridEditors[i].DataSource = [{ text: 'Không áp dụng', id: 0 }]
                    this.gridEditors[i].Value = 0
                    ItemList[i].sValue = 0
                    ItemList[i].Value = 0
                  }
                }
            if (ItemList[i].sFieldName == 'PHANLOAI_ID' || ItemList[i].sFieldName == 'LOAICTS_ID') {
              ItemList[i].sValue = repositoryItemLookUpEdit.Value
            }
            if (ItemList[i].strDepandField == 'DAILY' || (ItemList[i].sFieldName == 'LINHVUC_ID' && this.listbox.loaihinh_tb.value != LoaiHinhTB.BRANDNAME)) {
              let SQL = await this.ReturnSqlStr(ItemList, i)
              let data = ItemList[i].SqlData
              if (this.gridEditors[i]) this.gridEditors[i].DataSource = data.map((x) => ({ text: x[ItemList[i].sLookUpValueField], id: x[ItemList[i].sLookUpKeyField] }))
              // await API.post_sp_huonggiao_runsql(this.axios, { "command": SQL }).then(async (response) => {
              //   if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              //     return response.data.data.data_json;
              //   }
              //   else return [];
              // });//this.bangts.GetSQL(SQL, "");
              //((RepositoryItemGridLookUpEdit)gridEditors[i].RepositoryItem).DataSource = (object)data.Tables[0];
              // break;
            }
            if (this.listbox.loaihinh_tb.value == LoaiHinhTB.ELEARNING) {
              let phanloai_id_elearning = repositoryItemLookUpEdit.Value //((GridLookUpEdit)sender).EditValue.ToString();
              if (phanloai_id_elearning == '97') {
                // gói doanh nghiệp
                for (var ii = 0; ii < ItemList.length; ii++) {
                  if (ItemList[ii].sFieldName == 'TONGSO_ND') {
                    ItemList[ii].sValue = '0'
                    this.gridEditors[ii].Value = '0'
                  }
                  if (ItemList[ii].sFieldName == 'SOLUONG') {
                    ItemList[ii].sValue = '0'
                    this.gridEditors[ii].Value = '0'
                  }
                }
              }
            }
          }
        }
        if (repositoryItemLookUpEdit.ID == 'TOCDO_ID') {
          console.log(1, this.listbox.loaihinh_tb_moi.value);
          if(this.listbox.loaihinh_tb_moi.value == 2117)  {
            const goi_vcc = repositoryItemLookUpEdit.Value;
            let enable_UserNumber = false;
            let value_UserNumber = 0;
            let enable_ExtNumber = false;
            let value_ExtNumber = false;
            let enable_AppNumber = false;
            let value_AppNumber = 0;
            if(goi_vcc == 44704 /*VCC_CALL*/ ) {
              enable_UserNumber = false;
              value_UserNumber = 0;
              enable_ExtNumber = true;
              value_ExtNumber = 0;
              enable_AppNumber = false;
              value_AppNumber = 0;
            }
            else if(goi_vcc == 44705 /*VCC_CALLCENTER*/)  {
              enable_UserNumber = true;
              value_UserNumber = 1;
              enable_ExtNumber = true;
              value_ExtNumber = 0;
              enable_AppNumber = false;
              value_AppNumber = 0;
            }
            else if(goi_vcc == 44706 /*VCC_CONTACT_CENTER*/)  {
              enable_UserNumber = true;
              value_UserNumber = 1;
              enable_ExtNumber = true;
              value_ExtNumber = 0;
              enable_AppNumber = false;
              value_AppNumber = 0;
            }
            else if(goi_vcc == 44707 /*VCC_DEMO*/)  {
              enable_UserNumber = false;
              value_UserNumber = 1;
              enable_ExtNumber = false;
              value_ExtNumber = 3;
              enable_AppNumber = false;
              value_AppNumber = 0;
            }
            else if(goi_vcc == 44708 /*VCC_SIPCONNECT*/)  {
              enable_UserNumber = false;
              value_UserNumber = 0;
              enable_ExtNumber = false;
              value_ExtNumber = 0;
              enable_AppNumber = false;
              value_AppNumber = 0;
            }
            for (var i = 0; i < ItemList.length; i++) {
              if (ItemList[i].sFieldName == 'SOLUONG') {
                this.gridEditors[i].ReadOnly = !enable_UserNumber;
                this.gridEditors[i].Value = value_UserNumber;
                ItemList[i].sValue = value_UserNumber;
              }
              if (ItemList[i].sFieldName == 'SL_MAYLE') {
                this.gridEditors[i].ReadOnly = !enable_ExtNumber;
                this.gridEditors[i].Value = value_ExtNumber;
                ItemList[i].sValue = value_ExtNumber;
              }
              if (ItemList[i].sFieldName == 'SL_MAILING') {
                this.gridEditors[i].ReadOnly = !enable_AppNumber;
                this.gridEditors[i].Value = value_AppNumber;
                ItemList[i].sValue = value_AppNumber;
              }
              if(ItemList[i].sFieldName == 'DATA' ||
                  ItemList[i].sFieldName == 'UPS' ||
                  ItemList[i].sFieldName == 'MOC_INT' ||
                  ItemList[i].sFieldName == 'SMT' ||
                  ItemList[i].sFieldName == 'MTC' ||
                  ItemList[i].sFieldName == 'MOC_LOCAL' )
              {
                const data_item = (await API.post_lay_ds_dl_nghiepvu(this.axios,
                { p_ds_para: `{"TOCDO_ID": ${goi_vcc}, "FIELD_NAME": "${ItemList[i].sFieldName}"}`, p_nghiepvu: 'LAY_DS_CHIETKHAU_VCC' })).data.data;
                console.log('✅ data', data_item)
                const data = data_item.map((x) => ({ text: x.display, id: x.value }))
                this.gridEditors[i].DataSource = data;
                ItemList[i].SqlData = data;

              }
              console.log(ItemList);
            }
          }
          //FIXME can test ky lai cho nay, ValueMember co kha nang ~ ID, neu khong phai tim cach khac xu ly!!!
          if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.TTDL_HDDT) {
            // VNPT Invoice
            var invoiceTocdo_id = repositoryItemLookUpEdit.Value
            API.get_tocdo_adsl(this.axios, {})
              .then(async (response) => {
                //.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.TUDONG_TINHSOTHANG_SD_THEO_GOICUOC(${this.listbox.loaihinh_tb_moi.value}) kq from dual` }).then(async (response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  let datalist = response.data.data //.data_json;
                  if (!datalist) datalist = []
                  let currentData = datalist.find((x) => x.tocdo_id == invoiceTocdo_id)
                  for (var i = 0; i < ItemList.length; i++)
                    if (ItemList[i].sFieldName == 'SL_MAILING') {
                      if (currentData && currentData.ca_key_length && currentData.ca_key_length > 0) {
                        ItemList[i].sValue = currentData.ca_key_length + ''
                        ItemList[i].Value = currentData.ca_key_length
                        this.gridEditors[i].Value = currentData.ca_key_length
                      } else {
                        ItemList[i].sValue = ''
                        ItemList[i].Value = ''
                        this.gridEditors[i].Value = ''
                      }
                    }
                }
              })
              .catch(() => {})
          }
          let vtocdo_id = 0
          let vmuccuoc_id = 0
          for (var i = 0; i < ItemList.length; i++) {
            if (ItemList[i].sFieldName == 'TOCDO_ID') {
              ItemList[i].sValue = repositoryItemLookUpEdit.Value //((GridLookUpEdit)sender).EditValue.ToString();
              vtocdo_id = ItemList[i].sValue

              // this.tocdo_id = vtocdo_id
              // this.listbox.cboGoiCuocAdsl.value = vtocdo_id
              // await this.HT_GIAODIEN_DONVI();
              //await this.HT_DS_KhuyenMai_Combobox()
              try {
                let checkTD = await API.post_tudong_tinhsothang_sd_theo_goicuoc(this.axios, { vloaitb_id: this.listbox.loaihinh_tb_moi.value }).then(async (response) => {
                  //.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.TUDONG_TINHSOTHANG_SD_THEO_GOICUOC(${this.listbox.loaihinh_tb_moi.value}) kq from dual` }).then(async (response) => {
                  if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    return response.data.data //.data_json;
                  } else return []
                })
                // if (tdan.GET_VALUE_FUNC("{?DB2}.TUDONG_TINHSOTHANG_SD_THEO_GOICUOC", "vloaitb_id", this.listbox.loaihinh_tb_moi.value).ToString() == "ok")
                // if (checkTD && checkTD.length>0 && checkTD[0].kq && checkTD[0].kq == "ok")
                //if (checkTD && checkTD.length>0 && checkTD[0].kq && checkTD[0].kq == "ok")
                if (checkTD && checkTD == 'ok') {
                  let dt_tocdo_goicuoc = await API.post_dt_tocdo_goicuoc(this.axios, { id: vtocdo_id }).then(async (response) => {
                    //.post_sp_huonggiao_runsql(this.axios, { "command": `Select sothang_sd, rc_id from ${DatabaseConstants.DB2}.tocdo_adsl where tocdo_id = ${vtocdo_id}` }).then(async (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                      return response.data.data //.data_json;
                    } else return []
                  }) //objCheckData.GET_DATA_SQL(@"Select sothang_sd, rc_id from {?DB2}.tocdo_adsl where tocdo_id = :tocdo_id", "tocdo_id", vtocdo_id);
                  let so_thang_sd = dt_tocdo_goicuoc[0]['sothang_sd']
                  var index_so_thang = -1
                  var index_ngay_bd = -1
                  var index_ngay_kt = -1
                  for (var index_1 = 0; index_1 < ItemList.length; ++index_1) {
                    if (ItemList[index_1].sFieldName == 'SO_THANG') {
                      index_so_thang = index_1
                    }
                    if (ItemList[index_1].sFieldName == 'NGAY_BD') {
                      index_ngay_bd = index_1
                    }
                    if (ItemList[index_1].sFieldName == 'NGAY_KT') {
                      index_ngay_kt = index_1
                    }
                  }
                  try {
                    if (!(index_so_thang == -1 || index_ngay_bd == -1 || index_ngay_kt == -1)) {
                      if (this.gridEditors[index_so_thang]) this.gridEditors[index_so_thang].Value = so_thang_sd
                      ItemList[index_so_thang].sValue = so_thang_sd
                      if (!ItemList[index_ngay_bd].sValue && this.gridEditors[index_ngay_bd] && this.gridEditors[index_ngay_bd].Value) ItemList[index_ngay_bd].sValue = this.gridEditors[index_ngay_bd].Value
                      let tmpdate = moment(ItemList[index_ngay_bd].sValue, 'DD/MM/YYYY').toDate()
                      tmpdate = new Date(tmpdate.setMonth(tmpdate.getMonth() + so_thang_sd))
                      let ngay_kt_orm = moment(tmpdate).format('DD/MM/YYYY') //(DateTime.ParseExact(ItemList[index_ngay_bd].sValue, "dd/MM/yyyy", CultureInfo.InvariantCulture).AddMonths(Convert.ToInt32(so_thang_sd))).ToString("dd/MM/yyyy");
                      if (this.gridEditors[index_ngay_kt]) this.gridEditors[index_ngay_kt].Value = ngay_kt_orm
                      ItemList[index_ngay_kt].sValue = ngay_kt_orm
                    }
                  } catch (ex) {
                    if (this.debug) console.error('DEBUG', ex)
                  }
                }
                let kq_kt_tt_tocdo = await API.post_tudong_tinhthuoctinh_tocdo_adsl(this.axios, { id: this.listbox.loaihinh_tb_moi.value, id1: vtocdo_id }).then(async (response) => {
                  //.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.TUDONG_TINHTHUOCTINH_TOCDO_ADSL(${this.listbox.loaihinh_tb_moi.value,vtocdo_id}) kq from dual` }).then(async (response) => {
                  if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data.data_json && response.data.data.data_json.kq) {
                    return response.data.data.kq ? response.data.data.kq : -1 //.data_json.kq;
                  } else return -1
                }) //tdan.GET_VALUE_FUNC("{?DB2}.TUDONG_TINHTHUOCTINH_TOCDO_ADSL", "vloaitb_id", this.listbox.loaihinh_tb_moi.value, "vtocdo_id", vtocdo_id).ToString();
                if (kq_kt_tt_tocdo != '-1') {
                  // DataTable dt_tocdo_goicuoc = objCheckData.GET_DATA_SQL(@"Select sothang_sd, rc_id from {?DB2}.tocdo_adsl where tocdo_id = :tocdo_id", "tocdo_id", vtocdo_id);
                  let thuoctinh = kq_kt_tt_tocdo.split(';')[0]
                  let giatri = kq_kt_tt_tocdo.split(';')[1]
                  var index_thuoctinh = -1
                  for (var index_1 = 0; index_1 < ItemList.length; ++index_1) {
                    if (ItemList[index_1].sFieldName == thuoctinh) {
                      if (kq_kt_tt_tocdo.split(';')[2] == '1') {
                        this.gridEditors[index_1].Value = giatri
                      } else {
                        this.gridEditors[index_1].Value = giatri
                      }
                      ItemList[index_1].sValue = giatri
                    }
                  }
                }

                checkTD = await API.post_kiemtra_dichvu_tudong_thuoctinh_goicuoc(this.axios, { vloaitb_id: this.listbox.loaihinh_tb_moi.value }).then(async (response) => {
                  //.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.KIEMTRA_DICHVU_TUDONG_THUOCTINH_GOICUOC(${this.listbox.loaihinh_tb_moi.value}) kq from dual` }).then(async (response) => {
                  if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    return response.data.data //.data_json;
                  } else return []
                })
                //if (tdan.GET_VALUE_FUNC("{?DB2}.KIEMTRA_DICHVU_TUDONG_THUOCTINH_GOICUOC", "vloaitb_id", this.listbox.loaihinh_tb_moi.value).ToString() == "ok")
                if (checkTD && checkTD == 'ok') {
                  let dt_tskt_goicuoc = await API.post_lay_thongso_kt_goicuoc(this.axios, { vtocdo_id: this.tocdo_id }).then(async (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                      return this.UpperCasePropertyList(response.data.data)
                    } else return []
                  }) //tdan.GET_DATA_PROC("{?DB2}.LAY_THONGSO_KT_GOICUOC", "vtocdo_id", tocdo_id);
                  if (dt_tskt_goicuoc && dt_tskt_goicuoc.length > 0) {
                    for (let r of dt_tskt_goicuoc) {
                      for (var i_ts = 0; i_ts < ItemList.length; i_ts++) {
                        if (ItemList[i_ts].sFieldName == r['FIELD_NAME']) {
                          if (r['DATA_TYPE'] == '0') {
                            ItemList[i_ts].sValue = r['FIELD_VALUE']
                            this.gridEditors[i_ts].Value = r['FIELD_VALUE']
                          }

                          if (r['DATA_TYPE'] == '1') {
                            ItemList[i_ts].sValue = r['FIELD_VALUE']
                            this.gridEditors[i_ts].Value = r['FIELD_VALUE']
                            if (r['FIELD_NAME'] == 'SO_THANG') {
                              let vt_ngaybd = -1,
                                vt_ngaykt = -1
                              for (var i_gr = 0; i_gr < ItemList.length; i_gr++) {
                                if (ItemList[i_gr].sFieldName == 'NGAY_BD') {
                                  vt_ngaybd = i_gr
                                }
                                if (ItemList[i_gr].sFieldName == 'NGAY_KT') {
                                  vt_ngaykt = i_gr
                                  //break;
                                }
                              }
                              if (vt_ngaybd != -1 || vt_ngaykt != -1) {
                                let tmpdate = moment(ItemList[vt_ngaybd].sValue, 'DD/MM/YYYY').toDate()
                                tmpdate = new Date(tmpdate.setMonth(tmpdate.getMonth() + r['FIELD_VALUE'] * 1))
                                let ngay_kt = moment(tmpdate).format('DD/MM/YYYY')
                                // DateTime ngay_kt = DateTime.ParseExact(ItemList[vt_ngaybd].sValue, "dd/MM/yyyy", CultureInfo.InvariantCulture).AddMonths(Convert.ToInt32(r["FIELD_VALUE"].ToString()));
                                ItemList[vt_ngaykt].sValue = ngay_kt
                                this.gridEditors[vt_ngaykt].Value = ngay_kt
                              }
                            }
                          }

                          if (r['DATA_TYPE'] == 2) {
                            if (r['FIELD_VALUE'].indexOf('NOW') >= 0) {
                              let gt_ngay = r['FIELD_VALUE'].split('+')
                              if (gt_ngay.length == 1) {
                                ItemList[i_ts].sValue = moment(new Date()).format('DD/MM/YYYY') //tt_nd.ngay_cn.ToString("dd/MM/yyyy");
                                this.gridEditors[i_ts].Value = new Date() //tt_nd.ngay_cn;
                              } else {
                                let add = gt_ngay[1] * 1
                                let gt_ngay_gc = moment()
                                  .add(1, gt_ngay[2] ? gt_ngay[2] : 'D')
                                  .toDate()
                                // if (gt_ngay[2] == "Y")
                                // {
                                //     gt_ngay_gc = moment().add(1, 'Y');//tt_nd.ngay_cn.AddYears(add);
                                // }
                                // else if (gt_ngay[2] == "M")
                                // {
                                //     gt_ngay_gc = tt_nd.ngay_cn.AddMonths(add);
                                // }
                                // else
                                // {
                                //     gt_ngay_gc = tt_nd.ngay_cn.AddDays(add);
                                // }
                                ItemList[i_ts].sValue = moment(gt_ngay_gc).format('DD/MM/YYYY')
                                this.gridEditors[i_ts].Value = gt_ngay_gc
                              }
                            } else {
                              ItemList[i_ts].sValue = r['FIELD_VALUE']
                              this.gridEditors[i_ts].Value = moment(r['FIELD_VALUE'], 'DD/MM/YYYY').toDate() //DateTime.ParseExact(r["FIELD_VALUE"].ToString(), "dd/MM/yyyy", CultureInfo.InvariantCulture);
                            }
                          }
                        }
                      }
                    }
                  }
                }

                //FIXME 2 dong nay chi de thong bao gia tri combobox da thay doi, tam bo qua
                // if (this.listbox.cboGoiCuocAdsl.value == 0)
                //     cboGoiCuocAdsl.Properties.GetKeyValue(0);
                repositoryItemLookUpEdit.Value = vtocdo_id
                //this.HT_GIAODIEN_DONVI();
                this.HT_DS_KhuyenMai_Combobox()
              } catch (ex) {
                if (this.debug) console.error(ex)
                // Message_Box.Show(ex.Message);
              }
            }
            if (ItemList[i].sFieldName == 'MUCCUOC_ID') {
              vmuccuoc_id = ItemList[i].sValue
              try {
                this.listbox.muccuoc.value = vmuccuoc_id ? vmuccuoc_id : 0 //cboMucCuoc.SelectedValue = vmuccuoc_id;
                // if (this.listbox.muccuoc.list.filter((x) => x.muccuoc_id == this.listbox.muccuoc.value).length <= 0) this.listbox.muccuoc.value = this.listbox.muccuoc.list.length > 0 ? this.listbox.muccuoc.list[0].muccuoc_id : 0 //cboMucCuoc.SelectedIndex = 0;
              } catch (ex) {
                if (this.debug) console.error(ex)
              }
            }
            if (ItemList[i].sFieldName == 'HEDIEUHANH_ID') {
              let SQL = this.ReturnSqlStr(ItemList, i)
              let data = ItemList[i].SqlData
              if (this.gridEditors[i]) {
                if (data && data.length > 0) {
                  data = data.map((x) => ({ text: x[this.gridEditors[i].DisplayMember], id: x[this.gridEditors[i].ValueMember] }))
                }
                this.gridEditors[i].DataSource = data
              }
              // await API.post_sp_huonggiao_runsql(this.axios, { "command": SQL }).then(async (response) => {
              //   if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              //     return response.data.data.data_json;
              //   }
              //   else return [];
              // });
              // DataSet data = this.bangts.GetSQL(SQL, "");
              // ((RepositoryItemGridLookUpEdit)gridEditors[i].RepositoryItem).DataSource = (object)data.Tables[0];
              break
            }
          }
          let pdata = {
            vapld_id: this.diachiLD.AP_ID ? this.diachiLD.AP_ID : 0,
            vdoituong_id: this.listbox.doituong.value ? this.listbox.doituong.value : this.listbox.doituong.list && this.listbox.doituong.list[0] ? this.listbox.doituong.list[0].doituong_id : 0,
            vkhuld_id: this.diachiLD.KHU_ID ? this.diachiLD.KHU_ID : 0,
            vloaitb_id: this.listbox.loaihinh_tb_moi.value,
            vmuccuoc_id: vmuccuoc_id,
            vngay: this.NgayYeuCau.indexOf(' ') > 0 ? this.NgayYeuCau.substring(0, this.NgayYeuCau.indexOf(' ')) : this.NgayYeuCau,
            vphold_id: this.diachiLD.PHO_ID ? this.diachiLD.PHO_ID : 0,
            vphuongld_id: this.diachiLD.PHUONG_ID ? this.diachiLD.PHUONG_ID : 0,
            vtocdo_id: vtocdo_id
          }
          let api = cache.getters.getFromCache('post_lay_ds_mucuoc_tb', pdata)

          if (!api) api = API.post_lay_ds_mucuoc_tb(this.axios, pdata)
          else
            api = new Promise((resolve, reject) => {
              resolve(api)
            })

          let ds = await api.then(async (response) => {
            //API.post_lay_ds_mucuoc_tb(this.axios, pdata).then(async (response) => {
            cache.dispatch('addToCache', { key: 'post_lay_ds_mucuoc_tb', subkey: pdata, data: response })
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              return response.data.data
            } else return []
          })
          console.log(ds.length);
          if (ds.length > 0) {
            this.listbox.muccuoc_tb.list = ds
            if (!this.listbox.muccuoc_tb.value || !this.listbox.muccuoc_tb.list.find((x) => (x.muccuoctb_id ? x.muccuoctb_id : x.mucuoctb_id) == this.listbox.muccuoc_tb.value)) this.listbox.muccuoc_tb.value = ds[0].mucuoctb_id
            // cboMucCuocTB.ValueMember = "mucuoctb_id";
            // cboMucCuocTB.DisplayMember = "tenmuccuoc";
          } //else this.listbox.muccuoc_tb.list = []
          this.LayTienTheoKhoanMuc()
          await this.LAY_DS_DVGT_MACDINH(this.hdtb_id, this.listbox.loaihinh_tb_moi.value)
        }
        if (repositoryItemLookUpEdit.ID == 'MUCCUOC_ID') {
          // FIXME thay ValueMember = ID, can test lai
          let vtocdo_id = 0
          let vmuccuoc_id = 0
          for (var i = 0; i < ItemList.length; i++) {
            if (ItemList[i].sFieldName == 'MUCCUOC_ID') {
              ItemList[i].sValue = repositoryItemLookUpEdit.Value //((GridLookUpEdit)sender).EditValue.ToString();
              vmuccuoc_id = ItemList[i].sValue * 1
              this.muccuoc_id = vmuccuoc_id
              try {
                this.listbox.muccuoc.value = vmuccuoc_id //cboMucCuoc.SelectedValue = vmuccuoc_id;
                if (this.listbox.muccuoc.list.filter((x) => x.muccuoc_id == this.listbox.muccuoc.value).length <= 0) this.listbox.muccuoc.value = this.listbox.muccuoc.list.length > 0 ? this.listbox.muccuoc.list[0].muccuoc_id : 0 //cboMucCuoc.SelectedIndex = 0;
              } catch (ex) {}
            }
            if (ItemList[i].sFieldName == 'TOCDO_ID') {
              vtocdo_id = ItemList[i].sValue * 1
              this.tocdo_id = vtocdo_id
              try {
                await this.HT_GIAODIEN_DONVI();
                // if (this.listbox.cboGoiCuocAdsl.value == 0) //Bo qua doan nay vi chi co chuc nang raise event thay doi gia tri
                //     cboGoiCuocAdsl.Properties.GetKeyValue(0);
              } catch (ex) {}
            }
          }

          let pdata = {
            vapld_id: this.diachiLD.AP_ID ? this.diachiLD.AP_ID : 0,
            vdoituong_id: this.listbox.doituong.value ? this.listbox.doituong.value : this.listbox.doituong.list && this.listbox.doituong.list[0] ? this.listbox.doituong.list[0].doituong_id : 0,
            vkhuld_id: this.diachiLD.KHU_ID ? this.diachiLD.KHU_ID : 0,
            vloaitb_id: this.listbox.loaihinh_tb.value,
            vmuccuoc_id: vmuccuoc_id,
            vngay: this.NgayYeuCau.indexOf(' ') > 0 ? this.NgayYeuCau.substring(0, this.NgayYeuCau.indexOf(' ')) : this.NgayYeuCau,
            vphold_id: this.diachiLD.PHO_ID ? this.diachiLD.PHO_ID : 0,
            vphuongld_id: this.diachiLD.PHUONG_ID ? this.diachiLD.PHUONG_ID : 0,
            vtocdo_id: vtocdo_id
          }
          let api = cache.getters.getFromCache('post_lay_ds_mucuoc_tb', pdata)

          if (!api) api = API.post_lay_ds_mucuoc_tb(this.axios, pdata)
          else
            api = new Promise((resolve, reject) => {
              resolve(api)
            })

          let ds = await api.then(async (response) => {
            //API.post_lay_ds_mucuoc_tb(this.axios, pdata).then(async (response) => {
            cache.dispatch('addToCache', { key: 'post_lay_ds_mucuoc_tb', subkey: pdata, data: response })
            // let ds = await API.post_lay_ds_mucuoc_tb(this.axios, pdata).then(async (response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              return response.data.data
            } else return []
          })
          // let ds = tcdanhba.LAY_DS_MUCUOC_TB(khuld_id, apld_id, phold_id, phuongld_id, this.listbox.loaihinh_tb.value, this.listbox.doituong.value, vtocdo_id, vmuccuoc_id, dtpNgayYeuCau.Value.ToString("dd/MM/yyyy"));
          if (ds.length > 0) {
            this.listbox.muccuoc_tb.list = ds
            if (!this.listbox.muccuoc_tb.value || !this.listbox.muccuoc_tb.list.find((mc) => (mc.muccuoctb_id ? mc.muccuoctb_id : mc.mucuoctb_id) == this.listbox.muccuoc_tb.value)) this.listbox.muccuoc_tb.value = ds[0].mucuoctb_id
            // cboMucCuocTB.ValueMember = "mucuoctb_id";
            // cboMucCuocTB.DisplayMember = "tenmuccuoc";
          } //else this.listbox.muccuoc_tb.list = []
        }
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex)
        console.log(ex)
      }
    },
    async repositoryItemLookUpEdit_EditValueChanging(repositoryItemLookUpEdit) {
      let ItemList = this.ItemList
      if (this.listbox.loaihinh_tb.value == LoaiHinhTB.CA) {
        // RepositoryItemGridLookUpEdit repositoryItemLookUpEdit = !(sender is GridLookUpEdit) ? sender as RepositoryItemGridLookUpEdit : (sender as GridLookUpEdit).Properties;
        //FIXME comment tạm lại vì dữ liệu phân loại khách hàng thiếu field loại
        // if (repositoryItemLookUpEdit.ID == "LOAICTS_ID")
        // {
        //     if (this.vkieukh_id_ == 0)
        //     {
        //         if (repositoryItemLookUpEdit.Value != 1)
        //         {
        //             repositoryItemLookUpEdit.Cancel = false;
        //             this.$toast.error("Bạn chỉ được chọn loại khách hàng cá nhân!");
        //             return false;
        //         }
        //     }
        //     else
        //     {
        //         if (repositoryItemLookUpEdit.Value == 1)
        //         {
        //             repositoryItemLookUpEdit.Cancel = true;
        //             this.$toast.error("Bạn chỉ được chọn loại khách hàng doanh nghiệp hoặc cá nhân thuộc tổ chức/doanh nghiệp!");
        //             return false;
        //         }
        //     }
        // }

        if (repositoryItemLookUpEdit.ID == 'TOCDO_ID') {
          let vtocdo_id = repositoryItemLookUpEdit.Value
          let dt_tocdo_goicuoc = await API.post_dt_tocdo_goicuoc(this.axios, { id: vtocdo_id }).then(async (response) => {
            //.post_sp_huonggiao_runsql(this.axios, { "command": `Select sothang_sd, rc_id from ${DatabaseConstants.DB2}.tocdo_adsl where tocdo_id = ${vtocdo_id}` }).then(async (response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              return response.data.data //.data_json;
            } else return []
          }) //objCheckData.GET_DATA_SQL(@"Select sothang_sd, rc_id from {?DB2}.tocdo_adsl where tocdo_id = :tocdo_id", "tocdo_id", vtocdo_id);

          let rc_id = dt_tocdo_goicuoc[0]['rc_id'] * 1
          let vloaicts_id = 0
          for (var index_1 = 0; index_1 < ItemList.length; index_1++) {
            if (ItemList[index_1].sFieldName == 'LOAICTS_ID') {
              if (!this.gridEditors[index_1].Value) {
                vloaicts_id = 0
              } else {
                vloaicts_id = this.gridEditors[index_1].Value * 1
              }
              break
            }
          }
          let kt_chon_goicuoc_ca = await API.post_kt_chon_goicuoc_ca(this.axios, { id: rc_id, id1: vloaicts_id }).then(async (response) => {
            //.post_sp_huonggiao_runsql(this.axios, { "command": `Select ${DatabaseConstants.DB2}.KT_CHON_GOICUOC_CA(${rc_id},${vloaicts_id}) kq from dual` }).then(async (response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              return response.data.data //.data_json;
            } else return response.data.message_detail
          }) //tdan.GET_VALUE_FUNC("{?DB2}.KT_CHON_GOICUOC_CA", "vrc_id", rc_id, "vloaicts_id", vloaicts_id).ToString();
          if (!kt_chon_goicuoc_ca || kt_chon_goicuoc_ca != 'ok') {
            repositoryItemLookUpEdit.Cancel = true //FIXME can xem cach cancel phan xu ly nay!
            this.$toast.error(kt_chon_goicuoc_ca)
            // return false; // FIXME Ham check nay dang loi nen comment tam lai de chay, sua xong bo comment sau.
          }
        }
      }
      return true
    },
    async repositoryItemButtonEdit_ButtonClick(repositoryItemButtonEdit) {
      try {
        let ItemList = this.ItemList
        // BO SUNG 2022-11-02 THANGDM1
        // #region Báo hiệu quốc tế
        // if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.BaoHieuQuocTe ||
        //     Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.THOAIQT)
        // {
        //     if (repositoryItemButtonEdit.Properties.Tag.ToString() == "THIETBI")
        //     {
        //         string ma_pos = "";
        //         try
        //         {
        //             string sql = "select ma_pos from " + DatabaseConstants.DB2 + ".hdtb_cntt where hdtb_id = " + hdtb_id + "";
        //             ma_pos = tdan.GET_VALUE_SQL(sql).ToString();
        //             if (ma_pos == "")
        //             {
        //                 Message_Box.ShowTB("Không có thông tin loại cước. Hãy kiểm tra lại thông tin loại cước");
        //             }
        //         }
        //         catch
        //         {
        //             Message_Box.ShowTB("Lỗi lấy thông tin loại cước. Hãy kiểm tra lại thông tin loại cước");
        //         }
        //         string[] loaicuoc = ma_pos.Split(',');
        //         int point = 0;
        //         for (int i = 0; i < loaicuoc.Count(); i++)
        //         {
        //             if ((int.Parse(loaicuoc[i]) == 841 || int.Parse(loaicuoc[i]) == 844) && hdtb_id >= 0 && ma_tb != "")
        //             {
        //                 frmTronGoiBaoHieuQT frm = new frmTronGoiBaoHieuQT(hdtb_id, ma_tb, luong_id, int.Parse(loaicuoc[i].ToString()));
        //                 frm.ShowDialog();
        //                 if (frm.is_ghilai) point++;

        //             }
        //             if ((int.Parse(loaicuoc[i]) == 842 || int.Parse(loaicuoc[i]) == 845) && hdtb_id >= 0 && ma_tb != "")
        //             {
        //                 frmGoiSanLuong frm = new frmGoiSanLuong(hdtb_id, ma_tb, luong_id, int.Parse(loaicuoc[i].ToString()));
        //                 frm.ShowDialog();
        //                 if (frm.is_ghilai) point++;
        //             }
        //             if ((int.Parse(loaicuoc[i]) == 843 || int.Parse(loaicuoc[i]) == 846) && hdtb_id >= 0 && ma_tb != "")
        //             {
        //                 frmLuyTienSanLuong frm = new frmLuyTienSanLuong(hdtb_id, ma_tb, luong_id, int.Parse(loaicuoc[i].ToString()));
        //                 frm.ShowDialog();
        //                 if (frm.is_ghilai) point++;
        //             }
        //             if (point == loaicuoc.Count())
        //             {
        //                 for (int j = 0; j < ItemList.Length; j++)
        //                 {
        //                     if (this.ItemList[j].sFieldName == "THIETBI")
        //                     {
        //                         gridEditors[j].Value = tt_nd.ma_nd + " Đã cập nhật";
        //                         ItemList[j].sValue = tt_nd.ma_nd + " Đã cập nhật";
        //                     }
        //                 }
        //             }

        //         }
        //     }
        // }
        // #endregion
        // #region VNedu Content
        // if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.EduContent)
        // {
        //     string id_truong = "";
        //     string ten_truong = "";
        //     try
        //     {
        //         id_truong = ItemList.ToList().Find(x => x.sFieldName == "MA_PHU").sValue;
        //         ten_truong = ItemList.ToList().Find(x => x.sFieldName == "THUONGHIEU").sValue;
        //     }
        //     catch
        //     {
        //     }

        //     if (repositoryItemButtonEdit.Properties.Tag.ToString() == "ROUTER" && id_truong != "" && ten_truong != "")
        //     {
        //         string kq_check = tdan.GET_VALUE_FUNC("{?DB2}.FN_CHECK_DK_DV_VNEDU_CONTENT", "vloaitb_id", 323, "vtocdo_id", tocdo_id, "vid_truong", id_truong, "vtentruong", ten_truong).ToString();
        //         if (kq_check != "OK")
        //         {
        //             Message_Box.ShowTB(kq_check.ToString());
        //             return;
        //         }
        //         else
        //         {
        //             for (int i = 0; i < ItemList.Length; i++)
        //             {
        //                 if (ItemList[i].sFieldName == "ROUTER")
        //                 {
        //                     gridEditors[i].Value = "Đủ điều kiện";
        //                     ItemList[i].sValue = "Đủ điều kiện";
        //                     grvMacdinh.RefreshData();
        //                 }
        //             }
        //         }
        //     }
        // }
        // #endregion
        // if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.VNPT_HKD)
        // {
        //     frmDiaChiCNTT fdccntt = new frmDiaChiCNTT();
        //     fdccntt.loaitb_id = LoaiHinhTB.VNPT_HKD;
        //     for (int i = 0; i < ItemList.Length; i++)
        //     {
        //         if (ItemList[i].sFieldName == "DIACHI_DT")
        //         {
        //             fdccntt.diachi_text = gridEditors[i].Value.ToString();
        //         }
        //         if (ItemList[i].sFieldName == "MUCDICH_IDS")
        //         {
        //             fdccntt.diachi_phuong = gridEditors[i].Value.ToString();
        //         }
        //     }
        //     fdccntt.ShowDialog();
        //     for (int i = 0; i < ItemList.Length; i++)
        //     {
        //         if (ItemList[i].sFieldName == "DIACHI_DT")
        //         {
        //             gridEditors[i].Value = fdccntt.diachi_text;
        //             ItemList[i].sValue = fdccntt.diachi_text;
        //         }
        //         if (ItemList[i].sFieldName == "MUCDICH_IDS")
        //         {
        //             gridEditors[i].Value = fdccntt.diachi_phuong;
        //             ItemList[i].sValue = fdccntt.diachi_phuong;
        //         }
        //     }
        // }
        // if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.VNPT_IQMS)
        // {
        //     frmDiaChiCNTT fdccntt = new frmDiaChiCNTT();
        //     fdccntt.loaitb_id = LoaiHinhTB.VNPT_IQMS;
        //     for (int i = 0; i < ItemList.Length; i++)
        //     {
        //         if (ItemList[i].sFieldName == "DIACHI_DT")
        //         {
        //             fdccntt.diachi_text = gridEditors[i].Value.ToString();
        //         }
        //         if (ItemList[i].sFieldName == "MUCDICH_IDS")
        //         {
        //             fdccntt.diachi_phuong = gridEditors[i].Value.ToString();
        //         }
        //     }
        //     fdccntt.ShowDialog();
        //     for (int i = 0; i < ItemList.Length; i++)
        //     {
        //         if (ItemList[i].sFieldName == "DIACHI_DT")
        //         {
        //             gridEditors[i].Value = fdccntt.diachi_text;
        //             ItemList[i].sValue = fdccntt.diachi_text;
        //         }
        //         if (ItemList[i].sFieldName == "MUCDICH_IDS")
        //         {
        //             gridEditors[i].Value = fdccntt.diachi_phuong;
        //             ItemList[i].sValue = fdccntt.diachi_phuong;
        //         }
        //     }
        // }

        // if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.SMART_CA)
        // {
        //     if (repositoryItemButtonEdit.Properties.Tag.ToString() == "MA_PHU")
        //     {
        //         frmChonViNganSach f = new frmChonViNganSach();
        //         f.vma_kh = txtMaKH.Text;
        //         f.ShowDialog();
        //         for (int i = 0; i < ItemList.Length; i++)
        //         {
        //             if (ItemList[i].sFieldName == "MA_PHU")
        //             {
        //                 ItemList[i].sValue = f.out_mavi;
        //                 gridEditors[i].Value = f.out_mavi;
        //                 break;
        //             }

        //         }
        //     }
        // }
        // END BO SUNG 2022-11-02 THANGDM1
        // ButtonEdit repositoryItemButtonEdit = (sender as ButtonEdit);
        // if (this.listbox.loaihinh_tb.value == LoaiHinhTB.VNPT_SMCC) // bo do khong co chuc nang gi
        // {

        // }
        let kt_api = await API.post_loaihinh_tb_edu_api(this.axios, { id: this.listbox.loaihinh_tb.value }).then(async (response) => {
          //.post_sp_huonggiao_runsql(this.axios, { "command": `Select ${DatabaseConstants.DB2}.LOAHINH_TB_EDU_API(${this.listbox.loaihinh_tb.value}) kq from dual` }).then(async (response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            return response.data.data[0] && response.data.data[0].kq ? response.data.data[0].kq : 'no' //.data_json.kq;
          } else return 'no'
        }) //tdan.GET_VALUE_FUNC("{?DB2}.LOAHINH_TB_EDU_API", "vloaitb_id", this.listbox.loaihinh_tb.value).ToString();
        if (kt_api == 'ok') {
          if (repositoryItemButtonEdit.Tag == 'SL_MAILING') {
            for (var i = 0; i < ItemList.length; i++) {
              if (ItemList[i].sFieldName == 'SL_MAILING') {
                if (this.listbox.loaihinh_tb.value == LoaiHinhTB.TTDL_HDDT) {
                  this.popupComponentData = { dsHDTB_CT: this.dsHDTB_CT, soluong: this.gridEditors[i].Value || this.gridEditors[i].Value == '' ? 0 : this.gridEditors[i].Value }
                  this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonMauHDDT')
                  this.popupComponentName = 'popupChonMauHDDT'
                  this.Popup('popupComponents')
                  break
                }
              }
            }
          }
          if (repositoryItemButtonEdit.Tag == 'MA_PHU') {
            if (this.listbox.loaihinh_tb.value == LoaiHinhTB.Voice_Brandname) {
              this.popup.DSThanhVien = true;
              if (this.buttonSet != 1)
                this.$refs.frmNhapDSThanhVien.hdtb_id = this.hdtb_id
              else
                this.$toast.error('Chưa ghi lại hợp đồng. Hãy ghi lại hợp đồng trước');
              this.$refs.frmNhapDSThanhVien._ds_hdtbct = this.dsHDTB_CT;
              this.$refs.frmNhapDSThanhVien.ShowDialog()

              // this.popupComponentData = {"hdtb_id":this.hdtb_id, _ds_hdtbct: this.dsHDTB_CT};
              // this.popupComponentData.Text = "Nhập danh sách số hiển thị";
              // this.popupComponent = ()=>import('@/modules/contract/setup/ChangeContractOwner/popup/frmNhapDSThanhVien.vue');
              // this.popupComponentName = "frmNhapDSThanhVien";
              // this.Popup('popupComponents');
            } else
              for (var i = 0; i < ItemList.length; i++) {
                if (ItemList[i].sFieldName == 'MA_PHU') {
                  if (ItemList[i].sLookupApi != '') {
                    this.popupComponentData = {
                      loaihinh_tb: this.listbox.loaihinh_tb.value,
                      field_name: ItemList[i].sFieldName,
                      api_url: ItemList[i].sLookupApi,
                      method: ItemList[i].sAPIMethod,
                      hienthi_bang: ItemList[i].sAPIHienThiBang,
                      giatri_trave: ItemList[i].sAPIValueMember,
                      ItemList: ItemList,
                      idx: i
                    }
                    if (this.listbox.loaihinh_tb.value == LoaiHinhTB.VNPT_SLL) {
                      this.popupComponentData.Text = 'Lấy thông tin ID trường'
                    }
                    this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupLayDuLieuTuAPI')
                    this.popupComponentName = 'popupLayDuLieuTuAPI'
                    this.Popup('popupComponents')
                  }
                  break
                }
              }
          }

          if (repositoryItemButtonEdit.Tag == 'SOLUONG') {
            for (var i = 0; i < ItemList.length; i++) {
              if (ItemList[i].sFieldName == 'SOLUONG') {
                try {
                  var id_truong = ItemList.filter((x) => x.sFieldName == 'MA_PHU')[0].sValue
                  let tocdo_id = ItemList.filter((x) => x.sFieldName == 'TOCDO_ID')[0].sValue
                  this.popupComponentData = { id_truong: id_truong, dsHDTB_CT: this.dsHDTB_CT, tocdo_id: tocdo_id }
                  this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDangKySoLienLac')
                  this.popupComponentName = 'popupDangKySoLienLac'
                  this.Popup('popupComponents')
                } catch (ex) {
                  this.toast.error('' + ex)
                  if (this.debug) console.error(ex)
                }
              }
            }
          }
        }

        if (this.listbox.loaihinh_tb.value == LoaiHinhTB.VNEDU_SMARTID) {
          if (repositoryItemButtonEdit.Tag == 'SL_MAILING') {
            for (var i = 0; i < ItemList.length; i++) {
              if (ItemList[i].sFieldName == 'SL_MAILING') {
                this.popupComponentData = { loaitb_id: this.listbox.loaihinh_tb.value, append_name: 'SL_MAILING', saveList: this.dsHDTB_CT, ItemList: ItemList }
                this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChiTietHDTB')
                this.popupComponentName = 'popupChiTietHDTB'
                this.Popup('popupComponents')
              }
            }
          }
        }
        if (repositoryItemButtonEdit.Tag == 'MATB_TN') {
          let kieu = this.kieu_ld_kieu
          let dulieu = {}
          switch (kieu * 1) {
            case 2:
              if (this.khachhang.khachhang_id && this.khachhang.khachhang_id > 0) {
                dulieu.khachhang_id = this.khachhang.khachhang_id
                dulieu.kieuld_id = this.listbox.kieu_ld.value
                dulieu.loaitb_id = this.listbox.loaihinh_tb.value
                dulieu.dichvuvt_id = this.listbox.dichvu_vt.value
                this.popupComponentData = dulieu
                this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDSTBCungDoiCap')
                this.popupComponentName = 'popupDSTBCungDoiCapCNTT'
                this.popupRetVal = null
                this.Popup('popupComponents')
                let xlykq = setInterval(() => {
                  if (this.popupRetVal) {
                    clearInterval(xlykq)
                    for (var i = 0; i < ItemList.length; i++) {
                      if (ItemList[i].sFieldName == 'MATB_TN') {
                        ItemList[i].sValue = this.popupRetVal.MA_TB
                        this.gridEditors[i].Value = this.popupRetVal.MA_TB
                        repositoryItemButtonEdit.Text = this.popupRetVal.MA_TB
                      }
                      if (ItemList[i].sFieldName == 'MADOICAP') {
                        ItemList[i].sValue = this.popupRetVal.MADOICAP
                        this.gridEditors[i].Value = this.popupRetVal.MADOICAP
                      }
                    }
                    // grcMacdinh.RefreshDataSource();
                  }
                }, 1000)
                // while(this.popupRetVal==null) {
                //   //KHong lam gi, cho popup hoan thanh!
                // }
                // if (this.popupRetVal)
                // {
                //     for (var i = 0; i < ItemList.length; i++)
                //     {
                //         if (ItemList[i].sFieldName == "MATB_TN")
                //         {
                //             ItemList[i].sValue = this.popupRetVal.ma_tn;
                //             this.gridEditors[i].Value = this.popupRetVal.ma_tn;
                //             repositoryItemButtonEdit.Text = this.popupRetVal.ma_tn;
                //         }
                //         if (ItemList[i].sFieldName == "MADOICAP")
                //         {
                //             ItemList[i].sValue = this.popupRetVal.ma_doicap;
                //             this.gridEditors[i].Value = this.popupRetVal.ma_doicap;
                //         }
                //     }
                //     // grcMacdinh.RefreshDataSource();
                // }
              } else {
                this.$toast.error('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !')
                return
              }
              break
            case 1:
            case 3:
              if (this.khachhang.hdkh_id && this.khachhang.hdkh_id > 0) {
                dulieu.hdkh_id = this.khachhang.hdkh_id
                dulieu.vkhachhang_id = this.khachhang.khachhang_id
                dulieu.vkieuld_id = this.listbox.kieu_ld.value
                dulieu.loaitb_id = this.listbox.loaihinh_tb.value
                this.popupComponentData = dulieu
                this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDSTBLapKem')
                this.popupComponentName = 'popupDSTBLapKemCNTT'
                this.popupRetVal = null
                this.Popup('popupComponents')
                let xlykq = setInterval(() => {
                  if (this.popupRetVal) {
                    clearInterval(xlykq)
                    if (this.popupRetVal.MA_TB)
                      for (var i = 0; i < ItemList.length; i++) {
                        if (ItemList[i].sFieldName == 'MATB_TN') {
                          ItemList[i].sValue = this.popupRetVal.MA_TB
                          this.gridEditors[i].Value = this.popupRetVal.MA_TB
                          repositoryItemButtonEdit.Text = this.popupRetVal.MA_TB
                        }
                        if (ItemList[i].sFieldName == 'MADOICAP') {
                          ItemList[i].sValue = this.popupRetVal.MADOICAP
                          this.gridEditors[i].Value = this.popupRetVal.MADOICAP
                        }
                      }
                    else if (kieu * 1 == 3) {
                      for (var i = 0; i < ItemList.length; i++) {
                        if (ItemList[i].sFieldName == 'MATB_TN') {
                          ItemList[i].sValue = ''
                          this.gridEditors[i].Value = ''
                          repositoryItemButtonEdit.Text = ''
                        }
                        if (ItemList[i].sFieldName == 'MADOICAP') {
                          ItemList[i].sValue = '0'
                          this.gridEditors[i].Value = '0'
                        }
                      }
                      // grcMacdinh.RefreshDataSource();
                    }
                    // grcMacdinh.RefreshDataSource();
                  }
                }, 1000)
                // while(this.popupRetVal==null) {
                //   //KHong lam gi, cho popup hoan thanh!
                // }
                // if (this.popupRetVal)
                // {
                //   for (var i = 0; i < ItemList.length; i++)
                //   {
                //     if (ItemList[i].sFieldName == "MATB_TN")
                //     {
                //         ItemList[i].sValue = this.popupRetVal.ma_tn;
                //         this.gridEditors[i].Value = this.popupRetVal.ma_tn;
                //         repositoryItemButtonEdit.Text = this.popupRetVal.ma_tn;
                //     }
                //     if (ItemList[i].sFieldName == "MADOICAP")
                //     {
                //         ItemList[i].sValue = this.popupRetVal.ma_doicap;
                //         this.gridEditors[i].Value = this.popupRetVal.ma_doicap;
                //     }
                //   }
                //   // grcMacdinh.RefreshDataSource();
                // }
                // else if(kieu*1==3)
                // {
                //     for (var i = 0; i < ItemList.length; i++)
                //     {
                //         if (ItemList[i].sFieldName == "MATB_TN")
                //         {
                //             ItemList[i].sValue = "";
                //             this.gridEditors[i].Value = "";
                //             repositoryItemButtonEdit.Text = "";
                //         }
                //         if (ItemList[i].sFieldName == "MADOICAP")
                //         {
                //             ItemList[i].sValue = "0";
                //             this.gridEditors[i].Value = "0";
                //         }
                //     }
                //     // grcMacdinh.RefreshDataSource();
                // }
              } else {
                this.$toast.error('Chưa có thông tin thuê  bao để lấy danh sách mã truy nhập lắp kèm !')
                return
              }
              break
          }
        }

        if (repositoryItemButtonEdit.Tag == 'THONGTIN_CT' && this.listbox.loaihinh_tb.value == LoaiHinhTB.IT_CARE) {
          this.popupComponentData = { vloaitb_id: this.listbox.loaihinh_tb.value, vhdtb_id: this.hdtb_id, vappend_name: repositoryItemButtonEdit.Tag }
          this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupHDTB_CNTT_SUB')
          this.popupComponentName = 'popupHDTB_CNTT_SUB'
          this.Popup('popupComponents')
        }
        if (repositoryItemButtonEdit.Tag == 'DONGIA' || repositoryItemButtonEdit.Tag == 'DINHMUC_SMS' || repositoryItemButtonEdit.Tag == 'DOITAC' || repositoryItemButtonEdit.Tag == 'DINHMUC_LL' || repositoryItemButtonEdit.Tag == 'DINHTUYEN') {
          if (this.hdtb_id != 0) {
            // && loaihinh_tb == this.listbox.loaihinh_tb.value) // dieu kien 2 nay luon dung, khong xu ly
            if (this.listbox.loaihinh_tb.value == LoaiHinhTB.VNPT_SMCC) {
              if (repositoryItemButtonEdit.Tag == 'DOITAC') {
                this.popupComponentData = { loaitb_id: this.listbox.loaihinh_tb.value, append_name: 'DOITAC', saveList: this.dsHDTB_CT, ItemList: ItemList }
                this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChiTietHDTB')
                this.popupComponentName = 'popupChiTietHDTB' // Chung code xu ly voi ham phia tren do do khong can code doan nay.
                this.Popup('popupComponents')
              }
            } else {
              this.popupRetVal = null
              this.popupComponentData = { vloaitb_id: this.listbox.loaihinh_tb.value, vhdtb_id: this.hdtb_id, vappend_name: repositoryItemButtonEdit.Tag }
              this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupHDTB_CNTT_SUB')
              this.popupComponentName = 'popupHDTB_CNTT_SUB'
              this.Popup('popupComponents')
              let xlykq = setInterval(() => {
                if (this.popupRetVal) {
                  clearInterval(xlykq)
                  if ((this.listbox.loaihinh_tb.value == LoaiHinhTB.TD_1800 || this.listbox.loaihinh_tb.value == LoaiHinhTB.TD_1900) && this.thamsomacdinh['TRIENKHAI_1800_1900'] == 1) {
                    let tien_ld = this.popupRetVal.tien_ld
                    for (var item of this.ds_tien_khoanmuc) {
                      if (item['khoanmuctt_id'] == 142) {
                        //Cước Cài Đặt Đầu Số 1800 - 1900
                        if (tien_ld != '-1') {
                          item['tien'] = tien_ld
                          item['vat'] = tien_ld * 0.1
                          //HienThiTienKhuyenMaiLapDat(dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
                          this.HienThiTienKhuyenMaiLapDat()
                          this.LayTongTien_HDTB()
                        }
                      }
                    }
                  }
                }
              }, 1000)
              // while(this.popupRetVal==null) {
              //   //KHong lam gi, cho popup hoan thanh!
              // }
              // if(this.popupRetVal)
              //   if ((this.listbox.loaihinh_tb.value == LoaiHinhTB.TD_1800
              //       || this.listbox.loaihinh_tb.value == LoaiHinhTB.TD_1900) && this.thamsomacdinh['TRIENKHAI_1800_1900'] == 1)
              //   {
              //       let tien_ld = this.popupRetVal.tien_ld;
              //       for (var item of this.ds_tien_khoanmuc)
              //       {
              //           if (item["khoanmuctt_id"] == 142) //Cước Cài Đặt Đầu Số 1800 - 1900
              //           {
              //               if (tien_ld != "-1")
              //               {
              //                   item["tien"] = tien_ld;
              //                   item["vat"] = tien_ld * 0.1;
              //                   //HienThiTienKhuyenMaiLapDat(dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
              //                   this.HienThiTienKhuyenMaiLapDat();
              //                   this.LayTongTien_HDTB();
              //               }
              //           }
              //       }
              //   }
            }
          }
        }
        if (repositoryItemButtonEdit.Tag == 'CT_DIEMCAU') {
          if (this.hdtb_id != 0) {
            this.popupComponentData = { vhdtb_id: this.hdtb_id, vhdkh_id: this.khachhang.hdkh_id, ma_gd: this.khachhang.magd, vloaitb_id: this.listbox.loaihinh_tb.value }
            this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDanhSach_DiemCau')
            this.popupComponentName = 'popupDanhSach_DiemCau'
            this.Popup('popupComponents')
            //khong thay xu ly sau khi dong popup -> chua lam gi!
          }
        }

        if (this.listbox.loaihinh_tb.value == LoaiHinhTB.TD_1800 || this.listbox.loaihinh_tb.value == LoaiHinhTB.TD_1900) {
          if (repositoryItemButtonEdit.Tag == 'MA_TB') {
            var vsomay = ''
            for (var i = 0; i < ItemList.length; i++) {
              if (ItemList[i].sFieldName == 'MA_TB') {
                vsomay = ItemList[i].sValue
              }
            }

            this.popupComponentData = {
              vloaitb_id: Number(this.listbox.loaihinh_tb.value),
              vdichvuvt_id: Number(this.listbox.dichvu_vt.value),
              vsomay: vsomay
            }
            this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonSo_CNTT')
            this.popupComponentName = 'popupChonSo_CNTT'
            this.popupComponentHeader = ''
            this.Popup('popupComponents')
          }
        }
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex)
        console.log(ex)
      }
    },
    async ReturnSqlStr(ListItem, index) {
      let s = ''
      switch (ListItem[index].sLookUpTableName) {
        case 'PHANLOAI_DV':
          s = 'select ' + ListItem[index].sLookUpKeyField + ',' + ListItem[index].sLookUpValueField + ' from ' + DatabaseConstants.DB2 + '.' + ListItem[index].sLookUpTableName + ' WHERE LOAITB_ID = ' + this.listbox.loaihinh_tb.value + ' ORDER BY ' + ListItem[index].sLookUpKeyField
          break
        case 'TOCDO_ADSL':
          s = 'Select TOCDO_ID, THUONGHIEU FROM ' + DatabaseConstants.DB2 + '.' + ListItem[index].sLookUpTableName
          s += ' WHERE SUDUNG = 1 and LOAITB_ID = ' + this.listbox.loaihinh_tb_moi.value
          if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.Web_Hosting || this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.CA || this.listbox.loaihinh_tb_moi.value.value == LoaiHinhTB.CA_IVAN || this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.IVAN || this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.IOFFICE)
            for (let type of ListItem) if (type.sFieldName == 'DAILY') s += ' and kieu_mega in (2,' + type.sValue + ')'
          let pdata = { loaitb_id: this.listbox.loaihinh_tb_moi.value }
          let checkTD = await API.post_dvcntt_tocdo_adsl(this.axios, pdata).then(async (response) => {
            //.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.TOCDO_LOADTHEO_PHANLOAI(${this.listbox.loaihinh_tb.value}) kq from dual` }).then(async (response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              return response.data.data //.data_json;
            } else return []
          })
          if (checkTD && checkTD.length > 0 && checkTD[0].kq && checkTD[0].kq == 'ok')
            for (let type of ListItem)
              if (type.sFieldName == 'PHANLOAI_ID' || (type.sFieldName == 'LOAICTS_ID' && this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.SMART_CA)) {
                pdata.kieu_mega = !type.sValue || type.sValue == '' ? -10 : type.sValue
                s += ' and kieu_mega = ' + (!type.sValue || type.sValue == '' ? -10 : type.sValue)
              }
          s += ' ORDER BY TOCDO_ID'
          let checkData = await API.post_lay_tt_tocdo_adsl(this.axios, pdata).then(async (response) => {
            //.post_sp_huonggiao_runsql(this.axios, { "command": `select ${DatabaseConstants.DB2}.TOCDO_LOADTHEO_PHANLOAI(${this.listbox.loaihinh_tb.value}) kq from dual` }).then(async (response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              return response.data.data //.data_json;
            } else return []
          })
          if (checkData && checkData.length > 0) {
            ListItem[index].SqlData = this.UpperCasePropertyList(checkData)
            if (!ListItem[index].sValue || ListItem[index].sValue == '' || !ListItem[index].SqlData.find((x) => x[ListItem[index].sLookUpKeyField] == ListItem[index].sValue)) ListItem[index].sValue = ListItem[index].SqlData[0][ListItem[index].sLookUpKeyField]
            if (this.gridEditors[index]) {
              let valuechanged = this.gridEditors[index].Value != ListItem[index].sValue
              this.gridEditors[index].Value = ListItem[index].sValue //ListItem[index].SqlData[0][ListItem[index].sLookUpKeyField]; // Chỗ này có lẽ sau xử lý theo datasource thì hay hơn, nhưng tạm thời chưa lỗi thì cứ để vậy đã
              if (valuechanged && this.gridEditors[index].EditValueChanged) this.gridEditors[index].EditValueChanged(this.gridEditors[index])
            }
          }
          // console.log(ListItem[index].SqlData);
          break
        case 'HE_DIEU_HANH':
          for (let type of ListItem) {
            if (type.sFieldName == 'TOCDO_ID') {
              if (!type.sValue || type.sValue == '') {
                s = 'select ' + ListItem[index].sLookUpKeyField + ',' + ListItem[index].sLookUpValueField + ' from ' + DatabaseConstants.DB2 + '.' + ListItem[index].sLookUpTableName + ' ORDER BY ' + ListItem[index].sLookUpKeyField
              } else {
                s = 'select a.hedieuhanh_id, a.he_dieu_hanh  '
                s += ' from ' + DatabaseConstants.DB2 + '.he_dieu_hanh a, ' + DatabaseConstants.DB2 + '.hdh_tocdo b '
                s += ' where a.hedieuhanh_id = b.hedieuhanh_id '
                s += ' and b.tocdo_id = ' + type.sValue
                s += ' order by a.hedieuhanh_id '
              }
            }
          }
          break
        case 'ABC':
          s = 'Select vungpv_id, tenvung_pv FROM ' + DatabaseConstants.DB5 + ".vungphucvu WHERE trangthai='1' order by tenvung_pv"
          break
        case 'DUNGLUONG':
          s = 'Select DUNGLUONG AS MA_SO,DUNGLUONG AS TEN from ' + DatabaseConstants.DB5 + '.DUNGLUONG order by to_number(ten)'
          break
        case 'TUYEN_CAP':
          s = 'Select tuyencap_id, tuyen_cap from ' + DatabaseConstants.DB5 + '.' + ListItem[index].sLookUpTableName
          break
        case 'TUYEN_CB':
          s = 'Select tuyencb_id, tuyen_cb from ' + DatabaseConstants.DB5 + '.' + ListItem[index].sLookUpTableName
          break
        case 'TUYEN_COT':
          s = 'Select tuyencot_id, tuyen_cot from ' + DatabaseConstants.DB5 + '.' + ListItem[index].sLookUpTableName
          break
        case '{?CSS}.LINHVUC':
          s = 'Select linhvuc_id, linhvuc from ' + DatabaseConstants.DB2 + ".LINHVUC where phanloai_sms is null or phanloai_sms like '%" + this.PhanLoai_ID + "%'" //PhanLoai_ID
          break
        default:
          s = 'select ' + ListItem[index].sLookUpKeyField + ',' + ListItem[index].sLookUpValueField + ' from ' + ListItem[index].sLookUpTableName
          break
      }
      s = s.replace('{?ADMIN}', DatabaseConstants.DB1)
      s = s.replace('{?CSS}', DatabaseConstants.DB2)
      return s
    },
    async repositoryItemTextEdit_EditValueChanged(repositoryItemTextEdit) {
      //region cuongpq thêm code  sms  brandname
      let ItemList = this.ItemList
      if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.Voice_Brandname && this.listbox.kieu_ld.value == 793 && repositoryItemTextEdit.Tag == 'MA_PL' && repositoryItemTextEdit.Value) {
        let columns = ['MA_PL', 'NGAY_KY_HD', 'MA_CHUAN', 'LOAICTS_ID', 'CHUQUAN_ID', 'MST', 'PHANLOAI_ID', 'DONVIKG_ID', 'TOCDO_ID', 'TEN_CH', 'SO_DT', 'EMAIL', 'DOMAIN', 'USERNAME']
        let vma_pl = repositoryItemTextEdit.Value
        let dt = await this.axios.get(`/web-hopdong/vbn/lay_thongtin_hopdong_cu?vma_pl=${vma_pl}`)

        if (dt && dt.data && dt.data.data && dt.data.data.length > 0) {
          for (var i = ItemList.length - 1; i >= 0; i--) {
            if (columns.includes(ItemList[i].sFieldName)) {
              ItemList[i].sValue = dt.data.data[0][ItemList[i].sFieldName.toLowerCase()]
              this.gridEditors[i].Value = dt.data.data[0][ItemList[i].sFieldName.toLowerCase()]
              //sothang = Convert.ToInt32(textValue);
            }
          }
        }
        else {
          this.$toast.error('Không tồn tại hợp đồng hoặc hợp đồng đã bị hủy. Hãy kiểm tra lại.')
        }
      }

      else if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.Voice_Brandname && repositoryItemTextEdit.Tag == 'MA_CHUAN' && repositoryItemTextEdit.Value) {
        let so_tb = repositoryItemTextEdit.Value
        let source_hotline = "";
        for (var i = ItemList.length - 1; i >= 0; i--) {
          if (ItemList[i].sFieldName == "DONVIKG_ID") {
            source_hotline = this.gridEditors[i].Value;
          }
        }

        if (source_hotline == "") this.$toast.error('Chưa nhập Source hotline.');
        if (source_hotline == "12" /*meg*/) {
          let response_kt_meg = await this.axios.post('/ccbs/executor/meg_laytt_tb', { so_tb });
          if (response_kt_meg.data.data.length == 0) {
            this.$toast.error('Số thuê bao chủ nhóm không phải là số chính (số hotline) của dịch vụ MEG, đề nghị kiểm tra lại');
            repositoryItemTextEdit.Value = "";
            return;
          }
          let kt_meg = response_kt_meg.data.data;
        }
        else if (source_hotline == "11" /*VCC*/) {
          let response_kt_vcc = await this.axios.post('/ccbs/executor/ts_vcc_lay_tttb', { so_tb });
          if (response_kt_vcc.data.data.length == 0) {
            this.$toast.error('Số thuê bao chủ nhóm không phải là số chính (số hotline) của dịch vụ VCC, đề nghị kiểm tra lại');
            repositoryItemTextEdit.Value = "";
            return;
          }
          let kt_meg = response_kt_vcc.data.data;
        }
      }
      //thêm code  load thông tin hiện hữu sms brandname
      if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.BRANDNAME && this.listbox.kieu_ld.value == 13224 && repositoryItemTextEdit.Tag == 'LABEL_ID' && repositoryItemTextEdit.Value) {
        let label_id = repositoryItemTextEdit.Value
        let phanloai_id = -1
        for (var i = ItemList.length - 1; i >= 0; i--) {
          if (ItemList[i].sFieldName == 'PHANLOAI_ID') {
            phanloai_id = this.gridEditors[i].Value
            //sothang = Convert.ToInt32(textValue);
          }
        }
        var vphanvung_id = Number(this.$root.token.getPhanVungID())
        let response = await this.axios.post(`/tichhop/brandname/load_thongtin_brandname`, { vphanvung_id, vlabel_id: label_id + '', vphanloai_id: phanloai_id + '' })
        if (response.data.data && response.data.data.status == 200) {
          let columns_brn = ['LABEL_ID', 'AGENT_ID', 'CONTRACT_ID', 'LINHVUC_ID', 'LINHVUC_VMS_ID', 'LINHVUC_VTL_ID', 'LINHVUC_GTEL_ID', 'LINHVUC_VNM_ID', 'LINHVUC_ITEL_ID', 'LINHVUC_MOBICAST_ID']
          var label_data = response.data.data.data.items[0]
          for (var i = ItemList.length - 1; i >= 0; i--) {
            if (columns_brn.includes(ItemList[i].sFieldName)) {
              ItemList[i].sValue = label_data[ItemList[i].sFieldName.toLowerCase()]
              this.gridEditors[i].Value = label_data[ItemList[i].sFieldName.toLowerCase()]
            } else if (ItemList[i].sFieldName == 'THUONGHIEU') {
              ItemList[i].sValue = label_data['label']
              this.gridEditors[i].Value = label_data['label']
            } else if (ItemList[i].sFieldName == 'NGAY_BD') {
              ItemList[i].sValue = label_data['created_date']
              this.gridEditors[i].Value = label_data['created_date']
            } else if (ItemList[i].sFieldName == 'MST') {
              ItemList[i].sValue = label_data['tax_code']
              this.gridEditors[i].Value = label_data['tax_code']
            } else if (ItemList[i].sFieldName == 'ADSER_ID') {
              ItemList[i].sValue = '0'
              this.gridEditors[i].Value = '0'
            }
          }
        } else {
          this.$toast.error(response.data.data.message)
          return
        }
      }

      //  if (tEditor.Properties.Tag.ToString() == "MA_PL" && cboKieuLD.SelectedValue.ToString() == "793"/*thêm mới brandname*/
      //     && Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.Voice_Brandname && tEditor.Text != "") {
      //             DataTable dt = tdan.GET_DATA_PROC("{?DB2}.toantrinh_vbn.lay_thongtin_hopdong_cu", "vma_pl", tEditor.Text);
      //     if (dt.Rows.Count == 0) return;
      //     let kq = Message_Box.Show("Bạn có muốn sử dụng thông tin hợp đồng cũ", "Thông báo");
      //     if (!kq) return;
      //     string[] columns = new string[] {
      //       "MA_PL", "NGAY_KY_HD", "MA_CHUAN", "LOAICTS_ID", "CHUQUAN_ID",
      //       "MST", "PHANLOAI_ID", "DONVIKG_ID", "TOCDO_ID", "TEN_CH", "SO_DT", "EMAIL", "DOMAIN",
      //       "USERNAME"
      //     };
      //     for (int i = 0; i < ItemList.Length; i++)
      //     {
      //       foreach(string fieldname in columns)
      //       {
      //         if (ItemList[i].sFieldName == fieldname) {
      //           ItemList[i].sValue = dt.Rows[0][fieldname].ToString();
      //           gridEditors[i].Value = dt.Rows[0][fieldname];
      //         }
      //       }
      //     }
      //     grcMacdinh.RefreshDataSource();
      //     grcMacdinh.EndUpdate();
      //   }

      //end region
      // FIXME Can xem lai cho tham so nay xem nen truyen o code khoi tao doi tuong hay tu form ????
      //RepositoryItemTextEdit repositoryItemTextEdit = !(sender is TextEdit) ? sender as RepositoryItemTextEdit : (sender as TextEdit).Properties;

      if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.ELEARNING) {
        if (repositoryItemTextEdit.Tag == 'SOLUONG') {
          let textValue = repositoryItemTextEdit.Value
          for (var i = ItemList.length - 1; i >= 0; i--) {
            if (ItemList[i].sFieldName == 'SOLUONG') {
              ItemList[i].sValue = textValue
              //sothang = Convert.ToInt32(textValue);
              break
            }
          }
        }

        if (repositoryItemTextEdit.Tag == 'DOMAIN') {
          let textValue = repositoryItemTextEdit.Value
          for (var i = ItemList.length - 1; i >= 0; i--) {
            if (ItemList[i].sFieldName == 'DOMAIN') {
              ItemList[i].sValue = textValue
              //sothang = Convert.ToInt32(textValue);
              break
            }
          }
        }

        if (repositoryItemTextEdit.Tag == 'MA_PHU') {
          let textValue = repositoryItemTextEdit.Value
          for (var i = ItemList.length - 1; i >= 0; i--) {
            if (ItemList[i].sFieldName == 'MA_PHU') {
              ItemList[i].sValue = textValue
              //sothang = Convert.ToInt32(textValue);
              break
            }
          }
        }
      }

      if (repositoryItemTextEdit.Tag == 'SO_THANG') {
        let b_ngay_bd = false
        let b_ngay_kt = false
        let sothang = 0
        let ngay_bd = new Date() //tt_nd.ngay_cn;
        let ngay_kt = new Date() //tt_nd.ngay_cn;

        let textValue = repositoryItemTextEdit.Value
        sothang = textValue * 1
        if (!textValue || textValue == '' || isNaN(textValue)) return
        // Da chay o duoi > khong can chay lai o day!!!
        // for (var i = ItemList.length - 1; i >= 0; i--)
        // {
        //     if (ItemList[i].sFieldName == "SO_THANG")
        //     {
        //         ItemList[i].sValue = textValue;
        //         sothang = textValue;
        //         break;
        //     }
        // }
        for (var i = 0; i < ItemList.length; i++) {
          if (ItemList[i].sFieldName == 'SO_THANG') {
            ItemList[i].sValue = textValue
            sothang = textValue * 1
          }
          if (ItemList[i].sFieldName == 'NGAY_BD') {
            b_ngay_bd = true
            ItemList[i].sValue = this.gridEditors[i].Value
            ngay_bd = this.gridEditors[i].Value
            try {
              ngay_bd = moment(ngay_bd, ngay_bd.length > 10 ? 'DD/MM/YYYY HH:mm:ss' : 'DD/MM/YYYY').toDate()
            } catch (ex) {}
          }
          if (ItemList[i].sFieldName == 'NGAY_KT') {
            b_ngay_kt = true
            ItemList[i].sValue = this.gridEditors[i].Value
            ngay_kt = this.gridEditors[i].Value
            if (ngay_kt && (ngay_kt + '').indexOf('/') > 0)
              try {
                ngay_kt = moment(ngay_kt, ngay_kt.length > 10 ? 'DD/MM/YYYY HH:mm:ss' : 'DD/MM/YYYY').toDate()
              } catch (ex) {}
            if (ngay_kt)
              try {
                ngay_kt = moment(ngay_kt).toDate()
              } catch (ex) {}
          }
          if (ItemList[i].sFieldName == 'THOIGIAN_BD') {
            b_ngay_bd = true
            ItemList[i].sValue = this.gridEditors[i].Value
            ngay_bd = this.gridEditors[i].Value
            try {
              ngay_bd = moment(ngay_bd, ngay_bd.length > 10 ? 'DD/MM/YYYY HH:mm:ss' : 'DD/MM/YYYY').toDate()
            } catch (ex) {}
          }
          if (ItemList[i].sFieldName == 'THOIGIAN_KT') {
            b_ngay_kt = true
            ItemList[i].sValue = this.gridEditors[i].Value
            ngay_kt = this.gridEditors[i].Value

            if (ngay_kt && (ngay_kt + '').indexOf('/') > 0)
              try {
                ngay_kt = moment(ngay_kt, ngay_kt.length > 10 ? 'DD/MM/YYYY HH:mm:ss' : 'DD/MM/YYYY').toDate()
              } catch (ex) {}
            if (ngay_kt)
              try {
                ngay_kt = moment(ngay_kt).toDate()
              } catch (ex) {}
          }
          if (b_ngay_bd == true && b_ngay_kt == true) break
        }
        //moment(new Date()).format("DD/MM/YYYY")
        if (b_ngay_bd == true && b_ngay_kt == true) {
          ngay_kt = new Date(ngay_bd)
          ngay_kt = ngay_kt.setMonth(ngay_kt.getMonth() + sothang * 1) //ngay_bd.AddMonths(sothang);
          ngay_kt = new Date(ngay_kt)
          //ngay_kt = moment(ngay_bd).add(sothang*1,'M').toDate();
          for (var index_1 = 0; index_1 < ItemList.length; ++index_1) {
            if (ItemList[index_1].sFieldName == 'NGAY_KT') {
              try {
                ItemList[index_1].sValue = moment(ngay_kt).format('DD/MM/YYYY')
              } catch (ex) {
                ItemList[index_1].sValue = ngay_kt
              }
              this.gridEditors[index_1].Value = ItemList[index_1].sValue //moment(ngay_kt).format("DD/MM/YYYY");
            }

            if (ItemList[index_1].sFieldName == 'THOIGIAN_KT') {
              try {
                ItemList[index_1].sValue = moment(ngay_kt).format('DD/MM/YYYY HH:mm:ss')
              } catch (ex) {
                ItemList[index_1].sValue = ngay_kt
              }
              this.gridEditors[index_1].Value = ItemList[index_1].sValue //moment(ngay_kt).format("DD/MM/YYYY HH:mm:ss");
            }
          }
        }
      }

      if (repositoryItemTextEdit.Tag == 'TONGSO_ND') {
        if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.IOFFICE) {
          let textValue = repositoryItemTextEdit.Value

          if (!textValue || textValue == '' || isNaN(textValue)) return

          this._sl_cha = textValue
          this.HT_ThietBi()
          this._sl_cha = -1
        }
      }
      if (repositoryItemTextEdit.Tag == 'SL_MAILING') {
        if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.VNPT_MEETING || this.listbox.loaihinh_tb.value == LoaiHinhTB.HNTH_NGN) {
          let textValue = repositoryItemTextEdit.Value

          if (textValue == '' || isNaN(textValue)) return

          this._sl_cha = textValue
          this.HT_ThietBi()
          this._sl_cha = -1
        }
        if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.VNPT_MCAP) {
          let textValue = repositoryItemTextEdit.Value

          if (textValue == '' || isNaN(textValue)) return
          let sothang_mcap = 0
          for (let k = 0; k < this.gridEditors.length; k++) {
            if (ItemList[k].sFieldName == 'SO_THANG') {
              sothang_mcap = this.gridEditors[k].Value == '' ? 0 : this.gridEditors[k].Value
            }
          }
          if (sothang_mcap == 0) {
            sothang_mcap = 12
          }
          let tyle_thoigian = sothang_mcap / 12

          this._sl_cha = textValue * tyle_thoigian
          this.HT_ThietBi()
          this._sl_cha = -1
        }
      }

      if (repositoryItemTextEdit.Tag == 'SO_THANG') {
        if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.VNPT_MCAP) {
          let textValue = repositoryItemTextEdit.Value

          if (!textValue || textValue == '' || isNaN(textValue)) return
          let soluong_mcap = 0
          for (let k = 0; k < this.gridEditors.length; k++) {
            if (ItemList[k].sFieldName == 'SL_MAILING') {
              soluong_mcap = !this.gridEditors[k].Value || this.gridEditors[k].Value == '' ? 0 : this.gridEditors[k].Value
            }
          }
          if (soluong_mcap == 0) {
            soluong_mcap = 1
          }

          let so_thang_mcap = 0
          if (textValue % 12 != 0) {
            so_thang_mcap = 12
          } else {
            so_thang_mcap = textValue
          }
          this._sl_cha = (so_thang_mcap / 12) * soluong_mcap
          this.HT_ThietBi()
          this._sl_cha = -1
        }
      }
    },
    repositoryItemTextEdit_EditValueChanging(repoTextEdit) {
      // TextEdit repoTextEdit = (sender as TextEdit);
      let textValue = repoTextEdit.Value //repoTextEdit.EditValue.ToString();
      if (this.listbox.loaihinh_tb_moi.value == LoaiHinhTB.ELEARNING) {
        let phanloai_dv = ''
        for (var item of this.ItemList) {
          if (item.sFieldName == 'PHANLOAI_ID') {
            phanloai_dv = item.sValue
            break
          }
        }
        if (phanloai_dv == '97') {
          if (repoTextEdit.Tag == 'TONGSO_ND') {
            if (repoTextEdit.Value != '0') {
              this.$toast.error('Bạn không cần nhập số lượng giáo viên cho gói doanh nghiệp')
              repoTextEdit.Cancel = true //FIXME can tim cach cancel thay doi
              repoTextEdit.Value = 0 //FIXME fix tam cho van de o dong tren, thay vi cancel thay doi thi cho ve gia tri dung mac dinh.
            }
          }
          if (repoTextEdit.Tag == 'SOLUONG') {
            if (repoTextEdit.NewValue != '0') {
              this.$toast.error('Bạn không cần nhập số lượng giáo viên cho gói doanh nghiệp')
              repoTextEdit.Cancel = true //FIXME can tim cach cancel thay doi
              repoTextEdit.Value = 0 //FIXME fix tam cho van de o dong tren, thay vi cancel thay doi thi cho ve gia tri dung mac dinh.
            }
          }
        }
      }
    },
  }
};
</script>
