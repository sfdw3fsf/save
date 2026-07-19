<template src="./be.template.html"></template>
<script>
export default {
  name: "BeTemplate",
  data: function () {
    return {
      //dsTaiSan: [],
    };
  },
  computed: {
    grid: function () {
      return this.$parent;
    },
    dsLoaiBe: function () {
      return this.grid.$parent.dsLoaiBe;
    },
    dsViTri: function () {
      return this.grid.$parent.dsViTri;
    },
    dsHuongTamDan: function () {
      return this.grid.$parent.dsHuongTamDan;
    },
    dsLoaiDuong: function () {
      return this.grid.$parent.dsLoaiDuong;
    },
    dsChuQuan: function () {
      return this.grid.$parent.dsChuQuan;
    },
    dsTTSD: function () {
      return this.grid.$parent.dsTTSD;
    },
    // dsDuAn: function () {
    //   return this.grid.$parent.dsDuAn;
    // },
  },
  created: async function () {
    // this.dsTaiSan = await this.getDanhSachTaiSan();
    // this.validTaiSan();
  },
  methods: {
    updateData: function () {
      this.grid.$parent.updateDataSource(this.data);
    },
    // updateData_TaiSan: function () {
    //   //this.updateData();
    //   this.validTaiSan();
    // },
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
    //   this.$root.showLoading(true);
    //   try {
    //     this.dsTaiSan = await this.getDanhSachTaiSan();
    //     this.updateData();
    //     this.data.TAISAN_ID = null;
    //     this.data.TAISAN_TEXT = "";
    //   } finally {
    //     this.$root.showLoading(false);
    //   }
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
