<template>
  <div>
    <!-- <div class="popup-header">
      <div class="title"><span class="icon one-appstore"></span> {{ Text.title }}</div>
    </div> -->
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="tsbtnGhiLai_Click"> <span class="icon one-save"></span> Ghi lại </a>
        </li>
        <li v-if="Visible.tsbtnNhapExcel">
          <b-form-file id="fileUpload_frmGanSeri" v-model="arrayImportFile.filexcel" ref="fileUpload_frmGanSeri" @change="this.onChange" accept=".xls, .xlsx" class="-ap icon-more_horiz hidden" plain></b-form-file>
          <a @click="tsbtnNhapExcel_Click"> <span class="icon one-xlsx"></span>Nhập từ Excel </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div>
          <div v-if="!Visible.Panel1Collapsed">
            <div class="legend-title">
              Kho vật tư
            </div>
            <div class="info-row marb10">
              <div class="key w80">Từ kho</div>
              <div class="value">
                <div class="row">
                  <div class="col-sm-5 col-12">
                    <div class="select-custom">
                      <SelectExt ref="cboLoaiChungTu" :disabled="true" v-model="params.p_TuKho" :dataSource="cboTuKho" dataTextField="tenKho" dataValueField="khoID" />
                      <!-- <ejs-dropdownlist class="form-control" locale="vi" v-model="params.p_TuKho" :dataSource="cboTuKho" :fields="{ value: 'khoID', text: 'tenKho' }" :allowFiltering="true"> </ejs-dropdownlist> -->
                    </div>
                  </div>
                  <div class="col-sm-2 col-12">
                    <div class="select-custom">
                      <ejs-dropdownlist class="form-control" locale="vi" v-model="params.p_CheDoQuet" :dataSource="cboCheDoQuet" :fields="{ value: 'CHEDO_ID', text: 'CHEDO' }" :allowFiltering="true"> </ejs-dropdownlist>
                    </div>
                  </div>
                  <div class="col-sm-5 col-12">
                    <input type="text" class="form-control" v-model="params.p_txtQuetSerial" @keyup.enter="txtQuetSerial_KeyPress" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="box-col box-form col-sm-9 col-12">
                <div id="div-seri">
                  <DataGrid
                    ref="gridViewVatTu"
                    class="table-result table-gachle tree-grid-2"
                    v-bind:columns="[
                      { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true, allowSorting: true, isGroupedColumn: true },
                      { fieldName: '', headerText: '', allowSorting: false, allowFiltering: false, cssClass: 'text-center', width: 43, headerTemplate: colCheckboxAllVatTuSerial, template: colCheckboxVatTuSerial },
                      { fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: true },
                      { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true, allowSorting: true },
                      { fieldName: 'MA_TBI', headerText: 'Mã TBI', allowFiltering: true, allowSorting: true },
                      { fieldName: 'SERIAL', headerText: 'Serial', allowFiltering: true, allowSorting: true },
                      { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true, allowSorting: true },
                      { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true, allowSorting: true, format: 'n3', type: 'number', },
                      { fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true, allowSorting: true },
                      { fieldName: 'MATHUNG', headerText: 'Mã thùng', allowFiltering: true, allowSorting: true, visible: Visible.colMaThung },
                      { width: 200, fieldName: 'HAN_BH', headerText: 'Hạn bảo hành', allowFiltering: true, allowSorting: true},
                      { fieldName: 'THIETBI_ID', headerText: '', visible: false, isPrimaryKey: true }
                    ]"
                    dataKeyField="THIETBI_ID"
                    v-bind:dataSource="gridVatTu"
                    :showFilter="true"
                    :showColumnCheckbox="false"
                    :allowPaging="true"
                    :enablePagingServer="false"
                    :enabledSelectFirstRow="false"
                    :selectionSettings="{ type: 'Multiple' }"
                    @selectedItemsChanged="gridDSVatTuCheckBox"
                  />
                </div>
              </div>
              <div class="box-col box-form col-sm-3 col-12" id="boxRight">
                <DataGrid
                  ref="gridViewDSchon"
                  class="table-result table-gachle tree-grid-2"
                  v-bind:columns="[
                    { fieldName: 'TENVT', headerText: 'Tên VT', allowFiltering: true, allowSorting: true },
                    { fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true, allowSorting: true, width: 120 }
                  ]"
                  dataKeyField=""
                  v-bind:dataSource="DSChonVT"
                  :showFilter="true"
                  :showColumnCheckbox="false"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  :enabledSelectFirstRow="false"
                  :selectionSettings="{ persistSelection: true }"
                  :aggregates="TongSL"
                />
              </div>
            </div>
          </div>
          <div v-if="!Visible.Panel2Collapsed">
            <div class="legend-title">
              Danh sách cuộn cáp
            </div>
            <div class="d-flex align-items-center">
              <div class="col-3 d-flex align-items-center">
                {{ Text.lblSL }}
              </div>
              <div class="col-9 d-flex align-items-center">
                <div class="key w80">Từ kho</div>
                <div class="value flex-grow-1">
                  <input type="text" class="form-control" v-model="params.p_txtTenKho" readonly="true" />
                </div>
              </div>
            </div>
            <DataGrid
              ref="grvDS_ThietBi"
              v-bind:columns="[
                { fieldName: '', headerText: '', allowSorting: false, allowFiltering: false, cssClass: 'text-center', width: 43, headerTemplate: colCheckboxAllVatTuCap, template: colCheckboxVatTuCap, freeze: 'Left' },
                { fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: true, freeze: 'Left', width: '100' },
                { fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true, allowSorting: true },
                { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true, allowSorting: true },
                { fieldName: 'MA_TBI', headerText: 'Mã Tbi', allowFiltering: true, allowSorting: true },
                { fieldName: 'SL_TON', headerText: 'Số lượng', allowFiltering: true, allowSorting: true, type: 'number', format: 'N3' },
                { fieldName: 'SL_XUAT', headerText: 'SL xuất', allowFiltering: true, allowSorting: true, allowEditing: true, editType: 'numericedit', type: 'number', format: 'N3' },
                { fieldName: 'THIETBI_ID', headerText: '', visible: false, isPrimaryKey: true }
              ]"
              dataKeyField="THIETBI_ID"
              v-bind:dataSource="grcDS_ThietBi"
              :editSettings="editSettings"
              :showFilter="true"
              :showColumnCheckbox="false"
              :enabledSelectFirstRow="false"
              :allowPaging="true"
              :enablePagingServer="false"
              @cellSaved="editGridThietBi"
            />
            <!-- @selectedItemsChanged="grvDS_ThietBi_checked" -->
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- </div> -->
  <!-- </div> -->
