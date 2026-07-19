<template>
  <div class="box-form">
    <div class="grid-ext grid-ext-freeze">
      <ejs-grid
        ref="dataGridDanhSachNhanVien"
        :dataSource="danhSachNhanVien"
        :allowPaging="true"
        :pageSettings="pageSettings"
        :allowSorting="false"
        :allowFiltering="true"
        :allowSelection="true"
        :allowResizing="true"
        :selectionSettings="selectionOptionsSingle"
        :resizeSettings="resizeSettingsAuto"
        :dataBound="dataBound"
        :pagerTemplate="pagerTemplate"
        :rowSelected="rowSelected"
        :actionComplete="gridReady"
        gridLines="Both"
      >
        <e-columns>
          <e-column
            template="<span class='fa selected-flag'></span>"
            :allowFiltering="false"
            filterTemplate="<span></span>"
            width="26"
            maxWidth="26"
            minWidth="26"
            textAlign="center"
            :customAttributes="{ class: 'column-selected-flag' }"
            :freeze="'left'"
            :allowResizing="false"
            clipMode="Ellipsis"
          ></e-column>
          <e-column field="nhanvien_id" :visible="false" :isPrimaryKey="true" width="0"></e-column>
          <e-column headerText="STT" width="70" AllowFiltering="false" textAlign="Center" :template="columnIndexTemplate"></e-column>
          <e-column field="ma_nv" headerText="Mã nhân viên" :filter="filterOptions"></e-column>
          <e-column field="ma_the" headerText="Mã thẻ" :filter="filterOptions"></e-column>
          <e-column field="ma_dv" headerText="Mã đơn vị" :filter="filterOptions"></e-column>
          <e-column field="ten_nv" headerText="Tên nhân viên" :filter="filterOptions"></e-column>
          <e-column field="chucdanh" headerText="Chức danh" :filter="filterOptions"></e-column>
          <e-column field="vitri" headerText="Vị trí" :filter="filterOptions"></e-column>
          <e-column field="dien_thoai" headerText="Điện thoại" :filter="filterOptions"></e-column>
          <e-column field="diachi_nv" headerText="Địa chỉ" :filter="filterOptions"></e-column>
          <e-column field="hinhthuc_hd" headerText="Hình thức HĐ" :filter="filterOptions"></e-column>
          <e-column field="vitri_id" headerText="Mã vị trí" :filter="filterOptions"></e-column>
        </e-columns>
      </ejs-grid>
    </div>
  </div>
</template>

<script>
import { pagingAndFilter } from '../mixins/pagingAndFilter'

export default {
  mixins: [pagingAndFilter],
  components: {},
  props: {
    danhSachNhanVien: Array
  },
  computed: {},
  data: () => ({
    focusToRow: null
  }),
  methods: {
    dataBound: function() {
      // thời gian resize column tốn gần 7.5s
      // this.$refs.dataGridDanhSachNhanVien.autoFitColumns()
    },
    rowSelected($event) {
      this.$emit('selected-change', $event.data)
    },
    clearSelection() {
      this.$refs.dataGridDanhSachNhanVien.clearSelection()
    },
    selectRecord(seletedNhanVien) {
      const currentViewData = this.$refs.dataGridDanhSachNhanVien.getCurrentViewRecords()
      const index = currentViewData.findIndex((item) => item.nhanvien_id === seletedNhanVien.nhanvien_id)
      this.$refs.dataGridDanhSachNhanVien.selectRow(index)
    },
    selectIndex(index) {
      this.$refs.dataGridDanhSachNhanVien.selectRow(index)
    },
    focusToRowByMaNhanVien(maNhanVien) {
      const index = this.danhSachNhanVien.findIndex((item) => item.ma_nv === maNhanVien)
      if (index === -1) {
        this.$toast.error('Không tìm thấy nhân viên')
      }
      const pager = this.$refs.dataGridDanhSachNhanVien.getPager()
      const toPage = ((index / pager.ej2_instances[0].pageSize) | 0) + 1

      if (toPage !== pager.ej2_instances[0].currentPage) {
        this.$refs.dataGridDanhSachNhanVien.goToPage(toPage)
      } else {
        this.$nextTick(() => {
          this.$refs.dataGridDanhSachNhanVien.selectRow(index)
        })
        return
      }

      this.focusToRow = index % pager.ej2_instances[0].pageSize
    },
    focusToRowByNhanVienID(nhanvien_id) {
      try {
        console.log('focusToRowByNhanVienID')
        console.log(this.danhSachNhanVien)
        console.log(nhanvien_id)
        const index = this.danhSachNhanVien.findIndex((item) => item.nhanvien_id == nhanvien_id)
        console.log(`index ${index}`)
        if (index === -1) {
          this.$toast.error('Không tìm thấy nhân viên')
        }
        const pager = this.$refs.dataGridDanhSachNhanVien.getPager()
        const toPage = ((index / pager.ej2_instances[0].pageSize) | 0) + 1
        console.log(`toPage ${toPage}`)
        console.log(`index ${index % pager.ej2_instances[0].pageSize}`)
        this.focusToRow = index % pager.ej2_instances[0].pageSize
        if (toPage !== pager.ej2_instances[0].currentPage) {
          this.$refs.dataGridDanhSachNhanVien.goToPage(toPage)
        } else {
          this.$nextTick(() => {
            this.$refs.dataGridDanhSachNhanVien.selectRow(this.focusToRow)
          })
          return
        }
        
         console.log('pass to all')
      } catch (e) {
        console.log(e)
      }
    },
    gridReady($event) {
      if ($event.requestType === 'paging' && this.focusToRow !== null) {
        this.$nextTick(() => {
          this.$refs.dataGridDanhSachNhanVien.selectRow(this.focusToRow)
          this.focusToRow = null
        })
      }
      if ($event.requestType === 'refresh') {
        this.$refs.dataGridDanhSachNhanVien.clearFiltering()
        this.$emit('tableReady')
      }
    }
  }
}
</script>
