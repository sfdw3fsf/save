<template>
  <div>
    <div class="legend-title">Thông tin phiếu yêu cầu</div>
    <div class="row">
      <div class="col-sm-7 col-12">
        <vue-select v-model="formProps.dichVu" :required="true" :labelWidth="'105'" :disable="false" :options="options.options_dichVu" id="dichVu" labelField="label" valueField="value" label="Dịch vụ"></vue-select>
      </div>
      <div class="col-sm-5 col-12">
        <vue-input v-model="formProps.maGD" :required="true" :labelWidth="'90'" :highlight="true" label="Mã GD" :checkbox="false" > </vue-input>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-7 col-12">
        <vue-select v-model="formProps.quyTrinh" :required="true" :labelWidth="'105'" :disable="false" :options="options.options_quyTrinh" id="dichVu" labelField="label" valueField="value" label="Quy trình"></vue-select>
      </div>
      <div class="col-sm-5 col-12">
        <vue-input v-model="formProps.soMayAcc" :required="true" :labelWidth="'90'" :highlight="true" label="Số máy/Acc" :checkbox="false" > </vue-input>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-7 col-12">
        <vue-input v-model="formProps.tenThueBao" :required="true" :labelWidth="'105'" :highlight="false" label="Tên thuê bao" :checkbox="false" > </vue-input>
      </div>
      <div class="col-sm-5 col-12">
        <vue-date v-model="formProps.ngayHT" :labelWidth="'90'" :required="true" label="Ngày HT" :checkbox="true" ></vue-date>
      </div>
    </div>
    <vue-input v-model="formProps.diaChiTB" :required="true" :labelWidth="'105'" :highlight="false" label="Địa chỉ TB" :checkbox="false" > </vue-input>
    <vue-input v-model="formProps.diaChiLD" :required="true" :labelWidth="'105'" :highlight="false" label="Địa chỉ LĐ" :checkbox="false" > </vue-input>
    <vue-input v-model="formProps.hostLD" :required="true" :labelWidth="'105'" :highlight="false" label="Host LĐ" :checkbox="false" > </vue-input>
    <vue-input v-model="formProps.ghiChu" :required="true" :labelWidth="'105'" :highlight="false" label="Ghi chú" :checkbox="false" > </vue-input>
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
      options_dichVu: [],
      options_quyTrinh: [],
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
