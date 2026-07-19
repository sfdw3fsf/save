<template>
  <div class="comp-tabs">
    <div class="tabs-title">
        <span>{{ title }}</span>
        <ion-icon name="heart"></ion-icon>
    </div>
    <ion-icon name="heart"></ion-icon>
  </div>
</template>

<script>
export default {
  name: 'comp-tabs',
  props:{
    title:String
  },
  data() {
    return {}
  }
}
</script>

<style>
</style>