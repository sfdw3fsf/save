<template src="./ChangeContractOwner.html"></template>

<style src="./ChangeContractOwner.scss"></style>

<script>
import moment from 'moment'
import Select2Ext from '../DeclareLandline/components/Select2Ext'
import DataGridCustom from '../DeclareLandline/components/DataGridCustom'
import VueDateExt from '../DeclareLandline/components/VueDateExt'
import breadcrumb from '../DeclareLandline/components/breadcrumb.vue'
import frmDSTBCungMa from './popups/frmDSTBCungMa.vue'
import SearchContractModal from '@/modules/search/subscriber/SearchContract/SearchContract.vue'
import frmNguoiGT from '@/modules/contract/setup/SeparationSubscriber/popups/frmNguoiGT.vue'
import ModalChonDiaChi from '../InstallNewSubs/Popups/popupChonDiaChi'
import searchAccount from '@/modules/search/subscriber/SearchAccount'
import PopupTimKiemNhanVienThuCuoc from '@/modules/EXTS/HOPDONG/ThayDoiThongTinKhuyenMai/TimNhanVienThuCuoc'
import popupChiTietMLNS from '@/modules/contract/setup/SeparationSubscriber/popups/popupChiTietMLNS.vue'
import frmCT_Tien_KM from '@/modules/contract/setup/SeparationSubscriber/popups/frmCT_Tien_KM.vue'
import frmTC_DBTB_KH from '@/modules/contract/setup/SeparationSubscriber/popups/frmTC_DBTB_KH.vue'
import frmThanhToanHD from '@/modules/contract/setup/Payment/Payment.vue'
import ThongTinEmail from './popup/ThongTinEmail.vue'
import ThongTinLienHe from './popup/ThongTinLienHe.vue'
import InHoSo from '@/modules/print/InHopDong/InHopDong'
import InstallNewSubsAPI from '@/modules/contract/setup/InstallNewSubs/InstallNewSubsAPI.js'
import ComboboxGridCustom from '@/modules/contract/setup/DeclareLandline/components/ComboboxGridCustom'
import ChonDonVi from '@/modules/EXTS/HOPDONG/DichChuyenNew/components/ThongTinKhachHang/ChonDonVi'
import api from './Api'
import { TrangThaiHD, DichVuVienThong, LoaiHopDong, KIEU_HD, TRANGBI, Loai_NV, TRANGTHAI_DONGBO, TrangThaiCaiDat, LoaiHinhTB, LOAI_DV, KHOANMUC_TT, TrangThaiTB, DatabaseConstants, NHOM_LNV } from '@/const/enums'
import checkdata from './library/checkdata.js'
import tchopdong from './library/tchopdong.js'
import laphd from './library/laphd.js'
import tracuuchung from './library/tracuuchung.js'
import tcdanhba from './library/tcdanhba.js'
import bangts from './library/bangts.js'
const thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')

var rs
var temp
export default {
  components: {
    Select2Ext,
    DataGridCustom,
    VueDateExt,
    breadcrumb,
    frmDSTBCungMa,
    SearchContractModal,
    frmNguoiGT,
    ModalChonDiaChi,
    searchAccount,
    PopupTimKiemNhanVienThuCuoc,
    popupChiTietMLNS,
    frmCT_Tien_KM,
    frmTC_DBTB_KH,
    frmThanhToanHD,
    ThongTinEmail,
    ThongTinLienHe,
    InHoSo,
    ComboboxGridCustom,
    ChonDonVi
  },
  computed: {
    cboLoaiKH_value() {
      return this.$refs.cboLoaiKH.value
    }
  },
  watch: {
    cboDonviQL_value() {
      this.cboLoaiKH2_EditValueChanged()
    },
    async thuebao_id(val) {
      this.$refs.palThongSoKyThuat.style.display = 'none'
      try {
        if (val) {
          this.$refs.palThongSoKyThuat.style.display = null
          this.$refs.wbrTSKT.innerHTML = ''
          rs = await this.axios.get('web-hopdong/hopdong/lay_ds_thongtin_thuebao_pivot?thuebaoId=' + val)
          console.log(rs.data.data)
          rs.data.data.forEach((dr) => {
            this.$refs.wbrTSKT.innerHTML += '<b>' + dr['ten_truong'] + ': </b>' + (dr['giatri'] ? dr['giatri'] : '') + '<br />'
          })
        } else {
          this.$refs.palThongSoKyThuat.style.display = 'none'
          this.$refs.wbrTSKT.innerHTML = ''
        }
      } catch (err) {
        console.log(err)
        this.$refs.wbrTSKT.innerHTML = ''
      }
    }
  },
  data() {
    return {
      dskmtt_loaitbi: [],
      loaiPopupTraCuuNVTC: 0,
      thongsoKT: [],
      popupInHoSo_Param: {
        hdkh_id: 0,
        hdtb_id: 0,
        ma_gd: ''
      },
      objNVTC: {
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        TUYENTHU_ID: 0,
        NHANVIENTC_ID: 0,
        KT_KYLAI: false,
        DONVIQL_ID: 0
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      dikem: false,
      userComponentModule: null,
      popupComponentEvts: {
        'form-close': this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        paymentcreated: this.paymentcreated
      },
      popupComponentName: '',
      objNganhang: {},
      columns: [
        {
          fieldName: 'stt',
          headerText: 'STT',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'ma_tt',
          headerText: 'Mã TT',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'ma_tb',
          headerText: 'Số máy/Acc',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'ma_tt',
          headerText: 'Mã TT',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ lắp đặt',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'tien',
          headerText: 'Tiền',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'ten_dvvt',
          headerText: 'Dịch vụ VT',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'ma_nvtc',
          headerText: 'Mã NVTC',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: 'trangthai_dongbo',
          headerText: 'Trạng thái đồng bộ',
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        }
      ],
      dataDiaChi: {
        type: '',
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        //
        block: '',
        tang: '',
        sophong: '',
        motathem: '',
        diachi: ''
      },
      kieukh_canhan: false,
      PHANLOAIKH_ID: -1,
      kt_load: true,
      report: null,
      dichvuvt_id: 0,
      loaitb_id: 0,
      _dichvuvt_id: 0, //PNLINH 27/04/2020
      _loaitb_id: 0, //PNLINH 27/04/2020
      httt_id: 0,
      httt_id_cu: -1,
      modalSearch: {
        loaihd_id: 0,
        tthd_id: 0,
        ngay_yc: moment().format('DD/MM/YYYY')
      },
      hdkh_id: 0,
      hdkh_cha_id: 0,
      P_ACTION: 0,
      txtMaGDTT: '',
      hdtt_id: 0,
      hdtb_id: 0,
      khachhang_id: 0,
      khachhang_id_nm: 0, ///tuan them bien this.khachhang_id_ nm (nhap moi) -- ngay 05/07/2010
      khachhang_id_cq: 0, ///tuan them bien this.khachhang_id_cq (khac hhang chuyen quyen) -- ngay 06/07/2010
      ma_gd: '', ///tuan them bien this.ma_gd  -- ngay 06/07/2010
      TBHD_PL: 1, ///tuan them bien this.TBHD_PL  -- ngay 07/07/2010
      ma_tt_cu: '', ///tuan them bien this.ma_tt_cu  -- ngay 22/07/2010
      ten_tt_cu: '', ///tuan them bien this.ten_tt_cu  -- ngay 22/07/2010
      diachi_tt_cu: '', ///tuan them bien this.diachi_tt_cu  -- ngay 22/07/2010
      ma_tb_cu: '', ///tuan them bien this.ma_tb_cu  -- ngay 23/07/2010
      ten_tb_cu: '', ///tuan them bien this.ten_tb_cu  -- ngay 26/07/2010
      check_tb_dd: false, /// TuanNA thêm biến dùng để kiểm tra hợp đồng có thuê bao di động hay không -- Ngày 28/07/2010
      donvi_ql: 0, ///TuanNA khai báo biến thay cho this.$refs.cboDonviQL -- Ngày 29/07/2010
      ma_kh_cu: '', ///TuanNA khai báo biến this.ma_kh_cu -- Ngày 06/08/2010
      strError_VinaPhone: '', //HungHM - 10/08/2010 : Biến lưu lại chuỗi thông báo lỗi trả về từ VinaPhone
      clear_manvtc: '0',
      trangbi_id: 3,
      in_pl: 0,
      ds_isdn: [],
      matb_isdn_chinh: '',
      dsHDTBDV: [],
      dtControl: [],
      //Thực hiện khai báo lấy danh sách khoản mục -- nhapt 08/06/2015

      dtien_dv: 0,
      dvat_dv: 0,
      dtien_cq: 0,
      dvat_cq: 0,

      ds_tien_khoanmuc: [],

      thanhtoan_id: 0,
      thuebao_id: 0,
      thuebao_cha_id: 0,
      tuyenthu_id: 0,
      matb_dd: '',
      ma_tb: '',
      nhanvien_ql_am_id: 0,

      // -- dia chi lắp đặt
      diachild_id: 0,
      tinhld_id: 0,
      quanld_id: 0,
      phuongld_id: 0,
      phold_id: 0,
      apld_id: 0,
      khuld_id: 0,
      dacdiemld_id: 0,
      sonhald: '',
      blockld: '',
      tangld: '',
      sophongld: '',
      motadcld: '',

      // -- dia chi khách hàng
      diachikh_id: 0,
      tinhkh_id: 0,
      quankh_id: 0,
      phuongkh_id: 0,
      phokh_id: 0,
      apkh_id: 0,
      khukh_id: 0,
      dacdiemkh_id: 0,
      sonhakh: '',

      // -- dia chi người đại diện
      tinhndd_id: 0,
      quanndd_id: 0,
      phuongndd_id: 0,
      phondd_id: 0,
      apndd_id: 0,
      khundd_id: 0,
      dacdiemndd_id: 0,
      sonhandd: '',

      // -- dia chi chứng từ
      tinhct_id: 0,
      quanct_id: 0,
      phuongct_id: 0,
      phoct_id: 0,
      apct_id: 0,
      khuct_id: 0,
      dacdiemct_id: 0,
      sonhact: '',

      // -- dia chi thanh toán
      diachitt_id: 0,
      tinhtt_id: 0,
      quantt_id: 0,
      phuongtt_id: 0,
      phott_id: 0,
      aptt_id: 0,
      khutt_id: 0,
      dacdiemtt_id: 0,
      sonhatt: '',

      // -- dia chi thue bao
      diachitb_id: 0,
      tinhtb_id: 0,
      quantb_id: 0,
      phuongtb_id: 0,
      photb_id: 0,
      aptb_id: 0,
      khutb_id: 0,
      dacdiemtb_id: 0,
      sonhatb: '',
      blocktb: '',
      tangtb: '',
      sophongtb: '',
      motadctb: '',

      // -- dia chi BC
      diachibc_id: 0,
      tinhbc_id: 0,
      quanbc_id: 0,
      phuongbc_id: 0,
      targetTop: '#app .main-wrapper',
      position: {
        X: 'center',
        Y: 'top'
      },
      phobc_id: 0,
      apbc_id: 0,
      khubc_id: 0,
      dacdiembc_id: 0,
      sonhabc: '',
      //

      tinhdau_id: 0,
      diachidau_id: 0,
      phodau_id: 0,
      ngodau_id: 0,
      phuongdau_id: 0,
      paymentloaded: false,
      quandau_id: 0,
      sonhadau: 0,
      donvi_dau_id: 0,

      tinhcuoi_id: 0,
      diachicuoi_id: 0,
      phocuoi_id: 0,
      ngocuoi_id: 0,
      phuongcuoi_id: 0,
      quancuoi_id: 0,
      sonhacuoi: 0,
      donvi_cuoi_id: 0,
      status: '',
      linhvuc_id: 0,
      ma_tinh: 0,
      chapnhan: false, //PNLINH 27/04/2020
      autoload: false, //PNLINH 27/04/2020
      thamso_md: [],
      bangts: [],
      chuyenquyen: [],
      laphd: [],
      tchopdong: [],
      tcdanhba: [],
      hths: [],
      //,GetKeyFacade getkey : new GetKeyFacade(),
      khoso: [],
      tracuuchung: [],

      tt_nd: {
        ngay_cn: moment().format('DD/MM/YYYY'),
        nhanvien_id: this.$root.token.getNhanVienID(),
        donvi_id: this.$root.token.getDonViID()
      },
      cmUlt: [],

      dsHDKH: [],
      dsHDTT: [],
      dsHDTB: [],
      dsHDTB_CD: [],
      dsHDTB_GP: [],
      dsHDTB_MEWAN: [],
      dsHDTB_DD: [],
      dsHDTB_INT: [],
      dsHDTB_TSL: [],
      dsHDTB_ADSL: [],

      dsHDTB_CNTT: [],
      ItemList: [],
      dsDiaChi: [],
      dsDiaChiHDKH: [],
      dsDiaChiHDTT: [],
      dsDiaChiHDTB: [],
      dsHDTB_IMS: [],
      dsHDTT_NV: [],

      dsTTTT: [],
      dsCTTHD: [],
      CT_TIENHD_ID: 0,

      dsDK_DVGT: [],
      dsDK_DVK: [],

      m_dsThueBao_DichVu: 0,
      checkdata: [],

      //TuấnNA thêm khai báo cho nút ngân sách nhà nước --- Ngày 12/11/2010
      DV_QHNS: null,
      MACHUONG: null,
      MN_KT: null,
      MA_NDKT: null,
      MN_NSNN: null,
      TIEN_DM: null,

      //Hiếu bổ sung phần khai báo cho hàm laytien
      loaihd_id: 0,
      kieuld_id: 0,
      doituong_id: 0,
      ngaylhd: null,

      //TuấnNA thêm code khai báo các biến tiểu ngành
      TNC1_ID: 0,
      TNC2_ID: 0,
      TNC3_ID: 0,
      TNC4_ID: 0,
      nganhnghe_id: 0,
      doituong_id_bd: 0,
      mucuoctb_id: 0,
      tocdo_id: 0,
      ltb_adsl_id: 1, //Default
      kh_cd: 0,
      ltb_adsl_cu_id: 0,
      ltb_adsl_moi_id: 0,
      tocdo_moi_id: 0,
      tocdo_cu_id: 0,

      //Hiếu bổ sung phần khai báo cho hàm laytien --14/09/2010
      ///TuấnNA bổ sung thêm biến sử dụng trong hàm lấy tiền ld ---Ngày 15/09/2010
      tien_tb: 0,
      vat_tb: 0,
      tien_dv: 0,
      vat_dv: 0,
      tien_kmld: 0,
      tien_kmtb: 0,
      DS_ID_DV: '', //Lưu danh sách ID dịch vụ gia tăng--Hiếu 16/09/2010
      DS_ID_TB: '', //Lưu danh sách ID mua thiết bị - Hiếu 16/09/2010
      dtCTKM: null, //hiếu 16/09/2010: Lưu danh sách các khoản mục.

      nhanvientc_id: 0,
      quytrinh_id: 0,
      cbo: null,
      lbl: null,

      hdmig_id: 0,
      mig_id: 0,

      baocaocon: [],
      baocao: [],
      dtLoaiKH: [],
      ts_kt_mst: 0,

      DONVIQL_HTTT: 0,
      TUYENTHU_HTTT: 0,
      check_chuahoa_cntt: false,
      ts_sinhma_gd_theo_donvi: false, //Dungf cho Hni sinh ma GD theo don vi quan ly cua ng dung
      loi_sinh_magd: false,
      khong_batbuoc_ctv: 0,
      batbuoc_gioithieu_chuyenquyen: 0,
      ctv_id: 0,
      donvi_ctv_id: 0,
      loainv_ctv_id: 0,
      nguoigt_id: 0,
      donvi_nguoi_gt_id: 0,
      loainv_nguoi_gt_id: 0,
      vkieukh_id_: 0, //biến toàn cục kiểm tra KH cá nhân hay doanh nghiệp
      ts_kt_mstmns_kh: 0, //Kiểm tra MST MNS khách hàng DN
      ts_kt_tthdkhdn: 0, //Kiểm tra cho phép thêm thông tin khách hàng DN  đối với PTTB KHDN mới xài
      load_tt_lh: true,
      index_loai_kh: 0,

      dsHDKH_Sub: [],
      dsHDTB_Sub: [],

      phantap_kh: false,
      pt_doanhthu: 0,
      pt_diem_tn: 0,
      nopopup: false
    }
  },
  mounted() {
    checkdata.khoitao(this.axios)
    tchopdong.khoitao(this.axios)
    bangts.khoitao(this.axios)
    laphd.khoitao(this.axios)
    tracuuchung.khoitao(this.axios)
    tcdanhba.khoitao(this.axios)
    this.frmChuyenQuyenSD_Load()
  },
  methods: {
    open_thanhToanPopUp() {
      this.loading(true)
    },
    paymentSuccess() {
      this.SetButton(0)
    },
    formatDate(date) {
      if (date && date.includes('/')) return date.split(' ')[0]
      else return moment(date).format('DD/MM/YYYY : HH:mm:ss')
    },
    acceptNganhang(frm) {
      if (!frm) return
      this.DV_QHNS = frm.DV_QHNS
      this.MACHUONG = frm.MACHUONG
      this.MN_KT = frm.MN_KT
      this.MA_NDKT = frm.MA_NDKT
      this.MN_NSNN = frm.MN_NSNN
      this.TIEN_DM = frm.TIEN_DM
    },
    getNVTCProcess(data) {
      if (this.loaiPopupTraCuuNVTC == 0) {
        if (this.quantt_id != 0 && this.phuongtt_id != 0 && (this.phott_id != 0 || this.khutt_id != 0 || this.aptt_id != 0)) {
          this.nhanvientc_id = data.nhanvientc_id
          this.$refs.txtMaNVTC.value = data.ma_nvtc
          this.$refs.txtMaTuyen.value = data.ma_tuyen
          this.tuyenthu_id = data.tuyenthu_id

          let vloaikh_id = 0
          if (this.loaikh_id) vloaikh_id = Number(this.loaikh_id)
          if (this.DONVIQL_HTTT == 1) this.HienThi_NVQL_AM_HTTT(vloaikh_id, Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, Number(this.$refs.cboDonviQL.value), Number(this.$refs.cboHTTT.value))
          else {
            if (!this.phantap_kh) this.HienThi_NVQL_AM(vloaikh_id, Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, Number(this.$refs.cboDonviQL.value))
          }
        } else {
          this.$toast.error('Chưa có thông tin về địa chỉ thanh toán\nBạn kiểm tra lại!')
          this.nhanvientc_id = 0
          this.$refs.txtMaNVTC.value = ''
          this.$refs.txtMaTuyen.value = ''
          this.$refs.txtMaNVTC.focus()
          this.$refs.txtNhanVienQL.value = ''
          this.nhanvien_ql_am_id = 0
        }
      }
      if (this.loaiPopupTraCuuNVTC == 1) {
        console.log(data)
        if (this.objNVTC.QUAN_ID && this.objNVTC.PHUONG_ID) {
          this.nhanvientc_id = data.nhanvientc_id
          this.$refs.txtMaNVTC.value = data.ma_nvtc
          this.tuyenthu_id = data.tuyenthu_id
          this.$refs.txtMaTuyen.value = data.ma_tuyen
        } else {
          this.$toast.error('Chưa có thông tin về địa chỉ thanh toán. Bạn kiểm tra lại!')
          this.$refs.txtMaNVTC.value = ''
          this.$refs.txtMaTuyen.value = ''
          this.nhanvientc_id = 0
          this.tuyenthu_id = 0
        }
      }
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup('popupComponents')
      }
      try {
        this.Loading = true
        switch (this.popupComponentName) {
          case 'SearchAccount':
            console.log('SearchAccount', val)
            if (val.ma_tb) this.$refs.txtMaKH.value = val.ma_tb
            if (this.$refs.txtMaKH.value) {
              this.searchData = false // Ko bat popup nua
              this.txtMaKH_KeyPress()
            }
            break
          case 'popupTieuNganh':
            let obj = val
            console.log(obj)
            if (obj) {
              this.TNC1_ID = obj.TNC1_ID
              this.TNC2_ID = obj.TNC2_ID
              this.TNC3_ID = obj.TNC3_ID
              this.TNC4_ID = obj.TNC4_ID
            }
            break
          case 'popupChonMaTT':
            if (val.ma_tt) this.$refs.txtMaTT.value = val.ma_tt
            break
          case 'popupInforAdd':

            try {
              this.dsHDKH_Sub = JSON.parse(val)
            } catch (err) {
              console.log(err)
            }
            this.$refs.thanhtoanDialog.hide()
            break
          case 'popupChonMaInGhep':
            if (val && val.hdmig_id) {
              this.hdmig_id = val.hdmig_id
              this.$refs.txtMaIG.value = val.main_ghep
            } else {
              if (this.hdtt_id && this.hdtt_id > 0) {
                rs = await this.axios.post('/web-hopdong/lapdatmoi/lay_ds_hopdong_tt_theo_hdtt_id', {
                  hdtt_id: this.hdtt_id
                })
                this.HienThiTTHopDongTT(rs.data.data)
              }
            }
            break
          case 'popupCTV':
            if (val.ten_ht) {
              this.$refs.txtCTV.value = val.ten_ht
              this.ctv_id = val.nhanvien_id
            }
            break
          case 'popupNGT':
            if (val.ten_ht) {
              this.$refs.txtNguoiGT.value = val.ten_ht
              this.nguoigt_id = val.nhanvien_id
            }
            break
          default:
        }
      } catch (err) {
        console.log(err)
      } finally {
        this.ClosePopup('popupComponents')
        this.Loading = false
      }
      // this.$emit('close', this.PhieuId)
      // this.$emit('closeChuyenTo', this.ChapNhan)
    },
    CURRENT_DVVT_ID() {
      try {
        return Number(this.$refs.cboDichVuVT.value)
      } catch (err) {
        return -1
      }
    },

    //Lấy this.loaitb_id đang chọn trên combobox
    CURRENT_LOAITB_ID() {
      try {
        return Number(this.$refs.cboLoaihinhTB.value)
      } catch (err) {
        return -1
      }
    },
    async LAY_DS_THAMSO_MD_MATS(ma_ts) {
      return new Promise(async (resolve, reject) => {
        rs = await this.axios.post('web-hopdong/goi-da-dichvu/sp_ds_thamso_md', {
          kieu_id: null,
          ma_ts: ma_ts
        })
        resolve(rs)
      })
    },
    LOCK_UNLOCK_EVENT(vlock) {
      if (vlock) {
        this.cboDoituongTB_SelectedValueChanged()
        this.cboDichVuVT_SelectedValueChanged()
      } else {
        this.cboDoituongTB_SelectedValueChanged()
        this.cboDichVuVT_SelectedValueChanged()
        this.cboLoaiKH2_EditValueChanged()
        this.GiaoDien_DichVuVT(Number(this.$refs.cboDichVuVT.value))
      }
    },
    async frmChuyenQuyenSD_Load() {
      this.loading(true)
      this.LOCK_UNLOCK_EVENT(true)
      try {
        this.$refs.cboMaDD.dataTextField = 'ten_ht'
        this.$refs.cboMaDD.dataValueField = 'ma_tb'
        let dsTSCQ = await this.LAY_DS_THAMSO_MD_MATS('TS_RANG_BUOC_CHUYEN_QUYEN')
        if (dsTSCQ.length == 0 || dsTSCQ == null) {
          this.isHNI = false
        } else {
          this.isHNI = true
        }
        this.$refs.txtMaTB.value = this.ma_tb

        // rs = await this.axios.get(
        //   "/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh"
        // );
        this.ky_hd = '0522'

        this.$refs.dtpNgayLapHDNQ.value = this.tt_nd.ngay_cn
        this.$refs.dtpNgayYC.value = this.tt_nd.ngay_cn
        this.SetButton(-1)

        // Gán danh sách cho tổng tiền ban đầu -- nhapt 08/06/2015

        //region CƯỜNG THỬ LOAD KIỂU MỚI HIỂU QUẢ RA SAO KO BIẾT
        let arrTableName = ['TIEN_KM_CQ', 'LOAI_GT', 'LOAI_KH', 'LOAI_KHL', 'DONVI_QL', 'MUCCUOC', 'NGANHANG', 'DOITUONG_NEO', 'TOCDO_KENH', 'LOAI_TB', 'LOAI_MODEM', 'GOICUOC_DD']
        let args = ['vchuoi', 'TIEN_KM_CQ|LOAI_GT|LOAI_KH|LOAI_KHL|DONVI_QL|MUCCUOC|NGANHANG|DOITUONG_NEO|TOCDO_KENH|LOAI_TB|LOAI_MODEM|GOICUOC_DD']

        rs = await this.axios.get('web-hopdong/hopdong/lay_tatca_danhmuc_chuyenquyen')
        this.dsDanhMuc = rs.data.data
        let dtz = []
        dtz = this.dsDanhMuc['tien_km_cq']
        this.ds_tien_khoanmuc['danhsach'] = dtz
        dtz = null

        this.$refs.cboDoituongTB.HT_COMBOBOX(this.dsDanhMuc['doituong'], 'ten_dt', 'doituong_id')
        this.$refs.cboDichVuVT.HT_COMBOBOX(this.dsDanhMuc['dichvu_vt'], 'ten_dvvt', 'dichvuvt_id')
        this.$refs.cboGiayToKH.HT_COMBOBOX(this.dsDanhMuc['loai_gt'], 'loai_gt', 'loaigt_id')
        let dtLoaiKH = this.dsDanhMuc['loai_kh']
        //await bangts.HT_DS_GRIDLOOKUP(this.$refs.cboLoaiKH, dtLoaiKH, "TEN_LOAIKH", "LOAIKH_ID", true, 2, 400);
        dtLoaiKH.map((item) => {
          let data = item.ten_nhom.split('-')
          if (data.length > 2) {
            item.group_lon = 'Kiểu KH: ' + item.ten_nhom.split('-')[0].trim()
            item.group_nho = 'Nhóm LKH: ' + item.ten_nhom.split('-')[1].trim()
          } else {
            item.group_lon = 'Kiểu KH: ' + item.ten_loaikh_dis.split('-')[0].trim()
            item.group_nho = 'Nhóm LKH: ' + item.ten_nhom
          }
        })

        console.log(dtLoaiKH)

        this.$refs.cboLoaiKH.dataSource = dtLoaiKH
        this.$refs.cboLoaiKH.dataValueField = 'loaikh_id'
        this.$refs.cboLoaiKH.dataTextField = 'ten_loaikh'
        this.$refs.cboLoaiKH.value = -1

        this.$refs.cboDoituongTB.HT_COMBOBOX(this.dsDanhMuc['doituong'], 'ten_dt', 'doituong_id')
        this.$refs.cboDichVuVT.HT_COMBOBOX(this.dsDanhMuc['dichvu_vt'], 'ten_dvvt', 'dichvuvt_id')
        this.$refs.cboGiayToKH.HT_COMBOBOX(this.dsDanhMuc['loai_gt'], 'loai_gt', 'loaigt_id')

        this.$refs.cboKhachHangLon.HT_COMBOBOX(this.dsDanhMuc['loai_khl'], 'ten_lkhl', 'khlon_id')
        this.$refs.cboHTTT.dataSource = this.dsDanhMuc['hinhthuc_tt']
        this.$refs.cboHTTT.dataValueField = 'httt_id'
        this.$refs.cboHTTT.dataTextField = 'hinhthuc_tt'
        this.$refs.cboHTTT.value = null
        this.$refs.cboDaiVT.HT_COMBOBOX(
          this.dsDanhMuc['donvi_ql'].filter((item) => item.loaidv_id == LOAI_DV.DONVIQL_LD),
          'ten_dv',
          'donvi_id'
        )
        this.$refs.cboDonviQL.HT_COMBOBOX(
          this.dsDanhMuc['donvi_ql'].filter((item) => item.loaidv_id == LOAI_DV.DONVIQL_TT),
          'ten_dv',
          'donvi_id'
        )

        this.$refs.cboNganHangTT.HT_COMBOBOX(this.dsDanhMuc['nganhang'], 'ten_nh', 'nganhang_id')
        this.$refs.cboNganHangKH.HT_COMBOBOX(this.dsDanhMuc['nganhang'], 'ten_nh', 'nganhang_id')

        this.$refs.cboDanToc.HT_COMBOBOX(this.dsDanhMuc['dantoc'], 'tendantoc', 'dantoc_id')
        this.$refs.cboQuocTich.HT_COMBOBOX(this.dsDanhMuc['quoctich'], 'tenquoctich', 'quoctich_id')
        //await bangts.HT_COMBOBOX(cboNganhNghe, GlobalVar.LAY_NGANHNGHE(), "tennganhnghe", "nganhnghe_id");
        this.$refs.cboNganhNgheLoc.dataSource = this.dsDanhMuc['nganhnghe']
        this.$refs.cboNganhNgheLoc.dataValueField = 'nganhnghe_id'
        this.$refs.cboNganhNgheLoc.dataTextField = 'tennganhnghe'
        // this.$refs.cboNganhNgheLoc.value = this.$refs.cboNganhNgheLoc.dataSource[0].nganhnghe_id

        console.log('adlasjdlkjaldjasljdsla')
        var api10 = InstallNewSubsAPI.get_loai_tbi_lhtb(this.axios, {}).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.dskmtt_loaitbi = response.data.data
            if (this.dskmtt_loaitbi && this.dskmtt_loaitbi.length > 0) for (var item of this.dskmtt_loaitbi) item['khoanmuctt_id'] = item['khoanmuctt_id'] ? item['khoanmuctt_id'] : null
          }
        })

        //DD
        //await bangts.HT_COMBOBOX(this.$refs.cboTrangBiDD, GlobalVar.LAY_TRANGBI(), "tentrangbi", "trangbi_id");
        //GP
        //await bangts.HT_COMBOBOX(this.$refs.cboTrangBiGP, GlobalVar.LAY_TRANGBI(), "tentrangbi", "trangbi_id");

        //TSL
        //await bangts.HT_COMBOBOX(this.$refs.cboChuQuan, GlobalVar.LAY_CHUQUAN(), "ten_tat", "chuquan_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboTrangBi, GlobalVar.LAY_TRANGBI(), "tentrangbi", "trangbi_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboThoiHan, GlobalVar.LAY_THOIHAN(), "tenthoihan", "thoihan_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboLoaiKenhTSL, GlobalVar.LAY_LOAIKENH(), "loai_kenh", "loaikenh_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboLoaiTBTSL, dsDanhMuc["LOAI_TB"].Select("this.dichvuvt_id = " + DichVuVienThong.TSL).CopyToDataTable(), "ten_tbi", "thietbidc_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboTocDoKenhTSL, dsDanhMuc["TOCDO_KENH"], "toc_do", "tocdo_id");

        //WAN
        //await bangts.HT_COMBOBOX(this.$refs.cboTocDo_PIR, dsDanhMuc["TOCDO_KENH"], "toc_do", "tocdo_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboTrangBiWan, GlobalVar.LAY_TRANGBI(), "tentrangbi", "trangbi_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboChuQuanWan, GlobalVar.LAY_CHUQUAN(), "ten_tat", "chuquan_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboLoaiModem, dsDanhMuc["LOAI_MODEM"], "loai_md", "loaimd_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboLoaiTBiWan, dsDanhMuc["LOAI_TB"].Select("this.dichvuvt_id = " + DichVuVienThong.MEGAWAN).CopyToDataTable(), "ten_tbi", "thietbidc_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboThoihanWan, GlobalVar.LAY_THOIHAN(), "tenthoihan", "thoihan_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboLoaiKenhWan, GlobalVar.LAY_LOAIKENH(), "loai_kenh", "loaikenh_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboTocDo, dsDanhMuc["TOCDO_KENH"], "toc_do", "tocdo_id");

        //ADSL
        //await bangts.HT_COMBOBOX(cboGoiCuocAdsl, dsDanhMuc["TOCDO_ADSL"], "thuonghieu", "tocdo_id");
        //await bangts.HT_COMBOBOX(cboMucCuoc, dsDanhMuc["MUCCUOC"], "tenmuc", "muccuoc_id");
        //await bangts.HT_COMBOBOX(this.$refs.cboTrangBiADSL, GlobalVar.LAY_TRANGBI(), "tentrangbi", "trangbi_id");
        //endregion

        //region Kiểu LOAD CŨ

        /* this.ds_tien_khoanmuc = await bangts.Lay_tienkhoanmuc_loaihd(LoaiHopDong.CHUYEN_QUYEN);
                        await bangts.HT_DoiTuong_Combobox(this.$refs.cboDoituongTB);
                        await bangts.HT_DichVuVT_Combobox(this.$refs.cboDichVuVT);
                        if (this._dichvuvt_id != 0) //PNLINH 27/04/2020
                        {
                            this.$refs.cboDichVuVT.value = this._dichvuvt_id;
                            await bangts.HT_LoaiHinh_TB_Combobox(this.$refs.cboLoaihinhTB, this._dichvuvt_id);
                            this.$refs.cboLoaihinhTB.value = this._loaitb_id;
                        }
                        else
                        {
                            await bangts.HT_LoaiHinh_TB_Combobox(this.$refs.cboLoaihinhTB, 1);//Number(this.$refs.cboDichVuVT.value)
                        }

                        await bangts.HT_LoaiGT_Combobox(this.$refs.cboGiayToKH);
                        //await bangts.HT_Loai_KH_Combobox(this.$refs.cboLoaiKH);
                        dtLoaiKH = new DonViFacade().Lay_ds_loaikh(0)[0];
                        this.$refs.cboLoaiKH.dataSource = dtLoaiKH;
                        this.$refs.cboLoaiKH.valueMember = "LOAIKH_ID";
                        this.$refs.cboLoaiKH.DisplayMember = "TEN_LOAIKH";
                        this.$refs.cboLoaiKH.PopupFormWidth = 400;
                        this.$refs.cboLoaiKH.View.GridControl.Size = new Size(20, 20);
                        this.$refs.cboLoaiKH.valueEditStyle = DevExpress.XtraEditors.Controls.valueEditStyles.Standard;
                        this.$refs.cboLoaiKH.View.OptionsView.ShowAutoFilterRow = true;
                        this.$refs.cboLoaiKH.ImmediatePopup = true;
                        this.$refs.cboLoaiKH.value = this.$refs.cboLoaiKH.GetKeyValue(0);

                        await bangts.HT_LOAI_KHLON_Combobox(this.$refs.cboKhachHangLon);
                        await bangts.HT_HinhThucTT_Combobox(this.$refs.cboHTTT);
                        await bangts.HT_DonVi_loaidv_Combobox(this.$refs.cboDonviQL, 0, LOAI_DV.DONVIQL_TT);
                        await bangts.HT_DonVi_loaidv_Combobox(this.$refs.cboDaiVT, 0, LOAI_DV.DONVIQL_LD);
                        await bangts.HT_Tocdo_Adsl_Combobox(cboGoiCuocAdsl);
                        await bangts.HT_MucCuoc_Adsl_Combobox(cboMucCuoc);
                        //Tuan them code hien thi thông tin cac combobox len form -- Ngay 07/07/2010
                        //await bangts.HT_NganHang_Combobox_LD(cboNganHangTT);
                        let dtNganHangTT = new FacadeCommon_v2().Lay_ds_nganhang_ld()[0];
                        await bangts.HT_DS_GRIDLOOKUP(cboNganHangTT, dtNganHangTT, "TEN_NH", "NGANHANG_ID", true, 1, 400, true, true);
                        cboNganHangTT.View.Columns["TEN_NH"].Caption = "Ngân hàng";

                        //await bangts.HT_NganHang_Combobox_LD(this.$refs.cboNganHangKH);
                        let dtNganHang = new FacadeCommon_v2().Lay_ds_nganhang_ld()[0];
                        await bangts.HT_DS_GRIDLOOKUP(this.$refs.cboNganHangKH, dtNganHang, "TEN_NH", "NGANHANG_ID", true, 1, 400, true, true);
                        this.$refs.cboNganHangKH.View.Columns["TEN_NH"].Caption = "Ngân hàng";

                        await bangts.HT_DanToc_Combobox(this.$refs.cboDanToc);
                        await bangts.HT_QuocTich_Combobox(this.$refs.cboQuocTich);
                        await bangts.HT_NganhNghe_Combobox(cboNganhNghe);

                        await bangts.HT_DT_VinaPhone_Combobox(cboDTVinaPhone, 0);
                        ///TuấnNA thêm code hiển thị thông tin các combobox của thuê bao TLS --- Ngày 30/08/2010
                        await bangts.HT_ThoiHan_TSL_Combobox(this.$refs.cboThoiHan);
                        await bangts.HT_TrangBi_TSL_Combobox(this.$refs.cboTrangBi);
                        await bangts.HT_ChuQuan_TSL_Combobox(this.$refs.cboChuQuan);
                        await bangts.HT_Loai_Kenh_Combobox(this.$refs.cboLoaiKenhTSL);
                        await bangts.HT_Tocdo_Kenh_Combobox(this.$refs.cboTocDoKenhTSL);
                        await bangts.HT_LOAI_TB_TSL_Combobox(this.$refs.cboLoaiTBTSL);
                        await bangts.HT_Loai_Kenh_Combobox(this.$refs.cboLoaiKenhTSL);
                        await bangts.HT_Tocdo_Kenh_Combobox(this.$refs.cboTocDoKenhTSL);
                        await bangts.HT_Loai_Kenh_Combobox(this.$refs.cboLoaiKenhWan);
                        await bangts.HT_Tocdo_Kenh_Combobox(this.$refs.cboTocDo);
                        await bangts.HT_Tocdo_Kenh_Combobox(this.$refs.cboTocDo_PIR);
                        await bangts.HT_Loai_Modem_Combobox(this.$refs.cboLoaiModem);
                        await bangts.HT_LOAI_TB_MEGAWAN_Combobox(this.$refs.cboLoaiTBiWan);
                        await bangts.HT_ThoiHan_TSL_Combobox(this.$refs.cboThoihanWan);
                        await bangts.HT_ChuQuan_TSL_Combobox(this.$refs.cboChuQuanWan);

                        //Hiển thị các combo trang bị
                        await bangts.HT_TrangBi_TSL_Combobox(this.$refs.cboTrangBiADSL);
                        await bangts.HT_TrangBi_TSL_Combobox(this.$refs.cboTrangBiDD);
                        await bangts.HT_TrangBi_TSL_Combobox(this.$refs.cboTrangBiGP);
                        await bangts.HT_TrangBi_TSL_Combobox(this.$refs.cboTrangBiWan);

                        */
        //endregion

        this.LAY_DS_SUDUNG_DVGT(0)

        //this.ds_tien_khoanmuc_DV = await bangts.Lay_tienkhoanmuc_loaihd(LoaiHopDong.DAT_MOI);

        let dsTS = this.LAY_DS_THAMSO_MD_MATS('HIENTHI_KHL_DATMOI')
        let ht_khl = -1
        if (dsTS != null && dsTS.length > 0) {
          this.ht_khl = Number(dsTS[0]['TEN_TS'])
        }
        console.log(this.$refs.cboKhachHangLon.style)
        if (this.ht_khl == 1) {
          this.$refs.lblKHL.style.display = null
          this.$refs.cboKhachHangLon_div.style.display = null
          this.$refs.lblPhanLoaiKH.style.display = 'none'
          this.$refs.txtPhanLoaiKH.style.display = 'none'
        } else {
          this.$refs.lblKHL.style.display = 'none'
          this.$refs.cboKhachHangLon_div.style.display = 'none'
          this.$refs.lblPhanLoaiKH.style.display = null
          this.$refs.txtPhanLoaiKH.style.display = null
        }
        rs = await this.axios.get('web-quantri/danhmuc/ds_thamso_md/0')
        let ds_kt1 = rs.data.data
        if (ds_kt1.length > 0) {
          let str_vtemp = 'KIEMTRA_MST' // hoangpkn : 21/07/2018. Tham soos kieemr tra max soos thuee
          let det = ds_kt1.filter((x) => x['ma_ts'] == str_vtemp)

          if (det.length > 0) {
            let row = det[0]
            if (row['ten_ts'] == '1') this.ts_kt_mst = 1
          }

          str_vtemp = 'INDEX_LOAI_KH'
          det = ds_kt1.filter((x) => x['ma_ts'] == str_vtemp)
          if (det.length > 0) {
            let row = det[0]
            this.index_loai_kh = Number(row['ten_ts'])
            if (this.index_loai_kh != 0) this.$refs.cboLoaiKH.value = this.index_loai_kh
          }

          str_vtemp = 'DONVIQL_HTTT'
          det = ds_kt1.filter((x) => x['ma_ts'] == str_vtemp)
          console.log('DONVIQL_HTTT', det)
          if (det.length > 0) {
            let row = det[0]
            if (row['ten_ts'] == '1') this.DONVIQL_HTTT = 1
          }

          str_vtemp = 'phantap_kh' // hoangpkn : 26/10/2020. Tham số phân tập khách hàng
          det = ds_kt1.filter((x) => x['ma_ts'] == str_vtemp)
          if (det.length > 0) {
            let row = det[0]
            if (row['ten_ts'] == '1') this.phantap_kh = true
          }

          str_vtemp = 'CHECK_CHUANHOA_CNTT' // hoangpkn : 18/03/2020. Check thue bao CNTT can chuan hoa
          det = ds_kt1.filter((x) => x['ma_ts'] == str_vtemp)
          if (det.length > 0) {
            let row = det[0]
            if (row['ten_ts'] == '1') this.check_chuahoa_cntt = true
          }

          str_vtemp = 'TUYENTHU_HTTT'
          det = ds_kt1.filter((x) => x['ma_ts'] == str_vtemp)

          if (det.length > 0) {
            let row = det[0]
            if (row['ten_ts'] == '1') this.TUYENTHU_HTTT = 1
          }

          this.ts_sinhma_gd_theo_donvi = ds_kt1.filter((x) => x['ma_ts'] == 'SINH_MA_GD_THEO_DV').length > 0

          // THam số ko bắt buộc phải nhập cộng tác viên
          str_vtemp = 'KHONGBATBUOC_CTV'
          det = ds_kt1.filter((x) => x['ma_ts'] == str_vtemp)
          if (det.length > 0) {
            let row = det[0]
            if (row['ten_ts'] == '1') this.khong_batbuoc_ctv = 1
          }
          // THam số ko bắt buộc phải nhập cộng tác viên
          str_vtemp = 'BATBUOC_GIOITHIEU_CHUYENQUYEN'
          det = ds_kt1.filter((x) => x['ma_ts'] == str_vtemp)
          if (det.length > 0) {
            let row = det[0]
            if (row['ten_ts'] == '1') this.batbuoc_gioithieu_chuyenquyen = 1
          }

          str_vtemp = 'KIEMTRA_MSTMNS_KHDN' // hoangpkn : 21/07/2018. Tham soos kieemr tra max soos thuee
          det = ds_kt1.filter((x) => x['ma_ts'] == str_vtemp)
          if (det.length > 0) {
            let row = det[0]
            if (row['ten_ts'] == '1') this.ts_kt_mstmns_kh = 1
          }

          str_vtemp = 'THEM_TT_HDKHDN' // hoangpkn : 21/07/2018. Tham soos kieemr tra max soos thuee
          det = ds_kt1.filter((x) => x['ma_ts'] == str_vtemp)
          if (det.length > 0) {
            let row = det[0]
            if (row['ten_ts'] == '1') this.ts_kt_tthdkhdn = 1
          }
        }

        this.kt_load = false
        // vma_tinh = await checkdata.MAP_ID("tentat", DatabaseConstants.DB2 + ".tinh", "where tinh_id =" + this.tt_nd.tinh_id + "");
        try {
          let ma_kh = this.$route.query.ma_kh
          let ma_tb = this.$route.query.ma_tb
          let ten_kh = this.$route.query.ten_kh
          let sdt = this.$route.query.sdt
          if (ma_kh) {
            this.$refs.txtMaKH.value = ma_kh
            await this.txtMaKH_KeyPress()
          } else if (ma_tb) {
            this.$refs.txtMaTB.value = ma_tb
            await this.txtMaTB_KeyPress()
          }

          if (ten_kh) {
            this.$refs.txtTenKH.value = ten_kh
          }

          if (sdt) {
            this.$refs.txtTelKH.value = sdt
          }
        } catch (err) {
          console.log(err)
        }

        if (this.autoload) {
          this.Lay_Thongtin()
        }
      } catch (err) {
        console.log(err)
      } finally {
        this.LOCK_UNLOCK_EVENT(false)
      }

      await this.frmChuyenQuyenSD_Activated()
      this.loading(false)
    },

    Lay_DS_MucCuoc_TB(vphold_id, vphuongld_id, vloaitb_id, vdoituong_id, ngay) {
      //vtocdo_id = 0;
      //vmuccuoc_id = 2; //2 Lưu lượng
      //if ((this.CURRENT_LOAITB_ID()== LoaiHinhTB.INTERNET_FTTH) || (this.CURRENT_LOAITB_ID()== LoaiHinhTB.INTERNET_ADSL) || (this.CURRENT_LOAITB_ID()== LoaiHinhTB.INTERNET_MYTV))
      //{
      //    vtocdo_id = Number(cboGoiCuocAdsl.value);
      //    vmuccuoc_id = Number(cboMucCuoc.value);
      //}
      //let ds = [];
      //ds = await tcdanhba.LAY_DS_MUCUOC_TB(vphold_id, vphuongld_id, vloaitb_id, vdoituong_id, vtocdo_id, vmuccuoc_id, ngay);
      //if (ds.length > 0)
      //{
      //    this.$refs.cboMucCuocTB.dataSource = ds[0];
      //    this.$refs.cboMucCuocTB.valueMember = "mucuoctb_id";
      //    this.$refs.cboMucCuocTB.DisplayMember = "tenmuccuoc";
      //}
      //else
      //    this.$refs.cboMucCuocTB.dataSource = null;
    },
    async frmChuyenQuyenSD_Activated() {
      return new Promise(async (resolve, reject) => {
        if (this.$refs.txtMaGD.value) {
          ///TuấnNA: clear form sau khi thanh toán xong --- Ngày 24/09/2010
          let ds
          rs = await this.axios.post('web-hopdong/dichvu-ca/lay_ds_hopdong_theo_ma_gd', {
            in_ma_gd: this.$refs.txtMaGD.value,
            in_loaihd_id: LoaiHopDong.CHUYEN_QUYEN, //Đặt cọc mới
            in_donvi_id: this.tt_nd.donvi_id, //truyền ID đơn vị của người dùng,
            in_tthd_id: TrangThaiHD.MOI,
            in_nhanvien_id: this.tt_nd.nhanvien_id, //truyền ID nhân viên của người dùng,
            in_donvi_dl_id: 0
          })
          ds = rs.data.data
          if (ds.length > 0) this.HienThiTTHopDongKH(ds)
          else this.SetButton(0)
        }
        this.$refs.txtMaTB.focus()
        resolve(true)
      })
    },
    frmChuyenQuyenSD() {
      InitializeComponent()

      this.$refs.lvwThueBao.Anchor = System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right | System.Windows.Forms.AnchorStyles.Bottom

      this.$refs.lvwThanhToan.Anchor = System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right | System.Windows.Forms.AnchorStyles.Bottom

      this.$refs.lvDvkhac_TT.Anchor = System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right | System.Windows.Forms.AnchorStyles.Bottom

      //Chuyển tabcontrol sang control mới
      tabThongTin = this.CopyTabControlToFakeXtra(this, tabThongTin)
      tabThongTin.BringToFront()
    },

    async LoadTuHopDongTuVan(phdkh_cha_id) {
      try {
        this.SetButton(1)
        // Lấy thông tin từ phiếu tư vấn
        rs = await this.axios.post('/web-quantri/lay-dulieu/sp_tt_hd_thuebao', {
          param: phdkh_cha_id,
          type: 1
        })
        var d = rs.data.data
        if (d.length > 0) {
          this.hdkh_cha_id = phdkh_cha_id
          this.$refs.txtTenKH.value = this.$refs.txtTenTB.value = d[0]['TEN_KH']
          this.$refs.txtDiaChiKH.value = this.$refs.txtDiaChiTB.value = this.$refs.txtDiachiLD.value = d[0]['DIACHI_KH']
          this.$refs.txtTelKH.value = d[0]['SO_DT']

          // Khách hàng mới
          if (d[0]['KHACHHANG_ID'] == '0') {
            // Load thông tin địa chỉ khách hàng
            rs = await this.axios.post('/web-quantri/lay-dulieu/sp_tt_diachi_hdkh', {
              param: phdkh_cha_id,
              type: 1
            })
            d = rs.data.data
            if (d.length > 0) {
              this.tinhld_id = this.tinhtb_id = Number(d[0]['tinh_id'])
              this.quanld_id = this.quantb_id = Number(d[0]['quan_id'])
              this.phuongld_id = this.phuongtb_id = Number(d[0]['phuong_id'])
              this.phold_id = this.photb_id = Number(d[0]['pho_id'])
              this.khuld_id = this.khutb_id = Number(d[0]['khu_id'])
              this.apld_id = this.aptb_id = Number(d[0]['ap_id'])
              this.sonhald = this.sonhatb = d[0]['SONHA']
              if (d[0]['block_ld']) this.blockld = d[0]['block_ld']
              if (d[0]['tang_ld']) this.tangld = d[0]['tang_ld']
              if (d[0]['phong_ld']) this.sophongld = d[0]['phong_ld']
              if (d[0]['mota_ld']) this.motadcld = d[0]['mota_ld']
              /*
                                          if (d[0]["VIDO"] != null && d[0]["VIDO"])
                                              Lat_KH = Lat_TT = Lat_LD = Lat_TB = Convert.ToDouble(d[0]["VIDO"]);
                                          if (d[0]["KINHDO"] != null && d[0]["KINHDO"])
                                              Lng_KH = Lng_TT = Lng_LD = Lng_TB = Convert.ToDouble(d[0]["KINHDO"]);
                                           */
            }
          }
          // Khách hàng đã sử dụng dịch vụ của mình
          else {
            this.khachhang_id = Number(d[0]['KHACHHANG_ID'])
            this.$refs.txtMaTB.value = d[0]['MA_TB']
          }
        }
      } catch (err) {
        this.$toast.error(err.message)
      }
    },

    //endregion

    //region Set button
    SetButton(kieu) {
      this.$refs.tsbtnNhapMoi.classList.add('disabled')
      this.$refs.tsbtnGhiLai.classList.add('disabled')
      this.$refs.tsbtnXoa.classList.add('disabled')
      this.$refs.tsbtnHuyBo.classList.add('disabled')

      //tsbtnXoaTB.Enabled = false;
      //tsbtnXoaTT.Enabled = false;
      this.$refs.tsbtnThemTB.classList.add('disabled')
      //tsbtnThemTT.Enabled = false;
      this.$refs.tsddbIn.classList.add('disabled')
      //tsbtnInhopdong.Enabled = false;
      //tsbtnInPhieuYC.Enabled = false;//Tuan dat thuoc tinh mac dinh cho nut In PL --Ngay 07/07/2010

      this.$refs.tsbtnThanhToan.classList.add('disabled')
      console.log(this.$refs.tsbtnThanhToan)
      this.$refs.tsbtnGiaoPhieu.classList.add('disabled')

      this.$refs.tsbtnThemTB_DS.classList.add('disabled')

      if (kieu == -1) {
        //Bat dau
        this.$refs.txtMaTB.focus()
        this.$refs.tsbtnGhiLai.classList.remove('disabled')
        this.$refs.tsbtnHuyBo.classList.remove('disabled')
        this.$refs.tsbtnXoaTB.classList.add('disabled')
        this.$refs.tsbtnXoaTB_DS.classList.add('disabled')
        this.$refs.cboMaDD.value = ''
        this.$refs.cboMaDD.dataSource = []
      }

      if (kieu == 0) {
        //Bat dau
        this.$refs.txtMaTB.focus()
        this.$refs.tsbtnNhapMoi.classList.remove('disabled')
        this.$refs.tsbtnThemTT.classList.add('disabled')
        this.$refs.tsbtnXoaTB.classList.add('disabled')
        this.$refs.tsbtnXoaTB_DS.classList.add('disabled')
        this.Clear()
      }

      if (kieu == 1) {
        //Nhap moi
        this.$refs.txtMaTB.focus()
        this.$refs.tsbtnGhiLai.classList.remove('disabled')
        this.$refs.tsbtnHuyBo.classList.remove('disabled')
        this.$refs.tsbtnThemTT.classList.add('disabled')
        this.$refs.tsbtnXoaTB.classList.add('disabled')
        this.$refs.tsbtnXoaTB_DS.classList.add('disabled')
        this.Clear()
      }

      if (kieu == 2) {
        //Huy
        this.$refs.txtMaGD.focus()
        this.$refs.tsbtnNhapMoi.classList.remove('disabled')
        this.$refs.tsbtnXoa.classList.remove('disabled')
        this.Clear()
      }

      if (kieu == 3) {
        //Edit
        this.$refs.txtMaGD.focus()

        this.$refs.tsbtnNhapMoi.classList.remove('disabled')
        this.$refs.tsbtnXoa.classList.remove('disabled')
        this.$refs.tsbtnGhiLai.classList.remove('disabled')
        this.$refs.tsbtnHuyBo.classList.remove('disabled')

        if (this.$refs.lvwThanhToan.dataSource.length > 1) this.$refs.tsbtnXoaTT.classList.remove('disabled')
        if (this.$refs.lvwThueBao.dataSource.length > 1) {
          this.$refs.tsbtnXoaTB.classList.remove('disabled')
          this.$refs.tsbtnXoaTB_DS.classList.remove('disabled')
        }
        this.$refs.tsbtnThemTB.classList.remove('disabled')
        this.$refs.tsbtnThemTB_DS.classList.remove('disabled')
        //tsbtnThemTT.Enabled = true;

        //Tuan them code hien thi các nut In  --Ngay 07/07/2010
        //tsbtnInphuluc.Enabled = true;
        //tsbtnInPhieuYC.Enabled = true;
        this.$refs.tsddbIn.classList.remove('disabled')
        //if (this.TBHD_PL == 1)
        //{
        //    inHợpĐồngToolStripMenuItem.Enabled = true;
        //}
        //else
        //{
        //    inHợpĐồngToolStripMenuItem.Enabled = false;
        //}

        this.$refs.tsbtnThanhToan.classList.remove('disabled')
        this.$refs.tsbtnGiaoPhieu.classList.remove('disabled')
      }
    },
    //endregion

    //region Clear
    Clear() {
      try {
        this.pt_doanhthu = 0
        this.pt_diem_tn = 0

        this.$refs.txtNhanVienQL.value = ''
        this.nhanvien_ql_am_id = 0

        this.$refs.txtMaHD_TTKD_MGW.value = ''
        this.$refs.txtMaHD_TTKD_TSL.value = ''
        this.$refs.txtSiteID.value = ''

        this.$refs.cboMaDD.value = ''
        this.$refs.cboMaDD.dataSource = []
        this.$refs.dtpNgayYC.value = this.tt_nd.ngay_cn
        this.$refs.dtpNgayLapHDNQ.value = this.tt_nd.ngay_cn
        this.quytrinh_id = 0
        this.trangbi_id = 3
        this.$refs.txtTongTienHD.value = ''
        this.hdkh_id = 0
        this.$refs.txtMaGD.value = ''
        this.$refs.txtMaHDCu.value = ''
        this.$refs.txtMaKH.value = ''
        this.khachhang_id = 0
        this.$refs.txtTenKH.value = ''
        this.$refs.txtDiaChiKH.value = ''

        this.quankh_id = 0
        this.phuongkh_id = 0
        this.phokh_id = 0
        this.apkh_id = 0
        this.khukh_id = 0
        this.dacdiemkh_id = 0
        this.sonhakh = ''
        try {
          this.$refs.txtNguoiGT.value = ''
          this.$refs.txtCTV.value = ''
          this.nguoigt_id = 0
          this.ctv_id = 0
        } catch (err) {}
        this.$refs.txtNguoiDD.value = ''
        this.$refs.txtChucDanhDD.value = ''
        this.$refs.txtTelKH.value = ''
        this.$refs.txtFaxKH.value = ''

        this.$refs.txtNgaySN.value = ''
        //dtpNgayLapHD.value = this.tt_nd.ngay_cn;
        this.$refs.txtSoGT.value = ''
        this.$refs.txtNgayCap.value = ''
        this.$refs.txtNoiCap.value = ''
        this.$refs.cboGiayToKH.SelectedIndex = 0
        this.$refs.txtDacTrung.value = ''
        this.$refs.cboTT.dataSource = null
        this.$refs.lvwThanhToan.dataSource = []
        this.$refs.lvwThueBao.dataSource = []
        this.$refs.txtMaTuyen.value = ''

        this.$refs.rdoGTNam.checked = true

        this.hdtt_id = 0
        this.httt_id_cu = -1
        this.$refs.txtMaTT.value = ''
        this.$refs.txtTenTT.value = ''
        this.$refs.txtDiaChiTT.value = ''
        this.quantt_id = 0
        this.phuongtt_id = 0
        this.phott_id = 0
        this.aptt_id = 0
        this.khutt_id = 0
        this.dacdiemtt_id = 0
        this.sonhatt = ''

        this.$refs.txtMST_TT.value = ''
        this.$refs.txtSTK_TT.value = ''
        this.$refs.txtChu_TK.value = ''
        this.$refs.cboHTTT.SelectedIndex = 0
        this.$refs.cboDonviQL.SelectedIndex = 0 /// Tuan comment code bo this.$refs.cboDonviQL -- Ngay 29/07/2010
        //cboNganHangTT.SelectedIndex = 0;
        // cboNganHangTT.SelectedIndex = 0;

        this.$refs.txtDiaChiNDD.value = ''
        this.quanndd_id = 0
        this.phuongndd_id = 0
        this.phondd_id = 0
        this.apndd_id = 0
        this.khundd_id = 0
        this.dacdiemndd_id = 0
        this.sonhandd = ''

        this.$refs.txtDiaChiCT.value = ''
        //diachi bc
        this.$refs.txtDiaChiBC.value = ''
        this.tinhbc_id = 0
        this.quanbc_id = 0
        this.phuongbc_id = 0
        this.phobc_id = 0
        this.apbc_id = 0
        this.khubc_id = 0
        this.dacdiembc_id = 0
        this.sonhabc = ''
        //

        this.hdtb_id = 0
        this.thuebao_id = 0
        this.thuebao_cha_id = 0
        this.$refs.txtMaTB.value = ''
        this.$refs.txtTenTB.value = ''
        this.$refs.txtDiaChiTB.value = ''
        this.quantb_id = 0
        this.phuongtb_id = 0
        this.photb_id = 0
        this.aptb_id = 0
        this.khutb_id = 0
        this.dacdiemtb_id = 0
        this.sonhatb = ''
        this.blocktb = ''
        this.tangtb = ''
        this.sophongtb = ''
        this.motadctb = ''
        if (this._dichvuvt_id) {
          //PNLINH 27/04/2020
          this.dichvuvt_id = this._dichvuvt_id //PNLINH 27/04/2020
        } else {
          this.dichvuvt_id = DichVuVienThong.CO_DINH
        }
        this.$refs.cboDichVuVT.value = this.dichvuvt_id

        if (this.$refs.cboLoaihinhTB.value != null) {
          this.$refs.cboKieuLD.SelectedIndex = 0
          this.$refs.cboDoituongTB.SelectedIndex = 0
        }
        this.tuyenthu_id = 0
        this.nhanvientc_id = 0

        this.$refs.txtTenKHCu.value = ''
        this.$refs.txtDiaChiKHCu.value = ''
        this.$refs.txtMaTBCu.value = ''
        this.$refs.txtMaKHCu.value = ''
        this.$refs.txtTenTBCu.value = ''
        if (this.index_loai_kh != 0) this.$refs.cboLoaiKH.value = this.index_loai_kh

        this.$refs.txtNoiCapHK.value = ''
        this.$refs.txtMaHD.value = ''
        this.$refs.lvDvkhac.dataSource = []
        this.$refs.lvDvgt.dataSource = []
        this.khachhang_id_cq = 0
        this.khachhang_id_nm = 0
        this.$refs.txtMaNVTC.value = ''
        //rdoGTNone.checked = true;
        //txtDactrung.value = "";
        this.$refs.txtDacTrung.value = ''
        this.$refs.txtChucDanhDD.value = ''
        this.$refs.txtMaTBDD.value = ''
        //End tuan
        /// Tuấn thêm code clear các biến, khởi tạo lại giá trị ban đầu -- Ngày 29/07/2010
        this.quanld_id = 0
        this.phuongld_id = 0
        this.phold_id = 0
        this.apld_id = 0
        this.khuld_id = 0
        this.dacdiemld_id = 0
        this.sonhald = ''
        this.blockld = ''
        this.tangld = ''
        this.sophongld = ''
        this.motadcld = ''
        this.$refs.txtDiachiLD.value = ''
        console.log('set ds[0]["ngay_sn"] 1')
        this.$refs.txtNgaySinhTB.value = ''
        this.$refs.txtNgaySN.value = ''
        this.ma_kh_cu = ''
        this.ma_tb = ''
        this.ma_tt_cu = ''
        this.ma_tb_cu = ''
        this.ma_gd = ''
        this.$refs.cboDonviQL.SelectedIndex = 0
        this.khachhang_id_nm = 0
        this.khachhang_id_cq = 0
        this.ma_gd = ''
        this.TBHD_PL = 1
        this.ma_tt_cu = ''
        this.ten_tt_cu = ''
        this.diachi_tt_cu = ''
        this.ma_tb_cu = ''
        this.ten_tb_cu = ''
        this.check_tb_dd = false
        this.donvi_ql = 0
        this.ma_kh_cu = ''

        this.loaihd_id = 0
        this.kieuld_id = 0
        this.doituong_id = 0
        this.ngaylhd = ''

        //this.donvi_qlcu = "";
        //this.donvi_qlmoi = "";
        this.$refs.cboNganhNgheLoc.value = this.$refs.cboNganhNgheLoc.dataSource[0]
        this.$refs.cboQuocTich.SelectedIndex = 0
        this.$refs.cboDanToc.SelectedIndex = 0

        /// Tuấn thêm code clear các biến, khởi tạo lại giá trị các biến thuê bao truyền số liệu -- Ngày 31/08/2010
        if (this.load_dm_tsl) {
          this.$refs.cboTocDoKenhTSL.SelectedIndex = 0
          this.$refs.cboLoaiKenhTSL.SelectedIndex = 0
          this.$refs.cboThoiHan.SelectedIndex = 0
          this.$refs.cboLoaiTBTSL.SelectedIndex = 0
          this.$refs.cboTrangBi.SelectedIndex = 0
          this.$refs.cboChuQuan.SelectedIndex = 0
        }

        this.$refs.txtDiaChiDau.value = ''
        this.$refs.txtDiaChiCuoi.value = ''

        this.donvi_dau_id = 0
        this.diachidau_id = 0
        this.phodau_id = 0
        this.ngodau_id = 0
        this.phuongdau_id = 0
        this.quandau_id = 0
        this.sonhadau = ''

        this.donvi_cuoi_id = 0
        this.diachicuoi_id = 0
        this.phocuoi_id = 0
        this.ngocuoi_id = 0
        this.phuongcuoi_id = 0
        this.quancuoi_id = 0
        this.sonhacuoi = ''
        this.$refs.txtGhiChuTT.value = ''

        this.$refs.txtGhiChuKH.value = ''
        this.$refs.txtGhiChuTB.value = ''
        this.$refs.txtSoTKKH.value = ''
        this.$refs.txtNgayCapHK.value = ''
        this.$refs.txtHoKhau.value = ''
        this.clear_manvtc = '0'
        this.$refs.cboKhachHangLon.SelectedIndex = 0

        ///Tuấn thêm code clear một số thông tin của MGWan
        ///--- Ngày 12/09/2010
        if (this.load_dm_wan) {
          this.$refs.cboChuQuanWan.SelectedIndex = 0
          this.$refs.cboLoaiTBiWan.SelectedIndex = 0
          this.$refs.cboLoaiModem.SelectedIndex = 0
          this.$refs.cboChuQuanWan.SelectedIndex = 0
          this.$refs.cboThoihanWan.SelectedIndex = 0
          this.$refs.cboTocDo.SelectedItem = 0
          this.$refs.cboLoaiKenhWan.SelectedIndex = 0
        }

        this.$refs.txtMaSTKH.value = ''
        this.$refs.txtNgayHetHan.value = ''
        this.$refs.chkNganHangKH.checked = false
        //this.$refs.cboNganHangKH.SelectedIndex = 0;
        this.$refs.cboNganHangKH.SelectedIndex = 0

        // txtTienCQ.value = "0";
        //txtVATCQ.value = "0";
        //txtTienCD.value = "0";
        //txtVATCD.value = "0";
        //txtTienDV.value = "0";
        //txtVATDV.value = "0";
        this.doituong_id = 1

        this.LayTienTheoKhoanMuc()

        //TuấnNA thêm code clear ngân sách nhà nước --- Ngày 12/11/2010
        this.DV_QHNS = ''
        this.MACHUONG = ''
        this.MN_KT = ''
        this.MA_NDKT = ''
        this.MN_NSNN = ''
        this.TIEN_DM = 0

        //TuấnNA thêm code clear biến -- Ngày 03/12/2010
        this.TNC1_ID = 0
        this.TNC2_ID = 0
        this.TNC3_ID = 0
        this.TNC4_ID = 0
        this.nganhnghe_id = 0
        this.$refs.cboMuccuocTBCu.dataSource = null
        this.$refs.cboMucCuocTB.dataSource = null
        this.$refs.dtpNgaySD.value = this.tt_nd.ngay_cn
        //LAY_DS_SUDUNG_DVK(0, 1);
        //LAY_DS_SUDUNG_DVK(0, 2);
        this.$refs.lvDvkhac_TT.dataSource = []

        this.hdmig_id = 0
        this.mig_id = 0
        this.$refs.txtMaIG.value = ''
      } catch (err) {
        console.log(err)
      }
    },
    //endregion

    //region this.$refs.cboDichVuVT SelectedValueChanged
    async cboDichVuVT_SelectedValueChanged() {
      if (this.$refs.cboDichVuVT.dataSource && this.$refs.cboDichVuVT.dataSource.length > 0) {
        if (!this.$refs.cboDichVuVT.value) return
        this.dichvuvt_id = this.CURRENT_DVVT_ID()
        rs = await this.axios.get('web-hopdong/danhmuc/ds_loaitb/' + this.dichvuvt_id)
        console.log(rs.data.data)
        this.$refs.cboLoaihinhTB.HT_COMBOBOX(rs.data.data, 'loaihinh_tb', 'loaitb_id')
        //await bangts.HT_LoaiHinh_TB_Combobox(this.$refs.cboLoaihinhTB, this.dichvuvt_id);

        //CUONG ZEM
        /* if (this.dichvuvt_id == DichVuVienThong.ADSL)
                             this.$refs.cboLoaihinhTB.value = LoaiHinhTB.INTERNET_ADSL;*/

        //CUONG ZEM
        /* var t = dsDanhMuc["GOICUOC_DD"].Select("LOAITB_ID =" + this.CURRENT_LOAITB_ID()+ "");
                         await bangts.HT_COMBOBOX(cboGoiCuocDD, t.length > 0 ? t.CopyToDataTable() : dsDanhMuc["GOICUOC_DD"].Clone(), "tengoicuoc", "goicuoc_id");*/

        //await bangts.HT_GoiCuoc_DD_Combobox(cboGoiCuocDD, CURRENT_LOAITB_ID);
        //this.GiaoDien_DichVuVT(this.dichvuvt_id);
      }
    },

    async GiaoDien_DichVuVT(dichvuvt_id) {
      this.$refs.pnlTSL.style.display = 'none'
      this.$refs.pnlInt.style.display = 'none'
      this.$refs.pnlDD.style.display = 'none'
      this.$refs.pnlAdsl.style.display = 'none'
      this.$refs.pnlWan.style.display = 'none'
      this.$refs.pnlGphone.style.display = 'none'
      this.$refs.pnlIMS.style.display = 'none'

      switch (dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          //this.$refs.btnDiaChiTB.Enabled = false;
          break
        case DichVuVienThong.DI_DONG:
          if (!this.load_dm_dd) {
            console.log(1)
            this.$refs.cboTrangBiDD.HT_COMBOBOX(this.dsDanhMuc['trang_bi'], 'tentrangbi', 'trangbi_id')
            console.log(2)
            this.$refs.cboDTVinaPhone.HT_COMBOBOX(this.dsDanhMuc['doituong_neo'], 'ten_dt_neo', 'doituong_id_neo')
            this.load_dm_dd = true
          }
          console.log(3)
          var t = this.dsDanhMuc['goicuoc_dd'].filter((item) => (item.LOAITB_ID = this.CURRENT_LOAITB_ID()))
          this.$refs.cboGoiCuocDD.HT_COMBOBOX(t.length > 0 ? t : this.dsDanhMuc['goicuoc_dd'], 'tengoicuoc', 'goicuoc_id')
          console.log(4)
          this.$refs.btnDiaChiTB.classList.remove('disabled')
          this.$refs.pnlDD.style.display = null
          break

        case DichVuVienThong.MEGA_EYES:
        case DichVuVienThong.TRUNGTAM_DULIEU:
        case DichVuVienThong.HOINGHI_TRUYENHINH:
        case DichVuVienThong.ANTOAN_BAOMAT_TT:
        case DichVuVienThong.DICHVU_CNTT:
        //this.$refs.btnDiaChiTB.Enabled = false;
        /*this.$refs.pnlAdsl.style.display = null;
                            this.$refs.pnlAdsl.Dock = DockStyle.Fill;
                            break;*/
        case DichVuVienThong.ADSL:
          if (!this.load_dm_adsl) {
            this.$refs.cboMucCuoc.HT_COMBOBOX(this.dsDanhMuc['muccuoc'], 'tenmuc', 'muccuoc_id')
            this.$refs.cboTrangBiADSL.HT_COMBOBOX(this.dsDanhMuc['trang_bi'], 'tentrangbi', 'trangbi_id')
            this.load_dm_adsl = true
          }
          this.$refs.pnlAdsl.style.display = null
          break
        case DichVuVienThong.TSL:
          //TSL
          if (!this.load_dm_tsl) {
            this.$refs.cboChuQuan.HT_COMBOBOX(this.dsDanhMuc['chuquan'], 'ten_tat', 'chuquan_id')
            this.$refs.cboTrangBi.HT_COMBOBOX(this.dsDanhMuc['trang_bi'], 'tentrangbi', 'trangbi_id')
            this.$refs.cboThoiHan.HT_COMBOBOX(this.dsDanhMuc['thoihan'], 'tenthoihan', 'thoihan_id')
            this.$refs.cboLoaiKenhTSL.HT_COMBOBOX(this.dsDanhMuc['loai_kenh'], 'loai_kenh', 'loaikenh_id')
            this.$refs.cboLoaiTBTSL.HT_COMBOBOX(
              this.dsDanhMuc['loai_tb'].filter((item) => item.dichvuvt_id == DichVuVienThong.TSL),
              'ten_tbi',
              'thietbidc_id'
            )
            this.$refs.cboTocDoKenhTSL.HT_COMBOBOX(this.dsDanhMuc['tocdo_kenh'], 'toc_do', 'tocdo_id')
            this.load_dm_tsl = true
          }

          //this.$refs.btnDiaChiTB.Enabled = false;
          this.$refs.pnlTSL.style.display = null
          break
        case DichVuVienThong.GPHONE:
          if (!this.load_dm_gp) {
            this.$refs.cboTrangBiGP.HT_COMBOBOX(this.dsDanhMuc['trang_bi'], 'tentrangbi', 'trangbi_id')
            this.load_dm_gp = true
          }
          //this.$refs.btnDiaChiTB.Enabled = false;
          this.$refs.pnlGphone.style.display = null
          break
        case DichVuVienThong.MEGAWAN:
        case DichVuVienThong.METRONET:
          //this.$refs.btnDiaChiTB.Enabled = false;
          if (!this.load_dm_wan) {
            this.$refs.cboLoaiKenhWan.HT_COMBOBOX(this.dsDanhMuc['loai_kenh'], 'loai_kenh', 'loaikenh_id')

            this.$refs.cboThoihanWan.HT_COMBOBOX(this.dsDanhMuc['thoihan'], 'tenthoihan', 'thoihan_id')

            this.$refs.cboLoaiTBiWan.HT_COMBOBOX(
              this.dsDanhMuc['loai_tb'].filter((x) => x.dichvuvt_id == this.CURRENT_DVVT_ID()),
              'ten_tbi',
              'thietbidc_id'
            )

            this.$refs.cboTocDo_PIR.HT_COMBOBOX(this.dsDanhMuc['tocdo_kenh'], 'toc_do', 'tocdo_id')
            console.log(1)
            this.$refs.cboTrangBiWan.HT_COMBOBOX(this.dsDanhMuc['trang_bi'], 'tentrangbi', 'trangbi_id')
            console.log(2)
            this.$refs.cboChuQuanWan.HT_COMBOBOX(this.dsDanhMuc['chuquan'], 'ten_tat', 'chuquan_id')
            console.log(3)
            this.$refs.cboLoaiModem.HT_COMBOBOX(this.dsDanhMuc['loai_modem'], 'loai_md', 'loaimd_id')
            console.log(4)
            this.$refs.cboTocDo.HT_COMBOBOX(this.dsDanhMuc['tocdo_kenh'], 'toc_do', 'tocdo_id')
            this.load_dm_wan = true
          }

          this.$refs.pnlWan.style.display = null
          break
        case DichVuVienThong.IMS:
          this.$refs.pnlIMS.style.display = null
          rs = await this.axios.post('web-hopdong/thaydoiloaihinhtbcodinhims/sp_ht_dichvuvt_combobox', {
            dichvu_vt: '11'
          })
          this.$refs.cboThietBi.HT_COMBOBOX(rs.data.data, 'ten_tbi', 'thietbi_id')
          this.$refs.cboTrangBiIMS.HT_COMBOBOX(this.dsDanhMuc['TRANG_BI'], 'tentrangbi', 'trangbi_id')
          //await bangts.HT_TrangBi_TSL_Combobox(this.$refs.cboTrangBiIMS);
          break
        default:
          break
      }
    },
    //endregion

    //region HienThiTT_DanhBa
    async HienThiTT_DanhBa(ma_tb) {
      return new Promise(async (resolve, reject) => {
        try {
          ///Tuấn NA sửa code cho phép user được phép lập hợp đồng cho thuê bao không thuộc đơn vị dữ liệu của user đó --- Ngày 19/10/2010
          ///this.tt_nd.donvi_dl_id --> 0
          rs = await this.axios.post('web-hopdong/chuyendich/lay_danhba_theo_matb', {
            MA_TB: ma_tb,
            DICHVUVT_ID: this.$refs.cboDichVuVT.value,
            DONVI_DL_ID: 0
          })
          var ds = rs.data.data;

          //Ý nghĩa đoạn này do load lúc đầu ẩn hết sự kiện nên cần trả lại
          if (!this.$refs.cboLoaihinhTB.value) {
            this.cboDichVuVT_SelectedValueChanged()
            if (this.index_loai_kh != 0) this.$refs.cboLoaiKH.value = this.index_loai_kh
            this.$refs.cboDoituongTB.value = -1
          }
          if (ds && ds.length > 0) {
            //Lamnt: Neu co nhieu hon 2 thue bao trung ma-> Hien thi form chon
            if (ds.length > 1) {

              console.log(ds, 'trước chọn');
              rs = await this.axios.get('web-hopdong/hopdong/lay_ds_thuebao_matb?maTb=' + ma_tb.trim() + '&dvvtId=' + this.$refs.cboDichVuVT.value)
              let dsChon = rs.data.data ? rs.data.data : [];
              this.$refs.frmDSTBCungMa.ds = dsChon;
              this.loading(false);

              let rs = await this.$refs.frmDSTBCungMa.showDialogAndWait();
              let tbChon=rs.itemSelected;

              if(tbChon!=null && tbChon.length>0)
                ds=ds.filter((tb) => tb.thuebao_id==tbChon[0].thuebao_id);
              else
                return;
              
              console.log(ds, 'sau chọn'); 
            } 

            {
              let ds_km = []
              rs = await this.axios.get('web-tracuu/danhba/lay_ds_chitiet_km_by_id?thuebaoId=' + ds[0]['thuebao_id'] + '&kieu=2')
              ds_km = rs.data.data
              if (ds_km && ds_km.length > 0 && !this.dikem) {
                let tb = ''
                tb = 'Thuê bao ' + ds[0]['ma_tb']
                tb += ' đang hưởng khuyến mại : \n ' + ds_km[0]['ten_km']
                tb += ' \n    Có chi tiết khuyến mại : '
                for (let i = 0; i < ds_km.length; i++) {
                  tb += '\n     - ' + ds_km[i]['ten_ctkm']
                }
                //tb += "\nBạn không được thực hiện tiếp với thuê bao này!";
                this.$toast.success(tb)
                // resolve(true)
                // return;
              }
              //End TuanNA

              //TuấnNA thêm code thông báo thuê bao lập PL khác -- ngày 15/12/2010
              //HungHM-09/09/2010 - Kiểm tra xem mã TB này có đang được lập hợp đồng hoặc phụ lục khác hay không?
              rs = await this.axios.post('web-quantri/dangky-dichvu-vcc/kiemtra_ma_tb_lap_hopdong_khac', {
                ma_tb: this.$refs.txtMaTB.value,
                ma_gd: '0',
                dichvuvt_id: this.CURRENT_DVVT_ID(),
                loaihd_id: LoaiHopDong.CHUYEN_QUYEN
              })
              if (rs.data.data != 'OK') {
                this.$toast.error(rs.data.data)
                resolve(true)
                return
              }
              //

              ///Tuấn thêm code kiểm tra trạng thái thuê bao --- Ngày 07/09/2010
              ///---> Nếu trạng thái thuê bao // " Hoạt động bình thường" ---> không cho phép làm tiếp
              console.log(ds, 'ơ kìa')
              if (ds[0]['trangthaitb_id']) {
                console.log(ds[0]['trangthaitb_id'])
                if (Number(ds[0]['trangthaitb_id']) != TrangThaiTB.THUONG && Number(ds[0]['trangthaitb_id']) != TrangThaiTB.KHOA_MOTCHIEU_YEUCAU && Number(ds[0]['trangthaitb_id']) != TrangThaiTB.KHOA_HAICHIEU_YEUCAU && Number(ds[0]['trangthaitb_id']) != TrangThaiTB.TAMDUNG) {
                  this.$toast.error('Thuê bao có mã: ' + ds[0]['ma_tb'] + ' đang ở trạng thái: ' + ds[0]['trangthai_tb'] + '\nBạn không thể thực hiện chuyển quyền cho thuê bao này')
                  resolve(true)
                  return
                }
              }
              //Tuan bo sung code kiem tra khach hang da ky hop dong goc chua -- Ngay 07/07/2010
              //region lấy thông tin trong danh bạ
              if (Number(ds[0]['ky_hd']) == 0) {
                this.$toast.error('Khách hàng có mã thuê bao: ' + this.$refs.txtMaTB.value + ' chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc')
              } else {
                //Tuan bo sung code kiem tra thue bao nhap moi co thuoc khach hang dang chuyen quyen hay khong--Ngay 05/07/2010
                this.khachhang_id_nm = Number(ds[0]['khachhang_id'])
                // dang co khach hang dang chuyen quyen
                if (this.khachhang_id != 0 && this.$refs.lvwThueBao.dataSource.length > 0) {
                  if (this.khachhang_id_nm != this.khachhang_id_cq) {
                    console.log(this.khachhang_id_nm, this.khachhang_id_cq)
                    this.$toast.error('Mã thuê bao vừa nhập không thuộc khách hàng đang chuyển quyền. Bạn hãy kiểm tra lại !')
                  } else {
                    //Thông tin khách hàng (cũ)
                    this.$refs.txtMaHDCu.value = ds[0]['ma_hd']
                    this.$refs.txtMaKHCu.value = ds[0]['ma_kh']
                    this.ma_kh_cu = ds[0]['ma_kh'] ///Tuấn thêm code mã khách hàng cũ -- Ngày 06/08/2010
                    if (ds[0]['ngaylap_hd']) this.$refs.dtpNgayLapHDCQ.value = ds[0]['ngaylap_hd']
                    else this.$refs.dtpNgayLapHDCQ.value = this.tt_nd.ngay_cn
                    this.khachhang_id_cq = Number(ds[0]['khachhang_id'])
                    console.log('set khachhang_id_cq', ds[0]['khachhang_id'])
                    this.$refs.txtTenKHCu.value = ds[0]['ten_kh']
                    // Tuan them code lay gia tri this.ten_tb_cu cho khach hang CQ -- ngay 22/07/2010
                    this.ten_tb_cu = ds[0]['ten_tb']
                    this.$refs.txtDiaChiKHCu.value = ds[0]['diachi_kh']
                    rs = await this.axios.post('web-danhba/dungluong_thitruong/lay_ds_diachi_theo_dbkhid', {
                      in_khachhang_id: this.khachhang_id
                    })
                    this.HienThiTTDiaChi(rs.data.data, 1)
                    // Tuan them code lay gia tri this.ma_tt_cu cho khach hang CQ -- ngay 22/07/2010
                    this.ma_tt_cu = ds[0]['ma_tt']
                    // Tuan them code lay gia tri this.ten_tt_cu cho khach hang CQ -- ngay 22/07/2010
                    this.ten_tt_cu = ds[0]['ten_tt']
                    // Tuan them code lay gia tri this.diachi_tt_cu cho khach hang CQ -- ngay 22/07/2010
                    this.diachi_tt_cu = ds[0]['diachi_tt']

                    //Thông tin thuê bao
                    //region Thông tin thuê bao
                    this.thuebao_id = Number(ds[0]['thuebao_id'])
                    console.log('có vào đây không?', this.thuebao_id)

                    if (ds[0]['thuebao_cha_id']) this.thuebao_cha_id = Number(ds[0]['thuebao_cha_id'])
                    else this.thuebao_cha_id = 0

                    rs = await this.axios.post('web-quantri/dangky-dichvu-vcc/kiemtra_ma_tb_lap_hopdong_khac', {
                      ma_tb: this.$refs.txtMaTB.value,
                      ma_gd: '0',
                      dichvuvt_id: this.CURRENT_DVVT_ID(),
                      loaihd_id: LoaiHopDong.CHUYEN_QUYEN
                    })
                    if (rs.data.data != 'OK') {
                      this.$toast.error(rs.data.data)
                      resolve(true)
                      return
                    }

                    ///TuấnNA thêm code chặn trường hợp thuê bao di động nhập mới không cùng mã thanh toán với các thuê bao di động có trong hợp đồng
                    ///--- Ngày 15/07/2010
                    if (this.dichvuvt_id == DichVuVienThong.DI_DONG && this.CURRENT_LOAITB_ID() == LoaiHinhTB.DIDONGTRASAU) {
                      if (this.ma_gd) {
                        if ((await tchopdong.KIEMTRA_TBDD_CUNG_MATT_HDTB(this.thuebao_id, this.ma_gd)) != '1') {
                          this.$toast.success('Số thuê bao di động: ' + this.$refs.txtMaTB.value + ' vừa nhập không cùng mã thanh toán: ' + ds[0]['ma_tt'] + ' với thuê bao di động trong phụ lục\nBạn phải lập một phụ lục khác cho thuê bao này! ')
                          this.loading(false)
                          return
                        }
                      }
                    }

                    ///TuấnNA sửa code hiển thị combobox đài VT Full --- Ngày 19/10/2010
                    ///Trong trường hợp thuê bao không thuộc đơn vị dữ liệu của user, chương trình tự động chọn this.$refs.cboDaiVT --- > donvi_id(db_thuebao)
                    ///Giao dịch viên không được chọn.
                    //await bangts.HT_DaiVT_Combobox(this.$refs.cboDaiVT, 0);
                    //Tuan them code hien thi this.loaitb_id -- Ngay 23/07/2010
                    this.$refs.cboLoaihinhTB.value = Number(ds[0]['loaitb_id'])
                    await this.cboLoaihinhTB_SelectedValueChanged()
                    //Tuan them code hien thi diachi lapdat -- Ngay 29/07/2010
                    this.$refs.txtDiachiLD.value = ds[0]['diachi_ld']

                    // Tuan them code lay gia tri this.ma_tb cho khach hang CQ -- ngay 22/07/2010
                    this.ma_tb_cu = ds[0]['ma_tb']
                    this.$refs.txtMaTBCu.value = ds[0]['ma_tb']
                    this.$refs.txtTenTBCu.value = ds[0]['ten_tb']
                    this.$refs.txtDiaChiTB.value = ds[0]['diachi_tb']
                    rs = await this.axios.post('web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid', {
                      in_thuebao_id: this.thuebao_id
                    })
                    this.HienThiTTDiaChi(rs.data.data, 3)

                    //TuấnNA thêm code hiển thị thông tin email thuê bao -- theo y/c của A Thái --- Ngày 16/11/2010

                    this.loaitb_id = Number(ds[0]['loaitb_id'])
                    this.$refs.cboDoituongTB.value = ds[0]['doituong_id']
                    this.doituong_id = Number(ds[0]['doituong_id'])
                    let loaihinhtemp = await bangts.TTCT_LoaiHinh_TB(this.loaitb_id)
                    this.dichvuvt_id = Number(loaihinhtemp && loaihinhtemp.length > 0 ? loaihinhtemp['dichvuvt_id'] : 0)
                    this.$refs.cboDichVuVT.value = this.dichvuvt_id
                    this.$refs.cboDaiVT.value = Number(ds[0]['donviql_id']) ///Đơn vị trong bàng db_thuebao

                    //TuấnNA thêm code hiển thị ngày SD -- ngày 14/12/2010
                    if (ds[0]['ngay_sd']) this.$refs.dtpNgaySD.value = ds[0]['ngay_sd']
                    //endregion

                    if (ds[0]['mucuoctb_id']) {
                      mucuoctb_id = Number(ds[0]['mucuoctb_id'])
                      await bangts.HT_MucCuocTB_Combobox(this.$refs.cboMuccuocTBCu, Number(ds[0]['mucuoctb_id']))
                      await bangts.HT_MucCuocTB_Combobox(this.$refs.cboMucCuocTB, Number(ds[0]['mucuoctb_id']))
                    }

                    this.HienThiTTDBMoRong(this.thuebao_id, this.dichvuvt_id)

                    this.LAY_DS_SUDUNG_DVGT(this.thuebao_id)

                    this.LAY_DS_SUDUNG_DVK(this.thuebao_id, 1)

                    //frmTraCuuCTDanhBa frmTCCTDB = new frmTraCuuCTDanhBa(this.dichvuvt_id, this.$refs.txtMaTB.value);
                    //frmTCCTDB.ShowDialog();

                    //SendKeys.Send("{TAB}");
                    this.$refs.txtTenTB.focus()
                  }
                }
                // chưa có khách hàng đang chuyển quyền
                else {
                  //Thông tin khách hàng
                  this.$refs.txtMaHDCu.value = ds[0]['ma_hd']
                  this.$refs.txtMaKHCu.value = ds[0]['ma_kh']
                  this.ma_kh_cu = ds[0]['ma_kh'] ///Tuấn thêm code mã khách hàng cũ -- Ngày 06/08/2010
                  if (ds[0]['ngaylap_hd']) this.$refs.dtpNgayLapHDCQ.value = ds[0]['ngaylap_hd']
                  else this.$refs.dtpNgayLapHDCQ.value = this.tt_nd.ngay_cn

                  this.khachhang_id_cq = Number(ds[0]['khachhang_id'])
                  console.log('set khachhang_id_cq', ds[0]['khachhang_id'])
                  this.$refs.txtTenKHCu.value = ds[0]['ten_kh']

                  this.ten_tb_cu = ds[0]['ten_tb']
                  this.$refs.txtDiaChiKHCu.value = ds[0]['diachi_kh']
                  rs = await this.axios.get('web-hopdong/hopdong/lay_ds_diachi_theo_khachhang?khachHangId=' + this.khachhang_id_cq)
                  this.HienThiTTDiaChi(rs.data.data, 1)
                  this.ma_tt_cu = ds[0]['ma_tt']
                  this.ten_tt_cu = ds[0]['ten_tt']
                  this.diachi_tt_cu = ds[0]['diachi_tt']

                  //Thông tin thuê bao
                  this.thuebao_id = Number(ds[0]['thuebao_id'])
                  console.log(ds[0]['loaitb_id'], 'Sao bạn không vào đây?')
                  this.$refs.cboLoaihinhTB.value = Number(ds[0]['loaitb_id']) //Tuan them code hien thi this.loaitb_id -- Ngay 23/07/2010
                  await this.cboLoaihinhTB_SelectedValueChanged()
                  //Tuan them code hien thi diachi lapdat -- Ngay 29/07/2010
                  this.$refs.txtDiachiLD.value = ds[0]['diachi_ld']
                  //Tuan them code hien thi goi cuoc DD -- Ngay 23/07/2010
                  if (this.dichvuvt_id == DichVuVienThong.DI_DONG && this.CURRENT_LOAITB_ID() == LoaiHinhTB.DIDONGTRASAU) {
                    if (ds[0]['goicuoc_id']) {
                      this.$refs.cboGoiCuocDD.value = Number(ds[0]['goicuoc_id'])
                    }
                  }
                  ///TuấnNA sửa code hiển thị combobox đài VT Full --- Ngày 19/10/2010
                  ///Trong trường hợp thuê bao không thuộc đơn vị dữ liệu của user, chương trình tự động chọn this.$refs.cboDaiVT --- > donvi_id(db_thuebao)
                  ///Giao dịch viên không được chọn.

                  //await bangts.HT_DaiVT_Combobox(this.$refs.cboDaiVT, 0);
                  //this.$refs.txtMaTB.value = ds[0]["ma_tb"]
                  // Tuan them code lay gia tri this.ma_tb cho khach hang CQ -- ngay 22/07/2010
                  this.ma_tb_cu = ds[0]['ma_tb']
                  this.$refs.txtMaTBCu.value = ds[0]['ma_tb']
                  this.$refs.txtTenTBCu.value = ds[0]['ten_tb']
                  this.$refs.txtDiaChiTB.value = ds[0]['diachi_tb']
                  rs = await this.axios.post('web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid', {
                    in_thuebao_id: this.thuebao_id
                  })
                  this.HienThiTTDiaChi(rs.data.data, 3)
                  //TuấnNA thêm code hiển thị thông tin email thuê bao -- theo y/c của A Thái --- Ngày 16/11/2010

                  this.loaitb_id = Number(ds[0]['loaitb_id'])
                  this.$refs.cboDoituongTB.value = ds[0]['doituong_id']
                  this.doituong_id = Number(ds[0]['doituong_id'])
                  rs = await this.axios.post('/web-quantri/lay-dulieu/sp_tt_loaihinh_tb', {
                    param: this.loaitb_id,
                    type: 2
                  })
                  if (rs.data.data && rs.data.data.length > 0) this.dichvuvt_id = rs.data.data[0]['dichvuvt_id']
                  this.$refs.cboDichVuVT.value = this.dichvuvt_id
                  this.$refs.cboDaiVT.value = Number(ds[0]['donviql_id'])
                  this.$refs.txtMaTB.value = ds[0]['ma_tb']
                  this.$refs.txtTenTB.value = ds[0]['ten_tb']
                  //không vào
                  console.log(ds[0]['ngay_sn'], 'set ds[0]["ngay_sn"] 2')
                  this.$refs.txtNgaySinhTB.value = this.formatDate(ds[0]['ngay_sn'])
                  //TuấnNA thêm code hiển thị ngày SD -- ngày 14/12/2010
                  if (ds[0]['ngay_sd']) this.$refs.dtpNgaySD.value = ds[0]['ngay_sd']
                  this.HienThiTTDBMoRong(this.thuebao_id, this.dichvuvt_id)

                  if (ds[0]['mucuoctb_id']) {
                    this.mucuoctb_id = Number(ds[0]['mucuoctb_id'])
                    rs = await this.axios.get('web-hopdong/hopdong/lay_muccuoc_thuebao?mucCuocTbId=' + this.mucuoctb_id)
                    console.log(rs.data.data)
                    this.$refs.cboMuccuocTBCu.HT_COMBOBOX([rs.data.data], 'tenMucCuoc', 'mucCuocTbId')
                    this.$refs.cboMucCuocTB.HT_COMBOBOX([rs.data.data], 'tenMucCuoc', 'mucCuocTbId')
                    this.$refs.cboMucCuocTB.value = Number(rs.data.data.mucCuocTbId)
                  }

                  this.LAY_DS_SUDUNG_DVGT(this.thuebao_id)
                  this.LAY_DS_SUDUNG_DVK(this.thuebao_id, 1)

                  //frmTraCuuCTDanhBa frmTCCTDB = new frmTraCuuCTDanhBa(this.dichvuvt_id, this.$refs.txtMaTB.value);
                  //frmTCCTDB.ShowDialog();

                  //SendKeys.Send("{TAB}");
                  this.$refs.txtTenTB.focus()
                }
              }
            }
          } else {
            this.$toast.success('Không tìm thấy thông tin về thuê bao: ' + this.$refs.txtMaTB.value + '\nĐề nghị kiểm tra lại Số máy/Acc, Dịch vụ VT !')
          }
        } catch (err) {
          console.log(err)
        }
        resolve(true)
      })
    },
    async chonTBCungma(ds) {
      
    },
    //endregion

    //region HienThiTT_DanhBa_KH_NhanCQ
    async HienThiTT_DanhBa_KH_NhanCQ(ma_tb) {
      return new Promise(async (resolve, reject) => {
        this.httt_id_cu = -1
        try {
          this.clear_manvtc = '0' ///Kiểm tra để xử lý clear mã nhân viên thu cước--- Ngày 04/10/2010
          //let ds = await tcdanhba.LAY_DANHBA_THEO_MATB(this.ma_tb,this.dichvuvt_id);
          rs = await this.axios.post('web-hopdong/lapdatmoi/lay_db_theo_matb', {
            dichvuvt_id: 0,
            donvi_dl_id: 0,
            ma_tb: ma_tb
          })
          let ds = rs.data.data
          if (ds.length > 0) {
            //Tuan bo sung code kiem tra khach hang da ky hop dng goc chua -- Ngay 07/07/2010
            if (Number(ds[0]['ky_hd']) == 0) {
              this.$toast.error('Khách hàng có mã: ' + this.$refs.txtMaKH.value + ' chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc')
            } else {
              //Tuan them bien this.TBHD_PL = 0 --> khach hang nhan quyen la khach hang cu -- Ngay 07/07/2010
              this.TBHD_PL = 0 //In Phu Luc
              //Thông tin khách hàng
              if (ds[0]['ma_kh'] == this.ma_kh_cu) {
                ///Tuấn Sửa + Comment đoạn code sử dụng chức năng chuyển quyền để tách nhập thuê bao. --- Ngày 19/10/2010
                this.$toast.success('Khách hàng nhận quyền và khách hàng chuyển quyền phải khác nhau !')
                this.$refs.txtMaKH.value = ''
                resolve(true)
                return
              } else {
                this.$refs.txtMaKH.value = ds[0]['ma_kh']
                this.$refs.txtMaHD.value = ds[0]['ma_hd']
                if (ds[0]['ngaylap_hd']) this.$refs.dtpNgayLapHDNQ.value = ds[0]['ngaylap_hd']
                else this.$refs.dtpNgayLapHDNQ.value = this.tt_nd.ngay_cn

                //tuan 02/07/2010 them du lieu hien thi
                this.$refs.cboQuocTich.value = ds[0]['quoctich_id']
                this.$refs.cboDanToc.value = ds[0]['dantoc_id']

                // this.$refs.cboNganhNgheLoc.LockEvents();
                this.$refs.cboNganhNgheLoc.value = ds[0]['nganhnghe_id']
                this.nganhnghe_id = Number(ds[0]['nganhnghe_id'])
                // this.$refs.cboNganhNgheLoc.UnLockEvents();

                this.$refs.cboGiayToKH.value = ds[0]['loaigt_id']
                // end tuan
                this.khachhang_id = Number(ds[0]['khachhang_id'])
                this.$refs.txtTenKH.value = ds[0]['ten_kh']
                this.$refs.txtDiaChiKH.value = ds[0]['diachi_kh']
                if (ds[0]['loaikh_id']) this.$refs.cboLoaiKH.value = Number(ds[0]['loaikh_id'])

                try {
                  strPLKH = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_khachhang', {
                    param: this.khachhang_id,
                    type: 2
                  })
                  strPLKH = strPLKH.data.data

                  if (strPLKH != '-1') {
                    PHANLOAIKH_ID = Number(strPLKH)
                  }
                } catch (err) {}

                if (this.phantap_kh) {
                  let ds_dt_dtn = []
                  rs = await this.axios.get('web-hopdong/hopdong/lay_doanhthu_dtn', {
                    params: {
                      khachHangId: this.khachhang_id
                    }
                  })
                  ds_dt_dtn = rs.data.data
                  if (ds_dt_dtn.length > 0) {
                    if (ds_dt_dtn[0]['doanhthu']) this.pt_doanhthu = parseFloat(ds_dt_dtn[0]['doanhthu'])
                    else this.pt_doanhthu = 0

                    if (ds_dt_dtn[0]['dtn']) this.pt_diem_tn = Number(ds_dt_dtn[0]['dtn'])
                    else this.pt_diem_tn = 0
                  } else {
                    this.pt_doanhthu = 0
                    this.pt_diem_tn = 0
                  }
                }

                if (ds[0]['diachi_ndd']) this.$refs.txtDiaChiNDD.value = ds[0]['diachi_ndd']
                else this.$refs.txtDiaChiNDD.value = this.$refs.txtDiaChiKH.value
                //this.$refs.txtNguoiDD.value = (ds[0]["nguoi_dd"]);

                //Number(this.$refs.cboLoaiKH.value) == Loai_KH.CANHAN_HOGIADINH
                if (this.kieukh_canhan && ds[0]['nguoi_dd'] == '') this.$refs.txtNguoiDD.value = this.$refs.txtTenKH.value
                else this.$refs.txtNguoiDD.value = ds[0]['nguoi_dd']

                this.$refs.txtChucDanhDD.value = ds[0]['chucdanh']
                this.$refs.txtNgayHetHan.value = this.formatDate(ds[0]['ngayhethan_gt'])
                rs = await this.axios.post('web-danhba/dungluong_thitruong/lay_ds_diachi_theo_dbkhid', {
                  in_khachhang_id: this.khachhang_id
                })
                this.HienThiTTDiaChi(rs.data.data, 1)
                this.$refs.txtTelKH.value = ds[0]['so_dt']

                //rang buoc chuyen quyen
                this.$refs.tbDienThoaiLienHe.value = this.$refs.txtTelKH.value

                this.$refs.txtFaxKH.value = ds[0]['so_fax']

                this.$refs.txtHoKhau.value = ds[0]['hokhau']

                //txtDactrung.value = (ds[0]["dactrung"]);
                this.$refs.txtDacTrung.value = ds[0]['dactrung']
                this.$refs.txtSoTKKH.value = ds[0]['stk']
                this.$refs.txtMaSTKH.value = ds[0]['mst']
                //Rang buoc chuyen quyen
                this.$refs.txtSTK_TT.value = this.$refs.txtMaSTKH.value
                if (ds[0]['nganhang_id']) {
                  this.$refs.chkNganHangKH.checked = true
                  this.$refs.cboNganHangKH.value = ds[0]['nganhang_id']
                } else this.$refs.chkNganHangKH.checked = false
                //tuan them thong tin hien thi khach hang nhan quyen --Ngay 05/07/2010
                if (ds[0]['gioitinh'] == '1') {
                  this.$refs.rdoGTNu.checked = true
                } else if (ds[0]['gioitinh'] == '0') this.$refs.rdoGTNam.checked = true
                else if (ds[0]['gioitinh'] == null) this.$refs.rdoGTNone.checked = true
                this.$refs.txtGhiChuKH.value = ds[0]['ghichu']

                //end tuan
                if (ds[0]['ngay_hk']) this.$refs.txtNgayCapHK.value = this.formatDate(ds[0]['ngay_hk'])
                else this.$refs.txtNgayCapHK.value = ''

                this.$refs.txtNoiCapHK.value = ds[0]['noicap_hk']
                if (ds[0]['khlon_id']) this.$refs.cboKhachHangLon.value = Number(ds[0]['khlon_id'])

                if (ds[0]['ngay_sn']) {
                  this.$refs.txtNgaySN.value = ds[0]['ngay_sn']
                  this.$refs.txtNgaySN.focus()
                } else this.$refs.txtNgaySN.value = ''

                this.$refs.txtSoGT.value = ds[0]['so_gt']
                if (ds[0]['ngaycap']) this.$refs.txtNgayCap.value = this.formatDate(ds[0]['ngaycap'])
                else this.$refs.txtNgayCap.value = ''

                if (ds[0]['loaigt_id']) this.$refs.cboGiayToKH.value = ds[0]['loaigt_id']
                this.$refs.txtNoiCap.value = ds[0]['noicap']

                //TuấnNA thêm code hiển thị thông tin id tiểu ngành c1,c2,c3 -- Ngày 03/12/2010
                if (ds[0]['tnc1_id']) this.TNC1_ID = Number(ds[0]['tnc1_id'])
                if (ds[0]['tnc2_id']) this.TNC2_ID = Number(ds[0]['tnc2_id'])
                if (ds[0]['tnc3_id']) this.TNC3_ID = Number(ds[0]['tnc3_id'])
                if (ds[0]['tnc4_id']) this.TNC4_ID = Number(ds[0]['tnc4_id'])
                //
                //Thông tin thanh toán
                this.$refs.txtMaTT.value = ds[0]['ma_tt']
                this.thanhtoan_id = Number(ds[0]['thanhtoan_id'])

                rs = await this.axios.post('web-hopdong/hopdong/ht_combo_ma_daidien', {
                  thanhtoan_id: this.thanhtoan_id
                })
                let dt_madd = rs.data.data
                this.$refs.cboMaDD.dataSource = dt_madd
                //ten_ht

                this.$refs.cboMaDD.value = ds[0]['matb_dd']

                this.$refs.txtTenTT.value = ds[0]['ten_tt']
                /// TuanNA thêm code hiển thị thông tin  -- Ngày 06/08/2010

                this.$refs.txtMaTBDD.value = ds[0]['matb_dd']
                this.$refs.txtDiaChiTT.value = ds[0]['diachi_tt']
                this.$refs.txtDiaChiCT.value = ds[0]['diachi_ct']
                this.$refs.txtDiaChiBC.value = ds[0]['diachi_bc']
                rs = await this.axios.get('web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid', {
                  params: {
                    dbttid: this.thanhtoan_id
                  }
                })
                this.HienThiTTDiaChi(rs.data.data, 2)

                //TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT --- Ngày 12/11/2010
                this.DV_QHNS = ds[0]['DV_QHNS']
                this.MACHUONG = ds[0]['MACHUONG']
                this.MN_KT = ds[0]['MN_KT']
                this.MA_NDKT = ds[0]['MA_NDKT']
                this.MN_NSNN = ds[0]['MN_NSNN']
                if (ds[0]['TIEN_DM']) this.TIEN_DM = Number(ds[0]['TIEN_DM'])
                else this.TIEN_DM = 0
                //

                /////TuấnNA thêm code lấy đơn vị quản lý cũ ứng với mã thanh toán khách hàng đang sử dụng
                /////--- Ngày 01/09/2010
                //this.donvi_qlcu = LayDonVi_QL(this.$refs.txtMaTT.value, this.phuongtt_id, this.phott_id);
                //this.donvi_qlmoi = this.donvi_qlcu;
                ///
                this.$refs.txtMST_TT.value = ds[0]['mst_tt']
                this.$refs.txtSTK_TT.value = ds[0]['stk_tt']
                this.$refs.txtChu_TK.value = ds[0]['chu_tk_tt']
                this.$refs.txtGhiChuTT.value = ds[0]['ghichu_tt']

                this.$refs.txtMaIG.value = ds[0]['main_ghep']
                if (ds[0]['mig_id']) {
                  this.mig_id = Number(ds[0]['mig_id'])
                }

                if (ds[0]['nganhang_id_tt']) this.$refs.cboNganHangTT.value = ds[0]['nganhang_id_tt']

                this.$refs.cboHTTT.value = ds[0]['httt_id']
                this.httt_id_cu = Number(ds[0]['httt_id'])

                /// TuanNA thêm code thay thế this.donvi_ql -->this.$refs.cboDonviQL  -- Ngày 29/07/2010
                if (ds[0]['donvi_id'])
                  //this.donvi_ql = Number(ds[0]["donvi_id"]);
                  this.$refs.cboDonviQL.value = ds[0]['donvi_id']

                if (ds[0]['nhanvien_id']) {
                  this.nhanvientc_id = Number(ds[0]['nhanvien_id'])
                  rs = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', {
                    param: this.nhanvientc_id,
                    type: 4
                  })
                  this.$refs.txtMaNVTC.value = rs.data.data
                } else {
                  this.nhanvientc_id = 0
                  this.$refs.txtMaNVTC.value = ''
                }

                if (ds[0]['tuyenthu_id']) {
                  this.tuyenthu_id = Number(ds[0]['tuyenthu_id'])
                  rs = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tuyenthu', {
                    param: this.tuyenthu_id,
                    type: 1
                  })
                  this.$refs.txtMaTuyen.value = rs.data.data
                } else {
                  this.tuyenthu_id = 0
                  this.$refs.txtMaTuyen.value = ''
                }
                this.matb_dd = ds[0]['matb_dd']

                this.$refs.txtTenTB.value = ds[0]['ten_tb']
                this.$refs.txtNgaySinhTB.value = this.formatDate(ds[0]['ngay_sn'])
                this.$refs.txtMaKH.focus()

                this.LAY_DS_SUDUNG_DVK(this.thanhtoan_id, 2)
                this.HienThi_NVQL_AM_DB_HD(1, this.thanhtoan_id)
              }
            }
          } else {
            //Tuan them bien this.TBHD_PL = 1 --> khach hang nhan quyen la khach hang Moi -- Ngay 07/07/2010
            this.TBHD_PL = 1 // In HD
            this.$toast.success('Không tìm thấy thông tin về khách hàng có mã: ' + this.$refs.txtMaKH.value + '\nĐề nghị kiểm tra lại !')
            this.SetButton(0)
          }
        } catch (err) {
          console.log(err)
          console.log(err)
          this.$toast.error('Có lỗi:' + err.message)
        }
        resolve(true)
      })
    },
    //endregion

    //region Dia chi HD khach hang

    btnDiaChiKH_Click() {
      this.dataDiaChi.tinh_id = this.tinhkh_id
      this.dataDiaChi.quan_id = this.quankh_id
      this.dataDiaChi.phuong_id = this.phuongkh_id
      this.dataDiaChi.pho_id = this.phokh_id
      this.dataDiaChi.ap_id = this.apkh_id
      this.dataDiaChi.khu_id = this.khukh_id
      this.dataDiaChi.dacdiem_id = this.dacdiemkh_id
      this.dataDiaChi.sonha = this.sonhakh
      this.dataDiaChi.block = ''
      this.dataDiaChi.tang = ''
      this.dataDiaChi.sophong = ''
      this.dataDiaChi.motathem = ''
      this.dataDiaChi.diachi = this.$refs.txtDiaChiKH.value
      this.dataDiaChi.value = 'Địa chỉ khách hàng'
      this.dataDiaChi.type = 'btnDiaChiKH'
      this.$bvModal.show('diaChiModal')
    },
    //tuan code
    btnDiaChiCT_Click() {
      this.dataDiaChi.value = 'Địa chỉ chứng từ'
      this.dataDiaChi.diachi = this.$refs.txtDiaChiCT.value
      this.dataDiaChi.tinh_id = this.tinhct_id
      this.dataDiaChi.quan_id = this.quanct_id
      this.dataDiaChi.phuong_id = this.phuongct_id
      this.dataDiaChi.pho_id = this.phoct_id
      this.dataDiaChi.ap_id = this.apct_id
      this.dataDiaChi.khu_id = this.khuct_id
      this.dataDiaChi.dacdiem_id = this.dacdiemct_id
      this.dataDiaChi.sonha = this.sonhact
      this.dataDiaChi.type = 'btnDiaChiCT'
      this.$bvModal.show('diaChiModal')
    },

    btnDiaChiNDD_Click() {
      this.dataDiaChi.value = 'Địa chỉ người đại diện'
      ///TuanNA comment địa chỉ người đại diện để lấy giá trị được gợi nhớ bên địa chỉ khách hàng -- Ngày 30/07/2010
      //this.quanndd_id = this.quankh_id;
      //this.phuongndd_id = this.phuongkh_id;
      //this.phondd_id = this.phokh_id;
      //ngondd_id = this.apkh_id;
      //this.sonhandd = this.sonhakh;
      //obj.quan_id = this.quanndd_id;
      //obj.phuong_id = this.phuongndd_id;
      //obj.pho_id = this.phondd_id;
      //obj.ap_id = ngondd_id;
      //obj.sonha = this.sonhandd;

      if (!this.tinhdd_id) this.dataDiaChi.tinh_id = this.tinhkh_id
      else this.dataDiaChi.tinh_id = this.tinhndd_id

      if (!this.quanndd_id) this.dataDiaChi.quan_id = this.quankh_id
      else this.dataDiaChi.quan_id = this.quanndd_id

      if (!this.phuongndd_id) this.dataDiaChi.phuong_id = this.phuongkh_id
      else this.dataDiaChi.phuong_id = this.phuongndd_id

      if (!this.phondd_id) this.dataDiaChi.pho_id = this.phokh_id
      else this.dataDiaChi.pho_id = this.phondd_id

      if (!this.apndd_id) this.dataDiaChi.ap_id = this.apkh_id
      else this.dataDiaChi.ap_id = this.apndd_id

      if (!this.khundd_id) this.dataDiaChi.khu_id = this.khukh_id
      else this.dataDiaChi.khu_id = this.khundd_id

      if (!this.dacdiemndd_id) this.dataDiaChi.khu_id = this.dacdiemkh_id
      else this.dataDiaChi.khu_id = this.dacdiemndd_id

      if (!this.sonhandd) this.dataDiaChi.sonha = this.sonhakh
      else this.dataDiaChi.sonha = this.sonhandd

      this.dataDiaChi.diachi = this.$refs.txtDiaChiNDD.value

      this.dataDiaChi.type = 'btnDiaChiNDD'
      console.log(this.dataDiaChi)

      this.$bvModal.show('diaChiModal')
    },
    //end tuan code
    //endregion

    //region Dia chi HD thanh toan

    btnDiaChiTT_Click() {
      try {
        this.dataDiaChi.value = 'Địa chỉ thanh toán'
        this.dataDiaChi.tinh_id = this.tinhtt_id
        this.dataDiaChi.quan_id = this.quantt_id
        this.dataDiaChi.phuong_id = this.phuongtt_id
        this.dataDiaChi.pho_id = this.phott_id
        this.dataDiaChi.ap_id = this.aptt_id
        this.dataDiaChi.khu_id = this.khutt_id
        this.dataDiaChi.dacdiem_id = this.dacdiemtt_id
        this.dataDiaChi.sonha = this.sonhatt
        this.dataDiaChi.type = 'btnDiaChiTT'
        this.dataDiaChi.diachi = this.$refs.txtDiaChiTT.value ? this.$refs.txtDiaChiTT.value : ''
        console.log(this.dataDiaChi)
        this.$bvModal.show('diaChiModal')
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi: ' + err.message)
      }
    },
    //endregion

    HienThi_NVQL_AM_PTKH: async function(phuong_id, pho_id, ap_id, khu_id, dacdiem_id, loaikh_id, nganhnghe_id, doanhthu, diem_tn, loainv_id, donvi_ql_id) {
      try {
        if (!this.kt_load) {
          let ds_nvql = []
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_am_ptkh', {
            phuong_id: phuong_id,
            pho_id: pho_id,
            ap_id: ap_id,
            khu_id: khu_id,
            dacdiem_id: dacdiem_id,
            loaikh_id: loaikh_id,
            nganhnghe_id: nganhnghe_id,
            doanhthu: doanhthu,
            diemtn: diem_tn,
            loainv_id: loainv_id,
            donvi_ql_id: donvi_ql_id
          })
          ds_nvql = rs.data
          if (ds_nvql.length == 0) {
            this.$refs.txtNhanVienQL.value = ''
            this.nhanvien_ql_am_id = 0
          } else {
            this.$refs.txtNhanVienQL.value = ds_nvql[0].ma_nv + ' - ' + ds_nvql[0].ten_nv
            this.nhanvien_ql_am_id = ds[0].nhanvien_id
          }
        }
      } catch (error) {
        console.log(error)
      }
    },

    /// <summary>
    /// TuanNA: thêm hàm lấy mã nhân viên tc theo địa chỉ thanh toán vừa chọn
    /// Ngày 08.05.2012
    /// </summary>
    async HT_NVTC_TheoDC(quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id) {
      return new Promise(async (resolve, reject) => {
        console.log('HT_NVTC_TheoDC')
        try {
          let ds = []
          if (this.TUYENTHU_HTTT == 1 && this.$refs.cboHTTT.value) {
            let rs = await this.axios.post('/web-hopdong/thaydoithongtinkhuyenmai/ht_nvtc_theodc_v2', {
              phuong_id: phuong_id,
              pho_id: pho_id,
              ap_id: ap_id,
              khu_id: khu_id,
              donvi_ql_id: donvi_ql_id,
              dacdiem_id: dacdiem_id,
              httt_id: this.$refs.cboHTTT.value
            })
            ds = rs.data.data
          } else {
            let rs = await this.axios.post('/web-hopdong/thaydoithongtinkhuyenmai/ht_nvtc_theodc', {
              phuong_id: phuong_id,
              pho_id: pho_id,
              ap_id: ap_id,
              khu_id: khu_id,
              donvi_ql_id: donvi_ql_id,
              dacdiem_id: dacdiem_id
            })
            ds = rs.data.data
          }
          if (ds.length > 1) {
            this.loaiPopupTraCuuNVTC = 1
            this.objNVTC = {}
            this.objNVTC.QUAN_ID = quan_id
            this.objNVTC.PHUONG_ID = phuong_id
            this.objNVTC.PHO_ID = pho_id
            this.objNVTC.KHU_ID = khu_id
            this.objNVTC.AP_ID = ap_id
            this.objNVTC.DACDIEM_ID = dacdiem_id
            this.objNVTC.NHANVIENTC_ID = this.nhanvientc_id
            this.objNVTC.TUYENTHU_ID = ds[0].tuyenthu_id
            this.objNVTC.DONVIQL_ID = donvi_ql_id

            if (this.TUYENTHU_HTTT == 1 && this.$refs.cboHTTT.value) {
              this.objNVTC.HTTT_ID = this.$refs.cboHTTT.value
            }
            this.diachiLD = this.objNVTC
            this.$bvModal.show('dlgTimKiemNVTC')
          } else if (ds.length > 0) {
            this.$refs.txtMaNVTC.value = ds[0].manv_tc
            if (ds[0].tuyenthu_id) {
              this.tuyenthu_id = ds[0].tuyenthu_id
            }
            this.$refs.txtMaTuyen.value = ds[0].ma_tuyen
            this.nhanvientc_id = ds[0].nhanvien_id
          } else {
            this.$refs.txtMaNVTC.value = ''
            this.$refs.txtMaTuyen.value = ''
            this.tuyenthu_id = 0
            this.nhanvientc_id = 0
          }
        } catch (error) {
          console.log(error)
        }
        resolve(true)
      })
    },
    async onAcceptDiaChi(obj) {
      console.log(obj)
      console.log(this.dataDiaChi, 'Kiểu địa chỉ')
      if (this.dataDiaChi.type == 'btnDiaChiTB') {
        this.tinhtb_id = Number(obj.tinh_id)
        this.quantb_id = Number(obj.quan_id)
        this.phuongtb_id = Number(obj.phuong_id)
        this.photb_id = Number(obj.pho_id)
        this.aptb_id = Number(obj.ap_id)
        this.khutb_id = Number(obj.khu_id)
        this.dacdiemtb_id = Number(obj.dacdiem_id)
        this.sonhatb = obj.sonha
        this.blocktb = obj.block
        this.tangtb = obj.tang
        this.sophongtb = obj.sophong
        this.motadctb = obj.motathem
        this.$refs.txtDiaChiTB.value = obj.diachimoi
        this.$refs.txtDiaChiTB.focus()
      }
      if (this.dataDiaChi.type == 'btnDiaChiLD') {
        this.tinhld_id = Number(obj.tinh_id)
        this.quanld_id = Number(obj.quan_id)
        this.phuongld_id = Number(obj.phuong_id)
        this.phold_id = Number(obj.pho_id)
        this.apld_id = Number(obj.ap_id)
        this.khuld_id = Number(obj.khu_id)
        this.dacdiemld_id = Number(obj.dacdiem_id)
        this.sonhald = obj.sonha
        this.blockld = obj.block
        this.tangld = obj.tang
        this.sophongld = obj.sophong
        this.motadcld = obj.motathem
        this.$refs.txtDiaChiLD.value = obj.diachimoi
        this.$refs.txtDiaChiLD.focus()
      }
      if (this.dataDiaChi.type == 'btnDiaChiKH') {
        this.tinhkh_id = Number(obj.tinh_id)
        this.quankh_id = Number(obj.quan_id)
        this.phuongkh_id = Number(obj.phuong_id)
        this.phokh_id = Number(obj.pho_id)
        this.apkh_id = Number(obj.ap_id)
        this.khukh_id = Number(obj.khu_id)
        this.dacdiemkh_id = Number(obj.dacdiem_id)
        this.sonhakh = obj.sonha

        this.$refs.txtDiaChiKH.value = obj.diachimoi
        this.$refs.txtDiaChiKH.focus()
        if (!this.hdkh_id) {
          if (!this.$refs.txtDiaChiTT.value) {
            //Hiếu tc: 21.1.2014: sửa lại hàm lấy đơn vị quản lý.
            let ds = []
            //ds = laphd.LAY_DS_DONVIQL(obj.phuong_id, obj.pho_id, obj.ap_id, obj.khu_id, 1);
            if (this.DONVIQL_HTTT == 1) {
              rs = await this.axios.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_v2', {
                phuong_id: obj.phuong_id,
                pho_id: obj.pho_id,
                ap_id: obj.ap_id,
                khu_id: obj.khu_id,
                kieu: 1,
                loaikh_id: this.$refs.cboLoaiKH.value,
                httt_id: this.$refs.cboHTTT.value
              })

              ds = rs.data.data
            } else {
              if (this.phantap_kh) {
                rs = await this.axios.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_ptkh_v2', {
                  phuong_id: obj.phuong_id,
                  pho_id: obj.pho_id,
                  ap_id: obj.ap_id,
                  khu_id: obj.khu_id,
                  kieu: 1,
                  loaikh_id: this.$refs.cboLoaiKH.value,
                  httt_id: 104,
                  dacdiem_id: obj.dacdiem_id,
                  nganhnghe_id: this.$refs.cboNganhNgheLoc.value,
                  doanhthu: this.pt_doanhthu,
                  diemtn: this.pt_diem_tn
                })
                ds = rs.data.data
              } else {
                rs = await this.axios.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql', {
                  phuong_id: obj.phuong_id,
                  pho_id: obj.pho_id,
                  ap_id: obj.ap_id,
                  khu_id: obj.khu_id,
                  kieu: 1,
                  loaikh_id: this.$refs.cboLoaiKH.value
                })

                ds = rs.data.data
              }
            }
            if (!ds || ds.length <= 0) {
              if (this.$refs.cboDonviQL.dataSource.length != 1) {
                this.$toast.error('Địa danh này chưa được gán đơn vị quản lý. Hãy liên hệ với Admin để xử lý!')
                this.$refs.cboDonviQL.value = null
                return
              }
            } else if (ds.length == 1) {
              this.$refs.cboDonviQL.value = ds[0]['donvi_id']
            } else {
              let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.$refs.cboDonviQL.value, null, ds)
              if (resolvePromise.ok) {
                this.$refs.cboDonviQL.value = resolvePromise.data.donviql_id
              } else return
            }

            this.clear_manvtc = '1'
            this.tinhtt_id = this.HienThiDanhSacHDTTtinhkh_id
            this.quantt_id = this.quankh_id
            this.phuongtt_id = this.phuongkh_id
            this.phott_id = this.phokh_id
            this.aptt_id = this.apkh_id
            this.khutt_id = this.khukh_id
            this.dacdiemtt_id = this.dacdiemkh_id
            this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.$refs.cboDonviQL.value, this.dacdiemtt_id)

            if (DONVIQL_HTTT == 1) this.HienThi_NVQL_AM_HTTT(this.$refs.cboLoaiKH.value, Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.$refs.cboDonviQL.value, this.$refs.cboHTTT.value)
            else {
              if (this.phantap_kh) {
                this.HienThi_NVQL_AM_PTKH(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.dacdiemtt_id, this.$refs.cboLoaiKH.value, this.$refs.cboNganhNgheLoc.value, this.pt_doanhthu, this.pt_diem_tn, Loai_NV.NHANVIEN_AM, this.$refs.cboDonviQL.value)
              } else this.HienThi_NVQL_AM(this.$refs.cboLoaiKH.value, Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.$refs.cboDonviQL.value)
            }
            this.sonhatt = this.sonhakh
            this.$refs.txtDiaChiTT.value = this.$refs.txtDiaChiKH.value
          }

          if (!this.$refs.txtDiaChiCT.value) {
            this.tinhct_id = this.tinhkh_id
            this.quanct_id = this.quankh_id
            this.phuongct_id = this.phuongkh_id
            this.phoct_id = this.phokh_id
            this.apct_id = this.apkh_id
            this.khuct_id = this.khukh_id
            this.dacdiemct_id = this.dacdiemkh_id
            this.sonhact = this.sonhakh
            this.$refs.txtDiaChiCT.value = this.$refs.txtDiaChiKH.value
          }

          if (!this.$refs.txtDiaChiTB.value) {
            this.tinhtb_id = this.tinhkh_id
            this.quantb_id = this.quankh_id
            this.phuongtb_id = this.phuongkh_id
            this.photb_id = this.phokh_id
            this.aptb_id = this.apkh_id
            this.khutb_id = this.khukh_id
            this.dacdiemtb_id = this.dacdiemkh_id
            this.sonhatb = this.sonhakh
            this.$refs.txtDiaChiTB.value = this.$refs.txtDiaChiKH.value
          }
        }
      }
      if (this.dataDiaChi.type == 'btnDiaChiTT') {
        let ds = []
        //ds = laphd.LAY_DS_DONVIQL(obj.phuong_id, obj.pho_id, obj.ap_id, obj.khu_id, 1);
        if (this.DONVIQL_HTTT == 1) {
          rs = await this.axios.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_v2', {
            phuong_id: obj.phuong_id,
            pho_id: obj.pho_id,
            ap_id: obj.ap_id,
            khu_id: obj.khu_id,
            kieu: 1,
            loaikh_id: this.$refs.cboLoaiKH.value,
            httt_id: this.$refs.cboHTTT.value
          })

          ds = rs.data.data
        } else {
          if (this.phantap_kh) {
            rs = await this.axios.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_ptkh_v2', {
              phuong_id: obj.phuong_id,
              pho_id: obj.pho_id,
              ap_id: obj.ap_id,
              khu_id: obj.khu_id,
              kieu: 1,
              loaikh_id: this.$refs.cboLoaiKH.value,
              httt_id: 104,
              dacdiem_id: obj.dacdiem_id,
              nganhnghe_id: this.$refs.cboNganhNgheLoc.value,
              doanhthu: this.pt_doanhthu,
              diemtn: this.pt_diem_tn
            })
            ds = rs.data.data
          } else {
            rs = await this.axios.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql', {
              phuong_id: obj.phuong_id,
              pho_id: obj.pho_id,
              ap_id: obj.ap_id,
              khu_id: obj.khu_id,
              kieu: 1,
              loaikh_id: this.$refs.cboLoaiKH.value
            })

            ds = rs.data.data
          }
        }
        if (!ds || ds.length <= 0) {
          if (this.$refs.cboDonviQL.dataSource.length != 1) {
            this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!')
            this.$refs.cboDonviQL.value = null
            return
          }
        } else if (ds.length == 1) {
          this.$refs.cboDonviQL.value = ds[0]['donvi_id']
        } else {
          let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.$refs.cboDonviQL.value, null, ds)
          if (resolvePromise.ok) {
            this.$refs.cboDonviQL.value = resolvePromise.data.donviql_id
          } else return
        }
        this.tinhtt_id = obj.tinh_id
        this.quantt_id = obj.quan_id
        this.phuongtt_id = obj.phuong_id
        this.phott_id = obj.pho_id
        this.aptt_id = obj.ap_id
        this.khutt_id = obj.khu_id
        this.dacdiemtt_id = obj.dacdiem_id
        this.sonhatt = obj.sonha
        this.$refs.txtDiaChiTT.value = obj.diachimoi
        if (!this.$refs.txtDiaChiBC.value) {
          this.$refs.txtMaNVTC.value = '' //Nếu chọn lại địa chỉ  thanh toán thì clear mã nhân viên thu cước - Hiếu 29.09.2010
          this.$refs.txtMaTuyen.value = ''
          ///TuanNA: Lấy mã NVTC theo địa chỉ tt
          ///Ngày 07.12.2012
          this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.$refs.cboDonviQL.value, this.dacdiemtt_id)

          if (this.DONVIQL_HTTT == 1) this.HienThi_NVQL_AM_HTTT(this.$refs.cboLoaiKH.value, Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.$refs.cboDonviQL.value, this.$refs.cboHTTT.value)
          else {
            if (this.phantap_kh) {
              this.HienThi_NVQL_AM_PTKH(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.dacdiemtt_id, this.$refs.cboLoaiKH.value, this.$refs.cboNganhNgheLoc.value, this.pt_doanhthu, this.pt_diem_tn, Loai_NV.NHANVIEN_AM, this.$refs.cboDonviQL.value)
            } else this.HienThi_NVQL_AM(this.$refs.cboLoaiKH.value, Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.$refs.cboDonviQL.value)
          }
          ///
        }

        if (!this.$refs.txtDiaChiCT.value) {
          this.tinhct_id = this.tinhtt_id
          this.quanct_id = this.quantt_id
          this.phuongct_id = this.phuongtt_id
          this.phoct_id = this.phott_id
          this.apct_id = this.aptt_id
          this.khuct_id = this.khutt_id
          this.dacdiemct_id = this.dacdiemtt_id
          this.sonhact = this.sonhatt
          this.$refs.txtDiaChiCT.value = this.$refs.txtDiaChiTT.value
        }

        if (!this.$refs.txtDiaChiTB.value) {
          this.tinhtb_id = this.tinhtt_id
          this.quantb_id = this.quantt_id
          this.phuongtb_id = this.phuongtt_id
          this.photb_id = this.phott_id
          this.aptb_id = this.aptt_id
          this.khutb_id = this.khutt_id
          this.dacdiemtb_id = this.dacdiemtt_id
          this.sonhatb = this.sonhatt
          this.$refs.txtDiaChiTB.value = this.$refs.txtDiaChiTT.value
        }
        this.$refs.txtDiaChiTT.focus()
        // this.tinhtt_id = Number(obj.tinh_id);
        // this.quantt_id = Number(obj.quan_id);
        // this.phuongtt_id = Number(obj.phuong_id);
        // this.phott_id = Number(obj.pho_id);
        // this.aptt_id = Number(obj.ap_id);
        // this.khutt_id = Number(obj.khu_id);
        // this.dacdiemtt_id = Number(obj.dacdiem_id);
        // this.sonhatt = obj.sonha;

        // this.$refs.txtDiaChiTT.value = obj.diachi;
        // this.$refs.txtDiaChiTT.focus();
      }
      if (this.dataDiaChi.type == 'btnDiaChiBC') {
        this.tinhbc_id = Number(obj.tinh_id)
        this.quanbc_id = Number(obj.quan_id)
        this.phuongbc_id = Number(obj.phuong_id)
        this.phobc_id = Number(obj.pho_id)
        this.apbc_id = Number(obj.ap_id)
        this.khubc_id = Number(obj.khu_id)
        this.dacdiembc_id = Number(obj.dacdiem_id)
        this.sonhabc = obj.sonha

        this.$refs.txtDiaChiBC.value = obj.diachimoi
        this.$refs.txtDiaChiBC.focus()
      }
      if (this.dataDiaChi.type == 'btnDiaChiCT') {
        this.tinhct_id = Number(obj.tinh_id)
        this.quanct_id = Number(obj.quan_id)
        this.phuongct_id = Number(obj.phuong_id)
        this.phoct_id = Number(obj.pho_id)
        this.apct_id = Number(obj.ap_id)
        this.khuct_id = Number(obj.khu_id)
        this.dacdiemct_id = Number(obj.dacdiem_id)
        this.sonhact = obj.sonha

        this.$refs.txtDiaChiCT.value = obj.diachimoi
        this.$refs.txtDiaChiCT.focus()
      }

      if (this.dataDiaChi.type == 'btnDiaChiNDD') {
        this.tinhndd_id = Number(obj.tinh_id)
        this.quanndd_id = Number(obj.quan_id)
        this.phuongndd_id = Number(obj.phuong_id)
        this.phondd_id = Number(obj.pho_id)
        this.apndd_id = Number(obj.ap_id)
        this.khundd_id = Number(obj.khu_id)
        this.dacdiemndd_id = Number(obj.dacdiem_id)
        this.sonhandd = obj.sonha

        this.$refs.txtDiaChiNDD.value = obj.diachimoi
        this.$refs.txtDiaChiNDD.focus()
      }
    },

    //region Dia chi HD thue bao

    btnDiaChiTB_Click() {
      this.dataDiaChi.tinh_id = this.tinhtb_id
      this.dataDiaChi.quan_id = this.quantb_id
      this.dataDiaChi.phuong_id = this.phuongtb_id
      this.dataDiaChi.pho_id = this.photb_id
      this.dataDiaChi.ap_id = this.aptb_id
      this.dataDiaChi.khu_id = this.khutb_id
      this.dataDiaChi.dacdiem_id = this.dacdiemtb_id
      this.dataDiaChi.sonha = this.sonhatb
      this.dataDiaChi.block = this.blocktb
      this.dataDiaChi.tang = this.tangtb
      this.dataDiaChi.sophong = this.sophongtb
      this.dataDiaChi.motathem = this.motadctb
      this.dataDiaChi.diachi = this.$refs.txtDiaChiTB.value
      this.dataDiaChi.type = 'btnDiaChiTB'
      this.$bvModal.show('diaChiModal')
    },

    btnDiaChiBC_Click() {
      this.dataDiaChi.tinh_id = this.tinhbc_id
      this.dataDiaChi.quan_id = this.quanbc_id
      this.dataDiaChi.phuong_id = this.phuongbc_id
      this.dataDiaChi.pho_id = this.phobc_id
      this.dataDiaChi.ap_id = this.apbc_id
      this.dataDiaChi.khu_id = this.khubc_id
      this.dataDiaChi.dacdiem_id = this.dacdiembc_id
      this.dataDiaChi.sonha = this.sonhabc
      this.dataDiaChi.block = this.blockbc
      this.dataDiaChi.tang = this.tangbc
      this.dataDiaChi.sophong = this.sophongbc
      this.dataDiaChi.motathem = this.motadcbc
      this.dataDiaChi.diachi = this.$refs.txtDiaChiBC.value
      this.dataDiaChi.type = 'btnDiaChiBC'
      this.$bvModal.show('diaChiModal')
    },
    //endregion

    //region KeyPress
    async txtMaTB_KeyPress() {
      return new Promise(async (resolve, reject) => {
        try {
          this.loading(true)
          await this.Lay_Thongtin()
          this.loading(false)
        } catch (err) {
          console.log(err)
          this.$toast.error('Có lỗi: ' + err.message)
        }

        resolve(true)
      })
    },

    async Lay_Thongtin() {
      return new Promise(async (resolve, reject) => {
        try {
          //this.$refs.txtMaTB.value = this.$refs.txtMaTB.value;
          if (this.$refs.txtMaTB.value) {
            // let ds_somay = [];
            // ds_somay = await tcdanhba.LAY_DS_SOMAY_TN_THEO_MATB(
            //   this.$refs.txtMaTB.value
            // );
            // str = "";
            // if (ds_somay.length > 0) {
            //   for (let i = 0; i < ds_somay.length; i++) {
            //     str = str + ds_somay[0][i]["ma_tn"] + ", ";
            //   }
            //   if (str != ", ")
            //     this.$toast.error(
            //       "Số máy/Acc: " +
            //         this.$refs.txtMaTB.value +
            //         " đang được đi kèm với các mã thuê bao: " +
            //         str.Substring(0, str.length - 2) +
            //         "."
            //     );
            // }

            rs = await this.axios.post('web-hopdong/hopdong/lay_ds_hopdong_theo_matb', {
              dichVuVtId: this.$refs.cboDichVuVT.value,
              donViDlId: this.tt_nd.donvi_id,
              loaiHdId: LoaiHopDong.CHUYEN_QUYEN,
              loaiTbId: 0,
              maTb: this.$refs.txtMaTB.value.trim(),
              ttHdId: TrangThaiHD.MOI
            })

            let ds = rs.data.data
            if (ds.length > 0) {
              await this.HienThiTTHopDongKH(ds)
            } else await this.HienThiTT_DanhBa(this.$refs.txtMaTB.value)
          }
          resolve(true)
        } catch (err) {
          resolve(true)
          console.log(err)
          this.$toast.error('Có lỗi: ' + err.message)
        }
      })
    },

    Key_Press() {
      if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        SendKeys.Send('{TAB}')
      }
    },

    txtTenTB_Leave() {
      //this.$refs.txtTenTB.value = (this.$refs.txtTenTB.value);
      //this.$refs.txtNguoiDD.value = this.$refs.txtTenTB.value;
      //this.$refs.txtTenTT.value = this.$refs.txtTenTB.value;
      //this.$refs.txtTenKH.value = this.$refs.txtTenTB.value;
      // this.$refs.btnDiaChiTB_Click();
    },

    txtTenTT_KeyPress() {},

    txtEmailInt_KeyPress() {
      if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        this.$refs.txtTenKH.focus()
      }
    },

    txtEmailAdsl_KeyPress() {
      if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        this.$refs.txtTenKH.focus()
      }
    },

    txtTenKH_KeyPress() {
      if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        SendKeys.Send('{TAB}')
        btnDiaChiKH_Click()
      }
    },

    txtEmailKH_KeyPress() {
      if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        this.$refs.txtTenTT.focus()
      }
    },

    async txtMaGD_KeyPress() {
      this.loading(true)
      if (this.$refs.txtMaGD.value) {
        rs = await this.axios.post('web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', {
          in_donvi_dl_id: 0,
          in_loaihd_id: LoaiHopDong.CHUYEN_QUYEN,
          in_ma_gd: this.$refs.txtMaGD.value,
          in_tthd_id: TrangThaiHD.MOI
        })
        await this.HienThiTTHopDongKH(rs.data.data)
      }
      this.loading(false)
    },

    async txtMaKH_KeyPress() {
      if (this.$refs.txtMaKH.value) {
        this.loading(true)
        await this.HienThiTT_DanhBa_KH_NhanCQ(this.$refs.txtMaKH.value)
        this.loading(false)
      } else {
        this.$toast.error('Bạn chưa nhập mã khách hàng !')
        this.$refs.txtMaKH.focus()
        this.$refs.txtMaKH.value = ''
      }
    },

    async txtMaTT_KeyPress() {
      return new Promise(async (resolve, reject) => {
        if (this.$refs.txtMaTT.value) {
          if (this.hdkh_id == 0) {
            // Nếu chưa lập hđ thì view cả thông tin khách hàng cũ ra
            if (this.khachhang_id == 0) {
              this.HienThiTT_DanhBa_KH_NhanCQ(this.$refs.txtMaTT.value)
              this.$toast.error('Chưa có thông tin khách hàng. Hãy kiểm tra lại!')
              return
            } else {
              rs = await this.axios.post('web-hopdong/lapdatmoi/lay_dbtt_theo_matt_khid', {
                vdonvi_dl_id: 0,
                vkhachhang_id: this.khachhang_id,
                vma_tt: this.$refs.txtMaTT.value
              })
              this.HienThiTT_DanhBa_TT(rs.data.data)
            }
          } else {
            if (this.khachhang_id == 0) this.$toast.error('Đây là khách hàng mới. Bạn không thể tìm kiếm thông tin thanh toán cũ!')
            else {
              rs = await this.axios.post('web-hopdong/lapdatmoi/lay_dbtt_theo_matt_khid', {
                vdonvi_dl_id: 0,
                vkhachhang_id: this.khachhang_id,
                vma_tt: this.$refs.txtMaTT.value
              })
              this.HienThiTT_DanhBa_TT(rs.data.data)
            }
          }
        }
        resolve(true)
      })
    },

    //region HienThiTT_DanhBa_TT
    async HienThiTT_DanhBa_TT(ds) {
      try {
        this.httt_id_cu = -1
        if (ds.length > 0) {
          //Thông tin thanh toán
          this.$refs.txtMaTT.value = ds[0]['ma_tt']
          this.thanhtoan_id = Number(ds[0]['thanhtoan_id'])
          rs = await this.axios.post('web-hopdong/hopdong/ht_combo_ma_daidien', {
            thanhtoan_id: this.thanhtoan_id
          })
          let dt_madd = rs.data.data
          this.$refs.cboMaDD.dataSource = dt_madd
          this.$refs.cboMaDD.value = ds[0]['matb_dd']

          this.$refs.txtTenTT.value = ds[0]['ten_tt']
          /// TuanNA thêm code hiển thị thông tin  -- Ngày 06/08/2010

          this.$refs.txtMaTBDD.value = ds[0]['matb_dd']
          this.$refs.txtDiaChiTT.value = ds[0]['diachi_tt']
          this.$refs.txtDiaChiCT.value = ds[0]['diachi_ct']
          rs = await this.axios.get('web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid', {
            params: {
              dbttid: this.thanhtoan_id
            }
          })
          this.HienThiTTDiaChi(rs.data.data, 2)

          this.$refs.txtMaIG.value = ds[0]['main_ghep']
          if (ds[0]['mig_id']) {
            this.mig_id = Number(ds[0]['mig_id'])
          }

          //TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT --- Ngày 12/11/2010
          this.DV_QHNS = ds[0]['DV_QHNS']
          this.MACHUONG = ds[0]['MACHUONG']
          this.MN_KT = ds[0]['MN_KT']
          this.MA_NDKT = ds[0]['MA_NDKT']
          this.MN_NSNN = ds[0]['MN_NSNN']
          if (ds[0]['TIEN_DM']) this.TIEN_DM = Number(ds[0]['TIEN_DM'])
          else this.TIEN_DM = 0
          //

          /////--- Ngày 01/09/2010
          //this.donvi_qlcu = LayDonVi_QL(this.$refs.txtMaTT.value, this.phuongtt_id, this.phott_id);
          //this.donvi_qlmoi = this.donvi_qlcu;
          /////
          this.$refs.txtMST_TT.value = ds[0]['mst']
          this.$refs.txtSTK_TT.value = ds[0]['stk']
          this.$refs.txtChu_TK.value = ds[0]['chu_tk']
          this.$refs.txtGhiChuTT.value = ds[0]['ghichu']

          if (ds[0]['nganhang_id']) cboNganHangTT.value = ds[0]['nganhang_id']

          this.$refs.cboHTTT.value = ds[0]['httt_id']
          this.httt_id_cu = Number(ds[0]['httt_id'])
          /// TuanNA thêm code thay thế this.donvi_ql -->this.$refs.cboDonviQL  -- Ngày 29/07/2010
          if (ds[0]['donvi_id']) this.$refs.cboDonviQL.value = ds[0]['donvi_id']
          if (ds[0]['nhanvien_id']) {
            this.nhanvientc_id = Number(ds[0]['nhanvien_id'])
            rs = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', {
              param: this.nhanvientc_id,
              type: 4
            })
            this.$refs.txtMaNVTC.value = rs.data.data
          } else {
            this.nhanvientc_id = 0
            this.$refs.txtMaNVTC.value = ''
          }
          if (ds[0]['tuyenthu_id']) {
            this.tuyenthu_id = Number(ds[0]['tuyenthu_id'])
            rs = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tuyenthu', {
              param: this.tuyenthu_id,
              type: 1
            })
            this.$refs.txtMaTuyen.value = rs.data.data
          } else {
            this.tuyenthu_id = 0
            this.$refs.txtMaTuyen.value = ''
          }
          this.matb_dd = ds[0]['matb_dd']

          this.LAY_DS_SUDUNG_DVK(this.thanhtoan_id, 2)
          this.HienThi_NVQL_AM_DB_HD(1, this.thanhtoan_id)
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('' + err.message)
      }
    },
    //endregion

    cboDoituongTB_KeyPress() {
      if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        if (this.dichvuvt_id == DichVuVienThong.CO_DINH || this.dichvuvt_id == DichVuVienThong.DI_DONG) {
          this.$refs.txtTenKH.focus()
        } else {
          if (this.dichvuvt_id == DichVuVienThong.ADSL) this.$refs.cboGoiCuocAdsl.focus()
          else this.$refs.txtEmailInt.focus()
        }
      }
    },

    //endregion

    //region Tạo dữ liệu cho Dataset
    async TaoDuLieu(themmoi) {
      await this.TaoDuLieu_HDKH(themmoi)
      await this.TaoDuLieu_HDTT(themmoi)
      await this.TaoDuLieu_HDTB(themmoi)
      await this.TaoDuLieu_HDTBDV(themmoi)
      //Hiếu bổ sung hàm tạo dữ liệu thông tin thanh toán 15/09/2010
      ///TuấnNA thêm code tạo dữ liệu thông tin thanh toán --- Ngày 15/09/2010
      await this.TaoDuLieu_THONGTIN_TT(themmoi)
      await this.TaoDuLieu_CT_TIENHD(true)
      await this.TaoDuLieu_HDTT_NV(true)
      //
      await this.TaoDuLieu_DiaChi(themmoi, 4)
      await this.TaoDuLieu_DKDV()
      await this.CapNhat_HDTB_ID_DangKyDV(this.hdtb_id)

      this.dsHDTB_CD = []
      this.dsHDTB_ADSL = []
      this.dsHDTB_TSL = []
      this.dsHDTB_MEWAN = []
      this.dsHDTB_GP = []
      this.dsHDTB_IMS = []
      this.dsHDTB_CNTT = []
      this.dsHDTB_DD = []
      switch (this.dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          await this.TaoDuLieu_HDTB_CD(themmoi)
          break
        case DichVuVienThong.DI_DONG:
          await this.TaoDuLieu_HDTB_DD(themmoi)
          break

        case DichVuVienThong.ADSL:
        //case DichVuVienThong.HOINGHI_TRUYENHINH:
        case DichVuVienThong.EMAIL:
          await this.TaoDuLieu_HDTB_ADSL(themmoi)
          break
        case DichVuVienThong.MEGA_EYES:
          await this.TaoDuLieu_HDTB_ADSL(themmoi)
          break
        case DichVuVienThong.GPHONE:
          await this.TaoDuLieu_HDTB_GP(themmoi)
          break
        case DichVuVienThong.TSL:
        case DichVuVienThong.NOIBO_2DIEM:
          await this.TaoDuLieu_HDTB_TSL(themmoi)
          break
        case DichVuVienThong.MEGAWAN:
        case DichVuVienThong.METRONET:
        case DichVuVienThong.NOIBO_DADIEM:
          await this.TaoDuLieu_HDTB_MGWAN(themmoi)
          break
        case DichVuVienThong.IMS:
          await this.TaoDuLieu_HDTB_IMS(themmoi)
          break
        case DichVuVienThong.DICHVU_CNTT:
        case DichVuVienThong.ANTOAN_BAOMAT_TT:
        case DichVuVienThong.TRUNGTAM_DULIEU:
        case DichVuVienThong.HOINGHI_TRUYENHINH:
          await this.TaoDuLieu_HDTB_CNTT(themmoi)
          break
        default:
          await this.TaoDuLieu_HDTB_CD(themmoi)
          break
      }
    },

    //region TaoDuLieu_HDTB_DV
    async TaoDuLieu_HDTBDV(themmoi) {
      dsHDTBDV = new HDTB_DV_DATA()

      let dtTemp = await bangts.GetDataSql('select donvi_id, loaidv_id, kieudv_id from ' + DatabaseConstants.DB2 + '.dbtb_dv where this.thuebao_id =' + this.thuebao_id)

      for (let i = 0; i < dtTemp.length; i++) {
        let row = dsHDTBDV.HDTB_DV.NewHDTB_DVRow()
        row.HDTB_ID = this.hdtb_id
        row.LOAIDV_ID = Number(dtTemp[i]['loaidv_id'])
        row.DONVI_ID = Number(dtTemp[i]['donvi_id'])
        row.KIEUDV_ID = Number(dtTemp[i]['kieudv_id'])
        dsHDTBDV.HDTB_DV.AddHDTB_DVRow(row)
      }
    },

    async TaoDuLieu_HDTBDV_DS(hdtb_id, thuebao_id) {
      let dtTemp = await bangts.GetDataSql('select donvi_id, loaidv_id, kieudv_id from ' + DatabaseConstants.DB2 + '.dbtb_dv where this.thuebao_id =' + this.thuebao_id)

      for (let i = 0; i < dtTemp.length; i++) {
        let row = dsHDTBDV.HDTB_DV.NewHDTB_DVRow()
        row.HDTB_ID = this.hdtb_id
        row.LOAIDV_ID = Number(dtTemp[i]['loaidv_id'])
        row.DONVI_ID = Number(dtTemp[i]['donvi_id'])
        row.KIEUDV_ID = Number(dtTemp[i]['kieudv_id'])
        dsHDTBDV.HDTB_DV.AddHDTB_DVRow(row)
      }
    },
    //endregion

    //region  Tao du lieu cho bang HD_KHACHHANG
    async TaoDuLieu_HDKH(themmoi) {
      vmagd_temp = ''
      dsHDKH = new HD_KHACHHANG_DATA()
      if (themmoi == true) this.hdkh_id = new GetKeyFacade().GetKey('HD_KHACHHANG')
      let rowHDKH = dsHDKH.HD_KHACHHANG.NewHD_KHACHHANGRow()
      rowHDKH.HDKH_ID = this.hdkh_id
      if (themmoi == true) {
        if (ts_sinhthis.ma_gd_theo_donvi) vmagd_temp = laphd.Lay_Ma_GD_V2(this.tt_nd.donvi_id, LoaiHopDong.CHUYEN_QUYEN)
        else vmagd_temp = laphd.Lay_Ma_GD(LoaiHopDong.CHUYEN_QUYEN)
        if (vmagd_temp.Contains('ERROR:')) {
          this.$toast.success(vmagd_temp.Split(':')[1])
          loi_sinh_magd = true
        }
        this.$refs.txtMaGD.value = vmagd_temp

        this.ma_gd = this.$refs.txtMaGD.value ///TuấnNA thêm code lấy thông tin this.ma_gd --Ngày 09/08/2010
        rowHDKH.MA_GD = this.$refs.txtMaGD.value
        ///TuấnNA thêm code lấy dữ liệu cho mã HĐ --- Ngày 23/08/2010
        if (this.$refs.txtMaHD.value == '') {
          //nếu this.$refs.txtMaHD trống ---> sinh mã HD
          this.$refs.txtMaHD.value = laphd.Lay_Ma_HD(this.tt_nd.donvi_dl_id)
          rowHDKH.MA_HD = this.$refs.txtMaHD.value
        } else rowHDKH.MA_HD = this.$refs.txtMaHD.value
      } else {
        rowHDKH.MA_GD = this.$refs.txtMaGD.value
        rowHDKH.MA_HD = this.$refs.txtMaHD.value
      }
      if (this.$refs.txtMaKH.value == '') {
        this.$refs.txtMaKH.value = hths.SINH_MAKH(Number(this.$refs.cboDonviQL.value)) /// TuanNA sửa code thay thế this.donvi_ql --> this.$refs.cboDonviQL -- Ngay 29/07/2010
        this.khachhang_id = 0
      }
      rowHDKH.MA_KH = this.$refs.txtMaKH.value

      if (this.khachhang_id > 0) {
        rowHDKH.KHACHHANG_ID = this.khachhang_id
        rowHDKH.BOSUNGTB_ID = 1
      } else rowHDKH.BOSUNGTB_ID = 0
      ///Tuấn NA thêm code chuẩn hóa lại xâu ký tự đặc biệt(Ủy, Ánh...) trước khi đưa vào CSDL ---Ngày 23/10/2010
      rowHDKH.TEN_KH = this.$refs.txtTenKH.value
      rowHDKH.DIACHI_KH = this.$refs.txtDiaChiKH.value
      rowHDKH.NGUOI_DD = this.$refs.txtNguoiDD.value
      //rowHDKH.NGUOI_DD = this.$refs.txtNguoiDD.value;
      rowHDKH.SO_DT = this.$refs.txtTelKH.value
      rowHDKH.SO_FAX = this.$refs.txtFaxKH.value
      if (hdkh_cha_id > 0) {
        rowHDKH.HDKH_CHA_ID = hdkh_cha_id
      }

      //Tuan them du lieu vao dataset -- ngay 07/07/2010
      if (this.$refs.rdoGTNam.checked == true) {
        rowHDKH.GIOITINH = 0
      } else if (this.$refs.rdoGTNu.checked == true) {
        rowHDKH.GIOITINH = 1
      }
      rowHDKH.GHICHU = this.$refs.txtGhiChuKH.value
      //rowHDKH.GHICHU = this.$refs.txtGhiChuKH.value;
      rowHDKH.MST = this.$refs.txtMaSTKH.value
      rowHDKH.STK = this.$refs.txtSoTKKH.value

      /*if (cboNganhNghe.value != null)
                  {
                      rowHDKH.NGANHNGHE_ID = parseFloat(cboNganhNghe.value);
                  }
                  else
                      rowHDKH.NGANHNGHE_ID = 1;*/
      rowHDKH.NGANHNGHE_ID = parseFloat(this.$refs.cboNganhNgheLoc.value)

      if (this.$refs.cboQuocTich.value != null) {
        rowHDKH.QUOCTICH_ID = parseFloat(this.$refs.cboQuocTich.value)
      } else rowHDKH.QUOCTICH_ID = 1

      if (this.$refs.cboDanToc.value != null) {
        rowHDKH.DANTOC_ID = parseFloat(this.$refs.cboDanToc.value)
      } else rowHDKH.DANTOC_ID = 1

      if (this.$refs.chkNganHangKH.checked == true) rowHDKH.NGANHANG_ID = parseFloat(this.$refs.cboNganHangKH.value)

      rowHDKH.NGAY_YC = this.$refs.dtpNgayYC.value
      rowHDKH.DIACHI_NDD = this.$refs.txtDiaChiNDD.value
      rowHDKH.CHUCDANH_DD = this.$refs.txtChucDanhDD.value
      rowHDKH.SO_FAX = this.$refs.txtFaxKH.value

      ///TuấnNA : lấy các trường ghi chú, đặc trưng --- Ngày 23/09/2010
      rowHDKH.DACTRUNG = this.$refs.txtDacTrung.value
      //rowHDKH.SO_GT_KEM = (txtGiayToKem.value);
      //End Tuan
      if (this.$refs.txtNgaySN.value) {
        rowHDKH.NGAY_SN = this.$refs.txtNgaySN.value
      }
      rowHDKH.NGAYLAP_HD = this.$refs.dtpNgayLapHDNQ.value
      rowHDKH.LOAIGT_ID = Number(this.$refs.cboGiayToKH.value)
      rowHDKH.SO_GT = this.$refs.txtSoGT.value
      if (this.$refs.txtNgayCap.value) {
        rowHDKH.NGAYCAP = this.$refs.txtNgayCap.value
      }
      if (this.$refs.txtNgayHetHan.value) {
        rowHDKH.NGAYHETHAN_GT = this.$refs.txtNgayHetHan.value
      }
      rowHDKH.NOICAP = this.$refs.txtNoiCap.value
      rowHDKH.SO_FAX = this.$refs.txtFaxKH.value
      rowHDKH.DONVI_ID = this.tt_nd.donvi_id
      rowHDKH.NHANVIEN_ID = this.tt_nd.nhanvien_id
      rowHDKH.KHLON_ID = Number(this.$refs.cboKhachHangLon.value)
      rowHDKH.LOAIHD_ID = LoaiHopDong.CHUYEN_QUYEN
      rowHDKH.HOKHAU = this.$refs.txtHoKhau.value
      rowHDKH.NOICAP_HK = this.$refs.txtNoiCapHK.value

      if (this.$refs.txtNgayCapHK.value) rowHDKH.NGAY_HK = this.$refs.txtNgayCapHK.value

      rowHDKH.LOAIKH_ID = Number(this.$refs.cboLoaiKH.value)

      if (PHANLOAIKH_ID != -1 && PHANLOAIKH_ID != 0) rowHDKH.PHANLOAIKH_ID = PHANLOAIKH_ID

      rowHDKH.NGUOI_CN =  this.$root.token.getNguoiDungID()
      rowHDKH.NGAY_CN = new Date().toLocaleDateString('en-GB')
      rowHDKH.MAY_CN = await this.$root.token.getMachine()
      rowHDKH.IP_CN = await this.$root.token.getIP()
      //TuấnNA thêm tạo dữ liệu tiểu ngành cấp 1,2,3 --- Ngày 03/12/2010
      rowHDKH.this.TNC1_ID = this.TNC1_ID
      rowHDKH.this.TNC2_ID = this.TNC2_ID
      rowHDKH.this.TNC3_ID = this.TNC3_ID
      rowHDKH.this.TNC4_ID = this.TNC4_ID
      //
      if (this.$refs.chkCTV.checked && this.$refs.txtCTV.value) {
        rowHDKH.CTV_ID = this.ctv_id
      }
      if (this.$refs.chkNguoiGT.checked && this.$refs.txtNguoiGT.value) {
        rowHDKH.NHANVIENGT_ID = this.nguoigt_id
      }
      dsHDKH.HD_KHACHHANG.AddHD_KHACHHANGRow(rowHDKH)

      if (themmoi == false) {
        dsHDKH.AcceptChanges()
        dsHDKH.HD_KHACHHANG[0].SetModified()
      }
    },
    //endregion

    //region Tao du lieu cho bang HD_THANHTOAN
    async TaoDuLieu_HDTT(themmoi) {
      dsHDTT = new HD_THANHTOAN_DATA()

      if (themmoi == true) this.hdtt_id = new GetKeyFacade().GetKey('HD_THANHTOAN')
      let rowHDTT = dsHDTT.HD_THANHTOAN.NewHD_THANHTOANRow()
      rowHDTT.HDTT_ID = this.hdtt_id
      rowHDTT.HDKH_ID = this.hdkh_id
      if (this.$refs.txtMaTT.value == '') {
        this.$refs.txtMaTT.value = hths.SINH_MATT(Number(this.$refs.cboDonviQL.value), this.quanld_id) /// TuấnNA thay thế this.donvi_ql --> CboDonviQL -- Ngay 29/07/2010
        this.thanhtoan_id = 0
      }
      rowHDTT.DONVI_ID = Number(this.$refs.cboDonviQL.value) /// TuấnNA thay thế this.donvi_ql --> CboDonviQL -- Ngay 29/07/2010
      rowHDTT.MA_TT = this.$refs.txtMaTT.value
      if (thanhtoan_id > 0) rowHDTT.THANHTOAN_ID = thanhtoan_id
      ///Tuấn NA thêm code chuẩn hóa lại xâu ký tự đặc biệt(Ủy, Ánh...) trước khi đưa vào CSDL ---Ngày 23/10/2010
      rowHDTT.TEN_TT = this.$refs.txtTenTT.value
      rowHDTT.DIACHI_TT = this.$refs.txtDiaChiTT.value
      rowHDTT.DIACHI_BC = this.$refs.txtDiaChiBC.value
      rowHDTT.MST = this.$refs.txtMST_TT.value
      rowHDTT.HTTT_ID = Number(this.$refs.cboHTTT.value)
      rowHDTT.STK = this.$refs.txtSTK_TT.value
      rowHDTT.CHU_TK = this.$refs.txtChu_TK.value
      if (Number(this.$refs.cboHTTT.value) == HinhThucThanhToan.UYNHIEM_THU) {
        rowHDTT.NGANHANG_ID = Number(cboNganHangTT.value)
      }
      if (this.tuyenthu_id != 0) rowHDTT.TUYENTHU_ID = this.tuyenthu_id
      rowHDTT.MATB_DD = this.$refs.cboMaDD.value
      rowHDTT.NGUOI_CN =  this.$root.token.getNguoiDungID()
      rowHDTT.NGAY_CN = new Date().toLocaleDateString('en-GB')
      rowHDTT.MAY_CN = await this.$root.token.getMachine()
      //TuanNA Them code thêm dữ liệu vào Dataset -- Ngày 07/07/2010
      //rowHDTT.NHANVIEN_ID = this.nhanvientc_id;
      rowHDTT.DIACHI_CT = this.$refs.txtDiaChiCT.value
      rowHDTT.SO_DT = this.$refs.tbDienThoaiLienHe.value
      rowHDTT.EMAIL = this.$refs.tbEmail.value
      //rowHDTT.MS_NSNN = "";//TuấnNA bỏ các thông tin MS_NSNN chưa sử dụng -- Ngày 29/07/2010
      //rowHDTT.ML_NSNN = "";//TuấnNA bỏ các thông tin ML_NSNN chưa sử dụng -- Ngày 29/07/2010
      rowHDTT.GHICHU = this.$refs.txtGhiChuTT.value
      //End tuan
      //TuấnNA thêm code tạo dữ liệu NSNN --- Ngày 12/11/2010
      rs = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nganhang', {
        param: this.$refs.cboNganHangTT.value,
        type: 1
      })
      let loai_nh = rs.data.data
      if (loai_nh == '2') {
        rowHDTT.this.DV_QHNS = this.DV_QHNS
        rowHDTT.this.MACHUONG = this.MACHUONG
        rowHDTT.this.MN_KT = this.MN_KT
        rowHDTT.this.MA_NDKT = this.MA_NDKT
        rowHDTT.this.MN_NSNN = this.MN_NSNN
        rowHDTT.this.TIEN_DM = this.TIEN_DM
      } else {
        rowHDTT.this.DV_QHNS = ''
        rowHDTT.this.MACHUONG = ''
        rowHDTT.this.MN_KT = ''
        rowHDTT.this.MA_NDKT = ''
        rowHDTT.this.MN_NSNN = ''
        rowHDTT.this.TIEN_DM = 0
      }
      //
      dsHDTT.HD_THANHTOAN.AddHD_THANHTOANRow(rowHDTT)

      if (themmoi == false) {
        dsHDTT.AcceptChanges()
        dsHDTT.HD_THANHTOAN[0].SetModified()
      }
    },
    //endregion

    //region Tao du lieu cho bang HD_THUEBAO
    async TaoDuLieu_HDTB(themmoi) {
      this.dsHDTB = []

      if (themmoi == true) this.hdtb_id = new GetKeyFacade().GetKey('HD_THUEBAO')
      let rowHDTB = {}

      rowHDTB.HDKH_ID = this.hdkh_id
      rowHDTB.HDTB_ID = this.hdtb_id
      if (themmoi == true) rowHDTB.HDTT_ID = this.hdtt_id
      else rowHDTB.HDTT_ID = Number(this.$refs.cboTT.value)

      if (this.thuebao_id > 0) rowHDTB.THUEBAO_ID = this.thuebao_id

      if (this.thuebao_cha_id > 0) rowHDTB.THUEBAO_CHA_ID = this.thuebao_cha_id

      rowHDTB.MA_TB = this.$refs.txtMaTB.value
      rowHDTB.TEN_TB = this.$refs.txtTenTB.value
      rowHDTB.DIACHI_TB = this.$refs.txtDiaChiTB.value
      rowHDTB.KIEULD_ID = Number(this.$refs.cboKieuLD.value)
      rowHDTB.DOITUONG_ID = Number(this.$refs.cboDoituongTB.value)
      rowHDTB.TTHD_ID = TrangThaiHD.MOI
      rowHDTB.LOAITB_ID = loaitb_id
      rowHDTB.DICHVUVT_ID = this.dichvuvt_id
      rowHDTB.DONVI_ID = Number(this.$refs.cboDaiVT.value) //Number(this.$refs.cboDonviQL.value);/// TuấnNA thay thế this.donvi_ql -->CboDonviQL -- Ngay 29/07/2010
      rowHDTB.HDKH_ID = this.hdkh_id
      rowHDTB.NGUOI_CN = this.$root.token.getNguoiDungID()
      rowHDTB.NGAY_CN = new Date().toLocaleDateString('en-GB')
      rowHDTB.MAY_CN = await this.$root.token.getMachine()
      rowHDTB.IP_CN = await this.$root.token.getIP()
      if (themmoi == true) {
        if (rowHDTB.DICHVUVT_ID == DichVuVienThong.DI_DONG) status = '0'
        else if (rowHDTB.DICHVUVT_ID == DichVuVienThong.DI_DONG || rowHDTB.LOAITB_ID == LoaiHinhTB.INTERNET_MYTV) status = '0'
        else status = ''
        rowHDTB.STATUS = status
      } else rowHDTB.STATUS = status
      if (this.$refs.txtNgaySinhTB.value != '') {
        rowHDTB.NGAY_SN = this.$refs.txtNgaySinhTB.value //tuan tao du lieu cho truong ngay sinh cua thue bao -- Ngay 06/07/2010
      }
      rowHDTB.this.TBHD_PL = this.TBHD_PL // Tuan them truong this.TBHD_PL cho thue bao --> 1 <-> In HD; 0 <-> In PL -- Ngay 07/07/2010
      rowHDTB.DIACHI_LD = this.$refs.txtDiachiLD.value //TuấnNA thêm dữ liệu địa chỉ lắp đặt cho dataset HDTB -- Ngày 29/07/2010
      rowHDTB.GHICHU = this.$refs.txtGhiChuTB.value //TuấnNA thêm dữ liệu ghi chú cho dataset HDTB -- Ngày 29/07/2010
      //hiếu bổ sung thêm trường đơnvidau, donvicuoi vao bảng hd_thuebao
      rowHDTB.DONVI_DAU_ID = this.donvi_dau_id
      rowHDTB.DONVI_CUOI_ID = this.donvi_cuoi_id
      rowHDTB.QUYTRINH_ID = this.quytrinh_id
      //end
      ///Tuấn NA thêm code lấy giá trị datcoc_csd --- Ngày 20/10/2010
      //rowHDTB.DATCOC_CSD = 0;

      if (this.$refs.cboMucCuocTB.length > 0) {
        rowHDTB.MUCUOCTB_ID = Number(this.$refs.cboMucCuocTB.value)
        temp = this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_muccuoc_tb', {
          param: this.$refs.cboMucCuocTB.value,
          type: 1
        })
        rowHDTB.MUCUOC_TB = parseFloat(temp.data.data)
      }
      dsHDTB.HD_THUEBAO.AddHD_THUEBAORow(rowHDTB)
      if (themmoi == false) {
        dsHDTB.AcceptChanges()
        dsHDTB.HD_THUEBAO[0].SetModified()
      }
    },

    async TaoDuLieu_HDTB_DS(hdtb_id, thuebao_id) {
      this.dsHDTB = []
      let quytrinh_id_2 = 0
      let huonggiaotn_id = 0
      let rowHDTB = {}
      rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_adsl', {
        params: {
          thueBaoId: thuebao_id
        }
      })
      rowHDTB = rs.data.data
      rs = await this.axios.get('web-hopdong/hopdong/lay_thongtin_chuyenquyen_theo_tbid')
      let dsTT = rs.data.data
      if (dsTT != null && dsTT.length > 0) {
        let row = dsTT[0]
        rowHDTB.HDKH_ID = this.hdkh_id
        rowHDTB.HDTB_ID = hdtb_id

        rowHDTB.HDTT_ID = this.hdtt_id

        rowHDTB.THUEBAO_ID = this.thuebao_id
        //rowHDTB.MA_TB = this.$refs.txtMaTB.value;
        rowHDTB.TEN_TB = this.$refs.txtTenTB.value
        rowHDTB.DIACHI_TB = this.$refs.txtDiaChiTB.value
        rowHDTB.KIEULD_ID = Number(row['kieuld_id'])
        rowHDTB.DOITUONG_ID = Number(row['doituong_id'])
        rowHDTB.TTHD_ID = TrangThaiHD.MOI
        //rowHDTB.LOAITB_ID = loaitb_id;
        //rowHDTB.DICHVUVT_ID = this.dichvuvt_id;
        rowHDTB.DONVI_ID = Number(row['donviql_id']) //Number(this.$refs.cboDonviQL.value);/// TuấnNA thay thế this.donvi_ql -->CboDonviQL -- Ngay 29/07/2010
        //rowHDTB.HDKH_ID = this.hdkh_id;
        rowHDTB.NGUOI_CN = this.$root.token.getNguoiDungID()
        rowHDTB.NGAY_CN =  new Date().toLocaleDateString('en-GB')
        rowHDTB.MAY_CN = await this.$root.token.getMachine()
        rowHDTB.IP_CN = await this.$root.token.getIP()

        if (rowHDTB.DICHVUVT_ID == DichVuVienThong.DI_DONG) status = '0'
        else if (rowHDTB.DICHVUVT_ID == DichVuVienThong.DI_DONG || rowHDTB.LOAITB_ID == LoaiHinhTB.INTERNET_MYTV) this.status = '0'
        else this.status = ''
        rowHDTB.STATUS = this.status

        //if (this.$refs.txtNgaySinhTB.value != "  /  /")
        //{
        //    rowHDTB.NGAY_SN = CommonFunction.getDate(this.$refs.txtNgaySinhTB.value); //tuan tao du lieu cho truong ngay sinh cua thue bao -- Ngay 06/07/2010
        //}
        rowHDTB.this.TBHD_PL = this.TBHD_PL // Tuan them truong this.TBHD_PL cho thue bao --> 1 <-> In HD; 0 <-> In PL -- Ngay 07/07/2010
        //rowHDTB.DIACHI_LD = (this.$refs.txtDiachiLD.value); //TuấnNA thêm dữ liệu địa chỉ lắp đặt cho dataset HDTB -- Ngày 29/07/2010
        //rowHDTB.GHICHU = (this.$refs.txtGhiChuTB.value); //TuấnNA thêm dữ liệu ghi chú cho dataset HDTB -- Ngày 29/07/2010
        //hiếu bổ sung thêm trường đơnvidau, donvicuoi vao bảng hd_thuebao
        //rowHDTB.DONVI_DAU_ID = this.donvi_dau_id;
        //rowHDTB.DONVI_CUOI_ID = this.donvi_cuoi_id;

        rs = await this.axios.post('web-hopdong/goi-da-dichvu/sp_layhuonggiao_tiepnhan', {
          loaihd_id: LoaiHopDong.CHUYEN_QUYEN,
          kieuld_id: Number(row['kieuld_id'])
        })

        let dset = rs.data.data

        if (dset.length > 0) {
          huonggiaotn_id = Number(dset[0]['huonggiao_id'])
          quytrinh_id_2 = Number(dset[0]['quytrinh_id'])
        }

        rowHDTB.QUYTRINH_ID = quytrinh_id_2
        //end
        ///Tuấn NA thêm code lấy giá trị datcoc_csd --- Ngày 20/10/2010
        //rowHDTB.DATCOC_CSD = 0;

        //if (this.$refs.cboMucCuocTB.length > 0)
        //{
        //    rowHDTB.MUCUOCTB_ID = Number(this.$refs.cboMucCuocTB.value);
        //    rowHDTB.MUCUOC_TB = parseFloat(await checkdata.MAP_ID("cuoc_tb", DatabaseConstants.DB2 + ".muccuoc_tb", "where mucuoctb_id = " + Number(this.$refs.cboMucCuocTB.value)));
        //}
        this.dsHDTB.push(rowHDTB)
        //if (themmoi == false)
        //{
        //    dsHDTB.AcceptChanges();
        //    dsHDTB.HD_THUEBAO[0].SetModified();
        //}
      }
      return huonggiaotn_id
    },
    //endregion

    //region Tao du lieu cho bang TaoDuLieu_HDTT_NV
    async TaoDuLieu_HDTT_NV(themmoi) {
      this.dsHDTT_NV = []
      if (this.nhanvien_ql_am_id != 0) {
        let rowhdtt_nv = {}
        rowhdtt_nv.HDTT_ID = this.hdtt_id
        rowhdtt_nv.NHANVIEN_ID = this.nhanvien_ql_am_id
        rowhdtt_nv.MAY_CN = await this.$root.token.getMachine()
        rowhdtt_nv.NGAY_CN = new Date().toLocaleDateString('en-GB')
        rowhdtt_nv.NGUOI_CN = this.$root.token.getNguoiDungID()
        this.dsHDTT_NV.push(rowhdtt_nv)
      }
    },
    //endregion

    //region Tao du lieu cho Dia chi
    async TaoDuLieu_DiaChi(themmoi, kieu) {
      this.dsDiaChi = []
      this.dsDiaChiHDKH = []
      this.dsDiaChiHDTT = []
      this.dsDiaChiHDTB = []

      if (kieu == 1 || kieu == 4) {
        let rowDiaChiKH = {}
        if (themmoi == true) {
          rs = await this.axios.post('web-hopdong/khoiphucthanhly/get_keys', {
            keyname: 'DIACHI'
          })
          this.diachikh_id = rs.data.data
        }
        rowDiaChiKH.DIACHI_ID = this.diachikh_id
        if (this.tinhkh_id != 0) rowDiaChiKH.TINH_ID = this.tinhkh_id
        rowDiaChiKH.QUAN_ID = this.quankh_id

        if (this.phuongkh_id != 0) rowDiaChiKH.PHUONG_ID = this.phuongkh_id
        if (this.phokh_id != 0) rowDiaChiKH.PHO_ID = this.phokh_id
        if (this.apkh_id != 0) rowDiaChiKH.AP_ID = this.apkh_id
        if (this.khukh_id != 0) rowDiaChiKH.KHU_ID = this.khukh_id
        if (this.dacdiemkh_id != 0) rowDiaChiKH.DACDIEM_ID = this.dacdiemkh_id
        rowDiaChiKH.SONHA = this.sonhakh
        rowDiaChiKH.DIACHI = this.$refs.txtDiaChiKH.value
        this.dsDiaChi.push(rowDiaChiKH)

        if (themmoi == true) {
          let rowDiaChiHDKH = {}
          rowDiaChiHDKH.DIACHI_ID = this.diachikh_id
          rowDiaChiHDKH.HDKH_ID = this.hdkh_id
          this.dsDiaChiHDKH.push(rowDiaChiHDKH)
        }
      }

      if (kieu == 2 || kieu == 4) {
        let rowDiaChiTT = {}
        if (themmoi == true) {
          rs = await this.axios.post('web-hopdong/khoiphucthanhly/get_keys', {
            keyname: 'DIACHI'
          })
          this.diachitt_id = new GetKeyFacade().GetKey('DIACHI')
        }
        rowDiaChiTT.DIACHI_ID = diachitt_id
        if (this.tinhtt_id != 0) rowDiaChiTT.TINH_ID = this.tinhtt_id
        rowDiaChiTT.QUAN_ID = this.quantt_id
        if (this.phuongtt_id != 0) rowDiaChiTT.PHUONG_ID = this.phuongtt_id

        if (this.phott_id != 0) rowDiaChiTT.PHO_ID = this.phott_id
        if (this.aptt_id != 0) rowDiaChiTT.AP_ID = this.aptt_id
        if (this.khutt_id != 0) rowDiaChiTT.KHU_ID = this.khutt_id
        if (this.dacdiemtt_id != 0) rowDiaChiTT.DACDIEM_ID = this.dacdiemtt_id

        rowDiaChiTT.SONHA = this.sonhatt
        rowDiaChiTT.DIACHI = this.$refs.txtDiaChiTT.value
        dsDiaChi.DIACHI.AddDIACHIRow(rowDiaChiTT)

        if (themmoi == true) {
          let rowDiaChiHDTT = dsDiaChiHDTT.DIACHI_HDTT.NewDIACHI_HDTTRow()
          rowDiaChiHDTT.DIACHI_ID = diachitt_id
          rowDiaChiHDTT.HDTT_ID = this.hdtt_id
          dsDiaChiHDTT.DIACHI_HDTT.AddDIACHI_HDTTRow(rowDiaChiHDTT)
        }
      }

      if (kieu == 3 || kieu == 4) {
        let rowDiaChiTB = {}
        let rowDiachiLD = {}
        if (themmoi == true) {
          rs = await this.axios.post('web-hopdong/khoiphucthanhly/get_keys', {
            keyname: 'DIACHI'
          })
          this.diachitb_id = rs.data.data
          rs = await this.axios.post('web-hopdong/khoiphucthanhly/get_keys', {
            keyname: 'DIACHI'
          })
          this.diachild_id = rs.data.data
        }

        //Địa chỉ thuê bao
        rowDiaChiTB.DIACHI_ID = diachitb_id
        if (this.tinhtb_id != 0) rowDiaChiTB.TINH_ID = this.tinhtb_id
        rowDiaChiTB.QUAN_ID = this.quantb_id

        if (this.phuongtb_id != 0) rowDiaChiTB.PHUONG_ID = this.phuongtb_id

        if (this.photb_id != 0) rowDiaChiTB.PHO_ID = this.photb_id
        if (this.aptb_id != 0) rowDiaChiTB.AP_ID = this.aptb_id
        if (this.khutb_id != 0) rowDiaChiTB.KHU_ID = this.khutb_id
        if (this.dacdiemtb_id != 0) rowDiaChiTB.DACDIEM_ID = this.dacdiemtb_id
        ///
        rowDiaChiTB.SONHA = this.sonhatb
        rowDiaChiTB.BLOCK = this.blocktb
        rowDiaChiTB.TANG = this.tangtb
        rowDiaChiTB.PHONG = this.sophongtb
        rowDiaChiTB.MOTA = this.motadctb
        rowDiaChiTB.DIACHI = this.$refs.txtDiaChiTB.value
        this.dsDiaChi.push(rowDiaChiTB)

        //Địa chỉ lắp đặt --> dataset dsdiachi
        rowDiachiLD.DIACHI_ID = diachild_id
        rowDiachiLD.TINH_ID = this.tinhld_id
        rowDiachiLD.QUAN_ID = this.quanld_id

        if (this.phuongld_id != 0) rowDiachiLD.PHUONG_ID = this.phuongld_id
        if (this.phold_id != 0) rowDiachiLD.PHO_ID = this.phold_id
        if (this.apld_id != 0) rowDiachiLD.AP_ID = this.apld_id
        if (this.khuld_id != 0) rowDiachiLD.KHU_ID = this.khuld_id
        if (this.dacdiemld_id != 0) rowDiachiLD.DACDIEM_ID = this.dacdiemld_id

        rowDiachiLD.SONHA = this.sonhald
        rowDiachiLD.SONHA = this.sonhald
        rowDiachiLD.BLOCK = this.blockld
        rowDiachiLD.TANG = this.tangld
        rowDiachiLD.PHONG = this.sophongld
        rowDiachiLD.MOTA = this.motadcld
        rowDiachiLD.DIACHI = this.$refs.txtDiachiLD.value
        this.dsDiaChi.push(rowDiachiLD)

        if (themmoi == true) {
          let rowDiaChiHDTB = {}
          rowDiaChiHDTB.DIACHI_ID = diachitb_id
          rowDiaChiHDTB.HDTB_ID = this.hdtb_id
          rowDiaChiHDTB.DIACHILD_ID = diachild_id
          this.dsDiaChiHDTB.push(rowDiaChiHDTB)
        }
      }
    },

    async TaoDuLieu_DiaChi_DS(hdtb_id, thuebao_id) {
      rs = await this.axios.post('web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid', {
        in_thuebao_id: thuebao_id
      })
      this.HienThiTTDiaChi(rs.data.data, 3)
      rs = await this.axios.get('web-hopdong/hopdong/lay_thongtin_chuyenquyen_theo_tbid', {
        params: {
          thueBaoId: thuebao_id
        }
      })
      let dsTT = rs.data.data

      if (dsTT != null && dsTT.length > 0) {
        let row = dsTT[0]
        let rowDiaChiTB = {}
        let rowDiachiLD = {}

        rs = await this.axios.post('web-hopdong/khoiphucthanhly/get_keys', {
          keyname: 'DIACHI'
        })
        this.diachitb_id = rs.data.data
        rs = await this.axios.post('web-hopdong/khoiphucthanhly/get_keys', {
          keyname: 'DIACHI'
        })
        this.diachild_id = rs.data.data

        //Địa chỉ thuê bao
        rowDiaChiTB.DIACHI_ID = diachitb_id
        if (this.tinhtb_id != 0) rowDiaChiTB.TINH_ID = this.tinhtb_id
        rowDiaChiTB.QUAN_ID = this.quantb_id

        if (this.phuongtb_id != 0) rowDiaChiTB.PHUONG_ID = this.phuongtb_id

        if (this.photb_id != 0) rowDiaChiTB.PHO_ID = this.photb_id
        if (this.aptb_id != 0) rowDiaChiTB.AP_ID = this.aptb_id
        if (this.khutb_id != 0) rowDiaChiTB.KHU_ID = this.khutb_id
        if (this.dacdiemtb_id != 0) rowDiaChiTB.DACDIEM_ID = this.dacdiemtb_id
        ///
        rowDiaChiTB.SONHA = this.sonhatb
        rowDiaChiTB.DIACHI = row['diachi_tb']
        this.dsDiaChi.push(rowDiaChiTB)

        //Địa chỉ lắp đặt --> dataset dsdiachi
        rowDiachiLD.DIACHI_ID = diachild_id
        rowDiachiLD.TINH_ID = this.tinhld_id
        rowDiachiLD.QUAN_ID = this.quanld_id

        if (this.phuongld_id != 0) rowDiachiLD.PHUONG_ID = this.phuongld_id
        if (this.phold_id != 0) rowDiachiLD.PHO_ID = this.phold_id
        if (this.apld_id != 0) rowDiachiLD.AP_ID = this.apld_id
        if (this.khuld_id != 0) rowDiachiLD.KHU_ID = this.khuld_id
        if (this.dacdiemld_id != 0) rowDiachiLD.DACDIEM_ID = this.dacdiemld_id

        rowDiachiLD.SONHA = this.sonhald
        rowDiachiLD.DIACHI = row['diachi_ld']
        this.dsDiaChi.push(rowDiachiLD)

        let rowDiaChiHDTB = {}
        rowDiaChiHDTB.DIACHI_ID = diachitb_id
        rowDiaChiHDTB.HDTB_ID = hdtb_id
        rowDiaChiHDTB.DIACHILD_ID = diachild_id
        this.dsDiaChiHDTB.push(rowDiaChiHDTB)
      }
    },
    //endregion

    //region Cap nhat HDTB_ID cho cac bang dangky_dvgt, dangky_dvk
    CapNhat_HDTB_ID_DangKyDV(hdtb_id) {
      let ds2 = dsDK_DVK
      dsDK_DVK = new DANGKY_DVK_DATA()
      // Không thực hiện thêm mới dịch vụ khác
      //cmUlt.CopyDataSet(ds2, dsDK_DVK);

      //for (let i = 0; i < dsDK_DVK.length; i++)
      //{
      //    dsDK_DVK[0][i]["hdtb_id"] = this.hdtb_id;
      //}
    },
    //endregion

    //region Tao du lieu cho bang HDTB_CD
    async TaoDuLieu_HDTB_CD(themmoi) {
      this.dsHDTB_CD = []
      let rowHDTB_CD = {}
      if (themmoi == false) {
        rs = await this.axios.get('web-hopdong/hopdong/lay_ds_hopdong_thuebao_codinh', {
          params: {
            hdtbId: hdtb_id
          }
        })
        rowHDTB_CD = rs.data.data[0]
      } else {
        rs = await this.axios.post('web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid', {
          thuebao_id: this.thuebao_id
        })
        rowHDTB_CD = rs.data.data[0]
      }

      rowHDTB_CD.HDTB_ID = hdtb_id
      rowHDTB_CD.TRANGBI_ID = this.trangbi_id
      //TuấnNA thêm code tạo dữ liệu muccuoctb_id -- hdtb_adsl -- ngày 13/12/2010

      //
      this.dsHDTB_CD.push(rowHDTB_CD)
    },

    //region Tao du lieu cho bang HDTB_CNTT
    async TaoDuLieu_HDTB_CNTT(themmoi) {
      rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_cntt', {
        params: {
          thueBaoId: this.thuebao_id
        }
      })
      this.dsHDTB_CNTT = rs.data.data[0]
    },
    //endregion

    async TaoDuLieu_HDTB_CD_DS(hdtb_id, thuebao_id) {
      this.dsHDTB_CD = []
      let rowHDTB_CD = {}
      rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_codinh', {
        params: {
          thueBaoId: thuebao_id
        }
      })

      rowHDTB_CD = rs.data.data[0]

      rowHDTB_CD.HDTB_ID = hdtb_id
      rowHDTB_CD.TRANGBI_ID = this.trangbi_id
      //TuấnNA thêm code tạo dữ liệu muccuoctb_id -- hdtb_adsl -- ngày 13/12/2010

      //
      this.dsHDTB_CD.push(rowHDTB_CD)
    },
    //endregion

    //region Tao du lieu cho bang HDTB_DD
    async TaoDuLieu_HDTB_DD(themmoi) {
      this.dsHDTB_DD = []
      let rowHDTB_DD = {}
      if (themmoi == false) {
        rs = await this.axios.post('/web-hopdong/thongtin_tratruoc_dn/lay_ds_hdtb_dd_theo_hdtb_id', {
          in_hdtb_id: this.hdtb_id
        })
        rowHDTB_DD = rs.data.data[0]
      } else {
        rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_dd', {
          params: {
            thueBaoId: this.thuebao_id
          }
        })

        rowHDTB_DD = rs.data.data[0]
      }

      rowHDTB_DD.HDTB_ID = this.hdtb_id
      /// TuanNA - Them dữ liệu vào dataset -- Ngày 20/07/2010
      rowHDTB_DD.GOICUOC_ID = Number(cboGoiCuocDD.value)
      rowHDTB_DD.DOITUONG_ID_NEO = Number(cboDTVinaPhone.value)
      rowHDTB_DD.SIMCARD = this.$refs.txtSimCard.value
      rowHDTB_DD.TRANGBI_ID = Number(this.$refs.cboTrangBiDD.value)
      //TuấnNA thêm code tạo dữ liệu muccuoctb_id -- hdtb_adsl -- ngày 13/12/2010
      //rowHDTB_DD.MUCUOCTB_ID = Number(this.$refs.cboMucCuocTB.value);
      //
      this.dsHDTB_DD.push(rowHDTB_DD)
    },

    async TaoDuLieu_HDTB_DD_DS(hdtb_id, thuebao_id) {
      this.dsHDTB_DD = []
      let rowHDTB_DD = {}
      rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_dd', {
        params: {
          thueBaoId: thuebao_id
        }
      })
      rowHDTB_DD = rs.data.data[0]

      rowHDTB_DD.HDTB_ID = hdtb_id
      /// TuanNA - Them dữ liệu vào dataset -- Ngày 20/07/2010
      rowHDTB_DD.GOICUOC_ID = Number(cboGoiCuocDD.value)
      rowHDTB_DD.DOITUONG_ID_NEO = Number(cboDTVinaPhone.value)
      rowHDTB_DD.SIMCARD = this.$refs.txtSimCard.value
      rowHDTB_DD.TRANGBI_ID = Number(this.$refs.cboTrangBiDD.value)
      //TuấnNA thêm code tạo dữ liệu muccuoctb_id -- hdtb_adsl -- ngày 13/12/2010
      //rowHDTB_DD.MUCUOCTB_ID = Number(this.$refs.cboMucCuocTB.value);
      //
      this.dsHDTB_DD.push(rowHDTB_DD)
    },
    //endregion

    //region Tao du lieu cho bang HDTB_ADSL
    async TaoDuLieu_HDTB_ADSL(themmoi) {
      dsHDTB_ADSL = new HDTB_ADSL_DATA()
      let rowHDTB_ADSL = dsHDTB_ADSL.HDTB_ADSL.NewHDTB_ADSLRow()

      if (themmoi == false) await tchopdongFacade().LAY_DS_HDTB_ADSL_THEO_HDTB_ID(this.hdtb_id)
      else cmUlt.CopyRow(await tcdanhba.LAY_DS_DB_ADSL_THEO_TBID(this.thuebao_id)[0], rowHDTB_ADSL)

      rowHDTB_ADSL.HDTB_ID = this.hdtb_id
      rowHDTB_ADSL.EMAIL = this.$refs.txtEmailAdsl.value
      rowHDTB_ADSL.TOCDO_ID = Number(cboGoiCuocAdsl.value)
      rowHDTB_ADSL.MUCCUOC_ID = Number(cboMucCuoc.value)
      rowHDTB_ADSL.TRANGBI_ID = Number(this.$refs.cboTrangBiADSL.value)
      dsHDTB_ADSL.HDTB_ADSL.AddHDTB_ADSLRow(rowHDTB_ADSL)
      if (themmoi == false) {
        dsHDTB_ADSL.AcceptChanges()
        dsHDTB_ADSL.HDTB_ADSL[0].SetModified()
      }
    },

    async TaoDuLieu_HDTB_ADSL_DS(hdtb_id, thuebao_id) {
      //dsHDTB_ADSL = new HDTB_ADSL_DATA();
      let rowHDTB_ADSL = dsHDTB_ADSL.HDTB_ADSL.NewHDTB_ADSLRow()

      rowHDTB_ADSL = await tcdanhba.LAY_DS_DB_ADSL_THEO_TBID(this.thuebao_id)[0]

      rowHDTB_ADSL.HDTB_ID = this.hdtb_id
      rowHDTB_ADSL.EMAIL = this.$refs.txtEmailAdsl.value
      rowHDTB_ADSL.TOCDO_ID = Number(cboGoiCuocAdsl.value)
      rowHDTB_ADSL.MUCCUOC_ID = Number(cboMucCuoc.value)
      rowHDTB_ADSL.TRANGBI_ID = Number(this.$refs.cboTrangBiADSL.value)
      dsHDTB_ADSL.HDTB_ADSL.AddHDTB_ADSLRow(rowHDTB_ADSL)
    },
    //endregion

    //region Tao du lieu cho bang DANGKY_DVGT
    TaoDuLieu_DKDV() {
      let dsDK_DVGT = []

      let kieu_dk_dvgt
      for (let dr in this.m_dsThueBao_DichVu) {
        if (dr['DANGKY'] == '1') kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT
        else if (dr['HUY'] == '1') kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT
        else {
          continue
        }

        let rowHDTDDV = {}

        rowHDTDDV.HDTB_ID = this.hdtb_id
        rowHDTDDV.NGAY_YC = this.$refs.dtpNgayYC.value
        rowHDTDDV.DICHVUGT_ID = Number(dr['DICHVUGT_ID'])
        rowHDTDDV.NOIDUNG = dr['NOIDUNG']
        rowHDTDDV.KIEU_YC = kieu_dk_dvgt
        rowHDTDDV.TTTH = 1

        dsDK_DVGT.DANGKY_DVGT.AddDANGKY_DVGTRow(rowHDTDDV)
      }
    },
    //endregion

    //region TaoDuLieu_HDTB_GP
    async TaoDuLieu_HDTB_GP(themmoi) {
      dsHDTB_GP = new HDTB_GP_DATA()
      let rowHDTB_GP = dsHDTB_GP.HDTB_GP.NewHDTB_GPRow()
      if (themmoi == false) rowHDTB_GP = await tchopdong.lay_ds_hdtb_GP_theo_hdtb_id(this.hdtb_id)[0]
      else rowHDTB_GP = await tcdanhba.LAY_DS_DB_GP_THEO_TBID(this.thuebao_id)[0]

      rowHDTB_GP.HDTB_ID = this.hdtb_id

      ///Tuấn thêm code tạo dữ liệu cho thue bao GPhone --- Ngày 12/09/2010
      rowHDTB_GP.SIMCARD = this.$refs.txtSosimGP.value

      if (this.$refs.chkVSVXGP.checked) rowHDTB_GP.MC_TBGP_ID = 1
      else rowHDTB_GP.MC_TBGP_ID = 0
      rowHDTB_GP.TRANGBI_ID = Number(this.$refs.cboTrangBiGP.value)
      dsHDTB_GP.HDTB_GP.AddHDTB_GPRow(rowHDTB_GP)
      if (themmoi == false) {
        dsHDTB_GP.AcceptChanges()
        dsHDTB_GP.HDTB_GP[0].SetModified()
      }
    },

    async TaoDuLieu_HDTB_GP_DS(hdtb_id, thuebao_id) {
      //dsHDTB_GP = new HDTB_GP_DATA();
      let rowHDTB_GP = dsHDTB_GP.HDTB_GP.NewHDTB_GPRow()

      rowHDTB_GP = await tcdanhba.LAY_DS_DB_GP_THEO_TBID(this.thuebao_id)[0]

      rowHDTB_GP.HDTB_ID = this.hdtb_id

      ///Tuấn thêm code tạo dữ liệu cho thue bao GPhone --- Ngày 12/09/2010
      rowHDTB_GP.SIMCARD = this.$refs.txtSosimGP.value

      if (this.$refs.chkVSVXGP.checked) rowHDTB_GP.MC_TBGP_ID = 1
      else rowHDTB_GP.MC_TBGP_ID = 0
      rowHDTB_GP.TRANGBI_ID = Number(this.$refs.cboTrangBiGP.value)
      dsHDTB_GP.HDTB_GP.AddHDTB_GPRow(rowHDTB_GP)
    },
    //endregion

    //region TaoDuLieu_HDTB_TSL
    async TaoDuLieu_HDTB_TSL(themmoi) {
      dsHDTB_TSL = new HDTB_TSL_DATA()
      let ds = []
      ds = await tcdanhba.LAY_DS_DB_TSL_THEO_TBID(this.thuebao_id)
      for (let i = 0; i < ds.length; i++) {
        let rowHDTB_TSL = dsHDTB_TSL.HDTB_TSL.NewHDTB_TSLRow()
        rowHDTB_TSL.DAUCUOI_ID = parseFloat(ds[i]['daucuoi_id'])
        rowHDTB_TSL.MA_TB_SUB = ds[i]['ma_tb_sub']
        rowHDTB_TSL.DIACHI = ds[i]['diachi']
        rowHDTB_TSL.CHUQUAN_ID = parseFloat(ds[i]['chuquan_id'])
        rowHDTB_TSL.TRANGBI_ID = parseFloat(ds[i]['trangbi_id'])
        rowHDTB_TSL.MA_PL = this.$refs.txtMaHD_TTKD_TSL.value
        rowHDTB_TSL.HDTB_ID = this.hdtb_id
        rowHDTB_TSL.TOCDO_ID = Number(ds[i]['tocdo_id'])
        rowHDTB_TSL.LOAIKENH_ID = Number(ds[i]['loaikenh_id'])
        dsHDTB_TSL.HDTB_TSL.AddHDTB_TSLRow(rowHDTB_TSL)
      }
      if (themmoi == false) {
        dsHDTB_TSL.AcceptChanges()
        dsHDTB_TSL.HDTB_TSL[0].SetModified()
        dsHDTB_TSL.HDTB_TSL[1].SetModified()
      }
    },

    async TaoDuLieu_HDTB_TSL_DS(hdtb_id, thuebao_id) {
      //dsHDTB_TSL = new HDTB_TSL_DATA();
      let ds = []
      ds = await tcdanhba.LAY_DS_DB_TSL_THEO_TBID(this.thuebao_id)
      for (let i = 0; i < ds.length; i++) {
        let rowHDTB_TSL = dsHDTB_TSL.HDTB_TSL.NewHDTB_TSLRow()
        rowHDTB_TSL.DAUCUOI_ID = parseFloat(ds[i]['daucuoi_id'])
        rowHDTB_TSL.MA_TB_SUB = ds[i]['ma_tb_sub']
        rowHDTB_TSL.DIACHI = ds[i]['diachi']
        rowHDTB_TSL.CHUQUAN_ID = parseFloat(ds[i]['chuquan_id'])
        rowHDTB_TSL.TRANGBI_ID = parseFloat(ds[i]['trangbi_id'])
        rowHDTB_TSL.MA_PL = this.$refs.txtMaHD_TTKD_TSL.value
        rowHDTB_TSL.HDTB_ID = this.hdtb_id
        rowHDTB_TSL.TOCDO_ID = Number(ds[i]['tocdo_id'])
        rowHDTB_TSL.LOAIKENH_ID = Number(ds[i]['loaikenh_id'])
        dsHDTB_TSL.HDTB_TSL.AddHDTB_TSLRow(rowHDTB_TSL)
      }
    },
    //endregion

    //region Tao du lieu cho bang HDTB_MGWAN
    TaoDuLieu_HDTB_MGWAN(themmoi) {
      dsHDTB_MEWAN = new HDTB_MGWAN_DATA()
      let rowHDTB_MGWAN = dsHDTB_MEWAN.HDTB_MGWAN.NewHDTB_MGWANRow()

      rowHDTB_MGWAN.HDTB_ID = this.hdtb_id
      rowHDTB_MGWAN.THOIHAN_ID = Number(this.$refs.cboThoihanWan.value)
      rowHDTB_MGWAN.TOCDO_ID = Number(this.$refs.cboTocDo.value)
      rowHDTB_MGWAN.TOCDO_PIR_ID = Number(this.$refs.cboTocDo_PIR.value)
      rowHDTB_MGWAN.MA_PL = this.$refs.txtMaHD_TTKD_MGW.value
      rowHDTB_MGWAN.SITE_ID = this.$refs.txtSiteID.value
      rowHDTB_MGWAN.LOAIMD_ID = Number(this.$refs.cboLoaiModem.value)
      rowHDTB_MGWAN.LOAIKENH_ID = Number(this.$refs.cboLoaiKenhWan.value)
      rowHDTB_MGWAN.THIETBIDC_ID = Number(this.$refs.cboLoaiTBiWan.value)
      rowHDTB_MGWAN.CHUQUAN_ID = Number(this.$refs.cboChuQuanWan.value)
      rowHDTB_MGWAN.TRANGBI_ID = Number(this.$refs.cboTrangBiWan.value)

      dsHDTB_MEWAN.HDTB_MGWAN.AddHDTB_MGWANRow(rowHDTB_MGWAN)
      if (themmoi == false) {
        dsHDTB_MEWAN.AcceptChanges()
        dsHDTB_MEWAN.HDTB_MGWAN[0].SetModified()
      }
    },

    TaoDuLieu_HDTB_MGWAN_DS(hdtb_id, thuebao_id) {
      //dsHDTB_MEWAN = new HDTB_MGWAN_DATA();
      let rowHDTB_MGWAN = dsHDTB_MEWAN.HDTB_MGWAN.NewHDTB_MGWANRow()

      rowHDTB_MGWAN.HDTB_ID = this.hdtb_id
      rowHDTB_MGWAN.THOIHAN_ID = Number(this.$refs.cboThoihanWan.value)
      rowHDTB_MGWAN.TOCDO_ID = Number(this.$refs.cboTocDo.value)
      rowHDTB_MGWAN.TOCDO_PIR_ID = Number(this.$refs.cboTocDo_PIR.value)
      rowHDTB_MGWAN.MA_PL = this.$refs.txtMaHD_TTKD_MGW.value
      rowHDTB_MGWAN.SITE_ID = this.$refs.txtSiteID.value
      rowHDTB_MGWAN.LOAIMD_ID = Number(this.$refs.cboLoaiModem.value)
      rowHDTB_MGWAN.LOAIKENH_ID = Number(this.$refs.cboLoaiKenhWan.value)
      rowHDTB_MGWAN.THIETBIDC_ID = Number(this.$refs.cboLoaiTBiWan.value)
      rowHDTB_MGWAN.CHUQUAN_ID = Number(this.$refs.cboChuQuanWan.value)
      rowHDTB_MGWAN.TRANGBI_ID = Number(this.$refs.cboTrangBiWan.value)

      dsHDTB_MEWAN.HDTB_MGWAN.AddHDTB_MGWANRow(rowHDTB_MGWAN)
    },
    //endregion

    //region Tao du lieu cho bang HDTB_IMS
    async TaoDuLieu_HDTB_IMS(themmoi) {
      dsHDTB_IMS = new HDTB_IMS_DATA()
      let rowHDTB_IMS = dsHDTB_IMS.HDTB_IMS.NewHDTB_IMSRow()

      if (themmoi == false) rowHDTB_IMS = await tchopdongFacade().LAY_DS_HDTB_IMS_THEO_HDTB_ID(this.hdtb_id)[0]
      else rowHDTB_IMS = await tcdanhba.LAY_DS_DB_IMS_THEO_TBID(this.thuebao_id)[0]
      rowHDTB_IMS.HDTB_ID = this.hdtb_id

      dsHDTB_IMS.HDTB_IMS.AddHDTB_IMSRow(rowHDTB_IMS)

      if (themmoi == false) {
        dsHDTB_IMS.AcceptChanges()
        dsHDTB_IMS.HDTB_IMS[0].SetModified()
      }
    },

    async TaoDuLieu_HDTB_IMS_DS(hdtb_id, thuebao_id) {
      //dsHDTB_IMS = new HDTB_IMS_DATA();
      let rowHDTB_IMS = dsHDTB_IMS.HDTB_IMS.NewHDTB_IMSRow()
      cmUlt.CopyRow(await tcdanhba.LAY_DS_DB_IMS_THEO_TBID(this.thuebao_id)[0], rowHDTB_IMS)
      rowHDTB_IMS.HDTB_ID = this.hdtb_id
      dsHDTB_IMS.HDTB_IMS.AddHDTB_IMSRow(rowHDTB_IMS)
    },
    //endregion

    //Hiếu bổ sung tạo dữ liệu cho bảng thông tin tt& Ct_TIEN_HD 15/09/2010
    /// <summary>
    /// TuấnNA thêm hàm tạo dữ liệu --- Ngày 16/09/2010
    /// </summary>
    /// <param name="themmoi"></param>
    //region Tạo dữ liệu cho bảng THONGTIN_TT
    TaoDuLieu_THONGTIN_TT(themmoi) {
      //try
      //{
      //    dsTTTT = new THONGTIN_TT_DATA();
      //    THONGTIN_TT_DATA.THONGTIN_TTRow rowTTTT = dsTTTT.THONGTIN_TT.NewTHONGTIN_TTRow();
      //    rowTTTT.HDTB_ID = this.hdtb_id;
      //    rowTTTT.TYLE_VAT = 10;
      //    khuvuc_id = "";
      //    khuvuc_id = "0";
      //    if (khuvuc_id)
      //        rowTTTT.KHUVUC_ID = Number(khuvuc_id);
      //    else
      //        rowTTTT.KHUVUC_ID = 0;
      //    rowTTTT.KIEUTT_ID = 1;
      //    dsTTTT.THONGTIN_TT.AddTHONGTIN_TTRow(rowTTTT);
      //    if (themmoi == false)
      //    {
      //        dsTTTT.AcceptChanges();
      //        dsTTTT.THONGTIN_TT[0].SetModified();
      //    }
      //}
      //catch (err)
      //{
      //    this.$toast.error(err.message);
      //}
    },
    //endregion
    //region Tạo dữ liệu cho bảng CT_TIENHD
    TaoDuLieu_CT_TIENHD(themmoi) {
      this.dsCTTHD = new CT_TIENHD_DATA()
      let rowCTTHD
      //Hiếu 15/09/2010
      //Inset, 4 loại tiền vào dabase
      //1: Tiền khoản mục (theo loại hợp đồng: đặt mới, chuyển quyền, chuyển dịch)
      //2: Tiền cài đặt (Áp dụng cho dịch vụ INTERNET,
      //nhapt--===============
      //for (let i = 0; i < 2; i++)
      //{
      //    //tiền lắp mới =1 + khuyến mại lắp mới
      //    if (i == 0)
      //    {
      //        rowCTTHD = dsCTTHD.CT_TIENHD.NewCT_TIENHDRow();
      //        rowCTTHD.HDTB_ID = this.hdtb_id;
      //        if (themmoi == true)
      //        {
      //            CT_TIENHD_ID = getkey.GetKey("CT_TIENHD");
      //            rowCTTHD.CT_TIENHD_ID = CT_TIENHD_ID;
      //        }
      //        //if (themmoi == false)
      //        //{
      //        //    CT_TIENHD_ID = Number(dtCTKM[0]["ct_tienhd_id"]);
      //        //    rowCTTHD.CT_TIENHD_ID = CT_TIENHD_ID;
      //        //}
      //        tien = "";//Tiền
      //        vat = "";//Vat
      //        kmld = "";
      //        rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_CHUYENQUYEN;
      //        tien = Common.Utilities.(txtTienCQ.value);
      //        rowCTTHD.TIEN = Number(tien);

      //        vat = Common.Utilities.(txtVATCQ.value);
      //        rowCTTHD.VAT = Number(vat);

      //        //kmld = Common.Utilities.(txtTienKMLD.value);
      //        //Tiền khuyến mại --- tuancomment
      //        //rowCTTHD.TIEN_KM = Number(kmld);
      //        kmld = "0";
      //        rowCTTHD.TIEN_KM = 0;
      //        rowCTTHD.VAT_KM = 0;
      //        //Tiền thực thu
      //        tien_tt = 0;
      //        tien_tt = Number(tien) - Number(kmld);
      //        rowCTTHD.TIEN_TT = tien_tt;
      //        let vat_tt = 0;
      //        vat_tt = parseFloat(0.1 * tien_tt);
      //        rowCTTHD.VAT_TT = vat_tt;
      //        rowCTTHD.LOAI_ID = 1;
      //        //rowCTTHD.DS_ID = KHOANMUC_TT.KMTT_DATMOI
      //        rowCTTHD.ID = KHOANMUC_TT.KMTT_CHUYENQUYEN;
      //        dsCTTHD.CT_TIENHD.AddCT_TIENHDRow(rowCTTHD);
      //    }
      //    //Tiền dịch vụ =4
      //    if (i == 1)
      //    {
      //        //if (themmoi == false)
      //        //{
      //        //    CT_TIENHD_ID = Number(dtCTKM[2]["ct_tienhd_id"]);
      //        //    rowCTTHD.CT_TIENHD_ID = CT_TIENHD_ID;
      //        //}
      //        //Edit by HungHM - 16/09/2010
      //        for (j = 0; j < this.m_dsThueBao_DichVu.length; j++)
      //        {
      //            rowCTTHD = dsCTTHD.CT_TIENHD.NewCT_TIENHDRow();
      //            rowCTTHD.HDTB_ID = this.hdtb_id;
      //            if (themmoi == true)
      //            {
      //                CT_TIENHD_ID = getkey.GetKey("CT_TIENHD");
      //                rowCTTHD.CT_TIENHD_ID = CT_TIENHD_ID;
      //            }
      //            if (this.m_dsThueBao_DichVu[j]["dangky"] == "1" && this.m_dsThueBao_DichVu[j]["huy"] == "0")
      //            {
      //                rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DICHVU;
      //                rowCTTHD.TIEN = parseFloat((this.m_dsThueBao_DichVu[j]["cuoc_ld"]));
      //                rowCTTHD.VAT = parseFloat((this.m_dsThueBao_DichVu[j]["vat_ld"]));
      //                rowCTTHD.ID = Number(this.m_dsThueBao_DichVu[j]["dichvugt_id"]);
      //                //Tiền khuyến mại
      //                rowCTTHD.TIEN_KM = 0;
      //                rowCTTHD.VAT_KM = 0;
      //                //Tiền thực thu
      //                rowCTTHD.TIEN_TT = parseFloat((this.m_dsThueBao_DichVu[j]["cuoc_ld"]));
      //                rowCTTHD.VAT_TT = parseFloat((this.m_dsThueBao_DichVu[j]["vat_ld"]));
      //                rowCTTHD.LOAI_ID = 2;
      //                dsCTTHD.CT_TIENHD.AddCT_TIENHDRow(rowCTTHD);
      //            }
      //        }
      //    }
      //}
      //nhapt================

      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DICHVU) {
          for (j = 0; j < this.m_dsThueBao_DichVu.length; j++) {
            if (this.m_dsThueBao_DichVu[j]['dangky'] == '1' && this.m_dsThueBao_DichVu[j]['huy'] == '0') {
              rowCTTHD = dsCTTHD.CT_TIENHD.NewCT_TIENHDRow()
              rowCTTHD.HDTB_ID = this.hdtb_id
              if (themmoi == true) {
                CT_TIENHD_ID = new GetKeyFacade().GetKey('CT_TIENHD')
                rowCTTHD.CT_TIENHD_ID = CT_TIENHD_ID
              }
              rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DICHVU
              let tien = 0 //Tiền dịch vụ chưa khuyến mại
              let vat = 0 //Vat dịch vụ chưa khuyến mại
              tien = parseFloat(this.m_dsThueBao_DichVu[j]['cuoc_ld'])
              rowCTTHD.TIEN = tien
              vat = parseFloat(this.m_dsThueBao_DichVu[j]['vat_ld'])
              rowCTTHD.VAT = vat
              rowCTTHD.ID = Number(this.m_dsThueBao_DichVu[j]['dichvugt_id'])
              rowCTTHD.LOAI_ID = 2
              dsCTTHD.CT_TIENHD.AddCT_TIENHDRow(rowCTTHD)
            }
          }
        } else {
          rowCTTHD = dsCTTHD.CT_TIENHD.NewCT_TIENHDRow()
          rowCTTHD.HDTB_ID = this.hdtb_id
          if (themmoi == true) {
            CT_TIENHD_ID = new GetKeyFacade().GetKey('CT_TIENHD')
            rowCTTHD.CT_TIENHD_ID = CT_TIENHD_ID
          }
          rowCTTHD.KHOANMUCTT_ID = Number(this.ds_tien_khoanmuc[i]['khoanmuctt_id'])
          rowCTTHD.TIEN = parseFloat(this.ds_tien_khoanmuc[i]['tien'])
          rowCTTHD.VAT = parseFloat(this.ds_tien_khoanmuc[i]['vat'])
          rowCTTHD.ID = Number(this.ds_tien_khoanmuc[i]['khoanmuctt_id'])
          rowCTTHD.LOAI_ID = 1
          dsCTTHD.CT_TIENHD.AddCT_TIENHDRow(rowCTTHD)
        }
      }
    },

    async TaoDuLieu_CT_TIENHD_DS(hdtb_id, thuebao_id) {
      let rowCTTHD
      temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao', {
        param: this.thuebao_id,
        type: 8
      })
      this.loaitb_id = Number(temp.data.data)
      this.m_dsThueBao_DichVu = await tchopdong.LAY_DS_THUEBAO_DICHVU(0, this.thuebao_id, loaitb_id, 1)

      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DICHVU) {
          for (j = 0; j < this.m_dsThueBao_DichVu.length; j++) {
            if (this.m_dsThueBao_DichVu[j]['dangky'] == '1' && this.m_dsThueBao_DichVu[j]['huy'] == '0') {
              rowCTTHD = dsCTTHD.CT_TIENHD.NewCT_TIENHDRow()
              rowCTTHD.HDTB_ID = this.hdtb_id

              CT_TIENHD_ID = new GetKeyFacade().GetKey('CT_TIENHD')
              rowCTTHD.CT_TIENHD_ID = CT_TIENHD_ID

              rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DICHVU
              let tien = 0 //Tiền dịch vụ chưa khuyến mại
              let vat = 0 //Vat dịch vụ chưa khuyến mại
              tien = parseFloat(this.m_dsThueBao_DichVu[j]['cuoc_ld'])
              rowCTTHD.TIEN = tien
              vat = parseFloat(this.m_dsThueBao_DichVu[j]['vat_ld'])
              rowCTTHD.VAT = vat
              rowCTTHD.ID = Number(this.m_dsThueBao_DichVu[j]['dichvugt_id'])
              rowCTTHD.LOAI_ID = 2
              dsCTTHD.CT_TIENHD.AddCT_TIENHDRow(rowCTTHD)
            }
          }
        } else {
          rowCTTHD = dsCTTHD.CT_TIENHD.NewCT_TIENHDRow()
          rowCTTHD.HDTB_ID = this.hdtb_id
          CT_TIENHD_ID = new GetKeyFacade().GetKey('CT_TIENHD')
          rowCTTHD.CT_TIENHD_ID = CT_TIENHD_ID

          rowCTTHD.KHOANMUCTT_ID = Number(this.ds_tien_khoanmuc[i]['khoanmuctt_id'])
          rowCTTHD.TIEN = parseFloat(this.ds_tien_khoanmuc[i]['tien'])
          rowCTTHD.VAT = parseFloat(this.ds_tien_khoanmuc[i]['vat'])
          rowCTTHD.ID = Number(this.ds_tien_khoanmuc[i]['khoanmuctt_id'])
          rowCTTHD.LOAI_ID = 1
          dsCTTHD.CT_TIENHD.AddCT_TIENHDRow(rowCTTHD)
        }
      }
    },
    //endregion

    //endregion

    //region ButtonClick
    async tsbtnGhiLai_Click() {
      if (!this.KiemTraDL_KhachHang()) {
        return false
      }

      if(!this.$refs.cboHTTT.value) {
        this.$toast.error('Chưa chọn hình thức thanh toán')
        return false
      }

      this.loading(true)
      if (this.$refs.tsbtnNhapMoi.classList.contains('disabled')) {
        this.P_ACTION = 0
      } else {
        this.P_ACTION = 1
      }
      // HDTB_DATA: [],
      var row_hdtb = {}
      if (this.P_ACTION == 0) {
        row_hdtb.HDTB_ID = null
        row_hdtb.KIEULD_ID = this.$refs.cboKieuLD.value
        row_hdtb.NGAY_SN = this.$refs.txtNgaySinhTB.value
        row_hdtb.GHICHU = this.$refs.txtGhiChuTB.value
      } else {
        row_hdtb.HDTB_ID = this.hdtb_id
        row_hdtb.KIEULD_ID = this.$refs.cboKieuLD.value
        row_hdtb.NGAY_SN = this.$refs.txtNgaySinhTB.value
        row_hdtb.GHICHU = this.$refs.txtGhiChuTB.value
      }
      row_hdtb.MA_TB = this.$refs.txtMaTB.value
      row_hdtb.TEN_TB = this.$refs.txtTenTB.value
      this.dsHDTB.push(row_hdtb)
      // HDKH_DATA: []
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      var row_hdkh = {}
      if (this.P_ACTION == 0) {
        row_hdkh.HDKH_ID = null
        row_hdkh.TEN_KH = this.$refs.txtTenKH.value
        row_hdkh.DIACHI_KH = this.$refs.txtDiaChiKH.value
        row_hdkh.NGUOI_DD = this.$refs.txtNguoiDD.value
        row_hdkh.CHUCDANH_DD = this.$refs.txtChucDanhDD.value
        row_hdkh.SO_DT = this.$refs.txtTelKH.value
        row_hdkh.SO_FAX = this.$refs.txtFaxKH.value
        row_hdkh.WEBSITE = ''
        row_hdkh.NGAY_SN = this.$refs.txtNgaySN.value
        row_hdkh.NGAYLAP_HD = this.$refs.dtpNgayLapHDNQ.value
        row_hdkh.SO_GT = this.$refs.txtSoGT.value
        row_hdkh.NOICAP = this.$refs.txtNoiCapHK.value
        row_hdkh.GHICHU = this.$refs.txtGhiChuKH.value
        row_hdkh.DONVI_ID = donviId
        row_hdkh.NGAYCAP = this.$refs.txtNgayCap.value
        row_hdkh.LOAIGT_ID = this.$refs.cboGiayToKH.value
        row_hdkh.NHANVIEN_ID = nhanvienId
        row_hdkh.KHLON_ID = this.$refs.cboKhachHangLon.value
        row_hdkh.LOAIHD_ID = LoaiHopDong.CHUYEN_QUYEN
        row_hdkh.KH_KT3 = null
        row_hdkh.MST_KH = this.$refs.txtMaSTKH.value
        row_hdkh.HOKHAU = this.$refs.txtHoKhau.value
        row_hdkh.LOAIKH_ID = this.$refs.cboLoaiKH.value
        row_hdkh.NOICAP_HK = this.txtNoiCapHK_KHNQ
        row_hdkh.NGAY_HK = this.$refs.txtNoiCapHK.value
        row_hdkh.NGAY_YC = this.$refs.dtpNgayYC.value
        row_hdkh.GIOITINH = null
        if (this.$refs.rdoGTNam.checked == true) {
          row_hdkh.GIOITINH = 0
        } else if (this.$refs.rdoGTNu.checked == true) {
          row_hdkh.GIOITINH = 1
        }
        row_hdkh.DANTOC_ID = this.$refs.cboDanToc.value ? this.$refs.cboDanToc.value : 1
        row_hdkh.QUOCTICH_ID = this.$refs.cboQuocTich.value ? this.$refs.cboQuocTich.value : 1
        row_hdkh.NGANHNGHE_ID = this.$refs.cboNganhNgheLoc.value
        row_hdkh.DIACHI_NDD = this.$refs.txtDiaChiNDD.value
        row_hdkh.NGAYHETHAN_GT = this.$refs.txtNgayHetHan.value
        row_hdkh.MST = this.$refs.txtMaSTKH.value
        row_hdkh.STK = this.$refs.txtSoTKKH.value
        row_hdkh.NGANHANG_ID = this.$refs.cboNganHangTT.value
        row_hdkh.CTV_ID = null
        row_hdkh.NHANVIENGT_ID = null
        if (this.$refs.chkCTV.checked && this.$refs.txtCTV.value) {
          row_hdkh.CTV_ID = this.ctv_id
        }
        if (this.$refs.chkNguoiGT.checked && this.$refs.txtNguoiGT.value) {
          row_hdkh.NHANVIENGT_ID = this.nguoigt_id
        }
        row_hdkh.TNC1_ID = this.TNC1_ID
        row_hdkh.TNC2_ID = this.TNC2_ID
        row_hdkh.TNC3_ID = this.TNC3_ID
        row_hdkh.TNC4_ID = this.TNC4_ID
        row_hdkh.DACTRUNG = this.$refs.txtDacTrung.value
        row_hdkh.KIEUHD_ID = null
        row_hdkh.HTTTHI_ID = null
        row_hdkh.PHANLOAIKH_ID = this.PHANLOAIKH_ID
        row_hdkh.EMAIL = this.$refs.txtEmail.value
        if (this.khachhang_id > 0) {
          row_hdkh = {}
          row_hdkh.KHACHHANG_ID = this.khachhang_id
          if (this.$refs.chkCTV.checked && this.$refs.txtCTV.value) {
            row_hdkh.CTV_ID = this.ctv_id
          }
          if (this.$refs.chkNguoiGT.checked && this.$refs.txtNguoiGT.value) {
            row_hdkh.NHANVIENGT_ID = this.nguoigt_id
          }
        }
      } else {
        row_hdkh = {}
        row_hdkh.TEN_KH = this.$refs.txtTenKH.value
        row_hdkh.DIACHI_KH = this.$refs.txtDiaChiKH.value
        row_hdkh.NGUOI_DD = this.$refs.txtNguoiDD.value
        row_hdkh.CHUCDANH_DD = this.$refs.txtChucDanhDD.value
        row_hdkh.SO_DT = this.$refs.txtTelKH.value
        row_hdkh.SO_FAX = this.$refs.txtFaxKH.value
        row_hdkh.WEBSITE = ''
        row_hdkh.NGAY_SN = this.$refs.txtNgaySN.value
        row_hdkh.NGAYLAP_HD = this.$refs.dtpNgayLapHDNQ.value
        row_hdkh.SO_GT = this.$refs.txtSoGT.value
        row_hdkh.NOICAP = this.$refs.txtNoiCap.value
        row_hdkh.GHICHU = this.$refs.txtGhiChuKH.value
        row_hdkh.DONVI_ID = donviId
        row_hdkh.NGAYCAP = this.$refs.txtNgayCap.value
        row_hdkh.LOAIGT_ID = this.$refs.cboGiayToKH.value
        row_hdkh.NHANVIEN_ID = nhanvienId
        row_hdkh.KHLON_ID = this.$refs.cboKhachHangLon.value
        row_hdkh.LOAIHD_ID = LoaiHopDong.CHUYEN_QUYEN
        row_hdkh.KH_KT3 = null
        row_hdkh.MST_KH = this.$refs.txtMaSTKH.value
        row_hdkh.HOKHAU = this.$refs.txtHoKhau.value
        row_hdkh.LOAIKH_ID = this.$refs.cboLoaiKH.value
        row_hdkh.NOICAP_HK = this.txtNoiCapHK_KHNQ
        row_hdkh.NGAY_HK = this.$refs.txtNoiCapHK.value
        row_hdkh.NGAY_YC = this.$refs.dtpNgayYC.value
        row_hdkh.GIOITINH = null
        if (this.$refs.rdoGTNam.checked == true) {
          row_hdkh.GIOITINH = 0
        } else if (this.$refs.rdoGTNu.checked == true) {
          row_hdkh.GIOITINH = 1
        }
        row_hdkh.DANTOC_ID = this.$refs.cboDanToc.value ? this.$refs.cboDanToc.value : 1
        row_hdkh.QUOCTICH_ID = this.$refs.cboQuocTich.value ? this.$refs.cboQuocTich.value : 1
        row_hdkh.NGANHNGHE_ID = this.$refs.cboNganhNgheLoc.value
        row_hdkh.DIACHI_NDD = this.$refs.txtDiaChiNDD.value
        row_hdkh.NGAYHETHAN_GT = this.$refs.txtNgayHetHan.value
        row_hdkh.MST = this.$refs.txtMaSTKH.value
        row_hdkh.STK = this.$refs.txtSoTKKH.value
        row_hdkh.NGANHANG_ID = this.$refs.cboNganHangTT.value
        row_hdkh.CTV_ID = null
        row_hdkh.NHANVIENGT_ID = null
        if (this.$refs.chkCTV.checked && this.$refs.txtCTV.value) {
          row_hdkh.CTV_ID = this.ctv_id
        }
        if (this.$refs.chkNguoiGT.checked && this.$refs.txtNguoiGT.value) {
          row_hdkh.NHANVIENGT_ID = this.nguoigt_id
        }
        row_hdkh.TNC1_ID = this.TNC1_ID
        row_hdkh.TNC2_ID = this.TNC2_ID
        row_hdkh.TNC3_ID = this.TNC3_ID
        row_hdkh.TNC4_ID = this.TNC4_ID
        row_hdkh.DACTRUNG = this.$refs.txtDacTrung.value
        row_hdkh.KIEUHD_ID = null
        row_hdkh.HTTTHI_ID = null
        row_hdkh.PHANLOAIKH_ID = this.PHANLOAIKH_ID
        row_hdkh.EMAIL = this.$refs.txtEmail.value
        row_hdkh.HDKH_ID = this.hdkh_id
      }

      console.log(row_hdkh)
      this.dsHDKH.push(row_hdkh)
      // HDTT_DATA: []
      var row_hdtt = {}
      if (this.P_ACTION == 0) {
        if (!this.$refs.txtMaTT.value || !this.thanhtoan_id) {
          row_hdtt.HDTT_ID = null
          row_hdtt.THANHTOAN_ID = null
          row_hdtt.DONVI_ID = this.$refs.cboDonviQL.value
          row_hdtt.THANHTOAN_ID = null
          row_hdtt.TEN_TT = this.$refs.txtTenTT.value
          row_hdtt.SO_DT = this.$refs.tbDienThoaiLienHe.value
          row_hdtt.EMAIL = this.$refs.txtEmail.value
          row_hdtt.DIACHI_TT = this.$refs.txtDiaChiTT.value
          row_hdtt.DIACHI_BC = this.$refs.txtDiaChiBC.value
          row_hdtt.DIACHI_CT = this.$refs.txtDiaChiCT.value
          row_hdtt.MST = this.$refs.txtMST_TT.value
          row_hdtt.HTTT_ID = this.$refs.cboHTTT.value
          row_hdtt.STK = this.$refs.txtSTK_TT.value
          row_hdtt.CHU_TK = this.$refs.txtChu_TK.value
          row_hdtt.NGANHANG_ID = this.$refs.cboNganHangTT.value
          row_hdtt.TUYENTHU_ID = this.tuyenthu_id
          row_hdtt.MATB_DD = this.$refs.cboMaDD.value
          row_hdtt.GHICHU = this.$refs.txtGhiChuTT.value
          row_hdtt.DV_QHNS = this.DV_QHNS
          row_hdtt.MACHUONG = this.MACHUONG
          row_hdtt.MN_KT = this.MN_KT
          row_hdtt.MA_NDKT = this.MA_NDKT
          row_hdtt.MN_NSNN = this.MN_NSNN
          row_hdtt.TIEN_DM = this.TIEN_DM
        } else {
          row_hdtt.THANHTOAN_ID = this.thanhtoan_id
        }
      } else {
        row_hdtt.HDTT_ID = this.hdtt_id
        row_hdtt.THANHTOAN_ID = this.thanhtoan_id
        row_hdtt.DONVI_ID = this.$refs.cboDonviQL.value
        row_hdtt.THANHTOAN_ID = this.thanhtoan_id
        row_hdtt.TEN_TT = this.$refs.txtTenTT.value
        row_hdtt.SO_DT = this.$refs.tbDienThoaiLienHe.value
        row_hdtt.EMAIL = this.$refs.txtEmail.value
        row_hdtt.DIACHI_TT = this.$refs.txtDiaChiTT.value
        row_hdtt.DIACHI_BC = this.$refs.txtDiaChiBC.value
        row_hdtt.DIACHI_CT = this.$refs.txtDiaChiCT.value
        row_hdtt.MST = this.$refs.txtMST_TT.value
        row_hdtt.HTTT_ID = this.$refs.cboHTTT.value
        row_hdtt.STK = this.$refs.txtSTK_TT.value
        row_hdtt.CHU_TK = this.$refs.txtChu_TK.value
        row_hdtt.NGANHANG_ID = this.$refs.cboNganHangTT.value
        row_hdtt.TUYENTHU_ID = this.tuyenthu_id
        row_hdtt.MATB_DD = this.$refs.cboMaDD.value
        row_hdtt.GHICHU = this.$refs.txtGhiChuTT.value
        row_hdtt.DV_QHNS = this.DV_QHNS
        row_hdtt.MACHUONG = this.MACHUONG
        row_hdtt.MN_KT = this.MN_KT
        row_hdtt.MA_NDKT = this.MA_NDKT
        row_hdtt.MN_NSNN = this.MN_NSNN
        row_hdtt.TIEN_DM = this.TIEN_DM
        row_hdtt.MA_TT = this.$refs.txtMaTT.value
      }
      this.dsHDTT.push(row_hdtt)
      // HDKHSUB_DATA: []
      var row_hdkh_sub = {}
      // DIACHI_HDKH_DATA: []
      var row_diachikh = {}
      if (this.P_ACTION == 0) {
        row_diachikh.DIACHI_ID = null
        row_diachikh.DIACHI = this.$refs.txtDiaChiKH.value
        row_diachikh.TINH_ID = this.tinhkh_id
        row_diachikh.QUAN_ID = this.quankh_id
        row_diachikh.PHUONG_ID = this.phuongkh_id
        row_diachikh.PHO_ID = this.phokh_id
        row_diachikh.AP_ID = this.apkh_id
        row_diachikh.KHU_ID = this.khukh_id
        row_diachikh.SONHA = this.sonhakh
        row_diachikh.DACDIEM_ID = this.dacdiemkh_id
      }
      this.dsDiaChiHDKH.push(row_diachikh)
      // DIACHI_HDTT_DATA: []
      var row_diachitt = {}
      if (this.P_ACTION == 0) {
        row_diachitt.DIACHI_ID = null
        row_diachitt.DIACHI = this.$refs.txtDiaChiTT.value
        row_diachitt.TINH_ID = this.tinhtt_id
        row_diachitt.QUAN_ID = this.quantt_id
        row_diachitt.PHUONG_ID = this.phuongtt_id
        row_diachitt.PHO_ID = this.phott_id
        row_diachitt.AP_ID = this.aptt_id
        row_diachitt.KHU_ID = this.khutt_id
        row_diachitt.SONHA = this.sonha_tt
        row_diachitt.DACDIEM_ID = this.dacdiemtt_id
      }
      this.dsDiaChiHDTT.push(row_diachitt)
      // DIACHI_HDTB_DATA: []
      var row_diachitb = {}
      if (this.P_ACTION == 0) {
        row_diachitb.DIACHI_ID = null
        row_diachitb.DIACHI = this.$refs.txtDiaChiTB.value
        row_diachitb.TINH_ID = this.tinhtb_id
        row_diachitb.QUAN_ID = this.quantb_id
        row_diachitb.PHUONG_ID = this.phuongtb_id
        row_diachitb.PHO_ID = this.photb_id
        row_diachitb.AP_ID = this.aptb_id
        row_diachitb.KHU_ID = this.khutb_id
        row_diachitb.SONHA = this.sonhatb
        row_diachitb.DACDIEM_ID = this.dacdiemtb_id
      }

      this.dsDiaChiHDTB.push(row_diachitb);
      let rowCTTHD = [];

      // CHITIET_TIENHD_DATA[]
      var row_tienhd = {};

      for(var km of this.ds_tien_khoanmuc) {
        row_tienhd = {};
        row_tienhd.TIEN = km.tien;
        row_tienhd.VAT = km.vat;
        row_tienhd.TYLE_VAT = null;
        row_tienhd.TYLE_VAT_ID = null;
        row_tienhd.KHOANMUCTT_ID = km.khoanmuctt_id
        rowCTTHD.push(row_tienhd)
      }

      // this.CHITIET_TIENHD_DATA.push(row_tienhd)
      var pdata = {
        cmd: '',
        action: this.P_ACTION,
        thuebao_cq_id: this.thuebao_id,
        js_hdtb_data: JSON.stringify(row_hdtb),
        js_hdkh_data: JSON.stringify(row_hdkh),
        js_hdtt_data: JSON.stringify(row_hdtt),
        js_hdkh_sub_data: JSON.stringify({}),
        js_diachi_hdkh_data: JSON.stringify(row_diachikh),
        js_diachi_hdtt_data: JSON.stringify(row_diachitt),
        js_diachi_hdtb_data: JSON.stringify(row_diachitb),
        js_ct_tienhd_data: JSON.stringify(rowCTTHD),
      };
      api
        .postChuyenQuyen_v2(this.axios, JSON.stringify(pdata))
        .then(async (response) => {
          if (
            response &&
            response.data &&
            response.data.message.split("_")[0].toUpperCase() == "OK"
          ) {
            this.$toast.success("Cập nhật thông tin chuyển quyền thành công!");
            this.$refs.txtMaGD.value = response.data.message.split("_")[1];
            this.nopopup = true;
            this.txtMaGD_KeyPress();
            // temp = await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
            // await this.HienThiDanhSacHDTB(temp);
            // temp = await tchopdong.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id)
            // await this.HienThiDanhSacHDTT(temp);
            // await this.HienThiDS_HDTT_CBO(this.hdkh_id);
          } else {
            let message = response.data.message.replaceAll("\\r\\n", "\n");
            console.log(message, "trả về");
            this.$toast.error(message);
            this.loading(false);
          }
        });
    },

    async TraCuu_No_MaTB(ma_tb) {
      return new Promise(async (resolve, reject) => {
        let result = 0
        try {
          if (this.thuebao_id != 0) {
            let no_tb = ''
            rs = await this.axios.get('web-hopdong/hopdong/tracuu_no_thuebao', {
              params: {
                thueBaoId: this.thuebao_id
              }
            })
            no_tb = rs.data.data
            result = Number(no_tb)
          } else {
            this.$toast.error('Chưa có thông tin về thuê bao!')
          }
        } catch (err) {
          reject(false)
          console.log(err)
          this.$toast.error('Có lỗi:' + err.message)
        }
        resolve(result)
      })
    },

    tsbtnThemTT_Click() {
      if (!this.$refs.tsbtnGhiLai.classList.contains('disabled')) {
        this.P_ACTION = 0
      } else {
        this.P_ACTION = 1
      }
      // HDTB_DATA: [],
      var row_hdtb = {}
      // HDKH_DATA: []
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      var row_hdkh = {
        HDKH_ID: this.hdkh_id
      }
      // HDTT_DATA: []
      var row_hdtt = {}
      if (this.thanhtoan_id > 0) {
        row_hdtt.THANHTOAN_ID = this.thanhtoan_id
      } else {
        row_hdtt.HDTT_ID = null
        row_hdtt.THANHTOAN_ID = this.thanhtoan_id
        row_hdtt.DONVI_ID = this.donviql_id
        row_hdtt.THANHTOAN_ID = this.thanhtoan_id
        row_hdtt.TEN_TT = this.$refs.txtTenTT.value
        row_hdtt.SO_DT = this.$refs.tbDienThoaiLienHe.value
        row_hdtt.EMAIL = this.$refs.txtEmail.value
        row_hdtt.DIACHI_TT = this.$refs.txtDiaChiTT.value
        row_hdtt.DIACHI_BC = this.$refs.txtDiaChiBC.value
        row_hdtt.DIACHI_CT = this.$refs.txtDiaChiCT.value
        row_hdtt.MST = this.$refs.txtMST_TT.value
        row_hdtt.HTTT_ID = this.$refs.cboHTTT.value
        row_hdtt.STK = this.$refs.txtSTK_TT.value
        row_hdtt.CHU_TK = this.$refs.txtChu_TK.value
        row_hdtt.NGANHANG_ID = this.$refs.cboNganHangTT.value
        row_hdtt.TUYENTHU_ID = this.tuyenthu_id
        row_hdtt.MATB_DD = this.$refs.cboMaDD.value
        row_hdtt.GHICHU = this.$refs.txtGhiChuTT.value
        row_hdtt.DV_QHNS = this.DV_QHNS
        row_hdtt.MACHUONG = this.MACHUONG
        row_hdtt.MN_KT = this.MN_KT
        row_hdtt.MA_NDKT = this.MA_NDKT
        row_hdtt.MN_NSNN = this.MN_NSNN
        row_hdtt.TIEN_DM = this.TIEN_DM
      }
      // HDKHSUB_DATA: []
      var row_hdkh_sub = {}
      // DIACHI_HDKH_DATA: []
      var row_diachikh = {}
      // DIACHI_HDTT_DATA: []
      var row_diachitt = {}
      if (this.P_ACTION == 0) {
        row_diachitt.DIACHI_ID = null
        row_diachitt.DIACHI = this.$refs.txtDiaChiTT.value
        row_diachitt.TINH_ID = this.tinhtt_id
        row_diachitt.QUAN_ID = this.quantt_id
        row_diachitt.PHUONG_ID = this.phuongtt_id
        row_diachitt.PHO_ID = this.phott_id
        row_diachitt.AP_ID = this.aptt_id
        row_diachitt.KHU_ID = this.khutt_id
        row_diachitt.SONHA = this.sonha_tt
        row_diachitt.DACDIEM_ID = this.dacdiemtt_id
        row_diachitt.KINHDO = null
        row_diachitt.VIDO = null
      }
      // DIACHI_HDTB_DATA: []
      var row_diachitb = {}

      // CHITIET_TIENHD_DATA[]
      var row_tienhd = {}

      var pdata = {
        action: this.P_ACTION,
        thuebao_cq_id: this.thuebao_id,
        js_hdtb_data: JSON.stringify(row_hdtb),
        js_hdkh_data: JSON.stringify(row_hdkh),
        js_hdtt_data: JSON.stringify(row_hdtt),
        js_hdkh_sub_data: JSON.stringify(row_hdkh_sub),
        js_diachi_hdkh_data: JSON.stringify(row_diachikh),
        js_diachi_hdtt_data: JSON.stringify(row_diachitt),
        js_diachi_hdtb_data: JSON.stringify(row_diachitb),
        js_ct_tienhd_data: JSON.stringify(row_tienhd)
      }
      api.postChuyenQuyen(this.axios, JSON.stringify(pdata)).then((response) => {
        if (response && response.data && response.data.message.split('_')[0].toUpperCase() == 'OK') {
          this.$toast.success('Thêm thanh toán mới thành công!')
          this.HienThiDS_HDTT_CBO(this.hdkh_id)
          this.HienThiDanhSacHDTT(tchopdong.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id))
        } else {
          this.$toast.error(response.data.message)
        }
      })
    },
    async tsbtnThemTB_Click() {
      var row_tienhd = {}
      row_tienhd.TIEN = this.$refs.txtTongTien.value
      row_tienhd.VAT = this.$refs.txtTongVat.value
      row_tienhd.KHOANMUCTT_ID = this.khoanmuctt_id
      row_tienhd.TYLE_VAT = null
      row_tienhd.TYLE_VAT_ID = null

      var row_hdtb = {}
      row_hdtb.TEN_TB = this.$refs.txtTenTB.value
      if (this.P_ACTION == 0) {
        row_hdtb.HDTB_ID = null
        row_hdtb.KIEULD_ID = this.$refs.cboKieuLD.value
        row_hdtb.NGAY_SN = this.$refs.txtNgaySinhTB.value
        row_hdtb.GHICHU = this.$refs.txtGhiChuTB.value
      } else {
        row_hdtb.HDTB_ID = this.hdtb_id
        row_hdtb.KIEULD_ID = this.$refs.cboKieuLD.value
        row_hdtb.NGAY_SN = this.$refs.txtNgaySinhTB.value
        row_hdtb.GHICHU = this.$refs.txtGhiChuTB.value
      }

      var row_diachitb = {}
      if (this.P_ACTION == 0) {
        row_diachitb.DIACHI_ID = null
        row_diachitb.DIACHI = this.$refs.txtDiaChiTB.value
        row_diachitb.TINH_ID = this.tinhtb_id
        row_diachitb.QUAN_ID = this.quantb_id
        row_diachitb.PHUONG_ID = this.phuongtb_id
        row_diachitb.PHO_ID = this.photb_id
        row_diachitb.AP_ID = this.aptb_id
        row_diachitb.KHU_ID = this.khutb_id
        row_diachitb.SONHA = this.sonhatb
        row_diachitb.DACDIEM_ID = this.dacdiemtb_id
      }
      try {
        this.loading(true)
        let response = await this.axios.post('web-hopdong/hopdong/khac/sp_chuyenquyen_them_tb_v2', {
          cmd: '',
          ct_tienhd_data: JSON.stringify(row_tienhd),
          diachi_hdtb_data: JSON.stringify(row_diachitb),
          ds_para: JSON.stringify({
            THUEBAO_ID: this.thuebao_id,
            MA_TB_CU: this.$refs.txtMaTBCu.value,
            HDKH_ID: this.hdkh_id,
            HDTT_ID: this.hdtt_id,
            KIEULD_ID: this.$refs.cboKieuLD.value,
            NGUOI_CN: this.$root.token.getUserName(),
            MAY_CN: await this.$root.token.getMachine(),
            IP_CN: await this.$root.token.getIP()
          }),
          hd_thuebao_data: JSON.stringify(row_hdtb)
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          this.$toast.success('Thêm thuê bao vào vào viễn thông tỉnh thành công!')
          this.HienThiDanhSacHDTB(await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id))
          this.ma_tb = this.$refs.txtMaTB.value
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          } else {
            this.$toast.error('Đã xảy ra lỗi, thêm thuê bao không thành công!')
          }
        }
      } catch (e) {
        this.loading(false)
        if (e.data && e.data.message) {
          this.$toast.error(e.data.message)
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$toast.error(e.response.data.message)
        } else {
          this.$toast.error(e.message)
        }
      }
    },

    tsbtnThoat_Click() {
      Close()
    },

    async tsbtnXoa_Click() {
      this.$confirm('Bạn thật sự muốn xóa hợp đồng không ?', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy',
        type: 'success'
      }).then(async () => {
        let check = await this.axios.post('web-hopdong/hopdong/get_app_config', {
          keyname: 'OPEN_SYNC_VINA'
        })
        if (check && check.data && check.data.data.keyvalue == 'TRUE') {
          //region Xoá hợp đồng trên vinaphone
          if (this.check_tb_dd) {
            let ma_gd_neo = this.$refs.txtMaGD.value
            let deleteHDCG = await this.axios.post('web-hopdong/tachnhap_thuebao/Delete_HDCQ_VinaPhone', {
              ma_gd: ma_gd_neo
            })
            if (deleteHDCG && deleteHDCG.data && deleteHDCG.data.data == 'OK') {
              this.$toast.success('Đã xóa thành công hợp đồng/phụ lục có mã giao dịch: ' + this.$refs.txtMaGD.value + ' trên VinaPhone.')
            } else {
              this.$toast.error('Có lỗi trong quá trình xóa hợp đồng/phụ lục có mã giao dịch: ' + this.$refs.txtMaGD.value + ' trên VinaPhone.')
              return
            }
          }
          //endregion
        }
        this.XoaHDKH()
      })
    },

    async tsbtnXoaTT_Click() {
      //Hiếu - 25.09.2010: Kiểm tra trong hd thanh toán có hd thuê bao nào không, nếu có thì ko cho xóa
      let kt = await this.axios.get('web-hopdong/hopdong/kiemtra_hdtb_theo_hdtt', {
        params: {
          hdttId: this.hdtt_id
        }
      })

      if (kt.data && kt.data.data > 0) {
        this.$toast.error('Thanh toán ' + this.$refs.txtMaTT.value + ' đã có thuê bao! Hãy xóa thuê bao trước.')
      } else {
        this.$confirm(`Bạn thật sự muốn xóa thanh toán không ?`, {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'warning'
        }).then(async () => {
          if (this.check_tb_dd) {
            let ma_gd_neo = this.$refs.txtMaGD.value
            let deleteHDCG = await this.axios.post('web-hopdong/tachnhap_thuebao/Delete_HDCQ_VinaPhone', {
              ma_gd: ma_gd_neo
            })
            if (deleteHDCG && deleteHDCG.data && deleteHDCG.data.data == 'OK') {
              this.$toast.error('Đã xóa thành công hợp đồng/phụ lục có mã giao dịch: ' + this.$refs.txtMaGD.value + ' trên VinaPhone.')
            } else {
              this.$toast.error('Có lỗi trong quá trình xóa hợp đồng/phụ lục có mã giao dịch: ' + this.$refs.txtMaGD.value + ' trên VinaPhone.')
              return
            }
          }

          let rs = await this.axios.post('web-hopdong/hopdong/xoa_hopdong_thanhtoan?hdttId=' + this.hdtt_id)

          this.$toast.success('Xóa thanh toán thành công!')

          this.HienThiDS_HDTT_CBO(this.hdkh_id)
          let dsHopdong = await this.axios.get('web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh', {
            params: {
              hdKhId: this.hdkh_id
            }
          })
          this.HienThiDanhSacHDTT(dsHopdong.data ? dsHopdong.data.data : [])
        })
      }
    },

    async tsbtnXoaTB_Click() {
      try {
        await this.axios.post('/web-hopdong/khoiphucthanhly/xoa_hdtb', {
          hdtb_id: this.hdtb_id
        })
        this.HienThiDanhSacHDTB(await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id))
        if (this.$refs.lvwThueBao.dataSource.length > 0) {
          try {
            var count = 0
            this.$refs.lvwThueBao.dataSource.forEach((item) => {
              if (item.dichvuvt_id == DichVuVienThong.DI_DONG) {
                count++
              }
            })

            if (count > 0) {
              if (this.check_tb_dd) {
                let ma_gd_neo = this.$refs.txtMaGD.value
                let deleteHDCG = await this.axios.post('web-hopdong/tachnhap_thuebao/Delete_HDCQ_VinaPhone', {
                  ma_gd: ma_gd_neo
                })
                if (deleteHDCG && deleteHDCG.data && deleteHDCG.data.data == 'OK') {
                  this.$toast.success('Đã xóa thành công hợp đồng/phụ lục có mã giao dịch: ' + this.$refs.txtMaGD.value + ' trên VinaPhone.')
                } else {
                  this.$toast.error('Có lỗi trong quá trình xóa hợp đồng/phụ lục có mã giao dịch: ' + this.$refs.txtMaGD.value + ' trên VinaPhone.')
                  return
                }
              }
            } else {
              let deleteTBCQ = await this.axios.post('web-hopdong/tachnhap_thuebao/Delete_TBCQ_VinaPhone', {
                ma_tb: this.ma_tb,
                mahd_neo: ma_gd_neo
              })
              if (deleteTBCQ && deleteTBCQ.data && deleteTBCQ.data.data == 'OK') {
                this.$toast.error('Đã xóa thành công số thuê bao:: ' + this.dsHDTB[0]['ma_tb'] + ' trên VinaPhone.')
              } else {
                this.$toast.error('Không thể xóa thuê bao: ' + this.dsHDTB[0]['ma_tb'] + ' trên VinaPhone.')
                return
              }
            }
          } catch (err) {
            console.log(err)
          }

          this.hdtb_id = Number(this.$refs.lvwThueBao.getSelectedRecords()[0].hdtb_id)
          this.HienThiTTHopDongTB(await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.hdtb_id))
          // HienThiTienTheoKhoanMuc(hdtb_id, tien_cd, loaihd_id, dichvuvt_id, khlon_id, loaitb_id, kieuld_id, doituong_id, ngaylhd, nhaphd_kt, phuongld_id);
        }
      } catch (err) {
        console.log(err)
      }
    },

    tsbtnNhapMoi_Click() {
      this.NhapMoi()
    },

    NhapMoi() {
      this.SetButton(1)
    },

    tsbtnHuyBo_Click() {
      this.SetButton(0)
    },
    //endregion

    //region Cập nhật đồng bộ VNP
    async CAPNHAT_HD_VNP() {
      // CuongLT
      try {
        //HungHM - 12/07/2010: Nếu dv = di đông và loại = trả sau thì đẩy dữ liệu lên VinaPhone
        if (this.CURRENT_DVVT_ID == DichVuVienThong.DI_DONG && this.CURRENT_LOAITB_ID() == LoaiHinhTB.DIDONGTRASAU) {
          result

          PSUSERID = this.tt_nd.USER_NEO
          PSMA_TINH = ma_tinh
          PSMA_HD = dsHDKH[0]['ma_gd'] // Mã hợp đồng 15 ký tự
          PSMAKH_CU = this.ma_tt_cu // ma thanh toan cua khach hang chuyen quyen (ma cu)
          PSMAKH_MOI = dsHDTT[0]['ma_tt']
          PSDIACHI_CT = dsHDKH[0]['diachi_kh'] //Dia chi khach hang nhan quyen
          PSTEN_TT = dsHDTT[0]['ten_tt'] // ten thanh toan nhan quyen
          PSDIACHI_TT = dsHDTT[0]['diachi_tt'] // dia chi thanh toan nha quyen
          PSSO_GT = dsHDKH[0]['so_gt'] // nhan quyen
          PSDIENTHOAI_LH = dsHDKH[0]['so_dt'] // nhan quyen
          PSMS_THUE = dsHDTT[0]['mst'] // nhan quyen thanh toan
          PSTAIKHOAN = dsHDTT[0]['stk'] // nhan quyen - thanh toan
          PICUOC_DV = dtien_dv // txtTienDV.value; //"0";// bo sung sau // đã sửa
          PIVAT = dvat_dv // txtVATDV.value;//"0";//bo sung sau// đã sửa
          PSGHICHU = dsHDTT[0]['ghichu'] // ghi chu thanh toan await checkdata.MAP_ID("loaigt_id_neo", DatabaseConstants.DB2 + ".loai_gt", "where loaigt_id =" + dsHDKH[0]["loaigt_id"]);
          PDNGAY_LAPHD = dsHDKH[0]['ngaylap_hd']
          PITRANGTHAIHD_ID = '1'
          //PDNGAY_TT = (dsHDKH[0]["ngay_yc"]) //Ngay yeu cau -> format
          temp
          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hinhthuc_tt', {
            param: dsHDTT[0]['httt_id'],
            type: 1
          })
          PIDIADIEMTT_ID = temp.data.data
          if (PIDIADIEMTT_ID == '-1') {
            this.$toast.error('Thông tin hình thức thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
            return false
          }
          PINGANHANG_ID = ''
          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_kh', {
            param: dsHDKH[0]['loaikh_id'],
            type: 2
          })
          PILOAIKH_ID = temp.data.data
          if (PILOAIKH_ID == '-1') {
            this.$toast.error('Thông tin loại khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
            return false
          }
          // PSNGUOI_CN = CommonFunction.ChuanHoa_XML(await checkdata.MAP_ID("user_neo", DatabaseConstants.DB1 + ".nguoidung", "where nguoidung_id =" + this.tt_nd.nguoidung_id));

          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_quan', {
            param: this.quankh_id,
            type: 1
          })
          PIQUAN_ID = temp.data.data
          if (PIQUAN_ID == '-1') {
            this.$toast.error('Thông tin Quận/Huyện xóm của địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
            return false
          }

          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_phuong', {
            param: this.quankh_id,
            param1: this.phuongkh_id,
            type: 1
          })
          PIPHUONG_ID = temp.data.data
          if (PIPHUONG_ID == '-1') {
            this.$toast.error('Thông tin Phường/Xã xóm của địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
            return false
          }

          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_pho', {
            param: this.phuongkh_id,
            param1: this.phokh_id,
            type: 1
          })
          PIPHO_ID = temp.data.data
          if (PIPHO_ID == '-1') {
            this.$toast.error('Thông tin Phố/Thôn xóm của địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
            return false
          }

          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_gt', {
            param: dsHDKH[0]['loaigt_id'],
            type: 1
          })
          PILOAIGT_ID = temp.data.data
          if (PILOAIGT_ID == '-1') {
            this.$toast.error('Thông tin loại giấy tờ chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
            return false
          }

          PSCOQUAN = dsHDKH[0]['ten_kh']
          PSSODAIDIEN = ''
          if (!dsHDTT[0]['matb_dd']) {
            PSSODAIDIEN = dsHDTB[0]['ma_tb']
          } else {
            PSSODAIDIEN = dsHDTT[0]['matb_dd']
          }
          PSSO_NHA = this.sonhakh
          PIMA_CQ = '' // Bo sung sau// Đã sửa
          //PSMA_BC = dsHDTT[0]["donvi_id"]// "1";// Mã khu vực thu ---> Tuấn sửa ngày 30/09/2010

          //PSMA_BC = "";
          //if (this.tt_nd.donvi_dl_id == 0) PSMA_BC = "1";
          //else PSMA_BC = this.tt_nd.donvi_dl_id
          //Tuấn NA sửa PSMA_BC theo nghiệp vụ của HGiang --- Ngày 04/11/2010
          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi', {
            param: dsHDTT[0]['donvi_id'],
            type: 3
          })
          PSMA_BC = temp.data.data
          if (PSMA_BC == '-1') {
            this.$toast.error('Thông tin mã bưu cục chưa đồng bộ với dữ liệu VinaPhone.\nBạn không thể tiếp tục cập nhật dữ liệu.')
            return false
          }
          PIKHLON_ID = dsHDKH[0]['khlon_id']
          //PINGANHNGHE_ID = dsHDKH[0]["nganhnghe_id"]
          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tieunganh_c2', {
            param: dsHDKH[0]['tnc2_id'],
            type: 1
          })
          PINGANHNGHE_ID = temp.data.data
          PIUUTIEN_ID = '0'
          PIDANGKY_TV = '0'
          PIDANGKY_DB = '0'
          for (let i = 0; i < dsDK_DVK.length; i++) {
            if (Number(dsDK_DVK[0][i]['dvkhac_id']) == 1 || Number(dsDK_DVK[0][i]['dvkhac_id']) == 7) {
              PIDANGKY_DB = '1'
              break
            }
          }
          PSCHUCDANH = dsHDKH[0]['chucdanh_dd']
          PSNGAYCAP_GT = dsHDKH[0]['ngaycap']
          PSNOICAP_GT = dsHDKH[0]['noicap']
          PDNGAYSINH = dsHDKH[0]['ngay_sn']
          PIPHAI = dsHDKH[0]['gioitinh']
          PSTEN_TT_CU = this.ten_tt_cu
          // PSDIACHI_TT_CU = CommonFunction.ChuanHoa_XML(this.diachi_tt_cu);
          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_hd', {
            param: dsHDKH[0]['loaihd_id'],
            type: 1
          })
          PILOAIHD_ID = temp.data.data
          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_quan', {
            param: this.quantt_id,
            type: 1
          })
          PIQUANTT_ID = temp.data.data
          if (PIQUANTT_ID == '-1') {
            this.$toast.error('Thông tin về Quận/Huyện của địa chỉ thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
            return false
          }

          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_phuong', {
            param: this.quantt_id,
            param1: this.phuongtt_id,
            type: 1
          })

          PIPHUONGTT_ID = temp.data.data
          if (PIPHUONGTT_ID == '-1') {
            this.$toast.error('Thông tin Phường/Xã của địa chỉ thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
            return false
          }

          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_pho', {
            param: this.phuongtt_id,
            type: 1,
            param1: this.phott_id
          })
          PIPHOTT_ID = temp.data.data
          if (PIPHOTT_ID == '-1') {
            this.$toast.error('Thông tin Phố/Thôn xóm của địa chỉ thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
            return false
          }
          PSSOTT_NHA = this.sonhatt

          PSMA_NV = this.$refs.txtMaNVTC.value //"";//
          PSMA_TUYEN = this.$refs.txtMaTuyen.value
          PSEMAIL = dsHDKH[0]['email']
          PDNGAYSN = dsHDKH[0]['ngay_yc'] //"";//tạm thời
          PIKHRR = '0'
          PSSO_GT1 = ''
          PSNGAYCAP_GT1 = ''
          PSNOICAP_GT1 = '0'
          PILOAIGT_ID1 = '1'
          PIHINHTHUCS_CQ = '0'
          PIHINHTHUC_CK = ''
          //  PSNGUOI_DD = dsHDKH[0]["nguoi_dd"]
        }
        return false // nhan quyen
      } catch (err) {
        this.$toast.error('Có lỗi khi đồng bộ dữ liệu ' + err.message)
        return false
      }
    },
    //endregion

    //region Cap nhat
    async CapNhat() {
      this.$refs.labelFunctionTitle.focus()
      let async_status = false // Biến kiểm tra việc đồng bộ dữ liệu VinaPhone, MyTV...
    },
    //endregion

    async LAY_DS_ISDN_THEO_THUEBAO_ID(vthuebao_id) {
      ///TuanNA: thêm code lấy danh sách thuê bao ISDN theo thuê bao _id
      ///Ngày 16.05.2013
      try {
        matb_isdn_chinh = ''
        this.ds_isdn = await tcdanhba.LAY_DS_ISDN_THEO_THUEBAO_ID(vthuebao_id)
        for (let i = 0; i < this.ds_isdn.length; i++) {
          if (this.ds_isdn[0][i]['thuebao_cha_id'] == '') {
            matb_isdn_chinh = this.ds_isdn[0][i]['ma_tb']
            break
          }
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi: ' + err.message)
      }
    },

    //region Them HD thanh toán
    async ThemHDThanhToan() {
      try {
        this.$refs.txtMaTT.value = ''
        if (!KiemTraDL_ThanhToan()) return

        //if (this.$refs.txtMaNVTC.value == "")
        //{
        //    this.$toast.success("Hãy nhập mã nhân viên thu cước !");
        //
        //    this.$refs.txtMaNVTC.focus();
        //    return;
        //}
        ////end tuan code
        //if (this.$refs.txtTenTT.value == "")
        //{
        //    this.$toast.success("Hãy nhập tên thanh toán !");
        //
        //    this.$refs.txtTenTT.focus();
        //    return;
        //}

        //if (this.$refs.txtDiaChiTT.value == "" || this.quantt_id == 0 || this.phuongtt_id == 0 || this.phott_id == 0)
        //{
        //    this.$toast.success("Hãy nhập địa chỉ thanh toán !");
        //
        //    this.$refs.txtDiaChiTT.focus();
        //    return;
        //}

        this.TaoDuLieu_HDTT(true)
        this.TaoDuLieu_DiaChi(true, 2)
        chuyenquyen.ThemMoi_HopDongTT(dsHDTT, dsDiaChi, dsDiaChiHDTT)
        this.HienThiDS_HDTT_CBO(this.hdkh_id)
        this.HienThiDanhSacHDTT(await tchopdong.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id))
      } catch (err) {
        console.log(err)
      }
    },
    //endregion

    //region Them HD thuê bao
    ThemHDThueBao() {},
    //endregion

    //region XoaHDKH
    async XoaHDKH() {
      try {
        this.loading(true)
        await this.axios.post('/web-hopdong/hopdong/khoa_mo_thuebao_no_cuoc/xoa_hopdong', {
          list: [
            {
              hdkh_id: this.hdkh_id,
              loaihd_id: LoaiHopDong.TACHNHAP_TB
            }
          ]
        })
        this.$toast.success('Xoá hợp đồng trên Viễn thông tỉnh thành công !')
        this.loading(false)
        this.SetButton(0)
      } catch (err) {
        console.log(err)
      }
    },
    //endregion

    //region XoaHDTT
    async XoaHDTT() {
      try {
        laphd.XOA_HDTT(this.hdtt_id)
        HienThiDS_HDTT_CBO(this.hdkh_id)
        HienThiDanhSacHDTT(await tchopdong.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id))
      } catch (err) {
        console.log(err)
      }
    },
    //endregion

    //region XoaHDTB
    async XoaHDTB() {
      try {
        //TuanNA them code xoá thuê bao chuyển quyền -- 29/07/2010
        TaoDuLieu_HDTB(false)

        if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA) {
          //region Xoá thuê bao trên vinaphone
          if (this.check_tb_dd) {
            //Nếu có 1 thuê bao di động thì xóa cả hợp đồng đi
            count = 0
            for (let i = 0; i < this.$refs.lvwThueBao.length; i++) {
              if (this.$refs.lvwThueBao[i].SubItems[8].value == DichVuVienThong.DI_DONG) {
                count++
              }
            }
            if (count == 1) {
              var kqDel = Delete_HDCQ_VinaPhone(this.$refs.txtMaGD.value)
              if (kqDel == 'OK') {
                new KNotifications('Thành công', 'Đã xóa thành công hợp đồng/phụ lục có mã giao dịch: ' + this.$refs.txtMaGD.value + ' trên VinaPhone.', true).Show()
              } else {
                this.$toast.success('Có lỗi: ' + strError_VinaPhone + ' khi thực hiện xóa hợp đồng/phụ lục có mã giao dịch: ' + this.$refs.txtMaGD.value + ' trên VinaPhone.')
                return
              }
            } //Nếu có nhiều thuê bao di động
            else {
              var kqDel = Delete_TBCQ_VinaPhone(this.$refs.txtMaGD.value)
              if (kqDel == 'OK') {
                new KNotifications('Thành công', 'Đã xóa thành công số thuê bao: ' + dsHDTB[0]['ma_tb'] + ' trên VinaPhone.', true).Show()
              } else {
                this.$toast.error('Không thể xóa thuê bao ' + dsHDTB[0]['ma_tb'] + ' trên dữ liệu VinaPhone\nCó lỗi: ' + strError_VinaPhone)
                return
              }
            }
          }
          //endregion
        }
        laphd.XOA_HDTB(this.hdtb_id)
        this.HienThiDanhSacHDTB(await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id))
        if (this.$refs.lvwThueBao.length > 0) {
          this.hdtb_id = Number(this.$refs.lvwThueBao[0].SubItems[2].value)
          this.HienThiTTHopDongTB(await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.hdtb_id))
          // HienThiTienTheoKhoanMuc(this.hdtb_id, tien_cd, this.loaihd_id, this.dichvuvt_id, khlon_id, loaitb_id, this.kieuld_id, this.doituong_id, this.ngaylhd, nhaphd_kt, this.phuongld_id);
        }
      } catch (err) {
        console.log(err)
      }
    },
    //endregion

    //region HienThiTTHopDongKH
    async HienThiTTHopDongKH(ds) {
      return new Promise(async (resolve, reject) => {
        try {
          if (ds && ds.length > 0) {
            if (this.$refs.cboLoaihinhTB.value == null) {
              this.$refs.cboLoaiKH.value = null
              this.$refs.cboDoituongTB.value = -1
            }
            this.hdkh_id = Number(ds[0]['hdkh_id'])
            this.ma_gd = ds[0]['ma_gd']
            console.log('Hiển thị theo mã GD', ds[0])
            this.$refs.txtMaGD.value = ds[0]['ma_gd']
            this.$refs.txtMaHD.value = ds[0]['ma_hd']
            this.$refs.txtMaKH.value = ds[0]['ma_kh']
            if (ds[0]['khachhang_id']) this.khachhang_id = Number(ds[0]['khachhang_id'])
            else this.khachhang_id = 0

            if (this.phantap_kh && this.khachhang_id > 0) {
              let ds_dt_dtn = []
              rs = await this.axios.get('web-hopdong/hopdong/lay_doanhthu_dtn', {
                params: {
                  khachHangId: this.khachhang_id
                }
              })
              ds_dt_dtn = rs.data.data
              if (ds_dt_dtn.length > 0) {
                if (ds_dt_dtn[0]['doanhthu']) this.pt_doanhthu = parseFloat(ds_dt_dtn[0]['doanhthu'])
                else this.pt_doanhthu = 0

                if (ds_dt_dtn[0]['dtn']) this.pt_diem_tn = Number(ds_dt_dtn[0]['dtn'])
                else this.pt_diem_tn = 0
              } else {
                this.pt_doanhthu = 0
                this.pt_diem_tn = 0
              }
            } else {
              this.pt_doanhthu = 0
              this.pt_diem_tn = 0
            }

            this.$refs.txtTenKH.value = ds[0]['ten_kh']
            this.$refs.txtDiaChiKH.value = ds[0]['diachi_kh']
            this.HienThiTTDiaChi(await tchopdong.LAY_DS_DIACHI_THEO_HDKH_ID(this.hdkh_id), 1)
            if (ds[0]['diachi_ndd']) this.$refs.txtDiaChiNDD.value = ds[0]['diachi_ndd']
            else this.$refs.txtDiaChiNDD.value = this.$refs.txtDiaChiKH.value
            this.$refs.txtNguoiDD.value = ds[0]['nguoi_dd']
            this.$refs.txtChucDanhDD.value = ds[0]['chucdanh_dd']
            this.$refs.txtTelKH.value = ds[0]['so_dt']
            this.$refs.tbDienThoaiLienHe.value = ds[0]['so_dt_tt']
            this.$refs.txtFaxKH.value = ds[0]['so_fax']

            this.$refs.txtHoKhau.value = ds[0]['hokhau']
            this.$refs.txtNoiCapHK.value = ds[0]['noicap_hk']
            this.$refs.cboQuocTich.value = ds[0]['quoctich_id']
            this.$refs.cboDanToc.value = ds[0]['dantoc_id']

            //this.$refs.cboNganhNgheLoc.LockEvents();
            this.$refs.cboNganhNgheLoc.value = ds[0]['nganhnghe_id']
            this.nganhnghe_id = Number(ds[0]['nganhnghe_id'])
            //this.$refs.cboNganhNgheLoc.UnLockEvents();

            //Tuan them cac truong du lieu hien thi tren form -- Ngay 05/07/2010
            this.$refs.txtMaSTKH.value = ds[0]['mst']
            if (ds[0]['gioitinh'] == '1') {
              this.$refs.rdoGTNu.checked = true
            } else if (ds[0]['gioitinh'] == '0') this.$refs.rdoGTNam.checked = true
            if (ds[0]['ngay_hk']) this.$refs.txtNgayCapHK.value = this.formatDate(ds[0]['ngay_hk'])
            else this.$refs.txtNgayCapHK.value = ''

            if (ds[0]['khlon_id']) this.$refs.cboKhachHangLon.value = Number(ds[0]['khlon_id'])

            if (ds[0]['loaikh_id']) this.$refs.cboLoaiKH.value = Number(ds[0]['loaikh_id'])

            try {
              temp

              temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_khachhang', {
                param: this.hdkh_id,
                type: 4
              })
              strPLKH = temp.data.data
              if (strPLKH != '-1') {
                PHANLOAIKH_ID = Number(strPLKH)
              }
            } catch (err) {}

            if (ds[0]['ctv_id']) {
              //if (!this.nopopup) this.chkCTV_CheckedChanged();
              this.nopopup = false
              this.$refs.chkCTV.checked = true
              this.ctv_id = Number(ds[0]['ctv_id'])
              temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', {
                param: this.ctv_id,
                type: 3
              })
              this.$refs.txtCTV.value = temp.data.data
              //this.chkCTV_CheckedChanged();
              //this.$refs.chkCTV.checkedChanged -= new System.EventHandler(this.chkCTV_CheckedChanged);
              //this.$refs.chkCTV.checked = true;

              //let obj = new CheckDataFacade();
              //if (ds[0]["ctv_id"])
              //{
              //    let dt = await bangts.Lay_DonViQL_Theo_CTV(Number(ds[0]["ctv_id"]));
              //    if (dt.length > 0)
              //    {
              //        donvi_id = Number(dt[0]["donvi_id"]);
              //        if (donvi_id > 0)
              //            donvi_ctv_id = donvi_id;

              //        //Thangph: Bo sung 02/08/2015
              //        let dtt = await bangts.Lay_NhomLNV_Theo_CTV(Number(ds[0]["ctv_id"]), NHOM_LNV.CONGTACVIEN);
              //        loainv_ctv_id = Number(dtt[0]["loainv_id"]);
              //        this.ctv_id = Number(ds[0]["ctv_id"]);
              //        this.$refs.txtCTV.value = objawait checkdata.MAP_ID("ten_nv", DatabaseConstants.DB1 + ".nhanvien", "where nhanvien_id = " + this.ctv_id);
              //    }
              //}
              //this.$refs.chkCTV.checkedChanged += new System.EventHandler(this.chkCTV_CheckedChanged);
            } else {
              this.$refs.txtCTV.value = ''
              this.$refs.chkCTV.checked = false
            }

            if (ds[0]['nhanviengt_id']) {
              // this.chkNguoiGT_CheckedChanged();
              this.$refs.chkNguoiGT.checked = true
              this.nguoigt_id = Number(ds[0]['nhanviengt_id'])
              temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', {
                param: this.nguoigt_id,
                type: 3
              })
              this.$refs.txtNguoiGT.value = temp.data.data
              // this.chkNguoiGT_CheckedChanged();
              //this.chkNguoiGT.checkedChanged -= new System.EventHandler(this.chkNguoiGT_CheckedChanged);
              //chkNguoiGT.checked = true;

              //let obj = new CheckDataFacade();
              //if (ds[0]["nhanviengt_id"])
              //{
              //    let dt = await bangts.Lay_DonViQL_Theo_CTV(Number(ds[0]["nhanviengt_id"]));
              //    if (dt.length > 0)
              //    {
              //        donvi_id = Number(dt[0]["donvi_id"]);
              //        if (donvi_id > 0)
              //            donvi_nguoi_gt_id = donvi_id;

              //        //Thangph: Bo sung 02/08/2015
              //        let dtt = await bangts.Lay_NhomLNV_Theo_CTV(Number(ds[0]["nhanviengt_id"]), NHOM_LNV.GIOITHIEU_HD);
              //        loainv_nguoi_gt_id = Number(dtt[0]["loainv_id"]);
              //        this.nguoigt_id = Number(ds[0]["nhanviengt_id"]);
              //        this.$refs.txtNguoiGT.value = objawait checkdata.MAP_ID("ten_nv", DatabaseConstants.DB1 + ".nhanvien", "where nhanvien_id = " + this.nguoigt_id);
              //    }
              //}
              //this.chkNguoiGT.checkedChanged += new System.EventHandler(this.chkNguoiGT_CheckedChanged);
            } else {
              this.$refs.txtNguoiGT.value = ''
              this.$refs.chkNguoiGT.checked = false
            }

            if (ds[0]['ngay_sn']) this.$refs.txtNgaySN.value = ds[0]['ngay_sn']
            else this.$refs.txtNgaySN.value = ''

            if (ds[0]['ngay_yc']) this.$refs.dtpNgayYC.value = ds[0]['ngay_yc']
            if (ds[0]['ngaylap_hd']) this.$refs.dtpNgayLapHDCQ.value = ds[0]['ngaylap_hd']
            this.$refs.txtGhiChuKH.value = ds[0]['ghichu']
            //txtDactrung.value = (ds[0]["dactrung"]);
            this.$refs.txtDacTrung.value = ds[0]['dactrung']
            //Hiếu bổ sung khlon_id,this.ngaylhd,this.loaihd_id
            this.$refs.cboKhachHangLon.value = Number(ds[0]['khlon_id'])
            this.ngaylhd = this.$refs.dtpNgayLapHDCQ.value
            this.loaihd_id = Number(ds[0]['loaihd_id'])

            this.$refs.txtSoGT.value = ds[0]['so_gt']
            if (ds[0]['ngaycap']) {
              this.$refs.txtNgayCap.value = this.formatDate(ds[0]['ngaycap'])
            } else {
              this.$refs.txtNgayCap.value = ''
            }
            if (ds[0]['ngayhethan_gt']) this.$refs.txtNgayHetHan.value = this.formatDate(ds[0]['ngayhethan_gt'])
            else this.$refs.txtNgayHetHan.value = ''
            this.$refs.cboGiayToKH.value = ds[0]['loaigt_id']
            this.$refs.txtNoiCap.value = ds[0]['noicap']
            this.$refs.txtSoTKKH.value = ds[0]['stk']
            if (ds[0]['nganhang_id']) {
              this.$refs.chkNganHangKH.checked = true
              this.$refs.cboNganHangKH.value = ds[0]['nganhang_id']
            }
            //TuấnNA thêm code hiển thị thông tin id tiểu ngành c1,c2,c3 -- Ngày 03/12/2010
            if (ds[0]['tnc1_id']) {
              this.TNC1_ID = Number(ds[0]['tnc1_id'])
            }
            if (ds[0]['tnc2_id']) {
              this.TNC2_ID = Number(ds[0]['tnc2_id'])
            }
            if (ds[0]['tnc3_id']) {
              this.TNC3_ID = Number(ds[0]['tnc3_id'])
            }
            if (ds[0]['tnc4_id']) {
              this.TNC4_ID = Number(ds[0]['tnc4_id'])
            }
            //

            //this.$refs.txtMaNVTC.value = ds[0]["manv_tc"]
            //End tuan

            this.HienThiDS_HDTT_CBO(this.hdkh_id)
            var ds_tt = await tchopdong.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id)
            var ds_tb = await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)

            await this.HienThiTTHopDongTT(ds_tt)
            await this.HienThiTTHopDongTB(ds_tb)
            await this.HienThiDanhSacHDTT(ds_tt)
            await this.HienThiDanhSacHDTB(ds_tb)
            //Load nhiều quá thử bỏ đi
            /* HienThiTTHopDongTT(await tchopdong.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id));
                                  HienThiTTHopDongTB(await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id));
                                  HienThiDanhSacHDTT(await tchopdong.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(this.hdkh_id));
                                  HienThiDanhSacHDTB(await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id));*/

            //frmTraCuuCTDanhBa frmTCCTDB = new frmTraCuuCTDanhBa(this.$refs.txtMaKH.value);
            //frmTCCTDB.ShowDialog();

            this.SetButton(3)
          } else {
            this.$toast.error('Không tìm thấy thông tin về hợp đồng có mã giao dịch: ' + this.$refs.txtMaGD.value + '\n Bạn hãy kiểm tra lại')
            this.SetButton(-1) ///TuấnNA sửa code --- ngày 24/09/2010
            this.Clear()
          }
          resolve(true)
        } catch (err) {
          this.load_tt_lh = true
          console.log(err)
          resolve(true)
        }
      })
    },
    //endregion

    //region HienThiTTHopDongTT
    async HienThiTTHopDongTT(ds) {
      return new Promise(async (resolve, reject) => {
        try {
          this.httt_id_cu = -1
          if (ds.length > 0) {
            this.hdtt_id = Number(ds[0]['hdtt_id'])
            this.$refs.txtMaTT.value = ds[0]['ma_tt']
            if (ds[0]['thanhtoan_id']) {
              this.thanhtoan_id = Number(ds[0]['thanhtoan_id'])
              rs = await this.axios.post('/web-quantri/lay-dulieu/sp_tt_db_thanhtoan', {
                param: this.thanhtoan_id,
                type: 1
              })
              var t_httt = rs.data.data
              if (!t_httt) {
                this.httt_id_cu = -1
              } else {
                this.httt_id_cu = Number(t_httt)
              }
            } else {
              this.thanhtoan_id = 0
              this.httt_id_cu = -1
            }
            if (ds[0]['hdmig_id']) {
              this.hdmig_id = Number(ds[0]['hdmig_id'])
              temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_mig', {
                param: this.hdmig_id,
                type: 1
              })
              this.$refs.txtMaIG.value = temp.data.data
            } else {
              this.hdmig_id = 0
              this.$refs.txtMaIG.value = ''
            }
            this.$refs.txtTenTT.value = ds[0]['ten_tt']
            this.$refs.txtDiaChiTT.value = ds[0]['diachi_tt']
            this.$refs.txtDiaChiCT.value = ds[0]['diachi_ct']
            this.$refs.txtDiaChiBC.value = ds[0]['diachi_bc']
            this.HienThiTTDiaChi(await tchopdong.LAY_DS_DIACHI_THEO_HDTT_ID(this.hdtt_id), 2)
            //TuấnNA thêm code hiển thị thông tin NSNN trong bảng HDTT --- Ngày 12/11/2010
            this.DV_QHNS = ds[0]['DV_QHNS']
            this.MACHUONG = ds[0]['MACHUONG']
            this.MN_KT = ds[0]['MN_KT']
            this.MA_NDKT = ds[0]['MA_NDKT']
            this.MN_NSNN = ds[0]['MN_NSNN']
            if (ds[0]['TIEN_DM']) this.TIEN_DM = Number(ds[0]['TIEN_DM'])
            else this.TIEN_DM = 0

            this.$refs.txtMST_TT.value = ds[0]['mst']
            this.$refs.txtSTK_TT.value = ds[0]['stk']
            this.$refs.txtChu_TK.value = ds[0]['chu_tk']
            this.$refs.txtGhiChuTT.value = ds[0]['ghichu']
            if (ds[0]['nganhang_id']) this.$refs.cboNganHangTT.value = ds[0]['nganhang_id']
            this.$refs.cboHTTT.value = ds[0]['httt_id']

            ///TuanNA viết code thay thế this.$refs.cboDonviQL -- Ngày 26/07/2010
            if (ds[0]['donvi_id'])
              //this.donvi_ql = Number(ds[0]["donvi_id"]);
              this.$refs.cboDonviQL.value = ds[0]['donvi_id']
            //this.donvi_qlcu = ds[0]["donvi_id"]

            if (ds[0]['nhanvien_id']) {
              this.nhanvientc_id = Number(ds[0]['nhanvien_id'])
              temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', {
                param: this.nhanvientc_id,
                type: 4
              })
              this.$refs.txtMaNVTC.value = temp.data.data
            } else {
              this.nhanvientc_id = 0
              this.$refs.txtMaNVTC.value = ''
            }

            if (ds[0]['tuyenthu_id']) {
              this.tuyenthu_id = Number(ds[0]['tuyenthu_id'])
              this.$refs.txtMaTuyen.value = ds[0]['ma_tuyen']
            } else {
              this.tuyenthu_id = 0
              this.$refs.txtMaTuyen.value = ''
            }
            this.matb_dd = ds[0]['matb_dd']
            ///TuanNA thêm code hiển thị matb_dd trong hợp đồng thanh toán --- Ngày 29/08/2010
            rs = await this.axios.post('web-hopdong/hopdong/ht_combo_ma_daidien', {
              thanhtoan_id: this.thanhtoan_id
            })
            let dt_madd = rs.data.data
            this.$refs.cboMaDD.dataSource = dt_madd
            this.$refs.cboMaDD.value = ds[0]['matb_dd']

            this.LAY_DS_SUDUNG_DVK(this.thanhtoan_id, 2)
            this.HienThi_NVQL_AM_DB_HD(2, this.hdtt_id)
          }
        } catch (err) {
          console.log(err)
        }
        resolve(true)
      })
    },
    //endregion

    //region HienThiTTHopDongTB
    async HienThiTTHopDongTB(ds) {
      return new Promise(async (resolve, reject) => {
        try {
          if (ds.length > 0) {
            this.hdtb_id = Number(ds[0]['hdtb_id'])

            if (ds[0]['thuebao_id']) this.thuebao_id = Number(ds[0]['thuebao_id'])
            else this.thuebao_id = 0

            if (ds[0]['thuebao_cha_id']) this.thuebao_cha_id = Number(ds[0]['thuebao_cha_id'])
            else this.thuebao_cha_id = 0

            this.$refs.txtMaTB.value = ds[0]['ma_tb']
            this.ma_tb = this.$refs.txtMaTB.value
            this.$refs.txtTenTB.value = ds[0]['ten_tb']
            this.$refs.txtDiaChiTB.value = ds[0]['diachi_tb']
            this.$refs.txtDiachiLD.value = ds[0]['diachi_ld'] //Tuan them code dia chi lap dat -- Ngay 29/07/2010
            this.HienThiTTDiaChi(await tchopdong.LAY_DS_DIACHI_THEO_HDTB_ID(this.hdtb_id), 3)

            if (ds[0]['quytrinh_id']) this.quytrinh_id = Number(ds[0]['quytrinh_id'])
            else this.quytrinh_id = 0
            this.load_tt_lh = false

            rs = await this.axios.post('/web-quantri/lay-dulieu/sp_tt_loaihinh_tb', {
              param: Number(ds[0]['loaitb_id']),
              type: 2
            })
            this.dichvuvt_id = Number(rs.data.data[0]['dichvuvt_id'])
            this.$refs.cboDichVuVT.value = this.dichvuvt_id
            // Nếu load lần đầu mà gõ mã tìm đc thì nếu dịch vụ trùng thì gọi luôn sự kiện không
            // trùng thì thôi vì nó cũng gọi sự kiện rồi

            await this.cboDichVuVT_SelectedValueChanged()
            //TuanNA kiểm tra xem hợp đồng này có thuê bao di động hay không -- Ngày 28/07/2010
            for (let i = 0; i < ds.length; i++) {
              if (Number(ds[0]['dichvuvt_id']) == DichVuVienThong.DI_DONG && Number(ds[0]['loaitb_id']) == LoaiHinhTB.DIDONGTRASAU) {
                if (Number(ds[0]['status']) == 1) {
                  this.check_tb_dd = true
                  break
                }
              }
            } //comment test ---> không day len vinaphone
            ///Tuấn thêm code kiểm tra thuê bao thuộc hợp đồng hay phụ lục --- Ngày 19/08/2010
            this.TBHD_PL = Number(ds[0]['tbhd_pl'])
            this.loaitb_id = Number(ds[0]['loaitb_id'])
            this.load_tt_lh = true
            this.$refs.cboLoaihinhTB.value = this.loaitb_id
            await this.cboLoaihinhTB_SelectedValueChanged()
            this.$refs.cboKieuLD.value = ds[0]['kieuld_id']
            await this.cboKieuLD_SelectedValueChanged()
            this.$refs.cboDoituongTB.value = ds[0]['doituong_id']
            //Hiếu thêm this.kieuld_id,this.doituong_id
            this.kieuld_id = Number(ds[0]['kieuld_id'])
            this.doituong_id = Number(ds[0]['doituong_id'])
            this.$refs.cboTT.value = ds[0]['hdtt_id']
            this.$refs.cboDaiVT.value = Number(ds[0]['donvi_id'])
            this.status = ds[0]['status']
            this.$refs.txtGhiChuTB.value = ds[0]['ghichu']

            this.m_dsThueBao_DichVu = await tchopdong.LAY_DS_THUEBAO_DICHVU(this.hdtb_id, this.thuebao_id, this.loaitb_id, 1)
            this.HT_DichVuGT()

            if (ds[0]['mucuoctb_id']) {
              this.$refs.cboMucCuocTB.value = ds[0]['mucuoctb_id']
              this.$refs.cboMuccuocTBCu.value = ds[0]['mucuoctb_id']
            }

            this.LAY_DS_SUDUNG_DVK(this.thuebao_id, 1)

            //

            //Hiếu bổ sung lấy đơn vị đầu, đơn vị cuối ngày 06/09/2010
            if (ds[0]['donvi_dau_id']) this.donvi_dau_id = Number(ds[0]['donvi_dau_id'])
            if (ds[0]['donvi_cuoi_id']) this.donvi_cuoi_id = Number(ds[0]['donvi_cuoi_id'])
            //

            let ds_dbtb = []
            ds_dbtb = await tcdanhba.LAY_DS_DBTB_THEO_TBID(this.thuebao_id)
            if (ds_dbtb && ds_dbtb[0]['ngay_sd']) this.$refs.dtpNgaySD.value = ds_dbtb[0]['ngay_sd']
            else this.$refs.dtpNgaySD.value = this.tt_nd.ngay_cn
            //
            //không vào
            console.log(ds[0]['ngay_sn'], 'set ds[0]["ngay_sn"] 4')
            console.log(this.formatDate(ds[0]['ngay_sn']))
            if (ds[0]['ngay_sn']) this.$refs.txtNgaySinhTB.value = this.formatDate(ds[0]['ngay_sn'])
            this.HienThiTTMoRong(this.hdtb_id, this.dichvuvt_id)
            ///Tuấn thêm code lấy tiền theo khoản mục khi gõ mã GD tra cứu trong HĐ --- Ngày 16/09/2010
            // this.LayTienTheoKhoanMuc();
            this.HienThiThongTinCu(await tchopdong.LAY_BIENDONG_THEO_HDTB_ID(this.hdtb_id))
            //Tuấn NA thêm lấy tiền theo this.hdtb_id
            //dtCTKM = await tchopdong.LAY_TIEN_HD_KM_THEO_HDTB(this.hdtb_id)[0];
            //let tien_ld = 0, vat_ld = 0, tien_cd = 0, vat_cd = 0;
            //for (let i = 0; i < dtCTKM.length; i++)
            //{
            //    if (dtCTKM[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_CHUYENQUYEN)
            //    {
            //        tien_ld = parseFloat(dtCTKM[i]["tien"]);
            //        vat_ld = parseFloat(dtCTKM[i]["vat"]);
            //    }
            //    if (dtCTKM[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_CAIDAT)
            //    {
            //        tien_cd = parseFloat(dtCTKM[i]["tien"]);
            //        vat_cd = parseFloat(dtCTKM[i]["vat"]);
            //    }
            //}
            //HienThiTienLapDat(tien_ld, vat_ld, tien_cd, vat_cd);

            //Thực hiện lấy tiền --nhapt thêm
            this.ds_tien_khoanmuc = await tchopdong.LAY_TIENHD_KM_THEO_HDTB(this.hdtb_id)
            for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
              if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DICHVU) {
                this.dtien_dv = parseFloat(this.ds_tien_khoanmuc[i]['tien'])
                this.dvat_dv = parseFloat(this.ds_tien_khoanmuc[i]['vat'])
              }

              if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_CHUYENQUYEN) {
                this.dtien_cq = parseFloat(this.ds_tien_khoanmuc[i]['tien'])
                this.dvat_cq = parseFloat(this.ds_tien_khoanmuc[i]['vat'])
              }
            }
            this.LayTongTien_HDTB()
          }
        } catch (err) {
          console.log(err)
        }
        resolve(true)
      })
    },
    //endregion

    //region HienThiThongTinCu
    HienThiThongTinCu(ds) {
      if (ds && ds.length > 0) {
        this.$refs.txtTenKHCu.value = ds[0]['ten_kh']
        this.$refs.txtDiaChiKHCu.value = ds[0]['diachi_kh']
        this.$refs.txtMaHDCu.value = ds[0]['ma_hd']
        //this.$refs.txtMaHD
        this.ma_tt_cu = ds[0]['ma_tt'] // Tuan them code lay gia tri this.ma_tt_cu cua khach hang CQ -- Ngay 22/07/2010
        this.ten_tt_cu = ds[0]['ten_tt'] // Tuan them code lay gia tri this.ten_tt_cu cua khach hang CQ -- Ngay 22/07/2010
        this.diachi_tt_cu = ds[0]['diachi_tt'] // Tuan them code lay gia tri this.diachi_tt_cu cua khach hang CQ -- Ngay 22/07/2010
        // Tuan them code lay gia tri this.ten_tb_cu cho khach hang CQ -- ngay 22/07/2010
        this.ten_tb_cu = ds[0]['ten_kh']
        this.$refs.txtMaKHCu.value = ds[0]['ma_kh']
        ///TuấnNA thêm code lấy dữ liệu cho biến this.ma_kh_cu -- Ngày 06/08/2010
        this.ma_kh_cu = ds[0]['ma_kh']
        this.$refs.txtMaTBCu.value = ds[0]['ma_tb']
        this.$refs.txtTenTBCu.value = ds[0]['ten_tb']
        if (ds[0]['mucuoctb_id']) {
          this.$refs.cboMuccuocTBCu.value = Number(ds[0]['mucuoctb_id'])
        }

        this.doituong_id_bd = Number(ds[0]['doituong_id'])
      }
    },
    //endregion

    //region this.HienThiTTDiaChi
    HienThiTTDiaChi(ds, kieu) {
      if (ds && ds.length > 0) {
        if (kieu == 1) {
          /// Tuan sửa lại code kiểm tra dữ liệu địa chỉ thanh toán -- Ngay 29/07/2010
          if (ds[0]['diachi_id']) this.diachikh_id = Number(ds[0]['diachi_id'])
          else this.diachikh_id = 0

          if (ds[0]['tinh_id']) this.tinhkh_id = Number(ds[0]['tinh_id'])
          else this.tinhkh_id = 0

          if (ds[0]['quan_id']) this.quankh_id = Number(ds[0]['quan_id'])
          else this.quankh_id = 0

          if (ds[0]['phuong_id']) this.phuongkh_id = Number(ds[0]['phuong_id'])
          else this.phuongkh_id = 0

          if (ds[0]['pho_id']) this.phokh_id = Number(ds[0]['pho_id'])
          else this.phokh_id = 0

          if (ds[0]['ap_id']) this.apkh_id = Number(ds[0]['ap_id'])
          else this.apkh_id = 0

          if (ds[0]['khu_id']) this.khukh_id = Number(ds[0]['khu_id'])
          else this.khukh_id = 0

          if (ds[0]['dacdiem_id']) this.dacdiemkh_id = Number(ds[0]['dacdiem_id'])
          else this.dacdiemkh_id = 0

          this.sonhakh = ds[0]['sonha']
        }

        if (kieu == 2) {
          if (ds[0]['diachi_id']) this.diachitt_id = Number(ds[0]['diachi_id'])
          else this.diachitt_id = 0

          if (ds[0]['tinh_id']) this.tinhtt_id = Number(ds[0]['tinh_id'])
          else this.tinhtt_id = 0

          if (ds[0]['quan_id']) this.quantt_id = Number(ds[0]['quan_id'])
          else this.quantt_id = 0

          if (ds[0]['phuong_id']) this.phuongtt_id = Number(ds[0]['phuong_id'])
          else this.phuongtt_id = 0

          if (ds[0]['pho_id']) this.phott_id = Number(ds[0]['pho_id'])
          else this.phott_id = 0

          if (ds[0]['ap_id']) this.aptt_id = Number(ds[0]['ap_id'])
          else this.aptt_id = 0

          if (ds[0]['khu_id']) this.khutt_id = Number(ds[0]['khu_id'])
          else this.khutt_id = 0

          if (ds[0]['dacdiem_id']) this.dacdiemtt_id = Number(ds[0]['dacdiem_id'])
          else this.dacdiemtt_id = 0

          this.sonhatt = ds[0]['sonha']

          //Địa chỉ báo cước
          if (ds[0]['diachibc_id']) this.diachibc_id = Number(ds[0]['diachibc_id'])
          else this.diachibc_id = 0

          if (ds[0]['tinhbc_id']) this.tinhbc_id = Number(ds[0]['tinhbc_id'])
          else this.tinhbc_id = 0

          if (ds[0]['quanbc_id']) this.quanbc_id = Number(ds[0]['quanbc_id'])
          else this.quanbc_id = 0

          if (ds[0]['phuongbc_id']) this.phuongbc_id = Number(ds[0]['phuongbc_id'])
          else this.phuongbc_id = 0

          if (ds[0]['phobc_id']) this.phobc_id = Number(ds[0]['phobc_id'])
          else this.phobc_id = 0

          if (ds[0]['apbc_id']) this.apbc_id = Number(ds[0]['apbc_id'])
          else this.apbc_id = 0

          if (ds[0]['khubc_id']) this.khubc_id = Number(ds[0]['khubc_id'])
          else this.khubc_id = 0

          if (ds[0]['dacdiembc_id']) this.dacdiembc_id = Number(ds[0]['dacdiembc_id'])
          else this.dacdiembc_id = 0

          this.sonhabc = ds[0]['sonha_bc']
        }

        if (kieu == 3) {
          var data = ds[0]

          //Hiếu- thêm phần địa chỉ thuê bao + địa chỉ lắp đặt 22/07/2010
          this.diachitb_id = Number(ds[0]['diachi_id'])
          //Địa chỉ thuê bao
          if (ds[0]['tinh_id']) this.tinhtb_id = Number(ds[0]['tinh_id'])
          else this.tinhtb_id = 0

          if (ds[0]['quan_id']) this.quantb_id = Number(ds[0]['quan_id'])
          else this.quantb_id = 0
          if (ds[0]['phuong_id']) this.phuongtb_id = Number(ds[0]['phuong_id'])
          else this.phuongtb_id = 0

          if (ds[0]['pho_id']) this.photb_id = Number(ds[0]['pho_id'])
          else this.photb_id = 0

          if (ds[0]['ap_id']) this.aptb_id = Number(ds[0]['ap_id'])
          else this.aptb_id = 0

          if (ds[0]['khu_id']) this.khutb_id = Number(ds[0]['khu_id'])
          else this.khutb_id = 0

          if (ds[0]['dacdiem_id']) this.dacdiemtb_id = Number(ds[0]['dacdiem_id'])
          else this.dacdiemtb_id = 0
          ///
          this.sonhatb = ds[0]['sonha']
          if (data.hasOwnProperty('block')) this.blocktb = data['block']
          if (data.hasOwnProperty('tang')) this.tangtb = data['tang']
          if (data.hasOwnProperty('phong')) this.sophongtb = data['phong']
          if (data.hasOwnProperty('mota')) this.motadctb = data['mota']

          /// Tuấn thêm code lấy địa chỉ lắp đặt cho thuê bao -- Ngày 29/07/2010
          if (ds[0]['diachild_id']) this.diachild_id = Number(ds[0]['diachild_id'])
          else this.diachild_id = 0

          if (ds[0]['tinhld_id']) this.tinhld_id = Number(ds[0]['tinhld_id'])
          else this.tinhld_id = 0

          if (ds[0]['quanld_id']) this.quanld_id = Number(ds[0]['quanld_id'])
          else this.quanld_id = 0

          if (ds[0]['phuongld_id']) this.phuongld_id = Number(ds[0]['phuongld_id'])
          else this.phuongld_id = 0

          if (ds[0]['phold_id']) this.phold_id = Number(ds[0]['phold_id'])
          else this.phold_id = 0

          if (ds[0]['apld_id']) this.apld_id = Number(ds[0]['apld_id'])
          else this.apld_id = 0

          if (ds[0]['khuld_id']) this.khuld_id = Number(ds[0]['khuld_id'])
          else this.khuld_id = 0

          if (ds[0]['dacdiemld_id']) this.dacdiemld_id = Number(ds[0]['dacdiemld_id'])
          else this.dacdiemld_id = 0

          ///
          this.sonhald = ds[0]['sonha_ld']

          if (data.hasOwnProperty('block_ld')) this.blockld = data['block_ld']
          if (data.hasOwnProperty('tang_ld')) this.tangld = data['tang_ld']
          if (data.hasOwnProperty('phong_ld')) this.sophongld = data['phong_ld']
          if (data.hasOwnProperty('mota_ld')) this.motadcld = data['mota_ld']
        }
      }
    },
    //endregion

    //region HienThiDanhSacHDTT
    HienThiDanhSacHDTT(ds) {
      ///TuấnNA thêm code lấy đơn vị quản lý --- Ngày 07/09/2010
      ///Khách hàng nhận quyền sau khi lập hợp đồng có mã thanh toán ---> đơn vị quản lý
      //this.donvi_qlcu = LayDonVi_QL(this.$refs.txtMaTT.value, Number(ds[0]["phuong_id"]), Number(ds[0]["pho_id"]));

      this.$refs.lvwThanhToan.dataSource = []
      this.$refs.lvwThanhToan.dataSource = ds
      if (this.$refs.lvwThanhToan.dataSource.length == 1) this.$refs.tsbtnXoaTT.classList.add('disabled')
      else this.$refs.tsbtnXoaTT.classList.remove('disabled')
      return
    },

    HienThiDS_HDTT_CBO(hdkh_id) {
      try {
        this.$refs.cboTT.dataSource = this.dsDanhMuc['hinhthuc_tt']
        this.$refs.cboTT.dataTextField = 'hinhthuc_tt'
        this.$refs.cboTT.dataValueField = 'httt_id'
        this.$refs.cboTT.SelectedIndex = 0
      } catch (err) {}
    },
    //endregion

    //region HienThiDanhSacHDTB
    async HienThiDanhSacHDTB(ds) {
      let tongtienhd = 0
      let tongtientb = 0
      this.$refs.lvwThueBao.dataSource = []
      this.$refs.lvwThueBao.dataSource = ds

      if(ds && ds.length > 0) {
        this.$refs.tsbtnThemTT.classList.remove('disabled')
      }

      let ds_khcu = await tchopdong.LAY_BIENDONG_THEO_HDTB_ID(this.hdtb_id)
      console.log(ds_khcu, 'ds_khcu')
      if (ds_khcu.length > 0) {
        this.khachhang_id_cq = Number(ds_khcu[0]['khachhang_id'])
      }

      if (this.$refs.lvwThueBao.dataSource.length > 1) {
        this.$refs.tsbtnXoaTB.classList.remove('disabled')
      } else this.$refs.tsbtnXoaTB.classList.add('disabled')
      return
    },
    //endregion

    //region HienThiTTMoRong
    async HienThiTTMoRong(hdtb_id, dichvuvt_id) {
      let ds = []
      switch (this.dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          ds = await tchopdong.LAY_DS_HDTB_CD_THEO_HDTB_ID(this.hdtb_id)
          break
        case DichVuVienThong.DI_DONG:
          ds = await tchopdong.LAY_DS_HDTB_DD_THEO_HDTB_ID(this.hdtb_id)
          break
        case DichVuVienThong.ADSL:
        case DichVuVienThong.EMAIL:
          ds = await tchopdong.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(this.hdtb_id)
          break
        case DichVuVienThong.MEGAWAN:
        case DichVuVienThong.METRONET:
        case DichVuVienThong.NOIBO_DADIEM:
          ds = await tchopdong.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(this.hdtb_id)
          break
        case DichVuVienThong.TSL:
        case DichVuVienThong.NOIBO_2DIEM:
          ds = await tchopdong.LAY_DS_HDTB_TSL_THEO_HDTB_ID(this.hdtb_id)
          break
        case DichVuVienThong.GPHONE:
          ds = await tchopdong.lay_ds_hdtb_GP_theo_hdtb_id(this.hdtb_id)
          break
        case DichVuVienThong.IMS:
          ds = await tchopdong.LAY_DS_HDTB_IMS_THEO_HDTB_ID(this.hdtb_id)
          break
        case DichVuVienThong.DICHVU_CNTT:
        case DichVuVienThong.ANTOAN_BAOMAT_TT:
        case DichVuVienThong.TRUNGTAM_DULIEU:
        case DichVuVienThong.HOINGHI_TRUYENHINH:
          //this.Init_Properties(CURRENT_LOAITB_ID, "HDTB_CNTT", "00000", ItemList);
          ds = await tchopdong.LAY_DS_HDTB_CNTT_THEO_HDTB_ID(this.hdtb_id)
          break
        default:
          ds = await tchopdong.LAY_DS_HDTB_CD_THEO_HDTB_ID(this.hdtb_id)
          break
      }

      if (ds.length > 0) {
        ///TuấnNA thêm code hiển thị thông tin các loại dịch vụ viễn thông : Di động,internet...
        ///--Ngày 09/08/2010
        //let ds;
        switch (this.dichvuvt_id) {
          case DichVuVienThong.CO_DINH:
            //cboVeTinhCD.value = Number(ds[0]["donvi_id"]);
            //cboVeTinhCD.value = Number(ds[0]["donvi_id"]);
            if (ds[0]['trangbi_id']) this.trangbi_id = Number(ds[0]['trangbi_id'])
            else this.trangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI //khách hàng tự trang bị
            break
          case DichVuVienThong.DI_DONG:
            //txtSimDD.value = ds[0]["simcard"]
            ///TuấnNA thêm code hiển thị các thông tin về di động
            ///--Ngày 09/08/2010
            this.$refs.cboGoiCuocDD.value = Number(ds[0]['goicuoc_id'])
            this.$refs.cboDTVinaPhone.value = Number(ds[0]['doituong_id_neo'])
            this.$refs.txtSimCard.value = ds[0]['simcard']
            break

          ///TuấnNA thêm code hiển thị thông tin mở rộng MGWan
          ///---Ngày 11/09/2010
          case DichVuVienThong.MEGAWAN:
          case DichVuVienThong.METRONET:
          case DichVuVienThong.NOIBO_DADIEM:
            if (ds[0]['thoihan_id']) this.$refs.cboThoihanWan.value = Number(ds[0]['thoihan_id'])
            this.$refs.cboTocDo.value = Number(ds[0]['tocdo_id'])
            this.$refs.cboLoaiModem.value = Number(ds[0]['loaimd_id'])
            this.$refs.cboLoaiKenhWan.value = Number(ds[0]['loaikenh_id'])
            this.$refs.cboLoaiTBiWan.value = Number(ds[0]['thietbidc_id'])

            this.$refs.txtMaHD_TTKD_MGW.value = ds[0]['ma_pl']
            this.$refs.txtSiteID.value = ds[0]['site_id']
            if (ds[0]['tocdo_pir_id']) this.$refs.cboTocDo_PIR.value = Number(ds[0]['tocdo_pir_id'])
            break
          case DichVuVienThong.ADSL:
          case DichVuVienThong.EMAIL:
            if (ds[0]['trangbi_id']) this.$refs.cboTrangBiADSL.value = ds[0]['trangbi_id']
            else this.$refs.cboTrangBiADSL.value = TRANGBI.KHACHHANG_TU_TRANGBI //khách hàng tự trang bị
            this.$refs.txtEmailAdsl.value = ds[0]['email']
            this.$refs.cboGoiCuocAdsl.value = Number(ds[0]['tocdo_id'])
            this.$refs.cboMucCuoc.value = Number(ds[0]['muccuoc_id'])
            break
          case DichVuVienThong.TSL:
          case DichVuVienThong.NOIBO_2DIEM:
            ///TuấnNA thêm code hiển thị thông tin hdtb_TSL --- NGày 01/09/2010
            this.$refs.cboChuQuan.value = ds[0]['chuquan_id']
            this.$refs.cboTocDoKenhTSL.value = ds[0]['tocdo_id']
            this.$refs.cboLoaiKenhTSL.value = ds[0]['loaikenh_id']
            this.$refs.cboTrangBi.value = ds[0]['trangbi_id']
            this.$refs.cboThoiHan.value = ds[0]['thoihan_id']
            this.$refs.cboLoaiTBTSL.value = ds[0]['thietbidc_id']
            this.$refs.txtMaHD_TTKD_TSL.value = ds[0]['ma_pl']

            for (let i = 0; i < ds.length; i++) {
              if (ds[i]['daucuoi_id'] == '1')
                //Nếu là điểm đầu
                this.$refs.txtDiaChiDau.value = ds[i]['diachi']
              else this.$refs.txtDiaChiCuoi.value = ds[i]['diachi']
            }
            break
          case DichVuVienThong.GPHONE:
            this.$refs.chkVSVXGP.checked = ds[0]['mc_tbgp_id'] == '1'
            this.$refs.txtSosimGP.value = ds[0]['simcard']
            break
          case DichVuVienThong.IMS:
            if (ds[0]['thietbi_id']) this.$refs.cboThietBi.value = ds[0]['thietbi_id']
            if (ds[0]['trangbi_id']) this.$refs.cboTrangBiIMS.value = ds[0]['trangbi_id']
            break
          case DichVuVienThong.DICHVU_CNTT:
          case DichVuVienThong.ANTOAN_BAOMAT_TT:
          case DichVuVienThong.TRUNGTAM_DULIEU:
          case DichVuVienThong.HOINGHI_TRUYENHINH:
            if (ds.length > 0) {
              if (ds[0]['linhvuc_id']) this.linhvuc_id = Number(ds[0]['linhvuc_id'])
            } else this.linhvuc_id = 0
            this.tocdo_id = 0

            break
          default:
            break
        }
      }
    },
    //endregion

    //region HienThiTTDBMoRong
    async HienThiTTDBMoRong(thuebao_id, dichvuvt_id) {
      let ds = []
      switch (dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_codinh', {
            params: {
              thueBaoId: thuebao_id
            }
          })
          ds = rs.data.data
          break
        case DichVuVienThong.DI_DONG:
          rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_dd', {
            params: {
              thueBaoId: thuebao_id
            }
          })
          ds = rs.data.data
          break
        case DichVuVienThong.TSL:
        case DichVuVienThong.NOIBO_2DIEM:
          rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_tsl', {
            params: {
              thueBaoId: thuebao_id
            }
          })
          ds = rs.data.data
          break
        case DichVuVienThong.ADSL:
        case DichVuVienThong.EMAIL:
          rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_adsl', {
            params: {
              thueBaoId: thuebao_id
            }
          })
          ds = rs.data.data
          break
        case DichVuVienThong.MEGAWAN:
        case DichVuVienThong.METRONET:
        case DichVuVienThong.NOIBO_DADIEM:
          rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_megawan', {
            params: {
              thueBaoId: thuebao_id
            }
          })
          ds = rs.data.data
          break
        case DichVuVienThong.GPHONE:
          rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_gp', {
            params: {
              thueBaoId: thuebao_id
            }
          })
          ds = rs.data.data
          break
        case DichVuVienThong.IMS:
          rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_ims', {
            params: {
              thueBaoId: thuebao_id
            }
          })
          ds = rs.data.data
          break
        case DichVuVienThong.MEGA_EYES:
          rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_megaeys', {
            params: {
              thueBaoId: thuebao_id
            }
          })
          ds = rs.data.data
          break
        case DichVuVienThong.DICHVU_CNTT:
        case DichVuVienThong.ANTOAN_BAOMAT_TT:
        case DichVuVienThong.TRUNGTAM_DULIEU:
        case DichVuVienThong.HOINGHI_TRUYENHINH:
          rs = await this.axios.get('web-hopdong/hopdong/lay_ds_danhba_cntt', {
            params: {
              thueBaoId: thuebao_id
            }
          })
          ds = rs.data.data
          break
        default:
          rs = await this.axios.post('web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid', {
            thuebao_id: thuebao_id
          })
          ds = rs.data.data
          break
      }
      if (ds.length > 0) {
        switch (this.dichvuvt_id) {
          case DichVuVienThong.CO_DINH:
            if (ds[0]['trangbi_id']) this.trangbi_id = Number(ds[0]['trangbi_id'])
            else this.trangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI //khách hàng tự trang bị
            break
          case DichVuVienThong.DI_DONG:
            if (ds[0]['goicuoc_id']) {
              this.$refs.cboGoiCuocDD.value = Number(ds[0]['goicuoc_id'])
            }
            this.$refs.txtSimCard.value = ds[0]['simcard']
            if (ds[0]['trangbi_id']) this.$refs.cboTrangBiDD.value = ds[0]['trangbi_id']
            else this.$refs.cboTrangBiDD.value = TRANGBI.KHACHHANG_TU_TRANGBI //khách hàng tự trang bị
            break

          case DichVuVienThong.ADSL:
          case DichVuVienThong.EMAIL:
            this.$refs.txtMaTB_TN.value = ds[0]['matb_tn']
            this.$refs.txtEmailAdsl.value = ds[0]['email']
            this.$refs.cboGoiCuocAdsl.value = Number(ds[0]['tocdo_id'])
            this.$refs.cboMucCuoc.value = Number(ds[0]['muccuoc_id'])
            if (ds[0]['trangbi_id']) this.$refs.cboTrangBiADSL.value = ds[0]['trangbi_id']
            else this.$refs.cboTrangBiADSL.value = TRANGBI.KHACHHANG_TU_TRANGBI //khách hàng tự trang bị
            break
          case DichVuVienThong.MEGA_EYES:
            this.$refs.cboGoiCuocAdsl.value = Number(ds[0]['tocdo_id'])
            if (ds[0]['trangbi_id']) this.$refs.cboTrangBiADSL.value = ds[0]['trangbi_id']
            else this.$refs.cboTrangBiADSL.value = TRANGBI.KHACHHANG_TU_TRANGBI //khách hàng tự trang bị
            break
          case DichVuVienThong.TSL:
          case DichVuVienThong.NOIBO_2DIEM:
            this.$refs.cboTocDoKenhTSL.value = Number(ds[0]['tocdo_id'])
            this.$refs.cboLoaiKenhTSL.value = Number(ds[0]['loaikenh_id'])
            this.$refs.cboThoiHan.value = Number(ds[0]['thoihan_id'])
            this.$refs.cboTrangBi.value = Number(ds[0]['trangbi_id'])
            this.$refs.cboChuQuan.value = Number(ds[0]['chuquan_id'])
            this.$refs.cboLoaiTBTSL.value = Number(ds[0]['thietbidc_id'])
            this.$refs.txtMaHD_TTKD_TSL.value = ds[0]['ma_pl']

            //HienThiTTDiaChi_TSL();
            for (let i = 0; i < ds.length; i++) {
              if (Number(ds[i]['daucuoi_id']) == 1)
                //Nếu là điểm đầu
                this.$refs.txtDiaChiDau.value = ds[i]['diachi']
              else this.$refs.txtDiaChiCuoi.value = ds[i]['diachi']
            }
            break
          case DichVuVienThong.MEGAWAN:
          case DichVuVienThong.METRONET:
          case DichVuVienThong.NOIBO_2DIEM:
            ///TuấnNA thêm code hiển thị các thông tin của thuê bao MGWan --- Ngày 12/09/2010
            if (ds[0]['tocdo_id']) this.$refs.cboTocDo.value = Number(ds[0]['tocdo_id'])
            if (ds[0]['loaimd_id']) this.$refs.cboLoaiModem.value = Number(ds[0]['loaimd_id'])
            if (ds[0]['loaikenh_id']) this.$refs.cboLoaiKenhWan.value = Number(ds[0]['loaikenh_id'])
            if (ds[0]['thietbidc_id']) this.$refs.cboLoaiTBiWan.value = Number(ds[0]['thietbidc_id'])
            if (ds[0]['thoihan_id']) this.$refs.cboThoihanWan.value = Number(ds[0]['thoihan_id'])
            if (ds[0]['chuquan_id']) this.$refs.cboChuQuanWan.value = Number(ds[0]['chuquan_id'])

            this.$refs.txtMaHD_TTKD_MGW.value = ds[0]['ma_pl']
            this.$refs.txtSiteID.value = ds[0]['site_id']
            if (ds[0]['tocdo_pir_id']) this.$refs.cboTocDo_PIR.value = Number(ds[0]['tocdo_pir_id'])

            break
          case DichVuVienThong.GPHONE:
            this.$refs.chkVSVXGP.checked = ds[0]['mc_tbgp_id'] == '1'
            this.$refs.txtSosimGP.value = ds[0]['simcard']

            break
          case DichVuVienThong.IMS:
            if (ds[0]['thietbi_id']) this.$refs.cboThietBi.value = Number(ds[0]['thietbi_id'])
            if (ds[0]['trangbi_id']) this.$refs.cboTrangBiIMS.value = ds[0]['trangbi_id']
            break
          case DichVuVienThong.DICHVU_CNTT:
          case DichVuVienThong.ANTOAN_BAOMAT_TT:
          case DichVuVienThong.HOINGHI_TRUYENHINH:
            if (ds.length > 0) {
              if (ds[0]['linhvuc_id']) this.linhvuc_id = Number(ds[0]['linhvuc_id'])
            }
            this.tocdo_id = 0
            break
          case DichVuVienThong.TRUNGTAM_DULIEU: //nhapt bổ sung
            if (ds.length > 0) {
              if (ds[0]['linhvuc_id']) this.linhvuc_id = Number(ds[0]['linhvuc_id'])
            }
            this.$refs.cboGoiCuocAdsl.value = Number(ds[0]['tocdo_id'])
            this.$refs.cboMucCuoc.value = Number(ds[0]['muccuoc_id'])
            break
          default:
            break
        }
      }
    },
    //endregion

    //region Dịch vụ GT, khác, Tbi đang sử dụng
    async LAY_DS_SUDUNG_DVGT(thuebao_id) {
      rs = await this.axios.post('web-hopdong/hopdong/lay_ds_thuebao_dichvu', {
        hdtbId: 0,
        kieuId: 1,
        loaiTbId: this.loaitb_id,
        thueBaoId: thuebao_id
      })
      this.m_dsThueBao_DichVu = rs.data.data
      this.HT_DichVuGT()
    },

    async LAY_DS_SUDUNG_DVK(db_id, vloaiid) {
      let dset = []
      rs = await this.axios.get('web-hopdong/hopdong/lay_ds_sudung_dvk?dbId=' + db_id + '&loaiId=' + vloaiid)
      dset = rs.data.data

      this.dsDK_DVK = []

      for (let i = 0; i < dset.length; i++) {
        let row = {}
        row.DVKHAC_ID = Number(dset[i]['dvKhacId'])
        row.KIEU_YC = 1
        row.TTTH = TrangThaiCaiDat.MOI
        row.HD_ID = this.hdtb_id
        row.NGAY_YC = this.$refs.dtpNgayYC.value
        row.LOAIID = vloaiid
        this.dsDK_DVK.push(row)
      }

      let str
      if (vloaiid == 1) {
        this.$refs.lvDvkhac.value = ''

        for (let i = 0; i < this.dsDK_DVK.length; i++) {
          rs = await this.axios.get('web-tracuu/danhba/lay_tt_dichvu_khac?dvkhacId=' + this.dsDK_DVK[i]['DVKHAC_ID'])
          let ds = rs.data.data
          str = ds + ' (Đang SD)'
          this.$refs.lvDvkhac.value += str + '\n'
          console.log(this.$refs.lvDvkhac)
        }
      } else {
        this.$refs.lvDvkhac_TT.value = ''

        for (let i = 0; i < this.dsDK_DVK.length; i++) {
          rs = await this.axios.get('web-tracuu/danhba/lay_tt_dichvu_khac?dvkhacId=' + this.dsDK_DVK[i]['DVKHAC_ID'])
          let ds = rs.data.data
          str = ds + ' (Đang SD)'
          this.$refs.lvDvkhac_TT.value += str + '\n'
        }
      }
    },

    //endregion

    //region Hiển thị dịch vụ GT, khác ,TBI
    HT_DichVuGT() {
      //Hiếu sửa thêm lấy tiền dịch vụ 15/09/2010
      let kt = true // Kiểm tra xem có đăng ký dịch vụ nào không, nếu không trả tiền dv, vatdv về 0
      this.tien_dv = 0
      this.vat_dv = 0
      this.DS_ID_DV = ''
      let str
      this.$refs.lvDvgt.dataSource = []
      for (let i = 0; i < this.m_dsThueBao_DichVu.length; i++) {
        if (this.m_dsThueBao_DichVu[i]['huy'] == '1' || this.m_dsThueBao_DichVu[i]['dangky'] == '1') {
          str = this.m_dsThueBao_DichVu[i]['TEN_DVGT']
          if (this.m_dsThueBao_DichVu[i]['dangky'] == '1' && this.m_dsThueBao_DichVu[i]['huy'] == '0') {
            kt = false
            //Hiếu bổ sung code tính tiền dịch vụ
            if (this.m_dsThueBao_DichVu[i]['cuoc_ld']) {
              tien_dv = tien_dv + Number(this.m_dsThueBao_DichVu[i]['cuoc_ld'])
            }
            if (this.m_dsThueBao_DichVu[i]['vat_ld']) {
              vat_dv = vat_dv + Number(this.m_dsThueBao_DichVu[i]['vat_ld'])
            }
            //
            //Lấy danh sách ID dịch vụ gia tăng
            if (this.DS_ID_DV == '') this.DS_ID_DV = this.m_dsThueBao_DichVu[i]['dichvugt_id']
            else this.DS_ID_DV = this.DS_ID_DV + ',' + this.m_dsThueBao_DichVu[i]['dichvugt_id']
            //
            str = str + ' (ĐK)'
          }
          if (this.m_dsThueBao_DichVu[i]['huy'] == '1') str = str + ' (Hủy)'
          this.$refs.lvDvgt.dataSource.push(str)
        }
      }
      if (kt == true) {
        this.DS_ID_DV = ''
        this.tien_dv = 0
        this.vat_dv = 0
      }
      this.HienThiTienDichVu(this.tien_dv, this.vat_dv)
    },

    //endregion

    //region Sua doi dich vu
    btnEditDVGT_Click() {
      let f = new frmDangKyDVGT(this.m_dsThueBao_DichVu, this.dichvuvt_id, this.$refs.dtpNgayYC.value, false)
      f.ShowDialog()
      if (f.chapnhan) {
        this.m_dsThueBao_DichVu = f.dsDK_DVGT
        HT_DichVuGT()
      }
    },

    btnEditDVK_Click() {
      // Không cho đăng ký dịch vụ khác tại form này.
      // Sang form thay đổi thông tin thanh toán mà đăng ký
      //frmDangKyDVK f = new frmDangKyDVK(dsDK_DVK, this.dichvuvt_id, this.$refs.dtpNgayYC.value,1,0);
      //f.ShowDialog();
      //if (f.chapnhan)
      //{
      //    dsDK_DVK = f.dsDK_DVK;
      //    HT_DichVuKhac();
      //}
    },
    //endregion

    //region Kiểm tra dữ liệu
    KiemTraDL_KhachHang() {
      //tuan code them cac dieu kien kiem tra du lieu -- Ngay 06/07/2010
      if (!this.$refs.txtNgaySN.value) {
        this.$toast.success('Hãy nhập ngày sinh !')
        this.$refs.txtNgaySN.focus()
        return false
      }

      if (this.$refs.txtNguoiDD.value == '') {
        this.$toast.success('Hãy nhập tên người đại diện !')
        this.$refs.txtNguoiDD.focus()
        return false
      }
      if (!this.$refs.txtNgayCap.value) {
        this.$toast.success('Hãy nhập ngày cấp giấy tờ !')
        this.$refs.txtNgayCap.focus()
        return false
      }

      if (this.$refs.txtSoGT.value == '') {
        this.$toast.success('Hãy nhập số giấy tờ !')

        this.$refs.txtSoGT.focus()
        return false
      }

      if (this.$refs.txtTelKH.value == '') {
        this.$toast.success('Hãy nhập số điện thoại khách hàng !')

        this.$refs.txtTelKH.focus()
        return false
      }
      //CuongNM - Kiểm tra nhập số điện thoại theo đúng chuẩn - VB 3309/VNPT VNP-KTNV
      if (this.$refs.txtTelKH.value.length < 10) {
        this.$toast.error('Số điện thoại liên hệ của khách hàng không được nhỏ hơn 10 ký tự.')

        this.$refs.txtTelKH.focus()
        return false
      }

      if (!this.$refs.txtMaNVTC.value) {
        this.$toast.error('Hãy chọn Mã NVTC!')

        this.$refs.txtMaNVTC.focus()
        return false
      }

      if (this.$refs.txtTelKH.value.length > 11) {
        this.$toast.error('Số điện thoại liên hệ của khách hàng không được quá 11 ký tự.')

        this.$refs.txtTelKH.focus()
        return false
      }
      if (this.$refs.txtTelKH.value.charAt(0) != 0) {
        this.$toast.error('Dữ liệu điện thoại khách hàng phải bắt đầu là số 0')

        this.$refs.txtTelKH.focus()
        return false
      }

      if (this.$refs.tbDienThoaiLienHe.value == '') {
        this.$toast.error('Bạn hãy nhập số điện thoại thanh toán!')
        this.$refs.tbDienThoaiLienHe.focus()
        return false
      }
      if (this.$refs.tbDienThoaiLienHe.value.length < 10) {
        this.$toast.error('Số điện thoại thanh toán không được nhỏ hơn 10 ký tự!')
        this.$refs.tbDienThoaiLienHe.focus()
        return false
      }

      if (this.$refs.tbDienThoaiLienHe.value.length > 11) {
        this.$toast.error('Số điện thoại thanh toán không được quá 11 ký tự!')
        this.$refs.tbDienThoaiLienHe.focus()
        return false
      }
      if (this.$refs.tbDienThoaiLienHe.value.charAt(0) != '0') {
        this.$toast.error('Dữ liệu điện thoại thanh toán phải bắt đầu là số 0')
        this.$refs.tbDienThoaiLienHe.focus()
        return false
      }

      //if (Number(this.$refs.cboGiayToKH.value) == 1 && this.$refs.txtSoGT.value)
      //{
      //    if (!Common.Utilities.CommonFunction.CheckIsNumber(this.$refs.txtSoGT.value))
      //    {
      //        this.$toast.error("Bạn đang chọn loại giấy tờ: " + this.$refs.cboGiayToKH.value + "\nDữ liệu nhập phải là kiểu số!");
      //        if (tabThongTin.SelectedIndex == 0)
      //        this.$refs.txtSoGT.SelectAll();
      //        this.$refs.txtSoGT.focus();
      //        return false;
      //    }
      //}
      //KiemTraSoGT();
      //end tuan code
      if (this.$refs.txtTenKH.value == '') {
        this.$toast.error('Hãy nhập tên khách hàng !')

        this.$refs.txtTenKH.focus()
        return false
      }

      if (this.$refs.txtDiaChiKH.value == '' || this.quankh_id == 0 || this.phuongkh_id == 0) {
        this.$toast.error('Hãy nhập địa chỉ khách hàng !')

        this.$refs.txtDiaChiKH.focus()
        return false
      }

      // if (this.$refs.txtMaHDCu.value == "") {
      //   this.$toast.error("Hãy nhập mã hợp đồng cũ !");
      //   // tabThongTin.SelectedIndex = 0;
      //   this.$refs.txtMaHDCu.focus();
      //   return false;
      // }

      if (!this.TNC1_ID || !this.TNC2_ID || !this.TNC3_ID) {
        this.$toast.error('Bạn chưa chọn chi tiết ngành nghề.Hãy kiểm tra lại !')
        this.$refs.btnNganhNghe.focus()
        return false
      }
      //Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa

      if (this.khong_batbuoc_ctv == 0) {
        if (!this.$refs.chkCTV.checked || !this.$refs.txtCTV.value || this.ctv_id == 0) {
          this.$toast.error('Bạn chưa chọn nhân viên phát triển!')
          this.$refs.txtCTV.focus()
          return false
        }
      }
      if (this.batbuoc_gioithieu_chuyenquyen == 1) {
        if (!this.$refs.chkNguoiGT.checked || !this.$refs.txtNguoiGT.value || this.nguoigt_id == 0) {
          this.$toast.error('Bạn chưa chọn người giới thiệu hợp đồng/phụ lục')
          this.$refs.txtNguoiGT.focus()
          return false
        }
      }

      if (this.$refs.chkCTV.checked && !this.$refs.txtCTV.value && this.ctv_id == 0) {
        this.$toast.error('Bạn chưa chọn nhân viên phát triển!')
        this.$refs.txtCTV.focus()
        return false
      }
      if (this.$refs.chkNguoiGT.checked && !this.$refs.txtNguoiGT.value && this.nguoigt_id == 0) {
        this.$toast.error('Bạn chưa chọn nhân viên giới thiệu hợp đồng!')
        this.$refs.txtNguoiGT.focus()
        return false
      }
      // fix lỗi không chọn loại KH
      if (this.$refs.cboLoaiKH.value == '-1' || !this.$refs.cboLoaiKH.value) {
        this.$toast.error('Bạn chưa chọn loại khách hàng!')
        this.$refs.cboLoaiKH.focus()
        return false
      }

      return true
    },

    IsNumber(pValue) {
      for (let c in pValue) {
        if (!Char.IsDigit(c)) return false
      }
      return true
    },

    async KiemTraDL_ThanhToan() {
      //tuan code
      // kiem tra ma nhan vien thu cuoc
      if (this.$refs.txtMaNVTC.value == '') {
        this.$toast.success('Hãy nhập mã nhân viên thu cước !')

        this.$refs.txtMaNVTC.focus()
        return false
      }
      if (this.khachhang_id != 0) {
        if (this.$refs.cboMaDD.value == '') {
          this.$toast.success('Hãy chọn mã dại diện !')
          this.$refs.cboMaDD.focus()
          return false
        }

        if (this.$refs.cboMaDD.length > 0) {
          if (this.$refs.cboMaDD.SelectedIndex < 0) {
            this.$toast.success('Hãy chọn mã đại diện')
            this.$refs.cboMaDD.focus()
            return false
          }
        }
      }

      if (this.$refs.txtTenTT.value == '') {
        this.$toast.success('Hãy nhập tên thanh toán !')

        this.$refs.txtTenTT.focus()
        return false
      }

      if (this.$refs.txtDiaChiTT.value == '' || this.quantt_id == 0 || this.phuongtt_id == 0) {
        this.$toast.success('Hãy kiểm tra lại địa chỉ thanh toán !')

        this.$refs.txtDiaChiTT.focus()
        return false
      }
      if (this.$refs.txtMaTT.value) {
        let ds = await tcdanhba.LAY_DB_THEO_MATB(this.$refs.txtMaTT.value, 0, 0)
        if (ds.length > 0) {
          if (this.$refs.txtTenTT.value != ds[0]['ten_tt'] && tsbtnNhapMoi.Enabled == false) {
            if (MessageBox.Show('Bạn có muốn thay đổi tên thanh toán mới cho mã thanh toán cũ: ' + this.$refs.txtMaTT.value + ' không ?', 'Thông báo', MessageBoxButtons.YesNo, MessageBoxIcon.Warning) == DialogResult.Yes) {
              return true
            } else {
              this.HienThiTT_DanhBa_KH_NhanCQ(this.$refs.txtMaTT.value)
              return false
            }
          }
        }
      }

      //Hieutc: 11.10.2011: Kiểm tra mã thanh toán đã theo đúng chuẩn chưa
      if (this.$refs.txtMaTT.value) {
        if (laphd.Kiemtra_makh_mtt(this.$refs.txtMaTT.value, 2, LoaiHopDong.CHUYEN_QUYEN) == 0) {
          this.$toast.error('Mã thanh toán không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
          this.$refs.txtMaTT.focus()
          return false
        }
      }

      if (Number(this.$refs.cboHTTT.value) == HinhThucThanhToan.UYNHIEM_THU) {
        if (this.$refs.txtSTK_TT.value == '') {
          this.$toast.success('Hãy nhập số tài khoản!')
          this.$refs.txtSTK_TT.focus()
          return false
        }
      }

      //KhaiVK: 12/06/2018: Kiểm tra mã số thuế theo chuẩn
      maSoThue = this.$refs.txtMST_TT.value
      if (maSoThue != string.Empty && this.ts_kt_mst == 1) {
        ketQua = CommonUtilities.KiemTraDinhDangMaSoThue(maSoThue)
        if (ketQua != 'OK') {
          this.$toast.error(ketQua)
          this.$refs.txtMST_TT.focus()
          return false
        } else {
          let ds_maKH = []
          ds_maKH = await tcdanhba.LAY_DS_MAKH_THEO_MST(maSoThue, this.hdkh_id, this.khachhang_id)
          if (ds_maKH.length == 1) {
            this.$toast.success('Mã số thuế ' + maSoThue + ' đang được sử dụng bởi mã khách hàng: ' + ds_maKH[0]['ma_kh'] + '.')
            this.$refs.txtMST_TT.focus()
            return false
          } else if (ds_maKH.length > 1) {
            str = ''
            for (let i = 0; i < ds_maKH.length; i++) {
              str = str + ds_maKH[0][i]['ma_kh'] + ','
            }
            this.$toast.success('Mã số thuế ' + maSoThue + ' đang được sử dụng bởi các mã khách hàng: ' + str.Substring(0, str.length - 1) + '.')
            this.$refs.txtMST_TT.focus()
            return false
          }
        }
      }

      //Them chặn theo httt theo yc chị Bách
      if (!KIEMTRA_HTTT(GlobalVar.LAY_HINHTHUC_TT(), this.httt_id_cu, Number(this.$refs.cboHTTT.value))) {
        return false
      }
      return true
    },

    KiemTraDL_ThueBao() {
      if (this.$refs.txtMaHDCu.value == '' && this.khachhang_id != 0) {
        this.$toast.success('Hãy nhập mã hợp đồng !')
        tabThongTin.SelectedIndex = 0
        this.$refs.txtMaHDCu.focus()
        return false
      }
      if (this.$refs.cboKieuLD.value == null) {
        this.$toast.success('Hãy chọn kiểu yêu cầu cho thuê bao này !')
        return false
      }
      if (this.$refs.txtTenTB.value == '') {
        this.$toast.success('Hãy nhập tên thuê bao !')
        tabThongTin.SelectedIndex = 0
        this.$refs.txtTenTB.focus()
        return false
      }

      if (this.$refs.txtDiaChiTB.value == '' || this.quantb_id == 0 || this.phuongtb_id == 0) {
        ///TuannA: Zem code không sử dụng hàm này -- ngày 22.06.2012
        ///Theo nghiệp vụ cũ: dskh_thieudc là bảng lưu những địa chỉ chưa cập nhật
        ///còn hiện tại tất cả các khách hàng phải ký lại nên không dùng tới hàm và bảng này.
        //laphd.CAPNHAT_KH_THIEUDC(this.thuebao_id, 3);
        this.$toast.success('Hãy nhập địa chỉ thuê bao !')
        this.$refs.txtDiaChiTB.focus()
        return false
      }
      //if (this.$refs.txtDiachiLD.value == "" || this.quanld_id == 0 || this.phuongld_id == 0)
      //{
      //    this.$toast.success("Hãy nhập địa chỉ lắp đặt !");
      //    this.$refs.txtDiachiLD.focus();
      //    return false;

      //}
      if (!this.$refs.txtNgaySinhTB.value) {
        this.$toast.success('Hãy nhập ngày sinh thuê bao !')
        this.$refs.txtNgaySinhTB.focus()
        return false
      }
      //không kiểm tra ngày sinh thuê bao trên 18 tuổi
      //if (this.$refs.txtNgaySinhTB.value != "  /  /")
      //{
      //    if (Number(this.$refs.cboLoaiKH.value) == Loai_KH.CANHAN_HOGIADINH)//Nếu là khách hàng cá nhân thì kiểm tra đã đủ 18 tuổi chưa
      //    {
      //        let checkdata = new CheckDataFacade();

      //        if (checkdata.KIEMTRA_NGAYSINH(this.$refs.txtNgaySinhTB.value) == 0)
      //        {
      //            tabThongTin.SelectedIndex = 0;
      //            this.$toast.error("Thuê bao chưa đủ 18 tuổi để làm hợp đồng!");
      //            this.$refs.txtNgaySinhTB.focus();
      //            return false;
      //        }
      //    }
      //    //if (checkdata.KIEMTRA_NGAYSINH(this.$refs.txtNgaySinhTB.value) == 0)
      //    //{
      //    //    this.$toast.error("Thuê bao chưa đủ 18 tuổi để lập hợp đồng/phụ lục!");
      //    //    tabThongTin.SelectedIndex = 0;
      //    //    this.$refs.txtNgaySinhTB.focus();
      //    //    return false;
      //    //}
      //}
      //if (this.CURRENT_DVVT_ID == DichVuVienThong.CO_DINH
      //    || this.CURRENT_DVVT_ID == DichVuVienThong.GPHONE)
      //{
      //    if (this.CURRENT_LOAITB_ID()== LoaiHinhTB.DIENTHOAI_CD
      //        || this.CURRENT_LOAITB_ID()== LoaiHinhTB.GPHONE)
      //    {
      //        if (this.$refs.cboMucCuocTB.length == 0)
      //        {
      //            if (tabThongTin.SelectedIndex == 1)
      //                tabThongTin.SelectedIndex = 0;
      //            this.$toast.error("Chưa có mức cước thuê bao tháng cho dịch vụ cố định/Gphone. Bạn hãy kiểm tra lại thông tin !");
      //            this.$refs.cboMucCuocTB.focus();
      //            return false;
      //        }
      //    }
      //}

      // hoangpkn : 31/10/2013. Thêm hàm kiểm tra gói theo yc a Thắng
      kq = ''
      kq = laphd.KIEMTRA_THAYDOI_GOI_DADV_2(this.thuebao_id, LoaiHopDong.CHUYEN_QUYEN)
      if (kq != '1') {
        this.$toast.error(kq)
        return false
      }
      // end hoàng thêm

      if (this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU || this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH) {
        // Nếu là thuê bao cần chuẩn hóa --> ko cho thực hiện hợp đồng. cần phải Đặt mới (chon kieu ld = chuan hoa) hoặc làm hợp đồng Tạm dừng/Thanh lý
        if (check_chuahoa_cntt) {
          if (new LapHopDongFacade2().check_chuahoa_cntt(this.thuebao_id, LoaiHopDong.CHUYEN_QUYEN)) {
            //this.$toast.error("Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới ");
            if (MessageBox.Show('Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.\n Bạn muốn chuẩn hóa thông tin ?', 'Thông báo', MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes) return false
            {
              var frm = new frmDatMoi()
              frm.MdiParent = this.MdiParent
              frm.Show()
              frm.Load_hopdong_chuanhoa_cntt(this.$refs.txtMaKHCu.value, this.dichvuvt_id, loaitb_id, this.thuebao_id)
            }
            return false
          }
        }
      }

      return true
    },

    KemTraDL_HD_DC(thuebao_id) {
      kiemtra = laphd.KiemTra_HD_CQ(this.thuebao_id, 3)
      if (kiemtra) {
        this.$toast.success('Thuê bao đã thực hiện chuyển dịch, bạn không thể thực hiện chuyển quyền!')
        return false
      }
      return true
    },

    //endregion

    //region ListView SelectedIndexChanged
    async lvwThanhToan_SelectedIndexChanged() {
      try {
        if (this.$refs.lvwThanhToan.getSelectedRecords().length > 0) {
          this.hdtt_id = Number(this.$refs.lvwThanhToan.getSelectedRecords()[0].hdtt_id)
          this.HienThiTTHopDongTT(await tchopdong.LAY_DS_HOPDONG_TT_THEO_HDTT_ID(this.hdtt_id))
          this.$refs.cboTT.value = this.hdtt_id
        }
      } catch (err) {
        console.log(err)
      }
    },

    async lvwThueBao_SelectedIndexChanged() {
      try {
        if (this.$refs.lvwThueBao.dataSource.length > 0) {
          let selected = this.$refs.lvwThueBao.getSelectedRecords()
          this.hdtb_id = selected[0] ? selected[0].hdtb_id : 0
          this.HienThiTTHopDongTB(await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDTB_ID(this.hdtb_id))
          //HienThiTienTheoKhoanMuc(this.hdtb_id, tien_cd, this.loaihd_id, this.dichvuvt_id, khlon_id, loaitb_id, this.kieuld_id, this.doituong_id, this.ngaylhd, nhaphd_kt, this.phuongld_id);
        }
      } catch (err) {
        console.log(err)
      }
    },
    //endregion

    //region Form_KeyUp
    frmChuyenQuyenSD_KeyUp() {
      switch (e.KeyCode) {
        case Keys.F2:
          tabThongTin.SelectedIndex = 0
          this.$refs.txtMaTB.focus()
          break
        case Keys.F3:
          this.$refs.txtMaKH.focus()
          break
        case Keys.F5:
          NhapMoi()
          break
        case Keys.F6:
          ThemHDThanhToan()
          break
        case Keys.F7:
          ThemHDThueBao()
          break
        case Keys.F8:
          XoaHDKH()
          break
        case Keys.F9:
          if (tsbtnGhiLai.Enabled == true) CapNhat()
          break
      }
    },
    //endregion

    //region this.$refs.cboHTTT SelectedValueChanged
    async cboHTTT_SelectedValueChanged() {
      if (this.$refs.cboHTTT.length > 0) {
        if (this.$refs.cboHTTT.value == null) return
        if (this.$refs.cboHTTT.value == 'System.Data.letView') return

        httt_id = Number(this.$refs.cboHTTT.value)
        if (httt_id == HinhThucThanhToan.UYNHIEM_THU) {
          cboNganHangTT.Enabled = true

          if (cboNganHangTT.dataSource != null) {
            let obj = new CheckDataFacade()

            temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nganhang', {
              param: this.$refs.cboNganHangTT.value,
              type: 1
            })
            this.loai_nh = temp.data.data
            if (this.loai_nh == '2') {
              this.$refs.btnNSNN.disabled = false
            } else this.$refs.btnNSNN.disabled = true
          }
        } else {
          cboNganHangTT.Enabled = false
          btnNSNN.Enabled = false
        }

        //region Lấy lại thông tin ĐVQL, NVTC theo hình thức TT
        if (TUYENTHU_HTTT == 1) {
          if (this.phuongtt_id != 0 && (this.phott_id != 0 || this.aptt_id != 0 || this.khutt_id != 0)) {
            //region bỏ
            //    let ds = [];

            //    ds = new LapHopDongFacade2().Lay_ds_donviql_v2(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, 1,
            //        Number(this.$refs.cboLoaiKH.value), Number(this.$refs.cboHTTT.value));

            //    if (ds.length <= 0)
            //    {
            //        if (this.$refs.cboDonviQL.length != 1)
            //        {
            //            this.$toast.error("Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!");
            //            this.$refs.cboDonviQL.value = "";
            //            return;
            //        }
            //    }
            //    else if (ds.length == 1)
            //        this.$refs.cboDonviQL.value = Number(ds[0]["donvi_id"]);
            //    else
            //    {
            //        let frm = new frmChonDonVi(ds);
            //        frm.donvi_index_id = Number(this.$refs.cboDonviQL.value);
            //        frm.ShowDialog();
            //        if (frm.chapnhan)
            //        {
            //            this.$refs.cboDonviQL.value = frm.this.donvi_ql_id;
            //        }
            //        else
            //        {
            //            return;
            //        }
            //    }
            //}
            //endregion

            HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, Number(this.$refs.cboDonviQL.value), this.dacdiemtt_id)
          }
          //HienThi_NVQL_AM_HTTT(Number(this.$refs.cboLoaiKH.value), Loai_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, Number(this.$refs.cboDonviQL.value), Number(this.$refs.cboHTTT.value));
        }
        //endregion
      }
    },
    //endregion

    //region btnLayMaGD_Click
    btnLayMaGD_Click() {
      //this.tt_nd.ngay_cn
      this.$refs.searchContractModal.clearData()
      this.modalSearch.loaihd_id = LoaiHopDong.CHUYEN_QUYEN
      this.modalSearch.tthd_id = TrangThaiHD.MOI
      console.log(this.modalSearch)
      this.$refs.searchContractModal.loadPopup()
      this.$refs.popupFrmTraCuuHopDong.show()
    },
    async acceptSearchContract(item) {
      this.$refs.popupFrmTraCuuHopDong.hide()
      this.$refs.txtMaGD.value = item.hopdong.ma_gd ? item.hopdong.ma_gd : ''
      this.$refs.txtMaGD.focus()
      if (this.$refs.txtMaGD.value) {
        this.txtMaGD_KeyPress()
        // this.HienThiTTHopDongKH(
        //   await tchopdong.LAY_DS_HOPDONG_THEO_MA_GD(
        //     this.$refs.txtMaGD.value,
        //     LoaiHopDong.CHUYEN_QUYEN,
        //     this.tt_nd.donvi_id,
        //     TrangThaiHD.MOI,
        //     this.tt_nd.nhanvien_id,
        //     0
        //   )
        // );
      }
    },
    //endregion

    //region bntLayMaKh_Click
    bntLayMaKh_Click() {
      this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
      this.popupComponentName = 'SearchAccount'
      try {
        this.popupComponent.data.isPopup = true
        this.popupComponent.data.vdichvuvt_id = 0
      } catch (ex) {
        if (this.debug) console.error(ex)
      }
      this.Popup('popupComponents')
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    searchAccountDialogClose(data) {
      if (data != null) {
        this.$refs.txtMaKH.value = data.ma_tb
        this.$refs.txtMaKH.focus()
      }
      this.$refs.searchAccountDialog.hide()
    },
    //endregion

    //region Thanh Toan
    async tsbtnThanhToan_Click() {
      if (this.hdkh_id == 0) {
        this.$toast.error('Hãy ghi lại hợp đồng trước khi thanh toán!')
        return
      }
      rs = await this.axios.post('web-hopdong/tachnhapthuebao/kiemtra_thanhtoan_datmoi_lh', {
        hdkh_id: this.hdkh_id
      })
      if (rs.data.data == '0') {
        this.$toast.error('Hợp đồng chưa có thông tin liên hệ, bạn phải bổ sung thông tin liên hệ trước khi thanh toán !')
        return
      }
      this.userComponentModule = thanhToan
      this.popupComponentName = 'thanhtoanPopup'
      // goi su kien load lai ma giao dich
      this.txtMaGDTT = this.$refs.txtMaGD.value
      // tao component neu chua tao, show popup khi da goi xong ham khoi tao
      if (this.paymentloaded) this.$refs.thanhtoanDialog.show(true)
    },
    async paymentcreated(val) {
      this.paymentloaded = true
      this.$refs.thanhtoanDialog.show(true)
    },
    //endregion

    //region Focus Enter
    txtTenKH_Leave() {
      //this.$refs.txtTenKH.value = (this.$refs.txtTenKH.value);
      if (this.$refs.txtNguoiDD.value == '') this.$refs.txtNguoiDD.value = this.$refs.txtTenKH.value
      if (this.$refs.txtTenTT.value == '') this.$refs.txtTenTT.value = this.$refs.txtTenKH.value
      if (this.$refs.txtTenTB.value == '') this.$refs.txtTenTB.value = this.$refs.txtTenKH.value
    },
    //endregion

    //region Form_SizeChanged
    lvwThueBao_SizeChanged() {
      CommonUtilities.lsvSizeChanged(this.$refs.lvwThueBao)
    },

    lvwThanhToan_SizeChanged() {
      CommonUtilities.lsvSizeChanged(this.$refs.lvwThanhToan)
    },
    //endregion

    //region this.$refs.cboKieuLD_SelectedValueChanged
    async cboKieuLD_SelectedValueChanged() {
      if (this.$refs.cboKieuLD.length > 0) {
        if (!this.$refs.cboKieuLD.value) return
        if (Number(this.$refs.cboKieuLD.value) == KieuLapDat.STADSL_DOI_TOCDO) {
          this.$refs.cboMucCuoc.disabled = false
          this.$refs.cboGoiCuocAdsl.disabled = false
          this.$refs.txtEmailAdsl.disabled = false
        } else {
          this.$refs.cboMucCuoc.disabled = true
          this.$refs.cboGoiCuocAdsl.disabled = true
          this.$refs.txtEmailAdsl.disabled = true
        }
        ///TuấnNA bổ sung hàm lấy tiền lên ô text box --- Ngày 15/09/2010
        this.LayTienTheoKhoanMuc()
        if (Number(this.$refs.cboKieuLD.value) == KieuLapDat.STCD_DOI_DICHVU) {
          this.$refs.btnEditDVGT.disabled = false
        } else {
          ///TuấnNa sửa code lấy lại danh sách dịch vụ cộng thêm ban đầu của thuê bao
          /// Trong trường hợp user lập hợp đồng chọn yêu cầu CQ CĐịnh + đổi dịch vụ cộng thêm
          /// ---> Sửa thành CQ cố định
          /// --- Ngày 30/08/2010
          this.m_dsThueBao_DichVu = await tchopdong.LAY_DS_THUEBAO_DICHVU(0, this.thuebao_id, loaitb_id, 1)
          btnEditDVGT.Enabled = false
          this.$refs.lvDvgt.dataSource = []
        }
      }
    },
    //endregion

    //region btnInHopDong_Click
    btnInHopDong_Click() {},
    //endregion

    //region tsbtnGiaoPhieu_Click
    tsbtnGiaoPhieu_Click() {
      //frmGiaoPhieu f = new frmGiaoPhieu(this.$refs.txtMaGD.value, 1, HuongGiao.HOANTHIEN_HS, this.hdtb_id, 0, "0");
      //f.ShowDialog();
      //if (this.$refs.txtMaGD.value)
      //{
      //    HienThiTTHopDongKH(await tchopdong.LAY_DS_HOPDONG_THEO_MA_GD(this.$refs.txtMaGD.value, LoaiHopDong.CHUYEN_QUYEN, this.tt_nd.donvi_id, TrangThaiHD.MOI, this.tt_nd.nhanvien_id, 0));
      //}
    },
    //endregion

    chkNganHangKH_CheckedChanged() {
      if (this.$refs.chkNganHangKH.checked == true) {
        this.$refs.cboNganHangKH.Enabled = true
      } else this.$refs.cboNganHangKH.Enabled = false
    },

    //region btnLayMaTT_Click
    async btnLayMaTT_Click() {
      if (this.khachhang_id == 0) {
        this.$toast.error('Bạn không thể chọn mã thanh toán của khách hàng mới. Hãy kiểm tra lại!')
        return
      }

      if (this.$refs.txtMaKH.value) {
        this.popupComponentData = this.khachhang_id
        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonMaTT')
        this.popupComponentName = 'popupChonMaTT'
        this.Popup('popupComponents')
      }
    },
    //endregion

    async txtMaGDKH_KeyPress() {
      if (this.$refs.txtMaGD.value) {
        HienThiTTHopDongKH(await tchopdong.LAY_DS_HOPDONG_THEO_MA_GD(this.$refs.txtMaGD.value, LoaiHopDong.CHUYEN_QUYEN, this.tt_nd.donvi_id, TrangThaiHD.MOI, this.tt_nd.nhanvien_id, 0))
      }
    },

    bntLayMaKh_Click_1() {
      let f = new frmTraCuuDanhBa(0, 1)
      f.ShowDialog()
      if (f.chapnhan) {
        this.$refs.txtMaKH.value = f.ma_kh
        this.$refs.txtMaKH.focus()
      }
    },

    txtNgaySN_Leave() {
      console.log('set ds[0]["ngay_sn"] 5')
      if (!this.$refs.txtNgaySinhTB.value) this.$refs.txtNgaySinhTB.value = this.$refs.txtNgaySN.value
    },
    txtNgaySN_TextChanged() {
      //if (this.$refs.txtNgaySinhTB.value == "  /  /")
      //    this.$refs.txtNgaySinhTB.value = this.$refs.txtNgaySN.value;
    },

    async btnTKMaTB_Click() {
      if (this.$refs.txtMaTB.value) {
        let frm = new frmTraCuuThueBao()
        if (this.khachhang_id_cq != 0) {
          frm.Khachhang_id = this.khachhang_id_cq
        } else {
          let ds_khcu = []
          ds_khcu = await tchopdong.LAY_BIENDONG_THEO_HDTB_ID(this.hdtb_id)
          if (ds_khcu.length > 0) {
            this.khachhang_id_cq = Number(ds_khcu[0]['khachhang_id'])
            console.log('set khachhang_id_cq', ds[0]['khachhang_id'])
            frm.Khachhang_id = this.khachhang_id_cq
          } else {
            MessageBox.Show('Chưa có thông tin về khách hàng chuyển quyền có thuê bao: ' + this.$refs.txtMaTB.value + '\nKiểm tra lại !', 'Thông báo', MessageBoxButtons.OK, MessageBoxIcon.Warning)
            this.$refs.txtMaTB.focus()
            this.$refs.txtMaTB.SelectAll()
            return
          }
        }
        frm.this.loaihd_id = LoaiHopDong.CHUYEN_QUYEN
        frm.this.ma_gd = this.$refs.txtMaGD.value
        frm.TinhTrang = 'Đang chuyển quyền'
        //122474
        frm.ShowDialog()
        if (frm.ChapNhan) {
          if (frm.MaThueBao != null) {
            if (frm.MaThueBao) this.$refs.txtMaTB.value = frm.MaThueBao
            if (frm.this.dichvuvt_id != 0) this.$refs.cboDichVuVT.value = frm.this.dichvuvt_id
          }
          this.$refs.txtMaTB.focus()
        }
      } else MessageBox.Show('Bạn chưa nhập mã thuê bao!!!', 'Thông báo', MessageBoxButtons.OK, MessageBoxIcon.Warning)
    },

    cboLoaiKH_SelectedValueChanged() {
      //tuan code
      // cho phep hien thi chọn gioi tinh voi nhung khac hang la ca nhan, ho gia dinh
    },

    txtMaKHCu_TextChanged() {},

    txtDiaChiKH_TextChanged() {
      //this.$refs.txtDiaChiNDD.value = this.$refs.txtDiaChiKH.value;
      //this.$refs.txtDiaChiTT.value = this.$refs.txtDiaChiKH.value;
      //this.$refs.txtDiaChiCT.value = this.$refs.txtDiaChiKH.value;
    },

    txtDiaChiTT_TextChanged() {
      //this.$refs.txtDiaChiCT.value = this.$refs.txtDiaChiTT.value;
      //if (this.phott_id != 0 && this.phuongtt_id != 0)
      //{
      //    this.donvi_qlmoi = LayDonVi_QL("", this.phuongtt_id, this.phott_id);
      //    this.$refs.cboDonviQL.value = this.donvi_qlmoi;
      //}
      ////if(this.clear_manvtc== "1")
      ////    this.$refs.txtMaNVTC.value = "";
    },

    //Tuan them ham kiem tra so giay to -- Ngay 06/07/2010
    //region KiemTraSoGT
    async KiemTraSoGT() {
      let ds_maKH = []
      rs = await this.axios.post('/web-hopdong/lapdatmoi/lay_ds_makh_theo_sogt', {
        hdkh_id: this.hdkh_id,
        so_gt: this.$refs.txtSoGT.value,
        vhdkh_id: this.hdkh_id,
        vloaihd_id: 0,
        vso_gt: this.$refs.txtSoGT.value
      })
      ds_maKH = rs.data.data
      if (ds_maKH.length == 1) {
        if (this.$refs.txtMaKH.value != ds_maKH[0]['ma_kh']) {
          if (ds_maKH[0]['KQ'] == '1') {
            // Tìm thấy trong danh bạ
            if (MessageBox.Show('Số giấy tờ ' + this.$refs.txtSoGT.value + ' đang được sử dụng bởi mã khách hàng: ' + ds_maKH[0]['ma_kh'] + '\nBạn có muốn sử dụng thông tin khách hàng cũ không?', 'Thông báo', MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes) {
              //Lấy thông tin cũ của khách hàng khi chưa lập hợp đồng - Hieutc 21.10.2010
              if (this.hdkh_id == 0) {
                this.$refs.txtMaKH.value = ds_maKH[0]['ma_kh']
                HienThiTT_DanhBa_KH_NhanCQ(this.$refs.txtMaKH.value)
              } else {
                this.$toast.error('Hợp đồng/phụ lục đã được lập, bạn không được sử dụng số giấy tờ của khách hàng khác!')
                //this.$refs.txtSoGT.focus();
                //this.$refs.txtSoGT.value = "";
                //this.$refs.txtSoGT.SelectAll();
              }
            } else {
              //this.$refs.txtSoGT.focus();
              //this.$refs.txtSoGT.value = "";
              //this.$refs.txtSoGT.SelectAll();
            }
          } // = 2--> Tìm thấy trong hợp đồng
          else {
            this.$toast.error('Số giấy tờ: ' + this.$refs.txtSoGT.value + ' đang được sử dụng trong hợp đồng có mã khách hàng: ' + ds_maKH[0]['ma_kh'] + '\nBạn phải sử dụng số giấy tờ khác !')
            //this.$refs.txtSoGT.focus();
            //this.$refs.txtSoGT.value = "";
          }
        }
      } else if (ds_maKH.length > 1) {
        str = ''
        for (let i = 0; i < ds_maKH.length; i++) {
          str = str + ds_maKH[0][i]['ma_kh'] + ','
        }
        MessageBox.Show('Số giấy tờ ' + this.$refs.txtSoGT.value + ' đang được sử dụng bởi các mã khách hàng: ' + str.Substring(0, str.length - 1) + '.\nĐề nghị bạn ký lại hợp đồng gốc trước khi lập phụ lục.', 'Thông báo', MessageBoxButtons.OK, MessageBoxIcon.Warning)
        //this.$refs.txtSoGT.focus();
        //this.$refs.txtSoGT.value = "";
        //this.$refs.txtSoGT.SelectAll();
      }
    },
    //endregion

    txtSoGT_Leave() {
      KiemTraSoGT() //LS ko sư dung
    },

    txtNguoiDD_Leave() {
      this.$refs.txtNguoiDD.value = this.$refs.txtNguoiDD.value
    },

    txtNgayCap_Leave() {
      //Common.Utilities.CommonFunction.ValidatingDateTime(this.$refs.txtNgayCap);
      //if (this.$refs.txtNgayCap.value == "  /  /")
      //{
      //    this.$toast.error("Bạn chưa nhập ngày cấp giấy tờ");
      //    this.$refs.txtNgayCap.focus();
      //}
    },

    txtNgayHetHan_Leave() {
      Common.Utilities.CommonFunction.ValidatingDateTime(this.$refs.txtNgayHetHan)
    },

    txtNgayCapHK_Leave() {
      Common.Utilities.CommonFunction.ValidatingDateTime(this.$refs.txtNgayCapHK)
    },

    txtNgaySinhTB_Leave() {
      Common.Utilities.CommonFunction.ValidatingDateTime(this.$refs.txtNgaySinhTB)
    },
    //End Tuan

    //region Hàm đồng bộ vinaphone

    //region Insert_HopDong_ChuyenQuyen_VinaPhone
    /// <summary>
    /// Hàm thực hiện đẩy thông tin hợp đồng thuê bao lên VinaPhone.
    /// </summary>
    /// <returns>"OK": thành công</returns>

    //endregion

    //region Delete_HopDong_ChuyenQuyen_VinaPhone
    Delete_HDCQ_VinaPhone(ma_gd) {
      try {
        let checkdata = new CheckDataFacade()

        if (!this.tt_nd.USER_NEO) {
          return 'Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.'
        }
        var rs = new CCBSFacade().TS_LAPHD_CHUYENQUYEN_SUDUNG_XOAHD(this.tt_nd.tentat_ccbs, this.tt_nd.tentat_ccbs, this.ma_gd, this.tt_nd.USER_NEO)
        if (rs.IssSuccessed) {
          new KNotifications('Thành công', 'Xóa hợp đồng chuyển quyền {0} trên VinaPhone thành công!'.FormatText(this.ma_gd), true).Show()
          return 'OK'
        } else return rs.Message
      } catch (err) {
        return 'Có lỗi xảy ra trong quá trình xóa hợp đồng VinaPhone: ' + err.message
      }
    },
    //endregion

    //region Delete_ThueBao_ChuyenQuyen_VinaPhone
    /// <summary>
    /// Hàm xóa dữ liệu thuê bao trên VinaPhone
    /// </summary>
    /// <param name="mahd_neo">mahd_neo</param>
    /// <returns>"OK" thành công</returns>
    Delete_TBCQ_VinaPhone(mahd_neo) {
      try {
        let checkdata = new CheckDataFacade()

        if (!this.tt_nd.USER_NEO) {
          return 'Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.'
        }
        PSMA_TB = '84' + CommonFunction.ChuanHoa_XML(dsHDTB[0]['ma_tb'])
        PSMA_HD = mahd_neo

        var rs = new CCBSFacade().TS_LAPHD_CHUYENQUYEN_SUDUNG_XOA_THUEBAO(this.tt_nd.tentat_ccbs, PSMA_TB, PSMA_HD, this.tt_nd.USER_NEO)

        if (rs.IssSuccessed) {
          new KNotifications('Thành công', 'Xóa thuê bao {0} khỏi hợp đồng chuyển quyền trên VinaPhone thành công!'.FormatText(PSMA_TB), true).Show()
          return 'OK'
        } else return rs.Message
      } catch (err) {
        return 'Có lỗi khi xóa thuê bao khỏi hợp đồng trên CCBS.\n' + err.message.Message
      }
    },
    //endregion

    //region Insert_ThueBao_ChuyenQuyen_VinaPhone
    /// <summary>
    /// Hàm thực hiện đẩy dữ liệu hợp đồng thuê bao lên VinaPhone.
    /// </summary>
    /// <returns>"OK": thành công</returns>

    //endregion

    //region Update_HopDong_ChuyenQuyen_VinaPhone
    /// <summary>
    /// Hàm thực hiện cập nhât thông tin hợp đồng thuê bao lên VinaPhone.
    /// </summary>
    /// <returns>"OK": thành công</returns>
    //endregion

    //endregion
    /// <summary>
    /// TuấnNA thêm hàm xử lý nút địa chỉ lắp đặt -- Ngày 29/07/2010
    /// </summary>
    DiachiLD_Click() {
      this.LayTienTheoKhoanMuc()
      this.dataDiaChi.tinh_id = this.tinhld_id
      this.dataDiaChi.quan_id = this.quanld_id
      this.dataDiaChi.phuong_id = this.phuongld_id
      this.dataDiaChi.pho_id = this.phold_id
      this.dataDiaChi.ap_id = this.apld_id
      this.dataDiaChi.khu_id = this.khuld_id
      this.dataDiaChi.dacdiem_id = this.dacdiemld_id
      this.dataDiaChi.sonha = this.sonhald
      this.dataDiaChi.block = this.blockld
      this.dataDiaChi.tang = this.tangld
      this.dataDiaChi.sophong = this.sophongld
      this.dataDiaChi.motathem = this.motadcld
      this.dataDiaChi.diachi = this.$refs.txtDiachiLD.value
      this.dataDiaChi.type = 'btnDiaChiLD'
      this.$bvModal.show('diaChiModal')
      this.$refs.txtDiachiLD.focus()
    },

    /// <summary>
    /// TuấnNA thêm hàm kiểm tra Mã NVTC ứng với maTT cũ
    /// --- Ngày 07/10/2010
    /// </summary>
    /// <param name="matt"></param>
    async KiemTra_MaNVTC_MaTT(matt, manvtc_kt) {
      result = false
      rs = await this.axios.post('web-tracuu/tracu-danhba/LAY_DANHBA_THEO_MATB', {
        in_dichvuvt_id: 0,
        in_donvi_dl_id: 0,
        in_ma_tb: matt
      })
      let ds = rs.data.data
      if (ds.length > 0) {
        if (ds[0]['manv_tc']) {
          if (ds[0]['manv_tc'] == manvtc_kt) {
            result = true
          } else result = false
        } else result = true
      } else result = true
      return result
    },
    btnTraCuuNVTC_click() {
      try {
        this.objNVTC.QUAN_ID = this.quantt_id
        this.objNVTC.PHUONG_ID = this.phuongtt_id
        this.objNVTC.PHO_ID = this.phott_id
        this.objNVTC.AP_ID = this.aptt_id
        this.objNVTC.KHU_ID = this.khutt_id
        //this.objNVTC.DACDIEM_ID = this.dacdiemtt_id;
        this.objNVTC.TUYENTHU_ID = this.tuyenthu_id
        this.objNVTC.NHANVIENTC_ID = this.nhanvientc_id
        this.objNVTC.KT_KYLAI = true
        this.objNVTC.DONVIQL_ID = Number(this.$refs.cboDonviQL.value)
        console.log(this.objNVTC)
        this.loaiPopupTraCuuNVTC = 1
        this.$bvModal.show('dlgTimKiemNVTC')
      } catch (err) {
        console.log(err)
        console.log(err)
        this.$toast.error('Có lỗi: ' + err.message)
      }
    },

    txtChucDanhDD_Leave() {
      this.$refs.txtChucDanhDD.value = this.$refs.txtChucDanhDD.value
    },

    tsbtnInphuluc_Click() {},

    btnChonDCCuoi_Click() {
      btnChonDCCuoi_Click()
    },
    /// <summary>
    /// TuấnNA thêm hàm chọn địa chỉ cuối cho thuê bao TSL
    /// ---Ngày 30/08/2010
    /// </summary>
    btnChonDCCuoi_Click() {
      let obj = new frmDiaChi()
      obj.quan_id = this.quancuoi_id
      obj.phuong_id = this.phuongcuoi_id
      obj.pho_id = this.phocuoi_id
      obj.ap_id = this.ngocuoi_id
      obj.sonha = this.sonhacuoi
      obj.diachi = this.$refs.txtDiaChiCuoi.value
      obj.ShowDialog()
      if (obj.chapnhan) {
        this.quancuoi_id = obj.quan_id
        this.phuongcuoi_id = obj.phuong_id
        this.phocuoi_id = obj.pho_id
        this.ngocuoi_id = obj.ap_id
        this.sonhacuoi = obj.sonha
        this.$refs.txtDiaChiCuoi.value = obj.diachi
      }
      this.$refs.txtDiaChiCuoi.focus()
    },

    btnChonDCDau_Click() {
      btnChonDCDau_Click()
    },
    /// <summary>
    /// TuấnNA thêm hàm lấy địa chỉ đầu cho thuê bao TSL
    /// ---Ngày 30/08/2010
    /// </summary>
    btnChonDCDau_Click() {
      let obj = new frmDiaChi()
      obj.quan_id = this.quandau_id
      obj.phuong_id = this.phuongdau_id
      obj.pho_id = this.phodau_id
      obj.ap_id = this.ngodau_id
      obj.sonha = this.sonhadau
      obj.diachi = this.$refs.txtDiaChiDau.value
      obj.ShowDialog()
      if (obj.chapnhan) {
        this.quandau_id = obj.quan_id
        this.phuongdau_id = obj.phuong_id
        this.phodau_id = obj.pho_id
        this.ngodau_id = obj.ap_id
        this.sonhadau = obj.sonha
        this.$refs.txtDiaChiDau.value = obj.diachi
      }
      this.$refs.txtDiaChiDau.focus()
    },

    //region this.HienThiTTDiaChi_TSL
    HienThiTTDiaChi_TSL() {
      let ds = []
      ds = tracuuchung.LAY_THONGTIN_DIACHI(this.diachidau_id)
      if (ds.length > 0) {
        tinhdau_id = Number(ds[0]['tinh_id'])
        this.diachidau_id = Number(ds[0]['diachi_id'])
        this.quandau_id = Number(ds[0]['quan_id'])
        if (ds[0]['phuong_id']) this.phuongdau_id = Number(ds[0]['phuong_id'])
        if (ds[0]['pho_id']) this.phodau_id = Number(ds[0]['pho_id'])
        if (ds[0]['ap_id']) this.ngodau_id = Number(ds[0]['ap_id'])
        this.sonhadau = ds[0]['sonha']
      }

      ds = tracuuchung.LAY_THONGTIN_DIACHI(this.diachicuoi_id)
      if (ds.length > 0) {
        tinhcuoi_id = Number(ds[0]['tinh_id'])
        this.diachicuoi_id = Number(ds[0]['diachi_id'])
        this.quancuoi_id = Number(ds[0]['quan_id'])
        if (ds[0]['phuong_id']) this.phuongcuoi_id = Number(ds[0]['phuong_id'])
        if (ds[0]['pho_id']) this.phocuoi_id = Number(ds[0]['pho_id'])
        if (ds[0]['ap_id']) this.ngocuoi_id = Number(ds[0]['ap_id'])
        this.sonhacuoi = ds[0]['sonha']
      }
    },
    //endregion

    txtGhiChuKH_Leave() {
      this.$refs.txtGhiChuKH.value = this.$refs.txtGhiChuKH.value
    },

    txtGhiChuTT_Leave() {
      this.$refs.txtGhiChuTT.value = this.$refs.txtGhiChuTT.value
    },

    txtGhiChuTB_Leave() {
      this.$refs.txtGhiChuTB.value = this.$refs.txtGhiChuTB.value
    },
    async LayTienTheoKhoanMuc() {
      try {
        //Hiếu TC bổ sung hàm lấy tiền lên ô text box ngày 14/09/2010
        ///TuấnNA bổ sung hàm lấy tiền lên ô text box ---Ngày 15/09/2010
        if (this.dichvuvt_id != DichVuVienThong.ADSL) {
          this.kh_cd = 0
          this.ltb_adsl_id = 1
        }

        let ds = []
        ds = await laphd.LAY_TIEN_KHOANMUC_LD(
          LoaiHopDong.CHUYEN_QUYEN,
          this.$refs.dtpNgayYC.value,
          Number(this.$refs.cboDichVuVT.value),
          Number(this.$refs.cboLoaihinhTB.value),
          Number(this.$refs.cboKieuLD.value),
          this.phuongld_id,
          this.tocdo_id,
          0,
          this.ltb_adsl_id,
          this.ltb_adsl_moi_id,
          this.ltb_adsl_cu_id,
          this.tocdo_moi_id,
          this.tocdo_cu_id,
          this.kh_cd,
          this.phold_id,
          this.apld_id,
          this.khuld_id,
          Number(this.$refs.cboDoituongTB.value),
          this.trangbi_id
        )
        if (ds.length > 0) {
          this.HienThiTienLapDat(ds)
        }
      } catch (err) {
        this.$toast.error(err.message)
      }
      //
    },
    // HienThiTienLapDat(tien_ld, vat_ld, tien_cd, vat_cd) {
    //   //txtTienCQ.value = tien_ld;
    //   //txtVATCQ.value = vat_ld;
    //   //txtTienCD.value = tien_cd;
    //   //txtVATCD.value = vat_cd;
    // },
    HienThiTienLapDat(ds) {
      let kt_cd = true
      let kt_lm = true
      if (ds[0][0] != '-1') {
        for (let i = 0; i < ds.length; i++) {
          if (ds[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_CAIDAT) {
            //txtTienCD.value = parseFloat(ds[i]["tien"]);
            //txtVATCD.value = parseFloat(ds[i]["vat"]);
            this.dtien_dv = parseFloat(ds[i]['tien']) //;
            this.dvat_dv = parseFloat(ds[i]['vat']) //;
            kt_cd = false
          } else if (ds[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_CHUYENQUYEN) {
            //txtTienCQ.value = parseFloat(ds[0]["tien"]);
            //txtVATCQ.value = parseFloat(ds[0]["vat"]);
            this.dtien_cq = parseFloat(ds[0]['tien']) //;
            this.dvat_cq = parseFloat(ds[0]['vat']) //;
            kt_lm = false
          }
        }
        if (kt_cd == true) {
          this.dtien_dv = 0
          this.dvat_dv = 0
          //dtien_cq = 0;
          //dvat_cq = 0;
        }
        if (kt_lm == true) {
          //dtien_dv = 0;
          //dvat_dv = 0;
          this.dtien_cq = 0
          this.dvat_cq = 0
        }
      } else {
        this.dtien_dv = 0
        this.dvat_dv = 0
        this.dtien_cq = 0
        this.dvat_cq = 0
        //this.$toast.success("Thông tin giá chưa được cập nhật");
      }
      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_CHUYENQUYEN) {
          this.ds_tien_khoanmuc[j]['tien'] = this.dtien_cq
          this.ds_tien_khoanmuc[j]['vat'] = this.dvat_cq
        }
      }

      this.HienThiTienDichVu(this.dtien_dv, this.dvat_dv)
    },

    HienThiTienDichVu(tien_dv, vat_dv) {
      //if (tien_dv)
      //    txtTienDV.value = tien_dv;
      //else
      //    txtTienDV.value = "0";
      //if (vat_dv)
      //    txtVATDV.value = vat_dv;
      //else
      //    txtVATDV.value = "0";
      //nhapt
      this.dtien_dv = tien_dv
      this.dvat_dv = vat_dv

      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DICHVU) {
          this.ds_tien_khoanmuc[j]['tien'] = this.dtien_dv
          this.ds_tien_khoanmuc[j]['vat'] = this.dvat_dv
        }
      }
      this.LayTongTien_HDTB()
    },

    //,txtTienCQ_Leave()
    //{
    ////    if (txtTienCQ.value)
    ////    {
    ////        s = txtTienCQ.value;
    ////        s = Common.Utilities.(s);
    ////        if (!Common.Utilities.CommonFunction.CheckIsNumber(s))
    ////        {
    ////            this.$toast.error("Dữ liệu nhập phải là kiểu số");
    ////            txtTienCQ.focus();
    ////            return;
    ////        }
    ////        txtTienCQ.value = parseFloat(s);
    ////    }
    //}

    //,txtVATCQ_Leave()
    //{
    //    //if (txtVATCQ.value)
    //    //{
    //    //    s = txtVATCQ.value;
    //    //    s = Common.Utilities.(s);
    //    //    if (!Common.Utilities.CommonFunction.CheckIsNumber(s))
    //    //    {
    //    //        this.$toast.error("Dữ liệu nhập phải là kiểu số");
    //    //        txtVATCQ.focus();
    //    //        return;
    //    //    }
    //    //    txtVATCQ.value = parseFloat(s);
    //    //}
    //}
    ///TuấnNA zem code : Không hiển thị tiền cài đặt trong chuyển quyền --- Ngày 05/10/2010
    //,txtTienCD_Leave()
    //{
    //    if (txtTienCD.value)
    //    {
    //        s = txtTienCD.value;
    //        s = Common.Utilities.(s);
    //        if (!Common.Utilities.CommonFunction.CheckIsNumber(s))
    //        {
    //            this.$toast.error("Dữ liệu nhập phải là kiểu số");
    //            txtTienCD.focus();
    //            return;
    //        }
    //        txtTienCD.value = parseFloat(s);
    //    }
    //}

    //,txtVATCD_Leave()
    //{
    //    //if (txtVATCD.value)
    //    //{
    //    //    //s = txtVATCD.value;
    //    //    s = Common.Utilities.(s);
    //    //    if (!Common.Utilities.CommonFunction.CheckIsNumber(s))
    //    //    {
    //    //        this.$toast.error("Dữ liệu nhập phải là kiểu số");
    //    //        //txtVATCD.focus();
    //    //        return;
    //    //    }
    //    //    //txtVATCD.value = parseFloat(s);
    //    //}
    //}

    //,txtTienDV_Leave()
    //{
    //    if (txtTienDV.value)
    //    {
    //        s = txtTienDV.value;
    //        s = Common.Utilities.(s);
    //        if (!Common.Utilities.CommonFunction.CheckIsNumber(s))
    //        {
    //            this.$toast.error("Dữ liệu nhập phải là kiểu số");
    //            txtTienDV.focus();
    //            return;
    //        }
    //        txtTienDV.value = parseFloat(s);
    //    }
    //}

    //,txtVATDV_Leave()
    //{
    //    if (txtVATDV.value)
    //    {
    //        s = txtVATDV.value;
    //        s = Common.Utilities.(s);
    //        if (!Common.Utilities.CommonFunction.CheckIsNumber(s))
    //        {
    //            this.$toast.error("Dữ liệu nhập phải là kiểu số");
    //            txtVATDV.focus();
    //            return;
    //        }
    //        txtVATDV.value = parseFloat(s);
    //    }
    //}

    //,this.$refs.txtTenTT_Leave()
    //{
    //    this.$refs.txtTenTT.value = (this.$refs.txtTenTT.value);
    //    if (this.$refs.txtTenTB.value == "")
    //        this.$refs.txtTenTB.value = this.$refs.txtTenTT.value;
    //}

    //,this.$refs.txtMaTB_Leave()
    //{
    //    this.$refs.txtMaTB.value = this.$refs.txtMaTB.value;
    //}

    async cboLoaihinhTB_SelectedValueChanged() {
      //await bangts.HT_Tocdo_Adsl_Combobox_ByLoaiTB(cboGoiCuocAdsl, CURRENT_LOAITB_ID);
      //TuấnNA thêm hàm hiển thị thông tin mức cước thuê bao
      console.log('Thay đổi loại hình tb')
      if (this.load_tt_lh) {
        this.Lay_DS_MucCuoc_TB(this.phold_id, this.phuongld_id, this.CURRENT_LOAITB_ID(), Number(this.$refs.cboDoituongTB.value), this.tt_nd.ngay_cn)

        // rs = await this.axios.post(
        //   "web-hopdong/khoiphucthanhly/lay_ds_kieu_ld",
        //   {
        //     loaihd_id: LoaiHopDong.CHUYEN_QUYEN,
        //     loaitb_id: this.$refs.cboLoaihinhTB.value,
        //   }
        // );

        rs = await this.axios.get('web-hopdong/danhmuc/ds_kieu_ld/' + LoaiHopDong.CHUYEN_QUYEN + '/' + this.$refs.cboLoaihinhTB.value)

        this.$refs.cboKieuLD.HT_COMBOBOX(rs.data.data, 'ten_kieuld', 'kieuld_id')

        rs = await this.axios.post('/web-quantri/lay-dulieu/sp_tt_tocdo_adsl', {
          param: this.CURRENT_LOAITB_ID(),
          type: 2
        })

        var dt_adsl = rs.data.data
        this.$refs.cboGoiCuocAdsl.HT_COMBOBOX(dt_adsl, 'thuonghieu', 'tocdo_id')
      }
    },

    async cboTT_SelectedValueChanged() {
      try {
        if (this.$refs.cboTT.dataSource == null) return
        if (this.$refs.cboTT.value == 'System.Data.letView') return
        this.hdtt_id = Number(this.$refs.cboTT.value)
        this.HienThiTTHopDongTT(await tchopdong.LAY_DS_HOPDONG_TT_THEO_HDTT_ID(this.hdtt_id))
      } catch (err) {
        this.$toast.success('Có lỗi: ' + err.message)
      }
    },

    cboGiayToKH_SelectedValueChanged() {
      if (this.$refs.cboGiayToKH.length > 0) {
        if (this.$refs.cboGiayToKH.value == null) return
        if (this.$refs.cboGiayToKH.value == 'System.Data.letView') return
      }
    },
    /// <summary>
    /// TuấnNA thêm hàm kiểm tra trong hợp đồng có bao nhiêu thuê bao di động đã đồng bộ lên vinaphone
    /// --- Ngày 15/10/2010
    /// --- Nếu result >=2 ---> true
    /// --- Nếu result <2 ---> false
    /// </summary>
    /// <param name="hdkh_id"></param>
    /// <returns></returns>
    async KiemTra_TB_Vinaphone(hdkh_id) {
      result = false
      count = 0
      let dstb_vinaphone = []
      dstb_vinaphone = await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
      if (dstb_vinaphone.length > 0) {
        for (let i = 0; i < dstb_vinaphone.length; i++) {
          if (dstb_vinaphone[0][i]['dichvuvt_id'] == DichVuVienThong.DI_DONG && dstb_vinaphone[0][i]['loaitb_id'] == LoaiHinhTB.DIDONGTRASAU) {
            if (dstb_vinaphone[0][i]['status'] == TRANGTHAI_DONGBO.DONGBO_LHD) {
              count++
            }
          }
          if (count == 2) {
            result = true
            break
          }
        }
      }
      return result
    },

    cboDoituongTB_SelectedValueChanged() {
      if (this.$refs.cboDoituongTB.length > 0 && this.$refs.cboDichVuVT.length > 0) {
        if (this.$refs.cboDoituongTB.value == null) return
        if (this.$refs.cboDoituongTB.value == 'System.Data.letView') return
        if (this.$refs.cboDichVuVT.length == 0) return
        this.LayTienTheoKhoanMuc() //Lấy tiền theo đối tượng
      }
    },

    txtDiaChiKH_KeyPress() {
      this.btnDiaChiKH_Click()
    },

    txtDiaChiTT_KeyPress() {
      this.btnDiaChiTT_Click()
    },

    txtDiaChiCT_KeyPress() {
      if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        SendKeys.Send('{TAB}')
      }
      //else
      //{
      //    e.KeyChar = Convert.ToChar(0);
      //    btnDiaChiCT_Click();
      //}
    },

    txtDiaChiNDD_KeyPress() {
      this.btnDiaChiNDD_Click()
    },

    txtMaNVTC_KeyPress() {
      if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        SendKeys.Send('{TAB}')
      } else {
        e.KeyChar = Convert.ToChar(0)
        LayMaNVTC()
      }
    },

    txtDiaChiTB_KeyPress() {
      this.btnDiaChiTB_Click()
    },

    tsbtnInPhieuYC_Click() {},

    inHợpĐồngToolStripMenuItem_Click() {
      //inHợpĐồngToolStripMenuItem_Click(); code cũ
      in_pl = 0
      //InHD_LapMoi_CQ(LoaiHopDong.CHUYEN_QUYEN,1);
      Xuat_BC(116)
    },

    //region InHD_Lắp mới_Chuyển quyền
    //endregion

    //region InPL_Chuyển quyền
    async InPL_ChuyenQuyen() {
      try {
        report = new SSRTool()
        let ds = []
        let dsTempKH = []
        dsTempKH = await tchopdong.LAY_DS_HOPDONG_THEO_MA_GD(this.$refs.txtMaGD.value, LoaiHopDong.CHUYEN_QUYEN, 0, 0, 0, 0)
        if (dsTempKH.length > 0) {
          ds = await tchopdong.IN_HOPDONG_THEO_MA_GD(this.$refs.txtMaGD.value, LoaiHopDong.CHUYEN_QUYEN)
          ds[0].TableName = 'PL_CQSD'
          let ds_bd = []
          var dsetHDTB = await tchopdong.IN_HOPDONG_THEO_MA_GD(this.$refs.txtMaGD.value, LoaiHopDong.CHUYEN_QUYEN)
          if (dsTempKH.length > 0) {
            ds_bd = await tchopdong.LAY_BIENDONG_THEO_HDTB_ID(Number(dsTempKH[0]['hdtb_id']))
            if (ds_bd.length <= 0) {
              this.$toast.error('Không tìm thấy thông tin khách hàng cũ!')
              return
            }
          } else {
            this.$toast.error('Không tìm thấy thông tin thuê bao trong hợp đồng!')
            return
          }
          //region Lấy dữ liệu đưa vào mẫu phụ lục chuyển quyền
          ////thong tin khách hàng chuyển quyền
          report.Parameters.Add('mahd_cq', ds_bd[0]['ma_hd'])
          report.Parameters.Add('makh_cq', ds_bd[0]['ma_kh'])
          report.Parameters.Add('ngaylhd_cq', ds_bd[0]['ngaylap_hd'])
          report.Parameters.Add('tenkh_cq', ds_bd[0]['ten_kh'].toUpperCase())
          //
          //Thông tin khách hàng nhận quyền
          report.Parameters.Add('mahd_nq', dsTempKH[0]['ma_hd'])
          report.Parameters.Add('makh_nq', dsTempKH[0]['ma_kh'])
          report.Parameters.Add('ngaylhd_nq', dsTempKH[0]['ngaylap_hd'].ToString('dd/MM/yyyy'))

          report.Parameters.Add('tenkh_nq', dsTempKH[0]['ten_kh'].toUpperCase())
          report.Parameters.Add('nguoidd_nq', dsTempKH[0]['nguoi_dd'].toUpperCase())
          report.Parameters.Add('chucdanh_nq', dsTempKH[0]['chucdanh_dd'])
          report.Parameters.Add('so_gt', dsTempKH[0]['so_gt'])
          report.Parameters.Add('so_dt', dsTempKH[0]['so_dt'])
          report.Parameters.Add('fax', dsTempKH[0]['so_fax'])
          report.Parameters.Add('email', dsTempKH[0]['email'])
          let ds_diachi = []
          ds_diachi = await tchopdong.LAY_DS_DIACHI_THEO_HDKH_ID(this.hdkh_id)
          if (ds_diachi.length > 0) {
            report.Parameters.Add('sonha', ds_diachi[0]['sonha'])
            report.Parameters.Add('tenphuong', ds_diachi[0]['ten_phuong'])
            report.Parameters.Add('tentinh', ds_diachi[0]['tentinh'])
          } else {
            report.Parameters.Add('sonha', '')
            report.Parameters.Add('tenphuong', '')
            report.Parameters.Add('tentinh', '')
          }
          //Danh sách thuê bao
          KhoiTao_Daset_HDTB(ds, 'HDTB_CHUYENQUYEN')
          KhoiTao_Daset_HDTB(ds, 'HDTB_CHUYENQUYEN2')
          for (let i = 0; i < dsetHDTB.length; i++) {
            if (i < 2) {
              ds['HDTB_CHUYENQUYEN'].Add(dsetHDTB[0][i]['loaihinhdv'], dsetHDTB[0][i]['diachi_ld'], dsetHDTB[0][i]['ma_tb'], dsetHDTB[0][i]['todo_tentn'], dsetHDTB[0][i]['muccuoc'], '', dsetHDTB[0][i]['ma_dvgt'], dsetHDTB[0][i]['kieu_ld'])
            } else ds['HDTB_CHUYENQUYEN2'].Add(dsetHDTB[0][i]['loaihinhdv'], dsetHDTB[0][i]['diachi_ld'], dsetHDTB[0][i]['ma_tb'], dsetHDTB[0][i]['todo_tentn'], dsetHDTB[0][i]['muccuoc'], '', dsetHDTB[0][i]['ma_dvgt'], dsetHDTB[0][i]['kieu_ld'])
          }

          //endregion
          report.dataSource = ds
          ngay = this.tt_nd.ngay_cn.Day
          if (ngay.length <= 1) {
            ngay = '0' + ngay
          }
          thang = this.tt_nd.ngay_cn.Month
          if (thang.length <= 1) {
            thang = '0' + thang
          }
          ngayin = 'ngày ' + ngay + ' tháng ' + thang + ' năm ' + this.tt_nd.ngay_cn.Year
          diadanh = clsBaoCao.diadanh
          report.Parameters.Add('diadanh', diadanh)
          report.Parameters.Add('ngayin', ngayin)
          if (dsetHDTB.length <= 2) report.Parameters.Add('remove_page', '0')
          //Nếu <= 4 row thì remove  page 2
          else report.Parameters.Add('remove_page', '2') //không remove
          path = clsBaoCao.reportpath
          report.ReportFileName = System.IO.Path.Combine(clsBaoCao.reportpath + '\\ReportFile\\InHopDong', 'PL_ChuyenQuyen.rst')
          report.ProductName = '' // clsBaoCao.reportFooter;
          report.ShowReport(frmFormMain.ActiveForm, true)
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi: ' + err.messagep)
      }
    },
    //endregion

    //region KhoiTao_Daset
    KhoiTao_Daset_HDTB(ds, ten_ds) {
      if (in_pl == 0) {
        // in HĐ
        ds.Add(ten_ds)
        ds[ten_ds].Columns.Add('stt')
        ds[ten_ds].Columns.Add('diachi_ld')
        ds[ten_ds].Columns.Add('ma_tb')
        ds[ten_ds].Columns.Add('simcard')
        ds[ten_ds].Columns.Add('loaihinhdv')
        ds[ten_ds].Columns.Add('ma_dvgt')
        ds[ten_ds].Columns.Add('todo_tentn')
        ds[ten_ds].Columns.Add('muccuoc')
        ds[ten_ds].Columns.Add('tenkieu_ld')
      } // inPL
      else {
        ds.Add(ten_ds)
        ds[ten_ds].Columns.Add('loaihinh_dv')
        ds[ten_ds].Columns.Add('diachi_ld')
        ds[ten_ds].Columns.Add('ma_tn')
        ds[ten_ds].Columns.Add('tocdo')
        ds[ten_ds].Columns.Add('muccuoc')
        ds[ten_ds].Columns.Add('soserial')
        ds[ten_ds].Columns.Add('dichvu_gt')
        ds[ten_ds].Columns.Add('kieu_ld')
      }
    },
    //endregion

    async inPhụcLucToolStripMenuItem_Click() {
      if (this.hdkh_id != 0) {
        try {
          report = new SSRTool()
          let ds = []
          //Tuan sua code -- Ngay 22/07/2010
          ds = await tchopdong.IN_HOPDONG_THEO_MA_GD(this.$refs.txtMaGD.value, LoaiHopDong.CHUYEN_QUYEN)

          report.dataSource = ds
          ds[0].TableName = 'PL_CQSD'
          //region Lấy dữ liệu trên Form đẩy lên báo cáo In hợp đồng
          report.Parameters.Add('ten_cn_tc', this.$refs.txtTenKH.value)
          report.Parameters.Add('nguoi_dd', this.$refs.txtNguoiDD.value)
          ///Tu?n NA thêm code hi?n th? s? gi?y t? + ngày c?p + noi c?p --- Ngày 1/11/2010
          so_gt = ''
          so_gt = this.$refs.txtSoGT.value + ' - ' + this.$refs.txtNgayCap.value + ' - ' + this.$refs.txtNoiCap.value
          report.Parameters.Add('so_gt', so_gt)
          report.Parameters.Add('chucvu', this.$refs.txtChucDanhDD.value)
          report.Parameters.Add('diachi', this.$refs.txtDiaChiNDD.value)
          report.Parameters.Add('so_tk', this.$refs.txtSoTKKH.value)
          report.Parameters.Add('mst', this.$refs.txtMaSTKH.value)
          report.Parameters.Add('so_dt', this.$refs.txtTelKH.value)
          report.Parameters.Add('fax', this.$refs.txtFaxKH.value)

          report.Parameters.Add('httt', this.$refs.cboHTTT.value)
          report.Parameters.Add('kh_cq', this.$refs.txtTenKH.value.toUpperCase())
          report.Parameters.Add('dd_cq', this.$refs.txtNguoiDD.value)
          report.Parameters.Add('chucvu_cq', this.$refs.txtChucDanhDD.value)
          report.Parameters.Add('ma_hd', this.$refs.txtMaHD.value)
          ///Tu?n NA thêm code chia ngày - tháng- nam ký h?p d?ng KHNQ --- Ngày 31/10/2010
          date = ''
          ngayky = ''
          thangky = ''
          namky = ''
          date = this.$refs.dtpNgayLapHDNQ.value
          if (date) {
            ngayky = date.Substring(0, 2)
            thangky = date.Substring(3, 2)
            namky = date.Substring(6, date.length - 6)
          }
          report.Parameters.Add('ngayky', ngayky)
          report.Parameters.Add('thangky', thangky)
          report.Parameters.Add('namky', namky)
          ///Tu?n NA thêm code chia ngày - tháng- nam ký h?p d?ng KHCQ --- Ngày 31/10/2010
          date_cq = ''
          ngayky_cq = ''
          thangky_cq = ''
          namky_cq = ''
          date_cq = this.$refs.dtpNgayLapHDCQ.value
          if (date_cq) {
            ngayky_cq = date_cq.Substring(0, 2)
            thangky_cq = date_cq.Substring(3, 2)
            namky_cq = date.Substring(6, date_cq.length - 6)
          }
          report.Parameters.Add('ngayky_cq', ngayky_cq)
          report.Parameters.Add('thangky_cq', thangky_cq)
          report.Parameters.Add('namky_cq', namky_cq)
          report.Parameters.Add('makh_cq', this.$refs.txtMaKHCu.value)
          report.Parameters.Add('mahd_cq', this.$refs.txtMaHDCu.value)
          ///Tu?n NA thêm code l?y d?a ch? khách hàng nh?n quy?n --- Ngày 31/10/2010
          let ds_diachi = []
          ds_diachi = await tchopdong.LAY_DS_DIACHI_THEO_HDKH_ID(this.hdkh_id)
          sonha_nq = ''
          sonha_nq = ds_diachi[0]['sonha']
          pho_nq = ''
          pho_nq = ds_diachi[0]['ten_pho']
          phuong_nq = ''
          phuong_nq = ds_diachi[0]['ten_phuong']
          quan_nq = ''
          quan_nq = ds_diachi[0]['ten_quan']
          report.Parameters.Add('sonha_nq', sonha_nq)
          report.Parameters.Add('pho_nq', pho_nq)
          report.Parameters.Add('phuong_nq', phuong_nq)
          report.Parameters.Add('quan_nq', quan_nq)
          report.Parameters.Add('tinh_tp', clsBaoCao.diadanh)
          if (this.$refs.chkNganHangKH.checked == true) {
            report.Parameters.Add('nganhang_kh', this.$refs.cboNganHangKH.value)
          } else report.Parameters.Add('nganhang_kh', '')

          //endregion

          path = clsBaoCao.reportpath
          report.ReportFileName = path + '\\ReportFile\\PL_Chuyenquyen.rst'
          //report.ProductName = "H? th?ng CSS L?ng Son";
          report.ShowReport(frmFormMain.ActiveForm, true)
        } catch (err) {
          MessageBox.Show(err.message)
        }
      }
    },

    inPhiếuYêuCầuToolStripMenuItem_Click() {
      inPhiếuYêuCầuToolStripMenuItem_Click()
    },
    async inPhiếuYêuCầuToolStripMenuItem_Click() {
      if (this.hdkh_id != 0) {
        try {
          report = new SSRTool()
          let ds = []
          //Tuan sua code -- Ngay 22/07/2010
          //ds = await tchopdong.IN_HOPDONG_THEO_MA_GD(this.$refs.txtMaGD.value, LoaiHopDong.CHUYEN_QUYEN);
          ds = await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)

          report.dataSource = ds
          ds[0].TableName = 'PhieuYC_CQSD'
          //region Lấy dữ liệu trên Form đẩy lên báo cáo In hợp đồng
          ///Tuấn NA thêm code chia ngày - tháng- năm --- Ngày 31/10/2010
          date = ''
          ngay = ''
          thang = ''
          nam = ''
          date = this.tt_nd.ngay_cn
          if (date) {
            ngay = date.Substring(0, 2)
            thang = date.Substring(3, 2)
            nam = date.Substring(6, date.length - 6)
          }
          report.Parameters.Add('ngay', ngay)
          report.Parameters.Add('thang', thang)
          report.Parameters.Add('nam', nam)
          report.Parameters.Add('diachi_cq', this.$refs.txtDiaChiKHCu.value)
          report.Parameters.Add('ma_khcq', this.$refs.txtMaKHCu.value)
          report.Parameters.Add('ten_khcq', this.$refs.txtTenKHCu.value.toUpperCase())
          report.Parameters.Add('ten_khnq', this.$refs.txtTenKH.value.toUpperCase())
          report.Parameters.Add('diachi_nq', this.$refs.txtDiaChiKH.value)
          report.Parameters.Add('ngay_sn', this.$refs.txtNgaySN.value)
          report.Parameters.Add('mst', this.$refs.txtMaSTKH.value)
          report.Parameters.Add('stk', this.$refs.txtSoTKKH.value)
          report.Parameters.Add('dtlh', this.$refs.txtTelKH.value)
          report.Parameters.Add('so_gt', this.$refs.txtSoGT.value)
          report.Parameters.Add('diadanh', clsBaoCao.diadanh)

          //endregion

          s = ''
          //if (await tchopdong.KIEMTRA_TB_SUDUNG_G3DV(this.$refs.txtMaTB.value) > 0)
          //{
          //    //Thue bao nay dang su dung g3dv
          //    temp = await bangts.Lay_ds_tb_sudung_g3dv(this.$refs.txtMaTB.value);
          //    if (temp.length > 0)
          //    {
          //        s += " Thuê bao " + this.$refs.txtMaTB.value + " đang dùng gói 3dv (";
          //        for (let i = 0; i < 6; i++)
          //            if ((temp[0][i]))
          //                if (this.$refs.txtMaTB.value != temp[0][i])
          //                    s += temp[0][i] + ", ";
          //        s = s.Substring(0, s.length - 2);
          //        s += ") sẽ bị hủy gói 3dv khi thuê bao " + this.$refs.txtMaTB.value + " chuyển quyền sử dụng";
          //    }
          //}
          report.Parameters.Add('ghichu', s)
          path = clsBaoCao.reportpath
          report.ReportFileName = path + '\\ReportFile\\PhieuYC_CQSD.rst'
          report.ProductName = '' //"Hệ thống CSS Hà Giang";
          report.ShowReport(frmFormMain.ActiveForm, true)
        } catch (err) {
          MessageBox.Show(err.message)
        }
      }
    },

    cboTrangBiADSL_SelectedValueChanged() {
      if (this.$refs.cboTrangBiADSL.length > 0) {
        if (this.$refs.cboTrangBiADSL.value == null) return
        if (this.$refs.cboTrangBiADSL.value == 'System.Data.letView') return
        if (this.CURRENT_DVVT_ID == DichVuVienThong.ADSL) {
          this.trangbi_id = Number(this.$refs.cboTrangBiADSL.value)
          this.LayTienTheoKhoanMuc()
        }
      }
    },

    cboTrangBi_SelectedValueChanged() {
      if (this.$refs.cboTrangBi.length > 0) {
        if (this.$refs.cboTrangBi.value == null) return
        if (this.$refs.cboTrangBi.value == 'System.Data.letView') return
        if (this.CURRENT_DVVT_ID == DichVuVienThong.TSL || this.CURRENT_DVVT_ID == DichVuVienThong.NOIBO_2DIEM) {
          this.trangbi_id = Number(this.$refs.cboTrangBi.value)
          this.LayTienTheoKhoanMuc()
        }
      }
    },

    cboTrangBiDD_SelectedValueChanged() {
      if (this.$refs.cboTrangBiDD.length > 0) {
        if (this.$refs.cboTrangBiDD.value == null) return
        if (this.$refs.cboTrangBiDD.value == 'System.Data.letView') return
        if (this.CURRENT_DVVT_ID == DichVuVienThong.DI_DONG) {
          this.trangbi_id = Number(this.$refs.cboTrangBiDD.value)
          this.LayTienTheoKhoanMuc()
        }
      }
    },

    cboTrangBiGP_SelectedValueChanged() {
      if (this.$refs.cboTrangBiGP.length > 0) {
        if (this.$refs.cboTrangBiGP.value == null) return
        if (this.$refs.cboTrangBiGP.value == 'System.Data.letView') return
        if (this.CURRENT_DVVT_ID == DichVuVienThong.GPHONE) {
          this.trangbi_id = Number(this.$refs.cboTrangBiGP.value)
          this.LayTienTheoKhoanMuc()
        }
      }
    },

    cboTrangBiWan_SelectedValueChanged() {
      if (this.$refs.cboTrangBiWan.length > 0) {
        if (this.$refs.cboTrangBiWan.value == null) return
        if (this.$refs.cboTrangBiWan.value == 'System.Data.letView') return
        if (this.CURRENT_DVVT_ID == DichVuVienThong.MEGAWAN || this.CURRENT_DVVT_ID == DichVuVienThong.METRONET || this.CURRENT_DVVT_ID == DichVuVienThong.NOIBO_DADIEM) {
          this.trangbi_id = Number(this.$refs.cboTrangBiWan.value)
          this.LayTienTheoKhoanMuc()
        }
      }
    },

    chkNganHangKH_KeyPress() {
      if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        this.$refs.txtGhiChuKH.focus()
      }
    },

    txtGhiChuKH_KeyPress() {
      if (e.KeyChar == Convert.ToChar(Keys.Enter)) {
        this.$refs.txtMaTBDD.focus()
      }
    },

    async btnNSNN_Click() {
      this.objNganhang = {}
      rs = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nganhang', {
        param: this.$refs.cboNganHangTT.value,
        type: 1
      })
      let loai_nh = rs.data.data
      if (loai_nh == '2') {
        this.objNganhang.DV_QHNS = this.DV_QHNS
        this.objNganhang.MACHUONG = this.MACHUONG
        this.objNganhang.MN_KT = this.MN_KT
        this.objNganhang.MA_NDKT = this.MA_NDKT
        this.objNganhang.MN_NSNN = this.MN_NSNN
        this.objNganhang.TIEN_DM = this.TIEN_DM
        this.$refs.popupChiTietMLNS.showDialog()
      }
    },

    btnNganhNghe_Click() {
      try {
        this.$nextTick(() => {
          this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupTieuNganh')
          this.popupComponentName = 'popupTieuNganh'
          let obj = {}
          obj.TNC1_ID = this.TNC1_ID
          obj.TNC2_ID = this.TNC2_ID
          obj.TNC3_ID = this.TNC3_ID
          obj.TNC4_ID = this.TNC4_ID
          obj.dsTNC1 = []
          obj.dsTNC2 = []
          obj.dsTNC3 = []
          obj.dsTNC4 = []
          console.log(obj)
          this.popupComponentData = {
            vnganhnghe_id: this.$refs.cboNganhNgheLoc.value,
            tieunganh: obj
          }
          this.Popup('popupComponents')
        })
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi: ' + err.message)
      }
    },

    /*,cboNganhNghe_SelectionChangeCommitted()
            {
                if (this.nganhnghe_id != Number(cboNganhNghe.value))
                {
                    this.TNC1_ID = 0;
                    this.TNC2_ID = 0;
                    this.TNC3_ID = 0;
                }
                btnNganhNghe_Click(sender, e);
            }*/

    txtDiaChiCT_Leave() {
      this.$refs.txtDiaChiCT.value = this.$refs.txtDiaChiCT.value
    },

    txtTenKH_TabIndexChanged() {
      btnDiaChiKH_Click()
    },

    txtTenKH_TabStopChanged() {},

    btnDCHoKhau_Click() {
      this.LayDiaChiHK()
    },

    LayDiaChiHK() {
      try {
        if (this.$refs.txtHoKhau.value) {
          this.dataDiaChi.diachi = this.$refs.txtHoKhau.value
          this.dataDiaChi.type = 'btnDCHoKhau'
          this.$bvModal.show('diaChiModal')
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi: ' + err.message.Message)
      }
    },

    cboNganHangTT_SelectionChangeCommitted() {
      try {
        let obj = new CheckDataFacade()
        loai_nh = obj.MAP_ID('loai_nh', DatabaseConstants.DB2 + '.nganhang', 'where nganhang_id =' + cboNganHangTT.value)
        btnNSNN.Enabled = loai_nh == '2'
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi: ' + err.message.Message)
      }
    },

    txtMaTT_Leave() {
      try {
        if (this.$refs.txtMaTT.value == '') {
          this.$refs.lvDvkhac_TT.dataSource = []
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi: ' + err.message.Message)
      }
    },

    CapNhat_DiaChiBC() {
      try {
        if (this.$refs.txtDiaChiBC.value) laphd.CAPNHAT_DIACHI_BC_HDTT(this.diachibc_id, this.hdtt_id, this.phobc_id, this.phuongbc_id, this.quanbc_id, this.sonhabc, this.$refs.txtDiaChiBC.value, this.tinhbc_id, this.apbc_id, this.khubc_id, this.dacdiembc_id)
        else laphd.CAPNHAT_DIACHI_BC_HDTT(this.diachibc_id, this.hdtt_id, this.phobc_id, this.phuongbc_id, this.quanbc_id, this.sonhabc, '0', this.tinhbc_id, this.apbc_id, this.khubc_id, this.dacdiembc_id)
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi: ' + err.message)
      }
    },

    txtDiaChiBC_KeyDown() {
      try {
        if (!e.Control) {
          //Không cho phép ấn tổ hợp phím
          if (e.KeyCode == Keys.Delete) {
            if (MessageBox.Show('Bạn thật sự muốn xóa địa chỉ báo cước không ?', 'Thông báo', MessageBoxButtons.YesNo, MessageBoxIcon.Information) != DialogResult.Yes) return
            //diachi bc
            this.$refs.txtDiaChiBC.value = ''
            this.tinhbc_id = 0
            this.quanbc_id = 0
            this.phuongbc_id = 0
            this.phobc_id = 0
            this.apbc_id = 0
            this.khubc_id = 0
            this.dacdiembc_id = 0
            this.sonhabc = ''
            //
            this.$refs.txtMaNVTC.value = '' //Nếu chọn lại địa chỉ  thanh toán thì clear mã nhân viên thu cước - Hiếu 29.09.2010
            this.$refs.txtMaTuyen.value = ''
            ///TuanNA: Lấy mã NVTC theo địa chỉ tt
            ///Ngày 07.12.2012
            HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, Number(this.$refs.cboDonviQL.value), this.dacdiemtt_id)
          }
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi: ' + err.message)
      }
    },

    btnTaoMIG_Click() {
      if (!this.hdkh_id) {
        this.$toast.error('Chưa có thông tin về hợp đồng khách hàng!')
        return
      }

      var obj = {}
      obj.hdkh_id = this.hdkh_id
      obj.khachhang_id = this.khachhang_id
      obj.hdtt_id = this.hdtt_id
      obj.thanhtoan_id = this.thanhtoan_id
      obj.fmig_id = this.mig_id
      obj.fhdmig_id = this.hdmig_id
      obj.ma_tt = this.$refs.txtMaTT.value
      obj.ten_tt = this.$refs.txtTenTT.value

      this.popupComponent = () => import('../InstallNewSubs/Popups/popupChonMaInGhep')
      this.popupComponentName = 'popupChonMaInGhep'
      this.popupComponentData = obj
      this.Popup('popupComponents')
    },

    async Xuat_BC(vbaocao_id) {
      let outDocument = new PerpetuumSoft.Reporting.DOM.Document()
      let dsTemplate = []
      let dsBaoCao = []
      let dsBaoCaoCon = []
      try {
        //Luu file bao cao hien tai vao c:\ReportTemp.rst
        if (File.Exists('C:ReportTemp.rst')) File.Delete('C:ReportTemp.rst')
        //Lay report Template luu trong CSDL
        {
          baocao_id = vbaocao_id
          dsTemplate = await bangts.GetSQL('select * from ' + DatabaseConstants.DB1 + '.baocao where baocao_id = ' + baocao_id + '', '')
          if (dsTemplate.length > 0) {
            serializedDocument = dsTemplate[0]['noidung']
            //Load file bao cao tu file XML da doc ra
            if (serializedDocument) {
              let stringReader = new StringReader(serializedDocument)
              outDocument = PerpetuumSoft.Framework.Serialization.XSerializationManager.Read(stringReader)
            } else {
              this.$toast.success('Báo cáo này chưa được thiết kế mẫu !')
              return
            }
            PerpetuumSoft.Framework.Serialization.XSerializationManager.Write(outDocument, 'C:ReportTemp.rst')
            //Lay du lieu bao cao
            if (dsTemplate[0]['kieubc_id'] == '1') {
              //Lấy dữ liệu từ SQL
              sql = dsTemplate[0]['caulenh']
              sql = ThamSo_DataSet(sql)
              sql = ReplaceSQL(sql)
              dsBaoCao = await bangts.GetSQL(sql, 'baocao')
              if (dsBaoCao.length == 0) {
                this.$toast.success('Không có dữ liệu hiển thị báo cáo !')
                return
              }
              let ds = baocaocon.Select(baocao_id)
              if (ds.length > 0) {
                for (let i = 0; i < ds.length; i++) {
                  if (ds[i]['kieubc_id'] == '1') {
                    tablename = ds[i]['ma_bc_con']
                    if (!tablename) tablename = i + 1
                    sql = ds[i]['caulenh']
                    sql = ThamSo_DataSet(sql)
                    sql = ReplaceSQL(sql)
                    dsBaoCaoCon = await bangts.GetSQL(sql, '')
                    dsBaoCao.Add(dsBaoCaoCon[0].Copy())
                    dsBaoCao[i + 1].TableName = tablename
                  } else {
                    sql = ds[i]['caulenh']
                    sql = ReplaceSQL(sql)
                    dsBaoCaoCon = LAY_DULIEU_BAOCAO_DATASET(sql, Number(ds[i]['bc_con_id']))
                    if (dsBaoCaoCon == null) return
                    dsBaoCaoCon[0].TableName = ds[i]['ma_bc_con']
                    dsBaoCao.Add(dsBaoCaoCon[0].Copy())
                  }
                }
              }
            } //Lấy dữ liệu từ DataSet
            else {
              dsBaoCao = LAY_DULIEU_BAOCAO_DATASET(dsTemplate[0]['caulenh'], baocao_id)
              if (dsBaoCao == null) return
              if (dsBaoCao.length == 0) {
                this.$toast.success('Không có dữ liệu !')
                return
              }
              let ds = baocaocon.Select(baocao_id)
              if (ds.length > 0) {
                for (let i = 0; i < ds.length; i++) {
                  if (ds[i]['kieubc_id'] == '1') {
                    //Lấy dữ liệu từ SQL
                    tablename = ds[i]['ma_bc_con']
                    if (!tablename) tablename = i + 1
                    sql = ds[i]['caulenh']
                    sql = ThamSo_DataSet(sql)
                    sql = ReplaceSQL(sql)
                    dsBaoCaoCon = await bangts.GetSQL(sql, '')
                    dsBaoCao.Add(dsBaoCaoCon[0].Copy())
                    dsBaoCao[i + 1].TableName = tablename
                  } //Lấy dữ liệu từ Procedure
                  else {
                    dsBaoCaoCon = LAY_DULIEU_BAOCAO_DATASET(ds[i]['caulenh'], Number(ds[i]['bc_con_id']))
                    if (dsBaoCaoCon == null) return
                    dsBaoCaoCon[0].TableName = ds[i]['ma_bc_con']
                    dsBaoCao.Add(dsBaoCaoCon[0].Copy())
                  }
                }
              }
            }
            //Chay bao cao
            report = new SSRTool()
            report.Parameters.Add('tenbaocao', dsTemplate[0]['ten_bc'].toUpperCase())
            report.Parameters.Add('diadanh', clsBaoCao.diadanh)
            report.Parameters.Add('ngayin', clsBaoCao.ngayin)
            report.Parameters.Add('conghoa', clsBaoCao.conghoa)
            report.Parameters.Add('doclap', clsBaoCao.doclap)
            report.Parameters.Add('line', clsBaoCao.line)
            report.Parameters.Add('ngaybaocao', clsBaoCao.ngayin)
            report.dataSource = dsBaoCao
            fullpath = 'c:ReportTemp.rst'
            report.ReportFileName = fullpath
            report.ReportTitle = 'Test bao cao'
            report.IsDisplayProductName = true
            report.ProductName = clsBaoCao.reportFooter + ' ' + DateTime.Now
            report.ShowReport(this, true)
            if (File.Exists('C:ReportTemp.rst')) File.Delete('C:ReportTemp.rst')
          }
        }
      } catch (err) {
        MessageBox.Show('Có lỗi trong quá trình xem báo cáo: ' + err.message.Message, 'Thong bao')
      }
    },

    ReplaceSQL(sql) {
      sql = sql.Replace('{?DB4}', DatabaseConstants.DB4)
      sql = sql.Replace('{?DB3}', DatabaseConstants.DB3)
      sql = sql.Replace('{?DB2}', DatabaseConstants.DB2)
      sql = sql.Replace('{?DB1}', DatabaseConstants.DB1)
      sql = sql.Replace('\n', ' ')
      sql = sql.Replace('\r', ' ')
      sql = sql.Replace('\t', ' ')

      return sql
    },

    ThamSo_DataSet(sql) {
      sql = sql.Replace(':this.hdkh_id', this.hdkh_id)
      sql = sql.Replace(':this.ma_gd', this.$refs.txtMaGD.value)
      sql = sql.Replace(':donvi_dl_id', this.tt_nd.donvi_dl_id)
      return sql
    },

    async LAY_DULIEU_BAOCAO_DATASET(caulenh, baocao_id) {
      // loai_bc = 1 : cha
      // loai_bc = 2 : con
      let dsBC = []
      let dsthamso_bc = []
      str_command = ''
      str_command += ' SELECT a.thamso_bc_id, a.ten_thamso, a.ten_hienthi, a.loaidk_id, '
      str_command += ' a.kieu_dl, a.dinhdang, a.vitri, a.lenh, a.ht_nhan, a.dd_nhan, a.gt_text FROM '
      str_command += DatabaseConstants.DB1 + '.thamso_bc a where a.baocao_id = ' + baocao_id
      str_command += ' order by a.vitri'
      dsthamso_bc = await bangts.GetSQL(str_command, 'thamso_bc')
      if (dsthamso_bc.length > 0) {
        let parms = new OracleParameter[dsthamso_bc.length + 1]()
        for (let i = 0; i < dsthamso_bc.length; i++) parms[i] = new OracleParameter(dsthamso_bc[0][i]['ten_thamso'], dsthamso_bc[0][i]['kieu_dl'] == '1' ? OracleDbType.Int32 : OracleDbType.Varchar2)

        parms[dsthamso_bc.length] = new OracleParameter('returnds', OracleDbType.RefCursor, ParameterDirection.Output)

        for (let i = 0; i < dsthamso_bc.length; i++) {
          if (dsthamso_bc[0][i]['ten_thamso'] == ':this.hdkh_id') parms[i].value = this.hdkh_id
          if (dsthamso_bc[0][i]['ten_thamso'] == 'in_hdkh_id') parms[i].value = this.hdkh_id
          if (dsthamso_bc[0][i]['ten_thamso'] == 'vhdkh_id') parms[i].value = this.hdkh_id

          if (dsthamso_bc[0][i]['ten_thamso'] == ':this.ma_gd') parms[i].value = this.$refs.txtMaGD.value
          if (dsthamso_bc[0][i]['ten_thamso'] == 'in_ma_gd') parms[i].value = this.$refs.txtMaGD.value
          if (dsthamso_bc[0][i]['ten_thamso'] == 'vma_gd') parms[i].value = this.$refs.txtMaGD.value

          if (dsthamso_bc[0][i]['ten_thamso'] == ':donvi_tt_id') parms[i].value = this.tt_nd.donvi_dl_id
          if (dsthamso_bc[0][i]['ten_thamso'] == ':donvi_dl_id') parms[i].value = this.tt_nd.donvi_dl_id

          if (dsthamso_bc[0][i]['ten_thamso'] == ':in_loaihd_id') parms[i].value = LoaiHopDong.DAT_MOI
          if (dsthamso_bc[0][i]['ten_thamso'] == ':vloaihd_id') parms[i].value = LoaiHopDong.DAT_MOI
        }
        dsBC = await bangts.DecompressDataSet(baocao.getDataFromProcedure(caulenh, parms))
      }
      return dsBC
    },

    tsddbIn_Click() {
      try {
        if (this.hdkh_id != 0) {
          this.popupInHoSo_Param.hdkh_id = this.hdkh_id
          this.popupInHoSo_Param.ma_gd = this.$refs.txtMaGD.value
          this.popupInHoSo_Param.hdtb_id = this.hdtb_id
          this.popupInHoSo_Param.ngay_lhd = this.$refs.dtpNgayLapHDCQ.value
          this.$refs.popupInHoSo.showModal()
        } else {
          this.$toast.error('Chưa có thông tin hợp đồng!')
        }
      } catch (err) {
        this.$toast.success('Có lỗi: ' + err.message)
      }
    },

    cboMaDD_SelectedIndexChanged() {},

    tsbtnThongTinLH_Click() {
      if (this.hdkh_id != 0) {
        this.$bvModal.show('ThongTinLienHe')
      }
    },

    tsbtnEmail_Click() {
      if (this.hdkh_id != 0) {
        this.$bvModal.show('ThongTinEmail')
      }
    },
    btnTienCQ_Click() {
      // nhapt -copy từ form Đặt mới thêm 09/06/2015
      try {
        this.$refs.frmCT_Tien_KM.frmCT_Tien_KM(LoaiHopDong.CHUYEN_QUYEN, this.ds_tien_khoanmuc)
      } catch (err) {
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },
    btnTien_Close(m_dsTienKM) {
      console.log(m_dsTienKM)
      this.ds_tien_khoanmuc = m_dsTienKM
      if (this.ds_tien_khoanmuc.length > 0) {
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DICHVU) {
            this.dtientn = Number(this.ds_tien_khoanmuc[i]['tien'])
            this.dvattn = Number(this.ds_tien_khoanmuc[i]['vat'])
          }
          if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_CHUYENQUYEN) {
            this.dtien_cq = parseFloat(this.ds_tien_khoanmuc[i]['tien'])
            this.dvat_cq = parseFloat(this.ds_tien_khoanmuc[i]['vat'])
          }
          this.LayTongTien_HDTB()
        }
      }
    },

    // btnTienCQ_Click() {
    //   try {
    //     let f = new frmCT_Tien_KM(
    //       LoaiHopDong.CHUYEN_QUYEN,
    //       this.ds_tien_khoanmuc
    //     );
    //     f.ShowDialog();
    //     if (f.chapnhan) {
    //       this.ds_tien_khoanmuc = f.m_dsTienKM;
    //       if (this.ds_tien_khoanmuc.length > 0) {
    //         for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
    //           if (
    //             this.ds_tien_khoanmuc[i]["khoanmuctt_id"] ==
    //             KHOANMUC_TT.KMTT_DICHVU
    //           ) {
    //             dtien_dv = parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
    //             dvat_dv = parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
    //           }

    //           if (
    //             this.ds_tien_khoanmuc[i]["khoanmuctt_id"] ==
    //             KHOANMUC_TT.KMTT_CHUYENQUYEN
    //           ) {
    //             dtien_cq = parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
    //             dvat_cq = parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
    //           }
    //           this.LayTongTien_HDTB();
    //         }
    //       }
    //     }
    //   } catch (err) {
    //     this.$toast.error("Có lỗi : " + err.message);
    //   }
    // },
    // nhapt thêm- 08/06/2015 Thực hiện lấy tổng tiền
    LayTongTien_HDTB() {
      try {
        let dtongtien = 0
        let dtongvat = 0
        // for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        //   dtongtien += Math.round(this.ds_tien_khoanmuc[i]["tien"]);
        //   dtongvat += Math.round(this.ds_tien_khoanmuc[i]["vat"]);
        // }

        for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_KM_LAPDAT || this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_KM_DATCOC) {
            if (this.ds_tien_khoanmuc[i]['tien'] < 0) {
              dtongtien += this.ds_tien_khoanmuc[i]['tien'] ? Math.round(this.ds_tien_khoanmuc[i]['tien']) : 0
              dtongvat += this.ds_tien_khoanmuc[i]['vat'] ? Math.round(this.ds_tien_khoanmuc[i]['vat']) : 0
            } else {
              dtongtien += Math.round(this.ds_tien_khoanmuc[i]['tien']) * -1
              dtongvat += Math.round(this.ds_tien_khoanmuc[i]['vat']) * -1
            }
          } else if (
            this.ds_tien_khoanmuc[i]['khoanmuctt_id'] != KHOANMUC_TT.KMTT_TRAGOP_THIETBI &&
            this.ds_tien_khoanmuc[i]['khoanmuctt_id'] != KHOANMUC_TT.KMTT_KM_THIETBI &&
            this.ds_tien_khoanmuc[i]['khoanmuctt_id'] != KHOANMUC_TT.KMTT_MUATHIETBI &&
            this.ds_tien_khoanmuc[i]['khoanmuctt_id'] != KHOANMUC_TT.KMTT_THUETHIETBI &&
            this.ds_tien_khoanmuc[i]['khoanmuctt_id'] != KHOANMUC_TT.KMTT_PHI_THUMOTLAN
          ) {
            dtongtien += this.ds_tien_khoanmuc[i]['tien'] ? Math.round(this.ds_tien_khoanmuc[i]['tien']) : 0
            dtongvat += this.ds_tien_khoanmuc[i]['vat'] ? Math.round(this.ds_tien_khoanmuc[i]['vat']) : 0
          }
        }

        this.$refs.txtTongTien.value = dtongtien
        this.$refs.txtTongVat.value = dtongvat
        this.$refs.lblTongTien.html = dtongtien + dtongvat
      } catch (err) {
        this.$toast.error('' + err.message)
      }
    },

    btnLoaiKH_Click() {
      try {
        let obj = new frmTimLoaiKH()

        if (this.$refs.cboLoaiKH.value != null && this.$refs.cboLoaiKH.value && this.$refs.cboLoaiKH.value != '-1') {
          //DevExpress.XtraGrid.Views.Grid.GridView gr1 = this.$refs.cboLoaiKH.View;
          //kieukh_id = -1;
          //kieukh = gr1.GetRowCellValue(gr1.FocusedRowHandle, "LOAI")
          //if (kieukh)
          //    kieukh_id = Number(kieukh);

          //nhomlkh_id = -1;
          //nhomkh = gr1.GetRowCellValue(gr1.FocusedRowHandle, "NHOMLKH_ID")
          //if (nhomkh)
          //    nhomlkh_id = Number(nhomkh);

          let drLKH = dtLoaiKH.Select('loaikh_id=' + this.$refs.cboLoaiKH.value)
          kieukh_id = -1
          nhomlkh_id = -1

          if (drLKH.length > 0) {
            kieukh_id = Number(drLKH[0]['LOAI'])
            nhomlkh_id = Number(drLKH[0]['NHOMLKH_ID'])
          }

          obj.loaikh_id = Number(this.$refs.cboLoaiKH.value)
          obj.kieukh_id = kieukh_id
          obj.nhomlkh_id = nhomlkh_id
        }

        obj.ShowDialog()
        if (obj.ChapNhan == true) {
          if (obj.loaikh_id != -1) {
            this.$refs.cboLoaiKH.value = obj.loaikh_id
            let gr = this.$refs.cboLoaiKH.View
            index = 0
            for (let i = 0; i < gr.letCount; i++) {
              if (gr.GetRowCellValue(i, 'LOAIKH_ID') == obj.loaikh_id) {
                index = i
                break
              }
            }
            gr.ClearSelection()
            gr.FocusedRowHandle = index
            gr.ShowEditor()
            gr.SelectRow(index)
          }
        }
      } catch (err) {}
    },
    /// <summary>
    /// true: ca nhan; false: doanh nghiep
    /// </summary>
    async cboLoaiKH2_EditValueChanged(data) {
      if (data && data.kieu_kh) {
        this.$refs.txtPhanLoaiKH.value = data.ma_loaikh
      }
    },

    tsbtnXoaTB_DS_Click() {},

    async ThemHDThueBao_DS(hdtb_id_ds, thuebao_id_ds, dichvuvt_id) {
      try {
        huonggiaotn_id = TaoDuLieu_HDTB_DS(this.hdtb_id_ds, this.thuebao_id_ds)
        TaoDuLieu_HDTBDV_DS(this.hdtb_id_ds, this.thuebao_id_ds)
        ///TuấnNA thêm code tạo dữ liệu thông tin thanh toán --- Ngày 15/09/2010

        TaoDuLieu_CT_TIENHD_DS(this.hdtb_id_ds, this.thuebao_id_ds)

        //
        TaoDuLieu_DiaChi_DS(this.hdtb_id_ds, this.thuebao_id_ds)
        //TaoDuLieu_DKDV();
        CapNhat_HDTB_ID_DangKyDV(this.hdtb_id)

        HienThiTTDBMoRong(this.thuebao_id_ds, this.dichvuvt_id)
        dsHDTB_CNTT = new ITEM_TYPE[0]()
        switch (this.dichvuvt_id) {
          case DichVuVienThong.CO_DINH:
            TaoDuLieu_HDTB_CD_DS(this.hdtb_id_ds, this.thuebao_id_ds)
            break
          case DichVuVienThong.DI_DONG:
            TaoDuLieu_HDTB_DD_DS(this.hdtb_id_ds, this.thuebao_id_ds)
            break
          case DichVuVienThong.ADSL:
          case DichVuVienThong.EMAIL:
            TaoDuLieu_HDTB_ADSL_DS(this.hdtb_id_ds, this.thuebao_id_ds)
            break
          case DichVuVienThong.GPHONE:
            TaoDuLieu_HDTB_GP_DS(this.hdtb_id_ds, this.thuebao_id_ds)
            break
          case DichVuVienThong.TSL:
          case DichVuVienThong.NOIBO_2DIEM:
            TaoDuLieu_HDTB_TSL_DS(this.hdtb_id_ds, this.thuebao_id_ds)
            break
          case DichVuVienThong.MEGAWAN:
          case DichVuVienThong.METRONET:
          case DichVuVienThong.NOIBO_2DIEM:
            TaoDuLieu_HDTB_MGWAN_DS(this.hdtb_id_ds, this.thuebao_id_ds)
            break
          case DichVuVienThong.IMS:
            TaoDuLieu_HDTB_IMS_DS(this.hdtb_id_ds, this.thuebao_id_ds)
            break
          default:
            TaoDuLieu_HDTB_CD_DS(this.hdtb_id_ds, this.thuebao_id_ds)
            break
        }
        async_status = false // Trạng thái có đồng bộ dữ liệu được không?
        if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA) {
          // true => mở hàm
          //region Thêm thuê bao vào hợp đồng trên vinaphone
          //TuanNA - 28/07/2010: Thực hiện đẩy dữ liệu lên VinaPhone
          if (this.CURRENT_DVVT_ID == DichVuVienThong.DI_DONG && this.CURRENT_LOAITB_ID() == LoaiHinhTB.DIDONGTRASAU) {
            //Tạo dữ liệu cho HDKH && HDTT
            TaoDuLieu_HDKH(false)
            TaoDuLieu_HDTT(false)
            ///Tuấn thêm code kiểm tra thuê bao di động đang làm có cùng mã thanh toán với các thue bao di động khác trong hợp đồng không
            ///--- Ngày 18/08/2010
            if (laphd.KIEMTRA_TB_CUNG_MATT_HDTB(Number(this.$refs.cboTT.value), DichVuVienThong.DI_DONG, this.$refs.txtMaGD.value) != 1) {
              this.$toast.error('Đối với thuê bao di động, tất cả các thuê bao trong cùng hợp đồng phải cùng mã thanh toán')
              this.$refs.cboTT.focus()
              return
            }
            //Nếu dữ liệu HĐ chưa được đẩy lên VinaPhone.
          }
          //endregion
        }
        //laphd.CAPNHAT_KHOSO(this.$refs.txtMaTB.value, "", this.dichvuvt_id, this.tt_nd.donvi_id);
        //this.ma_tb = this.$refs.txtMaTB.value;
        //region Cập nhật trạng thái đồng bộ lên vinaphone vào CSDL
        //Tuan them code cập nhật trạng thái đồng bộ dữ liệu lên vinaphone --- Ngày 18/08/2010
        //Kiểm tra thuê bao là di động trả sau --> cho phép cập nhật
        if (this.CURRENT_DVVT_ID == DichVuVienThong.DI_DONG && this.CURRENT_LOAITB_ID() == LoaiHinhTB.DIDONGTRASAU) {
          if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA) {
            // true => mở hàm
            if (async_status) {
              //Gọi hàm cập nhật trạng thái HDTB = 1: Đã đồng bộ dữ liệu với VinaPhone.
              let obj = new LapHopDongFacade()
              obj.CAPNHAT_STATUS_HDTB(this.hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
            }
          } else {
            //Gọi hàm cập nhật trạng thái HDTB = 1: Đã đồng bộ dữ liệu với VinaPhone.
            let obj = new LapHopDongFacade()
            obj.CAPNHAT_STATUS_HDTB(this.hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
          }
        }
        //endregion
        //this.$toast.success("Thêm thuê bao vào vào viễn thông tỉnh thành công!");
        //HienThiDanhSacHDTB(await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id));
        //this.ma_tb = this.$refs.txtMaTB.value;
      } catch (err) {
        console.log(err)
      }
    },

    async tsbtnThemTB_DS_Click() {
      try {
        if (this.hdkh_id != 0) {
          this.$refs.frmTC_DBTB_KH.showModal(
            //this.khachhang_id,
            this.khachhang_id_cq,
            0,
            0,
            this.hdkh_id,
            0
          )
        }
      } catch (err) {}
    },
    async setDBTB(data) {
      this.loading(true)
      await this.themTBReturn(data)
      this.HienThiDanhSacHDTB(await tchopdong.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id))
      this.ma_tb = this.$refs.txtMaTB.value
      this.loading(false)
    },

    themTBReturn(data) {
      return new Promise((resolve) => {
        var count = 0
        data.forEach(async (item) => {
          count++
          var row_tienhd = {}
          row_tienhd.TIEN = this.$refs.txtTongTien.value
          row_tienhd.VAT = this.$refs.txtTongVat.value
          row_tienhd.KHOANMUCTT_ID = this.khoanmuctt_id
          row_tienhd.TYLE_VAT = null
          row_tienhd.TYLE_VAT_ID = null

          var row_hdtb = {}
          if (this.P_ACTION == 0) {
            row_hdtb.HDTB_ID = null
            row_hdtb.KIEULD_ID = this.$refs.cboKieuLD.value
            row_hdtb.NGAY_SN = this.$refs.txtNgaySinhTB.value
            row_hdtb.GHICHU = this.$refs.txtGhiChuTB.value
          } else {
            row_hdtb.HDTB_ID = this.hdtb_id
            row_hdtb.KIEULD_ID = this.$refs.cboKieuLD.value
            row_hdtb.NGAY_SN = this.$refs.txtNgaySinhTB.value
            row_hdtb.GHICHU = this.$refs.txtGhiChuTB.value
          }

          var row_diachitb = {}
          if (this.P_ACTION == 0) {
            row_diachitb.DIACHI_ID = null
            row_diachitb.DIACHI = this.$refs.txtDiaChiTB.value
            row_diachitb.TINH_ID = this.tinhtb_id
            row_diachitb.QUAN_ID = this.quantb_id
            row_diachitb.PHUONG_ID = this.phuongtb_id
            row_diachitb.PHO_ID = this.photb_id
            row_diachitb.AP_ID = this.aptb_id
            row_diachitb.KHU_ID = this.khutb_id
            row_diachitb.SONHA = this.sonhatb
            row_diachitb.DACDIEM_ID = this.dacdiemtb_id
          }
          try {
            let response = await this.axios.post('web-hopdong/hopdong/khac/sp_chuyenquyen_them_tb_v2', {
              cmd: '',
              ct_tienhd_data: JSON.stringify(row_tienhd),
              diachi_hdtb_data: JSON.stringify(row_diachitb),
              ds_para: JSON.stringify({
                THUEBAO_ID: item.thuebao_id,
                MA_TB_CU: this.$refs.txtMaTBCu.value,
                HDKH_ID: this.hdkh_id,
                HDTT_ID: this.hdtt_id,
                KIEULD_ID: this.$refs.cboKieuLD.value,
                NGUOI_CN: this.$root.token.getUserName(),
                MAY_CN: await this.$root.token.getMachine(),
                IP_CN:  await this.$root.token.getIP()
              }),
              hd_thuebao_data: JSON.stringify(row_hdtb)
            })
            try {
              let result = rs.data
              if (result.error_code != 'BSS-00000000') {
                this.$toast.error(result.message)
              } else {
                this.$toast.success('Thêm thuê bao vào vào viễn thông tỉnh thành công!')
              }
            } catch (e) {
              if (e.data && e.data.message) {
                this.$toast.error(e.data.message)
              } else if (e.response && e.response.data && e.response.data.message) {
                this.$toast.error(e.response.data.message)
              } else {
                this.$toast.error(e.message)
              }
            }

            if (count == data.length) resolve(true)
          } catch (e) {
            this.$toast.error(e.message)
          }
        })
      })
    },

    async HienThi_NVQL_AM(i_loaikh_id, i_loainv_id, i_phuong_id, i_pho_id, i_ap_id, i_khu_id, i_nvtc_id, vdonviql_id) {
      try {
        if (this.kt_load == false) {
          let ds_nvql = []
          rs = await this.axios.post('web-hopdong/tachnhapthuebao/lay_ds_nhanvien_ql_am_1', {
            loaikh_id: i_loaikh_id,
            loainv_id: i_loainv_id,
            phuong_id: i_phuong_id,
            pho_id: i_pho_id,
            ap_id: i_ap_id,
            khu_id: i_khu_id,
            nvtc_id: i_nvtc_id,
            donviql_id: vdonviql_id
          })
          ds_nvql = rs.data.data
          if (ds_nvql && ds_nvql.length <= 0) {
            this.$refs.txtNhanVienQL.value = ''
            this.nhanvien_ql_am_id = 0
          } else {
            this.$refs.txtNhanVienQL.value = ds_nvql[0]['ma_nv'] + ' - ' + ds_nvql[0]['ten_nv']
            this.nhanvien_ql_am_id = Number(ds_nvql[0]['nhanvien_id'])
          }
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },

    async HienThi_NVQL_AM_HTTT(i_loaikh_id, i_loainv_id, i_phuong_id, i_pho_id, i_ap_id, i_khu_id, i_nvtc_id, vdonviql_id, vhttt_id) {
      try {
        if (kt_load == false) {
          let ds_nvql = []
          //ds_nvql = new TraCuuDanhBaFacade2().lay_ds_nhanvien_ql_am(i_loaikh_id, i_loainv_id, i_phuong_id, i_pho_id, i_ap_id, i_khu_id, i_nvtc_id);
          rs = await this.axios.post('web-hopdong/lapdatmoi/lay_ds_nhanvien_ql_am_v2', {
            vap_id: i_ap_id,
            vdonviql_id: vdonviql_id,
            vhttt_id: vhttt_id,
            vkhu_id: i_khu_id,
            vloaikh_id: i_loaikh_id,
            vloainv_id: i_loainv_id,
            vnvtc_id: i_nvtc_id,
            vphanvung_id: this.$root.token.getPhanVungID(),
            vpho_id: i_pho_id,
            vphuong_id: i_phuong_id
          })
          ds_nvql = rs.data.data

          if (ds_nvql.length <= 0) {
            this.$refs.txtNhanVienQL.value = ''
            this.nhanvien_ql_am_id = 0
          } else {
            this.$refs.txtNhanVienQL.value = ds_nvql[0]['ma_nv'] + ' - ' + ds_nvql[0]['ten_nv']
            this.nhanvien_ql_am_id = Number(ds_nvql[0]['nhanvien_id'])
          }
        }
      } catch (err) {
        console.log(err)
        this.$toast.error('Có lỗi : ' + err)
      }
    },

    async HienThi_NVQL_AM_DB_HD(
      i_kieu,
      id // Kieu = 1 : danh ba, kieu = 2 : hop dong
    ) {
      try {
        let str = ''
        let ds_tt = []
        rs = await this.axios.post('web-hopdong/lapdatmoi/sp_lay_nhanvien_ql', {
          id: id,
          kieu: i_kieu,
          phanvung_id: this.$root.token.getPhanVungID()
        })
        ds_tt = rs.data.data
        if (ds_tt && ds_tt.length > 0) {
          this.$refs.txtNhanVienQL.value = ds_tt[0]['ma_nv'] + ' - ' + ds_tt[0]['ten_nv']
          this.nhanvien_ql_am_id = Number(ds_tt[0]['nhanvien_id'])
        } else {
          this.$refs.txtNhanVienQL.value = ''
          this.nhanvien_ql_am_id = 0
        }
      } catch (err) {
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },

    Init_Properties(vloaitb_id, table_name, opt_list, ItemList) {
      try {
        let ds = []
        let property = new Property_listFacade()
        ds = property.Lay_danhsach_thuoctinh_v2(vloaitb_id, table_name, opt_list)
        ItemList = new ITEM_TYPE[ds.length]()
        if (ItemList.length == 0) return
        for (let i = 0; i < ItemList.length; i++) {
          let row = ds[i]
          ItemList[i].sFieldName = row['field_name']
          ItemList[i].sCaption = row['ten_hienthi']
          ItemList[i].iAtt = Convert.ToByte(row['att'])
          ItemList[i].iDataType = Convert.ToByte(row['data_type'])
          ItemList[i].sMaxValue = row['max_value']
          ItemList[i].sMinvalue = row['min_value']
          ItemList[i].sValue = row['field_value']
          ItemList[i].nFieldLength = !row['field_length'] ? 0 : Number(row['field_length'])
          ItemList[i].bIsNotNull = !row['field_nullable'] ? false : row['field_nullable'] == '0' ? true : false
          ItemList[i].bIsUnique = !row['field_unique'] ? false : row['field_unique'] == '0' ? false : true
          ItemList[i].sLookUpTableName = row['parent_table']
          ItemList[i].sLookUpKeyField = row['parent_keyfield']
          ItemList[i].sLookUpValueField = row['parent_descfield']
          ItemList[i].strDepandField = row['depend_field']
          ItemList[i].StrSql = row['lookup_sql']
        }
      } catch (err) {
        this.$toast.success('Có lỗi: ' + err.message.Message)
      }
    },

    frmChuyenQuyenSD_Shown() {
      this.$refs.lvDvkhac_TT.Size = new Size(this.$refs.lvDvkhac_TT.Width, this.$refs.lvwThanhToan.Height)
    },

    cboNganHangTT_EditValueChanged() {
      cboNganHangTT_SelectionChangeCommitted()
    },

    chonNhanvien(item) {
      console.log(item)
      let f = this.$refs.frmNguoiGT
      if (item && item.ten_nv) {
        if (f.nhomlnv_id == NHOM_LNV.CONGTACVIEN) {
          this.$refs.txtCTV.value = item.ten_nv
          this.ctv_id = item.nhanvien_id ? item.nhanvien_id : 0
          this.loainv_ctv_id = item.loainv_id ? item.loainv_id : '0'
          this.donvi_ctv_id = item.donvi_id ? item.donvi_id : '0'
        } else {
          this.$refs.txtNguoiGT.value = item.ten_nv ? item.ten_nv : ''
          this.nguoigt_id = item.ctv_id ? item.ctv_id : 0
          this.loainv_nguoi_gt_id = item.loainv_id ? item.loainv_id : '0'
          this.donvi_nguoi_gt_id = item.donvi_id ? item.donvi_id : '0'
        }
      }
    },

    chkCTV_CheckedChanged() {
      if (this.$refs.chkCTV.checked) {
        // let f = this.$refs.frmNguoiGT;
        // f.nhomlnv_id = NHOM_LNV.CONGTACVIEN;
        // f.donvi_id = this.donvi_ctv_id;
        // f.loainv_id = this.loainv_ctv_id;
        // f.ctv_id = this.ctv_id;
        // f.ShowDialog();
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien')
        this.popupComponentName = 'popupCTV'
        // SearchAccount.data.isPopup = true;
        this.Popup('popupComponents')
      }
    },

    chkNguoiGT_CheckedChanged() {
      if (this.$refs.chkNguoiGT.checked) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu')
        this.popupComponentName = 'popupNGT'
        // SearchAccount.data.isPopup = true;
        this.Popup('popupComponents')
      }
    },

    txtCTV_KeyPress() {
      e.Handled = true
    },

    txtNguoiGT_KeyPress() {
      e.Handled = true
    },

    txtSoGT_TextChanged() {
      if (this.$refs.txtSoGT.value) {
        if (!Regex.IsMatch(this.$refs.txtSoGT.value, '^[a-zA-Z0-9]*$')) {
          this.$toast.success('Số giấy tờ không hợp lệ.Số giấy tờ gồm các ký tự từ 0->9a->z. Hãy kiểm tra lại')
          this.$refs.txtSoGT.focus()
          return
        }
      }
    },

    LookupByAccount(vmatb, dichvuvt_id, loaitb_id) {
      this.$refs.txtMaTB.value = vmatb
      this.$refs.txtMaTB_KeyPress(this.$refs.txtMaTB, new KeyPressEventArgs(Convert.ToChar(Keys.Enter)))
    },

    lineLabel11_Load() {},

    txtTelKH_TextChanged() {
      if (isHNI) {
        this.$refs.tbDienThoaiLienHe.value = sender.value
      }
    },

    txtMaSTKH_TextChanged() {
      if (isHNI) {
        this.$refs.txtMST_TT.value = sender.value
      }
    },

    KIEMTRA_HTTT(ds_httt, vhttt_id_cu, vhttt_id_moi) {
      try {
        var httt_cu = ds_httt.Select('HTTT_ID=' + vhttt_id_cu)
        var httt_moi = ds_httt.Select('HTTT_ID=' + vhttt_id_moi)
        if (httt_cu.length > 0) {
          //Nếu hình thức cũ đang sử dung =1  mà hình mới = 0 thì ko cho
          //Còn lại httt cũ = 1 httt_moi = 1 ok
          // httt_cu=0 và httt moi =0 hoặc 1 ok
          if (httt_cu[0]['SUDUNG'] == '1') {
            if (httt_moi.length > 0) {
              if (httt_moi[0]['SUDUNG'] == '0') {
                this.$toast.error('[PTTB] Bạn đang chọn hình thức không được sử dụng. Bạn hãy chọn lại')
                return false
              }
            }
          }
        } else {
          if (httt_moi.length > 0) {
            if (httt_moi[0]['SUDUNG'] == '0') {
              this.$toast.error('[PTTB] Bạn đang chọn hình thức không được sử dụng. Bạn hãy chọn lại')
              return false
            }
          }
        }

        return true
      } catch (err) {
        this.$toast.error('[PTTB] Lỗi khi kiểm tra HTTT ' + err.message)
        return false
      }
    },

    //region KT_MNS
    async btnTTKhac_Click() {
      try {
        if (!this.$refs.cboLoaiKH.value) {
          this.$toast.error('Bạn hãy chọn loại khách hàng trước')
          return
        }
        if (this.$refs.tsbtnNhapMoi.classList.contains('disabled') && this.khachhang_id > 0) {
          // this.$toast.error("Bạn hãy lập hợp đồng rồi điều chỉnh");
          //  return;
          this.dsHDKH_Sub = []
          rs = await this.axios.post('/web-quantri/lay-dulieu/sp_tt_dbkh_sub', {
            param: this.khachhang_id,
            type: 1
          })
          let dthdkh_sub = rs.data.data
          if (this.dthdkh_sub && this.dthdkh_sub.length > 0) {
            this.TAO_DULIEU_HDKH_SUB(dthdkh_sub, 0)
          }
        }
        var dt = this.$refs.cboLoaiKH.dataSource
        if (dt == null || dt.length == 0) {
          this.$toast.error('Không có thông tin loại khách hàng')
          return
        }

        let vkieukh_id_ = -1
        temp = dt.filter((item) => item.loaikh_id == this.$refs.cboLoaiKH.value)
        this.kieukh = temp.length > 0 ? temp[0].loai : 0
        if (this.kieukh) vkieukh_id_ = Number(this.kieukh)
        this.userComponentModule = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupInforAdd')
        this.popupComponentName = 'popupInforAdd'
        this.popupComponentData = {
          _nhapmoi: this.$refs.tsbtnNhapMoi.classList.contains('disabled'),
          _vkieukh_id: vkieukh_id_,
          loaikh: this.$refs.cboLoaiKH.value,
          _hdkh_id: this.hdkh_id,
          _vma_kh: this.$refs.txtMaKH.value,
          _mast: this.$refs.txtMaSTKH.value,
          _ts_kt_mstmns_kh: this.ts_kt_mstmns_kh,
          _ts_kt_tthdkhdn: this.ts_kt_tthdkhdn,
          _dichvuvt_id: this.dichvuvt_id
        }
        this.$refs.thanhtoanDialog.show(true)
      } catch (err) {
        this.$toast.error(err.message)
      }
    },

    TAO_DULIEU_HDKH_SUB(ds_hdkh_sub, v_hdkh_id) {
      let ds = []
      let row = {}
      row.CHUCVU_GT = ds_hdkh_sub[0]['ChucVu_GT']
      row.HDKH_ID = v_hdkh_id
      if (ds_hdkh_sub[0]['NgayCap_GT']) row.NGAYCAP_GT = ds_hdkh_sub[0]['NgayCap_GT']
      row.NGUOI_GT = ds_hdkh_sub[0]['Nguoi_GT']
      if (ds_hdkh_sub[0]['So_Phong']) row.SO_PHONG = Number(ds_hdkh_sub[0]['So_Phong'])

      row.SOGIAY_GT = ds_hdkh_sub[0]['SoGiay_GT']
      row.MA_NS = ds_hdkh_sub[0]['MA_NS']
      if (this.ts_kt_tthdkhdn == 1) {
        row.SO_HD_KHDN = ds_hdkh_sub[0]['SO_HD_KHDN']
        row.MA_KHDN = ds_hdkh_sub[0]['MA_KHDN']
        if (ds_hdkh_sub[0]['GIATRI_HD'] != null && ds_hdkh_sub[0]['GIATRI_HD'] != DBNull.value) {
          row.GIATRI_HD = Number(ds_hdkh_sub[0]['GIATRI_HD'])
        }
        if (ds_hdkh_sub[0]['NGAY_KY_HD'] != DBNull.value && ds_hdkh_sub[0]['NGAY_KY_HD'] != null) {
          row.NGAY_KY_HD = ds_hdkh_sub[0]['NGAY_KY_HD']
        }
        row.GIAY_UQ = ds_hdkh_sub[0]['GIAY_UQ']
        if (ds_hdkh_sub[0]['LOAIHDKH_ID'] != null && ds_hdkh_sub[0]['LOAIHDKH_ID'] != DBNull.value && ds_hdkh_sub[0]['LOAIHDKH_ID'] != '-1') {
          row.LOAIHDKH_ID = Number(ds_hdkh_sub[0]['LOAIHDKH_ID'])
        }
        if (ds_hdkh_sub[0]['TINHTRANGHD_ID'] != null && ds_hdkh_sub[0]['TINHTRANGHD_ID'] != DBNull.value && ds_hdkh_sub[0]['TINHTRANGHD_ID'] != '-1') {
          row.TINHTRANGHD_ID = Number(ds_hdkh_sub[0]['TINHTRANGHD_ID'])
        }
      }
      ds._HDKH_Sub.push(row)
      this.dsHDKH_Sub = ds
    },

    txtMaSTKH_Leave() {
      this.$refs.txtMST_TT.value = this.$refs.txtMaSTKH.value
    },
    //endregion KT_MNS

    /*,cboNganhNghe_SelectedValueChanged()
            {
                if (cboNganhNghe.length > 0)
                {
                    if (cboNganhNghe.value == "System.Data.letView") return;
                    if (cboNganhNghe.value == null) return;

                    if (this.phantap_kh)
                    {
                        if (this.phuongtt_id != 0 && (this.phott_id != 0 || this.aptt_id != 0 || this.khutt_id != 0))
                        {
                            let ds = [];
                            ds = new LapHopDongFacade2().LAY_DS_DONVIQL_PHANTAP(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.dacdiemtt_id, 1,
                                        Number(this.$refs.cboLoaiKH.value), Number(cboNganhNghe.value), this.pt_doanhthu, this.pt_diem_tn);

                            if (ds.length <= 0)
                            {
                                if (this.$refs.cboDonviQL.length != 1)
                                {
                                    this.$toast.error("Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!");
                                    this.$refs.cboDonviQL.value = "";
                                    return;
                                }
                            }
                            else if (ds.length == 1)
                                this.$refs.cboDonviQL.value = Number(ds[0]["donvi_id"]);
                            else
                            {
                                let frm = new frmChonDonVi(ds);
                                frm.donvi_index_id = Number(this.$refs.cboDonviQL.value);
                                frm.ShowDialog();
                                if (frm.chapnhan)
                                    this.$refs.cboDonviQL.value = frm.this.donvi_ql_id;
                                else
                                    return;
                            }

                            HienThi_NVQL_AM_PTKH(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.dacdiemtt_id, Number(this.$refs.cboLoaiKH.value), Number(cboNganhNghe.value),
                                this.pt_doanhthu, this.pt_diem_tn, Loai_NV.NHANVIEN_AM, Number(this.$refs.cboDonviQL.value));
                        }
                    }
                }
            }*/

    /*,kt_cbo_nganhnghe()
            {
                if (Number(this.$refs.cboNganhNgheLoc.value) < 0)
                {
                    if (tabThongTin.SelectedIndex == 0)
                    this.$toast.error("Bạn chưa chọn ngành nghề !");
                    this.$refs.cboNganhNgheLoc.focus();
                    return false;
                }
                return true;
            }*/

    async cboNganhNgheLoc_EditValueChanged() {
      try {
        let select_id = Number(this.$refs.cboNganhNgheLoc.value)
        if (select_id < 0) return
        if (this.nganhnghe_id != select_id) {
          this.TNC1_ID = 0
          this.TNC2_ID = 0
          this.TNC3_ID = 0
          this.TNC4_ID = 0
        }
        this.btnNganhNghe_Click()

        if (this.phantap_kh) {
          if (this.phuongtt_id != 0 && (this.phott_id != 0 || this.aptt_id != 0 || this.khutt_id != 0)) {
            let ds = []
            rs = await this.axios.post('web-hopdong/tachnhapthuebao/lay_ds_donviql_ptkh', {
              phuong_id: this.phuongtt_id,
              pho_id: this.phott_id,
              ap_id: this.aptt_id,
              khu_id: this.khutt_id,
              kieu: 1,
              loaikh_id: this.$refs.cboLoaiKH.value,
              httt_id: this.$refs.cboHTTT.value,
              dacdiem_id: this.dacdiemtt_id,
              nganhnghe_id: this.$refs.cboNganhNgheLoc.value,
              doanhthu: this.pt_doanhthu,
              diemtn: this.pt_diem_tn
            })
            ds = rs.data.data

            if (ds && ds.length <= 0) {
              if (this.$refs.cboDonviQL.length != 1) {
                this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!')
                this.$refs.cboDonviQL.value = ''
                return
              }
            } else if (ds.length == 1) this.$refs.cboDonviQL.value = Number(ds[0]['donvi_id'])
            else {
              let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.$refs.cboDonviQL.value, null, ds)
              if (resolvePromise.ok) {
                this.$refs.cboDonviQL.value = resolvePromise.data.donviql_id
              } else return
            }

            this.HienThi_NVQL_AM_PTKH(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.dacdiemtt_id, Number(this.$refs.cboLoaiKH.value), Number(this.$refs.cboNganhNgheLoc.value), this.pt_doanhthu, this.pt_diem_tn, Loai_NV.NHANVIEN_AM, Number(this.$refs.cboDonviQL.value))
          }
        }
      } catch (err) {
        this.$toast.error('Ngành nghề: ' + err.message)
      }
    }
  }
}
</script>
<style>
.modal-content .page-content {
  position: unset;
}

.modal-content .main-wrapper {
  position: unset;
}

#frmThanhToanHD .modal-xl {
  max-width: 90% !important;
}

.btn-second {
  background-color: rgb(255, 255, 255) !important;
  border-color: rgb(1, 118, 255) !important;
  color: rgb(1, 118, 255) !important;
  font-weight: 600 !important;
}

/* .select2-container {
  width: auto !important;
} */
</style>
