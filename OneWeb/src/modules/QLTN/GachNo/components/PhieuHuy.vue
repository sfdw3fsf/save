<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgPhieuHuy"
    :header='"Danh sách các phiếu hủy"'
    showCloseIcon=true
    width='1300'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    :beforeClose="onBeforeClose"
    :beforeOpen="onBeforeOpen"
    >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
          <ul class="list">
              <li>
                <a href="#" v-on:click.prevent="doSearch()"><span class="icon ui-1_zoom-split nc-icon-outline"></span> Tìm kiếm</a>
              </li>
              <li>
                <a :class="{ disabled: !f_enabled }" href="#" v-on:click.prevent="doExportExcel()"><span class="icon -ap icon-file-excel"></span> Xuất Excel</a>
              </li>
              <li>
                <a :class="{ disabled: !f_enabled }" href="#" v-on:click.prevent="printPhieu()" title="Thay đổi thông tin seri piếu gạch"><span class="icon ui-1_edit-76 nc-icon-glyph"></span> Sửa phiếu</a>
                <PopupInPhieuThu ref="refPopupInPhieuThu" @accept="acceptPrintPhieuThuHandler"></PopupInPhieuThu>
              </li>
              <li>
                <a href="#" v-on:click.prevent="doCancel()" title="Hủy thao tác"><span class="icon ui-1_circle-remove  nc-icon-glyph"></span> Hủy</a>
              </li>

          </ul>
          <div class="more">
              <a href="#">
                  <span class="icon -ap  icon-dots-three-vertical"></span>
              </a>
          </div>
      </div>
      <div class="popup-body">
          <div class="box-form">
              <div class="legend-title mart0">
                  <div class="pull-left mart10">
                      Thông tin tìm kiếm
                  </div>
                  <div class="pull-right">
                  </div>
                  <div class="clearfix"></div>
              </div>
              <div class="row">
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                          <div class="key w100">Mã thanh toán</div>
                          <div class="value">
                            <input v-model="params.p_ma_tt" @keyup.enter="doSearch" type="text" class="form-control highlight">
                          </div>
                      </div>
                  </div>

                  <div class="col-sm-5 col-12">
                      <div class="info-row">
                          <div class="key w80">
                              <div class="">
                                <b-form-checkbox
                                  v-model = "params.p_chk_kycuoc"
                                  class = "check"
                                  value = '1'
                                  unchecked-value = '0'
                                >
                                </b-form-checkbox>
                              </div>
                          </div>
                          <div class="value">
                              <app-ky-cuoc :value="p_default_kycuoc" :isDisabled="!chk_kycuoc" @chukycuocChange="chukycuocChangeHandler"></app-ky-cuoc>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w100">
                              <span class="name">Tên thanh toán</span>
                          </div>
                          <div class="value">
                            <input v-model="params.p_ten_tt" type="text" class="form-control">
                          </div>
                      </div>
                  </div>

              </div>
              <div class="row">

                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                          <div class="key w100">
                              <div class="">
                                <b-form-checkbox
                                  v-model = "params.p_chk_ngay_huy"
                                  class = "check"
                                  value = '1'
                                  unchecked-value = '0'
                                >
                                  <span class="name">Ngày TT</span>
                                </b-form-checkbox>
                              </div>
                          </div>
                          <div class="value">
                            <ejs-datepicker
                              v-model="p_ngay_huy"
                              :value="configs.dateConfig.value"
                              :format="configs.dateConfig.format"
                              :allowEdit=false
                              :showClearButton=false
                              :enabled="chk_ngay_huy"
                              >
                            </ejs-datepicker>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-5 col-12">
                      <div class="info-row">
                          <div class="key w80">
                              <span class="name">User hủy</span>
                          </div>
                          <div class="value">
                            <input v-model="params.p_user_huy" @keyup.enter="doSearch" type="text" class="form-control highlight">
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-4 col-12">
                      <div class="info-row">
                          <div class="key w100">
                              <span class="name">Địa chỉ TT</span>
                          </div>
                          <div class="value">
                            <input v-model="params.p_diachi_tt" type="text" class="form-control">
                          </div>
                      </div>
                  </div>

              </div>
          </div>
          <div class="box-form">
              <div class="legend-title">Danh sách phiếu hủy</div>
              <div class="table-content">
                <DataGrid
                  v-bind:columns="[
                    {fieldName: 'NGAYHUY', headerText: 'Ngày hủy', textAlign: 'Right', allowFiltering: true, width: 120},
                    {fieldName: 'MA_TT', headerText: 'Mã TT', textAlign: 'Left', allowFiltering: true, width: 100},
                    {fieldName: 'NGAY_TT', headerText: 'Ngày TT', textAlign: 'Right', allowFiltering: true, width: 90},
                    {fieldName: 'SOTIENHUY', headerText: 'Tổng tiền', textAlign: 'Right', type:'number', format: 'N0', allowFiltering: true, width: 100},
                    {fieldName: 'SO_HD', headerText: 'Số HĐ', textAlign: 'Right', allowFiltering: true, width: 100},
                    {fieldName: 'HINHTHUC_TT', headerText: 'Hình thức TT', textAlign: 'Left', allowFiltering: true},
                    {fieldName: 'NGUOIHUY', headerText: 'User hủy', textAlign: 'Left', allowFiltering: true, width: 100},
                    {fieldName: 'SOPHIEU', headerText: 'Số phiếu', textAlign: 'Right', format: 'N0', allowFiltering: true, width: 100},
                    {fieldName: 'HINHTHUC_TT', headerText: 'Kiểu gạch', textAlign: 'Left', allowFiltering: true, width: 200},
                    ]"
                  v-bind:aggregates="[
                    {field: 'SOTIENHUY', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
                  ]"
                  v-bind:dataSource="dsPhieuHuy"
                  :showFilter="true"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  :allowExcelExport="true"
                  :totalRecords="pageinfo.totalElement"
                  :pageSize="pageinfo.maxSize"
                  :pageIndex="pageinfo.page"

                  @selectedRowChanged="phieuTTRowChanged"
                  @pageChanged="phieuTTpageChangeHandler"
                  :panelDataHeight='"300"'
                  ref="dsPhieuHuy"
                  :key="keyDSPhieuHuy"
                  >
                </DataGrid>
              </div>
          </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
