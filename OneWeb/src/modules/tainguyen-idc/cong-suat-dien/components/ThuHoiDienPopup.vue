<template>
  <ejs-dialog
    :visible="false"
    ref="dialog"
    :isModal="true"
    :header="'Thông tin thu hồi điện'"
    :animationSettings="animationSettings"
    :close="onDialogClose"
    width="90%"
    height="auto"
    showCloseIcon="true"
    :target="target"
  >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="doAccept">
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Thu hồi
            </a>
          </li>
        </ul>
      </div>

      <div class="popup-body">
        <div class="box-form">
          <div class="row mb-3">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">Thiết bị</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietBi.tenThietBi" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12 mt-2">
              <div class="info-row">
                <div class="key">IDC</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietBi.tenIDC" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12 mt-2">
              <div class="info-row">
                <div class="key">Mặt sàn</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietBi.tenMatSan" disabled />
                </div>
              </div>
            </div>
          </div>
          <div class="row mb-3">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">Tên Rack</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietBi.tenRack" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">U trên</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietBi.viTriUTren" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">U dưới</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietBi.viTriUDuoi" disabled />
                </div>
              </div>
            </div>
          </div>
          <div class="row mb-3">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">Tên thanh nguồn</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietBi.tenThanhNguon" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">ID ổ cắm</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietBi.idOCam" disabled />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">Trạng thái ổ cắm</div>
                <div class="value">
                  <input type="text" class="form-control" :value="thietBi.trangThaiOcamText" disabled />
                </div>
              </div>
            </div>
          </div>
          <div class="row mb-3">
            <div class="col-12">
              <div class="info-row">
                <label class="key">Ghi chú:</label>
                <div class="value">
                  <input
                    type="text"
                    v-model="noiDungThuHoi"
                    class="form-control"
                    placeholder="Nhập ghi chú nếu có..."
                  />
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Bảng danh sách thiết bị -->
        <div v-if="phieuTcId" class="box-form">
          <div class="legend-title d-flex justify-content-between align-items-center">
            <div class="pull-left">Danh sách các thiết bị thu hồi điện</div>
          </div>
          <DataGrid
            v-bind:columns="[
              { fieldName: 'tenThietBi', headerText: 'Thiết bị', width: 150 },
              { fieldName: 'tenRack', headerText: 'Rack', width: 150 },
              { fieldName: 'viTriUTren', headerText: 'U trên', width: 80 },
              { fieldName: 'viTriUDuoi', headerText: 'U dưới', width: 80 },
              { fieldName: 'tenIDC', headerText: 'IDC', width: 150 },
              { fieldName: 'tenMatSan', headerText: 'Mặt sàn', width: 150 },
              { fieldName: 'tenThanhNguon', headerText: 'Thanh nguồn', width: 120 },
              { fieldName: 'idOCam', headerText: 'Ổ cắm', width: 100 },
              { fieldName: 'trangThaiOcamText', headerText: 'Trạng thái ổ cắm', width: 150 }
            ]"
            :dataSource="dsThietBi"
            :showColumnCheckbox="false"
            :allowPaging="true"
            dataKeyField="idThietBi"
            :showFilter="true"
            ref="gridThuHoi"
            @rowSelected="onRowSelected"
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
    phieuTcId: { type: Number, default: null },
    selectedThietBi: { type: Object, default: null }
  },
  data() {
    return {
      dsThietBi: [],
      noiDungThuHoi: '',
      thietBi: {},
      // dialog config ui
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper'
    }
  },
  methods: {
    onDialogClose() {
      this.$refs.dialog.hide()
    },
    async openDialog() {
      if (this.phieuTcId) {
        const res = await api.getDsThietBiThuHoiDien(this.$root.context, this.phieuTcId)
        this.dsThietBi = (res.data || []).map((x) => ({
          ...x,
          trangThaiOcamText: x.trangThaiOcam === 1 ? 'Đã cắm nguồn' : 'Chưa cắm nguồn'
        }))
        this.thietBi = {}
      }
      if (this.selectedThietBi) {
        this.thietBi = this.selectedThietBi
      } 
      this.noiDungThuHoi = ''
      this.$refs.dialog.show()
    },
    onRowSelected(args) {
      this.thietBi = args.data
    },
    async doAccept() {
      if (!this.thietBi) {
        this.$alert('Vui lòng chọn thiết bị cần thu hồi')
        return
      }

      let flag = await this.$confirm(
        `Bạn có chắc muốn thu hồi công suất điện cho thiết bị "${this.thietBi.tenThietBi}"?`,
        'Xác nhận thực hiện',
        { confirmButtonText: 'Có', cancelButtonText: 'Không', type: 'info' }
      )
        .then(() => true)
        .catch(() => false)

      if (flag) {
        const result = {
          idThietBi: this.thietBi.idThietBi,
          ocamId: this.thietBi.idOCam,
          noiDung: this.noiDungThuHoi || ''
        }
        this.$emit('accept', result)
        this.$refs.dialog.hide()
      }
    }
  }
})
</script>
