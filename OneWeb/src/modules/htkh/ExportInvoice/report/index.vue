<template>
  <div id="apppdf">
    <vue-html2pdf :show-layout="controlOptions.showLayout" :float-layout="controlOptions.floatLayout"
                  :enable-download="controlOptions.enableDownload" :preview-modal="controlOptions.previewModal"
                  :paginate-elements-by-height="controlOptions.paginateElementsByHeight"
                  :pdf-quality="controlOptions.pdfQuality" :pdf-format="controlOptions.pdfFormat"
                  :pdf-orientation="controlOptions.pdfOrientation" :pdf-content-width="controlOptions.pdfContentWidth"
                  :manual-pagination="controlOptions.manualPagination" :html-to-pdf-options="htmlToPdfOptions"
                  @progress="onProgress($event)" @startPagination="startPagination()" @hasPaginated="hasPaginated()"
                  @beforeDownload="beforeDownload($event)" @hasDownloaded="hasDownloaded($event)" ref="html2Pdf">

      <section slot="pdf-content" v-if="!(thongTinThueBaos == null)">
        <template v-for="(item, index) in thongTinThueBaos">
          <section class="pdf-content">
            <section class="report-header">
              <section class="rp-row">
                <section class="rp-somay">
                  <div class="rp-row">
                    <span class="label">VIỄN THÔNG HÀ GIANG</span>
                    <span class="value"></span>
                  </div>
                </section>
                <section class="rp-title">
                  <div class="label">HÓA ĐƠN DỊCH VỤ VIỄN THÔNG</div>
                  <span>Tháng {{item.thang}} năm {{item.nam}}</span>
                </section>
                <section class="rp-tongdai">

                </section>
              </section>
            </section>

            <section class="report-info report-info-tttb">
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Đơn vị thu cước:</span>
                  <span class="value">{{item.ten_dv}}</span>
                </div>
                <div class="rp-col">
                  <span class="label">Quý khách:</span>
                  <span class="value">{{item.ten_tt}}</span>
                </div>
              </div>
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Địa chỉ:</span>
                  <span class="value">{{item.diachi_dv}}</span>
                </div>
                <div class="rp-col">
                  <span class="label">Địa chỉ TT:</span>
                  <span class="value">{{item.diachi_tt}}</span>
                </div>
              </div>
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Số tài khoản:</span>
                  <span class="value">{{item.ten_nh}}</span>
                </div>
                <div class="rp-col">
                  <div class="rp-row">
                    <div class="rp-col">
                      <span class="label">Mã TT:</span>
                      <span class="value">{{item.ma_tt}}</span>
                    </div>
                    <div class="rp-col">
                      <span class="label">Mã NV:</span>
                      <span class="value">{{item.manv_tc}}</span>
                    </div>
                  </div>
                </div>
              </div>

              <div class="rp-row">
                <div class="rp-col">
                  <div class="rp-row">
                    <div class="rp-col">
                      <span class="label">ĐT:</span>
                      <span class="value">{{item.so_dt}}</span>
                    </div>

                    <div class="rp-col">
                      <span class="label">- Fax:</span>
                      <span class="value">{{item.so_fax}}</span>
                    </div>
                  </div>
                </div>

                <div class="rp-col">
                  <div class="rp-row">
                    <div class="rp-col">
                      <span class="label">Số đại diện:</span>
                      <span class="value">{{item.sodaidien}}</span>
                    </div>

                    <div class="rp-col">
                      <span class="label">ĐT liên hệ:</span>
                      <span class="value">{{item.dienthoai_lh}}</span>
                    </div>
                  </div>
                </div>
              </div>

              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Số ĐT khiếu nại:</span>
                  <span class="value"></span>
                </div>

                <div class="rp-col">
                  <div class="rp-row">
                    <div class="rp-col">
                      <span class="label">Tổng tiền:</span>
                      <span class="value">{{ formatMoney(item.tongtientra) }}</span>
                    </div>
                  </div>
                </div>
              </div>

              <div class="rp-row">
                <div class="rp-col">
                  <span class="label"></span>
                  <span class="value" style="padding: 0px">Báo hỏng xin vui lòng gọi</span>
                </div>

                <div class="rp-col">
                  <span class="label">Bằng chữ:</span>
                  <span class="value">{{item.tongtientra1}}</span>
                </div>
              </div>

              <div class="rp-row">
                <div class="rp-col">
                  <span class="label"></span>
                  <span class="value"
                        style="padding: 0px">Thời gian khiếu nại: 1 tháng kể từ ngày nhận thông báo cước</span>
                </div>
              </div>
            </section>

            <section class="report-info" v-if="!(thongTinThueBao2[item.ma_tt] == null)">
              <table class="rp-table">
                <tr>
                  <th>STT</th>
                  <th>Số máy</th>
                  <th>Tiền TB</th>
                  <th>Cước tn & qt</th>
                  <th>RM novat</th>
                  <th>Simcard</th>
                  <th>Truy thu</th>
                  <th>Tổng tiền</th>
                </tr>

                <template v-for="(row, index2) in thongTinThueBao2[item.ma_tt]">
                  <tr>
                    <td class="text-center">{{row.stt}}</td>
                    <td>{{ row.ma_tb }}</td>
                    <td>{{ formatMoney(row.cuoc_tb) }}</td>
                    <td>{{ formatMoney(row.cuoc_sd) }}</td>
                    <td>{{ formatMoney(row.cuoc_novat) }}</td>
                    <td>{{ formatMoney(row.cuoc_sim) }}</td>
                    <td>{{ formatMoney(row.truythu) }}</td>
                    <td>{{ formatMoney(row.tongno) }}</td>
                  </tr>
                </template>
              </table>
            </section>

            <section class="report-info" v-if="!(thongTinThueBao3[item.ma_tt] == null)">
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Tiền khuyến mại gọi 10 phút:</span>
                  <span class="value">{{ formatMoney(thongTinThueBao3[item.ma_tt].km10ph) }}</span>
                </div>
              </div>
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Dịch vụ viễn thông chịu thuế:</span>
                  <span class="value">{{ formatMoney(thongTinThueBao3[item.ma_tt].cuoc_dv) }}</span>
                </div>
              </div>
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Dịch vụ viễn thông không chịu thuế:</span>
                  <span class="value">{{ formatMoney(thongTinThueBao3[item.ma_tt].cuoc_novat) }}</span>
                </div>
              </div>
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Tiền km:</span>
                  <span class="value">{{ formatMoney(thongTinThueBao3[item.ma_tt].cuoc_km) }}</span>
                </div>
              </div>
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Thuế VAT:</span>
                  <span class="value">{{ formatMoney(thongTinThueBao3[item.ma_tt].thue) }}</span>
                </div>
              </div>
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Tổng tiền:</span>
                  <span class="value">{{ formatMoney(thongTinThueBao3[item.ma_tt].tongno) }}</span>
                </div>
              </div>
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Tiền đ.cọc sdtk:</span>
                  <span class="value">{{ formatMoney(thongTinThueBao3[item.ma_tt].cuoc_dc) }}</span>
                </div>
              </div>
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Tiền trích thưởng:</span>
                  <span class="value">{{ formatMoney(thongTinThueBao3[item.ma_tt].cuoc_tth) }}</span>
                </div>
              </div>
              <div class="rp-row">
                <div class="rp-col">
                  <span class="label">Tiền thực trả:</span>
                  <span class="value">{{ formatMoney(thongTinThueBao3[item.ma_tt].thuctra) }}</span>
                </div>
              </div>
            </section>

            <section class="report-info" v-if="!(thongTinThueBao5[item.ma_tt] == null)">
              <template v-for="(thuebao, index3) in thongTinThueBao5[item.ma_tt]">
                <br>
                <div class="rp-row info-header">Số máy : {{ thuebao.ma_tb }}</div>

                <template v-if="thuebao.trong_nuoc_2.length > 0">
                  <div class="rp-row info-header">Trong nước : {{ formatMoney(thuebao.trong_nuoc_1) }}</div>
                  <table class="rp-table">
                    <tr>
                      <th>Ngày</th>
                      <th>Số bị gọi</th>
                      <th>Giờ BĐ</th>
                      <th>TG</th>
                      <th>Tiền</th>
                      <th>Nơi đến</th>
                    </tr>
                    <tr v-for="(cuocgoi, index5) in thuebao.trong_nuoc_2" :key="`chi_tiet_${index5}`">
                      <td>{{cuocgoi.ngay_bd}}</td>
                      <td>{{ cuocgoi.bigoi }}</td>
                      <td>{{ cuocgoi.gio_bd }}</td>
                      <td>{{ cuocgoi.tg_goi }}</td>
                      <td>{{ formatMoney(cuocgoi.tien) }}</td>
                      <td>{{ cuocgoi.ten_vung }}</td>
                    </tr>
                  </table>
                </template>

                <template v-if="thuebao.quoc_te_2.length > 0">
                  <div class="rp-row info-header">Quốc tế : {{ formatMoney(thuebao.quoc_te_1) }}</div>
                  <table class="rp-table">
                    <tr>
                      <th>Ngày</th>
                      <th>Số bị gọi</th>
                      <th>Giờ BĐ</th>
                      <th>TG</th>
                      <th>Tiền</th>
                      <th>Nơi đến</th>
                    </tr>
                    <tr v-for="(cuocgoi, index5) in thuebao.quoc_te_2" :key="`chi_tiet_${index5}`">
                      <td>{{cuocgoi.ngay_bd}}</td>
                      <td>{{ cuocgoi.bigoi }}</td>
                      <td>{{ cuocgoi.gio_bd }}</td>
                      <td>{{ cuocgoi.tg_goi }}</td>
                      <td>{{ formatMoney(cuocgoi.tien) }}</td>
                      <td>{{ cuocgoi.ten_vung }}</td>
                    </tr>
                  </table>
                </template>

                <div class="rp-row info-header" v-if="thuebao.nhan_tin > 0 ">Nhắn tin : {{ formatMoney(thuebao.nhan_tin) }}</div>

                <template v-if="thuebao.dich_vu_2.length > 0">
                  <div class="rp-row info-header">Dịch vụ : {{ formatMoney(thuebao.dich_vu_1) }}</div>
                  <table class="rp-table">
                    <tr>
                      <th>Ngày</th>
                      <th>Số bị gọi</th>
                      <th>Giờ BĐ</th>
                      <th>TG</th>
                      <th>Tiền</th>
                      <th>Nơi đến</th>
                    </tr>
                    <tr v-for="(cuocgoi, index5) in thuebao.dich_vu_2" :key="`chi_tiet_${index5}`">
                      <td>{{cuocgoi.ngay_bd}}</td>
                      <td>{{ cuocgoi.bigoi }}</td>
                      <td>{{ cuocgoi.gio_bd }}</td>
                      <td>{{ cuocgoi.tg_goi }}</td>
                      <td>{{ formatMoney(cuocgoi.tien) }}</td>
                      <td>{{ cuocgoi.ten_vung }}</td>
                    </tr>
                  </table>
                </template>

                <div class="rp-row info-header" v-if="thuebao.wap_data > 0">WAP / DATA : {{formatMoney(thuebao.wap_data) }}</div>

                <div class="rp-row info-header" v-if="thuebao.gprs > 0">GPRS : {{formatMoney(thuebao.gprs) }}</div>

                <div class="rp-row info-header" v-if="thuebao.ringtone > 0">RINGTONE : {{formatMoney(thuebao.ringtone) }}</div>

                <div class="rp-row info-header" v-if="thuebao.tien_goi_cuoc !== '' ">Tiền gói cước : {{formatMoney(thuebao.tien_goi_cuoc) }}</div>
              </template>
            </section>

          </section>

          <div class="html2pdf__page-break"/>
        </template>
      </section>

    </vue-html2pdf>
  </div>
