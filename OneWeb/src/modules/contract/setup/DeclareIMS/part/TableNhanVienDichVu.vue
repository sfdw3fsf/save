<template>
  <div class="row">
    <div class="col-sm-6 col-12">
      <div class="box-form">
        <div class="legend-title">Danh sách nhân viên thực hiện</div>
        <div class="table-content">
          <ejs-grid
            ref="tableNhanVienThucHien"
            :allowPaging="true"
            :dataSource="danhSachNhanVienThucHien"
            :pageSettings="pageSettings"
            :allowSorting="false"
            :allowFiltering="true"
            :allowSelection="true"
            :selectionSettings="selectionOptionsSingle"
            :pagerTemplate='pagerTemplate'
            gridLines="Both"
          >
            <e-columns>
              <e-column template="<span class='fa selected-flag'></span>" :allowFiltering="false"
                        filterTemplate="<span></span>"
                        width="26" maxWidth="26" minWidth="26" textAlign="center"
                        :customAttributes="{class: 'column-selected-flag'}"
                        :freeze="'left'" :allowResizing="false" clipMode="Ellipsis"></e-column>
              <e-column field="ma_nv" :visible="false" :isPrimaryKey="true"></e-column>
              <e-column field="ten_nv" headerText="Tên nhân viên" :filter="filterOptions" width="auto"></e-column>
              <e-column field="so_dt" headerText="Số điện thoại" :filter="filterOptions"></e-column>
              <e-column field="nhiemvu" headerText="Nhiệm vụ" :filter="filterOptions"></e-column>
              <e-column field="ghichu" headerText="Ghi chú" :filter="filterOptions"></e-column>
            </e-columns>
          </ejs-grid>
        </div>
      </div>
    </div>
    <div class="col-sm-6 col-12">
      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">
            <span class="icon fa fa-angle-up"></span>Dịch vụ đăng ký
          </div>
          <div class="pull-right">
            <div class="list-checks">
              <div class="item red">
                {{warningText}}
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
        <div class="table-content">
          <ejs-grid
            ref="tableDichVuDangKy"
            :dataSource="danhSachDichVuDangKy"
            :allowPaging="true"
            :pageSettings="pageSettings"
            :allowSorting="false"
            :allowFiltering="true"
            :allowSelection="true"
            :pagerTemplate='pagerTemplate'
            :selectionSettings="selectionOptionsSingle"
            gridLines="Both"
          >
            <e-columns>
              <e-column template="<span class='fa selected-flag'></span>" :allowFiltering="false"
                        filterTemplate="<span></span>"
                        width="26" maxWidth="26" minWidth="26" textAlign="center"
                        :customAttributes="{class: 'column-selected-flag'}"
                        :freeze="'left'" :allowResizing="false" clipMode="Ellipsis"></e-column>
              <e-column field="ma_dvgt" headerText="Mã DVGT" :isPrimaryKey="true" :filter="filterOptions" width="auto"></e-column>
              <e-column field="ten_dvgt" headerText="Tên dịch vụ" :filter="filterOptions"></e-column>
              <e-column field="kieu" headerText="Kiểu YC" :filter="filterOptions"></e-column>
              <e-column field="noidung" headerText="Nội dung" :filter="filterOptions"></e-column>
            </e-columns>
          </ejs-grid>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'
export default {
  mixins: [ pagingAndFilter ],
  name: 'TableNhanVienDichVu',
  props: {
    danhSachNhanVienThucHien: Array,
    danhSachDichVuDangKy: Array
  },
  components: {},
  data: () => ({}),
  computed: {
    warningText () {
      if (this.danhSachDichVuDangKy && this.danhSachDichVuDangKy.length > 0 && !this.danhSachDichVuDangKy[0].id_vtn) {
        return 'Lưu ý: có dịch vụ phải kích hoạt nhân công'
      }
      return ''
    }
  },
  methods: {}
}
</script>
