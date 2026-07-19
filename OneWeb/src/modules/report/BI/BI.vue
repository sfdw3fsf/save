<template>
  <div class="report-wrapper">
    <Split style="height: calc(100vh - 120px)">
      <SplitArea class="main-sidebar" :size="25">
        <div>
          <div class="input-icon-right mt-2">
            <input ref="txtLocBaoCao" v-model="textFiltered" type="text" class="form-control" />
            <span class="icon nc-icon-outline ui-1_zoom"></span>
          </div>
          <div id="treeDonvi">
            <ejs-treeview
              :selectedNodes="selectedNodes"
              :expandedNodes="expandedNodes"
              :allowMultiSelection="false"
              id="treeview"
              ref="tvDonvi"
              :fields="{
                dataSource: report_dataSource_computed,
                id: 'report_id',
                text: 'report_name',
                expanded: 'expanded',
                parentID: 'report_parent_id'
              }"
              :nodeSelected="tvBaoCao_FocusedNodeChanged"
            ></ejs-treeview>
          </div>
        </div>
      </SplitArea>
      <SplitArea :size="75">
        <div class="main">
          <breadcrumb :title_in="title ? title : 'Báo cáo'" />
          <div class="bip-report mt-2">
            <vue-nav></vue-nav>
            <div class="item-loading" v-if="loadingStatus">
              <span>Đang tải ...</span>
              <b-spinner class="d-block ml-3 mt-3" variant="success"></b-spinner>
              <button @click="huyLoading()" class="btn btn-success d-block mt-3 ml-2">Hủy</button>
            </div>
            <div v-else class="page-report">
              <vue-card label="Tham số báo cáo">
                <div class="row">
                  <template v-for="(i, ind) in listOfParamNameValues">
                    <div :key="i.name + ind" v-if="i.label" :class="listOfParamNameValues.length > 4 ? 'col-md-4' : 'col'">
                      <div class="info-row bip">
                        <div class="key w110">{{ i.label }}</div>
                        <div class="value">
                          <!-- <vue-select
                        :options="i.options ? i.options : []"
                        labelField="text"
                        v-if="i.UIType=='Menu'"
                        valueField="id"
                        labelWidth="0"
                        :disable="disableParams"
                        v-model="i.value"
                        @input="changeItem()"
                        ></vue-select> -->
                          <template v-if="!i.multiValuesAllowed">
                            <SelectExt
                              locale="vi"
                              v-model="i.value"
                              v-if="i.UIType == 'Menu'"
                              :options="i.options ? i.options : []"
                              popupWidth="auto"
                              :allowFiltering="true"
                              name="loaicap"
                              @change="changeItem(i.refreshParamOnChange)"
                              :disabled="disableParams || i.disabled || i.disableParams"
                              placeholder="--Chọn--"
                              dataTextField="text"
                              dataValueField="id"
                            />
                          </template>
                          <template v-else>
                            <!-- <ejs-multiselect
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
                            ></ejs-multiselect> -->
                            <ejs-multiselect
                              :dataSource="listOfParamNameValues[ind].options ? listOfParamNameValues[ind].options : []"
                              placeholder="--Chọn--"
                              mode="CheckBox"
                              popupWidth="fit-content"
                              v-model="listOfParamNameValues[ind].temp"
                              :allowFiltering="true"
                              v-if="i.UIType == 'Menu'"
                              locale="vi-VN"
                              :fields="{ text: 'text', value: 'id' }"
                              :showSelectAll="listOfParamNameValues[ind].selectAll"
                              @blur="changeItem(i.refreshParamOnChange)"
                              filterType="Contains"
                              selectAllText="Chọn tất cả"
                              unSelectAllText="Chọn tất cả"
                            />
                          </template>
                          <vue-date @change="changeItem(i.refreshParamOnChange)" v-if="i.UIType == 'Date'" v-model="i.value" :format="i.dateFormatString.toUpperCase()" type="date" :placeholder="i.label" :disabled="disableParams" />
                          <ejs-textbox @change="changeItem(i.refreshParamOnChange)" autocomplete="nope" class="form-control" :name="i.name" v-if="i.UIType == 'Text'" v-model="i.value" :disabled="i.disableParams" />
                        </div>
                      </div>
                    </div>
                  </template>
                  <input type="text" style="opacity: 0; position: absolute; bottom: 0; right: 0; width: 20px" />
                </div>
                <b-row class="row">
                  <b-col v-if="current_node.xuat_excel == 1" class="pt-3 align-content-center text-center">
                    <a class="btn btn-primary m-2" @click="exportFile('excel')"> <span class="icon one-file-chart"></span> Xuất XLS </a>
                    <a class="btn btn-primary m-2" @click="viewReport('grid')"> <span class="icon one-file-chart"></span> Xem Lưới </a>
                    <a class="btn btn-primary m-2" @click="exportFile('direct')"> <span class="icon one-file-chart"></span> Xuất trực tiếp </a>
                  </b-col>
                  <b-col v-else class="pt-3 align-content-center text-center">
                    <a class="btn btn-primary m-2" @click="viewReport('pdf')"> <span class="icon one-file-chart"></span> Xem báo cáo </a>
                    <a class="btn btn-primary m-2" @click="viewReport('grid')"> <span class="icon one-file-check"></span> Xem Lưới </a>
                    <a @click="printCustom()" class="btn btn-primary m-2"> <span class="icon one-print"></span> In báo cáo </a>
                    <b-dropdown class="m-2" id="dropdown-1" text="Xuất file" variant="primary">
                      <!-- <b-dropdown-item @click="exportFile('csv')">CSV</b-dropdown-item> -->
                      <!-- <b-dropdown-item @click="exportFile('excel')"
                        >EXCEL</b-dropdown-item
                      > -->
                      <b-dropdown-item @click="exportFile('direct')">Xuất Trực Tiếp </b-dropdown-item>
                      <b-dropdown-item @click="exportFile('excel')">XLS </b-dropdown-item>
                      <b-dropdown-item @click="exportFile('xlsx')">XLSX</b-dropdown-item>
                      <b-dropdown-item @click="exportFile('excel2000')">EXCEL2000</b-dropdown-item>
                      <!-- <b-dropdown-item @click="exportFile('html')"
                    >HTML</b-dropdown-item
                  > -->
                      <b-dropdown-item @click="exportFile('pdf')">PDF</b-dropdown-item>
                      <!-- <b-dropdown-item @click="exportFile('rtf')">RTF</b-dropdown-item> -->

                      <!-- <b-dropdown-item @click="exportFile('xml')"
                        >XML</b-dropdown-item
                      > -->
                    </b-dropdown>
                    <input class="check" type="checkbox" ref="downloadOption" />
                    Tải xuống file theo tên báo cáo
                  </b-col>
                </b-row>
              </vue-card>
              <b-row>
                <b-col v-if="this.path && this.path != '0'" md="12">
                  <!-- <vue-pdf-app
                    v-show="pdf"
                    class="rounded"
                    style="height: 520px"
                    :pdf="pdf"
                    @open="openPdf"
                    v-if="type == 'pdf'"
                  ></vue-pdf-app> -->

                  <vue-pdf-app @pages-rendered="pagesRendered" v-show="pdf" class="rounded" style="height: 520px" :pdf="pdf" @open="openPdf" v-if="type == 'pdf'"> </vue-pdf-app>
                  <!-- <iframe
                    style="width: 100%; height: 60vh"
                    id="frame"
                    v-else-if="type == 'html'"
                    :src="html"
                  ></iframe> -->
                  <HCMComponent :inputEdit="path == 'TINH/HOCHIMINH/QLTNA/RP_BC_HCM_TINH_BSS_88775'" @gridData="HCM_pushGrid" @change="HCM_pushParam" v-else-if="HCM_path.includes(path)" :listOfParamNameValues="listOfParamNameValues" />
                  <GridDataReport v-show="current_node.co_thutuc && type=='grid'" ref="GridDataReport" />
                </b-col>
              </b-row>
            </div>
          </div>
        </div>
      </SplitArea>
    </Split>
  </div>
