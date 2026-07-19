<template>
  <b-modal
    id="equipmentDetailModal"
    ref="equipmentDetailModal"
    size="xl"
    hide-footer
    @hidden="$emit('close')"
    modal-class="equipment-detail-modal"
  >
    <template #modal-header>
      <h5 class="modal-title">
        <strong>THÔNG TIN THIẾT BỊ - {{ equipmentDetail ? equipmentDetail.name : '' }}</strong>
      </h5>
      <button type="button" class="close" @click="closeModal">
        <span aria-hidden="true">&times;</span>
      </button>
    </template>

    <div v-if="equipmentDetail" class="equipment-detail-container">
      <b-tabs>
        <b-tab title="Thông tin chung" active>
          <EquipmentGeneralInfoTab :equipment-data="equipmentDetail" />
        </b-tab>
        <b-tab title="Thông số kỹ thuật">
          <EquipmentSpecTab :equipment-data="equipmentDetail" />
        </b-tab>
        <b-tab title="Kết nối">
          <EquipmentConnectionTab :equipment-data="equipmentDetail" />
        </b-tab>
      </b-tabs>
    </div>
  </b-modal>
</template>

<script>
import EquipmentGeneralInfoTab from '../tab-content/EquipmentGeneralInfoTab.vue'
import EquipmentSpecTab from '../tab-content/EquipmentSpecTab.vue'
import EquipmentConnectionTab from '../tab-content/EquipmentConnectionTab.vue'

export default {
  name: 'EquipmentDetailModal',
  components: {
    EquipmentGeneralInfoTab,
    EquipmentSpecTab,
    EquipmentConnectionTab
  },
  props: {
    equipmentDetail: {
      type: Object,
      default: null
    }
  },
  methods: {
    show () {
      this.$refs.equipmentDetailModal.show()
    },
    closeModal () {
      this.$refs.equipmentDetailModal.hide()
    }
  }
}
</script>

<style scoped>
.equipment-detail-container {
  padding: 0;
}
::v-deep .nav-tabs {
  border-bottom: 2px solid #dee2e6;
}
::v-deep .nav-tabs .nav-link {
  color: #666;
  font-weight: 500;
  padding: 10px 20px;
  border: none;
  border-bottom: 3px solid transparent;
  background: none;
}
::v-deep .nav-tabs .nav-link:hover {
  color: #2196f3;
  border-color: transparent;
}
::v-deep .nav-tabs .nav-link.active {
  color: #2196f3;
  background: none;
  border-color: transparent transparent #2196f3 transparent;
}
</style>
