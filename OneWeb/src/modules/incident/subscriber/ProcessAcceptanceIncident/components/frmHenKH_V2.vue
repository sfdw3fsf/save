<template>
  <div>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjHenKH" :position="position" :close="closeDialog" :header="'Cập nhật thông tin hẹn'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
      <div class="popup-box">
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="#" @click.prevent="tsbtnCapNhat_Click"> <span class="icon one-reload1"></span>Cập nhật </a>
            </li>
            <li>
              <a href="#" @click.prevent="Thoat"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát</a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <div class="info-row">
              <div class="key w80 padt0">Lý do hẹn</div>
              <div class="value">
                <SelectExt v-model="cboLyDoHen" :placeholder="`Chọn lý do hẹn`" :dataSource="cboLyDoHenList" dataTextField="lydohen" dataValueField="lydohen_id"></SelectExt>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">Từ giờ</div>
                  <div class="value">
                    <date-picker ref="dtpNgayHenTu" :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="dtpGioHen_Tu" :type="typeFormat" :disabled-date="disabledRange">
                      <template #icon-calendar>
                        <span class="icon one-calendar"></span>
                      </template>
                    </date-picker>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80">Đến giờ</div>
                  <div class="value">
                    <date-picker ref="dtpNgayHenDen" :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="dtpGioHen_Den" :type="typeFormat">
                      <template #icon-calendar>
                        <span class="icon one-calendar"></span>
                      </template>
                    </date-picker>
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w80">Ghi chú</div>
              <div class="value">
                <textarea   rows="5" v-model="txtGhiChu" class="form-control" />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="table-content">
            <DataGrid v-bind:columns="gridViewLichSuHen.headers" :enabledSelectFirstRow="true" v-bind:dataSource="gridViewLichSuHen.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="false" :showFilter="true"> </DataGrid>
          </div>
        </div>
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
// // WinUI.WinUIQLDHXLSuCo.frmHenKH_v2
import api from '../Api'
import frmApi from './PopUpApi.js'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
export default {
  name: 'frmHenKHV2',
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
      cboLyDoHen: '',
      cboLyDoHenList: [],
      txtGhiChu: '',
      dtpGioHen_Tu: '',
      dtpGioHen_Den: '',
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      typeFormat: 'datetime',
      gridViewLichSuHen: {
        headers: [
          { fieldName: 'giohen_tu', headerText: 'Ngày hẹn từ' },
          { fieldName: 'giohen_den', headerText: 'Ngày hẹn đến' },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN' },
          { fieldName: 'nguoi_cn', headerText: 'Người CN' },
          { fieldName: 'noidung', headerText: 'Nội dung' },
          { fieldName: 'lydohen', headerText: 'Lý do hẹn' },
          { fieldName: 'dv_hen', headerText: 'ĐV hẹn' },
          { fieldName: 'kieu_hen', headerText: 'Kiểu hẹn' },
          { fieldName: 'lydo_xm', headerText: 'KQ xác minh' }
        ],
        data: [],
        selectedItem: {},
        selectedItems: []
      },
      phieu_id: 0,
      baohong_id: 0,
      thuchien: false,
      Quyen_Duoc_hen: 0
    }
  },
  watch: {
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  methods: {
    openDialog() {
      try {
        this.getDataFromProp()
        this.Loading = true
        Promise.all([this.frmHenKHV2_Load()])
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
        this.$refs.dialogObjHenKH.show()
      }
    },
    closeDialog() {},
    Thoat() {
      this.$refs.dialogObjHenKH.hide()
    },
    async map_ttbh_id(baohong_id) {
      let input = { id_neo: 'ttbh_id', in_table: 'baohong.baohong', in_dk: `where baohong_id= ${this.baohong_id}` }
      let data = this.GetData(await api.map_id(this.axios, input))
      return data
    },
    async map_ngay_bh(baohong_id) {
      let input = { id_neo: "to_char(ngay_bh,'dd/mm/yyyy hh24:mi:ss')", in_table: 'baohong.baohong', in_dk: `where baohong_id= ${baohong_id}` }
      let data = this.GetData(await api.map_id(this.axios, input))
      return data
    },
    async tsbtnCapNhat_Click() {
      try {
        //ThongTinNguoiDung tt_nd = new ThongTinNguoiDung();
        if (this.Quyen_Duoc_hen == 0) {
          this.ShowError('Bạn chưa được cấp quyền hẹn xử lý với khách hàng')
          return
        }
        if (this.dtpGioHen_Tu == null || this.dtpGioHen_Den == null) {
          this.ShowError(`Bạn chưa cập nhật giờ hẹn !`)
          return
        }
        if (this.dtpGioHen_Tu == '' || this.dtpGioHen_Den == '') {
          this.ShowError(`Bạn chưa cập nhật giờ hẹn !`)
          return
        }
        let ttbh = ''
        ttbh = await this.map_ttbh_id(this.baohong_id) // checkdata.MAP_ID("ttbh_id", DatabaseConstants.DB7 + ".baohong", "where baohong_id=" + baohong_id);
        if (ttbh == '6') {
          this.ShowError('Báo hỏng đã hoàn thành. Không thể cập nhật giờ hẹn!')
          return
        } else if (ttbh == '-1') {
          //thêm trường hợp ngoại lệ cho chắc
          this.ShowError('Báo hỏng không tồn tại !')
          return
        }
        //Lấy ngày báo hỏng
        let ngay_bh = await this.map_ngay_bh(this.baohong_id) //  checkdata.MAP_ID("to_char(ngay_bh,'dd/mm/yyyy hh24:mi:ss')", DatabaseConstants.DB7 + ".baohong", "where baohong_id=" + baohong_id);
        let dNgayBH = moment(ngay_bh, 'DD/MM/YYYY HH:mm:ss') // DateTime.ParseExact(ngay_bh, "dd/MM/yyyy HH:mm:ss", System.Globalization.CultureInfo.CurrentCulture, System.Globalization.DateTimeStyles.None);
        let dNgayHenTu = moment(this.dtpGioHen_Tu, 'DD/MM/YYYY HH:mm:ss')
        let dNgayHenDen = moment(this.dtpGioHen_Den, 'DD/MM/YYYY HH:mm:ss')
        let iNgayHenTu = parseInt(moment(dNgayHenTu).format('YYYYMMDDHHmmss'))
        let iNgayHenDen = parseInt(moment(dNgayHenDen).format('YYYYMMDDHHmmss'))
        let idNgayBH = parseInt(moment(dNgayBH).format('YYYYMMDDHHmmss'))
        let iMoment = parseInt(moment().format('YYYYMMDDHHmmss'))
        // console.log('iNgayHenTu', iNgayHenTu, 'iNgayHenDen', iNgayHenDen, 'iMoment', iMoment)
        if (iNgayHenTu <= idNgayBH || iNgayHenDen <= idNgayBH) {
          this.ShowError('Ngày hẹn phải lớn hơn ngày báo hỏng!')
          return
        }
        // if (dNgayHenTu > dNgayHenDen) {
        if (iNgayHenTu > iNgayHenDen) {
          this.ShowError('Ngày hẹn từ không thể lớn hơn Ngày hẹn đến. Hãy kiểm tra lại!')
          return
        }
        if (iNgayHenTu < iMoment) {
          this.ShowError('Ngày bắt đầu hẹn không thể nhỏ hơn ngày hiện tại!')
          this.$refs.dtpNgayHenTu.focus()
          return
        }
        if (iNgayHenDen < iMoment) {
          this.ShowError('Ngày hẹn đến không thể nhỏ hơn ngày hiện tại!')
          this.$refs.dtpNgayHenDen.focus()
          return
        }
        if (moment(dNgayHenTu).format('HH:mm:ss') == '00:00:00') {
          this.ShowError('Ngày bắt đầu hẹn có định dạng Giờ:phút:giây là 00:00:00. Hãy kiểm tra lại!')
          this.$refs.dtpNgayHenTu.focus()
          return
        }
        if (moment(dNgayHenDen).format('HH:mm:ss') == '00:00:00') {
          this.ShowError('Ngày hẹn đến có định dạng Giờ:phút:giây là 00:00:00. Hãy kiểm tra lại!')
          this.$refs.dtpNgayHenDen.focus()
          return
        }
        let imomentAdd1Hours = parseInt(moment().add(1, 'hours').format('YYYYMMDDHHmmss'))
        if (this.Quyen_Duoc_hen == 1 && iNgayHenDen < imomentAdd1Hours) {
          // TTKD hẹn -> giờ hẹn đến phải ít nhất 1h
          this.ShowError('Giờ hẹn đến phải lớn hơn thời gian hiện tại ít nhất 1 giờ!')
          return
        }
        //Kiểm tra hẹn
        //TruyenDanFacade tdan = new TruyenDanFacade();
        //var kq = tdan.GET_VALUE_FUNC('{?DB7}.kiemtra_hen_baohong_v2', 'vbaohong_id', baohong_id, 'vlydohen_id', Convert.ToInt32(cboLyDoHen.EditValue), 'vquyen_hen', Quyen_Duoc_hen)
        let kq = await this.kiemtra_hen_baohong()
        if (kq == null || kq == '') {
          this.ShowError('Không lấy được kết quả kiểm tra!')
          return
        } else {
          if (kq != 'ok') {
            this.ShowError(kq)
            return
          }
        }
        // BaoHong_Facade Baohong = new BaoHong_Facade();
        // if (this.Quyen_Duoc_hen == 2)//TTVT
        //     Baohong.Capnhat_giohen_v2(baohong_id, phieu_id, dtpGioHen_Tu.Value, dtpGioHen_Den.Value,
        //         tt_nd.nhanvien_id, txtGhiChu.Text.Trim(), tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip, Convert.ToInt32(cboLyDoHen.EditValue));
        // if (this.Quyen_Duoc_hen == 1)//TTKD
        //     Baohong.Capnhat_giohen_ttkd(baohong_id, phieu_id, dtpGioHen_Tu.Value, dtpGioHen_Den.Value,
        //         tt_nd.nhanvien_id, txtGhiChu.Text.Trim(), tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip, Convert.ToInt32(cboLyDoHen.EditValue));
        if (this.Quyen_Duoc_hen == 2) {
          await this.capnhat_giohen_v2()
        }
        if (this.Quyen_Duoc_hen == 1) {
          await this.capnhat_giohen_ttkd()
        }
        this.ShowSuccess('Cập nhật thành công')
        await this.HienThiDanhSach()
        this.thuchien = true
        this.Close()
      } catch (ex) {
        this.ShowError('Lỗi: ' + ex.message)
        this.thuchien = false
      }
    },
    disabledRange: function (date) {
       return date < moment().subtract(1, "days");
    },
    Close() {
      this.$emit('closeHenKH', this.thuchien)
      this.Thoat()
    },
    async frmHenKHV2_Load() {
      this.sp_lydohen_bh()
      this.HienThiDanhSach()
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
    async sp_lydohen_bh() {
      let input = { kieu_hen: this.Quyen_Duoc_hen == 2 ? 1 : 2 }
      let data = this.GetData(await frmApi.sp_lydohen_bh(this.axios, input))
      this.cboLyDoHenList = data
      if (this.Quyen_Duoc_hen == 1) this.cboLyDoHen = 6
      return data
    },
    async kiemtra_hen_baohong() {
      let input = { vbaohong_id: this.baohong_id, vlydohen_id: this.cboLyDoHen, vquyen_hen: this.Quyen_Duoc_hen }
      // let data = this.GetData(await frmApi.kiemtra_hen_baohong(this.axios, input))
      let data = this.GetData(await frmApi.kiemtra_hen_baohong_v2(this.axios, input))
      return data
    },
    async capnhat_giohen_v2() {
      //let input = { vbaohong_id: this.baohong_id, vghichu_ton: this.txtGhiChu, vhensuaden: this.dtpGioHen_Den, vhensuatu: this.dtpGioHen_Tu, vphieu_id: this.phieu_id, vsodt_kh: 'string', vten_kh: 'string' }
      let input = { vbaohong_id: this.baohong_id, vgiohen_den: this.dtpGioHen_Den, vgiohen_tu: this.dtpGioHen_Tu, vlydohen_id: this.cboLyDoHen, vnoidung: this.txtGhiChu, vphieu_id: this.phieu_id }
      let data = this.GetData(await frmApi.capnhat_giohen_v2(this.axios, input))
      return data
    },
    async capnhat_giohen_ttkd() {
      let input = { vbaohong_id: this.baohong_id, vgiohen_den: this.dtpGioHen_Den, vgiohen_tu: this.dtpGioHen_Tu, vlydohen_id: this.cboLyDoHen, vnoidung: this.txtGhiChu, vphieu_id: this.phieu_id }
      let data = this.GetData(await frmApi.capnhat_giohen_ttkd(this.axios, input))
      return data
    },
    async HienThiDanhSach() {
      try {
        if (this.baohong_id != 0) {
          // BaoHong_Facade Baohong = new BaoHong_Facade();
          // DataSet ds = Baohong.Lay_lichsu_hen_baohong(baohong_id);
          let ds = await this.lay_lichsu_hen_baohong()
          if (ds != null && ds.length > 0) {
            this.gridViewLichSuHen.data = ds
            //gridViewLichSuHen.BestFitColumns();
          } else {
            this.gridViewLichSuHen.data = []
          }
        } else this.gridViewLichSuHen.data = []
      } catch (ex) {
        this.ShowError(`${ex.message}`)
      }
    },
    async lay_lichsu_hen_baohong() {
      let input = { p_baohong_id: this.baohong_id } // 4516625 this.baohong_id
      let data = this.GetData(await frmApi.lay_lichsu_hen_baohong(this.axios, input))
      return data
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