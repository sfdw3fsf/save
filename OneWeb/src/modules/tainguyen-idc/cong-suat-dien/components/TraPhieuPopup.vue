<template>
  <ejs-dialog
    :visible="false"
    ref="dialog"
    :isModal="true"
    :header="'Trả phiếu'"
    :animationSettings="animationSettings"
    width="80%"
    height="auto"
    showCloseIcon="true"
  >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <b-link href="javascript:void(0)" @click="doAccept"> <span class="icon one-save"></span>Trả phiếu </b-link>
          </li>
        </ul>
      </div>
      <div class="modal-content p-2 ">
        <!-- THÔNG TIN CÔNG VIỆC -->
        <div class="row form-section">
          <div class="col-12">
            <div class="box-form">
              <div class="row">
                <div class="legend-title">Lý do trả phiếu</div>
              </div>
              <div class="row">
                <!-- Left column - 7 fields -->
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Lý do</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        v-model="lyDoTraPhieu"
                        placeholder="Nhập lý do trả phiếu"
                      />
                    </div>
                  </div>
                </div>
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
import api from '../api'
Vue.use(DialogPlugin)

export default Vue.extend({
  props: {
    phieuTcId: {
      type: Number,
      default: null
    }
  },
  data() {
    return {
      lyDoTraPhieu: '',
      animationSettings: { effect: 'Zoom' }
    }
  },
  methods: {
    async openDialog() {
      this.$refs.dialog.show()
    },
    async closeDialog() {
      this.$refs.dialog.hide()
    },
    doAccept() {
      this.$confirm('Có chắc bạn muốn trả phiếu?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
        .then(async () => {
          const payload = {
            phieuTcId: this.phieuTcId,
            trangthaiId: 23, // 23 : trang thai tra phieu
            lyDoTraPhieu: this.lyDoTraPhieu
          }
          try {
            await api.updateTrangThaiPhieuTC(this.$root.context, payload)
            this.$emit('accept', this.phieuTcId)
            this.$toast.success('Trả phiếu thành công')
            this.closeDialog()
            this.$router.push('/tainguyen-idc/ketqua-congsuatdien')
          } catch (e) {
            if (e.response && e.response.data) {
              this.$toast.error('Lỗi trả phiếu: ' + e.response.data.message)
            } else {
              this.$toast.error('Lỗi trả phiếu: ' + e.message)
            }
          }
        })
        .catch(() => {
          // do nothing
        })
    }
  }
})
</script>
