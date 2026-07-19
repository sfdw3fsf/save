<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgCacKhoanNo"
    :header='"Các khoản nợ"'
    showCloseIcon=true
    width='1100px'
    height='700'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="popup-top-action">
        <div class="acept item pointer" v-on:click.prevent="doAccept">
            <span class="icon -ap icon-ion-ios-checkmark"></span> Chấp nhận
        </div>
      </div>
      <div class="popup-body">
          <div class="nav tabs tab-over">
              <a href="#tab1" v-on:click="tab_index = 1" data-toggle="tab" class="tab active">Tháng nợ</a>
              <a href="#tab2" v-on:click="tab_index = 2" data-toggle="tab" class="tab" v-show="params.p_db_datcoc === 0">Thuê bao</a>
              <a href="#tab3" v-on:click="tab_index = 3" data-toggle="tab" class="tab" v-show="params.p_db_datcoc === 1">Tiền đặt cọc trả trước</a>
          </div>
          <div class="tab-content" style="border-top-left-radius: 0px;border-top-right-radius: 0px;">
              <div class="tab-pane active" id="tab1">
                  <div class="row">
                      <div class="col-md-6 col-12">
                          <div class="info-row">
                              <div class="key w30 center nowrap padr20">
                                  <div class="red">
                                    <b-form-checkbox
                                        v-model = "params.p_chk_chonthang"
                                        class = "check"
                                        value = '1'
                                        unchecked-value = '0'
                                      >
                                        <span class="name">Chọn theo tháng</span>
                                      </b-form-checkbox>
                                  </div>
                              </div>
                              <div class="key w30 nowrap text-main">
                                  Dịch vụ
                              </div>
                              <div class="value">
                                  <div class="select-custom">
                                    <ejs-dropdownlist :change="onChangeDichVu" :allowFiltering="false" :dataSource='params.p_dichvu_options' :fields="fieldsDichVu" v-model = "params.p_dichvu"></ejs-dropdownlist>
                                  </div>
                              </div>

                          </div>
                      </div>
                      <div class="col-md-6 col-12">
                          <span class="note text-main fw6  block">
                              <span class="-ap icon-warning3 text-warning f20 mar10"></span> Insert: check All. Delete: bỏ chọn, A: Mở rộng, Z: Thu gọn
                          </span>
                      </div>
                  </div>
                  <div class="table-content">
                    <DataGrid3
                      v-bind:columns="[
                        {fieldName: 'CKN', headerText: 'Chu kỳ nợ', isPrimaryKey: true, allowFiltering: true, isGroupedColumn: true},
                        {fieldName: '_CHECK', headerText: '', allowFiltering: false, allowEditing: true, editType: 'booleanedit', displayAsCheckBox: true, type: 'boolean', width: 70},
                        {fieldName: 'MA_TT', visible: false},
                        {fieldName: 'MA_TB', headerText: 'Số máy/Acc', isPrimaryKey: true, allowFiltering: true},
                        {fieldName: 'TONGNO', headerText: 'Còn nợ', allowFiltering: true, format:'N0', type: 'number'},
                        {fieldName: 'TAMTHU', headerText: 'Tạm thu', allowFiltering: true, format:'N0', type: 'number'},
                        {fieldName: 'DONVI_QL', headerText: 'Đơn vị TT', allowFiltering: true},
                      ]"
                      v-bind:aggregates="
                        [
                        {field: 'MA_TB', type: 'Custom', footerTemplate: this.countKhoanNo, customAggregateFn: this.countKhoanNoAggregate},
                        {field: 'TONGNO', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.customAggregate },
                        {field: 'TAMTHU', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate}
                        ]
                      "
                      v-bind:dataSource="dsKhoanNoTheoKyHD"
                      :showFilter="true"
                      :allowPaging="false"
                      :editSettings= "editSettings"
                      height="100%"
                      ref="gridKhoanNoTheoKyHD"
                      :key="keyKhoanNoTheoKyHD"
                      :enabledSelectFirstRow="false"
                      @queryCellInfo="queryCellInfoKyHD"
                      >
                    </DataGrid3>

                  </div>
              </div>
              <div class="tab-pane" id="tab2"  v-show="params.p_db_datcoc === 0">

                  <div class="table-content" style="height:400px">
                    <DataGrid
                      v-bind:columns="[
                        {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true},
                        {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', allowFiltering: true},
                        {fieldName: 'THUCNO', headerText: 'Còn nợ', allowFiltering: true, format:'N0', type: 'number'},
                      ]"
                      v-bind:aggregates="[
                        {field: 'THUCNO', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
                      ]"
                      v-bind:dataSource="dsKhoanNoTheoTB"
                      :showFilter="true"
                      :allowPaging="false"
                      :enablePagingServer="false"
                      height="100%"
                      :totalRecords="pageinfoTB.totalElement"
                      :pageSize="pageinfoTB.maxSize"
                      :pageIndex="pageinfoTB.page"
                      :enabledSelectFirstRow="false"
                      :showColumnCheckbox="true"
                      :key="keyKhoanNoTheoTB"
                      @actionComplete="actionCompleteTB"
                      @dataBound="dataBoundTB"
                      
                      ref="gridKhoanNoTheoTB"
                      >
                    </DataGrid>
                  </div>
              </div>
              <div class="tab-pane" id="tab3"  v-show="params.p_db_datcoc === 1">

                  <div class="table-content" style="height:400px">
                    <DataGrid
                      v-bind:columns="columnsDCTT"
                      v-bind:dataSource="dsTraTruoc"
                      :showColumnCheckbox="true"
                      :showFilter="true"
                      :allowPaging="false"
                      :enablePagingServer="false"
                      height="100%"
                      ref="gridTraTruoc"
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
import Vue from "vue"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import GachNoAPI from '../../api/GachNoAPI'
import { currency } from '@/filters/currency'
import moment from 'moment'
import EventBus from '@/utils/eventBus'
import { GridPlugin, Page, Edit } from "@syncfusion/ej2-vue-grids";
//import checkBoxTemplate from './checkBoxTemplate'

