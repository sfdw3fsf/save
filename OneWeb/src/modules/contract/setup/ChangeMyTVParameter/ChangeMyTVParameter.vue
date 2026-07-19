<template src="./ChangeMyTVParameter.html"></template>
<style scoped src="./ChangeMyTVParameter.scss"></style>
<script
  type="text/javascript"
  src="https://cdn.bootcss.com/select2/4.0.0/js/i18n/vi.js"
></script>
<script>
import ChangeMyTVParameterAPI from './ChangeMyTVParameterAPI'
import breadcrumb from '@/components/breadcrumb'
import ActionTop from '@/components/kylq_components/ActionTop'
import moment from 'moment'
import {LoaiHinhTB, TrangThaiTB} from '../../../../const/enums'
import LoaiHopDong from '../../../../const/enums/LoaiHopDong'
import {DataManager, ODataAdaptor, Predicate, Query} from '@syncfusion/ej2-data'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import {KHOANMUC_TT, KieuLapDat, TRANGBI} from '../InstallNewSubs/Enums'
import columns from './define/columns'
import DataGridCustom from './components/DataGrid'
// import SearchContractModal from "../ChangeInstallUnit/popup/SearchContract/SearchContractModal"
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import {TrangThaiHD} from '../../../../utils/Enum'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import VatTuThueBao from "../ChangeServiceOMCVIPCM/popups/VatTuThueBao"
export default {
  name: 'ChangeMyTVParameter',
  components: {
    breadcrumb,
    ActionTop,
    DataManager,
    Query,
    ODataAdaptor,
    Predicate,
    KDatePicker,
    DataGridCustom,
    SearchContractModal,
    SearchAccount,
    VatTuThueBao
  },
  data() {
    return {
      ...columns,
      header: {
        title: 'Thay đổi Thông số MYTV',
        list: [
          // Lập hợp đồng/Đổi tốc độ - Loại hình thuê bao/Thay đổi thông số mytv
          {name: 'Lập hợp đồng/Đổi tốc độ', link: {name: 'Ui.cards'}},
          {
            name: 'Loại hình thuê bao/Thay đổi thông số mytv',
            link: {name: 'Ui.buttons'}
          }
        ]
      },
      dichvuvt_id: 0,
      doituong_id: 1, // Default tư nhân
      hdkh_id: 0,
      hdtb_id: 0,
      khachhang_id: 0,
      hdkh_cha_id: 0,
      thuebao_id: 0,
      loaitb_id: 0,
      muccuoc_id: 0,
      tocdo_id: 0,
      vtrangbi_id: 0,
      vtrangbi_id_cu: 0,
      diachi_tb: '',
      donvi_id: 0,
      trangbi_id: 3, // Default khách hàng tự trang bị,
      bangts: [],
      phuuongld_id: 0, // Lấy cho hàm lấy tiền theo khoản mục
      phold_id: 0, // Lấy cho hàm lấy tiền theo khoản mục,
      apld_id: 0,
      khuld_id: 0,
      ltb_adsl_id: 4,
      ts_sinhma_gd_theo_donvi: false,
      ts_td_trangbi_mytv: false,
      loi_sinh_magd: false,
      dtList: [],
      CT_TIENHD_ID: 0,
      diachi_ld: '',
      popupComponent: null,
      diachild_id: '',
      huonggiaotb_id: 0,
      quytrinhtn_id: 0,
      VPHIEUCSKH_ID: 0,
      VPHIEUCSKHDV_ID: 0,
      LOAITBI_ADSL: {
        ADSL: 1,
        SHDL: 2,
        CPE: 3,
        SET_TOP_BOX: 4,
        CONG_FE: 5,
        CONG_GEL: 6
      },
      settingSelect2: {
        language: "vi",
      },
      actions: [
        {
          id: 'nhapmoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'one-file-plus'
        },
        {
          id: 'ghilai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save'
        },
        {
          id: 'huy',
          name: 'Hủy',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove'
        },
        {
          id: 'thanhtoan',
          name: 'Thanh toán',
          active: true,
          icon_class: 'nc-icon-glyph shopping_credit-card'
        },
        {
          id: 'xoa',
          name: 'Xóa HĐ',
          active: true,
          icon_class: 'one-trash'
        },
        {
          id: 'hoanthien',
          name: 'Hoàn Thiện',
          active: true,
          icon_class: 'one-file-check'
        },
        {
          id: 'kichhoat',
          name: 'Kích hoạt',
          active: false,
          icon_class: 'one-file-arrow-up'
        },
        {
          id: 'vattu',
          name: 'Vật tư',
          active: false,
          icon_class: 'one-vt'
        }
      ],
      totalPrice: 0,
      form_thongTinPhieuYeuCau: {
        txtMaGD: '',
        txtMaHD: '',
        dtpNgayYeuCau: moment(new Date()).format('DD/MM/YYYY'),
        dtpNgayLapHD: moment(new Date()).format('DD/MM/YYYY'),
        txtMaKH: '',
        txtTenKH: '',
        txtDiaChiKH: ''
      },
      form_thongTinThueBao: {
        txtTongTienHD: 0,
        txtMaDuAn: '',
        txtMaTB: '',
        txtTenTB: '',
        txtTrangBiCu: '',
        selectTrangBi: null,
        selectMucCuocTB: null,
        txtLoaiTbiCu: '',
        selectLoaiTB: null,
        txtMucCuocTBCu: '',
        selectDichVuVT: 4,
        selectLoaiHinhTB: 61,
        txtGhiChu: '',
        selectKieuLD: null,
        txtDiaChiLD: '',
        txtTienHD: '0',
        txtVatHD: '0'
      },
      options: {
        options_dsTrangBi: [],
        options_dsMucCuocTB: [],
        options_dsLoaiThietBi: [],
        options_dsKieuYeucau: [],
        options_dsDichVuVT: [],
        options_dsLoaiHinhTB: []
      },
      waterMaskConfig: {
        waterMarkcbDichVuVT: 'Chọn dịch vụ VT',
        waterMarkcbLoaiHinhTB: 'Chọn loại hình thuê bao',
        waterMarkcbKieuYC: 'Chọn kiểu yêu cầu',
        waterMarkcbTrangBiMoi: '---Chọn trang bị---',
        waterMarkcbMucCuocTB: '---Chọn mức cước thuê bao---',
        waterMarkcbLoaiTBMoi: '---Chọn loại thiết bị'
      },
      disableBox: {
        txtTrangBiCu: true,
        txtMucCuocTBCu: true,
        cboTrangBiMoi: true,
        cboMucCuocTBMoi: true,
        txtLoaiTbiCu: true,
        cboLoaiTbiMoi: true
      },
      modelVatTu: {
        phieu_id: 0,
        loaihd_id: 0,
        hdtb_id: 0,
        thuebao_id: 0
      },
      selectThueBao: null,
      dsParam: [],
      dsHDKH: [],
      dsHDTB: [],
      dsHDTBADSL: [],
      dsHDTBDV: [],
      dsCTTHD: [],
      target: '.main-wrapper',
      maycn: '',
      ipcn: '',
      modelSearchContract: {
        loai_hd_id: 0,
        dichvu_id: 0,
        ngay_yc: '',
        ngay_kt: '',
        trangthai_hd: TrangThaiHD.MOI
      }
    }
  },
  watch: {
    'form_thongTinThueBao.selectDichVuVT': async function (newValue, oldValue) {
      this.dichvuvt_id = newValue
      let dsLHTB = await this.sp_tt_loaihinh_tb(newValue, 1)
      this.form_thongTinThueBao.selectLoaiHinhTB = '0'
      if (dsLHTB.length > 0) {
        this.options.options_dsLoaiHinhTB = dsLHTB
      }
      this.form_thongTinThueBao.selectLoaiHinhTB = LoaiHinhTB.INTERNET_MYTV
      this.options.options_dsKieuYeucau = await this.LAY_DS_KIEU_LD(newValue, LoaiHopDong.THAYDOI_THONGSO_MYTV)
    },
    'form_thongTinThueBao.selectKieuLD': async function (newValue, oldValue) {
      if (newValue == KieuLapDat.TD_TBI_MYTV) {
        this.disableBox.txtTrangBiCu = false
        this.disableBox.txtMucCuocTBCu = false
        this.disableBox.cboTrangBiMoi = true
        this.disableBox.cboMucCuocTBMoi = true
        this.disableBox.txtLoaiTbiCu = false
        this.disableBox.cboLoaiTbiMoi = true
      } else {
        this.disableBox.txtTrangBiCu = true
        this.disableBox.txtMucCuocTBCu = true
        this.disableBox.cboTrangBiMoi = false
        this.disableBox.cboMucCuocTBMoi = false
        this.disableBox.txtLoaiTbiCu = true
        this.disableBox.cboLoaiTbiMoi = false
      }
      await this.LayTienTheoKhoanMuc()
    },
    'form_thongTinThueBao.selectTrangBi': async function (newValue) {
      console.log(newValue)
      let dsMucCuocTB = await this.LAY_DS_MUCUOC_TB_V2(0, 0,
        0, 0, this.form_thongTinThueBao.selectLoaiHinhTB, this.doituong_id,
        this.tocdo_id, this.muccuoc_id, '', newValue)
      if (dsMucCuocTB.length > 0) {
        this.options.options_dsMucCuocTB = dsMucCuocTB
      }
    }
  },
  computed: {},
  mounted() {
    this.initData()
    this.LoadFromExternalForm()
  },
  methods: {
    async sp_tt_db_thuebao (param) {
      return this.GetDataList(await ChangeMyTVParameterAPI.sp_tt_db_thuebao(this.axios, { param: param, type: 3 }))
    },
    async sp_tt_dbtb_dv (thuebaoid) {
      return this.GetDataList(await ChangeMyTVParameterAPI.sp_tt_dbtb_dv(this.axios, {"p_param": thuebaoid, "p_type":1}))
    },
    async sp_tt_loaihinh_tb (param, type) {
      return this.GetDataList(await ChangeMyTVParameterAPI.sp_tt_loaihinh_tb(this.axios, {"param": param, "type": type}))
    },
    LoadFromExternalForm () {
      let phieucskhid = 0
      let phieucskhdvid = 0
      if (
        this.$route.query.phieucskh_id != null &&
        this.$route.query.phieucskh_id !== '' &&
        this.$route.query.phieucskh_id.length > 0
      ) {
        phieucskhid = Number(this.$route.query.phieucskh_id)
      }
      if (
        this.$route.query.phieucskhdv_id != null &&
        this.$route.query.phieucskhdv_id !== '' &&
        this.$route.query.phieucskhdv_id.length > 0
      ) {
        phieucskhdvid = Number(this.$route.query.phieucskhdv_id)
      }
      if (phieucskhid > 0) {
        this.LoadTuHopDongB2A(phieucskhid, phieucskhdvid)
      }
    },
    showPopupSearchContract() {
      this.modelSearchContract.loai_hd_id = Number(LoaiHopDong.THAYDOI_THONGSO_MYTV)
      this.modelSearchContract.dichvu_id = 0
      this.modelSearchContract.ngay_yc = moment(new Date()).format('DD/MM/YYYY')
      this.$refs.popupSearchContract.showModal()
    },
    acceptSearchContract(item) {
      this.form_thongTinPhieuYeuCau.txtMaGD = item.ma_gd
      if (this.form_thongTinPhieuYeuCau.txtMaGD && this.form_thongTinPhieuYeuCau.txtMaGD.trim() !== '') {
        this.onMaGDEnter()
      }
    },
    showPopupSearchContact() {
      this.popupComponent = SearchAccount
      SearchAccount.data.isPopup = true
      this.Popup('popupComponents')
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
    },
    popupClosed(val) {
      if (this.popupComponent == SearchAccount) {
        if (val) {
          if (val.ma_tb) this.form_thongTinThueBao.txtMaTB = val.ma_tb.trim()
          this.onMaTBEnter()
        }
      }
      this.ClosePopup('popupComponents')
    },
    async onMaGDEnter() {
      if (this.form_thongTinPhieuYeuCau.txtMaGD !== '') {
        let dsHd = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.form_thongTinPhieuYeuCau.txtMaGD.trim(), LoaiHopDong.THAYDOI_THONGSO_MYTV, TrangThaiHD.MOI, 0)
        await this.HienThiTTHopDongKH(dsHd)
      }
    },
    async onMaTBEnter() {
      if (this.form_thongTinThueBao.txtMaTB !== '') {
        let dsHDTheoMaTb = await this.LAY_DS_HOPDONG_THEO_MA_TB(this.form_thongTinThueBao.txtMaTB,
          LoaiHopDong.THAYDOI_THONGSO_MYTV,
          this.$root.token.getDonViID(), TrangThaiHD.MOI, this.$root.token.getNhanVienID(), 0, this.form_thongTinThueBao.selectDichVuVT, 0
        )
        if (dsHDTheoMaTb.length > 0) {
          await this.HienThiTTHopDongKH(dsHDTheoMaTb)
        } else {
          await this.HienThiTT_DanhBa(this.form_thongTinThueBao.txtMaTB.trim())
        }
      }
    },
    onFilteringDichVuVT(e) {
      let query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('TEN_DVVT', 'startswith', e.text.trim(), true)
        // predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.options.options_dsDichVuVT, query)
    },
    onFilteringLoaiHinhTB(e) {
      let query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('loaihinh_tb', 'startswith', e.text.trim(), true)
        // predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.this.options.options_dsLoaiHinhTB, query)
    },
    onFilteringKieuLD(e) {
      let query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('TEN_KIEULD', 'startswith', e.text.trim(), true)
        // predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.options.options_dsKieuYeucau, query)
    },
    onFilteringTrangBi(e) {
      let query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('TENTRANGBI', 'startswith', e.text.trim(), true)
        // predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.options.options_dsTrangBi, query)
    },
    onFilteringLoaiThieiBi(e) {
      let query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('ten_tbi', 'startswith', e.text.trim(), true)
        // predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.options.options_dsLoaiThietBi, query)
    },
    onFilteringMucCuocTB(e) {
      let query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('tenmuccuoc', 'startswith', e.text.trim(), true)
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.options.options_dsMucCuocTB, query)
    },
    async initData() {
      this.setActiveActions(-1)
      await this.loadDefaultParam()
      let dsDv = await this.CSS_DICHVU_VT()
      this.options.options_dsDichVuVT = dsDv.filter(item => item.DICHVUVT_ID == 4)
      let dsKieuLapDat = await this.CSS_KIEU_LD()
      this.options.options_dsKieuYeucau = dsKieuLapDat.filter(item => item.LOAIHD_ID == LoaiHopDong.THAYDOI_THONGSO_MYTV && LoaiHinhTB.INTERNET_MYTV)
      this.form_thongTinThueBao.selectKieuLD = this.options.options_dsKieuYeucau[0].KIEULD_ID
      this.options.options_dsTrangBi = await this.CSS_TRANGBI()
      let dsLoaiThietBi = await this.SP_LAY_DS_LOAI_TB_LHTB(LoaiHinhTB.INTERNET_MYTV)
      dsLoaiThietBi = dsLoaiThietBi.filter(item => item.ten_tbi !== null)
      if (dsLoaiThietBi.length > 0) {
        let firstIndexLoaiTb = dsLoaiThietBi[0].thietbidc_id
        this.options.options_dsLoaiThietBi = dsLoaiThietBi
        this.form_thongTinThueBao.selectLoaiTB = firstIndexLoaiTb
      }
      let dsLHTB = await this.CSS_LOAIHINH_TB()
      this.options.options_dsLoaiHinhTB = dsLHTB.map((x) => ({
        loaitb_id: x.LOAITB_ID,
        loaihinh_tb: x.LOAIHINH_TB == null ? "" : x.LOAIHINH_TB,
      }));
      let dsThamSoMd = await this.LAY_DS_THAMSO_MD()
      if (dsThamSoMd.length > 0) {
        this.ts_sinhma_gd_theo_donvi = dsThamSoMd.filter(item => item.ma_ts == 'SINH_MA_GD_THEO_DV').length > 0
        this.ts_td_trangbi_mytv = dsThamSoMd.filter(item => item.ma_ts == 'TD_TRANGBI_MYTV').length > 0
      }
      this.$refs.tenkhachhang.focus()
      if (this.form_thongTinPhieuYeuCau.txtMaGD) {
        let dsHDKH = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.form_thongTinPhieuYeuCau.txtMaGD.trim(), LoaiHopDong.THAYDOI_THONGSO_MYTV, TrangThaiHD.MOI, 0)
        await this.HienThiTTHopDongKH(dsHDKH)
      }
      if (this.$route.query.ma_tb != null && this.$route.query.ma_tb && this.$route.query.ma_tb.length > 0) {
        this.form_thongTinThueBao.txtMaTB = this.$route.query.ma_tb
        await this.onMaTBEnter()
      }
      if (this.$route.query.ma_kh != null && this.$route.query.ma_kh && this.$route.query.ma_kh.length > 0) {
        this.form_thongTinPhieuYeuCau.txtMaKH = this.$route.query.ma_kh
      }
      if (this.$route.query.ten_kh != null && this.$route.query.ten_kh && this.$route.query.ten_kh.length > 0) {
        this.form_thongTinPhieuYeuCau.txtTenKH = this.$route.query.ten_kh
      }
    },
    async loadDefaultParam() {
      this.maycn = await this.$root.token.getMachine()
      this.ipcn = await this.$root.token.getIP()
    },
    async HienThiTT_DanhBa(maTb) {
      let danhBa = await this.LAY_DANHBA_THEO_MATB(maTb, this.form_thongTinThueBao.selectDichVuVT, 0)
      if (danhBa !== null) {
        // TuấnNA thêm code thông báo thuê bao lập PL khác
        // Kiểm tra xem mã TB này có đang được lập hợp đồng hoặc phụ lục khác hay không?
        /*let check = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(maTb.trim(), "0", Number.parseInt(this.form_thongTinThueBao.selectDichVuVT), LoaiHopDong.THAYDOI_THONGSO_MYTV)
        if (check == false) {
         return
        }*/
        if (danhBa.trangthaitb_id !== TrangThaiTB.THUONG) {
          this.$root.$toast.warning('Thuê bao có mã: ' + danhBa.ma_tb +
            ' đang ở trạng thái: ' + danhBa.trangthai_tb +
            ' bạn không thể thực hiện thay đổi thông số cho thuê bao này');
          return
        }
        if (danhBa.loaitb_id !== LoaiHinhTB.INTERNET_MYTV&&danhBa.loaitb_id !== LoaiHinhTB.INTERNET_MYTV_B2B) {
          this.$root.$toast.warning('Chức năng này chỉ thực hiện đối với thuê bao MyTV!')
          return
        }
        this.loaitb_id = danhBa.loaitb_id
        this.form_thongTinThueBao.selectLoaiHinhTB = this.loaitb_id
        if (danhBa.vpm == '1') {
          this.form_thongTinThueBao.selectKieuLD = KieuLapDat.CHUYEN_VMP_SANG_MYTV
        } else {
          this.form_thongTinThueBao.selectKieuLD = KieuLapDat.CHUYEN_MYTV_THUONG_SANG_VMP
        }
        // Thông tin khách hàng
        this.form_thongTinPhieuYeuCau.txtMaKH = danhBa.ma_kh
        this.form_thongTinPhieuYeuCau.txtMaHD = danhBa.ma_hd
        this.khachhang_id = danhBa.khachhang_id
        this.form_thongTinPhieuYeuCau.txtTenKH = this.ChuanHoaTen(danhBa.ten_kh)
        this.form_thongTinPhieuYeuCau.txtDiaChiKH = this.ChuanHoaTen(danhBa.diachi_kh)
        this.form_thongTinPhieuYeuCau.dtpNgayLapHD = moment(danhBa.ngaylap_hd, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
        if (danhBa.hasOwnProperty('ma_duan')) {
          this.form_thongTinThueBao.txtMaDuAn = danhBa.ma_duan
        }
        // Thông tin thuê bao
        this.form_thongTinThueBao.txtGhiChu = danhBa.ghichu
        this.thuebao_id = danhBa.thuebao_id
        this.form_thongTinThueBao.txtTenTB = this.ChuanHoaTen(danhBa.ten_tb)
        this.diachi_tb = this.ChuanHoaTen(danhBa.diachi_tb)
        this.diachi_ld = this.ChuanHoaTen(danhBa.diachi_ld)
        this.form_thongTinThueBao.txtDiaChiLD = this.diachi_ld
        this.loaitb_id = danhBa.loaitb_id
        this.tocdo_id = danhBa.tocdo_id ? danhBa.tocdo_id : 0
        this.muccuoc_id = danhBa.muccuoc_id ? danhBa.muccuoc_id : 0
        this.doituong_id = danhBa.doituong_id
        this.donvi_id = danhBa.donviql_id
        let dsTrangBi = await this.LAY_TT_TRANGGBI(this.thuebao_id, this.hdtb_id)
        if (dsTrangBi !== null) {
          // ret la tuong ung voi tt_tbi_cu ret_hd tuong ung voi tt_tbi_moi
          if (dsTrangBi.ret.length > 0 && dsTrangBi.ret_hd.length > 0) {
            this.form_thongTinThueBao.txtTrangBiCu = dsTrangBi.ret[0].tentrangbi
            this.form_thongTinThueBao.txtMucCuocTBCu = dsTrangBi.ret[0].muccuoc
            this.form_thongTinThueBao.txtLoaiTbiCu = dsTrangBi.ret[0].ten_tbi
            if (dsTrangBi.ret[0].trangbi_id !== "") {
              this.form_thongTinThueBao.selectTrangBi = Number.parseInt(dsTrangBi.ret[0].trangbi_id)
            } else if (dsTrangBi.ret[0].thietbidc_id) {
              this.form_thongTinThueBao.selectTrangBi = Number.parseInt(dsTrangBi.ret[0].thietbidc_id)
            }
          }
        } else {
          this.form_thongTinThueBao.txtTrangBiCu = ''
          this.form_thongTinThueBao.txtMucCuocTBCu = ''
          this.form_thongTinThueBao.txtLoaiTbiCu = ''
        }
        let dsMucCuoc = await this.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0,
          this.form_thongTinThueBao.selectLoaiHinhTB, this.doituong_id, this.tocdo_id,
          this.muccuoc_id, '', Number.parseInt(this.form_thongTinThueBao.selectTrangBi))
        if (dsMucCuoc.length > 0) {
          this.options.options_dsMucCuocTB = dsMucCuoc
        }
      } else {
        this.$root.$toast.warning('"Không tìm thấy thông tin về mã thuê bao ' + this.form_thongTinThueBao.txtMaTB)
      }
    },
    async HienThiTTDiaChi(dsHd, kieu) {
    },
    HienThiTienLapDat(ds) {
      let ktlm = true
      if (ds.length > 0 && ds[0][Object.keys(ds[0])[0]] != '-1') {
        ds.forEach(item => {
          if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_DOITS) {
            this.form_thongTinThueBao.txtTienHD = item.tien
            this.form_thongTinThueBao.txtVatHD = item.vat
            ktlm = false
          }
        })
        if (ktlm) {
          this.form_thongTinThueBao.txtTienHD = '0'
          this.form_thongTinThueBao.txtVatHD = '0'
        }
      } else {
        this.form_thongTinThueBao.txtTienHD = '0'
        this.form_thongTinThueBao.txtVatHD = '0'
        // this.$root.$toast.warning('Thông tin giá chưa được cập nhật')
      }
    },
    async HienThiTTHopDongKH(ds) {
      try {
        if (ds.length > 0) {
          const item = ds[0]
          this.hdkh_id = item.hdkh_id
          this.form_thongTinPhieuYeuCau.txtMaGD = item.ma_gd
          this.form_thongTinPhieuYeuCau.txtMaHD = item.ma_hd
          this.form_thongTinPhieuYeuCau.txtMaKH = item.ma_kh
          if (item.khachhang_id !== null) {
            this.khachhang_id = item.khachhang_id
          }
          this.form_thongTinPhieuYeuCau.txtTenKH = this.ChuanHoaTen(item.ten_kh)
          this.form_thongTinPhieuYeuCau.txtDiaChiKH = this.ChuanHoaTen(item.diachi_kh)
          this.form_thongTinPhieuYeuCau.dtpNgayLapHD = moment(item.ngaylap_hd, "YYYY-MM-DD HH:mm:ss").format('DD/MM/YYYY')
          this.form_thongTinPhieuYeuCau.dtpNgayYeuCau = moment(item.ngay_yc,"YYYY-MM-DD HH:mm:ss").format('DD/MM/YYYY')
          if (item.hasOwnProperty('ma_duan')) {
            this.form_thongTinThueBao.txtMaDuAn = item.ma_duan
          }
          if (item.PHIEUCSKH_ID !== null) { 
            this.VPHIEUCSKH_ID = item.PHIEUCSKH_ID
          }
          let dset = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(item.hdkh_id)
          await this.HienThiTTHopDongTB(dset)
          await this.HienThiDanhSacHDTB(dset)
          this.setActiveActions(3)
        } else {
          this.setActiveActions(0)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi hiển thị thông tin hợp đồng khách hàng')
      }
    },
    async HienThiTTHopDongTB(ds) {
      try {
        if (ds.length > 0) {
          const item = ds[0]
          this.hdtb_id = item.hdtb_id
          if (item.thuebao_id !== null) {
            this.thuebao_id = item.thuebao_id
          }
          this.form_thongTinThueBao.txtMaTB = item.ma_tb
          this.form_thongTinThueBao.txtTenTB = this.ChuanHoaTen(item.ten_tb)
          this.form_thongTinThueBao.txtGhiChu = item.ghichu
          this.diachi_tb = this.ChuanHoaTen(item.diachi_tb)
          this.diachi_ld = this.ChuanHoaTen(item.diachi_ld)
          this.form_thongTinThueBao.txtDiaChiLD = this.diachi_ld
          let dsCT = await this.sp_tt_loaihinh_tb(item.loaitb_id, 2)
          this.dichvuvt_id = dsCT[0].dichvuvt_id
          this.form_thongTinThueBao.selectDichVuVT = this.dichvuvt_id
          this.loaitb_id = item.loaitb_id
          this.form_thongTinThueBao.selectKieuLD = item.kieuld_id
          let dset = await this.LAY_DS_HOPDONG_THUEBAO_ADSL(this.hdtb_id)
          if (dset.length > 0) {
            this.tocdo_id = dset[0].tocdo_id
            this.muccuoc_id = dset[0].muccuoc_id
          }
          this.doituong_id = item.doituong_id
          this.donvi_id = item.donvi_id
          if (this.form_thongTinThueBao.selectKieuLD == KieuLapDat.TD_TBI_MYTV) {
            let dsMucCuocTb = await this.LAY_DS_MUCUOC_TB_V2(0, 0,
              0, 0, this.form_thongTinThueBao.selectLoaiHinhTB, this.doituong_id,
              this.tocdo_id, this.muccuoc_id, '', this.form_thongTinThueBao.selectTrangBi)
            if (dsMucCuocTb.length > 0) {
              this.options.options_dsMucCuocTB = dsMucCuocTb
            }
            let dsTrangBi = await this.LAY_TT_TRANGGBI(this.thuebao_id, this.hdtb_id)
            if (dsTrangBi !== null) {
              if (dsTrangBi.ret.length > 0 && dsTrangBi.ret_hd.length > 0) {
                // ret la tuong ung voi tt_tbi_cu ret_hd tuong ung voi tt_tbi_moi
                this.form_thongTinThueBao.txtTrangBiCu = dsTrangBi.ret[0].tentrangbi
                this.form_thongTinThueBao.txtMucCuocTBCu = dsTrangBi.ret[0].muccuoc
                this.form_thongTinThueBao.selectTrangBi = Number.parseInt(dsTrangBi.ret_hd[0].trangbi_id)
                this.vtrangbi_id = Number.parseInt(dsTrangBi.ret_hd[0].trangbi_id)
                this.vtrangbi_id_cu = Number.parseInt(dsTrangBi.ret[0].trangbi_id)
                this.form_thongTinThueBao.selectMucCuocTB = Number.parseInt(dsTrangBi.ret_hd[0].mucuoctb_id)
                this.form_thongTinThueBao.txtLoaiTbiCu = dsTrangBi.ret[0].ten_tbi
                this.form_thongTinThueBao.selectLoaiTB = Number.parseInt(dsTrangBi.ret_hd[0].thietbidc_id)
              }
            }
          } else {
            this.form_thongTinThueBao.txtTrangBiCu = ''
            this.form_thongTinThueBao.txtMucCuocTBCu = ''
            this.form_thongTinThueBao.txtLoaiTbiCu = ''
            this.form_thongTinThueBao.selectLoaiTB = '0'
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi hiển thị thông tin hợp đồng thuê bao')
      }
    },
    async HienThiDanhSacHDTB(dset) {
      this.dtList = dset
      this.actions[this.actions.findIndex(x => x.id == 'xoa')].active = this.dtList.length !== 1
      let tongTienHd = 0
      let tongTienTb = 0
      this.dtList.forEach(dataItem => {
        tongTienTb = Number.parseInt(dataItem.tongtien)
        tongTienHd = tongTienHd + tongTienTb
      })
      this.form_thongTinThueBao.txtTongTienHD = tongTienHd
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(matb, magd, dichvuvtid, loaihdid) {
      let result = false
      await ChangeMyTVParameterAPI.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.axios, {
        "loaihd_id": loaihdid,
        "ma_gd": magd,
        "dichvuvt_id": dichvuvtid,
        "ma_tb": matb
      }).then(response => {
        if (response && response.data && response.data.error_code == '0' && response.data.data) {
          if (response.data.data.length > 0) {
            result = true
            let tenLoaiHd = response.data.data[0].ten_loaihd
            let maGd = response.data.data[0].ma_gd
            let nguoiCn = response.data.data[0].nguoi_cn
            let tenDV = response.data.data[0].ten_dv
            let ngayYc = response.data.data[0].ngay_yc
            this.$root.$toast.warning(`Số máy/Acc: ${matb} đang được lập bởi hợp đồng (phụ lục) ${tenLoaiHd} có mã giao dịch ${maGd} do User ${nguoiCn} thuộc đơn vị ${tenDV} thực hiện vào ngày ${ngayYc}`)
          }
        }
      })
      return result
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_V2(matb, magd, dichvuvtid, loaihdid, kieuldId, loaitbId) {
      let result = false
      await ChangeMyTVParameterAPI.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_V2(this.axios, {
        "loaihd_id": loaihdid,
        "ma_gd": magd,
        "dichvuvt_id": dichvuvtid,
        "ma_tb": matb,
        "kieuld_id": kieuldId,
        "loaitb_id": loaitbId
      }).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          if (response.data.data.length > 0) {
            result = true
            let tenLoaiHd = response.data.data[0].ten_loaihd
            let maGd = response.data.data[0].ma_gd
            let nguoiCn = response.data.data[0].nguoi_cn
            let tenDV = response.data.data[0].ten_dv
            let ngayYc = response.data.data[0].ngay_yc
            this.$root.$toast.info(`Số máy/Acc: ${matb} đang được lập bởi hợp đồng (phụ lục) ${tenLoaiHd} có mã giao dịch ${maGd} do User ${nguoiCn} thuộc đơn vị ${tenDV} thực hiện vào ngày ${ngayYc}`)
          }
        }
      })
      return result
    },
    async LAY_DANHBA_THEO_MATB(maTB, dichvuVTId, donviId) {
      let danhBa = null
      await ChangeMyTVParameterAPI.LAY_DANHBA_THEO_MATB(this.axios, {
        'maTb': maTB,
        "dichVuVtId": dichvuVTId,
        "donViDlId": donviId
      }).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          danhBa = response.data.data
        }
      })
      return danhBa
    },
    async LAY_DS_HOPDONG_THEO_MA_GD(maGd, loaiHdId, trangThaiHdId, donViDlId) {
      let ds = []
      let request = {
        'ma_gd': maGd,
        'loaihd_id': loaiHdId,
        'tthd_id': trangThaiHdId,
        'donvi_dl_id': donViDlId
      }
      await ChangeMyTVParameterAPI.LAY_DS_HOPDONG_THEO_MA_GD(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LAY_TT_TRANGGBI(thuebaoId, hdtbId) {
      let ds = null
      await ChangeMyTVParameterAPI.LAY_TT_TRANGGBI(this.axios, {
        'thuebao_id': thuebaoId,
        'hdtb_id': hdtbId
      }).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LAY_DS_MUCUOC_TB_V2(khuldId, apldId, pholdId, phuongldId, loaitbId, doituongId, tocdoId, muccuocId, ngay, trangbiId) {
      let ds = []
      let request = {
        'apld_id': apldId,
        'doituong_id': doituongId,
        'khuld_id': khuldId,
        'loaitb_id': loaitbId,
        'muccuoc_id': muccuocId,
        'ngay': ngay,
        'phold_id': pholdId,
        'phuongld_id': phuongldId,
        'tocdo_id': tocdoId,
        'trangbi_id': trangbiId
      }
      await ChangeMyTVParameterAPI.LAY_DS_MUCUOC_TB_V2(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LAY_DS_HOPDONG_THUEBAO_ADSL(hdtbId) {
      let ds = []
      await ChangeMyTVParameterAPI.LAY_DS_HOPDONG_THUEBAO_ADSL(this.axios, hdtbId).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdkhId) {
      let ds = []
      await ChangeMyTVParameterAPI.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.axios, hdkhId).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LAY_DS_HOPDONG_THEO_MA_TB(maTb, loaihdId, donviId, tthdId, nhanvienId, donvidlid, dichvuvtid, loaitbid) {
      let ds = []
      let request = {
        'ma_tb': maTb,
        'in_loaihd_id': loaihdId,
        'in_donvi_id': donviId,
        'in_tthd_id': tthdId,
        'in_nhanvien_id': nhanvienId,
        'in_donvi_dl_id': donvidlid,
        'in_dichvuvt_id': dichvuvtid,
        'in_loaitb_id': loaitbid
      }
      await ChangeMyTVParameterAPI.LAY_DS_HOPDONG_THEO_MA_TB(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LayTienTheoKhoanMuc() {
      try {
        this.loading(true)
        let ds = await this.LAY_TIEN_KHOANMUC_LD()
        if (ds.length > 0) {
          this.HienThiTienLapDat(ds)
        } else {
          this.form_thongTinThueBao.txtTenTB = ''
          this.form_thongTinThueBao.txtVatHD = ''
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi xảy ra khi lấy tiền theo khoản mục')
      } finally {
        this.loading(false)
      }
    },
    async SP_LAY_DS_LOAI_TB_LHTB(loaitbId) {
      let ds = []
      try {
        this.loading(true)
        await ChangeMyTVParameterAPI.SP_LAY_DS_LOAI_TB_LHTB(this.axios, {
          loaitb_id: loaitbId
        }).then(response => {
          if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
            ds = response.data.data
          }
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi lấy danh sách loại thiết bị theo loại hình thuê bao')
      } finally {
        this.loading(false)
      }
      return ds
    },
    async LAY_TIEN_KHOANMUC_LD() {
      let danhsach = []
      const request = {
        'phanvung_id': this.$root.token.getPhanVungID(),
        'loaihd_id': LoaiHopDong.THAYDOI_THONGSO_MYTV,
        'ngay_yc': this.form_thongTinPhieuYeuCau.dtpNgayYeuCau,
        'dichvuvt_id': this.form_thongTinThueBao.selectDichVuVT,
        'loaitb_id': this.form_thongTinThueBao.selectLoaiHinhTB,
        'kieuld_id': this.form_thongTinThueBao.selectKieuLD,
        'phuongld_id': this.phuongld_id,
        'tocdo_id': 0,
        'muccuoc_id': 1,
        'ltb_adsl_id': this.ltb_adsl_id,
        'ltb_adsl_moi_id': 0,
        'ltb_adsl_cu_id': 0,
        'tocdo_moi_id': 0,
        'tocdo_cu_id': 0,
        'kh_cd': 0,
        'phold_id': this.phold_id,
        'apld_id': this.apld_id,
        'khuld_id': this.khuld_id,
        'doituong_id': this.doituong_id,
        'trangbi_id': this.trangbi_id
      }
      await ChangeMyTVParameterAPI.LAY_TIEN_KHOANMUC_LD(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
          danhsach = response.data.data
        }
      })
      return danhsach
    },
    async LAY_DS_KIEU_LD(loaihdId, loaitbId) {
      let ds = []
      await ChangeMyTVParameterAPI.LAY_DS_KIEU_LD(this.axios, {
        'vloaihd_id': loaihdId,
        'vloaitb_id': loaitbId
      }).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async CSS_DICHVU_VT() {
      let ds = []
      try {
        this.loading(true)
        await ChangeMyTVParameterAPI.CSS_DICHVU_VT(this.axios).then(response => {
          if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
            ds = response.data.data
          }
        })
      } catch (error) {
        this.$root.$toast.error('Không load được dịch vụ viễn thông')
      } finally {
        this.loading(false)
      }
      return ds
    },
    async CSS_KIEU_LD() {
      let ds = []
      try {
        this.loading(true)
        await ChangeMyTVParameterAPI.CSS_KIEU_LD(this.axios).then(response => {
          if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
            ds = response.data.data
          }
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Không load được danh sách kiểu lắp đặt')
      } finally {
        this.loading(false)
      }
      return ds
    },
    async CSS_TRANGBI() {
      let ds = []
      try {
        this.loading(true)
        await ChangeMyTVParameterAPI.CSS_TRANGBI(this.axios).then(response => {
          if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
            ds = response.data.data
          }
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Không load được danh sách trang bị')
      } finally {
        this.loading(false)
      }
      return ds
    },
    async CSS_LOAIHINH_TB() {
      let ds = []
      try {
        this.loading(true)
        await ChangeMyTVParameterAPI.CSS_LOAIHINH_TB(this.axios).then(response => {
          if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
            ds = response.data.data
          }
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Không load được danh sách loại hình tb')
      } finally {
        this.loading(false)
      }
      return ds
    },
    async SP_CAPNHAT_HD_THAYDOI_TS_MYTV(kieu, dsParams, hdKhachHangData, hdThueBaoData, hdtbDVData, hdtbAdsl, ctTienHd) {
      let result = false
      let request = {
        'kieu': kieu,
        'ds_param': dsParams,
        'hd_khachhang_data': hdKhachHangData,
        'hd_thuebao_data': hdThueBaoData,
        'hdtb_dv_data': hdtbDVData,
        'hdtb_adsl': hdtbAdsl,
        'ct_tienhd': ctTienHd
      }
      await ChangeMyTVParameterAPI.SP_CAPNHAT_HD_THAYDOI_TS_MYTV(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          const resp = JSON.parse(response.data.data)
          if (resp.KETQUA == 0) {
            this.$root.$toast.warning(resp.THONGBAO)
          } else {
            result = true
          }
        } else {
          console.log(response)
          this.$root.$toast.error('Có lỗi xảy ra khi cập nhật hợp đồng thay đổi thông số mytv kieu ' + kieu + response.data.message_detail)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.data.message)
      })
      return result
    },
    async LAY_DS_THAMSO_MD() {
      let ds = []
      try {
        this.loading(true)
        await ChangeMyTVParameterAPI.LAY_DS_THAMSO_MD(this.axios).then(response => {
          if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data.length > 0) {
            ds = response.data.data
          }
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Không load được danh sách tham số mặc định')
      } finally {
        this.loading(false)
      }
      return ds
    },
    select_selectedItemsChanged(dataKeys) {
      if (dataKeys.length > 0) {
        this.selectThueBao = dataKeys[0]
        this.hdtb_id = this.selectThueBao.hdtb_id
        this.HienThiCTTTHopDongTB(this.selectThueBao)
        this.actions[this.actions.findIndex(x => x.id == 'vattu')].active = true
        if (this.selectThueBao.status != '5') {
          this.actions[this.actions.findIndex(x => x.id == 'kichhoat')].active = !(this.ts_td_trangbi_mytv && this.form_thongTinThueBao.selectKieuLD == KieuLapDat.TD_TBI_MYTV)
          this.actions[this.actions.findIndex(x => x.id == 'hoanthien')].active = false
          this.actions[this.actions.findIndex(x => x.id == 'thanhtoan')].active = true
        } else {
          this.actions[this.actions.findIndex(x => x.id == 'kichhoat')].active = false
          this.actions[this.actions.findIndex(x => x.id == 'hoanthien')].active = true
          this.actions[this.actions.findIndex(x => x.id == 'thanhtoan')].active = false
          this.actions[this.actions.findIndex(x => x.id == 'xoa')].active = false
        }
      }
    },
    selectRowsChon() {
    },
    async HienThiCTTTHopDongTB(item) {
      if (item !== null) {
        this.hdtb_id = item.hdtb_id
        if (item.thuebao_id !== null) {
          this.thuebao_id = item.thuebao_id
        }
        this.form_thongTinThueBao.txtMaTB = item.ma_tb
        this.form_thongTinThueBao.txtTenTB = this.ChuanHoaTen(item.ten_tb)
        this.form_thongTinThueBao.txtGhiChu = item.ghichu
        this.diachi_tb = this.ChuanHoaTen(item.diachi_tb)
        this.diachi_ld = this.ChuanHoaTen(item.diachi_ld)
        this.form_thongTinThueBao.txtDiaChiLD = this.ChuanHoaTen(item.diachi_ld)
        let dsCT = await this.sp_tt_loaihinh_tb(item.loaitb_id,2)
        this.dichvuvt_id = dsCT[0].dichvuvt_id
        this.form_thongTinThueBao.selectDichVuVT = this.dichvuvt_id
        this.loaitb_id = item.loaitb_id
        this.form_thongTinThueBao.selectKieuLD = item.kieuld_id
        let dsHd = await this.LAY_DS_DB_ADSL_THEO_TBID(this.hdtb_id)
        if (dsHd.length > 0) {
          this.tocdo_id = dsHd[0].tocdo_id
          this.muccuoc_id = dsHd[0].muccuoc_id
        }
        this.doituong_id = item.doituong_id
        this.donvi_id = item.donvi_id
        let dsMucCuoc = await this.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, this.form_thongTinThueBao.selectLoaiHinhTB, this.doituong_id, this.tocdo_id, this.muccuoc_id, '', Number.parseInt(this.form_thongTinThueBao.selectTrangBi))
        if (dsMucCuoc.length > 0) {
          this.options.options_dsMucCuocTB = dsMucCuoc
        }
        let dsTTTB = await this.LAY_TT_TRANGGBI(this.thuebao_id, this.hdtb_id)
        if (dsTTTB !== null) {
          if (dsTTTB.ret.length > 0 && dsTTTB.ret_hd.length > 0) {
            this.form_thongTinThueBao.txtTrangBiCu = dsTTTB.ret[0].tentrangbi
            this.form_thongTinThueBao.txtMucCuocTBCu = dsTTTB.ret[0].muccuoc
            this.form_thongTinThueBao.selectTrangBi = Number.parseInt(dsTTTB.ret_hd[0].trangbi_id)
            this.vtrangbi_id = Number.parseInt(dsTTTB.ret_hd[0].trangbi_id)
            this.vtrangbi_id_cu = Number.parseInt(dsTTTB.ret[0].trangbi_id)
            this.form_thongTinThueBao.selectMucCuocTB = Number.parseInt(dsTTTB.ret_hd[0].mucuoctb_id)
          }
        }
      }
    },
    async KichHoat() {
      try {
        if (this.selectThueBao == null) {
          this.$root.$toast.warning('Chưa chọn thuê bao')
          return
        }
        let vMaTB = this.selectThueBao.ma_tb
        let vmp = await this.FN_TT_HDTB_ADSL(this.hdtb_id)
        let title = ''
        if (vmp == '1') {
          title = 'Bạn thật sự kích hoạt Mytv -> VMP không ?'
        } else {
          title = 'Bạn thật sự kích hoạt VMP -> MyTV không ?'
        }
        this.$confirm(title, 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          let result = await this.KICHHOAT_TONGDAI(vMaTB, this.form_thongTinThueBao.selectKieuLD, this.hdtb_id, Number(vmp))
          if (result.success) {
            this.$root.$toast.success(result.message)
          } else {
            this.$root.$toast.error(result.message)
          }
          let dsHd = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
          await this.HienThiDanhSacHDTB(dsHd)
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi kích hoạt')
      }
    },
    async FN_TT_HDTB_ADSL (hdtbid) {
      let req = { param: hdtbid, type: 1 }
      return this.GetData(await ChangeMyTVParameterAPI.FN_TT_HDTB_ADSL(this.axios, req))
    },
    async KICHHOAT_TONGDAI(matb, kieuLdId, hdtbId, vmp) {
      let result = {}
      await ChangeMyTVParameterAPI.KICHHOAT_TONGDAI(this.axios, {
        ma_tb: matb,
        cb_kieuld: kieuLdId,
        hdtb_id: hdtbId,
        vmp: vmp
      }).then(response => {
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          result.success = true
          result.message = response.data.message
        } else {
          result.success = false
          result.message = response.data.message
        }
      })
      return result
    },
    async saveData() {
      try {
        this.loading(true)
        let checkDLKH = await this.KiemTraDL_KhachHang()
        if (!checkDLKH) {
          return
        }
        let checkDLTB = await this.KiemTraDL_ThueBao()
        if (!checkDLTB) {
          return
        }
        let kieu = this.actions[this.actions.findIndex(x => x.id == 'nhapmoi')].active == false ? 1 : 2
        if (kieu == 1) {
          await this.TaoDuLieu(true)
        } else {
          await this.TaoDuLieu(false)
        }
        let kqTm = await this.SP_CAPNHAT_HD_THAYDOI_TS_MYTV(kieu,
          JSON.stringify(this.dsParam[0]),
          JSON.stringify(this.dsHDKH[0]),
          JSON.stringify(this.dsHDTB[0]),
          JSON.stringify(this.dsHDTBDV),
          JSON.stringify(this.dsHDTBADSL[0]),
          JSON.stringify(this.dsCTTHD[0])
        )
        if (kqTm) {
          let dsHDTBByKHID = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
          await this.HienThiDanhSacHDTB(dsHDTBByKHID)
          this.setActiveActions(3)
          this.$root.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
          if (this.VPHIEUCSKHDV_ID > 0) {
            await this.B2A_HT_PHIEUCSKHDV(this.VPHIEUCSKH_ID, 1)
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi thực hiện ghi lại hồ sơ!')
      } finally {
        this.loading(false)
      }
    },
    async KiemTraDL_KhachHang() {
      if (this.form_thongTinPhieuYeuCau.txtTenKH == '') {
        this.$root.$toast.warning('Vui lòng nhập tên khách hàng !')
        this.$refs.tenkhachhang.focus()
        return false
      }
      if (this.form_thongTinPhieuYeuCau.txtMaKH == '' || this.khachhang_id == 0) {
        this.$root.$toast.warning('Kiểm tra lại mã khách hàng !')
        this.$refs.makhachhang.focus()
        return false
      }
      // Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
      if (this.form_thongTinPhieuYeuCau.txtMaKH) {
        let check = await this.KIEMTRA_MAKH_MTT(this.form_thongTinPhieuYeuCau.txtMaKH.trim(), 1, LoaiHopDong.THAYDOI_THONGSO_MYTV)
        if (check == false) {
          this.$root.$toast.warning('Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh. Bạn hãy kiểm tra lại!')
          this.$refs.makhachhang.focus()
          return false
        }
      }
      if (this.form_thongTinPhieuYeuCau.txtMaGD) {
        let check = await this.KIEMTRA_MAKH_MTT(this.form_thongTinPhieuYeuCau.txtMaGD.trim(), 3, LoaiHopDong.THAYDOI_THONGSO_MYTV)
        if (check == false) {
          this.$root.$toast.warning('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh. Bạn hãy kiểm tra lại!')
          this.$refs.magiaodich.focus()
          return false
        }
      }
      return true
    },
    async KiemTraDL_ThueBao() {
      if (this.form_thongTinPhieuYeuCau.txtMaHD == '' && this.khachhang_id == 0) {
        this.$root.$toast.warning('Vui lòng nhập mã hợp đồng mới !')
        this.$refs.mahopdong.focus()
        return false
      }
      if (!this.form_thongTinThueBao.txtTenTB) {
        this.$root.$toast.warning('Vui lòng nhập tên thuê bao !')
        this.$refs.tenthuebao.focus()
        return false
      }
      let vmp = await this.FN_TT_DB_ADSL(this.thuebao_id)
      if (vmp == '0') {
        if (this.form_thongTinThueBao.selectKieuLD == KieuLapDat.CHUYEN_VMP_SANG_MYTV) {
          this.$root.$toast.warning('Thuê bao đang là Mytv thường. Bạn chọn sai kiểu lắp đặt !')
          return false
        }
      }
      if (vmp == '1') {
        if (this.form_thongTinThueBao.selectKieuLD == KieuLapDat.CHUYEN_MYTV_THUONG_SANG_VMP) {
          this.$root.$toast.warning('Thuê bao đang là VMP. Bạn chọn sai kiểu lắp đặt !')
          return false
        }
      }
      if (this.form_thongTinThueBao.selectKieuLD == KieuLapDat.TD_TBI_MYTV) {
        if (this.form_thongTinThueBao.selectLoaiTB == '0') {
          this.$root.$toast.warning('Vui lòng chọn loại thiết bị mới')
          return false
        }
        if (!this.form_thongTinThueBao.selectMucCuocTB) {
          this.$root.$toast.warning('Vui lòng chọn mức cước thuê bao mới')
          return false
        }
      }
      return true
    },
    async FN_TT_DB_ADSL (thuebaoid) {
      return this.GetData(await ChangeMyTVParameterAPI.FN_TT_DB_ADSL(this.axios, { param: thuebaoid, type: 1 }))
    },
    async KIEMTRA_MAKH_MTT(ma, kieu, loaihdId) {
      let result = false
      await ChangeMyTVParameterAPI.KIEMTRA_MAKH_MTT(this.axios, {
        "kieu": kieu,
        "loaihd_id": loaihdId,
        "ma_kh": ma
      }).then(response => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          result = response.data.data == 1
        }
      })
      return result
    },
    async TaoDuLieu(isNew) {
      await this.TaoDuLieu_DS_Param()
      await this.TaoDuLieu_HDKH(isNew)
      await this.TaoDuLieu_HDTB(isNew)
      await this.TaoDuLieu_HDTBDV()
      await this.TaoDuLieu_HDTB_ADSL(isNew)
      this.TaoDuLieu_THONGTIN_TT()
      await this.TaoDuLieu_CT_TIENHD(true)
    },
    TaoDuLieu_DS_Param() {
      this.dsParam = []
      let row = {}
      row.HDKH_ID = this.hdkh_id
      row.MA_KH = this.form_thongTinPhieuYeuCau.txtMaKH.trim()
      row.MA_GD = this.form_thongTinPhieuYeuCau.txtMaGD.trim()
      row.MA_TB = this.ChuanHoaTen(this.form_thongTinThueBao.txtMaTB.trim())
      row.KHACHHANG_ID = this.khachhang_id
      row.THUEBAO_ID = this.thuebao_id
      row.DICHVUVT_ID = this.form_thongTinThueBao.selectDichVuVT
      row.KIEULD_ID = Number.parseInt(this.form_thongTinThueBao.selectKieuLD)
      row.TOCDO_ID = this.tocdo_id
      row.MUCCUOC_ID = this.muccuoc_id
      row.LOAITB_ID = Number.parseInt(this.form_thongTinThueBao.selectLoaiHinhTB)
      row.HDTB_ID = this.hdtb_id
      row.NHANVIEN_ID = Number.parseInt(this.$root.token.getNhanVienID())
      row.NGUOI_CN = this.$root.token.getUserName()
      row.MAY_CN = this.maycn
      row.IP_CN = this.ipcn
      this.dsParam.push(row)
    },
    async TaoDuLieu_CT_TIENHD(isNew) {
      // Inset,update 4 row tiền vào database
      // 1: Tiền khoản mục (theo loại hợp đồng: đặt mới, chuyển quyền, chuyển dịch)
      // 2: Tiền cài đặt (Áp dụng cho dịch vụ INTERNET,
      this.dsCTTHD = []
      for (let i = 0; i < 1; i++) {
        const rowCTTHD = {}
        if (isNew == true) {
          this.CT_TIENHD_ID = await this.POST_GET_KEYS('CT_TIENHD')
          rowCTTHD.CT_TIENHD_ID = this.CT_TIENHD_ID
        }
        rowCTTHD.HDTB_ID = this.hdtb_id
        // tiền lắp mới =1 + khuyến mại lắp mới
        if (i == 0) {
          let tien = '' // Tiền
          let vat = '' // Vat
          rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DOITS
          tien = this.form_thongTinThueBao.txtTienHD
          rowCTTHD.TIEN = Number.parseInt(tien)
          vat = this.form_thongTinThueBao.txtVatHD
          rowCTTHD.VAT = Number.parseInt(vat)
          rowCTTHD.LOAI_ID = 1
          rowCTTHD.ID = KHOANMUC_TT.KMTT_DATMOI
          this.dsCTTHD.push(rowCTTHD)
        }
      }
    },
    TaoDuLieu_THONGTIN_TT() {
    },
    async TaoDuLieu_HDTBDV() {
      this.dsHDTBDV = []
      let dsDBTB = await this.sp_tt_dbtb_dv( this.thuebao_id)
      if (dsDBTB.length > 0) {
        this.dsHDTBDV = dsDBTB.map(item => {
          return {
            HDTB_ID: this.hdtb_id,
            LOAIDV_ID: item.loaidv_id,
            DONVI_ID: item.donvi_id,
            KIEUDV_ID : 2
          }
        })
      }
    },
    async TaoDuLieu_HDTB_ADSL(isNew) {
      this.dsHDTBADSL = []
      let dsAdsl = await this.LAY_DS_DB_ADSL_THEO_TBID(this.thuebao_id)
      if (dsAdsl.length > 0) {
        let item = dsAdsl[0]
        for (const key in item) {
          if (item.hasOwnProperty(key)) {
            item[key.toUpperCase()] = item[key]
            delete item[key]
          }
        }
        const rowHDTBADSL = Object.assign({}, item)
        rowHDTBADSL.HDTB_ID = this.hdtb_id
        if (this.form_thongTinThueBao.selectKieuLD == KieuLapDat.CHUYEN_MYTV_THUONG_SANG_VMP) {
          rowHDTBADSL.VMP = 1
        } else if (this.form_thongTinThueBao.selectKieuLD == KieuLapDat.CHUYEN_VMP_SANG_MYTV) {
          rowHDTBADSL.VMP = 0
        }
        if (this.form_thongTinThueBao.selectKieuLD == KieuLapDat.TD_TBI_MYTV) {
          rowHDTBADSL.TRANGBI_ID = Number.parseInt(this.form_thongTinThueBao.selectTrangBi)
          rowHDTBADSL.THIETBIDC_ID = Number.parseInt(this.form_thongTinThueBao.selectLoaiTB)
        }
        this.dsHDTBADSL.push(rowHDTBADSL)
      }
    },
    async TaoDuLieu_HDTB(isNew) {
      this.dsHDTB = []
      const rowHDTB = {}
      if (isNew) {
        this.hdtb_id = await this.POST_GET_KEYS('HD_THUEBAO')
      }
      rowHDTB.HDTB_ID = this.hdtb_id
      rowHDTB.HDKH_ID = this.hdkh_id
      if (this.thuebao_id > 0) {
        rowHDTB.THUEBAO_ID = this.thuebao_id
      }
      rowHDTB.MA_TB = this.ChuanHoaTen(this.form_thongTinThueBao.txtMaTB.trim())
      rowHDTB.GHICHU = this.form_thongTinThueBao.txtGhiChu ? this.form_thongTinThueBao.txtGhiChu.trim() : ''
      rowHDTB.TEN_TB = this.ChuanHoaTen(this.form_thongTinThueBao.txtTenTB.trim())
      rowHDTB.DIACHI_TB = this.ChuanHoaTen(this.diachi_tb)
      rowHDTB.DIACHI_LD = this.ChuanHoaTen(this.diachi_ld)
      rowHDTB.KIEULD_ID = Number.parseInt(this.form_thongTinThueBao.selectKieuLD)
      rowHDTB.DOITUONG_ID = this.doituong_id
      rowHDTB.TTHD_ID = TrangThaiHD.MOI
      rowHDTB.LOAITB_ID = this.loaitb_id
      rowHDTB.DICHVUVT_ID = this.form_thongTinThueBao.selectDichVuVT
      rowHDTB.DONVI_ID = this.donvi_id
      rowHDTB.NGUOI_CN = this.$root.token.getUserName()
      rowHDTB.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      rowHDTB.MAY_CN = this.ipcn
      if (this.form_thongTinThueBao.selectKieuLD == KieuLapDat.TD_TBI_MYTV) {
        rowHDTB.MUCUOCTB_ID = Number.parseInt(this.form_thongTinThueBao.selectMucCuocTB)
      }
      rowHDTB.QUYTRINH_ID = this.quytrinhtn_id
      rowHDTB.STATUS = '0'
      this.dsHDTB.push(rowHDTB)
    },
    async TaoDuLieu_HDKH(isNew) {
      this.dsHDKH = []
      const rowHDKH = {}
      let maGDTemp = null
      if (isNew) {
        this.hdkh_id = await this.POST_GET_KEYS('HD_KHACHHANG')
      }
      rowHDKH.HDKH_ID = this.hdkh_id
      if (isNew) {
        if (this.ts_sinhma_gd_theo_donvi) {
          maGDTemp = await this.SINH_MAGD_V2()
          this.form_thongTinPhieuYeuCau.txtMaGD = maGDTemp.ma
        } else {
          maGDTemp = await this.SINH_MAGD()
        }
        if (maGDTemp.success == false) {
          this.$root.$toast.warning('Có lỗi xảy ra: ' + maGDTemp.message)
          this.loi_sinh_magd = true
        }
        this.form_thongTinPhieuYeuCau.txtMaGD = maGDTemp.ma
        rowHDKH.MA_GD = this.form_thongTinPhieuYeuCau.txtMaGD.trim()
        rowHDKH.MA_HD = this.form_thongTinPhieuYeuCau.txtMaHD.trim()
      } else {
        rowHDKH.MA_GD = this.form_thongTinPhieuYeuCau.txtMaGD
        rowHDKH.MA_HD = this.form_thongTinPhieuYeuCau.txtMaHD.trim()
      }
      rowHDKH.NGAY_YC = this.form_thongTinPhieuYeuCau.dtpNgayYeuCau
      rowHDKH.MA_KH = this.form_thongTinPhieuYeuCau.txtMaKH.trim()
      if (this.khachhang_id > 0) {
        rowHDKH.KHACHHANG_ID = this.khachhang_id
      }
      if (this.hdkh_cha_id > 0) {
        rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
        this.hdkh_cha_id = 0
      }
      rowHDKH.TEN_KH = this.ChuanHoaTen(this.form_thongTinPhieuYeuCau.txtTenKH)
      rowHDKH.DIACHI_KH = this.ChuanHoaTen(this.form_thongTinPhieuYeuCau.txtDiaChiKH)
      rowHDKH.NGAYLAP_HD = this.form_thongTinPhieuYeuCau.dtpNgayLapHD
      rowHDKH.DONVI_ID = this.$root.token.getDonViID()
      rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID()
      rowHDKH.KHLON_ID = 1
      rowHDKH.LOAIHD_ID = LoaiHopDong.THAYDOI_THONGSO_MYTV
      rowHDKH.NGUOI_CN = this.$root.token.getUserName()
      rowHDKH.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      rowHDKH.MAY_CN = this.maycn
      rowHDKH.IP_CN = this.ipcn
      if (this.VPHIEUCSKH_ID !== 0) {
        rowHDKH.PHIEUCSKH_ID = this.VPHIEUCSKH_ID
      }
      // Bổ sung cho HCM theo y/c chị Tô Thảo - TP DHNV TTKD
      rowHDKH.MA_DUAN = this.form_thongTinThueBao.txtMaDuAn == null ? '' : this.form_thongTinThueBao.txtMaDuAn
      this.dsHDKH.push(rowHDKH)
    },
    async B2A_HT_PHIEUCSKHDV(phieucskhdv_id, kieu) {
      try {
        await ChangeMyTVParameterAPI.B2A_HT_PHIEUCSKHDV(this.axios, {
          "phieucskhdv_id": phieucskhdv_id,
          "kieu": kieu
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi cập nhật trạng thái ttphdv_id sau khi liên kết hd')
      }
    },
    async KIEMTRA_DB_KHI_THEMMOI(maTb, khachHangId, thueBaoId, dichVuVtId) {
      let result = 0
      await ChangeMyTVParameterAPI.KIEMTRA_DB_KHI_THEMMOI(this.axios, {
        maTb: maTb,
        khachHangId: khachHangId,
        thueBaoId: thueBaoId,
        dichVuVtId: dichVuVtId
      }).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          result = response.data.data
        }
      })
      return result == 1
    },
    async LAY_HUONGGIAO_TIEPNHAN(loaihdId, loaitbId, tocdoId, muccuocId, kieuldId) {
      const request = {
        'kieuLdId': kieuldId,
        'loaiHdId': loaihdId,
        'loaiTbId': loaitbId,
        'mucCuocId': muccuocId,
        'tocDoId': tocdoId
      }
      let out = null
      await ChangeMyTVParameterAPI.LAY_HUONGGIAO_TIEPNHAN(this.axios, request).then(response => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          out = response.data.data
        }
      })
      return out
    },
    async LAY_DS_DB_ADSL_THEO_TBID(thuebaoId) {
      let ds = []
      await ChangeMyTVParameterAPI.LAY_DS_DB_ADSL_THEO_TBID(this.axios, thuebaoId).then(response => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          ds = response.data.data
        }
      })
      return ds
    },
    async POST_GET_KEYS(keyname) {
      let key = ''
      await ChangeMyTVParameterAPI.POST_GET_KEYS(this.axios, {
        keyname: keyname
      }).then(response => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          key = response.data.data
        }
      })
      return key
    },
    async SINH_MAGD_V2() {
      let result = {}
      await ChangeMyTVParameterAPI.SINH_MAGD_V2(this.axios, {'loaihd_id': LoaiHopDong.THAYDOI_THONGSO_MYTV, 'donvi_id': this.$root.token.getDonViID()})
      .then(response => {
        if (response && response.data) {
          if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
            result.success = true
            result.ma = response.data.data
            result.message = response.data.message
          } else {
            result.success = false
            result.ma = response.data.data
            result.message = response.data.message
          }
        }
      })
      return result
    },
    async SINH_MAGD() {
      let result = {}
      await ChangeMyTVParameterAPI.SINH_MAGD(this.axios, {'loaihd_id': LoaiHopDong.THAYDOI_THONGSO_MYTV}).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          result.success = true
          result.ma = response.data.data
          result.message = 'Thành công'
        } else {
          result.success = false
          result.ma = ''
          result.message = 'Thất bại'
        }
      })
      return result
    },
    xoaDuLieu() {
      this.$confirm('Bạn thật sự muốn xóa phụ lục không ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      }).then(async () => {
        try {
          this.loading(true)
          const dsParams = {HDKH_ID: this.hdkh_id, HDTB_ID: this.hdtb_id}
          let result = await this.SP_CAPNHAT_HD_THAYDOI_TS_MYTV(3, JSON.stringify(dsParams), '', '', '', '', '')
          if (result) {
            this.$root.$toast.success('Xóa phụ lục thành công !')
            this.setActiveActions(0)
            this.dtList = []
          }
        } catch (error) {
          console.log(error)
          this.$root.$toast.error('Lỗi khi xóa hợp đồng: ' + error.message)
        } finally {
          this.loading(false)
        }
      })
    },
    async HOANTHIEN_HS_THAYDOI_TS_MYTV(hdkhId, kieuldId, maTb, hdtbId) {
      let dsHD = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdkhId)
      const hdtb = dsHD[0]
      await ChangeMyTVParameterAPI.HOANTHIEN_HS_THAYDOI_TS_MYTV(this.axios, {
        hdtb_obj: hdtb,
        cb_kieuld: kieuldId,
        ma_tb: maTb.trim(),
        hdtb_id: hdtbId
      }).then(response => {
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          this.$root.$toast.success(response.data.message)
          this.dtList = []
        } else {
          this.$root.$toast.warning(response.data.message)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.data.message_detail)
      })
    },
    async HoanThienHS() {
      if (this.dtList.length > 0) {
        this.$confirm('Bạn thật sự muốn hoàn thiện phụ lục không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          await this.HOANTHIEN_HS_THAYDOI_TS_MYTV(this.hdkh_id, Number.parseInt(this.form_thongTinThueBao.selectDichVuVT), this.form_thongTinThueBao.txtMaTB, this.hdtb_id)
        })
      } else {
        this.$root.$toast.error('Không có thuê bao để hoàn thiện!')
      }
    },
    async tsbtnVatTu_Click() {
      try {
        let phieuid = 0
        let s = await this.fn_tt_giaophieu(this.hdtb_id)
        if (s !== "" && s !== "-1") {
          phieuid = Number.parseInt(s)
        }
        this.modelVatTu.hdtb_id = this.hdtb_id
        this.modelVatTu.phieu_id = phieuid
        this.modelVatTu.loaihd_id = LoaiHopDong.VATTU_THUEBAO
        this.modelVatTu.thuebao_id = this.thuebao_id
        this.$refs.popupVatTuThueBao.showModal()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ', error)
      }
    },
    async fn_tt_giaophieu (hdtbid) {
      let req = { param: hdtbid, type: 1 }
      return this.GetData(await ChangeMyTVParameterAPI.fn_tt_giaophieu(this.axios, req))
    },
    onActionClick(action) {
      if (action.id == 'nhapmoi') {
        this.setActiveActions(1)
      } else if (action.id == 'ghilai') {
        this.saveData()
      } else if (action.id == 'huy') {
        this.setActiveActions(0)
      } else if (action.id == 'xoa') {
        this.xoaDuLieu()
      } else if (action.id == 'hoanthien') {
        this.HoanThienHS()
      } else if (action.id == 'kichhoat') {
        this.KichHoat()
      } else if (action.id == 'vattu') {
        this.tsbtnVatTu_Click()
      } else if (action.id == 'thanhtoan') {
        let routeData = this.$router.resolve({
          name: 'Payment',
          query: {magd: this.form_thongTinPhieuYeuCau.txtMaGD.trim()}
        })
        window.open(routeData.href, '_blank')
      }
    },
    setActiveActions(kieu) {
      this.actions.forEach((item) => {
        if (item.id !== 'kichhoat' && item.id !== 'vattu') {
          item.active = false
        }
      })
      if (kieu == -1) { // bat dau
        this.$refs.mathuebao.focus()
        this.actions[this.actions.findIndex(x => x.id == 'ghilai')].active = true
        this.actions[this.actions.findIndex(x => x.id == 'huy')].active = true
      } else if (kieu == 0) { // bắt đầu
        this.actions[this.actions.findIndex(x => x.id == 'nhapmoi')].active = true
        this.$refs.mathuebao.focus()
        this.clear()
      } else if (kieu == 1) { // thêm mới
        this.actions[this.actions.findIndex(x => x.id == 'ghilai')].active = true
        this.actions[this.actions.findIndex(x => x.id == 'huy')].active = true
        this.clear()
      } else if (kieu == 2) { // Huỷ
        this.$refs.magiaodich.focus()
        this.actions[this.actions.findIndex(x => x.id == 'nhapmoi')].active = true
        this.actions[this.actions.findIndex(x => x.id == 'xoa')].active = true
        this.clear()
      } else if (kieu == 3) { // edit
        this.actions[this.actions.findIndex(x => x.id == 'nhapmoi')].active = true
        this.actions[this.actions.findIndex(x => x.id == 'ghilai')].active = true
        this.actions[this.actions.findIndex(x => x.id == 'huy')].active = true
        this.actions[this.actions.findIndex(x => x.id == 'xoa')].active = true
        this.actions[this.actions.findIndex(x => x.id == 'thanhtoan')].active = true
        this.actions[this.actions.findIndex(x => x.id == 'hoanthien')].active = true
      }
    },
    async clear() {
      this.form_thongTinThueBao.selectLoaiTB = null
      this.form_thongTinThueBao.txtMucCuocTBCu = ''
      this.form_thongTinThueBao.txtTrangBiCu = ''
      this.form_thongTinThueBao.txtLoaiTbiCu = ''
      this.form_thongTinThueBao.txtMaTB = ''
      this.actions[this.actions.findIndex(x => x.id == 'kichhoat')].active = false
      this.actions[this.actions.findIndex(x => x.id == 'vattu')].active = false
      this.actions[this.actions.findIndex(x => x.id == 'hoanthien')].active = false
      this.phuuongld_id = 0
      this.phold_id = 0
      this.apld_id = 0
      this.khuld_id = 0
      this.$refs.mathuebao.focus()
      this.form_thongTinThueBao.txtDiaChiLD = ''
      this.hdkh_id = 0
      this.form_thongTinPhieuYeuCau.txtMaGD = ''
      this.form_thongTinPhieuYeuCau.txtMaKH = ''
      this.form_thongTinPhieuYeuCau.txtMaHD = ''
      this.khachhang_id = 0
      this.form_thongTinPhieuYeuCau.txtTenKH = ''
      this.form_thongTinPhieuYeuCau.txtDiaChiKH = ''
      this.form_thongTinPhieuYeuCau.dtpNgayLapHD = moment(new Date()).format('DD/MM/YYYY')
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.form_thongTinThueBao.txtTenTB = ''
      this.form_thongTinThueBao.txtGhiChu = ''
      this.diachi_tb = ''
      this.form_thongTinThueBao.selectKieuLD = null
      this.form_thongTinPhieuYeuCau.dtpNgayYeuCau = moment(new Date()).format('DD/MM/YYYY')
      this.doituong_id = 1
      this.form_thongTinThueBao.txtTienHD = '0'
      this.form_thongTinThueBao.txtVatHD = '0'
      this.trangbi_id = TRANGBI.KHACHHANG_TU_TRANGBI
      this.ltb_adsl_id = this.LOAITBI_ADSL.SET_TOP_BOX
      await this.LayTienTheoKhoanMuc()
      this.totalPrice = '0'
      this.dtList = []
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    async LoadTuHopDongB2A (phieucskh_id, phieucskhdv_id) {
      this.VPHIEUCSKH_ID = phieucskh_id
      this.VPHIEUCSKHDV_ID = phieucskhdv_id
      let data = await this.sp_tt_db_thuebao(phieucskh_id)
      if (data.length > 0) {
        this.form_thongTinThueBao.selectDichVuVT = data[0].dichvuvt_id
        this.form_thongTinThueBao.txtMaTB = data[0].ma_tb
        await this.onMaTBEnter()
      } else {
        this.$root.$toast.warning('Khách hàng không có thuê bao MyTV')
      }
    },
    ChuanHoaTen(ten) {
      if (!ten) return ''
      let tenNew = ''
      let strCon = ten.trim()
      let strXl = ''
      let k = 0
      let found = true
      if (strCon.length < 1) return
      strCon = strCon.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = strCon.indexOf(' ')
          if (k > 0) {
            strXl = strCon.substring(0, k)
            strCon = strCon.substring(k + 1)
            strXl = this.ChuyenChuHoa(strXl)
            if (tenNew !== '') {
              tenNew += ' ' + strXl
            } else tenNew = strXl
            found = true
          } else found = false
        }
        if (strCon !== '') {
          strXl = strCon
          strXl = this.ChuyenChuHoa(strXl)
          if (tenNew !== '') {
            tenNew = tenNew + ' ' + strXl
          } else {
            tenNew = strXl
          }
        }
        return tenNew
      } catch (e) {
        return ten
      }
    }
  }
}
</script>
<style>
.modal-body .page-content {
  position: inherit !important;
}
</style>