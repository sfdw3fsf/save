<template src="./TiepNhanPhanAnhKhachHang.html"></template>
<style src="./TiepNhanPhanAnhKhachHang.scss"></style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import select2 from "@/components/Select2.vue";
import TiepNhanPhanAnhKhachHangApi from "./../api/TiepNhanPhanAnhKhachHangApi";
import TraCuuKhachHangLonApi from "./../api/TraCuuKhachHangLonApi";
import KyCuoc from "@/components/KyCuoc.vue";
import EventBus from "@/utils/eventBus";
import YesNoModal from "../utils/YesNoModal.vue";
import TraCuuLichSuPhanAnh from "../TraCuuLichSuPhanAnh"
import CapNhapTTLH from "../TraCuuKhachHangLon/popup/CapNhapTTLH.vue"
// import frmTraCuuLSPA from "./popup/frmTraCuuLSPA.vue"
import frmTraCuuLSPA from "./popup/popupTraCuuLSPA.vue"
import { currency } from '@/filters/currency'
import downloadexcel from "vue-json-excel";
import SearchAccount from "@/modules/search/subscriber/SearchAccount";

import Vue from 'vue'
import { L10n, setCulture } from '@syncfusion/ej2-base';
import { GridPlugin, Filter, Resize, ExcelExport, Sort, Selection, Group } from "@syncfusion/ej2-vue-grids"

setCulture('vi-VN');

L10n.load({
    'vi-VN': {
        'grid': {
            'EmptyRecord': 'Không có bản ghi nào để hiển thị'
        }
    }
});

Vue.use(GridPlugin)

