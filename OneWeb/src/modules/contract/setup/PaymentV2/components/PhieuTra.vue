<template>
  <ejs-dialog
    :enableResize="true"
    :allowDragging="true"
    :visible="false"
    :animationSettings="animationSettings"
    ref="dialogObjPhieuTra"
    :position="position"
    :header="'Danh sách phiếu trả'"
    showCloseIcon="true"
    width="90%"
    :target="target"
    :close="phieuTraModalDlgClose"
    :open="phieuTraModalDlgOpen"
    :isModal="true"
    height="800px"
  >
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="tsbtnTimKiem_Click"> <span class="icon one-search"></span>Tìm kiếm </a>
          </li>
          <li>
            <a href="#" v-bind:class="{ disabled: !btnXoaPhieu }" @click.prevent="tsbtnXoaPhieu_Click"> <span class="icon one-trash"></span>Xoá phiếu </a>
          </li>
          <li>
            <a href="#" v-bind:class="{ disabled: !btnInHD }" @click.prevent="InHoaDon"> <span class="icon one-print"></span>In HĐ </a>
          </li>
          <li>
            <a href="#" v-bind:class="{ disabled: !btnPhieuThu }" @click.prevent="PhieuThu"> <span class="icon one-file-arrow-left"></span>Phiếu thu </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tìm kiếm</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Mã giao dịch</div>
                <div class="value">
                  <input type="text" v-on:keyup.enter="Search" v-model="txtMaGd" class="form-control highlight" />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">
                  <div class="check-action">
                    <input type="checkbox" class="check" v-model="chkCheckDate" />
                    <span class="name">Từ ngày</span>
                  </div>
                </div>
                <div class="value">
                  <date-picker :disabled="!chkCheckDate" :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="txtTuNgay" :type="typeFormat" :disabled-date="disabledRange">
                    <template #icon-calendar>
                      <span class="icon one-calendar"></span>
                    </template>
                  </date-picker>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">Đến ngày</div>
                <div class="value">
                  <date-picker :disabled="!chkCheckDate" :format="dateFormat" :class="{ 'is-invalid': $v.txtDenNgay.$error }" :value-type="dateFormat" :time-title-format="dateFormat" v-model="txtDenNgay" :type="typeFormat" :disabled-date="disabledRange">
                    <template #icon-calendar>
                      <span class="icon one-calendar"></span>
                    </template>
                  </date-picker>
                </div>
              </div>
            </div>
          </div>
          <div class="legend-title mart20">
            <div class="pull-left">Danh sách phiếu TT</div>
            <div class="pull-right red normal">Tổng số: {{ TongSoPhieu }} phiếu</div>
            <div class="clearfix"></div>
          </div>
          <DataGrid ref="grdDsPhieuTra" v-bind:columns="DsPhieuTra.headers" v-bind:dataSource="DsPhieuTra.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="true" @rowClicked="selectPhieuTT"> </DataGrid>
        </div>
      </div>
      <inHopDong ref="popupInHopDong" :params="modelIn" />
      <inPhieuThu ref="popupInPhieuThu" :ma_gd="maGD" />
      <PrintReport ref="printReport1" :data="dataReport" :type="7" />
      <selectPrintType ref="refselectPrintType" @selectprintType="selectprintType" />
    </div>
  </ejs-dialog>
