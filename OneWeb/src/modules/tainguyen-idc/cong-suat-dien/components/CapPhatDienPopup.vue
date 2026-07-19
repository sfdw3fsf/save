<template>
  <ejs-dialog
    :visible="false"
    ref="dialog"
    :header="'Thông tin cấp phát điện'"
    :animationSettings="animationSettings"
    :close="onDialogClose"
    width="80%"
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
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Ghi lại
            </a>
          </li>
          <li>
            <a href="#" @click.prevent="doCancel"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Xoá </a>
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
          <!-- Khu chọn thanh nguồn + ổ cắm ở dưới -->
          <div class="row mb-3">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">Thanh nguồn</div>
                <div class="value">
                  <SelectExt
                    v-model="thanhnguon.value"
                    :dataSource="thanhnguon.options"
                    :allowFiltering="true"
                    dataTextField="TEN"
                    dataValueField="ID"
                    @change="onThanhNguonChange"
                    placeholder="Chọn thanh nguồn"
                    class="w-100"
                  ></SelectExt>
                </div>
              </div>
            </div>

            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">Ổ cắm</div>
                <div class="value">
                  <SelectExt
                    v-model="o_cam.value"
                    :dataSource="o_cam.options"
                    :allowFiltering="true"
                    dataTextField="TEN"
                    dataValueField="ID"
                    placeholder="Chọn ổ cắm"
                  ></SelectExt>
                </div>
              </div>
            </div>

            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key">Trạng thái ổ cắm</div>
                <div class="value">
                  <input type="text" class="form-control" :value="o_cam.value ? 'Chưa sử dụng' : ''" disabled />
                </div>
              </div>
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-12">
              <div class="info-row">
                <div class="key">Ghi chú</div>
                <div class="value">
                  <input
                    type="text"
                    v-model="noiDungCapPhat"
                    class="form-control"
                    placeholder="Nhập ghi chú nếu có..."
                  />
                </div>
              </div>
            </div>
          </div>
        </div>

        <div v-if="this.phieuTcId" class="box-form">
          <div class="legend-title d-flex justify-content-between align-items-center">
            <div class="pull-left">Danh sách các thiết bị cấp phát điện</div>
          </div>
          <!-- Grid danh sách thiết bị bên dưới -->
          <DataGrid
            v-bind:columns="[
              { fieldName: 'tenThietBi', headerText: 'Thiết bị', width: 150 },
              { fieldName: 'tenRack', headerText: 'Tên Rack', width: 150 },
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
    phieuTcId: { type: Number, default: null },
    selectedThietBi: { type: Object, default: () => ({}) }
  },
  data() {
    return {
      dsThietBi: [],
      noiDungCapPhat: '',
      dsThanhNguonOCam: [],
      thietbi: {}, // thiết bị đang chọn
      thanhnguon: { value: null, options: [] },
      o_cam: { value: null, options: [] },

      // dialog
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper'
    }
  },
  methods: {
    async openDialog() {
      if (this.phieuTcId) {
        const res = await api.getDSThietBiCapPhatDien(this.$root.context, this.phieuTcId)
        this.dsThietBi = res.data || []
      }
      if (this.selectedThietBi && this.selectedThietBi.idThietBi) {
        this.thietbi = this.selectedThietBi
      }
      if (this.thietbi.idRack) {
        const res = await api.getDsThanhNguonOCam(this.$root.context, this.thietbi.idRack)
        this.dsThanhNguonOCam = res.data
        this.thanhnguon.options = [
          ...new Map(
            this.dsThanhNguonOCam.map((item) => [item.idThanhNguon, { ID: item.idThanhNguon, TEN: item.tenThanhNguon }])
          ).values()
        ]
      }
      this.thanhnguon.value = null
      this.o_cam.value = null
      this.noiDungCapPhat = ''
      this.$refs.dialog.show()
    },
    onDialogClose() {
      this.$refs.dialog.hide()
    },
    onThietBiSelected(args) {
      this.thietbi = args.data
      // load lại danh sách thanh nguồn/ổ cắm theo rack
      if (this.thietbi.idRack) {
        api.getDsThanhNguonOCam(this.$root.context, this.thietbi.idRack).then((res) => {
          this.dsThanhNguonOCam = res.data
          this.thanhnguon.options = [
            ...new Map(
              this.dsThanhNguonOCam.map((item) => [
                item.idThanhNguon,
                { ID: item.idThanhNguon, TEN: item.tenThanhNguon }
              ])
            ).values()
          ]
          this.thanhnguon.value = null
          this.o_cam.value = null
          this.o_cam.options = []
        })
      }
    },
    onThanhNguonChange(e) {
      const id = e
      this.o_cam.options = this.dsThanhNguonOCam
        .filter((item) => item.idThanhNguon == id)
        .map((item) => ({ ID: item.idOCam, TEN: item.idOCam }))
      this.o_cam.value = null
    },
    async doAccept() {
      if (!this.thietbi.idThietBi) {
        this.$toast.error('Vui lòng chọn thiết bị cần cấp phát điện')
        return
      }
      if (!this.thanhnguon.value) {
        this.$toast.error('Vui lòng chọn thanh nguồn')
        return
      }
      if (!this.o_cam.value) {
        this.$toast.error('Vui lòng chọn ổ cắm')
        return
      }

      let flag = await this.$confirm('Có chắc bạn muốn cấp phát điện cho thiết bị vừa chọn?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
        .then(() => true)
        .catch(() => false)

      if (flag) {
        const result = {
          idThietBi: this.thietbi.idThietBi,
          idThanhNguon: this.thanhnguon.value,
          idOCam: this.o_cam.value,
          noiDung: this.noiDungCapPhat || ''
        }
        this.$emit('accept', result)
      }
    },
    doCancel() {
      this.thietbi = {}
      this.thanhnguon.value = null
      this.o_cam.value = null
      this.noiDungCapPhat = null
    }
  }
})
</script>
