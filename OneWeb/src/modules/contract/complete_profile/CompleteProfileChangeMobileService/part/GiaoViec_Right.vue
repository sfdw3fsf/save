<template>
  <div class="row mart20">
    <div class="col-sm-12 col-12">
      <div class="row">
        <div class="col-sm-12 col-12">
          <vue-input v-model="formProps.noiDungGiao" :required="true" :labelWidth="'105'" :highlight="false" label="Nội dung giao" :checkbox="false" > </vue-input>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-12 col-12">
          <vue-input v-model="formProps.noiDungTra" :required="true" :labelWidth="'105'" :highlight="false" label="Nội dung trả" :checkbox="false" > </vue-input>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-12 col-12">
          <div class="info-row">
            <div class="key w105">Nội dung TH <span class="required">*</span></div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" @click="btnNoiDungTHOnClick()">
                  <span class="nc-icon-glyph ui-1_edit-76 f16"></span>
                </button>
                <vue-input v-model="formProps.noiDungTH" :required="false" :labelWidth="'0'" label="" :checkbox="false" > </vue-input>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-5 col-12">
          <vue-select v-model="formProps.nhomKQ" :required="true" :labelWidth="'105'" :disable="false" :options="options.options_nhomKQ" id="nhomKQ" labelField="label" valueField="value" label="Nhóm KQ"></vue-select>
        </div>
        <div class="col-sm-7 col-12">
          <vue-select v-model="formProps.ketQua" :required="true" :labelWidth="'90'" :disable="false" :options="options.options_ketQua" id="ketQua" labelField="label" valueField="value" label="Kết quả"></vue-select>
        </div>
      </div>
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
      options_nhomKQ: [],
      options_ketQua: [],
    }
  }),
  watch: {
  },
  computed: {},
  created () {
  },
  mounted () {},
  methods: {
    btnNoiDungTHOnClick(){
      alert('btnNoiDungTHOnClick');
    }
  }
}
</script>
