<template src='./RestoreLiquidationSubsCrossSale.html'></template>
<style scoped src='./RestoreLiquidationSubsCrossSale.scss'></style>
<script>
/* eslint eqeqeq: 'off', quotes: 'off', camelcase: off */

import gridview from '@/components/Shared/gridview.vue'
import select3 from '@/components/form/VueSelect.vue'
import input3 from '@/components/form/VueInput.vue'
import DatePicker from 'vue2-datepicker'
import { mapActions } from 'vuex'
import 'vue2-datepicker/index.css'
import * as moment from 'moment'
import api from './RestoreLiquidationSubsCrossSaleAPI.js'
import { LOAI_HOPDONG, TRANGTHAI_HOPDONG, LOAI_DONVI, DUNG_THU, DICHVU_VIENTHONG, KHOANMUC_TT, LOAIHINH_THUEBAO, TRANGTHAI_TB, TRANGTHAI_DONGBO, KieuCaiDatDVGT } from '@/constants'
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
import cmUlt from "@/utils/commonFunction"
import DialogMaTN from './components/dialogMaTN.vue'
import frmChonTinhTSL from "@/modules/contract/setup/SetupChangeTSLSpeedCrossSale/Popups/frmChonTinhTSL.vue"
import frmThongSoTSL from "@/modules/contract/setup/SetupChangeTSLSpeedCrossSale/Popups/frmThongSoTSL/frmThongSoTSL.vue"
import DialogChonTinh from '@/modules/contract/setup/SubscriptionLiquidationCrossSell/components/PopupChonTinh.vue'
import ThongSoCoDinh from "@/modules/contract/setup/TransferRightsCrossSell/popups/ThongSoCoDinh/ThongSoCoDinh.vue"
import DangKyDVGTBC from "@/modules/contract/setup/TransferRightsCrossSell/popups/DangKyDVGTBC/DangKyDVGTBC.vue"
import TocDoMucCuoc from '@/modules/install/ChangeADSLSpeed/popups/TocDoMucCuoc/TocDoMucCuoc.vue'
import frmThongSoWan from '@/modules/contract/setup/ChangeMegaWanConfigCrossSell/popups/frmThongSoWan.vue'
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
    DialogMaTN,
    frmChonTinhTSL,
    frmThongSoTSL,
    DialogChonTinh,
    ThongSoCoDinh,
    TocDoMucCuoc,
    DangKyDVGTBC,
    frmThongSoWan    
  },
  name: 'RestoreLiquidationSubsCrossSale',
  watch: {
    'dgvDanhSach.Rows': {
      handler (val) {
        this.tsbtnGiaoPhieu.Enabled = val.length > 0
        //this.tsbtnThemTB.Enabled = val.length >= 1
        // this.tsbtnXoaTB.Enabled = val.length > 1
      },
      deep: true
    },
    'cboKieuLD.Items': {
      handler (val) {
        if (val.length > 0) {
          this.cboKieuLD.SelectedValue = this.cboKieuLD.Items[0].kieuld_id
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
      tsbtnGhiLai: { Enabled: false },
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
      txtMaDA: { Text: '' },
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
        SelectedValue: 0
      },
      Kieuld_click:false,
      dtpNgayLapHD: { Value: null },
      dtpNgayYeuCau: { Value: moment(new Date()).format('DD/MM/YYYY') },
      dtpNgayTL: { Value: null },
      // thong tin thue bao
      txtMaTB: { Text: '' },
      txtTenTB: { Text: '' },
      txtDiaChiTB: { Text: '' },
      txtDiaChiLD: { Text: '' },
      txtGhiChu: { Text: '' },
      txtMaTN_ADSL: { 
        Text: '',
        Enabled: false,
      },
      btnMaTN_ADSL:{
        Enabled: false,
      },
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
      khachhang_id: 0,
      thuebao_id: 0,
      thuebao_cha_id: 0,
      hdkh_cha_id: 0,
      loaitb_id: 0,
      check_tb_adsl: false,
      doituong_id: 0,
      khlon_id: 0,
      tocdo_id: 0,
      muccuoc_id: 0,
      quytrinh_id: 0,
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
      m_dsThueBao_DichVu: {
        dsdvgt_ban: [],
        dsdvgt_tc: []
      },
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
      // ==================

      //==================
      /// <summary>
      /// -----------------Bán chéo----------------
      /// </summary>
      dsHDBC: [],
      dsHDTBBC : [],
      dsHDBC_ADSL : [],
      dsHDBC_CD : [],
      dsHDBC_IMS : [],
      dsHDBC_MGW :[],
      dsHDBC_TSL :[],
      dsDKBCDVGT: [],
      dsLYDOHUYBC: [],
      dsDiaChi_BanCheo: [],

      kenh_trang:false,
      chi_giao_net:false,
      tinh: {},
      tinh_dau: {},
      tinh_cuoi: {},
      thuebao_tc_id:0,
      thuebao_tc_dau:0,
      thuebao_tc_cuoi:0,
      dsDBTBBC:{},
      dsDBTB:[],
      thongso_ban: {},
      thongso_tc: {},
      thongso_tc_dau: {},
      thongso_tc_cuoi: {},
      khachhang_tc_id:0,
      vloaikenh_id : 0,
      quytrinh_tc_tn_dau:0,
      huonggiao_tc_tn_dau : 0,
      quytrinh_tc_tn_cuoi:0,
      huonggiao_tc_tn_cuoi : 0,
      btnDiemDau: {        
        Visible: true,        
      },
      btnDiemCuoi: {        
        Visible: true,        
      },
      btnKiemTraAccount: {        
        Visible: true,          
      },
      btnThongTinTC: {        
        Visible: true,  
        Enabled: true,      
      },
      position: {X: 'center', Y: 'top'},
      targetTop: '#app .main-wrapper',
    }
  },
  computed: {
    tt_nd () {
      return {
        nhanvien_id: this.$auth.getNhanVienID(),
        donvi_id: this.$auth.getDonViID(),
        ngay_cn: this.$auth.getNgayCapNhat(),
        nguoidung_id: this.$root.token.getNguoiDungID(),
        USER_NEO: "",
        ma_nd: this.$auth.getMaNhanVien(),
        may_cn: this.$auth.getMaNhanVien(),//this.$root.token.getMachine(),//tochange
        ip: this.$auth.getMaNhanVien(),//this.$root.token.getIP(), //tochange
        tinh_id: this.$auth.getPhanVungID()
      }
    },
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
    },
    ckieu_ld() {      
      var list = this.cboKieuLD.Items.map(ld=>({id:ld.kieuld_id,text:ld.ten_kieuld}));      
      return list;
    },
  },
  mounted () {
    window['component'] = this
    this.getListTelecomService()
    this.getListSubscriptionType().then(ds => {
      this.cboDichVuVT_SelectedValueChanged()
    })
    // this.getListDonVi({ donViID: 0, loaiDonViID: LOAI_DONVI.TRAM_VT })

    // this.initSpliter()

    this.onLoad()
  },
  methods: {
    ...mapActions('restoreLiquidate', [
      'getListTelecomService',
      'getListSubscriptionType',
      'getListRequestType',
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
        console.log('onload')
        this.dtpNgayLapHD.Value = this.$auth.getNgayCapNhat()

        this.setButton(-1)
        //this.ds_tien_khoanmuc = await api.sp_lay_tienkhoanmuc_loaihd(LOAI_HOPDONG.KHOIPHUC_SD)
        this.ds_tien_khoanmuc = await api.post_lay_khoanmuc_tt_theo_loaihd({ "vloaihd_id": LOAI_HOPDONG.KHOIPHUC_SD })

        // goi api => load du lieu vao cac combobox
        api.getListTelecomService().then(rs => {
          console.log('list dịch vụ')
          console.log(rs)
          this.cboDichVuVT.Items = rs
          this.cboDichVuVT_SelectedValueChanged()
        })
        //TODO: tạm dùng mã của HNI: 284410
        api.lay_ds_donvi_theo_loaidv_bc(0, LOAI_DONVI.DONVIQL_LD).then(ds => {
          this.cboDaiVT_CD.Items = ds.map(r => ({...r, donvi_id: parseInt(r.donvi_id)}))
          this.cboDaiVT_CD_SelectedValueChanged()

          this.cboDaiVT_ADSL.Items = ds.map(r => ({...r, donvi_id: parseInt(r.donvi_id)}))
          this.cboDVQLGP.Items = ds.map(r => ({...r, donvi_id: parseInt(r.donvi_id)}))
          this.cboDVQL_MGW.Items = ds.map(r => ({...r, donvi_id: parseInt(r.donvi_id)}))
          this.cboDaiVT_IMS.Items = ds.map(r => ({...r, donvi_id: parseInt(r.donvi_id)}))
        })
        

        // api.lay_dulieu_theo_dieukien("tocdo_kenh", "tocdo_id", "tocdo||' '||donvi toc_do", "", "tocdo").then(ds => {
        //   this.cboTocDo_MGW.Items = ds
        // })
        
        let ds0 = await api.sp_tt_tocdo_kenh({ "param": '',"type": 1})
        if (ds0 && ds0.length>0){
          this.cboTocDo_MGW.Items = ds0.map(r => ({ ...r, toc_do: r.tocdo+' '+r.donvi }))
        }


        // api.lay_dulieu_theo_dieukien('muccuoc', 'muccuoc_id', 'tenmuc as tenmuccuoc', '', 'muccuoc_id').then(ds => {
        //   this.cboMucCuoc_ADSL.Items = ds.map(r => ({...r, muccuoc_id: parseInt(r.muccuoc_id)}))
        // })
        let ds1 = await api.sp_tt_muccuoc({ "p_param": '',"p_type": 1})
        console.log('sp_tt_muccuoc')
        console.log(ds1)
        if (ds1 && ds1.length>0){
          this.cboMucCuoc_ADSL.Items = ds1.map(r => ({ ...r, muccuoc_id: parseInt(r.muccuoc_id),tenmuccuoc: r.tenmuc }))
        }

        // bangts.HT_Tocdo_Kenh_Combobox(this.cboTocDo_MGW)
        // bangts.HT_MucCuoc_Adsl_Combobox(this.cboMucCuoc_ADSL)

        this.dsDanhMuc = await api.lay_ds_doitac()
        this.cboDoiTac_ADSL.Items = this.dsDanhMuc
        // bangts.HT_DS_GRIDLOOKUP(this.cboDoiTac_ADSL, dsDanhMuc.Tables['DOITAC'], 'TEN_DT', 'DOITAC_ID', true, 2, 400)

        // region Lấy các tham số mặc đinh
        var ds = await api.lay_ds_thamso_md(0)
        // DataSet ds = GlobalVar.glbThamSoMD
        if (ds.length > 0) {
          let strvtemp = 'BATBUOC_CTV_KHOIPHUC'
          let det = ds.filter(x => x['ma_ts'] == strvtemp)
          if (det.length > 0) {
            var row = det[0]
            if (row['ten_ts'] == '1') { this.batbuoc_batbuoc_ctv = 1 }
          }

          

          // THam số ko bắt buộc phải nhập cộng tác viên
          strvtemp = 'BATBUOC_GIOITHIEU_KHOIPHUC'
          det = ds.filter(x => x['ma_ts'] == strvtemp)
          if (det.length > 0) {
            var row = det[0]
            if (row['ten_ts'] == '1') { this.batbuoc_gioithieu_khoiphuc = 1 }
          }
          
        }

        // if (this.$route.query) {
        //   let { kieu, phieucskh_id, phieucskhdv_id, hdkh_cha_id } = this.$route.query
        //   if ((kieu = kieu || 0) != 0) {
        //     if (kieu == 5 && phieucskh_id && phieucskhdv_id) {
        //       this.LoadTuHopDongB2A(phieucskh_id, phieucskhdv_id)
        //     } else if (hdkh_cha_id) {
        //       this.LoadTuHopDongTuVan(hdkh_cha_id)
        //     }
        //   }
        // }
      } catch (exp) {
        this.$toast.error(exp.toString())
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
      try {
        if (e.keyCode !== 13) {
          return
        }

        if (!this.txtMaTB.Text.trim()) {
          return
        }
        
        
        
        this.loading(true)
        var ds_tinh = await api.lay_tinh_theo_matb({
          'matb': this.txtMaTB.Text        
        })
        if (    (ds_tinh && ds_tinh.length > 0 && (ds_tinh[0].tinh_thicong == null ||ds_tinh[0].tinh_thicong == 0))
            || (ds_tinh == null) || (ds_tinh.length == 0) 
          ) {
            this.kenh_trang = true
            this.chi_giao_net = false
            this.loading(false)
            var cf = confirm("Thuê bao không có trong danh bạ bán chéo. Bạn phải chọn tỉnh thi công !")
            if (cf) {
              //
              
              if (this.cboDichVuVT.SelectedValue == DICHVU_VIENTHONG.TSL)
              {
                this.$refs.frmChonTinhTSL.openDialog()                
              }
              else
              {
                  var rsTinh = await this.$refs.dlgChonTinh.showModal()
                  if (rsTinh) {
                    this.tinh = { tinhthicong_id: rsTinh }
                  } else {
                    this.clear()
                    this.loading(false)
                    return
                  }                
              }
              
            }
            else {
              this.clear()
              this.loading(false)
              return
            }
        }
        else if (ds_tinh && ds_tinh.length > 0 && ds_tinh[0].tinh_thicong != null && ds_tinh[0].tinh_thicong!=0){
          this.tinh = { tinhthicong_id: ds_tinh[0].tinh_thicong }
          this.kenh_trang = false        
          var ds_tbtc = await api.sp_lay_thuebaoid_thicong_theo_matb({
            'vma_tb': this.txtMaTB.Text,
            "vtinhthicong":this.tinh.tinhthicong_id,        
          })
          if (ds_tbtc && ds_tbtc.length>0){
            this.thuebao_tc_id=ds_tbtc[0].thuebao_kn_id
          }        
          // /get_thuebao_kn_id
          var ds_thuebao_kn = await api.get_thuebao_kn_id({
            'ma_tb': this.txtMaTB.Text,              
          })
          if (ds_thuebao_kn && ds_thuebao_kn.length>0&& ds_thuebao_kn[0].thuebao_kn_id=='-1'){
            this.chi_giao_net = true
          }
          else this.chi_giao_net = false
          
          
          var data = {
                "in_ma_tb": this.txtMaTB.Text,
                "in_dichvuvt_id": this.dichvuvt_id,
                "in_donvi_dl_id": 0,
                "in_loaihd_bancheo": 1
            }        
          console.log('lay_danhba_theo_matb_bc')
          var ds_dba_bc = await api.lay_danhba_theo_matb_bc(data)        
          console.log(ds_dba_bc)
          if (ds_dba_bc && ds_dba_bc.length>0){
            this.dsDBTBBC = ds_dba_bc
            if (ds_dba_bc[0]['ngay_cat']) {
                this.dtpNgayTL.Value = formatDate(ds_dba_bc[0]['ngay_cat'])
            }
          }
          else this.dsDBTBBC={}
        } 
        else {
          this.clear()
          this.loading(false)
          return
        }
        
        if (this.cboDichVuVT.SelectedValue == DICHVU_VIENTHONG.TSL || this.cboDichVuVT.SelectedValue == 25)
        {          
            let dtTinh_id_tsl = await api.lay_tinhtc_id_tsl(this.txtMaTB.Text, this.cboDichVuVT.SelectedValue)
            var tinh_dau_id = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 1)["tinh_thicong"])
            var tinh_cuoi_id = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 2)["tinh_thicong"])
            this.thuebao_tc_id = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 1)["thuebao_kn_id"])
            this.thuebao_tc_dau = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 1)["thuebao_kn_id"])
            this.thuebao_tc_cuoi = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 2)["thuebao_kn_id"])

            this.tinh_dau = await api.tinh_thicong(tinh_dau_id)
            this.tinh_cuoi = await api.tinh_thicong(tinh_cuoi_id)
        }


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
          this.hienThiTTHopDongKH(ds)
        } else {
          this.hienThiTTDanhBa(this.txtMaTB.Text)
        }

        this.loading(false)
      } catch (e) {
        this.loading(false)
      } finally {
        this.loading(false)
      }
    },
    //function bán chéo TSL
    async frmChonTinhTSL_onChapNhan(obj) {
        this.tinh_dau = obj.tinhdau
        this.tinh_cuoi = obj.tinhcuoi
        
        if (this.tinh != null && this.tinh != undefined)
            this.tinh.tinhthicong_id = 0;                
    },
    changeSelectSubscription (e) {
      console.log('changeSelectSubscription', e, this.listSubscriptionTypeData.filter(el => el.LOAITB_ID === e.loaitb_id))
    },
    async hienThiTTHopDongKH (data) {
      if (data.length > 0) {
        this.dtpNgayYeuCau.Value = formatDate(data[0]['ngay_yc'])
        this.dtpNgayLapHD.Value = formatDate(data[0]['ngaylap_hd'])
        this.hdkh_id = parseInt(data[0]['hdkh_id'])
        this.txtMaGD.Text = data[0]['ma_gd']
        this.txtMaHD.Text = data[0]['ma_hd']
        this.txtMaKH.Text = data[0]['ma_kh']
        this.txtSoDT.Text = data[0]['so_dt']        
        if (data[0]['MA_DUAN']){
          this.txtMaDA.Text = data[0]['MA_DUAN']
        }
        if (data[0]['khachhang_id']) {
          this.khachhang_id = parseInt(data[0]['khachhang_id'])
          this.khachhang_tc_id = await this.LAY_KHACHHANGID_THICONG(this.khachhang_id);
        } else {
          this.khachhang_id = 0
        }

        this.txtTenKH.Text = data[0]['ten_kh']
        this.txtDiaChiKH.Text = data[0]['diachi_kh']
        if (!data[0]['ctv_id']) {
          this.chkCTV.Checked = true
          this.ctv_id = parseInt(data[0]['ctv_id'])
          this.txtCTV.Text = await api.fn_tt_nhanvien({param:this.ctv_id,type:3})// comment attt: 'ten_nv', 'nhanvien', 'where nhanvien_id = ' + this.ctv_id)
        } else {
          this.txtCTV.Text = ''
          this.chkCTV.Checked = false
        }
        if (!data[0]['nhanviengt_id']) {
          this.chkNguoiGT.Checked = true
          this.nguoigt_id = parseInt(data[0]['nhanviengt_id'])
          this.txtNguoiGT.Text = await api.fn_tt_nhanvien({param:this.nguoigt_id,type:3}) //comment attt: 'ten_nv', 'nhanvien', 'where nhanvien_id = ' + this.nguoigt_id)
        } else {
          this.txtNguoiGT.Text = ''
          this.chkNguoiGT.Checked = false
        }

        if (data[0]['PHIEUCSKH_ID']) {
          this.VPHIEUCSKH_ID = parseInt(data[0]['PHIEUCSKH_ID'])
        }
        //
        this.setButton(3)
        let dsHD = await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TRANGTHAI_HOPDONG.MOI)
        dsHD = dsHD.map(r => this.formatJson(r))
        this.hienThiDanhSacHDTB(dsHD)
        //this.hienThiTTHopDongTB(dsHD)

        if (this.dgvDanhSach.Rows.length > 1) {
          this.tsbtnXoaTB.Enabled = true
        } else {
          this.tsbtnXoaTB.Enabled = false
        }
      } else {
        this.setButton(0)
      }
    },
    async hienThiTTHopDongTB (data) {
      console.log('hienThiTTHopDongTB')
      console.log(data)
      try {
        if (data.length > 0) {
          this.hdtb_id = parseInt(data[0]['hdtb_id']) 
          if (this.dichvuvt_id == DICHVU_VIENTHONG.TSL || this.dichvuvt_id == 25)
              await this.Gan_thongso_bancheo_hdtb_tsl(this.hdtb_id)              
          else
              await this.Gan_thongso_bancheo_hdtb(this.hdtb_id)                 
          if (data[0]['thuebao_id']) { this.thuebao_id = parseInt(data[0]['thuebao_id']) } else { this.thuebao_id = 0 }          
          var ds = await api.lay_tinh_theo_thuebaoid(this.thuebao_id)          
          if (ds && ds.length){            
            this.tinh = { tinhthicong_id: ds[0].tinh_thicong }
            if (this.tinh.tinhthicong_id > 0)
            {
                
                var ds_tbtc = await api.sp_lay_thuebaoid_thicong_theo_matb({
                  'vma_tb': this.txtMaTB.Text,
                  "vtinhthicong":this.tinh.tinhthicong_id,        
                })
                if (ds_tbtc && ds_tbtc.length>0){
                  this.thuebao_tc_id=ds_tbtc[0].thuebao_kn_id
                }
            }
            else
            {
                
                var ds_tinhtc = await api.lay_tinh_thi_cong(data[0]['tinh_thicong'])
                
                if (ds_tinhtc && ds_tinhtc.length){
                  this.tinh = { tinhthicong_id: ds_tinhtc[0].tinh_thicong }
                }
                this.thuebao_tc_id = 0;
            }
          }
          

          if (data[0]['thuebao_cha_id']) { this.thuebao_cha_id = parseInt(data[0]['thuebao_cha_id']) } else { this.thuebao_cha_id = 0 }

          //let dsdc = await api.lay_ds_diachi_theo_dbtbid(this.thuebao_id)
          //this.hienThiTTDiaChi(dsdc)

          this.txtMaTB.Text = data[0]['ma_tb']
          this.txtTenTB.Text = data[0]['ten_tb']
          this.txtDiaChiTB.Text = data[0]['diachi_tb']
          this.txtDiaChiLD.Text = data[0]['diachi_ld']
          this.txtGhiChu.Text = data[0]['ghichu']
          this.dichvuvt_id = parseInt(data[0]['dichvuvt_id'])
          this.cboDichVuVT.SelectedValue = this.dichvuvt_id

          this.cboDichVuVT_SelectedValueChanged()

          this.cboLoaihinhTB.SelectedValue = parseInt(data[0]['loaitb_id'])
          this.loaitb_id = parseInt(data[0]['loaitb_id'])

          this.cboLoaihinhTB_SelectedValueChanged()

          this.doituong_id = parseInt(data[0]['doituong_id'])

          // if (this.doituong_id === DUNG_THU.DOITUONG_TB && this.CHUYENDOI_LH_DUNGTHU === 1) {
          //   this.chkDungThu.Checked = true
          // } else { this.chkDungThu.Checked = false }

          if (data[0]['khlon_id']) { this.khlon_id = parseInt(data[0]['khlon_id']) }
          // Hiển thị kiểu lắp đặt - Hiếu ngày 29.09.2010- Kiểu lắp đặt ăn theo loại hình thuê bao

          var dtKieuLD = await api.lay_ds_kieu_ld(LOAI_HOPDONG.KHOIPHUC_SD, this.loaitb_id)
          this.cboKieuLD.Items = dtKieuLD
          //this.cboKieuLD.Items.unshift({ten_kieuld:'Chọn kiểu lắp đặt',kieuld_id:0})

          var dsdc = await api.lay_ds_diachi_theo_hdtb_id(this.hdtb_id)
          this.hienThiTTDiaChi(dsdc)
          this.cboKieuLD.SelectedValue = parseInt(data[0]['kieuld_id'])

          if (this.dichvuvt_id === DICHVU_VIENTHONG.ADSL) {
            this.cboDaiVT_ADSL.SelectedValue = data[0]['donvi_id']
            var dsmc = await api.sp_tt_muccuoc_tb({ param: parseInt(data[0]['mucuoctb_id']), type: 1 }) //await api.hien_thi_cbo_muc_cuoc(parseInt(data[0]['mucuoctb_id']))
            this.cboMucCuocTB_ADSL.Items = dsmc
            this.cboMucCuocTB_ADSL.SelectedValue = parseInt(data[0]['mucuoctb_id'])
          } 
          else if (this.dichvuvt_id === DICHVU_VIENTHONG.CO_DINH) { this.cboDaiVT_CD.SelectedValue = data[0]['donvi_id'] } 
          else if (this.dichvuvt_id === DICHVU_VIENTHONG.IMS) { this.cboDaiVT_IMS.SelectedValue = data[0]['donvi_id'] } 
          // else if (this.dichvuvt_id === DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
          //               this.dichvuvt_id === DICHVU_VIENTHONG.DICHVU_CNTT ||
          //               this.dichvuvt_id === DICHVU_VIENTHONG.HOINGHI_TRUYENHINH ||
          //               this.dichvuvt_id === DICHVU_VIENTHONG.TRUNGTAM_DULIEU) { this.donvi_ql_cntt = parseInt(data[0]['donvi_id']) }

          for (let i = 0; i < data.length; i++) {
            if (parseInt(data[i]['dichvuvt_id']) === DICHVU_VIENTHONG.ADSL &&
                            (parseInt(data[i]['loaitb_id']) === LOAIHINH_THUEBAO.INTERNET_ADSL ||
                                parseInt(data[i]['loaitb_id']) === LOAIHINH_THUEBAO.INTERNET_FTTH ||
                                parseInt(data[i]['loaitb_id']) === LOAIHINH_THUEBAO.WIFI_FIBER)) {
              if (parseInt(data[i]['status']) === 1) {
                this.check_tb_adsl = true
                break
              }
            }
          }

          this.hienThiTTThanhToan(this.txtMaTB.Text.trim(), this.dichvuvt_id, 0)
          this.hienThiTTHDMoRong(this.hdtb_id)

          if (this.dichvuvt_id !== DICHVU_VIENTHONG.TSL && this.dichvuvt_id !== 25) {
            var dstc = await api.lay_donvi_thicong(this.hdtb_id, 2, LOAI_DONVI.TRAM_VT, 2)
            if (dstc.lengtht > 0) {
              if (this.dichvuvt_id === DICHVU_VIENTHONG.CO_DINH) { this.cboTram_CD.SelectedValue = parseInt(dstc[0]['donvi_id']) }
              if (this.dichvuvt_id === DICHVU_VIENTHONG.ADSL) { this.cboVeTinh_Adsl.SelectedValue = parseInt(dstc[0]['donvi_id']) }
              if (this.dichvuvt_id === DICHVU_VIENTHONG.IMS) { this.cboTram_IMS.SelectedValue = parseInt(dstc[0]['donvi_id']) }
              if (this.dichvuvt_id === DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id === DICHVU_VIENTHONG.METRONET || this.dichvuvt_id === 26) { this.cboTram_MGW.SelectedValue = parseInt(dstc[0]['donvi_id']) }
            }
          }

          this.m_dsThueBao_DichVu.dsdvgt_ban = await api.lay_ds_thuebao_dichvu(this.hdtb_id, 0, parseInt(this.cboLoaihinhTB.SelectedValue), 0)
          this.HT_DichVuGT()

          this.dsDK_DVKHAC = await api.lay_ds_dangky_dvk(this.hdtb_id, 1)
          this.hienThiDichVuKhac()

          this.ds_tien_khoanmuc = await api.lay_tienhd_km_theo_hdtb(this.hdtb_id)
          for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
            if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] === KHOANMUC_TT.KMTT_KHOIPHUCSD) {
              this.dtienld = parseInt(this.ds_tien_khoanmuc[i]['tien'])
              this.dvatld = parseInt(this.ds_tien_khoanmuc[i]['vat'])
            }

            if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] === KHOANMUC_TT.KMTT_DICHVU) {
              this.dtien_dv = parseInt(this.ds_tien_khoanmuc[i]['tien'])
              this.dvat_dv = parseInt(this.ds_tien_khoanmuc[i]['vat'])
            }
          }
          this.LayTongTien_HDTB()
          this.NapThongTinDichVuThueBao() 
          this.tsbtnXoaTB.Enabled = (this.dgvDanhSach.Rows.length >= 2)
        }
      } catch (ex) {
        this.$toast.error('Có lỗi')
        console.log(ex)
      }   
    },
    async hienThiTTDanhBa (maTB) {
      try {
        let ds ={}
        if (this.tinh.tinhthicong_id>0 && this.kenh_trang==false) {          
          var data = {
              "in_ma_tb": maTB,
              "in_dichvuvt_id": this.dichvuvt_id,
              "in_donvi_dl_id": 0,
              "in_loaihd_bancheo": 1
          }
          
          ds = await api.lay_danhba_theo_matb_bc(data)
        }
        else {          
          ds = await api.lay_danhba_theo_matb(maTB, this.dichvuvt_id, 0)
        }
        this.dsDBTB = ds
        if (ds && ds.length > 0) {
          if (ds[0]['dichvuvt_id'] == DICHVU_VIENTHONG.ADSL) {
            if (ds[0]['loaitb_id'] != LOAIHINH_THUEBAO.INTERNET_ADSL &&
                            ds[0]['loaitb_id'] != LOAIHINH_THUEBAO.INTERNET_FTTH &&
                            ds[0]['loaitb_id'] != LOAIHINH_THUEBAO.WIFI_FIBER
            ) {
              var loaihinhTB = await api.fn_tt_loaihinh_tb({param:ds[0]['loaitb_id'],type:1})// attt:'loaihinh_tb', 'loaihinh_tb', 'where loaitb_id = ' + ds[0]['loaitb_id'])
              this.$toast.error('Không thực hiện khôi phục thanh lý với thuê bao ' + loaihinhTB)
              return
            }
          }
          if (parseInt(ds[0]['trangthaitb_id']) == TRANGTHAI_TB.THANHLY || parseInt(ds[0]['trangthaitb_id']) == TRANGTHAI_TB.THANHLY_NO) {
            // Thông tin khách hàng
            this.txtMaKH.Text = ds[0]['ma_kh']
            this.txtMaHD.Text = ds[0]['ma_hd']            
            if (ds[0]['MA_DUAN']){
              this.txtMaDA.Text = ds[0]['MA_DUAN']
            }
            this.khachhang_id = parseInt(ds[0]['khachhang_id'])
            this.txtTenKH.Text = ds[0]['ten_kh']
            this.txtDiaChiKH.Text = ds[0]['diachi_kh']
            if (ds[0]['ngaylap_hd']) { this.dtpNgayLapHD.Value = formatDate(ds[0]['ngaylap_hd']) } else { this.dtpNgayLapHD.Value = this.$auth.getNgayCapNhat() }
            this.txtSoDT.Text = ds[0]['so_dt']

            // thông tin thanh toán
            this.txtMaTT.Text = ds[0]['ma_tt']
            this.txtTenTT.Text = ds[0]['ten_tt']
            this.txtDiaChiTT.Text = ds[0]['diachi_tt']

            // Thông tin thuê bao
            this.thuebao_id = parseInt(ds[0]['thuebao_id'])            
            if (this.tinh.tinhthicong_id > 0 && this.kenh_trang == false){              
              var ds_tbtc = await api.sp_lay_thuebaoid_thicong_theo_matb({
                'vma_tb': this.txtMaTB.Text,
                "vtinhthicong":this.tinh.tinhthicong_id,        
              })
              if (ds_tbtc && ds_tbtc.length>0){
                this.thuebao_tc_id=ds_tbtc[0].thuebao_kn_id
              }              
              var data = {
                  "in_ma_tb": this.txtMaTB.Text,
                  "in_dichvuvt_id": this.dichvuvt_id,
                  "in_donvi_dl_id": 0,
                  "in_loaihd_bancheo": 1
              }
              
              var ds_dba_bc = await api.lay_danhba_theo_matb_bc(data)
              
              if (ds_dba_bc && ds_dba_bc.length>0){
                this.dsDBTBBC = ds_dba_bc
              }
              else this.dsDBTBBC={}
              
            }
            else {
                this.thuebao_tc_id = 0;
                this.dsDBTBBC = null;
            }

            if (ds[0]['thuebao_cha_id']) { this.thuebao_cha_id = parseInt(ds[0]['thuebao_cha_id']) } else {
              this.thuebao_cha_id = 0
            }

            this.txtMaTB.Text = ds[0]['ma_tb']
            this.txtTenTB.Text = ds[0]['ten_tb']
            this.txtDiaChiTB.Text = ds[0]['diachi_tb']
            this.txtDiaChiLD.Text = ds[0]['diachi_ld']
            this.txtGhiChu.Text = ds[0]['ghichu_tb']
            this.loaitb_id = parseInt(ds[0]['loaitb_id'])
            var dsdc = await api.lay_ds_diachi_theo_dbtbid(this.thuebao_id)
            this.hienThiTTDiaChi(dsdc)
            this.cboLoaihinhTB.SelectedValue = parseInt(this.loaitb_id)
            this.cboLoaihinhTB_SelectedValueChanged()
            var data1 = {
              "p_loaitb_id": this.cboLoaihinhTB.SelectedValue,
              "p_loaihd_id": LOAI_HOPDONG.KHOIPHUC_SD,
              "p_kieu": 2
            }
            
            this.cboKieuLD.Items = await api.sp_lay_ds_kieu_ld_bc(data1)
            //this.cboKieuLD.Items.unshift({ten_kieuld:'Chọn kiểu lắp đặt',kieuld_id:0})

            if (this.loaitb_id == LOAIHINH_THUEBAO.INTERNET_FTTH && this.CHUYENDOI_LH_DUNGTHU == 1) {
              this.chkDungThu.Enabled = true
            } else {
              this.chkDungThu.Enabled = false
              this.chkDungThu.Checked = false
            }

            if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) {
              console.log('set lại đv quản lý')
              console.log(ds[0]['donviql_id'])

              this.cboDaiVT_ADSL.SelectedValue = parseInt(ds[0]['donviql_id'])
              this.cboDaiVT_ADSL_SelectedValueChanged()
              console.log('sp_tt_muccuoc_tb')
              console.log(ds[0]['mucuoctb_id'])
              if (ds[0]['mucuoctb_id']) {
                var dsmc = await api.sp_tt_muccuoc_tb({ param: parseInt(ds[0]['mucuoctb_id']), type: 1 })                
                
                this.cboMucCuocTB_ADSL.Items = dsmc
                if (dsmc&&dsmc.length > 0) {
                  this.cboMucCuocTB_ADSL.SelectedValue = dsmc[0]['mucuoctb_id']
                }
              } else {
                this.$toast.error('Không tìm thấy mức cước thuê bao. Liên hệ admin để xử lý !')
                this.cboMucCuocTB_ADSL.Items = []
                this.clear()
                return
              }
            } else if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26) {
              this.cboDVQL_MGW.SelectedValue = parseInt(ds[0]['donviql_id'])
              // this.cboDVQL_MGW_SelectedValueChanged()
            } else if (this.dichvuvt_id == DICHVU_VIENTHONG.CO_DINH) {
              this.cboDaiVT_CD.SelectedValue = parseInt(ds[0]['donviql_id'])
              this.cboDaiVT_CD_SelectedValueChanged()

              if (ds[0]['mucuoctb_id']) {
                var dsmc = await api.sp_tt_muccuoc_tb({ param: parseInt(ds[0]['mucuoctb_id']), type: 1 })
                this.cboMucCuocTB_CD.Items = dsmc
                if (dsmc&&dsmc.length > 0) {
                  this.cboMucCuocTB_CD.SelectedValue = dsmc[0]['mucuoctb_id']
                }
                // bangts.HT_MucCuocTB_Combobox(this.cboMucCuocTB_CD, parseInt(ds[0]['mucuoctb_id']))
              } else {
                this.$toast.error('Không tìm thấy mức cước thuê bao. Liên hệ admin để xử lý !')
                this.clear()
                return
              }
            } else if (this.dichvuvt_id == DICHVU_VIENTHONG.IMS) {
              this.cboDaiVT_IMS.SelectedValue = parseInt(ds[0]['donviql_id'])
              this.cboDaiVT_IMS_SelectedValueChanged()
              if (ds[0]['mucuoctb_id']) {
                var dsmc = await api.sp_tt_muccuoc_tb({ param: parseInt(ds[0]['mucuoctb_id']), type: 1 })
                this.cboMucCuocTB_IMS.Items = dsmc
                if (dsmc&&dsmc.length > 0) {
                  this.cboMucCuocTB_IMS.SelectedValue = dsmc[0]['mucuoctb_id']
                }
                // bangts.HT_MucCuocTB_Combobox(this.cboMucCuocTB_IMS, parseInt(ds[0]['mucuoctb_id']))
              } else {
                this.$toast.error('Không tìm thấy mức cước thuê bao. Liên hệ admin để xử lý !')
                this.clear()
                return
              }
            } else if (this.dichvuvt_id == DICHVU_VIENTHONG.GPHONE) {
              this.cboDVQLGP.SelectedValue = parseInt(ds[0]['donviql_id'])
            } 

            if (this.dichvuvt_id != DICHVU_VIENTHONG.TSL && this.dichvuvt_id != 25) {
              var dstc = await api.lay_donvi_thicong(this.thuebao_id, 1, LOAI_DONVI.TRAM_VT, 2)
              if (dstc.length > 0) {
                if (this.dichvuvt_id == DICHVU_VIENTHONG.CO_DINH) { this.cboTram_CD.SelectedValue = parseInt(dstc[0]['donvi_id']) }
                if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) { this.cboVeTinh_Adsl.SelectedValue = parseInt(dstc[0]['donvi_id']) }
                if (this.dichvuvt_id == DICHVU_VIENTHONG.IMS) { this.cboTram_IMS.SelectedValue = parseInt(dstc[0]['donvi_id']) }
                if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26) { this.cboTram_MGW.SelectedValue = parseInt(dstc[0]['donvi_id']) }
              }
            }

            this.hienThiTTDBMoRong()            
            if (ds[0]['ngay_cat']) {
              this.dtpNgayTL.Value = formatDate(ds[0]['ngay_cat'])
            }

            this.doituong_id = parseInt(ds[0]['doituong_id'])
            if (ds[0]['khlon_id']) { this.khlon_id = parseInt(ds[0]['khlon_id']) }

            this.dsSDDVK = await api.lay_ds_sudung_dvk(this.thuebao_id, 1)

            this.hienThiDichVuKhac_DB()

            this.thongso_ban = this.Reset_Thongso_Thuebao();
            this.thongso_tc = this.Reset_Thongso_Thuebao();
            this.gan_thongso_theodanhba(1, this.dichvuvt_id, this.thuebao_id);

            if (this.thuebao_tc_id != 0)
              this.gan_thongso_theodanhba(2, this.dichvuvt_id, this.thuebao_tc_id);

            this.NapThongTinDichVuThueBao() 
            
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
        this.$toast.error('Có lỗi : ' + ex)
        console.log(ex)
      }
    },
    hienThiDanhSacHDTB (data) {
      if (data.length > 0) {
        data = data.map((r, index) => ({ ...r, stt: index + 1 }))
        this.dgvDanhSach.Rows = data

        console.log('list data', data)

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
    },
    hienThiTTDiaChi (ds) {
      if (ds.length > 0) {
        if (ds[0]['phuongld_id']) { this.phuongld_id = parseInt(ds[0]['phuongld_id']) } else { this.phuongld_id = 0 }
        if (ds[0]['quanld_id']) { this.quanld_id = parseInt(ds[0]['quanld_id']) } else { this.quanld_id = 0 }
        if (ds[0]['dacdiemld_id']) { this.dacdiemld_id = parseInt(ds[0]['dacdiemld_id']) } else { this.dacdiemld_id = 0 }
        if (ds[0]['phold_id']) { this.phold_id = parseInt(ds[0]['phold_id']) } else { this.phold_id = 0 }
        if (ds[0]['apld_id']) { this.apld_id = parseInt(ds[0]['apld_id']) } else { this.apld_id = 0 }
        if (ds[0]['khuld_id']) { this.khuld_id = parseInt(ds[0]['khuld_id']) } else { this.khuld_id = 0 }
      }
    },
    async hienThiTTThanhToan (maTb, dichVuID, donvidlID) {
      var dsThanhToan = await api.lay_danhba_theo_matb(maTb, dichVuID, donvidlID)
      if (dsThanhToan.length > 0) {
        this.txtMaTT.Text = dsThanhToan[0]['ma_tt']
        this.txtTenTT.Text = dsThanhToan[0]['ten_tt']
        this.txtDiaChiTT.Text = dsThanhToan[0]['diachi_tt']
      }
    },
    async hienThiTTHDMoRong (hdtbID) {
      try {
        this.tocdo_id = 0
        this.muccuoc_id = 0

        if (this.dichvuvt_id === DICHVU_VIENTHONG.ADSL) {
          var hdcon = await api.lay_ds_hopdong_thuebao_adsl(hdtbID)
          console.log('hienThiTTHDMoRong')
          console.log(hdcon)
          if (hdcon.length > 0) {
            // cboVeTinh_Adsl.SelectedValue = parseInt(hdcon.Tables[0].Rows[0]['tramtb_id'].toString())
            this.cboTocDo_Adsl.SelectedValue = parseInt(hdcon[0]['tocdo_id'])
            this.cboMucCuoc_ADSL.SelectedValue = parseInt(hdcon[0]['muccuoc_id'])
            this.tocdo_id = parseInt(hdcon[0]['tocdo_id'])
            this.muccuoc_id = parseInt(hdcon[0]['muccuoc_id'])
            this.txtMaTN_ADSL.Text = hdcon[0]['matb_tn']
            this.txtSoAo_ADSL.Text = hdcon[0]['ma_lt']
            this.pass_adsl = hdcon[0]['password']
            this.txtMaTN_ADSL.Text = hdcon[0]['matb_tn']
          }
        }


        if (this.dichvuvt_id === DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id === DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26) {
          var hdcon = await api.lay_ds_hopdong_thuebao_mgwan(hdtbID)
          if (hdcon.length > 0) {
            this.tocdo_id = parseInt(hdcon[0]['tocdo_id'])
            this.cboTocDo_MGW.SelectedValue = this.tocdo_id
            this.txtSoAo_Wan.Text = hdcon[0]['ma_lt']
            this.vhuong_kn = hdcon[0]['huong_kn']
            if (hdcon[0]['huong_kn']) { this.txtHuongKN.Text = await api.lay_text_huongketnoi(this.vhuong_kn) } else { this.txtHuongKN.Text = '' }
          }
        }

        if (this.dichvuvt_id == DICHVU_VIENTHONG.TSL || this.dichvuvt_id == 25) {
          var hdcon = await api.lay_ds_db_tsl_theo_tbid(this.thuebao_id) 
          if (hdcon.length > 0)
          {
              this.vloaikenh_id = hdcon[0]['loaikenh_id'] 
          }
        }

        if (this.dichvuvt_id === DICHVU_VIENTHONG.IMS) {
          // var hdcon = await api.lay_ds_hopdong_thuebao_ims(hdtbID)
          // if (hdcon.length > 0) {
          //   this.pass_ims = hdcon[0]['password']
          //   this.txtMaTN_ADSL.Text = hdcon[0]['matb_tn']
          // }
        }

        if (this.dichvuvt_id === DICHVU_VIENTHONG.GPHONE) {
          var hdcon = await api.lay_ds_hopdong_thuebao_gp(hdtbID)
          if (hdcon.length > 0) {
            this.txtSoSimGphone.Text = hdcon[0]['simcard']
          }
        }

        
      } catch (ex) {
        this.$toast.error('Có lỗi : ' + ex)
        console.log(ex)
      }
    },
    async hienThiTTDBMoRong () {
      try {
        let tocdoID = 0
        let muccuocID = 0
        if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) {
          var dbcon = await api.lay_ds_db_adsl_theo_tbid(this.thuebao_id)
          if (dbcon && dbcon.length > 0) {
            // nhapt thêm việc kiểm tra tốc độ:24012017
            /// nếu tốc độ cũ của thuê bao đã hết thời hạn sử dụng
            ///  sẽ để user chọn tốc độ khác phù hợp
            ///  còn nếu vẫn còn thời hạn thì enable ô chọn tốc độ adsl

            tocdoID = parseInt(dbcon[0]['tocdo_id'])
            muccuocID = parseInt(dbcon[0]['muccuoc_id'])
            this.tocdo_id = tocdoID
            this.cboTocDo_Adsl.SelectedValue = tocdoID
            if (this.cboTocDo_Adsl.SelectedValue == null) {
              this.$toast.error('Tốc độ ADSL chưa đúng. Bạn hãy kiểm tra lại!')
              this.cboTocDo_Adsl.Enabled = true
              return
            } else {
              this.cboTocDo_Adsl.Enabled = false
            }
            console.log('hienThiTTDBMoRong')
            console.log(dbcon[0]['muccuoc_id'])
            this.cboMucCuoc_ADSL.SelectedValue = muccuocID
            if (!this.cboMucCuoc_ADSL.SelectedValue) {
              this.$toast.error('Mức cước ADSL chưa đúng. Bạn hãy kiểm tra lại!')
              return
            }
            this.txtSoAo_ADSL.Text = dbcon[0]['ma_lt']
            
          }
        }
        if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26) {
          var dbcon = await api.lay_ds_danhba_megawan(this.thuebao_id)
          if (dbcon && dbcon.length > 0) {
            tocdoID = parseInt(dbcon[0]['tocdo_id'])
            this.tocdo_id = tocdoID
            this.cboTocDo_MGW.SelectedValue = tocdoID
            this.txtSoAo_Wan.Text = dbcon[0]['ma_lt']
            this.vhuong_kn = dbcon[0]['huong_kn']
            if (dbcon[0]['huong_kn']) { this.txtHuongKN.Text = await api.lay_text_huongketnoi(this.vhuong_kn) } else { this.txtHuongKN.Text = '' }
            this.vloaikenh_id = dbcon[0]['loaikenh_id']
          }
        }
        if (this.dichvuvt_id == DICHVU_VIENTHONG.TSL || this.dichvuvt_id == 25) {
          var dbcon = await api.lay_ds_db_tsl_theo_tbid(this.thuebao_id) 
          if (dbcon && dbcon.length > 0)
          {
              this.vloaikenh_id = dbcon[0]['loaikenh_id'] 
          }          
        }        
        
      } catch (ex) {
        this.$toast.error('Có lỗi : ' + ex)
        console.log(ex)
      }
    },
    async hienThiDichVuKhac () {
      let str
      this.lvDvkhac.Items = []
      for (let i = 0; i < this.dsDK_DVKHAC.length; i++) {
        var ds = await api.lay_tt_dichvu_khac(parseInt(this.dsDK_DVKHAC[i]["dvkhac_id"]))
        str = ds // [0]['ten_dvkhac']
        if (this.dsDK_DVKHAC[i]['kieu_yc'] == 1) { str = str + ' (ĐK)' } else { str = str + ' (Hủy)' }
        this.lvDvkhac.Items.push({ id: this.dsDK_DVKHAC[i]["dvkhac_id"], text: str })
      }
    },
    async hienThiDichVuKhac_DB () {
      let str
      this.lvDvkhac.Items = []
      for (let i = 0; i < this.dsSDDVK.length; i++) {
        var ds = await api.lay_tt_dichvu_khac(parseInt(this.dsSDDVK[i]['dvkhac_id']))
        if (ds && ds.length && ds.length > 0) {
          str = ds // [0]['ten_dvkhac']
          this.lvDvkhac.Items.push({ id: this.dsSDDVK[i]['dvkhac_id'], text: str })
        }
      }
    },
    async kiemTraDLThueBao () {
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
      if (this.cboKieuLD.SelectedValue == null ||this.cboKieuLD.SelectedValue == 0) {
        this.$toast.error('Hãy chọn kiểu yêu cầu cho thuê bao này !')
        return false
      }

      var mapIDKieu = await api.fn_tt_kieu_ld({param:this.cboKieuLD.SelectedValue,type:1})// attt'kieu', 'kieu_ld', 'where kieuld_id =' + this.cboKieuLD.SelectedValue)
      let kieu = parseInt(mapIDKieu)

      if ((kieu == 1 || kieu == 2) &&
                (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL ||
                parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.CO_DINH ||
                parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.IMS) &&
                this.txtMaTN_ADSL.Text.trim().length == 0) {
        this.$toast.error('Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!')
        this.$refs.txtMaTN_ADSL.focus()
        return false
      }
      
      if (parseInt(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_MYTV) {
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
      if (parseInt(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_SGTV) {
        this.$toast.error('Không khôi phục thanh lý cho thuê bao SaiGonTV !')
        this.$refs.txtMaTB.focus()
        return false
      }
      

      var dskt = await api.sp_lay_db_theo_matb(this.txtMaTB.Text.trim(), 0, parseInt(this.cboDichVuVT.SelectedValue))
      if (dskt.length > 0) {
        // var dr = dskt.Select('trangthaitb_id in(' + TrangThaiTB.THANHLY + ',' + TrangThaiTB.THANHLY_NO + ')')
        var dr = dskt.filter(r => [TRANGTHAI_TB.THANHLY, TRANGTHAI_TB.THANHLY_NO].includes(r['trangthaitb_id']))
        if (dr.length > 0) {
          if (parseInt(dr[0]['loaitb_id']) != parseInt(this.cboLoaihinhTB.SelectedValue)) {
            this.$toast.error('Không được thay đổi loại hình thuê bao của thuê bao : ' + this.txtMaTB.Text.trim())
            return false
          }
        }
      } else {
        this.$toast.error('Không tìm thấy thông tin thuê bao : ' + this.txtMaTB.Text.trim())
        return false
      }

      
      // nhapt:24/01/2017: thêm kiểm tra tốc độ
      if (this.cboTocDo_Adsl.SelectedValue == null && this.dichvuvt_id == 4) {
        this.$toast.error('Tốc độ ADSL chưa đúng. Bạn hãy kiểm tra lại!')
        return false
      }
      // bổ sung đoạn kiểm tra thông tin tính cước not null
      // yêu cầu từ jira BGG
      // Anh Hiếu HPG yêu cầu bỏ 20/07/2023
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

      // SonNMP: Không có khu vực không có tạo hợp đồng
      // Tuan Anh xac nhan: ban cheo hong có khu vuc
      // if (!this.dsHDTB_KV || !this.dsHDTB_KV.HDTB_KV || this.dsHDTB_KV.HDTB_KV.length == 0 || this.dsHDTB_KV.HDTB_KV[0]['khuvuc_id'] == '0') {
      //   this.$toast.error('Thuê bao chưa có khu vực, cần chọn khu vực')
      //   this.$refs.txtKhuVuc.focus()
      //   return false
      // }

      return true
    },
    async initProperties (loaitbId, tableName, optList, ItemList) {
      try {
        var ds = await api.lay_danhsach_thuoctinh_v2(loaitbId, tableName, optList)
        if (ds.length == 0) { return }

        ItemList = []

        for (let i = 0; i < ds.length; i++) {
          var row = ds[i]
          var _item = {}
          _item['sFieldName'] = row['field_name']
          _item['sCaption'] = row['ten_hienthi']
          _item['iAtt'] = parseInt(row['att'])
          _item['iDataType'] = parseInt(row['data_type'])
          _item['sMaxValue'] = row['max_value']
          _item['sMinvalue'] = row['min_value']
          _item['sValue'] = row['field_value']
          _item['nFieldLength'] = !row['field_length'] ? 0 : parseInt(row['field_length'])
          _item['bIsNotNull'] = !row['field_nullable'] ? false : (row['field_nullable'] == "0")
          _item['bIsUnique'] = !row['field_unique'] ? false : (row['field_unique'] != "0")
          _item['sLookUpTableName'] = row['parent_table']
          _item['sLookUpKeyField'] = row['parent_keyfield']
          _item['sLookUpValueField'] = row['parent_descfield']
          _item['strDepandField'] = row['depend_field']
          _item['StrSql'] = row['lookup_sql']
          ItemList.push(_item)
        }
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex)
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
        this.thongso_ban = this.Reset_Thongso_Thuebao()
        this.thongso_tc = this.Reset_Thongso_Thuebao()
      }
      // Bat dau
      if (kieu === 0) {
        this.$refs.txtMaTB.focus()
        this.tsbtnNhapMoi.Enabled = true
        this.clear()
        this.thongso_ban = this.Reset_Thongso_Thuebao()
        this.thongso_tc = this.Reset_Thongso_Thuebao()
      }
      // Them moi
      if (kieu === 1) {
        this.$refs.txtMaTB.focus()
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.tsbtnXoaTB.Enabled = false
        this.clear()
        this.thongso_ban = this.Reset_Thongso_Thuebao()
        this.thongso_tc = this.Reset_Thongso_Thuebao()
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
      this.kenh_trang = true
      this.chi_giao_net = true
      this.donvi_ql_cntt = 0
      this.hdkh_id = 0
      this.txtMaGD.Text = ''
      this.txtMaHD.Text = ''
      this.txtMaKH.Text = ''
      this.khachhang_id = 0
      this.txtTenKH.Text = ''
      this.txtDiaChiKH.Text = ''
      this.cboKieuLD.SelectedValue = 0
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

      this.vloaikenh_id = 0
      this.txtTongTienHD.Text = '0'

      this.dsDK_DVKHAC = []

      this.cboLoaihinhTB.SelectedValue = 1
      this.LAY_DS_DVGT_MACDINH(parseInt(this.cboLoaihinhTB.SelectedValue))
      // LAY_DS_DVK_MACDINH_TB(parseInt(cboLoaihinhTB.SelectedValue)) //dsDK_DVK_TB hàm load thông tin ko còn được dùng trong code C#
    },
    async onTransactionCodeChange (e) {
      if (e.key === 'Enter') {
        if (this.txtMaGD.Text && this.txtMaGD.Text.length > 0) {
          this.loading(true)
          var ds = await api.lay_ds_hopdong_theo_ma_gd(this.txtMaGD.Text.trim(), LOAI_HOPDONG.KHOIPHUC_SD, TRANGTHAI_HOPDONG.MOI)
          await this.hienThiTTHopDongKH(ds)
          if (ds.length > 0) {
            this.dtpNgayYeuCau.Value = moment(ds[0]['ngay_yc'],'DD/MM/yyyy HH:mm:ss').format('DD/MM/yyyy')
            this.dtpNgayLapHD.Value = moment(ds[0]['ngaylap_hd'],'DD/MM/yyyy HH:mm:ss').format('DD/MM/yyyy')
            let ds1 ={}
            if (this.tinh.tinhthicong_id>0 && this.kenh_trang==false) {          
              console.log('onTransactionCodeChange1')
              var data = {
                  "in_ma_tb": ds[0]['ma_tb'],
                  "in_dichvuvt_id": ds[0]['dichvuvt_id'],
                  "in_donvi_dl_id": 0,
                  "in_loaihd_bancheo": 1
              }
              
              ds1 = await api.lay_danhba_theo_matb_bc(data)
              if(ds1 && ds1.length>0){                
                this.dtpNgayTL.Value = moment(ds1[0]['ngay_cat'],'yyyy/MM/DD HH:mm:ss').format('DD/MM/yyyy')//formatDate(ds1[0]['ngay_cat'])
              }
            }
            else {    
              console.log('onTransactionCodeChange2')      
              ds1 = await api.lay_danhba_theo_matb(ds[0]['ma_tb'], ds[0]['dichvuvt_id'], 0)
              if(ds1 && ds1.length>0){                
                this.dtpNgayTL.Value = moment(ds1[0]['ngay_cat'],'DD/MM/yyyy HH:mm:ss').format('DD/MM/yyyy')//formatDate(ds1[0]['ngay_cat'])
              }
            }
            
          }

          this.loading(false)
        }
      }
    },
    btnNhapMoiClick () {
      this.setButton(1)
    },
    btnHuyBoClick () {
      this.setButton(0)
    },
    async btnGhiLaiClick () {
      try {
        if (!this.kenh_trang && this.tinh.tinhthicong_id > 0 && (this.thongso_ban.muccuoctb_id == 0 || this.thongso_tc.muccuoctb_id == 0) && this.thongso_tc.kieuld_id > 0 && (this.dichvuvt_id != DICHVU_VIENTHONG.TSL && this.dichvuvt_id != 25))
        {
            if (!this.chi_giao_net)
              this.$toast.error("Bạn chưa chọn mức cước cho thuê bao bán và thi công. Ấn vào kiểu lắp đặt để lấy thông tin")              
            else
              this.$toast.error("Bạn chưa chọn thông số. Ấn vào kiểu yêu cầu để lấy thông tin")                            
            return;
        }
        if (this.kenh_trang && (this.thongso_ban.muccuoctb_id == 0 || this.thongso_tc_dau.muccuoctb_id == 0 || this.thongso_tc_cuoi.muccuoctb_id == 0) && (this.dichvuvt_id == DICHVU_VIENTHONG.TSL || this.dichvuvt_id == 25))
        {            
            this.$toast.error("Bạn chưa chọn thông số cho kênh trắng. Ấn vào điểm đầu, điểm cuối để lấy thông tin")                            
            return;
        }
        
        this.loading(true)

        var rsKiemTraDuLieuTB = await this.kiemTraDLThueBao()
        if (!rsKiemTraDuLieuTB) {
          this.loading(false)
          return
        }
        

        if (this.tsbtnNhapMoi.Enabled == false) {
          let rsLapHDKhac = await api.kiemtra_ma_tb_lap_hopdong_khac(this.txtMaTB.Text.trim(), "0", parseInt(this.cboDichVuVT.SelectedValue), LOAI_HOPDONG.KHOIPHUC_SD)
          rsLapHDKhac = rsLapHDKhac.filter(r => r.loaihd_id == LOAI_HOPDONG.KHOIPHUC_SD)
          if (rsLapHDKhac && rsLapHDKhac.length > 0) {
            this.$toast.error('Thuê bao đã lập hợp đồng khác: \n - Tên hợp đồng: ' + rsLapHDKhac[0]['ten_loaihd'])
            this.loading(false)
            return
          }

          let statusVdc = true //code bán thường là false# code bán chéo là true?????
          

          var dshg = await api.layhuonggiao_tiepnhan_v2(LOAI_HOPDONG.KHOIPHUC_SD, this.loaitb_id, this.tocdo_id, this.muccuoc_id, parseInt(this.cboKieuLD.SelectedValue), this.vloaikenh_id, parseInt(this.cboDichVuVT.SelectedValue))
          let huonggiaoTN = 0
          this.quytrinh_id = 0
          this.luong_id = 0
          if (dshg && dshg.length <= 0) {
            this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            this.loading(false)
            return
          }
          huonggiaoTN = parseInt(dshg[0]['huonggiao_id'])
          this.quytrinh_id = parseInt(dshg[0]['quytrinh_id'])
          this.luong_id = parseInt(dshg[0]['luong_id'])

          //bán chéo          

          if (this.dichvuvt_id == DICHVU_VIENTHONG.TSL || this.dichvuvt_id == 25)
          {
              if (!this.thongso_tc_dau.kieuld_id || !this.thongso_tc_dau.tocdo_id)
              {
                this.$toast.error("Chưa chọn thông số lắp đặt đầu.")
                this.loading(false)
                return
              }

              if (!this.thongso_tc_cuoi.kieuld_id || !this.thongso_tc_cuoi.tocdo_id)
              {
                this.$toast.error("Chưa chọn thông số lắp đặt cuối.")
                this.loading(false)
                return
              }
          }
          else
              if (this.thongso_tc.kieuld_id == 0)
              {
                this.$toast.error("Chưa chọn thông số lắp đặt.")
                this.loading(false)
                return
              }

          if (this.dichvuvt_id == DICHVU_VIENTHONG.TSL || this.dichvuvt_id == 25)
          {   
              let rs_qt_dau = await this.LayHuongGiao_TiepNhan_BC(this.tinh_dau.tinhthicong_id, LOAI_HOPDONG.KHOIPHUC_SD, 
                  parseInt(this.cboLoaihinhTB.SelectedValue), this.thongso_tc_dau.tocdo_id, this.thongso_tc_dau.muccuoc_id, 
                  this.vloaikenh_id, this.thongso_tc_dau.kieuld_id != undefined ? this.thongso_tc_dau.kieuld_id : 0)
              if (!rs_qt_dau || rs_qt_dau.length <= 0) {
                  this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công điểm đầu. Liên hệ Admin để xử lý")
                  this.loading(false)
                  return;
              }
              this.quytrinh_tc_tn_dau = 0;
              this.huonggiao_tc_tn_dau = 0;
              this.quytrinh_tc_tn_dau = Number(rs_qt_dau.quytrinh_id);
              this.huonggiao_tc_tn_dau = Number(rs_qt_dau.huonggiao_id);

              // Lấy hướng giao, quy trình tại điểm cuối
              let rs_qt_cuoi = await this.LayHuongGiao_TiepNhan_BC(this.tinh_cuoi.tinhthicong_id, LOAI_HOPDONG.KHOIPHUC_SD, 
                  parseInt(this.cboLoaihinhTB.SelectedValue), this.thongso_tc_cuoi.tocdo_id, this.thongso_tc_cuoi.muccuoc_id, 
                  this.vloaikenh_id, this.thongso_tc_cuoi.kieuld_id != undefined ? this.thongso_tc_cuoi.kieuld_id : 0)
              if (!rs_qt_cuoi ||rs_qt_cuoi.length <= 0) {
                  this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công điểm cuối. Liên hệ Admin để xử lý");
                  this.loading(false)
                  return;
              }
              this.quytrinh_tc_tn_cuoi = 0;
              this.huonggiao_tc_tn_cuoi = 0;
              this.quytrinh_tc_tn_cuoi = Number(rs_qt_cuoi.quytrinh_id);
              this.huonggiao_tc_tn_cuoi = Number(rs_qt_cuoi.huonggiao_id);

          }
          else
          {                            
            var muccuoc_id = this.thongso_tc.muccuoc_id ? this.thongso_tc.muccuoc_id : 0
            let rs_qt = await this.LayHuongGiao_TiepNhan_BC(this.tinh.tinhthicong_id, LOAI_HOPDONG.KHOIPHUC_SD, 
                parseInt(this.cboLoaihinhTB.SelectedValue), this.thongso_tc.tocdo_id, muccuoc_id, 
                this.vloaikenh_id, this.thongso_tc.kieuld_id != undefined ? this.thongso_tc.kieuld_id : 0)
            
            if (!rs_qt || rs_qt.length <= 0) {
                this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý")
                this.loading(false)
                return
            }
            this.quytrinh_tc_tn = 0;
            this.huonggiao_tc_tn = 0;
            this.quytrinh_tc_tn = Number(rs_qt.quytrinh_id);
            this.huonggiao_tc_tn = Number(rs_qt.huonggiao_id);
          }
          
          //end bán chéo          
          await this.TaoDuLieu(true)          
          await this.TaoDuLieuBC(true) 
          if (this.loi_sinh_magd) {
            this.loi_sinh_magd = false
            this.loading(false)
            return
          }
          

          // #region Insert dữ liệu vào DataBase và hiển thị

          var rs = await api.themmoi_hopdong_khoiphucthanhly_v2(this.dsHDKH, this.dsHDTB, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsHDTB_MGW, this.dsDK_DVGT, this.dsDK_DVKHAC, this.dsCTTHD, this.dsHDTBDV, huonggiaoTN, this.dsHDTB_IMS, this.dsHDTB_KV, this.dsHDTB_CNTT, this.dsHDTB_TSL)

          if (rs.message != "Thành công" && rs.message != "Success") {
            this.$toast.error(rs.message)
            this.loading(false)
            return
          }

          if (this.tinh.tinhthicong_id > 0 || this.kenh_trang) {              
              await api.sp_themmoi_hdbancheo_biendong_v3(this.dsHDBC, this.dsHDTBBC,this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDKBCDVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsLYDOHUYBC, this.dsDiaChi_BanCheo)
          }
          
          //this.$toast.success("Thêm hợp đồng thành công !")

          if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL && statusVdc == true) {
            // Gọi hàm cập nhật trạng thái HDTB = 1: Đã đặt chỗ thành công trên VDC
            // obj.CAPNHAT_STATUS_HDTB(hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
            await api.capnhat_status_hdtb(this.hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
          }

          try {                        
            await api.capnhat_goikt(this.hdtb_id)
          } catch (Exception) {
          }

          this.setButton(3)

          var dsHD = await api.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
          this.hienThiDanhSacHDTB(dsHD)
          if (!dsHD) await this.onMaTBKeyPress({keyCode: 13})
          this.$toast.success('Thêm mới hợp đồng thành công !')
          this.loading(false)
        } 
        else {
          // DataSet dshg = bangts.LayHuongGiao_TiepNhan(LOAI_HOPDONG.KHOIPHUC_SD, loaitb_id, tocdo_id, muccuoc_id, parseInt(this.cboKieuLD.SelectedValue))
          var dshg = await api.layhuonggiao_tiepnhan_v2(LOAI_HOPDONG.KHOIPHUC_SD, this.loaitb_id, this.tocdo_id, this.muccuoc_id, parseInt(this.cboKieuLD.SelectedValue), this.vloaikenh_id, parseInt(this.cboDichVuVT.SelectedValue))
          console.log('btnGhiLaiClick 000')
          let huonggiaoTN = 0 
          this.quytrinh_id = 0
          this.luong_id = 0
          if (dshg&& dshg.length <= 0) {
            this.$toast.error('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            this.loading(false)
            return
          }

          huonggiaoTN = parseInt(dshg[0]['huonggiao_id']) // 
          this.quytrinh_id = parseInt(dshg[0]['quytrinh_id'])
          this.luong_id = parseInt(dshg[0]['luong_id'])
          console.log('btnGhiLaiClick 001')
          let rs_qt = await this.LayHuongGiao_TiepNhan_BC(this.tinh.tinhthicong_id, LOAI_HOPDONG.KHOIPHUC_SD, 
                  parseInt(this.cboLoaihinhTB.SelectedValue), this.thongso_tc.tocdo_id, this.thongso_tc.muccuoc_id, 
                  this.vloaikenh_id, this.thongso_tc.kieuld_id != undefined ? this.thongso_tc.kieuld_id : 0)
          if (!rs_qt || rs_qt.length <= 0) {
              this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý")
              this.loading(false)
              return
          }
          console.log('btnGhiLaiClick 002')
          this.quytrinh_tc_tn = 0;
          this.huonggiao_tc_tn = 0;
          this.quytrinh_tc_tn = Number(rs_qt.quytrinh_id);
          this.huonggiao_tc_tn = Number(rs_qt.huonggiao_id);
          console.log('btnGhiLaiClick 003')
          await this.TaoDuLieu(false)
          await this.TaoDuLieuBC(false) 

          

          // #region Cập nhật dữ liệu vào DataBase và hiển thị          
          await api.sp_capnhat_hopdong_khoiphuc(this.dsHDKH, this.dsHDTB, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsHDTB_MGW, this.dsDK_DVGT, this.dsDK_DVKHAC,this.dsHDTB_IMS, this.dsHDTB_TSL, this.dsHDTBDV, this.dsCTTHD)
          
          if (this.tinh.tinhthicong_id > 0){            
            await api.sp_capnhat_hdbancheo_biendong(this.dsHDBC, this.dsHDTBBC,this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDKBCDVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsDiaChi_BanCheo)
          }

          if (parseInt(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_1260 ||
                        parseInt(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_ADSL ||
                        parseInt(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.INTERNET_FTTH ||
                        parseInt(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.WIFI_FIBER) {
            await api.capnhat_status_hdtb(this.hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
          }
          // hoangpkn : 14/06/2014
          // Đối với loại hợp đồng có thay đổi tốc độ, mức cước
          // Phải lấy lại huonggiao_tn để update vào bảng giaophieu
          await api.update_huonggiao_tiepnhan(this.hdtb_id, huonggiaoTN)
          // end hoàng thêm
          this.setButton(3)

          try {                     
            await api.capnhat_goikt(this.hdtb_id)
          } catch (ex) {
          }

          var dsHD = await api.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id)
          this.hienThiDanhSacHDTB(dsHD)

          this.loading(false)

          this.$toast.success('Cập nhật hợp đồng thành công !')
        }
      } catch (ex) {
        this.$toast.error('Có lỗi : ' + ex)
        console.log(ex)
        this.loading(false)
      }
    },
    formatJson (data) {
      let output = {}
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
      
      await this.TaoDuLieu_HDKH(themmoi)
      await this.TaoDuLieu_HDTB(themmoi)

      

      await this.TaoDuLieu_THONGTIN_TT(themmoi)
      await this.TaoDuLieu_CT_TIENHD(true)

      // //
      this.TaoDuLieu_DKDV()
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

      await this.TaoDuLieu_HDTBDV(true)

      switch (this.dichvuvt_id) {
        case DICHVU_VIENTHONG.CO_DINH:
          await this.TaoDuLieu_HDTB_CD(themmoi)
          break
        case DICHVU_VIENTHONG.IMS:
          await this.TaoDuLieu_HDTB_IMS(themmoi)
          break
        case DICHVU_VIENTHONG.ADSL:
          await this.TaoDuLieu_HDTB_ADSL(themmoi)
          break
        case DICHVU_VIENTHONG.GPHONE:
          this.TaoDuLieu_HDTB_GP(themmoi)
          break
        case DICHVU_VIENTHONG.MEGAWAN:
        case DICHVU_VIENTHONG.METRONET:
        case 26:
          await this.TaoDuLieu_HDTB_MGWAN(themmoi)
          break
        case DICHVU_VIENTHONG.TSL:
        case 25:
          this.TaoDuLieu_HDTB_TSL(themmoi)
          break
        case DICHVU_VIENTHONG.DICHVU_CNTT:
        case DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT:
        case DICHVU_VIENTHONG.TRUNGTAM_DULIEU:
        case DICHVU_VIENTHONG.HOINGHI_TRUYENHINH:
          await this.TaoDuLieu_HDTB_CNTT(themmoi)
          break
        default:
          break
      }
    },
    async TaoDuLieu_HDKH (themmoi) {
      let vmagd_temp = ''
      this.dsHDKH = []
      if (themmoi == true) {
        this.hdkh_id = await api.get_keys('HD_KHACHHANG') 
        this.hdkh_id = parseInt(this.hdkh_id)
      }
      let rowHDKH = {}

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
      rowHDKH.MA_DUAN = this.txtMaDA.Text
      rowHDKH.KHACHHANG_ID = this.khachhang_id
      rowHDKH.TEN_KH = this.txtTenKH.Text
      rowHDKH.DIACHI_KH = this.txtDiaChiKH.Text
      rowHDKH.KHLON_ID = this.khlon_id// 1
      rowHDKH.DONVI_ID = parseInt(this.$auth.getDonViID())
      rowHDKH.NHANVIEN_ID = parseInt(this.$auth.getNhanVienID())
      rowHDKH.LOAIHD_ID = LOAI_HOPDONG.KHOIPHUC_SD
      rowHDKH.NGUOI_CN = this.$auth.getMaNhanVien()
      rowHDKH.NGAY_CN = this.$auth.getNgayCapNhat()
      rowHDKH.MAY_CN = this.tt_nd.may_cn
      rowHDKH.IP_CN = this.tt_nd.ip

      rowHDKH.NGAYLAP_HD = this.dtpNgayLapHD.Value
      rowHDKH.SO_DT = this.txtSoDT.Text

      if (this.chkCTV.Checked && this.txtCTV.Text) {
        rowHDKH.CTV_ID = this.ctv_id
      }
      if (this.chkNguoiGT.Checked && this.txtNguoiGT.Text) {
        rowHDKH.NHANVIENGT_ID = this.nguoigt_id
      }

      // if (this.hdkh_cha_id > 0) {
      //   rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
      //   this.hdkh_cha_id = 0
      // }
      // // PNLINH B2A 02/05/2020
      // if (this.VPHIEUCSKH_ID != 0) {
      //   rowHDKH.PHIEUCSKH_ID = this.VPHIEUCSKH_ID
      // }
      this.dsHDKH.push(rowHDKH)
      this.dsHDKH = this.dsHDKH && this.dsHDKH.length > 0 ? this.UpperCasePropertyList(this.dsHDKH) : []
    },
    async TaoDuLieu_HDTB (themmoi) {      
      this.dsHDTB = []

      if (themmoi == true) {
        this.hdtb_id = await api.get_keys("HD_THUEBAO")
        this.hdtb_id = parseInt(this.hdtb_id)
      }

      let rowHDTB = {}

      rowHDTB['HDTB_ID'] = this.hdtb_id
      rowHDTB['HDKH_ID'] = this.hdkh_id
      rowHDTB['THUEBAO_ID'] = this.thuebao_id
      if (this.thuebao_cha_id != 0) { rowHDTB['THUEBAO_CHA_ID'] = this.thuebao_cha_id }

      rowHDTB['MA_TB'] = this.txtMaTB.Text
      rowHDTB['TEN_TB'] = this.txtTenTB.Text
      rowHDTB['DIACHI_TB'] = this.txtDiaChiTB.Text
      rowHDTB['KIEULD_ID'] = parseInt(this.cboKieuLD.SelectedValue)
      rowHDTB['LOAITB_ID'] = parseInt(this.cboLoaihinhTB.SelectedValue)

      if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL) {
        rowHDTB['DONVI_ID'] = parseInt(this.cboDaiVT_ADSL.SelectedValue)
      } else if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.CO_DINH) {
        rowHDTB['DONVI_ID'] = parseInt(this.cboDaiVT_CD.SelectedValue)
      } else if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.IMS) {
        rowHDTB['DONVI_ID'] = parseInt(this.cboDaiVT_IMS.SelectedValue)
      } else if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.GPHONE) {
        rowHDTB['DONVI_ID'] = parseInt(this.cboDVQLGP.SelectedValue)
      } else if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.MEGAWAN ||
                parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.METRONET) {
        rowHDTB['DONVI_ID'] = parseInt(this.cboDVQL_MGW.SelectedValue)
      } else {
        rowHDTB['DONVI_ID'] = this.dsDBTB[0].donvi_id
      }
      // else if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
      //           parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.DICHVU_CNTT ||
      //           parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.HOINGHI_TRUYENHINH ||
      //           parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.TRUNGTAM_DULIEU) {
      //   rowHDTB['DONVI_ID'] = this.donvi_ql_cntt
      // }

      rowHDTB['DOITUONG_ID'] = this.doituong_id
      // if (this.CHUYENDOI_LH_DUNGTHU == 1 && this.chkDungThu.Checked) { rowHDTB['DOITUONG_ID'] = DUNG_THU.DOITUONG_TB }

      rowHDTB['TTHD_ID'] = TRANGTHAI_HOPDONG.MOI
      rowHDTB['DICHVUVT_ID'] = parseInt(this.cboDichVuVT.SelectedValue)
      rowHDTB['KIEU_TB'] = '0' // Kiểu thuê bao thường
      rowHDTB['NGUOI_CN'] = this.$auth.getMaNhanVien()
      rowHDTB['NGAY_CN'] = this.$auth.getNgayCapNhat()
      rowHDTB['MAY_CN'] = this.tt_nd.may_cn
      // rowHDTB['DUNGTHU'] = 0
      // HungHM-27/06/2010: bổ xung thêm các column cho bảng HD_ThueBao
      rowHDTB['DIACHI_LD'] = this.txtDiaChiLD.Text
      rowHDTB['GHICHU'] = this.txtGhiChu.Text
      rowHDTB['STATUS'] = TRANGTHAI_DONGBO.CHUADONGBO.toString()

      var rsktt = await api.kt_thaotac(this.luong_id, 'STATUS_TONGDAI_CD')
      if (parseInt(rsktt) > 0) { rowHDTB['STATUS'] = "0" }

      rowHDTB['QUYTRINH_ID'] = this.quytrinh_id
      if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.CO_DINH) {
        if (this.cboMucCuocTB_CD.Items.length > 0) {
          rowHDTB['MUCUOCTB_ID'] = parseInt(this.cboMucCuocTB_CD.SelectedValue)
          var rsMapId = await api.fn_tt_muccuoc_tb({param:parseInt(this.cboMucCuocTB_CD.SelectedValue),type:1})// attt: 'cuoc_tb', 'muccuoc_tb', 'where mucuoctb_id = ' + parseInt(this.cboMucCuocTB_CD.SelectedValue))
          rowHDTB['MUCUOC_TB'] = parseFloat(rsMapId)
        }
      }
      if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL) {
        if (this.cboMucCuocTB_ADSL.Items.length > 0) {
          rowHDTB['MUCUOCTB_ID'] = parseInt(this.cboMucCuocTB_ADSL.SelectedValue)
          var rsMapId = await api.fn_tt_muccuoc_tb({param:parseInt(this.cboMucCuocTB_ADSL.SelectedValue),type:1}) // attt: "cuoc_tb", '.muccuoc_tb', 'where mucuoctb_id = ' + parseInt(this.cboMucCuocTB_ADSL.SelectedValue))
          rowHDTB['MUCUOC_TB'] = parseFloat(rsMapId)
        }
      }

      if (this.doituong_id == DUNG_THU.DOITUONG_TB && this.doituong_ct_id != 0) { // đối tượng dùng thử kptl -> đổi đối tượng luôn
        rowHDTB['DOITUONG_ID'] = this.doituong_ct_id
      }

      this.dsHDTB.push(rowHDTB)
      this.dsHDTB = this.dsHDTB && this.dsHDTB.length > 0 ? this.UpperCasePropertyList(this.dsHDTB) : []
    },
    TaoDuLieu_HDTB_DUNGTHU () {
      try {
        this.dsHDTB_DUNGTHU = []
        var rowHDTB = {}

        rowHDTB['HDTB_ID'] = this.hdtb_id
        rowHDTB['SONGAY'] = 30
        rowHDTB['MAY_CN'] = ''
        rowHDTB['NGAY_CN'] = this.$auth.getNgayCapNhat()
        rowHDTB['NGUOI_CN'] = this.$auth.getMaNhanVien()
        rowHDTB['IP_CN'] = ''

        this.dsHDTB_DUNGTHU.push(rowHDTB)
      } catch (e) {
        this.$toast.error(e)
        console.log(e)
      }
    },
    async TaoDuLieu_CT_TIENHD (themmoi) {
      this.dsCTTHD = []
      let rowCTTHD = {}

      for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        if (this.ds_tien_khoanmuc[i]['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DICHVU) {
          for (let j = 0; j < this.m_dsThueBao_DichVu.dsdvgt_ban.length; j++) {
            if (this.m_dsThueBao_DichVu.dsdvgt_ban[j]['dangky'] == "1" && this.m_dsThueBao_DichVu.dsdvgt_ban[j]['huy'] == "0") {
              rowCTTHD = {}
              rowCTTHD['HDTB_ID'] = this.hdtb_id
              if (themmoi) {
                this.CT_TIENHD_ID = await api.get_keys('CT_TIENHD')
                rowCTTHD['CT_TIENHD_ID'] = this.CT_TIENHD_ID
              }
              rowCTTHD['KHOANMUCTT_ID'] = KHOANMUC_TT.KMTT_DICHVU
              let tien = 0// Tiền dịch vụ chưa khuyến mại
              let vat = 0// Vat dịch vụ chưa khuyến mại
              tien = parseFloat(this.m_dsThueBao_DichVu.dsdvgt_ban[j]['cuoc_ld'])
              rowCTTHD['TIEN'] = tien
              vat = parseFloat(this.m_dsThueBao_DichVu.dsdvgt_ban[j]['vat_ld'])
              rowCTTHD['VAT'] = vat
              rowCTTHD['ID'] = parseInt(this.m_dsThueBao_DichVu.dsdvgt_ban[j]['dichvugt_id'])
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
          rowCTTHD['KHOANMUCTT_ID'] = parseInt(this.ds_tien_khoanmuc[i]['khoanmuctt_id'])

          rowCTTHD['TIEN'] = parseFloat(this.ds_tien_khoanmuc[i]['tien'])
          rowCTTHD['VAT'] = parseFloat(this.ds_tien_khoanmuc[i]['vat'])

          rowCTTHD['ID'] = parseInt(this.ds_tien_khoanmuc[i]['khoanmuctt_id'])
          rowCTTHD['LOAI_ID'] = 1
          this.dsCTTHD.push(rowCTTHD)
        }
      }
      this.dsCTTHD = this.dsCTTHD && this.dsCTTHD.length > 0 ? this.UpperCasePropertyList(this.dsCTTHD) : []
    },
    TaoDuLieu_DKDV () {      
      this.dsDK_DVGT = []

      let kieu_dk_dvgt
      for (var dr of this.m_dsThueBao_DichVu.dsdvgt_ban) {
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
        rowHDTDDV['DICHVUGT_ID'] = parseInt(dr['dichvugt_id'])
        rowHDTDDV['NOIDUNG'] = dr['noidung']
        rowHDTDDV['KIEU_YC'] = kieu_dk_dvgt
        rowHDTDDV['TTTH'] = 1

        this.dsDK_DVGT.push(rowHDTDDV)
      }      
      this.dsDK_DVGT = this.dsDK_DVGT && this.dsDK_DVGT.length ? this.UpperCasePropertyList(this.dsDK_DVGT) : []  
    },
    async TaoDuLieu_THONGTIN_TT (themmoi) {},
    async TaoDuLieu_HDTBDV (themmoi) {
      console.log('TaoDuLieu_HDTBDV')
      this.dsHDTBDV = []      
      let rs = await api.sp_tt_dbtb_dv({"p_param": this.thuebao_id, "p_type":1})
      if(rs && rs.length>0){
        console.log(rs)
        for (let i = 0; i < rs.length; i++){
          let HDTB_DVRow  = {}
          HDTB_DVRow.HDTB_ID = this.hdtb_id
          HDTB_DVRow.LOAIDV_ID = parseInt(rs[i]["loaidv_id"])
          HDTB_DVRow.DONVI_ID = parseInt(rs[i]["donvi_id"])
          HDTB_DVRow.KIEUDV_ID = parseInt(rs[i]["kieudv_id"])

          if (parseInt(rs[i]["loaidv_id"]) == LOAI_DONVI.TRAM_VT
              && parseInt(this.cboDichVuVT.SelectedValue) != DICHVU_VIENTHONG.TSL && parseInt(this.cboDichVuVT.SelectedValue) != 25)
          {
              if (this.dichvuvt_id == DICHVU_VIENTHONG.CO_DINH)
                  HDTB_DVRow.DONVI_ID = parseInt(this.cboTram_CD.SelectedValue)
              if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL)
                  HDTB_DVRow.DONVI_ID = parseInt(this.cboVeTinh_Adsl.SelectedValue)
              if (this.dichvuvt_id == DICHVU_VIENTHONG.IMS)
                  HDTB_DVRow.DONVI_ID = parseInt(this.cboTram_IMS.SelectedValue)
              if (this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == 26)
                  HDTB_DVRow.DONVI_ID = parseInt(this.cboTram_MGW.SelectedValue)
          }
          this.dsHDTBDV.push(HDTB_DVRow)
        }
        this.dsHDTBDV = this.dsHDTBDV && this.dsHDTBDV.length > 0 ? this.UpperCasePropertyList(this.dsHDTBDV) : []   
      }
    },
    async TaoDuLieu_HDTB_CD (themmoi) {
      var dscd = await api.lay_ds_db_cd_theo_tbid(this.thuebao_id)

      this.dsHDTB_CD = []
      var rowHDTB_CD = {}

      rowHDTB_CD['HDTB_ID'] = this.hdtb_id
      rowHDTB_CD['MATB_TN'] = this.txtMaTN_ADSL.Text
      if (dscd.length > 0) {
        if (dscd[0]['ne_id'] != "") { rowHDTB_CD['NE_ID'] = parseInt(dscd[0]['ne_id']) }
        if (dscd[0]['ne'] != "") { rowHDTB_CD['NE'] = dscd[0]['ne'] }
        if (dscd[0]['tuyencap'] != "") { rowHDTB_CD['TUYENCAP'] = dscd[0]['tuyencap'] }
        if (dscd[0]['lientu'] != "") { rowHDTB_CD['LIENTU'] = dscd[0]['lientu'] }
        if (dscd[0]['doicap_p'] != "") { rowHDTB_CD['DOICAP_P'] = parseInt(dscd[0]['doicap_p']) }
        if (dscd[0]['hopcap_p'] != "") { rowHDTB_CD['HOPCAP_P'] = dscd[0]['hopcap_p'] }
        if (dscd[0]['tucap_p'] != "") { rowHDTB_CD['TUCAP_P'] = dscd[0]['tucap_p'] }
        if (dscd[0]['loaicap_id'] != "") { rowHDTB_CD['LOAICAP_ID'] = parseInt(dscd[0]['loaicap_id']) }
        if (dscd[0]['cap_goc'] != "") { rowHDTB_CD['CAP_GOC'] = dscd[0]['cap_goc'] }
        if (dscd[0]['cap_p'] != "") { rowHDTB_CD['CAP_P'] = dscd[0]['cap_p'] }
        if (dscd[0]['doicap_goc'] != "") { rowHDTB_CD['DOICAP_GOC'] = parseInt(dscd[0]['doicap_goc']) }
        if (dscd[0]['tucap_goc'] != "") { rowHDTB_CD['TUCAP_GOC'] = dscd[0]['tucap_goc'] }
        if (dscd[0]['tramtb_id'] != "") { rowHDTB_CD['TRAMTB_ID'] = parseInt(dscd[0]['tramtb_id']) }
      }

      var mapID = await api.fn_tt_kieu_ld({param:this.cboKieuLD.SelectedValue,type:1}) //attt api.map _id('kieu', "kieu_ld", "where kieuld_id =" + this.cboKieuLD.SelectedValue)
      var kieu = parseInt(mapID)
      // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
      if (kieu == 1 || kieu == 2) {
        if (this.txtMaDoiCap_ADSL.Text.trim().length == 0) {
          this.$toast.warning("Đối kiểu lắp đặt là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.")
          return
        }
        rowHDTB_CD['MADOICAP'] = parseInt(this.txtMaDoiCap_ADSL.Text.trim())
      } else { // TH còn lại sinh mã đôi cáp
        if (themmoi == true) {
          rowHDTB_CD['MADOICAP'] = await api.get_keys("MADOICAP")
          this.txtMaDoiCap_ADSL.Text = rowHDTB_CD['MADOICAP']
        } else {
          if (this.txtMaDoiCap_ADSL.Text.trim().length > 0) {
            // Trong trường hợp : thêm mới kiểu lắp trên đường có sẵn hoặc lắp kèm, sau đó ấn ghi lại chọn trên đường ko số
            // => kiểm tra xem đã có thuê bao nào ăn vào mã đôi cáp này hay chưa
            // Nếu có thuê bao ăn vào rồi thì pải sinh mã đôi cáp mới

            // TODO: chua co api laphd.Kiemtra_thuebao_sd_madoicap
            var rs = 0 // await laphd.Kiemtra_thuebao_sd_madoicap(parseInt(this.txtMaDoiCap_ADSL.Text))
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
      this.dsHDTB_CD = this.dsHDTB_CD && this.dsHDTB_CD.length > 0 ? this.UpperCasePropertyList(this.dsHDTB_CD) : []     
    },
    async TaoDuLieu_HDTB_IMS (themmoi) {
      this.dsHDTB_IMS = []
      let rowHDTB_IMS = {}
      var dsDBIMS = await api.lay_ds_db_ims_theo_tbid(this.thuebao_id)

      if (dsDBIMS && dsDBIMS.length > 0) {
        dsDBIMS = this.UpperCasePropertyList(dsDBIMS)      
        rowHDTB_IMS = {...dsDBIMS[0]}
      }

      rowHDTB_IMS['HDTB_ID'] = this.hdtb_id
      this.dsHDTB_IMS.push(rowHDTB_IMS)
      

    },
    async TaoDuLieu_HDTB_ADSL (themmoi) {
      var dsadsl = await api.lay_ds_db_adsl_theo_tbid(this.thuebao_id)

      this.dsHDTB_ADSL = []
      var rowHDTB_ADSL = {}

      rowHDTB_ADSL['HDTB_ID'] = this.hdtb_id
      rowHDTB_ADSL['TOCDO_ID'] = parseInt(this.cboTocDo_Adsl.SelectedValue)
      rowHDTB_ADSL['MUCCUOC_ID'] = parseInt(this.cboMucCuoc_ADSL.SelectedValue)
      rowHDTB_ADSL['CHUQUAN_ID'] = this.thongso_ban.chuquan_id
      rowHDTB_ADSL['CHUQUANLD_ID'] = this.thongso_ban.chuquanld_id
      rowHDTB_ADSL['TRANGBI_ID'] = this.thongso_ban.trangbi_id
      rowHDTB_ADSL['THIETBIDC_ID'] = this.thongso_ban.thietbidc_id
      // nhapt 03082017: thêm thông tin tính cước
      rowHDTB_ADSL['THONGTIN_TC'] = this.txtTTCuoc.Text
      if (dsadsl && dsadsl.length > 0) {
        if (dsadsl[0]["dslam_id"] != "") { rowHDTB_ADSL['DSLAM_ID'] = parseInt(dsadsl[0]["dslam_id"]) }
        if (dsadsl[0]["bras_id"] != "") { rowHDTB_ADSL['BRAS_ID'] = parseInt(dsadsl[0]["bras_id"]) }
        if (dsadsl[0]["port_id"] != "") { rowHDTB_ADSL['PORT_ID'] = parseInt(dsadsl[0]["port_id"]) }
        if (dsadsl[0]["vci_vpi_id"] != "") { rowHDTB_ADSL['VCI_VPI_ID'] = parseInt(dsadsl[0]["vci_vpi_id"]) }
        if (dsadsl[0]["ma_lt"] != "") { rowHDTB_ADSL['MA_LT'] = dsadsl[0]["ma_lt"] }
        if (dsadsl[0]["kieutra_id"] != "") { rowHDTB_ADSL['KIEUTRA_ID'] = parseInt(dsadsl[0]["kieutra_id"]) }
        if (dsadsl[0]["thang_id"] != "") { rowHDTB_ADSL['THANG_ID'] = parseInt(dsadsl[0]["thang_id"]) }
        if (dsadsl[0]["ngaytra"] != "") { rowHDTB_ADSL['NGAYTRA'] = dsadsl[0]["ngaytra"] }

        if (this.pass_adsl == "") { // lấy pass cũ khi chưa có pass
          this.pass_adsl = dsadsl[0]["password"]
        }

        if (dsadsl[0]["tramtb_id"] != "") { rowHDTB_ADSL['TRAMTB_ID'] = parseInt(dsadsl[0]["tramtb_id"]) }
      }
      // SonNMP 15 July 2019: sinh pass ADSL tự động theo yêu cầu của QBH, tránh trường hợp kích hoạt lên Visa thất bại do pass rỗng
      // Luôn tạo mới mật khẩu khi mật khẩu cũ rỗng
      // if (!this.pass_adsl) {
      //   if (this.matkhau_md_adsl) {
      //     if (this.matkhau_md_adsl == "random_sql") { this.pass_adsl = CommonFunction.GetRandomText(11) } else { this.pass_adsl = this.matkhau_md_adsl }
      //   } else {
      //     if (this.sinh_TD_matkhau_adsl == 1) {
      //       var partsDot = this.txtMaTB.Text.split('.')
      //       var partsDash = this.txtMaTB.Text.split('-')
      //       this.pass_adsl = this.txtMaTB.Text.includes(".") ? partsDot[partsDot.length - 1] : ""
      //       if (!this.pass_adsl) { this.pass_adsl = this.txtMaTB.Text.includes("-") ? partsDash[partsDash.length - 1] : "" }
      //     } else { this.pass_adsl = CommonFunction.GetRandomText(8) }
      //   }
      // }
      rowHDTB_ADSL['PASSWORD'] = this.pass_adsl

      let loaitb_id = parseInt(this.cboLoaihinhTB.SelectedValue)
      if (loaitb_id == LOAIHINH_THUEBAO.INTERNET_1260 || loaitb_id == LOAIHINH_THUEBAO.INTERNET_ADSL ||
                loaitb_id == LOAIHINH_THUEBAO.INTERNET_FTTH || loaitb_id == LOAIHINH_THUEBAO.WIFI_FIBER) {
        
        //rowHDTB_ADSL['HANDATCHO'] = moment(this.$auth.getNgayCapNhat()).add(7, 'days').format("DD/MM/YYYY")
        rowHDTB_ADSL['HANDATCHO'] = moment(new Date()).add(7, 'days').format("DD/MM/YYYY")
      }
      // HungHM - Bổ xung thêm mã truy nhập + mã đôi cáp
      rowHDTB_ADSL['MATB_TN'] = this.txtMaTN_ADSL.Text
      // rowHDTB_ADSL['MADOICAP'] = await api.get_keys("MADOICAP")

      var id = await api.fn_tt_kieu_ld({param:this.cboKieuLD.SelectedValue,type:1})// attt: api.map _id("kieu", "kieu_ld", "where kieuld_id =" + this.cboKieuLD.SelectedValue)
      let kieu = parseInt(id)
      // Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
      if (kieu == 1 || kieu == 2) {
        if (this.txtMaDoiCap_ADSL.Text.trim().length == 0) {
          this.$toast.warning("Đối kiểu lắp đặt là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.")
          return
        }
        rowHDTB_ADSL['MADOICAP'] = parseInt(this.txtMaDoiCap_ADSL.Text.trim())
      } else { // TH còn lại sinh mã đôi cáp
        if (themmoi == true) {
          rowHDTB_ADSL['MADOICAP'] = await api.get_keys("MADOICAP")
          this.txtMaDoiCap_ADSL.Text = rowHDTB_ADSL['MADOICAP']
        } else {
          if (this.txtMaDoiCap_ADSL.Text.trim().length > 0) {
            // Trong trường hợp : thêm mới kiểu lắp trên đường có sẵn hoặc lắp kèm, sau đó ấn ghi lại chọn trên đường ko số
            // => kiểm tra xem đã có thuê bao nào ăn vào mã đôi cáp này hay chưa
            // Nếu có thuê bao ăn vào rồi thì pải sinh mã đôi cáp mới

            // TODO: chua co api Kiemtra_thuebao_sd_madoicap
            // if (laphd.Kiemtra_thuebao_sd_madoicap(parseInt(this.txtMaDoiCap_ADSL.Text)) != 0) {
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
      
      this.dsHDTB_ADSL.push(rowHDTB_ADSL)
      this.dsHDTB_ADSL = this.dsHDTB_ADSL && this.dsHDTB_ADSL.length > 0 ? this.UpperCasePropertyList(this.dsHDTB_ADSL) : []     
    },
    TaoDuLieu_HDTB_GP (themmoi) {
      this.dsHDTB_GP = []
      var rowHDTB_GP = {}

      rowHDTB_GP['HDTB_ID'] = this.hdtb_id
      rowHDTB_GP['SIMCARD'] = this.txtSoSimGphone.Text
      this.dsHDTB_GP.push(rowHDTB_GP)
    },
    async TaoDuLieu_HDTB_MGWAN (themmoi) {
      this.dsHDTB_MGW = []
      var dsDBMegaWAN = await api.lay_ds_danhba_megawan(this.thuebao_id)
      let rowHDTB_MGW = {}

      if (dsDBMegaWAN && dsDBMegaWAN.length > 0) {
        dsDBMegaWAN = this.UpperCasePropertyList(dsDBMegaWAN)
        rowHDTB_MGW = {...dsDBMegaWAN[0]}
      }
      rowHDTB_MGW['HDTB_ID'] = this.hdtb_id
      this.dsHDTB_MGW.push(rowHDTB_MGW)
    },
    async TaoDuLieu_HDTB_TSL (themmoi) {
      this.dsHDTB_TSL = []
      var ds_tsl = await api.lay_ds_db_tsl_theo_tbid(this.thuebao_id) 
      if (ds_tsl && ds_tsl.length > 0){
        for (let i = 0; i < ds_tsl.length; i++){
            let rowHDTB_TSL = {}            
            cmUlt.CopyRowAndUppercase(ds_tsl[i], rowHDTB_TSL)
            rowHDTB_TSL.HDTB_ID = this.hdtb_id            
            this.dsHDTB_TSL.push(rowHDTB_TSL)
        }
      } 
      
    },
    async TaoDuLieu_HDTB_CNTT (themmoi) {
      var ds = await api.lay_ds_danhba_cntt(this.thuebao_id)

      if (ds.length > 0) {
        ds = this.UpperCasePropertyList(ds)
        CommonFunction.CopyRow(ds[0], this.ItemList)
      }

      this.dsHDTB_CNTT = this.ItemList
    },
    CapNhat_HDTB_ID_DangKyDV () {
      // let ds2 = this.dsDK_DVKHAC
      // dsDK_DVKHAC = new DANGKY_DVK_DATA()
      // cmUlt.CopyDataSet(ds2, dsDK_DVKHAC)

      for (let i = 0; i < this.dsDK_DVKHAC.length; i++) {
        this.dsDK_DVKHAC[i]["hd_id"] = this.hdtb_id
      }
    },
    HT_DichVuGT () {
      try {
        // Hiếu sửa thêm lấy tiền dịch vụ 15/09/2010
        let kt = true// Kiểm tra xem có đăng ký dịch vụ nào không, nếu không trả tiền dv, vatdv về 0
        this.tien_dv = 0
        this.vat_dv = 0
        //
        let str
        this.lvDvgt.Items = []
        for (let i = 0; i < this.m_dsThueBao_DichVu.dsdvgt_ban.length; i++) {
          if (this.m_dsThueBao_DichVu.dsdvgt_ban[i]["huy"] == "1" || this.m_dsThueBao_DichVu.dsdvgt_ban[i]["dangky"] == "1") {
            str = this.m_dsThueBao_DichVu.dsdvgt_ban[i]["ten_dvgt"]
            if (this.m_dsThueBao_DichVu.dsdvgt_ban[i]["dangky"] == "1" && this.m_dsThueBao_DichVu.dsdvgt_ban[i]["huy"] == "0") {
              kt = false
              // Hiếu bổ sung code tính tiền dịch vụ
              if (this.m_dsThueBao_DichVu.dsdvgt_ban[i]["cuoc_ld"] != "") {
                this.tien_dv = this.tien_dv + parseInt(this.m_dsThueBao_DichVu.dsdvgt_ban[i]["cuoc_ld"])
              }
              if (this.m_dsThueBao_DichVu.dsdvgt_ban[i]["vat_ld"] != "") {
                this.vat_dv = this.vat_dv + parseInt(this.m_dsThueBao_DichVu.dsdvgt_ban[i]["vat_ld"])
              }

              str = str + " (ĐK)"
            }
            if (this.m_dsThueBao_DichVu.dsdvgt_ban[i]["huy"] == "1") { str = str + " (Hủy)" }
            this.lvDvgt.Items.push({ DICHVUGT_ID: this.m_dsThueBao_DichVu.dsdvgt_ban[i]['dichvugt_id'], TEN_DVGT: str })
          }
        }
        if (kt == true) {
          this.tien_dv = 0
          this.vat_dv = 0
        }
        this.HienThiTienDichVu(this.tien_dv, this.vat_dv)
      } catch (err) {
        this.$toast.error('Có lỗi')
        console.log(err)
      }
    },
    HienThiTienDichVu (tien_dv, vat_dv) {
      this.dtien_dv = tien_dv
      this.dvat_dv = vat_dv

      for (let j = 0; j < this.ds_tien_khoanmuc.length; j++) {
        if (this.ds_tien_khoanmuc[j]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DICHVU) {
          this.ds_tien_khoanmuc[j]["tien"] = this.dtien_dv
          this.ds_tien_khoanmuc[j]["vat"] = this.dvat_dv
        }
      }

      this.LayTongTien_HDTB()
    },
    LayTongTien_HDTB () {
      try {
        let dtongtien = 0
        let dtongvat = 0
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          dtongtien += parseFloat(this.ds_tien_khoanmuc[i]["tien"])
          dtongvat += parseFloat(this.ds_tien_khoanmuc[i]["vat"])
        }
        this.txtTongTien.Text = formatCurrency(dtongtien)
        this.txtTongVat.Text = formatCurrency(dtongvat)
      } catch (err) {
        this.$toast.error(err)
      }
    },
    HienThiTienLapDat (ds) {
      console.log(">>>HienThiTienLapDat")
      let kt_lm = true;
      if (ds[0].id != "-1") {
          for (let i = 0; i < ds.length; i++) {
              if (ds[i].khoanmuctt_id == KHOANMUC_TT.KMTT_KHOIPHUCSD) {
                  this.dtienld = parseInt(ds[0].tien)
                  this.dvatld = parseInt(ds[0].vat)
                  kt_lm = false;
              }
          }
      } else {
          this.dtienld = 0
          this.dvatld = 0
          
      }
    },
    //HÀM BÁN CHÉO
    async LAY_DS_DVGT_MACDINH(loaitb_id){
      console.log('LAY_DS_DVGT_MACDINH')
      this.m_dsThueBao_DichVu.dsdvgt_ban = await api.lay_ds_thuebao_dichvu(this.hdtb_id, 0, parseInt(loaitb_id), 0)
      console.log(this.m_dsThueBao_DichVu.dsdvgt_ban)
      this.HT_DichVuGT()
    },
    
    Reset_Thongso_Thuebao() {
        let ketqua = {
            trangbi_id: 0,
            muccuoc_id: 0,
            chuquan_id: 0,
            congnghe_id: 0,
            doituong_id: 0,
            kieuld_id: 0,
            kieutra_id: 0,
            loaikenh_id: 0,
            loaimd_id: 0,
            muccuoctb_id: 0,
            thietbidc_id: 0,
            thoihan_id: 0,
            tocdo_id: 0,
            tocdo_isp: 0,
            tocdo_pir_id: 0,
            tocdothuc_id: 0,
            trangbi_id: 0,
            ma_doicap: "",
            ma_tn: "",
        }
        return ketqua
    },
    
    async gan_thongso_theodanhba (vkieu, vdichvu, vthuebaoid) {
      console.log('gan_thongso_theodanhba')
      let ds = []
      if (vkieu == 1)
        this.thongso_ban = this.Reset_Thongso_Thuebao()
      else
        this.thongso_tc = this.Reset_Thongso_Thuebao()

      if (vdichvu == 1) {
        if (vkieu == 1) {
          ds = await api.lay_ds_db_cd_theo_tbid(vthuebaoid)
          if (ds && ds.length > 0) {
            this.thongso_ban.trangbi_id = ds[0]["trangbi_id"]
            this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"]
            this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"]
          }
        } else {
          ds = await api.lay_ds_db_cd_theo_tbid_bc(this.tinh.tinhthicong_id, vthuebaoid)
          if (ds && ds.length > 0) {
            this.thongso_tc.trangbi_id = ds[0]["trangbi_id"]
            if (this.dsDBTBBC.length > 0) {
              this.thongso_tc.doituong_id = this.dsDBTBBC[0]["doituong_id"]
              this.thongso_tc.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"]
            }
          }
        }
      }

      // dichvu vu co dinh
      if (vdichvu == 11) {
        if (vkieu == 1) {
          ds = await api.lay_ds_danhba_ims(vthuebaoid)
          if (ds.length > 0) {
            this.thongso_ban.trangbi_id = ds[0]["trangbi_id"]
            this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"]
            this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"]
          }
        } else {
          ds = await api.lay_ds_db_ims_theo_tbid_bc(this.tinh.tinhthicong_id, vthuebaoid)
          if (ds && ds.length > 0) {
            this.thongso_tc.trangbi_id = ds[0]["trangbi_id"]
            if (this.dsDBTBBC.length > 0) {
              this.thongso_tc.doituong_id = this.dsDBTBBC[0]["doituong_id"]
              this.thongso_tc.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"]
            }
          }
        }
      }

      // dich vu megwan metronet
      if (vdichvu == 7 || vdichvu == 8||vdichvu == 26) {
        if (vkieu == 1) {
          let exc = ""

          try {
            ds = await api.lay_ds_db_megawan_theo_tbid(vthuebaoid)
            if (ds && ds.length > 0) {
              exc = "TRANGBI_ID"
              this.thongso_ban.trangbi_id = ds[0]["trangbi_id"]
              exc = "tocdo_id"
              this.thongso_ban.tocdo_id = ds[0]["tocdo_id"]

              if (ds[0]["tocdo_pir_id"] != null && ds[0]["tocdo_pir_id"] != "") { this.thongso_ban.tocdo_pir_id = ds[0]["tocdo_pir_id"] }
              if (ds[0]["tocdo_isp"] != null && ds[0]["tocdo_isp"] != "") { this.thongso_ban.tocdo_isp = ds[0]["tocdo_isp"] }
              if (ds[0]["tocdo_nix"] != null && ds[0]["tocdo_nix"] != "") { this.thongso_ban.tocdo_nix = ds[0]["tocdo_nix"] }
              exc = "loaimd_id"
              this.thongso_ban.loaimd_id = ds[0]["loaimd_id"]
              exc = "loaikenh_id"
              this.thongso_ban.loaikenh_id = ds[0]["loaikenh_id"]
              exc = "thoihan_id"
              this.thongso_ban.thoihan_id = ds[0]["thoihan_id"]
              exc = "muccuoc_id"
              if (ds.find(item => typeof item.muccuoc_id != "undefined")) {
                if (ds[0]["muccuoc_id"] != null && ds[0]["muccuoc_id"] != "") { this.thongso_ban.muccuoc_id = ds[0]["muccuoc_id"] }
              }
              exc = "congnghe_id"
              if (ds[0]["congnghe_id"] != null && ds[0]["congnghe_id"] != "") { this.thongso_ban.congnghe_id = ds[0]["congnghe_id"] }
              exc = "thietbidc_id"
              this.thongso_ban.thietbidc_id = ds[0]["thietbidc_id"]
              exc = "doituong_id"
              this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"]
              exc = "mucuoctb_id"
              this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"] ? this.dsDBTB[0]["mucuoctb_id"] : this.dsDBTB[0]["muccuoctb_id"] ? this.dsDBTB[0]["muccuoctb_id"] : 0
            }
          } catch (e) {
            this.$toast.error("Có lỗi trong việc lấy dữ liệu tại trường: " + exc.toUpperCase())
          }
        } else {
          ds = await api.lay_ds_db_megawan_theo_tbid_bc(this.tinh.tinhthicong_id, vthuebaoid)
          if (ds && ds.length > 0) {
            let exc = ""

            try {
              exc = "TRANGBI_ID"
              this.thongso_tc.trangbi_id = ds[0]["trangbi_id"]
              exc = "TOCDO_ID"
              this.thongso_tc.tocdo_id = ds[0]["tocdo_id"]
              if (ds[0]["tocdo_pir_id"] != null && ds[0]["tocdo_pir_id"] != "") { this.thongso_tc.tocdo_pir_id = ds[0]["tocdo_pir_id"] }
              if (ds[0]["tocdo_isp"] != null && ds[0]["tocdo_isp"] != "") { this.thongso_tc.tocdo_isp = ds[0]["tocdo_isp"] }
              if (ds[0]["tocdo_nix"] != null && ds[0]["tocdo_nix"] != "") { this.thongso_tc.tocdo_nix = ds[0]["tocdo_nix"] }
              exc = "LOAIMD_ID"
              this.thongso_tc.loaimd_id = ds[0]["loaimd_id"]
              exc = "LOAIKENH_ID"
              this.thongso_tc.loaikenh_id = ds[0]["loaikenh_id"]
              exc = "THOIHAN_ID"
              this.thongso_tc.thoihan_id = ds[0]["thoihan_id"]
              exc = "MUCCUOC_ID"
              if (ds.find(item => typeof item.muccuoc_id != "undefined")) {
                if (ds[0]["muccuoc_id"] != null && ds[0]["muccuoc_id"] != "") { this.thongso_tc.muccuoc_id = ds[0]["muccuoc_id"] }
              }
              exc = "CONGNGHE_ID"
              if (ds[0]["congnghe_id"] != null && ds[0]["congnghe_id"] != "") { this.thongso_tc.congnghe_id = ds[0]["congnghe_id"] }
              exc = "THIETBIDC_ID"
              this.thongso_tc.thietbidc_id = ds[0]["thietbidc_id"]
              if (this.dsDBTBBC.length > 0) {
                exc = "DOITUONG_ID"
                this.thongso_tc.doituong_id = this.dsDBTBBC[0]["doituong_id"]
                exc = "MUCUOCTB_ID"
                this.thongso_tc.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"]
              }
            } catch (e) {            
              this.$toast.error("Có lỗi trong việc lấy dữ liệu tại trường: " + exc)
            }
          }
        }
      }
      // dich vu băng rộng
      if (vdichvu == 4) {
        if (vkieu == 1) {          
          ds = []
          ds = await api.lay_ds_danhba_adsl(vthuebaoid)
          
          if (ds && ds.length > 0) {            
            let exc = ""

            try {
              exc = "TRANGBI_ID"
              this.thongso_ban.trangbi_id = ds[0]["trangbi_id"]
              exc = "tocdo_id"
              this.thongso_ban.tocdo_id = ds[0]["tocdo_id"]
              exc = "tocdo_id"
              this.thongso_ban.tocdothuc_id = ds[0]["tocdo_id"]
              exc = "muccuoc_id"
              this.thongso_ban.muccuoc_id = ds[0]["muccuoc_id"]
              exc = "kieutra_id"
              this.thongso_ban.kieutra_id = ds[0]["kieutra_id"]
              exc = "thietbidc_id"
              // this.thongso_ban.congnghe_id = Convert.ToInt32(ds[0]["congnghe_id"].ToString());
              this.thongso_ban.thietbidc_id = ds[0]["thietbidc_id"]
              exc = "doituong_id"
              this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"]
              exc = "mucuoctb_id"
              this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"]              
            } catch (e) {            
              this.$toast.error("Có lỗi trong việc lấy dữ liệu tại ID-ERR: " + vthuebaoid + " trường: " + exc.toUpperCase())
            }
          }          
          
        } else {
          ds = await api.lay_ds_db_adsl_theo_tbid_bc(this.tinh.tinhthicong_id, vthuebaoid)          
          if (ds && ds.length > 0) {
            let exc = ""

            try {
              exc = "TRANGBI_ID"
              this.thongso_tc.trangbi_id = ds[0]["trangbi_id"]
              exc = "tocdo_id"
              this.thongso_tc.tocdo_id = ds[0]["tocdo_id"]
              exc = "tocdo_id"
              this.thongso_tc.tocdothuc_id = ds[0]["tocdo_id"]
              exc = "muccuoc_id"
              this.thongso_tc.muccuoc_id = ds[0]["muccuoc_id"]
              exc = "kieutra_id"
              this.thongso_tc.kieutra_id = ds[0]["kieutra_id"]
              exc = "thietbidc_id"
              this.thongso_tc.thietbidc_id = ds[0]["thietbidc_id"]
              if (this.dsDBTBBC.length > 0) {
                exc = "doituong_id"
                this.thongso_tc.doituong_id = this.dsDBTBBC[0]["doituong_id"]
                exc = "mucuoctb_id"
                this.thongso_tc.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"]
              }
            } catch (e) {
              this.$toast.error("Có lỗi trong việc lấy dữ liệu tại " + (this.tinh && this.tinh.matinh ? this.tinh.matinh : "") + " ID-ERR: " + vthuebaoid + " trường: " + exc.toUpperCase())
            }
          }
        }
        
      }

      // dich vu TSL
      if (vdichvu == 9 || vdichvu == 25) {
        if (vkieu == 1) {
          ds = await api.lay_ds_db_tsl_theo_tbid(vthuebaoid)
          if (ds && ds.length > 0) {
            let exc = ""
            try {
              exc = "TRANGBI_ID"
              this.thongso_ban.trangbi_id = ds[0]["trangbi_id"]
              exc = "TOCDO_ID"
              this.thongso_ban.tocdo_id = ds[0]["tocdo_id"]
              exc = "LOAIKENH_ID"
              this.thongso_ban.loaikenh_id = ds[0]["loaikenh_id"]
              exc = "THIETBIDC_ID"
              this.thongso_ban.thietbidc_id = ds[0]["thietbidc_id"]
              exc = "THIETBIDC_ID"
              this.thongso_ban.thietbidc_id_cuoi = ds[1]["thietbidc_id"]
              exc = "DOITUONG_ID"
              this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"]
              exc = "MUCUOCTB_ID"
              this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"]
            } catch (e) {
              this.$toast.error("Có lỗi trong việc lấy dữ liệu tại " + (this.tinh && this.tinh.matinh ? this.tinh.matinh : "") + " ID-ERR: " + vthuebaoid + " trường: " + exc.toUpperCase())
            }
          }
        } else {
          var input = {
            tinhthicong_id: this.tinh_dau.tinhthicong_id, 
            thuebao_id: this.thuebao_tc_dau
          }
          ds = await api.lay_ds_db_tsl_theo_tbid_bc(input)
          if (ds && ds.length > 0) {            
            try {              
              this.thongso_tc_dau.trangbi_id = ds[0]["trangbi_id"]
              this.thongso_tc_dau.tocdo_id = ds[0]["tocdo_id"]
              this.thongso_tc_dau.loaikenh_id = ds[0]["loaikenh_id"]              
              this.thongso_tc_dau.thietbidc_id = ds[0]["thietbidc_id"]              
              this.thongso_tc_dau.thietbidc_id_cuoi = ds[1]["thietbidc_id"]
              if (this.dsDBTBBC.length > 0) {                
                this.thongso_tc_dau.doituong_id = this.dsDBTBBC[0]["doituong_id"]                
                this.thongso_tc_dau.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"]
              }
            } catch (e) {
              console.log(e)
            }
          }
          var input = {
            tinhthicong_id: this.tinh_cuoi.tinhthicong_id, 
            thuebao_id: this.thuebao_tc_cuoi
          }
          var ds1 = await api.lay_ds_db_tsl_theo_tbid_bc(input)
          if (ds1 && ds1.length > 0) {            
            try {              
              this.thongso_tc_cuoi.trangbi_id = ds1[0]["trangbi_id"]
              this.thongso_tc_cuoi.tocdo_id = ds1[0]["tocdo_id"]              
              this.thongso_tc_cuoi.loaikenh_id = ds1[0]["loaikenh_id"]              
              this.thongso_tc_cuoi.thietbidc_id = ds1[0]["thietbidc_id"]              
              this.thongso_tc_cuoi.thietbidc_id_cuoi = ds1[1]["thietbidc_id"]
              if (this.dsDBTBBC.length > 0) {                
                this.thongso_tc_cuoi.doituong_id = this.dsDBTBBC[0]["doituong_id"]                
                this.thongso_tc_cuoi.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"]
              }
            } catch (e) {
              console.log(e)
            }
          }
        }
      }
    },
    
    async NapThongTinDichVuThueBao() {        
        try
        {
            this.m_dsThueBao_DichVu = {
                dsdvgt_ban: [],
                dsdvgt_tc: []
            }
            if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.IMS)
            {
              this.m_dsThueBao_DichVu.dsdvgt_ban = await api.lay_ds_tb_dvgt_theo_tt_ims({
                hdtb_id: this.hdtb_id,
                thuebao_id: this.thuebao_id,
                loaitb_id: parseInt(this.cboLoaihinhTB.SelectedValue),
                kieu_id: 0,
                trangthaitb_id: 1,
                kieuld_id: this.thongso_tc.kieuld_id
              })
              if (this.tinh.tinhthicong_id > 0)
              {
                  //TODO đổi api bán chéo
                  this.m_dsThueBao_DichVu.dsdvgt_tc = await api.lay_ds_tb_dvgt_theo_tt_ims({
                    hdtb_id: this.hdtb_id,
                    thuebao_id: this.thuebao_id,
                    loaitb_id: parseInt(this.cboLoaihinhTB.SelectedValue),
                    kieu_id: 0,
                    trangthaitb_id: 1,
                    kieuld_id: this.thongso_tc.kieuld_id
                  })
              }              
            }

            else
            {
              this.m_dsThueBao_DichVu.dsdvgt_ban = await api.lay_ds_tb_dvgt_theo_trangthai({
                "tinhthicong_id": this.tt_nd.tinh_id,
                "vhdtb_id": this.hdtb_id,
                "vthuebao_id": this.thuebao_id,
                "vloaitb_id": parseInt(this.cboLoaihinhTB.SelectedValue),
                "vkieu_id": 0,
                "vtrangthaitb_id": 1
              })                  
              if (this.tinh.tinhthicong_id > 0)
              {
                  this.m_dsThueBao_DichVu.dsdvgt_tc = await api.lay_ds_tb_dvgt_theo_trangthai({
                    "tinhthicong_id": this.tinh.tinhthicong_id,
                    "vhdtb_id": this.hdtb_id,
                    "vthuebao_id": this.thuebao_id,
                    "vloaitb_id": parseInt(this.cboLoaihinhTB.SelectedValue),
                    "vkieu_id": 0,
                    "vtrangthaitb_id": 1
                  })                      
              }
            }


        }
        catch (e)
        {
          console.log(e)
        }
    },
    async LAY_KHACHHANGID_THICONG(khachhangbanid) {
        let result = 0
        try {
            let params = {
                "khachhangbanid": khachhangbanid,
                "tinhthicong_id": this.tinh.tinhthicong_id
            }
            let response = await api.lay_khachhangid_thicong(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },
    GiaoDien_DichVuVT (dichvuvt_id){
      //đã check giao diện sẵn trong code
    },
    async LayTienTheoKhoanMuc (){      
      try
      {
          let tocdo_id = 0;
          if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL)
          {
              tocdo_id = parseInt(this.cboTocDo_Adsl.SelectedValue)
          }
          var params = {
            loaihd_id: LOAI_HOPDONG.KHOIPHUC_SD,
            ngay_yc: moment(new Date()).format("DD/MM/yyyy"),
            dichvuvt_id: parseInt(this.cboDichVuVT.SelectedValue),
            loaitb_id: parseInt(this.cboLoaihinhTB.SelectedValue),
            kieuld_id: parseInt(this.cboKieuLD.SelectedValue),
            phuongld_id: this.phuongld_id,
            tocdo_id: parseInt(this.cboTocDo_Adsl.SelectedValue),
            muccuoc_id: 0,
            ltb_adsl_id: 1,
            ltb_adsl_moi_id: 0,
            ltb_adsl_cu_id: 0,
            tocdo_moi_id: 0,
            tocdo_cu_id: 0,
            kh_cd: 0,
            phold_id: this.phold_id,
            apld_id: this.apld_id,
            khuld_id: this.khuld_id,
            doituong_id: this.doituong_id,
            trangbi_id: 0
          };
          let ds = await api.lay_tien_khoanmuc_ld(params)
          if (ds && ds.length > 0) {
              this.HienThiTienLapDat(ds);
              this.LayTongTien_HDTB();
          }
          else {
            this.dtienld = 0
          }
          
      }
      catch (ex)
      {
          console.log(ex);
      }
    },
    async LayHuongGiao_TiepNhan_BC(tinhthicong_id, loaihd_id, loaitb_id, tocdo_id, muccuoc_id, loaikenh_id, kieuld_id) {
        let result;
        try {
            var params = {
                tinhthicong_id: tinhthicong_id ? tinhthicong_id : 0,
                loaihd_id: loaihd_id ? loaihd_id : 0,
                loaitb_id: loaitb_id ? loaitb_id : 0,
                tocdo_id: tocdo_id ? tocdo_id : 0,
                muccuoc_id: muccuoc_id ? muccuoc_id : 0,
                loaikenh_id: loaikenh_id ? loaikenh_id : 0,
                kieuld_id: kieuld_id ? kieuld_id : 0,
                dichvuvt_id:parseInt(this.cboDichVuVT.SelectedValue)
            };            
            //let response = await api.layhuonggiao_tiepnhan(this.axios, params);
            let response = await api.post_layhuonggiao_tiepnhan_v4(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data
                    && response.data.data.length > 0) {
                result = response.data.data[0];                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async LAY_THONGSO_THUEBAO_BANCHEO_HDTBID(hdtb_id) {
        let result = [];
        try {
            var params = {
                tinhthicong_id: this.tinh.tinhthicong_id,
                hdtb_id: hdtb_id
            };
            let response = await api.lay_thongso_thuebao_bancheo_hdtbid(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;
            }
        } catch(e) {
            console.log(e);
        }
        return result;
    },
    async Gan_thongso_bancheo_hdtb_tsl(vhdtb) {
        let ds = await this.LAY_THONGSO_THUEBAO_BANCHEO_HDTBID(Number(vhdtb));
        if (ds && ds.length > 0) {
            for (let i = 0; i < ds.length; i++) {
                let drTHONGSO = this.CopyRow(ds[i])

                this.thongso_ban.kieuld_id = drTHONGSO.KIEULD_BAN;
                this.thongso_ban.doituong_id = drTHONGSO.DOITUONG_BAN;
                this.thongso_ban.chuquan_id = drTHONGSO.CHUQUAN_BAN;
                this.thongso_ban.chuquanld_id = drTHONGSO.CHUQUANLD_BAN;
                this.thongso_ban.chuquanld_id_cuoi = drTHONGSO.CHUQUANLD_BAN_CUOI;
                this.thongso_ban.congnghe_id = drTHONGSO.CONGNGHE_BAN;
                this.thongso_ban.kieutra_id = drTHONGSO.KIEUTRA_BAN;
                this.thongso_ban.loaikenh_id = drTHONGSO.LOAIKENH_BAN;
                this.thongso_ban.loaimd_id = drTHONGSO.LOAIMD_BAN;
                this.thongso_ban.ma_doicap = drTHONGSO.MADOICAP_BAN;
                this.thongso_ban.ma_tn = drTHONGSO.MATN_BAN;
                this.thongso_ban.muccuoc_id = drTHONGSO.MUCCUOC_BAN;
                this.thongso_ban.muccuoctb_id = drTHONGSO.MUCCUOCTB_BAN;
                this.thongso_ban.thietbidc_id = drTHONGSO.THIETBIDAU_BAN;
                this.thongso_ban.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_BAN;
                this.thongso_ban.thoihan_id = drTHONGSO.THOIHAN_BAN;
                this.thongso_ban.tocdo_id = drTHONGSO.TOCDO_BAN;
                this.thongso_ban.tocdo_isp = drTHONGSO.TOCDOISP_BAN;
                this.thongso_ban.tocdo_nix = drTHONGSO.TOCDONIX_BAN;
                this.thongso_ban.tocdo_pir_id = drTHONGSO.TOCDOPIR_BAN;
                this.thongso_ban.tocdothuc_id = drTHONGSO.TOCDOTHUC_BAN;
                this.thongso_ban.trangbi_id = drTHONGSO.TRANGBI_BAN;

                // Thông số thi công
                if (String(ds[i].tinh_dc_id) == "1") // Nếu row của tỉnh đầu
                {
                    this.thongso_tc_dau.kieuld_id = drTHONGSO.KIEULD_TC;
                    this.thongso_tc_dau.doituong_id = drTHONGSO.DOITUONG_TC;
                    this.thongso_tc_dau.chuquan_id = drTHONGSO.CHUQUAN_TC;
                    this.thongso_tc_dau.chuquanld_id = drTHONGSO.CHUQUANLD_TC;
                    this.thongso_tc_dau.congnghe_id = drTHONGSO.CONGNGHE_TC;
                    this.thongso_tc_dau.kieutra_id = drTHONGSO.KIEUTRA_TC;
                    this.thongso_tc_dau.loaikenh_id = drTHONGSO.LOAIKENH_TC;
                    this.thongso_tc_dau.loaimd_id = drTHONGSO.LOAIMD_TC;
                    this.thongso_tc_dau.ma_doicap = drTHONGSO.MADOICAP_TC;
                    this.thongso_tc_dau.ma_tn = drTHONGSO.MATN_TC;
                    this.thongso_tc_dau.muccuoc_id = drTHONGSO.MUCCUOC_TC;
                    this.thongso_tc_dau.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC;
                    this.thongso_tc_dau.thietbidc_id = drTHONGSO.THIETBIDAU_TC;
                    this.thongso_tc_dau.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC;
                    this.thongso_tc_dau.thoihan_id = drTHONGSO.THOIHAN_TC;
                    this.thongso_tc_dau.tocdo_id = drTHONGSO.TOCDO_TC;
                    this.thongso_tc_dau.tocdo_isp = drTHONGSO.TOCDOISP_TC;
                    this.thongso_tc_dau.tocdo_nix = drTHONGSO.TOCDONIX_TC;
                    this.thongso_tc_dau.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC;
                    this.thongso_tc_dau.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC;
                    this.thongso_tc_dau.trangbi_id = drTHONGSO.TRANGBI_TC;
                } else {
                    this.thongso_tc_cuoi.kieuld_id = drTHONGSO.KIEULD_TC;
                    this.thongso_tc_cuoi.doituong_id = drTHONGSO.DOITUONG_TC;
                    this.thongso_tc_cuoi.chuquan_id = drTHONGSO.CHUQUAN_TC;
                    this.thongso_tc_cuoi.chuquanld_id = drTHONGSO.CHUQUANLD_TC;
                    this.thongso_tc_cuoi.congnghe_id = drTHONGSO.CONGNGHE_TC;
                    this.thongso_tc_cuoi.kieutra_id = drTHONGSO.KIEUTRA_TC;
                    this.thongso_tc_cuoi.loaikenh_id = drTHONGSO.LOAIKENH_TC;
                    this.thongso_tc_cuoi.loaimd_id = drTHONGSO.LOAIMD_TC;
                    this.thongso_tc_cuoi.ma_doicap = drTHONGSO.MADOICAP_TC;
                    this.thongso_tc_cuoi.ma_tn = drTHONGSO.MATN_TC;
                    this.thongso_tc_cuoi.muccuoc_id = drTHONGSO.MUCCUOC_TC;
                    this.thongso_tc_cuoi.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC;
                    this.thongso_tc_cuoi.thietbidc_id = drTHONGSO.THIETBIDAU_TC;
                    this.thongso_tc_cuoi.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC;
                    this.thongso_tc_cuoi.thoihan_id = drTHONGSO.THOIHAN_TC;
                    this.thongso_tc_cuoi.tocdo_id = drTHONGSO.TOCDO_TC;
                    this.thongso_tc_cuoi.tocdo_isp = drTHONGSO.TOCDOISP_TC;
                    this.thongso_tc_cuoi.tocdo_nix = drTHONGSO.TOCDONIX_TC;
                    this.thongso_tc_cuoi.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC;
                    this.thongso_tc_cuoi.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC;
                    this.thongso_tc_cuoi.trangbi_id = drTHONGSO.TRANGBI_TC;
                }
            }
        }
    },
    async Gan_thongso_bancheo_hdtb (vhdtb) {
      console.log('Gan_thongso_bancheo_hdtb')
      
      var ds = await api.lay_thongso_thuebao_hdbc(vhdtb)
      
      if (ds && ds.length > 0) {
        var drTHONGSO = this.CopyRow(ds[0])
        var dvvt_id = ds[0]["DICHVUVT_ID"]
        if (dvvt_id != DICHVU_VIENTHONG.HOINGHI_TRUYENHINH &&
                    dvvt_id != DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT &&
                    dvvt_id != DICHVU_VIENTHONG.DICHVU_CNTT &&
                    dvvt_id != DICHVU_VIENTHONG.TRUNGTAM_DULIEU) {
          this.thongso_ban.chuquanld_id = drTHONGSO.CHUQUANLD_BAN
          this.thongso_ban.congnghe_id = drTHONGSO.CONGNGHE_BAN
          this.thongso_ban.loaikenh_id = drTHONGSO.LOAIKENH_BAN
          this.thongso_ban.loaimd_id = drTHONGSO.LOAIMD_BAN
          this.thongso_ban.ma_doicap = drTHONGSO.MADOICAP_BAN
          this.thongso_ban.chuquan_id = drTHONGSO.CHUQUAN_BAN
          this.thongso_ban.ma_tn = drTHONGSO.MATN_BAN
          this.thongso_ban.thietbidc_id = drTHONGSO.THIETBIDAU_BAN
          this.thongso_ban.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_BAN
          this.thongso_ban.thoihan_id = drTHONGSO.THOIHAN_BAN
          this.thongso_ban.tocdo_id = drTHONGSO.TOCDO_BAN
          this.thongso_ban.tocdo_isp = drTHONGSO.TOCDOISP_BAN
          this.thongso_ban.tocdo_nix = drTHONGSO.TOCDONIX_BAN

          this.thongso_ban.tocdo_pir_id = drTHONGSO.TOCDOPIR_BAN
          this.thongso_ban.phanloai_id = drTHONGSO.PHANLOAI_ID
          this.thongso_ban.phantach_id = drTHONGSO.PHANTACH_TD

          this.thongso_ban.tocdothuc_id = drTHONGSO.TOCDOTHUC_BAN
          this.thongso_ban.trangbi_id = drTHONGSO.TRANGBI_BAN

          this.thongso_tc.chuquan_id = drTHONGSO.CHUQUAN_TC
          this.thongso_tc.chuquanld_id = drTHONGSO.CHUQUANLD_TC
          this.thongso_tc.congnghe_id = drTHONGSO.CONGNGHE_TC
          this.thongso_tc.loaikenh_id = drTHONGSO.LOAIKENH_TC
          this.thongso_tc.loaimd_id = drTHONGSO.LOAIMD_TC
          this.thongso_tc.ma_doicap = drTHONGSO.MADOICAP_TC
          this.thongso_tc.ma_tn = drTHONGSO.MATN_TC

          this.thongso_tc.thietbidc_id = drTHONGSO.THIETBIDAU_TC
          this.thongso_tc.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC

          this.thongso_tc.thoihan_id = drTHONGSO.THOIHAN_TC

          this.thongso_tc.tocdo_id = drTHONGSO.TOCDO_TC
          this.thongso_tc.tocdo_isp = drTHONGSO.TOCDOISP_TC
          this.thongso_tc.tocdo_nix = drTHONGSO.TOCDONIX_TC

          this.thongso_tc.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC

          this.thongso_tc.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC
          this.thongso_tc.trangbi_id = drTHONGSO.TRANGBI_TC
        }
        this.thongso_ban.muccuoc_id = drTHONGSO.MUCCUOC_BAN
        this.thongso_ban.muccuoctb_id = drTHONGSO.MUCCUOCTB_BAN
        this.thongso_ban.kieuld_id = drTHONGSO.KIEULD_BAN
        this.thongso_ban.doituong_id = drTHONGSO.DOITUONG_BAN
        this.thongso_tc.kieuld_id = drTHONGSO.KIEULD_TC
        this.thongso_tc.doituong_id = drTHONGSO.DOITUONG_TC
        this.thongso_tc.muccuoc_id = drTHONGSO.MUCCUOC_TC
        this.thongso_tc.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC

        this.thongso_tc.kieutra_id = drTHONGSO.KIEUTRA_TC
        this.thongso_ban.kieutra_id = drTHONGSO.KIEUTRA_BAN
        if (dvvt_id == DICHVU_VIENTHONG.MEGAWAN || dvvt_id == DICHVU_VIENTHONG.METRONET || dvvt_id == DICHVU_VIENTHONG.TSL) {
          // gan tskt rieng cho kenh qt
          if (drTHONGSO.MHKN_ID) { this.thongso_ban.mhkn_id = drTHONGSO.MHKN_ID }
          if (drTHONGSO.GDKN_ID) { this.thongso_ban.gdkn_id = drTHONGSO.GDKN_ID }
          if (drTHONGSO.YC_BAOVE) { this.thongso_ban.yc_baove = drTHONGSO.YC_BAOVE }
          if (drTHONGSO.TOCDO_POP) { this.thongso_ban.tocdo_pop = drTHONGSO.TOCDO_POP }
          if (drTHONGSO.GDKN_POP_ID) { this.thongso_ban.gdkn_pop_id = drTHONGSO.GDKN_POP_ID }
          if (drTHONGSO.CAUHINH_DT_ID) { this.thongso_ban.cauhinh_dt_id = drTHONGSO.CAUHINH_DT_ID }
          if (drTHONGSO.KIEU_DUPLEX_ID) { this.thongso_ban.kieu_duplex_id = drTHONGSO.KIEU_DUPLEX_ID }
          if (drTHONGSO.KIEUKN_ID) { this.thongso_ban.kieukn_id = drTHONGSO.KIEUKN_ID }

          this.thongso_ban.mtu_size = drTHONGSO.MTU_SIZE
          this.thongso_ban.auto_nego = drTHONGSO.AUTO_NEGO
          this.thongso_ban.sncp = drTHONGSO.SNCP
          this.thongso_ban.cap_chinh = drTHONGSO.CAP_CHINH
          this.thongso_ban.cap_duphong = drTHONGSO.CAP_DUPHONG
          this.thongso_ban.diem_pop = drTHONGSO.DIEM_POP
          this.thongso_ban.wan_ip = drTHONGSO.WAN_IP
          this.thongso_ban.lan_ip = drTHONGSO.LAN_IP
          this.thongso_ban.ce_ip = drTHONGSO.CE_IP
          this.thongso_ban.tt_ip = drTHONGSO.TT_IP
          this.thongso_ban.sl_ip = drTHONGSO.SL_IP
          this.thongso_ban.ten_trunk_nni = drTHONGSO.TEN_TRUNK_NNI
          this.thongso_ban.so_as = drTHONGSO.SO_AS
          this.thongso_ban.hqos = drTHONGSO.HQOS
          this.thongso_ban.cos = drTHONGSO.COS
          this.thongso_ban.svlan = drTHONGSO.SVLAN
          this.thongso_ban.cvlan = drTHONGSO.CVLAN
          this.thongso_ban.vlan_transparent = drTHONGSO.VLAN_TRANSPARENT
          this.thongso_ban.tt_port = drTHONGSO.TT_PORT

          if (drTHONGSO.MHKN_ID) { this.thongso_tc.mhkn_id = drTHONGSO.MHKN_ID }
          if (drTHONGSO.GDKN_ID) { this.thongso_tc.gdkn_id = drTHONGSO.GDKN_ID }
          if (drTHONGSO.YC_BAOVE) { this.thongso_tc.yc_baove = drTHONGSO.YC_BAOVE }
          if (drTHONGSO.TOCDO_POP) { this.thongso_tc.tocdo_pop = drTHONGSO.TOCDO_POP }
          if (drTHONGSO.GDKN_POP_ID) { this.thongso_tc.gdkn_pop_id = drTHONGSO.GDKN_POP_ID }
          if (drTHONGSO.CAUHINH_DT_ID) { this.thongso_tc.cauhinh_dt_id = drTHONGSO.CAUHINH_DT_ID }
          if (drTHONGSO.KIEU_DUPLEX_ID) { this.thongso_tc.kieu_duplex_id = drTHONGSO.KIEU_DUPLEX_ID }
          if (drTHONGSO.KIEUKN_ID) { this.thongso_tc.kieukn_id = drTHONGSO.KIEUKN_ID }

          this.thongso_tc.mtu_size = drTHONGSO.MTU_SIZE
          this.thongso_tc.auto_nego = drTHONGSO.AUTO_NEGO
          this.thongso_tc.sncp = drTHONGSO.SNCP
          this.thongso_tc.cap_chinh = drTHONGSO.CAP_CHINH
          this.thongso_tc.cap_duphong = drTHONGSO.CAP_DUPHONG
          this.thongso_tc.diem_pop = drTHONGSO.DIEM_POP
          this.thongso_tc.wan_ip = drTHONGSO.WAN_IP
          this.thongso_tc.lan_ip = drTHONGSO.LAN_IP
          this.thongso_tc.ce_ip = drTHONGSO.CE_IP
          this.thongso_tc.tt_ip = drTHONGSO.TT_IP
          this.thongso_tc.sl_ip = drTHONGSO.SL_IP
          this.thongso_tc.ten_trunk_nni = drTHONGSO.TEN_TRUNK_NNI
          this.thongso_tc.so_as = drTHONGSO.SO_AS
          this.thongso_tc.hqos = drTHONGSO.HQOS
          this.thongso_tc.cos = drTHONGSO.COS
          this.thongso_tc.svlan = drTHONGSO.SVLAN
          this.thongso_tc.cvlan = drTHONGSO.CVLAN
          this.thongso_tc.vlan_transparent = drTHONGSO.VLAN_TRANSPARENT
          this.thongso_tc.tt_port = drTHONGSO.TT_PORT

          this.thongso_ban.loai_ip = await api.lay_ds_loaiip_theo_hdtb_id(vhdtb)
          this.thongso_tc.loai_ip = this.thongso_ban.loai_ip
          this.thongso_ban.kieu_nghiemthu = await api.lay_ds_kieunt_theo_hdtb_id(vhdtb)
          this.thongso_tc.kieu_nghiemthu = this.thongso_ban.kieu_nghiemthu
        }
      }
    },
    async TaoDuLieuBC (themmoi) {
      if (this.tinh.tinhthicong_id != 0 || this.kenh_trang) {
        if (this.dichvuvt_id == DICHVU_VIENTHONG.TSL || this.dichvuvt_id == 25) {
          this.dsHDBC = []
          this.dsHDTBBC = []
          await this.TaoDuLieu_HDTBBC_TSL(themmoi, 1)
          await this.TaoDuLieu_HDTBBC_TSL(themmoi, 2)
        } else {
          await this.TaoDuLieu_HDTBBC(themmoi)
        }
        //this.TaoDuLieu_LYDOHUYBC()
        this.TaoDuLieu_DKDVBC()

        this.dsHDBC_ADSL = []
        this.dsHDBC_CD = []
        this.dsHDBC_TSL = []
        this.dsHDBC_IMS = []
        this.dsHDBC_MGW = []


        if (this.cboDichVuVT.SelectedValue == DICHVU_VIENTHONG.ADSL ) { 
          await this.TaoDuLieu_HDBC_ADSL(themmoi) 
        }
        else if (this.cboDichVuVT.SelectedValue == DICHVU_VIENTHONG.CO_DINH) { 
          await this.TaoDuLieu_HDBC_CD(themmoi) 
        }        
        else if (this.cboDichVuVT.SelectedValue == DICHVU_VIENTHONG.TSL || this.cboDichVuVT.SelectedValue == 25) {
          this.dsHDBC_TSL = []          
          await this.TaoDuLieu_HDBC_TSL(themmoi, 1)
          await this.TaoDuLieu_HDBC_TSL(themmoi, 2)
        }
        else if (this.cboDichVuVT.SelectedValue == DICHVU_VIENTHONG.MEGAWAN || this.cboDichVuVT.SelectedValue == DICHVU_VIENTHONG.METRONET || this.cboDichVuVT.SelectedValue == 26) { 
          await this.TaoDuLieu_HDBC_MGWAN(themmoi) 
        }        
        else if (this.cboDichVuVT.SelectedValue == DICHVU_VIENTHONG.IMS) { 
          await this.TaoDuLieu_HDBC_IMS(themmoi) 
        }        
      }
    },
    async TaoDuLieu_DKDVBC() {
      this.dsDKBCDVGT = [];
      if(!this.m_dsThueBao_DichVu.dsdvgt_tc)
        return;

      console.log('TaoDuLieu_DKDVBC');
      this.m_dsThueBao_DichVu.dsdvgt_tc = this.LowerCasePropertyList(this.m_dsThueBao_DichVu.dsdvgt_tc); 
      var kieu_dk_dvgt = 0;
      console.log('this.m_dsThueBao_DichVu', this.m_dsThueBao_DichVu.dsdvgt_tc);
      for (var dr of this.m_dsThueBao_DichVu.dsdvgt_tc) {
          if (dr["dangky"].toString() == "1")
              kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT;
          else if (dr["huy"].toString() == "1")
              kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT;
          else {
              continue;
          }

          var rowHDTDDV = {};
          rowHDTDDV.HDTB_ID = this.hdtb_id;
          rowHDTDDV.NGAY_YC =  moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
          rowHDTDDV.DICHVUGT_ID = (dr["dichvugt_id"] ? dr["dichvugt_id"]*1 : dr["dichvugtid"]*1);
          rowHDTDDV.NOIDUNG = dr["noidung"];
          rowHDTDDV.KIEU_YC = kieu_dk_dvgt;
          rowHDTDDV.TTTH = 1;

          this.dsDKBCDVGT.push(Object.assign({}, rowHDTDDV));
      }
      this.dsDKBCDVGT = this.dsDKBCDVGT && this.dsDKBCDVGT.length > 0 ? this.UpperCasePropertyList(this.dsDKBCDVGT) : []  
      console.log('TaoDuLieu_DKDV DONE', this.dsDK_DVGT);
    },
    async TaoDuLieu_HDTBBC_TSL (themmoi, vdaucuoi_id) {
      let ds = []
      let rowHDBC = {}
      let tinh_dau_id = 0
      let tinh_cuoi_id = 0
      let thuebao_id_dau = 0
      let thuebao_id_cuoi = 0

      if (!this.kenh_trang) {
        let dtTinh_id_tsl = await api.lay_tinhtc_id_tsl(this.txtMaTB.Text, this.cboDichVuVT.SelectedValue)
        tinh_dau_id = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 1)["tinh_thicong"])
        tinh_cuoi_id = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 2)["tinh_thicong"])
        thuebao_id_dau = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 1)["thuebao_kn_id"])
        thuebao_id_cuoi = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 2)["thuebao_kn_id"])

        this.tinh_dau = await api.tinh_thicong(tinh_dau_id)
        this.tinh_cuoi = await api.tinh_thicong(tinh_cuoi_id)

        if (vdaucuoi_id == 1) {
          ds = await api.lay_ds_db_thuebao_theo_tbid(this.tinh_dau.tinhthicong_id, thuebao_id_dau)
          rowHDBC.TINH_THICONG = this.tinh_dau.tinhthicong_id
          rowHDBC.THUEBAO_ID_THICONG = thuebao_id_dau
        } else {
          ds = await api.lay_ds_db_thuebao_theo_tbid(this.tinh_cuoi.tinhthicong_id, thuebao_id_cuoi)
          rowHDBC.TINH_THICONG = this.tinh_cuoi.tinhthicong_id
          rowHDBC.THUEBAO_ID_THICONG = thuebao_id_cuoi
        }
      } else {
        if (vdaucuoi_id == 1) {
          rowHDBC.TINH_THICONG = this.tinh_dau.tinhthicong_id
          rowHDBC.THUEBAO_ID_THICONG = 0
        } else {
          rowHDBC.TINH_THICONG = this.tinh_cuoi.tinhthicong_id
          rowHDBC.THUEBAO_ID_THICONG = 0
        }
      }

      rowHDBC.DAUCUOI_ID = vdaucuoi_id

      rowHDBC.HDTB_BAN = this.hdtb_id
      rowHDBC.TINH_BAN = this.tt_nd.tinh_id
      rowHDBC.TRANG_THAI = 0
      rowHDBC.LOAIHD_BANCHEO = 1

      rowHDBC.THUEBAO_ID_BAN = this.thuebao_id
      if (this.kenh_trang) {
        rowHDBC.KHACHHANG_ID = null
        rowHDBC.THANHTOAN_ID = null
      } else {
        rowHDBC.THANHTOAN_ID_TC = parseInt(ds[0]["thanhtoan_id"])
        rowHDBC.KHACHHANG_ID_TC = parseInt(ds[0]["khachhang_id"])
      }

      var mapId = await api.fn_tt_db_thuebao({param:this.thuebao_id,type:1})// attt: "thanhtoan_id", "db_thuebao", "where thuebao_id =" + this.thuebao_id)
      rowHDBC.THANHTOAN_ID_BAN = parseInt(mapId)
      rowHDBC.KHACHHANG_ID_BAN = this.khachhang_id

      this.dsHDBC.push(rowHDBC)
      // hop dong thue bao ban cheo
      let rowHDTBBC = {}
      if (!this.kenh_trang) {
        if (vdaucuoi_id == 1) {
          cmUlt.CopyRowAndUppercase(await api.lay_danhba_theo_thuebaoid(thuebao_id_dau, DICHVU_VIENTHONG.TSL, 0, 2)[0], rowHDTBBC)
          rowHDTBBC.TINH_ID = this.tinh_dau.tinhthicong_id
        } else {
          cmUlt.CopyRowAndUppercase(await api.lay_danhba_theo_thuebaoid(thuebao_id_cuoi, DICHVU_VIENTHONG.TSL, 0, 2)[0], rowHDTBBC)
          rowHDTBBC.TINH_ID = this.tinh_cuoi.tinhthicong_id
        }
      } else {
        if (vdaucuoi_id == 1) {
          cmUlt.CopyRowAndUppercase(this.dsHDTB[0], rowHDTBBC)
          rowHDTBBC.MUCUOCTB_ID = this.thongso_tc_dau.muccuoctb_id
          try {
            var muccuocTb = await api.fn_tt_muccuoc_tb({param:parseInt(this.thongso_tc_dau.muccuoctb_id),type:1}) // attt: api.map _id("cuoc_tb", "CSS.muccuoc_tb", "where mucuoctb_id = " + this.thongso_tc_dau.muccuoctb_id)
            rowHDTBBC.MUCUOC_TB = parseInt(muccuocTb)
          } catch (error) {

          }

          rowHDTBBC.DOITUONG_ID = this.thongso_tc_dau.doituong_id
          rowHDTBBC.TINH_ID = this.tinh_dau.tinhthicong_id
        } else {
          cmUlt.CopyRowAndUppercase(this.dsHDTB[0], rowHDTBBC)
          rowHDTBBC.MUCUOCTB_ID = this.thongso_tc_cuoi.muccuoctb_id
          try {
            var muccuocTb = await api.fn_tt_muccuoc_tb({param:parseInt(this.thongso_tc_cuoi.muccuoctb_id),type:1}) // attt: api.map _id("cuoc_tb", "CSS.muccuoc_tb", "where mucuoctb_id = " + this.thongso_tc_cuoi.muccuoctb_id)
            rowHDTBBC.MUCUOC_TB = parseInt(muccuocTb)
          } catch (error) {

          }

          rowHDTBBC.DOITUONG_ID = this.thongso_tc_cuoi.doituong_id
          rowHDTBBC.TINH_ID = this.tinh_cuoi.tinhthicong_id
        }
        rowHDTBBC.THUEBAO_ID = null
        rowHDTBBC.DONVI_ID = null
      }
      rowHDTBBC.HDTB_ID = this.hdtb_id
      rowHDTBBC.HDKH_ID = this.hdkh_id
      rowHDTBBC.TTHD_ID = 1

      if (vdaucuoi_id == 1) {
        rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn_dau
        rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn_dau
        rowHDTBBC.TINH_ID = this.tinh_dau.tinhthicong_id
      }
      if (vdaucuoi_id == 2) {
        rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn_cuoi
        rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn_cuoi
        rowHDTBBC.TINH_ID = this.tinh_cuoi.tinhthicong_id
      }

      rowHDTBBC.KIEULD_ID = this.thongso_tc.kieuld_id

      rowHDTBBC.NGUOI_CN = this.tt_nd.ma_nd
      rowHDTBBC.NGAY_CN = this.tt_nd.ngay_cn
      rowHDTBBC.MAY_CN = this.tt_nd.may_cn
      rowHDTBBC.IP_CN = this.tt_nd.ip
      rowHDTBBC.GHICHU = this.txtGhiChu.Text
      rowHDTBBC.TINH_DC_ID = vdaucuoi_id
      if ((this.dichvuvt_id == DICHVU_VIENTHONG.TSL || this.dichvuvt_id == 25) && !this.kenh_trang) {
        try {
          var donvi_dau_id = parseInt(await api.fn_tt_db_thuebao_bc({tinththicong_id: this.tinh_dau.tinhthicong_id, thuebao_id:rowHDTBBC.THUEBAO_ID,type:6}))// attt: : api.map _id("donvi_dau_id", "CSS_" + this.tinh_dau.matinh + ".db_thuebao", "where thuebao_id = " + rowHDTBBC.THUEBAO_ID))
          rowHDTBBC.DONVI_DAU_ID = donvi_dau_id
        } catch (error) {

        }

        try {
          var donvi_cuoi_id = parseInt(await api.fn_tt_db_thuebao_bc({tinththicong_id: this.tinh_cuoi.tinhthicong_id,thuebao_id:rowHDTBBC.THUEBAO_ID,type:6}))// attt: : api.map _id("donvi_cuoi_id", "CSS_" + this.tinh_cuoi.matinh + ".db_thuebao", "where thuebao_id = " + rowHDTBBC.THUEBAO_ID))
          rowHDTBBC.DONVI_CUOI_ID = donvi_cuoi_id
        } catch (error) {

        }
      }

      if (this.dichvuvt_id == DICHVU_VIENTHONG.DI_DONG || this.dichvuvt_id == DICHVU_VIENTHONG.ADSL ||
          this.dichvuvt_id == DICHVU_VIENTHONG.IMS || this.dichvuvt_id == DICHVU_VIENTHONG.CO_DINH ||
          this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == 25 || this.dichvuvt_id == 26 || this.dichvuvt_id == DICHVU_VIENTHONG.TSL ||
          this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == DICHVU_VIENTHONG.TRUNGTAM_DULIEU ||
          this.dichvuvt_id == DICHVU_VIENTHONG.DICHVU_CNTT || this.dichvuvt_id == DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
          this.dichvuvt_id == DICHVU_VIENTHONG.GPHONE || this.dichvuvt_id == DICHVU_VIENTHONG.HOINGHI_TRUYENHINH) {
        rowHDTBBC.STATUS = "0"
      }
      this.dsHDTBBC.push(rowHDTBBC)
    },
    async TaoDuLieu_HDTBBC (themmoi) {
      console.log('TaoDuLieu_HDTBBC')
      this.dsHDBC = []

      let ds = []
      if (!this.kenh_trang && !this.chi_giao_net) { ds = await api.lay_ds_db_thuebao_theo_tbid(this.tinh.tinhthicong_id, this.thuebao_tc_id) }

      let rowHDBC = {}

      rowHDBC.HDTB_BAN = this.hdtb_id
      rowHDBC.TINH_THICONG = this.tinh.tinhthicong_id
      rowHDBC.TINH_BAN = this.tt_nd.tinh_id
      rowHDBC.TRANG_THAI = 0
      rowHDBC.LOAIHD_BANCHEO = 1

      rowHDBC.THUEBAO_ID_BAN = this.thuebao_id
      rowHDBC.THUEBAO_ID_THICONG = this.thuebao_tc_id

      if (!this.kenh_trang && !this.chi_giao_net && ds.length > 0) {
        rowHDBC.THANHTOAN_ID_TC = parseInt(ds[0]["thanhtoan_id"])
        rowHDBC.KHACHHANG_ID_TC = parseInt(ds[0]["khachhang_id"])
      } else {
        rowHDBC.THANHTOAN_ID_TC = 0
        rowHDBC.KHACHHANG_ID_TC = 0
      }

      rowHDBC.THANHTOAN_ID_BAN = parseInt(await api.fn_tt_db_thuebao({param:this.thuebao_id,type:1}))// attt: api.map _id("thanhtoan_id", "db_thuebao", "where thuebao_id =" + this.thuebao_id))
      rowHDBC.KHACHHANG_ID_BAN = this.khachhang_id

      this.dsHDBC.push(rowHDBC)

      this.dsHDTBBC = []
      let rowHDTBBC = {}

      if (!this.kenh_trang && !this.chi_giao_net) {
        var ds1 = await api.bancheo_lay_danhba_theo_thuebaoid(this.tinh.tinhthicong_id, parseInt(this.thuebao_tc_id), parseInt(this.cboDichVuVT.SelectedValue), 0, 2)
        if (ds1.length > 0) {
          cmUlt.CopyRowAndUppercase(ds1[0], rowHDTBBC)
        }
      } else {
        cmUlt.CopyRowAndUppercase(this.dsHDTB[0], rowHDTBBC)
        rowHDTBBC.MUCUOCTB_ID = this.thongso_tc.muccuoctb_id
        rowHDTBBC.MUCUOC_TB = parseInt(await await api.fn_tt_muccuoc_tb({param:parseInt(this.thongso_tc.muccuoctb_id),type:1})) // attt: api.map _id(this.tinh.tinhthicong_id, "cuoc_tb", "CSS.muccuoc_tb", "where mucuoctb_id = " + this.thongso_tc.muccuoctb_id))
        rowHDTBBC.DOITUONG_ID = this.thongso_tc.doituong_id
        rowHDTBBC.MA_TB = this.txtMaTB.Text
        // rowHDTBBC.SetTHUEBAO_IDNull();
        // rowHDTBBC.SetDONVI_IDNull();
        rowHDTBBC.THUEBAO_ID = null
        rowHDTBBC.DONVI_ID = null

      }
      if (rowHDTBBC.NGAYLAP_HD) rowHDTBBC.NGAYLAP_HD = moment(rowHDTBBC.NGAYLAP_HD,'yyyy-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') 
      if (rowHDTBBC.NGAY_SN_TB) rowHDTBBC.NGAY_SN_TB = moment(rowHDTBBC.NGAY_SN_TB,'yyyy-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') 
      if (rowHDTBBC.NGAY_HK) rowHDTBBC.NGAY_HK = moment(rowHDTBBC.NGAY_HK,'yyyy-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') 
      if (rowHDTBBC.NGAY_SN) rowHDTBBC.NGAY_SN = moment(rowHDTBBC.NGAY_SN,'yyyy-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') 
      if (rowHDTBBC.NGAY_SD) rowHDTBBC.NGAY_SD = moment(rowHDTBBC.NGAY_SD,'yyyy-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') 
      if (rowHDTBBC.NGAYCAP) rowHDTBBC.NGAYCAP = moment(rowHDTBBC.NGAYCAP,'yyyy-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') 
      if (rowHDTBBC.NGAY_CAT) rowHDTBBC.NGAY_CAT = moment(rowHDTBBC.NGAY_CAT,'yyyy-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') 
      if (rowHDTBBC.NGAY_CV) rowHDTBBC.NGAY_CV = moment(rowHDTBBC.NGAY_CV,'yyyy-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') 

      rowHDTBBC.HDTB_ID = this.hdtb_id
      rowHDTBBC.HDKH_ID = this.hdkh_id
      rowHDTBBC.TTHD_ID = 1
      rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn
      rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn
      rowHDTBBC.KIEULD_ID = this.thongso_tc.kieuld_id
      rowHDTBBC.NGUOI_CN = this.tt_nd.ma_nd
      rowHDTBBC.NGAY_CN = this.tt_nd.ngay_cn
      rowHDTBBC.MAY_CN = this.tt_nd.may_cn
      rowHDTBBC.IP_CN = this.tt_nd.ip
      rowHDTBBC.GHICHU = this.txtGhiChu.Text

      

      if (this.dichvuvt_id == DICHVU_VIENTHONG.DI_DONG || this.dichvuvt_id == DICHVU_VIENTHONG.ADSL ||
                this.dichvuvt_id == DICHVU_VIENTHONG.IMS || this.dichvuvt_id == DICHVU_VIENTHONG.CO_DINH ||
                this.dichvuvt_id == DICHVU_VIENTHONG.MEGAWAN || this.dichvuvt_id == 25 || this.dichvuvt_id == 26 || this.dichvuvt_id == DICHVU_VIENTHONG.TSL ||
                this.dichvuvt_id == DICHVU_VIENTHONG.METRONET || this.dichvuvt_id == DICHVU_VIENTHONG.TRUNGTAM_DULIEU ||
                this.dichvuvt_id == DICHVU_VIENTHONG.DICHVU_CNTT || this.dichvuvt_id == DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
                this.dichvuvt_id == DICHVU_VIENTHONG.GPHONE || this.dichvuvt_id == DICHVU_VIENTHONG.HOINGHI_TRUYENHINH) {
        rowHDTBBC.STATUS = "0"
      }
      this.dsHDTBBC.push(rowHDTBBC)
      console.log(this.dsHDTBBC)
    },
    async TaoDuLieu_HDBC_ADSL (themmoi) {
      console.log('TaoDuLieu_HDBC_ADSL')
      this.dsHDBC_ADSL = []

      let rowHDBCADSL = {}
      var ds = await api.lay_ds_db_adsl_theo_tbid_bc(this.tinh.tinhthicong_id, this.thuebao_tc_id)
      if (ds.length > 0) {
        cmUlt.CopyRowAndUppercase(ds[0], rowHDBCADSL)
        rowHDBCADSL.HDTB_ID = this.hdtb_id
        rowHDBCADSL.TOCDO_ID = this.thongso_tc.tocdo_id
        rowHDBCADSL.MUCCUOC_ID = this.thongso_tc.muccuoc_id
        rowHDBCADSL.THONGTIN_TC = this.txtTTCuoc.Text
        rowHDBCADSL.CHUQUAN_ID = this.thongso_tc.chuquan_id
        rowHDBCADSL.CHUQUANLD_ID = this.thongso_tc.chuquanld_id
        rowHDBCADSL.TRANGBI_ID = this.thongso_tc.trangbi_id
        rowHDBCADSL.THIETBIDC_ID = this.thongso_tc.thietbidc_id
        
        var loaitb_id = parseInt(this.cboLoaihinhTB.SelectedValue)
        if (loaitb_id == LOAIHINH_THUEBAO.INTERNET_1260 || loaitb_id == LOAIHINH_THUEBAO.INTERNET_ADSL
            || loaitb_id == LOAIHINH_THUEBAO.INTERNET_FTTH || loaitb_id == LOAIHINH_THUEBAO.WIFI_FIBER)
        {
            
            rowHDBCADSL.HANDATCHO = moment(new Date()).add(7, 'days').format("DD/MM/YYYY")
            
        }
        //HungHM - Bổ xung thêm mã truy nhập + mã đôi cáp
        rowHDBCADSL.MATB_TN = this.thongso_tc.ma_tn.trim()
        //rowHDTB_ADSL.MADOICAP = getkey.GetKey("MADOICAP");

        var mapId = await api.fn_tt_kieu_ld({param:this.thongso_tc.kieuld_id,type:1})// attt api.map _id("kieu", "css.kieu_ld", "where kieuld_id =" + this.thongso_tc.kieuld_id)
        var kieu = parseInt(mapId) 
        //Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
        if (kieu == 1 || kieu == 2)
        {
            if (this.thongso_tc.ma_doicap.trim().length == 0)
            {                
                this.$toast.error("Đối kiểu lắp đặt là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.")
                return
            }
            else
                rowHDBCADSL.MADOICAP = parseInt(this.thongso_tc.ma_doicap.trim()) 
        }
        else //TH còn lại sinh mã đôi cáp
        {
            if (themmoi == true)
            {
                rowHDBCADSL.MADOICAP = this.getKey("MADOICAP")
            }

        }
        
        this.dsHDBC_ADSL.push(rowHDBCADSL)
        console.log(this.dsHDBC_ADSL)
      }
    },
    async TaoDuLieu_HDBC_CD (themmoi) {
      this.dsHDBC_CD = []
      let rowHDBC_CD = {}

      var ds = await api.lay_ds_db_cd_theo_tbid_bc(this.tinh.tinhthicong_id, this.thuebao_tc_id)
      if (ds.length > 0) {
        cmUlt.CopyRowAndUppercase(ds[0], rowHDBC_CD)
        rowHDBC_CD.HDTB_ID = this.hdtb_id

        rowHDBC_CD.MATB_TN = this.thongso_tc.ma_tn.trim()

        var mapId = await api.fn_tt_kieu_ld({param:this.thongso_tc.kieuld_id,type:1})// attt  api.map _id("kieu", "css.kieu_ld", "where kieuld_id =" + this.thongso_tc.kieuld_id)
        var kieu = parseInt(mapId) 
        //Nếu lắp trên đường có sẵn hoặc lắp kèm thì lấy mã đôi cáp từ form chọn xuống
        if (kieu == 1 || kieu == 2)
        {
            if (this.thongso_tc.ma_doicap.trim().length == 0)
            {
                this.$toast.error("Đối kiểu lắp đặt là lắp kèm hoặc trên đường có sẵn thì mã đôi cáp không được trống.")                
                return
            }
            rowHDBC_CD.MADOICAP = parseInt(this.thongso_tc.ma_doicap.trim())

        }
        else //TH còn lại sinh mã đôi cáp
        {
            if (themmoi == true)
            {
                rowHDBC_CD.MADOICAP = this.getKey("MADOICAP")
            }
        }


        this.dsHDBC_CD.push(this.rowHDBC_CD)
      }
    },
    async TaoDuLieu_HDBC_IMS (themmoi) {
      this.dsHDBC_IMS = []

      let rowHDBC_IMS = {}
      var ds = await api.lay_ds_db_ims_theo_tbid_bc(this.tinh.tinhthicong_id, this.thuebao_tc_id)
      if (ds.length > 0) {
        cmUlt.CopyRowAndUppercase(ds[0], rowHDBC_IMS)
      }
      rowHDBC_IMS.HDTB_ID = this.hdtb_id
      this.dsHDBC_IMS.push(rowHDBC_IMS)
    },
    async TaoDuLieu_HDBC_MGWAN (themmoi) {
      this.dsHDBC_MGW = []

      let rowHDBC_MGW = {}
      if (!this.kenh_trang && !this.chi_giao_net) {
        var ds = await api.lay_ds_db_megawan_theo_tbid_bc(this.tinh.tinhthicong_id, this.thuebao_tc_id)
        if (ds.length > 0) {
          cmUlt.CopyRowAndUppercase(ds[0], rowHDBC_MGW)
        }
      } else {
        rowHDBC_MGW.MADOICAP = -1
        rowHDBC_MGW.LOAIMD_ID = this.thongso_tc.loaimd_id

        if (this.thongso_tc.chuquan_id == 0) { rowHDBC_MGW.CHUQUAN_ID = null } else { rowHDBC_MGW.CHUQUAN_ID = this.thongso_tc.chuquan_id }

        if (this.thongso_tc.loaikenh_id == 0) { rowHDBC_MGW.LOAIKENH_ID = null } else { rowHDBC_MGW.LOAIKENH_ID = this.thongso_tc.loaikenh_id }

        if (this.thongso_tc.thietbidc_id == 0) { rowHDBC_MGW.THIETBIDC_ID = null } else { rowHDBC_MGW.THIETBIDC_ID = this.thongso_tc.thietbidc_id }

        if (this.thongso_tc.thoihan_id == 0) { rowHDBC_MGW.THOIHAN_ID = null } else { rowHDBC_MGW.THOIHAN_ID = this.thongso_tc.thoihan_id }

        if (this.thongso_tc.tocdo_id == 0) { rowHDBC_MGW.TOCDO_ID = null } else { rowHDBC_MGW.TOCDO_ID = this.thongso_tc.tocdo_id }

        if (this.thongso_tc.tocdo_pir_id == 0) { rowHDBC_MGW.TOCDO_PIR_ID = null } else { rowHDBC_MGW.TOCDO_PIR_ID = parseInt(this.thongso_tc.tocdo_pir_id) }

        if (this.thongso_tc.trangbi_id == 0) { rowHDBC_MGW.TRANGBI_ID = null } else { rowHDBC_MGW.TRANGBI_ID = this.thongso_tc.trangbi_id }
        if (this.thongso_tc.congnghe_id == 0) { rowHDBC_MGW.CONGNGHE_ID = null } else { rowHDBC_MGW.CONGNGHE_ID = this.thongso_tc.congnghe_id }
        if (this.thongso_tc.loaimd_id == 0) { rowHDBC_MGW.LOAIMD_ID = null } else { rowHDBC_MGW.LOAIMD_ID = this.thongso_tc.loaimd_id }
        if (this.thongso_tc.chuquanld_id == 0) { rowHDBC_MGW.CHUQUANLD_ID = null } else { rowHDBC_MGW.CHUQUANLD_ID = this.thongso_tc.chuquanld_id }
        
        if (parseInt(this.cboLoaihinhTB.SelectedValue) == LOAIHINH_THUEBAO.MGW_INTERNET_TRUCTIEP)
        {
            if (this.thongso_tc.tocdo_nix > 0)
              rowHDBC_MGW.TOCDO_NIX = this.thongso_tc.tocdo_nix
            else
              rowHDBC_MGW.TOCDO_NIX = null

            if (this.thongso_tc.tocdo_isp > 0)
              rowHDBC_MGW.TOCDO_ISP = this.thongso_tc.tocdo_isp
            else
              rowHDBC_MGW.TOCDO_ISP = null
        }
        else
        {
          rowHDBC_MGW.SL_IP = null
          rowHDBC_MGW.CUOC_IP = null
          rowHDBC_MGW.TOCDO_NIX = null
          rowHDBC_MGW.TOCDO_ISP = null
          rowHDBC_MGW.CUOC_NIX = null
          rowHDBC_MGW.CUOC_ISP = null
        }
      }

      rowHDBC_MGW.HDTB_ID = this.hdtb_id      
      this.dsHDBC_MGW.push(rowHDBC_MGW)
    },
    async TaoDuLieu_HDBC_TSL (themmoi, vdaucuoi_id) {      
      let tinh_id=0
      let thuebao_tc_id=0
      let tinh={}
      if (!this.kenh_trang){
          //region Kenh thuong          

          let dtTinh_id_tsl = await api.lay_tinhtc_id_tsl(this.txtMaTB.Text, this.cboDichVuVT.SelectedValue)
          tinh_id = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == vdaucuoi_id)["tinh_thicong"])          
          thuebao_tc_id = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == vdaucuoi_id)["thuebao_kn_id"])
          tinh = await api.tinh_thicong(tinh_id)


          let rowHDBC_TSL = {}          
          let dt_ban = await api.lay_ds_db_tsl_theo_tbid(this.thuebao_id)
          var input = {
            tinhthicong_id: tinh.tinhthicong_id, 
            thuebao_id: this.thuebao_id
          }
          let dt_tc= await api.lay_ds_db_tsl_theo_tbid_bc(input)

          if (dt_tc.length>0){
            for (let i=0;i< dt_tc.length;i++ )
            {
                rowHDBC_TSL = {}
                cmUlt.CopyRow(dt_tc[i], rowHDBC_TSL)
                rowHDBC_TSL.HDTB_ID = this.hdtb_id
                rowHDBC_TSL.MA_PL = this.txtMaHD.Text.trim()
                rowHDBC_TSL.TINH_DC_ID = vdaucuoi_id
                rowHDBC_TSL.TINH_ID = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == dt_tc[i].daucuoi_id)["tinh_thicong"])
                
                rowHDBC_TSL.NGAY_YCHT = this.tt_nd.ngay_cn
                rowHDBC_TSL.DIACHI_ID = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == vdaucuoi_id)["diachi_id"])
                
                this.dsHDBC_TSL.push(rowHDBC_TSL)
                
            }
          }
          //endregion
      }
      else
      {
          //region kenh trang
          let row_count = 0;
          row_count = this.dsHDTB_TSL.length
          var input = {
            tinhthicong_id: tinh.tinhthicong_id, 
            thuebao_id: this.thuebao_id
          }
          let dt_diachi= await api.lay_ds_db_tsl_theo_tbid_bc(input)
          
          for (let i = 0; i < row_count; i++)
          {
              let rowHDBCTSL = {}
              cmUlt.CopyRow(this.dsHDTB_TSL[i], rowHDBCTSL);
              if (vdaucuoi_id == 1)
              {
                  rowHDBCTSL.LOAIMD_ID = this.thongso_tc_dau.loaimd_id;

                  if (this.thongso_tc_dau.chuquan_id == 0)
                      rowHDBCTSL.CHUQUAN_ID = null
                  else
                      rowHDBCTSL.CHUQUAN_ID = this.thongso_tc_dau.chuquan_id

                  if (this.thongso_tc_dau.loaikenh_id == 0)
                      rowHDBCTSL.LOAIKENH_ID = null
                  else
                      rowHDBCTSL.LOAIKENH_ID = this.thongso_tc_dau.loaikenh_id

                  if (this.thongso_tc_dau.thietbidc_id == 0)
                      rowHDBCTSL.THIETBIDC_ID = null
                  else
                      rowHDBCTSL.THIETBIDC_ID = this.thongso_tc_dau.thietbidc_id

                  if (this.thongso_tc_dau.tocdo_id == 0)
                      rowHDBCTSL.TOCDO_ID = null
                  else
                      rowHDBCTSL.TOCDO_ID = this.thongso_tc_dau.tocdo_id

                  if (this.thongso_tc_dau.trangbi_id == 0)
                      rowHDBCTSL.TRANGBI_ID = null
                  else
                      rowHDBCTSL.TRANGBI_ID = this.thongso_tc_dau.trangbi_id

                  rowHDBCTSL.CHUQUANLD_ID = this.thongso_tc_dau.chuquanld_id
              }
              else
              {
                  rowHDBCTSL.LOAIMD_ID = this.thongso_tc_cuoi.loaimd_id

                  if (this.thongso_tc_cuoi.chuquan_id == 0)
                      rowHDBCTSL.CHUQUAN_ID  = null
                  else
                      rowHDBCTSL.CHUQUAN_ID = this.thongso_tc_cuoi.chuquan_id

                  if (this.thongso_tc_cuoi.loaikenh_id == 0)
                      rowHDBCTSL.LOAIKENH_ID = null
                  else
                      rowHDBCTSL.LOAIKENH_ID = this.thongso_tc_cuoi.loaikenh_id

                  if (this.thongso_tc_cuoi.thietbidc_id == 0)
                      rowHDBCTSL.THIETBIDC_ID = null
                  else
                      rowHDBCTSL.THIETBIDC_ID = this.thongso_tc_cuoi.thietbidc_id
                  

                  if (this.thongso_tc_cuoi.tocdo_id == 0)
                      rowHDBCTSL.TOCDO_ID = null
                  else
                      rowHDBCTSL.TOCDO_ID = this.thongso_tc_cuoi.tocdo_id

                  if (this.thongso_tc_cuoi.trangbi_id == 0)
                      rowHDBCTSL.TRANGBI_ID = null
                  else
                      rowHDBCTSL.TRANGBI_ID = this.thongso_tc_cuoi.trangbi_id

                  rowHDBCTSL.CHUQUANLD_ID = this.thongso_tc_cuoi.chuquanld_id
              }
              if (vdaucuoi_id == 1)
                  if (this.thongso_tc_dau.loaimd_id != 0)
                      rowHDBCTSL.LOAIMD_ID = this.thongso_tc_dau.loaimd_id
                  else
                      rowHDBCTSL.LOAIMD_ID = null
              else
                  if (this.thongso_tc_cuoi.loaimd_id != 0)
                      rowHDBCTSL.LOAIMD_ID = this.thongso_tc_cuoi.loaimd_id
                  else
                      rowHDBCTSL.LOAIMD_ID = null

              rowHDBCTSL.DIACHI_ID = parseInt(dt_diachi.find(r => r["daucuoi_id"] == vdaucuoi_id)["diachi_id"])
              //Convert.ToInt64(dt_diachi.Select("daucuoi_id = " + vdaucuoi_id)[0]["diachi_id"].ToString());


              rowHDBCTSL.HDTB_ID = this.hdtb_id
              rowHDBCTSL.TINH_DC_ID = vdaucuoi_id
              if (rowHDBCTSL.DAUCUOI_ID == 1)
                  rowHDBCTSL.TINH_ID = this.tinh_dau.tinhthicong_id
              else
                  rowHDBCTSL.TINH_ID = this.tinh_cuoi.tinhthicong_id

              this.dsHDBC_TSL.push(rowHDBCTSL)              
          }
          
      }
    },

    //end bán chéo
    chkCTVChange () {
      if (this.chkCTV.Checked) {        
        this.$refs.dlgNguoiGioiThieu.show(true, 'ctv')
      } else {
        this.txtCTV.Text = ''
      }
    },
    chkNguoiGTChange () {
      if (this.chkNguoiGT.Checked) {        
        this.$refs.dlgNguoiGioiThieu.show(true, 'gt')
      } else {
        this.txtNguoiGT.Text = ''
      }
    },
    async cboDaiVT_ADSL_SelectedValueChanged () {
      if (this.cboDaiVT_ADSL.Items.length > 0) {
        if (!this.cboDaiVT_ADSL.SelectedValue) return
        let daivt_id = parseInt(this.cboDaiVT_ADSL.SelectedValue)
        var rs = await api.lay_ds_donvi_theo_loaidv(daivt_id, LOAI_DONVI.TRAM_VT)
        this.cboVeTinh_Adsl.Items = rs
        if (rs && rs.length){          
          this.cboVeTinh_Adsl.SelectedValue = parseInt(rs[0].donvi_id)
        }
      }
    },
    async cboDVQL_MGW_SelectedValueChanged () {
      if (this.cboDVQL_MGW.Items.length > 0) {
        if (!this.cboDVQL_MGW.SelectedValue) return
        var daivtID = parseInt(this.cboDVQL_MGW.SelectedValue)
        var rs = await api.lay_ds_donvi_theo_loaidv(daivtID, LOAI_DONVI.TRAM_VT)
        this.cboTram_MGW.Items = rs
      }
    },
    async cboDaiVT_CD_SelectedValueChanged () {
      if (this.cboDaiVT_CD.Items.length > 0) {
        if (!this.cboDaiVT_CD.SelectedValue) return
        let daivtID = parseInt(this.cboDaiVT_CD.SelectedValue)
        var rs = await api.lay_ds_donvi_theo_loaidv(daivtID, LOAI_DONVI.TRAM_VT)
        this.cboTram_CD.Items = rs
      }
    },
    async cboDaiVT_IMS_SelectedValueChanged () {
      if (this.cboDaiVT_IMS.Items.length > 0) {
        if (!this.cboDaiVT_IMS.SelectedValue) return
        var daivtID = parseInt(this.cboDaiVT_IMS.SelectedValue)
        var rs = await api.lay_ds_donvi_theo_loaidv(daivtID, LOAI_DONVI.TRAM_VT)
        this.cboTram_IMS.Items = rs
      }
    },
    cboDichVuVT_SelectedValueChanged () {
      console.log('cboDichVuVT_change', this.cboDichVuVT.SelectedValue)
      if (this.cboDichVuVT.Items.length > 0) {
        if (!this.cboDichVuVT.SelectedValue) return
        this.dichvuvt_id = parseInt(this.cboDichVuVT.SelectedValue)

        this.cboLoaihinhTB.Items = this.listSubscriptionTypeData.filter(el => el.DICHVUVT_ID === +this.cboDichVuVT.SelectedValue)

        
        if (this.cboLoaihinhTB.Items && this.cboLoaihinhTB.Items.length>0) {
          this.cboLoaihinhTB.SelectedValue = parseInt(this.cboLoaihinhTB.Items[0].LOAITB_ID)
          this.cboLoaihinhTB_SelectedValueChanged()
        }
        if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) { this.cboLoaihinhTB.SelectedValue = LOAIHINH_THUEBAO.INTERNET_ADSL }
        // đã check sẵn trong code
        //this.GiaoDien_DichVuVT(this.dichvuvt_id)

        if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) {
          this.btnKiemTraAccount.Visible = true
          this.btnThongTinTC.Enabled = true
          this.txtTTCuoc.Enabled = true
        } else {
          this.btnKiemTraAccount.Visible = false
          this.btnThongTinTC.Enabled = false
          this.txtTTCuoc.Enabled = false
        }
        if (this.dichvuvt_id == DICHVU_VIENTHONG.TSL || this.dichvuvt_id == 25) {
            this.btnDiemCuoi.Visible = false
            this.btnDiemDau.Visible = false
            //label22.Visible = false;  CHECK LẠI LABEL
        }
        else {
            this.btnDiemCuoi.Visible = true;
            this.btnDiemDau.Visible = true;
            //label22.Visible = true;  CHECK LẠI LABEL
        }

        
        this.LayTienTheoKhoanMuc()
      }
    },
    async cboLoaihinhTB_SelectedValueChanged (e) {
      try {
        if (this.cboLoaihinhTB.Items.length > 0) {
          if (!this.cboLoaihinhTB.SelectedValue) return

          var loaitbID = parseInt(this.cboLoaihinhTB.SelectedValue)
          var ds = await api.sp_tt_tocdo_adsl({ "param": loaitbID,"type": 2})
          // api.lay_dulieu_theo_dieukien("tocdo_adsl", "tocdo_id", "thuonghieu as toc_do", "loaitb_id = " + loaitbID, "iptinh").then(rs => {
          //   this.cboTocDo_Adsl.Items = rs.map(r => ({ ...r, tocdo_id: parseInt(r.tocdo_id) }))
          // })          
          if (ds && ds.length>0){
            this.cboTocDo_Adsl.Items = ds.map(r => ({ ...r, tocdo_id: parseInt(r.tocdo_id),toc_do:r.thuonghieu }))
          }
          // DataTable dtKieuLD = new DataTable()
          // let dichvu_vt = parseInt(this.cboDichVuVT.SelectedValue)
          let data = {
            "p_loaitb_id": this.cboLoaihinhTB.SelectedValue,
            "p_loaihd_id": LOAI_HOPDONG.KHOIPHUC_SD,
            "p_kieu": 2
          }
          
          this.cboKieuLD.Items = await api.sp_lay_ds_kieu_ld_bc(data)
          // this.cboKieuLD.Items.unshift({ten_kieuld:'Chọn kiểu lắp đặt',kieuld_id:0})
          // console.log('this.cboKieuLD.Items')
          // console.log(this.cboKieuLD.Items)
          // code vô hiệu trên C#
          // if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL)
          // {
          //     if (loaitbID == LOAIHINH_THUEBAO.INTERNET_MYTV)
          //         btnKiemTraAccount.Enabled = false
          //     else
          //         btnKiemTraAccount.Enabled = true
          // }
          this.LAY_DS_DVGT_MACDINH(loaitbID)
        }
      } catch (ex) {
        console.log(ex)
        this.$toast.error("Có lỗi")
      }
    },
    async cboKieuLD_SelectedValueChanged () {      
      
      if (this.cboKieuLD.Items.length > 0){
          if (!this.cboKieuLD.SelectedValue || this.cboKieuLD.SelectedValue == 0) return          

          this.txtMaTN_ADSL.Text = ""
          var kieuld = parseInt(this.cboKieuLD.SelectedValue)
          //Kiểm tra nếu tất cả các kiểu lắp đặt trên đường có sẵn || lắp kèm thì hiện button btnMaTN + txtMaTN          
          var kieu = await api.fn_tt_kieu_ld({param:kieuld,type:1})// attt  api.map _id("kieu", "css.kieu_ld", "where kieuld_id =" + kieuld)
          if (kieu == 1 || kieu == 2)
          {
              this.btnMaTN_ADSL.Enabled = true;
              this.txtMaTN_ADSL.Enabled = true;
          }
          else //Neu ko so thi clear o text ma truy nhap
          {
              this.btnMaTN_ADSL.Enabled = false;
              this.txtMaTN_ADSL.Enabled = false;
              if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL)
                  this.txtMaTN_ADSL.Text = "";
          }
          
          if (this.tinh.tinhthicong_id > 0 && this.Kieuld_click)
          {
              
              //region Hien thi form thong so de chon ben ban va ben thi cong
              var loaitb_id = parseInt(this.cboLoaihinhTB.SelectedValue)
              var dichvu_vt = parseInt(this.cboDichVuVT.SelectedValue)

              if ((dichvu_vt == DICHVU_VIENTHONG.CO_DINH || dichvu_vt == DICHVU_VIENTHONG.IMS) && this.Kieuld_click)
              {                  
                  this.$refs.frmThongSoCoDinh.openDialog(this.thongso_ban, this.thongso_tc)
              }

              if (dichvu_vt == DICHVU_VIENTHONG.ADSL && this.Kieuld_click)
              {                  
                  await this.$refs.TocDoMucCuocDialog.$children[0].clearData()
                  await this.$refs.TocDoMucCuocDialog.$children[0].loadData()
                  this.$refs.TocDoMucCuocDialog.show()
                  
              }
              if ((dichvu_vt == DICHVU_VIENTHONG.MEGAWAN || dichvu_vt == DICHVU_VIENTHONG.METRONET || dichvu_vt == 26) && this.Kieuld_click)
              {
                console.log('this.thongso_ban, this.thongso_tc', this.thongso_ban, this.thongso_tc)
                  //TODO: CHỜ POPUP MEGAWAN
                  this.$refs.ref_frmThongSoWan.ThongsoBan = this.thongso_ban
                  this.$refs.ref_frmThongSoWan.ThongsoTc = this.thongso_tc
                  this.$refs.ref_frmThongSoWan.khachhang_id = this.khachhang_id
                  this.$refs.ref_frmThongSoWan.hdkh_id = this.hdkh_id
                  await this.$refs.ref_frmThongSoWan.openDialog1(this.tinh, this.loaitb_id, this.loaitb_id, LOAI_HOPDONG.KHOIPHUC_SD, this.dichvuvt_id, this.thongso_ban, this.thongso_tc)
                  
              }
              
              this.NapThongTinDichVuThueBao()
          }
        this.Kieuld_click = false

      }
    },   
    async cboKieuLD_Click(){
      console.log('cboKieuLD_Click')
    },
    CloseHienThiChonThongSoMegaWan(data) {
      //this.Capnhatthongso = data.Capnhat
      if (data.Capnhat) {
        this.thongso_ban = data.thongso_ban ? data.thongso_ban : data.ThongsoBan ? data.ThongsoBan : []
        this.thongso_tc = data.thongso_tc ? data.thongso_tc : data.ThongsoTc ? data.ThongsoTc : []
      }
    },
    capnhat_TSCD(tscd) {
      this.thongso_ban = tscd.thongso_Ban
      this.thongso_tc = tscd.thongso_TC
      if (this.dichvu_vt == DICHVU_VIENTHONG.CO_DINH)
        this.cboMucCuocTB_CD.SelectedValue = this.thongso_ban.muccuoctb_id
      else
        this.cboMucCuocTB_IMS.SelectedValue = this.thongso_ban.muccuoctb_id
    },
    acceptTocDoMucCuoc (data) {
      this.$refs.TocDoMucCuocDialog.hide()
      console.log(data)      
      this.thongso_ban = {...data.thongso_ban}
      this.thongso_tc = {...data.thongso_tc}
      if (this.thongso_ban.muccuoctb_id !=null &&this.thongso_ban.muccuoctb_id != 0)
        this.cboMucCuocTB_ADSL.SelectedValue = this.thongso_ban.muccuoctb_id
      
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
    cboKieuLD_Click(){
      this.Kieuld_click = true
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
        data.hdtb_id = this.hdtb_id
      } else {
        data.thuebao_id = this.thuebao_id
      }
      console.log(data)

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
          return
        }

        if (ds_tb.filter(r => r.status == 5).length > 0) {
          this.$toast.warning("Hợp đồng đã có phiếu kích hoạt visa. Bạn không thể xóa hợp đồng !")
          return
        }

        if (this.dgvDanhSach.Rows.length <= 0) {
          this.$toast.warning("Không có thông tin trên lưới. Bạn không thể xóa !")
          return
        }


        var rs = await api.xoa_hdkh_v2(this.hdkh_id, LOAI_HOPDONG.KHOIPHUC_SD)
        if (rs.message == "Success") {
          this.$toast.success('Xoa hợp đồng thành công !')
          this.setButton(0)
        } else {
          this.$toast.error("Có lổi xảy ra:\n" + rs.message)
        }
        // end hoàng
      } catch (error) {
        this.$toast.error(error)
      }

      this.loading(false)
    },
    async btnThemTB_Click () {
      try {
        this.$toast.error("Không sử dụng chức năng này!")
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

        
        // CheckDataFacade checkdata = new CheckDataFacade()
        // int kieu = Convert.ToInt32(checkdata.MAP _ID("kieu", DatabaseConstants.DB2 + ".kieu_ld", "where kieuld_id =" + cboKieuLD.SelectedValue))
        // if ((kieu == 1 || kieu == 2) && txtMaTN_ADSL.Text.Trim().Length == 0)
        // {
        //     Message_Box.ShowWarning("Khi kiểu lắp đặt trên đường có sẵn, mã truy nhập không được trống!")
        //     txtMaTN_ADSL.Focus()
        //     return
        // }

        // this.loading(true)

        // var dshg = await api.layhuonggiao_tiepnhan_v2(LOAI_HOPDONG.KHOIPHUC_SD, this.loaitb_id, this.tocdo_id, this.muccuoc_id, parseInt(this.cboKieuLD.SelectedValue), this.vloaikenh_id, parseInt(this.cboDichVuVT.SelectedValue))
        // let huonggiao_tn = 0
        // this.quytrinh_id = 0
        // if (dshg.length <= 0) {
        //   this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!")
        //   this.loading(false)
        //   return
        // }

        // huonggiao_tn = parseInt(dshg[0]["huonggiao_id"])
        // this.quytrinh_id = parseInt(dshg[0]["quytrinh_id"])

        // await this.TaoDuLieu_HDTB(true)

        // this.dsHDTB_ADSL = []        
        // this.dsHDTB_CD = []
        // this.dsHDTB_GP = []
        // this.dsHDTB_IMS = []
        // this.dsHDTB_MGW = []
        // this.dsHDTB_TSL = []
        // this.dsHDTBDV = []

        // await this.TaoDuLieu_HDTBDV(true)

        // switch (this.dichvuvt_id) {
        //   case DICHVU_VIENTHONG.CO_DINH:
        //     await this.TaoDuLieu_HDTB_CD(true)
        //     break
        //   case DICHVU_VIENTHONG.IMS:
        //     await this.TaoDuLieu_HDTB_IMS(true)
        //     break
        //   case DICHVU_VIENTHONG.ADSL:
        //     await this.TaoDuLieu_HDTB_ADSL(true)
        //     break
        //   case DICHVU_VIENTHONG.GPHONE:
        //     await this.TaoDuLieu_HDTB_GP(true)
        //     break
        //   case DICHVU_VIENTHONG.MEGAWAN:
        //   case DICHVU_VIENTHONG.METRONET:
        //     await this.TaoDuLieu_HDTB_MGWAN(true)
        //     break
        //   case DICHVU_VIENTHONG.TSL:
        //     await this.TaoDuLieu_HDTB_TSL(true)
        //     break
        //   default:
        //     break
        // }
        // await this.TaoDuLieu_CT_TIENHD(true)
        // await this.TaoDuLieu_DKDV()
        // this.CapNhat_HDTB_ID_DangKyDV()


        // var rs = await api.themmoi_hopdongtb_khoiphucthanhly_v2(this.dsHDTB, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsDK_DVGT, this.dsDK_DVKHAC, this.dsCTTHD, this.dsHDTBDV, huonggiao_tn, this.dsHDTB_IMS, this.dsHDTB_MGW, this.dsHDTB_TSL)

        // // if (rs.message != "Success" && rs.message != "Thành Công") {
        // //   this.$toast.error(rs.message)
        // //   this.loading(false)
        // //   return
        // // }
        // if (this.tinh.tinhthicong_id > 0) {
        //     this.TaoDuLieuBC(true)        
        //     await api.sp_themmoi_hdbancheo_biendong_v3(this.dsHDBC, this.dsHDTBBC,this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDKBCDVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsLYDOHUYBC, this.dsDiaChi_BanCheo)
        // }

        
        // let statusVdc= true

        // if (parseInt(this.cboDichVuVT.SelectedValue) == DICHVU_VIENTHONG.ADSL && statusVdc == true) {
        //   // Gọi hàm cập nhật trạng thái HDTB = 1: Đã đặt chỗ thành công trên VDC
        //   // obj.CAPNHAT_STATUS_HDTB(hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
        //   await api.capnhat_status_hdtb(this.hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
        // }        

        // this.hienThiDanhSacHDTB(await api.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id))

        // this.loading(false)
      } catch (error) {
        console.log(error)
        this.$toast.error(error)
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

        var id = await api.fn_tt_hd_thuebao({param:this.hdtb_id,type:2})// attt  api.map _id("tthd_id", "hd_thuebao", "where hdtb_id = " + this.hdtb_id)
        var i_tthd_id = parseInt(id)
        if (i_tthd_id != TRANGTHAI_HOPDONG.MOI) {
          this.$toast.error("Trạng thái hợp đồng không phải mới tiếp nhận. Bạn hãy kiểm tra lại !")
          this.loading(false)
          return
        }

        var rs = await api.xoa_hdtb(this.hdtb_id)

        if (rs.message != "Success" && rs.message != "Thành Công") {
          this.$toast.error(rs.message)
          this.loading(false)
          return
        }

        this.loading(false)
        this.$toast.success("Xóa thuê bao thành công !")

        this.hienThiDanhSacHDTB(await api.lay_ds_hopdong_thuebao_theo_hdkh(this.hdkh_id))
      } catch (error) {
        this.$toast.error(error)
        this.loading(false)
      }
    },
    async btnGiaoPhieu_Click (){
      console.log('btnGiaoPhieu_Click')
      if (!this.hdtb_id || this.hdtb_id ==0) {
          this.$toast.error("Bạn chưa chọn thuê bao!");  
          return          
        }
      try {
        // this.quytrinh_tc_tn = 0;
        //       this.huonggiao_tc_tn = 0;
        var rsQTrinh = await api.fn_tt_hd_thuebao({param:parseInt(this.hdtb_id),type:3})// attt api.map  _id('quytrinh_id', 'css.hd_thuebao', 'where hdtb_id = ' + parseInt(this.hdtb_id))
        var quytrinh_id= parseInt(rsQTrinh)
        var input = {
          'quytrinh_id':quytrinh_id, 
          'thutu_id':2
        }
        var ds_HGiao = await api.fn_lay_huonggiao_quytrinh(input)
        let huonggiao_id = ds_HGiao?parseInt(ds_HGiao):0
        var inputhg = {
          "dichvuvt_id": this.dichvuvt_id,
          "loaihd_id": 30,
          "metaClass": {'abc':1},
          "nhom_qt_id": 0,
          "quyen_huonggiao": "0",
          "quytrinh_id": quytrinh_id
        }
        var ds_DanhsachHGiao = await api.lay_ds_huonggiao(inputhg)
        
        if (quytrinh_id) {
          
          await this.$router.push({
            path: '/contract/HandoverTicket',
            query: {
              quytrinh:quytrinh_id,
              huonggiao: huonggiao_id,
              magd: this.txtMaGD.Text.trim(),
              madv:this.dichvuvt_id,
              loaihd:LOAI_HOPDONG.KHOIPHUC_SD,
            }
          })
        } 
      } catch (error) {
        console.log(error)
        this.$toast.error("Có lỗi xảy ra");
      }
    },
    async dgvDanhSach_SelectionChanged (e) {
      this.loading(true)
      var rows = this.$refs.lvwThueBao.getSelectedRecords()
      if (rows.length <= 0) return
      try {
        var row = rows[0]
        this.hdtb_id = parseInt(row["hdtb_id"])        
        var ds = await api.LAY_DS_HOPDONG_TB_THEO_HDTB_ID_BC(this.hdtb_id)  
        this.hienThiTTHopDongTB(ds)
      } catch (error) {
        this.$toast.error(error)
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    btnTienKM_Click () {
      console.log('btnTienKM_Click')
      console.log(this.ds_tien_khoanmuc)
      this.$refs.dlgTienKhoanMuc.show(true, {
        ds: this.ds_tien_khoanmuc,
        cols: [
          { fieldName: 'tentat_kmtt', headerText: 'Khoản mục', isPrimaryKey: true, visible: false },
          {fieldName: 'tongtien', textAlign:'right', allowEditing:true, editType: 'numericedit', format:'N', customAttributes:{class: 'tongtien'},  headerText: 'Tổng tiền', allowFiltering: false, allowSorting: false, edit:"tongtienParam"},
          { fieldName: 'tien', headerText: 'Tiền', allowEditing: true, editType: 'numericedit', format:'N', customAttributes:{class: 'tongtien'},  headerText: 'Tổng tiền', allowFiltering: false, allowSorting: false, edit:"tienParam"},
          { fieldName: 'vat', headerText: 'VAT', allowEditing: true, editType: 'numericedit' },
          {fieldName: 'tyle_vat', textAlign:'right', format:'N', headerText: 'Tỷ lệ vat', allowFiltering: false, allowSorting: false},
        ],
        tieude: 'Chi tiết tiền theo khoản mục',
        tieudebang: 'Chi tiết tiền theo khoản mục'
      })
    },
    dlgTienKM_Close (data) {
      console.log('close tien KM', data)
      this.ds_tien_khoanmuc = data.map(r => ({ ...r, khoanmuctt_id: parseInt(r.khoanmuctt_id) }))
      if (this.ds_tien_khoanmuc.length > 0) {
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
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
      //bán chéo
      this.$refs.frmDangKyDVGTBC.openDialog()
    },
    chapnhan_DangKyDVGTBC(dsDK_DVGT) {
        console.log('dsDK_DVGT')
        console.log(dsDK_DVGT)
        this.m_dsThueBao_DichVu = dsDK_DVGT
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

      let cuoc_tb = await api.fn_tt_muccuoc_tb({param:parseInt(this.cboMucCuocTB_ADSL.SelectedValue),type:1})// attt:api.map _id("cuoc_tb", "muccuoc_tb", "where mucuoctb_id = " + this.cboMucCuocTB_ADSL.SelectedValue)
      let cuockhoan = await api.fn_tt_muccuoc_tb({param:parseInt(this.cboMucCuocTB_ADSL.SelectedValue),type:2})// attt: api.map _id("cuockhoan", "muccuoc_tb", "where mucuoctb_id = " + this.cboMucCuocTB_ADSL.SelectedValue)
      let cuoc_tg = await api.fn_tt_muccuoc_tb({param:parseInt(this.cboMucCuocTB_ADSL.SelectedValue),type:3})// attt: api.map _id("cuoc_tg", "muccuoc_tb", "where mucuoctb_id = " + this.cboMucCuocTB_ADSL.SelectedValue)
      let cuoc_ll = await api.fn_tt_muccuoc_tb({param:parseInt(this.cboMucCuocTB_ADSL.SelectedValue),type:4})// attt: api.map _id("cuoc_ll", "muccuoc_tb", "where mucuoctb_id = " + this.cboMucCuocTB_ADSL.SelectedValue)
      if (cuoc_tb == "-1") cuoc_tb = "0"
      if (cuockhoan == "-1") cuockhoan = "0"
      if (cuoc_tg == "-1") cuoc_tg = "0"
      if (cuoc_ll == "-1") cuoc_ll = "0"

      var data = {}
      data.loaitb_id = parseInt(this.cboLoaihinhTB.SelectedValue)
      data.thongtintc = this.txtTTCuoc.Text
      data.cuoc_tb = cuoc_tb
      data.cuoc_tg = cuoc_tg
      data.cuockhoan = cuockhoan
      data.cuoc_ll = cuoc_ll

      this.$refs.dlgTinhCuoc.show(true, data)
    },
    dlgKhuVucChapNhan (data) {
      console.log(data)
      this.khuvuc_id = data.khuvuc_id
      this.dsHDTB_KV = {
        HDTB_KV: data.hdtb_kv_data
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
      if (data.source == 'gt') {
        this.txtNguoiGT.Text = data.data.ten_ht
        this.ctv_id = data.data.ctv_id
        this.loainv_ctv_id = data.data.loainv_id
        this.donvi_ctv_id = data.data.donvi_id
      } else if (data.source == 'ctv') {
        this.txtCTV.Text = data.data.ten_ht
        this.nguoigt_id = data.data.ctv_id
        this.loainv_nguoi_gt_id = data.data.loainv_id
        this.donvi_nguoi_gt_id = data.data.donvi_id
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
        kieuld_id: parseInt(this.cboKieuLD.SelectedValue),
        loaitb_id: parseInt(this.cboLoaihinhTB.SelectedValue),
        dichvuvt_id: parseInt(this.cboDichVuVT.SelectedValue)
      })
    },
    dlgMaTN_Close (data) {
      this.txtMaTN_ADSL.Text = data.ma_tn
      this.txtMaDoiCap_ADSL.Text = data.ma_doicap
    },
    btnThanhToan_Click () {
      // this.$refs.dlgPayment.show(true, this.txtMaGD.Text)
      this.$router.push({ name: 'Payment', params: {"magd": this.txtMaGD.Text} })
    },
    btnDiemDau_Click () {
      if (this.tinh_dau.tinhthicong_id == 0)
      {
          this.$toast.error("Chưa có thuê bao")
          return;
      }
      console.log('this.tinh_dau')
      console.log(this.tinh_dau)
      let i_loaitb_id = Number(this.cboLoaihinhTB.SelectedValue)
      this.$refs.frmThongSoTSLDau.openDialog(this.tinh_dau, i_loaitb_id, i_loaitb_id, parseInt(this.cboKieuLD.SelectedValue),
                this.thongso_ban, this.thongso_tc_dau, LOAI_HOPDONG.KHOIPHUC_SD, this.kenh_trang);      
    },
    frmThongSoTSLDau_onChapNhan(frm) {
        this.thongso_ban = frm.thongso_Ban
        this.thongso_tc_dau = frm.thongso_TC        
    },
    btnDiemCuoi_Click () {
      if (this.tinh_cuoi.tinhthicong_id == 0)
      {
          this.$toast.error("Chưa có thuê bao")          
          return;
      }
      let i_loaitb_id = Number(this.cboLoaihinhTB.SelectedValue)
        this.$refs.frmThongSoTSLCuoi.openDialog(this.tinh_cuoi, i_loaitb_id, i_loaitb_id, parseInt(this.cboKieuLD.SelectedValue),
                this.thongso_ban, this.thongso_tc_cuoi, LOAI_HOPDONG.KHOIPHUC_SD, this.kenh_trang);
      
    },
    frmThongSoTSLCuoi_onChapNhan(frm) {
        this.thongso_ban = frm.thongso_Ban;
        this.thongso_tc_cuoi = frm.thongso_TC;        
    },
    CopyRow(row) {
        let entries = Object.entries(row);
        let capsEntries = entries.map((entry) => [entry[0].toUpperCase(), entry[1]]);
        return Object.fromEntries(capsEntries);
    },
    async getKey(keyname) {
      let key;
      try {
        await api.getKey(this.axios, {
              keyName : keyname,
              numBlockSize : 1,
              numRetry:  10
            }).then((res) => {         
              if (res.data.error_code == "BSS-00000000") {
                key = res.data.data;
              }
            });
      } catch(e) {
        console.log(e)
      }
      return key;
    },
    LowerCasePropertyList(obj) {
      return obj.map(function(item) {
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
      return obj.map(function(item) {
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
