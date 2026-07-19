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
        }
      }
    },
    methods: {
      async onClickDropdown() {

        if (this.disabled) {
          return
        }

        this.expanded = !this.expanded
        
        if (this.expanded) {
          try {
            this.loading(true)
            await this.layDmNhanVienBaoHong(!this.textInput ? '' : this.textInput)
          } catch (err) {
          } finally {
            this.loading(false)
          }
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
        this.textInput = !this.selectedItem ? '' : this.selectedItem.TEN_NV
        this.$emit('input', !this.selectedItem ? null : this.selectedItem.NHANVIEN_ID)
        this.expanded = false
      },
      async onKeyUp() {
        this.expanded = true
        try {
          this.loading(true)
          await this.layDmNhanVienBaoHong(this.textInput)
        } catch (err) {
        } finally {
          this.loading(false)
        }
      },
      async layDmNhanVienBaoHong(tenNhanVien) {
          try {
              let response = await this.$root.context.get(`/web-suco/danhmuc/nhanvien-baohong?tenNv=${tenNhanVien}`)

              if (response.error_code === 'BSS-00000000' && response.data != null) {
                  this.dsNhanVien = response.data
              } else {
                  this.dsNhanVien = []
              }
          } catch (err) {
              this.dsNhanVien = []
          }
      },
    },
    directives: {
      ClickOutside
  }
}
</script>
