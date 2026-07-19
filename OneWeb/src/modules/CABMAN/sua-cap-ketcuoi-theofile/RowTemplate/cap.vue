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
    // dsDuAn: function () {
    //   return this.grid.$parent.dsDuAn;
    // },
    dsChuQuan: function () {
      return this.grid.$parent.dsChuQuan;
    },
    dsToQuanLy: function () {
      return this.grid.$parent.dsToQuanLy;
    },
    trangThaiText: function () {
      // let rs = this.data.TRANGTHAI;
      // if (!this.isNullOrEmpty(this.data.MESSAGE.donVi)) {
      //   rs = this.data.MESSAGE.donVi;
      // } else if (!this.isNullOrEmpty(this.data.MESSAGE.kyHieu)) {
      //   rs = this.data.MESSAGE.kyHieu;
      // } else if (!this.isNullOrEmpty(this.data.MESSAGE.tenCap)) {
      //   rs = this.data.MESSAGE.tenCap;
      // } else if (!this.isNullOrEmpty(this.data.MESSAGE.capgoc)){
      //   rs = this.data.MESSAGE.capgoc
      // }
      let cap = this.grid.$parent.danhSachCap.find(x => x.STT == this.data.STT)
      return cap ? cap.TRANGTHAI : null;
    },
    IS_FORM_EDIT: function () {
      return this.grid.$parent.IS_FORM_EDIT === true;
    },
  },
  created: async function () {
    // this.dsTaiSan = await this.getDanhSachTaiSan();
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
    // getDanhSachTaiSan: async function () {
    //   let r = await this.grid.$parent.getDsTaiSan(this.data.DUAN_ID);
    //   return r;
    // },
    // comboboxDuAn_Change: async function () {
    //   this.dsTaiSan = await this.getDanhSachTaiSan();
    //   this.updateData();
    // },
    // updateData_TaiSan: function () {
    //   this.validTaiSan();
    //   //this.updateData();
    // },
    // validTaiSan: function () {
    //   let taiSan = this.dsTaiSan.find(
    //     (x) => x.TAISAN_ID == this.data.TAISAN_ID
    //   );
    //   if (!(taiSan == undefined || taiSan == null)) {
    //     this.data.TAISAN_TEXT = taiSan.MA_TS;
    //   }
    // },
    isNullOrEmpty: function (value) {
      return this.grid.$parent.isNullOrEmpty(value);
    },
  },
};
</script>
<style>
input.form-control.is-invalid.e-control.e-dropdownlist.e-lib.e-input {
  border-color: #dc3545 !important;
  border-width: inherit;
}
</style>
