<template>
  <div class="actions">
    <button class="btn" @click="moveToRight">
      <span class="fa fa-angle-right"></span>
    </button>
    <button class="btn" @click="moveAllToRight">
      <span class="fa fa-angle-double-right"></span>
    </button>
    <button class="btn" @click="moveToLeft">
      <span class="fa fa-angle-left" ></span>
    </button>
    <button class="btn" @click="moveAllToLeft">
      <span class="fa fa-angle-double-left"></span>
    </button>
  </div>
</template>

<script>
export default {
  name: 'MovingButton',
  data: () => ({}),
  mounted () {},
  methods: {
    moveToRight () {
      this.$emit('move-to-right')
    },
    moveAllToRight () {
      this.$emit('move-all-to-right')
    },
    moveToLeft () {
      this.$emit('move-to-left')
    },
    moveAllToLeft () {
      this.$emit('move-all-to-left')
    }
  },
  watch: {}
}
</script>
<style scoped>
</style>
