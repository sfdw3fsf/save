<template>
  <div class="table-content">
    <DataGrid3
      v-bind:columns="loai_tien==1?columnsDieuChinh:columnsDieuChinh_USD"
      v-bind:aggregates="loai_tien==1?sumColumnsDieuChinh:sumColumnsDieuChinh_USD"
      v-bind:dataSource="$props.dsDieuChinh"
      :showFilter="true"
      :allowPaging="false"
      :enablePagingServer="false"
      :editSettings="editSettings"
      :columnCheckboxFreeze="false"
      @actionComplete="actionComplete"
      @headerCellInfo="dcHeaderCellInfo"
      @queryCellInfo="queryCellInfo"
      ref="gridDieuChinh"
      >
    </DataGrid3>
  </div>
</template>

<script>
import Vue from 'vue'

import EventBus from '@/utils/eventBus'
import { Tooltip } from '@syncfusion/ej2-popups'

export default Vue.extend({
  components: { },
  props: {
    dsDieuChinh: {
      type: Array
    },
    loai_tien: {
      type: Number,
      default: 1 // Tiền VNĐ
    }
  },
  computed: {
    
  },
  
updated(){
  console.log('loaitien',this.loai_tien);
},
  data () {
    return {
      footerSum: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<span>{{data.Sum}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      columnsDieuChinh: [
        {field: 'KHOANMUCTT_ID', isPrimaryKey: true, visible: false},
        {headerText: 'Điều chỉnh',
          columns: [
            {
              field: 'TENKHOANMUC',
              headerText: 'Khoản mục',
              headerTextAlign: 'Center',
              width: 200,
              textAlign: 'Left',
              minWidth: 100,
              allowEditing: false,
              freeze: 'Left'
            },
            {
              field: 'DC_NOGOC',
              headerText: 'Nợ gốc',
              headerTextAlign: 'Center',
              width: 120,
              textAlign: 'Right',
              minWidth: 100,
              allowFiltering: true,
              format: 'N0',
              type: 'number',
              allowEditing: true,
              editType: 'numericedit',
              validationRules: this.editRules,
              freeze: 'Left'
            },
            {
              field: 'DC_THUE',
              headerText: 'Thuế',
              headerTextAlign: 'Center',
              width: 120,
              textAlign: 'Right',
              minWidth: 100,
              allowFiltering: true,
              format: 'N0',
              type: 'number',
              allowEditing: true,
              editType: 'numericedit',
              validationRules: this.editRules,
              freeze: 'Left'
            },
            {
              field: 'DC_HOAHONG',
              headerText: 'Hoa hồng',
              headerTextAlign: 'Center',
              width: 120,
              textAlign: 'Right',
              minWidth: 100,
              allowFiltering: true,
              format: 'N0',
              type: 'number',
              allowEditing: true,
              editType: 'numericedit',
              validationRules: this.editRules,
              freeze: 'Left'
            },
            {
              field: 'DC_TONG',
              headerText: 'Tổng cộng',
              headerTextAlign: 'Center',
              width: 120,
              textAlign: 'Right',
              minWidth: 100,
              allowFiltering: true,
              format: 'N0',
              type: 'number',
              allowEditing: false,
              freeze: 'Left'
            }
          ]
        },
        {headerText: 'Tổng nợ',
          columns: [
            {
              field: 'NOGOC',
              headerText: 'Nợ gốc',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            },

            {
              field: 'THUE',
              headerText: 'Thuế',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'HOAHONG',
              headerText: 'Hoa hồng',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'TONGNO',
              headerText: 'Tổng nợ',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            }

          ]
        },
        {headerText: 'Nợ đầu kỳ',
          columns: [
            {
              field: 'NOGOC_DK',
              headerText: 'Nợ gốc',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'THUE_DK',
              headerText: 'Thuế',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'HOAHONG_DK',
              headerText: 'Hoa hồng',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'TONGNO_DK',
              headerText: 'Tổng nợ',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            }
          ]
        },
        {headerText: 'Nợ phát sinh',
          columns: [
            {
              field: 'NOGOC_PS',
              headerText: 'Nợ gốc',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'THUE_PS',
              headerText: 'Thuế',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'HOAHONG_PS',
              headerText: 'Hoa hồng',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'TONGNO_PS',
              headerText: 'Tổng nợ',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N0',
              type: 'number',
              allowEditing: false
            }
          ]
        }
      ],
      columnsDieuChinh_USD: [
        {field: 'KHOANMUCTT_ID', isPrimaryKey: true, visible: false},
        {headerText: 'Điều chỉnh',
          columns: [
            {
              field: 'TENKHOANMUC',
              headerText: 'Khoản mục',
              headerTextAlign: 'Center',
              width: 200,
              textAlign: 'Left',
              minWidth: 100,
              allowEditing: false,
              freeze: 'Left'
            },
            {
              field: 'DC_NOGOC',
              headerText: 'Nợ gốc',
              headerTextAlign: 'Center',
              width: 120,
              textAlign: 'Right',
              minWidth: 100,
              allowFiltering: true,
              format: 'N2',
              type: 'number',
              allowEditing: true,
              editType: 'numericedit',
              validationRules: this.editRules,
              freeze: 'Left'
            },
            {
              field: 'DC_THUE',
              headerText: 'Thuế',
              headerTextAlign: 'Center',
              width: 120,
              textAlign: 'Right',
              minWidth: 100,
              allowFiltering: true,
              format: 'N2',
              type: 'number',
              allowEditing: true,
              editType: 'numericedit',
              validationRules: this.editRules,
              freeze: 'Left'
            },
            {
              field: 'DC_HOAHONG',
              headerText: 'Hoa hồng',
              headerTextAlign: 'Center',
              width: 120,
              textAlign: 'Right',
              minWidth: 100,
              allowFiltering: true,
              format: 'N2',
              type: 'number',
              allowEditing: true,
              editType: 'numericedit',
              validationRules: this.editRules,
              freeze: 'Left'
            },
            {
              field: 'DC_TONG',
              headerText: 'Tổng cộng',
              headerTextAlign: 'Center',
              width: 120,
              textAlign: 'Right',
              minWidth: 100,
              allowFiltering: true,
              format: 'N2',
              type: 'number',
              allowEditing: false,
              freeze: 'Left'
            }
          ]
        },
        {headerText: 'Tổng nợ',
          columns: [
            {
              field: 'NOGOC',
              headerText: 'Nợ gốc',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            },

            {
              field: 'THUE',
              headerText: 'Thuế',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'HOAHONG',
              headerText: 'Hoa hồng',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'TONGNO',
              headerText: 'Tổng nợ',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            }

          ]
        },
        {headerText: 'Nợ đầu kỳ',
          columns: [
            {
              field: 'NOGOC_DK',
              headerText: 'Nợ gốc',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'THUE_DK',
              headerText: 'Thuế',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'HOAHONG_DK',
              headerText: 'Hoa hồng',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'TONGNO_DK',
              headerText: 'Tổng nợ',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            }
          ]
        },
        {headerText: 'Nợ phát sinh',
          columns: [
            {
              field: 'NOGOC_PS',
              headerText: 'Nợ gốc',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'THUE_PS',
              headerText: 'Thuế',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'HOAHONG_PS',
              headerText: 'Hoa hồng',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            },
            {
              field: 'TONGNO_PS',
              headerText: 'Tổng nợ',
              headerTextAlign: 'Center',
              width: 150,
              textAlign: 'Right',
              minWidth: 100,
              format: 'N2',
              type: 'number',
              allowEditing: false
            }
          ]
        }
      ],
      sumColumnsDieuChinh: [
        {field: 'DC_NOGOC', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'DC_THUE', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'DC_HOAHONG', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'DC_TONG', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'NOGOC', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'THUE', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'HOAHONG', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGNO', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'NOGOC_DK', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'THUE_DK', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'HOAHONG_DK', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGNO_DK', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'NOGOC_PS', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'THUE_PS', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'HOAHONG_PS', type: 'Sum', format: 'N0', footerTemplate: this.footerSum},
        {field: 'TONGNO_PS', type: 'Sum', format: 'N0', footerTemplate: this.footerSum}
      ],
      sumColumnsDieuChinh_USD: [
        {field: 'DC_NOGOC', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'DC_THUE', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'DC_HOAHONG', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'DC_TONG', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'NOGOC', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'THUE', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'HOAHONG', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TONGNO', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'NOGOC_DK', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'THUE_DK', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'HOAHONG_DK', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TONGNO_DK', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'NOGOC_PS', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'THUE_PS', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'HOAHONG_PS', type: 'Sum', format: 'N2', footerTemplate: this.footerSum},
        {field: 'TONGNO_PS', type: 'Sum', format: 'N2', footerTemplate: this.footerSum}
      ],
      pageinfoDieuChinh: {
        page: 1,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      editSettings: { allowEditing: true, allowAdding: false, allowDeleting: false, mode: 'Normal' },
      filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true },
      editRules: { required: true },
      selectedRecord: null
    }
  },
  methods: {
    actionComplete: function (args) {
      if (args.requestType === 'beginEdit') {

      }
      if (args.requestType === 'save' && args.action === 'edit') {
        let data = args.data

        EventBus.$emit('changeDSDieuChinh', data)
      }
    },
    actionCompleteDieuChinh: function (args) {
      // console.log(args)
      if ((args.requestType === 'refresh' && this.dsDieuChinh.length > 0) ||
          (args.requestType === 'filtering' && this.$refs.gridDSDieuChinh.getFilteredRecords().length > 0)) {
        this.rowActiveDieuChinhHandler(1)
      }
      if (args.requestType === 'save' && args.action === 'edit') {
        this.selectedRecord = {}
        this.selectedRecord = args.data
        this.changeAfterEdit()

        let ds = this.$refs.gridDSDieuChinh.ej2Instances.dataSource

        let grandTotalDC = 0
        // console.log(ds)
        ds.forEach((item, index) => {
          let totalDC = 0
          totalDC = parseInt(item.DC_NOGOC) + parseInt(item.DC_THUE) + parseInt(item.DC_HOAHONG)
          ds[index].DC_TONG = totalDC
          grandTotalDC += totalDC
        })
        // console.log(grandTotalDC)
        EventBus.$emit('tongDieuChinhChange', grandTotalDC)
        /*
          let gridObj = this.$refs.gridDSDieuChinh.ej2Instances;
          let ds = gridObj.dataSource
          let tongConNo = 0
          ds.forEach( (item, index) => {
            tongConNo += parseInt(item.DC_NOGOC)
          })
          */
      }
    },
    dcHeaderCellInfo (args) {
      if (args.cell.column.field === 'DC_NOGOC') {
        new Tooltip ({ content: 'Số tiền khách hàng sẽ trả' }, args.node)
      }
    },
    queryCellInfo (args) {
      // console.log(args)
    }
  },
  provide: {
  },
  watch: {
  }
})
</script>
