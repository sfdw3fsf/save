<template src="./index.template.html"></template>
<script>
import VueHtml2pdf from "vue-html2pdf";
import PdfContent from "./components/PdfContent.vue";
export default {
  components: {
    VueHtml2pdf,
    PdfContent,
  },
  created(){
    this.downloadPdf();
  },
  data() {
    return {
      contentRendered: false,
      progress: 0,
      generatingPdf: false,
      pdfDownloaded: false,
      controlValue: {
        showLayout: false,
        floatLayout: true,
        enableDownload: false,
        previewModal: true,
        filename: "filename",
        paginateElementsByHeight: 1100,
        pdfQuality: 2,
        pdfFormat: "a4",
        pdfOrientation: "portrait",
        pdfContentWidth: "800px",
        manualPagination: false
      },
    };
  },
  computed: {
    htmlToPdfOptions() {
      return {
        margin: 0,
        filename: "hee hee.pdf",
        image: {
          type: "jpeg",
          quality: 0.98,
        },
        enableLinks: true,
        html2canvas: {
          scale: this.controlValue.pdfQuality,
          useCORS: true,
        },
        jsPDF: {
          unit: "in",
          format: this.controlValue.pdfFormat,
          orientation: this.controlValue.pdfOrientation,
        },
      };
    },
  },
  methods: {
    async downloadPdf() {
      if (!(await this.validateControlValue())) return;
      this.$refs.html2Pdf.generatePdf();
    },
    validateControlValue() {
      if (this.controlValue.pdfQuality > 2) {
        alert("pdf-quality value should only be 0 - 2");
        this.controlValue.pdfQuality = 2;
        return false;
      }
      if (!this.controlValue.paginateElementsByHeight) {
        alert("paginate-elements-by-height value cannot be empty");
        this.controlValue.paginateElementsByHeight = 1400;
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
      if (!paperSizes.includes(this.controlValue.pdfFormat)) {
        alert(`pdf-format value should only be ${paperSizes}`);
        this.controlValue.pdfFormat = "a4";
        return false;
      }
      if (!this.controlValue.pdfOrientation) {
        alert("pdf-orientation value cannot be empty");
        this.controlValue.pdfOrientation = "portrait";
        return false;
      }
      if (!this.controlValue.pdfContentWidth) {
        alert("pdf-content-width value cannot be empty");
        this.controlValue.pdfContentWidth = "800px";
        return false;
      }
      return true;
    },
    onProgress(progress) {
      this.progress = progress;
      console.log(`PDF generation progress: ${progress}%`);
    },
    startPagination() {
      console.log(`PDF has started pagination`);
    },
    hasPaginated() {
      console.log(`PDF has been paginated`);
    },
    async beforeDownload({ html2pdf, options, pdfContent }) {
      console.log(`On Before PDF Generation`);
      await html2pdf()
        .set(options)
        .from(pdfContent)
        .toPdf()
        .get("pdf")
        .then((pdf) => {
          const totalPages = pdf.internal.getNumberOfPages();
          for (let i = 1; i <= totalPages; i++) {
            pdf.setPage(i);
            pdf.setFontSize(10);
            pdf.setTextColor(150);
            pdf.text(
              "Page " + i + " of " + totalPages,
              pdf.internal.pageSize.getWidth() * 0.88,
              pdf.internal.pageSize.getHeight() - 0.3
            );
          }
        })
        .save();
    },
    hasDownloaded(blobPdf) {
      console.log(`PDF has downloaded yehey`);
      this.pdfDownloaded = true;
      console.log(blobPdf);
    },
    domRendered() {
      console.log("Dom Has Rendered");
      this.contentRendered = true;
    },
    onBlobGenerate(blob) {
      console.log(blob);
    },
  },
};
</script>
