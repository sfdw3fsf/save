<template src="./index.template.html"></template>

<script>
export default {
  data: function () {
    return {
      dataItems: [],
    };
  },
  computed: {
    gridItems: function () {
      return this.$refs.gridItems;
    },
  },
  methods: {
    loadData: async function (loaiHaTang, id) {
      this.dataItems = [];
      let result = await this.getDsThietBi(loaiHaTang, id);
      if (result.result) {
        this.dataItems = result.data;
      } else {
        this.$root.toastError(result.msg);
      }
    },
    getDsThietBi: async function (loaiHaTang, id) {
      let apiUrl = "/web-ecms/hatang/sodo-logic/get-ds-thietbi-htm";
      switch (loaiHaTang) {
        case "HTAH":
          apiUrl = "/web-ecms/hatang/sodo-logic/get-ds-thietbi-ah";
          break;
        case "HTIDG":
          apiUrl = "/web-ecms/hatang/sodo-logic/get-ds-thietbi-idg";
          break;
        default:
          break;
      }
      let result = {
        result: false,
        data: null,
        msg: "",
      };
      try {
        let rs = await this.$root.context.post(apiUrl, {
          id: id,
        });
        result.data = rs.data;
        result.result = true;
      } catch (e) {
        result.msg = "Có lỗi xảy ra khi lấy thông tin danh sách thiết bị";
      }
      return result;
    },
  },
};
</script>
