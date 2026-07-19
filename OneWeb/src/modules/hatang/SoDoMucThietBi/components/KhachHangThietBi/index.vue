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
    show(tbiId){
      this.$root.context.post('/web-ecms/hatang/sodo-logic/get-all-kh-by-thietbi', {
        id: tbiId,
      }).then((result) => {
        this.dataItems = result.data;
      });
    }
  },
};
</script>
