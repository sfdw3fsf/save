<template src="./ketcuoi.template.html"></template>
<script>
export default {
  name: "KetCuoiTemplate",
  data: function () {
    return {
      dsTaiSan: [],
      dsHuongBeA: [],
      dsHuongBeB: [],
    };
  },
  computed: {
    grid: function () {
      return this.$parent;
    },
    dsPhanLoaiKC: function () {
      return this.grid.$parent.dsPhanLoaiKC;
    },
    dsLoaiKC: function () {
      return this.grid.$parent.dsLoaiKC;
    },
    dsCapSP: function () {
      return this.grid.$parent.dsCapSP;
    },
    dsTTSD: function () {
      return this.grid.$parent.dsTTSD;
    },
    dsKieuLap: function () {
      return this.grid.$parent.dsKieuLap;
    },
    dsNoiSX: function () {
      return this.grid.$parent.dsNoiSX;
    },
    dsViTriLapDat: function () {
      return this.grid.$parent.dsViTriLapDat;
    },
    dsLoaiVo: function () {
      return this.grid.$parent.dsLoaiVo;
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
    dsToaNha: function () {
      return this.grid.$parent.dsToaNha;
    },
  },
  created: async function () {
    this.dsTaiSan = await this.getDanhSachTaiSan();
  },
  methods: {
    updateData: function () {
      this.grid.$parent.updateDataSource(this.data);
    },
    getDanhSachTaiSan: async function () {
      let r = await this.grid.$parent.getDsTaiSan(this.data.DUAN_ID);
      return r;
    },
    buttonDiaChi_Click: function () {
      this.grid.$parent.showModalDiaChi(this.data);
    },
    buttonToaDo_Click: function () {
      this.grid.$parent.showModalToaDo(this.data);
    },
    comboboxDuAn_Change: async function () {
      this.dsTaiSan = await this.getDanhSachTaiSan();
      this.updateData();
    },
  },
};
</script>
