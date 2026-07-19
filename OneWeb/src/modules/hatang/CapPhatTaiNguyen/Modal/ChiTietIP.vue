<template src="./ChiTietIP.html"> </template>
<script>
export default {
  props: {
    currentItemAoHoaCP: {
      type: Object,
      default: () => ({})
    }
  },
  data: function() {
    return {
      dataItem: [],
      target: '.main-wrapper',
      animationSettings: { effect: 'Zoom' }
    }
  },
  watch: {},
  created: async function() {},
  methods: {
    clearAll() {
      this.dataItem = []
    },
    onGetData: async function(id) {
      try {
        this.$root.showLoading(true)
        let rs = await this.apiChiTiet(id)
        if (rs) {
          this.clearAll()

          this.dataItem = rs
        } else {
          this.$toast.error('Tải dữ liệu không thành công!')
        }
      } finally {
        this.$root.showLoading(false)
      }
    },
    closeDialog() {
      this.clearAll()
      this.$refs.dialogChiTietIP.hide()
    },
    apiChiTiet: async function(id) {
      let result
      try {
        let rs = await this.$root.context.get(`/web-ecms/hatang/capphat-ip/view/${id}`)
        result = rs.data[0]
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
        if (this.$refs.dialogChiTietIP) {
          if (this.currentItemAoHoaCP) {
            await this.onGetData(this.currentItemAoHoaCP.capphat_mayao_id)
          }
          this.$refs.dialogChiTietIP.show() // Hiển thị dialog
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
