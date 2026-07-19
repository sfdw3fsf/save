<template>
  <div class="nguondien-tab">
    <div class="tab-pane-content">
      <p class="text-muted">Nội dung tab Nguồn điện</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NguonDienTab'
}
</script>

<style scoped>
.nguondien-tab {
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

.text-muted {
  color: #9ca3af;
  font-size: 14px;
  text-align: center;
  padding: 40px 0;
}
</style>

