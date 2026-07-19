<template>
  <div class="info-row">
    <div class="key w70">
      <div class="check-action">
        <input type="checkbox" class="check" />
        <span class="name">{{label}}</span>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    label: String
  }
}
</script>
