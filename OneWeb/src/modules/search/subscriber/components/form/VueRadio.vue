<template>
  <div class="item marb10 mart5">
    <div class="check-action">
        <input v-model="current_value" :value="defaultValue" :name="name" type="radio" class="check" />
      <span class="name">{{label}}</span>
      <span class="required" v-if="required">*</span>
    </div>
  </div>
</template>
<script>
export default {
  computed : {
    current_value: {
      get() {
        return this.value
      },
      set(val) {
        this.current_value = val
      }
    }
  },
  props: {
    label: String,
    value: [String, Number],
    name: String,
    defaultValue: String,
    required: Boolean
  },
};
</script>
<style>
  .required {
    color: red;
  }
</style>
