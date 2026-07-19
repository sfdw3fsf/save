<template>
  <div class="info-row">
    <div v-if="checkbox" :class="'key' + ' w' + labelWidth">
      <div class="check-action">
        <input v-model="checkbox_value" type="checkbox" class="check" />
        <span class="name">{{ label }}</span>
      </div>
    </div>
    <div v-else :class="'key' + ' w' + labelWidth">{{ label }}</div>
    <div class="value">
      <div class="input-icon-right">
        <input
          :value="value"
          :disabled="checkbox_value"
          @change="update($event.target.value)"
          type="text"
          class="form-control"
        />
        <span class="icon one-calendar"></span>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data: () => ({
    current_value: "",
    checkbox_value: false
  }),
  props: {
    label: String,
    value: String,
    checkbox: Boolean,
    labelWidth: String
  },
  methods: {
    update(value) {
      this.$emit("input", value);
    },
  },
};
</script>
