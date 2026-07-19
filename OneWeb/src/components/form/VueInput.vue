<template>
  <div class="info-row">
    <div v-if="checkbox" :class="'key' + ' w' + (labelWidth ? labelWidth : '80')">
      <div class="check-action">
        <input :disabled="disabled" v-model="checkbox_value" type="checkbox" class="check" />
        <span class="name">{{ label }}</span>
      </div>
    </div>
    <div :class="'key' + ' w' + (labelWidth ? labelWidth : '80')" v-else-if="radio">
      <div class="check-action">
        <input :disabled="disabled" type="radio" class="check" />
        <span class="name">{{ label }}</span>
      </div>
    </div>
    <div :class="'key' + ' w' + (labelWidth ? labelWidth : '80')" v-else>
    {{ label }}<span v-if="required" class="required"> *</span>
    </div>
    <div class="value">
      <input
        :disabled="disabled"
        :value="value"
        @change="update($event.target.value)"
        type="text"
        class="form-control"
      />
    </div>
  </div>
</template>
<script>
export default {
  data: () => ({
    checkbox_value: false,
  }),
  props: {
    label: String,
    checkbox: Boolean,
    radio: Boolean,
    disabled: Boolean,
    required: Boolean,
    value: [String, Number],
    labelWidth: String,
  },
  methods: {
    update(value) {
      this.$emit("input", value);
    },
  },
  computed: {
    disable() {
      if (!this.checkbox_value && this.checkbox) {
        return true;
      } else {
        return false;
      }
    },
  },
};
</script>

<style>
.key {
  white-space: nowrap;
}
</style>
