<template>
  <div class="row">
    <div class="col-md-12 col-xl-9 col-lg-9 col-12">
      <div class="table-content" id="grid-ds">
        <DataGrid3
          v-bind:columns="columnsDanhSach"
          v-bind:dataSource="$props.dsGachTheoFile"
          v-bind:aggregates="[
            {field: '_CHECK', type: 'Custom', footerTemplate: this.countTemp, customAggregateFn: this.countTTAgg},
            {field: 'TONGNO', type: 'Custom', footerTemplate: this.countTemp, customAggregateFn: this.sumTongNoAgg},
            {field: 'CONLAI', type: 'Custom', footerTemplate: this.countTemp, customAggregateFn: this.sumConNoAgg},
            {field: 'TIENTRA', type: 'Custom', footerTemplate: this.countTemp, customAggregateFn: this.sumTienTraAgg},
            {field: 'TAMTHU', type: 'Custom', footerTemplate: this.countTemp, customAggregateFn: this.sumTamThuAgg},
          ]"
          :showFilter="true"
          :allowPaging="true"
          :enablePagingServer="false"
          :allowExcelExport='true'
          :totalRecords="pageinfoSuccess.totalElement"
          :pageSize="pageinfoSuccess.maxSize"
          :pageIndex="pageinfoSuccess.page"
          :editSettings="editSettings"
          :showColumnCheckbox="false"
          :selectionSettings="selectionSettings"
          :enabledSelectFirstRow="false"
          :columnCheckboxFreeze="false"
          :commands="[
            {name: 'Delete', cssClass: 'btn btn-second pad3 lh14 button-not-bg', width: 20, text: ' X ', title:'Xóa dòng'},
          ]"
          :commandColumn="{headerText: '', width: 50, cellCssClass: 'css1', firstColumn: false, show: true/*, freeze: true*/}"
          :contextMenuItems="contextMenuItems"
          @commandClicked="gridDS_onCommandClicked"
          @actionComplete="actionComplete"
          @selectedRowChanged="rowChanged"
          @contextMenuClick="contextMenuClick"
          @recordClick="recordClickHandler"
          @created="onCreatedHandler"
          @queryCellInfo="queryCellInfo"
          ref="gridGachDanhSach"
          :panelDataHeight='"300"'
          >
        </DataGrid3>
      </div>
    </div>
    <div class="col-md-12 col-xl-3 col-lg-3 col-12">
      <div class="box-form">
        <div class="legend-title">
            <div class="pull-left">
              Danh sách lỗi
            </div>
            <div class="pull-right">
              <a href="#" class="btn btn-main lh14 pad4" title="Xuất excel" v-on:click.prevent="doEportExcelErrorList">
                  <span class="one-excel"></span>
              </a>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="table-content">
          <DataGrid
            v-bind:columns="[
            {fieldName: 'MA_TT', headerText: 'Mã TT', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true},
            {fieldName: 'THONGTINLOI', headerText: 'Thông tin lỗi', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true}
            ]"
          v-bind:dataSource="$props.dsErrors"
          v-bind:aggregates="columnsErrorCount"
          :showFilter="true"
          :allowPaging="true"
          :enablePagingServer="false"
          :allowExcelExport='true'
          :selectionSettings="selectionSettings"
          :panelDataHeight='"270"'
          :totalRecords="pageinfoError.totalElement"
          :pageSize="pageinfoError.maxSize"
          :pageIndex="pageinfoError.page"
          ref="gridGachDanhSachErr"
          >
        </DataGrid>
        </div>
    </div>
    </div>
    <cap-nhat-nhom-c-k ref="refCapNhatNhomCK" :create="f_create" :ds_thanhtoan="ds_thanhtoan"></cap-nhat-nhom-c-k>
  </div>

</template>

