<template>
  <ejs-dialog
    :visible="false"
    ref="dialog"
    :header="'Chọn thiết bị thi công công suất điện'"
    :animationSettings="animationSettings"
    :close="onDialogClose"
    width="70%"
    height="auto"
    showCloseIcon="true"
    :target="target"
    :isModal="true"
  >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="doAccept">
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Lưu
            </a>
          </li>
          <li>
            <a href="#" @click.prevent="doCancel"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Đóng </a>
          </li>
        </ul>
      </div>

      <div class="popup-body">
        <div class="box-form">
          <!-- Thông tin thiết bị được chọn -->
          <div class="row mb-3">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">Thiết bị</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietbi.tenThietBi" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12 mt-2">
              <div class="info-row">
                <div class="key">IDC</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietbi.tenIDC" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12 mt-2">
              <div class="info-row">
                <div class="key">Mặt sàn</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietbi.tenMatSan" disabled />
                </div>
              </div>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">Tên Rack</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietbi.tenRack" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">U trên</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietbi.viTriUTren" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">U dưới</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietbi.viTriUDuoi" disabled />
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="box-form">
          <div class="legend-title d-flex justify-content-between align-items-center">
            <div class="pull-left">Danh sách thiết bị</div>
          </div>
          <DataGrid
            v-bind:columns="[
              { fieldName: 'tenThietBi', headerText: 'Thiết bị', width: 200 },
              { fieldName: 'tenRack', headerText: 'Rack', width: 150 },
              { fieldName: 'viTriUTren', headerText: 'U trên', width: 100 },
              { fieldName: 'viTriUDuoi', headerText: 'U dưới', width: 100 },
              { fieldName: 'tenIDC', headerText: 'IDC', width: 150 },
              { fieldName: 'tenMatSan', headerText: 'Mặt sàn', width: 150 }
            ]"
            :dataSource="dsThietBi"
            :showColumnCheckbox="false"
            :allowPaging="true"
            dataKeyField="idThietBi"
            :showFilter="true"
            ref="gridThietBi"
            @rowSelected="onThietBiSelected"
          />
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import Vue from 'vue'
import api from '../api'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
Vue.use(DialogPlugin)

export default Vue.extend({
  props: {
    phieuTcId: { type: Number, required: true },
    actionType: { type: String, required: true }
  },
  data() {
    return {
      dsThietBi: [],
      noiDung: '',
      thietbi: {},
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper'
    }
  },
  methods: {
    async openDialog() {
      if (this.actionType === 'CAP_PHAT') {
        const res = await api.getDSThietBiCapPhatDien(this.$root.context, this.phieuTcId)
        this.dsThietBi = res.data || []
      } else if (this.actionType === 'THU_HOI') {
        const res = await api.getDsThietBiThuHoiDien(this.$root.context, this.phieuTcId)
        this.dsThietBi = res.data || []
      } else if (this.actionType === 'CHUYEN_DOI') {
        const res = await api.getDSThietBiChuyenDoiDien(this.$root.context, this.phieuTcId)
        this.dsThietBi = res.data || []
      }
      this.thietbi = {}
      this.noiDung = ''
      this.$refs.dialog.show()
    },
    onDialogClose() {
      this.$refs.dialog.hide()
    },
    onThietBiSelected(args) {
      this.thietbi = args.data
    },
    async doAccept() {
      if (!this.thietbi.idThietBi) {
        this.$toast.error('Vui lòng chọn thiết bị')
        return
      }

      let flag = await this.$confirm(`Xác nhận chọn thiết bị?`, 'Xác nhận', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
        .then(() => true)
        .catch(() => false)

      if (!flag) return

      const payload = {
        phieuTcId: this.phieuTcId,
        thietBiId: this.thietbi.idThietBi
      }

      try {
        let res = await api.selectThietBiCongSuatDien(this.$root.context, payload)
        this.$toast.success(res?.data.message || 'Thành công')
        this.$emit('accept', this.phieuTcId)
        this.$refs.dialog.hide()
      } catch (err) {
        this.$toast.error(err?.message || 'Có lỗi xảy ra')
      }
    },
    doCancel() {
      this.thietbi = {}
      this.noiDung = ''
      this.$refs.dialog.hide()
    }
  }
})
</script>
