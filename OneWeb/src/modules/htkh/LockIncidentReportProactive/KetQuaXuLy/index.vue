<template src="./index.html"></template>

<script>
import Const from './const';
import {MultiSelect, CheckBoxSelection} from '@syncfusion/ej2-dropdowns';

MultiSelect.Inject(CheckBoxSelection);

export default {
  name: "KetQuaXuLy",
  data() {
    return {
      dsKQXL: []
    }
  },
  mounted() {
    this.dsKQXL = [
      {kqxl_id: 0, kqxl: 'Đã xử lý xong'},
      {kqxl_id: 1, kqxl: 'Không xử lý được'}
    ];
  },
  props: {
    value: {
      type: Object,
      required: true
    },
    options: {
      type: Object,
      required: true
    },
  },
  computed: {
    kqxl: {
      get() {
        return this.value;
      },
      set(kqxl) {
        this.$emit('input', kqxl);
      }
    }
  }
}
</script>

<style scoped>
</style>
