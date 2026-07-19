<template src="./index.template.html"></template>
<script>
export default {
  name: 'ThongTinKiemKe',
  props: {
    currentItem: {
      type: Object,
      required: true
    },
    controls: {
      type: Object,
      required: true
    },
    donViQuanLyList: {
      type: Array,
      default: () => []
    },
    phongBanQuanLyList: {
      type: Array,
      default: () => []
    },
    nguoiQuanLyList: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    'currentItem.donviqly_id': function(val) {
      if (val) {
        this.$emit('don-vi-quan-ly-changed', val)
        // Reset dependent fields
        this.currentItem.phongbanqly_id = null
        this.currentItem.nguoiqly_id = null
      }
    },
    'currentItem.phongbanqly_id': function(val) {
      if (val) {
        this.$emit('phong-ban-quan-ly-changed', val)
        // Reset dependent fields
        this.currentItem.nguoiqly_id = null
      }
    }
  },
  methods: {
    onDonViQuanLyChange() {
      this.$emit('don-vi-quan-ly-changed', this.currentItem.donviqly_id)
    },
    onPhongBanQuanLyChange() {
      this.$emit('phong-ban-quan-ly-changed', this.currentItem.phongbanqly_id)
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