// import EventBus from '@/utils/eventBus'
import GachNoAPI from '../../api/GachNoAPI'

import KyCuoc from '@/components/KyCuoc2'
import PopupInPhieuThu from '../../components/PopupInXoaSuaPhieuThanhToan/PopupInPhieuThu'
import moment from 'moment'
import { DialogUtility } from '@syncfusion/ej2-popups'
Vue.use(DialogUtility)

Vue.use(DialogPlugin)

export default Vue.extend({
  components: {
    appKyCuoc: KyCuoc,
    PopupInPhieuThu
  },
  props: {
    kycuoc: {
      type: String,
      required: false
    },
    ma_tt: {
      type: String,
      required: false
    },
    loai_tien: {
      type: Number,
      default: 1 // Tiền VNĐ
    }
  },
  computed: {
    chk_kycuoc () {
      return this.params.p_chk_kycuoc === '1'
    },
    f_enabled () {
      return this.dsPhieuHuy.length > 0
    },
    chk_ngay_huy () {
      return this.params.p_chk_ngay_huy === '1'
    },
    f_kycuoc () {
      return this.params.p_kycuoc
    },
    p_ngay_huy: {
      get () {
        return this.params.p_ngay_huy
      },
      set (value) {
        this.params.p_ngay_huy = value
      }
    }
  },
  async created () {
    this.loadDSTinhThanh()

    if (this.params.p_ma_tt !== null && this.params.p_ma_tt.trim() !== '') this.doSearch()
  },
  data () {
    return {
      footerSum: function () {
        return {
          template: Vue.component('sumTemplate', {
            template: `<span>{{data.Sum}}</span>`,
            data: function () { return {data: {data: {}}} }
          })
        }
      },
      fieldsDichVu: { text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
      configs: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        fieldsNhomHTTT: { text: 'NHOM_HTTT', value: 'NHOM_HTTT_ID' },
        fieldsHTTT: { text: 'HTTT', value: 'HTTT_ID' }
      },
      p_default_kycuoc: null,
      params: {
        // p_kycuoc: moment(new Date()).subtract(1,'months').endOf('month').format('YYYYMM') + '01',
        p_chk_kycuoc: '1',
        p_kycuoc: moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM') + '01',
        p_ma_tt: this.ma_tt,
        p_ten_tt: null,
        p_diachi_tt: null,
        p_user_huy: null,
        p_chk_ngay_huy: '0',
        p_ngay_huy: new Date(),
        p_tinhthanh_options: [],
        p_tinhthanh: null
      },
      searchData: {
        pKyCuoc: null,
        pMaTinhThuHo: null,
        pMaTT: null,
        pNgayHuy: null,
        pNguoiHuy: null,
        pageNo: 0,
        pageSize: 0,
        totalRow: 1
      },
      pageinfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      selectedRow: null,
      dsPhieuHuy: [],
      keyDSPhieuHuy: 0,
      totalItemsPhieuHuy: 0,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  methods: {
    initDefaultValue () {
      this.params.p_ma_tt = this.ma_tt
      this.params.p_user_huy = this.$auth.getUserName()
      this.params.p_ngay_huy = new Date()
      this.params.p_chk_ngay_huy = '0'
      if (this.kycuoc !== null) {
        this.p_default_kycuoc = this.kycuoc
        this.params.p_kycuoc = this.kycuoc
      }
      /*
        this.getTongNoTheoKyHoaDon().then(
          (response) => {
            if(response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined)
            {
              this.dsKhoanNoTheoKyHD = response.data.data
            }
          }
        )
        */
    },
    loadDSTinhThanh: function () {

    },
    doSearch: function () {
      if (this.params.p_ma_tt !== null) this.params.p_ma_tt = this.params.p_ma_tt.trim()

      if (this.params.p_user_huy !== null) this.params.p_user_huy = this.params.p_user_huy.trim()

      this.dsPhieuHuy = []
      this.pageinfo.totalElement = 0
      this.pageinfo.page = 0

      this.searchData = {
        pKyCuoc: this.params.p_chk_kycuoc === '1' ? this.params.p_kycuoc : null,
        pMaTinhThuHo: null,
        pMaTT: this.params.p_ma_tt !== null && this.params.p_ma_tt !== '' ? this.params.p_ma_tt : null,
        pNgayHuy: this.params.p_chk_ngay_huy === '1' ? moment(this.params.p_ngay_huy).format('DD/MM/YYYY') : null,
        pNguoiHuy: (this.params.p_user_huy !== null && this.params.p_user_huy !== '') ? this.params.p_user_huy : null,
        pageNo: this.pageinfo.page + 1,
        pageSize: this.pageinfo.maxSize,
        totalRow: 0
      }

      this.fetchDataSearch()
    },
    fetchDataSearch: function () {
      this.loading(true)

      GachNoAPI.getDSPhieuDaXoa(this.axios, this.searchData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
                response.data.data !== undefined &&
                response.data.data.data !== undefined &&
                response.data.data.data.length > 0) {
              this.keyDSPhieuHuy++
              this.dsPhieuHuy = response.data.data.data
              this.pageinfo.totalElement = response.data.data.totalElement
              this.pageinfo.page = response.data.data.page - 1
            }
          }
        )
        .catch(err => {
          this.$toast.error(err.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },
    doExportExcel: function () {
      let excelExportProperties = {
        dataSource: this.dsPhieuHuy
      }
      this.$refs.dsPhieuHuy.excelExport(excelExportProperties)
    },
    doEdit: function () {
      this.$toast.error('Chức năng in phiếu thu đang được phát triển. Vui lòng thử lại sau.')
    },
    doCancel: function () {
      this.params.p_ma_tt = null
      this.params.p_ten_tt = null
      this.params.p_diachi_tt = null
      // this.params.p_user_huy = null
      this.params.p_ngay_huy = new Date()
    },
    openDialog () {
      this.initDefaultValue()

      this.$refs.dlgPhieuHuy.show()
    },
    closeDialog () {
      this.$refs.dlgPhieuHuy.hide()
    },
    onDialogClose () {
      this.clearForm()
      this.clearResult()
    },

    onDialogOpen () {
      if (this.params.p_ma_tt !== undefined && this.params.p_ma_tt !== null && this.params.p_ma_tt !== '') { this.doSearch() }
    },
    clearForm () {
      this.params.p_ma_tt = null
      this.params.p_ten_tt = null
      this.params.p_diachi_tt = null
    },
    clearResult () {
      this.selectedRow = null
      this.dsPhieuHuy = []
      this.pageinfo.totalElement = 0
      this.pageinfo.page = 0
    },

    async getPayment_Info_ForPay () {
      try {
        const results = await GachNoAPI.getPayment_Info_ForPay(this.axios, {pKyCuoc: this.params.p_kycuoc, pPaymentCode: this.params.p_ma_tt,pLoaiTien: this.loai_tien})
        return results
      } catch (err) {
        console.log(err)
      }
    },

    printPhieu () {
      if (this.selectedRow === null) {
        this.$toast.error('Chưa chọn phiếu để in.')
        return false
      }

      let user = this.$auth.getUserName()

      if (this.selectedRow.NGUOIHUY.toUpperCase() !== user.toUpperCase()) {
        this.$toast.error('Bạn không sửa phiếu thanh toán của user ' + this.selectedRow.NGUOIHUY)
        return false
      }

      // this.$toast.error('Chức năng In đang được phát triển. Vui lòng thử lại sau.')
      this.$refs.refPopupInPhieuThu.openDialog()
    },

    /** Event Handler */
    chukycuocChangeHandler (e) {
      if (e.p_chuky_no !== null) { this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no } else { this.params.p_kycuoc = null }
    },
    phieuTTRowChanged (e) {
      if (e === null) {
        this.selectedRow = null
        return
      }
      this.selectedRow = e
      this.params.p_ma_tt = e.MA_TT
      this.params.p_ten_tt = e.TEN_TT
      this.params.p_diachi_tt = e.DIACHI_TT
      /*
        this.getPayment_Info_ForPay()
          .then(
            (res) => {
              if(res.data.error_code === 'BSS-00000000'
                && res.data.data !== undefined
                && res.data.data.length > 0
              )
              {
                this.params.p_ten_tt = res.data.data[0].TEN_TT
                this.params.p_diachi_tt = res.data.data[0].DIACHI_TT
              }
            }
          )
        */
    },
    phieuTTpageChangeHandler (e) {
      // console.log('pageChange')
      this.pageinfo.page = e.pageIndex
      this.pageinfo.maxSize = e.pageSize

      this.searchData.pageNo = this.pageinfo.page + 1
      this.searchData.pageSize = this.pageinfo.maxSize

      // this.doSearch()
      this.fetchDataSearch()
    },
    async acceptPrintPhieuThuHandler (e) {
      let phieuId = this.selectedRow.PHIEU_ID
      if (e.is_print === 1) {

      } else {
        let apiBody = {
          'pKyCuoc': this.params.p_kycuoc,
          'pSeri': e.seri,
          'pQuyen': e.thung,
          'pSoSeri': e.so_seri,
          'pPhieuId': phieuId
        }

        let result = await this.updateSeriPhieuHuy(apiBody)
        if (result) {
          this.$toast.success('Cập nhật phiếu thanh toán thành công!')
          this.fetchDataSearch()
        }
      }
    },
    async updateSeriPhieuHuy (apiBody) {
      this.loading(true)
      try {
        let response = await GachNoAPI.updateSeriPhieuHuy(this.axios, apiBody)
        return response.data.error_code === 'BSS-00000000'
      } catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      } finally {
        this.loading(false)
      }
    },
    onBeforeOpen (args) {
      this.$emit('beforeOpen', args)
    },
    onBeforeClose (args) {
      this.$emit('beforeClose', args)
    }
  },
  watch: {
    ma_tt: {
      handler: function (newValue, oldValue) {
        this.params.p_ma_tt = newValue
      }
    },
    kycuoc: {
      handler: function (newValue, oldValue) {
        this.p_default_kycuoc = newValue
      }
    }
  }
})
</script>
