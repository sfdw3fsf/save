<template>
  <div class="box-form">
    <div class="legend-title">Danh sách nhân viên</div>
    <div class="table-content">
      <ejs-grid
        ref="gridNhanVienNganhNghe"
        :dataSource="staffList"
        :allowPaging="true"
        :pageSettings="pageSettings"
        :allowSorting="false"
        :allowFiltering="true"
        :selectionSettings='selectionOptionsSingle'
        @recordClick="rowSelectedEvent"
        :actionComplete="gridReady"
        :pagerTemplate='pagerTemplate'
        gridLines="Both"
      >
        <e-columns>
          <e-column template="<span class='fa selected-flag'></span>" :allowFiltering="false"
                    filterTemplate="<span></span>"
                    width="26" maxWidth="26" minWidth="26" textAlign="center"
                    :customAttributes="{class: 'column-selected-flag'}"
                    :freeze="'left'" :allowResizing="false" clipMode="Ellipsis"></e-column>
          <e-column field="ma_nv" headerText="Mã nhân viên" :isPrimaryKey="true" :filter="filterOptions" width="150"></e-column>
          <e-column field="ten_nv" headerText="Tên nhân viên" :filter="filterOptions" width="250"></e-column>
          <e-column field="ma_dv" headerText="Mã đơn vị" :filter="filterOptions" width="150"></e-column>
          <e-column field="ten_dv" headerText="Tên đơn vị" :filter="filterOptions" width="250"></e-column>
          <e-column field="ten" headerText="Loại nhân viên" :filter="filterOptions" width="auto"></e-column>
        </e-columns>
      </ejs-grid>
    </div>
  </div>
</template>

<script>
import gridview from '@/components/Shared/gridview'
import { mapState, mapMutations } from 'vuex'
import { pagingAndFilter } from "../../Staff/mixins/pagingAndFilter.js"
import api from '../api'

export default {
  mixins: [pagingAndFilter],
  name: 'TableListStaff',
  components: {
    gridview
  },
  computed: {
    ...mapState('mapStaffAndCareer', ['staffList', 'isLoadingStaffList'])
  },
  mounted () {
    api
      .getCareerNotMapped(this.axios, {
        p_nhanvien_id: 0
      })
      .then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === 'BSS-00000000' &&
          response.data.data
        ) {
          this.setCareerNotMappedList(response.data.data)
        } else {
          this.setCareerNotMappedList([])
        }
      })
      .finally(() => {
        this.setIsLoadingCareerNotMappedList(false)
      })
      .catch((err) => {
        this.$toast.error(err.response.data.message)
        this.setCareerNotMappedList([])
      })
  },
  methods: {
    ...mapMutations('mapStaffAndCareer', [
      'setCareerMappedList',
      'setCareerNotMappedList',
      'setIsLoadingCareerMappedList',
      'setIsLoadingCareerNotMappedList',
      'setSelectedStaff'
    ]),
    loadCareerMappedList (selectedStaff) {
      api
        .getCareerMapped(this.axios, {
          p_nhanvien_id: selectedStaff.nhanvien_id
        })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === 'BSS-00000000' &&
            response.data.data
          ) {
            this.setCareerMappedList(response.data.data)
          } else {
            this.setCareerMappedList([])
          }
        })
        .finally(() => {
        })
        .catch((err) => {
          this.$toast.error(err.response.data.message)
          this.setCareerMappedList([])
        })
    },
    loadCareerNotMappedList (selectedStaff) {
      this.setIsLoadingCareerNotMappedList(true)
      api
        .getCareerNotMapped(this.axios, {
          p_nhanvien_id: selectedStaff.nhanvien_id
        })
        .then((response) => {
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error_code &&
            response.data.error_code === 'BSS-00000000' &&
            response.data.data
          ) {
            this.setCareerNotMappedList(response.data.data)
          } else {
            this.setCareerNotMappedList([])
          }
        })
        .finally(() => {
          this.setIsLoadingCareerNotMappedList(false)
        })
        .catch((err) => {
          this.$toast.error(err.response.data.message)
          this.setCareerNotMappedList([])
        })
    },
    rowSelectedEvent ($event) {
      const newSelected = $event.rowData
      if (newSelected && newSelected.nhanvien_id === this.selectedStaff.nhanvien_id) {
        return
      }
      this.loadDataByStaff(newSelected)
    },

    loadDataByStaff (staff) {
      this.selectedStaff = JSON.parse(JSON.stringify(staff))
      if (this.selectedStaff === undefined || this.selectedStaff.nhanvien_id === undefined) {
        this.setCareerMappedList([])
        this.setCareerNotMappedList([])
        this.setSelectedStaff({})
        return
      }
      this.loadCareerMappedList(this.selectedStaff)
      this.loadCareerNotMappedList(this.selectedStaff)
      this.setSelectedStaff(this.selectedStaff)
    },

    gridReady ($event) {
      if ($event.requestType === 'paging' && this.focusToRow !== null) {
        this.$nextTick(() => {
        })
      }
      if ($event.requestType === 'refresh') {
        this.$refs.gridNhanVienNganhNghe.clearFiltering()
        this.$nextTick(() => {
          if (this.staffList && this.staffList.length !== 0) {
            this.loadDataByStaff(this.staffList[0])
            this.$refs.gridNhanVienNganhNghe.selectRow(0)
          }
        })
      }
    }
  },
  data: () => ({
    selectedStaff: {}
  })
}
</script>
