<template src='./PhanChiaTuyenThu.html'></template>
<style src='./PhanChiaTuyenThu.scss' scoped></style>
<script>
import Vue from "vue";
import xlsx from "xlsx";
import { mapActions, mapState } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import PopupDSChuaPhanTuyen from "./popup-DSChuaPhanTuyen.vue";
import API from "../api/Commons";
import PopupCapNhatTuyenThu from "./components/frmCapNhatTuyenThu.vue";

export default {
  components: { breadcrumb, PopupDSChuaPhanTuyen, PopupCapNhatTuyenThu },
  name: "PhanChiaTuyenThu",
  mounted() {},
  computed: {
    ...mapState("quanlythutuin", ["danhsachNhanVienTC"]),
    danhsachPhanChiaTuyenThu: [],
    ...mapState("phanchiatuyenthu", [
      "danhsachTuyenThu",
      "danhsachDotGiao",
      "danhsachQuan",
      "danhsachPhuong",
      "danhsachPho",
      "danhsachAp",
      "danhsachPhanChiaTuyenThu",
      "danhsachChuaPhanTuyen",
    ]),
    PTAggregatesColumns() {
      return [
        {
          field: "STT",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.customAggregate,
        },
      ];
    },
  },
  data() {
    return {
      header: {
        title: "PHÂN CHIA TUYẾN THU THEO",
        list: [
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          {
            name: "Quản lý đại lý",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Phân chia tuyến thu",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      pKyCuoc:
        moment(new Date())
          .subtract(1, "months")
          .endOf("month")
          .format("YYYYMM") + "01",
      tuyenThu: "1",
      fromNVThuCuoc: "",
      toNVThuCuoc: "",
      danhSachTuTuyenThu: [],
      selectTuTuyenThu: "",
      danhSachDenTuyenThu: [],
      selectDenTuyenThu: "",
      danhSachDenDotGiao: [],
      selectDenDotGiao: "",
      selectQuan: "",
      selectPhuong: "",
      selectPho: "",
      selectKhu: "",
      selectAp: "",
      dsPho: [],
      dsKhu: [],
      dsAp: [],
      enabeQuan: false,
      enabePhuong: false,
      enabePho: false,
      enabeAp: false,
      enabeKhu: false,
      enableSo: false,
      tuSo: 0,
      denSo: 0,
      ngoaiLe: false,
      nhoThu: false,
      fields: [
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          width: 80,
          //   isSumSelected:true,isSum:true,
        },
        {
          fieldName: "MA_TT",
          headerText: "Mã thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "MATB_DD",
          headerText: "Mã đại diện",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_BC",
          headerText: "Địa chỉ báo cước",
          allowFiltering: true,
        },
        {
          fieldName: "SONHA",
          headerText: "Số nhà",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_NH",
          headerText: "Ngân hàng",
          allowFiltering: true,
        },
        {
          fieldName: "THUTU_IN",
          headerText: "Thứ tự in",
          allowFiltering: true,
        },
        {
          fieldName: "DOTGIAO_ID",
          headerText: "Đợt giao",
          allowFiltering: true,
        },
        {
          fieldName: "MAIN_GHEP",
          headerText: "Mã in ghép",
          allowFiltering: true,
        },
        {
          fieldName: "THANHTOAN_ID",
          headerText: "Mã phát hóa đơn",
          allowFiltering: true,
        },
        {
          fieldName: "DV_KHAC",
          headerText: "DV khác",
          allowFiltering: true,
        },
      ],
      labelFrom: "Chuyển từ tuyến đường",
      labelTo: "Chuyển đến tuyến đường",
      labelTuyenThu: "Tuyến thu",
      danhSachTuDotGiao: [],
      selectTuDotGiao: "",
      isCountSelected: 0,
      customTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" disabled style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },
      totalItems: 0,
      dsTemp:[]
    };
  },
  methods: {
    ...mapActions("quanlythutuin", ["getDanhSachNVThuCuoc"]),
    ...mapActions("phanchiatuyenthu", [
      "getDanhSachTuyenThuByNhanVien",
      "getDanhSachDotGiao",
      "getDanhSachQuan",
      "getDanhSachPhuong",
      "getDanhSachPho",
      "getDanhSachAp",
      "getDanhSachPhanChiaTuyenThu",
      "getDanhSachChuaPhanTuyen",
      "capNhatDanhSachPhanChiaTuyenThu",
      "clearData",
    ]),
    DanhSach() {
      if (this.fromNVThuCuoc === "") {
        this.$root.toastError("Phải chọn nhân viên thu cước!");
        return;
      }
      if (this.tuyenThu == "1") {
        if (this.selectTuTuyenThu === "") {
          this.$root.toastError("Nhân viên này không có tuyến thu!");
          return;
        }
      } else {
        if (this.selectTuDotGiao === "") {
          this.$root.toastError("Chưa có đợt giao cho nhân viên này !");
          return;
        }
      }
      this.fetchDanhSachKH(0, 10);
    },
    async fetchDanhSachKH(page, size) {
      this.loading(true);
      let data = {
        pNhanVienTCId: this.fromNVThuCuoc,
        pTuyenThuId:
          this.tuyenThu === "1" ? this.selectTuTuyenThu : 0,
        pDotGiaoId: this.tuyenThu == "2" ? this.selectTuDotGiao : 0,
        pNgoaiLe: this.ngoaiLe ? 1 : 0,
        pNhoThu: this.nhoThu ? 3 : 0,
        pKieu: this.tuyenThu,
        pQuanId: this.enabeQuan ? this.selectQuan : "",
        pPhuongId: this.enabePhuong ? this.selectPhuong : "",
        pPhoId: this.enabePho ? this.selectPho : "",
        pApId: this.enabeAp ? this.selectAp : "",
        pKhuId: this.enabeKhu ? this.selectKhu : "",
        // pageNum: page + 1,
        // pageSize: size,
        // totalRow: 1,
      };
      const response = await this.getDanhSachPhanChiaTuyenThu(data);
      if (response.data.error_code === "BSS-00000000") {
        this.loading(false);
        if (response.data.data.length > 0) {
        //   this.totalItems = response.data.data.totalElement;
          this.totalItems = response.data.data.length;
        } else {
          this.totalItems = 0;
          this.$root.toastError("Không có dữ liệu, vui lòng thử lại!");
        }
      } else {
        this.loading(false);
        this.totalItems = 0;
        this.$root.toastError(response.message_detail);
      }
      this.isCountSelected = 0;
      return response;
    },
    gridChanged(args) {
      this.fetchDanhSachKH(args.pageIndex, args.pageSize);
    },
    selectedItemsChanged(data) {
      this.isCountSelected = data.length;
      let dataGridObj = this.$refs.danhSach.$refs.grid.ej2Instances;
      dataGridObj.aggregateModule.refresh();
    },
    XuatExcel() {
      if (this.danhsachPhanChiaTuyenThu.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.danhsachPhanChiaTuyenThu);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    async CapNhat() {
      if (this.toNVThuCuoc === "") {
        this.$root.toastError(
          "Phải chọn nhân viên thu cước chuyển đến tuyến đường!"
        );
        return;
      }
      if (this.danhsachPhanChiaTuyenThu.length <= 0) {
        this.$root.toastError("Không có hóa đơn để chuyển tuyến !");
        return;
      }
      const dsSelected = this.$refs.danhSach.getSelectedRecords() || [];
      this.dsTemp = dsSelected;
      if (dsSelected.length === 0) {
        this.$root.toastError("Phải chọn khách hàng muốn cập nhật !");
        return;
      }
      if (this.selectDenTuyenThu == "") {
        this.$root.toastError("Nhân viên này không có tuyến thu !");
        return;
      }
      if (this.selectTuTuyenThu == "") {
        this.$root.toastError("Nhân viên chuyển đến không có tuyến thu !");
        return;
      }
      if (this.tuyenThu == "1") {
        if (this.selectTuTuyenThu == this.selectDenTuyenThu) {
          this.$root.toastError("Tuyến thu mới trùng tuyến thu cũ !");
          return;
        }
      } else {
        if (this.selectDenDotGiao == "") {
          this.$root.toastError("Chưa có đợt giao cho nhân viên này !");
          return;
        }
        if (
          this.fromNVThuCuoc == this.toNVThuCuoc &&
          this.selectTuDotGiao == this.selectDenDotGiao
        ) {
          this.$root.toastError(
            "Bạn không được chọn cùng Nhân viên thu và đợt giao!"
          );
          return;
        }
      }
      this.$bvModal.show("modal-capnhattuyenthu");
      //   const dsCapNhat = this.danhsachPhanChiaTuyenThu.filter((item) =>
      //     this.dsChon.includes(item.THANHTOAN_ID)
      //   );
    },
    async onClickCapNhat(dsSelected){
      const dsCapNhat = dsSelected.map((item,index)=>({maTT:item.MA_TT,thanhToanId:item.THANHTOAN_ID,thuTuInCu:item.THUTU_IN,thuTuInMoi:++index}));
      const data = {
        kyCuoc: this.pKyCuoc,
        nvtcCuId: this.fromNVThuCuoc,
        tuyenThuCuId: this.tuyenThu == '1' ? this.selectTuTuyenThu : 0,
        nvtcMoiId: this.toNVThuCuoc,
        tuyenThuMoiId: this.selectDenTuyenThu,
        dotGiaoCuId: this.tuyenThu == '2' ? this.selectTuDotGiao : 0,
        dotGiaoMoiId: this.selectDenDotGiao,
        kieu: this.tuyenThu,
        dsPhanTuyen: dsCapNhat,
      };
      this.loading(true);
      const response = await this.capNhatDanhSachPhanChiaTuyenThu(data);
      if (response.data.error_code === "BSS-00000000") {
        this.loading(false);
        if (response.data.data) {
          this.$root.toastSuccess("Cập nhật phân chia tuyến thu thành công !");
          this.fetchDanhSachKH(0, 10);
        } else {
          this.$root.toastError("Cập nhật không thành công!");
        }
      } else {
        this.loading(false);
        this.$root.toastError(response.message_detail);
      }
    },
    async ChuaPhanTuyen() {
      this.loading(true);
      const data = {
        pageNum: null,
        pageSize: 10,
        totalRow: 1,
      };
      const response = await this.getDanhSachChuaPhanTuyen(data);
      if (response.data.error_code === "BSS-00000000") {
        this.loading(false);
        if (response.data.data.data.length > 0) {
          this.$bvModal.show("modal-danhsachChuaPhanTuyen");
        } else {
          this.$root.toastError(
            "Không có khách hàng nào chưa được phân tuyến!"
          );
        }
      } else {
        this.loading(false);
        this.$root.toastError(response.message_detail);
      }
      return response;
    },
    SuaTTKH() {
      this.$router.push({ path: "/qltn/thay-doi-tt-khachhang" });
    },
    customAggregate() {
      return `${this.isCountSelected}/${this.totalItems}`;
    },
    async fetchKyCuoc() {
      const response = await API.getKyCuocHienHanh(this.axios);
      if (response.data.error_code === "BSS-00000000") {
        this.pKyCuoc = response.data.data[0].KYCUOC;
      }
    },
  },
  created() {
    this.clearData();
    let data = {
      pNguoiDungId: this.$auth.getNguoiDungID()
    };
    this.getDanhSachNVThuCuoc(data);
    this.getDanhSachQuan();
    this.fetchKyCuoc();
  },
  watch: {
    fromNVThuCuoc: {
      handler: async function (val) {
        this.clearData();
        this.totalItems = 0;
        const response = await this.getDanhSachTuyenThuByNhanVien({
          pNhanVienId: val,
        });
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.danhSachTuTuyenThu = response.data.data;
          this.selectTuTuyenThu = response.data.data[0].TUYENTHU_ID;
        } else {
          this.danhSachTuTuyenThu = [];
          this.selectTuTuyenThu = "";
        }
        const response2 = await this.getDanhSachDotGiao({
          pNhanVienId: val,
        });
        if (
          response2.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.danhSachTuDotGiao = response2.data.data;
          this.selectTuDotGiao = response2.data.data[0].DOTGIAO_ID;
        } else {
          this.danhSachTuDotGiao = [];
          this.selectTuDotGiao = "";
        }
      },
    },
    toNVThuCuoc: {
      handler: async function (val) {
        const response = await this.getDanhSachTuyenThuByNhanVien({
          pNhanVienId: val,
        });
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.danhSachDenTuyenThu = response.data.data;
          this.selectDenTuyenThu = response.data.data[0].TUYENTHU_ID;
        } else {
          this.danhSachDenTuyenThu = [];
          this.selectDenTuyenThu = "";
        }
        const response2 = await this.getDanhSachDotGiao({
          pNhanVienId: val,
        });
        if (
          response2.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.danhSachDenDotGiao = response2.data.data;
          this.selectDenDotGiao = response2.data.data[0].DOTGIAO_ID;
        } else {
          this.danhSachDenDotGiao = [];
          this.selectDenDotGiao = "";
        }
      },
    },
    selectQuan: {
      handler: async function (val) {
        const response = await this.getDanhSachPhuong({ pQuanId: val });
        if (response.data.error_code === "BSS-00000000") {
          this.selectPhuong = response.data.data[0].PHUONG_ID;
        }
      },
    },
    selectPhuong: {
      handler: async function (val) {
        const response = await this.getDanhSachPho({
          pPhuongId: val,
          pNhomPhoId: 1,
        });
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsPho = response.data.data;
          this.selectPho = response.data.data[0].ID;
        }
        const response2 = await this.getDanhSachPho({
          pPhuongId: val,
          pNhomPhoId: 2,
        });
        if (
          response2.data.error_code === "BSS-00000000" &&
          response2.data.data.length > 0
        ) {
          this.dsAp = response2.data.data;
          this.selectAp = response2.data.data[0].ID;
        }
        const response3 = await this.getDanhSachPho({
          pPhuongId: val,
          pNhomPhoId: 3,
        });
        if (
          response3.data.error_code === "BSS-00000000" &&
          response3.data.data.length > 0
        ) {
          this.dsKhu = response3.data.data;
          this.selectKhu = response3.data.data[0].ID;
        }
      },
    },
    tuyenThu: {
      handler: function (val) {
        if (val === "1") {
          this.labelFrom = "Chuyển từ tuyến đường";
          this.labelTo = "Chuyển đến tuyến đường";
          this.labelTuyenThu = "Tuyến thu";
        } else {
          this.labelFrom = "Chuyển từ đợt giao";
          this.labelTo = "Chuyển đến đợt giao";
          this.labelTuyenThu = "Đợt giao";
        }
      },
    },
    enabeQuan: {
      handler: function (val) {
        if (!val) {
          this.enabePhuong = false;
          this.enabePho = false;
          this.enabeAp = false;
          this.enabeKhu = false;
        }
      },
    },
    enabePhuong: {
      handler: function (val) {
        if (!val) {
          this.enabePho = false;
          this.enabeAp = false;
          this.enabeKhu = false;
        }
      },
    },
    enabePho: {
      handler: function (val) {
        if (!val) {
          this.enabeAp = false;
          this.enabeKhu = false;
        }
      },
    },
  },
};
</script>
