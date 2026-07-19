<template>
  <div class="toanha-tab">
    <div class="tab-pane-content">
      <div class="legend-title">
        Danh sách Tòa nhà
      </div>
      <div class="grid-container">
        <DataGrid
          v-if="toaNhaColumns.length > 0"
          :columns="toaNhaColumns"
          :dataSource="toaNhaList"
          :allowSorting="true"
          :allowPaging="true"
          :enablePagingServer="false"
          :pageSize="10"
          :showFilter="true"
          dataKeyField="MA_TOANHA"
          @recordDoubleClick="handleRowDoubleClick"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ToaNhaTab',
  props: {
    toaNhaList: {
      type: Array,
      default: () => []
    },
    toaNhaColumns: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    handleRowDoubleClick(args) {
      const row = args?.rowData || {}
      const id = row.ID_TOANHA || row.MA_TOANHA || row.ID
      if (!id) return
      this.$emit('selectObjectTaiNguyen', {
        id,
        type: 'TOANHA'
      })
    }
  }
}
</script>

<style scoped>
.toanha-tab {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
}

.tab-pane-content {
  flex: 1;
  overflow-y: auto;
  min-height: 0;
}

.section-title {
  font-size: 14px;
  font-weight: 600;
  color: #111827;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 8px;
  padding-bottom: 6px;
  border-bottom: 1px solid #e5e7eb;
}

.section-title i {
  color: #2563eb;
}

.grid-container {
  flex: 1;
  overflow: auto;
  min-height: 200px;
}
</style>

