<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :isModal="true"
    :animationSettings='animationSettings'
    ref="dlgTraCuuDSThanhToan"
    :header='"Tra cứu danh sách thanh toán"'
    showCloseIcon=true
    width='80%'
    height="750"
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <ChonThanhToan ref="refChonThanhToan" :ds_tt="ds_thanhtoan" @acceptChonMaThanhToan="acceptChonMaThanhToan"></ChonThanhToan>
      <div class="list-actions-top">
        <ul class="list">
          <li v-show="tab_index===0">
              <a href="#" v-on:click.prevent="doSearch()">
                  <span class="icon one-search"></span>Tìm kiếm
              </a>
          </li>
          <li v-show="tab_index===0">
              <a href="#" v-on:click.prevent="doAccept()">
                  <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
              </a>
          </li>
          <li v-show="tab_index===1">
              <a href="#"  v-on:click.prevent="doUpdate()">
                  <span class="icon one-reload1"></span>Cập nhật
              </a>
          </li>
          <li v-show="tab_index===1">
              <a href="#" v-on:click.prevent="doCancel()">
                  <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Huỷ bỏ
              </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        
          <div class="nav tabs tab-over">
            <a href="#tabTimKiem" @click="tab_index=0" data-toggle="tab" class="active">Tìm kiếm thông tin</a>
            <a href="#tabSoSungKhoanMuc" v-if="this.nhom_httt_id === 7" @click="tab_index=1" data-toggle="tab">Bổ sung khoản mục</a>
          </div>
          <div class="tab-content">
            <div id="tabTimKiem" class="tab-pane active">
              <form  @submit.prevent="doSearch">
              <div class="legend-title">
                  <span class="icon fa fa-angle-up"></span> Tiêu chí tìm kiếm
              </div>
              <div class="info-row">
                  <div class="key w60">Tiêu chí</div>
                  <div class="value dropdown">
                      <div class="select-custom">
                          <button type="button" class="btn form-control tleft" @click="is_show = true">Tiêu chí: {{lblTieuChi}}</button>
                      </div>
                      <div class="fullw pad10 vattu_table" v-show="is_show" >
                          <div class="legend-title">Điều kiện lọc</div>
                          <div class="grid-stack-box">
                            <Split style="height:230px;">
                              <SplitArea :size="50" :minSize="100">
                                <div class="box-col" id="boxLeft">
                                    <div class="info-row">
                                        <div class="key w120">
                                            <b-form-checkbox
                                              v-model = "params.p_chk_opt0"
                                              class = "check"
                                              value = '1'
                                              unchecked-value = '0'
                                            >
                                              <span class="name">Tra cứu theo</span>
                                            </b-form-checkbox>
                                        </div>
                                        <div class="value">
                                            <div class="row">
                                                <div class="col-sm-7 col-12">
                                                  <ejs-dropdownlist :enabled="chk_opt0" :allowFiltering="false" :dataSource='params.p_options' :fields="configs.fieldsOpts" v-model = "params.p_opt0"></ejs-dropdownlist>
                                                </div>
                                                <div class="col-sm-5 col-12">
                                                    <input type="text" class="form-control" v-model="params.p_tieuchi0">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="info-row">
                                        <div class="key w120">
                                          <b-form-checkbox
                                            v-model = "params.p_chk_opt1"
                                            class = "check"
                                            value = '1'
                                            unchecked-value = '0'
                                          >
                                            <span class="name">Tra cứu theo</span>
                                          </b-form-checkbox>
                                        </div>
                                        <div class="value">
                                            <div class="row">
                                                <div class="col-sm-7 col-12">
                                                  <ejs-dropdownlist :enabled="chk_opt1" :allowFiltering="false" :dataSource='params.p_options' :fields="configs.fieldsOpts" v-model = "params.p_opt1"></ejs-dropdownlist>
                                                </div>
                                                <div class="col-sm-5 col-12">
                                                    <input type="text" class="form-control" v-model="params.p_tieuchi1">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">
                                            <b-form-checkbox
                                              v-model = "params.p_chk_opt2"
                                              class = "check"
                                              value = '1'
                                              unchecked-value = '0'
                                            >
                                              <span class="name">Tra cứu theo</span>
                                            </b-form-checkbox>
                                        </div>
                                        <div class="value">
                                            <div class="row">
                                                <div class="col-sm-7 col-12">
                                                  <ejs-dropdownlist :enabled="chk_opt2" :allowFiltering="false" :dataSource='params.p_options' :fields="configs.fieldsOpts" v-model = "params.p_opt2"></ejs-dropdownlist>
                                                </div>
                                                <div class="col-sm-5 col-12">
                                                    <input type="text" class="form-control" v-model="params.p_tieuchi2">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">
                                          <b-form-checkbox
                                            v-model = "params.p_chk_opt3"
                                            class = "check"
                                            value = '1'
                                            unchecked-value = '0'
                                          >
                                            <span class="name">Tra cứu theo</span>
                                          </b-form-checkbox>
                                        </div>
                                        <div class="value">
                                            <div class="row">
                                                <div class="col-sm-7 col-12">
                                                  <ejs-dropdownlist :enabled="chk_opt3" :allowFiltering="false" :dataSource='params.p_options' :fields="configs.fieldsOpts" v-model = "params.p_opt3"></ejs-dropdownlist>
                                                </div>
                                                <div class="col-sm-5 col-12">
                                                    <input type="text" class="form-control" v-model="params.p_tieuchi3">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">
                                          <b-form-checkbox
                                            v-model = "params.p_chk_thangno"
                                            class = "check"
                                            value = '1'
                                            unchecked-value = '0'
                                          >
                                            <span class="name">Hiện tháng nợ</span>
                                          </b-form-checkbox>
                                        </div>
                                        <div class="value">
                                          <ejs-multiselect
                                            :dataSource='params.p_thangno_options'
                                            :fields='configs.thang_tt.fields'
                                            v-model="params.p_thangno"
                                            :enabled="chk_thangno"
                                            :mode="configs.thang_tt.mode"
                                            :allowFiltering="false"
                                            :showSelectAll="configs.thang_tt.showSelectAll"
                                            :showDropDownIcon="configs.thang_tt.showDropDownIcon"
                                            :filterBarPlaceholder="configs.thang_tt.filterBarPlaceholder"
                                          ></ejs-multiselect>
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">
                                          <b-form-checkbox
                                            v-model = "params.p_chk_nhom"
                                            class = "check"
                                            value = '1'
                                            unchecked-value = '0'
                                          >
                                            <span class="name">Tìm tên nhóm</span>
                                          </b-form-checkbox>
                                        </div>
                                        <div class="value">
                                            <div class="row">
                                                <div class="col-sm-7 col-12">
                                                  <input type="text" :disabled="!chk_nhom" class="form-control" v-model="params.p_nhom" @keyup.enter="timkiem_TenNhom">
                                                </div>
                                                <div class="col-sm-5 col-12">
                                                  <button type="button" @click="doSearch()" class="btn btn-secondary btn-md">Tìm kiếm</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                              </SplitArea>
                              <SplitArea :size="49" :minSize="100">
                                <div class="box-col" id="boxRight">
                                  <div class="info-row">
                                      <div class="key w100">
                                          Kiểu nợ
                                      </div>
                                      <div class="value">
                                        <ejs-dropdownlist :allowFiltering="false" :dataSource='params.p_kieuno_options' :fields="configs.fieldsKieuNo" v-model = "params.p_kieuno"></ejs-dropdownlist>
                                      </div>
                                  </div>
                                  <div class="info-row">
                                      <div class="key w100">
                                        <b-form-checkbox
                                          v-model = "params.p_chk_tutien"
                                          class = "check"
                                          value = '1'
                                          unchecked-value = '0'
                                        >
                                          <span class="name">Từ tiền</span>
                                        </b-form-checkbox>
                                      </div>
                                      <div class="value">
                                        <div class="input-icon-right calculator-box">
                                          <ejs-numerictextbox locale="vi-VN" :enabled="chk_tutien" style="padding-right:15px" :showSpinButton="false" class="red form-control tright" v-model="$data.params.p_tutien" format="N0"></ejs-numerictextbox>
                                          <span class="icon one-calculator" @click="isTuTienCalShow = !isTuTienCalShow"></span>
                                          <app-calculator @display="$data.params.p_tutien = $event" :value="$data.params.p_tutien" :is-show="isTuTienCalShow" @close="isTuTienCalShow = !isTuTienCalShow"></app-calculator>
                                        </div>
                                      </div>
                                  </div>
                                  <div class="info-row">
                                      <div class="key w100">
                                        <b-form-checkbox
                                          v-model = "params.p_chk_dentien"
                                          class = "check"
                                          value = '1'
                                          unchecked-value = '0'
                                        >
                                          <span class="name">Đến tiền</span>
                                        </b-form-checkbox>
                                      </div>
                                      <div class="value">
                                        <div class="input-icon-right calculator-box">
                                          <ejs-numerictextbox locale="vi-VN" :enabled="chk_dentien" style="padding-right:15px" :showSpinButton="false" class="red form-control tright" v-model="$data.params.p_dentien" format="N0"></ejs-numerictextbox>
                                          <span class="icon one-calculator" @click="isDenTienCalShow = !isDenTienCalShow"></span>
                                          <app-calculator @display="$data.params.p_dentien = $event" :value="$data.params.p_dentien" :is-show="isDenTienCalShow" @close="isDenTienCalShow = !isDenTienCalShow"></app-calculator>
                                        </div>
                                      </div>
                                  </div>
                                  <div class="info-row">
                                      <div class="key w100">
                                        <b-form-checkbox
                                            v-model = "params.p_chk_chungtu"
                                            class = "check"
                                            value = '1'
                                            unchecked-value = '0'
                                          >
                                            <span class="name">Chứng từ</span>
                                          </b-form-checkbox>
                                      </div>
                                      <div class="value">
                                          <input :disabled="!chk_chungtu" type="text" class="form-control" v-model="params.p_chungtu">
                                      </div>
                                  </div>
                                  <div class="info-row">
                                      <div class="key w100">
                                          Kỳ HĐ
                                      </div>
                                      <div class="value">
                                        <ejs-datepicker
                                          v-model="p_ky_hd"
                                          :max="configs.max_kyhoadon"
                                          :start="'Year'"
                                          :depth="'Year'"
                                          :format="'MM/y'"
                                          :width="'100%'"
                                        >
                                        </ejs-datepicker>
                                      </div>
                                  </div>
                                  <div class="info-row" v-show="f_show_khoanmuc">
                                      <div class="key w100">
                                          <b-form-checkbox
                                            v-model = "params.p_chk_khoanmuc"
                                            class = "check"
                                            value = '1'
                                            unchecked-value = '0'
                                            @change="chkKhoanMucChange"
                                          >
                                            <span class="name">Khoản mục</span>
                                          </b-form-checkbox>
                                      </div>
                                      <div class="value">
                                        <ejs-multiselect
                                            :dataSource='params.p_khoanmuc_options'
                                            :fields='configs.khoanmuc.fields'
                                            v-model="params.p_khoanmuc"
                                            :enabled="chk_khoanmuc"
                                            :mode="configs.khoanmuc.mode"
                                            :showSelectAll="configs.khoanmuc.showSelectAll"
                                            :showDropDownIcon="configs.khoanmuc.showDropDownIcon"
                                            :filterBarPlaceholder="configs.khoanmuc.filterBarPlaceholder"
                                            :allowFiltering="configs.khoanmuc.allowFiltering"
                                            :filtering="onKhoanMucFiltering"
                                          ></ejs-multiselect>
                                      </div>
                                  </div>
                              </div>
                              </SplitArea>
                            </Split>
                          </div>
                          <div class="p-2">
                            <button type="button" @click="is_show = false" class="btn btn-secondary btn-sm" aria-label="Close">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                      </div>
                  </div>
              </div>
              <div class="legend-title mart10">Kết quả tìm kiếm</div>
              <div class="table-content" style="height: 420px">
                <DataGrid
                  v-bind:columns="columnsDSThanhToan"
                  :showColumnCheckbox="true"
                  ref="gridDSThanhToan"
                  v-bind:dataSource="dsDSThanhToan"
                  v-bind:aggregates="[
                    {field: 'MA_TT', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.aggCount},
                    {field: 'TONGNO', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.aggTONGNO},
                    {field: 'TAMTHU', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.aggTAMTHU},
                    {field: 'NOPS', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.aggNOPS},
                    {field: 'NODK', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.aggNODK}
                    ]"
                  :allowExcelExport="true"
                  :showFilter="true"
                  :allowPaging="false"
                  :enabledSelectFirstRow="false"
                  :enablePagingServer="false"
                  :totalRecords="pageinfoDSThanhToan.totalElement"
                  :pageSize="pageinfoDSThanhToan.maxSize"
                  :pageIndex="pageinfoDSThanhToan.page"
                  :panelDataHeight='"250"'
                  :key="keyDSThanhToan"
                  @rowSelected="dsPhieuSelected" @rowDeselected="dsPhieuDeselected"
                  >
                </DataGrid>
              </div>
              </form>
            </div>
            <div id="tabSoSungKhoanMuc" class="tab-pane">
              <div class="legend-title">Thông tin khoản mục</div>
              <div class="row">
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                          <div class="key w50">Mã TT</div>
                          <div class="value">
                              <div class="input-icon-right">
                                  <input type="text" class="form-control highlight" @keyup.enter="timkiem_KMTT" v-model="params.p_ma_tt">
                                  <span class="icon one-search"></span>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                          <div class="key w90">Số máy/ACC</div>
                          <div class="value">
                            <ejs-dropdownlist :change="onChangeSmAcc" :allowFiltering="false" :dataSource='params.p_sm_acc_options' :fields="configs.fieldsSM_ACC" v-model = "params.p_sm_acc"></ejs-dropdownlist>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                          <div class="key w70">Tháng nợ</div>
                          <div class="value">
                            <ejs-datepicker
                              v-model="p_thang"
                              :max="configs.max_kyhoadon"
                              :start="'Year'"
                              :depth="'Year'"
                              :format="'MM/y'"
                              :width="'100%'"
                            >
                            </ejs-datepicker>
                          </div>
                      </div>
                  </div>
                  <div class="col-sm-3 col-12">
                      <div class="info-row">
                          <div class="key w80">Khoản mục</div>
                          <div class="value">
                            <ejs-dropdownlist :allowFiltering="true" :dataSource='params.p_khoanmuc_bosung_opts' :fields="configs.fieldsKhoanMuc" v-model = "params.p_khoanmuc_bosung"></ejs-dropdownlist>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="legend-title mart20">Danh sách khoản mục</div>
              <div class="table-content">
                  <DataGrid
                  v-bind:columns="[
                    //{fieldName: 'CHUKYNO', isGroupedColumn: true, headerText: 'Chu kỳ nợ'},
                    {fieldName: 'CHUKYNO_HIENTHI', isGroupedColumn: true, headerText: 'Chu kỳ nợ'},
                    {fieldName: 'KHOANMUCTT_ID', visible: false},
                    {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, width: 100},
                    {fieldName: 'KHOANMUC_TT', headerText: 'Khoản mục', allowFiltering: true}
                  ]"
                  v-bind:dataSource="dsKMTT"
                  :sortSettings='sortSettings'
                  :enablePagingServer="false"
                  :showFilter="true"
                  :allowPaging="false"
                  :panelDataHeight='"300"'
                  ref="gridDSKMTT"
                  >
                </DataGrid>
              </div>
            </div>

          </div>
      </div>
  </div>
  </ejs-dialog>
