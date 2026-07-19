<template src="./index.html"></template>
<style src="./styles.scss"  scoped>
</style>

<script>
import API from "../api/TraCuuNoVinaphone";
import KyCuoc from "../components/KyCuoc.vue";
import moment from "moment";

export default {
  components: {
    appKyCuoc: KyCuoc,
  },
  data() {
    return {
      pKyCuoc: "",
      maKH: "",
      maTB: "",
      type: 1,
      columnMaKH: [
        {
          fieldName: "MA_KH",
          headerText: "Mã khách hàng",
          allowFiltering: true,
        },
        {
          fieldName: "CHUKYNO",
          headerText: "Chu kỳ nợ",
          allowFiltering: true,
        },
        {
          fieldName: "TONGNO",
          headerText: "Tiền nợ",
          allowFiltering: true,
          cssClass: "text-tienno",
        },
      ],
      columnMaTB: [
        {
          fieldName: "MA_TB",
          headerText: "Mã thuê bao",
          allowFiltering: true,
        },
        {
          fieldName: "CHUKYNO",
          headerText: "Chu kỳ nợ",
          allowFiltering: true,
        },
        {
          fieldName: "TONGNO",
          headerText: "Tiền nợ",
          allowFiltering: true,
          cssClass: "text-tienno",
        },
      ],
      dsChuky: [],
      dsKhoanMuc: [],
      dsThueBao: [],
      kyHD: "",
      dsMaTT: [],
    };
  },
  watch: {
    type: {
      handler: async function (value) {
        this.maKH = "";
        this.maTB = "";
      },
    },
  },
  created() {},
  mounted() {},
  methods: {
    handleKyCuoc(e) {
      this.pKyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
      this.kyHD = e.p_kyhoadon;
    },
    onEnterMaKH() {
      if (this.maKH.trim() != "") {
        this.fetchTraCuuMaKH(this.maKH);
      } else {
        this.$root.toastError("Chưa nhập mã khách hàng!");
        this.$refs.refMaKH.focus();
        return;
      }
    },
    onEnterMaTB() {
      if (this.maTB.trim() != "") {
        this.fetchTraCuuMaTB(this.maTB);
      } else {
        this.$root.toastError("Chưa nhập mã thuê bao!");
        this.$refs.refMaTB.focus();
        return;
      }
    },
    onClickTimKiem() {
      if (this.type == 1) {
        if (this.maKH.trim() == "") {
          this.$root.toastError("Chưa nhập mã khách hàng!");
          return;
        }
        this.fetchTraCuuMaKH(this.maKH);
      } else {
        if (this.maTB.trim() == "") {
          this.$root.toastError("Chưa nhập mã thuê bao!");
          return;
        }
        this.fetchTraCuuMaTB(this.maTB);
      }
    },
    async fetchTraCuuMaKH(txt) {
      if (!this.$auth.getMaCCBS()) {
        this.$root.toastError(
          "Người dùng chưa được cấp quyền truy cập dữ liệu trên Vinaphone. Liên hệ admin !"
        );
        return;
      }
      const chukyno = moment(this.kyHD).format("MMYYYY");
      const chukyno_vnp = moment(this.kyHD).format("MMYYYY");
      const ma_tinh = this.$auth.getMaTinh() || "";

      this.fetchNapDSThueBao_TheoMTT();
      const param1 = {
        kieuno: "",
        ma_tinh: ma_tinh,
        ma_kh: txt,
        kyhoadon: chukyno,
        so_tb: "",
        chukyno: chukyno_vnp,
        theokhoanmuc: "0",
        userid: this.$auth.getMaCCBS() || "",
      };
      const param2 = {
        kieuno: "",
        ma_tinh: ma_tinh,
        ma_kh: txt,
        kyhoadon: chukyno,
        so_tb: "",
        chukyno: chukyno_vnp,
        theokhoanmuc: "1",
        userid: this.$auth.getMaCCBS() || "",
      };
      const param3 = {
        kieuno: "",
        ma_tinh: ma_tinh,
        ma_kh: txt,
        kyhoadon: chukyno,
        so_tb: "",
        chukyno: chukyno_vnp,
        theokhoanmuc: "11",
        userid: this.$auth.getMaCCBS() || "",
      };
      await this.fetchThuNoTamThuTraTruocNoChiTiet(param1, 1);
      await this.fetchThuNoTamThuTraTruocNoChiTiet(param2, 2);
      await this.fetchThuNoTamThuTraTruocNoChiTiet(param3, 3);
      if (
        this.dsChuky.length <= 0 &&
        this.dsKhoanMuc.length <= 0 &&
        this.dsThueBao.length <= 0
      ) {
        this.$root.toastError("Không tìm thấy thông tin mã thanh toán này!");
        return;
      }
    },
    async fetchNapDSThueBao_TheoMTT() {
      try {
        const params = {
          kyCuoc: moment(this.kyHD).format("YYYYMM") + "01",
          ma: this.maKH,
          chiTiet: 1,
        };
        const response = await API.getMaTTNeo(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length >= 0
        ) {
          this.dsMaTT = response.data.data;
        }
      } catch (error) {
        this.dsMaTT = [];
        // this.$root.toastError(error.data.message_detail);
      } finally {
      }
    },
    async fetchTraCuuMaTB(txt) {
      if (!this.$auth.getMaCCBS()) {
        this.$root.toastError(
          "Người dùng chưa được cấp quyền truy cập dữ liệu trên Vinaphone. Liên hệ admin !"
        );
        return;
      }
      const ma_tinh = this.$auth.getMaTinh() || "";
      const chukyno = moment(this.kyHD).format("MMYYYY");
      const chukyno_vnp = moment(this.kyHD).format("MMYYYY");
      const param1 = {
        kieuno: "",
        ma_tinh: ma_tinh,
        ma_kh: "",
        kyhoadon: chukyno,
        so_tb: txt,
        chukyno: chukyno_vnp,
        theokhoanmuc: "0",
        userid: this.$auth.getMaCCBS() || "",
      };
      const param2 = {
        kieuno: "",
        ma_tinh: ma_tinh,
        ma_kh: "",
        kyhoadon: chukyno,
        so_tb: txt,
        chukyno: chukyno_vnp,
        theokhoanmuc: "1",
        userid: this.$auth.getMaCCBS() || "",
      };
      const param3 = {
        kieuno: "",
        ma_tinh: ma_tinh,
        ma_kh: "",
        kyhoadon: chukyno,
        so_tb: txt,
        chukyno: chukyno_vnp,
        theokhoanmuc: "11",
        userid: this.$auth.getMaCCBS() || "",
      };
      await this.fetchThuNoTamThuTraTruocNoChiTiet(param1, 1);
      await this.fetchThuNoTamThuTraTruocNoChiTiet(param2, 2);
      await this.fetchThuNoTamThuTraTruocNoChiTiet(param3, 3);
      if (
        this.dsChuky.length <= 0 &&
        this.dsKhoanMuc.length <= 0 &&
        this.dsThueBao.length <= 0
      ) {
        this.$root.toastError("Không tìm thấy thông tin mã thuê bao này!");
        return;
      }
    },
    async fetchThuNoTamThuTraTruocNoChiTiet(dataPost, type) {
      let result = [];
      try {
        this.loading(true);
        const data = dataPost;
        const response = await API.getDSThuNoTamThuTraTruoc(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          result = response.data.data;
        } else {
          result = [];
          //   this.$root.toastError("Không có dữ liệu. Mời thử lại!");
        }
      } catch (error) {
        result = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        if (type == 1) {
          this.dsChuky = result;
        } else if (type == 2) {
          this.dsKhoanMuc = result;
        } else {
          this.dsThueBao = result;
        }
        this.loading(false);
      }
    },
    onClickTimMaTT() {
      this.$bvModal.show("modal-matt");
    },
  },
  computed: {
    getColumns() {
      return this.type == 1 ? this.columnMaKH : this.columnMaTB;
    },
  },
};
</script>