</template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from 'vuex'
import moment from 'moment'
import Vue from 'vue'
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import { getterName, actionName, statePropertyName, mutationName } from '@/modules/QLVT/store/frmganseri'
// import select2 from '@/components/Select2.vue'
import { L10n } from '@syncfusion/ej2-base'
import XLSX from 'xlsx'

export default {
  components: { XLSX, breadcrumb },
  emits: ['onBtnGhiLaiClicked'],
  props: {
    tag: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    functionName: {
      type: [String],
      defaul: null,
      require: false
    },
    vlohang: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vvattu_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vkho_id: {
      type: [String, Number],
      defaul: null,
      require: false
    },
    vchungtu_id: {
      type: [String, Number],
      defaul: null,
      require: false
    }
  },
  async mounted() {
    console.log('ganseri mounted')
    await this.ThietLap()
    await this.frmGanSeri_Load()
  },
  computed: {
    ...mapState('frmganseri', statePropertyName),
    ...mapGetters('frmganseri', getterName),
    TongSL() {
      return [{ field: 'SOLUONG', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.getTongSL }]
    }
  },
  data() {
    return {
      localLoading: 0,
      cttbi: [],
      dtVatTu: [],
      dtThietBi: [],
      ds_cat_temp: [],
      ds_dachon: [],
      dt: [],
      DSChonVT: [],
      v_ctct_id: '',
      lohang: '',

      vkho_nhan_id: 0,
      soluong_tong: 0,
      soluong_ton: 0,
      sl: 0,

      chungtu_id: 0,
      ctct_id: 0,
      vctct_id: 0,
      vattu_id: 0,

      XacNhan: false,
      checkAll: false,
      xacnhan: false,
      cat_temp: false,
      qr_code: false,
      DSThietBi_Checked: [],
      listVatTuChecked: [],
      listVatTuCuonCapChecked: [],
      params: {
        p_TuKho: '',
        p_CheDoQuet: '',
        p_txtQuetSerial: '',
        p_txtTenKho: ''
      },
      editSettings: { allowEditing: true, mode: 'Batch' },
      gridVatTu: [],
      grcDS_ThietBi: [],
      cboCheDoQuet: [],
      cboTuKho: [],

      Text: {
        lblSL: '',
        title: 'Gán Serial'
      },

      Visible: {
        Panel1Collapsed: true,
        Panel2Collapsed: true,
        tsbtnNhapExcel: false,
        colMaThung: true
      },
      arrayImportFile: {
        filexcel: null,
        ok: false,
        dsImportExcel: [],
        dataExcel: [],
        ds_file: []
      },

      checkAllVatTuCap: false,
      colCheckboxAllVatTuCap: function() {
        return {
          template: {
            template: `
                              <div class="check-action">
                                  <input type="checkbox" v-model="parent.checkAllVatTuCap" class="uncheck"/>
                                  <span class="name"></span>
                              </div>
                          `,
            data: function() {
              return {}
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              }
            }
          }
        }
      },

      colCheckboxVatTuCap: function() {
        return {
          template: {
            template: `
                              <div class="check-action">
                                  <input
                                      type="checkbox"
                                      class="uncheck"                                   
                                      v-model="parent.DSThietBi_Checked"
                                      :value="data.THIETBI_ID"/>
                                      
                                  <span class="name"></span>
                              </div>
                          `,
            data() {
              return {
                data: {}
              }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              }
            }
          }
        }
      },

      checkAllVatTuSerial: false,
      colCheckboxAllVatTuSerial: function() {
        return {
          template: {
            template: `
                              <div class="check-action">
                                  <input type="checkbox" v-model="parent.checkAllVatTuSerial" class="uncheck"/>
                                  <span class="name"></span>
                              </div>
                          `,
            data: function() {
              return {}
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              }
            }
          }
        }
      },
      colCheckboxVatTuSerial: function() {
        return {
          template: {
            template: `
                              <div class="check-action">
                                  <input
                                      type="checkbox"
                                      class="uncheck"                                    
                                      v-model="parent.listVatTuChecked"
                                      :value="data.THIETBI_ID"/>
                                  <span class="name"></span>
                              </div>
                          `,
            data() {
              return {
                data: {}
              }
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent
              }
            }
          }
        }
      },
      customTemplate: function() {
        return {
          template: Vue.component('footerTemplate', {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } }
            }
          })
        }
      }
    }
  },
  methods: {
    ...mapActions('frmganseri', actionName),
    ...mapMutations('frmganseri', mutationName),
    async callApiWrapper(action, body) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        console.log('result')
        console.log(result.data.data)
        return result.data.data != null ? result.data.data : null
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
    // chuyển Date thành type string
    f_DateToString: function(value, format = 'DD/MM/YYYY') {
      return moment(value).format(format)
    },
    f_StringToDate(value, format = 'DD/MM/YYYY') {
      return moment(value, format).toDate()
    },

    getTongSL() {
      var TongSL = 0
      this.DSChonVT.forEach((e) => {
        TongSL += parseInt(e.SOLUONG)
      })
      return TongSL
    },
    async load_data() {
      // try{

      if (this.tag == 0) {
        this.dtThietBi = await this.getDSThietBiSeri({
          chungTuId: this.vchungtu_id,
          khoId: this.vkho_id,
          loHang: this.vlohang,
          vatTuId: this.vvattu_id
        })
        this.gridVatTu = this.dtThietBi.map(e => {
          e.DONGIA = parseInt(e.DONGIA)
          return e
        })
        this.listVatTuChecked = []
        this.checkAllVatTuSerial = false
        if (this.dtThietBi.length && this.dtThietBi[0].hasOwnProperty('MATHUNG')) {
          this.Visible.colMaThung = true
        } else {
          this.Visible.colMaThung = false
        }
      }

      // }
      // catch (error){
      //     this.$toast.error("Có lỗi xảy ra khi lấy dữ liệu! " + error);
      // }
    },
    async load_cbo() {
      if (this.tag == 0) {
        this.cboTuKho = await this.getDSKhoTheoNV({ nhanVienId: this.$auth.getNhanVienID() })
        this.params.p_TuKho = this.vkho_id

        this.cboCheDoQuet = [
          { CHEDO_ID: 0, CHEDO: 'Quét theo số serial' },
          { CHEDO_ID: 1, CHEDO: 'Quét theo mã thùng' }
        ]
        this.params.p_CheDoQuet = 0
      }
    },
    async taodulieu(d) {
      this.cttbi = []
      for (let item of d) {
        var ri = {}

        ri.CTCT_ID = parseInt(item['CTCT_ID'].toString())
        let vthietbi_id
        if (item['THIETBI_ID'].toString() != '0') {
          vthietbi_id = parseInt(item['THIETBI_ID'].toString())
        } else {
          vthietbi_id = await this.getKeys({
            keyName: 'THIETBI_ID',
            numBlockSize: 1,
            numRetry: 10
          })
        }

        ri.THIETBI_ID = vthietbi_id
        ri.LOHANG = item['LOHANG'].toString()
        ri.KHO_ID = parseInt(item['KHO_ID'].toString())

        var tbcha = item['THIETBI_CHA_ID']
        if (tbcha != null && tbcha != '') {
          ri.THIETBI_CHA_ID = parseInt(item['THIETBI_CHA_ID'].toString())
        }

        ri.VATTU_ID = parseInt(item['VATTU_ID'].toString())
        ri.TINHTRANGTBI_ID = 1
        ri.SERIAL = item['SERIAL'].toString()
        ri.SERIAL_GP = item['SERIAL_GP'].toString()
        ri.MA_TBI = item['MA_TBI'].toString()

        if (item['HAN_BH'] != null && item['HAN_BH'] != '') {
          ri.HAN_BH = item['HAN_BH'].toString()
        }
        // quy bo sung 27/11
        else {
          ri.HAN_BH = ''
        }
        // quy bo sung 27/11
        // ri.DVITINH_ID = item.DVITINH_ID || ''

        if (item['SOTHANG_PB']) {
          ri.SOTHANG_PB = parseInt(item['SOTHANG_PB'].toString())
        } else {
          ri.SOTHANG_PB = 0
        }

        ri.DONGIA = parseInt(item['DONGIA'])
        ri.NGAY_CN = this.f_DateToString(new Date(), 'DD/MM/YYYY')
        ri.NGUOI_CN = this.$auth.getUserName()
        ri.MAY_CN = this.$auth.getUserName()
        ri.IP_CN = '10.59.90123' //ttnd.ip
        ri.SL_GOC = 1
        ri.SL_TON = 1
        ri.GHICHU = item['GHICHU']
        ri.MATHUNG = item['MATHUNG']
        ri.MAC = item['MAC']
        this.cttbi.push(ri)
      }
    },
    async ghilai() {
      if (this.tag == 0) {
        // var b = this.$refs.gridViewVatTu.getSelectedRecords()
        let b = this.gridVatTu.filter((a) => this.listVatTuChecked.includes(a.THIETBI_ID))

        if (!b.length) {
          this.$toast.error('Bạn chưa chọn serial nào để cập nhật')
          return
        }
        var d = b
        var d_temp = []
        var dt_ctct = b.map((e) => {
          let x = { CTCT_ID: e.CTCT_ID }
          return x
        })
        // let json_list = JSON.stringify(d_temp);
        // Kiểm tra sl serial chọn có lớn hơn sl chitiet_ct ko

        // var kt_sl = await this.KiemTraSLGanSeri({
        //   "chungTuId" : this.vchungtu_id,
        //   "data" : dt_ctct
        // })

        // if (kt_sl != "1"){
        //     this.$toast.error(kt_sl)
        //     return
        // }
        //

        if (d.length > 0) {
          await this.taodulieu(d)

          var kiemtra = await this.KiemTraChiTietVatTu({ chungTuId: this.vchungtu_id })

          if (kiemtra.toString() != '0') {
            var ds_tbi = this.cttbi
            let json_listtbi = JSON.stringify(ds_tbi)
            try {
              var thucthi = await this.CapNhatSLChiTietVatTu({
                chungTuId: this.vchungtu_id,
                ds: json_listtbi,
                ctctId: 0,
                kieu: 1
              })

              if (thucthi.toString() != '1') {
                this.$toast.error('Có lỗi khi cập nhật thiết bị ' + thucthi)
                return
              }
            } catch (error) {
              this.$toast.error('Có lỗi khi cập nhật thiết bị ' + error)
              return
            }
          } else {
            await this.ThemChiTietThietBi(this.cttbi)
          }

          // if (d.length && d[0].hasOwnProperty("MATHUNG")){
          //   d.forEach(async (item)=>{
          //     await this.CapNhatChungTu({
          //       "maThung" : item["MATHUNG"],
          //       "ctctId": item["CTCT_ID"],
          //       "serial" : item["SERIAL"]
          //     })
          //   })
          // }
          await this.load_data()
          this.xacnhan = true

          this.$emit('onBtnGhiLaiClicked', { xacnhan: this.xacnhan, functionName: this.functionName })
        } else {
          this.$toast.error('Không có dữ liệu cập nhật')
        }
      }
    },
    async LAY_DS_CUONCAP() {
      var ds = await this.getDSCuonCapV2({
        khoId: this.vkho_id,
        vatTuId: this.vattu_id,
        loHang: this.lohang
      })
      console.log(ds, ' =  ds')
      console.log(this.cat_temp, ' =  cat_temp')
      if (this.cat_temp) {
        if (ds.length) {
          // check more
          for (let i = 0; i < this.ds_dachon.length; i++) {
            var t = ds.filter((a) => a.THIETBI_ID == this.ds_dachon[i]['THIETBI_ID'] && a.MA_TBI == this.ds_dachon[i]['MA_TBI'] && a.LOHANG == this.ds_dachon[i]['LOHANG'])
            if (t.length > 0) {
              for (let r of t) {
                r['ISCHECK'] = 's1'
                r['SL_XUAT'] = this.ds_dachon[i]['SL_XUAT']
              }
            }
          }
        }
      }
      for (let e of ds) {
        e.SL_TON = e.SL_TON == null || e.SL_TON == '' ? 0 : parseFloat(e.SL_TON)
        e.SL_XUAT = e.SL_XUAT == null || e.SL_XUAT == '' ? 0 : parseFloat(e.SL_XUAT)
      }
      this.grcDS_ThietBi = ds
      this.DSThietBi_Checked = [] // reset column checked
      if (this.cat_temp) {
        var q = this.grcDS_ThietBi
        var kq = this.$refs.grvDS_ThietBi.getSelectedRecords().reduce((sum, b) => sum + parseFloat(b.SL_XUAT), 0)
        if (kq) {
          kq = Math.round((kq + this.soluong_ton) * 100) / 100

          this.Text.lblSL = 'SL cáp đã chọn: ' + kq + '/' + this.soluong_tong + ''
        }
        this.sl = kq
      }
    },
    async frmGanSeri() {
      this.tag = 0
      await this.ThietLap()
    },
    async frmGanSeri_2(kieu) {
      this.tag = 1
      await this.ThietLap()
    },
    async ThietLap() {
      if (this.tag.toString() == '1') {
        this.Visible.Panel1Collapsed = true
        this.Visible.Panel2Collapsed = false
      } else {
        this.Visible.Panel1Collapsed = false
        this.Visible.Panel2Collapsed = true
      }
    },
    async frmGanSeri_Load() {
      if (this.tag.toString() == '0') {
        this.listVatTuChecked = []
        this.Visible.tsbtnNhapExcel = true
        await this.load_cbo()
        await this.load_data()
      }
      if (this.tag.toString() == '1') {
        console.log(this.soluong_ton, ' = this.soluong_ton')
        var sl_ton = this.soluong_ton.toString() == '' ? 0 : this.soluong_ton.toString()
        this.Text.lblSL = 'SL cáp đã chọn: ' + sl_ton + '/' + this.soluong_tong.toString() + ''
        await this.LAY_DS_CUONCAP()
      }
    },
    async txtQuetSerial_KeyPress() {
      if (this.params.p_CheDoQuet.toString() == '0') {
        let vserial = this.params.p_txtQuetSerial.trim()

        var vsl = vserial.split('|')

        if (this.qr_code) {
          if (vsl.length != 3) {
            this.$toast.error('QR Code không đúng định dạng !!!')
            this.params.p_txtQuetSerial = ''
            return
          } else {
            vserial = vsl[2]
          }
        }

        let listSerial = this.gridVatTu.filter((vt) => vt.SERIAL == vserial.trim())
        if (listSerial.length > 0) {
          for (let vt of listSerial) {
            this.listVatTuChecked.push(vt.THIETBI_ID)
          }
        } else {
          this.$toast.error('Không tìm thấy serial: ' + vserial)
        }
        // var value = await this.FocusGridViewRow('gridViewVatTu',this.gridVatTu , "SERIAL", vserial.trim())

        // if(value != ""){
        //   this.$toast.error("Không tìm thấy mã serial: " + value);
        // }
      } else {
        if (!(this.gridVatTu.length && this.gridVatTu[0].hasOwnProperty('MATHUNG'))) {
          return
        }

        let vserial = this.params.p_txtQuetSerial.trim()

        var vsl = vserial.split('|')
        for (var e of vsl) {
          // var value = await this.FocusGridViewRow('gridViewVatTu',this.gridVatTu , "MATHUNG", e.trim())
          // if(value != "" && value != null){
          //   list_not_found.push(value)
          // }
          let listMaThung = this.gridVatTu.filter((vt) => vt.MATHUNG == e.trim())
          if (listMaThung.length > 0) {
            for (let vt of listMaThung) {
              this.listVatTuChecked.push(vt.THIETBI_ID)
            }
          } else {
            this.$toast.error('Không tìm thấy mã thùng: ' + e)
          }
        }
      }
      this.params.p_txtQuetSerial = ''
    },
    async gridVatTu_MouseDown() {
      // try{
      // if (this.tag == 0){
      //     var info = gridViewVatTu.CalcHitInfo((e).Location);
      //     if (info.RowHandle < 0) return;
      //     if (!info.InRow && !info.InRowCell) return;
      //     if (info.Column.FieldName == "CHON")
      //     {
      //         ((HandledMouseEventArgs) e).Handled = true;
      //         if (gridViewVatTu.GetRowCellValue(info.RowHandle, "CHON").toString() == "s0")
      //             gridViewVatTu.SetRowCellValue(info.RowHandle, "CHON", "s1");
      //         else gridViewVatTu.SetRowCellValue(info.RowHandle, "CHON", "s0");
      //         gridVatTu.Update();
      //     }
      // }
      // }
      // catch{
      // }
    },
    async CAPNHAT_CAP() {
      if (this.DSThietBi_Checked.length == 0) {
        this.$toast.error('Bạn chưa chọn cáp ')
        return
      }
      if (this.sl < this.soluong_tong) {
        this.$toast.error('Số lượng cuộn cáp bạn chọn chưa đủ')
        return
      }
      if (this.sl > this.soluong_tong) {
        this.$toast.error('Số lượng cuộn cáp bạn chọn lớn hơn số lượng tồn')
        return
      }
      var dataItem = this.DSThietBi_Checked
      var data = []
      this.grcDS_ThietBi.forEach((e) => {
        if (dataItem.includes(e.THIETBI_ID)) {
          data.push({
            tbi: e.THIETBI_ID,
            sl: e.SL_XUAT
          })
        }
      })

      if (!this.cat_temp) {
        let s = await this.CatChuyenCap({
          ctctId: this.vctct_id,
          data: JSON.stringify(data),
          khoNhanId: this.vkho_nhan_id,
          nguoiCN: this.$auth.getUserName(),
          mayCN: this.$auth.getUserName(),
          ipCN: ''
        })

        if (s != '1') {
          this.$toast.error(s)
          await this.LAY_DS_CUONCAP()
        } else {
          this.xacnhan = true
          this.$emit('onBtnGhiLaiClicked', { xacnhan: this.xacnhan, functionName: this.functionName })
        }
      } else if (this.cat_temp) {
        this.xacnhan = true
        this.ds_cat_temp = this.grcDS_ThietBi.filter((item) => this.DSThietBi_Checked.includes(item.THIETBI_ID))
        this.$emit('onBtnGhiLaiClicked', { xacnhan: this.xacnhan, functionName: this.functionName })
      }
    },
    async tsbtnGhiLai_Click() {
      try {
        if (this.tag.toString() == '0') {
          this.$root.showLoading(true)
          await this.ghilai()
          await this.load_data()
          this.$root.showLoading(false)
        }
        if (this.tag.toString() == '1') {
          await this.CAPNHAT_CAP()
        }
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },

    checkfile: function() {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error('Chưa chọn file nào!')
        return false
      }
      if (this.arrayImportFile.filexcel.type != 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
        this.$toast.error('File không phải định dạng xlsx hoặc xls!')
        return false
      }
      return true
    },
    async onChange() {
      this.arrayImportFile.filexcel = this.$refs.fileUpload_frmGanSeri.$refs.input.files[0]

      let json_list = []
      try {
        this.loading(true)
        if (this.checkfile()) {
          let tencot
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: 'binary' })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            // retrieve headers (i.e. remove first row)
            let headers = data.shift() // bỏ dòng header đầu tiên trong data

            // then build the json for each row
            var result = data.map(function(a) {
              var jsonRow = {}
              a.forEach(function(cellValue, cellIndex) {
                jsonRow[headers[cellIndex]] = cellValue
              })
              headers.forEach((b) => {
                if (!Object.keys(jsonRow).includes(b)) {
                  jsonRow[b] = ''
                }
              })
              return jsonRow
            })

            await this.CapNhatExcel(JSON.parse(JSON.stringify(result)))
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
        }
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra: ' + error)
        return
      } finally {
        this.$refs.fileUpload_frmGanSeri.reset()
        this.loading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    async CapNhatExcel(dtDulieu) {
      var dt_Loi = []
      var selectedIndexs = []
      // quy them
      if (dtDulieu.length == 0) {
        return
      }

      if (!dtDulieu[0].hasOwnProperty('SERIAL')) {
        this.$toast.error('Chưa có cột SERIAL không thể đọc dữ liệu')
        return
      }
      var ds = this.gridVatTu
      if (ds.length == 0) {
        this.$toast.error('Không có dữ liệu serial để đọc')
        return
      }

      for (var dr of dtDulieu) {
        if (dr['SERIAL'] == null || dr['SERIAL'] == '') {
          this.$toast.error('Có dòng dữ liệu không có serial bạn hãy kiểm tra lại')
          return
        }

        var t = this.gridVatTu.findIndex((a) => a.SERIAL.toString().toUpperCase() == dr.SERIAL.toString().toUpperCase())
        // tim ko thay index
        if (t != -1) {
          selectedIndexs.push(t)
        } else {
          dt_Loi.push({ SERIIAL: dr['SERIAL'] })
        }
      }

      if (dt_Loi.length) {
        await this.$bvModal
          .msgBoxConfirm('Có ' + dt_Loi.length + ' serial bị lỗi. Không tìm thấy bạn có muốn xuất excel kiểm tra không?', {
            okVariant: 'success',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
            centered: true
          })
          .then(async (value) => {
            if (value) {
              await this.SaveXlsAndOpenFile('fileDS_SR_Loi.xlsx', 'ThietBi', dt_Loi)
            }
          })
          .catch((err) => {
            // An error occurred
          })
      } else {
        selectedIndexs.length ? this.$refs.gridViewVatTu.grid.selectRows(selectedIndexs) : ''
        this.$refs.gridViewVatTu.flagSelectedRowIndexes = selectedIndexs
        this.$toast.success('Đã chọn serial từ excel vào thành công')
      }
    },
    async SaveXlsAndOpenFile(fileName, sheetName, exportData) {
      if (!exportData.length) {
        this.$toast.error('Không có bản ghi nào để xuất!')
        return
      }
      let data1 = XLSX.utils.json_to_sheet(exportData)
      let wb = XLSX.utils.book_new() // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, sheetName) // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, fileName)
    },
    async tsbtnNhapExcel_Click() {
      document.getElementById('fileUpload_frmGanSeri').click()
    },
    // async Close() {
    //   await this.$parent.$parent.XacNhan_frmGanSeri()
    //   this.$parent.hide()
    // },
    async ShowDialog() {
      await this.frmGanSeri_Load()
      await this.$parent.show()
    },
    // grvDS_ThietBi_checked: function (dataItem) {
    //   var soluong = 0
    //   this.DSThietBi_Checked = dataItem
    //   this.grcDS_ThietBi.forEach((e)=>{
    //     if(dataItem.includes(e.THIETBI_ID)){
    //       e.SL_XUAT = e.SL_TON
    //       soluong = soluong +  parseInt(e.SL_TON)
    //     }
    //   })
    //   this.sl = soluong
    //   this.Text.lblSL = "SL cáp đã chọn :" + soluong + "/" + this.soluong_tong + "";
    // },
    getSoLuongDaChon() {
      this.DSChonVT = []
      var DSChecked = this.gridVatTu.filter((a) => this.listVatTuChecked.includes(a.THIETBI_ID))

      //Lấy vật tư đã chọn
      let DSVatTuDaChon = []
      for (let e of DSChecked) {
        if (!DSVatTuDaChon.includes(e.TEN_VT)) {
          DSVatTuDaChon.push(e.TEN_VT)
        }
      }
      for (let e of DSVatTuDaChon) {
        let SLDaChon = DSChecked.filter((a) => a.TEN_VT == e)
        this.DSChonVT.push({
          TENVT: e,
          SOLUONG: SLDaChon.length
        })
      }
    },
    gridDSVatTuCheckBox: function(dataItem) {
      for (let e of dataItem) {
        if (!this.listVatTuChecked.includes(e)) {
          this.listVatTuChecked.push(e)
        }
      }
    },
    async FocusGridViewRow(grid, data, field, value) {
      let indexs = data.findIndex((a) => a[field] != null && a[field].toString() == value)
      console.log(indexs, ' = indexs')
      if (indexs != -1) {
        if (!this.listVatTuChecked.includes(this.gridVatTu[indexs].THIETBI_ID)) {
          this.listVatTuChecked.push(this.gridVatTu[indexs].THIETBI_ID)
        }
        if (!(indexs + 1 > this.$refs[grid].pageIndexData * this.$refs[grid].pageSizeData && indexs + 1 <= (this.$refs[grid].pageIndexData + 1) * this.$refs[grid].pageSizeData)) {
          this.$refs[grid].$refs.pagination.gotoPage(parseInt(indexs / this.$refs[grid].pageSizeData))
        }
        let self = this
        let ind = parseInt(indexs % this.$refs[grid].pageSizeData)
        setTimeout(() => {
          self.$refs[grid].grid.selectRow(ind, true)
        }, 200)
        return ''
      } else {
        return value
      }
    },
    async editGridThietBi(arg) {
      let grv = this.$refs.grvDS_ThietBi
      let changes = grv.getBatchChanges()

      if (changes.changedRecords.length == 0) {
        return
      }
      if (arg.columnName == 'SL_XUAT') {
        if (changes.changedRecords[0].SL_XUAT > changes.changedRecords[0].SL_TON) {
          changes.changedRecords[0].SL_XUAT = changes.changedRecords[0].SL_TON
          this.$toast.error('Yêu cầu: Số lượng xuất phải bé hơn hoặc bằng số lượng tồn')
        } else if (changes.changedRecords[0].SL_XUAT < 0) {
          changes.changedRecords[0].SL_XUAT = 0
          this.$toast.error('Số lượng xuất không được âm!')
        } else {
          changes.changedRecords[0].SL_XUAT = arg.value
        }
      }
      grv.batchUpdate(changes)
      this.$refs.grvDS_ThietBi.grid.refreshColumns()

      var soluong = 0
      this.grcDS_ThietBi.forEach((e) => {
        if (this.DSThietBi_Checked.includes(e.THIETBI_ID)) {
          soluong = soluong + parseFloat(e.SL_XUAT)
        }
      })
      this.sl = Math.round(soluong * 100) / 100
      this.Text.lblSL = 'SL cáp đã chọn: ' + this.sl + '/' + this.soluong_tong + ''
    }
  },
  created: async function() {},
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
    DSThietBi_Checked: function(value) {
      this.DSThietBi_Checked = value
      var soluong = 0
      this.grcDS_ThietBi.forEach((e) => {
        if (value.includes(e.THIETBI_ID)) {
          soluong = soluong + parseFloat(e.SL_XUAT)
        }
      })
      this.sl = Math.round(soluong * 100) / 100
      this.Text.lblSL = 'SL cáp đã chọn: ' + this.sl + '/' + this.soluong_tong + ''
    },
    checkAllVatTuCap: function(value) {
      if (value) {
        for (const e of this.grcDS_ThietBi) {
          this.DSThietBi_Checked.push(e.THIETBI_ID)
        }
      } else {
        this.DSThietBi_Checked = []
      }

      var soluong = 0
      this.grcDS_ThietBi.forEach((e) => {
        if (this.DSThietBi_Checked.includes(e.THIETBI_ID)) {
          soluong = soluong + parseFloat(e.SL_XUAT)
        }
      })
      this.sl = Math.round(soluong * 100) / 100
      this.Text.lblSL = 'SL cáp đã chọn: ' + this.sl + '/' + this.soluong_tong + ''
    },

    // Rem dùng lại hàm của grid
    listVatTuChecked: function(value) {
      this.listVatTuChecked = value
      this.getSoLuongDaChon()
    },
    checkAllVatTuSerial: function(value) {
      if (value) {
        for (const e of this.gridVatTu) {
          this.listVatTuChecked.push(e.THIETBI_ID)
        }
      } else {
        this.listVatTuChecked = []
      }
      this.getSoLuongDaChon()
    }
  },
  destroyed() {
    this.DSThietBi_Checked = []
  }
}
</script>
<style>
.frm-ganseri .e-grid tr.e-emptyrow td {
  text-align: center !important;
}
#div-seri .e-grid .e-content {
  max-height: 500px !important;
}
</style>
