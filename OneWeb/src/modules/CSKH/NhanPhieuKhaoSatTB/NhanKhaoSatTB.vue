<template src="./NhanKhaoSatTB.html"></template>

<script>
import { DSLoaiNhom, DSTrangThaiLG, DSTrangThaiPhieu } from "./common.js";
import API from "../api/GiaoNhanPhieuKhaoSatTB.js";
import moment from "moment";
import NhanVienQuanLy from "../DanhSachPhieuKhaoSatOutbound/popup/NhanVienQuanLy.vue";
import ThongTinLienHe from "../GiaoPhieuKhaoSatKhachHang/popup/modal-tt-lien-he/index.vue";
import frmKhaoSatKH_HDTB from "../KhaoSat/frmKhaoSatKH_HDTB.vue";
import frmKhaoSatKH from "../KhaoSat/frmKhaoSatKH.vue";
import frmKhaoSatKH_TB from "../KhaoSat/frmKhaoSatKH_TB.vue";
import xlsx from "xlsx";

export default {
  name: "NhanPhieuKhaoSatTBTrongDB",
  components: {
    appNhanVienQuanLy: NhanVienQuanLy,
    appThongTinLienHe: ThongTinLienHe,
    appfrmKhaoSatKH_HDTB: frmKhaoSatKH_HDTB,
    appfrmKhaoSatKH: frmKhaoSatKH,
    appfrmKhaoSatKH_TB: frmKhaoSatKH_TB,
  },
  beforeMount() {
    if (this.$route.query.TAG) this.TAG = this.$route.query.TAG;
  },
  data() {
    return {
      TAG: "",
      HPG_PHANVUNG_ID: 26,
      HCM_PHANVUNG_ID: 28,
      tag_TuDong_LayTT: 0,
      /// <summary>
      /// Tự động lấy phiếu
      /// </summary>
      LAYPHIEU_OUTBOUND_TUDONG: -1,
      KS_THIETLAP_TGLAYPHIEU: -1,
      THAMSO_THOIGIAN_TIMKIEM_OUTBOUND: -1,
      /// <summary>
      /// 1 -> Không được ấn tạm dừng
      /// </summary>
      KHONG_TAMDUNG_LAYPHIEU: -1,
      /// <summary>
      /// 1: Lấy phiếu, 0: Tạm dừng
      /// </summary>
      tt_btb_LayPhieu: -1,
      vgiaophieu_ks_id: 0,
      vmophieu: 0,
      hdtb_id: 0,
      hdkh_id: 0,
      baohong_id: 0,
      ct_suyhao_id: 0,
      thuebao_id: 0,
      loai: 0,
      donviID: 0,
      donviIDCHA: -1,
      muc_id_cha: 0,
      nodeText: "",
      donviBC: 0,
      kt_load: true,
      vnhom_id: 0,
      vkieu_ks: 0,
      logout_time: 0,
      co_hen: -1,
      //design
      chkNgayGiao: false,
      dtpGiaoTu: new Date(),
      dtpGiaoDen: new Date(),
      cboTrangthaiLanGiao: [],
      cboTrangthaiLanGiaoValue: "",
      cboLoaiNhom: [],
      cboLoaiNhomValue: "",
      cboTrangThaiPhieu: [],
      cboTrangThaiPhieuValue: "",
      btnLayPhieuShow: false,
      btnBoQuaShow: false,
      lblCanhBaoQuaGio: false,
      lblTuDongLayTT: false,
      lblTuDongLayTTText: "Lấy TT sau mỗi 5 phút không hoạt động (?/5 phút)",
      btnLayPhieuText: "Lấy phiếu",
      lblThongBaoOBText: "",
      //datagrid
      dtPhieuKS: [],
      dtPhieuKSItemSelecteds: [],
      dtPhieuKSRowSelected: "",
      dtPhieuKSTotalRows: 0,

      dtPhieuDaGiao: [],
      dtPhieuDaGiaoItemSelecteds: [],
      dtPhieuDaGiaoRowSelected: "",
      dtPhieuDaGiaoTotalRows: 0,

      intervalTimer1: "",
      intervalTimer2: "",
      dsHinhThucKS: [],
      idHinhThucKS: 0,
      vHinhThucKS: "",
      vIdHinhThucKS: 0
    };
  },
  beforeRouteLeave(to, from, next) {
    clearInterval(this.intervalTimer1);
    clearInterval(this.intervalTimer2);
    next();
  },
  watch: {
    async cboTrangThaiPhieuValue() {
      if (this.dtPhieuKS.length <= 0) {
        return;
      }
      let vgpksId = this.dtPhieuKSRowSelected["GIAOPHIEU_KS_ID"];
      let vTrangThai = this.cboTrangThaiPhieuValue;
      let vLoai = this.dtPhieuKSRowSelected["LOAI"];
      if (vLoai == 1 || vLoai == 2) {
        let data = {
          giaoPhieuKSId: vgpksId,
          loai: vLoai,
          trangThai: vTrangThai,
        };
        let resData = await this.getDSPhieuDaGiaoKSPaging(data, 1, 10000000, 1);
        if (resData.data.length) {
          this.dtPhieuDaGiao = resData.data;
          this.dtPhieuDaGiaoTotalRows = resData.totalElement;
        } else this.dtPhieuDaGiao = [];
      } else {
        let data = {
          giaoPhieuKSId: vgpksId,
          trangThai: vTrangThai,
        };
        let resData = await this.getDSPhieuDaGiaoKSTBPaging(
          data,
          1,
          10000000,
          1
        );
        if (resData.data.length > 0) {
          this.dtPhieuDaGiao = resData.data;
          this.dtPhieuDaGiaoTotalRows = resData.totalElement;
        } else this.dtPhieuDaGiao = [];
      }
    },
  },
  methods: {
    formatDate(value) {
      return moment(value).format("DD/MM/yyyy");
    },
    async dtPhieuKSSelectedRowChanged(args) {
      if (args) {
        let value = args;
        if (value) {
          this.dtPhieuKSRowSelected = value;
          let vgpks_id = value["GIAOPHIEU_KS_ID"];
          this.vgiaophieu_ks_id = value["GIAOPHIEU_KS_ID"];
          let vtrangthai = this.cboTrangThaiPhieuValue;
          this.vkieu_ks = value["KIEU_KS"];
          this.vmophieu = value["MOPHIEU"];
          this.loai = value["LOAI"];
          this.co_hen = value["CO_HEN"];
          this.vHinhThucKS = value["HINHTHUC_KS"]
          this.vIdHinhThucKS = value["HINHTHUCKS_ID"]
          let ds_tb = "";
          if (this.vkieu_ks == 1) {
            if (this.loai == 1 || this.loai == 2) {
              let data = {
                giaoPhieuKSId: vgpks_id,
                loai: this.loai,
                trangThai: vtrangthai,
              };
              ds_tb = await this.getDSPhieuDaGiaoKSPaging(data, 1, 10000000, 1);
            } else {
              let data = {
                giaoPhieuKSId: vgpks_id,
                trangThai: vtrangthai,
              };
              ds_tb = await this.getDSPhieuDaGiaoKSTBPaging(
                data,
                1,
                10000000,
                1
              );
            }
          } else if (this.vkieu_ks == 2) {
            let data = {
              giaoPhieuKSId: vgpks_id,
              loai: this.loai,
              trangThai: vtrangthai,
            };
            ds_tb = await this.getDSPhieuDaGiaoKSV2Paging(data, 1, 10000000, 1);
          }
          if (ds_tb.data.length > 0) {
            this.dtPhieuDaGiao = ds_tb.data;
            this.dtPhieuDaGiaoTotalRows = ds_tb.totalElement;
          } else this.dtPhieuDaGiao = [];
        }
      }
    },
    dtPhieuKSSelectedItemsChanged(value) {
      if (value) {
        this.dtPhieuKSItemSelecteds = value;
      }
      if (!value.length) this.dtPhieuDaGiao = [];
    },
    dtPhieuKSGridChanged(args) {
      this.timKiem(args.pageIndex + 1, args.pageSize);
    },

    dtPhieuDaGiaoSelectedRowChanged(args) {
      if (args) {
        let value = args;
        if (value) {
          this.dtPhieuDaGiaoRowSelected = value;
          if (this.loai == 1) {
            this.hdkh_id = value["HDKH_ID"];
            this.hdtb_id = value["HDTB_ID"];
          } else if (this.loai == 2) {
            this.baohong_id = value["BAOHONG_ID"];
          } else {
            this.thuebao_id = value["THUEBAO_ID"];
          }
        }
      }
    },
    dtPhieuDaGiaoSelectedItemsChanged(value) {
      if (value) this.dtPhieuDaGiaoItemSelecteds = value;
    },
    dtPhieuDaGiaoGridChanged(args) {},
    btnNhanVienQL() {
      if (this.dtPhieuDaGiao.length <= 0)
        return this.$toast.error("Không có dữ liệu hiển thị !");
      let loai = this.dtPhieuKSRowSelected["LOAI"];
      if (loai == 1) {
        this.$refs.popupNVQL.vid = this.dtPhieuDaGiaoRowSelected["HDKH_ID"];
        this.$refs.popupNVQL.vloai = 2;
        this.$refs.popupNVQL.openDialog();
      } else if (loai == 2) {
        this.$refs.popupNVQL.vid =
          this.dtPhieuDaGiaoRowSelected["KHACHHANG_ID"];
        this.$refs.popupNVQL.vloai = 1;
        this.$refs.popupNVQL.openDialog();
      } else {
        this.$toast.error(
          "Chức năng này chỉ áp dụng cho trường hợp: Outbound báo hỏng/ lắp đặt"
        );
      }
    },
    btnLienHe() {
      if (this.dtPhieuDaGiao.length <= 0)
        return this.$toast.error("Không có dữ liệu hiển thị !");
      if (
        this.dtPhieuDaGiaoRowSelected["KHACHHANG_ID"] != 0 &&
        this.dtPhieuDaGiaoRowSelected["KHACHHANG_ID"] != null
      ) {
        this.$refs.popupTTLH.khachHangId =
          this.dtPhieuDaGiaoRowSelected["KHACHHANG_ID"];
        this.$refs.popupTTLH.onShow();
      }
    },
    async btnLayPhieuClick() {
      if (this.tt_btb_LayPhieu == 0 && this.KHONG_TAMDUNG_LAYPHIEU == 1) {
        return this.$toast.error("Không thể tạm dừng!");
      }
      if (this.tt_btb_LayPhieu == 1) {
        if (this.KS_THIETLAP_TGLAYPHIEU == 1) {
          let response = await API.getKiemTraThoiGianLayPhieu(this.axios);
          if (response.data.data != 1) {
            return this.$toast.error(response.data.data);
          }
        }
        this.btnLayPhieuText = "Tạm dừng";
        this.tt_btb_LayPhieu = 0;
      } else {
        this.btnLayPhieuText = "Lấy phiếu";
        this.tt_btb_LayPhieu = 1;
      }
      if (this.tt_btb_LayPhieu == 0) {
        await API.postThemNVKS(this.axios);
      }
      if (this.tt_btb_LayPhieu == 1) {
        await API.deleteXoaPhieuDSTMPTheoNV(this.axios);
      }
      this.timKiem(1, 10);
    },
    async btnBoQuaClick() {
      if (this.dtPhieuKS.length > 0) {
        if (this.dtPhieuKSRowSelected["KIEU_KS"] == 2 || this.co_hen > 0) {
          let giaophieuKSId = this.dtPhieuKSRowSelected["GIAOPHIEU_KS_ID"];
          let response = await API.getBoQuaPhieuKS(this.axios, {
            giaoPhieuKSId: this.vgiaophieu_ks_id,
          });
          if (response.data.error_code === "BSS-00000000")
            this.$toast.error("Bỏ qua phiếu thành công!");
        } else {
          return this.$toast.error("Không thể bỏ qua phiếu giao nhân công!");
        }
        this.dtPhieuKS = [];
        this.dtPhieuDaGiao = [];
        this.timKiem();
      }
    },
    btnTimKiem() {
      this.timKiem(1, 10);
    },
    btnKhaoSat() {
      if (this.dtPhieuKS.length <= 0){
        this.$toast.error("Không có dữ liệu khảo sát!");
        return;
      }
      if (this.dtPhieuDaGiao.length <= 0){
        this.$toast.error("Không có dữ liệu khảo sát!");
        return;
      }
    //   let vgiaophieu_ks_id_temp = this.vgiaophieu_ks_id;
    //   if (this.loai == 1) {
    //     this.$refs.frmKhaoSatKH_HDTB.hdkhId = this.hdkh_id;
    //     this.$refs.frmKhaoSatKH_HDTB.hdtbId = this.hdtb_id;
    //     this.$refs.frmKhaoSatKH_HDTB.giaoPhieuKsId = this.vgiaophieu_ks_id;
    //     this.$refs.frmKhaoSatKH_HDTB.kieuKs = this.vkieu_ks;
    //     this.$refs.frmKhaoSatKH_HDTB.moPhieu = this.vmophieu;
    //     this.$refs.frmKhaoSatKH_HDTB.openDialog();
    //   } else if (this.loai == 2) {
    //     this.$refs.frmKhaoSatKH.giaoPhieuKsId = this.vgiaophieu_ks_id;
    //     this.$refs.frmKhaoSatKH.baoHongId = this.baohong_id;
    //     this.$refs.frmKhaoSatKH.pMoPhieu = this.vmophieu;
    //     this.$refs.frmKhaoSatKH.pKieuKs = this.vkieu_ks;
    //     this.$refs.frmKhaoSatKH.openDialog();
    //   } else {
    //     this.$refs.frmKhaoSatKH_TB.thueBaoId = this.thuebao_id;
    //     this.$refs.frmKhaoSatKH_TB.giaoPhieuKsId = this.vgiaophieu_ks_id;
    //     this.$refs.frmKhaoSatKH_TB.openDialog();
    //   }
      let titleKS = ` - ${this.vHinhThucKS}`
      let id_hinhthuc_ks = this.vIdHinhThucKS

      this.$refs.frmKhaoSatKH_TB.thueBaoId = this.thuebao_id;
      this.$refs.frmKhaoSatKH_TB.giaoPhieuKsId = this.vgiaophieu_ks_id;
      this.$refs.frmKhaoSatKH_TB.txtTitle = titleKS;
      this.$refs.frmKhaoSatKH_TB.hinhThucId = id_hinhthuc_ks;
      this.$refs.frmKhaoSatKH_TB.openDialog();
      this.timKiemV2(1, 10);
    },
    //functional
    async timKiem(pageNum, pageSize) {
      this.dtPhieuDaGiao = [];
      let vtungay = null;
      let vdenngay = null;
      let vloainhom_id = this.idHinhThucKS;
      let tt_luotgiao_id = this.cboTrangthaiLanGiaoValue;
      if (this.chkNgayGiao) {
        vtungay = this.formatDate(this.dtpGiaoTu);
        vdenngay = this.formatDate(this.dtpGiaoDen);
      }
      try {
        this.loading(true);
        let data = {
          nguoiNhanId: this.$auth.getNhanVienID(),
          tuNgay: vtungay,
          denNgay: vdenngay,
          loaiNhomId: vloainhom_id,
          ttLuotGiaoId: tt_luotgiao_id,
          pageNum: pageNum,
          pageSize: pageSize ? pageSize : this.$refs.griddtPhieuKS.pageSize,
          totalRow: 1,
        };
        let response = await API.getDSphieuGiaoNhanKS(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.dtPhieuKS = response.data.data.data;
          this.dtPhieuKSTotalRows = response.data.data.totalElement;
        }
        let resOB = await API.getThongBaoOBToanTinh(this.axios);
        this.lblThongBaoOBText = resOB.data.data;
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.loading(false);
      }
    },
    async timKiemV2(pageNum, pageSize) {
      this.dtPhieuDaGiao = [];
      let vtungay = null;
      let vdenngay = null;
      let vloainhom_id = this.idHinhThucKS;
      let tt_luotgiao_id = this.cboTrangthaiLanGiaoValue;
      if (this.chkNgayGiao) {
        vtungay = this.formatDate(this.dtpGiaoTu);
        vdenngay = this.formatDate(this.dtpGiaoDen);
      }
      try {
        this.loading(true);
        let data = {
          nguoiNhanId: this.$auth.getNhanVienID(),
          tuNgay: vtungay,
          denNgay: vdenngay,
          loaiNhomId: vloainhom_id,
          ttLuotGiaoId: tt_luotgiao_id,
          pageNum: pageNum,
          pageSize: pageSize ? pageSize : this.$refs.griddtPhieuKS.pageSize,
          totalRow: 1,
        };
        let response = await API.getDSphieuGiaoNhanKS(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.dtPhieuKS = response.data.data.data;
          this.dtPhieuKSTotalRows = response.data.data.totalElement;
        }
        let resOB = await API.getThongBaoOBToanTinh(this.axios);
        this.lblThongBaoOBText = resOB.data.data;
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.loading(false);
      }
    },
    async getDSPhieuDaGiaoKSPaging(
      data,
      pageNum = 1,
      pageSize = 10,
      totalRow = 1
    ) {
      let payload = { ...data, pageNum, pageSize, totalRow };
      try {
        this.loading(true);
        let response = await API.getDSphieuDaGiaoKS(this.axios, payload);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          return {
            data: response.data.data.data,
            totalElement: response.data.data.totalElement,
          };
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.loading(false);
      }
    },
    async getDSPhieuDaGiaoKSTBPaging(
      data,
      pageNum = 1,
      pageSize = 10,
      totalRow = 1
    ) {
      let payload = { ...data, pageNum, pageSize, totalRow };
      try {
        this.loading(true);
        let response = await API.getDSphieuDaGiaoKSTB(this.axios, payload);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          return {
            data: response.data.data.data,
            totalElement: response.data.data.totalElement,
          };
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.loading(false);
      }
    },
    async getDSPhieuDaGiaoKSV2Paging(
      data,
      pageNum = 1,
      pageSize = 10,
      totalRow = 1
    ) {
      let payload = { ...data, pageNum, pageSize, totalRow };
      try {
        this.loading(true);
        let response = await API.getDSphieuDaGiaoKSV2(this.axios, payload);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          return {
            data: response.data.data.data,
            totalElement: response.data.data.totalElement,
          };
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.error_code);
      } finally {
        this.loading(false);
      }
    },
    ondtPhieuDaGiaoQueryCellInfo(args) {
      if (
        args.column.field === "MA_TB" ||
        args.column.field === "LOAIHINH_TB" ||
        args.column.field === "NGAY_YC" ||
        args.column.field === "NGAY_HT"
      ) {
        if (args.data.TRANGTHAI == 1) {
          args.cell.classList.add("text-blue");
        } else {
          args.cell.classList.remove("text-blue");
        }
      }
    },
    async timer2Tick() {
      try {
        console.log("timer2 starting...");
        let response = await API.getPhieuQuaGioTheoNV(this.axios, {});
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data.length > 0) {
            this.lblCanhBaoQuaGio = true;
          } else {
            this.lblCanhBaoQuaGio = false;
          }
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.error_code);
      }
    },
    async timer1Tick() {
      console.log("timer1 starting...");
      let time = this.tag_TuDong_LayTT * this.THAMSO_THOIGIAN_TIMKIEM_OUTBOUND;
      this.logout_time += 30000;
      if (this.logout_time >= time) {
        await this.timKiem(1, 10);
        this.logout_time = 0;
      }
      if (this.KS_THIETLAP_TGLAYPHIEU == 1) {
        let response = await API.getKiemTraThoiGianLayPhieu(this.axios, {});
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data == 1) {
            this.btnLayPhieuText = "Lấy phiếu";
            this.tt_btb_LayPhieu = 1;
            await API.deleteXoaPhieuDSTMPTheoNV(this.axios, {});
          }
        }
      }
      if (this.logout_time >= 6000) {
        this.lblTuDongLayTTText =
          "Lấy TT sau mỗi " +
          this.tag_TuDong_LayTT +
          " phút không hoạt động <b>(" +
          this.logout_time / 60000 +
          "/" +
          this.tag_TuDong_LayTT +
          " phút)</b>";
      } else this.lblTuDongLayTTText = "";
    },
    async getDSHinhThucKS(){
      const response = await API.getDSHinhThucKS(this.axios,{kieu : 1});
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length >= 0
      ) {
        this.dsHinhThucKS = response.data.data.map(item => ({...item,HINHTHUC_KS_TXT: `${item.HINHTHUCKS_ID} - ${item.HINHTHUC_KS}`}));
        this.dsHinhThucKS.unshift({HINHTHUCKS_ID:0,HINHTHUC_KS:'Tất cả',HINHTHUC_KS_TXT:'0 - Tất cả'})
      }
    },
    XuatExcel() {
      if (this.dtPhieuDaGiao.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.dtPhieuDaGiao);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "danhSach.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
  },
  async mounted() {
    this.getDSHinhThucKS()
    if (this.TAG != "") {
      let words = this.TAG.split("+");
      if (words.length >= 1) {
        this.tag_TuDong_LayTT = words[0];
      }
    }
    if (this.$auth.getPhanVungID() == this.HCM_PHANVUNG_ID) {
      this.tag_TuDong_LayTT = 1;
    }
    // Loại nhóm
    this.cboLoaiNhom = DSLoaiNhom.filter((e) =>
      this.$auth.getPhanVungID() == this.HPG_PHANVUNG_ID
        ? true
        : e.PHANVUNG_ID == 0
    );
    this.cboLoaiNhomValue = DSLoaiNhom[0].NHOM_ID;
    //TrangThai
    this.cboTrangthaiLanGiao = DSTrangThaiLG;
    this.cboTrangthaiLanGiaoValue = DSTrangThaiLG[0].TT_LUOT_ID;
    //TrangThai phieu
    this.cboTrangThaiPhieu = DSTrangThaiPhieu;
    this.cboTrangThaiPhieuValue = DSTrangThaiPhieu[0].TT_PHIEU_ID;

    this.kt_load = false;
    let response = await API.getDSThamSoMD(this.axios);
    let dsTSMD = response.data.data;
    if (dsTSMD.length > 0) {
      let strTS = "LAYPHIEU_OUTBOUND_TUDONG";
      let det = dsTSMD.filter((e) => e.MA_TS == strTS);
      if (det.length > 0) {
        if (det[0].TEN_TS == 1) {
          this.LAYPHIEU_OUTBOUND_TUDONG = 1;
        }
      }
      strTS = "KS_THIETLAP_TGLAYPHIEU";
      det = dsTSMD.filter((e) => e.MA_TS == strTS);
      if (det.length > 0) {
        if (det[0].TEN_TS == 1) {
          this.KS_THIETLAP_TGLAYPHIEU = 1;
        }
      }
      strTS = "KHONG_TAMDUNG_LAYPHIEU";
      det = dsTSMD.filter((e) => e.MA_TS == strTS);
      if (det.length > 0) {
        if (det[0].TEN_TS == 1) {
          this.KHONG_TAMDUNG_LAYPHIEU = 1;
        }
      }
      strTS = "THAMSO_THOIGIAN_TIMKIEM_OUTBOUND";
      det = dsTSMD.filter((e) => e.MA_TS == strTS);
      if (det.length > 0) {
        if (det[0].TEN_TS != 0) {
          this.THAMSO_THOIGIAN_TIMKIEM_OUTBOUND = det[0].TEN_TS;
        }
      }
    }
    if (this.tag_TuDong_LayTT != 0) {
      console.log(this.THAMSO_THOIGIAN_TIMKIEM_OUTBOUND);
      this.intervalTimer1 = setInterval(() => {
        this.timer1Tick();
      }, this.THAMSO_THOIGIAN_TIMKIEM_OUTBOUND);
    }
    if (this.LAYPHIEU_OUTBOUND_TUDONG == 1) {
      this.btnLayPhieuShow = true;
      this.tt_btb_LayPhieu = 1;
    } else this.btnLayPhieuShow = false;
    this.lblCanhBaoQuaGio = false;
    this.intervalTimer2 = setInterval(() => {
      this.timer2Tick();
    }, 60000);
    this.lblTuDongLayTT = false;
    this.btnBoQuaShow = false;
    let resOB = await API.getThongBaoOBToanTinh(this.axios);
    this.lblThongBaoOBText = resOB.data.data;
  },
};
</script>

<style src="./NhanKhaoSatTB.scss"></style>
