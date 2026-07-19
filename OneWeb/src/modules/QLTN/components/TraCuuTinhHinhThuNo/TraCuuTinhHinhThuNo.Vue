<template src="./TraCuuTinhHinhThuNo.html"></template>
<style scoped src="./TraCuuTinhHinhThuNo.scss"></style>
<script>
import { mapState, mapGetters, mapActions } from "vuex";
import monthSelectPlugin from "flatpickr/dist/plugins/monthSelect/index.js";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import KyCuoc from "./KyCuoc.vue";
import EventBus from "../../../../utils/eventBus.js";
import BssLocalPagination from "../../../../components/BssLocalPagination.vue";

export default {
  components: { breadcrumb, appKyCuoc: KyCuoc, BssLocalPagination },
  name: "TraCuuTinhHinhThuNo",
  prop: {},
  computed: {
    ...mapState("tracuutinhhinhthuno", [
      "danhSachTinhCuocTN",
      "tyLeThuNVThuCuoc",
    ]),
    ...mapState("qltnCommon", ["thongtinKyHoaDon"]),
    ...mapGetters("tracuutinhhinhthuno", ["tyLeThuNVThuCuocGetters"]),
  },
  data() {
    return {
      header: {
        title: "TRA CỨU TÌNH HÌNH THU NỢ CỦA NHÂN VIÊN THU CƯỚC",
        list: [
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          { name: "Quản lý thu nợ", link: { name: "Ui.cards" } },
          {
            name: "Tra cứu thu nợ",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      dateConfig: {
        altInput: true,
        // dateFormat: 'm/Y',
        // altFormat: 'm/Y',my
        plugins: [
          new monthSelectPlugin({
            shorthand: true,
            dateFormat: "m/y",
            altFormat: "m/Y",
          }),
        ],
      },
      dateConfig2: {
        altInput: true,
        dateFormat: "m/d/Y",
        altFormat: "m/d/Y",
      },
      p_ngayTT: new Date(),
      p_ngayDen: new Date(),
      ky_hoadon: new Date(),
      selectedChuKy: 1,
      fieldsTinhHinhThuNo: [
        {
          key: "STT",
          label: "  ",
          thClass: "center",
        },
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
      arraySearchInput: [
        { key: "maNV", value: "" },
        { key: "tenNV", value: "" },
        { key: "phieuGiao", value: "" },
        { key: "tienGiao", value: "" },
        { key: "phieuThu", value: "" },
        { key: "tienThu", value: "" },
        { key: "tyLePT", value: "" },
        { key: "tyLeTT", value: "" },
      ],
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
    };
  },
  watch: {},
  mounted() {
    console.log(this.thongtinKyHoaDon.kyHoaDonSelected);
  },
  methods: {
    ...mapActions("tracuutinhhinhthuno", [
      "getDanhSachTinhCuocTN",
      "getTyLeThuNVThuCuoc",
      "getObjSearch",
      "clearData",
    ]),
    async fetchDanhSachTinhCuocTN() {
      this.loading(true);
      let pNguoiDungId = 3069;
      try {
        const response = await this.getDanhSachTinhCuocTN(pNguoiDungId);
        console.log(response);
        if (response.data.error_code === "BSS-00000000") {
          this.loading(false);
          this.DSTinhCuocTN = response.data.data;
          if (response.data.data !== "") {
            await this.fetchTyLeThuNVThuCuoc();
          }
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail);
      }
      return response;
    },
    async fetchTyLeThuNVThuCuoc() {
      this.listTimKiem = [];
      this.loading(true);
      let data = {
        pPhanVungId: 26,
        pKyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
        pTuNgay: moment(this.$data.p_ngayTT).format("DD/MM/YYYY"),
        pDenNgay: this.isDisabled
          ? moment(this.$data.p_ngayDen).format("DD/MM/YYYY")
          : "",
        pDanhSachNVId: this.DSTinhCuocTN,
        pMaNVTC: null,
      };
      console.log("test1", data);
      const response = await this.getTyLeThuNVThuCuoc(data)
        .then((res) => {
          if (res.data.error_code === "BSS-00000000") {
            this.checkPhieu(res.data.data);
            this.loading(false);
          }
        })
        .catch((err) => {
          this.loading(false);
          this.$toast.error(err.response.data.message_detail);
        });
      return response;
    },
    checkPhieu(data) {
      data.forEach((element) => {
        this.listMaNV.push(element.MANV_TC);
      });
      const temp = new Set(this.listMaNV);
      const value = [];
      this.value = Array.from(new Set(temp));
      this.value.forEach((temp1) => {
        let value2 = {
          maNV: "",
          tenNV: "",
          phieuGiao: 0,
          tienGiao: 0,
          phieuThu: 0,
          tienThu: 0,
          tyLePT: 0,
          tyLeTT: 0,
        };
        data.forEach((temp2) => {
          if (temp1 === temp2.MANV_TC) {
            value2.tenNV = temp2.TENNV_TC;
            if (temp2.KIEU === "2") {
              value2.phieuThu = temp2.PHIEU;
              value2.tienThu = parseFloat(temp2.TIEN).toFixed(2);
            } else {
              value2.phieuGiao = temp2.PHIEU;
              value2.tienGiao = parseFloat(temp2.TIEN).toFixed(2);
            }
          }
        });
        if (value2.phieuGiao === "0") {
          value2.tyLePT = 0;
        } else {
          value2.tyLePT =
            (parseFloat(value2.phieuThu) / parseFloat(value2.phieuGiao)) * 100;
          value2.tyLePT = value2.tyLePT.toFixed(2);
        }
        if (value2.tienGiao === "0") {
          value2.tyLeTT = 0;
        } else {
          value2.tyLeTT =
            (parseFloat(value2.tienThu) / parseFloat(value2.tienGiao)) * 100;
          value2.tyLeTT = value2.tyLeTT.toFixed(2);
        }
        (value2.maNV = temp1),
          (this.tongTienThu += parseFloat(value2.tienThu)),
          (this.tongTienGiao += parseFloat(value2.tienGiao)),
          (this.tongPhieuThu += parseFloat(value2.phieuThu)),
          (this.tongPhieuGiao += parseFloat(value2.phieuGiao));
        this.listTimKiem.push(value2);
      });
    },
    chukycuocChangeHandler(e) {
      this.thongtinKyHoaDon.kyHoaDonSelected =
        moment(e.p_kyhoadon).format("YYYYMM") + "01";
      this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no;
      // console.log(this.thongtinKyHoaDon)
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
  },
  created() {
    EventBus.$on("chukycuocChange", this.chukycuocChangeHandler);
    // this.mounted = false;
    // this.clearData();
  },
  destroyed() {
    EventBus.$off("chukycuocChange", this.chukycuocChangeHandler);
  },
};
</script>
