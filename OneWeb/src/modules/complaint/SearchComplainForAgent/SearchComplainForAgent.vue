<template src="./SearchComplainForAgent.html"></template>
<style scoped src="./SearchComplainForAgent.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import DanhMucAPI from "./DanhMucAPI";
import Vue from "vue";
import moment from "moment";
import gridview from "@/components/Shared/gridview";
import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);
export default {
  components: { breadcrumb, gridview },
  data: function () {
    return {
      header: {
        title: "",
      },
      list: [
        { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
        {
          name: "Lắp đặt mới",
          link: { name: "Ui.buttons" },
        },
      ],
      params: {
        p_dichvu_options: [],
        p_dichvu: null,
        p_loaihinh_options: [],
        p_loaihinh: null,
        resultLoaiHinh: [],
        p_somay_acc: null,
        p_testdvvt: null,
        p_tentt: null,
        p_trangthai: null,
        p_diachi: null,
        p_matt: null,
        p_daily: null,
        p_tuyenthu: null,
      },
      dsNhanVien_table: [],
      dsKhieuNai_table: [],
    };
  },
  validations: {},
  computed: {},
  watch: {},
  created() {
    this.loadDichvu(); 
  },
  async mounted() {},
  methods: {
    loadDichvu: function () {
      this.params.p_dichvu_options = [];
      this.params.p_dichvu = null;
      DanhMucAPI.getDSDichVu(this.axios, {})
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.dichvuvt_id, text: item.ten_dvvt });
              });
              this.params.p_dichvu_options = items;
              this.params.p_dichvu = items[0].id;
              this.loadLoaiHinh(this.params.p_dichvu);
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadLoaiHinh: function (dichvuvt_id) {
      this.params.p_loaihinh_options = [];
      this.params.p_loaihinh = null;
      DanhMucAPI.getLoaiHinhTB(this.axios, dichvuvt_id)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({ id: item.ID, text: item.NAME });
            });
            this.params.p_loaihinh_options = items;
            this.params.p_loaihinh = items[0].id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    async getDSNhanVienQLDL(dailyid) {
      this.dsNhanVien_table = [];
      try {
        this.loading(true);
        let apiParams = {
          p_daily_id: dailyid, //this.$root.token.getNguoiDungID(),
        };
        const response = await DanhMucAPI.getDSNhanVienQLDL(
          this.axios,
          apiParams
        );
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.dsNhanVien_table = response.data.data;
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loading(false);
      }
    },
    async getDSKhieuNai(dvvtid, kyhoadon, matb) {
      this.dsKhieuNai_table = [];
      //this.loading(true);
      try {
        let apiParams = {
          p_dichvu_vt_id: dvvtid,
          p_kyhoadon: kyhoadon,
          p_ma_tb: matb,
        };
        const response = await DanhMucAPI.getDSKhieuNai(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.dsKhieuNai_table = response.data.data;
        }
      } catch (error) {
        console.log(error);
      } finally {
        //this.loading(false);
      }
    },
    async searchThongTinTB(dvvtid, kyhoadon, matb) {
      let apiParams = {
        p_dichvuvt_id: dvvtid,
        p_kyhoadon: kyhoadon,
        p_ma_tb: matb,
      };
      this.loading(true);
      await DanhMucAPI.getThongTinThueBao(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            response.data.data.forEach(function (item) {
              items.push({
                ma_tt: item.ma_tt,
                ten_tt: item.ten_tt,
                diachi_tt: item.diachi_tt,
                trangthai_tb: item.trangthai_tb,
                daily_id: item.daily_id,
                ma_tuyenthu: item.ten_tt,
                tentuyen: item.tentuyen,
                tuyenthu_id: item.tuyenthu_id,
                loaihinhtb: item.loaitb_id,
              });
            });
            this.getDSNhanVienQLDL(items[0].daily_id);
          } else {
            this.$alert(
              "Không tìm thấy thông tin thuê bao " + this.params.p_somay_acc,
              {
                dangerouslyUseHTMLString: true,
                confirmButtonText: "OK",
                type: "error",
              }
            );
            this.params.p_matt = null;
            this.params.p_trangthai = null;
            this.params.p_daily = null;
            this.params.p_tentt = null;
            this.params.p_diachi = null;
            this.params.p_tuyenthu = null;
            this.dsNhanVien_table = [];
            this.dsKhieuNai_table = null;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    onSearch: function () {
      var date =
        moment(new Date())
          .subtract(1, "months")
          .endOf("month")
          .format("YYYYMM") + "01";
      if (
        this.params.p_somay_acc === null ||
        this.params.p_somay_acc.trim() === ""
      ) {
        this.$alert("Trường Số máy/ account bắt buộc nhập.", "Thông báo", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "error",
        });
        this.$refs.inputSoMayAcc.focus();
        return false;
      } else {
        this.searchThongTinTB(
          this.params.p_dichvu,
          date,
          this.params.p_somay_acc.trim()
        );
        this.getDSKhieuNai(
          this.params.p_dichvu,
          date,
          this.params.p_somay_acc.trim()
        );
      }
    },
    async btnTimKiem() {
      this.onSearch();
    },
    lvw_SelectedIndexChanged(data) {
      this.params.p_matt = data.ma_tt;
      this.params.p_trangthai = data.trangthai_tb;
      this.params.p_daily = data.ten_dly;
      this.params.p_tentt = data.ten_tt;
      this.params.p_diachi = data.diachi_tt;
      this.params.p_tuyenthu = data.tentuyen;
      this.params.p_loaihinh = data.loaitb_id;
    },
    cboDichVuVT_SelectedValueChanged(agrs){
      this.loadLoaiHinh(agrs.id);
    }
  },
};
</script>
