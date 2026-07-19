<template src="./index.html"></template>
<style scoped>
.displayNone {
  display: none;
}
table tbody tr:hover {
  background-color: #035fcc;
  cursor: pointer;
  color: #fff;
}
table tbody tr input:hover,
label:hover {
  cursor: pointer;
}
.container {
  max-height: 150px;
  overflow-y: scroll;
  padding-left: 0;
  padding-right: 0;
}
table tr td:first-child {
  width: 10%;
}
</style>
<script>
export default {
  name: "dropdown-custom",
  components: {},
  props: ["data"],
  data: function () {
    return {
      selectedValue: [],
      isHide: true,
      isCheckAll: false,
    };
  },
  validations: {},
  watch: {},
  created: async function () {},
  async mounted() {},
  methods: {
    onToggle() {
      this.isHide = !this.isHide;
    },
    toggleRowCb(value, event) {
      if (event.target.checked) {
        this.selectedValue.push(value);
        if (this.selectedValue.length == this.data.length) {
          this.isCheckAll = true;
        } else {
          this.isCheckAll = false;
        }
      } else {
        const index = this.selectedValue.indexOf(value);
        if (index > -1) {
          this.selectedValue.splice(index, 1);
        }
        if (this.selectedValue.length == this.data.length) {
          this.isCheckAll = true;
        } else {
          this.isCheckAll = false;
        }
      }
      const listItem = this.data.filter(item=> this.selectedValue.includes(item.ID));
      this.$emit("onChoseItem", listItem);
    },
    toggleAllCb(event) {
      if (event.target.checked) {
        this.selectedValue = [];
        this.data.forEach((element) => {
          this.selectedValue.push(element.ID);
        });
      } else {
        this.selectedValue = [];
      }
      const listItem = this.data.filter(item=> this.selectedValue.includes(item.ID));
      this.$emit("onChoseItem", listItem);
    },
    onOk() {
			const listItem = this.data.filter(item=> this.selectedValue.includes(item.ID));
      this.$emit("onOk", listItem);
      this.isHide = true;
    },
    onCancel() {
      this.$emit("onCancel", null);
      this.isHide = true;
    },
  },
};
</script>
