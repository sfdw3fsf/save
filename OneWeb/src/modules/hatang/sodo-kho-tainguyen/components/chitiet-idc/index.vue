<template src="./index.template.html"></template>

<script>
export default {
  data: function () {
    return {
      currentItem: {
        ID: null,
        TEN: "",
        DONVI: "",
        DIACHI: "",
        HOATDONG: null,
      },
    };
  },
  computed: {},
  watch: {},
  created: function () {},
  methods: {
    loadData: async function (args) {
      try {
        let item = await this.getThongTinIDC(args);
        if (item.result) {
          this.setData(item.data);
        } else {
          this.setData(null);
          this.$root.toastError(item.msg);
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    setData: function (args) {
      if (args != null) {
        this.currentItem.ID = args.ID;
        this.currentItem.TEN = args.TEN;
        this.currentItem.DONVI = args.TEN_DV;
        this.currentItem.DIACHI = args.DIA_CHI;
        this.currentItem.HOATDONG = args.THOI_DIEM;
      } else {
        this.currentItem = {
          ID: null,
          TEN: "",
          DONVI: "",
          DIACHI: "",
          HOATDONG: null,
        };
      }
    },
    getThongTinIDC: async function (id) {
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(
          "/web-ecms/hatang/sodo-logic/get-thongtin-idc",
          {
            id: id,
          }
        );
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin IDC";
      }
      return result;
    },
  },
};
</script>
