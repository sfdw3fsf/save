<template>
    <b-modal
      size="xl"
      dialog-class="dialog-content"
      body-class="body-content"
      content-class="popup-box"
      v-model="visible"
      hide-footer
      hide-header
    >
    <div style="margin: -1rem">
      <ManagementUnit
        v-if="visible"
        @form-close="show(false)"
        :in_khuvuc_id='in_khuvuc_id'
        :ma_kv='ma_kv'
        :ten_kv='ten_kv'/>
    </div>
</b-modal>
</template>
<script>
import ManagementUnit from "./ManagementUnit.vue"
export default {
  components: {
    ManagementUnit
  },
  data: () => ({
    visible: false,
    in_khuvuc_id: null,
    ma_kv: null,
    ten_kv: null
  }),
  methods: {
    show (visible, data) {
      this.visible = visible
      this.in_khuvuc_id = data.in_khuvuc_id
      this.ma_kv = data.ma_kv
      this.ten_kv = data.ten_kv
    }
  }
}
</script>
