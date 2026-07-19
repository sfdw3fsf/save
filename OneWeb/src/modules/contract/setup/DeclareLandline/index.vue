<template src="./template.html"></template>
<script>
import state from "./state.js";
import {
  TrangThaiHD,
  DichVuVienThong,
  LoaiHopDong,
  KIEU_HD,
  KieuLapDat,
  TRANGTHAI_DONGBO,
  LoaiHinhTB,
  LOAI_DV,
} from "@/const/enums";
import api from "./api.js";
import moment from "moment";
import breadcrumb from "./components/breadcrumb.vue";
import Select2Ext from "./components/Select2Ext";
import DataGridCustom from "./components/DataGridCustom";
import ComboboxGridCustom from "./components/ComboboxGridCustom";
import VueDateExt from "./components/VueDateExt";
import frmBanDoTB from "./popups/frmBanDoTB.vue";
import frmChonNe from "./popups/frmChonNe.vue";
import frnGiaoPhieuNV from "./popups/frnGiaoPhieuNV.vue";
import frmDauNoiTuyenCap from "./popups/frmDauNoiTuyenCap.vue";
import ChuyenTo from "@/modules/contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue";
import CapVatTuTB from "@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue";
import frmThongTinLienHe from "./popups/frmThongTinLienHe.vue";
import ThongTinHenKhachHangModal from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue";
import frmThongTinKT_2 from "./popups/frmThongTinKT_2.vue";
import ThayDoiKhuVucQuanLyThueBaoModal from "../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue";
import TonPhieuGiaoModal from "@/modules/contract/setup/Payment/components/TonPhieu.vue";
import DuAnHDTB from "@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/DuAnHDTB/DuAnHDTB";
import DoKiemModal from "@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/DoKiem/DoKiemModal.vue";
import frmTraPhieuTC from "../ReturnInstallTicket/frmTraPhieuTC.vue";
import ExportDataModal from "@/modules/contract/profile/ExportData/ExportDataModal.vue";
import CompleteProfileChangeTSMyTVAPI from "@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/CompleteProfileChangeTSMyTVAPI";
import InBienBan from "@/modules/print/InBienBan/InBienBan";
import popupGiaoPhieuPhoiHop from "../HandOverTicketVerifyCombine/popupGiaoPhieuPhoiHop.vue";
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue';
import ThayDoiKenhThu from '@/modules/QLTN/ThayDoiKenhThu'

