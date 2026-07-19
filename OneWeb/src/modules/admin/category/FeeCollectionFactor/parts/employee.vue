<template>
    <div class="box-move-select-table">
        <div class="box-col box-form">
            <div class="legend-title">Loại nhân viên đã gán</div>
            <DataGrid ref="employeeTypesAdded"
                :columns="tableAdded.columns"
                :dataSource="tableAdded.options"
                :enable-paging-server="false"
                :allowPaging="true"
                :loading="true"
                :showFilter="true"
                :showColumnCheckbox="true"
                :enabledSelectFirstRow="false"
                :selectionSettings="{ type: 'Multiple'}"
                @selectedItemsChanged="selectedItemsAddedChanged"
            ></DataGrid>
        </div>
        <div class="actions">
            <button class="btn" @click="removeEmployeesFromList">
                <span class="fa fa-angle-right"></span>
            </button>
            <button class="btn" @click="removeAllEmployeesFromList">
                <span class="fa fa-angle-double-right"></span>
            </button>
            <button class="btn" @click="addEmployeesToList">
                <span class="fa fa-angle-left"></span>
            </button>
            <button class="btn" @click="addAllEmployeesToList">
                <span class="fa fa-angle-double-left"></span>
            </button>
        </div>
        <div class="box-col box-form">
            <div class="legend-title">Loại nhân viên chưa gán</div>
            <DataGrid ref="employeeTypesNotAdded"
                :columns="tableNotAdded.columns"
                :dataSource="tableNotAdded.options"
                :enable-paging-server="false"
                :allowPaging="true"
                :loading="true"
                :showFilter="true"
                :showColumnCheckbox="true"
                :enabledSelectFirstRow="false"
                :selectionSettings="{ type: 'Multiple'}"
                @selectedItemsChanged="selectedItemsNotAddedChanged"
            ></DataGrid>
        </div>
    </div>
</template>

<script>
import gridView from '@/components/Shared/gridview.vue'
import { mapActions } from 'vuex'
export default {
  components: {
    gridView
  },
  data () {
    return {
      perpage: Number.MAX_SAFE_INTEGER,
      tableAdded: {
        selected: null,
        columns: [{
          fieldName: 'ten',
          headerText: 'Loại nhân viên'
        }],
        options: []
      },
      tableNotAdded: {
        selected: null,
        columns: [{
          fieldName: 'ten',
          headerText: 'Loại nhân viên'
        }],
        options: []
      },
      checkedElementsNotAdded: [],
      checkedElementsAdded: []
    }
  },
  props: {
    employeeTypesNotAdd: Array,
    employeeTypesAdded: Array
  },
  watch: {
    employeeTypesNotAdd: function () {
      this.tableNotAdded.options = this.employeeTypesNotAdd
    },
    employeeTypesAdded: function () {
      this.tableAdded.options = this.employeeTypesAdded
    }
  },
  methods: {
    ...mapActions('feeCollectionFactorCommon', [
      'addEmployeesToAddedList',
      'removeEmployeesFromAddedList'
    ]),
    addAllEmployeesToList: function (e) {
      this.addEmployeesToAddedList(this.tableNotAdded.options)
    },
    addEmployeesToList: function () {
      this.addEmployeesToAddedList(this.checkedElementsNotAdded)
      this.checkedElementsNotAdded = []
    },
    removeAllEmployeesFromList: function () {
      this.removeEmployeesFromAddedList(this.tableAdded.options)
    },
    removeEmployeesFromList: function () {
      this.removeEmployeesFromAddedList(this.checkedElementsAdded)
      this.checkedElementsAdded = []
    },
    selectedItemsNotAddedChanged: function (e) {
      this.checkedElementsNotAdded = e
    },
    selectedItemsAddedChanged: function (e) {
      this.checkedElementsAdded = e
    }
  }
}
</script>
