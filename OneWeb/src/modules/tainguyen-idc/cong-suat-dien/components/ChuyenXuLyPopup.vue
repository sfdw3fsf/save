<template>
  <ejs-dialog
    :visible="false"
    ref="dialog"
    :header="'Chuyển xử lý'"
    :animationSettings="animationSettings"
    width="80%"
    height="auto"
    showCloseIcon="true"
    :target="target"
    :isModal="true"
  >
    <div class="modal-content popup-box">
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">Thông tin công việc thực hiện</div>
            <div class="clearfix"></div>
          </div>

          <div class="row">
            <!-- first column -->
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w150">Mã công việc(*)</div>
                <div class="value">
                  <input
                    ref="maCongViec"
                    type="text"
                    class="form-control"
                    v-model.trim="congviec.maCongViec"
                    disabled
                  />
                </div>
              </div>

              <div class="info-row">
                <div class="key w150">Loại công việc(*)</div>
                <div class="value">
                  <input
                    ref="loaiCongViec"
                    type="text"
                    class="form-control"
                    v-model.trim="congviec.tenLoaiCv"
                    disabled
                  />
                </div>
              </div>

              <div class="info-row">
                <div class="key w150">Hạng mục triển khai(*)</div>
                <div class="value">
                  <input
                    ref="hangMucTrienKhai"
                    type="text"
                    class="form-control"
                    v-model.trim="congviec.hangMucTrienKhai"
                    disabled
                  />
                </div>
              </div>

              <div class="info-row">
                <div class="key w150">Căn cứ phiếu yêu cầu(*)</div>
                <div class="value">
                  <input
                    ref="canCuPhieuYC"
                    type="text"
                    class="form-control"
                    v-model.trim="congviec.canCuPhieuYC"
                    disabled
                  />
                </div>
              </div>

              <div class="info-row">
                <div class="key w150">Mã công việc cha(*)</div>
                <div class="value">
                  <input
                    ref="maCongViecCha"
                    type="text"
                    class="form-control"
                    v-model.trim="congviec.maCongViecCha"
                    disabled
                  />
                </div>
              </div>

              <div class="info-row">
                <div class="key w150">Khách hàng(*)</div>
                <div class="value">
                  <input
                    ref="tenKhachHang"
                    type="text"
                    class="form-control"
                    v-model.trim="congviec.tenKhachHang"
                    disabled
                  />
                </div>
              </div>

              <div class="info-row">
                <div class="key w150">Mã thuê bao(*)</div>
                <div class="value">
                  <input ref="maThueBao" type="text" class="form-control" v-model.trim="congviec.maThueBao" disabled />
                </div>
              </div>
            </div>
            <!-- second column -->
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w150">Đơn vị thực hiện(*)</div>
                <div class="value">
                  <input
                    ref="donViThucHien"
                    type="text"
                    class="form-control"
                    v-model.trim="congviec.donViThucHien"
                    disabled
                  />
                </div>
              </div>

              <div class="info-row">
                <div class="key w150">Người thực hiện(*)</div>
                <div class="value">
                  <SelectExt
                    v-model="selectedNhanVien"
                    :dataSource="dsNhanVien"
                    :allowFiltering="true"
                    dataTextField="TEN_NV"
                    dataValueField="NHANVIEN_ID"
                  ></SelectExt>
                </div>
              </div>
              <div class="info-row">
                <div class="key w150">Ngày tạo</div>
                <div class="value">
                  <input ref="ngayTao" type="text" class="form-control" v-model.trim="congviec.ngayTao" disabled />
                </div>
              </div>

              <div class="info-row">
                <div class="key w150">Trạng thái thực hiện(*)</div>
                <div class="value">
                  <input
                    ref="trangThaiThucHien"
                    type="text"
                    class="form-control"
                    v-model.trim="congviec.trangThaiThucHien"
                    disabled
                  />
                </div>
              </div>

              <div class="info-row">
                <div class="key w150">Thời điểm hoàn thành</div>
                <div class="value">
                  <input
                    ref="ngayHoanThanh"
                    type="text"
                    class="form-control"
                    v-model.trim="congviec.ngayHoanThanh"
                    disabled
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="modal-footer d-flex justify-content-center p-0 my-auto">
      <button @click="doAccept" class="btn btn-primary">Xác nhận chuyển</button>
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
    congviec: {
      type: Object,
      default: () => ({})
    },
    phieuTcId: {
      type: [String, Number],
      default: null
    } 
  },
  data() {
    return {
      donviId: null,
      dsNhanVien: [],
      selectedNhanVien: null,
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper'
    }
  },
  methods: {
    async openDialog() {
      await this.loadNhanVien()
      this.selectedNhanVien = null // Reset trước
      await this.$nextTick() // Đợi DOM update
      this.selectedNhanVien = this.congviec.idNguoiThucHien || null // Set lại giá trị
      this.$refs.dialog.show()
    },
    async closeDialog() {
      //reset form
      this.selectedNhanVien = null

      this.$refs.dialog.hide()
      await this.$nextTick()
    },
    async loadNhanVien() {
      this.donviId = this.$root.context.user.getProperty('donvi_id')
      try {
        const res = await api.getNhanVienByDonVi(this.$root.context, this.donviId)
        this.dsNhanVien = res.data || []
      } catch (err) {}
        return []
    },
    doAccept() {
      if (!this.selectedNhanVien) {
        this.$toast.error('Vui lòng chọn người thực hiện')
        return
      }
      this.$confirm('Có chắc bạn muốn chuyển xử lý công việc này cho người thực hiện đã chọn?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info'
      })
        .then(() => {
          const result = {
            idNguoiThucHien: this.selectedNhanVien
            
          }
          this.$emit('accept', result)
          this.closeDialog()
        })
        .catch(() => {
          // do nothing
        })
    },
    validate(result) {}
  }
})
</script>
