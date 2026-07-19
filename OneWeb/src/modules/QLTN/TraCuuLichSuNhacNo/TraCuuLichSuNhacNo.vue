<template src='./TraCuuLichSuNhacNo.html'></template>
<script>
import Vue from 'vue'
import moment from 'moment'
import breadcrumb from '@/components/breadcrumb'
import KyCuoc from '@/components/KyCuoc'
import { GridPlugin, Filter, Resize, Selection, Group} from "@syncfusion/ej2-vue-grids"
import RemotePagination from '@/components/RemotePagination'
import { TabPlugin } from '@syncfusion/ej2-vue-navigations'
import { Tab, TabComponent, SelectEventArgs } from '@syncfusion/ej2-navigations'
import { enableRipple, createElement } from '@syncfusion/ej2-base'
import KetQuaTraCuu from './components/KetQuaTraCuu'
import TraCuuAPI from '../api/TraCuuAPI'
import EventBus from '@/utils/eventBus'

Vue.use(TabPlugin);
Vue.use(GridPlugin)

export default Vue.extend({
  components : { breadcrumb,
    appKyCuoc: KyCuoc,
    appRemotePagination : RemotePagination,
    KetQuaTraCuu},
  created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
    this.contentVal[0] = {
      data : []
    }
  },
  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },
  data () {
    return {
      header: {
        title: "TRA CỨU LỊCH SỬ NHẮC NỢ",
        list: [
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          { name: "Quản lý thu nợ", link: { name: "Ui.buttons" } },
          { name: "Tra cứu xử lý thuê bao", link: { name: "Ui.buttons" } },
          { name: "Tra cứu lịch sử nhắc nợ", link: { name: "Ui.buttons" } },
        ],
      },
      templateContent: function(data, total, index) {
        return () => {
          return {
            template: {
              extends: KetQuaTraCuu,
              propsData: {
                items: data,
                totalItems: total,
                id: 'tab_grid_' + index
              }
            }
          };
        };
      },
      p_kycuoc : null,
      contentVal: [],
      p_active_index: 0,
      totalRecords: 0,
      p_somay_acc: null,
      totalItems: 0,
      headerText: { text: "Kết quả", iconCss: "e-twitter" }
    }
  },
  methods : {
    tabSelected: function(args) {

      let total = document.querySelectorAll('#custom_tab .e-toolbar-item').length

      if(total <= 0 )
      {
        this.p_active_index = 0
        return false
      } else
      {
        let id = document.querySelectorAll('#custom_tab .e-toolbar-item.e-active')[0].id
        this.p_active_index = parseInt(id.substring(18, id.length)) + 1
      }



      /*
      if (args.selectedIndex === document.querySelectorAll('#dynamic_tab .e-toolbar-item').length - 1) {
          document.getElementById('tab-title').value = '';
          document.getElementById('tab-content').value = '';
      }
      */

    },
    tabRemoved: function(args){
      // console.log(args)
    },
    validateData : function () {
      let error = []
      if(this.p_somay_acc === null
        || this.p_somay_acc.trim() === '')
      {
        error.push('Trường Số máy/ Account bắt buộc nhập dữ liệu')
        this.$refs.p_somay_acc.focus()
      }
      if(this.p_kycuoc === null)
        error.push('Trường kỳ hóa đơn, Chu kỳ bắt buộc phải chọn')
      if(error.length > 0){
        this.$toast.error(error.join('<BR>'))
        return true
      }
      this.p_somay_acc = this.p_somay_acc.trim()
      return false
    },
    doSearch: function(e) {

      if(this.validateData())
        return

      let postData = {
        kyCuoc : this.p_kycuoc,
        maTB: this.p_somay_acc
      }

      this.loading(true)
      let results = []

      TraCuuAPI.getTraCuuLichSuNhacNo(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
                  && response.data.data !== undefined
                  && response.data.data.length > 0
              )
          {
            results = response.data.data
            /*
            pageinfo = {
              page : response.data.data.page + 1, // base 1
              maxSize: response.data.data.maxSize,
              totalElement: response.data.data.totalElement,
              totalPages: response.data.data.totalPages,
              sort: response.data.data.sort,
              propertiesSort: response.data.data.propertiesSort
            }
            */
            let total = document.querySelectorAll('#custom_tab .e-toolbar-item').length + 1
            this.totalItems = this.totalItems + 1
            let title = 'Kết quả ' + total;
            var tabObj = this.$refs.tabTraCuuLichSuNhacNo.ej2Instances;
            //console.log(this.totalItems)
            let item =  { header: { text: title }, content: this.templateContent(results, results.length, this.totalItems), id : this.totalItems }
            // console.log(total)
            /*
            if(total <= 0)
              tabObj.addTab([item])
            else
              tabObj.addTab([item], total + 1)
            tabObj.select(total + 2)
              */
            tabObj.addTab([item])
          } else {
            this.$toast.error('Không tìm thấy kết quả.')
          }
        }
      )
      .catch(error => {
        // console.log(error)
      })
      .finally(() => {
        this.loading(false)
      })

    },
    exportExcel(dataSource){
      if(this.p_active_index === null || this.p_active_index === 0) {
        this.$toast.error('Không tìm thấy dữ liệu để xuất excel.')
        return
      }
      /*
      var grid = document.getElementById("tab-grid-" + this.p_active_index).ej2_instances[0];
      grid.excelExport()
      */
      // this.$refs.dsKetQuaTraCuu.excelExport()
      //console.log(document.getElementById("tab_grid_" + this.p_active_index))
      //console.log(this.$refs.tabTraCuuLichSuNhacNo.ej2Instances)
      let id = document.getElementById("tab_grid_" + this.p_active_index).querySelectorAll('[role="grid"]')[0].id;

      var grid = document.getElementById(id).ej2_instances[0].vueInstance;
      //console.log(document.getElementById(id).ej2_instances)
      grid.excelExport()
      //this.$refs["tab-grid-" + this.p_active_index].$refs.grid.excelExport()
    },
    chukycuocChangeHandler(e) {
      if(e.p_chuky_no !== null)
        this.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      else
        this.p_kycuoc = null
    }
  }
})
</script>
