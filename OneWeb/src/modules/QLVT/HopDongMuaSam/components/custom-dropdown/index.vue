<template src="./index.html"></template>
<style scoped>

.input-group-1 {
	display: table !important;
	width: 100%;
}

.vattu_box {
	position: relative;
}

.vattu_table {
	width: 100%;
	position: absolute;
	z-index: 1;
	max-height: 300px;
	background: aliceblue;
	overflow-y: scroll;
}
</style>
<script>
import moment from "moment";
import ClickOutside from "vue-click-outside";
import EventBus from "@/utils/eventBus";
import Vue from "vue";

export default {
  name: "VTCustomDropdown",
  components: {},
  props: ["column","dataSource","textField", "isDisabled"],
  directives: {
    ClickOutside,
  },
  data: function () {
    return {
			isShow: false,
			text: ''

		};
  },
  validations: {},
  watch: {},
  created: async function () {},
  destroyed() {},
  async mounted() {},
  methods: {
		onToggle() {
			this.isShow = !this.isShow;
		},
		onHide() {
			this.isShow = false;
		},
		onSelect(value) {
			console.log(value);
			this.text='';
			if(value) {
				this.textField.forEach(element => {
					this.text+=value[element];
				});
				this.$emit("onSelectValue", value);
				this.onHide();
			}
		}
	},
};
</script>
