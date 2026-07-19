<template src="./index.template.html"></template>
<script>
export default {
  data: function () {
    return {
      dsTTVT: [],
      dsToKT: [],
      kqTimKiem: [],
    };
  },
  created: function () {
    this.getDsTTVT();
    this.getToKT();
  },
  methods: {
    getDsTTVT: async function () {
      var rs = await this.$root.context.post(
        "/web-cabman/mang-truyen-dan/layDanhSachTTVTTheoNV"
      );
      this.dsTTVT = rs.data;
    },
    getToKT: async function () {
      var rs = await this.$root.context.post(
        "/web-cabman/mang-truyen-dan/layDanhSachTramTheoToQL",
        { id: 221 }
      );
      this.dsToKT = rs.data;
    },
    getKqTK: async function () {
      this.$root.showLoading(true);
      try {
        var rs = await this.$root.context.post(
          "/web-cabman/thueBaoCapKetCuoi/dsthuebaocapketcuoi",
          { doituong_id: 540035, loaidt_id: 0, vitri_bd: 1, vitri_kt: 4 }
        );
        this.kqTimKiem = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    grid1_selectedRowChanged(dataItem){
      console.log(dataItem);
    },
  },
};
</script>