<template>
  <div class="box-move-select-table">
    <div class="box-col box-form">
      <div class="legend-title">Đơn vị đã gán</div>
      <div class="grid-ext grid-ext-freeze" style="height: 232px; overflow: hidden">
        <ejs-grid
          ref="tableDonViDaGan"
          height="110"
          :dataSource="danhSachDonViDaGan"
          :allowPaging="true"
          :pageSettings="pageSettings"
          :allowSorting="false"
          :allowFiltering="true"
          :allowSelection="true"
          :selectionSettings="selectionOptionsMultiple"
          :pagerTemplate='pagerTemplate'
          :resizeSettings="resizeSettingsAuto"
          :allowResizing="true"
          @dataBound="dataBound('tableDonViDaGan')"
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

            <e-column field="ten_dv" headerText="Đơn vị" width="150" :filter="filterOptions"></e-column>
            <e-column field="ma_dv" headerText="Mã ĐV" :filter="filterOptions"/>
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
      <div class="legend-title">Đơn vị chưa gán</div>
      <div class="grid-ext grid-ext-freeze" style="height: 232px; overflow: hidden">
        <ejs-grid
          ref="tableDonViChuaGan"
          height="110"
          :dataSource="danhSachDonViChuaGan"
          :allowPaging="true"
          :pageSettings="pageSettings"
          :allowSorting="false"
          :allowFiltering="true"
          :allowSelection="true"
          :selectionSettings="selectionOptionsMultiple"
          :pagerTemplate='pagerTemplate'
          :resizeSettings="resizeSettingsAuto"
          :allowResizing="true"
          @dataBound="dataBound('tableDonViChuaGan')"
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

            <e-column field="ten_dv" headerText="Đơn vị" width="150" :filter="filterOptions"></e-column>
            <e-column field="ma_dv" headerText="Mã DV" :filter="filterOptions"></e-column>
          </e-columns>
        </ejs-grid>
      </div>
    </div>
  </div>
</template>

<script>
import { pagingAndFilter } from '../mixins/pagingAndFilter'
import { getDanhSachDonViChuaGan, getDanhSachDonViDaGan } from '../api'
export default {
  mixins: [pagingAndFilter],
  components: {},
  data: () => ({
    danhSachDonViDaGan: [],
    danhSachDonViChuaGan: []
  }),
  computed: {
  },
  methods: {
    async loadDanhSachDonViDaGan (loaiDonViId, donViDLId, nhanVienId) {
      const param = {
        p_donvidl_id: 0, // luôn luôn = 0
        p_loaidv_id: loaiDonViId,
        p_nhanvien_id: nhanVienId || 0,
        p_kieu: 1 // kiểu = 1 để lấy đã gán
      }
      this.danhSachDonViDaGan = await getDanhSachDonViDaGan(this.axios, param)
    },
    async loadDanhSachDonViChuaGan (loaiDonViId, donViDLId, nhanVienId) {
      const param = {
        p_donvidl_id: 0, // luôn luôn = 0
        p_loaidv_id: loaiDonViId,
        p_nhanvien_id: nhanVienId || 0,
        p_kieu: 0
      }
      this.danhSachDonViChuaGan = await getDanhSachDonViChuaGan(this.axios, param)
    },
    moveToRight () {
      const selected = this.$refs.tableDonViChuaGan.getSelectedRecords()
      if (selected.length === 0) return
      const a = JSON.parse(JSON.stringify(this.danhSachDonViDaGan))
      const b = JSON.parse(JSON.stringify(this.danhSachDonViChuaGan))
      a.push(...selected)
      this.danhSachDonViDaGan = a
      selected.forEach(item => {
        const index = b.findIndex(findItem => findItem.donvi_id === item.donvi_id)
        b.splice(index, 1)
      })
      this.danhSachDonViChuaGan = b
    },
    moveToLeft () {
      const selected = this.$refs.tableDonViDaGan.getSelectedRecords()
      if (selected.length === 0) return
      const a = JSON.parse(JSON.stringify(this.danhSachDonViChuaGan))
      const b = JSON.parse(JSON.stringify(this.danhSachDonViDaGan))
      a.push(...selected)
      this.danhSachDonViChuaGan = a
      selected.forEach(item => {
        const index = b.findIndex(findItem => findItem.donvi_id === item.donvi_id)
        b.splice(index, 1)
      })
      this.danhSachDonViDaGan = b
    },
    resetTable () {
      this.danhSachDonViDaGan = []
      this.danhSachDonViChuaGan = []
    },
    getDanhSachDonviDaGan () {
      return this.danhSachDonViDaGan
    },
    dataBound (tableName) {
      // this.$refs[tableName].autoFitColumns()
    }
  }
}
</script>
