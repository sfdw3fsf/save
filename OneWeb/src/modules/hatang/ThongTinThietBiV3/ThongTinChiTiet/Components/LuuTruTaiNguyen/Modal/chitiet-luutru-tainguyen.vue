<template src="./chitiet-luutru-tainguyen.html"></template>
<script>

export default {
  props: {
    modalId: {
      type: Number,
      default: 0
    }
  },
  data: function () {
    return {
      thanhNguonItemList: [],
    }
  },
  methods: {

    showModal: async function () {
      try {
        this.$root.showLoading(true);
        this.$refs.modalChiTietLuuTruTaiNguyen.show()
      } catch (err) {
        console.log(err);
      } finally {
        this.$root.showLoading(false);
      }
    },


  }
}
</script>
