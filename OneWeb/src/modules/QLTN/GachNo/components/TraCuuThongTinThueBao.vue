<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgTraCuuTTTB"
    :header='"Tra cứu thông tin thuê bao"'
    showCloseIcon=true
    width='80%'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="popup-top-action">
          <div class="pull-left">
              <div class="search item pointer" v-on:click.prevent="doSearch" style="cursor: hand">
                <span class="icon one-search"></span> Tìm kiếm
              </div>
              <div class="acept item pointer" v-on:click.prevent="doAccept">
                  <span class="icon -ap icon-ion-ios-checkmark"></span> Chấp nhận
              </div>
          </div>
          <div class="pull-right w120">
              <app-ky-cuoc :value="p_default_kycuoc" @chukycuocChange="ckcuocChangeHandler"></app-ky-cuoc>
          </div>
          <div class="clearfix"></div>
      </div>
      <div class="popup-body">
          <div class="tc-box-form-tab">
              <ul class="tabs">
                  <li><a href="#" v-on:click.prevent="clickTab(0)" v-bind:class="{ active: params.p_type === 0 }">Khách hàng</a></li>
                  <li><a href="#" v-on:click.prevent="clickTab(1)" v-bind:class="{ active: params.p_type === 1 }">Thanh toán</a></li>
                  <li><a href="#" v-on:click.prevent="clickTab(2)" v-bind:class="{ active: params.p_type === 2 }">Thuê bao</a></li>
              </ul>
              <div class="form-content">
                  <div class="row">
                      <div class="col-md-8 col-xs-12 col-12">
                          <div class="info-row">
                              <div class="key">Mã</div>
                              <div class="value">
                                <input v-model="params.p_ma" autofocus ref="timtb_p_ma_tt" type="text" class="form-control"  @keyup.enter="doSearch">
                              </div>
                          </div>
                      </div>
                      <div class="col-md-4 col-xs-12 col-12">
                          <div class="info-row">
                              <div class="key">Kiểu nợ</div>
                              <div class="value">
                                  <div class="select-custom">
                                    <ejs-dropdownlist :dataSource='params.p_kieuno_options' :fields="params.fieldsKieuNo" v-model = "params.p_kieuno"></ejs-dropdownlist>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="row">
                      <div class="col-md-8 col-xs-12 col-12">
                          <div class="info-row">
                              <div class="key">Tên</div>
                              <div class="value">
                                <input v-model="params.p_ten" type="text" class="form-control" @keyup.enter="doSearch">
                              </div>
                          </div>
                      </div>
                      <div class="col-md-4 col-xs-12 col-12">
                          <div class="info-row">
                              <div class="key">
                                  <div class="">
                                    <b-form-checkbox
                                      v-model = "params.p_chk_tutien"
                                      class = "check"
                                      value = '1'
                                      unchecked-value = '0'
                                    >
                                      <span class="name">Từ tiền</span>
                                    </b-form-checkbox>
                                  </div>
                              </div>
                              <div class="value">
                                <div class="input-icon-right calculator-box">
                                  <ejs-numerictextbox locale="vi-VN" :enabled="chk_tutien" style="padding-right:15px" :showSpinButton="false" class="red form-control tright" v-model="$data.params.p_tutien" format="N0"></ejs-numerictextbox>
                                  <span class="icon one-calculator" @click="config.isTuTienCalShow = !config.isTuTienCalShow"></span>
                                  <app-calculator @display="$data.params.p_tutien = $event" :value="$data.params.p_tutien" :is-show="config.isTuTienCalShow" @close="config.isTuTienCalShow = !config.isTuTienCalShow"></app-calculator>
                                </div>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="row">
                      <div class="col-md-8 col-xs-12 col-12">
                          <div class="info-row">
                              <div class="key">Địa chỉ</div>
                              <div class="value">
                                <input v-model="params.p_diachi" type="text" class="form-control"  @keyup.enter="doSearch">
                              </div>
                          </div>
                      </div>
                      <div class="col-md-4 col-xs-12 col-12">
                          <div class="info-row">
                              <div class="key">
                                  <div class="">
                                    <b-form-checkbox
                                      v-model = "params.p_chk_dentien"
                                      class = "check"
                                      value = '1'
                                      unchecked-value = '0'
                                    >
                                      <span class="name">Đến tiền</span>
                                    </b-form-checkbox>
                                  </div>
                              </div>
                              <div class="value">
                                <div class="input-icon-right calculator-box">
                                  <ejs-numerictextbox locale="vi-VN" :enabled="chk_dentien" style="padding-right:15px" :showSpinButton="false" class="red form-control tright" v-model="$data.params.p_dentien" format="N0"></ejs-numerictextbox>
                                  <span class="icon one-calculator" @click="config.isDenTienCalShow = !config.isDenTienCalShow"></span>
                                  <app-calculator @display="$data.params.p_dentien = $event" :value="$data.params.p_dentien" :is-show="config.isDenTienCalShow" @close="config.isDenTienCalShow = !config.isDenTienCalShow"></app-calculator>
                                </div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
          <div class="box-form">
              <div class="table-content" style="height: 400px">
                <DataGrid
                  v-bind:columns="this.loai_tien==1?[
                    {fieldName: 'MA_TT', visible: false, captionTemplate: this.captionTemplate},
                    {fieldName: 'THANHTOAN', isGroupedColumn: true, headerText: 'Mã TT', captionTemplate: this.captionTemplate},
                    {fieldName: 'MA_TB', headerText: 'Thuê bao', allowFiltering: true, width: 100},
                    {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true},
                    {fieldName: 'DIACHI_TB', headerText: 'Địa chỉ thuê bao', allowFiltering: true},
                    {fieldName: 'DAUKYTB', headerText: 'Nợ đầu kỳ', allowFiltering: true, type: 'number', format:'N0', width: 120},
                    {fieldName: 'PHATSINHTB', headerText: 'Phát sinh', allowFiltering: true, type: 'number', format:'N0', width: 120},
                    {fieldName: 'TONGNOTB', headerText: 'Tổng nợ', allowFiltering: true, type: 'number', format:'N0', width: 120}
                  ]:[
                    {fieldName: 'MA_TT', visible: false, captionTemplate: this.captionTemplate},
                    {fieldName: 'THANHTOAN', isGroupedColumn: true, headerText: 'Mã TT', captionTemplate: this.captionTemplate},
                    {fieldName: 'MA_TB', headerText: 'Thuê bao', allowFiltering: true, width: 100},
                    {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true},
                    {fieldName: 'DIACHI_TB', headerText: 'Địa chỉ thuê bao', allowFiltering: true},
                    {fieldName: 'DAUKYTB', headerText: 'Nợ đầu kỳ', allowFiltering: true, type: 'number', format:'N2', width: 120},
                    {fieldName: 'PHATSINHTB', headerText: 'Phát sinh', allowFiltering: true, type: 'number', format:'N2', width: 120},
                    {fieldName: 'TONGNOTB', headerText: 'Tổng nợ', allowFiltering: true, type: 'number', format:'N2', width: 120}
                  ]"
                  v-bind:dataSource="results"
                  v-bind:aggregates="aggregates"
                  :enablePagingServer="false"
                  :showFilter="true"
                  :allowPaging="false"
                  :allowExcelExport="true"
                  :groupSettings="groupSettings"
                  :key="keyDSThueBao"
                  height="100%"
                  @selectedRowChanged="RowChanged"
                  ref="gridKetQuaTraCuuTB"
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
import KyCuoc2 from '@/components/KyCuoc2'
import GachNoAPI from '../../api/GachNoAPI'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import Calculator from '@/components/Calculator'
import moment from 'moment'
import EventBus from '@/utils/eventBus'

