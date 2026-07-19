<template src="./InterDividePointInstallTSL.html"></template>
<script scoped src="./InterDividePointInstallTSL.scss"></script>

<script>
import DanhSachThueBao from "./part/DanhSachThueBao";
import DiemDauDiemCuoi from "./part/DiemDauDiemCuoi";
import ThongTinGiaoViec from "./part/ThongTinGiaoViec";
import ThongTinHopDong from "./part/ThongTinHopDong";
import giaophieunhanvien from "@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue";
import CapVatTuTB from "@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue"
import captrunggian from "@/modules/CABMAN/nhap-captrunggian-cho-thuebaotsl";

import api from "./InterDividePointInstallTSLAPI.js";
import utilsJS from "./UtilsJS";
import moment from "moment";
export default {
  components: {
    DanhSachThueBao,
    DiemDauDiemCuoi,
    ThongTinGiaoViec,
    ThongTinHopDong,
    giaophieunhanvien,
    captrunggian,
    CapVatTuTB
  },
  data() {
    return {
      dialogComponent: null,
      dialogEvts: {
        'close': this.dlgClose
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close':this.popupClosed,
      },
      position: { X: 'center', Y: 'top' },
      moment,
      showModalTrungGian: true,
      header: {
        title: "ĐIỂM CHIA TRUNG GIAN XỬ LÝ PHIẾU THI CÔNG",
        list: [
          // Cập nhật KQTC  -> Điểm chia trung gian - Thi công TSL
          { name: "Cập nhật KQTC", link: { name: "Ui.cards" } },
          {
            name: "Điểm chia trung gian - Thi công TSL",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      state: {},
      form_button: {
        isLayThongTin: false,
        layThongTin: true,
        capNhat: true,
        giaoViec: true,
        vatTu: true,
        hoanThanh: false,
        traPhieu: true,
        nhapCapTrungGian: true,
      },
      form_thongTinHopDong: {
        phieu_id: null,
        loaiHD: null,
        maGiaoDich: null,
        account: null,
        maGiaoDichSearch: null,
        accountSearch: null,
        kieuLapDat: null,
        loaiHinh: null,
        tocDoKenh: null,
        tenThueBao: null,
        diaChiTB: null,
        donViGiao: null,
        nhanVienGiao: null,
        noiDungGiao: null,
      },
      form_thongTinGiaoViec: {
        loaiHinh: null,
        currentSelectRecord: {},
        phieu_id: null,
        ngayGV: null,
        ngayHT: null,
        nguoiGV: null,
        nhanVienYC: null,
        noiDungTH: null,
        lyDoTra: null,
        noiDungTra: null,
        table_DSNV: {
          currentRecord: null,
          data: null,
          loading: false,
          options: [],
          config: [
            {
              label: "Tên nhân viên",
              filterAble: true,
              key: "ten_nv",
            },
            {
              label: "Số điện thoại",
              filterAble: true,
              key: "so_dt",
            },
            {
              label: "Nhiệm vụ",
              filterAble: true,
              key: "nhiemvu",
            },
            {
              label: "Ghi chú",
              filterAble: true,
              key: "ghichu",
            },
          ],
        },
      },
      isForm_diemDauDiemCuoi: false,
      form_diemDauDiemCuoi: {
        diemDau_donVi: null,
        diemDau_soAo: null,
        diemDau_diaChiMoi: null,
        diemCuoi_donVi: null,
        diemCuoi_soAo: null,
        diemCuoi_diaChiMoi: null,
        optionsDonVi: [],
      },
      form_danhSachThueBao: {
        dshuonggiao_id: "207",
        loaihd_id: 1,
        table: {
          currentRecord: null,
          data: null,
          loading: false,
          options: [],
          config: [
            {
              label: "Số máy/Acc",
              filterAble: true,
              key: "ma_tb",
            },
            {
              label: "Trạng thái hợp đồng",
              filterAble: true,
              key: "trangthai_hd",
            },
            {
              label: "Tên thuê bao",
              filterAble: true,
              key: "ten_tb",
            },
            {
              label: "Địa chỉ thuê bao",
              filterAble: true,
              key: "diachi_tb",
            },
            {
              label: "Ngày YC",
              filterAble: true,
              key: "ngay_yc",
            },
            {
              label: "Ngày giao",
              filterAble: true,
              key: "ngaygiao",
            },
            {
              label: "Đơn vị nhận",
              filterAble: true,
              key: "ten_dv_nhan",
            },
          ],
        },
      },
      nhom_qt_id: 1,
      quytrinh_id: 0,
      dichvuvt_id: 1,
      loadingDiem: true,
      modelVatTu: {}
    };
  },
  watch: {
    "form_thongTinHopDong.phieu_id"(newValue, oldValue) {
      this.form_thongTinGiaoViec.phieu_id = newValue;
      if (utilsJS.isNullOrEmpty(newValue)) {
        this.form_thongTinGiaoViec.table_DSNV.data = [];
      } else {
        this.form_thongTinGiaoViec.table_DSNV.loading = true;
        api
          .getDanhSachNhanVienTheoPhieuID(this.axios, {
            kieu_id: 0,
            phieu_id: newValue,
          })
          .then((response) => {
            this.form_thongTinGiaoViec.table_DSNV.loading = false;

            if (response.data.message == "Success") {
              this.form_thongTinGiaoViec.table_DSNV.data = response.data.data;
            } else {
              this.form_thongTinGiaoViec.table_DSNV.data = [];
            }
          })
          .catch((error) => {
            this.form_thongTinGiaoViec.table_DSNV.loading = false;
            this.form_thongTinGiaoViec.table_DSNV.data = [];
            if (!error.response.data.message) {
              this.$toast.error(error.response.data.message);
            }
          });
      }
    },
    async "form_thongTinHopDong.loaiHinh"(newValue, oldValue) {
      this.loadingDiem = false;
      this.form_thongTinGiaoViec.loaiHinh = newValue;
      if (this.form_thongTinGiaoViec.loaiHinh) {
        this.form_diemDauDiemCuoi.optionsDonVi = await this.$store.dispatch(
          "donViCommon/getDanhSachDonVi",
          { loaidv_id: this.form_thongTinGiaoViec.loaiHinh }
        );
      } else {
        this.form_diemDauDiemCuoi.optionsDonVi = [];
      }

      this.loadingDiem = true;
    },
    "form_thongTinGiaoViec.table_DSNV.data"(newValue, oldValue) {
      this.form_thongTinGiaoViec.table_DSNV.options = newValue;
    },
    "form_thongTinHopDong.loaiHD"(newValue, oldValue) {
      this.$root.showLoading(true);

      this.form_thongTinHopDong.maGiaoDichSearch = null;
      this.form_thongTinHopDong.accountSearch = null;
      this.form_danhSachThueBao.table.loading = true;
      this.getDanhSachHuongGiao();
    },
    "form_danhSachThueBao.table.data"(newValue, oldValue) {
      this.form_danhSachThueBao.table.options = newValue.map((e) => {
        var newObj = {};
        newObj = e;
        newObj["ngay_yc"] = moment(e.ngay_yc, 'DD/MM/YYYY HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");        
        return newObj;
      });
    },
    "form_danhSachThueBao.table.options"(newValue, oldValue) {
      if (newValue != undefined && newValue != null && newValue.length > 0) {
        this.form_danhSachThueBao.table.currentRecord = newValue[0];
      } else {
        this.form_danhSachThueBao.table.currentRecord = null;
      }
    },
    "form_danhSachThueBao.table.currentRecord"(newValue, oldValue) {
      if (newValue != undefined && newValue != null) {
        this.form_thongTinHopDong.phieu_id = newValue.phieu_id;
        this.form_thongTinHopDong.maGiaoDich = newValue.ma_gd;
        this.form_thongTinHopDong.account = newValue.ma_tb;
        this.form_thongTinHopDong.kieuLapDat = newValue.ten_kieuld;
        this.form_thongTinHopDong.loaiHinh = newValue.loaitb_id;
        this.form_thongTinHopDong.loaitb_id = newValue.loaitb_id;
        this.form_thongTinHopDong.tocDoKenh = null;
        this.form_thongTinHopDong.tenThueBao = newValue.ten_tb;
        this.form_thongTinHopDong.diaChiTB = newValue.diachi_tb;
        this.form_thongTinHopDong.donViGiao = newValue.donvi_giao;
        this.form_thongTinHopDong.nhanVienGiao = newValue.nhanvien_giao;
        this.form_thongTinHopDong.noiDungGiao = newValue.nd_giao;
        this.form_thongTinHopDong.thuebao_id = newValue.thuebao_id;

        /* this.form_diemDauDiemCuoi.diemDau_donVi = newValue.donvi_dau_id; */
        this.form_diemDauDiemCuoi.diemDau_soAo = null;
        /* this.form_diemDauDiemCuoi.diemDau_diaChiMoi = null; */
        /* (this.form_diemDauDiemCuoi.diemCuoi_donVi = newValue.donvi_cuoi_id), */
          (this.form_diemDauDiemCuoi.diemCuoi_soAo = null);
        /* this.form_diemDauDiemCuoi.diemCuoi_diaChiMoi = null; */
        if(newValue.ngay_ht_gp)
          this.form_button.hoanThanh = true;
        else
          this.form_button.hoanThanh = false;
      } else {
        this.form_thongTinHopDong.phieu_id = null;
        this.form_thongTinHopDong.maGiaoDich = !this.form_thongTinHopDong
          .maGiaoDichSearch
          ? this.form_thongTinHopDong.maGiaoDichSearch
          : null;
        this.form_thongTinHopDong.account = !this.form_thongTinHopDong
          .accountSearch
          ? this.form_thongTinHopDong.accountSearch
          : null;
        this.form_thongTinHopDong.kieuLapDat = null;
        this.form_thongTinHopDong.loaiHinh = null;
        this.form_thongTinHopDong.tocDoKenh = null;
        this.form_thongTinHopDong.tenThueBao = null;
        this.form_thongTinHopDong.diaChiTB = null;
        this.form_thongTinHopDong.donViGiao = null;
        this.form_thongTinHopDong.nhanVienGiao = null;
        this.form_thongTinHopDong.noiDungGiao = null;
        this.form_diemDauDiemCuoi = {
          diemDau_donVi: null,
          diemDau_soAo: null,
          diemDau_diaChiMoi: null,
          diemCuoi_donVi: null,
          diemCuoi_soAo: null,
          diemCuoi_diaChiMoi: null,
          optionsDonVi: [],
        };
        this.form_button.hoanThanh = false;
      }
    },
  },
  mounted() {},
  methods: {
    rowSelected(item) {
      this.form_danhSachThueBao.table.currentRecord = item;
    },
    getDanhSachHuongGiao() {
      api
        .getDanhSachHuongGiao(this.axios, {
          nguoidung_id: this.$root.token.getNguoiDungID(),
          nhom_qt_id: this.nhom_qt_id,
          quytrinh_id: this.quytrinh_id,
          loaihd_id: this.form_thongTinHopDong.loaiHD,
          dichvuvt_id: this.dichvuvt_id,
        })
        .then((response) => {
          if (response.data.message == "Success") {
            var dshuonggiao_id = [];
            response.data.data.map((e) => {
              dshuonggiao_id = [...dshuonggiao_id, e.huonggiao_id];
            });
            if (dshuonggiao_id.length > 0) {
              api
                .getDanhSachThueBao(this.axios, {
                  loaihd_id: this.form_thongTinHopDong.loaiHD,
                  dshuonggiao_id: dshuonggiao_id.join(),
                })
                .then((response) => {
                  if (response.data.message == "Success") {
                    this.form_danhSachThueBao.table.data = response.data.data;
                  } else {
                    this.form_danhSachThueBao.table.data = [];
                  }
                })
                .catch((error) => {
                  this.form_danhSachThueBao.table.data = [];
                  if (!error.response.data.message) {
                    this.$toast.error(error.response.data.message);
                  }
                })
                .finally(() => {
                  this.form_danhSachThueBao.table.loading = false;
                  this.$root.showLoading(false);
                });
            }
          } else {
            this.form_danhSachThueBao.table.data = [];

            this.form_thongTinHopDong.phieu_id = null;
            this.form_thongTinHopDong.maGiaoDich = !this.form_thongTinHopDong
              .maGiaoDichSearch
              ? this.form_thongTinHopDong.maGiaoDichSearch
              : null;
            this.form_thongTinHopDong.account = !this.form_thongTinHopDong
              .accountSearch
              ? this.form_thongTinHopDong.accountSearch
              : null;
            this.form_thongTinHopDong.kieuLapDat = null;
            this.form_thongTinHopDong.loaiHinh = null;
            this.form_thongTinHopDong.tocDoKenh = null;
            this.form_thongTinHopDong.tenThueBao = null;
            this.form_thongTinHopDong.diaChiTB = null;
            this.form_thongTinHopDong.donViGiao = null;
            this.form_thongTinHopDong.nhanVienGiao = null;
            this.form_thongTinHopDong.noiDungGiao = null;
            this.form_diemDauDiemCuoi = {
              diemDau_donVi: null,
              diemDau_soAo: null,
              diemDau_diaChiMoi: null,
              diemCuoi_donVi: null,
              diemCuoi_soAo: null,
              diemCuoi_diaChiMoi: null,
              optionsDonVi: [],
            };

            this.form_danhSachThueBao.table.loading = false;
            this.$root.showLoading(false);
          }
        })
        .catch((error) => {
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        });
    },
    getDanhSanhTable() {
      this.form_thongTinHopDong.maGiaoDichSearch = null;
      this.form_thongTinHopDong.accountSearch = null;
      this.form_danhSachThueBao.table.loading = true;
      api
        .getDanhSachThueBao(this.axios, {
          loaihd_id: this.form_thongTinHopDong.loaiHD,
          dshuonggiao_id: this.form_danhSachThueBao.dshuonggiao_id,
        })
        .then((response) => {
          this.form_danhSachThueBao.table.loading = false;
          if (response.data.message == "Success") {
            this.form_danhSachThueBao.table.data = response.data.data;
          } else {
            this.form_danhSachThueBao.table.data = [];
          }
        })
        .catch((error) => {
          this.form_danhSachThueBao.table.loading = false;
          this.form_danhSachThueBao.table.data = [];
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        });
    },
    onSelectLyDoTraThongtGiaoViec(e) {
      this.form_thongTinGiaoViec.lyDoTra = e;
    },
    async onCheckedTable(data) {
      console.log(data);
      this.loadingDiem = false;
      if (data.hdtb_id) {
          this.form_diemDauDiemCuoi.diemDau_diaChiMoi = data.diachi_tb;
        this.form_diemDauDiemCuoi.diemCuoi_diaChiMoi = data.diachi_tb;
        this.form_thongTinGiaoViec.nhanVienYC = data.nhanvien_yc;
        this.form_thongTinGiaoViec.noiDungTH = data.nd_thuchien;
        data.ngay_ht_gp
          ? (this.form_button.hoanThanh = true)
          : (this.form_button.hoanThanh = false);

        api
          .getHopDongTSLDiemDau(this.axios, { hdtb_id: data.hdtb_id })
          .then((response) => {
            if (response.data.message == "Success") {
              this.form_diemDauDiemCuoi.diemDau_donVi =
                response.data.data[0].donvi_id;
            }
          })
          .catch((error) => {
            if (!error.response.data.message) {
              this.$toast.error(error.response.data.message);
            }
          });
        api
          .getHopDongTSLDiemCuoi(this.axios, { hdtb_id: data.hdtb_id })
          .then((response) => {
            if (response.data.message == "Success") {
              this.form_diemDauDiemCuoi.diemCuoi_donVi =
                response.data.data[0].donvi_id;
            }
          })
          .catch((error) => {
            if (!error.response.data.message) {
              this.$toast.error(error.response.data.message);
            }
          });

      }
  console.log(this.form_diemDauDiemCuoi)
   this.$nextTick(() =>  this.loadingDiem = true)

    },
    onRowThongtGiaoViec(data) {
      this.form_thongTinGiaoViec.currentSelectRecord = data;
    },
    async btnLayThongTinOnClick() {
      // location.reload();
      this.$root.showLoading(true);
      this.form_danhSachThueBao.table.loading = true;

      await this.getDanhSachHuongGiao();
    },
    btnCapNhatOnClick() {
      if (this.$refs.thongTinGiaoViec.$refs.ngayGV.disable_value) {
        return this.$toast.error("Hãy chọn ngày giao việc!");
      }
      if (this.$refs.thongTinGiaoViec.$refs.ngayHT.disable_value) {
        return this.$toast.error("Hãy nhập ngày hoàn thành!");
      }

      if (
        this.form_thongTinGiaoViec.ngayGV >= this.form_thongTinGiaoViec.ngayHT
      ) {
        return this.$toast.error("Ngày HT phải lớn hơn Ngày GV!");
      }

      // console.log(moment());

      // if () {
      //   return this.$toast.error(
      //     "Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại!"
      //   );
      // }

      if (
        Object.keys(this.form_thongTinGiaoViec.currentSelectRecord).length == 0
      ) {
        return this.$toast.error("Hãy chọn nhân viên");
      }
      const data = {
        ngay_gv: this.form_thongTinGiaoViec.ngayGV,
        ngay_th: this.form_thongTinGiaoViec.ngayHT,
        nhanvien_gv_id: this.form_thongTinGiaoViec.nguoiGV,
        nhanvien_th_id:
          this.form_thongTinGiaoViec.currentSelectRecord.nhanvien_th_id,
        phieu_id: this.form_thongTinGiaoViec.phieu_id,
      };
      api
        .updateTTPhieu(this.axios, data)
        .then((response) => {
          if (response.data.message == "Success") {
            this.$toast.success(
              "Cập nhật dữ liệu lên viễn thông tỉnh thành công!"
            );
          }
        })
        .catch((error) => {
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        });
    },
    async btnGiaoViecOnClick() {
      if (this.form_danhSachThueBao.table.options.length == 0) {
        return this.$toast.error(
          "Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại!"
        );
      }
      if (!this.form_thongTinGiaoViec.nguoiGV) {
        return this.$toast.error("Hãy nhập nhân viên giao việc!");
      }
      if (this.$refs.thongTinGiaoViec.$refs.ngayGV.disable_value) {
        return this.$toast.error("Hãy chọn ngày giao việc!");
      }
       
      let vnhanvien_giao_id = Number(this.form_thongTinGiaoViec.nguoiGV);
      let vngaygiao = moment(this.form_thongTinGiaoViec.ngayGV, "DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY HH:mm:ss");
      console.log("vngaygiao", vngaygiao);
 
      this.state.modalTitle = "Giao phiếu nhân viên";
      this.dialogComponent = () => import("@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV.vue");
      this.popupComponentName = "frnGiaoPhieuNV"; 
      this.popupComponentEvts["giaoviec_success"] = this.giaoviec_success;
      //this.Popup("modalComponentsITVAS");
      await this.dialogComponent().then((x) => {});
      this.$nextTick(() => { 
        this.$refs.generalComponent.frnGiaoPhieuNV(this.form_thongTinHopDong.phieu_id, this.$root.token.getNhanVienID(), vnhanvien_giao_id, vngaygiao);
        this.$refs.generalComponent.vma_tb = this.form_thongTinHopDong.account;
        this.$refs.generalComponent.vhdtb_id = this.form_thongTinHopDong.hdtb_id;
        this.$refs.generalComponent.huonggiao_id = this.form_danhSachThueBao.dshuonggiao_id; 
        this.$refs.generalComponent.load();  
        this.$refs.generalDialog.show();  
      });        
    },
    btnVatTuOnClick() {
      if (!this.form_danhSachThueBao.table.currentRecord) {
        return this.$toast.error("Bạn chưa chọn phiếu!");
      }
      console.log(this.form_danhSachThueBao.table.currentRecord)
      let data = this.form_danhSachThueBao.table.currentRecord;
      let inphieuid = Number.parseInt(this.form_thongTinHopDong.phieu_id)
      let inhdtbid = Number.parseInt(data.hdtb_id) 
      this.modelVatTu.hdtb_id = inhdtbid
      this.modelVatTu.phieu_id = inphieuid
      this.modelVatTu.loaihd_id = this.form_thongTinHopDong.loaiHD
      this.modelVatTu.thuebao_id = data.thuebao_id
      this.modelVatTu.loaitb_id = this.form_thongTinHopDong.loaitb_id*1
      console.log("btnVatTuOnClick", this.modelVatTu);
      this.$refs.popupCapVatTuTB.openDialog()
    },
    btnHoanThanhOnClick() {
      if (this.form_thongTinHopDong.phieu_id) {
        api
          .updateTrangThaiPhieu(this.axios, {
            phieu_id: this.form_thongTinHopDong.phieu_id,
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.$toast.success("Cập nhật dữ liệu thành công!");
              this.btnLayThongTinOnClick();
            }
          })
          .catch((error) => {
            if (!error.response.data.message) {
              this.$toast.error(error.response.data.message);
            }
          });
      }
    },
    isValidForm() {
      this.$refs.thongTinGiaoViec.$refs.lyDoTra.checkValidate();
      if (!this.$refs.thongTinGiaoViec.$refs.lyDoTra.value) {
        this.$toast.error("Vui lòng chọn lý do trả!");

        return false;
      }
      this.$refs.thongTinGiaoViec.$refs.noiDungTra.checkValidate();
      if (!this.$refs.thongTinGiaoViec.$refs.lyDoTra.value) {
        this.$toast.error("Vui lòng nhập lý do trả!");

        return false;
      }
      return true;
    },
    btnTraPhieuOnClick() {
      if (!this.isValidForm()) return;

      if (this.form_thongTinHopDong.phieu_id) {
        api
          .updateTraPhieu(this.axios, {
            lydotra: this.form_thongTinGiaoViec.lyDoTra,
            ndtra: this.form_thongTinGiaoViec.noiDungTra,
            phieu_id: this.form_thongTinHopDong.phieu_id,
          })
          .then((response) => {
            if (response.data.message == "Success") {
              this.$toast.success("Cập nhật dữ liệu thành công!");
              this.form_thongTinGiaoViec.lyDoTra = -1;
              this.form_thongTinGiaoViec.noiDungTra = null;
            }
          })
          .catch((error) => {
            if (!error.response.data.message) {
              this.$toast.error(error.response.data.message);
            }
          });
      } else {
        this.$toast.error("Hãy chọn thuê bao");
      }
    },
    btnNhapCapTrungGianOnClick() {
      this.$refs.captrunggian.show();

      const note = document.querySelector(".captrunggian");
      note.style.zIndex = "1000";
    },
    update_ThongTinHopDong_loaiHD(newValue) {
      this.form_thongTinHopDong.loaiHD = newValue;
    },
    filterDanhSachThueBaoByAccount(value) {
      this.form_thongTinHopDong.accountSearch = value.trim().toLowerCase();
      this.form_danhSachThueBao.table.options =
        this.form_danhSachThueBao.table.data.filter(
          (x) => x.ma_tb.toLowerCase().indexOf(value.trim().toLowerCase()) > -1
        );
    },
    filterDanhSachThueBaoByMaGiaoDich(value) {
      this.form_thongTinHopDong.maGiaoDichSearch = value.trim().toLowerCase();
      this.form_danhSachThueBao.table.options =
        this.form_danhSachThueBao.table.data.filter(
          (x) => x.ma_gd.toLowerCase().indexOf(value.trim().toLowerCase()) > -1
        );
    },
    async dlgClose(e){
      console.log("dlgClose", e);      
    },
    async popupClosed(e){
      console.log("popupClosed", e);      
    }, 
    async giaoviec_success(){  
      console.log("giaoviec_success"); 
      this.form_thongTinGiaoViec.table_DSNV.loading = true;
      api
        .getDanhSachNhanVienTheoPhieuID(this.axios, {
          kieu_id: 0,
          phieu_id: this.form_thongTinGiaoViec.phieu_id,
        })
        .then((response) => {
          this.form_thongTinGiaoViec.table_DSNV.loading = false;

          if (response.data.message == "Success") {
            this.form_thongTinGiaoViec.table_DSNV.data = response.data.data;
          } else {
            this.form_thongTinGiaoViec.table_DSNV.data = [];
          }
        })
        .catch((error) => {
          this.form_thongTinGiaoViec.table_DSNV.loading = false;
          this.form_thongTinGiaoViec.table_DSNV.data = [];
          if (!error.response.data.message) {
            this.$toast.error(error.response.data.message);
          }
        });  
    }, 
  },
};
</script>
