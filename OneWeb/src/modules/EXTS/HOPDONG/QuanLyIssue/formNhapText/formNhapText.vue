<template src="./formNhapText.html">
</template>

<script>
export default {
  name: "formNhapText",
  props:{
    id:String,
  },
  data: function () {
    return {
      // formNhapText
      titleFromNhapText:"Nhập text",
      noidungFormNhapText:"",
      resolvePromiseFormNhapText: null,
      rejectPromiseFormNhapText: null,
    }
  },
  methods: {
    /**
     * Mở form nhập text
     */
    show(title) {
      this.titleFromNhapText = "Nhập text"
      this.noidungFormNhapText = ""
      this.titleFromNhapText = title
      return new Promise((resolve, reject) => {
        this.$bvModal.show(this.id)
        this.resolvePromiseFormNhapText = resolve
        this.rejectPromiseFormNhapText = reject
      })
    },
    /**
     * Chấp nhận form nhập text
     */
    acceptFormNhapText() {
      this.resolvePromiseFormNhapText({ok: true, option: null, data: this.noidungFormNhapText})
      // clear
      this.titleFromNhapText = "Nhập text"
      this.noidungFormNhapText = ""
      this.$bvModal.hide(this.id)
    },
    /**
     * Hủy bỏ form nhập text
     */
    cancelFormNhapText() {
      this.resolvePromiseFormNhapText({ok: false, option: null, data: null})
      // clear
      this.titleFromNhapText = "Nhập text"
      this.noidungFormNhapText = ""
      this.$bvModal.hide(this.id)
    },
  }
}
</script>

<style scoped>

</style>
