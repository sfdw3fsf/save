<template src="./template.html"></template>
<script>
import cas from "./CAServices.js";
import moment from "moment";
import Select2Ext from "../DeclareLandline/components/Select2Ext";
import DataGridCustom from "../DeclareLandline/components/DataGridCustom";
import VueDateExt from "../DeclareLandline/components/VueDateExt";
import frmTimKiemHopDong from "./frmTimKiemHopDong.vue";
import fakeData from "./fakeData.js";
import UploadEProfileContent from "@/modules/contract/profile/UploadEProfile/UploadEProfile_content.vue";
import DiaChiModal from "@/modules/contract/setup/SurveyRequest/popups/ChonDiaChi/DiaChiModal.vue";
import breadcrumb from "@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue";
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue';
const thanhToan = () => import('@/modules/contract/profile/UploadEProfile/UploadEProfile_content.vue')

import {
  TrangThaiHD,
  DichVuVienThong,
  LoaiHopDong,
  KIEU_HD,
  TRANGTHAI_DONGBO,
  LoaiHinhTB,
  LOAI_DV,
} from "@/const/enums";
export default {
  components: {
    Select2Ext,
    DataGridCustom,
    VueDateExt,
    frmTimKiemHopDong,
    UploadEProfileContent,
    DiaChiModal,
    breadcrumb,
    GiaoPhieuView
  },
  data() {
    return {
      ngay_cn: moment().format("DD/MM/YYYY"),
      tdan: null,
      danhbaKH: null,
      thuebao_id: 0,
      cboMucDichCTS_dataSource: [],
      cboMucDichCTS: [],
      danhbaCNTT: null,
      dbtb_sub: null,
      tt_nd: null,
      diachiDB: null,
      caHH: null,
      txtMaGD: "",
      dataDiaChi: {},
      danhsachHopDongThueBao: null,
      diachiHD: null,
      bts: null,
      ds_dv: [],
      vquytrinh_id: 0,
      vluong_id: 0,
      vhuonggiao_id: 0,
      vldCAChuanHoa: null,
      _isNhapMoi: false,
      hdtb_id: null,
      muccuoctn_id: 0,
      tocdotn_id: 0,
      btnThongtinType: "",
      componentKey: 1,
      tt_nd: {},
      ma_thue_bao: "",
      ErrorText: {},
      ErrorControl: [],
      paymentloaded: false,
      indexClick: [],
      ChungThuSoLienQuan: [],
      targetTop: "#app .main-wrapper",
      position: { X: "center", Y: "top" },
      userComponentModule: null,
      popupComponentEvts: {
        "form-close": this.popupClosed,
        paymentcreated: this.paymentcreated
      },
      popupComponentName: "",
      popupComponentAttr: {},
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
      modelIn: {},
      modalTitle: "",
      position: { X: "center", Y: "top" },
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
  async mounted() {
    let rs = await this.axios.post("/quantri/user/thongtin_nguoidung2", {});
    this.tt_nd = rs.data.data;
    await this.LoadForm();
  },
  methods: {
    async dlgClose(e) {
      console.log("dlgClose", e);
    },
    overlayClick() {
      this.$refs.generalDialog.hide();
    },
    async paymentcreated(val) {
      this.loading(false)
      this.paymentloaded = true
      this.$refs.hosoDialog.show(true)
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup("popupComponents");
      }
      try {
      } catch (err) {
        console.log(err);
      } finally {
        this.ClosePopup("popupComponents");
      }
    },
    open_thanhToanPopUp() {
      // this.loading(true);
    },
    paymentSuccess() {
      this.SetButton(0);
    },
    gdvChungThuSoCMS_Clicked(index, data) {
      $(".thongtincts tr td").css(
          "background-color",
          "white"
        );
      let ee_id = this.$refs.grcThucTheCMS.getSelectedRecords()[0].ee_id;
      this.TaoDanhSachLienQuan(ee_id);

      console.log(this.ChungThuSoLienQuan, "ChungThuSoLienQuan")
      this.ChungThuSoLienQuan.forEach((item) => {
        let i = this.$refs.grcChungThuSoCMS.dataSource.findIndex(ct => ct.serial_number == item)

        $(".thongtincts tr[aria-rowindex=" + i + "] td").css(
          "background-color",
          "red"
        );
      });
    },
    DuyetDeQuy(parent_serial, cert_lst) {
      for (let i in cert_lst) {
        let item = cert_lst[i];
        if (item.serial_number == parent_serial) {
          this.ChungThuSoLienQuan.push(parent_serial);
          this.DuyetDeQuy(item.parent_serial, cert_lst);
        }
      }
      return;
    },
    TaoDanhSachLienQuan(ee_id) {
      this.ChungThuSoLienQuan = [];
      let serial_cha_dangchon =
        this.$refs.grcChungThuSoCMS.getSelectedRecords()[0].parent_serial;
      let serial_dangchon =
        this.$refs.grcChungThuSoCMS.getSelectedRecords()[0].serial_number;
      let cert_lst = this.caHH.meta_data.list.filter((x) => x.ee_id == ee_id);
      if (cert_lst.length > 0) {
        cert_lst = cert_lst[0].cert_list;
      } else {
        cert_lst = [];
      }
      if (serial_dangchon) {
        this.DuyetDeQuy(serial_cha_dangchon, cert_lst);
        this.ChungThuSoLienQuan.push(serial_dangchon);
      }
    },
    formatDate(value_) {
      if (value_ && !value_.includes("/")) {
        return moment(value_).format("DD/MM/YYYY HH:mm:ss");
      } else {
        return value_ ? value_ : moment().format("DD/MM/YYYY HH:mm:ss");
      }
    }
    ,
    ckbChonTatCa_CheckedChanged() {
      this.$refs.ckbDongBoCMSUserName.checked = this.$refs.ckbChonTatCa.checked;
      this.$refs.ckbDongBoSerial.checked = this.$refs.ckbChonTatCa.checked;
      this.$refs.ckbDongBoTenChuThe.checked = this.$refs.ckbChonTatCa.checked;
      this.$refs.ckbDongBoEmail.checked = this.$refs.ckbChonTatCa.checked;
      this.$refs.cbkDongBoIDCTS.checked = this.$refs.ckbChonTatCa.checked;
      this.$refs.ckbDongBoIdThucThe.checked = this.$refs.ckbChonTatCa.checked;
      this.$refs.ckbDongBoSDT.checked = this.$refs.ckbChonTatCa.checked;
      this.$refs.ckbDongBoNgayBD.checked = this.$refs.ckbChonTatCa.checked;
      this.$refs.ckbDongBoNgayKT.checked = this.$refs.ckbChonTatCa.checked;
      this.$refs.ckbDongBoSoThang.checked = this.$refs.ckbChonTatCa.checked;

      this.ckbDongBoAll_CheckedChanged('ckbDongBoSoThang')
      this.ckbDongBoAll_CheckedChanged("ckbDongBoCMSUserName");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoSerial");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoTenChuThe");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoEmail");
      this.ckbDongBoAll_CheckedChanged("cbkDongBoIDCTS");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoNgayBD");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoNgayKT");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoIdThucThe");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoSDT");
    },
    txtMaGD_ButtonClick() {
      this.$refs.frmTimKiemHopDong.showModal();
      this.btnThongtinType = "ma_gd";
    },
    timthongtinHD(data) {
      if (this.btnThongtinType == "ma_gd")
        this.$refs.txtMaGD.value = data.ma_gd;
      if (this.btnThongtinType == "ma_tb")
        this.$refs.txtMaTB.value = data.ma_tb;
    },

    txtMaTB_ButtonClick() {
      this.$refs.frmTimKiemHopDong.showModal();
      this.btnThongtinType = "ma_tb";
    },

    async HienThiThongTinDBKH() {
      return new Promise(async (resolve, reject) => {
        try {
          console.log("Bắt đầu HienThiThongTinDBKH")
          let rs;
          rs = await this.axios.post(
            "web-hopdong/dichvu-ca/lay_danhba_theo_matb",
            {
              in_ma_tb: this.$refs.txtMaTB.value,
              in_dichvuvt_id: 14,
              in_donvi_dl_id: 0,
            }
          );

          var dt = rs.data.data;
          if (dt && dt.length > 0) {
            this.danhbaKH = dt[0];
          } else {
            this.$toast.error("Không tìm thấy thông tin");
            resolve(true);
            return;
          }
          if (this.danhbaKH.loaitb_id != 116) {
            this.$toast.error("Chức năng chỉ dùng cho dịch vụ CA");
            resolve(true);
            return;
          }
          rs = await this.axios.post(
            "web-hopdong/dichvu-ca/lay_ds_diachi_theo_dbtbid",
            { in_thuebao_id: this.danhbaKH.thuebao_id }
          );
          var dt_dc_tonghop = rs.data && rs.data.data ? rs.data.data : [];
          if (dt_dc_tonghop && dt_dc_tonghop.length > 0) {
            this.diachiDB = dt_dc_tonghop[0];
            console.log(this.diachiDB, "test");
          } else {
            this.$toast.error("Không lấy được thông tin địa chỉ");
            resolve(true);
            return;
          }

          this.$refs.txtMaHD.value = this.danhbaKH.ma_hd;
          this.$refs.dtpNgayLapHD.value = this.formatDate(
            this.danhbaKH.ngaylap_hd
          );
          this.$refs.txtTenKH.value = this.danhbaKH.ten_kh;
          this.$refs.txtMaKH.value = this.danhbaKH.ma_kh;
          this.$refs.txtDiaChiKH.value = this.danhbaKH.diachi_kh;
          this.$refs.txtTenTB.value = this.danhbaKH.ten_tb;
          this.$refs.txtMaTB.value = this.danhbaKH.ma_tb;
          this.$refs.txtDiaChiLD.value = this.danhbaKH.diachi_ld;
          this.$refs.txtTrangThaiTB.value = this.danhbaKH.trangthai_tb;
          this.thuebao_id = this.danhbaKH.thuebao_id;
          this.$refs.dtpNgayYeuCau.value = this.ngay_cn;

          this.LoadThongTinDBSub();
          await this.LoadThongTinDBTBCNTT();
          this.LayDonViDich(
            this.danhsachHopDongThueBao.donvi_id,
            this.danhbaKH.thuebao_id,
            0
          );
          this.LayLuongNghiepVu();
          this.isNhapMoi = true;
        } catch (err) {
          console.log(err);
          this.$toast.error("Lỗi: " + err.Message);
        }
        resolve(true);
      });
    },

    async LoadThongTinDBTBCNTT() {
      return new Promise(async (resolve, reject) => {
        try {
          let rs = await this.axios.post(
            "web-hopdong/dichvu-ca/lay_ds_db_cntt_theo_tbid",
            {
              in_thuebao_id: this.thuebao_id,
              vphanvung_id: this.$root.token.getPhanVungID(),
            }
          );
          var dt = rs.data.data;
          if (dt.length > 0) {
            this.danhbaCNTT = dt[0];
            this.$refs.txtMST.value = this.danhbaCNTT.mst;
            this.$refs.txtSDT.value = this.danhbaCNTT.so_dt;
            this.$refs.txtSerial.value = this.danhbaCNTT.serial;
            this.$refs.txtSoThangSD.value = this.danhbaCNTT.so_thang ? this.danhbaCNTT.so_thang : "";
            this.$refs.dtpNgayBD.value = this.formatDate(
              this.danhbaCNTT.ngay_bd
            );
            this.$refs.dtpNgayKT.value = this.formatDate(
              this.danhbaCNTT.ngay_kt
            );
            this.$refs.cboGoiCuoc.value = this.danhbaCNTT.tocdo_id;
            this.$refs.txtEmail.value = this.danhbaCNTT.email;
            this.$refs.txtUserName.value = this.danhbaCNTT.username;
            this.cboMucDichCTS = this.danhbaCNTT.mucdich_ids
              ? this.danhbaCNTT.mucdich_ids
                  .split(",")
                  .map((item) => Number(item))
              : [];
            console.log(this.cboMucDichCTS, "this.cboMucDichCTS");
            this.$refs.txtCTSID.value = this.danhbaCNTT.cts_id;
            this.$refs.txtThucTheID.value = this.danhbaCNTT.ee_id;
            this.muccuoctn_id = this.danhbaCNTT.muccuoc_id;
            this.tocdotn_id = this.danhbaCNTT.tocdo_id;
            this.$refs.cboLoaiCTSId.value = this.danhbaCNTT.loaicts_id;
            this.$refs.cboHinhThucSinhKhoaId.value =
              this.danhbaCNTT.ht_sinhkhoa_id;
          }
          resolve(true);
        } catch (err) {
          console.log(err);
          this.$toast.error("Lỗi: " + err.message);
          reject(true);
        }
      });
    },

    async LoadThongTinDBSub() {
      try {
        let rs = await this.axios.post(
          "web-hopdong/dichvu-ca/lay_danhba_thuebao_sub_by_thuebao_id",
          {
            vthuebao_id: this.thuebao_id,
          }
        );
        var dt = rs.data.data;
        this.dbtb_sub = dt[0];
        this.$refs.txtSoGT.value = this.dbtb_sub.so_gt;
        this.$refs.dtpNgayHetHan.value = this.dbtb_sub.ngayhethan_gt;
        this.$refs.txtChucDanh.value = this.dbtb_sub.chucdanh;
        this.$refs.cboLoaiGT.value = this.dbtb_sub.loaigt_id;
      } catch (err) {}
    },

    async HienThiThongTinHDTB() {
      return new Promise(async (resolve, reject) => {
        try {
          if (this.$refs.txtMaGD.value != "") {
            let rs = await this.axios.post(
              "web-hopdong/dichvu-ca/lay_ds_hopdong_theo_ma_gd",
              {
                in_ma_gd: this.$refs.txtMaGD.value,
                in_loaihd_id: "24",
                in_donvi_id: this.$root.token.getDonViID(),
                in_tthd_id: "1",
                in_nhanvien_id: this.$root.token.getNhanVienID(),
                in_donvi_dl_id: "0",
              }
            );
            var dt = rs.data.data ? rs.data.data : [];
            var row_init = dt.filter(
              (item) => (item.KIEULD_ID = this.$refs.cboKieuLD.value)
            );
            if (row_init && row_init.length > 0) {
              this.dt_init = row_init;
              var dshd = this.dt_init;
              this.$refs.grcThueBao.dataSource = dshd;
              this.danhsachHopDongThueBao = dshd[0];

              //Thong tin khach hang
              this.$refs.txtMaGD.value = this.danhsachHopDongThueBao.ma_gd;
              this.$refs.txtMaKH.value = this.danhsachHopDongThueBao.ma_kh;
              this.$refs.txtTenKH.value = this.danhsachHopDongThueBao.ten_kh;
              this.$refs.dtpNgayYeuCau.value =
                this.danhsachHopDongThueBao.ngay_yc;
              this.$refs.dtpNgayLapHD.value =
                this.danhsachHopDongThueBao.ngaylap_hd;
              this.$refs.txtMaHD.value = this.danhsachHopDongThueBao.ma_hd;
              this.$refs.txtDiaChiKH.value =
                this.danhsachHopDongThueBao.diachi_kh;

              //thong tin thue bao
              this.$refs.txtMaTB.value = this.danhsachHopDongThueBao.ma_tb;
              this.$refs.txtTenTB.value = this.danhsachHopDongThueBao.ten_tb;
              this.$refs.txtDiaChiLD.value =
                this.danhsachHopDongThueBao.diachi_ld;
              this.$refs.txtTrangThaiTB.value =
                this.danhsachHopDongThueBao.trangthai_hd;

              rs = await this.axios.post(
                "web-hopdong/dichvu-ca/lay_ds_diachi_theo_hdtb_id",
                {
                  in_hdtb_id: this.danhsachHopDongThueBao.hdtb_id,
                }
              );
              var dt_dc_tonghop = (rs.data && rs.data.data) ? rs.data.data : [];
              if (dt_dc_tonghop.length > 0) {
                this.diachiHD = dt_dc_tonghop[0];
              } else {
                this.$toast.error("Không lấy được thông tin địa chỉ");
                this.loading(false)
                return;
              }
              //end thong tin thue bao
              await this.LoadThongTinHDTBCNTT(
                this.danhsachHopDongThueBao.hdtb_id
              );
              await this.LoadThongTinHDTB_SUB(
                this.danhsachHopDongThueBao.hdtb_id
              );
              this.LayDonViDich(
                this.danhsachHopDongThueBao.donvi_id,
                0,
                this.danhsachHopDongThueBao.hdtb_id
              );
              this.LayLuongNghiepVu();
              this.isNhapMoi = false;
            } else {
              this.$toast.error("Không tìm được hợp đồng");
            }
          }
        } catch (err) {
          console.log(err);
          this.$toast.error("Lỗi: " + err.message);
        }
        resolve(true);
      });
    },

    async LoadThongTinHDTBCNTT(in_hdtb_id) {
      return new Promise(async (resolve, reject) => {
        try {
          let rs = await this.axios.post(
            "web-hopdong/dichvu-ca/lay_ds_hdtb_cntt_theo_hdtb_id",
            {
              in_hdtb_id: in_hdtb_id,
            }
          );
          let dt = rs.data ? rs.data.data : [];
          if (dt && dt.length > 0) {
            var dshdtb_cntt = dt[0];
            this.$refs.txtSerial.value = dshdtb_cntt.serial;
            this.$refs.txtSDT.value = dshdtb_cntt.so_dt;
            this.$refs.txtMST.value = dshdtb_cntt.mst;
            this.cboMucDichCTS = dshdtb_cntt.mucdich_ids
              ? dshdtb_cntt.mucdich_ids.split(",").map((item) => Number(item))
              : [];
            this.$refs.dtpNgayBD.value = dshdtb_cntt.ngay_bd;
            this.$refs.dtpNgayKT.value = dshdtb_cntt.ngay_kt;
            this.$refs.txtCTSID.value = dshdtb_cntt.cts_id;
            this.$refs.txtUserName.value = dshdtb_cntt.username;
            this.$refs.txtThucTheID.value = dshdtb_cntt.ee_id;
            this.$refs.txtSoThangSD.value = dshdtb_cntt.so_thang;
            this.$refs.txtEmail.value = dshdtb_cntt.email;
            this.$refs.cboGoiCuoc.value = dshdtb_cntt.tocdo_id;
            this.muccuoctn_id = dshdtb_cntt.muccuoc_id;
            this.tocdotn_id = dshdtb_cntt.tocdo_id;
            this.$refs.cboHinhThucSinhKhoaId.value = dshdtb_cntt.ht_sinhkhoa_id;
            this.$refs.cboLoaiCTSId.value = dshdtb_cntt.loaicts_id;
          } else {
            this.$toast.error("Không lấy được thông tin hợp đồng CNTT");
            return;
          }
        } catch (err) {
          console.log(err);
          this.$toast.error("Lỗi: " + err.Message);
        }
        resolve(true);
      });
    },

    async LoadThongTinHDTB_SUB(in_hdtb_id) {
      return new Promise(async (resolve, reject) => {
        try {
          let rs = await this.axios.post(
            "web-hopdong/dichvu-ca/sp_lay_hdtbsub_doisoatca",
            {
              hdtb_id: in_hdtb_id,
            }
          );
          var dt = rs.data.data;
          if (dt.length > 0) {
            let hdtb_sub = dt[0];
            this.$refs.txtSoGT.value = hdtb_sub.so_gt;
            this.$refs.cboLoaiGT.value = hdtb_sub.loaigt_id;
            this.$refs.dtpNgayHetHan.value = hdtb_sub.ngayhethan_gt;
            this.$refs.txtChucDanh.value = hdtb_sub.chucdanh;
          } else {
            this.$toast.error("Không có dữ liệu");
            return;
          }
        } catch (err) {
          this.$toast.error("Lỗi: " + ex.Message);
        }
        resolve(true);
      });
    },

    async KhoiTaoDoiTuong() {
      this.tdan = {};
      this.danhbaKH = {};
      this.danhbaCNTT = {};
      this.dbtb_sub = {};
      this.diachiDB = {};
      this.diachiHD = {};
      this.danhsachHopDongThueBao = {};
      this.bts = null;
      this.ds_dv = [];
      this.vldCAChuanHoa = {};
      this.vldCAChuanHoa = {};
      // this.ValidateControl();
      this.isNhapMoi = true;
      this.thuebao_id = 0;
      this.caHH = null;
      this.hdtb_id = 0;
      this.muccuoctn_id = 0;
      this.vquytrinh_id = 0;
      this.vhuonggiao_id = 0;
      this.vluong_id = 0;
    },
    async LoadForm() {
      await this.KhoiTaoDoiTuong();
      await this.LoadCboTocDo();
      await this.LoadCboLoaiGT();
      await this.LoadCboDV();
      await this.LoadCboMucDich();
      await this.LoadCboKieuLD();
      await this.LoadCboLoaiHinhTB();
      await this.LoadCboHinhThucSinhKhoa();
      await this.LoadCboLoaiCTS();
      this.$refs.cboKieuLD.readOnly = true;
      this.$refs.cboDichVuVT.readOnly = true;
      this.$refs.cboLoaiHinhThueBao.readOnly = true;
    },

    async LoadCboLoaiCTS() {
      return new Promise(async (resolve, reject) => {
        let rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_LOAI_CTS");
        var dt = rs.data.data.filter((item) => item.LOAITB_ID == 116);
        this.$refs.cboLoaiCTSId.dataSource = dt;
        this.$refs.cboLoaiCTSId.dataTextField = "LOAI_CTS";
        this.$refs.cboLoaiCTSId.dataValueField = "LOAICTS_ID";
        resolve(true);
      });
    },

    async LoadCboHinhThucSinhKhoa() {
      return new Promise(async (resolve, reject) => {
        let rs = await this.axios.post(
          "web-hopdong/dichvu-ca/sp_lay_ht_sinhkhoa",
          { loaitb_id: 116 }
        );
        var dt = rs.data.data;
        this.$refs.cboHinhThucSinhKhoaId.dataSource = dt;
        this.$refs.cboHinhThucSinhKhoaId.dataTextField = "ht_sinhkhoa";
        this.$refs.cboHinhThucSinhKhoaId.dataValueField = "ht_sinhkhoa_id";
        resolve(true);
      });
    },

    async LoadCboTocDo() {
      return new Promise(async (resolve, reject) => {
        let rs = await this.axios.post(
          "web-hopdong/dichvu-ca/sp_lay_tocdo_adsl",
          {
            loaitb_id: 116,
          }
        );
        var dt = rs.data.data;
        this.$refs.cboGoiCuoc.dataSource = dt;
        this.$refs.cboGoiCuoc.dataTextField = "thuonghieu";
        this.$refs.cboGoiCuoc.dataValueField = "tocdo_id";
        resolve(true);
      });
    },

    async LoadCboLoaiGT() {
      return new Promise(async (resolve, reject) => {
        let rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_LOAI_GT");
        var dt = rs.data.data.filter((item) => item.MA_GT_CA);
        this.$refs.cboLoaiGT.dataSource = dt;
        this.$refs.cboLoaiGT.dataValueField = "LOAIGT_ID";
        this.$refs.cboLoaiGT.dataTextField = "LOAI_GT";
        resolve(true);
      });
    },

    async LoadCboLoaiHinhTB() {
      return new Promise(async (resolve, reject) => {
        let rs = await this.axios.get(
          "web-quantri/danhmuc-chung/CSS_LOAIHINH_TB"
        );
        var dt = rs.data.data.filter((item) => item.LOAITB_ID == 116);
        this.$refs.cboLoaiHinhThueBao.dataSource = dt;
        this.$refs.cboLoaiHinhThueBao.dataValueField = "LOAITB_ID";
        this.$refs.cboLoaiHinhThueBao.dataTextField = "LOAIHINH_TB";
        this.$refs.cboLoaiHinhThueBao.value = 116;
        resolve(true);
      });
    },

    async LoadCboKieuLD() {
      return new Promise(async (resolve, reject) => {
        let rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_KIEU_LD");
        var dt = rs.data.data.filter((item) => item.KIEULD_ID == 13182);
        this.$refs.cboKieuLD.dataSource = dt;
        this.$refs.cboKieuLD.dataValueField = "KIEULD_ID";
        this.$refs.cboKieuLD.dataTextField = "TEN_KIEULD";
        this.$refs.cboKieuLD.value = 13182;
        resolve(true);
      });
    },

    async LoadCboMucDich() {
      return new Promise(async (resolve, reject) => {
        // let rs = await this.axios.get("web-quantri/danhmuc-chung/CSS_MUCDICH_SD");
        let rs = await this.axios.get(
          "web-quantri/danhmuc-chung/CSS_MUCDICH_SD"
        );
        let dt = rs.data.data;
        dt = dt.filter((item) => item.MUCDICH_CA_ID);
        this.cboMucDichCTS_dataSource = dt.map((item) => ({
          id: item.MUCDICH_CA_ID,
          label: item.MUCDICH_SD,
        }));
        resolve(true);
      });
    },

    async LoadCboDV() {
      return new Promise(async (resolve, reject) => {
        let rs = await this.axios.get(
          "web-quantri/danhmuc-chung/CSS_DICHVU_VT"
        );
        var dt = rs.data.data.filter(
          (item) => item.SUDUNG == 1 && item.DICHVUVT_ID == 14
        );
        this.$refs.cboDichVuVT.dataSource = dt;
        this.$refs.cboDichVuVT.dataValueField = "DICHVUVT_ID";
        this.$refs.cboDichVuVT.dataTextField = "TEN_DVVT";
        this.$refs.cboDichVuVT.value = 14;
        resolve(true);
      });
    },

    async txtMaTB_KeyPress() {
      // if (e.KeyChar == Convert.ToChar(Keys.Enter))
      // {
      //     WaitDialogForm fwait = new WaitDialogForm("Bạn vui lòng chờ!");
      //     fwait.Show();
      //     fwait.Caption = "Đang lấy thông tin khách hàng";
      //     this.XoaForm();
      //     this.$refs.txtMaGD.value = null;
      this.loading(true);
      await this.HienThiThongTinDBKH();
      this.loading(false);
      //     fwait.Close();
      // }
    },

    frmChuanHoaCA_Load() {
      this.LoadForm();
    },

    cboLoaiGT_ButtonClick() {
      if (e.Button.Index == 1) {
        cboLoaiGT.value = null;
      } else {
        cboLoaiGT.ShowPopup();
      }
    },

    cboGoiCuoc_ButtonClick() {
      if (e.Button.Index == 1) {
        cboGoiCuoc.value = null;
      } else {
        cboGoiCuoc.ShowPopup();
      }
    },
    async txtMaGD_KeyPress() {
      this.loading(true);
      await this.HienThiThongTinHDTB();
      this.loading(false);
    },

    async TaoDuLieu() {
      let rs;
      return new Promise(async (resolve, reject) => {
        try {
          if (this.isNhapMoi) {
            this.vds_hdkh = {};
            rs = await this.axios.post(
              "/web-hopdong/khoiphucthanhly/get_keys",
              {
                keyname: "HD_KHACHHANG",
              }
            );
            this.vds_hdkh.HDKH_ID = rs.data.data;
            rs = await this.axios.post(
              "/web-hopdong/thongtin_tratruoc_dn/sinh_magd_v2",
              {
                vdonvi_id: this.$root.token.getDonViID(),
                vloaihd_id: 24,
              }
            );
            this.vds_hdkh.MA_GD = rs.data.data;
            this.vds_hdkh.KHACHHANG_ID = this.danhbaKH.khachhang_id;
            rs = await this.axios.post(
              "/web-hopdong/khoiphucthanhly/get_keys",
              {
                keyname: "HD_THUEBAO",
              }
            );
            this.hdtb_id = rs.data.data;
            this.vds_hdtb = {};

            this.vds_hdtb.HDTB_ID = this.hdtb_id;
            this.vds_hdtb.HDKH_ID = this.vds_hdkh.HDKH_ID;
            this.vds_hdtb.MA_TB = this.$refs.txtMaTB.value;
            this.vds_hdtb.TEN_TB = this.$refs.txtTenTB.value;
            this.vds_hdtb.THUEBAO_ID = this.danhbaKH.thuebao_id;
            this.vds_hdtb.DIACHI_LD = this.diachiDB.DIACHI_LD;
            this.vds_hdtb.QUYTRINH_ID = this.vquytrinh_id;
            this.vds_hdtb_sub = {};
            this.vds_hdtb_sub.HDTB_ID = this.hdtb_id;
            this.vds_hdtb_sub.CHUC_DANH = this.$refs.txtChucDanh.value;
            this.vds_hdtb_sub.LOAIGT_ID = this.$refs.cboLoaiGT.value;
            this.vds_hdtb_sub.SO_GT = this.$refs.txtSoGT.value;
            this.vds_hdtb_sub.NGAYHETHAN_GT = this.$refs.dtpNgayHetHan.value;

            this.vds_hdtb_cntt = {};
            this.vds_hdtb_cntt.HDTB_ID = this.hdtb_id;
            this.vds_hdtb_cntt.SERIAL = this.$refs.txtSerial.value;
            this.vds_hdtb_cntt.MST = this.$refs.txtMST.value;
            this.vds_hdtb_cntt.NGAY_BD =
              this.$refs.dtpNgayBD.value.includes("/") ? this.$refs.dtpNgayBD.value.split(" ")[0] : moment(this.$refs.dtpNgayBD.value).format("DD/MM/YYYY");
            this.vds_hdtb_cntt.NGAY_KT =
              this.$refs.dtpNgayKT.value.includes("/") ? this.$refs.dtpNgayKT.value.split(" ")[0] : moment(this.$refs.dtpNgayKT.value).format("DD/MM/YYYY");
            this.vds_hdtb_cntt.SO_DT = this.$refs.txtSDT.value;
            this.vds_hdtb_cntt.SO_THANG = Number(this.$refs.txtSoThangSD.value);
            this.vds_hdtb_cntt.USER_NAME = this.$refs.txtUserName.value;
            this.vds_hdtb_cntt.EMAIL = this.$refs.txtEmail.value;
            this.vds_hdtb_cntt.EE_ID = Number(this.$refs.txtThucTheID.value);
            this.vds_hdtb_cntt.CTS_ID = Number(this.$refs.txtCTSID.value);
            this.vds_hdtb_cntt.TOCDO_ID = Number(this.$refs.cboGoiCuoc.value);
            this.vds_hdtb_cntt.MUCDICH_IDS = this.cboMucDichCTS.join(",");
            this.vds_hdtb_cntt.HT_SINHKHOA_ID = Number(
              this.$refs.cboHinhThucSinhKhoaId.value
            );
            this.vds_hdtb_cntt.LOAICTS_ID = Number(
              this.$refs.cboLoaiCTSId.value
            );

            this.vds_diachi_ld = {};
            rs = await this.axios.post(
              "/web-hopdong/khoiphucthanhly/get_keys",
              {
                keyname: "DIACHI",
              }
            );
            this.vds_diachi_ld.DIACHI_ID = rs.data.data;
            this.vds_diachi_ld.AP_ID = this.diachiDB.apld_id;
            this.vds_diachi_ld.DACDIEM_ID = this.diachiDB.dacdiemld_id;
            this.vds_diachi_ld.DIACHI = this.diachiDB.diachi_ld;
            this.vds_diachi_ld.KHU_ID = this.diachiDB.khuld_id;
            this.vds_diachi_ld.KINHDO = this.diachiDB.kinhdo_ld;
            this.vds_diachi_ld.PHO_ID = this.diachiDB.phold_id;
            this.vds_diachi_ld.PHUONG_ID = this.diachiDB.phuongld_id;
            this.vds_diachi_ld.QUAN_ID = this.diachiDB.quanld_id;
            this.vds_diachi_ld.SONHA = this.diachiDB.sonha;
            this.vds_diachi_ld.TINH_ID = this.diachiDB.tinhld_id;
            this.vds_diachi_ld.VIDO = this.diachiDB.vido_ld;
            console.log(this.vds_diachi_ld, "this.vds_diachi_ld")

            this.vds_hdtb_donvi = [];
            console.log(this.ds_dv, "this.ds_dv")
            Object.values(this.ds_dv).forEach((item) => {
              var t = {};
              t.DONVI_ID = Number(item.value);
              if (!item.value && item.value != 0) {
                this.$toast.error("Chưa chọn đơn vị: " + item.loaidv_dich_id);
                resolve(false);
                return;
              }
              t.HDTB_ID = this.hdtb_id;
              t.LOAIDV_ID = Number(item.loaidv_dich_id);
              t.KIEUDV_ID = 2;
              this.vds_hdtb_donvi.push(t);
            });

          } else {
            this.vds_hdkh = {};
            this.vds_hdkh.HDKH_ID = this.danhsachHopDongThueBao.hdkh_id;
            this.vds_hdkh.MA_GD = this.danhsachHopDongThueBao.ma_gd;
            this.vds_hdkh.KHACHHANG_ID =
              this.danhsachHopDongThueBao.khachhang_id;

            this.hdtb_id = this.danhsachHopDongThueBao.hdtb_id;
            this.vds_hdtb = {};

            this.vds_hdtb.HDTB_ID = this.hdtb_id;
            this.vds_hdtb.HDKH_ID = this.vds_hdkh.HDKH_ID;
            this.vds_hdtb.MA_TB = this.$refs.txtMaTB.value;
            this.vds_hdtb.TEN_TB = this.$refs.txtTenTB.value;
            this.vds_hdtb.THUEBAO_ID = this.danhsachHopDongThueBao.thuebao_id;
            this.vds_hdtb.DIACHI_LD = this.$refs.txtDiaChiLD.value;
            this.vds_hdtb.QUYTRINH_ID = this.vquytrinh_id;

            this.vds_hdtb_sub = {};
            this.vds_hdtb_sub.HDTB_ID = this.hdtb_id;
            this.vds_hdtb_sub.CHUC_DANH = this.$refs.txtChucDanh.value;
            this.vds_hdtb_sub.LOAIGT_ID = this.$refs.cboLoaiGT.value;
            this.vds_hdtb_sub.SO_GT = this.$refs.txtSoGT.value;
            this.vds_hdtb_sub.NGAYHETHAN_GT = this.$refs.dtpNgayHetHan.value;

            this.vds_hdtb_cntt = {};
            this.vds_hdtb_cntt.HDTB_ID = this.hdtb_id;
            this.vds_hdtb_cntt.SERIAL = this.$refs.txtSerial.value;
            this.vds_hdtb_cntt.MST = this.$refs.txtMST.value;
            this.vds_hdtb_cntt.NGAY_BD =
              this.$refs.dtpNgayBD.value.includes("/") ? this.$refs.dtpNgayBD.value.split(" ")[0] : moment(this.$refs.dtpNgayBD.value).format("DD/MM/YYYY");
            this.vds_hdtb_cntt.NGAY_KT =
              this.$refs.dtpNgayKT.value.includes("/") ? this.$refs.dtpNgayKT.value.split(" ")[0] : moment(this.$refs.dtpNgayKT).format("DD/MM/YYYY");
            this.vds_hdtb_cntt.SO_DT = this.$refs.txtSDT.value;
            this.vds_hdtb_cntt.SO_THANG = Number(this.$refs.txtSoThangSD.value);
            this.vds_hdtb_cntt.USER_NAME = this.$refs.txtUserName.value;
            this.vds_hdtb_cntt.EMAIL = this.$refs.txtEmail.value;
            this.vds_hdtb_cntt.EE_ID = this.$refs.txtThucTheID.value;
            this.vds_hdtb_cntt.CTS_ID = this.$refs.txtCTSID.value;
            this.vds_hdtb_cntt.TOCDO_ID = Number(this.$refs.cboGoiCuoc.value);
            this.vds_hdtb_cntt.MUCDICH_IDS = this.cboMucDichCTS.join(",");
            this.vds_hdtb_cntt.HT_SINHKHOA_ID = Number(
              this.$refs.cboHinhThucSinhKhoaId.value
            );
            this.vds_hdtb_cntt.LOAICTS_ID = Number(
              this.$refs.cboLoaiCTSId.value
            );

            this.vds_diachi_ld = {};
            rs = await this.axios.post(
              "/web-hopdong/khoiphucthanhly/get_keys",
              {
                keyname: "DIACHI",
              }
            );
            this.vds_diachi_ld.DIACHI_ID = rs.data.data;
            this.vds_diachi_ld.AP_ID = this.diachiHD.apld_id;
            this.vds_diachi_ld.DACDIEM_ID = this.diachiHD.dacdiemld_id;
            this.vds_diachi_ld.DIACHI = this.diachiHD.diachi;
            this.vds_diachi_ld.KHU_ID = this.diachiHD.khuld_id;
            this.vds_diachi_ld.KINHDO = this.diachiHD.kinhdo_ld;
            this.vds_diachi_ld.PHO_ID = this.diachiHD.phold_id;
            this.vds_diachi_ld.PHUONG_ID = this.diachiHD.phuongld_id;
            this.vds_diachi_ld.QUAN_ID = this.diachiHD.quanld_id;
            this.vds_diachi_ld.SONHA = this.diachiHD.sonha;
            this.vds_diachi_ld.TINH_ID = this.diachiHD.tinhld_id;
            this.vds_diachi_ld.VIDO = this.diachiHD.vido_ld;

            this.vds_hdtb_donvi = [];
            Object.values(this.ds_dv).forEach((item) => {
              var t = {};
              t.DONVI_ID = Number(item.value);
              if (!item.value && item.value != 0) {
                this.$toast.error("Chưa chọn đơn vị: " + item.value);
                resolve(false);
                return;
              }
              t.HDTB_ID = this.hdtb_id;
              t.LOAIDV_ID = Number(item.loaidv_dich_id);
              t.KIEUDV_ID = 2;
              this.vds_hdtb_donvi.push(t);
            });
          }
        } catch (err) {
          console.log(err);
        }

        resolve(true);
      });
    },

    async CAPNHAT() {
      return new Promise(async (resolve, reject) => {
        try {
          if (this.isNhapMoi) {
            this.ValidateControl();
            if (this.ErrorControl.length > 0) {
              this.$toast.error("Dữ liệu chưa hợp lệ");
              resolve(true);
              return;
            }
          }
          let message;
          if (this.isNhapMoi) {
            message = "Thêm thành công!";
            let rs = await this.TaoDuLieu();
            console.log(this.vds_diachi_ld, "this.vds_diachi_ld sau")
            if (!rs) {
              this.loading(false);
              return;
            }
            rs = await this.axios.post(
              "web-hopdong/dichvu-ca/fn_capnhat_chuanhoaca",
              {
                isnhapmoi: 1,
                vds: JSON.stringify([
                  {
                    MA_TB: this.$refs.txtMaTB.value,
                    HUONGGIAO_ID: this.vhuonggiao_id,
                    KHACHHANG_ID: this.danhbaKH.khachhang_id,
                    THUEBAO_ID: this.danhbaKH.thuebao_id,
                    DICHVUVT_ID: this.$refs.cboDichVuVT.value,
                    NHANVIEN_ID: this.$root.token.getNhanVienID(),
                    DONVI_ID: this.$root.token.getDonViID(),
                    NGUOIDUNG_ID: this.tt_nd.nguoidung_id,
                    MAY_CN: this.tt_nd.ma_nd,
                    IP_CN: "1.2.3.4",
                  },
                ]),
                vds_hdkh: JSON.stringify(this.vds_hdkh),
                vds_hdtb: JSON.stringify(this.vds_hdtb),
                vds_hdtb_sub: JSON.stringify(this.vds_hdtb_sub),
                vds_hdtb_cntt: JSON.stringify(this.vds_hdtb_cntt),
                vds_diachi_ld: JSON.stringify(this.vds_diachi_ld),
                vds_hdtb_dv: JSON.stringify(this.vds_hdtb_donvi),
              }
            );
            if (rs.data && rs.data.data.substring(0, 2) == "1+") {
              this.isNhapMoi = false;
              this.$toast.success("Thêm thành công!");
              this.XoaForm();
              this.$refs.txtMaGD.value = rs.data.data.split("+")[1];
              this.HienThiThongTinHDTB();
            } else this.$toast.error(rs.data.data);
          } else {
            message = "Cập nhật thành công!";
            let rs = await this.TaoDuLieu();
            if (!rs) {
              this.loading(false);
              return;
            }
            rs = await this.axios.post(
              "web-hopdong/dichvu-ca/fn_capnhat_chuanhoaca",
              {
                isnhapmoi: 0,
                vds: JSON.stringify([
                  {
                    MA_TB: this.$refs.txtMaTB.value,
                    HUONGGIAO_ID: this.vhuonggiao_id,
                    KHACHHANG_ID: this.danhsachHopDongThueBao.khachhang_id,
                    THUEBAO_ID: this.danhsachHopDongThueBao.thuebao_id,
                    DICHVUVT_ID: this.$refs.cboDichVuVT.value,
                    NHANVIEN_ID: this.$root.token.getNhanVienID(),
                    DONVI_ID: this.$root.token.getDonViID(),
                    NGUOIDUNG_ID: this.tt_nd.nguoidung_id,
                    MAY_CN: this.tt_nd.ma_nd,
                    IP_CN: "1.2.3.4",
                  },
                ]),
                vds_hdkh: JSON.stringify(this.vds_hdkh),
                vds_hdtb: JSON.stringify(this.vds_hdtb),
                vds_hdtb_sub: JSON.stringify(this.vds_hdtb_sub),
                vds_hdtb_cntt: JSON.stringify(this.vds_hdtb_cntt),
                vds_diachi_ld: JSON.stringify(this.vds_diachi_ld),
                vds_hdtb_dv: JSON.stringify(this.vds_hdtb_donvi),
              }
            );
            if (rs.data && rs.data.data && !rs.data.data.includes("ERROR")) {
              this.isNhapMoi = false;
              this.$toast.success(message);
              this.XoaForm();
              this.txtMaGD_KeyPress();
              // this.HienThiThongTinHDTB();
            } else {
              this.$toast.error("Lưu thất bại!");
            }
          }
        } catch (err) {
          console.log(err);
          this.$toast.error(err.message);
        }
        resolve(true);
      });
    },

    tsbNhapMoi_Click() {
      this.XoaForm();
    },

    async tsbCapNhat_Click() {
      this.loading(true);
      await this.CAPNHAT();
      this.loading(false);
    },

    tsbThoat_Click() {
      this.Close();
    },

    async XoaHDKH() {
      this.loading(true);
      try {
        if (this.isNhapMoi) {
          this.$toast.error("Không đúng trạng thái không thể xoá");
          this.loading(false);
          return;
        }

        //Kiểm tra trạng thái HĐ
        console.log(this.danhsachHopDongThueBao);
        let rs = await this.axios.post(
          "web-hopdong/dichvu-ca/fn_xoa_hdkh_chuanhoaca",
          {
            vhdtb_id: this.danhsachHopDongThueBao.hdtb_id,
            vhdkh_id: this.danhsachHopDongThueBao.hdkh_id,
            vloaihd_id: LoaiHopDong.KY_LAI_HD_GOC,
          }
        );
        if (rs.data.data == 1) {
          this.XoaForm();
          this.$refs.grcThueBao.dataSource = [];
        } else {
          this.$toast.error(rs.data.data);
        }
      } catch (err) {
        this.$toast.error("Có lỗi khi xoá hợp đồng " + err.message);
      }
      this.loading(false);
    },

    //Xu ly cms

    async CMSTimKiemThueBaoHienHuu(serial, ten_chu_the, email, username, mst) {
      this.loading(true);
      try {
        try {
          let rs = await this.axios.post(
            "web-hopdong/dichvu-ca/sp_lay_ds_chungchi_ssl",
            {
              chungchi_id: 1,
            }
          );
          let dt_chungchi_ssl = rs.data.data;
          let chung_chi_ssl_b64 = dt_chungchi_ssl[0]["command"];

          rs = await this.axios.post("tichhop/ca/LayThongTinCAHienHuu", {
            cn: ten_chu_the,
            email: email,
            serial: serial,
            username: username,
          });
          this.caHH = rs.data.data;
          // this.caHH = fakeData
          this.$refs.grcThucTheCMS.dataSource = this.caHH.meta_data
            ? this.caHH.meta_data.list
            : [];
          // cas.LayThongTinCAHienHuu(
          //   serial,
          //   ten_chu_the,
          //   email,
          //   username,
          //   mst
          // );
        } catch (err) {
          console.log(err);
        }
      } catch (err) {
        console.log(err);
      }
      this.loading(false);
    },

    txtCMSSerial_KeyPress() {
      this.CMSTimKiemThueBaoHienHuu(
        this.$refs.txtCMSSerial.value,
        this.$refs.txtCMSTenChuThe.value,
        this.$refs.txtCMSEmail.value,
        this.$refs.txtCMSUserName.value,
        this.$refs.txtCMSMaSoThue.value
      );
    },

    txtCMSTenChuThe_KeyPress() {
      this.CMSTimKiemThueBaoHienHuu(
        this.$refs.txtCMSSerial.value,
        this.$refs.txtCMSTenChuThe.value,
        this.$refs.txtCMSEmail.value,
        this.$refs.txtCMSUserName.value,
        this.$refs.txtCMSMaSoThue.value
      );
    },

    txtCMSEmail_KeyPress() {
      this.CMSTimKiemThueBaoHienHuu(
        this.$refs.txtCMSSerial.value,
        this.$refs.txtCMSTenChuThe.value,
        this.$refs.txtCMSEmail.value,
        this.$refs.txtCMSUserName.value,
        this.$refs.txtCMSMaSoThue.value
      );
    },

    gdvThuTheCMS_FocusedRowChanged() {
      try {
        this.$refs.grcChungThuSoCMS.dataSource = null;
        let selected = this.$refs.grcThucTheCMS.getSelectedRecords();
        let cert_lst = this.caHH.meta_data.list.filter(
          (x) => x.ee_id == Number(selected[0]["ee_id"])
        )[0].cert_list;
        this.$refs.grcChungThuSoCMS.dataSource = cert_lst.map(item => ({
          ...item,
          start_time : moment(item.start_time).format("DD/MM/YYYY HH:mm:ss"),
          end_time : moment(item.end_time).format("DD/MM/YYYY HH:mm:ss")
        }));

        let select_gdvThuTheCMS = this.$refs.grcThucTheCMS.getSelectedRecords();
        this.$refs.txtCMSTenChuThe.value = select_gdvThuTheCMS[0]["ee_name"];
        this.$refs.txtCMSLoaiGT.value = select_gdvThuTheCMS[0]["uid_prefix"];
        this.$refs.txtCMSSoDT.value = select_gdvThuTheCMS[0]["contact_phone"];
        this.$refs.txtCMSDiaChi.value =
          select_gdvThuTheCMS[0]["l"] + ", " + select_gdvThuTheCMS[0]["st"];
        this.$refs.txtCMSUserName.value = select_gdvThuTheCMS[0]["username"];
        this.$refs.txtIDThucTheCMS.value = select_gdvThuTheCMS[0]["ee_id"];
        if (select_gdvThuTheCMS[0]["uid_prefix"] == "mst") {
          this.$refs.txtCMSMaSoThue.value =
            select_gdvThuTheCMS[0]("uid_subfix");
        } else {
          this.$refs.txtCMSSoGT.value = select_gdvThuTheCMS[0]["uid_subfix"];
        }
      } catch (err) {
        console.log(err);
      }
    },

    gdvChungThuSoCMS_FocusedRowChanged() {
      try {
        let selected_gdvChungThuSoCMS =
          this.$refs.grcChungThuSoCMS.getSelectedRecords();
        if (selected_gdvChungThuSoCMS.length > 0) {
          this.$refs.txtCMSSerial.value =
            selected_gdvChungThuSoCMS[0].serial_number;
          this.$refs.txtCMSEmail.value =
            selected_gdvChungThuSoCMS[0].subject_email;
          this.$refs.txtCMSSoThang.value =
            selected_gdvChungThuSoCMS[0].time_validity ? selected_gdvChungThuSoCMS[0].time_validity : "";
          this.$refs.dtpCMSNgayBD.value = selected_gdvChungThuSoCMS[0].start_time.includes("/") ? selected_gdvChungThuSoCMS[0].start_time : moment(
            selected_gdvChungThuSoCMS[0].start_time
          ).format("DD/MM/YYYY");
          this.$refs.dtpCMSNgayKT.value = selected_gdvChungThuSoCMS[0].end_time.includes("/") ? selected_gdvChungThuSoCMS[0].end_time : moment(
            selected_gdvChungThuSoCMS[0].end_time
          ).format("DD/MM/YYYY");
          this.$refs.txtGoiCuocCMS.value =
            selected_gdvChungThuSoCMS[0].certificate_profile_name;
          this.$refs.txtMucDichCMS.value = selected_gdvChungThuSoCMS[0].targets;
          this.$refs.txtCMSDoDaiKhoa.value =
            selected_gdvChungThuSoCMS[0].keylength;
          //txtCTSID.value = gdvChungThuSoCMS.getCurrentRowCellValue("id");
          this.$refs.txtIDChungThuSoCMS.value = selected_gdvChungThuSoCMS[0].id;
        }
      } catch (err) {
        console.log(err);
      }
    },

    gdvChungThuSoCMS_DataSourceChanged() {
      let selected_gdvChungThuSoCMS =
        this.$refs.gdvChungThuSoCMS.getSelectedRecords();
      this.$refs.txtCMSSerial.value =
        selected_gdvChungThuSoCMS[0].serial_number;
      this.$refs.txtCMSEmail.value = selected_gdvChungThuSoCMS[0].subject_email;
      this.$refs.txtCMSSoThang.value =
        selected_gdvChungThuSoCMS[0].time_validity ? selected_gdvChungThuSoCMS[0].time_validity : "";
      this.$refs.dtpCMSNgayBD.value = selected_gdvChungThuSoCMS[0].start_time;
      this.$refs.dtpCMSNgayKT.value = selected_gdvChungThuSoCMS[0].end_time;
      this.$refs.txtGoiCuocCMS.value =
        selected_gdvChungThuSoCMS[0].certificate_profile_name;
      this.$refs.txtMucDichCMS.value = selected_gdvChungThuSoCMS[0].targets;
      this.$refs.txtCMSDoDaiKhoa.value = selected_gdvChungThuSoCMS[0].keylength;
      this.$refs.txtIDChungThuSoCMS.value = selected_gdvChungThuSoCMS[0].id;
    },
    onAcceptDiaChi(obj) {
      if (this.isNhapMoi) {
        this.diachiDB.TINHLD_ID = obj.tinh_id;
        this.diachiDB.QUANLD_ID = obj.quan_id;
        this.diachiDB.PHUONGLD_ID = obj.phuong_id;
        this.diachiDB.PHOLD_ID = obj.pho_id;
        this.diachiDB.APLD_ID = obj.ap_id;
        this.diachiDB.KHULD_ID = obj.khu_id;
        this.diachiDB.DIACHI_LD = obj.diachi;
        this.diachiDB.SONHA_LD = obj.sonha;
        this.diachiDB.DACDIEMLD_ID = obj.dacdiem_id;
        this.diachiDB.KINHDO_LD = obj.longtitude;
        this.diachiDB.VIDO_LD = obj.lattitude;
        this.$refs.txtDiaChiLD.value = obj.diachi;
      } else {
        this.diachiHD.TINHLD_ID = obj.tinh_id;
        this.diachiHD.QUANLD_ID = obj.quan_id;
        this.diachiHD.PHUONGLD_ID = obj.phuong_id;
        this.diachiHD.PHOLD_ID = obj.pho_id;
        this.diachiHD.APLD_ID = obj.ap_id;
        this.diachiHD.KHULD_ID = obj.khu_id;
        this.diachiHD.DIACHI = obj.diachi;
        this.diachiHD.SONHA_LD = obj.sonha;
        this.diachiHD.DACDIEMLD_ID = obj.dacdiem_id;
        this.diachiHD.KINHDO_LD = obj.longtitude;
        this.diachiHD.VIDO_LD = obj.lattitude;
        this.txtDiaChiLD.value = obj.diachi;
      }
    },
    txtThayDoiDiaChi_Click() {
      this.dataDiaChi = {};
      if (this.isNhapMoi) {
        this.dataDiaChi.tinh_id = this.diachiDB.tinhld_id
          ? this.diachiDB.tinhld_id
          : null;
        this.dataDiaChi.quan_id = this.diachiDB.quanld_id
          ? this.diachiDB.quanld_id
          : null;
        this.dataDiaChi.phuong_id = this.diachiDB.phuongld_id
          ? this.diachiDB.phuongld_id
          : null;
        this.dataDiaChi.pho_id = this.diachiDB.phold_id
          ? this.diachiDB.phold_id
          : null;
        this.dataDiaChi.ap_id = this.diachiDB.apld_id
          ? this.diachiDB.apld_id
          : null;
        this.dataDiaChi.khu_id = this.diachiDB.khuld_id
          ? this.diachiDB.khuld_id
          : null;
        this.dataDiaChi.sonha = this.diachiDB.sonha_ld
          ? this.diachiDB.sonha_ld
          : null;
        this.dataDiaChi.dacdiem_id = this.diachiDB.dacdiemld_id
          ? this.diachiDB.dacdiemld_id
          : null;
        this.dataDiaChi.diachi = this.diachiDB.diachi_ld
          ? this.diachiDB.diachi_ld
          : "";
        this.dataDiaChi.lattitude = this.diachiDB.vido_ld
          ? his.diachiDB.vido_ld
          : null;
        this.dataDiaChi.longtitude = this.diachiDB.kinhdo_ld
          ? this.diachiDB.kinhdo_ld
          : null;
      } else {
        this.dataDiaChi.tinh_id = this.diachiHD.tinhld_id
          ? this.diachiHD.tinhld_id
          : null;
        this.dataDiaChi.quan_id = this.diachiHD.quanld_id
          ? this.diachiHD.quanld_id
          : null;
        this.dataDiaChi.phuong_id = this.diachiHD.phuongld_id
          ? this.diachiHD.phuongld_id
          : null;
        this.dataDiaChi.pho_id = this.diachiHD.phold_id
          ? this.diachiHD.phold_id
          : null;
        this.dataDiaChi.ap_id = this.diachiHD.apld_id
          ? this.diachiHD.apld_id
          : null;
        this.dataDiaChi.khu_id = this.diachiHD.khuld_id
          ? this.diachiHD.khuld_id
          : null;
        this.dataDiaChi.sonha = this.diachiHD.sonha_ld
          ? this.diachiHD.sonha_ld
          : null;
        this.dataDiaChi.dacdiem_id = this.diachiHD.dacdiemld_id
          ? this.diachiHD.dacdiemld_id
          : null;
        this.dataDiaChi.diachi = this.$refs.txtDiaChiLD.value
          ? this.$refs.txtDiaChiLD.value
          : null;
        this.dataDiaChi.lattitude = this.diachiHD.vido_ld
          ? this.diachiHD.vido_ld
          : null;
        this.dataDiaChi.longtitude = this.diachiHD.kinhdo_ld
          ? this.diachiHD.kinhdo_ld
          : null;
      }

      console.log(this.dataDiaChi);

      this.$refs.diaChiModal.showModal();
    },

    ckbDongBoAll_CheckedChanged(name) {
      switch (name) {
        case "ckbDongBoSerial":
          if (this.$refs.ckbDongBoSerial.checked) {
            this.$refs.txtSerial.style.background = "#43fd4369";

            this.$refs.txtCMSSerial.style.background = "#43fd4369";
          } else {
            this.$refs.txtSerial.style.background = "white";

            this.$refs.txtCMSSerial.style.background = "#fbf5e6";
          }
          break;
        case "ckbDongBoEmail":
          if (this.$refs.ckbDongBoEmail.checked) {
            this.$refs.txtEmail.style.background = "#43fd4369";

            this.$refs.txtCMSEmail.style.background = "#43fd4369";
          } else {
            this.$refs.txtEmail.style.background = "white";

            this.$refs.txtCMSEmail.style.background = "#fbf5e6";
          }
          break;
        case "ckbDongBoTenChuThe":
          if (this.$refs.ckbDongBoTenChuThe.checked) {
            this.$refs.txtTenTB.style.background = "#43fd4369";

            this.$refs.txtCMSTenChuThe.style.background = "#43fd4369";
          } else {
            this.$refs.txtTenTB.style.background = "white";

            this.$refs.txtCMSTenChuThe.style.background = "#fbf5e6";
          }
          break;
        case "ckbDongBoSoThang":
          if (this.$refs.ckbDongBoSoThang.checked) {
            this.$refs.txtSoThangSD.style.background = "#43fd4369";

            this.$refs.txtCMSSoThang.style.background = "#43fd4369";
          } else {
            this.$refs.txtSoThangSD.style.background = "white";

            this.$refs.txtCMSSoThang.style.background = "#fbf5e6";
          }
          break;
        case "ckbDongBoSDT":
          if (this.$refs.ckbDongBoSDT.checked) {
            this.$refs.txtSDT.style.background = "#43fd4369";

            this.$refs.txtCMSSoDT.style.background = "#43fd4369";
          } else {
            this.$refs.txtSDT.style.background = "white";

            this.$refs.txtCMSSoDT.style.background = "#fbf5e6";
          }
          break;
        case "ckbDongBoNgayBD":
          if (this.$refs.ckbDongBoNgayBD.checked) {
            $("#dtpNgayBD input").css("background-color", "#43fd4369");

            $("#dtpCMSNgayBD input").css("background-color", "#43fd4369");
          } else {
            $("#dtpNgayBD input").css("background-color", "#f5dc9a");

            $("#dtpCMSNgayBD input").css("background-color", "white");
          }
          break;
        case "ckbDongBoNgayKT":
          if (this.$refs.ckbDongBoNgayKT.checked) {
            $("#dtpNgayKT input").css("background-color", "#43fd4369");

            $("#dtpCMSNgayKT input").css("background-color", "#43fd4369");
          } else {
            $("#dtpNgayKT input").css("background-color", "#f5dc9a");

            $("#dtpCMSNgayKT input").css("background-color", "white");
          }
          break;
        case "ckbDongBoIdThucThe":
          if (this.$refs.ckbDongBoIdThucThe.checked) {
            this.$refs.txtIDThucTheCMS.style.background = "#43fd4369";

            this.$refs.txtThucTheID.style.background = "#43fd4369";
          } else {
            this.$refs.txtIDThucTheCMS.style.background = "white";

            this.$refs.txtThucTheID.style.background = "white";
          }
          break;
        case "cbkDongBoIDCTS":
          if (this.$refs.cbkDongBoIDCTS.checked) {
            this.$refs.txtCTSID.style.background = "#43fd4369";

            this.$refs.txtIDChungThuSoCMS.style.background = "#43fd4369";
          } else {
            this.$refs.txtCTSID.style.background = "white";

            this.$refs.txtIDChungThuSoCMS.style.background = "white";
          }
          break;
        case "ckbDongBoCMSUserName":
          if (this.$refs.ckbDongBoCMSUserName.checked) {
            this.$refs.txtCMSUserName.style.background = "#43fd4369";
            console.log(this.$refs.txtUserName);
            this.$refs.txtUserName.style.background = "#43fd4369";
          } else {
            this.$refs.txtCMSUserName.style.background = "white";

            this.$refs.txtUserName.style.background = "white";
          }
          break;
        default:
          break;
      }
    },

    // //Lay Luong, Quy trinh, huong giao
    async LayLuongNghiepVu() {
      let rs = await this.axios.post(
        "web-hopdong/hopdong/lay_huonggiao_tiepnhan",
        {
          kieuLdId: this.$refs.cboKieuLD.value,
          loaiHdId: LoaiHopDong.KY_LAI_HD_GOC,
          loaiTbId: this.$refs.cboLoaiHinhThueBao.value,
          mucCuocId: this.muccuoctn_id,
          tocDoId: this.tocdotn_id,
        }
      );
      let dset = rs.data && rs.data.data ? rs.data.data : null;
      if (!dset) {
        this.$toast.error(
          "Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!"
        );
        return;
      }
      this.vhuonggiao_id = Number(dset["huonggiao_id"]);
      this.vquytrinh_id = Number(dset["quytrinh_id"]);
      this.vluong_id = Number(dset["luong_id"]);
    },

    //reset form
    XoaForm() {
      // foreach (DevExpress.XtraLayout.LayoutControlItem item in layoutControlGroup2.Items)
      // {
      //     if (item.Control.Name != "this.$refs.txtMaGD" && item.Control.Name != "txtMaTB" && item.Control.Name != "cboKieuLD" && item.Control.Name != "cboDichVuVT" && item.Control.Name != "cboLoaiHinhThueBao")
      //     {
      //         if (typeof(TextEdit) == item.Control.GetType() || typeof(GridLookUpEdit) == item.Control.GetType() || typeof(CheckedComboBoxEdit) == item.Control.GetType() || typeof(DateEdit) == item.Control.GetType())
      //         {
      //             ((BaseEdit)item.Control).value = null;
      //         }
      //     }

      this.$refs.dtpNgayYeuCau.value = null;
      this.$refs.txtMaHD.value = null;
      this.$refs.dtpNgayLapHD.value = null;
      this.$refs.txtMaKH.value = null;
      this.$refs.txtTenKH.value = null;
      this.$refs.txtDiaChiKH.value = null;
      this.$refs.txtTenTB.value = null;
      this.$refs.txtDiaChiLD.value = null;
      this.$refs.txtUserName.value = null;
      this.$refs.txtSoThangSD.value = null;
      this.$refs.dtpNgayBD.value = null;
      this.$refs.dtpNgayKT.value = null;
      this.$refs.txtSerial.value = null;
      this.$refs.txtMST.value = null;
      this.$refs.txtSDT.value = null;
      this.$refs.txtEmail.value = null;
      this.$refs.cboLoaiGT.value = null;
      this.$refs.txtSoGT.value = null;
      this.$refs.txtChucDanh.value = null;
      this.$refs.dtpNgayHetHan.value = null;
      this.$refs.cboGoiCuoc.value = null;
      this.cboMucDichCTS = null;
      this.$refs.txtThucTheID.value = null;
      this.$refs.txtCTSID.value = null;

      this.$refs.cboHinhThucSinhKhoaId.value = null;
      this.$refs.cboLoaiCTSId.value = null;
      this.$refs.txtTrangThaiTB.value = null;

      // }
      // foreach (DevExpress.XtraLayout.LayoutControlItem item in layoutControlGroup3.Items)
      // {

      //     if (item.Control.Name != "this.$refs.txtMaGD" && item.Control.Name != "txtMaTB" && item.Control.Name != "cboKieuLD" && item.Control.Name != "cboDichVuVT" && item.Control.Name != "cboLoaiHinhThueBao")
      //     {
      //         if (typeof(TextEdit) == item.Control.GetType() || typeof(GridLookUpEdit) == item.Control.GetType() || typeof(CheckedComboBoxEdit) == item.Control.GetType() || typeof(DateEdit) == item.Control.GetType())
      //         {
      //             ((BaseEdit)item.Control).value = null;
      //         }
      //     }

      // }
      // cboMucDichCTS.SetEditValue(null);
      // grcThueBao.dataSource = null;

      this.KhoiTaoDoiTuong();
    },

    tsbXoaHopDong_Click() {
      let selected = this.$refs.grcThueBao.getSelectedRecords();
      if (selected.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu!");
      }
      this.$confirm(
        "Bạn có muốn xóa hợp đồng cho thuê bao: " + selected[0]["ma_tb"],
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }
      ).then(async () => {
        this.XoaHDKH();
      });
    },

    tsbUpHoSo_Click() {
      try {
        if (this.$refs.grcThueBao.getSelectedRecords().length == 0) {
          this.$toast.error("Bạn chưa chọn thuê bao nào");
          return;
        }
        //f.MdiParent = this.MdiParent;
        this.userComponentModule = thanhToan;
        this.txtMaGD = this.$refs.txtMaGD.value
        this.componentKey++;
        this.ma_thue_bao = this.$refs.grcThueBao.getSelectedRecords()[0]["ma_tb"];
        //f.setHoanCong();
        this.$nextTick(() => {
          this.$refs.hosoDialog.show(true)
        })
      } catch (err) {
        this.$toast.error(err.message);
      }
    },

    btnLamMoiCMS_Click() {
      this.$refs.ckbDongBoCMSUserName.checked = false;
      this.$refs.txtCMSUserName.value = "";
      this.$refs.ckbDongBoEmail.checked = false;
      this.$refs.txtCMSEmail.value = "";
      this.$refs.ckbDongBoIdThucThe.checked = false;
      this.$refs.txtIDThucTheCMS.value = "";
      this.$refs.ckbDongBoNgayBD.checked = false;
      this.$refs.dtpCMSNgayBD.value = "";
      this.$refs.ckbDongBoNgayKT.checked = false;
      this.$refs.dtpCMSNgayKT.value = "";
      this.$refs.ckbDongBoSDT.checked = false;
      this.$refs.txtCMSSoDT.value = "";
      this.$refs.ckbDongBoSerial.checked = false;
      this.$refs.txtCMSSerial.value = "";
      this.$refs.ckbDongBoSoThang.checked = false;
      this.$refs.txtCMSSoThang.value = "";
      this.$refs.ckbDongBoTenChuThe.checked = false;
      this.$refs.txtCMSTenChuThe.value = "";
      this.$refs.cbkDongBoIDCTS.checked = false;
      this.$refs.txtIDChungThuSoCMS.value = "";
      this.$refs.txtGoiCuocCMS.value = "";
      this.$refs.txtCMSLoaiGT.value = "";
      this.$refs.txtMucDichCMS.value = "";
      this.$refs.txtCMSDoDaiKhoa.value = "";
      this.$refs.txtCMSDiaChi.value = "";
      this.$refs.txtCMSSoGT.value = "";
      this.$refs.txtCMSMaSoThue.value = "";
      this.$refs.grcChungThuSoCMS.dataSource = null;
      this.$refs.grcThucTheCMS.dataSource = null;
      this.$refs.ckbChonTatCa.checked = false;
      this.ckbDongBoAll_CheckedChanged("ckbDongBoCMSUserName");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoSerial");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoTenChuThe");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoEmail");
      this.ckbDongBoAll_CheckedChanged("cbkDongBoIDCTS");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoNgayBD");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoNgayKT");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoIdThucThe");
      this.ckbDongBoAll_CheckedChanged("ckbDongBoSDT");
    },

    async LayDonViDich(donvi_ql_id, tb_id, hd_id) {
      try {
        this.layoutControlDonVi = [];
        this.ds_dv = {};
        // ds_dv.Clear();
        let rs = await this.axios.post(
          "web-hopdong/dichvu-ca/sp_ht_giaodien_donvi_theo_loaihd",
          {
            dichvuvt_id: this.$refs.cboDichVuVT.value,
            tocdo: this.$refs.cboGoiCuoc.value,
            muccuoc: this.muccuoctn_id,
            loaihinhtb_id: this.$refs.cboLoaiHinhThueBao.value,
            kieuld_id: this.$refs.cboKieuLD.value,
            loaidv_id: LOAI_DV.GIAODICH,
            loaihd_id: LoaiHopDong.KY_LAI_HD_GOC,
          }
        );
        let dt = rs.data.data ? rs.data.data : [];

        console.log(dt, "danh sách đơn vị");

        dt.forEach((row) => {
          row.placeholder = row["ten_loaidv"];
          row.name =
            "layoutItemDV" + row["loaidv_dich_id"] + "." + row["thutu_id"];
          row.dataSource = [];
          row.dataTextField = "ten_dv";
          row.dataValueField = "donvi_id";
          row.value = null;
          this.$set(this.ds_dv, row["loaidv_dich_id"], row);
        });
        // load datasource vao
        this.$nextTick(async () => {
          // lay gia tri mac dinh
          let promise = [];
          let key_source = [];
          dt.forEach(async (row, key) => {
            if (
              row["loaidv_dich_id"] == LOAI_DV.TRAM_VT ||
              row["loaidv_dich_id"] == LOAI_DV.TRAM_CAP_PORT
            ) {
              rs = this.axios.post(
                "web-hopdong/dichvu-ca/sp_lay_ds_donvi_tramtc_banthietbi",
                {
                  donviql_id: donvi_ql_id,
                  loaidv_id: LOAI_DV.TRAM_VT,
                }
              );
            } else {
              rs = this.axios.post(
                "web-hopdong/dichvu-ca/sp_lay_ds_donvi_theo_loaidv_banthietbi",
                {
                  loaidv_id: row["loaidv_dich_id"],
                }
              );
            }
            this.ds_dv[row["loaidv_dich_id"]].value = null;
            key_source.push(row["loaidv_dich_id"]);
            promise.push(rs);
          });

          Promise.all(promise).then(async (values) => {
            console.log(values, "DAtasource đây");
            values.forEach((item, key) => {
              this.ds_dv[key_source[key]].dataSource = item.data.data;
            });
            // this.ds_dv[row["loaidv_dich_id"]].dataSource = rs.data.data;
            console.log("Mày phải vào sau");
            if (Number(tb_id) > 0) {
              rs = await this.axios.post(
                "web-hopdong/dichvu-ca/sp_lay_ds_dbtb_dv",
                {
                  thuebao_id: tb_id,
                }
              );
              var dbtb_dv = rs.data.data;
              if (dbtb_dv.length > 0) {
                dt.forEach((dr) => {
                  var kiemtra = dbtb_dv.filter(
                    (item) => item.loaidv_id == dr["loaidv_dich_id"]
                  );
                  if (kiemtra.length > 0) {
                    this.ds_dv[dr["loaidv_dich_id"]].value = Number(
                      kiemtra[0]["donvi_id"]
                    );
                  }
                });
              }
            } else if (Number(hd_id) > 0) {
              rs = await this.axios.post(
                "web-hopdong/dichvu-ca/sp_lay_ds_hdtb_dv_ca",
                {
                  hdtb_id: hd_id,
                }
              );
              var dbtb_dv = rs.data.data;

              if (dbtb_dv && dbtb_dv.length > 0) {
                dt.forEach((dr) => {
                  var kiemtra = dbtb_dv.filter(
                    (item) => item.loaidv_id == dr["loaidv_dich_id"]
                  );
                  if (kiemtra.length > 0) {
                    this.$set(
                      this.ds_dv[dr["loaidv_dich_id"]],
                      "value",
                      Number(kiemtra[0]["donvi_id"])
                    );
                  }
                });
              }
            }
          });
        });
      } catch (err) {
        console.log(err);
      }
    },

    // ,gdvThueBao_FocusedRowChanged()
    // {

    // }

    async tsbtnGiaoPhieu_Click() {
      let rs = await this.axios.post(
        "web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id",
        { luong_id: this.vluong_id }
      );
      let dtTemp = rs.data.data;
      if (dtTemp.length > 0) {
        let data = {
          huonggiao: dtTemp[0].huonggiao_id,
          magd: this.$refs.txtMaGD.value
        };

        this.giaoPhieuViewDialog.input = {
          vhuonggiao_id: dtTemp[0].huonggiao_id,
          vma_gd: this.$refs.txtMaGD.value,
        };
        this.GiaoPhieuView_OnClick()
      } else {
        this.$toast.error("Chưa có hướng giao được gán");
      }
    },

    txtCMSUserName_KeyPress() {
      this.CMSTimKiemThueBaoHienHuu(
        this.$refs.txtCMSSerial.value,
        this.$refs.txtCMSTenChuThe.value,
        this.$refs.txtCMSEmail.value,
        this.$refs.txtCMSUserName.value,
        this.$refs.txtCMSMaSoThue.value
      );
    },

    txtCMSMaSoThue_KeyPress() {
      this.CMSTimKiemThueBaoHienHuu(
        this.$refs.txtCMSSerial.value,
        this.$refs.txtCMSTenChuThe.value,
        this.$refs.txtCMSEmail.value,
        this.$refs.txtCMSUserName.value,
        this.$refs.txtCMSMaSoThue.value
      );
    },

    //validate du lieu trước khi cập nhật
    ValidateControl() {
      this.ErrorText = {};
      this.ErrorControl = [];
      let rs = true;
      rs = this.Validate("txtUserName", this.$refs.txtUserName.value);
      rs = this.Validate("txtSerial", this.$refs.txtSerial.value);
      rs = this.Validate("txtTenTB", this.$refs.txtTenTB.value);
      // rs = this.Validate("dtpNgayBD", this.$refs.dtpNgayBD.value);
      // rs = this.Validate("dtpNgayKT", this.$refs.dtpNgayKT.value);
      rs = this.Validate("txtCTSID", this.$refs.txtCTSID.value);
      rs = this.Validate("txtThucTheID", this.$refs.txtThucTheID.value);
      rs = this.Validate("txtSDT", this.$refs.txtSDT.value);
      rs = this.Validate("txtEmail", this.$refs.txtEmail.value);
      rs = this.Validate("cboLoaiCTSId", this.$refs.cboLoaiCTSId.value);
      rs = this.Validate(
        "cboHinhThucSinhKhoaId",
        this.$refs.cboHinhThucSinhKhoaId.value
      );
      console.log(rs);
      return rs;
    },

    DongBoDuLieu() {
      if (this.$refs.ckbDongBoCMSUserName.checked)
        this.$refs.txtUserName.value = this.$refs.txtCMSUserName.value;
      if (this.$refs.ckbDongBoEmail.checked)
        this.$refs.txtEmail.value = this.$refs.txtCMSEmail.value;
      if (this.$refs.ckbDongBoIdThucThe.checked)
        this.$refs.txtThucTheID.value = this.$refs.txtIDThucTheCMS.value;
      if (this.$refs.ckbDongBoNgayBD.checked)
        this.$refs.dtpNgayBD.value = this.$refs.dtpCMSNgayBD.value;
      if (this.$refs.ckbDongBoNgayKT.checked)
        this.$refs.dtpNgayKT.value = this.$refs.dtpCMSNgayKT.value;
      if (this.$refs.ckbDongBoSDT.checked)
        this.$refs.txtSDT.value = this.$refs.txtCMSSoDT.value;
      if (this.$refs.ckbDongBoSerial.checked)
        this.$refs.txtSerial.value = this.$refs.txtCMSSerial.value;
      if (this.$refs.ckbDongBoSoThang.checked)
        this.$refs.txtSoThangSD.value = this.$refs.txtCMSSoThang.value;
      if (this.$refs.ckbDongBoTenChuThe.checked)
        this.$refs.txtTenTB.value = this.$refs.txtCMSTenChuThe.value;
      if (this.$refs.cbkDongBoIDCTS.checked)
        this.$refs.txtCTSID.value = this.$refs.txtIDChungThuSoCMS.value;
    },
    Validate(control, value) {
      switch (control) {
        case "txtUserName":
          if (value == "") {
            this.ErrorText.txtUserName =
              "Bạn không được để trống trường Username";
            this.ErrorControl.push("txtUserName");
            return false;
          } else {
            if (value != this.$refs.txtCMSUserName.value) {
              this.ErrorText.txtUserName =
                "Không khớp thông tin user name với CMS";
              this.ErrorControl.push("txtUserName");
              return false;
            }
          }
          break;
        case "txtCTSID":
          if (value == "") {
            this.ErrorText.txtCTSID =
              "Bạn không được để trống trường Chứng thư số ID";
            this.ErrorControl.push("txtCTSID");
            return false;
          } else {
            if (value != this.$refs.txtIDChungThuSoCMS.value) {
              this.ErrorText.txtCTSID =
                "Không khớp thông tin chứng thư số ID với CMS";
              this.ErrorControl.push("txtCTSID");
              return false;
            }
          }
          break;
        case "txtThucTheID":
          if (value == "") {
            this.ErrorText.txtThucTheID =
              "Bạn không được để trống trường ID thực thể";
            this.ErrorControl.push("txtThucTheID");
            return false;
          } else {
            if (value != this.$refs.txtIDThucTheCMS.value) {
              this.ErrorText.txtThucTheID =
                "Không khớp thông tin ID thực thể với CMS";
              this.ErrorControl.push("txtThucTheID");
              return false;
            }
          }
          break;
        case "txtSerial":
          if (value == "") {
            this.ErrorText.txtSerial = "Bạn không được để trống trường Serial";
            this.ErrorControl.push("txtSerial");
            return false;
          } else {
            if (value != this.$refs.txtCMSSerial.value) {
              this.ErrorText.txtSerial = "Không khớp thông tin Serial với CMS";
              this.ErrorControl.push("txtSerial");
              return false;
            }
          }
          break;
        case "txtTenTB":
          if (value == "") {
            this.ErrorText.txtTenTB =
              "Bạn không được để trống trường tên thuê bao";
            this.ErrorControl.push("txtTenTB");
            return false;
          } else {
            if (value != this.$refs.txtCMSTenChuThe.value) {
              this.ErrorText.txtTenTB =
                "Không khớp thông tin tên thuê bao với tên chủ thể CMS";
              this.ErrorControl.push("txtTenTB");
              return false;
            }
          }
          break;
        case "dtpNgayBD":
          if (value == "") {
            this.ErrorText.dtpNgayBD =
              "Bạn không được để trống trường ngày bắt đầu";
            this.ErrorControl.push("dtpNgayBD");
            return false;
          } else {
            if (value != this.$refs.dtpCMSNgayBD.value) {
              this.ErrorText.dtpNgayBD = "Không khớp thông tin ngày bắt đầu";
              this.ErrorControl.push("dtpNgayBD");
              return false;
            }
          }
          break;
        case "dtpNgayKT":
          if (value == "") {
            this.ErrorText.dtpNgayKT =
              "Bạn không được để trống trường ngày kết thúc";
            this.ErrorControl.push("dtpNgayKT");
            return false;
          } else {
            if (value != this.$refs.dtpCMSNgayKT.value) {
              this.ErrorText.dtpNgayKT = "Không khớp thông tin ngày kết thúc";
              this.ErrorControl.push("dtpNgayKT");
              return false;
            }
          }
          break;
        case "txtEmail":
          if (value == "") {
            this.ErrorText.txtEmail = "Bạn không được để trống trường email";
            this.ErrorControl.push("txtEmail");
            return false;
          } else {
            if (value != this.$refs.txtCMSEmail.value) {
              this.ErrorText.txtEmail = "Không khớp thông tin email";
              this.ErrorControl.push("txtEmail");
              return false;
            }
          }
          break;
        case "txtSDT":
          if (value == "") {
            this.ErrorText.txtSDT =
              "Bạn không được để trống trường số điện thoại";
            this.ErrorControl.push("txtSDT");
            return false;
          } else {
            if (value != this.$refs.txtCMSSoDT.value) {
              this.ErrorText.txtSDT = "Không khớp thông tin số điện thoại";
              this.ErrorControl.push("txtSDT");
              return false;
            }
          }
          break;
        case "cboHinhThucSinhKhoaId":
          if (value == "") {
            this.ErrorText.cboHinhThucSinhKhoaId =
              "Bạn không được để trống trường hình thức sinh khóa";
            this.ErrorControl.push("cboHinhThucSinhKhoaId");
            return false;
          }
          break;
        case "cboLoaiCTSId":
          if (value == "") {
            this.ErrorText.cboLoaiCTSId =
              "Bạn không được để trống trường loại chứng thư số";
            this.ErrorControl.push("cboLoaiCTSId");
            return false;
          }
          break;
        default:
          break;
      }
      return true;
    },

    tstDongBoCMS_Click() {
      this.DongBoDuLieu();
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
<style scoped>
.hl {
  background: #fbf5e6;
}
</style>

<style>
.hl4,
.highlight .vue-treeselect__control,
.highlight input,
.highlight .select2-selection {
  background: #f5dc9a;
}
.collapsed.fa-angle-up,
.not-collapsed.fa-angle-down {
  display: none !important;
}

.hoso-dientu .page-content {
  position: unset;
}

.popupGiaophieu .main-wrapper {
  position: unset !important; 
}
</style>