<template src="./SearchEsim.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component'
import gridview from '@/components/Shared/gridview'
import FormSearch from './FormSearch/FormSearch'
import moment from 'moment'
import VueBarcode from 'vue-barcode'
import VueQRCodeComponent from 'vue-qrcode-component'
import API from './API'
// CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
// JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {
    breadcrumb,
    VueFlatPickr,
    gridview,
    FormSearch,
    barcode: VueBarcode,
    'qr-code': VueQRCodeComponent,
    API
  },
  name: 'SearchEsim',
  data() {
    return {
      form: {
        phoneCode: '',
        chkEsim: false,
        phone: ''
      },
      barcodeValue: '',
      qrcodeValue: '',
      typeCode: 'bar code',
      srcimg: ''
    }
  },
  methods: {
    onSubmitSearch() {
      this.CallAPI()
    },
    CallAPI() {
      var pdata = {}
      if (this.form.phone === '' || this.form.phone == null) {
        this.$toast.error('Bạn chưa nhập Số máy tìm kiếm')
      } else {
        pdata.so_tb = '84' + this.form.phone.trim()
      }
      console.log(pdata, 'esim_pdata')
      API.ts_tc_tb_esim(this.axios, pdata).then((response) => {
        if (response.data.message == 'No Content') {
          this.$toast.error('Không có dữ liệu hiện thị')
        }
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
          //render dữ liệu
          for (var i = 0; i < response.data.data.length; i++) {
            if (response.data.data[i].BARCODES != '') {
              this.barcodeValue = response.data.data[i].BARCODES
            }
            if (response.data.data[i].QRSTRING != 'QRSTRING') {
              // this.qrcodeValue = "dữ liệu hardcode"
              this.qrcodeValue = response.data.data[i].QRSTRING
            }
            if (response.data.data[i].SO_SIM != 'SO_SIM') {
              this.form.phoneCode = response.data.data[i].SO_SIM
            }
            if (response.data.data[i].ESIM == '1') {
              this.form.chkEsim = true
            }
          }
        } else {
          this.$toast.error('')
        }
      })
      this.loading(false)
    },
    hideModal() {
      this.$refs['my-modal'].hide()
    },
    btnSearchClicked() {
      this.loading(true)
      this.CallAPI()
    },
    ShowModal(type) {
      this.srcimg = ''
      if (type == 'barcode') {
        this.typeCode = 'BarCode'
        this.$refs['my-modal'].show()
        var svg = document.querySelector('svg')
        var data = new XMLSerializer().serializeToString(svg)
        var svg64 = btoa(data) //for utf8: btoa(unescape(encodeURIComponent(xml)))
        var b64start = 'data:image/svg+xml;base64,'
        var image64 = b64start + svg64
        this.srcimg = image64
        console.log(this.srcimg,'barcode')
      }
      if (type == 'qrcode') {
        this.typeCode = 'QRCode'
        this.$refs['my-modal'].show()
        var image64 = document.querySelectorAll('#divQrcode img')[0].src
        this.srcimg = image64
        console.log(this.srcimg,'qrcode')
      }
    },
    btnPrintClicked() {
      var mywindow = window.open('', 'PRINT', 'height=400,width=600')
      mywindow.document.write('<img src="' + this.srcimg + '"/>')
      mywindow.document.close() // necessary for IE >= 10
      mywindow.focus() // necessary for IE >= 10*/
      mywindow.print()
      mywindow.close()
    }
  }
}
</script>
