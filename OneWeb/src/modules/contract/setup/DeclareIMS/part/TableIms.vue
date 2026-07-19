<template>
  <div class="grid-ext grid-ext-freeze">
    
    <ejs-grid
      ref="gridIMS"
      :dataSource="danhSachIMS"
      :allowPaging="true"
      :pageSettings="pageSettings"
      :allowSorting="false"
      :allowFiltering="true"
      :allowTextWrap="false"
      :selectionSettings='selectionOptionsSingle'
      :pagerTemplate='pagerTemplate'
      @recordClick="rowClickEvent"
      :actionComplete="gridReady"
      gridLines="Both"
    >
      <e-columns>
        <e-column template="<span class='fa selected-flag'></span>" :allowFiltering="false"
                  filterTemplate="<span></span>"
                  width="26" maxWidth="26" minWidth="26" textAlign="center"
                  :customAttributes="{class: 'column-selected-flag'}"
                  :freeze="'left'" :allowResizing="false" clipMode="Ellipsis"></e-column>
        <e-column :allowFiltering="false"
                  field="isSelected"
                  :template="templateCheckboxSelected"
                  headerText=""
                  filterTemplate="<span></span>"
                  width="39"
                  maxWidth="39"
                  minWidth="39"
                  textAlign="center"
                  :allowResizing="false"></e-column>

        <!-- <e-column type='checkbox' width='50'></e-column> -->
        <e-column field="ma_tb" headerText="Số máy" width=100 :filter="filterOptions"></e-column>
        <e-column field="phieu_id" isPrimaryKey='true' :visible="false"></e-column>
        <e-column field="ma_tb_cha" headerText="Số chính" width=100 :filter="filterOptions"></e-column>
        <e-column field="ten_tb" headerText="Tên thuê bao" width=150 :filter="filterOptions"></e-column>

        <e-column field="diachi_ld" headerText="Địa chỉ lắp đặt" width=300 :filter="filterOptions"></e-column>

        <e-column field="ma_kv" headerText="Mã khu vực" width=150 :filter="filterOptions"></e-column>

        <e-column field="ten_kv" headerText="Tên khu vực" width=150 :filter="filterOptions"></e-column>

        <e-column field="ngay_yc" headerText="Ngày YC" width=150 :filter="filterOptions"></e-column>

        <e-column field="ngaygiao" headerText="Ngày giao" width=150 :filter="filterOptions"></e-column>

        <e-column field="dv_hen" headerText="Đơn vị hẹn" width=150 :filter="filterOptions"></e-column>

        <e-column field="giohen_tu" headerText="Hẹn từ" width=150 :filter="filterOptions"></e-column>

        <e-column field="giohen_den" headerText="Hẹn đến" width=150 :filter="filterOptions"></e-column>

        <e-column field="GOI_KT" headerText="Gói KT" width=150 :filter="filterOptions"></e-column>

        <e-column field="GIO_CT" headerText="Giờ CT" width=150 :filter="filterOptions"></e-column>

        <e-column field="GIO_CONLAI" headerText="Giờ còn lại" width=150 :filter="filterOptions"></e-column>

        <e-column field="ma_gd" headerText="Mã GĐ" width=150 :filter="filterOptions"></e-column>

        <e-column field="cl_luu_in" headerText="Trạng thái in" width=150 :filter="filterOptions"></e-column>

        <e-column field="ngay_ht" headerText="Ngày BG" width=150 :filter="filterOptions"></e-column>

        <e-column field="ngay_ht_gp" headerText="Ngày thực hiện" width=150 :filter="filterOptions"></e-column>

        <e-column field="ten_status" headerText="Trạng thái" width=150 :filter="filterOptions"></e-column>

        <e-column field="dv_giao" headerText="Đơn vi giao" width=150 :filter="filterOptions"></e-column>

        <e-column field="LYDOTON_TC" headerText="Lý do tồn" width=150 :filter="filterOptions"></e-column>

        <e-column field="GHICHU_TON" headerText="Ghi chú tồn" width=150 :filter="filterOptions"></e-column>

        <e-column field="ghichu" headerText="Ghi chú" width=150 :filter="filterOptions"></e-column>
      </e-columns>
    </ejs-grid>
  </div>
</template>

<script>
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'
import Vue from 'vue'
export default {
  name: 'TableIms',
  mixins: [pagingAndFilter],
  components: {},
  props: {
    danhSachIMS: {
      type: Array,
      default: () => []
    }
  },
  data: () => ({
    pageSettings: { pageSizes: [10, 50, 100, 200], pageCount: 4, pageSize: 10 },
    selectionOptions: {
      enableToggle: false,
      type: 'Single'
    },
    focusToRow: 0,
    // template check in column
    templateCheckboxSelected: function () {
      return {
        template: Vue.component("templateCheckboxSelected", {
          template: `<div @click="clickCheckBox">
                <ejs-checkbox  v-model="checkedState"></ejs-checkbox>
            </div>`,
          data: function () {
            return {
              data: {}
            }
          },
          computed: {
            checkedState: {
              get () {
                return this.data.isSelected
              },
              set (value) {
                this.data.isSelected = value
              }
            }
          },
          methods: {
            clickCheckBox ($event) {
              $event.stopPropagation()
            }
          },
          mounted () {
          }
        })
      }
    }
  }),
  computed: {},
  methods: {
    rowClickEvent ($event) {
      this.focusToRow = $event.rowIndex
      this.$emit('selected-change', $event.rowData)
    },
    getSelectedRecords () {
      return this.$refs.gridIMS.getSelectedRecords()
    },
    rowSelectedEvent ($event) {
      if ($event.isHeaderCheckboxClicked) {
        this.$emit('selected-change', $event.data[0])
      } else {
        this.$emit('selected-change', $event.data)
      }
    },
    gridReady ($event) {
      if ($event.requestType === 'paging' && this.focusToRow !== null) {
        this.$nextTick(() => {
        })
      }
      if ($event.requestType === 'refresh') {
        this.$refs.gridIMS.clearFiltering()
        this.$nextTick(() => {
          this.$refs.gridIMS.selectRow(this.focusToRow)
        })
      }
    }
  },
  watch: {

  }
}
</script>

<style scoped>
</style>
