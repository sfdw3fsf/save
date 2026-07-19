<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import api from "./api";
export default {
  components: { breadcrumb },
  name: "SearchFreightDebt",
  props: {
    isPopup: {
      type: Boolean,
    },
  },
  mounted() {
    // this.clearData()
    // this.$refs.maKHGocInput.focus()
    this.addKeyDownEvent();
  },
  destroyed() {},
  computed: {
    // enableDeleteBnt() {
    //   return this.gridboxDSPTT.selected !== null
    // }
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val);
    },
    openedPopup: function (val, oldval) {
      if (val) {
        this.removeKeyDownEvent();
      } else {
        this.addKeyDownEvent();
      }
    },
  },
  data() {
    return {
      loading: false,
      header: {
        title: "TRA CỨU NỢ CƯỚC",
        list: [],
      },
      gridboxKQTK: {
        data: [],
        selected: null,
        columns: [
          {
            fieldName: "STT",
            headerText: "STT",
            allowFiltering: true,
            allowSorting: false,
            width: "50",
          },
          {
            fieldName: "DETAIL",
            headerText: "Chi tiết",
            allowFiltering: true,
            allowSorting: false,
            width: "200",
          },
          {
            fieldName: "PHAITHU",
            headerText: "Số phải thu",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "THUDUOC",
            headerText: "Số đã thu được trong tuần",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "THULK",
            headerText: "Số đã thu được lũy kế",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "CONPHAITHU",
            headerText: "Số còn phải thu",
            allowFiltering: true,
            allowSorting: false,
          },
        ],
        rowBuffer: {
          tuoino: [
            {
              STT: "A",
              DETAIL: "B",
              PHAITHU: "1",
              THUDUOC: "2",
              THULK: "3",
              CONPHAITHU: "4=1-3",
            },
            {
              STT: "1",
              DETAIL: "Tổng số",
              PHAITHU: "",
              THUDUOC: "",
              THULK: "",
              CONPHAITHU: "",
            },
            {
              STT: "2",
              DETAIL: "Phân loại theo tuổi nợ",
              PHAITHU: "",
              THUDUOC: "",
              THULK: "",
              CONPHAITHU: "",
            },
          ],
          hinhthuc: [
            {
              STT: "3",
              DETAIL: "Phân loại theo các hình thức thu nợ",
              PHAITHU: "",
              THUDUOC: "",
              THULK: "",
              CONPHAITHU: "",
            },
          ],
          loaikh: [
            {
              STT: "4",
              DETAIL: "Phân loại theo đối tương nợ",
              PHAITHU: "",
              THUDUOC: "",
              THULK: "",
              CONPHAITHU: "",
            },
          ],
        },
      },
      openedPopup: false,
      maKH: "",
      soTB: "",
      searchDate: {
        isEnabledFromDate: false,
        isEnabledToDate: false,
        fromDate: moment(new Date()).format("DD/MM/YYYY"),
        toDate: moment(new Date()).format("DD/MM/YYYY"),
        dateconfig: {
          altFormat: "d/m/Y",
          altInput: true,
          dateFormat: "d/m/Y",
          allowInput: true,
        },
      },
    };
  },
  methods: {
    addKeyDownEvent() {
      window.addEventListener("keydown", this.onF3KeyDown);
    },
    removeKeyDownEvent() {
      window.removeEventListener("keydown", this.onF3KeyDown);
    },
    onF3KeyDown(event) {
      if (event.keyCode === 114) {
        event.preventDefault();
        this.search();
      }
    },
    GetData(response) {
      // console.log(response)
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },
    GetDataObj(response) {
      // console.log(response)
      if (
        (response.data.error === 200 || response.data.error === "200") &&
        response.data.error_code === "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        throw new Error(response.data.message);
      }
    },
    async search() {
      // if (this.maKH !== '' || this.soTB !== '') {
      this.gridboxKQTK.data = [];
      this.gridboxKQTK.selected = null;
      const params = {
        ma_tinh: "",
        ma_kh: this.maKH,
        thang: moment(new Date()).format("MM"),
        tu_ngay: this.searchDate.fromDate,
        so_tb: `84${this.soTB}`,
        nam: moment(new Date()).format("YYYY"),
        den_ngay: this.searchDate.toDate,
      };
      await this.getDataTuoino(params);
      await this.getDataHinhThuc(params);
      await this.getDataLoaiKH(params);
      if (this.gridboxKQTK.data.length > 0) {
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    },
    async getDataTuoino(params) {
      let data = [];
      try {
        this.loading = true;
        data = this.GetData(await api.ts_tracuu_nocuoc_cv87_tuoino(this.axios, params));
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
      if (data.length > 0) {
        this.gridboxKQTK.data = this.gridboxKQTK.data.concat(
          this.gridboxKQTK.rowBuffer.tuoino
        );
        this.gridboxKQTK.data = this.gridboxKQTK.data.concat(data);
      }
    },
    async getDataHinhThuc(params) {
      let data = [];
      try {
        this.loading = true;
        data = this.GetData(await api.ts_tracuu_nocuoc_cv87_hinhthuc(this.axios, params));
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
      if (data.length > 0) {
        this.gridboxKQTK.data = this.gridboxKQTK.data.concat(
          this.gridboxKQTK.rowBuffer.hinhthuc
        );
        this.gridboxKQTK.data = this.gridboxKQTK.data.concat(data);
      }
    },
    async getDataLoaiKH(params) {
      let data = [];
      try {
        this.loading = true;
        data = this.GetData(await api.ts_tracuu_nocuoc_cv87_loaikh(this.axios, params));
      } catch (err) {
        console.error(err);
      } finally {
        this.loading = false;
      }
      if (data.length > 0) {
        this.gridboxKQTK.data = this.gridboxKQTK.data.concat(
          this.gridboxKQTK.rowBuffer.loaikh
        );
        this.gridboxKQTK.data = this.gridboxKQTK.data.concat(data);
      }
    },
    selectedItemsChangedGridboxKQTK(items) {
      if (items.length > 0) this.gridboxKQTK.selected = items[0];
    },
    clearData() {
      this.searchDate.fromDate = moment(new Date()).format("DD/MM/YYYY");
      this.searchDate.toDate = moment(new Date()).format("DD/MM/YYYY");
      this.gridboxKQTK.data = [];
      this.gridboxKQTK.selected = null;
      this.maKH = "";
      this.soTB = "";
    },
  },
};
</script>