export default {
  props: [
    'isPopup',
    'Tag',
    '_matb_link',
    '_vquytrinh_id'
  ],
  data() {
    return {
      matb_link: "",
      ...state,
      arrTonphieu: [],
      modelIn: {},
      dataKenhThu: {
        ma_gd : ''
      },
      kieubando: 1,
      modalTitle: "",
      position: { X: "center", Y: "top" },
      collumns: [
        {
          fieldName: "ma_tb",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
          textAlign: "left",
          freeze: "left",
        },
        {
          fieldName: "ma_tb_cha",
          headerText: "Số chính",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
          textAlign: "left",
        },
        {
          fieldName: "ten_tb",
          headerText: "Tên thuê bao",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          textAlign: "left",
        },
        {
          fieldName: "diachi_ld",
          headerText: "Địa chỉ lắp đặt",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
        },
        {
          fieldName: "ma_kv",
          headerText: "Mã khu vực",
          allowFiltering: true,
          allowSorting: false,
          width: 170,
        },
        {
          fieldName: "ten_kv",
          headerText: "Tên khu vực",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
        },
        {
          fieldName: "ngay_yc",
          headerText: "Ngày YC",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
        },
        {
          fieldName: "ngaygiao",
          headerText: "Ngày giao",
          customAttributes: { class: "red" },
          allowFiltering: true,
          allowSorting: false,
          width: 140,
        },
        {
          fieldName: "dv_hen",
          headerText: "Đơn vị hẹn",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: 140,
        },
        {
          fieldName: "goi_kt",
          headerText: "Gói KT",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: 140,
        },
        {
          fieldName: "gio_conlai",
          headerText: "Giờ còn lại",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
        },
        {
          fieldName: "chitieu_tg",
          headerText: "Chỉ tiêu TG",
          allowFiltering: true,
          allowSorting: false,
          width: 140,
        },
        {
          fieldName: "giohen_tu",
          headerText: "Hẹn từ",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: 140,
        },
        {
          fieldName: "giohen_den",
          headerText: "Hẹn đến",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: 140,
        },
        {
          fieldName: "loaihinh_tb",
          headerText: "Loại hình",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: 140,
        },
        {
          fieldName: "ma_gd",
          headerText: "Mã GD",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: 140,
        },
        {
          fieldName: "thoigian_ck",
          headerText: "Thời gian cam kết",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: 200,
        },
        {
          fieldName: "ngay_ht",
          headerText: "Ngày bàn giao",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: 170,
        },
        {
          fieldName: "dv_giao",
          headerText: "Đơn vị giao",
          customAttributes: { class: "blue" },
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: 170,
        },
        {
          fieldName: "trangthai_in",
          headerText: "Trạng thái in",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: 170,
        },
        {
          fieldName: "ghichu",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: false,
          autoFit: "true",
          width: 140,
        },
      ],
      dialogComponent: null,
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentAttr: {},
      popupComponentEvts: {
        "form-close": this.popupClosed,
      },
      dialogEvts: {
        close: this.dlgClose,
      },
      giaoPhieuViewDialog: {
        isVisiable: false,
        input: {
          vhuonggiao_id: null,
          vloai_hd: null,
          vdichvuvt_id: null,
          vma_gd: null,
          vquytrinh: null,
          vtrangthaiphieu: 1,
        }
      },
      animationSettings: { effect: 'None' },
    };
  },
  components: {
    breadcrumb,
    InBienBan,
    Select2Ext,
    DataGridCustom,
    VueDateExt,
    frmBanDoTB,
    frmChonNe,
    frnGiaoPhieuNV,
    frmDauNoiTuyenCap,
    ChuyenTo,
    CapVatTuTB,
    frmThongTinLienHe,
    ThongTinHenKhachHangModal,
    frmThongTinKT_2,
    ThayDoiKhuVucQuanLyThueBaoModal,
    TonPhieuGiaoModal,
    DuAnHDTB,
    DoKiemModal,
    ComboboxGridCustom,
    frmTraPhieuTC,
    ExportDataModal,
    popupGiaoPhieuPhoiHop,
    GiaoPhieuView,
    ThayDoiKenhThu
  },
  mounted() {
    this.frmHoanCongCoDinh_Load();
  },
  methods: {
    thayDoiKhuVucQuanLyThueBaoModal_success() {
      this.HienThiDanhSachHDTB();
    },
    traphieu_success() {
      this.HienThiDanhSachHDTB();
    },
    async popupClosed(val) {
      console.log("popupClosed");
      if (!val) {
        this.popupRetVal = {};
        this.ClosePopup("modalComponentsITVAS");
        return;
      }

      this.ClosePopup("modalComponentsITVAS");
    },
    async dlgClose(e) {
      console.log("dlgClose", e);
    },

    overlayClick() {
      this.$refs.generalDialog.hide();
    },
    async tsbtnGiaoPhieu_PH_V2_Click() {
      if (this.$refs.gvDanhSach.getSelectedRecords().length == 0) return;
      console.log("vhdtb_id_cur", Number(this.hdtb_id));
      console.log("vphieu_id_cur", Number(this.phieu_id));
      console.log("vquytrinh_id", this.quytrinh_id);
      this.$refs.popupGiaoPhieuPhoiHop.showModal();
    },
    async tsbtnHoanThienHS_Click() {
      try {
        this.loading(true);
        let data = {
          HDTB_ID: this.hdtb_id,
          LUONG_ID: this.luong_id,
          PHIEU_ID: this.phieu_id,
          NHANVIEN_ID: this.$root.token.getNhanVienID(),
          NGAY_BG: this.$refs.dtpNgayBG.value,
          LOAIHD_ID: this.loaihd_id,
          DONVI_ID: this.$root.token.getDonViID(),
          MAY_CN: this.$root.token.getMaNhanVien(),
          NGUOI_CN: this.$root.token.getMaNhanVien(),
          IP_CN: "1.1.1.1",
        };
        let rs = await this.axios.post(
          "/web-ccdv/khaibaocodinh/fn_hths_chuyendb_hoancongcodinh",
          {
            vds: JSON.stringify(data),
          }
        );
        if (rs.data.data == 1)
          this.$toast.success("Cật nhật dữ liệu vào danh bạ thành công!");
        else this.$toast.error(rs.data.data);
        this.loading(false);
        this.HienThiDanhSachHDTB();
      } catch (e) {
        this.loading(false);
        this.$toast.error("Cật nhật dữ liệu vào danh bạ thất bại!");
      }
    },
    tsbtnTraPhieu_Click() {
      this.$refs.frmTraPhieuTC.frmTraPhieuTC(
        this.$refs.txtMaTB.value,
        this.$refs.cboLoaiHD.value,
        DichVuVienThong.CO_DINH,
        this.$root.token.getDonViID(),
        this.$refs.txtMaTB.value,
        "0",
        "0",
        3
      );
      this.$refs.popupFrmTraPhieuTC.show();
    },
    chkLayTSo_CheckedChanged() {
      if (this.$refs.chkLayTSo.checked) {
        if (this.$refs.txtSoMayTN.value.trim() == "") {
          this.$toast.error(
            "Thuê bao đang lắp trên đường không số. \n Bạn không thể lấy các thông số đã có."
          );
        } else {
          this.axios
            .post("web-ccdv/khaibaocodinh/lay_thongso_thicong", {
              ma_tb: this.$refs.txtMaTB.value,
              madoicap: this.madoicap,
              kieu: 0,
            })
            .then((res) => {
              if (res.data && res.data.data.length > 0) {
                let item = res.data.data[0];
                this.$refs.txtTuyenCap.value = item["tuyencap"].toString();
                this.$refs.txtLienTu.value = item["lientu"].toString();
                if (item["loaicap_id"].toString() != "")
                  cboLoaiCap.SelectedValue = Number(
                    item["loaicap_id"].toString()
                  );
                this.$refs.txtTuCapNgon.value = item["tucap_p"].toString();
                this.$refs.txtDoiCapNgon.value = item["doicap_p"].toString();
                this.$refs.txtHopCapNgon.value = item["hopcap_p"].toString();
                this.$refs.txtCapNgon.value = item["cap_p"].toString();
                this.$refs.txtCapGoc.value = item["cap_goc"].toString();
                this.$refs.txtDoiCapGoc.value = item["doicap_goc"].toString();
                this.$refs.txtTuCapGoc.value = item["tucap_goc"].toString();
                this.$refs.txtSoMetDay.value = item["culy"].toString();
                if (!this.$refs.txtSoMetDay.value)
                  this.$refs.txtSoMetDay.value = "0";
              } else {
                this.$toast.error("Không tìm thấy thông tin!");
              }
            });
        }
      }
    },
    clickButton(ref) {
      let button = this.$refs[ref];
      if (!button || button.classList.contains("non-ative")) {
        return false;
      }

      switch (ref) {
        case "btnLayTTMoi":
          this.HienThiDanhSachHDTB();
          break;
        case "tsbtnHoanCong":
          this.HoanCong();
          break;
        case "tsbtnHoanCong2":
          this.HoanCong();
          break;
        case "tsbtnChapNhan":
          this.CapNhat();
          break;
        case "tsbtnGiaoViec":
          this.GiaoViec();
          break;
        case "tsbtnKichHoat":
          this.KichHoat();
          break;
        case "tsbtnHuyKH":
          this.HuyKichHoat();
          break;
        case "tsbtnDauNoiCap":
          this.DauNoi();
          break;
        case "tsbtnChuyenTo":
          this.ChuyenTo();
          break;
        case "tsbtnVatTu":
          this.tsbtnVatTu_Click();
          break;
        case "tsbtnVatTuMoi":
          this.tsbtnVatTuMoi_Click();
          break;
        case "tsbtnThongTinLH":
          this.tsbtnThongTinLH_Click();
          break;
        case "tsbtnHen":
          this.tsbtnHen_Click();
          break;
        case "tsbtnThongTinKT":
          this.tsbtnThongTinKT_Click();
          break;
        case "tsbtnNVKT":
          this.tsbtnNVKT_Click();
          break;
        case "tsbtnGiaoPhieu_Ton":
          this.tsbtnGiaoPhieu_Ton_Click();
          break;
        case "tsbtnDuAn":
          this.tsbtnDuAn_Click();
          break;
        case "tsbtnKQ_DoKiem":
          this.tsbtnKQ_DoKiem_Click();
          break;
        case "tsbtnSua_KenhThu":
          this.tsbtnSua_KenhThu_Click();
          break;
        case "tsbtnGiaoPhieu":
          this.tsbtnGiaoPhieu_Click();
          break;
      }
    },
    txtNV_TiepThi_KeyPress(event) {
      event.preventDefault();
    },
    async updateNDTH() {
      if (this.$refs.txtNoiDungTH.value.length > 150) {
        this.$toast.error("Nội dung TH dữ liệu vượt quá 150 ký tự!");
        return;
      }
      this.loading(true);
      api
        .updateNDTH(this.axios, {
          phieu_id: this.phieu_id,
          noidung: this.$refs.txtNoiDungTH.value,
        })
        .catch((err) => {
          this.loading(false);
          this.$toast.error('Có lỗi: " ' + err.data.message + '"');
        })
        .then((res) => {
          this.loading(true);
          this.$toast.success("Cập nhật thành công");
          this.loading(false);
          // data = this.$refs.gvDanhSach.getSelectedRecords()
          this.HienThiDanhSachHDTB();
        });
    },
    async txtMaGD_KeyPress() {
      if (this.$refs.txtMaGD.value) {
        this.loading(true);
        let vloai_id = 0; // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
        if (this.$refs.rdoPhieuMoi.checked) vloai_id = 0;
        else vloai_id = 1;
        let rs_dsIn = await api.LAY_DS_PHIEU_HOANCONG_119(this.axios, {
          dichvuvt_id: DichVuVienThong.CO_DINH,
          tthd_id: this.tthd_id,
          nhanvien_id: this.$root.token.getNhanVienID(),
          ma_gd: this.$refs.txtMaGD.value,
          phieu_id: 0,
          loaihd_id: Number(this.$refs.cboLoaiHD.value),
          caidat_dv: 0,
          kieu_id: 0,
          huonggiao_id: this.huonggiao_id,
          loai_id: vloai_id,
          loaidv_id: Number(this.$refs.cboLoaiDVTK.value),
          donvi_tk_id: Number(this.$refs.cboDViTimKiem.value),
        });
        this.dsIn = rs_dsIn.data ? rs_dsIn.data.data : [];
        let dtList = this.dsIn;
        dtList = dtList.map((item) => ({
          ...item,
          thoigian_ck: this.formatDate(item.thoigian_ck),
        }));
        console.log(dtList);
        this.$refs.gvDanhSach.dataSource = dtList;
        if (dtList.length > 0) {
          this.$refs.gvDanhSach.setCurrentSelectedRow(this.old_index);
        } else {
          this.$refs.tsbtnTraPhieu.classList.add("non-ative");
          this.$refs.tsbtnChapNhan.classList.add("non-ative");
          this.$refs.tsbtnHoanCong.classList.add("non-ative");
          this.$refs.tsbtnHoanThienHS.classList.add("non-ative");
          // this.$toast.error("Không có dữ liệu, vui lòng thử lại");
          this.Clean();
        }
        this.loading(false);
      }
    },
    async txtMaTB_KeyPress() {
      this.loading(true);
      await this.txtMaTB_KeyPress_load()
      this.loading(false);
    },
    async txtMaTB_KeyPress_load() {
      return new Promise(async (resolve, reject) => {
        try {
          if (this.$refs.txtMaTB.value) {

            let vloai_id = 0; // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
            if (this.$refs.rdoPhieuMoi.checked) vloai_id = 0;
            else vloai_id = 1;
            let rs_dsIn = await api.LAY_DS_PHIEU_HOANCONG_119(this.axios, {
              dichvuvt_id: DichVuVienThong.CO_DINH,
              tthd_id: this.tthd_id,
              nhanvien_id: this.$root.token.getNhanVienID(),
              ma_gd: this.$refs.txtMaTB.value,
              phieu_id: 0,
              loaihd_id: Number(this.$refs.cboLoaiHD.value),
              caidat_dv: 0,
              kieu_id: 0,
              huonggiao_id: this.huonggiao_id,
              loai_id: vloai_id,
              loaidv_id: Number(this.$refs.cboLoaiDVTK.value),
              donvi_tk_id: Number(this.$refs.cboDViTimKiem.value),
            });
            this.dsIn = rs_dsIn.data ? rs_dsIn.data.data : [];
            let dtList = this.dsIn;
            dtList = dtList.map((item) => ({
              ...item,
              thoigian_ck: this.formatDate(item.thoigian_ck),
            }));
            this.$refs.gvDanhSach.dataSource = dtList;
            if (dtList.length > 0) {
              this.$refs.gvDanhSach.setCurrentSelectedRow(this.old_index);
              this.$refs.gvDanhSach.$refs.DataGridCustom.flagSelectedRowIndexes = [];
              this.$refs.gvDanhSach.$refs.DataGridCustom.flagSelectedRowIndexes.push(this.old_index);
            } else {
              this.$refs.tsbtnTraPhieu.classList.add("non-ative");
              this.$refs.tsbtnChapNhan.classList.add("non-ative");
              this.$refs.tsbtnHoanCong.classList.add("non-ative");
              this.$refs.tsbtnHoanThienHS.classList.add("non-ative");
              // this.$toast.error("Không có dữ liệu, vui lòng thử lại");
              this.Clean();
            }
          }
        } catch(err) {
          console.log(err)
        }
        resolve(true)
      })
    },
    chkTimKiem_CheckedChanged(value) {
      if (this.$refs.chkTimKiem.checked) {
        this.$refs.cboLoaiDVTK.disabled = false;
        this.$refs.cboDViTimKiem.disabled = false;
        if (this.$refs.cboLoaiDVTK.dataSource.length == 0)
          this.$refs.cboLoaiDVTK.dataSource = [
            {
              id: 6,
              ten: "Host",
            },
            {
              id: 25,
              ten: "Trạm vệ tinh",
            },
          ];
        this.$refs.cboLoaiDVTK.dataValueField = "id";
        this.$refs.cboLoaiDVTK.dataTextField = "ten";
        this.$refs.cboLoaiDVTK.value = this.$refs.cboLoaiDVTK.value
          ? this.$refs.cboLoaiDVTK.value
          : 6;
        this.cboLoaiDVTK_SelectedValueChanged(this.$refs.cboLoaiDVTK.value);
        try {
          if (!this.$refs.cboDViTimKiem.value) return;
          this.HienThiDanhSachHDTB_TK_LOAIDV(
            this.$refs.cboLoaiDVTK.value,
            this.$refs.cboDViTimKiem.value
          );
        } catch (err) {
          this.$toast.error("Tìm kiếm lỗi!");
        }
      } else {
        this.$refs.cboLoaiDVTK.disabled = true;
        this.$refs.cboDViTimKiem.disabled = true;
        this.HienThiDanhSachHDTB();
      }
    },
    cboDViTimKiem_EditValueChanged(value) {
      try {
        this.HienThiDanhSachHDTB_TK_LOAIDV(
          value ? value : this.$refs.cboLoaiDVTK.value,
          this.$refs.cboDViTimKiem.value
        );
      } catch (err) {
        console.log(err);
      }
    },
    async cboLoaiDVTK_SelectedValueChanged(value) {
      if (!value) return;
      if (value == LOAI_DV.HOST) {
        let rs = await api.getDsDonvi(this.axios, { loaidv_id: LOAI_DV.HOST });
        this.$refs.cboDViTimKiem.dataSource =
          rs.data && rs.data.data ? rs.data.data : [];
        this.$refs.cboDViTimKiem.dataValueField = "donvi_id";
        this.$refs.cboDViTimKiem.dataTextField = "ten_dv";
      } else {
        // this.$refs.cboDViTimKiem.dataSource = [];
      }
    },
    async HienThiDanhSachHDTB_TK_LOAIDV() {
      this.loading(true);
      let vloai_id = 0; // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
      if (this.$refs.rdoPhieuMoi.checked) vloai_id = 0;
      else vloai_id = 1;
      let rs_dsIn = await this.axios.post(
        "web-ccdv/khaibaocodinh/lay_ds_phieu_hoancong_119_theo_dv",
        {
          dichvuvt_id: DichVuVienThong.CO_DINH,
          tthd_id: this.tthd_id,
          nhanvien_id: this.$root.token.getNhanVienID(),
          ma_gd: "",
          phieu_id: 0,
          loaihd_id: Number(this.$refs.cboLoaiHD.value),
          caidat_dv: 0,
          kieu_id: 0,
          huonggiao_id: this.huonggiao_id,
          loai_id: vloai_id,
          loaidv_id: Number(this.$refs.cboLoaiDVTK.value),
          donvi_tk_id: Number(this.$refs.cboDViTimKiem.value),
        }
      );
      this.dsIn = rs_dsIn.data ? rs_dsIn.data.data : [];
      let dtList = this.dsIn;
      dtList = dtList.map((item) => ({
        ...item,
        thoigian_ck: this.formatDate(item.thoigian_ck),
      }));
      this.$refs.gvDanhSach.dataSource = dtList;
      if (dtList.length > 0) {
        this.$refs.gvDanhSach.setCurrentSelectedRow(this.old_index);
      } else {
        this.$refs.tsbtnTraPhieu.classList.add("non-ative");
        this.$refs.tsbtnChapNhan.classList.add("non-ative");
        this.$refs.tsbtnHoanCong.classList.add("non-ative");
        this.$refs.tsbtnHoanThienHS.classList.add("non-ative");
        this.Clean();
      }

      this.loading(false);
    },
    async tsbtnGiaoPhieu_Click() {
      let rs = await this.axios.post(
        "web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id",
        { luong_id: this.luong_id }
      );
      let dtTemp = rs.data.data;
      if (dtTemp.length > 0) {
        this.giaoPhieuViewDialog.input = {
          vhuonggiao_id: dtTemp[0].huonggiao_id,
          vloai_hd: this.loaihd_id,
          vdichvuvt_id: 1,
          vma_gd: this.$refs.txtMaGD.value,
          vquytrinh: this.quytrinh_id,
          vtrangthaiphieu: 1,
        };
        this.GiaoPhieuView_OnClick()
      } else {
        this.$toast.error("Chưa có hướng giao được gán");
      }
    },
    tsbtnSua_KenhThu_Click() {
      if (!this.$refs.gvDanhSach.getSelectedRecords().length == 0) {
        this.$toast.error("Bạn chưa chọn phiếu!");
        return
      }
      this.dataKenhThu.ma_gd = this.$refs.txtMaGD.value
      console.log(this.dataKenhThu.ma_gd)
      this.$bvModal.show('formthaydoikenhthu')
    },
    tsbtnKQ_DoKiem_Click() {
      if (this.hdtb_id != 0) {
        this.$refs.doKiem.showModal();
      } else {
        this.$toast.error("Bạn chưa chọn phiếu!");
      }
    },
    tsbtnDuAn_Click() {
      this.$refs.popupDuAnHDTB.showModal();
    },
    tsbtnGiaoPhieu_Ton_Click() {
      if (this.phieu_id == 0) {
        this.$toast.error("Hãy chọn phiếu trước để nhập lý do tồn!");
        return;
      }
      //binding phieu_id, tthd_id
      // this.$refs.tonPhieuGiaoModal.showModal();
      this.$refs.tonPhieuGiaoModal.showPhieuTon(
        this.phieu_id,
        this.tthd_id,
        []
      );
    },
    tsbtnNVKT_Click() {
      this.dataPopupKhuVuc.hdtb_id = this.hdtb_id;
      this.dataPopupKhuVuc.donviql_id = 0;
      console.log(this.dataPopupKhuVuc)
      this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal();
    },
    tsbtnThongTinKT_Click() {
      this.$refs.frmThongTinKT_2.thuebao_id = this.thuebao_id;
      this.$refs.frmThongTinKT_2.ma_tb = this.$refs.txtMaTB.value;
      this.$refs.frmThongTinKT_2.dichvuvt_id = 4;
      this.$refs.frmThongTinKT_2.kieu = 1;
      this.$refs.frmThongTinKT_2.show();
    },
    tsbtnHen_Click() {
      this.$refs.thongTinHenKhachHangModal.showModal();
    },
    tsbtnThongTinLH_Click() {
      this.$refs.frmThongTinLienHe.hdkh_id = Number(this.hdkh_id);
      this.$refs.frmThongTinLienHe.show();
    },
    tsddbIn_Click() {
      try {
        this.kt_thaydoi = false;
        this.InPhieu();
        if (this.kt_thaydoi) {
          let matb = this.$refs.txtMaTB.value;
          this.HienThiDanhSachHDTB();
          let index = this.$refs.gvDanhSach.dataSource.filter(
            (item) => item.ma_tb == matb
          );
          if (index >= 0) {
            this.$refs.gvDanhSach.$refs.grid.selectRow(index, true);
          }
        }
      } catch (error) {
        console.log(error);
        this.$root.$toast.error("Có lỗi: " + error.message);
      }
    },
    async FN_MAP_ID(dk, idNeo, table) {
      let result = "";
      const req = {
        dk: dk,
        id_neo: idNeo,
        table: table,
      };
      let data = this.GetData(
        await CompleteProfileChangeTSMyTVAPI.FN_MAP_ID(this.axios, req)
      );
      if (data) {
        result = data;
      }
      return result;
    },
    async InPhieu() {
      try {
        let rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao",
          { param: this.hdtb_id, type: 1 }
        )
        let vhdkhid = rs.data.data
        if (vhdkhid == "-1") {
          this.$root.$toast.error(
            "Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!"
          );
          return;
        }
        if (this.$refs.gvDanhSach.getSelectedRecords().length === 0) {
          this.$root.$toast.error("Hãy chọn phiếu!");
          return;
        }
        let nhdkhid = [];
        let nhdtbid = [];
        let nphieuid = [];
        nhdkhid = this.$refs.gvDanhSach
          .getSelectedRecords()
          .map((item) => item.hdkh_id);
        nhdtbid = this.$refs.gvDanhSach
          .getSelectedRecords()
          .map((item) => item.hdtb_id);
        nphieuid = this.$refs.gvDanhSach
          .getSelectedRecords()
          .map((item) => item.phieu_id);
        this.modelIn.n_phieu_id = nphieuid.join(",");
        this.modelIn.ma_gd = this.$refs.txtMaGD.value.trim();
        this.modelIn.n_hdkh_id = nhdkhid.join(",");
        this.modelIn.n_hdtb_id = nhdtbid.join(",");
        this.modelIn.nvth_id = -1;
        this.modelIn.huonggiao_id = this.huonggiao_id;
        this.modelIn.tentram_tb = "";
        this.$refs.popupInBB.showModal();
      } catch (error) {
        this.$root.$toast.error("Có lỗi khi in phiếu: " + error.message);
      }
    },
    tsbtnVatTuMoi_Click() {
      this.modelVatTu.phieu_id = this.phieu_id;
      this.modelVatTu.loaihd_id = this.loaihd_id;
      this.modelVatTu.hdtb_id = this.hdtb_id;
      this.modelVatTu.thuebao_id = this.thuebao_id;
      this.modelVatTu.vdvvt_id = this.dichvuvt_id;
      this.modelVatTu.loaitb_id = this.loaitb_id;
      this.modelVatTu.vma_tbcur = this.$refs.txtMaTB.value;
      this.modelVatTu.baohong_id = 0;
      this.$refs.frmVatTuThueBao.openDialog();
    },
    async tsbtnXuatFile_Click() {
      this.$refs.ExportDataModal.showModal();
    },
    tsbtnVatTu_Click() {
      if (this.phieu_id != 0) {
        this.modelVatTu.phieu_id = this.phieu_id;
        this.modelVatTu.loaihd_id = this.loaihd_id;
        this.modelVatTu.hdtb_id = this.hdtb_id;
        this.modelVatTu.thuebao_id = this.thuebao_id;
        this.$refs.frmVatTuThueBao.openDialog();
      } else {
        MessageBox.Show("Bạn chưa chọn phiếu!");
      }
    },
    async ChuyenTo() {
      try {
        if (await this.PHAILAM("KIEMTRA_VT_CHUYENTO")) {
          let bh = await this.axios.post(
            "/web-ccdv/khaibaocodinh/lay_ds_phieuvt_hdbh",
            {
              baohong_id: 0,
              hdtb_id: this.hdtb_id,
              kieutbi: 0,
              phieu_id: this.phieu_id,
              status: 0,
            }
          );
          if (bh.data.data.length > 0) {
            this.$toast.error(
              "Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ"
            );
            return;
          }
        }
        //
        //this.ShowSuccess(`Chuyển sang UR chuyển tổ`)
        if (this.phieu_id > 0) {
          /*
           WinUIQLDHXLSuCo.frmChuyenTo f = new WinUIQLDHXLSuCo.frmChuyenTo();
                f.hdtb_id = hdtb_id;
                f.baohong_id = 0;
                f.phieu_id = phieu_id;
          */
          this.$refs.popupChuyenTo.openDialog(this.phieu_id, this.hdtb_id);
        } else {
          this.$toast.error("Bạn chưa chọn thuê bao để chuyển !");
          return;
        }

        //
        // WinUIQLDHXLSuCo.frmChuyenTo f = new WinUIQLDHXLSuCo.frmChuyenTo();
        // f.hdtb_id = hdtb_id;
        // f.baohong_id = 0;
        // f.phieu_id = phieu_id;
        // f.ShowDialog();
        // if (f.Chapnhan)
        //     HienThiDanhSach(dichvuvt_id);
      } catch (ex) {
        console.log("Lỗi chuyển tổ" + ex);
        this.$toast.error(JSON.stringify(ex.data ? ex.data.message : ex));
      }
    },
    DauNoi() {
      let selected = this.$refs.gvDanhSach.getSelectedRecords();
      if (selected.length == 0) {
        this.$toast.error("Chưa có thông tin thuê bao để đấu nối!");
        return;
      }
      let thongTinDauNoi = {
        choPhepDoiTramTbi: true,
        tramTbiId: this.tramtb_id ? this.tramtb_id : 0,
        chonTramTbiTheoTB: false,
        thueBaoId: this.hdtb_id,
        kieu: 1,
      };
      this.$refs.frmDauNoiTuyenCap.data = thongTinDauNoi;
      this.$refs.frmDauNoiTuyenCap.show();
    },
    onSuccessNhapCapThueBao() {
      this.HienThiDanhSachHDTB();
    },
    async HoanCong() {
      try {
        this.loading(true);
        let data = [];
        let selected = this.$refs.gvDanhSach.getSelectedRecords();
        selected.forEach((item) => {
          data.push({
            HDTB_CHA_ID: item.hdtb_cha_id,
            HDTB_ID: item.hdtb_id,
            PHIEU_ID: item.phieu_id,
            HDKH_ID: item.hdkh_id,
            THUEBAO_CHA_ID: item.thuebao_cha_id,
          });
        });
        let sochinh = await api.phailam(this.axios, {
          luong_id: this.luong_id,
          code: "GIAOVIEC",
        });
        let dt = [
          {
            HDTB_ID: this.hdtb_id,
            HDKH_ID: this.hdkh_id,
            THUEBAO_CHA_ID: this.thuebao_cha_id,
            LUONG_ID: this.luong_id,
            SOCHINH: Number(
              sochinh.data && sochinh.data.data ? sochinh.data.data : 0
            ),
            LOAIHD_ID: this.loaihd_id,
            LOAITB_ID: this.loaitb_id,
            NHANVIEN_ID: this.$root.token.getNhanVienID(),
            NGAY_CN: moment().format("DD/MM/YYYY HH:mm:ss"),
            PHIEU_ID: this.phieu_id,
            DONVI_ID: this.$root.token.getDonViID(),
            MAY_CN: this.$root.token.getMaNhanVien(),
            NGUOI_CN: this.$root.token.getUserName(),
            IP_CN: "1.1.1.1",
            SO_DT: this.$refs.txtSoDT.value,
            HUONGGIAO_ID: this.huonggiao_id,
            HDTB_CHA_ID: this.hdtb_cha_id,
            MA_TB: this.$refs.txtMaTB.value,
            NE_ID: this.ne_id,
            NE_ID_CU: this.ne_cu_id,
            TRAMVETINH_ID: this.$refs.cboTramVeTinh.value,
            TTHD_ID: this.tthd_id,
            TRAMTB_ID: this.tramtb_id ? this.tramtb_id : null,
            NE: this.ne,
            DOICAPNGON: this.$refs.txtDoiCapNgon.value,
            DOICAPGOC: this.$refs.txtDoiCapGoc.value,
            HOPCAPNGON: this.$refs.txtHopCapNgon.value,
            TUCAPNGON: this.$refs.txtTuCapNgon.value,
            TUYENCAP: this.$refs.txtTuyenCap.value,
            MADOICAP: this.madoicap ? this.madoicap : 0,
            LIENTU: this.$refs.txtLienTu.value,
            CAPGOC: this.$refs.txtCapGoc.value,
            CAPNGON: this.$refs.txtCapNgon.value,
            TUCAPGOC: this.$refs.txtTuCapGoc.value,
            LOAICAP: this.$refs.cboLoaiCap.value,
            SOMETDAY: this.$refs.txtSoMetDay.value,
            STATUS: this.status,
            TEN_DV: this.$refs.txtTramNE.value,
            NGUOI_GV: this.$refs.cboNguoiGV.value,
          },
        ];

        let params = {
          ds_in: JSON.stringify(data),
          dt: JSON.stringify(dt),
          ngay_bg: this.formatDate(this.$refs.dtpNgayBG.value),
          ngay_gv: this.formatDate(this.$refs.dtpNgayGV.value),
          key_parameter: "xx",
        };
        let hoancong = await api.fn_thuchien_hoancong_hoancongcodich(
          this.axios,
          params
        );
        if ((!hoancong.data.data || hoancong.data.data.split("-")[0] == "1" || hoancong.data.message == "Success") && hoancong.data.error_code === 'BSS-00000000') {
          if(hoancong.data && hoancong.data.data && hoancong.data.data.split("-").length > 1)
            this.$toast.success(hoancong.data.data.split("-")[1]);
          else
            this.$toast.success("Hoàn công thành công");
          await this.HienThiDanhSachHDTB();
        } else
          this.$toast.error(hoancong.data.message);
        this.loading(false);
      } catch (e) {
        this.loading(false);
        console.log(e);
        this.$toast.error("Hoàn công thất bại");
      }
    },
    async HuyKichHoat() {
      if (this.$refs.gvDanhSach.getSelectedRecords().length == 0) {
        this.$toast.error("Chưa chọn thuê bao để hủy kích hoạt.");
        return;
      }
      this.$confirm(
        "Bạn có chắc chắn thực hiện hủy kích hoạt Số máy " +
          this.$refs.txtMaTB.value +
          " không?",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }
      ).then(async () => {
        this.loading(true);
        try {
          let khstatus = await api.capnhat_huykh_status(this.axios, {
            hdtb_id: this.hdtb_id,
            thuebao_id: this.thuebao_id,
            ma_tb: this.$refs.txtMaTB.value,
            ngay_kh: moment(new Date()).format("DD/MM/YYYY"),
            status: TRANGTHAI_DONGBO.CHUADONGBO,
            nguoi_kh: this.$root.token.getUserName(),
            ip_kh: "1.1.1.1",
            ne: this.$refs.txtNE.value,
            loaihd_id: this.loaihd_id
          });

          // let huykh = await api.fn_lay_tt_huy_kh_codinh(this.axios, {
          //   ds_para:
          //     '{"LOAIHD_ID":' +
          //     this.loaihd_id +
          //     ',"DICHVUVT_ID":' +
          //     1 +
          //     ',"LOAITB_ID":' +
          //     this.loaitb_id +
          //     ',"THUEBAO_ID":' +
          //     this.thuebao_id +
          //     ',"DIACHILD_ID":' +
          //     null +
          //     "} ",
          // });
          // if (huykh.data && huykh.data.data) {
          //   let parse = JSON.parse(huykh.data.data);
          //   this.$toast.success(
          //     parse.MESSAGE ? parse.MESSAGE : "Hủy kích hoạt thành công!"
          //   );
          // } else {
            if(khstatus.data.error_code == "BSS-00000000") {
              this.$toast.success("Hủy kích hoạt thành công. !");
            } else {
              this.$toast.error(khstatus.data.message);
            }
          // }
          await this.HienThiDanhSachHDTB();
        } catch (err) {
          console.log(err);
          this.$toast.error(
            'Hủy kích hoạt không thành công. !nLỗi tổng đài trả về: " ' +
              (err.data && err.data.message ? err.data.message : "") +
              '"'
          );
        }
        this.loading(false);
      });
    },
    formatDate(value_) {
      if (moment(value_).isValid()) {
        return moment(value_).format("DD/MM/YYYY HH:mm:ss");
      } else {
        return value_;
      }
    },
    async KichHoat() {
      if (this.$refs.gvDanhSach.getSelectedRecords().length == 0) {
        this.$toast.error("Chưa chọn thuê bao để kích hoạt.");
        return;
      }
      this.loading(true);
      try {
        let ds_in = [];
        let selected = this.$refs.gvDanhSach.getSelectedRecords();
        selected.forEach((item) => {
          ds_in.push({
            HDTB_CHA_ID: item.hdtb_cha_id,
            HDTB_ID: item.hdtb_id,
            PHIEU_ID: this.phieu_id,
            HDKH_ID: this.hdkh_id,
            THUEBAO_CHA_ID: item.hdtb_cha_id,
          });
        });

        let chuyendoi_ims
        console.log(this.loaihd_id, LoaiHopDong.CHUYENDOI_LH,this.hdtb_id, KieuLapDat.CHUYENDOIIMS_CD)
        if (this.loaihd_id == LoaiHopDong.CHUYENDOI_LH && this.$refs.gvDanhSach.dataSource.filter(r => r["kieuld_id"] == KieuLapDat.CHUYENDOIIMS_CD && r["hdtb_id"] == this.hdtb_id).length > 0) {
          chuyendoi_ims = true
        } else {
          chuyendoi_ims = false
        }

        let kichhoat = await api.kichhoat(this.axios, {
          hdtb_id: this.hdtb_id,
          loaitb_id: this.loaitb_id,
          luong_id: this.luong_id,
          sochinh: 1,
          thangsau: moment(new Date()).add(1, "months").format("YYYYMM"),
          ngay_ht: this.formatDate(this.$refs.dtpNgayBG.value),
          ds_in: JSON.stringify(ds_in),
          nguoi_cn: this.$root.token.getNhanVienID(),
          listDVGT: this.$refs.gridDichvuGT.getSelectedRecords(),
          loaihd_id: this.$refs.cboLoaiHD.value,
          ma_tb: this.$refs.txtMaTB.value,
          ip_cn: "1.1.1.1",
          ne: this.$refs.txtNE.value,
          ma_tb_cu: this.$refs.txtMaTB_Cu.value,
          chuyendoi_ims,
          thuebao_id: this.thuebao_id ? this.thuebao_id : "0"
        });

        let status = await api.updateStatus(this.axios, {
          hdtb_id: this.hdtb_id,
          luong_id: this.luong_id,
          thangsau: moment(new Date()).add(1, "months").format("YYYYMM"),
          ngay_ht: this.formatDate(this.$refs.dtpNgayBG.value),
          nguoi_cn: this.$root.token.getNhanVienID(),
          ip_cn: "1.1.1.1",
        });

        this.loading(false);
        this.HienThiDanhSachHDTB();
        this.$toast.success("Kích hoạt thành công. !");
      } catch (err) {
        console.log(err);
        this.$toast.error(
          'Kích hoạt không thành công. !nLỗi tổng đài trả về: " ' +
            (err.data && err.data.message ? err.data.message : "") +
            '"'
        );
        this.loading(false);
      }
    },
    GiaoViec() {
      let data = this.$refs.gvDanhSach.getSelectedRecords();
      if (data.length <= 0) {
        this.$toast.error("Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại! ");
        return;
      }
      if (!this.$refs.cboNguoiGV.value) {
        this.$toast.error("Hãy nhập nhân viên giao việc!");
        return;
      }
      console.log(this.$refs.dtpNgayBG);
      if (!this.$refs.dtpNgayBG.value) {
        this.$toast.error("Hãy nhập ngày giao việc!");
        return;
      }
      if (
        this.loaitb_id != LoaiHinhTB.INTERNET_ADSL_TINH_KHAC &&
        this.loaitb_id != LoaiHinhTB.INTERNET_FIBER_TINH_KHAC
      ) {
        if (this.$refs.chkNgayBG.checked) {
          if (
            moment(
              this.$refs.dtpNgayGV.value,
              "DD/MM/YYYY HH:mm:ss"
            ).valueOf() >
            (moment(this.$refs.dtpNgayBG.value),
            "DD/MM/YYYY HH:mm:ss").valueOf()
          ) {
            this.$toast.error(
              "Ngày giao việc không được lớn hơn ngày hoàn thành!"
            );
            return;
          }
        }
      }

      let selected = this.$refs.gvDanhSach.getSelectedRecords();
      this.dsphieuGV = [];
      if (selected.length > 0) {
        selected.forEach((item) => {
          this.dsphieuGV.push(item.phieu_id);
        });
      }

      //frnGiaoPhieuNV(CURRENT_PHIEU_ID, tt_nd.donvi_id, vnhanvien_giao_id, vngaygiao);
      //}
      let vnhanvien_giao_id = Number(this.$refs.cboNguoiGV.value);
      let vngaygiao = this.$refs.dtpNgayGV.value;
      this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(
        this.phieu_id,
        this.$root.token.getDonViID(),
        vnhanvien_giao_id,
        vngaygiao
      );
      this.$refs.frnGiaoPhieuNV.vma_tb = this.$refs.txtMaTB.value;
      this.$refs.frnGiaoPhieuNV.vhdtb_id = this.hdtb_id;
      this.$refs.frnGiaoPhieuNV.huonggiao_id = this.huonggiao_id;
      this.$nextTick(() => {
        this.$refs.frnGiaoPhieuNV.load();
        this.$refs.popupfrnGiaoPhieuNV.show();
      });
    },
    async giaoviec_success() {
      let rs_ds = await api.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.axios, {
        phieu_id: this.phieu_id,
        nhanvien_id: this.$root.token.getNhanVienID(),
        kieu_id: 2,
      });
      this.ds = rs_ds.data ? rs_ds.data.data : [];
      this.HienThiDSNV(this.ds);
    },
    rdoPhieuMoi_CheckedChanged(value) {
      if (value) {
        this.HienThiDanhSachHDTB();
      }
    },
    rdoPhieuTra_CheckedChanged(value) {
      if (value) {
        this.HienThiDanhSachHDTB();
      }
    },
    async NEchange(value) {
      console.log(value)
      try {
        this.axios
          .post("/web-ccdv/khaibaocodinh/fn_chon_ne_hc_codinh", {
            phanvung_id: this.$root.token.getPhanVungID(),
            luong_id: this.luong_id,
            tramtb_id: value.tramtb_id,
            ne_id: value.ne_id,
            hdtb_id: this.hdtb_id,
            ma_tb: this.$refs.txtMaTB.value,
            ne: value.sone,
          })
          .then((res) => {
            this.HienThiDanhSachHDTB();
            this.$toast.success("Cập nhập ne thành công");
          });
      } catch (e) {
        this.$toast.error("Cập nhập ne lỗi");
      }
    },
    async btnChonNe_Click() {
      if (!this.tramvt_id && !this.host_id) {
        this.$toast.error(
          "Không có thông tin Tổ thi công và Host để chọn Ne !"
        );
        return;
      }
      this.$refs.frmChonNe.options = this.ds_tramvt;
      this.$refs.frmChonNe.show();
    },
    async btnChonNe_TuDong_Click() {
      try {
        if (!this.tramvt_id && !this.host_id) {
          this.$toast.error(
            "Không có thông tin Tổ thi công và Host để chọn Ne !"
          );
          return;
        }
        let check = await api.phailam(this.axios, {
          luong_id: this.luong_id,
          code: "CAPNHAT_NE",
        });

        let chonne = check.data.data ? check.data.data : 0;
        let data = {
          TRAMVT_ID: this.tramtb_id,
          HOST_ID: this.host_id,
          HDTB_ID: this.hdtb_id,
          MA_TB: this.$refs.txtMaTB.value,
          CAPNHAT_NE: chonne,
        };
        let giaone = await this.axios.post(
          "web-ccdv/khaibaocodinh/fn_chon_ne_tudong_hc_codinh",
          { ds_param: JSON.stringify(data) }
        );
        if (giaone.data.data) {
          let capnhat = JSON.parse(giaone.data.data);
          if (capnhat["MESSAGE"] && capnhat["MESSAGE"].length > 10) {
            this.$toast.error(capnhat["MESSAGE"]);
          } else {
            this.$toast.success("Lưu Ne tự động thành công");
            this.HienThiDanhSachHDTB()
          }
        }
      } catch (e) {
        console.log(e);
        this.$toast.error(e.data ? e.data.message : "Có lỗi xảy ra");
      }
    },
    async btnBanDoTB_Click() {
      var data = {};
      var rs
      this.kieubando = 0
      rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_hdtb",
        { param: this.hdtb_id, type: 4 }
      )
      let kinhdo = rs.data.data
      if (kinhdo) {
        data.lng =
          Number(kinhdo) > 0 ? Number(kinhdo) : null;
      }
      rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_hdtb",
        { param: this.hdtb_id, type: 5 }
      )
      let vido = rs.data.data
      if (vido) {
        data.lat = Number(vido) > 0 ? Number(vido) : null;
      }
      if (Number(data.lat) * Number(data.lng) > 0) {
        this.$refs.frmBanDoTB.map_position = data;
        this.$refs.frmBanDoTB.map_info = {
          dia_chi: "",
          title: this.$refs.txtMaTB.value,
        };
        this.$refs.frmBanDoTB.mode = 1;
        this.$refs.frmBanDoTB.show();
      } else {
        this.$refs.frmBanDoTB.mode = 2;
        this.$refs.frmBanDoTB.map_info = {
          dia_chi: this.$refs.txtDiaChiTB.value,
          title: "Vị trí tương đối theo địa chỉ: " + this.$refs.txtMaTB.value,
        };
        this.$refs.frmBanDoTB.show();
      }
    },
    async btnBanDoLD_Click() {
      var data = {};
      var rs
      this.kieubando = 1
      rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_hdtb",
        { param: this.hdtb_id, type: 1 }
      )
      let kinhdo = rs.data.data
      if (kinhdo) {
        data.lng =
          Number(kinhdo) > 0 ? Number(kinhdo) : null;
      }

      rs = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_hdtb",
        { param: this.hdtb_id, type: 2 }
      )
      let vido = rs.data.data
      if (vido) {
        data.lat = Number(vido) > 0 ? Number(vido) : null;
      }
      console.log(data, "Địa chỉ đây");
      if (Number(data.lat) * Number(data.lng) > 0) {
        this.$refs.frmBanDoTB.map_position = data;
        this.$refs.frmBanDoTB.map_info = {
          dia_chi: "",
          title: this.$refs.txtMaTB.value,
        };
        this.$refs.frmBanDoTB.mode = 1;
        this.$refs.frmBanDoTB.show();
      } else {
        this.$refs.frmBanDoTB.map_position = data;
        this.$refs.frmBanDoTB.map_info = {
          dia_chi: this.$refs.txtDiaChiTB.value,
          title: "Vị trí tương đối theo địa chỉ: " + this.$refs.txtMaTB.value,
        };
        this.$refs.frmBanDoTB.mode = 2;
        this.$refs.frmBanDoTB.show();
      }
    },
    async XacNhanToaDo(value) {
      try {
        if (!this.hdtb_id) {
          return false;
        }
        await api.fn_capnhat_toado_hdtb(this.axios, {
          lat: value.lat,
          lng: value.lng,
          hdtb_id: this.hdtb_id,
          mode: this.kieubando,
        });
      } catch (error) {
        this.$toast.error(error.message ? error.message : "Đã có lỗi xảy ra");
      }
    },
    Clean() {
      this.lblLienHe = "Liên hệ:";
      this.$refs.txtNhanVien.value = "";
      this.$refs.txtNV_TiepThi.value = "";
      this.$refs.txtTongTien.value = "0";
      this.hdtb_id = 0;
      this.hdkh_id = 0;
      this.phieu_id = 0;
      this.madoicap = 0;
      this.kieuld_id = 0;
      this.$refs.txtKHLon.value = "";
      this.$refs.txtMaGD.value = "";
      this.$refs.txtMaTB.value = "";
      this.$refs.txtMaTB_Cu.value = "";
      this.$refs.txtTenTB.value = "";
      this.$refs.txtNoiDungTH.value = "";
      this.$refs.txtDiaChiLD.value = "";
      this.$refs.txtDiaChiLD_Cu.value = "";
      this.$refs.txtNE.value = "";
      this.$refs.txtTBLapKem.value = "";
      this.$refs.txtSoDT.value = "";
      this.$refs.txtKhuvuc.value = "";
      this.$refs.txtTuCapGoc.value = "";
      this.$refs.txtDoiCapGoc.value = "";
      this.$refs.txtCapGoc.value = "";
      this.$refs.txtHopCapNgon.value = "";
      this.$refs.txtTuCapNgon.value = "";
      this.$refs.txtDoiCapNgon.value = "";
      this.$refs.txtCapNgon.value = "";
      this.$refs.txtTuyenCap.value = "";
      this.$refs.txtLienTu.value = "";
      this.$refs.txtSoMetDay.value = "0";
      this.$refs.txtGhiChu.value = "";
      this.$refs.txtThongTinTra.value = "";
      this.$refs.txtLyDoTra.value = "";
      this.$refs.txtSoMayTN.value = "";
      this.$refs.chkNgayBG.checked = false;
      console.log("Không check chkNgayBG")
      this.chkNgayBG_CheckedChanged()
      let ngay_hientai = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      this.$refs.dtpNgayBG.value = ngay_hientai;
      this.$refs.dtpNgayGV.value = ngay_hientai;
      this.$refs.chkNgayGV.checked = false;
      this.$refs.gridGiaoviec.dataSource = null;
      this.$refs.gridDichvuGT.dataSource = null;
      this.$refs.txtKieuLD.value = "";
      this.$refs.txtDiaChiTB.value = "";
      this.$refs.txtNDGiao.value = "";
      this.daikv_id = 0;
      this.host_id = 0;
      this.tramvt_id = 0;
      this.tramtb_id = 0;
      this.ne_id = 0;
      this.ne_id_cu = 0;
      this.$refs.txtTramNE.value = "";
      this.$refs.wbrTSKT.innerHTML = "";
      this.$refs.txtHostLD.value = "";
      this.$refs.txtHostVL.value = "";
      this.$refs.txtHostLD_Cu.value = "";
    },
    initRefs() {
      for (let key in this.$refs) {
        this.$set(this, key, this.$refs[key]);
      }
    },
    async frmHoanCongCoDinh_Load() {
      try {
        if(this._matb_link)
          this.matb_link = this._matb_link
        this.$refs.cboLoaiDVTK.disabled = true;
        this.$refs.cboDViTimKiem.disabled = true;
        let str = "";
        try {
          let Tag = this.isPopup ? this.Tag : this.$route.query.tag;
          console.log("frmHoanCongCoDinh_Load", Tag, this.isPopup)
          if (Tag) {
            str = Tag;
            let words = str.split(" ");
            if (words.length <= 1) {
              words = str.split("+");
            }
            if (words.length >= 1) this.tthd_id = Number(words[0]);
            if (words.length >= 2) {
              this.dsloaihd_id = words[1];
              this.dsloaihd_id = this.dsloaihd_id.replaceAll(";", ",");
            }
            if (words.length >= 3) {
              this.dsdichvuvt_id = words[2];
              this.dsdichvuvt_id = this.dsdichvuvt_id.replaceAll(";", ",");
            }
          } else {
            this.tthd_id = TrangThaiHD.DANG_THI_CONG;
            this.dsloaihd_id = "1";
            this.dsdichvuvt_id = "1";
          }
        } catch (error) {
          console.log(error, "Lỗi tag");
          this.tthd_id = TrangThaiHD.DANG_THI_CONG;
          this.dsloaihd_id = "1";
          this.dsdichvuvt_id = "1";
        }
        console.log(this.dsdichvuvt_id, "this.dsdichvuvt_id")
        //endregion
        this.$refs.wbrTSKT.innerHTML =
          "<html><body style='font: 9.75pt Tahoma; margin: 5px; background-color://E3EFFF'></body></html>";

        //region Lấy các tham số mặc đinh
        let rs_dsThamSo = await api.getThamSoMacDinh(this.axios, {
          ds_ma_ts: '["KHONGHOANCONG_CHUA_THANHTOAN","USING_CABMAN_2.0"]',
        });

        // if (dsThamSo.Tables[0].Rows.Count > 0) {
        //   str_vtemp = "KHONGHOANCONG_CHUA_THANHTOAN";
        //   List < DataRow > det = dsThamSo.Tables[0].AsEnumerable()
        //     .Where(x => x["ma_ts"] == str_vtemp).ToList();
        //   if (det.Count > 0) {
        //                 DataRow row = (DataRow)det[0];
        //     if (row["ten_ts"].Trim() == "1")
        //       khong_hoancong_chua_thanhtoan = 1;
        //   }
        // }
        //endregion
        let dsThamSo = rs_dsThamSo.data.data;
        dsThamSo.forEach((item) => {
          if (item.ma_ts == "KHONGHOANCONG_CHUA_THANHTOAN" && item.ten_ts) {
            this.khong_hoancong_chua_thanhtoan = 1;
          }

          if (item.ma_ts == "USING_CABMAN_2.0") {
            this._IS_USING_CABMAN_V2 = 1;
          } else {
            this._IS_USING_CABMAN_V2 = 0;
          }
        });

        //region Lấy dữ liệu danh mục

        let rs_dsDanhMuc = await api.getComboBox(this.axios, {
          chuoi:
            "KENHTHU|KIEU_HD|LOAI_HD|LOAI_CAP|NHANVIEN-donvi_id = " +
            this.$root.token.getDonViID() +
            "|QUYTRINH",
        });

        this.dsDanhMuc = rs_dsDanhMuc.data.data;
        this.$refs.cboKenhThu.HT_COMBOBOX(
          this.dsDanhMuc["kenhthu"],
          "kenhthu",
          "kenhthu_id"
        );
        this.$refs.cboKieu_HD.HT_COMBOBOX(
          this.dsDanhMuc["kieu_hd"],
          "kieu_hd",
          "kieuhd_id"
        );
        this.$refs.cboLoaiHD.HT_COMBOBOX(
          this.dsDanhMuc["loai_hd"],
          "ten_loaihd",
          "loaihd_id"
        );
        this.$refs.cboLoaiCap.HT_COMBOBOX(
          this.dsDanhMuc["loai_cap"],
          "loaicap",
          "loaicap_id"
        );
        this.$refs.cboNguoiGV.HT_COMBOBOX(
          this.dsDanhMuc["nhanvien"],
          "ten_nv",
          "nhanvien_id"
        );

        // this.cboLoaiHD_SelectedValueChanged(this.$refs.cboLoaiHD.value);
        this.$refs.cboNguoiGV.value = this.$root.token.getNhanVienID();
        let ngay_hientai = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
        this.$refs.dtpNgayBG.value = ngay_hientai;
        this.$refs.dtpNgayGV.value = ngay_hientai;
        // dtpNgayGV.Value = ngay_hientai;
        this.$refs.dtpNgayGV.disabled = true;
        let rs_ds_tramvt = await api.getDsVetinh(this.axios, {
          donvi_id: 0,
          loaidv_ql: 5,
          loaidv_id: 25,
        }); // Sửa lại. lấy theo Trạm thi công chứ ko lấy theo Host nữa
        this.ds_tramvt =
          rs_ds_tramvt.data && rs_ds_tramvt.data.data
            ? rs_ds_tramvt.data.data
            : [];

        // this.HienThiDanhSachHDTB();

        if (this.tthd_id == TrangThaiHD.DA_THI_CONG_XONG) {
          this.$refs.tsbtnHoanThienHS.style.display = null;
        }

        this.kt_load = true;
        //endregion
      } catch (err) {
        console.log(err);
        this.$toast.error("Đã có lỗi xảy ra");
      }
    },
    async HienThiDanhSachHDTB() {
      this.loading(true);
      try {
        return new Promise(async (resolve, reject) => {
          let vloai_id = 0; // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
          if (this.$refs.rdoPhieuMoi.checked) vloai_id = 0;
          else vloai_id = 1;
          let rs_dsIn = await api.LAY_DS_PHIEU_HOANCONG_119(this.axios, {
            dichvuvt_id: DichVuVienThong.CO_DINH,
            tthd_id: this.tthd_id,
            nhanvien_id: this.$root.token.getNhanVienID(),
            ma_gd: "",
            phieu_id: 0,
            loaihd_id: this.$refs.cboLoaiHD.value,
            caidat_dv: 0,
            kieu_id: 0,
            huonggiao_id: this.huonggiao_id,
            loai_id: vloai_id,
            loaidv_id: 0,
            donvi_tk_id: 0,
          });
          this.dsIn = rs_dsIn.data ? rs_dsIn.data.data : [];
          let dtList = this.dsIn;
          dtList = dtList.map((item) => ({
            ...item,
            thoigian_ck: this.formatDate(item.thoigian_ck),
          }));

          if(this.matb_link) {
            let filter = dtList.filter(item => item.ma_tb == this.matb_link)
            if(filter.length > 0)
              this.$refs.gvDanhSach.dataSource = filter
            this.matb_link = ""
          } else {
            this.$refs.gvDanhSach.dataSource = dtList;
          }
          if (dtList.length > 0) {
            setTimeout(() => {
              this.$refs.gvDanhSach.$refs.DataGridCustom.setCurrentSelectedRow(
                this.old_index
              );
              this.$refs.gvDanhSach.$refs.DataGridCustom.flagSelectedRowIndexes =
                [];
              this.$refs.gvDanhSach.$refs.DataGridCustom.flagSelectedRowIndexes.push(
                this.old_index
              );
            }, 500);
          } else {
            this.$refs.tsbtnTraPhieu.classList.add("non-ative");
            this.$refs.tsbtnChapNhan.classList.add("non-ative");
            this.$refs.tsbtnHoanCong.classList.add("non-ative");
            this.$refs.tsbtnHoanThienHS.classList.add("non-ative");
            // this.$toast.error("Không có dữ liệu, vui lòng thử lại");
            this.Clean();
          }
          this.loading(false);
          resolve(true);
        });
      } catch (err) {
        this.loading(false);
        this.$toast.error("Có lỗi xảy ra");
        console.log(err);
      }
      this.$refs.gvDanhSach.setCurrentSelectedRow(this.old_index);
      this.$refs.gvDanhSach.$refs.DataGridCustom.flagSelectedRowIndexes = [];
      this.$refs.gvDanhSach.$refs.DataGridCustom.flagSelectedRowIndexes.push(this.old_index);
    },
    async HienThiGiaoDien(luong_id) {
      this.$refs.tsbtnKQ_DoKiem.style.display = "none";
      this.$refs.tsbtnDuAn.style.display = "none";
      this.$refs.tsbtnHoanCong.style.display = "none";
      this.$refs.tsbtnGiaoPhieu.style.display = "none";
      this.$refs.btnPhieuTC.style.display = "none";
      this.$refs.tsbtnTraPhieu.style.display = "none";
      this.$refs.tsbtnHoanThanh.style.display = "none";
      this.$refs.tsbtnVatTu.style.display = "none";
      // this.$refs.tsbtnVatTuMoi.style.display = "none";
      // this.$refs.tsbtnGiaoPhieu.style.display = "none";
      this.$refs.btnChonNe.disabled = true;
      this.$refs.btnChonNe_TuDong.disabled = true;
      this.$refs.tsbtnHoanThienHS.style.display = "none";
      console.log(this.$refs.tsbtnHoanThienHS);
      this.$refs.txtTuCapGoc.disabled = true;
      this.$refs.txtDoiCapGoc.disabled = true;
      this.$refs.txtCapGoc.disabled = true;
      this.$refs.txtHopCapNgon.disabled = true;
      this.$refs.txtTuCapNgon.disabled = true;
      this.$refs.txtDoiCapNgon.disabled = true;
      this.$refs.txtCapNgon.disabled = true;
      this.$refs.txtTuyenCap.disabled = true;
      this.$refs.txtLienTu.disabled = true;
      this.$refs.txtSoMetDay.disabled = true;
      this.$refs.chkLayTSo.disabled = true;
      this.$refs.tsbtnKichHoat.style.display = "none";
      this.$refs.tsbtnKichHoat.classList.add("non-ative");
      this.$refs.tsbtnGiaoViec_KBB.style.display = "none";
      this.$refs.tsbtnSua_KenhThu.style.display = "none";
      //dtgDanhSach.Columns["sochinh"].style.display = 'none'
      try {
        this.dtThaoTac = this.dsDanhMuc["quytrinh"].filter(
          (DSTONG) =>
            DSTONG["loaihd_id"] == this.loaihd_id &&
            DSTONG["luong_id"] == this.luong_id &&
            DSTONG["tthd_id"] == this.tthd_id
        );
        if (this.dtThaoTac.length > 0) {
          for (let i = 0; i < this.dtThaoTac.length; i++) {
            this.dtControl = this.dsDanhMuc["quytrinh"].filter(
              (DSTONG) =>
                DSTONG["loaihd_id"] == this.loaihd_id &&
                DSTONG["luong_id"] == this.luong_id &&
                DSTONG["tthd_id"] == this.tthd_id &&
                DSTONG["thaotac_id"] == this.dtThaoTac[i].thaotac_id
            );

            if (this.dtControl.length > 0) {
              for (let j = 0; j < this.dtControl.length; j++) {
                let control_name = this.dtControl[j]["control_name"];
                let Control = this.$refs[control_name];
                if (!Control) {
                  continue;
                }
                let type = Control.nodeName;
                switch (type) {
                  case "BUTTON":
                    if (this.dtThaoTac[i].enable == "1")
                      Control.disabled = false;
                    else Control.disabled = true;
                    break;
                  case "INPUT":
                    if (this.dtThaoTac[i].enable == "1") {
                      Control.disabled = false;
                      Control.style.background = "#fff8dc";
                      Control.style.color = "#800000";
                    } else Control.disabled = true;
                    break;
                  case "A":
                    if (this.dtThaoTac[i].enable == "1") {
                      console.log(control_name)
                      console.log(Control)
                      Control.style.display = null;
                    } else Control.style.display = "none";
                    break;
                  default:
                    break;
                }
              }
            }
          }
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi");
      }
    },
    async cboQuyTrinh_SelectedValueChanged(value) {
      if (this.$refs.cboQuyTrinh.dataSource.length > 0) {
        if (!value) return;
        this.quytrinh_id = Number(value);
        let dt_luong = this.dsDanhMuc["quytrinh"].filter(
          (DSTONG) =>
            DSTONG["quytrinh_id"] == this.quytrinh_id &&
            DSTONG["tthd_id"] == this.tthd_id
        );
        console.log(this.quytrinh_id, "quy_trinh_id");
        console.log(this.tthd_id, "tthd_id");
        console.log(dt_luong, "dt_luong lấy từ danh sách quy trình");
        if (dt_luong.length > 0) {
          this.luong_id = Number(dt_luong[0]["luong_id"]);
          this.huonggiao_id = Number(dt_luong[0]["huonggiao_id"]);
          let tenhg = dt_luong[0]["huonggiao"];
          this.labelFunctionTitle = tenhg.toUpperCase();
        } else {
          this.$toast.error(
            "Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại"
          );
          await this.HienThiDanhSachHDTB();
          return;
        }
        this.HienThiGiaoDien(this.luong_id);
        if (!(await this.PHAILAM("GIAOVIEC"))) {
          this.$refs.chkNgayBG.value = true;
          this.$refs.chkNgayGV.value = true;
          let ngayhientai = new Date();
          let ngayht = ngayhientai.setSeconds(ngayhientai.getSeconds() + 30);
          this.$refs.dtpNgayBG.value = moment(ngayht).format(
            "DD/MM/YYYY HH:mm:ss"
          );
          this.$refs.dtpNgayGV.value = moment(ngayhientai).format(
            "DD/MM/YYYY HH:mm:ss"
          );
        }

        await this.HienThiDanhSachHDTB(this.luong_id);
      }
    },
    chkNgayBG_CheckedChanged() {
      this.$refs.dtpNgayBG.disabled = !this.$refs.chkNgayBG.checked;
    },
    chkNgayGV_CheckedChanged() {
      this.$refs.dtpNgayGV.disabled = !this.$refs.chkNgayGV.checked;
    },
    PHAILAM(code) {
      return new Promise(async (resolve, reject) => {
        try {
          let rs = await this.axios.post(
            "/web-hopdong/thaydoi_thongso_megawan/phailam",
            {
              luong_id: this.luong_id,
              code: code,
            }
          );
          if (rs.data && rs.data.data == 1) {
            resolve(true);
          } else {
            resolve(false);
          }
        } catch (err) {
          console.log(err);
          reject(false);
        }
      });
    },
    async cboLoaiHD_SelectedValueChanged(value) {
      try {
        if (this.$refs.cboLoaiHD.dataSource.length > 0) {
          if (!value) return;
          this.loaihd_id = value;
          this.$refs.tsbtnHuyKH.style.display = "none";
          if (
            this.loaihd_id == LoaiHopDong.DAT_MOI ||
            this.loaihd_id == LoaiHopDong.DI_CHUYEN
          ) {
            this.$refs.tsbtnHuyKH.style.display = null;
            this.$refs.tsbtnHuyKH.classList.add("non-ative");
          }

          let ds = await api.Lay_DS_QuyTrinh(this.axios, {
            dsloaihd_id: this.loaihd_id,
            tthd_id: this.tthd_id,
            kieu: 0,
            dichvuvt_id: this.dsdichvuvt_id,
          });

          if (ds.data && ds.data.data.length > 0) {
            this.$refs.cboQuyTrinh.dataSource = ds.data.data.sort((a, b) => {
              return Number(a.quytrinh_id) - Number(b.quytrinh_id);
            });
            this.$refs.cboQuyTrinh.dataValueField = "quytrinh_id";
            this.$refs.cboQuyTrinh.dataTextField = "quytrinh";

            if (this._vquytrinh_id && this._vquytrinh_id != 0) {
              this.$refs.cboQuyTrinh.value = this._vquytrinh_id;
            } else {
              this.$refs.cboQuyTrinh.value = this.$refs.cboQuyTrinh
                .dataSource[0]
                ? this.$refs.cboQuyTrinh.dataSource[0].quytrinh_id
                : null;
            }
            this.cboQuyTrinh_SelectedValueChanged(this.$refs.cboQuyTrinh.value);
          } else {
            this.quytrinh_id = -1;
            //this.HienThiDanhSachHDTB();
            this.$refs.cboQuyTrinh.dataSource = [];
            this.$refs.cboQuyTrinh.placeholder = "--Không có quy trình--";
          }

          if (
            Number(this.$refs.cboLoaiHD.value) == LoaiHopDong.DAT_MOI ||
            Number(this.$refs.cboLoaiHD.value) == LoaiHopDong.DI_CHUYEN ||
            Number(this.$refs.cboLoaiHD.value) == LoaiHopDong.KHOIPHUC_SD
          ) {
            this.$refs.chkLayTSo.disabled = false;
          } else {
            this.$refs.chkLayTSo.disabled = true;
            this.$refs.chkLayTSo.value = false;
          }
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi xảy ra");
      }
    },
    txtSoMetDay_Leave() {
      if (this.$refs.txtSoMetDay.value) {
        let s = this.$refs.txtSoMetDay.value;
        if (!this.isNumber(s)) {
          this.$toast.error("Số mét dây phải là kiểu số");
          this.$refs.txtSoMetDay.value = "0";
          this.$refs.txtSoMetDay.focus();
          return;
        }
      }
    },
    isNumber(n) {
      return !isNaN(parseFloat(n)) && !isNaN(n - 0);
    },
    async gvDanhSach_FocusedRowChanged(data) {
      let selected = this.$refs.gvDanhSach.getSelectedRecords();
      let item = selected[0];
      this.old_index = data.rowIndex;
      try {
        this.$refs.tsbtnChapNhan.classList.add("non-ative");
        this.$refs.tsbtnTraPhieu.classList.add("non-ative");
        this.Clean();
        if (selected.length > 0) {
          let dr = selected[0];
          this.HienThiTTHopDongTB(item);
          this.phieu_id = dr["phieu_id"];
          //Hiển thị danh sách nhân viên
          let rs_ds = await api.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.axios, {
            phieu_id: this.phieu_id,
            nhanvien_id: this.$root.token.getNhanVienID(),
            kieu_id: 2,
          });
          this.ds = rs_ds.data ? rs_ds.data.data : [];
          this.HienThiDSNV(this.ds);
          this.$refs.chkLayTSo.checked = false;
        }
      } catch (err) {
        console.log(err);
        this.$toast.error("Có lỗi xảy ra");
      }
    },
    async HienThiTTHopDongTB(dr) {
      console.log(dr, "Load lại data");
      if (dr && dr.ma_tb) {
        try {
          this.loading(true);
          let check_omc = 0;
          let hienthi_host = 0;
          if (await this.PHAILAM("CHECK_OMC")) {
            check_omc = 1;
          }
          if (await this.PHAILAM("HIENTHI_HOST")) {
            hienthi_host = 1;
          }

          let thongtin_rs = await this.axios.post(
            "web-ccdv/khaibaocodinh/fn_lay_tt_hd_hoancong_codinh",
            {
              ds_para: JSON.stringify({
                HDKH_ID: dr.hdkh_id,
                NHANVIEN_ID: dr.nhanvien_id,
                LYDOTRA_CON_ID: dr.lydotra_con_id,
                NE_ID: dr.ne,
                HDTB_ID: dr.hdtb_id,
                PHANLOAIKH_ID: dr.phanloaikh_id,
                MADOICAP: dr.madoicap,
                HIENTHI_HOST: hienthi_host,
                LOAITB_ID: dr.loaitb_id,
                CHECK_OMC: check_omc,
              }),
            }
          );

          let thongtin_parse =
            thongtin_rs.data && thongtin_rs.data.data
              ? JSON.parse(thongtin_rs.data.data)
              : {};
          let thongtin = thongtin_parse.RESULT ? thongtin_parse.RESULT : {};

          this.$refs.tsbtnChapNhan.classList.remove("non-ative");
          this.$refs.tsbtnTraPhieu.classList.remove("non-ative");

          this.ne_id = 0;
          this.ne_id_cu = 0;

          this.hdtb_cha_id = 0;
          this.thuebao_cha_id = 0;

          this.$refs.txtTramNE.value = "";
          this.hdtb_id = dr["hdtb_id"];
          if (dr["thuebao_id"]) this.thuebao_id = dr["thuebao_id"];
          else this.thuebao_id = 0;

          this.hdkh_id = dr["hdkh_id"];

          this.$refs.txtTongTien.value = thongtin.TONGTIEN
            ? this.numberWithCommas(thongtin.TONGTIEN)
            : "0";
          this.madoicap = dr["madoicap"];
          this.$refs.txtNhanVien.value = dr["ten_nv"];
          let rs_ctv = await api.LAY_THONGTIN_NHANVIEN_DV(this.axios, {
            nhanvien_id: dr["ctv_id"],
          });
          this.$refs.txtNV_TiepThi.value =
            rs_ctv.data && rs_ctv.data.data && rs_ctv.data.data[0]
              ? rs_ctv.data.data[0].nhanvien
              : "";
          let kenhthu_rs = await this.axios.post(
            "web-thicong/hoancongmegawan/sp_lay_kenhthu_theo_hdkh",
            {
              hdkh_id: this.hdkh_id,
            }
          );

          let kenhthu =
            kenhthu_rs.data && kenhthu_rs.data.data ? kenhthu_rs.data.data : [];

          this.$refs.cboKenhThu.value =
            kenhthu.length > 0 ? kenhthu[0].kenhthu_id : null;

          if (dr["kieuhd_id"]) this.$refs.cboKieu_HD.value = dr["kieuhd_id"];
          else this.$refs.cboKieu_HD.value = KIEU_HD.TAI_GD;
          //Lấy thông tin liên hệ lắp đặt thuê bao
          if (dr["hdkh_id"]) {
            let rs_lh = await api.Lay_thongtin_lh_lapdat(this.axios, {
              hdkh_id: dr["hdkh_id"],
            });
            this.lblLienHe = "Liên hệ:" + (rs_lh.data ? rs_lh.data.data : "");
          } else {
            this.lblLienHe = "Liên hệ:";
          }

          this.phieu_id = dr["phieu_id"];

          if (dr["giohen_tu"]) this.$refs.chkHen.checked = true;
          else this.$refs.chkHen.checked = false;

          this.$refs.txtThongTinTra.value = dr["nd_tra_con"];

          this.$refs.txtLyDoTra.value = thongtin.LYDOTRA
            ? thongtin.LYDOTRA
            : "";

          this.loaihd_id = dr["loaihd_id"];
          this.loaitb_id = dr["loaitb_id"];
          this.$refs.txtMaGD.value = dr["ma_gd"];
          this.$refs.txtMaTB.value = dr["ma_tb"];
          this.$refs.txtTenTB.value = dr["ten_tb"];
          //nhapt them thông tin khu vực 24/03/2017
          this.$refs.txtKhuvuc.value = dr["ten_kv"];
          this.$refs.txtDiaChiLD.value = dr["diachi_ld"];
          if (this.loaihd_id != LoaiHopDong.DAT_MOI) {
            this.$refs.txtDiaChiLD_Cu.value = dr["diachild_cu"] ? dr["diachild_cu"] : "";
            this.$refs.txtMaTB_Cu.value = dr["ma_tb_cu"];
          } else {
            this.$refs.txtDiaChiLD_Cu.value = "";
            this.$refs.txtMaTB_Cu.value = "";
          }

          this.$refs.txtSoDT.value = dr["so_dt"];
          this.$refs.txtDiaChiTB.value = dr["diachi_tb"];
          this.$refs.txtGhiChu.value = dr["ghichu"];
          this.$refs.txtKieuLD.value = dr["ten_kieuld"];
          this.kieuld_id = dr["kieuld_id"];
          this.$refs.txtThoiGianThueSo.value = dr["tg_thueso"];
          this.$refs.txtSoTruot.value = dr["sotruot"];
          this.$refs.txtNDThongBao.value = dr["noidung_tb"];

          //gvDanhSach.Columns["MA_TB_CHA"].Visible = true;
          //dtgDanhSach.Columns["ma_tb_cha"].Visible = true;
          // end hoang sửa
          if (dr["thuebao_cha_id"]) {
            this.sochinh = false;
            if (dr["hdtb_cha_id"]) this.hdtb_cha_id = Number(dr["hdtb_cha_id"]);
            else this.thuebao_cha_id = Number(dr["thuebao_cha_id"]);
          } else this.sochinh = true;

          //DataTable dt = tchopdong.LAY_DS_HDTB_CD_THEO_HDTB_ID(hdtb_id).Tables[0];
          //if (dt.Rows.Count > 0)
          //{
          this.$refs.txtNE.value = dr["ne"];
          if (dr["ne_id"]) {
            this.ne_id = Number(dr["ne_id"]);
            this.ne_id_cu = Number(dr["ne_id"]);
            this.$refs.txtTramNE.value = dr["ne"];
          }
          this.tramvt_id = 0;
          let rs = await this.axios.post(
            "/web-quantri/lay-dulieu/sp_tt_hdtb_dv",
            {
              "param": this.hdtb_id,
              "type": 2
            }
          );
          let dtTemp = rs.data.data;
          this.tramvt_id = Number(dtTemp[0] ? dtTemp[0]["donvi_id"] : 0);

          rs = await this.axios.post(
            "/web-quantri/lay-dulieu/sp_tt_hdtb_dv",
            {
              "param": this.hdtb_id,
              "type": 3
            }
          );
          dtTemp = rs.data.data;
          this.host_id = Number(dtTemp.length > 0 ? dtTemp[0]["donvi_id"] : 0);

          this.tramtb_id = dr["tramtb_id"];
          console.log(dr.phanloaikh_id, "phanloaikh_id")
          let rs_pl = await this.axios.post("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_phanloai_kh",
            { param: dr.phanloaikh_id , type: 1 }
          )
          // let rs_pl = await api.MAP_ID(this.axios, {
          //   id_neo: "ma_plkh",
          //   in_table: "phanloai_kh",
          //   in_dk: "where phanloaikh_id=" + dr["phanloaikh_id"],
          // });
          this.$refs.txtKHLon.value = rs_pl.data ? rs_pl.data.data : "";

          // dữ liệu mới
          this.$refs.txtTuyenCap.value = dr["tuyencap"];
          this.$refs.txtLienTu.value = dr["lientu"];
          this.$refs.txtTuCapNgon.value = dr["tucap_p"];
          if (dr["doicap_p"] != "-1")
            this.$refs.txtDoiCapNgon.value = dr["doicap_p"];
          else this.$refs.txtDoiCapNgon.value = "";

          this.$refs.txtHopCapNgon.value = dr["hopcap_p"];
          if (dr["loaicap_id"] != "")
            this.$refs.cboLoaiCap.value = Number(dr["loaicap_id"]);
          this.$refs.txtCapGoc.value = dr["cap_goc"];
          this.$refs.txtCapNgon.value = dr["cap_p"];
          if (dr["doicap_goc"] != "-1")
            this.$refs.txtDoiCapGoc.value = dr["doicap_goc"];
          else this.$refs.txtDoiCapGoc.value = "";
          this.$refs.txtTuCapGoc.value = dr["tucap_goc"];
          // end du lieu moi

          this.$refs.txtSoMetDay.value = dr["culy"];
          if (this.$refs.txtSoMetDay.value == "")
            this.$refs.txtSoMetDay.value = "0";
          this.$refs.txtSoMayTN.value = dr["matb_tn"];
          this.$refs.txtNDGiao.value = dr["nd_giao"];
          this.$refs.txtNoiDungTH.value = dr["nd_thuchien"];
          this.status = dr["status"];

          this.$refs.tsbtnHuyKH.style.display = "none";
          if (await this.PHAILAM("TSBTNHUYKH")) {
            console.log("Phải hủy kích hoạt");
            this.$refs.tsbtnHuyKH.style.display = null;
          }

          //nhapt thêm : 17/03/2017
          /// Thực hiện ẩn/hiện nút thay đổi thông tin liên hệ
          /// trên chức năng của trạm; để trạm cập nhật lại thông tin liên hệ sai
          this.$refs.tsbtnThongTinLH.style.display = "none";
          if (await this.PHAILAM("TSBTNTHONGTINLH")) {
            console.log("phải làm this.TSBTNTHONGTINLH");
            this.$refs.tsbtnThongTinLH.style.display = null;
          }
          if (dr["status"]) {
            if (Number(dr["status"]) == TRANGTHAI_DONGBO.DONGBO_SERVICE)
              this.$refs.tsbtnHuyKH.classList.remove("non-ative");
            else this.$refs.tsbtnHuyKH.classList.add("non-ative");
          }

          let rs_lapkem = await this.axios.post(
            "web-ccdv/khaibaoims/lay_thuebao_lapkem",
            {
              hdtb_id: this.hdtb_id,
              madoicap: dr.madoicap,
            }
          );
          this.$refs.txtTBLapKem.value =
            rs_lapkem.data && rs_lapkem.data.data ? rs_lapkem.data.data : "";

          this.$refs.txtHostLD.value = "";
          this.$refs.txtHostVL.value = "";
          this.$refs.txtHostLD_Cu.value = "";
          if (await this.PHAILAM("HIENTHI_HOST")) {
            let rs_host = await this.axios.get(
              "web-ccdv/hoanthien_hoso_doiso_account/lay_thongtin_host",
              {
                params: {
                  hdtbId: this.hdtb_id,
                  kieu: 0,
                },
              }
            );
            let dt = rs_host.data && rs_host.data.data ? rs_host.data.data : [];
            if (dt.length > 0) {
              this.$refs.txtHostLD.value =
                thongtin.THONGTIN_HOST && thongtin.THONGTIN_HOST.length > 0
                  ? thongtin.THONGTIN_HOST[0].HOST_LD
                  : "";
              this.$refs.txtHostVL.value =
                thongtin.THONGTIN_HOST && thongtin.THONGTIN_HOST.length > 0
                  ? thongtin.THONGTIN_HOST[0].HOST_VL
                  : "";
              this.$refs.txtHostLD_Cu.value =
                thongtin.THONGTIN_HOST && thongtin.THONGTIN_HOST.length > 0
                  ? thongtin.THONGTIN_HOST[0].HOST_LD_CU
                  : "";
            }

            this.$refs.cboTramVeTinh.dataSource = [];

            this.$refs.cboTramVeTinh.dataValueField = "donvi_id";
            this.$refs.cboTramVeTinh.dataTextField = "ten_dv";
            if (this.tramvt_id != 0) {
              //var t = ds_tramvt.Tables[0].Select("DONVI_QL_ID=" + dt.Rows[0]["hostld_id"]);
              var t = this.ds_tramvt.filter(
                (item) => (item.donvi_ql_id == this.tramvt_id)
              );
              this.$refs.cboTramVeTinh.dataSource = t.length > 0 ? t : [];
            } else this.$refs.cboTramVeTinh.dataSource = this.ds_tramvt;

            this.$refs.cboTramVeTinh.dataValueField = "donvi_id";
            this.$refs.cboTramVeTinh.dataTextField = "ten_dv";

            if (this.tramtb_id != 0)
              this.$refs.cboTramVeTinh.value = this.tramtb_id;
            else {
              this.$refs.cboTramVeTinh.value = 0;
            }
          } else
            this.$refs.cboTramVeTinh.placeholder = this.$refs.txtTramNE.value;

          console.log("Check cập nhật NE", this.sochinh);
          if (await this.PHAILAM("CAPNHAT_NE")) {
            console.log("Phải cập nhập NE", this.sochinh, this.loaitb_id);
            if (
              this.loaitb_id == LoaiHinhTB.PABX || // minhnt thêm pabx cho agg, bpc
              this.loaitb_id == LoaiHinhTB.ISDN2B_CQ ||
              this.loaitb_id == LoaiHinhTB.ISDN2B_CD ||
              this.loaitb_id == LoaiHinhTB.ISDN30B_CD ||
              this.loaitb_id == LoaiHinhTB.ISDN30B_CQ ||
              this.loaitb_id == LoaiHinhTB.TRUNGKE_2M ||
              this.loaitb_id == LoaiHinhTB.TRUNGKE_THUONG ||
              this.loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU
            ) {
              console.log("Vào đây");
              if (this.sochinh) {
                this.$refs.btnChonNe.disabled = false;
                this.$refs.btnChonNe_TuDong.disabled = false;
              } else {
                if (await this.PHAILAM("CHON_NE_SO_PHU")) {
                  this.$refs.btnChonNe.disabled = false;
                  this.$refs.btnChonNe_TuDong.disabled = false;
                } else {
                  this.$refs.btnChonNe.disabled = true;
                  this.$refs.btnChonNe_TuDong.disabled = true;
                }
              }
              //KNV||04/08/2016 ẩn nút chọn NE nếu đã kích hoạt tổng đài(tránh trường hợp kích hoạt xong lại chọn NE khác)
              this.$refs.btnChonNe.disabled =
                this.status == TRANGTHAI_DONGBO.DONGBO_SERVICE;
              this.$refs.btnChonNe_TuDong.disabled =
                this.status == TRANGTHAI_DONGBO.DONGBO_SERVICE;
            } else {
              this.$refs.btnChonNe.disabled = !this.sochinh;
              this.$refs.btnChonNe_TuDong.disabled = !this.sochinh;
            }
          }

          this.$refs.dtpNgayBG.readOnly = false;
          this.$refs.chkNgayBG.checked = true;
          this.chkNgayBG_CheckedChanged()

          let ngayhientai = new Date();
          let ngayht = ngayhientai.setSeconds(ngayhientai.getSeconds() + 30);
          if (dr["ngay_ht_gp"])
            this.$refs.dtpNgayBG.value = !dr["ngay_ht_gp"].includes("/")
              ? moment(dr["ngay_ht_gp"]).format("DD/MM/YYYY HH:mm:ss")
              : dr["ngay_ht_gp"];
          else
            this.$refs.dtpNgayBG.value = moment(ngayht).format(
              "DD/MM/YYYY HH:mm:ss"
            );

          //Nếu phải kích hoạt tổng đài
          if (await this.PHAILAM("KICHHOAT_TD")) {
            if (dr["ngay_ht_gp"]) {
              if (this.status == TRANGTHAI_DONGBO.DONGBO_SERVICE) {
                this.$refs.tsbtnGiaoPhieu.classList.remove("non-ative");
                this.$refs.tsbtnKichHoat.classList.add("non-ative");
              } else {
                this.$refs.tsbtnGiaoPhieu.classList.add("non-ative");
                this.$refs.tsbtnKichHoat.classList.remove("non-ative");
              }
            } else {
              this.$refs.tsbtnGiaoPhieu.classList.add("non-ative");
              this.$refs.tsbtnKichHoat.classList.add("non-ative");
            }
          }

          //Nếu phải giao phiếu
          if (await this.PHAILAM("GIAOPHIEU")) {
            console.log("Phải giao phiếu");
            if (dr["ngay_ht_gp"]) {
              if (await this.PHAILAM("KICHHOAT_TD")) {
                if (this.status == TRANGTHAI_DONGBO.DONGBO_SERVICE) {
                  this.$refs.tsbtnGiaoPhieu.classList.remove("non-ative");
                  this.$refs.tsbtnKichHoat.classList.add("non-ative");
                } else {
                  this.$refs.tsbtnGiaoPhieu.classList.add("non-ative");
                  this.$refs.tsbtnKichHoat.classList.remove("non-ative");
                }
              } else this.$refs.tsbtnGiaoPhieu.classList.remove("non-ative");
            } else {
              this.$refs.tsbtnGiaoPhieu.classList.add("non-ative");
              this.$refs.tsbtnKichHoat.classList.add("non-ative");
            }
          }

          //Nếu phải kích hoạt tổng đài
          if (await this.PHAILAM("HOANCONG")) {
            console.log("Phải hoàn công");
            if (
              dr["ngay_ht_gp"] &&
              this.status == TRANGTHAI_DONGBO.DONGBO_SERVICE
            )
              this.$refs.tsbtnHoanCong.classList.remove("non-ative");
            else this.$refs.tsbtnHoanCong.classList.add("non-ative");
          }
          if (await this.PHAILAM("HOANTHIENHOSO")) {
            if (dr["ngay_ht_gp"] != "")
              this.$refs.tsbtnHoanThienHS.classList.remove("non-ative");
            else this.$refs.tsbtnHoanThienHS.classList.add("non-ative");
          }

          if (!(await this.PHAILAM("GIAOVIEC"))) {
            this.$refs.chkNgayBG.checked = true;
            this.$refs.dtpNgayBG.disabled = true;
            this.$refs.chkNgayBG.disabled = true;

            this.$refs.chkNgayGV.checked = true;
            this.$refs.dtpNgayGV.disabled = true;
            this.$refs.chkNgayGV.disabled = true;
            this.chkNgayGV_CheckedChanged()
            this.chkNgayBG_CheckedChanged()
          }
          console.log(await this.PHAILAM("CAPNHAT_CAP"), "PHAILAM CAPNHAT_CAP");
          console.log(dr["chan"], "chan");
          if ((await this.PHAILAM("CAPNHAT_CAP")) && dr["chan"] == "1")
            this.$refs.tsddbIn.classList.add("non-ative");
          else this.$refs.tsddbIn.classList.remove("non-ative");

          if (dr["caidat_dv"]) {
            this.$refs.checkTTCaiDatDVDK.checked = true;
          } else this.$refs.checkTTCaiDatDVDK.checked = false;

          if (await this.PHAILAM("CHECK_OMC")) {
            // Nếu có thao tác kiểm tra OMC thì -> làm
            console.log("Phải check OMC");
            try {
              if (!thongtin.KT_OMC) {
                this.$toast.error(
                  "Không lấy được thông tin kiểm tra giao phiếu cho bộ phận tiếp theo"
                );
                this.$refs.tsbtnHoanCong.style.display = "none";
                this.$refs.tsbtnKichHoat.style.display = "none";
                this.$refs.tsbtnHuyKH.style.display = "none";
                this.loading(false)
                return;
              } else if (thongtin.KT_OMC == "2") {
                // bo qua
                if (dr["ngay_ht_gp"] != "")
                  this.$refs.tsbtnHoanCong.classList.remove("non-ative");
                this.$refs.tsbtnHoanCong.style.display = null;
                this.$refs.tsbtnKichHoat.style.display = null;
                this.$refs.tsbtnHuyKH.style.display = null;
              } else if (thongtin.KT_OMC == "1") {
                // giao OMC thi hien nut hoan cong
                //Giaophieu
                if (dr["ngay_ht_gp"] != "")
                  this.$refs.tsbtnHoanCong.classList.remove("non-ative");
                this.$refs.tsbtnHoanCong.style.display = null;
                this.$refs.tsbtnKichHoat.style.display = "none";
                this.$refs.tsbtnHuyKH.style.display = "none";
              } else {
                this.$refs.tsbtnHoanCong.style.display = "none";
                this.$refs.tsbtnKichHoat.style.display = "none";
                this.$refs.tsbtnHuyKH.style.display = "none";
                this.loading(false)
                return;
              }
            } catch (err) {
              console.log(err);
              this.$toast.error("Có lỗi khi gọi hàm kiểm thông tin thuê bao ");
              this.$refs.tsbtnHoanCong.style.display = "none";
              this.$refs.tsbtnKichHoat.style.display = "none";
              this.$refs.tsbtnHuyKH.style.display = "none";
            }
          }
          let rs_DVGT = await api.LAY_DS_DANGKY_DVGT_V2(this.axios, {
            hdtb_id: this.hdtb_id,
          });
          this.HienThiDVGT(rs_DVGT.data ? rs_DVGT.data.data : []);

          await this.HIEN_THONGSO_KYTHUAT();

          this.loading(false);
        } catch (e) {
          console.log(e);
          this.loading(false);
          console.log("Khởi tạo lỗi " + e.message);
        }
      }
    },
    HienThiDVGT(ds) {
      if (ds.length > 0) {
        //dgvdangkyDV.AutoGenerateColumns = false;
        this.$refs.gridDichvuGT.dataSource = ds;
      } else this.$refs.gridDichvuGT.dataSource = [];
    },
    async HIEN_THONGSO_KYTHUAT() {
      return new Promise(async (resolve, reject) => {
        if (!this._IS_USING_CABMAN_V2) {
          if (this.$refs.palThongTinCapCu.style.display == "none") {
            this.$refs.palThongTinCapCu.style.display = null;
            this.$refs.palThongSoKyThuat.style.display = "none";
          }
          reject(false)
          return;
        }
        if (this.$refs.palThongSoKyThuat.style.display == "none") {
          this.$refs.palThongTinCapCu.style.display = "none";
          this.$refs.palThongSoKyThuat.style.display = null;
        }

        try {
          let rs_ds = await api.lay_ds_thongtin_kythuat_hdtb(this.axios, {
            hdtb_id: this.hdtb_id,
          });
          let ds = rs_ds.data ? rs_ds.data.data : [];
          // Xóa hiển thị cũ
          this.$refs.wbrTSKT.innerHTML = "";
          // Hiển thị dữ liệu mới
          if (ds) {
            ds.forEach((dr) => {
              this.$refs.wbrTSKT.innerHTML +=
                "<b>" +
                dr["ten_truong"] +
                ": </b>" +
                (dr["giatri"] ? dr["giatri"] : "") +
                "<br />";
            });
          }
        } catch (err) {
          if (this.$refs.wbrTSKT)
            this.$refs.wbrTSKT.innerHTML = "Lỗi: " + err.message;
          reject(false);
        }

        resolve(true);
      });
    },

    async TuDongGiaoViec() {
      let nhiemvu = "";
      let ds_phieu = [];
      if (this.tthd_id == TrangThaiHD.DIEUHANH_THI_CONG)
        nhiemvu = "Điều hành thi công";
      else if (this.tthd_id == TrangThaiHD.KHAIBAO_TONGDAI)
        nhiemvu = "Khai báo thông số";
      else if (this.tthd_id == TrangThaiHD.DANG_THI_CONG) nhiemvu = "Thi công";
      else if (this.tthd_id == TrangThaiHD.DOISOAT_HOSO)
        nhiemvu = "Đối soát hồ sơ";
      else if (this.tthd_id == TrangThaiHD.DANGCHO_HOANCONG)
        nhiemvu = "Đang chờ hoàn công";
      else nhiemvu = "";

      console.log(this.tthd_id, "tthd_id");
      //Cập nhật thông tin giao việc nhân viên tự động
      // hoangpkn : 23/03/2015
      // thêm phục vụ cho ISDN
      //objgiaophieu.Delete(phieu_id);
      //TaoDuLieu_GIAOPHIEU_NV(phieu_id, nhiemvu);
      //objgiaophieu.Insert(dsnhanviengp);
      let vphieu_id = 0;
      if (
        (this.loaitb_id == LoaiHinhTB.ISDN2B_CD ||
          this.loaitb_id == LoaiHinhTB.ISDN2B_CQ ||
          this.loaitb_id == LoaiHinhTB.ISDN30B_CD ||
          this.loaitb_id == LoaiHinhTB.ISDN30B_CQ ||
          this.loaitb_id == LoaiHinhTB.TRUNGKE_2M ||
          this.loaitb_id == LoaiHinhTB.TRUNGKE_THUONG ||
          this.loaitb_id == LoaiHinhTB.TRUNGKE_TUONGTU) &&
        this.sochinh == true
      ) {
        for (let i = 0; i < this.dsIn.length; i++) {
          if (this.dsIn[i]["hdtb_cha_id"] == this.hdtb_id) {
            vphieu_id = Number(this.dsIn[i]["phieu_id"]);
            ds_phieu.push(vphieu_id);
            this.TaoDuLieu_GIAOPHIEU_NV(vphieu_id, nhiemvu);
          }
        }
      }

      if (await this.PHAILAM("GIAOVIEC_NVQL_CAP")) {
        let rs = await api.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.axios, {
          phieu_id: this.phieu_id,
          nhanvien_id: this.$root.token.getNhanVienID(),
          kieu_id: 2,
        });

        let dsNV = rs.data.data;

        if (dsNV && dsNV.length <= 0) {
          rs = await this.axios.post(
            "/web-quantri/lay-dulieu/sp_tt_vi_nhanvien_theocap_hdtb",{
                "param": this.hdtb_id,
                "type": 1
            })

          let dsGV = rs.data.data;

          if (dsGV && dsGV.length > 0) {
            if (dsGV[0]["nhanvien_id"] != "") {
              nvql_cap_id = Number(dsGV[0]["nhanvien_id"]);
              this.TaoDL_GiaoPhieu_NVQL_CAP(
                this.phieu_id,
                nhiemvu,
                nvql_cap_id
              );
            }
          } else {
            this.TaoDuLieu_GIAOPHIEU_NVth(this.phieu_id, nhiemvu);
          }
        }
      } else {
        this.TaoDuLieu_GIAOPHIEU_NV(this.phieu_id, nhiemvu);
      }

      let giaoviec_nvql_cap = await this.PHAILAM("GIAOVIEC_NVQL_CAP");

      let data = {
        TTHD_ID: this.tthd_id,
        LOAITB_ID: this.loaitb_id,
        NGUOIGV_ID: Number(this.$refs.cboNguoiGV.value),
        NGAYGIAO: this.$refs.dtpNgayGV.value,
        DS_PHIEU_ID: ds_phieu,
        PHIEU_ID: this.phieu_id,
        NHANVIEN_ID: this.$root.token.getNhanVienID(),
        HDTB_ID: this.hdtb_id,
        SOCHINH: this.sochinh,
        GIAOVIEC_NVQL_CAP: giaoviec_nvql_cap ? 1 : 0,
      };

      await api.fn_tudong_giaoviec_hc_codinh(this.axios, {
        ds_para: JSON.stringify(data),
      });

      //return true;
      // end hoàng thêm
    },
    TaoDuLieu_GIAOPHIEU_NV(vphieu_id, nhiemvu) {
      this.dsnhanviengp = [];
      let row = {};
      row.PHIEU_ID = vphieu_id;
      row.NHANVIEN_TH_ID = this.$root.token.getNhanVienID();
      row.NHIEMVU = nhiemvu;
      row.GHICHU = "";
      row.NHANVIEN_GIAO_ID = Number(this.$refs.cboNguoiGV.value);
      row.NGAYGIAO = this.$refs.dtpNgayGV.value;
      this.dsnhanviengp.push(row);
    },
    TaoDL_GiaoPhieu_NVQL_CAP(vphieu_id, nhiemvu, nvql_cap_id) {
      this.dsnhanviengp = [];
      let row = {};
      row.PHIEU_ID = vphieu_id;
      row.NHANVIEN_TH_ID = Number(nvql_cap_id);
      row.SO_DT = "";
      row.NHIEMVU = nhiemvu;
      row.GHICHU = "";
      row.NHANVIEN_GIAO_ID = Number(this.$refs.cboNguoiGV.value);
      row.NGAYGIAO = this.$refs.dtpNgayGV.value;
      this.dsnhanviengp.push(row);
    },

    async giaoviectudong() {
      let sochinh = await api.phailam(this.axios, {
        luong_id: this.luong_id,
        code: "GIAOVIEC",
      });
      let giaoviec_nvql_cap = await api.phailam(this.axios, {
        luong_id: this.luong_id,
        code: "GIAOVIEC_NVQL_CAP",
      });
      let ds_phieu = [];
      let selected = this.$refs.gvDanhSach.getSelectedRecords();
      selected.forEach((item) => ds_phieu.push({ ID: item.phieu_id }));
      let param = {
        TTHD_ID: this.tth_id,
        LOAITB_ID: this.loaitb_id,
        NGUOIGV_ID: this.nguoigv_id,
        NGAYGIAO: this.ngaygiao,
        DS_PHIEU_ID: ds_phieu,
        PHIEU_ID: this.phieu_id,
        NHANVIEN_ID: this.$root.token.getNhanVienID(),
        HDTB_ID: this.hdtb_id,
        SOCHINH: sochinh.data ? sochinh.data.data : 0,
        GIAOVIEC_NVQL_CAP: giaoviec_nvql_cap.data
          ? giaoviec_nvql_cap.data.data
          : 0,
      };
      await api.fn_tudong_giaoviec_hc_codinh(this.axios, {
        ds_para: JSON.stringify(param),
      });
    },
    async CapNhat() {
      try {
        if (!(await this.PHAILAM("GIAOVIEC"))) {
          console.log("Giao việc tự động");
          this.TuDongGiaoViec();
        }

        let dr_rs = this.$refs.gvDanhSach.getSelectedRecords();
        let dr = dr_rs.length > 0 ? dr_rs[0] : {};

        let ngay_bg = moment(this.$refs.dtpNgayBG.value, "DD/MM/YYYY HH:mm:ss");
        let ngay_tt = moment(dr.thangnam_tt, "DD/MM/YYYY HH:mm:ss");
        let now = moment();
        if (ngay_tt > ngay_bg) {
          this.$toast.error(
            "Thuê bao " +
              dr["ma_tb"] +
              " có ngày thanh toán : " +
              dr.thangnam_tt +
              " \n Ngày hoàn thành không được phép nhỏ hơn ngày thanh toán"
          );
          return;
        }
        // if (ngay_bg > now) {
        //   this.$toast.error(
        //     "Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại!"
        //   );
        //   return;
        // }
        console.log(this.$refs.dtpNgayGV.value, "dtpNgayGV");
        console.log(this.$refs.dtpNgayBG.value, "dtpNgayBG");
        // if (
        //   moment(this.$refs.dtpNgayGV.value, "DD/MM/YYYY HH:mm:ss").valueOf() >
        //     moment(this.$refs.dtpNgayBG.value, "DD/MM/YYYY HH:mm:ss").valueOf()
        // ) {
        //   this.$toast.error(
        //     "Ngày giao việc không được lớn hơn ngày hoàn thành!"
        //   );
        //   return;
        // }

        let data = {
          ds_in: [],
          dt: [],
          ngay_bg: "",
          ngay_gv: "",
        };

        data.dt.push({
          HDTB_ID: this.hdtb_id,
          HDKH_ID: this.hdkh_id,
          THUEBAO_CHA_ID: this.thuebao_cha_id,
          MA_TB: this.$refs.txtMaTB.value,
          LUONG_ID: this.luong_id,
          SOCHINH: this.sochinh ? 1 : 0,
          LOAITB_ID: this.loaitb_id,
          LOAIHD_ID: this.loaihd_id,
          TRAMVETINH_ID: this.$refs.cboTramVeTinh.value,
          TRAMTB_ID: this.tramtb_id ? this.tramtb_id : null,
          NE_ID: this.ne_id,
          NE: this.$refs.txtNE.value,
          TTHD_ID: this.tthd_id,
          DOICAPNGON: this.$refs.txtDoiCapNgon.value,
          DOICAPGOC: this.$refs.txtDoiCapGoc.value,
          HOPCAPNGON: this.$refs.txtHopCapNgon.value,
          TUCAPNGON: this.$refs.txtTuCapNgon.value,
          TUYENCAP: this.$refs.txtTuyenCap.value,
          LIENTU: this.$refs.txtLienTu.value,
          CAPGOC: this.$refs.txtCapGoc.value,
          NE_ID_CU: this.ne_cu_id,
          CAPNGON: this.$refs.txtCapNgon.value,
          TUCAPGOC: this.$refs.txtTuCapGoc.value,
          LOAICAP: this.loaicap_id,
          SOMETDAY: this.$refs.txtSoMetDay.value,
          NHANVIEN_ID: this.$root.token.getNhanVienID(),
          NGUOI_GV: this.$refs.cboNguoiGV.value,
          STATUS: this.status,
          PHIEU_ID: this.phieu_id,
        });
        let selected = this.$refs.gvDanhSach.getSelectedRecords();
        selected.forEach((item) => {
          data.ds_in.push({
            HDTB_CHA_ID: item.hdtb_cha_id,
            HDTB_ID: item.hdtb_id,
            PHIEU_ID: this.phieu_id,
            HDKH_ID: this.hdkh_id,
            THUEBAO_CHA_ID: item.hdtb_cha_id,
          });
        });
        data.dt = JSON.stringify(data.dt);
        data.ds_in = JSON.stringify(data.ds_in);
        data.ngay_bg = this.$refs.dtpNgayBG.value;
        data.ngay_gv = this.$refs.dtpNgayGV.value;
        this.loading(true);
        let rs = await api.capnhat(this.axios, data);
        this.HienThiDanhSachHDTB();
        if (rs.data.data == 1) this.$toast.success("Cập nhật thành công!");
        else this.$toast.error(rs.data.data);
        this.loading(false);
        this.$refs.gvDanhSach.setCurrentSelectedRow(this.old_index);
        this.$refs.gvDanhSach.$refs.DataGridCustom.flagSelectedRowIndexes = [];
        this.$refs.gvDanhSach.$refs.DataGridCustom.flagSelectedRowIndexes.push(this.old_index);
      } catch (err) {
        console.log(err);
        this.loading(false);
        this.$toast.error(
          "Cập nhập thất bại. '" + (err.message ? err.message : "") + "'"
        );
        this.loading(false);
      }
      this.loading(false);
    },
    async HienThiDSNV(ds) {
      //lvwNhanVien.Items.Clear();
      if (ds && ds.length > 0) {
        this.$refs.chkNgayGV.checked = true;
        this.chkNgayGV_CheckedChanged()
        this.$refs.dtpNgayGV.value = ds[0]["ngaygiao"] ? ds[0]["ngaygiao"] : moment(new Date()).format(
          "DD/MM/YYYY HH:mm:ss"
        );
        this.$refs.cboNguoiGV.value = ds[0]["nhanvien_giao_id"];
        //dgvNhanVien.AutoGenerateColumns = false;
        this.$refs.gridGiaoviec.dataSource = ds;
        this.nhanvien_tc_id = ds[0]["nhanvien_th_id"];
        this.congviec_th = ds[0]["nhiemvu"];
      } else {
        this.$refs.gridGiaoviec.dataSource = [];
        this.$refs.chkNgayGV.checked = true;
        this.chkNgayGV_CheckedChanged()
        this.$refs.dtpNgayGV.value = moment(new Date()).format(
          "DD/MM/YYYY HH:mm:ss"
        );

        this.$refs.cboNguoiGV.placeholder = "";
        this.$refs.cboNguoiGV.value = this.$root.token.getNhanVienID();
        this.nhanvien_tc_id = -1;
        this.congviec_th = "";
      }
    },
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
    async GiaoPhieuView_OnClick() {
      this.giaoPhieuViewDialog.isVisiable = true
      this.$refs.GiaoPhieuView.show()
    },
    async GiaoPhieuView_OnClose() {
      this.giaoPhieuViewDialog.isVisiable = false
    },
  },
};
</script>
<style>
.collapsed.fa-angle-up,
.not-collapsed.fa-angle-down {
  display: none !important;
}

.bold {
  font-weight: bold !important;
}

.brown {
  color: brown !important;
}

.blue {
  color: blue !important;
}

.form-control:disabled,
.form-control[readonly] {
  background-color: #f7f7f7 !important;
  opacity: 0.9 !important;
}

a.non-ative {
  color: #d3d3d3 !important;
  pointer-events: none;
  cursor: not-allowed;
  opacity: 0.6;
}

.form-control[readonly].highlight {
  background-color: #fff9eb !important;
}

.btn-second {
  border-color: #0176ff !important;
  color: #0176ff !important;
}

.red {
  color: red;
}
</style>
