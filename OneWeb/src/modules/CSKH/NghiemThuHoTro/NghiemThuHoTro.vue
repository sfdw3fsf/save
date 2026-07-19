<template src="./NghiemThuHoTro.html"></template>
<style src="./NghiemThuHoTro.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import select2 from '@/components/Select2.vue'

import { currency } from '@/filters/currency'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import SearchAccountModal from '@/modules/contract/setup/SurveyRequest/popups/SearchAccountModal'

import { L10n, setCulture } from '@syncfusion/ej2-base'
import PopupUploadFile from '@/modules/CSKH/TiepNhanHoTroKhachHang/popup/ModalUploadFile.vue'
import API from '@/modules/CSKH/api/TiepNhanHoTroKhachHangApi'

setCulture('vi-VN')

L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})

export default {
  components: {
    breadcrumb,
    appSelect2: select2,
    SearchAccount,
    SearchAccountModal,
    PopupUploadFile
  },
  data() {
    return {
      header: {
        title: 'NGHIỆM THU HỖ TRỢ',
        list: [
          { name: 'Chăm sóc khách hàng', link: { name: 'Ui.cards' } },
          {
            name: 'Xử lý thông tin hỗ trợ',
            link: { name: 'Ui.buttons' }
          },
          {
            name: 'Nghiệm thu hỗ trợ khách hàng',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      currentTab: 1,

      Enabled: {
        NhapMoi: true
      },

      popupComponent: null,

      MaThueBao: '',
      MaHoTro: '',
      TenThueBao: '',
      DiaChiLD: '',
      YeuCauHT: '',
      MaKH: '',
      NgayTiepNhan: new Date(),
      TenKH: '',
      DiaChi: '',
      MaSoThue: '',
      NguoiYeuCau: '',
      SoDT: '',
      Email: '',
      NgayHoTro: new Date(),
      NoiDung: '',
      DonViXL: '',

      DichVu: {
        ID: null,
        DataSouce: []
      },
      LoaiHinh: {
        ID: null,
        DataSouce: []
      },
      KenhTiepNhan: {
        ID: null,
        DataSouce: []
      },
      LoaiHoTro: {
        ID: null,
        DataSouce: []
      },
      ChuDe: {
        ID: null,
        DataSouce: []
      },
      MucDoHaiLong: {
        ID: null,
        DataSouce: []
      },
      HinhThuc: {
        ID: null,
        DataSouce: []
      },
      KetQua: {
        ID: null,
        DataSouce: []
      },
      PhanLoaiKH: {
        ID: null,
        DataSouce: []
      },
      DonViXL: {
        ID: null,
        DataSouce: []
      },
      phieuht_id: null,
      htkh_id: null,
      hdtb_id: null,
      quytrinh_id: 40360,
      thuebao_id: null,
      khachhang_id: null,
      isDiaChiLD: true,
      DSPhieuHoTro: [],
      dataDiaChiKH: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: '',
        // lat: 10.222151,
        // lng: 105.688407,
        kinhdo: null,
        vido: null,
        buttonTraPhieu: false
      },

      columnsPhieuHoTro: [
        { fieldName: 'TEN_TINH', headerText: 'Tinh/TP', allowFiltering: true, allowHtml: true },
        { fieldName: 'MA_HT', headerText: 'Mã HT', allowFiltering: true, allowHtml: true },
        { fieldName: 'MA_TB', headerText: 'Mã TB', allowFiltering: true, allowHtml: true },
        { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, allowHtml: true },
        { fieldName: 'TEN_TB', headerText: 'Tên TB', allowFiltering: true, allowHtml: true },
        { fieldName: 'DIACHI_LD', headerText: 'Địa chỉ LĐ', allowFiltering: true, allowHtml: true },
        { fieldName: 'TEN_KH', headerText: 'Tên KH', allowFiltering: true, allowHtml: true },
        { fieldName: 'CHUDE_HT', headerText: 'Chủ đề HT', allowFiltering: true, allowHtml: true },
        { fieldName: 'NGUOI_TN', headerText: 'Người tiếp nhận', allowFiltering: true, allowHtml: true },
        { fieldName: 'NGAY_TN', headerText: 'Ngày tiếp nhận', allowFiltering: true, allowHtml: true },
        { fieldName: 'NGUOI_XM', headerText: 'Người xử lý', allowFiltering: true, allowHtml: true },
        { fieldName: 'NGAY_XM', headerText: 'Ngày xử lý', allowFiltering: true, allowHtml: true },
        { fieldName: 'NGUOI_XM', headerText: 'Người xác minh', allowFiltering: true, allowHtml: true },
        { fieldName: 'NGAY_XM', headerText: 'Ngày xác minh', allowFiltering: true, allowHtml: true },
        { fieldName: 'NOIDUNG_XM', headerText: 'ND xác minh', allowFiltering: true, allowHtml: true },
        { fieldName: 'KETQUA', headerText: 'Kết quả', allowFiltering: true, allowHtml: true }
      ]
    }
  },
  computed: {},
  async created() {
    try {
      this.loading(true)
      this.DichVu.DataSouce = await this.getDSDichVu()
      this.KenhTiepNhan.DataSouce = await this.getDSDanhMuc('HINHTHUC_KN')
      this.LoaiHoTro.DataSouce = await this.getDSDanhMuc('LOAI_HT')
      this.KetQua.DataSouce = await this.getDSDanhMuc('KETQUA_HTKH')
      // this.ChuDe.DataSouce = await this.getDSDanhMuc('CHUDE_HOTRO')
      this.MucDoHaiLong.DataSouce = await this.getDSDanhMuc('MUCDO_HL')
      this.HinhThuc.DataSouce = await this.getDSHinhThuc()
      this.PhanLoaiKH.DataSouce = await this.getDSLoaiKH()
      this.TimKiem()
    } catch (e) {
      this.$toast.error(e.data.message)
    } finally {
      this.loading(false)
    }
  },
  async mounted() {},
  methods: {
    async getChuDe(thamSo) {
      const rs = await this.$root.context.get(`web-cskh/api/danh-muc/ho-tro?loaiDanhMuc=CHUDE_HOTRO&thamSo=${thamSo}`)
      console.log('getChuDe = ', rs)
      let result = []
      if (rs.data.length != 0) {
        result = rs.data
      }
      return result
    },

    async KhoaPhieu() {
      try {
        this.loading(true)
        if (this.htkh_id == null) {
          this.$toast.error('Chưa chọn phiếu hổ trợ!')
          return
        }
        if (this.KetQua.ID == null) {
          this.$toast.error('Chưa chọn kết quả!')
          return false
        }

        if (this.MucDoHaiLong.ID == null) {
          this.$toast.error('Chưa chọn mức độ hài lòng!')
          return false
        }
        let data = {
          htkh_id: this.htkh_id,
          kqxl_id: this.KetQua.ID,
          hailong: this.MucDoHaiLong.ID,
          phieuht_id: this.phieuht_id
        }
        const rs = await this.axios.put(`/web-cskh/api/tiep-nhan-khach-hang/khoa-phieu`, data)
        this.Enabled.NhapMoi = this.DSPhieuHoTro.length > 0 ? true : false
        if (rs.data.error_code === 'BSS-00000000') {
          this.$toast.success('Khóa phiếu thành công!')
        }
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.TimKiem()
        this.loading(false)
      }
    },
    async DichVuChanged() {
      if (this.DichVu.ID == null) {
        return
      }
      this.LoaiHinh.DataSouce = await this.getDSLoaiHinh(this.DichVu.ID)
      if (this.ChuDe.ID == null) {
        return
      }
      this.DonViXL.DataSouce = await this.getDSDonViXL({
        chuDe_Id: this.ChuDe.ID,
        dichVu_Id: this.DichVu.ID
      })
    },
    async ChuDeChanged() {
      if (this.DichVu.ID == null || this.ChuDe.ID == null) {
        return
      }
      let CheckChuDe = this.ChuDe.DataSouce.filter((e) => e.CHUDEHT_ID == this.ChuDe.ID)
      if (CheckChuDe.length > 0 && CheckChuDe[0].CAP == 1) {
        this.$toast.error('Chỉ được chọn chủ đề cha!')
        this.ChuDe.ID = null
        return
      }
      this.DonViXL.DataSouce = await this.getDSDonViXL({
        chuDe_Id: this.ChuDe.ID,
        dichVu_Id: this.DichVu.ID
      })
    },
    f_StringToDate(value, format = 'DD/MM/YYYY') {
      return moment(value, format).toDate()
    },
    f_DateToString: function (value, format = 'DD/MM/YYYY') {
      return moment(value).format(format)
    },
    ShowSearchAccount() {
      this.popupComponent = SearchAccount
      this.$bvModal.show('popupComponents')
    },
    ShowSearchAccountModal() {
      this.popupComponent = SearchAccount
      this.$bvModal.show('popupComponents')
    },
    async ShowPopupFile() {
      if (this.htkh_id == null) {
        this.$toast.error('Chưa chọn phiếu hổ trợ!')
        return
      }
      this.$refs.popupUploadFile.htkh_id = this.htkh_id
      this.$refs.popupUploadFile.showModal()
    },
    async GhiLai() {
      try {
        this.loading(true)
        let kiemTra = await this.ValidateGhilai()
        if (!kiemTra) {
          return
        }

        let data = {
          htkh_id: this.Enabled.NhapMoi ? this.htkh_id : 0, // true update, false tạp mới
          ma_ht: this.MaHoTro.trim(),
          thuebao_id: this.thuebao_id,
          ma_tb: this.MaThueBao.trim(),
          dichvuvt_id: this.DichVu.ID,
          loaitb_id: this.LoaiHinh.ID,
          ten_tb: this.TenThueBao.trim(),
          diachi_ld: this.DiaChiLD.trim(),
          diachild_id: 0,
          httn_id: this.KenhTiepNhan.ID,
          ngay_tn: this.f_DateToString(this.NgayTiepNhan),
          loaiht_id: this.LoaiHoTro.ID,
          chudeht_id: this.ChuDe.ID,
          yc_hotro: this.YeuCauHT.trim(),
          khachhang_id: this.khachhang_id,
          phanloaikh_id: this.PhanLoaiKH.ID, // chưa đúng
          ma_kh: this.MaKH.trim(),
          ten_kh: this.TenKH.trim(),
          diachi_kh: this.DiaChi.trim(),
          diachikh_id: 0,
          mst: this.MaSoThue,
          nguoi_yc: this.NguoiYeuCau.trim(),
          dienthoai_lh: this.SoDT.trim(),
          email: this.Email,
          ngay_ht: this.f_DateToString(this.NgayHoTro),
          htht_id: this.HinhThuc.ID,
          kqxl_id: this.KetQua.ID,
          hailong: this.MucDoHaiLong.ID,
          noidung_ht: this.NoiDung
        }
        let KetQua = await this.GhiLaiPhieuTiepNhan(data)
        let message = this.Enabled.NhapMoi ? 'Cập nhật thành công!' : 'Thêm mới thành công'
        this.DSPhieuHoTro = await this.getDSPhieuHoTro(this.$root.token.getDonViID())
        this.Enabled.NhapMoi = this.DSPhieuHoTro.length > 0 ? true : false
        this.$toast.success(message)
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    async MaThueBao_KeyPress(MaThueBao) {
      try {
        this.loading(true)
        if (MaThueBao == null || MaThueBao.trim() == '') {
          this.$toast.error('Chưa nhập thuê bao!')
          return
        }
        this.clearPhieuHoTro()
        this.MaThueBao = MaThueBao
        let KetQua = await this.getThongTinThueBao(MaThueBao)
        if (KetQua.length == 0) {
          return
        }
        this.TenThueBao = KetQua[0].TEN_TB
        this.DiaChiLD = KetQua[0].DIACHI_LD
        this.thuebao_id = KetQua[0].THUEBAO_ID
        this.khachhang_id = KetQua[0].KHACHHANG_ID
        this.DichVu.ID = KetQua[0].DICHVUVT_ID
        await this.DichVuChanged()
        this.LoaiHinh.ID = KetQua[0].LOAITB_ID

        let KetQuaKH = await this.getThongTinKhachHang({
          loaiId: 1,
          thongTinKH: this.khachhang_id
        })

        if (KetQuaKH.length == 0) {
          return
        }
        this.khachhang_id = KetQuaKH[0].KHACHHANG_ID
        this.TenKH = KetQuaKH[0].TEN_KH
        this.DiaChi = KetQuaKH[0].DIACHI_KH
        this.PhanLoaiKH.ID = KetQuaKH[0].LOAIKH_ID
        this.MaSoThue = KetQuaKH[0].MST
        this.SoDT = KetQuaKH[0].SO_DT
        this.Email = KetQuaKH[0].EMAIL
        this.MaKH = KetQuaKH[0].MA_KH
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },

    async MaKH_KeyPress(MaKH) {
      try {
        this.loading(true)
        if (this.MaKH == null || this.MaKH.trim() == '') {
          this.$toast.error('Chưa nhập mã khách hàng!')
          return
        }
        this.clearThongTinKhachHang()
        this.MaKH = MaKH
        let KetQua = await this.getThongTinKhachHang({
          loaiId: 2,
          thongTinKH: this.MaKH
        })

        if (KetQua.length > 0) {
          this.khachhang_id = KetQua[0].KHACHHANG_ID
          this.TenKH = KetQua[0].TEN_KH
          this.DiaChi = KetQua[0].DIACHI_KH
          this.PhanLoaiKH.ID = KetQua[0].LOAIKH_ID
          this.MaSoThue = KetQua[0].MST
          this.SoDT = KetQua[0].SO_DT
          this.Email = KetQua[0].EMAIL
        }
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    async TimKiem() {
      try {
        this.DSPhieuHoTro = await this.getDSPhieuHoTro(this.$root.token.getDonViID())
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    acceptDiaChiKH(data) {
      this.isDiaChiLD ? (this.DiaChiLD = data.diachi) : (this.DiaChi = data.diachi)
    },
    clearTab() {},
    clearPhieuHoTro() {
      this.MaThueBao = ''
      this.MaHoTro = ''
      this.TenThueBao = ''
      this.DiaChiLD = ''
      this.YeuCauHT = ''
      this.NgayTiepNhan = new Date()
      this.DichVu.ID = null
      this.LoaiHinh.ID = null
      this.KenhTiepNhan.ID = null
      this.LoaiHoTro.ID = null
      this.ChuDe.ID = null
    },
    clearThongTinKhachHang() {
      this.MaKH = ''
      this.TenKH = ''
      this.DiaChi = ''
      this.MaSoThue = ''
      this.NguoiYeuCau = ''
      this.SoDT = ''
      this.Email = ''
      this.NgayHoTro = new Date()
      this.NoiDung = ''
      this.DonViXL.ID = null
      this.KetQua.ID = null
      this.MucDoHaiLong.ID = null
      this.PhanLoaiKH.ID = null
    },
    clearAll() {
      this.htkh_id = null
      this.thuebao_id = null
      this.khachhang_id = null
      this.clearPhieuHoTro()
      this.clearThongTinKhachHang()
      this.clearTab()
    },
    SelectedPhieuHoTro: async function (item) {
      this.Enabled.NhapMoi = !item ? false : true
      if (!item) {
        return
      }
      console.log('SelectedPhieuHoTro = ', item)
      this.clearPhieuHoTro()
      this.clearThongTinKhachHang()
      this.phieuht_id = item.PHIEUHT_ID
      this.htkh_id = item.HTKH_ID
      this.thuebao_id = item.THUEBAO_ID
      this.khachhang_id = item.KHACHHANG_ID
      // Phiếu Hổ Trợ
      this.MaThueBao = item.MA_TB
      this.MaHoTro = item.MA_HT
      this.TenThueBao = item.TEN_TB
      this.DiaChiLD = item.DIACHI_LD
      this.YeuCauHT = item.YC_HOTRO
      this.NgayTiepNhan = item.NGAY_TN
      this.DichVu.ID = item.DICHVUVT_ID
      await this.DichVuChanged()
      this.LoaiHinh.ID = item.DICHVUVT_ID // Chưa có
      this.KenhTiepNhan.ID = item.HTTN_ID
      this.LoaiHoTro.ID = item.LOAIHT_ID
      await this.onChangeLoaiHT()
      this.ChuDe.ID = item.CHUDEHT_ID

      // Thông tin khách hàng
      this.MaKH = item.MA_KH
      this.TenKH = item.TEN_KH
      this.DiaChi = item.DIACHI_KH
      this.MaSoThue = item.MST
      this.NguoiYeuCau = item.NGUOI_YC
      this.SoDT = item.DIENTHOAI_LH
      this.Email = item.EMAIL
      this.NgayHoTro = item.NGAY_HT
      this.NoiDung = item.NOIDUNG_HT
      this.DonViXL.ID = item.DONVI_XL
      this.KetQua.ID = item.KQXL_ID
      this.MucDoHaiLong.ID = item.HAILONG
      this.PhanLoaiKH.ID = item.PHANLOAIKH_ID
      this.HinhThuc.ID = item.HTHT_ID
    },

    async GhiLaiPhieuTiepNhan(data) {
      const rs = await API.GhiLaiPhieuTiepNhan(this.axios, data)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async getDSPhieuHoTro(DonViID) {
      const rs = await this.$root.context.get(`web-cskh/api/tiep-nhan-khach-hang/danh-sach?donViId=${DonViID}&tthtId=5`)
      // console.log('getDSPhieuHoTro = ', rs)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async getDSHinhThuc() {
      const rs = await API.getDSHinhThuc(this.axios)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async getDSMucDoHaiLong() {
      const rs = await API.getDSMucDoHaiLong(this.axios)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async getDSChuDeKhieuNai() {
      const rs = await API.getDSChuDeKhieuNai(this.axios)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async getDSDichVu() {
      const rs = await API.getDSDichVu(this.axios)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async getDSLoaiHinh(dichVuId) {
      const rs = await API.getDSLoaiHinh(this.axios, dichVuId)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async getDSDanhMuc(loaiDanhMuc) {
      const rs = await API.getDSDanhMuc(this.axios, loaiDanhMuc)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async xoaPhieuHoTro(htkhId) {
      const rs = await API.xoaPhieuHoTro(this.axios, htkhId)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async getDSLoaiKH() {
      const rs = await API.getDSLoaiKH(this.axios)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async getThongTinThueBao(MaThueBao) {
      const rs = await API.getThongTinThueBao(this.axios, MaThueBao)
      let result = []
      if (rs.data.data.data.length != 0) result = rs.data.data.data
      return result
    },

    async getThongTinKhachHang(data) {
      const rs = await API.getThongTinKhachHang(this.axios, data)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },

    // async getDSThueBao(khachHang_Id){
    //   const rs = await API.getDSThueBao(this.axios,khachHang_Id);
    //   let result = [];
    //   if (rs.data.data.data.length != 0) result = rs.data.data.data;
    //   return result;
    // },

    async getLichSuBaoHong(thueBaoId) {
      const rs = await API.getLichSuBaoHong(this.axios, thueBaoId)
      let result = []
      if (rs.data.data.data.length != 0) result = rs.data.data.data
      return result
    },

    async getLichSuCKSH(khachHang_Id) {
      const rs = await API.getLichSuCKSH(this.axios, khachHang_Id)
      let result = []
      if (rs.data.data.data.length != 0) result = rs.data.data.data
      return result
    },

    async getLichSuKhieuNai(khachHang_Id) {
      const rs = await API.getLichSuKhieuNai(this.axios, khachHang_Id)
      let result = []
      if (rs.data.data.data.length != 0) result = rs.data.data.data
      return result
    },

    async getLichSuDonHang(khachHang_Id) {
      const rs = await API.getLichSuDonHang(this.axios, khachHang_Id)
      let result = []
      if (rs.data.data.data.length != 0) result = rs.data.data.data
      return result
    },

    async getLichSuHoTro(khachHang_Id) {
      const rs = await API.getLichSuHoTro(this.axios, khachHang_Id)
      let result = []
      if (rs.data.data.data.length != 0) result = rs.data.data.data
      return result
    },
    // async khoaPhieuHoTroKhachHang(data) {
    //   const rs = await this.axios.put(`/web-cskh/api/tiep-nhan-khach-hang/khoa-phieu`, data)
    //   let result = []
    //   console.log('khoaPhieuHoTroKhachHang = ', rs)
    //   if (rs.data.length != 0) result = rs.data
    //   return result
    // },
    async getDSDonViXL(data) {
      const rs = await API.getDSDonViXL(this.axios, data)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async tsbtnTraPhieu_Click() {
      try {
        this.loading(true)
        let data = {
          pHTKHID: this.htkh_id,
          pPHIEUHTID: this.phieuht_id,
          pTTHTID: 5,
          pNoiDung: this.NoiDung
        }
        const rs = await this.$root.context.post('/web-cskh/api/xu-ly-thue-bao/traphieu-hotro', data)
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Thành công!')
        }
      } catch (error) {
        this.$toast.error(error.response.data.message)
      } finally {
        this.DSPhieuHoTro = await this.getDSPhieuHoTro(this.$root.token.getDonViID())
        this.loading(false)
      }
    },

    async onChangeLoaiHT() {
      // console.log('onChangeLoaiHT = ', this.LoaiHoTro.ID)
      if (this.LoaiHoTro.ID) {
        this.ChuDe.DataSouce = await this.getChuDe(this.LoaiHoTro.ID)
      }
    }
  },
  watch: {
    DSPhieuHoTro: {
      handler: function (data) {
        if (data) {
          this.buttonTraPhieu = true
        } else {
          this.buttonTraPhieu = false
        }
      }
    }
  },
  destroyed() {}
}
</script>
