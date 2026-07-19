<template>
  <div>
    <b-modal
      ref="popupInBienBan"
      size="lg"
      hide-header
      hide-header-close
      hide-footer
      body-class="p-0"
      no-close-on-backdrop
      @show="onShowModal"
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
        <!--      <vue-card label="Tham số báo cáo">
                <div class="row">
                  <div
                    :class="listOfParamNameValues.length > 4 ? 'col-md-4' : 'col'"
                    v-for="i in listOfParamNameValues"
                    :key="i.name"
                  >
                    <div class="info-row">
                      <div class="key w110">{{ i.label }}</div>
                      <div class="value">
                        &lt;!&ndash; <vue-select
                          :options="i.options ? i.options : []"
                          labelField="text"
                          v-if="i.UIType=='Menu'"
                          valueField="id"
                          labelWidth="0"
                          :disable="disableParams"
                          v-model="i.value"
                           @input="changeItem()"
                          ></vue-select> &ndash;&gt;
                        <template v-if="!i.multiValuesAllowed">
                          <ejs-dropdownlist
                            locale="vi"
                            v-model="i.value"
                            v-if="i.UIType == 'Menu'"
                            :dataSource="i.options ? i.options : []"
                            popupWidth="auto"
                            :allowFiltering="true"
                            name="loaicap"
                            @change="changeItem()"
                            :disabled="disableParams"
                            placeholder="&#45;&#45;Chọn&#45;&#45;"
                            :fields="{ value: 'id', text: 'text' }"
                          />
                        </template>
                        <template v-else>
                          <ejs-multiselect
                            id="multiselect"
                            locale="vi"
                            v-model="i.temp"
                            v-if="i.UIType == 'Menu'"
                            :enableGroupCheckBox="true"
                            :dataSource="i.options ? i.options : []"
                            popupWidth="auto"
                            name="loaicap"
                            placeholder="&#45;&#45;Chọn&#45;&#45;"
                            :fields="{ value: 'id', text: 'text' }"
                          ></ejs-multiselect>
                        </template>
                        <vue-date
                          v-if="i.UIType == 'Date'"
                          v-model="i.value"
                          :format="i.dateFormatString.toUpperCase()"
                          type="date"
                          :placeholder="i.label"
                          :disabled="disableParams"
                        />
                        <ejs-textbox
                          v-if="i.UIType == 'Text'"
                          v-model="i.value"
                          :disabled="disableParams"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <b-row class="row">
                  <b-col class="pt-3 align-content-center text-center">
                    <a class="btn btn-primary m-2" @click="viewReport('html')">
                      <span class="icon one-file-chart"></span> Xem HĐ
                    </a>
                    <a @click="printFile()" class="btn btn-primary m-2">
                      <span class="icon one-print"></span> In HĐ
                    </a>
                    <b-dropdown
                      class="m-2"
                      id="dropdown-1"
                      text="Xuất file"
                      variant="primary"
                    >
                      <b-dropdown-item @click="exportFile('csv')">CSV</b-dropdown-item>
                      <b-dropdown-item @click="exportFile('excel')"
                      >EXCEL
                      </b-dropdown-item
                      >
                      <b-dropdown-item @click="exportFile('excel2000')"
                      >EXCEL2000
                      </b-dropdown-item
                      >
                      <b-dropdown-item @click="exportFile('html')"
                      >HTML
                      </b-dropdown-item
                      >
                      <b-dropdown-item @click="exportFile('pdf')">PDF</b-dropdown-item>
                      <b-dropdown-item @click="exportFile('rtf')">RTF</b-dropdown-item>
                      <b-dropdown-item @click="exportFile('xlsx')"
                      >XLSX
                      </b-dropdown-item
                      >
                      <b-dropdown-item @click="exportFile('xml')">XML</b-dropdown-item>
                    </b-dropdown>
                  </b-col>
                </b-row>
              </vue-card>-->
        <!--      <b-row>
                <b-col v-if="this.path && this.path !== '0'" md="12">
                  <vue-pdf-app
                    class="rounded"
                    style="height: 520px"
                    :pdf="pdf"
                    @open="openPdf"
                    v-if="type === 'pdf'"
                  ></vue-pdf-app>
                  <iframe
                    style="width: 100%; height: 60vh"
                    id="frame"
                    v-else-if="type === 'html'"
                    :src="html"
                  ></iframe>
                </b-col>
                <b-row>
                  <pdf :src="srcUrl"></pdf>
                </b-row>
              </b-row>-->
      </div>
    </b-modal>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings"
                ref="dialogPreview" :position="position" :close="previewDialogClose" :header="'Preview Báo cáo'"
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
import API from './API'

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
          name: 'In HĐ',
          active: true,
          icon_class: 'one-print',
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
        dtpNgayLHD: '',
        dtpNgayYC: ''
      },
      html: '',
      listOfParamNameValues: [],
      pdf: null,
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
      type: 'html',
      disableParams: false,
      path: 'BIEUMAU/HOPDONGPHULUC/RP_BC_BSS_42734',
      title: 'Báo cáo',
      phanvungId: Number.parseInt(this.$root.token.getPhanVungID())
    }
  },
  methods: {
    previewDialogOpen () {

    },
    previewDialogClose () {

    },
    showModal () {
      this.$refs.popupInBienBan.show()
    },
    onShowModal () {
      this.getDataFromProps()
      this.getReport(this.path)
    },
    onHideModal () {

    },
    getDataFromProps () {
      if (this.params != null) {
        this.hdkh_id = this.params.hdkh_id ? this.params.hdkh_id : this.hdkh_id
        this.hdtb_id = this.params.hdtb_id ? this.params.hdtb_id : this.hdtb_id
        this.ma_gd = this.params.ma_gd ? this.params.ma_gd : this.ma_gd
        this.ngay_yc = this.params.ngay_yc ? this.params.ngay_yc : this.ngay_yc
        this.ngaylap_hd = this.params.ngaylap_hd ? this.params.ngaylap_hd : this.ngaylap_hd
        this.loaihd_id = this.params.loaihd_id ? this.params.loaihd_id : this.loaihd_id
      }
    },
    hide () {
      this.$refs.popupInBienBan.hide()
    },
    changeItem () {
      this.getParam()
    },
    convertToSlug (Text) {
      return Text.toLowerCase().replace(/ /g, '-')
    },
    async viewReport (type) {
      this.type = type
      let request = {
        report: this.path,
        type: type,
        file_name:
          this.convertToSlug(this.title) + '.' + this.extension[this.type],
        items: this.listOfParamNameValues
      }
      console.log(request)
      await API.RUN(this.axios, request)
        .then((response) => {
          if (response.data) {
            this.pdf = this.base64ToArrayBuffer(response.data)
            let blob = new Blob([this.pdf], {type: 'text/html'})
            this.html = URL.createObjectURL(blob)
            this.srcUrl = URL.createObjectURL(blob)
          } else {
            this.html = null
          }
        }).catch(error => {
          console.log(error)
          this.$root.$toast.error('Xảy ra lỗi khi lấy dữ liệu báo cáo')
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
    async getParam () {
      this.loading(true)
      let param = {
        report: this.path,
        parameterNameValues: {
          listOfParamNameValues: {
            item: []
          }
        }
      }
      this.listOfParamNameValues.forEach((item) => {
        param.parameterNameValues.listOfParamNameValues.item.push({
          defaultValue: item.value,
          name: item.name,
          values: {
            item: item.value
          }
        })
      })
      await API.POST_BI_PARAMS(this.axios, param)
        .then((response) => {
          console.log('vao day', response)
          if (response.data.listOfParamNameValues) {
            this.listOfParamNameValues =
              response.data.listOfParamNameValues.item
            this.listOfParamNameValues.forEach((v, index) => {
              if (v.UIType === 'Menu') {
                var options = []
                if (v.lovLabels && v.lovLabels.item) {
                  v.lovLabels.item.forEach(function (x, i) {
                    options.push({
                      text: v.lovLabels.item[i],
                      id: v.values.item[i]
                    })
                  })

                  if (!v.values.item.includes(v.defaultValue)) {
                    this.$set(this.listOfParamNameValues[index], 'value', null)
                  } else {
                    this.$set(
                      this.listOfParamNameValues[index],
                      'value',
                      v.defaultValue
                    )
                  }
                  this.$set(
                    this.listOfParamNameValues[index],
                    'options',
                    options
                  )
                } else {
                  this.$set(this.listOfParamNameValues[index], 'value', null)
                }
              } else {
                if (v.defaultValue) {
                  this.$set(
                    this.listOfParamNameValues[index],
                    'value',
                    v.defaultValue
                  )
                } else {
                  this.$set(
                    this.listOfParamNameValues[index],
                    'value',
                    null
                  )
                }
              }
            })
          }
          console.log(this.listOfParamNameValues)
        })
      this.loading(false)
    },
    getReport (path) {
      this.pdf = null
      this.html = null
      this.listOfParamNameValues = []
      API.GET_BI_PARAMS(this.axios, path).then(response => {
        console.log(response)
        if (response.data.listOfParamNameValues) {
          this.listOfParamNameValues =
            response.data.listOfParamNameValues.item
          this.listOfParamNameValues.forEach((v, index) => {
            let paramName = v.name
            let paramLable = v.lable
            let paramType = v.UIType
            console.log(paramName)
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
            }
          })
          this.viewReport('pdf')
        }
      })
    },
    async printFile () {
      await this.viewReport('pdf')
      let blob = new Blob([this.pdf], {type: 'application/pdf'}) // this make the magic
      let blobURL = URL.createObjectURL(blob)
      const link = document.createElement('iframe')
      link.src = blobURL
      link.setAttribute('class', 'd-none') // or any other extension
      link.setAttribute('id', 'printWindow')
      document.body.appendChild(link)
      let frm = document.getElementById('printWindow').contentWindow
      frm.focus() // focus on contentWindow is needed on some ie versions
      frm.print()
      return false
    },
    async print () {
      try {
        this.loading(true)
        await this.viewReport('pdf')
        await this.$refs.dialogPreview.show()
      } catch (error) {
        this.$root.$toast.error(error.message)
      } finally {
        this.loading(false)
      }
    },
    async exportFile (type) {
      this.type = type
      await this.viewReport(type)
      const url = window.URL.createObjectURL(new Blob([this.pdf]))
      const link = document.createElement('a')
      link.href = url
      link.setAttribute(
        'download',
        this.convertToSlug(this.title) + '.' + this.extension[this.type]
      ) // or any other extension
      document.body.appendChild(link)
      link.click()
    },
    onActionClick (action) {
      if (action.id === 'btnInHopDong') {
        this.print()
      }
    }
  }
}
</script>

<style scoped>

</style>
