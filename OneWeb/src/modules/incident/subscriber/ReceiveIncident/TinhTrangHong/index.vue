<template src="./index.html"></template>

<script>
import Const from './const';
import {MultiSelect, CheckBoxSelection} from '@syncfusion/ej2-dropdowns';

MultiSelect.Inject(CheckBoxSelection);

export default {
  name: "TinhTrangHong",
  data() {
    return {
      data: {}
    }
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
    ttHong: {
      get() {
        return this.value;
      },
      set(ttHong) {
        this.$emit('input', ttHong);
      }
    }
  },
  methods: {
    togglePanel(panelName) {
      this.$emit('togglePanel', panelName);
    }
  }
}
</script>

<style scoped>
</style>
