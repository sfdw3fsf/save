<template src="./index.html">
</template>
<script>
import Adsl from '../DsDichVu/Adsl'
import CD from '../DsDichVu/CD'
import GP from '../DsDichVu/GP'
import IMS from '../DsDichVu/IMS'
import TSL from '../DsDichVu/TSL'
import Wan from '../DsDichVu/Wan'
import Enum from '../../Enum'
import DateTimeLib from '@/utils/DateTimeLib'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import ThueBaoCungMa from '../ThueBaoCungMa/index'
import frmChonTinhTSL from '../ChonTinh/ModalChonTinhTSL.vue'
import frmChonTinh from '../ChonTinh/ModalChonTinh.vue'
import frmDiaChiBC from '../Popups/frmDiaChiBC/frmDiaChiBC.vue'
import frmDangKyDVGTBC from '../Popups/frmDangKyDVGTBC/frmDangKyDVGTBC.vue'
import frmThongSoTSL from '../Popups/frmThongSoTSL/frmThongSoTSL.vue'
import frmGankhuvuc from '../Popups/frmGanKhuVucQuanLyThueBao'
import frmChuyenDich_ThueSo from '../Popups/frmChuyenDich_ThueSo'
import { currency } from '@/filters/currency'
import TocDoMucCuoc from '../Popups/TocDoMucCuoc/TocDoMucCuoc'
// import frmThongSoWan from '../Popups/frmThongSoWan2/frmThongSoWan.vue'
import frmThongSoWan from '@/modules/contract/setup/ChangeMegaWanConfigCrossSell/popups/frmThongSoWan.vue'
// import frmThongSoCoDinh from '../Popups/frmThongSoCoDinh/ThongSoCoDinhModal.vue'

