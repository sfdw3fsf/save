<template>
  <DeviceForm :device-type-id="DEVICE_TYPE_ID.CARD_DU_LIEU" :device-id="id"/>
</template>
<script>
import DeviceForm from '../DeviceForm.vue'
import { DEVICE_TYPE_ID } from '../const'

export default {
  name: 'LinecardDevice',
  components: {
    DeviceForm
  },
  data() {
    return {
      DEVICE_TYPE_ID,
      id: null
    }
  },
  created() {
    this.handleRouteLogic()
  },
  methods: {
    handleRouteLogic() {
      const deviceId = this.$route.query.id
      
      if (deviceId <= 0) {
        const query = { ...this.$route.query }
        delete query.id
        this.$router.replace({
          path: this.$route.path,
          query: query
        })
        this.id = null
        return
      }
    }
  }
}
</script>

