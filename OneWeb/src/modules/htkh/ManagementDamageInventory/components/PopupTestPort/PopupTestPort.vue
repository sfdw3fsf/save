<template>
  <b-modal ref="testPortRef" size="xs" body-class="p-0" hide-header hide-header-close hide-footer centered>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">Test port: {{ selectedRow ? selectedRow.ma_tb : '' }}</div>
        <div class="close -ap icon-close" @click="onClose"></div>
      </div>
      <div class="popup-body text-center">
        <b-spinner style="width: 2rem; height: 2rem" v-if="status === ''" label="Loading..."></b-spinner>
        <h1 v-else :class="[status === 'UP' ? 'text-success' : 'text-danger']">
          {{ status }}
        </h1>
      </div>
    </div>
  </b-modal>
</template>

<script>
import { BSpinner } from 'bootstrap-vue'
import checkPortAPI from '@/modules/htkh/ManagementDamageInventory/Api'
export default {
  name: 'PopupTestPort',
  props: {
    selectedRow: {
      type: Object,
      default: () => {}
    }
  },
  components: {
    BSpinner
  },
  data() {
    return {
      status: ''
    }
  },
  methods: {
    async show() {
      this.$refs.testPortRef.show()
      this.status = ''
      await this.testPort(this.selectedRow.baohong_id)
    },
    onClose() {
      this.$refs.testPortRef.hide()
    },
    async testPort(thamso) {
      const response = await checkPortAPI.test_port(this.$root.axios, { vma_ts: thamso })
      const { data, error_code, message } = response.data
      if (error_code === 'BSS-00000000') {
        if (data) {
          const strLines = data.split('\n')
          const statusString = 'Trạng thái port: '
          const statusLine = strLines.find((el) => el.includes(statusString))
          if (statusLine) {
            this.status = statusLine.replace(statusString, '').trim().toUpperCase()
          }
        }
      } else {
        this.$toast.error(message, 'title')
        if (error_code === 'BSS-00000500') this.status = 'Chưa được cấp port'
      }
    }
  }
}
</script>