export default {
  components: {
    breadcrumb,
    appSelect2: select2,
    KyCuoc,
    confirmPopup: YesNoModal,
    appTraCuuLichSuPhanAnh: TraCuuLichSuPhanAnh,
    appCapNhapTTLH: CapNhapTTLH,
    appTraCuuLSPA: frmTraCuuLSPA,
    downloadexcel,
    SearchAccount
  },
  provide: {
    grid: [Group]
  },
  data() {
    return {
      header: {
        title: "TIẾP NHẬN PHẢN ÁNH KHÁCH HÀNG",
        list: [
          { name: "Chăm sóc khách hàng", link: { name: "Ui.cards" } },
          {
            name: "Xử lý thông tin phản ánh",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tiếp nhận phản ánh khách hàng",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      btnStatus: {
        btnNhapMoiEnabled: false,
        btnGhiLaiEnabled: false,
        btnXoaEnabled: false,
        btnHuyBoEnabled: false,
        txtNoiDungGQEnabled: false,
        btnKhoaPhieuEnabled: false,
        btnCapNhapLienHeEnabled: false,
        btnXuaXLSEnabled: true,
        btnTraCuuLSEnabled: true,
        btnLienHeVisible: false,
      },
      optStatus: {
        optNganhNgheEnabled: false,
        optLoaiKHEnabled: false,
        optLoaiKHLonEnabled: false,
        optHinhThucKNEnabled: true,
        optNoiDungPAEnabled: true,
      },
      datePickerStatus: {
        ngayTLTuEnabled: false,
        ngayTLDenEnabled: false,
        ngayTNEnabled: false,
      },
      thongtinKyHoaDon: {
        kyHoaDonSelected: null,
        chuKySelected: null,
      },
      params: {
        pOptNganhNghe: "",
        pOptLoaiKH: "",
        pOptLoaiKHLon: "",
        pOptHinhThucKN: "",
        pOptNoiDungPA: "",
        pTxtMaKH: "",
        ngayTiepNhan: new Date(),
        ngayTLDen: new Date(),
        ngayTLTu: new Date(),
        pTxtTenKH: "",
        pTxtDiaChi: "",
        pTxtNguoiLH: "",
        pTxtDienThoaiLH: "",
        pTxtChiTiet: "",
        pTxtGhiChu: "",
        pTxtNoiDungXL: "",
        pKhachHangId: 0,
        pThanhToanId: "",
        pThueBaoId: "",
        pMaKH: "",
        pMaTT: "",
        pPhanAnhId: 0,
      },
      gridData: {
        lichSuPhanAnh: [],
      },
      onSelected: {
        phanAnh: null,
      },
      TRANGTHAI_PHAN_ANH: {
        MOI_TIEP_NHAN: 1,
        DA_GIAI_QUYET: 6,
        PHAN_ANH_TON: 7,
      },
      today: new Date(),
      tableColumns: {
        dsChiTietNo: [
          {
            fieldName: "STT",
            headerText: "STT",
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: "MA_TT",
            headerText: "Mã TT",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "MATB_DD",
            headerText: "Mã đại diện",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "TEN_TT",
            headerText: "Tên thanh toán",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "DIACHI_TT",
            headerText: "Địa chỉ thanh toán",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NO_DK_TEXT",
            headerText: "Nợ ĐK",
            allowFiltering: true,
            allowSorting: false,
            cssClass: "txt-nodk",
          },
          {
            fieldName: "NO_PS_TEXT",
            headerText: "Nợ PS",
            allowFiltering: true,
            allowSorting: false,
            cssClass: "txt-nops",
          },
          {
            fieldName: "TONG_NO_TEXT",
            headerText: "Tổng nợ",
            allowFiltering: true,
            allowSorting: false,
            cssClass: "txt-tongno",
          },
          {
            fieldName: "GHICHU",
            headerText: "Ghi chú",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "HINHTHUC_TT",
            headerText: "Hình thức TT",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "MAIN_GHEP",
            headerText: "Mã in ghép",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
        dsLichSuPhanAnh: [
          { fieldName: "NGAYTIEPNHAN", headerText: "Ngày tiếp nhận", allowFiltering: true, allowSorting: false, },
          { fieldName: "NGAY_GQ", headerText: "Ngày giải quyết", allowFiltering: true, allowSorting: false, },
          { fieldName: "TRANGTHAI_PA", headerText: "Trạng thái", allowFiltering: true, allowSorting: false, },
        ],
        dsPhanAnh: [
          {
            fieldName: "MA_KH",
            headerText: "Mã KH",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGAY_LH",
            headerText: "Ngày LH",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NGUOI_LH",
            headerText: "Người LH",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "NOI_DUNG",
            headerText: "Nội dung",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "DIENTHOAI_LH",
            headerText: "Điện thoại LH",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "TEN_NV",
            headerText: "Nhân viên TN",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
      },
      styles: {
        ttno: {},
        lspa: {},
        kqxl: {}
      },
      popupComponent: null,
      exportExcelFields: {
        "Mã KH": "MA_KH",
        "Ngày LH": "NGAY_LH",
        "Người LH": "NGUOI_LH",
        "Nội dung": "NOI_DUNG",
        "Điện thoại LH": "DIENTHOAI_LH",
        "Nhân viên TN": "TEN_NV",
      },
      groupOptions: {showDropArea: false, columns: ['NOIDUNGPA'], captionTemplate: '${key}'},
      selectionOptions: { persistSelection: true, type: 'Multiple', checkboxOnly: true },
      pageOptions: { pageSize: 10, pageCount: 5, pageSizes: true, totalRecordCount : 1000},
      filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
      templateOptions : {
        create: function (args) {
            return '<span class="fa"></span>'
        },
        write: function (args) {}
    },
    };
  },
  computed: {
    ...mapState("tiepnhanphananhkhachhang", [
      "dsNganhNghe",
      "dsLoaiKH",
      "dsLoaiKHLon",
      "dsHinhThucKN",
      "dsNoiDungPA",
      "dsPAMoiTiepNhanTheoNV",
    ]),
    ...mapGetters("tracuukhachhanglon", ["dsThanhToanGetter"]),
    countSLPAMoiTiepNhanTheoNV() {
      return this.dsPAMoiTiepNhanTheoNV.length;
    },
    upToDate() {
      let tmp = new Date();
      tmp.setDate(this.today.getDate() + 2);
      return tmp;
    },
  },
  async created() {
    EventBus.$on("chukycuocChange", this.chukycuocChangeHandler);
  },
  async mounted() {
    this.loading(true);
    this.clearData();
    await Promise.all([
      this.getDsNganhNghe(),
      this.getDsLoaiKH(),
      this.getDsLoaiKHLon(),
      this.getDsHinhThucKN(),
      this.getDsNoiDungPA(),
    ])
      .then(() => {
        if (this.dsNganhNghe.length > 0)
          this.params.pOptNganhNghe = this.dsNganhNghe[0].id;

        if (this.dsLoaiKH.length > 0)
          this.params.pOptLoaiKH = this.dsLoaiKH[0].id;

        if (this.dsLoaiKHLon.length > 0)
          this.params.pOptLoaiKHLon = this.dsLoaiKHLon[0].id;

        if (this.dsNoiDungPA.length > 0)
          this.params.pOptNoiDungPA = this.dsNoiDungPA[0].id;

        if (this.dsHinhThucKN.length > 0)
          this.params.pOptHinhThucKN = this.dsHinhThucKN[0].id;
        this.loading(false);
      })
      .catch((e) => {
        console.log("created: ", e);
        this.loading(false);
      });
    this.$refs.txtMaKH.select();
    this.hienThiDsPhanAnh(false);
    this.setButtonStatus(-1);
    this.matchHeight()
  },
  methods: {
    ...mapActions("tiepnhanphananhkhachhang", [
      "getDsNganhNghe",
      "getDsLoaiKH",
      "getDsLoaiKHLon",
      "getDsHinhThucKN",
      "getDsNoiDungPA",
      "getDsPAMoiTiepNhanTheoNV",
      "clearDsPAMoiTiepNhanTheoNV",
    ]),
    ...mapActions("tracuukhachhanglon", [
      "traCuuThanhToan",
      "getDsLienHe",
      "clearData",
      "clearDataDSThanhToan"
    ]),
    openLSPA() {
    //   this.$refs.popupTraCuuLSPA.openDialog()
      this.$bvModal.show("modal-tracuu-lspa");
    },
    matchHeight() {
      var heightTTKH = this.$refs.boxTTKH.clientHeight + 2 + 'px'
      var heightTTNPA = this.$refs.boxTTNPA.clientHeight + 2 + 'px'
      var heightTTPA = this.$refs.boxTTPA.clientHeight + 2 + 'px'
      Vue.set(this.styles.ttno, 'height', heightTTKH)
      Vue.set(this.styles.lspa, 'height', heightTTNPA)
      Vue.set(this.styles.kqxl, 'height', heightTTPA)
    },
    chukycuocChangeHandler(e) {
      this.thongtinKyHoaDon.kyHoaDonSelected = moment(e.p_kyhoadon).format(
        "MM/YYYY"
      );
      this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no;
    },
    async hienThiDsPhanAnh(firstLoad) {
      if (firstLoad) {
        this.clearDsPAMoiTiepNhanTheoNV();
        this.setButtonStatus(1);
      } else {
        await this.getDsPAMoiTiepNhanTheoNV({
          nhanVienId: this.$auth.getNhanVienID(),
          ttpaId: this.TRANGTHAI_PHAN_ANH.MOI_TIEP_NHAN,
          pageNo: 1,
          pageSize: 1000,
        })
          .then(() => {
            if (this.dsPAMoiTiepNhanTheoNV.length == 0) {
              this.clearDsPAMoiTiepNhanTheoNV();
              this.setButtonStatus(1);
            }
          })
          .catch(() => {});
      }
    },
    setButtonStatus(kieu) {
      console.log("setButtonStatus", kieu);
      this.btnStatus.btnNhapMoiEnabled = false;
      this.btnStatus.btnGhiLaiEnabled = false;
      this.btnStatus.btnXoaEnabled = false;
      this.btnStatus.btnHuyBoEnabled = false;
      this.btnStatus.txtNoiDungGQEnabled = false;
      this.btnStatus.btnKhoaPhieuEnabled = false;
      this.btnStatus.btnCapNhapLienHeEnabled = false;

      switch (kieu) {
        case -1: //Bat dau
          this.btnStatus.btnGhiLaiEnabled = true;
          this.btnStatus.btnHuyBoEnabled = true;
          break;
        case 0: //Bat dau
          this.btnStatus.btnNhapMoiEnabled = true;
          this.$refs.txtMaKH.select();
          break;
        case 1: //Them moi
          this.$refs.txtMaKH.select();
          this.btnStatus.btnGhiLaiEnabled = true;
          this.btnStatus.btnHuyBoEnabled = true;
          this.clear();
          break;
        case 2: //Huy
          this.btnStatus.btnNhapMoiEnabled = true;
          this.btnStatus.btnXoaEnabled = true;
          break;
        case 3: //Edit
          this.btnStatus.btnNhapMoiEnabled = true;
          this.btnStatus.btnGhiLaiEnabled = true;
          this.btnStatus.btnHuyBoEnabled = true;
          this.btnStatus.btnXoaEnabled = true;
          this.btnStatus.btnKhoaPhieuEnabled = true;
          this.btnStatus.txtNoiDungGQEnabled = true;
          break;
      }
    },
    clear() {
      this.params.ngayTLTu = this.today;
      this.params.ngayTLDen = this.upToDate;
      this.params.pTxtChiTiet = "";
      this.params.pTxtGhiChu = "";
      this.params.pTxtTenKH = "";
      this.params.pTxtDiaChi = "";
      this.params.pTxtNguoiLH = "";
      this.params.pTxtDienThoaiLH = "";
      this.params.ngayTiepNhan = this.today;
      this.datePickerStatus.ngayTLTuEnabled = false;
      this.params.pTxtMaKH = "";
      this.params.pTxtNoiDungXL = "";
      // daily_id = -1;
      // thuebao_id = 0;
      // thanhtoan_id = 0;
    },
    async onMaKHKeydown(event) {
      if (event.keyCode == 13) {
        if (this.params.pTxtMaKH.trim() === "") {
          this.$refs.txtMaKH.select();
          return;
        }

        this.loading(true);
        try {
          await this.hienThiThongTinTB();
          if (this.dsPAMoiTiepNhanTheoNV.length > 0) {
            let index = this.dsPAMoiTiepNhanTheoNV
              .map((x) => x.MA_KH)
              .indexOf(this.params.pTxtMaKH.trim());


            if (index != -1) {
              //Nếu đã có Mã KH đó trên lưới thì index
              this.$refs.gridPhanAnh.setCurrentSelectedRow(index);
            } else {
              //Chưa có thì chuyển trạng thái Thêm mới
              this.btnStatus.btnNhapMoiEnabled = true;
              this.btnStatus.btnGhiLaiEnabled = true;
              this.btnStatus.btnHuyBoEnabled = true;
              this.params.pTxtChiTiet = "";
            }

            // this.$nextTick(() => this.btnStatus.btnLienHeVisible = true)
          }
        } catch (e) {
        } finally {
          this.loading(false);
        }
      }
    },
    async hienThiThongTinTB() {
      await TraCuuKhachHangLonApi.traCuuKHTheoMa(
        this.axios,
        this.params.pTxtMaKH
      )
        .then(async (response) => {
          if (response.data.error_code === "BSS-00000000") {
            if (response.data.data && response.data.data.length > 0) {
              //kiểm tra quyền
              //thinh need check
              // if (cs_khl_facade.KiemTra_NhanVien_Chamsoc_KHL(vkhachhang_id, tt_nd.nhanvien_id))
              //                   tsbtnCapNhatLH.Visible = true;

              let khachHang = response.data.data[0];
              this.params.pTxtMaKH = khachHang.MA_KH;
              this.params.pTxtTenKH = khachHang.TEN_KH;
              this.params.pTxtDiaChi = khachHang.DIACHI_KH;
              this.params.pKhachHangId = khachHang.KHACHHANG_ID;
              this.params.pOptLoaiKHLon = khachHang.KHLON_ID;
              this.params.pOptLoaiKH = khachHang.LOAIKH_ID;
              this.params.pOptNganhNghe = khachHang.NGANHNGHE_ID;
              this.params.pMaKH = khachHang.MA_KH;
              this.params.pMaTT = khachHang.MA_TT;
              //this.params.pThueBaoId
              //this.params.pKhachHangId
              await Promise.all([
                this.hienThiThongTinLienHe(this.params.pKhachHangId),
                this.hienThiThongTinNo(1, this.params.pKhachHangId),
                this.hienThiLichSuKhieuNai(this.params.pKhachHangId),
              ]);
            } else {
              this.$root.toastError("Không tìm thấy thông tin danh bạ!");
            }
          } else {
            this.$root.toastError(response.data.message);
          }
        })
        .catch((reject) => {
          if (
            reject.response &&
            reject.response.data &&
            reject.response.data.message
          )
            this.$root.toastError(reject.response.data.message);
          else this.$root.toastError("Có lỗi xảy ra!");
        });
    },
    async hienThiThongTinLienHe(khachHangId) {
      await TraCuuKhachHangLonApi.traCuuLienHe(this.axios, khachHangId)
        .then((response) => {
          if (response.data.error_code === "BSS-00000000") {
            if (response.data.data && response.data.data.length > 0) {
              let lienHe = response.data.data[0];
              this.params.pTxtNguoiLH = lienHe.TEN_LH;
              this.params.pTxtDienThoaiLH = lienHe.DIENTHOAI;
              this.btnStatus.btnLienHeVisible = true
            } else {
              this.params.pTxtNguoiLH = "";
              this.params.pTxtDienThoaiLH = "";
              this.btnStatus.btnLienHeVisible = false
            }
          }
        })
        .catch(() => {});
    },
    async hienThiThongTinNo(kieu, khachHangId) {
      let data = {
        khachHangId: khachHangId,
        kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
        pageNo: 1,
        pageSize: 1000,
      };
      await this.traCuuThanhToan(data);
    },
    async hienThiLichSuKhieuNai(khachHangId) {
      try {
        this.gridData.lichSuPhanAnh = []
        let rs = await this.$root.context.post('/web-cskh/api/tra-cuu/lich-su-phan-anh', {
          ngayTiepNhan: '',
          denNgay: '',
          maKh: this.params.pTxtMaKH
        })
        if (rs.error_code === "BSS-00000000") {
          this.gridData.lichSuPhanAnh = rs.data.data || []
        }
      }
      catch (e) {

      }
      // let postData = {
      //   khachHangId: khachHangId,
      //   nhanVienId: this.$auth.getNhanVienID(),
      //   pageSize: 1000,
      //   pageNo: 1,
      // };
      // await TiepNhanPhanAnhKhachHangApi.getDsPAKhachHangTheoNV(
      //   this.axios,
      //   postData
      // )
      //   .then((response) => {
      //     console.log('hienThiLichSuKhieuNai', response)
      //     this.gridData.lichSuPhanAnh = []
      //     if (response.data.error_code === "BSS-00000000") {
      //       this.gridData.lichSuPhanAnh = response.data.data.data || [];
      //     }
      //   })
      //   .catch(() => {
      //     this.gridData.lichSuPhanAnh = [];
      //   });
    },
    onHuyBoClick() {
      this.setButtonStatus(2);
      this.clear();
      this.clearDataDSThanhToan();
      this.gridData.lichSuPhanAnh = [];
      this.clearDsPAMoiTiepNhanTheoNV();
    },
    onXoaClick() {
      if (this.dsPAMoiTiepNhanTheoNV.length == 0) {
        this.$root.toastError(
          "Danh sách phản ánh trống! Bạn không thể thực hiện xóa!"
        );
        return;
      }

      this.$bvModal.show("xoaConfirm");
    },
    async onXoaConfirm() {
      let data = {
        phanAnhId: this.params.pPhanAnhId,
      };
      try {
        this.loading(true);
        await TiepNhanPhanAnhKhachHangApi.xoaPA(this.axios, data)
          .then(async (response) => {
            if (response.data.error_code == "BSS-00000000") {
              this.$root.toastSuccess("Xóa phản ánh thành công !");
              await this.hienThiDsPhanAnh();
            } else
              this.$root.toastError(
                "Không thể xóa bản ghi này: " + response.data.message
              );
          })
          .catch((reject) => {
            this.$root.toastError(
              reject.response.data.message || reject.response.data.error
            );
          });
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },
    async onGhiLaiClick() {
      if (!this.onValidateInput()) return;

      this.loading(true);
      try {
        let postData = {
          khachHangId: this.params.pKhachHangId,
          maKh: this.params.pMaKH,
          ngayLh: moment(this.params.ngayTiepNhan).format("DD/MM/YYYY"),
          nguoiLh: this.params.pTxtNguoiLH,
          dienThoaiLh: this.params.pTxtDienThoaiLH,
          chuDeKnId: this.params.pOptNoiDungPA,
          ndPhanAnh: this.params.pTxtChiTiet,
          htknId: this.params.pOptHinhThucKN,
          //TTPA_ID: this.TRANGTHAI_PHAN_ANH.MOI_TIEP_NHAN,
          nhanVienId: this.$auth.getNhanVienID(),
          ghiChu: this.params.pTxtGhiChu,
          mayCn: this.$auth.getUserName(),
          nguoiCn: this.$auth.getUserName(),
          ngayCn: moment(new Date()).format("DD/MM/YYYY"),
        };

        if (this.datePickerStatus.ngayTLTuEnabled) {
          postData.henGqTtu = moment(this.params.ngayTLTu).format("DD/MM/YYYY");
          postData.henGqDen = moment(this.params.ngayTLDen).format(
            "DD/MM/YYYY"
          );
        }

        if (this.btnStatus.btnNhapMoiEnabled) {
          postData.maKh = this.params.pTxtMaKH;
          postData.phanAnhId = 0;
        } else {
          postData.phanAnhId = this.params.pPhanAnhId;
        }

        await TiepNhanPhanAnhKhachHangApi.capNhapPA(this.axios, postData)
          .then((response) => {
            if (response.data.error_code == "BSS-00000000")
              this.$root.toastSuccess(
                this.btnStatus.btnNhapMoiEnabled
                  ? "Thêm mới thành công!"
                  : "Cập nhập thành công!"
              );
            else
              this.$root.toastError(
                "Xảy ra lỗi trong quá trình cập nhập thông tin: " +
                  response.data.message
              );
          })
          .catch((reject) => {
            this.$root.toastError(
              reject.response.data.message || reject.response.data.error
            );
          });
      } catch (e) {
        this.$root.toastError("Có lỗi: " + e.data.message);
      } finally {
        this.loading(false);
      }
      await this.hienThiDsPhanAnh(false);
    },
    onNhapMoiClick() {
      this.setButtonStatus(1);
      let hinhThucMacDinhIndex = this.dsNoiDungPA.map((x) => x.id).indexOf(2);
      if (hinhThucMacDinhIndex >= 0) {
        this.params.pOptHinhThucKN = 2;
      }
    },
    onValidateInput() {
      if (this.params.pTxtNguoiLH.trim() == "") {
        this.$root.toastError("Bạn chưa nhập người phản ánh!");
        this.$refs.txtNguoiLH.select();
        return false;
      }
      if (this.params.pTxtDienThoaiLH.trim() == "") {
        this.$root.toastError("Bạn chưa nhập điện thoại liên hệ!");
        this.$refs.txtDienThoaiLH.select();
        return false;
      }
      if (!this.isNumeric(this.params.pTxtDienThoaiLH.trim())) {
        this.$root.toastError("Điện thoại liên hệ phải là kiểu số!");
        this.$refs.txtDienThoaiLH.select();
        return false;
      }
      if((!/(0[1-9])+(\d{8})/.test(this.params.pTxtDienThoaiLH) || this.params.pTxtDienThoaiLH.length > 11) && this.params.pTxtDienThoaiLH.length > 0) {
        this.$toast.error("Điện thoại liên hệ không hợp lệ!");
        this.$refs.txtDienThoaiLH.select();
        return false;
      }
      if (this.params.pTxtChiTiet.trim() == "") {
        this.$root.toastError("Bạn chưa nhập chi tiết phản ánh!");
        this.$refs.TxtChiTiet.select();
        return false;
      }
      if (this.datePickerStatus.ngayTLTuEnabled) {
        if (this.params.ngayTLTu < this.today) {
          this.$root.toastError("Từ ngày không được nhỏ hơn ngày hiện tại!");
          this.$refs.dtpNgayTLTu.select();
          return false;
        }
        if (this.params.ngayTLTu > this.params.ngayTLDen) {
          this.$root.toastError("Từ ngày không được lớn hơn đến ngày!");
          this.$refs.dtpNgayTLTu.select();
          return false;
        }
      }
      if (this.btnStatus.btnNhapMoiEnabled) {
        if (this.dsPAMoiTiepNhanTheoNV.length <= 0) {
          this.$root.toastError(
            "Bạn không thể cập nhật dữ liệu do khách hàng: " +
              this.params.pTxtMaKH +
              " chưa có phản ánh"
          );
          return false;
        }
      }
      return true;
    },
    async onKhoaPhieuClick() {
      if (this.dsPAMoiTiepNhanTheoNV.length > 0) {
        if (this.params.pTxtNoiDungXL.trim() == "") {
          this.$root.toastError("Bạn chưa nhập nội dung xử lý phản ánh!");
          this.$refs.txtNoiDungXL.select();
          return;
        }
        if (this.onSelected.phanAnh) {
          let postData = {
            phanAnhId: this.onSelected.phanAnh.PHANANH_ID,
            noiDungGq: this.params.pTxtNoiDungXL.trim(),
            nhanVienGqId: this.$auth.getNhanVienID(),
          };

          await TiepNhanPhanAnhKhachHangApi.khoaPhieu(this.axios, postData)
            .then(async (response) => {
              if (response.data.error_code == "BSS-00000000") {
                this.$root.toastSuccess("Bạn đã khóa phiếu thành công!");
                await this.hienThiDsPhanAnh();
              } else
                this.$root.toastError(
                  "Khóa phiêu thất bại: " + response.data.message
                );
            })
            .catch((reject) => {
              this.$root.toastError(
                reject.response.data.message || reject.response.data.error
              );
            });
        }
      }
    },
    isNumeric(str) {
      if (typeof str != "string") return false;
      return !isNaN(str) && !isNaN(parseFloat(str));
    },
    async gridPhanAnh_selectedRowChanged(dataItem) {
      if (dataItem) {
        let chuDeKNId = dataItem.CHUDEKN_ID;
        this.onSelected.phanAnh = dataItem;
        this.params.pPhanAnhId = dataItem.PHANANH_ID;
        this.params.pNhanVienId = dataItem.NHANVIEN_ID;
        this.params.pTxtMaKH = dataItem.MA_KH;
        this.params.pTxtChiTiet = dataItem.ND_PHANANH;
        this.params.pOptNoiDungPA = dataItem.CHUDEKN_ID;
        this.params.ngayTiepNhan = moment(dataItem.NGAY_LH).format(
          "DD/MM/YYYY"
        );
        this.params.pOptHinhThucKN = dataItem.HTKN_ID;
        this.datePickerStatus.ngayTLTuEnabled = dataItem.HENGQ_TU
          ? true
          : false;
        this.params.ngayTLTu = dataItem.HENGQ_TU
          ? moment(dataItem.HENGQ_TU)
          : this.today;
        this.params.ngayTLDen = dataItem.HENGQ_TU
          ? moment(dataItem.HENGQ_DEN)
          : this.upToDate;
        this.params.pTxtNguoiLH = dataItem.NGUOI_LH;
        this.params.pTxtDienThoaiLH = dataItem.DIENTHOAI_LH;
        this.loading(true);
        try {
          await this.hienThiThongTinTB();
        } catch (e) {
          this.loading(false);
        } finally {
          this.loading(false);
        }
        this.setButtonStatus(3);
      }
    },
    onStartDownload() {
        if (this.dsPAMoiTiepNhanTheoNV.length == 0) {
            this.$root.toastError('Không có dữ liệu để xuất Excel !')
            return
        }
    },
    ShowSearchAccount() {
      this.popupComponent = SearchAccount;
    //   SearchAccount.data.isPopup = true;
      this.Popup("popupComponents");
      //this.$refs.SearchAccountComponent.show();
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
      // // Xu ly form popup
      // $('#'+modalId).modal();
      // //appending modal background inside the bigform-content
      // $('.modal-backdrop').appendTo('#ChangeADSLSpeed');
      // //removing body classes to enable click events
      // $('body > .modal-backdrop').remove();
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      // $('#'+modalId).modal('hide');
      // $('#ChangeADSLSpeed > .modal-backdrop').remove();
    },
    searchAccountClosed(val) {
      if(val){
        this.params.pTxtMaKH = val.ma_kh || "";
      }
      this.ClosePopup("popupComponents");
    },
    onRowSelected(args) {
      let refValue = args.row.closest(".e-grid").getAttribute("ref-value");
      this.$refs[refValue].$el
        .querySelectorAll(".col-mark-selected-row")
        .forEach((item) => {
          item.style.display =
            args.rowIndex == parseInt(item.id) ? "block" : "none";
        });
    },
    onDataBoundTienTrinh() {
      let grid = this.$refs["grid-dsphananh"];
      grid.groupColumn("NOIDUNGPA");
      grid.selectRow(0);
    },
    onActionCompleteTienTrinh(args) {
      this.onActionComplete(args);

      this.$refs["grid-dsphananh"].$el
        .querySelectorAll(".e-indentcell")
        .forEach((item) => {
          item.classList.add("tree-first", "bg-white");

          let div1 = document.createElement("div");
          div1.classList.add("tree-line");

          let div2 = document.createElement("div");
          div2.classList.add("tree-indent");

          item.append(div1);
          item.append(div2);
        });
    },
    onCloseTTLH() {
      this.params.pTxtNguoiLH = this.$refs.popupTTLH.txtTenLH
      this.params.pTxtDienThoaiLH = this.$refs.popupTTLH.txtDienThoai
    },
    onLienHeClick(){
      this.$refs.popupTTLH.openDialog()
    },
    openPopUpNguoiLH(){
      if(this.params.pKhachHangId == "" || this.params.pKhachHangId == 0 || this.params.pKhachHangId == undefined){
         this.$root.toastError('Bạn hãy nhập mã khách hàng và Enter để tìm kiếm!')
        return
      }
      this.$refs.popupTTLH.openDialog()
    }
  },
  watch: {},
  destroyed() {
    EventBus.$off("chukycuocChange", this.chukycuocChangeHandler);
  },
};
</script>
