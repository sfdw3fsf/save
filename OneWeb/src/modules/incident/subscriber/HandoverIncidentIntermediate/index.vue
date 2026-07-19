<template>
  <div>
    <breadcrumb :header="header" />

    <vue-nav>
      <ul class="list">
        <li>
          <a style="cursor: pointer;" @click="getThongTinThueBaoTLS">
            <span class="icon one-search"></span>Tìm kiếm
          </a>
        </li>
        <li>
          <a style="cursor: pointer;" @click="giaoPhieu">
            <span class="icon one-file-arrow-right"></span>Giao phiếu
          </a>
        </li>
        <li>
          <a style="cursor: pointer;" @click="huyGiaoPhieu">
            <span class="icon one-cancel"></span>Huỷ giao
          </a>
        </li>
      </ul>
    </vue-nav>

    <div class="page-content">
      <vue-card label="Thông tin phiếu yêu cầu">
        <b-row>
          <b-col sm="3" cols="12">
            <vue-input
              labelWidth="110"
              label="Dịch vụ"
              v-model="filterForm.dichVu"
            />
            <div class="info-row">
              <div class="key">Số máy/Acc</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="onOpenSearchAccountDialog">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                  <bss-error-marker :has-error="$v.filterForm.acc.$error">
                    <input
                      type="text"
                      class="form-control"
                      v-model="filterForm.acc"
                      ref="acc"
                      @keyup.enter="getThongTinThueBaoTLS"
                    >
                  </bss-error-marker>
                </div>
              </div>
            </div>
            <vue-input
              labelWidth="110"
              label="Địa chỉ LĐ đầu"
              v-model="filterForm.ldDauAddress"
            />
            <vue-input
              labelWidth="110"
              label="Địa chỉ LĐ cuối"
              v-model="filterForm.ldCuoiAddress"
            />
          </b-col>

          <b-col sm="3" cols="12">
            <vue-input
              labelWidth="110"
              label="Loại hình TB"
              v-model="filterForm.loaiHinhThueBao"
            />
            <vue-input
              labelWidth="110"
              label="Tên TB"
              v-model="filterForm.tenThueBao"
            />
            <vue-input
              labelWidth="110"
              label="Tổ thi công đầu"
              v-model="filterForm.toThiCongDau"
            />
            <vue-input
              labelWidth="110"
              label="Tổ thi công cuối"
              v-model="filterForm.toThiCongCuoi"
            />
          </b-col>
          <b-col sm="6" cols="12">
            <vue-input
              labelWidth="140"
              label="Điểm chia giao phiếu"
              v-model="giaoPhieuFilter.diemChiaGiaoPhieu"
            />
            <b-row>
              <b-col sm="6" cols="12">
                <div class="info-row">
                  <div class="key w140">Hướng giao</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt
                        dataValueField="huonggiao_id"
                        dataTextField="huonggiao"
                        v-model="giaoPhieuFilter.huongGiao"
                        :settings="{ dropdownParent: $refs['huongGiao'] }"
                        :dataSource="danhSachHuongGiao"
                      />
                    </div>
                  </div>
                </div>
              </b-col>
              <b-col sm="6" cols="12">
                <div class="info-row">
                  <div class="key w80">Điểm chia</div>
                  <div class="value">
                    <div class="select-custom">
                      <bss-error-marker :has-error="hasGiaoPhieuValidate && $v.giaoPhieuFilter.diemChia.$error">
                        <SelectExt
                          dataValueField="donvi_id"
                          dataTextField="ten_dv"
                          v-model="giaoPhieuFilter.diemChia"
                          :settings="{ dropdownParent: $refs['diemChia'] }"
                          :dataSource="danhSachTram"
                        />
                      </bss-error-marker>
                    </div>
                  </div>
                </div>
              </b-col>
            </b-row>
            <vue-input
              labelWidth="140"
              label="Nội dung giao"
              v-model="giaoPhieuFilter.noiDungGiao"
            />
          </b-col>
        </b-row>
        <div class="legend-title mart20">Chi tiết điều hành xử lý sự cố</div>
        <DataGrid
          :columns="dataGrid.columns"
          :dataSource="dataGrid.dataSource"
          :showColumnCheckbox="false"
          :enable-paging-server="false"
          :allowPaging="true"
          :showFilter="true"
          @selectedRowChanged="onSelectedRowChanged"
        />
      </vue-card>
    </div>

    <ejs-dialog
      ref="searchAccountDialog"
      showCloseIcon="true"
      target="#app"
      width="80%"
      height="800"
      :enableResize="true"
      :visible="false"
      :position="position"
      :header="'Tra cứu danh bạ'"
    >
      <div class="top-modal popupBody">
        <search-account :isPopup="true" @form-close="onCloseSearchAccountDialog" />
      </div>
    </ejs-dialog>
  </div>
