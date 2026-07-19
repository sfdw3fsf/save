<template>
  <div>
    <b-modal
      ref="popupInPhieuThu"
      size="xl"
      hide-header
      hide-header-close
      hide-footer
      body-class="p-0"
      no-close-on-backdrop
      @show="frmInPhieuThu_Load"
      @hide="onHideModal"
    >
      <div class="modal-content popup-box">
        <div class="popup-header">
          <div class="title">
            <span class="icon one-notepad"></span> In phiếu thu
          </div>
          <div class="close -ap icon-close" @click="hideModal"></div>
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
          <div class="row">
            <div class="col-md-6 col-sm-6 col-xs-12">
              <div class="box-form">
                <div class="legend-title">Thông tin phiếu thu</div>
                <div class="row">
                  <div class="info-row">
                    <div class="key w120">Mã giao dịch</div>
                    <div class="value">
                      <input type="text" ref="txtMaGD" class="form-control highlight" v-model="formProps.txtMaGD" v-on:keyup.enter="txtMaGD_KeyPress">
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Loại tiền trả</div>
                    <div class="value">
                      <Select2 ref="cboLoaiTien" :disabled='false' v-model="cboLoaiTien.value"
                               :options="cboLoaiTien.list" :settings="settingSelect2"/>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-md-6 col-sm-6 col-xs-12">
              <div class="box-form">
                <div class="legend-title">Thông tin khách hàng</div>
                <div class="row">
                  <div class="info-row">
                    <div class="key w120">Tên khách hàng</div>
                    <div class="value">
                      <input type="text" ref="txtMaGD" class="form-control " v-model="formProps.txtTenKH">
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Địa chỉ KH</div>
                    <div class="value">
                      <input type="text" ref="txtSeri" class="form-control" v-model="formProps.txtDiaChiKH">
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-12">
              <DataGrid ref="dgvDanhSach" class="table-result table-gachle" v-bind:columns="dgvDanhSach.headers"
                        v-bind:dataSource="dgvDanhSach.data" :showColumnCheckbox="false"
                        :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                        :enabled-select-first-row="true"
                        @selectedRowChanged="dgvDanhSach_SelectionChanged"
              ></DataGrid>
            </div>
          </div>
        </div>
      </div>
    </b-modal>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings"
                ref="dialogPreviewPhieuThu" :position="position" :header="'Preview phiếu thu'"
                showCloseIcon="true" width="80%" height="100%" target="#app" :isModal="true">
      <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
                 isPopup="true" v-on="popupComponentEvts"></component>
    </ejs-dialog>
  </div>
</template>

<script>
import API from './API'
import VuePdfApp from 'vue-pdf-app'
import 'vue-pdf-app/dist/icons/main.css'

