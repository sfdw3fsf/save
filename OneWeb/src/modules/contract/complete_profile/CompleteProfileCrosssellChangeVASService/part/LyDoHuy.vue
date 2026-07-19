<template>
  <div>
    <div class="legend-title mart20">Lý do hủy</div>
    <div style="max-height: 135px; min-height: 135px !important; overflow: scroll;">
      <vue-table 
        ref="vueTable"
        v-model="formProps.table.currentRecord"
        :options="formProps.table.options" 
        :config="formProps.table.config" 
        :loading="formProps.table.loading" 
        :unique="'PHANLOAI_ID'"
        :multiple="false"></vue-table>  
    </div>
  </div>
</template>
<script>
export default {
  components: {
  },
  props: ['formProps'],
  data: () => ({
  }),
  watch: {
  },
  computed: {},
  created () {
  },
  mounted () {},
  methods: {
  }
}
</script>
