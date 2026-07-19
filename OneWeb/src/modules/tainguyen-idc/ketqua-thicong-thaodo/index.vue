<template src="./index.template.html"> </template>
<script>
import Vue from 'vue'
import moment from 'moment'
import QuanLyThiCongHeThongThietBi from '../quanly-thicong-hethong-thietbi/index.vue'
export default {
  name: 'KetQuaThiCongLapDat',
  components: {
    QuanLyThiCongHeThongThietBi
  },
  data() {
    return {
    }
  },

  watch: {},
  created: async function() {
  },

  mounted: async function() {},
  methods: {
    
  }
}
</script>
<style scoped>
.action-buttons {
  display: flex;
  gap: 5px;
}

.action-buttons .btn {
  padding: 2px 8px;
  font-size: 12px;
}

.action-buttons .btn i {
  font-size: 14px;
}

/* Custom styles for child grid */
:deep(.e-detailrow) {
  background-color: #f8f9fa !important;
}

:deep(.e-detailrow .e-grid) {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1) !important;
  border-radius: 4px !important;
  overflow: hidden !important;
}

:deep(.e-detailrow .e-gridheader) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%) !important;
}

:deep(.e-detailrow .e-gridheader .e-headercontent) {
  color: white !important;
}

:deep(.e-detailrow .e-gridheader .e-columnheader) {
  background: transparent !important;
  border-bottom: 1px solid rgba(255, 255, 255, 0.2) !important;
}

:deep(.e-detailrow .e-gridheader th) {
  color: white !important;
  font-weight: 500 !important;
}

:deep(.e-detailrow .e-grid .e-content) {
  background-color: white !important;
}

:deep(.e-detailrow .e-row:hover) {
  background-color: #e3f2fd !important;
}

/* Icon for expand/collapse */
:deep(.e-dtdiagonaldown:before) {
  content: '\e7a9' !important;
  color: #2196f3 !important;
}

:deep(.e-dtdiagonalright:before) {
  content: '\e7a8' !important;
  color: #2196f3 !important;
}

/* Custom header for child devices */
.child-device-header {
  padding: 10px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  font-weight: bold;
  border-radius: 4px 4px 0 0;
  margin-bottom: 0;
}
</style>
