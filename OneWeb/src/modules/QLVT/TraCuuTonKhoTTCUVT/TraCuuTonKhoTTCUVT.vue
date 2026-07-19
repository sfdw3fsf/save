<template src='./TraCuuTonKhoTTCUVT.html'></template>
<style scoped src='./TraCuuTonKhoTTCUVT.scss'></style>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import ClickOutside from "vue-click-outside";
import moment from "moment";
export default {
  components: { breadcrumb },
  name: "TraCuuTonKhoTTCUVT",
  mounted() {},
  watch: {
    listID_Kho: function (data) {
      this.khoSelected = "";
      console.log("listID_Kho", data);
      data.forEach((element) => {
        this.khoSelected += `, ${element}`;
      });
      this.khoSelected = this.khoSelected.substring(2);
    },
  },
  computed: {},
  created() {
    this.fetchDanhSachKho();
  },
  data() {
    return {
      header: {
        title: "Tra cứu tồn kho TTCUVT",
        list: [
          {
            name: "Quản lí vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tra cứu vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tra cứu tồn kho TTCUVT",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      DanhSachTonKhoDb: [],
      khoSelected: "",
      DSTraCuuTonKho: [],
      statusListKho: false,
      denNgay: new Date(),
      listKhoID: [],
      showListKho: [],
      listID_Kho: [],
      pageInfo: {
        pageNo: 1,
        pageSize: 10,
        totalElement: 10,
      },
    };
  },
  methods: {
    ...mapActions("tracuutonkhoTTCUVT", ["getTraCuuVatTu", "getDanhSachKho"]),
    gridDSVatTu_selectedRowChanged() {},
    gridKho_selectedRowChanged() {},
    gridKho_selectedData_checkbox(dataItem) {
      this.listKhoID = [];
      this.listKhoID = dataItem;
      this.showListKho = [];
      this.listID_Kho = [];
      this.listKhoID.forEach((el) => {
        this.DanhSachTonKhoDb.forEach((data) => {
          if (el === data.khoID) {
            this.showListKho.push({ KHO_ID: data.khoID });
            this.listID_Kho.push(data.tenKho);
          }
        });
      });
      // this.showListKho = JSON.stringify(this.showListKho);
      console.log("this.showListKho", this.showListKho);
    },
    async fetchTraCuuVatTu() {
      this.loading(true);
      console.log("test", this.showListKho);
      if (!this.khoSelected) {
        this.$root.toastError("Vui lòng chọn Kho");
        this.$refs.Kho.focus();
      } else {
        let data = {
          dataRequest: {
            khoId: JSON.stringify(this.showListKho),
            ngayCT: moment(this.denNgay).format("DD/MM/YYYY"),
            chungTuId: 0,
          },
          pageNo: this.pageInfo.pageNo,
          pageSize: this.pageInfo.pageSize,
        };
        const response = await this.getTraCuuVatTu(data);
        if (response.error_code === "BSS-00000000") {
          this.DSTraCuuTonKho = response.data;
        }
        if (this.DSTraCuuTonKho.length == 0) {
          this.$root.toastError("Không có dữ liệu!");
        }
        console.log("this.DSTraCuuTonKho", this.DSTraCuuTonKho);
      }
      this.loading(false);
    },
    async fetchDanhSachKho() {
      this.loading(true);
      const response = await this.getDanhSachKho();
      if (response.error_code === "BSS-00000000") {
        this.showListKho = [];
        this.listID_Kho = [];
        this.DanhSachTonKhoDb = response.data;
      }
      console.log("this.DanhSachTonKhoDb", this.DanhSachTonKhoDb);
      this.loading(false);
    },
    checkBlackedOutKho() {
      this.$refs.Kho.select();
    },
    hide: function () {
      this.statusListKho = false;
    },
    async pageChanged_DSTraCuuTonKho(e) {
      this.pageInfo.pageNo = e.pageIndex;
      this.pageInfo.pageSize = e.pageSize;
      this.pageInfo.totalElement = e.totalElement;
      await this.fetchTraCuuVatTu();
    },
  },
  directives: {
    ClickOutside,
  },
};
</script>
