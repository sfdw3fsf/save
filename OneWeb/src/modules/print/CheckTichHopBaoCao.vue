<template>
  <div class="box-form">
    <div class="row mb-2">
      <b-col>
        <div class="info-row">
          <div class="key w90">Path</div>
          <div class="value">
            <input type="text" ref="txtPath" class="form-control highlight" v-model="txtPath">
          </div>
        </div>
      </b-col>
    </div>
    <vue-card label="Tham số báo cáo">
      <div class="row">
        <div
          :class="listOfParamNameValues.length > 4 ? 'col-md-4' : 'col'"
          v-for="i in listOfParamNameValues"
          :key="i.name"
        >
          <div class="info-row">
            <div class="key w110">{{ i.label }}</div>
            <div class="value">
              <template v-if="!i.multiValuesAllowed">
                <ejs-dropdownlist
                  locale="vi"
                  v-model="i.value"
                  v-if="i.UIType == 'Menu'"
                  :dataSource="i.options ? i.options : []"
                  popupWidth="auto"
                  :allowFiltering="true"
                  name="loaicap"
                  :disabled="disableParams"
                  placeholder="--Chọn--"
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
                  placeholder="--Chọn--"
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
          <a class="btn btn-primary m-2" @click="getReport()">
            <span class="icon one-file-chart"></span> Xem tham số
          </a>
          <a class="btn btn-primary m-2" @click="viewReport('pdf')">
            <span class="icon one-file-chart"></span> Xem báo cáo
          </a>
        </b-col>
      </b-row>
    </vue-card>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings"
                ref="dialogPreviewBC" :position="position" :header="'Xem biên bản'"
                showCloseIcon="true" width="80%" height="100%" target="#app" :isModal="true">
      <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
                 isPopup="true" v-on="popupComponentEvts"></component>
    </ejs-dialog>
  </div>
</template>

<script>
import API from './InBienBan/API'
import moment from 'moment'

export default {
  name: 'CheckTichHopBaoCao',
  data () {
    return {
      animationSettings: {
        effect: 'Zoom'
      },
      position: {X: 'center', Y: 'top'},
      disableParams: false,
      listOfParamNameValues: [],
      popupComponent: null,
      popupComponentEvts: {},
      popupComponentData: null,
      popupComponentAttr: {},
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
      txtPath: '',
      title: 'Biên bản',
      type: 'pdf'
    }
  },
  methods: {
    changeItem () {
      this.getParam()
    },
    convertToSlug (Text) {
      return Text.toLowerCase().replace(/ /g, '-')
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
    async viewReport (type) {
      try {
        this.loading(true)
        this.type = type
        if (this.listOfParamNameValues.length <= 0) {
          this.$root.$toast.warning('Nhập tham số báo cáo !')
          return
        }
        let request = {
          report: this.txtPath,
          type: type,
          file_name:
            this.convertToSlug(this.title) + '_' + moment(new Date()).format('DD_MM_YYYY_HH_mm_ss') + '.' + this.extension[this.type],
          items: this.listOfParamNameValues
        }
        await API.RUN(this.axios, request)
          .then((response) => {
            console.log(response)
            if (response.data) {
              try {
                this.pdf = this.base64ToArrayBuffer(response.data)
              } catch(error) {
                console.log(error)
                this.$root.$toast.error('Xảy ra lỗi khi lấy dữ liệu biên bản in: ' + response.data)
              }
              if (this.pdf) {
                this.popupComponent = () => import('./components/PreviewReport')
                this.popupComponentData = {
                  pdf: this.pdf
                }
                this.$refs.dialogPreviewBC.show()
              }
            } else {
              this.$root.$toast.error('Biên bản từ hệ thống BIP trả về rỗng. Hãy thử lại!')
            }
          }).catch(error => {
            console.log(error)
            if (error.data) {
              this.$root.$toast.error('Xảy ra lỗi khi lấy dữ liệu biên bản in: ' + error.data.message)
            } else if (error.request) {
              console.log('Request: ', error.request)
              if (error.request.status == 500) {
                this.$root.$toast.error('API lấy biên bản in lỗi: ' + error.request.statusText)
              }
            } else {
              this.$root.$toast.error('Lỗi khi thực hiện lấy biên bản in: ' + error.message)
            }
          })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Lỗi xem báo cáo: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async getReport () {
      try {
        this.loading(true)
        this.pdf = null
        this.html = null
        this.listOfParamNameValues = []
        if (this.txtPath == '') {
          this.$root.$toast.warning('Nhập biểu mẫu báo cáo !')
          return
        }

        await API.GET_BI_PARAMS(this.axios, this.txtPath)
          .then((response) => {
            if (response.data.listOfParamNameValues) {
              this.listOfParamNameValues =
                response.data.listOfParamNameValues.item
              this.listOfParamNameValues.forEach((v, index) => {
                if (v.UIType === 'Menu') {
                  var options = []
                  if (v.values && v.values.item) {
                    v.values.item.forEach(function (x, i) {
                      options.push({text: v.lovLabels.item[i], id: x})
                    })
                    this.$set(
                      this.listOfParamNameValues[index],
                      'options',
                      options
                    )
                  }
                }
                if (!v.value) {
                  this.$set(
                    this.listOfParamNameValues[index],
                    'value',
                    ''
                  )
                }
              })
            }
          }).catch(error => {
            if (error.data) {
              this.$root.$toast.error('Lỗi khi lấy tham số báo cáo: ' + error.data.message)
            } else if (error.request) {
              if (error.request.status == 500) {
                this.$root.$toast.error('API lấy tham số báo cáo lỗi: ' + error.request.statusText)
              }
            } else {
              this.$root.$toast.error('Lỗi khi thực hiện lấy tham số báo cáo: ' + error.message)
            }
          })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Lỗi khi lấy tham số báo cáo: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async getParam () {
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
        .finally(() => {
          this.disableParams = false
        })

      this.loading(false)
    }
  }
}
</script>

<style scoped>

</style>
