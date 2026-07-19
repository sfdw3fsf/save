<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings"
              ref="dialogTraCuThongTinNo" :position="position" :header="'Tra cứu thông tin nợ'" showCloseIcon="true"
              width="80%" target="#app .main-wrapper">
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
        <div class="col-md-6 col-12">
          <div class="box-form">
            <div class="legend-title">Thông tin tra cứu</div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">Kỳ hóa đơn</div>
                  <div class="value">
                    <k-date-picker ref="dtpKyHoaDon" format="MM/YYYY" type="datetime"
                                   v-model="frmProps.dtpKyHoaDon"/>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w90">Số máy/Acc</div>
                  <div class="value">
                    <input type="text" ref="txtSoMay" v-model="frmProps.txtSoMay" v-on:keyup.enter="TCThongTinNo"
                           class="form-control highlight">
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">Chu kỳ</div>
                  <div class="value">
                    <div class="select-custom">
                      <Select2 ref="cboChuKy" :disabled='false' v-model="cboChuKy.value"
                               :options="cboChuKy.list" :settings="settingSelect2"/>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w90">Mã TT</div>
                  <div class="value">
                    <input type="text" ref="txtMaTT" v-model="frmProps.txtMaTT" v-on:keyup.enter="TCThongTinNo"
                           class="form-control">
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-6 col-12">
          <div class="box-form">
            <div class="legend-title">Thông tin khách hàng</div>
            <div class="row">
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key w90">Tên TT</div>
                  <div class="value">
                    <input type="text" ref="txtTenTT" v-model="frmProps.txtTenTT"
                           class="form-control">
                  </div>
                </div>
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w90">Tổng nợ</div>
                  <div class="value">
                    <input type="number" ref="txtTongNo" v-model="frmProps.txtTongNo"
                           class="form-control">
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Địa chỉ TT</div>
              <div class="value">
                <input type="text" ref="txtDiaChiTT" v-model="frmProps.txtDiaChiTT"
                       class="form-control">
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12 col-12">
          <div class="box-form">
            <div class="legend-title">Danh sách tiến trình xử lý</div>
            <b-tabs>
              <b-tab title="Thông tin nợ" active>
                <DataGrid ref="gridviewThongTinNo" v-bind:columns="gridviewThongTinNo.headers"
                          v-bind:dataSource="gridviewThongTinNo.data" :showColumnCheckbox="false"
                          :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                          :enabled-select-first-row="true"
                ></DataGrid>
              </b-tab>
              <b-tab title="Thông tin trả">
                <DataGrid ref="gridviewThongTinTra" v-bind:columns="gridviewThongTinTra.headers"
                          v-bind:dataSource="gridviewThongTinTra.data" :showColumnCheckbox="false"
                          :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                          :enabled-select-first-row="true"
                ></DataGrid>
              </b-tab>
            </b-tabs>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import TCThongTinNoAPI from './TCThongTinNoAPI'

