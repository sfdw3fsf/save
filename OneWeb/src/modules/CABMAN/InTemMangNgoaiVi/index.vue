<template src="./index.template.html"></template>

<script>
import XLSX from 'xlsx';
import VueHtml2pdf from 'vue-html2pdf';
var defaultParams = {
  p_spMuc: 1,
  p_spMuc2: 1,
  p_rdioCKb: 18
};

export default {
  components: {
    XLSX,
    VueHtml2pdf
  },
  name: 'InTemMangNgoaiVi',

  computed: {
    htmlToPdfOptions() {
      this.controlOptions.paginateElementsByHeight = this.selectedRecords.length * (this.params.p_rdioCKb / 10);
      return {
        filename: this.controlOptions.filename,
        image: {
          type: 'jpeg',
          quality: 2
        },
        enableLinks: true,
        html2canvas: {
          scale: this.controlOptions.pdfQuality,
          useCORS: true
        },
        jsPDF: {
          unit: 'cm',
          format: [10, this.selectedRecords.length * (this.params.p_rdioCKb / 10)],
          orientation: this.selectedRecords.length * (this.params.p_rdioCKb / 10) > 10 ? 'portrait' : 'landscape',
          precision: 2
        }
      };
    }
  },
  data() {
    return {
      disableBtnPrint: false,
      params: Object.assign({}, defaultParams),
      grcDS_TS: [],
      RadioOptions: [
        {
          text: '2.4 x 10 cm',
          value: 24
        },
        {
          text: '1.8 x 10 cm',
          value: 18
        },
        {
          text: '1.2 x 10 cm',
          value: 12
        }
      ],
      arrayImportFile: {
        filexcel: null
      },
      controlOptions: {
        showLayout: false,
        floatLayout: false,
        enableDownload: false,
        paginateElementsByHeight: 0,
        previewModal: true,
        filename: '',
        pdfQuality: 2,
        pdfOrientation: 'landscape',
        manualPagination: false
      },
      selectedRecords: []
    };
  },
  methods: {
    onRowSelected() {
      this.selectedRecords = this.$refs.grvDS_TS.getSelectedRecords();
    },
    async onBeforeDownload({ html2pdf, options, pdfContent }) {
      try {
        this.$root.showLoading(true);
        this.controlOptions.filename = `${this.$auth.getMaTinh()}}.pdf`;
        await html2pdf()
          .set(options)
          .from(pdfContent)
          .toPdf()
          .get('pdf')
          .then((pdf) => {
            const totalPages = pdf.internal.getNumberOfPages();
            for (let i = totalPages; i > this.params.p_spMuc2; i--) {
              pdf.deletePage(i);
            }
            let blob = pdf.output('blob');
            this.previewPrint(blob);
            this.$root.showLoading(false);
          });
      } catch (error) {
        this.$root.showLoading(false);
      }
    },
    previewPrint(data) {
      var blobURL = window.URL.createObjectURL(
        new Blob([data], {
          type: 'application/pdf'
        })
      );
      var iframe = document.createElement('iframe');
      const link = document.createElement('a');
      link.href = blobURL;
      link.download = this.controlOptions.filename;
      document.body.appendChild(iframe);
      iframe.style.display = 'none';
      iframe.src = blobURL;
      iframe.onload = function () {
        setTimeout(function () {
          iframe.focus();
          iframe.contentWindow.print();
        }, 1);
      };
    },
    btnInTem() {
      try {
        let soluongTem = 0;
        soluongTem = this.params.p_spMuc2;
        let dsqrcode = [];
        for (const row of this.selectedRecords) {
          for (let i = 0; i < soluongTem; i++) {
            let id_chitiet = row['ID_CHITIET'];
            let ma_da = row['MA_DU_AN'];
            let kyhieu = row['KYHIEU'];
            let ten_ttvt = row['TEN_TTVT'];
            let to_vt = row['TO_VT'];
            let item = {
              dataQr: `${id_chitiet} | ${ma_da}`,
              id_chitiet: id_chitiet,
              ma_da: ma_da,
              kyhieu: kyhieu,
              ten_ttvt: ten_ttvt,
              to_vt: to_vt,
              ten_diaban: this.$auth.getMaTinh()
            };
            dsqrcode.push(item);
          }
        }
        if (dsqrcode.length == 0) {
          return this.$toast.error('Hãy chọn các serial cần in');
        }
        this.generateQRCode(dsqrcode);
      } catch (error) {
        console.log(error);
      }
    },
    generateQRCode(dsItem) {
      try {
        this.$root.showLoading(true);
        let htmlContent = document.getElementById('htmlContent');
        htmlContent.style.height = `${(this.params.p_rdioCKb / 10) * this.selectedRecords.length}cm`;
        while (htmlContent.firstChild) {
          htmlContent.removeChild(htmlContent.firstChild);
        }
        let html = '';
        if (this.params.p_rdioCKb == 18) {
          html = `
          ${dsItem
            .map(
              (item, index) => `
          <div style="max-height: 1.8cm; overflow: hidden">
            <div style="width: 1.8cm; height: 10cm; border: 1px solid #000; transform-origin: top left; transform:rotate(-90deg); background:white; ${index > 0 ? '' : 'margin-top: 1.8cm'}">
            <div style="height: 5cm; border-bottom: 1px dashed #000; padding: 8px; display:flex; flex-direction: column;justify-content: space-around;">
              <div style="font-size: 6px; font-weight: bold; text-align: center">
                TSCĐ - ${item.ten_ttvt} - ${item.to_vt}
              </div>
              <div style="font-size: 6px; font-weight: bold; text-align: center">
                TSCĐ
              </div>
              <div style="height: 1.8cm; display:flex;align-items:center;justify-content:center">
                <img src="https://api.qrserver.com/v1/create-qr-code/?size=68x68&data=${item.dataQr}" alt="" width="48.708661417px">
              </div>
              <div style="font-size: 10px; font-weight: bold; text-align: center">
                ${item.ten_diaban}
              </div>
              <div style="font-size: 6px; font-weight: bold; text-align: center">
                ${item.id_chitiet}
              </div>
              <div style="font-size: 6px; font-weight: bold; text-align: center">
                ${item.ma_da}
              </div>
              <div style="font-size: 6px; font-weight: bold; text-align: center; padding-top: 10px">
                ${item.kyhieu}
              </div>
            </div>
          </div>
          </div>
          `
            )
            .join('')}
        `;
        }
        if (this.params.p_rdioCKb == 24) {
          html = `
          ${dsItem
            .map(
              (item, index) => `
          <div style="max-height: 2.4cm">
            <div style="width: 2.4cm; height: 10cm; border: 1px solid #000; transform-origin: top left; transform:rotate(-90deg); background:white; ${index > 0 ? '' : 'margin-top: 2.4cm'}">
            <div style="height: 5cm; border-bottom: 1px dashed #000; padding: 8px; display:flex; flex-direction: column;justify-content: space-around;">
              <div style="font-size: 8px; font-weight: bold; text-align: center">
                TSCĐ - ${item.ten_ttvt} - ${item.to_vt}
              </div>
              <div style="font-size: 8px; font-weight: bold; text-align: center; padding-top: 10px">
                TSCĐ
              </div>
              <div style="height: 2.4cm; display:flex;align-items:center;justify-content:center">
                <img src="https://api.qrserver.com/v1/create-qr-code/?size=68x68&data=${item.dataQr}" alt="" width="70.708661417px">
              </div>
              <div style="font-size: 12px; font-weight: bold; text-align: center">
                ${item.ten_diaban}
              </div>
              <div style="font-size: 8px; font-weight: bold; text-align: center">
                ${item.id_chitiet}
              </div>
              <div style="font-size: 8px; font-weight: bold; text-align: center">
                ${item.ma_da}
              </div>
              <div style="font-size: 8px; font-weight: bold; text-align: center; padding-top: 10px">
                ${item.kyhieu}
              </div>
            </div>
          </div></div>
          `
            )
            .join('')}
        `;
        }
        if (this.params.p_rdioCKb == 12) {
          html = `
          ${dsItem
            .map(
              (item, index) => `
          <div style="max-height: 1.2cm">
            <div style="width: 1.2cm; height: 10cm; border: 1px solid #000; transform-origin: top left; transform:rotate(-90deg); background:white; ${index > 0 ? '' : 'margin-top: 1.2cm'}">
            <div style="height: 5cm; border-bottom: 1px dashed #000; padding: 8px; display:flex; flex-direction: column;justify-content: space-around;">
              <div style="font-size: 4px; font-weight: bold; text-align: center">
                TSCĐ - ${item.ten_ttvt} - ${item.to_vt}
              </div>
              <div style="font-size: 4px; font-weight: bold; text-align: center">
                TSCĐ
              </div>
              <div style="height: 2.4cm; display:flex;align-items:center;justify-content:center">
                <img src="https://api.qrserver.com/v1/create-qr-code/?size=68x68&data=${item.dataQr}" alt="" width="40px">
              </div>
              <div style="font-size: 8px; font-weight: bold; text-align: center">
                ${item.ten_diaban}
              </div>
              <div style="font-size: 4px; font-weight: bold; text-align: center">
                ${item.id_chitiet}
              </div>
              <div style="font-size: 4px; font-weight: bold; text-align: center">
                ${item.ma_da}
              </div>
              <div style="font-size: 4px; font-weight: bold; text-align: center; padding-top: 10px">
                ${item.kyhieu}
              </div>
            </div>
          </div></div>
          `
            )
            .join('')}
        `;
        }
        htmlContent.appendChild(document.createRange().createContextualFragment(html));
        this.$refs.html2pdf.generatePdf();
      } catch (error) {
        this.$root.showLoading(false);
      }
    },
    tsbtnMauEx_Click() {
      let dt = [
        {
          ID_CHITIET: '',
          MA_DU_AN: '',
          KYHIEU: '',
          TEN_TTVT: '',
          TO_VT: ''
        }
      ];
      this.SaveXlsAndOpenFile('fileITMNV.xlsx', 'TS', dt);
    },
    SaveXlsAndOpenFile(fileName, sheetName, exportData) {
      let data1 = XLSX.utils.json_to_sheet(exportData);
      let wb = XLSX.utils.book_new(); // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, sheetName); // sheetAName is name of Worksheet
      XLSX.writeFile(wb, fileName);
    },
    tsbtnExcelDK_Click() {
      document.getElementById('fileUpload').click();
    },
    async Load_DL(dt) {
      this.disableBtnPrint = false;
      this.grcDS_TS = dt;
      dt.forEach(async (element) => {
        const rs = await this.$root.context.post('/web-cabman/ketcuoi/kiemTraKetCuoiTheoKyHieuInTem', {
          kyHieu: element.KYHIEU.trim(),
          maDuAn: element.MA_DU_AN.trim()
        });
        if (rs.data[0].KQ == 0) {
          this.disableBtnPrint = true;
          this.$toast.error(`${element.KYHIEU} | ${element.MA_DU_AN} không tồn tại trong hệ thống`);
          return;
        }
      });
    },
    resetParams() {
      this.params = Object.assign({}, defaultParams);
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error('Chưa chọn file nào!');
        return false;
      }
      if (this.arrayImportFile.filexcel.type != 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' && this.arrayImportFile.filexcel.type != 'application/vnd.ms-excel') {
        this.$toast.error('File không phải định dạng xlsx hoặc xls!');
        return false;
      }
      return true;
    },
    async onChange() {
      this.arrayImportFile.filexcel = this.$refs.fileUpload.$refs.input.files[0];
      try {
        this.loading(true);
        if (this.checkfile()) {
          const reader = new FileReader();
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result;
            const wb = XLSX.read(bstr, {
              type: 'binary'
            });
            /* Get first worksheet */
            const wsname = wb.SheetNames[0];
            const ws = wb.Sheets[wsname];
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, {
              header: 1
            });
            // retrieve headers (i.e. remove first row)
            let headers = data.shift(); // bỏ dòng header đầu tiên trong data
            const array1 = ['ID_CHITIET', 'MA_DU_AN', 'KYHIEU', 'TEN_TTVT', 'TO_VT'];
            const array2 = headers; // assuming headers is your second array

            const arraysAreEqual = array1.every((item) => array2.includes(item));

            if (arraysAreEqual) {
              console.log('The arrays are equal');
            } else {
              this.$toast.error('File không đúng định dạng');
              return;
            }
            // then build the json for each row
            var result = data.map(function (a) {
              var jsonRow = {};
              a.forEach(function (cellValue, cellIndex) {
                jsonRow[headers[cellIndex]] = cellValue;
              });
              headers.forEach((b) => {
                if (!Object.keys(jsonRow).includes(b)) {
                  jsonRow[b] = '';
                }
              });
              return jsonRow;
            });
            result.forEach((element, index) => {
              if (Object.values(element).every((x) => x === '')) {
                result.splice(index, 1);
              }
            });
            await this.Load_DL(JSON.parse(JSON.stringify(result)));
          };
          reader.readAsBinaryString(this.arrayImportFile.filexcel);
        }
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra: ' + error);
        return;
      } finally {
        this.$refs.fileUpload.reset();
        this.loading(false);
      }
      this.arrayImportFile.filexcel = null;
    }
  }
};
</script>

<style>
.vue-html2pdf {
  display: none;
}

.disable-btn {
  pointer-events: none;
  opacity: 0.8;
}

.disable-btn:hover {
  cursor: not-allowed;
  background: white !important;
}
</style>
