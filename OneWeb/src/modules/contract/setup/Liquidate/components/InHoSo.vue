<template>
  <div>
    <ejs-dialog ref="popupInBienBan" cssClass="mt-20" :visible="false" :position="{X: 'center', Y: 10}" width="80%" :header='"In hồ sơ"' showCloseIcon=true target='#app' :isModal="true" :open="onShowModal">
      <div class="list-actions-top">
        <ul class="list">
         <li @click.prevent="btnIn_Click"
              :class="{disabled: !btnPrintEnabled}">
            <a href="#" style="cursor: pointer;" :class="{disabled_color: !btnPrintEnabled}">
              <span class="icon one-print"></span> In HĐ
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin hợp đồng/Phụ lục</div>
          <div class="row">
            <div class="col-md-12 col-sm-12">
              <div class="info-row">
                <div class="key w90">Mẫu in</div>
                <div class="value">
                  <ejs-dropdownlist
                    :dataSource='cboMauIn.list'
                    :fields="{ text: 'ten_bc', value: 'baocao_id' }"
                    v-model="cboMauIn.value"
                    :allowFiltering="true"
                    :filtering='filtering'
                    @change="cboMauIn_SelectedValueChanged"></ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12 col-sm-12">
              <div class="info-row">
                <div class="key w90">Mã GD</div>
                <div class="value">
                  <input type="text" ref="txtMaGD" v-model="txtMaGD.Text"
                         class="form-control highlight">
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="info-row">
                <div class="key w90">Ngày lập HĐ</div>
                <div class="value">
                  <div class="input-icon-right">
                    <k-date-picker ref="dtpNgayBG" format="DD/MM/YYYY" type="date"
                                   v-model="dtpNgayLHD.Value" />
                  </div>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="info-row">
                <div class="key w90">Ngày yêu cầu</div>
                <div class="value">
                  <div class="input-icon-right">
                    <k-date-picker ref="dtpNgayBG" format="DD/MM/YYYY" type="date"
                                   v-model="dtpNgayYC.Value" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </ejs-dialog>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="{effect: 'Zoom'}"
                ref="dialogPreview" :position="{X: 'center', Y: 'top'}" :header="'Preview Báo cáo'"
                showCloseIcon="true" width="80%" height="100%" target="#app" :isModal="true">
      <vue-pdf-app
        class="rounded"
        style="height: 100%"
        :pdf="pdf"
        @open="openPdf"
        v-if="type === 'pdf'"
      ></vue-pdf-app>
    </ejs-dialog>
  </div>
</template>

<script>
import VuePdfApp from 'vue-pdf-app'
import 'vue-pdf-app/dist/icons/main.css'
import { Query } from '@syncfusion/ej2-data'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import { Filter } from "@syncfusion/ej2-vue-grids"

