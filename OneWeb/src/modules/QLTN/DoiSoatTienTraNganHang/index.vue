<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import API from "../api/DoiSoatTienTraNganHang";
import APICommon from "../api/Commons";
import xlsx from "xlsx";
import Vue from 'vue'

export default {
  components: { breadcrumb },
  data() {
    return {
      header: {
        title: "ĐỐI SOÁT TIỀN TRẢ NGÂN HÀNG",
        list: [
          { name: "Gạch nợ", link: { name: "Ui.cards" } },
          {
            name: "Đối soát tiền trả ngân hàng (STK)",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      kyHoaDon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      max_kyhoadon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      chuKy: "01",
      kyCuoc: "",
      duongDan: "",
      isShowTT: false,
      filterThanhToan: {
        dsDieuKien: [],
        isCheck1: false,
        idDk1: "",
        valueDk1: "",
        tieuChi1: "",
        isCheck2: false,
        idDk2: "",
        valueDk2: "",
        tieuChi2: "",
        isCheck3: false,
        idDk3: "",
        valueDk3: "",
        tieuChi3: "",
        isCheck4: false,
        idDk4: "",
        valueDk4: "",
        tieuChi4: "",
        isCheckDichVuVT: false,
        idDichVuVT: "",
        dsDichVu: [],
        isCheckDonVi: false,
        idDonVi: "",
        dsDonVi: [],
        isCheckTuyenThu: false,
        idTuyenThu: "",
        dsTuyenThu: [],
      },
      dsDanhBaThanhToan: [],
      txtTieuChi: "",
      dsSoTaiKhoan: [],
      dsKetQua: [],
      dsMapMaTT: [],
      dsNapDL: [],
      mapMaTTSelected: null,
      statusTemplate () {
        return { template : Vue.component('columnTemplate', {
          template: `<div>
                        <input v-if="data.KT_LECH === '0'" class="check f20 text-primary" disabled type="checkbox"></input>
                        <span v-else-if="data.KT_LECH === '1'" class="one-check f20 text-success"></span>
                    </div>`,
            data () { return { data: {} }; }})
        }
      }
    };
  },
  validations: {},
  watch: {
    "filterThanhToan.idDonVi": {
      handler: async function (value) {
        const response = await API.getDSTuyenThuByDonVi(this.axios, value);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.filterThanhToan.dsTuyenThu = response.data.data;
          this.filterThanhToan.idTuyenThu = response.data.data[0].TUYENTHU_ID;
        }
      },
    },
    "filterThanhToan.idDk1": {
      handler: function (value) {
        const obj = this.filterThanhToan.dsDieuKien.find(
          (item) => item.KIEUTK_ID == value
        );
        if (obj) {
          const txt = obj.KIEU_TK;
          this.filterThanhToan.tieuChi1 = txt;
        } else {
          this.filterThanhToan.tieuChi1 = "";
        }
        this.DocTieuChi();
      },
    },
    "filterThanhToan.idDk2": {
      handler: function (value) {
        const obj = this.filterThanhToan.dsDieuKien.find(
          (item) => item.KIEUTK_ID == value
        );
        if (obj) {
          const txt = obj.KIEU_TK;
          this.filterThanhToan.tieuChi2 = txt;
        } else {
          this.filterThanhToan.tieuChi2 = "";
        }
        this.DocTieuChi();
      },
    },
    "filterThanhToan.idDk3": {
      handler: function (value) {
        const obj = this.filterThanhToan.dsDieuKien.find(
          (item) => item.KIEUTK_ID == value
        );
        if (obj) {
          const txt = obj.KIEU_TK;
          this.filterThanhToan.tieuChi3 = txt;
        } else {
          this.filterThanhToan.tieuChi3 = "";
        }
        this.DocTieuChi();
      },
    },
    "filterThanhToan.idDk4": {
      handler: function (value) {
        const obj = this.filterThanhToan.dsDieuKien.find(
          (item) => item.KIEUTK_ID == value
        );
        if (obj) {
          const txt = obj.KIEU_TK;
          this.filterThanhToan.tieuChi4 = txt;
        } else {
          this.filterThanhToan.tieuChi4 = "";
        }
        this.DocTieuChi();
      },
    },
    "filterThanhToan.isCheck1": {
      handler: function (value) {
        this.DocTieuChi();
      },
    },
    "filterThanhToan.isCheck2": {
      handler: function (value) {
        this.DocTieuChi();
      },
    },
    "filterThanhToan.isCheck3": {
      handler: function (value) {
        this.DocTieuChi();
      },
    },
    "filterThanhToan.isCheck4": {
      handler: function (value) {
        this.DocTieuChi();
      },
    },
    "filterThanhToan.isCheckDichVuVT": {
      handler: function (value) {
        this.DocTieuChi();
      },
    },
    "filterThanhToan.isCheckDonVi": {
      handler: function (value) {
        this.DocTieuChi();
      },
    },
    "filterThanhToan.isCheckTuyenThu": {
      handler: function (value) {
        this.DocTieuChi();
      },
    },
  },
  created() {
    this.fetchDsDieuKien();
    this.fetchDsDichVu();
    this.fetchDsDonVi();
    this.fetchKyCuoc();
    $("body").on("click", ".dropdown-menu", function (e) {
      $(this).parent().is(".show") && e.stopPropagation();
    });
  },
  mounted() {
    this.kyCuoc = moment(this.kyHoaDon).format("YYYYMM") + this.chuKy;
  },
  methods: {
    onKyHoaDonChange: function (args) {
      if (args.value === null) {
        this.$root.toastError("Vui lòng chọn Kỳ hóa đơn");
        return;
      }
      this.kyCuoc = moment(args.value).format("YYYYMM") + this.chuKy;
    },
    async fetchDsDieuKien() {
      const response = await API.getDSDieuKien(this.axios, 2);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length > 0
      ) {
        this.filterThanhToan.dsDieuKien = response.data.data;
        this.filterThanhToan.idDk1 = response.data.data[0].KIEUTK_ID;
        this.filterThanhToan.idDk2 = response.data.data[0].KIEUTK_ID;
        this.filterThanhToan.idDk3 = response.data.data[0].KIEUTK_ID;
        this.filterThanhToan.idDk4 = response.data.data[0].KIEUTK_ID;
      }
    },
    async fetchDsDichVu() {
      const response = await API.getDSDichVuVT(this.axios);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length > 0
      ) {
        this.filterThanhToan.dsDichVu = response.data.data;
        this.filterThanhToan.idDichVuVT = response.data.data[0].DICHVUVT_ID;
      }
    },
    async fetchDsDonVi() {
      const response = await API.getDSDonVi(this.axios);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length > 0
      ) {
        this.filterThanhToan.dsDonVi = response.data.data;
        this.filterThanhToan.idDonVi = response.data.data[0].DONVI_ID;
      }
    },
    async fetchDsDanhBaThanhToan() {
      let dsDieuKien = [];
      if (this.filterThanhToan.isCheck1) {
        dsDieuKien.push({
          id: this.filterThanhToan.idDk1,
          value: this.filterThanhToan.valueDk1,
        });
      }
      if (this.filterThanhToan.isCheck2) {
        dsDieuKien.push({
          id: this.filterThanhToan.idDk2,
          value: this.filterThanhToan.valueDk2,
        });
      }
      if (this.filterThanhToan.isCheck3) {
        dsDieuKien.push({
          id: this.filterThanhToan.idDk3,
          value: this.filterThanhToan.valueDk3,
        });
      }
      if (this.filterThanhToan.isCheck4) {
        dsDieuKien.push({
          id: this.filterThanhToan.idDk4,
          value: this.filterThanhToan.valueDk4,
        });
      }
      try {
        this.loading(true);
        const param = {
          page: 0,
          size: 1000,
        };
        const data = {
          dsTimKiem: dsDieuKien, //[{ id: 178, value: "Đà Nẵng" }],
          dichVuVTId: this.filterThanhToan.isCheckDichVuVT
            ? this.filterThanhToan.idDichVuVT
            : "", //4
          donViId: this.filterThanhToan.isCheckDonVi
            ? this.filterThanhToan.idDonVi
            : "", //40
          tuyenThuId: this.filterThanhToan.isCheckTuyenThu
            ? this.filterThanhToan.idTuyenThu
            : "",
        };
        const response = await API.getDSDanhBaThanhToan(
          this.axios,
          data,
          param
        );
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.data.length > 0
        ) {
          this.dsDanhBaThanhToan = response.data.data.data;
        } else {
          this.$root.toastError("Không tìm thấy dữ liệu thanh toán");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchKiemTraSTK(ds) {
      try {
        this.loading(true);
        const data = {
          kyCuoc: this.kyCuoc,
          kieu: 1,
          dsSTK: ds,
        };
        const response = await API.doiSoatNganHang(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          //   this.dsSoTaiKhoan = response.data.data;
          this.dsMapMaTT = response.data.data;
        } else {
          this.$root.toastError("Có lỗi xảy ra, mời thử lại!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchDoiSoat(ds) {
      const dsSTKResult = ds.map((item) => ({
        soTK: item.SO_TK,
        tien: item.TIEN,
      }));
      try {
        this.loading(true);
        const data = {
          kyCuoc: this.kyCuoc,
          kieu: 2, // đối soát
          dsSTK: dsSTKResult,
        };
        const response = await API.doiSoatNganHang(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          const resultData = response.data.data;
          const dsRemoveDups = resultData.filter(
            (v, i, a) => a.findIndex((t) => t.SO_TK == v.SO_TK) === i
          );
          this.dsSoTaiKhoan = dsRemoveDups;
          const ds_kq = resultData.filter((item) => item.STATUS == "1"); // 1
          //   if (ds_kq.length > 0) {
          this.dsKetQua = ds_kq;
          //   }
          const ds_kq_notmatt = resultData.filter(
            (item) => item.KT_LECH == "-2"
          ); //-2
          //   if (ds_kq_notmatt.length > 0) {
          this.dsMapMaTT = ds_kq_notmatt;
          //   }
        } else {
          this.$root.toastError("Không tìm thấy dữ liệu đối soát!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    DocTieuChi() {
      let s = "";
      if (this.filterThanhToan.isCheck1) {
        s = s + this.filterThanhToan.tieuChi1 + ", ";
      }
      if (this.filterThanhToan.isCheck2) {
        s = s + this.filterThanhToan.tieuChi2 + ", ";
      }
      if (this.filterThanhToan.isCheck3) {
        s = s + this.filterThanhToan.tieuChi3 + ", ";
      }
      if (this.filterThanhToan.isCheck4) {
        s = s + this.filterThanhToan.tieuChi4 + ", ";
      }
      if (this.filterThanhToan.isCheckDichVuVT) {
        s = s + "Dịch vụ, ";
      }
      if (this.filterThanhToan.isCheckDonVi) {
        s = s + "Đơn vị, ";
      }
      if (this.filterThanhToan.isCheckTuyenThu) {
        s = s + "Tuyến thu, ";
      }
      if (s != "") {
        this.txtTieuChi = "Tiêu chí: " + s;
      } else {
        this.txtTieuChi = "";
      }
    },
    onClickTimKiem() {
      // this.$refs.dropdown.hide(true);
      if (this.txtTieuChi == "") {
        this.$root.toastError("Bạn phải chọn ít nhất 1 tiêu chí tìm kiếm");
        return;
      }
      this.fetchDsDanhBaThanhToan();
    },
    async onChangeFile(e) {
      const files = e.target.files;
      this.duongDan = e.target.files[0].name;
      this.dsSoTaiKhoan = [];
      this.dsKetQua = [];
      this.dsMapMaTT = [];
      this.dsNapDL = [];
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError("File excel không đúng định dạng!");
      }
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];
          let dsDanhSach = [];
          for (let i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsDanhSach.push({
              SO_TK: ws[i].SO_TK,
              TIEN: parseInt(ws[i].TIEN),
            });
          }
          let propCount = 0;
          if (excellist && excellist.length > 0) {
            for (var prop in excellist[0]) {
              if (excellist[0].hasOwnProperty(prop)) propCount++;
            }
          }
          if (propCount == 0) {
            this.$root.toastError("File không có dữ liệu!");
            this.duongDan = "";
            return;
          }
          if (propCount != 2) {
            this.$root.toastError("Dữ liệu file excel chưa đúng số lượng cột!");
            return;
          }
          const arrayCol = Object.keys(excellist[0]);
          if (arrayCol[0] != "SO_TK" && arrayCol[1] != "TIEN") {
            this.$root.toastError("File không đúng cấu trúc SO_TK, TIEN");
            return;
          }
          //   this.fetchKiemTraSTK(dsDanhSach);
          this.dsSoTaiKhoan = dsDanhSach;
          this.$refs.stk_grid.setCurrentSelectedRow(0);
        } catch (error) {
          this.$root.toastError("Kiểm tra dữ liệu file đầu vào!");
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    handleNap_DL() {
      const ds_nap = this.$refs.danhba_grid.getSelectedRecords() || [];
      const dsNap_DL = ds_nap.map((item) => ({
        SO_TK: this.mapMaTTSelected.SO_TK,
        MA_TT: item.MA_TT,
        MA_KH: item.MA_KH,
      }));
      this.dsNapDL = dsNap_DL;
    },
    async fetchCapNhat() {
      const dsBody = this.dsNapDL.map((item) => ({
        maTT: item.MA_TT,
        soTK: item.SO_TK,
      }));
      try {
        this.loading(true);
        const data = {
          ds: dsBody,
        };
        const response = await API.capNhatDoiSoat(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Cập nhật thành công!");
          this.dsNapDL = [];
          this.Nap_DL_STKMAP();
        } else {
          this.$root.toastError("Cập nhật có lỗi, mời thử lại!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async Nap_DL_STKMAP() {
      if (this.dsMapMaTT.length <= 0) {
        return;
      }
      const ds = this.dsMapMaTT.map((item) => ({ soTK: item.SO_TK, tien: 0 }));
      //   this.fetchKiemTraSTK(ds);
      try {
        this.loading(true);
        const data = {
          kyCuoc: this.kyCuoc,
          kieu: 2,
          dsSTK: ds,
        };
        const response = await API.doiSoatNganHang(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          const resultData = response.data.data;
          const ds_kq = resultData.filter((item) => item.STATUS == "1"); // 1
          this.dsKetQua = [...this.dsKetQua, ...ds_kq];
          const ds_kq_notmatt = resultData.filter(
            (item) => item.KT_LECH == "-2"
          ); //-2
          this.dsMapMaTT = ds_kq_notmatt;
          //   this.dsMapMaTT = []
        } else {
          this.$root.toastError("Có lỗi xảy ra, mời thử lại!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    dsMapMaTT_selectedRowChanged(data) {
      if (data) {
        this.mapMaTTSelected = data;
      } else {
        this.mapMaTTSelected = null;
      }
    },
    LayTT() {
      if (this.duongDan.trim() == "") {
        this.$root.toastError("Bạn chưa chọn file để đọc thông tin file!");
        return;
      }
    },
    DoiSoat() {
      if (this.dsSoTaiKhoan.length <= 0) {
        this.$root.toastError("Không có danh sách số tài khoản để thực hiện!");
        return;
      }
      const dsSelected_STK = this.$refs.stk_grid.getSelectedRecords() || [];
      if (dsSelected_STK.length <= 0) {
        this.$root.toastError("Bạn chưa chọn phiếu để thực hiện!");
        return;
      }
      this.$confirm(
        `Bạn có muốn đối soát danh sách số tài khoản không ?`,
        "Thông báo",
        {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Không đồng ý",
        }
      )
        .then(() => {
          this.fetchDoiSoat(dsSelected_STK);
        })
        .catch(() => {});
    },
    Nap_DL() {
      if (this.mapMaTTSelected == null) {
        this.$root.toastError(
          "Bạn chưa chọn số tài khoản cần máp mã thanh toán!"
        );
        return;
      }
      this.dsNapDL = [];
      if (this.dsMapMaTT.length <= 0) {
        this.$root.toastError(
          "Bạn không có danh sách số tài khoản cần map. Không thể thực hiện!"
        );
        return;
      }
      if (this.dsDanhBaThanhToan.length <= 0) {
        this.$root.toastError(
          "Bạn không có danh sách thanh toán để gán. Không thể thực hiện!"
        );
        return;
      }
      const dsSelected_ThanhToan = this.$refs.danhba_grid.getSelectedRecords() || [];
      if (dsSelected_ThanhToan.length <= 0) {
        this.$root.toastError("Bạn chưa chọn danh sách mã thanh toán!");
        return;
      }
      this.handleNap_DL();
    },
    Xoa_DL() {
      if (this.dsNapDL.length <= 0) {
        return;
      }
      this.dsNapDL = [];
    },
    CapNhat() {
      if (this.dsNapDL.length <= 0) {
        this.$root.toastError(
          "Chưa có dữ liệu map số tài khoản và mã thanh toán!"
        );
        return;
      }
      this.$confirm(
        `Bạn có muốn cập nhật dữ liệu số tài khoản vào danh sách thanh toán này không?`,
        "Thông báo",
        {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Hủy",
        }
      )
        .then(() => {
          this.fetchCapNhat();
        })
        .catch(() => {});
    },
    onChonTT() {
      this.isShowTT = true;
    },
    TaiFileMau() {
      this.loading(true);
      try {
        const temp = [
          { SO_TK: "0123695959", TIEN: "50000" },
        ];
        var dsach = xlsx.utils.json_to_sheet(temp);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "TB"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "fileDSSTK.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    XuatExcelDoiSoat() {
      if (this.dsKetQua.length <= 0) {
        this.$root.toastError("Không có danh sách kết quả để xuất excel!");
        return;
      }
      const dsSelected_KetQua = this.$refs.ketqua_grid.getSelectedRecords() || [];
      if (dsSelected_KetQua.length <= 0) {
        this.$root.toastError("Bạn chưa chọn danh sách để xuất!");
        return;
      }
      const dsExcel = dsSelected_KetQua;
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(dsExcel);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "ketquadoisoat.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    async fetchKyCuoc() {
      const response = await APICommon.getKyCuocHienHanh(this.axios);
      if (response.data.error_code === "BSS-00000000") {
        this.kyCuoc = response.data.data[0].KYCUOC;
        this.kyHoaDon = moment(response.data.data[0].KYCUOC,"YYYYMMDD").toDate();
      }
    },
  },
  computed: {},
};
</script>
