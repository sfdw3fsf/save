<template src="./chitiet-vung-dulieu.html"></template>
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
        this.$refs.modalChiTietVungDuLieu.show()
      } catch (err) {
        console.log(err);
      } finally {
        this.$root.showLoading(false);
      }
    },


  }
}
</script>
