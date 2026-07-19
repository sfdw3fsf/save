<template src="./index.template.html"></template>
<script>
import popupChonThueBao from './chon-thuebao'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import report from './report'
export default {
  components: {
    popupChonThueBao,
    report,
    SearchAccount
  },
  props: {
    dataInput: Object
  },
  data: function () {
    return {
      dsThueBao: [],
      thongTinTB: {
        type: Object,
        default: null
      },
      thongTinTBTraCuu: {},
      thongTinGianDo: [],
      thongTinBaoHong: [],
      doRenderPDF: false,
      showPanelReport: false,
      maThueBao: '',
      kieu: 0
    }
  },
  async mounted () {
    console.log('dataInput', this.dataInput)
    if (this.dataInput.DB_ID !== -1) {
      if (this.dataInput.Kieu === 0) {
        this.maThueBao = 'THUEBAO_ID|' + this.dataInput.DB_ID
        this.kieu = 0
      } else {
        this.maThueBao = 'HDTB_ID|' + this.dataInput.DB_ID
        this.kieu = 1
      }
    }
    await this.P_LAY_THONGTIN_THUEBAO()
  },
  created: async function () {},
  computed: {},
  methods: {
    P_LAY_THONGTIN_THUEBAO () {
      if (this.maThueBao === '') return false
      this.inputThueBao_enter()
    },
    radioDBorHD_click: function (args) {
      // this.$bvModal.msgBoxOk(
      //   'Chưa hỗ trợ thuê bao đang thi công, sẽ bổ sung trong thời gian tới.',
      //   {
      //     title: 'Thông báo',
      //     size: 'sm',
      //     centered: true,
      //     okTitle: 'Đóng',
      //     buttonSize: 'sm'
      //   }
      // )
    },
    bindData: async function (maThueBao, kieu) {
      this.maThueBao = maThueBao
      this.kieu = kieu
      await this.inputThueBao_enter()
    },
    popupChonThueBao_ok: async function () {
      this.$root.showLoading(true)
      try {
        await this.chonThueBao()
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    chonThueBao: async function () {},
    popupChonThueBao_selectedRowChanged: function (dataItem) {
      this.thongTinTB = dataItem
    },
    inputThueBao_enter: async function () {
      this.$root.showLoading(true)
      try {
        this.thongTinTB = {}
        // this.dsThueBao = await this.getDsThueBao()
        this.dsThueBao = await this.LAY_THONGTIN_THUEBAO(this.kieu, -1)
        console.log('🚀 ~ file: index.vue ~ line 80 ~ this.dsThueBao', this.dsThueBao)
        let showModal = false
        if (this.dsThueBao.length > 0) {
          if (this.dsThueBao.length === 1) {
            this.thongTinTB = this.dsThueBao[0]
            await this.showReport()
          } else showModal = true
        } else {
          showModal = true
          this.$root.toastError(
            'Không tìm thấy thông tin thuê bao ' + this.maThueBao
          )
        }
        if (showModal) this.$bvModal.show('modal-ChonThueBao')
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    inputThueBao_onChange: function () {
      this.thongTinTB = {}
    },
    async LAY_THONGTIN_THUEBAO (vkieu, vdichvuvt_id) {
      try {
        var data = {
          ma_tb: this.maThueBao,
          kieu: vkieu,
          dichvuvt_id: vdichvuvt_id
        }
        var rps = await this.$root.context.post('/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/ds-thue-bao-theo-ma-tb', data)
        return rps.data
      } catch (error) {
        return []
      }
    },
    getDsThueBao: async function () {
      let rs = await this.$root.context.post(
        '/web-cabman/danhmuc/layDsThueBaoTheoMaTB',
        {
          maThueBao: this.maThueBao,
          kieu: this.kieu
        }
      )
      return rs.data
    },
    getLyLichDayMay: async function (thueBaoID) {
      let rs = await this.$root.context.post(
        '/web-cabman/tracuu/traCuuLyLichDayMay',
        {
          id: thueBaoID
        }
      )
      return rs.data
    },
    getThongTinBaoHong: async function (thueBaoID) {
      // thueBaoID = 1680477
      let rs = await this.$root.context.post(
        '/web-cabman/tracuu/traCuuLichSuBaoHongThueBao',
        {
          id: thueBaoID
        }
      )
      return rs.data
    },
    showReport: async function () {
      let rs = await this.getLyLichDayMay(this.thongTinTB.THUEBAO_ID)
      this.thongTinTBTraCuu = rs.ThongTinThueBao[0]
      this.thongTinGianDo = rs.DanDoCap

      let rsBaoHong = await this.getThongTinBaoHong(this.thongTinTB.THUEBAO_ID)
      this.thongTinBaoHong = rsBaoHong

      this.doRenderPDF = !this.doRenderPDF
      this.showPanelReport = true
    },
    btnTraCuu_Click: async function () {
      this.$root.showLoading(true)
      try {
        if (this.thongTinTB.THUEBAO_ID == undefined) {
          // this.dsThueBao = await this.getDsThueBao()
          this.dsThueBao = await this.LAY_THONGTIN_THUEBAO(this.kieu, -1)
          if (this.dsThueBao.length > 0) {
            this.thongTinTB = this.dsThueBao[0]
          }
        }
        if (this.thongTinTB.THUEBAO_ID != undefined) {
          await this.showReport()
        } else {
          this.$root.toastError(
            'Không tìm thấy thông tin thuê bao ' + this.maThueBao
          )
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    btnInLyLich_Click: function () {
      if ($('#apppdf iframe').length !== 0) { $('#apppdf iframe')[0].contentWindow.print() }
    },
    btnTimThueBao_Click: function (args) {
      this.$refs.popupSearchAccount.show()
    },
    searchAccount_Close: async function (args) {
      this.maThueBao = args.ma_tb
      if (
        !(
          this.maThueBao == null ||
          this.maThueBao == undefined ||
          this.maThueBao == ''
        )
      ) { await this.inputThueBao_enter() }
      this.$refs.popupSearchAccount.hide()
    }
  }
}
</script>
<style>
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
  font-weight: bold;
  font-size: 18px;
  padding: 10px 0;
}

.vue-html2pdf .pdf-preview {
    position: inherit !important;
    transform: none !important;
    width: 100% !important;
    box-shadow: none !important;
}

.pdf-content .report-header .rp-somay,
.pdf-content .report-header .rp-tongdai {
  width: 33%;
  border: 1px solid #9e9e9e;
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
  font-weight: bold;
  text-decoration: underline;
}
.pdf-content .rp-row .label {
  white-space: nowrap;
}
.pdf-content .rp-row .value {
  padding-left: 5px;
}
.pdf-content .rp-row .label {
  width: 115px;
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
.bss-modal-tracuu-danhba {
}
.bss-modal-tracuu-danhba .breadcrumb-top {
  display: none;
}
.bss-modal-tracuu-danhba.bss-modal .modal-body .list-actions-top {
  position: inherit;
}
</style>
