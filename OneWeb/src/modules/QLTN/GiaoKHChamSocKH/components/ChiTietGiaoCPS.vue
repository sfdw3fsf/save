<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='configs.animationSettings'
    ref="dlgChiTietGiaoCPS"
    :header='"Chi tiết giao kế hoạch thu cước phát sinh"'
    showCloseIcon=true
    width='1200px'
    height='700'
    :target='configs.target'
    :close="beforeDialogClose"
    :open="beforeDialogOpen"
    >
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" class="pointer" v-on:click.prevent="exportExcel()">
              <span class="icon one-xlsx-import"></span>Xuất Excel
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
          <div class="legend-title">
            <div class="title pull-left">Danh sách chi tiết</div>
            <div class="clearfix"></div>
          </div>
          <DataGrid
            v-bind:columns="[
              {fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, width: 120},
              {fieldName: 'TEN_TT', headerText: 'Tên TT', allowFiltering: true, width: 200},
              {fieldName: 'DIACHI_TT', headerText: 'Địa chỉ TT', allowFiltering: true, width: 250},
              {fieldName: 'TIENNO', headerText: 'Tiền nợ', allowFiltering: true, width: 100, format: 'N0', type: 'number', textAlign: 'Right'},
              {fieldName: 'DONVI_CPS', headerText: 'Loại ĐV giao', allowFiltering: true, width: 250},
              {fieldName: 'NHANVIEN_CPS', headerText: 'NV giao CPS', allowFiltering: true, width: 150},
              {fieldName: 'NGAY_GIAO', headerText: 'Ngày giao', allowFiltering: true, width: 100, textAlign: 'Right'},
            ]"
            v-bind:dataSource="dsChiTietGiaoCPS"
            :showFilter="true"
            :allowPaging="false"
            :allowExcelExport="true"
            :showColumnCheckbox="false"
            :enabledSelectFirstRow="false"
            :key="keyDSChiTietGiaoCPS"
            ref="gridDSChiTietGiaoCPS"
            >
          </DataGrid>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import GiaoKHChamSocKHAPI from '../../api/GiaoKHChamSocKH.js'

Vue.use(DialogPlugin)


export default Vue.extend({
  components : { },
  props: {
    kehoach_id: {
      type: Number,
      required: false
    }
  },
  computed : {
  },
  data () {
    return {
      configs:{
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' }
      },
      params: {

      },
      dsChiTietGiaoCPS: [],
      keyDSChiTietGiaoCPS: 1,
    }
  },
  methods :
  {
    /* Button actions */

    exportExcel() {

      if(this.dsChiTietGiaoCPS.length === 0)
      {
        this.$toast.error('Không có dữ liệu để xuất excel.')
        return
      }
      let excelExportProperties = {
          dataSource: this.dsChiTietGiaoCPS
      };
      this.$refs.gridDSChiTietGiaoCPS.excelExport(excelExportProperties)
    },


    HienThiDanhSach()
    {

      this.loading(true)
      GiaoKHChamSocKHAPI.getChiTietKeHoachCPS(this.axios, this.kehoach_id)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.dsChiTietGiaoCPS = res.data.data
            this.keyDSChiTietGiaoCPS++
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    openDialog()
    {
      this.$refs.dlgChiTietGiaoCPS.show()
    },
    beforeDialogOpen()
    {
      this.HienThiDanhSach()
    },
    beforeDialogClose()
    {

    },
    closeDialog()
    {
      this.$refs.dlgChiTietGiaoCPS.hide()
    },

  },
  provide: {
  },
  watch: {
  }
})
</script>
