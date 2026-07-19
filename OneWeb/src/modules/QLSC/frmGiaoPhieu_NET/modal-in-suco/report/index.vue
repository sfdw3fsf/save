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
        paginateElementsByHeight: 1100,
        pdfQuality: 2,
        pdfFormat: "a4",
        pdfOrientation: "portrait",
        pdfContentWidth: "100%",
        manualPagination: false
      },
      data: null
    }
  },
  computed: {
    htmlToPdfOptions() {
      return {
        margin: [0.5, 0.5, 0.5, 0.5],
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
      if (!(await this.validateControlValue())) return;
      this.$refs.html2Pdf.generatePdf();
    },
    setData(data) {
      this.data = data.dsSuCo.map(item => {
        let obj = {...item}
        obj.SELECTED = data.suCoId == obj.SUCO_ID
        return obj
      })
    },
    validateControlValue() {
      if (this.controlOptions.pdfQuality > 2) {
        alert("pdf-quality value should only be 0 - 2");
        this.controlOptions.pdfQuality = 2;
        return false;
      }
      if (!this.controlOptions.paginateElementsByHeight) {
        alert("paginate-elements-by-height value cannot be empty");
        this.controlOptions.paginateElementsByHeight = 1400;
        return false;
      }
      const paperSizes = [
        "a0",
        "a1",
        "a2",
        "a3",
        "a4",
        "letter",
        "legal",
        "a5",
        "a6",
        "a7",
        "a8",
        "a9",
        "a10",
      ];
      if (!paperSizes.includes(this.controlOptions.pdfFormat)) {
        alert(`pdf-format value should only be ${paperSizes}`);
        this.controlOptions.pdfFormat = "a4";
        return false;
      }
      if (!this.controlOptions.pdfOrientation) {
        alert("pdf-orientation value cannot be empty");
        this.controlOptions.pdfOrientation = "portrait";
        return false;
      }
      if (!this.controlOptions.pdfContentWidth) {
        alert("pdf-content-width value cannot be empty");
        this.controlOptions.pdfContentWidth = "100%";
        return false;
      }
      return true;
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