<template src="./index.html"></template>
<style src="./style.scss"></style>

<script>
import moment from 'moment'
import api from './api/index.js'
import { DichVuVienThong, LoaiHinhTB, LoaiHopDong, TrangThaiHD } from '../../../const/enums'
import KDatePicker from '@/components/kylq_components/KDatePicker'
import breadcrumb from '@/components/breadcrumb'
import SearchContract from '@/modules/search/subscriber/SearchContract/SearchContract.vue'
import { KieuLapDat } from '../../contract/setup/InstallNewSubs/Enums'
import GiaoPhieuNhanVien from '@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien'
import TraPhieu from '@/modules/contract/setup/ReturnInstallTicket/components/traphieu'
import ChuyenTo from '../../contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo'
import CapVatTuTB from '../../contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB'
import TonPhieu from '../../contract/setup/Payment/components/TonPhieu'
import '@/assets/vendor/jquery/popper'
import PopupGanKhuVucQuanLyThueBao from '../../contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao'
import XLSX from 'xlsx'
import InBienBan from '@/modules/print/InBienBan/InBienBan'

export default {
  name: 'DividePointChangeMyTVConfig',
  components: {
    breadcrumb,
    KDatePicker,
    SearchContract,
    GiaoPhieuNhanVien,
    InBienBan,
    TraPhieu,
    ChuyenTo,
    CapVatTuTB,
    TonPhieu,
    PopupGanKhuVucQuanLyThueBao,
    XLSX
  },
  data () {
    return {
      header: {
        title: 'Thay đổi thông số Mytv',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      actions: [
        {
          id: 'btnLayTTMoi',
          name: 'Lấy TT (F5)',
          disabled: false,
          icon_class: 'one-file-attach',
          visible: true
        },
        {
          id: 'tsbtnCapNhat',
          name: 'Cập nhật (F9)',
          disabled: false,
          icon_class: 'one-reload1',
          visible: true
        },
        {
          id: 'tsbtnGiaoViec',
          name: 'Giao việc',
          disabled: false,
          icon_class: 'one-file-edit2',
          visible: true
        },
        {
          id: 'tsbtnTraPhieu',
          name: 'Trả phiếu',
          disabled: false,
          icon_class: 'one-file-arrow-right1',
          visible: true
        },
        {
          id: 'tsbtnKichHoat',
          name: 'Kích hoạt',
          disabled: false,
          icon_class: 'one-file-arrow-up',
          visible: true
        },
        {
          id: 'tsbtnChuyenTo',
          name: 'Chuyển tổ',
          disabled: false,
          icon_class: 'one-change',
          visible: true
        },
        {
          id: 'tsbtnVatTuMoi',
          name: 'Vật tư',
          disabled: false,
          icon_class: 'one-vt',
          visible: true
        },
        {
          id: 'btnHoanCong',
          name: 'Hoàn công',
          disabled: false,
          icon_class: 'one-hoancong',
          visible: false
        },
        {
          id: 'tsbtnChuyenDB',
          name: 'Hoàn thiện',
          disabled: false,
          icon_class: 'one-file-check',
          visible: true
        },
        {
          id: 'tsbtnInBB',
          name: 'Xuất phiếu',
          disabled: false,
          icon_class: 'one-print',
          visible: true
        },
        {
          id: 'tsbtnGiaoPhieu_Ton',
          name: 'Tồn phiếu',
          disabled: false,
          icon_class: 'one-calendar-number',
          visible: true
        },
        {
          id: 'tsbtnNVKT',
          name: 'Gán KV',
          disabled: false,
          icon_class: 'one-users',
          visible: true
        },
        {
          id: 'btnXuatExcel',
          name: 'Xuất Excel',
          disabled: true,
          icon_class: 'one-xlsx-import',
          visible: true
        }
      ],
      modelGiaoViec: {
        ngaygiao: '',
        nguoigv_id: 0,
        phieu_id: 0
      },
      modelTraPhieu: {
        maGd: '',
        loaihdId: 0,
        dichvuvtId: 0,
        donviId: 0,
        matb: '',
        ngaygiao: '',
        ngaytra: '',
        kieugoiId: 0,
        nhanvienId: 0
      },
      position: { X: 'center', Y: 'top' },
      modelVatTu: {},
      showModalGanNV: false,
      ganKhuVucModel: { hdtb_id: null },
      cboNguoiGV: [],
      cboNguoiGVId: null,
      dtpNgayGV: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
      dtpNgayBG: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
      dtpNgayHT: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
      cboDichVuVT: [],
      cboDichVuVTId: null,
      cboKieuLD: [],
      cboKieuLDId: null,
      luong_id: 0,
      huonggiao_id: 0,
      trangThaiHopDongId: 0,
      dtgDanhSach: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Account', allowFiltering: true },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true }
        ],
        dataSources: [],
        selectedItem: null
      },
      hdtb_id: 0,
      phieu_id: 0,
      txtDiaChiLD: '',
      txtMaGD: '',
      txtMaKH: '',
      txtMaTB: '',
      txtTenKH: '',
      txtTenTB: '',
      txtDiaChiKH: '',
      txtGhiChu: '',
      chkNgayGV: false,
      disabledNgayGV: true,
      gridGiaoviec: {
        headers: [
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true },
          { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true },
          { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true }
        ],
        dataSources: []
      },
      nhanvien_tc_id: -1,
      congviec_th: '',
      chkNgayBG: false,
      disabledNgayBG: true,
      dtThaoTac: [],
      txtTrangBiCu: '',
      txtTrangBiMoi: '',
      txtLoaiTbiCu: '',
      txtLoaiTbiMoi: '',
      cboLoaihinhTB: [],
      cboLoaihinhTBId: null,
      searchContractProp: {
        loai_hd_id: 0,
        dichvu_id: 0,
        ngay_yc: '',
        trangthai_hd: 0,
        donvi_dl_id: 0
      },
      giaoViecProp: {
        ngaygiao: '',
        nguoigv_id: 0,
        phieu_id: 0,
        ma_tb: '',
        hdtb_id: 0,
        huonggiao_id: 0
      },
      showModalTraphieu: false,
      dulieuGanKVQLTB: {
        hdtb_id: 0,
        donvi_id: 0
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed
      },
      popupComponentName: '',
      modelIn: {}
    }
  },
  async created () {
    await this.frmHTHSThayDoiThongSoMyTV_Load()
  },
  computed: {
    donViId () {
      return +this.$root.token.getDonViID()
    },
    nhanVienId () {
      return +this.$root.token.getNhanVienID()
    },
    donViDLId () {
      return +this.$root.token.getDonViDuLieuID()
    },
    nguoiDungId () {
      return +this.$root.token.getNguoiDungID()
    }
  },
  methods: {
    async frmHTHSThayDoiThongSoMyTV_Load () {
      this.loading(true)
      try {
        this.getTag()
        // this.setButtonVisible('tsbtnChuyenDB', this.trangThaiHopDongId === TrangThaiHD.DA_THI_CONG_XONG)
        // this.setButtonVisible('btnHoanCong', this.trangThaiHopDongId === TrangThaiHD.DANG_THI_CONG)
        const [nvgiaoViecResponse, quyenResponse, dvvtResponse, kieuLDResponse, dsPhieuResponse] = await Promise.all([
          this.getDanhSachNhanVienGiaoViec(),
          this.getGiaTriBangPhanQuyen(),
          this.getDanhSachDichVuVienThong(),
          this.getDanhSachKieuYeuCau(LoaiHopDong.THAYDOI_THONGSO_MYTV, 61),
          this.HienThiDanhSach()
        ])
        this.cboNguoiGV = nvgiaoViecResponse
        this.cboNguoiGVId = this.nhanVienId
        const dsDVVTTheoQuyen = [...new Set(quyenResponse.replace(/\s+/g, '').split(','))]
        if (!dsDVVTTheoQuyen.includes('4')) dsDVVTTheoQuyen.push('4')
        this.cboDichVuVT = dvvtResponse.filter(dichVuVienThong => dsDVVTTheoQuyen.indexOf(dichVuVienThong.DICHVUVT_ID.toString()) !== -1)
        if (this.cboDichVuVT.length > 0) this.cboDichVuVTId = this.cboDichVuVT[0].DICHVUVT_ID
        this.cboKieuLD = kieuLDResponse
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    getTag () {
      const tag = this.$route.query.tag
      if (tag && tag.length > 0) {
        if (tag.includes('+')) {
          const [trangThaiHopDong, huongGiaoId] = tag.split('+')
          this.trangThaiHopDongId = +trangThaiHopDong
          this.huonggiao_id = +huongGiaoId
        } else {
          this.trangThaiHopDongId = 0
          this.huonggiao_id = 0
        }
      } else {
        this.trangThaiHopDongId = 0
        this.huonggiao_id = 0
      }
    },
    setButtonVisible (id, visible) {
      this.actions[this.actions.findIndex(item => item.id === id)].visible = visible
    },
    async getDanhSachNhanVienGiaoViec () {
      try {
        const response = await api.getDanhSachNhanVienGiaoViec(this.axios, this.donViId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Lấy danh sách nhân viên không thành công')
        return []
      }
    },
    async getGiaTriBangPhanQuyen () {
      try {
        const response = await api.getGiaTriBangPhanQuyen(this.axios, this.nguoiDungId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' && data.data ? data.data.trim() : ''
      } catch (e) {
        this.$toast.error('Lấy giá trị bản phân quyền không thành công')
        return ''
      }
    },
    async getDanhSachDichVuVienThong () {
      try {
        const response = await api.getDanhSachDichVuVienThong(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Lấy danh sách dịch vụ viễn thông không thành công')
        return []
      }
    },
    async getDanhSachKieuYeuCau (loaiHopDongId, loaiThueBaoId) {
      try {
        const response = await api.getDanhSachKieuYeuCau(
          this.axios,
          { loaiHopDongId, loaiThueBaoId }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Lấy danh sách kiểu yêu cầu không thành công')
        return []
      }
    },
    async HienThiDanhSach () {
      this.setButtonVisible('tsbtnVatTuMoi', false)
      this.luong_id = 0
      const dsQuyTrinh = await this.getDanhSachQuyTrinh()
      if (dsQuyTrinh.length > 0) {
        const dsHG = await this.getDanhSachHuongGiao(dsQuyTrinh[0].quytrinh_id)
        if (dsHG.length > 0) {
          this.huonggiao_id = +dsHG[0].huonggiao_id
          const dtList = await this.getDanhSachPhieuHC(this.huonggiao_id)
          if (dtList.length > 0) {
            this.luong_id = +dsHG[0].luong_id
            await this.HienThiGiaoDien(this.luong_id)
          }
          this.dtgDanhSach.dataSources = dtList
        }
      }
    },
    async getDanhSachQuyTrinh () {
      try {
        const response = await api.getDanhSachQuyTrinh(
          this.axios,
          {
            dsloaihd_id: LoaiHopDong.THAYDOI_THONGSO_MYTV,
            dsdichvuvt_id: DichVuVienThong.ADSL,
            dstthd_id: TrangThaiHD.DANG_THI_CONG,
            kieu: 0
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Lấy danh sách quy trình không thành công')
        return []
      }
    },
    async getDanhSachHuongGiao (quyTrinhId) {
      try {
        const response = await api.getDanhSachHuongGiao(
          this.axios,
          { quyTrinhId, trangThaiHopDong: TrangThaiHD.DANG_THI_CONG }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Lấy danh sách hướng giao không thành công')
        return []
      }
    },
    async getDanhSachPhieuHC (huongGiaoId) {
      try {
        const response = await api.getDanhSachPhieuHC(
          this.axios,
          {
            'tthd_id': TrangThaiHD.DANG_THI_CONG,
            'nhanvien_id': this.nhanVienId,
            'ma_gd': 0,
            'phieu_id': 0,
            'loaihd_id': LoaiHopDong.THAYDOI_THONGSO_MYTV,
            'caidat_dv': 0,
            'kieu_id': 1,
            'huonggiao_id': huongGiaoId,
            'loc_nv': 0,
            'loai_id': 0
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Lấy danh sách phiếu không thành công')
        return []
      }
    },
    async HienThiGiaoDien (luongId) {
      this.setButtonVisible('tsbtnVatTuMoi', false)
      this.dtThaoTac = await this.getDanhSachThaoTacTheoLuong(luongId)
      if (this.dtThaoTac.length > 0) {
        for (const thaoTac of this.dtThaoTac) {
          await this.getThaoTacControl(thaoTac)
        }
      }
    },
    async getDanhSachThaoTacTheoLuong (luongId) {
      try {
        const response = await api.getDanhSachThaoTacTheoLuong(
          this.axios,
          {
            luong_id: luongId,
            code: 'GIAOVIEC,TUDONG_GIAOPHIEU_DUYET_CAPVT,TUDONG_GIAOPHIEU_HC,VAT_TU_MOI,VAT_TU,DIEUKIEN_GIAOPHIEU_BP_TIEPTHEO,TAMTHU_VT'
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Lấy danh sách thao tác không thành công')
        return []
      }
    },
    async getThaoTacControl (thaoTac) {
      try {
        const response = await api.getThaoTacControl(this.axios, { thaotac_id: thaoTac.thaotac_id })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          const controls = data.data
          if (controls.length > 0) {
            for (const control of controls) {
              const btnId = control.control_name
              if (this.actions.some(action => action.id === btnId)) {
                this.setButtonVisible(btnId, thaoTac.enable === '1')
              }
            }
          }
        }
      } catch (e) {
        this.$toast.error('Lấy danh sách control không thành công')
      }
    },
    setButtonDisabled (id, disabled) {
      this.actions[this.actions.findIndex(item => item.id === id)].disabled = disabled
    },
    async onSelectedRowChanged (data) {
      if (this.dtgDanhSach.dataSources.length > 0) {
        this.dtgDanhSach.selectedItem = { ...data }
        this.hdtb_id = +data.hdtb_id
        this.thuebao_id = +data.thuebao_id
        this.dichvuvt_id = +data.dichvuvt_id
        this.loaitb_id = +data.loaitb_id
        this.cboKieuLDId = +data.kieuld_id
        this.phieu_id = +data.phieu_id
        this.txtDiaChiLD = data.diachi_ld
        this.txtMaGD = data.ma_gd
        this.txtMaKH = data.ma_kh
        this.txtMaTB = data.ma_tb
        this.txtTenKH = data.ten_kh
        this.txtTenTB = data.ten_tb
        this.cboDichVuVTId = +data.dichvuvt_id
        this.txtDiaChiKH = data.diachi_kh
        this.txtGhiChu = data.ghichu
        this.loading(true)
        try {
          const dsNhanVien = await this.getDanhSachNhanVienTheoPhieu()
          this.HienThiDSNV(dsNhanVien)
        } catch (e) {
        } finally {
          this.loading(false)
        }
        if (data.ngay_ht_gp) {
          this.dtpNgayBG = data.ngay_ht_gp
          this.chkNgayBG = true
        } else {
          this.chkNgayBG = !this.PHAILAM('GIAOVIEC')
          this.dtpNgayBG = moment(new Date()).add(30, 'seconds').format('DD/MM/YYYY HH:mm:ss')
        }
        if (data.status.toString() === '5') {
          this.setButtonDisabled('tsbtnKichHoat', true)
          this.setButtonDisabled('tsbtnChuyenDB', false)
          this.setButtonVisible('tsbtnChuyenDB', true)
        } else {
          this.setButtonDisabled('tsbtnKichHoat', false)
          this.setButtonDisabled('tsbtnChuyenDB', true)
        }
        if (this.cboKieuLDId === KieuLapDat.TD_TBI_MYTV) {
          const trangBi = await this.layThongTinTrangBiV2(this.thuebao_id, this.hdtb_id)
          if (trangBi) {
            this.txtTrangBiCu = trangBi.vreturn[0].tentrangbi
            this.txtTrangBiMoi = trangBi.vreturn_hd[0].tentrangbi
            this.txtLoaiTbiCu = trangBi.vreturn[0].ten_tbi
            this.txtLoaiTbiMoi = trangBi.vreturn_hd[0].ten_tbi
          } else {
            this.txtTrangBiCu = ''
            this.txtTrangBiMoi = ''
            this.txtLoaiTbiCu = ''
            this.txtLoaiTbiMoi = ''
          }
          const kichHoatDisabled = this.actions[this.actions.findIndex(item => item.id === 'tsbtnKichHoat')].disabled
          this.setButtonDisabled('tsbtnTraPhieu', kichHoatDisabled)
        }
      }
    },
    async getDanhSachNhanVienTheoPhieu () {
      try {
        const response = await api.getDanhSachNhanVienTheoPhieu(
          this.axios,
          { phieu_id: this.phieu_id, nhanvien_id: this.nhanVienId, kieu_id: 2 }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Lấy danh sách nhân viên không thành công')
        return []
      }
    },
    HienThiDSNV (dsNhanVien) {
      this.chkNgayGV = true
      if (dsNhanVien.length > 0) {
        this.dtpNgayGV = dsNhanVien[0].ngaygiao
        this.cboNguoiGVId = +dsNhanVien[0].nhanvien_giao_id
        this.gridGiaoviec.dataSources = dsNhanVien
        this.nhanvien_tc_id = +dsNhanVien[0].nhanvien_th_id
        this.congviec_th = dsNhanVien[0].nhiemvu
      } else {
        this.gridGiaoviec.dataSources = []
        this.dtpNgayGV = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
        this.cboNguoiGVId = this.nhanVienId
        this.nhanvien_tc_id = -1
        this.congviec_th = ''
      }
    },
    PHAILAM (code) {
      const dt = this.dtThaoTac.filter(elm => elm.enable.toString() === '1' && elm.code.toString() === code)
      return dt.length > 0
    },
    async layThongTinTrangBiV2 (thueBaoId, vhdtbId) {
      try {
        const response = await api.layThongTinTrangBiV2(
          this.axios,
          { 'vthuebao_id': thueBaoId, 'vhdtb_id': vhdtbId }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : null
      } catch (e) {
        this.$toast.error('Lấy danh sách trang bị không thành công')
        return null
      }
    },
    async getDanhSachLoaiThueBaoByDVVT () {
      try {
        const response = await api.getDanhSachLoaiThueBaoByDVVT(this.axios)
        const data = response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Lấy danh sách loại thuê bao không thành công')
        return []
      }
    },
    async getDanhSachKieuLapDat () {
      try {
        const response = await api.getDanhSachKieuLapDat(this.axios)
        const data = response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Lấy danh sách loại thuê bao không thành công')
        return []
      }
    },
    btnLayMaGD_Click () {
      this.searchContractProp.dichvu_id = 0
      this.searchContractProp.loai_hd_id = LoaiHopDong.THAYDOI_THONGSO_MYTV
      this.searchContractProp.trangthai_hd = this.trangThaiHopDongId
      this.searchContractProp.ngay_yc = moment(new Date()).format('DD/MM/YYYY')
      this.searchContractProp.donvi_dl_id = this.donViDLId
      this.$refs.popupFrmTraCuuHopDong.show()
    },
    acceptSearchContract (data) {
      if (data) {
        this.txtMaGD = data.hopdong.ma_gd
        this.$refs.maGiaoDichRef.focus()
      }
      this.$refs.popupFrmTraCuuHopDong.hide()
    },
    async onClickAction (action) {
      switch (action.id) {
        case 'btnLayTTMoi':
          this.loading(true)
          try {
            await this.HienThiDanhSach()
          } catch (e) {
          } finally {
            this.loading(false)
          }
          break
        case 'tsbtnCapNhat':
          await this.capNhat()
          break
        case 'tsbtnGiaoViec':
          this.tsbtnGiaoViec_Click()
          break
        case 'tsbtnTraPhieu':
          await this.tsbtnTraPhieu_Click()
          break
        case 'tsbtnKichHoat':
          await this.tsbtnKichHoat_Click()
          break
        case 'tsbtnChuyenTo':
          this.tsbtnChuyenTo_Click()
          break
        case 'tsbtnVatTuMoi':
          await this.tsbtnVatTuMoi_Click()
          break
        case 'btnHoanCong':
          await this.btnHoanCong_Click()
          break
        case 'tsbtnChuyenDB':
          await this.tsbtnChuyenDB_Click()
          break
        case 'tsbtnInBB':
          await this.tsbtnInBB_Click()
          break
        case 'tsbtnGiaoPhieu_Ton':
          this.tsbtnGiaoPhieu_Ton_Click()
          break
        case 'tsbtnNVKT':
          this.tsbtnNVKT_Click()
          break
        default:
          this.xuatExcel()
          break
      }
    },
    async capNhat () {
      this.loading(true)
      try {
        if (!this.KT_DuLieuNhap()) return false
        const vds = [{
          'HDTB_ID': this.hdtb_id,
          'LUONG_ID': this.luong_id,
          'PHIEU_ID': this.phieu_id,
          'NHANVIEN_ID': this.nhanVienId,
          'NGAY_GV': this.dtpNgayGV,
          'NGAY_BG': this.dtpNgayBG,
          'NGUOIGV_ID': this.cboNguoiGVId
        }]
        const response = await api.capNhat(this.axios, { vds })
        const data = await response.data
        data.error_code === 'BSS-00000000' && data.error === '200' && data.data === '1'
          ? this.$toast.success('Cập nhật dữ liệu thành công!')
          : this.$toast.error('Cập nhật dữ liệu không thành công!')
        await this.HienThiDanhSach()
      } catch (e) {
        this.$toast.error('Không thể cập nhật dữ liệu!')
      } finally {
        this.loading(false)
      }
    },
    KT_DuLieuNhap () {
      if (this.dtgDanhSach.dataSources.length <= 0) {
        this.$root.$toast.warning('Không có dữ liệu để Cập nhật!')
        return false
      }
      if (!this.chkNgayGV) {
        this.$root.$toast.warning('Hãy chọn ngày giao việc!')
        this.$refs.chkNgayGVRef.focus()
        return false
      }
      if (this.chkNgayGV && !this.dtpNgayGV) {
        this.$root.$toast.warning('Hãy nhập ngày giao việc!')
        return false
      }
      if (!this.cboNguoiGVId) {
        this.$root.$toast.warning('Hãy nhập người giao việc!')
        return false
      }
      if (!this.chkNgayBG) {
        this.$root.$toast.warning('Hãy chọn ngày hoàn thành!')
        this.$refs.chkNgayBGRef.focus()
        return false
      }
      if (!this.dtpNgayBG) {
        this.$root.$toast.warning('Hãy nhập ngày hoàn thành!')
        return false
      }
      if (this.PHAILAM('GIAOVIEC') && this.gridGiaoviec.dataSources.length <= 0) {
        this.$root.$toast.warning('Hãy nhập nhân viên thực hiện!')
        return false
      }
      if (moment(this.dtpNgayGV, 'DD/MM/YYYY HH:mm:ss').isAfter(moment(this.dtpNgayBG, 'DD/MM/YYYY HH:mm:ss'))) {
        this.$root.$toast.warning('Ngày giao việc không được lớn hơn ngày hoàn thành!')
        return false
      }
      return true
    },
    tsbtnGiaoViec_Click () {
      if (this.dtgDanhSach.dataSources.length <= 0) {
        this.$root.$toast.warning('Chưa có danh thuê bao. Bạn hãy kiểm tra lại!')
        return false
      }
      if (this.cboNguoiGV.length <= 0 || !this.cboNguoiGVId) {
        this.$root.$toast.warning('Hãy nhập nhân viên giao việc!')
        return false
      }
      if (!this.chkNgayGV) {
        this.$root.$toast.warning('Hãy chọn ngày giao việc!')
        return false
      }
      if (this.chkNgayGV && !this.dtpNgayGV) {
        this.$root.$toast.warning('Hãy nhập ngày giao việc!')
        return false
      }
      if (this.chkNgayBG) {
        if (!this.dtpNgayBG) {
          this.$root.$toast.warning('Hãy nhập ngày hoàn thành!')
          return false
        }
        if (moment(this.dtpNgayGV, 'DD/MM/YYYY HH:mm:ss').isAfter(moment(this.dtpNgayBG, 'DD/MM/YYYY HH:mm:ss'))) {
          this.$root.$toast.warning('Ngày giao việc không được lớn hơn ngày hoàn thành!')
          return false
        }
      }
      this.giaoViecProp.ngaygiao = this.dtpNgayGV
      this.giaoViecProp.phieu_id = this.phieu_id
      this.giaoViecProp.nguoigv_id = this.cboNguoiGVId
      this.giaoViecProp.ma_tb = this.txtMaTB
      this.giaoViecProp.hdtb_id = this.hdtb_id
      this.giaoViecProp.huonggiao_id = this.huonggiao_id
      this.$refs.formGiaoPhieuRef.init()
      this.$refs.giaoPhieuModalRef.show()
    },
    async giaoViecSuccess () {
      this.loading(true)
      try {
        const ds = await this.getDanhSachNhanVienTheoPhieu()
        this.HienThiDSNV(ds)
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnTraPhieu_Click () {
      if (this.phieu_id === 0) {
        this.$root.$toast.warning('Bạn chưa chọn phiếu!')
        return false
      }
      this.loading(true)
      try {
        const ds = await this.getThongTinTheoHDTB()
        if (ds.length > 0) {
          this.modelTraPhieu.dichvuvtId = +ds[0].dichvuvt_id
          this.modelTraPhieu.kieugoiId = 3
          this.modelTraPhieu.maGd = this.txtMaTB
          this.modelTraPhieu.loaihdId = +ds[0].loaihd_id
          this.modelTraPhieu.matb = this.txtMaTB
          this.modelTraPhieu.donviId = this.donViId
          this.modelTraPhieu.ngaytra = '0'
          this.modelTraPhieu.ngaygiao = '0'
          this.modelTraPhieu.nhanvienId = this.nhanVienId
        } else {
          this.$root.$toast.warning('Danh sách thông tin theo hợp đồng thuê bao trống!')
        }
        this.showModalTraphieu = true
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async getThongTinTheoHDTB () {
      try {
        const response = await api.getThongTinTheoHDTB(this.axios, { hdtb_id: this.hdtb_id })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$toast.error('Lấy việc phải làm không thành công')
        return []
      }
    },
    async hiddenTraPhieu () {
      this.loading(true)
      try {
        await this.HienThiDanhSach()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnKichHoat_Click () {
      if (!this.dtgDanhSach.selectedItem) {
        this.$root.$toast.warning('Chưa chọn thuê bao!')
        return false
      }
      const vmp = this.dtgDanhSach.selectedItem.vmp
      const maTB = this.dtgDanhSach.selectedItem.ma_tb
      if (this.cboKieuLDId === KieuLapDat.TD_TBI_MYTV) {
        this.loading(true)
        try {
          await this.kichHoatMyTV({
            maTB,
            kieuLdId: this.cboKieuLDId,
            hdtbId: this.hdtb_id,
            vmp
          })
          await this.HienThiDanhSach()
        } catch (e) {
        } finally {
          this.loading(false)
        }
      } else {
        this.$confirm(vmp === '1'
          ? 'Bạn thật sự kích hoạt Mytv -> VMP không ?'
          : 'Bạn thật sự kích hoạt VMP -> Mytv không ?'
          ,
        'Thông báo',
        { confirmButtonText: 'Có', cancelButtonText: 'Không' }
        ).then(async () => {
          this.loading(true)
          try {
            await this.kichHoatMyTV({
              maTB,
              kieuLdId: this.cboKieuLDId,
              hdtbId: this.hdtb_id,
              vmp
            })
            await this.HienThiDanhSach()
          } catch (e) {
          } finally {
            this.loading(false)
          }
        })
      }
    },
    async kichHoatMyTV ({ maTB, kieuLdId, hdtbId, vmp }) {
      try {
        const response = await api.kichHoatMyTV(
          this.axios,
          { ma_tb: maTB, cb_kieuld: kieuLdId, hdtb_id: hdtbId, vmp: vmp }
        )
        const data = await response.data
        data.error_code === 'BSS-00000000' && data.error === '200'
          ? this.$root.$toast.success('Kích hoạt thành công!')
          : this.$root.$toast.error(data.message)
      } catch (e) {
        this.$toast.error('Không thể thực hiện kích hoạt!')
      }
    },
    tsbtnChuyenTo_Click () {
      this.$refs.popupChuyenToRef.openDialog(this.phieu_id, this.hdtb_id)
    },
    async closePopupChuyenTo () {
      this.loading(true)
      try {
        await this.HienThiDanhSach()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnVatTuMoi_Click () {
      if (this.phieu_id === 0) {
        this.$root.$toast.warning('Bạn chưa chọn phiếu!')
        return false
      }
      const ds = await this.getThongTinTheoHDTB()
      if (ds.length > 0) {
        this.modelVatTu.phieu_id = this.phieu_id
        this.modelVatTu.loaihd_id = +ds[0].loaihd_id
        this.modelVatTu.hdtb_id = this.hdtb_id
        this.modelVatTu.thuebao_id = +ds[0].thuebao_id
        this.modelVatTu.vdvvt_id = this.dichvuvt_id
        this.modelVatTu.loaitb_id = this.loaitb_id
        this.modelVatTu.vma_tbcur = this.txtMaTB
        this.modelVatTu.baohong_id = 0
        this.$refs.popupVatTuTBRef.openDialog()
      }
    },
    async btnHoanCong_Click () {
      if (this.dtgDanhSach.dataSources.length <= 0) {
        this.$root.$toast.warning('Không có dữ liệu để thực hiện hoàn công')
        return false
      }
      if (!this.KT_DuLieuNhap()) return false
      this.loading(true)
      try {
        const vds = [{
          HDTB_ID: this.hdtb_id,
          LUONG_ID: this.luong_id,
          PHIEU_ID: this.phieu_id,
          DONVI_ID: this.donViId,
          DONVI_DL_ID: this.donViDLId,
          NHANVIEN_ID: this.nhanVienId
        }]
        const response = await api.hoanCongHoSoThayDoiTSMyTV(this.axios, { vds })
        const data = response.data
        data.error_code === 'BSS-00000000' && data.error === '200'
          ? this.$root.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
          : this.$root.$toast.error(data.message)
        await this.getDanhSachThueBao()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnChuyenDB_Click () {
      if (this.dtgDanhSach.dataSources.length <= 0) {
        this.$root.$toast.warning('Không có thuê bao để hoàn thiện!')
        return false
      }
      this.loading(true)
      try {
        const vds = [
          {
            HDTB_ID: this.hdtb_id,
            LUONG_ID: this.luong_id,
            PHIEU_ID: this.phieu_id,
            HUONGGIAO_ID: this.huonggiao_id,
            DONVI_ID: this.donViId,
            NHANVIEN_ID: this.nhanVienId
          }
        ]
        const response = await api.hoanThienHoSoThayDoiTSMyTV(this.axios, { vds })
        const data = await response.data
        data.error_code === 'BSS-00000000' && data.error === '200'
          ? this.$root.$toast.success('Cập nhật dữ liệu vào viễn thông tỉnh thành công!')
          : this.$root.$toast.error(data.message)
        await this.HienThiDanhSach()
      } catch (e) {
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện hoàn thiện')
      } finally {
        this.loading(false)
      }
    },
    async tsbtnInBB_Click () {
      this.$refs.maGiaoDichRef.focus()
      this.loading(true)
      try {
        const vhdkhid = this.getThongTinHdtb(this.hdtb_id, 1)
        if (vhdkhid === '-1') {
          this.$root.$toast.warning('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
          return false
        }
        if (!this.dtgDanhSach.selectedItem) {
          this.$root.$toast.warning('Hãy chọn phiếu!')
          return false
        }
        this.modelIn.n_phieu_id = this.dtgDanhSach.selectedItem.phieu_id
        this.modelIn.n_hdkh_id = this.dtgDanhSach.selectedItem.hdkh_id
        this.modelIn.n_hdtb_id = this.dtgDanhSach.selectedItem.hdtb_id
        this.modelIn.nvth_id = -1
        this.modelIn.huonggiao_id = this.huonggiao_id
        this.modelIn.tentram_tb = ''
        this.$refs.popupInBBRef.showModal()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async getThongTinHdtb (param, type) {
      try {
        const response = await api.getThongTinHdtb(this.axios, { param, type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hợp đồng thuê bao')
        return ''
      }
    },
    tsbtnGiaoPhieu_Ton_Click () {
      if (this.phieu_id === 0) {
        this.$root.$toast.warning('Hãy chọn phiếu trước để nhập lý do tồn!')
        return false
      }
      const dsPhieuTon = this.dtgDanhSach
        .dataSources
        .filter(elm => elm.chon.toString() === '1')
        .map(elm => +elm.phieu_id)
      if (!dsPhieuTon.includes(this.phieu_id)) dsPhieuTon.push(this.phieu_id)
      this.$refs.popupTonPhieuRef.showPhieuTon(this.phieu_id, this.trangThaiHopDongId, dsPhieuTon)
    },
    closePopupTonPhieu () {
      this.$refs.popupTonPhieuRef.$refs.dialogObjPhieuTon.hide()
    },
    tsbtnNVKT_Click () {
      this.dulieuGanKVQLTB.hdtb_id = this.hdtb_id
      this.dulieuGanKVQLTB.donvi_id = 0
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao')
      this.popupComponentName = 'popupChonKhuVuc'
      this.popupComponentData = this.dulieuGanKVQLTB
      this.$bvModal.show('popupComponents')
    },
    popupClosed () {
      this.$bvModal.hide('popupComponents')
      this.popupComponent = null
    },
    xuatExcel () {
      const data = []
      const dataSources = this.dtgDanhSach.dataSources
      if (dataSources.length > 0) {
        for (let i = 0; i < dataSources.length; i++) {
          data.push({
            'STT': i + 1,
            'MA_GD': dataSources[i].ma_gd,
            'MA_KH': dataSources[i].ma_kh,
            'TEN_KH': dataSources[i].ten_kh,
            'MA_TB': dataSources[i].ma_tb,
            'TEN_TB': dataSources[i].ten_tb,
            'DIACHI_LD': dataSources[i].diachi_ld,
            'NGAY_HT': dataSources[i].ngay_ht,
            'DIACHI_KH': dataSources[i].diachi_kh,
            'TEN_KIEULD': dataSources[i].ten_kieuld,
            'TOCDO': dataSources[i].ttocdo,
            'TENMUC': dataSources[i].mtenmuc,
            'MA_TT': dataSources[i].ma_tt,
            'NGAY_TT': dataSources[i].ngay_tt
          })
        }
        const sheet = XLSX.utils.json_to_sheet(data)
        const wb = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(wb, sheet, 'DSTB')
        XLSX.writeFile(wb, 'DSTB.xls')
      } else this.$root.$toast.warning('Không có dữ liệu!')
    }
  },
  watch: {
    chkNgayGV (val) {
      this.disabledNgayGV = !this.disabledNgayGV
    },
    chkNgayBG (val) {
      this.disabledNgayBG = !this.disabledNgayBG
    },
    async cboDichVuVTId (val) {
      if (this.cboDichVuVT.length > 0) {
        this.dichvuvt_id = val
        this.loading(true)
        try {
          const [loaiTBRes, kieuLDRes] = await Promise.all([
            this.getDanhSachLoaiThueBaoByDVVT(),
            this.getDanhSachKieuLapDat()
          ])
          this.cboLoaihinhTB = loaiTBRes.filter(elm => +elm.DICHVUVT_ID === this.dichvuvt_id)
          this.cboLoaihinhTBId = LoaiHinhTB.INTERNET_MYTV
          const dsKieuLD = this.dichvuvt_id === 0
            ? kieuLDRes.filter(elm => +elm.LOAIHD_ID === LoaiHopDong.THAYDOI_THONGSO_MYTV)
            : await this.getDanhSachKieuYeuCau(LoaiHopDong.THAYDOI_THONGSO_MYTV, this.dichvuvt_id)
          this.cboKieuLD = dsKieuLD
          if (dsKieuLD.length > 0) this.cboKieuLDId = dsKieuLD[0].kieuld_id
        } catch (e) {
        } finally {
          this.loading(false)
        }
      }
    },
    'dtgDanhSach.dataSources': function (val) {
      this.setButtonDisabled('btnXuatExcel', val.length === 0)
    }
  }
}
</script>
