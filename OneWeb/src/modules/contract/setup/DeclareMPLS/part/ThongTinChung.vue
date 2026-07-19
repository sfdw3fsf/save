<template>
  <div class="box-form box-form-1">
    <div class="row">
      <div class="col-sm-5 col-12">
        <vue-input
          v-model="formProps.txtNV_TiepThi"
          :disable="true"
          :labelWidth="'0'"
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
              :labelWidth="'55'"
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
              :labelWidth="'60'"
              :disable="true"
              :options="options.options_kenhThu"
              id="kenhThu"
              labelField="KENHTHU"
              valueField="KENHTHU_ID"
              label="Kênh thu"
            ></vue-select>
          </div>
          <div class="col-sm-4 col-12">
            <!-- <vue-input
              v-model="formProps.txtTongTien"
              class="price"
              :disable="true"
              :labelWidth="'0'"
              label="Tổng tiền"
              :checkbox="false"
            >
            </vue-input> -->
            <div class="info-row">
              <div class="key w60">Tổng tiền</div>
              <div class="value price">
                <vue-numeric class="form-control tright red fw6 bold" separator="," v-model="formProps.txtTongTien" readonly="true" placeholder="0" output-type="Number"></vue-numeric>
                <!-- <ejs-numerictextbox
                  format="N"  
                  :value="formProps.txtTongTien"
                  :enabled="false"
                  :showSpinButton="false"
                  cssClass="e-style"
                  class="form-control tright pr-2 line-red"
                >
                </ejs-numerictextbox> -->
              </div>
            </div>
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
