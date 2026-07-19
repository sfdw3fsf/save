<template>
  <div class="row">
    <div class="col-sm-7 col-12">
      <div class="info-row">
        <div class="key w80">Cell1</div>
        <div class="value">
          <input type="text" v-model="pnlGphone.form.txtCell1" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Tọa độ</div>
        <div class="value">
          <input type="text" v-model="pnlGphone.form.txtToaDo" class="form-control">
        </div>
      </div>
    </div>
    <div class="col-sm-5 col-12">
      <div class="info-row">
        <div class="key w80">Cell2</div>
        <div class="value">
          <input type="text" v-model="pnlGphone.form.txtCell2" class="form-control">
        </div>
      </div>
      <div class="info-row">
        <div class="key w80">Simcard</div>
        <div class="value">
          <input type="text" v-model="pnlGphone.form.txtSimcard" class="form-control">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PanelGphone',
  data () {
    return {
      pnlGphone: {
        visible: false,
        config: {
          txtCell1: '',
          txtToaDo: '',
          txtCell2: '',
          txtSimcard: ''
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
