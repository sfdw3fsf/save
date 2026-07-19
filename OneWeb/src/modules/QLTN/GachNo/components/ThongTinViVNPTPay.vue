<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgThongTinViVNPTPay"
    :header='"Thông tin ví"'
    showCloseIcon=true
    width='400'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    :isModal="true"
    >
    <div class="modal-content popup-box">
      <div class="popup-body">
        <div class="box-form">
            <div class="row">
                <div class="info-row">
                    <div class="key w100">Số dư ví</div>
                    <div class="value red bold" style="font-weight: bold; color: ">
                        {{ info.availableBalance | currency }}
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w100">Tên</div>
                    <div class="value ">
                        {{ info.name}}
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w100">WalletId </div>
                    <div class="value ">
                        {{ info.walletId}}
                    </div>
                </div>
                <div class="info-row">
                    <div class="key w100">SĐT ví </div>
                    <div class="value ">
                        {{ info.phoneNumber}}
                    </div>
                </div>
            </div>
        </div>
      </div>
  </div>
  </ejs-dialog>
</template>
<script>
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

Vue.use(DialogPlugin)

export default Vue.extend({
  components: {
  },
  props: {
    info: {
      type: Object,
      required: false
    },
    isConfirm: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  methods: {
    openDialog () {
      this.$refs.dlgThongTinViVNPTPay.show()
    },
    closeDialog () {
      this.$refs.dlgThongTinViVNPTPay.hide()
    },
    onDialogClose () {
    },
    onDialogOpen () {
    },
    clearForm () {
    },
    clearResult () {
    },
    closeHandler () {
      this.closeDialog()
    },
    confirmHandler () {
      this.$emit('confirmPayment', {})
      this.closeDialog()
    }
  }
})
</script>
