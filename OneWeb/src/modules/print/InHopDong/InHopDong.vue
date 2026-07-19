<template>
  <div>
    <b-modal
      ref="popupInHopDong"
      size="lg"
      hide-header
      hide-header-close
      hide-footer
      body-class="p-0"
      no-close-on-backdrop
      @shown="onShowModal"
      @hide="onHideModal"
    >
      <div class="modal-content popup-box">
        <div class="popup-header">
          <div class="title">
            <span class="icon one-notepad"></span> In hồ sơ
          </div>
          <div class="close -ap icon-close" @click="hide"></div>
        </div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-for="item in actions" :key="item.id" @click="onActionClick(item)"
              :class="{disabled: !item.active}" v-show="item.visible">
            <a style="cursor: pointer;" :class="{disabled_color: !item.active}" :ref="item.id">
              <span class="icon" :class="item.icon_class"></span> {{ item.name }}
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin hợp đồng/ Phụ lục</div>
          <div class="row">
            <div class="col-md-12 col-sm-12">
              <div class="info-row">
                <div class="key w90">Mẫu in</div>
                <div class="value">
                  <div class="select-custom">
                    <Select2 ref="cboMauIn" :disabled='false' v-model="cboMauIn.value"
                             :options="cboMauIn.list" :settings="settingSelect2"/>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12 col-sm-12">
              <div class="info-row">
                <div class="key w90">Mã GD</div>
                <div class="value">
                  <input type="text" ref="txtMaGD" v-model="frmProps.txtMaGD"
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
                                   v-model="frmProps.dtpNgayLHD" />
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
                                   v-model="frmProps.dtpNgayYC" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </b-modal>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings"
                ref="dialogPreviewHopDong" :position="position" :header="'Xem báo cáo'"
                showCloseIcon="true" width="80%" height="100%" target="#app" :isModal="true">
      <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
                 isPopup="true" v-on="popupComponentEvts"></component>
    </ejs-dialog>
    <b-modal
      ref="popupXemThamSo"
      id="popupXemThamSo"
      size="lg"
      hide-header
      hide-header-close
      hide-footer
      body-class="p-0"
      no-close-on-backdrop
    >
      <div class="modal-content popup-box">
        <div class="popup-header">
          <div class="title">
            <span class="icon one-notepad"></span> Tham số báo cáo
          </div>
          <div class="close -ap icon-close" @click="hide1"></div>
        </div>
      </div>
      <div class="popup-body">
        <pre style="font-family: 'Roboto',serif; white-space: pre; font-size: 14px">{{ pretty(jsonstr) }}</pre>
      </div>
    </b-modal>
  </div>
</template>

<script>

import VuePdfApp from 'vue-pdf-app'
import 'vue-pdf-app/dist/icons/main.css'
import API from './API'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import NHOM_BC from '../NhomBC'

