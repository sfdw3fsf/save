<template src="./ChangeNumberAccount.html"></template>
<style src="./ChangeNumberAccount.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import {
  DichVuVienThong,
  KHOANMUC_TT,
  KieuLapDat,
  LOAI_DV,
  LoaiHinhTB,
  LoaiHopDong,
  TRANGBI,
  TrangThaiHD
} from '@/const/enums'
import moment from 'moment'
import { LOAISO } from '@/modules/contract/setup/InstallNewSubs/Enums'
import SearchContract from '@/modules/search/subscriber/SearchContract/SearchContract.vue'
import DiaChiModal from '@/modules/contract/setup/SurveyRequest/popups/ChonDiaChi/DiaChiModal.vue'
import TienKMCuPopup from '@/modules/search/subscriber/ModifyContractMoney/popup/TienKMCuPopup'
import ThongTinEmail from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'
import ThongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import Payment from '@/modules/contract/setup/Payment/Payment.vue'
import api from './api'
import frmDSTBCungMa from '@/modules/contract/setup/ChangeContractOwner/popups/frmDSTBCungMa.vue'
import InHoSo from '@/modules/print/InHopDong/InHopDong'
import frmTraCuuThueBao from './popup/frmTraCuuThueBao'
import frmChonSoChinh from './popup/frmChonSoChinh'
import SearchGeneral from '@/modules/search/subscriber/SearchGeneral/SearchGeneral'
import popupChonSM from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonSM'

