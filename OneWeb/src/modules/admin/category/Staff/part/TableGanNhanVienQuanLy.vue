<template>
    <div class="box-move-select-table">
      <div class="box-col box-form">
        <div class="legend-title">Nhân viên quản lý đã gán</div>
        <div class="grid-ext grid-ext-freeze" style="height: 352px; overflow: hidden">
          <ejs-grid
            height="230"
            ref="tableNhanVienQuanLyDaGan"
            :dataSource="danhSachNhanVienQuanLyDaGan"
            :allowPaging="true"
            :pageSettings="pageSettings"
            :allowSorting="false"
            :allowFiltering="true"
            :allowSelection="true"
            :selectionSettings="selectionOptionsMultiple"
            :pagerTemplate='pagerTemplate'
            :resizeSettings="resizeSettingsAuto"
            :allowResizing="true"
            @dataBound="dataBound('tableNhanVienQuanLyDaGan')"
            gridLines="Both"
          >
            <e-columns>
              <e-column template="<span class='fa selected-flag'></span>" :allowFiltering="false"
                        filterTemplate="<span></span>"
                        width="26" maxWidth="26" minWidth="26" textAlign="center"
                        :customAttributes="{class: 'column-selected-flag'}"
                        :freeze="'left'" :allowResizing="false" clipMode="Ellipsis"></e-column>
              <e-column type="checkbox" width="39" maxWidth="39" minWidth="39" headerTextAlign="center"
                        textAlign="center" :customAttributes="{class: 'column-check-selected'}"
                        :allowResizing="false" clipMode="Ellipsis"></e-column>
              <e-column field="ma_nv" headerText="Mã NV" :isPrimaryKey="true"></e-column>
              <e-column field="ten_nv" headerText="Tên NV" width="120" :filter="filterOptions"></e-column>
              <e-column field="ten_dv" headerText="Đơn vị" :filter="filterOptions"></e-column>
            </e-columns>
          </ejs-grid>
        </div>
      </div>
      <div class="actions">
        <button class="btn" @click="moveToLeft">
          <span class="-ap icon-chevron-thin-right"></span>
        </button>
        <button class="btn" @click="moveToRight">
          <span class="-ap icon-chevron-thin-left"></span>
        </button>
      </div>
      <div class="box-col box-form">
        <div class="legend-title">Nhân viên quản lý chưa gán</div>
        <div class="grid-ext grid-ext-freeze" style="height: 352px; overflow: hidden">
          <ejs-grid
            height="230"
            ref="tableNhanVienQuanLyChuaGan"
            :dataSource="danhSachNhanVienQuanLyChuaGan"
            :allowPaging="true"
            :pageSettings="pageSettings"
            :allowSorting="false"
            :allowFiltering="true"
            :allowSelection="true"
            :selectionSettings="selectionOptionsMultiple"
            :pagerTemplate='pagerTemplate'
            :resizeSettings="resizeSettingsAuto"
            :allowResizing="true"
            @dataBound="dataBound('tableNhanVienQuanLyChuaGan')"
            gridLines="Both"
          >
            <e-columns>
              <e-column template="<span class='fa selected-flag'></span>" :allowFiltering="false"
                        filterTemplate="<span></span>"
                        width="26" maxWidth="26" minWidth="26" textAlign="center"
                        :customAttributes="{class: 'column-selected-flag'}"
                        :freeze="'left'" :allowResizing="false" clipMode="Ellipsis"></e-column>
              <e-column type="checkbox" width="39" maxWidth="39" minWidth="39" headerTextAlign="center"
                        textAlign="center" :customAttributes="{class: 'column-check-selected'}"
                        :allowResizing="false" clipMode="Ellipsis"></e-column>
              <e-column field="ma_nv" headerText="Mã NV" :isPrimaryKey="true"></e-column>
              <e-column field="ten_nv" headerText="Tên NV" width="120" :filter="{ operator: 'contains' }"></e-column>
              <e-column field="ten_dv" headerText="Đơn vị" :filter="{ operator: 'contains' }"></e-column>
            </e-columns>
          </ejs-grid>
        </div>
      </div>
    </div>
</template>

<script>
import { pagingAndFilter } from '../mixins/pagingAndFilter'
import { getDanhSachNhanVienQuanLy } from '../api'

export default {
  mixins: [pagingAndFilter],
  components: {},
  data: () => ({
    danhSachNhanVienQuanLyDaGan: [],
    danhSachNhanVienQuanLyChuaGan: []
  }),
  computed: {},
  methods: {
    async loadNhanVienQuanLyDaGan (nhanVienId, donViId) {
      this.danhSachNhanVienQuanLyDaGan = await getDanhSachNhanVienQuanLy(this.axios, {
        p_nhanvien_id: nhanVienId,
        p_donvi_id: donViId,
        p_kieu: 1
      })
    },
    async loadNhanVienQuanLyChuaGan (nhanVienId, donViId) {
      this.danhSachNhanVienQuanLyChuaGan = await getDanhSachNhanVienQuanLy(this.axios, {
        p_nhanvien_id: nhanVienId,
        p_donvi_id: donViId,
        p_kieu: 0
      })
    },
    moveToRight () {
      const selected = this.$refs.tableNhanVienQuanLyChuaGan.getSelectedRecords()
      if (selected.length === 0) return
      const a = JSON.parse(JSON.stringify(this.danhSachNhanVienQuanLyDaGan))
      const b = JSON.parse(JSON.stringify(this.danhSachNhanVienQuanLyChuaGan))
      a.push(...selected)
      this.danhSachNhanVienQuanLyDaGan = a
      selected.forEach(item => {
        const index = b.findIndex(findItem => findItem.ma_nv === item.ma_nv)
        b.splice(index, 1)
      })
      this.danhSachNhanVienQuanLyChuaGan = b
    },
    moveToLeft () {
      const selected = this.$refs.tableNhanVienQuanLyDaGan.getSelectedRecords()
      if (selected.length === 0) return
      const a = JSON.parse(JSON.stringify(this.danhSachNhanVienQuanLyChuaGan))
      const b = JSON.parse(JSON.stringify(this.danhSachNhanVienQuanLyDaGan))
      a.push(...selected)
      this.danhSachNhanVienQuanLyChuaGan = a
      selected.forEach(item => {
        const index = b.findIndex(findItem => findItem.ma_nv === item.ma_nv)
        b.splice(index, 1)
      })
      this.danhSachNhanVienQuanLyDaGan = b
    },
    resetTable () {
      this.danhSachNhanVienQuanLyChuaGan = []
      this.danhSachNhanVienQuanLyDaGan = []
    },
    getDanhSachNhanVienQuanLyDaGan () {
      return this.danhSachNhanVienQuanLyDaGan
    },
    dataBound (tableName) {
      // this.$refs[tableName].autoFitColumns()
    }
  }
}
</script>