export default {
  name: 'InHoSo',
  components: {
    VuePdfApp,
    KDatePicker
  },
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
      animationSettings: {
        effect: 'Zoom'
      },
      position: {X: 'center', Y: 'top'},
      targetTop: '#app',
      settingSelect2: {
        language: 'vi'
      },
      actions: [
        {
          id: 'btnInHopDong',
          name: 'In HĐ',
          active: true,
          icon_class: 'one-print',
          visible: true
        },
        {
          id: 'btnXemThamSo',
          name: 'Xem tham số',
          active: false,
          icon_class: 'one-view-result',
          visible: true
        }
      ],
      cboMauIn: {
        list: [],
        disable: false,
        value: 0
      },
      frmProps: {
        txtMaGD: '',
        dtpNgayLHD: moment(new Date()).format('DD/MM/YYYY'),
        dtpNgayYC: moment(new Date()).format('DD/MM/YYYY')
      },
      popupComponent: null,
      popupComponentEvts: {},
      popupComponentData: null,
      popupComponentAttr: {},
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
      hdkh_id: 0,
      hdtb_id: 0,
      ma_gd: '',
      ngaylap_hd: '',
      ngay_yc: '',
      loaihd_id: 0,
      seri_id: 0,
      jsonstr: null,
      loaihoadon_id: 0,
      hoadon: '',
      type: 'pdf',
      disableParams: false,
      listOfParamNameValues: [],
      pdf: null,
      path: 'BIEUMAU/HOPDONGPHULUC/RP_BC_BSS_42734',
      title: 'Báo cáo',
      phanvungId: Number.parseInt(this.$root.token.getPhanVungID()),
      dsMauIn: []
    }
  },
  watch: {
    'cboMauIn.value': async function (newValue) {
      try {
        this.loading(true)
        let itemSelectIdx = this.dsMauIn.findIndex(item => item.baocao_id == newValue)
        console.log(itemSelectIdx)
        console.log(this.dsMauIn)
        this.path = this.dsMauIn[itemSelectIdx].caulenh.trim()
        await this.getReport(this.path)
        this.setActiveButton('btnXemThamSo', true)
      } catch (e) {
        console.log(e)
        this.$root.$toast.error('Có lỗi: ' + e.message)
      } finally {
        this.loading(false)
      }
    }
  },
  methods: {
    showModal () {
      this.$refs.popupInHopDong.show()
    },
    hide1() {
      this.$refs.popupXemThamSo.hide()
    },
    onShowModal () {
      this.getDataFromProps()
      this.frmInHopDong_Load()
    },
    onHideModal () {
      this.dsMauIn = []
      this.cboMauIn.list = []
      this.frmProps.txtMaGD = ''
      this.hdkh_id = 0
      this.pdf = null
      this.popupComponent = null
      this.popupComponentData = null
    },
    getDataFromProps () {
      console.log('params inhopdong:', this.params)
      if (this.params != null) {
        this.hdkh_id = this.params.hdkh_id ? this.params.hdkh_id : this.hdkh_id
        this.hdtb_id = this.params.hdtb_id ? this.params.hdtb_id : this.hdtb_id
        this.ma_gd = this.params.ma_gd ? this.params.ma_gd : this.ma_gd
        this.ngay_yc = this.params.ngay_yc ? this.params.ngay_yc : this.ngay_yc
        this.ngaylap_hd = this.params.ngaylap_hd ? this.params.ngaylap_hd : this.ngaylap_hd
        this.seri_id = this.params.seri_id ? this.params.seri_id : this.seri_id
        this.loaihoadon_id = this.params.loaihoadon_id ? this.params.loaihoadon_id : this.loaihoadon_id
        this.hoadon = this.params.hoadon ? this.params.hoadon : this.hoadon
        if (this.ma_gd) {
          this.frmProps.txtMaGD = this.ma_gd
        }
      }
    },
    async frmInHopDong_Load () {
      let vloaihdid = ''
      this.setActiveButton('btnXemThamSo', false)
      vloaihdid = await this.FN_TT_HD_KHACHHANG(this.hdkh_id)
      if (vloaihdid.toLocaleString() !== '-1') {
        this.loaihd_id = Number.parseInt(vloaihdid)
      }
      await this.SP_LAY_DS_MAU_IN(this.loaihd_id, NHOM_BC.HD_PL_PYC)
      if (this.ngaylap_hd) {
        this.frmProps.dtpNgayLHD = this.ngaylap_hd
      }
      if (this.ngay_yc) {
        this.frmProps.dtpNgayYC = this.ngay_yc
      }
    },
    async FN_TT_HD_KHACHHANG (hdkhid) {
      return this.GetData(await API.FN_TT_HD_KHACHHANG(this.axios, { param: hdkhid, type: 1 }))
    },
    hide () {
      this.$refs.popupInHopDong.hide()
    },
    convertToSlug (Text) {
      return Text.toLowerCase().replace(/ /g, '-')
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
    async SP_LAY_DS_MAU_IN (loahdid, nhombc) {
      let request = {
        'p_loaihd_id': loahdid,
        'p_nhombc_id': nhombc,
        'p_huonggiao_id': 0
      }
      let data = this.GetDataList(await API.SP_LAY_DS_MAU_IN(this.axios, request))
      if (data.length > 0) {
        this.dsMauIn = data
        this.cboMauIn.list = data.map((item) => {
          return {
            id: item.baocao_id,
            text: item.ten_bc
          }
        })
        if (this.cboMauIn.list.length > 0) {
          this.cboMauIn.value = this.cboMauIn.list[0].id
        }
      } else {
        this.dsMauIn = []
        this.cboMauIn.list = []
        this.cboMauIn.value = 0
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
          console.log(response)
          if (response.data) {
            this.pdf = this.base64ToArrayBuffer(response.data)
          } else {
            this.$root.$toast.warning('Không lấy được dữ liệu báo cáo')
          }
        }).catch(error => {
          console.log(error)
          if (error.data) {
            this.$root.$toast.error('Xảy ra lỗi khi lấy dữ liệu báo cáo: ' + error.data.message)
          } else if (error.request) {
            console.log(error.request)
          } else {
            console.log('Error', error.message)
          }
          if (error.data) {
            this.$root.$toast.error('Xảy ra lỗi khi lấy dữ liệu báo cáo: ' + error.data.message)
          } else if (error.request) {
            if (error.request.status == 500) {
              this.$root.$toast.error('API lấy báo cáo lỗi: ' + error.request.statusText)
            }
          } else {
            this.$root.$toast.error('Lỗi khi lấy dữ liệu báo cáo: ' + error.message)
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
    async getReport (path) {
      this.pdf = null
      this.html = null
      this.listOfParamNameValues = []
      await API.GET_BI_PARAMS(this.axios, path).then(response => {
        if (response.data.listOfParamNameValues) {
          this.listOfParamNameValues =
            response.data.listOfParamNameValues.item
          this.listOfParamNameValues.forEach((v, index) => {
            let paramName = v.name
            let paramLabel = v.label
            let paramType = v.UIType
            console.log('ParamName: ' + paramName + ', ParamType:' + paramType + ', ParamLable:' + paramLabel)
            paramName = paramName.toLowerCase()
            if (paramName.includes('phanvung_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.phanvungId.toString()
              )
            } else if (paramName.includes('hdtb_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.hdtb_id
              )
            } else if (paramName.includes('hdkh_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.hdkh_id.toString()
              )
            } else if (paramName.includes('ma_gd') || paramName.includes('magd')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.ma_gd.toString()
              )
            } else if (paramName.includes('loaihoadon') || paramName.includes('loaihoadon_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.loaihoadon_id
              )
            } else if (paramName.includes('seri') || paramName.includes('seri_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.seri_id
              )
            } else if (paramName.includes('hoadon')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.hoadon
              )
            }
            // TODO check tiep param
          })
          // this.viewReport('pdf')
        }
      })
    },
    async tstbnIn_Click () {
      try {
        this.loading(true)
        if (this.hdkh_id === 0) {
          this.$root.$toast.warning('Chưa có thông tin hợp đồng!')
          return
        }
        if (this.cboMauIn.value === 0) {
          this.$root.$toast.warning('Chưa chọn mẫu in hợp đồng!!')
          return
        }
        await this.viewReport('pdf')
        if (this.pdf) {
          this.popupComponent = () => import('../components/PreviewReport')
          this.popupComponentData = {
            pdf: this.pdf
          }
          await this.$refs.dialogPreviewHopDong.show()
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi:' + error.message)
      } finally {
        this.loading(false)
      }
    },
    pretty(value) {
      return JSON.stringify(value, null, 2);
    },
    setActiveButton(id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
    },
    btnXemThamSo_Click() {
      this.jsonstr = {
        report: this.path,
        items: this.listOfParamNameValues
      }
      this.$refs.popupXemThamSo.show()
    },
    onActionClick (action) {
      if (action.id === 'btnInHopDong') {
        this.tstbnIn_Click()
      } else if (action.id === 'btnXemThamSo') {
        this.btnXemThamSo_Click()
      }
    }
  }
}
</script>

<style scoped>

</style>
