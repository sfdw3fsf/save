<template>
  <ejs-dialog
    :visible="visible"
    :isModal="true"
    :animationSettings="{ effect: 'Zoom' }"
    :showHeader="false"
    width="70%"
    height="auto"
    :showCloseIcon="false"
    @close="$emit('close')"
  >
    <div class="popup-box p-3 vlan-select-modal-wrapper">
      <!-- HEADER -->
      <div class="popup-header">
        <div class="title d-flex align-items-center">
          <span class="icon one-notepad"></span>
          <span>Chọn VLAN</span>
        </div>
        <div class="close -ap icon-close" @click="$emit('close')"></div>
      </div>

      <!-- GRID CHỌN VLAN -->
      <DataGrid
        ref="vlanGrid"
        :dataSource="vlanList"
        :columns="columns"
        :allowPaging="true"
        :enablePagingServer="false"
        @rowSelected="onRowSelected"
        @rowDeselected="onRowDeselected"
        :showColumnCheckbox="true"
      />

      <!-- ACTION BUTTONS -->
      <div class="d-flex justify-content-end mt-3">
        <button class="btn btn-secondary me-2" @click="$emit('close')">
          Hủy
        </button>
        <button class="btn btn-primary" @click="saveSelection">
          Lưu lựa chọn
        </button>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
export default {
  name: 'VlanSelectModal',
  props: {
    visible: { type: Boolean, default: false },
    vlanList: { type: Array, default: () => [] }
  },
  data() {
    return {
      selectedRows: [],
      columns: [
        { fieldName: 'TEN', headerText: 'Tên VLAN', width: 150 },
        { fieldName: 'ID', headerText: 'VLAN ID', width: 100 },
        { fieldName: 'IDC_TEN', headerText: 'IDC', width: 200 }
      ]
    }
  },
  methods: {
    onRowSelected(e) {
      // e.data hoặc e.rowData tùy DataGrid wrapper
      const row = e.data || e.rowData
      if (!row) return
      const exists = this.selectedRows.some((r) => r.ID === row.ID)
      if (!exists) this.selectedRows.push(row)
    },

    onRowDeselected(e) {
      const row = e.data || e.rowData
      if (!row) return
      this.selectedRows = this.selectedRows.filter((r) => r.ID !== row.ID)
    },

    saveSelection() {
      this.$emit('save', this.selectedRows)
    }
  }
}
</script>
