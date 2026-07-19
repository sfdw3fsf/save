<template>
  <div :class="noborder ? '' : 'box-form'">
    <div v-if="label" class="legend-title">
      <div class="title pull-left">
        {{ label }}
      </div>
      <div v-if="subLabel" class="pull-right">
        <div class="red">{{subLabel}}</div>
      </div>
      <div class="clearfix"></div>
    </div>
    <slot></slot>
  </div>
</template>
<script>
export default {
  props: { 
    label: String,
    subLabel: String, 
    noborder: Boolean
  }
}
</script>