Vue.use(DialogPlugin)

import {
    DichVuVienThong
  } from "@/const/enums"

 export default Vue.extend({
    components : {
      //checkBoxTemplate
    },
    props: {
      ds_tongno:{
        type: Array,
        required: false
      },
      vkieutra:{
        type: Number
      },
      vsoluong_tb:{
        type: Number
      },
      ma_tt: {
        type: String
      },
      ma_tb: {
        type: String,
        required: false
      },
      kycuoc: {
        type: String
      },
      db_datcoc: {
        type: Number
      },
      vquyen_dd: {
        type: Boolean,
        default: false
      },
    },
    computed : {
      currentRowKyHD () {
        return this.pageinfoKyHD.currentRow
      },
    },
    created () {
      EventBus.$on('checkAll', this.checkAllHandler)
    },
    mounted ()
    {
    },
    destroyed () {
      EventBus.$off('checkAll', this.checkAllHandler)
    },
    data () {
      return {
        isChecked: false,
        editSettings: {
          allowEditing: true,
          allowAdding: true,
          allowDeleting: true
        },
        groupOptions: {
          columns: ["CHUKYNO"]
        },
        cTemplate: function() {
          return {
            template: Vue.component("columnTemplate", {
              template: `<div class="check-action"><input type="checkbox" v-on:click="headerclicked($event,data)" class="check"><span class="name"></span></div>`,
              data: function() {
                return {
                  data: {}
                };
              },
              computed: {},
              methods: {
                headerclicked: function(e, data) {
                  console.log(e.target.checked)
                  console.log(e);
  // here you can perform your actions
                  //alert(data.field+ " " + "Clicked");
                  EventBus.$emit('checkAll', e.target.checked)
                  return false
                }
              }
            })
          };
        },
        checkBoxTemplate: function(isChecked){
          return () => {
            return {
              template: {
                extends: checkBoxTemplate,
                propsData: {
                  isChecked: isChecked
                }
              }
            };
          };
        },

        templateOptions : {
          create: function (args) {
            if(args.column.field === 'STT')
              return '<span class="fa"></span>'
            else if(args.column.field === '_CHECK'){
              return '<div class="e-checkbox-wrapper e-css"><input class="e-checkselect" type="checkbox"><span class="e-frame e-icons"></span><span class="e-label"> </span></div>'
            } else {
               return '<div class="input-icon-right"><input id="' + args.column.field + '" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
            }

          },
          write: function (args) {
            // const $this = this
            args.element.addEventListener('input', (args) => {
              let value = args.target.value
              if(value !== '')
                $this.$refs.gridKhoanNoTheoTB.filterByColumn(args.target.id, 'contains', value, 'AND', false)
              else
              {
                let arrFilter = []
                $this.$refs.gridKhoanNoTheoTB.clearFiltering(new Array(args.target.id))
              }
              // console.log($this.$refs.gridDSTreoNo.getFilteredRecords())
            })
          }
        },
        cRowSelected: function() {
          return {
            template : Vue.component('columntemplate', {
              template : '<span class="fa"></span>'
            })  // end of template
          }
        },
        footerSum: function () {
          return  { template : Vue.component('sumTemplate', {
              template: `<span>{{data.Sum}}</span>`,
              data () {return { data: {}};}
              })
            }
        },
        footerCount: function () {
          return  { template : Vue.component('countTemplate', {
              template: `<span>{{data.Count}}</span>`,
              data () {return { data: {}};}
              })
            }
        },
        sumTemplate: function() {
          return { template: Vue.component('sumTemplate', {
              template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
              data: function () {return {data: {data: {}}};}
            })
          }
        },
        countTemplate: function() {
          return { template: Vue.component('countTemplate', {
              template: `<input type="text" class="form-control tright" :value="data.Count" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
              data: function () {return {data: {data: {}}};}
            })
          }
        },
        countKhoanNo: function(){
          return  { template : Vue.component('footerTemplate', {
              template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
              data () { return { data: {data: {}}}; }
            })
          }
        },
        customTemplate: function() {
          return  { template : Vue.component('footerTemplate', {
              template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
              data () { return { data: {data: {}}}; }
            })
          }
        },
        tab_index: 1,
        selectionOptions: { type: 'Multiple', checkboxOnly: true},
        columnsKyHD: [
          {fieldName: 'CKN', headerText: 'Chu kỳ nợ', isPrimaryKey: true, allowFiltering: true/*, isGroupedColumn: true*/},
          {fieldName: '_CHECK', displayAsCheckBox: true, headerText: 'CHECK'},
          {fieldName: 'MA_TT', visible: false},
          {fieldName: 'MA_TB', headerText: 'Số máy/Acc', isPrimaryKey: true, allowFiltering: true},
          {fieldName: 'TONGNO', headerText: 'Còn nợ', allowFiltering: true, format:'N0', type: 'number'},
          {fieldName: 'TAMTHU', headerText: 'Tạm thu', allowFiltering: true, format:'N0', type: 'number'},
          {fieldName: 'DONVI_QL', headerText: 'Đơn vị TT', allowFiltering: true},

        ],
        sumColumnsKyHD: [
          {field: 'CHUKYNO', type: 'Count', format: 'N0', footerTemplate: this.footerCount},
          {field: 'TONGNO', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
          {field: 'TAMTHU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
        ],
        columnsTB: [
          {fieldName: '_CHECK', headerText: '', allowFiltering: false, allowEditing: true, editType: 'booleanedit', displayAsCheckBox: true, type: 'boolean', width: 40},
          {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true},
          {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', allowFiltering: true},
          {fieldName: 'THUCNO', headerText: 'Còn nợ', allowFiltering: true, format:'N0', type: 'number'},
        ],
        sumColumnsTB: [
          {field: 'THUCNO', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
        ],
        columnsDCTT: [
          {fieldName: 'RKM_ID', visible: false},
          {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true},
          {fieldName: 'THANG_BD', headerText: 'Tháng bắt đầu', allowFiltering: true},
          {fieldName: 'THANG_KT', headerText: 'Tháng kết thúc', allowFiltering: true},
          {fieldName: 'TIEN_VI', headerText: 'Tiền', allowFiltering: true, format:'N0', type: 'number'},
        ],
        fieldsDichVu: {text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
        params: {
          p_dichvu_options: [
              {DICHVUVT_ID: 0, TEN_DVVT: "Tất cả"},
              {DICHVUVT_ID: 1, TEN_DVVT: "Di động"},
              {DICHVUVT_ID: 2, TEN_DVVT: "Dịch vụ khác di động"},
            ],
          p_dichvu: -1,
          p_chk_chonthang: '0',
          p_matb_hienthi: '',
          p_vquyen_dd: false
        },
        p_ds_tongno: [],
        dsKhoanNoTheoTB : [],
        dsKhoanNoTheoKyHD : [],
        dsTraTruoc : [],
        selectedRows: [],
        pageinfoKyHD : {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        pageinfoTB: {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        f_check: false,
        total: 0,
        selectedTotal: 0,
        selectedCountKhoanNo: 0,
        totalKhoanNo: 0,
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
        KyHD_grid: {
            items: [],
            checked: []
        },
        keyKhoanNoTheoKyHD: 0,
        keyKhoanNoTheoTB: 0
      }
    },
    methods : {
      customAggregate() {
        return currency(this.selectedTotal) + '/' + currency(this.total);
      },
      countKhoanNoAggregate() {
        return currency(this.selectedCountKhoanNo) + '/' + currency(this.totalKhoanNo);
      },
      async getTongNoTheoKyHoaDon() {
        try{
          let postData = {
            pKyCuoc: this.kycuoc,
            pMaTT: this.ma_tt,
            pMaTB: this.vkieutra === 1 ? this.ma_tb : null
          }
          const results = await GachNoAPI.getTongNoTheoKyHoaDon(this.axios, postData)
          return results
        }catch(err) {
          console.log(err)
        }
      },
      async getTongNoTheoThueBao() {
        try{
          let postData = {
            pKyCuoc: this.kycuoc,
            pMaTT: this.ma_tt,
            pMaTB: this.vkieutra === 1 ? this.ma_tb : null
          }
          const results = await GachNoAPI.getTongNoTheoThueBao(this.axios, postData)
          return results
        }catch(err) {
          console.log(err)
        }
      },
      clickOnGridTB(e)
      {
        console.log('click')
        if (
          e.target.classList.contains("e-rowcell") &&
          e.target.querySelector(".e-checkbox-wrapper")
        ) 
        {
          if (this.$refs.gridKhoanNoTheoTB.$refs.grid.ej2Instances.isEdit)
            this.$refs.gridKhoanNoTheoTB.$refs.grid.ej2Instances.endEdit();
          
          var index = parseInt(e.target.getAttribute("Index"), 10); // row index
          //console.log(index)
          //console.log(this.$refs.gridKhoanNoTheoKyHD.ej2Instances.currentViewData.records)
          var rowData = this.$refs.gridKhoanNoTheoTB.$refs.grid.ej2Instances.currentViewData.records[index]; // row data

          let arr = []

          this.dsKhoanNoTheoTB.forEach((item) => {
              if(item.MA_TB === rowData.MA_TB)
              {
                item._CHECK = !item._CHECK

                //alert(item._CHECK)
              }
              arr.push(item)
            })
          this.dsKhoanNoTheoTB = [...arr]
        }
      },
      click(e) {

        //console.log(e)

        if (
          e.target.classList.contains("e-rowcell") &&
          e.target.querySelector(".e-checkbox-wrapper")
        ) {
          //console.log(e)
          this.selectedTotal = 0
          this.selectedCountKhoanNo = 0
          if (this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances.isEdit)
            this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances.endEdit();
          var index = parseInt(e.target.getAttribute("Index"), 10); // row index
          //console.log(index)
          //console.log(this.$refs.gridKhoanNoTheoKyHD.ej2Instances.currentViewData.records)
          var rowData = this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances.currentViewData.records[index]; // row data
          /*
          var column = this.$refs.gridKhoanNoTheoKyHD.ej2Instances.columns[
            parseInt(e.target.getAttribute("aria-colindex"), 10)
          ].field; // field name
          */
          let arr = []
          if(this.params.p_chk_chonthang !== '1')
          {
            this.dsKhoanNoTheoKyHD.forEach((item) => {
              if(item._PK === rowData._PK)
              {
                item._CHECK = !item._CHECK

                //alert(item._CHECK)
              }
              if(item._CHECK)
              {
                this.selectedTotal += item.TONGNO
                this.selectedCountKhoanNo++
              }

              arr.push(item)
            })
          } else
          {
            let checkValue = rowData._CHECK

            this.dsKhoanNoTheoKyHD.forEach((item) => {
              if(item.CHUKYNO === rowData.CHUKYNO)
              {
                item._CHECK = !checkValue
              }
              if(item._CHECK)
              {
                this.selectedTotal += item.TONGNO
                this.selectedCountKhoanNo++
              }

              arr.push(item)
            })
          }


          this.dsKhoanNoTheoKyHD = [...arr]

          //console.log(this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances)

          //rowData[column] = !checkValue;
          //console.log(rowData[column])

          // Update the row with the modified data
          //this.$refs.gridKhoanNoTheoKyHD.ej2Instances.updateRow(index, rowData);
        }
      },
      openDialog() {
        this.params.p_db_datcoc = this.db_datcoc
        this.params.p_ds_tongno = [...this.ds_tongno]

        this.$refs.dlgCacKhoanNo.show()
      },
      closeDialog() {
        this.clearForm()
        this.clearResult()
        window.removeEventListener("keydown", this.keypressHandler);

        this.$refs.dlgCacKhoanNo.hide()
      },
      onDialogClose() {
        this.clearForm()
        this.clearResult()
        window.removeEventListener("keydown", this.keypressHandler);
      },
      keypressHandler(e){
        //console.log(e.key)
        //let gridObj = this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances
        let gridObj = this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances

        let key = e.key.toUpperCase()
        if(key === 'INSERT')
        {
          //gridObj.selectRowsByRange(0, this.dsKhoanNoTheoKyHD.length)
          let arr = []
          this.dsKhoanNoTheoKyHD.forEach((item, index) => {
            item._CHECK = true
            arr.push(item)
          })

          this.dsKhoanNoTheoKyHD = [...arr]
        } else if(key === 'DELETE')
        {
          //gridObj.clearSelection()
          let arr = []
          this.dsKhoanNoTheoKyHD.forEach((item, index) => {
            item._CHECK = false
            arr.push(item)
          })

          this.dsKhoanNoTheoKyHD = [...arr]
        } else if(key === 'A')
        {
          //console.log(this.$refs.gridKhoanNoTheoKyHD.$refs.grid)
          //this.$refs.gridKhoanNoTheoKyHD.$refs.grid.groupExpandAll()
          //console.log(this.$refs.gridKhoanNoTheoKyHD)
          this.$refs.gridKhoanNoTheoKyHD.$refs.grid.groupExpandAll()
        } else if(key === 'Z')
        {
          //console.log(this.$refs.gridKhoanNoTheoKyHD.$refs.grid)
          //this.$refs.gridKhoanNoTheoKyHD.$refs.grid.groupCollapseAll()
          this.$refs.gridKhoanNoTheoKyHD.$refs.grid.groupCollapseAll()
        } else if(key === 'ENTER')
        {
          this.doAccept()
        }
      },

      onDialogOpen() {


        if(this.vquyen_dd !== undefined)
          this.params.p_vquyen_dd = this.vquyen_dd
        this.params.p_chk_chonthang = '0'


        //console.log(this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances)

        this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances.element.addEventListener(
          "click",
          this.click
        );
/*
        this.$refs.gridKhoanNoTheoTB.$refs.grid.ej2Instances.element.addEventListener(
          "click",
          this.clickOnGridTB
        );
*/
        this.total = 0
        this.selectedTotal = 0
        window.addEventListener('keydown', this.keypressHandler);
        this.getTongNoTheoKyHoaDon().then(
          (response) => {
            console.log(response.data.data)
            if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0
            ){
              let arr = []
              this.total = 0
              this.totalKhoanNo = 0
              response.data.data.forEach((item, index) => {
                item.CKN = 'Tháng ' + moment(item.CHUKYNO, 'YYYYMMDD').format('MM/YYYY')
                if (item.TONGNO === 0 || (item.DICHVUVT_ID === DichVuVienThong.DI_DONG && !this.quyen_dd))
                  item._CHECK = false
                else
                {
                  item._CHECK = true
                  this.selectedCountKhoanNo++
                }
                item._PK = index
                this.total += item.TONGNO
                this.selectedTotal += item.TONGNO
                this.totalKhoanNo++

                arr.push(item)
              })



              this.dsKhoanNoTheoKyHD = [...arr]
              //this.keyKhoanNoTheoKyHD = 2
              //this.dsKhoanNoTheoKyHD = response.data.data
              //this.pageinfoKyHD.totalElement = response.data.data.length



              if(this.db_datcoc === 1)
              {
                const dsTB  = [...new Set(this.dsKhoanNoTheoKyHD.map(item => item.MA_TB))];
                /*
                this.dsKhoanNoTheoKyHD.forEach((item) => {
                  if(!dsTB.includes(item.MA_TB))
                    dsTB.push(item.MA_TB)
                })
                */

                let postData = {
                  "pKyCuoc": this.kycuoc,
                  "pDSMaTB": dsTB
                }
                this.getTienDatCoc(postData)
              }
            }
          }
        )
        this.getTongNoTheoThueBao().then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0
            )
            {
              this.keyKhoanNoTheoTB++
              this.dsKhoanNoTheoTB = response.data.data
              this.pageinfoTB.totalElement = response.data.data.length
              this.pageinfoTB.totalPages = 1
            }
          }
        )
      },
      clearForm() {
        this.params.p_dichvu = -1
        //console.log(this.params.p_dichvu)
      },
      clearResult() {
        this.dsKhoanNoTheoKyHD = []
        this.dsKhoanNoTheoTB = []
        this.pageinfoKyHD = {
          page: 1,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        this.pageinfoTB = {
          page: 1,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        }
      },
      getTienDatCoc(postData){

        this.loading(true)
        GachNoAPI.getTienDatCoc(this.axios, postData)
          .then((res) => {
            if(res.data.error_code === 'BSS-00000000')
            {
              this.dsTraTruoc = res.data.data
            }
          })
          .catch(error => {
            this.$toast.error(error.data.message_detail)
          })
          .finally(() => {this.loading(false)})
      },
      doAccept: function(){
        let postData = {
          matb_hienthi : this.params.p_matb_hienthi,
          cacmatb: [],
          cacchukyno: [],
          cacthangno: [],
          vsochukylchon: 0,
          ds: []
        }
        if(this.tab_index === 1 || (this.tab_index === 3 && this.db_datcoc === 1))
        {
          this.params.p_matb_hienthi = ''
          //let selectedrows = this.$refs.gridKhoanNoTheoKyHD.getSelectedRecords();  // Get the selected row indexes.
          let selectedrows = this.dsKhoanNoTheoKyHD.filter(x => x._CHECK === true)
          //console.log(selectedrows)
          /*
          if(selectedrows.length <= 0) {
            //console.log('not found')
            this.$toast.error('Chọn kỳ hóa đơn để thanh toán')
            return false
          }
          */

          if(selectedrows.length > 0)
          {
            selectedrows.forEach(item => {
              if(!postData.cacmatb.includes(item.MA_TB))
                postData.cacmatb.push(item.MA_TB)
              if(!postData.cacchukyno.includes(item.CHUKYNO))
                postData.cacchukyno.push(item.CHUKYNO)
              if(!postData.cacthangno.includes(item.CHUKYNO + ',' + item.MA_TB))
                postData.cacthangno.push(item.CHUKYNO + ',' + item.MA_TB)

              if(postData.matb_hienthi === '')
                postData.matb_hienthi = item.MA_TB

              //postData.push({chukyno: item.CHUKYNO, ma_tb: item.MA_TB})
              postData.vsochukylchon = postData.vsochukylchon + 1
            })
          }

          if(postData.matb_hienthi !== '')
          {
            if (this.db_datcoc === 1)
            {
                if(this.dsTraTruoc.length <= 0)
                {
                  this.$toast.error("Không tìm thấy thông tin đặt cọc trả trước không thể gạch");
                  return;
                }
                let ds_chon_tt = this.$refs.gridTraTruoc.getSelectedRecords()
                if (ds_chon_tt.length <= 0)
                {
                  this.$toast.error("Bạn chưa chọn thông tin tiền trả trước cần trừ");
                  return;
                }
                postData.ds = [...ds_chon_tt]
            }
          } else 
          {
            postData.cacchukyno = []
            postData.cacmatb = []
            postData.cacthangno = []
            if (this.db_datcoc === 1)
            {
                const distinctValues = [...new Set(this.dsKhoanNoTheoKyHD.map(item => item.MA_TB))];
                if (distinctValues.length > 1)
                {
                  this.$toast.error("Bạn đang chọn nhiều hơn 1 mã thuê bao");
                  return;
                }
                if (this.dsTraTruoc.length <= 0)
                {
                  this.$toast.error("Không tìm thấy thông tin đặt cọc trả trước không thể gạch");
                  return;
                }
                let ds_chon_tt = this.dsKhoanNoTheoKyHD.filter(x => x._CHECK === true)
                if (ds_chon_tt.length <= 0)
                {
                  this.$toast.error("Bạn chưa chọn thông tin tiền trả trước cần trừ");
                  return;
                }
                postData.ds = [...ds_chon_tt]
            }
          }

          

          if(this.params.p_chk_chonthang === '1')
          {
            postData.cacthangno = []
            postData.cacmatb = []
          }
          /*
          if (this.db_datcoc === 1)
          {
            if(this.dsTraTruoc.length <= 0)
            {
              this.$toast.error('Không tìm thấy thông tin đặt cọc trả trước không thể gạch')
              return
            }

            if(this.$refs.gridTraTruoc.getSelectedRecords().length <= 0)
            {
              this.$toast.error('Bạn chưa chọn thông tin tiền trả trước cần trừ')
              return
            }
            postData.ds = this.$refs.gridTraTruoc.getSelectedRecords()
          }
          */

        } else
        {
          let selectedrows = this.$refs.gridKhoanNoTheoTB.getSelectedRecords();  // Get the selected row indexes.
          /*
          if(selectedrows.length <= 0) {
            this.$toast.error('Chọn thuê bao để thanh toán')
            return false
          }
          */
          //let postData = []
          //console.log(selectedrows)
          if(selectedrows.length > 0)
          {
            selectedrows.forEach(item => {
              postData.vsochukylchon = postData.vsochukylchon + 1
              postData.cacmatb.push(item.MA_TB)
              //postData.push({chukyno: '', ma_tb: item.MA_TB})

              if(postData.matb_hienthi === '')
                postData.matb_hienthi = item.MA_TB

            })
          }
          

          if(postData.matb_hienthi === '')
          {
            postData.cacchukyno = []
            postData.cacmatb = []
            postData.cacthangno = []
          }
          //EventBus.$emit('acceptChuKyNo', postData)
        }

        EventBus.$emit('acceptChuKyNo', postData)

        this.$refs.dlgCacKhoanNo.hide()
      },
      rowChange(e)
      {
        /*
        console.log(e)
        if(this.params.p_chk_chonthang === '1')
        {
          console.log(this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances)
          if(this.f_check)
            return
          let ckn = e.data.CHUKYNO
          let arr = []
          this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
            if(item.CHUKYNO === ckn)
            {
              arr.push(index)
            }
          })
          console.log(arr)
          this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances.selectRows(arr)
          this.f_check = true
        }
        */
      },
      activeRowKyHD (args){
        this.rowActiveKyHDHandler(this.pageinfoKyHD.currentRow )
        /*
        this.rowActiveKyHDHandler(parseInt(e.rowIndex + 1))
        if(this.params.p_chk_chonthang === '1')
        {
          let checked = false
          if(e.row.ariaSelected !== null && e.row.ariaSelected === "true")
            checked = true

          checked = !checked
          console.log(checked)
          if(checked){
            let arr = []
            this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
              if(item.CHUKYNO === e.rowData.CHUKYNO)
              {
                arr.push(index)
                //console.log(index)
                //this.$refs.gridKhoanNoTheoKyHD.ej2Instances.selectRow(index, false)
              }
            })
            this.$refs.gridKhoanNoTheoKyHD.ej2Instances.selectRows(arr)
          } else
          {
            /*
            let arr = []
            this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
              if(item.CHUKYNO === e.rowData.CHUKYNO)
              {
                arr.push(index)
                //console.log(item)
                //this.$refs.gridKhoanNoTheoKyHD.ej2Instances.selectRow(index, false)

              }

            })
            this.$refs.gridKhoanNoTheoKyHD.ej2Instances.selectRows(arr, false)

           this.$refs.gridKhoanNoTheoKyHD.ej2Instances.clearSelection()
          }
        }
        */
      },
      rowActiveKyHDHandler(e){
        this.pageinfoKyHD.currentRow = e
      },
      onCheckedKyHD(items)
      {
        this.selectedTotal = 0
        if(items.length > 0)
        {
          items.forEach((item) => {
            this.selectedTotal = this.selectedTotal + item.TONGNO
          })
        }
      },
      recordClickKyHD(e)
      {
        let selectedIndexes = this.$refs.gridKhoanNoTheoKyHD.$refs.grid.getSelectedRowIndexes()
        console.log(e, selectedIndexes)
        if(this.params.p_chk_chonthang === '1')
        {
          let checked = false
          let selected = this.$refs.gridKhoanNoTheoKyHD.getSelectedRecords()
          if(selected.length > 0)
          {
            selected.forEach((item) => {
              if(item.CHUKYNO === e.rowData.CHUKYNO && item.MA_TB === e.rowData.MA_TB)
                checked = true
            })
          }
          checked = !checked
          //checked = e.target.classList.contains('e-check') || e.target.classList.contains('e-uncheck')
          //console.log(e)
          let arr = []
          console.log(checked)
          if(checked)
          {
            let group = []
            this.dsKhoanNoTheoKyHD.forEach((item, index) => {
              if(item.CHUKYNO === e.rowData.CHUKYNO)
              {
                if(selectedIndexes.length <= 0 || !selectedIndexes.includes(index))
                  selectedIndexes.push(index)
              }
            })
          }
          this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances.clearSelection()
          this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances.selectRows(selectedIndexes)
        }
      },
      recordClickKyHDOld(e)
      {

        let selectedIndexes = this.$refs.gridKhoanNoTheoKyHD.$refs.grid.getSelectedRowIndexes()
        e.cancel = true
        if(this.params.p_chk_chonthang === '1')
        {
          console.log(this.$refs.gridKhoanNoTheoKyHD.getSelectedRecords())
          //if click vào check box
          let checked = false
          let selected = this.$refs.gridKhoanNoTheoKyHD.getSelectedRecords()
          if(selected.length > 0)
          {
            selected.forEach((item) => {
              if(item.CHUKYNO === e.rowData.CHUKYNO && item.MA_TB === e.rowData.MA_TB)
                checked = true
            })
          }
          checked = !checked
          //checked = e.target.classList.contains('e-check') || e.target.classList.contains('e-uncheck')
          //console.log(e)
          let arr = []
          if(checked)
          {
            let group = []
            this.dsKhoanNoTheoKyHD.forEach((item, index) => {
              if(item.CHUKYNO === e.rowData.CHUKYNO)
              {
                if(selectedIndexes.length <= 0 || !selectedIndexes.includes(index))
                  selectedIndexes.push(index)
              }
            })
            //let index = this.$refs.gridKhoanNoTheoKyHD.$refs.grid.getRowIndexByPrimaryKey(e.rowData)
            console.log(selectedIndexes)
            //group
            this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances.clearSelection()
            //this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances.selectRows(selectedIndexes)
          } else
          {

          }
        }

      },
      recordClick(e){
        if(this.params.p_chk_chonthang === '1')
        {
          //e.cancel = true
          //console.log(e)
          let checked = false
          //console.log(e.row.ariaSelected)
          if(e.row.ariaSelected !== null && e.row.ariaSelected === "true")
            checked = true

          checked = !checked

          if(checked){
            let arr = []
            this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
              if(item.CHUKYNO === e.rowData.CHUKYNO)
              {
                arr.push(index)
                //console.log(index)
                //this.$refs.gridKhoanNoTheoKyHD.ej2Instances.selectRow(index, false)
              }
            })
            console.log(arr)
            this.$refs.gridKhoanNoTheoKyHD.ej2Instances.selectRows(arr, true)
          } else
          {
            let arr = []
            this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
              if(item.CHUKYNO === e.rowData.CHUKYNO)
              {
                arr.push(index)
                //console.log(item)
                //this.$refs.gridKhoanNoTheoKyHD.ej2Instances.selectRow(index, false)

              }

            })
            this.$refs.gridKhoanNoTheoKyHD.ej2Instances.selectRows(arr, true)
          }
          /*
          checked = !checked
          //alert(checked)
          if(checked)
            this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
              if(item.CHUKYNO === e.rowData.CHUKYNO)
              {
                this.$refs.gridKhoanNoTheoKyHD.ej2Instances.selectRow(index)
              }
            })
          else
            this.$refs.gridKhoanNoTheoKyHD.ej2Instances.clearSelection()

          if(false)//e.target.classList.contains('e-check'))
          {
            alert('click check')
            let selected = this.$refs.gridKhoanNoTheoKyHD.ej2Instances.getSelectedRecords()
            let checked = false
            if(selected.length > 0 )
            {
              selected.forEach( (item, index) => {
                if(item.CHUKYNO === e.rowData.CHUKYNO && item.MA_TB === e.rowData.MA_TB)
                  checked = true
              })
             let ret = selected.filter(x => x.CHUKYNO === e.rowData.CHUKYNO && x.MA_TB === e.rowData.MA_TB)
             if(ret.length > 0)
              checked = true
            }
            console.log(checked)
            this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
              if(item.CHUKYNO === e.rowData.CHUKYNO)
              {
                //console.log(index)
                if(!checked)
                  this.$refs.gridKhoanNoTheoKyHD.ej2Instances.selectRow(index)
              }
            })

            let checked = (e.cell.ariaSelected === undefined || e.cell.ariaSelected === null) ? false : true
            console.log(checked)
            this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
              if(item.CHUKYNO === e.rowData.CHUKYNO)
              {
                //console.log(index)
                if(!checked)
                  this.$refs.gridKhoanNoTheoKyHD.ej2Instances.selectRow(index)
              }
            })

          }
          */
        }

      },
      onChangeDichVu(e){
        console.log(e)
        this.total = 0
        if(this.dsKhoanNoTheoKyHD.length <= 0)
          return

        //let gridObj = this.$refs.gridKhoanNoTheoKyHD.$refs.grid.ej2Instances
        let arr = []
        if(e.value === 0) // tất cả
        {
          //gridObj.clearSelection()
          arr = []
          this.selectedTotal = 0
          this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
            this.total = this.total + item.TONGNO
            this.selectedTotal = this.selectedTotal + item.TONGNO
            if(item.TONGNO !== 0)
            {
              //gridObj.selectRow(index, false)
              if(this.vkieutra === 1)
              {
                if(item.MA_TB === this.ma_tb)
                  item._CHECK = true
                else
                  item._CHECK = false
              }
              else
                item._CHECK = true
            } else
              item._CHECK = false

            arr.push(item)

          })

        } else if(e.value === 1)  // di động
        {
          //gridObj.clearSelection()
          arr = []
          this.selectedTotal = 0

          this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
            this.total = this.total + item.TONGNO

            if(item.TONGNO !== 0 && item.DICHVUVT_ID === DichVuVienThong.DI_DONG)
            {
              this.selectedTotal = this.selectedTotal + item.TONGNO
              item._CHECK = true
            } else
              item._CHECK = false

            arr.push(item)

          })
        } else if(e.value === 2) // khác di động
        {
          //gridObj.clearSelection()
          arr = []
          this.selectedTotal = 0

          this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
            this.total = this.total + item.TONGNO
            if(item.TONGNO !== 0 && item.DICHVUVT_ID !== DichVuVienThong.DI_DONG)
            {
              this.selectedTotal = this.selectedTotal + item.TONGNO
              item._CHECK = true
            } else
            {
              item._CHECK = false
            }

            arr.push(item)

          })
        }

        this.dsKhoanNoTheoKyHD = [...arr]
      },

      actionCompleteKyHD: function(args) {
        if(args.requestType == 'refresh' && this.dsKhoanNoTheoKyHD.length > 0)
        {
          //this.rowActiveKyHDHandler(this.pageinfoKyHD.currentRow)
        } else if(args.requestType == "filtering" && this.$refs.gridKhoanNoTheoKyHD.getFilteredRecords().length > 0)
        {
          this.rowActiveKyHDHandler(1)
        }
      },
      actionCompleteTB: function(args) {
        console.log(args)
        
        if(args.requestType === 'refresh')
        {
          if(this.dsKhoanNoTheoTB.length <= 0)
            return
          let gridObj = this.$refs.gridKhoanNoTheoTB.$refs.grid.ej2Instances
          let arr = []

          gridObj.clearSelection()
          arr = []
          this.dsKhoanNoTheoTB.forEach( (item, index) => {
            if(item.THUCNO !== 0)
              //gridObj.selectRow(index, false)
              arr.push(index)
          })

          gridObj.selectRows(arr)
        }

      },
      dataBoundTB(args)
      {
        console.log(args)
        if(this.dsKhoanNoTheoTB.length <= 0)
          return
        let gridObj = this.$refs.gridKhoanNoTheoTB.$refs.grid.ej2Instances
        let arr = []

        gridObj.clearSelection()
        arr = []
        this.dsKhoanNoTheoTB.forEach( (item, index) => {
          if(item.THUCNO !== 0)
            //gridObj.selectRow(index, false)
            arr.push(index)
        })

        gridObj.selectRows(arr)
      },
      checkAllHandler(e)
      {
        //this.isChecked = e
        console.log(e)

        let arr = []
        this.total = 0
        this.selectedTotal = 0



        this.dsKhoanNoTheoKyHD.forEach( (item, index) => {
          this.total = this.total + item.TONGNO
          if(e === true)
            this.selectedTotal = this.selectedTotal + item.TONGNO
          item._CHECK = e
          arr.push(item)
          this.$refs.gridKhoanNoTheoKyHD.ej2Instances.updateRow(item)
        })

        //this.$refs.gridKhoanNoTheoKyHD.ej2Instances.dataSource = arr
        /*

        for(let i = 0; i < this.dsKhoanNoTheoKyHD.length; i++)
        {
          this.total = this.total + this.dsKhoanNoTheoKyHD[i].TONGNO
          if(e === true)
            this.selectedTotal = this.selectedTotal + this.dsKhoanNoTheoKyHD[i].TONGNO
          this.dsKhoanNoTheoKyHD[i]._CHECK = e
        }
        */


        //console.log(this.$refs.gridKhoanNoTheoKyHD.ej2Instances)
        //this.$refs.gridKhoanNoTheoKyHD.ej2Instances.refresh()
        //this.dsKhoanNoTheoKyHD = [...arr]
        return false
      },
      queryCellInfoKyHD(args)
      {
        
        if (args.column.field === 'TAMTHU')
        {
          //console.log(args)
          //args.cell.classList.add('grid-tamthu');
          if(args.data.TAMTHU < 0)
            args.data.TAMTHU = 0 - args.data.TAMTHU
        }
      },
    },
    provide: {
      grid: [Page, Edit]
    },
    watch : {
      currentRowKyHD : {
        handler: function(new_value, old_value) {

          let newItem = this.$refs.gridKhoanNoTheoKyHD.getRowByIndex(new_value - 1)
          let oldItem = this.$refs.gridKhoanNoTheoKyHD.getRowByIndex(old_value - 1)
          // console.log(newItem)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined)
            oldItem.classList.remove('e-focus')

          // EventBus.$emit('rowActive', { currentRow : parseInt(args.rowIndex + 1), data : this.items[new_value]})
        }
      },
    }
  })
</script>
