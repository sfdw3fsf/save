<template>
  <div class="khachhang-tab">
    <div class="tab-pane-content">
      <div class="legend-title">
        Danh sách Khách hàng
      </div>
      <div class="grid-container">
        <DataGrid
          v-if="khachHangColumns.length > 0"
          :columns="khachHangColumns"
          :dataSource="khachHangList"
          :allowSorting="true"
          :allowPaging="true"
          :enablePagingServer="false"
          :pageSize="10"
          :showFilter="true"
          dataKeyField="KHACHHANG_ID"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'KhachHangTab',
  props: {
    khachHangList: {
      type: Array,
      default: () => []
    },
    khachHangColumns: {
      type: Array,
      default: () => []
    }
  }
}
</script>

<style scoped>
.khachhang-tab {
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

