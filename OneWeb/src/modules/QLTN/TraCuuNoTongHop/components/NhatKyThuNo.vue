<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='$data.animationSettings'
    ref="dlgNhatKyThuNo"
    :header='"Nhật ký thu nợ"'
    showCloseIcon=true
    width='90%'
    :target='$data.target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Nhật ký thu nợ</div>
          <div class="table-content" style="height: 250px;">
            <DataGrid
              v-bind:columns="[
                {fieldName: 'MA_TT', headerText: 'Mã thanh toán', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'MA_TUYEN', headerText: 'Mã tuyến', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'MA_NV', headerText: 'Mã NVTC', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'KYHOADON', headerText: 'Kỳ HĐ', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'NGAY_DEN', headerText: 'Ngày đến', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'NOIDUNG', headerText: 'Nội dung', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'NGUOI_CN', headerText: 'Người CN', textAlign: 'Left', allowFiltering: true},
                {fieldName: 'NGAY_CN', headerText: 'Ngày CN', textAlign: 'Left', allowFiltering: true}
              ]"
              ref="gridNhatKy"
              :panelDataHeight='"150"'
              v-bind:dataSource="dsNKThuNo"
              :showFilter="true"
              :allowPaging="true"
              :totalRecords="pageinfo.totalElement"
              :pageIndex="pageinfo.page"
              :pageSize="pageinfo.pageSize"
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
  import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

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
    },
    data () {
      return {
        selectionOptions: { persistSelection: true, type: 'Single'},
        configs: {
        },
        params: {

        },
        dsNKThuNo : [],
        pageinfo : {
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
      openDialog () {
        this.$refs.dlgNhatKyThuNo.show()
      },
      closeDialog() {
        this.$refs.dlgNhatKyThuNo.hide()
      },
      onDialogClose () {
        this.clearResult()
      },
      onDialogOpen () {
        this.getNhatKyThuNo().then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
            {
              this.dsNKThuNo = response.data.data
              this.pageinfo.totalElement = response.data.data.length
              this.pageinfo.page = 0
            }
          }
        )
      },
      async getNhatKyThuNo() {
        try{
          const results = await TraCuuNoTongHopAPI.getTraCuuNhatKyThuNo(this.axios, this.ma_tt);
          return results
        }catch(err) {
          console.log(err)
        }
      },
      clearResult: function() {
        this.dsNKThuNo = []
        this.pageinfo = {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          sort: null,
          propertiesSort: null,
          currentRow: 0
        }
      }
    },
  })
</script>