</template>

<script>
  import VueHtml2pdf from 'vue-html2pdf'

  export default {
    components: {
      VueHtml2pdf
    },
    props: {
      fName: '',
      thongTinThueBaos: [],
      thongTinThueBao2: [],
      thongTinThueBao3: [],
      thongTinThueBao4: [],
      thongTinThueBao5: [],
      doRender: false
    },
    created() {
      //this.downloadPdf()
    },
    data() {
      return {
        progress: 0,
        generatingPdf: false,
        pdfDownloaded: false,
        controlOptions: {
          showLayout: false,
          floatLayout: false,
          enableDownload: true,
          previewModal: false,
          filename: "test.pdf",
          paginateElementsByHeight: 1100,
          pdfQuality: 2,
          pdfFormat: 'a4',
          pdfOrientation: 'portrait',
          pdfContentWidth: '100%',
          manualPagination: true
        }
      }
    },
    watch: {
      doRender: function(value) {
        this.downloadPdf()
      }
    },
    computed: {
      htmlToPdfOptions() {
        return {
          margin: [0.5, 0.5, 0.5, 0.5],
          filename: this.fName,
          image: {
            type: 'jpeg',
            quality: 0.98
          },
          enableLinks: true,
          html2canvas: {
            scale: this.controlOptions.pdfQuality,
            useCORS: true
          },
          jsPDF: {
            unit: 'in',
            format: this.controlOptions.pdfFormat,
            orientation: this.controlOptions.pdfOrientation
          }
        }
      }
    },
    methods: {
      async downloadPdf() {
        if (!(await this.validateControlValue())) return
        this.$refs.html2Pdf.generatePdf()
      },
      formatMoney(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
      },
      validateControlValue() {
        if (this.controlOptions.pdfQuality > 2) {
          alert('pdf-quality value should only be 0 - 2')
          this.controlOptions.pdfQuality = 2
          return false
        }
        if (!this.controlOptions.paginateElementsByHeight) {
          alert('paginate-elements-by-height value cannot be empty')
          this.controlOptions.paginateElementsByHeight = 1400
          return false
        }
        const paperSizes = [
          'a0', 'a1', 'a2', 'a3', 'a4', 'letter', 'legal', 'a5', 'a6', 'a7', 'a8', 'a9', 'a10'
        ]

        if (!paperSizes.includes(this.controlOptions.pdfFormat)) {
          alert(`pdf-format value should only be ${paperSizes}`)
          this.controlOptions.pdfFormat = 'a4'
          return false
        }
        if (!this.controlOptions.pdfOrientation) {
          alert('pdf-orientation value cannot be empty')
          this.controlOptions.pdfOrientation = 'portrait'
          return false
        }
        if (!this.controlOptions.pdfContentWidth) {
          alert('pdf-content-width value cannot be empty')
          this.controlOptions.pdfContentWidth = '100%'
          return false
        }
        return true
      },
      onProgress(progress) {
        this.progress = progress
        //console.log(`PDF generation progress: ${progress}%`);
      },
      startPagination() {
        //console.log(`PDF has started pagination`);
      },
      hasPaginated() {
        //console.log(`PDF has been paginated`);
      },
      async beforeDownload({ html2pdf, options, pdfContent }) {

      },
      hasDownloaded(blobPdf) {

      },
      onBlobGenerate(blob) {

      }
    }
  }
