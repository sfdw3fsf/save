<template src="./index.html"></template>
<style scoped>
.popup-box .popup-header {
  padding: 10px 15px;
  font-size: 20px;
  background-color: #fff;
  border-bottom: 1px solid #e0e0e0;
}

</style>
<script>
export default {
  name: "DSThueBao",
  components: {},
  props: ["dsDBModal", "title"],
  data: function () {
    return {
      dataItem: null,
    };
  },
  validations: {},
  watch: {},
  created: async function () {},
  async mounted() {},
  methods: {
    grid1_selectedRowChanged(dataItem) {
      this.dataItem = dataItem;
    },
    confirmChose(value) {
      if(value) {
        this.$emit("onConfirm", this.dataItem)
      } else {
        this.$emit("close")
      }
      
    },
  },
};
</script>