const NHOM_BC = Object.freeze(
  {
    'HD_PL_PYC': 900,
    'BB_NGHIEMTHU': 901,
    'BC_BAOHONG_119': 902
  }
)
const API = {
  RUN: async (axios, request) => axios.post('/web-report/report/bi/run', request),
  GET_BI_PARAMS: async (axios, path) => axios.get(`/web-report/report/bi/parameters?report=${path}`),
  FN_MAP_ID: async (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/fn_map_id', data),
  SP_LAY_DS_MAU_IN: async (axios, request) => axios.post('/web-quantri/danhmuc-chung/sp_lay_ds_mau_in', request)
}

export default {
  name: 'InHoSo',
  components: {
    VuePdfApp,
    KDatePicker
  },
  provide: { dropdownlist: [Filter] },
  props: {
    params: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  data () {
    return {
      listOfParamNameValues: [],
      pdf: null,
      hdkh_id: 0,
      hdtb_id: 0,
      phieu_id: 53768878,
      huonggiao_id: 0,
      nvth_id: 0,
      cv_thuchien: '',
      ma_gd: '',
      ngay_lhd: '',
      ngay_yc: '',
      baohong: false,
      kt_thaydoi: false,
      tentram_tb: '',
      loaihd_id: 0,
      n_hdkh_id: '',
      n_hdtb_id: '',
      n_phieu_id: '53768878',
      n_baohong_id: 0,
      cboMauIn: {
        list: [],
        disable: false,
        value: null
      },
      extension: {
        csv: 'csv',
        excel: 'xls',
        excel2000: 'xls',
        html: 'html',
        pdf: 'pdf',
        rtf: 'rft',
        xlsx: 'xlsx',
        xml: 'xml'
      },
      chkLuuIn: {
        checked: true,
        disable: false
      },
      txtMaGD: { Text: '' },
      dtpNgayLHD: { Value: moment(new Date()).format('DD/MM/YYYY') },
      dtpNgayYC: { Value: moment(new Date()).format('DD/MM/YYYY') },
      type: 'pdf',
      path: 'BIEUMAU/BIENBANNGHIEMTHU/RP_BC_BSS_42901',
      title: 'Biên bản',
      phanvungId: Number.parseInt(this.$root.token.getPhanVungID()),
      dsMauIn: []
    }
  },
  computed: {
    btnPrintEnabled () {
      return this.cboMauIn.value >= 0
    }
  },
  methods: {
    showModal () {
      this.$refs.popupInBienBan.show()
    },
    onShowModal () {
      this.getDataFromProps()
      this.frmInBienBan_Load()
    },
    hide () {
      this.$refs.popupInBienBan.hide()
    },
    convertToSlug (Text) {
      return Text.toLowerCase().replace(/ /g, '-')
    },
    async frmInBienBan_Load () {
      try {
        this.SP_LAY_DS_MAU_IN(this.loaihd_id, NHOM_BC.HD_PL_PYC, this.huonggiao_id)
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    filtering: function (args) {
      var query = new Query()
      // frame the query based on search string with filter type.
      query = (args.text != "") ? query.where("ten_bc", "contains", args.text, false) : query
      // pass the filter data source, filter query to updateData method.
      args.updateData(this.cboMauIn.list, query)
    },
    getDataFromProps () {
      if (this.params) {
        this.n_hdkh_id = this.params.n_hdkh_id ? this.params.n_hdkh_id : this.n_hdkh_id
        this.n_hdtb_id = this.params.n_hdtb_id ? this.params.n_hdtb_id : this.n_hdtb_id
        this.txtMaGD.Text = this.ma_gd = this.params.ma_gd ? this.params.ma_gd : this.ma_gd

        this.ngay_yc = this.params.ngay_yc ? this.params.ngay_yc : this.ngay_yc
        this.ngaylap_hd = this.params.ngaylap_hd ? this.params.ngaylap_hd : this.ngaylap_hd
        this.n_baohong_id = this.params.n_baohong_id ? this.params.n_baohong_id : this.n_baohong_id
        this.huonggiao_id = this.params.huonggiao_id ? this.params.huonggiao_id : this.huonggiao_id
        this.tentram_tb = this.params.tentram_tb ? this.params.tentram_tb : this.tentram_tb
        this.n_phieu_id = this.params.n_phieu_id ? this.params.n_phieu_id : this.n_phieu_id
        this.cv_thuchien = this.params.cv_thuchien ? this.params.cv_thuchien : this.cv_thuchien
        this.nvth_id = this.params.nvth_id ? this.params.nvth_id : this.nvth_id
      }
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    async FN_MAP_ID (dk, idNeo, table) {
      let result = ''
      const req = {
        dk: dk,
        id_neo: idNeo,
        table: table
      }
      let data = this.GetData(await API.FN_MAP_ID(this.axios, req))
      if (data) {
        result = data
      }
      return result
    },
    async SP_LAY_DS_MAU_IN (loahdid, nhombc, huonggiaoid) {
      let request = {
        'p_loaihd_id': loahdid,
        'p_nhombc_id': nhombc,
        'p_huonggiao_id': huonggiaoid
      }
      let data = this.GetDataList(await API.SP_LAY_DS_MAU_IN(this.axios, request))
      if (data.length > 0) {
        this.dsMauIn = this.cboMauIn.list = data
        if (this.cboMauIn.list.length > 0) {
          setTimeout(() => {
            this.cboMauIn.value = this.cboMauIn.list[0]['baocao_id']
          }, 500)
        }
      }
    },
    async viewReport (type) {
      this.type = type
      let request = {
        report: this.path,
        type: type,
        file_name:
          this.convertToSlug(this.title) + '_' + this.hdkh_id + '.' + this.extension[this.type],
        items: this.listOfParamNameValues
      }
      await API.RUN(this.axios, request)
        .then((response) => {
          try {
            console.log(response)
            if (response.data) {
              this.pdf = this.base64ToArrayBuffer(response.data)
            } else {
              this.$root.$toast.warning('Không lấy được dữ liệu biên bản để in')
            }
          } catch (error) {
            console.error(error)
            this.$root.$toast.error('Không lấy được dữ liệu biên bản để in')
          }
        }).catch(error => {
          console.log(error)
          this.$root.$toast.error('Xảy ra lỗi khi lấy dữ liệu biên bản in' + error.data.message)
        })
    },
    getReport (path) {
      this.pdf = null
      this.listOfParamNameValues = []
      API.GET_BI_PARAMS(this.axios, path).then(response => {
        if (response.data.listOfParamNameValues) {
          this.listOfParamNameValues = response.data.listOfParamNameValues.item
          this.listOfParamNameValues.forEach((v, index) => {
            let paramName = v.name

            for (const key in this.params) {
              if (Object.hasOwnProperty.call(this.params, key)) {
                if (paramName.includes(key)) {
                  this.$set(
                    this.listOfParamNameValues[index],
                    'value',
                    this.params[key]
                  )
                }
              }
            }

            // if (paramName.includes('phanvung_id')) {
            //   this.$set(
            //     this.listOfParamNameValues[index],
            //     'value',
            //     this.phanvungId.toString()
            //   )
            // } else if (paramName.includes('hdtb_id')) {
            //   this.$set(
            //     this.listOfParamNameValues[index],
            //     'value',
            //     this.n_hdtb_id
            //   )
            // } else if (paramName.includes('hdkh_id')) {
            //   this.$set(
            //     this.listOfParamNameValues[index],
            //     'value',
            //     this.n_hdkh_id
            //   )
            // } else if (paramName.includes('ma_gd')) {
            //   this.$set(
            //     this.listOfParamNameValues[index],
            //     'value',
            //     this.ma_gd.toString()
            //   )
            // } else if (paramName.includes('phieu_id')) {
            //   this.$set(
            //     this.listOfParamNameValues[index],
            //     'value',
            //     this.n_phieu_id
            //   )
            // } else if (paramName.includes('ma_kh')) {
            //   this.$set(
            //     this.listOfParamNameValues[index],
            //     'value',
            //     this.n_ma_kh
            //   )
            // } else if (paramName.includes('ma_hd')) {
            //   this.$set(
            //     this.listOfParamNameValues[index],
            //     'value',
            //     this.n_ma_hd
            //   )
            // }
          })

          console.log('listOfParamNameValues after filling data', this.listOfParamNameValues)
        }
      })
    },
    base64ToArrayBuffer (base64) {
      let binaryString = window.atob(base64)
      let len = binaryString.length
      let bytes = new Uint8Array(len)
      for (let i = 0; i < len; i++) {
        bytes[i] = binaryString.charCodeAt(i)
      }
      return bytes.buffer
    },
    async openPdf (pdfApp) {
      if (pdfApp.pdfViewer) {
        await pdfApp.pdfViewer.pagesPromise
        pdfApp.pdfViewer.currentScale = 1
      }
    },
    async btnIn_Click () {
      try {
        this.loading(true)
        if (this.phieu_id === 0) {
          this.$root.$toast.warning('Chưa có thông tin phiếu!')
          return
        }
        if (this.cboMauIn.value === 0) {
          this.$root.$toast.warning('Chưa chọn mẫu in!!')
          return
        }
        await this.viewReport('pdf')
        await this.$refs.dialogPreview.show()
      } catch (error) {
        console.error(error)
        this.$root.$toast.error(error.message)
      } finally {
        this.loading(false)
      }
    },
    cboMauIn_SelectedValueChanged () {
      const bcId = this.cboMauIn.value
      let itemSelectIdx = this.dsMauIn.findIndex(item => item.baocao_id == bcId)
      if (itemSelectIdx >= 0) {
        const _path = this.dsMauIn[itemSelectIdx]
        if (_path) {
          this.path = _path.caulenh.trim()
          this.getReport(this.path)
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