</template>
<script>

import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import GachNoAPI from '../../api/GachNoAPI'
import CommonsAPI from '../../api/Commons'
import { Query } from '@syncfusion/ej2-data'
import moment from 'moment'
import ChonThanhToan from '../../GachNo/components/ChonThanhToan'
import Calculator from '@/components/Calculator'
import { MultiSelectPlugin, CheckBoxSelection } from '@syncfusion/ej2-vue-dropdowns'
import { CheckBoxPlugin } from '@syncfusion/ej2-vue-buttons'
import { DialogUtility } from '@syncfusion/ej2-popups'
import { currency } from '@/filters/currency'

import {
  NHOM_HTTT
} from '@/const/enums'
Vue.use(MultiSelectPlugin)
Vue.use(CheckBoxPlugin)
Vue.use(DialogUtility)
let DialogObj

Vue.use(DialogPlugin)

export default Vue.extend({
  components: {
    appCalculator: Calculator,
    ChonThanhToan
  },
  props: {
    nhom_httt_id: {
      type: Number,
      required: true
    },
    loai_tien: {
      type: Number,
      default: 1 // Tiền VNĐ
    }
  },
  computed: {
    lblTieuChi () {
      let label = []
      if (this.params.p_chk_opt0 === '1' && this.params.p_opt0 !== null) {
        let val = this.getNoiDungTieuChi(this.params.p_opt0)
        if (val !== null) { label.push(val.KIEU_TK) }
      }

      if (this.params.p_chk_opt1 === '1' && this.params.p_opt1 !== null) {
        let val = this.getNoiDungTieuChi(this.params.p_opt1)
        if (val !== null) { label.push(val.KIEU_TK) }
      }

      if (this.params.p_chk_opt2 === '1' && this.params.p_opt2 !== null) {
        let val = this.getNoiDungTieuChi(this.params.p_opt2)
        if (val !== null) { label.push(val.KIEU_TK) }
      }

      if (this.params.p_chk_opt3 === '1' && this.params.p_opt3 !== null) {
        let val = this.getNoiDungTieuChi(this.params.p_opt3)
        if (val !== null) { label.push(val.KIEU_TK) }
      }

      if (this.params.p_chk_thangno === '1') { label.push('Tìm tháng') }
      if (this.params.p_chk_khoanmuc === '1') { label.push('Tìm khoản mục') }
      if (this.params.p_chk_tutien === '1' && this.params.p_chk_dentien === '1') {
        label.push('Từ tiền')
        label.push('Đến tiền')
      }

      if (this.params.p_chk_nhom === '1') { label.push('Tìm tên nhóm') }

      if (this.params.p_chk_chungtu === '1') { label.push('Tìm chứng từ') }

      if (label.length <= 0) { return 'chưa chọn' } else { return label.join(',') }
    },
    chk_opt0 () {
      return this.params.p_chk_opt0 === '1'
    },
    chk_opt1 () {
      return this.params.p_chk_opt1 === '1'
    },
    chk_opt2 () {
      return this.params.p_chk_opt2 === '1'
    },
    chk_opt3 () {
      return this.params.p_chk_opt3 === '1'
    },
    chk_thangno () {
      return this.params.p_chk_thangno === '1'
    },
    chk_khoanmuc () {
      return this.params.p_chk_khoanmuc === '1'
    },
    chk_tutien () {
      return this.params.p_chk_tutien === '1'
    },
    chk_dentien () {
      return this.params.p_chk_dentien === '1'
    },
    chk_chungtu () {
      return this.params.p_chk_chungtu === '1'
    },
    chk_nhom () {
      return this.params.p_chk_nhom === '1'
    },
    p_ky_hd: {
      get () {
        return this.params.p_ky_hd
      },
      set (value) {
        this.params.p_ky_hd = value
      }
    },
    p_thang: {
      get () {
        return this.params.p_thang
      },
      set (value) {
        this.params.p_thang = value
      }
    }
  },
  async created () {

  },
  data () {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' },
      sortSettings: { columns: [{ field: 'CHUKYNO', direction: 'Descending' }] },
      footerSum: function () {
        return {
          template: Vue.component('sumTemplate', {
            template: `<span>{{data.Sum}}</span>`,
            data: function () { return {data: {data: {}}} }
          })
        }
      },
      customTemplate: function () {
        return {
          template: Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
            data () { return { data: { data: {} } } }
          })
        }
      },
      columnsDSThanhToan: [
        {fieldName: 'MA_TT', headerText: 'Mã thanh toán', textAlign: 'Left', allowFiltering: true, width: 130},
        {fieldName: 'MATB_DD', headerText: 'Mã đại diện', textAlign: 'Left', allowFiltering: true, width: 130},
        {fieldName: 'TEN_TT', headerText: 'Tên thanh toán', textAlign: 'Left', allowFiltering: true, width: 200},
        {fieldName: 'THANGTRA', headerText: 'Tháng nợ', textAlign: 'Center', allowFiltering: true, width: 120},
        {fieldName: 'TEN_KM', headerText: 'Khoản mục', textAlign: 'Left', allowFiltering: true, width: 150},
        {fieldName: 'TONGNO', headerText: 'Tiền nợ', textAlign: 'Right', allowFiltering: true, width: 100, type: 'number', format: 'N0'},
        {fieldName: 'TAMTHU', headerText: 'Tạm thu', textAlign: 'Right', allowFiltering: true, format: 'N0', type: 'number'},
        {fieldName: 'NOPS', headerText: 'Tiền phát sinh', textAlign: 'Right', allowFiltering: true, width: 100, type: 'number', format: 'N0'},
        {fieldName: 'NODK', headerText: 'Tiền đầu kỳ', textAlign: 'Right', allowFiltering: true, format: 'N0', type: 'number'},        
        {fieldName: 'HINHTHUC_TT', headerText: 'Hình thức TT', textAlign: 'Left', allowFiltering: true, width: 150},
        {fieldName: 'KHOANMUCTT_ID', headerText: '', visible: false},        
        {fieldName: 'DIACHI_TT', headerText: 'Địa chỉ thanh toán', textAlign: 'Left', allowFiltering: true, width: 150},
        {fieldName: 'STK', headerText: 'Số tài khoản', textAlign: 'Left', allowFiltering: true, width: 100},
        {fieldName: 'MA_TUYEN', headerText: 'Tuyến thu', textAlign: 'Left', allowFiltering: true, width: 120}
      ],
      responseData: {
        ds_chon: [],
        dschonthang: false,
        dsthang: '',
        dschonkm: '',
        dskmtt: ''
      },
      dsDSThanhToan: [],
      keyDSThanhToan: 0,
      pageinfoDSThanhToan: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },

      configs: {
        kycuoc_hienhanh: null,
        thang_tt: {
          fields: { text: 'text', value: 'id' },
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh'
        },
        khoanmuc: {
          fields: { text: 'TEN_KM', value: 'KHOANMUCTT_ID' },
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh',
          allowFiltering: true
        },
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        fieldsOpts: { text: 'KIEU_TK', value: 'KIEUTK_ID' },
        fieldsKhoanMuc: { text: 'TEN_KM', value: 'KHOANMUCTT_ID' },
        fieldsThangNo: { text: 'KIEU_TK', value: 'KIEUTK_ID' },
        fieldsKieuNo: { text: 'text', value: 'id' },
        fieldsSM_ACC: { text: 'MA_TB', value: 'MA_TB' },
        max_kyhoadon: moment(new Date()).subtract(1, 'months').endOf('month').toDate()
      },
      params: {
        p_options: [],
        p_opt0: null,
        p_opt1: null,
        p_opt2: null,
        p_opt3: null,
        p_thangno: [],
        p_thangno_options: [],
        p_kieuno_options: [{ id: 0, text: 'Tổng nợ' }, { id: 1, text: 'Phát sinh' }, { id: 2, text: 'Đầu kỳ' }],
        p_kieuno: 0,
        p_khoanmuc_options: [],
        p_khoanmuc: [],
        p_chk_opt0: '1',
        p_chk_opt1: '1',
        p_chk_opt2: '1',
        p_chk_opt3: '1',
        p_chk_thangno: '1',
        p_chk_khoanmuc: '0',
        p_chk_tutien: '0',
        p_chk_dentien: '0',
        p_tieuchi0: null,
        p_tieuchi1: null,
        p_tieuchi2: null,
        p_tieuchi3: null,
        p_tutien: 0,
        p_dentien: 0,
        p_chungtu: null,
        p_chk_chungtu: '0',
        p_chk_nhom: '0',
        p_nhom: null,
        p_ky_hd: moment(new Date()).subtract(1, 'months').endOf('month').toDate(),

        p_ma_tt: null,
        p_sm_acc_options: [],
        p_sm_acc: null,
        p_thang: moment(new Date()).subtract(1, 'months').endOf('month').toDate(),
        p_khoanmuc_bosung: null,
        p_nhomck_id: -1
      },
      is_show: false,
      isTuTienCalShow: false,
      isDenTienCalShow: false,
      f_show_khoanmuc: false,
      tab_index: 0,
      dsKMTT: [],
      ds_thanhtoan: []
    }
  },
  methods: {
    aggCount () {
      const selected = this.$refs.gridDSThanhToan.$refs.grid.getSelectedRecords()
      return currency(selected.length) + '/' + currency(this.dsDSThanhToan.length)
    },
    aggTONGNO () {
      const selected = this.$refs.gridDSThanhToan.$refs.grid.getSelectedRecords().reduce((acc, curr) => {
        return acc = Number(curr.TONGNO) + acc
      }, 0)
      const total = this.dsDSThanhToan.reduce((acc, curr) => {
        return acc = Number(curr.TONGNO) + acc
      }, 0)
      return currency(selected) + '/' + currency(total)
    },
    aggTAMTHU () {
      const selected = this.$refs.gridDSThanhToan.$refs.grid.getSelectedRecords().reduce((acc, curr) => {
        return acc = Number(curr.TAMTHU) + acc
      }, 0)
      const total = this.dsDSThanhToan.reduce((acc, curr) => {
        return acc = Number(curr.TAMTHU) + acc
      }, 0)
      return currency(selected) + '/' + currency(total)
    },
    aggNOPS () {
      const selected = this.$refs.gridDSThanhToan.$refs.grid.getSelectedRecords().reduce((acc, curr) => {
        return acc = Number(curr.NOPS) + acc
      }, 0)
      const total = this.dsDSThanhToan.reduce((acc, curr) => {
        return acc = Number(curr.NOPS) + acc
      }, 0)
      return currency(selected) + '/' + currency(total)
    },
    aggNODK () {
      const selected = this.$refs.gridDSThanhToan.$refs.grid.getSelectedRecords().reduce((acc, curr) => {
        return acc = Number(curr.NODK) + acc
      }, 0)
      const total = this.dsDSThanhToan.reduce((acc, curr) => {
        return acc = Number(curr.NODK) + acc
      }, 0)
      return currency(selected) + '/' + currency(total)
    },
    openDialog () {
      this.$refs.dlgTraCuuDSThanhToan.show()
    },
    closeDialog () {
      this.$refs.dlgTraCuuDSThanhToan.hide()
    },
    onDialogClose () {
      this.clearForm()
      this.clearResult()
    },
    async onDialogOpen () {
      this.clearForm()
      this.clearResult()
      await this.loadData()
    },
    clearForm () {
      this.params.p_opt0 = null
      this.params.p_opt1 = null
      this.params.p_opt2 = null
      this.params.p_opt3 = null
      this.params.p_thangno = []
      this.params.p_kieuno = 0
      this.params.p_khoanmuc = []
      this.params.p_chk_opt0 = '1'
      this.params.p_chk_opt1 = '1'
      this.params.p_chk_opt2 = '1'
      this.params.p_chk_opt3 = '1'
      this.params.p_chk_thangno = '1'
      this.params.p_chk_khoanmuc = '0'
      this.params.p_chk_tutien = '0'
      this.params.p_chk_dentien = '0'
      this.params.p_tieuchi0 = null
      this.params.p_tieuchi1 = null
      this.params.p_tieuchi2 = null
      this.params.p_tieuchi3 = null
      this.params.p_tutien = 0
      this.params.p_dentien = 0
      this.params.p_chungtu = null
      this.params.p_chk_chungtu = '0'
      this.params.p_chk_nhom = '0'
      this.params.p_nhom = null
      this.params.p_ky_hd = moment(new Date()).subtract(1, 'months').endOf('month').toDate()

      this.params.p_ma_tt = null
      this.params.p_sm_acc_options = []
      this.params.p_sm_acc = null
      this.params.p_thang = moment(new Date()).subtract(1, 'months').endOf('month').toDate()
      this.params.p_khoanmuc_bosung_opts = null
      this.params.p_khoanmuc_bosung = null
      this.params.p_nhomck_id = -1
      this.params.is_show = false
      this.params.isTuTienCalShow = false
      this.params.isDenTienCalSho = false
      this.params.f_show_khoanmuc = false
      this.params.tab_index = 0
    },
    clearResult () {
      this.params.dsKMTT = []
      this.ds_thanhtoan = []
      this.dsDSThanhToan = []
    },
    async loadData () {
      const res = await Promise.all([
        this.getKyCuocHienHanh(),
        this.getDSTieuChiTimKiem(),
        this.loadThangTT(),
        this.getDSKhoanMuc(),
        this.getDSThamSoMacDinh()
      ])
      console.log(res)
      this.configs.kycuoc_hienhanh = res[0][0].KYCUOC
      this.params.p_options = res[1]
      if (res[1].length > 3) {
        this.params.p_opt0 = this.params.p_options[0].KIEUTK_ID
        this.params.p_opt1 = this.params.p_options[1].KIEUTK_ID
        this.params.p_opt2 = this.params.p_options[2].KIEUTK_ID
      }

      this.params.p_khoanmuc_options = []
      this.params.p_khoanmuc_options = [...res[3]]

      if (res[4] !== null) {
        let arr = res[4].split(',')
        if (res[3].length > 0) {
          let ds = res[3].filter(x => arr.includes(x.KHOANMUCTT_ID + ''))
          // this.params.p_khoanmuc_options = [...ds]
          this.params.p_khoanmuc_bosung_opts = [...ds]
        }
      }
      this.f_show_khoanmuc = this.nhom_httt_id === NHOM_HTTT.DIEU_CHINH_NO
    },
    async getDSThamSoMacDinh () {
      try {
        const res = await CommonsAPI.getThamSoMacDinh(this.axios, {pMaThamSo: 'KHOANMUC_TT_CHENNO'})
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data[0].TEN_TS : null
      } catch (error) {
        return null
      }
    },
    loadThangTT () {
      let months = []
      let monthsRequired = 36

      for (let i = 0; i <= monthsRequired; i++) {
        months.push({
          id: moment().add(0 - i - 1, 'months').format('YYYYMM') + '01',
          text: moment().add(0 - i - 1, 'months').format('MM/YYYY')
        })
      }

      this.params.p_thangno_options = months
      return null
    },
    getNoiDungTieuChi (id) {
      if (this.params.p_options === null || this.params.p_options.length === 0) { return null }
      let ret = this.params.p_options.filter(x => x.KIEUTK_ID === id)
      if (ret !== null && ret.length > 0) { return ret[0] } else { return null }
    },
    async getDSKhoanMuc () {
      try {
        const res = await CommonsAPI.getDanhSachKhoanMucGachNo(this.axios)
        const data = await res.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      } catch (error) {
        return []
      }
    },
    validateSearch () {
      let errors = []
      /*
      if (this.params.p_chk_opt0 === '1') {
        if (this.params.p_opt0 === null) { errors.push('Chưa chọn điều kiện tìm kiếm 1.') } else if (this.params.p_tieuchi0 === null || this.params.p_tieuchi0 === '' || this.params.p_tieuchi0.trim() === '') {
          let val = this.getNoiDungTieuChi(this.params.p_opt0)
          if (val !== null) { errors.push('Chưa nhập giá trị tìm kiếm cho ' + val.KIEU_TK + '.') }
        }
      }

      if (this.params.p_chk_opt1 === '1') {
        if (this.params.p_opt1 === null) { errors.push('Chưa chọn điều kiện tìm kiếm 2.') } else if (this.params.p_tieuchi1 === null || this.params.p_tieuchi1 === '' || this.params.p_tieuchi1.trim() === '') {
          let val = this.getNoiDungTieuChi(this.params.p_opt1)
          if (val !== null) { errors.push('Chưa nhập giá trị tìm kiếm cho ' + val.KIEU_TK + '.') }
        }
      }

      if (this.params.p_chk_opt2 === '1') {
        if (this.params.p_opt2 === null) { errors.push('Chưa chọn điều kiện tìm kiếm 3.') } else if (this.params.p_tieuchi2 === null || this.params.p_tieuchi2 === '' || this.params.p_tieuchi2.trim() === '') {
          let val = this.getNoiDungTieuChi(this.params.p_opt2)
          if (val !== null) { errors.push('Chưa nhập giá trị tìm kiếm cho ' + val.KIEU_TK + '.') }
        }
      }

      if (this.params.p_chk_opt3 === '1') {
        if (this.params.p_opt3 === null) { errors.push('Chưa chọn điều kiện tìm kiếm 4.') } else if (this.params.p_tieuchi3 === null || this.params.p_tieuchi3 === '' || this.params.p_tieuchi3.trim() === '') {
          let val = this.getNoiDungTieuChi(this.params.p_opt3)
          if (val !== null) { errors.push('Chưa nhập giá trị tìm kiếm cho ' + val.KIEU_TK + '.') }
        }
      }

      if (this.params.p_chk_thangno === '1' && this.params.p_thangno.length <= 0) { errors.push('Chưa chọn tháng nợ.') }
      */

      // if (this.params.p_chk_khoanmuc === '1' && this.params.p_khoanmuc.length <= 0) { errors.push('Chưa chọn khoản mục.') }

      if (this.params.p_chk_chungtu === '1' && (this.params.p_chungtu === null || this.params.p_chungtu.trim() === '')) { errors.push('Chưa nhập chứng từ.') }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    doSearch () {
      if (!this.validateSearch()) {
        return false
      }

      let arrDieuKien = []
      // let strDieuKien = ''
      // let tieuchi = null

      if (this.params.p_chk_opt0 === '1' && this.params.p_tieuchi0 !== null && this.params.p_tieuchi0.trim() !== '') {
        // tieuchi = this.getNoiDungTieuChi(this.params.p_opt0)
        // arrDieuKien.push(tieuchi.DIEUKIEN.replace(':values', this.params.p_tieuchi0))
        arrDieuKien.push({
          'ID': this.params.p_opt0,
          'GIATRI': this.params.p_tieuchi0.trim()
        })
      }

      if (this.params.p_chk_opt1 === '1' && this.params.p_tieuchi1 !== null && this.params.p_tieuchi1.trim() !== '') {
        // tieuchi = this.getNoiDungTieuChi(this.params.p_opt1)
        // arrDieuKien.push(tieuchi.DIEUKIEN.replace(':values', this.params.p_tieuchi1))

        arrDieuKien.push({
          'ID': this.params.p_opt1,
          'GIATRI': this.params.p_tieuchi1.trim()
        })
      }

      if (this.params.p_chk_opt2 === '1' && this.params.p_tieuchi2 !== null && this.params.p_tieuchi2.trim() !== '') {
        // tieuchi = this.getNoiDungTieuChi(this.params.p_opt2)
        // arrDieuKien.push(tieuchi.DIEUKIEN.replace(':values', this.params.p_tieuchi2))
        arrDieuKien.push({
          'ID': this.params.p_opt2,
          'GIATRI': this.params.p_tieuchi2.trim()
        })
      }

      if (this.params.p_chk_opt3 === '1' && this.params.p_tieuchi3 !== null && this.params.p_tieuchi3.trim() !== '') {
        // tieuchi = this.getNoiDungTieuChi(this.params.p_opt3)
        // arrDieuKien.push(tieuchi.DIEUKIEN.replace(':values', this.params.p_tieuchi3))
        arrDieuKien.push({
          'ID': this.params.p_opt3,
          'GIATRI': this.params.p_tieuchi3.trim()
        })
      }

      /*
        if(arrDieuKien.length > 0)
          strDieuKien = arrDieuKien.join(' and ')
        */

      let vKyHD = ''

      if (this.params.p_chk_chungtu === '1') {
        vKyHD = '01' + moment(this.params.p_ky_hd).format('MMYYYY')
      }

      this.is_show = false

      this.responseData.dschonthang = this.params.p_chk_thangno === '1' ? 1 : 0
      this.responseData.dsthang = this.params.p_chk_thangno === '1' ? this.params.p_thangno.length > 0 ? this.params.p_thangno.join(',') : '0' : null
      this.responseData.dschonkm = this.params.p_chk_khoanmuc === '1' ? 1 : 0
      this.responseData.dskmtt = this.params.p_chk_khoanmuc === '1' ? this.params.p_khoanmuc.length > 0 ? this.params.p_khoanmuc.join(',') : '0' : null

      let postData = {
        pDieuKien: arrDieuKien,
        pKieuNo: this.params.p_kieuno,
        pTuTien: this.params.p_chk_tutien === '1' ? this.params.p_tutien : null,
        pDenTien: this.params.p_chk_dentien === '1' ? this.params.p_dentien : null,
        pChungTu: this.params.p_chk_chungtu === '1' ? this.params.p_chungtu.trim() : null,
        pKyHD: vKyHD,
        pTimThang: this.responseData.dschonthang,
        pDSThang: this.responseData.dsthang,
        pNhomCKId: this.params.p_nhomck_id,
        pTimKMTT: this.responseData.dschonkm,
        pDSKhoanMuc: this.responseData.dskmtt
      }

      this.loading(true)

      this.dsDSThanhToan = []
      this.pageinfoDSThanhToan.totalElement = 0
      this.pageinfoDSThanhToan.page = 0

      GachNoAPI.tracuuDSTTDieuChinhNo(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
                response.data.data !== undefined) {
              this.keyDSThanhToan++
              this.dsDSThanhToan = response.data.data
              this.pageinfoDSThanhToan.totalElement = response.data.data.length
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    doAccept () {
      if (this.dsDSThanhToan.length <= 0) {
        this.$toast.error('Không có dữ liệu để xác nhận')
        return
      }

      let selectedRows = this.$refs.gridDSThanhToan.getSelectedRecords()

      if (selectedRows.length <= 0) {
        this.$toast.error('Bạn chưa chọn mã thanh toán để xác nhận')
        return
      }

      if (this.params.p_chk_thangno !== '1') {
        for (let i = 0; i < selectedRows.length; i++) {
          selectedRows[i].THANGTRA = null
        }
      }

      this.responseData.ds_chon = [...selectedRows]

      this.$emit('onSelectRowsAccept', this.responseData)
      // this.$refs.dlgTraCuuDSThanhToan.hide()
      this.closeDialog()
    },

    doUpdate () {
      if (this.params.p_ma_tt === null || this.params.p_ma_tt === '' || this.params.p_ma_tt.trim() === '') {
        this.$toast.error('Chưa nhập mã Thanh toán.')
        return
      }

      if (this.params.p_khoanmuc_bosung === null) {
        this.$toast.error('Chưa chọn khoản mục.')
        return
      }

      let nThangNo = parseInt(moment(this.params.p_thang).format('YYYYMM') + '01')
      let nThangHT = parseInt(this.configs.kycuoc_hienhanh)

      // if (nThangNo >= nThangHT) {
      //   this.$toast.error('Bạn chỉ được chọn tháng nợ nhỏ hơn tháng hiện tại')
      //   return
      // }

      let khoanmuc = this.params.p_khoanmuc_options.filter(x => x.KHOANMUCTT_ID === this.params.p_khoanmuc_bosung)
      console.log(khoanmuc)

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn có muốn thêm khoản mục " + khoanmuc[0].TEN_KM + ' vào mã thanh toán của tháng nợ ' + moment(this.p_thang).format('MM/YYYY') + ' ?</div>',
        okButton: { text: 'Đồng ý', click: this.confirmUpdate },
        cancelButton: { text: 'Hủy' },
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },

    confirmUpdate () {
      DialogObj.hide()

      if (this.dsKMTT.length > 0) {
        this.dsKMTT.forEach((item) => {
          if (item.KHOANMUCTT_ID === this.params.p_khoanmuc_bosung) {
            this.$toast.error('Khoản mục đã có rồi bạn không thể thêm')
          }
        })

        let kieuno = 5

        if (moment(this.params.p_thang).format('YYYYMM') + '01' !== this.configs.kycuoc_hienhanh) { kieuno = 0 }

        let postData = {
          'kyCuoc': this.configs.kycuoc_hienhanh,
          'maTT': this.params.p_ma_tt,
          'maTB': this.params.p_sm_acc,
          'loaiTienId': 1,
          'khoanMucTTId': this.params.p_khoanmuc_bosung,
          'vbccId': this.dsKMTT[0].VBCC_ID,
          'donViId': this.dsKMTT[0].DONVI_ID,
          'chuKyNo': moment(this.params.p_thang).format('YYYYMM') + '01',
          'kieuNo': kieuno,
          'dichVuVTId': this.dsKMTT[0].DICHVUVT_ID,
          'donViQLId': this.dsKMTT[0].DONVI_QL_ID,
          'thanhToanId': this.dsKMTT[0].THANHTOAN_ID
        }

        this.loading(true)
        GachNoAPI.insertKhoanMucNo(this.axios, postData)
          .then(
            (response) => {
              if (response.data.error_code === 'BSS-00000000' &&
                response.data.data !== undefined) {
                this.$toast.success('Thêm khoản mục thành công')
                this.getKhoanMucTT()
              }
            }
          )
          .catch(err => {
            this.$toast.error(err.data.message_detail)
          })
          .finally(() => { this.loading(false) })
      } else {
        // let donviql_id = 0
        // let donvitc_id = 0
        // let dichvuvt_id = 0
        // let thanhtoan_id = 0
      }
    },

    async getDSTieuChiTimKiem () {
      try {
        const res = await GachNoAPI.getTieuChiTimKiem(this.axios)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (error) {
        return []
      }
      // GachNoAPI.getTieuChiTimKiem(this.axios)
      //   .then(
      //     (response) => {
      //       if (response.data.error_code === 'BSS-00000000' &&
      //           response.data.data !== undefined) {
      //         // console.lo
      //         this.params.p_options = response.data.data
      //         this.params.p_opt0 = this.params.p_options[0].KIEUTK_ID
      //         this.params.p_opt1 = this.params.p_options[1].KIEUTK_ID
      //         this.params.p_opt2 = this.params.p_options[2].KIEUTK_ID
      //       }
      //     }
      //   )
      //   .catch(err => {
      //     this.$toast.error(err.data.message_detail)
      //   })
      //   .finally(() => { this.loading(false) })
    },
    async timkiem_KMTT () {
      if (this.params.p_ma_tt === null || this.params.p_ma_tt === '' || this.params.p_ma_tt.trim() === '') {
        this.$toast.error('Bạn chưa nhập mã thanh toán')
        return
      }

      this.params.p_sm_acc_options = []
      this.params.p_sm_acc = null

      this.dsKMTT = []
      this.loading(true)

      try {
        let response = await this.getPayment_Info_ForPay(this.params.p_ma_tt, moment(this.params.p_thang).format('YYYYMM') + '01',this.loai_tien)

        if (response !== null) {
          if (response.length > 1) {
            this.ds_thanhtoan = response
            this.$refs.refChonThanhToan.openDialog()
          } else if (response.length === 1) {
            await this.getDSTBTheoMaTT()
            this.loading(false)
          }
        } else {
          const res = await this.getThongTinTTTheoDBHienTai()
          if (res === null) {
            this.$toast.error('Không tìm thấy thông tin thanh toán')
            return false
          }
          await this.getDSTBTheoMaTT()
          this.loading(false)
        }
      } catch (error) {
        this.loading(false)
      }
      // await this.getKhoanMucTT()
    },

    async getPayment_Info_ForPay (paycode, kycuoc,loaiTien) {
      this.loading(true)
      try {
        const res = await GachNoAPI.getPayment_Info_ForPay(this.axios, {pKyCuoc: kycuoc, pPaymentCode: paycode,pLoaiTien:loaiTien})
        const data = await res.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      } catch (err) {
        console.log(err.data.message_detail)
        return null
      } finally {
        this.loading(false)
      }
    },

    async getThongTinTTTheoDBHienTai () {
      try {
        const res = await GachNoAPI.getTTThanhhToanTheoDBHienTai(this.axios, this.params.p_ma_tt)
        const data = await res.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      } catch (error) {
        // console.log(err)
        return null
      }
    },

    async getDSTBTheoMaTT () {
      const response = await this.getDanhBaTheoMaTT()

      // console.log(response)

      this.params.p_sm_acc_options = []

      if (response !== null) {
        let tmp = []
        response.forEach(item => {
          // this.params.p_acc_somay_options.push({ THUEBAO_ID: item.THUEBAO_ID, MA_TB: item.MA_TB })
          if (this.params.p_sm_acc_options.length <= 0 || !tmp.includes(item.MA_TB)) {
            tmp.push(item.MA_TB)
            this.params.p_sm_acc_options.push({ MA_TB: item.MA_TB })
          }
        })
        this.params.p_sm_acc = this.params.p_sm_acc_options[0].MA_TB
        // console.log(this.params.p_sm_acc)
      }
    },

    async getDanhBaTheoMaTT () {
      try {
        let postData = {
          maTT: this.params.p_ma_tt,
          donViDLId: 0
        }
        const res = await CommonsAPI.getDanhBaTheoMaTT2(this.axios, postData)
        const data = await res.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      } catch (err) {
        console.log(err)
      }
    },

    async getKyCuocHienHanh () {
      // if (this.configs.kycuoc_hienhanh !== null) { return }
      try {
        const res = await CommonsAPI.getKyCuocHienHanh(this.axios)
        const data = await res.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      } catch (error) {
        return []
      }
      // CommonsAPI.getKyCuocHienHanh(this.axios)
      //   .then(
      //     (res) => {
      //       if (res.data.error_code === 'BSS-00000000' &&
      //         res.data.data !== undefined &&
      //         res.data.data.length > 0
      //       ) {
      //         this.configs.kycuoc_hienhanh = res.data.data[0].KYCUOC
      //         this.params.p_thang = moment(this.configs.kycuoc_hienhanh, 'YYYYMMDD').toDate()
      //       }
      //     }
      //   )
    },

    async getKhoanMucTT () {
      let postData = {
        kyCuoc: this.configs.kycuoc_hienhanh,
        maTB: this.params.p_sm_acc,
        maTT: this.params.p_ma_tt
      }

      try {
        const res = await GachNoAPI.getKhoanMucTT(this.axios, postData)
        const data = await res.data

        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) {
          let arr = [...data.data]
          let chukyno = ''
          for (let i = 0; i < arr.length; i++) {
            chukyno = '' + arr[i].CHUKYNO
            arr[i].CHUKYNO_HIENTHI = 'Tháng nợ ' + chukyno.substr(4, 2) + '/' + chukyno.substr(0, 4)
          }
          /*
          data.forEach((item) => {
            data.CHUKYNO_HIENTHI = 'Tháng nợ ' + item.CHUKYNO.substr(4,2) + '/' + item.CHUKYNO.substr(0,4)
          })
          */
          console.log(arr)
          // this.dsKMTT = res.data.data
          this.dsKMTT = arr
        }
      } catch (error) {
        return false
      }
      
    },

    timkiem_TenNhom () {
      if (this.params.p_chk_nhom !== '1') { return }
      if (this.params.p_nhom === null || this.params.p_nhom === '' || this.params.p_nhom.trim() === '') {
        this.$toast.error('Bạn phải nhập tên nhóm.')
      }
    },

    onKhoanMucFiltering: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_KM', 'contains', e.text, true) : query
      e.updateData(this.params.p_khoanmuc_options, query)
    },

    async onChangeSmAcc () {
      await this.getKhoanMucTT()
    },

    acceptChonMaThanhToan (e) {
      console.log(e)
    },
    chkKhoanMucChange (e) {
      if (e === 1) {  // checked

      }
    },
    dsPhieuSelected (args) {
      this.$refs.gridDSThanhToan.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    dsPhieuDeselected (args) {
      this.$refs.gridDSThanhToan.$refs.grid.ej2Instances.aggregateModule.refresh()
    }
  },
  watch: {    
  },
  provide: {
    multiselect: [CheckBoxSelection]
  }
})
</script>
<style>
  .vattu_box {
    position: relative;
  }

  .vattu_table {
    position: absolute;
    z-index: 100;
    max-height: 350px;
    background: aliceblue;
    overflow-y: scroll;
    min-width: 600px;
  }
  .split
  {
    overflow-x: unset;
  }
</style>
