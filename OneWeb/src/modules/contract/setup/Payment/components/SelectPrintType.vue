<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogSelectPrintType" :position="position" :header="'Chọn kiểu in'" showCloseIcon="true" width='400px' :target="targetTop">
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row"><input type="radio" id="printType1Id"  v-model="printType" value="1" checked="checked"  /> <label for="printType1Id" style="padding-left:10px;"> In kim</label></div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row"><input type="radio" id="printType2Id"  v-model="printType" value="2" /> <label for="printType2Id" style="padding-left:10px;"> In thường</label></div>
          </div>
        </div>
      </div>
    </div>
    <div class="popup-footer">
      <div class="row">
        <div class="col-sm-12 col-12">
          <p class="text-center">
            <a href="javascript:void(0)" class="btn btn-primary" v-on:click.prevent="BtnOkOnClick"> <span class="icon nc-icon-glyph"></span> <span class="icon one-print"></span> Xem </a>
          </p>
        </div>
      </div>
    </div>
    <PrintReport ref="printReportPhieuThu" :data="this.dataReport" :type="7" />
  </ejs-dialog>
</template>
<script>
import api from '../Api'
import PrintReport from '@/modules/print/InPhieuChi/PrintReport.vue'
export default {
  name: 'SelectPrintType',
  components: {
    api,
    PrintReport
  },
  data() {
    return {
      printType: '1',
      phieuttId: 0,
      Loading: false,
      targetTop: '#app .main-wrapper', // .main-wrapper-payment
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'center' },
      dataReport: []
    }
  },
  mounted() {
    this.printType = '1'
  },
  methods: {
    openDialog(pTtId) {
      this.printType = '1'
      this.phieuttId = pTtId
      this.$refs.dialogSelectPrintType.show(false)
    },
    async BtnOkOnClick() {
      if (this.printType == 2) this.$emit('selectprintType', this.printType)
      else {
        // In kim
        await this.InKim()
      }
    },
    async InKim() {
      var input = { in_phieutt_id: this.phieuttId }
      var data = this.GetData(await api.in_phieuthu(this.axios, input))
      if (data != null && data.length > 0) {
        this.dataReport = data.map((x) => ({ dong: x.dong, noidung: x.noidung }))
        this.$refs.printReportPhieuThu.startPrint(this.dataReport)
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (message) {
      this.$toast.warning(message)
    },
    ShowError: function (message) {
      this.$toast.error(message)
    },
    ShowSuccess: function (message) {
      this.$toast.success(message)
    }
  }
}
</script>
