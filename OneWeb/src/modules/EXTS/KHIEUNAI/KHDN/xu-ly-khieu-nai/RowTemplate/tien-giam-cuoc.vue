<template src="./tien-giam-cuoc.template.html"></template>
<script>
export default {
  name: "TienGCTemplate",
  data: function () {
    return {
      rowFocused: false,
    };
  },
  computed: {
    grid: function () {
      return this.$parent;
    },
    formChinh: function () {
      return this.grid.$parent.$parent.$parent;
    },
    dsKyApDung: function () {
      return this.formChinh.dsKyApDung;
    },
    dsThangGC: function () {
      return this.formChinh.dsThangGC;
    },
    isShowForm: function () {
      if (this.isAddNew) {
        return this.data.FOCUSED == true;
      } else {
        return true;
      }
    },
    isAddNew: function () {
      return this.data.ID == -1;
    },
  },
  created: async function () {},
  methods: {
    row_clickAddNew: function (args) {
      this.formChinh.focusRowThemNo(true);
    },
    newRow_Enter: async function (args) {
      if (this.isAddNew) {
        this.themNo();
      } else {
        this.updateData();
      }
    },
    tinhTheoTien: function (args) {
      if (args.event != undefined) {
        let tongTien = Math.round(
          this.data.TIEN_GC + (this.data.TIEN_GC * this.data.VAT_PT) / 100
        );
        this.data.TIEN = tongTien;
        this.data.VAT_GC = tongTien - this.data.TIEN_GC;
        this.updateData();
      }
    },
    tinhTheoTongTien: function (args) {
      if (args.event != undefined) {
        let tien = this.data.TIEN / (1 + this.data.VAT_PT / 100);
        this.data.TIEN_GC = tien;
        this.data.VAT_GC = this.data.TIEN - tien;
        this.updateData();
      }
    },
    tinhTheoVAT: function (args) {
      if (args.event != undefined) {
        let tongTien = Math.round(
          this.data.TIEN_GC + (this.data.TIEN_GC * this.data.VAT_PT) / 100
        );
        this.data.TIEN = tongTien;
        this.data.VAT_GC = tongTien - this.data.TIEN_GC;
        this.updateData();
      }
    },
    themNo: function () {
      this.formChinh.themNo(this.data);
    },
    btn_click: function (args) {
      if (this.isAddNew) {
        this.formChinh.focusRowThemNo(false);
      } else {
        this.formChinh.deleteNo(this.data);
      }
    },
    updateData: function () {
      this.formChinh.updateNo(this.data);
    },
  },
};
</script>
<style scoped>
.text-right .e-numerictextbox {
  text-align: right;
  padding-right: 7px !important;
}
</style>
