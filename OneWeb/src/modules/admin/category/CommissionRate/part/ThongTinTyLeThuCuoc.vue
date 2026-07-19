<template>
  <div class="box-form">
    <div class="legend-title">Thông tin tỷ lệ thu cước</div>
    <div class="info-row">
      <vue-input
        v-model="formProps.tyLeThuCuoc"
        :labelWidth="'90'"
        ref="tyLeThuCuoc"
        required
        label="Tỷ lệ thu cước"
        :checkbox="false"
      >
      </vue-input>
    </div>
    <div class="row">
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <vue-input
            v-model="formProps.tyLePStu"
            ref="tyLePStu"
            :labelWidth="'100'"
            class="text-rigth"
            label="Tỷ lệ PS từ"
            required
            :checkbox="false"
          >
          </vue-input>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <vue-input
            v-model="formProps.tyLePSden"
            :labelWidth="'40'"
            ref="tyLePSden"
            label="Tỷ lệ PS đến"
            required
            class="text-rigth"
            :checkbox="false"
          >
          </vue-input>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <vue-input
            v-model="formProps.heSo"
            ref="heSo"
            :labelWidth="'60'"
            class="text-rigth"
            required
            id="heSo"
            label="Hệ số"
            :checkbox="false"
          >
          </vue-input>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["formProps"],
};
</script>

<style >
.text-rigth .value .form-control {
  text-align: right !important;
}
</style>
