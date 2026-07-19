
<template>
  <div class="box-form">
    <vue-card label="Danh sách tỷ lệ thu cước">
      <vue-table
        ref="vueTable"
        
        v-model="table.currentRecord"
        :options="data"
        :config="table.config"
        :loading="table.loading"
        :unique="'STT'"
        :multiple="false"
        @input="dataCheck"
      ></vue-table>
    </vue-card>
  </div>
</template>

<script>
export default {
  props: ["table", "data"],
  methods: {
    dataCheck(val) {
      this.$emit("data", val);
    },
  },
};
</script>

<style>
</style>
