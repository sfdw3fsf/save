<template src="./index.template.html"></template>
<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'

export default {
  name: 'ThongTinLapDat',
  components: {
    BssErrorMarker,
    BssRequiredMarker
  },
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    currentMode: {
      type: String,
      required: true
    },
    danhMucData: {
      type: Object,
      default: () => ({
        toaNhaList: [],
        phongList: [],
        matSanList: [],
        rackList: [],
        danhMucIdcList: [],
        uTrenList: [],
        uDuoiList: []
      })
    }
  },
  methods: {}
}
</script>
