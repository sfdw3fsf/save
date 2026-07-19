<template src="./DSKhachHangKS.html"></template>
<style src="./DSKhachHangKS.scss"></style>
<script>
import { mapActions, mapState } from "vuex";
import select2 from "@/components/Select2.vue";
import downloadexcel from "vue-json-excel";
import frmKhaoSatKH_HDTB from "../KhaoSat/frmKhaoSatKH_HDTB.vue";
import frmKhaoSatKH_KLL from "../KhaoSat/frmKhaoSatKH_KLL.vue";
import frmKhaoSatKH_LM from "../KhaoSat/frmKhaoSatKH_LM.vue";
import frmKhaoSatKH_TB from "../KhaoSat/frmKhaoSatKH_TB.vue";
import frmKhaoSatKH_TDDV from "../KhaoSat/frmKhaoSatKH_TDDV.vue";
import frmKhaoSatKH from "../KhaoSat/frmKhaoSatKH.vue";
import frmKhaoSatKH_Chua_NT from "../KhaoSat/frmKhaoSatKH_Chua_NT.vue";
import frmKhaoSatTB_CatChuyen from "../KhaoSat/frmKhaoSatTB_CatChuyen.vue";
import frmKhaoSatKH_BaoDuong from "../KhaoSat/frmKhaoSatKH_BaoDuong.vue";
import XuatFile from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu";
import { TrangThaiTB, DichVuVienThong } from "@/const/enums";
import moment from "moment";
import { CSKH_CONST } from "../utils/functions/constants.js";

