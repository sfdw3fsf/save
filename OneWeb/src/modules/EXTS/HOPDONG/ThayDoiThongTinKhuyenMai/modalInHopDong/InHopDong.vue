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
          <div class="row">
            <div class="col-md-12 col-sm-12">
              <div class="info-row">
                <div class="key w90">Mẫu in</div>
                <div class="value">
                  <div class="select-custom">
                    <!-- <Select2 ref="cboMauIn" :disabled='false' v-model="cboMauIn.value"
                             :options="cboMauIn.list" :settings="settingSelect2"/> -->
                    <SelectExt
                      v-model.trim="cboMauIn.value"
                      :settings="{dropdownParent: $refs['cboMauIn']}"
                      :dataSource="cboMauIn.list"
                      dataTextField="text"
                      dataValueField="id"
                    />
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
        </div>
      </div>
    </b-modal>
  </div>
</template>

<script>

import VuePdfApp from 'vue-pdf-app'
import 'vue-pdf-app/dist/icons/main.css'

export default {
  name: 'InHoSo',
  components: {
    VuePdfApp
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
          name: 'In phụ lục',
          active: true,
          icon_class: 'one-print',
          visible: true
        },
        {
          id: 'btnThoat',
          name: 'Thoát',
          active: true,
          icon_class: 'icon nc-icon-glyph ui-1_circle-remove',
          visible: true
        }
      ],
      cboMauIn: {
        list: [],
        disable: false,
        value: 0
      },
      frmProps: {
        txtMaGD: ''
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
      loaihd_id: 0,
      seri_id: 0,
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
      // let itemSelectIdx = this.dsMauIn.findIndex(item => item.baocao_id == newValue)
      // console.log(itemSelectIdx)
      // console.log(this.dsMauIn)
      // this.path = this.dsMauIn[itemSelectIdx].caulenh.trim()
      // await this.getReport(this.path)
    }
  },
  methods: {
    showModal () {
      this.$refs.popupInHopDong.show()
    },
    onShowModal () {
      this.getDataFromProps()
      this.frmInHopDong_Load()
    },
    onHideModal () {
      this.dsMauIn = []
      this.cboMauIn.list = []
      this.cboMauIn.value = 0
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
        this.seri_id = this.params.seri_id ? this.params.seri_id : this.seri_id
        this.loaihoadon_id = this.params.loaihoadon_id ? this.params.loaihoadon_id : this.loaihoadon_id
        this.hoadon = this.params.hoadon ? this.params.hoadon : this.hoadon
        if (this.ma_gd) {
          this.frmProps.txtMaGD = this.ma_gd
        }
      }
    },
    async frmInHopDong_Load () {
      // let vloaihdid = ''
      // vloaihdid = await this.FN_TT_HD_KHACHHANG(this.hdkh_id)
      // if (vloaihdid.toLocaleString() !== '-1') {
      //   this.loaihd_id = Number.parseInt(vloaihdid)
      // }
      await this.SP_LAY_DS_MAU_IN(100, 15);
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
      let rs = await this.$root.context.post('/web-quantri/danhmuc-chung/sp_lay_ds_mau_in', request)
      if (rs.data.length > 0) {
        this.dsMauIn = rs.data
        this.cboMauIn.list = rs.data.map((item) => {
          return {
            id: item.baocao_id,
            text: item.ten_bc
          }
        })
        if (this.cboMauIn.list.length > 0) {
          this.cboMauIn.value = this.cboMauIn.list[0].id
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
    getReport (path) {
      this.pdf = null
      this.html = null
      this.listOfParamNameValues = []
      API.GET_BI_PARAMS(this.axios, path).then(response => {
        if (response.data.listOfParamNameValues) {
          this.listOfParamNameValues =
            response.data.listOfParamNameValues.item
          this.listOfParamNameValues.forEach((v, index) => {
            let paramName = v.name
            let paramLabel = v.label
            let paramType = v.UIType
            console.log('ParamName: ' + paramName + ', ParamType:' + paramType + ', ParamLable:' + paramLabel)
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
      
    },
    onActionClick (action) {
      if (action.id === 'btnInHopDong') {
        this.tstbnIn_Click()
      }
      else if(action.id === 'btnThoat') {
        this.hide();
      }
    }
  }
}
</script>

<style scoped>

</style>
