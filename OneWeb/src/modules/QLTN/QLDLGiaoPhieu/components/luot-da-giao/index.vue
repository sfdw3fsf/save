<template src="./index.html"></template>
<style scoped>
</style>
<style>
 .overlay-common .loading {
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.overlay-common.show {
  display: flex;
  justify-content: center;
}

.overlay-common {
  background-color: rgba(0, 0, 0, 0.7);
}

.overlay-common .loading-panel {
  display: flex;
  text-align: center;
  background-color: #fff;
  padding: 10px 15px;
  align-items: center;
  color: #035fcc;
  border: 1px solid #035fcc;
  border-radius: 7px;
}

.overlay-common .loading-panel .loading-content {
  flex-grow: 1;
  padding-left: 10px;
}
</style>
<script>
import Vue from 'vue'
import moment from 'moment'
import { export_json_to_excel } from '../../../TraCuuDichVu/Export2Excel'
import VueHtml2pdf from 'vue-html2pdf'
import DanhSachTon from '../danh-sach-ton'
import PhieuGiao from '../phieu-giao'

export default {
  name: 'luot-da-giao',
  components: { VueHtml2pdf, 'danh-sach-ton': DanhSachTon, 'phieu-giao': PhieuGiao },
  props: ['data', 'kyCuoc'],
  data: function () {
    return {
      footerSum: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<span>{{data.Sum}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      footerCount: function () {
        return { template: Vue.component('countTemplate', {
          template: `<span>{{data.Count}}</span>`,
          data () { return { data: {} } }
        })
        }
      },
      columns: [
        {
          fieldName: 'TEN_NVTC',
          headerText: 'Nhân viên',
          allowFiltering: true,
          allowSorting: false,
          isGroupedColumn: true
        },
        {
          fieldName: 'GIAOPHIEU_ID',
          headerText: 'Lượt giao',
          allowFiltering: true,
          allowSorting: false,
          width: 100
        },
        {
          fieldName: 'NGAYGIAO',
          headerText: 'Ngày giao',
          allowFiltering: true,
          allowSorting: false,
          width: 120
        },
        {
          fieldName: 'SOLUONG_GBC',
          headerText: 'Giấy báo giao',
          allowFiltering: true,
          allowSorting: false, width: 140,
          format: 'N0', type: 'number', textAlign: 'Right'
        },
        {
          fieldName: 'SOLUONG_HD',
          headerText: 'Hóa đơn giao',
          allowFiltering: true,
          allowSorting: false, width: 140,
          format: 'N0', type: 'number', textAlign: 'Right'
        },
        {
          fieldName: 'TONGTIEN_HD',
          headerText: 'Tiền hóa đơn giao',
          allowFiltering: true,
          allowSorting: false, width: 160,
          format: 'N0', type: 'number', textAlign: 'Right'
        },
        {
          fieldName: 'TRA_HD',
          headerText: 'Trả hóa đơn',
          allowFiltering: true,
          allowSorting: false, width: 140,
          format: 'N0', type: 'number', textAlign: 'Right'
        },
        {
          fieldName: 'TRA_TB',
          headerText: 'Trả trọn bộ',
          allowFiltering: true,
          allowSorting: false, width: 140,
          format: 'N0', type: 'number', textAlign: 'Right'
        },
        {
          fieldName: 'DS_DUONG',
          headerText: 'Danh sách đường',
          allowFiltering: true,
          allowSorting: false, width: 140,
        },
        {
          fieldName: 'DS_DOT',
          headerText: 'Danh sách đợt',
          allowFiltering: true,
          allowSorting: false, width: 140,
        },
        {
          fieldName: 'NVGIAO',
          headerText: 'NV Giao',
          allowFiltering: true,
          allowSorting: false, width: 140
        },
      ],
      dsLuotGiao: [],
      itemSelectedId: [],
      dsTon: [],
      dsPhieuGiao:[]
    }
  },
  computed: {
    itemSelected: function () {
      return this.dsLuotGiao.filter((item) => this.itemSelectedId.includes(item.GIAOPHIEU_ID))
    } 
  },
  validations: {},
  watch: {},
  created: async function () {
    if (this.data) {
      this.getLuotGiao()
    }
  },
  async mounted () {
    $('.table-content1 table thead tr').find('th:first').after('<th></th>')
  },
  methods: {
    onClickChon () {
      if (this.itemSelectedId.length === 0) {
        this.$toast.error('Chưa chọn lượt giao!')
        return false
      }

      if (this.itemSelectedId.length > 1) {
        this.$toast.error('Bạn không được chọn nhiều hơn 1 lượt giao!')
        return false
      }

      const dsChon = this.dsLuotGiao.filter((item) => this.itemSelectedId.includes(item.GIAOPHIEU_ID))
      const dsDot = dsChon.filter((item) => item.DS_DOT != '').map((item) => item.DS_DOT)
      const giaoPhieuIds = dsChon.map((item) => item.GIAOPHIEU_ID)
      this.$emit('select', { dsDot, giaoPhieuIds })
    },
    onClose () {
      this.$emit('close')
    },
    selectItemGrid (value) {
      this.itemSelectedId = value
    },
    onClickExport () {
      this.exportExcel()
    },
    onClickHuyGiao () {
      const dsChon = this.dsLuotGiao.filter((item) => this.itemSelectedId.includes(item.GIAOPHIEU_ID))
      if (dsChon.length === 0) {
        this.$toast.error('Chưa chọn lượt giao để hủy giao!')
        return false
      }

      if (dsChon.length > 1) {
        this.$toast.error('Bạn không được chọn nhiều hơn 1 lượt giao hủy giao!')
        return false
      }
      const nguoiCn = dsChon[0].NGUOI_CN.trim()
      // console.log(nguoiCn)
      // console.log(this.$auth.getMaNhanVien())
      // console.log(this.$auth.getUserName())
      if (nguoiCn !== this.$auth.getUserName()) {
        this.$toast.error(
          `Bạn không được hủy giao phiếu do user ${nguoiCn} giao !"`
        )
        return false
      }

      this.$bvModal
        .msgBoxConfirm(`Bạn có chắc chắn hủy giao hay không?`, {
          title: 'Thông báo',
          size: 'sm',
          buttonSize: 'md',
          okVariant: 'primary',
          headerClass: 'p-2 pb-0 border-bottom-0',
          footerClass: 'p-2 pt-0 border-top-0 justify-content-center',
          centered: true,
          noCloseOnBackdrop: true,
          okTitle: 'Đồng ý',
          cancelTitle: 'Không đồng ý',
        })
        .then((value) => {
          value && this.huyGiao([dsChon[0].GIAOPHIEU_ID])
        })
    },
    exportExcel () {
      let displayedData = this.dsLuotGiao
      if (displayedData.length === 0) {
        this.$toast.error('Không có dữ liệu để xuất Excel')
        return false
      }
      const tHeader = this.columns.map((item) => item.headerText)
      const filterVal = this.columns.map((item) => item.fieldName)
      const data = this.formatJson(filterVal, displayedData)
      export_json_to_excel({
        header: tHeader,
        data,
        filename: `export`,
        sheetname: 'export',
        bookType: 'xlsx'
      })
    },
    formatJson (filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          return v[j]
        })
      )
    },
    onClickDsTon: async function () {
      if (this.itemSelectedId.length == 0) {
        this.$toast.error('Chưa chọn lượt giao để in!')
        return false
      }
      this.$root.showLoading(true)
      await this.getDsTon()
      this.$refs.html2Pdf.generatePdf()
    },
    onClickPhieuGiao: async function () {
      if (this.itemSelectedId.length == 0) {
        this.$toast.error('Chưa chọn lượt giao để in!')
        return false
      }
      this.$root.showLoading(true)
      await this.getDsPhieuGiao()
      this.$refs.html2Pdf2.generatePdf()
    },
    onhasStartedGeneration () {
      this.$root.showLoading(true)
    },
    onhasGenerated (evt) {
      this.$root.showLoading(false)
    },
    onProgress (evt) {
      this.$root.showLoading(false)
    },
    getLuotGiao: async function () {
      const body = {
        pChuKyNo: `${moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM')}01`,
        pNVTCId: this.data.id,
        pKieu: 5, // this.$root.token.getPhanVungID() == 26 ? 5 : 1, // HP 5 else 1
        pLanTaoId: null
      }
      try {
        this.loading(true)
        var rs = await this.$root.context.post('/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-luot-giao', body)
        this.dsLuotGiao = rs.data.map((item) => ({
          ...item,
          TEN_NVTC: this.data.text,
          NGAYGIAO: item.NGAYGIAO.split(' ')[0].split('-').reverse().join('/'),
        }))
        // console.log(this.dsLuotGiao)
        this.loading(false)
      } catch (error) {}
    },
    huyGiao: async function (giaoPhieuId) {
      const body = {
        dsGiaoPhieu: giaoPhieuId,
        nguoiHuy: this.$auth.getMaNhanVien(),
        mayHuy: 'test',
        chuKyNo: this.kyCuoc
      }
      console.log(body)
      try {
        const { data } = await this.axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/huy-giao-phieu-nvtc-theo-luot', body)
        if (data && data.error == '200') {
          this.$toast.success('Hủy giao thành công!')
          this.getLuotGiao()
        } else {
          this.$toast.error('Hủy giao thất bại!')
        }
      } catch (error) {
        this.$toast.error('Hủy giao thất bại!')
      }
    },
    getDsTon: async function () {
      const param = {
        kyCuoc: this.kyCuoc, // "20210101",
        dsPhieuGiao: this.itemSelectedId[0],
        page: 0,
        size: 10000
      }
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/cong-no/bao-cao-in-ds-con-no`,
          param
        )
        this.dsTon = rs.data.data
        console.log(rs)
      } catch (error) {}
    },
    getDsPhieuGiao: async function () {
      const param = {
        kyCuoc: this.kyCuoc, // "20210301",
        giaoPhieuId: this.itemSelectedId[0],
        page: 0,
        size: 10000
      }
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/bao-cao/giao-in-bao-cao`,
          param
        )
        this.dsPhieuGiao = rs.data
      } catch (error) {}
    },
  },
}
</script>
