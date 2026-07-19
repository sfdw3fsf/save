<template src="./index.html"></template>
<style scoped>
	.displayNone {
		display: none;
	}
	table tbody tr:hover {
		background-color: #035FCC;
		cursor: pointer;
		color: #fff;
	}
	table tbody tr input:hover, label:hover {
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
  name: "multi-select-demo",
  components: {},
  props: {
	data: {
      type: Array,
      required: false
    }
  },
  data: function() {
    return {
      selectedValue: [],
      isHide: true,
	  isCheckAll: false
    };
  },
  validations: {},
  watch: {},
  created: async function() {
  },
  async mounted() {},
  methods: {
    onToggle() {
      this.isHide = !this.isHide;
    },
		toggleRowCb(value, event){
			if (event.target.checked) {
				this.selectedValue.push(value);
				if (this.data !== undefined && this.selectedValue.length === this.data.length) {
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
			// console.log(this.selectedValue);
		},
		toggleAllCb(event){
			if (event.target.checked) {
				// this.selectedValue.push('NV01');
				this.selectedValue = [];
				this.data.forEach(element => {
					this.selectedValue.push(element.NHANVIEN_ID);
				});
			} else {
				this.selectedValue = [];
			}
		},
		onOk(){
  		this.$emit('onOk', this.selectedValue);
			this.isHide = true;
		},
		onCancel(){
			this.$emit('onCancel', this.selectedValue);
			this.isHide = true;
		}
  }
};
</script>