</script>

<style scoped>
  .no-position {
    position: unset !important;
  }

  .vue-html2pdf .pdf-preview {
    position: unset !important;
    width: 100% !important;
    transform: unset !important;
  }

  .vue-html2pdf .pdf-preview button {
    display: none !important;
  }

  .pdf-content {
    width: 100%;
    background: #fff;
    font-size: 13px;
    line-height: 185%;
  }

  .pdf-content .report-info {
    margin-top: 15px;
    width: 100%;
    display: block;
  }

  .pdf-content .rp-row {
    width: 100%;
    display: flex;
  }

  .pdf-content .rp-col {
    width: 50%;
    display: flex;
  }

  .pdf-content .report-header .rp-title {
    flex-grow: 1;
    text-align: center;
    /*font-weight: bold;*/
    font-size: 14px;
    padding: 10px 0;
  }

  .pdf-content .report-header .rp-somay,
  .pdf-content .report-header .rp-tongdai {
    width: 33%;
    /*border: 1px solid #9e9e9e;*/
    padding: 10px;
    display: flex;
    flex-flow: column;
  }

  .pdf-content .report-header .rp-somay .label,
  .pdf-content .report-header .rp-tongdai .label {
    width: auto;
    flex-grow: 1;
    font-weight: normal;
  }

  .pdf-content .report-info .info-header {
    /*font-weight: bold;
    text-decoration: underline;*/
  }

  .pdf-content .rp-row .label {
    white-space: nowrap;
  }

  .pdf-content .rp-row .value {
    padding-left: 5px;
  }

  .pdf-content .rp-row .label {
    /*width: 115px;*/
    flex: none;
    font-weight: bold;
  }

  .pdf-content .rp-cus .label {
    width: auto;
  }

  .pdf-content .text-right {
    text-align: right;
  }

  .pdf-content .text-center {
    text-align: center;
  }

  .pdf-content .text-bold {
    font-weight: bold;
  }

  .pdf-content .rp-table {
    width: 100%;
  }

  .pdf-content .rp-table th,
  .pdf-content .rp-table td {
    padding: 5px;
    line-height: 140%;
    border-right: 1px solid #999;
  }

  .pdf-content .rp-table tr th:first-child,
  .pdf-content .rp-table tr td:first-child {
    border-left: 1px solid #999;
  }

  .pdf-content .rp-table th {
    text-align: center;
  }

  .pdf-content .rp-table tr {
    page-break-inside: avoid;
    border-bottom: 1px solid #999;
  }

  .pdf-content .rp-table tr:first-child {
    border-top: 1px solid #999;
  }

  th {
    font-weight: normal;
  }
</style>
