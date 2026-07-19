<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogSelectPrintType" :position="position" :header="'Danh sách kiểu in liên'" showCloseIcon="true" width='400px' :target="targetTop">
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row"><input type="radio" id="printType1Id"  v-model="printType" value="1"  /> <label for="printType1Id" style="padding-left:10px;"> In 1 liên</label></div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="info-row"><input type="radio" id="printType2Id"  v-model="printType" value="2" checked="checked" /> <label for="printType2Id" style="padding-left:10px;"> In 2 liên</label></div>
          </div>
        </div>
      </div>
    </div>
    <div class="popup-footer">
      <div class="row">
        <div class="col-sm-12 col-12">
          <p class="text-center">
            <a href="javascript:void(0)" class="btn btn-primary" v-on:click.prevent="BtnOkOnClick"> <span class="icon nc-icon-glyph"></span> <span class="icon one-print"></span> OK </a>
            <a href="javascript:void(0)" class="btn btn-primary" v-on:click.prevent="BtnCancelOnClick"> <span class="icon nc-icon-glyph"></span> <span class="icon one-cancel"></span> Hủy </a>
          </p>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import api from '../Api'
import PrintReport from '@/modules/print/InPhieuChi/PrintReport.vue'
export default {
  name: 'SelectPrintPhieuChi',
  components: {
    api,
    PrintReport
  },
  data() {
    return {
      printType: '2',
      phieuttId: 0,
      Loading: false,
      targetTop: '#app .main-wrapper', // .main-wrapper-payment
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'center' },
      dataReport: []
    }
  },
  mounted() {
    this.printType = '2'
  },
  methods: {
    openDialog(pTtId) {
      this.printType = '2'
      this.phieuttId = pTtId
      this.$refs.dialogSelectPrintType.show(false)
    },
    async BtnOkOnClick() {
       this.$emit('selectprintType', this.printType)
    },
    BtnCancelOnClick(){
      this.$refs.dialogSelectPrintType.hide()
    },
    async InKim() {
      var input = { in_phieutt_id: this.phieuttId }
      var data = this.GetData(await api.in_phieuthu(this.axios, input))
      if (data != null && data.length > 0) {

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
