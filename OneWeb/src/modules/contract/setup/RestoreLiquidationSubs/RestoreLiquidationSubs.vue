<template src='./RestoreLiquidationSubs.html'></template>
<style scoped src='./RestoreLiquidationSubs.scss'></style>
<script>
/* eslint eqeqeq: 'off', quotes: 'off', camelcase: off */
import gridview from '@/components/Shared/gridview.vue'
import select3 from '@/components/form/VueSelect.vue'
import input3 from '@/components/form/VueInput.vue'
import DatePicker from 'vue2-datepicker'
import { mapActions } from 'vuex'
import 'vue2-datepicker/index.css'
import * as moment from 'moment'
import api from './RestoreLiquidationSubsAPI.js'
import api_installnewsubs from '@/modules/contract/setup/InstallNewSubs/InstallNewSubsAPI.js'
import { LOAI_HOPDONG, TRANGTHAI_HOPDONG, LOAI_DONVI, LOAI_DV, DUNG_THU, DICHVU_VIENTHONG, KHOANMUC_TT, LOAIHINH_THUEBAO, TRANGTHAI_TB, TRANGTHAI_DONGBO, KieuCaiDatDVGT } from '@/constants'
import {formatDate, formatCurrency} from '@/utils/format'
import DialogSearchAccount from '../Liquidate/components/DialogSearchAccount.vue'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import DialogKhuVuc from './components/popupGanKhuVuc.vue'
import DialogTienKhoanMuc from './components/popupTienKM.vue'
import DialogDVCongThem from './components/popupServiceAdds.vue'
import DialogDVKhac from './components/popupServiceOthers.vue'
import DialogTinhCuoc from './components/dialogThongTinTC.vue'
import DialogThongTinLienHe from './components/dialogLienHe.vue'
import DialogEmail from './components/dialogEmail.vue'
import DialogNguoiGioiThieu from './components/dialogNguoiGioiThieu.vue'
import CommonFunction from '@/utils/commonFunction'
import DialogMaTN from './components/dialogMaTN.vue'
import cmFunc from '@/utils/commonFunction'
import { SUCCESS_CODE } from '../../../../constants.js'
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'
export default {
  components: {
    gridview,
    select3,
    input3,
    DatePicker,
    DialogSearchAccount,
    SearchContractModal,
    DialogKhuVuc,
    DialogTienKhoanMuc,
    DialogDVCongThem,
    DialogDVKhac,
    DialogTinhCuoc,
    DialogThongTinLienHe,
    DialogEmail,
    DialogNguoiGioiThieu,
    DialogMaTN
  },
  mixins: [pagingAndFilter],
  name: 'RestoreLiquidationSubs',
  watch: {
    'dgvDanhSach.Rows': {
      handler (val) {
        this.tsbtnGiaoPhieu.Enabled = val.length > 0
        this.tsbtnThemTB.Enabled = val.length >= 1
        // this.tsbtnXoaTB.Enabled = val.length > 1
      },
      deep: true
    },
    'cboKieuLD.Items': {
      handler (val) {
        if (val.length > 0) {
          this.cboKieuLD.SelectedValue = this.cboKieuLD.Items[0].ID
        }
      },
      deep: true
    }
  },
  data: () => {
    return {
      pageSettings: { pageSizes: [10, 50, 100, 200], pageCount: 4, pageSize: 10 },
      gridSelectionSettings: {
        enableToggle: false
      },
      serviceType: 1, // serviceType = 2: co dinh, 3: MegawanMetronet, nguoc lai: giao dien chinh
      momentFormat: {
        stringify: (date) => {
          return date ? moment(date).format('DD/MM/YYYY') : ''
        },
        parse: (value) => {
          return value ? moment(value, 'DD/MM/YYYY').toDate() : null
        }
      },
      headerSubscriptionList: [
        { text: 'Số máy/Acc', fieldtype: '', name: 'so_dt' },
        { text: 'Loại hình', fieldtype: '', name: 'ten_loaihd' },
        { text: 'Tên Thuê bao', fieldtype: '', name: 'ten_tb' },
        { text: 'Địa chỉ thuê bao', fieldtype: '', name: 'diachi_tb' },
        { text: 'Trạng thái', fieldtype: '', name: 'trangthai_hd' }
      ],
      subscriptionList: [
      ],
      // ----menu buttons----
      tsbtnNhapMoi: { Enabled: true },
      tsbtnGhiLai: { Enabled: false, count: 0 },
      tsbtnHuyBo: { Enabled: true },
      tsbtnXoa: { Enabled: false },
      tsbtnThanhToan: { Enabled: true },
      tsbtnThemTB: { Enabled: false },
      tsbtnXoaTB: { Enabled: false },
      tsbtnGiaoPhieu: { Enabled: false },
      tsbtnThongTinLH: { Enabled: true },
      tsbtnEmail: { Enabled: true },
      // ----comboBox----
      cboTocDo_Adsl: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      // thong tin yeu cau
      txtMaGD: { Text: '' },
      txtMaHD: { Text: '' },
      cboDichVuVT: {
        Items: [],
        Enabled: true,
        SelectedValue: 1
      },
      cboLoaihinhTB: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboKieuLD: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      dtpNgayLapHD: { Value: null },
      dtpNgayYeuCau: { Value: moment(new Date()).format('DD/MM/YYYY') },
      dtpNgayTL: { Value: null },
      // thong tin thue bao
      txtMaTB: { Text: '' },
      txtTenTB: { Text: '' },
      txtDiaChiTB: { Text: '' },
      txtDiaChiLD: { Text: '' },
      txtGhiChu: { Text: '' },
      txtMaTN_ADSL: { Text: '' },
      txtKhuVuc: { Text: '' },
      chkDungThu: {
        Enabled: false,
        Checked: ''
      },
      cboDVQL_MGW: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboTram_MGW: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboTocDo_MGW: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      txtSoAo_Wan: { Text: '' },
      cboDaiVT_ADSL: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      txtHuongKN: { Text: '' },
      // thong tin khach hang
      txtMaKH: { Text: '' },
      txtSoDT: { Text: '' },
      txtTenKH: { Text: '' },
      txtDiaChiKH: { Text: '' },
      // thong tin thanh toan
      txtMaTT: { Text: '' },
      txtTenTT: { Text: '' },
      txtDiaChiTT: { Text: '' },
      //
      lvDvgt: {
        Items: []
      },
      lvDvkhac: {
        Items: []
      },
      //
      txtTongTien: { Text: '0' },
      txtTongVat: { Text: '0' },
      txtTTCuoc: { Text: '' },
      dgvDanhSach: { Rows: [] },
      // -----chung------
      txtTongTienHD: { Text: '0' },
      chkCTV: { Checked: false, Enabled: true },
      chkNguoiGT: { Checked: false, Enabled: true },
      chkDoiTac_ADSL: { Checked: true, Enabled: true },
      txtNguoiGT: { Text: '' },
      txtCTV: { Text: '' },
      txtSoAo_ADSL: { Text: '' },
      txtMaDoiCap_ADSL: { Text: '' },
      txtSoSimGphone: { Text: '' },
      txtCuocDT_Adsl: { Text: '0', Enabled: true },
      cboDaiVT_IMS: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboDaiVT_CD: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboDVQLGP: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboMucCuocTB_ADSL: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboMucCuocTB_CD: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboMucCuocTB_IMS: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboVeTinh_Adsl: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboTram_CD: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboTram_IMS: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboMucCuoc_ADSL: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      cboDoiTac_ADSL: {
        Items: [],
        Enabled: true,
        SelectedValue: null
      },
      // ==================
      donvi_ql_cntt: 0,
      dichvuvt_id: 0,
      hdkh_id: 0,
      hdtb_id: 0,
      trangbi_id: 0,
      khachhang_id: 0,
      thuebao_id: 0,
      thuebao_cha_id: 0,
      hdkh_cha_id: 0,
      loaitb_id: 0,
      check_tb_adsl: false,
      doituong_id: 0,
      frmTT:{chapnhan: false},
      khlon_id: 0,
      tocdo_id: 0,
      muccuoc_id: 0,
      quytrinh_id: 0,
      object: {},
      luong_id: 0,
      check_chuahoa_cntt: false,
      dtienld: 0,
      dvatld: 0,
      dtien_dv: 0,
      dvat_dv: 0,
      ds_tien_khoanmuc: [],
      dtCTKM: [],
      dsHDKH: [],
      dsHDTB: [],
      dsHDTB_ADSL: [],
      dsHDTB_CD: [],
      dsHDTB_GP: [],
      dsHDTB_IMS: [],
      dsHDTB_TSL: [],
      dsHDTB_MGW: [],
      dsHDTB_CNTT: [],
      ItemList: [],
      dtList: [],
      CT_TIENHD_ID: 0,
      m_dsThueBao_DichVu: [],
      tien_dv: 0,
      vat_dv: 0,
      dsDK_DVKHAC: [],
      dsDK_DVK_TB: [],
      dsDK_DVGT: [],
      dsSDDVK: [],
      dsTTTT: [],
      dsCTTHD: [],
      dsHDTB_KV: [],
      dsHDTB_DUNGTHU: [],
      khuvuc_id: null,
      phold_id: 0,
      phuongld_id: 0,
      apld_id: 0,
      khuld_id: 0,
      quanld_id: 0,
      dacdiemld_id: 0,
      donviql_id: 0,
      dsHDTBDV: [],
      vhuong_kn: '',
      ctv_id: null,
      donvi_ctv_id: null,
      loainv_ctv_id: null,
      nguoigt_id: null,
      donvi_nguoi_gt_id: null,
      loainv_nguoi_gt_id: null,
      batbuoc_batbuoc_ctv: 0,
      batbuoc_gioithieu_khoiphuc: 0,
      batbuoc_tttc_khoiphuc: 0,
      ts_sinhma_gd_theo_donvi: false, // Dungf cho Hni sinh ma GD theo don vi quan ly cua ng dung
      loi_sinh_magd: false,
      pass_ims: '',
      pass_adsl: '',
      tusinh_pass_ims: 0,
      sinh_TD_matkhau_adsl: 0,
      matkhau_md_ims: '',
      matkhau_md_adsl: '',
      /// <summary>
      /// Cho phép chọn đối tượng dùng thử khi chuyển đổi loại hình; =1: Cho phép
      /// </summary>
      CHUYENDOI_LH_DUNGTHU: -1,
      /// <summary>
      /// Không khôi phục thuê bao dùng thử
      /// </summary>
      KHONG_KHOIPHUC_TB_DUNGTHU: -1,
      VPHIEUCSKH_ID: 0,
      VPHIEUCSKHDV_ID: 0,
      dsDanhMuc: [],
      doituong_ct_id: 0,
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        // 'form-close':this.popupClosed
      },
      popupComponentName: '',
    }
  },
  computed: {
    listTelecomService () {
      return this.$store.getters['restoreLiquidate/listTelecomService']
    },
    listSubscriptionTypeData () {
      return this.$store.getters['restoreLiquidate/listSubscriptionType']
    },
    listSubscriptionType () {
      return this.listSubscriptionTypeData.filter(el => el.DICHVUVT_ID === +this.cboDichVuVT)
    },
    listRequestType () {
      return this.$store.getters['restoreLiquidate/listRequestType']
    },
    listDonViQL () {
      return this.$store.getters['restoreLiquidate/listDonVi']
    }
  },
  mounted () {
    try {
      this.loading(false)
      window['component'] = this
      this.getListTelecomService()
      this.getListSubscriptionType().then(ds => {
        this.cboDichVuVT_SelectedValueChanged()
      })
      this.popupComponentEvts['form-close'] = this.popupClosed;
      // this.getListDonVi({ donViID: 0, loaiDonViID: LOAI_DONVI.TRAM_VT })
      // this.initSpliter()
      this.onLoad()
    } catch (e) {} finally { this.loading(false) }
  },
  methods: {
    ...mapActions('restoreLiquidate', [
      'getListTelecomService',
      'getListSubscriptionType',
      'getListDonVi'
    ]),
    initSpliter () {
      console.log('init spliter')
      window['Split'](['#boxLeft', '#boxRight'], {
        sizes: [60, 40],
        gutterSize: 16,
        onDragEnd: function (sizes) {}
      })
      window['Split'](['#boxLeft1', '#boxRight1'], {
        sizes: [40, 60],
        gutterSize: 16,
        onDragEnd: function (sizes) {}
      })
    },
    async onLoad () {
      try {
        this.dtpNgayLapHD.Value = this.$auth.getNgayCapNhat()
        this.setButton(-1)
        var tmp_tien_km = await api.sp_lay_tienkhoanmuc_loaihd(LOAI_HOPDONG.KHOIPHUC_SD)
        this.ds_tien_khoanmuc = tmp_tien_km.map(r => ({ ...r, khoanmuctt_id: Number(r['khoanmuctt_id']), tongtien: Number(r['tien']) + Number(r['vat']) }))
        // TODO goi api => load du lieu vao cac combobox
        api.getListTelecomService().then(rs => {
          this.cboDichVuVT.Items = rs
          this.cboDichVuVT_SelectedValueChanged()
        })
        api.lay_ds_donvi_theo_loaidv(0, LOAI_DONVI.DONVIQL_LD).then(ds => {
          this.cboDaiVT_CD.Items = ds.map(r => ({...r, donvi_id: Number(r.donvi_id)}))
          this.cboDaiVT_CD_SelectedValueChanged()
          this.cboDaiVT_ADSL.Items = ds.map(r => ({...r, donvi_id: Number(r.donvi_id)}))
          this.cboDVQLGP.Items = ds.map(r => ({...r, donvi_id: Number(r.donvi_id)}))
          this.cboDVQL_MGW.Items = ds.map(r => ({...r, donvi_id: Number(r.donvi_id)}))
          this.cboDaiVT_IMS.Items = ds.map(r => ({...r, donvi_id: Number(r.donvi_id)}))
        })
        // bangts.HT_DonVi_loaidv_Combobox(this.cboDaiVT_ADSL, 0, LOAI_DONVI.DONVIQL_LD)
        // bangts.HT_DonVi_loaidv_Combobox(this.cboDaiVT_CD, 0, LOAI_DONVI.DONVIQL_LD)
        // bangts.HT_DonVi_loaidv_Combobox(this.cboDVQLGP, 0, LOAI_DONVI.DONVIQL_LD)
        // bangts.HT_DonVi_loaidv_Combobox(this.cboDVQL_MGW, 0, LOAI_DONVI.DONVIQL_LD)
        // bangts.HT_DonVi_loaidv_Combobox(this.cboDaiVT_IMS, 0, LOAI_DONVI.DONVIQL_LD)
        // api.lay_dulieu_theo_dieukien("tocdo_kenh", "tocdo_id", "tocdo||' '||donvi toc_do", "", "tocdo").then(ds => {
        //   this.cboTocDo_MGW.Items = ds
        // })
        //Fix ATT
        api.lay_dulieu_theo_dieukien_toc_do_kenh().then(ds => {
          this.cboTocDo_MGW.Items = ds
        })
        // api.lay_dulieu_theo_dieukien('muccuoc', 'muccuoc_id', 'tenmuc as tenmuccuoc', '', 'muccuoc_id').then(ds => {
        //   this.cboMucCuoc_ADSL.Items = ds.map(r => ({...r, muccuoc_id: Number(r.muccuoc_id)}))
        // })
        //FIX ATT
        api.lay_dulieu_theo_dieukien_muc_cuoc().then(ds => {
          this.cboMucCuoc_ADSL.Items = ds.map(r => ({...r, muccuoc_id: Number(r.muccuoc_id)}))
        })
        // bangts.HT_Tocdo_Kenh_Combobox(this.cboTocDo_MGW)
        // bangts.HT_MucCuoc_Adsl_Combobox(this.cboMucCuoc_ADSL)
        this.dsDanhMuc = await api.lay_ds_doitac()
        this.cboDoiTac_ADSL.Items = this.dsDanhMuc
        // bangts.HT_DS_GRIDLOOKUP(this.cboDoiTac_ADSL, dsDanhMuc.Tables['DOITAC'], 'TEN_DT', 'DOITAC_ID', true, 2, 400)
        // region Lấy các tham số mặc đinh
        var ds = await api.lay_ds_thamso_md(0)
        // DataSet ds = GlobalVar.glbThamSoMD
        if (ds.length > 0) {
          var strvtemp = 'BATBUOC_CTV_KHOIPHUC'
          var det = ds.filter(x => x['ma_ts'] == strvtemp)
          if (det.length > 0) {
            var row = det[0]
            if (row['ten_ts'] == '1') { this.batbuoc_batbuoc_ctv = 1 }
          }
          strvtemp = 'CHECK_CHUANHOA_CNTT' // hoangpkn : 18/03/2020. Check thue bao CNTT can chuan hoa
          det = ds.filter(x => x['ma_ts'] == strvtemp)
          if (det.length > 0) {
            var row = det[0]
            if (row['ten_ts'] == '1') { this.check_chuahoa_cntt = true }
          }
          strvtemp = 'TUSINH_PASS_IMS'
          det = ds.filter(x => x['ma_ts'] == strvtemp)
          if (det.length > 0) {
            var row = det[0]
            if (row['ten_ts'] == '1') { this.tusinh_pass_ims = 1 }
            if (row['ten_ts'] == '100') { this.tusinh_pass_ims = 100 }
            if (row['ten_ts'] == '50') { this.tusinh_pass_ims = 50 }
          }
          strvtemp = 'MACDINH_MATKHAU_IMS'
          det = ds.filter(x => x['ma_ts'] == strvtemp)
          if (det.length > 0) {
            var row = det[0]
            this.matkhau_md_ims = row['ten_ts']
          }
          // Tham số mật khẩu mặc định ADSL
          strvtemp = 'MACDINH_MATKHAU_ADSL'
          var rts = ds.find(x => x['ma_ts'] == strvtemp)
          if (rts) {
            this.matkhau_md_adsl = rts['ten_ts']
          }
          // Tham số tự động sinh mật khẩu thuê bao theo yêu cầu
          strvtemp = 'TUDONG_SINH_MATKHAU_TB'
          rts = ds.find(x => x['ma_ts'] == strvtemp)
          if (rts) {
            if (rts['ten_ts'] == '1') { this.sinh_TD_matkhau_adsl = 1 }
          }
          // THam số ko bắt buộc phải nhập cộng tác viên
          strvtemp = 'BATBUOC_GIOITHIEU_KHOIPHUC'
          det = ds.filter(x => x['ma_ts'] == strvtemp)
          if (det.length > 0) {
            var row = det[0]
            if (row['ten_ts'] == '1') { this.batbuoc_gioithieu_khoiphuc = 1 }
          }
          // Tham số bắt buocj nhập thông tin tính cước
          strvtemp = 'BATBUOC_TTTC_KHOIPHUC'
          det = ds.filter(x => x['ma_ts'] == strvtemp)
          if (det.length > 0) {
            var row = det[0]
            if (row['ten_ts'] == '1') { this.batbuoc_tttc_khoiphuc = 1 }
          }
          this.ts_sinhma_gd_theo_donvi = ds.filter(x => x['ma_ts'] == 'SINH_MA_GD_THEO_DV').length > 0
          strvtemp = 'CHUYENDOI_LH_DUNGTHU'
          det = ds.filter(x => x['ma_ts'] == strvtemp)
          if (det.length > 0) {
            var row = det[0]
            if (row['ten_ts'] == '1') { this.CHUYENDOI_LH_DUNGTHU = 1 }
          }
          strvtemp = 'KHONG_KHOIPHUC_TB_DUNGTHU'
          det = ds.filter(x => x['ma_ts'] == strvtemp)
          if (det.length > 0) {
            var row = det[0]
            if (row['ten_ts'] == '1') { this.KHONG_KHOIPHUC_TB_DUNGTHU = 1 }
          }
        }
        if (this.$route.query) {
          var { kieu, phieucskh_id, phieucskhdv_id, hdkh_cha_id, ma_tb } = this.$route.query
          if ((kieu = kieu || 0) != 0) {
            if (kieu == 5 && phieucskh_id && phieucskhdv_id) {
              this.LoadTuHopDongB2A(phieucskh_id, phieucskhdv_id)
            } else if (hdkh_cha_id) {
              this.LoadTuHopDongTuVan(hdkh_cha_id)
            }
          }
          // One
          if (ma_tb) {
            this.txtMaTB.Text = ma_tb
            await this.onMaTBKeyPress({keyCode: 13})
          }
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error(cmFunc.getErrorMessage(ex))
        // new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true)
      }
    },
    async LoadTuHopDongB2A (phieucskh_id, phieucskhdv_id) {
      this.VPHIEUCSKH_ID = phieucskh_id
      this.VPHIEUCSKHDV_ID = phieucskhdv_id
      // var filter = `trangthaitb_id = 7 and ma_tb is not null and khachhang_id in (select khachhang_id from phieu_cskh where phieucskh_id = ${phieucskh_id})`
      // var dt = await api.lay_dulieu_theo_dieukien("db_thuebao", "ma_tb", "dichvuvt_id", filter, "")
      //FIX ATT
      var dt = await api.lay_dulieu_theo_dieukien_thue_bao( 4, phieucskh_id );
      if (dt.length > 0) {
        this.cboDichVuVT.SelectedValue = dt["DICHVUVT_ID"]
        this.txtMaTB.Text = dt["MA_TB"]
        this.onMaTBKeyPress({keyCode: 13})
      } else {
        this.$toast.error("Khách hàng không có thuê bao đã thanh lý, không thể khôi phục")
      }
    },
    async LoadTuHopDongTuVan (hdkh_cha_id) {
      this.hdkh_cha_id = hdkh_cha_id
      // var filter = `trangthaitb_id = 7 and ma_tb is not null and khachhang_id in (select khachhang_id from hd_khachhang)${hdkh_cha_id}`
      // var dt = api.lay_dulieu_theo_dieukien("db_thuebao", "ma_tb", "dichvuvt_id", filter, "")
      //FIX ATT
      var dt = await api.lay_dulieu_theo_dieukien_thue_bao( 5, hdkh_cha_id );
      if (dt.length > 0) {
        this.cboDichVuVT.SelectedValue = dt[0][1]
        this.txtMaTB.Text = dt[0][0]
        this.onMaTBKeyPress({keyCode: 13})
      } else {
        this.$toast.error("Khách hàng không có thuê bao đã thanh lý, không thể khôi phục")
      }
    },
    async onMaTBKeyPress (e) {
      if (e.keyCode !== 13) {
        return
      }
      if (!this.txtMaTB.Text.trim()) {
        return
      }
      try {
        this.loading(true)
        var ds = await api.lay_ds_hopdong_theo_ma_tb({
          'ma_tb': this.txtMaTB.Text,
          'in_loaihd_id': LOAI_HOPDONG.KHOIPHUC_SD,
          'in_donvi_id': 0, // +this.$auth.getDonViID(),
          'in_tthd_id': TRANGTHAI_HOPDONG.MOI,
          'in_nhanvien_id': 0, // +this.$auth.getNhanVienID(),
          'in_donvi_dl_id': 0,
          'in_dichvuvt_id': this.cboDichVuVT.SelectedValue,
          'in_loaitb_id': 0
        })
        if (ds.length > 0 && ds[0]['ma_tb'] === this.txtMaTB.Text.trim()) {
          await this.hienThiTTHopDongKH(ds)
        } else {
          await this.hienThiTTDanhBa(this.txtMaTB.Text)
        }
        let pdata = {
              vapld_id: 0,
              vdichvuvt_id: this.dichvuvt_id,
              vdoituong_id: this.doituong_id,
              vkh_cd: 0,
              vkhuld_id: 0, 
              vkieuld_id: this.cboKieuLD.SelectedValue,
              vloaihd_id: LOAI_HOPDONG.KHOIPHUC_SD,
              vloaitb_id: this.loaitb_id, 
              vltb_adsl_cu_id: 0,
              vltb_adsl_id: 1,
              vltb_adsl_moi_id: 0,
              vmuccuoc_id: this.muccuoc_id,
              vngay_yc: this.dtpNgayYeuCau.Value,
              vphanvung_id: this.$root.token.getPhanVungID(),
              vphold_id: 0,
              vphuongld_id: 0,  
              vtocdo_cu_id: 0,
              vtocdo_id: this.tocdo_id,
              vtocdo_moi_id: 0,
              vtrangbi_id: this.trangbi_id 
          }
          var tmp_tien_km = await  api.get_lay_tien_khoanmuc_ld_v2(this.axios, { params: pdata })
          if(tmp_tien_km&&tmp_tien_km.length>0&&tmp_tien_km.data.data[0].tien != 0){
              let ds_tien_khoanmuc = tmp_tien_km.data.data.map(r => ({ ...r, khoanmuctt_id: Number(r['khoanmuctt_id']), tongtien: Number(r['tien']) + Number(r['vat']) }))
              this.LayTongTien_HDTB_V2(ds_tien_khoanmuc)
          }
      } catch (e) {} finally {this.loading(false)}
    },
    changeSelectSubscription (e) {
      console.log('changeSelectSubscription', e, this.listSubscriptionTypeData.filter(el => el.LOAITB_ID === e.loaitb_id))
    },
    async hienThiTTHopDongKH (data) {
      if (data.length > 0) {
        this.dtpNgayYeuCau.Value = formatDate(data[0]['ngay_yc'])
        this.dtpNgayLapHD.Value = formatDate(data[0]['ngaylap_hd'])
        this.hdkh_id = Number(data[0]['hdkh_id'])
        this.txtMaGD.Text = data[0]['ma_gd']
        this.txtMaHD.Text = data[0]['ma_hd']
        this.txtMaKH.Text = data[0]['ma_kh']
        this.txtSoDT.Text = data[0]['so_dt']
        if (data[0]['khachhang_id']) {
          this.khachhang_id = Number(data[0]['khachhang_id'])
        } else {
          this.khachhang_id = 0
        }
        this.txtTenKH.Text = data[0]['ten_kh']
        this.txtDiaChiKH.Text = data[0]['diachi_kh']
        if (data[0]['ctv_id']) {
          this.chkCTV.Checked = true
          this.ctv_id = Number(data[0]['ctv_id'])
          this.txtCTV.Text = await api.fn_tt_nhanvien(this.ctv_id, 3)
        } else {
          this.txtCTV.Text = ''
          this.chkCTV.Checked = false
        }
        if (data[0]['nhanviengt_id']) {
          this.chkNguoiGT.Checked = true
          this.nguoigt_id = Number(data[0]['nhanviengt_id'])
          this.txtNguoiGT.Text = await api.fn_tt_nhanvien(this.nguoigt_id, 3)
        } else {
          this.txtNguoiGT.Text = ''
          this.chkNguoiGT.Checked = false
        }
        if (data[0]['PHIEUCSKH_ID']) {
          this.VPHIEUCSKH_ID = Number(data[0]['PHIEUCSKH_ID'])
        }
        //
        this.setButton(3)
        var dsHD = await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TRANGTHAI_HOPDONG.MOI)
        dsHD = dsHD.map(r => this.formatJson(r))
        await this.hienThiDanhSacHDTB(dsHD)
        await this.hienThiTTHopDongTB(dsHD)
        if (this.dgvDanhSach.Rows.length > 1) {
          this.tsbtnXoaTB.Enabled = true
        } else {
          this.tsbtnXoaTB.Enabled = false
        }
      } else {
        this.$toast.error(`Không tìm thấy thông tin về hợp đồng có mã giao dịch: ${this.txtMaGD.Text}. Bạn hãy kiểm tra lại`)
        this.setButton(0)
      }
    },
    async hienThiTTHopDongTB (data) {
      try {
        this.loading(true)
        if (data.length > 0) {
          this.hdtb_id = Number(data[0]['hdtb_id'])
          if (data[0]['thuebao_id']) { this.thuebao_id = Number(data[0]['thuebao_id']) } else { this.thuebao_id = 0 }
          if (data[0]['thuebao_cha_id']) { this.thuebao_cha_id = Number(data[0]['thuebao_cha_id']) } else { this.thuebao_cha_id = 0 }
          var dsdc = await api.lay_ds_diachi_theo_dbtbid(this.thuebao_id)
          this.hienThiTTDiaChi(dsdc)
          this.txtMaTB.Text = data[0]['ma_tb']
          this.txtTenTB.Text = data[0]['ten_tb']
          this.txtDiaChiTB.Text = data[0]['diachi_tb']
          this.txtDiaChiLD.Text = data[0]['diachi_ld']
          this.txtGhiChu.Text = data[0]['ghichu']
          this.dichvuvt_id = Number(data[0]['dichvuvt_id'])
          this.cboDichVuVT.SelectedValue = this.dichvuvt_id
          await this.cboDichVuVT_SelectedValueChanged()
          this.cboLoaihinhTB.SelectedValue = Number(data[0]['loaitb_id'])
          this.loaitb_id = Number(data[0]['loaitb_id'])
          await this.cboLoaihinhTB_SelectedValueChanged()
          this.doituong_id = Number(data[0]['doituong_id'])
          if (this.doituong_id === DUNG_THU.DOITUONG_TB && this.CHUYENDOI_LH_DUNGTHU === 1) {
            this.chkDungThu.Checked = true
          } else { this.chkDungThu.Checked = false }
          if (data[0]['khlon_id']) { this.khlon_id = Number(data[0]['khlon_id']) }
          // Hiển thị kiểu lắp đặt - Hiếu ngày 29.09.2010- Kiểu lắp đặt ăn theo loại hình thuê bao
          var dtKieuLD = await api.lay_ds_kieu_ld(LOAI_HOPDONG.KHOIPHUC_SD, this.loaitb_id)
          this.cboKieuLD.Items = dtKieuLD
          dsdc = await api.lay_ds_diachi_theo_hdtb_id(this.hdtb_id)
          this.hienThiTTDiaChi(dsdc)
          this.cboKieuLD.SelectedValue = Number(data[0]['kieuld_id'])
          if (this.dichvuvt_id === DICHVU_VIENTHONG.ADSL) {
            this.tocdo_id = data[0]['tocdo_id'] ? Number(data[0]['tocdo_id']) : 0
            this.cboTocDo_Adsl.SelectedValue = data[0]['tocdo_id'] ? Number(data[0]['tocdo_id']) : 0
            this.cboDaiVT_ADSL.SelectedValue = data[0]['donvi_id']
            var dsmc = await api.hien_thi_cbo_muc_cuoc(Number(data[0]['mucuoctb_id']))
            this.cboMucCuocTB_ADSL.Items = dsmc
            this.cboMucCuocTB_ADSL.SelectedValue = Number(data[0]['mucuoctb_id'])
          } else if (this.dichvuvt_id === DICHVU_VIENTHONG.CO_DINH) { 
            this.cboDaiVT_CD.SelectedValue = data[0]['donvi_id']             
            var dsmc = await api.hien_thi_cbo_muc_cuoc(Number(data[0]['mucuoctb_id']))
            this.cboMucCuocTB_CD.Items = dsmc
            this.cboMucCuocTB_CD.SelectedValue = Number(data[0]['mucuoctb_id'])
          } else if (this.dichvuvt_id === DICHVU_VIENTHONG.IMS) { 
            this.cboDaiVT_IMS.SelectedValue = data[0]['donvi_id']             
            var dsmc = await api.hien_thi_cbo_muc_cuoc(Number(data[0]['mucuoctb_id']))
            this.cboMucCuocTB_IMS.Items = dsmc
            this.cboMucCuocTB_IMS.SelectedValue = data[0]['mucuoctb_id']
          } else if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26) {
            this.cboDVQL_MGW.SelectedValue = Number(data[0]['donviql_id'])
            this.cboTocDo_MGW.SelectedValue = Number(data[0]['tocdo_id'])
            this.tocdo_id = Number(data[0]['tocdo_id'])
          } else if ( this.dichvuvt_id === DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
                      this.dichvuvt_id === DICHVU_VIENTHONG.DICHVU_CNTT ||
                      this.dichvuvt_id === DICHVU_VIENTHONG.HOINGHI_TRUYENHINH ||
                      this.dichvuvt_id === DICHVU_VIENTHONG.TRUNGTAM_DULIEU ) { 
            this.donvi_ql_cntt = Number(data[0]['donvi_id']) 
          }
          for (var i = 0; i < data.length; i++) {
            if (Number(data[i]['dichvuvt_id']) === DICHVU_VIENTHONG.ADSL &&
                            (Number(data[i]['loaitb_id']) === LOAIHINH_THUEBAO.INTERNET_ADSL ||
                                Number(data[i]['loaitb_id']) === LOAIHINH_THUEBAO.INTERNET_FTTH ||
                                Number(data[i]['loaitb_id']) === LOAIHINH_THUEBAO.WIFI_FIBER)) {
              if (Number(data[i]['status']) === 1) {
                this.check_tb_adsl = true
                break
              }
            }
          }
          await this.hienThiTTThanhToan(this.txtMaTB.Text.trim(), this.dichvuvt_id, 0)
          await this.hienThiTTHDMoRong(this.hdtb_id)
          if (this.dichvuvt_id !== DICHVU_VIENTHONG.TSL && this.dichvuvt_id != 25) {
            var dstc = await api.lay_donvi_thicong(this.hdtb_id, 2, LOAI_DONVI.TRAM_VT, 2)
            // this.cboTram_MGW.SelectedValue = Number(dstc[0]['donvi_id'])
            if (dstc && dstc.length > 0) {
              if (this.dichvuvt_id === DICHVU_VIENTHONG.CO_DINH) { this.cboTram_CD.SelectedValue = Number(dstc[0]['donvi_id']) }
              if (this.dichvuvt_id === DICHVU_VIENTHONG.ADSL) { this.cboVeTinh_Adsl.SelectedValue = Number(dstc[0]['donvi_id']) }
              if (this.dichvuvt_id === DICHVU_VIENTHONG.IMS) { this.cboTram_IMS.SelectedValue = Number(dstc[0]['donvi_id']) }
              if (this.dichvuvt_id === DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id === DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26) {
                this.cboTram_MGW.SelectedValue = Number(dstc[0]['donvi_id'])
              }
            } else {
              this.$toast.error('Không có đơn vị thi công, hãy liên hệ Admin để xử lý!')
              return
            }
          }
          this.m_dsThueBao_DichVu = await api.lay_ds_thuebao_dichvu(this.hdtb_id, 0, Number(this.cboLoaihinhTB.SelectedValue), 0)
          this.HT_DichVuGT()
          this.dsDK_DVKHAC = await api.lay_ds_dangky_dvk(this.hdtb_id, 1)
          await this.hienThiDichVuKhac()
          var tmp_tien_km = await api.lay_tienhd_km_theo_hdtb(this.hdtb_id)
          this.ds_tien_khoanmuc = tmp_tien_km.map(r => ({ ...r, khoanmuctt_id: Number(r.khoanmuctt_id), tongtien: Number(r['tien']) + Number(r['vat']) }))
          for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
            if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] === KHOANMUC_TT.KMTT_KHOIPHUCSD) {
              this.dtienld = Number(this.ds_tien_khoanmuc[i]['tien'])
              this.dvatld = Number(this.ds_tien_khoanmuc[i]['vat'])
            }
            if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] === KHOANMUC_TT.KMTT_DICHVU) {
              this.dtien_dv = Number(this.ds_tien_khoanmuc[i]['tien'])
              this.dvat_dv = Number(this.ds_tien_khoanmuc[i]['vat'])
            }
          }
          this.LayTongTien_HDTB()
          this.tsbtnXoaTB.Enabled = (this.dgvDanhSach.Rows.length >= 2)
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi : ' + cmFunc.getErrorMessage(ex))
      } finally {
        this.loading(false)
      }
    },
    async hienThiTTDanhBa (maTB) {
      try {
        var ds = await api.lay_danhba_theo_matb(maTB, this.dichvuvt_id, 0)
        if (ds.length > 0) {
          if (ds[0]['dichvuvt_id'] == DICHVU_VIENTHONG.ADSL) {
            if (ds[0]['loaitb_id'] != LOAIHINH_THUEBAO.INTERNET_ADSL &&
                            ds[0]['loaitb_id'] != LOAIHINH_THUEBAO.INTERNET_FTTH &&
                            ds[0]['loaitb_id'] != LOAIHINH_THUEBAO.WIFI_FIBER
            ) {
              var loaihinhTB = await api.fn_tt_loaihinh_tb(ds[0]['loaitb_id'], 1)
              this.$toast.error('Không thực hiện khôi phục thanh lý với thuê bao ' + loaihinhTB)
              return
            }
          }
          if (Number(ds[0]['trangthaitb_id']) == TRANGTHAI_TB.THANHLY || Number(ds[0]['trangthaitb_id']) == TRANGTHAI_TB.THANHLY_NO) {
            /// /Thông tin khách hàng
            // if (khachhang_id != 0 && khachhang_id != Number(ds[0]['khachhang_id']))
            // {
            //    this.$toast.error('Hãy nhấn nút nhập mới để tiếp tục nhập số mới !')
            //    setButton(0)
            //    return
            // }
            // Thông tin khách hàng
            this.txtMaKH.Text = ds[0]['ma_kh']
            this.txtMaHD.Text = ds[0]['ma_hd']
            this.khachhang_id = Number(ds[0]['khachhang_id'])
            this.txtTenKH.Text = ds[0]['ten_kh']
            this.txtDiaChiKH.Text = ds[0]['diachi_kh']
            if (ds[0]['ngaylap_hd']) { this.dtpNgayLapHD.Value = formatDate(ds[0]['ngaylap_hd']) } else { this.dtpNgayLapHD.Value = this.$auth.getNgayCapNhat() }
            this.txtSoDT.Text = ds[0]['so_dt']
            // thông tin thanh toán
            this.txtMaTT.Text = ds[0]['ma_tt']
            this.txtTenTT.Text = ds[0]['ten_tt']
            this.txtDiaChiTT.Text = ds[0]['diachi_tt']
            // Thông tin thuê bao
            this.thuebao_id = Number(ds[0]['thuebao_id'])
            if (ds[0]['thuebao_cha_id']) { this.thuebao_cha_id = Number(ds[0]['thuebao_cha_id']) } else {
              this.thuebao_cha_id = 0
            }
            this.txtMaTB.Text = ds[0]['ma_tb']
            this.txtTenTB.Text = ds[0]['ten_tb']
            this.txtDiaChiTB.Text = ds[0]['diachi_tb']
            this.txtDiaChiLD.Text = ds[0]['diachi_ld']
            this.txtGhiChu.Text = ds[0]['ghichu_tb']
            this.loaitb_id = Number(ds[0]['loaitb_id'])
            var dsdc = await api.lay_ds_diachi_theo_dbtbid(this.thuebao_id)
            this.hienThiTTDiaChi(dsdc)
            this.cboLoaihinhTB.SelectedValue = this.loaitb_id
            if (this.loaitb_id == LOAIHINH_THUEBAO.INTERNET_FTTH && this.CHUYENDOI_LH_DUNGTHU == 1) {
              this.chkDungThu.Enabled = true
            } else {
              this.chkDungThu.Enabled = false
              this.chkDungThu.Checked = false
            }
            if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) {
              this.cboDaiVT_ADSL.SelectedValue = parseInt(ds[0]['donviql_id'])
              // this.cboDaiVT_ADSL_SelectedValueChanged()
              if (ds[0]['mucuoctb_id']) {
                this.cboMucCuocTB_ADSL.SelectedValue = Number(ds[0]['mucuoctb_id'])
                var dsmc = await api.hien_thi_cbo_muc_cuoc(Number(ds[0]['mucuoctb_id']))
                this.cboMucCuocTB_ADSL.Items = dsmc
              } else {
                this.$toast.error('Không tìm thấy mức cước thuê bao. Liên hệ admin để xử lý !')
                this.cboMucCuocTB_ADSL.Items = []
                this.clear()
                return
              }
            } else if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26) {
              this.cboDVQL_MGW.SelectedValue = Number(ds[0]['donviql_id'])
              this.cboTocDo_MGW.SelectedValue = Number(ds[0]['tocdo_id'])
              this.tocdo_id = Number(ds[0]['tocdo_id'])
              // this.cboDVQL_MGW_SelectedValueChanged()
            } else if (this.dichvuvt_id == DICHVU_VIENTHONG.CO_DINH) {
              this.cboDaiVT_CD.SelectedValue = Number(ds[0]['donviql_id'])
              // this.cboDaiVT_CD_SelectedValueChanged()
              if (ds[0]['mucuoctb_id']) {
                this.cboMucCuocTB_CD.SelectedValue = ds[0]['mucuoctb_id']
                var dsmc = await api.hien_thi_cbo_muc_cuoc(Number(ds[0]['mucuoctb_id']))
                this.cboMucCuocTB_CD.Items = dsmc
                // bangts.HT_MucCuocTB_Combobox(this.cboMucCuocTB_CD, Number(ds[0]['mucuoctb_id']))
              } else {
                this.$toast.error('Không tìm thấy mức cước thuê bao. Liên hệ admin để xử lý !')
                this.clear()
                return
              }
            } else if (this.dichvuvt_id == DICHVU_VIENTHONG.IMS) {
              this.cboDaiVT_IMS.SelectedValue = Number(ds[0]['donviql_id'])
              // this.cboDaiVT_IMS_SelectedValueChanged()
              if (ds[0]['mucuoctb_id']) {
                this.cboMucCuocTB_IMS.SelectedValue = ds[0]['mucuoctb_id']
                var dsmc = await api.hien_thi_cbo_muc_cuoc(Number(ds[0]['mucuoctb_id']))
                this.cboMucCuocTB_IMS.Items = dsmc
                // bangts.HT_MucCuocTB_Combobox(this.cboMucCuocTB_IMS, Number(ds[0]['mucuoctb_id']))
              } else {
                this.$toast.error('Không tìm thấy mức cước thuê bao. Liên hệ admin để xử lý !')
                this.clear()
                return
              }
            } else if (this.dichvuvt_id == DICHVU_VIENTHONG.GPHONE) {
              this.cboDVQLGP.SelectedValue = Number(ds[0]['donviql_id'])
            } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
                            Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.DICHVU_CNTT ||
                            Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.HOINGHI_TRUYENHINH ||
                            Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.TRUNGTAM_DULIEU) { this.donvi_ql_cntt = Number(ds[0]['donviql_id']) }
            if (this.dichvuvt_id != DICHVU_VIENTHONG.TSL && this.dichvuvt_id != 25) {
              var dstc = await api.lay_donvi_thicong(this.thuebao_id, 1, LOAI_DONVI.TRAM_VT, 2)
              if (dstc.length > 0) {
                if (this.dichvuvt_id == DICHVU_VIENTHONG.CO_DINH) { this.cboTram_CD.SelectedValue = Number(dstc[0]['donvi_id']) }
                if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) { this.cboVeTinh_Adsl.SelectedValue = Number(dstc[0]['donvi_id']) }
                if (this.dichvuvt_id == DICHVU_VIENTHONG.IMS) { this.cboTram_IMS.SelectedValue = Number(dstc[0]['donvi_id']) }
                if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26) { this.cboTram_MGW.SelectedValue = Number(dstc[0]['donvi_id']) }
              }
            }
            await this.hienThiTTDBMoRong()
            if (ds[0]['ngay_cat']) {
              this.dtpNgayTL.Value = formatDate(ds[0]['ngay_cat'], 'dd/mm/yyyy', 'DD/MM/YYYY hh:mm:ss')
            }
            this.doituong_id = Number(ds[0]['doituong_id'])
            if (ds[0]['khlon_id']) { this.khlon_id = Number(ds[0]['khlon_id']) }
            this.dsSDDVK = await api.lay_ds_sudung_dvk(this.thuebao_id, 1)
            await this.hienThiDichVuKhac_DB()
            await this.LAY_DS_DVGT_MACDINH()
            // SendKeys.Send('{TAB}')
          } else {
            this.$toast.error('Thuê bao không ở trạng thái thanh lý. Bạn không thể khôi phục !')
            if (this.hdkh_id == 0) {
              this.clear()
            }
            return
          }
        } else {
          var selectedDvVT = this.cboDichVuVT.Items.find(i => i.dichvuvt_id === this.cboDichVuVT.SelectedValue)
          var dvvtText = selectedDvVT ? selectedDvVT.ten_dvvt : ''
          this.$toast.error('Không tìm thấy thuê bao : ' + this.txtMaTB.Text.trim() + ' cho dịch vụ ' + dvvtText)
          this.clear()
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi : ' + cmFunc.getErrorMessage(ex))
      }
    },
    hienThiDanhSacHDTB (data) {
      try {
        this.loading(true)
        if (data && data.length > 0) {
          data = data.map((r, index) => ({ ...r, stt: index + 1 }))
          this.dgvDanhSach.Rows = data
          if (data.length > 1) { this.tsbtnXoaTB.Enabled = true } else { this.tsbtnXoaTB.Enabled = false }
          if (data.length > 0) { this.tsbtnThemTB.Enabled = true } else { this.tsbtnThemTB.Enabled = false }
          if (data.length > 0) {
            setTimeout(() => {
              this.$refs.lvwThueBao.selectRow(this.dgvDanhSach.Rows.length - 1, false)
            }, 500)
          }
        } else {
          this.clear()
          this.dgvDanhSach.Rows = []
        }
      } catch (e) {
        console.log('hienThiDanhSacHDTB', e)
      } finally {
        this.loading(false)
      }
    },
    hienThiTTDiaChi (ds) {
      if (ds.length > 0) {
        if (ds[0]['phuongld_id']) { this.phuongld_id = Number(ds[0]['phuongld_id']) } else { this.phuongld_id = 0 }
        if (ds[0]['quanld_id']) { this.quanld_id = Number(ds[0]['quanld_id']) } else { this.quanld_id = 0 }
        if (ds[0]['dacdiemld_id']) { this.dacdiemld_id = Number(ds[0]['dacdiemld_id']) } else { this.dacdiemld_id = 0 }
        if (ds[0]['phold_id']) { this.phold_id = Number(ds[0]['phold_id']) } else { this.phold_id = 0 }
        if (ds[0]['apld_id']) { this.apld_id = Number(ds[0]['apld_id']) } else { this.apld_id = 0 }
        if (ds[0]['khuld_id']) { this.khuld_id = Number(ds[0]['khuld_id']) } else { this.khuld_id = 0 }
      }
    },
    async hienThiTTThanhToan (maTb, dichVuID, donvidlID) {
      var dsThanhToan = await api.lay_danhba_theo_matb(maTb, dichVuID, donvidlID)
      if (dsThanhToan.length > 0) {
        this.txtMaTT.Text = dsThanhToan[0]['ma_tt']
        this.txtTenTT.Text = dsThanhToan[0]['ten_tt']
        this.txtDiaChiTT.Text = dsThanhToan[0]['diachi_tt']
        if (dsThanhToan[0]['ngay_cat']) {
          this.dtpNgayTL.Value = formatDate(dsThanhToan[0]['ngay_cat'], 'dd/mm/yyyy', 'DD/MM/YYYY hh:mm:ss')
        }
      }
    },
    async hienThiTTHDMoRong (hdtbID) {
      try {
        this.tocdo_id = 0
        this.muccuoc_id = 0
        if (this.dichvuvt_id === DICHVU_VIENTHONG.ADSL) {
          var hdcon = await api.lay_ds_hopdong_thuebao_adsl(hdtbID)
          if (hdcon.length > 0) {
            // cboVeTinh_Adsl.SelectedValue = Number(hdcon.Tables[0].Rows[0]['tramtb_id'].toString())
            this.cboTocDo_Adsl.SelectedValue = hdcon[0]['tocdo_id'] ? Number(hdcon[0]['tocdo_id']) : 0
            this.cboMucCuoc_ADSL.SelectedValue = Number(hdcon[0]['muccuoc_id'])
            this.tocdo_id = hdcon[0]['tocdo_id'] ? Number(hdcon[0]['tocdo_id']) : 0
            this.muccuoc_id = Number(hdcon[0]['muccuoc_id'])
            this.txtMaTN_ADSL.Text = hdcon[0]['matb_tn']
            this.txtSoAo_ADSL.Text = hdcon[0]['ma_lt']
            if (hdcon[0]['doitac_id']) {
              this.chkDoiTac_ADSL.Checked = true
              this.cboDoiTac_ADSL.SelectedValue = Number(hdcon[0]['doitac_id'])
            } else {
              this.chkDoiTac_ADSL.Checked = false
              this.cboDoiTac_ADSL.SelectedValue = null
              this.txtCuocDT_Adsl.Text = '0'
            }
          }
        }
        if (this.dichvuvt_id === DICHVU_VIENTHONG.TRUNGTAM_DULIEU || this.dichvuvt_id === DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
            this.dichvuvt_id === DICHVU_VIENTHONG.DICHVU_CNTT || this.dichvuvt_id === DICHVU_VIENTHONG.HOINGHI_TRUYENHINH) {
          this.initProperties(this.loaitb_id, 'HDTB_CNTT', '00000')
        }
        if (this.dichvuvt_id === DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id === DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26) {
          var hdcon = await api.lay_ds_hopdong_thuebao_mgwan(hdtbID)
          if (hdcon.length > 0) {
            this.tocdo_id = Number(hdcon[0]['tocdo_id'])
            this.cboTocDo_MGW.SelectedValue = this.tocdo_id
            this.txtSoAo_Wan.Text = hdcon[0]['ma_lt']
            this.vhuong_kn = hdcon[0]['huong_kn']
            if (hdcon[0]['huong_kn']) { this.txtHuongKN.Text = await api.lay_text_huongketnoi(this.vhuong_kn) } else { this.txtHuongKN.Text = '' }
          }
        }
        if (this.dichvuvt_id === DICHVU_VIENTHONG.IMS) {
          var hdcon = await api.lay_ds_hopdong_thuebao_ims(hdtbID)
          if (hdcon.length > 0) {
            this.pass_ims = hdcon[0]['password']
            this.txtMaTN_ADSL.Text = hdcon[0]['matb_tn']
          }
        }
        if (this.dichvuvt_id === DICHVU_VIENTHONG.GPHONE) {
          var hdcon = await api.lay_ds_hopdong_thuebao_gp(hdtbID)
          if (hdcon.length > 0) {
            this.txtSoSimGphone.Text = hdcon[0]['simcard']
          }
        }
        if (this.dichvuvt_id === DICHVU_VIENTHONG.ADSL) {
          var hdcon = await api.lay_ds_hopdong_thuebao_adsl(hdtbID)
          if (hdcon.length > 0) {
            this.pass_adsl = hdcon[0]['password']
            this.txtMaTN_ADSL.Text = hdcon[0]['matb_tn']
          }
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi : ' + cmFunc.getErrorMessage(ex))
      }
    },
    async hienThiTTDBMoRong () {
      try {
        var tocdoID = 0
        var muccuocID = 0
        if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) {
          var dbcon = await api.lay_ds_db_adsl_theo_tbid(this.thuebao_id)
          if (dbcon.length > 0) {
            // nhapt thêm việc kiểm tra tốc độ:24012017
            /// nếu tốc độ cũ của thuê bao đã hết thời hạn sử dụng
            ///  sẽ để user chọn tốc độ khác phù hợp
            ///  còn nếu vẫn còn thời hạn thì enable ô chọn tốc độ adsl
            tocdoID = dbcon[0]['tocdo_id'] ? Number(dbcon[0]['tocdo_id']) : 0
            muccuocID = Number(dbcon[0]['muccuoc_id'])
            this.cboTocDo_Adsl.SelectedValue = tocdoID
            this.tocdo_id=tocdoID
            this.trangbi_id = dbcon[0]['trangbi_id'] ? Number(dbcon[0]['trangbi_id']) : 0
            if (this.cboTocDo_Adsl.SelectedValue == null) {
              this.$toast.error('Tốc độ ADSL chưa đúng. Bạn hãy kiểm tra lại!')
              this.cboTocDo_Adsl.Enabled = true
              return
            } else {
              this.cboTocDo_Adsl.Enabled = false
            }
            this.cboMucCuoc_ADSL.SelectedValue = muccuocID
            if (!this.cboMucCuoc_ADSL.SelectedValue) {
              this.$toast.error('Mức cước ADSL chưa đúng. Bạn hãy kiểm tra lại!')
              return
            }
            this.txtSoAo_ADSL.Text = dbcon[0]['ma_lt']
            // if (dbcon[0]['tramtb_id'] != '')
            //    this.cboVeTinh_Adsl.SelectedValue = Number(dbcon[0]['tramtb_id'])
            if (dbcon[0]['doitac_id']) {
              this.chkDoiTac_ADSL.Checked = true
              this.cboDoiTac_ADSL.SelectedValue = Number(dbcon[0]['doitac_id'])
            } else {
              this.chkDoiTac_ADSL.Checked = false
              this.cboDoiTac_ADSL.SelectedValue = null
              this.txtCuocDT_Adsl.Text = '0'
            }
          }
        }
        if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26) {
          var dbcon = await api.lay_ds_danhba_megawan(this.thuebao_id)
          if (dbcon.length > 0) {
            tocdoID = Number(dbcon[0]['tocdo_id'])
            this.cboTocDo_MGW.SelectedValue = tocdoID
            this.tocdo_id = tocdoID
            this.txtSoAo_Wan.Text = dbcon[0]['ma_lt']
            this.vhuong_kn = dbcon[0]['huong_kn']
            if (dbcon[0]['huong_kn']) { this.txtHuongKN.Text = await api.lay_text_huongketnoi(this.vhuong_kn) } else { this.txtHuongKN.Text = '' }
          }
        }
        if (this.dichvuvt_id == DICHVU_VIENTHONG.TRUNGTAM_DULIEU || this.dichvuvt_id == DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
                    this.dichvuvt_id == DICHVU_VIENTHONG.DICHVU_CNTT || this.dichvuvt_id == DICHVU_VIENTHONG.HOINGHI_TRUYENHINH) {
          this.initProperties(this.loaitb_id, 'HDTB_CNTT', '00000')
        }
        if (this.dichvuvt_id == DICHVU_VIENTHONG.GPHONE) {
          var dbcon = await api.lay_ds_danhba_gp(this.thuebao_id)
          if (dbcon.length > 0) {
            // this.cboVeTinhGP.SelectedValue = Number(dbcon[0]['tramtb_id'])
            this.txtSoSimGphone.Text = dbcon[0]['simcard']
          }
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi : ' + cmFunc.getErrorMessage(ex))
      }
    },
    async hienThiDichVuKhac () {
      var str
      this.lvDvkhac.Items = []
      for (var i = 0; i < this.dsDK_DVKHAC.length; i++) {
        var ds = await api.lay_tt_dichvu_khac(Number(this.dsDK_DVKHAC[i]["dvkhac_id"]))
        str = ds // [0]['ten_dvkhac']
        if (this.dsDK_DVKHAC[i]['kieu_yc'] == 1) { str = str + ' (ĐK)' } else { str = str + ' (Hủy)' }
        this.lvDvkhac.Items.push({ id: this.dsDK_DVKHAC[i]["dvkhac_id"], text: str })
      }
    },
    async hienThiDichVuKhac_DB () {
      var str
      this.lvDvkhac.Items = []
      for (var i = 0; i < this.dsSDDVK.length; i++) {
        var ds = await api.lay_tt_dichvu_khac(Number(this.dsSDDVK[i]['dvkhac_id']))
        if (ds && ds.length && ds.length > 0) {
          str = ds // [0]['ten_dvkhac']
          this.lvDvkhac.Items.push({ id: this.dsSDDVK[i]['dvkhac_id'], text: str })
        }
      }
    },
    async kiemTraDLThueBao () {
      let kt_bc=''
      await api.kiemtra_hd_bancheo_tc(this.axios, {
        thuebao_id: this.thuebao_id,
        loaihd_id: LOAI_HOPDONG.KHOIPHUC_SD
      })
      .then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {            
          kt_bc = response.data.data            
        }
      })
      .catch((error) => {
        console.log(error)
      })      

      if (kt_bc != 'OK') {
        this.$toast.error('Lỗi: '+ kt_bc)
        return false
      }
      if (this.khachhang_id == 0) {
        this.$toast.error('Chưa có dữ liệu về khách hàng. Bạn hãy kiểm tra lại !')
        return false
      }
      if (this.thuebao_id == 0) {
        this.$toast.error('Chưa có dữ liệu về thuê bao. Bạn hãy kiểm tra lại !')
        return false
      }
      if (!this.txtSoDT.Text || this.txtSoDT.Text.trim() == '') {
        this.$toast.error('Bạn chưa nhập số điện thoại khách hàng !')
        this.$refs.txtSoDT.focus()
        return false
      }
      if (this.cboKieuLD.SelectedValue == null) {
        this.$toast.error('Hãy chọn kiểu yêu cầu cho thuê bao này !')
        return false
      }
      var mapIDKieu = await api.fn_tt_kieu_ld(this.cboKieuLD.SelectedValue, 1)
      var kieu = Number(mapIDKieu)
      if ((kieu == 1 || kieu == 2) &&
                (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL ||
                Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.CO_DINH ||
                Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.IMS) &&
                this.txtMaTN_ADSL.Text.trim().length == 0) {
        this.$toast.error('Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!')
        this.$refs.txtMaTN_ADSL.focus()
        return false
      }
      if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL || Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.BRCD_DOITAC) {
        var check_greennet = 0
        await api.get_fn_ktra_tocdo_dk_greennet(this.axios,{params:{tocdo_id: Number(this.cboTocDo_Adsl.SelectedValue)}}).then(async (response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            check_greennet = Number(response.data.data)
          } else check_greennet = 0
        })
        .catch(() => {
          check_greennet = 0
        })
        if (check_greennet == 1 ) {
          if (!this.m_dsThueBao_DichVu || !this.m_dsThueBao_DichVu.find((x) => Number(x.dangky) && x.dichvugt_id==8939)) {
            this.$toast.error('Gói cước có bao gồm dịch vụ GreenNet. Đề nghị khai báo Dịch vụ GTGT GreenNet trước khi khởi tạo gói cước!')
            return false
          }
          var check_kmtt_dvgt = this.ds_tien_khoanmuc.filter(e => e['khoanmuctt_id'].toString() == KHOANMUC_TT.KMTT_DICHVU.toString());
          if (!check_kmtt_dvgt || check_kmtt_dvgt.length <= 0) {
            // KMTT_DICHVU: 4,//Tiền dịch vụ gia tăng
            this.$toast.error('Không có khoản mục thanh toán 4 - Tiền dịch vụ gia tăng. \nThực hiện tại menu khoản mục thanh toán!')
            return false;
          }
        } else {
          if (this.m_dsThueBao_DichVu && this.m_dsThueBao_DichVu.find((x) => Number(x.dangky) && x.dichvugt_id == 8939)) {
            this.$toast.error('Tốc độ hiện tại của thuê bao không hỗ trợ đăng ký DVGT GreenNet!')
            return false
          }
        }
      }
      // if ((kieu == 1 || kieu == 2) && Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.CO_DINH
      //    && txtMaTN_ADSL.Text.trim().length == 0)
      // {
      //    this.$toast.error('Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!')
      //    txtMaTN_ADSL.focus()
      //    return false
      // }
      if (Number(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_MYTV) {
        this.$toast.error('Không khôi phục thanh lý cho thuê bao MyTV !')
        this.$refs.txtMaTB.focus()
        return false
      }
      if (this.batbuoc_batbuoc_ctv == 1) {
        if (!this.chkCTV.Checked) {
          this.$toast.error('Bạn chưa chọn cộng tác viên !')
          this.$refs.txtCTV.focus()
          return false
        }
        if (this.chkCTV.Checked && this.txtCTV.Text && this.ctv_id == 0) {
          this.$toast.error('Bạn chưa chọn cộng tác viên !')
          this.$refs.txtCTV.focus()
          return false
        }
      }
      if (Number(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_SGTV) {
        this.$toast.error('Không khôi phục thanh lý cho thuê bao SaiGonTV !')
        this.$refs.txtMaTB.focus()
        return false
      }
      // TODO: chua co api
      // if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.DICHVU_CNTT || Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT
      //     || Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.TRUNGTAM_DULIEU || Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.HOINGHI_TRUYENHINH)
      // {
      //     // Nếu là thuê bao cần chuẩn hóa --> ko cho thực hiện hợp đồng. cần phải Đặt mới (chon kieu ld = chuan hoa) hoặc làm hợp đồng Tạm dừng/Thanh lý
      //     if (check_chuahoa_cntt)
      //     {
      //         if (new LapHopDongFacade2().check_chuahoa_cntt(this.thuebao_id, LOAI_HOPDONG.KHOIPHUC_SD))
      //         {
      //             //this.$toast.error('Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới ')
      //             if (confirm('Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.\n Bạn muốn chuẩn hóa thông tin ?')) return false
      //             {
      //                 var frm = new frmDatMoi()
      //                 frm.MdiParent = this.MdiParent
      //                 frm.Show()
      //                 frm.Load_hopdong_chuanhoa_cntt(this.txtMaKH.Text.trim(), Number(this.cboDichVuVT.SelectedValue), Number(this.cboLoaihinhTB.SelectedValue), this.thuebao_id)
      //             }
      //             return false
      //         }
      //     }
      // }
      var dskt = await api.sp_lay_db_theo_matb(this.txtMaTB.Text.trim(), 0, Number(this.cboDichVuVT.SelectedValue))
      if (dskt.length > 0) {
        // var dr = dskt.Select('trangthaitb_id in(' + TrangThaiTB.THANHLY + ',' + TrangThaiTB.THANHLY_NO + ')')
        var dr = dskt.filter(r => [TRANGTHAI_TB.THANHLY, TRANGTHAI_TB.THANHLY_NO].includes(r['trangthaitb_id']))
        if (dr.length > 0) {
          if (Number(dr[0]['loaitb_id']) != Number(this.cboLoaihinhTB.SelectedValue)) {
            this.$toast.error('Không được thay đổi loại hình thuê bao của thuê bao : ' + this.txtMaTB.Text.trim())
            return false
          }
        }
      } else {
        this.$toast.error('Không tìm thấy thông tin thuê bao : ' + this.txtMaTB.Text.trim())
        return false
      }
      // var kt = await api.map_id('ma_tb', 'db_thuebao', 'where thuebao_id = ' + this.thuebao_id + '')
      // if (kt != this.txtMaTB.Text.trim()) {
      //   this.$toast.error('Bạn nhập sai dữ liệu về Mã thuê bao !')
      //   this.$refs.txtMaTB.focus()
      //   return false
      // }
      // nhapt:24/01/2017: thêm kiểm tra tốc độ
      if (this.cboTocDo_Adsl.SelectedValue == null && this.dichvuvt_id == 4) {
        this.$toast.error('Tốc độ ADSL chưa đúng. Bạn hãy kiểm tra lại!')
        return false
      }
      // bổ sung đoạn kiểm tra thông tin tính cước not null
      // yêu cầu từ jira BGG
      // Anh Hiếu HPG yêu cầu bỏ 14/07/2023
      // if ((!this.txtTTCuoc.Text || !this.txtTTCuoc.Text) && this.batbuoc_tttc_khoiphuc == 1 && this.dichvuvt_id == 4) {
      //   this.$toast.error('Chưa nhập thông tin tính cước!')
      //   this.$refs.txtTTCuoc.focus()
      //   return false
      // }
      if (this.KHONG_KHOIPHUC_TB_DUNGTHU == 1) {
        if (this.loaitb_id == LOAIHINH_THUEBAO.INTERNET_FTTH || this.loaitb_id == LOAIHINH_THUEBAO.INTERNET_MYTV) {
          if (this.doituong_id == DUNG_THU.DOITUONG_TB) {
            this.$toast.error('Không thể lập hợp đồng với thuê bao thuộc đối tượng \'Thuê bao dùng thử\'!')
            return false
          }
        }
      }
      // Không có khu vực không có tạo hợp đồng
      if (!this.dsHDTB_KV || !this.dsHDTB_KV.HDTB_KV || this.dsHDTB_KV.HDTB_KV.length == 0 || this.dsHDTB_KV.HDTB_KV[0]['khuvuc_id'] == '0') {
        this.$toast.error('Thuê bao chưa có khu vực, cần chọn khu vực')
        this.$refs.txtKhuVuc.focus()
        return false
      }
      return true
    },
    async initProperties (loaitbId, tableName, optList) {
      try {
        var ds = await api.lay_danhsach_thuoctinh_v2(loaitbId, tableName, optList)
        if (ds.length == 0) { return }
        var _list = []
        for (var i = 0; i < ds.length; i++) {
          var row = ds[i]
          var _item = {}
          _item['sFieldName'] = row['field_name']
          _item['sCaption'] = row['ten_hienthi']
          _item['iAtt'] = Number(row['att'])
          _item['iDataType'] = Number(row['data_type'])
          _item['sMaxValue'] = row['max_value']
          _item['sMinvalue'] = row['min_value']
          _item['sValue'] = row['field_value']
          _item['nFieldLength'] = !row['field_length'] ? 0 : Number(row['field_length'])
          _item['bIsNotNull'] = !row['field_nullable'] ? false : (row['field_nullable'] == "0")
          _item['bIsUnique'] = !row['field_unique'] ? false : (row['field_unique'] != "0")
          _item['sLookUpTableName'] = row['parent_table']
          _item['sLookUpKeyField'] = row['parent_keyfield']
          _item['sLookUpValueField'] = row['parent_descfield']
          _item['strDepandField'] = row['depend_field']
          _item['StrSql'] = row['lookup_sql']
          _list.push(_item)
        }
        this.ItemList = _list
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi: " + cmFunc.getErrorMessage(ex))
      }
    },
    setButton (kieu) {
      this.tsbtnNhapMoi.Enabled = false
      this.tsbtnGhiLai.Enabled = false
      this.tsbtnXoa.Enabled = false
      this.tsbtnHuyBo.Enabled = false
      this.cboTocDo_Adsl.Enabled = false
      // tsbtnInhopdong.Enabled = false
      // Bat dau
      if (kieu === -1) {
        this.$refs.txtMaTB.focus()
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
      }
      // Bat dau
      if (kieu === 0) {
        this.$refs.txtMaTB.focus()
        this.tsbtnNhapMoi.Enabled = true
        this.clear()
      }
      // Them moi
      if (kieu === 1) {
        this.$refs.txtMaTB.focus()
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.tsbtnXoaTB.Enabled = false
        this.clear()
      }
      // Huy
      if (kieu === 2) {
        this.$refs.txtMaTB.focus()
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.clear()
      }
      // Edit
      if (kieu === 3) {
        this.$refs.txtMaGD.focus()
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        // tsbtnInhopdong.Enabled = true
      }
    },
    clear () {
      // thông tin khách hàng
      // controls nay khong tim thay
      // chkDoiTac_ADSL.Checked = false
      // cboDoiTac_ADSL.EditValue = null
      this.donvi_ql_cntt = 0
      this.hdkh_id = 0
      this.txtMaGD.Text = ''
      this.txtMaHD.Text = ''
      this.txtMaKH.Text = ''
      this.khachhang_id = 0
      this.txtTenKH.Text = ''
      this.txtDiaChiKH.Text = ''
      this.cboKieuLD.SelectedValue = null
      this.txtSoDT.Text = ''
      this.chkCTV.Checked = false
      this.chkNguoiGT.Checked = false
      this.txtNguoiGT.Text = ''
      this.txtCTV.Text = ''
      this.nguoigt_id = 0
      this.ctv_id = 0
      this.pass_ims = ''
      this.pass_adsl = ''
      // thông tin thanh toán
      this.txtMaTT.Text = ''
      this.txtTenTT.Text = ''
      this.txtDiaChiTT.Text = ''
      this.dtpNgayLapHD.Value = this.$auth.getNgayCapNhat() // this.$auth.getNgayCapNhat()
      this.dtpNgayYeuCau.Value = this.$auth.getNgayCapNhat() // this.$auth.getNgayCapNhat()
      this.dtpNgayTL.Value = this.$auth.getNgayCapNhat() // this.$auth.getNgayCapNhat()
      // thông tin thuê bao
      this.vhuong_kn = ''
      this.txtSoAo_Wan.Text = ''
      this.txtHuongKN.Text = ''
      this.txtSoAo_ADSL.Text = ''
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.thuebao_cha_id = 0
      this.txtTenTB.Text = ''
      this.txtDiaChiTB.Text = ''
      this.txtDiaChiLD.Text = ''
      this.txtGhiChu.Text = ''
      this.txtMaTN_ADSL.Text = ''
      this.txtMaDoiCap_ADSL.Text = ''
      this.txtTTCuoc.Text = ''
      this.doituong_id = 0
      this.doituong_ct_id = 0
      this.khlon_id = 0
      this.dgvDanhSach.Rows = []
      this.$refs.txtMaTB.focus()
      this.lvDvgt.Items = []
      this.lvDvkhac.Items = []
      this.txtKhuVuc.Text = "";
      this.txtTongTienHD.Text = '0'
      this.dsDK_DVKHAC = []
      this.tsbtnGhiLai.count = 0;   
      // TODO
      // LAY_DS_DVGT_MACDINH(Number(cboLoaihinhTB.SelectedValue))
      // LAY_DS_DVK_MACDINH_TB(Number(cboLoaihinhTB.SelectedValue))
    },
    async onTransactionCodeChange (e) {
      try {
        this.loading(true)
        if (e.key === 'Enter') {
          if (this.txtMaGD.Text && this.txtMaGD.Text.length > 0) {            
            var ds = await api.lay_ds_hopdong_theo_ma_gd(this.txtMaGD.Text.trim(), LOAI_HOPDONG.KHOIPHUC_SD, TRANGTHAI_HOPDONG.MOI)
            await this.hienThiTTHopDongKH(ds)          
          }
        }
      } catch (err) {
        console.log(err)
        this.$toast.error(`Có lỗi: ${cmFunc.getErrorMessage(err)}`)
      } finally {
        this.loading(false)
      }
    },
    btnNhapMoiClick () {
      this.setButton(1)
    },
    btnHuyBoClick () {
      this.setButton(0)
    },
    Popup(modalId) {
      this.loading(false);
      if (this.$refs[modalId]) {
        this.$refs[modalId].show()
      } else {
        this.$bvModal.show(modalId)
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
          return response.data.data
      } else {
          console.log(response.data.error_code)
      }
      return []
    },
    async btnGhiLaiClick () {
      try {
        this.loading(true)
        this.tsbtnGhiLai.count = 0;        
        var rsKiemTraDuLieuTB = await this.kiemTraDLThueBao()
        if (!rsKiemTraDuLieuTB) {
          this.loading(false)
          return
        }
        // TODO: show dialog (tham chieu ?)
        if (this.doituong_id == DUNG_THU.DOITUONG_TB)//đối tượng dùng thử kptl -> chọn đối tượng chính thức
        {
          var doituong =  await api.sp_lay_doituong_tb(this.axios,{"nhanvienId": Number(this.$root.token.getNhanVienID())});
          if (doituong && doituong.data && doituong.data.data && doituong.data.data.length > 0) {
            var dulieu = {};
            dulieu.ds = doituong.data.data;
            dulieu.value = this.doituong_ct_id;
            dulieu.textcol = "ten_dt";
            dulieu.valuecol = "doituong_id";
            dulieu.tieudebang = "Thông tin thuê bao";
            dulieu.tieude = "CHỌN ĐỐI TƯỢNG CHÍNH THỨC";
            dulieu.allowExit = true;
            this.popupComponent = ()=>import('./components/popupChonComboBox');
            this.popupComponentName = "frmChonDoiTuong_CT";
            this.popupComponentData = dulieu
            this.Popup('popupComponents');
            return
          } else {
            this.$toast.error('Không có đối tượng tb!')
            this.loading(false)
            return
          }         
          
        } else {
          await this.capnhat();
        }
      } catch (ex) {
        this.$toast.error('Có lỗi : ' + cmFunc.getErrorMessage(ex))
      } finally {
        this.loading(false)
      }
    },
    async capnhat(){
      try {
        if (this.tsbtnNhapMoi.Enabled == false) {
          var rsLapHDKhac = await api.kiemtra_ma_tb_lap_hopdong_khac(this.txtMaTB.Text.trim(), "0", Number(this.cboDichVuVT.SelectedValue), LOAI_HOPDONG.KHOIPHUC_SD)
          rsLapHDKhac = rsLapHDKhac.filter(r => r.loaihd_id == LOAI_HOPDONG.KHOIPHUC_SD)
          if (rsLapHDKhac && rsLapHDKhac.length > 0) {
            this.$toast.error('Thuê bao đã lập hợp đồng khác: \n - Tên hợp đồng: ' + rsLapHDKhac[0]['ten_loaihd'])
            this.loading(false)
            return
          }
          var statusVdc = false
          if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL) {
            var loaihinhTB = Number(this.cboLoaihinhTB.SelectedValue)
            if (loaihinhTB == LOAIHINH_THUEBAO.INTERNET_1260 || loaihinhTB == LOAIHINH_THUEBAO.INTERNET_ADSL || loaihinhTB == LOAIHINH_THUEBAO.INTERNET_FTTH || loaihinhTB == LOAIHINH_THUEBAO.WIFI_FIBER) {
            }
            // TODO: goi service ??? object[] ketqua = vdcService.TraCuuThueBao(txtMaTB.Text.trim())
            // if (!KiemTraAccountVisa()) return
          }
          let loaikenh_id = 1
          if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26 || this.dichvuvt_id == DICHVU_VIENTHONG.TSL || this.dichvuvt_id == 25) {
            var dsDBMegaWAN = await api.lay_ds_danhba_megawan(this.thuebao_id)
            loaikenh_id = dsDBMegaWAN && dsDBMegaWAN.length > 0 ? dsDBMegaWAN[0]['loaikenh_id'] : 1
          }
          var dshg = await api.layhuonggiao_tiepnhan_v2(LOAI_HOPDONG.KHOIPHUC_SD, this.loaitb_id, this.tocdo_id, this.muccuoc_id, Number(this.cboKieuLD.SelectedValue), loaikenh_id, Number(this.cboDichVuVT.SelectedValue))
          var huonggiaoTN = 0
          this.quytrinh_id = 0
          this.luong_id = 0
          if (dshg.length <= 0) {
            this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            this.loading(false)
            return
          }
          huonggiaoTN = dshg[0]['huonggiao_id'] ? Number(dshg[0]['huonggiao_id']) : 0
          this.quytrinh_id = dshg[0]['quytrinh_id'] ? Number(dshg[0]['quytrinh_id']) : 0
          this.luong_id = dshg[0]['luong_id'] ? Number(dshg[0]['luong_id']) : 0
          var result = await this.TaoDuLieu(true)
          if (!result) {
            this.$toast.error('Lỗi tạo dữ liệu hđ thanh lý!')
            this.loading(false)
            return
          }
          if (this.loi_sinh_magd) {
            this.loi_sinh_magd = false
            this.loading(false)
            return
          }
          // #region Kích hoạt Account dùng thử lên Visa
          // TODO: goi web service ???
          // if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL)
          // {
          //     var loaihinh_tb = Number(this.cboLoaihinhTB.SelectedValue)
          //     if (loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_1260 || loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_ADSL || loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_FTTH || loaihinh_tb == LOAIHINH_THUEBAO.WIFI_FIBER)
          //     {
          //         if (DONGBO_TEST.TEST == false)
          //         {
          //             VDCService vdcService = new VDCService(tt_nd.user_visa, tt_nd.pass_visa)
          //             var account = this.txtMaTB.Text.trim()
          //             var contex = objCheckData.MAP_ID('ma_lhtb_vdc', 'loaihinh_tb', 'where loaitb_id =' + this.cboLoaihinhTB.SelectedValue.toString())
          //             //nhapt : 17/01/2018: thực hiện hủy đặt chỗ và đặt chỗ lại
          //             #region "Detail :nhapt : 17/01/2018"
          //             //vì trước khi cập nhật đã kiểm tra thuê bao đó đã được sử dụng hay chưa nên k sợ ảnh hưởng đến trường hợp đã sử dụng
          //             //nhiều trường hợp xảy ra; đặt chỗ khác phương thức hoặc user
          //             //nên chương trình k đặt chỗ lại được nữa
          //             //đã thực hiện test trên thuê bao đã đặt chỗ và chưa đặt chỗ
          //             //=> trả về thành công
          //             #endregion
          //             vdcService.HuyDatChoMegaVNN(account)
          //             statusVdc = vdcService.DatChoMegaVNN(account, contex, this.$auth.getNgayCapNhat().AddDays(7))
          //             if (!statusVdc)
          //             {
          //                 this.$toast.error('Có lỗi xảy ra trong quá trình đặt chỗ Account " + account + " lên Visa.Bạn hãy vui lòng thực hiện lại chức năng này!')
          //                 return
          //             }
          //             check_tb_adsl = true;//Hiếu thêm biến kiểm tra thuê bao ADSL đã được đặt chỗ hay chưa. 5.10.2010
          //         }
          //         else
          //         {
          //             statusVdc = true
          //             check_tb_adsl = true
          //         }
          //     }
          // }
          // #region Insert dữ liệu vào DataBase và hiển thị
          // TODO: chua co api
          // - khoiphuc.ThemMoi_HopDong_V2
          // - khoiphuc.ThemMoi_HopDong_V3
          // if (CHUYENDOI_LH_DUNGTHU == 1)
          //     khoiphuc.ThemMoi_HopDong_V3(dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, huonggiao_tn, dsHDTB_IMS, dsHDTB_KV, dsHDTB_CNTT, dsHDTB_DUNGTHU, dsHDTB_TSL)
          // else
          //     khoiphuc.ThemMoi_HopDong_V2(dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, huonggiao_tn, dsHDTB_IMS, dsHDTB_KV, dsHDTB_CNTT, dsHDTB_TSL)
          var rs = await api.themmoi_hopdong_khoiphucthanhly_v2(this.dsHDKH, this.dsHDTB, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsHDTB_MGW, this.dsDK_DVGT, this.dsDK_DVKHAC, this.dsCTTHD, this.dsHDTBDV, huonggiaoTN, this.dsHDTB_IMS, this.dsHDTB_KV, this.dsHDTB_CNTT, this.dsHDTB_TSL)
          if (rs.error_code != SUCCESS_CODE) {
            this.$toast.error(rs.message)
            this.loading(false)
            return
          }
          if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL && statusVdc == true) {
            // Gọi hàm cập nhật trạng thái HDTB = 1: Đã đặt chỗ thành công trên VDC
            // obj.CAPNHAT_STATUS_HDTB(hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
            await api.capnhat_status_hdtb(this.hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
          }
          try {
            // TODO: cho update api khoiphuc.Capnhat_goikt(dsHDTB)
            // khoiphuc.Capnhat_goikt(dsHDTB)
          } catch (Exception) {
          }
          // var dsHD = await api.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
          var dsHD = await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TRANGTHAI_HOPDONG.MOI)
          dsHD = dsHD.map(r => this.formatJson(r))
          await this.hienThiDanhSacHDTB(dsHD)
          this.setButton(3)                
          this.$toast.success('Thêm mới hợp đồng thành công !')
          this.loading(false)
          return
        } else {
          // DataSet dshg = bangts.LayHuongGiao_TiepNhan(LOAI_HOPDONG.KHOIPHUC_SD, loaitb_id, tocdo_id, muccuoc_id, Number(this.cboKieuLD.SelectedValue))
          var dshg = await api.layhuonggiao_tiepnhan_v2(LOAI_HOPDONG.KHOIPHUC_SD, this.loaitb_id, this.tocdo_id, this.muccuoc_id, Number(this.cboKieuLD.SelectedValue), 1, Number(this.cboDichVuVT.SelectedValue))
          var huonggiaoTN = 0 // TODO: đợi uncomment bên dưới
          this.quytrinh_id = 0
          this.luong_id = 0
          if (dshg.length <= 0) {
            this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            this.loading(false)
            return
          }
          huonggiaoTN = dshg[0]['huonggiao_id'] ? Number(dshg[0]['huonggiao_id']) : 0 // TODO: đợi uncomment bên dưới
          this.quytrinh_id = dshg[0]['quytrinh_id'] ? Number(dshg[0]['quytrinh_id']) : 0
          this.luong_id = dshg[0]['luong_id'] ? Number(dshg[0]['luong_id']) : 0
          var result = await this.TaoDuLieu(false)
          if (!result) {
            this.$toast.error('Lỗi tạo dữ liệu hđ thanh lý!')
            this.loading(false)
            return
          }
          // #region Đặt chỗ lại cho Account ADSL
          // TODO: goi server ???
          // if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL)
          // {
          //     var loaihinh_tb = Number(this.cboLoaihinhTB.SelectedValue)
          //     if (loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_1260 || loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_ADSL || loaihinh_tb == LOAIHINH_THUEBAO.INTERNET_FTTH || loaihinh_tb == LOAIHINH_THUEBAO.WIFI_FIBER)
          //     {
          //         if (DONGBO_TEST.TEST == false)
          //         {
          //             VDCService vdcService = new VDCService(tt_nd.user_visa, tt_nd.pass_visa)
          //             string account = this.txtMaTB.Text.trim()
          //             if (account.trim().length == 0)
          //             {
          //                 this.$toast.error('Account không được trống. Hãy kiểm tra lại!')
          //                 this.txtMaTB.focus()
          //                 return
          //             }
          //             if (!vdcService.DatChoLaiMegaVNN(account, this.$auth.getNgayCapNhat().AddDays(7)))
          //             {
          //                 this.$toast.error('Có lỗi xảy ra trong quá trình đặt chỗ lại cho Account " + account + " lên Visa.Bạn hãy vui lòng thử lại!')
          //                 return
          //             }
          //             else
          //             {
          //                 this.$toast.error('Đặt chỗ lại cho Account " + account + " lên Visa thành công!')
          //             }
          //         }
          //     }
          // }
          // #region Cập nhật dữ liệu vào DataBase và hiển thị
          // khoiphuc.CapNhat_HopDong(dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV)
          // TODO: chua co api CapNhat_HopDong & CapNhat_HopDong_v3
          // if (CHUYENDOI_LH_DUNGTHU == 1) {
          //   khoiphuc.CapNhat_HopDong_v3(dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, dsHDTB_IMS, dsHDTB_DUNGTHU)
          // } else {
          //     khoiphuc.CapNhat_HopDong(dsHDKH, dsHDTB, dsHDTB_ADSL, dsHDTB_CD, dsHDTB_GP, dsHDTB_MGW, dsDK_DVGT, dsDK_DVKHAC, dsCTTHD, dsHDTBDV, dsHDTB_IMS)
          // }
          var input = {
            dsHDKH: this.dsHDKH, 
            dsHDTB: this.dsHDTB, 
            dsHDTB_ADSL: this.dsHDTB_ADSL, 
            dsHDTB_CD: this.dsHDTB_CD, 
            dsHDTB_GP: this.dsHDTB_GP, 
            dsHDTB_MGW: this.dsHDTB_MGW, 
            dsDK_DVGT: this.dsDK_DVGT, 
            dsDK_DVKHAC: this.dsDK_DVKHAC, 
            dsCTTHD: this.dsCTTHD, 
            dsHDTBDV: this.dsHDTBDV, 
            dsHDTB_IMS: this.dsHDTB_IMS, 
            dsHDTB_DUNGTHU: this.dsHDTB_DUNGTHU,
            dsHDTB_KV: this.dsHDTB_KV
          }
          var res_capnhat = ''
          if (this.CHUYENDOI_LH_DUNGTHU == 1) {
            res_capnhat = await api.capnhat_hopdong_khoiphuc_v3(input)
          } else {
            res_capnhat = await api.capnhat_hopdong_khoiphuc(input)
          }
          
          if (!res_capnhat || res_capnhat.error_code != "BSS-00000000") {
            this.$toast.error("Lỗi cập nhật hợp đồng thuê bao Khôi phục thanh lý: " + res_capnhat.message_detail);
            this.loading(false)
            return;
          }
          if (Number(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_1260 ||
            Number(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_ADSL ||
            Number(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_FTTH ||
            Number(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.WIFI_FIBER) {
            await api.capnhat_status_hdtb(this.hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
          }
          // hoangpkn : 14/06/2014
          // Đối với loại hợp đồng có thay đổi tốc độ, mức cước
          // Phải lấy lại huonggiao_tn để update vào bảng giaophieu
          var resUp =  await api.update_huonggiao_tiepnhan(this.hdtb_id, huonggiaoTN)          
          // end hoàng thêm
          this.setButton(3)
          try {
            // TODO: dang doi update api Capnhat_goikt
            // khoiphuc.Capnhat_goikt(dsHDTB)
          } catch (ex) {
          }
          var dsHD = await api.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
          await this.hienThiDanhSacHDTB(dsHD)
          this.$toast.success('Cập nhật hợp đồng thành công !')
          this.loading(false)
          return
        }
      } catch (e) {
        this.loading(false)
      } finally {
        this.loading(false)
      }
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async popupClosed(val) {      
      this.tsbtnGhiLai.count++;
      if (this.tsbtnGhiLai.count == 1) {
        if (val != -1) {
          if(val == 190){
            this.$toast.warning("Khôi phục thanh lý \"Thuê bao dùng thử\" phải chọn đối tượng khác dùng thử!");
            return;
          }
          this.doituong_ct_id = val
          await this.capnhat() 
          this.ClosePopup('popupComponents')
          return;
        } else {
          this.$toast.warning("Khôi phục thanh lý \"Thuê bao dùng thử\" phải chọn đối tượng chính thức!");
          return;
        } 
      }      
    },
    formatJson (data) {
      var output = {}
      Object.keys(data).forEach(el => {
        if (el.indexOf('_')) {
          output[el.replace(el.substring(el.indexOf('_'), el.length), el.substring(el.indexOf('_'), el.length).toLowerCase())] = data[el]
        } else {
          output[el] = data[el]
        }
      })
      return output
    },
    async TaoDuLieu (themmoi) {
      try {
        var result = true;
        result = result == true ? await this.TaoDuLieu_HDKH(themmoi) : false
        result = result == true ? await this.TaoDuLieu_HDTB(themmoi) : false
        if (this.CHUYENDOI_LH_DUNGTHU == 1 && this.chkDungThu.Checked) {
          result = result == true ? await this.TaoDuLieu_HDTB_DUNGTHU(themmoi) : false
        }
        result = result == true ? await this.TaoDuLieu_THONGTIN_TT(themmoi) : false
        result = result == true ? await this.TaoDuLieu_CT_TIENHD(true) : false
        // //
        result = result == true ? await this.TaoDuLieu_DKDV() : false
        // CapNhat_HDTB_ID_DangKyDV()
        // CapNhat_HDTB_ID_HDTB_KV()
        this.dsHDTB_ADSL = []
        this.dsHDTB_TSL = []
        this.dsHDTB_CD = []
        this.dsHDTB_GP = []
        this.dsHDTB_IMS = []
        this.dsHDTB_MGW = []
        this.dsHDTBDV = []
        this.dsHDTB_CNTT = []
        result = result == true ? await this.TaoDuLieu_HDTBDV(true) : false
        switch (this.dichvuvt_id) {
          case DICHVU_VIENTHONG.CO_DINH:
            result = result == true ? await this.TaoDuLieu_HDTB_CD(themmoi) : false
            break
          case DICHVU_VIENTHONG.IMS:
            result = result == true ? await this.TaoDuLieu_HDTB_IMS(themmoi) : false
            break
          case DICHVU_VIENTHONG.ADSL:
            result = result == true ? await this.TaoDuLieu_HDTB_ADSL(themmoi) : false
            break
          case DICHVU_VIENTHONG.GPHONE:
            result = result == true ? await this.TaoDuLieu_HDTB_GP(themmoi) : false
            break
          case DICHVU_VIENTHONG.MEGAWAN:
          case DICHVU_VIENTHONG.METRONET:
          case 26:
            result = result == true ? await this.TaoDuLieu_HDTB_MGWAN(themmoi) : false
            break
          case DICHVU_VIENTHONG.TSL:
          case 25:
            result = result == true ? await this.TaoDuLieu_HDTB_TSL(themmoi) : false
            break
          case DICHVU_VIENTHONG.DICHVU_CNTT:
          case DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT:
          case DICHVU_VIENTHONG.TRUNGTAM_DULIEU:
          case DICHVU_VIENTHONG.HOINGHI_TRUYENHINH:
            result = result == true ? await this.TaoDuLieu_HDTB_CNTT(themmoi) : false
            break
          default:
            break
        }
        return result
      } catch (e) {
        return false
      }
    },
    async TaoDuLieu_HDKH (themmoi) {
      try {
        var vmagd_temp = ''
        this.dsHDKH = []
        if (themmoi == true) { this.hdkh_id = await api.get_keys('HD_KHACHHANG') }
        var rowHDKH = {}
        // var dsDBKH = await api.lay_ds_dbkh_theo_khid(this.khachhang_id)
        // // cmUlt.CopyRow(tcdanhba.LAY_DS_DBKH_THEO_KHID(khachhang_id).Tables[0].Rows[0], rowHDKH)
        // if (dsDBKH.length > 0) {
        //   rowHDKH = {...dsDBKH[0]}
        // }
        rowHDKH.HDKH_ID = this.hdkh_id
        if (themmoi == true) {
          if (this.ts_sinhma_gd_theo_donvi) {
            vmagd_temp = await api.sinh_magd_v2(this.$auth.getDonViID(), LOAI_HOPDONG.KHOIPHUC_SD)
          } else {
            vmagd_temp = await api.sinh_magd(LOAI_HOPDONG.KHOIPHUC_SD)
          }
          // TODO: kiem tra ket qua tra ve cua api sinh_magd & sinh_magd_v2
          // if (vmagd_temp.includes("ERROR:"))
          // {
          //     this.$toast.success(vmagd_temp.split(':')[1])
          //     loi_sinh_magd = true
          // }
          this.txtMaGD.Text = vmagd_temp
          if (!this.txtMaHD.Text) {
            var rsMaHD = await api.lay_ma_hd(this.$auth.getDonViID())
            this.txtMaHD.Text = rsMaHD.ma_hd || ''
          }
          rowHDKH.MA_GD = this.txtMaGD.Text
          rowHDKH.MA_HD = this.txtMaHD.Text
          rowHDKH.NGAY_YC = this.$auth.getNgayCapNhat()
        } else {
          rowHDKH.MA_GD = this.txtMaGD.Text
          rowHDKH.MA_HD = this.txtMaHD.Text
          rowHDKH.NGAY_YC = this.dtpNgayYeuCau.Value
        }
        if (this.chkCTV.Checked && this.txtCTV.Text) { rowHDKH.CTV_ID = this.ctv_id }
        if (this.chkNguoiGT.Checked && this.txtNguoiGT.Text) { rowHDKH.NHANVIENGT_ID = this.nguoigt_id }
        rowHDKH.MA_KH = this.txtMaKH.Text
        rowHDKH.KHACHHANG_ID = this.khachhang_id
        rowHDKH.TEN_KH = this.txtTenKH.Text
        rowHDKH.DIACHI_KH = this.txtDiaChiKH.Text
        rowHDKH.KHLON_ID = this.khlon_id// 1
        rowHDKH.DONVI_ID = await this.$auth.getDonViID()
        rowHDKH.NHANVIEN_ID = await this.$auth.getNhanVienID()
        rowHDKH.LOAIHD_ID = LOAI_HOPDONG.KHOIPHUC_SD
        rowHDKH.NGUOI_CN = await this.$auth.getMaNhanVien()
        rowHDKH.NGAY_CN = await this.$auth.getNgayCapNhat()
        rowHDKH.MAY_CN = await this.$auth.getMachine()
        rowHDKH.IP_CN = await this.$auth.getIP()
        rowHDKH.NGAYLAP_HD = this.dtpNgayLapHD.Value
        rowHDKH.SO_DT = this.txtSoDT.Text
        if (this.hdkh_cha_id > 0) {
          rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
          this.hdkh_cha_id = 0
        }
        // PNLINH B2A 02/05/2020
        if (this.VPHIEUCSKH_ID != 0) {
          rowHDKH.PHIEUCSKH_ID = this.VPHIEUCSKH_ID
        }
        this.dsHDKH.push(rowHDKH)
        return true
      } catch (e) {
        return false
      }
    },
    async TaoDuLieu_HDTB (themmoi) {
      try {
        this.dsHDTB = []
        if (themmoi == true) {
          this.hdtb_id = await api.get_keys("HD_THUEBAO")
          if (this.dsHDTB_KV && this.dsHDTB_KV.HDTB_KV && this.dsHDTB_KV.HDTB_KV.length > 0) {
            for (var item of this.dsHDTB_KV.HDTB_KV) {
              item['HDTB_ID'] = this.hdtb_id
            }
          }
        }
        var rowHDTB = {}
        rowHDTB['HDTB_ID'] = this.hdtb_id
        rowHDTB['HDKH_ID'] = this.hdkh_id
        rowHDTB['THUEBAO_ID'] = this.thuebao_id
        if (this.thuebao_cha_id != 0) { rowHDTB['THUEBAO_CHA_ID'] = this.thuebao_cha_id }
        rowHDTB['MA_TB'] = this.txtMaTB.Text
        rowHDTB['TEN_TB'] = this.txtTenTB.Text
        rowHDTB['DIACHI_TB'] = this.txtDiaChiTB.Text
        rowHDTB['KIEULD_ID'] = Number(this.cboKieuLD.SelectedValue)
        rowHDTB['LOAITB_ID'] = Number(this.cboLoaihinhTB.SelectedValue)
        if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL) {
          rowHDTB['DONVI_ID'] = Number(this.cboDaiVT_ADSL.SelectedValue)
        } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.CO_DINH) {
          rowHDTB['DONVI_ID'] = Number(this.cboDaiVT_CD.SelectedValue)
        } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.IMS) {
          rowHDTB['DONVI_ID'] = Number(this.cboDaiVT_IMS.SelectedValue)
        } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.GPHONE) {
          rowHDTB['DONVI_ID'] = Number(this.cboDVQLGP.SelectedValue)
        } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.MEGAWAN ||
                  Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.METRONET || 
                  Number(this.cboDichVuVT.SelectedValue) == 26) {
          rowHDTB['DONVI_ID'] = Number(this.cboDVQL_MGW.SelectedValue)
        } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
                  Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.DICHVU_CNTT ||
                  Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.HOINGHI_TRUYENHINH ||
                  Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.TRUNGTAM_DULIEU) {
          rowHDTB['DONVI_ID'] = this.donvi_ql_cntt
        } else if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.TSL || Number(this.cboDichVuVT.SelectedValue) == 25) {
          rowHDTB['DONVI_ID'] = Number(this.cboDaiVT_CD.SelectedValue)
        }
        rowHDTB['DOITUONG_ID'] = this.doituong_id
        if (this.CHUYENDOI_LH_DUNGTHU == 1 && this.chkDungThu.Checked) { rowHDTB['DOITUONG_ID'] = DUNG_THU.DOITUONG_TB }
        rowHDTB['TTHD_ID'] = TRANGTHAI_HOPDONG.MOI
        rowHDTB['DICHVUVT_ID'] = Number(this.cboDichVuVT.SelectedValue)
        rowHDTB['KIEU_TB'] = '0' // Kiểu thuê bao thường
        rowHDTB['NGUOI_CN'] = await this.$auth.getMaNhanVien()
        rowHDTB['NGAY_CN'] = await this.$auth.getNgayCapNhat()
        rowHDTB['MAY_CN'] = await this.$auth.getMachine()
        // rowHDTB['DUNGTHU'] = 0
        // HungHM-27/06/2010: bổ xung thêm các column cho bảng HD_ThueBao
        rowHDTB['DIACHI_LD'] = this.txtDiaChiLD.Text
        rowHDTB['GHICHU'] = this.txtGhiChu.Text
        rowHDTB['STATUS'] = TRANGTHAI_DONGBO.CHUADONGBO.toString()
        var rsktt = await api.kt_thaotac(this.luong_id, 'STATUS_TONGDAI_CD')
        if (!rsktt || rsktt.error_code != "BSS-00000000") {
          this.$toast.error(rsktt.message_detail);
          return false;
        }
        if (Number(rsktt.data) > 0) { rowHDTB['STATUS'] = "0" }
        rowHDTB['QUYTRINH_ID'] = this.quytrinh_id
        if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.CO_DINH) {
          if (this.cboMucCuocTB_CD.Items.length > 0) {
            rowHDTB['MUCUOCTB_ID'] = Number(this.cboMucCuocTB_CD.SelectedValue)
            var rsMapId = await api.fn_tt_muccuoc_tb(this.cboMucCuocTB_CD.SelectedValue, 1)
            rowHDTB['MUCUOC_TB'] = parseFloat(rsMapId)
          }
        }
        if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL) {
          if (this.cboMucCuocTB_ADSL.Items.length > 0) {
            rowHDTB['MUCUOCTB_ID'] = Number(this.cboMucCuocTB_ADSL.SelectedValue)
            var rsMapId = await api.fn_tt_muccuoc_tb(this.cboMucCuocTB_ADSL.SelectedValue, 1)
            rowHDTB['MUCUOC_TB'] = parseFloat(rsMapId)
          }
        }
        if (this.doituong_id == DUNG_THU.DOITUONG_TB && this.doituong_ct_id != 0 && this.doituong_ct_id != undefined) { // đối tượng dùng thử kptl -> đổi đối tượng luôn
          rowHDTB['DOITUONG_ID'] = this.doituong_ct_id
        }
        this.dsHDTB.push(rowHDTB)
        return true
      } catch (e) { return false }
    },
    async TaoDuLieu_HDTB_DUNGTHU () {
      try {
        this.dsHDTB_DUNGTHU = []
        var rowHDTB = {}
        rowHDTB['HDTB_ID'] = this.hdtb_id
        rowHDTB['SONGAY'] = 30
        rowHDTB['MAY_CN'] = await this.$auth.getMachine()
        rowHDTB['NGAY_CN'] = await this.$auth.getNgayCapNhat()
        rowHDTB['NGUOI_CN'] = await this.$auth.getMaNhanVien()
        rowHDTB['IP_CN'] = await this.$auth.getIP()
        this.dsHDTB_DUNGTHU.push(rowHDTB)
        return true
      } catch (ex) {
        console.error(ex)
        this.$toast.error(cmFunc.getErrorMessage(ex))
        return false
      }
    },
    async TaoDuLieu_CT_TIENHD (themmoi) {
      try {
        this.dsCTTHD = []
        var rowCTTHD = {}
        for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DICHVU) {
            for (var j = 0; j < this.m_dsThueBao_DichVu.length; j++) {
              if (this.m_dsThueBao_DichVu[j]['dangky'] == "1" && this.m_dsThueBao_DichVu[j]['huy'] == "0") {
                rowCTTHD = {}
                rowCTTHD['HDTB_ID'] = this.hdtb_id
                if (themmoi) {
                  this.CT_TIENHD_ID = await api.get_keys('CT_TIENHD')
                  rowCTTHD['CT_TIENHD_ID'] = this.CT_TIENHD_ID
                }
                rowCTTHD['KHOANMUCTT_ID'] = KHOANMUC_TT.KMTT_DICHVU
                var tien = 0// Tiền dịch vụ chưa khuyến mại
                var vat = 0// Vat dịch vụ chưa khuyến mại
                tien = parseFloat(this.m_dsThueBao_DichVu[j]['cuoc_ld'])
                rowCTTHD['TIEN'] = tien
                vat = parseFloat(this.m_dsThueBao_DichVu[j]['vat_ld'])
                rowCTTHD['VAT'] = vat
                rowCTTHD['ID'] = Number(this.m_dsThueBao_DichVu[j]['dichvugt_id'])
                rowCTTHD['LOAI_ID'] = 2
                this.dsCTTHD.push(rowCTTHD)
              }
            }
          } else {
            rowCTTHD = {}
            rowCTTHD['HDTB_ID'] = this.hdtb_id
            if (themmoi == true) {
              this.CT_TIENHD_ID = await api.get_keys('CT_TIENHD')
              rowCTTHD['CT_TIENHD_ID'] = this.CT_TIENHD_ID
            }
            rowCTTHD['KHOANMUCTT_ID'] = Number(this.ds_tien_khoanmuc[i]['khoanmuctt_id'])
            rowCTTHD['TIEN'] = parseFloat(this.ds_tien_khoanmuc[i]['tien'])
            rowCTTHD['VAT'] = parseFloat(this.ds_tien_khoanmuc[i]['vat'])
            rowCTTHD['ID'] = Number(this.ds_tien_khoanmuc[i]['khoanmuctt_id'])
            rowCTTHD['LOAI_ID'] = 1
            this.dsCTTHD.push(rowCTTHD)
          }
        }
        return true
      } catch (e) { return false }
    },
    TaoDuLieu_DKDV () {
      try {
        this.dsDK_DVGT = []
        var kieu_dk_dvgt
        for (var dr of this.m_dsThueBao_DichVu) {
          if (dr['dangky'] == "1") {
            kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT
          } else if (dr['huy'] == "1") {
            kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT
          } else {
            continue
          }
          var rowHDTDDV = {}
          rowHDTDDV['HDTB_ID'] = this.hdtb_id
          rowHDTDDV['NGAY_YC'] = this.dtpNgayYeuCau.Value
          rowHDTDDV['DICHVUGT_ID'] = Number(dr['dichvugt_id'])
          rowHDTDDV['NOIDUNG'] = dr['noidung']
          rowHDTDDV['KIEU_YC'] = kieu_dk_dvgt
          rowHDTDDV['TTTH'] = 1
          this.dsDK_DVGT.push(rowHDTDDV)
        }
        return true
      } catch (e) { return false }
    },
    async TaoDuLieu_THONGTIN_TT (themmoi) {return true},
    async TaoDuLieu_HDTBDV (themmoi) {
      try {
        this.dsHDTBDV = []
        var dtTemp = await api.LAY_DONVI_THEO_LOAIDV_DIACHI_LD(this.thuebao_id)
        for (var i = 0; i < dtTemp.length; i++) {
          var row = {}
          row.HDTB_ID = this.hdtb_id
          row.LOAIDV_ID = Number(dtTemp[i]["loaidv_id"])
          row.DONVI_ID = Number(dtTemp[i]["donvi_id"])
          row.KIEUDV_ID = Number(dtTemp[i]["kieudv_id"])
          if (Number(dtTemp[i]["loaidv_id"]) == LOAI_DONVI.TRAM_VT &&
                Number(this.cboDichVuVT.SelectedValue) != DICHVU_VIENTHONG.TSL && Number(this.cboDichVuVT.SelectedValue) != 25) {
            if (this.dichvuvt_id == DICHVU_VIENTHONG.CO_DINH) { row.DONVI_ID = Number(this.cboTram_CD.SelectedValue) }
            if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) { row.DONVI_ID = Number(this.cboVeTinh_Adsl.SelectedValue) }
            if (this.dichvuvt_id == DICHVU_VIENTHONG.IMS) { row.DONVI_ID = Number(this.cboTram_IMS.SelectedValue) }
            if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26) { row.DONVI_ID = Number(this.cboTram_MGW.SelectedValue) }
            if (row.DONVI_ID == 0) {
              this.$toast.error('Bạn chưa chọn đơn vị Trạm VT!')
              return false
            }
          }
          this.dsHDTBDV.push(row)
        }
        return true
      } catch (e) { return false }
    },
    async TaoDuLieu_HDTB_CD (themmoi) {
      try {
        var dscd = await api.lay_ds_db_cd_theo_tbid(this.thuebao_id)
        this.dsHDTB_CD = []
        var rowHDTB_CD = {}
        rowHDTB_CD['HDTB_ID'] = this.hdtb_id
        rowHDTB_CD['MATB_TN'] = this.txtMaTN_ADSL.Text
        if (dscd.length > 0) {
          if (dscd[0]['ne_id'] != "") { rowHDTB_CD['NE_ID'] = Number(dscd[0]['ne_id']) }
          if (dscd[0]['ne'] != "") { rowHDTB_CD['NE'] = dscd[0]['ne'] }
          if (dscd[0]['tuyencap'] != "") { rowHDTB_CD['TUYENCAP'] = dscd[0]['tuyencap'] }
          if (dscd[0]['lientu'] != "") { rowHDTB_CD['LIENTU'] = dscd[0]['lientu'] }
          if (dscd[0]['doicap_p'] != "") { rowHDTB_CD['DOICAP_P'] = Number(dscd[0]['doicap_p']) }
          if (dscd[0]['hopcap_p'] != "") { rowHDTB_CD['HOPCAP_P'] = dscd[0]['hopcap_p'] }
          if (dscd[0]['tucap_p'] != "") { rowHDTB_CD['TUCAP_P'] = dscd[0]['tucap_p'] }
          if (dscd[0]['loaicap_id'] != "") { rowHDTB_CD['LOAICAP_ID'] = Number(dscd[0]['loaicap_id']) }
          if (dscd[0]['cap_goc'] != "") { rowHDTB_CD['CAP_GOC'] = dscd[0]['cap_goc'] }
          if (dscd[0]['cap_p'] != "") { rowHDTB_CD['CAP_P'] = dscd[0]['cap_p'] }
          if (dscd[0]['doicap_goc'] != "") { rowHDTB_CD['DOICAP_GOC'] = Number(dscd[0]['doicap_goc']) }
          if (dscd[0]['tucap_goc'] != "") { rowHDTB_CD['TUCAP_GOC'] = dscd[0]['tucap_goc'] }
          if (dscd[0]['tramtb_id'] != "") { rowHDTB_CD['TRAMTB_ID'] = Number(dscd[0]['tramtb_id']) }
        }
        var mapID = await api.fn_tt_kieu_ld(this.cboKieuLD.SelectedValue, 1)
        var kieu = Number(mapID)
        // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
        if (kieu == 1 || kieu == 2) {
          if (this.txtMaDoiCap_ADSL.Text.toString().trim().length == 0) {
            this.$toast.warning("Đối kiểu lắp đặt là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.")
            return false
          }
          rowHDTB_CD['MADOICAP'] = Number(this.txtMaDoiCap_ADSL.Text.toString().trim())
        } else { // TH còn lại sinh mã đôi cáp
          if (themmoi == true) {
            rowHDTB_CD['MADOICAP'] = await api.get_keys("MADOICAP")
            this.txtMaDoiCap_ADSL.Text = rowHDTB_CD['MADOICAP']
          } else {
            if (this.txtMaDoiCap_ADSL.Text.toString().trim().length > 0) {
              // Trong trường hợp : thêm mới kiểu lắp trên đường có sẵn hoặc lắp kèm, sau đó ấn ghi lại chọn trên đường ko số
              // => kiểm tra xem đã có thuê bao nào ăn vào mã đôi cáp này hay chưa
              // Nếu có thuê bao ăn vào rồi thì pải sinh mã đôi cáp mới
              // TODO: chua co api laphd.Kiemtra_thuebao_sd_madoicap
              var rs = 0 // await laphd.Kiemtra_thuebao_sd_madoicap(Number(this.txtMaDoiCap_ADSL.Text))
              if (rs != 0) {
                rowHDTB_CD['MADOICAP'] = await api.get_keys("MADOICAP")
                this.txtMaDoiCap_ADSL.Text = rowHDTB_CD['MADOICAP']
              } else {
                rowHDTB_CD['MADOICAP'] = parseFloat(this.txtMaDoiCap_ADSL.Text)
              }
            } else { // Trường hợp trên text chưa có mã đôi cáp thì sinh mã đôi cáp khác
              rowHDTB_CD['MADOICAP'] = await api.get_keys("MADOICAP")
              this.txtMaDoiCap_ADSL.Text = rowHDTB_CD['MADOICAP']
            }
          }
        }
        this.dsHDTB_CD.push(rowHDTB_CD)
        return true
      } catch (e) { return false }
    },
    async TaoDuLieu_HDTB_IMS (themmoi) {
      try {
        this.dsHDTB_IMS = []
        var rowHDTB_IMS = {}
        var dsDBIMS = await api.lay_ds_db_ims_theo_tbid(this.thuebao_id)
        if (dsDBIMS.length > 0) {
          cmFunc.CopyRowAndUppercase(dsDBIMS[0], rowHDTB_IMS)
        }
        rowHDTB_IMS['HDTB_ID'] = this.hdtb_id
        rowHDTB_IMS['MATB_TN'] = this.txtMaTN_ADSL.Text // Tú thêm do không update được Mã TN
        // TODO: chua co api cho query nay
        // var _dt = bangts.getDataFromSQL("select dauso_ims from " + DatabaseConstants.DB2 + ".tinh where tentat = '" + DatabaseConstants.DB2.Substring(4) + "'", "tinh").Tables[0]
        // rowHDTB_IMS['USERNAME'] = "84" + _dt.Rows[0][0].toString() + this.txtMaTB.Text.trim()
        // if (themmoi) {
        //   this.pass_ims = ""
        //   if (this.tusinh_pass_ims == 100) {
        //     if (this.matkhau_md_ims != "") { this.pass_ims = this.matkhau_md_ims + this.txtMaTB.Text.trim() } else { this.pass_ims = CommonFunction.GetRandomText(11) }
        //   } else if (this.tusinh_pass_ims == 50) {
        //     this.pass_ims = CommonFunction.GetRandomText(11)
        //   } else {
        //     if (this.matkhau_md_ims != "") { this.pass_ims = this.matkhau_md_ims } else {
        //       if (this.tusinh_pass_ims == 0) { this.pass_ims = "84" + _dt.Rows[0][0].toString() + this.txtMaTB.Text.trim() } else { this.pass_ims = CommonFunction.GetRandomText(11) }
        //     }
        //   }
        // }
        // rowHDTB_IMS['PASSWORD'] = this.pass_ims
        // rowHDTB_IMS['MATB_TN'] = this.txtMaTN_ADSL.Text
        // // Lamnt: Bổ sung lấy thông tin mã đôi cáp nếu khôi phục kèm, khôi phục trên đg có sẵn
        // var id = await api.map_id("kieu", "kieu_ld", "where kieuld_id =" + this.cboKieuLD.SelectedValue)
        // var kieu = Number(id)
        // // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
        // if (kieu == 1 || kieu == 2) {
        //   if (this.txtMaDoiCap_ADSL.Text.trim().length == 0) {
        //     this.$toast.warning("Đối kiểu lắp đặt là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.")
        //     return
        //   }
        //   rowHDTB_IMS['MADOICAP'] = Number(this.txtMaDoiCap_ADSL.Text)
        // }
        this.dsHDTB_IMS.push(rowHDTB_IMS)
        return true
      } catch (e) { return false }
    },
    async TaoDuLieu_HDTB_ADSL (themmoi) {
      try {
        var dsadsl = await api.lay_ds_db_adsl_theo_tbid(this.thuebao_id)
        this.dsHDTB_ADSL = []
        var rowHDTB_ADSL = {}
        rowHDTB_ADSL['HDTB_ID'] = this.hdtb_id
        rowHDTB_ADSL['TOCDO_ID'] = Number(this.cboTocDo_Adsl.SelectedValue)
        rowHDTB_ADSL['MUCCUOC_ID'] = Number(this.cboMucCuoc_ADSL.SelectedValue)
        // nhapt 03082017: thêm thông tin tính cước
        rowHDTB_ADSL['THONGTIN_TC'] = this.txtTTCuoc.Text
        if (dsadsl.length > 0) {
          if (dsadsl[0]["dslam_id"] != "") { rowHDTB_ADSL['DSLAM_ID'] = Number(dsadsl[0]["dslam_id"]) }
          if (dsadsl[0]["bras_id"] != "") { rowHDTB_ADSL['BRAS_ID'] = Number(dsadsl[0]["bras_id"]) }
          if (dsadsl[0]["port_id"] != "") { rowHDTB_ADSL['PORT_ID'] = Number(dsadsl[0]["port_id"]) }
          if (dsadsl[0]["vci_vpi_id"] != "") { rowHDTB_ADSL['VCI_VPI_ID'] = Number(dsadsl[0]["vci_vpi_id"]) }
          if (dsadsl[0]["ma_lt"] != "") { rowHDTB_ADSL['MA_LT'] = dsadsl[0]["ma_lt"] }
          if (dsadsl[0]["kieutra_id"] != "") { rowHDTB_ADSL['KIEUTRA_ID'] = Number(dsadsl[0]["kieutra_id"]) }
          if (dsadsl[0]["thang_id"] != "") { rowHDTB_ADSL['THANG_ID'] = Number(dsadsl[0]["thang_id"]) }
          if (dsadsl[0]["ngaytra"] != "") { rowHDTB_ADSL['NGAYTRA'] = dsadsl[0]["ngaytra"] }
          if (this.pass_adsl == "") { // lấy pass cũ khi chưa có pass
            this.pass_adsl = dsadsl[0]["password"]
          }
          if (dsadsl[0]["tramtb_id"] != "") { rowHDTB_ADSL['TRAMTB_ID'] = Number(dsadsl[0]["tramtb_id"]) }
        }
        // SonNMP 15 July 2019: sinh pass ADSL tự động theo yêu cầu của QBH, tránh trường hợp kích hoạt lên Visa thất bại do pass rỗng
        // Luôn tạo mới mật khẩu khi mật khẩu cũ rỗng
        if (!this.pass_adsl) {
          if (this.matkhau_md_adsl) {
            if (this.matkhau_md_adsl == "random_sql") { this.pass_adsl = CommonFunction.GetRandomText(11) } else { this.pass_adsl = this.matkhau_md_adsl }
          } else {
            if (this.sinh_TD_matkhau_adsl == 1) {
              var partsDot = this.txtMaTB.Text.split('.')
              var partsDash = this.txtMaTB.Text.split('-')
              this.pass_adsl = this.txtMaTB.Text.includes(".") ? partsDot[partsDot.length - 1] : ""
              if (!this.pass_adsl) { this.pass_adsl = this.txtMaTB.Text.includes("-") ? partsDash[partsDash.length - 1] : "" }
            } else { this.pass_adsl = CommonFunction.GetRandomText(8) }
          }
        }
        rowHDTB_ADSL['PASSWORD'] = this.pass_adsl
        var loaitb_id = Number(this.cboLoaihinhTB.SelectedValue)
        if (loaitb_id == LOAIHINH_THUEBAO.INTERNET_1260 || loaitb_id == LOAIHINH_THUEBAO.INTERNET_ADSL ||
                  loaitb_id == LOAIHINH_THUEBAO.INTERNET_FTTH || loaitb_id == LOAIHINH_THUEBAO.WIFI_FIBER) {
          var d = moment(this.$auth.getNgayCapNhat(), 'DD/MM/YYYY')
          if (!d.isValid()) {
            d = moment()
          }
          rowHDTB_ADSL['HANDATCHO'] = d.add(7, 'days').format("DD/MM/YYYY HH:mm:ss")
        }
        // HungHM - Bổ xung thêm mã truy nhập + mã đôi cáp
        rowHDTB_ADSL['MATB_TN'] = this.txtMaTN_ADSL.Text
        // rowHDTB_ADSL['MADOICAP'] = await api.get_keys("MADOICAP")
        var id = await api.fn_tt_kieu_ld(this.cboKieuLD.SelectedValue, 1)
        var kieu = Number(id)
        // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
        if (kieu == 1 || kieu == 2) {
          if (this.txtMaDoiCap_ADSL.Text.toString().trim().length == 0) {
            this.$toast.warning("Đối kiểu lắp đặt là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.")
            return false
          }
          rowHDTB_ADSL['MADOICAP'] = Number(this.txtMaDoiCap_ADSL.Text.toString().trim())
        } else { // TH còn lại sinh mã đôi cáp
          if (themmoi == true) {
            rowHDTB_ADSL['MADOICAP'] = await api.get_keys("MADOICAP")
            this.txtMaDoiCap_ADSL.Text = rowHDTB_ADSL['MADOICAP']
          } else {
            if (this.txtMaDoiCap_ADSL.Text.toString().trim().length > 0) {
              // Trong trường hợp : thêm mới kiểu lắp trên đường có sẵn hoặc lắp kèm, sau đó ấn ghi lại chọn trên đường ko số
              // => kiểm tra xem đã có thuê bao nào ăn vào mã đôi cáp này hay chưa
              // Nếu có thuê bao ăn vào rồi thì pải sinh mã đôi cáp mới
              // TODO: chua co api Kiemtra_thuebao_sd_madoicap
              // if (laphd.Kiemtra_thuebao_sd_madoicap(Number(this.txtMaDoiCap_ADSL.Text)) != 0) {
              //   rowHDTB_ADSL['MADOICAP'] = await api.get_keys("MADOICAP")
              //   this.txtMaDoiCap_ADSL.Text = rowHDTB_ADSL['MADOICAP'].toString()
              // } else {
              rowHDTB_ADSL['MADOICAP'] = parseFloat(this.txtMaDoiCap_ADSL.Text)
              // }
            } else { // Trường hợp trên text chưa có mã đôi cáp thì sinh mã đôi cáp khác
              rowHDTB_ADSL['MADOICAP'] = await api.get_keys("MADOICAP")
              this.txtMaDoiCap_ADSL.Text = rowHDTB_ADSL['MADOICAP']
            }
          }
        }
        if (this.chkDoiTac_ADSL.Checked) {
          rowHDTB_ADSL['USERNAME'] = this.txtMaTB.Text.trim()
          // TODO: dsDanhMuc k có trường username_sub
          // if (this.cboDoiTac_ADSL.SelectedValue) {
          //   var dsDT = this.dsDanhMuc.filter(r => r.doitac_id == Number(this.cboDoiTac_ADSL.SelectedValue))
          //   rowHDTB_ADSL['USERNAME'] = this.txtMaTB.Text.trim() + dsDanhMuc.Tables["DOITAC"].Select("doitac_id = " + Number(this.cboDoiTac_ADSL.EditValue)).CopyToDataTable().Rows[0]["username_sub"].toString().trim()
          // }
          rowHDTB_ADSL['DOITAC_ID'] = Number(this.cboDoiTac_ADSL.SelectedValue)
          if (this.txtCuocDT_Adsl.Text) {
            rowHDTB_ADSL['CUOC_DOITAC'] = parseFloat(CommonFunction.Unformat_tien(this.txtCuocDT_Adsl.Text))
          } else {
            rowHDTB_ADSL['CUOC_DOITAC'] = 0
          }
        } else {
          rowHDTB_ADSL['USERNAME'] = this.txtMaTB.Text.trim()
          rowHDTB_ADSL['CUOC_DOITAC'] = 0
        }
        this.dsHDTB_ADSL.push(rowHDTB_ADSL)
        return true
      } catch (e) { return false }
    },
    TaoDuLieu_HDTB_GP (themmoi) {
      try {
        this.dsHDTB_GP = []
        var rowHDTB_GP = {}
        rowHDTB_GP['HDTB_ID'] = this.hdtb_id
        rowHDTB_GP['SIMCARD'] = this.txtSoSimGphone.Text
        this.dsHDTB_GP.push(rowHDTB_GP)
        return true
      } catch (e) { return false }
    },
    async TaoDuLieu_HDTB_MGWAN (themmoi) {
      try {
        this.dsHDTB_MGW = []
        var dsDBMegaWAN = await api.lay_ds_danhba_megawan(this.thuebao_id)
        var rowHDTB_MGW = {}
        if (dsDBMegaWAN.length > 0) {
          // rowHDTB_MGW = {...dsDBMegaWAN[0]}
          cmFunc.CopyRowAndUppercase(dsDBMegaWAN[0], rowHDTB_MGW)
        }
        rowHDTB_MGW['HDTB_ID'] = this.hdtb_id
        this.dsHDTB_MGW.push(rowHDTB_MGW)
        return true
      } catch (e) { return false }
    },
    async TaoDuLieu_HDTB_TSL (themmoi) {
      try { 
        this.dsHDTB_TSL = []
        var ds_tsl = await api.lay_ds_danhba_tsl(this.thuebao_id)
        if (ds_tsl.length > 0) {
          for (var i = 0; i < ds_tsl.length; i++) {
            var rowHDTB_TSL = {}
            cmFunc.CopyRowAndUppercase(ds_tsl[i], rowHDTB_TSL)
            rowHDTB_TSL.HDTB_ID = this.hdtb_id
            this.dsHDTB_TSL.push(rowHDTB_TSL)
          }
        }
        return true
      } catch (e) { return false }
    },
    async TaoDuLieu_HDTB_CNTT (themmoi) {
      try {
        var ds = await api.lay_ds_danhba_cntt(this.thuebao_id)
        if (ds.length > 0) {
          for (var i = 0; i < this.ItemList.length; i++) {
            var fieldName = this.ItemList[i]['sFieldName']
            if (fieldName == 'MA_TB') continue
            var value = ds[0][fieldName.toLowerCase()]
            if (value != undefined) {
              this.ItemList[i].sValue = value
            }
          }
        }
        this.dsHDTB_CNTT = [...this.ItemList]
        return true
      } catch (e) { return false }
    },
    CapNhat_HDTB_ID_DangKyDV () {
      // var ds2 = this.dsDK_DVKHAC
      // dsDK_DVKHAC = new DANGKY_DVK_DATA()
      // cmUlt.CopyDataSet(ds2, dsDK_DVKHAC)
      for (var i = 0; i < this.dsDK_DVKHAC.length; i++) {
        this.dsDK_DVKHAC[i]["hd_id"] = this.hdtb_id
      }
    },
    LAY_DS_DVGT_MACDINH: async function () {
      if (!this.cboLoaihinhTB.SelectedValue || !this.cboTocDo_Adsl.SelectedValue) return
      var input = {
        ds_para: JSON.stringify({ LOAITB_ID: Number(this.cboLoaihinhTB.SelectedValue), HDTB_ID: this.hdtb_id, LINHVUC_ID: '', I_TOCDO_ID: Number(this.cboTocDo_Adsl.SelectedValue) })
      }
      var ds_dvgt_macdinh = await api_installnewsubs.post_fn_lay_ds_dvgt_macdinh(this.axios, input).then(async (response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          let apidata = JSON.parse(response.data.data)
          if (apidata['ERROR_CODE'] == 1) {
            var ds_dvgt_macdinh = apidata['RESULT']['DS_TB_DICHVU'] && apidata['RESULT']['DS_TB_DICHVU'].length > 0 ? this.LowerCasePropertyList(apidata['RESULT']['DS_TB_DICHVU']) : []
            if (ds_dvgt_macdinh && ds_dvgt_macdinh.length > 0) {
              // Bổ sung tự động chọn DV GTGT 8939 khi chọn tốc độ green net
              if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL || Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.BRCD_DOITAC) {
                var check_greennet = 0
                await api.get_fn_ktra_tocdo_dk_greennet(this.axios, { params: { tocdo_id: Number(this.cboTocDo_Adsl.SelectedValue) } })
                  .then(async (response) => {
                    if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                      check_greennet = Number(response.data.data)
                    } else check_greennet = 0
                  })
                  .catch(() => {
                    check_greennet = 0
                  })
                if (check_greennet == 1) {
                  for (var i = 0; i < ds_dvgt_macdinh.length; i++) {
                    if (Number(ds_dvgt_macdinh[i]['dichvugt_id']) == 8939 && ds_dvgt_macdinh[i]['kieu_yc'] && Number(ds_dvgt_macdinh[i]['kieu_yc']) == 1) {
                      ds_dvgt_macdinh[i]['dangky'] = 1
                    }
                  }
                }
              }
              this.m_dsThueBao_DichVu = ds_dvgt_macdinh
              this.HT_DichVuGT()
            }
          } else {
            this.$toast.error(apidata['MESSAGE'])
          }
        }
      })
    },
    HT_DichVuGT () {
      try {
        // Hiếu sửa thêm lấy tiền dịch vụ 15/09/2010
        var kt = true// Kiểm tra xem có đăng ký dịch vụ nào không, nếu không trả tiền dv, vatdv về 0
        this.tien_dv = 0
        this.vat_dv = 0
        //
        var str
        this.lvDvgt.Items = []
        for (var i = 0; i < this.m_dsThueBao_DichVu.length; i++) {
          if (this.m_dsThueBao_DichVu[i]["huy"] == "1" || this.m_dsThueBao_DichVu[i]["dangky"] == "1") {
            str = this.m_dsThueBao_DichVu[i]["dichvugt_id"] + ' - ' + this.m_dsThueBao_DichVu[i]["ten_dvgt"]
            if (this.m_dsThueBao_DichVu[i]["dangky"] == "1" && this.m_dsThueBao_DichVu[i]["huy"] == "0") {
              kt = false
              // Hiếu bổ sung code tính tiền dịch vụ
              if (this.m_dsThueBao_DichVu[i]["cuoc_ld"] != "") {
                this.tien_dv = this.tien_dv + Number(this.m_dsThueBao_DichVu[i]["cuoc_ld"])
              }
              if (this.m_dsThueBao_DichVu[i]["vat_ld"] != "") {
                this.vat_dv = this.vat_dv + Number(this.m_dsThueBao_DichVu[i]["vat_ld"])
              }
              str = str + " (ĐK)"
            }
            if (this.m_dsThueBao_DichVu[i]["huy"] == "1") { str = str + " (Hủy)" }
            this.lvDvgt.Items.push({ DICHVUGT_ID: this.m_dsThueBao_DichVu[i]['dichvugt_id'], TEN_DVGT: str })
          }
        }
        if (kt == true) {
          this.tien_dv = 0
          this.vat_dv = 0
        }
        this.HienThiTienDichVu(this.tien_dv, this.vat_dv)
      } catch (ex) {
        console.error(ex)
        this.$toast.error(cmFunc.getErrorMessage(ex))
      }
    },
    HienThiTienDichVu (tien_dv, vat_dv) {
      this.dtien_dv = tien_dv
      this.dvat_dv = vat_dv
      for (var j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DICHVU) {
          this.ds_tien_khoanmuc[j]["tien"] = this.dtien_dv
          this.ds_tien_khoanmuc[j]["vat"] = this.dvat_dv
        }
      }
      this.LayTongTien_HDTB()
    },
    LayTongTien_HDTB_V2 (ds_tien_khoanmuc) {
      try {
        var dtongtien = 0
        var dtongvat = 0
        for (var i = 0; i < ds_tien_khoanmuc.length; i++) {
          dtongtien += parseFloat(ds_tien_khoanmuc[i]["tien"])
          dtongvat += parseFloat(ds_tien_khoanmuc[i]["vat"])
        }
        this.txtTongTien.Text = formatCurrency(dtongtien)
        this.txtTongVat.Text = formatCurrency(dtongvat)
      } catch (ex) {
        console.error(ex)
        this.$toast.error(cmFunc.getErrorMessage(ex))
      }
    }, 
    LayTongTien_HDTB () {
      try {
        var dtongtien = 0
        var dtongvat = 0
        for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          dtongtien += parseFloat(this.ds_tien_khoanmuc[i]["tien"])
          dtongvat += parseFloat(this.ds_tien_khoanmuc[i]["vat"])
        }
        this.txtTongTien.Text = formatCurrency(dtongtien)
        this.txtTongVat.Text = formatCurrency(dtongvat)
      } catch (ex) {
        console.error(ex)
        this.$toast.error(cmFunc.getErrorMessage(ex))
      }
    }, 
    chkCTVChange () {
      if (this.chkCTV.Checked) {
        // TODO: chua co form tham chieu
        // frmNguoiGT f = new frmNguoiGT()
        // f.nhomlnv_id = NHOM_LNV.CONGTACVIEN
        // f.donvi_id = donvi_ctv_id
        // f.loainv_id = loainv_ctv_id
        // f.ctv_id = ctv_id
        // f.ShowDialog()
        // if (f.chapnhan)
        // {
        //     txtCTV.Text = f.ten_ctv
        //     ctv_id = f.ctv_id
        //     loainv_ctv_id = f.loainv_id
        //     donvi_ctv_id = f.donvi_id
        // }
        this.$refs.dlgNguoiGioiThieu.show(true, 'ctv')
      } else {
        this.txtCTV.Text = ''
      }
    },
    chkNguoiGTChange () {
      if (this.chkNguoiGT.Checked) {
        // TODO: chua co form tham chieu
        // frmNguoiGT f = new frmNguoiGT()
        // f.nhomlnv_id = NHOM_LNV.GIOITHIEU_HD
        // f.donvi_id = donvi_nguoi_gt_id
        // f.loainv_id = loainv_nguoi_gt_id
        // f.ctv_id = nguoigt_id
        // f.ShowDialog()
        // if (f.chapnhan)
        // {
        //     txtNguoiGT.Text = f.ten_ctv
        //     nguoigt_id = f.ctv_id
        //     loainv_nguoi_gt_id = f.loainv_id
        //     donvi_nguoi_gt_id = f.donvi_id
        // }
        this.$refs.dlgNguoiGioiThieu.show(true, 'gt')
      } else {
        this.txtNguoiGT.Text = ''
      }
    },
    async cboDaiVT_ADSL_SelectedValueChanged () {
      if (this.cboDaiVT_ADSL.Items.length > 0) {
        if (!this.cboDaiVT_ADSL.SelectedValue) return
        var daivt_id = Number(this.cboDaiVT_ADSL.SelectedValue)
        var rs = await api.lay_ds_donvi_theo_loaidv(daivt_id, LOAI_DONVI.TRAM_VT)
        this.cboVeTinh_Adsl.Items = rs
      }
    },
    async cboDVQL_MGW_SelectedValueChanged () {
      if (this.cboDVQL_MGW.Items.length > 0) {
        if (!this.cboDVQL_MGW.SelectedValue) return
        var daivtID = Number(this.cboDVQL_MGW.SelectedValue)
        var rs = await api.lay_ds_donvi_theo_loaidv(daivtID, LOAI_DONVI.TRAM_VT)
        this.cboTram_MGW.Items = rs
      }
    },
    async cboDaiVT_CD_SelectedValueChanged () {
      if (this.cboDaiVT_CD.Items.length > 0) {
        if (!this.cboDaiVT_CD.SelectedValue) return
        var daivtID = Number(this.cboDaiVT_CD.SelectedValue)
        var rs = await api.lay_ds_donvi_theo_loaidv(daivtID, LOAI_DONVI.TRAM_VT)
        this.cboTram_CD.Items = rs
      }
    },
    async cboDaiVT_IMS_SelectedValueChanged () {
      if (this.cboDaiVT_IMS.Items.length > 0) {
        if (!this.cboDaiVT_IMS.SelectedValue) return
        var daivtID = Number(this.cboDaiVT_IMS.SelectedValue)
        var rs = await api.lay_ds_donvi_theo_loaidv(daivtID, LOAI_DONVI.TRAM_VT)
        this.cboTram_IMS.Items = rs
      }
    },
    async cboDichVuVT_SelectedValueChanged () {
      if (this.cboDichVuVT.Items.length > 0) {
        if (!this.cboDichVuVT.SelectedValue) return
        this.dichvuvt_id = Number(this.cboDichVuVT.SelectedValue)
        this.cboLoaihinhTB.Items = this.listSubscriptionTypeData.filter(el => el.DICHVUVT_ID === +this.cboDichVuVT.SelectedValue)
        if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) { 
          this.cboLoaihinhTB.SelectedValue = LOAIHINH_THUEBAO.INTERNET_ADSL 
        } else {
          if (this.cboLoaihinhTB.Items && this.cboLoaihinhTB.Items.length > 0) {  
            this.cboLoaihinhTB.SelectedValue = this.cboLoaihinhTB.Items[0].LOAITB_ID
          }       
        }
        await this.cboLoaihinhTB_SelectedValueChanged(this.cboLoaihinhTB.SelectedValue)
        // TODO: lam sau
        // GiaoDien_DichVuVT(dichvuvt_id)
        if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) {
          // btnKiemTraAccount.Visible = true
          // btnThongTinTC.Enabled = true
          this.txtTTCuoc.Enabled = true
        } else {
          // btnKiemTraAccount.Visible = false
          // btnThongTinTC.Enabled = false
          this.txtTTCuoc.Enabled = false
        }
        // TODO: lam sau
        // LayTienTheoKhoanMuc()
      }
    },
    async cboLoaihinhTB_SelectedValueChanged (e) {
      try {
        this.loading(true)
        if (this.cboLoaihinhTB.Items.length > 0) {
          if (!this.cboLoaihinhTB.SelectedValue) return
          var loaitbID = Number(this.cboLoaihinhTB.SelectedValue)
          // api.lay_dulieu_theo_dieukien("tocdo_adsl", "tocdo_id", "thuonghieu as toc_do", "loaitb_id = " + loaitbID, "iptinh").then(rs => {
          //   this.cboTocDo_Adsl.Items = rs.map(r => ({ ...r, tocdo_id: Number(r.tocdo_id) }))
          // })
          //FIX ATT
          api.lay_dulieu_theo_dieukien_tocdo_adsl(loaitbID).then(rs => {
            this.cboTocDo_Adsl.Items = rs ? rs.map(r => ({ ...r, tocdo_id: Number(r.tocdo_id) })) : []
          })
          // DataTable dtKieuLD = new DataTable()
          // var dichvu_vt = Number(this.cboDichVuVT.SelectedValue)
          this.cboKieuLD.Items = await api.lay_ds_kieu_ld(LOAI_HOPDONG.KHOIPHUC_SD, this.cboLoaihinhTB.SelectedValue)
          // TODO: lam sau
          // if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL)
          // {
          //     if (loaitbID == LOAIHINH_THUEBAO.INTERNET_MYTV)
          //         btnKiemTraAccount.Enabled = false
          //     else
          //         btnKiemTraAccount.Enabled = true
          // }
          await this.LAY_DS_DVGT_MACDINH()
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi: " + cmFunc.getErrorMessage(ex))
      } finally {
        this.loading(false)
      }
    },
    async cboTocDoADSL_SelectedValueChanged(agrs) {
      try {
        this.loading(true)
        await this.LAY_DS_DVGT_MACDINH()
      } catch (e) {} finally { this.loading(false) }
    },
    cboKieuLD_SelectedValueChanged () {
    },
    chkDoiTac_ADSL_CheckedChanged () {
      if (this.chkDoiTac_ADSL.Checked) {
        this.cboDoiTac_ADSL.Enabled = true
        this.txtCuocDT_Adsl.Enabled = true
      } else {
        this.cboDoiTac_ADSL.Enabled = false
        this.txtCuocDT_Adsl.Enabled = false
        this.txtCuocDT_Adsl.Text = "0"
      }
    },
    onSelectCustomer (e) {
      this.txtMaTB.Text = e.ma_tb
      this.onMaTBKeyPress({keyCode: 13})
    },
    btnSearchAccountClick () {
      this.$refs.dlgSearchAccount.show(true)
    },
    btnMaNVKD_Click () {
      var data = {
        diachi: {
          QUAN_ID: this.quanld_id,
          PHUONG_ID: this.phuongld_id,
          PHO_ID: this.phold_id,
          KHU_ID: this.khuld_id,
          QUAN_AP_ID: this.apld_id,
          DACDIEM_ID: this.dacdiemld_id
        },
        donvi_id: 0
      }
      if (this.thuebao_id == 0) {
        data.thuebao_id = 0
        data.hdtb_id = this.hdtb_id
      } else {
        data.thuebao_id = this.thuebao_id
        data.hdtb_id = 0
      }
      this.$refs.dlgKhuVuc.show(true, data)
    },
    async btnXoaHD_Click (e) {
      e.preventDefault()
      var cf = confirm("Bạn có chắc muốn xóa hợp đồng ?")
      if (!cf) {
        return
      }
      this.loading(true)
      try {
        var ds_tb = await api.lay_ds_hdtb_theo_hdkh(this.hdkh_id)
        if (ds_tb.filter(r => r["tthd_id"] != 1).length > 0) {
          this.$toast.warning("Hợp đồng đã có phiếu giao đi. Bạn không thể xóa hợp đồng !")
          this.loading(false)
          return
        }
        if (ds_tb.filter(r => r.status == 5).length > 0) {
          this.$toast.warning("Hợp đồng đã có phiếu kích hoạt visa. Bạn không thể xóa hợp đồng !")
          this.loading(false)
          return
        }
        if (this.dgvDanhSach.Rows.length <= 0) {
          this.$toast.warning("Không có thông tin trên lưới. Bạn không thể xóa !")
          this.loading(false)
          return
        }
        // TODO: goi service ??
        // VDCService vdc = new VDCService(tt_nd.user_visa, tt_nd.pass_visa)
        // if (ds_tb.Tables[0].Rows.Count == dgvDanhSach.Rows.Count)
        // {
        //     for (int i = 0; i < ds_tb.Tables[0].Rows.Count; i++)
        //     {
        //         if (Convert.ToInt32(ds_tb.Tables[0].Rows[i]["loaitb_id"].ToString()) == LoaiHinhTB.INTERNET_ADSL
        //             || Convert.ToInt32(ds_tb.Tables[0].Rows[i]["loaitb_id"].ToString()) == LoaiHinhTB.INTERNET_FTTH
        //             || Convert.ToInt32(ds_tb.Tables[0].Rows[i]["loaitb_id"].ToString()) == LoaiHinhTB.WIFI_FIBER)
        //         {
        //             if (DONGBO_TEST.TEST == false)
        //             {
        //                 if (ds_tb.Tables[0].Rows[i]["status"].ToString() == TRANGTHAI_DONGBO.DONGBO_LHD.ToString())
        //                 {
        //                     if (vdc.HuyDatChoMegaVNN(ds_tb.Tables[0].Rows[i]["ma_tb"].ToString()))
        //                     {
        //                         this.$toast.success("Hủy đặt chỗ account " + ds_tb.Tables[0].Rows[i]["ma_tb"].ToString() + " trên Visa thành công!")
        //                     }
        //                     else
        //                     {
        //                         this.$toast.warning("Có lỗi xảy ra trong quá trình hủy đặt chỗ account " + txtMaTB.Text + " trên Visa!")
        //                         return
        //                     }
        //                 }
        //             }
        //         }
        //     }
        // }
        var rs = await api.xoa_hdkh_v2(this.hdkh_id, LOAI_HOPDONG.KHOIPHUC_SD)
        if (rs.message == "Success") {
          this.$toast.success('Xoa hợp đồng thành công !')
          this.loading(false)
          this.setButton(0)
        } else {
          this.$toast.error("Có lổi xảy ra:\n" + rs.message)
          this.loading(false)
        }
        // end hoàng
      } catch (ex) {
        console.error(ex)
        this.$toast.error(cmFunc.getErrorMessage(ex))
        this.loading(false)
      }
      this.loading(false)
    },
    async btnThemTB_Click () {
      try {
        // TODO: chua co api - lam sau
        // if (!laphd.KIEMTRA_TB_CUNG_MAKH_TB_PL(txtMaGD.Text, thuebao_id))
        // {
        //     Message_Box.ShowWarning("Thuê bao không cùng mã khách hàng với thuê bao đang thực hiện chuyển dịch !")
        //     return
        // }
        // if (tracuuchung.KIEMTRA_HDTB_KHI_THEMMOI(txtMaTB.Text, hdkh_id, dichvuvt_id, 0))
        // {
        //     Message_Box.ShowTB("Thuê bao đã tồn tại trong hợp đồng, không thêm mới được ")
        //     return
        // }
        // if (!tracuuchung.KIEMTRA_DB_KHI_THEMMOI(txtMaTB.Text, khachhang_id, thuebao_id, dichvuvt_id))
        // {
        //     Message_Box.ShowTB("Thuê bao không tồn tại trong danh bạ, không thêm mới được ")
        //     return
        // }
        // TODO: chua co api - lam sau
        // CheckDataFacade checkdata = new CheckDataFacade()
        // int kieu = Convert.ToInt32(checkdata.MAP_ID("kieu", DatabaseConstants.DB2 + ".kieu_ld", "where kieuld_id =" + cboKieuLD.SelectedValue))
        // if ((kieu == 1 || kieu == 2) && txtMaTN_ADSL.Text.Trim().Length == 0)
        // {
        //     Message_Box.ShowWarning("Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!")
        //     txtMaTN_ADSL.Focus()
        //     return
        // }
        if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL || Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.BRCD_DOITAC) {
          var check_greennet = 0
          await api.get_fn_ktra_tocdo_dk_greennet(this.axios,{params:{tocdo_id: Number(this.cboTocDo_Adsl.SelectedValue)}}).then(async (response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              check_greennet = Number(response.data.data)
            } else check_greennet = 0
          })
          .catch(() => {
            check_greennet = 0
          })
          if (check_greennet == 1 ) {
            if (!this.m_dsThueBao_DichVu || !this.m_dsThueBao_DichVu.find((x) => Number(x.dangky) && x.dichvugt_id==8939)) {
              this.$toast.error('Gói cước có bao gồm dịch vụ GreenNet. Đề nghị khai báo Dịch vụ GTGT Green Net trước khi khởi tạo gói cước')
              return false
            }
            var check_kmtt_dvgt = this.ds_tien_khoanmuc.filter(e => e['khoanmuctt_id'].toString() == KHOANMUC_TT.KMTT_DICHVU.toString());
            if (!check_kmtt_dvgt || check_kmtt_dvgt.length <= 0) {
              // KMTT_DICHVU: 4,//Tiền dịch vụ gia tăng
              this.$toast.error('Không có khoản mục thanh toán 4 - Tiền dịch vụ gia tăng. \nThực hiện tại menu khoản mục thanh toán!')
              return false;
            }
          }
        }
        this.loading(true)
        var result = true
        let loaikenh_id = 1
        if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26 || this.dichvuvt_id == DICHVU_VIENTHONG.TSL || this.dichvuvt_id == 25) {
          var dsDBMegaWAN = await api.lay_ds_danhba_megawan(this.thuebao_id)
          loaikenh_id = dsDBMegaWAN && dsDBMegaWAN.length > 0 ? dsDBMegaWAN[0]['loaikenh_id'] : 1
        }
        
        var dshg = await api.layhuonggiao_tiepnhan_v2(LOAI_HOPDONG.KHOIPHUC_SD, this.loaitb_id, this.tocdo_id, this.muccuoc_id, Number(this.cboKieuLD.SelectedValue), loaikenh_id, Number(this.cboDichVuVT.SelectedValue))
        var huonggiao_tn = 0
        this.quytrinh_id = 0
        if (dshg.length <= 0) {
          this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!")
          this.loading(false)
          return
        }
        huonggiao_tn = Number(dshg[0]["huonggiao_id"])
        this.quytrinh_id = Number(dshg[0]["quytrinh_id"])
        result = result == true ? await this.TaoDuLieu_HDTB(true) : false
        this.dsHDTB_ADSL = []
        this.dsHDTB_TSL = []
        this.dsHDTB_CD = []
        this.dsHDTB_GP = []
        this.dsHDTB_IMS = []
        this.dsHDTB_MGW = []
        this.dsHDTB_TSL = []
        this.dsHDTBDV = []
        result = result == true ? await this.TaoDuLieu_HDTBDV(true) : false
        switch (this.dichvuvt_id) {
          case DICHVU_VIENTHONG.CO_DINH:
            result = result == true ? await this.TaoDuLieu_HDTB_CD(true) : false
            break
          case DICHVU_VIENTHONG.IMS:
            result = result == true ? await this.TaoDuLieu_HDTB_IMS(true) : false
            break
          case DICHVU_VIENTHONG.ADSL:
            result = result == true ? await this.TaoDuLieu_HDTB_ADSL(true) : false
            break
          case DICHVU_VIENTHONG.GPHONE:
            result = result == true ? await this.TaoDuLieu_HDTB_GP(true) : false
            break
          case DICHVU_VIENTHONG.MEGAWAN:
          case DICHVU_VIENTHONG.METRONET:
          case 26:
            result = result == true ? await this.TaoDuLieu_HDTB_MGWAN(true) : false
            break
          case DICHVU_VIENTHONG.TSL:
          case 25:
            result = result == true ? await this.TaoDuLieu_HDTB_TSL(true) : false
            break
          default:
            break
        }
        result = result == true ? await this.TaoDuLieu_CT_TIENHD(true) : false
        result = result == true ? await this.TaoDuLieu_DKDV() : false
        if (!result) {
          this.$toast.error('Lỗi tạo dữ liệu hđ thanh lý thêm TB!')
          this.loading(false)
          return
        }
        this.CapNhat_HDTB_ID_DangKyDV()
        // TODO: goi service ??
        // #region "Dat cho Account VDC"
        // bool statusVdc = false
        // if (Convert.ToInt32(cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL)
        // {
        //     int loaihinh_tb = Convert.ToInt32(cboLoaihinhTB.SelectedValue)
        //     if (loaihinh_tb == LoaiHinhTB.INTERNET_1260 || loaihinh_tb == LoaiHinhTB.INTERNET_ADSL
        //         || loaihinh_tb == LoaiHinhTB.INTERNET_FTTH || loaihinh_tb == LoaiHinhTB.WIFI_FIBER)
        //     {
        //         if (DONGBO_TEST.TEST == false)
        //         {
        //             CheckDataFacade obj = new CheckDataFacade()
        //             VDCService vdcService = new VDCService(tt_nd.user_visa, tt_nd.pass_visa)
        //             string account = dsHDTB[0]["ma_tb"].ToString()
        //             string contex = obj.MAP_ID("ma_lhtb_vdc", DatabaseConstants.DB2 + ".loaihinh_tb", "where loaitb_id =" + cboLoaihinhTB.SelectedValue.ToString())
        //             statusVdc = vdcService.DatChoMegaVNN(account, contex, tt_nd.ngay_cn.AddDays(7))
        //             if (!statusVdc)
        //             {
        //                 Message_Box.ShowWarning("Có lỗi xảy ra trong quá trình đặt chỗ Account " + account + " lên Visa.Bạn hãy vui lòng thực hiện lại chức năng này!")
        //                 return
        //             }
        //             check_tb_adsl = true
        //         }
        //         else
        //         {
        //             statusVdc = true
        //             check_tb_adsl = true
        //         }
        //     }
        // }
        // #endregion
        var rs = await api.themmoi_hopdongtb_khoiphucthanhly_v2(this.dsHDTB, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsDK_DVGT, this.dsDK_DVKHAC, this.dsCTTHD, this.dsHDTBDV, huonggiao_tn, this.dsHDTB_IMS, this.dsHDTB_MGW, this.dsHDTB_TSL)
        if (rs.error_code != SUCCESS_CODE) {
          this.$toast.error(rs.message)
          this.loading(false)
          return
        }
        this.$toast.success("Thêm HĐ thuê bao thành công !")
        // TODO: lam sau
        // if (Number(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL && statusVdc == true)
        // {
        //     //Gọi hàm cập nhật trạng thái HDTB = 1: Đã đặt chỗ thành công trên VDC
        //     LapHopDongFacade lhd = new LapHopDongFacade()
        //     lhd.CAPNHAT_STATUS_HDTB(hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
        // }
        await this.hienThiDanhSacHDTB(await api.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id))
        this.loading(false)
      } catch (ex) {
        console.error(ex)
        this.$toast.error(cmFunc.getErrorMessage(ex))
        this.loading(false)
      }
    },
    async btnXoaTB_Click () {
      if (this.$refs.lvwThueBao.getSelectedRecords().length === 0) {
        this.$toast.error("Vui lòng chọn thuê bao cần xóa")
        return
      }
      var cf = confirm("Bạn có chắc muốn xóa thuê bao này không ?")
      if (!cf) {
        return
      }
      this.loading(true)
      try {
        // TODO: goi service ??
        // if (Convert.ToInt32(dgvDanhSach.CurrentRow.Cells["cluLoaiHinhTBID"].Value.ToString()) == LoaiHinhTB.INTERNET_ADSL
        //         || Convert.ToInt32(dgvDanhSach.CurrentRow.Cells["cluLoaiHinhTBID"].Value.ToString()) == LoaiHinhTB.INTERNET_FTTH
        //         || Convert.ToInt32(dgvDanhSach.CurrentRow.Cells["cluLoaiHinhTBID"].Value.ToString()) == LoaiHinhTB.WIFI_FIBER)
        // {
        //     if (dgvDanhSach.CurrentRow.Cells["cluMaTB"].Value.ToString().Length > 0)
        //     {
        //         if (DONGBO_TEST.TEST == false)
        //         {
        //             VDCService vdc = new VDCService(tt_nd.user_visa, tt_nd.pass_visa)
        //             if (vdc.HuyDatChoMegaVNN(dgvDanhSach.CurrentRow.Cells["cluMaTB"].Value.ToString())) //txtMaTB.Text.Trim()
        //             {
        //                 Message_Box.ShowTB("Hủy đặt chỗ account " + dgvDanhSach.CurrentRow.Cells["cluMaTB"].Value.ToString() + " trên Visa thành công!")
        //             }
        //             else
        //             {
        //                 Message_Box.ShowTB("Có lỗi xảy ra trong quá trình hủy đặt chỗ account " + dgvDanhSach.CurrentRow.Cells["cluMaTB"].Value.ToString() + " trên Visa!")
        //                 return
        //             }
        //         }
        //     }
        // }
        var id = await api.fn_tt_hd_thuebao(this.hdtb_id, 2)
        var i_tthd_id = Number(id)
        if (i_tthd_id != TRANGTHAI_HOPDONG.MOI) {
          this.$toast.error("Trạng thái hợp đồng không phải mới tiếp nhận. Bạn hãy kiểm tra lại !")
          this.loading(false)
          return
        }
        var rs = await api.xoa_hdtb(this.hdtb_id)
        if (rs.error_code != SUCCESS_CODE) {
          this.$toast.error(rs.message)
          this.loading(false)
          return
        }
        this.$toast.success("Xóa thuê bao thành công !")
        await this.hienThiDanhSacHDTB(await api.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id))
        
        this.loading(false)
      } catch (ex) {
        console.error(ex)
        this.$toast.error(cmFunc.getErrorMessage(ex))
        this.loading(false)
      }
      finally {
        this.loading(false)
      }
    },
    async dgvDanhSach_SelectionChanged (e) {
      this.loading(true)
      var rows = this.$refs.lvwThueBao.getSelectedRecords()
      if (rows.length <= 0) return
      try {
        var row = rows[0]
        this.hdtb_id = Number(row["hdtb_id"])
        var ds = await api.lay_ds_hopdong_tb_theo_hdtb_id(this.hdtb_id)
        await this.hienThiTTHopDongTB(ds)
      } catch (ex) {
        console.error(ex)
        this.$toast.error(cmFunc.getErrorMessage(ex))
      } finally {
        this.loading(false)
      }
    },
    btnTienKM_Click () {
      this.$refs.dlgTienKhoanMuc.show(true, {
        ds: this.ds_tien_khoanmuc,
        cols: [
          { fieldName: 'khoanmuctt_id', headerText: 'Id', isPrimaryKey: true, visible: false },
          { fieldName: 'tentat_kmtt', headerText: 'Khoản mục', allowFiltering: false, allowSorting: false, isPrimaryKey: false },
          { fieldName: 'tongtien', textAlign: 'right', allowEditing: true, editType: 'numericedit', format: 'N', customAttributes: { class: 'tongtien' }, headerText: 'Tổng tiền', allowFiltering: false, allowSorting: false, edit: 'tongtienParam' },
          { fieldName: 'tien', textAlign: 'right', allowEditing: true, editType: 'numericedit', format: 'N', customAttributes: { class: 'cuoclapdat' }, headerText: 'Tiền', allowFiltering: false, allowSorting: false, edit: 'tienParam' },
          { fieldName: 'vat', textAlign: 'right', format: 'N', customAttributes: { class: 'vatlapdat' }, headerText: 'Vat', allowFiltering: false, allowSorting: false },
          { fieldName: 'tyle_vat', textAlign: 'right', format: 'N', headerText: 'Tỷ lệ vat', allowFiltering: false, allowSorting: false }
        ],
        tieude: 'Chi tiết tiền khoản mục',
        tieudebang: 'Chi tiết khoản mục thanh toán'
      })
    },
    dlgTienKM_Close (data) {
      console.log('close tien KM', data)
      this.ds_tien_khoanmuc = data.map(r => ({ ...r, khoanmuctt_id: Number(r.khoanmuctt_id) }))
      if (this.ds_tien_khoanmuc.length > 0) {
        for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (this.ds_tien_khoanmuc[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_KHOIPHUCSD) {
            this.dtienld = parseFloat(this.ds_tien_khoanmuc[i]["tien"])
            this.dvatld = parseFloat(this.ds_tien_khoanmuc[i]["vat"])
          }
          if (this.ds_tien_khoanmuc[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DICHVU) {
            this.dtien_dv = parseFloat(this.ds_tien_khoanmuc[i]["tien"])
            this.dvat_dv = parseFloat(this.ds_tien_khoanmuc[i]["vat"])
          }
          this.LayTongTien_HDTB()
        }
      }
    },
    btnDvCongThem_Click () {
      this.$refs.dlgDvCongThem.show(true, {
        _loaitb_id: this.loaitb_id,
        _hdtb_id: this.hdtb_id,
        _linhvuc_id: null,
        _tocdo_id: this.tocdo_id,
        lvDvgt: this.m_dsThueBao_DichVu
      })
    },
    dlgDvCongThem_formClose (data) {
      var tmpList = []
      for (var row of data) {
        var tmpObj = {}
        for (var key in row) {
          if (Object.hasOwnProperty.call(row, key)) {
            tmpObj[key.toLowerCase()] = row[key]
          }
        }
        tmpList.push(tmpObj)
      }
      this.m_dsThueBao_DichVu = tmpList
      this.HT_DichVuGT()
    },
    btnDvKhac_Click () {
      var data = {
        ds: this.dsDK_DVKHAC,
        _loai_id: 1,
        _loaitb_id: this.loaitb_id,
        _thanhtoan_id: 0
      }
      this.$refs.dlgDvKhac.show(true, data)
    },
    dlgDvKhac_Close (data) {
      console.log('dv khac close', data)
      data = this.toLowerCaseList(data)
      this.dsDK_DVKHAC = data
      this.hienThiDichVuKhac()
    },
    toLowerCaseList (list) {
      var tmpList = []
      for (var row of list) {
        var tmpObj = {}
        for (var key in row) {
          if (Object.hasOwnProperty.call(row, key)) {
            tmpObj[key.toLowerCase()] = row[key]
          }
        }
        tmpList.push(tmpObj)
      }
      return tmpList
    },
    async btnThongTinCuoc_Click () {
      if (!this.cboMucCuoc_ADSL.SelectedValue) {
        this.$toast.warning("Chưa có mức cước thuê bao. Bạn hãy kiểm tra lại!")
        return
      }
      var cuoc_tb = await api.fn_tt_muccuoc_tb(this.cboMucCuocTB_ADSL.SelectedValue, 1)
      var cuockhoan = await api.fn_tt_muccuoc_tb(this.cboMucCuocTB_ADSL.SelectedValue, 1)
      var cuoc_tg = await api.fn_tt_muccuoc_tb(this.cboMucCuocTB_ADSL.SelectedValue, 1)
      var cuoc_ll = await api.fn_tt_muccuoc_tb(this.cboMucCuocTB_ADSL.SelectedValue, 1)
      if (cuoc_tb == "-1") cuoc_tb = "0"
      if (cuockhoan == "-1") cuockhoan = "0"
      if (cuoc_tg == "-1") cuoc_tg = "0"
      if (cuoc_ll == "-1") cuoc_ll = "0"
      var data = {}
      data.loaitb_id = Number(this.cboLoaihinhTB.SelectedValue)
      data.thongtintc = this.txtTTCuoc.Text
      data.cuoc_tb = cuoc_tb
      data.cuoc_tg = cuoc_tg
      data.cuockhoan = cuockhoan
      data.cuoc_ll = cuoc_ll
      this.$refs.dlgTinhCuoc.show(true, data)
    },
    dlgTinhCuoc_onAccept (data) {
      this.txtTTCuoc.Text = data
    },
    dlgKhuVucChapNhan (data) {
      console.log('dlgKhuVucChapNhan', data)
      this.khuvuc_id = data.khuvuc_id
      this.dsHDTB_KV = {
        HDTB_KV: data.hdtb_kv_data && data.hdtb_kv_data.length > 0 ? data.hdtb_kv_data.map(e => ({...e, HDTB_ID: this.hdtb_id})) : []
      }
      this.txtKhuVuc.Text = data.ma_kv
    },
    btnLienHe_Click () {
      this.$refs.dlgLienHe.show(true, {
        hdkh_id: this.hdkh_id,
        khachhang_id: this.khachhang_id
      })
    },
    btnEmail_Click () {
      this.$refs.dlgEmail.show(true)
    },
    dlgNguoiGioiThieu_Accept (data) {
      if (data.source == 'ctv') {
        this.txtCTV.Text = data.data.rowData.ten_ht
        this.ctv_id = data.data.rowData.nhanvien_id
        this.loainv_ctv_id = data.data.rowData.loainv_id
        this.donvi_ctv_id = data.data.rowData.donvi_id
      } else if (data.source == 'gt') {        
        this.txtNguoiGT.Text = data.data.rowData.ten_ht
        this.nguoigt_id = data.data.rowData.nhanvien_id
        this.loainv_nguoi_gt_id = data.data.rowData.loainv_id
        this.donvi_nguoi_gt_id = data.data.rowData.donvi_id
      }
    },
    btnMaGD_Click () {
      this.$refs.dlgSearchContract.showModal()
    },
    onAcceptSearchContract (data) {
      this.txtMaGD.Text = data.ma_gd
      this.$refs.txtMaGD.focus()
      this.onTransactionCodeChange({ key: 'Enter' })
    },
    btnMaTN_Click () {
      this.$refs.dlgMaTN.btnThoat = true
      this.$refs.dlgMaTN.show(true, {
        khachhang_id: this.khachhang_id,
        kieuld_id: Number(this.cboKieuLD.SelectedValue),
        loaitb_id: Number(this.cboLoaihinhTB.SelectedValue),
        dichvuvt_id: Number(this.cboDichVuVT.SelectedValue)
      })
    },
    dlgMaTN_Close (data) {
      this.txtMaTN_ADSL.Text = data.MA_TB
      this.txtMaDoiCap_ADSL.Text = data.MADOICAP
    },
    btnThanhToan_Click () {
      // this.$refs.dlgPayment.show(true, this.txtMaGD.Text)
      this.$router.push({ name: 'Payment', params: {"magd": this.txtMaGD.Text} })
    },
    async tsbtnGiaoPhieu_Click (e) {
      e.preventDefault()
      try {
        if (this.dgvDanhSach.Rows.length === 0) {
          this.$toast.error('Thuê bao chưa lập HĐ không thể giao phiếu')
          return
        }
        var quytrinhId = await api.fn_tt_hd_thuebao(this.hdtb_id, 3)
        var data = {
          'hdtb_id': this.hdtb_id,
          'hdkh_id': this.hdkh_id,
          'loaitb_id': this.loaitb_id,
          'luong_id': this.luong_id
        }
        var huongGiaoId = await api.fn_giaophieu_chamdutsd(data)
        if (huongGiaoId.result && +huongGiaoId.result != 0) {
          window.open(`/#/contract/HandoverTicket?vquytrinh=${quytrinhId}&vhuonggiao_id=${huongGiaoId.result}&vdichvuvt_id=${this.dichvuvt_id}&vloai_hd=${LOAI_HOPDONG.KHOIPHUC_SD}`)
        } else {
          this.$toast.error('Không tìm thấy hướng giao')
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi xảy ra ' + cmFunc.getErrorMessage(ex))
      }
    },
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
  }
}
</script>
<style>
#khoiPhucThanhLy .listbox{
  height: 100px;
}
</style>