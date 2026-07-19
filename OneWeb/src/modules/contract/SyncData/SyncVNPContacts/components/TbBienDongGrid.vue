<script>
export default {
  name: 'TbBienDongGrid',
  props: {
    dataGrid: [],
    loaiDanhsach: 0
  },
  data: () => ({
    pageSize: 10,
    pageIndex: 0,
    totalRecords: 0,
    current: 0,
    columns: [
      {
        fieldName: "somay",
        headerText: "Số máy",
        allowSorting: true,
        autoFit: "true",
        textAlign: 'left',
        width: '130'
      },
      {
        fieldName: "ma_kh",
        headerText: "Mã TT",
        allowSorting: true,
        autoFit: "true",
        textAlign: 'left'
      },
      {
        fieldName: "ten_tb",
        headerText: "Tên thuê bao",
        allowSorting: true,
        autoFit: "true",
        textAlign: 'left'
      },
      {
        fieldName: "diachi",
        headerText: "Địa chỉ thuê bao",
        allowSorting: true,
        autoFit: "true",
        textAlign: 'left'
      },
      {
        fieldName: "trangthai_tb",
        headerText: "Trạng thái",
        allowSorting: true,
        autoFit: "true",
        textAlign: 'left'
      },
      {
        fieldName: "flag",
        headerText: "Kiểu biến động",
        allowSorting: true,
        autoFit: "true",
        textAlign: 'left'
      },
      {
        fieldName: "date_sync",
        headerText: "Ngày biến động",
        allowSorting: true,
        autoFit: "true",
        textAlign: 'left',
        width: '140',
        key: "col_date"
      },
      {
        fieldName: "khachhang_cu",
        headerText: "KH cũ",
        allowSorting: true,
        autoFit: "true",
        textAlign: 'left'
      },
    ]
  }),
  watch: {
    'loaiDanhsach'(newValue, oldValue) {
      this.updateColumns(newValue);
    },
  },
  methods: {
    rowSelected({data}) {
      this.$emit("tbbd_DvSelected", data);
    },
    rowDeselected() {
      this.$emit("tbbd_DvSelected", null);
    },
    pageChanged(args) {
      this.pageSize = args.pageSize;
      this.pageIndex = args.pageIndex;
      this.current = 0
    },
    setCurrent(data) {
      this.current = data.rowIndex;
    },
    getDataSelectedRow() {
      return this.$refs.danh_sach_tb_bien_dong.getSelectedRecords();
    },
    updateColumns(type) {
      const index = this.columns.findIndex(function(i) {
        return typeof i['key'] !== 'undefined' && i['key'] === 'col_date'
      });

      if(type == 0) { // Chưa đồng bộ dữ liệu
        this.columns[index]['headerText'] = 'Ngày biến động'
      } else { // Đã đồng bộ dữ liệu
        this.columns[index]['headerText'] = 'Ngày đồng bộ'
      }
    }
  },
  computed: {
    tongThuebao: function () {
      return this.dataGrid.length;
    }
  }
}
</script>
<template>
  <div class="SyncVNPContacts TbBienDongGrid">
    <div class="legend-title">
      <div class="pull-left">Danh sách thuê bao biến động</div>
      <div class="pull-right"><span class="tongsothuebao">Tổng số: {{tongThuebao}}</span></div>
      <div class="clearfix"></div>
    </div>
    <DataGrid
      ref="danh_sach_tb_bien_dong"
      v-bind:dataSource="dataGrid||[]"
      :columns="columns"
      :enablePagingServer="false"
      :allowPaging="true"
      :pageIndex="pageIndex"
      :pageSize="pageSize"
      :showColumnCheckbox="true"
      :totalRecords="totalRecords"
      @pageChanged="pageChanged"
      @rowSelected="rowSelected"
      @rowDeselected="rowDeselected"
      panelDataHeight="330"
    />
  </div>
</template>