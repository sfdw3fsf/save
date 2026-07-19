<template src="./AssignStaffLocation.html"></template>
<style scoped src="./AssignStaffLocation.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import Vue from "vue";
import gridview from "@/components/Shared/gridview";
import KTable from "@/components/KTable.vue";
import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns";
Vue.use(DropDownListPlugin);
export default {
  components: { breadcrumb, gridview, KTable },
  mounted: {},

  watch: {
    chk_LoaiNV: function (bool) {
      if (bool === false) {
        this.params.p_loaiNV = "";
      } else {
        this.loadLoaiNV();
      }
    },
    chk_ViTri: function (bool) {
      if (bool === false) {
        this.params.p_vitri_id = "";
      } else {
        this.loadViTri();
      }
    },
  },
  computed: {},
  created() {
  },
  data: function () {
    return {
      header: {
        title: "Gán vị trí nhân viên",
      },
      params: {
        p_loaiNV_options: [],
        p_loaiNV: null,
        p_nhanvien_id: null,
        p_manhanvien: null,
        p_vitri_options: [],
        p_vitri_id: null,
        p_donvi_id: null,
        ds_vtdagan_table: [],
        p_vtdagan: null,
        ds_vtchuagan_table: [],
        p_vtchuagan: null,
        isDonVi: true,
        chkDVval: false,
      },
      chk_LoaiNV: false,
      chk_ViTri: false,
      listItemVTChuaGan: [],
      listItemVTDaGan: [],
      listItemDonVi: [],
      listItemDonViGoc: [],
      defaultValueTree: null,
      listboxNV: [],
      columns_DSVTriDaGan: [
        {
          field: "VITRI",
          label: "Vị trí",
          allowFilter: true,
        },
        {
          field: "CHUCDANH",
          label: "Chức danh",
          allowFilter: true,
        },
      ],
      columns_DSVTriChuaGan: [
        {
          field: "VITRI",
          label: "Vị trí",
          allowFilter: true,
        },
      ],
    };
  },
  methods: {
    //#region Treeview đơn vị
    Onclick: function () {
      if (this.params.chkDVval === false) {
        //enable
        this.getDonViTheoND();
       //this.loadDonViGoc();
        this.params.isDonVi = false;
      } else {
        //disable
        this.value = null;
        this.params.isDonVi = true;
        this.params.p_donvi_id = null;
         this.defaultValueTree = null;
      }
    },
    buildTree: function (arr) {
      var _ = require("lodash");
      var nodes = {};
      return arr.filter(function (obj) {
        var id = obj["id"],
          parentId = obj["pid"];
        nodes[id] = _.defaults(obj, nodes[id], { children: [] });
        parentId &&
          (nodes[parentId] = nodes[parentId] || { children: [] })[
            "children"
          ].push(obj);
        return !parentId;
      });
    },
    removeNoChildren (node) {
    return {
        children: node.children && node.children.length ? node.children : 0
        }
    } ,
    async getDonViTheoND() {
      this.loading(true);
      let apiParams = {
        v_ma_nd: this.$root.token.getUserName(),
      };
      API.getDonViIDTheoND(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
           let donviIDCHA = response.data.data;
            this.loadDonViGoc(donviIDCHA);
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    async loadDonViGoc(donvi_nguoidung) {
      this.loading(true);
      API.getDonvi(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({
                  id: item.DONVI_ID,
                  label: item.TEN_DV,
                  pid: item.DONVI_CHA_ID,
                  children: [],
                });
              });
             // this.listItemDonViGoc = items.filter((x) => x.id === 0);
              this.listItemDonViGoc = items.filter((x) => x.id == donvi_nguoidung);
              console.log("dv: "+donvi_nguoidung+" - "+JSON.stringify(this.listItemDonViGoc))
              //this.listItemDonViGoc[0].children.push(...this.buildTree(items));
              this.buildTree(items)
              //console.log("dv: "+JSON.stringify(this.buildTree(items)))
              this.params.p_donvi_id = donvi_nguoidung;
              this.defaultValueTree = donvi_nguoidung;
              this.loaiDSNhanVien(this.params.p_donvi_id,this.params.p_loaiNV,this.params.p_vitri_id);
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    //#endregion
    async loadLoaiNV() {
      API.getDSLoaiNV(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.LOAINV_ID, text: item.TEN });
              });
              this.params.p_loaiNV_options = items;
              this.params.p_loaiNV = items[0].id;
              this.loaiDSNhanVien(
                this.params.p_donvi_id,
                items[0].id,
                this.params.p_vitri_id
              );
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    async loaiDSNhanVien(_donviid, _loainv, _vitriid) {
      this.listboxNV = [];
      this.loading(true);
      try {
        let apiParams = {
          donvi_id: _donviid,
          loainv_id: _loainv,
          vitri_id: _vitriid,
        };
        const response = await API.getDSNhanVien(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.listboxNV = response.data.data;

          this.params.p_nhanvien_id =
            this.listboxNV[0].NHANVIEN_ID.toString();
        }else{
          this.params.ds_vtdagan_table = [];
          this.params.ds_vtchuagan_table = [];
          this.params.p_manhanvien = null;
          this.params.p_manhanvien =null;
        }
      } catch (error) {
        console.log(error);
      } finally {
         this.loading(false);
      }
    },
    async loadViTri() {
      API.getDSViTri(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.VITRI_ID, text: item.VITRI });
              });
              this.params.p_vitri_options = items;
              this.params.p_vitri_id = items[0].id;
              this.loaiDSNhanVien(
                this.params.p_donvi_id,
                this.params.p_loaiNV,
                items[0].id
              );
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    async loaiDSVTDaGan(nvid) {
      this.params.ds_vtdagan_table = [];
      // this.loading(true);
      try {
        let apiParams = {
          nhanvien_id: nvid,
        };
        const response = await API.getNVDaGan(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.params.ds_vtdagan_table = response.data.data;
        }
      } catch (error) {
        console.log(error);
      } finally {
        // this.loading(false);
      }
    },
    async loaiDSVTChuaGan(donviid, nvid) {
      this.params.ds_vtchuagan_table = [];
      // this.loading(true);
      try {
        let apiParams = {
          donvi_id: donviid,
          nhanvien_id: nvid,
        };
        const response = await API.getNVChuaGan(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.params.ds_vtchuagan_table = response.data.data;
        }
      } catch (error) {
        console.log(error);
      } finally {
        // this.loading(false);
      }
    },
    async insertNhanVien_Vitri(items) {
      // this.loading(true);
      try {
        const response = await API.insertNhanVienVT(this.axios, items);
        if (response.data.error_code === "BSS-00000000") {
          if (
            response.data.data !== undefined &&
            response.data.data.length >= 0
          ) {
            this.$toast.success("Gán thành công");
            this.loaiDSVTDaGan(this.params.p_nhanvien_id);
            this.loaiDSVTChuaGan(
              this.params.p_donvi_id,
              this.params.p_nhanvien_id
            );
          }
        } else {
           this.$toast.error("Gán lỗi");
        }
      } catch (error) {
        this.$toast.error("Gán lỗi");
        console.log(error);
      } finally {
        // this.loading(false);
      }
    },
    async deleteNhanVien_Vitri(items) {
      //  this.loading(true);
      try {
        const response = await API.deleteNhanVienVT(this.axios, items);
        if (response.data.error_code === "BSS-00000000") {
          if (
            response.data.data !== undefined &&
            response.data.data.length >= 0
          ) {
            this.$toast.success("Gỡ thành công");
            this.loaiDSVTDaGan(this.params.p_nhanvien_id);
            this.loaiDSVTChuaGan(
              this.params.p_donvi_id,
              this.params.p_nhanvien_id
            );
          }
        } else {
           this.$toast.error("Gỡ lỗi!");
        }
      } catch (error) {
        this.$toast.error("Gỡ lỗi!");
        console.log(error);
      } finally {
        //  this.loading(false);
      }
    },
    grdNVChanged(obj) {
      this.params.p_nhanvien_id = obj.NHANVIEN_ID.toString();
      this.params.p_manhanvien = obj.MA_NV;
      this.loaiDSVTDaGan(this.params.p_nhanvien_id);
      this.loaiDSVTChuaGan(this.params.p_donvi_id, this.params.p_nhanvien_id);
    },
    checkedDataChuaGan(dataItem) {
      this.listItemVTChuaGan = dataItem;
    },
    checkedDataDaGan(dataItem) {
      this.listItemVTDaGan = dataItem;
    },

    onSearch: function () {
      let dsnvfiter = this.listboxNV.filter(x => x.MA_NV === this.params.p_manhanvien);
      if (dsnvfiter.length < 1) {
        this.$toast.error("Không tìm thấy thông tin về mã người dùng vừa nhập");
      } else {
        this.listboxNV = dsnvfiter;
      }
    },
    async btnLamMoi() {
      this.listboxNV = [];
      this.params.ds_vtdagan_table = [];
      this.params.ds_vtchuagan_table = [];
      this.chk_ViTri = false;
      this.chk_LoaiNV = false;
      this.params.chkDVval = false;
      this.params.isDonVi = true;
      this.listItemDonViGoc = [];
      this.value = null;
      this.params.p_manhanvien = null;
      this.listItemVTChuaGan = [];
      this.listItemVTDaGan = [];
       this.params.p_donvi_id =null;
    },
    onChangeLoaiNV: function (agrs) {
      this.loaiDSNhanVien(this.params.p_donvi_id, agrs.id, this.params.p_vitri_id);
    },
    onChangeViTri: function (agrs) {
      this.loaiDSNhanVien(this.params.p_donvi_id, this.params.p_loaiNV, agrs.id);
    },
    async btnInsertVT() {
      let items=[];
      let nvid = this.params.p_nhanvien_id;
       this.listItemVTChuaGan.forEach(function (item) {
                items.push({ NHANVIEN_ID: nvid , VITRI_ID: item.VITRI_ID });
              });
      if (this.listItemVTChuaGan.length != 0) {
        this.insertNhanVien_Vitri(items);
      } else {
        this.$toast.error("Vui lòng chọn vị trí");
      }
      this.listItemVTChuaGan = [];
    },
    async btnDeleteVT() {
      let items=[];
      let nvid = this.params.p_nhanvien_id;
       this.listItemVTDaGan.forEach(function (item) {
                items.push({ NHANVIEN_ID: nvid , VITRI_ID: item.VITRI_ID });
              });
      if (this.listItemVTDaGan.length != 0) {
        this.deleteNhanVien_Vitri(items);
      } else {
        this.$toast.error("Vui lòng chọn vị trí");
      }
      this.listItemVTDaGan = [];
    },
    onChangeTreeSelect: function (dataitem) {
      this.params.p_donvi_id = dataitem.id.toString();
      this.loaiDSNhanVien(dataitem.id.toString(),this.params.p_loaiNV,this.params.p_vitri_id);
    },
  },
};
</script>
