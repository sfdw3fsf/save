<template>
  <div class="box-form">
    <div class="legend-title">
      <div class="pull-left">Danh sách thuê bao</div>
      <div class="pull-right">
        <div class="list-checks red normal">
          <div class="item">
            <div class="check-action">
              <input
                type="radio"
                v-model="formProps.rdoPhieu"
                class="check"
                value="0"
              />
              <span class="name">Phiếu mới</span>
            </div>
          </div>
          <div class="item">
            <div class="check-action">
              <input
                type="radio"
                v-model="formProps.rdoPhieu"
                class="check"
                value="1"
              />
              <span class="name">Phiếu trả lại</span>
            </div>
          </div>
          <div class="item" v-if="formProps.isRdoPhieu_Da_TH">
            <div class="check-action">
              <input
                type="radio"
                v-model="formProps.rdoPhieu"
                class="check"
                value="2"
              />
              <span class="name">Phiếu đã TH</span>
            </div>
          </div>
        </div>
      </div>
      <div class="clearfix"></div>
    </div>
    <vue-table
      ref="vueTableDSThueBao"
      v-model="formProps.table.currentRecord"
      :options="formProps.table.options"
      :config="formProps.table.config"
      :loading="formProps.table.loading"
      :unique="'matb'"
      :multiple="false"
    ></vue-table>
  </div>
</template>
<script>
export default {
  components: {},
  props: ["formProps"],
  data: () => ({
    options: {
      options_dsDichVuVT: [],
    },
  }),
  watch: {},
  computed: {},
  created() {},
  mounted() {},
  methods: {},
};
</script>
