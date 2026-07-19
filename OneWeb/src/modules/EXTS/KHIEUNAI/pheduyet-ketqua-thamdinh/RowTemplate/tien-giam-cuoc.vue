<template src="./tien-giam-cuoc.template.html"></template>
<script>
export default {
  name: "TienGCTemplate",
  data: function () {
    return {
      rowFocused: false,
      data: {
        STT: 1,
        MA_TT: 4,
        MA_TB: 23,
        SO_HD: 6,
        THANG_GC: '2022-01',
        KY_GC: '2022-01',
        TIEN_GC: 1000000,
        TIEN: 800000,
        THUE: 100000,
        VAT: 100000,
        MA_DOANHTHU: 123456,
        TEN_CHITIEU: 12
      },
      dsThangGC: [
        { THANG_GCHT: '2022-01', THANG_GC: '2022-01' },
        { THANG_GCHT: '2022-02', THANG_GC: '2022-02' },
        // thêm các tháng khác nếu cần
      ],
      dsKyApDung: [
        { THANG_APHT: '2022-01', KY_GC: '2022-01' },
        { THANG_APHT: '2022-02', KY_GC: '2022-02' },
        // thêm các kỳ áp dụng khác nếu cần
      ]
    };
  },
  computed: {
    grid: function () {
      return this.$parent;
    },
    formChinh: function () {
      return this.grid.$parent.$parent.$parent;
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
  }
}
</script>
<style scoped>
.text-right .e-numerictextbox {
  text-align: right;
  padding-right: 7px !important;
}
</style>
