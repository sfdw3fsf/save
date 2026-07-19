<template src="./index.template.html" />
<style scoped>
  .combobox-grid .select-custom input[type="text"]:disabled {
    background-color: #e9ecef !important;
  }
</style>
<script>
import ClickOutside from 'vue-click-outside';
export default {
    props: ['value', 'disabled'],
    data: function() {
        return {
          textInput: null,
          dataSource: [],
          dsNhanVien: [],
          selectedItem: null,
          expanded: false
        }
    },
    watch: {
      value(newValue) {
        if (!newValue) {
          this.selectedItem = null
          this.textInput = null
        } else {
          this.selectedItem = this.dataSource.find(item => item.NHANVIEN_ID == newValue)
          this.textInput = !this.selectedItem ? '' : this.selectedItem.TEN_NV
        }
      }
    },
    async mounted() {
      await this.layDmNhanVienBaoHong('')
      let nhanVienId = parseInt(this.$root.token.getNhanVienID())
      this.selectedItem = this.dataSource.find(item => item.NHANVIEN_ID == nhanVienId)
      this.textInput = !this.selectedItem ? '' : this.selectedItem.TEN_NV
      this.$emit('input', !this.selectedItem ? null : this.selectedItem.NHANVIEN_ID)
    },
    methods: {
      async onClickDropdown() {

        if (this.disabled) {
          return
        }

        this.expanded = !this.expanded
        
        if (this.expanded) {
          this.dsNhanVien = this.dataSource.filter(item => {
            return `${item.TEN_NV}`.toLowerCase().includes(!this.textInput ? '' : `${this.textInput}`.toLowerCase())
          })
        } else {
          this.textInput = !this.selectedItem ? '' : this.selectedItem.TEN_NV
          this.$emit('input', !this.selectedItem ? null : this.selectedItem.NHANVIEN_ID)
        }
      },
      dataGrid_selectedRowChanged: function(dataItem) {
        this.selectedItem = dataItem
      },
      hide: function() {
        this.textInput = !this.selectedItem ? '' : this.selectedItem.TEN_NV
        this.$emit('input', !this.selectedItem ? null : this.selectedItem.NHANVIEN_ID)
        this.expanded = false
      },
      dataGrid_rowClicked: function(rowIndex) {
        setTimeout(() => {
          this.textInput = !this.selectedItem ? '' : this.selectedItem.TEN_NV
          this.$emit('input', !this.selectedItem ? null : this.selectedItem.NHANVIEN_ID)
          this.expanded = false
        }, 100);
      },
      getSelectedItem() {
        return this.selectedItem
      },
      getItemByValue(value) {
        return this.dataSource.find(item => item.NHANVIEN_ID == value)
      },
      async onKeyUp() {
        this.expanded = true
        this.dsNhanVien = this.dataSource.filter(item => {
          return `${item.TEN_NV}`.toLowerCase().includes(`${this.textInput}`.toLowerCase())
        })
      },
      async layDmNhanVienBaoHong(tenNhanVien) {
          try {
              let response = await this.$root.context.get(`/web-suco/danhmuc/nhanvien-baohong?tenNv=${tenNhanVien}`)

              if (response.error_code === 'BSS-00000000' && response.data != null) {
                  this.dataSource = response.data
              } else {
                  this.dataSource = []
              }
          } catch (err) {
              this.dataSource = []
          }
      },
    },
    directives: {
      ClickOutside
    }
}
</script>
