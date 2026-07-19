<template src="./PrepaidDeposite.html"></template>
<style scoped src="./PrepaidDeposite.scss"></style>
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
import DangKyChiTietKhuyenMaiModal from "@/modules/contract/setup/UpdateSubscriberDeposit/popups/DangKyChiTietKhuyenMaiModal.vue";
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
  name: "PrepaidDeposite",
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
  props: ["dulieu"],
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
        title: "Đặt cọc trả trước",
        list: [
          //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
        //   { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Đặt cọc trả trước",
            link: { name: "Ui.cards" },
          },
        ],
      },
      formWidth: 0,
      re_render: 1,
      vbaocao_hd_id: '',
      dateFormat: "DD/MM/YYYY",
      monthFormat: "MM/YYYY",
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
        txtMaGD: {value: ''},
        dtpNgayYeuCau: {value: moment(new Date()).format("DD/MM/YYYY"),},
        txtMaTB: {value: ''},
        cboKieuLD: {value: 31, Items: []},
        cboDichVuVT: {value: 0},
        cboLoaiHinhTB: {value: 0},
        cboGoiCuocAdsl: {value: 0, Items: []},
        txtTenTB: {value: ''},
        txtDiaChiLD: {value: ''},
        txtTienThoaiTra: {value: 0},
        dtpThangThoaiTra: {value: moment(new Date()).format("MM/YYYY"),},
        dtpNgayThoaiTra: {value: moment(new Date()).format("DD/MM/YYYY"),},
        txtGhiChu: {value: ''},
      },
      rightForm: {
        cboTraTruoc: {value: 0},
        txtThoiHan: {value: 0},
        dtpTuNgay: {value: moment(new Date()).format("DD/MM/YYYY"),},
        dtpDenNgay: {value: moment(new Date()).format("DD/MM/YYYY"),},
        dtpThangBDMG: {value: moment(new Date()).format("MM/YYYY"), Enabled: false},
        txtSoThangMG: {value: 0, Enabled: false},
        txtTyLeTB: {value: 0, Enabled: false},
        txtTyLeSD: {value: 0, Enabled: false},
        txtTienTB: {value: 0, Enabled: false},
        txtTienSD: {value: 0, Enabled: false},
        dtpThangBD: {value: moment(new Date()).format("MM/YYYY"), Enabled: false},
        txtSoThang: {value: 0, Enabled: false},
        txtCuocDC: {value: 0, Enabled: false},
        txtTienTD: {value: 0, Enabled: false},
        dtpNgayBDDC: {value: moment(new Date()).format("DD/MM/YYYY"),},
        dtpNgayKTDC: {value: moment(new Date()).format("DD/MM/YYYY"),},
        txtSumTien: {value: 0},
        txtTongTien: {value: 0},
        txtTongVat: {value: 0},
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
      loaihd_id: 31,
      CT_TIENHD_ID: 0,
      CT_TIENHD_DC_MOI_ID: 0,
      thang_bd_cu: 0, 
      thang_kt_cu: 0, 
      nhom_datcoc_id_cu: 0,
      ttdc_id: 0,
      cuoc_dc_cu: 0,
      tien_td_cu: 0,
      khachhang_id: 0,
      nhomtb_id: 0,
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
      dscheck_thoai: 0,
      _MACDINH_THOAIDC_LUITHANG: 0,
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
      dgvDatCoc: {
        list: [],
        header: [
          {
            fieldName: "ten_ctkm",
            headerText: "Tên KM/Công văn",
            allowFiltering: true,
          },
          {
            fieldName: "thang_huongdc",
            headerText: "Số tháng",
            allowFiltering: true,
          },
          {
            fieldName: "thang_bddc",
            headerText: "Tháng BĐĐC",
            allowFiltering: true,
          },
          {
            fieldName: "thang_ktdc",
            headerText: "Tháng KTĐC",
            allowFiltering: true,
          },
          {
            fieldName: "thang_ktdc",
            headerText: "Tháng thoái",
            allowFiltering: true,
          },
          {
            fieldName: "datcoc_csd",
            headerText: "Cước ĐC",
            allowFiltering: true,
          },
          {
            fieldName: "tien_td",
            headerText: "Tiền TD",
            allowFiltering: true,
          },
          {
            fieldName: "tyle_tb",
            headerText: "Tỷ lệ TB",
            allowFiltering: true,
          },
          {
            fieldName: "tyle_sd",
            headerText: "Tỷ lệ SD",
            allowFiltering: true,
          },
          {
            fieldName: "tien_tb",
            headerText: "Tiền TB",
            allowFiltering: true,
          },
          {
            fieldName: "tien_sd",
            headerText: "Tiền SD",
            allowFiltering: true,
          },
          {
            fieldName: "tien_thoai",
            headerText: "Tiền thoái",
            allowFiltering: true,
          },
          {
            fieldName: "ten_nhom",
            headerText: "Nhóm ĐC",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
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
      this.formWidth = Number(window.innerWidth)
      await Promise.all([this.frmDK_TraTruoc_Load()]);
    } catch (ex) {
      this.loading(false);
      console.log(ex);
    } finally {
      this.loading(false);
    }
  },
  watch: {
    'formWidth'(val) {
      if (val) this.re_render += 1
    }
  },
  methods: {
    frmDK_TraTruoc_Load: async function () {
      this.tt_nd = {
        phanvung_id: Number(await this.$root.token.getPhanVungID()),
        donvi_id: Number(await this.$root.token.getDonViID()),
        nhanvien_id: Number(await this.$root.token.getNhanVienID()),
        ip_cn: await this.$root.token.getIP(),
        may_cn: await this.$root.token.getMachine(),
        nguoi_cn: await this.$root.token.getUserName(),
        ngay_cn: await this.$auth.getNgayCN()
      };
      console.log('frmDK_TraTruoc_Load > tt_nd',this.tt_nd)
      var Tag = null
      if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
          Tag = this.$route.query.tag
      } else if (this.dulieu && this.dulieu.Tag && this.dulieu.Tag.length > 0) {
          Tag = this.dulieu.Tag
      }

      if (Tag != null && Tag.toString() != "")
      {
        //Tag bao gồm 2 mẫu BC: mau_bn_id + mau_hd_id
        // 
        var str1 = Tag.toString();
        if (str1.includes("ShowDialog"))
        {
            str1 = str1.replace("ShowDialog", "");
            str1 = str1.replace("+", "");
        }
        if (str1.includes("+"))
        {
            var bc = str1.split('+');
            if (bc.Length >= 2)
            {
                this.vbaocao_hd_id = bc[1];
            }
        }
        else if (str1 != "")
            this.vbaocao_hd_id = str1;
      }
      else
      {
        this.vbaocao_hd_id = "";
      }

      await Promise.all([
        this.loadKieuLD(),
        this.SetButton(-1),
        this.HT_DichVuVT_Combobox_LM(),
      ]);
      if (this.leftForm.cboKieuLD.Items && this.leftForm.cboKieuLD.Items.length > 0) {
        var index = this.leftForm.cboKieuLD.Items.findIndex(e => e.loaihd_id == 31)
        this.leftForm.cboKieuLD.value = this.leftForm.cboKieuLD.Items[index].kieuld_id
        this.loaihd_id = this.leftForm.cboKieuLD.Items[index].loaihd_id
      }
      
      await this.lay_tienkhoanmuc_loaihd()     
      this.cp_dichvuvt_id = this.ds_dichvuvt && this.ds_dichvuvt.length > 0 ? this.ds_dichvuvt[0].id : this.cp_dichvuvt_id;
      
      if (this.dulieu) {
        console.log('frmDK_TraTruoc_Load > dulieu', this.dulieu)
        this.cp_dichvuvt_id = this.dulieu.dichvu_vt ? this.dulieu.dichvu_vt : this.cp_dichvuvt_id
        this.leftForm.cboLoaiHinhTB.value = this.dulieu.loaitb_id ? this.dulieu.loaitb_id : this.leftForm.cboLoaiHinhTB.value
        // this.leftForm.cboKieuLD.value = this.dulieu.kieuld_id ? this.dulieu.kieuld_id : this.leftForm.cboKieuLD.value
        // this.leftForm.txtMaTB.value = this.dulieu.ma_tb ? this.dulieu.ma_tb : this.leftForm.txtMaTB.value
        // this.vma_tb = this.dulieu.ma_tb ? this.dulieu.ma_tb : this.vma_tb
        this.hdkh_id = this.dulieu.hdkh_id ? this.dulieu.hdkh_id : this.hdkh_id
      } else if (this.$route.query.hdkh_id) {
        this.hdkh_id = this.$route.query.hdkh_id
      }
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

        str_vtemp = "TINHTIEN_THOAI_MOI";
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
            this.dscheck_thoai = 1;
        }

        str_vtemp = "MACDINH_THOAIDC_LUITHANG";  // hoangpkn : 24/12/2018
        det = ds.filter((x) => x["ma_ts"].toString() == str_vtemp);
        if (det && det.length > 0) {
            this._MACDINH_THOAIDC_LUITHANG = 1;
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

        if (this.MACDINH_CTV_NGUOIDUNG == 1) {
            this.filterbox.ctv.ctv_id = Number(
            await this.$root.token.getNhanVienID()
            );
            this.donvi_ctv_id = Number(await this.$root.token.getDonViID());
            var promiseCTV = this.getNhanvien_nvID(this.filterbox.ctv.ctv_id);
            this.filterbox.ctv.txtCTV = await promiseCTV;
            this.filterbox.ctv.chkCTV = true;
        }

        var d = ''
        if (this._MACDINH_THOAIDC_LUITHANG == 0) {
          d = this.tt_nd.ngay_cn;
        } else {
          d = new Date(this.tt_nd.ngay_cn.setMonth(this.tt_nd.ngay_cn.getMonth()-1))
        }

        this.leftForm.dtpThangThoaiTra.value = moment(d).format('MM/YYYY');
        this.leftForm.dtpNgayThoaiTra.value = moment(d).format('DD/MM/YYYY');
      }

      // if ((this.dulieu && this.dulieu.isPopup) || this.$route.query.hdkh_id || this.$route.query.hdkh_id == 0) await this.HienThiDanhSacHDTB()
    },
    frmThueBao_DatCoc_Activated: async function () {
      if (this.dulieu && this.dulieu.isPopup && this.vma_tb != "") {
        await this.txtMaTB_KeyPress()
        // var ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.vma_tb);
        // await this.HienThiTTHopDongKH(ds);
        // this.button.tsbtnThanhToan = this.tthd_id == 1;
        // this.button.tsbtnCapNhatDB = this.tthd_id == 2;
      }
    },
    loadKieuLD: async function () {
      this.leftForm.cboKieuLD.Items = []
      let response = await api.sp_load_KieuLD(this.axios);
      if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
      ) {
          this.leftForm.cboKieuLD.Items = response.data.data;
      } else if (response.data.error_code == "BSS-00000204") {

      } else {
          this.ShowError("Lỗi lấy DS kiểu yêu cầu " + response.data.message_detail);
      }
    },
    lay_tienkhoanmuc_loaihd: async function () {
      var input = {
        loaihd_id: Number(this.loaihd_id),
      };
      let data = this.GetData(
        await api.sp_lay_tienkhoanmuc_loaihd(this.axios, input)
      );
      if (data && data.length > 0) {
        this.ds_tien_khoanmuc = data;
      } else {
        this.ds_tien_khoanmuc = [];
        this.$toast.error("Không lấy được thông tin tiền khoản mục đặt cọc tham chiếu, hãy liên hệ admin để xử lý!");
      }
    },
    HT_DichVuVT_Combobox_LM: async function () {
      let data = this.GetData(await api.sp_lay_dl_dichvuvt(this.axios));
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
      let data = this.GetData(await api.sp_lay_tocdo_internet_theo_dv_id(this.axios, dichvuvt_id));
      if (data && data.length > 0) {
        this.ds_tocdo = data.map((th) => ({
          id: th.tocdo_id,
          text: th.tocdo
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
        vkieuld_id: Number(this.leftForm.cboKieuLD.value), //KieuLapDat.DATCOC_MOI,
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
        in_loaihd_id: this.loaihd_id,
        in_donvi_id: Number(this.$root.token.getDonViID()), //0, //Number(this.$root.token.getDonViID()),
        in_tthd_id: TrangThaiHD.MOI,
        in_nhanvien_id: Number(this.$root.token.getNhanVienID()), //0, //Number(this.$root.token.getNhanVienID()),
        in_donvi_dl_id: 0,
        in_dichvuvt_id: Number(this.cp_dichvuvt_id),
        // in_loaitb_id: loaitb_id,
      };

      let data = this.GetData(
        await api.sp_lay_ds_hopdong_theo_ma_tb(this.axios, input)
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
      let data = this.GetData(
        await api.sp_lay_ds_hdtb_tratruoc_theo_hdkh_id(this.axios, hdkh_id)
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
      let data = this.GetData(await api.sp_lay_danhba_theo_matb(this.axios, input));
      if (!data || data.length == 0) {
        // this.ShowError("Không lấy được danh bạ theo mã thuê bao!");
      }
      return data;
    },
    LAY_DS_DIACHI_THEO_DBTBID: async function (thuebao_id) {
      var input = {
        in_thuebao_id: thuebao_id,
      };
      let data = this.GetData(await api.sp_lay_ds_diachi_theo_dbtbid(this.axios, input));
      return data;
    },
    LAY_DS_HOPDONG_THEO_MA_GD: async function (ma_gd) {
      var input = {
        in_ma_gd: ma_gd,
        in_loaihd_id: this.loaihd_id,
        in_donvi_id: Number(this.$root.token.getDonViID()), //0, //Number(this.$root.token.getDonViID()),
        in_tthd_id: 0,
        in_nhanvien_id: Number(this.$root.token.getNhanVienID()), //0, //Number(this.$root.token.getNhanVienID()),
        in_donvi_dl_id: 0,
      };
      let data = this.GetData(await api.getds_hopdong_magd(this.axios, input));
      return data;
    },
    lay_ds_datcoc_theo_tbid: async function (vthuebao_id, vthang_ht) {
      try {
        // this.dgvDatCoc.list = []
        var input = {
          vthuebao_id: vthuebao_id,
          vthang_ht: vthang_ht
        }
        let response = await api.sp_lay_ds_datcoc_theo_tbid(this.axios, input);
        if (
            response &&
            response.data &&
            response.data.error_code == "BSS-00000000"
        ) {
            // this.dgvDatCoc.list = response.data.data;
          var data = response.data.data
          if (data && data.length > 0) {
            for (var item of data) {
              item.ten_ctkm = item['ten']
              item.thang_huongdc = item['sothang']
              item.thang_bddc = item['thang_bd']
              item.thang_ktdc = item['thang_kt']
              item.datcoc_csd = item['cuoc_dc']
            }
          }
          return data
        } else if (response.data.error_code == "BSS-00000204") {
          return []
        } else {
          this.ShowError("Lỗi lấy DS đặt cọc theo TB ID " + response.data.message_detail);
          return []
        }
      } catch (e) {
        return []
      }
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
    Xoa_hdtb_datcoc_hienhuu: async function (hdtb_id) {
      try {
        var input = {
          vhdtb_id: hdtb_id,
        };
        let response = await api.sp_xoa_hdtb_datcoc_hienhuu(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.ShowSuccess(
            "Xóa thông tin thuê bao khỏi hợp đồng đặt cọc thành công!"
          );
          await this.txtMaGD_KeyPress();
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
            (e) => e.id == Number(this.leftForm.cboLoaiHinhTB.value)
          );
          if (filter.length <= 0)
            this.leftForm.cboLoaiHinhTB.value = this.ds_loaihinhtb[0].id;
        }
        if (this.ds_tocdo && this.ds_tocdo.length > 0) {
          var filter = this.ds_tocdo.filter(
            (e) => e.id == Number(this.leftForm.cboGoiCuocAdsl.value)
          );
          if (filter.length <= 0)
            this.leftForm.cboGoiCuocAdsl.value = this.ds_tocdo[0].id;          
        }
        if (
          Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.DICHVU_CNTT ||
          Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.ANTOAN_BAOMAT_TT ||
          Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.TRUNGTAM_DULIEU ||
          Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.HOINGHI_TRUYENHINH
        ) {
          this.chkThoiHan.Enabled = true;
          this.chkThoiHan.Checked = true;
        } 
      } catch (e) {
        this.loading(false);
      }

      this.loading(false);
    },
    onChangeLoaiHinhTB: async function () {
      if (
        Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.DICHVU_CNTT ||
        Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.ANTOAN_BAOMAT_TT ||
        Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.TRUNGTAM_DULIEU ||
        Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.HOINGHI_TRUYENHINH
      ) {
        this.chkThoiHan.Enabled = true;
        this.chkThoiHan.Checked = true;
      } else {
        this.chkThoiHan.Enabled = false;
        this.chkThoiHan.Checked = false;
      }
      
      if (this.phainhap_thoihan) {
        var input = {
          type: 6,
          param: this.leftForm.cboLoaiHinhTB.value.toString(),
        };
        let tt = this.GetData(await api.fn_tt_loaihinh_tb(this.axios, input));
        if (tt && tt.toString() == "1" && this.phainhap_thoihan)
          this.chkThoiHan.Enabled = true;
      }
    },
    onChangeKieuLD: async function () {
      var ts_loaihd_id = this.GetData(
        await api.map_id_dk_tratruoc(this.axios, {
          p_type: 2, // loaihd theo kieuld
          p_param: Number(this.leftForm.cboKieuLD.value),
        })
      );
      var loaihd_id = 0
      if (ts_loaihd_id && ts_loaihd_id.length > 0) this.loaihd_id = Number(ts_loaihd_id)
      await this.lay_tienkhoanmuc_loaihd()
    },
    onChangeThangThoaiTra(value_) {
      if (!moment(value_, "MM/YYYY").isValid()) {
        this.leftForm.dtpThangThoaiTra.value = "01/2000";
        this.leftForm.dtpNgayThoaiTra.value = "01/01/2000"
      } else {
        this.leftForm.dtpThangThoaiTra.value = value_;
        this.leftForm.dtpNgayThoaiTra.value = moment(value_, "MM/YYYY").format("DD/MM/YYYY");
      }
    },
    onChangeNgayThoaiTra(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.leftForm.dtpThangThoaiTra.value = "01/2000";
        this.leftForm.dtpNgayThoaiTra.value = "01/01/2000"
      } else {
        this.leftForm.dtpThangThoaiTra.value = moment(value_, "DD/MM/YYYY").format("MM/YYYY");
        this.leftForm.dtpNgayThoaiTra.value = value_;
      }
    },
    onChangeNgayBDDC(value_) {
      if (!moment(value_, "MM/YYYY").isValid()) {
        this.rightForm.dtpNgayBDDC.value = "01/2000";
      } else {
        this.rightForm.dtpNgayBDDC.value = value_;
      }
    },
    onChangeNgayKTDC(value_) {
      if (!moment(value_, "MM/YYYY").isValid()) {
        this.rightForm.dtpNgayKTDC.value = "01/2000";
      } else {
        this.rightForm.dtpNgayKTDC.value = value_;
      }
    },
    onChangeThangMG(value_) {
      if (!moment(value_, "MM/YYYY").isValid()) {
        this.rightForm.dtpThangBDMG.value = "01/2000";
      } else {
        this.rightForm.dtpThangBDMG.value = value_;
      }
    },
    onChangeThangBD(value_) {
      if (!moment(value_, "MM/YYYY").isValid()) {
        this.rightForm.dtpThangBD.value = "01/2000";
      } else {
        this.rightForm.dtpThangBD.value = value_;
      }
    },
    onChangeNgayYC(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.leftForm.dtpNgayYeuCau.value  = "01/01/2000";
      } else {
        this.leftForm.dtpNgayYeuCau.value  = value_;
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
      this.rightForm.txtTongTien.value = 0;
      this.rightForm.txtTongVat.value = 0;
      this.rightForm.txtSumTien.value = 0;
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
      this.leftForm.txtTenTB.value = tb["ten_tb"]
        ? this.ChuanHoaTen(tb["ten_tb"].toString())
        : "";
      this.leftForm.txtDiaChiLD.value = tb["diachi_ld"]
        ? this.ChuanHoaTen(tb["diachi_ld"].toString())
        : "";
      this.leftForm.txtMaTB.value = tb["ma_tb"].toString();
      this.ten_tb = this.ChuanHoaTen(tb["ten_tb"].toString());
      this.diachi_ld = tb["diachi_ld"]
        ? this.ChuanHoaTen(tb["diachi_ld"].toString())
        : "";
      this.loaitb_id = Number(tb["loaitb_id"].toString());
      this.leftForm.cboLoaiHinhTB.value = this.loaitb_id;
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
        this.leftForm.cboGoiCuocAdsl.value = this.vtocdo_id;
      var thang_ht = moment(this.tt_nd.ngay_cn).format('yyyyMM')
      var ds_datcoc = await this.lay_ds_datcoc_theo_tbid(this.thuebao_id, thang_ht)
      this.hdtb_dc_1 = [];
      this.hdtb_dc_2 = [];
      this.dgvDatCoc.list = [];
      if (ds_datcoc && ds_datcoc.length > 0) {
        if (ds_datcoc[0]["khuyenmai_id"]) this.rightForm.cboTraTruoc.value = Number(ds_datcoc[0]["khuyenmai_id"]);
        this.hdtb_dc_1 = ds_datcoc;
        this.hdtb_dc_2 = ds_datcoc;
        this.dgvDatCoc.list = ds_datcoc;
      }
      await this.HT_DS_TraTruoc_Combobox();

      // if (this.khong_ht_tracuudb == 0) {
      //   this.ShowPopupSearchAccount();
      // }
      this.hdtb_id = 0;
      this.$refs.danhSachThueBaoCungMaModal.hideModal();
    },
    HienThiTTHopDongKH: async function (ds) {
      console.log(ds)
      if (ds && ds.length > 0) {
        if (ds[0]["hdkh_id"]) {
          this.hdkh_id = Number(ds[0]["hdkh_id"]);
        } else {
          this.hdkh_id = 0;
          this.ShowError("HDKH_ID is null");
        }

        this.leftForm.txtMaGD.value = ds[0]["ma_gd"] ? ds[0]["ma_gd"].toString() : "";
        this.ma_kh = ds[0]["ma_kh"] ? ds[0]["ma_kh"].toString() : "";

        ds[0]["khachhang_id"] && ds[0]["khachhang_id"] != null
          ? (this.khachhang_id = Number(ds[0]["khachhang_id"]))
          : (this.khachhang_id = 0);

        this.khlon_id = ds[0]["khlon_id"]
          ? Number(ds[0]["khlon_id"].toString())
          : 0;
        this.leftForm.txtTenTB.value = ds[0]["ten_tb"]
          ? this.ChuanHoaTen(ds[0]["ten_tb"].toString())
          : "";
        this.leftForm.txtDiaChiLD.value = ds[0]["diachi_ld"]
          ? this.ChuanHoaTen(ds[0]["diachi_ld"].toString())
          : "";
        this.ten_kh = ds[0]["ten_kh"]
          ? this.ChuanHoaTen(ds[0]["ten_kh"].toString())
          : "";
        this.diachi_kh = ds[0]["diachi_kh"]
          ? this.ChuanHoaTen(ds[0]["diachi_kh"].toString())
          : "";
        if (ds[0]["ngay_yc"] && ds[0]["ngay_yc"].toString() != "")
          this.leftForm.dtpNgayYeuCau.value  =
            this.keyenter == 0
              ? moment(ds[0]["ngay_yc"]).format("DD/MM/YYYY")
              : moment(ds[0]["ngay_yc"], "DD/MM/YYYY HH:mm:ss").format(
                  "DD/MM/YYYY"
                );
        this.leftForm.txtGhiChu.value = ds[0]["ghichu"]
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

        // #region "Thực hiện thông tin mở rộng cho dịch vụ CNTT"
        // if (dichvuvt_id == DichVuVienThong.DICHVU_CNTT || dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT
        //     || dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU || dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH)
        // {
        //     //ds = tcdanhba.LAY_DS_DB_CNTT_THEO_TBID(thuebao_id);
        //     Init_Properties(loaitb_id, "HDTB_CNTT", "00000", ref ItemList);
        // }
        // #endregion

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
          this.leftForm.txtTenTB.value = this.ChuanHoaTen(ds[0]["ten_tb"].toString());
          this.leftForm.txtDiaChiLD.value = ds[0]["diachi_ld"]
            ? this.ChuanHoaTen(ds[0]["diachi_ld"].toString())
            : "";
          if (ds[0]["ma_tb"]) {
            this.leftForm.txtMaTB.value = ds[0]["ma_tb"];
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
            this.leftForm.cboGoiCuocAdsl.value = this.vtocdo_id;
          
          var thang_ht = moment(this.tt_nd.ngay_cn).format('yyyyMM')
          var ds_datcoc = await this.lay_ds_datcoc_theo_tbid(this.thuebao_id, thang_ht)
          this.hdtb_dc_1 = [];
          this.hdtb_dc_2 = [];
          this.dgvDatCoc.list = [];
          if (ds_datcoc && ds_datcoc.length > 0) {
            if (ds_datcoc[0]["khuyenmai_id"]) this.rightForm.cboTraTruoc.value = Number(ds_datcoc[0]["khuyenmai_id"]);
            this.hdtb_dc_1 = ds_datcoc;
            this.hdtb_dc_2 = ds_datcoc;
            this.dgvDatCoc.list = ds_datcoc;
          }
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
      if (!this.isMaTb_enter && this.leftForm.txtMaTB.value != "") {
        this.ShowError(
          "Thuê bao đang có Hợp đồng/Phụ lục mới chưa hoàn thiện!"
        );
        return;
      }
      var check = await this.TaoDL(true, true, true); 
      if (!check) {
        this.$toast.error('Có lỗi trong quá trình tạo dữ liệu!')
        return false
      }
      // datcoc.ThemMoi_Thuebao_tratruoc(dsHDTB, hdtb_dc_1, dsCTTHD, dsHDTB_CNTT, huonggiaotn_id);
      var input = {
        p_hd_thuebao_data: this.dsHDTB && this.dsHDTB.length > 0 ? JSON.stringify(this.dsHDTB) : '', 
        p_js_hdtb_datcoc: this.hdtb_dc_1 && this.hdtb_dc_1.length > 0 ? JSON.stringify(this.hdtb_dc_1) : '', 
        p_ct_tienhd: this.dsCTTHD && this.dsCTTHD.length > 0 ? JSON.stringify(this.dsCTTHD) : '', 
        p_js_hdtb_cntt: this.dsHDTB_CNTT && this.dsHDTB_CNTT.length > 0 ? JSON.stringify(this.dsHDTB_CNTT) : '', 
        l_huonggiaotn_id: this.huonggiaotn_id
      }
      await this.ThemMoi_Thuebao_tratruoc(input);
    },
    txtMaTB_KeyPress: async function () {
      this.loading(true);
      try {
        this.gridDanhSach.list = []
        this.isMaTb_enter = true;
        if (this.khong_load_ctv == 0) this.filterbox.ctv.chkCTV = false;
        this.filterbox.nguoigt.chkNguoiGT = false;
        if (this.leftForm.txtMaTB.value != "") {
          this.keyenter = 1;
          this.dichvuvt_id = Number(this.cp_dichvuvt_id);
          var promiseDB_matb = this.LAY_DANHBA_THEO_MATB(
            this.leftForm.txtMaTB.value,
            this.dichvuvt_id,
            0
          ); //Lấy ra bất kỳ thuê bao nào
          var dt = await promiseDB_matb;
          if (dt && dt.length > 0) {
            this.loaitb_id = dt[0]["loaitb_id"]
              ? Number(dt[0]["loaitb_id"].toString())
              : 0;
            this.leftForm.cboLoaiHinhTB.value = this.loaitb_id;

            var promiseDSHD = this.LAY_DS_HOPDONG_THEO_MA_TB(
              this.leftForm.txtMaTB.value,
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
              (e) => e.id == Number(this.leftForm.cboDichVuVT.value)
            )[0].text;
            this.ShowError(
              "Không tìm thấy thông tin về mã thuê bao " +
                this.leftForm.txtMaTB.value +
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
        this.gridDanhSach.list = []
        this.isMaTb_enter = false;
        if (this.leftForm.txtMaGD.value != "") {
          this.keyenter = 0;
          var promise_dshd = this.LAY_DS_HOPDONG_THEO_MA_GD(
            this.leftForm.txtMaGD.value
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
        await this.XOA_HDKH_V2(Number(this.hdkh_id), this.loaihd_id);
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
        if (this.leftForm.txtMaGD.value != "") {
          // this.$router.push({
          //   name: "Payment",
          //   params: { magd: this.leftForm.txtMaGD.value },
          // });
          let query = {
            magd: this.leftForm.txtMaGD.value.trim(),
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
            await this.Xoa_hdtb_datcoc_hienhuu(this.hdtb_id);

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
        if (this.leftForm.txtMaGD.value == "") {
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
            this.leftForm.cboLoaiHinhTB.value.toString() ==
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
      this.thongtin_tracuu = this.leftForm.txtMaGD.value;
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
          var khuyenmai_id_tt = Number(this.rightForm.cboTraTruoc.value);
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
                Number(e.khuyenmai_id) == Number(this.rightForm.cboTraTruoc.value)
            );
            var ten_km = km && km.length > 0 ? km[0].ten_km : "";
            var ngay_kt_km = "";
            await api
              .post_lay_ngaykt_km(
                this.axios,
                Number(this.rightForm.cboTraTruoc.value)
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
                  this.$refs.dangKyChiTietKhuyenMaiModal.showModal();
                  this.loading(false);
                  return
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
            this.$refs.dangKyChiTietKhuyenMaiModal.showModal();
            this.loading(false);
            return
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
        if (this.rightForm.dtpTuNgay.value) {
          var tungay = new Date(moment(this.rightForm.dtpTuNgay.value, "DD/MM/YYYY"));
          var _sothang = Number(this.cp_txtThoiHan);
          this.rightForm.dtpDenNgay.value = moment(
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
          var check = await this.TaoDuLieu(true, true);
          if (!check) {
            this.$toast.error('Có lỗi trong quá trình tạo dữ liệu!')
            return false
          }
          // if (this.loi_sinh_magd) {
          //   this.loi_sinh_magd = false;
          //   return;
          // }
          // datcoc.ThemMoi_Thuebao_tratruoc(dsHDTB, hdtb_dc_1, dsCTTHD, dsHDTB_CNTT, huonggiaotn_id);
          var input = {
            p_hd_thuebao_data: this.dsHDTB && this.dsHDTB.length > 0 ? JSON.stringify(this.dsHDTB) : '', 
            p_js_hdtb_datcoc: this.hdtb_dc_1 && this.hdtb_dc_1.length > 0 ? JSON.stringify(this.hdtb_dc_1) : '', 
            p_ct_tienhd: this.dsCTTHD && this.dsCTTHD.length > 0 ? JSON.stringify(this.dsCTTHD) : '', 
            p_js_hdtb_cntt: this.dsHDTB_CNTT && this.dsHDTB_CNTT.length > 0 ? JSON.stringify(this.dsHDTB_CNTT) : '', 
            l_huonggiaotn_id: this.huonggiaotn_id
          }
          console.log(input)
          await this.ThemMoi_Thuebao_tratruoc(input);
        } else {
          var kiemtra_Dulieu = await this.Kiemtra_Dulieu(false);
          if (!kiemtra_Dulieu) return;
          var check = await this.TaoDuLieu(false, true);
          if (!check) {
            this.$toast.error('Có lỗi trong quá trình tạo dữ liệu!')
            return false
          }
          // datcoc.CapNhat_Thuebao_tratruoc(dsHDTB, hdtb_dc_1, dsCTTHD, dsHDTB_CNTT);
          var input = {
            p_hd_thuebao_data: this.dsHDTB && this.dsHDTB.length > 0 ? JSON.stringify(this.dsHDTB) : '', 
            p_js_hdtb_datcoc: this.hdtb_dc_1 && this.hdtb_dc_1.length > 0 ? JSON.stringify(this.hdtb_dc_1) : '', 
            p_ct_tienhd: this.dsCTTHD && this.dsCTTHD.length > 0 ? JSON.stringify(this.dsCTTHD) : '', 
            p_js_hdtb_cntt: this.dsHDTB_CNTT && this.dsHDTB_CNTT.length > 0 ? JSON.stringify(this.dsHDTB_CNTT) : '', 
            l_huonggiaotn_id: this.huonggiaotn_id
          }
          console.log(input)
          await this.CapNhat_Thuebao_tratruoc(input)
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
    ThemMoi_Thuebao_tratruoc: async function (input) {
      let response = await api.sp_themmoi_thuebao_tratruoc(this.axios, input);
      if (response.data.error == 200 || response.data.error == "200" || response.data.error_code == "BSS-00000000") {
        //ok;ma_gd;hdkh_id;hdtb_id
        if (response.data.data && response.data.data.toLowerCase().includes("ok")) {
          this.ShowSuccess("Thêm mới dữ liệu thành công!")
          if (response.data.data && response.data.data.includes(';')) {
            var data = response.data.data.split(";");
            if (data[1]) this.leftForm.txtMaGD.value = data[1];
            if (data[2]) this.hdkh_id = Number(data[2]);
          }
          await this.HienThiDanhSacHDTB();
          // await this.txtMaGD_KeyPress();
        } else {
          this.ShowError(response.data.data);
        }
      } else {
        console.log(response.data.message);
        this.ShowError("Lỗi thêm mới thuê bao trả trước");
      }
    },
    CapNhat_Thuebao_tratruoc: async function (input) {
      let response = await api.sp_capnhat_thuebao_tratruoc(this.axios, input);
      if (response.data.error == 200 || response.data.error == "200" || response.data.error_code == "BSS-00000000") {
        //ok;ma_gd;hdkh_id;hdtb_id
        if (!response.data.data) this.ShowError(response.data.message_detail);
        if (response.data.data.toLowerCase().includes("ok")) {
          this.ShowSuccess("Cập nhật dữ liệu thành công!")
          if (response.data.data && response.data.data.includes(';')) {
            var data = response.data.data.split(";");
            if (data[1]) this.leftForm.txtMaGD.value = data[1];
            if (data[2]) this.hdkh_id = Number(data[2]);
          }
          await this.HienThiDanhSacHDTB();
          // await this.txtMaGD_KeyPress();
        } else {
          this.ShowError(response.data.data);
        }
      } else {
        console.log(response.data.message);
        this.ShowError("Lỗi Cập nhật thuê bao trả trước");
      }
    },
    update_thuebao_datcoc: async function (
      dsHDKH,
      dsHDTB,
      hdtb_dc_1,
      dsCTTHD,
      dsHDTB_CNTT,
      huonggiaotn_id,
      is_insert
    ) {
      var input = {
        donvi_id: Number(await this.$root.token.getDonViID()),
        huongiao_id: huonggiaotn_id,
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
        if (response.data.data.toLowerCase().includes("ok")) {
          is_insert == 1
            ? this.ShowSuccess("Thêm mới dữ liệu thành công!")
            : this.ShowSuccess("Cập nhật dữ liệu thành công!");
          var data = response.data.data.split(";");
          if (data[1]) this.leftForm.txtMaGD.value = data[1];
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
      if (!this.hdkh_id) {
        this.$toast.error('Không có HDKH ID, bạn hãy liên hệ admin để xử lý!')
        return
      }
      if (this.phainhap_thoihan) {
        var input = {
          type: 6,
          param: this.leftForm.cboLoaiHinhTB.value.toString(),
        };
        let tt = this.GetData(await api.fn_tt_loaihinh_tb(this.axios, input));
        if (tt && tt.toString() == "1")
          if (this.chkThoiHan.Checked == false) {
            this.ShowError("Bạn phải chọn Thời hạn !");
            return false;
          }
      }
      if (
        Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.DICHVU_CNTT ||
        Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.ANTOAN_BAOMAT_TT ||
        Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.TRUNGTAM_DULIEU ||
        Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.HOINGHI_TRUYENHINH
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
        (e) => Number(e.khuyenmai_id) == Number(this.rightForm.cboTraTruoc.value)
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
      if (!this.leftForm.txtMaTB.value || this.leftForm.txtMaTB.value == "") {
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

      if (Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.DICHVU_CNTT || 
        Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.ANTOAN_BAOMAT_TT || 
        Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.TRUNGTAM_DULIEU || 
        Number(this.leftForm.cboDichVuVT.value) == DichVuVienThong.HOINGHI_TRUYENHINH)
      {
        // Nếu là thuê bao cần chuẩn hóa --> ko cho thực hiện hợp đồng. cần phải Đặt mới (chon kieu ld = chuan hoa) hoặc làm hợp đồng Tạm dừng/Thanh lý
        if (this.check_chuahoa_cntt)
        { 
          var chk_chuanhoa_cntt = this.GetData(await this.sp_check_chuahoa_cntt(this.thuebao_id, this.loaihd_id))
          if (chk_chuanhoa_cntt)
          {
            //Message_Box.ShowWarning("Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới ");
            // if (MessageBox.Show("Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.\n Bạn muốn chuẩn hóa thông tin ?", "Thông báo", MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes) return false;
            // {
            //     var frm = new frmDatMoi();
            //     frm.MdiParent = this.MdiParent;
            //     frm.Show();
            //     frm.Load_hopdong_chuanhoa_cntt(ma_kh, Number(cboDichVuVT.SelectedValue), loaitb_id, thuebao_id);
            // }
            this.$bvModal
              .msgBoxConfirm("Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.\n Bạn muốn chuẩn hóa thông tin ?", {
                title: "Thông báo",
                size: "sm",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
              })
              .then(async (v) => {
                if (v) {
                  this.loading(true);
                  // frm.Load_hopdong_chuanhoa_cntt(ma_kh, Number(cboDichVuVT.SelectedValue), loaitb_id, thuebao_id);

                  this.loading(false);
                }
              })
              .catch((e) => {
                // if (e.data && e.data.message_detail)
                //   this.ShowError("Lỗi chuẩn hóa dữ liệu: " + e.data.message_detail);
                // else this.ShowError("Lỗi chuẩn hóa dữ liệu: " + e);
                console.log(e);
                this.ShowError("Lỗi chuẩn hóa dữ liệu");
                this.loading(false);
              });
            return false;
          }
        }
      }

      if (themmoi) {
        if (this.thuebao_id != 0) {
          var input = {
            p_thuebao_id: this.thuebao_id, 
            p_ma_tb: this.leftForm.txtMaTB.value, 
            p_dichvuvt_id: Number(this.leftForm.cboDichVuVT.value), 
            p_loaitb_id: Number(this.leftForm.cboLoaiHinhTB.value)
          }
          var Kiemtra_loaitb_id_db_thuebao = this.GetData(await api.sp_kiemtra_loaitb_id_db_thuebao(this.axios, input))
          if (!Kiemtra_loaitb_id_db_thuebao)
          {
            this.ShowError("Hãy kiểm tra lại loại hình/dịch vụ/trạng thái của thuê bao. Thuê bao phải ở trạng thái hoạt động bình thường. " + this.leftForm.txtMaTB.value);
            return false;
          }
        }

        // if (this.kiemtra_no_tratruoc == 1) {
        //   var tienno = TraCuu_No_MaTB(txtMaTB.Text.toString().Trim().ToLower());
        //   if (tienno > 0)
        //   {
        //       Message_Box.ShowWarning("Thuê bao: " + txtMaTB.Text.toString().Trim().ToLower()
        //       + " còn nợ: " + Convert.ToDecimal(tienno).toString(Format.FM_TIEN_VND)
        //       + " vnđ.\nPhải thanh toán trước khi thực hiện!");
        //       return false;
        //   }
        // }
      }
      else
      {
        if (this.tthd_id > 1) {
          var promise_tthd = this.trangthai_hd();
          var tthd = await promise_tthd;
          this.ShowError("Hợp đồng " + tthd + ", bạn không thể thay đổi!");
          return false;
        }
      }

      if (!themmoi) {
        if (this.in_ma_tb != this.leftForm.txtMaTB.value) {
          this.ShowError("Bạn không được thay đổi số máy/account");
          return false;
        }
      }

      var thangbdmoi = 0;
      var thangbdmoi = 0;
      var thangktmoi = 0;
      if (themmoi) { 
        // string sql = "select c.ten_loaihd from " + DatabaseConstants.DB2 + ".hd_thuebao a, " + DatabaseConstants.DB2 + ".hd_khachhang b, " + DatabaseConstants.DB2 + ".loai_hd c where a.hdkh_id = b.hdkh_id ";
        // sql += " and b.loaihd_id = c.loaihd_id and a.tthd_id not in (6,7) and b.loaihd_id < 33 and nvl(b.loainc_id,0) <> " + LOAI_NC.DATCOC + " and a.ma_tb = '" + txtMaTB.Text.Trim() + "' and a.loaitb_id = " + Convert.ToInt32(cboLoaiHinhTB.SelectedValue);
        // var dt = bangts.GetDataSql(sql);
        // if (dt.Rows.Count > 0)
        // {
        //     Message_Box.ShowWarning("Thuê bao đã có hợp đồng: " + dt.Rows[0][0].toString() + " chưa hoàn thiện, bạn không thể lập hợp đồng!");
        //     return false;
        // }
      }
      else
      {
        for (var i = 0; i < this.dsDC_HDTB.length; i++)
        {
          if (this.hdtb_dc_2[i]["nhom_datcoc_id"].toString() != "")
          {
            var input = { 
              "p_thuebao_dc_id": 0, 
              "p_ma_tb": this.leftForm.txtMaTB.value, 
              "p_dichvuvt_id": this.leftForm.cboDichVuVT.value, 
              "p_loaitb_id": this.leftForm.cboLoaiHinhTB.value, 
              "p_thang_bd": this.hdtb_dc_2[i]["thang_bd"], 
              "p_thang_kt": this.hdtb_dc_2[i]["thang_kt"], 
              "p_nhom_datcoc_id": this.hdtb_dc_2[i]["nhom_datcoc_id"], 
            }
            var Kiemtra_tb_datcoc = this.GetData(await api.sp_kiemtra_tb_datcoc(this.axios, input))
            if (!Kiemtra_tb_datcoc) {
              this.ShowError("Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!");
              return false;
            }
            var Kiemtra_datcoc_tyle_KM_v1 = this.GetData(await api.sp_kiemtra_datcoc_tyle_km_v1(this.axios, input))
            //nhapt 12042017: Thực hiện kiểm tra; nếu có khuyến mại 100% trùng thời gian đặt cọc thì thông báo
            if (Kiemtra_datcoc_tyle_KM_v1) {
              this.ShowError("Dữ liệu bị trùng khoảng thời gian hưởng khuyến mại. Bạn hãy kiểm tra lại!");
              return false;
            }
          }
        }
      }

      if (this.leftForm.cboLoaiHinhTB.value == LoaiHinhTB.INTERNET_MYTV) {
          var in_ma_tb = this.leftForm.txtMaTB.value;

          for (var i = 0; i < this.dsDC_HDTB.length; i++)
          {
            var promise_kt2731 = this.kiemtra_km_2731("0",Number(this.dsDC_HDTB[i]["chitietkm_id"]),0,in_ma_tb);
            var ckb_km = await promise_kt2731;
            
            if (ckb_km == null || !ckb_km)
            {
              this.ShowError("Không lấy được thông tin kiểm tra đặt cọc");
              return false;
            }
            if (ckb_km.toString() != "1" && ckb_km.toString() != "-1")
            {
              this.ShowError(ckb_km.toString());
              return false;
            }
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
                    this.hdtb_dc_2[j]["datcoc_csd"] &&
                    Number(this.hdtb_dc_2[j]["datcoc_csd"].toString()) > 0 &&
                    this.hdtb_dc_2[i]["datcoc_csd"] &&
                    Number(this.hdtb_dc_2[i]["datcoc_csd"].toString()) > 0
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
    TaoDuLieu: async function (themmoi, KoTaoHDKH) {
      var check = await this.TaoDL(false, themmoi, KoTaoHDKH);
      return check
    },
    TaoDL: async function (themTB, themmoi, KoTaoHDKH) {
      try {
        var check = true
        if (!KoTaoHDKH) check = await this.TaoDuLieu_HDKH(themmoi, check);
        // if (this.loi_sinh_magd) return;

        if (Number(this.leftForm.cboKieuLD.value) == KieuLapDat.DATCOC_MOI) {
          check = await this.TaoDuLieu_HDTB(themTB, themmoi, check);
          check = await this.TaoDuLieu_HDTB_DATCOC(themmoi, check);
          check = await this.TaoDuLieu_CT_TIENHD(themmoi, check);
          if (
            Number(this.cp_dichvuvt_id) == DichVuVienThong.ANTOAN_BAOMAT_TT ||
            Number(this.cp_dichvuvt_id) == DichVuVienThong.DICHVU_CNTT ||
            Number(this.cp_dichvuvt_id) ==
              DichVuVienThong.HOINGHI_TRUYENHINH ||
            Number(this.cp_dichvuvt_id) == DichVuVienThong.TRUNGTAM_DULIEU
          ) {
            check = await this.Tao_HDTB_CNTT(themmoi, check);
          }
        }

        if (Number(this.leftForm.cboKieuLD.value) == KieuLapDat.THAYDOIGOICUOC_DATCOC) {
          check = await this.TaoDuLieu_HDTB_HUY(themTB, themmoi, check);
          check = await this.TaoDuLieu_HDTB_DATCOC_HUY(themmoi, check);
          check = await this.TaoDuLieu_CT_TIENHD_HUY(themmoi, check);

          check = await this.TaoDuLieu_HDTB_moi(themmoi, check);
          check = await this.TaoDuLieu_HDTB_DATCOC_moi(true, check);
          check = await this.TaoDuLieu_CT_TIENHD_moi(true, check);
        }
        return check
      } catch (e) {
        console.log('TaoDL', e)
        return false
      }
    },
    TaoDuLieu_HDKH: async function (themmoi, check) {
      try {
        var result = check
        if (!result) {
          console.log('Lỗi TaoDuLieu_HDKH')
          return false
        }
        this.dsHDKH = [];
        var rowHDKH = {};
        if (themmoi == true)  {
          this.hdkh_id = await this.GET_KEYS("HD_KHACHHANG");
          this.nhomtb_id = await this.GET_KEYS("NHOMTB");
        }
        if (themmoi == true) {
          rowHDKH.NGAY_YC = moment(new Date()).format("DD/MM/YYYY");
        } else {
          rowHDKH.HDKH_ID = Number(this.hdkh_id);
          rowHDKH.MA_GD = this.leftForm.txtMaGD.value;
          rowHDKH.NGAY_YC = this.leftForm.dtpNgayYeuCau.value ;
        }
        rowHDKH.MA_KH = this.ma_kh;
        if (this.khachhang_id > 0) rowHDKH.KHACHHANG_ID = this.khachhang_id;
        rowHDKH.KHLON_ID = this.khlon_id;
        rowHDKH.TEN_KH = this.ten_kh;
        rowHDKH.DIACHI_KH = this.diachi_kh;
        rowHDKH.NGAYLAP_HD = this.dtpNgayLHD;

        rowHDKH.GHICHU = this.leftForm.txtGhiChu.value;
        rowHDKH.DONVI_ID = Number(await this.$root.token.getDonViID());
        rowHDKH.NHANVIEN_ID = Number(await this.$root.token.getNhanVienID());
        rowHDKH.LOAIHD_ID = this.loaihd_id;
        rowHDKH.NGUOI_CN = await this.$root.token.getUserName();
        rowHDKH.MAY_CN = await this.$root.token.getMaNhanVien();
        rowHDKH.NGAY_CN = moment(this.tt_nd.ngay_cn).format("DD/MM/YYYY");

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
        return result
      } catch (e) {
        console.log('TaoDuLieu_HDKH',e)
        return false
      }
    },
    TaoDuLieu_HDTB: async function (themTB, themmoi, check) {
      try {
        var result = check
        if (!result) {
          console.log('Lỗi TaoDuLieu_HDTB')
          return false
        }
        this.dsHDTB = [];
        var rowHDTB = {};
        if (themmoi == true)
          this.hdtb_id = await this.GET_KEYS("HD_THUEBAO");
        rowHDTB.HDTB_ID = this.hdtb_id;
        rowHDTB.HDKH_ID = this.hdkh_id;
        if (this.thuebao_id > 0) rowHDTB.THUEBAO_ID = this.thuebao_id;
        rowHDTB.MA_TB = this.leftForm.txtMaTB.value;
        rowHDTB.TEN_TB = this.leftForm.txtTenTB.value.trim();
        rowHDTB.DIACHI_LD = this.leftForm.txtDiaChiLD.value.trim();
        rowHDTB.DIACHI_TB = this.leftForm.txtDiaChiLD.value.trim();
        rowHDTB.KIEULD_ID = KieuLapDat.DATCOC_MOI; // Kiểu đặt cọc mới
        rowHDTB.DOITUONG_ID = this.doituong_id;
        rowHDTB.TTHD_ID = TrangThaiHD.MOI;
        rowHDTB.LOAITB_ID = this.loaitb_id;
        rowHDTB.DICHVUVT_ID = this.dichvuvt_id;
        rowHDTB.GHICHU = this.leftForm.txtGhiChu.value.trim();
        rowHDTB.DONVI_ID = this.donvi_id;
        rowHDTB.NGUOI_CN = await this.$root.token.getUserName();
        rowHDTB.NGAY_CN = moment(this.tt_nd.ngay_cn).format("DD/MM/YYYY");
        rowHDTB.MAY_CN = await this.$root.token.getMaNhanVien();

        // var ts_loaihd_id = this.GetData(
        //   await api.map_id_dk_tratruoc(this.axios, {
        //     p_type: 2, // loaihd theo kieuld
        //     p_param: Number(this.leftForm.cboKieuLD.value),
        //   })
        // );
        // var loaihd_id = 0
        // if (ts_loaihd_id && ts_loaihd_id.length > 0) this.loaihd_id = Number(ts_loaihd_id)
        var dset = this.GetData(
          await api.LayHuongGiao_TiepNhan(this.axios, {
            kieuLdId: Number(this.leftForm.cboKieuLD.value),
            loaiHdId: this.loaihd_id,
            loaiTbId: this.loaitb_id,
            mucCuocId: this.vmuccuoc_id,
            tocDoId: this.vtocdo_id,
          })
        );

        this.huonggiaotn_id = 0;
        this.quytrinh_id = 0;
        if (dset && dset instanceof Array && dset.length > 0) {
          this.huonggiaotn_id = Number(dset[0]["huonggiao_id"]);
          this.quytrinh_id = Number(dset[0]["quytrinh_id"]);
        } else if (dset && dset instanceof Object) {
          this.huonggiaotn_id = Number(dset["huonggiao_id"]);
          this.quytrinh_id = Number(dset["quytrinh_id"]);
        } else {
          this.$toast.error('Quy trình chưa được thiết lập, hãy liên hệ admin để xử lý!')
          return false
        }
        
        rowHDTB.QUYTRINH_ID = this.quytrinh_id;
        this.dsHDTB.push(rowHDTB);
        return result
      } catch (e) {
        console.log('TaoDuLieu_HDTB',e)
        return false
      }
    },
    TaoDuLieu_HDTB_DATCOC: async function (themmoi, check) {
      try {
        var result = check
        if (!result) {
          console.log('Lỗi TaoDuLieu_HDTB_DATCOC')
          return false
        }
        this.hdtb_dc_1 = [];
        var hdtb_dc_1 = [];
        for (var i = 0; i < this.hdtb_dc_2.length; i++) {
          var rowGOI = {};
          this.thuebao_dc_id = await this.GET_KEYS("THUEBAO_DC_ID");
          this.rkm_id = await this.GET_KEYS("RKM_ID");
          rowGOI.RKM_ID = this.rkm_id;
          rowGOI.THUEBAO_DC_ID = this.thuebao_dc_id;
          rowGOI.HDTB_ID = this.hdtb_id;
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

          rowGOI.NGAY_BDDC = this.rightForm.dtpNgayBDDC.value
          rowGOI.NGAY_KTDC = this.rightForm.dtpNgayKTDC.value

          rowGOI.NGAY_DK = this.leftForm.dtpNgayYeuCau.value ;
          rowGOI.CHITIETKM_ID = this.hdtb_dc_2[i]["chitietkm_id"]
            ? Number(this.hdtb_dc_2[i]["chitietkm_id"])
            : 0;
          rowGOI.CUOC_DC = this.hdtb_dc_2[i]["datcoc_csd"]
            ? Number(this.hdtb_dc_2[i]["datcoc_csd"])
            : 0;
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
        return result
      } catch (e) {
        console.log('TaoDuLieu_HDTB_DATCOC',e)
        return false
      }
    },
    TaoDuLieu_HDTB_HUY: async function (themTB, themmoi, check) {
      try {
        var result = check
        if (!result) {
          console.log('Lỗi TaoDuLieu_HDTB_HUY')
          return false
        }
        this.dsHDTB = []      
        
        var rowHDTB = {}
        if (themmoi == true)
          this.hdtb_id = await this.GET_KEYS("HD_THUEBAO");
        rowHDTB.HDTB_ID = this.hdtb_id
        rowHDTB.HDKH_ID = this.hdkh_id && Number(this.hdkh_id) ? Number(this.hdkh_id) : 0;;
        
        rowHDTB.THUEBAO_ID = this.thuebao_id && Number(this.thuebao_id) > 0 ? Number(this.thuebao_id) : 0;
        rowHDTB.MA_TB = this.leftForm.txtMaTB.value;
        rowHDTB.TEN_TB = this.leftForm.txtTenTB.value;
        rowHDTB.DIACHI_LD = this.leftForm.txtDiaChiLD.value.trim();
        rowHDTB.DIACHI_TB = this.leftForm.txtDiaChiLD.value.trim();

        rowHDTB.KIEULD_ID = Number(this.leftForm.cboKieuLD.value); // Kiểu đặt cọc mới
        rowHDTB.DOITUONG_ID = this.doituong_id;
        rowHDTB.TTHD_ID = TrangThaiHD.MOI;
        rowHDTB.LOAITB_ID = this.loaitb_id;
        rowHDTB.DICHVUVT_ID = this.dichvuvt_id;
        rowHDTB.GHICHU = this.leftForm.txtGhiChu.value.trim();
        rowHDTB.DONVI_ID = this.donvi_id;
        rowHDTB.NGUOI_CN = await this.$root.token.getUserName();
        rowHDTB.NGAY_CN = moment(this.tt_nd.ngay_cn).format("DD/MM/YYYY");
        rowHDTB.MAY_CN = await this.$root.token.getMaNhanVien();
        rowHDTB.TBHD_PL = 0;
        rowHDTB.STATUS = "0";

        // vinhpv lay huonggiao_id
        // var dset = await api.LayHuongGiao_TiepNhan(LoaiHopDong.THAYDOI_DATCOC, Number(cboKieuLD.SelectedValue)); // Kieu
       
        // var ts_loaihd_id = this.GetData(
        //   await api.map_id_dk_tratruoc(this.axios, {
        //     p_type: 2, // loaihd theo kieuld
        //     p_param: Number(this.leftForm.cboKieuLD.value),
        //   })
        // );
        // var loaihd_id = 0
        // if (ts_loaihd_id && ts_loaihd_id.length > 0) loaihd_id = Number(ts_loaihd_id)
        var dset = this.GetData(
          await api.LayHuongGiao_TiepNhan(this.axios, {
            loaiHdId: this.loaihd_id,
            kieuLdId: Number(this.leftForm.cboKieuLD.value),
            loaiTbId: this.loaitb_id,
            mucCuocId: this.vmuccuoc_id,
            tocDoId: this.vtocdo_id,
          })
        );
        this.huonggiaotn_id = 0;
        this.quytrinh_id = 0;
        if (dset && dset instanceof Array && dset.length > 0) {
          this.huonggiaotn_id = Number(dset[0]["huonggiao_id"]);
          this.quytrinh_id = Number(dset[0]["quytrinh_id"]);
        } else if (dset && dset instanceof Object) {
          this.huonggiaotn_id = Number(dset["huonggiao_id"]);
          this.quytrinh_id = Number(dset["quytrinh_id"]);
        } else {
          this.$toast.error('Quy trình chưa được thiết lập, hãy liên hệ admin để xử lý!')
          return false
        }
        
        rowHDTB.QUYTRINH_ID = this.quytrinh_id;
        this.dsHDTB.push(rowHDTB);
        return result
      } catch (e) {
        console.log('TaoDuLieu_HDTB_HUY',e)
        return false
      }
    },
    TaoDuLieu_HDTB_DATCOC_HUY: async function (themmoi, check) {
      try {
        var result = check
        if (!result) {
          console.log('Lỗi TaoDuLieu_HDTB_DATCOC_HUY')
          return false
        }
        this.hdtb_dc_1 = [];
        var hdtb_dc_1 = [];
        if (!this.hdtb_dc_2 || this.hdtb_dc_2.length <= 0) return true
        for (var i = 0; i < this.hdtb_dc_2.length; i++) {
          var rowGOI = {};
          if (!this.leftForm.txtTienThoaiTra.value) return true
          if (themmoi == true)
            this.thuebao_dc_id = await this.GET_KEYS("THUEBAO_DC_ID");
          rowGOI.THUEBAO_DC_ID = this.thuebao_dc_id;
          rowGOI.THUEBAO_THOAITRA_ID = this.thuebao_thoaitra_id;
          rowGOI.RKM_ID = this.rkm_id;
          rowGOI.HDTB_ID = this.hdtb_id;
          rowGOI.NHOM_DATCOC_ID = this.nhom_datcoc_id_cu;
          rowGOI.THANG_BD = this.thang_bd_cu;
          rowGOI.THANG_KT = this.thang_kt_cu;
          rowGOI.THANG_BD_MG = 0;
          rowGOI.THANG_KT_MG = 0;
          rowGOI.NGAY_DK = this.leftForm.dtpNgayYeuCau.value;
          rowGOI.GHICHU = this.leftForm.txtGhiChu.value;
          rowGOI.THANG_THOAITRA = moment(this.leftForm.dtpThangThoaiTra.value,'MM/YYYY').format('YYYYMM')
          rowGOI.NGAY_THOAITRA = this.leftForm.dtpNgayThoaiTra.value
          hdtb_dc_1.push(rowGOI);
        }
        this.hdtb_dc_1 = hdtb_dc_1;
        return result
      } catch (e) {
        console.log('TaoDuLieu_HDTB_DATCOC_HUY',e)
        return false
      }        
    },
    TaoDuLieu_HDTB_DATCOC_moi: async function (themmoi, check) {
      try {
        var result = check
        if (!result) {
          console.log('Lỗi TaoDuLieu_HDTB_DATCOC_moi')
          return false
        }
        this.hdtb_dc_1 = [];
        if (!this.hdtb_dc_2 || this.hdtb_dc_2.length <= 0) return true
        for (var i = 0; i < this.hdtb_dc_2.length; i++) {
          var rowGOI = {};
          this.thuebao_dc_id = await this.GET_KEYS("THUEBAO_DC_ID");
          this.rkm_id = await this.GET_KEYS("RKM_ID");
          rowGOI.RKM_ID = this.rkm_id;
          rowGOI.THUEBAO_DC_ID = this.thuebao_dc_id;
          rowGOI.HDTB_ID = this.hdtb_dc_id;
          rowGOI.NHOM_DATCOC_ID = Number(this.hdtb_dc_2[i]["nhom_datcoc_id"].toString());
          rowGOI.THANG_BD = this.hdtb_dc_2[i]["thang_bd"] ? Number(this.hdtb_dc_2[i]["thang_bd"].toString()) : 
            this.hdtb_dc_2[i]["thang_bddc"] ? this.hdtb_dc_2[i]["thang_bddc"] : '';
          rowGOI.THANG_KT = this.hdtb_dc_2[i]["thang_kt"] ? Number(this.hdtb_dc_2[i]["thang_kt"].toString()) :
            this.hdtb_dc_2[i]["thang_ktdc"] ? this.hdtb_dc_2[i]["thang_ktdc"] : '';

          if (this.hdtb_dc_2[i]["thang_bd_mg"] && this.hdtb_dc_2[i]["thang_bd_mg"].toString() != "")
            rowGOI.THANG_BD_MG = this.hdtb_dc_2[i]["thang_bd_mg"].toString();
          else
            rowGOI.THANG_BD_MG = 0;

          if (this.hdtb_dc_2[i]["thang_kt_mg"] && this.hdtb_dc_2[i]["thang_kt_mg"].toString() != "")
            rowGOI.THANG_KT_MG = this.hdtb_dc_2[i]["thang_kt_mg"].toString();
          else
            rowGOI.THANG_KT_MG = 0;

          rowGOI.NGAY_BDDC = this.rightForm.dtpNgayBDDC.value
          rowGOI.NGAY_KTDC = this.rightForm.dtpNgayKTDC.value

          rowGOI.NGAY_DK = this.leftForm.dtpNgayYeuCau.Value;
          rowGOI.CHITIETKM_ID = this.hdtb_dc_2[i]["chitietkm_id"];
          rowGOI.CUOC_DC = this.hdtb_dc_2[i]["datcoc_csd"];
          rowGOI.TIEN_TD = this.hdtb_dc_2[i]["tien_td"];

          rowGOI.TIEN_SD = this.hdtb_dc_2[i]["tien_sd"];
          rowGOI.TIEN_TB = this.hdtb_dc_2[i]["tien_tb"];
          rowGOI.CUOC_SD = this.hdtb_dc_2[i]["cuoc_sd"];
          rowGOI.CUOC_TB = this.hdtb_dc_2[i]["cuoc_tb"];
          rowGOI.TYLE_SD = this.hdtb_dc_2[i]["tyle_sd"];
          rowGOI.TYLE_TB = this.hdtb_dc_2[i]["tyle_tb"];

          this.hdtb_dc_1.push(rowGOI)
        }
        return result
      } catch (e) {
        console.log('TaoDuLieu_HDTB_DATCOC_moi',e)
        return false
      }
    },
    //long hdtb_dc_id = 0;
    TaoDuLieu_HDTB_moi: async function (themmoi, check) {
      try {
        var result = check
        if (!result) {
          console.log('Lỗi TaoDuLieu_HDTB_moi')
          return false
        }
        var rowHDTB = {}
        // var loaihd_id = Number(checkdata.MAP_ID("loaihd_id", DatabaseConstants.DB2 + ".kieu_ld",
        //     "where kieuld_id =" + Number(cboKieuLD.SelectedValue)));

        if (themmoi == true)
          this.hdtb_dc_id = await this.GET_KEYS("HD_THUEBAO");
        rowHDTB.HDTB_ID = this.hdtb_dc_id;
        rowHDTB.HDTB_CHA_ID = this.hdtb_id;
        rowHDTB.HDKH_ID = this.hdkh_id && Number(this.hdkh_id) > 0 ? Number(this.hdkh_id) : 0;
        rowHDTB.THUEBAO_ID = this.thuebao_id && Number(this.thuebao_id) > 0 ? Number(this.thuebao_id) : 0;
            
        rowHDTB.MA_TB = this.leftForm.txtMaTB.value;
        rowHDTB.TEN_TB = this.leftForm.txtTenTB.value;
        rowHDTB.DIACHI_LD = this.leftForm.txtDiaChiLD.value;
        rowHDTB.DIACHI_TB = this.diachi_ld;
        rowHDTB.KIEULD_ID = Number(this.leftForm.cboKieuLD.value); // Kiểu đặt cọc mới
        rowHDTB.DOITUONG_ID = this.doituong_id;
        rowHDTB.TTHD_ID = TrangThaiHD.MOI;
        rowHDTB.LOAITB_ID = this.loaitb_id;
        rowHDTB.DICHVUVT_ID = this.dichvuvt_id;
        rowHDTB.GHICHU = this.leftForm.txtGhiChu.value;
        rowHDTB.DONVI_ID = this.donvi_id;
        rowHDTB.NGUOI_CN = await this.$root.token.getUserName();
        rowHDTB.NGAY_CN = moment(this.tt_nd.ngay_cn).format("DD/MM/YYYY");
        rowHDTB.MAY_CN = await this.$root.token.getMaNhanVien();
        rowHDTB.TBHD_PL = 0;
        rowHDTB.STATUS = "0";

        // var ts_loaihd_id = this.GetData(
        //   await api.map_id_dk_tratruoc(this.axios, {
        //     p_type: 2, // loaihd theo kieuld
        //     p_param: Number(this.leftForm.cboKieuLD.value),
        //   })
        // );
        // var loaihd_id = 0
        // if (ts_loaihd_id && ts_loaihd_id.length > 0) loaihd_id = Number(ts_loaihd_id)
        var dset = this.GetData(
          await api.LayHuongGiao_TiepNhan(this.axios, {
            loaiHdId: this.loaihd_id,
            kieuLdId: Number(this.leftForm.cboKieuLD.value),
            loaiTbId: this.loaitb_id,
            mucCuocId: this.vmuccuoc_id,
            tocDoId: this.vtocdo_id,
          })
        );
        
        this.huonggiaotn_id = 0
        this.quytrinh_id = 0
        if (dset && dset instanceof Array && dset.length > 0) {
          this.huonggiaotn_id = Number(dset[0]["huonggiao_id"]);
          this.quytrinh_id = Number(dset[0]["quytrinh_id"]);
        } else if (dset && dset instanceof Object) {
          this.huonggiaotn_id = Number(dset["huonggiao_id"]);
          this.quytrinh_id = Number(dset["quytrinh_id"]);
        } else {
          this.$toast.error('Quy trình chưa được thiết lập, hãy liên hệ admin để xử lý!')
          return false
        }
        rowHDTB.QUYTRINH_ID = this.quytrinh_id;

        this.dsHDTB.push(rowHDTB)
        return result
      } catch (e) {
        console.log('TaoDuLieu_HDTB_moi', e)
        return false
      }
    },
    TaoDuLieu_CT_TIENHD_moi: async function (themmoi, check) {
      try {
        //dsCTTHD = new CT_TIENHD_DATA();
        var result = check
        if (!result) {
          console.log('Lỗi TaoDuLieu_CT_TIENHD_moi')
          return false
        }
        var row = {};
        row.HDTB_ID = this.hdtb_dc_id;
        if (themmoi == true)
        {
          this.CT_TIENHD_DC_MOI_ID = await this.GET_KEYS("CT_TIENHD");
        }
        row.CT_TIENHD_ID = this.CT_TIENHD_DC_MOI_ID;
        row.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DATCOC;
        var tien = 0;
        var vat = 0;
        tien = this.rightForm.txtCuocDC.value ? Math.round(Number(this.rightForm.txtCuocDC.value)/1.1) : 0;
        // (Math.Round(Convert.ToDecimal(CommonFunction.Unformat_tien(txtCuocDC.Text)) / (decimal)1.1));
        vat = this.rightForm.txtCuocDC.value ? Number(this.rightForm.txtCuocDC.value) - tien : 0;
        // ((Convert.ToDecimal(CommonFunction.Unformat_tien(txtCuocDC.Text))) - tien);
        row.TIEN = tien;
        row.VAT = vat;
        row.ID = KHOANMUC_TT.KMTT_DATCOC;
        row.LOAI_ID = 1;
        this.dsCTTHD.push(row)
        return result
      } catch (e) {
        console.log('TaoDuLieu_CT_TIENHD_moi', e)
        return false
      }
    },
    TaoDuLieu_CT_TIENHD_HUY: async function (themmoi, check) {
      try {
        var result = check
        if (!result) {
          console.log('Lỗi TaoDuLieu_CT_TIENHD_HUY')
          return false
        }
        this.dsCTTHD = [];
        var row = {};
        row.HDTB_ID = this.hdtb_id;
        if (themmoi) {
          this.CT_TIENHD_ID = await this.GET_KEYS("CT_TIENHD");
        }
        row.CT_TIENHD_ID = this.CT_TIENHD_ID;
        row.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_TIENTHOAI_TDDC;
        var tien = 0;
        var vat = 0;
        tien = this.leftForm.txtTienThoaiTra.value ? Math.round(Number(this.leftForm.txtTienThoaiTra.value)/1.1) : 0;
        vat = this.leftForm.txtTienThoaiTra.value ? Number(this.leftForm.txtTienThoaiTra.value) - tien : 0;

        if (tien > 0)
        {
          row.TIEN = tien * -1;
          row.VAT = vat * -1;
        }
        else
        {
          row.TIEN = tien;
          row.VAT = vat;
        }

        row.ID = KHOANMUC_TT.KMTT_TIENTHOAI_TDDC;
        row.LOAI_ID = 1;
        this.dsCTTHD.push(row)
        return result
      } catch (e) {
        console.log('TaoDuLieu_CT_TIENHD_HUY', e)
        return false
      }        
    },
    TaoDuLieu_CT_TIENHD: async function (themmoi, check) {
      try {
        var result = check
        if (!result) {
          console.log('Lỗi TaoDuLieu_CT_TIENHD')
          return false
        }
        this.dsCTTHD = [];
        for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (this.ds_tien_khoanmuc[i]["tien"] > 0) {
            var rowCTTHD = {};
            rowCTTHD.HDTB_ID = Number(this.hdtb_id);
            if (themmoi == true)
            {
              this.CT_TIENHD_ID = await this.GET_KEYS("CT_TIENHD");
              rowCTTHD.CT_TIENHD_ID = this.CT_TIENHD_ID;
            }
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
        return result
      } catch (e) {
        console.log('TaoDuLieu_CT_TIENHD', e)
        return false
      }
    },
    Tao_HDTB_CNTT: async function (themmoi, check) {
      try {
        var result = check
        if (!result) {
          console.log('Lỗi Tao_HDTB_CNTT')
          return false
        }
        this.dsHDTB_CNTT = [];
        var promise = this.LAY_DS_DB_CNTT_THEO_TBID(this.thuebao_id);
        var dt = await promise;
        if (dt && dt.length > 0) {
          var ItemList = dt[0];
          ItemList["SO_THANG"] = this.rightForm.txtThoiHan.value.trim();
          ItemList["NGAY_BD"] = this.rightForm.dtpTuNgay.value.trim();
          ItemList["NGAY_KT"] = this.rightForm.dtpDenNgay.value.trim();
          this.dsHDTB_CNTT.push(ItemList);
        }
        return result
      } catch (e) { 
        console.log('Tao_HDTB_CNTT', e) 
        return false
      }
    },
    tinhtien_thoai() {
      // var tienthoai = 0;
      // var ton_ck = 0;
      // if (this.nhom_datcoc_id_cu == 2 && DatabaseConstants.DB2 == "CSS_HPG") // Đặt cọc lấy trong qltn với kỳ hóa đơn hiện tại
      // {
      //     string sql = "select sum(ton_ck) from " + DatabaseConstants.DB3 + ".tb_datcoc where kyhoadon = " + tt_nd.ngay_cn.AddMonths(-1).ToString("yyyyMM") + "01" + " and ma_tb = '" + txtMaTB.Text + "' and dichvuvt_id = " + cboDichVuVT.SelectedValue.ToString();
      //     var dt = bangts.GetDataSql(sql);
      //     if (dt.Rows[0][0].ToString() != "")
      //         ton_ck = Convert.ToDecimal(dt.Rows[0][0]);
      //     else
      //         ton_ck = cuoc_dc_cu;
      // }
      // if (thang_bd_cu != 0)
      // {
      //     int thang_thoai = Convert.ToInt32(dtpThangThoaiTra.Value.ToString("yyyyMM"));
      //     int sothang = bangts.Months_between_2(thang_thoai.ToString(), thang_kt_cu.ToString()) - 1;
      //     int sothangcu = bangts.Months_between_2(thang_bd_cu.ToString(), thang_kt_cu.ToString());
      //     decimal tientd = tien_td_cu;
      //     if (tien_td_cu == 0)
      //         tientd = Math.Round(cuoc_dc_cu / bangts.Months_between_2(thang_bd_cu.ToString(), thang_kt_cu.ToString()));
      //     if (sothang >= sothangcu)
      //     {
      //         sothang = sothangcu;
      //         tienthoai = cuoc_dc_cu;
      //     }
      //     else
      //     {
      //         tienthoai = (tientd * sothang);
      //     }
      //     if (tienthoai > cuoc_dc_cu)
      //         tienthoai = cuoc_dc_cu;
      //     // end hoang
      // }
      // else
      //     tienthoai = cuoc_dc_cu;
      // if (tienthoai < 0) tienthoai = 0;
      // txtTienThoaiTra.Text = tienthoai.ToString("N0");
      // _tienthoai = tienthoai;
    },
    dgvDatCoc_FocusedRowChanged: function (data) {
      try {
        var drs = []
        if (data instanceof Array) drs = data
        else if (data instanceof Object) drs = [data]
        else if (!data || data.length <= 0) return
        console.log('dgvDatCoc_FocusedRowChanged',drs)
        this.rightForm['dtpThangBD']['Enabled'] = false;
        this.rightForm['txtSoThang']['Enabled'] = false;
        this.rightForm['txtCuocDC']['Enabled'] = false;
        this.rightForm['txtTienTD']['Enabled'] = false;

        this.rightForm['dtpThangBDMG']['Enabled'] = false;
        this.rightForm['txtSoThangMG']['Enabled'] = false;
        this.rightForm['txtTienTB']['Enabled'] = false;
        this.rightForm['txtTienSD']['Enabled'] = false;        
        this.rightForm['txtTyLeSD']['Enabled'] = false;
        this.rightForm['txtTyLeTB']['Enabled'] = false;
        if (drs && drs.length > 0) {
          this.rightForm.dtpNgayBDDC.value = drs[0]["ngay_bddc"] ? moment(drs[0]["ngay_bddc"]).format('DD/MM/YYYY') : ''
          this.rightForm.dtpNgayKTDC.value = drs[0]["ngay_ktdc"] ? moment(drs[0]["ngay_ktdc"]).format('DD/MM/YYYY') : ''
          this.rightForm.txtSoThang.value = drs[0]["thang_huongdc"] ? drs[0]["thang_huongdc"] : 0;
          this.rightForm.txtCuocDC.value = drs[0]["datcoc_csd"] ? drs[0]["datcoc_csd"] : 0;
          this.rightForm.txtTienTD.value = drs[0]["tien_td"];
          // dateTo.getMonth() - dateFrom.getMonth() + (12 * (dateTo.getFullYear() - dateFrom.getFullYear()))
          // var dateFrom = new Date(moment(drs[0]["thang_bd_mg"].toString(),'YYYYMM'))
          // var dateTo = new Date(moment(drs[0]["thang_kt_mg"].toString(),'YYYYMM'))
          this.rightForm.txtSoThangMG.value = drs[0]["thang_huongkm"] ? drs[0]["thang_huongkm"] : 0;
          this.rightForm.txtTienTB.value = drs[0]["tien_tb"];
          this.rightForm.txtTienSD.value = drs[0]["tien_sd"];
          this.rightForm.txtTyLeSD.value = drs[0]["tyle_sd"];
          this.rightForm.txtTyLeTB.value = drs[0]["tyle_tb"];
          
          // Tháng bắt đầu đặt cọc
          switch (Number(drs[0]["loai_bddc"]))
          {
            case 1:
              this.rightForm.dtpThangBD.value = new Date(moment(drs[0]["thang_bddc"].toString(),'YYYYMM'));
              break;
            default:
              this.rightForm['dtpThangBD']['Enabled'] = true;
              break;
          }

          // Tháng bắt đầu huong km
          var thang_bdkm = drs[0]["thang_bdkm"] ? drs[0]["thang_bdkm"] : drs[0]["thang_bdkm"] ? drs[0]["thang_bd"] : ''
          if (thang_bdkm) {
            switch (Number(drs[0]["loai_bdkm"]))
            {
              case 1:
                this.rightForm.dtpThangBDMG.value = new Date(moment(thang_bdkm.toString(),'YYYYMM'));
                break;
              case 2:
                var date = new Date(moment(thang_bdkm.toString(),'YYYYMM'))
                this.rightForm.dtpThangBDMG.value = new Date(date.setMonth(date.getMonth() + Number(drs[0]["huong_dc"]) - 1))
                // this.dtpThangBD.value.AddMonths(Number(drs[0]["thang_bdkm"]) + Number(drs[0]["huong_dc"]) - 1);
                break;
              case 4:
                var date = new Date(moment(thang_bdkm.toString(),'YYYYMM'))
                this.rightForm.dtpThangBDMG.value = new Date(date.setMonth(date.getMonth()))
                // this.dtpThangBD.value.AddMonths(Number(drs[0]["thang_bdkm"]));
                break;
              default:
                this.rightForm['dtpThangBDMG']['Enabled'] = true;
                break;
            }
          }
          
          this.rightForm['txtCuocDC']['Enabled'] = (Number(drs[0]["kieu_tratruoc"]) == 1) ? true : false;
          this.thang_bd_cu = Number(drs[0]["thang_bd"]);
          this.thang_kt_cu = Number(drs[0]["thang_kt"]);
          // try { _INTngay_yc = Number(r["ngay_yc"]); }
          // catch { }
          this.nhom_datcoc_id_cu = Number(drs[0]["nhom_datcoc_id"]);
          this.cuoc_dc_cu = Number(drs[0]["datcoc_csd"]);
          this.tien_td_cu = Number(drs[0]["tien_td"]);
          this.thuebao_thoaitra_id = Number(drs[0]["thuebao_dc_id"]);
          this.leftForm.txtTienThoaiTra.value = drs[0]["tien_thoai"] ? drs[0]["tien_thoai"] : 0
          if (drs[0]["ngay_thoai"]) {
            this.leftForm.dtpNgayThoaiTra.value =  moment(drs[0]["ngay_thoai"]).format('DD/MM/YYYY') 
            this.leftForm.dtpThangThoaiTra.value = moment(drs[0]["ngay_thoai"]).format('MM/YYYY') 
          }
          // try
          // {
          //     _thoaitra = Number([0]["thoaitra"]);
          // }
          // catch { }
          this.rkm_id = Number(drs[0]["rkm_id"]);
          if (drs[0]["ttdc_id"] && drs[0]["ttdc_id"].toString() != "")
            this.ttdc_id = Number(drs[0]["ttdc_id"]);
          // if (this.dscheck_thoai > 0) {
          //   var _tienthoai = tchopdong2.Lay_tienthoai_datcoc_v2(rkm_id, thuebao_thoaitra_id, Number(dtpThangThoaiTra.value.toString("yyyyMM")));
          //   this.leftForm.txtTienThoaiTra.value = _tienthoai.toString("N0");
          // } else this.tinhtien_thoai();
          this.HienThiTien_TraTruoc()
        }
      } catch (e) {
        console.log(e)
      }
    },
    gvDanhSach_FocusedRowChanged: async function (data) {
      this.loading(true);
      try {
        if (data && data.length > 0) {
          if (data[0].ma_gd) this.leftForm.txtMaGD.value = data[0].ma_gd
          this.in_ma_tb = data[0].ma_tb;
          this.hdtb_id = Number(data[0].hdtb_id);
          this.cp_dichvuvt_id = this.dichvuvt_id = Number(data[0].dichvuvt_id);
          this.leftForm.cboLoaiHinhTB.value = this.loaitb_id = Number(
            data[0].loaitb_id
          );
          this.leftForm.txtMaTB.value = data[0].ma_tb;
          this.leftForm.txtTenTB.value = this.ten_tb = data[0].ten_tb
            ? data[0].ten_tb
            : "";
          this.leftForm.txtDiaChiLD.value = this.diachi_ld = data[0].diachi_ld
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
            this.leftForm.cboGoiCuocAdsl.value = this.vtocdo_id;

          var thang_ht = moment(this.tt_nd.ngay_cn).format('yyyyMM')
          var ds_datcoc = await this.lay_ds_datcoc_theo_tbid(this.thuebao_id, thang_ht)   
          // var ds_datcoc = await this.LAY_CHITIET_DC_THEO_HDTB_ID(this.hdtb_id);
          this.hdtb_dc_1 = [];
          this.hdtb_dc_2 = [];
          this.dgvDatCoc.list = [];
          if (ds_datcoc && ds_datcoc.length > 0) {
            if (ds_datcoc[0]["khuyenmai_id"]) this.rightForm.cboTraTruoc.value = Number(ds_datcoc[0]["khuyenmai_id"]);
            this.hdtb_dc_1 = ds_datcoc;
            this.hdtb_dc_2 = ds_datcoc;
            this.dgvDatCoc.list = ds_datcoc;
          }

          await this.HT_DS_TraTruoc_Combobox();
          if (data[0].khuyenmai_id) {
            this.rightForm.cboTraTruoc.value = Number(data[0].khuyenmai_id);
          } else {
            this.rightForm.cboTraTruoc.value = -1;
          }

          this.leftForm.txtGhiChu.value = data[0].ghichu ? data[0].ghichu : "";
          var promiseKhoanMuc = this.LAY_TIENHD_KM_THEO_HDTB(this.hdtb_id);
          this.ds_tien_khoanmuc = await promiseKhoanMuc;
          for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
            if (
              this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() ==
              KHOANMUC_TT.KMTT_DATCOC.toString()
            ) {
              this.dtiendatcoc_tt = Number(this.ds_tien_khoanmuc[i]["tien"]);
              this.dvatdatcoc_tt = Number(this.ds_tien_khoanmuc[i]["vat"]);
            }
          }
          this.HienThiTien_TraTruoc();
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
                this.rightForm.txtThoiHan.value = ds_hdtb_cntt[0]["so_thang"];
                if (
                  ds_hdtb_cntt[0]["ngay_bd"] &&
                  ds_hdtb_cntt[0]["ngay_bd"].toString() != ""
                )
                  this.rightForm.dtpTuNgay.value =
                    this.keyenter == 0
                      ? moment(ds_hdtb_cntt[0]["ngay_bd"]).format("DD/MM/YYYY")
                      : moment(
                          ds_hdtb_cntt[0]["ngay_bd"],
                          "DD/MM/YYYY HH:mm:ss"
                        ).format("DD/MM/YYYY");
                else
                  this.rightForm.dtpTuNgay.value = moment(new Date()).format(
                    "DD/MM/YYYY"
                  );
                if (
                  ds_hdtb_cntt[0]["ngay_kt"] &&
                  ds_hdtb_cntt[0]["ngay_kt"].toString() != ""
                )
                  this.rightForm.dtpDenNgay.value =
                    this.keyenter == 0
                      ? moment(ds_hdtb_cntt[0]["ngay_kt"]).format("DD/MM/YYYY")
                      : moment(
                          ds_hdtb_cntt[0]["ngay_kt"],
                          "DD/MM/YYYY HH:mm:ss"
                        ).format("DD/MM/YYYY");
                else
                  this.rightForm.dtpDenNgay.value = moment(new Date()).format(
                    "DD/MM/YYYY"
                  );
              } else {
                this.chkThoiHan.Checked = false;
                this.rightForm.txtThoiHan.value = 0;
                this.rightForm.dtpTuNgay.value = moment(new Date()).format(
                  "DD/MM/YYYY"
                );
                this.rightForm.dtpDenNgay.value = moment(new Date()).format(
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
      var dtiendatcoc_tt = 0
      var dvatdatcoc_tt = 0
      if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0) {
        if (this.dgvDatCoc.list && this.dgvDatCoc.list.length > 0) {
          for (var datcoc_tt of this.dgvDatCoc.list) {
            var tyle_vat = datcoc_tt['tyle_vat'] ? datcoc_tt['tyle_vat'] : 10
            dtiendatcoc_tt += Math.round(datcoc_tt['datcoc_csd'] * 100 / (100 + tyle_vat))
            dvatdatcoc_tt += Math.round(datcoc_tt['datcoc_csd'] * tyle_vat / (100 + tyle_vat))
          }
        }
        this.ds_tien_khoanmuc.forEach((e) => {
          if (e.khoanmuctt_id.toString() == KHOANMUC_TT.KMTT_DATCOC.toString()) {
            e.tien = this.dtiendatcoc_tt ? this.dtiendatcoc_tt : dtiendatcoc_tt;
            e.vat = this.dvatdatcoc_tt ? this.dvatdatcoc_tt : dvatdatcoc_tt;
          }
        });
      }
      this.LayTongTien_HDTB();
    },
    LayTongTien_HDTB() {
      try {
        var dtongtien = 0;
        var dtongvat = 0;
        console.log('LayTongTien_HDTB ds_tien_khoanmuc',this.ds_tien_khoanmuc)
        for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() ==
              KHOANMUC_TT.KMTT_KM_LAPDAT.toString() ||
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() ==
              KHOANMUC_TT.KMTT_TIENTHOAI_DC_DOI_TD.toString() ||
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() ==
              KHOANMUC_TT.KMTT_KM_DATCOC.toString()
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
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() !=
              KHOANMUC_TT.KMTT_TRAGOP_THIETBI.toString() &&
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() !=
              KHOANMUC_TT.KMTT_KM_THIETBI.toString() &&
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"].toString() !=
              KHOANMUC_TT.KMTT_MUATHIETBI.toString()
          ) {
            dtongtien += Number(this.ds_tien_khoanmuc[i]["tien"]);
            dtongvat += Number(this.ds_tien_khoanmuc[i]["vat"]);
          }
        }
        this.rightForm.txtTongTien.value = dtongtien;
        this.rightForm.txtTongVat.value = dtongvat;
        this.rightForm.txtSumTien.value = dtongtien + dtongvat;
      } catch (ex) {}
    },
    async popupTienKM() {
      var ds = [];
      await api_installnewsubs
        .post_lay_khoanmuc_tt_theo_loaihd(this.axios, {
          vloaihd_id: this.loaihd_id,
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
                  (x) => x.khoanmuctt_id.toString() == item.khoanmuctt_id.toString()
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
      this.Popup("popupComponents1");
    },
    acceptTimKiemHDTB: async function (item) {
      if (item) {
        this.leftForm.txtMaGD.value = item.ma_gd;
        this.$refs.txtMaGD.focus();
        if (this.leftForm.txtMaGD.value && this.leftForm.txtMaGD.value != "") {
          this.gridDanhSach.list = [];
          var promise_dshd = this.LAY_DS_HOPDONG_THEO_MA_GD(
            this.leftForm.txtMaGD.value
          );
          var ds = await promise_dshd;
          await this.HienThiTTHopDongKH(ds);
        }
      }
    },
    ShowSearchAccount: function () {
      this.popupComponentAttr = {
        vma_tb: this.leftForm.txtMaTB.value,
        vdichvuvt_id: this.leftForm.cboDichVuVT.value,
      };
      this.popupComponent = () =>
        import("@/modules/search/subscriber/SearchAccount");
      this.popupComponentName = "SearchAccount";
      this.popupComponentHeader = "";
      this.Popup("popupComponents1");
    },
    ShowPopupCTV() {
      if (this.filterbox.ctv.chkCTV) {
        this.popupComponent = () =>
          import(
            "@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien"
          );
        this.popupComponentName = "popupCTV";
        this.popupComponentHeader = "Chọn cộng tác viên";
        this.Popup("popupComponents1");
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
        this.Popup("popupComponents1");
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
      this.Popup("popupComponents1");
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
        this.ClosePopup("popupComponents1");
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
            if (val.ma_tb) this.leftForm.txtMaTB.value = val.ma_tb;
            else this.leftForm.txtMaTB.value = "";
            if (val.ma_kh) this.ma_kh = val.ma_kh;
            else this.ma_kh = "";
            break;
          case "popup-ChonKhuyenMai":
            this.rightForm.cboTraTruoc.value = val.khuyenmai.khuyenmai_id;
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
        this.ClosePopup("popupComponents1");
        this.loading(false);
      }
    },
    formCloseChiTietKM(val) {
      try {        
        if (!val) return
        this.dgvDatCoc.list = val.dsDC_HDTB && val.dsDC_HDTB.length > 0 ? this.LowerCasePropertyList(val.dsDC_HDTB) : [];
        this.hdtb_dc_2 = val.dsDC_HDTB && val.dsDC_HDTB.length > 0 ? this.LowerCasePropertyList(val.dsDC_HDTB) : [];
        this.dtiendatcoc_tt = Math.round((val.tien_datcoc_tt * 10) / 11);
        this.dvatdatcoc_tt = val.tien_datcoc_tt - this.dtiendatcoc_tt;
        if (this.hdtb_dc_2.length == 0) {
          this.dtiendatcoc_tt = 0;
          this.dvatdatcoc_tt = 0;
        }
        this.HienThiTien_TraTruoc();
      } catch (e) {console.log(e)}
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
      this.CT_TIENHD_DC_MOI_ID = 0
      this.CT_TIENHD_ID = 0
      this.thuebao_dc_id = 0
      this.hdtb_dc_id = 0

      this.thang_bd_cu = 0 
      this.thang_kt_cu = 0 
      this.nhom_datcoc_id_cu = 0
      this.ttdc_id = 0;
      this.cuoc_dc_cu = 0
      this.tien_td_cu = 0;

      this.nhomtb_id = 0      
      this.thuebao_dc_id = 0;
      this.thuebao_id = 0;
      this.hdtb_id = 0
      this.hdkh_id = 0

      this.rightForm.txtThoiHan.value = 0;
      this.rightForm.dtpTuNgay.value = moment(new Date()).format("DD/MM/YYYY")
      this.rightForm.dtpDenNgay.value = moment(new Date()).format("DD/MM/YYYY")
      this.rightForm.dtpThangBDMG.value = moment(new Date()).format("MM/YYYY")
      this.rightForm.txtSoThangMG.value = 0;
      this.rightForm.txtTyLeTB.value = 0;
      this.rightForm.txtTyLeSD.value = 0;
      this.rightForm.txtTienTB.value = 0;
      this.rightForm.txtTienSD.value = 0;
      this.rightForm.dtpThangBD.value = moment(new Date()).format("MM/YYYY")
      this.rightForm.txtSoThang.value = 0;
      this.rightForm.txtCuocDC.value = 0;
      this.rightForm.txtTienTD.value = 0;
      this.rightForm.dtpNgayBDDC.value = moment(new Date()).format("DD/MM/YYYY")
      this.rightForm.dtpNgayKTDC.value = moment(new Date()).format("DD/MM/YYYY")
      this.rightForm.txtTongTien.value = 0;
      this.rightForm.txtTongVat.value = 0;
      this.rightForm.txtSumTien.value = 0;
      this.gridDanhSach.list = [];

      this.phuongld_id = 0;
      this.phold_id = 0;
      this.apld_id = 0;
      this.khuld_id = 0;
      this.vtrangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI; //default khách hàng tự trang bị
      this.vtocdo_id = 0;
      this.vmuccuoc_id = 0;

      this.leftForm.txtMaGD.value = "";
      this.leftForm.txtMaTB.value = "";
      this.leftForm.txtTenTB.value = "";
      this.leftForm.txtDiaChiLD.value = "";
      this.leftForm.txtGhiChu.value = "";
      this.leftForm.txtTienThoaiTra.value = 0
      this.filterbox.nguoigt.chkNguoiGT = false;
      if (this.khong_load_ctv == 0) this.filterbox.ctv.chkCTV = false;
    },
    GET_KEYS: async function(key) {
      var input = {
        keyname: key
      }
      let response = await api.getKey(this.axios, input)
      if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
        return Number(response.data.data)
      } else {
        this.$toast.error('Có lỗi trong quá trình tạo key mới')
        return
      }
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
        return Number(this.leftForm.cboDichVuVT.value);
      },
      set(value) {
        this.leftForm.cboDichVuVT.value = Number(value);
      },
    },
    cp_txtThoiHan: {
      get() {
        return Number(this.rightForm.txtThoiHan.value);
      },
      set(value) {
        this.rightForm.txtThoiHan.value = Number(value);
      },
    },
    cp_TongTien: {
      get() {
        var txtTongTien = this.rightForm.txtTongTien.value
          ? this.numberWithCommas(this.rightForm.txtTongTien.value.toString())
          : "0";
        return txtTongTien;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.rightForm.txtTongTien.value = Number(replaced);
      },
    },
    cp_TongVat: {
      get() {
        var txtTongVat = this.rightForm.txtTongVat.value
          ? this.numberWithCommas(this.rightForm.txtTongVat.value.toString())
          : "0";
        return txtTongVat;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.rightForm.txtTongVat.value = Number(replaced);
      },
    },
    cp_SumTien: {
      get() {
        var txtSumTien = this.rightForm.txtSumTien.value
          ? this.numberWithCommas(this.rightForm.txtSumTien.value.toString())
          : "0";
        return txtSumTien;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.rightForm.txtSumTien.value = Number(replaced);
      },
    },
    cp_TienTB: {
      get() {
        var txtTienTB = this.rightForm.txtTienTB.value
          ? this.numberWithCommas(this.rightForm.txtTienTB.value.toString())
          : "0";
        return txtTienTB;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.rightForm.txtTienTB.value = Number(replaced);
      },
    },
    cp_TienSD: {
      get() {
        var txtTienSD = this.rightForm.txtTienSD.value
          ? this.numberWithCommas(this.rightForm.txtTienSD.value.toString())
          : "0";
        return txtTienSD;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.rightForm.txtTienSD.value = Number(replaced);
      },
    },
    cp_CuocDC: {
      get() {
        var txtCuocDC = this.rightForm.txtCuocDC.value
          ? this.numberWithCommas(this.rightForm.txtCuocDC.value.toString())
          : "0";
        return txtCuocDC;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.rightForm.txtCuocDC.value = Number(replaced);
      },
    },
    cp_TienTD: {
      get() {
        var txtTienTD = this.rightForm.txtTienTD.value
          ? this.numberWithCommas(this.rightForm.txtTienTD.value.toString())
          : "0";
        return txtTienTD;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.rightForm.txtTienTD.value = Number(replaced);
      },
    },
    cp_TienThoaiTra: {
      get() {
        var txtTienThoaiTra = this.leftForm.txtTienThoaiTra.value
          ? this.numberWithCommas(this.leftForm.txtTienThoaiTra.value.toString())
          : "0";
        return txtTienThoaiTra;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.leftForm.txtTienThoaiTra.value = Number(replaced);
      },
    },
  },
  watch: {
    cp_dichvuvt_id(newval, oldval) {
      if (oldval && newval != oldval) this.onChangeDVVT(newval);
    },
    "chkThoiHan.Checked": {
      handler: function (newvalue, oldvalue) {
        this.chkThoiHan_CheckedChanged();
      },
    },
    "rightForm.dtpTuNgay.value"(val) {
      this.chkThoiHan_CheckedChanged();
    },
    "rightForm.txtThoiHan.value"(val) {
      this.chkThoiHan_CheckedChanged();
    },
    "rightForm.txtTongTien.value"(val) {
      var vat = Math.round((Number(val) * 10) / 100);
      this.rightForm.txtTongVat.value = vat;
      this.rightForm.txtSumTien.value = Number(val) + Number(vat);
    },
    "rightForm.txtTongVat.value"(val) {
      this.rightForm.txtSumTien.value =
        Number(this.rightForm.txtTongTien.value) + Number(val);
    },
  },
};
</script>
