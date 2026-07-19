<template src="./ModifyContractMoney.html"></template>
<style src="./style.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import { KHOANMUC_TT, DUNG_THU, TRANGTHAI_DONGBO, KieuLapDat } from '../../../contract/setup/InstallNewSubs/Enums'
import DanhSachBDTBPopup from './popup/DanhSachBDTBPopup'
import TienKMCuPopup from './popup/TienKMCuPopup'
import { LoaiHinhTB } from '../../../../const/enums'
import moment from 'moment'
import MuaThietBiPopup from './popup/MuaThietBiPopup'

export default {
  name: 'ModifyContractMoney',
  components: { breadcrumb, DanhSachBDTBPopup, TienKMCuPopup, MuaThietBiPopup },
  data() {
    return {
      header: {
        title: 'Điều chỉnh tiền hợp đồng',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      actions: [
        { id: 'tsbtnNhapMoi', icon: 'one-file-plus', name: 'Nhập mới' },
        { id: 'tsbtnGhiLai', icon: 'one-save', name: 'Ghi lại' }
      ],
      txtMaGD: '',
      txtLoaiHD: '',
      txtMa_TB: '',
      txtNgayYC: '',
      txtNgayLHD: '',
      txtKieuLD: '',
      txtTenTB: '',
      txtDiaChiLD: '',
      txtTenKH: '',
      txtDTLienLac: '',
      txtDiachiKH: '',
      txtNguoiLHD: '',
      txtGhiChu: '',
      txtTongTien: 0,
      txtTongVat: 0,
      txtTongTienHD: 0,
      ht_kmtt: false,
      kiemtra_khop_tien_datcoc: false,
      ht_ctmuatbi_v2: false,
      vTDTT_HDTB_ID: 0,
      loaihd_id: 0,
      kieuld_id: 0,
      tocdo_id: 0,
      muccuoc_id: 0,
      trangbi_id: 0,
      loaitb_id: 0,
      phieu_id: 0,
      hdkh_id: 0,
      hdtb_id: 0,
      thuebao_id: 0,
      dichvuvt_id: 0,
      doituong_id: 0,
      ds_tien_khoanmuc: [],
      ds_tien_khoanmuc_bd: [],
      cboKhuyenMai: [],
      cboKhuyenMaiId: null,
      cboTraTruoc: [],
      cboTraTruocId: null,
      m_dsThueBao_DichVu: [],
      dsKM_HDTB: [],
      dsDC_HDTB: [],
      lvDvgt: [],
      dsCTM_TBI: [],
      dtienld: 0,
      dvatld: 0,
      dtiencd: 0,
      dvatcd: 0,
      dtien_dv: 0,
      dvat_dv: 0,
      dtienkmld: 0,
      dvatkmld: 0,
      dtienkmdc: 0,
      dvatkmdc: 0,
      dtiendatcoc_tt: 0,
      dvatdatcoc_tt: 0,
      dtiendatcoc_km: 0,
      tyle_kmld: 0,
      chkKhuyenMai: false,
      chkTraTruoc: false,
      dsBDTBProp: [],
      tien_tbi: 0,
      vat_tbi: 0,
      tien_kmtbi: 0,
      vat_kmtbi: 0,
      tien_tgtb_tragop: 0,
      vat_tgtb_tragop: 0,
      tien_tgtb_tratruoc: 0,
      vat_tgtb_tratruoc: 0,
      tyle_kmld_tt: 0,
      vtyle_vat_dnhm: 0,
      vtyle_vat_id_dnhm: 0,
      vtyle_vat_cd: 0,
      vtyle_vat_id_cd: 0,
      ma_tk: '',
      disabledBtnEditTBi: false,
      mayCN: '',
      ipCN: '',
      lvMuaThietbi: [],
      dtTMP_KMTBI: [],
      dsCTTHD: [
        {
          CT_TIENHD_ID: null,
          TIEN: null,
          VAT: null,
          LOAI_ID: null,
          ID: null,
          HDTB_ID: null,
          KHOANMUCTT_ID: null,
          PHIEUTT_ID: null,
          TYLE_VAT: null,
          TYLE_VAT_ID: null
        }
      ],
      dsTDTT_HD_THUEBAO: [],
      thongTinNguoiDung: null,
      popupComponent: null,
      popupComponentData: null,
      popupComponentEvts: {
        'form-close': this.popupClosed
      },
      gridThueBao: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true },
          { fieldName: 'matb_tn', headerText: 'Mã TN', allowFiltering: true },
          { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true },
          { fieldName: 'tongtien', headerText: 'Tổng tiền', allowFiltering: true },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true },
          {
            fieldName: 'trangthai_hd',
            headerText: 'Trạng thái HĐ',
            allowFiltering: true,
            customAttributes: { class: 'text-trangthaihd' }
          },
          { fieldName: 'ngay_thoai', headerText: 'Ngày thoái', allowFiltering: true },
          { fieldName: 'trangthai_kh', headerText: 'Trạng thái KH', allowFiltering: true },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
          { fieldName: 'diachi_dau', headerText: 'Địa chỉ LĐ đầu', allowFiltering: true },
          { fieldName: 'diachi_cuoi', headerText: 'Địa chỉ LĐ cuối', allowFiltering: true },
          { fieldName: 'gio_conlai', headerText: 'Giờ còn lại', allowFiltering: true },
          { fieldName: 'ten_dt', headerText: 'Đối tượng', allowFiltering: true },
          { fieldName: 'tocdo', headerText: 'Tốc độ', allowFiltering: true },
          { fieldName: 'hinhthuc', headerText: 'HT chuyển đổi', allowFiltering: true },
          { fieldName: 'ngay_kt', headerText: 'Ngày hết hạn', allowFiltering: true },
          { fieldName: 'tg_thue_tu', headerText: 'TG thuê từ', allowFiltering: true },
          { fieldName: 'tg_thue_den', headerText: 'TG thuê đến', allowFiltering: true },
          { fieldName: 'ngay_kh', headerText: 'Ngày kích hoạt', allowFiltering: true },
          { fieldName: 'ngay_ht', headerText: 'Ngày HT', allowFiltering: true },
          { fieldName: 'ngay_ins', headerText: 'Ngày nghiệm thu', allowFiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true },
          { fieldName: 'congnghe', headerText: 'Công nghệ', allowFiltering: true },
          { fieldName: 'ungdung_nt', headerText: 'Ứng dụng NT', allowFiltering: true }
        ],
        dataSources: [],
        selectedItem: null
      },
      slChaMuaTBiProp: 0
    }
  },
  computed: {
    phanVungId() {
      return +this.$root.token.getPhanVungID()
    },
    nguoiDungId() {
      return +this.$root.token.getNguoiDungID()
    },
    ds_CTKM() {
      let sDs = '-1'
      for (const elm of this.dsKM_HDTB) {
        sDs += `,${elm.chitietkm_id}`
      }
      for (const elm of this.dsDC_HDTB) {
        sDs += `,${elm.chitietkm_id}`
      }
      return sDs
    },
    lvMuaThietbiText() {
      let sDs = ''
      for (const elm of this.lvMuaThietbi) {
        sDs += `${elm.loai_tbi} - ${elm.tien}\n`
      }
      return sDs
    },
    lvDvgtText() {
      return this.lvDvgt.join(', ')
    },
    maNguoiDung() {
      return this.thongTinNguoiDung ? this.thongTinNguoiDung.ma_nd : ''
    }
  },
  async created() {
    this.loading(true)
    try {
      const [nguoiDungResponse, thongTinCNResponse, frmLoadResponse] = await Promise.all([this.getThongTinNguoiDung2(), this.getThongTinCN(), this.frmSuaTien_HD_Load()])
      this.thongTinNguoiDung = nguoiDungResponse
      this.mayCN = thongTinCNResponse ? thongTinCNResponse.mayCn : ''
      this.ipCN = thongTinCNResponse ? thongTinCNResponse.ipCn : ''
    } catch (e) {
    } finally {
      this.loading(false)
    }
  },
  methods: {
    async getThongTinNguoiDung2() {
      try {
        const response = await api.getThongTinNguoiDung2(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' ? data.data : null
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin người dùng')
        return null
      }
    },
    async getThongTinCN() {
      try {
        const response = await api.getThongTinCN(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : null
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin cập nhật')
        return null
      }
    },
    async frmSuaTien_HD_Load() {
      try {
        const ds = await this.getDanhSachThamSoMacDinh()
        if (ds.length > 0) {
          const HT_KMTT = this.getThamSoByMaThamSo(ds, 'HT_KMTT')
          if (HT_KMTT && HT_KMTT.ten_ts.trim() === '1') this.ht_kmtt = true
          const KIEMTRA_KHOP_TIEN_DATCOC = this.getThamSoByMaThamSo(ds, 'KIEMTRA_KHOP_TIEN_DATCOC')
          if (KIEMTRA_KHOP_TIEN_DATCOC && KIEMTRA_KHOP_TIEN_DATCOC.ten_ts.trim() === '1') this.kiemtra_khop_tien_datcoc = true
          const HT_CTMUATBI_V2 = this.getThamSoByMaThamSo(ds, 'HT_CTMUATBI_V2')
          if (HT_CTMUATBI_V2 && HT_CTMUATBI_V2.ten_ts.trim() === '1') this.ht_ctmuatbi_v2 = true
        }
      } catch (e) {}
    },
    async getDanhSachThamSoMacDinh() {
      try {
        const response = await api.getDanhSachThamSoMacDinh(this.axios, { p_kieu_id: 0 })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách tham số mặc định')
        return []
      }
    },
    getThamSoByMaThamSo(ds, maTS) {
      return ds.find((elm) => elm.ma_ts === maTS)
    },
    async txtMaGD_KeyPress() {
      if (!this.txtMaGD) return false
      this.ma_tk = this.txtMaGD
      this.ClearWithoutTxtMaGD()
      this.loading(true)
      try {
        const ds = await this.getDanhSachHopDongByMaTB()
        if (ds.length === 0) {
          this.Clear()
          this.$root.$toast.warning('Không tìm thấy hợp đồng')
          this.$refs.txtMaGDRef.focus()
          return false
        }
        if (ds.length === 1) {
          const item = ds[0]
          this.hdkh_id = +item.hdkh_id
          this.txtDTLienLac = item.so_dt
          this.txtMaGD = item.ma_gd
          this.txtTenKH = item.ten_kh
          this.txtDiachiKH = item.diachi_kh
          this.txtNgayLHD = moment(item.ngaylap_hd, 'DD/MM/YYYY').format('DD/MM/YYYY')
          this.txtNgayYC = moment(item.ngay_yc, 'DD/MM/YYYY').format('DD/MM/YYYY')
          this.txtLoaiHD = item.ten_loaihd
          this.loaihd_id = +item.loaihd_id || 0
          this.txtNguoiLHD = item.ten_nv || ''
          await this.LoadDS_HopDongTB(this.hdkh_id)
        } else {
          this.dsBDTBProp = ds
          this.$refs.danhSachBDTBPopupRef.showModal()
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachHopDongByMaTB() {
      try {
        const response = await api.getDanhSachHopDongByMaTB(this.axios, { ma_tb: this.txtMaGD })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng')
        return []
      }
    },
    async LoadDS_HopDongTB(hdkhId) {
      let tongTien = 0
      this.txtTongTienHD = 0
      try {
        const myDataTables = await this.getDanhSachHDTBByHDKHId(hdkhId)
        this.gridThueBao.dataSources = myDataTables
        if (myDataTables.length > 0) {
          const location = myDataTables.findIndex((elm) => elm.ma_tb === this.ma_tk)
          if (location !== -1) {
            this.$refs.gridThueBaoRef.setCurrentSelectedRow(location)
            this.gridThueBao.selectedItem = this.gridThueBao.dataSources[location]
          } else {
            this.$refs.gridThueBaoRef.setCurrentSelectedRow(0)
            this.gridThueBao.selectedItem = this.gridThueBao.dataSources[0]
          }
        }
        await this.grvThuebao_SelectedIndexChanged()
        for (const elm of myDataTables) {
          tongTien += +elm.tongtien || 0
        }
        this.txtTongTienHD = tongTien
        this.LayTongTien_HDTB()
      } catch (e) {}
    },
    async getDanhSachHDTBByHDKHId(hdkhId) {
      try {
        const response = await api.getDanhSachHDTBByHDKHId(this.axios, { hdkh_id: hdkhId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách hợp đồng thuê bao')
        return []
      }
    },
    selectedRowChanged(item) {
      this.gridThueBao.selectedItem = { ...item }
    },
    async grvThuebao_SelectedIndexChanged() {
      const selectedItem = this.gridThueBao.selectedItem
      if (this.gridThueBao.dataSources.length === 0 || !selectedItem) {
        this.Clear()
        return false
      }
      this.txtMa_TB = selectedItem.ma_tb
      this.hdtb_id = +selectedItem.hdtb_id
      this.txtGhiChu = selectedItem.ghichu
      this.txtKieuLD = selectedItem.ten_kieuld
      this.dichvuvt_id = +selectedItem.dichvuvt_id
      this.txtTenTB = selectedItem.ten_tb
      this.txtDiaChiLD = selectedItem.diachi_ld
      this.loaitb_id = +selectedItem.loaitb_id
      this.doituong_id = +selectedItem.doituong_id
      this.kieuld_id = +selectedItem.kieuld_id
      this.tocdo_id = +selectedItem.tocdo_id || 0
      this.muccuoc_id = +selectedItem.muccuoc_id || 0
      this.trangbi_id = +selectedItem.trangbi_id || 0
      this.phuongld_id = 0
      this.phold_id = 0
      this.apld_id = 0
      this.khuld_id = 0
      const [dsDiaChi, dsKhoanMuc, dsTBDV, dsCTMTBi, dsTienHDKM, dsTemp, dsTempTT] = await Promise.all([
        this.getDanhSachDiaChiByHDTBId(this.hdtb_id),
        this.getDanhSachTienKhoanMuc(),
        this.getDanhSachThueBaoDichVu(this.loaitb_id,this.hdtb_id),
        this.getDanhSachMuaThietBi(this.hdtb_id),
        this.getTienHDKhuyenMaiHDTB(this.hdtb_id),
        this.getChiTietKMByHDTBId(this.hdtb_id, 0),
        this.getChiTietKMByHDTBId(this.hdtb_id, 1)
      ])
      //console.log(dsDiaChi, dsKhoanMuc, dsTBDV, dsCTMTBi, dsTienHDKM, dsTemp, dsTempTT)
      this.ds_tien_khoanmuc_bd = dsTienHDKM.map((elm) => {
        return { ...elm }
      })
      if (dsDiaChi.length > 0) {
        const diaChi = dsDiaChi[0]
        this.phuongld_id = +diaChi.phuong_id
        this.phold_id = +diaChi.pho_id
        this.apld_id = +diaChi.ap_id
        this.khuld_id = +diaChi.khu_id
      }
      this.ds_tien_khoanmuc = dsKhoanMuc
      this.m_dsThueBao_DichVu = dsTBDV
      this.dsCTM_TBI = dsCTMTBi
      this.lvMuaThietbi = dsCTMTBi
      await this.HT_DS_KhuyenMai_Combobox()
      await this.HT_DichVuGT()
      this.ds_tien_khoanmuc = dsTienHDKM.map((elm) => {
        return { ...elm }
      })
      for (const elm of this.ds_tien_khoanmuc) {
        const khoanMucTtId = +elm.khoanmuctt_id
        const tien = +elm.tien
        const vat = +elm.vat
        if (khoanMucTtId === KHOANMUC_TT.KMTT_DATMOI) {
          this.dtienld = tien
          this.dvatld = vat
        } else if (khoanMucTtId === KHOANMUC_TT.KMTT_CAIDAT) {
          this.dtiencd = tien
          this.dvatcd = vat
        } else if (khoanMucTtId === KHOANMUC_TT.KMTT_DICHVU) {
          this.dtien_dv = tien
          this.dvat_dv = vat
        } else if (khoanMucTtId === KHOANMUC_TT.KMTT_KM_LAPDAT) {
          this.dtienkmld = tien
          this.dvatkmld = vat
        } else if (khoanMucTtId === KHOANMUC_TT.KMTT_KM_DATCOC) {
          this.dtienkmdc = tien
          this.dvatkmdc = vat
        } else if (khoanMucTtId === KHOANMUC_TT.KMTT_DATCOC) {
          this.dtiendatcoc_tt = tien
          this.dvatdatcoc_tt = vat
        } else if (khoanMucTtId === KHOANMUC_TT.KMTT_DATCOC_KM) {
          this.dtiendatcoc_km = tien
        }
      }
      if (dsTemp.length > 0) {
        if (dsTemp[0].khuyenmai_id.toString()) {
          this.cboKhuyenMaiId = +dsTemp[0].khuyenmai_id
          this.chkKhuyenMai = true
        } else {
          this.chkKhuyenMai = false
        }
        this.dsKM_HDTB = [...dsTemp]
      } else {
        this.chkKhuyenMai = false
        this.dsKM_HDTB = []
      }
      if (dsTempTT.length > 0) {
        if (dsTempTT[0].khuyenmai_id.toString()) {
          this.cboTraTruocId = +dsTempTT[0].khuyenmai_id
          this.chkTraTruoc = true
        } else {
          this.chkTraTruoc = false
        }
        this.dsDC_HDTB = [...dsTempTT]
      } else {
        this.chkTraTruoc = false
        this.dsDC_HDTB = []
      }
    },
    ClearWithoutTxtMaGD() {
      this.vTDTT_HDTB_ID = 0
      this.chkKhuyenMai = false
      this.chkTraTruoc = false
      this.txtTongTien = 0
      this.txtTongTienHD = 0
      this.txtTongVat = 0
      this.txtNguoiLHD = ''
      this.txtTenTB = ''
      this.txtDiaChiLD = ''
      this.txtDTLienLac = ''
      this.lvDvgt = []
      this.lvMuaThietbi = []
      this.cboKhuyenMai = []
      this.cboKhuyenMaiId = null
      this.cboTraTruoc = []
      this.cboTraTruocId = null
      this.loaihd_id = 0
      this.kieuld_id = 0
      this.tocdo_id = 0
      this.muccuoc_id = 0
      this.trangbi_id = 0
      this.loaitb_id = 0
      this.phieu_id = 0
      this.hdkh_id = 0
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.dichvuvt_id = 0
      this.doituong_id = 0
      this.txtKieuLD = ''
      this.txtLoaiHD = ''
      this.txtGhiChu = ''
      this.txtTenKH = ''
      this.txtDiachiKH = ''
      this.txtNgayYC = ''
      this.txtNgayLHD = ''
      this.txtMa_TB = ''
      this.gridThueBao.dataSources = []
    },
    Clear() {
      this.txtMaGD = ''
      this.ClearWithoutTxtMaGD()
    },
    async getDanhSachDiaChiByHDTBId(hdtbId) {
      try {
        const response = await api.getDanhSachDiaChiByHDTBId(this.axios, hdtbId)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách địa chỉ')
        return []
      }
    },
    async getDanhSachTienKhoanMuc() {
      try {
        const response = await api.getDanhSachTienKhoanMuc(this.axios, { loaihd_id: 1 })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách tiền khoản mục')
        return []
      }
    },
    async HT_DS_KhuyenMai_Combobox() {
      const cboKhuyenMaiParam = {
        p_phanvung_id: this.phanVungId,
        vloaitb_id: this.loaitb_id,
        vkieuld_id: this.kieuld_id,
        vphuongld_id: this.phuongld_id,
        vkhoanmuctt_id: -1,
        vphold_id: this.phold_id,
        vapld_id: this.apld_id,
        vkhuld_id: this.khuld_id,
        vtrangbi_id: this.trangbi_id,
        vdoituong_id: this.doituong_id,
        vtocdo_id: this.tocdo_id,
        vmuccuoc_id: this.muccuoc_id,
        vloaikm: 0,
        vhdtb_id: this.hdtb_id
      }
      const cboTraTruocParam = { ...cboKhuyenMaiParam, vloaikm: 1 }
      const [cboKhuyemMaiResponse, cboTraTruocResponse] = await Promise.all([this.getDanhSachKhuyenMaiHDTB(cboKhuyenMaiParam), this.getDanhSachKhuyenMaiHDTB(cboTraTruocParam)])
      this.cboKhuyenMai = cboKhuyemMaiResponse
      if (this.cboKhuyenMai.length > 0) this.cboKhuyenMaiId = this.cboKhuyenMai[0].khuyenmai_id
      this.cboTraTruoc = cboTraTruocResponse
      if (this.cboTraTruoc.length > 0) this.cboTraTruocId = this.cboTraTruoc[0].khuyenmai_id
      this.chkKhuyenMai = this.chkTraTruoc = false
    },
    async getDanhSachKhuyenMaiHDTB(body) {
      try {
        const response = await api.getDanhSachKhuyenMaiHDTB(this.axios, body)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data.rs1 : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách khuyến mại')
        return []
      }
    },
    async getDanhSachThueBaoDichVu(loaitbId, hdtbId) {
      try {
        const response = await api.getDanhSachThueBaoDichVu(this.axios, {
          loaitb_id: loaitbId,
          hdtb_id: hdtbId,
          thuebao_id: 0,
          kieu_id: 1
        })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách thuê bao dịch vụ')
        return []
      }
    },
    async HT_DichVuGT() {
      let kt = false
      let tien_dv = 0
      let vat_dv = 0
      let tien_km_dvgt = 0
      let vat_km_dvgt = 0
      let dTyLe_KM_DVGT = 0
      let dtien_kmdvgt_tmp = 0
      let dvat_kmdvgt_tmp = 0
      let str = ''
      this.lvDvgt = []
      for (const elm of this.m_dsThueBao_DichVu) {
        if (elm.huy.toString() === '1' || elm.dangky.toString() === '1') {
          str = elm.ten_dvgt.toString()
          if (elm.dangky.toString() === '1' && elm.huy.toString() === '0') {
            kt = false
            if (elm.cuoc_ld.toString()) tien_dv += +elm.cuoc_ld
            if (elm.vat_ld.toString()) vat_dv += +elm.vat_ld
            str += ' (ĐK)'
            const dtTMP_KMDVGT = await this.getDanhSachDVGT(this.ds_CTKM, elm.dichvugt_id)
            if (dtTMP_KMDVGT.length > 0) dTyLe_KM_DVGT = +dtTMP_KMDVGT[0].tyle_giamcuoc_ld
            else {
              dtien_kmdvgt_tmp = 0
              dvat_kmdvgt_tmp = 0
              dTyLe_KM_DVGT = 0
            }
            if (dTyLe_KM_DVGT > 0) {
              dtien_kmdvgt_tmp = Math.round((+elm.cuoc_ld * dTyLe_KM_DVGT) / 100)
              dvat_kmdvgt_tmp = Math.round((+elm.vat_ld * dTyLe_KM_DVGT) / 100)
            }
            if (dtTMP_KMDVGT.length > 0) {
              elm.cuoc_kmld = dtien_kmdvgt_tmp
              elm.vat_kmld = dvat_kmdvgt_tmp
              tien_km_dvgt += dtien_kmdvgt_tmp
              vat_km_dvgt += dvat_kmdvgt_tmp
            }
          }
          if (elm.huy.toString() === '1') str += ' (Hủy)'
          this.lvDvgt.push(str)
        }
      }
      if (kt) {
        tien_dv = 0
        vat_dv = 0
      }
      this.HienThiTienDichVu(tien_dv, vat_dv)
      this.HienThiTienKhuyenMai_DVGT(tien_km_dvgt, vat_km_dvgt)
    },
    async getDanhSachDVGT(ctkmId, dvgtId) {
      try {
        const response = await api.getDanhSachDVGT(this.axios, {
          vphanvung_id: this.phanVungId,
          p_ds_ctkm_id: ctkmId,
          p_dichvugt_id: dvgtId
        })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data.rs1 : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách dịch vụ gia tăng')
        return []
      }
    },
    HienThiTienDichVu(tien_dv, vat_dv) {
      //console.log(ds_tien_khoanmuc,KHOANMUC_TT.KMTT_DICHVU);
      for (const elm of this.ds_tien_khoanmuc) {
        if (+elm.khoanmuctt_id === KHOANMUC_TT.KMTT_DICHVU) {
          elm.tien = tien_dv
          elm.vat = vat_dv
        }
      }
      this.LayTongTien_HDTB()
    },
    HienThiTienKhuyenMai_DVGT(tien_kmdvgt, vat_kmdvgt) {
      for (const elm of this.ds_tien_khoanmuc) {
        if (+elm.khoanmuctt_id === KHOANMUC_TT.KMTT_KM_DVGT) {
          if (tien_kmdvgt > 0) {
            elm.tien = tien_kmdvgt * -1
            elm.vat = vat_kmdvgt * -1
          } else {
            elm.tien = tien_kmdvgt
            elm.vat = vat_kmdvgt
          }
        }
      }
      this.LayTongTien_HDTB()
    },
    LayTongTien_HDTB() {
      let dtongtien = 0
      let dtongvat = 0
      console.log(123)
      console.log(this.ds_tien_khoanmuc)
      for (const elm of this.ds_tien_khoanmuc) {
        const khoanmucttId = +elm.khoanmuctt_id
        if (khoanmucttId === KHOANMUC_TT.KMTT_KM_LAPDAT || khoanmucttId === KHOANMUC_TT.KMTT_KM_DATCOC) {
          if (+elm.tien < 0) {
            dtongtien += +elm.tien
            dtongvat += +elm.vat
          } else {
            dtongtien += +elm.tien * -1
            dtongvat += +elm.vat * -1
          }
        } else if (khoanmucttId !== KHOANMUC_TT.KMTT_TRAGOP_THIETBI && khoanmucttId !== KHOANMUC_TT.KMTT_KM_THIETBI && khoanmucttId !== KHOANMUC_TT.KMTT_MUATHIETBI) {
          dtongtien += +elm.tien
          dtongvat += +elm.vat
        }
      }
      console.log(124)
      this.txtTongTien = dtongtien
      this.txtTongVat = dtongvat
      this.txtTongTienHD = dtongtien + dtongvat
    },
    async getDanhSachMuaThietBi(hdtbId) {
      try {
        const response = await api.getDanhSachMuaThietBi(this.axios, { hdtb_id: hdtbId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách mua thiết bị')
        return []
      }
    },
    async getTienHDKhuyenMaiHDTB(hdtbId) {
      try {
        const response = await api.getTienHDKhuyenMaiHDTB(this.axios, { vphanvung_id: this.phanVungId, vhdtb_id: hdtbId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data.rs1 : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách tiền khuyến mại')
        return []
      }
    },
    async getChiTietKMByHDTBId(hdtbId, loaiKM) {
      try {
        const response = await api.getChiTietKMByHDTBId(this.axios, hdtbId, loaiKM)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy chi tiết khuyến mại')
        return []
      }
    },
    async onOkBDTB(data) {
      this.$refs.danhSachBDTBPopupRef.hideModal()
      if (data.chapnhan) {
        const item = data.item
        this.hdkh_id = +item.hdkh_id
        this.txtDTLienLac = item.so_dt
        this.txtMaGD = item.ma_gd
        this.txtTenKH = item.ten_kh
        this.txtDiachiKH = item.diachi_kh
        this.txtNgayLHD = moment(item.ngaylap_hd, 'DD/MM/YYYY').format('DD/MM/YYYY')
        this.txtNgayYC = moment(item.ngay_yc, 'DD/MM/YYYY').format('DD/MM/YYYY')
        this.txtLoaiHD = item.ten_loaihd
        this.loaihd_id = +item.loaihd_id || 0
        this.txtNguoiLHD = item.ten_nv || ''
        this.loading(true)
        try {
          await this.LoadDS_HopDongTB(this.hdkh_id)
        } catch (e) {
        } finally {
          this.loading(false)
        }
      }
    },
    async chkKhuyenMai_CheckedChanged() {
      if (this.kieuld_id === 13224) return false
      if (this.doituong_id === DUNG_THU.DOITUONG_TB) return false
      if (!this.chkKhuyenMai) {
        this.dtiendatcoc_km = 0
        this.dtienkmld = 0
        this.dvatkmld = 0
        this.tyle_kmld = 0
        this.dsKM_HDTB = []
      }
      this.loading(true)
      try {
        await this.HT_ThietBi()
        await this.HT_DichVuGT()
        this.HienThiTienKhuyenMaiLapDat(this.dtiendatcoc_tt)
        this.LayTongTien_HDTB()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async HT_ThietBi() {
      this.loading(true)
      try {
        let vsl_cha = -1
        if (this.loaitb_id === LoaiHinhTB.IOFFICE) {
          const tsnd = await this.getThongTinHdtbCntt(this.hdtb_id, 1)
          if (tsnd !== '-1') vsl_cha = +tsnd
        } else if (this.loaitb_id === LoaiHinhTB.VNPT_MEETING || this.loaitb_id === LoaiHinhTB.HNTH_NGN || this.loaitb_id === LoaiHinhTB.VNPT_MCAP || this.loaitb_id === LoaiHinhTB.VNPT_LIS) {
          vsl_cha = await this.getThongTinHdtbCntt(this.hdtb_id, 2)
          if (this.loaitb_id === LoaiHinhTB.VNPT_MCAP) {
            const jsl_cha_thang = await this.getThongTinHdtbCntt(this.hdtb_id, 3)
            if (jsl_cha_thang % 12 === 0) vsl_cha *= jsl_cha_thang / 12
          }
        }
        let kt = true
        this.tien_tbi = 0
        this.vat_tbi = 0
        this.tien_kmtbi = 0
        this.vat_kmtbi = 0
        this.tien_tgtb_tragop = 0
        this.vat_tgtb_tragop = 0
        this.tien_tgtb_tratruoc = 0
        this.vat_tgtb_tratruoc = 0
        let dTyLe_KMTB = 0
        let dtien_kmtbi_tmp = 0
        let dvat_kmtbi_tmp = 0
        let dtien_tbi_tmp = 0
        let dvat_tbi_tmp = 0
        let dtien_tgtb_tratruoc_tmp = 0
        let dvat_tgtb_tratruoc_tmp = 0
        let soluong = 1
        let str = ''
        this.lvMuaThietbi = []
        //console.log(this.dsCTM_TBI);
        // let ds = []
        // let strtem = ''
        // for (const elm of this.dsCTM_TBI) {
        //   ds.push(await this.getHtTtctThietbiLoaitbi(+elm.loaitbi_id))
        // }
        // console.log(ds);
        // for (const inx of ds) {
        //   if (inx.length > 0) this.lvMuaThietbi.push(inx[0].loai_tbi.toString())
        //   kt = false
        //   soluong = soluong + inx[0][0].soluong
        //   console.log(soluong)
        //   console.log(inx.soluong)
        //   console.log(inx[0].soluong)
        //   console.log(inx[0][0].soluong, '--', soluong)
        // }
        for (const elm of this.dsCTM_TBI) {
          const ds = await this.getHtTtctThietbiLoaitbi(+elm.loaitbi_id)

          if (ds.length > 0) str = ds[0].loai_tbi.toString()
          this.lvMuaThietbi.push(str)
          kt = false
          soluong = +elm.soluong
          //console.log(elm.tien.toString())
          if (elm.tien.toString() !== '') {
            dtien_tbi_tmp = +elm.tien
            this.tien_tbi += dtien_tbi_tmp * soluong
          }

          if (elm.vat.toString() !== '') {
            dvat_tbi_tmp = +elm.vat
            this.vat_tbi += dvat_tbi_tmp * soluong
          }
          //console.log(vsl_cha)
          if (vsl_cha != -1) {
            const blockDau = +elm.block_dau
            const blockTiep = +elm.block_tiep
            if (elm.block_tiep.toString() && elm.gia_block_tiep.toString() && elm.gia_block_tiep.toString() !== '0') {
              if (this.loaitb_id === LoaiHinhTB.IOFFICE || this.loaitb_id === LoaiHinhTB.HNTH_NGN || this.loaitb_id === LoaiHinhTB.VNPT_MEETING || this.loaitb_id === LoaiHinhTB.VNPT_MCAP || this.loaitb_id === LoaiHinhTB.VNPT_LIS) {
                if (vsl_cha > blockDau) {
                  const vsl_conlai = vsl_cha - blockDau
                  const t2 = vsl_conlai % blockTiep
                  const vsl_bl = t2 > 0 ? vsl_conlai / blockTiep + 1 : vsl_conlai / blockTiep
                  const tien_block = vsl_bl * +elm.gia_block_tiep
                  dtien_tbi_tmp += tien_block
                  this.tien_tbi += tien_block * soluong
                }
              }
            }
            if (elm.block_tiep.toString() && elm.vat_block_tiep.toString() && elm.vat_block_tiep.toString() !== '0') {
              if (this.loaitb_id === LoaiHinhTB.IOFFICE || this.loaitb_id === LoaiHinhTB.HNTH_NGN || this.loaitb_id === LoaiHinhTB.VNPT_MEETING || this.loaitb_id === LoaiHinhTB.VNPT_MCAP || this.loaitb_id === LoaiHinhTB.VNPT_LIS) {
                if (vsl_cha > blockDau) {
                  const vsl_conlai = vsl_cha - blockDau
                  const t3 = vsl_conlai % blockTiep
                  const rsl_bl = t3 > 0 ? vsl_conlai / blockTiep + 1 : vsl_conlai / blockTiep
                  const vat_block = rsl_bl * +elm.vat_block_tiep
                  dvat_tbi_tmp += vat_block
                  this.vat_tbi += vat_block * soluong
                }
              }
            }
          }
          this.dtTMP_KMTBI = await this.getCTKMLoaiTbi(+elm.loaitbi_id)
          if (this.dtTMP_KMTBI) {
            if (this.dtTMP_KMTBI.length > 0) {
              dtien_kmtbi_tmp = +this.dtTMP_KMTBI[0].tien_km
              dvat_kmtbi_tmp = +this.dtTMP_KMTBI[0].vat_km
              dTyLe_KMTB = +this.dtTMP_KMTBI[0].tyle_km
              //gom 2 cai if cung dieu kien thanh 1
              elm.tien_km = dtien_kmtbi_tmp
              elm.vat_km = dvat_kmtbi_tmp
              this.tien_kmtbi += dtien_kmtbi_tmp * soluong
              this.vat_kmtbi += dvat_kmtbi_tmp * soluong
            } else {
              dtien_kmtbi_tmp = 0
              dvat_kmtbi_tmp = 0
              dTyLe_KMTB = 0
            }
          } else {
            dtien_kmtbi_tmp = 0
            dvat_kmtbi_tmp = 0
            dTyLe_KMTB = 0
          }

          if (dTyLe_KMTB > 0) {
            dtien_kmtbi_tmp = Math.round((dtien_tbi_tmp * dTyLe_KMTB) / 100)
            dvat_kmtbi_tmp = Math.round((dvat_tbi_tmp * dTyLe_KMTB) / 100)
          }
          // if (this.dtTMP_KMTBI.length > 0) {
          //   elm.tien_km = dtien_kmtbi_tmp
          //   elm.vat_km = dvat_kmtbi_tmp
          //   this.tien_kmtbi += dtien_kmtbi_tmp * soluong
          //   this.vat_kmtbi += dvat_kmtbi_tmp * soluong
          // }
          const dtTMP = await this.getDanhSachTraGopTbi(+elm.loaitbi_id)
          
          if (dtTMP) {
            if (dtTMP.length > 0) {
              if (elm.tien_tratruoc.toString()) dtien_tgtb_tratruoc_tmp = +elm.tien_tratruoc
              if (elm.vat_tratruoc.toString()) dvat_tgtb_tratruoc_tmp = +elm.vat_tratruoc
            } else {
              dtien_tgtb_tratruoc_tmp = dtien_tbi_tmp - dtien_kmtbi_tmp
              dvat_tgtb_tratruoc_tmp = dvat_tbi_tmp - dvat_kmtbi_tmp
              elm.tien_tratruoc = dtien_tgtb_tratruoc_tmp
              elm.vat_tratruoc = dvat_tgtb_tratruoc_tmp
            }
          }
          this.tien_tgtb_tratruoc += dtien_tgtb_tratruoc_tmp * soluong
          this.vat_tgtb_tratruoc += dvat_tgtb_tratruoc_tmp * soluong
          this.tien_tgtb_tragop += (dtien_tbi_tmp - Math.abs(dtien_kmtbi_tmp) - dtien_tgtb_tratruoc_tmp) * soluong
          this.vat_tgtb_tragop += (dvat_tbi_tmp - Math.abs(dvat_kmtbi_tmp) - dvat_tgtb_tratruoc_tmp) * soluong
        }

        if (kt) {
          this.tien_tbi = 0
          this.vat_tbi = 0
          this.tien_kmtbi = 0
          this.vat_kmtbi = 0
          this.tien_tgtb_tratruoc = 0
          this.vat_tgtb_tratruoc = 0
          this.tien_tgtb_tragop = 0
          this.vat_tgtb_tragop = 0
        }
        //console.log(this.tien_tbi,this.vat_tbi,this.tien_kmtbi,this.tien_tgtb_tratruoc,this.tien_tgtb_tragop)
        this.HienThiTienMuaThietBi(this.tien_tbi, this.vat_tbi)
        this.HienThiTienKhuyenMai_ThietBi(this.tien_kmtbi, this.vat_kmtbi)
        this.HienThiTienTraTruocThietBi(this.tien_tgtb_tratruoc, this.vat_tgtb_tratruoc)
        this.HienThiTienTraGopThietBi(this.tien_tgtb_tragop, this.vat_tgtb_tragop)
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    HienThiTienMuaThietBi(tienTbi, vatTbi) {
      for (const element of this.ds_tien_khoanmuc) {
        if (+element.khoanmuctt_id === KHOANMUC_TT.KMTT_MUATHIETBI) {
          element.tien = tienTbi
          element.vat = vatTbi
        }
      }
      this.LayTongTien_HDTB()
    },
    HienThiTienKhuyenMai_ThietBi(tienKMTB, vatKMTB) {
      for (const element of this.ds_tien_khoanmuc) {
        if (+element.khoanmuctt_id === KHOANMUC_TT.KMTT_KM_THIETBI) {
          if (tienKMTB > 0) {
            element.tien = tienKMTB * -1
            element.vat = vatKMTB * -1
          } else {
            element.tien = tienKMTB
            element.vat = vatKMTB
          }
        }
      }
      this.LayTongTien_HDTB()
    },
    HienThiTienTraTruocThietBi(tienTraTruocTbi, vatTraTruocTbi) {
      for (const element of this.ds_tien_khoanmuc) {
        if (+element.khoanmuctt_id === KHOANMUC_TT.KMTT_TRATRUOC_THIETBI) {
          element.tien = tienTraTruocTbi
          element.vat = vatTraTruocTbi
        }
      }
      this.LayTongTien_HDTB()
    },
    HienThiTienTraGopThietBi(tienTraGopTbi, vatTraGopTbi) {
      for (const element of this.ds_tien_khoanmuc) {
        if (+element.khoanmuctt_id === KHOANMUC_TT.KMTT_TRAGOP_THIETBI) {
          element.tien = tienTraGopTbi
          element.vat = vatTraGopTbi
        }
      }
      this.LayTongTien_HDTB()
    },
    HienThiTienKhuyenMaiLapDat(dtiendatcoc_tt) {
      let dtienkmld_tmp = this.dtienkmld
      let dvatkmld_tmp = this.dvatkmld
      let dtyle_kmld_tmp = this.tyle_kmld
      if (Math.abs(this.dtienkmld) < Math.abs(this.dtienkmdc)) {
        dtienkmld_tmp = this.dtienkmdc
        dvatkmld_tmp = this.dvatkmdc
      }
      if (dtyle_kmld_tmp < this.tyle_kmld_tt) dtyle_kmld_tmp = this.tyle_kmld_tt
      if (dtyle_kmld_tmp > 0) {
        dtienkmld_tmp = Math.round((this.dtienld * dtyle_kmld_tmp) / 100)
        dvatkmld_tmp = ((this.dtienld + this.dvatld) * dtyle_kmld_tmp) / 100 - dtienkmld_tmp
      }
      for (const elm of this.ds_tien_khoanmuc) {
        if (+elm.khoanmuctt_id === KHOANMUC_TT.KMTT_KM_LAPDAT) {
          if (this.dtienkmld < 0) {
            elm.tien = dtienkmld_tmp * -1
            elm.vat = dvatkmld_tmp * -1
          } else {
            elm.tien = dtienkmld_tmp
            elm.vat = dvatkmld_tmp
          }
        } else if (+elm.khoanmuctt_id === KHOANMUC_TT.KMTT_DATCOC) {
          elm.tien = Math.round(dtiendatcoc_tt)
          elm.vat = Math.round(this.dvatdatcoc_tt)
        } else if (+elm.khoanmuctt_id === KHOANMUC_TT.KMTT_DATCOC_KM) {
          elm.tien = Math.round((this.dtiendatcoc_km * 10) / 11)
          elm.vat = Math.round(this.dtiendatcoc_km / 11)
        }
      }
      this.LayTongTien_HDTB()
    },
    async chkTraTruoc_CheckedChanged() {
      if (this.kieuld_id === 13224) return false
      if (!this.chkTraTruoc) {
        this.dtienkmdc = 0
        this.dvatkmdc = 0
        this.dtiendatcoc_tt = 0
        this.dvatdatcoc_tt = 0
        this.tyle_kmld_tt = 0
        this.dsDC_HDTB = []
      }
      if (!this.chkKhuyenMai) {
        this.dtiendatcoc_km = 0
        this.dtienkmld = 0
        this.dvatkmld = 0
        this.tyle_kmld = 0
      }
      this.loading(true)
      try {
        await this.HT_ThietBi()
        await this.HT_DichVuGT()
        this.HienThiTienKhuyenMaiLapDat(this.dtiendatcoc_tt)
        this.LayTongTien_HDTB()
        if (this.doituong_id === DUNG_THU.DOITUONG_TB) this.disabledBtnEditTBi = !this.chkTraTruoc
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async getThongTinHdtbCntt(param, type) {
      try {
        const response = await api.getThongTinHdtbCntt(this.axios, { param, type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hdtb')
        return ''
      }
    },
    async getHtTtctThietbiLoaitbi(loaiTbiId) {
      try {
        const response = await api.getHtTtctThietbiLoaitbi(this.axios, { loaitbi_id: loaiTbiId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin danh sách thiết bị, loại thiết bị')
        return []
      }
    },
    async getCTKMLoaiTbi(loaiTbiId) {
      try {
        const response = await api.getCTKMLoaiTbi(this.axios, { ds_ctkm_id: this.ds_CTKM, loaitbi_id: loaiTbiId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin chi tiết khuyến mại loại thiết bị')
        return []
      }
    },
    async getDanhSachTraGopTbi(loaiTbiId) {
      try {
        const response = await api.getDanhSachTraGopTbi(this.axios, { p_ds_ctkm_id: this.ds_CTKM, p_loaitbi_id: loaiTbiId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin danh sách trả góp thiết bị')
        return []
      }
    },
    async onClickAction(id) {
      if (id === 'tsbtnNhapMoi') this.Clear()
      else await this.tsbtnGhiLai_Click()
    },
    async tsbtnGhiLai_Click() {
      if (this.hdtb_id === 0) {
        this.$root.$toast.warning('Bạn phải lấy thông tin hợp đồng trước khi sửa khuyến mại!')
        return false
      }
      this.loading(true)
      try {
        if (!(await this.KIEMTRA_SUAKM())) return false
        let blKiemTraTraGop = false
        for (const elm of this.dsDC_HDTB) {
          elm.hdtb_id = this.hdtb_id
          elm.tien_tg = await this.TongTien_TG(elm.chitietkm_id)
          if (elm.tien_tg > 0) blKiemTraTraGop = true
        }
        for (const elm of this.dsKM_HDTB) {
          elm.hdtb_id = this.hdtb_id
          if (!blKiemTraTraGop) elm.tien_tg = await this.TongTien_TG(elm.chitietkm_id)
        }
        let update_tien = false
        let vphieutt_id = -1
        let ds = []
        if (!this.kiemtra_khoanmuc_tien()) {
          console.log(11111111111111111)
          this.$confirm('Tiền hợp đồng đã thay đổi, bạn có muốn tiếp tục cập nhật?', 'Cập nhật tiền hợp đồng', { confirmButtonText: 'Có', cancelButtonText: 'Không' })
            .then(async () => {
              this.loading(true)
              try {
                ds = await this.getDanhSachPhieuChuaTT(this.hdtb_id)
                if (ds.length === 0) {
                  this.$root.$toast.warning('Hợp đồng đã thoái trả, không thể sửa tiền!')
                  return false
                }
                if (ds[0].trangthai.toString() === '1') {
                  let accepted = true
                  await this.$confirm('Phiếu thanh toán đã thu tiền sẽ chuyển thành trạng thái chưa thu tiền và hủy hóa đơn cũ, bạn có muốn sửa?', 'Cập nhật phiếu thanh toán', { confirmButtonText: 'Có', cancelButtonText: 'Không' })
                    .then()
                    .catch((e) => {
                      accepted = false
                    })
                    .finally(() => {
                      this.loading(false)
                    })
                  if (!accepted) return false
                }
              } catch (e) {
                this.$root.$toast.error('Không thể lấy thông tin phiếu thanh toán, xin thử lại sau!')
              } finally {
                this.loading(false)
              }
              vphieutt_id = +ds[0].phieutt_id
              update_tien = true
              this.loading(true)
              try {
                await this.TaoDuLieu_CT_TIENHD(true)
                console.log(125)
                console.log(this.ds_tien_khoanmuc)
                if (this.kiemtra_khop_tien_datcoc) {
                  let tienTraTruoc = 0
                  let tienDatCoc = 0
                  for (const element of this.ds_tien_khoanmuc) {
                    if (+element.khoanmuctt_id === KHOANMUC_TT.KMTT_DATCOC) tienTraTruoc = +element.tien + +element.vat
                  }
                  for (const element of this.dsKM_HDTB) {
                    tienDatCoc += +element.datcoc_csd
                  }
                  for (const element of this.dsDC_HDTB) {
                    tienDatCoc += +element.datcoc_csd
                  }
                  if (tienTraTruoc !== tienDatCoc) {
                    this.$root.$toast.warning('Tiền đặt cọc và tiền khoản mục không khớp. Liên hệ admin để kiểm tra lại!')
                    return false
                  }
                }
                await this.TaoDuLieu_TDTT_HD_THUEBAO()
                console.log(777777777777)
                console.log(this.dsCTTHD)
                for (const element of this.dsCTM_TBI) {
                  element.hdtb_id = this.hdtb_id
                }
                const dsKM = [...this.dsKM_HDTB, ...this.dsDC_HDTB]
                this.dsCTTHD.forEach((elm) => {
                  elm.PHIEUTT_ID = vphieutt_id
                })
                
                const response = await this.capNhatKMTienHd({
                  vphanvung_id: this.phanVungId,
                  p_phieutt_id: vphieutt_id,
                  p_hdtb_id: this.hdtb_id,
                  p_sua_tien: update_tien ? 1 : 0,
                  p_nguoidung_id: this.nguoiDungId,
                  p_ma_nd: this.maNguoiDung,
                  p_may_cn: this.mayCN,
                  p_ip_cn: this.ipCN,
                  p_json_ct_tienhd: JSON.stringify(this.dsCTTHD),
                  p_json_km_hdtb: JSON.stringify(dsKM),
                  p_json_ct_mua_tbi: JSON.stringify(this.dsCTM_TBI)
                })
                if (response.error_code === 'BSS-00000000' && response.error === '200' && response.data === 'OK') {
                  this.$root.$toast.success('Cập nhật thành công')
                  await this.txtMaGD_KeyPress()
                } else {
                  this.$root.$toast.error(response.data)
                }
              } catch (e) {
              } finally {
                this.loading(false)
              }
            })
            .catch((e) => {})
            .finally(() => {
              this.loading(false)
            })
        } else {
          console.log(22222222222222)
          await this.TaoDuLieu_CT_TIENHD(true)
          if (this.kiemtra_khop_tien_datcoc) {
            let tienTraTruoc = 0
            let tienDatCoc = 0
            for (const element of this.ds_tien_khoanmuc) {
              if (+element.khoanmuctt_id === KHOANMUC_TT.KMTT_DATCOC) tienTraTruoc = +element.tien + +element.vat
            }
            for (const element of this.dsKM_HDTB) {
              tienDatCoc += +element.datcoc_csd
            }
            for (const element of this.dsDC_HDTB) {
              tienDatCoc += +element.datcoc_csd
            }
            if (tienTraTruoc !== tienDatCoc) {
              this.$root.$toast.warning('Tiền đặt cọc và tiền khoản mục không khớp. Liên hệ admin để kiểm tra lại!')
              return false
            }
          }
          await this.TaoDuLieu_TDTT_HD_THUEBAO()
          for (const element of this.dsCTM_TBI) {
            element.hdtb_id = this.hdtb_id
          }
          ds = await this.getDanhSachPhieuChuaTT(this.hdtb_id)
          const dsKM = [...this.dsKM_HDTB, ...this.dsDC_HDTB]
          const response = await this.capNhatKMTienHd({
            vphanvung_id: this.phanVungId,
            p_phieutt_id: ds[0].phieutt_id,
            p_hdtb_id: this.hdtb_id,
            p_sua_tien: update_tien ? 1 : 0,
            p_nguoidung_id: this.nguoiDungId,
            p_ma_nd: this.maNguoiDung,
            p_may_cn: this.mayCN,
            p_ip_cn: this.ipCN,
            p_json_ct_tienhd: JSON.stringify(this.dsCTTHD),
            p_json_km_hdtb: JSON.stringify(dsKM),
            p_json_ct_mua_tbi: JSON.stringify(this.dsCTM_TBI)
          })
          if (response.error_code === 'BSS-00000000' && response.error === '200' && response.data === 'OK') {
            this.$root.$toast.success('Cập nhật thành công')
            await this.txtMaGD_KeyPress()
          } else {
            this.$root.$toast.error(response.data)
          }
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async KIEMTRA_SUAKM() {
      const tt_stb = this.chkTraTruoc ? this.cboTraTruocId : -1
      const km_stb = this.chkKhuyenMai ? this.cboKhuyenMaiId : -1
      const list_ctkm_id = (this.dsDC_HDTB.length > 0 || this.dsKM_HDTB.length) > 0 ? '' : '-1'
      let xdoc = '<ds>'
      for (const elm of this.dsDC_HDTB) {
        xdoc += `<vt ctkm_id="${elm.chitietkm_id}"></vt>`
      }
      for (const elm of this.dsKM_HDTB) {
        xdoc += `<vt ctkm_id="${elm.chitietkm_id}"></vt>`
      }
      xdoc += '</ds>'
      const temp = list_ctkm_id ? '' : xdoc
      const kq = await this.kiemTraThayDoiKM(km_stb, tt_stb, temp)
      if (!kq) {
        this.$root.$toast.warning('Không lấy được thông tin trả ra')
        return false
      }
      if (kq !== '1') {
        this.$root.$toast.warning(kq)
        return false
      }
      return true
    },
    async kiemTraThayDoiKM(kmSTB, ttSTB, listCTKM) {
      try {
        const response = await api.kiemTraThayDoiKM(this.axios, {
          list_ctkm: listCTKM,
          vhdtb_id: this.hdtb_id,
          vkhuyenmai_id_new: kmSTB,
          vma_nd: this.maNguoiDung,
          vphanvung_id: this.phanVungId,
          vtt_km_id_new: ttSTB
        })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : null
      } catch (e) {
        this.$root.$toast.error('Không thể kiểm tra thay đổi khuyến mại')
        return null
      }
    },
    async capNhatKMTienHd(body) {
      try {
        const response = await api.capNhatKMTienHd(this.axios, body)
        const data = await response.data
        return data
      } catch (e) {
        this.$root.$toast.error('Không thể cập nhật')
        return null
      }
    },
    async TongTien_TG(chitietkmId) {
      let dTien_TG = 0
      let dTien = 0
      let dTien_tt = 0
      let dTien_km = 0
      let dSoLuong = 0
      const dt = await this.getDsKMThietBiByCTKMId(chitietkmId)
      for (const element of dt) {
        const drs = this.dsCTM_TBI.filter((elm) => elm.loaitbi_id === element.loaitbi_id)
        for (const dr of drs) {
          dTien = +dr.tien
          dTien_tt = +dr.tien_tratruoc
          dTien_km = +dr.tien_km
          dSoLuong = +dr.soluong
          dTien_TG += (dTien - dTien_tt - dTien_km) * dSoLuong
        }
      }
      return dTien_TG
    },
    async getDsKMThietBiByCTKMId(chitietkmId) {
      try {
        const response = await api.getDsKMThietBiByCTKMId(this.axios, { vphanvung_id: this.phanVungId, p_chitietkm_id: chitietkmId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data.rs1 : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách khuyến mại thiết bị')
        return []
      }
    },
    kiemtra_khoanmuc_tien() {
      const drs_cu = this.ds_tien_khoanmuc_bd.filter((elm) => +elm.tien !== 0 && +elm.vat !== 0)
      const drs_moi = this.ds_tien_khoanmuc.filter((elm) => +elm.tien !== 0 && +elm.vat !== 0)
      if (drs_cu.length !== drs_moi.length) return false
      let vtien_bd
      let vvat_bd
      let vtien_moi
      let vvat_moi
      let kt = false
      for (const drmoi of drs_moi) {
        kt = false
        for (const drcu of drs_cu) {
          vtien_bd = 0
          vvat_bd = 0
          vtien_moi = 0
          vvat_moi = 0
          if (+drmoi.khoanmuctt_id === +drcu.khoanmuctt_id) {
            vtien_moi = +drmoi.tien
            vvat_moi = +drmoi.vat
            vtien_bd = +drcu.tien
            vvat_bd = +drcu.vat
            if (vtien_bd === vtien_moi && vvat_bd === vvat_moi) {
              kt = true
              break
            }
          }
        }
        if (!kt) return false
      }
      for (const drcu of drs_cu) {
        kt = false
        for (const drmoi of drs_moi) {
          vtien_bd = 0
          vvat_bd = 0
          vtien_moi = 0
          vvat_moi = 0
          if (+drmoi.khoanmuctt_id === +drcu.khoanmuctt_id) {
            vtien_moi = +drmoi.tien
            vvat_moi = +drmoi.vat
            vtien_bd = +drcu.tien
            vvat_bd = +drcu.vat
            if (vtien_bd === vtien_moi && vvat_bd === vvat_moi) {
              kt = true
              break
            }
          }
        }
        if (!kt) return false
      }
      return kt
    },
    async getDanhSachPhieuChuaTT(hdtbId) {
      try {
        const response = await api.getDanhSachPhieuChuaTT(this.axios, { vhdtb_id: hdtbId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin phiếu thanh toán, xin thử lại sau!')
        return []
      }
    },
    async TaoDuLieu_CT_TIENHD(themmoi) {
      this.dsCTTHD = []
      let rowCTTHD
      console.log('this.ds_tien_khoanmuc')
      console.log(this.ds_tien_khoanmuc)
      for (const km of this.ds_tien_khoanmuc) {
        console.log(km)
        if (+km.khoanmuctt_id === KHOANMUC_TT.KMTT_MUATHIETBI) {
          console.log(1)
          for (const tb of this.dsCTM_TBI) {
            rowCTTHD = { HDTB_ID: this.hdtb_id, KHOANMUCTT_ID: +km.khoanmuctt_id, PHIEUTT_ID: null }
            if (themmoi) rowCTTHD.CT_TIENHD_ID = await this.getKeys('CT_TIENHD')
            let tien = 0
            let vat = 0
            let soluong = +tb.soluong || 1
            if (this.loaitb_id === LoaiHinhTB.IOFFICE || this.loaitb_id === LoaiHinhTB.HNTH_NGN || this.loaitb_id === LoaiHinhTB.VNPT_MEETING) {
              if (tb.sl_cha.toString() && tb.sl_cha.toString() !== '0') {
                const h_sl_cha = +tb.sl_cha
                const h_block_dau = +tb.block_dau
                const h_block_tiep = +tb.block_tiep
                const h_gia_block = +tb.gia_block_tiep
                const h_vat_block = +tb.vat_block_tiep
                let h_sl_conlai = 0
                if (h_sl_cha >= h_block_dau) {
                  h_sl_conlai = h_sl_cha - h_block_dau
                  const t2 = h_sl_conlai % h_block_tiep
                  const h_sl_bl = t2 > 0 ? h_sl_conlai / h_block_tiep + 1 : h_sl_conlai / h_block_tiep
                  const tien_block = h_sl_bl * h_gia_block
                  const vat_block = h_sl_bl * h_vat_block
                  if (this.ht_ctmuatbi_v2) {
                    tien = +tb.tong_tien + tien_block * soluong
                    vat = +tb.tong_thue + vat_block * soluong
                  } else {
                    tien = (+tb.tien + tien_block) * soluong
                    vat = (+tb.vat + vat_block) * soluong
                  }
                }
              } else if (this.ht_ctmuatbi_v2) {
                tien = +tb.tong_tien
                vat = +tb.tong_thue
              } else {
                tien = +tb.tien * soluong
                vat = +tb.vat * soluong
              }
            } else if (this.ht_ctmuatbi_v2) {
              tien = +tb.tong_tien
              vat = +tb.tong_thue
            } else {
              tien = +tb.tien * soluong
              vat = +tb.vat * soluong
            }
            rowCTTHD.TIEN = tien
            rowCTTHD.VAT = vat
            rowCTTHD.ID = +tb.loaitbi_id
            rowCTTHD.LOAI_ID = 3
            rowCTTHD.TYLE_VAT = +tb.tyle_vat
            rowCTTHD.TYLE_VAT_ID = +tb.tyle_vat_id
            this.dsCTTHD.push(rowCTTHD)
          }
          console.log( this.dsCTTHD)
        } else if (+km.khoanmuctt_id === KHOANMUC_TT.KMTT_KM_THIETBI) {
           console.log(2)
          for (const tb of this.dsCTM_TBI) {
            rowCTTHD = {
              HDTB_ID: this.hdtb_id,
              KHOANMUCTT_ID: +km.khoanmuctt_id,
              ID: +tb.loaitbi_id,
              LOAI_ID: 3,
              TYLE_VAT: +tb.tyle_vat,
              TYLE_VAT_ID: +tb.tyle_vat_id,
              PHIEUTT_ID: null
            }
            if (themmoi) rowCTTHD.CT_TIENHD_ID = await this.getKeys('CT_TIENHD')
            const soluong = +tb.soluong || 1
            const tien = +tb.tien_km * soluong
            const vat = +tb.vat_km * soluong
            rowCTTHD.TIEN = tien ? Math.abs(tien) * -1 : 0
            rowCTTHD.VAT = vat ? Math.abs(vat) * -1 : 0
            this.dsCTTHD.push(rowCTTHD)
          }
           console.log( this.dsCTTHD)
        } else if (+km.khoanmuctt_id === KHOANMUC_TT.KMTT_TRATRUOC_THIETBI) {
           console.log(3)
          for (const tb of this.dsCTM_TBI) {
            rowCTTHD = {
              HDTB_ID: this.hdtb_id,
              KHOANMUCTT_ID: +km.khoanmuctt_id,
              ID: +tb.loaitbi_id,
              LOAI_ID: 3,
              TYLE_VAT: +tb.tyle_vat,
              TYLE_VAT_ID: +tb.tyle_vat_id,
              PHIEUTT_ID: null
            }
            if (themmoi) rowCTTHD.CT_TIENHD_ID = await this.getKeys('CT_TIENHD')
            const soluong = +tb.soluong || 1
            const tien = +tb.tien_tratruoc * soluong
            const vat = +tb.vat_tratruoc * soluong
            rowCTTHD.TIEN = tien || 0
            rowCTTHD.VAT = vat || 0
            this.dsCTTHD.push(rowCTTHD)
          }
           console.log( this.dsCTTHD)
        } else if (+km.khoanmuctt_id === KHOANMUC_TT.KMTT_DICHVU) {
          console.log(4)
          for (const dv of this.m_dsThueBao_DichVu) {
            if (dv.dangky.toString() === '1' && dv.huy.toString() === '0') {
              rowCTTHD = {
                HDTB_ID: this.hdtb_id,
                KHOANMUCTT_ID: KHOANMUC_TT.KMTT_DICHVU,
                TIEN: km.tien,// +dv.cuoc_ld || 0,
                VAT: km.vat, //+dv.vat_ld || 0,
                ID: +dv.dichvugt_id,
                LOAI_ID: 2,
                TYLE_VAT: km.tyle_vat, //+dv.tyle_vat,
                TYLE_VAT_ID: +dv.tyle_vat_id,
                PHIEUTT_ID: null
              }
              if (themmoi) rowCTTHD.CT_TIENHD_ID = await this.getKeys('CT_TIENHD')
              this.dsCTTHD.push(rowCTTHD)
            }
          }
           console.log( this.dsCTTHD)
        } else if (+km.khoanmuctt_id === KHOANMUC_TT.KMTT_KM_DVGT) {
          console.log(5)
          for (const dv of this.m_dsThueBao_DichVu) {
            if (dv.dangky.toString() === '1' && dv.huy.toString() === '0') {
              const tien = +dv.cuoc_kmld
              const vat = +dv.vat_kmld
              if (tien !== 0 && vat !== 0) {
                rowCTTHD = {
                  HDTB_ID: this.hdtb_id,
                  KHOANMUCTT_ID: KHOANMUC_TT.KMTT_KM_DVGT,
                  TIEN: Math.abs(tien) * -1,
                  VAT: Math.abs(vat) * -1,
                  ID: +dv.dichvugt_id,
                  LOAI_ID: 2,
                  TYLE_VAT: +dv.tyle_vat,
                  TYLE_VAT_ID: +dv.tyle_vat_id,
                  PHIEUTT_ID: null
                }
                if (themmoi) rowCTTHD.CT_TIENHD_ID = await this.getKeys('CT_TIENHD')
                this.dsCTTHD.push(rowCTTHD)
              }
            }
          }
           console.log( this.dsCTTHD)
        } else {
          console.log(6)
          if (+km.khoanmuctt_id === KHOANMUC_TT.KMTT_KM_LAPDAT || +km.khoanmuctt_id === KHOANMUC_TT.KMTT_KM_THIETBI || +km.khoanmuctt_id === KHOANMUC_TT.KMTT_KM_DATCOC) {
            rowCTTHD = {
              HDTB_ID: this.hdtb_id,
              KHOANMUCTT_ID: +km.khoanmuctt_id,
              TIEN: +km.tien > 0 ? +km.tien * -1 : +km.tien,
              VAT: +km.tien > 0 ? +km.vat * -1 : +km.vat,
              ID: +km.khoanmuctt_id,
              LOAI_ID: 1,
              TYLE_VAT: 10,
              TYLE_VAT_ID: 1,
              PHIEUTT_ID: null
            }
            if (themmoi) rowCTTHD.CT_TIENHD_ID = await this.getKeys('CT_TIENHD')
            this.dsCTTHD.push(rowCTTHD)
          } else if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU && this.kieuld_id === KieuLapDat.DM_DIDONG && +km.khoanmuctt_id === KHOANMUC_TT.KMTT_SODEP) {
            const dtTienCcbs = await this.LayTienSoDepDDTraSau(`84${this.txtMa_TB}`)
            rowCTTHD = {
              HDTB_ID: this.hdtb_id,
              KHOANMUCTT_ID: +km.khoanmuctt_id,
              TIEN: +dtTienCcbs[0].tiendv,
              VAT: +dtTienCcbs[0].tiendvvat,
              ID: +km.khoanmuctt_id,
              LOAI_ID: 1,
              TYLE_VAT: 10,
              TYLE_VAT_ID: 1,
              PHIEUTT_ID: null
            }
            if (themmoi) rowCTTHD.CT_TIENHD_ID = await this.getKeys('CT_TIENHD')
            this.dsCTTHD.push(rowCTTHD)
          } else {
            if (+km.tien !== 0 || km.vat !== 0) {
              rowCTTHD = {
                HDTB_ID: this.hdtb_id,
                KHOANMUCTT_ID: +km.khoanmuctt_id,
                TIEN: +km.tien,
                VAT: +km.vat,
                ID: +km.khoanmuctt_id,
                LOAI_ID: 1,
                PHIEUTT_ID: null
              }
              if (themmoi) rowCTTHD.CT_TIENHD_ID = await this.getKeys('CT_TIENHD')
              if (+km.khoanmuctt_id === KHOANMUC_TT.KMTT_DATMOI) {
                rowCTTHD.TYLE_VAT = this.vtyle_vat_dnhm
                rowCTTHD.TYLE_VAT_ID = this.vtyle_vat_id_dnhm
              } else if (+km.khoanmuctt_id === KHOANMUC_TT.KMTT_CAIDAT) {
                rowCTTHD.TYLE_VAT = this.vtyle_vat_cd
                rowCTTHD.TYLE_VAT_ID = this.vtyle_vat_id_cd
              } else {
                rowCTTHD.TYLE_VAT = 10
                rowCTTHD.TYLE_VAT_ID = 1
              }
              this.dsCTTHD.push(rowCTTHD)
            }
          }
           console.log( this.dsCTTHD)
        }
      }
      console.log( 888888888888)
      console.log( this.dsCTTHD)
      if (this.dsCTTHD.length === 0) {
        rowCTTHD = {
          HDTB_ID: this.hdtb_id,
          KHOANMUCTT_ID: 1,
          TIEN: 0,
          VAT: 0,
          ID: 1,
          LOAI_ID: 1,
          PHIEUTT_ID: null
        }
        if (themmoi) rowCTTHD.CT_TIENHD_ID = await this.getKeys('CT_TIENHD')
        this.dsCTTHD.push(rowCTTHD)
        console.log( 888888888888)
        console.log( this.dsCTTHD)
      }
    },
    async getKeys(keyname) {
      try {
        const response = await api.getKeys(this.axios, { keyname, numblocksize: 0, numretry: 0 })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? +data.data : null
      } catch (e) {
        this.$root.$toast.error('Không thể lấy key')
        return null
      }
    },
    async LayTienSoDepDDTraSau(somay) {
      try {
        const response = await api.ts_laphd_hoamangmoi_layds_cuocld(this.axios, { kieuld_id: '1', so_tb: `'${somay}'` })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          const dtTienCcbs = data.data
          if (dtTienCcbs.length === 0 || !dtTienCcbs.some((elm) => elm.hasOwnProperty('tiendv')) || !dtTienCcbs.some((elm) => elm.hasOwnProperty('tiendvvat'))) {
            throw new Error('Có lỗi lấy tiến số đẹp từ ccbs: ts_laphd_hoamangmoi_layds_cuocld')
          }
          return dtTienCcbs
        }
      } catch (e) {
        throw e
      }
    },
    changeKeysToUpper(obj) {
            var key, upKey;
            for (key in obj) {
                if (obj.hasOwnProperty(key)) {
                    upKey = key.toUpperCase();
                    if (upKey !== key) {
                        obj[upKey] = obj[key];
                        delete(obj[key]);
                    }
                    // recurse
                    if (typeof obj[upKey] === "object") {
                        this.changeKeysToUpper(obj[upKey]);
                    }
                }
            }
            return obj;
        },
    async TaoDuLieu_TDTT_HD_THUEBAO() {
      this.dsTDTT_HD_THUEBAO = []
      this.vTDTT_HDTB_ID = await this.getKeys('TDTT_HD_THUEBAO')
      this.dsTDTT_HD_THUEBAO.push({
        TDTT_HDTB_ID: this.vTDTT_HDTB_ID,
        HDKH_ID: this.hdkh_id,
        HDTB_ID: this.hdtb_id,
        KIEU_THAYDOI: 7,
        TEN_KH: this.txtTenKH,
        DIACHI_KH: this.txtDiachiKH,
        NGUOI_CN: this.maNguoiDung,
        NGAY_CN: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
        MAY_CN: this.mayCN,
        IP_CN: this.ipCN
      })
    },
    // btnEditTBi_Click () {
    //   if (this.kieuld_id === 13224) return false
    //   this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupBuyDevices')
    //   this.popupComponentName = 'popupBuyDevices'
    //   this.popupComponentData = { _loaitb_id: this.loaitb_id }
    //   if (this.dsCTM_TBI.length > 0) this.popupComponentData.ds = this.dsCTM_TBI
    //   this.$bvModal.show('popupComponents')
    // },
    async btnEditTBi_Click() {
      if (this.kieuld_id === 13224) return false
      const ktra = await this.kiemTraHienThiThietBi(this.loaitb_id)
      if (ktra !== 'OK') {
        if (this.loaitb_id === LoaiHinhTB.IOFFICE) {
          const tsnd = await this.getThongTinHdtbCntt(this.hdtb_id, 1)
          if (tsnd !== '-1') this.slChaMuaTBiProp = +tsnd
        } else if (this.loaitb_id === LoaiHinhTB.VNPT_MEETING || this.loaitb_id === LoaiHinhTB.HNTH_NGN || this.loaitb_id === LoaiHinhTB.VNPT_LIS) {
          const slMl = await this.getThongTinHdtbCntt(this.hdtb_id, 2)
          if (slMl !== '-1') this.slChaMuaTBiProp = +slMl
        } else if (this.loaitb_id === LoaiHinhTB.VNPT_MCAP) {
          let [jsl_cha, jsl_cha_thang] = await Promise.all([this.getThongTinHdtbCntt(this.hdtb_id, 2), this.getThongTinHdtbCntt(this.hdtb_id, 3)])
          if (jsl_cha === '-1' || jsl_cha === '0') jsl_cha = 1
          if (jsl_cha_thang === '-1' || jsl_cha_thang === '0') jsl_cha = 12
          if (+jsl_cha_thang % 12 !== 0) {
            this.$root.$toast.warning('Số tháng không tròn năm')
            return false
          }
          this.slChaMuaTBiProp = (jsl_cha * jsl_cha_thang) / 12
        }
      }
      this.$refs.muaThietBiPopupRef.showModal()
    },
    async muaThietBiPopupClose(data) {
      //console.log(data)
      this.dsCTM_TBI = data
      await this.HT_ThietBi()
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup('popupComponents')
        return false
      }
      this.loading(true)
      try {
        if (this.popupComponentName === 'popupBuyDevices') {
          this.dsCTM_TBI = val
          await this.HT_ThietBi()
        } else {
          let kmtt_id = -1
          let tien_kmtt = 0
          let tien_vat = 0
          this.ds_tien_khoanmuc = val
          if (this.ds_tien_khoanmuc.length > 0) {
            for (const element of this.ds_tien_khoanmuc) {
              const khoanmucttId = +element.khoanmuctt_id
              if (khoanmucttId === KHOANMUC_TT.KMTT_DATMOI) {
                this.dtienld = +element.tien
                this.dvatld = +element.vat
              } else if (khoanmucttId === KHOANMUC_TT.KMTT_CAIDAT) {
                this.dtiencd = +element.tien
                this.dvatcd = +element.vat
              } else if (khoanmucttId === KHOANMUC_TT.KMTT_DICHVU) {
                this.dtien_dv = +element.tien
                this.dvat_dv = +element.vat
              } else if (khoanmucttId === KHOANMUC_TT.KMTT_KM_LAPDAT) {
                this.dtienkmld = +element.tien
                this.dvatkmld = +element.vat
              } else if (khoanmucttId === KHOANMUC_TT.KMTT_KM_DATCOC) {
                this.dtienkmdc = +element.tien
                this.dvatkmdc = +element.vat
                if (this.dtienkmdc > this.dtienkmld) {
                  this.dtienkmld = this.dtienkmdc
                  this.dvatkmld = this.dvatkmdc
                }
              } else if (khoanmucttId === KHOANMUC_TT.KMTT_DATCOC) {
                this.dtiendatcoc_tt = +element.tien
                this.dvatdatcoc_tt = +element.vat
              } else if (khoanmucttId === KHOANMUC_TT.KMTT_DATCOC_KM) {
                this.dtiendatcoc_km = +element.tien
              } else {
                kmtt_id = khoanmucttId
                tien_kmtt = +element.tien
                tien_vat = +element.vat
              }
              this.HienThiTienKhuyenMaiLapDatNhieuTS({
                dtiendatcoc_tt: this.dtiendatcoc_tt,
                kmtt_id,
                tien_kmtt,
                tien_vat
              })
              this.LayTongTien_HDTB()
            }
          }
        }
      } catch (e) {
      } finally {
        this.loading(false)
        this.ClosePopup('popupComponents')
      }
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    HienThiTienKhuyenMaiLapDatNhieuTS({ dtiendatcoc_tt, kmtt_id, tien_kmtt, tien_vat }) {
      let dtienkmld_tmp = this.dtienkmld
      let dvatkmld_tmp = this.dvatkmld
      let dtyle_kmld_tmp = this.tyle_kmld
      if (Math.abs(this.dtienkmld) < Math.abs(this.dtienkmdc)) {
        dtienkmld_tmp = this.dtienkmdc
        dvatkmld_tmp = this.dvatkmdc
      }
      if (dtyle_kmld_tmp < this.tyle_kmld_tt) dtyle_kmld_tmp = this.tyle_kmld_tt
      if (dtyle_kmld_tmp > 0) {
        dtienkmld_tmp = Math.round((this.dtienld * dtyle_kmld_tmp) / 100)
        dvatkmld_tmp = ((this.dtienld + this.dvatld) * dtyle_kmld_tmp) / 100 - dtienkmld_tmp
      }
      for (const element of this.ds_tien_khoanmuc) {
        const khoanmucttId = +element.khoanmuctt_id
        if (khoanmucttId === KHOANMUC_TT.KMTT_KM_LAPDAT) {
          if (this.dtienkmld < 0) {
            element.tien = dtienkmld_tmp * -1
            element.vat = dvatkmld_tmp * -1
          } else {
            element.tien = dtienkmld_tmp
            element.vat = dvatkmld_tmp
          }
        } else if (khoanmucttId === KHOANMUC_TT.KMTT_DATCOC) {
          element.tien = Math.round(dtiendatcoc_tt)
          element.vat = Math.round(this.dvatdatcoc_tt)
        } else if (khoanmucttId === KHOANMUC_TT.KMTT_DATCOC_KM) {
          element.tien = Math.round((this.dtiendatcoc_km * 10) / 11)
          element.vat = Math.round(this.dtiendatcoc_km / 11)
        } else if (khoanmucttId === kmtt_id && kmtt_id !== -1) {
          element.tien = tien_kmtt
          element.vat = tien_vat
        }
      }
      this.LayTongTien_HDTB()
    },
    async kiemTraHienThiThietBi(loaiTbiId) {
      try {
        const response = await api.kiemTraHienThiThietBi(this.axios, { loaitb_id: loaiTbiId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể kiểm tra hiển thị thiết bị')
        return ''
      }
    },
    async btnTien_Click() {
      if (this.ht_kmtt) {
        const ds = await this.getDanhSachTienKMTT()
        if (this.ds_tien_khoanmuc.length > 0) {
          for (const item of ds) {
            const items = this.ds_tien_khoanmuc.filter((x) => +x.khoanmuctt_id === +item.khoanmuctt_id)
            if (items.length > 0) {
              const [last] = items.slice(-1)
              item.tien = Number(last.tien)
              item.vat = Number(last.vat)
              item.tongtien = Number(item.tien) + Number(item.vat)
            }
          }
        }
        this.popupComponentData = {
          ds,
          cols: [
            {
              fieldName: 'tentat_kmtt',
              headerText: 'Khoản mục',
              allowFiltering: false,
              allowSorting: false,
              isPrimaryKey: true
            },
            {
              fieldName: 'tongtien',
              textAlign: 'right',
              allowEditing: true,
              editType: 'numericedit',
              format: 'N',
              customAttributes: { class: 'tongtien' },
              headerText: 'Tổng tiền',
              allowFiltering: false,
              allowSorting: false,
              edit: 'tongtienParam'
            },
            {
              fieldName: 'tien',
              textAlign: 'right',
              allowEditing: true,
              editType: 'numericedit',
              format: 'N',
              customAttributes: { class: 'cuoclapdat' },
              headerText: 'Tiền',
              allowFiltering: false,
              allowSorting: false,
              edit: 'tienParam'
            },
            {
              fieldName: 'vat',
              textAlign: 'right',
              format: 'N',
              customAttributes: { class: 'vatlapdat' },
              headerText: 'Vat',
              allowFiltering: false,
              allowSorting: false
            },
            {
              fieldName: 'tyle_vat',
              textAlign: 'right',
              format: 'N',
              headerText: 'Tỷ lệ vat',
              allowFiltering: false,
              allowSorting: false
            }
          ],
          keycol: 'khoanmuctt_id',
          allowExit: false,
          tieude: 'Chi tiết tiền khoản mục',
          tieudebang: 'Chi tiết khoản mục thanh toán'
        }
        this.popupComponentName = 'popupTienKM'
        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM')
        this.$bvModal.show('popupComponents')
      } else {
        console.log(222222222222)
        this.$refs.tienKMCuPopupRef.showModal()
      }
    },
    async getDanhSachTienKMTT() {
      try {
        const response = await api.getDanhSachTienKMTT(this.axios, { vkieuld_id: this.kieuld_id, vloaihd_id: this.loaihd_id, vloaitb_id: this.loaitb_id })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách khoản mục thanh toán')
        return []
      }
    },
    acceptTienKM(data) {
      console.log(333333333333)
      console.log(data)
      this.ds_tien_khoanmuc = data
      if (this.ds_tien_khoanmuc.length > 0) {
        for (const element of this.ds_tien_khoanmuc) {
          const khoanmucttId = +element.khoanmuctt_id
          if (khoanmucttId === KHOANMUC_TT.KMTT_DATMOI) {
            this.dtienld = +element.tien
            this.dvatld = +element.vat
          } else if (khoanmucttId === KHOANMUC_TT.KMTT_CAIDAT) {
            this.dtiencd = +element.tien
            this.dvatcd = +element.vat
          } else if (khoanmucttId === KHOANMUC_TT.KMTT_DICHVU) {
            this.dtien_dv = +element.tien
            this.dvat_dv = +element.vat
          } else if (khoanmucttId === KHOANMUC_TT.KMTT_KM_LAPDAT) {
            this.dtienkmld = +element.tien
            this.dvatkmld = +element.vat
          } else if (khoanmucttId === KHOANMUC_TT.KMTT_KM_DATCOC) {
            this.dtienkmdc = +element.tien
            this.dvatkmdc = +element.vat 
            if (this.dtienkmdc > this.dtienkmld) { 
              this.dtienkmld = this.dtienkmdc
              this.dvatkmld = this.dvatkmdc 
            }
          } else if (khoanmucttId === KHOANMUC_TT.KMTT_DATCOC) {
            this.dtiendatcoc_tt = +element.tien
            this.dvatdatcoc_tt = +element.vat
          } else if (khoanmucttId === KHOANMUC_TT.KMTT_DATCOC_KM) {
            this.dtiendatcoc_km = +element.tien
          }
          this.HienThiTienKhuyenMaiLapDat(this.dtiendatcoc_tt)
          this.LayTongTien_HDTB()
        }
      }
    }
  },
  watch: {
    chkKhuyenMai: async function (newVal, oldVal) {
      if (this.kieuld_id === 13224) return false
      if (this.doituong_id === DUNG_THU.DOITUONG_TB) return false
      if (!newVal) {
        this.dtiendatcoc_km = 0
        this.dtienkmld = 0
        this.dvatkmld = 0
        this.tyle_kmld = 0
        this.dsKM_HDTB = []
      }
      this.loading(true)
      try {
        await this.HT_ThietBi()
        await this.HT_DichVuGT()
        this.HienThiTienKhuyenMaiLapDat(this.dtiendatcoc_tt)
        this.LayTongTien_HDTB()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    chkTraTruoc: async function (newVal, oldVal) {
      if (this.kieuld_id === 13224) return false
      if (!newVal) {
        this.dtienkmdc = 0
        this.dvatkmdc = 0
        this.dtiendatcoc_tt = 0
        this.dvatdatcoc_tt = 0
        this.tyle_kmld_tt = 0
        this.dsDC_HDTB = []
      }
      if (!this.chkKhuyenMai) {
        this.dtiendatcoc_km = 0
        this.dtienkmld = 0
        this.dvatkmld = 0
        this.tyle_kmld = 0
      }
      this.loading(true)
      try {
        await this.HT_ThietBi()
        await this.HT_DichVuGT()
        this.HienThiTienKhuyenMaiLapDat(this.dtiendatcoc_tt)
        this.LayTongTien_HDTB()
        if (this.doituong_id === DUNG_THU.DOITUONG_TB) this.disabledBtnEditTBi = !newVal
      } catch (e) {
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>
