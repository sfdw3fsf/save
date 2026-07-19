<template>
  <div>
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tsbtnTaoSerial.visible" :class="{ disabledInput: !tsbtnTaoSerial.enabled }" @click="tsbtnTaoSerial_Click">
          <a> <span class="icon one-seri"></span> Tạo Serial </a>
        </li>
        <li v-if="toolStripButton1.visible" :class="{ disabledInput: !toolStripButton1.enabled }" @click="toolStripButton1_Click">
          <a> <span class="icon one-print"></span> In serial </a>
        </li>
        <li v-if="toolStripButton2.visible" :class="{ disabledInput: !toolStripButton2.enabled }" @click="toolStripButton2_Click">
          <a> <span class="icon one-download"></span> Xuất File </a>
        </li>
        <li v-if="btnXuatExel.visible" :class="{ disabledInput: !btnXuatExel.enabled }" @click="btnXuatExel_Click">
          <a> <span class="icon one-excel"></span>Excel </a>
        </li>
        <li>
          <a> <span class="icon one-circle-question"></span>Trợ giúp </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="legend-title">
              Thông tin in tem
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w60 nowrap">Số lượng thiết bị</div>
                  <div class="value">
                    <input type="number" class="form-control" v-model="txtSoLuongTBi.value" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w60 nowrap">Số lượng tem / Thiết bị</div>
                  <div class="value">
                    <input type="number" class="form-control" v-model="txtSoLuongTem.value" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="legend-title">
              Cấu hình
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w60 nowrap">Chiều ngang</div>
                  <div class="value">
                    <input type="number" class="form-control" v-model="txtChieuNgang.value" />
                  </div>
                  <div class="key w20">
                    mm
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w60 nowrap">Chiều dọc</div>
                  <div class="value">
                    <input type="number" class="form-control" v-model="txtChieuDoc.value" />
                  </div>
                  <div class="key w20">
                    mm
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          Danh sách thiết bị
        </div>
        <div class="table-content">
          <DataGrid
            :columns="gridThietBi.header"
            :dataSource="gridThietBi.list"
            ref="gridViewThietBi"
            :showColumnCheckbox="false"
            :showFilter="true"
            :allowPaging="true"
            :enablePagingServer="false"
            :totalRecords="gridThietBiPage.totalElement"
            :pageSize="gridThietBiPage.maxSize"
            :pageIndex="gridThietBiPage.page"
            @pageChanged="gridThietBiPageHandler"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import VueBarcode from 'vue-barcode'
import VueQRCodeComponent from 'vue-qrcode-component'
import moment from 'moment'
import api from './api'
import XLSX from 'xlsx'
import { previewPrint } from '@/utils/util'
import DateTimeHelper from '../../helpers/dateHelper'
import { convertToUpcaseKey, isNullOrEmpty, getDateStringOrDefault } from '../../helpers'
import { FORM_CONFIG } from '../../const'
import { gridThietBi_btnXoaTemplate } from './components'

