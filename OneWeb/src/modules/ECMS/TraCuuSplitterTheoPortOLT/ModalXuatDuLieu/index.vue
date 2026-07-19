<template src="./index.template.html"></template>
<script>
import XLSX from "xlsx";

export default {
  name: "ModalXuatDuLieu",
  props: ["id", "thuocTinh", "data"],
  data() {
    return {
      dsThuocTinh: [],
      dsThuocTinhHeader: [],
      key: 1,
    };
  },
  computed: {
    columns() {
      return this.dsThuocTinhHeader.map((thuocTinh) => {
        return {
          fieldName: thuocTinh.name,
          headerText: thuocTinh.name,
          textAlign: thuocTinh.textAlign,
          width: `${thuocTinh.width}`,
          allowFiltering: true,
        };
      });
    },
    getData: function () {
      if (this.dsThuocTinhHeader.length > 0) return this.data;
      else return [];
    },
  },
  methods: {
    onModalShown() {
      this.dsThuocTinh = [];
      this.thuocTinh.forEach((item) => {
        this.dsThuocTinh.push({ NAME: item });
      });
      this.setColumnsDisplay();
    },
    onModalHidden() {
      this.dsThuocTinh = [];
      this.dsThuocTinhHeader = [];
      ++this.key;
      this.$emit("close");
    },
    onClickButtonXuatFile() {
        let excelExportProperties = {
            dataSource: this.getData
        }
        this.$refs.gridDuLieu.excelExport(excelExportProperties);
    //   if (this.dsThuocTinh.length == 0) {
    //     this.$toast.error("Bạn đã xóa hết các cột lấy DL!");
    //     return;
    //   }

    //   let exportData = [];

    //   this.data.forEach((item) => {
    //     let data = {};

    //     this.dsThuocTinh.forEach((item1) => {
    //       data[item1.NAME] = item[item1.NAME];
    //     });

    //     exportData.push(data);
    //   });

    //   let worksheet = XLSX.utils.json_to_sheet(exportData);
    //   let workbook = XLSX.utils.book_new();
    //   XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");
    //   XLSX.writeFile(workbook, "export.xlsx");
    },
    onClickButtonXemDuLieu() {
      this.$refs.gridDuLieu.refresh();
    },
    column_OnRowSelected: function (args) {
      this.setColumnsDisplay();
    },
    column_OnRowDeselected: function (args) {
      this.setColumnsDisplay();
    },
    setColumnsDisplay: function () {
      let cs = this.$refs.gridThuocTinh.getSelectedRecords();
      this.dsThuocTinhHeader = [];
      this.thuocTinh.forEach((item) => {
        if (cs.findIndex((x) => x.NAME == item) >= 0) {
          let t = {
            name: item,
            width: 100,
          };
          this.dsThuocTinhHeader.push(t);
        }
      });
    },
  },
};
</script>
<style>
.tracuu-splitter-theo-port-olt-xuat-excel .gutter {
  background-color: transparent;
}
</style>
>
