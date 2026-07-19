<template>
  <ejs-dialog
    :enableResize="true"
    :visible="false"
    ref="popupVatTuTraCuu"
    :header="'Thông tin cấp vật tư cho thuê bao'"
    showCloseIcon="true"
    target=".main-wrapper"
    width="80%"
    height="800"
  >
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click.prevent="exportExcel">
            <span class="icon fa fa-excel"></span> Xuất Excel
          </a>
        </li>
        <li>
          <a @click="hideDialog">
            <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
          </a>
        </li>
      </ul>
    </div>
    <div class="box-form">
      <div class="legend-title">Danh sách vật tư</div>
      <DataGrid
        v-bind:columns="grvheaders"
        v-bind:dataSource="grvdatalist"
        :showColumnCheckbox="false"
        :enable-paging-server="false"
        :allowPaging="true"
        :showFilter="true"
        :enabled-select-first-row="true"
      ></DataGrid>
    </div>
  </ejs-dialog>
</template>

<script>
export default {
    data() {
        return {
            grvheaders: [
                { fieldName: 'loai_tbi', headerText: 'Tên vật tư' },
                { fieldName: 'seri', headerText: 'Seri' },
                { fieldName: 'ma_tbi', headerText: 'Mã tbị' },
                { fieldName: 'kieu_tbi', headerText: 'Kiểu thiết bị' },
                { fieldName: 'soluong', headerText: 'Số lượng' },
                { fieldName: 'dongia', headerText: 'Đơn giá' },
                { fieldName: 'lohang', headerText: 'Lô hàng' },
                { fieldName: '', headerText: 'Mô tả' },
                { fieldName: 'trangbi', headerText: 'Trang bị' },
                { fieldName: 'ngay_th', headerText: 'Ngày cấp' },
                { fieldName: 'nguoi_cn', headerText: 'Người CN' },
                { fieldName: 'tinhtrang_tbi', headerText: 'Tình trạng' },
                { fieldName: 'mucdich_cap', headerText: 'Lý do cấp' },
                { fieldName: 'ten_dv', headerText: 'Đơn vị' },
                { fieldName: 'ten_kho', headerText: 'Kho cấp' },
                { fieldName: 'so_phieu', headerText: 'Số phiếu' },
                { fieldName: 'trangthai', headerText: 'Trạng thái' }
            ],
            grvdatalist: []
        }
    },
  methods: {
    showDialog() {
      this.$refs.popupVatTuTraCuu.show();
    },
    hideDialog() {
      this.$refs.popupVatTuTraCuu.hide();
    },
    exportExcel() {}
  },
};
</script>

<style>
</style>