<template src="./index.template.html"></template>
<script>
import VueHtml2pdf from "vue-html2pdf";
import reportContent from "./content";
export default {
  components: {
    VueHtml2pdf,
    reportContent,
  },
  props:{
    thongTinThueBao: {},
    thongTinThueBaoDiemCuoi: {},
    thongTinGianDo: [],
    thongTinBaoHong: [],
    doRender: false
  },
  created(){
    //this.downloadPdf();
  },
  data() {
    return {
      contentRendered: false,
      progress: 0,
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
    };
  },
  watch:{
    doRender: function(value){
      this.downloadPdf();
    },
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
    async downloadPdf() {
      if (!(await this.validateControlValue())) return;
      this.$refs.html2Pdf.generatePdf();
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
    onProgress(progress) {
      this.progress = progress;
      //console.log(`PDF generation progress: ${progress}%`);
    },
    startPagination() {
      //console.log(`PDF has started pagination`);
    },
    hasPaginated() {
      //console.log(`PDF has been paginated`);
    },
    async beforeDownload({ html2pdf, options, pdfContent }) {
      //console.log(`On Before PDF Generation`);
      // await html2pdf()
      //   .set(options)
      //   .from(pdfContent)
      //   .toPdf()
      //   .get("pdf")
      //   .then((pdf) => {
      //     const totalPages = pdf.internal.getNumberOfPages();
      //     for (let i = 1; i <= totalPages; i++) {
      //       pdf.setPage(i);
      //       pdf.setFontSize(10);
      //       pdf.setTextColor(150);
      //       pdf.text(
      //         "Page " + i + " of " + totalPages,
      //         pdf.internal.pageSize.getWidth() * 0.88,
      //         pdf.internal.pageSize.getHeight() - 0.3
      //       );
      //     }
      //   })
      //   .save();
    },
    hasDownloaded(blobPdf) {
      //console.log(`PDF has downloaded yehey`);
      //this.pdfDownloaded = true;
      //console.log(blobPdf);
    },
    domRendered() {
      //console.log("Dom Has Rendered");
      this.contentRendered = true;
    },
    onBlobGenerate(blob) {
      //console.log(blob);
    },
  },
};
</script>