import frmThongSoCoDinh from '@/modules/contract/setup/ChangeSubsTypeCrossSale/popups/ThongSoCoDinhModal.vue'
import frmTienKMCuPopup from '@/modules/search/subscriber/ModifyContractMoney/popup/TienKMCuPopup'
import frmSearchContractModal from '@/modules/contract/setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue'
export default {
  name: 'ThongTinThueBao',
  components: {DateTimeLib,
    BssRequiredMarker,
    Adsl,
    CD,
    GP,
    IMS,
    TSL,
    Wan,
    ThueBaoCungMa,
    frmChonTinhTSL,
    frmChonTinh,
    frmDiaChiBC,
    frmDangKyDVGTBC,
    frmThongSoTSL,
    frmGankhuvuc,
    frmChuyenDich_ThueSo,
    TocDoMucCuoc,
    frmThongSoWan,
    frmThongSoCoDinh,
    frmTienKMCuPopup,
    frmSearchContractModal
  },
  props: ['parent'],
  data () {
    return {
      animationSettings: {effect: 'Zoom'},
      position: {X: 'center', Y: 'top'},
      draggable: true,
      targetTop: '#app .main-wrapper',
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentEvents: {
        'form-close': 'popupClosed',
        'acceptChangeCTV': 'popupClosed',
        'acceptChangeNGT': 'popupClosed'
      },
      Capnhatthongso: null,
      diaChiLD_BC: {},
      diaChiTB_BC: {},
      tinh: {},
      dulieu_gankhuvuc: {}
    }
  },
  computed: {
    cboDaiVT () {
      return this.parent.modelCbo.cboDaiVT
    },
    cboLoaihinhTB () {
      return this.parent.modelCbo.cboLoaihinhTB
    },
    getTinh () {
      if (this.parent.dichvuvt_id === 9) {
        return this.parent.tinh_cuoi
      } else {
        return this.parent.BanCheoData.Tinh_TC
      }
    }
  },
  async mounted () {
  },
  methods: {
    btnTimMaNVTC_Click () {},
    btnDiaChiBC_Click () {},
    btnDiaChiCT_Click () {},
    btnDiaChiTT_Click () {},
    btnDiaChiNDD_Click () {
      try {

      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 57 ~ btnDiaChiNDD_Click ~ error', error)
      }
    },
    async acceptSearchContract (item) {
      console.log('🚀 ~ file: index.vue ~ line 89 ~ acceptSearchContract ~ item', item)
      this.parent.modelInput.txtMaGD = item.ma_gd
      if (this.parent.modelInput.txtMaGD !== '' && this.parent.modelInput.txtMaGD !== null) {
        let ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.parent.modelInput.txtMaGD, Enum.LoaiHopDong.DI_CHUYEN, Enum.TrangThaiHD.MOI, 0)
        await this.HienThiTTHopDongKH(ds)
      }
    },
    btnLoaiKH_Click () {
      try {

      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 64 ~ btnLoaiKH_Click ~ error', error)
      }
    },
    async btnEditDVGT_Click () {
      this.$refs.frmDangKyDVGTBC.openDialog()
    },
    async  chapnhan_DangKyDVGTBC (data) {
      this.parent.m_dsThueBao_DichVu = data
      await this.HT_DichVuGT()
    },
    btnDiemDau_Click () {
      if (this.parent.tinh_dau.tinhthicong_id === 0) {
        this.$root.$toast.warning('Chưa có thuê bao')
        return
      }
      console.log('this.parent.tinh_dau', this.parent.tinh_dau)
      this.$refs.frmThongSoTSLDau.openDialog(this.parent.tinh_dau, this.parent.loaitb_id, this.parent.loaitb_id, Number(this.parent.modelCbo.cboKieuLD), this.parent.thongso_ban, this.parent.thongso_tc_dau, Enum.LoaiHopDong.DI_CHUYEN, this.parent.kenh_trang)
    },
    async popupClosed (val) {
      this.loading(true)
      try {
        console.log('🚀 ~ file: index.vue ~ line 102 ~ popupClosed ~ val', val)
        switch (this.popupComponentName) {
          case 'popupChuyenDich_ThueSo':
            if (val) {
              this.parent.kieu_thueso = val.kieu_thueso
              this.parent.noidung_thueso = val.noidung_thueso
              this.parent.sothang_thueso = val.sothang_thueso
            } else {
              this.parent.kieu_thueso = 0
              this.parent.noidung_thueso = ''
              this.parent.sothang_thueso = 0
            }
            break
          case 'popupChonSM':
            if (!val) return
            this.parent.modelInput.txtMaTBMoi = val.somay
            break
          case 'popupTKTBChuyenDich':
            if (!val || val.MaThueBao === null || val.MaThueBao === '') return
            if (val.dichvuvt_id === Enum.DichVuVienThong.GPHONE || val.dichvuvt_id === Enum.DichVuVienThong.DI_DONG) {
              this.$root.$toast.error('Không thực hiện dịch chuyển đối với thuê bao Gphone, Di động!')
              return
            }
            this.parent.modelInput.txtMaTB = val.MaThueBao
            this.parent.modelInput.txtMaTBMoi = ''
            if (Number(val.dichvuvt_id) !== 0) { this.parent.modelCbo.cboDichVuVT = Number(val.dichvuvt_id) }
            this.$refs.txtMaTB.focus()
            break
          case 'popupChonKhuVuc':
            if (val.khuvuc_id !== 0) {
              if (this.parent.quanld_id !== 0 && this.parent.phuongld_id !== 0) {
                this.parent.khuvuc_id = parseInt(val.khuvuc_id)
                this.parent.dsHDTB_KV = val.hdtb_kv_data
                this.parent.modelInput.txtKhuVuc = val.ma_kv
              }
            } else {
              this.$root.$toast.warning('Chưa có thông tin về địa chỉ lắp đặt. Bạn hãy kiểm tra lại!')
              this.parent.modelInput.txtKhuVuc = ''
              this.parent.khuvuc_id = 0
              this.parent.dsHDTB_KV = []
            }
            break
          case 'popupChonKhuyenMai':
            if (val.khuyenmai) {
              this.parent.modelCbo.cboKhuyenMai = Number(val.khuyenmai)
            }
            break
        }
      } catch (error) {

      } finally {
        this.ClosePopup('popupComponents_bc')
        this.loading(false)
      }
    },
    ClosePopup (modalId) {
      this.$bvModal.hide(modalId)
    },
    frmThongSoTSLDau_onChapNhan (frm) {
      console.log('🚀 ~ file: index.vue:195 ~ frmThongSoTSLDau_onChapNhan ~ frm:', frm)
      this.parent.thongso_ban = frm.thongso_Ban
      this.parent.thongso_tc_dau = frm.thongso_TC
    },
    frmThongSoTSLCuoi_onChapNhan (frm) {
      console.log('🚀 ~ file: index.vue:200 ~ frmThongSoTSLCuoi_onChapNhan ~ frm:', frm)
      this.parent.thongso_ban = frm.thongso_Ban
      this.parent.thongso_tc_cuoi = frm.thongso_TC
    },
    btnDiemCuoi_Click () {
      if (this.parent.tinh_cuoi.tinhthicong_id === 0) {
        this.$root.$toast.warning('Chưa có thuê bao')
        return
      }
      this.$refs.frmThongSoTSLCuoi.openDialog(this.parent.tinh_cuoi, this.parent.loaitb_id, this.parent.loaitb_id, Number(this.parent.modelCbo.cboKieuLD), this.parent.thongso_ban, this.parent.thongso_tc_cuoi, Enum.LoaiHopDong.DI_CHUYEN, this.parent.kenh_trang)
    },
    btnNganhNghe_Click () {},
    btnDiaChiKH_Click () {
      try {

      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 60 ~ btnDiaChiKH_Click ~ error', error)
      }
    },
    async acceptTienKM (data) {
      try {
        this.parent.ds_tien_khoanmuc = data
        if (this.parent.ds_tien_khoanmuc.length > 0) {
          for (let index = 0; index < this.parent.ds_tien_khoanmuc.length; index++) {
            const e = this.parent.ds_tien_khoanmuc[index]
            if (e.khoanmuctt_id.toString() === Enum.KHOANMUC_TT.KMTT_CHUYENDICH.toString()) {
              this.parent.dtienld = parseInt(e.tien)
              this.parent.dvatld = parseInt(e.vat)
            }
            if (e.khoanmuctt_id.toString() === Enum.KHOANMUC_TT.KMTT_DICHVU.toString()) {
              this.parent.dtien_dv = parseInt(e.tien)
              this.parent.dvat_dv = parseInt(e.vat)
            }
            await this.LayTongTien_HDTB()
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 110 ~ acceptTienKM ~ error', error)
      }
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
    btnThueSo_Click () {
      try {
        if (this.parent.modelInput.txtMaTBMoi === '') {
          this.$root.$toast.warning('Bạn chưa chọn số mới!')
          this.$refs.btnChonSM.focus()
          return
        }
        if (this.parent.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) {
          this.popupComponent = () => import('../Popups/frmChuyenDich_ThueSo')
          this.popupComponentName = 'popupChuyenDich_ThueSo'
          this.popupComponentData = {
            vma_tb: this.parent.modelInput.txtMaTB,
            sotruot: this.parent.modelInput.txtMaTBMoi,
            vhdtb_id: this.parent.vhdtb_id
          }
          this.Popup('popupComponents')
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 229 ~ btnThueSo_Click ~ error', error)
      }
    },
    async btnTKMaTB_Click () {
      if (this.parent.modelInput.txtMaTB !== '') {
        this.popupComponent = () => import('../Popups/frmTKTBChuyenDich.vue')
        this.popupComponentName = 'popupTKTBChuyenDich'
        if ((this.parent.khachhang_id_cu) !== 0) {
          this.popupComponentData = {
            khachhang_id: this.parent.khachhang_id_cu,
            ma_gd: this.parent.modelInput.txtMaGD
          }
        } else {
          var dskhcu = await this.parent.LAY_BIENDONG_THEO_HDTB_ID(this.parent.hdtb_id)
          if (dskhcu !== null && dskhcu.length > 0) {
            this.parent.khachhang_id_cu = parseInt(dskhcu[0].khachhang_id)
            this.popupComponentData = {
              khachhang_id: this.parent.khachhang_id_cu,
              ma_gd: this.parent.modelInput.txtMaGD
            }
          } else {
            this.$root.$toast.error('Không có thông tin về thuê bao: ' + this.parent.modelInput.txtMaTB + '\nKiểm tra lại mã thuê bao')
            this.$refs.txtMaTB.focus()
            return false
          }
        }
        this.Popup('popupComponents_bc')
      } else {
        this.$root.$toast.error('Bạn chưa nhập mã thuê bao!!!')
      }
    },
    Popup (modalId) {
      this.$bvModal.show(modalId)
    },
    txtTenTB_Leave () {
      this.parent.modelInput.txtTenTB = this.parent.ChuanHoaTen(this.parent.modelInput.txtTenTB)
    },
    btnChonSM_Click () {
      if (this.parent.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) {
        var result = true
        if (result) {
          if (this.parent.quanld_id == 0 || this.parent.phuongld_id == 0) {
            this.$root.$toast.error('Bạn chưa chọn địa chỉ lắp đặt !')
            this.$refs.txtDiaChiLD.focus()
            return
          }
          this.popupComponent = () => import('../Popups/frmChonSM')
          this.popupComponentName = 'popupChonSM'
          this.popupComponentData = {
            _quan_id: this.parent.quanld_id,
            _phuong_id: this.parent.phuongld_id,
            _loaiso_id: Enum.LOAISO.THUONG,
            _thueso: false,
            loaitb_id: this.parent.loaitb_id,
            dichvuvt_id: this.parent.dichvuvt_id,
            hdkh_id: this.parent.hdkh_id,
            donvi_ql_id: this.parent.modelCbo.cboDaiVT
          }
          this.Popup('popupComponents')
        } else {
          this.modelInput.txtMaTBMoi = ''
          this.$refs.txtMaTBMoi.focus()
        }
      } else {
        this.$root.$toast.warning('Bạn chỉ được đổi số cho thuê bao Cố định hoặc IMS')
      }
    },
    async btnDiaChiTB_Click () {
      try {
        if (this.parent.dichvuvt_id === 9) {
          this.tinh = {...this.parent.tinh_cuoi}
        } else {
          this.tinh = {...this.parent.BanCheoData.Tinh_TC}
        }
        this.diaChiTB_BC.tinh_id = this.parent.tinhtb_id
        this.diaChiTB_BC.quan_id = this.parent.quantb_id
        this.diaChiTB_BC.phuong_id = this.parent.phuongtb_id
        this.diaChiTB_BC.pho_id = this.parent.photb_id
        this.diaChiTB_BC.ap_id = this.parent.aptb_id
        this.diaChiTB_BC.khu_id = this.parent.khutb_id
        this.diaChiTB_BC.dacdiem_id = this.parent.dacdiemtb_id
        this.diaChiTB_BC.diachi = this.parent.modelInput.txtDiaChiTB
        this.diaChiTB_BC.sonha = this.parent.sonhatb
        this.diaChiTB_BC.longtitude = this.parent.Lng_TB
        this.diaChiTB_BC.lattitude = this.parent.Lat_TB
        this.$refs.diaChiTB_BC.show()
        await this.$refs.popupDiaChiTB_BC.frmDiaChiBC_Load()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 82 ~ btnDiaChiTB_Click ~ error', error)
      }
    },
    btnLayMaGD_Click () {
      this.$refs.frmSearchContractModal.showModal()
    },
    chkCongVan_CheckedChanged () {
      this.parent.modelInput.txtCongVan = ''
      this.parent.modelDateTime.dtpNgayCV = new Date()
    },
    chkKhuyenMai_CheckedChanged () {
      this.parent.dtienkmld = 0
      this.parent.dvatkmld = 0
      this.parent.tyle_kmld = 0
      this.parent.tong_kmld = 0
      this.parent.dsKM_HDTB = []
    },
    btnChon_KhuyenMai_Click () {
      if (this.parent.modelCbo.vcboKhuyenMai.length === 0) return
      var dulieu = {}
      dulieu.loai = 0 // 0: khuyến mại, 1: trả trước
      dulieu.dt = this.dsKhuyenMai
      this.popupComponentData = dulieu
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai')
      this.popupComponentTitle = 'Chọn khuyến mại/đặt cọc'
      this.popupComponentName = 'popupChonKhuyenMai'
      this.$bvModal.show('popupComponents_bc')
    },
    async btnChiTietKM_Click () {
      try {
        if (this.parent.modelCkeckBox.chkKhuyenMai) {
          if (this.parent.modelCbo.cboKhuyenMai.length > 0) {
            let vmuccuoctn_id = 0
            let vtocdotn_id = 0
            if (this.parent.dichvuvt_id === Enum.DichVuVienThong.ADSL) {
              vtocdotn_id = Number(this.$refs.refAdsl.cboGoiCuocAdsl.Value)
              if (Number(this.parent.modelCbo.cboLoaihinhTB) === Enum.LoaiHinhTB.INTERNET_ADSL || Number(this.parent.modelCbo.cboLoaihinhTB) === Enum.LoaiHinhTB.INTERNET_FTTH ||
                                 Number(this.parent.modelCbo.cboLoaihinhTB) === Enum.LoaiHinhTB.WIFI_FIBER) {
                vmuccuoctn_id = Number(this.$refs.refAdsl.cboMucCuoc.Value)
              }
            } else if (this.parent.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
              this.parent.dichvuvt_id === Enum.DichVuVienThong.METRONET || 
              this.parent.dichvuvt_id === Enum.DichVuVienThong.NOIBO_DADIEM
              ) {
              vtocdotn_id = Number(this.$refs.refWan.cboTocDo.Value)
            } else if (this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || this.parent.dichvuvt_id === Enum.DichVuVienThong.NOIBO_2DIEM) {
              vtocdotn_id = Number(this.$refs.refTSL.cboTocDoKenhTSL.Value)
            }
            let khuyenmai_id = Number(this.parent.modelCbo.cboKhuyenMai)
            let KIEMTRA_THOIHAN_KM = await this.KIEMTRA_THOIHAN_KM(khuyenmai_id, new Date())
            if (KIEMTRA_THOIHAN_KM === 0) {

            } else {

            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 377 ~ btnChiTietKM_Click ~ error', error)
      }
    },
    async KIEMTRA_THOIHAN_KM (vkhuyenmai_id, vngay_yc) {
      try {
        return 0
      } catch (error) {
        return 1
      }
    },
    async btnMaNVKD_Click () {
      try {
        if (!this.parent.quanld_id || this.parent.quanld_id <= 0 || !this.parent.phuongld_id || this.parent.phuongld_id <= 0) {
          this.$toast.error('Chưa có thông tin về địa chỉ lắp đặt. Bạn hãy kiểm tra lại!')
          return
        }
        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao')
        this.popupComponentName = 'popupChonKhuVuc'
        // this.popupComponent = () => import('../Popups/frmGanKhuVucQuanLyThueBao')
        // this.popupComponentName = 'popupChonKhuVuc'
        let diachi = {}
        diachi.QUAN_ID = this.parent.quanld_id
        diachi.PHUONG_ID = this.parent.phuongld_id
        diachi.PHO_ID = this.parent.phold_id
        diachi.AP_ID = this.parent.apld_id
        diachi.KHU_ID = this.parent.khuld_id
        diachi.DACDIEM_ID = this.parent.dacdiemld_id
        diachi.TINH_ID = this.parent.tinhld_id
        this.popupComponentData = { diachi: diachi, khuvuc_id: this.parent.khuvuc_id, hdtb_id: this.parent.hdtb_id === null ? 0 : this.parent.hdtb_id, thuebao_id: this.parent.thuebao_id, donvi_id: this.parent.modelCbo.cboDonViQL }
        this.$bvModal.show('popupComponents_bc')
        // if (this.parent.thuebao_id) {
        //   this.popupComponentData = { diachi: diachi, khuvuc_id: this.parent.khuvuc_id, hdtb_id: this.parent.thuebao_id, donvi_id: this.parent.modelCbo.cboDonViQL, isStrictMode: true }
        // } else { this.popupComponentData = { diachi: diachi, khuvuc_id: this.parent.khuvuc_id, thuebao_id: this.parent.hdtb_id, donvi_id: this.parent.modelCbo.cboDonViQL, isStrictMode: true } }
        // console.log('🚀 ~ file: index.vue ~ line 224 ~ btnMaNVKD_Click 1 ~ diachi', diachi)
        // this.$bvModal.show('popupComponents_bc')
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 229 ~ btnMaNVKD_Click ~ error', error)
      }
    },
    async btnDiachiLD_Click () {
      try {
        await this.DiachiLD_Click()
        await this.HT_DS_KhuyenMai_Combobox()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 88 ~ btnDiachiLD_Click ~ error', error)
      }
    },
    async DiachiLD_Click () {
      if (this.parent.dichvuvt_id === 9) {
        this.tinh = { tinhthicong_id: this.parent.tinh_cuoi.tinhthicong_id }
      } else {
        let tinhthicong_id = this.parent.BanCheoData.Tinh_TC.tinhthicong_id
        this.tinh = {tinhthicong_id: tinhthicong_id}
        console.log('🚀 ~ file: index.vue ~ line 143 ~ DiachiLD_Click ~ this.parent.BanCheoData.Tinh_TC', this.parent.BanCheoData.Tinh_TC)
        console.log('🚀 ~ file: index.vue ~ line 1422 ~ DiachiLD_Click ~ this.tinh', this.tinh)
      }
      console.log('🚀 ~ file: index.vue ~ line 146 ~ DiachiLD_Click ~ this.parent.tinhld_id', this.parent.tinhld_id)
      this.diaChiLD_BC.tinh_id = this.parent.tinhld_id
      this.diaChiLD_BC.quan_id = this.parent.quanld_id
      this.diaChiLD_BC.phuong_id = this.parent.phuongld_id
      this.diaChiLD_BC.pho_id = this.parent.phold_id
      this.diaChiLD_BC.ap_id = this.parent.apld_id
      this.diaChiLD_BC.khu_id = this.parent.khuld_id
      this.diaChiLD_BC.dacdiem_id = this.parent.dacdiemld_id
      this.diaChiLD_BC.diachi = this.parent.modelInput.txtDiaChiLD
      this.diaChiLD_BC.sonha = this.parent.sonhald
      this.diaChiLD_BC.longtitude = this.parent.Lng_LD
      this.diaChiLD_BC.lattitude = this.parent.Lat_LD
      this.diaChiLD_BC.ketcuoi_id = this.parent.KetCuoi_ID
      this.$refs.diaChiLD_BC.show()
      await this.$refs.popupDiaChiLD_BC.frmDiaChiBC_Load()
    },
    async getDiaChi_LDBC (obj) {
      try {
        this.$refs.diaChiLD_BC.hide()
        this.parent.tinhld_id = obj.tinh_id
        this.parent.quanld_id = obj.quan_id
        this.parent.phuongld_id = obj.phuong_id
        this.parent.phold_id = obj.pho_id
        this.parent.apld_id = obj.ap_id
        this.parent.khuld_id = obj.khu_id
        this.parent.dacdiemld_id = obj.dacdiem_id
        this.parent.sonhald = obj.sonha
        this.parent.Lng_LD = obj.longtitude
        this.parent.Lat_LD = obj.lattitude
        this.parent.modelInput.txtDiaChiLD = obj.diachi
        if (obj.ketcuoi_id !== -1) { this.parent.KetCuoi_ID = obj.ketcuoi_id }
        await this.LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2(this.parent.phuongtt_id, this.parent.phott_id, this.parent.aptt_id, this.parent.khutt_id, this.parent.dacdiemtt_id)
        this.parent.tinhld_id = obj.tinh_id
        this.parent.quanld_id = obj.quan_id
        this.parent.phuongld_id = obj.phuong_id
        this.parent.phold_id = obj.pho_id
        this.parent.apld_id = obj.ap_id
        this.parent.khuld_id = obj.khu_id
        this.parent.dacdiemld_id = obj.dacdiem_id
        this.parent.sonhald = obj.sonha
        this.parent.Lng_LD = obj.longtitude
        this.parent.Lat_LD = obj.lattitude
        this.parent.modelInput.txtDiaChiLD = obj.diachi
        await this.LayTienTheoKhoanMuc()

        this.parent.tinhtb_id = obj.tinh_id
        this.parent.quantb_id = obj.quan_id
        this.parent.phuongtb_id = obj.phuong_id
        this.parent.photb_id = obj.pho_id
        this.parent.dacdiemtb_id = obj.dacdiem_id
        this.parent.aptb_id = obj.ap_id
        this.parent.khutb_id = obj.khu_id

        this.parent.Lng_TB = obj.longtitude
        this.parent.Lat_TB = obj.lattitude

        this.parent.sonhatb = obj.sonha
        this.parent.modelInput.txtDiaChiTB = obj.diachi
        await this.HT_NV_KinhDoanh_Theo_DiaBan(this.parent.quanld_id, this.parent.phuongld_id, this.parent.phold_id, this.parent.apld_id, this.parent.khuld_id,
          Number(this.parent.modelCbo.cboDonViQL), this.parent.dacdiemld_id)
        this.$refs.txtDiaChiLD.focus()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 183 ~ getDiaChi_LDBC ~ error', error)
      }
    },
    async HT_KV_CSKH_Theo_DiaBan (quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/sp_ht_kv_cskh_theo_diaban', {quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id})
        return rs.data
      } catch (error) {
        return []
      }
    },
    gankhuvuc_close () {
      this.$bvModal.hide('gankhuvucbancheo')
    },
    chapnhan_gankhuvuc (obj) {
      if (this.parent.quanld_id !== 0 && this.parent.phuongld_id !== 0) {
        this.parent.khuvuc_id = parseInt(obj.khuvuc_id)
        this.parent.modelInput.txtKhuVuc = obj.ma_kv
        this.parent.dsHDTB_KV = obj.hdtb_kv_data
      } else {

      }
      this.$bvModal.hide('gankhuvuc')
    },
    async HT_NV_KinhDoanh_Theo_DiaBan (quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id) {
      try {
        let ds = await this.HT_KV_CSKH_Theo_DiaBan(quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id)
        console.log('🚀 ~ file: index.vue ~ line 436 ~ HT_NV_KinhDoanh_Theo_DiaBan ~ ds', ds)
        if (ds !== null && ds.length > 1) {
          this.dulieu_gankhuvuc = {
            donvi_id: donvi_ql_id,
            diachi: {
              'QUAN_ID': quan_id, 'PHUONG_ID': phuong_id, 'PHO_ID': pho_id, 'AP_ID': ap_id, 'KHU_ID': khu_id, 'DACDIEM_ID': dacdiem_id
            }
          }
          this.$bvModal.show('gankhuvucbancheo')
        } else if (ds !== null && ds.length > 0) {
          this.parent.modelInput.txtKhuVuc = ds[0]['ten_kv']
          this.parent.khuvuc_id = parseInt(ds[0]['khuvuc_id'])
          this.parent.dsHDTB_KV = []
          let dr = {}
          dr.HDTB_ID = this.parent.hdtb_id
          dr.KHUVUC_ID = this.parent.khuvuc_id
          dr.LOAIKV_ID = Enum.LOAI_KV.KHUVUC_DIABAN
          dr.KIEUKV_ID = 2
          this.parent.dsHDTB_KV.push(dr)
        } else {
          this.parent.modelInput.txtKhuVuc = ''
          this.parent.khuvuc_id = 0
          this.parent.dsHDTB_KV = []
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 463 ~ HT_NV_KinhDoanh_Theo_DiaBan ~ error', error)
      }
    },
    async getDiaChi_TBBC (obj) {
      try {
        this.$refs.diaChiTB_BC.hide()
        this.parent.tinhtb_id = obj.tinh_id
        this.parent.quantb_id = obj.quan_id
        this.parent.phuongtb_id = obj.phuong_id
        this.parent.photb_id = obj.pho_id
        this.parent.aptb_id = obj.ap_id
        this.parent.khutb_id = obj.khu_id
        this.parent.dacdiemtb_id = obj.dacdiem_id
        this.parent.Lng_TB = obj.longtitude
        this.parent.Lat_TB = obj.lattitude
        this.parent.sonhatb = obj.sonha
        this.parent.modelInput.txtDiaChiTB = obj.diachi
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 183 ~ getDiaChi_LDBC ~ error', error)
      }
    },
    async cboKieuLD_click () {
      this.parent.Click_kieuld = true
      // await this.cboKieuLD_SelectedValueChanged()
    },
    async LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2 () {
      let dtControl = await this.parent.getControl()
      if (!dtControl || dtControl.length <= 0)  return
      for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
        const e = dtControl.dsComboDonVi[index]
        console.log('🚀 ~ file: index.vue ~ line 586 ~ LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2 ~ e', e)
        // let dr = dtTemp.filter(e => e.loaidv_id === element.loaidv_id)
        // if (dr.length > 0) {
        //   dtControl.ref.$refs[element.key][0].value = dr[0].donvi_id
        // }
      }
    },
    closePopupDiaChiLD_BC () {
      this.$refs.diaChiLD_BC.hide()
    },
    closePopupDiaChiTB_BC () {
      this.$refs.diaChiTB_BC.hide()
    },
    btnTien_Click () {
      this.$refs.frmTienKMCuPopup.showModal()
    },
    async lvwThueBao_selectedRowChanged (data) {
      console.log('🚀 ~ file: index.vue ~ line 452 ~ lvwThueBao_selectedRowChanged ~ data', data)
      try {
        if (this.parent.modelInput.lvwThueBao.length > 0 && data) {
          this.parent.hdtb_id = Number(data.hdtb_id)
          await this.Gan_thongso_bancheo_hdtb(this.parent.hdtb_id)
          let ma_tt = data.ma_tt
          await this.HienThiTTHopDongTB(await this.parent.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.parent.hdtb_id))
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 461 ~ lvwThueBao_selectedRowChanged ~ error', error)
      }
    },
    lvwThanhToan_selectedRowChanged () {},
    Reset_Thongso_Thuebao () {},
    async cboNganhNgheLoc_EditValueChanged () {},
    async cboLoaiKH2_EditValueChanged () {},
    async LayTienTheoKhoanMuc () {
      try {
        if (this.parent.dichvuvt_id !== Enum.DichVuVienThong.ADSL && this.parent.dichvuvt_id !== Enum.DichVuVienThong.MEGAWAN && 
          this.parent.dichvuvt_id !== Enum.DichVuVienThong.METRONET && this.parent.dichvuvt_id !== 26) {
          this.parent.kh_cd = 0
          this.parent.dsltb_adsl_id = 1
        }
        let itocdo_id = 0, imuccuoc_id = 0
        if (this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || this.parent.dichvuvt_id === 25) {
          if (this.$refs.refTSL) {
            itocdo_id = parseInt(this.$refs.refTSL.cboTocDoKenhTSL.Value)
            this.parent.trangbi_id = parseInt(this.$refs.refTSL.cboTrangBi.Value)
          }
        }
        if (this.parent.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
            this.parent.dichvuvt_id === Enum.DichVuVienThong.METRONET ||
            this.parent.dichvuvt_id === 26
            ) {
          if (this.$refs.refWan) {
            itocdo_id = parseInt(this.$refs.refWan.cboTocDo.Value)
            this.parent.trangbi_id = parseInt(this.$refs.refWan.cboTrangBiWan.Value)
          }
        }
        if (this.parent.dichvuvt_id === Enum.DichVuVienThong.ADSL) {
          if (this.$refs.refAdsl) {
            itocdo_id = parseInt(this.$refs.refAdsl.cboGoiCuocAdsl.Value)
            imuccuoc_id = parseInt(this.$refs.refAdsl.cboMucCuoc.Value)
            this.parent.trangbi_id = parseInt(this.$refs.refAdsl.cboTrangBiADSL.Value)
          }
        }
        let ds = await this.LAY_TIEN_KHOANMUC_LD(Enum.LoaiHopDong.DI_CHUYEN, DateTimeLib.toDateDisplay(this.parent.modelDateTime.dtpNgayYC), parseInt(this.parent.modelCbo.cboDichVuVT),
          parseInt(this.parent.modelCbo.cboLoaihinhTB), parseInt(this.parent.modelCbo.cboKieuLD),
          this.parent.phuongld_id, itocdo_id, imuccuoc_id, this.parent.ltb_adsl_id, this.parent.ltb_adsl_moi_id, this.parent.ltb_adsl_cu_id, this.parent.tocdo_moi_id, this.parent.tocdo_cu_id, this.parent.kh_cd, this.parent.phold_id, this.parent.apld_id, this.parent.khuld_id, parseInt(this.parent.modelCbo.cboDoituongTB), this.parent.trangbi_id)
        if (ds !== null && ds.length > 0) {
          await this.HienThiTienLapDat(ds)
          await this.LayTongTien_HDTB()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 89 ~ LayTienTheoKhoanMuc ~ error', error)
      }
    },
    async cboLoaihinhTB_SelectedValueChanged () {
      try {
        if (this.parent.modelCbo.vcboLoaihinhTB.length > 0) {
          this.parent.loaitb_id = parseInt(this.parent.modelCbo.cboLoaihinhTB)
          await this.HT_Kieu_LD_BANCHEO_Combobox(this.parent.loaitb_id, Enum.LoaiHopDong.DI_CHUYEN, 2)
          if (this.parent.dichvuvt_id === Enum.DichVuVienThong.ADSL && this.parent.ikieuld_id !== 0) { this.parent.modelCbo.cboKieuLD = this.parent.ikieuld_id }
          if (this.parent.kt_load === false) {
            if (this.parent.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
              this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || 
              this.parent.dichvuvt_id === 26 || this.parent.dichvuvt_id === 25 ||
              this.parent.dichvuvt_id === Enum.DichVuVienThong.METRONET) { await this.HienThi_CTK_CTC() }
          }
          await this.HT_COMBO_DONVI()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 104 ~ cboLoaihinhTB_SelectedValueChanged ~ error', error)
      }
    },
    async txtMaGD_KeyPress () {
      try {
        this.loading(true)
        this.parent.modelInput.txtMaGD = this.clearTheSpace(this.parent.modelInput.txtMaGD)
        this.parent.modelInput.txtMaGD = this.Catdaunhay(this.parent.modelInput.txtMaGD)
        if (this.parent.modelInput.txtMaGD !== '') {
          let ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.parent.modelInput.txtMaGD, Enum.LoaiHopDong.DI_CHUYEN, Enum.TrangThaiHD.MOI, 0)
          await this.HienThiTTHopDongKH(ds)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 147 ~ txtMaGD_KeyPress ~ error', error)
      } finally {
        this.loading(false)
      }
    },
    txtDiaChiTB_KeyPress () {
      this.btnDiaChiTB_Click()
    },
    async LAY_DS_HOPDONG_THEO_MA_GD (in_ma_gd, in_loaihd_id, in_tthd_id, in_donvi_dl_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_hopdong_theo_ma_gd', {in_ma_gd, in_loaihd_id, in_tthd_id, in_donvi_dl_id})
        console.log('🚀 ~ file: index.vue ~ line 644 ~ LAY_DS_HOPDONG_THEO_MA_GD ~ rs', rs)
        if (rs.data === null && rs.error_code === 'BSS-00000204') {
          this.$root.$toast.error(rs.message)
          return []
        } else {
          return rs.data
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 159 ~ LAY_DS_HOPDONG_THEO_MA_GD ~ error', error)
        return []
      }
    },
    async cboKieuLD_SelectedValueChanged () {
      try {
        console.log('🚀 ~ file: index.vue ~ line 653 ~ cboKieuLD_SelectedValueChanged ~ this.parent.modelCbo.vcboKieuLD', this.parent.modelCbo.vcboKieuLD)
        console.log('🚀 ~ file: index.vue ~ line 701 ~ cboKieuLD_SelectedValueChanged ~ this.parent.BanCheoData.Tinh_TC', this.parent.BanCheoData.Tinh_TC)
        if (this.parent.modelCbo.vcboKieuLD.length > 0) {
          if (this.parent.BanCheoData.Tinh_TC.tinhthicong_id === 0 || this.parent.BanCheoData.Tinh_TC.tinhthicong_id === null) {
            this.parent.Click_kieuld = false
          } else {
            this.parent.Click_kieuld = true
          }

          if (this.parent.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) {
            let nhomkld = await this.MAP_ID('NHOMKIEU_LD', this.parent.modelCbo.cboKieuLD)
            if (nhomkld === 1) { this.parent.buttonState.btnThueSo = true } else { this.parent.buttonState.btnThueSo = false }
          }
          this.parent.kieu_thueso = 0
          this.parent.noidung_thueso = ''
          this.parent.sothang_thueso = 0
          if (this.parent.dichvuvt_id !== Enum.DichVuVienThong.TSL && this.parent.dichvuvt_id !== 25) { await this.HT_COMBO_DONVI() }
          await this.LayTienTheoKhoanMuc()
          await this.HT_DS_KhuyenMai_Combobox()

          this.parent.dataSourceDV = {
            idDichVu: this.parent.modelCbo.cboDichVuVT,
            idLoaihinhTb: this.parent.modelCbo.cboLoaihinhTB,
            idKieuLd: this.parent.modelCbo.cboKieuLD,
            idDaiVt: this.parent.modelCbo.cboDaiVT
          }
          let loaitb_id = parseInt(this.parent.modelCbo.cboLoaihinhTB)
          if (this.parent.Click_kieuld && (this.parent.dichvuvt_id === Enum.DichVuVienThong.CO_DINH || this.parent.dichvuvt_id === Enum.DichVuVienThong.IMS)) {
            this.parent.Click_kieuld = false
            await this.HienThiChonThongSoCoDinh(loaitb_id, loaitb_id)
          }

          if (this.parent.Click_kieuld && (this.parent.dichvuvt_id === Enum.DichVuVienThong.ADSL)) {
            this.parent.Click_kieuld = false
            await this.HienThiChonThongSoADSL()
          }

          if (this.parent.Click_kieuld && (this.parent.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
            this.parent.dichvuvt_id === Enum.DichVuVienThong.METRONET || this.parent.dichvuvt_id === 26)) {
            this.parent.Click_kieuld = false
            await this.HienThiChonThongSoWan()
          }

          if (this.parent.Click_kieuld && (this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || this.parent.dichvuvt_id === 25)) {
            this.parent.Click_kieuld = false
            // await this.HienThiChonThongSoWan()
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 154 ~ cboKieuLD_SelectedValueChanged ~ error', error)
      } finally {
        this.parent.Click_kieuld = false
      }
    },
    async CloseHienThiChonThongSoWan (data) {
      console.log('🚀 ~ file: index.vue:762 ~ CloseHienThiChonThongSoWan ~ data:', data)
      try {
        this.Capnhatthongso = data.Capnhat
        if (this.Capnhatthongso) {
          this.parent.thongso_ban = data.ThongsoBan
          this.parent.thongso_tc = data.ThongsoTc
          if (this.parent.thongso_ban.tocdo_id > 0) this.$refs.refWan.cboTocDo.Value = this.parent.thongso_ban.tocdo_id
          if (this.parent.thongso_ban.tocdo_pir_id > 0) {
            this.$refs.refWan.chkTocDo_PIR.Checked = true
            this.$refs.refWan.cboTocDo_PIR.Value = this.parent.thongso_ban.tocdo_pir_id
          }
          // if (this.parent.thongso_ban.chuquan_id > 0) this.$refs.refWan.cboChuQuanWan.Value = this.parent.thongso_ban.chuquan_id
          if (this.parent.thongso_ban.loaimd_id > 0) this.$refs.refWan.cboLoaiModem.Value = this.parent.thongso_ban.loaimd_id
          if (this.parent.thongso_ban.thietbidc_id > 0) this.$refs.refWan.cboLoaiTBiWan.Value = this.parent.thongso_ban.thietbidc_id
          if (this.parent.thongso_ban.trangbi_id > 0) this.$refs.refWan.cboTrangBiWan.Value = this.parent.thongso_ban.trangbi_id
          if (this.parent.thongso_ban.doituong_id > 0) this.parent.modelCbo.cboDoituongTB = this.parent.thongso_ban.doituong_id
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 178 ~ CloseHienThiChonThongSoWan ~ error', error)
      }
    },
    async HienThiChonThongSoWan () {
      try {
        if (this.$refs.ref_frmThongSoWan) {
          this.$refs.ref_frmThongSoWan.ThongsoBan = this.parent.thongso_ban
          console.log('🚀 ~ file: index.vue:787 ~ HienThiChonThongSoWan ~ this.$refs.ref_frmThongSoWan.ThongsoBan:', this.$refs.ref_frmThongSoWan.ThongsoBan)
          this.$refs.ref_frmThongSoWan.ThongsoTc = this.parent.thongso_tc
          console.log('🚀 ~ file: index.vue:789 ~ HienThiChonThongSoWan ~ this.$refs.ref_frmThongSoWan.ThongsoTc:', this.$refs.ref_frmThongSoWan.ThongsoTc)
          this.$refs.ref_frmThongSoWan.khachhang_id = this.parent.khachhang_tc_id
          this.$refs.ref_frmThongSoWan.hdkh_id = this.parent.hdkh_id
          await this.$refs.ref_frmThongSoWan.openDialog2(this.parent.BanCheoData.Tinh_TC, this.parent.loaitb_id, this.parent.loaitb_id, Enum.LoaiHopDong.DI_CHUYEN, this.parent.dichvuvt_id, this.parent.kenh_trang, this.parent.thongso_ban, this.parent.thongso_tc)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 203 ~ HienThiChonThongSoWan ~ error', error)
      }
    },
    async HienThiChonThongSoADSL () {
      try {
        console.log('🚀 ~ file: index.vue:805 ~ HienThiChonThongSoADSL ~ this.parent.thongso_tc:', this.parent.thongso_tc)
        console.log('🚀 ~ file: index.vue:805 ~ HienThiChonThongSoADSL ~ this.parent.thongso_ban:', this.parent.thongso_ban)
        await this.$refs.TocDoMucCuocDialog.$children[0].clearData()
        await this.$refs.TocDoMucCuocDialog.$children[0].loadData()
        this.$refs.TocDoMucCuocPopup.khachhang_id = this.parent.khachhang_tc_id
        this.$refs.TocDoMucCuocPopup.hdkh_id = this.parent.hdkh_id
        this.$refs.TocDoMucCuocDialog.show()
      } catch (error) {
        console.log('🚀 ~ file: index.vue:799 ~ HienThiChonThongSoADSL ~ error:', error)
      }
    },
    async HienThiChonThongSoCoDinh (loaitb_id, loaitb_id_moi) {
      try {
        console.log('🚀 ~ file: index.vue:805 ~ HienThiChonThongSoCoDinh ~ this.parent.thongso_tc:', this.parent.thongso_tc)
        console.log('🚀 ~ file: index.vue:805 ~ HienThiChonThongSoCoDinh ~ this.parent.thongso_ban:', this.parent.thongso_ban)
        this.$refs.frmThongSoCoDinh.showModal(this.parent.BanCheoData.Tinh_TC.tinhthicong_id, loaitb_id, loaitb_id_moi, 0, 0, Enum.LoaiHopDong.DI_CHUYEN)
      } catch (error) {
        console.log('🚀 ~ file: index.vue:799 ~ HienThiChonThongSoCoDinh ~ error:', error)
      }
    },
    acceptTocDoMucCuoc (data) {
      try {
        console.log('🚀 ~ file: index.vue:786 ~ acceptTocDoMucCuoc ~ data:', data)
        this.$refs.TocDoMucCuocDialog.hide()
        if (data) {
          this.parent.thongso_ban = {...data.thongso_ban}
          this.parent.thongso_tc = {...data.thongso_tc}

          this.$refs.refAdsl.cboGoiCuocAdsl.Value = data.thongso_ban.tocdo_id

          this.$refs.refAdsl.cboTrangBiADSL.Value = data.thongso_ban.trangbi_id
          this.$refs.refAdsl.cboMucCuoc.Value = data.thongso_ban.muccuoc_id

          this.parent.modelCbo.cboDoituongTB = data.thongso_ban.doituong_id
          this.parent.modelCbo.cboMucCuocTB = data.thongso_ban.muccuoctb_id
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:817 ~ acceptTocDoMucCuoc ~ error:', error)
      }
    },
    async txtMaTB_KeyPress () {
      try {
        this.loading(true)
        this.parent.modelInput.txtMaTB = this.clearTheSpace(this.parent.modelInput.txtMaTB)
        this.parent.modelInput.txtMaTB = this.Catdaunhay(this.parent.modelInput.txtMaTB)
        if (this.parent.modelInput.txtMaTB !== null && this.parent.modelInput.txtMaTB !== '') {
          this.parent.BanCheoData.Tinh_TC = await this.LAY_TINHTHICONG_THEO_MATB(this.parent.modelInput.txtMaTB)
          console.log('🚀 ~ file: index.vue ~ line 140 ~ txtMaTB_KeyPress ~ this.parent.BanCheoData.Tinh_TC', this.parent.BanCheoData.Tinh_TC)
          if (this.parent.BanCheoData.Tinh_TC.tinhthicong_id === 0) {
            this.parent.kenh_trang = true
            this.parent.chi_giao_net = false

            if (this.parent.dichvuvt_id !== Enum.DichVuVienThong.TSL &&
                                this.parent.dichvuvt_id !== Enum.DichVuVienThong.MEGAWAN &&
                                this.parent.dichvuvt_id !== 26 && this.parent.dichvuvt_id !== 25 &&
                                this.parent.dichvuvt_id !== Enum.DichVuVienThong.METRONET) {
              this.loading(false)
              this.$root.$toast.warning('Thuê bao chưa map bán chéo tại tỉnh thi công.')
              this.parent.Clear()
              return false
            }
            try {
              this.loading(false)
              let confirm = await this.$confirm('Thuê bao không có trong danh bạ bán chéo. Bạn phải chọn tỉnh thi công !', 'Thông báo', {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không'
              })
              console.log('🚀 ~ file: index.vue:880 ~ txtMaTB_KeyPress ~ confirm:', confirm)
              if (confirm) {
                try {
                  this.loading(true)
                  if (this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || this.parent.dichvuvt_id === 25) {
                    let result = await this.$refs.frmChonTinhTSL.show()
                    console.log('🚀 ~ file: index.vue ~ line 173 ~ txtMaTB_KeyPress ~ result', result.item)
                    this.parent.tinh_dau = result.item.tinhdau
                    this.parent.tinh_dau_id = this.parent.tinh_dau.tinhthicong_id

                    this.parent.tinh_cuoi = result.item.tinhcuoi
                    this.parent.tinh_cuoi_id = this.parent.tinh_cuoi.tinhthicong_id
                  } else {
                    let result = await this.$refs.frmChonTinh.show()
                    console.log('🚀 ~ file: index.vue ~ line 183 ~ txtMaTB_KeyPress ~ result', result.item)
                    this.parent.BanCheoData.Tinh_TC = result.item
                  }
                } catch (error) {
                  console.log('🚀 ~ file: index.vue ~ line 187 ~ txtMaTB_KeyPress ~ error', error)
                  this.loading(false)
                }
              }
            } catch (error) {
              console.log('🚀 ~ file: index.vue ~ line 167 ~ txtMaTB_KeyPress ~ error', error)
              this.parent.Clear()
              this.loading(false)
              return false
            }
            this.parent.thuebao_tc_id = 0
          } else {
            this.parent.kenh_trang = false
            if (this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || this.parent.dichvuvt_id === 25) {
              // let dtTinh_id_tsl = await this.LAY_TINHTC_ID_TSL(this.parent.modelInput.txtMaTB, Enum.DichVuVienThong.TSL)
              let dtTinh_id_tsl = await this.LAY_TINHTC_ID_TSL(this.parent.modelInput.txtMaTB, this.parent.dichvuvt_id)
              if (dtTinh_id_tsl && dtTinh_id_tsl.length > 1) {
                this.parent.tinh_dau_id = parseInt(dtTinh_id_tsl.filter(e => e.daucuoi_id === 1)[0].tinh_thicong)
                this.parent.tinh_cuoi_id = parseInt(dtTinh_id_tsl.filter(e => e.daucuoi_id === 2)[0].tinh_thicong)
                this.parent.thuebao_id_dau = parseInt(dtTinh_id_tsl.filter(e => e.daucuoi_id === 1)[0].thuebao_kn_id)
                this.parent.thuebao_id_cuoi = parseInt(dtTinh_id_tsl.filter(e => e.daucuoi_id === 2)[0].thuebao_kn_id)
                this.parent.tinh_dau = await this.tinh_thicong(this.parent.tinh_dau_id)
                this.parent.tinh_cuoi = await this.tinh_thicong(this.parent.tinh_cuoi_id)
              } else {
                if (dtTinh_id_tsl && dtTinh_id_tsl.length === 1) {
                  let tinh_id = parseInt(dtTinh_id_tsl[0].tinh_thicong)
                  this.parent.BanCheoData.Tinh_TC = await this.tinh_thicong(tinh_id)
                  this.parent.thuebao_id = parseInt(dtTinh_id_tsl[0].thuebao_kn_id)
                }
              }
            }
            this.parent.thuebao_tc_id = await this.LAY_THUEBAOID_THICONG_THEO_MATB(this.parent.BanCheoData.Tinh_TC.tinhthicong_id, this.parent.modelInput.txtMaTB)
            let thuebao_kn_id = await this.GET_THUEBAO_KN_ID(this.parent.modelInput.txtMaTB)
            if (thuebao_kn_id === -1) {
              this.parent.chi_giao_net = true
            } else {
              this.parent.chi_giao_net = false
            }
            if (this.parent.dichvuvt_id !== Enum.DichVuVienThong.IMS &&
                                this.parent.dichvuvt_id !== Enum.DichVuVienThong.CO_DINH &&
                                this.parent.dichvuvt_id !== Enum.DichVuVienThong.GPHONE) {
              this.parent.dsDBTBBC = await this.LAY_DANHBA_THEO_MATBTC(this.parent.BanCheoData.Tinh_TC, this.parent.modelInput.txtMaTB, this.parent.modelCbo.cboDichVuVT, 0, 2)
            } else {
              let txtMaTB = this.parent.modelInput.txtMaTB.substring(this.parent.BanCheoData.Tinh_TC.mavung.length - 1)
              this.parent.dsDBTBBC = await this.LAY_DANHBA_THEO_MATBTC(this.parent.BanCheoData.Tinh_TC, txtMaTB, this.parent.modelCbo.cboDichVuVT, 0, 2)
            }
          }
          let ds = []
          let donvi_id = this.$root.token.getDonViID()
          let nhanvien_id = this.$root.token.getNhanVienID()
          if (this.parent.BanCheoData.Tinh_TC.tinhthicong_id > 0 && this.parent.kenh_trang === false) {
            ds = await this.TraCuu_LAY_DS_HOPDONG_THEO_MA_TB(this.parent.modelInput.txtMaTB, Enum.LoaiHopDong.DI_CHUYEN, Number(donvi_id), Enum.TrangThaiHD.MOI, Number(nhanvien_id), 0, this.parent.dichvuvt_id, 0)
            console.log('🚀 ~ file: index.vue:923 ~ txtMaTB_KeyPress ~ ds:', ds)
          } else {
            ds = await this.TCHopDong_LAY_DS_HOPDONG_THEO_MA_TB(this.parent.modelInput.txtMaTB, Enum.LoaiHopDong.DI_CHUYEN, donvi_id, Enum.TrangThaiHD.MOI, nhanvien_id, 0, this.parent.dichvuvt_id, 0)
            console.log('🚀 ~ file: index.vue ~ line 216 ~ txtMaTB_KeyPress ~ ds', ds)
          }
          if (ds !== null && ds.length > 0) {
            this.parent.modelInput.txtDiaChiLD = ''
            this.parent.modelInput.txtDiaChiTB = ''
            this.parent.modelInput.txtNgaySinhTB = ''
            await this.HienThiTTHopDongKH(ds)
          } else {
            let kieuld_id = parseInt(this.parent.modelCbo.cboKieuLD)
            this.parent.ikieuld_id = kieuld_id
            this.parent.modelInput.txtDiaChiLD = ''
            this.parent.modelInput.txtDiaChiTB = ''
            this.parent.modelInput.txtNgaySinhTB = ''
            if (this.parent.hdkh_id !== 0 && this.parent.hdkh_id !== null) {
              ds = await this.LAY_DANHBA_THEO_MATB(this.parent.modelInput.txtMaTB, this.parent.dichvuvt_id, 0)
              if (ds !== null && ds.length > 0) { await this.HienThiTT_HopDong_ThueBao(ds) } else {
                this.loading(false)
                this.$root.$toast.warning('Không tìm thấy thông tin thuê bao ' + this.parent.modelInput.txtMaTB + ' ứng với dịch vụ ' + this.parent.modelCbo.cboDichVuVT)
                return false
              }
            } else {
              await this.HienThiTT_DanhBa_KH_NhanCQ(this.parent.modelInput.txtMaTB, 1)
            }
          }
        }
        this.loading(false)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 138 ~ txtMaTB_KeyPress ~ error', error)
        this.loading(false)
      }
    },
    async frmChonTinhTSL_ChonTinh (data) {
      console.log('🚀 ~ file: index.vue ~ line 275 ~ frmChonTinhTSL_ChonTinh ~ data', data)
    },
    async frmChonTinh_ChonTinh (data) {
      console.log('🚀 ~ file: index.vue ~ line 278 ~ frmChonTinh_ChonTinh ~ data', data)
    },
    async HienThiTT_DanhBa_KH_NhanCQ (ma_tb, kieu_id) {
      try {
        let ds = []
        if (kieu_id === 0) {
          ds = await this.LAY_DB_THEO_MATB(ma_tb, 0, 0)
        } else {
          ds = await this.LAY_DANHBA_THEO_MATB(ma_tb, this.parent.dichvuvt_id, 0)
        }
        console.log('🚀 ~ file: index.vue ~ line 249 ~ HienThiTT_DanhBa_KH_NhanCQ ~ ds', ds)
        this.parent.kieu_thueso = 0
        this.parent.noidung_thueso = ''
        this.parent.sothang_thueso = 0
        if (ds !== null && ds.length > 0) {
          if (ds.length > 1) {
            let result = await this.$refs.thuebaocungma.show(ds)
            ds = [result.itemSelected]
          }
          this.parent.modelInput.txtMaTB = ds[0].ma_tb
          let kiemtra = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.parent.modelInput.txtMaTB, '0', this.parent.modelCbo.cboDichVuVT, Enum.LoaiHopDong.DI_CHUYEN)
          if (kiemtra) return false
          try {
            let dsTS = await this.parent.LAY_DS_THAMSO_MD_MATS('KTRA_TRANGTHAI_DICHCHUYEN')
            let ktratt = -1
            if (dsTS !== null && dsTS.length > 0) {
              ktratt = parseInt(dsTS[0].ten_ts)
            }
            if (ktratt === 1) {
              if (parseInt(ds[0].trangthaitb_id) !== Enum.TrangThaiTB.THUONG) {
                this.$root.$toast.warning('Thuê bao ' + ds[0].ma_tb +
                                                ' đang ở trạng thái: ' + ds[0].trangthai_tb +
                                                '\nBạn không thể thực hiện chuyển dịch cho thuê bao này')
                return false
              }
            } else {
              if (parseInt(ds[0].trangthaitb_id) !== Enum.TrangThaiTB.THUONG &&
                            parseInt(ds[0].trangthaitb_id) !== Enum.TrangThaiTB.KHOA_MOTCHIEU_YEUCAU &&
                            parseInt(ds[0].trangthaitb_id) !== Enum.TrangThaiTB.KHOA_HAICHIEU_YEUCAU &&
                            parseInt(ds[0].trangthaitb_id) !== Enum.TrangThaiTB.TAMDUNG) {
                this.$root.$toast.warning('Thuê bao ' + ds[0].ma_tb +
                                                ' đang ở trạng thái: ' + ds[0].trangthai_tb +
                                                '\nBạn không thể thực hiện chuyển dịch cho thuê bao này')
                return false
              }
            }
          } catch (error) {
            console.log('🚀 ~ file: index.vue ~ line 267 ~ HienThiTT_DanhBa_KH_NhanCQ ~ error', error)
          }
          if (parseInt(ds[0].ky_hd) === 0) {
            this.$root.$toast.warning('Khách hàng có mã thuê bao: ' + this.parent.modelInput.txtMaTB + ' chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc')
            return false
          }
          this.parent.ikieuld_id = 0

          if (ds[0].ngaylap_hd !== null) {
            this.parent.modelDateTime.dtpNgayLapHD = new Date(ds[0]['ngaylap_hd'])
          } else {
            this.parent.modelDateTime.dtpNgayLapHD = new Date()
          }
          this.parent.modelDateTime.dtpNgayYC = new Date()

          // Thông tin khách hàng
          this.parent.modelInput.txtMaKH = ds[0]['ma_kh']

          this.parent.khachhang_id = parseInt(ds[0]['khachhang_id'])
          this.parent.khach_hang_id = parseInt(ds[0]['khachhang_id'])
          this.parent.khachhang_tc_id = await this.LAY_KHACHHANGID_THICONG(this.parent.BanCheoData.Tinh_TC, this.parent.khachhang_id)
          this.parent.modelInput.txtTenKH = this.parent.ChuanHoaTen(ds[0]['ten_kh'])
          if (ds[0]['loaikh_id'] !== null) {
            this.parent.modelCbo.cboLoaiKH = parseInt(ds[0]['loaikh_id'])
          }
          try {
            let strPLKH = await this.MAP_ID('PHANLOAIKH_ID', this.parent.khachhang_id)
            if (strPLKH !== null) {
              this.parent.PHANLOAIKH_ID = parseInt(strPLKH)
            }
          } catch (error) {
            console.log('🚀 ~ file: index.vue ~ line 317 ~ HienThiTT_DanhBa_KH_NhanCQ ~ error', error)
          }
          this.parent.khachhang_id_cu = parseInt(ds[0]['khachhang_id'])
          if (this.parent.kieukh_canhan && ds[0]['nguoi_dd'] === null) { this.parent.modelInput.txtNguoiDD = this.parent.modelInput.txtTenKH } else { this.parent.modelInput.txtNguoiDD = this.parent.ChuanHoaTen(ds[0]['nguoi_dd']) }
          if (ds[0]['diachi_ndd'] === null) { this.parent.modelInput.txtDiaChiNDD = this.parent.ChuanHoaTen(ds[0]['diachi_kh']) } else { this.parent.modelInput.txtDiaChiNDD = this.parent.ChuanHoaTen(ds[0]['diachi_ndd']) }
          this.parent.modelInput.txtChucDanhDD = this.parent.ChuanHoaTen(ds[0]['chucdanh'])
          this.parent.modelInput.txtDiaChiKH = this.parent.ChuanHoaTen(ds[0]['diachi_kh'])
          let ds2 = await this.LAY_DS_DIACHI_THEO_DBKHID(this.parent.khachhang_id)
          await this.HienThiTTDiaChi(ds2, 1)
          this.parent.modelInput.txtTelKH = ds[0]['so_dt']
          this.parent.modelInput.txtFaxKH = ds[0]['so_fax']

          this.parent.modelInput.txtHoKhau = this.parent.ChuanHoaTen(ds[0]['hokhau'])
          this.parent.modelInput.txtNoiCapHK = this.parent.ChuanHoaTen(ds[0]['noicap_hk'])

          if (ds[0]['ngay_hk'] !== null) { this.parent.modelInput.txtNgayCapHK = DateTimeLib.toDateDisplay(new Date(ds[0]['ngay_hk'])) } else { this.parent.modelInput.txtNgayCapHK = '' }
          this.parent.modelCbo.cboKhachHangLon = parseInt(ds[0]['khlon_id'])

          if (ds[0]['ngay_sn'] !== null) { this.parent.modelInput.txtNgaySN = DateTimeLib.toDateDisplay(new Date(ds[0]['ngay_sn'])) } else { this.parent.modelInput.txtNgaySN = '' }

          if (ds[0]['loaigt_id'] !== null) { this.parent.modelCbo.cboGiayToKH = parseInt(ds[0]['loaigt_id']) }
          this.parent.modelInput.txtSoGT = ds[0]['so_gt']
          if (ds[0]['ngaycap'] !== null) { this.parent.modelInput.txtNgayCap = DateTimeLib.toDateDisplay(ds[0]['ngaycap']) } else { this.parent.modelInput.txtNgayCap = '' }
          this.parent.modelInput.txtNoiCap = this.parent.ChuanHoaTen(ds[0]['noicap'])
          this.parent.modelInput.txtMaTT = ds[0]['ma_tt']
          this.parent.modelInput.txtMaIG = ds[0]['main_ghep']
          if (ds[0]['mig_id'] !== null) {
            this.parent.mig_id = parseInt(ds[0]['mig_id'])
          }
          this.parent.thanhtoan_id = parseInt(ds[0]['thanhtoan_id'])
          let dt_madd = await this.HT_COMBO_MA_DAIDIEN(this.parent.thanhtoan_id)
          this.parent.modelCbo.vcboMaDD = dt_madd
          this.parent.modelCbo.cboMaDD = ds[0]['matb_dd']

          this.parent.modelInput.txtTenTT = this.parent.ChuanHoaTen(ds[0]['ten_tt'])
          this.parent.modelInput.txtDiaChiTT = this.parent.ChuanHoaTen(ds[0]['diachi_tt'])
          this.parent.modelInput.txtDiaChiBC = this.parent.ChuanHoaTen(ds[0]['diachi_bc'])
          this.parent.modelInput.txtDiaChiCT = this.parent.ChuanHoaTen(ds[0]['diachi_ct'])
          this.parent.modelInput.txtGhiChuTT = this.parent.ChuanHoaTen(ds[0]['ghichu'])

          this.parent.modelInput.txtMaTBDD = ds[0]['matb_dd']
          await this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBTTID(this.parent.thanhtoan_id), 2)

          this.parent.DV_QHNS = ds[0]['dv_qhns']
          this.parent.MACHUONG = ds[0]['machuong']
          this.parent.MN_KT = ds[0]['mn_kt']
          this.parent.MA_NDKT = ds[0]['ma_ndkt']
          this.parent.MN_NSNN = ds[0]['mn_nsnn']

          if (ds[0]['tien_dm'] !== null) { this.parent.TIEN_DM = parseInt(ds[0]['tien_dm']) } else { this.parent.TIEN_DM = 0 }

          this.parent.modelInput.txtMST = ds[0]['mst_tt']
          this.parent.modelInput.txtSoTKKH = ds[0]['stk']
          if (ds[0]['nganhang_id_tt'] !== null) { this.parent.modelCbo.cboNganHangTT = parseInt(ds[0]['nganhang_id_tt']) }
          this.parent.lock = true
          this.parent.modelCbo.cboHTTT = parseInt(ds[0]['httt_id'])
          this.parent.modelCbo.cboDonViQL = parseInt(ds[0]['donvi_id'])

          console.log("🚀 ~ file: index.vue:1123 ~ HienThiTT_DanhBa_KH_NhanCQ ~ ds[0]['nhanvien_id']:", ds[0]['nhanvien_id'])
          if (ds[0]['nhanvien_id'] !== null) {
            this.parent.nhanvientc_id = parseInt(ds[0]['nhanvien_id'])
            this.parent.modelInput.txtMaNVTC = await this.MAP_ID('MA_NV', this.parent.nhanvientc_id)
            console.log('🚀 ~ file: index.vue:1126 ~ HienThiTT_DanhBa_KH_NhanCQ ~ this.parent.modelInput.txtMaNVTC:', this.parent.modelInput.txtMaNVTC)
          } else {
            this.parent.nhanvientc_id = 0
            this.parent.modelInput.txtMaNVTC = ''
            console.log("🚀 ~ file: index.vue:1130 ~ HienThiTT_DanhBa_KH_NhanCQ ~ this.parent.modelInput.txtMaNVTC = '':", this.parent.modelInput.txtMaNVTC = '')
          }
          console.log("🚀 ~ file: index.vue:1131 ~ HienThiTT_DanhBa_KH_NhanCQ ~ ds[0]['tuyenthu_id']:", ds[0]['tuyenthu_id'])
          if (ds[0]['tuyenthu_id'] !== null) {
            this.parent.tuyenthu_id = parseInt(ds[0]['tuyenthu_id'])
            this.parent.modelInput.txtMaTuyen = await this.MAP_ID('MA_TUYEN', this.parent.tuyenthu_id)
            console.log('🚀 ~ file: index.vue:1133 ~ HienThiTT_DanhBa_KH_NhanCQ ~ this.parent.modelInput.txtMaTuyen:', this.parent.modelInput.txtMaTuyen)
          } else {
            this.parent.tuyenthu_id = 0
            this.parent.modelInput.txtMaTuyen = ''
          }
          this.parent.matb_dd = ds[0]['matb_dd']

          this.parent.modelInput.txtSTKTT = ds[0]['stk_tt']
          this.parent.modelInput.txtChu_TK = ds[0]['chu_tk_tt']

          // Hiếu bổ sung:10/08/2010
          this.parent.modelInput.txtMaSTKH = ds[0]['mst']
          this.parent.modelInput.txtSoTKKH = ds[0]['stk']
          this.parent.modelInput.txtNgayHetHan = ds[0]['ngayhethan_gt'] ? DateTimeLib.toDateDisplay(new Date(ds[0]['ngayhethan_gt'])) : null

          if (ds[0]['gioitinh'] !== null) {
            let iGioiTinh = parseInt(ds[0]['gioitinh'])
            if (iGioiTinh === 0) {
              this.parent.rdoGioiTinh = 0
            } else {
              this.parent.rdoGioiTinh = 1
            }
          } else { this.parent.rdoGioiTinh = null }

          if (ds[0]['quoctich_id'] !== null) {
            this.parent.isEnableCboQuocTich = true
            this.parent.modelCbo.cboQuocTich = parseInt(ds[0]['quoctich_id'])
          } else {
            this.parent.isEnableCboQuocTich = false
            this.parent.modelCbo.cboQuocTich = -1
          }

          if (ds[0]['dantoc_id'] !== null) {
            this.parent.isEnableCboDanToc = true
            this.parent.modelCbo.cboDanToc = parseInt(ds[0]['dantoc_id'])
          } else {
            this.parent.isEnableCboDanToc = false
            this.parent.modelCbo.cboDanToc = -1
          }

          if (ds[0]['nganhnghe_id'] !== null) {
            this.parent.cboNganhNgheLoc_LockEvents = true
            this.parent.isEnableCboNganhNgheLoc = true
            this.parent.modelCbo.cboNganhNgheLoc = parseInt(ds[0]['nganhnghe_id'])
            this.parent.nganhnghe_id = parseInt(ds[0]['nganhnghe_id'])
            this.parent.cboNganhNgheLoc_LockEvents = false
          } else {
            this.parent.isEnableCboNganhNgheLoc = false
            this.parent.modelCbo.cboNganhNgheLoc = -1
          }

          // TuấnNA thêm code hiển thị thông tin id tiểu ngành c1,c2,c3 -- Ngày 03/12/2010
          if (ds[0]['tnc1_id'] !== null) { this.parent.TNC1_ID = parseInt(ds[0]['tnc1_id']) }
          if (ds[0]['tnc2_id'] !== null) { this.parent.TNC2_ID = parseInt(ds[0]['tnc2_id']) }
          if (ds[0]['tnc3_id'] !== null) { this.parent.TNC3_ID = parseInt(ds[0]['tnc3_id']) }
          if (ds[0]['tnc4_id'] !== null) { this.parent.TNC4_ID = parseInt(ds[0]['tnc4_id']) }
          //
          await this.LAY_DS_SUDUNG_DVK(this.parent.thanhtoan_id, 2)

          if (ds[0]['nganhang_id'] !== null) {
            this.parent.modelCbo.cboNganHangKH = parseInt(ds[0]['nganhang_id'])
            this.parent.modelCkeckBox.chkNganHangKH = true
          }
          this.parent.modelInput.txtGhiChuKH = ds[0]['ghichu']
          if (kieu_id === 1) {
            this.parent.modelInput.txtMaHDcu = ds[0]['ma_hd']
            this.parent.modelInput.txtDiaChiTB = this.parent.ChuanHoaTen(ds[0]['diachi_tb'])
            this.parent.modelInput.txtDiaChiLD = this.parent.ChuanHoaTen(ds[0]['diachi_ld'])
            this.parent.thuebao_id = parseInt(ds[0]['thuebao_id'])
            if (this.parent.BanCheoData.Tinh_TC.tinhthicong_id > 0) { this.parent.thuebao_tc_id = await this.LAY_THUEBAOID_THICONG(this.parent.BanCheoData.Tinh_TC.tinhthicong_id, this.parent.thuebao_id) } else { this.parent.thuebao_tc_id = 0 }
            let dsNV = await this.LAY_DS_KHUVUC_THEO_HD_DB(this.parent.thuebao_id, 2)
            console.log('🚀 ~ file: index.vue ~ line 461 ~ HienThiTT_DanhBa_KH_NhanCQ ~ dsNV', dsNV)
            this.parent.modelInput.txtKhuVuc = ''
            this.parent.khuvuc_id = 0

            if (ds[0]['mucuoctb_id'] !== null) {
              await this.parent.HT_MucCuocTB_Combobox(parseInt(ds[0]['mucuoctb_id']))
            } else {
              this.parent.modelCbo.vcboMucCuocTB = []
              this.parent.modelCbo.cboMucCuocTB = -1
            }

            let chk = await this.gan_thongso_theodanhba(1, this.parent.dichvuvt_id, this.parent.thuebao_id)
            if (!chk) return
            chk = await this.gan_thongso_theodanhba(2, this.parent.dichvuvt_id, this.parent.thuebao_tc_id)
            if (!chk) return

            if (dsNV != null && dsNV.length > 0) {
              this.parent.modelInput.txtKhuVuc = dsNV[0]['ten_kv']
              this.parent.khuvuc_id = parseInt(dsNV[0]['khuvuc_id'])
            }
            this.parent.modelInput.txtMaTB = ds[0]['ma_tb']

            this.parent.modelInput.txtTenTB = this.parent.ChuanHoaTen(ds[0]['ten_tb'])
            this.parent.modelCbo.cboDaiVT = parseInt(ds[0]['donviql_id'])

            if (ds[0]['ngay_sn_tb'] !== null) { this.parent.modelInput.txtNgaySinhTB = DateTimeLib.toDateDisplay(new Date(ds[0]['ngay_sn_tb'])) } else { this.parent.modelInput.txtNgaySinhTB = '' }

            await this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBTBID(this.parent.thuebao_id), 3)
            this.parent.modelCbo.cboDoituongTB = parseInt(ds[0]['doituong_id'])
            this.parent.modelCbo.cboLoaihinhTB = parseInt(ds[0]['loaitb_id'])
            await this.cboLoaihinhTB_SelectedValueChanged()

            this.parent.loaitb_id = parseInt(ds[0]['loaitb_id'])
            this.parent._loaitb_id = parseInt(ds[0]['loaitb_id'])
            let dtTemp = await this.GetDataSql('DBTB_DV', this.parent.thuebao_id)
            console.log('🚀 ~ file: index.vue ~ line 481 ~ HienThiTT_DanhBa_KH_NhanCQ ~ dtTemp', dtTemp)
            if (dtTemp.length > 0) {
              console.log('🚀 ~ file: index.vue ~ line 565 ~ HienThiTT_DanhBa_KH_NhanCQ ~ dtTemp', dtTemp)
              let dtControl = await this.parent.getControl()
              for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
                const element = dtControl.dsComboDonVi[index]
                let dr = dtTemp.filter(e => e.loaidv_id === element.loaidv_id)
                if (dr.length > 0) {
                  dtControl.ref.$refs[element.key][0].value = dr[0].donvi_id
                }
              }
            }
            await this.HienThiTTMoRongTB(this.parent.thuebao_id, this.parent.dichvuvt_id)
            this.parent.m_dsThueBao_DichVu.dsdvgt_ban = await this.TC_LAY_DS_THUEBAO_DICHVU(0, this.parent.thuebao_id, this.parent.loaitb_id, 1)
            if (this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || this.parent.dichvuvt_id === 25) {
              let t1 = await this.BANCHEO_LAY_DS_THUEBAO_DICHVU(this.parent.tinh_dau.tinhthicong_id, 0, this.parent.thuebao_id_dau, this.parent.loaitb_id, 1)
              let dtTBDV = {
                DS1: t1
              }
              this.parent.m_dsThueBao_DichVu.dsdvgt_tc.push(dtTBDV)
              let t2 = await this.BANCHEO_LAY_DS_THUEBAO_DICHVU(this.parent.tinh_cuoi.tinhthicong_id, 0, this.parent.thuebao_id_cuoi, this.parent.loaitb_id, 1)
              dtTBDV = {
                DS2: t2
              }
              this.parent.m_dsThueBao_DichVu.dsdvgt_tc.push(dtTBDV)
            } else {
              let t3 = await this.BANCHEO_LAY_DS_THUEBAO_DICHVU(this.parent.BanCheoData.Tinh_TC.tinhthicong_id, 0, this.parent.thuebao_tc_id, this.parent.loaitb_id, 1)
              this.parent.m_dsThueBao_DichVu.dsdvgt_tc = t3
            }
            let dset = await this.TC_LAY_DS_SUDUNG_DVGT(this.parent.thuebao_id)
            await this.HT_DichVuGT_DB(dset)
            await this.LAY_DS_SUDUNG_DVK(this.parent.thuebao_id, 1)

            await this.HienThi_NVQL_AM_DB_HD(1, this.parent.thanhtoan_id)
            await this.HT_DS_KhuyenMai_Combobox()

            if (this.parent.khong_ht_tracuudb === 0) {
            }
          }
          if (this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || this.parent.dichvuvt_id === 25) { await this.HT_COMBO_DONVI() }
        } else {
          this.$root.$toast.error('Không tìm thấy thông tin về thuê bao ' + this.parent.modelInput.txtMaTB + ' đối với dịch vụ ' + this.getTenDichVu(this.parent.modelCbo.cboDichVuVT))
          this.parent.SetButton(1)
        }
        this.parent.lock = false
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 246 ~ HienThiTT_DanhBa_KH_NhanCQ ~ error', error)
      }
    },
    getTenDichVu (id) {
      let ds = this.parent.modelCbo.vcboDichVuVT.find(x => x.dichvuvt_id.toString() === id.toString())
      if (ds !== undefined) {
        return ds.ten_dvvt
      } else return id
    },
    async LAY_DS_DIACHI_THEO_DBTBID (THUEBAO_ID) {
      try {
        var data = {THUEBAO_ID}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_diachi_theo_dbtbid', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async gan_thongso_theodanhba (vkieu, vdichvu, vthuebaoid) {
      console.log('🚀 ~ file: index.vue:1276 ~ gan_thongso_theodanhba ~ vkieu, vdichvu, vthuebaoid:', vkieu, vdichvu, vthuebaoid)
      try {
        let ds = []
        if (vkieu === 1) { this.parent.thongso_ban = this.parent.Reset_Thongso_Thuebao() } else { this.parent.thongso_tc = this.parent.Reset_Thongso_Thuebao() }
        if (vdichvu === 1) {
          if (vkieu === 1) {
            ds = await this.LAY_DS_DB_CD_THEO_TBID(vthuebaoid)
            if (ds.length > 0) {
              this.parent.thongso_ban.trangbi_id = parseInt(ds[0]['trangbi_id'])
              this.parent.thongso_ban.doituong_id = parseInt(this.parent.modelCbo.cboDoituongTB)
              this.parent.thongso_ban.muccuoctb_id = parseInt(this.parent.modelCbo.cboMucCuocTB)
            }
          } else {
            ds = await this.BANCHEO_LAY_DS_DB_CD_THEO_TBID(this.parent.BanCheoData.Tinh_TC, vthuebaoid)
            if (ds.length > 0) {
              this.parent.thongso_tc.trangbi_id = parseInt(ds[0]['trangbi_id'])
              if (this.parent.dsDBTBBC.length > 0) {
                this.parent.thongso_tc.doituong_id = parseInt(this.parent.dsDBTBBC[0]['doituong_id'])
                this.parent.thongso_tc.muccuoctb_id = parseInt(this.parent.dsDBTBBC[0]['mucuoctb_id'])
              }
            }
          }
        }

        if (vdichvu === 11) {
          if (vkieu === 1) {
            ds = await this.LAY_DS_DB_IMS_THEO_TBID(vthuebaoid)
            console.log('🚀 ~ file: index.vue:1338 ~ gan_thongso_theodanhba ~ ds:', ds)
            if (ds !== null && ds.length > 0) {
              this.parent.thongso_ban.trangbi_id = parseInt(ds[0]['trangbi_id'])
              this.parent.thongso_ban.doituong_id = parseInt(this.parent.modelCbo.cboDoituongTB)
              this.parent.thongso_ban.muccuoctb_id = parseInt(this.parent.modelCbo.cboMucCuocTB)
            }
          } else {
            ds = await this.BANCHEO_LAY_DS_DB_IMS_THEO_TBID(this.parent.BanCheoData.Tinh_TC, vthuebaoid)
            console.log('🚀 ~ file: index.vue:1345 ~ gan_thongso_theodanhba ~ ds:', ds)
            if (ds !== null && ds.length > 0) {
              this.parent.thongso_tc.trangbi_id = parseInt(ds[0]['trangbi_id'])
              if (this.parent.dsDBTBBC.length > 0) {
                this.parent.thongso_tc.doituong_id = parseInt(this.parent.dsDBTBBC[0]['doituong_id'])
                this.parent.thongso_tc.muccuoctb_id = parseInt(this.parent.dsDBTBBC[0]['mucuoctb_id'])
              }
            }
          }
        }
        if (vdichvu === 8 || vdichvu === 7) {
          let chk = await this.Gan_thongso_bancheo_thuebaoid(this.parent.thuebao_id, this.parent.thuebao_tc_id, this.parent.BanCheoData.Tinh_TC.tinhthicong_id)
          return chk
        }
        if (vdichvu === 4) {
          if (vkieu === 1) {
            ds = await this.LAY_DS_DB_ADSL_THEO_TBID(vthuebaoid)
            console.log('🚀 ~ file: index.vue ~ line 1206 ~ gan_thongso_theodanhba ~ ds', ds)
            if (ds !== null && ds.length > 0) {
              try {
                this.parent.thongso_ban.trangbi_id = parseInt(ds[0]['trangbi_id'])
                this.parent.thongso_ban.tocdo_id = parseInt(ds[0]['tocdo_id'])
                this.parent.thongso_ban.tocdothuc_id = parseInt(ds[0]['tocdo_id'])
                this.parent.thongso_ban.muccuoc_id = parseInt(ds[0]['muccuoc_id'])
                this.parent.thongso_ban.kieutra_id = parseInt(ds[0]['kieutra_id'])
                this.parent.thongso_ban.thietbidc_id = parseInt(ds[0]['thietbidc_id'])
                this.parent.thongso_ban.doituong_id = parseInt(this.parent.modelCbo.cboDoituongTB)
                this.parent.thongso_ban.muccuoctb_id = parseInt(this.parent.modelCbo.cboMucCuocTB)
                console.log('🚀 ~ file: index.vue:1351 ~ gan_thongso_theodanhba ~ this.parent.modelCbo.cboMucCuocTB:', this.parent.modelCbo.cboMucCuocTB)
                console.log('🚀 ~ file: index.vue ~ line 1217 ~ gan_thongso_theodanhba ~ this.parent.thongso_ban', this.parent.thongso_ban)
              } catch (e) {
                this.$root.$toast.error('Thiếu dữ liệu tại tỉnh bán. Liên hệ admin để xử lý.')
                return false
              }
            }
          } else {
            ds = await this.BANCHEO_LAY_DS_DB_ADSL_THEO_TBID(this.parent.BanCheoData.Tinh_TC, vthuebaoid)
            console.log('🚀 ~ file: index.vue ~ line 1224 ~ gan_thongso_theodanhba ~ ds', ds)
            if (ds !== null && ds.length > 0) {
              try {
                this.parent.thongso_tc.trangbi_id = parseInt(ds[0]['trangbi_id'])
                this.parent.thongso_tc.tocdo_id = parseInt(ds[0]['tocdo_id'])
                this.parent.thongso_tc.tocdothuc_id = parseInt(ds[0]['tocdo_id'])
                this.parent.thongso_tc.muccuoc_id = parseInt(ds[0]['muccuoc_id'])
                this.parent.thongso_tc.kieutra_id = parseInt(ds[0]['kieutra_id'])
                this.parent.thongso_tc.thietbidc_id = parseInt(ds[0]['thietbidc_id'])
                console.log('🚀 ~ file: index.vue ~ line 1235 ~ gan_thongso_theodanhba ~ this.parent.dsDBTBBC', this.parent.dsDBTBBC)
                if (this.parent.dsDBTBBC.length > 0) {
                  this.parent.thongso_tc.doituong_id = parseInt(this.parent.dsDBTBBC[0]['doituong_id'])
                  this.parent.thongso_tc.muccuoctb_id = parseInt(this.parent.dsDBTBBC[0]['mucuoctb_id'])
                }
              } catch (e) {
                this.$root.$toast.error('Thiếu dữ liệu tại tỉnh thi công (' + this.parent.BanCheoData.Tinh_TC.matinh + '). Liên hệ admin để xử lý.')
                return false
              }
            }
          }
        }

        if (vdichvu === 9) {
          if (vkieu === 1) {
            ds = await this.LAY_DS_DB_TSL_THEO_TBID(vthuebaoid)
            console.log('🚀 ~ file: index.vue:1370 ~ gan_thongso_theodanhba ~ ds:', ds)
            if (ds.length > 0) {
              this.parent.thongso_ban.trangbi_id = parseInt(ds[0]['trangbi_id'])
              this.parent.thongso_ban.tocdo_id = parseInt(ds[0]['tocdo_id'])
              this.parent.thongso_ban.loaikenh_id = parseInt(ds[0]['loaikenh_id'])
              this.parent.thongso_ban.thietbidc_id = parseInt(ds[0]['thietbidc_id'])
              this.parent.thongso_ban.thietbidc_id_cuoi = parseInt(ds[1]['thietbidc_id'])
              this.parent.thongso_ban.doituong_id = parseInt(this.parent.modelCbo.cboDoituongTB)
              this.parent.thongso_ban.muccuoctb_id = parseInt(this.parent.modelCbo.cboMucCuocTB)
            }
          } else {
            ds = await this.BANCHEO_LAY_DS_DB_TSL_THEO_TBID(this.parent.tinh_dau, vthuebaoid)
            console.log('🚀 ~ file: index.vue:1382 ~ gan_thongso_theodanhba ~ ds:', ds)
            if (ds.length > 0) {
              this.parent.thongso_tc_dau.trangbi_id = parseInt(ds[0]['trangbi_id'])
              this.parent.thongso_tc_dau.tocdo_id = parseInt(ds[0]['tocdo_id'])
              this.parent.thongso_tc_dau.loaikenh_id = parseInt(ds[0]['loaikenh_id'])
              this.parent.thongso_tc_dau.thietbidc_id = parseInt(ds[0]['thietbidc_id'])
              this.parent.thongso_tc_dau.thietbidc_id_cuoi = parseInt(ds[1]['thietbidc_id'])
              if (this.parent.dsDBTBBC.length > 0) {
                this.parent.thongso_tc_dau.doituong_id = parseInt(this.parent.dsDBTBBC[0]['doituong_id'])
                this.parent.thongso_tc_dau.muccuoctb_id = parseInt(this.parent.dsDBTBBC[0]['mucuoctb_id'])
              }
            }
            ds = await this.BANCHEO_LAY_DS_DB_TSL_THEO_TBID(this.parent.tinh_cuoi, vthuebaoid)
            console.log('🚀 ~ file: index.vue:1395 ~ gan_thongso_theodanhba ~ ds:', ds)
            if (ds.length > 0) {
              this.parent.thongso_tc_cuoi.trangbi_id = parseInt(ds[0]['trangbi_id'])
              this.parent.thongso_tc_cuoi.tocdo_id = parseInt(ds[0]['tocdo_id'])
              this.parent.thongso_tc_cuoi.loaikenh_id = parseInt(ds[0]['loaikenh_id'])
              this.parent.thongso_tc_cuoi.thietbidc_id = parseInt(ds[0]['thietbidc_id'])
              this.parent.thongso_tc_cuoi.thietbidc_id_cuoi = parseInt(ds[1]['thietbidc_id'])
              if (this.parent.dsDBTBBC.length > 0) {
                this.parent.thongso_tc_cuoi.doituong_id = parseInt(this.parent.dsDBTBBC[0]['doituong_id'])
                this.parent.thongso_tc_cuoi.muccuoctb_id = parseInt(this.parent.dsDBTBBC[0]['mucuoctb_id'])
              }
            }
          }
        }
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1276 ~ gan_thongso_theodanhba ~ error', error)
        return false
      }
    },
    async Gan_thongso_bancheo_thuebaoid (vthuebao_ban, vthuebao_tc, tinh_tc_id) {
      try {
        let exp_text = ''
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_thongso_thuebao_thuebaoid', {tinh_id: this.$root.token.getPhanVungID(), thuebao_id: vthuebao_ban})
        console.log('🚀 ~ file: index.vue ~ line 683 ~ Gan_thongso_bancheo_thuebaoid ~ rs', rs)
        let ds = rs.data
        if (rs.data.length > 0) {
          try {
            exp_text = 'kieuld_id'
            this.parent.thongso_ban.kieuld_id = parseInt(ds[0]['kieuld_id'])
            exp_text = 'doituong_id'
            this.parent.thongso_ban.doituong_id = parseInt(ds[0]['doituong_id'])
            exp_text = 'chuquan_id'
            this.parent.thongso_ban.chuquan_id = parseInt(ds[0]['chuquan_id'])
            exp_text = 'chuquanld_id'
            this.parent.thongso_ban.chuquanld_id = parseInt(ds[0]['chuquanld_id'])
            exp_text = 'congnghe_id'
            this.parent.thongso_ban.congnghe_id = parseInt(ds[0]['congnghe_id'])
            exp_text = 'kieutra_id'
            this.parent.thongso_ban.kieutra_id = parseInt(ds[0]['kieutra_id'])
            exp_text = 'loaikenh_id'
            this.parent.thongso_ban.loaikenh_id = parseInt(ds[0]['loaikenh_id'])
            exp_text = 'loaimd_id'
            this.parent.thongso_ban.loaimd_id = parseInt(ds[0]['loaimd_id'])
            exp_text = 'ma_doicap'
            this.parent.thongso_ban.ma_doicap = ds[0]['ma_doicap']
            exp_text = 'ma_tn'
            this.parent.thongso_ban.ma_tn = ds[0]['ma_tn']
            exp_text = 'mucuoc_tb'
            this.parent.thongso_ban.muccuoc_id = parseInt(ds[0]['mucuoc_tb'])
            exp_text = 'mucuoctb_id'
            this.parent.thongso_ban.muccuoctb_id = parseInt(ds[0]['mucuoctb_id'])
            exp_text = 'thietbidc_id'
            this.parent.thongso_ban.thietbidc_id = parseInt(ds[0]['thietbidc_id'])
            exp_text = 'THIETBI_DICH'
            this.parent.thongso_ban.thietbidc_id_cuoi = parseInt(ds[0]['thietbi_dich'])
            exp_text = 'thoihan_id'
            this.parent.thongso_ban.thoihan_id = parseInt(ds[0]['thoihan_id'])
            exp_text = 'tocdo_id'
            this.parent.thongso_ban.tocdo_id = parseInt(ds[0]['tocdo_id'])
            exp_text = 'tocdo_isp'
            this.parent.thongso_ban.tocdo_isp = parseInt(ds[0]['tocdo_isp'])
            exp_text = 'tocdo_nix'
            this.parent.thongso_ban.tocdo_nix = parseInt(ds[0]['tocdo_nix'])
            try {
              this.parent.thongso_ban.tocdo_pir_id = parseInt(ds[0]['tocdo_pir_id'])
            } catch (e) {
            }
            exp_text = 'tocdothuc_id'
            this.parent.thongso_ban.tocdothuc_id = parseInt(ds[0]['tocdothuc_id'])
            exp_text = 'trangbi_id'
            this.parent.thongso_ban.trangbi_id = parseInt(ds[0]['trangbi_id'])
            console.log('🚀 ~ file: index.vue ~ line 730 ~ Gan_thongso_bancheo_thuebaoid ~ this.parent.thongso_ban', this.parent.thongso_ban)
          } catch (error) {
            console.log('🚀 ~ file: index.vue ~ line 687 ~ Gan_thongso_bancheo_thuebaoid ~ error', error)
            this.$root.$toast.error('Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' ở tỉnh bán. Liên hệ admin để xử lý')
            return false
          }
        }

        if (vthuebao_tc !== 0 && vthuebao_tc !== -1) {
          let tinh_tc = await this.tinh_thicong(tinh_tc_id)
          rs = await this.$root.context.post('/web-bancheo/tracuu/lay_thongso_thuebao_thuebaoid', {tinhthicong_id: tinh_tc.tinhthicong_id, thuebao_id: vthuebao_tc})
          let ds_tc = rs.data
          console.log('🚀 ~ file: index.vue ~ line 739 ~ Gan_thongso_bancheo_thuebaoid ~ ds_tc', ds_tc)
          if (ds_tc.length > 0) {
            try {
              exp_text = 'kieuld_id'
              this.parent.thongso_tc.kieuld_id = parseInt(ds_tc[0]['kieuld_id'])
              exp_text = 'doituong_id'
              this.parent.thongso_tc.doituong_id = parseInt(ds_tc[0]['doituong_id'])
              exp_text = 'chuquan_id'
              this.parent.thongso_tc.chuquan_id = parseInt(ds_tc[0]['chuquan_id'])
              exp_text = 'chuquanld_id'
              this.parent.thongso_tc.chuquanld_id = parseInt(ds_tc[0]['chuquanld_id'])
              exp_text = 'congnghe_id'
              this.parent.thongso_tc.congnghe_id = parseInt(ds_tc[0]['congnghe_id'])
              exp_text = 'kieutra_id'
              this.parent.thongso_tc.kieutra_id = parseInt(ds_tc[0]['kieutra_id'])
              exp_text = 'loaikenh_id'
              this.parent.thongso_tc.loaikenh_id = parseInt(ds_tc[0]['loaikenh_id'])
              exp_text = 'loaimd_id'
              this.parent.thongso_tc.loaimd_id = parseInt(ds_tc[0]['loaimd_id'])
              exp_text = 'ma_doicap'
              this.parent.thongso_tc.ma_doicap = ds_tc[0]['ma_doicap']
              exp_text = 'ma_tn'
              this.parent.thongso_tc.ma_tn = ds_tc[0]['ma_tn']
              exp_text = 'mucuoc_tb'
              this.parent.thongso_tc.muccuoc_id = parseInt(ds_tc[0]['mucuoc_tb'])
              exp_text = 'mucuoctb_id'
              this.parent.thongso_tc.muccuoctb_id = parseInt(ds_tc[0]['mucuoctb_id'])
              exp_text = 'thietbidc_id'
              this.parent.thongso_tc.thietbidc_id = parseInt(ds_tc[0]['thietbidc_id'])
              exp_text = 'THIETBI_DICH'
              this.parent.thongso_tc.thietbidc_id_cuoi = parseInt(ds_tc[0]['thietbi_dich'])
              exp_text = 'thoihan_id'
              this.parent.thongso_tc.thoihan_id = parseInt(ds_tc[0]['thoihan_id'])
              exp_text = 'tocdo_id'
              this.parent.thongso_tc.tocdo_id = parseInt(ds_tc[0]['tocdo_id'])
              exp_text = 'tocdo_isp'
              this.parent.thongso_tc.tocdo_isp = parseInt(ds_tc[0]['tocdo_isp'])
              exp_text = 'tocdo_nix'
              this.parent.thongso_tc.tocdo_nix = parseInt(ds_tc[0]['tocdo_nix'])
              try {
                this.parent.thongso_tc.tocdo_pir_id = parseInt(ds_tc[0]['tocdo_pir_id'])
              } catch (e) {
              }
              exp_text = 'tocdothuc_id'
              this.parent.thongso_tc.tocdothuc_id = parseInt(ds_tc[0]['tocdothuc_id'])
              exp_text = 'trangbi_id'
              this.parent.thongso_tc.trangbi_id = parseInt(ds_tc[0]['trangbi_id'])
              console.log('🚀 ~ file: index.vue ~ line 789 ~ Gan_thongso_bancheo_thuebaoid ~ this.parent.thongso_tc', this.parent.thongso_tc)
            } catch (e) {
              console.log('🚀 ~ file: index.vue ~ line 789 ~ Gan_thongso_bancheo_thuebaoid ~ e', e)
              this.$root.$toast.error('Thiếu dữ liệu trường ' + exp_text.toUpperCase() + ' thuê bao ' + vthuebao_tc + ' ở tỉnh thi công (' + tinh_tc.matinh + '). Liên hệ admin để xử lý')
              return false
            }
          }
        }
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 691 ~ Gan_thongso_bancheo_thuebaoid ~ error', error)
        return false
      }
    },
    async HienThi_NVQL_AM_DB_HD (i_kieu, id) {
      try {
        let ds_tt = await this.Lay_NhanVien_QL(i_kieu, id)
        if (ds_tt !== null && ds_tt.length > 0) {
          this.parent.modelInput.txtNhanVienQL = ds_tt[0]['ma_nv'] + ' - ' + ds_tt[0]['ten_nv']
          this.parent.nhanvien_ql_am_id = parseInt(ds_tt[0]['nhanvien_id'])
        } else {
          this.parent.modelInput.txtNhanVienQL = ''
          this.parent.nhanvien_ql_am_id = 0
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 543 ~ HienThi_NVQL_AM_DB_HD ~ error', error)
      }
    },
    async LAY_DS_DB_TSL_THEO_TBID (thuebao_id) {
      try {
        var data = {thuebao_id}
        var rs = await this.$root.context.get('/web-hopdong/chuyendich-tsl/lay_ds_db_tsl_theo_tbid', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async BANCHEO_LAY_DS_DB_TSL_THEO_TBID (tinh, thueBaoId) {
      try {
        var data = {tinh_id: tinh.tinhthicong_id, thueBaoId}
        var rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_db_tsl_theo_tbid', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_DB_ADSL_THEO_TBID (thuebao_id) {
      try {
        var data = {thuebao_id}
        var rs = await this.$root.context.get('/web-hopdong/chuyendich-adsl/lay_ds_db_adsl_theo_tbid', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async BANCHEO_LAY_DS_DB_ADSL_THEO_TBID (tinh, thueBaoId) {
      try {
        var data = {tinh_id: tinh.tinhthicong_id, thueBaoId}
        var rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_db_adsl_theo_tbid', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_DB_IMS_THEO_TBID (thueBaoId) {
      try {
        var data = {thueBaoId}
        var rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_danhba_ims', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async BANCHEO_LAY_DS_DB_IMS_THEO_TBID (tinh, thueBaoId) {
      try {
        var data = {tinh_id: tinh.tinhthicong_id, thueBaoId}
        var rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_db_ims_theo_tbid', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_DB_CD_THEO_TBID (thuebao_id) {
      try {
        var data = {thuebao_id}
        var rs = await this.$root.context.get('/web-hopdong/chuyendich-codinh/lay_ds_db_cd_theo_tbid', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async BANCHEO_LAY_DS_DB_CD_THEO_TBID (tinh, thuebao_id) {
      try {
        var data = {tinh_id: tinh.tinhthicong_id, thuebao_id}
        var rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_db_cd_theo_tbid', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async Lay_NhanVien_QL (KIEU, ID) {
      try {
        var data = {KIEU, ID}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_nhanvien_ql', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async GET_DS_SUDUNG_DVK (DB_ID, LOAIID) {
      try {
        var data = {DB_ID, LOAIID}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_sudung_dvk', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_SUDUNG_DVK (db_id, vloaiid) {
      var dset = await this.GET_DS_SUDUNG_DVK(db_id, vloaiid)
      console.log('🚀 ~ file: index.vue ~ line 908 ~ LAY_DS_SUDUNG_DVK ~ dset', dset)
      this.parent.dsDK_DVK = []
      for (let i = 0; i < dset.length; i++) {
        let row = {}
        row.DVKHAC_ID = dset[i].dvkhac_id
        row.KIEU_YC = 1
        row.TTTH = Enum.TrangThaiCaiDat.MOI
        row.HD_ID = this.parent.hdtb_id
        row.NGAY_YC = DateTimeLib.toDateDisplay(this.parent.modelDateTime.dtpNgayYC)
        row.LOAIID = vloaiid
        this.parent.dsDK_DVK.push(row)
      }
      if (vloaiid === 1) {
        this.parent.modelInput.lvDvkhac = []
        for (let i = 0; i < this.parent.dsDK_DVK.length; i++) {
          let data = {
            loai: 'DICHVU_KHAC',
            value: this.parent.dsDK_DVK[i].DVKHAC_ID
          }
          try {
            let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getdata', data)
            if (rs.data.length > 0) {
              let str = rs.data[0].ten_dvkhac + '  (Đang SD)'
              console.log('🚀 ~ file: index.vue ~ line 935 ~ LAY_DS_SUDUNG_DVK ~ str', str)
              this.parent.modelInput.lvDvkhac.push(str)
            }
          } catch (error) {
            console.log('🚀 ~ file: index.vue ~ line 2066 ~ LAY_DS_SUDUNG_DVK ~ error', error)
          }
        }
      } else {
        this.parent.modelInput.lvDvkhac_TT = []
        for (let i = 0; i < this.parent.dsDK_DVK.length; i++) {
          let data = {
            loai: 'DICHVU_KHAC',
            value: this.parent.dsDK_DVK[i].DVKHAC_ID
          }
          try {
            let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getdata', data)
            if (rs.data.length > 0) {
              let str = rs.data[0].ten_dvkhac + '  (Đang SD)'
              console.log('🚀 ~ file: index.vue ~ line 954 ~ LAY_DS_SUDUNG_DVK ~ str', str)
              this.parent.modelInput.lvDvkhac_TT.push(str)
            }
          } catch (error) {
            console.log('🚀 ~ file: index.vue ~ line 2066 ~ LAY_DS_SUDUNG_DVK ~ error', error)
          }
        }
      }
    },
    async HT_DichVuGT_DB (dset) {
      try {
        let str
        for (var i = 0; i < dset.length; i++) {
          try {
            let dichvugt_id = Number(dset[i].DICHVUGT_ID)
            let data = {
              loai: 'DICHVU_GT',
              value: dichvugt_id
            }
            try {
              let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getdata', data)
              if (rs.data.length > 0) {
                str = rs.data[0].ten_dvgt + ' (Đang SD)'
                this.parent.modelInput.lvDvgt = this.parent.modelInput.lvDvgt + str + '\n'
              }
            } catch (error) {
              console.log('🚀 ~ file: index.vue ~ line 2108 ~ HT_DichVuGT_DB ~ error', error)
            }
          } catch (error) {
            console.log('🚀 ~ file: index.vue ~ line 556 ~ HT_DichVuGT_DB ~ error', error)
          }
        }
      } catch (error) {

      }
    },
    async TC_LAY_DS_SUDUNG_DVGT (THUEBAO_ID) {
      try {
        var data = {THUEBAO_ID}
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_sudung_dvgt', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async BANCHEO_LAY_DS_THUEBAO_DICHVU (tinh_id, hdtb_id, thuebao_id, loaitb_id, kieu_id) {
      try {
        var data = {tinh_id, hdtb_id, thuebao_id, loaitb_id, kieu_id}
        console.log('🚀 ~ file: index.vue ~ line 1029 ~ BANCHEO_LAY_DS_THUEBAO_DICHVU ~ data', data)
        var rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_ds_thuebao_dichvu_2', data)
        console.log('🚀 ~ file: index.vue ~ line 1026 ~ BANCHEO_LAY_DS_THUEBAO_DICHVU ~ rs', rs)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async HienThiTTMoRongTB (thuebao_id, dichvuvt_id) {
      try {
        this.parent.modelCkeckBox.chkCongVan = false
        this.parent.modelCkeckBox.chkdisableCongVan = true
        this.parent.modelInput.txtCongVan = ''
        this.parent.modelDateTime.dtpNgayCV = new Date()
        this.parent.kt_kenhphu_ao = false
        switch (parseInt(dichvuvt_id)) {
          case Enum.DichVuVienThong.CO_DINH:
            await this.$refs.refCD.HienThiTTMoRongTB(thuebao_id)
            this.parent.ds_isdn = await this.$refs.refCD.LAY_DS_ISDN_THEO_THUEBAO_ID(thuebao_id)
            break
          case Enum.DichVuVienThong.IMS:
            await this.$refs.refIMS.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.ADSL:
            await this.$refs.refAdsl.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.MEGA_EYES:
            await this.$refs.refAdsl.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.MEGAWAN:
          case 26:
            await this.$refs.refWan.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.METRONET:
            await this.$refs.refWan.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.TSL:
          case 25:
            await this.$refs.refTSL.HienThiTTMoRongTB(thuebao_id)
            break
          case Enum.DichVuVienThong.GPHONE:
            await this.$refs.refGP.HienThiTTMoRongTB(thuebao_id)
            break
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 517 ~ HienThiTTMoRongTB ~ error', error)
      }
    },
    async HienThiTTDiaChi (ds, kieu) {
      if (ds !== null && ds.length > 0) {
        console.log('🚀 ~ file: index.vue ~ line 1286 ~ HienThiTTDiaChi ~ ds', ds, kieu)
        if (kieu === 1) {
          this.parent.diachikh_id = parseInt(ds[0]['diachi_id'])
          if (ds[0]['tinh_id'] !== null) { this.parent.tinhkh_id = parseInt(ds[0]['tinh_id']) } else { this.parent.tinhkh_id = 0 }
          if (ds[0]['quan_id'] !== null) { this.parent.quankh_id = parseInt(ds[0]['quan_id']) } else { this.parent.quankh_id = 0 }
          if (ds[0]['phuong_id'] !== null) { this.parent.phuongkh_id = parseInt(ds[0]['phuong_id']) } else { this.parent.phuongkh_id = 0 }

          if (ds[0]['pho_id'] !== null) { this.parent.phokh_id = parseInt(ds[0]['pho_id']) } else { this.parent.phokh_id = 0 }

          if (ds[0]['ap_id'] !== null) { this.parent.apkh_id = parseInt(ds[0]['ap_id']) } else { this.parent.apkh_id = 0 }

          if (ds[0]['khu_id'] !== null) { this.parent.khukh_id = parseInt(ds[0]['khu_id']) } else { this.parent.khukh_id = 0 }

          if (ds[0]['dacdiem_id'] !== null) { this.parent.dacdiemkh_id = parseInt(ds[0]['dacdiem_id']) } else { this.parent.dacdiemkh_id = 0 }

          if (ds[0]['kinhdo'] !== null) { this.parent.Lng_KH = parseInt(ds[0]['kinhdo']) } else { this.parent.Lng_KH = 0 }
          if (ds[0]['vido'] !== null) { this.parent.Lat_KH = parseInt(ds[0]['vido']) } else { this.parent.Lat_KH = 0 }

          this.parent.sonhakh = this.parent.ChuanHoaTen(ds[0]['sonha'])
        }
        if (kieu === 2) {
          // Hiếu sửa địa chỉ thanh toán ngày 29/07/2010
          this.parent.diachitt_id = parseInt(ds[0]['diachi_id'])
          if (ds[0]['tinh_id'] !== null) { this.parent.tinhtt_id = parseInt(ds[0]['tinh_id']) } else { this.parent.tinhtt_id = 0 }
          if (ds[0]['quan_id'] !== null) { this.parent.quantt_id = parseInt(ds[0]['quan_id']) } else { this.parent.quantt_id = 0 }

          if (ds[0]['phuong_id'] !== null) { this.parent.phuongtt_id = parseInt(ds[0]['phuong_id']) } else { this.parent.phuongtt_id = 0 }

          if (ds[0]['pho_id'] !== null) { this.parent.phott_id = parseInt(ds[0]['pho_id']) } else { this.parent.phott_id = 0 }

          if (ds[0]['ap_id'] !== null) { this.parent.aptt_id = parseInt(ds[0]['ap_id']) } else { this.parent.aptt_id = 0 }

          if (ds[0]['khu_id'] !== null) { this.parent.khutt_id = parseInt(ds[0]['khu_id']) } else { this.parent.khutt_id = 0 }

          if (ds[0]['dacdiem_id'] !== null) { this.parent.dacdiemtt_id = parseInt(ds[0]['dacdiem_id']) } else { this.parent.dacdiemtt_id = 0 }

          if (ds[0]['kinhdo'] !== null) { this.parent.Lng_TT = parseFloat(ds[0]['kinhdo']) } else { this.parent.Lng_TT = 0 }
          if (ds[0]['vido'] !== null) { this.parent.Lat_TT = parseFloat(ds[0]['vido']) } else { this.parent.Lat_TT = 0 }

          this.parent.sonhatt = this.parent.ChuanHoaTen(ds[0]['sonha'])

          // Địa chỉ báo cước
          if (ds[0]['diachibc_id'] !== null) { this.parent.diachibc_id = parseInt(ds[0]['diachibc_id']) } else { this.parent.diachibc_id = 0 }

          if (ds[0]['tinhbc_id'] !== null) { this.parent.tinhbc_id = parseInt(ds[0]['tinhbc_id']) } else { this.parent.tinhbc_id = 0 }

          if (ds[0]['quanbc_id'] !== null) { this.parent.quanbc_id = parseInt(ds[0]['quanbc_id']) } else { this.parent.quanbc_id = 0 }

          if (ds[0]['phuongbc_id'] !== null) { this.parent.phuongbc_id = parseInt(ds[0]['phuongbc_id']) } else { this.parent.phuongbc_id = 0 }

          if (ds[0]['phobc_id'] !== null) { this.parent.phobc_id = parseInt(ds[0]['phobc_id']) } else { this.parent.phobc_id = 0 }

          if (ds[0]['apbc_id'] !== null) { this.parent.apbc_id = parseInt(ds[0]['apbc_id']) } else { this.parent.apbc_id = 0 }

          if (ds[0]['khubc_id'] !== null) { this.parent.khubc_id = parseInt(ds[0]['khubc_id']) } else { this.parent.khubc_id = 0 }

          if (ds[0]['dacdiembc_id'] !== null) { this.parent.dacdiembc_id = parseInt(ds[0]['dacdiembc_id']) } else { this.parent.dacdiembc_id = 0 }

          if (ds[0]['kinhdo_bc'] !== null) { this.parent.Lng_BC = parseFloat(ds[0]['kinhdo_bc']) } else { this.parent.Lng_BC = 0 }
          if (ds[0]['vido_bc'] !== null) { this.parent.Lat_BC = parseFloat(ds[0]['vido_bc']) } else { this.parent.Lat_BC = 0 }

          this.parent.sonhabc = this.parent.ChuanHoaTen(ds[0]['sonha_bc'])
        }

        if (kieu === 3) {
          // Hiếu- thêm phần địa chỉ thuê bao + địa chỉ lắp đặt 21/07/2010
          this.parent.diachitb_id = parseInt(ds[0]['diachi_id'])
          // Địa chỉ thuê bao
          if (ds[0]['tinh_id'] !== null) { this.parent.tinhtb_id = parseInt(ds[0]['tinh_id']) } else { this.parent.tinhtb_id = 0 }
          if (ds[0]['quan_id'] !== null) { this.parent.quantb_id = parseInt(ds[0]['quan_id']) } else { this.parent.quantb_id = 0 }
          if (ds[0]['phuong_id'] !== null) { this.parent.phuongtb_id = parseInt(ds[0]['phuong_id']) } else { this.parent.phuongtb_id = 0 }

          if (ds[0]['pho_id'] !== null) { this.parent.photb_id = parseInt(ds[0]['pho_id']) } else { this.parent.photb_id = 0 }

          if (ds[0]['ap_id'] !== null) { this.parent.aptb_id = parseInt(ds[0]['ap_id']) } else { this.parent.aptb_id = 0 }

          if (ds[0]['khu_id'] !== null) { this.parent.khutb_id = parseInt(ds[0]['khu_id']) } else { this.parent.khutb_id = 0 }

          if (ds[0]['dacdiem_id'] !== null) { this.parent.dacdiemtb_id = parseInt(ds[0]['dacdiem_id']) } else { this.parent.dacdiemtb_id = 0 }

          if (ds[0]['kinhdo'] !== null) { this.parent.Lng_TB = parseFloat(ds[0]['kinhdo']) } else { this.parent.Lng_TB = 0 }
          if (ds[0]['vido'] !== null) { this.parent.Lat_TB = parseFloat(ds[0]['vido']) } else { this.parent.Lat_TB = 0 }

          this.parent.sonhatb = this.parent.ChuanHoaTen(ds[0]['sonha'])
          // Địa chỉ lắp đặt
          this.parent.diachild_id = parseInt(ds[0]['diachild_id'])
          if (ds[0]['tinhld_id'] !== null) { this.parent.tinhld_id = parseInt(ds[0]['tinhld_id']) } else { this.parent.tinhld_id = 0 }
          if (ds[0]['quanld_id'] !== null) { this.parent.quanld_id = parseInt(ds[0]['quanld_id']) } else { this.parent.quanld_id = 0 }

          if (ds[0]['phuongld_id'] !== null) { this.parent.phuongld_id = parseInt(ds[0]['phuongld_id']) } else { this.parent.phuongld_id = 0 }

          if (ds[0]['phold_id'] !== null) { this.parent.phold_id = parseInt(ds[0]['phold_id']) } else { this.parent.phold_id = 0 }

          if (ds[0]['apld_id'] !== null) { this.parent.apld_id = parseInt(ds[0]['apld_id']) } else { this.parent.apld_id = 0 }

          if (ds[0]['khuld_id'] !== null) { this.parent.khuld_id = parseInt(ds[0]['khuld_id']) } else { this.parent.khuld_id = 0 }

          if (ds[0]['dacdiemld_id'] !== null) { this.parent.dacdiemld_id = parseInt(ds[0]['dacdiemld_id']) } else { this.parent.dacdiemld_id = 0 }

          if (ds[0]['kinhdo_ld'] !== null) { this.parent.Lng_LD = parseFloat(ds[0]['kinhdo_ld']) } else { this.parent.Lng_LD = 0 }

          if (ds[0]['vido_ld'] !== null) { this.parent.Lat_LD = parseFloat(ds[0]['vido_ld']) } else { this.parent.Lat_LD = 0 }

          this.parent.sonhald = this.parent.ChuanHoaTen(ds[0]['sonha_ld'])
        }
      }
    },
    async LAY_DS_DIACHI_THEO_DBKHID (khachhang_id) {
      let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_diachi_theo_khid', {khachHangId: khachhang_id})
      return res.data
    },
    async LAY_DS_DIACHI_THEO_DBTTID (thanhtoan_id) {
      let res = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid', {dbttid: thanhtoan_id})
      return res.data
    },
    async LAY_DS_KHUVUC_THEO_HD_DB (vhdtb_id, vinhd_db) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_khuvuc_theo_hd_db', {HDTB_ID: vhdtb_id, INHD_DB: vinhd_db})
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 473 ~ LAY_DS_KHUVUC_THEO_HD_DB ~ error', error)
        return []
      }
    },
    async LAY_THUEBAOID_THICONG (tinhthicong_id, thuebao_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/chuyendoi-codinh-sang-ims-bancheo/lay_thuebaoid_thicong', {vtinh_id: tinhthicong_id, vthuebao_id: thuebao_id})
        if (rs.data.length > 0) {
          return rs.data[0].thuebao_kn_id
        } else return 0
      } catch (error) {
        return 0
      }
    },
    async HT_COMBO_MA_DAIDIEN (thanhtoan_id) {
      try {
        var rs = await this.$root.context.get('/web-hopdong/chuyendich/ht_combo_ma_daidien', {thanhtoan_id: thanhtoan_id})
        console.log('🚀 ~ file: index.vue ~ line 362 ~ HT_COMBO_MA_DAIDIEN ~ rs', rs)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 363 ~ HT_COMBO_MA_DAIDIEN ~ error', error)
        return []
      }
    },
    async LAY_KHACHHANGID_THICONG (tinh, khachhangbanid) {
      try {
        if (khachhangbanid === 0) return 0
        else {
          var data = {
            tinhthicong_id: tinh.tinhthicong_id,
            khachhangbanid
          }
          console.log('🚀 ~ file: index.vue ~ line 1228 ~ LAY_KHACHHANGID_THICONG ~ data', data)
          // var rs = await this.$root.context.post('/web-bancheo/tracuu/lay_khachhangid_thicong', data)
          var rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_khachhangid_thicong2', data)
          console.log('🚀 ~ file: index.vue ~ line 328 ~ LAY_KHACHHANGID_THICONG ~ rs', rs)
          return rs.data
          // if (rs.data.length === 0) {
          //   return 0
          // } else {
          //   let thuebao_id = await this.getFieldID_TBID(tinh.tinhthicong_id, 'CSS', 'KHACHHANG_ID', 'DB_THUEBAO', parseInt(rs.data[0].thuebao_kn_id))
          //   return thuebao_id
          // }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 323 ~ LAY_KHACHHANGID_THICONG ~ error', error)
        return 0
      }
    },
    async getFieldID_TBID (tinh_id, schema, fieldget, table, thuebaoid) {
      try {
        var data = {
          tinh_id,
          thuebaoid
        }
        console.log('🚀 ~ file: index.vue ~ line 648 ~ getFieldID_TBID ~ data', data)
        var rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getfieldid_tbid', data)
        console.log('🚀 ~ file: index.vue ~ line 348 ~ getFieldID_TBID ~ rs', rs)
        if (rs.data.length > 0) {
          return parseInt(rs.data[0].khachhang_id)
        } else {
          return 0
        }
      } catch (error) {
        return 0
      }
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC (ma_tb, ma_gd, vdichvuvt_id, vloaihd_id) { return false },
    async LAY_DB_THEO_MATB (ma_tb, donvi_id, dichvuvt_id) {
      try {
        var data = {
          MA_TB: ma_tb,
          DONVI_DL_ID: donvi_id,
          DICHVUVT_ID: dichvuvt_id
        }
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_db_theo_matb', data)
        console.log('🚀 ~ file: index.vue ~ line 262 ~ LAY_DB_THEO_MATB ~ rs', rs)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 264 ~ LAY_DB_THEO_MATB ~ error', error)
        return []
      }
    },
    async HienThiTT_HopDong_ThueBao (ds) {
      console.log('🚀 ~ file: index.vue ~ line 1281 ~ HienThiTT_HopDong_ThueBao ~ ds', ds)
      try {
        if (ds && ds.length > 0) {
          this.parent.modelInput.txtMaTB = ds[0]['ma_tb']
          this.parent.thuebao_id = Number(ds[0]['thuebao_id'])
          if (this.parent.BanCheoData.Tinh_TC.tinhthicong_id > 0) { this.parent.thuebao_tc_id = await this.LAY_THUEBAOID_THICONG(this.parent.BanCheoData.Tinh_TC.tinhthicong_id, this.parent.thuebao_id) } else { this.parent.thuebao_tc_id = 0 }
          this.parent.modelInput.txtDiaChiTB = ds[0]['diachi_tb']
          this.parent.modelInput.txtDiaChiLD = ds[0]['diachi_ld']
          this.parent.modelInput.txtTenTB = ds[0]['ten_tb']
          this.parent.ma_tbmoi = ''
          this.parent.modelInput.txtMaTBMoi = ''
          this.parent.modelCbo.cboDaiVT = Number(ds[0]['donviql_id'])
          let ls = this.parent.modelCbo.vcboDaiVT.find(x => x.donvi_id.toString() === this.parent.modelCbo.cboDaiVT.toString())
          console.log('🚀 ~ file: index.vue ~ line 1724 ~ HienThiTT_HopDong_ThueBao ~ ls', ls)
          if (ls === undefined) {
            this.parent.modelCbo.cboDaiVT = this.parent.modelCbo.vcboDaiVT[0].donvi_id
          }
          if (ds[0]['ngay_sn'] != null) { this.parent.modelInput.txtNgaySinhTB = DateTimeLib.toDateDisplay(new Date(ds[0]['ngay_sn'])) } else { this.parent.modelInput.txtNgaySinhTB = '' }
          this.parent.modelInput.txtMaTT = ds[0]['ma_tt']
          this.parent.thanhtoan_id = Number(ds[0]['thanhtoan_id'])
          let dtmadd = await this.HT_COMBO_MA_DAIDIEN(this.parent.thanhtoan_id)
          this.parent.modelCbo.vcboMaDD = dtmadd
          this.parent.modelCbo.cboMaDD = ds[0]['matb_dd']
          let chk = await this.gan_thongso_theodanhba(1, this.parent.dichvuvt_id, this.parent.thuebao_id)
          if (!chk) return
          chk = await this.gan_thongso_theodanhba(2, this.parent.dichvuvt_id, this.parent.thuebao_tc_id)
          if (!chk) return

          this.parent.modelInput.txtTenTT = ds[0]['ten_tt']
          this.parent.modelInput.txtDiaChiTT = ds[0]['diachi_tt']
          this.parent.modelInput.txtDiaChiCT = ds[0]['diachi_ct']

          this.parent.modelInput.txtGhiChuTT = ds[0]['ghichu']
          await this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBTTID(this.parent.thanhtoan_id), 2)
          this.parent.modelInput.txtMST = ds[0]['mst']
          this.parent.modelInput.txtSoTKKH = ds[0]['stk']
          if (ds[0]['nganhang_id'] != null) { this.parent.modelCbo.cboNganHangTT = Number(ds[0]['nganhang_id']) }
          this.parent.modelCbo.cboHTTT = Number(ds[0]['httt_id'])
          this.parent.modelCbo.cboDonViQL = Number(ds[0]['donvi_id'])

          ls = this.parent.modelCbo.vcboDonViQL.find(x => x.donvi_id.toString() === this.parent.modelCbo.cboDonViQL.toString())
          if (ls === undefined) {
            this.parent.modelCbo.cboDonViQL = this.parent.modelCbo.vcboDonViQL[0].donvi_id
          }

          if (ds[0]['tuyenthu_id'] !== null) { this.parent.tuyenthu_id = Number(ds[0]['tuyenthu_id']) } else { this.parent.tuyenthu_id = 0 }
          if (ds[0]['nhanvien_id'] != null) {
            this.parent.nhanvientc_id = Number(ds[0]['nhanvien_id'])
            this.parent.modelInput.txtMaNVTC = await this.MAP_ID('MA_NV', this.parent.nhanvientc_id)
          } else {
            this.parent.nhanvientc_id = 0
            this.parent.modelInput.txtMaNVTC = ''
            console.log("🚀 ~ file: index.vue:2071 ~ HienThiTT_HopDong_ThueBao ~ this.parent.modelInput.txtMaNVTC = '':", this.parent.modelInput.txtMaNVTC = '')
          }
          this.parent.matb_dd = ds[0]['matb_dd']
          this.parent.modelInput.txtMaSTKH = ds[0]['mst']
          this.parent.modelInput.txtSoTKKH = ds[0]['stk']
          //
          await this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_DBTBID(this.parent.thuebao_id), 3)
          this.parent.modelCbo.cboDoituongTB = Number(ds[0]['doituong_id'])
          this.parent.modelCbo.cboLoaihinhTB = Number(ds[0]['loaitb_id'])
          this.parent._loaitb_id = Number(ds[0]['loaitb_id'])

          let dsDonVi = await this.lay_ds_donvi_cua_tbao(this.parent.thuebao_id)
          console.log('🚀 ~ file: index.vue ~ line 1787 ~ HienThiTT_HopDong_ThueBao ~ dsDonVi', dsDonVi)

          if (dsDonVi && dsDonVi.length > 0) {
            let dtControl = await this.parent.getControl()
            console.log('🚀 ~ file: index.vue ~ line 1775 ~ HienThiTT_HopDong_ThueBao ~ dtControl', dtControl)
            for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
              let cbo = dtControl.dsComboDonVi[index]
              let donVi = dsDonVi.find(item => item.loaidv_id.toString() === cbo.loaiDonVi.toString())
              console.log('🚀 ~ file: index.vue ~ line 1794 ~ HienThiTT_HopDong_ThueBao ~ donVi', donVi)
              if (donVi) {
                dtControl.ref.$refs[cbo.key][0].value = donVi['donvi_id']
              }
            }
          }
          if (ds[0]['mucuoctb_id'] !== null) {
            await this.parent.HT_MucCuocTB_Combobox(Number(ds[0]['mucuoctb_id']))
          } else {
            this.parent.modelCbo.cboMucCuocTB = -1
            this.parent.modelCbo.vcboMucCuocTB = []
          }
          await this.HienThiTTMoRongTB(this.parent.thuebao_id, this.parent.dichvuvt_id)
          await this.LAY_DS_SUDUNG_DVGT(this.parent.thuebao_id)

          await this.LAY_DS_SUDUNG_DVK(this.parent.thuebao_id, 1)
          await this.HT_DS_KhuyenMai_Combobox()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1284 ~ HienThiTT_HopDong_ThueBao ~ error', error)
      }
    },
    async lay_ds_donvi_cua_tbao (thuebao_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich-codinh/lay_ds_donvi_cua_tbao`, { thuebao_id })
        return response.data
      } catch (err) {
        return []
      }
    },
    async LAY_DANHBA_THEO_MATB (ma_tb, dichvuvt_id, donvi_id) {
      try {
        var data = {
          MA_TB: ma_tb,
          DICHVUVT_ID: dichvuvt_id,
          DONVI_DL_ID: donvi_id
        }
        console.log('🚀 ~ file: index.vue ~ line 251 ~ LAY_DANHBA_THEO_MATB ~ data', data)
        var rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_danhba_theo_matb', data)
        console.log('🚀 ~ file: index.vue ~ line 252 ~ LAY_DANHBA_THEO_MATB ~ rs', rs)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async TraCuu_LAY_DS_HOPDONG_THEO_MA_TB (in_ma_tb, in_loaihd_id, in_donvi_id, in_tthd_id, in_nhanvien_id, in_donvi_dl_id, in_dichvuvt_id, in_loaitb_id) {
      try {
        let data = { in_ma_tb, in_loaihd_id, in_donvi_id, in_tthd_id, in_nhanvien_id, in_donvi_dl_id, in_dichvuvt_id, in_loaitb_id }
        console.log('🚀 ~ file: index.vue ~ line 248 ~ TraCuu_LAY_DS_HOPDONG_THEO_MA_TB ~ data', data)
        let rs = await this.$root.context.post('/web-bancheo/chuyen-dich/lay_ds_hopdong_theo_ma_tb', data)
        console.log('🚀 ~ file: index.vue ~ line 249 ~ TraCuu_LAY_DS_HOPDONG_THEO_MA_TB ~ rs', rs)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 251 ~ TraCuu_LAY_DS_HOPDONG_THEO_MA_TB ~ error', error)
        return []
      }
    },
    async TCHopDong_LAY_DS_HOPDONG_THEO_MA_TB (in_ma_tb, in_loaihd_id, in_donvi_id, in_tthd_id, in_nhanvien_id, in_donvi_dl_id, in_dichvuvt_id, in_loaitb_id) {
      try {
        let data = { in_ma_tb, in_loaihd_id, in_donvi_id, in_tthd_id, in_nhanvien_id, in_donvi_dl_id, in_dichvuvt_id, in_loaitb_id }
        console.log('🚀 ~ file: index.vue ~ line 260 ~ TCHopDong_LAY_DS_HOPDONG_THEO_MA_TB ~ data', data)
        let rs = await this.$root.context.post('/web-hopdong/goi-da-dichvu/lay_ds_hopdong_theo_ma_tb', data)
        console.log('🚀 ~ file: index.vue ~ line 260 ~ TCHopDong_LAY_DS_HOPDONG_THEO_MA_TB ~ rs', rs)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 262 ~ TCHopDong_LAY_DS_HOPDONG_THEO_MA_TB ~ error', error)
        return []
      }
    },
    async LAY_DS_DIACHI_THEO_HDKH_ID (hdkh_id) {
      try {
        let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_diachi_theo_hdkh', {'hdKhId': hdkh_id})
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1324 ~ LAY_DS_DIACHI_THEO_HDKH_ID ~ error', error)
        return []
      }
    },
    async HienThiTTHopDongKH (ds) {
      try {
        if (ds !== null && ds.length > 0) {
          console.log('🚀 ~ file: index.vue ~ line 1331 ~ HienThiTTHopDongKH ~ ds', ds)
          this.parent.ikieuld_id = 0
          this.parent.hdkh_id = parseInt(ds[0]['hdkh_id'])
          this.parent.modelInput.txtMaGD = ds[0]['ma_gd']
          this.parent.modelInput.txtMaKH = ds[0]['ma_kh']
          this.parent.modelDateTime.dtpNgayYC = ds[0]['ngay_yc'] ? new Date(ds[0]['ngay_yc']) : new Date()
          if (ds[0]['khachhang_id'] !== null) { this.parent.khachhang_id = parseInt(ds[0]['khachhang_id']) } else { this.parent.khachhang_id = 0 }
          if (this.parent.khachhang_id > 0) { this.parent.khachhang_tc_id = await this.LAY_KHACHHANGID_THICONG(this.parent.BanCheoData.Tinh_TC, this.parent.khachhang_id) }
          if (ds[0]['kieuhd_id'] !== null) { this.parent.modelCbo.cboKieu_HD = parseInt(ds[0]['kieuhd_id']) } else { this.parent.modelCbo.cboKieu_HD = Enum.KIEU_HD.TAI_GD }
          this.parent.modelCbo.cboKhachHangLon = parseInt(ds[0]['khlon_id'])
          this.parent.modelInput.txtTenKH = this.parent.ChuanHoaTen(ds[0]['ten_kh'])
          this.parent.modelInput.txtDiaChiKH = this.parent.ChuanHoaTen(ds[0]['diachi_kh'])
          await this.HienThiTTDiaChi(await this.LAY_DS_DIACHI_THEO_HDKH_ID(this.parent.hdkh_id), 1)
          this.parent.modelInput.txtNguoiDD = this.parent.ChuanHoaTen(ds[0]['nguoi_dd'])
          this.parent.modelInput.txtChucDanhDD = this.parent.ChuanHoaTen(ds[0]['chucdanh_dd'])
          this.parent.modelInput.txtTelKH = ds[0]['so_dt']
          this.parent.modelInput.txtFaxKH = ds[0]['so_fax']
          this.parent.modelInput.txtHoKhau = this.parent.ChuanHoaTen(ds[0]['hokhau'])
          this.parent.modelInput.txtNoiCapHK = this.parent.ChuanHoaTen(ds[0]['noicap_hk'])
          if (ds[0]['ngay_hk'] !== null) { this.parent.modelInput.txtNgayCapHK = DateTimeLib.toDateDisplay(new Date(ds[0]['ngay_hk'])) } else { this.parent.modelInput.txtNgayCapHK = '' }
          if (ds[0]['ngay_sn'] !== null) {
            this.parent.modelInput.txtNgaySN = DateTimeLib.toDateDisplay(new Date(ds[0]['ngay_sn']))
          } else {
            this.parent.modelInput.txtNgaySN = ''
          }
          this.parent.modelInput.txtSoGT = ds[0]['so_gt']
          if (ds[0]['ngaycap'] !== null) {
            this.parent.modelInput.txtNgayCap = DateTimeLib.toDateDisplay(new Date(ds[0]['ngaycap']))
          } else {
            this.parent.modelInput.txtNgayCap = ''
          }
          this.parent.modelCbo.cboGiayToKH = ds[0]['loaigt_id']
          this.parent.modelInput.txtNoiCap = this.parent.ChuanHoaTen(ds[0]['noicap'])
          this.parent.modelInput.txtMaSTKH = ds[0]['mst']
          this.parent.modelInput.txtSoTKKH = ds[0]['stk']

          if (ds[0]['ngayhethan_gt'] !== null) {
            this.parent.modelInput.txtNgayHetHan = DateTimeLib.toDateDisplay(new Date(ds[0]['ngayhethan_gt']))
          } else {
            this.parent.modelInput.txtNgayHetHan = ''
          }

          if (ds[0]['gioitinh'] !== null) {
            let iGioiTinh = parseInt(ds[0]['gioitinh'])
            if (iGioiTinh === 0) {
              this.parent.modelCkeckBox.rdoGioiTinh = 0
            } else {
              this.parent.modelCkeckBox.rdoGioiTinh = 1
            }
          } else { this.parent.modelCkeckBox.rdoGioiTinh = null }
          if (ds[0]['quoctich_id'] !== null) {
            this.parent.isEnableCboQuocTich = true
            this.parent.modelCbo.cboQuocTich = ds[0]['quoctich_id']
          } else {
            this.parent.isEnableCboQuocTich = false
            this.parent.modelCbo.cboQuocTich = -1
          }
          if (ds[0]['dantoc_id'] !== null) {
            this.parent.isEnableCboDanToc = true
            this.parent.modelCbo.cboDanToc = ds[0]['dantoc_id']
          } else {
            this.parent.isEnableCboDanToc = false
            this.parent.modelCbo.cboDanToc = -1
          }

          if (ds[0]['nganhnghe_id'] !== null) {
            this.parent.cboNganhNgheLoc_LockEvents = true
            this.parent.isEnableCboNganhNgheLoc = true
            this.parent.modelCbo.cboNganhNgheLoc = ds[0]['nganhnghe_id']
            this.parent.nganhnghe_id = parseInt(ds[0]['nganhnghe_id'])
            this.parent.cboNganhNgheLoc_LockEvents = false
          } else {
            this.parent.isEnableCboNganhNgheLoc = false
            this.parent.modelCbo.cboNganhNgheLoc = this.parent.modelCbo.vcboNganhNgheLoc[0].nganhnghe_id
          }
          this.parent.modelInput.txtDiaChiNDD = this.parent.ChuanHoaTen(ds[0]['diachi_ndd'])
          if (ds[0]['nganhang_id'] !== null) {
            this.parent.modelCbo.cboNganHangKH = parseInt(ds[0]['nganhang_id'])
            this.parent.modelCkeckBox.chkNganHangKH = true
          }
          this.parent.modelInput.txtGhiChuKH = ds[0]['ghichu']
          if (ds[0]['tnc1_id'] !== null) {
            this.parent.TNC1_ID = parseInt(ds[0]['tnc1_id'])
          }
          if (ds[0]['tnc2_id'] !== null) {
            this.parent.TNC2_ID = parseInt(ds[0]['tnc2_id'])
          }
          if (ds[0]['tnc3_id'] !== null) {
            this.parent.TNC3_ID = parseInt(ds[0]['tnc3_id'])
          }
          if (ds[0]['tnc4_id'] !== null) {
            this.parent.TNC4_ID = parseInt(ds[0]['tnc4_id'])
          }
          if (ds[0]['loaikh_id'] !== null) { this.parent.modelCbo.cboLoaiKH = parseInt(ds[0]['loaikh_id']) }

          if (ds[0]['ctv_id'] !== null) {
            this.parent.modelCkeckBox.chkCTV = true
            this.parent.ctv_id = parseInt(ds[0]['ctv_id'])
            this.parent.modelInput.txtCTV = await this.MAP_ID('TEN_NV', this.parent.ctv_id)
            console.log('🚀 ~ file: index.vue ~ line 2081 ~ HienThiTTHopDongKH ~ this.parent.modelInput.txtCTV', this.parent.modelInput.txtCTV)
            await this.parent.chkCTV_CheckedChanged()
          } else {
            this.parent.modelInput.txtCTV = ''
            this.parent.modelCkeckBox.chkCTV = false
          }

          if (ds[0]['nhanviengt_id'] !== null) {
            this.parent.modelCkeckBox.chkNguoiGT = true
            this.parent.nguoigt_id = parseInt(ds[0]['nhanviengt_id'])
            this.parent.modelInput.txtNguoiGT = await this.MAP_ID('TEN_NV', this.parent.nguoigt_id)
            console.log('🚀 ~ file: index.vue ~ line 2092 ~ HienThiTTHopDongKH ~ this.parent.modelInput.txtNguoiGT', this.parent.modelInput.txtNguoiGT)
            await this.parent.chkNguoiGT_CheckedChanged()
          } else {
            this.parent.modelInput.txtNguoiGT = ''
            this.parent.modelCkeckBox.chkNguoiGT = false
          }

          try {
            let strPLKH = await this.MAP_ID('PHANLOAIKH_ID_HD_KHACHHANG', this.parent.hdkh_id)
            if (strPLKH !== null) {
              this.parent.PHANLOAIKH_ID = parseInt(strPLKH)
            }
          } catch (e) {
            console.log('🚀 ~ file: index.vue ~ line 1448 ~ HienThiTTHopDongKH ~ e', e)
          }
          await this.parent.HienThiDS_HDTT_CBO(this.parent.hdkh_id)
          let ds_hd = await this.parent.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.parent.hdkh_id)
          await this.HienThiTTHopDongTT(ds_hd)
          ds_hd = await this.parent.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.parent.hdkh_id)
          await this.HienThiTTHopDongTB(ds_hd)
          ds_hd = await this.parent.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.parent.hdkh_id)
          await this.HienThiDanhSacHDTT(ds_hd)
          ds_hd = await this.parent.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.parent.hdkh_id)
          await this.HienThiDanhSacHDTB(ds_hd)
          this.parent.kieuld_ds = parseInt(this.parent.modelCbo.cboKieuLD)
          if (this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || this.parent.dichvuvt_id === 25) { await this.HT_COMBO_DONVI() }
          this.parent.SetButton(3)
        } else {
          this.parent.SetButton(0)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1321 ~ HienThiTTHopDongKH ~ error', error)
      }
    },
    async HienThiTTHopDongTT (ds) {
      console.log('🚀 ~ file: index.vue ~ line 1465 ~ HienThiTTHopDongTT ~ ds', ds)
      try {
        if (ds !== null && ds.length > 0) {
          this.parent.hdtt_id = parseInt(ds[0]['hdtt_id'])
          this.parent.modelInput.txtMaTT = ds[0]['ma_tt']
          if (ds[0]['thanhtoan_id'] !== null) { this.parent.thanhtoan_id = parseInt(ds[0]['thanhtoan_id']) } else { this.parent.thanhtoan_id = 0 }
          let dt_madd = await this.HT_COMBO_MA_DAIDIEN(this.parent.thanhtoan_id)
          this.parent.modelCbo.vcboMaDD = dt_madd
          this.parent.modelCbo.cboMaDD = ds[0]['matb_dd']

          if (ds[0]['hdmig_id'] !== null) {
            this.parent.hdmig_id = parseInt(ds[0]['hdmig_id'])
            this.parent.modelInput.txtMaIG = await this.MAP_ID('MAIN_GHEP', this.parent.hdmig_id)
          } else {
            this.parent.hdmig_id = 0
            this.parent.modelInput.txtMaIG = ''
          }
          this.parent.modelInput.txtTenTT = this.parent.ChuanHoaTen(ds[0]['ten_tt'])
          this.parent.modelInput.txtDiaChiTT = this.parent.ChuanHoaTen(ds[0]['diachi_tt'])
          this.parent.modelInput.txtDiaChiBC = this.parent.ChuanHoaTen(ds[0]['diachi_bc'])
          // TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT --- Ngày 12/11/2010
          this.parent.DV_QHNS = ds[0]['dv_qhns']
          this.parent.MACHUONG = ds[0]['machuong']
          this.parent.MN_KT = ds[0]['mn_kt']
          this.parent.MA_NDKT = ds[0]['ma_ndkt']
          this.parent.MN_NSNN = ds[0]['mn_nsnn']
          this.parent.TIEN_DM = parseInt(ds[0]['tien_dm'])
          let ds2 = await this.LAY_DS_DIACHI_THEO_HDTT_ID(this.parent.hdtt_id)
          await this.HienThiTTDiaChi(ds2, 2)
          this.parent.modelInput.txtMST = ds[0]['mst']
          this.parent.modelInput.txtSTKTT = ds[0]['stk']
          this.parent.modelInput.txtChu_TK = ds[0]['chu_tk']
          if (ds[0]['nganhang_id'] !== null) { this.parent.modelCbo.cboNganHangTT = ds[0]['nganhang_id'] }
          this.parent.modelCbo.cboHTTT = ds[0]['httt_id']

          this.parent.modelCbo.cboDonViQL = ds[0]['donvi_id']
          if (ds[0]['nhanvien_id'] !== null) {
            this.parent.nhanvientc_id = parseInt(ds[0]['nhanvien_id'])
            this.parent.modelInput.txtMaNVTC = await this.MAP_ID('MA_NV', this.parent.nhanvientc_id)
          } else {
            this.parent.nhanvientc_id = 0
            this.parent.modelInput.txtMaNVTC = ''
            console.log("🚀 ~ file: index.vue:2359 ~ HienThiTTHopDongTT ~ this.parent.modelInput.txtMaNVTC = '':", this.parent.modelInput.txtMaNVTC = '')
          }
          if (ds[0]['tuyenthu_id'] !== null) {
            this.parent.tuyenthu_id = parseInt(ds[0]['tuyenthu_id'])
            this.parent.modelInput.txtMaTuyen = await this.MAP_ID('MA_TUYEN', this.parent.tuyenthu_id)
          } else {
            this.parent.tuyenthu_id = 0
            this.parent.modelInput.txtMaTuyen = ''
          }
          this.parent.matb_dd = ds[0]['matb_dd']
          // Hiếu bố sung matb_dd 1/09/2010
          this.parent.modelInput.txtMaTBDD = ds[0]['matb_dd']
          //
          this.parent.modelInput.txtDiaChiCT = this.parent.ChuanHoaTen(ds[0]['diachi_ct'])
          this.parent.modelInput.txtGhiChuTT = ds[0]['ghichu']
          await this.LAY_DS_SUDUNG_DVK(this.parent.thanhtoan_id, 2)
          await this.HienThi_NVQL_AM_DB_HD(2, this.parent.hdtt_id)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1467 ~ HienThiTTHopDongTT ~ error', error)
      }
    },
    async LAY_DS_DIACHI_THEO_HDTT_ID (hdttId) {
      try {
        let rs = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_hdttid', {hdttId})
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1506 ~ LAY_DS_DIACHI_THEO_HDTT_ID ~ error', error)
      }
    },
    async LAY_DS_DIACHI_THEO_HDTB_ID (hdtb_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_diachi_theo_hdtb_id', {hdtb_id})
        return rs.data
      } catch (error) {
        return []
      }
    },
    acceptThongSoCoDinh (data) {
      try {
        console.log('🚀 ~ file: index.vue:2398 ~ acceptThongSoCoDinh ~ data:', data)
        Object.assign(this.parent.thongso_ban, data.thongso_ban)
        Object.assign(this.parent.thongso_tc, data.thongso_tc)
        this.$refs.refCD.cboTrangBiCD.Value = data.thongso_ban.trangbi_id
        this.parent.modelCbo.cboDoituongTB = data.thongso_ban.doituong_id
        this.parent.modelCbo.cboMucCuocTB = data.thongso_ban.muccuoctb_id
        if (this.$refs.refIMS !== undefined && this.$refs.refIMS !== null) {
          this.$refs.refIMS.txtMATN_IMS.Text = this.parent.thongso_tc.ma_tn
        }
        if (this.$refs.refCD !== undefined && this.$refs.refCD !== null) {
          this.$refs.refCD.txtMATN_CD.Text = this.parent.thongso_tc.ma_tn
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2407 ~ acceptThongSoCoDinh ~ error:', error)
      }
    },
    async HienThiTTHopDongTB (ds) {
      console.log('🚀 ~ file: index.vue ~ line 2240 ~ HienThiTTHopDongTB ~ ds', ds)
      try {
        if (ds !== null && ds.length > 0) {
          this.parent.ikieuld_id = 0
          this.parent.hdtb_id = parseInt(ds[0]['hdtb_id'])
          if (ds[0]['thuebao_id'] !== null) { this.parent.thuebao_id = parseInt(ds[0]['thuebao_id']) } else { this.parent.thuebao_id = 0 }
          if (this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || this.parent.dichvuvt_id === 25) {
            let dt = await this.LAY_TINHTC_ID_TSL(this.parent.modelInput.txtMaTB, this.parent.dichvuvt_id)
            if (dt && dt.length > 0) {
              this.parent.kenh_trang = false
              if (this.parent.BanCheoData.Tinh_TC.tinhthicong_id != null) { this.parent.BanCheoData.Tinh_TC.tinhthicong_id = 0 } else {
                this.parent.kenh_trang = true
                this.parent.thuebao_tc_id = 0
              }
            }
            await this.Gan_thongso_bancheo_hdtb_tsl(this.parent.hdtb_id)
          } else {
            this.parent.BanCheoData.Tinh_TC = await this.LAY_TINHTHICONG_THEO_THUEBAOID(this.parent.thuebao_id)
            if (this.parent.BanCheoData.Tinh_TC.tinhthicong_id === 0) {
              this.parent.kenh_trang = true
            }
            await this.Gan_thongso_bancheo_hdtb(this.parent.hdtb_id)
          }
          let dsNV = []
          if (this.parent.thuebao_id !== 0) { dsNV = await this.LAY_DS_KHUVUC_THEO_HD_DB(this.parent.thuebao_id, 2) } else { dsNV = await this.LAY_DS_KHUVUC_THEO_HD_DB(this.parent.hdtb_id, 1) }
          this.parent.modelInput.txtKhuVuc = ''
          if (dsNV != null && dsNV.length > 0) {
            this.parent.modelInput.txtKhuVuc = dsNV[0]['ten_kv']
            this.parent.khuvuc_id = parseInt(dsNV[0]['khuvuc_id'])
          }

          this.parent.modelInput.txtMaTB = ds[0]['ma_tb']
          this.parent.ma_tb = this.parent.modelInput.txtMaTB
          this.parent.modelInput.txtTenTB = this.parent.ChuanHoaTen(ds[0]['ten_tb'])
          this.parent.ma_tbmoi = ds[0]['ma_tb']
          this.parent.modelInput.txtMaTBMoi = ds[0]['ma_tb']
          this.parent.modelInput.txtDiaChiTB = this.parent.ChuanHoaTen(ds[0]['diachi_tb'])
          // hieu
          this.parent.modelInput.txtDiaChiLD = this.parent.ChuanHoaTen(ds[0]['diachi_ld'])

          if (ds[0]['ngay_sn'] !== null) {
            this.parent.modelInput.txtNgaySinhTB = DateTimeLib.toDateDisplay(new Date(ds[0]['ngay_sn']))
          } else {
            this.parent.modelInput.txtNgaySinhTB = ''
          }

          if (ds[0]['hdtb_cha_id'] !== null) {
            this.parent.hdtb_id_cha = parseInt(ds[0]['hdtb_cha_id'])
            this.parent.loaisochinh = 0
          }
          let ds2 = await this.LAY_DS_DIACHI_THEO_HDTB_ID(this.parent.hdtb_id)
          await this.HienThiTTDiaChi(ds2, 3)
          let TTCT_LoaiHinh_TB = await this.TTCT_LoaiHinh_TB(parseInt(ds[0]['loaitb_id']))
          if (TTCT_LoaiHinh_TB !== null && TTCT_LoaiHinh_TB.length > 0) {
            this.parent.dichvuvt_id = parseInt(TTCT_LoaiHinh_TB[0].dichvuvt_id)
          }
          this.parent.modelCbo.cboDichVuVT = Number(this.parent.dichvuvt_id)
          await this.cboDichVuVT_SelectedValueChanged()

          if (ds[0]['goikt_id'] !== null) { this.parent.modelCbo.cboGoiKT = parseInt(ds[0]['goikt_id']) } else { this.parent.modelCbo.cboGoiKT = this.parent.modelCbo.vcboGoiKT[0].goikt_id }
          this.parent.modelCbo.cboLoaihinhTB = parseInt(ds[0]['loaitb_id'])
          this.parent._loaitb_id = parseInt(ds[0]['loaitb_id'])
          this.parent.modelCbo.cboKieuLD = ds[0]['kieuld_id']
          this.parent.modelCbo.cboDoituongTB = ds[0]['doituong_id']
          this.parent.modelCbo.cboTT = ds[0]['hdtt_id']
          this.parent.modelCbo.cboDaiVT = ds[0]['donvi_id']
          // Hiếu bổ sung lấy đơn vị đầu, đơn vị cuối ngày 06/09/2010
          if (ds[0]['donvi_dau_id'] !== null) { this.parent.donvi_dau_id = parseInt(ds[0]['donvi_dau_id']) }
          if (ds[0]['donvi_cuoi_id'] !== null) { this.parent.donvi_cuoi_id = parseInt(ds[0]['donvi_cuoi_id']) }
          if ((ds[0]['mucuoctb_id'] !== null)) {
            await this.parent.HT_MucCuocTB_Combobox(parseInt(ds[0]['mucuoctb_id']))
          } else {
            this.parent.modelCbo.vcboMucCuocTB = []
            this.parent.modelCbo.cboMucCuocTB = -1
          }
          this.parent.m_dsThueBao_DichVu.dsdvgt_ban = await this.TC_LAY_DS_THUEBAO_DICHVU(this.parent.hdtb_id, this.parent.thuebao_id, this.parent.loaitb_id, 1)
          await this.HT_DichVuGT()
          await this.LAY_DS_SUDUNG_DVK(this.parent.thuebao_id, 1)
          ds2 = await this.parent.LAY_BIENDONG_THEO_HDTB_ID(this.parent.hdtb_id)
          await this.HienThiThongTinCu(ds2)
          await this.HienThiTTMoRongHD(this.parent.hdtb_id, this.parent.dichvuvt_id)
          let dtKC = await this.LAY_THONG_TIN_KET_CUOI(this.parent.hdtb_id)
          if (dtKC !== null && dtKC.length > 0) {
            this.parent.KetCuoi_ID = parseInt(dtKC[0].ketcuoi_id)
          } else {
            this.parent.KetCuoi_ID = -1
          }
          await this.HT_DS_KhuyenMai_Combobox()
          let dsTemp = await this.LAY_CHITIET_KM_THEO_HDTB_ID(this.parent.hdtb_id, 0)
          if (dsTemp !== null && dsTemp.length > 0) {
            if (dsTemp[0]['khuyenmai_id'] !== null) {
              this.parent.modelCbo.cboKhuyenMai = dsTemp[0]['khuyenmai_id']
              this.parent.modelCkeckBox.chkKhuyenMai = true
            } else {
              this.parent.modelCkeckBox.chkKhuyenMai = false
            }
            let ithang_huongdc = 0
            let ithang_huongkm = 0
            let ithang_tg = 0
            let dtiendatcoc_csd = 0
            let dtien_td = 0
            this.parent.dsKM_HDTB = []
            for (let index = 0; index < dsTemp.length; index++) {
              const element = dsTemp[index]
              ithang_huongdc = 0
              ithang_huongkm = 0
              ithang_tg = 0
              dtiendatcoc_csd = 0
              dtien_td = 0
              let dr = {}
              ithang_huongdc = parseInt(element.thang_huongdc)
              ithang_huongkm = parseInt(element.thang_huongkm)
              ithang_tg = parseInt(element.sothang_tg)
              dtiendatcoc_csd = parseInt(element.datcoc_csd)
              dtien_td = parseInt(element.tien_td)

              dr.THANG_HUONGDC = ithang_huongdc
              dr.THANG_HUONGKM = ithang_huongkm
              dr.SOTHANG_TG = ithang_tg
              dr.DATCOC_CSD = dtiendatcoc_csd
              dr.TIEN_TD = dtien_td
              let tmp = null
              if (element.kieu_bdkm.toString() === '3') {
                try {
                  tmp = DateTimeLib.toDateObjectWithFormat(element.thang_bdkm, 'yyyyMM')
                  dr.THANG_BD = parseInt(element.thang_bdkm)
                  dr.THANG_KT = tmp.addMonths(ithang_huongkm)
                } catch (error) {
                  console.log('🚀 ~ file: index.vue ~ line 1648 ~ HienThiTTHopDongTB ~ error', error)
                }
              }
              if (element.kieu_bdtg.toString() === '3') {
                try {
                  tmp = DateTimeLib.toDateObjectWithFormat(element.thang_bdtg, 'yyyyMM')
                  dr.THANG_BDTG = parseInt(element.thang_bdtg)
                  dr.THANG_KTTG = tmp.addMonths(ithang_tg)
                } catch (error) {
                  console.log('🚀 ~ file: index.vue ~ line 1657 ~ HienThiTTHopDongTB ~ error', error)
                }
              }
              if (element.kieu_bddc.toString() === '3') {
                try {
                  tmp = DateTimeLib.toDateObjectWithFormat(element.thang_bddc, 'yyyyMM')
                  dr.THANG_BDTG = parseInt(element.thang_bddc)
                  dr.THANG_KTTG = tmp.addMonths(ithang_huongkm)
                } catch (error) {
                  console.log('🚀 ~ file: index.vue ~ line 1666 ~ HienThiTTHopDongTB ~ error', error)
                }
              }
              // longdx: bổ sung thông tin tỷ lệ km
              dr.TYLE_KMLD = parseInt(element.tyle_kmld)
              dr.TYLE_SD = parseInt(element.tyle_sd)
              dr.TYLE_TB = parseInt(element.tyle_tb)
              dr.HDTB_ID = this.parent.hdtb_id
              dr.CHITIETKM_ID = parseInt(element.chitietkm_id)
              dr.KHUYENMAI_ID = parseInt(element.khuyenmai_id)
              dr.KIEU_YC = parseInt(element.kieu_yc)
              this.parent.dsKM_HDTB.push(dr)
            }
          } else {
            this.parent.modelCkeckBox.chkKhuyenMai = false
            this.parent.dsKM_HDTB = []
          }
          this.parent.ds_tien_khoanmuc = await this.LAY_TIENHD_KM_THEO_HDTB(this.parent.hdtb_id)
          for (let i = 0; i < this.parent.ds_tien_khoanmuc.length; i++) {
            if (this.parent.ds_tien_khoanmuc[i]['khoanmuctt_id'].toString() === Enum.KHOANMUC_TT.KMTT_CHUYENDICH.toString()) {
              this.parent.dtienld = parseInt(this.parent.ds_tien_khoanmuc[i]['tien'])
              this.parent.dvatld = parseInt(this.parent.ds_tien_khoanmuc[i]['vat'])
            }
            if (this.parent.ds_tien_khoanmuc[i]['khoanmuctt_id'].toString() === Enum.KHOANMUC_TT.KMTT_KM_LAPDAT.toString()) {
              this.parent.dtienkmld = parseInt(this.parent.ds_tien_khoanmuc[i]['tien'])
              this.parent.dvatkmld = parseInt(this.parent.ds_tien_khoanmuc[i]['vat'])
            }
            if (this.parent.ds_tien_khoanmuc[i]['khoanmuctt_id'].toString() === Enum.KHOANMUC_TT.KMTT_DICHVU.toString()) {
              this.parent.dtien_dv = parseInt(this.parent.ds_tien_khoanmuc[i]['tien'])
              this.parent.dvat_dv = parseInt(this.parent.ds_tien_khoanmuc[i]['vat'])
            }
          }
          await this.LayTongTien_HDTB()
          // try {
          //   let data = {
          //     loai: 'HDTB_DV',
          //     value: this.parent.hdtb_id
          //   }
          //   let dtTemp = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getdata', data)
          //   if (dtTemp.data.length > 0) {
          //     let dtControl = await this.parent.getControl()
          //     for (let index = 0; index < dtControl.dsComboDonVi.length; index++) {
          //       const element = dtControl.dsComboDonVi[index]
          //       let dr = dtTemp.data.filter(e => e.loaidv_id === element.loaidv_id)
          //       if (dr.length > 0) {
          //         dtControl.ref.$refs[element.key][0].value = dr[0].donvi_id
          //       }
          //     }
          //   }
          // } catch (error) {
          //   console.log('🚀 ~ file: index.vue ~ line 1708 ~ HienThiTTHopDongTB ~ error', error)
          // }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1535 ~ HienThiTTHopDongTB ~ error', error)
      }
    },
    async LAY_TIENHD_KM_THEO_HDTB (HDTB_ID) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_tienhd_km_theo_hdtb', {HDTB_ID})
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1798 ~ LAY_TIENHD_KM_THEO_HDTB ~ error', error)
        return []
      }
    },
    async LAY_CHITIET_KM_THEO_HDTB_ID (vhdtb_id, vloai_km) {
      try {
        let rs = await this.$root.context.get('/web-hopdong/HoanCongThuTienPhatSinh/lay_chitiet_km_theo_hdtb_id?vhdtb_id=' + vhdtb_id + '&vloai_km=' + vloai_km)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1807 ~ LAY_CHITIET_KM_THEO_HDTB_ID ~ error', error)
        return []
      }
    },
    async LAY_THONG_TIN_KET_CUOI (hdtb_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/lay_thong_tin_ket_cuoi', {hdtb_id})
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1611 ~ LAY_THONG_TIN_KET_CUOI ~ error', error)
        return []
      }
    },
    async HienThiThongTinCu (ds) {
      try {
        if (ds !== null && ds.length > 0) {
          this.parent.modelInput.txtMaHDcu = ds[0]['ma_hd']
          this.parent.modelDateTime.dtpNgayLapHD = ds[0]['ngaylap_hd'] ? new Date(ds[0]['ngaylap_hd']) : new Date()
          this.parent.modelInput.txtMaTB = ds[0]['ma_tb']
          if (this.parent.modelInput.txtMaTB === this.parent.modelInput.txtMaTBMoi) { this.parent.modelInput.txtMaTBMoi = '' }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1610 ~ HienThiThongTinCu ~ error', error)
      }
    },
    async HienThiTTMoRongHD (hdtb_id, dichvuvt_id) {
      console.log('🚀 ~ file: index.vue:2671 ~ HienThiTTMoRongHD ~ hdtb_id, dichvuvt_id:', hdtb_id, dichvuvt_id)
      try {
        this.parent.modelCkeckBox.chkCongVan = false
        this.parent.modelCkeckBox.chkdisableCongVan = true
        this.parent.modelInput.txtCongVan = ''
        this.parent.modelDateTime.dtpNgayCV = new Date()
        this.parent.kt_kenhphu_ao = false

        switch (dichvuvt_id) {
          case Enum.DichVuVienThong.CO_DINH:
            await this.$refs.refCD.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.IMS:
            await this.$refs.refIMS.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.ADSL:
            await this.$refs.refAdsl.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.MEGA_EYES:
            await this.$refs.refAdsl.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.MEGAWAN:
          case Enum.DichVuVienThong.METRONET:
          case 26:
            await this.$refs.refWan.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.TSL:
          case 25:
            await this.$refs.refTSL.HienThiTTMoRongHD(hdtb_id)
            break
          case Enum.DichVuVienThong.GPHONE:
            await this.$refs.refGP.HienThiTTMoRongHD(hdtb_id)
            break
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1617 ~ HienThiTTMoRongHD ~ error', error)
      }
    },
    async TTCT_LoaiHinh_TB (loaitb_id) {
      try {
        let data = {
          loai: 'LOAIHINH_TB',
          value: loaitb_id
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getdata', data)
        if (rs.data.length > 0) {
          return rs.data
        } else {
          return []
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1592 ~ TTCT_LoaiHinh_TB ~ error', error)
        return []
      }
    },
    async LAY_TINHTHICONG_THEO_THUEBAOID (thuebao_id) {
      try {
        let Tinh_TC = {}
        let rs = await this.$root.context.get('/web-hopdong/thanhly-thuebao-bancheo/lay_tinh_theo_thuebaoid', {thuebao_id})
        if (rs.data.length > 0) { Tinh_TC = await this.tinh_thicong(parseInt(rs.data[0]['tinh_thicong'])) } else { Tinh_TC.tinhthicong_id = 0 }
        return Tinh_TC
      } catch (error) {
        return {}
      }
    },
    async LAY_THONGSO_THUEBAO_BANCHEO_HDTBID (hdtbId) {
      console.log('🚀 ~ file: index.vue ~ line 1837 ~ LAY_THONGSO_THUEBAO_BANCHEO_HDTBID ~ hdtbId', hdtbId)
      try {
        let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_thongso_thuebao_bancheo_hdtbid', {hdtbId})
        console.log('🚀 ~ file: index.vue ~ line 1839 ~ LAY_THONGSO_THUEBAO_BANCHEO_HDTBID ~ rs', rs)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1842 ~ LAY_THONGSO_THUEBAO_BANCHEO_HDTBID ~ error', error)
        return []
      }
    },
    async Gan_thongso_bancheo_hdtb_tsl (vhdtb) {
      let ds = await this.LAY_THONGSO_THUEBAO_BANCHEO_HDTBID(vhdtb)
      console.log('🚀 ~ file: index.vue ~ line 1846 ~ Gan_thongso_bancheo_hdtb_tsl ~ ds', ds)
      if (ds !== null && ds.length > 0) {
        ds = this.parent.UpperCasePropertyList(ds)
        for (let index = 0; index < ds.length; index++) {
          const element = ds[index]
          let drTHONGSO = {}
          drTHONGSO = {...element}
          this.parent.thongso_ban.kieuld_id = drTHONGSO.KIEULD_BAN
          this.parent.thongso_ban.doituong_id = drTHONGSO.DOITUONG_BAN
          this.parent.thongso_ban.chuquan_id = drTHONGSO.CHUQUAN_BAN
          this.parent.thongso_ban.chuquanld_id = drTHONGSO.CHUQUANLD_BAN
          this.parent.thongso_ban.chuquanld_id_cuoi = drTHONGSO.CHUQUANLD_BAN_CUOI

          this.parent.thongso_ban.congnghe_id = drTHONGSO.CONGNGHE_BAN
          this.parent.thongso_ban.kieutra_id = drTHONGSO.KIEUTRA_BAN
          this.parent.thongso_ban.loaikenh_id = drTHONGSO.LOAIKENH_BAN
          this.parent.thongso_ban.loaimd_id = drTHONGSO.LOAIMD_BAN
          this.parent.thongso_ban.ma_doicap = drTHONGSO.MADOICAP_BAN

          this.parent.thongso_ban.ma_tn = drTHONGSO.MATN_BAN

          this.parent.thongso_ban.muccuoc_id = drTHONGSO.MUCCUOC_BAN
          this.parent.thongso_ban.muccuoctb_id = drTHONGSO.MUCCUOCTB_BAN
          this.parent.thongso_ban.thietbidc_id = drTHONGSO.THIETBIDAU_BAN
          this.parent.thongso_ban.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_BAN
          this.parent.thongso_ban.thoihan_id = drTHONGSO.THOIHAN_BAN
          this.parent.thongso_ban.tocdo_id = drTHONGSO.TOCDO_BAN
          this.parent.thongso_ban.tocdo_isp = drTHONGSO.TOCDOISP_BAN
          this.parent.thongso_ban.tocdo_nix = drTHONGSO.TOCDONIX_BAN

          try {
            this.parent.thongso_ban.tocdo_pir_id = drTHONGSO.TOCDOPIR_BAN
          } catch (e) { }
          this.parent.thongso_ban.tocdothuc_id = drTHONGSO.TOCDOTHUC_BAN
          this.parent.thongso_ban.trangbi_id = drTHONGSO.TRANGBI_BAN

          if (element.tinh_dc_id === '1') {
            this.parent.thongso_tc_dau.kieuld_id = drTHONGSO.KIEULD_TC
            this.parent.thongso_tc_dau.doituong_id = drTHONGSO.DOITUONG_TC
            this.parent.thongso_tc_dau.chuquan_id = drTHONGSO.CHUQUAN_TC
            this.parent.thongso_tc_dau.chuquanld_id = drTHONGSO.CHUQUANLD_TC
            this.parent.thongso_tc_dau.congnghe_id = drTHONGSO.CONGNGHE_TC
            this.parent.thongso_tc_dau.kieutra_id = drTHONGSO.KIEUTRA_TC
            this.parent.thongso_tc_dau.loaikenh_id = drTHONGSO.LOAIKENH_TC
            this.parent.thongso_tc_dau.loaimd_id = drTHONGSO.LOAIMD_TC
            this.parent.thongso_tc_dau.ma_doicap = drTHONGSO.MADOICAP_TC
            this.parent.thongso_tc_dau.ma_tn = drTHONGSO.MATN_TC
            this.parent.thongso_tc_dau.muccuoc_id = drTHONGSO.MUCCUOC_TC
            this.parent.thongso_tc_dau.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC
            this.parent.thongso_tc_dau.thietbidc_id = drTHONGSO.THIETBIDAU_TC
            this.parent.thongso_tc_dau.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC
            this.parent.thongso_tc_dau.thoihan_id = drTHONGSO.THOIHAN_TC
            this.parent.thongso_tc_dau.tocdo_id = drTHONGSO.TOCDO_TC
            this.parent.thongso_tc_dau.tocdo_isp = drTHONGSO.TOCDOISP_TC
            this.parent.thongso_tc_dau.tocdo_nix = drTHONGSO.TOCDONIX_TC
            try {
              this.parent.thongso_tc_dau.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC
            } catch (e) { }
            this.parent.thongso_tc_dau.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC
            this.parent.thongso_tc_dau.trangbi_id = drTHONGSO.TRANGBI_TC
          } else {
            this.parent.thongso_tc_cuoi.kieuld_id = drTHONGSO.KIEULD_TC
            this.parent.thongso_tc_cuoi.doituong_id = drTHONGSO.DOITUONG_TC
            this.parent.thongso_tc_cuoi.chuquan_id = drTHONGSO.CHUQUAN_TC
            this.parent.thongso_tc_cuoi.chuquanld_id = drTHONGSO.CHUQUANLD_TC
            this.parent.thongso_tc_cuoi.congnghe_id = drTHONGSO.CONGNGHE_TC
            this.parent.thongso_tc_cuoi.kieutra_id = drTHONGSO.KIEUTRA_TC
            this.parent.thongso_tc_cuoi.loaikenh_id = drTHONGSO.LOAIKENH_TC
            this.parent.thongso_tc_cuoi.loaimd_id = drTHONGSO.LOAIMD_TC
            this.parent.thongso_tc_cuoi.ma_doicap = drTHONGSO.MADOICAP_TC
            this.parent.thongso_tc_cuoi.ma_tn = drTHONGSO.MATN_TC
            this.parent.thongso_tc_cuoi.muccuoc_id = drTHONGSO.MUCCUOC_TC
            this.parent.thongso_tc_cuoi.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC
            this.parent.thongso_tc_cuoi.thietbidc_id = drTHONGSO.THIETBIDAU_TC
            this.parent.thongso_tc_cuoi.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC
            this.parent.thongso_tc_cuoi.thoihan_id = drTHONGSO.THOIHAN_TC
            this.parent.thongso_tc_cuoi.tocdo_id = drTHONGSO.TOCDO_TC
            this.parent.thongso_tc_cuoi.tocdo_isp = drTHONGSO.TOCDOISP_TC
            this.parent.thongso_tc_cuoi.tocdo_nix = drTHONGSO.TOCDONIX_TC
            try {
              this.parent.thongso_tc_cuoi.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC
            } catch (e) { }
            this.parent.thongso_tc_cuoi.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC
            this.parent.thongso_tc_cuoi.trangbi_id = drTHONGSO.TRANGBI_TC
          }
        }
      }
    },
    async Gan_thongso_bancheo_hdtb (vhdtb) {
      try {
        let ds = await this.LAY_THONGSO_THUEBAO_BANCHEO_HDTBID(vhdtb)
        if (ds !== null && ds.length > 0) {
          ds = this.parent.UpperCasePropertyList(ds)
          let drTHONGSO = { ...ds[0] }
          let dvvt_id = parseInt(ds[0]['dichvuvt_id'])
          if (dvvt_id !== Enum.DichVuVienThong.HOINGHI_TRUYENHINH &&
                    dvvt_id !== Enum.DichVuVienThong.ANTOAN_BAOMAT_TT &&
                    dvvt_id !== Enum.DichVuVienThong.DICHVU_CNTT &&
                    dvvt_id !== Enum.DichVuVienThong.TRUNGTAM_DULIEU) {
            this.parent.thongso_ban.chuquanld_id = drTHONGSO.CHUQUANLD_BAN
            this.parent.thongso_ban.congnghe_id = drTHONGSO.CONGNGHE_BAN
            this.parent.thongso_ban.loaikenh_id = drTHONGSO.LOAIKENH_BAN
            this.parent.thongso_ban.loaimd_id = drTHONGSO.LOAIMD_BAN
            this.parent.thongso_ban.ma_doicap = drTHONGSO.MADOICAP_BAN
            this.parent.thongso_ban.chuquan_id = drTHONGSO.CHUQUAN_BAN
            this.parent.thongso_ban.ma_tn = drTHONGSO.MATN_BAN
            this.parent.thongso_ban.thietbidc_id = drTHONGSO.THIETBIDAU_BAN
            this.parent.thongso_ban.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_BAN
            this.parent.thongso_ban.thoihan_id = drTHONGSO.THOIHAN_BAN
            this.parent.thongso_ban.tocdo_id = drTHONGSO.TOCDO_BAN
            this.parent.thongso_ban.tocdo_isp = drTHONGSO.TOCDOISP_BAN
            this.parent.thongso_ban.tocdo_nix = drTHONGSO.TOCDONIX_BAN
            try {
              this.parent.thongso_ban.tocdo_pir_id = drTHONGSO.TOCDOPIR_BAN
              this.parent.thongso_ban.phanloai_id = drTHONGSO.PHANLOAI_ID
              this.parent.thongso_ban.phantach_id = drTHONGSO.PHANTACH_TD
            } catch (e) { }
            this.parent.thongso_ban.tocdothuc_id = drTHONGSO.TOCDOTHUC_BAN
            this.parent.thongso_ban.trangbi_id = drTHONGSO.TRANGBI_BAN

            this.parent.thongso_tc.chuquan_id = drTHONGSO.CHUQUAN_TC
            this.parent.thongso_tc.chuquanld_id = drTHONGSO.CHUQUANLD_TC
            this.parent.thongso_tc.congnghe_id = drTHONGSO.CONGNGHE_TC
            this.parent.thongso_tc.loaikenh_id = drTHONGSO.LOAIKENH_TC
            this.parent.thongso_tc.loaimd_id = drTHONGSO.LOAIMD_TC
            this.parent.thongso_tc.ma_doicap = drTHONGSO.MADOICAP_TC
            this.parent.thongso_tc.ma_tn = drTHONGSO.MATN_TC

            this.parent.thongso_tc.thietbidc_id = drTHONGSO.THIETBIDAU_TC
            this.parent.thongso_tc.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC
            try {
              this.parent.thongso_tc.thoihan_id = drTHONGSO.THOIHAN_TC
            } catch (e) { }
            this.parent.thongso_tc.tocdo_id = drTHONGSO.TOCDO_TC
            this.parent.thongso_tc.tocdo_isp = drTHONGSO.TOCDOISP_TC
            this.parent.thongso_tc.tocdo_nix = drTHONGSO.TOCDONIX_TC
            try {
              this.parent.thongso_tc.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC
            } catch (e) { }
            this.parent.thongso_tc.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC
            this.parent.thongso_tc.trangbi_id = drTHONGSO.TRANGBI_TC
          }
          this.parent.thongso_ban.muccuoc_id = drTHONGSO.MUCCUOC_BAN
          this.parent.thongso_ban.muccuoctb_id = drTHONGSO.MUCCUOCTB_BAN
          this.parent.thongso_ban.kieuld_id = drTHONGSO.KIEULD_BAN
          this.parent.thongso_ban.doituong_id = drTHONGSO.DOITUONG_BAN
          this.parent.thongso_tc.kieuld_id = drTHONGSO.KIEULD_TC
          this.parent.thongso_tc.doituong_id = drTHONGSO.DOITUONG_TC
          this.parent.thongso_tc.muccuoc_id = drTHONGSO.MUCCUOC_TC
          this.parent.thongso_tc.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC

          this.parent.thongso_tc.kieutra_id = drTHONGSO.KIEUTRA_TC
          this.parent.thongso_ban.kieutra_id = drTHONGSO.KIEUTRA_BAN
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2005 ~ Gan_thongso_bancheo_hdtb ~ error', error)
      }
    },
    async HienThiDanhSacHDTT (ds) {
      try {
        this.parent.modelInput.lvwThanhToan = []
        let myDataTables = ds
        if (myDataTables !== null && myDataTables.length > 0) {
          for (let index = 0; index < myDataTables.length; index++) {
            const myRow = myDataTables[index]
            let lvItem = {}
            lvItem.stt = index + 1
            lvItem.hdtt_id = myRow.hdtt_id
            lvItem.ma_tt = myRow.ma_tt

            lvItem.ten_tt = this.parent.ChuanHoaTen(myRow.ten_tt)
            lvItem.diachi_tt = this.parent.ChuanHoaTen(myRow.diachi_tt)
            lvItem.diachi_ct = this.parent.ChuanHoaTen(myRow.diachi_ct)
            this.parent.modelInput.lvwThanhToan.push(lvItem)
          }
          if (myDataTables.length <= 1) { this.parent.buttonState.tsbtnXoaTT = false } else { this.parent.buttonState.tsbtnXoaTT = true }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1992 ~ HienThiDanhSacHDTT ~ error', error)
      }
    },
    async HienThiDanhSacHDTB (ds) {
      try {
        let tongtienhd = 0
        let tongtientb = 0
        this.parent.modelInput.lvwThueBao = []
        let myDataTables = ds
        if (myDataTables !== null && myDataTables.length > 0) {
          let ds_khcu = await this.parent.LAY_BIENDONG_THEO_HDTB_ID(myDataTables[0].hdtb_id)
          if (ds_khcu !== null && ds_khcu.length > 0) {
            this.parent.khach_hang_id = parseInt(ds_khcu[0].khachhang_id)
          }
          for (let index = 0; index < myDataTables.length; index++) {
            const myRow = myDataTables[index]
            tongtientb = parseInt(myRow.tongtien)
            let lvItem = {}
            lvItem.stt = index + 1
            lvItem.ma_tt = myRow.ma_tt
            lvItem.hdtb_id = myRow.hdtb_id
            lvItem.ma_tb = myRow.ma_tb
            lvItem.ten_tb = this.parent.ChuanHoaTen(myRow.ten_tb)
            lvItem.diachi_ld = this.parent.ChuanHoaTen(myRow.diachi_ld)
            lvItem.tongtientb = tongtientb
            lvItem.ten_dvvt = myRow.ten_dvvt
            lvItem.manv_tc = myRow.manv_tc
            tongtienhd = tongtienhd + tongtientb
            this.parent.modelInput.lvwThueBao.push(lvItem)
          }
          if (myDataTables.length <= 1) {
            this.parent.buttonState.tsbtnXoaTB = false
          } else { this.parent.buttonState.tsbtnXoaTB = true }
        }
        this.parent.modelInput.txtTongTienHD = tongtienhd
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2014 ~ HienThiDanhSacHDTB ~ error', error)
      }
    },
    async LAY_TINHTC_ID_TSL (vma_tb, vdichvuvt_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/thanhly-thuebao-bancheo/lay_tinhtc_id_tsl', {vma_tb, vdichvuvt_id})
        console.log('🚀 ~ file: index.vue ~ line 1470 ~ LAY_TINHTC_ID_TSL ~ rs', rs)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1472 ~ LAY_TINHTC_ID_TSL ~ error', error)
      }
    },
    async LAY_DANHBA_THEO_MATBTC (tinh, ma_tb, dichvuvt_id, donvi_dl_id, loaihd_bancheo) {
      try {
        let data = { tinhthicong_id: tinh.tinhthicong_id, ma_tb, dichvuvt_id, donvi_dl_id, loaihd_bancheo }
        console.log('🚀 ~ file: index.vue ~ line 218 ~ LAY_DANHBA_THEO_MATBTC ~ data', data)
        let rs = await this.$root.context.post('/web-bancheo/chuyen-dich/lay_danhba_theo_matbtc', data)
        console.log('🚀 ~ file: index.vue ~ line 218 ~ LAY_DANHBA_THEO_MATBTC ~ rs', rs)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 219 ~ LAY_DANHBA_THEO_MATBTC ~ error', error)
        return []
      }
    },
    async GET_THUEBAO_KN_ID (matb) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/get_thuebao_kn_id', { matb })
        console.log('🚀 ~ file: index.vue ~ line 220 ~ GET_THUEBAO_KN_ID ~ rs', rs)
        if (rs.data && rs.data.length > 0) {
          return parseInt(rs.data[0].thuebao_kn_id)
        } else {
          return -1
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 221 ~ LAY_THUEBAOID_THICONG_THEO_MATB ~ error', error)
        return -1
      }
    },
    async LAY_THUEBAOID_THICONG_THEO_MATB (tinhthicong_id, matb) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyendoi-codinh-sang-ims-bancheo/fn_lay_thuebaoid_thicong_theo_matb', {tinhthicong_id, matb})
        console.log('🚀 ~ file: index.vue ~ line 219 ~ LAY_THUEBAOID_THICONG_THEO_MATB ~ rs', rs)
        if (rs.error_code === 'BSS-00000000') {
          return parseInt(rs.data)
        } else {
          return 0
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 221 ~ LAY_THUEBAOID_THICONG_THEO_MATB ~ error', error)
        return 0
      }
    },
    async tinh_thicong (tinhthicong_id) {
      let tinh = {}
      try {
        // let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/tinh_thicong', {tinhtc})
        let rs = await this.$root.context.post('/web-bancheo/danhmuc/tinh_thicong', {tinhthicong_id})
        console.log('🚀 ~ file: index.vue ~ line 201 ~ tinh_thicong ~ rs', rs)
        if (rs.data !== null && rs.data.length > 0) {
          tinh.user = rs.data[0].ten_truycap
          tinh.pass = rs.data[0].mat_khau
          tinh.nhanvien_id = parseInt(rs.data[0].nhanvien_id)
          tinh.donvi_id = parseInt(rs.data[0].donvi_id)
          tinh.matinh = rs.data[0].matinh
          tinh.TNS = rs.data[0].tns_string
          tinh.tinhthicong_id = parseInt(rs.data[0].tinh_id)
          tinh.mavung = rs.data[0].ma_vung
          tinh.ip_dll = rs.data[0].ip_dll
          return tinh
        } else {
          tinh.user = ''
          tinh.pass = ''
          tinh.nhanvien_id = 0
          tinh.donvi_id = 0
          tinh.matinh = ''
          tinh.TNS = ''
          tinh.tinhthicong_id = 0
          tinh.ip_dll = ''
          return tinh
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 202 ~ tinh_thicong ~ error', error)
        return tinh
      }
    },
    async LAY_TINHTHICONG_THEO_MATB (txtMaTB) {
      let Tinh_TC = {}
      try {
        // let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_tinhthicong_theo_matb?matb=' + txtMaTB)
        let rs = await this.$root.context.get('/web-bancheo/tracuu/lay_tinhthicong_theo_matb?matb=' + txtMaTB)
        console.log('🚀 ~ file: index.vue ~ line 148 ~ LAY_TINHTHICONG_THEO_MATB ~ rs', rs)
        if (rs.data !== null && rs.data.length > 0) {
          Tinh_TC = await this.tinh_thicong(rs.data[0].tinh_thicong)
          return Tinh_TC
        } else {
          Tinh_TC.tinhthicong_id = 0
          return Tinh_TC
        }
      } catch (error) {
        Tinh_TC.tinhthicong_id = 0
        return Tinh_TC
      }
    },
    clearTheSpace (st) {
      let st1 = ''
      st = st.trim()
      while (st.length != 0) {
        st += ' '
        let i = st.indexOf(' ')
        st1 += st[0] + st.substring(1, i)
        st = st.substring(i + 1).trim()
      }
      return st1.trim()
    },
    Catdaunhay (str) {
      str = str.replace('\'', '')
      return str
    },
    async HT_DS_KhuyenMai_Combobox () {
      try {
        let itocdo_id = 0
        let itrangbi_id = 0 // Default: KH tự trang bị
        let imuccuoc_id = 0
        let iloaitb_id = 0
        if (this.parent.modelCbo.vcboKieuLD.length <= 0) { return }
        if (this.parent.modelCbo.vcboDoituongTB.length <= 0) { return }
        if (this.parent.modelCbo.cboDichVuVT !== null && !this.parent.modelCbo.cboDichVuVT.toString().includes('.')) {
          this.parent.dichvuvt_id = parseInt(this.parent.modelCbo.cboDichVuVT)
        }
        switch (this.parent.dichvuvt_id) {
          case Enum.DichVuVienThong.CO_DINH:
            itocdo_id = 0
            break
          case Enum.DichVuVienThong.IMS:
            itocdo_id = 0
            break
          case Enum.DichVuVienThong.ADSL:
            if (this.$refs.refAdsl === undefined || (
              this.$refs.refAdsl.cboGoiCuocAdsl.Value == null ||
            this.$refs.refAdsl.cboGoiCuocAdsl.Value.toString().includes('.'))
            ) {
              return
            }
            itocdo_id = this.$refs.refAdsl.cboGoiCuocAdsl.Value
            break
          case Enum.DichVuVienThong.MEGA_EYES:
            if (this.$refs.refAdsl === undefined || (
              this.$refs.refAdsl.cboGoiCuocAdsl.Value == null ||
            this.$refs.refAdsl.cboGoiCuocAdsl.Value.toString().includes('.'))
            ) {
              return
            }
            itocdo_id = this.$refs.refAdsl.cboGoiCuocAdsl.Value
            break
          case Enum.DichVuVienThong.MEGAWAN:
          case Enum.DichVuVienThong.METRONET:
          case 26:
            try {
              if (
                this.$refs.refWan === undefined || (this.$refs.refWan.cboTocDo.Value == null || this.$refs.refWan.cboTocDo.Value.toString().includes('.'))
              ) {
                return
              }
              itocdo_id = this.$refs.refWan.cboTocDo.Value
            } catch (error) {
              console.log('🚀 ~ file: index.vue ~ line 2210 ~ HT_DS_KhuyenMai_Combobox ~ error', error)
            }

            break
          case Enum.DichVuVienThong.TSL:
          case 25:
            if (this.$refs.refTSL === undefined || (
              this.$refs.refTSL.cboTocDoKenhTSL.Value == null ||
            this.$refs.refTSL.cboTocDoKenhTSL.Value.toString().includes('.'))
            ) {
              return
            }
            itocdo_id = this.$refs.refTSL.cboTocDoKenhTSL.Value
            break
          case Enum.DichVuVienThong.GPHONE:
            itocdo_id = 0
            break
          default:
            itocdo_id = 0
            break
        }
        try {
          if (this.$refs.refAdsl && this.$refs.refAdsl.cboMucCuoc.Value != null && !this.$refs.refAdsl.cboMucCuoc.Value.toString().includes('.')) {
            imuccuoc_id = this.$refs.refAdsl.cboMucCuoc.Value
          }
          if (this.parent.modelCbo.cboLoaihinhTB != null && !this.parent.modelCbo.cboLoaihinhTB.toString().includes('.')) {
            iloaitb_id = this.parent.modelCbo.cboLoaihinhTB
          }
        } catch (error) {
          console.log('🚀 ~ file: index.vue ~ line 2238 ~ HT_DS_KhuyenMai_Combobox ~ error', error)
        }

        this.parent.dsKhuyenMai = await this.LAY_KHUYENMAI_HDTB(
          iloaitb_id,
          this.parent.modelCbo.cboKieuLD,
          this.parent.phuongld_id,
          Enum.KHOANMUC_TT.KMTT_DATMOI,
          this.parent.phold_id,
          this.parent.apld_id,
          this.parent.khuld_id,
          itrangbi_id,
          this.parent.modelCbo.cboDoituongTB,
          itocdo_id,
          imuccuoc_id,
          0
        )
        this.parent.modelCbo.vcboKhuyenMai = this.parent.dsKhuyenMai
        if (this.parent.modelCbo.vcboKhuyenMai.length === 0) {
          this.parent.modelCkeckBox.chkdisableKhuyenMai = true
        } else {
          this.parent.modelCkeckBox.chkdisableKhuyenMai = false
        }
        this.parent.modelCkeckBox.chkKhuyenMai = false
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2167 ~ HT_DS_KhuyenMai_Combobox ~ error', error)
      }
    },
    async LAY_KHUYENMAI_HDTB (
      loaitb_id,
      kieuld_id,
      phuongld_id,
      khoanmuctt_id,
      phold_id,
      apld_id,
      khuld_id,
      trangbi_id,
      doituong_id,
      tocdo_id,
      muccuoc_id,
      loai_km
    ) {
      try {
        let data = {
          loaitb_id,
          kieuld_id,
          phuongld_id,
          khoanmuctt_id,
          phold_id,
          apld_id,
          khuld_id,
          trangbi_id,
          doituong_id,
          tocdo_id,
          muccuoc_id,
          loai_km
        }
        let res = await this.$root.context.post('/web-hopdong/chuyendich/lay_ds_khuyenmai_hdtb', data)
        return res.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2363 ~ error', error)
        return []
      }
    },
    async MAP_ID (P_MAP, P_VALUE) {
      try {
        let data = {
          loai: P_MAP,
          value: P_VALUE
        }
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getdata', data)
        if (rs.data.length > 0) {
          var obj = rs.data[0]
          if (P_MAP === 'PHANLOAIKH_ID_HD_KHACHHANG') {
            return obj.phanloaikh_id
          } else {
            return obj[P_MAP.toLowerCase()]
          }
        } else return null
      } catch (error) {
        return null
      }
    },
    async cboTrangBiCD_SelectedValueChanged (data) {
      try {
        let trangbi_id = data.trangbi_id
        if (Number(this.parent.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.CO_DINH) {
          this.parent.trangbi_id = Number(trangbi_id)
          await this.LayTienTheoKhoanMuc()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2957 ~ cboTrangBiCD_SelectedValueChanged ~ error', error)
      }
    },
    async cboTrangBiGP_SelectedValueChanged (data) {
      try {
        let trangbi_id = data.trangbi_id
        if (Number(this.parent.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.GPHONE) {
          this.parent.trangbi_id = Number(trangbi_id)
          await this.LayTienTheoKhoanMuc()
        }
      } catch (error) {
      }
    },
    async cboTrangBiADSL_SelectedValueChanged (data) {
      try {
        let trangbi_id = data.trangbi_id
        if (Number(this.parent.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.ADSL) {
          this.parent.trangbi_id = Number(trangbi_id)
          await this.LayTienTheoKhoanMuc()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2957 ~ cboTrangBiADSL_SelectedValueChanged ~ error', error)
      }
    },
    async  cboTrangBiWan_SelectedValueChanged (data) {
      try {
        let trangbi_id = data.trangbi_id
        if (Number(this.parent.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.MEGAWAN ||
                    Number(this.parent.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.METRONET ||
                    Number(this.parent.modelCbo.cboDichVuVT) == 26
                    ) {
          this.parent.trangbi_id = Number(trangbi_id)
          await this.LayTienTheoKhoanMuc()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2957 ~ cboTrangBiWan_SelectedValueChanged ~ error', error)
      }
    },
    cboLoaiTBiWan_SelectedValueChanged (data) {
      try {
        let loaitbi_id = data.loaitbi_id
        this.parent.ltb_adsl_id = loaitbi_id
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2964 ~ cboLoaiTBiWan_SelectedValueChanged ~ error', error)
      }
    },
    async cboTrangBi_SelectedValueChanged (data) {
      console.log('🚀 ~ file: index.vue ~ line 2954 ~ cboTrangBi_SelectedValueChanged trangbi_id~ data', data)
      try {
        let trangbi_id = data.trangbi_id
        if (Number(this.parent.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.TSL || Number(this.parent.modelCbo.cboDichVuVT) == 25) {
          this.parent.trangbi_id = Number(trangbi_id)
          await this.LayTienTheoKhoanMuc()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2957 ~ cboTrangBi_SelectedValueChanged ~ error', error)
      }
    },
    async cboTocDoKenhTSL_SelectedValueChanged (data) {
      console.log('🚀 ~ file: index.vue ~ line 2962 ~ cboTocDoKenhTSL_SelectedValueChanged tocdo_id~ data', data)
      try {
        let tocdo_id = data.tocdo_id
        if (Number(this.parent.modelCbo.cboDichVuVT) == Enum.DichVuVienThong.TSL || Number(this.parent.modelCbo.cboDichVuVT) == 25) {
          this.parent.tocdo_id = Number(tocdo_id)
          await this.HT_COMBO_DONVI()
          await this.LayTienTheoKhoanMuc()
          if (this.parent.kt_load == false) {
            await this.HienThi_CTK_CTC()
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2964 ~ cboTocDoKenhTSL_SelectedValueChanged ~ error', error)
      }
    },
    async GetDataSql (P_MAP, P_VALUE) {
      try {
        let data = {
          loai: P_MAP,
          value: P_VALUE
        }
        console.log('🚀 ~ file: index.vue ~ line 2312 ~ GetDataSql ~ data', data)
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/getdata', data)
        if (rs.data !== null && rs.data.length > 0) {
          return rs.data
        } else return []
      } catch (error) {
        return []
      }
    },
    async HienThi_CTK_CTC () {
      try {

      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2354 ~ HienThi_CTK_CTC ~ error', error)
      }
    },
    async HT_COMBO_DONVI () {
      try {
        this.parent.tocdotn_id = 0
        this.parent.muccuoctn_id = 0
        if (this.parent.dichvuvt_id === Enum.DichVuVienThong.ADSL) {
          await this.$refs.refAdsl.HT_COMBO_DONVI()
        }
        if (this.parent.dichvuvt_id === Enum.DichVuVienThong.MEGA_EYES) {
          await this.$refs.refAdsl.HT_COMBO_DONVI()
        }
        if (this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || this.parent.dichvuvt_id === 25) {
          await this.$refs.refTSL.HT_COMBO_DONVI()
        }
        if (this.parent.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
            this.parent.dichvuvt_id === 26 ||
            this.parent.dichvuvt_id === Enum.DichVuVienThong.METRONET) {
          await this.$refs.refWan.HT_COMBO_DONVI()
        }
        if (this.parent.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) {
          await this.$refs.refCD.HT_COMBO_DONVI()
        }
        if (this.parent.dichvuvt_id === Enum.DichVuVienThong.IMS) {
          await this.$refs.refIMS.HT_COMBO_DONVI()
        }
        if (this.parent.dichvuvt_id === Enum.DichVuVienThong.GPHONE) {
          await this.$refs.refGP.HT_COMBO_DONVI()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2317 ~ HT_COMBO_DONVI ~ error', error)
      }
    },
    async HT_Kieu_LD_BANCHEO_Combobox (vloaitb_id, vloaihd_id, vkieu) {
      console.log('🚀 ~ file: index.vue ~ line 3165 ~ HT_Kieu_LD_BANCHEO_Combobox ~ vloaitb_id, vloaihd_id, vkieu', vloaitb_id, vloaihd_id, vkieu)
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_kieu_ld_bancheo_combobox', {vloaitb_id, vloaihd_id, vkieu})
        console.log('🚀 ~ file: index.vue ~ line 820 ~ HT_Kieu_LD_BANCHEO_Combobox ~ rs', rs)
        if (rs.data.length > 0) {
          this.parent.modelCbo.vcboKieuLD = rs.data
          this.parent.modelCbo.cboKieuLD = this.parent.modelCbo.vcboKieuLD[0].kieuld_id
          await this.cboKieuLD_SelectedValueChanged()
        } else {
          this.parent.modelCbo.vcboKieuLD = []
          this.parent.modelCbo.cboKieuLD = -1
          await this.cboKieuLD_SelectedValueChanged()
        }
        return true
      } catch (error) {
        return false
      }
    },
    async cboDichVuVT_SelectedValueChanged () {
      try {
        if (this.parent.modelCbo.vcboDichVuVT.length > 0) {
          this.parent.dichvuvt_id = parseInt(this.parent.modelCbo.cboDichVuVT)
          if (this.parent.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
            this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || 
            this.parent.dichvuvt_id === 25 || this.parent.dichvuvt_id === 26 || 
            this.parent.dichvuvt_id === Enum.DichVuVienThong.METRONET) {
            this.parent.modelCkeckBox.chkCongVan = true
          } else {
            this.parent.modelCkeckBox.chkCongVan = false
            this.parent.modelInput.txtCongVan = ''
          }
          if (this.parent.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) { this.parent.buttonState.btnChonSM = true } else { this.parent.buttonState.btnChonSM = false }
          await this.GiaoDien_DichVuVT(this.parent.dichvuvt_id)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 304 ~ cboDichVuVT_SelectedValueChanged ~ error', error)
      }
    },
    async GiaoDien_DichVuVT (dichvuvt_id) {
      console.log('🚀 ~ file: index.vue ~ line 3199 ~ GiaoDien_DichVuVT ~ dichvuvt_id', dichvuvt_id)
      this.parent.s_display_div = -1
      this.parent.buttonVisible.btnDiemDau = false
      this.parent.buttonVisible.btnDiemCuoi = false
      this.parent.buttonVisible.label76 = false
      await this.HT_LoaiHinh_TB_Combobox(dichvuvt_id)
      await this.LAY_DS_SUDUNG_DVGT(0)
      if (this.parent.dichvuvt_id === Enum.DichVuVienThong.CO_DINH) {
        this.parent.s_display_div = 1
        this.parent.modelCbo.cboLoaihinhTB = Enum.LoaiHinhTB.DIENTHOAI_CD
        this.parent.modelCbo.cboKieuLD = Enum.KieuLapDat.DC_DIENTHOAI// Hieu-4.10.2010: Defaul dịch chuyển cố định
        this.parent.ltb_adsl_id = 1
      }

      if (this.parent.dichvuvt_id === Enum.DichVuVienThong.ADSL) {
        this.parent.s_display_div = 2
        this.parent.modelCbo.cboLoaihinhTB = Enum.LoaiHinhTB.MEGA
        this.parent.ltb_adsl_id = parseInt(this.parent.modelCbo.cboLoaiTB)
        this.parent.modelCbo.cboKieuLD = Enum.KieuLapDat.DC_ADSL_KHONG_SO// Hieu-4.10.2010: Defaul dịch chuyển internet không số
      }

      if (this.parent.dichvuvt_id === Enum.DichVuVienThong.MEGA_EYES) {
        this.parent.s_display_div = 2
        this.parent.ltb_adsl_id = parseInt(this.parent.modelCbo.cboLoaiTB)
      }

      if (this.parent.dichvuvt_id === Enum.DichVuVienThong.MEGAWAN || 
          this.parent.dichvuvt_id === Enum.DichVuVienThong.METRONET ||
          this.parent.dichvuvt_id === 26
          ) {
        this.parent.s_display_div = 3
        if (this.$refs.refWan) { this.parent.ltb_adsl_id = parseInt(this.$refs.refWan.cboLoaiTBiWan.Value) }
      }

      if (this.parent.dichvuvt_id === Enum.DichVuVienThong.TSL || this.parent.dichvuvt_id === 25) {
        this.parent.s_display_div = 5
        this.parent.ltb_adsl_id = 1
        this.parent.buttonVisible.btnDiemDau = true
        this.parent.buttonVisible.btnDiemCuoi = true
        this.parent.buttonVisible.label76 = true
      }

      if (this.parent.dichvuvt_id === Enum.DichVuVienThong.GPHONE) {
        this.parent.s_display_div = 4
        this.parent.ltb_adsl_id = 1
      }
      if (this.parent.dichvuvt_id === Enum.DichVuVienThong.IMS) {
        this.parent.s_display_div = 6
        this.parent.ltb_adsl_id = 1
      }
    },
    async HT_LoaiHinh_TB_Combobox (dichvuvt_id) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/chuyen-dich-ban-cheo/ht_loaihinh_tb_combobox', {dichvuvt_id})
        console.log('🚀 ~ file: index.vue ~ line 358 ~ HT_LoaiHinh_TB_Combobox ~ rs', rs)
        if (rs.data.length > 0) {
          this.parent.modelCbo.vcboLoaihinhTB = rs.data
          this.parent.modelCbo.cboLoaihinhTB = this.parent.modelCbo.vcboLoaihinhTB[0].loaitb_id
          this.cboLoaihinhTB_SelectedValueChanged()
          return true
        }
      } catch (error) {
        return false
      }
    },
    async LayTongTien_HDTB () {
      try {
        var dtongtien = 0
        var dtongvat = 0
        for (let i = 0; i < this.parent.ds_tien_khoanmuc.length; i++) {
          if (this.parent.ds_tien_khoanmuc[i].khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_KM_LAPDAT) {
            dtongtien += Math.abs(parseFloat(this.parent.ds_tien_khoanmuc[i].tien)) * -1
            dtongvat += Math.abs(parseFloat(this.parent.ds_tien_khoanmuc[i].vat)) * -1
          } else {
            dtongtien += parseFloat(this.parent.ds_tien_khoanmuc[i].tien)
            dtongvat += parseFloat(this.parent.ds_tien_khoanmuc[i].vat)
          }
        }
        this.parent.modelInput.txtTongTien = currency(dtongtien)
        this.parent.modelInput.txtTongVat = currency(dtongvat)
        this.parent.lblTongTien = currency(dtongtien + dtongvat)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 386 ~ LayTongTien_HDTB ~ error', error)
      }
    },
    async HienThiTienLapDat (ds) {
      if (ds.length === 0) { return }
      var kt_lm = true
      if (ds[0].id.toString() !== '-1') {
        for (var i = 0; i < ds.length; i++) {
          if (ds[i].khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_CHUYENDICH) {
            this.parent.dtienld = parseFloat(ds[i].tien)
            this.parent.dvatld = parseFloat(ds[i].vat)
            kt_lm = false
          }
        }
        if (kt_lm === true) {
          this.parent.dtienld = 0
          this.parent.dvatld = 0
        }
      } else {
        this.parent.dtienld = 0
        this.parent.dvatld = 0
      }
      for (let j = 0; j < this.parent.ds_tien_khoanmuc.length; j++) {
        if (this.parent.ds_tien_khoanmuc[j].khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_CHUYENDICH) {
          this.parent.ds_tien_khoanmuc[j].tien = this.parent.dtienld
          this.parent.ds_tien_khoanmuc[j].vat = this.parent.dvatld
        }
      }
    },
    async LAY_TIEN_KHOANMUC_LD (loaihd_id, ngay_yc, dichvuvt_id, loaitb_id, kieuld_id, phuongld_id, tocdo_id, muccuoc_id, ltb_adsl_id, ltb_adsl_moi_id, ltb_adsl_cu_id, tocdo_moi_id, tocdo_cu_id, kh_cd, phold_id, apld_id, khuld_id, doituong_id, trangbi_id) {
      try {
        let data = {
          loaihd_id,
          ngay_yc,
          dichvuvt_id,
          loaitb_id,
          kieuld_id,
          phuongld_id,
          tocdo_id,
          muccuoc_id,
          ltb_adsl_id,
          ltb_adsl_moi_id,
          ltb_adsl_cu_id,
          tocdo_moi_id,
          tocdo_cu_id,
          kh_cd,
          phold_id,
          apld_id,
          khuld_id,
          doituong_id,
          trangbi_id
        }
        console.log('🚀 ~ file: index.vue ~ line 444 ~ LAY_TIEN_KHOANMUC_LD ~ data', data)
        let response = await this.$root.context.post(`/web-hopdong/chuyendich/lay_tien_khoanmuc_ld_v2`, data)
        console.log('🚀 ~ file: index.vue ~ line 444 ~ LAY_TIEN_KHOANMUC_LD ~ response', response)
        return response.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_SUDUNG_DVGT (thuebao_id) {
      this.parent.m_dsThueBao_DichVu.dsdvgt_ban = await this.TC_LAY_DS_THUEBAO_DICHVU(0, thuebao_id, this.parent.loaitb_id, 1)
      console.log('🚀 ~ file: index.vue ~ line 1747 ~ LAY_DS_SUDUNG_DVGT ~ this.parent.m_dsThueBao_DichVu', this.parent.m_dsThueBao_DichVu.dsdvgt_ban)
      await this.HT_DichVuGT()
    },
    async HT_DichVuGT () {
      try {
        this.parent.modelInput.lvDvgt = ''
        var str
        var kt = true
        this.parent.tien_dv = 0
        this.parent.vat_dv = 0
        for (let index = 0; index < this.parent.m_dsThueBao_DichVu.dsdvgt_ban.length; index++) {
          const element = this.parent.m_dsThueBao_DichVu.dsdvgt_ban[index]
          if (element.huy.toString() === '1' || element.dangKy.toString() === '1' || element.sua.toString() === '1') {
            str = element.tenDvGt
            if (element.dangKy.toString() === '1' && element.huy.toString() === '0' && element.sua.toString() === '1') {
              kt = false
              if (element.cuocLd !== null) { this.parent.tien_dv = this.parent.tien_dv + parseInt(element.cuocLd) }
              if (element.vatLd !== null) { this.parent.vat_dv = this.parent.vat_dv + parseInt(element.vatLd) }
              str = str + ' (ĐK)'
            }
            if (element.huy.toString() === '1') { str = str + ' (Hủy)' }
            if (element.sua.toString() === '1') { str = str + ' (Sửa)' }
            this.parent.modelInput.lvDvgt = this.parent.modelInput.lvDvgt + str + '\n'
          }
        }
        if (kt === true) {
          this.parent.tien_dv = 0
          this.parent.vat_dv = 0
        }
        await this.HienThiTienDichVu(this.parent.tien_dv, this.parent.vat_dv)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 475 ~ HT_DichVuGT ~ error', error)
      }
    },
    async HienThiTienDichVu () {
      try {
        this.parent.dtien_dv = this.parent.tien_dv
        this.parent.dvat_dv = this.parent.vat_dv
        for (let index = 0; index < this.parent.ds_tien_khoanmuc.length; index++) {
          const element = this.parent.ds_tien_khoanmuc[index]
          if (element.khoanmuctt_id === Enum.KHOANMUC_TT.KMTT_DICHVU) {
            element.tien = this.parent.dtien_dv
            element.vat = this.parent.dvat_dv
          }
        }
        await this.LayTongTien_HDTB()
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 502 ~ HienThiTienDichVu ~ error', error)
      }
    },
    async TC_LAY_DS_THUEBAO_DICHVU (hdtbId, thueBaoId, loaiTbId, kieuId) {
      try {
        let data = {
          hdtbId: hdtbId,
          thueBaoId: thueBaoId,
          loaiTbId: loaiTbId,
          kieuId: kieuId
        }
        let res = await this.$root.context.post('web-hopdong/hopdong/lay_ds_thuebao_dichvu', data)
        console.log('🚀 ~ file: index.vue ~ line 1805 ~ TC_LAY_DS_THUEBAO_DICHVU ~ res', res)
        if (res.data) {
          return res.data
        } else {
          return []
        }
      } catch (error) {
        return []
      }
    }
  },
  watch: {}
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
