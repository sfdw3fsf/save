<template>
  <div class="item marb10 mart5">
    <div class="check-action">
      <input v-model="current_value" :value="defaultValue" :name="name" type="radio" class="check" />
      <span class="name">{{label}}</span>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      current_value: '0'
    }
  },
  created() {
    this.current_value = this.value
  },
  watch: {
    current_value(val) {
      this.$emit('input', val)
    },
    value(val) {
      this.current_value = val
    }
  },
  props: {
    label: String,
    value: String,
    name: String,
    defaultValue: String
  },
};
</script>
