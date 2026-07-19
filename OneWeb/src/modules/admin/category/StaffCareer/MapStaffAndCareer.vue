<template>
  <div class="main-wrapper">
    <div class="breadcrumb-top">
      <breadcrumb/>
    </div>
    <header-search-staff></header-search-staff>
    <div class="page-content">
      <form-search-staff />
      <table-list-staff ref="tableListStaff"></table-list-staff>

      <div class="box-move-select-table">
        <div class="box-col box-form">
          <div class="legend-title">Danh sách nghề nghiệp đã gán</div>

          <div class="grid-ext grid-ext-freeze">
            <ejs-grid
              ref="tableCareerMapped"
              :pageSettings="pageSettings"
              :dataSource="careerMappedList"
              :allowPaging="true"
              :allowSorting="false"
              :allowFiltering="true"
              :allowSelection="true"
              :selectionSettings="selectionOptionsMultiple"
              :pagerTemplate='pagerTemplate'
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

                <e-column field="tennganhnghe" headerText="Nghành nghề"  :filter="filterOptions"></e-column>
              </e-columns>
            </ejs-grid>
          </div>
        </div>

        <div class="actions">
          <button class="btn" @click="moveToRight">
            <span class="-ap icon-chevron-thin-right"></span>
          </button>
          <button class="btn" @click="moveToLeft">
            <span class="-ap icon-chevron-thin-left"></span>
          </button>
        </div>
        <div class="box-col box-form">
          <div class="legend-title">Danh sách nghề nghiệp chưa gán</div>
          <div class="grid-ext grid-ext-freeze">
            <ejs-grid
              ref="tableCareerNotMapped"
              :pageSettings="pageSettings"
              :dataSource="careerNotMappedList"
              :allowPaging="true"
              :allowSorting="false"
              :allowFiltering="true"
              :allowSelection="true"
              :selectionSettings="selectionOptionsMultiple"
              :pagerTemplate='pagerTemplate'
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

                <e-column field="tennganhnghe" headerText="Nghành nghề"  :filter="filterOptions"></e-column>
                <e-column field="manganh" headerText="Mã nghành nghề" width="150" :filter="filterOptions"/>
              </e-columns>
            </ejs-grid>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderSearchStaff from './part/HeaderSearchStaff.vue'
import FormSearchStaff from './part/FormSearchStaff.vue'
import TableListStaff from './part/TableListStaff.vue'
import { pagingAndFilter } from '../Staff/mixins/pagingAndFilter'
import { mapActions, mapState } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị',
      Item: 'Bản ghi',
      Items: 'Bản ghi',
      FilterbarTitle: ''
    },
    pager: {
      currentPageInfo: '{0} của {1} trang'
    }
  }
})

export default {
  mixins: [pagingAndFilter],
  name: 'MapStaffAndCareer',
  components: {
    HeaderSearchStaff,
    FormSearchStaff,
    TableListStaff,
    breadcrumb
  },
  data: () => ({
  }),
  computed: {
    ...mapState('mapStaffAndCareer', [
      'careerMappedList',
      'isLoadingCareerMappedList',
      'careerNotMappedList',
      'isLoadingCareerNotMappedList'])
  },
  methods: {
    ...mapActions('mapStaffAndCareer', [
      'moveCareerNotMappedToMapped',
      'moveCareerMappedToNotMapped'
    ]),
    moveToLeft () {
      const lstNotMapped = this.$refs.tableCareerNotMapped.getSelectedRecords()
      if (lstNotMapped.length > 0) {
        this.moveCareerNotMappedToMapped(lstNotMapped)
      }
    },
    moveToRight () {
      const lstMapped = this.$refs.tableCareerMapped.getSelectedRecords()
      if (lstMapped.length > 0) {
        this.moveCareerMappedToNotMapped(lstMapped)
      }
    }
  }
}
</script>
