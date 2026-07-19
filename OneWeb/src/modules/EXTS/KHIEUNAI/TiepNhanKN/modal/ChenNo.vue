<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgChenNo"
    :header='"Chèn nợ"'
    showCloseIcon=true
    width='80%'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
          <ul class="list">
              <li>
                  <a href="javascript: void(0)" @click="doSearch">
                      <span class="icon one-search"></span> Tìm kiếm
                  </a>
              </li>

              <li>
                  <a href="javascript: void(0)" @click="doChenNo">
                      <span class="icon one-excel"></span> Chèn nợ
                  </a>
              </li>
              <li>
                  <a href="javascript: void(0)" @click="doExcelExport">
                      <span class="icon arrows-2_square-download nc-icon-glyph"></span> Xuất File
                  </a>
              </li>

          </ul>
      </div>
      <div class="popup-body">
          <div class="box-form">
              <div class="legend-title mart0">
                  <div class="pull-left mart10">
                      Thông tin thanh toán
                  </div>
                  <div class="pull-right">
                      <span class="note text-main fw6  block">
                          <span class="-ap icon-warning3 text-warning f20 mar10"></span> Tìm tháng nợ của các thuê bao trong các kỳ cước trước
                      </span>
                  </div>
                  <div class="clearfix"></div>
              </div>
              <div class="row">
                  <div class="col-sm-6 col-12">
                      <div class="info-row">
                          <div class="key w60">Mã TT</div>
                          <div class="value">
                              <div class="input-more-button">
                                  <button class="btn">
                                      <span class="-ap icon-more_horiz"></span>
                                  </button>
                                  <input @keyup.enter="doSearch" type="text" class="form-control highlight" v-model="params.p_ma_tt">
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <app-ky-cuoc :value="p_default_kycuoc" @chukycuocChange="chukycuocChangeHandler"></app-ky-cuoc>
                  </div>
              </div>
              <div class="info-row">
                  <div class="key w60">Tên TT</div>
                  <div class="value">
                      <input type="text" class="form-control highlight" v-model="params.p_ten_tt">
                  </div>
              </div>
          </div>
          <div class="box-form">
              <div class="legend-title">Danh sách thuê bao</div>
              <div class="table-content" style="height: 300px">
                <DataGrid
                v-bind:columns="[
                  {fieldName: 'THANGNO', headerText: 'Tháng nợ', allowFiltering: true},
                  {fieldName: 'THUEBAO', headerText: 'Thuê bao', allowFiltering: true},
                  {fieldName: 'NOGOC', headerText: 'Nợ gốc', allowFiltering: true, type:'number', format:'N0'},
                  {fieldName: 'THUE', headerText: 'Thuế', allowFiltering: true, type:'number', format:'N0'},
                  {fieldName: 'HOAHONG', headerText: 'Hoa hồng', allowFiltering: true, type:'number', format:'N0'},
                  ]"
                v-bind:dataSource="results"
                :enablePagingServer="false"
                :showFilter="true"
                :allowPaging="true"
                :totalRecords="pageinfo.totalElement"
                :pageSize="pageinfo.maxSize"
                :pageIndex="pageinfo.page"
                :showColumnCheckbox="true"
                height="100%"
                v-bind:aggregates="sumColumns"
                :allowSelection="true"
                :allowExcelExport="true"
                ref="dsThueBaoChenNo"
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
import KyCuoc from './KyCuoc2'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import GachNoAPI from '../api/GachNoAPI'
import moment from 'moment'

Vue.use(DialogPlugin)