</template>

<script>
import breadcrumb from '@/components/breadcrumb'
import BssErrorMarker from '@/components/BssErrorMarker'
import api from './api'
import { required } from 'vuelidate/lib/validators'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'

export default {
  name: 'HandoverIncidentIntermediate',
  components: { breadcrumb, BssErrorMarker, SearchAccount },
  props: {
    baoHongId: { type: Number, default: null },
    phieuId: { type: Number, default: null }
  },
  data () {
    return {
      header: {
        title: 'Giao phiếu điểm chia trung gian cấu hình TSL',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      filterForm: {
        dichVu: '',
        acc: '',
        ldDauAddress: '',
        ldCuoiAddress: '',
        loaiHinhThueBao: '',
        tenThueBao: '',
        toThiCongDau: '',
        toThiCongCuoi: ''
      },
      giaoPhieuFilter: {
        diemChiaGiaoPhieu: '',
        huongGiao: '',
        diemChia: '',
        noiDungGiao: ''
      },
      danhSachTram: [],
      danhSachHuongGiao: [],
      phieuSelected: null,
      position: { X: 'center', Y: 'top' },
      hasGiaoPhieuValidate: false,
      dataGrid: {
        columns: [
          { fieldName: 'dvg', headerText: 'Đơn vị giao', allowFiltering: true },
          { fieldName: 'nv_giao', headerText: 'Nhân viên giao', allowFiltering: true },
          { fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true },
          { fieldName: 'nd_giao', headerText: 'Nội dung giao', allowFiltering: true },
          { fieldName: 'dvn', headerText: 'Đơn vị nhận', allowFiltering: true },
          { fieldName: 'ngay_tra', headerText: 'Ngày trả', allowFiltering: true },
          { fieldName: 'nd_tra', headerText: 'Nội dung trả', allowFiltering: true },
          { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', allowFiltering: true },
          { fieldName: 'nv_th', headerText: 'Nhân viên TH', allowFiltering: true },
          { fieldName: 'ngay_th', headerText: 'Ngày TH', allowFiltering: true },
          { fieldName: 'ghichu_th', headerText: 'Nội dung TH', allowFiltering: true },
          { fieldName: 'huonggiao', visible: false, isGroupedColumn: true }
        ],
        dataSource: []
      },
      vphieu_id: null,
      vbaohong_id: null
    }
  },

  validations: {
    filterForm: {
      acc: { required }
    },
    giaoPhieuFilter: {
      diemChia: { required }
    }
  },

  async created () {
    await this.getThongTinTramVaHuongGiao()
  },

  methods: {
    initData () {
      this.vbaohong_id = this.baoHongId
      this.vphieu_id = this.phieuId
    },
    async getThongTinTramVaHuongGiao () {
      this.loading(true)
      try {
        const response = await api.getThongTinTramVaHuongGiao(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.danhSachTram = data.data.vtram
          this.danhSachHuongGiao = data.data.vhuonggiao
          if (this.danhSachHuongGiao.length > 0) {
            this.giaoPhieuFilter.huongGiao = this.danhSachHuongGiao[0].huonggiao_id
          }
        }
      } catch (e) {
        this.$toast.error('Không tìm thấy thông tin điểm chia và hướng giao')
      } finally {
        this.loading(false)
      }
    },
    async getThongTinThueBaoTLS () {
      if (this.isFormValid()) {
        this.loading(true)
        try {
          const response = await api.getThongTinThueBaoTLS(
            this.axios,
            { vbaohong_id: this.vbaohong_id, vma_tb: this.filterForm.acc, vphieu_id: this.vphieu_id }
          )
          const data = await response.data
          if (data.error_code === 'BSS-00000000' && data.error === '200') {
            const danhSachThueBao = data.data.ttdb
            const dataSource = data.data.ctbh
            if (danhSachThueBao.length > 0) {
              const thueBao = danhSachThueBao[0]
              this.setFilter(thueBao)
            }
            this.setGridDataSource(dataSource)
          }
        } catch (e) {
          this.$toast.error('Không tìm thấy thông tin')
          this.resetFilter()
        } finally {
          this.loading(false)
        }
      }
    },
    isFormValid () {
      this.hasGiaoPhieuValidate = false
      this.$v.$touch()
      if (this.$v.$invalid && !this.$v.filterForm.acc.required) {
        this.$toast.warning('Vui lòng nhập mã thuê bao')
        return false
      }
      return true
    },
    isGiaoPhieuFormValid () {
      this.hasGiaoPhieuValidate = true
      this.$v.$touch()
      if (this.$v.$invalid && !this.$v.giaoPhieuFilter.diemChia.required) {
        this.$toast.warning('Bạn chưa chọn trạm trung gian')
        return false
      }
      return true
    },
    setFilter (thueBao) {
      this.filterForm.dichVu = thueBao.ten_dvvt
      this.filterForm.tenThueBao = thueBao.ten_tb
      this.filterForm.ldDauAddress = thueBao.dcdau
      this.filterForm.ldCuoiAddress = thueBao.dccuoi
      this.filterForm.toThiCongDau = thueBao.totcdau
      this.filterForm.toThiCongCuoi = thueBao.totccuoi
      this.filterForm.loaiHinhThueBao = thueBao.loaihinh_tb
    },
    setGridDataSource (dataSource) {
      this.dataGrid.dataSource = [...dataSource]
    },
    resetFilter () {
      this.filterForm.dichVu = ''
      this.filterForm.ldDauAddress = ''
      this.filterForm.ldCuoiAddress = ''
      this.filterForm.loaiHinhThueBao = ''
      this.filterForm.tenThueBao = ''
      this.filterForm.toThiCongDau = ''
      this.filterForm.toThiCongCuoi = ''
      this.giaoPhieuFilter.diemChiaGiaoPhieu = ''
      this.giaoPhieuFilter.huongGiao = ''
      this.giaoPhieuFilter.diemChia = ''
      this.giaoPhieuFilter.noiDungGiao = ''
      this.dataGrid.dataSource = []
    },
    onOpenSearchAccountDialog () {
      this.$refs.searchAccountDialog.show()
    },
    async onCloseSearchAccountDialog (data) {
      if (data && data.ma_tb) {
        this.filterForm.acc = data.ma_tb
        await this.getThongTinThueBaoTLS()
      }
      this.$refs.searchAccountDialog.hide()
    },
    onSelectedRowChanged (data) {
      if (data) {
        this.phieuSelected = {...data}
        this.giaoPhieuFilter.diemChiaGiaoPhieu = data.dvn || ''
        this.giaoPhieuFilter.noiDungGiao = data.nd_giao || ''
      }
    },
    async giaoPhieu () {
      if (!this.phieuSelected) {
        this.$root.$toast.warning('Bạn phải chọn phiếu để giao!')
        return false
      }
      if (this.isGiaoPhieuFormValid()) {
        this.vbaohong_id = this.phieuSelected.baohong_id
        this.vphieu_id = this.phieuSelected.phieu_id
        this.loading(true)
        try {
          const response = await api.giaoPhieu(
            this.axios,
            {
              vbaohong_id: this.phieuSelected.baohong_id,
              vdsdonvi_nhan_id: this.giaoPhieuFilter.diemChia,
              vphieu_id: this.phieuSelected.phieu_id
            }
          )
          const data = await response.data
          if (data.error_code === 'BSS-00000000' && data.error === '200') {
            this.$toast.success(data.message)
            await this.getThongTinThueBaoTLS()
          }
        } catch (e) {
          this.$toast.error('Không tìm thấy thông tin')
        } finally {
          this.loading(false)
        }
      }
    },
    async huyGiaoPhieu () {
      if (!this.phieuSelected) {
        this.$root.$toast.warning('Bạn phải chọn phiếu để giao!')
        return false
      }
      if (+this.phieuSelected.huonggiao_id !== 5001 || +this.vphieu_id !== +this.phieuSelected.phieu_cha_id) {
        this.$root.$toast.warning('Chỉ hủy giao với phiếu giao đến trạm trung gian!')
        return false
      }
      this.loading(true)
      try {
        const response = await api.huyGiaoPhieu(this.axios, { vphieu_id: this.phieuSelected.phieu_id })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.$toast.success(data.message)
          await this.getThongTinThueBaoTLS()
        }
      } catch (e) {
        this.$toast.error('Không tìm thấy thông tin')
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>

<style>
  .top-modal .page-content {
    top: 130px !important;
  }
</style>
