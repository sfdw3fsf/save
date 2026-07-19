<template>
  <div>
    <iframe id="printing-frame" name="print_frame" src="about:blank" style="display:none;"/>
  </div>
</template>

<script>

export default {
  name: 'PrintReport',
  props: {
    type: {
      type: Number,
      required: true
    },
    data: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      PaperHeight: 0,
      PaperWidth: 0,
      hoadon: 0,
      linesPrinted: 0,
      container: '',
      receiptType: 0
    }
  },
  methods: {
    startPrint(printData) {
      try {
        this.loading(true)
        window.frames.print_frame.document.body.innerHTML = this.renderContent(printData)
        window.frames.print_frame.window.focus()
        window.frames.print_frame.window.print()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Lỗi khi tạo phiếu in: ', error.message)
      } finally {
        this.loading(false)
      }
    },
    renderContent(printData) {
      let el = ''
      printData.forEach(
        (item) => {
          el += `<span style="white-space: pre;">${item.noidung}</span><br>`
        }
      )
      return el
    }
  }
}
</script>

<style>
@page {
  size: auto;
  margin: 0;
}
</style>
