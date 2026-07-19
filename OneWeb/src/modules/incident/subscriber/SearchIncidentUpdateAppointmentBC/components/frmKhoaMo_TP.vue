<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjKhoaMoTP" :position="position" :close="closeDialog" :header="'Khóa/mở TP'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
    <div class="popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="btnLayTT_Click"> <span class="icon one-reload1"></span>Lấy TT </a>
          </li>
          <li>
            <a href="#" @click.prevent="tsbtnGhiLai_Click"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Ghi lại</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100 padt0">Số máy/Acc</div>
                <div class="value">
                  <input type="text" v-model="txtSoAcc" class="form-control" />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100 padt0">Kiểu YC</div>
                <div class="value">
                  <SelectExt v-model="cboKieuYC" :placeholder="`Chọn kiểu yc`" :dataSource="cboKieuYCList" dataTextField="kieu_yc" dataValueField="id"></SelectExt>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w100 padt0">Hướng giao</div>
                <div class="value">
                  <input type="text" v-model="txtHuongGiao" class="form-control" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w100 padt0">Nội dung TH</div>
                <div class="value">
                  <textarea type="text" v-model="txtNoiDung" class="form-control" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Lịch sử khoá phiếu</div>
          <DataGrid v-bind:columns="grvLS_KhoaPhieu.headers" :enabledSelectFirstRow="true" v-bind:dataSource="grvLS_KhoaPhieu.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true"> </DataGrid>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import api from '../Api'
import frmApi from './PopUpApi.js'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
export default {
  name: 'frmKhoaMo_TP',
  components: { moment, DatePicker, api, frmApi },
  props: {
    params: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  data() {
    return {
      Loading: false,
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      ckbSendSMS: true,
      txtGhiChu: '',
      dtpNgayHenTu: '',
      dtpNgayHenDen: '',
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      typeFormat: 'datetime',
      txtSoAcc: '',
      cboKieuYC: '',
      cboKieuYCList: [],
      txtNoiDung: '',
      txtHuongGiao: '',
      grvLS_KhoaPhieu: {
        headers: [
          { fieldName: 'ngay_th', headerText: 'Ngày TH' },
          { fieldName: 'huonggiao', headerText: 'Hướng giao' },
          { fieldName: 'tenkieu_yc', headerText: 'Kiểu YC' },
          { fieldName: 'noidung', headerText: 'Nội dung' },
          { fieldName: 'ten_nv', headerText: 'Nhân viên TH' },
          { fieldName: 'ten_dv', headerText: 'Đơn vị ' },
          { fieldName: 'ma_nd', headerText: 'Người CN' }
        ],
        data: [],
        selectedItem: {},
        selectedItems: []
      },
      ma_tb: '',
      vphieu_id: 0,
      vbaohong_id: 0,
      vhuonggiao: 0
    }
  },
  watch: {
    Loading: function (val) {
      this.$root.showLoading(val)
    }
  },
  methods: {
    openDialog() {
      try {
        this.getDataFromProp()
        this.Loading = true
        Promise.all([this.frmKhoaMo_TP_Load()])
          .then((r) => {
            console.log(r)
          })
          .finally((x) => {
            this.Loading = false
          })
      } catch (ex) {
        this.Loading = false
      } finally {
        this.Loading = false
        this.$refs.dialogObjKhoaMoTP.show()
      }
    },
    frmKhoaMo_TP_Load() {
      Promise.all([this.LOAD_DS(), this.LOAD_COMBO(), this.CLEAR()])
        .then((r) => {})
        .catch((err) => {
          console.log(err)
        })
        .finally(() => {
          this.txtHuongGiao = this.vhuonggiao
          this.txtSoAcc = this.ma_tb
        })
    },
    async LOAD_DS() {
      let data = this.GetData(await frmApi.sp_lay_ds_phieubh_khoamotp(this.axios, { baohong_id: this.vbaohong_id }))
      this.grvLS_KhoaPhieu.data = data
    },
    async LOAD_COMBO() {
      let data = this.GetData(await frmApi.sp_lay_ds_kieuyc_khoamotp(this.axios, { baohong_id: this.vbaohong_id }))
      this.cboKieuYCList = data
    },
    async CLEAR() {
      this.txtNoiDung = ''
      this.cboKieuYC = -1
    },
    async GHILAI() {
      try {
        if (this.vbaohong_id <= 0) {
          this.ShowError('Không lấy được thông tin báo hỏng (ID) không thể cập nhật tình trạng khoá phiếu')
          return
        }
        if (this.vphieu_id <= 0) {
          this.ShowError('Không lấy được thông tin phiếu (ID) không thể cập nhật tình trạng khoá phiếu')
          return
        }
        if (this.cboKieuYC == '-1') {
          this.ShowError('Chưa chọn kiểu yêu cầu không thể cập nhật trình trạng khoá phiếu')
          return
        }
        this.$bvModal
          .msgBoxConfirm('Bạn có chắc chắn muốn cập nhật tình trạng trả phiếu báo hỏng không?', {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              return
            }
            // Xử lý code ở đây
            this.Loading = true
            await this.sp_capnhat_khoamotp()
            this.ShowSuccess('Cập nhật tình trạng trả phiếu bảo hỏng thành công')
            this.CLEAR()
            await this.LOAD_DS()
            this.Loading = false
          })
      } catch (ex) {
        this.ShowError('Có lỗi trong quá trình khoá phiếu ' + ex.message)
      } finally {
        this.Loading = false
      }
    },
    async sp_capnhat_khoamotp() {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let nguoidungId = this.$root.token.getNguoiDungID()
      let phanvungid = this.$root.token.getPhanVungID()
      let username = this.$root.token.getUserName()
      let input = { phanvung_id: phanvungid, phieu_id: this.vphieu_id, nhanvien_id: nhanvienId, nguoidung_id: nguoidungId, donvi_id: donviId, ma_nd: username, km_tp: this.cboKieuYC, noidung: this.txtNoiDung, may_cn: 'admin', ip_cn: 'admin', baohong_id: this.vbaohong_id }
      let data = this.GetData(await frmApi.sp_capnhat_khoamotp(this.axios, input))
      return data
    },
    getDataFromProp() {
      if (this.params != null) {
        let keys = Object.keys(this.params)
        for (const k of keys) {
          console.log('getDataFromProp:', k, ',value:', this.params[k])
          if (this.params[k] != null && this.params[k] != undefined) this[k] = this.params[k]
        }
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    closeDialog() {
      this.$emit('closeDialog', false)
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    },
    async btnLayTT_Click() {
      try {
        this.Loading = true
        await this.LOAD_DS()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnGhiLai_Click() {
      try {
        this.Loading = true
        await this.GHILAI()
      } catch (err) {
        this.Loading = false
      } finally {
        this.Loading = false
      }
    }
  }
}
</script>