<template>
  <div class="info-row">
    <div class="key w70">
      <div class="check-action">
          <input v-model="current_value" type="checkbox" class="check" />
        <span class="name">{{label}}</span>
        <span v-if="required" class="required">*</span>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  computed: {
    current_value: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit('input', val)
      }
    }
  },
  props: {
    label: String,
    value: Boolean,
    required: Boolean
  }
}
</script>
<style>
  .required {
    color: rgb(255, 0, 0);
  }
</style>