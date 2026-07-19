<template src="./TuChoi.html"> </template>
<script>
export default {
  props: {
    currentItem: {
      type: Object,
      default: () => ({})
    }
  },
  data: function() {
    return {
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
      //   controls: {
      //     socu_molai: {
      //       invalid: false,
      //     }
      //   },
    }
  },
  watch: {
    // 'currentItem.lydotuchoi': function(value) {
    //   this.controls.socu_molai.invalid = value == ''
    // },
  },
  methods: {
    clearAll() {
      this.currentItem.lydotuchoi = ''
    },
    onSave(formName) {
      if (!this.validateControls()) return

      try {
        this.$root.showLoading(true)
        this.currentItem.lydotuchoi = this.currentItem.lydotuchoi.trim()

        let rs = this.apiTuChoiCapPhat(this.currentItem.id, this.currentItem.lydotuchoi)
        if (rs) {
          this.clearAll()
          this.$toast.success('Từ chối cấp phát tài nguyên thành công!')
          this.$emit('refreshData')
          this.closeDialog()
        } else {
          this.$toast.error('Từ chối cấp phát tài nguyên không thành công!')
        }
      } catch (e) {
        if (e.response && e.response.data && e.response.data.message_detail) {
          this.$toast.error(e.response.data.message_detail)
        } else {
          this.$toast.error('Đã xảy ra lỗi khi từ chối cấp phát tài nguyên')
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    closeDialog() {
      this.clearAll()
      this.$refs.dialogTuChoi.hide()
    },
    validateControls: function() {
      let msg = ''
      if (this.currentItem.lydotuchoi == null || this.currentItem.lydotuchoi == '') {
        msg = 'Lý do từ chối không được để trống!'
      }
      if (msg) {
        this.$toast.error(msg)
      }
      return msg == ''
    },
    apiTuChoiCapPhat: async function(ycID, lydo) {
      let result
      try {
        let rs = await this.$root.context.post('/web-ecms/phieu-cap-phat/tu-choi-cap-phat', {
          p_capphat_id: ycID,
          p_lydotuchoi: lydo
        })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },
    async dialogOpen() {
      try {
        this.loading(true)
        this.clearAll()
        // this.setCurrentItem()
        // Kiểm tra sự tồn tại của ref và gọi phương thức show() trên dialog
        if (this.$refs.dialogTuChoi) {
          this.$refs.dialogTuChoi.show() // Hiển thị dialog
        } else {
          this.$toast.error('Dialog không tồn tại hoặc không được tham chiếu đúng')
        }
      } catch (e) {
        // Hiển thị thông báo lỗi nếu có vấn đề
        console.log('loi', e)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi khi mở dialog')
        }
      } finally {
        this.loading(false) // Tắt trạng thái loading
      }
    }
  },
  mounted() {}
}
</script>
