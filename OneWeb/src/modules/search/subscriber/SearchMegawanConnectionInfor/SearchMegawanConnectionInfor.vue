<template src="./SearchMegawanConnectionInfor.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import SearchAccount from '../SearchAccount/SearchAccount'
import SearchContract from '../SearchContract/SearchContract'
import moment from 'moment'

export default {
  name: 'SearchMegawanConnectionInfor',
  components: { breadcrumb, SearchAccount, SearchContract },
  data () {
    return {
      filter: {
        txtMaKH: '',
        txtTenKH: '',
        txtDiaChi_KH: '',
        rdoType: 1
      },
      gridDSSoChinh: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Account', allowFiltering: true },
          { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true },
          { fieldName: 'tocdo', headerText: 'Tốc độ', allowFiltering: true },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: true },
          { fieldName: 'trangthai_tb', headerText: 'Trạng thái DB', allowFiltering: true },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', allowFiltering: true }
        ],
        dataSources: []
      },
      gridSoPhu: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Account', allowFiltering: true },
          { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true },
          { fieldName: 'tocdo', headerText: 'Tốc độ', allowFiltering: true },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: true },
          { fieldName: 'trangthai_tb', headerText: 'Trạng thái DB', allowFiltering: true },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', allowFiltering: true }
        ],
        dataSources: []
      },
      thongTinSoChinh: {
        cboRouterChinhId: null,
        cboRouterChinh: [],
        txtSlotPE_Chinh: '',
        txtPortPE_Chinh: '',
        txtDSLAMWan_Chinh: '',
        txtTenTbi_Chinh: '',
        txtVRF_Chinh: '',
        txtLink_Chinh: '',
        txtLAN_IP_Chinh: '',
        cboLoaiCapChinhId: null,
        cboLoaiCap_Chinh: [],
        txtTuCapGoc_Chinh: '',
        txtDoiCapGoc_Chinh: '',
        txtCapGoc_Chinh: '',
        txtTramTB_Chinh: '',
        txtSvLan_Chinh: null,
        txtCvLan_Chinh: null,
        txtPort_Chinh: '',
        txtRoute_Chinh: '',
        txtSerial_Chinh: '',
        txtWAN_IP_Chinh: '',
        txtHopCapNgon_Chinh: '',
        txtTuCapNgon_Chinh: '',
        txtTuyenCap_Chinh: '',
        txtDoiCapNgon_Chinh: '',
        txtLienTu_Chinh: '',
        txtCapNgon_Chinh: '',
        txtSoMetDay_Chinh: null
      },
      thongTinSoPhu: {
        cboRouterPhuId: null,
        cboRouterPhu: [],
        txtSlotPE_Phu: '',
        txtPortPE_Phu: '',
        txtDSLAMWan_Phu: '',
        txtTenTbi_Phu: '',
        txtVRF_Phu: '',
        txtLink_Phu: '',
        txtLAN_IP_Phu: '',
        cboLoaiCapPhuId: null,
        cboLoaiCap_Phu: [],
        txtTuCapGoc_Phu: '',
        txtDoiCapGoc_Phu: '',
        txtCapGoc_Phu: '',
        txtTramTB_Phu: '',
        txtSvLan_Phu: null,
        txtCvLan_Phu: null,
        txtPort_Phu: '',
        txtRoute_Phu: '',
        txtSerial_Phu: '',
        txtWAN_IP_Phu: '',
        txtHopCapNgon_Phu: '',
        txtTuCapNgon_Phu: '',
        txtTuyenCap_Phu: '',
        txtDoiCapNgon_Phu: '',
        txtLienTu_Phu: '',
        txtCapNgon_Phu: '',
        txtSoMetDay_Phu: ''
      },
      position: { X: 'center', Y: 'top' },
      ngayYC: moment(new Date()).format('DD/MM/YYYY'),
      khachhang_id: null,
      hdkh_id: null
    }
  },
  async created () {
    await this.frmTraCuu_KetNoi_Megawan_Load()
  },
  mounted () {
    this.show()
  },
  methods: {
    async frmTraCuu_KetNoi_Megawan_Load () {
      this.loading(true)
      try {
        const [cboRouterResponse, cboLoaiCapResponse] = await Promise.all([
          this.getThongTinPe('', 1),
          this.getThongTinLoaiCap('', '', 1)
        ])
        this.thongTinSoChinh.cboRouterChinh = cboRouterResponse
        this.thongTinSoChinh.cboRouterChinhId = cboRouterResponse.length > 0 ? cboRouterResponse[0].pe_id : null
        this.thongTinSoPhu.cboRouterPhu = cboRouterResponse
        this.thongTinSoPhu.cboRouterPhuId = cboRouterResponse.length > 0 ? cboRouterResponse[0].pe_id : null
        this.thongTinSoChinh.cboLoaiCap_Chinh = cboLoaiCapResponse
        this.thongTinSoChinh.cboLoaiCapChinhId = cboLoaiCapResponse.length > 0 ? cboLoaiCapResponse[0].loaicap_id : null
        this.thongTinSoPhu.cboLoaiCap_Phu = cboLoaiCapResponse
        this.thongTinSoPhu.cboLoaiCapPhuId = cboLoaiCapResponse.length > 0 ? cboLoaiCapResponse[0].loaicap_id : null
      } catch (e) {
        this.$root.$toast.error('Không thể lấy dữ liệu bảng theo điều kiện')
        this.resetFormLoad()
      } finally {
        this.loading(false)
      }
    },
    async getThongTinPe (param, type) {
      const response = await api.getThongTinPe(this.axios, { p_param: param, p_type: type })
      const data = await response.data
      return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
    },
    async getThongTinLoaiCap (param, param1, type) {
      const response = await api.getThongTinLoaiCap(this.axios, { p_param: param, p_param1: param1, p_type: type })
      const data = await response.data
      return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
    },
    resetFormLoad () {
      this.thongTinSoChinh.cboRouterChinh = []
      this.thongTinSoChinh.cboRouterChinhId = null
      this.thongTinSoPhu.cboRouterPhu = []
      this.thongTinSoPhu.cboRouterPhuId = null
      this.thongTinSoChinh.cboLoaiCap_Chinh = []
      this.thongTinSoChinh.cboLoaiCapChinhId = null
      this.thongTinSoPhu.cboLoaiCap_Phu = []
      this.thongTinSoPhu.cboLoaiCapPhuId = null
    },
    show () {
      this.$refs.searchMegawanConnectionInforRef.show()
    },
    onClose () {
      this.$refs.searchMegawanConnectionInforRef.hide()
    },
    btnTimKiemKH_Click () {
      if (this.filter.rdoType === 1) {
        this.$refs.popupSearchAccount.show()
      } else {
        this.$refs.popupFrmTraCuuHopDong.show()
      }
    },
    async formCloseSearchAccount (data) {
      this.$refs.popupSearchAccount.hide()
      if (data) {
        const khachHang = data.khachhang || null
        this.filter.txtMaKH = data.ma_kh || ''
        this.filter.txtTenKH = (khachHang ? khachHang.ten_kh : '') || ''
        this.filter.txtDiaChi_KH = (khachHang ? khachHang.diachi_kh : '') || ''
        this.khachhang_id = khachHang.khachhang_id
        this.hdkh_id = 0
        this.$refs.txtMaKHRef.focus()
        await this.setDataSources()
      }
    },
    async acceptSearchContract (data) {
      this.$refs.popupFrmTraCuuHopDong.hide()
      if (data) {
        const hopDong = data.hopdong
        this.filter.txtMaKH = (hopDong ? hopDong.ma_kh : '') || ''
        this.filter.txtTenKH = (hopDong ? hopDong.ten_kh : '') || ''
        this.filter.txtDiaChi_KH = (hopDong ? hopDong.diachi_kh : '') || ''
        this.khachhang_id = (hopDong ? hopDong.khachhang_id : 0) || 0
        this.hdkh_id = (hopDong ? hopDong.hdkh_id : 0) || 0
        this.$refs.txtMaKHRef.focus()
        await this.setDataSources()
      }
    },
    async setDataSources () {
      this.loading(true)
      try {
        const [soChinhResponse, soPhuResponse] = await Promise.all([
          this.layThongTinKetNoiMegawan(1),
          this.layThongTinKetNoiMegawan(2)
        ])
        this.gridDSSoChinh.dataSources = soChinhResponse
        this.gridSoPhu.dataSources = soPhuResponse
        if (soChinhResponse.length === 0) this.resetThongTinSoChinh()
        if (soPhuResponse.length === 0) this.resetThongTinSoPhu()
      } catch (e) {
        this.gridDSSoChinh.dataSources = []
        this.gridSoPhu.dataSources = []
        this.resetThongTinSoChinh()
        this.resetThongTinSoPhu()
      } finally {
        this.loading(false)
      }
    },
    async layThongTinKetNoiMegawan (loaiId) {
      try {
        const response = await api.layThongTinKetNoiMegawan(
          this.axios,
          {
            vloai_id: loaiId,
            vkhachhang_id: this.khachhang_id,
            vhdkh_id: this.hdkh_id,
            vhdtb_cha_id: 0,
            vthuebao_cha_id: 0
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin megawan')
        return []
      }
    },
    resetThongTinSoChinh () {
      this.thongTinSoChinh.txtSlotPE_Chinh = ''
      this.thongTinSoChinh.txtPortPE_Chinh = ''
      this.thongTinSoChinh.txtDSLAMWan_Chinh = ''
      this.thongTinSoChinh.txtTramTB_Chinh = ''
      this.thongTinSoChinh.txtTenTbi_Chinh = ''
      this.thongTinSoChinh.txtSvLan_Chinh = null
      this.thongTinSoChinh.txtCvLan_Chinh = null
      this.thongTinSoChinh.txtVRF_Chinh = ''
      this.thongTinSoChinh.txtPort_Chinh = ''
      this.thongTinSoChinh.txtRoute_Chinh = ''
      this.thongTinSoChinh.txtLink_Chinh = ''
      this.thongTinSoChinh.txtSerial_Chinh = ''
      this.thongTinSoChinh.txtLAN_IP_Chinh = ''
      this.thongTinSoChinh.txtWAN_IP_Chinh = ''
      this.thongTinSoChinh.txtHopCapNgon_Chinh = ''
      this.thongTinSoChinh.txtTuCapGoc_Chinh = ''
      this.thongTinSoChinh.txtTuCapNgon_Chinh = ''
      this.thongTinSoChinh.txtTuyenCap_Chinh = ''
      this.thongTinSoChinh.txtDoiCapGoc_Chinh = ''
      this.thongTinSoChinh.txtDoiCapNgon_Chinh = ''
      this.thongTinSoChinh.txtLienTu_Chinh = ''
      this.thongTinSoChinh.txtCapGoc_Chinh = ''
      this.thongTinSoChinh.txtCapNgon_Chinh = ''
      this.thongTinSoChinh.txtSoMetDay_Chinh = ''
    },
    resetThongTinSoPhu () {
      this.thongTinSoPhu.txtSlotPE_Phu = ''
      this.thongTinSoPhu.txtPortPE_Phu = ''
      this.thongTinSoPhu.txtDSLAMWan_Phu = ''
      this.thongTinSoPhu.txtTramTB_Phu = ''
      this.thongTinSoPhu.txtTenTbi_Phu = ''
      this.thongTinSoPhu.txtSvLan_Phu = null
      this.thongTinSoPhu.txtCvLan_Phu = null
      this.thongTinSoPhu.txtVRF_Phu = ''
      this.thongTinSoPhu.txtPort_Phu = ''
      this.thongTinSoPhu.txtRoute_Phu = ''
      this.thongTinSoPhu.txtLink_Phu = ''
      this.thongTinSoPhu.txtSerial_Phu = ''
      this.thongTinSoPhu.txtLAN_IP_Phu = ''
      this.thongTinSoPhu.txtWAN_IP_Phu = ''
      this.thongTinSoPhu.txtHopCapNgon_Phu = ''
      this.thongTinSoPhu.txtTuCapGoc_Phu = ''
      this.thongTinSoPhu.txtTuCapNgon_Phu = ''
      this.thongTinSoPhu.txtTuyenCap_Phu = ''
      this.thongTinSoPhu.txtDoiCapGoc_Phu = ''
      this.thongTinSoPhu.txtDoiCapNgon_Phu = ''
      this.thongTinSoPhu.txtLienTu_Phu = ''
      this.thongTinSoPhu.txtCapGoc_Phu = ''
      this.thongTinSoPhu.txtCapNgon_Phu = ''
      this.thongTinSoPhu.txtSoMetDay_Phu = ''
    },
    gridviewSoChinh_FocusedRowChanged (data) {
      if (this.gridDSSoChinh.dataSources <= 0) return false
      if (data) {
        if (data.pe_id) this.thongTinSoChinh.cboRouterChinhId = data.pe_id
        this.thongTinSoChinh.txtSlotPE_Chinh = data.slot_pe
        this.thongTinSoChinh.txtPortPE_Chinh = data.port_pe
        this.thongTinSoChinh.txtDSLAMWan_Chinh = data.dslam
        this.thongTinSoChinh.txtTramTB_Chinh = data.tram_tbi
        this.thongTinSoChinh.txtTenTbi_Chinh = data.ten_tbi
        this.thongTinSoChinh.txtSvLan_Chinh = data.svlan
        this.thongTinSoChinh.txtCvLan_Chinh = data.cvlan
        this.thongTinSoChinh.txtVRF_Chinh = data.vrf
        this.thongTinSoChinh.txtPort_Chinh = data.port
        this.thongTinSoChinh.txtRoute_Chinh = data.route
        this.thongTinSoChinh.txtLink_Chinh = data.link
        this.thongTinSoChinh.txtSerial_Chinh = data.serial
        this.thongTinSoChinh.txtLAN_IP_Chinh = data.lan_ip
        this.thongTinSoChinh.txtWAN_IP_Chinh = data.wan_ip
        if (data.loaicap_id) this.thongTinSoChinh.cboLoaiCapChinhId = data.loaicap_id
        this.thongTinSoChinh.txtHopCapNgon_Chinh = data.hopcap_p
        this.thongTinSoChinh.txtTuCapGoc_Chinh = data.tucap_goc
        this.thongTinSoChinh.txtTuCapNgon_Chinh = data.tucap_p
        this.thongTinSoChinh.txtTuyenCap_Chinh = data.tuyencap
        this.thongTinSoChinh.txtDoiCapGoc_Chinh = data.doicap_goc
        this.thongTinSoChinh.txtDoiCapNgon_Chinh = data.doicap_p
        this.thongTinSoChinh.txtLienTu_Chinh = data.lientu
        this.thongTinSoChinh.txtCapGoc_Chinh = data.cap_goc
        this.thongTinSoChinh.txtCapNgon_Chinh = data.cap_p
        this.thongTinSoChinh.txtSoMetDay_Chinh = data.culy
      }
    },
    gridViewSoPhu_FocusedRowChanged (data) {
      if (this.gridSoPhu.dataSources <= 0) return false
      if (data) {
        if (data.pe_id) this.thongTinSoPhu.cboRouterPhuId = data.pe_id
        this.thongTinSoPhu.txtSlotPE_Phu = data.slot_pe
        this.thongTinSoPhu.txtPortPE_Phu = data.port_pe
        this.thongTinSoPhu.txtDSLAMWan_Phu = data.dslam
        this.thongTinSoPhu.txtTramTB_Phu = data.tram_tbi
        this.thongTinSoPhu.txtTenTbi_Phu = data.ten_tbi
        this.thongTinSoPhu.txtSvLan_Phu = data.svlan
        this.thongTinSoPhu.txtCvLan_Phu = data.cvlan
        this.thongTinSoPhu.txtVRF_Phu = data.vrf
        this.thongTinSoPhu.txtPort_Phu = data.port
        this.thongTinSoPhu.txtRoute_Phu = data.route
        this.thongTinSoPhu.txtLink_Phu = data.link
        this.thongTinSoPhu.txtSerial_Phu = data.serial
        this.thongTinSoPhu.txtLAN_IP_Phu = data.lan_ip
        this.thongTinSoPhu.txtWAN_IP_Phu = data.wan_ip
        if (data.loaicap_id) this.thongTinSoPhu.cboLoaiCapPhuId = data.loaicap_id
        this.thongTinSoPhu.txtHopCapNgon_Phu = data.hopcap_p
        this.thongTinSoPhu.txtTuCapGoc_Phu = data.tucap_goc
        this.thongTinSoPhu.txtTuCapNgon_Phu = data.tucap_p
        this.thongTinSoPhu.txtTuyenCap_Phu = data.tuyencap
        this.thongTinSoPhu.txtDoiCapGoc_Phu = data.doicap_goc
        this.thongTinSoPhu.txtDoiCapNgon_Phu = data.doicap_p
        this.thongTinSoPhu.txtLienTu_Phu = data.lientu
        this.thongTinSoPhu.txtCapGoc_Phu = data.cap_goc
        this.thongTinSoPhu.txtCapNgon_Phu = data.cap_p
        this.thongTinSoPhu.txtSoMetDay_Phu = data.culy
      }
    }
  }
}
</script>

<style>
@media (min-width: 992px) {
  .modal .modal-huge {
    max-width: 1500px;
    width: 1500px;
  }
}
</style>
