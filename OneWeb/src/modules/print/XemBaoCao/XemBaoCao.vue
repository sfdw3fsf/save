<template>
<div>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings"
              ref="dialogXemBaoCao" :position="position" :header="'Xem báo cáo'"
              showCloseIcon="true" width="80%" height="100%" target="#app" :isModal="true" :open="dialogOpen"
              :close="dialogClose">
    <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
               isPopup="true" v-on="popupComponentEvts"></component>
  </ejs-dialog>
</div>
</template>

<script>
import VuePdfApp from 'vue-pdf-app'
import 'vue-pdf-app/dist/icons/main.css'
import API from '../XemBaoCao/API'

export default {
  name: 'XemBaoCao',
  components: {
    VuePdfApp
  },
  props: {
    ma_bc: {
      type: String,
      required: true
    },
    dsGiaTri: {
      type: Array,
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
      settingSelect2: {
        language: 'vi'
      },
      type: 'pdf',
      listOfParamNameValues: [],
      pdf: null,
      path: '',
      title: 'Báo cáo',
      phanvungId: Number.parseInt(this.$root.token.getPhanVungID()),
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
      }
    }
  },
  methods: {
    async dialogOpen () {
    },
    dialogClose () {
    },
    async XemNgay () {
      try {
        if (!this.ma_bc) {
          return
        }
        this.loading(true)
        let caulenh = await this.FN_TT_BAOCAO(this.ma_bc)
        if (caulenh != '-1') {
          this.path = caulenh
          await this.getReport(caulenh)
          await this.viewReport('pdf')
        } else {
          this.$root.$toast.warning('Báo cáo ' + this.ma_bc + ' chưa được thiết kế biểu mẫu')
          this.$refs.dialogXemBaoCao.hide()
        }
        this.popupComponent = () => import('../components/PreviewReport')
        this.popupComponentData = {
          pdf: this.pdf
        }
        if (this.pdf) {
          this.$refs.dialogXemBaoCao.show()
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi xem báo cáo')
      } finally {
        this.loading(false)
      }
    },
    async FN_TT_BAOCAO (mabc) {
      return this.GetData(await API.FN_TT_BAOCAO(this.axios, { param: mabc, type: 2 }))
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
    async viewReport (type) {
      this.type = type
      let request = {
        report: this.path,
        type: type,
        file_name:
          this.convertToSlug(this.title) + '_' + this.ma_bc + '.' + this.extension[this.type],
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
    convertToSlug (Text) {
      return Text.toLowerCase().replace(/ /g, '-')
    },
    async getReport (path) {
      this.pdf = null
      this.listOfParamNameValues = []
      await API.GET_BI_PARAMS(this.axios, path).then(response => {
        if (response.data.listOfParamNameValues) {
          this.listOfParamNameValues =
            response.data.listOfParamNameValues.item
          let firstItem = this.listOfParamNameValues[0]
          let firstItemName = firstItem.name
          console.log(this.dsGiaTri)
          // Kiểm tra param đầu xem có phải là phan vu ng id ko nếu đúng thì thêm phân vùng id vào danh sách giá trị
          if (firstItemName.includes('phanvung_id') || firstItemName.includes('phanvung')) {
            this.$set(
              this.listOfParamNameValues[0],
              'value',
              this.phanvungId
            )
            for (let i = 1; i < this.listOfParamNameValues.length; i++) {
              let item = this.listOfParamNameValues[i]
              let paramName = item.name
              let paramLabel = item.label
              let paramType = item.UIType
              console.log('ParamName: ' + paramName + ', ParamType:' + paramType + ', ParamLabel:' + paramLabel)
              console.log(this.dsGiaTri[i - 1])
              this.$set(
                this.listOfParamNameValues[i],
                'value',
                this.dsGiaTri[i - 1]
              )
            }
          } else {
            this.listOfParamNameValues.forEach((v, index) => {
              let paramName = v.name
              let paramLabel = v.label
              let paramType = v.UIType
              console.log('ParamName: ' + paramName + ', ParamType:' + paramType + ', ParamLabel:' + paramLabel)
              console.log(index)
              console.log(this.dsGiaTri[index])
              this.$set(
                this.listOfParamNameValues[index],
                'value',
                this.dsGiaTri[index]
              )
            })
          }
        }
      })
      console.log('List params: ', this.listOfParamNameValues)
    },
    base64ToArrayBuffer (base64) {
      let binaryString = window.atob(base64)
      let len = binaryString.length
      let bytes = new Uint8Array(len)
      for (let i = 0; i < len; i++) {
        bytes[i] = binaryString.charCodeAt(i)
      }
      return bytes.buffer
    }
  }
}
</script>

<style>
</style>
