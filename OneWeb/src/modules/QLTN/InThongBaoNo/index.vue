<template src="./index.template.html"></template>
<style src="./style.scss" scoped></style>
<script>
import Vue from "vue";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns";
import { L10n } from "@syncfusion/ej2-base";
import {previewPrint} from "../../../utils/util";
import moment from "moment";
import KyCuoc from "../components/KyCuoc.vue";
import { mapActions, mapState } from "vuex";
import xlsx from "xlsx";
import API from "../api/InThongBaoNo";
import DanhMucAPI from "../api/DanhMucAPI";
import ComboboxGrid from "../components/ComboboxGrid";

Vue.use(DialogPlugin);
L10n.load({
  "vi-VN": {
    dropdowns: {
      noRecordsTemplate: "Không có dữ liệu",
      actionFailureTemplate: "",
    },
  },
});
export default Vue.extend({
  components: {
    appKyCuoc: KyCuoc,
    ComboboxGrid,
  },
  name: "InThongBaoNo",
  provide: {
    multiselect: [CheckBoxSelection],
  },
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      kyCuoc: "",
      fields: [
        {
          fieldName: "SO_CV",
          headerText: "Số",
          allowFiltering: true,
          width: 100
        },
        {
          fieldName: "MA_XLN",
          headerText: "Mã khách hàng",
          allowFiltering: true,
        },
        {
          fieldName: "SODAIDIEN",
          headerText: "Mã đại diện",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên khách hàng",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ khách hàng",
          allowFiltering: true,
        },
        {
          fieldName: "TONGNO",
          headerText: "Tổng nợ",
          allowFiltering: true,
          type: 'number',
          format: 'N0',
          cssClass:'text-tong-no'
        },
        {
          fieldName: "MANV_TC",
          headerText: "Nhân viên thu",
          allowFiltering: true,
        },
      ],
      checkXemTruoc: true,
      form: {
        checkLanTao: true,
        lanTao: "",
        checkNhanVien: false,
        idNhanVienXl: "",
        mauIn: "",
        checkQuan: false,
        quan: [],
        checkPhongBH: false,
        idPhongBH: [],
        checkKHDN: false,
      },
      dsKhachHang: [],
      dsNhanVienXLN: [],
      dsLanTao: [],
      dsMauIn: [],
    };
  },
  validations: {},
  watch: {},
  created() {},
  mounted() {
    // this.$refs.soMay.focus();
  },
  methods: {
    ...mapActions("qltnCommon", ["getDanhMucQuanHuyen", "getDanhSachDonVi"]),
    openDialog() {
      this.$refs.dlgInThongBaoNo.show();
    },
    handleKyHoaDon(e) {
      this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
    },
    async getDanhSachLanTao() {
      const response = await API.getDanhSachLanTao(this.axios);
      if (response.data.error_code === "BSS-00000000") {
        if (
          response.data.data !== undefined &&
          response.data.data.length >= 0
        ) {
        //   this.form.lanTao = response.data.data[0].LANTAO_ID;
          this.dsLanTao = response.data.data;
        }
      } else {
        this.dsLanTao = [];
      }
    },
    async getDanhSachMauIn() {
      const data = {
        loaiHD: 1,
        dsBaoCao: null, //1940
        trangThaiXL: null, //3
        nhomBCId: 24, //báo cáo Xử lý nợ
        // buocXLNId:0,
      };
      const response = await API.getDanhSachMauIn(this.axios, data);
      if (response.data.error_code === "BSS-00000000") {
        if (
          response.data.data !== undefined &&
          response.data.data.length > 0
        ) {
          this.dsMauIn = response.data.data;
          this.form.mauIn = response.data.data[0].BAOCAO_ID
        }
      } else {
        this.dsMauIn = [];
      }
    },
    lantao_selectedChanged(dataItem) {
      if (dataItem) {
        this.form.lanTao = dataItem.LANTAO_ID;
      }
    },
    NV_selectedChanged(dataItem) {
      if (dataItem) {
        this.form.idNhanVienXl = dataItem.NHANVIEN_ID;
      }
    },
    InHD() {
      const dsSelected = this.$refs.danhSach.getSelectedRecords() || [];
      if (dsSelected <= 0) {
        this.$root.toastError("Chưa có dữ liệu để in!");
        return;
      }
      this.inThongBaoNoLe(dsSelected);
    },
    async inThongBaoNoLe(dsMaXLN){
      this.loading(true);
      try {
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-thuno/api/thu-no/xu-ly-no/xuat-file-in-tbno/${this.form.mauIn}`,
          method: "POST",
          responseType: "blob",
          data: {
              ds: dsMaXLN
          }
        }).then((response) => {
          previewPrint(response.data);
        });
      } catch (error) {
        // this.$root.toastError(error.statusText);
        this.$root.toastError("Báo cáo này chưa được thiết kế mẫu !");
      } finally {
        this.loading(false);
      }
    },
    XuatFile() {
      if (this.dsKhachHang.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.dsKhachHang);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    DanhSach() {
      if (!this.form.checkLanTao || this.form.lanTao === "") {
        this.$root.toastError("Bạn chưa chọn lần tạo!");
        return;
      }
      if (!this.form.checkNhanVien || this.form.idNhanVienXl === "") {
        this.$root.toastError("Bạn chưa chọn nhân viên xử lý!");
        return;
      }
      this.fetchDanhSachKhachHang();
    },
    async fetchDanhSachKhachHang() {
      const data = {
        kyCuoc: this.kyCuoc,
        lanTaoId: this.form.checkLanTao ? this.form.lanTao : "", // 35
        nhanVienXLId: this.form.checkNhanVien ? this.form.idNhanVienXl : "", // 3137
        dsQuan: this.form.checkQuan ? this.form.quan.toString() : null, //6
        dsDonViId: this.form.checkPhongBH
          ? this.form.idPhongBH.toString()
          : null, //991
      };
      this.loading(true);
      try {
        const response = await API.getDanhSachInThongBao(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          if (
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.dsKhachHang = response.data.data;
          } else {
            this.dsKhachHang = [];
            this.$root.toastError("Không có dữ liệu!");
          }
        } else {
          this.dsKhachHang = [];
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
        this.dsKhachHang = [];
      } finally {
        this.loading(false);
      }
    },
    dialogOpen() {
      this.getDanhMucQuanHuyen();
      this.getDanhSachDonVi();
      this.getDanhSachNhanVienXLN();
      this.getDanhSachLanTao();
      this.getDanhSachMauIn();
      if (this.danhMucQuanHuyen.length > 0) {
        this.form.quan = this.danhMucQuanHuyen.map((item) => item.QUAN_ID);
      }
      if (this.danhsachDonVi.length > 0) {
        this.form.idPhongBH = this.danhsachDonVi.map((item) => item.DONVI_ID);
      }
    },
    close() {
      this.dsKhachHang = [];
      this.form.checkLanTao = true;
      this.form.checkNhanVien = false;
      this.form.mauIn = "";
      this.form.checkQuan = false;
      this.form.checkPhongBH = false;
      this.form.checkKHDN = false;
    },
    async getDanhSachNhanVienXLN() {
      try {
        let response = await DanhMucAPI.getNhanVienTheoLoai(this.axios, 25);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
        //   this.form.idNhanVienXl = response.data.data[0].NHANVIEN_ID;
          this.dsNhanVienXLN = response.data.data;
        }
      } catch (error) {
        console.log(error);
      }
    },
  },
  computed: {
    ...mapState("qltnCommon", ["danhMucQuanHuyen", "danhsachDonVi"]),
  },
});
</script>