</template>

<script>
//import { ValidationProvider, ValidationObserver, extend } from 'vee-validate'
import axios from 'axios'
const request = axios.CancelToken.source()
//import Select2 from 'v-select2-component'
import VuePdfApp from 'vue-pdf-app'
import 'vue-pdf-app/dist/icons/main.css'
import VueElement from '@/modules/contract/setup/DeclareLandline/components/VueElement.vue'
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import { CheckBoxSelection } from '@syncfusion/ej2-vue-dropdowns'
import { L10n } from '@syncfusion/ej2-base'
import HCMComponent from './HCMComponent.vue'
import GridDataReport from './GridDataReport.vue'
import moment from "moment"
L10n.load({
  'vi-VN': {
    dropdowns: {
      noRecordsTemplate: 'Không có dữ liệu',
      actionFailureTemplate: ''
    }
  }
})

export default {
  //components: { breadcrumb, Select2, ValidationProvider, ValidationObserver, extend, VuePdfApp },
  components: {
    VueElement,
    breadcrumb,
    VuePdfApp,
    HCMComponent,
    GridDataReport
  },
  name: 'BI',
  provide: {
    multiselect: [CheckBoxSelection]
  },
  computed: {
    path() {
      return this.$route.query.path ? this.$route.query.path : ''
    },
    title() {
      return this.$route.query.name ? this.$route.query.name : ''
    },
    report_dataSource_computed() {
      if (this.textFiltered) {
        return this.getFiltered(this.textFiltered)
      } else {
        return this.report_dataSource
      }
    }
  },
  watch: {
    path(val) {
      if (val) this.getReport(val, true)
    },
    title(val) {
      this.header.title = val.replaceAll('@@', '&')
    }
  },
  async mounted() {
    console.log(this.$root.token.getPhanVungID())
    this.loading(true)
    try {
      let response = await axios.post('/web-quantri/nguoidung/report_list')

      this.report_dataSource = response.data.data.map((item) => ({
        ...item,
        report_parent_id: response.data.data.filter((x) => x.report_id == item.report_parent_id).length > 0 ? item.report_parent_id : null,
        hasChildren: response.data.data.filter((x) => x.report_parent_id == item.report_id).length > 0 ? true : false
      }))
      setTimeout(() => {
        if (this.path) {
          let filter = this.report_dataSource.filter((item) => item.path == this.path)
          if (filter.length > 0) {
            this.gotoReport(filter[0].report_id)
            this.header.title = this.title
            if (this.path) this.getReport(this.path, true)
          } else {
            this.header.title = ""
            this.$toast.error('Không tìm thấy báo cáo')
            return
          }
        }
      }, 200)
    } catch (error) {
      console.log(error)
    }

    this.loading(false)
  },
  data() {
    return {
      HCM_path: ['TINH/HOCHIMINH/QLTNA/RP_HCM_BSS_89965_V999', 'TINH/HOCHIMINH/QLTNA/RP_HCM_BSS_88774_V999', 'TINH/HOCHIMINH/QLTNA/RP_BC_HCM_TINH_BSS_88775'],
      HCM_toanha: [],
      HCM_Grid: [],
      count: 0,
      original: [],
      selectedNodes: [0],
      expandedNodes_temp: [],
      expandedNodes: null,
      temp_text: '',
      disableParams: false,
      loadingStatus: false,
      filterDonvi: [],
      current_node: {},
      report_dataSource: [],
      header: {
        title: '',
        list: [
          { name: 'Báo cáo', link: { name: 'Ui.cards' } },
          {
            name: 'BI',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      html: '',
      listOfParamNameValues: [],
      pdf: null,
      showpdf: '',
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
      type: 'html',
      textFiltered: '',
      isPrint: false
    }
  },
  methods: {
    async DirectExport(param, id) {
      return new Promise(async (resolve, reject) => {
      const retry = () => {
                let request_id = moment().format("DDMMYYHHmmss") + String(Math.floor(Math.random() * 1000));
                let defaultapi = '/web-report/report/bi/run_v5?requestId=' + request_id
                const getData = (api = defaultapi) => {
                  console.log(api)
                  return new Promise(async (rs, rj) => {
                      
                      let rRes = await axios.post(api
                        ,
                        {
                          baocao_id: id,
                          // baocao_id : 41795,
                          params: param
                        },
                        { headers: { apiKey: 'x' }, responseType: 'blob' }
                      )
                      if(rRes.status == "202") {
                        const reader = new FileReader();
                        reader.addEventListener('loadend', (e) => {
                          const text = e.srcElement.result;
                          // this.$toast.error(text)
                          setTimeout(async () => {
                          rRes = await getData('/web-report' + text)
                            rs(rRes)
                          }, 60000);
                          console.log("Đợi xong")
                        });
                        let blb = new Blob([rRes.data], {type: 'text/plain'})
                        // Start reading the blob as text.
                        reader.readAsText(blb);
                      } else {
                        rs(rRes)
                      }
                  })
                } 

                return new Promise(async (resolve, reject) => {
                    let res = await getData()
                    resolve(res)
                })
              }

        try {
          let response = await retry()
          console.log("diẻct 1", response)
          if (response.headers && response.headers['content-type'] == "text/plain") {
            const reader = new FileReader();
            reader.addEventListener('loadend', (e) => {
              const text = e.srcElement.result;
              this.$toast.error(text)
              this.loading(false)
              return resolve(false)
            });
            let blb = new Blob([response.data], {type: 'text/plain'})
            // Start reading the blob as text.
            reader.readAsText(blb);
            return
          }
          console.log("diẻct 2", response)

          if(response.data && response.data.size == 0) {
            this.$toast.error("Không có dữ liệu. Vui lòng kiểm tra lại")
            this.loading(false)
            return resolve(false)
          }
          console.log("diẻct 3", response)

          const link = document.createElement('a')

          // Tell the browser to associate the response data to
          // the URL of the link we created above.
          link.href = window.URL.createObjectURL(new Blob([response.data]))
          let filenameDownload = (this.$refs.downloadOption && this.$refs.downloadOption.checked) ? this.title : this.path
          // Tell the browser to download, not render, the file.
          link.setAttribute('download', (filenameDownload + ".xlsx"))

          // Place the link in the DOM.
          document.body.appendChild(link)
          // Make the magic happen!
          link.click()
        } catch(err) {
          console.log(err)
        }

        return resolve(true)
      })
    },
    HCM_pushGrid(value) {
      this.HCM_Grid = value
    },
    HCM_pushParam(value) {
      this.HCM_toanha = value
    },
    pagesRendered() {
      if(this.isPrint == true) {
        let element = document.getElementById("print")
        element.click()
        this.isPrint == false
      }
    },
    async printCustom() {
      console.log("in báo cáo")
      this.isPrint = true
      try {
        await this.viewReport("pdf", false)
      } catch (err) {
        console.log(err)
        return false
      }
    },
    handleDownload() {
      const linkSource = `data:application/pdf;base64,${this.showpdf}`
      const downloadLink = document.createElement('a')
      const fileName = this.title + '.pdf'
      downloadLink.href = linkSource
      downloadLink.download = fileName
      downloadLink.click()
    },
    pushDonviCha(node) {
      if (node.report_parent_id != null) {
        let data = this.report_dataSource.filter((x) => x.report_id == node.report_parent_id)
        console.log(data)
        if (data.length > 0) {
          this.expandedNodes_temp.push(data[0].report_id)
          this.pushDonviCha(data[0])
        }
      }
    },
    gotoReport(report_id) {
      try {
        let filter = this.report_dataSource.filter((item) => item.report_id == report_id)
        this.expandedNodes_temp = []
        this.pushDonviCha(filter[0])
        this.expandedNodes = this.expandedNodes_temp
        console.log($('#treeDonvi').offset())
        this.selectedNodes = [report_id]
        // $('#treeDonvi').animate({
        //      scrollTop: $("#treeview_active").offset().top
        // }, 900);
        setTimeout(() => {
          $('#treeDonvi').animate(
            {
              scrollTop: $('#treeDonvi').scrollTop() - $('#treeDonvi').offset().top + $('#treeview_active').offset().top
            },
            500
          )
        }, 200)
      } catch (err) {}
    },
    getText: function (text) {
      return text
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '')
        .replace(/đ/g, 'd')
        .replace(/Đ/g, 'D')
    },
    getParents: function (parentID) {
      let rs = []
      let parent = this.report_dataSource.find((x) => x.report_id == parentID)
      if (parent != null) {
        rs.push(parent)
        if (parent.report_parent_id != null) {
          let ps = this.getParents(parent.report_parent_id)
          if (!(ps == null || ps.length == 0)) {
            for (let i = 0; i < ps.length; i++) {
              rs.push(ps[i])
            }
          }
        }
      }
      return rs
    },
    getFiltered: function (textFiltered) {
      let result = []
      if (this.report_dataSource != null) {
        let items = this.report_dataSource.filter((item) => {
          return this.getText(item.report_name.toLowerCase()).includes(this.getText(textFiltered.toLowerCase()))
        })
        if (!(items == null || items.length == 0)) {
          for (let item of items) {
            let ck = result.findIndex((x) => x.report_id == item.report_id)
            if (ck < 0) {
              result.push(item)
              if (item.report_parent_id != null) {
                let ps = this.getParents(item.report_parent_id)
                if (!(ps == null || ps.length == 0)) {
                  for (let jtem of ps) {
                    ck = result.findIndex((x) => x.report_id == jtem.report_id)
                    if (ck < 0) {
                      jtem.expanded = true
                      result.push(jtem)
                    }
                  }
                }
              }
            }
          }
        }
      }
      console.log(result)

      return result.sort((a, b) => (a.report_id < b.report_id ? -1 : 1))
    },
    txtLocBaoCao_KeyUp() {
      try {
        if (this.temp_text != this.$refs.txtLocBaoCao.value) {
          this.temp_text = this.$refs.txtLocBaoCao.value
          this.filterDonvi = this.report_dataSource.filter((item) => item.report_name.toUpperCase().includes(this.$refs.txtLocBaoCao.value.toUpperCase()))
        } else {
          if (this.filterDonvi.length > 0) this.filterDonvi.splice(0, 1)
          else {
            this.filterDonvi = this.report_dataSource.filter((item) => item.report_name.toUpperCase().includes(this.$refs.txtLocBaoCao.value.toUpperCase()))
          }
        }

        if (this.filterDonvi.length != 0) {
          this.gotoReport(this.filterDonvi[0].report_id)
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Không tìm thấy đơn vị phù hợp')
      }
    },
    tvBaoCao_FocusedNodeChanged(data) {
      console.log(data, "dhakdhask")
      let filter = this.report_dataSource.filter((item) => item.report_id == data.nodeData.id && item.report_name.trim() == data.nodeData.text.trim())
      console.log(filter)
      if (filter.length > 0 && filter[0].path && filter[0].report_name && filter[0].report_type == 3) {
        this.current_node = filter[0]
        if (filter[0].path != this.path || (filter[0].report_name.replaceAll('&', '@@').trim() != this.title)) {
          this.$router.push('/report/bi?path=' + filter[0].path + '&name=' + filter[0].report_name.replaceAll('&', '@@'))
        }
        //this.path = filter[0].path
        //this.title = filter[0].report_name
      }
    },
    huyLoading() {
      request.cancel()
      this.loadingStatus = false
    },
    changeItem(reload = false) {
      console.log(reload, 'Load lại tham số')
      if (reload) {
        this.getParam()
      }
    },
    convertToSlug(Text) {
      return Text.toLowerCase().replace(/ /g, '-')
    },
    async openPdf(pdfApp) {
      if (pdfApp.pdfViewer) {
        await pdfApp.pdfViewer.pagesPromise
        pdfApp.pdfViewer.currentScale = 1
      }
    },
    async exportFile(type) {
      this.type = type
      if (!(await this.viewReport(type, true))) return
    },
    async printFile() {
      await this.viewReport('pdf')
      if (this.pdf.includes('Invalid format requested: pdf')) {
        this.html = 'Báo cáo này không hỗ trợ định dạng PDF'
      } else {
        var blob = new Blob([this.pdf], { type: 'application/pdf' }) //this make the magic
        var blobURL = URL.createObjectURL(blob)
        const link = document.createElement('iframe')
        link.src = blobURL
        link.setAttribute('class', 'd-none') //or any other extension
        link.setAttribute('id', 'printWindow')
        document.body.appendChild(link)
        var frm = document.getElementById('printWindow').contentWindow
        frm.focus() // focus on contentWindow is needed on some ie versions
        frm.print()
        return false
      }
    },

    binaryAgent(str) {
      console.log(str)
      var newBin = str.split(' ')
      var binCode = []

      for (i = 0; i < newBin.length; i++) {
        binCode.push(String.fromCharCode(parseInt(newBin[i], 2)))
      }
      return binCode.join('')
    },
    base64ToArrayBuffer(base64) {
      // eslint-disable-next-line camelcase
      var binary_string = window.atob(base64)
      var len = binary_string.length
      var bytes = new Uint8Array(len)
      for (var i = 0; i < len; i++) {
        bytes[i] = binary_string.charCodeAt(i)
      }
      return bytes.buffer
    },
    async viewReport(type , exportFile = false) {
      // if(type === 'excel') {
      //   type = 'xlsx';
      // }
      return new Promise(async (resolve, reject) => {
        try {
          this.type = type
          this.loading(true)
          var temp = []
          console.log(this.listOfParamNameValues)
          var multiselect = this.listOfParamNameValues.filter((item) => item.multiValuesAllowed).length > 0 ? 1 : 0

          this.listOfParamNameValues.forEach((item) => {
            if (multiselect) {
              item.mvalue = []
              if (item.UIType == 'Menu') {
                console.log(item)
                if (item.temp && item.temp.length > 0) {
                  item.temp.forEach((dup) => {
                    console.log(dup, "đoạn push đây")
                    item.mvalue.push({ value: dup })
                  })
                } else {
                  item.mvalue.push({ value: item.value ? item.value : (item.defaultValue ? item.defaultValue : "") })
                }
              } else {
                if (item.value != null) item.mvalue.push({ value: item.value })
                else if(item.defaultValue != null) item.mvalue.push({ value: item.defaultValue })
                else item.mvalue.push({ value: "" })
              }
            }
            // if (!item.value) {
            //   item.value = item.defaultValue != null ? item.defaultValue : ''
            // }
            temp.push(item)
          })

          if (this.HCM_path.includes(this.path)) {
            temp.push({
              name: 'p_toanha_id',
              mvalue: this.HCM_toanha,
              value: this.HCM_toanha.map((i) => i.value).join(','),
              multiselect: true
            }),
              temp.push({
                name: 'p_ds',
                value: JSON.stringify(this.HCM_Grid),
                mvalue: [{ value: JSON.stringify(this.HCM_Grid) }],
                multiselect: true
              })
          }
          if (this.type == 'direct') {
            let params_v2 = temp.reduce((obj, item) => Object.assign(obj, { [item.name]: item.multiValuesAllowed ? item.mvalue.map((i) => i.value).join(',') : item.value }), {})
            if(!await this.DirectExport(params_v2, this.current_node.report_id))
              return resolve(false)
          } else if (this.type == 'grid') {

            if(!this.current_node.co_thutuc) {
              this.$toast.error('Báo cáo chưa hỗ trợ xem dạng lưới, bạn thử định dạng khác!')
              this.loading(false)
              return resolve(false)
            }
            this.$refs.GridDataReport.gridColumns = []
            this.$refs.GridDataReport.dataSource = []
            let params_v2 = temp.reduce((obj, item) => Object.assign(obj, { [item.name]: item.multiValuesAllowed ? item.mvalue.map((i) => i.value).join(',') : item.value }), {})
            // let params_v2 =  {
            //     "p_phanvung_id": "28",
            //     "p_dichvu_id": "4",
            //     "p_loaihinh_tb": "58",
            //     "p_loaihopdong": "1",
            //     "p_tungay": "01/09/2023",
            //     "p_denngay": "30/09/2023",
            //     "p_donvi": "283451",
            //     "p_donvicon": "283602"
            // }

            const retry = () => {
              let request_id = moment().format("DDMMYYHHmmss") + String(Math.floor(Math.random() * 1000));
              let defaultapi = '/web-report/report/bi/run_v7?requestId=' + request_id
              const getData = (api = defaultapi) => {
                return new Promise(async (resolve, reject) => {
                    
                    let rRes = await axios.post(api
                      ,
                      {
                        baocao_id: this.current_node.report_id,
                        // baocao_id : 41795,
                        params: params_v2
                      },
                      { headers: { apiKey: 'x' } }
                    )
                    if(rRes.status == "202") {
                      setTimeout(async () => {
                        rRes = await getData("/web-report" + rRes.headers.location)
                        resolve(rRes)
                      }, 60000);
                      console.log("Đợi xong")
                    } else {
                      resolve(rRes)
                    }
                })
              } 

              return new Promise(async (resolve, reject) => {
                  let res = await getData()
                  resolve(res)
              })
            }

            var response = await retry()
            
            
            if (response.data.error_code && response.data.error_code == 'BSS-00000500') {
              this.$toast.error('Lỗi từ server ' + response.data.message + "\n" + response.data.message_detail + "\n" + response.data.data)
              this.loading(false)
              return
            }
            let dataSrc = response.data ? response.data.data : []
            if (dataSrc.length > 0) {
              let collum = dataSrc[0]
              collum = Object.keys(collum).map((i) => ({
                fieldName: i,
                headerText: i.toUpperCase(),
                allowFiltering: true
              }))
              this.$refs.GridDataReport.path = this.path
              this.$refs.GridDataReport.fileName = moment().format("DD.MM.YYYY") + this.title
              this.$refs.GridDataReport.gridColumns = collum
              this.$refs.GridDataReport.dataSource = dataSrc
            }
          } else {
            let params_v2 = temp.reduce((obj, item) => Object.assign(obj, { [item.name]: item.multiValuesAllowed ? item.mvalue.map((i) => i.value).join(',') : item.value }), {})
            
            if(this.current_node.bc_vung && params_v2.P_PHANVUNG_ID && (params_v2.P_PHANVUNG_ID == 0 || params_v2.P_PHANVUNG_ID == 100)) {
              const retry = () => {
                let request_id = moment().format("DDMMYYHHmmss") + String(Math.floor(Math.random() * 1000));
                let defaultapi = '/web-report-khdn/report/run?requestId=' + request_id
                //let request_id = "271123090145545"
                //let defaultapi = '/web-report-khdn/report/run/redirect?requestId=' + request_id + '&start=0'
                const getData = (api = defaultapi) => {
                  console.log(api)
                  return new Promise(async (resolve, reject) => {
                      
                      let rRes = await axios.post(api
                        ,
                        {
                          baocao_id: this.current_node.report_id,
                          // baocao_id : 41795,
                          params: params_v2
                        },
                        { headers: { apiKey: 'x' }, responseType: 'blob' }
                      )
                      if(rRes.status == "202") {
                        const reader = new FileReader();
                        reader.addEventListener('loadend', (e) => {
                          const text = e.srcElement.result;
                          // this.$toast.error(text)
                          setTimeout(async () => {
                          rRes = await getData('/web-report-khdn' + text)
                            resolve(rRes)
                          }, 60000);
                          console.log("Đợi xong")
                        });
                        let blb = new Blob([rRes.data], {type: 'text/plain'})
                        // Start reading the blob as text.
                        reader.readAsText(blb);
                      } else {
                        resolve(rRes)
                      }
                  })
                } 

                return new Promise(async (resolve, reject) => {
                    let res = await getData()
                    resolve(res)
                })
              }

              var response = await retry()
              
              if (response.data.error_code && response.data.error_code == 'BSS-00000500') {
                this.$toast.error('Lỗi từ server ' + response.data.message + "\n" + response.data.message_detail + "\n" + response.data.data)
                this.loading(false)
                return resolve(false)
              }

              if (response.headers && response.headers['content-type'] == "text/plain") {
                const reader = new FileReader();
                reader.addEventListener('loadend', (e) => {
                  const text = e.srcElement.result;
                  this.$toast.error(text)
                  this.loading(false)
                  return resolve(false)
                });
                let blb = new Blob([response.data], {type: 'text/plain'})
                // Start reading the blob as text.
                reader.readAsText(blb);
                return
              }

              if(response.data && response.data.size == 0) {
                this.$toast.error("Không có dữ liệu. Vui lòng kiểm tra lại")
                this.loading(false)
                return resolve(false)
              }

              const link = document.createElement('a')

              // Tell the browser to associate the response data to
              // the URL of the link we created above.
              link.href = window.URL.createObjectURL(new Blob([response.data]))
              let filenameDownload = (this.$refs.downloadOption && this.$refs.downloadOption.checked) ? this.title : this.path
              // Tell the browser to download, not render, the file.
              link.setAttribute('download', (filenameDownload + ".xlsx"))

              // Place the link in the DOM.
              document.body.appendChild(link)
              // Make the magic happen!
              link.click()
            } else {
              let filenameDownload = (this.$refs.downloadOption && this.$refs.downloadOption.checked) ? this.title : this.path
              var response = await axios.post(
                '/web-report/report/bi/run_v3',
                {
                  "baocao_id": this.current_node.report_id,
                  // "params" : JSON.stringify(data)
                  report: this.path,
                  type: type,
                  multiselect: multiselect,
                  file_name: filenameDownload + "." + this.extension[this.type],
                  items: temp,
                  cancelToken: request
                },
                { headers: { apiKey: 'x' }, responseType: 'blob' }
              )

              if (response.headers && response.headers['content-type'] == "text/plain") {
                const reader = new FileReader();
                reader.addEventListener('loadend', (e) => {
                  const text = e.srcElement.result;
                  this.$toast.error(text)
                  this.loading(false)
                  return resolve(false)
                });
                let blb = new Blob([response.data], {type: 'text/plain'})
                // Start reading the blob as text.
                reader.readAsText(blb);
                return
              }

              if(response.data && response.data.size == 0) {
                this.$toast.error("Không có dữ liệu. Vui lòng kiểm tra lại")
                this.loading(false)
                return resolve(false)
              }

              if(exportFile) {
                const link = document.createElement('a')

                // Tell the browser to associate the response data to
                // the URL of the link we created above.
                link.href = window.URL.createObjectURL(new Blob([response.data]))
                
                // Tell the browser to download, not render, the file.
                link.setAttribute('download', (filenameDownload + "." + this.extension[this.type]))

                // Place the link in the DOM.
                document.body.appendChild(link)
                // Make the magic happen!
                link.click()
              } else {
                this.pdf = window.URL.createObjectURL(new Blob([response.data], {type: 'application/pdf'}))
              }
            }
            
          }
          this.loading(false)
        } catch (err) {
          this.loading(false)
          console.log(err)
        }
        resolve(true)
      })
    },
    async getParam() {
      this.disableParams = true
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
        if (item.multiValuesAllowed) {
          //var value=item.temp?item.temp.toString():'0'
          var value = item.temp ? item.temp : []
          param.parameterNameValues.listOfParamNameValues.item.push({
            defaultValue: value,
            temp: value,
            name: item.name,
            values: {
              item: value
            }
          })
        } else {
          param.parameterNameValues.listOfParamNameValues.item.push({
            defaultValue: item.value,
            name: item.name,
            values: {
              item: item.value
            }
          })
        }
        // param.parameterNameValues.listOfParamNameValues.item.push({
        //   defaultValue: item.value,
        //   name: item.name,
        //   values: {
        //     item: item.value,
        //   },
        // });
      })
      await axios
        .post('/web-report/report/bi/parameters', param)
        .then((response) => {
          if (response.data.listOfParamNameValues) {
            this.listOfParamNameValues = response.data.listOfParamNameValues.item
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

                  if (v.label == 'Tỉnh' && this.$root.token.getPhanVungID() != 0) {
                    this.$set(this.listOfParamNameValues[index], 'disableParams', true)
                  }

                  if (!v.multiValuesAllowed) {
                    if (!v.values.item.includes(v.defaultValue)) {
                      console.log(v.defaultValue, !v.values.item)
                      this.$set(this.listOfParamNameValues[index], 'value', v.values.item[0])
                    } else {
                      if (v.defaultValue == '-1') {
                        this.$set(this.listOfParamNameValues[index], 'value', v.values.item[0])
                      } else {
                        this.$set(this.listOfParamNameValues[index], 'value', v.defaultValue)
                      }
                    }
                  } else {
                    if (!v.defaultValue || v.defaultValue == '-1' || v.defaultValue == '*' || v.defaultValue.trim() == '') {
                      this.$set(this.listOfParamNameValues[index], 'temp', [])
                    } else {
                      let temp = v.defaultValue.split(',').map((x) => x.toString().trim())
                      this.$set(this.listOfParamNameValues[index], 'temp', temp)
                    }
                  }

                  this.$set(this.listOfParamNameValues[index], 'options', options)
                } else {
                  this.$set(this.listOfParamNameValues[index], 'value', null)
                }
              } else {
                if (v.label == 'username') {
                  this.$set(this.listOfParamNameValues[index], 'disableParams', true)
                }

                if (v.defaultValue) {
                  this.$set(this.listOfParamNameValues[index], 'value', v.defaultValue)
                } else {
                  this.$set(this.listOfParamNameValues[index], 'value', null)
                }
              }
            })
          }
        })
        .finally(() => {
          if (this.HCM_path.includes(this.path)) {
            this.type = 'params'
            this.listOfParamNameValues = this.listOfParamNameValues.filter((i) => i.name != 'p_toanha_id' && i.name != 'p_ds')
          }
          this.disableParams = false
        })

      this.loading(false)
    },
    getReport(path, init) {
      this.pdf = null
      this.html = null
      this.listOfParamNameValues = []
      this.loadingStatus = true
      axios
        .get('/web-report/report/bi/parameters?report=' + path, {
          headers: { apiKey: 'x' }
        })
        .then((response) => {
          if (response.data.listOfParamNameValues) {
            this.listOfParamNameValues = response.data.listOfParamNameValues.item
            const count = this.listOfParamNameValues.filter((item) => item.label == 'Tỉnh' || item.label == 'username' || item.label == "Tỉnh TP").length
            this.listOfParamNameValues.forEach((v, index) => {
              if (v.UIType === 'Menu') {
                var options = []
                if (v.values && v.values.item) {
                  v.values.item.forEach(function (x, i) {
                    options.push({ text: v.lovLabels.item[i], id: x })
                  })
                  this.$set(this.listOfParamNameValues[index], 'options', options)
                }

                if (init) {
                  if (this.listOfParamNameValues[index].defaultValue && this.listOfParamNameValues[index].defaultValue != '*' && this.listOfParamNameValues[index].defaultValue != '-1') {
                    this.$set(this.listOfParamNameValues[index], 'value', this.listOfParamNameValues[index].defaultValue)
                    if (this.listOfParamNameValues[index].multiValuesAllowed) {
                      let temp = this.listOfParamNameValues[index].defaultValue.split(',').map((x) => x.toString().trim())
                      console.log('test kaka', temp)
                      this.$set(this.listOfParamNameValues[index], 'temp', temp)
                    }
                  } else {
                    if (this.listOfParamNameValues[index].values && this.listOfParamNameValues[index].values.item) {
                      this.$set(this.listOfParamNameValues[index], 'value', this.listOfParamNameValues[index].values.item[0])
                    } else {
                      this.$set(this.listOfParamNameValues[index], 'value', null)
                    }
                    if (this.listOfParamNameValues[index].multiValuesAllowed) {
                      this.$set(this.listOfParamNameValues[index], 'temp', [])
                    }
                  }

                  // if(v.label && v.label == "Tỉnh") {
                  //   this.$set(
                  //     this.listOfParamNameValues[index],
                  //     "disabled",
                  //     true
                  //   );
                  //   this.$set(
                  //     this.listOfParamNameValues[index],
                  //     "value",
                  //     this.$root.token.getPhanVungID()
                  //   );
                  //   this.count = this.count + 1
                  // }
                }
                if (v.label && v.label == 'Tỉnh') {
                  this.count = this.count + 1
                  if (this.$root.token.getPhanVungID() != 0) {
                    this.$set(this.listOfParamNameValues[index], 'disableParams', true)
                    this.$set(this.listOfParamNameValues[index], 'value', this.$root.token.getPhanVungID())
                    if (this.count == count) {
                      this.changeItem(true)
                    }
                  }

                  console.log('vào tỉnh', this.listOfParamNameValues)
                }

                if (v.label && v.label == 'Tỉnh TP') {
                  this.count = this.count + 1
                  if (this.$root.token.getPhanVungID() != 0) {
                    if (this.count == count) {
                      this.changeItem(true)
                    }
                  }

                  console.log('vào tỉnh', this.listOfParamNameValues)
                }

              } else {
                if (init) {
                  if (this.listOfParamNameValues[index].defaultValue) {
                    this.$set(this.listOfParamNameValues[index], 'value', this.listOfParamNameValues[index].defaultValue)
                    if (this.listOfParamNameValues[index].multiValuesAllowed) {
                      let temp = this.listOfParamNameValues[index].defaultValue.split(',').map((x) => x.toString().trim())
                      console.log('test kaka', temp)
                      this.$set(this.listOfParamNameValues[index], 'temp', temp)
                    }
                  } else {
                    if (this.listOfParamNameValues[index].multiValuesAllowed) {
                      this.$set(this.listOfParamNameValues[index], 'temp', [])
                    }
                  }
                  if (v.UIType === 'Date') {
                    if (this.listOfParamNameValues[index].defaultValue) {
                      this.$set(this.listOfParamNameValues[index], 'value', this.listOfParamNameValues[index].defaultValue)
                    }
                  }
                  console.log(v.label)
                  // if (v.label && v.label == 'Tỉnh') {
                  //   this.count = this.count + 1
                  //   if (this.$root.token.getPhanVungID() != 0) {
                  //     this.$set(this.listOfParamNameValues[index], 'disableParams', true)
                  //     this.$set(this.listOfParamNameValues[index], 'value', this.$root.token.getPhanVungID())
                  //     if (this.count == count) {
                  //       this.changeItem(true)
                  //     }
                  //   }

                  //   console.log('vào tỉnh', this.listOfParamNameValues)
                  // }

                  if (v.label && v.label == 'username') {
                    this.$set(this.listOfParamNameValues[index], 'value', this.$root.token.getUserName())
                    this.count = this.count + 1
                    if (this.count == count) {
                      this.changeItem(true)
                    }
                    this.$set(this.listOfParamNameValues[index], 'disableParams', true)

                    console.log('vào username', this.count, count)
                  }
                }
              }

              if (!v.value) {
                this.$set(this.listOfParamNameValues[index], 'value', '')
              }
            })
            console.log(this.listOfParamNameValues)
            if (!init) this.viewReport('html')
          }
        })
        .finally(() => {
          if (this.HCM_path.includes(this.path)) {
            this.type = 'params'
            this.listOfParamNameValues = this.listOfParamNameValues.filter((i) => i.name != 'p_toanha_id' && i.name != 'p_ds')
          }
          this.loadingStatus = false
        })
    }
  }
}
</script>
<style>
.e-dropdownbase .e-list-item,
.e-dropdownbase .e-list-item.e-item-focus {
  text-overflow: clip;
  font-size: 12px;
}

.e-dropdownbase .e-list-item p,
.e-dropdownbase .e-list-item.e-item-focus p {
  word-wrap: break-word;
}

.custom-label-multi {
  overflow-wrap: break-word !important;
}

.page-report {
  width: 100%;
}
.bip-report .info-row .value {
  padding-left: 0px !important;
}
.bip-report .dropdown-menu {
  z-index: 99999;
}
.bip-report .item-loading {
  top: 50%;
  left: 50%;
  position: absolute;
}

.bip-report iframe table {
  width: auto !important;
}

.select2-dropdown,
.mx-datepicker-main {
  z-index: 9999999 !important;
}

.main-sidebar {
  max-height: calc(100vh - 100px);
  border: 1px solid rgb(204, 195, 195);
}

#treeDonvi {
  max-height: calc(100vh - 140px);
  overflow: scroll;
}
.report-wrapper {
  padding: 10px;
}

.e-treeview .e-list-item .e-ul {
  padding: 0 0 0 24px !important;
}

.e-disabled {
  pointer-events: none;
}
</style>
