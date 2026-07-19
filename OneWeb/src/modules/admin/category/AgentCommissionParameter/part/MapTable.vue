<template>
  <div class="box-col">
    <div class="title-bg-main">
      <span class="title">{{title}}</span>
    </div>
    <div class="box-form" style="height: 140px; overflow: auto; padding: 5px">
      <div v-if="loading" class="container d-flex h-100 justify-content-center">
        <b-spinner
          class="row justify-content-center align-self-center"
          variant="primary"
          label="Spinning"
        ></b-spinner>
      </div>
      <template v-else>
        <vue-checkbox
          v-for="item in items"
          :key="`${name}-${item.value}`"
          v-model="item.isChecked"
          :class="{ highlight: item.isChecked }"
          :label="item.text"
        ></vue-checkbox>
      </template>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MapTable',
  props: ['items', 'name', 'loading', 'title'],
  data: () => ({}),
  mounted () {},
  methods: {},
  watch: {
    items: {
      deep: true,
      handler () {
        this.$emit('update:items', this.items)
      }
    }
  }
}
</script>
<style scoped>
/deep/ .check-action {
  width: 100% !important;
}
</style>