import { cloneArray } from '@/utils/util'

Vue.use(DialogPlugin)

export default Vue.extend({
  props: {
    kycuoc:{ 
      type:String
    },
    loai_tien: {
      type: Number,
      default: 1 // Tiền VNĐ
    }
  },
  components: {
    appKyCuoc: KyCuoc2,
    appCalculator: Calculator
  },
  created () {

  },
  computed: {
    chk_dentien () {
      return this.params.p_chk_dentien === '1'
    },
    chk_tutien () {
      return this.params.p_chk_tutien === '1'
    }
  },
  data () {
    return {
      config: {
        isTuTienCalShow: false,
        isDenTienCalShow: false
      },
      groupSettings: {
        captionTemplate: '<div> TEst</div> ',
        columns: ['MA_TT']
      },
      columns: [

      ],
      selectionOptions: { persistSelection: true, type: 'Single' },

      aggregates:this.loai_tien==1? [
        {field: 'DAUKYTB', type: 'Sum', format: 'N0', groupCaptionTemplate: this.footerSum},
        {field: 'PHATSINHTB', type: 'Sum', format: 'N0', groupCaptionTemplate: this.footerSum},
        {field: 'TONGNOTB', type: 'Sum', format: 'N0', groupCaptionTemplate: this.footerSum}
      ]: [
        {field: 'DAUKYTB', type: 'Sum', format: 'N2', groupCaptionTemplate: this.footerSum},
        {field: 'PHATSINHTB', type: 'Sum', format: 'N2', groupCaptionTemplate: this.footerSum},
        {field: 'TONGNOTB', type: 'Sum', format: 'N2', groupCaptionTemplate: this.footerSum}
      ],
      p_default_kycuoc: null,
      captionTemplate: '<div>${key}</div>',
      params: {
        p_kycuoc: null,
        p_type: 1, // mã TT
        p_kieuno_options: [
          { id: 0, text: 'Nợ đầu kỳ' },
          { id: 1, text: 'Tổng nợ' },
          { id: 5, text: 'Nợ phát sinh' }],
        p_kieuno: 1,
        fieldsKieuNo: { text: 'text', value: 'id' },
        p_chk_dentien: '0',
        p_chk_tutien: '0',
        p_tutien: 0,
        p_dentien: 0,
        p_ma: null,
        p_ten: null,
        p_diachi: null
      },
      results: [],
      pageinfo: {
        page: 0,
        pageSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      keyDSThueBao: 0,
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      currentInfo: {} // lưu thông tin dòng đang nhận focus
    }
  },
  methods: {
    validateData () {
      let errors = []

      if (this.params.p_kycuoc === null) { errors.push('Trường chu kỳ hóa đơn bắt buộc nhập') }
      /*
      if(
        (this.params.p_ten === null || this.params.p_ten.trim() === '')
        && (this.params.p_ma === null || this.params.p_ma.trim() === '')
        && (this.params.p_diachi === null || this.params.p_diachi.trim() === '')
      )
      {
        errors.push('Bắt buộc phải nhập Mã hoặc Tên hoặc địa chỉ')
      }
*/

      if (this.params.p_chk_tutien === '1' &&
        this.params.p_tutien === null) { errors.push('Bắt buộc phải nhập từ tiền') }

      if (this.params.p_chk_dentien === '1' &&
        this.params.p_dentien === null) { errors.push('Bắt buộc phải nhập đến tiền') }

      if (this.params.p_chk_dentien === '0' && this.params.p_chk_tutien === '0' &&
        (this.params.p_ten === null || this.params.p_ten.trim() === '') &&
        (this.params.p_ma === null || this.params.p_ma.trim() === '') &&
        (this.params.p_diachi === null || this.params.p_diachi.trim() === '')
      ) {
        errors.push('Hãy nhập thông tin tìm kiếm')
      }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },
    compare: function (a, b) {
      if (a.MA_TT < b.MA_TT) {
        return -1
      }
      if (a.MA_TT > b.MA_TT) {
        return 1
      }
      return 0
    },
    async searchThueBao (postData) {
      try {
        const res = await GachNoAPI.getThongTinThueBao(this.axios, postData)
        const data = await res.data

        return data.error_code === 'BSS-00000000' &&
            data.data !== undefined &&
            data.data.data !== undefined ? data.data : null
      } catch (error) {
        if (error.data !== undefined) { this.$toast.error(error.data.message_detail) } else { this.$toast.error(error) }
        return null
      } finally {}
    },
    async searchThueBao2 (postData) {
      try {
        const res = await GachNoAPI.getThongTinThueBao2(this.axios, postData)
        const data = await res.data

        return data.error_code === 'BSS-00000000' &&
            data.data !== undefined &&
            data.data.data !== undefined ? data.data : null
      } catch (error) {
        if (error.data !== undefined) { this.$toast.error(error.data.message_detail) } else { this.$toast.error(error) }
        return null
      } finally {}
    },
    async doSearch () {
      if (!this.validateData()) { return false }

      let postData = {
        pKyCuoc: this.params.p_kycuoc,
        pKieuNo: this.params.p_kieuno,
        pMa: this.params.p_ma === null ? null : this.params.p_ma,
        pTen: this.params.p_ten,
        pDiaChi: this.params.p_diachi,
        pKieu: this.params.p_type,
        pTutien: this.params.p_chk_tutien === '1' ? this.params.p_tutien : null,
        pDenTien: this.params.p_chk_dentien === '1' ? this.params.p_dentien : null,
        pLoaiTien:this.loai_tien,
        pageNo: this.pageinfo.page + 1,
        pageSize: 1000, // this.pageinfo.pageSize,
        totalRow: 1
      }

      this.loading(true)
      const res = await this.searchThueBao(postData)
      if (res !== null) {
        let arr = cloneArray(res.data)
        arr.sort(this.compare)

        this.keyDSThueBao++
        this.results = arr
        this.pageinfo.totalElement = res.total_rows
      } else {
        this.results = []
        this.pageinfo.totalElement = 0
        this.pageinfo.page = 0
      }

      this.loading(false)
      /*
      this.loading(true)

      this.results = []
      this.pageinfo.totalElement = 0
      this.pageinfo.page = 0
      //this.clearResult()

      GachNoAPI.getThongTinThueBao(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.data !== undefined
                && response.data.data.data.length > 0
            )
            {
              let arr = [...response.data.data.data]
              arr.sort( this.compare );

              this.keyDSThueBao++
              this.results = arr
              this.pageinfo.totalElement = response.data.data.total_rows
            }
          })
        .catch(error => {
          //console.log(error)
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
*/
    },
    doAccept () {
      if (this.currentInfo.MA_TT === undefined) {
        this.$toast.error('Vui lòng chọn khách hàng cần gạch nợ')
        return
      }
      EventBus.$emit('getTTTB', this.currentInfo)
      this.$refs.dlgTraCuuTTTB.hide()
    },
    clearForm () {
      this.params.p_ma = ''
      this.params.p_ten = ''
      this.params.p_diachi = ''
      this.params.p_kieuno = 1
      this.params.p_tutien = 0
      this.params.p_dentien = 0
      this.params.p_chk_tutien = '0'
      this.params.p_chk_dentien = '0'
    },
    clearResult () {
      this.currentInfo = {}
      this.results = []
      this.pageinfo.page = 0
      this.pageinfo.totalElement = 0
      this.pageinfo.totalPages = 0
    },
    openDialog () {
      this.$refs.dlgTraCuuTTTB.show()
    },
    closeDialog () {
      this.clearForm()
      this.clearResult()
      this.$refs.dlgTraCuuTTTB.hide()
    },
    onDialogClose () {
      this.clearForm()
      this.clearResult()
    },
    onDialogOpen () {
      if (this.kycuoc !== null) {
        this.p_default_kycuoc = this.kycuoc
      }
      this.$refs.timtb_p_ma_tt.focus()
    },
    clickTab (index) {
      this.params.p_type = index
    },
    ckcuocChangeHandler (e) {
      // console.log(e)
      if (e.p_chuky_no !== null) { this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no } else { this.params.p_kycuoc = null }
    },
    RowChanged (e) {
      // console.log(e)
      this.currentInfo = e
    },
    pageChanged (e) {
      // console.log(e)
      this.pageinfo.page = e.pageIndex
      this.pageinfo.pageSize = e.pageSize
      this.doSearch()
    },
    async actionBegin (args) {
      console.log(args)
      if (args.action === 'filter' && args.requestType === 'filtering') {
        // args.currentFilterObject
        // args.currentFilterObject.field
        // args.currentFilterObject.value

        // if(!this.validateData())
        // return false

        let postData = {
          pKyCuoc: this.params.p_kycuoc,
          pKieuNo: this.params.p_kieuno,
          pMa: this.params.p_ma === null ? null : this.params.p_ma,
          pTen: this.params.p_ten,
          pDiaChi: this.params.p_diachi,
          pKieu: this.params.p_type,
          pTutien: this.params.p_chk_tutien === '1' ? this.params.p_tutien : null,
          pDenTien: this.params.p_chk_dentien === '1' ? this.params.p_dentien : null,
          pLoaiTien:this.loai_tien,
          pageNo: this.pageinfo.page + 1,
          pageSize: 1000, // this.pageinfo.pageSize,
          totalRow: 1,
          search:
          {
            'KEY': args.currentFilterObject.field,
            'VALUE': args.currentFilterObject.value
          }
        }

        this.loading(true)
        const res = await this.searchThueBao2(postData)
        if (res !== null) {
          let arr = cloneArray(res.data)
          arr.sort(this.compare)

          this.keyDSThueBao++
          this.results = arr
          this.pageinfo.totalElement = res.total_rows
        } else {
          this.results = []
          this.pageinfo.totalElement = 0
          this.pageinfo.page = 0
        }

        this.loading(false)
        args.cancel = true
      }
    }

  }
})
</script>
<style scoped>
  .tc-box-form-tab
  {
    overflow: unset;
  }
  .e-input-group
  {
    padding-right: 24px!important;
  }
  .e-input-group-icon:last-child
  {
    border-right-width: 1px!important;
  }
</style>
