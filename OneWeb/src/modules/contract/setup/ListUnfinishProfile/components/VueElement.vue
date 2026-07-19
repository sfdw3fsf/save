<template>
  <div>
    <div class="info-row">
      <div :class="'key w' + (labelWidth ? labelWidth : '100')">{{ label }}<span v-if="isRequired" class="ml-1">(*)</span></div>
      <div class="value">
        <slot></slot>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: ['labelWidth', 'label', 'isRequired']
}
</script>
