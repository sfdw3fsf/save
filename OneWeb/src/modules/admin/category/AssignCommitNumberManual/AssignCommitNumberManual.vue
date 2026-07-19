<template src="./AssignCommitNumberManual.html"></template>
<style scoped src="./AssignCommitNumberManual.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import Vue from "vue";
import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns";
import KTable from "@/components/KTable.vue";
import ActionTop from "@/components/ActionTop.vue";
Vue.use(DropDownListPlugin);
export default {
  components: { breadcrumb, KTable, ActionTop },
  data: function () {
    return {
      header: {
        title: "Gán số đẹp nhân công",
      },
      params: {
        p_loaiso_options: [],
        p_loaiso: null,
        p_nhomsodep_options: [],
        p_nhomsodep: null,
        p_kieusodep_options: [],
        p_kieusodep: null,
        p_txtMoTa: null,
        p_dichvuvt_options: [],
        p_dichvuvt: null,
        p_prefix: null,
      },
      ds_Prefix_table: [],
      ds_so_table: [],
      listItemChecked: [],
      columns_DSSo: [
        {
          field: "somay",
          label: "Số máy",
          allowFilter: true,
        },
        {
          field: "kieu_so",
          label: "Kiểu số đẹp",
          allowFilter: true,
        },
      ],
    };
  },
  validations: {},
  computed: {},
  watch: {},
  created() {
    this.loaiDSLoaiSo();
    this.loadNhomSoDep();
  },
  async mounted() {},
  methods: {
    async loaiDSLoaiSo() {
      this.loading(true);
      try {
        let apiParams = {
          p_dk: "1,2,3",
        };
        const response = await API.getLoaiSo(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          let items = [];
          response.data.data.forEach(function (item) {
            items.push({ id: item.loaiso_id, text: item.ten_ls });
          });
          this.params.p_loaiso_options = items;
          this.params.p_loaiso = items[0].id;
          this.loadDichVuVT(items[0].id);
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loading(false);
      }
    },
    async loadNhomSoDep() {
      API.getNhomSoDep(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.nhomso_id, text: item.nhom_so });
              });
              this.params.p_nhomsodep_options = items;
              this.params.p_nhomsodep = items[0].id;
              this.params.p_txtMoTa = items[0].text;
              this.loaiKieuSoDep(items[0].id);
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    async loaiKieuSoDep(id) {
      try {
        this.loading(true);
        let apiParams = {
          p_nhomso_id: id,
        };
        const response = await API.getKieuSoDep(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          let items = [];
          response.data.data.forEach(function (item) {
            items.push({ id: item.kieuso_id, text: item.kieu_so });
          });
          this.params.p_kieusodep_options = items;
          this.params.p_kieusodep = items[0].id;
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loading(false);
      }
    },
    async loadDichVuVT(id) {
      //so thuong
      let items = [];
      if (id == 1) {
        items = [
          { id: 1, text: "CỐ ĐỊNH" },
          { id: 10, text: "GPHONE" },
          { id: 11, text: "IMS" },
        ];
        //so ao
      } else if (id == 3) {
        items = [
          { id: 8, text: "MEGAWAN" },
          { id: 9, text: "TSL" },
          { id: 12, text: "MEGA EYES" },
        ];
      } else {
        items = [];
      }
      this.params.p_dichvuvt_options = items;
      /*  this.params.p_dichvuvt = items[0].id;
      this.loadDSPrefix(items[0].id, this.params.p_loaiso); */
    },
    loadDSPrefix: function (dvvt, loaiso) {
      //reset
      this.loading(true);
      this.ds_Prefix_table = [];
      this.ds_so_table = [];
      let apiParams = {
        p_dichvuvt_id: dvvt,
        p_loai_so_id: loaiso,
      };
      API.getDSPrefix(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.ds_Prefix_table = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    async loadDSSo(dvvtid, numberprefix, loaiso) {
      this.ds_so_table = [];
      this.listItemChecked = [];
      try {
        this.loading(true);
        let apiParams = {
          p_dichvuvt_id: dvvtid,
          p_numprefix: numberprefix,
          p_loaiso_id: loaiso,
        };
        const response = await API.getDSSo(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.ds_so_table = response.data.data;
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loading(false);
      }
    },
    onLoaiSoChange: function (agrs) {
      this.loadDichVuVT(agrs.id);
    },
    onDichVuVTChange: function (agrs) {
      this.loadDSPrefix(agrs.id, this.params.p_loaiso);
    },
    onNhomSoDepChange: function (agrs) {
      this.loaiKieuSoDep(agrs.id);
      this.params.p_txtMoTa = agrs.text;
    },
    onKieuSoDepChange: function (agrs) {},
    onSelectionChanged: function (subject) {
      if (subject != null && subject.prefix > 0) {
        this.params.p_prefix = subject.prefix;
        this.loadDSSo(
          this.params.p_dichvuvt,
          subject.prefix,
          this.params.p_loaiso
        );
      }
    },
    async btnGhiLai() {
      if (this.listItemChecked != null && this.listItemChecked.length > 0) {
        this.CapNhatKieuSo(
          this.params.p_dichvuvt,
          this.listItemChecked.toString(),
          this.params.p_loaiso,
          this.params.p_kieusodep
        );
      } else {
        this.$alert("Vui lòng chọn số máy", "Thông báo", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "info",
        });
      }
    },
    onDataCheckeDSSo: function (data) {
       if (data != null && data.length > 0) {
       this.listItemChecked = data;
      }
      
    },
    async CapNhatKieuSo(_dvvtid, _somay, _loaiso, _kieuso) {
      this.loading(true);
      let data = {
        p_dichvuvt_id: _dvvtid,
        p_ds_somay: _somay,
        p_loaiso_id: _loaiso,
        p_kieuso_id: _kieuso,
      };
      API.getCapNhatKieuSo(this.axios, data)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.$alert("Cập nhật thành công", "Thông báo", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "success",
            });
            this.loadDSSo(
              this.params.p_dichvuvt,
              this.params.p_prefix,
              this.params.p_loaiso
            );
          } else {
            this.$alert("Có lỗi trong quá trình cập nhật!", "Thông báo", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          }
        })
        .catch((error) => {
          this.$alert(error.toString(), "Thông báo", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
  },
};
</script>
