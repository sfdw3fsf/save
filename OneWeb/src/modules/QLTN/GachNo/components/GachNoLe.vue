<template>
  <div class="tab-gachle">
    <div class="nav tabs tab-right">
        <a href="#tabThangNo" @click="onTabLoaiGachChange(2)" :class="{active: currentTab === 2}" data-toggle="tab" v-if="f_show_tab(2)" title="Gạch nợ theo tháng">Tháng nợ (F2)</a>
        <a href="#tabKhoanNo" @click="onTabLoaiGachChange(1)" :class="{active: currentTab === 1}" data-toggle="tab" v-if="f_show_tab(1)" title="Gạch nợ theo khoản nợ">Khoản nợ (F3)</a>
        <a href="#tabDichVu" @click="onTabLoaiGachChange(3)" :class="{active: currentTab === 3}" data-toggle="tab" v-if="f_show_tab(3)" title="Gạch nợ theo dịch vụ">Dịch vụ (F4)</a>
    </div>
    <div class="tab-content" style="padding:0!important">
      <div id="tabThangNo" class="tab-pane" :class="{active: currentTab === 2}" v-if="f_show_tab(2)">
        <DataGrid
          v-bind:columns="loai_tien==1?columnsThangNo:columnsThangNo_USD"
          v-bind:aggregates="loai_tien==1?[
            {field: 'TONGNO', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
            //{field: 'CONNO', type: 'Custom', footerTemplate: this.footerSumConNo, customAggregateFn: this.connoThangNoAggregateFn},
            { field: 'CONNO', type: 'Sum', format: 'N0', footerTemplate: this.footerSumConNo},
            {field: 'DATRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
            {field: 'TAMTHU', type: 'Sum', format: 'N0', footerTemplate: this.footerSumTamThu}
          ]:[
            {field: 'TONGNO', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
            //{field: 'CONNO', type: 'Custom', footerTemplate: this.footerSumConNo, customAggregateFn: this.connoThangNoAggregateFn},
            { field: 'CONNO', type: 'Sum', format: 'N2', footerTemplate: this.footerSumConNo},
            {field: 'DATRA', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
            {field: 'TAMTHU', type: 'Sum', format: 'N2', footerTemplate: this.footerSumTamThu}
          ]"
          v-bind:dataSource="dataThangNo"
          :showFilter="true"
          :allowPaging="false"
          :enablePagingServer="false"
          :editSettings="editSettings"
          :key="keyThangNo"
          :enabledSelectFirstRow="false"
          @queryCellInfo="queryCellInfoThangNo"
          @actionComplete="actionCompleteThangNo"
          @load="onLoadThangNo"
          @headerCellInfo="thangnoHeaderCellInfo"
          @keyPressed="thangnoKeyPressHandler"
          @rowSelected="thangnoRowSelectedHandler"
          :panelDataHeight='"300"'
          ref="gridThangNo"
          >
        </DataGrid>
      </div>
      <div id="tabKhoanNo" class="tab-pane" :class="{active: currentTab === 1}" v-if="f_show_tab(1)">
        <DataGrid3
          v-bind:columns="loai_tien==1?columnsKhoanMuc:columnsKhoanMuc_USD"
          v-bind:aggregates="loai_tien==1?[
            {field: 'TONGNOTHUCTE', type: 'Sum', format: 'N0', footerTemplate: this.footerSumTamThu},
            {field: 'TONGSETRADK', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
            {field: 'TONGSETRAPS', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
            {field: 'TONGSEPHAITRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSumConNo},
            {field: 'TONGTHUCTRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
            {field: 'TONGTHUCDC', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
            //{field: 'TONG_TAMTHU', type: 'Sum', format: 'N0', footerTemplate: this.footerSumTamThu},
            {field: 'TONG_TAMTHU', type: 'Custom', footerTemplate: this.sumTamThuKhoanNo, customAggregateFn: this.sumTamThuKhoanNoAggFn},
          ]:[
            {field: 'TONGNOTHUCTE', type: 'Sum', format: 'N2', footerTemplate: this.footerSumTamThu},
            {field: 'TONGSETRADK', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
            {field: 'TONGSETRAPS', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
            {field: 'TONGSEPHAITRA', type: 'Sum', format: 'N2', footerTemplate: this.footerSumConNo},
            {field: 'TONGTHUCTRA', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
            {field: 'TONGTHUCDC', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
            //{field: 'TONG_TAMTHU', type: 'Sum', format: 'N0', footerTemplate: this.footerSumTamThu},
            {field: 'TONG_TAMTHU', type: 'Custom', footerTemplate: this.sumTamThuKhoanNo, customAggregateFn: this.sumTamThuKhoanNoAggFn},
          ]"
          v-bind:dataSource="dataKhoanMuc"
          :showFilter="true"
          :allowPaging="false"
          :allowSorting="false"
          :enablePagingServer="false"
          :editSettings="editSettings"
          :key="keyKhoanMuc"
          :enabledSelectFirstRow="false"
          @actionComplete="actionComplete"
          @queryCellInfo="queryCellInfoKhoanMuc"
          @load="onLoadKhoanMuc"
          @keyPressed="khoannoKeyPressHandler"
          @rowSelected="khoannoRowSelectedHandler"
          :panelDataHeight='"300"'
          ref="gridKhoanMuc"
          >
        </DataGrid3>
      </div>
      <div id="tabDichVu" class="tab-pane" :class="{active: currentTab === 3}" v-if="f_show_tab(3)">
      <DataGrid
          v-bind:columns="loai_tien==1?columnsDichVu:columnsDichVu_USD"
          v-bind:aggregates="loai_tien==1?sumColumnsDichVu:sumColumnsDichVu_USD"
          v-bind:dataSource="dataDichVu"
          :showFilter="true"
          :allowPaging="false"
          :enablePagingServer="false"
          :editSettings="editSettings"
          :key="keyDichVu"
          :enabledSelectFirstRow="false"
          @actionComplete="actionCompleteDichVu"
          @queryCellInfo="queryCellInfoDichVu"
          @load="onLoadDichVu"
          :panelDataHeight='"300"'
          ref="gridDichVu"
          >
        </DataGrid>
      </div>
    </div>
</div>
</template>

<script>
import Vue from 'vue'
import {
  NHOM_HTTT
} from '@/const/enums'

import EventBus from '@/utils/eventBus'
import { Tooltip } from '@syncfusion/ej2-popups'
import {cloneArray} from '@/utils/util'
import { currency } from '@/filters/currency'

export default Vue.extend({
  components: { },
  props: {
    dsThangNo: {
      type: Array
    },
    dsKhoanMuc: {
      type: Array
    },
    dsDichVu: {
      type: Array
    },
    nhom_httt: {
      type: Number
    },
    loai_gach: {
      type: Number,
      default: 2 // gạch theo tháng nợ
    },
    loai_tien: {
      type: Number,
      default: 1 // Tiền VNĐ
    }
  },
  computed: {
  },
  created () {
    this.currentTab = this.loai_gach
    if (this.nhom_httt === 12) {
      let f = this.columnsThangNo.find(x => x.fieldName === 'TIEN_VI')
      if (f !== undefined) f.visible = true
    }
    console.log('loai tien',this.loai_tien)
  },
  data () {
    // const $this = this
    return {
      footerSum: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<span>{{data.Sum}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      footerSumConNo: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<span class="grid-conno">{{data.Sum}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      footerSumTamThu: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<span class="grid-conno">{{data.Sum}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      sumTamThuKhoanNo: function () {
        return { template: Vue.component('footerTemplate', {
          template: `<span class="grid-conno">{{data.Custom}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      customTemplate: function () {
        return { template: Vue.component('footerTemplate', {
          template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
          data () { return { data: {data: {}} } }
        })
        }
      },
      /*
        footerSumConNo: function () {
          return  { template : Vue.component('footerTemplate', {
            template: `<span class="grid-conno">{{data.Custom}}</span>`,
            data () {return { data: {}};}
            })
          }
        },
        */
      editSettings: { allowEditing: true, allowAdding: false, allowDeleting: false, mode: 'Inline' },
      editRules: { required: true },
      selectionOptions: { type: 'Multiple', checkboxOnly: true },
      columnsThangNo: [
        {fieldName: 'STT', visible: false, isPrimaryKey: true},
        // {fieldName: 'CHUKYNO', visible: false, isPrimaryKey: true},
        {fieldName: 'THANGNO', headerText: 'Tháng nợ', allowFiltering: true, allowEditing: false},
        {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, allowEditing: false},
        {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowEditing: false},
        {fieldName: 'TONGNO', headerText: 'Tổng nợ', allowFiltering: true, format:'N0', type: 'number', allowEditing: false},
        {fieldName: 'CONNO', headerText: 'Còn nợ', allowFiltering: true, format: 'N0', type: 'number', allowEditing: true, editType: 'numericedit', validationRules: this.editRules},
        {fieldName: 'DATRA', headerText: 'Đã trả', allowFiltering: true, format: 'N0', type: 'number'},
        {fieldName: 'TAMTHU', headerText: 'Tạm thu', allowFiltering: true, format: 'N0', type: 'number'},
        {fieldName: 'TIEN_VI', headerText: 'Tiền trả trước', allowFiltering: true, format: 'N0', type: 'number', visible: false},
      ],
      columnsThangNo_USD: [
        {fieldName: 'STT', visible: false, isPrimaryKey: true},
        // {fieldName: 'CHUKYNO', visible: false, isPrimaryKey: true},
        {fieldName: 'THANGNO', headerText: 'Tháng nợ', allowFiltering: true, allowEditing: false},
        {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, allowEditing: false},
        {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowEditing: false},
        {fieldName: 'TONGNO', headerText: 'Tổng nợ', allowFiltering: true, format:'N2', type: 'number', allowEditing: false},
        {fieldName: 'CONNO', headerText: 'Còn nợ', allowFiltering: true, format: 'N2', type: 'number', allowEditing: true, editType: 'numericedit', validationRules: this.editRules},
        {fieldName: 'DATRA', headerText: 'Đã trả', allowFiltering: true, format: 'N2', type: 'number'},
        {fieldName: 'TAMTHU', headerText: 'Tạm thu', allowFiltering: true, format: 'N2', type: 'number'},
        {fieldName: 'TIEN_VI', headerText: 'Tiền trả trước', allowFiltering: true, format: 'N2', type: 'number', visible: false},
      ],
      sumColumnsThangNo: [
        {field: 'TONGNO', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        // {field: 'CONNO', type: 'Custom', footerTemplate: this.footerSumConNo, customAggregateFn: this.connoThangNoAggregateFn},
        { field: 'CONNO', type: 'Custom', format: 'N0', footerTemplate: this.customTemplate, customAggregateFn: this.customAggregate },
        {field: 'DATRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TAMTHU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
      ],
      sumColumnsThangNo_USD: [
        {field: 'TONGNO', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        // {field: 'CONNO', type: 'Custom', footerTemplate: this.footerSumConNo, customAggregateFn: this.connoThangNoAggregateFn},
        { field: 'CONNO', type: 'Custom', format: 'N2', footerTemplate: this.customTemplate, customAggregateFn: this.customAggregate },
        {field: 'DATRA', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TAMTHU', type: 'Sum', format: 'N2', footerTemplate: this.footerSum}
      ],

      pageinfoThangNo: {
        page: 1,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: -1
      },
      columnsKhoanMuc: [
        {fieldName: 'KHOANMUCTT_ID', visible: false, isPrimaryKey: true},
        {fieldName: 'MA_TB', visible: false, isPrimaryKey: true},
        {fieldName: 'MA_TT', visible: false, isPrimaryKey: true},
        {fieldName: 'TENKHOANMUC', headerText: 'Khoản mục', allowFiltering: true, allowEditing: false},
        {fieldName: 'TONGNOTHUCTE', headerText: 'Tổng nợ', allowFiltering: true, format: 'N0', type: 'number', allowEditing: false},
        {fieldName: 'TONGSETRADK', headerText: 'Cộng nợ ĐK', allowFiltering: true, format: 'N0', type: 'number', allowEditing: false},
        {fieldName: 'TONGSETRAPS', headerText: 'Cộng nợ PS', allowFiltering: true, format: 'N0', type: 'number', allowEditing: true, editType: 'numericedit', validationRules: this.editRules},
        {fieldName: 'TONGSEPHAITRA', headerText: 'Còn nợ', allowFiltering: true, format: 'N0', type: 'number', allowEditing: true, editType: 'numericedit', validationRules: this.editRules},
        {fieldName: 'TONGTHUCTRA', headerText: 'Tổng đã trả', allowFiltering: true, format: 'N0', type: 'number'},
        {fieldName: 'TONGTHUCDC', headerText: 'Đã điều chỉnh', allowFiltering: true, format: 'N0', type: 'number'},
        {fieldName: 'TONG_TAMTHU', headerText: 'Tổng tạm thu', allowFiltering: true, format: 'N0', type: 'number'}
      ],
      sumColumnsKhoanMuc: [
        {field: 'TONGNOTHUCTE', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGSETRADK', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGSETRAPS', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGSEPHAITRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGTHUCTRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGTHUCDC', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONG_TAMTHU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
      ],
      columnsKhoanMuc_USD: [
        {fieldName: 'KHOANMUCTT_ID', visible: false, isPrimaryKey: true},
        {fieldName: 'MA_TB', visible: false, isPrimaryKey: true},
        {fieldName: 'MA_TT', visible: false, isPrimaryKey: true},
        {fieldName: 'TENKHOANMUC', headerText: 'Khoản mục', allowFiltering: true, allowEditing: false},
        {fieldName: 'TONGNOTHUCTE', headerText: 'Tổng nợ', allowFiltering: true, format: 'N2', type: 'number', allowEditing: false},
        {fieldName: 'TONGSETRADK', headerText: 'Cộng nợ ĐK', allowFiltering: true, format: 'N2', type: 'number', allowEditing: false},
        {fieldName: 'TONGSETRAPS', headerText: 'Cộng nợ PS', allowFiltering: true, format: 'N2', type: 'number', allowEditing: true, editType: 'numericedit', validationRules: this.editRules},
        {fieldName: 'TONGSEPHAITRA', headerText: 'Còn nợ', allowFiltering: true, format: 'N2', type: 'number', allowEditing: true, editType: 'numericedit', validationRules: this.editRules},
        {fieldName: 'TONGTHUCTRA', headerText: 'Tổng đã trả', allowFiltering: true, format: 'N2', type: 'number'},
        {fieldName: 'TONGTHUCDC', headerText: 'Đã điều chỉnh', allowFiltering: true, format: 'N2', type: 'number'},
        {fieldName: 'TONG_TAMTHU', headerText: 'Tổng tạm thu', allowFiltering: true, format: 'N2', type: 'number'}
      ],
      sumColumnsKhoanMuc_USD: [
        {field: 'TONGNOTHUCTE', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TONGSETRADK', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TONGSETRAPS', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TONGSEPHAITRA', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TONGTHUCTRA', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TONGTHUCDC', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TONG_TAMTHU', type: 'Sum', format: 'N2', footerTemplate: this.footerSum}
      ],
      pageinfoKhoanMuc: {
        page: 1,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: -1
      },
      columnsDichVu: [
        {fieldName: 'DICHVUVT_ID', visible: false, isPrimaryKey: true},
        {headerText: 'Thanh toán',
          columns: [
            {
              field: 'TEN_DVVT',
              headerText: 'Dịch vụ',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Left',
              minWidth: 100,
              allowEditing: false
            },
            {
              field: 'TIENTRA',
              headerText: 'Tiền trả',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              allowFiltering: true,
              format: 'N0',
              type: 'number',
              allowEditing: true,
              editType: 'numericedit',
              validationRules: this.editRules
            },
            {
              field: 'TAMTHU',
              headerText: 'Tạm thu',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              allowEditing: false
            }
          ]
        },
        {headerText: 'Tổng nợ',
          columns: [
            {
              field: 'TONGNO',
              headerText: 'Tổng nợ',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              allowEditing: false
            },
            {
              field: 'NODK',
              headerText: 'Nợ ĐK',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              allowEditing: false
            },
            {
              field: 'NOPS',
              headerText: 'Nợ PS',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              allowEditing: false
            }
          ]
        },
        
        {headerText: 'Tổng trả',
          columns: [
            {
              field: 'TONGTRA',
              headerText: 'Tổng trả',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              allowEditing: false
            },
            {
              field: 'TRADK',
              headerText: 'Trả ĐK',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              allowEditing: false
            },
            {
              field: 'TRAPS',
              headerText: 'Trả PS',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              allowEditing: false
            }
          ]
        }
      ],
      columnsDichVu_USD: [
        {fieldName: 'DICHVUVT_ID', visible: false, isPrimaryKey: true},
        {headerText: 'Thanh toán',
          columns: [
            {
              field: 'TEN_DVVT',
              headerText: 'Dịch vụ',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Left',
              minWidth: 100,
              allowEditing: false
            },
            {
              field: 'TIENTRA',
              headerText: 'Tiền trả',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              allowFiltering: true,
              format: 'N2',
              type: 'number',
              allowEditing: true,
              editType: 'numericedit',
              validationRules: this.editRules
            },
            {
              field: 'TAMTHU',
              headerText: 'Tạm thu',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              allowEditing: false
            }
          ]
        },
        {headerText: 'Tổng nợ',
          columns: [
            {
              field: 'TONGNO',
              headerText: 'Tổng nợ',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              allowEditing: false
            },
            {
              field: 'NODK',
              headerText: 'Nợ ĐK',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              allowEditing: false
            },
            {
              field: 'NOPS',
              headerText: 'Nợ PS',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              allowEditing: false
            }
          ]
        },
        
        {headerText: 'Tổng trả',
          columns: [
            {
              field: 'TONGTRA',
              headerText: 'Tổng trả',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              allowEditing: false
            },
            {
              field: 'TRADK',
              headerText: 'Trả ĐK',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              allowEditing: false
            },
            {
              field: 'TRAPS',
              headerText: 'Trả PS',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              allowEditing: false
            }
          ]
        }
      ],
      sumColumnsDichVu: [
        {field: 'TIENTRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TAMTHU', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGNO', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'NODK', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'NOPS', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGTRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TIENTRA', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TRADK', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TRAPS', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
      ],
      sumColumnsDichVu_USD: [
        {field: 'TIENTRA', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TAMTHU', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TONGNO', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'NODK', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'NOPS', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TONGTRA', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TIENTRA', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TRADK', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TRAPS', type: 'Sum', format: 'N2', footerTemplate: this.footerSum}
      ],
      pageinfoDichVu: {
        page: 1,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: -1
      },
      selectedRecord: null,
      currentTab: 2,
      dataKhoanMuc: [],
      dataThangNo: [],
      dataDichVu: [],
      keyThangNo: 0,
      keyKhoanMuc: 0,
      keyDichVu: 0,
      sumTTKhoanNo: 0,
      rowFocus: -1
    }
  },
  methods: {
    f_show_tab: function (tab) {
      if
      (tab === 1) { // tab khoản mục
        if (this.nhom_httt === NHOM_HTTT.GACHNO_DATCOC) return false
        return true
      } else if (tab === 2) { // tab tháng nợ
        return true
      } else { // tab dịch vụ
        if (this.nhom_httt === NHOM_HTTT.GACHNO_DATCOC) return false
        return true
      }
    },
    sumTamThuKhoanNoAggFn () {
      return currency(this.sumTTKhoanNo)
    },
    customAggregate () {
      return 0 // currency(this.common_params.tienTra_Checked) + '/' + currency(this.common_params.tienTra_Sum);
    },
    connoThangNoAggregateFn (data) {
      // console.log(data)
      return 0// data.result.filter((item) => item.ShipCountry === 'Brazil').length;
    },
    onTabLoaiGachChange (args) {
      this.currentTab = args
      EventBus.$emit('tabGachLeKieuGach', args)
    },
    getLoaigachNo () {
      return this.currentTab
    },
    actionCompleteThangNo: function (args) {
      if (args.requestType === 'save' && args.action === 'edit') {
        this.pageinfoThangNo.currentRow = args.rowIndex

        let data = args.data
        this.$emit('changeDSGachNoLe', data)
      }

      if (args.requestType === 'refresh' && this.pageinfoThangNo.currentRow >= 0) {
        this.$refs.gridThangNo.selectRow(this.pageinfoThangNo.currentRow)
      }
    },
    actionComplete: function (args) {
      if (args.requestType === 'save' && args.action === 'edit') {
        this.pageinfoKhoanMuc.currentRow = args.rowIndex

        let data = args.data

        if (args.data.TONGSEPHAITRA !== args.rowData.TONGSEPHAITRA) {
          // data.TONGSEPHAITRA = args.rowData.TONGSEPHAITRA
          data.TONGSETRAPS = args.data.TONGSEPHAITRA - args.data.TONGSETRADK
        }
        // EventBus.$emit('changeDSGachNoLe', data)
        this.$emit('changeDSGachNoLe', data)
      }

      if (args.requestType === 'refresh' && this.pageinfoKhoanMuc.currentRow >= 0) {
        this.$refs.gridKhoanMuc.selectRow(this.pageinfoKhoanMuc.currentRow)
      }
    },
    actionCompleteDichVu: function (args) {
      if (args.requestType === 'save' && args.action === 'edit') {
        this.pageinfoDichVu.currentRow = args.rowIndex

        let data = args.data

        this.$emit('changeDSGachNoLe', data)
      }

      if (args.requestType === 'refresh' && this.pageinfoDichVu.currentRow >= 0) {
        this.$refs.gridDichVu.selectRow(this.pageinfoDichVu.currentRow)
      }
    },
    getSelectedIds () {
      let selectedrows = this.$refs.gridDSTreoNo.getSelectedRecords() // Get the selected row indexes.
      let a = selectedrows.map(item => (item.THUEBAO_ID))
      return a
    },

    queryCellInfoThangNo (args) {
      // console.log(args.column.field)
      if (args.column.field === 'CONNO') {
        args.cell.classList.add('grid-conno')
      } else if (args.column.field === 'TAMTHU') {
        args.cell.classList.add('grid-tamthu')
      }
    },
    queryCellInfoKhoanMuc (args) {
      // console.log(args.column.field)
      if (args.column.field === 'TONGSEPHAITRA') {
        args.cell.classList.add('grid-conno')
      } else if (args.column.field === 'TONG_TAMTHU' ||
          args.column.field === 'TONGNOTHUCTE') {
        args.cell.classList.add('grid-tamthu')
      }
    },
    queryCellInfoDichVu (args) {
      /*
        console.log(args.column.field)
        if(args.column.field === 'CONNO')
        {
          args.cell.classList.add('grid-conno');
        } else if (args.column.field === 'TAMTHU')
        {
          args.cell.classList.add('grid-tamthu');
        }
        */
    },
    onLoadKhoanMuc (e) {
      this.$refs.gridKhoanMuc.$refs.grid.ej2Instances.element.addEventListener('mouseup', function (e) {
        var instance = this.ej2_instances[0]
        if (e.target.classList.contains('e-rowcell')) {
          if (instance.isEdit) instance.endEdit()
          let index = parseInt(e.target.getAttribute('Index'))
          instance.selectRow(index)
          instance.startEdit()
        };
      })
    },
    onLoadThangNo (e) {
      this.$refs.gridThangNo.$refs.grid.ej2Instances.element.addEventListener('mouseup', function (e) {
        var instance = this.ej2_instances[0]
        if (e.target.classList.contains('e-rowcell')) {
          if (instance.isEdit) instance.endEdit()
          let index = parseInt(e.target.getAttribute('Index'))
          instance.selectRow(index)
          instance.startEdit()
        };
      })
    },
    onLoadDichVu (e) {
      this.$refs.gridDichVu.$refs.grid.ej2Instances.element.addEventListener('mouseup', function (e) {
        var instance = this.ej2_instances[0]
        if (e.target.classList.contains('e-rowcell')) {
          if (instance.isEdit) instance.endEdit()
          let index = parseInt(e.target.getAttribute('Index'))
          instance.selectRow(index)
          instance.startEdit()
        };
      })
    },
    thangnoHeaderCellInfo (args) {
      if (args.cell.column.field === 'CONNO') {
        new Tooltip({ content: 'Số tiền khách hàng sẽ trả' }, args.node)
      }
    },
    thangnoRowSelectedHandler (args) {
      // console.log(args)
      // console.log('row index: ' + args.row.getAttribute('aria-rowindex'))

      let index = this.dataThangNo.findIndex(x => x.THANGNO === args.data.THANGNO && x.DICHVUVT_ID === args.data.DICHVUVT_ID && x.MA_TT === args.data.MA_TT && x.MA_TB === args.data.MA_TB)

      this.rowFocus = -1
      if (index > -1) this.rowFocus = index
      else console.log('Not found')
    },
    thangnoKeyPressHandler (args) {
      let key = args.key.toUpperCase()

      switch (key) {
        case 'DELETE' :
          // console.log(args)
          args.target.ej2_instances[0].value = 0
          // dsThangNo[this.rowFocus]
          break
        case 'INSERT' :
          args.target.ej2_instances[0].value = this.dsThangNo[this.rowFocus].CONNO
          break
      }
    },
    khoannoRowSelectedHandler (args) {

      let index = this.dataThangNo.findIndex(x => x.KHOANMUCTT_ID === args.data.KHOANMUCTT_ID)

      this.rowFocus = -1
      if (index > -1) this.rowFocus = index
      else console.log('Not found')
    },
    khoannoKeyPressHandler (args) {
      let key = args.key.toUpperCase()
      switch (key) {
        case 'DELETE' :
          // console.log(args)
          args.target.ej2_instances[0].value = 0
          // dsThangNo[this.rowFocus]
          break
        case 'INSERT' :
          args.target.ej2_instances[0].value = this.dsThangNo[this.rowFocus].CONNO
          break
      }
    }
  },
  watch: {
    loai_gach: {
      handler: function (newValue, oldValue) {
        this.currentTab = newValue
      }
    },
    dsKhoanMuc: {
      handler: function (newValue, oldValue) {
        // this.dataKhoanMuc = new_value.map(o => ({...o}))
        // this.dataKhoanMuc = []
        this.keyKhoanMuc++
        this.sumTTKhoanNo = 0

        let arr = []
        if (newValue.length > 0) {
          newValue.forEach((item, index) => {
            if (this.nhom_httt === NHOM_HTTT.HOANTRA) {
              if ("3130,5102".includes(item.KHOANMUCTT_ID)) {
                let t = {...item}
                t.STT = index
                arr.push(t)
                this.sumTTKhoanNo = item.TONGSETRA
              }
            } else {
              if (!"3130,5102".includes(item.KHOANMUCTT_ID)) {
                let t = {...item}
                t.STT = index
                // if(t.TONG_TAMTHU < 0)
                //  t.TONG_TAMTHU = 0 - t.TONG_TAMTHU
                arr.push(t)
              } else {
                this.sumTTKhoanNo = Math.abs(item.TONGSETRA)// Math.abs(item.TONGSEPHAITRA)
                // console.log(this.sumTTKhoanNo, item.TONGSEPHAITRA)
              }
            }
          })
        }
        // this.dataKhoanMuc = [...arr]
        this.dataKhoanMuc = cloneArray(arr)
      }
    },
    dsThangNo: {
      handler: function (newValue, oldValue) {
        // this.dataThangNo = new_value.map(o => ({...o}))
        // this.dataThangNo = []
        this.keyThangNo++

        let arr = []

        if (newValue.length > 0) {
          newValue.forEach((item, index) => {
            let t = {...item}
            t.STT = index
            // if(t.TAMTHU < 0)
            //  t.TAMTHU = 0 - t.TAMTHU
            arr.push(t)
          })
        }
        this.dataThangNo = [...arr]
      }
    },
    dsDichVu: {
      handler: function (newValue, oldValue) {
        // this.keyDichVu++
        // this.dataDichVu = new_value.map(o => ({...o}))
        this.keyDichVu++

        let arr = []

        if (newValue.length > 0) {
          newValue.forEach((item, index) => {
            let t = {...item}
            t.STT = index
            // if(t.TAMTHU < 0)
            //  t.TAMTHU = 0 - t.TAMTHU
            arr.push(t)
          })
        }
        this.dataDichVu = [...arr]
      }
    }
  }
})
</script>
<style>
  .grid-conno {
    color: red!important;
    font-weight: bold!important;
  }
  .grid-tamthu {
    color: brown!important;
    font-weight: bold!important;
  }
</style>
