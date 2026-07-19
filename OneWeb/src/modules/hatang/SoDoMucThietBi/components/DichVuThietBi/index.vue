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
  watch: {},
  created: function () {},
  methods: {
    loadData: async function (loaiHaTang, id) {
      this.dataItems = [];
      let result = await this.getDsDichVu(loaiHaTang, id);
      if (result.result) {
        this.dataItems = result.data;
      } else {
        this.$root.toastError(result.msg);
      }
    },
    getDsDichVu: async function (loaiHaTang, id) {
      let apiUrl = "/web-ecms/hatang/sodo-logic/get-ds-dichvu-htm";
      switch (loaiHaTang) {
        case "HTAH":
          apiUrl = "/web-ecms/hatang/sodo-logic/get-ds-dichvu-ah";
          break;
        case "HTCLOUD":
          apiUrl = "/web-ecms/hatang/sodo-logic/get-ds-dichvu-ah";
          break;
        case "HTIDG":
          apiUrl = "/web-ecms/hatang/sodo-logic/get-ds-dichvu-idg";
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
        result.msg = "Có lỗi xảy ra khi lấy thông tin danh sách dịch vụ";
      }
      return result;
    },
    show(tbiId){
      this.$root.context.post('/web-ecms/hatang/sodo-logic/get-all-spdv-by-thietbi', {
        id: tbiId,
      }).then((result) => {
        this.dataItems = result.data;
      });
    }
  },
};
</script>
