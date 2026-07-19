<template src="./TCTinhHinhThuNo.html"></template>
<style scoped src="./style.scss"></style>
<style>
.overlay-common .loading {
  display: flex;
  justify-content: center;
  flex-direction: column;
}
.overlay-common.show {
  display: flex;
  justify-content: center;
}
.overlay-common {
  background-color: rgba(0, 0, 0, 0.7);
}
.overlay-common .loading-panel {
  display: flex;
  text-align: center;
  background-color: #fff;
  padding: 10px 15px;
  align-items: center;
  color: #035fcc;
  border: 1px solid #035fcc;
  border-radius: 7px;
}
.overlay-common .loading-panel .loading-content {
  flex-grow: 1;
  padding-left: 10px;
}
</style>
<script>
import { mapState, mapGetters, mapActions } from "vuex";
import moment from "moment";
import EventBus from "@/utils/eventBus.js";
import CommonsAPI from "@/modules/QLTN/api/Commons";
/*
import PopupTimKiemNhanVienThuCuoc from '../components/PopupTimKiemNhanVienThuCuoc/PopupTimKiemNhanVienThuCuoc';
*/
import TimNhanVienThuCuoc from "../GachNo/components/TimNhanVienThuCuoc";
import Vue from "vue";
import { currency } from "@/filters/currency";

