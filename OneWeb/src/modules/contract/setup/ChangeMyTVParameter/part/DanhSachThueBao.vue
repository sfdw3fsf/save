<template>
  <div class="box-form">
    <vue-card label="Danh sách thuê bao">
      <vue-table
        ref="vueTable"
        v-model="table.currentRecord"
        :options="table.options"
        :config="table.config"
        :loading="table.loading"
        :multiple="false"
      ></vue-table>
    </vue-card>
  </div>
</template>

<script>
export default {
  props:['table']
};
</script>

<style>
</style>