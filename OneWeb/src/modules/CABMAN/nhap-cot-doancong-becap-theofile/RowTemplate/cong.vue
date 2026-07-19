<template src="./cong.template.html"></template>
<script>
export default {
  name: "CongTemplate",
  data: function () {
    return {
      //dsTaiSan: [],
      dsHuongBeA: [],
      dsHuongBeB: [],
    };
  },
  computed: {
    grid: function () {
      return this.$parent;
    },
    dsLoaiCong: function () {
      return this.grid.$parent.dsLoaiCong;
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
    //this.dsTaiSan = await this.getDanhSachTaiSan();
    this.dsHuongBeA = await this.getDsHuongBe(this.data.BECAP_A_ID);
    this.dsHuongBeB = await this.getDsHuongBe(this.data.BECAP_B_ID);
    //this.validTaiSan();
  },
  methods: {
    updateData: function () {
      this.grid.$parent.updateDataSource(this.data);
    },
    // updateData_TaiSan: function () {
    //   //this.updateData();
    //   this.validTaiSan();
    // },
    // getDanhSachTaiSan: async function () {
    //   let r = await this.grid.$parent.getDsTaiSan(this.data.DUAN_ID);
    //   return r;
    // },
    getDsHuongBe: async function (beCapID) {
      if (beCapID != null) {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getDsHuongBe",
          {
            id: beCapID,
          }
        );
        return rs.data;
      } else return [];
    },
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