const LOAI_NHOM = {
  THI_CONG: 1,
  BAO_HONG: 2,
  THUE_BAO_KLL: 3,
  TON_BH: 4,
  KHOA_MAY_NO_CUOC: 5,
  TAM_DUNG: 6,
  THANH_LY_THEO_YEU_CAU: 7,
  THUE_BAO_CHUA_NGHIEM_THU: 8,
  THUE_BAO_CAT_CHUYEN: 9,
  DI_DONG_THAM_GIA_GOI_GD: 10,
  THANH_LY_CB: 11,
  KHAO_SAT_THUE_BAO_BAO_DUONG: 12,
};
export default {
  name: "DSKhachHangKS",
  components: {
    appSelect2: select2,
    downloadexcel,
    frmKhaoSatKH_HDTB,
    frmKhaoSatKH_KLL,
    frmKhaoSatKH_LM,
    frmKhaoSatKH_TB,
    frmKhaoSatKH_TDDV,
    frmKhaoSatKH,
    frmKhaoSatKH_Chua_NT,
    frmKhaoSatTB_CatChuyen,
    frmKhaoSatKH_BaoDuong,
    appModalXuatDuLieu: XuatFile,
  },
  data() {
    return {
      header: {
        title: "DANH SÁCH KHÁCH HÀNG KHẢO SÁT",
        list: [
          //   { name: "Trang chủ", link: { name: "Ui.cards" } },
          {
            name: "Chăm sóc khách hàng",
            link: { name: "Ui.buttons" },
          },
          {
            name: "DANH SÁCH KHÁCH HÀNG KHẢO SÁT",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      // treeDonViFields: "",
      // 				1        Thi công
      // 2        Báo hỏng
      // 3        Thuê bao không lưu lượng
      // 5        Khóa máy nợ cước
      // 6        Tạm dừng
      // 7        Thanh lý theo yêu cầu
      // 8        Thuê bao chưa nghiệm thu
      // 10        Di động tham gia gói GĐ
      // 12        Khảo sát thuê bao bảo dưỡng
      dsLoaiNhom: [
        { id: 1, text: "Thi công" },
        { id: 2, text: "Báo hỏng" },
        { id: 3, text: "Thuê bao không lưu lượng" },
        { id: 4, text: "Tồn BH" },
        { id: 5, text: "Khóa máy nợ cước" },
        { id: 6, text: "Tạm dừng" },
        { id: 7, text: "Thanh lý theo yêu cầu" },
        { id: 8, text: "Thuê bao chưa nghiệm thu" },
        { id: 9, text: "Thuê bao cắt chuyển" },
        // { "id": 10, "text": "Di động tham gia gói GĐ" },
        { id: 11, text: "Thanh lý  cb" },
        { id: 12, text: "Khảo sát thuê bao bảo dưỡng" },
      ],
      loaiNhomSelected: 1,
      dsLoaiHD: [],
      loaiHDSelected: 1,
      dsDichVu: [],
      dichVuSelected: 0,
      dsTrangThai: [
        { id: 0, text: "Chưa khảo sát" },
        { id: 1, text: "Đã khảo sát" },
        { id: 2, text: "Chưa trả lời" },
      ],
      trangThaiSelected: 0,
      today: new Date(),
      dsKhachHangKS: [],
      dsKhachHangKSFields: [
        {
          fieldName: "MA_TB",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
          cellCreatedEventEnabled: true,
        },
        {
          fieldName: "LOAIHINH_TB",
          headerText: "Loại hình TB",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
        },
        {
          fieldName: "TOCDO",
          headerText: "Tốc độ",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
        },
        {
          fieldName: "NGAY_YC",
          headerText: "Ngày yêu cầu",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
          cellCreatedEventEnabled: true,
        },
        {
          fieldName: "NGAY_HT",
          headerText: "Ngày hoàn thành",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
        },
        {
          fieldName: "NGAY_SD",
          headerText: "Ngày BĐSD	",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAY_CAT",
          headerText: "Ngày TL",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "SO_THANG",
          headerText: "Số tháng SD",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAY_INS",
          headerText: "Ngày INS",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "LAN_KS",
          headerText: "Lần gọi",
          allowFiltering: true,
          allowSorting: false,
          cssClass: "font-weight-bold",
        },
        {
          fieldName: "HEN_TU",
          headerText: "Ngày hẹn đầu",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "HEN_DEN",
          headerText: "Ngày hẹn cuối",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_TB",
          headerText: "Tên thuê bao",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DIACHI_LD",
          headerText: "Địa chỉ lắp đặt",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_DT",
          headerText: "Đối tượng",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "CHUNHOM",
          headerText: "Chủ nhóm",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "GOI_KT",
          headerText: "Gói KT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "CHITIEU_TG",
          headerText: "Chỉ tiêu TG",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DONVI_TN",
          headerText: "ĐV lập HĐ",
          allowFiltering: true,
          allowSorting: false,
          cellCreatedEventEnabled: true,
        },
        {
          fieldName: "NHANVIEN_TN",
          headerText: "NV tiếp nhận",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DONVI_QL",
          headerText: "Đơn vị QL",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TRAM_VT",
          headerText: "Tổ thi công",
          allowFiltering: true,
          allowSorting: false,
          cellCreatedEventEnabled: true,
        },
        {
          fieldName: "TRANGTHAI_HD",
          headerText: "Trạng thái HĐ",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "SO_DT",
          headerText: "Điện thoại KH",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGUOI_CN",
          headerText: "Người CN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAY_CN",
          headerText: "Ngày CN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_KIEULD",
          headerText: "Kiểu yêu cầu",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGUOI_XL",
          headerText: "Nhân viên hoàn công",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "LYDO_HONG",
          headerText: "Lý do",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "THOIGIAN_TH",
          headerText: "Thời gian TH",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAY_HOI",
          headerText: "Ngày hỏi",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGUOI_HOI",
          headerText: "Người hỏi",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "GHICHU",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_NV_TH",
          headerText: "Nhân viên thực hiện",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NVKT_DB",
          headerText: "Nhân viên KT quản lý địa bàn",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NVKD_DB",
          headerText: "Nhân viên KD quản lý địa bàn",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TRANGTHAI_TB",
          headerText: "Trạng thái thuê bao",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "ten_loaikh",
          headerText: "Loại khách hàng",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_PBHKV",
          headerText: "Phòng BH",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TUYENTHU",
          headerText: "Tuyến thu",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_NVTC",
          headerText: "NVTC",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "CHUYENMANG",
          headerText: "NCC khác",
          allowFiltering: true,
          allowSorting: false,
          displayAsCheckBox: true,
          cellCreatedEventEnabled: true,
        },
      ],

      dsKhachHangKSFieldsExcel: [
        {
          fieldName: "MA_TB",
          headerText: "MA_TB",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
          cellCreatedEventEnabled: true,
        },
        {
          fieldName: "LOAIHINH_TB",
          headerText: "LOAIHINH_TB",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
        },
        {
          fieldName: "TOCDO",
          headerText: "TOCDO",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
        },
        {
          fieldName: "NGAY_YC",
          headerText: "NGAY_YC",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
          cellCreatedEventEnabled: true,
        },
        {
          fieldName: "NGAY_HT",
          headerText: "NGAY_HT",
          allowFiltering: true,
          allowSorting: false,
          freeze: "left",
        },
        {
          fieldName: "NGAY_SD",
          headerText: "NGAY_SD",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAY_CAT",
          headerText: "NGAY_CAT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "SO_THANG",
          headerText: "SO_THANG",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAY_INS",
          headerText: "NGAY_INS",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "LAN_KS",
          headerText: "LAN_KS",
          allowFiltering: true,
          allowSorting: false,
          cssClass: "font-weight-bold",
        },
        {
          fieldName: "HEN_TU",
          headerText: "HEN_TU",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "HEN_DEN",
          headerText: "HEN_DEN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_TB",
          headerText: "TEN_TB",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DIACHI_LD",
          headerText: "DIACHI_LD",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_DT",
          headerText: "TEN_DT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "CHUNHOM",
          headerText: "CHUNHOM",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "GOI_KT",
          headerText: "GOI_KT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "CHITIEU_TG",
          headerText: "CHITIEU_TG",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DONVI_TN",
          headerText: "DONVI_TN",
          allowFiltering: true,
          allowSorting: false,
          cellCreatedEventEnabled: true,
        },
        {
          fieldName: "NHANVIEN_TN",
          headerText: "NHANVIEN_TN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "DONVI_QL",
          headerText: "DONVI_QL",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TRAM_VT",
          headerText: "TRAM_VT",
          allowFiltering: true,
          allowSorting: false,
          cellCreatedEventEnabled: true,
        },
        {
          fieldName: "TRANGTHAI_HD",
          headerText: "TTRANGTHAI_HD",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "SO_DT",
          headerText: "SO_DT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGUOI_CN",
          headerText: "NGUOI_CN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAY_CN",
          headerText: "NGAY_CN",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_KIEULD",
          headerText: "TEN_KIEULD",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGUOI_XL",
          headerText: "NGUOI_XL",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "LYDO_HONG",
          headerText: "LYDO_HONG",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "THOIGIAN_TH",
          headerText: "THOIGIAN_TH",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGAY_HOI",
          headerText: "Ngày hỏi",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NGUOI_HOI",
          headerText: "NGAY_HOI",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "GHICHU",
          headerText: "GHICHU",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_NV_TH",
          headerText: "TEN_NV_TH",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NVKT_DB",
          headerText: "NVKT_DB",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "NVKD_DB",
          headerText: "NVKD_DB",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TRANGTHAI_TB",
          headerText: "TRANGTHAI_TB",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "ten_loaikh",
          headerText: "TEN_LOAIKH",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_PBHKV",
          headerText: "TEN_PBHKV",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TUYENTHU",
          headerText: "TUYENTHU",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "TEN_NVTC",
          headerText: "TEN_NVTC",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "CHUYENMANG",
          headerText: "CHUYENMANG",
          allowFiltering: true,
          allowSorting: false,
          displayAsCheckBox: true,
          cellCreatedEventEnabled: true,
        },
      ],
      pTxtThoiGianKSTu: new Date(),
      pTxtThoiGianKSDen: new Date(),
      txtDiaChiLD: "",
      txtMaGD: "",
      txtMaTB: "",
      txtTenTB: "",
      txtSoAo: "",
      txtKieuLD: "",
      txtKHLon: "",
      txtGhiChu: "",
      _tbtlId: 0,
      hdtbId: 0,
      hdkhId: 0,
      baoHongId: 0,
      ctSuyHaoId: 0,
      dkccId: 0,
      KHAOSAT_HDTB: false,
      exportExcelFields: {
        "Số máy/ACC": "MA_TB",
        // "Số ảo": "MA_LT",
        "Loại hình TB": "LOAIHINH_TB",
        "Tốc độ": "TOC_DO",
        "Ngày yêu cầu": "NGAY_YC",
        "Ngày hoàn thành": "NGAY_HT",
        "Ngày BĐSD": "NGAY_SD",
        "Ngày TL": "NGAY_CAT",
        "Số tháng SD": "NGAY_YC",
        "Ngày INS": "NGAY_INS",
        "Lần gọi": "LAN_KS",
        "Ngày hẹn đầu": "HEN_TU",
        "Ngày hẹn cuối": "HEN_DEN",
        "Tên thuê bao": "TEN_TB",
        "Địa chỉ lắp đặt": "DIACHI_LD",
        "Đối tượng": "TEN_DT",
        "Chủ nhóm": "CHUNHOM",
        "Gói KT": "GOI_KT",
        "Chỉ tiêu TG": "CHITIEU_TG",
        "ĐV lập HĐ": "DONVI_TN",
        "NV tiếp nhận": "NHANVIEN_TN",
        "Đơn vị QL": "DONVI_QL",
        "Tổ thi công": "TRAM_VT",
        "Trạng thái HĐ": "TRANGTHAI_HD",
        "Điện thoại KH": "SO_DT",
        "Người CN": "NGUOI_CN",
        "Ngày CN": "NGAY_CN",
        "Kiểu yêu cầu": "TEN_KIEULD",
        "Người XL": "NGUOI_XL",
        "Nhân viên KT quản lý địa bàn": "NVKT_DB",
        "Nhân viên KD quản lý địa bàn": "NVKD_DB",
        "Lý do": "LYDO_HONG",
        "Thời gian TH": "THOIGIAN_TH",
        "Ngày hỏi": "NGAY_HOI",
        "Người hỏi": "NGUOI_HOI",
        "Ghi chú": "GHICHU",
        "Nhân viên thực hiện": "TEN_NV_TH",
        "Trạng thái thuê bao": "TRANGTHAI_TB",
        "Loại khách hàng": "ten_loaikh",
        "Phòng BH": "TEN_PBHKV",
        "Tuyến thu": "TUYENTHU",
        NVTC: "TEN_NVTC",
        "NCC khác": "CHUYENMANG",
      },

      maTBTemplate: function () {
        return {
          template: Vue.component("columnTemplate", {
            template: `<div><button class="btn btn-danger lh14 pad3" @click="onDeleteRow" title="Xóa lần tạo">
										<span class="-ap icon-close"></span>
									</button>
									<button class="btn btn-primary lh14 pad3" @click="onRefreshRow" title="Danh sách lần tạo">
										<span class="btn-refresh nc-icon-outline arrows-1_refresh-68"></span>
									</button></div>`,
            data() {
              return { data: {} };
            },
            methods: {},
          }),
        };
      },
      loaiHDDisable: false,
      dsLanGoiChuaTraLoi: [],
      selectedNodes: [],
      donViSelected: 0,
      tenDonViSearch:"",
      currentFindIndex: 0,
      keyReset: 0,
      idDonVi: null,
      totalItemsPhieu: 0,
      sizePage: 10
    };
  },
  async created() {
    await this.getCayDonViData();
  },
  async mounted() {
    try {
      this.$root.showLoading(true);
      await this.getCayDonViData();
      this.KHAOSAT_HDTB = await this.getThamSoMacDinh({
        dsMaTs: "KHAOSAT_HDTB",
      });
    } catch (e) {
      console.log(e);
    } finally {
      this.$root.showLoading(false);
    }

    this.getDsLoaiHD();
    this.getDsDichVu();
  },
  computed: {
    treeDonViFields() {
      return {
        dataSource: this.cayDonVi,
        id: "DONVI_ID",
        text: "TEN_DV",
        parentID: "DONVI_CHA_ID",
        hasChildren: "hasChild",
      };
    },

    ...mapState("cskhCommon", ["cayDonVi"]),
    dataExcel() {
      return JSON.stringify(this.danhSachCoDinh);
      //return Object.values(this.dsThueBao);
    },
    dsThuocTinh(){
      return ['STT','HDKH_ID','NGAY_YC','NGAY_HT','LAN_KS','MA_GD','MA_TB','TEN_TB','DIACHI_LD','DONVI_TN','TTHD_ID','STATUS','LOAITB_ID','TEN_DT','NHANVIEN_TN','TEN_LOAIHD','LOAIHINH_TB','TEN_KIEULD','TRANGTHAI_HD','NHANVIEN_LHD','GHICHU_TB','TRAM_VT','KHLON_ID','NGUOI_CN','NGAY_CN','SO_DT','DICHVUVT_ID','HDTB_ID','HDTB_CHA_ID','QUYTRINH_ID','GOI_KT','CHITIEU_TG','HEN_TU','HEN_DEN','CHUYENMANG','AUTO_OB']
    },
    dataXuatFileDSKH(){
      return this.dsKhachHangKS.map((item,index)=>({...item,STT:++index}))
    }
  },
  watch: {
    loaiNhomSelected: {
      handler: async function (value) {
        this.loaiHDDisable = true;
        this.loaiHDSelected = ""
        this.clearData();
        if(this.loaiNhomSelected == 1){
          this.loaiHDDisable = false;
          this.loaiHDSelected = 1
        }
        if (this.loaiNhomSelected == 6) {
          this.loaiHDSelected = 7;
        //   this.loaiHDDisable = true;
        } else if (this.loaiNhomSelected == 7) {
          this.loaiHDSelected = 4;
        //   this.loaiHDDisable = true;
        }
      },
    },
    loaiHDSelected: {
      handler: async function (value) {
        this.clearData();
      },
    },
    dichVuSelected: {
      handler: async function (value) {
        this.clearData();
      },
    },
  },
  methods: {
    ...mapActions("cskhCommon", ["getCayDonViData", "getThamSoMacDinh"]),

    async getDsLoaiHD() {
      try {
        let rs = await this.$root.context.get(
          "/web-cskh/api/danh-muc/loai-hop-dong"
        );
        let ds = rs.data || [];
        ds.forEach((element) => {
          this.dsLoaiHD.push({
            id: element.LOAIHD_ID,
            text: element.TEN_LOAIHD,
          });
        });
      } catch (e) {}
    },

    async getDsDichVu() {
      try {
        let rs = await this.$root.context.get(
          "/web-cskh/api/danh-muc/dich-vu-vt"
        );
        let ds = rs.data || [];
        this.dsDichVu.push({
          id: 0,
          text: "Tất cả",
        });
        ds.forEach((element) => {
          this.dsDichVu.push({
            id: element.DICHVUVT_ID,
            text: element.TEN_DVVT,
          });
        });
      } catch (e) {}
    },

    async onTimKiemClicked() {
      try {
        this.clearData();
        let treeDonVi = document.getElementById("treeview").ej2_instances[0];
        let donViId = treeDonVi.selectedNodes[0];

        if (!donViId) {
          this.$root.toastError("Chưa chọn đơn vị!");
          return;
        }
        this.idDonVi = donViId
        this.fetchData(0,this.sizePage)
      } catch (e) {
      }
    },
    async fetchData(page,size){
        try {
            this.loading(true);
            let rs = await this.$root.context.post(
            `/web-cskh/api/ds-kh-ks-thanh-ly/tim-kiem?pageNo=${page+1}&pageSize=${size}`,
            {
                nhom: this.loaiNhomSelected,
                loaiHdId: this.loaiHDSelected,
                dichVuVtId: this.dichVuSelected,
                donViId: this.idDonVi,
                tuNgay: moment(this.pTxtThoiGianKSTu).format("DD/MM/YYYY"),
                denNgay: moment(this.pTxtThoiGianKSDen).format("DD/MM/YYYY"),
                khaoSat: this.trangThaiSelected,
            }
            );
            if (rs.error_code == "BSS-00000000") {
                this.dsKhachHangKS = rs.data.data || [];
                this.totalItemsPhieu = rs.data.totalElement
            }
        } catch (error) {
            let msg = error.response ? error.response.data.message : "Tìm kiếm không thành công!"
            this.$root.toastError(msg);
            this.totalItemsPhieu = 0
        } finally {
            this.loading(false);
        }
    },
    gridChanged(args){
        this.sizePage = args.pageSize
        this.fetchData(args.pageIndex, args.pageSize);
    },
    clearData() {
      this.keyReset += 1;
      this.dsKhachHangKS = [];
      this.txtMaGD = "";
      this.txtMaTB = "";
      this.txtKieuLD = "";
      this.txtTenTB = "";
      this.txtDiaChiLD = "";
      this.txtSoAo = "";
      this.txtGhiChu = "";
      this.txtKHLon = "";
    },

    onStartDownload() {
      if (this.dsKhachHangKS.length == 0) {
        this.$root.toastError("Không có dữ liệu để xuất Excel !");
        return;
      }
    },

    onKhaoSatClicked() {
      if(this.txtTenTB.trim() == "") {
        this.$root.toastError("Không có thông tin để khảo sát!");
        return;
      }
      if (this.loaiNhomSelected == LOAI_NHOM.THI_CONG) {
        if (this.KHAOSAT_HDTB) {
          this.$refs.frmKhaoSatKH_HDTB.hdtbId = this.hdtbId;
          if (this._tbtlId != 0) {
            this.$refs.frmKhaoSatKH_HDTB.tbtlId = this._tbtlId;
          }
          this.$refs.frmKhaoSatKH_HDTB.maTb = this.txtMaTB;
          this.$refs.frmKhaoSatKH_HDTB.openDialog();
          return;
        } else {
          this.$refs.frmKhaoSatKH_LM.hdkhId = this.hdkhId;
          this.$refs.frmKhaoSatKH_LM.maTb = this.maTb;
          this.$refs.frmKhaoSatKH_LM.hdtbId = this.hdtbId;
          this.$refs.frmKhaoSatKH_LM.openDialog();
        }
      } else if (this.loaiNhomSelected == LOAI_NHOM.BAO_HONG) {
        this.$refs.frmKhaoSatKH.baoHongId = this.baoHongId;
        if (this._tbtlId != 0) {
          this.$refs.frmKhaoSatKH.tbtlId = this._tbtlId;
        }
        this.$refs.frmKhaoSatKH._maTb = this.txtMaTB;
        this.$refs.frmKhaoSatKH.openDialog();
      } else if (this.loaiNhomSelected == LOAI_NHOM.THUE_BAO_KLL) {
        this.$refs.frmKhaoSatKH_KLL.baoHongId = this.baoHongId;
        this.$refs.frmKhaoSatKH_KLL.ctSuyHaoId = this.ctSuyHaoId;
        this.$refs.frmKhaoSatKH_KLL.openDialog();
      } else if (this.loaiNhomSelected == LOAI_NHOM.TON_BH) {
        this.$refs.frmKhaoSatKH.baoHongId = this.baoHongId;
        if (this._tbtlId != 0) {
          this.$refs.frmKhaoSatKH.tbtlId = this._tbtlId;
        }
        this.$refs.frmKhaoSatKH._maTb = this.txtMaTB;
        this.$refs.frmKhaoSatKH.openDialog();
      } else if (this.loaiNhomSelected == LOAI_NHOM.KHOA_MAY_NO_CUOC) {
        this.$refs.frmKhaoSatKH_TDDV.hdkhId = this.hdkhId;
        this.$refs.frmKhaoSatKH_TDDV._maTb = this.txtMaTB;
        this.$refs.frmKhaoSatKH_TDDV.loaiNhomId = 5;
        this.$refs.frmKhaoSatKH_TDDV.openDialog();
      } else if (this.loaiNhomSelected == LOAI_NHOM.TAM_DUNG) {
        this.$refs.frmKhaoSatKH_TDDV.hdkhId = this.hdkhId;
        this.$refs.frmKhaoSatKH_TDDV._maTb = this.txtMaTB;
        this.$refs.frmKhaoSatKH_TDDV.loaiNhomId = 6;
        this.$refs.frmKhaoSatKH_TDDV.openDialog();
      } else if (this.loaiNhomSelected == LOAI_NHOM.THANH_LY_THEO_YEU_CAU) {
        this.$refs.frmKhaoSatKH_TDDV.hdkhId = this.hdkhId;
        this.$refs.frmKhaoSatKH_TDDV._maTb = this.txtMaTB;
        this.$refs.frmKhaoSatKH_TDDV.loaiNhomId = 7;
        this.$refs.frmKhaoSatKH_TDDV.openDialog();
      } else if (this.loaiNhomSelected == LOAI_NHOM.DI_DONG_THAM_GIA_GOI_GD) {
        this.$refs.frmKhaoSatKH_TDDV.hdkhId = this.hdkhId;
        this.$refs.frmKhaoSatKH_TDDV.hdtbId = this.hdtbId;
        this.$refs.frmKhaoSatKH_TDDV._maTb = this.txtMaTB;
        this.$refs.frmKhaoSatKH_TDDV.loaiNhomId = 10;
        this.$refs.frmKhaoSatKH_TDDV.openDialog();
      } else if (this.loaiNhomSelected == LOAI_NHOM.THUE_BAO_CHUA_NGHIEM_THU) {
        this.$refs.frmKhaoSatKH_Chua_NT.baoHongId = this.baoHongId;
        if (this._tbtlId != 0) {
          this.$refs.frmKhaoSatKH_Chua_NT.tbtlId = this._tbtlId;
        }
        this.$refs.frmKhaoSatKH_Chua_NT._maTb = this.txtMaTB;
        this.$refs.frmKhaoSatKH_Chua_NT.openDialog();
      } else if (this.loaiNhomSelected == LOAI_NHOM.THUE_BAO_CAT_CHUYEN) {
        this.$refs.frmKhaoSatTB_CatChuyen.vdkccId = this.dkccId;
        if (this._tbtlId != 0) {
          this.$refs.frmKhaoSatTB_CatChuyen.tbtlId = this._tbtlId;
        }
        this.$refs.frmKhaoSatTB_CatChuyen.openDialog();
      } else if (this.loaiNhomSelected == LOAI_NHOM.THANH_LY_CB) {
        this.$refs.frmKhaoSatKH_TDDV.hdkhId = this.hdkhId;
        this.$refs.frmKhaoSatKH_TDDV._maTb = this.txtMaTB;
        this.$refs.frmKhaoSatKH_TDDV.loaiNhomId = 9;
        this.$refs.frmKhaoSatKH_TDDV.openDialog();
      } else if (
        this.loaiNhomSelected == LOAI_NHOM.KHAO_SAT_THUE_BAO_BAO_DUONG
      ) {
        this.$refs.frmKhaoSatKH_BaoDuong.baoHongId = this.baoHongId;
        if (this._tbtlId != 0) {
          this.$refs.frmKhaoSatKH_BaoDuong.tbtlId = this._tbtlId;
        }
        this.$refs.frmKhaoSatKH_BaoDuong._maTb = this.txtMaTB;
        this.$refs.frmKhaoSatKH_BaoDuong.openDialog();
      }
      this.onTimKiemClicked();
    },

    async onRowDSKhachHangKSSelected(item) {
      console.log("Danh sách KS", item);
      if (!item) return;
      //  txtMaGD.Text = gridviewDanhSach.GetRowCellValue(nIndex, "MA_GD").ToString();
      // txtMaTB.Text = gridviewDanhSach.GetRowCellValue(nIndex, "MA_TB").ToString();
      // txtTenTB.Text = gridviewDanhSach.GetRowCellValue(nIndex, "TEN_TB").ToString();
      // txtDiaChiLD.Text = gridviewDanhSach.GetRowCellValue(nIndex, "DIACHI_LD").ToString();
      this.txtMaGD = item.MA_GD;
      this.txtMaTB = item.MA_TB;
      this.txtTenTB = item.TEN_TB;
      this.txtDiaChiLD = item.DIACHI_LD;
      if (this.loaiNhomSelected != 10) this.txtKieuLD = item.TEN_KIEULD;
      if (this.trangThaiSelected == 1) { this._tbtlId = item.TBTL_ID } else this._tbtlId = 0;

      // if (Convert.ToInt32(cboLoaiNhom.SelectedValue) == 1
      // || Convert.ToInt32(cboLoaiNhom.SelectedValue) == 5
      // || Convert.ToInt32(cboLoaiNhom.SelectedValue) == 6
      // || Convert.ToInt32(cboLoaiNhom.SelectedValue) == 7
      // || Convert.ToInt32(cboLoaiNhom.SelectedValue) == 10)
      let loai = 0
      let id = 0
      if (
        this.loaiNhomSelected == 1 ||
        this.loaiNhomSelected == 5 ||
        this.loaiNhomSelected == 6 ||
        this.loaiNhomSelected == 7 ||
        this.loaiNhomSelected == 10
      ) {
        this.hdtbId = item.HDTB_ID;
        this.hdkhId = item.HDKH_ID;
        this.baoHongId = 0;
        let dichVuVtId = item.DICHVUVT_ID;
        id = item.HDKH_ID
        loai = 1
        if (
          dichVuVtId == DichVuVienThong.ADSL ||
          dichVuVtId == DichVuVienThong.MEGA_EYES ||
          dichVuVtId == DichVuVienThong.MEGAWAN ||
          dichVuVtId == DichVuVienThong.TSL
        ) {
          // ds = tchopdong.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
          // if (ds.Tables[0].Rows.Count > 0)
          //     txtSoAo.Text = ds.Tables[0].Rows[0]["ma_lt"].ToString();
          // else
          //     txtSoAo.Text = "";
          if (this.dsKhachHangKS.length > 0) this.txtSoAo = item.MA_LT;
          else this.txtSoAo = "";
        } else {
          this.txtSoAo = "";
        }
      } else if (this.loaiNhomSelected == 2) {
        this.txtSoAo = "";
        this.baoHongId = item.BAOHONG_ID;
        this.hdkhId = 0;
        id = item.BAOHONG_ID
        loai = 2
      } else if (this.loaiNhomSelected == 3) {
        this.txtSoAo = "";
        this.ctSuyHaoId = item.CT_SUYHAO_ID;
        this.baoHongId = 0;
        this.hdkhId = 0;
        id = item.CT_SUYHAO_ID
        loai = 3
      } else if (this.loaiNhomSelected == 4 || this.loaiNhomSelected == 8) {
        this.txtSoAo = "";
        this.baoHongId = item.BAOHONG_ID;
        // baohong_id = Convert.ToInt64(gridviewDanhSach.GetRowCellValue(nIndex, "BAOHONG_ID"));
        this.hdkhId = 0;
        id = item.BAOHONG_ID
        loai = 2
      } else if (this.loaiNhomSelected == 9) {
        this.txtSoAo = "";
        this.dkccId = item.DKCC_ID;
        this.hdkhId = 0;
        id = item.DKCC_ID
        loai = 4
      } else if (this.loaiNhomSelected == 12) {
        this.txtSoAo = "";
        // baohong_id = Convert.ToInt64(gridviewDanhSach.GetRowCellValue(nIndex, "BAOHONG_ID"));
        // hdkh_id = 0;
        this.baoHongId = item.BAOHONG_ID;
        this.hdkhId = 0;
        id = item.BAOHONG_ID
        loai = 2
      } else if (this.loaiNhomSelected == 11) {
        this.txtSoAo = "";
        // baohong_id = Convert.ToInt64(gridviewDanhSach.GetRowCellValue(nIndex, "BAOHONG_ID"));
        // hdkh_id = 0;
        this.baoHongId = item.BAOHONG_ID;
        this.hdtbId = item.HDTB_ID;
        this.hdkhId = item.HDKH_ID;
        id = item.HDKH_ID
        loai = 1
      }
      if(id > 0) this.dsLanGoiChuaTraLoi = await this.layDsLanGoiChuaTL(loai,id);
    },

    grid_dataGridCellCreated(e) {
      // let now = moment(new Date()); //todays date
      // let end = moment(e.cellDataItem.NGAY_HT, "DD/MM/YYYY hh:mm:ss"); // another date
      // let duration = moment.duration(now.diff(end));
      // let days = duration.asHours();
      // console.log("days" + days);

      // console.log("checkTime"+checkTime);

      // console.log("currentTime"+currentTime);

      switch (e.cellFieldName) {
        case "MA_TB":
          let now = moment(new Date()); //todays date
          let end = moment(e.cellDataItem.NGAY_HT, "DD/MM/YYYY hh:mm:ss"); // another date
          let duration = moment.duration(now.diff(end));
          let hoursDistance = duration.asHours();
          console.log("hoursDistance" + hoursDistance);
          if (hoursDistance > 72) {
            $(e.cellElement).css({ background: "red" });
          } else if (hoursDistance < 24) {
            $(e.cellElement).css({ background: "lightgreen" });
          }
          break;
        case "CHUYENMANG":
          e.cellAllowHtml = true;
          $(e.cellElement).removeClass("text-right");
          $(e.cellElement).addClass("center");
          $(e.cellElement).css({ text: "center" });
          $(e.cellElement).empty();
          $(e.cellElement).append(
            '<div class="check-action">' +
              (e.cellDataItem.CHUYENMANG == 0
                ? '<input type="checkbox" class="check" disabled><span class="name"></span>'
                : '<input type="checkbox" class="check" disabled checked><span class="name"></span>') +
              "</div>"
          );
          break;
        // case "LAN_GOI":
        // 		$(e.cellElement).css({'font-weight': 'bold'})
        // 	break
      }
    },

    onQueryCellInfo(args) {
      let field = args.column.field;
      let dataField = args.data;

      if (field == "MA_TB") {
        let now = moment(new Date()); //todays date
        let end = moment(dataField.NGAY_HT, "DD/MM/YYYY hh:mm:ss"); // another date
        let duration = moment.duration(now.diff(end));
        let hoursDistance = duration.asHours();
        // console.log("hoursDistance" + hoursDistance);
        if (hoursDistance > 72) {
          args.cell.classList.add("background-red");
        } else if (hoursDistance < 24) {
          args.cell.classList.add("background-lightgreen");
        }
      }

      if (field == "CHUYENMANG") {
        args.cell.innerHTML =
          '<div class="check-action">' +
          (dataField.CHUYENMANG == 0
            ? '<input type="checkbox" class="check" disabled><span class="name"></span>'
            : '<input type="checkbox" class="check" disabled checked><span class="name"></span>') +
          "</div>";
      }
    },

    onExcelQueryCellInfo(args) {
      let field = args.column.field;
      let dataField = args.data;

      if (field == "MA_TB") {
        let now = moment(new Date()); //todays date
        let end = moment(dataField.NGAY_HT, "DD/MM/YYYY hh:mm:ss"); // another date
        let duration = moment.duration(now.diff(end));
        let hoursDistance = duration.asHours();
        // console.log("hoursDistance" + hoursDistance);
        if (hoursDistance > 72) {
          args.style = { backColor: "ec3c3c" };
        } else if (hoursDistance < 24) {
          args.style = { backColor: "#8ae68d" };
        }
      }
      if (field == "NGAY_YC") {
        args.style = { fontColor: "ec3c3c" };
      }
      if (field == "TRAM_VT") {
        args.style = { fontColor: "#986498" };
      }
    },

    onStartXuatFile() {
      if (this.dsKhachHangKSFields.length == 0) {
        this.$root.toastError("Không có dữ liệu để xuất file !");
        return;
      } else {
        this.$bvModal.show("modal-xuat-dulieu");
      }
    },

    onExcelExport() {
      if (this.dsKhachHangKS.length <= 0)
        this.$toast.error("Chưa có dữ liệu để xuất excel!");
      else {
        let excelExportProperties = {
          fileName: "export.xlsx",
          dataSource: this.dsKhachHangKS,
        };
        this.$refs.gridKhachHangKS.excelExport(excelExportProperties);
      }
    },
    async Ver2_onExcelExport(){
        let treeDonVi = document.getElementById("treeview").ej2_instances[0];
        let donViId = treeDonVi.selectedNodes[0];
        if (!donViId) {
          this.$root.toastError("Chưa chọn đơn vị!");
          return;
        }
        this.idDonVi = donViId
        this.loading(true);
        try {
            const baseUrl = process.env.API;
            await this.axios({
            url: `${baseUrl}/web-cskh/api/ds-kh-ks-thanh-ly/export-file`,
            method: "POST",
            responseType: "blob",
            data: {
                nhom: this.loaiNhomSelected,
                loaiHdId: this.loaiHDSelected,
                dichVuVtId: this.dichVuSelected,
                donViId: this.idDonVi,
                tuNgay: moment(this.pTxtThoiGianKSTu).format("DD/MM/YYYY"),
                denNgay: moment(this.pTxtThoiGianKSDen).format("DD/MM/YYYY"),
                khaoSat: this.trangThaiSelected,
            },
            }).then((response) => {
                this.downloadFileZip(`danhSachKS.xlsx`,response.data)
            });
        } catch (error) {
            this.$root.toastError("Có lỗi trong quá trình xuất excel! Mời thử lại");
        } finally {
            this.loading(false);
        }
    },
    downloadFileZip(filename,data){
        let blob = new Blob([data],{type:"octet/stream"})
        let blobUrl = URL.createObjectURL(blob);

        var element = document.createElement('a');
        element.setAttribute('href', blobUrl);
        element.setAttribute('download', filename);

        element.style.display = 'none';
        document.body.appendChild(element);

        element.click();

        document.body.removeChild(element);
    },
    onDsLanGoiRowSelected(item) {},
    async layDsLanGoiChuaTL(loai, id) {
      try {
        let rs = await this.$root.context.get(
          "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-lan-goi-chua-tra-loi",
          {
            id: id,
            loai: loai,
            pageNo: 1,
            pageSize: 10000,
          }
        );
        if (rs.error_code === CSKH_CONST.API_SUCCESS) {
          return rs.data.data || [];
        }
      } catch (e) {
        console.log(e);
        return [];
      }
      return [];
    },
    onDonViSelect(item) {
      var treeObj = document.getElementById("treeview").ej2_instances[0];
      this.donViSelected = treeObj.selectedNodes;
    },
    onTenDonViSearch(event) {
      if (event.keyCode == 13) {
        if (this.tenDonViSearch) {
          let filterDonVi = this.treeDonViFields.dataSource
            .filter(x =>
              x.TEN_DV.toLowerCase().includes(
                this.tenDonViSearch.trim().toLowerCase()
              )
            )
            .sort((a, b) => a.TEN_DV.localeCompare(b.TEN_DV));

          if (filterDonVi.length == 0) {
            this.$root.toastError('Không tìm thấy đơn vị phù hợp')
            return
          }

          let treeObj = document.getElementById("treeview").ej2_instances[0];

          if (this.currentFindIndex >= filterDonVi.length - 1)
            this.currentFindIndex = 0;

          this.selectedNodes = []
          treeObj.selectedNodes = [];
          if (filterDonVi[this.currentFindIndex]) {
            let donViId = filterDonVi[this.currentFindIndex].DONVI_ID;
            let dsDonViChaId = this.findParentOnTree(filterDonVi[this.currentFindIndex].DONVI_CHA_ID)

            this.$refs.treeObj.expandAll(dsDonViChaId)
            this.selectedNodes.push("" + donViId);
            this.$refs["treeObj"].ensureVisible("" + donViId);
            setTimeout(function() {
              // document.getElementById("treeview").ej2_instances[0].getElement("" + donViId).scrollIntoView({ behavior: "smooth" })
              this.scrollToTop;
            }, 100)
            this.currentFindIndex++
          }

        }
      }
    },
    findParentOnTree(nodeParentId) {
      var dsParents = this.treeDonViFields.dataSource.filter(x => x.DONVI_ID == nodeParentId)
      var dsParentsId = []
      if (dsParents != null && dsParents.length > 0) {
        dsParentsId.push('' + dsParents[0].DONVI_ID)
        dsParentsId = dsParentsId.concat(this.findParentOnTree(dsParents[0].DONVI_CHA_ID))
      }
      return dsParentsId;
    },
    onTenDonViChange() {
      this.currentFindIndex = 0;
    },
    scrollToTop() {
      document.getElementById("txtTenDonViSearch").scrollIntoView({ behavior: "smooth" })
      // this.$refs.txtTenDonViSearch.scrollIntoView({ behavior: "smooth" })
    },
  },
};
</script>
