<template src="./index.html"></template>
<script>
import XLSX from "xlsx";

export default {
  name: "ModalXuatDuLieu",
  props: ["id", "thuocTinh", "data", "filename"],
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
  },
  methods: {
    onModalShown() {
      let dsThuocTinh = [];
      let dsThuocTinhHeader = [];

      this.thuocTinh.forEach((item) => {
        let thuocTinh;

        if (typeof item == "string") {
          thuocTinh = {
            name: item,
            width: 100,
          };
        } else {
          thuocTinh = { ...item };
        }

        dsThuocTinh.push(thuocTinh.name);
        dsThuocTinhHeader.push(thuocTinh);
      });

      this.dsThuocTinh = dsThuocTinh.map((item) => {
        return { NAME: item };
      });
      this.dsThuocTinhHeader = dsThuocTinhHeader;
    },
    onModalHidden() {
      this.dsThuocTinh = [];
      this.dsThuocTinhHeader = [];
      ++this.key;
      this.$emit("close");
    },
    getColumnTemplateXoa(parent) {
      return function () {
        return {
          template: {
            template: `
                            <button class="btn btn-sm btn-danger lh14 pad3" @click="parent.onClickButtonXoaThuocTinh(data.NAME)">
                                <span class="-ap icon-close"></span>
                            </button>
                        `,
            data() {
              return {
                parent: parent,
                data: {},
              };
            },
          },
        };
      };
    },
    getColumnTemplateChonNhieu() {
      return function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" class="check" :checked="true">
                                <span class="name"></span>
                            </div>
                        `,
          },
        };
      };
    },
    onClickButtonXuatFile() {
      if (this.dsThuocTinh.length == 0) {
        this.$toast.error("Bạn đã xóa hết các cột lấy DL!");
        return;
      }

      let exportData = [];
      let result = this.$refs.gridDSXuatFile.filteredDataSource || []

      result.forEach((item) => {
        let data = {};

        this.dsThuocTinh.forEach((item1) => {
          data[item1.NAME] = item[item1.NAME];
        });

        exportData.push(data);
      });

      let worksheet = XLSX.utils.json_to_sheet(exportData);
      let workbook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(workbook, worksheet, "Sheet1");

      let file = this.filename || "export.xlsx"
      XLSX.writeFile(workbook, file);
    },
    onClickButtonXemDuLieu() {
      this.dsThuocTinhHeader = this.dsThuocTinhHeader.filter((item) => {
        return (
          this.dsThuocTinh
            .map((thuocTinh) => thuocTinh.NAME)
            .indexOf(item.name) >= 0
        );
      });
    },
    onClickButtonXoaThuocTinh(thuocTinh) {
      this.dsThuocTinh = this.dsThuocTinh.filter(
        (item) => item.NAME != thuocTinh
      );
    },
  },
};
</script>
<style>
.tracuu-splitter-theo-port-olt-xuat-excel .gutter {
  background-color: transparent;
}
</style>