export default {
  name: 'ChangeNumberAccount',
  components: {
    breadcrumb,
    SearchContract,
    DiaChiModal,
    TienKMCuPopup,
    ThongTinEmail,
    ThongTinLienHe,
    Payment,
    frmDSTBCungMa,
    InHoSo,
    frmTraCuuThueBao,
    frmChonSoChinh,
    SearchGeneral,
    popupChonSM
  },
  data () {
    return {
      header: {
        title: 'Thay đổi số máy/Account',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      donvi_id: 0,
      hdtb_id: 0,
      hdkh_id: 0,
      khachhang_id: 0,
      thuebao_id: 0,
      loaitb_id: 0,
      doituong_id: 0,
      photb_id: 0,
      dacdiemtb_id: 0,
      aptb_id: 0,
      khutb_id: 0,
      phuongtb_id: 0,
      quantb_id: 0,
      tinhtb_id: 0,
      quanld_id: 0,
      dsdichvuvt_id: '',
      sonhatb: '',
      dichvuvt_id: 0,
      dtList: [],
      trangbi_id: TRANGBI.VIENTHONG_TRANGBI,
      phuongld_id: 0,
      phold_id: 0,
      apld_id: 0,
      khuld_id: 0,
      isdn_sochinh: false,
      matb_isdn_chinh: '',
      dtienld: 0,
      dvatld: 0,
      ds_tien_khoanmuc: [],
      khong_ht_tracuudb: 0,
      actions: [
        {
          id: 'tsbtnNhapMoi',
          name: 'Nhập mới',
          disabled: false,
          icon: 'one-file-plus',
          visible: true
        },
        {
          id: 'tsbtnGhiLai',
          name: 'Ghi lại',
          disabled: false,
          icon: 'one-save',
          visible: true
        },
        {
          id: 'tsbtnHuyBo',
          name: 'Hủy',
          disabled: false,
          icon: 'nc-icon-glyph ui-1_circle-remove',
          visible: true
        },
        {
          id: 'tsbtnXoa',
          name: 'Xóa HĐ',
          disabled: false,
          icon: 'one-trash',
          visible: true
        },
        {
          id: 'btnThanhtoan',
          name: 'Thanh toán',
          disabled: false,
          icon: 'nc-icon-glyph shopping_credit-card',
          visible: true
        },
        {
          id: 'toolStripDropDownButton1',
          name: 'In PL',
          disabled: false,
          icon: 'one-print',
          visible: true
        },
        {
          id: 'tsbtnThemTB',
          name: 'Thêm TB',
          disabled: false,
          icon: 'one-mobile-plus',
          visible: true
        },
        {
          id: 'tsbtnXoaTB',
          name: 'Xóa TB',
          disabled: true,
          icon: 'one-phone-remove',
          visible: true
        },
        {
          id: 'tsbtnThongTinLH',
          name: 'Liên hệ',
          disabled: false,
          icon: 'nc-icon-glyph business_contacts',
          visible: true
        },
        {
          id: 'tsbtnEmail',
          name: 'Email',
          disabled: false,
          icon: 'one-email',
          visible: true
        }
      ],
      txtMaTB: '',
      txtMaGD: '',
      txtMaHD: '',
      txtDiaChiTB: '',
      txtSoMayTBMoi: '',
      txtSoMayTBMoiReadOnly: false,
      txtTenTB: '',
      txtGhiChu: '',
      txtMaKH: '',
      txtTenKH: '',
      txtDiaChiKH: '',
      txtTongTienHD: 0,
      txtDiachiLD: '',
      txtDiaChiTT: '',
      dgrThueBao: {
        headers: [
          { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, width: '15%' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: '15%' },
          { fieldName: 'diachi_tb', headerText: 'Địa chỉ thuê bao', allowFiltering: true, width: '40%' },
          { fieldName: 'ma_tb_cu', headerText: 'Số máy/Acc cũ', allowFiltering: true, width: '15%' },
          { fieldName: 'ma_tb_moi', headerText: 'Số máy/Acc mới', allowFiltering: true, width: '15%' }
        ],
        dataSources: []
      },
      dtpNgayLHD: '',
      dtpNgayYeuCau: '',
      dtpHanDatCho: '',
      dtpHanDatChoDisabled: false,
      txtTongTien: 0,
      txtTongVat: 0,
      lblTongTien: 0,
      cboDichVuVT: [],
      cboDichVuVTId: null,
      cboLoaihinhTB: [],
      cboLoaihinhTBId: null,
      cboKieuLD: [],
      cboKieuLDId: null,
      cboVeTinh: [],
      cboVeTinhId: null,
      cboTrangBi: [],
      cboTrangBiId: null,
      btnKhoSoDisabled: false,
      searchContractProp: {
        loaihd_id: LoaiHopDong.DOISO_TB,
        tthd_id: TrangThaiHD.MOI,
        donviId: 0,
        nhanvienId: 0,
        ngay_yc: moment(new Date()).format('DD/MM/YYYY'),
        donviDLId: 0
      },
      dataDiaChi: {
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        dacdiem_id: 0,
        khu_id: 0,
        ap_id: 0,
        sonha: '',
        diachi: ''
      },
      tienKMLoaiHdId: LoaiHopDong.DOISO_TB,
      mayCN: '',
      ipCN: '',
      inHopDongParam: {
        ma_gd: '',
        ngaylap_hd: '',
        ngay_yc: '',
        hdkh_id: 0
      },
      position: { X: 'center', Y: 'top' },
      thongTinNguoiDung: null,
      chonSMProp: {
        _quan_id: 0,
        _phuong_id: 0,
        _loaiso_id: LOAISO.THUONG,
        _thueso: false,
        loaitb_id: 0,
        dichvuvt_id: 0,
        hdkh_id: 0,
        donvi_ql_id: 0
      }
    }
  },
  computed: {
    LayKhachhang_ID () {
      if (this.dgrThueBao.dataSources.length === 0) return 0
      if (!this.dgrThueBao.dataSources[0].hasOwnProperty('kh_id')) return 0
      return +this.dgrThueBao.dataSources[0].kh_id
    },
    nhanVienId () {
      return +this.$root.token.getNhanVienID()
    },
    donViId () {
      return +this.$root.token.getDonViID()
    },
    donViDLId () {
      return +this.$root.token.getDonViDuLieuID()
    },
    maNguoiDung () {
      return this.thongTinNguoiDung ? this.thongTinNguoiDung.ma_nd : ''
    },
    phanVungId () {
      return +this.$root.token.getPhanVungID()
    }
  },
  async mounted () {
    await this.frmDoiSo_Load()
  },
  methods: {
    async frmDoiSo_Load () {
      this.loading(true)
      try {
        await this.setButton(-1)
        const [khoanMucRes, thongTinCNRes, nguoiDungResponse] = await Promise.all([
          this.getTienKhoanMucLoaiHD(),
          this.getThongTinCN(),
          this.getThongTinNguoiDung2()
        ])
        this.ds_tien_khoanmuc = khoanMucRes
        this.mayCN = thongTinCNRes ? thongTinCNRes.mayCn : ''
        this.ipCN = thongTinCNRes ? thongTinCNRes.ipCn : ''
        this.thongTinNguoiDung = nguoiDungResponse
        try {
          const tag = this.$route.query.tag
          this.dsdichvuvt_id = tag && tag.length > 0 ? tag : ''
        } catch (e) {
          this.dsdichvuvt_id = ''
        }
        await this.load()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async setButton (kieu) {
      this.setButtonDisabled('tsbtnNhapMoi')
      this.setButtonDisabled('tsbtnGhiLai')
      this.setButtonDisabled('tsbtnXoa')
      this.setButtonDisabled('tsbtnHuyBo')
      this.setButtonDisabled('tsbtnThemTB')
      this.setButtonDisabled('tsbtnXoaTB')
      this.setButtonDisabled('btnThanhtoan')
      this.setButtonDisabled('toolStripDropDownButton1')
      if (kieu === -1) {
        this.$refs.txtMaTBRef.focus()
        this.setButtonEnabled('tsbtnGhiLai')
        this.setButtonEnabled('tsbtnHuyBo')
        this.doituong_id = 1
      } else if (kieu === 0) {
        this.$refs.txtMaTBRef.focus()
        this.setButtonEnabled('tsbtnNhapMoi')
        await this.clear()
      } else if (kieu === 1) {
        this.$refs.txtMaTBRef.focus()
        this.setButtonEnabled('tsbtnGhiLai')
        this.setButtonEnabled('tsbtnHuyBo')
        await this.clear()
      } else if (kieu === 2) {
        this.$refs.txtMaGDRef.focus()
        this.setButtonEnabled('tsbtnNhapMoi')
        this.setButtonEnabled('tsbtnThoat')
        await this.clear()
      } else if (kieu === 3) {
        this.$refs.txtMaTBRef.focus()
        this.setButtonEnabled('tsbtnNhapMoi')
        this.setButtonEnabled('tsbtnGhiLai')
        this.setButtonEnabled('tsbtnXoa')
        this.setButtonEnabled('tsbtnHuyBo')
        this.setButtonEnabled('tsbtnThemTB')
        this.setButtonEnabled('tsbtnXoaTB')
        this.setButtonEnabled('btnThanhtoan')
        this.setButtonEnabled('toolStripDropDownButton1')
      }
    },
    setButtonDisabled (id) {
      this.actions[this.actions.findIndex(item => item.id === id)].disabled = true
    },
    setButtonEnabled (id) {
      this.actions[this.actions.findIndex(item => item.id === id)].disabled = false
    },
    async clear () {
      const currentDate = moment(new Date()).format('DD/MM/YYYY')
      this.trangbi_id = TRANGBI.VIENTHONG_TRANGBI
      this.dtpHanDatCho = moment(new Date()).add(7, 'days').format('DD/MM/YYYY')
      this.txtMaGD = ''
      this.txtMaHD = ''
      this.txtMaTB = ''
      this.txtDiaChiTB = ''
      this.txtSoMayTBMoi = ''
      this.txtTenTB = ''
      this.txtGhiChu = ''
      this.txtMaKH = ''
      this.txtTenKH = ''
      this.txtDiaChiKH = ''
      this.txtTongTienHD = 0
      this.dtienld = 0
      this.dvatld = 0
      this.hdtb_id = 0
      this.hdkh_id = 0
      this.khachhang_id = 0
      this.thuebao_id = 0
      this.loaitb_id = 0
      this.doituong_id = 1
      this.donvi_id = 0
      this.dacdiemtb_id = 0
      this.photb_id = 0
      this.khutb_id = 0
      this.aptb_id = 0
      this.phuongtb_id = 0
      this.quantb_id = 0
      this.tinhtb_id = 0
      this.quanld_id = 0
      this.sonhatb = '0'
      this.dgrThueBao.dataSources = []
      this.dtpNgayLHD = currentDate
      this.dtpNgayYeuCau = currentDate
      try {
        this.ds_tien_khoanmuc = await this.getTienKhoanMucLoaiHD()
        await this.LayTienTheoKhoanMuc()
      } catch (e) {
      }
      this.txtDiachiLD = ''
      this.phuongld_id = 0
      this.phold_id = 0
      this.apld_id = 0
      this.khuld_id = 0
      this.txtDiaChiTT = ''
    },
    async LayTienTheoKhoanMuc () {
      const ds = await this.getDanhSachTienKhoanMuc(this.thuebao_id, this.cboKieuLDId)
      if (ds.length > 0) {
        this.HienThiTienLapDat(ds)
      }
      this.LayTongTien_HDTB()
    },
    async getDanhSachTienKhoanMuc (thueBaoId, kieuLdId) {
      if (thueBaoId === 0 || !kieuLdId || kieuLdId === 0) return []
      try {
        const response = await api.getDanhSachTienKhoanMuc(this.axios, thueBaoId, kieuLdId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách tiền khoản mục')
        return []
      }
    },
    HienThiTienLapDat (ds) {
      let ktLm = true
      if (ds[0].id && ds[0].id.toString() === '-1') {
        this.dtienld = 0
        this.dvatld = 0
      } else {
        for (const element of ds) {
          if (+element.khoanmuctt_id === KHOANMUC_TT.KMTT_DOISO) {
            this.dtienld = +element.tien
            this.dvatld = +element.vat
            ktLm = false
          }
        }
        if (ktLm) {
          this.dtienld = 0
          this.dvatld = 0
        }
      }
      for (const element of this.ds_tien_khoanmuc) {
        if (+element.khoanmuctt_id === KHOANMUC_TT.KMTT_DOISO) {
          element.tien = this.dtienld
          element.vat = this.dvatld
        }
      }
    },
    LayTongTien_HDTB () {
      let dtongtien = 0
      let dtongvat = 0
      for (const element of this.ds_tien_khoanmuc) {
        dtongtien += +element.tien
        dtongvat += +element.vat
      }
      this.txtTongTien = dtongtien
      this.txtTongVat = dtongvat
      this.lblTongTien = dtongtien + dtongvat
    },
    async getTienKhoanMucLoaiHD () {
      try {
        const response = await api.getTienKhoanMucLoaiHD(this.axios, { loaihd_id: LoaiHopDong.DOISO_TB })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách khoản mục loại hợp đồng')
        return []
      }
    },
    async getThongTinCN () {
      try {
        const response = await api.getThongTinCN(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : null
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin cập nhật')
        return null
      }
    },
    async getThongTinNguoiDung2 () {
      try {
        const response = await api.getThongTinNguoiDung2(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' ? data.data : null
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin người dùng')
        return null
      }
    },
    async load () {
      this.$refs.txtMaTBRef.focus()
      const [cboDichVuVTRes, thamSoMacDinhRes, cboVeTinhRes, cboTrangBiRes] = await Promise.all([
        this.getDanhSachDichVuVT(),
        this.getDanhSachThamSoMacDinh(),
        this.getDanhSachVeTinh(),
        this.getDanhSachTrangBi(),
        this.HTTT_VAO_DATAGRID()
      ])
      this.setCboDichVuVT(cboDichVuVTRes)
      this.setThamSoMacDinh(thamSoMacDinhRes)
      this.setCboVeTinh(cboVeTinhRes)
      this.setCboTrangBi(cboTrangBiRes)
      this.dtpHanDatCho = moment(new Date()).add(7, 'days').format('DD/MM/YYYY')
      this.btnKhoSoDisabled = true
    },
    async getDanhSachDichVuVT () {
      try {
        const response = await api.getDanhSachDichVuVT(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách dịch vụ viễn thông')
        return []
      }
    },
    async getDanhSachThamSoMacDinh () {
      try {
        const response = await api.getDanhSachThamSoMacDinh(this.axios, 0)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách tham số mặc định')
        return []
      }
    },
    async getDanhSachVeTinh () {
      try {
        const response = await api.getDanhSachVeTinh(this.axios, { loaidv_id: LOAI_DV.TRAM_VT })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách vệ tinh')
        return []
      }
    },
    async getDanhSachTrangBi () {
      try {
        const response = await api.getDanhSachTrangBi(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách trang bị')
        return []
      }
    },
    setCboDichVuVT (ds) {
      const dvu = this.dsdichvuvt_id
        ? this.dsdichvuvt_id.split(';')
        : [DichVuVienThong.CO_DINH, DichVuVienThong.ADSL, DichVuVienThong.IMS]
      this.cboDichVuVT = ds.filter(element => dvu.includes(element.DICHVUVT_ID))
      if (this.cboDichVuVT.length > 0) this.cboDichVuVTId = this.cboDichVuVT[0].DICHVUVT_ID
    },
    setThamSoMacDinh (dsTS) {
      if (dsTS.length > 0) {
        const KHONG_HIENTHI_TRACUU_DB_DOISO = dsTS.find(element => element.ma_ts && element.ma_ts.toString() === 'KHONG_HIENTHI_TRACUU_DB_DOISO')
        if (KHONG_HIENTHI_TRACUU_DB_DOISO && KHONG_HIENTHI_TRACUU_DB_DOISO.ten_ts.toString() === '1') this.khong_ht_tracuudb = 1
      }
    },
    setCboVeTinh (ds) {
      this.cboVeTinh = ds
      if (this.cboVeTinh.length > 0) this.cboVeTinhId = this.cboVeTinh[0].donvi_id
    },
    setCboTrangBi (ds) {
      this.cboTrangBi = ds
      if (this.cboTrangBi.length > 0) this.cboTrangBiId = this.cboTrangBi[0].trangbi_id
    },
    async HTTT_VAO_DATAGRID () {
      const dsHopDong = await this.getDanhSachHopDongByMaGD()
      this.dtList = Array.isArray(dsHopDong) ? dsHopDong : []
      if (this.dtList.length > 0) {
        this.dgrThueBao.dataSources = this.dtList
        this.dgrThueBao.dataSources.length > 1 ? this.setButtonEnabled('tsbtnXoaTB') : this.setButtonDisabled('tsbtnXoaTB')
      } else {
        await this.clear()
      }
    },
    async getDanhSachHopDongByMaGD () {
      try {
        const response = await api.getDanhSachHopDongByMaGD(this.axios, {
          in_ma_gd: this.txtMaGD,
          in_loaihd_id: LoaiHopDong.DOISO_TB,
          in_donvi_id: this.donViId,
          in_tthd_id: TrangThaiHD.MOI,
          in_nhanvien_id: this.nhanVienId,
          in_donvi_dl_id: 0
        })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng')
        return []
      }
    },
    async selectedRowChanged (item) {
      this.loading(true)
      try {
        this.cboDichVuVTId = +item.dichvuvt_id
        this.txtMaGD = item.ma_gd
        this.txtMaHD = item.ma_hd
        this.txtMaTB = item.ma_tb_cu
        this.txtSoMayTBMoi = item.ma_tb_moi
        this.thuebao_id = +item.thuebao_id
        this.txtTenTB = item.ten_tb
        this.txtDiaChiTB = item.diachi_tb
        this.txtDiachiLD = item.diachi_ld
        this.hdtb_id = +item.hdtb_id
        this.cboLoaihinhTBId = +item.loaitb_id
        this.cboKieuLDId = +item.kieuld_id
        this.txtDiaChiTT = item.diachi_tt
        const dsDcld = await this.getDanhSachDiaChiByThueBaoId(this.thuebao_id)
        if (dsDcld.length > 0) {
          this.tinhld_id = dsDcld[0].tinhld_id ? +dsDcld[0].tinhld_id : 0
          this.quanld_id = dsDcld[0].quanld_id ? +dsDcld[0].quanld_id : 0
          this.phuongld_id = dsDcld[0].phuongld_id ? +dsDcld[0].phuongld_id : 0
          this.phold_id = dsDcld[0].phold_id ? +dsDcld[0].phold_id : 0
          this.apld_id = dsDcld[0].apld_id ? +dsDcld[0].apld_id : 0
          this.khuld_id = dsDcld[0].khuld_id ? +dsDcld[0].khuld_id : 0
        } else {
          this.tinhld_id = 0
          this.quanld_id = 0
          this.phuongld_id = 0
          this.phold_id = 0
          this.apld_id = 0
          this.khuld_id = 0
        }
        this.txtGhiChu = item.ghichu1
        this.txtMaKH = item.ma_kh
        this.txtTenKH = item.ten_kh || ''
        this.txtDiaChiKH = item.diachi_kh || ''
        this.dtpNgayYeuCau = item.ngaylap_hd
          ? moment(item.ngaylap_hd).format('DD/MM/YYYY')
          : moment(new Date()).format('DD/MM/YYYY')
        let tongTien = 0
        for (const element of this.dtList) {
          tongTien += +element.tongtien
        }
        this.txtTongTienHD = tongTien
        if (item.loaitb_id && item.loaitb_id.toString()) this.loaitb_id = +item.loaitb_id
        if (item.khachhang_id && item.khachhang_id.toString()) this.khachhang_id = +item.khachhang_id
        this.hdkh_id = +item.hdkh_id
        this.doituong_id = +item.doituong_id
        this.donvi_id = +item.donviql_id
        if (this.cboDichVuVTId === DichVuVienThong.ADSL) {
          const daSet = await this.getDanhSachHopDongTBADSL(this.hdtb_id)
          if (daSet.length > 0) {
            if (daSet[0].handatcho && daSet[0].handatcho.toString()) {
              this.dtpHanDatCho = moment(daSet[0].handatcho.toString()).format('DD/MM/YYYY')
            }
          } else {
            this.dtpHanDatCho = moment(new Date()).add(7, 'days').format('DD/MM/YYYY')
          }
        } else if (this.dichvuvt_id === DichVuVienThong.IMS) {
          const daSet = await this.getDanhSachHopDongTBIMS(this.hdtb_id)
          if (daSet.length > 0) {
            this.cboVeTinhId = +daSet[0].tramtb_id
          }
        } else if (this.dichvuvt_id === DichVuVienThong.CO_DINH) {
          const daSet = await this.getDanhSachHopDongTBCD(this.hdtb_id)
          if (daSet.length > 0) this.cboVeTinhId = +daSet[0].tramtb_id
        }
        this.setButtonEnabled('tsbtnXoaTB')
        this.setButtonEnabled('tsbtnThemTB')
        this.setButtonEnabled('tsbtnXoa')
        this.setButtonEnabled('tsbtnNhapMoi')
        this.setButtonEnabled('btnThanhtoan')
        this.setButtonEnabled('toolStripDropDownButton1')
        this.btnKhoSoDisabled = false
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    btnLayMaGD_Click () {
      this.searchContractProp.donviDLId = this.donViDLId
      this.searchContractProp.donviId = this.donViId
      this.searchContractProp.nhanvienId = this.nhanVienId
      this.$refs.popupFrmTraCuuHopDong.show()
    },
    async acceptSearchContract (data) {
      this.txtMaGD = data.hopdong.ma_gd
      this.$refs.txtMaGDRef.focus()
      this.$refs.popupFrmTraCuuHopDong.hide()
      this.loading(true)
      try {
        await this.setButton(3)
        await this.HTTT_VAO_DATAGRID()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async txtMaTB_KeyPress () {
      this.txtSoMayTBMoi = ''
      if (this.txtMaTB) {
        this.loading(true)
        try {
          const ds = await this.getDanhSachHopDongByMaTB()
          if (ds.length > 0) {
            await this.HienThiTTHopDongKH(ds)
          } else if (this.dichvuvt_id === DichVuVienThong.CO_DINH || this.dichvuvt_id === DichVuVienThong.ADSL ||
            this.dichvuvt_id === DichVuVienThong.IMS || this.dichvuvt_id === DichVuVienThong.GPHONE
          ) {
            await this.HienThiTT_DanhBa(this.txtMaTB)
          } else {
            const dichVuVTName = this.cboDichVuVT.find(element => element.dvvt_id === this.cboDichVuVTId).dvvt_name || ''
            this.$root.$toast.error(`Không được phép thay đổi số máy/Account của thuê bao: ${dichVuVTName} có mã thuê bao: ${this.txtMaTB}`)
            this.btnKhoSoDisabled = true
          }
        } catch (e) {
        } finally {
          this.loading(false)
        }
      }
    },
    async getDanhSachHopDongByMaTB () {
      try {
        const response = await api.getDanhSachHopDongByMaTB(
          this.axios,
          {
            maTb: this.txtMaTB,
            loaiHdId: LoaiHopDong.DOISO_TB,
            ttHdId: TrangThaiHD.MOI,
            donViDlId: 0,
            dichVuVtId: this.dichvuvt_id,
            loaiTbId: 0
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng')
        return []
      }
    },
    async HienThiTTHopDongKH (ds) {
      if (ds.length > 0) {
        await this.setButton(3)
        this.hdkh_id = +ds[0].hdkh_id
        this.txtMaGD = ds[0].ma_gd
        this.txtMaHD = ds[0].ma_hd
        this.txtMaKH = ds[0].ma_kh
        this.thuebao_id = +ds[0].thuebao_id
        this.txtSoMayTBMoi = ds[0].ma_tb
        this.txtTenTB = ds[0].ten_tb
        this.txtDiaChiTB = ds[0].diachi_tb
        this.txtDiachiLD = ds[0].diachi_ld
        const dsDcld = await this.getDanhSachDiaChiByThueBaoId(+ds[0].thuebao_id)
        if (dsDcld.length > 0) {
          this.tinhld_id = dsDcld[0].tinhld_id ? +dsDcld[0].tinhld_id : 0
          this.quanld_id = dsDcld[0].quanld_id ? +dsDcld[0].quanld_id : 0
          this.phuongld_id = dsDcld[0].phuongld_id ? +dsDcld[0].phuongld_id : 0
          this.phold_id = dsDcld[0].phold_id ? +dsDcld[0].phold_id : 0
          this.apld_id = dsDcld[0].apld_id ? +dsDcld[0].apld_id : 0
          this.khuld_id = dsDcld[0].khuld_id ? +dsDcld[0].khuld_id : 0
        } else {
          this.tinhld_id = 0
          this.quanld_id = 0
          this.phuongld_id = 0
          this.phold_id = 0
          this.apld_id = 0
          this.khuld_id = 0
        }
        this.txtGhiChu = ds[0].ghichu
        if (ds[0].khachhang_id && ds[0].khachhang_id.toString()) this.khachhang_id = +ds[0].khachhang_id
        this.txtTenKH = ds[0].ten_kh || ''
        this.txtDiaChiKH = ds[0].diachi_kh || ''
        this.loaitb_id = +ds[0].loaitb_id
        this.cboLoaihinhTBId = this.loaitb_id
        this.cboKieuLDId = +ds[0].kieuld_id
        const dsISDN = await this.getDanhSachIDSN(this.thuebao_id, this.cboKieuLDId)
        if (dsISDN.length > 0) {
          this.isdn_sochinh = +dsISDN[0].isdn_sochinh === 1
          this.matb_isdn_chinh = dsISDN[0].matb_isdn_chinh
        }
        this.doituong_id = +ds[0].doituong_id
        this.donvi_id = +ds[0].donviql_id
        if (this.dichvuvt_id === DichVuVienThong.ADSL) {
          const daSet = await this.getDanhSachHopDongTBADSL(this.hdtb_id)
          if (daSet.length > 0) {
            this.cboVeTinhId = +daSet[0].tramtb_id
          }
        } else if (this.dichvuvt_id === DichVuVienThong.IMS) {
          const daSet = await this.getDanhSachHopDongTBIMS(this.hdtb_id)
          if (daSet.length > 0) {
            this.cboVeTinhId = +daSet[0].tramtb_id
          }
        } else if (this.dichvuvt_id === DichVuVienThong.CO_DINH) {
          const daSet = await this.getDanhSachHopDongTBCD(this.hdtb_id)
          if (daSet.length > 0) this.cboVeTinhId = +daSet[0].tramtb_id
        }
        await this.HTTT_VAO_DATAGRID()
        this.btnKhoSoDisabled = false
      } else {
        await this.setButton(0)
        this.btnKhoSoDisabled = true
        this.txtSoMayTBMoi = ''
      }
    },
    async getDanhSachDiaChiByThueBaoId (thueBaoId) {
      try {
        const response = await api.getDanhSachDiaChiByThueBaoId(this.axios, { in_thuebao_id: thueBaoId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách địa chỉ')
        return []
      }
    },
    async getDanhSachIDSN (thueBaoId, kieuLDId) {
      try {
        const response = await api.getDanhSachIDSN(this.axios, thueBaoId, kieuLDId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách địa chỉ')
        return []
      }
    },
    async getDanhSachHopDongTBADSL (hdtbId) {
      try {
        const response = await api.getDanhSachHopDongTBADSL(this.axios, hdtbId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng ADSL')
        return []
      }
    },
    async getDanhSachHopDongTBIMS (hdtbId) {
      try {
        const response = await api.getDanhSachHopDongTBIMS(this.axios, hdtbId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng IMS')
        return []
      }
    },
    async getDanhSachHopDongTBCD (hdtbId) {
      try {
        const response = await api.getDanhSachHopDongTBCD(this.axios, hdtbId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng cố định')
        return []
      }
    },
    btnDiaChi_Click () {
      this.dataDiaChi.tinh_id = this.tinhtb_id
      this.dataDiaChi.quan_id = this.quantb_id
      this.dataDiaChi.phuong_id = this.phuongtb_id
      this.dataDiaChi.pho_id = this.photb_id
      this.dataDiaChi.dacdiem_id = this.dacdiemtb_id
      this.dataDiaChi.khu_id = this.khutb_id
      this.dataDiaChi.ap_id = this.aptb_id
      this.dataDiaChi.sonha = this.sonhatb
      this.dataDiaChi.diachi = this.txtDiaChiTB
      this.$refs.diaChiModalRef.showModal()
    },
    onAcceptDiaChi (data) {
      this.tinhtb_id = +data.tinh_id
      this.quantb_id = +data.quan_id
      this.phuongtb_id = +data.phuong_id
      this.photb_id = +data.pho_id
      this.dacdiemtb_id = +data.dacdiem_id
      this.khutb_id = +data.khu_id
      this.aptb_id = +data.ap_id
      this.sonhatb = data.sonha
      this.txtDiaChiTB = data.diachi
      this.$refs.txtDiaChiTBRef.focus()
    },
    async btnLayMaKh_Click () {
      if (!this.txtMaTB) {
        this.$root.$toast.warning('Bạn chưa nhập mã thuê bao!!!')
        return false
      }
      this.loading(true)
      try {
        if (this.khachhang_id === 0) {
          const dsKhCu = await this.getDanhSachBienDongByHdtbId()
          if (!dsKhCu) return false
          if (dsKhCu.length === 0) {
            this.$root.$toast.warning(`Chưa có thông tin về khách hàng đổi số có thuê bao: ${this.txtMaTB}\nKiểm tra lại !`)
            this.$refs.txtMaTBRef.focus()
            return false
          }
          this.khachhang_id = dsKhCu[0].khachhang_id
        }
        await this.$refs.frmTraCuuThueBaoRef.showDialog(
          this.khachhang_id,
          this.txtMaGD,
          'Đang đổi số'
        )
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachBienDongByHdtbId () {
      try {
        const response = await api.getDanhSachBienDongByHdtbId(this.axios, this.hdtb_id)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách biến động')
        return null
      }
    },
    async onOkTraCuuThueBao (item) {
      if (item.maTB) {
        this.txtMaTB = item.maTB
      }
      if (item.dichVuVTId !== 0) {
        this.cboDichVuVTId = item.dichVuVTId
      }
      this.$refs.txtMaTBRef.focus()
      await this.txtMaTB_KeyPress()
    },
    btnTien_Click () {
      this.$refs.tienKMCuPopupRef.showModal()
    },
    acceptTienKM (data) {
      this.ds_tien_khoanmuc = data
      if (this.ds_tien_khoanmuc.length > 0) {
        for (const element of this.ds_tien_khoanmuc) {
          if (+element.khoanmuctt_id === KHOANMUC_TT.KMTT_DOISO) {
            this.dtienld = +element.tien
            this.dvatld = +element.vat
          }
          this.LayTongTien_HDTB()
        }
      }
    },
    async txtMaGD_KeyUp () {
      this.loading(true)
      try {
        await this.HTTT_VAO_DATAGRID()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async HienThiTT_DanhBa (maTb) {
      const response = await this.getDanhSachThueBaoByMaTB(maTb, this.cboDichVuVTId)
      if (response) {
        if (response.error_code === 'BSS-00000000' && response.error === '200') {
          const ds = response.data
          if (ds.length > 1) {
            this.$refs.frmDSTBCungMaRef.ds = ds
            this.$refs.frmDSTBCungMaRef.showDialog()
            return true
          }
          await this.setThongTinDanhBa(ds)
        } else {
          this.$root.$toast.warning(response.message)
        }
      }
    },
    async getDanhSachThueBaoByMaTB (maTB, dichVuVTId) {
      try {
        const response = await api.getDanhSachThueBaoByMaTB(this.axios, maTB, dichVuVTId)
        const data = await response.data
        return data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách thuê bao')
        return null
      }
    },
    async setThongTinDanhBa (dsThueBao) {
      try {
        const response = await this.getThongTinTBByMaTB(dsThueBao[0].thuebao_id)
        if (response) {
          if (response.error_code === 'BSS-00000000' && response.error === '200') {
            let ds = response.data
            if (ds.length > 0) {
              this.txtMaKH = ds[0].ma_kh
              this.txtMaHD = ds[0].ma_hd
              this.khachhang_id = +ds[0].khachhang_id
              if (this.LayKhachhang_ID !== 0 && this.LayKhachhang_ID !== this.khachhang_id) {
                this.$root.$toast.warning(`Mã thuê bao: ${this.txtMaTB} vừa nhập không thuộc khách hàng đang thực hiện đổi số/Account\nBạn hãy kiểm tra lại!`)
                return false
              }
              this.txtTenKH = ds[0].ten_kh || ''
              this.txtDiaChiKH = ds[0].diachi_kh || ''
              this.thuebao_id = +ds[0].thuebao_id
              this.txtTenTB = ds[0].ten_tb
              this.txtDiaChiTB = ds[0].diachi_tb
              if (this.hasDiaChi(ds[0])) {
                this.tinhld_id = ds[0].tinh_ld_id ? +ds[0].tinh_ld_id : 0
                this.quanld_id = ds[0].quan_ld_id ? +ds[0].quan_ld_id : 0
                this.phuongld_id = ds[0].phuong_ld_id ? +ds[0].phuong_ld_id : 0
                this.phold_id = ds[0].pho_ld_id ? +ds[0].pho_ld_id : 0
                this.apld_id = ds[0].ap_ld_id ? +ds[0].ap_ld_id : 0
                this.khuld_id = ds[0].khu_ld_id ? +ds[0].khu_ld_id : 0
              } else {
                this.$root.$toast.warning('Kiểm tra lại thông tin địa chỉ của thuê bao')
                await this.setButton(3)
                return false
              }
              this.txtDiaChiTT = ds[0].diachi_tt
              this.txtDiachiLD = ds[0].diachi_ld
              this.loaitb_id = +ds[0].loaitb_id
              this.cboLoaihinhTBId = +ds[0].loaitb_id
              if (this.cboLoaihinhTBId === LoaiHinhTB.DIENTHOAI_CD) this.cboKieuLDId = KieuLapDat.DOISO_CD_ISDN_SOMOI
              const dsISDN = await this.getDanhSachIDSN(this.thuebao_id, this.cboKieuLDId)
              if (dsISDN.length > 0) {
                this.isdn_sochinh = +dsISDN[0].isdn_sochinh === 1
                this.matb_isdn_chinh = dsISDN[0].matb_isdn_chinh
              }
              this.doituong_id = +ds[0].doituong_id
              this.donvi_id = +ds[0].donviql_id
              this.txtMaTB = ds[0].ma_tb
              this.dtpNgayLHD = ds[0].ngaylap_hd && ds[0].ngaylap_hd.toString()
                ? moment(ds[0].ngaylap_hd.toString()).format('DD/MM/YYYY')
                : moment(new Date()).format('DD/MM/YYYY')
              if (this.dichvuvt_id === DichVuVienThong.CO_DINH) {
                const daSet = await this.getDanhSachDanhBaCD(this.thuebao_id)
                if (daSet.length > 0) {
                  if (daSet[0].tramtb_id && daSet[0].tramtb_id.toString()) {
                    this.cboVeTinhId = +daSet[0].tramtb_id
                  }
                  this.cboTrangBiId = daSet[0].trangbi_id && daSet[0].trangbi_id.toString()
                    ? +daSet[0].trangbi_id
                    : TRANGBI.KHACHHANG_TU_TRANGBI
                }
              } else if (this.dichvuvt_id === DichVuVienThong.ADSL) {
                const daSet = await this.getDanhSachDanhBaADSL(this.thuebao_id)
                if (daSet.length > 0) {
                  if (daSet[0].tramtb_id && daSet[0].tramtb_id.toString()) this.cboVeTinhId = +daSet[0].tramtb_id
                  this.cboTrangBiId = daSet[0].trangbi_id && daSet[0].trangbi_id.toString()
                    ? +daSet[0].trangbi_id
                    : TRANGBI.KHACHHANG_TU_TRANGBI
                }
              } else if (this.dichvuvt_id === DichVuVienThong.IMS) {
                const daSet = await this.getDanhSachDanhBaIMS(this.thuebao_id)
                if (daSet.length > 0) {
                  if (daSet[0].tramtb_id && daSet[0].tramtb_id.toString()) this.cboVeTinhId = +daSet[0].tramtb_id
                  this.cboTrangBiId = daSet[0].trangbi_id && daSet[0].trangbi_id.toString()
                    ? +daSet[0].trangbi_id
                    : TRANGBI.KHACHHANG_TU_TRANGBI
                }
              } else if (this.dichvuvt_id === DichVuVienThong.GPHONE) {
                const daSet = await this.getDanhSachDanhBaGPhone(this.thuebao_id)
                if (daSet.length > 0) {
                  this.cboTrangBiId = daSet[0].trangbi_id && daSet[0].trangbi_id.toString()
                    ? +daSet[0].trangbi_id
                    : TRANGBI.KHACHHANG_TU_TRANGBI
                }
              }
              if (this.khong_ht_tracuudb === 0) {
                this.$refs.searchGeneralRef.MaTB = this.txtMaTB
                await this.$refs.searchGeneralRef.onMaTBEnter()
                this.$refs.searchGeneralDialogRef.show()
              }
              this.btnKhoSoDisabled = false
            } else {
              const dichVuVT = this.cboDichVuVT.find(element => element.id === this.cboDichVuVTId)
              const dichVuVTName = dichVuVT ? dichVuVT.name : ''
              this.$root.$toast.warning(`Không tìm thấy thuê bao: ${this.txtMaTB} tương ứng với dịch vụ: ${dichVuVTName}`)
              this.btnKhoSoDisabled = true
              this.txtSoMayTBMoi = ''
              await this.clear()
            }
          } else {
            this.$root.$toast.warning(response.message_detail)
          }
        }
      } catch (e) {
      }
    },
    async getThongTinTBByMaTB (maTB) {
      try {
        const response = await api.getThongTinTBByMaTB(this.axios, maTB)
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin thuê bao')
        return null
      }
    },
    hasDiaChi (thongTin) {
      return thongTin.hasOwnProperty('tinh_ld_id') && thongTin.hasOwnProperty('quan_ld_id') &&
        thongTin.hasOwnProperty('phuong_ld_id') && thongTin.hasOwnProperty('pho_ld_id') &&
        thongTin.hasOwnProperty('ap_ld_id') && thongTin.hasOwnProperty('khu_ld_id')
    },
    async getDanhSachDanhBaCD (thueBaoId) {
      try {
        const response = await api.getDanhSachDanhBaCD(this.axios, thueBaoId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng ADSL')
        return []
      }
    },
    async getDanhSachDanhBaADSL (thueBaoId) {
      try {
        const response = await api.getDanhSachDanhBaADSL(this.axios, thueBaoId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng ADSL')
        return []
      }
    },
    async getDanhSachDanhBaIMS (thueBaoId) {
      try {
        const response = await api.getDanhSachDanhBaIMS(this.axios, thueBaoId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng IMS')
        return []
      }
    },
    async getDanhSachDanhBaGPhone (thueBaoId) {
      try {
        const response = await api.getDanhSachDanhBaGPhone(this.axios, thueBaoId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng cố định')
        return []
      }
    },
    async acceptChonTB (dsThueBao) {
      this.loading(true)
      try {
        await this.setThongTinDanhBa(dsThueBao)
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async btnKhoSo_Click () {
      this.loading(true)
      try {
        if (this.cboDichVuVTId === DichVuVienThong.CO_DINH ||
          this.cboDichVuVTId === DichVuVienThong.IMS ||
          this.cboDichVuVTId === DichVuVienThong.GPHONE
        ) {
          if ((this.loaitb_id === LoaiHinhTB.ISDN2B_CD || this.loaitb_id === LoaiHinhTB.PABX || this.loaitb_id === LoaiHinhTB.ISDN2B_CQ ||
            this.loaitb_id === LoaiHinhTB.ISDN30B_CD || this.loaitb_id === LoaiHinhTB.ISDN30B_CQ) && (
            this.cboKieuLDId === KieuLapDat.DOISO_CHINH_ISDN || this.cboKieuLDId === KieuLapDat.DOINHOM_ISDN ||
            this.cboKieuLDId === 355 || this.cboKieuLDId === 450
          )) {
            await this.LayDanhSachISDN()
          } else if (this.quanld_id !== 0) {
            this.LaySoMayMoi()
          }
        } else if (!this.txtSoMayTBMoi) {
          this.$root.$toast.warning('Bạn chưa nhập Account mới!')
          return false
        } else if (!await this.kiemTraMaTB()) {
          this.$root.$toast.warning(`Account ${this.txtSoMayTBMoi} đã được cấp. Bạn hãy kiểm tra lại`)
          return false
        } else if (this.cboLoaihinhTBId === LoaiHinhTB.INTERNET_1260 || this.cboLoaihinhTBId === LoaiHinhTB.INTERNET_ADSL ||
          this.cboLoaihinhTBId === LoaiHinhTB.INTERNET_FTTH || this.cboLoaihinhTBId === LoaiHinhTB.WIFI_FIBER
        ) {
          const response = await this.KiemTraAccountVisa()
          if (!response) return false
          if (response.data !== null) {
            this.$root.$toast.warning('Account đã tồn tại')
            return false
          }
          if (response.faultCode !== 'error.resource.not.exist') {
            this.$root.$toast.warning(response.faultString)
            return false
          }
          this.$root.$toast.success(`Tài khoản ${this.txtSoMayTBMoi} chưa được sử dụng. Bạn có thể thực hiện đặt chỗ cho Account này!`)
          this.$refs.dtpHanDatChoRef.focus()
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async LayDanhSachISDN () {
      const response = await this.getDanhSachSoISDN()
      if (!response) return false
      if (response.error_code === 'BSS-00000000' && response.error === '200') {
        this.$refs.frmChonSoChinhRef.showDialog(response.data)
      } else {
        this.$root.$toast.error(response.message_detail)
      }
    },
    async getDanhSachSoISDN () {
      try {
        const response = await api.getDanhSachSoISDN(this.axios, this.thuebao_id, this.cboKieuLDId)
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách số ISDN')
        return null
      }
    },
    onOkChonSoChinh (data) {
      this.txtSoMayTBMoi = data
    },
    LaySoMayMoi () {
      if (this.cboDichVuVTId === DichVuVienThong.CO_DINH ||
        this.cboDichVuVTId === DichVuVienThong.IMS ||
        this.cboDichVuVTId === DichVuVienThong.GPHONE
      ) {
        this.chonSMProp._quan_id = this.quanld_id
        this.chonSMProp._phuong_id = this.phuongld_id
        this.chonSMProp.loaitb_id = this.cboLoaihinhTBId
        this.chonSMProp.dichvuvt_id = this.cboDichVuVTId
        this.chonSMProp.donvi_ql_id = this.donvi_id
        this.$refs.popupChonSMRef.show()
      }
    },
    onPopupChonSMClose (val) {
      if (val) this.txtSoMayTBMoi = val.somay
      this.$refs.popupChonSMRef.hide()
    },
    async kiemTraMaTB () {
      try {
        const response = await api.kiemTraMaTB(
          this.axios,
          {
            maTb: this.txtSoMayTBMoi,
            hdtbId: 0,
            dichVuVtId: this.cboDichVuVTId
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' && +data.data === 0
      } catch (e) {
        this.$root.$toast.error('Không thể kiểm tra thuê bao')
        return null
      }
    },
    async KiemTraAccountVisa () {
      try {
        const response = await api.kiemTraAccountVisa(this.axios, { accountName: this.txtSoMayTBMoi })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể kiểm tra account visa')
        return null
      }
    },
    async onClickAction (id) {
      switch (id) {
        case 'tsbtnNhapMoi':
          await this.tsbtnNhapMoi_Click()
          break
        case 'tsbtnGhiLai':
          await this.tsbtnGhiLai_Click()
          break
        case 'tsbtnHuyBo':
          await this.tsbtnHuyBo_Click()
          break
        case 'tsbtnXoa':
          this.tsbtnXoa_Click()
          break
        case 'btnThanhtoan':
          this.btnThanhtoan_Click()
          break
        case 'toolStripDropDownButton1':
          this.toolStripDropDownButton1_Click()
          break
        case 'tsbtnThemTB':
          await this.tsbtnThemTB_Click()
          break
        case 'tsbtnXoaTB':
          await this.tsbtnXoaTB_Click()
          break
        case 'tsbtnThongTinLH':
          this.tsbtnThongTinLH_Click()
          break
        default:
          this.tsbtnEmail_Click()
          break
      }
    },
    async tsbtnNhapMoi_Click () {
      this.loading(true)
      try {
        await this.setButton(1)
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async tsbtnGhiLai_Click () {
      if (!this.txtSoMayTBMoi) {
        this.cboDichVuVT === DichVuVienThong.CO_DINH || this.cboDichVuVT === DichVuVienThong.IMS
          ? this.$root.$toast.warning('Hãy nhập số máy mới cần thay đổi')
          : this.$root.$toast.warning('Hãy nhập Account mới cần thay đổi')
        this.$refs.txtSoMayTBMoiRef.focus()
        return false
      }
      if (!this.txtMaTB) {
        this.$root.$toast.warning('Hãy nhập số máy cũ của bạn')
        this.$refs.txtMaTBRef.focus()
        return false
      }
      if ((this.loaitb_id === LoaiHinhTB.ISDN2B_CD || this.loaitb_id === LoaiHinhTB.ISDN2B_CQ ||
        this.loaitb_id === LoaiHinhTB.ISDN30B_CD || this.loaitb_id === LoaiHinhTB.ISDN30B_CQ) &&
        this.cboKieuLDId === KieuLapDat.DOISO_CHINH_ISDN &&
        !this.isdn_sochinh
      ) {
        this.$root.$toast.warning(`Số máy ${this.txtMaTB} là số phụ. Bạn không thể thực hiện nghiệp vụ đổi số chính ISDN!`)
      }
      this.loading(true)
      try {
        const tienKMUpperKey = this.ds_tien_khoanmuc.map(element => this.getObjectUpperKey(element))
        if (this.actions.find(element => element.id === 'tsbtnNhapMoi').disabled) {
          const response = await this.lapHopDongDoiSo(
            0,
            JSON.stringify([{
              HDTB_ID: null,
              KIEULD_ID: this.cboKieuLDId,
              MA_TB: this.txtSoMayTBMoi,
              HANDATCHO: this.dtpHanDatCho,
              GHICHU: this.txtGhiChu,
              DONVI_ID: this.donViId
            }]),
            JSON.stringify(tienKMUpperKey)
          )
          if (response.error_code === 'BSS-00000000' && response.error === '200') {
            if (response.data.includes('OK')) {
              this.$root.$toast.success('Thêm mới hợp đồng thành công')
              let [status, ...maGD] = response.data.split('_')
              this.txtMaGD = maGD.join('_')
              await this.setButton(3)
              await this.HTTT_VAO_DATAGRID()
            } else {
              this.$root.$toast.error(response.data)
            }
          } else {
            this.$root.$toast.error(response.message)
          }
        } else {
          const response = await this.lapHopDongDoiSo(
            1,
            JSON.stringify([{
              HDTB_ID: this.hdtb_id,
              KIEULD_ID: this.cboKieuLDId,
              MA_TB: this.txtSoMayTBMoi,
              HANDATCHO: this.dtpHanDatCho,
              GHICHU: this.txtGhiChu,
              DONVI_ID: this.donViId
            }]),
            JSON.stringify(tienKMUpperKey)
          )
          if (response.error_code === 'BSS-00000000' && response.error === '200') {
            if (response.data === 'OK') {
              this.$root.$toast.success('Cập nhật hợp đồng thành công')
              await this.setButton(3)
              await this.HTTT_VAO_DATAGRID()
            } else {
              this.$root.$toast.error(response.data)
            }
          } else {
            this.$root.$toast.error(response.message)
          }
        }
      } catch (e) {
        this.$root.$toast.error('Không thể thực hiện thao tác')
      } finally {
        this.loading(false)
      }
    },
    async lapHopDongDoiSo (action, hdtbData, tienHDData) {
      const response = await api.lapHopDongDoiSo(
        this.axios,
        {
          p_phanvung_id: this.phanVungId,
          p_action: action,
          p_thuebao_id: this.thuebao_id,
          p_js_hdtb_data: hdtbData,
          p_js_ct_tienhd_data: tienHDData,
          p_nhanvien_id: this.nhanVienId,
          p_nguoi_cn: this.maNguoiDung,
          p_may_cn: this.mayCN,
          p_ip_cn: this.ipCN
        }
      )
      return await response.data
    },
    getObjectUpperKey (source) {
      return Object.keys(source).reduce((destination, key) => {
        destination[key.toUpperCase()] = source[key]
        return destination
      }, {})
    },
    async tsbtnHuyBo_Click () {
      this.loading(true)
      try {
        await this.setButton(2)
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    tsbtnXoa_Click () {
      this.$confirm(
        'Bạn thật sự muốn xóa hợp đồng không?',
        'Thông báo',
        { confirmButtonText: 'Có', cancelButtonText: 'Không' }
      ).then(async () => {
        this.loading(true)
        try {
          await this.xoaHopDong()
          await this.HTTT_VAO_DATAGRID()
          await this.setButton(0)
        } catch (e) {
        } finally {
          this.loading(false)
        }
      }).catch((e) => {})
    },
    async xoaHopDong () {
      try {
        const response = await api.xoaHopDong(
          this.axios,
          {
            list: [{ hdkh_id: this.hdkh_id, loaihd_id: LoaiHopDong.DOISO_TB }]
          })
        const data = await response.data
        data.error_code === 'BSS-00000000' && data.error === '200'
          ? this.$root.$toast.success('Xóa hợp đồng thành công')
          : this.$root.$toast.error(data.message)
      } catch (e) {
        this.$root.$toast.error('Không thể xóa hợp đồng')
      }
    },
    btnThanhtoan_Click () {
      this.loading(true)
      try {
        this.$refs.thanhtoanDialog.show()
        this.$refs.thanhToanPopUp.SetMaGd(this.txtMaGD)
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    paymentSuccess () {
      this.$refs.frmThanhToanHDRef.hide()
    },
    toolStripDropDownButton1_Click () {
      this.inHopDongParam.ma_gd = this.txtMaGD
      this.inHopDongParam.ngaylap_hd = this.dtpNgayLHD
      this.inHopDongParam.ngay_yc = this.dtpNgayYeuCau
      this.inHopDongParam.hdkh_id = this.hdkh_id
      this.$refs.frmInHopDongRef.showModal()
    },
    async tsbtnThemTB_Click () {
      if (!this.txtSoMayTBMoi) {
        this.cboDichVuVT === DichVuVienThong.CO_DINH || this.cboDichVuVT === DichVuVienThong.IMS
          ? this.$root.$toast.warning('Hãy nhập số máy mới cần thay đổi')
          : this.$root.$toast.warning('Hãy nhập Account mới cần thay đổi')
        this.$refs.txtSoMayTBMoiRef.focus()
        return false
      }
      if (!this.txtMaTB) {
        this.$root.$toast.warning('Hãy nhập số máy cũ của bạn')
        this.$refs.txtMaTBRef.focus()
        return false
      }
      if (this.loaitb_id === LoaiHinhTB.ISDN2B_CD || this.loaitb_id === LoaiHinhTB.ISDN2B_CQ ||
          this.loaitb_id === LoaiHinhTB.ISDN30B_CD || this.loaitb_id === LoaiHinhTB.ISDN30B_CQ
      ) {
        if (this.cboKieuLDId === KieuLapDat.DOISO_CHINH_ISDN && !this.isdn_sochinh) {
          this.$root.$toast.warning(`Số máy ${this.txtMaTB} là số phụ. Bạn không thể thực hiện nghiệp vụ đổi số chính ISDN!`)
        }
      }
      this.loading(true)
      try {
        const tienKMUpperKey = this.ds_tien_khoanmuc.map(element => this.getObjectUpperKey(element))
        const response = await this.lapHopDongDoiSo(
          2,
          JSON.stringify([{
            HDTB_ID: this.hdtb_id,
            HDKH_ID: this.hdkh_id,
            KIEULD_ID: this.cboKieuLDId,
            MA_TB: this.txtSoMayTBMoi,
            HANDATCHO: this.dtpHanDatCho,
            GHICHU: this.txtGhiChu,
            DONVI_ID: this.donViId
          }]),
          JSON.stringify(tienKMUpperKey)
        )
        if (response.error_code === 'BSS-00000000' && response.error === '200') {
          if (response.data.includes('OK')) {
            this.$root.$toast.success('Thêm thuê bao thành công')
            let [status, ...maGD] = response.data.split('_')
            this.txtMaGD = maGD.join('_')
            await this.HTTT_VAO_DATAGRID()
          } else {
            this.$root.$toast.error(response.data)
          }
        } else {
          this.$root.$toast.error(response.message)
        }
      } catch (e) {
        this.$root.$toast.error('Không thể thực hiện thêm thuê bao')
      } finally {
        this.loading(false)
      }
    },
    async tsbtnXoaTB_Click () {
      if (this.dgrThueBao.dataSources.length === 1) {
        this.$root.$toast.warning('Phụ lục này hiện đang có 1 thuê bao, bạn hãy thực hiện chức năng xóa hợp đồng!')
        return false
      }
      this.loading(true)
      try {
        await this.xoaThueBao()
        await this.HTTT_VAO_DATAGRID()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async xoaThueBao () {
      try {
        const response = await api.xoaThueBao(this.axios, { hdtb_id: this.hdtb_id })
        const data = await response.data
        data.error_code === 'BSS-00000000' && data.error === '200'
          ? this.$root.$toast.success('Xóa thuê bao thành công')
          : this.$root.$toast.error(data.message)
      } catch (e) {
        this.$root.$toast.error('Không thể xóa thuê bao')
      }
    },
    tsbtnThongTinLH_Click () {
      if (this.hdkh_id !== 0) this.$refs.frmThongTinLienHeRef.show()
    },
    tsbtnEmail_Click () {
      if (this.hdkh_id !== 0) this.$refs.frmThongTinEmailRef.show()
    },
    async getDanhSachLoaiHinhTB () {
      try {
        const response = await api.getDanhSachLoaiHinhTB(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách loại hình thuê bao')
        return []
      }
    },
    async getDanhSachKieuLD (loaiHdId, loaiTbId) {
      try {
        const response = await api.getDanhSachKieuLD(this.axios, { loaihd_id: loaiHdId, loaitb_id: loaiTbId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách kiểu lắp đặt')
        return []
      }
    }
  },
  watch: {
    async cboDichVuVTId (val) {
      if (!val) return false
      this.dichvuvt_id = val
      this.loading(true)
      try {
        const cboLoaiHinhRes = await this.getDanhSachLoaiHinhTB()
        if (cboLoaiHinhRes.length > 0) {
          this.cboLoaihinhTB = cboLoaiHinhRes.filter(element => +element.DICHVUVT_ID === val)
          this.cboLoaihinhTBId = +this.cboLoaihinhTB[0].LOAITB_ID
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
      if (this.dichvuvt_id === DichVuVienThong.ADSL) {
        this.cboLoaihinhTBId = LoaiHinhTB.INTERNET_ADSL
        this.btnKhoSoDisabled = false
        this.txtSoMayTBMoiReadOnly = false
        this.dtpHanDatChoDisabled = false
      } else {
        this.btnKhoSoDisabled = true
        this.txtSoMayTBMoiReadOnly = true
        this.dtpHanDatChoDisabled = true
      }
    },
    async cboTrangBiId (val) {
      if (this.cboTrangBi.length > 0) {
        if (!val) return false
        this.trangbi_id = val
        this.loading(true)
        try {
          await this.LayTienTheoKhoanMuc()
        } catch (e) {
        } finally {
          this.loading(false)
        }
      }
    },
    async cboLoaihinhTBId (val) {
      if (this.cboLoaihinhTB.length > 0) {
        if (!val) return false
        this.loaitb_id = val
        this.loading(true)
        try {
          this.cboKieuLD = await this.getDanhSachKieuLD(LoaiHopDong.DOISO_TB, this.loaitb_id)
          if (this.cboKieuLD.length > 0) {
            this.cboKieuLDId = +this.cboKieuLD[0].kieuld_id
          }
        } catch (e) {
        } finally {
          this.loading(false)
        }
      }
    },
    async cboKieuLDId (val) {
      if (this.cboKieuLD.length > 0) {
        if (!val) return false
        this.loading(true)
        try {
          await this.LayTienTheoKhoanMuc()
        } catch (e) {
          this.$root.$toast.error('Không thể lấy danh sách tiền theo khoản mục')
        } finally {
          this.loading(false)
        }
      } else {
        this.dtienld = 0
        this.dvatld = 0
      }
    }
  }
}
</script>
