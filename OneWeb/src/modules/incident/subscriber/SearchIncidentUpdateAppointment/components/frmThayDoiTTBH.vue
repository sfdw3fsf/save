<template>
  <div>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjfrmThayDoiTTBH" :position="position" :close="closeDialog" :header="' Thay đổi thông tin báo hỏng'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
      <div>
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="#" @click.prevent="tsbCapNhat_Click"> <span class="icon one-reload1"></span>Cập nhật </a>
            </li>
            <li>
              <a href="#" @click.prevent="tsbClear_Click"> <span class="icon one-trash"></span>Clear </a>
            </li>
            <!-- <li>
              <a href="#"> <span class="icon one-user-mess"></span>Trợ giúp </a>
            </li> -->
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Số máy/Acc</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtMaTB" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Tên thuê bao</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtTenTB" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Dịch vụ</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtTrangthaiTB" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Ngày báo hỏng</div>
                  <div class="value">
                    <date-picker :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="dtpNgayBH" :type="typeFormat" :disabled-date="disabledRange">
                      <template #icon-calendar>
                        <span class="icon one-calendar"></span>
                      </template>
                    </date-picker>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="legend-title">Thông tin cũ</div>
                <div class="info-row">
                  <div class="key w100">Người BH</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtNguoiBHcu" />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Điện thoại LH</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtDienThoaiLHcu" />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Địa chỉ LĐ</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtDiaChiLDCu" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="legend-title">Thông tin mới</div>
                <div class="info-row">
                  <div class="key w100">Người BH</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtNguoiBHmoi" ref="txtNguoiBHmoi" maxlength="250" />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Điện thoại LH</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtDienthoaiLHmoi" ref="txtDienthoaiLHmoi" maxlength="15" />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Địa chỉ LĐ</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="txtDiaChiLDMoi" maxlength="250" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import api from '../Api'
