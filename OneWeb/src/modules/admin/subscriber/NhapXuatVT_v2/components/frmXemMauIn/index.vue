<template>
  <div>
    <vue-pdf-app v-if="this.base64" style="height: 100vh;" :pdf="base64" @open="openPdf" @pages-rendered="pagesRendered" :page-scale="page-fit" />
  </div>
</template>
<script>
import VuePdfApp from 'vue-pdf-app'
export default {
  components: { VuePdfApp },
  props: {
    pdf: {
      type: [String, ArrayBuffer, Object],
      required: true
    }
  },
  data() {
    return {
      base64: null,
      visible: false
    }
  },
  async mounted() {
    this.loading(true)
    console.log('frmXemMauIn - mounted')
    this.base64 = this.pdf
    this.visible = false
  },
  methods: {
    pagesRendered(pdfApp) {
      console.log('pagesRendered')
      this.loading(false)
      this.visible = true
      pdfApp.pdfViewer.currentScaleValue = "1"
      // setTimeout(() => (pdfApp.pdfViewer.currentScaleValue = "page-height"));
    },
    async openPdf(pdfApp) {
      if (pdfApp.pdfViewer) {
        await pdfApp.pdfViewer.pagesPromise
        pdfApp.pdfViewer.currentScale = 1
      }
    }
  }
}
</script>
<style scoped></style>