export default {
  name: 'InPhieuThu',
  components: {
    VuePdfApp
  },
  props: {
    ma_gd: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      animationSettings: {
        effect: 'Zoom'
      },
      position: {X: 'center', Y: 'top'},
      targetTop: '#app',
      actions: [
        {
          id: 'tsbtnInPhieu',
          name: 'Phiếu thu',
          active: true,
          icon_class: 'one-print',
          visible: true
        }
      ],
      popupComponent: null,
      popupComponentEvts: {},
      popupComponentData: null,
      popupComponentAttr: {},
      settingSelect2: {
        language: 'vi'
      },
      dgvDanhSach: {
        data: [],
        headers: [
          {
            fieldName: 'MA_TB',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'TEN_KH',
            headerText: 'Tên KH',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'DIACHI_KH',
            headerText: 'Địa chỉ KH',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'so_seri',
            headerText: 'Số seri',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'LOAIHINH_TB',
            headerText: 'Loại hình TB',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'GOICUOC',
            headerText: 'Gói cước',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'TIEN_TC',
            headerText: 'Tiền TC',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            type: 'Number',
            format: 'N0'
          }
        ],
        selected: 0,
        selectItem: null,
        checked: []
      },
      cboLoaiTien: {
        list: [],
        disable: false,
        value: '0'
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
      type: 'pdf',
      disableParams: false,
      listOfParamNameValues: [],
      pdf: null,
      title: 'Báo cáo',
      phanvungId: Number.parseInt(this.$root.token.getPhanVungID()),
      formProps: {
        txtMaGD: '',
        txtTenKH: '',
        txtDiaChiKH: ''
      },
      loaitien_id: 0,
      path: 'BIEUMAU/HOPDONGPHULUC/RP_BC_BSS_42840'
    }
  },
  watch: {
    'cboLoaiTien.value': function (newValue) {
      this.cboLoaiTien_SelectedValueChanged(newValue)
    }
  },
  methods: {
    frmInPhieuThu_Load () {
      this.formProps.txtMaGD = this.ma_gd
      this.FN_INPHIEUTHU_LOAD()
    },
    async FN_INPHIEUTHU_LOAD () {
      let data = this.GetData(await API.FN_INPHIEUTHU_LOAD(this.axios))
      if (data) {
        let kq = data.kq
        let result = JSON.parse(data.p_js_returns)
        if (kq === '1') {
          this.cboLoaiTien.list = result.DS_KHOANMUC_TT.map(item => {
            return {
              id: item.KHOANMUCTT_ID,
              text: item.TENKHOANMUC
            }
          })
          if (this.cboLoaiTien.list.length > 0) {
            this.cboLoaiTien.value = this.cboLoaiTien.list[0].id
          }
        } else {
          this.$root.$toast.warning(result.MESSAGE)
        }
      } else {
        this.$root.$toast.warning('Không lấy được danh sách loại tiền')
      }
      await this.getReport(this.path)
    },
    async cboLoaiTien_SelectedValueChanged (newValue) {
      this.loaitien_id = Number(newValue)
      await this.FN_INPHIEUTHU_CBOLOAITIEN(this.ma_gd, this.loaitien_id)
      // update lại loại tiền trong param truyền lên báo cáo
      let index = this.listOfParamNameValues.findIndex(item => item.name == 'p_loaitien_id')
      if (index >= 0) {
        this.listOfParamNameValues[index].value = Number(newValue)
      }
      console.log(this.listOfParamNameValues)
    },
    async FN_INPHIEUTHU_TXTMAGD (magd, loaitienid) {
      try {
        this.loading(true)
        let req = {
          ma_gd: magd,
          loaitien_id: loaitienid
        }
        let dsPara = {
          p_ds_para: JSON.stringify(req)
        }
        let data = this.GetData(await API.FN_INPHIEUTHU_TXTMAGD(this.axios, dsPara))
        if (data) {
          let kq = data.kq
          let result = JSON.parse(data.p_js_returns)
          if (kq === '1') {
            let ds = result.DS_TIEN_HDTB
            console.log(ds)
            if (ds.length > 0) {
              this.dgvDanhSach.data = ds.map(item => {
                return {
                  ...item,
                  TIEN_TC: Number(item.TIEN_TC)
                }
              })
              console.log(this.dgvDanhSach.data)
              this.setActiveButton('tsbtnInPhieu', true)
            } else {
              this.clear()
              this.dgvDanhSach.data = []
              this.setActiveButton('tsbtnInPhieu', false)
            }
          } else {
            this.$root.$toast.error(result.MESSAGE)
          }
        } else {
          this.$root.$toast.error('Không lấy được dữ liệu')
        }
      } catch (error) {
        this.$root.$toast.error(error.message)
      } finally {
        this.loading(false)
      }
    },
    async FN_INPHIEUTHU_CBOLOAITIEN (magd, loaitienid) {
      try {
        this.loading(true)
        let req = {
          ma_gd: magd,
          loaitien_id: loaitienid
        }
        let dsPara = {
          p_ds_para: JSON.stringify(req)
        }
        let data = this.GetData(await API.FN_INPHIEUTHU_CBOLOAITIEN(this.axios, dsPara))
        if (data) {
          let kq = data.kq
          let result = JSON.parse(data.p_js_returns)
          if (kq === '1') {
            let ds = result.DS_TIEN_HDTB
            if (ds.length > 0) {
              this.dgvDanhSach.data = ds.map(item => {
                return {
                  ...item,
                  TIEN_TC: item.TIEN_TC ? Number(item.TIEN_TC) : 0
                }
              })
              console.log(this.dgvDanhSach.data)
              this.setActiveButton('tsbtnInPhieu', true)
            } else {
              this.dgvDanhSach.data = []
              this.clear()
              this.setActiveButton('tsbtnInPhieu', false)
            }
          } else {
            this.$root.$toast.error(result.MESSAGE)
          }
        } else {
          this.$root.$toast.error('Không lấy được dữ liệu')
        }
      } catch (error) {
        this.$root.$toast.error(error.message)
      } finally {
        this.loading(false)
      }
    },
    dgvDanhSach_SelectionChanged (item) {
      if (item != null) {
        this.formProps.txtTenKH = item.TEN_KH
        this.formProps.txtDiaChiKH = item.DIACHI_KH
      }
    },
    txtMaGD_KeyPress () {
      if (this.formProps.txtMaGD) {
        this.FN_INPHIEUTHU_TXTMAGD(this.formProps.txtMaGD.trim(), this.loaitien_id)
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
    setActiveButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
    },
    onHideModal () {
      this.popupComponent = null
      this.popupComponentData = null
    },
    showModal () {
      this.$refs.popupInPhieuThu.show()
    },
    hideModal () {
      this.$refs.popupInPhieuThu.hide()
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
          this.convertToSlug(this.title) + '_' + this.ma_gd + '.' + this.extension[this.type],
        items: this.listOfParamNameValues
      }
      await API.RUN(this.axios, request)
        .then((response) => {
          console.log(response)
          if (response.data) {
            this.pdf = this.base64ToArrayBuffer(response.data)
          } else {
            this.$root.$toast.warning('Không lấy báo cáo/biên bản/phiếu để in. Phiếu chưa được thiết kế biểu mẫu')
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
                this.phanvungId
              )
            } else if (paramName.includes('magd')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.ma_gd.toString()
              )
            } else if (paramName.includes('loaitien_id')) {
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.loaitien_id.toString()
              )
            }
          })
        }
      })
      console.log(this.listOfParamNameValues)
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
    async tsbtnInPhieu_Click () {
      try {
        this.loading(true)
        await this.viewReport('pdf')
        this.popupComponent = () => import('../components/PreviewReport')
        this.popupComponentData = {
          pdf: this.pdf
        }
        if (this.pdf) {
          this.$refs.dialogPreviewPhieuThu.show()
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    onActionClick (action) {
      if (action.id === 'tsbtnInPhieu') {
        this.tsbtnInPhieu_Click()
      }
    },
    clear () {
      this.formProps.txtDiaChiKH = ''
      this.formProps.txtTenKH = ''
    }
  }
}
</script>

<style scoped>

</style>