export default {
  name: 'TCThongTinNo',
  components: {
    KDatePicker
  },
  data () {
    return {
      actions: [
        {
          id: 'tsbtnTraCuu',
          name: 'Tra cứu',
          active: true,
          icon_class: 'one-file-search',
          visible: true
        }
      ],
      animationSettings: {effect: 'Zoom'},
      position: {X: 'center', Y: 'top'},
      frmProps: {
        dtpKyHoaDon: moment(new Date()).format('MM/YYYY'),
        txtSoMay: '',
        txtMaTT: '',
        txtTenTT: '',
        txtDiaChiTT: '',
        txtTongNo: ''
      },
      settingSelect2: {
        language: 'vi'
      },
      ma_tb: '',
      ma_tt: '',
      loaitien_id: 1,
      kieu: 1,
      kyhoadon: '',
      chukyno: '',
      thongtinThanhToanTB: [],
      cboChuKy: {
        list: [],
        value: ''
      },
      gridviewThongTinNo: {
        data: [],
        headers: [
          {
            fieldName: 'TENKHOANMUC',
            headerText: 'Tên khoản mục',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'NODK',
            headerText: 'Nợ đầu kỳ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'NOPS',
            headerText: 'Nợ phát sinh',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TRADK',
            headerText: 'Trả đầu kỳ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TRAPS',
            headerText: 'Trả phát sinh',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'DCDAUKY',
            headerText: 'ĐC đầu kỳ ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'DCPS',
            headerText: 'ĐC phát sinh',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'CONNODK',
            headerText: 'Còn nợ ĐK',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'CONNOPS',
            headerText: 'Còn nợ PS',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: 0,
        checked: [],
        selectItem: null
      },
      gridviewThongTinTra: {
        data: [],
        headers: [
          {
            fieldName: 'MA_TB',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TIENTRA',
            headerText: 'Tiền trả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'NGAY_TT',
            headerText: 'Ngày trả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'SOPHIEU',
            headerText: 'Số phiếu',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'NGUOIGACH',
            headerText: 'Người gạch',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: 0,
        checked: [],
        selectItem: null
      }
    }
  },
  watch: {
    'frmProps.dtpKyHoaDon': function (newValue) {
      this.FN_DTPKYHOADON(newValue)
    }
  },
  methods: {
    async handleShowModal () {
      await this.LAY_KY_CUOC_HIEN_HANH()
      await this.GET_CHUKYNO_THEO_KYCUOC()
      await this.FN_TCTHONGTINNO_LOAD()
      if (this.frmProps.txtSoMay) {
        await this.HienThi_DanhSach()
      }
    },
    openDialog (maTB) {
      this.frmProps.txtSoMay = maTB
      this.handleShowModal()
      this.$refs.dialogTraCuThongTinNo.show()
    },
    hideModal () {
      this.$bvModal.hide('popupTraCuuThongTinNo')
    },
    async GET_CHUKYNO_THEO_KYCUOC () {
      let khd = moment(this.kyhoadon, 'YYYYMMDD').format('YYYYMM')
      await TCThongTinNoAPI.GET_CHUKYNO_THEO_KYCUOC(this.axios, khd).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
          this.chukyno = response.data.data[0].CHUKY
        }
      })
    },
    async LAY_KY_CUOC_HIEN_HANH () {
      await TCThongTinNoAPI.LAY_KY_CUOC_HIEN_HANHN_HANH(this.axios).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.kyhoadon = response.data.data[0].KYCUOC.toString()
          this.frmProps.dtpKyHoaDon = moment(this.kyhoadon, 'YYYYMMDD').format('MM/YYYY')
        }
      })
    },
    async FN_TCTHONGTINNO_LOAD () {
      let req = {
        namthang: moment(this.kyhoadon, 'YYYYMMDD').format('YYYYMM')
      }
      let data = this.GetData(await TCThongTinNoAPI.FN_TCTHONGTINNO_LOAD(this.axios, {
        p_ds_para: JSON.stringify(req)
      }))
      if (data) {
        let dsChuky = JSON.parse(data.p_js_returns).DS_CHUKYNO
        if (dsChuky.length > 0) {
          this.cboChuKy.list = dsChuky.map(item => {
            return {
              id: item.CHUKY,
              text: item.CHUKY
            }
          })
          this.cboChuKy.value = this.cboChuKy.list[0].id
        } else {
          this.cboChuKy.list = []
          this.cboChuKy.value = ''
        }
      }
    },
    async FN_DTPKYHOADON (value) {
      let date = this.getDate(value, 'MM/YYYY').toDate()
      let req = {
        thang: date.getUTCMonth() + 1,
        nam: date.getFullYear()
      }
      let data = this.GetData(await TCThongTinNoAPI.FN_DTPKYHOADON(this.axios, {
        p_ds_para: JSON.stringify(req)
      }))
      if (data != null) {
        let jsonReturn = JSON.parse(data.p_js_returns)
        if (data.kq === '1') {
          let dsChuKy = jsonReturn.DS_CHUKY
          if (dsChuKy.length > 0) {
            if (dsChuKy.length > 0) {
              this.cboChuKy.list = dsChuKy.map(item => {
                return {
                  id: item.CHUKY_ID,
                  text: item.GHICHU
                }
              })
              this.cboChuKy.value = this.cboChuKy.list[0].id
            } else {
              this.cboChuKy.list = []
              this.cboChuKy.value = ''
            }
          }
        } else {
          this.$root.$toast.warning(jsonReturn.MESSAGE)
        }
      }
    },
    TCThongTinNo () {
      if (this.frmProps.txtMaTT) {
        this.HienThi_DanhSach()
        if (this.thongtinThanhToanTB.length <= 0) {
          this.frmProps.txtSoMay = ''
          this.frmProps.txtTongNo = ''
        }
      }
    },
    async HienThi_DanhSach () {
      try {
        if (!this.cboChuKy.value) {
          this.$root.$toast.warning('Kỳ hóa đơn : ' + this.frmProps.dtpKyHoaDon + 'không có chu kỳ tính cước ! Bạn hãy xem lại')
          return
        }
        let ck = ''
        if (this.cboChuKy.value.length < 2) {
          ck = '0' + this.cboChuKy.value
        } else {
          ck = this.cboChuKy.value
        }
        let kyhd = moment(this.frmProps.dtpKyHoaDon, 'MM/YYYY').format('YYYYMM')
        let req = {
          'kyhd': kyhd,
          'chuky': ck,
          'kyhd_reg': this.kyhoadon,
          'chuky_reg': this.chukyno,
          'somay': this.frmProps.txtSoMay.trim(),
          'ma_tt': this.frmProps.txtMaTT.trim(),
          'loaitien_id': this.loaitien_id
        }
        let data = this.GetData(await TCThongTinNoAPI.FN_TCTHONGTINNO_HIENTHI_DANHSACH(this.axios, {
          p_ds_para: JSON.stringify(req)
        }))
        if (data != null) {
          let jsonReturn = JSON.parse(data.p_js_returns)
          console.log(jsonReturn)
          if (data.kq === '1') {
            this.thongtinThanhToanTB = jsonReturn.THONGTIN_TT_TB
            if (this.thongtinThanhToanTB.length > 0) {
              this.frmProps.txtMaTT = this.thongtinThanhToanTB[0].MA_TT
              this.frmProps.txtSoMay = this.thongtinThanhToanTB[0].MATB_DD
              this.frmProps.txtTenTT = this.thongtinThanhToanTB[0].TEN_TT
              this.frmProps.txtDiaChiTT = this.thongtinThanhToanTB[0].DIACHI_TT
              this.ma_tt = this.thongtinThanhToanTB[0].DIACHI_TT
              this.ma_tb = this.thongtinThanhToanTB[0].MATB_DD
            }
            this.gridviewThongTinNo.data = jsonReturn.THONGTIN_NO

            this.gridviewThongTinTra.data = jsonReturn.THONGTIN_TRA

            this.frmProps.txtTongNo = jsonReturn.TONGNO[0].TONGNO
          } else {
            this.$root.$toast.error(jsonReturn.MESSAGE)
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗiL: ' + error.message)
      }
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
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    tsbtnTraCuu_Click () {
      if (!this.frmProps.txtMaTT && !this.frmProps.txtSoMay) {
        this.$root.$toast.warning('Bạn hãy nhập mã thuê bao hoặc mã thanh toán!')
        this.$refs.txtSoMay.focus()
        return
      }
      this.HienThi_DanhSach()
    },
    onActionClick (action) {
      if (action.id === 'tsbtnTraCuu') {
        this.tsbtnTraCuu_Click()
      }
    },
    getDate (dateStr, format) {
      if (format) {
        return moment(dateStr, format)
      } else {
        return moment(dateStr, 'DD/MM/YYYY HH:mm A')
      }
    }
  }
}
</script>

<style scoped>

</style>
