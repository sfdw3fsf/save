<template src="./index.template.html" />
<script>
import ClickOutside from 'vue-click-outside';
export default {
    model: {
      prop: 'value',
      event: 'change'
    },
    props: {
        dataSource: Array,
        textField: String,
        valueField: String,
        value: Array
    },
    data() {
        return {
          expanded: false,
          selectedValue: null,
          checkedValue: [],
          itemList: [],
          searchText: null,
          checkAll: false
        }
    },
    computed: {
        selectedText: function() {
          let checkedItems = this.dataSource.filter(item => this.checkedValue.indexOf(item[this.valueField]) >= 0)
          return checkedItems.map(item => item[this.textField]).join(', ')
        }
    },
    watch: {
      dataSource(newVal) {
        this.checkedValue = []
      },
      value(newVal) {
        this.checkedValue = newVal
      },
      checkAll(newVal) {

        if (newVal) {
          this.checkedValue = this.dataSource.map(item => item[this.valueField])
          return
        }

        if (this.checkedValue.length > 0 && this.checkedValue.length == this.dataSource.length) {
          this.checkedValue = []
        }
      },
      checkedValue(newVal) {
        this.checkAll = newVal.length > 0 && newVal.length == this.dataSource.length
        this.$emit('change', newVal)
      },
    },
    mounted() {
      if (!!this.value) {
        this.checkedValue = this.value
      }
    },
    methods: {
      onClickSelection() {
        this.expanded = !this.expanded
        this.itemList = this.dataSource
      },
      hide: function() {
        this.expanded = false
        this.itemList = []
        this.searchText = null
      },
      onInput(e) {
        let searchText = e.target.value
        searchText = !searchText ? '' : searchText.toString().toLowerCase().trim()
        this.itemList = this.dataSource.filter(item => {
          return item[this.textField].toString().toLowerCase().includes(searchText)
        })
      }
    },
    directives: {
      ClickOutside
    }
}
</script>
<style scoped>
  .selected {
    color: white;
    background-color: #5897fb;
  }
</style>
