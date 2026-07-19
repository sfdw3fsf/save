<template>
  <b-modal id="modalThongtinHanhLang" ref="modalThongtinHanhLang" size="md" hide-footer @hidden="onModalHidden">
    <template #modal-header="{ close }">
      <h5 class="modal-title">
        <i class="fa fa-info-circle"></i> Thông tin Hành lang
      </h5>
      <button type="button" class="close" @click="close()">
        <span aria-hidden="true">&times;</span>
      </button>
    </template>

    <div v-if="aisleDetail" class="aisle-info">
      <div class="info-row">
        <div class="info-label">ID:</div>
        <div class="info-value">{{ aisleDetail.id }}</div>
      </div>

      <div class="info-row">
        <div class="info-label">Tên hành lang:</div>
        <div class="info-value">{{ aisleDetail.name }}</div>
      </div>

      <div class="info-row">
        <div class="info-label">Loại:</div>
        <div class="info-value">
          <span class="aisle-type-badge" :style="{ background: aisleDetail.color + '20', color: aisleDetail.color }">
            {{ getAisleIcon(aisleDetail.type) }} {{ aisleDetail.type }}
          </span>
        </div>
      </div>

      <div class="info-row">
        <div class="info-label">Màu sắc:</div>
        <div class="info-value">
          <span class="color-box" :style="{ background: aisleDetail.color }"></span>
          {{ aisleDetail.color }}
        </div>
      </div>

      <div class="info-row">
        <div class="info-label">Tọa độ (X, Y):</div>
        <div class="info-value">{{ aisleDetail.x }}, {{ aisleDetail.y }}</div>
      </div>

      <div class="info-row">
        <div class="info-label">Diện tích:</div>
        <div class="info-value">{{ aisleDetail.area }} m²</div>
      </div>

      <div class="info-row">
        <div class="info-label">Số điểm polygon:</div>
        <div class="info-value">{{ aisleDetail.polygon ? aisleDetail.polygon.length : 0 }}</div>
      </div>
    </div>

    <div class="mt-3 d-flex justify-content-end">
      <button type="button" class="btn btn-secondary" @click="closeModal">
        <i class="fa fa-times"></i> Đóng
      </button>
    </div>
  </b-modal>
</template>

<script>
export default {
  name: 'ModalThongtinHanhLang',
  props: {
    aisleDetail: {
      type: Object,
      default: null
    }
  },

  methods: {
    showModal() {
      this.$refs.modalThongtinHanhLang.show()
    },

    closeModal() {
      this.$refs.modalThongtinHanhLang.hide()
    },

    onModalHidden() {
      this.$emit('close')
    },

    getAisleIcon(type) {
      const icons = {
        hot: '🔥',
        cold: '❄️',
        intermediate: '🌡️',
        main: '🚪',
        emergency: '🚶'
      }
      return icons[type] || '🚶'
    }
  }
}
</script>

<style scoped>
.aisle-info {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.info-row {
  display: flex;
  align-items: flex-start;
  padding: 10px;
  border-bottom: 1px solid #f0f0f0;
}

.info-row:last-child {
  border-bottom: none;
}

.info-label {
  font-weight: 600;
  color: #666;
  min-width: 150px;
  flex-shrink: 0;
}

.info-value {
  color: #333;
  flex: 1;
}

.aisle-type-badge {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 14px;
  font-weight: 500;
}

.color-box {
  display: inline-block;
  width: 24px;
  height: 24px;
  border-radius: 4px;
  border: 1px solid #ddd;
  vertical-align: middle;
  margin-right: 8px;
}
</style>
