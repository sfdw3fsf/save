<template>
  <div class="modal-content popup-box">
    <div class="popup-body">
      <div class="row">
        <div class="box-form">
          <DataGrid v-bind:columns="grvheaders" v-bind:dataSource="input_data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabled-select-first-row="true"></DataGrid>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'TraCuuNhanVienTH',
  props: {
    input_data: {
      type: Array,
      default: []
    }
  },

  components: {},
  data() {
    return {
      txtKQTH: '',
      txtLyDo: '',
      txtLyDo: '',
      grvdatalist: [],
      grvheaders: [
        { fieldName: 'gr', headerText: ' ', isGroupedColumn: true },
        { fieldName: 'ngaygiao', headerText: 'Ngày giao' },
        { fieldName: 'nv_giao', headerText: 'Người giao' },
        { fieldName: 'ma_nd_th', headerText: 'User người TH' },
        { fieldName: 'ma_nv', headerText: 'Mã NV TH' },
        { fieldName: 'ten_nv', headerText: 'Người thực hiện' },
        { fieldName: 'so_dt', headerText: 'Điện thoại' },
        { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ' },
        { fieldName: 'kqxl_id', headerText: 'Kết quả XL' },
        { fieldName: 'trangthai', headerText: 'Trạng thái' },
        { fieldName: 'ghichu', headerText: 'Ghi chú' }
      ]
    }
  },
  methods: {}
}
</script>
