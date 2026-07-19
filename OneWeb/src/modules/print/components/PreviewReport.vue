<template>
  <vue-pdf-app
    style="height: 100vh;"
    theme="dark"
    :pdf="dulieu.pdf"
    @open="openPdf"
  ></vue-pdf-app>

</template>

<script>
import VuePdfApp from 'vue-pdf-app'
import 'vue-pdf-app/dist/icons/main.css'

export default {
  name: 'PreviewReport',
  components: {
    VuePdfApp
  },
  props: {
    dulieu: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  data() {
    return {}
  },
  methods: {
    async openPdf(pdfApp) {
      if (pdfApp.pdfViewer) {
        await pdfApp.pdfViewer.pagesPromise
        pdfApp.pdfViewer.currentScale = 1
      }
    }
  }
}
</script>

<style scoped>

</style>
