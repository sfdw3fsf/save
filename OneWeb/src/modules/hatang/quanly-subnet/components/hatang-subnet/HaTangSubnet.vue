<template src="./hatang-subnet.html"> </template>

<script>
import HaTangIDG from './hatang-tabs/HaTangIDG.vue'
import HaTangAoHoa from './hatang-tabs/HaTangAoHoa.vue'
import HaTangBackUp from './hatang-tabs/HaTangBackUp.vue'
import HaTangSmartCloud from './hatang-tabs/HaTangSmartCloud.vue'

export default {
  name: 'HaTangSubnetTab',
  components: {
    HaTangAoHoa,
    HaTangBackUp,
    HaTangIDG,
    HaTangSmartCloud
  },

  data() {
    return {
      activeTab: 1
    }
  },

  props: {},

  methods: {
    changeTab(index) {
      this.activeTab = index
    }
  }
}
</script>
