<template src="./index.html"></template>
<style src="./style.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import '@/assets/vendor/jquery/split'
import api from './api'
import { DichVuVienThong, LoaiHinhTB } from '@/const/enums/index.js'
import moment from 'moment'
import PopupNhapTienTrinh from './popups/PopupNhapTienTrinh'
import ThayDoiThongSoInternet from '../../EXTS/QUANTRI/ThayDoiThongSoInternet'
import TracuuThongtinTrenmdf from '../../ECMS/tracuu-thongtin-trenmdf'
import XuatFile from '@/modules/CSKH/utils/XuatFile'
import PopupLichSuVt from './popups/PopupLichSuVt'
import XLSX from 'xlsx'
import PopupGuiSms from './popups/PopupGuiSms'
import SearchVISAOtherProvince from '../../search/subscriber/SearchVISAOtherProvince'
import PopupTestPort from './popups/PopupTestPort'
import CheckPort from '@/modules/search/subscriber/CheckPort';

export default {
  name: 'UpdateInstallProgress',
  components: {
    breadcrumb,
    PopupNhapTienTrinh,
    ThayDoiThongSoInternet,
    TracuuThongtinTrenmdf,
    appModalXuatDuLieu: XuatFile,
    PopupLichSuVt,
    PopupGuiSms,
    SearchVISAOtherProvince,
    XLSX,
    PopupTestPort,
    CheckPort
  },
  data () {
    return {
      header: {
        title: 'Cập nhật tiến trình thi công',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      actions: [
        { id: 'btnTimKiem', icon: 'one-search', name: 'Tìm kiếm', visible: true, disabled: false },
        { id: 'tsbtnDoKiem', icon: 'one-clipart-arrow-right', name: 'Show port', visible: true, disabled: false },
        { id: 'tsbtnTestAllPort', icon: 'nc-icon-glyph arrows-1_simple-up', name: 'Test port', visible: true, disabled: false },
        { id: 'tsbtnShowIP', icon: 'one-location-ip', name: 'Show IP', visible: true, disabled: false },
        { id: 'tsbtnThayDoiTS_ADSL', icon: 'one-usb-port', name: 'Đổi port', visible: true, disabled: false },
        { id: 'tsbtnLichSuVT', icon: 'one-calendar-history', name: 'Lịch sử VT', visible: true, disabled: false },
        { id: 'tsbtnGianDo', icon: 'one-search', name: 'Tra cứu cáp', visible: true, disabled: false },
        { id: 'tsbtnXuatFile', icon: 'one-download', name: 'Xuất file', visible: true, disabled: false },
        { id: 'tsbtnXuatExcel', icon: 'one-xlsx-import', name: 'Xuất Excel', visible: true, disabled: false },
        { id: 'btnNhapMoi', icon: 'one-file-plus', name: 'Nhập tiến trình', visible: true, disabled: false },
        { id: 'toolStripButton1', icon: 'one-circle-question', name: 'Trợ giúp', visible: true, disabled: false },
        { id: 'tsbtnFileTT', icon: 'one-upload', name: 'File T/T', visible: false, disabled: true }
      ],
      danhSachDonVi: [],
      treeFields: '',
      selectedNodes: [0, 1],
      expandedNodes: [0],
      timKiemFilter: {
        loaiNhomId: null,
        dichVuId: null,
        loaiHDPLId: null,
        ckbNgayGiao: false,
        ngayGiao: moment(new Date()).format('DD/MM/YYYY'),
        isDisabledNgayGiao: true
      },
      thueBaoFilter: {
        acc: '',
        tenThueBao: '',
        soAo: '',
        kieuLD: '',
        loaiKHLon: '',
        diaChiLapDat: '',
        ghiChu: ''
      },
      phieuFilter: {
        maGD: '',
        soQuyetDinh: '',
        donVi: '',
        nhanVien: '',
        noiDungGiao: ''
      },
      loaiNhomOptions: [],
      dichVuOptions: [],
      loaiHDPLOptions: [],
      tienTrinhXuLy: {
        headers: [
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, freeze: 'left' },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, freeze: 'left' },
          { fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true },
          { fieldName: 'donvi_cn', headerText: 'Đơn vị', allowFiltering: true },
          { fieldName: '', headerText: 'SMS', allowFiltering: true, template: this.getColumnSMS(this) },
          { fieldName: '', headerText: 'Sửa', allowFiltering: true, template: this.getColumnSua(this) },
          { fieldName: '', headerText: 'Xóa', allowFiltering: true, template: this.getColumnXoa(this) }
        ],
        dataSources: [],
        selectedRow: null
      },
      baoTon: {
        headers: [
          { fieldName: 'ngay_bt', headerText: 'Ngày báo tồn', allowFiltering: true },
          { fieldName: 'lydoton_tc', headerText: 'Lý do tồn', allowFiltering: true },
          { fieldName: 'nguoi_bt', headerText: 'Người báo tồn', allowFiltering: true },
          { fieldName: 'ghichu_ton', headerText: 'Ghi chú', allowFiltering: true }
        ],
        dataSources: [],
        isShow: true
      },
      ckbLocNV: false,
      phieuYeuCau: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, visible: true, freeze: 'left' },
          { fieldName: 'matb_tn', headerText: 'Mã TN', allowFiltering: true, visible: true, freeze: 'left' },
          { fieldName: 'so_ao', headerText: 'Số ảo', allowFiltering: true, visible: true, freeze: 'left' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, visible: true, freeze: 'left' },
          { fieldName: 'ten_loaihd', headerText: 'Loại HĐ/PL', allowFiltering: true, visible: true, freeze: 'left' },
          {
            fieldName: 'donvi_nhan',
            headerText: 'Đơn vị nhận',
            allowFiltering: true,
            visible: true,
            customAttributes: { class: 'text-donvi-nhan' }
          },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, visible: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, visible: true },
          { fieldName: 'goi_kt', headerText: 'Gói KT', allowFiltering: true, visible: true },
          { fieldName: 'chitieu_tg', headerText: 'Chỉ tiêu TG', allowFiltering: true, visible: true },
          {
            fieldName: 'ngay_giao',
            headerText: 'Ngày giao',
            allowFiltering: true,
            visible: true,
            customAttributes: { class: 'text-ngaygiao' }
          },
          { fieldName: 'thoigian_ck', headerText: 'Thời gian cam kết', allowFiltering: true, visible: true },
          { fieldName: 'gio_conlai', headerText: 'Giờ còn lại', allowFiltering: true, visible: true },
          {
            fieldName: 'donvi_giao',
            headerText: 'Đơn vị giao',
            allowFiltering: true,
            visible: true,
            customAttributes: { class: 'text-donvi-giao' }
          },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true, visible: true },
          { fieldName: 'so_dt', headerText: 'Điện thoại KH', allowFiltering: true, visible: true },
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, visible: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, visible: true },
          { fieldName: 'lydoton_tc', headerText: 'Lý do tồn TC', allowFiltering: true, visible: true },
          { fieldName: 'ngay_bt', headerText: 'Ngày BT', allowFiltering: true, visible: true },
          { fieldName: 'nhomton', headerText: 'Nhóm tồn', allowFiltering: true, visible: true },
          { fieldName: 'ghichu_ton', headerText: 'Ghi chú tồn', allowFiltering: true, visible: true },
          { fieldName: 'thongtin_dh', headerText: 'Thông tin ĐH', allowFiltering: true, visible: true },
          { fieldName: 'ngay_dh', headerText: 'Ngày ĐH', allowFiltering: true, visible: true },
          { fieldName: 'ten_kieuld', headerText: 'Kiểu yêu cầu', allowFiltering: true, visible: true },
          { fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true, visible: true },
          { fieldName: 'sophieu', headerText: 'Số phiếu', allowFiltering: true, visible: true },
          { fieldName: 'giohen_tu', headerText: 'Giờ hẹn đầu', allowFiltering: true, visible: true },
          { fieldName: 'giohen_den', headerText: 'Giờ hẹn cuối', allowFiltering: true, visible: true },
          { fieldName: 'nhanvien_hen', headerText: 'Người hẹn', allowFiltering: true, visible: true },
          { fieldName: 'url_file_tt', headerText: 'File TT', allowFiltering: true, visible: true, template: this.getColumnFileTT(this) }
        ],
        dataSources: [],
        selectedRow: null
      },
      userALT: '',
      passALT: '',
      userXTest: '',
      passXTest: '',
      vnptNetUploadFileTienTrinh: null,
      kieuGoiId: 0,
      donViBC: null,
      position: { X: 'center', Y: 'top' },
      txtFileName: '',
      txtFilePath: '',
      lichSuVatTuProp: 0,
      nhapTienTrinhProp: {
        hdtbId: 0,
        tienTrinhTCId: 0,
        noiDung: ''
      },
      smsProp: {
        hdtbId: null,
        nhomId: null,
        baoHongId: 0,
        tientrinhTCId: null,
        maTB: '',
        noiDung: ''
      },
      testPortStatusProp: 'Đang kết nối ...',
      showIPAccountProp: '',
      showPortProp: '',
      xuatFileHeaders: [
        { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowSorting: true },
        { fieldName: 'chon', headerText: 'Chọn', allowFiltering: true, allowSorting: true },
        { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, allowSorting: true },
        { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: true },
        { fieldName: 'matb_tn', headerText: 'Mã TN', allowFiltering: true, allowSorting: true },
        { fieldName: 'so_ao', headerText: 'Số ảo', allowFiltering: true, allowSorting: true },
        { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: true },
        { fieldName: 'donvi_nhan', headerText: 'Đơn vị nhận', allowFiltering: true, allowSorting: true },
        { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowSorting: true },
        { fieldName: 'nd_giao', headerText: 'Nội dung giao', allowFiltering: true, allowSorting: true },
        { fieldName: 'phieu_id', headerText: 'Phiếu ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'phieu_cha_id', headerText: 'Phiếu cha ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, allowSorting: true },
        { fieldName: 'goi_kt', headerText: 'Gói KT', allowFiltering: true, allowSorting: true },
        { fieldName: 'chitieu_tg', headerText: 'Chỉ tiêu TG', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true, allowSorting: true },
        { fieldName: 'thoigian_ck', headerText: 'Thời gian cam kết', allowFiltering: true, allowSorting: true },
        { fieldName: 'gio_conlai', headerText: 'Giờ còn lại', allowFiltering: true, allowSorting: true },
        { fieldName: 'donvi_giao', headerText: 'Đơn vị giao', allowFiltering: true, allowSorting: true },
        { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true, allowSorting: true },
        { fieldName: 'so_dt', headerText: 'Điện thoại KH', allowFiltering: true, allowSorting: true },
        { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, allowSorting: true },
        { fieldName: 'lydoton_tc', headerText: 'Lý do tồn TC', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_bt', headerText: 'Ngày BT', allowFiltering: true, allowSorting: true },
        { fieldName: 'nhomton', headerText: 'Nhóm tồn', allowFiltering: true, allowSorting: true },
        { fieldName: 'ghichu_ton', headerText: 'Ghi chú tồn', allowFiltering: true, allowSorting: true },
        { fieldName: 'thongtin_dh', headerText: 'Thông tin ĐH', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_dh', headerText: 'Ngày ĐH', allowFiltering: true, allowSorting: true },
        { fieldName: 'ten_kieuld', headerText: 'Kiểu yêu cầu', allowFiltering: true, allowSorting: true },
        { fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true, allowSorting: true },
        { fieldName: 'sophieu', headerText: 'Số phiếu', allowFiltering: true, allowSorting: true },
        { fieldName: 'giohen_tu', headerText: 'Giờ hẹn đầu', allowFiltering: true, allowSorting: true },
        { fieldName: 'giohen_den', headerText: 'Giờ hẹn cuối', allowFiltering: true, allowSorting: true },
        { fieldName: 'nhanvien_hen', headerText: 'Người hẹn', allowFiltering: true, allowSorting: true },
        { fieldName: 'url_file_tt', headerText: 'URL file tt', allowFiltering: true, allowSorting: true },
        { fieldName: 'donvi_nhan_id', headerText: 'Đơn vị nhận ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'donvi_giao_id', headerText: 'Đơn vị giao ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'tthd_id', headerText: 'TTHD ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'status', headerText: 'Status', allowFiltering: true, allowSorting: true },
        { fieldName: 'loaitb_id', headerText: 'Loại thuê bao ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'ten_loaihd', headerText: 'Tên loại HD', allowFiltering: true, allowSorting: true },
        { fieldName: 'nhanvien_lhd', headerText: 'Nhân viên LHD', allowFiltering: true, allowSorting: true },
        { fieldName: 'ghichu_tb', headerText: 'Ghi chú', allowFiltering: true, allowSorting: true },
        { fieldName: 'huonggiao_id', headerText: 'Hướng giao ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'khlon_id', headerText: 'KH lớn ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, allowSorting: true },
        { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, allowSorting: true },
        { fieldName: 'lydotra_id', headerText: 'Lý do trả ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'dichvuvt_id', headerText: 'Dịch vụ VT ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'hdtb_id', headerText: 'HDTB ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'hdtb_cha_id', headerText: 'HDTB cha ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'quytrinh_id', headerText: 'Quy trình ID', allowFiltering: true, allowSorting: true },
        { fieldName: 'ten_goi', headerText: 'Tên gọi', allowFiltering: true, allowSorting: true },
        { fieldName: 'mau_ht', headerText: 'Mẫu HT', allowFiltering: true, allowSorting: true }
      ],
      checkPortDialog: {
        isVisiable: false,
        input: {
          vma_tb: null,
          vthuebao_id: null,
          vkieu: null,
        }
      },
      animationSettings: { effect: 'None' },
    }
  },

  async created () {
    this.getTag()
    await Promise.all([
      this.getThongTinDangNhapALT(),
      this.getDanhSachCayDonVi(),
      this.getDanhSachLoaiNhom(),
      this.getDanhSachDichVuVienThong(),
      this.anHienToolbar(),
      this.getDanhSachLoaiHDPL()
    ])
    /*
    if (colNhomTon != null) colNhomTon.Visible = false;
     */
  },

  mounted () {
    Split(
      ['#boxLeft', '#boxRight'],
      { sizes: [30, 70], gutterSize: 16, onDragEnd: function (sizes) {} }
    )
  },

  computed: {
    nhanVienId () {
      return +this.$root.token.getNhanVienID()
    },
    donViId () {
      return +this.$root.token.getDonViID()
    }
  },

  methods: {
    getTag () {
      if (this.kieuGoiId === 0) {
        const tag = this.$route.query.tag
        if (tag && tag.length > 0) {
          if (tag.includes('+')) {
            const [kieuGoiId] = tag.split('+')
            if (kieuGoiId) this.kieuGoiId = +kieuGoiId
            else this.kieuGoiId = 1
          } else {
            this.$toast.warning('Tham số tag truyền vào không hợp lệ')
            this.kieuGoiId = 1
          }
        } else {
          this.kieuGoiId = 1
        }
      }
    },
    async getThongTinDangNhapALT () {
      this.loading(true)
      try {
        const response = await api.getThongTinDangNhapALT(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.userALT = data.data.user_alt
          this.passALT = data.data.pass_alt
          this.userXTest = data.data.user_xtest
          this.passXTest = data.data.pass_xtest
        } else {
          this.userALT = ''
          this.passALT = ''
          this.userXTest = ''
          this.passXTest = ''
        }
      } catch (e) {
        this.$root.$toast.error('Lấy danh sách ứng dụng không thành công')
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachCayDonVi () {
      this.loading(true)
      try {
        const response = await api.getDanhSachCayDonVi(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.danhSachDonVi = data.data.ds_donvi
          this.treeFields = {
            dataSource: this.createTreeDonVi(data.data.ds_donvi),
            id: 'donvi_id',
            parentID: 'donvi_cha_id',
            text: 'ten_dv',
            hasChildren: 'hasChild',
            expanded: 'expanded'
          }
        } else {
          this.danhSachDonVi = []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách đơn vị')
      } finally {
        this.loading(false)
      }
    },
    createTreeDonVi (danhSachDonVi) {
      let trees = []
      let donviChaArray = danhSachDonVi.map(item => item.donvi_cha_id)
      donviChaArray = new Set(donviChaArray)
      donviChaArray = Array.from(donviChaArray)
        .sort((a, b) => a - b)
      let donviArray = danhSachDonVi.map(item => item.donvi_id)
        .sort((a, b) => a - b)
      danhSachDonVi.forEach(item => {
        if (donviChaArray.includes(item['donvi_id'])) {
          if (!donviArray.includes(item['donvi_cha_id'])) {
            delete item.donvi_cha_id
          }
          trees.push({...item, hasChild: true, expanded: true})
        } else if (item['donvi_cha_id'] != null && donviArray.includes(item['donvi_cha_id'])) {
          trees.push(item)
        } else {
          if (!donviArray.includes(item['donvi_cha_id'])) {
            delete item.donvi_cha_id
          }
          trees.push({...item, hasChild: true, expanded: true})
        }
      })
      return trees
    },
    async getDanhSachLoaiNhom () {
      this.loading(true)
      try {
        const response = await api.getDanhSachLoaiNhom(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.loaiNhomOptions = data.data
          this.timKiemFilter.loaiNhomId = data.data.length > 0 ? 1 : null
        } else {
          this.loaiNhomOptions = []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách loại nhóm')
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachDichVuVienThong () {
      this.loading(true)
      try {
        const response = await api.getDanhSachDichVuVienThong(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          data.data.push({
            DICHVUVT_ID: 0,
            MA_DVVT: 'TATCA',
            TEN_DVVT: 'Tất cả',
            GHICHU: '0',
            SUDUNG: 0,
            SU_DUNG: null
          })
          this.dichVuOptions = data.data
            .filter(elm => elm.DICHVUVT_ID !== 3 && elm.DICHVUVT_ID !== 6)
            .sort((a, b) => a.DICHVUVT_ID - b.DICHVUVT_ID)
          this.timKiemFilter.dichVuId = this.dichVuOptions[0].DICHVUVT_ID
        } else {
          this.dichVuOptions = []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách dịch vụ viễn thông')
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachThamSoMacDinh () {
      this.loading(true)
      try {
        const response = await api.getDanhSachThamSoMacDinh(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          return data.data
        } else {
          return []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách tham số mặc định')
      } finally {
        this.loading(false)
      }
    },
    async anHienToolbar () {
      const selectedRow = this.phieuYeuCau.selectedRow
      if (this.phieuYeuCau.dataSources.length <= 0 || !selectedRow) {
        for (const action of this.actions) {
          if (action.id !== 'btnTimKiem' && action.id !== 'toolStripButton1') {
            this.onSetButtonDisabled(action.id, true)
          }
        }
      } else {
        for (const action of this.actions) {
          this.onSetButtonDisabled(action.id, false)
        }
        this.onSetButtonDisabled('tsbtnThayDoiTS_ADSL', !(selectedRow.dichvuvt_id === DichVuVienThong.ADSL || selectedRow.dichvuvt_id === DichVuVienThong.IMS))
        this.onSetButtonDisabled('tsbtnShowIP', selectedRow.loaitb_id !== LoaiHinhTB.MEGA)
      }
      const danhSachThamSoMacDinh = await this.getDanhSachThamSoMacDinh()
      const thamSoVNPTNETUploadFile = danhSachThamSoMacDinh.find(thamSo => thamSo.ma_ts === 'VNPT_NET_UPLOAD_FILE_TIEN_TRINH')
      if (thamSoVNPTNETUploadFile && thamSoVNPTNETUploadFile.ten_ts.trim() === '1') {
        this.vnptNetUploadFileTienTrinh = 1
      }
      if (this.vnptNetUploadFileTienTrinh === 1) {
        this.onSetButtonVisible('tsbtnFileTT', true)
        this.onSetColumnVisible('url_file_tt', true)
      } else {
        this.onSetButtonVisible('tsbtnFileTT', false)
        this.onSetColumnVisible('url_file_tt', false)
      }
    },
    onSetButtonVisible (id, isVisible) {
      this.actions.find(item => item.id === id).visible = isVisible
    },
    onSetButtonDisabled (id, isDisabled) {
      this.actions.find(item => item.id === id).disabled = isDisabled
    },
    onSetColumnVisible (fieldName, isVisible) {
      this.phieuYeuCau.headers.find(header => header.fieldName === fieldName).visible = isVisible
    },
    async getDanhSachLoaiHDPL () {
      this.loading(true)
      try {
        const response = await api.getDanhSachLoaiHDPL(this.axios)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          if (data.data.length > 0) {
            const found = data.data.find(elm => elm.ten_loaihd && elm.ten_loaihd.toString() === 'Tat ca')
            if (found) found.ten_loaihd = 'Tất cả'
            this.timKiemFilter.loaiHDPLId = data.data[0].loaihd_id
          }
          this.loaiHDPLOptions = data.data
        } else {
          this.loaiHDPLOptions = []
          this.timKiemFilter.loaiHDPLId = null
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách loại HD/PL')
      } finally {
        this.loading(false)
      }
    },
    onNodeSelected (node) {
      this.donViBC = +node.nodeData.id
    },
    async onChangeLoaiNhom () {
      this.loading(true)
      try {
        const response = await api.getDanhSachLoaiHDByLoaiNhom(this.axios, this.timKiemFilter.loaiNhomId)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.loaiHDPLOptions = data.data
        } else {
          this.loaiHDPLOptions = []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách loại HD/PL')
      } finally {
        this.loading(false)
      }
    },
    onChangeCkbNgayGiao () {
      this.timKiemFilter.isDisabledNgayGiao = !this.timKiemFilter.isDisabledNgayGiao
      if (this.timKiemFilter.isDisabledNgayGiao) this.timKiemFilter.ngayGiao = moment(new Date()).format('DD/MM/YYYY')
    },
    async getDanhSachTienTrinhTC (maGd) {
      this.loading(true)
      try {
        const response = await api.getDanhSachTienTrinhTC(
          this.axios,
          {
            ma_gd: maGd,
            loaihd_id: this.timKiemFilter.loaiHDPLId,
            dichvuvt_id: this.timKiemFilter.dichVuId,
            ngay_giao: this.timKiemFilter.ckbNgayGiao ? this.timKiemFilter.ngayGiao : '0',
            ngay_tra: '0',
            nhanvien_id: this.ckbLocNV ? this.nhanVienId : 0
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.hienThiDataGrid(data.data)
        } else {
          this.phieuYeuCau.dataSources = []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách loại HD/PL')
      } finally {
        this.loading(false)
      }
    },
    async onChangeLocNV () {
      await this.getDanhSachTienTrinhTCBH()
      await this.anHienToolbar()
    },
    async onClickAction (id) {
      switch (id) {
        case 'btnTimKiem':
          await this.onTimKiem()
          break
        case 'tsbtnDoKiem':
          this.showPort()
          break
        case 'tsbtnTestAllPort':
          await this.testPort()
          break
        case 'tsbtnShowIP':
          this.showPopupShowIP()
          break
        case 'tsbtnThayDoiTS_ADSL':
          this.showPopupDoiPort()
          break
        case 'tsbtnLichSuVT':
          break
        case 'tsbtnGianDo':
          this.showPopupTraCuuCap()
          break
        case 'tsbtnXuatFile':
          this.showPopupXuatFile()
          break
        case 'tsbtnXuatExcel':
          this.xuatExcel()
          break
        case 'btnNhapMoi':
          this.showPopupNhapTienTrinh()
          break
        case 'tsbtnFileTT':
          this.uploadFileTT()
          break
        default:
          this.$root.$toast.info('Hướng dẫn sử dụng chức năng')
          break
      }
    },
    async onTimKiem () {
      await this.taoCTDLTienTrinh()
      await this.getDanhSachTienTrinhTCBH()
      await this.anHienToolbar()
    },
    async taoCTDLTienTrinh () {
      this.loading(true)
      try {
        await api.taoCTDLTienTrinh(this.axios)
      } catch (e) {
        this.$root.$toast.error('Không thể tạo cấu trúc dữ liệu')
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachTienTrinhTCBH () {
      if (this.donViBC === null) {
        this.$root.$toast.warning('Chọn đơn vị muốn lấy báo cáo !')
        return false
      }
      this.loading(true)
      try {
        const ngayGiao = this.timKiemFilter.ckbNgayGiao
          ? (this.timKiemFilter.ngayGiao ? this.timKiemFilter.ngayGiao : '0')
          : '0'
        const response = await api.getDanhSachTienTrinhTCBH(
          this.axios,
          {
            loainhom: this.timKiemFilter.loaiNhomId,
            loaihd_id: this.timKiemFilter.loaiHDPLId,
            dichvuvt_id: this.timKiemFilter.dichVuId,
            donvibc_id: this.donViBC,
            ngaygiao: ngayGiao,
            ngaytra: '0',
            kieugoi_id: this.kieuGoiId,
            nhanvien_id: this.ckbLocNV ? this.nhanVienId : 0
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          let results = []
          if (this.vnptNetUploadFileTienTrinh === 1) {
            this.loading(true)
            try {
              await Promise.all(data.data.map(async (elm) => {
                let pathFileTT = ''
                let urlFileTT = ''
                const dsFileTT = await this.getUrlFileTT(elm.hdtb_id)
                if (dsFileTT.length > 0) pathFileTT = dsFileTT[0].url
                if (pathFileTT) {
                  if (this.isValidUrl(pathFileTT)) urlFileTT = pathFileTT
                  else {
                    const dsUrlFileTT = await this.getFileByPath(pathFileTT)
                    if (dsUrlFileTT.length > 0) urlFileTT = dsUrlFileTT[0].file_url
                  }
                }
                const val = { ...elm, url_file_tt: urlFileTT }
                results.push(val)
              }))
            } catch (e) {
              this.$root.$toast.error('Không thể lấy thông tin url file TT')
            } finally {
              this.loading(false)
            }
          } else results = data.data
          this.hienThiDataGrid(results)
        } else {
          this.phieuYeuCau.dataSources = []
          this.tienTrinhXuLy.dataSources = []
          this.baoTon.dataSources = []
          this.phieuFilter.maGD = ''
          this.thueBaoFilter.acc = ''
          this.thueBaoFilter.kieuLD = ''
          this.thueBaoFilter.tenThueBao = ''
          this.thueBaoFilter.diaChiLapDat = ''
          this.phieuFilter.donVi = ''
          this.phieuFilter.soQuyetDinh = ''
          this.phieuFilter.nhanVien = ''
          this.phieuFilter.noiDungGiao = ''
          this.timKiemFilter.ngayGiao = moment(new Date()).format('DD/MM/YYYY')
          this.thueBaoFilter.soAo = ''
          this.thueBaoFilter.loaiKHLon = ''
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách tiến trình')
      } finally {
        this.loading(false)
      }
    },
    async getUrlFileTT (hdtbId) {
      try {
        const response = await api.getUrlFileTT(this.axios, hdtbId)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          return data.data
        } else {
          return []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin url file TT')
      }
    },
    isValidUrl (text) {
      let url
      try {
        url = new URL(text)
      } catch (e) {
        return false
      }
      return url.protocol === 'http:' || url.protocol === 'https:'
    },
    async getFileByPath (path) {
      try {
        const response = await api.getFileByPath(this.axios, { list_file: path })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          return data.data
        } else {
          return []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy đường dẫn file')
      }
    },
    hienThiDataGrid (data) {
      this.onSetColumnVisible('nhomton', data.some(item => item.hasOwnProperty('nhomton')))
      if (data.length > 0) this.phieuYeuCau.dataSources = [...data]
      else {
        this.phieuYeuCau.dataSources = []
        this.phieuFilter.maGD = ''
        this.thueBaoFilter.acc = ''
        this.thueBaoFilter.kieuLD = ''
        this.thueBaoFilter.tenThueBao = ''
        this.thueBaoFilter.diaChiLapDat = ''
        this.phieuFilter.donVi = ''
        this.phieuFilter.soQuyetDinh = ''
        this.phieuFilter.nhanVien = ''
        this.phieuFilter.noiDungGiao = ''
        this.timKiemFilter.ngayGiao = moment(new Date()).format('DD/MM/YYYY')
      }
    },
    showPort () {
      if (this.phieuYeuCau.dataSources.length <= 0) {
        this.$root.$toast.warning('Bạn hãy nhập mã thuê bao!')
        return false
      }
      this.checkPortDialog.input.vma_tb = this.showPortProp
      this.checkPortOnClick()
    },
    async testPort () {
      this.$refs.popupTestPortRef.show()
      const status = await this.getPortStatus()
      this.testPortStatusProp = status || 'Không tìm thấy thông tin'
    },
    async getPortStatus () {
      if (!this.userALT || !this.passALT) this.testPortStatusProp = ''
      if (this.phieuYeuCau.selectedRow.dichvuvt_id !== DichVuVienThong.ADSL) return ''
      this.loading(true)
      try {
        const response = await api.getPortStatus(
          this.axios,
          {
            input: this.phieuYeuCau.selectedRow.ma_tb,
            linetestPassword: this.passALT,
            linetestUser: this.userALT,
            mac: 0
          }
        )
        const data = await response.data
        return data.data
      } catch (e) {
        this.$root.$toast.error('Không thể thực hiện test port')
        return ''
      } finally {
        this.loading(false)
      }
    },
    showPopupShowIP () {
      this.$refs.showIPRef.init()
      this.$refs.popupShowIPRef.show()
    },
    showPopupNhapTienTrinh () {
      const selectedRow = this.phieuYeuCau.selectedRow
      this.nhapTienTrinhProp.hdtbId = selectedRow.hdtb_id
      this.nhapTienTrinhProp.tienTrinhTCId = 0
      this.nhapTienTrinhProp.noiDung = `${selectedRow.ma_tb}|${selectedRow.ten_tb}|${selectedRow.diachi_ld}`
      this.$refs.nhapTienTrinhRef.onInit()
      this.$refs.popupNhapTienTrinhRef.show()
    },
    async onSuccessNhapTienTrinh () {
      await this.napTienTrinh()
      this.$refs.popupNhapTienTrinhRef.hide()
    },
    async showPopupDoiPort () {
      this.$refs.doiPortRef.setPropData({
        in_trangthai_dl: 0,
        in_hdtb_id: this.phieuYeuCau.selectedRow.hdtb_id,
        in_ma_tb: this.thueBaoFilter.acc,
        in_ten_tb: this.thueBaoFilter.tenThueBao,
        in_diachi_ld: this.thueBaoFilter.diaChiLapDat,
        in_loaitb_id: this.phieuYeuCau.selectedRow.loaitb_id,
        in_dichvuvt_id: this.phieuYeuCau.selectedRow.dichvuvt_id,
        in_flagTSKT: null
      })
      await this.$refs.doiPortRef.frmThayDoiTSInternet_Load()
      this.$refs.popupDoiPortRef.show()
    },
    showPopupLichSuVT () {
      this.$refs.popupLichSuVTRef.show()
    },
    showPopupTraCuuCap () {
      this.$refs.traCuuCapRef.initData(this.thueBaoFilter.acc)
      this.$refs.popupTraCuuCapRef.show()
    },
    showPopupXuatFile () {
      this.$bvModal.show('modal-xuat-dulieu')
    },
    xuatExcel () {
      const dataSources = this.phieuYeuCau.dataSources
      if (dataSources.length > 0) {
        const data = []
        const header = {}
        this.phieuYeuCau.headers.map(phieuYC => {
          if (phieuYC.visible) header[phieuYC.fieldName] = phieuYC.headerText
        })
        const headerKeys = Object.keys(header)
        const dataSourceKeys = Object.keys(dataSources[0])
        const duplicates = dataSourceKeys.filter(val => headerKeys.includes(val))
        duplicates.sort((a, b) => headerKeys.indexOf(a) - headerKeys.indexOf(b))
        for (const dataSource of dataSources) {
          const elm = {}
          for (const key of duplicates) {
            elm[header[key]] = dataSource[key]
          }
          data.push(elm)
        }
        const sheet = XLSX.utils.json_to_sheet(data)
        const wb = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(wb, sheet, 'DSPYC')
        XLSX.writeFile(wb, 'DSPYC.xls')
      } else {
        this.$root.$toast.warning('Không có dữ liệu để xuất Excel!')
      }
    },
    uploadFileTT () {
      this.$refs.fileTTRef.click()
    },
    async importFile (e) {
      const files = e.target.files
      let formData = new FormData()
      try {
        formData.append('files', files[0])
        this.loading(true)
        const resFileUpload = await api.uploadFile(
          this.axios,
          formData,
          { headers: { 'Content-Type': 'multipart/form-data' } })
        const data = await resFileUpload.data
        if (data.error_code === 'BSS-00000000' && data.error === 'BSS-00000000' && data.data.length > 0) {
          const url = data.data[0]
          const fileComponents = url.split('/')
          const fileName = fileComponents[fileComponents.length - 1]
          const isSuccess = await this.capNhatFileTT({
            p_hdtb_id: this.phieuYeuCau.selectedRow.hdtb_id,
            p_url: url,
            p_file_name: fileName,
            p_ma_tb: this.thueBaoFilter.acc
          })
          if (isSuccess) {
            this.$root.$toast.success('Cập nhật thành công!')
            await this.getDanhSachTienTrinhTCBH()
          } else {
            this.$root.$toast.error('Cập nhật không thành công!')
            this.resetFileTT()
          }
        } else {
          this.$root.$toast.error('Upload file thất bại.')
          this.resetFileTT()
          return false
        }
      } catch (e) {
        this.$root.$toast.error('Không thể upload file')
        this.resetFileTT()
        return false
      } finally {
        this.loading(false)
      }
    },
    async capNhatFileTT ({ p_hdtb_id, p_url, p_file_name, p_ma_tb }) {
      try {
        const response = await api.capNhatFileTT(this.axios, { p_hdtb_id, p_url, p_file_name, p_ma_tb })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200'
      } catch (e) {
        this.$root.$toast.error('Không thể cập nhật file')
        return ''
      }
    },
    resetFileTT () {
      this.$refs.fileTTRef.type = 'text'
      this.$refs.fileTTRef.type = 'file'
      this.$refs.fileTTRef.value = null
    },
    async onSelectedRowChanged (data) {
      if (data) {
        this.phieuYeuCau.selectedRow = { ...data }
        this.showIPAccountProp = this.showPortProp = data.ma_tb
        await this.anHienToolbar()
        if (this.phieuYeuCau.dataSources.length <= 0) return false
        await this.napTienTrinh()
        this.phieuFilter.noiDungGiao = data.nd_giao
        this.phieuFilter.nhanVien = data.nv_giao
        this.phieuFilter.donVi = data.donvi_giao
        this.timKiemFilter.ngayGiao = data.ngaygiao
        if (this.timKiemFilter.loaiNhomId === 1) {
          this.phieuFilter.soQuyetDinh = data.sophieu
          await this.getSoAoVaLoaiKHLon()
        } else {
          this.phieuFilter.soQuyetDinh = ''
          this.thueBaoFilter.soAo = ''
        }
      } else {
        this.phieuYeuCau.selectedRow = null
      }
    },
    async napTienTrinh () {
      if (this.phieuYeuCau.selectedRow) {
        const selectedRow = this.phieuYeuCau.selectedRow
        this.thueBaoFilter.acc = selectedRow.ma_tb
        this.phieuFilter.maGD = selectedRow.ma_gd
        this.thueBaoFilter.kieuLD = selectedRow.ten_kieuld
        this.thueBaoFilter.tenThueBao = selectedRow.ten_tb
        this.thueBaoFilter.diaChiLapDat = selectedRow.diachi_ld
        this.thueBaoFilter.ghiChu = selectedRow.ghichu_tb
        let soAoType = null
        switch (selectedRow.dichvuvt_id) {
          case DichVuVienThong.CO_DINH:
            soAoType = 1
            break
          case DichVuVienThong.ADSL:
          case DichVuVienThong.MEGA_EYES:
            soAoType = 2
            break
          case DichVuVienThong.TSL:
            soAoType = 3
            break
          default:
            soAoType = null
            break
        }
        if (soAoType) this.thueBaoFilter.soAo = await this.getSoAo(selectedRow.thuebao_id, soAoType)
        this.thueBaoFilter.loaiKHLon = await this.getLoaiKHLon(selectedRow.khlon_id, 1)
        await Promise.all([
          this.getDanhSachTienTrinhXuLy(),
          this.getDanhSachBaoTon()
        ])
        const trangThaiId = await this.getTrangThaiId(this.phieuYeuCau.selectedRow.hdtb_id, 2)
        this.onSetButtonDisabled('btnNhapMoi', trangThaiId.toString() === '6')
      } else {
        this.tienTrinhXuLy.dataSources = []
        this.baoTon.dataSources = []
        this.phieuFilter.maGD = ''
        this.thueBaoFilter.acc = ''
        this.thueBaoFilter.kieuLD = ''
        this.thueBaoFilter.tenThueBao = ''
        this.thueBaoFilter.diaChiLapDat = ''
        this.thueBaoFilter.ghiChu = ''
        this.thueBaoFilter.soAo = ''
        this.thueBaoFilter.loaiKHLon = ''
        this.onSetButtonDisabled('btnNhapMoi', true)
      }
    },
    async getDanhSachTienTrinhXuLy () {
      this.loading(true)
      try {
        const response = await api.getDanhSachTienTrinhXuLy(this.axios, this.phieuYeuCau.selectedRow.hdtb_id)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          if (data.data.length > 0) {
            data.data.forEach(elm => {
              elm.ngay_cn = moment(elm.ngay_cn).format('DD/MM/YYYY HH:mm:ss')
            })
          }
          this.tienTrinhXuLy.dataSources = data.data
        } else {
          this.tienTrinhXuLy.dataSources = []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách tiến trình xử lý')
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachBaoTon () {
      this.loading(true)
      try {
        const response = await api.getDanhSachBaoTon(this.axios, this.phieuYeuCau.selectedRow.phieu_id)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          if (data.data.length > 0) {
            data.data.forEach(elm => {
              elm.ngay_bt = moment(elm.ngay_bt).format('DD/MM/YYYY HH:mm:ss')
            })
          }
          this.baoTon.dataSources = data.data
          this.baoTon.isShow = data.data.length > 0
        } else {
          this.baoTon.dataSources = []
          this.baoTon.isShow = false
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách báo tồn')
      } finally {
        this.loading(false)
      }
    },
    async getSoAoVaLoaiKHLon () {
      const selectedRow = this.phieuYeuCau.selectedRow
      this.loading(true)
      try {
        const response = await api.getSoAoVaLoaiKHLon(
          this.axios,
          {
            loainhom: this.timKiemFilter.loaiNhomId,
            dichvuvt_id: selectedRow.dichvuvt_id,
            hdtb_id: selectedRow.hdtb_id,
            loaikhlon_id: selectedRow.khlon_id
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.thueBaoFilter.soAo = data.data.ds_hdtb.length > 0 ? data.data.ds_hdtb[0].ma_lt : ''
          this.thueBaoFilter.loaiKHLon = data.data.ten_lkhl
        } else {
          this.thueBaoFilter.soAo = ''
          this.thueBaoFilter.loaiKHLon = ''
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin số ảo')
      } finally {
        this.loading(false)
      }
    },
    async getTrangThaiId (param, type) {
      this.loading(true)
      try {
        const response = await api.getThongTinHdtb(this.axios, { param, type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin trạng thái')
      } finally {
        this.loading(false)
      }
    },
    async getSoAo (param, type) {
      this.loading(true)
      try {
        const response = await api.getThongTinDbCon(this.axios, { param, type })
        const data = await response.data
        return data.data ? data.error_code === 'BSS-00000000' && data.error === '200' : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin số ảo')
        return ''
      } finally {
        this.loading(false)
      }
    },
    async getLoaiKHLon (param, type) {
      this.loading(true)
      try {
        const response = await api.getThongTinLoaiKhl(this.axios, { param, type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin loại KH lớn')
      } finally {
        this.loading(false)
      }
    },
    getColumnFileTT (parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="center">
                <span title="Download file TT">
                  <a :href="urlFileTT" target="_blank" download>
                    <span class="icon one-download"></span>
                  </a>
                </span>
              </div>
            `,
            data () {
              return {
                parent: parent,
                data: {}
              }
            },
            computed: {
              urlFileTT () {
                return this.data.url_file_tt || 'javascript:void(0)'
              }
            }
          }
        }
      }
    },
    getColumnSMS (parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="center">
                <span title="Gửi SMS">
                  <button class="btn btn-sm btn-primary" @click="parent.onClickSendSMS(data)">
                    <span>SMS</span>
                  </button>
                </span>
              </div>
            `,
            data () {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    getColumnSua (parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="center">
                <span title="Cập nhật ghi chú xử lý">
                  <button class="btn btn-sm btn-primary" @click="parent.onClickSuaPhieu(data)">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                </span>
              </div>
            `,
            data () {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    getColumnXoa (parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="center">
                <span title="Xóa tiến trình">
                  <button class="btn btn-sm btn-danger" @click="parent.onClickXoaPhieu(data)">
                    <span class="-ap icon-close"></span>
                  </button>
                </span>
              </div>
            `,
            data () {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    onClickSendSMS (data) {
      const hdtbId = this.phieuYeuCau.selectedRow.hdtb_id
      const tienTrinhTCId = data.tientrinhtc_id
      if (hdtbId && tienTrinhTCId) {
        this.smsProp.hdtbId = hdtbId
        this.smsProp.nhomId = this.timKiemFilter.loaiNhomId
        this.smsProp.tientrinhTCId = tienTrinhTCId
        this.smsProp.maTB = this.thueBaoFilter.acc
        this.smsProp.noiDung = data.noidung
        this.$refs.guiSmsRef.onInit()
        this.$refs.popupGuiSmsRef.show()
      }
    },
    onClosePopupGuiSMS () {
      this.$refs.popupGuiSmsRef.hide()
    },
    onClickSuaPhieu (data) {
      this.nhapTienTrinhProp.hdtbId = data.hdtb_id
      this.nhapTienTrinhProp.tienTrinhTCId = data.tientrinhtc_id
      this.nhapTienTrinhProp.noiDung = data.noidung
      this.$refs.nhapTienTrinhRef.onInit()
      this.$refs.popupNhapTienTrinhRef.show()
    },
    async onClickXoaPhieu (data) {
      const sdt = await this.checkGuiSMSXoaTienTrinh(data.tientrinhtc_id, 1)
      if (sdt && sdt !== '-1') {
        this.$root.$toast.warning(`Bạn không thể xóa tiến trình này. Tiến trình đã được gửi tin nhắn đến ${sdt}`)
        return false
      }
      this.$confirm(
        'Bạn có thực sự muốn xóa tiến trình được chọn không ?',
        'Xóa tiến trình',
        { confirmButtonText: 'Có', cancelButtonText: 'Không' }
      ).then(async () => {
        const isSuccess = await this.xoaTienTrinhXuLy(data.tientrinhtc_id)
        if (isSuccess) {
          this.$root.$toast.success('Xóa tiến trình thành công')
          await this.napTienTrinh()
        }
      }).catch((e) => {})
    },
    async checkGuiSMSXoaTienTrinh (param, type) {
      this.loading(true)
      try {
        const response = await api.getThongTinTienTrinhSMS(this.axios, { param, type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin số điện thoại')
        return ''
      } finally {
        this.loading(false)
      }
    },
    async xoaTienTrinhXuLy (tienTrinhTCId) {
      this.loading(true)
      try {
        const response = await api.xoaTienTrinhXuLy(this.axios, { tientrinhtc_id: tienTrinhTCId })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200'
      } catch (e) {
        this.$root.$toast.error('Không thể xóa tiến trình')
      } finally {
        this.loading(false)
      }
    },
    async checkPortOnClick() {
      this.checkPortDialog.isVisiable = true
      this.$refs.checkPort.show()
    },
    async checkPortOnClose() {
      this.checkPortDialog.isVisiable = false
    },
  }
}
</script>
