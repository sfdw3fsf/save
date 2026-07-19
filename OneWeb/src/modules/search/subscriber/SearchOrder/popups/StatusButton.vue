<template>
  <div
    :style="{
      backgroundColor: color
    }"
    class="status-button"
    :class="active ? 'status-button--active' : 'status-button--inactive'"
    :title="status"
  >
    <span>
      ({{ number }}) {{ status }}
    </span>
  </div>
</template>

<script>
export default {
  props: {
    color: {
      type: String,
      default: '#fff'
    },
    number: {
      type: Number,
      default: 0
    },
    status: {
      type: String,
      default: ''
    },
    active: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style lang="scss" scoped>
.status-button {
  border: 2px solid;
  max-width: 100%;
  padding: 4px;
  cursor: pointer;
  span {
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    display: block;
    width: 100%;
  }
}
.status-button--active {
  border-color: #00DC82;
}
.status-button--inactive {
  border-color: #d2d4d9;
}
</style>
