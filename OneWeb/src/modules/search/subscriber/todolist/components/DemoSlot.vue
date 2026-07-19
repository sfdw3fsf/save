<template>
  <div class="demo-slot">
    <h1>demo slot</h1>
    <slot name="test"></slot>
    <h1>demo slot 1</h1>
    <!-- <CompTabs title="Tieu de cua tab"></CompTabs> -->
  </div>
</template>

<script>
import CompTabs from './CompTabs.vue'
export default {
  name: 'demo-slot',
  components: { CompTabs },
  data() {
    return {}
  },
  methods: {}
}
</script>

<style>
.demo-slot {
  margin-top: 100px;
  padding: 100px 30px;
  border: 1px solid #000;
}
</style>