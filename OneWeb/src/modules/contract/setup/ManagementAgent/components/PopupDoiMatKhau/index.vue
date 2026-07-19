<template src="./PopupDoiMatKhau.html"></template>

<script>
export default {
  name: "PopupDoiMatKhau",
  props: {
    show: {
      type: Boolean,
      default: false
    },
    row: {
      type: [Object, null],
      default: () => {}
    }
  },
  data() {
    return {
      showModal: false
    };
  },
  methods: {
    closeModal() {
      this.$emit("update:show", false);
      this.$emit("update:row", null);
      this.showModal = false;
    }
  },
  watch: {
    show(newValue) {
      this.showModal = newValue;
    }
  }
};
</script>
