<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgDanhSachPhieuThu"
    :header='"Danh sách phiếu thu"'
    showCloseIcon=true
    width='80%'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    
  </ejs-dialog>
</template>
<script>
import Vue from "vue"
import { mapActions, mapGetters } from 'vuex'
import { GridPlugin, Filter, Resize, ExcelExport, Sort} from "@syncfusion/ej2-vue-grids"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import RemotePagination from '@/components/RemotePagination'
import EventBus from '@/utils/eventBus'
import GachNoAPI from '../../api/GachNoAPI'

Vue.use(GridPlugin)
Vue.use(DialogPlugin)

 export default Vue.extend({
    components : {
    },
    props: {
      ma_tt: {
        type: String,
        required: true
      },
      kycuoc: {
        type: String,
        required: true
      },
    },
    computed () {
    },
    async created () {

    },
    destroyed () {
    },
    data () {
      return {
        groupOptions: {
          columns: ["CHUKYNO"]
        },
        selectionOptions: { type: 'Multiple', checkboxOnly: true},
        pageOptions: { pageSize: 10, pageCount: 5, pageSizes: true, totalRecordCount : 1000},
        filterOptions: { enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
        templateOptions : {
          create: function (args) {
            if(args.column.field === 'STT')
              return '<span class="fa"></span>'
            else if(args.column.field === 'CHECKBOX'){
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
                $this.$refs.gridDSTreoNo.filterByColumn(args.target.id, 'contains', value, 'AND', false)
              else
              {
                let arrFilter = []
                $this.$refs.gridDSTreoNo.clearFiltering(new Array(args.target.id))
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
        fieldsDichVu: {text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
        params: {
          p_dichvu_options: [
              {DICHVUVT_ID: 0, TEN_DVVT: "Tất cả"},
              {DICHVUVT_ID: 1, TEN_DVVT: "Di động"},
              {DICHVUVT_ID: 2, TEN_DVVT: "Dịch vụ khác di động"},
            ],
          p_dichvu: 0
        },
        dsKhoanNoTheoTB : [],
        dsKhoanNoTheoKyHD : [],
        pageinfoThang : {
          page: 1,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' }
      }
    },
    methods : {
      async getTongNoTheoKyHoaDon() {
        try{
          let postData = {
            pKyCuoc: this.kycuoc,
            pMaTT: this.ma_tt
          }
          const results = await GachNoAPI.getTongNoTheoKyHoaDon(this.axios, postData)
          return results
        }catch(err) {
          console.log(err)
        }
      },
      openDialog() {
        this.$refs.dlgDanhSachPhieuThu.show()
      },
      closeDialog() {
        this.$refs.dlgDanhSachPhieuThu.hide()
      },
      onDialogClose() {
        this.clearForm()
        this.clearResult()
      },
      onDialogOpen() {
        this.getTongNoTheoKyHoaDon().then(
          (response) => {
            if(response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined)
            {
              this.dsKhoanNoTheoKyHD = response.data.data
            }
          }
        )
      },
      clearForm() {

      },
      clearResult() {

      },
      activeRowThang (args){
        // EventBus.$emit('rowActive', parseInt(args.rowIndex + 1))
        this.activeRowThangHandler(parseInt(args.rowIndex + 1))
      },
      actionCompleteThang: function(args) {
        if((args.requestType == 'refresh' && this.dsKhoanNoTheoKyHD.length > 0)
          || (args.requestType == "filtering" && this.$refs.gridThangNo.getFilteredRecords().length > 0))
        {
          this.activeRowThangHandler(1)
        }
      },
      activeRowThangHandler(index){
        this.pageinfoThang.currentRow = index
      },
      doAccept: function(){
        let selectedrows = this.$refs.gridThangNo.getSelectedRecords();
        if(selectedrows.length <= 0) {
          this.$toast.error('Chọn chu kỳ thanh toán')
          return false
        }

        let arrChuKyNo = []
        let arrTB = []
        let arr = []
        selectedrows.forEach( item => {
          //arrChuKyNo.push(item.CHUKYNO)
          //arrTB.push(item.MA_TB)
          arr.push({ chukyno: item.CHUKYNO, ma_tb: item.MA_TB})
        })

        //EventBus.$emit('acceptChuKyNo', {ds_chukyno: arrChuKyNo.join(","), ds_ma_tb: arrTB.join(",")})
        EventBus.$emit('acceptChuKyNo', arr)
        this.$refs.dlgCacKhoanNo.hide()
      }
    },
    provide: {
      grid: [Filter, Resize, Sort, ExcelExport]
    },
    watch : {
        /*
      currentRowThang : {
        handler: function(new_value, old_value) {

          let newItem = this.$refs.gridThangNo.getRowByIndex(new_value - 1)
          let oldItem = this.$refs.gridThangNo.getRowByIndex(old_value - 1)
          // console.log(newItem)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined)
            oldItem.classList.remove('e-focus')

        }
      },
      */
    }
  })
</script>