export default {
  components: { appTimNhanVienThuCuoc: TimNhanVienThuCuoc },
  name: "TCTinhHinhThuNo",
  prop: {},
  computed: {
    ...mapState("tracuutinhhinhthuno", [
      "danhSachTinhCuocTN",
      "tyLeThuNVThuCuoc",
    ]),
    ...mapState("qltnCommon", ["thongtinKyHoaDon"]),
    ...mapGetters("tracuutinhhinhthuno", ["tyLeThuNVThuCuocGetters"]),
    PTAggregatesColumns() {
      return [
        {
          field: "maNV",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.sumRecord,
        },
        {
          field: "phieuGiao",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.sumPhieuGiao,
        },
        {
          field: "tienGiao",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.sumTienGiao,
        },
        {
          field: "phieuThu",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.sumPhieuThu,
        },
        {
          field: "tienThu",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.sumTienThu,
        },
      ];
    },
  },
  data() {
    return {
      dateConfig2: {
        altInput: true,
        dateFormat: "d/m/Y",
        altFormat: "d/m/Y",
      },
      p_ngayTT: new Date(),
      p_ngayDen: new Date(),
      selectedChuKy: 1,
      fieldsTinhHinhThuNo: [
        {
          key: "maNV",
          label: "Mã NVTC",
          thClass: "center",
        },
        {
          key: "tenNV",
          label: "Tên NVTC",
          thClass: "center",
        },
        {
          key: "phieuGiao",
          label: "Phiếu giao",
          thClass: "center",
          tdClass: "text-right",
        },
        {
          key: "tienGiao",
          label: "Tiền giao",
          thClass: "center",
          tdClass: "text-right",
        },
        {
          key: "phieuThu",
          label: "Phiếu thu",
          thClass: "center",
          tdClass: "text-right",
        },
        {
          key: "tienThu",
          label: "Tiền thu",
          thClass: "center",
          tdClass: "text-right",
        },
        {
          key: "tyLePT",
          label: "Tỷ lệ phiếu thu",
          thClass: "center",
          tdClass: "text-right",
        },
        {
          key: "tyLeTT",
          label: "Tỷ lệ tiền thu",
          thClass: "center",
          tdClass: "text-right",
        },
      ],
      DSTinhCuocTN: "",
      DSTyLeThuCuoc: [],
      listTimKiem: [],
      listPhanTrang: [],
      listMaNV: [],
      tongPhieuGiao: 0,
      tongPhieuThu: 0,
      tongTienThu: 0,
      tongTienGiao: 0,
      pTuNgay: new Date(),
      pDenNgay: new Date(),
      mounted: false,
      fieldnames: {
        "Mã nhân viên": "maNV",
        "Tên nhân viên": "tenNV",
        "Phiếu giao": "phieuGiao",
        "Tiền giao": "tienGiao",
        "Phiếu thu": "phieuThu",
        "Tiền thu": "tienThu",
        "Tỷ lệ phiếu thu": "tyLePT",
        "Tỷ lệ tiền thu": "tyLeTT",
      },
      isDisabled: false,
      max_kyhoadon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      kyCuoc: "",
      waterMark: "Chọn kỳ hóa đơn",
      kyHoaDon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      chuKySelected: null,
      chuKy: [],
      quanHuyenIdSelected: null,
      daiLy: "",
      tableDataSource: [],
      dsMaNVTC: [],
      customTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright bold" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },
    };
  },
  watch: {},
  mounted() {
    this.updateChuKyHoaDon(moment(this.kyHoaDon).format("YYYYMM"));
    this.$refs.daiLy.focus();
  },
  methods: {
    ...mapActions("tracuutinhhinhthuno", [
      "getDanhSachTinhCuocTN",
      "getTyLeThuNVThuCuoc",
      "getObjSearch",
      "clearData",
    ]),
    sumRecord() {
      return this.tableDataSource.length.toString();
    },
    sumPhieuGiao() {
      const tong = this.tableDataSource.reduce((a, b) => +a + +b.phieuGiao, 0);
      return currency(tong);
    },
    sumTienGiao() {
      const tong = this.tableDataSource.reduce((a, b) => +a + +b.tienGiao, 0);
      return currency(tong);
    },
    sumPhieuThu() {
      const tong = this.tableDataSource.reduce((a, b) => +a + +b.phieuThu, 0);
      return currency(tong);
    },
    sumTienThu() {
      const tong = this.tableDataSource.reduce((a, b) => +a + +b.tienThu, 0);
      return currency(tong);
    },
    async updateChuKyHoaDon(pKyHoaDon) {
      try {
        this.loading(true);
        let response = await CommonsAPI.getChuKyNoTheoKyCuoc(
          this.axios,
          pKyHoaDon
        );
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined &&
          response.data.data.length > 0
        ) {
          this.chuKy = response.data.data;
          this.chuKySelected = this.chuKy[0].CHUKY;
        } else {
          this.chuKy = [];
          this.chuKySelected = null;
        }
        this.kyCuoc =
          moment(this.kyHoaDon).format("YYYYMM") + this.chuKySelected;
      } catch (error) {
        // console.log(error);
      } finally {
        this.loading(false);
      }
    },
    onKyHoaDonChange: function (args) {
      if (args.value === null) {
        this.$root.toastError("Vui lòng chọn Kỳ hóa đơn");
        return;
      }
      this.updateChuKyHoaDon(moment(args.value).format("YYYYMM"));
      // console.log(moment(this.kyHoaDon).format('MM/YYYY'));
    },
    onChangeText(txt, key) {
      let data = {
        key: key || "",
        value: txt || "",
      };
      this.getObjSearch(data);
    },
    changeData(val) {
      this.listPhanTrang = val;
    },
    gridDanDoCT_selectedRowChanged(val) {},
    async fetchDanhSachTinhCuocTN() {
      this.loading(true);
      const pNguoiDungId = this.$root.token.getNguoiDungID();
      try {
        this.listTimKiem = [];
        const response = await this.getDanhSachTinhCuocTN(pNguoiDungId);
        if (response.data.error_code === "BSS-00000000") {
          this.DSTinhCuocTN = response.data.data;
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchTyLeThuNVThuCuoc() {
      this.loading(true);
      let data = {
        //pPhanVungId: this.$root.token.getPhanVungID(),
        pKyCuoc: this.kyCuoc,
        pTuNgay: moment(this.pTuNgay).format("DD/MM/YYYY"),
        pDenNgay: this.isDisabled
          ? moment(this.pDenNgay).format("DD/MM/YYYY")
          : null,
        pDanhSachNVId: this.DSTinhCuocTN,
        pMaNVTC: this.daiLy,

        // pPhanVungId: 26,
        // pKyCuoc: 20210101,
        // pTuNgay: "02/01/2021",
        // pDenNgay: "20/04/2021",
        // pDanhSachNVId: "2474,2953,2954,2955,2956,2957,3038,3115,3386,6569",
        // pMaNVTC: this.daiLy,
      };
      try {
        const response = await this.getTyLeThuNVThuCuoc(data);
        console.log("response", response);
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data.length > 0) {
            this.checkPhieu(response.data.data);
          } else {
            this.$toast.error("Không có dữ liệu, vui lòng thử lại!");
            this.resetData();
          }
        }
      } catch (ex) {
        // this.$toast.error("Có lỗi xảy ra!");
      } finally {
        this.loading(false);
      }
    },
    resetData() {
      this.tableDataSource = [];
      document.querySelector(".grid-content .table-result").deleteTFoot();
      this.tongPhieuGiao = 0;
      this.tongPhieuThu = 0;
      this.tongTienGiao = 0;
      this.tongTienThu = 0;
    },
    checkPhieu(data) {
      let sumPhieuGiao = 0;
      let sumPhieuThu = 0;
      let sumTienGiao = 0;
      let sumTienThu = 0;
      this.tableDataSource = [];
      data.forEach((element) => {
        !this.dsMaNVTC.includes(element.MANV_TC) &&
          this.dsMaNVTC.push(element.MANV_TC);
      });
      this.dsMaNVTC.forEach((manv) => {
        const itemPThu = data.filter(
          (item) => item.MANV_TC == manv && item.KIEU == 2
        );
        const itemPgiao = data.filter(
          (item) => item.MANV_TC == manv && item.KIEU != 2
        );
        let tlPhieuThu = 0;
        let tlTienThu = 0;
        if (itemPgiao.length > 0 && itemPThu.length > 0) {
          if (itemPgiao[0].PHIEU > 0) {
            tlPhieuThu = (
              parseFloat(
                Number(itemPThu[0].PHIEU) / Number(itemPgiao[0].PHIEU)
              ).toFixed(2) * 100
            ).toFixed(2);
          }
          if (itemPgiao[0].TIEN > 0) {
            tlTienThu = (
              parseFloat(
                Number(itemPThu[0].TIEN) / Number(itemPgiao[0].TIEN)
              ).toFixed(2) * 100
            ).toFixed(2);
          }
        }

        if (itemPgiao.length > 0) {
          sumPhieuGiao += itemPgiao[0].PHIEU;
          sumTienGiao += !!itemPgiao[0].TIEN ? itemPgiao[0].TIEN : 0;
        }

        if (itemPThu.length > 0) {
          sumPhieuThu += itemPThu[0].PHIEU;
          sumTienThu += !!itemPThu[0].TIEN ? itemPThu[0].TIEN : 0;
        }

        const itemData = {
          maNV: manv,
          tenNV:
            itemPThu.length > 0
              ? itemPThu[0].TENNV_TC
              : itemPgiao.length > 0
              ? itemPgiao[0].TENNV_TC
              : "",
          phieuGiao: Number(itemPgiao.length > 0 ? itemPgiao[0].PHIEU : 0), //.toLocaleString("en-En"),
          tienGiao: Number(itemPgiao.length > 0 ? itemPgiao[0].TIEN : 0), //.toLocaleString("en-En"),
          phieuThu: Number(itemPThu.length > 0 ? itemPThu[0].PHIEU : 0), //.toLocaleString("en-En"),
          tienThu: Number(itemPThu.length > 0 ? itemPThu[0].TIEN : 0), //.toLocaleString("en-En"),
          tyLePT: tlPhieuThu,
          tyLeTT: tlTienThu,
        };
        this.tableDataSource.push(itemData);
      });
      // document.querySelector(".grid-content .table-result").deleteTFoot();
      // const footer = `<tfoot>
      // <tr>
      //   <th></th>
      //   <th><input type="text" disabled value="${this.tableDataSource.length}" class="form-control"></th>
      //   <th></th>
      //   <th><input type="text" disabled value="${Number(sumPhieuGiao).toLocaleString("en-En")}" class="form-control"</th>
      //   <th><input type="text" disabled value="${Number(sumTienGiao).toLocaleString("en-En")}" class="form-control"</th>
      //   <th><input type="text" disabled value="${Number(sumPhieuThu).toLocaleString("en-En")}" class="form-control"</th>
      //   <th><input type="text" disabled value="${Number(sumTienThu).toLocaleString("en-En")}" class="form-control"</th>
      //   <th></th>
      //   <th></th>
      // </tr>
      // </tfoot>`
      // $('.grid-content .table-result').append(footer);
      this.tongPhieuGiao = Number(sumPhieuGiao).toLocaleString("en-En");
      this.tongPhieuThu = Number(sumPhieuThu).toLocaleString("en-En");
      this.tongTienGiao = Number(sumTienGiao).toLocaleString("en-En");
      this.tongTienThu = Number(sumTienThu).toLocaleString("en-En");
    },
    async getNVTCProcess(value) {
      // console.log(value)
      if (value) {
        this.daiLy = value.maNVTC;
        this.fetchTyLeThuNVThuCuoc();
      }
    },
    onTimKiemNVTC() {
      this.$refs.dlgTimKiemNVTC.openDialog();
    },
    onExport() {
      if(this.tableDataSource.length <= 0){
        this.$toast.error("Không có dữ liệu để xuất Excel!");
        return;
      }
      const itemData = {
        maNV: this.tableDataSource.length,
        tenNV: "",
        phieuGiao: this.tongPhieuGiao,
        tienGiao: this.tongTienGiao,
        phieuThu: this.tongPhieuThu,
        tienThu: this.tongTienThu,
        tyLePT: "",
        tyLeTT: "",
      };
      const exportData = JSON.parse(JSON.stringify(this.tableDataSource));
      exportData.push(itemData);

      // const tHeader = this.fieldsTinhHinhThuNo.map((item) => item.label);
      // const filterVal = this.fieldsTinhHinhThuNo.map((item) => item.key);
      // const data = this.formatJson(filterVal, exportData);
      // export_json_to_excel({
      //   header: tHeader,
      //   data,
      //   filename: `export`,
      //   sheetname: "export",
      //   bookType: "xlsx",
      // });

      let excelExportProperties = {
        dataSource: exportData,
      };
      this.$refs.grdKetQua.$refs.grid.excelExport(excelExportProperties);
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          return v[j];
        })
      );
    },
    doSearchTheoNVTC() {
      if (
        this.daiLy !== null &&
        this.daiLy !== "" &&
        this.daiLy.trim() !== ""
      ) {
        this.daily = this.daily.trim();
        this.fetchTyLeThuNVThuCuoc();
      }
    },
    async chonNVTCHandler(e) {
      //console.log(e)
      this.daiLy = e.MANV_TC;
      this.fetchTyLeThuNVThuCuoc();
      /*
      let vdaily = e.NHANVIEN_ID

      this.params.p_daily = e.MANV_TC;
      let nv_quanly = " - NV Quản lý: ";
      let v_ten_nvql = ''
      let v_so_dt = ''
      let res = await this.getDanhSachNhanVienQLTheoDaiLy(vdaily)

      if(res.data.error_code === 'BSS-00000000'
        && res.data.data !== undefined
        && res.data.data.length > 0)
      {
        v_ten_nvql = res.data.data[0].MA_NV + '-' + res.data.data[0].TEN_NV
        v_so_dt = res.data.data[0].SO_DT === null ? '' : res.data.data[0].SO_DT
      }

      this.params.p_daily = "Tuyến thu: " + e.MA_TUYENTHU + " - Đại lý: " + e.MANV_TC + nv_quanly + ': ' + v_ten_nvql + " - " + v_so_dt;
      */
    },
  },
  created() {
    EventBus.$on("chukycuocChange", this.chukycuocChangeHandler);
    EventBus.$on("chonNVTC", this.chonNVTCHandler);
    // this.mounted = false;
    // this.clearData();
    // this.kyHoaDon = "01/01/2021";
    this.fetchDanhSachTinhCuocTN();
  },
  destroyed() {
    EventBus.$off("chukycuocChange", this.chukycuocChangeHandler);
    EventBus.$off("chonNVTC", this.chonNVTCHandler);
  },
};
</script>
