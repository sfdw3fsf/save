<template>
  <b-modal
    ref="testPortRef"
    size="xs"
    body-class="p-0"
    hide-header
    hide-header-close
    hide-footer
    centered
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          Test port: {{ maTB }}
        </div>
        <div class="close -ap icon-close" @click="onClose"></div>
      </div>
      <div class="popup-body text-center" :class="[status === 'UP' ? 'text-success' : 'text-danger']">
        {{ status }}
      </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  name: 'PopupTestPort',
  props: ['status', 'maTB'],
  methods: {
    show () {
      this.$refs.testPortRef.show()
    },
    onClose () {
      this.$refs.testPortRef.hide()
    }
  }
}
</script>
