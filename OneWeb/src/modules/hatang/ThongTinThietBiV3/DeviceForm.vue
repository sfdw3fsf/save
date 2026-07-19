<template>
  <div>
    <!-- Hiển thị danh sách thiết bị -->
    <DeviceList v-if="!shouldShowDetail" ref="deviceList" :device-type-id="deviceTypeId" />
    <!-- Hiển thị chi tiết thiết bị -->
    <DeviceDetail
      v-else
      ref="deviceDetail"
      :device-type-id="deviceTypeId"
      :device-id="currentDeviceId"
      :mode="currentMode"
      @device-saved="onDeviceSaved"
      @detail-closed="onDetailClosed"
    />
  </div>
</template>
<style src="./styles.scss" scoped></style>
<script>
import DeviceList from './DeviceList.vue'
import DeviceDetail from './DeviceDetail.vue'

export default {
  name: 'DeviceForm',
  components: {
    DeviceList,
    DeviceDetail
  },
  props: {
    deviceTypeId: {
      type: Number,
      required: true
    },
    deviceId: {
      type: Number,
      default: null
    }
  },
  data() {
    const isAddMode = this.$route.query.action === 'add'
    const deviceId = this.deviceId || this.$route.query.id
    const isNotListRoute = !this.$route.path.includes('/ds-')
    const shouldAutoAdd = isNotListRoute && !deviceId && !isAddMode

    return {
      currentDeviceId: deviceId ? deviceId : null,
      currentMode: isAddMode ? 'ADD' : shouldAutoAdd ? 'ADD' : deviceId ? 'VIEW' : null
    }
  },
  computed: {
    shouldShowDetail() {
      return !!this.currentDeviceId || this.currentMode === 'ADD' || this.$route.query.action === 'add'
    }
  },
  watch: {
    '$route.query.action': {
      handler: function(action) {
        if (action === 'add') {
          this.currentMode = 'ADD'
          this.currentDeviceId = null
        }
      },
      immediate: false
    }
  },
  methods: {
    onDeviceSaved() {
      if (this.$refs.deviceList) {
        this.$refs.deviceList.refresh()
      }
    },
    onDetailClosed() {
      this.currentDeviceId = null
      this.currentMode = null
      if (this.$refs.deviceList) {
        this.$refs.deviceList.refresh()
      }
    }
  }
}
</script>
