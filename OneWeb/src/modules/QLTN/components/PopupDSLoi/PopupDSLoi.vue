<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='animationSettings'
    ref="dlgDSLoi"
    :header='"Danh sách lỗi"'
    showCloseIcon=true
    width='900px'
    :target='target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)" @click="exportExcel()">
              <span class="icon one-export"></span> Xuất excel
            </a>
          </li>
          <li>
            <a href="javascript: void(0)" @click="closeDialog()">
              <span class="icon one-export"></span> Thoát
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="table-content">
            <DataGrid
              v-bind:columns="[
                {fieldName: 'STT', headerText: 'STT', textAlign: 'Center', allowFiltering: false, width: 50},
                {fieldName: 'MA_TT', headerText: 'Mã thanh toán', textAlign: 'Left', allowFiltering: true, width: 120},
                {fieldName: 'LOI', headerText: 'Lỗi', textAlign: 'Left', allowFiltering: true, width: 250}
                ]"
              v-bind:dataSource="ds_loi"
              :showFilter="false"
              :allowPaging="true"
              :enablePagingServer="false"
              :allowExcelExport="true"
              ref="gridDSLoi"
              >
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Vue from "vue"
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

Vue.use(DialogPlugin)

 export default Vue.extend({
    components : {
    },
    props: {
      ds_loi: {
        type: Array,
        required: false
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
        params:{
        },
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' }
      }
    },
    methods : {
      exportExcel()
      {
        let excelExportProperties = {
          dataSource: this.ds_loi
        };
        this.$refs.gridDSLoi.excelExport(excelExportProperties)
      },
      openDialog() {
        this.$refs.dlgDSLoi.show()
      },
      closeDialog() {
        this.$refs.dlgDSLoi.hide()
      },
      onDialogClose() {
        this.clearForm()
        this.clearResult()
      },
      onDialogOpen() {
      },
      clearForm() {

      },
      clearResult() {

      },
    }
  })
</script>
