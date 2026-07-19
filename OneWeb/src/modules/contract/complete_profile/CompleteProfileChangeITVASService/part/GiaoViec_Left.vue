<template>
  <div>
    <div class="legend-title mart20">
      <div class="pull-left">Giao việc</div>
      <div class="pull-right">
        <div class="normal red">
          <span class="name"> {{formProps.lienHe}} </span>
        </div>
      </div>
      <div class="clearfix"></div>
    </div>
    <div class="row">
      <div class="col-sm-7 col-12">
        <vue-select v-model="formProps.nguoiGiaoViec" :required="true" :labelWidth="'105'" :disable="false" :options="options.options_nguoiGiaoViec" id="dichVu" labelField="label" valueField="value" label="Người giao việc"></vue-select>
      </div>
      <div class="col-sm-5 col-12">
        <vue-date v-model="formProps.ngayGV" :labelWidth="'90'" :required="true" label="Ngày GV" :checkbox="true" ></vue-date>
      </div>
    </div>
    <div style="max-height: 130px; min-height: 130px !important; overflow: scroll;">
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
    model: null,
    options: {
      options_nguoiGiaoViec: [],
    }
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
