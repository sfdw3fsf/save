<template>
  <div class="modal-content popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="onSave">
            <span class="icon one-save"></span>Ghi lại
          </a>
        </li>
        <li>
          <a @click="resetFilter">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Thông tin người giới thiệu</div>
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w90">Số máy/Acc</div>
              <div class="value">
                <div class="input-more-button">
                  <button ref="searchAccountBtnRef" class="btn" @click="showModalSearchAccount">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <input ref="accRef" type="text" class="form-control highlight font-weight-bold" v-model.trim="nguoiGTFilter.acc" :readonly="true">
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 100px">Mã thanh toán</div>
              <div class="value">
                <input type="text" class="form-control font-weight-bold" ref="maThanhToanRef" v-model.trim="nguoiGTFilter.maThanhToan" :readonly="true">
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 100px">Tên người GT</div>
              <div class="value">
                <input type="text" class="form-control font-weight-bold" ref="tenNguoiGTRef" v-model.trim="nguoiGTFilter.tenNguoiGT" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key" style="width: 60px">Số ĐT</div>
              <div class="value">
                <input type="text" class="form-control font-weight-bold text-primary" ref="soDienThoaiRef" v-model.trim="nguoiGTFilter.soDienThoai" />
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-3 col-12">
            <div class="info-row">
              <div class="key w100">Số tiền thưởng</div>
              <div class="value">
                <vue-numeric
                  class="form-control tright"
                  separator=","
                  placeholder="0"
                  output-type="Number"
                  disabled
                  v-model.number="nguoiGTFilter.tienThuong"
                ></vue-numeric>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12"></div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key" style="width: 100px">Địa chỉ NGT</div>
              <div class="value">
                <input type="text" class="form-control font-weight-bold" ref="diaChi" v-model.trim="nguoiGTFilter.diaChi" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          Lịch sử thay đổi thông tin giới thiệu
        </div>
        <div class="table-content">
          <DataGrid
            :columns="lichSu.headers"
            :dataSource="lichSu.dataSources"
            :showColumnCheckbox="false"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
          />
        </div>
      </div>
    </div>

    <b-modal
      ref="popupSearchAccount"
      size="xl"
      title="Tra cứu danh bạ"
      hide-footer
      body-class="modal-body p-0"
      modal-class="bss-modal bss-modal-tracuu-danhba"
    >
      <search-account @form-close="formCloseSearchAccount" :isPopup="true" />
    </b-modal>
  </div>
</template>

<script>
import SearchAccount from '@/modules/search/subscriber/SearchAccount/SearchAccount'
import api from '../api'

export default {
  name: 'PopupThongTinGt',
  components: { SearchAccount },
  props: ['hdkhId'],
  data () {
    return {
      nguoiGTFilter: {
        acc: '',
        maThanhToan: '',
        tenNguoiGT: '',
        soDienThoai: '',
        tienThuong: 100000,
        diaChi: ''
      },
      lichSu: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true },
          { fieldName: 'ma_tt', headerText: 'Mã thanh toán', allowFiltering: true },
          { fieldName: 'tien', headerText: 'Tiền', allowFiltering: true },
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true }
        ],
        dataSources: []
      },
      thanhToanId: null
    }
  },
  methods: {
    async init () {
      await Promise.all([this.getThongTinNguoiGT(), this.getDanhSachLichSu()])
    },
    async getThongTinNguoiGT () {
      this.loading(true)
      try {
        const response = await api.getThongTinNguoiGT(this.axios, { hdkh_id: this.hdkhId })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200' && data.data.length > 0) {
          const thongTin = data.data[0]
          this.nguoiGTFilter.acc = thongTin.ma_tb_gt
          this.nguoiGTFilter.maThanhToan = thongTin.ma_tt
          this.nguoiGTFilter.tenNguoiGT = thongTin.ten_nguoigt
          this.nguoiGTFilter.soDienThoai = thongTin.so_dt
          this.nguoiGTFilter.tienThuong = thongTin.tru_tien ? thongTin.tru_tien : 100000
          this.nguoiGTFilter.diaChi = thongTin.diachi_ngt
          this.thanhToanId = thongTin.thanhtoan_id
        } else {
          this.resetFilter()
        }
      } catch (e) {
        this.$root.$toast.error('Không lấy được thông tin người giới thiệu')
      } finally {
        this.loading(false)
      }
    },
    resetFilter () {
      this.nguoiGTFilter.acc = ''
      this.nguoiGTFilter.maThanhToan = ''
      this.nguoiGTFilter.tenNguoiGT = ''
      this.nguoiGTFilter.soDienThoai = ''
      this.nguoiGTFilter.diaChi = ''
    },
    async getDanhSachLichSu () {
      this.loading(true)
      try {
        const response = await api.getDanhSachLichSu(this.axios, { hdkh_id: this.hdkhId })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.lichSu.dataSources = data.data
        } else {
          this.lichSu.dataSources = []
        }
      } catch (e) {
        this.$root.$toast.error('Không lấy được danh sách lịch sử')
      } finally {
        this.loading(false)
      }
    },
    async onSave () {
      if (!this.nguoiGTFilter.acc) {
        this.$root.$toast.warning('Chưa có thông tin thuê bao gán!')
        this.$refs.accRef.focus()
        return false
      }
      if (!this.nguoiGTFilter.maThanhToan) {
        this.$root.$toast.warning('Chưa có thông tin thanh toán gán!')
        this.$refs.maThanhToanRef.focus()
        return false
      }
      if (!this.nguoiGTFilter.tenNguoiGT) {
        this.$root.$toast.warning('Chưa có tên người giới thiệu!')
        this.$refs.tenNguoiGTRef.focus()
        return false
      }
      if (!this.nguoiGTFilter.soDienThoai) {
        this.$root.$toast.warning('Chưa có số điện thoại người giới thiệu!')
        this.$refs.soDienThoaiRef.focus()
        return false
      }
      if (!this.nguoiGTFilter.diaChi) {
        this.$root.$toast.warning('Chưa có địa chỉ người giới thiệu!')
        this.$refs.diaChiRef.focus()
        return false
      }
      if (!this.thanhToanId) {
        this.$root.$toast.warning('Chưa có thông tin thuê bao gán!')
        this.$refs.searchAccountBtnRef.focus()
        return false
      }
      this.loading(true)
      try {
        const response = await api.luuThongTinGT(
          this.axios,
          {
            hdkh_id: this.hdkhId,
            ma_tb: this.nguoiGTFilter.acc,
            so_dt: this.nguoiGTFilter.soDienThoai,
            diachi_ngt: this.nguoiGTFilter.diaChi,
            ten_ngt: this.nguoiGTFilter.tenNguoiGT,
            thanhtoan_id: 1
          })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.$root.$toast.success('Cập nhật thành công')
          await this.init()
        }
      } catch (e) {
        this.$root.$toast.error('Không lưu được thông tin')
      } finally {
        this.loading(false)
      }
    },
    showModalSearchAccount () {
      this.$refs.popupSearchAccount.show()
    },
    formCloseSearchAccount (data) {
      if (data) {
        this.nguoiGTFilter.acc = data.ma_tb || ''
        this.nguoiGTFilter.maThanhToan = data.ma_tt || ''
        this.thanhToanId = (data.thuebao ? data.thuebao.thanhtoan_id : 0) || 0
      }
      this.$refs.popupSearchAccount.hide()
    }
  }
}
</script>
