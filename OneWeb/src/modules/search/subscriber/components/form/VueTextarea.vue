<template>
  <div class="info-row">
      <div v-if="checkbox" :class="'key w' + (labelWidth ? labelWidth : 80)">
        <div class="check-action">
          <input v-model="checkbox_value" type="checkbox" class="check" />
          <span class="name">{{label}}</span>
          <span v-if="required" class="required">*</span>
        </div>
      </div>
      <div :class="'key w' + (labelWidth ? labelWidth : 80)"  v-else>
        <span class="name">{{label}}</span>
        <span v-if="required" class="required">*</span>
      </div>
    <div class="value">
        <textarea cols="30" rows="10" :style="'height: '+height+'px;resize: none;'" :disabled="disable" :value="value" @change="update($event.target.value)" type="text" class="form-control" />
    </div>
  </div>
</template>
<script>
export default {
  data: () => ({
    checkbox_value: false
  }),
  props: {
    label: String,
    checkbox: Boolean,
    value: String,
    height: String,
    labelWidth: String,
    required: Boolean
  },
  methods: {
    update (value) {
      this.$emit('input', value)
    }
  },
  computed: {
    disable () {
      if (!this.checkbox_value && this.checkbox) {
        return true
      } else {
        return false
      }
    }
  }
}
</script>

<style>
.key {
  white-space: nowrap;
}
.required {
  color: rgb(255, 0, 0);
}
</style>
