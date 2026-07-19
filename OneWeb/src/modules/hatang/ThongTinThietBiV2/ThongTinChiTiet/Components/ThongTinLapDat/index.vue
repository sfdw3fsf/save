<template src="./index.template.html"></template>

<script>
export default {
  name: 'ThongTinLapDat',
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    controls: {
      type: Object,
      required: true
    },
    IDCList: {
      type: Array,
      default: () => []
    },
    alterBuildingList: {
      type: Array,
      default: () => []
    },
    alterFloorList: {
      type: Array,
      default: () => []
    },
    alterZoneList: {
      type: Array,
      default: () => []
    },
    alterRackList: {
      type: Array,
      default: () => []
    },
    UpperUList: {
      type: Array,
      default: () => []
    },
    LowerUList: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    'currentItem.idc_id': function(val) {
      if (val) {
        this.$emit('idc-changed', val)
      }
    },
    'currentItem.building_id': function(val) {
      if (val) {
        this.$emit('building-changed', val)
      }
    },
    'currentItem.floor_id': function(val) {
      if (val) {
        this.$emit('floor-changed', val)
      }
    },
    'currentItem.zone_id': function(val) {
      if (val) {
        this.$emit('zone-changed', val)
      }
    }
  },
  methods: {
    onIDCChange() {
      this.currentItem.building_id = null
      this.currentItem.floor_id = null
      this.currentItem.zone_id = null
      this.currentItem.rack_id = null
    },
    onBuildingChange() {
      this.currentItem.floor_id = null
      this.currentItem.zone_id = null
      this.currentItem.rack_id = null
    },
    onFloorChange() {
      this.currentItem.zone_id = null
      this.currentItem.rack_id = null
    },
    onZoneChange() {
      this.currentItem.rack_id = null
    },
    validateControls() {
      let msg = ''
      // Add validation logic here if needed
      return msg
    },
    enableControls(enabled) {
      Object.keys(this.controls).forEach((key) => {
        if (this.controls[key].hasOwnProperty('enabled')) {
          this.controls[key].enabled = enabled
        }
      })
    },
    resetValidation() {
      // Reset tất cả validation states về false
      Object.keys(this.controls).forEach((key) => {
        if (this.controls[key].hasOwnProperty('invalid')) {
          this.controls[key].invalid = false
        }
      })
    }
  }
}
</script>
