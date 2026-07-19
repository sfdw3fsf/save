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
        value: null
    },
    data() {
        return {
          expanded: false,
          selectedValue: null,
          itemList: [],
          searchText: null,
        }
    },
    computed: {
        selectedText: function() {
          let selectedItems = this.dataSource.find(item => item[this.valueField] == this.selectedValue)
          return !selectedItems ? '' : selectedItems[this.textField]
        }
    },
    watch: {
      dataSource() {
        this.$emit('change', null)
      },
      value(newVal) {
        this.selectedValue = newVal
      }
    },
    methods: {
      onClickSelection() {
        this.expanded = !this.expanded
        this.itemList = this.dataSource
      },
      onSelect(value) {
        this.selectedValue = value
        this.$emit('change', this.selectedValue)
        this.hide()
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
