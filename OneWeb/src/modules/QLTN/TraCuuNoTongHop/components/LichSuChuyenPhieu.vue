<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='$data.animationSettings'
    ref="dlgLichSuChuyenPhieu"
    :header='"Lịch sử chuyển phiếu"'
    showCloseIcon=true
    width='90%'
    :target='$data.target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Lịch sử chuyển phiếu</div>
          <div class="table-content" style="height: 250px;">
            <DataGrid
              v-bind:columns="[
                {fieldName: 'NGAY_CHUYEN', headerTextAlign: 'Center', headerText: 'Ngày chuyển', textAlign: 'Right', allowFiltering: true},
                {fieldName: 'NVTC_CU', headerTextAlign: 'Center', headerText: 'NVTC cũ', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'TUYENTHU_CU', headerTextAlign: 'Center', headerText: 'Tuyến cũ', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'NVTC_MOI', headerTextAlign: 'Center', headerText: 'NVTC mới', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'TUYENTHU_MOI', headerTextAlign: 'Center', headerText: 'Tuyến mới', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'NGUOICHUYEN', headerTextAlign: 'Center', headerText: 'Người chuyển', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'LYDO', headerTextAlign: 'Center', headerText: 'Lý do', textAlign: 'Left', allowFiltering: true},
                ]"
              ref="grdLSChuyenPhieu"
              v-bind:dataSource="dsLSChuyenPhieu"
              :showFilter="true"
              :allowPaging="true"
              :totalRecords="pageinfoLSChuyenPhieu.totalElement"
              >
            </DataGrid>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Thông tin hóa đơn hiện tại</div>
          <div class="table-content" style="height: 250px;">
            <DataGrid
              v-bind:columns="[
                {fieldName: 'KYHOADON', headerTextAlign: 'Center', headerText: 'Kỳ hóa đơn', textAlign: 'Right', allowFiltering: true},
                {fieldName: 'TIENNO', headerText: 'Tiền còn nợ', textAlign: 'Right', format: 'N0', type: 'number'},
                {fieldName: 'TIEN_HD', headerText: 'Tiền hóa đơn', textAlign: 'Right', format: 'N0', type: 'number'},
                {fieldName: 'GIAOPHIEU_ID', headerText: 'Lần giao', textAlign: 'Right', format: 'N0', type: 'number'},
                {fieldName: 'NGUOINHAN', headerTextAlign: 'Center', headerText: 'Giao cho', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'TRANGTHAI_HD', headerTextAlign: 'Center', headerText: 'Trạng thái', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'TRANGTHAI_XNNC', headerTextAlign: 'Center', headerText: 'Xác nhận NNNC', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'NVTC_MOI', headerTextAlign: 'Center', headerText: 'NVTC mới', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'SO_THUNG', headerText: 'Số thùng', textAlign: 'Right'},
                {fieldName: 'SERI', headerText: 'Seri', textAlign: 'Right'},
                {fieldName: 'SO_SERI', headerText: 'Số Seri', textAlign: 'Right'},
                ]"
              ref="grdTTHoaDon"
              v-bind:dataSource="dsTTHoaDon"
              :showFilter="true"
              :allowPaging="true"
              :totalRecords="pageinfoTTHoaDon.totalElement"
              >
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
  import Vue from 'vue'
  import TraCuuNoTongHopAPI from '../../api/TraCuuNoTongHopAPI'
  import { GridPlugin, Filter, Resize, ExcelExport, Sort} from "@syncfusion/ej2-vue-grids"
  import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
  import RemotePagination from '@/components/RemotePagination'
  import EventBus from '@/utils/eventBus'
  import moment from 'moment'

  Vue.use(GridPlugin)
  Vue.use(DialogPlugin)

  export default Vue.extend({
    components : {
      //appRemotePagination : RemotePagination,
    },
    props: {
      ma_tt: {
        type: String,
        required: false
      }
    },
    computed : {
      currentRowLSChuyenPhieu () {
        return this.pageinfoLSChuyenPhieu.currentRow
      },
      currentRowTTHoaDon () {
        return this.pageinfoTTHoaDon.currentRow
      },
    },
    data () {
      return {
        configs: {
        },
        params: {

        },
        dsLSChuyenPhieu : [],
        pageinfoLSChuyenPhieu : {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        dsTTHoaDon : [],
        pageinfoTTHoaDon : {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        },
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
      }
    },
    methods : {
      clearData: function(){
        this.dsLSChuyenPhieu = []
        this.dsTTHoaDon = []

        this.pageinfoLSChuyenPhieu.totalElement = 0
        this.pageinfoLSChuyenPhieu.page = 0

        this.pageinfoTTHoaDon.totalElement = 0
        this.pageinfoTTHoaDon.page = 0
      },
      openDialog () {
        this.$refs.dlgLichSuChuyenPhieu.show()
      },
      closeDialog() {
        this.$refs.dlgLichSuChuyenPhieu.hide()
      },
      onDialogClose () {
        this.clearForm()
        this.clearResult()
      },
      onDialogOpen () {
        this.clearData()

        this.getLichSuChuyenPhieu().then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
            {
              this.dsLSChuyenPhieu = response.data.data
              this.pageinfoLSChuyenPhieu.totalElement = response.data.data.length
            }
          }
        )

        this.getThongTinHoaDon().then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
            {
              this.dsTTHoaDon = response.data.data
              this.pageinfoTTHoaDon.totalElement = response.data.data.length
            }
          }
        )
      },
      async getLichSuChuyenPhieu() {
        try{
          const results = await TraCuuNoTongHopAPI.getTraCuuLichSuChuyenPhieu(this.axios, this.ma_tt);
          return results
        }catch(err) {
          console.log(err)
        }
      },
      async getThongTinHoaDon() {
        try{
          const results = await TraCuuNoTongHopAPI.getTraCuuTrangThaiHoaDon(this.axios, this.ma_tt);
          return results
        }catch(err) {
          console.log(err)
        }
      },
    },
    provide: {
      grid: [Filter, Resize, Sort, ExcelExport]
    },
    watch : {
      currentRowLSChuyenPhieu : {
        handler: function(new_value, old_value) {

          let newItem = this.$refs.grdLSChuyenPhieu.getRowByIndex(new_value - 1)
          let oldItem = this.$refs.grdLSChuyenPhieu.getRowByIndex(old_value - 1)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined)
            oldItem.classList.remove('e-focus')
        }
      },
      currentRowTTHoaDon : {
        handler: function(new_value, old_value) {

          let newItem = this.$refs.grdTTHoaDon.getRowByIndex(new_value - 1)
          let oldItem = this.$refs.grdTTHoaDon.getRowByIndex(old_value - 1)
          if(newItem !== undefined)
            newItem.classList.add('e-focus')
          if(oldItem !== undefined)
            oldItem.classList.remove('e-focus')
        }
      },
    }
  })
</script>