</template>
<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import inHopDong from '@/modules/print/InHopDong/InHopDong.vue'
import inPhieuThu from '@/modules/print/InPhieuThu/InPhieuThu.vue'
import api from './ApiPhieuTon'
import PrintReport from '@/modules/print/InPhieuChi/PrintReport.vue'
import SelectPrintType from './SelectPrintType.vue'
import { LoaiHopDong, TRANGTHAI_DONGBO } from '@/const/enums'
export default {
  name: 'PhieuTra',
  components: {
    DatePicker,
    moment,
    api,
    inHopDong,
    inPhieuThu,
    PrintReport,
    SelectPrintType
  },
  props: ['cleardata'],
  data() {
    return {
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'date',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      dataReport: [],
      targetTop: '#app', // .main-wrapper-payment
      target: '#app .main-wrapper', // .main-wrapper-payment
      animationSettings: {},
      position: { X: 'center', Y: 'top' },
      modelIn: {
        hdkh_id: 0,
        ma_gd: '',
        ngaylap_hd: '',
        ngay_yc: ''
      },
      maGD: '',
      PhieuTtId: '',
      DsPhieuTra: {
        headers: [
          {
            fieldName: 'ngay_tt',
            headerText: 'Ngày TT',
            allowFiltering: true,
            allowSorting: false,
            width: 160
          },
          {
            fieldName: 'ma_gd',
            headerText: 'Mã GD',
            allowFiltering: true,
            allowSorting: false,
            width: 180
          },
          {
            fieldName: 'ten_loaihd',
            headerText: 'Loại HĐ',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          },
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_kh',
            headerText: 'Tên khách hàng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'diachi_kh',
            headerText: 'Địa chỉ KH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'soseri',
            headerText: 'Số seri',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ht_tra',
            headerText: 'Hình thức TT',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          },
          {
            fieldName: 'tongtien',
            headerText: 'Tổng tiền',
            allowFiltering: true,
            allowSorting: false,
            type: 'Number',
            format: 'N0',
            width: 120
          }
        ],
        selectItem: {},
        data: []
      },
      txtMaGd: '',
      tt_nd: {},
      txtTuNgay: moment(new Date()).format('DD/MM/YYYY'),
      txtDenNgay: moment(new Date()).format('DD/MM/YYYY'),
      chkCheckDate: false,
      tt: {},
      Loading: false
    }
  },
  computed: {
    TongSoPhieu: function() {
      if (this.DsPhieuTra.data != null) {
        return this.DsPhieuTra.data.length
      }
      return 0
    },
    btnXoaPhieu: function() {
      if (this.DsPhieuTra.data != null && this.DsPhieuTra.data.length > 0) {
        return true
      }
      return false
    },
    btnInHD: function() {
      if (this.DsPhieuTra.data != null && this.DsPhieuTra.data.length > 0) {
        return true
      }
      return false
    },
    btnPhieuThu: function() {
      if (this.DsPhieuTra.data != null && this.DsPhieuTra.data.length > 0) {
        return true
      }
      return false
    }
  },
  watch: {
    Loading: function(val, oldval) {
      this.$root.showLoading(val)
    }
  },
  mounted() {
    try {
      this.Loading = true
      /*
      Promise.all([
        this.getDsPhieuTT()
      ]).then((values) => {
        this.Loading = false
      })
        .catch(error => {
          this.Loading = false
          console.log(`Error in promises ${error}`)
        })
      */
      this.Loading = false
    } catch (ex) {
      this.Loading = false
    }
  },
  validations: {
    txtDenNgay: {
      maxValue: new Date()
    }
  },
  methods: {
    show: function() {
      this.$refs.dialogObjPhieuTra.show()
    },
    disabledRange: function(date) {
      return date > new Date()
    },
    getDsPhieuTT: async function() {
      try {
        let donviId = this.$root.token.getDonViID()
        let nhanvienId = this.$root.token.getNhanVienID()
        this.DsPhieuTra.data = []
        // var input = {
        //   vdenngay: this.chkCheckDate && this.txtDenNgay.length > 0 ? this.txtDenNgay : moment(new Date()).format('DD/MM/YYYY'), //""
        //   vma_gd: this.txtMaGd,
        //   vnhanvien_id: nhanvienId,
        //   vtungay: this.chkCheckDate && this.txtTuNgay.length > 0 ? this.txtTuNgay : '01/01/2020' //""
        // }
        var input = {
          vdenngay: this.chkCheckDate && this.txtDenNgay.length > 0 ? this.txtDenNgay : '0',
          vma_gd: this.txtMaGd,
          vnhanvien_id: nhanvienId,
          vtungay: this.chkCheckDate && this.txtTuNgay.length > 0 ? this.txtTuNgay : '0'
        }
        var data = this.GetData(await api.getDsPhieuTT(this.axios, input))
        this.DsPhieuTra.data = data
        if (data == null || data.length == 0) {
          this.ShowAlert(`Không tìm thấy kết quả theo yêu cầu !`)
        } else {
          this.selectPhieuTT(0, data[0])
        }
        return true
      } catch (err) {
        console.log('getDsPhieuTT ~ err:', err)
        return false
      }
    },
    tsbtnTimKiem_Click: async function() {
      try {
        this.Loading = true
        await this.DanhSach()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    selectPhieuTT: function(i, item) {
      console.log('selectPhieuTT', item, item.phieutt_id, i)
      this.PhieuTtId = item.phieutt_id
      this.DsPhieuTra.selectItem = item
      this.maGD = item.ma_gd
      this.txtMaGd = item.ma_gd
    },
    async DanhSach() {
      await this.getDsPhieuTT()
    },
    tsbtnXoaPhieu_Click: async function() {
      let self = this
      await self.thongtin_nguoidung()
      await self.thongtin()
      let item = this.DsPhieuTra.selectItem
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let username = this.$root.token.getUserName()
      let phieutt_id = item.phieutt_id
      var input = {
        phieutt_id: parseInt(item.phieutt_id),
        nguoidung_id: parseInt(nhanvienId),
        may_cn: self.tt.mayCn,
        nguoi_cn: username
      }
      this.Loading = true

      const ktra = await self.kiemtra_xoa_phieutt_v2(phieutt_id)
      this.Loading = false
      if (ktra.toLowerCase() != 'ok') {
        self.ShowError(ktra)
        return
      } else {
        try {
          this.$bvModal
            .msgBoxConfirm(`Bạn thật sự muốn xóa phiếu không ? Mã giao dịch: ${item.ma_gd}, Số máy/Account:${item.ma_tb}, Ngày thanh toán: ${item.ngay_tt}, Tổng tiền:${item.tongtien}`, {
              title: 'Xóa phiếu',
              size: 'sm',
              okTitle: 'Xóa phiếu',
              cancelTitle: 'Hủy'
            })
            .then(async (v) => {
              if (!v) {
                self.ShowAlert(`Bạn đã hủy xóa phiếu `)
                return
              } else {
                console.log('LAY_TTTB_THEO_PHIEUTT_ID:', phieutt_id)
                let loaihd_id = 0
                let dshdtb_id = ''
                let ds = await self.LAY_TTTB_THEO_PHIEUTT_ID(phieutt_id)
                if (ds != null || ds.length > 0) {
                  loaihd_id = ds[0]['LOAIHD_ID']
                  dshdtb_id = ds[0]['DSHDTB_ID']
                  if (loaihd_id == LoaiHopDong.DAT_MOI) {
                    let phieutt_id_neo = item['phieutt_id_neo']
                    let ma_gd_neo = item['ma_gd']
                    let _isNumber = self.CheckIsNumber(phieutt_id_neo)
                    if (phieutt_id_neo != null && phieutt_id_neo != '' && _isNumber) {
                      let kq = await self.Delete_ThanhToan_TBLD_VinaPhone(phieutt_id_neo, ma_gd_neo)
                      if (kq.toUpperCase() == 'OK') {
                        let arr = dshdtb_id.split(',')
                        for (const tbid of arr) {
                          await self.CAPNHAT_STATUS_HDTB(tbid, TRANGTHAI_DONGBO.DONGBO_LHD)
                        }
                        self.ShowSuccess('Xoá thành công phiếu thanh toán của hợp đồng có mã giao dịch: ' + item.ma_gd + ' trên VinaPhone !')
                      } else {
                        self.ShowError(kq)
                        return
                      }
                    }
                  } else if (loaihd_id == LoaiHopDong.CHUYEN_QUYEN || loaihd_id == LoaiHopDong.TACHNHAP_TB) {
                    console.log('Xóa HĐ Chuyển quyền:', item)
                    let phieutt_id_neo = item['phieutt_id_neo']
                    let ma_gd_neo = item['ma_gd']
                    let _isNumber = self.CheckIsNumber(phieutt_id_neo)
                    if (phieutt_id_neo != null && phieutt_id_neo != '' && _isNumber) {
                      let arrNumber = [1, 2, 3]
                      for (const i in arrNumber) {
                        let kq = await self.Ts_laphd_chuyenquyen_sudung_xoaphieu(ma_gd_neo, phieutt_id_neo)
                        if ((kq == 'OK' || kq == ma_gd_neo) && _isNumber) {
                          self.ShowSuccess('Xoá thành công phiếu thanh toán của hợp đồng có mã giao dịch:' + ma_gd_neo + ' trên VinaPhone !')
                          break
                        }
                        if (i == 2) {
                          self.ShowError('Có lỗi xảy ra trong quá trình xóa phiếu thanh toán của Hợp đồng có mã giao dịch: ' + item.ma_gd + ' trên VinaPhone !. Lỗi: ' + kq)
                          return
                        }
                      }
                    }
                  }
                }

                this.Loading = true
                var response = await api.xoaPhieuTT(this.axios, input)
                this.Loading = false
                if (response.data.error === 200 || response.data.error === '200') {
                  if (response.data.error_code == 'BSS-00000000') {
                    let data = response.data.data
                    if (data.vkq == 'ok') {
                      self.ShowSuccess(`Xoá thành công phiếu thanh toán của hợp đồng có mã giao dịch:${item.ma_gd}`)
                      self.txtMaGd = ''
                      await self.DanhSach()
                    } else {
                      this.ShowError(`${data.vkq}`)
                    }
                  } else {
                    self.ShowError(`Có lỗi xảy ra trong quá trình xóa phiếu thanh toán của Hợp đồng có mã giao dịch:${item.ma_gd}:Lỗi:${response.data.message_detail}`)
                  }
                } else {
                  self.ShowAlert(`Có lỗi xảy ra trong quá trình xóa phiếu thanh toán của Hợp đồng có mã giao dịch:${item.ma_gd}`)
                }
                this.Loading = false
              }
            })
        } catch (ex) {
          this.Loading = false
          self.ShowAlert(`Có lỗi xảy ra trong quá trình xóa phiếu thanh toán của Hợp đồng có mã giao dịch:${item.ma_gd}`)
        }
      }
    },
    async get_hoadon_vt01(phieutt_id) {
      var input = {
        vphieu_id: phieutt_id
      }
      let response = await api.get_hoadon_vt01(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        let data = response.data.data
        return data
      } else {
        return 0
      }
    },
    InHoaDon: async function() {
      try {
        if (this.PhieuTtId > 0) {
          this.Loading = true
          let data = await this.get_hoadon_vt01(this.PhieuTtId)
          this.dataReport = data.map((x) => ({ dong: x.DONG, noidung: x.NOIDUNG }))
          this.$refs.printReport1.data = data.map((x) => ({ dong: x.DONG, noidung: x.NOIDUNG }))
          console.log('InHoaDon-dataReport:', this.dataReport)
          this.$refs.printReport1.startPrint(this.dataReport)
        } else {
          this.ShowError(`Bạn chưa chọn phiếu để in HĐ !`)
        }
      } catch (err) {
        console.log('tsbtnInHoaDon_Click', err)
        this.ShowError(err.message)
      } finally {
        this.Loading = false
      }
    },
    InHD_: function() {
      try {
        this.modelIn.hdkh_id = this.HdKhId
        this.modelIn.ma_gd = this.txtMaGDTT
        this.modelIn.ngaylap_hd = this.txtNgayHD
        this.modelIn.ngay_yc = this.txtNgayYeuCau
        console.log('modelIn', this.modelIn)
        this.$refs.popupInHopDong.showModal()
      } catch (err) {
        console.log('tsbtnInHoaDon_Click', err)
      } finally {
      }
    },
    async PhieuThu() {
      try {
        if (this.maGD == null || this.maGD.length <= 3) {
          this.ShowError(`Chưa có mã giao dịch !`)
          return
        }
        let phieutt_id = 0
        if (this.DsPhieuTra.selectItem != null) {
          phieutt_id = this.DsPhieuTra.selectItem.phieutt_id
        }
        this.$refs.refselectPrintType.openDialog(phieutt_id)
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    PhieuThu_Old: function() {
      try {
        if (this.maGD == null || this.maGD.length <= 3) {
          this.ShowError(`Chưa có mã giao dịch !`)
          return
        }
        this.$refs.popupInPhieuThu.showModal()
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    InPhieuThu() {
      try {
        if (this.maGD == null || this.maGD.length <= 3) {
          this.ShowError(`Chưa có mã giao dịch !`)
          return
        }
        this.$refs.popupInPhieuThu.showModal()
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    phieuTraModalDlgClose: function() {
      this.chkCheckDate = false
      this.$emit('close', this.DsPhieuTra.selectItem)
    },
    openDialog() {
      this.DsPhieuTra.data = []
      this.chkCheckDate = false
      this.txtMaGd = ''
      this.txtTuNgay = moment(new Date()).format('DD/MM/YYYY')
      this.txtDenNgay = moment(new Date()).format('DD/MM/YYYY')
      this.Loading = true
      Promise.all([this.getDsPhieuTT()])
        .then((values) => {
          console.log('this.getDsPhieuTT():', values)
          // this.Loading = false
        })
        .catch((error) => {
          this.Loading = false
          console.log(`Error in promises ${error}`)
        })
        .finally(() => {
          this.Loading = false
          this.$refs.dialogObjPhieuTra.show()
        })
    },
    async selectprintType(type) {
      if (type == 2) {
        await this.InPhieuThu()
      }
    },
    phieuTraModalDlgOpen: function() {
      // this.DsPhieuTra.data = []
      // this.chkCheckDate = false
      // this.txtMaGd = ''
      // this.txtTuNgay = moment(new Date()).format('DD/MM/YYYY')
      // this.txtDenNgay = moment(new Date()).format('DD/MM/YYYY')
      // this.$emit('open')
      // this.Loading = true
      // Promise.all([this.getDsPhieuTT()])
      //   .then((values) => {
      //     this.Loading = false
      //   })
      //   .catch((error) => {
      //     this.Loading = false
      //     console.log(`Error in promises ${error}`)
      //   })
    },
    check_ccbs() {
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      if (ma_nhanvien_ccbs == null || ma_nhanvien_ccbs == '') {
        this.ShowError(`Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.`)
        return false
      }
      return true
    },
    async thongtin_nguoidung() {
      try {
        if (this.tt_nd != null && this.tt_nd.nhanvien_id != null && this.tt_nd.nhanvien_id > 0) {
          return this.tt_nd
        }
        const res = await api.thongtin_nguoidung(this.axios, {})
        let userinfo = res.data.data ? res.data.data : {}
        console.log('🚀thongtin_nguoidung ~ a', userinfo)
        this.tt_nd = userinfo
        return userinfo
      } catch (err) {
        console.log('🚀 Thongtin_nguoidung ~ err', err)
        this.tt_nd = {}
        return {}
      }
    },
    async Delete_ThanhToan_TBLD_VinaPhone(so_phieu, ma_gd) {
      try {
        if (this.check_ccbs()) {
          const tt_nd = this.tt_nd
          let tentat_ccbs = tt_nd.tentat_ccbs
          let user_neo = tt_nd.user_neo
          let response = await api.ts_thuphi_hoamangmoi_thuphi_xoaphieu(this.axios, {
            ma_tinh: tentat_ccbs,
            phieu_id: so_phieu,
            ma_hd: ma_gd,
            userid: user_neo,
            kieu_huy: 0
          })
          if (response.data.error == 200 || response.data.error == '200') {
            if (response.data.error_code == 'BSS-00000000') {
              if (response.data.data == ma_gd || response.data.data.result == ma_gd) return 'OK'
            }
            return response.data.data.result
          }
          return response.data.data.result
        }
      } catch (err) {
        console.log(' Delete_ThanhToan_TBLD_VinaPhone ~ err:', err)
      }
      return ''
    },
    async Ts_laphd_chuyenquyen_sudung_xoaphieu(ma_gd_neo, phieutt_id_neo) {
      try {
        if (this.check_ccbs()) {
          const tt_nd = this.tt_nd
          let tentat_ccbs = tt_nd.tentat_ccbs
          let user_neo = tt_nd.user_neo
          let input = { ma_tinh: tentat_ccbs, userid: user_neo, ma_hd: ma_gd_neo, phieu_id: phieutt_id_neo, kieu_xoa: 0, ip: this.tt.mayCn }
          var response = await api.ts_laphd_chuyenquyen_sudung_xoaphieu(this.axios, input)
          return response.data.data.result
        }
      } catch (err) {
        console.log('Ts_laphd_chuyenquyen_sudung_xoaphieu ~ err:', err)
        return err
      }
    },
    async kiemtra_xoa_phieutt_v2(phieutt_id) {
      try {
        let input = { vphieutt_id: phieutt_id }
        var response = await api.kiemtra_xoa_phieutt_v2(this.axios, input)
        return response.data.data
      } catch (err) {
        return err
      }
    },
    async LAY_TTTB_THEO_PHIEUTT_ID(phieutt_id) {
      try {
        let input = { vphieutt_id: phieutt_id }
        var response = await api.lay_tttb_theo_phieutt_id(this.axios, input)
        if (response.data.error == 200 || response.data.error == '200') {
          return response.data.data
        }
        return []
      } catch (err) {
        return []
      }
    },
    async CAPNHAT_STATUS_HDTB(hdtb_id, status) {
      try {
        let input = { hdtbId: hdtb_id, status: status }
        var response = await api.capnhat_status_hopdong_thuebao(this.axios, input)
        if (response.data.error == 200 || response.data.error == '200') {
          return true
        }
        return false
      } catch (err) {
        console.log('CAPNHAT_STATUS_HDTB ~ err:', err)
        return false
      }
    },
    async thongtin() {
      try {
        if (this.tt != null && this.tt.ipCn != null && this.tt.ipCn != '127.0.0.1') {
          return this.tt
        }
        const res = await api.thongtin(this.axios, {})
        let tt = res.data.data ? res.data.data : {}
        console.log('thongtin ~ a', tt)
        this.tt = tt
        return tt
      } catch (err) {
        console.log('🚀 thongtin ~ err', err)
        this.tt = { ipCn: '127.0.0.1', mayCn: 'web' }
        return this.tt
      }
    },
    CheckIsNumber(input) {
      try {
        if (isNaN(input)) {
          return false
        } else {
          return true
        }
      } catch (err) {
        return false
      }
      return false
    },
    async Huy_TtPay(phieutt_id) {},
    ShowAlert: function(message) {
      this.$toast.error(message)
    },
    ShowError: function(message) {
      this.$toast.error(message)
    },
    ShowSuccess: function(message) {
      this.$toast.success(message)
    }
  }
}
</script>
