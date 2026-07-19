<template src="./index.html">
</template>
<script>
import Vue from 'vue'
import Enum from '../../Enum'
import DateTimeLib from '@/utils/DateTimeLib'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ChiTietNganhNghe from '../../../ThayDoiThongTinKhuyenMai/ChiTietNganhNghe'
import ModalChonDiaChi from '../../../../../CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonDiaChi/Modal'
import LoaiKH from '../../../ThayDoiThongTinKhuyenMai/LoaiKH'
import TimNhanVienThuCuoc from '@/modules/QLTN/components/PopupTimKiemNhanVienThuCuoc/PopupTimKiemNhanVienThuCuoc.vue'
const VueInputMask = require('vue-inputmask').default
Vue.use(VueInputMask)
export default {
  name: 'ThongTinKhachHang',
  components: {
    DateTimeLib,
    BssRequiredMarker,
    ChiTietNganhNghe,
    ModalChonDiaChi,
    LoaiKH,
    TimNhanVienThuCuoc
  },
  props: ['parent'],
  data () {
    return {
      diachi: {},
      diachi_flag: null,
      showThongTinThem: false,
      inputObjFrmTraCuuNVTC: {},
      objLoaiKH: {}
    }
  },
  async mounted () {
  },
  methods: {
    btnTimMaNVTC_Click () {
      this.inputObjFrmTraCuuNVTC = {
        quan_Id: this.parent.quantt_id,
        phuong_Id: this.parent.phuongtt_id,
        pho_Id: this.parent.phott_id,
        ap_Id: this.parent.aptt_id,
        khu_Id: this.parent.khutt_id,
        dacDiem_Id: this.parent.dacdiemtt_id,
        nhanvientc_id: this.parent.nhanvientc_id,
        tuyenThu_Id: this.parent.tuyenthu_id,
        donViQL_Id: this.parent.modelCbo.cboDonViQL
      }
      if (this.$refs.dlgTimKiemNVTC) this.$refs.dlgTimKiemNVTC.openDialog()
    },
    async getDiaChiTuFormDiaChi (diachi) {
      let data = {...diachi}
      console.log('🚀 ~ file: index.vue ~ line 49 ~ getDiaChiTuFormDiaChi ~ data', data)
      try {
        switch (this.diachi_flag) {
          case 'DIACHI_NDD':
            this.diachi_flag = null
            this.$refs.txtDiaChiNDD.focus()
            this.parent.tinhndd_id = data.tinh_id
            this.parent.quanndd_id = data.quan_id
            this.parent.phuongndd_id = data.phuong_id
            this.parent.phondd_id = data.pho_id
            this.parent.dacdiemndd_id = data.dacdiem_id
            this.parent.apndd_id = data.ap_id
            this.parent.khundd_id = data.khu_id
            this.parent.sonhandd = data.sonha
            this.parent.modelInput.txtDiaChiNDD = data.diachimoi
            break
          case 'DIACHI_KH':
            this.diachi_flag = null
            this.$refs.txtDiaChiKH.focus()
            let obj = {...data}
            this.parent.tinhkh_id = obj.tinh_id
            this.parent.quankh_id = obj.quan_id
            this.parent.phuongkh_id = obj.phuong_id
            this.parent.phokh_id = obj.pho_id
            this.parent.apkh_id = obj.ap_id
            this.parent.khukh_id = obj.khu_id
            this.parent.dacdiemkh_id = obj.dacdiem_id
            this.parent.Lng_KH = obj.longtitude
            this.parent.Lat_KH = obj.lattitude
            this.parent.sonhakh = obj.sonha
            this.parent.modelInput.txtDiaChiKH = obj.diachimoi
            // Hiếu thêm địa chỉ NDD: ngày 30/07/2010
            this.parent.tinhndd_id = obj.tinh_id
            this.parent.quanndd_id = obj.quan_id
            this.parent.phuongndd_id = obj.phuong_id
            this.parent.phondd_id = obj.pho_id
            this.parent.apndd_id = obj.ap_id
            this.parent.khundd_id = obj.khu_id
            this.parent.dacdiemndd_id = obj.dacdiem_id
            this.parent.sonhandd = obj.sonha
            this.parent.modelInput.txtDiaChiNDD = obj.diachimoi
            break
          case 'DIACHI_BC':
            this.diachi_flag = null
            this.$refs.txtDiaChiBC.focus()

            this.parent.modelInput.txtMaNVTC = ''// Nếu chọn lại địa chỉ báo cước thì clear mã nhân viên thu cước - Hiếu 29.09.2010
            console.log('🚀 ~ file: index.vue:100 ~ getDiaChiTuFormDiaChi ~ this.parent.modelInput.txtMaNVTC:', this.parent.modelInput.txtMaNVTC)
            this.parent.modelInput.txtMaTuyen = ''

            // Địa chỉ báo cước
            this.parent.tinhbc_id = data.tinh_id
            this.parent.quanbc_id = data.quan_id
            this.parent.phuongbc_id = data.phuong_id ? data.phuong_id : 0
            this.parent.phobc_id = data.pho_id ? data.pho_id : 0
            this.parent.dacdiembc_id = data.dacdiem_id ? data.dacdiem_id : 0
            this.parent.apbc_id = data.ap_id ? data.ap_id : 0
            this.parent.khubc_id = data.khu_id ? data.khu_id : 0
            this.parent.sonhabc = data.sonha
            this.parent.modelInput.txtDiaChiBC = data.diachimoi
            await this.HT_NVTC_TheoDC(this.parent.quanbc_id, this.parent.phuongbc_id, this.parent.phobc_id, this.parent.apbc_id, this.parent.khubc_id,
              parseInt(this.parent.modelCbo.cboDonViQL), this.parent.dacdiembc_id)
            break
          case 'DIACHI_CT':
            this.diachi_flag = null
            this.$refs.txtDiaChiCT.focus()
            this.parent.tinhct_id = data.tinh_id
            this.parent.quanct_id = data.quan_id
            this.parent.phuongct_id = data.phuong_id
            this.parent.phoct_id = data.pho_id
            this.parent.dacdiemct_id = data.dacdiem_id
            this.parent.apct_id = data.ap_id
            this.parent.khuct_id = data.khu_id
            this.parent.sonhact = data.sonha
            this.parent.modelInput.txtDiaChiCT = data.diachimoi
            break
          case 'DIACHI_TT':
            this.diachi_flag = null
            this.$refs.txtDiaChiTT.focus()
            this.parent.tinhtt_id = data.tinh_id
            this.parent.quantt_id = data.quan_id
            this.parent.phuongtt_id = data.phuong_id
            this.parent.phott_id = data.pho_id
            this.parent.aptt_id = data.ap_id
            this.parent.khutt_id = data.khu_id
            this.parent.dacdiemtt_id = data.dacdiem_id
            this.parent.Lng_TT = data.longtitude
            this.parent.Lat_TT = data.lattitude
            this.parent.sonhatt = data.sonha
            this.parent.modelInput.txtDiaChiTT = data.diachimoi

            if (this.parent.modelInput.txtDiaChiCT === '') {
              // Lấy địa chỉ chứng từ theo địa chỉ thanh toán
              this.parent.tinhct_id = data.tinh_id
              this.parent.quanct_id = data.quan_id
              this.parent.phuongct_id = data.phuong_id
              this.parent.phoct_id = data.pho_id
              this.parent.apct_id = data.ap_id
              this.parent.khuct_id = data.khu_id
              this.parent.dacdiemct_id = data.dacdiem_id
              this.parent.sonhact = data.sonha
              this.parent.modelInput.txtDiaChiCT = data.diachimoi
            }
            if (this.parent.modelInput.txtDiaChiBC === '') {
              this.parent.modelInput.txtMaNVTC = ''// Nếu chọn lại địa chỉ  thanh toán thì clear mã nhân viên thu cước - Hiếu 29.09.2010
              console.log('🚀 ~ file: index.vue:158 ~ getDiaChiTuFormDiaChi ~ this.parent.modelInput.txtMaNVTC:', this.parent.modelInput.txtMaNVTC)
              this.parent.modelInput.txtMaTuyen = ''
              /// TuanNA: Lấy mã NVTC theo địa chỉ tt
              /// Ngày 07.12.2012
              await this.HT_NVTC_TheoDC(this.parent.quantt_id, this.parent.phuongtt_id, this.parent.phott_id, this.parent.aptt_id, this.parent.khutt_id,
                Number(this.parent.modelCbo.cboDonViQL), this.parent.dacdiemtt_id)

              if (this.parent.DONVIQL_HTTT === 1) {
                await this.HienThi_NVQL_AM_HTTT(Number(this.parent.modelCbo.cboLoaiKH), Enum.Loai_NV.NHANVIEN_KINHDOANH, this.parent.phuongtt_id, this.parent.phott_id, this.parent.aptt_id, this.parent.khutt_id, this.parent.nhanvientc_id, Number(this.parent.modelCbo.cboDonViQL), Number(this.parent.modelCbo.cboHTTT))
              } else {
                await this.HienThi_NVQL_AM(Number(this.parent.modelCbo.cboLoaiKH), Enum.Loai_NV.NHANVIEN_KINHDOANH, this.parent.phuongtt_id, this.parent.phott_id, this.parent.aptt_id, this.parent.khutt_id, this.parent.nhanvientc_id, Number(this.parent.modelCbo.cboDonViQL))
              }
            }
            break
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 53 ~ getDiaChiTuFormDiaChi ~ error', error)
      }
    },
    txtTenKH_KeyPress () {
      this.btnDiaChiKH_Click()
    },
    async txtSoGT_Leave () {
      await this.KiemTraSoGT()
    },
    txtNguoiDD_Leave () {
      this.parent.modelInput.txtNguoiDD = this.parent.ChuanHoaTen(this.parent.modelInput.txtNguoiDD)
    },
    txtChucDanhDD_Leave () {
      this.parent.modelInput.txtChucDanhDD = this.parent.ChuanHoaTen(this.parent.modelInput.txtChucDanhDD)
    },
    txtTenTT_Leave () {
      this.parent.modelInput.txtTenTT = this.parent.ChuanHoaTen(this.parent.modelInput.txtTenTT)
    },
    txtHoKhau_Leave () {
      this.parent.modelInput.txtHoKhau = this.parent.ChuanHoaTen(this.parent.modelInput.txtHoKhau)
    },
    txtNoiCapHK_Leave () {
      this.parent.modelInput.txtNoiCapHK = this.parent.ChuanHoaTen(this.parent.modelInput.txtNoiCapHK)
    },
    txtDiaChiCT_Leave () {
      this.parent.modelInput.txtDiaChiCT = this.parent.ChuanHoaTen(this.parent.modelInput.txtDiaChiCT

      )
    },
    async LAY_DS_MAKH_THEO_SOGT (vso_gt, vhdkh_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/sp_lay_ds_makh_theo_sogt', {vso_gt, vhdkh_id})
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 182 ~ LAY_DS_MAKH_THEO_SOGT ~ error', error)
        return []
      }
    },
    async KiemTraSoGT () {
      try {
        if (this.parent.modelInput.txtSoGT !== '') {
          let ds_maKH = await this.LAY_DS_MAKH_THEO_SOGT(this.parent.modelInput.txtSoGT, this.parent.hdkh_id)
          if (ds_maKH && ds_maKH.length === 1) {
            if (this.parent.modelInput.txtMaKH !== ds_maKH[0]['ma_kh']) {
              if (ds_maKH[0]['KQ'].toString() === '1') {
                this.$root.$toast.warning('Số giấy tờ ' + this.parent.modelInput.txtSoGT + ' đang được sử dụng bởi mã khách hàng: ' + ds_maKH[0]['ma_kh'])
                this.$refs.txtSoGT.focus()
                return
              } else {
                this.$root.$toast.warning('Số giấy tờ: ' + this.parent.modelInput.txtSoGT +
                                                    ' đang được sử dụng trong hợp đồng có mã khách hàng: ' +
                                                    ds_maKH[0]['ma_kh'] + '\nBạn phải sử dụng số giấy tờ khác !')
                this.$refs.txtSoGT.focus()
                return
              }
            }
          } else {
            if (ds_maKH.length > 1) {
              let str = ''
              for (let index = 0; index < ds_maKH.length; index++) {
                const e = ds_maKH[index]
                str = str + e.ma_kh + ','
              }
              this.$root.$toast.warning('Số giấy tờ ' + this.parent.modelInput.txtSoGT + ' đang được sử dụng bởi các mã khách hàng: ' +
                                    str + '.\nĐề nghị bạn ký lại hợp đồng gốc trước khi lập phụ lục.', 'Thông báo')
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 222 ~ KiemTraSoGT ~ error', error)
      }
    },
    txtTenKH_Leave () {
      this.parent.modelInput.txtTenKH = this.parent.ChuanHoaTen(this.parent.modelInput.txtTenKH)
      if (this.parent.modelInput.txtNguoiDD === '') { this.parent.modelInput.txtNguoiDD = this.parent.modelInput.txtTenKH }
      if (this.parent.modelInput.txtTenTT === '') { this.parent.modelInput.txtTenTT = this.parent.modelInput.txtTenKH }
    },
    async Lay_ds_nhanvien_ql_am_v2 (vloaikh_id, vloainv_id, vphuong_id, vpho_id, vap_id, vkhu_id, vnvtc_id, vdonviql_id, vhttt_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/lay_ds_nhanvien_ql_am_v2', {vloaikh_id, vloainv_id, vphuong_id, vpho_id, vap_id, vkhu_id, vnvtc_id, vdonviql_id, vhttt_id})
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 137 ~ Lay_ds_nhanvien_ql_am_v2 ~ error', error)
        return []
      }
    },
    async lay_ds_nhanvien_ql_am_1 (loaikh_id, loainv_id, phuong_id, pho_id, ap_id, khu_id, nvtc_id, donviql_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/tachnhapthuebao/lay_ds_nhanvien_ql_am_1', {loaikh_id, loainv_id, phuong_id, pho_id, ap_id, khu_id, nvtc_id, donviql_id})
        console.log('🚀 ~ file: index.vue ~ line 145 ~ lay_ds_nhanvien_ql_am_1 ~ rs', rs)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 147 ~ lay_ds_nhanvien_ql_am_1 ~ error', error)
        return []
      }
    },
    async HienThi_NVQL_AM (i_loaikh_id, i_loainv_id, i_phuong_id, i_pho_id, i_ap_id, i_khu_id, i_nvtc_id, vdonviql_id) {
      try {
        if (this.parent.kt_load === false) {
          var ds_nvql = await this.lay_ds_nhanvien_ql_am_1(i_loaikh_id, i_loainv_id, i_phuong_id || 0, i_pho_id || 0, i_ap_id || 0, i_khu_id || 0, i_nvtc_id, vdonviql_id)
          if (ds_nvql !== null && ds_nvql.length <= 0) {
            this.parent.modelInput.txtNhanVienQL = ''
            this.parent.nhanvien_ql_am_id = 0
          } else {
            if (ds_nvql !== null && ds_nvql.length > 0) {
              this.parent.modelInput.txtNhanVienQL = ds_nvql[0].ma_nv + ' - ' + ds_nvql[0].ten_nv
              this.parent.nhanvien_ql_am_id = ds_nvql[0].nhanvien_id
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 209 ~ HienThi_NVQL_AM ~ error', error)
      }
    },
    async HienThi_NVQL_AM_HTTT (i_loaikh_id, i_loainv_id, i_phuong_id, i_pho_id, i_ap_id, i_khu_id, i_nvtc_id, vdonviql_id, vhttt_id) {
      try {
        if (this.parent.kt_load === false) {
          var ds_nvql = await this.Lay_ds_nhanvien_ql_am_v2(i_loaikh_id, i_loainv_id, i_phuong_id, i_pho_id, i_ap_id, i_khu_id, i_nvtc_id, vdonviql_id, vhttt_id)
          if (ds_nvql !== null && ds_nvql.length <= 0) {
            this.parent.modelInput.txtNhanVienQL = ''
            this.parent.nhanvien_ql_am_id = 0
          } else {
            if (ds_nvql !== null && ds_nvql.length > 0) {
              this.parent.modelInput.txtNhanVienQL = ds_nvql[0].ma_nv + ' - ' + ds_nvql[0].ten_nv
              this.parent.nhanvien_ql_am_id = ds_nvql[0].nhanvien_id
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 137 ~ HienThi_NVQL_AM_HTTT ~ error', error)
      }
    },
    async  HT_NVTC_TheoDC (quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id) {
      try {
        let ds = []
        if (this.parent.TUYENTHU_HTTT === 1 && this.parent.modelCbo.cboHTTT) {
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/ht_nvtc_theodc_v2', {
            phuong_id: phuong_id || 0,
            pho_id: pho_id || 0,
            ap_id: ap_id || 0,
            khu_id: khu_id || 0,
            donvi_ql_id: donvi_ql_id,
            dacdiem_id: dacdiem_id || 0,
            httt_id: this.cboHTTT
          })
          ds = rs.data
        } else {
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/ht_nvtc_theodc', {
            phuong_id: phuong_id || 0,
            pho_id: pho_id || 0,
            ap_id: ap_id || 0,
            khu_id: khu_id || 0,
            donvi_ql_id: donvi_ql_id,
            dacdiem_id: dacdiem_id || 0
          })
          ds = rs.data
        }
        if (ds.length > 1) {
          this.inputObjFrmTraCuuNVTC = {
            quan_Id: quan_id,
            phuong_Id: phuong_id,
            pho_Id: pho_id,
            ap_Id: ap_id,
            khu_Id: khu_id,
            dacDiem_Id: dacdiem_id,
            nhanvientc_id: this.nhanvientc_id,
            tuyenThu_Id: ds[0].tuyenthu_id,
            donViQL_Id: donvi_ql_id
          }
          if (this.parent.TUYENTHU_HTTT === 1 && this.parent.modelCbo.cboHTTT) { this.inputObjFrmTraCuuNVTC.httt_Id = this.parent.modelCbo.cboHTTT }
          this.$refs.dlgTimKiemNVTC.openDialog()
        } else if (ds.length > 0) {
          this.parent.modelInput.txtMaNVTC = ds[0].manv_tc
          console.log('🚀 ~ file: index.vue:347 ~ HT_NVTC_TheoDC ~ this.parent.modelInput.txtMaNVTC:', this.parent.modelInput.txtMaNVTC)
          if (ds[0].tuyenthu_id) {
            this.parent.tuyenthu_id = ds[0].tuyenthu_id
          }
          this.parent.modelInput.txtMaTuyen = ds[0].ma_tuyen
          this.parent.nhanvientc_id = ds[0].nhanvien_id
        } else {
          this.parent.modelInput.txtMaNVTC = ''
          console.log('🚀 ~ file: index.vue:355 ~ HT_NVTC_TheoDC ~ this.parent.modelInput.txtMaNVTC:', this.parent.modelInput.txtMaNVTC)
          this.parent.modelInput.txtMaTuyen = ''
          this.parent.tuyenthu_id = 0
          this.parent.nhanvientc_id = 0
        }
      } catch (error) {

      }
    },
    btnDiaChiBC_Click () {
      this.showThongTinThem = false
      this.diachi = {}
      this.diachi_flag = 'DIACHI_BC'
      this.diachi.tinh_id = this.parent.tinhbc_id
      this.diachi.quan_id = this.parent.quanbc_id
      this.diachi.phuong_id = this.parent.phuongbc_id
      this.diachi.pho_id = this.parent.phobc_id
      this.diachi.dacdiem_id = this.parent.dacdiembc_id
      this.diachi.ap_id = this.parent.apbc_id
      this.diachi.khu_id = this.parent.khubc_id
      this.diachi.so_nha = this.parent.sonhabc
      this.diachi.diachi = this.parent.modelInput.txtDiaChiBC
      this.$bvModal.show('popupDiaChi')
    },
    btnDiaChiCT_Click () {
      this.showThongTinThem = false
      this.diachi = {}
      this.diachi_flag = 'DIACHI_CT'
      if (this.parent.quanct_id && this.parent.phuongct_id && this.parent.phoct_id && this.parent.modelInput.txtDiaChiCT) {
        this.diachi.tinh_id = this.parent.tinhct_id
        this.diachi.quan_id = this.parent.quanct_id
        this.diachi.phuong_id = this.parent.phuongct_id
        this.diachi.pho_id = this.parent.phoct_id
        this.diachi.dacdiem_id = this.parent.dacdiemct_id
        this.diachi.ap_id = this.parent.apct_id
        this.diachi.khu_id = this.parent.khuct_id
        this.diachi.so_nha = this.parent.sonhact
        this.diachi.diachi = this.parent.modelInput.txtDiaChiCT
      } else {
        this.diachi.tinh_id = this.parent.tinhkh_id
        this.diachi.quan_id = this.parent.quankh_id
        this.diachi.phuong_id = this.parent.phuongkh_id
        this.diachi.pho_id = this.parent.phokh_id
        this.diachi.dacdiem_id = this.parent.dacdiemkh_id
        this.diachi.ap_id = this.parent.apkh_id
        this.diachi.khu_id = this.parent.khukh_id
        this.diachi.so_nha = this.parent.sonhakh
        this.diachi.diachi = this.parent.modelInput.txtDiaChiKH
      }
      this.$bvModal.show('popupDiaChi')
    },
    btnDiaChiTT_Click () {
      this.showThongTinThem = false
      this.diachi = {}
      this.diachi_flag = 'DIACHI_TT'
      this.diachi.tinh_id = this.parent.tinhtt_id
      this.diachi.quan_id = this.parent.quantt_id
      this.diachi.phuong_id = this.parent.phuongtt_id
      this.diachi.pho_id = this.parent.phott_id
      this.diachi.ap_id = this.parent.aptt_id
      this.diachi.khu_id = this.parent.khutt_id
      this.diachi.dacdiem_id = this.parent.dacdiemtt_id
      this.diachi.so_nha = this.parent.sonhatt
      this.diachi.diachi = this.parent.modelInput.txtDiaChiTT
      this.diachi.longtitude = this.parent.Lng_TT
      this.diachi.lattitude = this.parent.Lat_TT
      this.$bvModal.show('popupDiaChi')
    },
    btnDiaChiNDD_Click () {
      this.showThongTinThem = false
      this.diachi = {}
      this.diachi_flag = 'DIACHI_NDD'
      if (this.parent.quanndd_id && this.parent.phuongndd_id && this.parent.phondd_id && this.parent.modelInput.txtDiaChiNDD) {
        this.diachi.tinh_id = this.parent.tinhndd_id
        this.diachi.quan_id = this.parent.quanndd_id
        this.diachi.phuong_id = this.parent.phuongndd_id
        this.diachi.pho_id = this.parent.phondd_id
        this.diachi.ap_id = this.parent.apndd_id
        this.diachi.khu_id = this.parent.khundd_id
        this.diachi.dacdiem_id = this.parent.dacdiemndd_id
        this.diachi.so_nha = this.parent.sonhandd
        this.diachi.diachi = this.parent.modelInput.txtDiaChiNDD
      } else {
        this.diachi.tinh_id = this.parent.tinhkh_id
        this.diachi.quan_id = this.parent.quankh_id
        this.diachi.phuong_id = this.parent.phuongkh_id
        this.diachi.pho_id = this.parent.phokh_id
        this.diachi.ap_id = this.parent.apkh_id
        this.diachi.khu_id = this.parent.khukh_id
        this.diachi.dacdiem_id = this.parent.dacdiemkh_id
        this.diachi.so_nha = this.parent.sonhakh
        this.diachi.diachi = this.parent.modelInput.txtDiaChiKH
      }
      this.$bvModal.show('popupDiaChi')
    },
    btnLoaiKH_Click () {
      if (this.parent.modelCbo.cboLoaiKH !== null && this.parent.modelCbo.cboLoaiKH !== -1) {
        let loaiKH = this.parent.modelCbo.vcboLoaiKH.find(item => item.loaikh_id === this.parent.modelCbo.cboLoaiKH)
        let kieukh_id = -1
        let kieukh = loaiKH ? loaiKH.loai : ''
        if (kieukh !== '') { kieukh_id = parseInt(kieukh.toString(), 10) }
        let nhomlkh_id = -1
        let nhomkh = loaiKH ? loaiKH.nhomlkh_id : ''
        if (nhomkh !== '') { nhomlkh_id = parseInt(nhomkh.toString(), 10) }
        this.objLoaiKH = {}
        this.objLoaiKH.loaikh_id = this.parent.modelCbo.cboLoaiKH
        this.objLoaiKH.kieukh_id = kieukh_id
        this.objLoaiKH.nhomlkh_id = nhomlkh_id
        this.$bvModal.show('popupLoaiKH')
      }
    },
    getLoaiKHID (loaikh_id) {
      if (loaikh_id !== -1) {
        let grid = this.$refs['cboGridLoaiKH'].$children[0].grid
        let idx = grid.getCurrentViewRecords().findIndex(item => item.loaikh_id === loaikh_id)
        if (idx > -1) {
          setTimeout(() => {
            grid.selectRow(idx)
          }, 200)
        }
      }
    },
    btnNganhNghe_Click () {
      this.parent.objChiTietNganhNghe = {}
      this.parent.objChiTietNganhNghe.nganhnghe_id = this.parent.modelCbo.cboNganhNgheLoc
      this.parent.objChiTietNganhNghe.TNC1_ID = this.parent.TNC1_ID
      this.parent.objChiTietNganhNghe.TNC2_ID = this.parent.TNC2_ID
      this.parent.objChiTietNganhNghe.TNC3_ID = this.parent.TNC3_ID
      this.parent.objChiTietNganhNghe.TNC4_ID = this.parent.TNC4_ID
      this.$bvModal.show('popupChiTietNganhNghe')
    },
    async getTNC (objTNC) {
      this.parent.TNC1_ID = objTNC.TNC1_ID
      this.parent.TNC2_ID = objTNC.TNC2_ID
      this.parent.TNC3_ID = objTNC.TNC3_ID
      this.parent.TNC4_ID = objTNC.TNC4_ID
      this.parent.nganhnghe_id = objTNC.nganhnghe_id
      this.parent.modelCbo.cboNganhNgheLoc = this.parent.nganhnghe_id
    },
    btnDiaChiKH_Click () {
      this.showThongTinThem = false
      this.diachi = {}
      this.diachi.tinh_id = this.parent.tinhkh_id
      this.diachi.quan_id = this.parent.quankh_id
      this.diachi.phuong_id = this.parent.phuongkh_id
      this.diachi.pho_id = this.parent.phokh_id
      this.diachi.ap_id = this.parent.apkh_id
      this.diachi.khu_id = this.parent.khukh_id
      this.diachi.dacdiem_id = this.parent.dacdiemkh_id
      this.diachi.so_nha = this.parent.sonhakh
      this.diachi.diachi = this.parent.modelCbo.txtDiaChiKH
      this.diachi.longtitude = this.parent.Lng_KH
      this.diachi.lattitude = this.parent.Lat_KH
      this.diachi_flag = 'DIACHI_KH'
      this.$bvModal.show('popupDiaChi')
    },
    tsbtnNhapMoi_Click () {},
    tsbtnGhiLai_Click () {},
    tsbtnHuyBo_Click () {},
    tsbtnXoa_Click () {},
    tsbtnThanhToan_Click () {},
    tsbtnInPL_Click () {},
    btnInHopDong_Click () {},
    tsbtnThemTT_Click () {},
    tsbtnXoaTT_Click () {},
    tsbtnThemTB_Click () {},
    tsbtnXoaTB_Click () {},
    tsbtnThemTB_DS_Click () {},
    tsbtnThongTinLH_Click () {},
    tsbtnEmail_Click () {},
    tsbtnHen_Click () {},
    chkCTV_CheckedChanged () {},
    chkNguoiGT_CheckedChanged () {},
    chkDC_NoiBo_CheckedChanged () {},
    btnThueSo_Click () {},
    btnTKMaTB_Click () {},
    btnChonSM_Click () {},
    btnDiaChiTB_Click () {},
    chkCongVan_CheckedChanged () {},
    chkKhuyenMai_CheckedChanged () {},
    btnChon_KhuyenMai_Click () {},
    btnChiTietKM_Click () {},
    btnMaNVKD_Click () {},
    btnDiachiLD_Click () {},
    btnTien_Click () {},
    lvwThueBao_selectedRowChanged () {},
    async lvwThanhToan_selectedRowChanged (data) {
      console.log('🚀 ~ file: index.vue ~ line 464 ~ lvwThanhToan_selectedRowChanged ~ data', data)
      try {
        if (data && this.parent.modelInput.lvwThanhToan && this.parent.modelInput.lvwThanhToan.length > 0) {
          this.parent.hdtt_id = Number(data.hdtt_id)
          await this.parent.$refs.ThongTinThueBao.HienThiTTHopDongTT(await this.LAY_DS_HOPDONG_TT_THEO_HDTT_ID(this.parent.hdtt_id))
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 468 ~ lvwThanhToan_selectedRowChanged ~ error', error)
      }
    },
    async LAY_DS_HOPDONG_TT_THEO_HDTT_ID (hdtt_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/tachnhapthuebao/lay_ds_hopdong_tt_theo_hdtt_id', {hdtt_id})
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 478 ~ LAY_DS_HOPDONG_TT_THEO_HDTT_ID ~ error', error)
        return []
      }
    },
    Reset_Thongso_Thuebao () {},
    async cboLoaiKH2_EditValueChanged (data) {
      console.log('🚀 ~ file: index.vue ~ line 464 ~ cboLoaiKH2_EditValueChanged ~ data', data)
      try {
        if (data === null || data === undefined || data === -1) {
          return
        }
        var kieukh_id = parseInt(data.loai)
        this.parent.modelInput.txtPhanLoaiKH = ''
        if (kieukh_id === Enum.KIEU_KH.CA_NHAN) {
          let dsPL = await this.SP_PHANLOAIKH(Enum.PHANLOAI_KH.CA_NHAN)
          if (dsPL.length > 0) {
            this.parent.PHANLOAIKH_ID = parseInt(dsPL[0].phanloaikh_id)
            this.parent.modelInput.txtPhanLoaiKH = dsPL[0].ma_plkh
          }
          this.parent.lblNgaySN = 'Ngày sinh'

          this.parent.buttonState.rdoGTNam = true
          this.parent.buttonState.rdoGTNone = false
          this.parent.buttonState.rdoGTNu = true
          this.parent.modelCkeckBox.rdoGioiTinh = 0
          this.parent.kieukh_canhan = true
          console.log('🚀 ~ file: index.vue:579 ~ cboLoaiKH2_EditValueChanged ~ this.parent.kieukh_canhan:', this.parent.kieukh_canhan)
        } else {
          let dsPL = await this.SP_PHANLOAIKH(Enum.PHANLOAI_KH.DOANH_NGHIEP)
          if (dsPL.length > 0) {
            this.parent.PHANLOAIKH_ID = parseInt(dsPL[0].phanloaikh_id)
            this.parent.modelInput.txtPhanLoaiKH = dsPL[0].ma_plkh
          }
          this.parent.lblNgaySN = '"Ngày TL'

          this.parent.buttonState.rdoGTNam = false
          this.parent.buttonState.rdoGTNone = true
          this.parent.buttonState.rdoGTNu = false
          this.parent.modelCkeckBox.rdoGioiTinh = null
          this.parent.kieukh_canhan = false
          console.log('🚀 ~ file: index.vue:593 ~ cboLoaiKH2_EditValueChanged ~ this.parent.kieukh_canhan:', this.parent.kieukh_canhan)
        }
        try {
          await this.HT_NVTC_TheoDC(this.parent.quantt_id, this.parent.phuongtt_id, this.parent.phott_id, this.parent.aptt_id, this.parent.khutt_id, this.parent.modelCbo.cboDonViQL, this.parent.dacdiemtt_id)
          if (this.parent.DONVIQL_HTTT === 1) {
            await this.HienThi_NVQL_AM_HTTT(Number(this.parent.modelCbo.cboLoaiKH), Enum.Loai_NV.NHANVIEN_KINHDOANH, this.parent.phuongtt_id, this.parent.phott_id, this.parent.aptt_id, this.parent.khutt_id, this.parent.nhanvientc_id, Number(this.parent.modelCbo.cboDonViQL), Number(this.parent.modelCbo.cboHTTT))
          } else {
            await this.HienThi_NVQL_AM(Number(this.parent.modelCbo.cboLoaiKH), Enum.Loai_NV.NHANVIEN_KINHDOANH, this.parent.phuongtt_id, this.parent.phott_id, this.parent.aptt_id, this.parent.khutt_id, this.parent.nhanvientc_id, Number(this.parent.modelCbo.cboDonViQL))
          }
        } catch (error) {
          console.log('🚀 ~ file: index.vue ~ line 103 ~ cboLoaiKH2_EditValueChanged ~ error', error)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 654 ~ cboLoaiKH2_EditValueChanged ~ error', error)
      }
    },
    async HT_NVTC_TheoDC (quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id) {
      try {
        let ds = []
        if (this.parent.TUYENTHU_HTTT === 1 && this.parent.modelCbo.cboHTTT !== -1) {
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/ht_nvtc_theodc_v2', {
            phuong_id: phuong_id || 0,
            pho_id: pho_id || 0,
            ap_id: ap_id || 0,
            khu_id: khu_id || 0,
            donvi_ql_id: donvi_ql_id,
            dacdiem_id: dacdiem_id || 0,
            httt_id: this.parent.modelCbo.cboHTTT
          })
          ds = rs.data
        } else {
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/ht_nvtc_theodc', {
            phuong_id: phuong_id || 0,
            pho_id: pho_id || 0,
            ap_id: ap_id || 0,
            khu_id: khu_id || 0,
            donvi_ql_id: donvi_ql_id,
            dacdiem_id: dacdiem_id || 0
          })
          ds = rs.data
        }
        console.log('🚀 ~ file: index.vue ~ line 559 ~ HT_NVTC_TheoDC ~ ds', ds)
        if (ds.length > 1) {
          this.inputObjFrmTraCuuNVTC = {
            quan_Id: quan_id,
            phuong_Id: phuong_id,
            pho_Id: pho_id,
            ap_Id: ap_id,
            khu_Id: khu_id,
            dacDiem_Id: dacdiem_id,
            nhanvientc_id: this.parent.nhanvientc_id,
            tuyenThu_Id: ds[0].tuyenthu_id,
            donViQL_Id: donvi_ql_id
          }
          if (this.parent.TUYENTHU_HTTT === 1 && this.parent.modelCbo.cboHTTT) { this.parent.inputObjFrmTraCuuNVTC.httt_Id = this.parent.modelCbo.cboHTTT }
          if (this.$refs.dlgTimKiemNVTC) this.$refs.dlgTimKiemNVTC.openDialog()
        } else if (ds.length > 0) {
          this.parent.modelInput.txtMaNVTC = ds[0].manv_tc
          if (ds[0].tuyenthu_id) {
            this.parent.tuyenthu_id = ds[0].tuyenthu_id
          }
          this.parent.modelInput.txtMaTuyen = ds[0].ma_tuyen
          this.parent.nhanvientc_id = ds[0].nhanvien_id
        } else {
          this.parent.modelInput.txtMaNVTC = ''
          console.log('🚀 ~ file: index.vue:662 ~ HT_NVTC_TheoDC ~ this.parent.modelInput.txtMaNVTC:', this.parent.modelInput.txtMaNVTC)
          this.parent.modelInput.txtMaTuyen = ''
          this.parent.tuyenthu_id = 0
          this.parent.nhanvientc_id = 0
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 168 ~ HT_NVTC_TheoDC ~ error', error)
      }
    },
    async SP_PHANLOAIKH (phanloai_kh) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/phanloaikh', {phanloai_kh})
        if (rs.data.length > 0) {
          return rs.data
        }
        return []
      } catch (error) {
        return []
      }
    },
    async getTraCuuNVTC (obj) {
      console.log('🚀 ~ file: index.vue:678 ~ getTraCuuNVTC ~ obj:', obj)
      try {
        if (this.parent.quantt_id != 0 && this.parent.phuongtt_id != 0) {
          this.parent.nhanvientc_id = obj.nhanVienTC_Id
          this.parent.modelInput.txtMaNVTC = obj.maNVTC
          this.parent.modelInput.txtMaTuyen = obj.maTuyen // Tuấn NA thêm code lấy mã tuyến thu --- ngày 05/11/2010
          this.parent.tuyenthu_id = obj.tuyenThuId
          if (this.parent.DONVIQL_HTTT === 1) { await this.HienThi_NVQL_AM_HTTT(Number(this.parent.modelCbo.cboLoaiKH), Enum.Loai_NV.NHANVIEN_KINHDOANH, this.parent.phuongtt_id, this.parent.phott_id, this.parent.aptt_id, this.parent.khutt_id, this.parent.nhanvientc_id, Number(this.parent.modelCbo.cboDonViQL), Number(this.parent.modelCbo.cboHTTT)) } else { await this.HienThi_NVQL_AM(Number(this.parent.modelCbo.cboLoaiKH), Enum.Loai_NV.NHANVIEN_KINHDOANH, this.parent.phuongtt_id, this.parent.phott_id, this.parent.aptt_id, this.parent.khutt_id, this.parent.nhanvientc_id, Number(this.parent.modelCbo.cboDonViQL)) }
        } else {
          this.$root.$toast.error('Chưa có thông tin về địa chỉ thanh toán\nBạn kiểm tra lại!')
          this.parent.modelInput.txtMaNVTC = ''
          this.parent.modelInput.txtMaTuyen = ''
          this.$$refs.txtMaNVTC.focus()
          this.parent.nhanvientc_id = 0
          this.parent.modelInputtxtNhanVienQL.Text = ''
          this.parent.nhanvien_ql_am_id = 0
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 581 ~ getTraCuuNVTC ~ error', error)
      }
    },
    cboNganhNgheLoc_EditValueChanged (val) {
      let select_id = val
      if (select_id < 0) return
      if (this.parent.nganhnghe_id !== select_id) {
        this.parent.TNC1_ID = 0
        this.parent.TNC2_ID = 0
        this.parent.TNC3_ID = 0
        this.parent.TNC4_ID = 0
      }
      this.btnNganhNghe_Click()
    }
  },
  watch: {
    async 'parent.modelCbo.cboHTTT' (val) {
      if (this.parent.lock) return
      try {
        console.log('🚀 ~ file: index.vue ~ line 1253 ~ val', val)
        if (this.parent.modelCbo.vcboHTTT.length > 0) {
          if (!val) return
          this.parent.httt_id = parseInt(val)
          console.log('🚀 ~ file: index.vue ~ line 1258 ~ this.parent.httt_id', this.parent.httt_id)
          if (this.parent.httt_id === Enum.HinhThucThanhToan.UYNHIEM_THU) {
            this.parent.enableCboNganHangTT = true
          } else {
            this.parent.enableCboNganHangTT = false
          }
          // Lấy lại thông tin NVTC theo hình thức TT
          if (this.parent.TUYENTHU_HTTT === 1) {
            if (this.parent.phuongtt_id && (this.parent.phott_id || this.parent.aptt_id || this.parent.khutt_id)) {
              await this.HT_NVTC_TheoDC(this.parent.quantt_id, this.parent.phuongtt_id, this.parent.phott_id, this.parent.aptt_id, this.parent.khutt_id,
                this.parent.modelCbo.cboDonViQL, this.parent.dacdiemtt_id)
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1268 ~ error', error)
      }
    }
  }
}
</script>
<style>
a.disable-btn-chuyendichbc {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.modal-ThanhToan-ChuyenDichBC .main-wrapper {
  position: unset !important;
}
.modal-ThanhToan-ChuyenDichBC .page-content {
  position: unset !important;
}
</style>
