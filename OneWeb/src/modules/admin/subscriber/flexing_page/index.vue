<template>
  <div class="page-content">
    123
    <DDataGrid
      ref="grid"
      :columns="grid.header"
      :dataSource="grid.list"
      :editSettings="{ allowEditing: true, mode: 'Batch' }"
      :showColumnCheckbox="true"
      :enabledSelectFirstRow="false"
      :allowPaging="true"
      :enablePagingServer="false"
      :allowExcelExport="true"
      :pageSize="500"
      :selectionSettings="{ enableToggle: false, checkboxOnly: true, persistSelection: true }"
    />
    <!--
                        :pageIndex="gridChungTu.page"
        :totalRecords="gridVatTu.list.length"
                @selectedItemsChanged="gridVatTuSelectedItemsChanged"
                @selectedRowChanged="selected_gridVatTuRow"
                @cellSaved="gridViewVatTu_ValidateRow"
                @dataBound="gridVatTuDataBound" -->
    <ejs-grid
      ref="originGrid"
      :dataSource="originGrid.list"
      height="300"
      :loadingIndicator="{ indicatorType: 'Shimmer' }"
      :enableInfiniteScrolling="true"
      :allowPaging="true"
      :showColumnCheckbox="true"
      :enablePersistence="true"
      @rowSelected="originGridRowSelected"
      :pageSettings="{ currentPage: 1, pageSize: 100, pageSizes: [5, 10, 15, 20], pageCount: 5 }"
      :selectionSettings="{ mode: 'Both', checkboxOnly: false, persistSelection: true }"
    >
      <e-columns>
        <e-column type="checkbox" width="50"></e-column>
        <e-column field="TaskID" headerText="Task ID" textAlign="Right" width="70" :isPrimaryKey="true"></e-column>
        <e-column field="Engineer" width="100"></e-column>
        <e-column field="Designation" width="100"></e-column>
        <e-column field="Estimation" textAlign="Right" width="100"></e-column>
        <e-column field="Status" width="100"></e-column>
      </e-columns>
    </ejs-grid>
    <button @click="clickSelectRow">click to row</button>
  </div>
</template>
<script>
import Vue from 'vue'
import { DDataGrid } from './components/common'
import { GridPlugin, Page, InfiniteScroll } from '@syncfusion/ej2-vue-grids'

Vue.use(GridPlugin)
export default {
  components: { DDataGrid },
  provide: {
    grid: [Page, InfiniteScroll]
  },
  data() {
    return {
      model: this.value,
      originGrid: {
        list: []
      },
      grid: {
        header: [
          //   { width: 40, headerText: '', template: GridChungTuStatusTemplate(this), allowFiltering: true, allowSorting: false, freeze: 'Left' },
          { width: 160,fieldName: 'TRANGTHAI', headerText: 'Trạng thái', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SOPHIEUCHA', headerText: 'Số phiếu cha', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_CT', headerText: 'Ngày yêu cầu', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYNHAN_TT', headerText: 'Ngày nhận', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYGUI_TT', headerText: 'Ngày xuất', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_HT', headerText: 'Ngày HT', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TIEN', headerText: 'Tiền', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true, allowSorting: true },
          { fieldName: 'KHO_GIAO', headerText: 'Từ kho', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'KHO_NHAN', headerText: 'Đến kho', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'MUCDICH', headerText: 'Mục đích', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'ND_GIAO', headerText: 'Nội dung', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGUOI_CN', headerText: 'Người lập', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGUOI_TH', headerText: 'Người TH', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGUOI_HT', headerText: 'Người HT', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGUOI_CTKT', headerText: 'Người CTKT', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          //TODO: tag=3 moi hien
          { fieldName: 'MAPHIEU_DK', headerText: 'Mã phiếu', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_VB', headerText: 'Ngày VB', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'SO_VB', headerText: 'Lệnh VB', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'PHIEU_KT_ID', headerText: 'Phiếu KT', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYCT_KT', headerText: 'Ngày Chuyển', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_XD', headerText: 'Ngày duyệt', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGUOI_XD', headerText: 'Người duyệt', textAlign: 'Left', allowFiltering: true, allowSorting: true }
        ],
        list: []
      }
    }
  },
  computed: {},
  async mounted() {
    let originList = []
    for (let i = 0; i < 10000; i++) {
      this.grid.list = [...this.grid.list, {}]
      originList.push({ TaskID: i + 1 })
    }
    this.originGrid.list = originList
  },
  watch: {
    value(val) {
      this.model = val
    }
  },
  methods: {
    clickSelectRow() {
      //   console.log(this.$refs.originGrid)
      //   this.$refs.originGrid.selectRows([1, 3], false)
      //   let pageSettings = this.$refs.originGrid.pageSettings
      //   console.log(pageSettings)
      //   console.log(this.$refs.originGrid.ej2Instances.properties.pageSettings)
      let records = this.$refs.originGrid.getSelectedRecords()
      console.log(records)
      console.log(this.$refs.originGrid.getSelectedRows())
    },
    originGridRowSelected(e) {
      console.log('originGridRowSelected')
      console.log(e)
    }
  }
}
</script>
<style scoped></style>
