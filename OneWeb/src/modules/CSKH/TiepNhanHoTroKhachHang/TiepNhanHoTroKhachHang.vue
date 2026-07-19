<template src="./TiepNhanHoTroKhachHang.html"></template>
<style src="./TiepNhanHoTroKhachHang.scss"></style>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import select2 from '@/components/Select2.vue'

import { currency } from '@/filters/currency'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import SearchAccountModal from '@/modules/contract/setup/SurveyRequest/popups/SearchAccountModal'
import GiaoPhieuHoTro from '../GiaoPhieuHoTro'
import DiaChiKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ChonDiaChi/DiaChiKhachHangModal.vue'
import Vue from 'vue'
import { L10n, setCulture } from '@syncfusion/ej2-base'
import PopupUploadFile from './popup/ModalUploadFile.vue'
import API from '@/modules/CSKH/api/TiepNhanHoTroKhachHangApi'
import formGiaoPhieuPhoiHop from '@/modules/CSKH/giaophieu-phoihop/index.vue'
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
    formGiaoPhieuPhoiHop,
    GiaoPhieuHoTro,
    DiaChiKhachHangModal,
    PopupUploadFile
  },
  data() {
    return {
      header: {
        title: 'TIẾP NHẬN HỖ TRỢ KHÁCH HÀNG',
        list: [
          { name: 'Chăm sóc khách hàng', link: { name: 'Ui.cards' } },
          {
            name: 'Xử lý thông tin hỗ trợ',
            link: { name: 'Ui.buttons' }
          },
          {
            name: 'Tiếp nhận hỗ trợ khách hàng',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      currentTab: 1,

      Enabled: {
        NhapMoi: true
      },
      Visible:{
        NhapMoi: true,
        GhiLai:true,
        Huy:true,
        XoaPhieu:true,
        GiaoPhieu:true,
        GiaoPhoiHop:true,
        FileDinhKem:true
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
      quytrinh_id: null,

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
      isShowMenu: false,
      isPhieuMoi: true,
      htkh_id: null,
      hdtb_id: null,
      chude_id: null,
      quytrinh_id: 40360,
      thuebao_id: null,
      khachhang_id: null,
      isDiaChiLD: true,
      DSPhieuHoTro: [],
      DSPhieuGiaoPhoiHop: [],
      ttht_id: null,
      flag: false,
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
        vido: null
      },
      temp_pTTPHID: 1,

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
        { fieldName: 'NGAY_TN', headerText: 'Ngày tiếp nhận', allowFiltering: true, allowHtml: true }
      ],

      DSThueBao: [],
      columnsDSThueBao: [
        { fieldName: 'MA_TB', headerText: 'Mã thuê bao', allowFiltering: true },
        { fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true },
        { fieldName: 'DIACHI_LD', headerText: 'Địa chỉ', allowFiltering: true },
        { fieldName: 'DICHVU_VT', headerText: 'Dịch vụ', allowFiltering: true },
        { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true },
        { fieldName: 'SO_DIENTHOAI', headerText: 'Số điện thoại', allowFiltering: true },
        { fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái TB', allowFiltering: true }
      ],

      DSLichSuHoTro: [],
      columnsDSLichSuHoTro: [
        { fieldName: 'STT', headerText: 'STT', allowFiltering: true },
        { fieldName: 'NGAY_TN', headerText: 'Ngày tiếp nhận', allowFiltering: true },
        { fieldName: 'MA_HT', headerText: 'Mã hỗ trợ', allowFiltering: true },
        { fieldName: 'LOAI_HT', headerText: 'Loại Hỗ trợ', allowFiltering: true },
        { fieldName: 'CHUDE_HT', headerText: 'Chủ đề', allowFiltering: true },
        { fieldName: 'YC_HOTRO', headerText: 'Yêu cầu HT', allowFiltering: true },
        { fieldName: 'KETQUA_XL', headerText: 'Kết quả XL', allowFiltering: true }
      ],

      DSLichSuBaoHong: [],
      columnsDSLichSuBaoHong: [
        { fieldName: 'LOAI_PHIEU', headerText: 'Loại phiếu', allowFiltering: true },
        { fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true },
        { fieldName: 'TEN_TB', headerText: 'Tên TB', allowFiltering: true },
        { fieldName: 'DIACHI_LD', headerText: 'Địa chỉ LĐ', allowFiltering: true },
        { fieldName: 'TINH_TRANG', headerText: 'Trạng thái', allowFiltering: true },
        { fieldName: 'NGAY_BH', headerText: 'Ngày báo hỏng', allowFiltering: true },
        { fieldName: 'NGAY_HT', headerText: 'Ngày nghiệm thu', allowFiltering: true },
        { fieldName: 'NGUOI_BH', headerText: 'Người báo hỏng', allowFiltering: true },
        { fieldName: 'TINH_TRANG', headerText: 'Tình trạng', allowFiltering: true },
        { fieldName: 'TRANGTHAI_BH', headerText: 'Nghiệm thu', allowFiltering: true },
        { fieldName: 'GHICHU_XL', headerText: 'Ghi chú', allowFiltering: true },
        { fieldName: 'NGUOI_TAO', headerText: 'Người tạo', allowFiltering: true }
      ],

      DSKhieuNai: [],
      columnsDSKhieuNai: [
        { fieldName: 'TENCHUDE', headerText: 'Chủ đề khiếu nại', allowFiltering: true },
        { fieldName: 'NGAY_TN', headerText: 'Ngày tiếp nhận', allowFiltering: true },
        { fieldName: 'ND_KHIEUNAI', headerText: 'Nội dung KN', allowFiltering: true },
        { fieldName: 'NHANVIEN_TN', headerText: 'Người tiếp nhận', allowFiltering: true },
        { fieldName: 'NGAY_GQ', headerText: 'Ngày giải quyết', allowFiltering: true },
        { fieldName: 'NHANVIEN_GQ', headerText: 'Người giải quyết', allowFiltering: true },
        { fieldName: 'MA_KN', headerText: 'Mã khiếu nại', allowFiltering: true },
        { fieldName: 'TRANGTHAI_KN', headerText: 'Trạng thái KN', allowFiltering: true }
      ],
      DSLichSuCSKH: [],
      columnsDSLichSuCSKH: [
        { fieldName: 'STT', headerText: 'STT', allowFiltering: true },
        { fieldName: 'NGAY_CSKH', headerText: 'Ngày CSKH', allowFiltering: true },
        { fieldName: 'MA_CSKH', headerText: 'Mã chăm sóc', allowFiltering: true },
        { fieldName: 'MA_KH', headerText: 'Mã khách hàng', allowFiltering: true },
        { fieldName: 'TEN_CT', headerText: 'Chương trình CSKH', allowFiltering: true },
        { fieldName: 'NGUOI_TH', headerText: 'Ngày thực hiện', allowFiltering: true },
        { fieldName: 'NGUOI_DUOC_CS', headerText: 'Người được CS', allowFiltering: true },
        { fieldName: 'TRANGTHAI', headerText: 'Trạng thái phiếu', allowFiltering: true }
      ],

      DSLichSuDonHang: [],
      columnsDSLichSuDonHang: [
        { fieldName: 'STT', headerText: 'STT', allowFiltering: true },
        { fieldName: 'NGAY_YC', headerText: 'Ngày lên đơn', allowFiltering: true },
        { fieldName: 'MA_KH', headerText: 'Mã khách hàng', allowFiltering: true },
        { fieldName: 'MA_DH', headerText: 'Mã đơn hàng', allowFiltering: true },
        { fieldName: 'LOAIHINH_TB', headerText: 'Dịch vụ', allowFiltering: true },
        { fieldName: 'GOI_DV', headerText: 'Gói dịch vụ', allowFiltering: true },
        { fieldName: 'DIACHI_LD', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
        { fieldName: 'TRANGTHAI_HD', headerText: 'Trạng thái đơn', allowFiltering: true },
        { fieldName: 'DIACHI_KH', headerText: 'Địa chỉ khách hàng', allowFiltering: true }
      ]
    }
  },
  computed: {},
  async created() {
    try {
      this.loading(true)      
      let thueBaoId = this.$route.query.thueBaoId != null ? this.$route.query.thueBaoId.trim() : ""
      this.DichVu.DataSouce = await this.getDSDichVu()
      this.KenhTiepNhan.DataSouce = await this.getDSDanhMuc('HINHTHUC_KN')
      this.LoaiHoTro.DataSouce = await this.getDSDanhMuc('NHOM_HT')
      this.KetQua.DataSouce = await this.getDSDanhMuc('KETQUA_HTKH')
      this.MucDoHaiLong.DataSouce = await this.getDSDanhMuc('MUCDO_HL')
      this.HinhThuc.DataSouce = await this.getDSHinhThuc()
      this.PhanLoaiKH.DataSouce = await this.getDSLoaiKH()
      if(thueBaoId == "" ){
        this.DSPhieuHoTro = await this.getDSPhieuHoTro({
          donViId: this.$root.token.getDonViID(),
          tthtId: 1
        })       
      }else{
        await this.MaThueBao_KeyPress(thueBaoId,1)
        this.Enabled.NhapMoi = false
      }      
    } catch (e) {
      this.$toast.error(e.data.message)
    } finally {
      this.loading(false)
    }
  },
  async mounted() {},
  methods: {
    modalFormGiaoPhieuPhoiHopRef_Shown: async function (args) {
      //Khi mở popup cần cài đặt phiếu HT ID
      await this.$refs.formGiaoPhieuPhoiHopRef.bindData(this.htkh_id)
    },
    modalFormGiaoPhieuPhoiHopRef_Hidden: async function (args) {
      if (this.flag) {
        await this.TimKiem()
      }
    },
    formGiaoPhieuPhoiHopRef_dataDeleted: async function (args) {
      //Khi phiếu bị hủy, cần đóng popup
      await this.$refs.modalFormGiaoPhieuPhoiHopRef.hide()
    },
    formGiaoPhieuPhoiHopRef_dataChanged: function (args) {
      //Khi dữ liệu thay đổi có thay đổi trên POPUP
      this.flag = true
    },
    async GiaoPhieu() {
      try{
        if (this.ttht_id == null) {
          this.$toast.error('Chưa chọn phiếu hổ trợ!')
          return
        }
        let kiemtra = await this.kiemTraChuDeGiaoPhieu(this.chude_id)
        this.$refs.frmGiaoPhieuHoTro.openDialog()
      }catch(e){
        this.$toast.error(e.data.message)
      }
    },    
    async closeGiaoPhieu() {
      await this.TimKiem()
    },

    async changeTab(phieuMoi){    
      this.isPhieuMoi = phieuMoi
      this.Visible.NhapMoi = phieuMoi
      this.Visible.GhiLai = phieuMoi
      this.Visible.Huy = phieuMoi
      this.Visible.XoaPhieu = phieuMoi
      this.Visible.GiaoPhieu = phieuMoi
      this.Visible.GiaoPhoiHop = phieuMoi
      this.Visible.FileDinhKem = phieuMoi      
      this.clearAll()
      this.DSPhieuHoTro = []
      this.DSPhieuGiaoPhoiHop = []
      if(this.isPhieuMoi){
        this.DSPhieuHoTro = await this.getDSPhieuHoTro({
          donViId: this.$root.token.getDonViID(),
          tthtId: 1
        })   
      }else{
        this.DSPhieuGiaoPhoiHop = await this.getDSPhieuHoTro({
          donViId: this.$root.token.getDonViID(),
          tthtId: 0
        })   
      }    
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
          hailong: this.MucDoHaiLong.ID
        }
        const rs = await this.axios.put(`/web-cskh/api/tiep-nhan-khach-hang/khoa-phieu`, data)
        this.Enabled.NhapMoi = this.DSPhieuHoTro.length > 0 ? true : false
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Khóa phiếu thành công!')
        }
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        if(this.isPhieuMoi){
          this.DSPhieuHoTro = await this.getDSPhieuHoTro({
            donViId: this.$root.token.getDonViID(),
            tthtId: 1
          })   
        }else{
          this.DSPhieuGiaoPhoiHop = await this.getDSPhieuHoTro({
            donViId: this.$root.token.getDonViID(),
            tthtId: 0
          })   
        }            
        this.loading(false)
      }
    },
    redirectLink(i){
      const BASE_URL = window.location.origin
      let hash = ""
      if(i == 1){
        hash = "#/contract/InstallNewSubs"
      }else if(i == 2){
        hash = "#/incident/ReceiveIncident"
      }else if(i == 3){
        hash = "#/ext-khieunai/tiep-nhan-khieu-nai"
      }else if(i == 4){
        hash = "#/htkh/TiepNhanDonHang"
      }

      // console.log(this.$route , " = router")
      // let Link = this.$router.resolve({path: 'htkh/TiepNhanDonHang', query: {}})
      window.open(BASE_URL + hash, '_blank'); 
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
    async NhomHoTroChanged() {
      if (this.LoaiHoTro.ID == null) {
        return
      }
      this.ChuDe.ID = null
      this.ChuDe.DataSouce = await this.getDSDanhMucChuDe({
        loaiDanhMuc: 'CHUDE_HOTRO',
        loaiHoTro: this.LoaiHoTro.ID
      })
    },
    async ChuDeChanged() {
      if (this.DichVu.ID == null || this.ChuDe.ID == null) {
        return
      }
      let CheckChuDe = this.ChuDe.DataSouce.filter((e) => e.CHUDEHT_ID == this.ChuDe.ID)
      if (CheckChuDe.length > 0 && CheckChuDe[0].CAP == 0) {
        this.ChuDe.ID = null
        return
      }
      this.DonViXL.DataSouce = await this.getDSDonViXL({
        chuDe_Id: this.ChuDe.ID,
        dichVu_Id: this.DichVu.ID
      })
    },
    NhapMoi() {
      this.khachhang_id = null
      this.thuebao_id = null
      this.clearPhieuHoTro()
      this.clearThongTinKhachHang()
      this.Enabled.NhapMoi = false
    },
    async ValidateGhilai() {
      if (this.KenhTiepNhan.ID == null) {
        this.$toast.error('Chưa chọn kênh tiếp nhận!')
        return false
      }

      if (this.NgayTiepNhan == null) {
        this.$toast.error('Chưa nhập ngày tiếp nhận!')
        return false
      }

      if (this.LoaiHoTro.ID == null) {
        this.$toast.error('Chưa chọn loại hổ trợ!')
        return false
      }

      if (this.ChuDe.ID == null) {
        this.$toast.error('Chưa chọn chủ đề!')
        return false
      }

      if (this.YeuCauHT == null || this.YeuCauHT.trim() == '') {
        this.$toast.error('Chưa nhập yêu cầu hổ trợ!')
        return false
      }

      // if(this.MaKH == null || this.MaKH.trim() == ""){
      //   this.$toast.error("Chưa nhập mã khách hàng!")
      //   return false
      // }

      if (this.TenKH == null || this.TenKH.trim() == '') {
        this.$toast.error('Chưa nhập tên khách hàng!')
        return false
      }

      if (this.DiaChi == null || this.DiaChi.trim() == '') {
        this.$toast.error('Chưa nhập địa chỉ!')
        return false
      }

      if (this.NguoiYeuCau == null || this.NguoiYeuCau.trim() == '') {
        this.$toast.error('Chưa nhập người yêu cầu!')
        return false
      }

      if (this.SoDT == null || this.SoDT.trim() == '') {
        this.$toast.error('Chưa nhập số điện thoại!')
        return false
      }

      if (this.NgayHoTro == null) {
        this.$toast.error('Chưa nhập ngày hổ trợ!')
        return false
      }

      // if(this.KetQua.ID == null ){
      //   this.$toast.error("Chưa chọn kết quả!")
      //   return false
      // }

      // if(this.HinhThuc.ID == null ){
      //   this.$toast.error("Chưa chọn hình thức!")
      //   return false
      // }

      return true
    },
    f_StringToDate(value, format = 'DD/MM/YYYY') {
      return moment(value, format).toDate()
    },
    f_DateTimeToString: function (value, format = "DD/MM/YYYY HH:mm:ss") {
      return moment(value).format(format);
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
    async ClosePopup(data) {
      this.$bvModal.hide('popupComponents')
      if (data) {
        await this.MaThueBao_KeyPress(data.ma_tb,2)
      }
    },
    ShowGiaoPhieuPhoiHop() {
      if (this.htkh_id == null) {
        this.$toast.error('Chưa chọn phiếu hổ trợ!')
        return
      }
      this.$refs.formGiaoPhieuPhoiHop.openDialog()
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
          thuebao_id: this.thuebao_id,
          ma_tb: this.MaThueBao,
          dichvuvt_id: this.DichVu.ID,
          loaitb_id: this.LoaiHinh.ID,
          ten_tb: this.TenThueBao,
          diachi_ld: this.DiaChiLD,
          diachild_id: 0,
          httn_id: this.KenhTiepNhan.ID,
          ngay_tn: this.f_DateTimeToString(this.NgayTiepNhan),
          loaiht_id: this.LoaiHoTro.ID,
          chudeht_id: this.ChuDe.ID,
          yc_hotro: this.YeuCauHT,
          khachhang_id: this.khachhang_id,
          phanloaikh_id: this.PhanLoaiKH.ID, // chưa đúng
          ma_kh: this.MaKH,
          ten_kh: this.TenKH,
          diachi_kh: this.DiaChi,
          diachikh_id: 0,
          mst: this.MaSoThue,
          nguoi_yc: this.NguoiYeuCau,
          dienthoai_lh: this.SoDT,
          email: this.Email,
          ngay_ht: this.f_DateToString(this.NgayHoTro),
          htht_id: this.HinhThuc.ID,
          kqxl_id: this.KetQua.ID,
          hailong: this.MucDoHaiLong.ID,
          noidung_ht: this.NoiDung
        }
        let KetQua = await this.GhiLaiPhieuTiepNhan(data)
        let message = this.Enabled.NhapMoi ? 'Cập nhật thành công!' : 'Thêm mới thành công'
        this.DSPhieuHoTro = await this.getDSPhieuHoTro({
          donViId: this.$root.token.getDonViID(),
          tthtId: 1
        })        
        this.Enabled.NhapMoi = this.DSPhieuHoTro.length > 0 ? true : false
        this.$toast.success(message)
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    async Huy() {
      try {
        this.Enabled.NhapMoi = true
        this.DSPhieuHoTro = await this.getDSPhieuHoTro({
          donViId: this.$root.token.getDonViID(),
          tthtId: 1
        })         
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    async MaThueBao_KeyPress(ThueBao,loai) {
      try {
        this.loading(true)
        if (ThueBao == null || ThueBao.trim() == '') {
          this.$toast.error('Chưa nhập thuê bao!')
          return
        }
        this.clearPhieuHoTro()
        // this.MaThueBao = ThueBao
        let KetQua = await this.getThongTinThueBao({
          thueBao :ThueBao,
          loai : loai
        })
       
        if (KetQua.length == 0) {
          return
        }
        this.MaThueBao = KetQua[0].MA_TB
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
        if (MaKH == null || MaKH.trim() == '') {
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
        this.loading(true)
        if(this.isPhieuMoi){
          this.DSPhieuHoTro = await this.getDSPhieuHoTro({
            donViId: this.$root.token.getDonViID(),
            tthtId: 1
          })   
          this.Enabled.NhapMoi = this.DSPhieuHoTro.length > 0 ? true : false
        }else{
          this.DSPhieuGiaoPhoiHop = await this.getDSPhieuHoTro({
            donViId: this.$root.token.getDonViID(),
            tthtId: 0
          })   
        }            
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    async TimKiemLichSu() {
      try {
        this.loading(true)
        this.clearTab()
        if (this.khachhang_id == null) {
          this.$toast.error('Không có thông tin khách hàng!')
        } else {
          this.DSThueBao = await this.getDSThueBao(this.khachhang_id)
          this.DSLichSuCSKH = await this.getLichSuCKSH(this.khachhang_id)
          this.DSKhieuNai = await this.getLichSuKhieuNai(this.khachhang_id)
          this.DSLichSuDonHang = await this.getLichSuDonHang(this.khachhang_id)
          this.DSLichSuHoTro = await this.getLichSuHoTro(this.khachhang_id)
        }
        if (this.thuebao_id == null) {
          this.$toast.error('Không có thông tin thuê bao!')
        } else {
          this.DSLichSuBaoHong = await this.getLichSuBaoHong(this.thuebao_id)
        }
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    ChonDiaChiLD() {
      this.isDiaChiLD = true
      this.$refs.diaChiKhachHangModal.showModal()
    },
    ChonDiaChiKH() {
      this.isDiaChiLD = false
      this.$refs.diaChiKhachHangModal.showModal()
    },
    acceptDiaChiKH(data) {
      this.isDiaChiLD ? (this.DiaChiLD = data.diachi) : (this.DiaChi = data.diachi)
    },

    async XoaPhieu() {
      try {
        if (this.htkh_id == null) {
          this.$toast.error('Chưa chọn phiếu để xóa!')
          return
        }

        let isConfirm = false
        await this.$bvModal
          .msgBoxConfirm('Bạn có chắc chắn muốn xóa phiếu này không!', {
            title: 'Thông báo',
            centered: true,
            size: 'md',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then((value) => {
            if (value) {
              isConfirm = true
            }
          })
        if (!isConfirm) {
          return
        }
        let KetQua = await this.xoaPhieuHoTro(this.htkh_id)
        
        this.DSPhieuHoTro = await this.getDSPhieuHoTro({
          donViId: this.$root.token.getDonViID(),
          tthtId: 1
        })        
        this.Enabled.NhapMoi = this.DSPhieuHoTro.length > 0 ? true : false
        this.$toast.success('Xóa phiếu thành công!')
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        this.loading(false)
      }
    },
    clearTab() {
      this.DSThueBao = []
      this.DSLichSuCSKH = []
      this.DSKhieuNai = []
      this.DSLichSuDonHang = []
      this.DSLichSuHoTro = []
      this.DSThueBao = []
    },
    clearPhieuHoTro() {
      this.MaThueBao = ''
      this.MaHoTro = ''
      this.quytrinh_id = null
      this.TenThueBao = ''
      this.DiaChiLD = ''
      this.YeuCauHT = ''
      this.NgayTiepNhan = new Date()
      this.DichVu.ID = null
      this.LoaiHinh.ID = null
      this.KenhTiepNhan.ID = null
      this.LoaiHoTro.ID = null
      this.ChuDe.ID = null
      this.ChuDe.DataSouce = []
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
      this.chude_id = null
      this.ttht_id = null
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
      this.ttht_id = item.TTHT_ID
      this.htkh_id = item.HTKH_ID
      this.thuebao_id = item.THUEBAO_ID
      this.khachhang_id = item.KHACHHANG_ID
      this.quytrinh_id = item.QUYTRINH_ID
      this.chude_id = item.CHUDEHT_ID
      // Phiếu Hổ Trợ
      this.MaThueBao = item.MA_TB
      this.MaHoTro = item.MA_HT
      
      this.TenThueBao = item.TEN_TB
      this.DiaChiLD = item.DIACHI_LD
      this.YeuCauHT = item.YC_HOTRO
      
      this.NgayTiepNhan = this.f_StringToDate(item.NGAY_TN,'DD-MM-YYYY HH:mm:ss')

      this.DichVu.ID = item.DICHVUVT_ID
      await this.DichVuChanged()
      this.LoaiHinh.ID = item.LOAITB_ID 
      this.KenhTiepNhan.ID = item.HTTN_ID
      this.LoaiHoTro.ID = item.LOAIHT_ID
      await this.NhomHoTroChanged()
      this.ChuDe.ID = item.CHUDEHT_ID
      
      // Thông tin khách hàng
      this.MaKH = item.MA_KH
      this.TenKH = item.TEN_KH
      this.DiaChi = item.DIACHI_KH
      this.MaSoThue = item.MST
      this.NguoiYeuCau = item.NGUOI_YC
      this.SoDT = item.DIENTHOAI_LH
      this.Email = item.EMAIL
      this.NgayHoTro = this.f_StringToDate(item.NGAY_HT)
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
    async getDSPhieuHoTro(data) {
      const rs = await API.getDSPhieuHoTro(this.axios, data)
      let result = []
      if (rs.data.data.data.length != 0) result = rs.data.data.data
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
    async getDSDanhMucChuDe(data) {
      const rs = await API.getDSDanhMucChuDe(this.axios, data)
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
    async getThongTinThueBao(data) {
      const rs = await API.getThongTinThueBao(this.axios, data)
      let result = []     
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },

    async getThongTinKhachHang(data) {
      const rs = await API.getThongTinKhachHang(this.axios, data)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },

    async getDSThueBao(khachHang_Id) {
      const rs = await API.getDSThueBao(this.axios, khachHang_Id)
      let result = []
      if (rs.data.data.data.length != 0) result = rs.data.data.data
      return result
    },

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
    async khoaPhieuHoTroKhachHang(data) {
      const rs = await API.khoaPhieuHoTroKhachHang(this.axios, data)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async getDSDonViXL(data) {
      const rs = await API.getDSDonViXL(this.axios, data)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    async kiemTraChuDeGiaoPhieu(data) {
      const rs = await API.kiemTraChuDeGiaoPhieu(this.axios, data)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    }
  },
  watch: {},
  destroyed() {}
}
</script>
