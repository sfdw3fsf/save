<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import api from "./api";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
export default {
  components: { breadcrumb },
  name: "AssignTrustPoint",
  props: {
    isPopup: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    this.loadData();
  },
  data() {
    return {
      header: {
        title: "GÁN ĐIỂM TÍN NHIỆM KHÓA MÁY",
        list: [],
      },
      loading: false,
      formData: {
        comboBoxLoaiNV: {
          data: [],
          selected: null,
        },
        DiemTNTu: 0,
        DiemTNDen: 0,
      },
      gridboxDSDG: {
        data: [],
        selected: null,
        columns: [
          {
            fieldName: "ten_lnv",
            headerText: "Loại NV",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "diemtn_tu",
            headerText: "Điểm TN từ",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "diemtn_den",
            headerText: "Điểm TN đến",
            allowFiltering: true,
            allowSorting: true,
          },
        ],
      },
    };
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val);
    },
    "gridboxDSDG.selected": {
      handler: function (val, oldval) {
        if (val) {
          this.formData.comboBoxLoaiNV.selected = this.formData.comboBoxLoaiNV.data.find(
            (element) => element.loainv_id == val.loainv_id
          );
          this.formData.DiemTNTu = val.diemtn_tu;
          this.formData.DiemTNDen = val.diemtn_den;
        }
      },
      deep: true,
    },
    "formData.DiemTNTu": {
      handler: function (val, oldval) {
        if (val < 0 || val > 100) {
          this.formData.DiemTNTu = 0;
        }
      },
      deep: true,
    },
    "formData.DiemTNDen": {
      handler: function (val, oldval) {
        if (val < 0 || val > 100) {
          this.formData.DiemTNDen = 100;
        }
      },
      deep: true,
    },
  },
  computed: {},
  methods: {
    GetData(response) {
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },

    async loadData() {
      await this.getComboboxData();
      await this.getGridboxDSDGData();
    },
    async getComboboxData() {
      this.loading = true;
      try {
        this.formData.comboBoxLoaiNV.selected = null;
        this.formData.comboBoxLoaiNV.data = this.GetData(
          await api.sp_load_cbo_loai_nv(this.axios, {})
        );
        if (this.formData.comboBoxLoaiNV.data.length > 0) {
          this.formData.comboBoxLoaiNV.selected = this.formData.comboBoxLoaiNV.data[0];
        }
      } catch (err) {
        console.error(err);
        // setTimeout(() => {
        //   this.getComboboxData()
        // }, 3000)
      } finally {
        this.loading = false;
      }
    },
    async getGridboxDSDGData() {
      this.loading = true;
      try {
        this.gridboxDSDG.selected = null;
        this.gridboxDSDG.data = this.GetData(
          await api.lay_ds_loainv_diemtn(this.axios, {})
        );
      } catch (err) {
        console.error(err);
        // setTimeout(() => {
        //   this.getComboboxData()
        // }, 3000)
      } finally {
        this.loading = false;
      }
    },
    rowSelectedGridbox(items) {
      if (items.length > 0) {
        this.gridboxDSDG.selected = items[0];
      }
    },
    async GhiLaiBntClick() {
      await this.cnDG();
    },
    async XoaBntClick() {
      this.$bvModal
        .msgBoxConfirm(`Bạn thật sự muốn xóa dữ liệu không?`, {
          title: "Thông báo",
          size: "sm",
          centered: true,
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (yes) => {
          if (yes) {
            this.xoaDG();
          }
        });
    },
    async xoaDG() {
      this.loading = true;
      try {
        await api.capnhat_loainv_diemtn(this.axios, {
          vloainv_id: this.formData.comboBoxLoaiNV.selected.loainv_id,
          vdiemtn_tu: 0,
          vdiemtn_den: 0,
          vkieu: 2,
        });
        await this.getGridboxDSDGData();
        this.$toast.info("Xóa dữ liệu thành công!");
      } catch (err) {
        console.error(err);
        this.$toast.error("Xóa dữ liệu lỗi!");
      } finally {
        this.loading = false;
      }
    },
    async cnDG() {
      this.loading = true;
      try {
        await api.capnhat_loainv_diemtn(this.axios, {
          vloainv_id: this.formData.comboBoxLoaiNV.selected.loainv_id,
          vdiemtn_tu: this.formData.DiemTNTu,
          vdiemtn_den: this.formData.DiemTNDen,
          vkieu: 1,
        });
        await this.getGridboxDSDGData();
        this.$toast.info("Cập nhật thông tin thành công!");
      } catch (err) {
        console.error(err);
        this.$toast.error("Cập nhật thông tin lỗi!");
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>