import frmApi from './PopUpApi.js'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
export default {
  name: 'frmThayDoiTTBH',
  components: { moment, DatePicker, api, frmApi },
  props: {
    params: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  watch: {
    Loading: function (val) {
      this.$root.showLoading(val)
    }
  },
  async created() {
    let ds_kt1 = await this.lay_ds_thamso_macdinh()
    if (ds_kt1 != null && ds_kt1.length > 0) {
      let str_vtemp = 'KIEMTRA_QUYEN_SUA_LH_BH'
      let det = ds_kt1.filter((x) => x.ma_ts == str_vtemp)
      // ds_kt1.Tables[0].AsEnumerable()
      //                         .Where(x => x["ma_ts"].ToString() == str_vtemp).ToList();
      if (det != null && det.length > 0) {
        let row = det[0]
        if (row['ten_ts'] == '1') this.KIEMTRA_QUYEN_SUA_LH_BH = 1
      }
      str_vtemp = 'CAPNHAT_DIACHI_BAOHONG'
      det = ds_kt1.filter((x) => x.ma_ts == str_vtemp)
      // det = ds_kt1.Tables[0]
      //   .AsEnumerable()
      //   .Where((x) => x['ma_ts'].ToString() == str_vtemp)
      //   .ToList()
      if (det != null && det.length > 0) {
        let row = det[0]
        if (row['ten_ts'] == '1') this.CAPNHAT_DIACHI_BAOHONG = 1
      }
    }

    this.txtMaTB = this.matb_t
    this.txtTrangthaiTB = this.trangthaitb_t
    this.txtTenTB = this.tentb_t
    this.dtpNgayBH = this.ngaybh_t
    this.txtNguoiBHcu = this.nguoibh_t
    this.txtDienThoaiLHcu = this.dienthoailh_t
    this.txtDiaChiLDCu = this.diachild_t
  },
  data() {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      typeFormat: 'datetime',
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      txtMaTB: '',
      txtTenTB: '',
      txtTrangthaiTB: '',
      dtpNgayBH: '',
      txtNguoiBHcu: '',
      txtDienThoaiLHcu: '',
      txtDiaChiLDCu: '',
      txtNguoiBHmoi: '',
      txtDienthoaiLHmoi: '',
      txtDiaChiLDMoi: '',
      KIEMTRA_QUYEN_SUA_LH_BH: -1,
      CAPNHAT_DIACHI_BAOHONG: -1,
      matb_t: '',
      trangthaitb_t: '',
      tentb_t: '',
      ngaybh_t: '',
      nguoibh_t: '',
      dienthoailh_t: '',
      diachild_t: '',
      baohong_id_t: '',
      nguoi_cn_t: '',
      ngay_cn_t: '',
      nhanvien_id_t: 0,
      may_cn_t: '',
      donvi_id_t: 0,
      ip_t: '',
      result: false
    }
  },
  methods: {
    openDiaLog() {
      this.txtMaTB = ''
      this.txtTrangthaiTB = ''
      this.txtTenTB = ''
      this.dtpNgayBH = ''
      this.txtNguoiBHmoi = ''
      this.txtDienthoaiLHmoi = ''
      this.txtDiaChiLDMoi = ''
      this.getDataFromProp()
      this.txtMaTB = this.matb_t
      this.txtTrangthaiTB = this.trangthaitb_t
      this.txtTenTB = this.tentb_t
      this.dtpNgayBH = this.ngaybh_t
      this.txtNguoiBHcu = this.nguoibh_t
      this.txtDienThoaiLHcu = this.dienthoailh_t
      this.txtDiaChiLDCu = this.diachild_t
      this.$refs.dialogObjfrmThayDoiTTBH.show()
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
    disabledRange: function (date) {
      return date > new Date()
    },
    closeDialog() {
      this.$emit('closefrmThayDoiTTBH', this.result)
    },
    async tsbCapNhat_Click() {
      this.result = true
      try {
        if (this.txtNguoiBHmoi == '') {
          this.ShowError('Hãy nhập tên người báo hỏng mới')
          this.$refs.txtNguoiBHmoi.focus()
          return
        }
        if (this.txtDienthoaiLHmoi == '') {
          this.ShowError('Hãy nhập số điện thoại liên hệ mới')
          this.$refs.txtDienthoaiLHmoi.focus()
          return
        }
        this.$bvModal
          .msgBoxConfirm('Bạn có muốn cập nhật thông tin này? ', {
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
            let response = await this.fn_thaydoi_tt_bh()
            if (response.data.error === 200 || response.data.error === '200') {
              let dataJson = JSON.parse(response.data.data)
              if (dataJson.ERROR_CODE == 1) {
                this.ShowSuccess('Cập nhật thành công')
                this.$refs.dialogObjfrmThayDoiTTBH.hide()
              } else this.ShowError(response.data.data)
            } else {
              this.ShowError(response.data.error_code)
            }
          })
      } catch (err) {
        this.ShowError('' + err.message)
      }
    },
    async tsbClear_Click() {
      this.txtNguoiBHmoi = ''
      this.txtDienthoaiLHmoi = ''
      this.txtDiaChiLDMoi = ''
      this.$refs.txtNguoiBHmoi.focus()
    },
    lay_ds_thamso_macdinh: async function () {
      var input = { kieu_id: 0 }
      return this.GetData(await api.lay_ds_thamso_macdinh(this.axios, input))
    },
    fn_thaydoi_tt_bh: async function () {
      let data = {
        KIEMTRA_QUYEN_SUA_LH_BH: this.KIEMTRA_QUYEN_SUA_LH_BH,
        NHOMND_ID: 1,
        BAOHONG_ID: this.baohong_id_t,
        NGUOI_BH_MOI: this.txtNguoiBHmoi,
        DIENTHOAI_LH_MOI: this.txtDienthoaiLHmoi,
        DIACHILD_MOI: this.txtDiaChiLDMoi,
        NGUOI_CN_T: this.nguoi_cn_t,
        MAY_CN_T: this.may_cn_t,
        IP_CN_T: this.ip_t,
        DONVI_ID_T: this.donvi_id_t,
        NHANVIEN_ID_T: this.nhanvien_id_t,
        MA_TB_T: this.matb_t,
        CAPNHAT_DIACHI_BAOHONG: this.CAPNHAT_DIACHI_BAOHONG
      }
      let phanvung_id = this.$root.token.getPhanVungID()
      var input = {
        ds_para: JSON.stringify(data),
        phanvung_id: phanvung_id
      }
      return frmApi.fn_thaydoi_tt_bh(this.axios, input)
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>