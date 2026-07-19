<template>
  <div>
    <div class="select-custom" data-toggle="dropdown">
      <input type="text" class="form-control" :value="selectedText" @click="showTableSelect"/>
    </div>
    <div class="dropdown-menu" style="visibility: hidden"></div>
    <div v-if="isShow" :class="isShow?'dropdown-menu__site-show pad10':'dropdown-menu__site-hidden pad10'" style="width: 800px;">
      <div class="table-content" style="height: 200px;">
        <DataGrid
        ref="lvwDropdown"
        :dataSource="optionsTable"
        :allowSorting='true'
        :allowPaging='true'
        :pageSettings='pageSettings'
        :allowFiltering='true'
        :selectionSettings='gridSelectionSettings'
        :allowColumnSelection="true"
        :columns="columns"
         @rowSelected="gridData_SelectionChanged">
        </DataGrid>
      </div>
    </div>
  </div>
</template>
<script>
import { Page, Filter } from '@syncfusion/ej2-vue-grids'

export default {
  props: { options: Array, value: Number, keyField: String, valueField: String, columnOptions: Array },
  mounted () {
    document.addEventListener('click', this.handleCloseDropdown)
    this.selectedItem = this.options.find(i => i[this.keyField] == this.value)

    if (this.columnOptions) {
      this.columns = [
        ...this.columnOptions.map(el => {
          return {
            ...el,
            fieldName: el.field
          }
        })
      ]
    }
  },
  computed: {
    optionsTable () {
      return this.options
    }
  },
  data: () => ({
    pageSettings: { pageSizes: [5, 10, 50, 100, 200], pageCount: 4, pageSize: 5 },
    gridSelectionSettings: {
      enableToggle: false
    },
    selectedItem: null,
    selectedText: null,
    isShow: false,
    columns: [
      {
        fieldName: 'TEN_QUAN',
        headerText: 'Tên quận'
      },
      {
        fieldName: 'MA_QUAN',
        headerText: 'Mã quận'
      }
    ]
  }),
  watch: {
    value (val) {
      this.selectedItem = this.options.find(i => i[this.keyField] == val)
    },
    selectedItem (val) {
      this.selectedText = val ? val[this.valueField] : null
    }
  },
  methods: {
    gridData_SelectionChanged (e) {
      this.selectedItem = e.data
      this.$emit('input', e.data[this.keyField])
      this.$emit('change', e.data)
    },
    handleCloseDropdown (e) {
      if (e && e.target && e.target.classList && e.target.classList.length > 0) {
        if (e.target.className.includes('e-row')) {
          this.isShow = false
          return
        }
      }
      if (e.path.length === 0) return
      for (let ele of e.path) {
        if (ele.className && (ele.className.includes('dropdown-menu__site-show') ||
            ele.className.includes('dropdown-menu__site-hidden') ||
            ele.className.includes('e-ddl'))) {
          return
        }
        if (ele.className && ele.className.includes('e-row')) {
          this.isShow = false
          return
        }
      }
      this.isShow = false
    },
    showTableSelect () {
      this.isShow = true
    }
  },
  provide: {
    grid: [Page, Filter]
  }
}
</script>
<style scoped>
.dropdown-menu__site-hidden {
  display: none;
}
.dropdown-menu__site-show {
  position: absolute;
  min-width: 10rem;
  padding: 0.5rem 0;
  margin: 0;
  font-size: 1rem;
  color: #212529;
  text-align: left;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid rgba(0,0,0,.15);
  border-radius: 0.25rem;
  z-index: 10;
}
</style>