<script>
import Vue from 'vue'
import CapNhatNhomCK from './CapNhatNhomCK'
import NhomChuyenKhoanAPI from '../../api/NhomChuyenKhoan'
import { currency } from '@/filters/currency'
export default Vue.extend({
  components: {
    CapNhatNhomCK
  },
  props: {
    dsGachTheoFile: {
      type: Array
    },
    totalItems: {
      type: Number
    },
    dsErrors: {
      type: Array
    },
    totalErrorItems: {
      type: Number
    }
  },
  computed: {
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
      footerCount: function () {
        return { template: Vue.component('countTemplate', {
          template: `<span>{{data.Count}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      pageinfoError: {
        page: 0,
        totalElement: 0,
        pageSize: 10
      },
      pageinfoSuccess: {
        page: 0,
        totalElement: 0,
        pageSize: 10
      },
      editSettings: { allowEditing: true, allowAdding: true, allowDeleting: true, mode: 'Normal' },
      selectionSettings: {checkboxOnly: true, type: 'Multiple'},
      editRules: { required: true },
      contextMenuItems: [
        {text: 'Chọn tất cả', target: '.e-content', id: 'checkAll', iconCss: 'e-icons e-check'},
        {text: 'Bỏ chọn tất cả', target: '.e-content', id: 'uncheckAll'},
        {text: 'Đọc file', target: '.e-content', id: 'readFile'},
        {text: 'Xóa danh sách', target: '.e-content', id: 'clearGrid'},
        {text: 'Xuất file', target: '.e-content', id: 'exportFile'},
        {text: 'Tạo/Cập nhật nhóm', target: '.e-content', id: 'createGroup'}
      ],
      countTemp: function () {
        return { template: Vue.component('footerTemplate', {
          template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
          data () { return { data: { data: {} } } }
        })
        }
      },
      columnsDanhSach: [
        {fieldName: '_CHECK', headerText: '', allowFiltering: false, allowEditing: true, editType: 'booleanedit', displayAsCheckBox: true, type: 'boolean', width: 60, freeze: 'Left'},
        {fieldName: 'STT', visible: false, isPrimaryKey: true},
        {fieldName: 'MA_TT', headerText: 'Mã TT', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 130, allowEditing: false},
        {fieldName: 'MATB_DD', headerText: 'Mã đại diện', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 120, allowEditing: false},
        {fieldName: 'TEN_TT', headerText: 'Tên thanh toán', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 200, allowEditing: false},
        {fieldName: 'TONGNO', headerText: 'Tổng nợ', headerTextAlign: 'Center', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true, width: 140, allowEditing: false},
        {fieldName: 'TIENTRA', headerText: 'Tiền trả', headerTextAlign: 'Center', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true, width: 140, allowEditing: true},
        {fieldName: 'TAMTHU', headerText: 'Tạm thu', headerTextAlign: 'Center', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true, width: 140, allowEditing: false},
        {fieldName: 'CONLAI', headerText: 'Còn lại', headerTextAlign: 'Center', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true, width: 140, allowEditing: false},
        {fieldName: 'THANGTRA', headerText: 'Tháng trả', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 150, allowEditing: false, visible: true},
        {fieldName: 'TEN_KHOANMUC', headerText: 'Khoản mục', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 150, allowEditing: false, visible: true},
        {fieldName: 'GHI_CHU', headerText: 'Ghi chú', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 150, allowEditing: true},
        {fieldName: 'SERI', headerText: 'Seri', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 100, allowEditing: true},
        {fieldName: 'QUYEN', headerText: 'Quyển', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 100, allowEditing: true},
        {fieldName: 'SO_SERI', headerText: 'Số Seri', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 100, allowEditing: true},
        {fieldName: 'CHUNG_TU', headerText: 'Chứng từ', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 140, allowEditing: false},
        {fieldName: 'CLKHOANMUC', visible: false},
        {fieldName: 'DIACHI_TT', visible: false},
        {fieldName: 'DIACHI_BC', visible: false},
        {fieldName: 'MA_TUYEN', visible: false},
        {fieldName: 'MANV_TC', visible: false},
        {fieldName: 'TEN_NV', visible: false}
      ],
      columnsDanhSachSum: [

      ],
      columnsErrorCount: [
        {field: 'TEN_TT', type: 'Count', format: 'N0', footerTemplate: this.footerCount}
      ],
      f_create: true,
      ds_thanhtoan: [],
      keyGachDS: 0
    }
  },
  methods: {
    countTTAgg () {
      let checked = this.dsGachTheoFile.filter(x => x._CHECK === true)
      return currency(checked.length) + '/' + currency(this.dsGachTheoFile.length)
    },
    sumTongNoAgg () {
      let checked = this.dsGachTheoFile.filter(x => x._CHECK === true)
      if (checked.length <= 0) { return 0 }
      let total = checked.reduce((acc, curr) => {
        return Number(curr.TONGNO) + acc
      }, 0)
      let grandTotal = this.dsGachTheoFile.reduce((acc, curr) => {
        return Number(curr.TONGNO) + acc
      }, 0)
      return currency(total) + '/' + currency(grandTotal)
    },
    sumConNoAgg () {
      let checked = this.dsGachTheoFile.filter(x => x._CHECK === true)
      if (checked.length <= 0) { return 0 }
      let total = checked.reduce((acc, curr) => {
        return Number(curr.CONLAI) + acc
      }, 0)
      let grandTotal = this.dsGachTheoFile.reduce((acc, curr) => {
        return Number(curr.CONLAI) + acc
      }, 0)
      return currency(total) + '/' + currency(grandTotal)
    },
    sumTamThuAgg () {
      let checked = this.dsGachTheoFile.filter(x => x._CHECK === true)
      if (checked.length <= 0) { return 0 }
      let total = checked.reduce((acc, curr) => {
        return Number(curr.TAMTHU) + acc
      }, 0)
      let grandTotal = this.dsGachTheoFile.reduce((acc, curr) => {
        return Number(curr.TAMTHU) + acc
      }, 0)
      return currency(total) + '/' + currency(grandTotal)
    },
    sumTienTraAgg () {
      let checked = this.dsGachTheoFile.filter(x => x._CHECK === true)
      if (checked.length <= 0) { return 0 }
      let total = checked.reduce((acc, curr) => {
        return Number(curr.TIENTRA) + acc
      }, 0)
      let grandTotal = this.dsGachTheoFile.reduce((acc, curr) => {
        return Number(curr.TIENTRA) + acc
      }, 0)
      return currency(total) + '/' + currency(grandTotal)
    },
    onCreatedHandler () {
      this.$refs.gridGachDanhSach.$refs.grid.ej2Instances.element.addEventListener(
        'click',
        this.click
      )
    },
    click (e) {
      if (e.target.classList.contains('e-rowcell') && e.target.querySelector('.e-checkbox-wrapper')) {
        if (this.$refs.gridGachDanhSach.$refs.grid.ej2Instances.isEdit) { this.$refs.gridGachDanhSach.$refs.grid.ej2Instances.endEdit() }

        var index = parseInt(e.target.getAttribute('Index'), 10) // row index
        // console.log(e.target.getAttribute("Index"), this.$refs.gridGachDanhSach.$refs.grid.ej2Instances)
        var rowData = this.$refs.gridGachDanhSach.$refs.grid.ej2Instances.currentViewData[index] // row data

        this.$emit('selectRecord', rowData)
      }
    },
    exportExcel: function () {
      if (this.dsGachTheoFile.length <= 0) {
        this.$toast.error('Không có thông tin thanh toán để xuất excel.')
        return
      }
      let excelExportProperties = {
        dataSource: this.dsGachTheoFile,
        fileName: 'dsThanhToan.xlsx'
      }
      this.$refs.gridGachDanhSach.excelExport(excelExportProperties)
    },
    getSelectedRows () {
      return this.$refs.gridGachDanhSach.getSelectedRecords()
    },
    actionComplete: function (args) {
      // console.log(args)
      if (args.requestType === 'beginEdit') {
        // console.log(this.$refs.gridGachDanhSach.$refs.grid.ej2Instances)
      }
      if (args.requestType === 'save' && args.action === 'edit') {
        let data = args.data
        if (data.THANGTRA !== undefined && data.THANGTRA !== null)  data.THANGTRA = Number(data.THANGTRA)
        console.log(data)
        this.$emit('changeDSGachNo', data)
        let gridObj = this.$refs.gridGachDanhSach.$refs.grid.ej2Instances
        gridObj.aggregateModule.refresh(data)
      }
      if (args.requestType === 'refresh' || args.requestType === 'delete' || args.requestType === 'save') {
        // this.refreshDS()
      }
    },
    refreshDS () {
      let gridObj = this.$refs.gridGachDanhSach.$refs.grid.ej2Instances
      if (this.dsGachTheoFile.length <= 0) { return }

      let selectedRows = []

      this.dsGachTheoFile.forEach((item) => {
        if (item._CHECK) {
          let index = gridObj.getRowIndexByPrimaryKey(item.MA_TT)
          if (index > -1) { selectedRows.push(index) }
        }
      })

      gridObj.selectRows(selectedRows)
    },
    rowChanged (e) {
      this.$emit('changeDSGachNoLe', e)
    },
    gridDS_onCommandClicked (name, args) {
      if (name === 'Delete') {
        console.log(args)

        // this.$refs.gridGachDanhSach.$refs.grid.deleteRecord('MA_TT', args)
        // this.$emit('changeDSGachNoLe', args);
        this.$emit('removeTToanGachDanhSach', args)
      }
    },
    contextMenuClick (args) {
      // console.log(args)
      if (args.item.id === 'checkAll') {
        this.$emit('triggerCheckAll')
      } else if (args.item.id === 'uncheckAll') {
        this.$emit('triggerUncheckAll')
      } else if (args.item.id === 'readFile') {
        this.$emit('triggerReadFile')
      } else if (args.item.id === 'clearGrid') {
        this.$emit('triggerClearGrid')
      } else if (args.item.id === 'exportFile') {
        this.$emit('triggerExportFile')
      } else if (args.item.id === 'createGroup') {
        if (this.dsGachTheoFile.length <= 0) {
          this.$toast.error('Không có danh sách mã thanh toán không thể cập nhật nhóm')
          return
        }

        // let selectedRows = this.$refs.gridGachDanhSach.getSelectedRecords()
        let selectedRows = this.dsGachTheoFile.filter(x => x._CHECK === true)
        console.log(selectedRows)
        if (selectedRows.length <= 0) {
          this.$toast.error('Không có danh sách mã thanh toán được chọn không thể cập nhật nhóm')
          return
        }

        const ds = [...new Set(selectedRows.map(item => item.MA_TT))] // [ 'A', 'B']

        this.ds_thanhtoan = [...ds]

        NhomChuyenKhoanAPI.checkDSTheoNhom(this.axios, {
          kieuId: 9,
          dsMaTT: ds
        })
          .then((res) => {
            if (res.data.error_code === 'BSS-00000000' &&
                res.data.data !== undefined &&
                res.data.data.length > 0
            ) {
              let sl = parseInt(res.data.data[0].SOLUONG)
              if (sl > 0) { this.f_create = false } else { this.f_create = true }
              this.$refs.refCapNhatNhomCK.openDialog()
            }
          })
          .catch(error => {
            this.$toast.error(error.data.message_detail)
          })
          .finally(() => {

          })

          // this.ds_thanhtoan = selectedRows.map(x => ({ MA_TT: x.MA_TT, THANHTOAN_ID: x.THANHTOAN_ID }))
          // console.log(this.ds_thanhtoan)
      }
    },
    recordClickHandler (e) {
      if (e === null) {
        return
      }

      let data = e.rowData

      this.$emit('recordClick', data)
    },
    getVisibleColumns () {
      let columns = this.$refs.gridGachDanhSach.$refs.grid.ej2Instances.columnModel
      var dsFiles = this.dsGachTheoFile
      if (dsFiles.length > 0) {
        // region ẩn hiện cột tháng trả
        console.log(columns)
        // let f = columns.filter(x => x.foreignKeyField === 'THANGTRA')
        // console.log(f)

        /*
          if (columns_ds.Contains("THANGTRA") && !dsthang)
          {
              Message_Box.ShowWarning("Bạn chưa chọn tìm theo tháng không thể gạch nợ");
              return;
          }
          else if (dsthang && !columns_ds.Contains("THANGTRA"))
          {
              Message_Box.ShowWarning("Bạn không thể gạch theo tháng nếu danh sách dưới chưa đọc file theo tháng");
              return;
          }
          if (dsthang && columns_ds.Contains("THANGTRA"))
          {
              if (ds_chon.Select("THANGTRA is null").Length > 0)
              {
                  Message_Box.ShowWarning("Bạn chưa chọn tìm theo tháng không thể gạch nợ");
                  return;
              }
              if (ds_file.Select("THANGTRA is null").Length > 0)
              {
                  Message_Box.ShowWarning("Danh sách chưa có mã chưa có tháng trả bạn không thể chọn gạch theo tháng");
                  return;

              }
          }
          if (dsthang)
              gridview_GachDS.Columns["THANGTRA"].Visible = true;
          else
              gridview_GachDS.Columns["THANGTRA"].Visible = false;
            */
        // #endregion
      }
      // return this.$refs.gridGachDanhSach.getColumnVisible()
    },
    queryCellInfo (args) {
      if (args.column.field === 'TONGNO') {
        if (args.data.TONGNO !== args.data.TIENTRA) { args.cell.classList.add('grid-tongno') } else if (args.data.TONGNO === 0) { args.cell.classList.add('grid-tongno') } else { args.cell.classList.remove('grid-tongno') }
      } else if (args.column.field === 'TIENTRA') {
        if (args.data.TONGNO !== args.data.TIENTRA) { args.cell.classList.add('grid-tientra') } else if (args.data.TONGNO === 0) { args.cell.classList.add('grid-tientra') } else { args.cell.classList.remove('grid-tientra') }
      }
    },
    doEportExcelErrorList () {
      if (this.dsErrors.length <= 0) {
        this.$toast.error('Danh sách lỗi không có dữ liệu để xuất file.')
        return
      }
      let excelExportProperties = {
        dataSource: this.dsErrors
      }
      this.$refs.gridGachDanhSachErr.excelExport(excelExportProperties)
    }
  },
  provide: {
  },
  watch: {
    dsErrors: {
      handler: function (newValue, oldValue) {
        this.pageinfoError.totalElement = newValue.length
        this.pageinfoError.page = 0
      }
    },
    dsGachTheoFile: {
      handler: function (newValue, oldValue) {
        this.pageinfoSuccess.totalElement = newValue.length
        this.pageinfoSuccess.page = 0
        this.keyGachDS++
      }
    }
  }
})
</script>

<style>
  .e-check:before{
    content:'\e718';
  }
  #grid-ds .e-summarycell .e-templatecell .e-ellipsistooltip{
    padding: 0px!important;
  }
  .e-grid .e-summaryrow .e-summarycell, .e-grid .e-summaryrow .e-templatecell, .e-grid .e-summarycontent .e-indentcell, .e-grid .e-indentcell.e-detailindentcelltop
  {
    padding-right: 0px!important;
  }
  .grid-tongno {
    background-color: rgb(148, 243, 148);
    font-weight: bold!important;
  }
  .grid-tientra {
    background-color: rgb(148, 243, 148);
    font-weight: bold!important;
    color: red!important;
  }
</style>
