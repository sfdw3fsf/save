<template src="./index.html"></template>
<style src="./style.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import { DichVuVienThong, LOAI_DV, LoaiHinhTB, KieuLapDat } from '../../../const/enums'
import PopupGiaoViec from './popups/PopupGiaoViec'
import PopupThongTinGt from './popups/PopupThongTinGt'
import PopupGhepMa from './popups/PopupGhepMa'
import moment from 'moment'

const MY_VNPT_UNG_DUNG_ID = 1
const PHIEU_MOI = 1
const PHIEU_TRA_LAI = 2
const CHUYEN_TC = 3

export default {
  name: 'UpdateOrdersMyVNPTFreedoo',
  components: { breadcrumb, PopupThongTinGt, PopupGiaoViec, PopupGhepMa },
  data () {
    return {
      header: {
        title: 'Cập nhật thông tin phát triển dịch vụ',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      actions: [
        { id: 'tsbtnXem', icon: 'nc-icon-glyph nc-icon-glyph design_bullet-list-67', name: 'Danh sách', visible: true },
        { id: 'tsbtnCapNhat', icon: 'one-reload1', name: 'Cập nhật', visible: true },
        { id: 'tsbtnGiaoViec', icon: 'one-file-edit2', name: 'Giao việc', visible: true },
        { id: 'btnGhepMa', icon: 'one-folder-code', name: 'Ghép mã', visible: true },
        { id: 'tsbtnThongTinGT', icon: '-ap icon-info', name: 'Thông tin GT', visible: true }
      ],
      lapHopDongMenus: [
        { name: 'Lắp đặt mới', frame: 'WinUI.WinUILapHopDong.frmDatMoi,PTTB.exe', href: 'InstallNewSubs', tag: '' },
        { name: 'Thay đổi thông tin khách hàng', frame: 'WinUI.WinUILapHopDong.frmThayDoiTTKhac,PTTB.exe', href: 'ThayDoiThongTinKhuyenMai', tag: '' },
        { name: 'Chuyển dịch', frame: 'WinUI.WinUILapHopDong.frmChuyenDich,PTTB.exe,2', href: 'DichChuyen', tag: '2' },
        { name: 'Tạm dừng', frame: 'WinUI.WinUILapHopDong.frmChamDutSD,PTTB.exe', href: 'liquidate', tag: '' },
        { name: 'Khôi phục', frame: 'WinUI.WinUILapHopDong.frmKhoiPhuc_ThanhLy,PTTB.exe', href: 'RestoreLiquidationSubs', tag: '' },
        {
          name: 'Thay đổi tốc độ',
          frame: '',
          tag: '',
          children: [
            { name: 'Thay đổi tốc độ Internet', frame: 'WinUI.WinUILapHopDong.frmThayDoiTocDoADSL,PTTB.exe,0', href: 'ChangeInternetSpeed', tag: '0' },
            { name: 'Thay đổi tốc độ MegaWan', frame: 'WinUI.WinUILapHopDong.frmThayDoiThongSoWan,PTTB.exe', href: 'ChangeMegaWanConfig', tag: '' },
            { name: 'Thay đổi thông số MyTV', frame: 'WinUI.WinUILapHopDong.frmThayDoiThongSoMyTV,PTTB.exe', href: 'changeMyTVParameter', tag: '' },
            { name: 'Thay đổi tốc đố TSL', frame: 'WinUI.WinUILapHopDong.frmThayDoiTDTSL,PTTB.exe', href: 'SetupChangeTSLSpeed', tag: '' }
          ]
        },
        {
          name: 'Chuyển đổi loại hình',
          frame: '',
          tag: '',
          children: [
            { name: 'Chuyển đổi CĐ <--> IMS', frame: 'WinUI.WinUILapHopDong.frmChuyenDoiCDIMS,PTTB.exe,2', href: 'ChangeLandlineIMSType', tag: '2' },
            { name: 'Chuyển đổi loại hình khác', frame: 'WinUI.WinUILapHopDong.frmThayDoiLHTB,PTTB.exe,0+2', href: 'ChangeSubsType', tag: '0+2' }
          ]
        },
        { name: 'Thay đổi DV GTGT', frame: 'WinUI.WinUILapHopDong.frmThayDoiDV,PTTB.exe', href: 'changeService', tag: '' },
        { name: 'Ghép gói Đa DV', frame: 'WinUI.WinUILapHopDong.frmThayDoiGoiDaDV,PTTB.exe', href: 'ChangeMultiServicePackage', tag: '' },
        { name: 'Tách ghép mã', frame: 'WinUI.WinUILapHopDong.frmTachNhapTB,PTTB.exe', href: 'SeparationSubscriber', tag: '' },
        { name: 'Khóa mở PayTV', frame: 'WinUI.WinUILapHopDong.frmThayDoiTTKhac,PTTB.exe', href: 'ThayDoiThongTinKhuyenMai', tag: '' },
        {
          name: 'Đăng ký nhóm - gói',
          frame: '',
          tag: '',
          children: [
            { name: 'Đăng ký gói đa dịch vụ', frame: 'WinUI.WinUILapHopDong.frmDangKyGoiDaDV,PTTB.exe', href: 'RegMultiServicePackage', tag: '' },
            { name: 'Thay đổi gói đa dịch vụ', frame: 'WinUI.WinUILapHopDong.frmThayDoiGoiDaDV,PTTB.exe', href: 'ChangeMultiServicePackage', tag: '' }
          ]
        }
      ],
      isShowLapHopDong: false,
      ungDungOptions: [],
      ketQuaOptions: [],
      donViTHOptions: [],
      donViNhanOptions: [],
      lyDoTTOptions: [],
      lyDoTraOptions: [],
      B2A_MENUTAGS: [],
      B2A_MENUTAGS_BOSUNG: [],
      tuVanFilter: {
        ungDungId: null,
        maGiaoDich: '',
        ngayYC: '',
        tenKH: '',
        soDienThoai: '',
        diaChiKH: '',
        ghiChu: '',
        ketQuaId: null,
        donViTHId: null,
        noiDung: '',
        lyDoTT: null,
        donViNhanId: null,
        lyDoTra: null
      },
      dataGrid: {
        danhSachGiaoViec: {
          headers: [
            { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true },
            { fieldName: 'sodienthoai', headerText: 'Số điện thoại', allowFiltering: true },
            { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true },
            { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true },
            { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true }
          ],
          dataSources: []
        },
        danhSachTuVan: {
          headers: [
            { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, visible: true },
            { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, visible: true }, // thieu
            { fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true, visible: true },
            { fieldName: 'ten_kh', headerText: 'Tên KH', allowFiltering: true, visible: true },
            { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowFiltering: true, visible: true },
            { fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true, visible: true },
            { fieldName: 'ma_gd_tc', headerText: 'Mã GD (Thi công)', allowFiltering: true, visible: true }, // thieu
            { fieldName: 'ten_kieuld', headerText: 'Kiểu YC', allowFiltering: true, visible: true },
            { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true, visible: true },
            { fieldName: 'chitieu_tg', headerText: 'Chỉ tiêu TG(giờ)', allowFiltering: true, visible: true },
            { fieldName: 'thoigian_ck', headerText: 'Thời gian CK', allowFiltering: true, visible: true },
            { fieldName: 'gio_cl', headerText: 'Giờ còn lại', allowFiltering: true, visible: true },
            { fieldName: 'nhanvien_th', headerText: 'NV địa bàn', allowFiltering: true, visible: true },
            { fieldName: 'giaoviec', headerText: 'Giao việc', allowFiltering: true, visible: true },
            // Grid Phieu tra lai
            { fieldName: 'lydotra', headerText: 'Lý do trả', allowFiltering: true, visible: false },
            { fieldName: 'nd_tra', headerText: 'Nội dung trả', allowFiltering: true, visible: false },
            // End grid Phieu tra lai, grid phieu TC
            { fieldName: 'phieucskh_id', headerText: 'ID', allowFiltering: true, visible: false },
            { fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true, visible: false },
            { fieldName: 'nhom_lnc', headerText: 'Nhóm NC', allowFiltering: true, visible: false },
            { fieldName: 'phanloai', headerText: 'Phân loại', allowFiltering: true, visible: false },
            { fieldName: 'ungdung', headerText: 'Ứng dụng', allowFiltering: true, visible: false }
          ],
          dataSources: [],
          selectedRow: {}
        }
      },
      checkActions: [
        { value: PHIEU_MOI, name: 'Phiếu mới', isShow: true },
        { value: PHIEU_TRA_LAI, name: 'Phiếu trả lại', isShow: true },
        { value: CHUYEN_TC, name: 'Chuyển TC', isShow: true }
      ],
      hidden: {
        isHdnTabOne: false,
        isHdnTabTwo: true,
        isHdnTabThree: true
      },
      show: {
        isShowKetQua: true,
        isShowThongTinGiaoViec: true,
        isShowDonViTH: false,
        isShowNoiDung: false,
        isShowLyDoTT: false,
        isShowDonViNhan: false,
        isShowLyDoTra: false
      },
      tag: '',
      checkValue: PHIEU_MOI,
      position: { X: 'center', Y: 'top' },
      phieuId: 0,
      hdkhId: 0,
      kieuNhanVienMacDinh: 0,
      ngayLabel: 'Ngày YC'
    }
  },
  computed: {
    phanVungId () {
      return +this.$root.token.getPhanVungID()
    },
    donViId () {
      return +this.$root.token.getDonViID()
    },
    nhanVienId () {
      return +this.$root.token.getNhanVienID()
    },
    ngayGiao () {
      return moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
    }
  },
  async created () {
    await this.frmCapNhatTT_PTDV_Load()
    $('#select2-ketQuaCbo-container').addClass('text-primary')
  },
  mounted () {
    $(document).on('click', '.dropdown-menu', function (e) {
      e.stopPropagation()
    })
    if ($(window).width() < 992) {
      $('.dropdown-menu a').click(function (e) {
        e.preventDefault()
        if ($(this).next('.submenu').length) {
          $(this).next('.submenu').toggle()
        }
        $('.dropdown').on('hide.bs.dropdown', function () {
          $(this).find('.submenu').hide()
        })
      })
    }
  },
  methods: {
    async frmCapNhatTT_PTDV_Load () {
      this.getTag()
      this.loading(true)
      try {
        const [ungDungResponse, ketQuaResponse] = await Promise.all([
          this.getDanhSachUngDung(),
          this.getDanhSachKetQua()
        ])
        this.ungDungOptions = ungDungResponse.sort((a, b) => a.ungdung_id - b.ungdung_id)
        if (ungDungResponse.some(ungDung => ungDung.ungdung_id === MY_VNPT_UNG_DUNG_ID)) {
          this.tuVanFilter.ungDungId = MY_VNPT_UNG_DUNG_ID
        }
        this.ketQuaOptions = ketQuaResponse
        this.ketQuaOptions.find(ketQua => ketQua.ketqua_id === 0).ten_ketqua = 'Chưa chọn'
        this.onSetButtonVisible('tsbtnGiaoViec', this.tag === '0')
        await this.onChangeTrangThai(PHIEU_MOI)
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    getTag () {
      try {
        const tag = this.$route.query.tag
        if (tag && tag.length > 0) {
          const tags = tag.split('+')
          if (tags.length > 0) this.tag = tags[0]
        } else {
          this.tag = '0'
        }
      } catch (e) {
        this.$root.$toast.error('Tham số truyền vào không hợp lệ')
      }
    },
    async getDanhSachUngDung () {
      try {
        const response = await api.getDanhSachUngDung(this.axios, { ungdung_id: null })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Lấy danh sách ứng dụng không thành công')
        return []
      }
    },
    async getDanhSachKetQua () {
      try {
        const response = await api.getDanhSachKetQua(this.axios, { tag: this.tag })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Lấy danh sách kết quả không thành công')
        return []
      }
    },
    async getDanhSachTuVan () {
      this.onResetFilter()
      try {
        const response = await api.getDanhSachTuVan(
          this.axios,
          {
            'ttph_id': this.checkValue === PHIEU_MOI || this.checkValue === CHUYEN_TC ? 1 : 3,
            'tthd_id': this.checkValue === CHUYEN_TC ? 5 : 3,
            'ttphdv_id': 1,
            'kieu': this.tuVanFilter.ungDungId,
            'kieuxem': +this.tag
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          return data.data.map(tuVan => {
            tuVan.ngay_yc = moment(tuVan.ngay_yc).format('DD/MM/YYYY HH:mm')
            return tuVan
          })
        } else {
          return []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách tư vấn')
        this.onResetFilter()
      }
    },
    async setDanhSachTuVan () {
      const dataSources = await this.getDanhSachTuVan()
      this.dataGrid.danhSachTuVan.dataSources = dataSources
      if (dataSources.length === 0) {
        this.onResetFilter()
        this.dataGrid.danhSachTuVan.selectedRow = {}
        this.dataGrid.danhSachGiaoViec.dataSources = []
      }
    },
    async getDanhSachDonViTH () {
      try {
        const response = await api.getDanhSachDonViTH(this.axios, { vkieu: 4 })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách đơn vị TH')
        return []
      }
    },
    async getDanhSachDonViNhan () {
      try {
        const response = await api.getDanhSachDonViNhan(this.axios, { loaidv: LOAI_DV.DONVIQL_TT })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách đơn vị nhận')
        return []
      }
    },
    async getDanhSachLyDoTT () {
      try {
        const response = await api.getDanhSachLyDoTT(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách lý do TT')
        return []
      }
    },
    onResetFilter () {
      this.tuVanFilter.maGiaoDich = ''
      this.tuVanFilter.tenKH = ''
      this.tuVanFilter.soDienThoai = ''
      this.tuVanFilter.diaChiKH = ''
      this.tuVanFilter.ngayYC = ''
      this.tuVanFilter.ghiChu = ''
    },
    async onSelectedRowChanged (data) {
      if (data) {
        this.dataGrid.danhSachTuVan.selectedRow = { ...data }
        this.phieuId = data.phieu_id
        this.hdkhId = data.hdkh_id
        this.tuVanFilter.maGiaoDich = data.ma_gd
        this.tuVanFilter.tenKH = data.ten_kh
        this.tuVanFilter.soDienThoai = data.so_dt
        this.tuVanFilter.diaChiKH = data.diachi_kh
        this.tuVanFilter.ghiChu = data.ghichu
        this.tuVanFilter.ngayYC = data.ngay_yc
        this.tuVanFilter.ketQuaId = 0
        this.loading(true)
        try {
          if (this.tuVanFilter.ungDungId !== 5) {
            this.dataGrid.danhSachGiaoViec.dataSources = await this.getThongTinGiaoViec(data.phieu_id)
          }
          this.lyDoTraOptions = await this.getDanhSachLyDoTra(data.huonggiao_id)
        } catch (e) {
        } finally {
          this.loading(false)
        }
      } else {
        this.dataGrid.danhSachGiaoViec.dataSources = []
        this.onResetFilter()
      }
    },
    async getThongTinGiaoViec (phieuId) {
      try {
        const response = await api.getThongTinGiaoViec(this.axios, { phieu_id: phieuId, kieu_id: 2 })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách giao việc')
        return []
      }
    },
    async getDanhSachLyDoTra (huongGiaoId) {
      try {
        const response = await api.getDanhSachLyDoTra(this.axios, { huonggiao_id: huongGiaoId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách lý do trả')
        return []
      }
    },
    onSetButtonVisible (id, isVisible) {
      this.actions.find(item => item.id === id).visible = isVisible
    },
    onSetColumnVisible (fieldName, isVisible) {
      this.dataGrid.danhSachTuVan.headers.find(header => header.fieldName === fieldName).visible = isVisible
    },
    onSetColumnName (fieldName, name) {
      this.dataGrid.danhSachTuVan.headers.find(header => header.fieldName === fieldName).headerText = name
    },
    async onClickAction (id) {
      switch (id) {
        case 'tsbtnXem':
          await this.tsbtnXem_Click()
          break
        case 'tsbtnCapNhat':
          await this.onCapNhat()
          break
        case 'tsbtnGiaoViec':
          await this.showPopupGiaoViec()
          break
        case 'btnGhepMa':
          this.showPopupGhepMa()
          break
        default:
          await this.showPopupThongTinGT()
          break
      }
    },
    async tsbtnXem_Click () {
      this.loading(true)
      try {
        await this.setDanhSachTuVan()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async onCapNhat () {
      let isThoaiTra = false
      if (this.dataGrid.danhSachTuVan.dataSources.length <= 0) {
        this.$root.$toast.warning('Không có dữ liệu')
        return false
      }
      if (this.tuVanFilter.ketQuaId === 0) {
        this.$root.$toast.warning('Vui lòng chọn kết quả thực hiện!')
        this.$refs.ketQuaId.focus()
        return false
      }
      let message = ''
      const selectedRow = this.dataGrid.danhSachTuVan.selectedRow
      // const isWarning = await this.canhBaoCapNhat(selectedRow.hdkh_id, 1)
      // if (isWarning) this.$root.$toast.warning('Hợp đồng chưa được cập nhật thông tin giới thiệu !')
      if (this.tuVanFilter.ketQuaId === 1) {
        if (!this.tuVanFilter.donViTHId) {
          this.$root.$toast.warning('Bạn chưa chọn quầy giao dịch')
          this.$refs.donViTHId.focus()
          return false
        }
        message = 'Cập nhật dữ liệu thành công!'
      } else if (this.tuVanFilter.ketQuaId === 3) {
        if (!this.tuVanFilter.donViNhanId) {
          this.$root.$toast.warning('Bạn chưa chọn đơn vị chuyển')
          return false
        }
        if (selectedRow.donvi_nhan_id === this.tuVanFilter.donViNhanId) {
          this.$root.$toast.warning('Đơn vị chuyển trùng với đơn vị hiện tại của đơn hàng')
          return false
        }
        message = 'Chuyển phiếu thành công!'
      } else if (this.tag === '0') {
        if (!this.tuVanFilter.lyDoTT) {
          this.$root.$toast.warning('Bạn chưa nhập nguyên nhân thoái trả!')
          this.$refs.lyDoTT.focus()
          return false
        }
        isThoaiTra = true
        this.$confirm(
          'Bạn có chắc chắn muốn thoái trả không?',
          'Thoái trả',
          { confirmButtonText: 'Có', cancelButtonText: 'Không' }
        ).then(async () => {
          this.loading(true)
          try {
            const isSuccess = await this.capNhat({
              phieuId: selectedRow.phieu_id,
              hdkhId: selectedRow.hdkh_id,
              ketQuaId: this.tuVanFilter.ketQuaId,
              donViTHId: this.tuVanFilter.donViTHId,
              nDGiao: this.tuVanFilter.noiDung,
              maGd: selectedRow.ma_gd,
              ungDungId: this.tuVanFilter.ungDungId,
              donViId: this.donViId,
              hdtbId: selectedRow.hdtb_id,
              donViNhanId: this.tuVanFilter.donViNhanId,
              nhanVienTHId: 0,
              lyDoTT: this.tuVanFilter.lyDoTT,
              lyDoTra: this.tuVanFilter.lyDoTra,
              tag: +this.tag,
              message: 'Thoái trả thành công!'
            })
            if (isSuccess) await this.setDanhSachTuVan()
          } catch (e) {
            return false
          } finally {
            this.loading(false)
          }
          return true
        }).catch((e) => {})
      } else {
        if (!this.tuVanFilter.lyDoTra) {
          this.$root.$toast.warning('Bạn chưa chọn lý do trả!')
          this.$refs.lyDoTra.focus()
          return false
        }
        if (!this.tuVanFilter.noiDung) {
          this.$root.$toast.warning('Hãy nhập nội dung để trả phiếu')
          this.$refs.noiDungRef.focus()
          return false
        }
        message = 'Trả phiếu thành công!'
      }
      if (!isThoaiTra) {
        this.loading(true)
        try {
          const isSuccess = await this.capNhat({
            phieuId: selectedRow.phieu_id,
            hdkhId: selectedRow.hdkh_id,
            ketQuaId: this.tuVanFilter.ketQuaId,
            donViTHId: +this.tuVanFilter.donViTHId,
            nDGiao: this.tuVanFilter.noiDung,
            maGd: selectedRow.ma_gd,
            ungDungId: this.tuVanFilter.ungDungId,
            donViId: this.donViId,
            hdtbId: selectedRow.hdtb_id,
            donViNhanId: this.tuVanFilter.donViNhanId,
            nhanVienTHId: 0,
            lyDoTT: this.tuVanFilter.lyDoTT,
            lyDoTra: this.tuVanFilter.lyDoTra,
            tag: +this.tag,
            message: message
          })
          if (isSuccess) await this.setDanhSachTuVan()
        } catch (e) {
          return false
        } finally {
          this.loading(false)
        }
        return true
      }
    },
    async canhBaoCapNhat (param, type) {
      this.loading(true)
      try {
        const response = await api.kiemTraCanhBaoCapNhat(this.axios, { p_param: param, p_type: type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' && data.data.length > 0
      } catch (e) {
        this.$root.$toast.error('Không thể kiểm tra cảnh báo cập nhật')
        return false
      } finally {
        this.loading(false)
      }
    },
    async capNhat (
      { phieuId, hdkhId, ketQuaId, donViTHId, nDGiao, maGd, ungDungId, donViId,
        hdtbId, donViNhanId, nhanVienTHId, lyDoTT, lyDoTra, tag, message
      }
    ) {
      try {
        const response = await api.capNhat(
          this.axios,
          {
            'phieu_id': phieuId,
            'hdkh_id': hdkhId,
            'ketqua_id': ketQuaId,
            'donvi_th_id': donViTHId,
            'nd_giao': nDGiao,
            'ma_gd': maGd,
            'ungdung_id': ungDungId,
            'donvi_id': donViId,
            'hdtb_id': hdtbId,
            'donvi_nhan_id': donViNhanId,
            'nhanvien_th_id': nhanVienTHId,
            'lydo_tt': lyDoTT,
            'lydo_tra': lyDoTra,
            'tag': tag
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200' && data.data.result === '1') {
          this.$root.$toast.success(message)
          return true
        } else {
          this.$root.$toast.error('Cập nhật không thành công')
          return false
        }
      } catch (e) {
        this.$root.$toast.error('Không thể cập nhật')
        return false
      }
    },
    async showPopupGiaoViec () {
      if (this.dataGrid.danhSachTuVan.dataSources.length <= 0) {
        this.$root.$toast.warning('Không có phiếu yêu cầu tiếp nhận. Bạn hãy kiểm tra lại!')
        return false
      }
      this.loading(true)
      try {
        const [countNVMD, countNVDB] = await Promise.all([
          this.countNhanVienMacDinh(this.nhanVienId, 1),
          this.countNhanVienDiaBan(this.nhanVienId, 1)
        ])
        if (countNVMD >= 1 || countNVDB >= 1) this.kieuNhanVienMacDinh = 1
        await this.$refs.giaoViec.init(this.kieuNhanVienMacDinh)
      } catch (e) {
      } finally {
        this.loading(false)
      }
      this.$refs.popupGiaoViec.show()
    },
    async countNhanVienMacDinh (param, type) {
      try {
        const response = await api.getThongTinNVMD(this.axios, { p_param: param, p_type: type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' && data.data.length > 0 ? data.data[0].count : 0
      } catch (e) {
        this.$root.$toast.error('Không thể kiểm tra nhân viên mặc định')
        return 0
      }
    },
    async countNhanVienDiaBan (param, type) {
      try {
        const response = await api.getThongTinLnv(this.axios, { p_param: param, p_type: type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' && data.data.length > 0 ? data.data[0].count : 0
      } catch (e) {
        this.$root.$toast.error('Không thể kiểm tra nhân viên địa bàn')
        return 0
      }
    },
    onClosePopupGiaoViec () {
      this.$refs.popupGiaoViec.hide()
    },
    async onAfterClosePopupGiaoViec () {
      this.loading(true)
      try {
        this.dataGrid.danhSachGiaoViec.dataSources = await this.getThongTinGiaoViec(this.phieuId)
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    showPopupGhepMa () {
      this.$refs.ghepMaRef.clear()
      this.$refs.popupGhepMa.show()
    },
    async onKhoaMoPayTVAction (href) {
      if (this.isEmptyObject(this.dataGrid.danhSachTuVan.selectedRow)) return
      const khoaMoPayTV = await this.lapHopDong('pay_tv')
      if (khoaMoPayTV.includes('không có')) {
        this.$root.$toast.warning(khoaMoPayTV)
        return false
      }
      const hdkh_id = this.dataGrid.danhSachTuVan.selectedRow.hdkh_id
      if (!hdkh_id) {
        this.$root.$toast.warning('Bạn chưa chọn đúng thông tin khách hàng')
        return false
      }
      const [count, maTB] = khoaMoPayTV.split(';')
      if (this.checkValue === PHIEU_MOI) {
        if (count === '0') {
          this.$confirm(
            'Hoàn thành phiếu tiếp nhận và tiến hành lập mới hợp đồng?',
            'Lập hợp đồng',
            { confirmButtonText: 'Có', cancelButtonText: 'Không' }
          ).then(async () => {
            await Promise.all([
              this.tuvan_ptdv_kyhd(),
              this.lapHopDong('update_pay_tv')
            ])
            await this.setDanhSachTuVan()
            const query = {
              kieuld_id: KieuLapDat.THAYDOI_TT_DICHVU_KHAC,
              vmatb: maTB,
              dichvuvt_id: DichVuVienThong.ADSL,
              loaitb_id: LoaiHinhTB.INTERNET_MYTV
            }
            const nextRoute = this.$router.resolve({ name: href, query })
            window.open(nextRoute.href, '_blank')
          }).catch((e) => {})
        } else {
          this.$root.$toast.warning('Thông tin phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin')
          return false
        }
      } else {
        const query = {
          kieuld_id: KieuLapDat.THAYDOI_TT_DICHVU_KHAC,
          vmatb: maTB,
          dichvuvt_id: DichVuVienThong.ADSL,
          loaitb_id: LoaiHinhTB.INTERNET_MYTV
        }
        const nextRoute = this.$router.resolve({ name: href, query })
        window.open(nextRoute.href, '_blank')
      }
    },
    async tuvan_ptdv_kyhd () {
      this.loading(true)
      try {
        const response = await api.tuvan_ptdv_kyhd(
          this.axios,
          {
            vphieu_id: this.dataGrid.danhSachTuVan.selectedRow.phieu_id,
            vnhanvien_id: this.nhanVienId,
            vdonvi_id: this.donViId,
            vdonvi_nhan_id: this.nhanVienId,
            vnhanvien_th_id: this.nhanVienId,
            vnd_giao: this.tuVanFilter.noiDung
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          return data.data
        } else {
          this.$root.$toast.error('Cập nhật khóa mở PayTV không thành công')
          return ''
        }
      } catch (e) {
        this.$root.$toast.error('Không thể cập nhật khóa mở PayTV')
        return ''
      } finally {
        this.loading(false)
      }
    },
    async onLapHopDongMenuAction (frame, href, tag) {
      if (!frame) return false
      const selectedRow = this.dataGrid.danhSachTuVan.selectedRow
      if (this.isEmptyObject(selectedRow)) {
        this.$root.$toast.warning('Chưa chọn phiếu')
        return false
      }
      let phieucskh_id
      let phieucskhdv_id
      let hdkh_id
      let ctv_id
      if (this.tuVanFilter.ungDungId === 5) {
        if (!this.B2A_MENUTAGS.includes(frame)) {
          this.$root.$toast.warning('Menu chưa hỗ trợ liên kết')
          return false
        }
        phieucskhdv_id = selectedRow.phieucskhdv_id
        phieucskh_id = selectedRow.phieucskh_id
        const lapHopDong = await this.lapHopDong('')
        if (lapHopDong === '0') {
          if (selectedRow.kieu === 2 && !this.B2A_MENUTAGS_BOSUNG.includes(frame)) {
            this.$root.$toast.warning('Phiếu bổ sung không được liên kết với chức năng này!')
            return false
          }
          this.$confirm(
            'Tiến hành lập mới hợp đồng?',
            'Lập hợp đồng',
            { confirmButtonText: 'Có', cancelButtonText: 'Không' }
          ).then(() => {
            const query = {
              kieu: this.tuVanFilter.ungDungId,
              phieucskh_id,
              phieucskhdv_id
            }
            if (tag) query.tag = encodeURIComponent(tag)
            const nextRoute = this.$router.resolve({ name: href, query })
            window.open(nextRoute.href, '_blank')
            return true
          }).catch((e) => {})
        } else {
          this.$root.$toast.warning('Thông tin phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin')
          return false
        }
      } else {
        if (!selectedRow.hdkh_id) {
          this.$root.$toast.warning('Bạn chưa chọn đúng thông tin khách hàng')
          return false
        }
        hdkh_id = selectedRow.hdkh_id
        ctv_id = selectedRow.nhanvien_tn_id || 0
        if (this.checkValue === PHIEU_MOI) {
          const lapHopDong = await this.lapHopDong('')
          if (lapHopDong === '0') {
            this.$confirm(
              'Hoàn thành phiếu tiếp nhận và tiến hành lập mới hợp đồng?',
              'Lập hợp đồng',
              { confirmButtonText: 'Có', cancelButtonText: 'Không' }
            )
              .then(async () => {
                await this.lapHopDong('update_tt')
                await this.setDanhSachTuVan()
                const query = {
                  kieu: this.tuVanFilter.ungDungId,
                  hdkh_cha_id: hdkh_id,
                  ctv_id
                }
                if (tag) query.tag = encodeURIComponent(tag)
                const nextRoute = this.$router.resolve({ name: href, query })
                window.open(nextRoute.href, '_blank')
                return true
              })
              .catch((e) => {})
          } else {
            this.$root.$toast.warning('Thông tin phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin')
            return false
          }
        } else {
          const query = {
            kieu: this.tuVanFilter.ungDungId,
            hdkh_cha_id: hdkh_id,
            ctv_id
          }
          if (tag) query.tag = encodeURIComponent(tag)
          const nextRoute = this.$router.resolve({ name: href, query })
          window.open(nextRoute.href, '_blank')
        }
      }
    },
    isEmptyObject (object) {
      return object && Object.keys(object).length === 0 && Object.getPrototypeOf(object) === Object.prototype
    },
    async lapHopDong (step) {
      const selectedRow = this.dataGrid.danhSachTuVan.selectedRow
      this.loading(true)
      try {
        const response = await api.lapHopDong(
          this.axios,
          {
            p_ungdung_id: this.tuVanFilter.ungDungId,
            p_phieucskhdv_id: selectedRow.phieucskhdv_id,
            p_phieu_id: selectedRow.phieu_id,
            p_hdkh_id: selectedRow.hdkh_id,
            p_hdtb_id: selectedRow.hdtb_id,
            p_phieumoi: this.checkValue === PHIEU_MOI ? 1 : 0,
            p_step: step
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          return data.data
        } else {
          this.$root.$toast.error('Lấy thông tin lập hợp đồng không thành công')
          return ''
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin lập hợp đồng')
        return ''
      } finally {
        this.loading(false)
      }
    },
    async showPopupThongTinGT () {
      if (this.dataGrid.danhSachTuVan.dataSources.length <= 0) {
        this.$root.$toast.warning('Không có dữ liệu')
        return false
      }
      if (this.hdkhId) {
        await this.$refs.thongTinGT.init()
        this.$refs.popupThongTinGT.show()
      } else {
        this.$root.$toast.warning('Không có dữ liệu HDKH')
      }
    },
    async onChangeTrangThai (value) {
      this.loading(true)
      try {
        this.checkValue = value
        if (value === PHIEU_MOI) {
          this.hidden.isHdnTabOne = false
          this.hidden.isHdnTabTwo = true
          this.hidden.isHdnTabThree = true
          this.onSetColumnVisible('nd_tra', false)
          this.onSetColumnVisible('lydotra', false)
          this.onSetButtonVisible('tsbtnCapNhat', true)
          this.onSetButtonVisible('tsbtnGiaoViec', true)
          this.show.isShowKetQua = true
        } else if (value === PHIEU_TRA_LAI) {
          this.hidden.isHdnTabOne = true
          this.hidden.isHdnTabTwo = false
          this.hidden.isHdnTabThree = true
          this.onSetColumnVisible('nd_tra', true)
          this.onSetColumnVisible('lydotra', true)
          this.onSetButtonVisible('tsbtnCapNhat', true)
          this.onSetButtonVisible('tsbtnGiaoViec', true)
          this.show.isShowKetQua = true
        } else {
          this.hidden.isHdnTabOne = true
          this.hidden.isHdnTabTwo = true
          this.hidden.isHdnTabThree = false
          this.onSetColumnVisible('nd_tra', false)
          this.onSetColumnVisible('lydotra', false)
          this.onSetButtonVisible('tsbtnCapNhat', false)
          this.onSetButtonVisible('tsbtnGiaoViec', false)
          this.show.isShowKetQua = false
        }
        this.tuVanFilter.ketQuaId = null
        this.tuVanFilter.donViTHId = null
        this.tuVanFilter.noiDung = ''
        this.show.isShowDonViTH = false
        this.show.isShowNoiDung = false
        this.show.isShowLyDoTT = false
        this.show.isShowLyDoTra = false
        await this.setDanhSachTuVan()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async onChangeUngDung () {
      this.loading(true)
      try {
        await this.setDanhSachTuVan()
        this.isShowLapHopDong = false
        if (this.tuVanFilter.ungDungId === 5) {
          const B2AData = await this.LayDS_B2AForm(1)
          if (B2AData.length > 0) {
            const menuTags = B2AData.map(tag => tag.form_name)
            this.B2A_MENUTAGS = [...new Set(menuTags)]
            const menuTagsBoSung = B2AData
              .filter(tag => tag.kieu === 2)
              .map(tag => tag.form_name)
            this.B2A_MENUTAGS_BOSUNG = [...new Set(menuTagsBoSung)]
          } else {
            this.B2A_MENUTAGS = []
            this.B2A_MENUTAGS_BOSUNG = []
          }
          this.show.isShowThongTinGiaoViec = false
          this.isShowLapHopDong = true
          this.onSetButtonVisible('tsbtnGiaoViec', false)
          this.onSetColumnVisible('nd_tra', false)
          this.onSetColumnVisible('lydotra', false)
          this.onSetColumnVisible('nhanvien_th', false)
          this.onSetColumnVisible('ghichu', false)
          this.onSetColumnVisible('giaoviec', false)
          this.onSetColumnVisible('gio_cl', false)
          this.onSetColumnVisible('thoigian_ck', false)
          this.onSetColumnVisible('chitieu_tg', false)
          this.tuVanFilter.ketQuaId = 1
          this.show.isShowKetQua = false
          this.onSetButtonVisible('tsbtnCapNhat', false)
          this.onSetButtonVisible('btnGhepMa', false)
          this.onSetButtonVisible('tsbtnThongTinGT', false)
          this.ngayLabel = 'Ngày CS'
          this.onSetColumnVisible('phieucskh_id', true)
          this.onSetColumnVisible('ma_tb', true)
          this.onSetColumnVisible('nhom_lnc', true)
          this.onSetColumnVisible('phanloai', true)
          this.onSetColumnVisible('ungdung', true)
          this.onSetColumnName('ngay_yc', 'Ngày CS')
        } else {
          this.show.isShowThongTinGiaoViec = true
          this.onSetColumnVisible('phieucskh_id', false)
          this.onSetColumnVisible('ma_tb', false)
          this.onSetColumnVisible('nhom_lnc', false)
          this.onSetColumnVisible('phanloai', false)
          this.onSetColumnVisible('ungdung', false)
          this.onSetButtonVisible('tsbtnThongTinGT', true)
          if (this.checkValue === CHUYEN_TC) {
            this.show.isShowKetQua = false
            this.onSetButtonVisible('tsbtnCapNhat', false)
            this.onSetButtonVisible('tsbtnGiaoViec', false)
            this.onSetColumnVisible('nd_tra', false)
            this.onSetColumnVisible('lydotra', false)
          } else {
            this.show.isShowKetQua = true
            this.onSetButtonVisible('tsbtnCapNhat', true)
            this.onSetButtonVisible('tsbtnGiaoViec', true)
            this.onSetColumnVisible('nd_tra', true)
            this.onSetColumnVisible('lydotra', true)
          }
          this.onSetButtonVisible('btnGhepMa', true)
          this.onSetColumnVisible('nhanvien_th', true)
          this.onSetColumnVisible('ghichu', true)
          this.onSetColumnVisible('giaoviec', true)
          this.onSetColumnVisible('gio_cl', true)
          this.onSetColumnVisible('thoigian_ck', true)
          this.onSetColumnVisible('chitieu_tg', true)
          this.ngayLabel = 'Ngày YC'
          this.onSetColumnName('ngay_yc', 'Ngày YC')
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async LayDS_B2AForm (type) {
      try {
        const response = await api.getThongTinB2aForm(this.axios, { p_param: '', p_type: type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin B2A')
        return []
      }
    },
    async onChangeKetQua () {
      this.show.isShowDonViNhan = false
      if (this.tuVanFilter.ketQuaId === 0) {
        this.show.isShowDonViTH = false
        this.show.isShowNoiDung = false
        this.show.isShowLyDoTT = false
        this.show.isShowLyDoTra = false
      } else if (this.tuVanFilter.ketQuaId === 1) {
        this.show.isShowDonViTH = true
        this.show.isShowNoiDung = true
        this.show.isShowLyDoTT = false
        this.show.isShowLyDoTra = false
        this.loading(true)
        try {
          this.donViTHOptions = await this.getDanhSachDonViTH()
        } catch (e) {
        } finally {
          this.loading(false)
        }
      } else if (this.tuVanFilter.ketQuaId === 3) {
        this.show.isShowDonViNhan = true
        this.show.isShowDonViTH = false
        this.show.isShowNoiDung = true
        this.show.isShowLyDoTT = false
        this.show.isShowLyDoTra = false
        this.loading(true)
        try {
          this.donViNhanOptions = await this.getDanhSachDonViNhan()
        } catch (e) {
        } finally {
          this.loading(false)
        }
      } else if (this.tag === '0') {
        this.show.isShowDonViTH = false
        this.show.isShowLyDoTra = false
        this.show.isShowNoiDung = true
        this.show.isShowLyDoTT = true
        this.tuVanFilter.lyDoTT = null
        this.loading(true)
        try {
          this.lyDoTTOptions = await this.getDanhSachLyDoTT()
        } catch (e) {
        } finally {
          this.loading(false)
        }
      } else {
        this.show.isShowDonViTH = false
        this.show.isShowNoiDung = true
        this.show.isShowLyDoTT = false
        this.show.isShowLyDoTra = true
      }
      if (this.tuVanFilter.ungDungId === 5) {
        this.isShowLapHopDong = true
      } else {
        this.isShowLapHopDong = this.tuVanFilter.ketQuaId === 1 && this.tuVanFilter.ungDungId === 4
      }
    }
  }
}
</script>
