<template>
<div class="row">
    <div class="col-sm-6 col-12">
      <div class="box-form">
          <div class="legend-title">Danh sách nhân viên đã gán</div>
          <div class="table-content grid-ext grid-ext-freeze" style="height: 248px;">
              <!-- <data-grid
              :columns="dataGrid.columns"
              :showColumnCheckbox="dataGrid.showColumnCheckbox"
              :showFilter="dataGrid.showFilter"
              :dataSource="dataGrid.dataSource"></data-grid> -->
              <ejs-grid ref="gridAdded"
              :dataSource="dataGridAdded.dataSource"
              :allowGrouping="true"
              :groupSettings="dataGridAdded.groupOptions"
              :allowSorting='true'
              :allowPaging='true'
              :pageSettings='pageSettings'
              :allowFiltering='true'
              :selectionSettings='dataGridAdded.selectionOptions'
              :pagerTemplate='pagerTemplate'>
                <e-columns>
                    <e-column type='checkbox' width='50'></e-column>
                    <e-column field='ten_dv' headerText='Tên đơn vị' width='120'></e-column>
                    <e-column field='nhanvien_id' isPrimaryKey='true' headerText='Mã NV' width='120'></e-column>
                    <e-column field='ten_nv' headerText='Tên NV' width='120'></e-column>
                </e-columns>
              </ejs-grid>
          </div>
      </div>
    </div>
    <div class="col-sm-6 col-12">
      <div class="box-form">
          <div class="legend-title">Danh sách nhân viên chưa gán</div>
          <div class="box-move-select-table">
              <div class="actions w40 padl0">
                  <button class="btn" @click="removeEmployeesAddedFromList">
                      <span class="-ap icon-chevron-thin-right"></span>
                  </button>
                  <button class="btn" @click="moveEmployeesToList">
                      <span class="-ap icon-chevron-thin-left"></span>
                  </button>
              </div>
              <div class="box-col" style="width:calc(100% - 40px);">
                  <div class="table-content grid-ext grid-ext-freeze" style="height: 248px;">
                      <!-- <data-grid
                      :columns="dataGrid.columns"
                      :showColumnCheckbox="true"
                      :showFilter="dataGrid.showFilter"
                      :dataSource="dataGrid.dataSource"
                      :allowPaging="true"
                      :enable-paging-server="false"
                      @selectedRowChanged="gridChanged"
                      @selectedItemsChanged="selectedItems_click"></data-grid> -->
                    <ejs-grid ref="gridNotAdded"
                    :dataSource="dataGridNotAdded.dataSource"
                    :allowGrouping="true"
                    :groupSettings="dataGridNotAdded.groupOptions"
                    :allowSorting='false'
                    :allowPaging='true'
                    :pageSettings='pageSettings'
                    :allowFiltering='true'
                    :selectionSettings='dataGridNotAdded.selectionOptions'
                    :pagerTemplate='pagerTemplate'>
                      <e-columns>
                          <e-column type='checkbox' width='50'></e-column>
                          <e-column field='ten_dv' headerText='Tên đơn vị' width='120'></e-column>
                          <e-column field='nhanvien_id' isPrimaryKey='true' headerText='Mã NV' width='120'></e-column>
                          <e-column field='ten_nv' headerText='Tên NV' width='120'></e-column>
                      </e-columns>
                    </ejs-grid>
                  </div>
              </div>
          </div>

      </div>
    </div>
</div>
</template>

<script>
import { GridComponent, ColumnsDirective, ColumnDirective, Group, Sort, Page, Filter } from '@syncfusion/ej2-vue-grids'
import { mapActions, mapGetters } from 'vuex'
import mixinStore from '../mixin/index'
export default {
  components: {
    'ejs-grid': GridComponent,
    'e-columns': ColumnsDirective,
    'e-column': ColumnDirective
  },
  provide: {
    grid: [Group, Sort, Page, Filter]
  },
  data () {
    return {
      dataGridAdded: {
        groupOptions: { columns: ['ten_dv'] },
        // pageOptions: { pageSize: 10, pageCount: 5 },
        selectionOptions: { type: 'Multiple' },
        dataSource: []
      },
      dataGridNotAdded: {
        groupOptions: { columns: ['ten_dv'] },
        // pageOptions: { pageSize: 10, pageCount: 5 },
        selectionOptions: { type: 'Multiple' },
        dataSource: []
      },
      selectedEmployeesNotAdded: []
    }
  },
  computed: {
    ...mapGetters('employeesCommon', [
      'getSelectedEmployeesAdded',
      'getEmployeesNotAdded',
      'getEmployeesAdded'
    ]),
    ...mapGetters('storesCommon', [
      'getSelectedStore',
      'getSelectedDepartment'
    ])
  },
  watch: {
    getEmployeesNotAdded: function () {
      this.dataGridNotAdded.dataSource = Array.from(this.getEmployeesNotAdded)
      this.dataGridAdded.dataSource = Array.from(this.getEmployeesAdded)
    },
    getEmployeesAdded: function () {
      this.dataGridNotAdded.dataSource = Array.from(this.getEmployeesNotAdded)
      this.dataGridAdded.dataSource = Array.from(this.getEmployeesAdded)
    }
  },
  mixins: [mixinStore],
  methods: {
    ...mapActions('employeesCommon', [
      'addEmployeesToEmployeesAdded',
      'removeEmployeesFromEmployeesNotAdded',
      'addEmployeesToEmployeesNotAdded',
      'removeEmployeesFromEmployeesAdded',
      'loadEmployeesNotAdded',
      'loadEmployeesAdded'
    ]),
    ...mapActions('storesCommon', [
      'insertEmployeesToStore',
      'deleteEmployeesFromStore'
    ]),
    reloadEmployeesData: async function () {
      try {
        await this.loadEmployeesNotAdded({
          donvi_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.DONVI_ID,
          kho_id: this.getSelectedStore.kho_id,
          phanvung_id: (this.getSelectedDepartment === null || this.getSelectedDepartment === undefined) ? 0 : this.getSelectedDepartment.PHANVUNG_ID
        })
        await this.loadEmployeesAdded({
          kho_id: this.getSelectedStore.kho_id
        })
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
    },
    moveEmployeesToList: async function () {
      this.$root.showLoading(true)
      try {
        const data = this.$refs.gridNotAdded.getSelectedRecords()
        if (data && data.length > 0) {
          // this.addEmployeesToEmployeesAdded(data)

          // uppercase key
          const upperCaseKeyData = this.upperCaseKey(data)
          await this.insertEmployeesToStore(upperCaseKeyData)
          await this.reloadEmployeesData()
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    removeEmployeesAddedFromList: async function () {
      this.$root.showLoading(true)
      try {
        const data = this.$refs.gridAdded.getSelectedRecords()
        if (data && data.length > 0) {
          // this.removeEmployeesFromEmployeesAdded(data)

          // uppercase key
          const upperCaseKeyData = this.upperCaseKey(data)
          await this.deleteEmployeesFromStore(upperCaseKeyData)
          await this.reloadEmployeesData()
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    }
  }
}
</script>
