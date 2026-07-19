<template>
    <div class="row">
        <div class="col-sm-12 col-12">
            <div class="box-form">
                <div class="legend-title">Thông tin tìm kiếm</div>
                <div class="info-row">
                    <div class="value">
                        <input type="text" class="form-control highlight" v-model="txtSearchKey"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-12 col-12">
            <div class="box-form">
                <div class="legend-title">Danh sách thuê bao</div>
                <div class="table-content">
                    <DataGrid
                        ref="grid_DanhSach"
                        v-bind:columns="grid_DanhSach.config"
                        v-bind:dataSource="grid_DanhSach.DataSource"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"
                        :showColumnCheckbox="false"
                        :selectionSettings="{ enableToggle: false }"
                    >
                    </DataGrid>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
  name: "TraCuuKHDN",
  data: () => ({
    txtSearchKey: "",
    grid_DanhSach: {
      config: [
        {
          fieldName: 'tinh',
          headerText: 'Tỉnh',
          allowFiltering: true,
          width: 80
        },
        {
          fieldName: 'hdtb_id',
          headerText: 'Số điện thoại',
          isPrimaryKey: true,
          allowFiltering: true
        },
        {
          fieldName: 'hdtb_id',
          headerText: 'Số máy/Account',
          isPrimaryKey: true,
          allowFiltering: true
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao',
          allowFiltering: true
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Loại hình',
          allowFiltering: true
        },
        {
          fieldName: 'diachi_tb',
          headerText: 'Trạng thái TB',
          allowFiltering: true
        },
        {
          fieldName: 'dichvuvt_id',
          headerText: 'Địa chỉ lắp đặt',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai_dongbo',
          headerText: 'Mã khách hàng',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai_dongbo',
          headerText: 'Số giấy tờ',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai_dongbo',
          headerText: 'Số ảo',
          allowFiltering: true
        },
        {
          fieldName: 'trangthai_dongbo',
          headerText: 'Mã VNPT',
          allowFiltering: true
        }
      ],
      DataSource: []
    }
  })
}
</script>