export default Vue.extend({
  components: {
    appKyCuoc: KyCuoc
  },
  props: {
    ma_tt: {
      type: String
    },
    kyhoadon: {
      type: String,
      required: false
    }
  },
  computed () {
  },
  async created () {
    // EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
  },
  destroyed () {
    // EventBus.$off('chukycuocChange', this.chukycuocChangeHandler);
  },
  data () {
    return {
      sumColumns: [
        {field: 'THANGNO', type: 'Count', format: 'N0', footerTemplate: this.footerCount},
        {field: 'NOGOC', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'THUE', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'HOAHONG', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
      ],
      footerSum: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<span>{{data.Sum}}</span>`,
          data () { return { data: {}} }
        })
        }
      },
      footerCount: function () {
        return { template: Vue.component('countTemplate', {
          template: `<span>{{data.Count}}</span>`,
          data () { return { data: {}} }
        })
        }
      },
      params: {
        // p_kycuoc: moment(new Date()).subtract(1,'months').endOf('month').format('YYYYMM') + '01',
        p_kyhoadon: this.kyhoadon,
        p_kycuoc_dc: moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM') + '01',
        p_ma_tt: this.p_ma_tt,
        p_ten_tt: null
      },
      p_default_kycuoc: null,
      results: [],
      pageinfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      totalItems: 0,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  methods: {
    async getDSChenNo () {
      try {
        let postData = {
          pKyCuoc: this.params.p_kyhoadon,
          pMaTT: this.params.p_ma_tt
        }

        let response = await this.$root.context.get('/web-thuno/api/thu-no/common/chu-ky-no/ky-cuoc/', { params: postData })
        this.results = response.data
        this.pageinfo.totalElement = response.data.length
        this.pageinfo.page = 0
        console.log('this.results', this.results)
        // await GachNoAPI.getDSChenNo(this.axios, postData)
        //   .then(
        //     (res) => {
        //       // onsole.log(res)
        //       if (res.data.error_code === 'BSS-00000000' &&
        //         res.data.data !== undefined &&
        //         res.data.data.length > 0) {
        //         this.results = res.data.data
        //         // this.totalItems = res.data.data.length
        //         this.pageinfo.totalElement = res.data.data.length
        //         this.pageinfo.page = 0
        //       }
        //     }
        //  )
      } catch (err) {
        console.log(err)
      }
    },
    async getTongNoTheoKyHoaDon () {
      try {
        let postData = {
          pKyCuoc: this.params.p_kyhoadon,
          pMaTT: this.ma_tt
        }
        const results = await GachNoAPI.getTongNoTheoKyHoaDon(this.axios, postData)
        return results
      } catch (err) {
        console.log(err)
      }
    },
    async getPayment_Info_ForPay () {
      try {
        let data = { pPhanVungId: this.$auth.getPhanVungID(), pKyCuoc: this.params.p_kyhoadon, pPaymentCode: this.params.p_ma_tt }
        console.log('getPayment_Info_ForPay', data)
        let response = await this.$root.context.get('/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-thong-tin-thanh-toan-de-thanh-toan', data)
        // const results = await GachNoAPI.getPayment_Info_ForPay(this.axios, {pPhanVungId: this.$auth.getPhanVungID(), pKyCuoc: this.params.p_kyhoadon, pPaymentCode: this.params.p_ma_tt})
        console.log('getPayment_Info_ForPay', response)
        return response.data
      } catch (err) {
        console.log('getPayment_Info_ForPay', err)
      }
    },
    openDialog () {
      this.$refs.dlgChenNo.show()
    },
    closeDialog () {
      this.$refs.dlgChenNo.hide()
    },
    onDialogClose () {
      this.clearForm()
      this.clearResult()
    },
    onDialogOpen () {
      this.params.p_ma_tt = this.ma_tt
      if (this.kyhoadon !== null) {
        this.p_default_kycuoc = this.kyhoadon
        this.params.p_kyhoadon = this.kyhoadon
      }
      if (this.params.p_ma_tt !== null && this.params.p_ma_tt.trim() !== '') {
        this.doSearch()
      }
    },
    clearForm () {

    },
    clearResult () {

    },
    doSearch: function () {
      if (this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '') {
        this.$toast.error('Hãy nhập mã thanh toán!')
        return false
      }
      this.getPayment_Info_ForPay().then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
            if (response.data.data.length > 1) {

            } else if (response.data.data.length <= 0) {
              this.getThongTinTTTheoDBHienTai().then(
                (res) => {
                  if (res.data.error_code === 'BSS-00000000' &&
                      res.data.data !== undefined) {
                    if (res.data.data.length <= 0) {
                      this.$toast.error('Không tìm thấy thông tin thanh toán')
                      return false
                    } else {
                      this.params.p_ten_tt = res.data.data[0].TEN_TT
                      this.getDSChenNo()
                    }
                  }
                }
              )
            } else {
              this.params.p_ten_tt = response.data.data[0].TEN_TT
              this.getDSChenNo()
            }
          } else { this.$toast.error(response.data.message) }
        }
      ).then(
        () => {
          // this.$refs.refCacKhoanNo.openDialog()
        }
      ).finally(() => {
        this.loading(false)
      })
    },

    doChenNo: function () {
      if (this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '') {
        this.$toast.error('Hãy nhập mã thanh toán!')
        return false
      }

      if (this.$refs.dsThueBaoChenNo.getSelectedRecords().length <= 0) {
        this.$toast.error('Bạn chưa chọn thuê bao trên lưới để cập nhật!')
        return false
      }
      let arr = []

      this.$refs.dsThueBaoChenNo.getSelectedRecords().forEach((item, index) => {
        arr.push({MA_TT: item.MA_TT, MA_TB: item.THUEBAO, DICHVUVT_ID: item.DICHVUVT_ID, CHUKYNO: item.CHUKYNO})
      })

      let postData =
        {
          'pKyCuoc': this.params.p_kyhoadon,
          'pKyCuocDC': this.params.p_kycuoc_dc,
          'pDSChenNo': arr
        }

      this.loading(true)
      try {
        GachNoAPI.doChenNo(this.axios, postData)
          .then(
            (res) => {
              this.$toast.success('Chèn nợ thành công')
            }
          )
          .finally(() => { this.loading(false) })
      } catch (err) {
        console.log(err)
        this.loading(false)
      }
    },

    doExcelExport: function () {
      if (this.results.length <= 0) {
        this.$toast.error('Không có dữ liệu để export')
        return false
      }
      let excelExportProperties = {
        dataSource: this.results
      }
      this.$refs.dsThueBaoChenNo.excelExport(excelExportProperties)
    },

    doAccept: function () {
      this.$refs.dlgChenNo.hide()
    },
    chukycuocChangeHandler (e) {
      if (e.p_chuky_no !== null) {
        this.params.p_kycuoc_dc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      }
    }
  },
  provide: {

  },
  watch: {

  }
})
</script>