export default {
  components: {
    'qr-code': VueQRCodeComponent,
    barcode: VueBarcode
  },
  props: {
    vctct_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    kieu: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    dsCT_ChungTu: {
      type: [String, Number],
      defaul: null,
      require: false
    }
  },
  data() {
    return {
      localLoading: 0,
      tsbtnTaoSerial: { visible: true, enabled: true },
      toolStripButton1: { visible: true, enabled: true }, //In Serial
      toolStripButton2: { visible: true, enabled: true }, //Xuất file
      btnXuatExel: { visible: true, enabled: true },
      txtSoLuongTBi: { visible: true, enabled: true, value: null },
      txtSoLuongTem: { visible: true, enabled: true, value: null },
      txtChieuNgang: { visible: true, enabled: true, value: null },
      txtChieuDoc: { visible: true, enabled: true, value: null },
      gridThietBi: {
        visible: true,
        enabled: true,
        header: [
          { fieldName: '', headerText: '', textAlign: 'Center', type: 'checkbox', allowFiltering: true, width: '70', visible: true },
          { fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', allowFiltering: true, visible: true },
          { fieldName: 'MA_VT', headerText: 'Mã vật tư', textAlign: 'Left', allowFiltering: true, visible: true },
          { fieldName: 'SERIAL', headerText: 'Serial', textAlign: 'Left', allowFiltering: true, visible: true },
          { fieldName: 'DVI_TINH', headerText: 'DVI_TINH', textAlign: 'Left', allowFiltering: true, visible: true },
          { fieldName: 'CotXoa', headerText: 'Xóa', textAlign: 'Center', allowFiltering: true, template: gridThietBi_btnXoaTemplate(this), width: '70', visible: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      dtChitietCT: [],
      dt: [],
      imgs: [],
      sinh_serial_quytac: false,
      kytu_dau: '',
      tinh_hientai: '',
      sl_goc: 0,
      PageCounter: 0,
      CODE128A: 29,
      gridThietBiPage: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      }
    }
  },
  computed: {},
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
  },
  methods: {
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        console.log('result')
        console.log(result.data.data)
        return result.data.data ? result.data.data : null
      } catch (e) {
        console.log('LOI ROI')
        console.log(e)
        if (e.data != null) {
          this.$toast.error(`${e.data.message}`)
        } else {
          this.$toast.error(`Có lỗi gọi API`)
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    gridThietBiPageHandler(e) {
      this.gridThietBiPage.page = e.pageIndex
      this.gridThietBiPage.maxSize = e.pageSize
    },
    async frmTaoSerial_Load() {
      let ds = []
      this.gridThietBi.list = []
      let result = await this.callApiWrapper(api.tham_so_mac_dinh_theo_ma, { ma: 'SINH_SERIAL_QUYTAC' })
      console.log('tham_so_mac_dinh_theo_ma')
      console.log(result)
      if(kq!=0) ds = kq

      if (ds.length) {
        this.sinh_serial_quytac = true
        this.kytu_dau = ds[0].tenTS.toString()
      }
      if (this.kieu == 0) {
        await this.callApiWrapper(api.thong_tin_chi_tiet_chung_tu, { chungTuId: this.vctct_id })
          
        this.dtChitietCT = response.data.data || []

        if (this.dtChitietCT.length) {
          this.txtSoLuongTBi.value = this.dtChitietCT[0]['SOLUONG']
        }
        let soluong = parseInt(this.txtSoLuongTBi.value)
        let soluong_tt = this.dsCT_ChungTu.length
        this.sl_goc = soluong_tt
      }
      if (this.kieu == 1) {
        this.tsbtnTaoSerial.visible = false
        this.txtSoLuongTBi.value = this.dsCT_ChungTu.length
        this.txtSoLuongTBi.enabled = false
        this.gridThietBi.header.find((a) => a.fieldName == 'CotXoa').visible = false

        this.gridThietBi.list = this.dsCT_ChungTu
        let soluong = parseInt(this.txtSoLuongTBi.value)
        let soluong_tt = this.dsCT_ChungTu.length
        this.sl_goc = soluong_tt
      }
    },
    async tsbtnTaoSerial_Click() {
      await this.TaoSerial(this.kieu)
    },
    async TaoSerial(kieu) {
      // try {
      if (this.kieu == 0) {
        let soluong = parseInt(this.txtSoLuongTBi.value)
        let soluong_tt = parseInt(this.dtChitietCT[0]['SOLUONG'])
        if (soluong > soluong_tt) {
          this.$toast.error('Bạn không được chọn số lượng thiết bị vượt quá số lượng trong lô hàng!')
          return
        }
        this.dt = []
        for (let i = 0; i < soluong; i++) {
          let r = {}
          r['LOHANG'] = this.dtChitietCT[0]['LOHANG'].toString()
          r['MA_VT'] = this.dtChitietCT[0]['MA_VT'].toString()
          r['TEN_VT'] = this.dtChitietCT[0]['TEN_VT'].toString()
          r['DVI_TINH'] = this.dtChitietCT[0]['DVI_TINH'].toString()
          let getKey = ''
          await api
            .get_key(this.axios, {
              keyName: 'SERIAL',
              numBlockSize: 1,
              numRetry: 10
            })
            .then((response) => {
              getKey = response.data.data || []
            })
            .catch(function() {})
            .finally(function() {})

          if (this.sinh_serial_quytac) {
            r['SERIAL'] = this.kytu_dau + getKey.toString() //("000000000000");
          } else {
            r['SERIAL'] = getKey.toString() //("000000000000");
          }

          r['SOLUONG'] = 1
          r['DONGIA'] = this.dtChitietCT[0]['DONGIA']
          if (this.$auth.getMaTinh() == 'HCM' || this.$auth.getMaTinh() == 'NET') {
            r['MATHUNG'] = ''
          }

          this.dt.push(r)
        }
        this.gridThietBi.list = this.dt
        await this.TaoCode()
      }
      if (kieu == 1) {
        let soluong = parseInt(this.txtSoLuongTBi.value)
        if (soluong > this.sl_goc) {
          this.$toast.error('Bạn không được chọn số lượng thiết bị vượt quá số lượng trong lô hàng!')
          return
        }
        this.dt = []
        if (soluong < this.sl_goc) {
          for (let i = 0; i < soluong; i++) {
            let r = {}
            if (this.dsCT_ChungTu[i]['ISCHECK'] == '1') {
              r['LOHANG'] = this.dsCT_ChungTu[i]['LOHANG'].toString()
              r['MA_VT'] = this.dsCT_ChungTu[i]['MA_VT'].toString()
              r['TEN_VT'] = this.dsCT_ChungTu[i]['TEN_VT'].toString()
              r['DVI_TINH'] = this.dsCT_ChungTu[i]['DVI_TINH'].toString()
              r['SERIAL'] = this.dsCT_ChungTu[i]['SERIAL'].toString()
              r['SOLUONG'] = 1
              r['DONGIA'] = this.dsCT_ChungTu[i]['DONGIA']
              this.dt.push(r)
            }
          }
        }
        if (soluong == this.sl_goc) {
          for (let i = 0; i < soluong; i++) {
            var r = {}
            r['LOHANG'] = this.dsCT_ChungTu[i]['LOHANG'].toString()
            r['MA_VT'] = this.dsCT_ChungTu[i]['MA_VT'].toString()
            r['TEN_VT'] = this.dsCT_ChungTu[i]['TEN_VT'].toString()
            r['DVI_TINH'] = this.dsCT_ChungTu[i]['DVI_TINH'].toString()
            r['SERIAL'] = this.dsCT_ChungTu[i]['SERIAL'].toString()
            r['SOLUONG'] = 1
            r['DONGIA'] = this.dsCT_ChungTu[i]['DONGIA']
            this.dt.push(r)
          }
        }
        if (this.dt.length == 0) {
          this.$toast.error('Không có dữ liệu để tạo serial')
          return
        }
        this.gridThietBi.list = this.dsCT_ChungTu
        this.TaoCode()
      }
      // } catch (error) {

      // }
    },
    async btnXuatExel_Click() {
      let data1 = XLSX.utils.json_to_sheet(this.dt)
      let wb = XLSX.utils.book_new() // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, 'sheet1') // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, 'Thietbi.xlsx')
    },
    async TaoCode() {
      // lam sau
      // let w = parseInt(this.txtChieuNgang.value)
      // let h = parseInt(this.txtChieuDoc.value)
      // for (let i = 0; i < this.dt.length; i++){
      //     let img = {}
      //     img.Name = this.dt[i]["SERIAL"].toString();
      //     img.value = this.dt[i]["SERIAL"].toString();
      //     img.Image = await this.SinhBarcode(this.dt[i]["SERIAL"].ToString(), w, h, this.CODE128A);
      //     imgs.Add(img);
      // }
    },
    async toolStripButton1_Click() {
      var soluongTem = 0
      try {
        soluongTem = parseInt(this.txtSoLuongTem.value)
      } catch (error) {
        this.$toast.error('Hãy nhập số lượng tem')
        this.$refs.txtSoLuongTem.focus()
        return
      }
      if (this.kieu == 1) {
        await this.TaoSerial(1)
      }
      var dsbarcode = []
      var dtbarcode = []
      var data = this.gridThietBi.list
      for (let dr of this.$refs.gridViewThietBi.getSelectedRecords()) {
        for (let i = 0; i < soluongTem; i++) {
          dtbarcode.push({ serial: dr['SERIAL'] })
        }
      }
      if (dtbarcode.length == 0) {
        this.$toast.error('Hãy tick chọn các serial cần in')
        return
      }
      await this.frmXemBaoCao(dtbarcode)
    },
    async frmXemBaoCao(dtbarcode) {
      this.$root.showLoading(true)
      var res = ''
      await api
        .nhap_xuat_vt_in_barcode(this.axios, dtbarcode)
        .then((response) => {
          res = response
        })
        .catch(function() {})
        .finally(function() {})

      this.$root.showLoading(false)
      res ? previewPrint(res.data) : ''
    },
    async pd_BeginPrint() {
      this.PageCounter = 0
    },
    PrintPage() {
      // Lam sau
    },
    SinhBarcode(value, w, h, type) {
      // Lam sau
    },
    async toolStripButton2_Click() {
      var dtbarcode = []
      this.$refs.gridViewThietBi.getSelectedRecords().forEach((item) => {
        dtbarcode.push({ serial: item['SERIAL'] })
      })

      await api
        .zip_file_bar_code(this.axios, dtbarcode)
        .then((response) => {
          var blob = new Blob([response.data], { type: 'application/json' })
          var url = window.URL.createObjectURL(blob)
          var a = document.createElement('a')
          a.href = url
          a.download = 'barcode.zip'
          a.click()
          document.body.removeChild(a)
        })
        .catch(function() {})
        .finally(function() {})
    },
    repositoryItemCheckEdit1_CheckedChanged() {
      if (this.kieu == 0) {
        return
      }
      let ds_dem = this.gridThietBi.list
      var q = this.$refs.gridViewThietBi.getSelectedRecords()
      this.txtSoLuongTBi.value = q.length != 0 ? q.length : this.sl_goc
    },

    // web bổ sung, C# ko thấy (quy)
    async CotXoa_Click(row) {
      this.gridThietBi.list.splice(row.index, 1)
    },

    async Close() {
      this.$parent.hide()
    }
  },
  async mounted() {
    await this.frmTaoSerial_Load()
  }
}
</script>
<style scoped></style>
