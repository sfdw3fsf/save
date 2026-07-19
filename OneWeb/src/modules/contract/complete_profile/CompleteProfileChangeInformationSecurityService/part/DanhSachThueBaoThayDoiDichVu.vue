<template>
  <div class="box-form">
    <div class="legend-title">
      <div class="row">
        <div class="col-3">Danh sách thuê bao thay đổi dịch vụ</div>
        <div class="col-9">
          <div class="row">
            <div class="col-4 list-checks red normal">
              <div class="item">
                <div class="check-action">
                  <input
                    type="radio"
                    v-model="formProps.radio_phieu"
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
                    v-model="formProps.radio_phieu"
                    class="check"
                    value="1"
                  />
                  <span class="name">Phiếu trả lại</span>
                </div>
              </div>
             
            </div>
            <div class="col-4">
              <vue-select
                v-model="formProps.timKiem"
                :required="false"
                :labelWidth="'90'"
                :disable="false"
                :options="options.options_timKiem"
                id="timKiem"
                labelField="label"
                valueField="value"
                label="Tìm kiếm"
                :checkbox="true"
              ></vue-select>
            </div>
            <div class="col-4">
              <vue-select
                v-model="formProps.donVi"
                :required="false"
                :labelWidth="'90'"
                :disable="false"
                :options="options.options_donVi"
                id="donVi"
                labelField="label"
                valueField="value"
                label="Đơn vị"
                :checkbox="false"
              ></vue-select>
            </div>
          </div>
        </div>
      </div>
      <div class="clearfix"></div>
    </div>
    <vue-table
      ref="vueTable"
      v-model="formProps.table.currentRecord"
      :options="formProps.table.options"
      :config="formProps.table.config"
      :loading="formProps.table.loading"
      :unique="'PHANLOAI_ID'"
      :multiple="true"
    ></vue-table>
  </div>
</template>
<script>
export default {
  components: {},
  props: ["formProps"],
  data: () => ({
    options: {
      options_timKiem: [],
      options_donVi: [],
    },
  }),
  watch: {},
  computed: {},
  created() {},
  mounted() {},
  methods: {},
};
</script>
