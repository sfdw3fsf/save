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
    // dsDuAn: function () {
    //   return this.grid.$parent.dsDuAn;
    // },
    dsChuQuan: function () {
      return this.grid.$parent.dsChuQuan;
    },
    dsToQuanLy: function () {
      return this.grid.$parent.dsToQuanLy;
    },
    dsToaNha: function () {
      return this.grid.$parent.dsToaNha;
    },
    trangThaiText: function () {
      // let rs = this.data.TRANGTHAI;
      // if (!this.isNullOrEmpty(this.data.MESSAGE.donVi)) {
      //   rs = this.data.MESSAGE.donVi;
      // } else if (!this.isNullOrEmpty(this.data.MESSAGE.kyHieu)) {
      //   rs = this.data.MESSAGE.kyHieu;
      // } else if (!this.isNullOrEmpty(this.data.MESSAGE.tenKC)) {
      //   rs = this.data.MESSAGE.tenKC;
      // } else if (!this.isNullOrEmpty(this.data.MESSAGE.diaChi)) {
      //   rs = this.data.MESSAGE.diaChi;
      // } else if (!this.isNullOrEmpty(this.data.MESSAGE.dungLuong)) {
      //   rs = this.data.MESSAGE.dungLuong;
      // } else if (!this.isNullOrEmpty(this.data.MESSAGE.capgoc)){
      //   rs = this.data.MESSAGE.capgoc
      // }
      let kc = this.grid.$parent.danhSachKetCuoi.find(x => x.STT == this.data.STT)
      return kc ? kc.TRANGTHAI : null;
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
    // getDanhSachTaiSan: async function () {
    //   let r = await this.grid.$parent.getDsTaiSan(this.data.DUAN_ID);
    //   return r;
    // },
    buttonDiaChi_Click: function () {
      this.grid.$parent.showModalDiaChi(this.data);
    },
    buttonToaDo_Click: function () {
      this.grid.$parent.showModalToaDo(this.data);
    },
    // comboboxDuAn_Change: async function () {
    //   this.dsTaiSan = await this.getDanhSachTaiSan();
    //   this.updateData();
    // },
    // updateData_TaiSan: function () {
    //   this.validTaiSan();
    //   //this.updateData();
    // },
    // validTaiSan: function () {
    //   let taiSan = this.dsTaiSan.find((x) => x.TAISAN_ID == this.data.TAISAN_ID);
    //   if (!(taiSan == undefined || taiSan == null)) {
    //     this.data.TAISAN_TEXT = taiSan.MA_TS;
    //   }
    // },
    isNullOrEmpty: function(value){
      return this.grid.$parent.isNullOrEmpty(value);
    }
  },
};
</script>
<style>
input.form-control.is-invalid.e-control.e-dropdownlist.e-lib.e-input {
  border-color: #dc3545 !important;
  border-width: inherit;
}
</style>
