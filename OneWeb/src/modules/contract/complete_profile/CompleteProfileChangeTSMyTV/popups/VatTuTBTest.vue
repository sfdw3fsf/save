<template>
  <div class="box-form">
    <div class="legend-title">Danh sách tham số</div>
    <div class="row">
      <b-col>
        <div class="info-row">
          <div class="key w90">JSON</div>
          <div class="value">
            <textarea ref="txtData" class="form-control highlight" v-model="input"></textarea>
          </div>
        </div>
      </b-col>
    </div>
    <div class="row">
      <b-col class="text-center pt-3 align-content-center">
        <button class="btn btn-primary" @click="show">Vật tư TB</button>
      </b-col>
    </div>
    <CapVatTuTB ref="popupVatTu" :input-data="params"/>
  </div>
</template>

<script>
import CapVatTuTB from './CapVatTuTB'

export default {
  name: 'VatTuTBTest',
  components: {CapVatTuTB},
  data() {
    return {
      params: {
      },
      input: ''
    }
  },
  methods: {
    show() {
      if (!this.input) {
        this.$root.$toast.warning('Nhập input để thực hiện chức năng')
        return
      }
      this.params = JSON.parse(this.input.toString())
      console.log(this.params)
      this.$refs.popupVatTu.openDialog()
    }
  }
}
</script>

<style scoped>

</style>
