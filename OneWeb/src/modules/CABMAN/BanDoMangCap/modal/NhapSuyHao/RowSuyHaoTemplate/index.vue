<template src="./index.template.html"></template>
<script>
export default {
  name: "RowSuyHaoTemplate",
  data: function () {
  },
  computed: {
    grid: function () {
      return this.$parent;
    },
    vitri: function () {
      return this.grid.$parent.vitri;
    },
    suyhao: function () {
      return this.grid.$parent.suyhao;
    },
    IS_FORM_EDIT: function () {
      return this.grid.$parent.IS_FORM_EDIT === true;
    },
  },
  created: async function () {
  },
  methods: {
    updateData: function () {
      this.grid.$parent.$parent.$parent.updateDataSource(this.data);
    },
    isNullOrEmpty: function (value) {
      return this.grid.$parent.$parent.$parent.isNullOrEmpty(value);
    },
  },
};
</script>

