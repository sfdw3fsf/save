<template src="./index.template.html"></template>
<script>
import VueHtml2pdf from "vue-html2pdf";
import ReportContent from "./content";
export default {
  components: {
    VueHtml2pdf,
    ReportContent,
  },
  data() {
    return {
      contentRendered: false,
      generatingPdf: false,
      pdfDownloaded: false,
      controlOptions: {
        showLayout: false,
        floatLayout: true,
        enableDownload: false,
        previewModal: true,
        filename: "one_bss.pdf",
        paginateElementsByHeight: 800,
        pdfQuality: 2,
        pdfFormat: "a4",
        pdfOrientation: "landscape",
        pdfContentWidth: "100%",
        manualPagination: false
      },
      data: null
    }
  },
  computed: {
    htmlToPdfOptions() {
      return {
        margin: [0.1, 0.1, 0.1, 0.1],
        filename: this.controlOptions.filename,
        image: {
          type: "jpeg",
          quality: 0.98,
        },
        enableLinks: true,
        html2canvas: {
          scale: this.controlOptions.pdfQuality,
          useCORS: true,
        },
        jsPDF: {
          unit: "in",
          format: this.controlOptions.pdfFormat,
          orientation: this.controlOptions.pdfOrientation,
        },
      };
    },
  },
  methods: {
    async generatePdf() {
      this.$refs.html2Pdf.generatePdf();
    },
    setData(data) {
      this.$refs['ref-report-content'].setData(data)
    },
  }
}
</script>
<style>
  .in-phieu-xuly-suco .vue-html2pdf {
    position: relative !important;
  }

  .in-phieu-xuly-suco .vue-html2pdf .pdf-preview {
    position: absolute !important;
    width: 100% !important;
    height: 800px !important;
    top: 0 !important;
  }

  .in-phieu-xuly-suco .vue-html2pdf .pdf-preview button {
    display: none !important;
  }
</style>