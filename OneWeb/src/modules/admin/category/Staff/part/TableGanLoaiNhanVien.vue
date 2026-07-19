<template>
  <div class="box-move-select-table">
    <div class="box-col box-form">
      <div class="legend-title">Loại nhân viên đã gán</div>
      <div class="grid-ext grid-ext-freeze" style="height: 333px; overflow: hidden">
        <ejs-grid
          ref="loaiNhanVienDaGan"
          height="200"
          :dataSource="loaiNhanVienDaGan"
          :allowPaging="true"
          :pageSettings="pageSettings"
          :allowSorting="false"
          :allowFiltering="true"
          :allowSelection="true"
          :selectionSettings="selectionOptionsMultiple"
          :pagerTemplate='pagerTemplate'
          :resizeSettings="resizeSettingsAuto"
          :allowResizing="true"
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
            <e-column field="loainv_id" :visible="false" headerText="loainv_id" :isPrimaryKey="true"/>
            <e-column field="ten" headerText="Loại NV" :filter="filterOptions"/>
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
      <div class="legend-title">Loại nhân viên chưa gán</div>
      <div class="grid-ext grid-ext-freeze" style="height: 333px; overflow: hidden">
        <ejs-grid
          ref="loaiNhanVienChuaGan"
          height="200"
          :dataSource="loaiNhanVienChuaGan"
          :allowPaging="true"
          :pageSettings="pageSettings"
          :allowSorting="false"
          :allowFiltering="true"
          :allowSelection="true"
          :selectionSettings="selectionOptionsMultiple"
          :pagerTemplate='pagerTemplate'
          :resizeSettings="resizeSettingsAuto"
          :allowResizing="true"
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
            <e-column field="loainv_id" :visible="false" headerText="loainv_id" :isPrimaryKey="true"/>
            <e-column field="ten" headerText="Loại NV" :filter="filterOptions"/>
          </e-columns>
        </ejs-grid>
      </div>
    </div>
  </div>
</template>

<script>
import { pagingAndFilter } from '../mixins/pagingAndFilter'

import { getDanhSachLoaiNhanVien, getDanhSachLoaiNhanVienTheoNhanVienId } from '../api'

export default {
  mixins: [pagingAndFilter],
  components: {},
  data: () => ({
    loaiNhanVienDaGan: [],
    loaiNhanVienChuaGan: []
  }),
  computed: {},
  methods: {
    async loadLoaiNhanVienChuaGan () {
      this.loaiNhanVienChuaGan = await getDanhSachLoaiNhanVien(this.axios)
    },
    async loadLoaiNhanVienChuaGanTheoNhanVienId (nhanVienId) {
      this.loaiNhanVienChuaGan = await getDanhSachLoaiNhanVienTheoNhanVienId(this.axios, {
        p_kieu: 0,
        p_nhanvien_id: nhanVienId
      })
    },
    async loadLoaiNhanVienDaGanTheoNhanVienId (nhanVienId) {
      this.loaiNhanVienDaGan = await getDanhSachLoaiNhanVienTheoNhanVienId(this.axios, {
        p_kieu: 1,
        p_nhanvien_id: nhanVienId
      })
    },
    moveToRight () {
      const selected = this.$refs.loaiNhanVienChuaGan.getSelectedRecords()
      if (selected.length === 0) return
      const a = JSON.parse(JSON.stringify(this.loaiNhanVienDaGan))
      const b = JSON.parse(JSON.stringify(this.loaiNhanVienChuaGan))
      a.push(...selected)
      this.loaiNhanVienDaGan = a
      selected.forEach(item => {
        const index = b.findIndex(findItem => findItem.loainv_id === item.loainv_id)
        b.splice(index, 1)
      })
      this.loaiNhanVienChuaGan = b
    },
    moveToLeft () {
      const selected = this.$refs.loaiNhanVienDaGan.getSelectedRecords()
      if (selected.length === 0) return
      const a = JSON.parse(JSON.stringify(this.loaiNhanVienChuaGan))
      const b = JSON.parse(JSON.stringify(this.loaiNhanVienDaGan))
      a.push(...selected)
      this.loaiNhanVienChuaGan = a
      selected.forEach(item => {
        const index = b.findIndex(findItem => findItem.loainv_id === item.loainv_id)
        b.splice(index, 1)
      })
      this.loaiNhanVienDaGan = b
    },
    resetTable () {
      this.loaiNhanVienChuaGan = []
      this.loaiNhanVienDaGan = []
    },
    getDanhSachLoaiNhanVienDaGan () {
      return this.loaiNhanVienDaGan
    },
    getDanhSachLoaiNhanVienChuaGan () {
      return this.loaiNhanVienChuaGan
    }
  }
}
</script>
