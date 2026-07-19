<template>
  <div class="modal-dialog" style="width: 100%; max-width: 1000px;">
    <div class="modal-content popup-box">
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="table-content" style="height: 380px;">
              <DataGrid
                :columns="vatTu.headers"
                :dataSource="vatTu.dataSources"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PopupLichSuVt',
  props: ['maTb'],
  data () {
    return {
      vatTu: {
        headers: [
          { fieldName: 'nguoi_cn', headerText: 'Tên vật tư', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Serial', allowFiltering: true },
          { fieldName: 'noidung', headerText: 'Số lượng', allowFiltering: true },
          { fieldName: 'donvi_cn', headerText: 'Lô hàng', allowFiltering: true },
          { fieldName: 'noidung', headerText: 'Lý do cấp', allowFiltering: true },
          { fieldName: 'donvi_cn', headerText: 'Mã GD', allowFiltering: true }
        ],
        dataSources: []
      }
    }
  }
}
</script>
