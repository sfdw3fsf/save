<template>
  <div class="box-form">
    <div class="row">
      <div class="col-sm-5 col-12">
        <vue-input
          v-model="formProps.txtNV_TiepThi"
          
          :labelWidth="'90'"
          label="NV tiếp thị"
          :checkbox="false"
        >
        </vue-input>
      </div>
      <div class="col-sm-7 col-12">
        <div class="row">
          <div class="col-sm-4 col-12">
            <vue-select
              v-model="formProps.cboKieu_HD"
              
              :labelWidth="'90'"
              :disable="true"
              :options="options.options_kieuHD"
              id="kieuHD"
              labelField="KIEU_HD"
              valueField="KIEUHD_ID"
              label="Kiểu HĐ"
            ></vue-select>
          </div>
          <div class="col-sm-4 col-12">
            <vue-select
              v-model="formProps.cboKenhThu"
              
              :labelWidth="'90'"
              :disable="true"
              :options="options.options_kenhThu"
              id="kenhThu"
              labelField="KENHTHU"
              valueField="KENHTHU_ID"
              label="Kênh thu"
            ></vue-select>
          </div>
          <div class="col-sm-4 col-12">
            <vue-input
              v-model="formProps.txtTongTien"
              
              :labelWidth="'90'"
              label="Tổng tiền"
              :checkbox="false"
            >
            </vue-input>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  props: ["formProps", "options"],
};
</script>
