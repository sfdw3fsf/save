<template>
  <b-modal
    ref="modal"
    title="Chọn lồng quây"
    size="md"
    @hidden="onHidden"
    @ok="onConfirm"
  >
    <div v-if="cages.length === 0" class="text-center text-muted py-4">
      <i class="fa fa-inbox fa-3x mb-3"></i>
      <p>Chưa có lồng quây nào trên sơ đồ</p>
    </div>

    <div v-else class="cage-list">
      <div
        v-for="cage in cages"
        :key="cage.id"
        class="cage-item"
        :class="{ selected: selectedCageId === cage.id }"
        @click="selectedCageId = cage.id"
      >
        <div class="cage-icon" :style="{ borderColor: cage.color, color: cage.color }">
          ⬡
        </div>
        <div class="cage-info">
          <div class="cage-name">{{ cage.name }}</div>
          <div class="cage-type">{{ cage.type }}</div>
        </div>
        <div v-if="selectedCageId === cage.id" class="check-icon">
          <i class="fa fa-check-circle"></i>
        </div>
      </div>
    </div>

    <template #modal-footer="{ ok, cancel }">
      <b-button variant="secondary" @click="cancel()">
        Hủy
      </b-button>
      <b-button variant="primary" :disabled="!selectedCageId" @click="ok()">
        Xác nhận
      </b-button>
    </template>
  </b-modal>
</template>

<script>
export default {
  name: 'SelectCageModal',

  data () {
    return {
      cages: [],
      selectedCageId: null
    }
  },

  methods: {
    show (cages) {
      this.cages = cages || []
      this.selectedCageId = null
      this.$refs.modal.show()
    },

    hide () {
      this.$refs.modal.hide()
    },

    onHidden () {
      this.cages = []
      this.selectedCageId = null
    },

    onConfirm () {
      if (this.selectedCageId) {
        this.$emit('confirm', this.selectedCageId)
      }
    }
  }
}
</script>

<style scoped>
.cage-list {
  max-height: 400px;
  overflow-y: auto;
}

.cage-item {
  display: flex;
  align-items: center;
  padding: 12px;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  margin-bottom: 8px;
  cursor: pointer;
  transition: all 0.2s;
}

.cage-item:hover {
  border-color: #2196F3;
  background-color: #f5f5f5;
}

.cage-item.selected {
  border-color: #2196F3;
  background-color: #E3F2FD;
}

.cage-icon {
  font-size: 32px;
  margin-right: 12px;
  border: 2px dashed;
  border-radius: 8px;
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cage-info {
  flex: 1;
}

.cage-name {
  font-weight: bold;
  font-size: 14px;
  margin-bottom: 4px;
}

.cage-type {
  font-size: 12px;
  color: #666;
}

.check-icon {
  color: #2196F3;
  font-size: 20px;
}
</style>
