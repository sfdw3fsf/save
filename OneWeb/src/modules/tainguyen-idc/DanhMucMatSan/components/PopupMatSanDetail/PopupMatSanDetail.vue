<template src="./PopupMatSanDetail.template.html"></template>
<script>
import MatSanDetailForm from '../MatSanDetailForm/MatSanDetailForm.vue'

export default {
  name: 'PopupMatSanDetail',
  components: {
    MatSanDetailForm
  },
  props: {
    itemId: {
      type: Number,
      default: 0
    },
    parentId: {
      type: Number
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
      if (this.mode === 'ADD') return 'Thêm mới mặt sàn'
      if (this.mode === 'EDIT') return 'Chỉnh sửa mặt sàn'
      return 'Chi tiết mặt sàn'
    }
  },
  methods: {
    async open() {
      // Gọi init của form component
      if (this.$refs.matSanForm) {
        await this.$refs.matSanForm.init()
      }
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
    async handleSave() {
      // Gọi handleSave của form component
      if (this.$refs.matSanForm) {
        await this.$refs.matSanForm.handleSave()
      }
    },
    onFormSaved(data) {
      // Forward event ra ngoài
      this.$emit('saved', data)
      this.close()
    }
  }
}
</script>
     