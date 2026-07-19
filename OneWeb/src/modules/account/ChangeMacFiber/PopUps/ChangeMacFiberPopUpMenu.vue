<template>
  <b-modal id="popupTCTTTB" hide-footer hide-header hide-header-close body-class="modal-body p-0">
    <div class="modal-dialog" style="width: 100%; max-width: 850px">
      <div class="modal-content popup-box" style="background: #fff">
        <div class="popup-header">
          <div class="title">Thay đổi địa chỉ MAC FIBER</div>
          <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popupTCTTTB')"></div>
        </div>
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="#" @click.prevent="NhapMoi">
                <span class="icon files_single-paragraph nc-icon-glyph"></span>
                Nhập mới
              </a>
            </li>
            <li>
              <a href="javascript:void(0);" @click.prevent="ThayDoi"> <span class="icon one-sync"></span>Thay đổi </a>
            </li>
            <li>
              <a href="javascript:void(0);" data-dismiss="modal" @click.prevent="$bvModal.hide('popupTCTTTB')"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát</a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <div class="legend-title mart0">
              <div class="title pull-left">Thông tin thuê bao</div>
              <div class="pull-right">
                <div class="list-checks">
                  <div class="item">
                    <div class="check-action">
                      <input type="radio" checked class="check" value="0" v-model="LoaiId" />
                      <span class="name">Danh bạ</span>
                    </div>
                  </div>
                  <div class="item">
                    <div class="check-action">
                      <input type="radio" class="check" value="1" v-model="LoaiId" />
                      <span class="name">Hợp đồng</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="clearfix"></div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key">Account <span style="color: #dc3545"> (*)</span></div>
                  <div class="value">
                    <input type="text" class="form-control" ref="txtMaTb" v-on:keyup.enter="Search" v-bind:readonly="ReadOnly" v-model.trim="MaTb" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key">Loại hình</div>
                  <div class="value">
                    <Select2 :disabled="disableLoaiHinh" v-model="LoaiHinhId" :options="DmDvVienThong" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key">Địa chỉ Mac cũ</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="Mac" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key">Địa chỉ Mac mới</div>
                  <div class="value">
                    <input type="text" class="form-control" ref="txtMacNew" v-model.trim="MacNew" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key">Tên thuê bao</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="TenThueBao" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key">Mã giao dịch</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="MaGiaoDich" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="box-form no-boder" style="border: none">
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key">Ghi chú</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="GhiChu" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import moment from 'moment'
import api from '../Api'
export default {
  components: {
    api,
    moment
  },
  name: 'ChangeMacFiberPopUp',
  props: ['hdtb_id', 'loaitb_id', 'ma_tb', 'ma_gd'],
  created() {
    this.Loading = true
    try {
      Promise.all([this.getDmDvVT()]).finally((x) => {
        this.Loading = false
      })
    } catch (ex) {
      this.Loading = false
    }

    this.Loading = false
    this.HdThueBaoId = this.hdtb_id
    this.MaTb = this.ma_tb
    this.LoaiHinhId = this.loaitb_id
    this.MaGiaoDich = this.ma_gd
  },
  watch: {
    LoaiId: function (val) {
      this.NhapMoi()
    },
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  data() {
    return {
      header: {
        title: 'Tra cứu',
        list: [
          { name: ' Thay đổi địa chỉ MAC FIBER', link: { name: 'Ui.cards' } },
          {
            name: 'Thay đổi địa chỉ MAC FIBER',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      Loading: false,
      ReadOnly: false,
      disableLoaiHinh: true,
      dateFormat: 'DD/MM/YYYY',
      typeFormat: 'date',
      LoaiId: 0,
      MaTb: '',
      LoaiHinhId: '',
      Mac: '',
      MacNew: '',
      TenThueBao: '',
      MaGiaoDich: '',
      GhiChu: '',
      ThueBaoId: 0,
      HdThueBaoId: 0,
      DmDvVienThong: [],
      DsHdThueBao: [],
      LoaiHinhTB: {
        INTERNET_FTTH: 58
      },
      TrangThaiHD: {
        HOAN_THANH: 6,
        THOAITRA: 7
      },
      TRANGTHAI_DONGBO: {
        DONGBO_CM: 5
      },
      ADSLData: {
        TDTT_DB_ID: '',
        THUEBAO_ID: '',
        MA_TB: '',
        TRANGTHAI_ID: '',
        HDTB_ID: '',
        NHANVIEN_ID: '',
        DONVI_ID: '',
        NGAY_DK: '',
        NGAY_HT: '',
        MAY_CN: '',
        NGAY_CN: '',
        NGUOI_CN: '',
        MAC_ADDR_CU: '',
        MAC_ADDR: '',
        GHICHU: ''
      }
    }
  },
  methods: {
    openDialog(hdtbid, matb, loaitbid, magd) {
      this.Loading = true
      try {
        Promise.all([this.getDmDvVT()])
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
      this.Loading = false
      this.ReadOnly = false
      this.HdThueBaoId = hdtbid == null ? 0 : hdtbid
      this.MaTb = matb == null ? '' : matb
      this.LoaiHinhId = loaitbid == null ? 0 : loaitbid
      this.MaGiaoDich = magd == null ? '' : magd
      this.$nextTick(() => {
        if (this.MaTb.length > 0) {
          this.Search()
        }
      })
      //this.$refs.dialogObjChangeMacFiber.show()
    },
    closeDialog: function () {
      this.$emit('close', true)
    },
    getDmDvVT: async function () {
      var input = { dichvuvt_id: 4 }
      let data = this.GetData(await api.getDmDvVT(this.axios, input))
      this.DmDvVienThong = data.map((x) => ({
        id: x.loaitb_id,
        text: x.loaihinh_tb == null ? '' : x.loaihinh_tb
      }))
    },
    getDsHdThueBaoThayDoiMac: async function (matb) {
      var input = { ma_tb: matb }
      return this.GetData(await api.getDsHdThueBaoThayDoiMac(this.axios, input))
    },
    getDanhBaTheoMaTb: async function (matb) {
      var input = {
        in_dichvuvt_id: 4,
        in_donvi_dl_id: 0,
        in_ma_tb: matb
      }
      return this.GetData(await api.getDanhBaTheoMaTb(this.axios, input))
    },
    getDsThueBaoAdsl: async function (thuebaoid) {
      var input = { thuebao_id: thuebaoid }
      return this.GetData(await api.getDsThueBaoAdsl(this.axios, input))
    },
    getKeyTDTTDBID: async function () {
      var input = {
        keyname: 'TDTT_DB_ID',
        numblocksize: 1,
        numretry: 10
      }
      let response = await api.getKey(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        let data = response.data.data
        return data
      } else {
        return 0
      }
    },
    capNhatAdsl: async function () {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let userName = this.$root.token.getUserName()
      let phanvungId = this.$root.token.getPhanVungID()
      let ngayht = moment(Date.now()).format('DD/MM/YYYY')
      let arrAdsl = []
      /*
      let adsl = {
        TDTT_DB_ID: '',
        THUEBAO_ID: this.ThueBaoId,
        MA_TB: this.MaTb,
        TRANGTHAI_ID: this.LoaiId === 0 ? 1 : 2,
        HDTB_ID: this.HdThueBaoId,
        NHANVIEN_ID: nhanvienId,
        DONVI_ID: donviId,
        NGAY_DK: ngayht,
        NGAY_HT: ngayht,
        MAY_CN: 'web',
        NGAY_CN: ngayht,
        NGUOI_CN: userName,
        MAC_ADDR_CU: this.Mac,
        MAC_ADDR: this.MacNew,
        GHICHU: `Đổi địa chỉ MAC mới : ${this.MacNew}`,
        PHANVUNG_ID: phanvungId
      }
      */
      let adsl = {
        PHANVUNG_ID: phanvungId,
        THUEBAO_ID: this.ThueBaoId,
        MA_TB: this.MaTb,
        NHANVIEN_ID: nhanvienId,
        DONVI_ID: donviId,
        TRANGTHAI_ID: 5,
        NGAY_DK: ngayht,
        NGAY_HT: ngayht,
        GHICHU: `Đổi địa chỉ MAC mới : ${this.MacNew}`,
        NGAY_CN: ngayht,
        NGUOI_CN: userName,
        MAY_CN: 'web',
        PORT_ID: null,
        PORT_ID_CU: null,
        VCI_VPI_ID: null,
        VCI_VPI_ID_CU: null,
        BRAS_ID: null,
        BRAS_ID_CU: null,
        DSLAM_ID: null,
        DSLAM_ID_CU: null,
        MA_LT: null,
        MA_LT_CU: null,
        PASSWORD: null,
        PASSWORD_CU: null,
        IP: null,
        IP_CU: null,
        TUYENCAP: null,
        LIENTU: null,
        DOICAP_P: null,
        HOPCAP_P: null,
        TUCAP_P: null,
        LOAICAP_ID: null,
        CAP_GOC: null,
        CAP_P: null,
        DOICAP_GOC: null,
        TUCAP_GOC: null,
        TUYENCAP_CU: null,
        LIENTU_CU: null,
        DOICAP_P_CU: null,
        HOPCAP_P_CU: null,
        TUCAP_P_CU: null,
        LOAICAP_ID_CU: null,
        CAP_GOC_CU: null,
        CAP_P_CU: null,
        DOICAP_GOC_CU: null,
        TUCAP_GOC_CU: null,
        CULY: null,
        CULY_CU: null,
        TDTT_DB_ID: null,
        THONGTIN_TC: null,
        SLID_CU: null,
        SLID_MOI: null,
        SERI_MD_CU: null,
        SERI_MD_MOI: null,
        SLTV_HTVC_CU: null,
        SLTV_HTVC_MOI: null,
        SODT_CU: null,
        SODT_MOI: null,
        KETCUOI_ID: null,
        VITRI: null,
        VITRI_2: null,
        CAP_ID: null,
        DOICAP: null,
        DOICAP_2: null,
        KETCUOI_ID_CU: null,
        VITRI_CU: null,
        VITRI_2_CU: null,
        CAP_ID_CU: null,
        DOICAP_CU: null,
        DOICAP_2_CU: null,
        HDTB_ID: null,
        KIEU: 0,
        NGUON_YC: 0,
        NOTE: `Đổi địa chỉ MAC mới : ${this.MacNew}`,
        CHUQUAN_ID: null,
        CHUQUANLD_ID: null,
        CHUQUAN_ID_CU: null,
        CHUQUANLD_ID_CU: null,
        MAC_ADDR_CU: this.Mac,
        MAC_ADDR: this.MacNew
      }
      adsl.TDTT_DB_ID = await this.getKeyTDTTDBID()
      arrAdsl.push(adsl)
      var input = { vds: JSON.stringify(arrAdsl) }
      let response = null
      try {
        response = await api.capNhatAdsl(this.axios, input)
        if (response.data.error === 200 || response.data.error === '200') {
          // this.ShowSuccess(`Cập nhật thành công !`)
        } else {
          console.log('capNhatAdsl eRROR', response)
          // this.ShowError(response.data.error_code)
        }
        return response
      } catch (ex) {
        console.log('capNhatAdsl', ex)
        response = ex
        return response
      }
    },
    capNhatDbMac: async function () {
      var input = { mac_addr: this.MacNew, thuebao_id: this.ThueBaoId }
      let response = await api.capNhatMac(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        //this.ShowSuccess(`Cập nhật thành công !`)
      } else {
        console.log(response.data.error_code)
        this.ShowError(response.data.error_code)
      }
      return response
    },
    capNhatTbMac: async function () {
      var input = { mac_addr: this.MacNew, hdtb_id: this.HdThueBaoId }
      let response = await api.capNhatMacHDTB(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        //this.ShowSuccess(`Cập nhật thành công !`)
      } else {
        console.log(response.data.error_code)
        this.ShowError(response.data.error_code)
      }
      return response
    },
    changeMac: async function () {
      var input = {
        acc_name: this.MaTb,
        mac_addr: this.MacNew,
        note: this.GhiChu
      }
      return api.changeMac(this.axios, input)
    },
    changeMac2: async function () {
      var input = {
        accountName: this.MaTb, // 'vunamcuong81', // this.MaTb,
        macAddress: this.MacNew,
        note: this.GhiChu
      }
      return api.changeMac2(this.axios, input)
    },
    capNhat: async function () {
      var input = { thuebao_id: this.thuebao_id }
      try {
        let response = await api.capNhat(this.axios, input)
        if (response.data.error === 200 || response.data.error === '200') {
          this.ShowSuccess(`Cập nhật thành công !`)
        } else {
          console.log(response.data.error_code)
        }
        return response
      } catch (ex) {
        return ex
      }
    },
    ShowPopUp: function () {
      this.$bvModal.show('my-modal1')
    },
    NhapMoi: async function () {
      this.ReadOnly = false
      this.MaTb = ''
      this.LoaiHinhId = ''
      this.Mac = ''
      this.MacNew = ''
      this.TenThueBao = ''
      this.MaGiaoDich = ''
      this.GhiChu = ''
      this.thuebao_id = ''
    },
    Search: async function () {
      this.Loading = true
      try {
        if (this.LoaiId == '0') {
          let data = await this.getDanhBaTheoMaTb(this.MaTb)
          if (data.length > 0) {
            this.ReadOnly = true
            let hd = data[0]
            this.ThueBaoId = hd.thuebao_id
            this.LoaiHinhId = hd.loaitb_id
            this.TenThueBao = hd.ten_tb
            let adsl = await this.getDsThueBaoAdsl(this.ThueBaoId)
            if (adsl.length > 0) {
              this.Mac = adsl[0].mac_ADDR
              this.MacNew = ''
            }
          } else {
            this.Loading = false
            this.ShowAlert(`Không tìm thấy thông tin thuê bao :${this.MaTb}`)
            this.NhapMoi()
            return
          }
        } else {
          let data = await this.getDsHdThueBaoThayDoiMac(this.MaTb)
          this.Loading = false
          if (data.length > 0) {
            if (data[0].tthd_id == this.TrangThaiHD.HOAN_THANH || data[0].tthd_id == this.TrangThaiHD.THOAITRA) {
              this.ShowAlert(`Bạn không được đổi địa chỉ Mac của thuê bao có trạng thái hợp đồng là : Thoái trả hoặc Đã hoàn thành !`)
              this.NhapMoi()
              return
            }
            if (data[0].status != this.TRANGTHAI_DONGBO.DONGBO_CM) {
              this.ShowAlert(`Bạn chỉ được đổi địa chỉ Mac của thuê bao khi đã kích hoạt lên hệ thống visas !`)
              this.NhapMoi()
              return
            }
            this.ReadOnly = true
            this.MaTb = data[0].ma_tb
            this.HdThueBaoId = data[0].hdtb_id
            this.TenThueBao = data[0].ten_tb
            this.LoaiHinhId = data[0].loaitb_id
            this.Mac = data[0].mac_addr
            this.MacNew = ''
          } else {
            this.ShowAlert(`Không tìm thấy thông tin thuê bao :${this.MaTb}`)
            this.NhapMoi()
            return
          }
        }
        this.Loading = false
      } catch (ex) {
        this.Loading = false
        this.ShowAlert(`Không tìm thấy thông tin thuê bao :${this.MaTb}`)
      }
    },
    ThayDoi: async function () {
      if (this.MaTb == '') {
        this.ShowAlert('Chưa nhập tài khoản/ thuê bao đổi MAC !')
        this.$refs.txtMaTb.focus()
        return
      }
      if (this.MacNew == '') {
        this.ShowAlert('Hãy nhập địa chỉ MAC mới !')
        this.$refs.txtMacNew.focus()
        return
      }
      if (this.MacNew == this.Mac) {
        this.ShowAlert('Địa chỉ MAC mới giống địa chỉ cũ !')
        this.$refs.txtMacNew.focus()
        return
      }

      if (this.LoaiHinhId != this.LoaiHinhTB.INTERNET_FTTH) {
        this.ShowAlert('Chỉ được phép đổi địa chỉ MAC với loại thuê bao Fiber')
        return
      }

      this.$bvModal
        .msgBoxConfirm('Bạn thật sự muốn cập nhật thông tin không ?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            this.Loading = false
            return
          }
          // try {
          // Xử lý code ở đây
          this.Loading = true
          let changeMacRes = await this.changeMac2()
          console.log('changeMacRes', changeMacRes)
          if (changeMacRes.data.errorCode == '1') {
            this.ShowError(changeMacRes.data.faultString)
            this.Loading = false
            return
          }
          if (changeMacRes.data.errorCode == '0') {
            let a = await this.capNhatAdsl()
            if (a.data.error === 200 || a.data.error === '200') {
              if (this.LoaiId == '0') {
                let response = await this.capNhatDbMac()
                if (response.data.error === 200 || response.data.error === '200') {
                  this.ShowSuccess(`Cập nhật dữ liệu thành công`)
                  this.Loading = false
                } else {
                  console.log(response.data.error_code)
                  this.ShowError(`Có lỗi trong quá trình đổi địa chỉ MAC cho thuê bao : ${this.MaTb}`)
                  this.Loading = false
                }
              } else {
                let response = await this.capNhatTbMac()
                if (response.data.error === 200 || response.data.error === '200') {
                  this.ShowSuccess(`Cập nhật dữ liệu thành công`)
                  this.Loading = false
                } else {
                  console.log(response.data.error_code)
                  this.ShowError(`Có lỗi trong quá trình đổi địa chỉ MAC cho thuê bao : ${this.MaTb}`)
                  this.Loading = false
                }
              }
            } else {
              console.log('a', a)
              this.ShowError(a.data.message)
              this.Loading = false
            }
          } else {
            this.ShowError(changeMacRes.data.faultString)
            this.Loading = false
          }
          // } catch (err) {
          //   this.ShowError(err.message)
          //   console.log(err)
          //   this.Loading = false
          // }
        })
      // if (confirm('Bạn thật sự muốn cập nhật thông tin không ?')) {
      // }
    },
    TroGiup: function () {
      this.$bvModal.hide('my-modal1')
    },
    Thoat: function () {
      //this.$refs.dialogObjChangeMacFiber.hide()
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    },
    ShowAlert: function (msg) {
      this.$toast.error(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    }
  }
}
</script>
<style>
.mx-datepicker {
  width: 100% !important;
}
.mx-icon-calendar {
  font-style: normal !important;
}
.modal-dialog {
  min-width: 900px;
}
.no-boder {
  border: 0px solid transparent !important;
}
</style>
