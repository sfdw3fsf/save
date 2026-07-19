<template src="./PopupPhongZoneDetail.template.html"></template>
<script>
import PhongZoneDetailForm from '../PhongZoneDetailForm/PhongZoneDetailForm.vue'

export default {
  name: 'PopupPhongZoneDetail',
  components: {
    PhongZoneDetailForm
  },
  props: {
    parentId: {
      type: Number
    },
    parentType: {
      type: String
    },
    itemId: {
      type: Number,
      default: 0
    },
    itemType: {
      type: String
    },
    mode: {
      type: String,
      default: 'VIEW', // 'ADD', 'EDIT', 'VIEW'
      validator: (value) => ['ADD', 'EDIT', 'VIEW'].includes(value)
    }
  },
  data() {
    return {
      animationSettings: { effect: 'Zoom' },
      target: 'body'
    }
  },
  computed: {
    dialogTitle() {
      if (this.mode === 'ADD') return 'Thêm mới Phòng/Zone'
      if (this.mode === 'EDIT') return 'Chỉnh sửa Phòng/Zone'
      return 'Chi tiết Phòng/Zone'
    }
  },
  methods: {
    async open(itemId) {
      // Gọi init của form component
      await this.$refs.phongZoneForm.init(itemId)
      // Show dialog sau khi init xong
      this.$nextTick(() => {
        if (this.$refs.dialog) {
          this.$refs.dialog.show()
        }
      })
    },
    close() {
      if (this.$refs.dialog) {
        this.$refs.dialog.hide()
      }
    },
    onDialogClose() {
      this.close()
    },
    onFormSaved(data) {
      // Forward event ra ngoài
      this.$emit('saved', data)
      this.close()
    }
  }
}
</script>
