<template src="./UpdateSubscriberDeposit.html"></template>
<style scoped src="./UpdateSubscriberDeposit.scss"></style>
<script>
import moment from "moment";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import breadcrumb from "@/components/breadcrumb";
import DataGrid from "@/components/Controls/DataGrid";
import DanhSachThueBaoCungMaModal from "@/modules/contract/setup/ChangeSubsType/popups/DanhSachThueBaoCungMaModal.vue";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
import ThongTinLienHe from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue";
import ThongTinEmail from "@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue";
import DangKyChiTietKhuyenMaiModal from "./popups/DangKyChiTietKhuyenMaiModal.vue";
import {
  LoaiHopDong,
  TrangThaiHD,
  DichVuVienThong,
  TRANGBI,
  KieuLapDat,
  KHOANMUC_TT,
  Loai_KM,
  LoaiHinhTB,
} from "@/modules/admin/category/MultiServicePackage/enum.js";
import api_installnewsubs from "@/modules/contract/setup/InstallNewSubs/InstallNewSubsAPI.js";
import api from "./API.js";

export default {
  name: "UpdateSubscriberDeposit",
  components: {
    breadcrumb,
    DataGrid,
    DatePickerPlugin,
    DatePicker,
    DanhSachThueBaoCungMaModal,
    SearchContractModal,
    SearchAccount,
    ThongTinLienHe,
    ThongTinEmail,
    DangKyChiTietKhuyenMaiModal,
  },
  props: {
    isPopup: {
      type: Boolean,
      default: false,
    },
    vma_tb: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      button: {
        tsbtnNhapMoi: true,
        tsbtnGhiLai: true,
        tsbtnXoa: true,
        tsbtnHuyBo: true,
        tsbtnThanhToan: true,
        tsbtnThemTB: false,
        tsbtnXoaTB: false,
        tsbtnCapNhatDB: true,
        tsbtnXemanh: { Enabled: true, Visible: true },
        btnTien: false,
      },
      header: {
        title: "Cập nhật thuê bao đặt cọc - Thử nghiệm",
        list: [
          //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Cập nhật thuê bao đặt cọc - Thử nghiệm",
            link: { name: "Ui.cards" },
          },
        ],
      },
      dateFormat: "DD/MM/YYYY",
      position: { X: "center", Y: "center" },
      keyenter: 0, //0: maGD; 1: maTB
      isMaTb_enter: false,
      tieuchi_id: "",
      thongtin_tracuu: "",
      popupComponentHeader: "",
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        "form-close": this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        PopUpCongTacVien_Thoat: this.popupClosed,
      },
      popupComponentName: "",
      ds_tien_khoanmuc: [],
      ds_dichvuvt: [],
      ds_tocdo: [],
      ds_loaihinhtb: [],
      ds_tratruoc: [],
      dsTBCungMa: [],
      hdtb_dc_1: [],
      hdtb_dc_2: [],
      dsHDTB: [],
      dsCTTHD: [],
      dsHDTB_CNTT: [],
      leftForm: {
        txtMaGD: "",
        dtpNgayYeuCau: moment(new Date()).format("DD/MM/YYYY"),
        txtMaTB: "",
        cboDichVuVT: 0,
        cboLoaiHinhTB: -1,
        cboGoiCuocAdsl: 0, //toc do
        txtTenTB: "",
        txtDiaChiLD: "",
      },
      rightForm: {
        cboTraTruoc: 0,
        txtThoiHan: 0,
        dtpTuNgay: moment(new Date()).format("DD/MM/YYYY"),
        dtpDenNgay: moment(new Date()).format("DD/MM/YYYY"),
        txtGhiChu: "",
        txtSumTien: 0,
        txtTongTien: 0,
        txtTongVat: 0,
      },
      filterbox: {
        ctv: {
          ctv_id: "",
          chkCTV: false,
          txtCTV: "",
        },
        nguoigt: {
          nguoigt_id: "",
          chkNguoiGT: false,
          txtNguoiGT: "",
        },
      },
      donvi_ctv_id: 0,
      today: new Date(),
      khong_load_ctv: 0,
      khong_ht_tracuudb: 0,
      ts_sinhma_gd_theo_donvi: false,
      loi_sinh_magd: false,
      chkThoiHan: { Checked: false, Enabled: false },
      khachhang_id: 0,
      hdkh_id: 0,
      ma_kh: "",
      khlon_id: 0,
      ten_kh: "",
      diachi_kh: "",
      dichvuvt_id: 0,
      hdtb_id: 0,
      thuebao_id: 0,
      ten_tb: "",
      dtpNgayLHD: moment(new Date()).format("DD/MM/YYYY"),
      diachi_ld: "",
      loaitb_id: 0,
      donvi_id: 0,
      doituong_id: 0,
      phuongld_id: 0,
      phold_id: 0,
      apld_id: 0,
      khuld_id: 0,
      vtocdo_id: 0,
      vmuccuoc_id: 0,
      dtienld: 0,
      dvatld: 0,
      vtrangbi_id: 0,
      thuebao_dc_id: 0,
      trung_matb: 0,
      tthd_id: 0,
      dtiendatcoc_tt: 0,
      dvatdatcoc_tt: 0,
      huonggiaotn_id: 0,
      quytrinh_id: 0,
      vkieu: 0,
      nhap_CTV: 0,
      check_chuahoa_cntt: false,
      kiemtra_no_tratruoc: 0,
      phainhap_thoihan: false,
      MACDINH_CTV_NGUOIDUNG: -1,
      in_ma_tb: "",
      gridDanhSach: {
        list: [],
        header: [
          {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowFiltering: true,
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên thuê bao",
            allowFiltering: true,
          },
          {
            fieldName: "loaihinh_tb",
            headerText: "Loại hình",
            allowFiltering: true,
          },
          {
            fieldName: "nguoi_cn",
            headerText: "Người CN",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
    };
  },
  created() {
    this.$root.$on("bv::modal::shown", (bvEvent, modalId) => {
      document.getElementsByClassName("modal-content").forEach(function (v) {
        v.removeAttribute("tabindex");
      });
    });
  },
  async mounted() {
    this.loading(true);
    try {
      await Promise.all([this.khoiTaoForm()]);
    } catch (ex) {
      this.loading(false);
      console.log(ex);
    } finally {
      this.loading(false);
    }
  },
  methods: {
    khoiTaoForm: async function () {
      await Promise.all([
        this.SetButton(-1),
        this.getds_tienkhoanmuc_loaihd(LoaiHopDong.DATCOC_MOI),
        this.getds_dvvt_lapmoi(),
      ]);
      this.cp_dichvuvt_id = this.ds_dichvuvt[0].id;

      await Promise.all([
        this.getds_loaihinhtb_loaidv(this.cp_dichvuvt_id),
        this.getds_tocdo_loaidv(this.cp_dichvuvt_id),
      ]);

      var ds = this.GetData(await api.LAY_DS_THAMSO_MD(this.axios, 0));
      if (ds && ds.length > 0) {
        var str_vtemp = "NHAP_CTV";
        var det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1") this.nhap_CTV = 1;
        }

        str_vtemp = "HIENTHI_IMG_DATCOC";
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        this.button.tsbtnXemanh.Visible = det && det.length > 0;

        str_vtemp = "TRUNG_MATB";
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          this.trung_matb = 1;
        }

        str_vtemp = "CHECK_CHUANHOA_CNTT"; // hoangpkn : 18/03/2020. Check thue bao CNTT can chuan hoa
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1")
            this.check_chuahoa_cntt = true;
        }

        str_vtemp = "KIEMTRA_NO_TRATRUOC";
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1")
            this.kiemtra_no_tratruoc = 1;
        }

        str_vtemp = "KHONG_LOAD_CTV";
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1") this.khong_load_ctv = 1;
        }

        str_vtemp = "PHAINHAP_THOIHAN";
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1")
            this.phainhap_thoihan = true;
        }

        str_vtemp = "KHONG_HIENTHI_TRACUU_DB_TB_DATCOC"; // hoangpkn : 13/09/2016 Thêm cho HCM chỉ được chọn 1 trong 2 loại Khuyến mại hoặc đặt cọc
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1")
            this.khong_ht_tracuudb = 1;
        }

        this.ts_sinhma_gd_theo_donvi =
          ds.filter((x) => x["ma_ts"].toString() == "SINH_MA_GD_THEO_DV")
            .length > 0;

        str_vtemp = "MACDINH_CTV_NGUOIDUNG";
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
          var row = det[0];
          if (row["ten_ts"].toString().trim() == "1")
            this.MACDINH_CTV_NGUOIDUNG = 1;
        }
      }

      if (this.MACDINH_CTV_NGUOIDUNG == 1) {
        this.filterbox.ctv.ctv_id = Number(
          await this.$root.token.getNhanVienID()
        );
        this.donvi_ctv_id = Number(await this.$root.token.getDonViID());
        var promiseCTV = this.getNhanvien_nvID(this.filterbox.ctv.ctv_id);
        this.filterbox.ctv.txtCTV = await promiseCTV;
        this.filterbox.ctv.chkCTV = true;
      }
      this.leftForm.cboLoaiHinhTB = this.ds_loaihinhtb[0].id;
      this.leftForm.cboGoiCuocAdsl =
        this.ds_tocdo.lenght > 0 ? this.ds_tocdo[0].id : -1;

      if (this.isPopup) this.frmThueBao_DatCoc_Activated();
    },
    frmThueBao_DatCoc_Activated: async function () {
      if (this.isPopup && this.vma_tb != "") {
        var ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.vma_tb);
        await this.HienThiTTHopDongKH(ds);
        // this.button.tsbtnThanhToan = this.tthd_id == 1;
        // this.button.tsbtnCapNhatDB = this.tthd_id == 2;
      }
    },
    getds_tienkhoanmuc_loaihd: async function (loaihd_id) {
      var input = {
        loaihd_id: Number(loaihd_id),
      };
      let data = this.GetData(
        await api.getds_tienkhoanmuc_loaihd(this.axios, input)
      );
      if (data && data.length > 0) {
        this.ds_tien_khoanmuc = data;
      } else {
        this.ds_tien_khoanmuc = [];
        this.$alert(
          "Không lấy được thông tin tiền khoản mục đặt cọc tham chiếu, hãy liên hệ admin để xử lý!",
          "",
          {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          }
        );
      }
    },
    getds_dvvt_lapmoi: async function () {
      let data = this.GetData(await api.getds_dvvt_lapmoi(this.axios));
      if (data && data.length > 0) {
        this.ds_dichvuvt = data.map((th) => ({
          id: th.dichvuvt_id,
          text: th.ten_dvvt,
        }));
      } else {
        this.ds_dichvuvt = [];
      }
    },
    getds_tocdo_loaidv: async function (dichvuvt_id) {
      var input = {
        dichvuvt_id: Number(dichvuvt_id),
      };
      let data = this.GetData(await api.getds_tocdo_loaidv(this.axios, input));
      if (data && data.length > 0) {
        this.ds_tocdo = data.map((th) => ({
          id: th.tocdo_id,
          text: th.tocdo,
        }));
        // this.leftForm.cboGoiCuocAdsl = this.ds_tocdo[0].id;
      } else {
        this.ds_tocdo = [];
        // this.leftForm.cboGoiCuocAdsl = 0;
      }
    },
    getds_loaihinhtb_loaidv: async function (dichvuvt_id) {
      var input = {
        dichvuvt_id: Number(dichvuvt_id),
      };
      let data = this.GetData(
        await api.getds_loaihinhtb_loaidv(this.axios, input)
      );
      if (data && data.length > 0) {
        this.ds_loaihinhtb = data.map((th) => ({
          id: th.loaitb_id,
          text: th.loaihinh_tb,
        }));
        // this.leftForm.cboLoaiHinhTB = this.ds_loaihinhtb[0].id;
      } else {
        this.ds_loaihinhtb = [];
        // this.leftForm.cboLoaiHinhTB = 0;
      }
    },
    getNhanvien_nvID: async function (nhanvien_id) {
      let data = this.GetData(
        await api.getNhanvien_nvID(this.axios, nhanvien_id)
      );
      if (data && data.length > 0) {
        return data[0].NAME;
      } else {
        return "";
      }
    },
    HT_DS_TraTruoc_Combobox: async function () {
      this.ds_tratruoc = [];
      var input = {
        vloaitb_id: this.loaitb_id,
        vkieuld_id: KieuLapDat.DATCOC_MOI,
        vphuongld_id: this.phuongld_id,
        vkhoanmuctt_id: KHOANMUC_TT.KMTT_DATMOI,
        vphold_id: this.phold_id,
        vapld_id: this.apld_id,
        vkhuld_id: this.khuld_id,
        vtrangbi_id: this.vtrangbi_id,
        vdoituong_id: this.doituong_id,
        vtocdo_id: this.vtocdo_id,
        vmuccuoc_id: this.vmuccuoc_id,
        vloaikm: 1,
      };
      let data = this.GetData(
        await api.getds_khuyenmai_hdtb(this.axios, input)
      );
      if (data && data.length > 0) {
        this.ds_tratruoc = data;
      }
    },
    LAY_DS_HOPDONG_THEO_MA_TB: async function (ma_tb, loaitb_id) {
      var input = {
        in_ma_tb: ma_tb,
        in_loaihd_id: LoaiHopDong.DATCOC_MOI,
        in_donvi_id: Number(this.$root.token.getDonViID()), //0, //Number(this.$root.token.getDonViID()),
        in_tthd_id: TrangThaiHD.MOI,
        in_nhanvien_id: Number(this.$root.token.getNhanVienID()), //0, //Number(this.$root.token.getNhanVienID()),
        in_donvi_dl_id: 0,
        in_dichvuvt_id: Number(this.cp_dichvuvt_id),
        in_loaitb_id: loaitb_id,
      };

      let data = this.GetData(
        await api.getds_hopdong_theo_ma_tb(this.axios, input)
      );
      if (!data || data.length == 0) {
        // this.ShowError("Không lấy được danh sách hợp đồng theo mã thuê bao!");
      }
      return data;
    },
    LAY_DS_DB_CNTT_THEO_TBID: async function (thuebao_id) {
      var input = {
        in_thuebao_id: thuebao_id,
      };
      let data = this.GetData(await api.getThongTinDBTBCNTT(this.axios, input));
      return data;
    },
    Lay_DS_HDTB_TT_THEO_HDKH: async function (hdkh_id) {
      var input = {
        vhdkh_id: Number(hdkh_id),
      };
      let data = this.GetData(
        await api.getds_hdtb_tt_theo_hdkh(this.axios, input)
      );
      if (!data || data.length == 0) {
        this.ShowError("Không lấy được danh sách HDTB TT theo HDKH!");
      }
      return data;
    },
    LAY_DANHBA_THEO_MATB: async function (ma_tb, dichvuvt_id) {
      var input = {
        in_ma_tb: ma_tb,
        in_dichvuvt_id: dichvuvt_id,
        in_donvi_dl_id: 0,
      };
      let data = this.GetData(await api.getds_db_matb(this.axios, input));
      if (!data || data.length == 0) {
        // this.ShowError("Không lấy được danh bạ theo mã thuê bao!");
      }
      return data;
    },
    LAY_DS_DIACHI_THEO_DBTBID: async function (thuebao_id) {
      var input = {
        in_thuebao_id: thuebao_id,
      };
      let data = this.GetData(await api.getDSDiaChi_DBTBID(this.axios, input));
      return data;
    },
    LAY_DS_HOPDONG_THEO_MA_GD: async function (ma_tb) {
      var input = {
        in_ma_gd: ma_tb,
        in_loaihd_id: LoaiHopDong.DATCOC_MOI,
        in_donvi_id: Number(this.$root.token.getDonViID()), //0, //Number(this.$root.token.getDonViID()),
        in_tthd_id: 0,
        in_nhanvien_id: Number(this.$root.token.getNhanVienID()), //0, //Number(this.$root.token.getNhanVienID()),
        in_donvi_dl_id: 0,
      };
      let data = this.GetData(await api.getds_hopdong_magd(this.axios, input));
      return data;
    },
    LAY_CHITIET_DC_THEO_HDTB_ID: async function (hdtb_id) {
      var input = {
        vhdtb_id: hdtb_id,
        vloai_km: 1,
      };
      let data = this.GetData(
        await api.getchitiet_dc_hdtb_id(this.axios, input)
      );
      if (data && data.length > 0) {
        for (var i = 0; i < data.length; i++) {
          data[i]["cuoc_sd"] =
            data[i]["cuoc_sd"] == null ? 0 : data[i]["cuoc_sd"];
          data[i]["cuoc_tb"] =
            data[i]["cuoc_tb"] == null ? 0 : data[i]["cuoc_tb"];
          data[i]["tien_ck"] =
            data[i]["tien_ck"] == null ? 0 : data[i]["tien_ck"];
          data[i]["tien_kmld"] =
            data[i]["tien_kmld"] == null ? 0 : data[i]["tien_kmld"];
          data[i]["tien_sd"] =
            data[i]["tien_sd"] == null ? 0 : data[i]["tien_sd"];
          data[i]["tien_tb"] =
            data[i]["tien_tb"] == null ? 0 : data[i]["tien_tb"];
          data[i]["tien_td"] =
            data[i]["tien_td"] == null ? 0 : data[i]["tien_td"];
          data[i]["tien_tg"] =
            data[i]["tien_tg"] == null ? 0 : data[i]["tien_tg"];
          data[i]["tyle_cuoc_ip"] =
            data[i]["tyle_cuoc_ip"] == null ? 0 : data[i]["tyle_cuoc_ip"];
          data[i]["tyle_giam_email"] =
            data[i]["tyle_giam_email"] == null ? 0 : data[i]["tyle_giam_email"];
          data[i]["tyle_kmld"] =
            data[i]["tyle_kmld"] == null ? 0 : data[i]["tyle_kmld"];
          data[i]["tyle_sd"] =
            data[i]["tyle_sd"] == null ? 0 : data[i]["tyle_sd"];
          data[i]["tyle_tb"] =
            data[i]["tyle_tb"] == null ? 0 : data[i]["tyle_tb"];
          data[i]["tyle_vat"] =
            data[i]["tyle_vat"] == null ? 0 : data[i]["tyle_vat"];
          data[i]["tyle_vat_id"] =
            data[i]["tyle_vat_id"] == null ? 0 : data[i]["tyle_vat_id"];
          data[i]["vat_kmld"] =
            data[i]["vat_kmld"] == null ? 0 : data[i]["vat_kmld"];
        }
      }
      return data;
    },
    LAY_TIENHD_KM_THEO_HDTB: async function (hdtb_id) {
      var input = {
        vhdtb_id: hdtb_id,
      };
      let data = this.GetData(
        await api.gettienhd_km_theo_hdtb(this.axios, input)
      );
      return data;
    },
    LAY_DS_HDTB_CNTT_THEO_HDTB_ID: async function (hdtb_id) {
      var input = {
        in_hdtb_id: hdtb_id,
      };
      let data = this.GetData(await api.getThongTinHDTBCNTT(this.axios, input));
      return data;
    },
    trangthai_hd: async function () {
      let data = this.GetData(await api.getds_trangthaihd(this.axios));
      if (data && data.length > 0) {
        var hd = data.filter((e) => e.ID == this.tthd_id);
        if (hd.length > 0) {
          return hd[0].NAME;
        } else {
          return "";
        }
      }
    },
    kiemtra_dk_goidadv: async function () {
      try {
        let response = await api.kiemtra_dk_goidadv(
          this.axios,
          Number(this.hdkh_id)
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (response.data.data == "1") return true;
          else {
            this.ShowError(
              "Khuyến mại yêu cầu có gói kèm theo, bạn hãy thêm thuê bao vào gói!"
            );
            return false;
          }
        } else {
          this.ShowError(
            "Lỗi kiểm tra đăng ký gói đa dịch vụ " +
              response.data.message_detail
          );
        }
      } catch (e) {}
    },
    XOA_HDKH_V2: async function (hdkh_id, loaihd_id) {
      try {
        var input = {
          vhdkh_id: Number(hdkh_id),
          vloaihd_id: loaihd_id,
        };
        let response = await api.xoa_hdkh_v2(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.ShowSuccess("Xóa hợp đồng khách hàng thành công!");

          this.isMaTb_enter = false;
          this.gridDanhSach.list = [];
          this.SetButton(0);
        } else {
          this.ShowError("Xóa hợp đồng khách hàng không thành công!");
        }
      } catch (e) {
        if (e.data && e.data.message_detail)
          this.ShowError(
            "Lỗi xóa hợp đồng khách hàng " + e.data.message_detail
          );
        else this.ShowError("Lỗi xóa hợp đồng khách hàng " + e);
      }
    },
    xoa_hdtb: async function (hdtb_id) {
      try {
        var input = {
          vhdtb_id: hdtb_id,
        };
        let response = await api.xoa_hdtb(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.ShowSuccess(
            "Xóa thông tin thuê bao khỏi hợp đồng đặt cọc thành công!"
          );
          await this.HienThiDanhSacHDTB();
        } else {
          this.ShowError(
            "Có lỗi khi xóa thông tin thuê bao: " + response.data.message
          );
        }
      } catch (e) {
        if (e.data && e.data.message_detail)
          this.ShowError(
            "Lỗi xóa thông tin thuê bao: " + e.data.message_detail
          );
        else this.ShowError("Lỗi xóa thông tin thuê bao: " + e);
      }
    },
    fn_tsbtncapnhatdb_frmthuebao_datcoc: async function (tthd) {
      var params = {
        TTHD_ID: tthd.tthd_id,
        HDKH_ID: tthd.hdkh_id,
        LOAITB_ID: tthd.loaitb_id,
        KICHHOAT: tthd.kichhoat,
        HDTB_ID: tthd.hdtb_id,
      };
      var input = {
        ds_para: JSON.stringify(params),
      };
      let response = await api.fn_tsbtncapnhatdb_frmthuebao_datcoc(
        this.axios,
        input
      );
      if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000000"
      ) {
        var data = JSON.parse(response.data.data);
        if (data.ERROR_CODE == 1) {
          if (tthd.kichhoat == 1) {
            this.ShowSuccess("Cập nhật dữ liệu thành công!");
            this.gridDanhSach.list = [];
            this.SetButton(0);
            return null;
          }
          return data.RESULT;
        } else {
          this.ShowError(data.MESSAGE);
          return null;
        }
      } else {
        this.ShowError("Có lỗi: " + response.data.message_detail);
        return null;
      }
    },
    kiemtra_km_2731: async function (ctkm_id, hdtb_id, kieu, ma_tb) {
      var input = {
        ctkm_id: ctkm_id,
        hdtb_id: hdtb_id,
        kieu: kieu,
        ma_tb: ma_tb,
      };
      let response = await api.kiemtra_km_2731(this.axios, input);
      if (
        response.data.error == "200" &&
        response.data.error_code == "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        this.ShowError("Có lỗi: " + response.data.message_detail);
        return null;
      }
    },
    STB_PROM_Register: async function (
      user_name,
      stb_serial,
      fiber_tv,
      stb_type,
      program,
      note
    ) {
      var input = {
        stbDTO: {
          fiber_MYTV: fiber_tv,
          note: note,
          program: program,
          stb_SERIAL: stb_serial,
          stb_TYPE: stb_type,
          username: user_name,
        },
      };
      let response = await api.STB_PROM_Register(this.axios, input);
      if (
        response.data.error == "200" &&
        response.data.error_code == "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        this.ShowError("Có lỗi: " + response.data.message_detail);
        return null;
      }
    },
    STB_PROM_Active: async function (
      user_name,
      stb_serial,
      stb_type,
      date,
      note,
      visfirst,
      program
    ) {
      var input = {
        stbDTO: {
          is_FIRST: visfirst,
          note: note,
          program: program,
          stb_DATE: date,
          stb_SERIAL: stb_serial,
          stb_TYPE: stb_type,
          username: user_name,
        },
      };
      let response = await api.STB_PROM_Active(this.axios, input);
      if (
        response.data.error == "200" &&
        response.data.error_code == "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        this.ShowError("Có lỗi: " + response.data.message);
        return null;
      }
    },
    log_action: async function (
      vuser_name,
      vcomputer_name,
      vaction_name,
      vkey_parameter,
      vserver_name,
      vnote
    ) {
      try {
        var input = {
          action_name: vaction_name,
          computer_name: vcomputer_name,
          key_parameter: vkey_parameter,
          note: vnote,
          server_name: vserver_name,
          user_name: vuser_name,
        };
        let response = await api.log_action(this.axios, input);
        if (
          response.data.error == "200" &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.ShowSuccess("Ghi log thành công!");
        } else {
          this.ShowError("Có lỗi: " + response.data.message);
        }
      } catch (e) {
        if (e.data && e.data.message_detail)
          this.ShowError("Có lỗi: " + e.data.message_detail);
        else this.ShowError("Có lỗi: " + e);
      }
    },
    HOANCONG_STB_2515: async function (
      vuser_name,
      stb_serial,
      stb_type,
      date,
      note,
      visfirst
    ) {
      // HOÀN CÔNG STB lên VASC
      try {
        var _result_STB = -99;

        _result_STB = await this.STB_PROM_Active(
          vuser_name,
          stb_serial,
          stb_type,
          date,
          "BUY->KM2731",
          visfirst,
          "2731"
        );

        if (_result_STB == 1) {
          // Kiểm tra xem có cập nhật được không

          //Log thành công
          var vuser_name = await this.$root.token.getMaNhanVien();
          var vcomputer_name = await this.$root.token.getMachine();
          var vaction_name = "frmThueBao_DatCoc";
          var vkey_parameter =
            "ma_tb=" +
            vuser_name +
            ",stb_serial=" +
            stb_serial +
            ",date=" +
            date +
            ",stb_type=" +
            stb_type +
            ",2731,KQ=" +
            _result_STB;
          var vserver_name = await this.$root.token.getIP();
          var vnote = "Ln:1770: Hoàn công và lưu dl dưới tỉnh thành công";
          await this.log_action(
            vuser_name,
            vcomputer_name,
            vaction_name,
            vkey_parameter,
            vserver_name,
            vnote
          );
          return "1";
        } else if (_result_STB == -4) {
          //Log thành công
          var vuser_name = await this.$root.token.getMaNhanVien();
          var vcomputer_name = await this.$root.token.getMachine();
          var vaction_name = "frmThueBao_DatCoc";
          var vkey_parameter =
            "ma_tb=" +
            vuser_name +
            ",stb_serial=" +
            stb_serial +
            ",date=" +
            date +
            ",stb_type=" +
            stb_type +
            ",2731,KQ=" +
            _result_STB;
          var vserver_name = await this.$root.token.getIP();
          var vnote = "Ln:1770: Hoàn công và lưu dl dưới tỉnh thành công";
          await this.log_action(
            vuser_name,
            vcomputer_name,
            vaction_name,
            vkey_parameter,
            vserver_name,
            vnote
          );
          return "-4";
        } else {
          //Log thất bại
          var vuser_name = await this.$root.token.getMaNhanVien();
          var vcomputer_name = await this.$root.token.getMachine();
          var vaction_name = "frmThueBao_DatCoc";
          var vkey_parameter =
            "ma_tb=" +
            vuser_name +
            ",stb_serial=" +
            stb_serial +
            ",date=" +
            date +
            ",stb_type=" +
            stb_type +
            ",2731,KQ=" +
            _result_STB;
          var vserver_name = await this.$root.token.getIP();
          var vnote = "Ln:1824: hoàn công thất bại";
          await this.log_action(
            vuser_name,
            vcomputer_name,
            vaction_name,
            vkey_parameter,
            vserver_name,
            vnote
          );
          return "0";
        }
      } catch (ex) {
        //Log thất bại
        var vuser_name = await this.$root.token.getMaNhanVien();
        var vcomputer_name = await this.$root.token.getMachine();
        var vaction_name = "frmThueBao_DatCoc";
        var vkey_parameter =
          "ma_tb=" +
          vuser_name +
          ",stb_serial=" +
          stb_serial +
          ",date=" +
          date +
          ",stb_type=" +
          stb_type +
          ",KQ=0";
        var vserver_name = await this.$root.token.getIP();
        var vnote = "Ln:1842: Hoàn công thất bại";
        await this.log_action(
          vuser_name,
          vcomputer_name,
          vaction_name,
          vkey_parameter,
          vserver_name,
          vnote
        );
        if (ex.data && ex.data.message_detail)
          this.ShowError(
            "Hoàn công STB tham gia chương trình thất bại " +
              ex.data.message_detail
          );
        else
          this.ShowError("Hoàn công STB tham gia chương trình thất bại " + ex);
        return "0";
      }
    },
    KICHHOAT: async function (user_name, stb_serial, fiber_tv, stb_type) {
      //ĐĂNG KÝ STB lên VASC
      var loi = "NOTOK";
      var current_date = moment(new Date()).format("YYYYMMDD");
      try {
        var _re = await this.STB_PROM_Register(
          user_name,
          stb_serial,
          fiber_tv,
          stb_type,
          "2731",
          "BUY->KM2731"
        );
        if (_re == 1) {
          // Update ngay sync=1
          // Kiểm tra xem có cập nhật được không
          // Log thành công
          var vuser_name = await this.$root.token.getMaNhanVien();
          var vcomputer_name = await this.$root.token.getMachine();
          var vaction_name = "frmThueBao_DatCoc";
          var vkey_parameter =
            "ma_tb=" +
            user_name +
            ",stb_serial=" +
            stb_serial +
            ",fiber_mytv=" +
            fiber_tv +
            ",stb_type=" +
            stb_type +
            ",2731,KQ=" +
            _re;
          var vserver_name = await this.$root.token.getIP();
          var vnote = "Ln:6312:Kich hoạt cập nhật dl dưới tỉnh thành công";
          await this.log_action(
            vuser_name,
            vcomputer_name,
            vaction_name,
            vkey_parameter,
            vserver_name,
            vnote
          );
          var k_ac = this.HOANCONG_STB_2515(
            user_name,
            stb_serial,
            stb_type,
            current_date,
            "BUY->KM2731",
            true
          );
          if (k_ac == "1") {
            loi = "OK";
            // return "1";
            return loi;
          } else if (k_ac == "-4") {
            loi = "BOQUA";
            // return "1";
            return loi;
          }
          // Lỗi thì văng ra
          else return "-1";
        } else if (_re == -4) {
          var k_ac = HOANCONG_STB_2515(
            user_name,
            stb_serial,
            stb_type,
            current_date,
            "BUY->KM2731",
            true
          );
          if (k_ac == "1") {
            loi = "OK";
            // return "1";
            return loi;
          } else if (k_ac == "-4") {
            loi = "BOQUA";
            // return "1";
            return loi;
          } else {
            loi = "BOQUA";
            // return "1";
            return loi;
          }
        } else {
          //Log thất bại
          var vuser_name = await this.$root.token.getMaNhanVien();
          var vcomputer_name = await this.$root.token.getMachine();
          var vaction_name = "frmThueBao_DatCoc";
          var vkey_parameter =
            "ma_tb=" +
            user_name +
            ",stb_serial=" +
            stb_serial +
            ",fiber_mytv=" +
            fiber_tv +
            ",stb_type=" +
            stb_type +
            ",2731,KQ=" +
            _re;
          var vserver_name = await this.$root.token.getIP();
          var vnote = "Ln:6284: Bỏ qua kích hoạt";
          await this.log_action(
            vuser_name,
            vcomputer_name,
            vaction_name,
            vkey_parameter,
            vserver_name,
            vnote
          );
          return loi;
        }
      } catch (e) {}
    },
    KICHHOAT_HOANTHIEN_2731: async function (vctkm_id) {
      try {
        try {
          var in_ma_tb =
            this.gridDanhSach.list.length > 0
              ? this.gridDanhSach.list[0].ma_tb
              : "";
          var promise_kt2731 = this.kiemtra_km_2731(
            "1",
            vctkm_id.toString(),
            this.hdtb_id.toString(),
            in_ma_tb.trim()
          );
          var ckb_km = await promise_kt2731;
          if (!ckb_km || ckb_km == null) {
            this.ShowError("Không lấy được thông tin kiểm tra đặt cọc");
            return "Không lấy được thông tin kiểm tra đặt cọc";
          }

          if (ckb_km.toString() == "1" || ckb_km.toString() == "-1") {
            return ckb_km.toString();
          }

          if (
            ckb_km.toString() != "1" &&
            ckb_km.toString() != "-1" &&
            !ckb_km.toString().includes("OK-") &&
            !ckb_km.toString().includes("NOTHING-")
          ) {
            this.ShowError("Lỗi khi kiểm tra " + ckb_km);
            return ckb_km.toString();
          }

          if (
            ckb_km.toString().includes("OK-") ||
            ckb_km.toString().includes("NOTHING-")
          ) {
            //Nếu khác stb type
            var user_name = in_ma_tb; // tài khoản Mytv
            var date = moment(new Date()).format("YYYYMMDD");
            var program_ = "2731";
            var note = "KM sau BUY";
            var stb_serial = ckb_km.toString().split("-")[2].toString();
            var stb_type = ckb_km.toString().split("-")[1].toString();
            var fiber_tv = ckb_km.toString().split("-")[3].toString();
            var loi = await this.KICHHOAT(
              user_name,
              stb_serial,
              fiber_tv,
              stb_type
            );
            if (loi == "OK" || loi == "BOQUA") {
              //Log thành công
              try {
                var vuser_name = await this.$root.token.getMaNhanVien();
                var vcomputer_name = await this.$root.token.getMachine();
                var vaction_name = "frmThueBao_DatCoc";
                var vkey_parameter =
                  "ma_tb=" +
                  user_name +
                  ",program=2731" +
                  ", ngaynt=" +
                  date +
                  ",KQ=OK";
                var vserver_name = await this.$root.token.getIP();
                var vnote =
                  "Ln:6312:Kich hoạt cập nhật dl dưới tỉnh thành công";
                await this.log_action(
                  vuser_name,
                  vcomputer_name,
                  vaction_name,
                  vkey_parameter,
                  vserver_name,
                  vnote
                );
              } catch (Exception) {}
              return "1";
            }
            return "-2";
          }
          return "-2";
        } catch (ex) {
          if (ex.data && ex.data.message_detail)
            this.ShowError(
              "Có lỗi khi kiểm tra đặt cọc của thuê bao " +
                ex.data.message_detail
            );
          else this.ShowError("Có lỗi khi kiểm tra đặt cọc của thuê bao " + ex);
          return (
            "[ERROR] Có lỗi khi kiểm tra đặt cọc của thuê bao " +
            ex.data.message_detail
          );
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.ShowError("Có lỗi: " + ex.data.message_detail);
          return "[ERROR] Có lỗi " + ex.data.message_detail;
        } else {
          this.ShowError("Có lỗi: " + ex);
          return "[ERROR] Có lỗi " + ex;
        }
      }
    },
    onChangeDVVT: async function (value) {
      this.loading(true);
      try {
        await Promise.all([
          this.getds_loaihinhtb_loaidv(value),
          this.getds_tocdo_loaidv(value),
        ]);
        if (this.ds_loaihinhtb && this.ds_loaihinhtb.length > 0) {
          var filter = this.ds_loaihinhtb.filter(
            (e) => e.id == Number(this.leftForm.cboLoaiHinhTB)
          );
          if (filter.length <= 0)
            this.leftForm.cboLoaiHinhTB = this.ds_loaihinhtb[0].id;
        }
        if (this.ds_tocdo && this.ds_tocdo.length > 0) {
          var filter = this.ds_tocdo.filter(
            (e) => e.id == Number(this.leftForm.cboGoiCuocAdsl)
          );
          if (filter.length <= 0)
            this.leftForm.cboGoiCuocAdsl = this.ds_tocdo[0].id;
        }
        if (
          Number(this.leftForm.cboDichVuVT) == DichVuVienThong.DICHVU_CNTT ||
          Number(this.leftForm.cboDichVuVT) == DichVuVienThong.ANTOAN_BAOMAT_TT ||
          Number(this.leftForm.cboDichVuVT) == DichVuVienThong.TRUNGTAM_DULIEU ||
          Number(this.leftForm.cboDichVuVT) == DichVuVienThong.HOINGHI_TRUYENHINH
        ) {
          this.chkThoiHan.Checked = true
          this.chkThoiHan.Enabled = true
        }
      } catch (e) {
        this.loading(false);
      }

      this.loading(false);
    },
    onChangeLoaiHinhTB: async function () {
      if (
        Number(this.leftForm.cboDichVuVT) == DichVuVienThong.DICHVU_CNTT ||
        Number(this.leftForm.cboDichVuVT) == DichVuVienThong.ANTOAN_BAOMAT_TT ||
        Number(this.leftForm.cboDichVuVT) == DichVuVienThong.TRUNGTAM_DULIEU ||
        Number(this.leftForm.cboDichVuVT) == DichVuVienThong.HOINGHI_TRUYENHINH
      ) {
        this.chkThoiHan.Checked = true
        this.chkThoiHan.Enabled = true
      } else {
        this.chkThoiHan.Enabled = false;
        this.chkThoiHan.Checked = false;
      }
      
      if (this.phainhap_thoihan) {
        var input = {
          type: 6,
          param: this.leftForm.cboLoaiHinhTB.toString(),
        };
        let tt = this.GetData(await api.fn_tt_loaihinh_tb(this.axios, input));
        if (tt && tt.toString() == "1" && this.phainhap_thoihan)
          this.chkThoiHan.Enabled = true;
      }
    },
    onChangeNgayYC(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.leftForm.dtpNgayYeuCau = "01/01/2000";
      } else {
        this.leftForm.dtpNgayYeuCau = value_;
      }
    },
    onChangeNgayBD(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.cp_tungay = "01/01/2000";
      } else {
        this.cp_tungay = value_;
      }
    },
    onChangeTraTruoc: function () {
      this.rightForm.txtTongTien = 0;
      this.rightForm.txtTongVat = 0;
      this.rightForm.txtSumTien = 0;
    },
    acceptThueBaoCungMa: async function (tb) {
      this.thuebao_id = Number(tb["thuebao_id"]);
      this.ma_kh = tb["ma_kh"].toString();
      if (tb["ngaylap_hd"] && tb["ngaylap_hd"].toString().trim() != "")
        this.dtpNgayLHD = tb["ngaylap_hd"];
      else this.dtpNgayLHD = moment(new Date()).format("DD/MM/YYYY");
      tb["khachhang_id"] && tb["khachhang_id"] != null
        ? (this.khachhang_id = Number(tb["khachhang_id"].toString()))
        : (this.khachhang_id = 0);
      this.ten_kh = tb["ten_kh"]
        ? this.ChuanHoaTen(tb["ten_kh"].toString())
        : "";
      this.diachi_kh = tb["diachi_kh"]
        ? this.ChuanHoaTen(tb["diachi_kh"].toString())
        : "";
      this.leftForm.txtTenTB = tb["ten_tb"]
        ? this.ChuanHoaTen(tb["ten_tb"].toString())
        : "";
      this.leftForm.txtDiaChiLD = tb["diachi_ld"]
        ? this.ChuanHoaTen(tb["diachi_ld"].toString())
        : "";
      this.leftForm.txtMaTB = tb["ma_tb"].toString();
      this.ten_tb = this.ChuanHoaTen(tb["ten_tb"].toString());
      this.diachi_ld = tb["diachi_ld"]
        ? this.ChuanHoaTen(tb["diachi_ld"].toString())
        : "";
      this.loaitb_id = Number(tb["loaitb_id"].toString());
      this.leftForm.cboLoaiHinhTB = this.loaitb_id;
      this.donvi_id = tb["donviql_id"] ? Number(tb["donviql_id"]) : 0;
      this.doituong_id = tb["doituong_id"] ? Number(tb["doituong_id"]) : 0;
      this.khlon_id = tb["khlon_id"] ? Number(tb["khlon_id"]) : 0;

      var promiseDCLD = this.LAY_DS_DIACHI_THEO_DBTBID(this.thuebao_id);
      var ds_dcld = await promiseDCLD;
      if (ds_dcld && ds_dcld.length > 0) {
        if (
          ds_dcld[0]["phuongld_id"] &&
          ds_dcld[0]["phuongld_id"].toString() != ""
        )
          this.phuongld_id = Number(ds_dcld[0]["phuongld_id"].toString());
        else this.phuongld_id = 0;
        if (ds_dcld[0]["phold_id"] && ds_dcld[0]["phold_id"].toString() != "")
          this.phold_id = Number(ds_dcld[0]["phold_id"].toString());
        else this.phold_id = 0;
        if (ds_dcld[0]["apld_id"] && ds_dcld[0]["apld_id"].toString() != "")
          this.apld_id = Number(ds_dcld[0]["apld_id"].toString());
        else this.apld_id = 0;
        if (ds_dcld[0]["khuld_id"] && ds_dcld[0]["khuld_id"].toString() != "")
          this.khuld_id = Number(ds_dcld[0]["khuld_id"].toString());
        else this.khuld_id = 0;
      }

      await this.HienThiTTMoRong_DB(this.thuebao_id, this.dichvuvt_id);
      if (
        this.dichvuvt_id == DichVuVienThong.ADSL ||
        this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
        this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT ||
        this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU ||
        this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH
      )
        this.leftForm.cboGoiCuocAdsl = this.vtocdo_id;
      await this.HT_DS_TraTruoc_Combobox();

      // if (this.khong_ht_tracuudb == 0) {
      //   this.ShowPopupSearchAccount();
      // }
      this.hdtb_id = 0;
      this.$refs.danhSachThueBaoCungMaModal.hideModal();
    },
    HienThiTTHopDongKH: async function (ds) {
      if (ds && ds.length > 0) {
        if (ds[0]["hdkh_id"]) {
          this.hdkh_id = Number(ds[0]["hdkh_id"]);
        } else {
          this.hdkh_id = 0;
          this.ShowError("HDKH_ID is null");
        }

        this.leftForm.txtMaGD = ds[0]["ma_gd"] ? ds[0]["ma_gd"].toString() : "";
        this.ma_kh = ds[0]["ma_kh"] ? ds[0]["ma_kh"].toString() : "";

        ds[0]["khachhang_id"] && ds[0]["khachhang_id"] != null
          ? (this.khachhang_id = Number(ds[0]["khachhang_id"]))
          : (this.khachhang_id = 0);

        this.khlon_id = ds[0]["khlon_id"]
          ? Number(ds[0]["khlon_id"].toString())
          : 0;
        this.leftForm.txtTenTB = ds[0]["ten_tb"]
          ? this.ChuanHoaTen(ds[0]["ten_tb"].toString())
          : "";
        this.leftForm.txtDiaChiLD = ds[0]["diachi_ld"]
          ? this.ChuanHoaTen(ds[0]["diachi_ld"].toString())
          : "";
        this.ten_kh = ds[0]["ten_kh"]
          ? this.ChuanHoaTen(ds[0]["ten_kh"].toString())
          : "";
        this.diachi_kh = ds[0]["diachi_kh"]
          ? this.ChuanHoaTen(ds[0]["diachi_kh"].toString())
          : "";
        if (ds[0]["ngay_yc"] && ds[0]["ngay_yc"].toString() != "")
          this.leftForm.dtpNgayYeuCau =
            this.keyenter == 0
              ? moment(ds[0]["ngay_yc"]).format("DD/MM/YYYY")
              : moment(ds[0]["ngay_yc"], "DD/MM/YYYY HH:mm:ss").format(
                  "DD/MM/YYYY"
                );
        this.rightForm.txtGhiChu = ds[0]["ghichu"]
          ? this.ChuanHoaTen(ds[0]["ghichu"].toString())
          : "";

        if (ds && ds.length > 0 && ds[0]["ctv_id"] && ds[0]["ctv_id"] !== 0) {
          this.filterbox.ctv.chkCTV = true;
          this.filterbox.ctv.ctv_id = Number(ds[0]["ctv_id"]);
          var promiseCTV = this.getNhanvien_nvID(this.filterbox.ctv.ctv_id);
          this.filterbox.ctv.txtCTV = await promiseCTV;
        } else {
          this.filterbox.ctv.txtCTV = "";
          this.filterbox.ctv.chkCTV = false;
        }

        if (
          ds &&
          ds.length > 0 &&
          ds[0]["nhanviengt_id"] &&
          ds[0]["nhanviengt_id"] != 0
        ) {
          this.filterbox.nguoigt.chkNguoiGT = true;
          this.filterbox.nguoigt.nguoigt_id = Number(ds[0]["nhanviengt_id"]);
          var promiseNGT = this.getNhanvien_nvID(
            this.filterbox.nguoigt.nguoigt_id
          );
          this.filterbox.nguoigt.txtNguoiGT = await promiseNGT;
        } else {
          this.filterbox.nguoigt.txtNguoiGT = "";
          this.filterbox.nguoigt.chkNguoiGT = false;
        }

        this.SetButton(3);
        await this.HienThiDanhSacHDTB();
      } else {
        this.gridDanhSach.list = [];
        this.SetButton(0);
      }
    },
    HienThiDanhSacHDTB: async function () {
      var promiseHDTB = this.Lay_DS_HDTB_TT_THEO_HDKH(Number(this.hdkh_id));
      var dtList = await promiseHDTB;
      if (dtList && dtList.length > 0) {
        this.gridDanhSach.list = dtList;
      } else {
        this.gridDanhSach.list = [];
        this.SetButton(1);
      }

      //Hiện nút thêm và xóa thuê bao
      this.button.tsbtnThemTB = dtList && dtList.length > 0 ? true : false;
      this.button.tsbtnXoaTB = dtList && dtList.length > 1 ? true : false;
    },
    HienThiTT_DanhBa: async function (ds) {
      this.dsTBCungMa = [];
      if (ds.length > 0) {
        if (ds.length > 1) {
          this.dsTBCungMa = ds;
          this.$refs.danhSachThueBaoCungMaModal.showModal();
        } else {
          this.thuebao_id = Number(ds[0]["thuebao_id"]);
          this.ma_kh = ds[0]["ma_kh"].toString();
          if (
            ds[0]["ngaylap_hd"] &&
            ds[0]["ngaylap_hd"].toString().trim() != ""
          )
            this.dtpNgayLHD = ds[0]["ngaylap_hd"];
          else this.dtpNgayLHD = moment(new Date()).format("DD/MM/YYYY");
          if (ds[0]["khachhang_id"] && ds[0]["khachhang_id"] != null) {
            this.khachhang_id = Number(ds[0]["khachhang_id"].toString());
          } else {
            this.khachhang_id = 0;
          }

          this.ten_kh = this.ChuanHoaTen(ds[0]["ten_kh"].toString());
          this.diachi_kh = ds[0]["diachi_kh"]
            ? this.ChuanHoaTen(ds[0]["diachi_kh"].toString())
            : "";
          this.leftForm.txtTenTB = this.ChuanHoaTen(ds[0]["ten_tb"].toString());
          this.leftForm.txtDiaChiLD = ds[0]["diachi_ld"]
            ? this.ChuanHoaTen(ds[0]["diachi_ld"].toString())
            : "";
          if (ds[0]["ma_tb"]) {
            this.leftForm.txtMaTB = ds[0]["ma_tb"];
          } else {
            this.ShowError("Không tìm thấy ma_tb");
          }
          this.ten_tb = ds[0]["ten_tb"]
            ? this.ChuanHoaTen(ds[0]["ten_tb"].toString())
            : "";
          this.diachi_ld = ds[0]["diachi_ld"]
            ? this.ChuanHoaTen(ds[0]["diachi_ld"].toString())
            : "";

          this.donvi_id = ds[0]["donviql_id"] ? Number(ds[0]["donviql_id"]) : 0;
          this.doituong_id = ds[0]["doituong_id"]
            ? Number(ds[0]["doituong_id"])
            : 0;
          this.khlon_id = ds[0]["khlon_id"] ? Number(ds[0]["khlon_id"]) : 0;

          var promiseDCLD = this.LAY_DS_DIACHI_THEO_DBTBID(this.thuebao_id);
          var ds_dcld = await promiseDCLD;
          if (ds_dcld.length > 0) {
            if (
              ds_dcld[0]["phuongld_id"] &&
              ds_dcld[0]["phuongld_id"].toString() != ""
            )
              this.phuongld_id = Number(ds_dcld[0]["phuongld_id"].toString());
            else this.phuongld_id = 0;
            if (
              ds_dcld[0]["phold_id"] &&
              ds_dcld[0]["phold_id"].toString() != ""
            )
              this.phold_id = Number(ds_dcld[0]["phold_id"].toString());
            else this.phold_id = 0;
            if (ds_dcld[0]["apld_id"] && ds_dcld[0]["apld_id"].toString() != "")
              this.apld_id = Number(ds_dcld[0]["apld_id"].toString());
            else this.apld_id = 0;
            if (
              ds_dcld[0]["khuld_id"] &&
              ds_dcld[0]["khuld_id"].toString() != ""
            )
              this.khuld_id = Number(ds_dcld[0]["khuld_id"].toString());
            else this.khuld_id = 0;
          }

          await this.HienThiTTMoRong_DB(this.thuebao_id, this.dichvuvt_id);
          if (
            this.dichvuvt_id == DichVuVienThong.ADSL ||
            this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
            this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT ||
            this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU ||
            this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH
          )
            this.leftForm.cboGoiCuocAdsl = this.vtocdo_id;
          await this.HT_DS_TraTruoc_Combobox();

          // if (this.khong_ht_tracuudb == 0) {
          //   this.ShowPopupSearchAccount();
          // }
          this.hdtb_id = 0;
        }
      }
    },
    HienThiTTMoRong_DB: async function (thuebao_id, dichvuvt_id) {
      var input = {
        thuebao_id: thuebao_id,
        dichvuvt_id: dichvuvt_id,
      };
      let ds = this.GetData(
        await api.getdsdb_dichvu_thuebao(this.axios, input)
      );

      if (ds && ds.length > 0) {
        switch (dichvuvt_id) {
          case DichVuVienThong.CO_DINH:
            this.vtocdo_id = 0;
            this.vmuccuoc_id = 0;
            if (ds[0]["trangbi_id"])
              this.vtrangbi_id = Number(ds[0]["trangbi_id"]);
            else this.vtrangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI;
            break;
          case DichVuVienThong.DI_DONG:
            this.vmuccuoc_id = 0;
            if (ds[0]["trangbi_id"])
              this.vtrangbi_id = Number(ds[0]["trangbi_id"]);
            else this.vtrangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI;

            if (ds[0]["goicuoc_id"])
              this.vtocdo_id = Number(ds[0]["goicuoc_id"]);
            else this.vtocdo_id = 0;
            break;
          case DichVuVienThong.ADSL:
            if (ds[0]["trangbi_id"])
              this.vtrangbi_id = Number(ds[0]["trangbi_id"]);
            else this.vtrangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI;

            if (ds[0]["tocdo_id"]) this.vtocdo_id = Number(ds[0]["tocdo_id"]);
            else this.vtocdo_id = 0;

            if (ds[0]["muccuoc_id"])
              this.vmuccuoc_id = Number(ds[0]["muccuoc_id"]);
            else this.vmuccuoc_id = 0;
            break;
          case DichVuVienThong.MEGAWAN:
          case DichVuVienThong.METRONET:
            this.vmuccuoc_id = 0;
            if (ds[0]["trangbi_id"])
              this.vtrangbi_id = Number(ds[0]["trangbi_id"]);
            else this.vtrangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI;

            if (ds[0]["tocdo_id"]) this.vtocdo_id = Number(ds[0]["tocdo_id"]);
            else this.vtocdo_id = 0;
            break;
          case DichVuVienThong.TSL:
            this.vmuccuoc_id = 0;
            if (ds[0]["trangbi_id"])
              this.vtrangbi_id = Number(ds[0]["trangbi_id"]);
            else this.vtrangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI;

            if (ds[0]["tocdo_id"]) this.vtocdo_id = Number(ds[0]["tocdo_id"]);
            else this.vtocdo_id = 0;
            break;
          case DichVuVienThong.GPHONE:
            this.vtocdo_id = 0;
            this.vmuccuoc_id = 0;
            if (ds[0]["trangbi_id"])
              this.vtrangbi_id = Number(ds[0]["trangbi_id"]);
            else this.vtrangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI;
            break;
          case DichVuVienThong.IMS:
            this.vtocdo_id = 0;
            this.vmuccuoc_id = 0;
            if (ds[0]["trangbi_id"])
              this.vtrangbi_id = Number(ds[0]["trangbi_id"]);
            else this.vtrangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI;
            break;
          case DichVuVienThong.ANTOAN_BAOMAT_TT:
          case DichVuVienThong.DICHVU_CNTT:
          case DichVuVienThong.HOINGHI_TRUYENHINH:
          case DichVuVienThong.TRUNGTAM_DULIEU:
            this.vtrangbi_id = 0;
            this.vmuccuoc_id = 0;
            if (ds[0]["tocdo_id"]) this.vtocdo_id = Number(ds[0]["tocdo_id"]);
            else this.vtocdo_id = 0;
            break;
          default:
            break;
        }
      }
    },
    async ThemHDThueBao() {
      if (!this.isMaTb_enter && this.leftForm.txtMaTB != "") {
        this.ShowError(
          "Thuê bao đang có Hợp đồng/Phụ lục mới chưa hoàn thiện!"
        );
        return;
      }
      await this.TaoDL(true, true, true); //
      await this.update_thuebao_datcoc(
        [],
        this.dsHDTB,
        this.hdtb_dc_1,
        this.dsCTTHD,
        this.dsHDTB_CNTT,
        // this.huonggiaotn_id,
        1
      );
      // datcoc.ThemMoi_HopDong_V3(
      //   null,
      //   dsHDTB,
      //   hdtb_dc_1,
      //   dsCTTHD,
      //   dsHDTB_CNTT,
      //   huonggiaotn_id
      // );
      // await this.HienThiDanhSacHDTB();
    },
    txtMaTB_KeyPress: async function () {
      this.loading(true);
      try {
        this.isMaTb_enter = true;
        if (this.khong_load_ctv == 0) this.filterbox.ctv.chkCTV = false;
        this.filterbox.nguoigt.chkNguoiGT = false;
        if (this.leftForm.txtMaTB != "") {
          this.keyenter = 1;
          this.dichvuvt_id = Number(this.cp_dichvuvt_id);
          var promiseDB_matb = this.LAY_DANHBA_THEO_MATB(
            this.leftForm.txtMaTB,
            this.dichvuvt_id,
            0
          ); //Lấy ra bất kỳ thuê bao nào
          var dt = await promiseDB_matb;
          if (dt && dt.length > 0) {
            this.loaitb_id = dt[0]["loaitb_id"]
              ? Number(dt[0]["loaitb_id"].toString())
              : 0;
            this.leftForm.cboLoaiHinhTB = this.loaitb_id;

            var promiseDSHD = this.LAY_DS_HOPDONG_THEO_MA_TB(
              this.leftForm.txtMaTB,
              this.loaitb_id
            );
            var ds = await promiseDSHD;

            if (ds && ds.length > 0) {
              await this.HienThiTTHopDongKH(ds);
            } else {
              await this.HienThiTT_DanhBa(dt);
            }
          } else {
            var ten_dv = this.ds_dichvuvt.filter(
              (e) => e.id == Number(this.leftForm.cboDichVuVT)
            )[0].text;
            this.ShowError(
              "Không tìm thấy thông tin về mã thuê bao " +
                this.leftForm.txtMaTB +
                " đối với dịch vụ " +
                ten_dv
            );
          }
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail)
          this.ShowError("Có lỗi: " + ex.data.message_detail);
        else this.ShowError("Có lỗi: " + ex);
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    txtMaGD_KeyPress: async function () {
      this.loading(true);
      try {
        this.isMaTb_enter = false;
        if (this.leftForm.txtMaGD != "") {
          this.keyenter = 0;
          var promise_dshd = this.LAY_DS_HOPDONG_THEO_MA_GD(
            this.leftForm.txtMaGD
          );
          var ds = await promise_dshd;
          if (ds && ds.length > 0) await this.HienThiTTHopDongKH(ds);
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail)
          this.ShowError(
            "Lỗi tìm kiếm theo mã giao dịch: " + ex.data.message_detail
          );
        else this.ShowError("Lỗi tìm kiếm theo mã giao dịch " + ex);
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    tsbtnNhapMoi_Click: function () {
      this.isMaTb_enter = false;
      this.SetButton(1);
    },
    tsbtnGhiLai_Click: async function () {
      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn ghi lại dữ liệu không?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            await this.CapNhat();
            this.isMaTb_enter = false;
            this.loading(false);
          }
        })
        .catch((e) => {
          // if (e.data && e.data.message_detail)
          //   this.ShowError("Lỗi ghi lại dữ liệu: " + e.data.message_detail);
          // else this.ShowError("Lỗi ghi lại dữ liệu: " + e);
          this.ShowError("Lỗi ghi lại dữ liệu");
          this.loading(false);
        });
    },
    tsbtnXoa_Click: async function () {
      this.loading(true);
      try {
        //Xoa_datcoc();
        if (Number(this.tthd_id) > 1) {
          var promise_tthd = this.trangthai_hd();
          var tthd = await promise_tthd;
          this.ShowError("Hợp đồng " + tthd + " bạn không thể xóa!");
          return;
        }
        await this.XOA_HDKH_V2(Number(this.hdkh_id), LoaiHopDong.DATCOC_MOI);
      } catch (e) {
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    tsbtnHuyBo_Click: async function () {
      this.isMaTb_enter = false;
      this.SetButton(0);
    },
    tsbtnThanhToan_Click: async function () {
      try {
        if (this.tthd_id > 1) {
          var promise_tthd = this.trangthai_hd();
          var tthd = await promise_tthd;
          this.ShowError("Hợp đồng " + tthd + " !");
          return;
        }
        if (!(await this.kiemtra_dk_goidadv())) return;
        if (this.leftForm.txtMaGD != "") {
          // this.$router.push({
          //   name: "Payment",
          //   params: { magd: this.leftForm.txtMaGD },
          // });
          let query = {
            magd: this.leftForm.txtMaGD.trim(),
          };
          let routeData = this.$router.resolve({
            name: "Payment",
            query: query,
          });
          window.open(routeData.href, "_blank");
        } else {
          this.ShowError("Bạn chưa nhập mã giao dịch!");
          this.$refs.txtMaGD.focus();
        }
      } catch (e) {
        // if (e.data && e.data.message)
        //   this.ShowError("Lỗi kiểm tra hợp đồng thanh toán " + e.data.message);
        // else this.ShowError("Lỗi kiểm tra hợp đồng thanh toán ");
        console.log(e);
        this.ShowError("Lỗi kiểm tra hợp đồng thanh toán ");
      }
    },
    tsbtnThemTB_Click: async function () {
      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn thêm thuê bao không?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            await this.ThemHDThueBao();
            this.loading(false);
          }
        })
        .catch((e) => {
          // if (e.data && e.data.message_detail)
          //   this.ShowError("Lỗi thêm thuê bao: " + e.data.message_detail);
          // else this.ShowError("Lỗi thêm thuê bao: " + e);
          console.log(e);
          this.ShowError("Lỗi thêm thuê bao");
          this.loading(false);
        });
    },
    tsbtnXoaTB_Click: async function () {
      if (this.tthd_id > 1) {
        var promise_tthd = this.trangthai_hd();
        var tthd = await promise_tthd;
        this.ShowError("Hợp đồng " + tthd + " bạn không thể xóa!");
        return;
      }
      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn xóa dữ liệu không?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            await this.xoa_hdtb(this.hdtb_id);

            this.loading(false);
          }
        })
        .catch((e) => {
          // if (e.data && e.data.message_detail)
          //   this.ShowError("Lỗi xóa dữ liệu: " + e.data.message_detail);
          // else this.ShowError("Lỗi xóa dữ liệu: " + e);
          console.log(e);
          this.ShowError("Lỗi xóa dữ liệu");
          this.loading(false);
        });
    },
    tsbtnCapNhatDB_Click: async function () {
      this.loading(true);
      try {
        if (this.leftForm.txtMaGD == "") {
          this.ShowError("Bạn chưa nhập mã giao dịch!");
          this.$refs.txtMaGD.focus();
          this.loading(false);
          return;
        }
        if (this.tthd_id != 2) {
          var promise_tthd = this.trangthai_hd();
          var tthd = await promise_tthd;
          this.ShowError("Hợp đồng " + tthd + " không thể hoàn thiện!");
          this.loading(false);
          return;
        }
        var thongtinHD = {
          tthd_id: this.tthd_id,
          hdkh_id: Number(this.hdkh_id),
          loaitb_id: this.loaitb_id,
          kichhoat: Number(this.loaitb_id) == LoaiHinhTB.INTERNET_MYTV ? 2 : 1, // API chua call WS vasc
          hdtb_id: Number(this.hdtb_id),
        };
        var promise_capnhat_daccoc =
          this.fn_tsbtncapnhatdb_frmthuebao_datcoc(thongtinHD);
        var ds_ctkm = await promise_capnhat_daccoc;
        if (ds_ctkm) {
          if (
            this.leftForm.cboLoaiHinhTB.toString() ==
            LoaiHinhTB.INTERNET_MYTV.toString()
          ) {
            if (ds_ctkm.length > 0) {
              for (var i = 0; i < ds_ctkm.length; i++) {
                var kq = await this.KICHHOAT_HOANTHIEN_2731(
                  Number(ds_ctkm[i]["chitietkm_id"].toString())
                );
                if (kq != "1" && kq != "-1") {
                  this.loading(false);
                  return;
                }
              }
            }
          }
          thongtinHD.kichhoat = 1; // API da call WS vasc
          var promise_hoanthien_hs_datcoc =
            this.fn_tsbtncapnhatdb_frmthuebao_datcoc(thongtinHD);
          var result = await promise_hoanthien_hs_datcoc;
          if (result == "done") {
            this.loading(false);
            return;
          }
        }
      } catch (ex) {
        // if (ex.data && ex.data.message_detail)
        //   this.ShowError(
        //     "Lỗi hoàn thiện thuê bao đặt cọc: " + ex.data.message_detail
        //   );
        // else this.ShowError("Lỗi hoàn thiện thuê bao đặt cọc: " + ex);
        console.log(ex);
        this.ShowError("Lỗi hoàn thiện thuê bao đặt cọc");
        this.loading(false);
      }
      this.loading(false);
    },
    tsbtnThongTinLH_Click: function () {
      if (this.hdkh_id && Number(this.hdkh_id) != 0) {
        // WinUI.WinUILapHopDong.frmThongTinLienHe f = new WinUI.WinUILapHopDong.frmThongTinLienHe();
        // f.vkhachhang_id = khachhang_id;
        // f.vhdkh_id = hdkh_id;
        // f.ten_kh = txtTenTB.Text;
        // f.diachi = txtDiaChiLD.Text;
        // f.ShowDialog();
        this.$refs.thongtinlienhe.show();
      } else {
        this.ShowError(
          "Chưa lấy được hợp đồng khách hàng ID, hãy lấy thông tin hợp đồng thuê bao!"
        );
      }
    },
    tsbtnEmail_Click: function () {
      if (this.hdkh_id && Number(this.hdkh_id) != 0) {
        // WinUI.WinUILapHopDong.frmThongTinEmail f = new WinUI.WinUILapHopDong.frmThongTinEmail();
        // f.vkhachhang_id = khachhang_id;
        // f.vhdkh_id = hdkh_id;
        // f.ShowDialog();
        this.$refs.thongtinemail.show();
      } else {
        this.ShowError(
          "Chưa lấy được hợp đồng khách hàng ID, hãy lấy thông tin hợp đồng thuê bao!"
        );
      }
    },
    tsbtnXemanh_Click() {
      // WinUI.WinUIECMS.XemAnhBH f = new WinUI.WinUIECMS.XemAnhBH();
      // var dt = bangts.GetDataSql("select mota from " + DatabaseConstants.DB2 + ".giaophieu where mota is not null and hdtb_id = " + hdkh_id);
      // if (dt.Rows.Count > 0)
      // {
      //     var l = GetImagePropeties(dt.Rows[0][0].toString(), "url");
      //     if (l.Count > 0)
      //     {
      //         string url = SERVER_IMAGES.BASE_URL + l[0];
      //         f.url = url;
      //         f.StartPosition = FormStartPosition.CenterParent;
      //         f.ShowDialog();
      //     }
      // }
    },
    btnLayMaGD_Click: function () {
      this.keyenter = 0;
      this.tieuchi_id =
        "kh.ma_kh in (select ma_kh from css.hd_khachhang where ma_gd =  ':values' and phanvung_id = kh.phanvung_id)";
      this.thongtin_tracuu = this.leftForm.txtMaGD;
      this.$refs.popupSearchContract.showModal();
    },
    btnTKMaTB_Click: function () {
      this.keyenter = 1;
      this.ShowSearchAccount();
    },
    btnChon_TraTruoc_Click: function () {
      this.ShowPopupChonKhuyenMai();
    },
    btnChiTiet_Click: async function () {
      try {
        if (this.ds_tratruoc && this.ds_tratruoc.length > 0) {
          var khuyenmai_id_tt = Number(this.rightForm.cboTraTruoc);
          var input_km = {
            khuyenmai_id: khuyenmai_id_tt,
            ngay_yc: moment(new Date()).format("DD/MM/YYYY"),
          };
          let data = this.GetData(
            await api.kiemtra_thoihan_khuyenmai(this.axios, input_km)
          );
          if (data.result == 0) {
            var km = this.ds_tratruoc.filter(
              (e) =>
                Number(e.khuyenmai_id) == Number(this.rightForm.cboTraTruoc)
            );
            var ten_km = km && km.length > 0 ? km[0].ten_km : "";
            var ngay_kt_km = "";
            await api
              .post_lay_ngaykt_km(
                this.axios,
                Number(this.rightForm.cboTraTruoc)
              )
              .then((response) => {
                if (
                  response &&
                  response.data &&
                  response.data.data &&
                  response.data.error_code &&
                  response.data.error_code == "BSS-00000000"
                ) {
                  ngay_kt_km = response.data.data;
                }
              });
            this.$bvModal
              .msgBoxConfirm(
                "Khuyến mại " +
                  ten_km +
                  " đã hết thời hạn từ ngày " +
                  ngay_kt_km +
                  ".\nBạn có muốn tiếp tục chọn chi tiết trả trước?",
                {
                  title: "Thông báo",
                  size: "sm",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                }
              )
              .then(async (v) => {
                if (v) {
                  this.loading(true);
                  this.popupComponentData = {
                    thuebao_id: this.thuebao_id,
                    khuyenmai_id: khuyenmai_id_tt,
                    _hdtb_id: this.hdtb_id,
                    vdichvuvt_id: Number(this.dichvuvt_id),
                    // m_dsThueBao_DichVu: this.m_dsThueBao_DichVu,
                    dsDC_HDTB: this.hdtb_dc_2 && this.hdtb_dc_2.length > 0 ? this.LowerCasePropertyList(this.hdtb_dc_2) : [],
                    vtocdotn_id: this.vtocdo_id,
                    vmuccuoctn_id: this.vmuccuoc_id,
                    loaihinh_tb: Number(this.loaitb_id),
                    kieu_km: 1,
                  }; // 0: khuyen mai, 1 tra truoc

                  setTimeout(() => {
                    this.$refs.dangKyChiTietKhuyenMaiModal.showModal();
                    this.loading(false);
                  }, 500);
                } else {
                  this.dtiendatcoc_tt = 0;
                  this.dvatdatcoc_tt = 0;
                  return;
                }
              })
              .catch((e) => {
                this.ShowError(e);
                this.loading(false);
              });
          } else {
            this.loading(true);
            this.popupComponentData = {
              thuebao_id: this.thuebao_id,
              khuyenmai_id: khuyenmai_id_tt,
              _hdtb_id: this.hdtb_id,
              vdichvuvt_id: Number(this.dichvuvt_id),
              // m_dsThueBao_DichVu: this.m_dsThueBao_DichVu,
              dsDC_HDTB: this.hdtb_dc_2 && this.hdtb_dc_2.length > 0 ? this.LowerCasePropertyList(this.hdtb_dc_2) : [],
              vtocdotn_id: this.vtocdo_id,
              vmuccuoctn_id: this.vmuccuoc_id,
              loaihinh_tb: Number(this.loaitb_id),
              kieu_km: 1,
            }; // 0: khuyen mai, 1 tra truoc
            setTimeout(() => {
              this.$refs.dangKyChiTietKhuyenMaiModal.showModal();
              this.loading(false);
            }, 500);
          }
        } else {
          this.$toast.error("Không có thông tin trả trước!");
        }
      } catch (ex) {
        // if (ex.data && ex.data.message_detail)
        //   this.ShowError("Có lỗi: " + ex.data.message_detail);
        // else this.ShowError("Có lỗi: " + ex);
        console.log(ex);
      }
    },
    btnTien_Click() {
      this.popupTienKM();
    },
    chkThoiHan_CheckedChanged: function () {
      if (this.chkThoiHan.Checked) {
        if (this.rightForm.dtpTuNgay) {
          var tungay = new Date(moment(this.rightForm.dtpTuNgay, "DD/MM/YYYY"));
          var _sothang = Number(this.cp_txtThoiHan);
          this.rightForm.dtpDenNgay = moment(
            tungay.setMonth(tungay.getMonth() + _sothang)
          ).format("DD/MM/YYYY");
        }
      }
    },
    CapNhat: async function () {
      this.loading(true);
      try {
        if (this.button.tsbtnNhapMoi == false) {
          var kiemtra_Dulieu = await this.Kiemtra_Dulieu(true);
          if (!kiemtra_Dulieu) return;
          await this.TaoDuLieu(true);
          // if (this.loi_sinh_magd) {
          //   this.loi_sinh_magd = false;
          //   return;
          // }
          await this.update_thuebao_datcoc(
            this.dsHDKH,
            this.dsHDTB,
            this.hdtb_dc_1,
            this.dsCTTHD,
            this.dsHDTB_CNTT,
            // this.huonggiaotn_id,
            1
          );
          //     datcoc.ThemMoi_HopDong_V3(
          //       dsHDKH,
          //       dsHDTB,
          //       hdtb_dc_1,
          //       dsCTTHD,
          //       dsHDTB_CNTT,
          //       huonggiaotn_id
          //     );
          // await this.HienThiDanhSacHDTB();
          // await this.txtMaTB_KeyPress();
        } else {
          var kiemtra_Dulieu = await this.Kiemtra_Dulieu(false);
          if (!kiemtra_Dulieu) return;
          await this.TaoDuLieu(false);

          await this.update_thuebao_datcoc(
            this.dsHDKH,
            this.dsHDTB,
            this.hdtb_dc_1,
            this.dsCTTHD,
            this.dsHDTB_CNTT,
            // this.huonggiaotn_id,
            0
          );
          //     datcoc.CapNhat_HopDong_DC_V3(
          //       dsHDKH,
          //       dsHDTB,
          //       hdtb_dc_1,
          //       dsCTTHD,
          //       dsHDTB_CNTT
          //     );
          // await this.HienThiDanhSacHDTB();
          // await this.txtMaTB_KeyPress();
        }
        // this.button.tsbtnThanhToan = this.tthd_id == 1 ? true : false;
        // this.button.tsbtnCapNhatDB = this.tthd_id == 2 ? true : false;
        this.loading(false);
      } catch (exp) {
        // if (exp.data && exp.data.message_detail)
        //   this.ShowError("Lỗi cập nhật " + exp.data.message_detail);
        // else this.ShowError("Lỗi cập nhật " + exp);
        console.log(exp);
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    update_thuebao_datcoc: async function (
      dsHDKH,
      dsHDTB,
      hdtb_dc_1,
      dsCTTHD,
      dsHDTB_CNTT,
      // huonggiaotn_id,
      is_insert
    ) {
      var input = {
        donvi_id: Number(await this.$root.token.getDonViID()),
        // huongiao_id: huonggiaotn_id,
        is_insert: is_insert,
        js_hdkh: dsHDKH,
        js_hdtb: dsHDTB,
        js_hdtb_dc: hdtb_dc_1,
        js_ct_tienhd: dsCTTHD,
        js_hdtb_cntt: dsHDTB_CNTT.length > 0 ? JSON.stringify(dsHDTB_CNTT) : "",
      };
      let response = await api.update_thuebao_datcoc(this.axios, input);
      if (response.data.error == 200 || response.data.error == "200") {
        //ok;ma_gd;hdkh_id;hdtb_id
        if (response.data.data.includes("ok")) {
          is_insert == 1
            ? this.ShowSuccess("Thêm mới dữ liệu thành công!")
            : this.ShowSuccess("Cập nhật dữ liệu thành công!");
          var data = response.data.data.split(";");
          if (data[1]) this.leftForm.txtMaGD = data[1];
          if (data[2]) this.hdkh_id = Number(data[2]);
          await this.HienThiDanhSacHDTB();
          // await this.txtMaGD_KeyPress();
        } else {
          this.ShowError(response.data.data);
        }
      } else {
        console.log(response.data.message);
        this.ShowError("Lỗi cập nhật thuê bao đặt cọc");
      }
    },
    async Kiemtra_Dulieu(themmoi) {
      if (this.phainhap_thoihan) {
        var input = {
          type: 6,
          param: this.leftForm.cboLoaiHinhTB.toString(),
        };
        let tt = this.GetData(await api.fn_tt_loaihinh_tb(this.axios, input));
        if (tt && tt.toString() == "1")
          if (this.chkThoiHan.Checked == false) {
            this.ShowError("Bạn phải chọn Thời hạn !");
            return false;
          }
      }
      if (
        Number(this.leftForm.cboDichVuVT) == DichVuVienThong.DICHVU_CNTT ||
        Number(this.leftForm.cboDichVuVT) == DichVuVienThong.ANTOAN_BAOMAT_TT ||
        Number(this.leftForm.cboDichVuVT) == DichVuVienThong.TRUNGTAM_DULIEU ||
        Number(this.leftForm.cboDichVuVT) == DichVuVienThong.HOINGHI_TRUYENHINH
      ) {
        if (this.chkThoiHan.Checked == false) {
          this.ShowError("Bạn phải chọn Thời hạn !");
          return false;
        }
      }
      if (this.thuebao_id <= 0) {
        this.ShowError("Không có thông tin thuê bao!");
        this.$refs.txtMaTB.focus();
        return false;
      }
      var tratruoc = this.ds_tratruoc.filter(
        (e) => Number(e.khuyenmai_id) == Number(this.rightForm.cboTraTruoc)
      );
      var ten_tt = tratruoc.length > 0 ? tratruoc[0].ten_km : "";
      if (ten_tt == "") {
        this.ShowError("Bạn hãy Trả trước !");
        // this.$refs.cboTraTruoc.focus();
        return false;
      }
      if (this.hdtb_dc_2.length == 0) {
        this.ShowError("Bạn chưa chọn chi tiết trả trước!");
        return false;
      }
      if (!this.leftForm.txtMaTB || this.leftForm.txtMaTB == "") {
        this.ShowError("Bạn chưa nhập số máy/account !");
        this.$refs.txtMaTB.focus();
        return false;
      }
      if (this.nhap_CTV == 1) {
        if (
          !this.filterbox.ctv.chkCTV ||
          this.filterbox.ctv.txtCTV.toString().trim() == ""
        ) {
          this.ShowError("Hãy chọn cộng tác viên");
          return false;
        }
      }
      if (!themmoi) {
        if (this.in_ma_tb != this.leftForm.txtMaTB) {
          this.ShowError("Bạn không được thay đổi số máy/account");
          return false;
        }
      }
      if (this.hdtb_dc_2.length > 0) {
        for (var i = 0; i < this.hdtb_dc_2.length; i++) {
          if (
            this.hdtb_dc_2[i]["nhom_datcoc_id"] &&
            this.hdtb_dc_2[i]["nhom_datcoc_id"].toString() != ""
          ) {
            if (
              this.hdtb_dc_2[i]["thang_bd"] &&
              this.hdtb_dc_2[i]["thang_bd"].toString() != "" &&
              this.hdtb_dc_2[i]["thang_kt"] &&
              this.hdtb_dc_2[i]["thang_kt"].toString() != ""
            ) {
              // Kiểm tra nhập trùng trên form
              for (var j = i + 1; j < this.hdtb_dc_2.length; j++) {
                if (i == this.hdtb_dc_2.length) break;
                if (
                  this.hdtb_dc_2[j]["nhom_datcoc_id"] &&
                  this.hdtb_dc_2[j]["nhom_datcoc_id"].toString() != ""
                ) {
                  if (
                    (Number(this.hdtb_dc_2[i]["nhom_datcoc_id"].toString()) ==
                      1 ||
                      Number(this.hdtb_dc_2[i]["nhom_datcoc_id"].toString()) ==
                        4) &&
                    (Number(this.hdtb_dc_2[j]["nhom_datcoc_id"].toString()) ==
                      1 ||
                      Number(this.hdtb_dc_2[j]["nhom_datcoc_id"].toString()) ==
                        4) &&
                    this.hdtb_dc_2[j]["thang_bd"] &&
                    this.hdtb_dc_2[j]["thang_bd"].toString() != "" &&
                    this.hdtb_dc_2[j]["thang_kt"] &&
                    this.hdtb_dc_2[j]["thang_kt"].toString() != "" &&
                    (this.hdtb_dc_2[j]["cuoc_dc"] &&
                    Number(this.hdtb_dc_2[j]["cuoc_dc"].toString()) > 0 &&
                    this.hdtb_dc_2[i]["cuoc_dc"] &&
                    Number(this.hdtb_dc_2[i]["cuoc_dc"].toString()) > 0) ||
                    (this.hdtb_dc_2[j]["datcoc_csd"] &&
                    Number(this.hdtb_dc_2[j]["datcoc_csd"].toString()) > 0 &&
                    this.hdtb_dc_2[i]["datcoc_csd"] &&
                    Number(this.hdtb_dc_2[i]["datcoc_csd"].toString()) > 0)
                  ) {
                    if (
                      (Number(this.hdtb_dc_2[i]["thang_bd"].toString()) <=
                        Number(this.hdtb_dc_2[j]["thang_bd"].toString()) &&
                        Number(this.hdtb_dc_2[i]["thang_kt"].toString()) >=
                          Number(this.hdtb_dc_2[j]["thang_bd"].toString())) ||
                      (Number(this.hdtb_dc_2[i]["thang_bd"].toString()) <=
                        Number(this.hdtb_dc_2[j]["thang_kt"].toString()) &&
                        Number(this.hdtb_dc_2[i]["thang_kt"].toString()) >=
                          Number(this.hdtb_dc_2[j]["thang_kt"].toString())) ||
                      (Number(this.hdtb_dc_2[i]["thang_bd"].toString()) >=
                        Number(this.hdtb_dc_2[j]["thang_bd"].toString()) &&
                        Number(this.hdtb_dc_2[i]["thang_kt"].toString()) <=
                          Number(this.hdtb_dc_2[j]["thang_kt"].toString()))
                    ) {
                      this.ShowError(
                        "Trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!"
                      );
                      return false;
                    }
                  }
                }
              }
              // end
            }
          }
        }
      }

      return true;
    },
    TaoDuLieu: async function (themmoi) {
      await this.TaoDL(false, themmoi, false);
    },
    TaoDL: async function (themTB, themmoi, KoTaoHDKH) {
      if (!KoTaoHDKH) await this.TaoDuLieu_HDKH(themmoi);
      // if (this.loi_sinh_magd) return;
      await this.TaoDuLieu_HDTB(themTB, themmoi);
      await this.TaoDuLieu_HDTB_DATCOC(themmoi);
      await this.TaoDuLieu_CT_TIENHD(themmoi);

      if (
        Number(this.leftForm.cboDichVuVT) == DichVuVienThong.ANTOAN_BAOMAT_TT ||
        Number(this.leftForm.cboDichVuVT) == DichVuVienThong.DICHVU_CNTT ||
        Number(this.leftForm.cboDichVuVT) ==
          DichVuVienThong.HOINGHI_TRUYENHINH ||
        Number(this.leftForm.cboDichVuVT) == DichVuVienThong.TRUNGTAM_DULIEU
      )
        await this.Tao_HDTB_CNTT(themmoi);
    },
    TaoDuLieu_HDKH: async function (themmoi) {
      this.dsHDKH = [];
      var rowHDKH = {};
      if (themmoi == true) {
        rowHDKH.NGAY_YC = moment(new Date()).format("DD/MM/YYYY");
      } else {
        rowHDKH.HDKH_ID = Number(this.hdkh_id);
        rowHDKH.MA_GD = this.leftForm.txtMaGD;
        rowHDKH.NGAY_YC = this.leftForm.dtpNgayYeuCau;
      }
      rowHDKH.MA_KH = this.ma_kh;
      if (this.khachhang_id > 0) rowHDKH.KHACHHANG_ID = this.khachhang_id;
      rowHDKH.KHLON_ID = this.khlon_id;
      rowHDKH.TEN_KH = this.ten_kh;
      rowHDKH.DIACHI_KH = this.diachi_kh;
      rowHDKH.NGAYLAP_HD = this.dtpNgayLHD;

      rowHDKH.GHICHU = this.rightForm.txtGhiChu;
      rowHDKH.DONVI_ID = Number(await this.$root.token.getDonViID());
      rowHDKH.NHANVIEN_ID = Number(await this.$root.token.getNhanVienID());
      rowHDKH.LOAIHD_ID = LoaiHopDong.DATCOC_MOI;
      rowHDKH.NGUOI_CN = await this.$root.token.getUserName();
      rowHDKH.MAY_CN = await this.$root.token.getMaNhanVien();
      rowHDKH.NGAY_CN = moment(new Date()).format("DD/MM/YYYY");

      if (this.filterbox.ctv.chkCTV && this.filterbox.ctv.txtCTV) {
        rowHDKH.CTV_ID = this.filterbox.ctv.ctv_id;
      }
      if (
        this.filterbox.nguoigt.chkNguoiGT &&
        this.filterbox.nguoigt.txtNguoiGT
      ) {
        rowHDKH.NHANVIENGT_ID = this.filterbox.nguoigt.nguoigt_id;
      }
      this.dsHDKH.push(rowHDKH);
    },
    TaoDuLieu_HDTB: async function (themTB, themmoi) {
      this.dsHDTB = [];
      var rowHDTB = {};
      if (!themmoi) {
        rowHDTB.HDTB_ID = Number(this.hdtb_id);
        rowHDTB.HDKH_ID = Number(this.hdkh_id);
      }
      if (themTB) rowHDTB.HDKH_ID = Number(this.hdkh_id);
      if (this.thuebao_id > 0) rowHDTB.THUEBAO_ID = this.thuebao_id;
      rowHDTB.MA_TB = this.leftForm.txtMaTB;
      rowHDTB.TEN_TB = this.leftForm.txtTenTB.trim();
      rowHDTB.DIACHI_LD = this.leftForm.txtDiaChiLD.trim();
      rowHDTB.DIACHI_TB = this.diachi_ld;
      rowHDTB.KIEULD_ID = KieuLapDat.DATCOC_MOI; // Kiểu đặt cọc mới
      rowHDTB.DOITUONG_ID = this.doituong_id;
      rowHDTB.TTHD_ID = TrangThaiHD.MOI;
      rowHDTB.LOAITB_ID = this.loaitb_id;
      rowHDTB.DICHVUVT_ID = this.dichvuvt_id;
      rowHDTB.GHICHU = this.rightForm.txtGhiChu.trim();
      rowHDTB.DONVI_ID = this.donvi_id;
      rowHDTB.NGUOI_CN = await this.$root.token.getNguoiDungID();
      rowHDTB.NGAY_CN = moment(new Date()).format("DD/MM/YYYY");
      rowHDTB.MAY_CN = await this.$root.token.getMaNhanVien();
      // var dset = this.GetData(
      //   await api.LayHuongGiao_TiepNhan(this.axios, {
      //     kieuLdId: KieuLapDat.DATCOC_MOI,
      //     loaiHdId: LoaiHopDong.DATCOC_MOI,
      //     loaiTbId: this.loaitb_id,
      //     mucCuocId: this.vmuccuoc_id,
      //     tocDoId: this.vtocdo_id,
      //   })
      // );

      // if (dset) {
      //   this.huonggiaotn_id = Number(dset["huonggiao_id"]);
      //   this.quytrinh_id = Number(dset["quytrinh_id"]);
      // }
      // rowHDTB.QUYTRINH_ID = this.quytrinh_id;
      this.dsHDTB.push(rowHDTB);
    },
    TaoDuLieu_HDTB_DATCOC: async function (themmoi) {
      this.hdtb_dc_1 = [];
      var hdtb_dc_1 = [];
      for (var i = 0; i < this.hdtb_dc_2.length; i++) {
        var rowGOI = {};
        if (!themmoi) {
          // rowGOI.RKM_ID = Number(this.rkm_id);
          // rowGOI.THUEBAO_DC_ID = Number(this.thuebao_dc_id);
          rowGOI.HDTB_ID = Number(this.hdtb_id);
        }
        rowGOI.NHOM_DATCOC_ID = this.hdtb_dc_2[i]["nhom_datcoc_id"]
          ? Number(this.hdtb_dc_2[i]["nhom_datcoc_id"])
          : 0;
        rowGOI.THANG_BD = this.hdtb_dc_2[i]["thang_bd"]
          ? Number(this.hdtb_dc_2[i]["thang_bd"].toString().slice(0, 6))
          : this.hdtb_dc_2[i]["thang_bddc"]
          ? Number(this.hdtb_dc_2[i]["thang_bddc"].toString().slice(0, 6))
          : 0;
        rowGOI.THANG_KT = this.hdtb_dc_2[i]["thang_kt"]
          ? Number(this.hdtb_dc_2[i]["thang_kt"].toString().slice(0, 6))
          : this.hdtb_dc_2[i]["thang_ktdc"]
          ? Number(this.hdtb_dc_2[i]["thang_ktdc"].toString().slice(0, 6))
          : 0;

        if (
          this.hdtb_dc_2[i]["thang_bd_mg"] &&
          this.hdtb_dc_2[i]["thang_bd_mg"].toString() != ""
        )
          rowGOI.THANG_BD_MG = Number(this.hdtb_dc_2[i]["thang_bd_mg"]);
        else rowGOI.THANG_BD_MG = 0;

        if (
          this.hdtb_dc_2[i]["thang_kt_mg"] &&
          this.hdtb_dc_2[i]["thang_kt_mg"].toString() != ""
        )
          rowGOI.THANG_KT_MG = Number(this.hdtb_dc_2[i]["thang_kt_mg"]);
        else rowGOI.THANG_KT_MG = 0;

        rowGOI.NGAY_DK = this.leftForm.dtpNgayYeuCau;
        rowGOI.CHITIETKM_ID = this.hdtb_dc_2[i]["chitietkm_id"]
          ? Number(this.hdtb_dc_2[i]["chitietkm_id"])
          : 0;
        rowGOI.CUOC_DC = this.hdtb_dc_2[i]["cuoc_dc"]
          ? Number(this.hdtb_dc_2[i]["cuoc_dc"])
          : (this.hdtb_dc_2[i]["datcoc_csd"] ? Number(this.hdtb_dc_2[i]["datcoc_csd"]) : 0);
        rowGOI.TIEN_TD = this.hdtb_dc_2[i]["tien_td"]
          ? Number(this.hdtb_dc_2[i]["tien_td"])
          : 0;

        rowGOI.TIEN_SD = this.hdtb_dc_2[i]["tien_sd"]
          ? Number(this.hdtb_dc_2[i]["tien_sd"])
          : 0;
        rowGOI.TIEN_TB = this.hdtb_dc_2[i]["tien_tb"]
          ? Number(this.hdtb_dc_2[i]["tien_tb"])
          : 0;
        rowGOI.CUOC_SD = this.hdtb_dc_2[i]["cuoc_sd"]
          ? Number(this.hdtb_dc_2[i]["cuoc_sd"])
          : 0;
        rowGOI.CUOC_TB = this.hdtb_dc_2[i]["cuoc_tb"]
          ? Number(this.hdtb_dc_2[i]["cuoc_tb"])
          : 0;
        rowGOI.TYLE_SD = this.hdtb_dc_2[i]["tyle_sd"]
          ? Number(this.hdtb_dc_2[i]["tyle_sd"])
          : 0;
        rowGOI.TYLE_TB = this.hdtb_dc_2[i]["tyle_tb"]
          ? Number(this.hdtb_dc_2[i]["tyle_tb"])
          : 0;

        hdtb_dc_1.push(rowGOI);
      }
      this.hdtb_dc_1 = hdtb_dc_1;
    },
    TaoDuLieu_CT_TIENHD: async function (themmoi) {
      this.dsCTTHD = [];
      for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        if (this.ds_tien_khoanmuc[i]["tien"] > 0) {
          var rowCTTHD = {};
          if (!themmoi) rowCTTHD.HDTB_ID = Number(this.hdtb_id);
          rowCTTHD.KHOANMUCTT_ID = Number(
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"]
          );
          rowCTTHD.TIEN = Number(this.ds_tien_khoanmuc[i]["tien"]);
          rowCTTHD.VAT = Number(this.ds_tien_khoanmuc[i]["vat"]);
          if (
            Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"]) ==
              KHOANMUC_TT.KMTT_KM_LAPDAT ||
            Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"]) ==
              KHOANMUC_TT.KMTT_KM_THIETBI ||
            Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"]) ==
              KHOANMUC_TT.KMTT_KM_DATCOC ||
            Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"]) ==
              KHOANMUC_TT.KMTT_TIENTHOAI_DC_DOI_TD
          ) {
            rowCTTHD.TIEN = Math.Abs(rowCTTHD.TIEN) * -1;
            rowCTTHD.VAT = Math.Abs(rowCTTHD.VAT) * -1;
          }
          rowCTTHD.ID = Number(this.ds_tien_khoanmuc[i]["khoanmuctt_id"]);
          rowCTTHD.LOAI_ID = 1;
          this.dsCTTHD.push(rowCTTHD);
        }
      }
    },
    Tao_HDTB_CNTT: async function (themmoi) {
      this.dsHDTB_CNTT = [];
      var promise = this.LAY_DS_DB_CNTT_THEO_TBID(this.thuebao_id);
      var dt = await promise;
      if (dt && dt.length > 0) {
        var ItemList = this.UpperCasePropertyList(dt)[0];
        ItemList["SO_THANG"] = this.rightForm.txtThoiHan;
        ItemList["NGAY_BD"] = this.rightForm.dtpTuNgay;
        ItemList["NGAY_KT"] = this.rightForm.dtpDenNgay;
        this.dsHDTB_CNTT.push(ItemList);
      }
    },
    gvDanhSach_FocusedRowChanged: async function (data) {
      this.loading(true);
      try {
        if (data && data.length > 0) {
          this.in_ma_tb = data[0].ma_tb;
          this.hdtb_id = Number(data[0].hdtb_id);
          this.cp_dichvuvt_id = this.dichvuvt_id = Number(data[0].dichvuvt_id);
          this.leftForm.cboLoaiHinhTB = this.loaitb_id = Number(
            data[0].loaitb_id
          );
          this.leftForm.txtMaTB = data[0].ma_tb;
          this.leftForm.txtTenTB = this.ten_tb = data[0].ten_tb
            ? data[0].ten_tb
            : "";
          this.leftForm.txtDiaChiLD = this.diachi_ld = data[0].diachi_ld
            ? data[0].diachi_ld
            : "";
          this.doituong_id = data[0].doituong_id
            ? Number(data[0].doituong_id)
            : 0;
          this.thuebao_id = data[0].thuebao_id ? Number(data[0].thuebao_id) : 0;
          this.tthd_id = data[0].tthd_id ? Number(data[0].tthd_id) : 0;
          var promiseDCLD = this.LAY_DS_DIACHI_THEO_DBTBID(this.thuebao_id);
          var ds_dcld = await promiseDCLD;
          if (ds_dcld && ds_dcld.length > 0) {
            if (
              ds_dcld[0]["phuongld_id"] &&
              ds_dcld[0]["phuongld_id"].toString() != ""
            )
              this.phuongld_id = Number(ds_dcld[0]["phuongld_id"].toString());
            else this.phuongld_id = 0;

            if (
              ds_dcld[0]["phold_id"] &&
              ds_dcld[0]["phold_id"].toString() != ""
            )
              this.phold_id = Number(ds_dcld[0]["phold_id"].toString());
            else this.phold_id = 0;

            if (ds_dcld[0]["apld_id"] && ds_dcld[0]["apld_id"].toString() != "")
              this.apld_id = Number(ds_dcld[0]["apld_id"].toString());
            else this.apld_id = 0;

            if (
              ds_dcld[0]["khuld_id"] &&
              ds_dcld[0]["khuld_id"].toString() != ""
            )
              this.khuld_id = Number(ds_dcld[0]["khuld_id"].toString());
            else this.khuld_id = 0;
          }

          await this.HienThiTTMoRong_DB(this.thuebao_id, this.dichvuvt_id);
          if (
            this.dichvuvt_id == DichVuVienThong.ADSL ||
            this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
            this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT ||
            this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU ||
            this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH
          )
            this.leftForm.cboGoiCuocAdsl = this.vtocdo_id;
          await this.HT_DS_TraTruoc_Combobox();
          if (data[0].khuyenmai_id) {
            this.rightForm.cboTraTruoc = Number(data[0].khuyenmai_id);
          } else {
            this.rightForm.cboTraTruoc = -1;
          }

          this.rightForm.txtGhiChu = data[0].ghichu ? data[0].ghichu : "";
          var promiseTT = this.LAY_CHITIET_DC_THEO_HDTB_ID(this.hdtb_id);
          var dsTempTT = await promiseTT;
          this.hdtb_dc_1 = this.hdtb_dc_2 = [];
          if (dsTempTT.length > 0) {
            if (dsTempTT && dsTempTT[0]["khuyenmai_id"])
              this.rightForm.cboTraTruoc = Number(dsTempTT[0]["khuyenmai_id"]);
            this.hdtb_dc_1 = await dsTempTT;
            this.hdtb_dc_2 = await dsTempTT;
          }
          var promiseKhoanMuc = this.LAY_TIENHD_KM_THEO_HDTB(this.hdtb_id);
          this.ds_tien_khoanmuc = await promiseKhoanMuc;
          this.LayTongTien_HDTB();
          //"Thực hiện thông tin mở rộng cho dịch vụ CNTT"
          if (
            this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT ||
            this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
            this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU ||
            this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH
          ) {
            var promiseHDCNTT = this.LAY_DS_HDTB_CNTT_THEO_HDTB_ID(
              this.hdtb_id
            );
            var ds_hdtb_cntt = await promiseHDCNTT;
            if (ds_hdtb_cntt && ds_hdtb_cntt.length > 0) {
              if (
                ds_hdtb_cntt[0]["so_thang"] &&
                ds_hdtb_cntt[0]["so_thang"] != 0
              ) {
                this.chkThoiHan.Checked = true;
                this.rightForm.txtThoiHan = ds_hdtb_cntt[0]["so_thang"];
                if (
                  ds_hdtb_cntt[0]["ngay_bd"] &&
                  ds_hdtb_cntt[0]["ngay_bd"].toString() != ""
                )
                  this.rightForm.dtpTuNgay =
                    this.keyenter == 0
                      ? moment(ds_hdtb_cntt[0]["ngay_bd"]).format("DD/MM/YYYY")
                      : moment(
                          ds_hdtb_cntt[0]["ngay_bd"],
                          "DD/MM/YYYY HH:mm:ss"
                        ).format("DD/MM/YYYY");
                else
                  this.rightForm.dtpTuNgay = moment(new Date()).format(
                    "DD/MM/YYYY"
                  );
                if (
                  ds_hdtb_cntt[0]["ngay_kt"] &&
                  ds_hdtb_cntt[0]["ngay_kt"].toString() != ""
                )
                  this.rightForm.dtpDenNgay =
                    this.keyenter == 0
                      ? moment(ds_hdtb_cntt[0]["ngay_kt"]).format("DD/MM/YYYY")
                      : moment(
                          ds_hdtb_cntt[0]["ngay_kt"],
                          "DD/MM/YYYY HH:mm:ss"
                        ).format("DD/MM/YYYY");
                else
                  this.rightForm.dtpDenNgay = moment(new Date()).format(
                    "DD/MM/YYYY"
                  );
              } else {
                this.chkThoiHan.Checked = false;
                this.rightForm.txtThoiHan = 0;
                this.rightForm.dtpTuNgay = moment(new Date()).format(
                  "DD/MM/YYYY"
                );
                this.rightForm.dtpDenNgay = moment(new Date()).format(
                  "DD/MM/YYYY"
                );
              }
            }
          }
          this.SetButton(3);
          this.button.tsbtnThanhToan = this.tthd_id == 1 ? true : false;
          this.button.tsbtnCapNhatDB = this.tthd_id == 2 ? true : false;
        }
      } catch (e) {
        console.log(e);
        this.loading(false);
      }
      this.loading(false);
    },
    HienThiTien_TraTruoc() {
      if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0) {
        this.ds_tien_khoanmuc.forEach((e) => {
          if (e.khoanmuctt_id == KHOANMUC_TT.KMTT_DATCOC) {
            e.tien = this.dtiendatcoc_tt;
            e.vat = this.dvatdatcoc_tt;
          }
        });
      }
      this.LayTongTien_HDTB();
    },
    LayTongTien_HDTB() {
      try {
        var dtongtien = 0;
        var dtongvat = 0;
        for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] ==
              KHOANMUC_TT.KMTT_KM_LAPDAT ||
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] ==
              KHOANMUC_TT.KMTT_TIENTHOAI_DC_DOI_TD ||
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] ==
              KHOANMUC_TT.KMTT_KM_DATCOC
          ) {
            if (
              this.ds_tien_khoanmuc[i]["tien"] &&
              Number(this.ds_tien_khoanmuc[i]["tien"]) < 0
            ) {
              dtongtien += Number(this.ds_tien_khoanmuc[i]["tien"]);
              dtongvat += Number(this.ds_tien_khoanmuc[i]["vat"]);
            } else {
              dtongtien += Number(this.ds_tien_khoanmuc[i]["tien"]) * -1;
              dtongvat += Number(this.ds_tien_khoanmuc[i]["vat"]) * -1;
            }
          } else if (
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] !=
              KHOANMUC_TT.KMTT_TRAGOP_THIETBI &&
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] !=
              KHOANMUC_TT.KMTT_KM_THIETBI &&
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] !=
              KHOANMUC_TT.KMTT_MUATHIETBI
          ) {
            dtongtien += Number(this.ds_tien_khoanmuc[i]["tien"]);
            dtongvat += Number(this.ds_tien_khoanmuc[i]["vat"]);
          }
        }
        this.rightForm.txtTongTien = dtongtien;
        this.rightForm.txtTongVat = dtongvat;
        this.rightForm.txtSumTien = dtongtien + dtongvat;
      } catch (ex) {}
    },
    async popupTienKM() {
      var ds = [];
      await api_installnewsubs
        .post_lay_khoanmuc_tt_theo_loaihd(this.axios, {
          vloaihd_id: LoaiHopDong.DATCOC_MOI,
        })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code == "BSS-00000000"
          ) {
            ds = response.data.data;
            if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0)
              for (var item of ds) {
                var items = this.ds_tien_khoanmuc.filter(
                  (x) => x.khoanmuctt_id == item.khoanmuctt_id
                );
                if (items && items.length > 0) {
                  item.tien = items[0].tien ? items[0].tien : 0;
                  item.vat = items[0].vat ? items[0].vat : 0;
                  item.tongtien = item.tien + item.vat;
                }
              }
          }
        });
      this.popupComponentData = {
        ds: ds,
        cols: [
          {
            fieldName: "tentat_kmtt",
            headerText: "Khoản mục",
            allowFiltering: false,
            allowSorting: false,
            isPrimaryKey: true,
          },
          {
            fieldName: "tongtien",
            textAlign: "right",
            allowEditing: true,
            editType: "numericedit",
            format: "N",
            customAttributes: { class: "tongtien" },
            headerText: "Tổng tiền",
            allowFiltering: false,
            allowSorting: false,
            edit: "tongtienParam",
          },
          {
            fieldName: "tien",
            textAlign: "right",
            allowEditing: true,
            editType: "numericedit",
            format: "N",
            customAttributes: { class: "cuoclapdat" },
            headerText: "Tiền",
            allowFiltering: false,
            allowSorting: false,
            edit: "tienParam",
          },
          {
            fieldName: "vat",
            textAlign: "right",
            format: "N",
            customAttributes: { class: "vatlapdat" },
            headerText: "Vat",
            allowFiltering: false,
            allowSorting: false,
          },
        ],
        keycol: "khoanmuctt_id",
        allowExit: false,
        tieude: "Chi tiết tiền khoản mục",
        tieudebang: "Chi tiết khoản mục thanh toán",
      };

      this.popupComponentName = "popupTienKM";
      this.popupComponentHeader = "";
      this.popupComponent = () =>
        import(
          "@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM.vue"
        );
      this.Popup("popupComponents");
    },
    acceptTimKiemHDTB: async function (item) {
      if (item) {
        this.leftForm.txtMaGD = item.ma_gd;
        this.$refs.txtMaGD.focus();
        if (this.leftForm.txtMaGD && this.leftForm.txtMaGD != "") {
          this.gridDanhSach.list = [];
          var promise_dshd = this.LAY_DS_HOPDONG_THEO_MA_GD(
            this.leftForm.txtMaGD
          );
          var ds = await promise_dshd;
          await this.HienThiTTHopDongKH(ds);
        }
      }
    },
    ShowSearchAccount: function () {
      this.popupComponentAttr = {
        vma_tb: this.leftForm.txtMaTB,
        vdichvuvt_id: this.leftForm.cboDichVuVT,
      };
      this.popupComponent = () =>
        import("@/modules/search/subscriber/SearchAccount");
      this.popupComponentName = "SearchAccount";
      this.popupComponentHeader = "";
      this.Popup("popupComponents");
    },
    ShowPopupCTV() {
      if (this.filterbox.ctv.chkCTV) {
        this.popupComponent = () =>
          import(
            "@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien"
          );
        this.popupComponentName = "popupCTV";
        this.popupComponentHeader = "Chọn cộng tác viên";
        this.Popup("popupComponents");
      } else {
        this.filterbox.ctv.txtCTV = "";
        this.filterbox.ctv.ctv_id = -1;
      }
    },
    ShowPopupNguoiGT() {
      if (this.filterbox.nguoigt.chkNguoiGT) {
        this.popupComponent = () =>
          import(
            "@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu"
          );
        this.popupComponentName = "popupNGT";
        this.popupComponentHeader = "Chọn người giới thiệu";
        this.Popup("popupComponents");
      } else {
        this.filterbox.nguoigt.txtNguoiGT = "";
        this.filterbox.nguoigt.nguoigt_id = -1;
      }
    },
    ShowPopupChonKhuyenMai() {
      var dulieu = {};
      dulieu.loai = Loai_KM.TraTruoc;
      dulieu.dt = this.ds_tratruoc;
      this.popupComponentData = dulieu;
      this.popupComponent = () =>
        import(
          "@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai"
        );
      this.popupComponentName = "popup-ChonKhuyenMai";
      this.popupComponentHeader = "";
      this.Popup("popupComponents");
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup("popupComponents");
        return;
      }
      this.loading(true);
      try {
        switch (this.popupComponentName) {
          case "popupCTV":
            this.filterbox.ctv.txtCTV = val.ten_ht;
            this.filterbox.ctv.ctv_id = val.nhanvien_id;
            break;
          case "popupNGT":
            this.filterbox.nguoigt.txtNguoiGT = val.ten_ht;
            this.filterbox.nguoigt.nguoigt_id = val.nhanvien_id;
            break;
          case "SearchAccount":
            if (val.ma_tb) this.leftForm.txtMaTB = val.ma_tb;
            else this.leftForm.txtMaTB = "";
            if (val.ma_kh) this.ma_kh = val.ma_kh;
            else this.ma_kh = "";
            break;
          case "popup-ChonKhuyenMai":
            this.rightForm.cboTraTruoc = val.khuyenmai.khuyenmai_id;
            break;
          // case "popup-DangkychitietkhuyenmaiTT":
          //   this.hdtb_dc_2 = this.LowerCasePropertyList(val.dsDC_HDTB);
          //   this.dtiendatcoc_tt = Math.round((val.tien_datcoc_tt * 10) / 11);
          //   this.dvatdatcoc_tt = val.tien_datcoc_tt - this.dtiendatcoc_tt;
          //   if (this.hdtb_dc_2.length == 0) {
          //     this.dtiendatcoc_tt = 0;
          //     this.dvatdatcoc_tt = 0;
          //   }
          //   this.HienThiTien_TraTruoc();
          //   break;
          case "popupTienKM":
            this.ds_tien_khoanmuc = val;
            if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0) {
              for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
                if (
                  this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() ==
                  KHOANMUC_TT.KMTT_DOITOCDO.toString()
                ) {
                  this.dtienld = Number(
                    this.ds_tien_khoanmuc[i]["tien"].toString()
                  );
                  this.dvatld = Number(
                    this.ds_tien_khoanmuc[i]["vat"].toString()
                  );
                }
                this.LayTongTien_HDTB();
              }
            }
            break;
        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail)
          this.$toast.error("" + ex.data.message_detail);
        else this.$toast.error("" + ex);
      } finally {
        this.ClosePopup("popupComponents");
        this.loading(false);
      }
    },
    formCloseChiTietKM(val) {
      this.hdtb_dc_2 = val.dsDC_HDTB && val.dsDC_HDTB.length > 0 ? this.LowerCasePropertyList(val.dsDC_HDTB) : [];
      this.dtiendatcoc_tt = Math.round((val.tien_datcoc_tt * 10) / 11);
      this.dvatdatcoc_tt = val.tien_datcoc_tt - this.dtiendatcoc_tt;
      if (this.hdtb_dc_2.length == 0) {
        this.dtiendatcoc_tt = 0;
        this.dvatdatcoc_tt = 0;
      }
      this.HienThiTien_TraTruoc();
    },
    SetButton: function (kieu) {
      this.button.tsbtnNhapMoi = false;
      this.button.tsbtnGhiLai = false;
      this.button.tsbtnXoa = false;
      this.button.tsbtnHuyBo = false;
      if (kieu == -1) {
        //Bat dau
        this.button.tsbtnGhiLai = true;
        this.button.tsbtnHuyBo = true;
      }
      if (kieu == 0) {
        //Bat dau
        this.$refs.txtMaTB.focus();
        this.button.tsbtnNhapMoi = true;
        this.Clear();
      }
      if (kieu == 1) {
        //Them moi
        this.$refs.txtMaTB.focus();
        this.button.tsbtnGhiLai = true;
        this.button.tsbtnHuyBo = true;
        this.Clear();
      }
      if (kieu == 2) {
        //Huy
        this.button.tsbtnNhapMoi = true;
        this.button.tsbtnXoa = true;
        this.Clear();
      }
      if (kieu == 3) {
        //Edit
        this.button.tsbtnNhapMoi = true;
        this.button.tsbtnXoa = true;
        this.button.tsbtnGhiLai = true;
        this.button.tsbtnHuyBo = true;
      }
    },
    Clear: function () {
      this.rightForm.txtTongTien = 0;
      this.rightForm.txtTongVat = 0;
      this.rightForm.txtSumTien = 0;
      this.gridDanhSach.list = [];

      this.phuongld_id = 0;
      this.phold_id = 0;
      this.apld_id = 0;
      this.khuld_id = 0;
      this.vtrangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI; //default khách hàng tự trang bị
      this.vtocdo_id = 0;
      this.vmuccuoc_id = 0;

      this.rightForm.txtThoiHan = 0;
      this.leftForm.txtMaGD = "";
      this.leftForm.txtMaTB = "";
      this.leftForm.txtTenTB = "";
      this.leftForm.txtDiaChiLD = "";
      this.rightForm.txtGhiChu = "";
      this.thuebao_dc_id = 0;
      this.thuebao_id = 0;
      this.filterbox.nguoigt.chkNguoiGT = false;
      if (this.khong_load_ctv == 0) this.filterbox.ctv.chkCTV = false;
    },
    GetData: function (response) {
      if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        // console.log(response.data.message);
        // this.ShowError(response.data.message);
        return [];
      }
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
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
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
  },
  computed: {
    cp_dichvuvt_id: {
      get() {
        return Number(this.leftForm.cboDichVuVT);
      },
      set(value) {
        this.leftForm.cboDichVuVT = Number(value);
      },
    },
    cp_txtThoiHan: {
      get() {
        return Number(this.rightForm.txtThoiHan);
      },
      set(value) {
        this.rightForm.txtThoiHan = Number(value);
      },
    },
    cp_TongTien: {
      get() {
        var txtTongTien = this.rightForm.txtTongTien
          ? this.numberWithCommas(this.rightForm.txtTongTien.toString())
          : "0";
        return txtTongTien;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.rightForm.txtTongTien = Number(replaced);
      },
    },
    cp_TongVat: {
      get() {
        var txtTongVat = this.rightForm.txtTongVat
          ? this.numberWithCommas(this.rightForm.txtTongVat.toString())
          : "0";
        return txtTongVat;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.rightForm.txtTongVat = Number(replaced);
      },
    },
    cp_SumTien: {
      get() {
        var txtSumTien = this.rightForm.txtSumTien
          ? this.numberWithCommas(this.rightForm.txtSumTien.toString())
          : "0";
        return txtSumTien;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.rightForm.txtSumTien = Number(replaced);
      },
    },
  },
  watch: {
    cp_dichvuvt_id(val) {
      this.onChangeDVVT(val);
    },
    "chkThoiHan.Checked": {
      handler: function (newValue, oldValue) {
        this.chkThoiHan_CheckedChanged();
      },
    },
    "rightForm.dtpTuNgay"(val) {
      this.chkThoiHan_CheckedChanged();
    },
    "rightForm.txtThoiHan"(val) {
      this.chkThoiHan_CheckedChanged();
    },
    "rightForm.txtTongTien"(val) {
      var vat = Math.round((Number(val) * 10) / 100);
      this.rightForm.txtTongVat = vat;
      this.rightForm.txtSumTien = Number(val) + Number(vat);
    },
    "rightForm.txtTongVat"(val) {
      this.rightForm.txtSumTien =
        Number(this.rightForm.txtTongTien) + Number(val);
    },
  },
};
</script>
