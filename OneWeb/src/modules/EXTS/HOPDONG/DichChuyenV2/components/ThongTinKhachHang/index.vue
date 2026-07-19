<template src="./index.html">
</template>
<script>
import Enum from '../../Enum'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import DateTimeLib from '@/utils/DateTimeLib'
import Vue from 'vue'
import ModalChonDiaChi from '../../../../../CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonDiaChi/Modal'
import ChiTietNganhNghe from '../../../ThayDoiThongTinKhuyenMai/ChiTietNganhNghe'
import ChonDonVi from '../ThongTinKhachHang/ChonDonVi'
import LoaiKH from '../../../ThayDoiThongTinKhuyenMai/LoaiKH'
import ThongTinKHBoSung from '../../../ThayDoiThongTinKhuyenMai/ThongTinKHBoSung'
import PopupTimKiemNhanVienThuCuoc from '../../../ThayDoiThongTinKhuyenMai/TimNhanVienThuCuoc'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'

Vue.use(DialogPlugin)
const VueInputMask = require('vue-inputmask').default
Vue.use(VueInputMask)
export default {
  name: 'ThongTinKhachHang',
  props: ['parent'],
  components: {ModalChonDiaChi,
    ChiTietNganhNghe,
    ChonDonVi,
    LoaiKH,
    ThongTinKHBoSung,
    PopupTimKiemNhanVienThuCuoc,
    BssRequiredMarker
  },
  data: function () {
    return {
      dsDanhMuc: [],
      glbDanhMuc_Chung: [],
      dsNganHang: [],

      /***
       * Thông tin khách hàng
       */
      txtMaKH: null,
      txtTenKH: null,
      txtDiaChiKH: null,

      dsGiayToKH: [],
      cboGiayToKH: null,

      txtSoGT: null,
      txtNgayCap: null,
      txtNoiCap: null,
      txtNgayHetHan: null,
      txtNgaySN: null,
      lblNgaySN: 'Ngày sinh ',

      dsQuocTich: [],
      cboQuocTich: null,
      isEnabledCboQuocTich: true,

      dsNganhNgheLoc: [],
      cboNganhNgheLoc: null,
      isEnableCboNganhNgheLoc: true,

      dsDanToc: [],
      cboDanToc: null,
      isEnabledCboDanToc: true,

      txtGiayToKem: null,

      dsLoaiKH: [],
      cboLoaiKH: null,
      vkieukh_id_: 0, // biến toàn cục kiểm tra KH cá nhân hay doanh nghiệp

      dsPhanLoaiKH: [],
      txtPhanLoaiKH: null,

      txtNguoiDD: null,
      txtChucDanhDD: null,
      txtDiaChiNDD: null,
      txtTelKH: null,
      txtFaxKH: null,
      txtHoKhau: null,
      txtNoiCapHK: null,
      txtNgayCapHK: null,

      chkNganHangKH: false,
      cboNganHangKH: null,

      txtMaSTKH: null,
      txtSoTKKH: null,
      txtGhiChuKH: null,

      rdoGioiTinh: 0,
      enableRdoGTNone: true,
      enableRdoGTNam: true,
      enableRdoGTNu: true,

      // DSKieu_HD:[],
      // cboKieu_HD:null,
      /***
       * Thông tin thanh toán
       */
      txtMaTT: null,
      dsMaDD: [],
      cboMaDD: null,
      txtTenTT: null,
      txtDiaChiTT: null,
      txtDiaChiCT: null,
      txtDiaChiBC: null,
      txtMaIG: null,
      dsDonViQL: [],
      cboDonViQL: null,
      cboNganHangTT: null,
      enableCboNganHangTT: false,
      dsHTTT: [],
      cboHTTT: null,
      txtMaNVTC: null,
      txtMaTuyen: null,
      txtSTKTT: null,
      txtChu_TK: null,
      txtMST: null,
      txtGhiChuTT: null,
      txtNhanVienQL: null,
      txtMaTBDD: null,
      /***
       * Tham số mặc định
       */
      nhap_nvkd: 0,
      phantap_kh: false,
      check_chuahoa_cntt: false,
      ts_kt_mst: false,
      ht_loc_cbo_tram: false,
      khong_batbuoc_ctv: 0,
      batbuoc_gioithieu_dichchuyen: 0,
      tudong_ganthem_hdtb_dv: false,
      DONVIQL_HTTT: 0,
      TUYENTHU_HTTT: 0,
      khong_ht_tracuudb: 0,
      khongcho_chon_khuvuc: false,
      ts_sinhma_gd_theo_donvi: false,
      batbuoc_sdt: false,
      ts_kt_mstmns_kh: 0,
      ts_kt_tthdkhdn: 0,
      ktratt: 0,
      batbuoc_chon_dacdiem_dcld: false,
      kt_fiber_colapchung: false,
      active_quytrinh_xacminh_ttlh: false,
      /***
       * Biến chung
       */
      dsDK_DVK: [],
      dsHDKH_Sub: [],
      dsHDKH: [],
      dsHDTT: [],
      dsHDTT_NV: [],
      dsDiaChi: [],
      dsDiaChiHDKH: [],
      dsDiaChiHDTT: [],

      lvDvkhac: [],
      lvDvkhac_TT: [],
      lvwThanhToan: [],

      hdtb_id: null,
      hdkh_id: null,
      hdtt_id: null,
      hdkh_cha_id: null,
      thanhtoan_id: 0,
      khachhang_id: null,
      hdmig_id: 0,
      mig_id: 0,
      nhanvientc_id: 0,
      tuyenthu_id: 0,
      matb_dd: null,
      dtpNgayYC: new Date(),

      pt_doanhthu: 0,
      pt_diem_tn: 0,

      TNC1_ID: 0,
      TNC2_ID: 0,
      TNC3_ID: 0,
      TNC4_ID: 0,
      nganhnghe_id: 0,

      PHANLOAIKH_ID: null,
      nhomplkh: null,
      VPHIEUCSKH_ID: null,

      // TuấnNA thêm khai báo cho nút ngân sách nhà nước --- Ngày 12/11/2010
      // Bổ sung Tien_dm ngày 16.03.2011
      DV_QHNS: null,
      MACHUONG: null,
      MN_KT: null,
      MA_NDKT: null,
      MN_NSNN: null,
      TIEN_DM: null,
      // Địa chỉ khách hàng
      diachikh_id: null,
      dacdiemkh_id: null,
      tinhkh_id: null,
      quankh_id: null,
      phuongkh_id: null,
      phokh_id: null,
      khukh_id: null,
      apkh_id: null,
      sonhakh: null,
      blockkh: null,
      tangkh: null,
      sophongkh: null,
      motadckh: null,
      Lat_KH: null,
      Lng_KH: null,
      // Địa chỉ người đại diện
      tinhndd_id: null,
      quanndd_id: null,
      phuongndd_id: null,
      phondd_id: null,
      apndd_id: null,
      khundd_id: null,
      dacdiemndd_id: null,
      sonhandd: null,
      Lat_DD: null,
      Lng_DD: null,
      // Địa chỉ thanh toán
      diachitt_id: null,
      tinhtt_id: null,
      quantt_id: null,
      phuongtt_id: null,
      phott_id: null,
      aptt_id: null,
      khutt_id: null,
      dacdiemtt_id: null,
      sonhatt: null,
      blocktt: null,
      tangtt: null,
      sophongtt: null,
      motadctt: null,
      Lat_TT: null,
      Lng_TT: null,
      // Địa chỉ chứng từ
      tinhct_id: null,
      quanct_id: null,
      phuongct_id: null,
      phoct_id: null,
      apct_id: null,
      khuct_id: null,
      dacdiemct_id: null,
      sonhact: null,
      // Địa chỉ báo cước
      diachibc_id: null,
      tinhbc_id: null,
      quanbc_id: null,
      phuongbc_id: null,
      phobc_id: null,
      dacdiembc_id: null,
      apbc_id: null,
      khubc_id: null,
      sonhabc: null,
      Lat_BC: null,
      Lng_BC: null,

      kieukh_canhan: false, // true: Cá nhân, false: Doanh nghiệp
      load_ds_nvtc: true,
      load_ds_donvi: true,

      kt_load: true,
      nhanvien_ql_am_id: 0,

      // input form DiaChi
      diachi: {},
      diachi_flag: null,
      showThongTinThem: false,
      // input form ChiTietNganhNghe
      objChiTietNganhNghe: {},
      // input form LoaiKH
      objLoaiKH: {},
      // input form TTKHBoSung
      objTTKHBoSung: {},
      // input frmTraCuuNVTC
      inputObjFrmTraCuuNVTC: {}
    }
  },
  async created () {
    await this.frmChuyenDich_Load()
  },
  computed: {
    ThongTinThueBao () {
      // return this.$parent.$children[1]
      try {
        console.log('🚀 ~ file: index.vue:289 ~ ThongTinThueBao ~ this.$parent', this.$parent)
      } catch (error) {
        console.log('🚀 ~ file: index.vue:290 ~ ThongTinThueBao ~ error', error)
      }
      return this.parent.$refs.ThongTinThueBao
    }
    // inputObjFrmTraCuuNVTC() {
    //   return {
    //     QUAN_ID: this.quantt_id,
    //     PHUONG_ID: this.phuongtt_id,
    //     PHO_ID: this.phott_id,
    //     AP_ID: this.aptt_id,
    //     KHU_ID: this.khutt_id,
    //     DACDIEM_ID: this.dacdiemtt_id,
    //     NHANVIENTC_ID: this.nhanvientc_id,
    //     TUYENTHU_ID: this.tuyenthu_id,
    //     DONVIQL_ID: this.donviql_id,
    //     KT_KYLAI: true,
    //   }
    // }
  },
  methods: {
    /***
     * Load thông tin khởi tạo
     * @returns {Promise<void>}
     */
    async frmChuyenDich_Load () {
      try {
        // this.loading(true)
        // Lấy thông tin danh mục
        await this.lay_danhmuc_chuyendich_tt_chung_v2()
        // await this.lay_dulieu_combobox()

        await this.HT_NganhNgheLoc_Combobox()
        this.kt_load = false
        // Lấy các tham số mặc đinh
        // let ds = await this.lay_thamso_macdinh(0)
        let ds = this.$parent.thamso_md
        let det = []
        let str_vtemp = null
        let row = null
        if (ds.length > 0) {
          // Tham số bắt buộc nhập nhân viên địa bàn
          str_vtemp = 'NHANVIEN_DIABAN_DC'
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.nhap_nvkd = 1 }
          }
          str_vtemp = 'CHECK_CHUANHOA_CNTT' // hoangpkn : 18/03/2020. Check thue bao CNTT can chuan hoa
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.check_chuahoa_cntt = true }
          }
          str_vtemp = 'PHANTAP_KH' // hoangpkn : 23/10/2020. Tham số phân tập khách hàng
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts === '1') {
              this.phantap_kh = true
            }
          }
          str_vtemp = 'KIEMTRA_MST' // hoangpkn : 21/07/2018. Tham số kiểm tra mã số thuế
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.ts_kt_mst = 1 }
          }
          str_vtemp = 'HT_LOC_CBO_TRAM' // hoangpkn : 11/03/2019. Lọc bớt dữ liệu combo Trạm trên dynamic
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.ht_loc_cbo_tram = true }
          }
          // THam số ko bắt buộc phải nhập cộng tác viên
          str_vtemp = 'KHONGBATBUOC_CTV'
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.khong_batbuoc_ctv = 1 }
          }
          // THam số ko bắt buộc phải nhập cộng tác viên
          str_vtemp = 'BATBUOC_GIOITHIEU_DICHCHUYEN'
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.batbuoc_gioithieu_dichchuyen = 1 }
          }

          str_vtemp = 'TUDONG_GANTHEM_HDTB_DV' // hoangpkn : 21/01/2019. Tự động insert thêm vào hdtb_dv ( phục vụ HNI )
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row['ten_ts'] == '1') { this.tudong_ganthem_hdtb_dv = true }
          }

          // Tham số lấy đơn vị quản lý theo điều kiện hình thức thanh toán
          str_vtemp = 'DONVIQL_HTTT'
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.DONVIQL_HTTT = 1 }
          }

          // Tham số lấy tuyến thu theo hình thức thanh toán -- Ha loi
          str_vtemp = 'TUYENTHU_HTTT'
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.TUYENTHU_HTTT = 1 }
          }

          str_vtemp = 'KHONG_HIENTHI_TRACUU_DB_DICHCHUYEN'
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.khong_ht_tracuudb = 1 }
          }

          str_vtemp = 'KHONGCHO_CHON_KHUVUC'
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') {
              this.khongcho_chon_khuvuc = true
              // btnMaNVKD.Enabled = false;
            }
          }

          this.ts_sinhma_gd_theo_donvi = ds.filter(x => x.ma_ts == 'SINH_MA_GD_THEO_DV').length > 0

          // 04-02-2020 AnhDt: Bắt buộc nhập sđt
          str_vtemp = 'KIEMTRA_SDT'
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') {
              // lblRequiredTel.Visible = true;
              this.batbuoc_sdt = true
            } else {
              // lblRequiredTel.Visible = false;
              this.batbuoc_sdt = false
            }
          }
          // End AnhDt
          const body = { 'ma_ts': 'GUI_YEUCAU_PHEDUYET_TTTT_KH' }
          let res = await this.$root.context.post('/web-hopdong/lapdatmoi/lay_ds_thamso_md_mats', body)
          if (res && res.data && res.data.length > 0) {
            const data = res.data[0]
            if (data.ten_ts && data.ten_ts == '1') {
              this.active_quytrinh_xacminh_ttlh = true
            } else {
              this.active_quytrinh_xacminh_ttlh = false
            }
          } else {
            this.active_quytrinh_xacminh_ttlh = false
          }
          str_vtemp = 'KIEMTRA_MSTMNS_KHDN' // hoangpkn : 21/07/2018. Tham soos kieemr tra max soos thuee
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.ts_kt_mstmns_kh = 1 }
          }

          str_vtemp = 'THEM_TT_HDKHDN' // hoangpkn : 21/07/2018. Tham soos kieemr tra max soos thuee
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.ts_kt_tthdkhdn = 1 }
          }

          str_vtemp = 'KTRA_TRANGTHAI_DICHCHUYEN'
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            row = det[0]
            if (row.ten_ts == '1') { this.ktratt = 1 }
          }

          // KhaiVK: bắt buộc chọn đặc điểm địa chỉ lắp đặt y/c HPG
          str_vtemp = 'BATBUOC_CHON_DACDIEM_DCLD'
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            let row = det[0]
            if (row.ten_ts == '1') { this.batbuoc_chon_dacdiem_dcld = true } else { this.batbuoc_chon_dacdiem_dcld = false }
          }

          str_vtemp = 'KT_DICHCHUYEN_FIBER_COLAPCHUNG'
          det = ds.filter(x => x.ma_ts == str_vtemp)
          if (det.length > 0) {
            let row = det[0]
            if (row.ten_ts == '1') { this.kt_fiber_colapchung = true }
          }
        }
      } catch (e) {

      } finally {
        // this.loading(false)
      }
    },

    /***
     * Hiển thị thông tin hợp đồng khách hàng
     * @param ds
     * @returns {Promise<void>}
     */
    async HienThiTTHopDongKH (ds) {
      try {
        ds = this.LowerCaseData(ds)
        console.log('🚀 ~ file: index.vue:494 ~ HienThiTTHopDongKH ~ ds:', ds)
        if (ds != null && ds.length) {
          let item = ds[0]
          this.hdkh_id = item.hdkh_id
          this.txtMaKH = item.ma_kh
          this.dtpNgayYC = item.ngay_yc
          item.khachhang_id != null ? this.khachhang_id = item.khachhang_id : this.khachhang_id = 0
          if (this.phantap_kh) {
            let ds_dt_dtn = await this.phantap_laydoanhthu_dtn(this.khachhang_id)
            if (ds_dt_dtn.length > 0) {
              if (ds_dt_dtn[0].doanhthu != null) { this.pt_doanhthu = ds_dt_dtn[0].doanhthu } else { this.pt_doanhthu = 0 }

              if (ds_dt_dtn[0].dtn != null) { this.pt_diem_tn = ds_dt_dtn[0].dtn } else { this.pt_diem_tn = 0 }
            } else {
              this.pt_doanhthu = 0
              this.pt_diem_tn = 0
            }
          }
          if (item.kieuhd_id !== '' && item.kieuhd_id != null) {
            this.$parent.cboKieu_HD = item.kieuhd_id
          } else {
            this.$parent.cboKieu_HD = 1 // KIEU_HD.TAI_GD
          }
          // ds[0].KIEUHD_ID != null ? this.cboKieu_HD = ds[0].KIEUHD_ID : this.cboKieu_HD = Enum.KIEU_HD.TAI_GD
          this.txtTenKH = this.ChuanHoaTen(item.ten_kh)
          this.txtDiaChiKH = this.ChuanHoaTen(item.diachi_kh)

          const KHACHHANG = 1
          let _ds = await this.LAY_DS_DIACHI_THEO_HDKH_ID(this.hdkh_id)
          this.HienThiTTDiaChi(_ds, KHACHHANG)

          this.txtNguoiDD = this.ChuanHoaTen(item.nguoi_dd)
          this.txtChucDanhDD = this.ChuanHoaTen(item.chucdanh_dd)
          this.txtTelKH = item.so_dt
          this.txtFaxKH = item.so_fax
          this.txtHoKhau = this.ChuanHoaTen(item.hokhau)
          this.txtNoiCapHK = this.ChuanHoaTen(item.noicap_hk)
          // Hiếu bổ sung thông tin mã hợp đồng, ngày lập hợp đồng của khách hàng mới 25/08/2010
          item.ngay_hk != null ? this.txtNgayCapHK = DateTimeLib.toDateDisplay(item.ngay_hk) : this.txtNgayCapHK = ''
          item.ngay_sn != null ? this.txtNgaySN = DateTimeLib.toDateDisplay(item.ngay_sn) : this.txtNgaySN = ''
          this.txtSoGT = item.so_gt
          item.ngaycap != null ? this.txtNgayCap = DateTimeLib.toDateDisplay(item.ngaycap) : this.txtNgayCap = ''
          this.cboGiayToKH = item.loaigt_id
          this.txtNoiCap = this.ChuanHoaTen(item.noicap)
          // Hiếu bổ sung một số thông tin:
          this.txtMaSTKH = item.mst
          this.txtSoTKKH = item.stk
          if (item.ngayhethan_gt) { this.txtNgayHetHan = DateTimeLib.toDateDisplay(item.ngayhethan_gt) } else this.txtNgayHetHan = ''
          this.rdoGioiTinh = item.gioitinh
          if (item.quoctich_id != null) {
            this.isEnabledCboQuocTich = true
            this.cboQuocTich = item.quoctich_id
          } else {
            this.isEnabledCboQuocTich = false
            this.cboQuocTich = null
          }
          if (item.dantoc_id != null) {
            this.isEnabledCboDanToc = true
            this.cboDanToc = item.dantoc_id
          } else {
            this.isEnabledCboDanToc = false
            this.cboDanToc = null
          }
          if (item.nganhnghe_id !== null) {
            this.isEnableCboNganhNgheLoc = true
            this.cboNganhNgheLoc = item.nganhnghe_id
            this.nganhnghe_id = item.nganhnghe_id
          } else {
            this.isEnableCboNganhNgheLoc = false
            this.cboNganhNgheLoc = this.dsNganhNgheLoc[0].nganhnghe_id
          }
          this.txtDiaChiNDD = this.ChuanHoaTen(item.diachi_ndd)
          if (item.nganhang_id != null) {
            this.cboNganHangKH = item.nganhang_id
            this.chkNganHangKH = true
          }
          this.txtGhiChuKH = item.ghichu
          // TuấnNA thêm code hiển thị thông tin id tiểu ngành c1,c2,c3 -- Ngày 03/12/2010
          if (item.tnc1_id != null) { this.TNC1_ID = item.tnc1_id }
          if (item.tnc2_id != null) { this.TNC2_ID = item.tnc2_id }
          if (item.tnc3_id != null) { this.TNC3_ID = item.tnc3_id }
          if (item.tnc4_id != null) { this.TNC4_ID = item.tnc4_id }
          if (item.loaikh_id != null) { this.setLoaiKHID(item.loaikh_id) }

          if (item.ctv_id != null) {
            this.$parent.modelCkeckBox.chkCTV = true
            this.$parent.ctv_id = item.ctv_id
            let rs = await this.$root.context.get('/web-hopdong/chuyendich/map_id_tennv_neo', {
              ctv_id: item.ctv_id
            })
            this.$parent.modelInput.txtCTV = rs.data
          } else {
            this.$parent.modelCkeckBox.chkCTV = false
            this.$parent.modelInput.txtCTV = ''
          }
          if (item.nhanviengt_id != null) {
            this.$parent.modelCkeckBox.chkNguoiGT = true
            this.$parent.nguoigt_id = item.nhanviengt_id
            let rs = await this.$root.context.get('/web-hopdong/chuyendich/map_id_tennv_neo', {
              ctv_id: item.nhanviengt_id
            })
            this.$parent.modelInput.txtNguoiGT = rs.data
          } else {
            this.$parent.modelCkeckBox.chkNguoiGT = false
            this.$parent.modelInput.txtNguoiGT = ''
          }

          if (item.phanloaikh_id != null) {
            this.PHANLOAIKH_ID = item.phanloaikh_id
            let temp = await this.MAP_PHANLOAIKH_ID_NEO(item.phanloaikh_id)
            if (temp != null && temp.length > 0) {
              if (temp[0].ma_plkh != '-1') { this.txtPhanLoaiKH = temp[0].ma_plkh }

              if (temp[0].nhom_pl != '-1') { this.nhomplkh = temp[0].nhom_pl }
            }
          }
          // PNLINH: B2A 06/05/2020
          if (item.phieucskh_id != null) { this.VPHIEUCSKH_ID = item.phieucskh_id }
          let _data = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id)
          await this.HienThiTTHopDongTT(_data)
          await this.HienThiDanhSacHDTT(_data)
        }
      } catch (error) {
        console.log(error)
        throw error
      }
    },
    /**
     * Hiển thị thông tin đã khảo sát lên form
     * @param ds - danh sách hợp đồng khách hàng
     * @param idx - index đã chọn trong danh sách hợp đồng
     */
    async HienThiTTHopDongKH_KhaoSat (ds, idx) {
      ds = this.LowerCaseData(ds)
      try {
        if (ds.length) {
          let item = ds[idx]
          let iHdkhID = item.hdkh_id
          this.khachhang_id = item.khachhang_id
          this.txtTenKH = item.ten_kh
          this.txtTenTT = item.ten_kh
          this.txtDiaChiKH = item.diachi_kh
          this.txtSoGT = item.so_gt
          this.cboGiayToKH = item.loaigt_id
          this.txtNoiCap = item.noicap
          // Hiếu bổ sung: 10/08/2010
          this.txtMaSTKH = item.mst
          this.txtSoTKKH = item.stk
          if (item.ngayhethan_gt) { this.txtNgayHetHan = DateTimeLib.toDateDisplay(item.ngayhethan_gt) } else this.txtNgayHetHan = ''
          this.rdoGioiTinh = item.gioitinh
          if (item.quoctich_id) {
            this.isEnabledCboQuocTich = true
            this.cboQuocTich = item.quoctich_id
          } else {
            this.isEnabledCboQuocTich = false
            this.cboQuocTich = null
          }
          if (item.dantoc_id) {
            this.isEnabledCboDanToc = true
            this.cboDanToc = item.dantoc_id
          } else {
            this.isEnabledCboDanToc = false
            this.cboDanToc = null
          }
          if (item.nganhnghe_id) {
            this.isEnableCboNganhNgheLoc = true
            this.cboNganhNgheLoc = item.nganhnghe_id
            this.nganhnghe_id = item.nganhnghe_id
          } else {
            this.isEnableCboNganhNgheLoc = false
            this.cboNganhNgheLoc = this.dsNganhNgheLoc[0].nganhnghe_id
          }
          // TuấnNA thêm code hiển thị thông tin id tiểu ngành c1,c2,c3 -- Ngày 03/12/2010
          if (item.tnc1_id != null) { this.TNC1_ID = item.tnc1_id }
          if (item.tnc2_id != null) { this.TNC2_ID = item.tnc2_id }
          if (item.tnc3_id != null) { this.TNC3_ID = item.tnc3_id }
          if (item.tnc4_id != null) { this.TNC4_ID = item.tnc4_id }
          if (item.loaikh_id != null) { this.setLoaiKHID(item.loaikh_id) }
          this.txtDiaChiNDD = this.ChuanHoaTen(item.diachi_ndd)
          if (item.nganhang_id) {
            this.cboNganHangKH = item.nganhang_id
            this.chkNganHangKH = true
          }
          this.txtGhiChuKH = item.ghichu
          this.txtTelKH = item.so_dt
          if (item.ngaycap) { this.txtNgayCap = DateTimeLib.toDateDisplay(item.ngaycap) } else this.txtNgayCap = ''

          const KHACHHANG = 1
          let _ds = await this.LAY_DS_DIACHI_THEO_HDKH_ID(iHdkhID)
          this.HienThiTTDiaChi(_ds, KHACHHANG)
        }
      } catch (e) {
        console.log(e)
      }
    },
    /**
     * Hiển thị thông hợp đồng thuê bao - lấy thông tin từ danh bạ
     * @param ds - danh sách thông tin thuê bao - danh bạ
     * @return {Promise<void>}
     */
    async HienThiTT_HopDong_ThueBao (ds) {
      ds = this.LowerCaseData(ds)
      if (ds.length) {
        let item = ds[0]
        // Phần thông tin thanh toán
        this.txtMaTT = item.ma_tt
        this.thanhtoan_id = item.thanhtoan_id
        let dt_madd = await this.HT_COMBO_MA_DAIDIEN(this.thanhtoan_id)
        if (dt_madd.length) {
          if (item.matb_dd) {
            let t = dt_madd.filter(i => i.ma_tb == item.matb_dd)
            if (t.length == 0) {
              dt_madd.push({ma_tb: item.matb_dd})
            }
          }
          this.dsMaDD = dt_madd
          this.setMaTBDD(item.matb_dd)
        } else this.cboMaDD = null
        this.txtTenTT = item.ten_tt
        this.txtDiaChiTT = item.diachi_tt
        this.txtDiaChiCT = item.diachi_ct
        this.txtGhiChuTT = item.ghichu
        let _dsDiachiTT = await this.LAY_DS_DIACHI_THEO_DBTTID(this.thanhtoan_id)
        const THANHTOAN = 2
        this.HienThiTTDiaChi(_dsDiachiTT, THANHTOAN)
        this.txtMST = item.mst
        this.txtSoTKKH = item.stk
        if (item.nganhang_id) { this.cboNganHangTT = item.nganhang_id }
        this.cboHTTT = item.httt_id
        this.cboDonViQL = item.donvi_id
        if (this.dsDonViQL.length && this.dsDonViQL.filter(item => item.donvi_id == this.cboDonViQL).length == 0) { this.cboDonViQL = this.dsDonViQL[0].donvi_id }
        this.tuyenthu_id = item.tuyenthu_id ? item.tuyenthu_id : 0
        if (item.nhanvien_id) {
          this.nhanvientc_id = item.nhanvien_id
          this.txtMaNVTC = await this.MAP_ID_NHANVIEN_ID_NEO(item.nhanvien_id)
        } else {
          this.nhanvientc_id = 0
          this.txtMaNVTC = null
        }
        this.matb_dd = item.matb_dd
        this.txtMaSTKH = item.mst
        this.txtSoTKKH = item.stk
      }
    },
    /**
     * Hiển thị thông tin danh bạ khách hàng theo mã thuê bao
     * @param ds
     * @return {Promise<void>}
     */
    async HienThiTT_DanhBa_KH_NhanCQ (ds) {
      ds = this.LowerCaseData(ds)
      this.load_ds_nvtc = false
      // this.load_ds_donvi = false

      if (ds.length) {
        let item = ds[0]
        // Thông tin khách hàng
        this.txtMaKH = item.ma_kh
        this.khachhang_id = item.khachhang_id
        this.txtTenKH = item.ten_kh
        item.loaikh_id ? this.setLoaiKHID(item.loaikh_id) : null
        if (this.phantap_kh) {
          let ds_dt_dtn = await this.phantap_laydoanhthu_dtn(this.khachhang_id)
          if (ds_dt_dtn.length > 0) {
            if (ds_dt_dtn[0].doanhthu != null) { this.pt_doanhthu = ds_dt_dtn[0].doanhthu } else { this.pt_doanhthu = 0 }

            if (ds_dt_dtn[0].dtn != null) { this.pt_diem_tn = ds_dt_dtn[0].dtn } else { this.pt_diem_tn = 0 }
          } else {
            this.pt_doanhthu = 0
            this.pt_diem_tn = 0
          }
        }
        if (item.phanloaikh_id != null) {
          this.PHANLOAIKH_ID = item.phanloaikh_id
          let temp = await this.MAP_PHANLOAIKH_ID_NEO(item.phanloaikh_id)
          if (temp != null && temp.length > 0) {
            if (temp[0].ma_plkh != '-1') { this.txtPhanLoaiKH = temp[0].ma_plkh }

            if (temp[0].nhom_pl != '-1') { this.nhomplkh = temp[0].nhom_pl }
          }
        }
        this.khachhang_id_cu = item.khachhang_id
        if (this.kieukh_canhan && !item.nguoi_dd) { this.txtNguoiDD = this.txtTenKH } else this.txtNguoiDD = item.nguoi_dd
        this.txtDiaChiNDD = item.diachi_ndd
        this.txtChucDanhDD = item.chucdanh
        this.txtDiaChiKH = item.diachi_kh
        let _dsDiaChiKH = await this.LAY_DS_DIACHI_THEO_DBKHID(this.khachhang_id)
        const KHACHHANG = 1
        this.HienThiTTDiaChi(_dsDiaChiKH, KHACHHANG)
        this.txtTelKH = item.so_dt
        this.txtFaxKH = item.so_fax
        this.txtHoKhau = item.hokhau
        this.txtNoiCapHK = item.noicap_hk
        this.txtNgayCapHK = item.ngay_hk ? DateTimeLib.toDateDisplay(item.ngay_hk) : ''
        this.txtNgaySN = item.ngay_sn ? DateTimeLib.toDateDisplay(item.ngay_sn) : ''
        this.cboGiayToKH = item.loaigt_id
        this.txtSoGT = item.so_gt
        this.txtNgayCap = item.ngaycap ? DateTimeLib.toDateDisplay(item.ngaycap) : ''
        this.txtNoiCap = item.noicap
        this.txtMaTT = item.ma_tt
        this.txtMaIG = item.main_ghep
        if (item.mig_id) { this.mig_id = item.mig_id }
        this.thanhtoan_id = item.thanhtoan_id
        let dt_madd = await this.HT_COMBO_MA_DAIDIEN(this.thanhtoan_id)
        if (dt_madd.length) {
          if (item.matb_dd) {
            let t = dt_madd.filter(i => i.ma_tb == item.matb_dd)
            if (t.length == 0) {
              dt_madd.push({ma_tb: item.matb_dd})
            }
          }
          this.dsMaDD = dt_madd
          this.setMaTBDD(item.matb_dd)
        } else this.cboMaDD = null
        this.txtTenTT = this.ChuanHoaTen(item.ten_tt)
        this.txtDiaChiTT = this.ChuanHoaTen(item.diachi_tt)
        this.txtDiaChiBC = this.ChuanHoaTen(item.diachi_bc)
        this.txtDiaChiCT = this.ChuanHoaTen(item.diachi_ct)
        this.txtGhiChuTT = item.ghichu

        this.txtMaTBDD = item.matb_dd
        let _dsDiachiTT = await this.LAY_DS_DIACHI_THEO_DBTTID(this.thanhtoan_id)
        const THANHTOAN = 2
        this.HienThiTTDiaChi(_dsDiachiTT, THANHTOAN)
        // TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT --- Ngày 12/11/2010
        this.DV_QHNS = item.dv_qhns
        this.MACHUONG = item.machuong
        this.MN_KT = item.mn_kt
        this.MA_NDKT = item.ma_ndkt
        this.MN_NSNN = item.mn_nsnn
        if (item.tien_dm != null) { this.TIEN_DM = item.tien_dm } else { this.TIEN_DM = 0 }
        this.txtMST = item.mst_tt
        this.txtSoTKKH = item.stk
        if (item.nganhang_id_tt) { this.cboNganHangTT = item.nganhang_id_tt }
        this.cboHTTT = item.httt_id
        this.load_ds_nvtc = true // Không hiển thị mã nvtc khi load cbo httt

        this.cboDonViQL = item.donvi_id
        if (this.dsDonViQL.length && this.dsDonViQL.filter(item => item.donvi_id == this.cboDonViQL).length == 0) { this.cboDonViQL = this.dsDonViQL[0].donvi_id }
        if (item.nhanvien_id != null) {
          this.nhanvientc_id = item.nhanvien_id
          this.txtMaNVTC = await this.MAP_ID_NHANVIEN_ID_NEO(item.nhanvien_id)
        } else {
          this.nhanvientc_id = 0
          this.txtMaNVTC = null
        }
        if (item.tuyenthu_id != null) {
          this.tuyenthu_id = item.tuyenthu_id
          this.txtMaTuyen = await this.MAP_ID_TUYENTHU_ID_NEO(item.tuyenthu_id)
        } else {
          this.tuyenthu_id = 0
          this.txtMaTuyen = null
        }
        this.matb_dd = item.matb_dd
        this.txtSTKTT = item.stk_tt
        this.txtChu_TK = item.chu_tk_tt
        // Hiếu bổ sung:10/08/2010
        this.txtMaSTKH = item.mst
        this.txtSoTKKH = item.stk
        if (item.ngayhethan_gt) { this.txtNgayHetHan = DateTimeLib.toDateDisplay(item.ngayhethan_gt) } else this.txtNgayHetHan = ''
        this.rdoGioiTinh = item.gioitinh
        if (item.quoctich_id != null) {
          this.isEnabledCboQuocTich = true
          this.cboQuocTich = item.quoctich_id
        } else {
          this.isEnabledCboQuocTich = false
          this.cboQuocTich = null
        }
        if (item.dantoc_id != null) {
          this.isEnabledCboDanToc = true
          this.cboDanToc = item.dantoc_id
        } else {
          this.isEnabledCboDanToc = false
          this.cboDanToc = null
        }
        if (item.nganhnghe_id) {
          this.isEnableCboNganhNgheLoc = true
          this.cboNganhNgheLoc = item.nganhnghe_id
          this.nganhnghe_id = item.nganhnghe_id
        } else {
          this.isEnableCboNganhNgheLoc = false
          this.cboNganhNgheLoc = this.dsNganhNgheLoc[0].nganhnghe_id
        }
        // TuấnNA thêm code hiển thị thông tin id tiểu ngành c1,c2,c3 -- Ngày 03/12/2010
        if (item.tnc1_id != null) { this.TNC1_ID = item.tnc1_id }
        if (item.tnc2_id != null) { this.TNC2_ID = item.tnc2_id }
        if (item.tnc3_id != null) { this.TNC3_ID = item.tnc3_id }
        if (item.tnc4_id != null) { this.TNC4_ID = item.tnc4_id }
        this.LAY_DS_SUDUNG_DVK(this.thanhtoan_id, 2)
        if (item.nganhang_id) {
          this.cboNganHangKH = item.nganhang_id
          this.chkNganHangKH = true
        }
        this.txtGhiChuKH = item.ghichu
      }
    },
    /***
     * Hiển thị thông tin hợp đồng thanh toán
     * @param ds
     * @return {Promise<void>}
     */
    async HienThiTTHopDongTT (ds) {
      try {
        if (ds.length > 0) {
          let item = ds[0]
          this.hdtt_id = item.hdtt_id
          this.txtMaTT = item.ma_tt
          if (item.thanhtoan_id != null || item.thanhtoan_id != 0) { this.thanhtoan_id = item.thanhtoan_id } else { this.thanhtoan_id = 0 }
          let dt_madd = await this.HT_COMBO_MA_DAIDIEN(this.thanhtoan_id)
          if (dt_madd !== null && dt_madd.length) {
            if (item.matb_dd) {
              let t = dt_madd.filter(i => i.ma_tb == item.matb_dd)
              if (t.length == 0) {
                dt_madd.push({ma_tb: item.matb_dd})
              }
            }
            this.dsMaDD = dt_madd
            this.setMaTBDD(item.matb_dd)
          } else this.cboMaDD = null
          if (item.hdmig_id) {
            this.hdmig_id = item.hdmig_id
            this.txtMaIG = await this.MAP_ID_MAIN_GHEP_HD_MIG_NEO(this.hdmig_id)
          } else {
            this.hdmig_id = 0
            this.txtMaIG = null
          }
          this.txtTenTT = this.ChuanHoaTen(item.ten_tt)
          this.txtDiaChiTT = this.ChuanHoaTen(item.diachi_tt)
          this.txtDiaChiBC = this.ChuanHoaTen(item.diachi_bc)
          // TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT --- Ngày 12/11/2010
          this.DV_QHNS = item.dv_qhns
          this.MACHUONG = item.machuong
          this.MN_KT = item.mn_kt
          this.MA_NDKT = item.ma_ndkt
          this.MN_NSNN = item.mn_nsnn
          if (item.tien_dm != null) { this.TIEN_DM = item.tien_dm } else { this.TIEN_DM = 0 }
          //
          const THANHTOAN = 2
          let _ds = await this.LAY_DS_DIACHI_THEO_HDTT_ID(this.hdtt_id)
          this.HienThiTTDiaChi(_ds, THANHTOAN)

          this.txtMST = item.mst
          this.txtSTKTT = item.stk
          this.txtChu_TK = item.chu_tk
          if (item.nganhang_id != null) { this.cboNganHang = item.nganhang_id }
          this.cboHTTT = item.httt_id
          this.cboDonViQL = item.donvi_id
          if (item.nhanvien_id != null) {
            this.nhanvientc_id = item.nhanvien_id
            this.txtMaNVTC = await this.MAP_ID_NHANVIEN_ID_NEO(item.nhanvien_id)
          } else {
            this.nhanvientc_id = 0
            this.txtMaNVTC = null
          }
          if (item.tuyenthu_id != null) {
            this.tuyenthu_id = item.tuyenthu_id
            this.txtMaTuyen = await this.MAP_ID_TUYENTHU_ID_NEO(item.tuyenthu_id)
          } else {
            this.tuyenthu_id = 0
            this.txtMaTuyen = null
          }
          this.matb_dd = item.matb_dd
          // Hiếu bố sung matb_dd 1/09/2010
          this.txtMaTBDD = item.matb_dd

          // TuấnNA thêm code hiển thị một số thông tin thanh toán -- Ngày 08/08/2010
          this.txtGhiChuTT = item.ghichu
          this.txtDiaChiCT = this.ChuanHoaTen(item.diachi_ct)
          await this.LAY_DS_SUDUNG_DVK(this.thanhtoan_id, 2)
        }
      } catch (exp) {
        if (exp.response !== undefined && exp.response.data !== undefined) {
          this.$root.$toast.error(' ' + exp.response.data.message_detail)
        } else {
          console.log(exp)
        }
        
      }
    },
    /**
     * Hiển thị danh sách hợp đồng thanh toán
     * @param ds
     * @return {Promise<void>}
     */
    async HienThiDanhSacHDTT (ds) {
      this.lvwThanhToan = []
      if (ds.length > 0) {
        for (let i = 0; i < ds.length; i++) {
          let myRow = ds[i]
          let lvItem = {}
          lvItem.stt = i + 1
          lvItem.hdtt_id = myRow['hdtt_id'].toString()
          lvItem.ma_tt = myRow['ma_tt'].toString()
          lvItem.ten_tt = this.ChuanHoaTen(myRow['ten_tt'].toString())
          lvItem.diachi_tt = this.ChuanHoaTen(myRow['diachi_tt'].toString())
          lvItem.diachi_ct = this.ChuanHoaTen(myRow['diachi_ct'].toString())
          this.lvwThanhToan.push(lvItem)
        }
        if (ds.length <= 1) { this.$parent.tsbtnXoaTT = false } else { this.$parent.tsbtnXoaTT = true }
      }
    },
    /***
     * Hiển thị thông tin địa chỉ
     * @param ds
     * @param kieu - 1 khách hàng, 2 thanh toán, 3 thuê bao
     * @returns {Promise<void>}
     */
    HienThiTTDiaChi (ds, kieu) {
      if (ds.length > 0) {
        let item = ds[0]
        if (kieu == 1) {
          // Khách hàng
          this.diachikh_id = item.diachi_id
          this.tinhkh_id = item.tinh_id
          this.quankh_id = item.quan_id
          this.phuongkh_id = item.phuong_id
          if (item.pho_id != null) { this.phokh_id = item.pho_id } else { this.phokh_id = 0 }
          /// TuấnNA comment không lấy thông tin ap_id -- Ngày 08/08/2010
          /// // HoangPKN : lấy thông tin ngõ : 16/05/2012
          if (item.ap_id != null) { this.apkh_id = item.ap_id } else { this.apkh_id = 0 }
          this.sonhakh = this.ChuanHoaTen(item.sonha)
        }

        if (kieu == 2) {
          // Thanh toán
          this.diachitt_id = item.diachi_id
          this.tinhtt_id = item.tinh_id
          this.quantt_id = item.quan_id
          this.phuongtt_id = item.phuong_id
          if (item.pho_id != null) { this.phott_id = item.pho_id } else { this.phott_id = 0 }
          /// TuấnNA comment không lấy thông tin ap_id -- Ngày 08/08/2010
          /// Hoang pkn : lấy thêm ngõ : 16/05/2012
          if (item.ap_id != null) { this.aptt_id = item.ap_id } else { this.aptt_id = 0 }
          this.sonhatt = this.ChuanHoaTen(item.sonha)
        }

        // if (kieu == 3)
        // {
        //   this.diachitb_id = ds[0].DIACHI_ID
        //   this.tinhtb_id = ds[0].TINH_ID
        //   this.quantb_id = ds[0].QUAN_ID
        //   this.phuongtb_id = ds[0].PHUONG_ID
        //   if (ds[0].PHO_ID != null)
        //     this.photb_id = ds[0].PHO_ID
        //   else
        //     this.photb_id = 0;
        //   ///TuấnNA comment không lấy thông tin ap_id -- Ngày 08/08/2010
        //   /// hoangpkn : 16/05/2012 : lấy thêm ngõ
        //   if (ds[0].AP_ID != null)
        //     this.aptb_id = ds[0].AP_ID
        //   else
        //     this.aptb_id = 0;
        //   this.sonhatb = this.ChuanHoaTen(ds[0].SONHA);
        //   if (ds[0].includes("block") || ds[0].includes("BLOCK"))
        //     this.blocktb = ds[0]["BLOCK"].toString();
        //   if (ds[0].includes("tang") || ds[0].includes("TANG"))
        //     this.tangtb = ds[0]["TANG"].toString();
        //   if (ds[0].includes("phong") || ds[0].includes("PHONG"))
        //     this.sophongtb = ds[0]["PHONG"].toString();
        //   if (ds[0].includes("mota") || ds[0].includes("MOTA"))
        //     this.motadctb = ds[0]["MOTA"].toString();
        //
        //   /// Tuấn thêm code lấy địa chỉ lắp đặt cho thuê bao -- Ngày 29/07/2010
        //   if (ds[0].DIACHILD_ID != null)
        //     this.diachild_id = ds[0].DIACHILD_ID
        //   else
        //     this.diachild_id = 0;
        //
        //   if (ds[0].TINHLD_ID != null)
        //     this.tinhld_id = ds[0].TINHLD_ID
        //   else
        //     this.tinhld_id = 0;
        //
        //   if (ds[0].QUANLD_ID != null)
        //     this.quanld_id = ds[0].QUANLD_ID
        //   else
        //     this.quanld_id = 0;
        //
        //   if (ds[0].PHUONGLD_ID != null)
        //     this.phuongld_id = ds[0].PHUONGLD_ID
        //   else
        //     this.phuongld_id = 0;
        //
        //   if (ds[0].PHOLD_ID != null)
        //     this.phold_id = ds[0].PHOLD_ID
        //   else
        //     this.phold_id = 0;
        //
        //   if (ds[0].APLD_ID != null )
        //     this.apld_id = ds[0].APLD_ID
        //   else
        //     this.apld_id = 0;
        //   this.sonhald = this.ChuanHoaTen(ds[0].SONHA_LD);
        //
        //   if (ds[0].includes("block_ld") || ds[0].includes("BLOCK_LD"))
        //     this.block_ld = ds[0]["BLOCK_LD"].toString();
        //   if (ds[0].includes("tang_ld") || ds[0].includes("TANG_LD"))
        //     this.tang_ld = ds[0]["TANG_LD"].toString();
        //   if (ds[0].includes("phong_ld") || ds[0].includes("PHONG_LD"))
        //     this.phong_ld = ds[0]["PHONG_LD"].toString();
        //   if (ds[0].includes("mota_ld") || ds[0].includes("MOTA_LD"))
        //     this.mota_ld = ds[0]["MOTA_LD"].toString();
        //
        // }
      }
    },
    /**
     * Thêm mới hợp đồng
     * dsHDKH, dsHDKH_Sub, dsHDTT, dsHDTT_NV, dsDiaChi, dsDiaChiHDKH, dsDiaChiHDTT
     */
    async ThemMoi_HopDong_Sub () {
      try {
        this.loading(true)
        // insert HD_KHACHHANG
        if (this.dsHDKH.length) {
          await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hdkhachhang', this.dsHDKH[0]).then(res => {
            if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail) { this.$root.toastError(res.message_detail) }
          })
        }
        // insert HDKH_SUB
        if (this.dsHDKH_Sub.length > 0) { this.dsHDKH_Sub[0].HDKH_ID = this.dsHDKH[0].HDKH_ID }
        await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hdkh_sub', this.dsHDKH_Sub[0]).then(res => {
          if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail) { this.$root.toastError(res.message_detail) }
        })

        // insert HDTT
        if (this.dsHDTT.length > 0) { await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hd_thanhtoan', this.dsHDTT[0]) }

        // insert HDTT_NV
        if (this.dsHDTT_NV.length > 0) {
          await this.$root.context.post('/web-hopdong/chuyendich/themmoi_hdtt_nv', this.dsHDTT_NV[0]).then(res => {
            if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail) { this.$root.toastError(res.message_detail) }
          })
        }

        // insert DIACHI
        if (this.dsDiaChi.length > 0) {
          await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/themmoi_ds_diachi', this.dsDiaChi).then(res => {
            if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail) { this.$root.toastError(res.message_detail) }
          })
        }
        // insert DIACHI_HDKH
        if (this.dsDiaChiHDKH.length > 0) {
          await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/themmoi_diachi_hdkh', this.dsDiaChiHDKH[0]).then(res => {
            if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail) { this.$root.toastError(res.message_detail) }
          })
        }
        // insert DIACHI_HDTT
        if (this.dsDiaChiHDTT.length > 0) { await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/themmoi_diachi_hdtt', this.dsDiaChiHDTT[0]) }

        if (this.dsHDTT.length > 0) { await this.CapNhat_MaTB_DD_HD(this.dsHDTT) }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    /**
     * Cập nhật hợp đồng
     * dsHDKH, dsHDTT, dsHDTT_NV, dsDiaChi, dsDiaChiHDKH, dsDiaChiHDTT
     */
    async CapNhat_HopDong () {
      try {
        this.loading(true)
        // update HD_KHACHHANG
        if (this.dsHDKH.length > 0) {
          await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hdkhachhang', this.dsHDKH[0]).then(res => {
            if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail) { this.$root.toastError(res.message_detail) }
          })
        }
        // update HD_THANHTOAN
        if (this.dsHDTT.length > 0) {
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hd_thanhtoan', this.dsHDTT[0])
          if (rs.data && rs.data[0].result) {
            // this.$root.toastSuccess('Cập nhật dữ liệu hợp đồng thanh toán thành công!')
          } else {
            this.$root.toastError('Cập nhật dữ liệu hợp đồng thanh toán thất bại!')
          }
        }
        // delete HDTT_NV, insert HDTT_NV
        if (this.dsHDTT.length > 0 && this.dsHDTT_NV.length > 0) {
          await this.$root.context.post(`/web-hopdong/chuyendich/xoa_hdtt_nv?hdkh_id=${this.dsHDTT[0].HDTT_ID}`)
          await this.$root.context.post('/web-hopdong/chuyendich/themmoi_hdtt_nv', this.dsHDTT_NV[0]).then(res => {
            if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail) { this.$root.toastError(res.message_detail) }
          })
        }
        // update DIACHI_HDKH
        if (this.dsDiaChiHDKH.length > 0) {
          await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/capnhat_diachi_hdkh', this.dsDiaChiHDKH[0]).then(res => {
            if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail) { this.$root.toastError(res.message_detail) }
          })
        }
        // update DIACHI_HDTT
        if (this.dsDiaChiHDTT.length > 0) {
          await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/capnhat_diachi_hdtt', this.dsDiaChiHDTT[0]).then(res => {
            if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail) { this.$root.toastError(res.message_detail) }
          })
        }
        // update DIACHI
        if (this.dsDiaChi.length > 0) {
          await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/capnhat_ds_diachi', this.dsDiaChi).then(res => {
            if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail) { this.$root.toastError(res.message_detail) }
          })
        }

        if (this.dsHDTT.length > 0) { await this.CapNhat_MaTB_DD_HD(this.dsHDTT) }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    /**
     * Cập nhật mã thuê bao đại diện hợp đồng
     */
    async CapNhat_MaTB_DD_HD (ds) {
      let dsHDTT_ID = ds.map(item => ({HDTT_ID: item.HDTT_ID}))
      await this.$root.context.post('/web-hopdong/chuyendich/capnhat_matb_dd_hd', dsHDTT_ID)
    },
    /**
     * Cập nhật địa chỉ báo cước
     */
    async CapNhat_DiaChiBC () {
      try {
        if (this.txtDiaChiBC) {
          await this.CAPNHAT_DIACHI_BC_HDTT(this.diachibc_id, this.hdtt_id, this.phobc_id, this.phuongbc_id, this.quanbc_id,
            this.sonhabc, this.txtDiaChiBC.toString().trim(), this.tinhbc_id, this.apbc_id, this.khubc_id, this.dacdiembc_id)
        } else {
          await this.CAPNHAT_DIACHI_BC_HDTT(this.diachibc_id, this.hdtt_id, this.phobc_id, this.phuongbc_id, this.quanbc_id,
            this.sonhabc, '0', this.tinhbc_id, this.apbc_id, this.khubc_id, this.dacdiembc_id)
        }
      } catch (e) {
        console.log(e)
      }
    },
    /**
     * Cập nhật địa chỉ báo cước
     */
    CAPNHAT_DIACHI_BC_HDTT: async function (vdiachibc_id, vhdtt_id,
      vpho_id, vphuong_id, vquan_id, vsonha, vdiachi,
      vtinh_id, vap_id, vkhu_id, vdacdiem_id) {
      let data = {
        diachibc_id: vdiachibc_id,
        hdtt_id: vhdtt_id,
        pho_id: vpho_id,
        phuong_id: vphuong_id,
        quan_id: vquan_id,
        sonha: vsonha,
        diachi: vdiachi,
        tinh_id: vtinh_id,
        ap_id: vap_id,
        khu_id: vkhu_id,
        dacdiem_id: vdacdiem_id
      }
      let res = await this.$root.context.post('/web-hopdong/chuyendich/capnhat_diachi_bc', data)
      return res.data
    },
    /**
     * Tạo dữ liệu
     * @param themmoi
     */
    TaoDuLieu: async function (themmoi) {
      await this.TaoDuLieu_HDKH(themmoi)
      await this.TaoDuLieu_HDTT(themmoi)
      // Hiếu bổ sung hàm tạo dữ liệu thông tin thanh toán 17/09/2010
      // await this.TaoDuLieu_THONGTIN_TT(themmoi)
      await this.TaoDuLieu_HDTT_NV(true)
      await this.TaoDuLieu_DiaChi(themmoi, 4)
    },
    /**
     * Tạo dữ liệu hợp đồng khách hàng HD_KHACHHANG
     * @param themmoi
     */
    TaoDuLieu_HDKH: async function (themmoi) {
      let vmagd_temp = ''
      this.dsHDKH = []
      if (themmoi == true) {
        await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', {keyname: 'HD_KHACHHANG'})
          .then(res => {
            if (res.error === '200' && res.data.length > 0) { this.hdkh_id = res.data[0].id }
          }).catch(e => { throw e })
      }
      let rowHDKH = {}
      rowHDKH.HDKH_ID = this.hdkh_id
      if (themmoi == true) {
        this.ts_sinhma_gd_theo_donvi = this.$parent.thamso_md.filter(x => x.ma_ts == 'SINH_MA_GD_THEO_DV').length > 0
        if (this.ts_sinhma_gd_theo_donvi) { vmagd_temp = await this.Lay_Ma_GD_V2(this.$root.token.getDonViID(), Enum.LoaiHopDong.DI_CHUYEN) } // s.PadLeft(1000, ' ');
        else { vmagd_temp = await this.Lay_Ma_GD(Enum.LoaiHopDong.DI_CHUYEN) } // s.PadLeft(1000, ' ');
        if (vmagd_temp.includes('ERROR:')) {
          this.$root.toastError(vmagd_temp.split(':')[1])
          this.loi_sinh_magd = true
        }
        this.ThongTinThueBao.modelInput.txtMaGD = vmagd_temp
        // this.$emit("txtMaGD", vmagd_temp)
        rowHDKH.MA_GD = vmagd_temp
      } else {
        rowHDKH.MA_GD = this.ThongTinThueBao.modelInput.txtMaGD
      }
      rowHDKH.MA_HD = this.ThongTinThueBao.modelInput.txtMaHDcu
      rowHDKH.NGAY_YC = DateTimeLib.toDateDisplay(this.ThongTinThueBao.modelDateTime.dtpNgayYC)
      rowHDKH.MA_KH = this.txtMaKH
      if (this.khachhang_id > 0) { rowHDKH.KHACHHANG_ID = this.khachhang_id } else { rowHDKH.KHACHHANG_ID = 0 }
      rowHDKH.TEN_KH = this.txtTenKH
      rowHDKH.DIACHI_KH = this.txtDiaChiKH
      rowHDKH.NGUOI_DD = this.txtNguoiDD
      rowHDKH.SO_DT = this.txtTelKH
      rowHDKH.CHUCDANH_DD = this.txtChucDanhDD
      rowHDKH.SO_FAX = this.txtFaxKH

      if (this.txtNgaySN != '') {
        rowHDKH.NGAY_SN = this.txtNgaySN
      }
      // rowHDKH.NGAYLAP_HD = moment(this.ThongTinThueBao.modelDateTime.dtpNgayLapHD).format('DD/MM/YYYY HH:mm:ss');
      rowHDKH.NGAYLAP_HD = typeof this.ThongTinThueBao.modelDateTime.dtpNgayLapHD === 'string'
        ? this.ThongTinThueBao.modelDateTime.dtpNgayLapHD : DateTimeLib.toDateDisplay(this.ThongTinThueBao.modelDateTime.dtpNgayLapHD)
      rowHDKH.LOAIGT_ID = this.cboGiayToKH
      rowHDKH.SO_GT = this.txtSoGT

      if (this.txtNgayCap != '') {
        rowHDKH.NGAYCAP = this.txtNgayCap
      }
      rowHDKH.NOICAP = this.txtNoiCap
      rowHDKH.SO_FAX = this.txtFaxKH
      rowHDKH.DONVI_ID = this.$root.token.getDonViID()
      rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID()
      rowHDKH.HOKHAU = this.txtHoKhau

      if (this.txtNgayCapHK != '') {
        rowHDKH.NGAY_HK = this.txtNgayCapHK
      }
      rowHDKH.NOICAP_HK = this.txtNoiCapHK

      rowHDKH.KHLON_ID = 1
      rowHDKH.LOAIKH_ID = this.cboLoaiKH

      if (this.PHANLOAIKH_ID != -1 && this.PHANLOAIKH_ID != 0) { rowHDKH.PHANLOAIKH_ID = this.PHANLOAIKH_ID }
      // let ip = await this.getIp()
      rowHDKH.LOAIHD_ID = Enum.LoaiHopDong.DI_CHUYEN
      rowHDKH.NGUOI_CN = this.$root.token.getUserName()
      rowHDKH.NGAY_CN = DateTimeLib.toDateDisplay(new Date())
      rowHDKH.MAY_CN = this.$root.token.getUserName()
      rowHDKH.IP_CN = this.$root.token.getUserName()
      // Hiếu thêm : 10/08/2010
      rowHDKH.MST = this.txtMaSTKH
      rowHDKH.STK = this.txtSoTKKH
      if (this.txtNgayHetHan != '') {
        rowHDKH.NGAYHETHAN_GT = this.txtNgayHetHan
      }

      rowHDKH.GIOITINH = this.rdoGioiTinh

      rowHDKH.NGANHNGHE_ID = this.cboNganhNgheLoc

      // TuấnNA thêm tạo dữ liệu tiểu ngành cấp 1,2,3 --- Ngày 03/12/2010
      rowHDKH.TNC1_ID = this.TNC1_ID
      rowHDKH.TNC2_ID = this.TNC2_ID
      rowHDKH.TNC3_ID = this.TNC3_ID
      rowHDKH.TNC4_ID = this.TNC4_ID

      rowHDKH.QUOCTICH_ID = this.cboQuocTich

      rowHDKH.DANTOC_ID = this.cboDanToc

      rowHDKH.DIACHI_NDD = this.txtDiaChiNDD
      if (this.chkNganHangKH) {
        rowHDKH.NGANHANG_ID = this.cboNganHangKH
      }
      rowHDKH.GHICHU = this.txtGhiChuKH
      rowHDKH.KIEUHD_ID = this.$parent.modelCbo.cboKieu_HD
      if (this.hdkh_cha_id > 0) {
        rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
      }

      if (this.$parent.modelCkeckBox.chkCTV && this.$parent.modelInput.txtCTV != '') {
        rowHDKH.CTV_ID = this.$parent.ctv_id
      }
      if (this.$parent.modelCkeckBox.chkNguoiGT && this.$parent.modelInput.txtNguoiGT != '') {
        rowHDKH.NHANVIENGT_ID = this.$parent.nguoigt_id
      }
      rowHDKH.KENHTN_ID = this.ThongTinThueBao.inputValue.iKenhTN
      // PNLINH B2A 02/05/2020
      if (this.VPHIEUCSKH_ID != 0) {
        rowHDKH.PHIEUCSKH_ID = this.VPHIEUCSKH_ID
      }

      this.dsHDKH.push(rowHDKH)
    },
    /**
     * Tạo dữ liệu hợp đồng thanh toán HD_THANHTOAN
     * @param themmoi
     */
    TaoDuLieu_HDTT: async function (themmoi) {
      this.dsHDTT = []

      if (themmoi == true) {
        await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', {keyname: 'HD_THANHTOAN'})
          .then(res => {
            if (res.error === '200' && res.data.length > 0) { this.hdtt_id = res.data[0].id }
          }).catch(e => {
            throw e
          })
      }
      let rowHDTT = {}
      rowHDTT.HDTT_ID = this.hdtt_id
      rowHDTT.HDKH_ID = this.hdkh_id
      if (this.txtMaTT == '') {
        let ma_tt = await this.$root.context.get(`/web-hopdong/chuyendich/sinh_matt?donvi_id=${this.cboDonViQL}&quan_id=${this.ThongTinThueBao.inputValue.quanld_id}`)
        this.txtMaTT = ma_tt.data
        this.thanhtoan_id = 0
      }
      if (this.thanhtoan_id > 0) { rowHDTT.THANHTOAN_ID = this.thanhtoan_id }
      rowHDTT.MA_TT = this.txtMaTT
      rowHDTT.TEN_TT = this.txtTenTT
      rowHDTT.DIACHI_TT = this.txtDiaChiTT
      rowHDTT.DIACHI_BC = this.txtDiaChiBC
      rowHDTT.MST = this.txtMST
      rowHDTT.HTTT_ID = this.cboHTTT
      if (this.cboHTTT == Enum.HinhThucThanhToan.UYNHIEM_THU) {
        rowHDTT.NGANHANG_ID = this.cboNganHangTT
      }
      rowHDTT.STK = this.txtSTKTT
      rowHDTT.CHU_TK = this.txtChu_TK
      rowHDTT.DONVI_ID = this.cboDonViQL
      if (this.tuyenthu_id != 0) { rowHDTT.TUYENTHU_ID = this.tuyenthu_id }
      if (this.cboMaDD != -1) { rowHDTT.MATB_DD = this.cboMaDD } else { rowHDTT.MATB_DD = '' }
      // let ip = await this.getIp()
      rowHDTT.NGUOI_CN = this.$root.token.getUserName()
      rowHDTT.NGAY_CN = DateTimeLib.toDateDisplay(new Date())
      rowHDTT.MAY_CN = this.$root.token.getUserName()

      rowHDTT.SO_DT = this.txtTelKH
      // Hiếu bổ sung thêm column HDTT
      rowHDTT.DIACHI_CT = this.txtDiaChiCT

      rowHDTT.GHICHU = this.txtGhiChuTT ? this.txtGhiChuTT.toString().trim() : null
      /// TuấnNA thêm code lấy tuyenthu_id --- Ngày 20/09/2010
      rowHDTT.TUYENTHU_ID = this.tuyenthu_id

      // TuấnNA thêm code tạo dữ liệu NSNN --- Ngày 12/11/2010
      let loai_nh = ''
      if (this.cboNganHangTT && this.cboNganHangTT != -1) {
        loai_nh = await this.MAP_LOAI_NH_ID_NEO(this.cboNganHangTT)
      }
      if (loai_nh == '2') {
        rowHDTT.DV_QHNS = this.DV_QHNS
        rowHDTT.MACHUONG = this.MACHUONG
        rowHDTT.MN_KT = this.MN_KT
        rowHDTT.MA_NDKT = this.MA_NDKT
        rowHDTT.MN_NSNN = this.MN_NSNN
        rowHDTT.TIEN_DM = this.TIEN_DM
      } else {
        rowHDTT.DV_QHNS = ''
        rowHDTT.MACHUONG = ''
        rowHDTT.MN_KT = ''
        rowHDTT.MA_NDKT = ''
        rowHDTT.MN_NSNN = ''
        rowHDTT.TIEN_DM = 0
      }
      //
      this.dsHDTT.push(rowHDTT)
    },
    /**
     * Tạo dữ liệu cho HDTT_NV
     */
    async TaoDuLieu_HDTT_NV (themmoi) {
      this.dsHDTT_NV = []
      // let ip = await this.getIp()
      if (this.nhanvien_ql_am_id != 0) {
        let rowhdtt_nv = {}
        rowhdtt_nv.HDTT_ID = this.hdtt_id
        rowhdtt_nv.NHANVIEN_ID = this.nhanvien_ql_am_id
        rowhdtt_nv.MAY_CN = this.$root.token.getUserName()
        rowhdtt_nv.NGAY_CN = DateTimeLib.toDateDisplay(new Date())
        rowhdtt_nv.NGUOI_CN = this.$root.token.getUserName()
        this.dsHDTT_NV.push(rowhdtt_nv)
      }
    },
    /**
     * Tạo dữ liệu HDKH_SUB
     */
    TAO_DULIEU_HDKH_SUB (ds_hdkh_sub, v_hdkh_id) {
      let row = {}
      row.CHUCVU_GT = ds_hdkh_sub[0]['chucvu_gt'] + ''
      row.HDKH_ID = v_hdkh_id
      if (ds_hdkh_sub[0]['ngaycap_gt']) { row.NGAYCAP_GT = ds_hdkh_sub[0]['ngaycap_gt'] }
      row.NGUOI_GT = ds_hdkh_sub[0]['nguoi_gt'] + ''
      if (ds_hdkh_sub[0]['so_phong']) { row.SO_PHONG = parseInt(ds_hdkh_sub.Rows[0]['so_phong'] + '', 10) }
      row.SOGIAY_GT = ds_hdkh_sub[0]['sogiay_gt'] + ''
      row.MA_NS = ds_hdkh_sub[0]['ma_ns'] + ''
      if (this.ts_kt_tthdkhdn == 1) {
        row.SO_HD_KHDN = ds_hdkh_sub[0]['so_hd_khdn'] + ''
        row.MA_KHDN = ds_hdkh_sub[0]['ma_khdn'] + ''
        if (ds_hdkh_sub[0]['giatri_hd']) { row.GIATRI_HD = parseInt(ds_hdkh_sub[0]['giatri_hd'] + '') }
        if (ds_hdkh_sub[0]['ngay_ky_hd']) { row.NGAY_KY_HD = ds_hdkh_sub[0]['ngay_ky_hd'] }
        row.GIAY_UQ = ds_hdkh_sub[0]['giay_uq'] + ''
        if (ds_hdkh_sub[0]['loaihdkh_id'] && ds_hdkh_sub[0]['loaihdkh_id'] + '' !== '-1') { row.LOAIHDKH_ID = parseInt(ds_hdkh_sub[0]['loaihdkh_id'] + '', 10) }
        if (ds_hdkh_sub[0]['tinhtranghd_id'] && ds_hdkh_sub[0]['tinhtranghd_id'] + '' !== '-1') { row.TINHTRANGHD_ID = parseInt(ds_hdkh_sub[0]['tinhtranghd_id']) }
      }
      this.dsHDKH_Sub = [row]
    },
    /**
     * Tạo dữ liệu địa chỉ
     */
    async TaoDuLieu_DiaChi (themmoi, kieu) {
      this.dsDiaChi = []
      this.dsDiaChiHDKH = []
      this.dsDiaChiHDTT = []
      if (kieu == 1 || kieu == 4) {
        let rowDiaChiKH = {}
        if (themmoi == true) {
          await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', {keyname: 'DIACHI'})
            .then(res => {
              if (res.error === '200' && res.data.length > 0) { this.diachikh_id = res.data[0].id }
            }).catch(e => {
              throw e
            })
        }
        rowDiaChiKH.DIACHI_ID = this.diachikh_id
        rowDiaChiKH.TINH_ID = this.tinhkh_id
        rowDiaChiKH.QUAN_ID = this.quankh_id
        rowDiaChiKH.PHUONG_ID = this.phuongkh_id
        if (this.phokh_id != 0) { rowDiaChiKH.PHO_ID = this.phokh_id }
        if (this.apkh_id != 0) { rowDiaChiKH.AP_ID = this.apkh_id }
        if (this.khukh_id != 0) { rowDiaChiKH.KHU_ID = this.khukh_id }
        if (this.dacdiemkh_id != 0) { rowDiaChiKH.DACDIEM_ID = this.dacdiemkh_id }
        rowDiaChiKH.BLOCK = this.blockkh
        rowDiaChiKH.TANG = this.tangkh
        rowDiaChiKH.PHONG = this.sophongkh
        rowDiaChiKH.MOTA = this.motadckh

        rowDiaChiKH.SONHA = this.sonhakh
        rowDiaChiKH.DIACHI = this.txtDiaChiKH
        this.dsDiaChi.push(rowDiaChiKH)

        let rowDiaChiHDKH = {}
        rowDiaChiHDKH.DIACHI_ID = this.diachikh_id
        rowDiaChiHDKH.HDKH_ID = this.hdkh_id
        rowDiaChiHDKH.KINHDO = this.Lng_KH
        rowDiaChiHDKH.VIDO = this.Lat_KH
        this.dsDiaChiHDKH.push(rowDiaChiHDKH)
      }

      if (kieu == 2 || kieu == 4 || kieu == 5) {
        let rowDiaChiTT = {}
        if (themmoi == true) {
          await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', {keyname: 'DIACHI'})
            .then(res => {
              if (res.error === '200' && res.data.length > 0) { this.diachitt_id = res.data[0].id }
            }).catch(e => {
              throw e
            })
        }
        rowDiaChiTT.DIACHI_ID = this.diachitt_id
        rowDiaChiTT.TINH_ID = this.tinhtt_id
        rowDiaChiTT.QUAN_ID = this.quantt_id
        rowDiaChiTT.PHUONG_ID = this.phuongtt_id

        if (this.phott_id != 0) { rowDiaChiTT.PHO_ID = this.phott_id }
        if (this.aptt_id != 0) { rowDiaChiTT.AP_ID = this.aptt_id }
        if (this.khutt_id != 0) { rowDiaChiTT.KHU_ID = this.khutt_id }
        if (this.dacdiemtt_id != 0) { rowDiaChiTT.DACDIEM_ID = this.dacdiemtt_id }
        rowDiaChiTT.BLOCK = this.blocktt
        rowDiaChiTT.TANG = this.tangtt
        rowDiaChiTT.PHONG = this.sophongtt
        rowDiaChiTT.MOTA = this.motadctt

        rowDiaChiTT.SONHA = this.sonhatt
        rowDiaChiTT.DIACHI = this.txtDiaChiTT
        this.dsDiaChi.push(rowDiaChiTT)

        let rowDiaChiHDTT = {}
        rowDiaChiHDTT.DIACHI_ID = this.diachitt_id
        rowDiaChiHDTT.HDTT_ID = this.hdtt_id
        rowDiaChiHDTT.KINHDO = this.Lng_TT
        rowDiaChiHDTT.VIDO = this.Lat_TT
        rowDiaChiHDTT.KINHDO_BC = this.Lng_BC
        rowDiaChiHDTT.VIDO_BC = this.Lat_BC
        this.dsDiaChiHDTT.push(rowDiaChiHDTT)
      }
    },

    getDsHDKH () {
      return this.dsHDKH
    },
    getDsHDTT () {
      return this.dsHDTT
    },
    getDsHDKH_SUB () {
      if (this.dsHDKH_Sub && this.dsHDKH_Sub.length) {
        this.dsHDKH_Sub[0].HDKH_ID = this.dsHDKH[0].HDKH_ID
        return this.dsHDKH_Sub
      } else return []
    },
    getDsHDTT_NV () {
      return this.dsHDTT_NV
    },
    getDsDIACHI () {
      return this.dsDiaChi
    },
    getDsDIACHI_HDKH () {
      return this.dsDiaChiHDKH
    },
    getDsDIACHI_HDTT () {
      return this.dsDiaChiHDTT
    },

    /**
     * Kiểm tra dữ liệu khách hàng
     * @param {Boolean}
     */
    async KiemTraDL_KhachHang () {
      // let kq_bc = await this.kiemtra_hd_bancheo_tc(thuebao_id, Enum.LoaiHopDong.DI_CHUYEN)
      // if (kq_bc != 'OK') {
      //   this.$root.toastError(kq_bc)
      //   return false
      // }
      if (!this.txtTenKH) {
        this.$root.toastError('Bạn chưa nhập tên khách hàng !')
        this.$refs.txtTenKH.focus()
        return false
      }
      if (!this.txtDiaChiKH || !this.quankh_id || !this.phuongkh_id) {
        this.$root.toastError('Bạn chưa nhập địa chỉ khách hàng !')
        this.$refs.txtDiaChiKH.focus()
        return false
      }
      // if (txtMaHDcu.Text == "")
      // {
      //   Message_Box.ShowWarning("Bạn chưa nhập mã hợp đồng cho thuê bao dịch chuyển!");
      //   tabThongTin.SelectedIndex = 0;
      //   txtMaHDcu.Focus();
      //   return false;
      // }
      if (!this.txtSoGT) {
        this.$root.toastError('Hãy nhập số giấy tờ !')
        this.$refs.txtSoGT.focus()
        return false
      }
      if (!this.txtNgayCap) {
        this.$root.toastError('Bạn chưa nhập ngày cấp giấy tờ !')
        this.$refs.txtNgayCap.focus()
        return false
      }
      if (!this.txtHoKhau && !this.txtSoGT) {
        this.$root.toastError('Bạn chưa nhập hộ khẩu hoặc loại giấy tờ khác !')
        this.$refs.txtHoKhau.focus()
        return false
      }
      if (!this.txtNguoiDD || (this.txtNguoiDD && this.txtNguoiDD.toString().trim() == '')) {
        this.$root.toastError('Hãy nhập tên người đại diện !')
        this.$refs.txtNguoiDD.focus()
        return false
      }
      if (!this.txtNgaySN) {
        this.$root.toastError('Bạn chưa nhập ngày sinh khách hàng !')
        this.$refs.txtNgaySN.focus()
        return false
      }
      if (this.txtNgaySN) {
        if (this.kieukh_canhan || this.cboGiayToKH == Enum.LoaiGiayTo.CHUNG_MINH_THU) { // Nếu là khách hàng cá nhân thì kiểm tra đã đủ 18 tuổi chưa
          let checkdata = await this.kiemtra_ngaysinh(this.txtNgaySN)
          if (checkdata == 0) {
            this.$root.toastError('Khách hàng chưa đủ 18 tuổi để làm hợp đồng!')
            this.$refs.txtNgaySN.focus()
            return false
          }
        }
      }
      if (!this.TNC1_ID || !this.TNC2_ID || !this.TNC3_ID) {
        this.$root.toastError('Bạn chưa chọn chi tiết ngành nghề.Hãy kiểm tra lại !')
        this.$refs.cboNganhNgheLoc.focus()
        return false
      }
      // Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
      if (this.txtMaKH && this.txtMaKH.toString().trim() != '') {
        let check = await this.Kiemtra_makh_mtt(this.txtMaKH.toString().trim(), 1, Enum.LoaiHopDong.DI_CHUYEN)
        if (check == 0) {
          this.$root.toastError('Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
          this.$refs.txtMaKH.focus()
          return false
        }
      }
      // if (txtMaGD.Text.Trim() != "")
      // {
      //   if (laphd.Kiemtra_makh_mtt(txtMaGD.Text.Trim(), 3, LoaiHopDong.DI_CHUYEN) == 0)
      //   {
      //     Message_Box.ShowWarning("Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!");
      //     txtMaGD.Focus();
      //     return false;
      //   }
      // }
      if (!this.txtTelKH || (this.txtTelKH && this.txtTelKH.toString().trim() == '')) {
        this.$root.toastError('Hãy nhập số điện thoại của khách hàng!')
        this.$refs.txtTelKH.focus()
        return false
      }
      // CuongNM - Kiểm tra nhập số điện thoại theo đúng chuẩn - VB 3309/VNPT VNP-KTNV
      if (this.txtTelKH && this.txtTelKH.toString().trim().length < 10) {
        this.$root.toastError('Số điện thoại của khách hàng không đúng định dạng! Độ dài tối thiểu 10 chữ số.')
        this.$refs.txtTelKH.focus()
        return false
      }
      if (this.txtTelKH && this.txtTelKH.toString().trim().length > 11) {
        this.$root.toastError('Số điện thoại liên hệ của khách hàng không được quá 11 ký tự.')
        this.$refs.txtTelKH.focus()
        return false
      }
      if (!this.IsNumber(this.txtTelKH.toString().trim())) {
        this.$root.toastError('Số điện thoại liên hệ của khách hàng phải là số!')
        this.$refs.txtTelKH.focus()
        return false
      }
      if (!this.txtTelKH.toString().trim().startsWith('0')) {
        this.$root.toastError('Dữ liệu điện thoại liên hệ phải bắt đầu là số 0')
        this.$refs.txtTelKH.focus()
        return false
      }
      if (this.khong_batbuoc_ctv == 0) {
        if (!this.$parent.modelCkeckBox.chkCTV || !this.$parent.modelInput.txtCTV || this.$parent.ctv_id == 0) {
          this.$root.toastError('Bạn chưa chọn nhân viên phát triển!')
          return false
        }
      }
      if (this.batbuoc_gioithieu_dichchuyen == 1) {
        if (!this.$parent.modelCkeckBox.chkNguoiGT || !this.$parent.modelInput.txtNguoiGT || this.$parent.nguoigt_id == 0) {
          this.$root.toastError('Bạn chưa chọn người giới thiệu hợp đồng/phụ lục')
          return false
        }
      }
      if (this.$parent.modelCkeckBox.chkCTV && !this.$parent.modelInput.txtCTV && this.$parent.ctv_id == 0) {
        this.$root.toastError('Bạn chưa chọn nhân viên phát triển!')
        return false
      }
      if (this.$parent.modelCkeckBox.chkNguoiGT && !this.$parent.modelInput.txtNguoiGT && this.$parent.nguoigt_id == 0) {
        this.$root.toastError('Bạn chưa chọn nhân viên giới thiệu hợp đồng!')
        return false
      }

      // region Kiểm tra mã ngân sách
      let loaikh = this.dsLoaiKH.filter(i => i.loaikh_id == this.cboLoaiKH)[0]
      let kieukh = loaikh ? loaikh.loai : ''
      if (!kieukh) {
        this.vkieukh_id_ = parseInt(kieukh)
      }
      if (this.vkieukh_id_ == Enum.KIEU_KH.DOANH_NGHIEP) { // Khách hàng doanh nghiệp
        if (this.cboGiayToKH == Enum.LoaiGiayTo.MA_NGANSACH) {
          let mans = this.txtSoGT.toString().trim()
          if (!this.IsNumber(mans)) {
            this.$root.toastError('Mã ngân sách phải là dạng số.\nBạn hãy kiểm tra lại!')
            return false
          }
          if (mans.length != 7) {
            this.$root.toastError('Mã ngân sách phải đủ 7 ký tự số. \nBạn hãy kiểm tra lại!')
            return false
          }
          let ArrCheck = [1, 2, 3, 7, 8, 9]
          if (!ArrCheck.includes(parseInt(mans.substr(0, 1)))) {
            this.$root.toastError('Mã ngân sách phải bắt đầu là 1/2/3/7/8/9. \nBạn hãy kiểm tra lại!')
            return false
          }
        }
        let mst_ = this.txtMaSTKH ? this.txtMaSTKH.toString().trim() : null
        if (this.ts_kt_mstmns_kh == 1) {
          if (!mst_ && (this.dsHDKH_Sub.length == 0 ||
            (this.dsHDKH_Sub.length > 0 && !this.dsHDKH_Sub[0].MA_NS))) {
            this.$root.toastError('Bạn phải nhập mã số thuế hoặc mã ngân sách')
            return false
          }
          if (mst_) {
            let kq_kt = await this.KiemTra_MST(mst_)
            if (kq_kt != 'OK') {
              this.$root.toastError(kq_kt)
              return false
            }
          }
          if (this.dsHDKH_Sub.length > 0 && this.dsHDKH_Sub[0].MA_NS) {
            let mans = this.dsHDKH_Sub[0].MA_NS
            if (!this.IsNumber(mans)) {
              this.$root.toastError('Mã ngân sách phải là dạng số.\nBạn hãy kiểm tra lại!')
              return false
            }
            if (mans.length != 7) {
              this.$root.toastError('Mã ngân sách phải đủ 7 ký tự số. \nBạn hãy kiểm tra lại!')
              return false
            }
            let ArrCheck = [1, 2, 3, 7, 8, 9]
            if (!ArrCheck.includes(parseInt(mans.substr(0, 1)))) {
              this.$root.toastError('Mã ngân sách phải bắt đầu là 1/2/3/7/8/9. \nBạn hãy kiểm tra lại!')
              return false
            }
          }
        }
      } else {
        if (this.txtMaSTKH && this.txtMaSTKH.toString().trim()) {
          let kq_kt = await this.KiemTra_MST(this.txtMaSTKH.toString().trim())
          if (kq_kt != 'OK') {
            this.$root.toastError(kq_kt)
            return false
          }
        }
      }
      return true
    },
    /**
     * Kiểm tra dữ liệu thanh toán
     */
    async KiemTraDL_ThanhToan () {
      if (!this.txtTenTT) {
        this.$root.toastError('Hãy nhập tên thanh toán !')
        return false
      }
      if (!this.cboMaDD) {
        this.$root.toastError('Hãy chọn mã dại diện !')
        this.$refs.cboGridMaTBDD.focus()
        return false
      }
      if (!this.txtDiaChiTT || !this.quantt_id || !this.phuongtt_id) {
        this.$root.toastError('Hãy nhập địa chỉ thanh toán !')
        this.$refs.txtDiaChiTT.focus()
        return false
      }
      if (!this.txtDiaChiCT) {
        this.$root.toastError('Hãy nhập địa chỉ chứng từ !')
        this.$refs.txtDiaChiCT.focus()
        return false
      }
      // Kiểm tra matt:Hiếu: 10/08/2010
      let kt_matt = await this.KIEMTRA_MATT(this.txtMaTT, this.thanhtoan_id)

      if (kt_matt[0].rs) {
        this.$root.toastError('Kiểm tra lại mã thanh toán !')
        this.$refs.txtMaTT.focus()
        return false
      }
      if (!this.txtMaNVTC) {
        this.$root.toastError('Hãy nhập mã nhân viên thu cước !')
        this.$refs.txtMaNVTC.focus()
        return false
      }
      // Hieutc: 11.10.2011: Kiểm tra mã thanh toán đã theo đúng chuẩn chưa
      if (this.txtMaTT && this.txtMaTT.toString().trim()) {
        let kt = await this.Kiemtra_makh_mtt(this.txtMaTT.toString().trim(), 2, Enum.LoaiHopDong.DI_CHUYEN)
        if (kt == 0) {
          this.$root.toastError('Mã thanh toán không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
          this.$refs.txtMaTT.focus()
          return false
        }
      }
      if (this.dsDonViQL.length <= 0) {
        this.$root.toastError('Địa chỉ thanh toán này chưa được thiết lập đơn vị quản lý !')
        this.$refs.btnDiaChiTT.focus()
        return false
      } else {
        if (!this.cboDonViQL) {
          this.$root.toastError('Địa chỉ thanh toán này chưa được thiết lập đơn vị quản lý !')
          this.$refs.btnDiaChiTT.focus()
          return false
        }
      }
      // KhaiVK: 12/06/2018: Kiểm tra mã số thuế theo chuẩn
      let maSoThue = this.txtMST ? this.txtMST.toString().trim() : null
      if (maSoThue && this.ts_kt_mst == 1) {
        let ketQua = this.KiemTraDinhDangMaSoThue(maSoThue)
        if (ketQua != 'OK') {
          this.$root.toastError(ketQua)
          this.$refs.txtMST.focus()
          return false
        } else {
          let ds_maKH = await this.LAY_DS_MAKH_THEO_MST(maSoThue, this.hdkh_id, this.khachhang_id)
          if (ds_maKH.length == 1) {
            this.$root.toastError('Mã số thuế ' + maSoThue + ' đang được sử dụng bởi mã khách hàng: ' +
              ds_maKH[0]['ma_kh'] + '.')
            this.$refs.txtMST.focus()
            return false
          } else if (ds_maKH.length > 1) {
            let str = ''
            for (let item of ds_maKH) { str += item.ma_kh + ',' }
            this.$root.toastError('Mã số thuế ' + maSoThue + ' đang được sử dụng bởi các mã khách hàng: ' +
              str.substr(0, str.length - 1) + '.')
            this.$refs.txtMST.focus()
            return false
          }
        }
      }
      return true
    },
    /**
     * Lấy danh sách mã khách hàng theo mã số thuế
     */
    LAY_DS_MAKH_THEO_MST: async function (vmasothue, vhdkh_id, vkhachhang_id) {
      let res = await this.$root.context.get(`/web-hopdong/chuyendich/lay_ds_makh_theo_mst?vmasothue=${vmasothue}&vhdkh_id=${vhdkh_id}&vkhachhang_id=${vkhachhang_id}`)
      return res.data
    },
    /**
     * Kiểm tra định dạng mã số thuế
     * @param inMaSoThue
     * @return Boolean
     */
    KiemTraDinhDangMaSoThue (inMaSoThue) {
      try {
        let ketqua = 'OK'
        // Cấu trúc MST 10 số: N1N2N3N4N5N6N7N8N9N10
        // Cấu trúc MST 13 số: N1N2N3N4N5N6N7N8N9N10-N11N12N13
        if (inMaSoThue.trim() != '') {
          if (inMaSoThue.length < 10) {
            ketqua = 'Mã số thuế tối thiểu 10 ký tự.\nBạn hãy kiểm tra lại!'
            return ketqua
          }
          if (inMaSoThue.includes(' ')) {
            ketqua = 'Mã số thuế không được có khoảng trống.\nBạn hãy kiểm tra lại!'
            return ketqua
          }
          let maSoThue = inMaSoThue.substr(0, 10)
          if (!this.IsNumber(maSoThue)) {
            ketqua = 'Mã số thuế phải là dạng số.\nBạn hãy kiểm tra lại!'
            return ketqua
          }
          // N10 -  Chữ số kiểm tra
          let ArrCheck = [ 31, 29, 23, 19, 17, 13, 7, 5, 3 ]
          let checkNumber = 0

          for (let i = 0; i < maSoThue.length - 1; i++) { checkNumber += maSoThue.substr(i, 1) * ArrCheck[i] }
          if (maSoThue.substr(9, 1) != (10 - checkNumber % 11)) {
            ketqua = 'Mã số thuế không hợp lệ!.\nBạn hãy kiểm tra lại!'
            return ketqua
          }
          // Với MST 13 số
          if (inMaSoThue.Length > 10) {
            if (inMaSoThue.Length > 14) {
              ketqua = 'Mã số thuế không hợp lệ.\nBạn hãy kiểm tra lại!'
              return ketqua
            }
            if (inMaSoThue.substr(10, 1) != '-') {
              // ketqua = "Mã số thuế 13 số chỉ bao gồm chữ số và ký tự -.\nBạn hãy kiểm tra lại!";
              ketqua = 'Mã số thuế 13 số không đúng định dạng theo quy định.\nCấu trúc MST 13 số: N1N2N3N4N5N6N7N8N9N10-N11N12N13 \nBạn hãy kiểm tra lại!'
              return ketqua
            }
            // Kiểm tra 3 ký
            let maSoThue2 = inMaSoThue.substr(11, 3)
            if (!this.IsNumber(maSoThue2)) {
              ketqua = '3 ký tự cuối MST phải là dạng số.\nBạn hãy kiểm tra lại!'
              return ketqua
            } else {
              let n3 = parseInt(maSoThue2)
              if (n3 < 1 && n3 > 999) {
                ketqua = 'Theo quy định 3 ký tự cuối MST là các số thứ tự từ 001 đến 999.\nBạn hãy kiểm tra lại!'
                return ketqua
              }
            }
          }
        }
        return ketqua
      } catch (Exception) {
        return 'Mã số thuế không hợp lệ!.\nBạn hãy kiểm tra lại!'
      }
    },
    /**
     * Kiểm tra mã thanh toán
     * @param vma_tt
     * @param vthanhtoan_id
     */
    KIEMTRA_MATT: async function (vma_tt, vthanhtoan_id) {
      let res = await this.$root.context.get(`/web-hopdong/chuyendich/kiemtra_matt?vma_tt=${vma_tt}&vthanhtoan_id=${vthanhtoan_id}`)
      return res.data
    },

    /**
     * Kiểm tra hợp đồng có phải bán chéo hay không
     * @param thuebao_id
     * @param loaihop_id
     */
    kiemtra_hd_bancheo_tc: async function (thuebao_id, loaihop_id) {
      let res = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_hd_bancheo_tc', {
        vthuebao_id: thuebao_id,
        vloaihd_id: loaihop_id
      })
      return res.data
    },
    /**
     * Kiểm tra ngày sinh
     * @param ngaysinh
     */
    kiemtra_ngaysinh: async function (ngaysinh) {
      let res = await this.$root.context.post('/web-hopdong/chuyendich/kiemtra_ngaysinh', {ngaysinh: ngaysinh})
      return res.data
    },
    /**
     * Kiểm tra mã khách hàng, mã thanh toán đúng chuẩn chưa
     * @param ma_kh
     * @param kieu
     * @param vloaihd_id
     */
    Kiemtra_makh_mtt: async function (ma_kh, kieu, vloaihd_id) {
      let res = await this.$root.context.get(`/web-hopdong/chuyendich/kiemtra_makh_mtt?ma_kh=${ma_kh}&kieu=${kieu}&vloaihd_id=${vloaihd_id}`)
      return res.data
    },
    /**
     * Kiểm tra có phải là số không
     */
    IsNumber: function (str) {
      try {
        let num = parseInt(str)
        return true
      } catch (e) {
        return false
      }
    },
    /**
     * Kiểm tra mã số thuế
     */
    KiemTra_MST: async function (mst) {
      let res = await this.$root.context.get('/web-hopdong/chuyendich/kiemtra_mst', {vmst: mst})
      return res.data
    },
    /**
     * Lấy mã giao dịch v2
     * @param donvi_id
     * @param loaihd_id
     */
    Lay_Ma_GD_V2: async function (donvi_id, loaihd_id) {
      let res = await this.$root.context.get(`/web-hopdong/chuyendich/sinh_magd_v2?donvi_id=${donvi_id}&loaihd_id=${loaihd_id}`)
      return res.data
    },
    /**
     * Lấy ip client
     */
    getIp: async function () {
      try {
        const response = await fetch('https://jsonip.com/')
        const data = await response.json()
        return data.ip
      } catch (e) {
        return null
      }
    },
    /**
     *
     */
    MAP_LOAI_NH_ID_NEO: async function (nganhang_id) {
      let rs = await this.$root.context.get('/web-hopdong/chuyendich/map_loai_nh_id_neo', {
        nganhang_id: nganhang_id
      })
      let kq = null
      if (rs && rs.data && rs.data.loai_nh) {
        kq = rs.data.loai_nh
      }
      return kq
    },
    /**
     * Lấy mã giao dịch
     * @param loaihd_id
     */
    Lay_Ma_GD: async function (loaihd_id) {
      let res = await this.$root.context.get(`/web-hopdong/chuyendich/sinh_magd?loaihd_id=${loaihd_id}`)
      return res.data
    },
    /***
     * Lấy danh sách, hiển thị sử dụng dịch vụ khác
     * @param db_id
     * @param vloaiid
     * @returns {Promise<void>}
     */
    async LAY_DS_SUDUNG_DVK (db_id, vloaiid) {
      let dset = []
      this.dsDK_DVK = []
      dset = await this.$root.context.post('/web-hopdong/khoiphucthanhly/lay_ds_sudung_dvk', {
        db_id: db_id,
        loaiid: vloaiid
      })
      if (dset.data && dset.data.length) {
        for (let item of dset.data) {
          let row = {}
          row.DVKHAC_ID = item.dvkhac_id
          row.KIEU_YC = 1
          row.TTTH = Enum.TrangThaiCaiDat.MOI
          row.HD_ID = this.hdtb_id
          row.NGAY_YC = this.dtpNgayYC
          row.LOAIID = vloaiid
          this.dsDK_DVK.push(row)
        }
      }
      if (vloaiid == 1) {
        // tab thue bao
        this.lvDvkhac = []
        for (let item of this.dsDK_DVK) {
          let rs = await this.$root.context.post('web-hopdong/thaydoithongtinkhuyenmai/lay_ds_ttct_dichvukhac', {dvkhac_id: item.DVKHAC_ID})
          if (rs.data && rs.data.length) {
            this.lvDvkhac.push(rs.data[0].ten_dvkhac + ' (Đang SD)')
          }
        }
      } else {
        this.lvDvkhac_TT = []
        for (let item of this.dsDK_DVK) {
          let rs = await this.$root.context.post('web-hopdong/thaydoithongtinkhuyenmai/lay_ds_ttct_dichvukhac', {dvkhac_id: item.DVKHAC_ID})
          if (rs.data && rs.data.length) {
            this.lvDvkhac_TT.push('- ' + rs.data[0].ten_dvkhac + ' (Đang SD)')
          }
        }
      }
    },
    /***
     * Lấy thông tin danh mục chung chuyển dịch
     * @returns {Promise<void>}
     */
    async lay_danhmuc_chuyendich_tt_chung_v2 () {
      try {
        let args = 'TRANG_BI|LOAI_TB|CHU_QUAN|TIEN_KM|LOAI_GT|PHANLOAI_KH|LOAI_KH|NGANHANG|GOI_KT|DONVI_QL|KIEU_LD|THOIHAN|DOITAC'
        await this.$root.context.post('web-hopdong/chuyendich/lay_danhmuc_chuyendich_tt_chung_v2', {vchuoi: args}).then(res => {
          if (res.data != null) {
            this.dsDanhMuc = res.data
            this.dsGiayToKH = res.data.loai_gt
            if (this.dsGiayToKH.length > 0) this.cboGiayToKH = this.dsGiayToKH[0].loaigt_id
            this.dsNganHang = res.data.nganhang
            if (this.dsNganHang.length > 0) {
              this.cboNganHangKH = this.dsNganHang[0].nganhang_id
              this.cboNganHangTT = this.dsNganHang[0].nganhang_id
            }
            this.dsDonViQL = res.data.donvi_ql.filter(i => i.loaidv_id == Enum.LOAI_DV.DONVIQL_TT)
            if (this.dsDonViQL.length) this.cboDonViQL = this.dsDonViQL[0].donvi_id
            this.dsPhanLoaiKH = res.data.phanloai_kh.filter(x => x.nhom_pl == 1)

            this.dsLoaiKH = res.data.loai_kh
          }
        }).catch(e => {
          throw e
        })
      } catch (e) {
        console.log(e)
      }
    },
    /***
     * Lấy thông tin danh mục combobox (sử dụng data từ parent)
     * @returns {Promise<void>}
     */
    lay_dulieu_combobox (res) {
      // let args = 'DICHVU_VT|LOAIHINH_TB|DANTOC|QUOCTICH|NGANHNGHE|TIEUNGANH_C1|TIEUNGANH_C2|TIEUNGANH_C3|TRANG_BI|THOIHAN|CHUQUAN|LOAIKENH|DOITUONG|HINHTHUC_TT|HINHTHUC_TC|KIEU_HD'
      // await this.$root.context.post('/web-hopdong/chuyendich/lay_danhmuc_chung', {vchuoi: args}).then(res => {
      //
      // }).catch(e => {
      // })
      if (res.data != null) {
        this.glbDanhMuc_Chung = res.data
        this.dsHTTT = res.data.hinhthuc_tt
        if (this.dsHTTT.length > 0) this.cboHTTT = this.dsHTTT[0].httt_id
        this.dsDanToc = res.data.dantoc
        if (this.dsDanToc.length > 0) this.cboDanToc = this.dsDanToc[0].dantoc_id
        this.dsQuocTich = res.data.quoctich
        if (this.dsQuocTich.length > 0) this.cboQuocTich = this.dsQuocTich[0].quoctich_id
        // this.DSKieu_HD = res.data.kieu_hd
        // if (this.DSKieu_HD.length > 0) this.cboKieu_HD = this.DSKieu_HD[0].kieuhd_id
      }
    },
    /***
     * Lấy thông tin ngành nghề
     */
    async HT_NganhNgheLoc_Combobox () {
      await this.$root.context.get('/web-ccdv/hoanthienhschuyenquyen/lay-ds-nganhnghe', {ma: 'NGANHNGHE'}).then(res => {
        if (res.data.length) {
          this.dsNganhNgheLoc = res.data
          this.cboNganhNgheLoc = res.data[0].nganhnghe_id
        }
      })
    },
    /***
     * Lấy thông tin mã thuê bao - đại diện theo thanh toán id
     * @param thanhtoan_id
     * @returns []
     */
    async HT_COMBO_MA_DAIDIEN (thanhtoan_id) {
      try {
        let res = await this.$root.context.get('/web-hopdong/chuyendich/ht_combo_ma_daidien', {thanhtoan_id: thanhtoan_id})
        return res.data || null 
      } catch (err) {
        console.log(err)
        return null
      }
      
    },
    // /***
    //  * Lấy thông tin tham số mặc định
    //  * @param ma_ts
    //  * @returns {Promise<null|*>}
    //  */
    // async lay_thamso_macdinh (ma_ts) {
    //   try {
    //     let rs = await this.$root.context.get(
    //       '/web-hopdong/chuyendich/lay_danhsach_thamso',
    //       {
    //         kieu: ma_ts
    //       }
    //     )
    //     return rs.data
    //   } catch (error) {
    //     if (error.response && error.response.data.message_detail) this.$root.toastError('Lỗi khi load dữ liệu: ' + error.response.data.message_detail)
    //     return null
    //   } finally {
    //   }
    // },

    /***
     * Lấy thông tin phân tập - doanh thu khách hàng
     * @param khachhang_id
     * @returns {Promise<*>}
     */
    async phantap_laydoanhthu_dtn (khachhang_id) {
      let res = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/phantap_laydoanhthu_dtn/${khachhang_id}`)
      return res.data
    },

    /***
     * Lấy thông tin phân loại khách hàng theo phanloaikh_id
     * @param input
     * @returns {Promise<*>}
     */
    MAP_PHANLOAIKH_ID_NEO: async function (input) {
      // let data = {
      //   P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
      //   P_PHANLOAIKH_ID: input,
      //   PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_PHANLOAIKH_ID_NEO'
      // }
      let rs = await this.$root.context.get('/web-hopdong/chuyendich/map_phanloaikh_id_neo', {phanloaikh_id: input})
      return rs.data
    },
    /***
     * Lấy main_ghep từ HD_MIG theo hdmig_id
     * @param hdmig_id
     * @returns {string|null}
     */
    MAP_ID_MAIN_GHEP_HD_MIG_NEO: async function (hdmig_id) {
      await this.$root.context.get('/web-hopdong/chuyendich/map_id_main_ghep_hd_mig_neo', {hdmig_id: hdmig_id}).then(res => {
        if (res.data) { return res.data.main_ghep } else return null
      }).catch(e => {
        return null
      })
    },
    /***
     * Lấy mã nhân viên theo nhanvien_id
     * @param nhanvien_id
     * @returns {string|null}
     */
    MAP_ID_NHANVIEN_ID_NEO: async function (nhanvien_id) {
      // let data = {
      //   P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
      //   NHANVIEN_ID: nhanvienid,
      //   PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_NHANVIEN_ID_NEO'
      // }
      let rs = null
      await this.$root.context.get('/web-hopdong/chuyendich/map_id_nhanvien_id_neo', {nhanvien_id: nhanvien_id}).then(res => {
        if (res.data) { rs = res.data.ma_nv } else rs = null
      }).catch(e => {
        rs = null
      })
      return rs
    },
    /***
     * Lấy mã tuyến của TUYENTHU theo tuyenthu_id
     * @param tuyenthu_id
     * @returns {string|null}
     */
    MAP_ID_TUYENTHU_ID_NEO: async function (tuyenthu_id) {
      // let data = {
      //   P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
      //   NHANVIEN_ID: nhanvienid,
      //   PROCEDURE_NAME: 'CSS.PKG_HTHS_CHUYENQUYEN.SP_MAP_TUYENTHU_ID_NEO'
      // }
      await this.$root.context.get('/web-hopdong/chuyendich/map_id_tuyenthu_id_neo', {tuyenthu_id: tuyenthu_id}).then(res => {
        if (res.data) { return res.data.ma_tuyen } else return null
      }).catch(e => {
        return null
      })
    },
    /**
     * Lấy thông tin danh sách hợp đồng thanh toán theo hdkh_id
     * @param hdkh_id
     * @return {Array|*}
     */
    LAY_DS_HOPDONG_TT_THEO_HDKH_ID: async function (hdkh_id) {
      let res = await this.$root.context.get('/web-hopdong/chuyendich/lay_ds_hopdong_tt_theo_hdkh_id', {hdkh_id: hdkh_id})
      return res.data
    },
    /**
     * Lấy danh sách địa chỉ theo hdkh_id
     * @param hdkh_id
     * @return {Array|*}
     */
    LAY_DS_DIACHI_THEO_HDKH_ID: async function (hdkh_id) {
      let res = await this.$root.context.get('/web-hopdong/chuyendich/lay_ds_diachi_theo_hdkh_id', {hdkh_id: hdkh_id})
      return res.data
    },
    /**
     * Lấy danh sách địa chỉ theo hdtt_id
     * @param hdtt_id
     * @return {Array|*}
     */
    LAY_DS_DIACHI_THEO_HDTT_ID: async function (hdtt_id) {
      let res = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_hdttid', {hdttId: hdtt_id})
      return res.data
    },
    /**
     * Lấy danh sách địa chỉ danh bạ theo khachhang_id
     * @param khachhang_id
     * @return {Array|*}
     */
    LAY_DS_DIACHI_THEO_DBKHID: async function (khachhang_id) {
      let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_diachi_theo_khid', {khachHangId: khachhang_id})
      return res.data
    },
    /**
     * Lấy danh sách địa chỉ danh bạ theo thanhtoan_id
     * @param khachhang_id
     * @return {Array|*}
     */
    LAY_DS_DIACHI_THEO_DBTTID: async function (thanhtoan_id) {
      let res = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid', {dbttid: thanhtoan_id})
      return res.data
    },
    /***
     * Chuẩn hóa tên
     * @param ten
     * @returns {string|*}
     * @constructor
     */
    ChuanHoaTen: function (ten) {
      if (ten != null) {
        let convertToArray = ten.toLowerCase().split(' ')
        let result = convertToArray.map(function (val) {
          return val.replace(val.charAt(0), val.charAt(0).toUpperCase())
        })
        return result.join(' ')
      } else { return '' }
    },
    /***
     * Convert Date '-' -> '/'
     * @param data
     * @returns {string|null}
     */
    // formatDate: function (data) {
    //   if (data) {
    //     return data.substr(0, 10).split('-').reverse().join('/')
    //   }
    //   return null
    // },
    /**
     * Xử lý khi thay đổi ngành nghề
     */
    cboNganhNgheLoc_EditValueChanged (val) {
      let select_id = val
      if (select_id < 0) return
      if (this.nganhnghe_id !== select_id) {
        this.TNC1_ID = 0
        this.TNC2_ID = 0
        this.TNC3_ID = 0
        this.TNC4_ID = 0
      }
      this.btnNganhNghe_Click()
    },
    /**
     * Mở popup Thông tin khách hàng bổ sung
     */
    async btnTTKhac_Click () {
      try {
        if (!this.cboLoaiKH || this.cboLoaiKH == -1) {
          this.$root.toastError('Bạn hãy chọn loại khách hàng trước')
          return
        }
        if (!this.$parent.tsbtnNhapMoi && this.khachhang_id > 0) {
          this.dsHDKH_Sub = []
          let dthdkh_sub = await this.$root.context.get('web-hopdong/thaydoithongtinkhuyenmai/lay_thongtin_dbkh_sub', {
            khachhang_id: this.khachhang_id
          })
          if (dthdkh_sub && dthdkh_sub.length > 0) { this.TAO_DULIEU_HDKH_SUB(dthdkh_sub, 0) }
        }
        let dt = this.dsLoaiKH
        if (dt == null && dt.length == 0) {
          this.$root.toastError('Không có thông tin loại khách hàng')
          return
        }
        let vkieukh_id_ = -1
        let dsKieuKH = dt.filter(item => item.loaikh_id == this.cboLoaiKH)[0]
        let kieukh = dsKieuKH ? dsKieuKH.loai + '' : ''
        if (kieukh !== '') { vkieukh_id_ = kieukh }
        this.objTTKHBoSung = {}
        this.objTTKHBoSung.hdkh_id = this.hdkh_id
        this.objTTKHBoSung.nhapmoi = !this.$parent.tsbtnNhapMoi
        this.objTTKHBoSung.vkieukh_id_ = vkieukh_id_
        this.objTTKHBoSung.mast = this.txtMaSTKH
        this.objTTKHBoSung.ts_kt_mstmns_kh = this.ts_kt_mstmns_kh
        this.objTTKHBoSung.ts_kt_tthdkhdn = this.ts_kt_tthdkhdn
        this.objTTKHBoSung.dichvuvt_id = this.ThongTinThueBao.inputValue.dichvuvt_id
        this.$bvModal.show('popupThongTinKHBoSung')
      } catch (e) {
        console.log(e)
      }
    },
    /**
     * Mở popup Chi tiết ngành nghề
     */
    btnNganhNghe_Click () {
      this.objChiTietNganhNghe = {}
      this.objChiTietNganhNghe.nganhnghe_id = this.cboNganhNgheLoc
      this.objChiTietNganhNghe.TNC1_ID = this.TNC1_ID
      this.objChiTietNganhNghe.TNC2_ID = this.TNC2_ID
      this.objChiTietNganhNghe.TNC3_ID = this.TNC3_ID
      this.objChiTietNganhNghe.TNC4_ID = this.TNC4_ID
      this.$bvModal.show('popupChiTietNganhNghe')
    },
    /**
     * Xử lý button Loại khách hàng
     */
    btnLoaiKH_Click () {
      if (this.cboLoaiKH !== null && this.cboLoaiKH !== '-1') {
        let loaiKH = this.dsLoaiKH.find(item => item.loaikh_id == this.cboLoaiKH)
        let kieukh_id = -1
        let kieukh = loaiKH ? loaiKH.loai : ''
        if (kieukh !== '') { kieukh_id = parseInt(kieukh.toString(), 10) }
        let nhomlkh_id = -1
        let nhomkh = loaiKH ? loaiKH.nhomlkh_id : ''
        if (nhomkh !== '') { nhomlkh_id = parseInt(nhomkh.toString(), 10) }
        this.objLoaiKH = {}
        this.objLoaiKH.loaikh_id = this.cboLoaiKH
        this.objLoaiKH.kieukh_id = kieukh_id
        this.objLoaiKH.nhomlkh_id = nhomlkh_id
        this.$bvModal.show('popupLoaiKH')
      }
    },
    /**
     * Nhận kết quả từ form Loại khách hàng
     */
    getLoaiKHID (loaikh_id) {
      console.log('getLoaiKHID:', loaikh_id)
      // this.cbo_loaiKH.Value = loaikh_id
      let grid = this.$refs['cboGridLoaiKH'].$children[0].grid
      let idx = grid.getCurrentViewRecords().findIndex(item => item.loaikh_id == loaikh_id)
      if (idx > -1) {
        setTimeout(() => {
          grid.selectRow(idx)
        }, 200)
        // this.cboLoaiKH2_EditValueChanged(this.dsLoaiKH[idx])
      }
    },
    /**
     * Xử lý sự kiện khi thay đổi cboLoaiKH
     */
    async cboLoaiKH2_EditValueChanged (item) {
      console.log('cboLoaiKH2_EditValueChanged:', this.cboLoaiKH, item)
      if (this.cboLoaiKH == null || this.cboLoaiKH == '-1') { return }
      let drPLKH = this.dsPhanLoaiKH.filter(i => i.loaikh_id == item.loaikh_id)
      if (this.nhomplkh <= 1) { // chỉ thay đổi nếu nhóm pl là ko theo doanh thu
        this.PHANLOAIKH_ID = 0
        this.txtPhanLoaiKH = ''
        if (drPLKH.length > 0) {
          this.PHANLOAIKH_ID = drPLKH[0].phanloaikh_id
          this.txtPhanLoaiKH = drPLKH[0].ma_plkh
        } else {
          this.vkieukh_id_ = 0
          let kieukh_id = 0, kieukh = ''
          let loaiKH = this.dsLoaiKH.filter(i => i.loaikh_id == item.loaikh_id)[0]
          kieukh = loaiKH ? loaiKH.loai.toString() : ''
          if (kieukh != '') { this.vkieukh_id_ = kieukh_id = parseInt(kieukh) }
          if (kieukh_id == Enum.KIEU_KH.CA_NHAN) {
            let dr_PLKH = this.dsPhanLoaiKH.filter(i => i.hienthi_md == Enum.PHANLOAI_KH.CA_NHAN)
            if (dr_PLKH.length > 0) {
              this.PHANLOAIKH_ID = dr_PLKH[0].phanloaikh_id
              this.txtPhanLoaiKH = dr_PLKH[0].ma_plkh
            }
            this.lblNgaySN = 'Ngày sinh '
            this.enableRdoGTNam = true
            this.enableRdoGTNone = false
            this.enableRdoGTNu = true
            this.rdoGioiTinh = 0 // nam
            this.kieukh_canhan = true
          } else {
            let dr_PLKH = this.dsPhanLoaiKH.filter(i => i.hienthi_md == Enum.PHANLOAI_KH.DOANH_NGHIEP)
            if (dr_PLKH.length > 0) {
              this.PHANLOAIKH_ID = dr_PLKH[0].phanloaikh_id
              this.txtPhanLoaiKH = dr_PLKH[0].ma_plkh
            }
            this.lblNgaySN = 'Ngày TL '
            this.enableRdoGTNam = false
            this.enableRdoGTNone = true
            this.enableRdoGTNu = false
            this.rdoGioiTinh = null // none
            this.kieukh_canhan = false
          }
        }
      }
      // string strLoai = "select b.loai from " +
      //   DatabaseConstants.DB2 + ".loai_kh a, " +
      //   DatabaseConstants.DB2 + ".nhom_lkh b where a.nhomlkh_id=b.nhomlkh_id and a.loaikh_id=" + cboLoaiKH.EditValue.ToString();

      // hoangpkn : 25/10/2017 Thêm nhân viên quản lý
      /***
       * xử lý ở phần popupChonDonVi ở watch.để tránh lặp lại khi load lại dữ liệu trùng
       */
      try {
        // if (this.phuongtt_id && (this.phott_id || this.aptt_id || this.khutt_id)) {
        //   let ds = []
        //   if (this.DONVIQL_HTTT == 1) {
        //     ds = await this.LAY_DS_DONVIQL_V2(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 1,
        //       this.cboLoaiKH, this.cboHTTT)
        //   } else {
        //     if (this.phantap_kh) {
        //       ds = await this.LAY_DS_DONVIQL_PHANTAP(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id,
        //         this.dacdiemtt_id, 1, this.cboLoaiKH, this.cboNganhNgheLoc, this.pt_doanhthu, this.pt_diem_tn)
        //     } else {
        //       ds = await this.LAY_DS_DONVIQL(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 1, this.cboLoaiKH)
        //     }
        //   }
        //   if (ds.length <= 0) {
        //     if (this.dsDonViQL.length != 1) {
        //       this.$root.toastError('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!')
        //       this.cboDonViQL = null
        //       return
        //     }
        //   } else if (ds.length == 1) {
        //     this.cboDonViQL = ds[0].donviql_id
        //   } else {
        //     // mở frmChonDonVi
        //     // show(donvi_ql_id, in_donvi_id, donvi_index_id, kieu, ds)
        //     let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.cboDonViQL, null, ds)
        //     if (resolvePromise.ok) { this.cboDonViQL = resolvePromise.data.donviql_id } else return
        //   }
        // }
        if (this.load_ds_nvtc) {
          await this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id,
            this.cboDonViQL, this.dacdiemtt_id)
        }
        if (this.DONVIQL_HTTT == 1) {
          await this.HienThi_NVQL_AM_HTTT(this.cboLoaiKH, Enum.Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id,
            this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cboDonViQL, this.cboHTTT)
        } else {
          if (this.phantap_kh) {
            await this.HienThi_NVQL_AM_PTKH(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id,
              this.dacdiemtt_id, this.cboLoaiKH, this.cboNganhNgheLoc, this.pt_doanhthu, this.pt_diem_tn,
              Enum.Loai_NV.NHANVIEN_AM, this.cboDonViQL)
          } else {
            await this.HienThi_NVQL_AM(this.cboLoaiKH, Enum.Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id,
              this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cboDonViQL)
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    /**
     * Xử lý sự kiện keypress 0 của Mã nhân viên thu cước
     */
    txtMaNVTC_KeyPress () {
      this.btnTimMaNVTC_Click()
    },
    /**
     * Tìm mã nhân viên thu cước
     */
    btnTimMaNVTC_Click () {
      this.inputObjFrmTraCuuNVTC = {
        'QUAN_ID': this.quantt_id,
        'PHUONG_ID': this.phuongtt_id,
        'PHO_ID': this.phott_id,
        'AP_ID': this.aptt_id,
        'KHU_ID': this.khutt_id,
        'DACDIEM_ID': this.dacdiemtt_id,
        'NHANVIENTC_ID': this.nhanvientc_id,
        'TUYENTHU_ID': this.tuyenthu_id,
        'DONVIQL_ID': this.cboDonViQL
        // KT_KYLAI: true,
      }
      this.$bvModal.show('dlgTimKiemNVTC')
    },
    /**
     * Xử lý sự kiện keypress 0 của địa chỉ thanh toán
     */
    txtDiaChiTT_KeyPress () {
      this.btnDiaChiTT_Click()
    },
    /**
     * Nhận kết quả trả về từ form Chi tiết ngành nghề
     */
    async getTNC (objTNC) {
      this.TNC1_ID = objTNC.TNC1_ID
      this.TNC2_ID = objTNC.TNC2_ID
      this.TNC3_ID = objTNC.TNC3_ID
      this.TNC4_ID = objTNC.TNC4_ID
      this.nganhnghe_id = objTNC.nganhnghe_id
      this.cboNganhNgheLoc = this.nganhnghe_id
      try {
        if (this.phantap_kh) {
          if (this.phuongtt_id && (this.phott_id || this.aptt_id || this.khutt_id)) {
            let ds = []
            ds = await this.LAY_DS_DONVIQL_PHANTAP(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.dacdiemtt_id, 1,
              this.cboLoaiKH, this.cboNganhNgheLoc, this.pt_doanhthu, this.pt_diem_tn)

            if (ds.length <= 0) {
              if (this.dsDonViQL.length != 1) {
                this.$root.toastError('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!')
                this.cboDonViQL = null
                return
              }
            } else if (ds.length == 1) { this.cboDonViQL = ds[0].donviql_id } else {
              // mở frmChonDonVi
              // show(donvi_ql_id, in_donvi_id, donvi_index_id, kieu, ds)
              let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.cboDonViQL, null, ds)
              if (resolvePromise.ok) { this.cboDonViQL = resolvePromise.data.donviql_id } else return
            }
            await this.HienThi_NVQL_AM_PTKH(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.dacdiemtt_id,
              this.cboLoaiKH, this.cboNganhNgheLoc, this.pt_doanhthu, this.pt_diem_tn, Enum.Loai_NV.NHANVIEN_AM, this.cboDonViQL)
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    /**
     * Nhận kết quả trả về từ form Địa chỉ
     */
    async getDiaChiTuFormDiaChi (diachi) {
      console.log('getDiaChiTuFormDiaChi:', {...diachi})
      let data = {...diachi}
      try {
        // this.loading(true)
        switch (this.diachi_flag) {
          case 'DIACHI_KH':
            this.tinhkh_id = data.tinh_id
            this.quankh_id = data.quan_id
            this.phuongkh_id = data.phuong_id
            this.phokh_id = data.pho_id
            this.apkh_id = data.ap_id
            this.khukh_id = data.khu_id
            this.dacdiemkh_id = data.dacdiem_id
            this.Lng_KH = data.longtitude
            this.Lat_KH = data.lattitude
            this.sonhakh = data.so_nha
            this.txtDiaChiKH = data.diachimoi
            this.diachi_flag = null
            this.$refs.txtDiaChiKH.focus()
            // Hiếu thêm địa chỉ NDD: ngày 30/07/2010
            this.tinhndd_id = data.tinh_id
            this.quanndd_id = data.quan_id
            this.phuongndd_id = data.phuong_id
            this.phondd_id = data.pho_id
            this.apndd_id = data.ap_id
            this.khundd_id = data.khu_id
            this.dacdiemndd_id = data.dacdiem_id
            this.sonhandd = data.so_nha
            this.txtDiaChiNDD = data.diachimoi
            this.blockkh = data.block
            this.tangkh = data.tang
            this.sophongkh = data.sophong
            this.motadckh = data.motathem
            break
          case 'DIACHI_NDD':
            this.diachi_flag = null
            this.tinhndd_id = data.tinh_id
            this.quanndd_id = data.quan_id
            this.phuongndd_id = data.phuong_id
            this.phondd_id = data.pho_id
            this.dacdiemndd_id = data.dacdiem_id
            this.apndd_id = data.ap_id
            this.khundd_id = data.khu_id
            this.sonhandd = data.so_nha
            this.txtDiaChiNDD = data.diachimoi
            this.$refs.txtDiaChiNDD.focus()
            break
          case 'DIACHI_TT':
            this.diachi_flag = null
            let ds = []
            let _phuongtt_id = data.phuong_id ? data.phuong_id : 0,
              _phott_id = data.pho_id ? data.pho_id : 0,
              _aptt_id = data.ap_id ? data.ap_id : 0,
              _khutt_id = data.khu_id ? data.khu_id : 0,
              _dacdiemtt_id = data.dacdiem_id ? data.dacdiem_id : 0
            if (this.DONVIQL_HTTT == 1) {
              ds = await this.LAY_DS_DONVIQL_V2(_phuongtt_id, _phott_id, _aptt_id, _khutt_id, 1, this.cboLoaiKH, this.cboHTTT)
            } else {
              if (this.phantap_kh) {
                ds = await this.LAY_DS_DONVIQL_PHANTAP(_phuongtt_id, _phott_id, _aptt_id, _khutt_id,
                  _dacdiemtt_id, 1, this.cboLoaiKH, this.cboNganhNgheLoc, this.pt_doanhthu, this.pt_diem_tn)
              } else {
                ds = await this.LAY_DS_DONVIQL(_phuongtt_id, _phott_id, _aptt_id, _khutt_id, 1, this.cboLoaiKH)
              }
            }
            if (ds.length == 0) {
              if (this.dsDonViQL.length != 1) {
                this.$root.toastError('Địa danh này chưa được gán đơn vị quản lý. Hãy liên hệ với Admin để xử lý!')
                return
              }
            } else if (ds.length == 1) {
              this.cboDonViQL = ds[0].donviql_id
            } else {
              // mở frmChonDonVi
              // show(donvi_ql_id, in_donvi_id, donvi_index_id, kieu, ds)
              let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.cboDonViQL, null, ds)
              if (resolvePromise.ok) { this.cboDonViQL = resolvePromise.data.donviql_id } else return
            }
            this.tinhtt_id = data.tinh_id
            this.quantt_id = data.quan_id
            this.phuongtt_id = data.phuong_id
            this.phott_id = data.pho_id
            this.aptt_id = data.ap_id
            this.khutt_id = data.khu_id
            this.dacdiemtt_id = data.dacdiem_id
            this.Lng_TT = data.longtitude
            this.Lat_TT = data.lattitude
            this.sonhatt = data.so_nha
            this.txtDiaChiTT = data.diachimoi
            this.blocktt = data.block
            this.tangtt = data.tang
            this.sophongtt = data.sophong
            this.motadctt = data.motathem
            if (!this.txtDiaChiCT) {
              // Lấy địa chỉ chứng từ theo địa chỉ thanh toán
              this.tinhct_id = data.tinh_id
              this.quanct_id = data.quan_id
              this.phuongct_id = data.phuong_id
              this.phoct_id = data.pho_id
              this.apct_id = data.ap_id
              this.khuct_id = data.khu_id
              this.dacdiemct_id = data.dacdiem_id
              this.sonhact = data.so_nha
              this.txtDiaChiCT = data.diachimoi
            }
            if (!this.txtDiaChiBC) {
              this.txtMaNVTC = '' // Nếu chọn lại địa chỉ  thanh toán thì clear mã nhân viên thu cước - Hiếu 29.09.2010
              this.txtMaTuyen = ''
              /// TuanNA: Lấy mã NVTC theo địa chỉ tt
              /// Ngày 07.12.2012
              await this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.cboDonViQL, this.dacdiemtt_id)
              if (this.DONVIQL_HTTT == 1) {
                await this.HienThi_NVQL_AM_HTTT(this.cboLoaiKH, Enum.Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id,
                  this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cboDonViQL, this.cboHTTT)
              } else {
                if (this.phantap_kh) {
                  await this.HienThi_NVQL_AM_PTKH(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id,
                    this.dacdiemtt_id, this.cboLoaiKH, this.cboNganhNgheLoc,
                    this.pt_doanhthu, this.pt_diem_tn, Enum.Loai_NV.NHANVIEN_AM, this.cboDonViQL)
                } else {
                  await this.HienThi_NVQL_AM(this.cboLoaiKH, Enum.Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id,
                    this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cboDonViQL)
                }
              }
            }
            this.$refs.txtDiaChiTT.focus()
            break
          case 'DIACHI_CT':
            this.diachi_flag = null
            this.$refs.txtDiaChiCT.focus()
            this.tinhct_id = data.tinh_id
            this.quanct_id = data.quan_id
            this.phuongct_id = data.phuong_id
            this.phoct_id = data.pho_id
            this.dacdiemct_id = data.dacdiem_id
            this.apct_id = data.ap_id
            this.khuct_id = data.khu_id
            this.sonhact = data.so_nha
            this.txtDiaChiCT = data.diachimoi
            break
          case 'DIACHI_BC':
            this.diachi_flag = null
            this.$refs.txtDiaChiBC.focus()
            // Lamnt: 07/05/2014 - Sửa lại hàm lấy đơn vị quản lý địa chỉ thanh toán
            let _ds = []
            let _phuongbc_id = data.phuong_id ? data.phuong_id : 0,
              _phobc_id = data.pho_id ? data.pho_id : 0,
              _apbc_id = data.ap_id ? data.ap_id : 0,
              _khubc_id = data.khu_id ? data.khu_id : 0,
              _dacdiembc_id = data.dacdiem_id ? data.dacdiem_id : 0
            if (this.DONVIQL_HTTT == 1) {
              _ds = await this.LAY_DS_DONVIQL_V2(_phuongbc_id, _phobc_id, _apbc_id, _khubc_id, 1, this.cboLoaiKH, this.cboHTTT)
            } else {
              if (this.phantap_kh) {
                _ds = await this.LAY_DS_DONVIQL_PHANTAP(_phuongbc_id, _phobc_id, _apbc_id, _khubc_id, _dacdiembc_id,
                  1, this.cboLoaiKH, this.cboNganhNgheLoc, this.pt_doanhthu, this.pt_diem_tn)
              } else {
                _ds = await this.LAY_DS_DONVIQL(_phuongbc_id, _phobc_id, _apbc_id, _khubc_id, 1, this.cboLoaiKH)
              }
            }
            if (_ds.length == 0) {
              if (this.dsDonViQL.length != 1) {
                this.$root.toastError('Địa danh này chưa được gán đơn vị quản lý. Hãy liên hệ với Admin để xử lý!')
                return
              }
            } else if (_ds.length == 1) {
              this.cboDonViQL = _ds[0].donviql_id
            } else {
              // mở frmChonDonVi
              // show(donvi_ql_id, in_donvi_id, donvi_index_id, kieu, ds)
              let donvi_dl_id = this.$root.context.user.getProperty('donvi_dl_id').toString()
              let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, donvi_dl_id, null, _ds)
              if (resolvePromise.ok) { this.cboDonViQL = resolvePromise.data.donviql_id } else return
            }
            this.txtMaNVTC = ''// Nếu chọn lại địa chỉ báo cước thì clear mã nhân viên thu cước - Hiếu 29.09.2010
            this.txtMaTuyen = ''
            // Địa chỉ báo cước
            this.tinhbc_id = data.tinh_id
            this.quanbc_id = data.quan_id
            this.phuongbc_id = data.phuong_id
            this.phobc_id = data.pho_id
            this.dacdiembc_id = data.dacdiem_id
            this.apbc_id = data.ap_id
            this.khubc_id = data.khu_id
            this.sonhabc = data.so_nha
            this.txtDiaChiBC = data.diachimoi
            /// TuanNA: Lấy mã NVTC theo địa chỉ bc
            /// Ngày 07.12.2012
            await this.HT_NVTC_TheoDC(this.quanbc_id, this.phuongbc_id, this.phobc_id, this.apbc_id, this.khubc_id,
              this.cboDonViQL, this.dacdiembc_id)
            if (this.phantap_kh) {
              await this.HienThi_NVQL_AM_PTKH(this.phuongbc_id, this.phobc_id, this.apbc_id, this.khubc_id, this.dacdiembc_id, this.cboLoaiKH,
                this.cboNganhNgheLoc, this.pt_doanhthu, this.pt_diem_tn, Enum.Loai_NV.NHANVIEN_AM, this.cboDonViQL)
            }
            break
        }
      } catch (e) {
        console.log(e)
      } finally {
        // this.loading(false)
      }
    },
    /**
     * Lấy danh sách đơn vị quản lý phân tập
     */
    async LAY_DS_DONVIQL_PHANTAP (phuong_id, pho_id, ap_id, khu_id, dacdiem_id, kieu, loaikh_id, nganhnghe_id, doanhthu, diemtn) {
      let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_ptkh_v2', {
        phuong_id: phuong_id || 0,
        pho_id: pho_id || 0,
        ap_id: ap_id || 0,
        khu_id: khu_id || 0,
        dacdiem_id: dacdiem_id || 0,
        kieu: kieu,
        loaikh_id: loaikh_id,
        nganhnghe_id: nganhnghe_id,
        doanhthu: doanhthu,
        diemtn: diemtn
      })
      return rs.data
    },
    /**
     *  Lấy danh sách đơn vị quản lý v2
     */
    async LAY_DS_DONVIQL_V2 (phuong_id, pho_id, ap_id, khu_id, kieu, loaikh_id, httt_id) {
      let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_v2', {
        phuong_id: phuong_id || 0,
        pho_id: pho_id || 0,
        ap_id: ap_id || 0,
        khu_id: khu_id || 0,
        kieu: kieu,
        loaikh_id: loaikh_id,
        httt_id: httt_id
      })
      return rs.data
    },
    /**
     * Lấy danh sách đơn vị quản lý
     */
    async LAY_DS_DONVIQL (phuong_id, pho_id, ap_id, khu_id, kieu, loaikh_id) {
      let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql', {
        phuong_id: phuong_id || 0,
        pho_id: pho_id || 0,
        ap_id: ap_id || 0,
        khu_id: khu_id || 0,
        kieu: kieu,
        loaikh_id: loaikh_id
      })
      return rs.data
    },
    /**
     * Hiển nhân viên thu cước theo địa chỉ
     */
    HT_NVTC_TheoDC: async function (quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id) {
      try {
        let ds = []
        if (this.TUYENTHU_HTTT == 1 && this.cboHTTT) {
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
            QUAN_ID: quan_id,
            PHUONG_ID: phuong_id,
            PHO_ID: pho_id,
            AP_ID: ap_id,
            KHU_ID: khu_id,
            DACDIEM_ID: dacdiem_id,
            NHANVIENTC_ID: this.nhanvientc_id,
            TUYENTHU_ID: ds[0].tuyenthu_id,
            DONVIQL_ID: donvi_ql_id
          }
          if (this.TUYENTHU_HTTT == 1 && this.cboHTTT) { this.inputObjFrmTraCuuNVTC.HTTT_ID = this.cboHTTT }
          this.$bvModal.show('dlgTimKiemNVTC')
        } else if (ds.length > 0) {
          this.txtMaNVTC = ds[0].manv_tc
          if (ds[0].tuyenthu_id) {
            this.tuyenthu_id = ds[0].tuyenthu_id
          }
          this.txtMaTuyen = ds[0].ma_tuyen
          this.nhanvientc_id = ds[0].nhanvien_id
        } else {
          this.txtMaNVTC = ''
          this.txtMaTuyen = ''
          this.tuyenthu_id = 0
          this.nhanvientc_id = 0
        }
      } catch (error) {

      }
    },
    /**
     * Lấy dữ liệu từ form tra cứu nhân viên thu cước
     */
    async getTraCuuNVTC (result) {
      console.log(result)
      if (this.quantt_id || this.phuongtt_id) {
        this.nhanvientc_id = result.nhanVienTC_Id
        this.txtMaNVTC = result.ma_nvtc
        this.txtMaTuyen = result.ma_tuyen
        this.tuyenthu_id = result.tuyenthu_id
        if (this.DONVIQL_HTTT == 1) {
          await this.HienThi_NVQL_AM_HTTT(this.cboLoaiKH, Enum.Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id,
            this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cboDonViQL, this.cboHTTT)
        } else if (!this.phantap_kh) {
          await this.HienThi_NVQL_AM(this.cboLoaiKH, Enum.Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id,
            this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cboDonViQL)
        }
      } else {
        this.$root.toastError('Chưa có thông tin về địa chỉ thanh toán\nBạn kiểm tra lại!')
        this.txtMaNVTC = ''
        this.txtMaTuyen = ''
        this.$refs.txtMaNVTC.focus()
        this.nhanvientc_id = 0
        this.txtNhanVienQL = ''
        this.nhanvien_ql_am_id = 0
      }
    },
    /**
     * Hiển thị nhân viên quản lý AM hình thức thanh toán
     */
    HienThi_NVQL_AM_HTTT: async function (loaikh_id, loainv_id, phuong_id, pho_id, ap_id, khu_id, nvtc_id, donvi_ql_id, httt_id) {
      try {
        if (!this.kt_load) {
          let ds_nvql = []
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_nhanvien_ql_am_v2', {
            loaikh_id: loaikh_id,
            loainv_id: loainv_id,
            phuong_id: phuong_id || 0,
            pho_id: pho_id || 0,
            ap_id: ap_id || 0,
            khu_id: khu_id || 0,
            nhanvientc_id: nvtc_id,
            donvi_ql_id: donvi_ql_id,
            httt_id: httt_id
          })
          ds_nvql = rs.data
          if (ds_nvql.length == 0) {
            this.txtNhanVienQL = ''
            this.nhanvien_ql_am_id = 0
          } else {
            this.txtNhanVienQL = ds_nvql[0].ma_nv + ' - ' + ds_nvql[0].ten_nv
            this.nhanvien_ql_am_id = ds[0].nhanvien_id
          }
        }
      } catch (error) {

      }
    },
    /**
     * Hiển thị nhân viên quản lý am - phân tập khách hàng
     */
    HienThi_NVQL_AM_PTKH: async function (phuong_id, pho_id, ap_id, khu_id, dacdiem_id, loaikh_id, nganhnghe_id, doanhthu, diem_tn, loainv_id, donvi_ql_id) {
      try {
        if (!this.kt_load) {
          let ds_nvql = []
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_am_ptkh', {
            phuong_id: phuong_id || 0,
            pho_id: pho_id || 0,
            ap_id: ap_id || 0,
            khu_id: khu_id || 0,
            dacdiem_id: dacdiem_id || 0,
            loaikh_id: loaikh_id,
            nganhnghe_id: nganhnghe_id,
            doanhthu: doanhthu,
            diemtn: diem_tn,
            loainv_id: loainv_id,
            donvi_ql_id: donvi_ql_id
          })
          ds_nvql = rs.data
          if (ds_nvql.length == 0) {
            this.txtNhanVienQL = ''
            this.nhanvien_ql_am_id = 0
          } else {
            this.txtNhanVienQL = ds_nvql[0].ma_nv + ' - ' + ds_nvql[0].ten_nv
            this.nhanvien_ql_am_id = ds[0].nhanvien_id
          }
        }
      } catch (error) {

      }
    },
    /**
     * Hiển thị nhân viên quản lý - am
     */
    HienThi_NVQL_AM: async function (loaikh_id, loainv_id, phuong_id, pho_id, ap_id, khu_id, nvtc_id, donvi_ql_id) {
      try {
        if (!this.kt_load) {
          let ds_nvql = []
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_nhanvien_ql_am_1', {
            loaikh_id: loaikh_id,
            loainv_id: loainv_id,
            phuong_id: phuong_id || 0,
            pho_id: pho_id || 0,
            ap_id: ap_id || 0,
            khu_id: khu_id || 0,
            nhanvientc_id: nvtc_id,
            donvi_ql_id: donvi_ql_id
          })
          ds_nvql = rs.data
          if (ds_nvql.length == 0) {
            this.txtNhanVienQL = ''
            this.nhanvien_ql_am_id = 0
          } else {
            this.txtNhanVienQL = ds_nvql[0].ma_nv + ' - ' + ds_nvql[0].ten_nv
            this.nhanvien_ql_am_id = ds[0].nhanvien_id
          }
        }
      } catch (error) {

      }
    },
    /***
     * Xử lý các sự kiện button địa chỉ khách hàng
     */
    btnDiaChiKH_Click () {
      this.showThongTinThem = true

      this.diachi = {}
      this.diachi.tinh_id = this.tinhkh_id
      this.diachi.quan_id = this.quankh_id
      this.diachi.phuong_id = this.phuongkh_id
      this.diachi.pho_id = this.phokh_id
      this.diachi.ap_id = this.apkh_id
      this.diachi.khu_id = this.khukh_id
      this.diachi.dacdiem_id = this.dacdiemkh_id
      this.diachi.so_nha = this.sonhakh
      this.diachi.diachi = this.txtDiaChiKH

      this.diachi.longtitude = this.Lng_KH
      this.diachi.lattitude = this.Lat_KH
      this.diachi.block = this.blockkh
      this.diachi.tang = this.tangkh
      this.diachi.sophong = this.sophongkh
      this.diachi.motathem = this.motadckh

      this.diachi_flag = 'DIACHI_KH'
      this.$bvModal.show('popupDiaChi')
    },
    /***
     * Xử lý các sự kiện button địa chỉ người đại diện
     */
    btnDiaChiNDD_Click () {
      this.showThongTinThem = false
      this.diachi = {}
      this.diachi_flag = 'DIACHI_NDD'
      if (this.quanndd_id && this.phuongndd_id && this.phondd_id && this.txtDiaChiNDD) {
        this.diachi.tinh_id = this.tinhndd_id
        this.diachi.quan_id = this.quanndd_id
        this.diachi.phuong_id = this.phuongndd_id
        this.diachi.pho_id = this.phondd_id
        this.diachi.ap_id = this.apndd_id
        this.diachi.khu_id = this.khundd_id
        this.diachi.dacdiem_id = this.dacdiemndd_id
        this.diachi.so_nha = this.sonhandd
        this.diachi.diachi = this.txtDiaChiNDD
      } else {
        this.diachi.tinh_id = this.tinhkh_id
        this.diachi.quan_id = this.quankh_id
        this.diachi.phuong_id = this.phuongkh_id
        this.diachi.pho_id = this.phokh_id
        this.diachi.ap_id = this.apkh_id
        this.diachi.khu_id = this.khukh_id
        this.diachi.dacdiem_id = this.dacdiemkh_id
        this.diachi.so_nha = this.sonhakh
        this.diachi.diachi = this.txtDiaChiKH
      }
      this.$bvModal.show('popupDiaChi')
    },
    /***
     * Xử lý các sự kiện button địa chỉ thanh toán
     */
    btnDiaChiTT_Click () {
      this.showThongTinThem = true
      this.diachi = {}
      this.diachi_flag = 'DIACHI_TT'
      this.diachi.tinh_id = this.tinhtt_id
      this.diachi.quan_id = this.quantt_id
      this.diachi.phuong_id = this.phuongtt_id
      this.diachi.pho_id = this.phott_id
      this.diachi.ap_id = this.aptt_id
      this.diachi.khu_id = this.khutt_id
      this.diachi.dacdiem_id = this.dacdiemtt_id
      this.diachi.so_nha = this.sonhatt
      this.diachi.diachi = this.txtDiaChiTT
      this.diachi.longtitude = this.Lng_TT
      this.diachi.lattitude = this.Lat_TT
      this.diachi.block = this.blocktt
      this.diachi.tang = this.tangtt
      this.diachi.sophong = this.sophongtt
      this.diachi.motathem = this.motadctt
      this.$bvModal.show('popupDiaChi')
    },
    /**
     * Xử lý các sự kiện button địa chỉ chứng từ
     */
    btnDiaChiCT_Click () {
      this.showThongTinThem = false
      this.diachi = {}
      this.diachi_flag = 'DIACHI_CT'
      if (this.quanct_id && this.phuongct_id && this.phoct_id && this.txtDiaChiCT) {
        this.diachi.tinh_id = this.tinhct_id
        this.diachi.quan_id = this.quanct_id
        this.diachi.phuong_id = this.phuongct_id
        this.diachi.pho_id = this.phoct_id
        this.diachi.dacdiem_id = this.dacdiemct_id
        this.diachi.ap_id = this.apct_id
        this.diachi.khu_id = this.khuct_id
        this.diachi.so_nha = this.sonhact
        this.diachi.diachi = this.txtDiaChiCT
      } else {
        this.diachi.tinh_id = this.tinhkh_id
        this.diachi.quan_id = this.quankh_id
        this.diachi.phuong_id = this.phuongkh_id
        this.diachi.pho_id = this.phokh_id
        this.diachi.dacdiem_id = this.dacdiemkh_id
        this.diachi.ap_id = this.apkh_id
        this.diachi.khu_id = this.khukh_id
        this.diachi.so_nha = this.sonhakh
        this.diachi.diachi = this.txtDiaChiKH
      }
      this.$bvModal.show('popupDiaChi')
    },
    /**
     * Xử lý các sự kiện button địa chỉ báo cước
     */
    btnDiaChiBC_Click () {
      this.showThongTinThem = false
      this.diachi = {}
      this.diachi_flag = 'DIACHI_BC'
      this.diachi.tinh_id = this.tinhbc_id
      this.diachi.quan_id = this.quanbc_id
      this.diachi.phuong_id = this.phuongbc_id
      this.diachi.pho_id = this.phobc_id
      this.diachi.dacdiem_id = this.dacdiembc_id
      this.diachi.ap_id = this.apbc_id
      this.diachi.khu_id = this.khubc_id
      this.diachi.so_nha = this.sonhabc
      this.diachi.diachi = this.txtDiaChiBC
      this.$bvModal.show('popupDiaChi')
    },
    /**
     * Tái thiết lập các dữ liệu ở tab khách hàng
     */
    Clear () {
      this.Lat_TT = 0; this.Lng_TT = 0; this.Lat_BC = 0; this.Lng_BC = 0
      this.Lat_KH = 0; this.Lng_KH = 0
      this.Lat_DD = 0; this.Lng_DD = 0
      this.pt_doanhthu = 0
      this.pt_diem_tn = 0

      this.hdkh_id = 0
      this.txtMaKH = ''
      this.khachhang_id = 0
      this.txtTenKH = ''
      this.txtDiaChiKH = ''
      this.tinhkh_id = 0
      this.quankh_id = 0
      this.phuongkh_id = 0
      this.phokh_id = 0
      this.apkh_id = 0
      this.khukh_id = 0
      this.dacdiemkh_id = 0
      this.sonhakh = ''
      this.txtNguoiDD = ''
      this.txtChucDanhDD = ''
      this.txtTelKH = ''
      this.txtFaxKH = ''

      this.txtNgaySN = ''
      this.txtSoGT = ''
      this.txtNgayCap = ''
      this.txtNoiCap = ''
      this.cboGiayToKH = this.dsGiayToKH[0].loaigt_id
      this.lvwThanhToan = []
      this.txtMaTuyen = ''
      this.hdtt_id = 0
      this.txtMaTT = ''
      this.thanhtoan_id = 0
      this.txtTenTT = ''
      this.txtDiaChiTT = ''
      // diachi bc
      this.txtDiaChiBC = ''
      this.tinhbc_id = 0
      this.quanbc_id = 0
      this.phuongbc_id = 0
      this.phobc_id = 0
      this.apbc_id = 0
      this.khubc_id = 0
      this.dacdiembc_id = 0
      this.sonhabc = ''
      //
      this.tinhtt_id = 0
      this.quantt_id = 0
      this.phuongtt_id = 0
      this.phott_id = 0
      this.aptt_id = 0
      this.khutt_id = 0
      this.dacdiemtt_id = 0
      this.sonhatt = ''
      this.txtMST = ''
      this.txtSoTKKH = ''
      this.cboHTTT = this.dsHTTT[0].httt_id
      this.cboDonViQL = this.dsDonViQL[0].donvi_id
      this.dsMaDD = []
      this.cboMaDD = null
      this.hdtb_id = 0
      // Hiếu bổ sung thêm thông tin: 10/08/2010
      this.txtDiaChiCT = ''
      this.txtChucDanhDD = ''
      this.txtNgayHetHan = ''
      this.txtMaSTKH = ''
      this.txtSoTKKH = ''
      this.txtGhiChuKH = ''
      // txtDacTrung.Text = "";
      this.txtGiayToKem = ''
      this.chkNganHangKH = false
      this.$parent.modelInput.txtCTV = ''
      this.$parent.modelInput.txtNguoiGT = ''
      this.$parent.ctv_id = 0
      this.$parent.nguoigt_id = 0

      this.blockkh = ''
      this.tangkh = ''
      this.sophongkh = ''
      this.motadckh = ''

      this.blocktt = ''
      this.tangtt = ''
      this.sophongtt = ''
      this.motadctt = ''

      this.txtMaNVTC = ''
      this.txtNhanVienQL = ''
      this.nhanvien_ql_am_id = 0

      this.txtGhiChuTT = ''
      this.txtNgayCapHK = ''
      this.txtNoiCapHK = ''

      // this.khach_hang_id = 0;
      this.txtDiaChiNDD = ''
      if (this.kieukh_canhan) { this.rdoGioiTinh = 0 } // nam
      // TuấnNA thêm code clear ngân sách nhà nước --- Ngày 12/11/2010
      this.DV_QHNS = ''
      this.MACHUONG = ''
      this.MN_KT = ''
      this.MA_NDKT = ''
      this.MN_NSNN = ''
      this.TIEN_DM = 0

      // TuấnNA thêm code clear biến -- Ngày 03/12/2010
      this.TNC1_ID = 0
      this.TNC2_ID = 0
      this.TNC3_ID = 0
      this.TNC4_ID = 0
      this.nganhnghe_id = 0

      this.hdmig_id = 0
      this.mig_id = 0
      this.txtMaIG = ''
      this.txtHoKhau = ''
      this.txtSTKTT = ''
      this.txtChu_TK = ''
      this.dsHDKH_Sub = []
    },
    /**
     * Convert obj property to lower case
     * @param ds
     * @return {[]}
     * @constructor
     */
    LowerCaseData (ds) {
      let _ds = []
      for (let item of ds) {
        let row = {}
        for (let key in item) {
          let k = key.toLowerCase()
          row[k] = item[key]
        }
        _ds.push(row)
      }
      return _ds
    },
    /**
     * Chọn loại khách hàng
     * @param loaikh_id
     */
    setLoaiKHID (loaikh_id) {
      this.$nextTick(() => {
        let grid = this.$refs['cboGridLoaiKH'].$children[0].grid
        let idx = grid.getCurrentViewRecords().findIndex(item => item.loaikh_id == loaikh_id)
        if (idx > -1) {
          // if (loaikh_id != this.cboLoaiKH) this.cboLoaiKH2_EditValueChanged(this.dsLoaiKH[idx])
          grid.selectRow(idx)
        }
      })
    },
    /**
     * Chọn mã thuê bao đại diện
     * @param matb_dd
     */
    setMaTBDD (matb_dd) {
      try {
        this.$nextTick(() => {
          let grid = this.$refs['cboGridMaTBDD'].$children[0].grid
          let idx = grid.getCurrentViewRecords().findIndex(item => item.ma_tb == matb_dd)
          if (idx > -1) grid.selectRow(idx)
        })
      } catch (e) {
        console.log(e)
      }
    },
    /**
     * Nhận dữ liệu từ form khách hàng bổ sung
     * @param ds
     */
    getHDKH_SUB (ds) {
      this.dsHDKH_Sub = ds
    },
    async lvwThanhToan_selectedRowChanged (item) {
      if (item) {
        let hdtt_id = item.hdtt_id
        let _data = await this.LAY_DS_HOPDONG_TT_THEO_HDTT_ID(hdtt_id)
        if (_data) await this.HienThiTTHopDongTT(_data)
      }
    },
    /**
     * Lấy danh sách hợp đồng thanh toán theo hdtt_id
     */
    LAY_DS_HOPDONG_TT_THEO_HDTT_ID: async function (hdttid) {
      let dataInput = {
        // P_PHANVUNG_ID: this.$root.token.getPhanVungID(),
        hdttId: hdttid//,
        // PROCEDURE_NAME: 'CSS.PKG_SUATHONGTIN_HOPDONG.SP_LAY_DS_HOPDONG_TT_THEO_HDTT_ID'
      }
      // console.log('data', data)
      let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_hdtt_theo_hdttid', dataInput)
      if (res.data.length > 0) { return res.data[0] } else return null
    },
    /**
     * Thêm hợp đồng thanh toán
     */
    async ThemHDThanhToan () {
      this.txtMaTT = ''
      if (!await this.KiemTraDL_ThanhToan()) return
      await this.TaoDuLieu_HDTT(true)
      await this.TaoDuLieu_DiaChi(true, 2)
      // Them diachi bao cuoc
      this.diachibc_id = 0
      await this.CapNhat_DiaChiBC()

      await this.ThongTinThueBao.HienThiDS_HDTT_CBO(this.hdkh_id)
      let _data = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id)
      await this.HienThiDanhSacHDTT(_data)
    },
    /**
     * Xóa hợp đồng thanh toán
     */
    async XoaHDTT () {
      try {
        this.loading(true)
        await this.$root.context.post(`/web-hopdong/hopdong/xoa_hopdong_thanhtoan?hdttId=${this.hdtt_id}`)
        await this.ThongTinThueBao.HienThiDS_HDTT_CBO(this.hdkh_id)
        let _data = await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id)
        await this.HienThiDanhSacHDTT(_data)
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    /**
     * Xóa hợp đồng
     */
    async XoaHDKH () {
      try {
        this.loading(true)
        await this.$root.context.post(`web-hopdong/chuyendich/xoa_hdkh_v2?hdkh_id=${this.hdkh_id}&loaihd_id=${Enum.LoaiHopDong.DI_CHUYEN}`)
        if (this.ThongTinThueBao.modelInput.txtMaTBMoi && this.ThongTinThueBao.modelInput.txtMaTBMoi.toString().trim() != '') {
          await this.$root.context.post('web-hopdong/chuyendich/capnhat_trangthaiso', {
            ma_tb: this.ThongTinThueBao.modelInput.txtMaTBMoi.toString().trim(),
            trangthai_so: 1,
            dichvuvt_id: this.ThongTinThueBao.inputValue.dichvuvt_id
          })
        }
        this.$parent.SetButton(0)
        this.$root.toastSuccess('Xóa hợp đồng khách hàng thành công')
      } catch (e) {
        console.log(e)
        this.$root.toastError('Có lỗi xảy ra khi xóa hợp đồng khách hàng')
      } finally {
        this.loading(false)
      }
    },
    /**
     * Xử lý keypress enter TenKH
     */
    txtTenKH_KeyPress () {
      this.btnDiaChiKH_Click()
    }
  },
  watch: {
    hdkh_id: async function (val) {
      console.log('🚀 ~ file: index.vue:3413 ~ val:', val)
    },
    cboHTTT: async function (val) {
      console.log('🚀 ~ file: index.vue:3416 ~ val:', val)
      if (this.dsHTTT.length > 0) {
        if (!val) return
        this.httt_id = val
        if (this.httt_id == Enum.HinhThucThanhToan.UYNHIEM_THU) { this.enableCboNganHangTT = true } else this.enableCboNganHangTT = false
        // Lấy lại thông tin NVTC theo hình thức TT
        if (this.TUYENTHU_HTTT == 1) {
          if (this.phuongtt_id && (this.phott_id || this.aptt_id || this.khutt_id)) {
            if (this.load_ds_nvtc) {
              await this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id,
                this.cboDonViQL, this.dacdiemtt_id)
            }
          }
        }
      }
    },
    cboLoaiKH: async function (val) {
      let kieukh_id = 0, kieukh = '', loaikh = this.dsLoaiKH.filter(i => i.loaikh_id == val)[0]
      kieukh = loaikh ? loaikh.loai.toString() : ''
      if (kieukh != '') {
        this.vkieukh_id_ = kieukh_id = parseInt(kieukh)
      }
      if (kieukh_id == Enum.KIEU_KH.CA_NHAN) {
        this.kieukh_canhan = true
      } else this.kieukh_canhan = false
      // hoangpkn : 25/10/2017 Thêm nhân viên quản lý
      if (this.phuongtt_id && (this.phott_id || this.aptt_id || this.khutt_id)) {
        let ds = []
        if (this.DONVIQL_HTTT == 1) {
          ds = await this.LAY_DS_DONVIQL_V2(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 1,
            this.cboLoaiKH, this.cboHTTT)
        } else {
          if (this.phantap_kh) {
            ds = await this.LAY_DS_DONVIQL_PHANTAP(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id,
              this.dacdiemtt_id, 1, this.cboLoaiKH, this.cboNganhNgheLoc, this.pt_doanhthu, this.pt_diem_tn)
          } else {
            ds = await this.LAY_DS_DONVIQL(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 1, this.cboLoaiKH)
          }
        }
        if (ds.length <= 0) {
          if (this.dsDonViQL.length != 1) {
            this.$root.toastError('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!')
            this.cboDonViQL = null
          }
        } else if (ds.length == 1) {
          this.cboDonViQL = ds[0].donviql_id
        } else {
          // mở frmChonDonVi
          // show(donvi_ql_id, in_donvi_id, donvi_index_id, kieu, ds)
          let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.cboDonViQL, null, ds)
          if (resolvePromise.ok) { this.cboDonViQL = resolvePromise.data.donviql_id } else return
        }
      }
    }
  }
}
</script>
<style>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.popup-expand-custom .grid-panel {
  right: 0;
}
</style>
