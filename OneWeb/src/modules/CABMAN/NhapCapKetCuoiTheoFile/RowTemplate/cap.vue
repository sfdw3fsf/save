<template src="./cap.template.html"></template>
<script>
export default {
  name: "CapTemplate",
  data: function () {
    return {
      dsTaiSan: [],
    };
  },
  computed: {
    grid: function () {
      return this.$parent;
    },
    dsKichCoCap: function () {
      return this.grid.$parent.dsKichCoCap;
    },
    dsDienCT: function () {
      return this.grid.$parent.dsDienCT;
    },
    dsNoiSX: function () {
      return this.grid.$parent.dsNoiSX;
    },
    dsTTSD: function () {
      return this.grid.$parent.dsTTSD;
    },
    dsLoaiCap: function () {
      return this.grid.$parent.dsLoaiCap;
    },
    dsLopMC: function () {
      return this.grid.$parent.dsLopMC;
    },
    dsNhomLC: function () {
      return this.grid.$parent.dsNhomLC;
    },
    dsKieuDC: function () {
      return this.grid.$parent.dsKieuDC;
    },
    dsDuAn: function () {
      return this.grid.$parent.dsDuAn;
    },
    dsChuQuan: function () {
      return this.grid.$parent.dsChuQuan;
    },
    dsToQuanLy: function () {
      return this.grid.$parent.dsToQuanLy;
    },
  },
  created: async function () {
    this.dsTaiSan = await this.getDanhSachTaiSan();
  },
  methods: {
    updateData: function () {
      this.grid.$parent.updateDataSource(this.data);
    },
    buttonDiaChi_Click: function () {
      this.grid.$parent.showModalDiaChi(this.data);
    },
    buttonToaDo_Click: function () {
      this.grid.$parent.showModalToaDo(this.data);
    },
    getDanhSachTaiSan: async function () {
      let r = await this.grid.$parent.getDsTaiSan(this.data.DUAN_ID);
      return r;
    },
    comboboxDuAn_Change: async function () {
      this.dsTaiSan = await this.getDanhSachTaiSan();
      this.updateData();
    }
  },
};
</script>
