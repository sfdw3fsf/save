<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgChenNo"
    :header='"Chèn nợ"'
    showCloseIcon=true
    width='80%'
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
                                  <input @keyup.enter="doSearch" ref="frmchenno_ma_tt" type="text" class="form-control highlight" v-model="params.p_ma_tt">
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
                :key="keyDSThueBao"
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
import KyCuoc from '@/components/KyCuoc2'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
// import EventBus from '@/utils/eventBus'
import GachNoAPI from '../../api/GachNoAPI'
import moment from 'moment'

Vue.use(DialogPlugin)

export default Vue.extend({
  components: {
    appKyCuoc: KyCuoc
  },
  props: {
    ma_tt: {
      type: String,
      required: false
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
          data () { return { data: {} } }
        })
        }
      },
      footerCount: function () {
        return { template: Vue.component('countTemplate', {
          template: `<span>{{data.Count}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      params: {
        // p_kycuoc: moment(new Date()).subtract(1,'months').endOf('month').format('YYYYMM') + '01',
        // p_kyhoadon: moment(new Date()).subtract(2,'months').startOf('month').format('YYYYMMDD'),
        p_kycuoc_dc: moment(new Date()).subtract(2, 'months').startOf('month').format('YYYYMMDD'),
        p_ma_tt: this.p_ma_tt,
        p_ten_tt: null
      },
      p_default_kycuoc: moment(new Date()).subtract(2, 'months').startOf('month').format('YYYYMMDD'),
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
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      keyDSThueBao: 0
    }
  },
  methods: {
    async getDSChenNo () {
      let postData = {
        pKyCuoc: this.params.p_kycuoc_dc,
        pMaTT: this.params.p_ma_tt
      }

      this.loading(true)

      this.results = []
      this.pageinfo.totalElement = 0
      this.pageinfo.page = 0

      await GachNoAPI.getDSChenNo(this.axios, postData)
        .then(
          (res) => {
            // onsole.log(res)
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              this.keyDSThueBao++
              this.results = res.data.data
              this.pageinfo.totalElement = res.data.data.length
              this.pageinfo.page = 0
            } else {              
              this.$toast.error('Không tìm thấy dữ liệu.')
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
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
        const results = await GachNoAPI.getPayment_Info_ForPay(this.axios, {pKyCuoc: this.kyhoadon, pPaymentCode: this.params.p_ma_tt})
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (error) {
        return []
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
      this.clearForm()

      this.params.p_ma_tt = this.ma_tt
      // this.params.p_default_kycuoc = this.kyhoadon
      this.params.p_default_kycuoc = moment(this.kyhoadon, 'YYYYMMDD').subtract(1, 'months').startOf('month').format('YYYYMMDD')
      /*
        if(this.kyhoadon !== null){
          this.p_default_kycuoc = this.kyhoadon
          this.params.p_kyhoadon = this.kyhoadon
        }
        */
      if (this.params.p_ma_tt !== null && this.params.p_ma_tt.trim() !== '') {
        this.doSearch()
      }
    },
    clearForm () {
      this.params.p_kycuoc_dc = moment(new Date()).subtract(2, 'months').startOf('month').format('YYYYMMDD')
      this.params.p_ma_tt = this.p_ma_tt
      this.params.p_ten_tt = null
    },
    clearResult () {
      this.results = []
      this.pageinfo.totalElement = 0
      this.pageinfo.page = 0
    },
    async doSearch () {
      if (this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '') {
        this.$toast.error('Hãy nhập mã thanh toán!')
        this.$refs.frmchenno_ma_tt.focus()
        return false
      }
      this.loading(true)
      const res = await this.getPayment_Info_ForPay()

      if (res.length > 1) {
        // show form chọn thanh toán
      } else if (res.length <= 0) {
        const info = await this.getThongTinTTTheoDBHienTai()
        if (info.length <= 0) {
          this.$toast.error('Không tìm thấy thông tin thanh toán')
        } else {
          this.params.p_ten_tt = info[0].TEN_TT
          await this.getDSChenNo()
        }
      } else {
        this.params.p_ten_tt = res[0].TEN_TT
        await this.getDSChenNo()
      }

      this.loading(false)
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
          'pKyCuoc': this.kyhoadon,
          'pKyCuocDC': this.params.p_kycuoc_dc,
          'pDSChenNo': arr
        }

      this.loading(true)

      GachNoAPI.doChenNo(this.axios, postData)
        .then(
          (res) => {
            this.$toast.success('Chèn nợ thành công')
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
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

    async getThongTinTTTheoDBHienTai () {
      try {
        const results = await GachNoAPI.getTTThanhhToanTheoDBHienTai(this.axios, this.params.p_ma_tt)
        const data = await results.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        // console.log(err)
        return []
      }
    },
    chukycuocChangeHandler (e) {
      if (e.p_chuky_no !== null) {
        this.params.p_kycuoc_dc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      }
    },
    onBeforeOpen (args) {
      this.$emit('beforeOpen', args)
    },
    onBeforeClose (args) {
      this.$emit('beforeClose', args)
    }
  },
  provide: {

  },
  watch: {
    kyhoadon: {
      handler: function (newValue, oldValue) {
        this.p_default_kycuoc = moment(newValue, 'YYYYMMDD').subtract(1, 'months').startOf('month').format('YYYYMMDD')
      }
    }
  }
})
</script>
