<template>
    <div class="row">
      <div class="col-12">
        <div class="row">
          <div class="legend-title">Danh sách trạng thái vận hành</div>
        </div>
        <div class="row">
          <div class="col-12">
            <DataGrid
              ref="gridItems"
              :allowPaging="true"
              :enablePagingServer="false"
              :showColumnCheckbox="true"
              :allowExcelExport="true"
              v-bind:columns="columnsOfGrid"
              :dataSource="dataItems"
              @rowSelected="onRowSelected"
            >
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
</template>
<script>
export default {
  name: 'HaTangMang',
  data() {
    return {
      columnsOfGrid: [
        { field: 'mahatang_infra', headerText: 'Mã hạ tầng', width: '150' },
        { field: 'ten', headerText: 'Tên hạ tầng', width: '200' },
        { field: 'hatang_congnghe', headerText: 'Công nghệ', width: '150' }, // chưa có
        { field: 'ten_idc', headerText: 'IDC', width: '150' },
        { field: 'ten_nguoiqly', headerText: 'Người quản lý', width: '150' },
        { field: 'ten_phongbanqly', headerText: 'Phòng ban quản lý', width: '150' },
        { field: 'trangthai_ccdv', headerText: 'Trạng thái CCDV', width: '150' }, // Chưa có
      ],
      dataItems: [
        // Dữ liệu mẫu
        {
          mahatang_infra: 'HTM001',
          ten: 'Hạ tầng mạng A',
          hatang_congnghe: 'Công nghệ X',
          ten_idc: 'IDC Hà Nội',
          ten_nguoiqly: 'Nguyễn Văn A',
          ten_phongbanqly: 'Phòng IT',
          trangthai_ccdv: 'Đang hoạt động'
        },
        {
          mahatang_infra: 'HTM002',
          ten: 'Hạ tầng mạng B',
          hatang_congnghe: 'Công nghệ Y',
          ten_idc: 'IDC Hồ Chí Minh',
          ten_nguoiqly: 'Trần Thị B',
          ten_phongbanqly: 'Phòng Mạng',
          trangthai_ccdv: 'Bảo trì'
        }
      ]
    };
  },
    methods: {
      onRowSelected(args) {
      console.log('Row selected:', args);
    },
  },
};
</script>