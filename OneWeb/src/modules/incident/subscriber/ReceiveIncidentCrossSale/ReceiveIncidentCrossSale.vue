<template src="./ReceiveIncidentCrossSale.html"></template>
<script>
import breadcrumb from "@/components/breadcrumb";
import '@/assets/vendor/jquery/split'
import ThongTinTiepNhan from './ThongTinTiepNhan';
import ThongSoKyThuat from "./ThongSoKyThuat";
import DanhSachTiepNhan from './DanhSachTiepNhan';
import LichSuBaoHong from './LichSuBaoHong';
import TinhTrangHong from './TinhTrangHong';
import KetQuaXuLy from "./KetQuaXuLy";
import DanhSachBaoHong from './DanhSachBaoHong';
import FormTraCuuTB from './popups/frmTraCuuThueBao';
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import frmXemThongBaoSuCo from './popups/frmXemThongBaoSuCo';
import frmCatChuyenBH from './popups/frmCatChuyenBH';
import frmPopUpTiepNhan from './popups/frmPopUpTiepNhan';
import frmTT_Port_TB_CungDoiCap from './popups/frmTT_Port_TB_CungDoiCap';
import frmHtktBH from './popups/frmHtktBH';
import TraCuuVatTuThueBao from '@/modules/QLVT/components/TraCuuVatTuThueBao/TraCuuVatTuThueBao.vue'
import CheckPort from '@/modules/search/subscriber/CheckPort';
import daunoithuebao from "../../../CABMAN/tracuu-thuebao-theo-cap/tracuu-dandocap-theothuebao"
import frmTraCuuMDF from '../HandleAcceptanceIncidentReports/Popups/TraCuuMDF/TraCuuMDF.vue';
import frmTiepNhan_PHKH from '@/modules/complaint/ReceiveCustomerFeedback/ReceiveCustomerFeedback.vue'
import frmThayDoiTTBH from "../SearchIncidentUpdateAppointment/components/frmThayDoiTTBH";
import frmPhanAnhBH from "../SearchIncidentUpdateAppointment/components/frmPhanAnhBH";
import frmBNMTools from "../../../search/subscriber/frmBNMTools";
import frmChonTinh from "./popups/frmChonTinh"
import Utils from './utils';

import API from "./API";
import {
  DichVuVienThong,
  DS_KHONG_XULY,
  KENHTN_BAOHONG,
  LOAI_DV,
  LoaiHinhTB,
  LOAIHONG,
  NHOM_TT,
  NHOMTON,
  TRANGTHAI_BH,
  TrangThaiTB
} from "../../../../const/enums";
import moment from "moment";
import {TRANGTHAI_PH} from "../../../../utils/Enum";

export default {
  components: {
    breadcrumb,
    ThongTinTiepNhan,
    ThongSoKyThuat,
    DanhSachTiepNhan,
    LichSuBaoHong,
    TinhTrangHong,
    KetQuaXuLy,
    DanhSachBaoHong,
    FormTraCuuTB,
    SearchAccount,
    frmXemThongBaoSuCo,
    frmCatChuyenBH,
    frmPopUpTiepNhan,
    TraCuuVatTuThueBao,
    frmTT_Port_TB_CungDoiCap,
    frmHtktBH,
    CheckPort,
    daunoithuebao,
    frmTraCuuMDF,
    frmTiepNhan_PHKH,
    frmThayDoiTTBH,
    frmPhanAnhBH,
    frmBNMTools,
    frmChonTinh
  },
  created() {
    window.addEventListener('keyup', (e) => {
      if (e.key === 'F4') {
        this.loading(true);
        this.HienThi_Danhsach_Tiepnhan();
        this.HienThi_DanhSach_Tb_BaoHong();
        this.loading(false);
      } else if (e.key === 'F5') {
        this.btnNhapMoi_Click();
      } else if (e.key === 'F6') {
        this.tsbtnChoTiepNhan_Click();
      } else if (e.key === 'F8') {
        this.tsbtnCapNhatKQ_Click();
      } else if (e.key === 'F9') {
        this.btnGhiLai_Click();
      }
    });

    this.xuLyTag();
  },
  async mounted() {
    if (this.allowLoadForm) {
      await this.frmLoad();
    }

    Split(['#boxLeft1', '#boxRight1'], {
      sizes: [50, 50],
    })
    Split(['#boxLeft2', '#boxRight2'], {
      sizes: [50, 50],
    })
    Split(['#boxLeft3', '#boxRight3'], {
      sizes: [50, 50],
    });
    if (this.i_maTB) {
      this.tttn.ma_tb = this.i_maTB;
      await this.onEnterMaTB();
    }

    API.thongtin_nguoidung2(this.axios).then(rs => {
      this.userInfo = rs.data.data;
    })
  },
  data() {
    return {
      phanVung: {
        HNI: 21,
        HCM: 28
      },
      header: {
        title: "TIẾP NHẬN BÁO HỎNG",
        list: [
          {name: "Báo hỏng", link: {name: "Ui.cards"}},
          {name: "Tiếp nhận báo hỏng", link: {name: "Ui.buttons"}},
        ],
      },
      options: {
        dichvu: [],
        dichvutk: [],
        loaihinh: [],
        loaihinhtk: [],
        nhomton: [],
        nhomnn: [],
        nguoigv: [],
        nhomtt: [],//nhóm tình trạng
        ttvt: [],
        hangsx: [],//hang san xuat
        phanloaibh: [],//phan loai bao hong,
        tinhtrangbh: [],
        nguyennhan: [],
        lydoton: [],
        donViPhoiHop: []
      },
      tttn: {
        ma_tb: '',
        ten_tb: '',
        diachi_ld: '',
        diachi_tb: '',
        dactrung: '',
        loaihinh_id: 0,
        dichvuvt_id: 0,
        so_dt: '',
        trangthai_tb: '',
        thuebao_id: 0,
        ngay_bh: '',
        tg_cktb: '',
        cuoc_ps: '',
        ipcc: '',
        nguoi_bh: '',
        dien_thoai: '',
        ngay_hentl_tu: '',
        ngay_hentl_den: '',
        checkHenTL: false,
        enableDichVu: false,
        enableLoaiHinh: false,
        enableNgayBH: false,
        lblCanhBao: ''
      },
      kqxl: {
        cboKQXL: 0,
        ngayXN: '',
        cboNhomNN: 0,
        cboNguyenNhan: [],
        cboNhomTon: 0,
        cboLyDoTon: [],
        txtGhiChu: '',
        enableNN: true,
        enableTon: false,
        cboHangSX: 0,
        enableHangSX: false
      },
      ttHong: {
        chkPhieuDaChuyenTinh: false,
        chkPhieutra: false,
        chkPhieuChoXL: false,
        chkPhieuDaSua: false,
        chkCamKet: false,
        cboNguoiTH: 0,
        chkNgayTH: false,
        ngayTH: '',
        cboNhomTT: 0,
        cboTinhTrang: [],
        txtGhiChu: '',
        chkCKCL_Visible: false,
        cboPhanLoaiBH: []
      },
      tskt: [],
      dsbh: {
        chkDichVu: false,
        cboDichVu: -1,
        chkLoaiHinh: false,
        cboLoaiHinh: -1,
        chkTTVT: false,
        cboTTVT: [],
        chkDonViPhoiHop: false,
        cboDonViPhoiHop: []
      },
      dsTiepNhan: [],
      lsBaoHong: [],
      dsBaoHong: [],
      dsBaoHong_Full: [],
      dsThueBao: [],
      tag: 0,
      tag_BH_AO: 0,
      tag_TuDong_LayTT: 0,
      btnVisible: {
        tsbtnPHKH: true,
        tsbtnKhoaPhieu: true,
        tsbtnChuyenNET: true,
        tsbtnChuyenMedia: true,
        tsbtnChuyenIT360: true,
        tsbtnChuyenDHIT: true,
        tsbtnChuyenVip: true,
        tsbtnChuyenOMCData: true,
        tsbtnLayPhieu: false,//false
        tsbtnBoQua: false,//false
        tsbtnGhiLai: true,//true
        tsbtnTiepNhan: true,//true
        tsbtnBaoHongAo: true,//true
        tsbtnNhapMoi: true,//true
        btnLayTTMoi: true,//true
        tsbtnCapNhatDoThu: true,
        tsmGiuPhieu: false,
        tsmHuyGiuPHieu: false,
        tsbtnGiaoPhieu: true,
        tsbtnXoaPhieu: true,
        tsbtnHTKT: true
      },
      idPhanVungHNI: 21,
      HIENTHI_PHANANH_NHANHONG: -1,
      Nhap_ghichu: false,
      GIOIHAN_NGUYENNHAN_BAOHONG: -1,
      TIMER_TIMKIEM_TIEPNHAN_BH: -1,
      THONGBAO_SUCO_TTVT: -1,
      HT_CHITIEU_TG_KHI_NHANHONG: -1,
      KHONG_BAOHONG_PHANHOI_DONGTHOI: -1,
      TIEPNHAN_BH_LINK_PHANHOI_KH: -1,
      TRACUU_BAOHONG_THONGTINKH: -1,
      CAPNHAT_TT_LIENHE_BH_NHANHONG: -1,
      LAY_HUONGGIAO_BH_THEO_TINHTRANG: -1,
      CHOPHEP_GIUPHIEU: -1,
      KHONGGIAO_HTKT_BAOHONG: -1,
      KHACHHANG_YC_KO_CHUYEN_DH: -1,
      BAOHONG_LAY_TINHTRANG_THEO_LHTB: -1,
      KHONGCHAN_NHANHONG: -1,
      NHANHONG_TB_CHUAHT_HOPDONG: -1,
      kt_dambao_ckcl_kh: false,
      kt_kenhtn_baohong: false,
      lblSuCoTTVT: '',
      vkyhoadon: '',
      vuser_alt: '',
      vpass_alt: '',
      dsThamSo: [],
      // dsThamSoQuanTri: [],
      kp: '-1',
      dsHuongGiao: [],
      KHOAPHIEU_BH_HTKH: 0,
      allowLoadForm: true,
      tinhChon: [],
      IS_HPG: -1,
      ma_lt: '',//số ảo,
      wsappmtv: 1,
      taomoi_mabh: true,
      quytrinh_id: 0,
      nhanhong_khoamay: false,
      khong_nhanhong: false,
      noidung_sc: '',
      NHANHONG_TB_KHOAMAY: -1,
      khachhang_id: 0,
      huonggiao_id: 0,
      thaotac: [],
      s: '',
      giaophieu_bdg: false,
      Giaoviec_td: false,
      giaophieu_tudong: false,
      ds_nn: [],
      ds_tt: [],
      ds_ton: [],
      dsBH_DATA: [],
      dsBH_BC_DATA: [],
      dsBH_TMP: [],
      dsBH_BC_TMP: [],
      dsBH_DV: [],
      dsSoDT_Dvi: [],
      dtTTCap: [],
      pSendSMS: false,
      PHIEU_ID: 0,
      hg: 0,
      DONVI_NHAN_ID: -1,
      vnoidung: '',
      ma_bh: '',
      itemTiepNhanFocused: null,
      itemDanhSachFocused: null,
      trangthaitb_id: -1,
      formatDate: 'DD/MM/YYYY HH:mm:ss',
      userInfo: {},
      baohong_id: 0,
      GIAOPHIEU_BAOHONG_CATCHUYEN: -1,
      kieugoi_id: 0,
      colNguoiGiu_Visible: false,
      modelInput: {},
      frmPhanAnhBHInput: {},
      frmGiaoPhieu: {
        inputServiceId: 0,
        inputQuyTrinhId: 0,
        inputHuongGiaoId: 0,
        inputStatusId: 0,
        inputDtpNgayGiaoTK: null,
        inputTxtMaTBList: []
      },
      chkKenhTrang: false,
      cboTinh: 0,
      cboTinh_Data: [],
      tinh_tc: 0,
      daucuoi_id: 0,
      kenh_trang: true,
      tinh: {},
      thuebao_id_tc: 0,
      tt_sua: 0,
      rsc_id: 0,
      checkPortDialog: {
          isVisiable: false,
          input: {
              vma_tb: null,
              vthuebao_id: null,
              vkieu: null,
          }
      },
      animationSettings: { effect: 'None' },
      i_maTB: "",
    }
  },
  watch: {
    'kqxl.cboKQXL': function (val) {
      this.cboKQXL_EditValueChanged();
    },
    'kqxl.cboNhomNN': function (val, oldVal) {
      if (parseInt(val) !== parseInt(oldVal)) {
        this.cboNhomNN_EditValueChanged();
      }
    },
    'kqxl.cboNhomTon': function (val) {
      this.cboNhomTon_EditValueChanged();
    },
    'tttn.dichvuvt_id': async function (val, oldVal) {
      if (parseInt(val) !== parseInt(oldVal)) {
        await this.cboDichVuVT_SelectedValueChanged();
      }
    },
    'tttn.loaihinh_id': function (val, oldVal) {
      if (parseInt(val) !== parseInt(oldVal)) {
        this.cboLoaiTB_SelectedValueChanged();
      }
    },
    'dsbh.cboDichVu': function (val) {
      this.cboTKDichVu_EditValueChanged();
    },
    'dsbh.cboLoaiHinh': function (val) {
      this.cboTKLoaiHinh_EditValueChanged();
    },
    'dsbh.cboTTVT': function (val) {
      this.cbeTK_TTVT_EditValueChanged();
    },
    'dsbh.chkDichVu': function (val) {
      if (!val) {
        this.dsbh.chkLoaiHinh = false;
      }
      this.chkTkDichVu_CheckedChanged();
    },
    'dsbh.chkLoaiHinh': function (val) {
      this.chkTkLoaiHinh_CheckedChanged();
    },
    'dsbh.chkTTVT': function (val) {
      this.cbeTK_TTVT_EditValueChanged();
    },
    'ttHong.cboNhomTT': async function (val, oldVal) {
      if (parseInt(val) !== parseInt(oldVal)) {
        await this.cboNhomTT_EditValueChanged();
      }
    },
    'ttHong.chkPhieuChoXL': function (val) {
      this.chkPhieuChoXL_CheckedChanged();
    },
    'ttHong.chkPhieutra': function (val) {
      this.chkPhieutra_CheckedChanged()
    },
    'ttHong.chkPhieuDaSua': function (val) {
      this.chkPhieuDaSua_CheckedChanged()
    },
    'ttHong.chkPhieuDaChuyenTinh': function (val) {
      this.chkPhieuDaChuyenTinh_CheckedChanged()
    },
  },
  methods: {
    async frmLoad() {
      try {
        this.loading(true)

        await this.HT_DS_Tinh_Thicong_Combobox();
        await this.initParams();
        await this.load();

        this.loading(false)
      } catch (err) {
        this.$toast.error('frmLoad: ' + err.message);
      }
    },

    async HT_DS_Tinh_Thicong_Combobox() {
      try {
        let rs = await API.load_tinh_thi_cong(this.axios, {tinhthicong_id: this.$root.token.getPhanVungID()});
        let data = this.getDataFromAPI(rs);
        this.cboTinh_Data = data || [];
        if (this.cboTinh_Data.length > 0) {
          this.cboTinh = this.cboTinh_Data[0].tinh_id;
        }
      } catch (e) {
        console.log('Khong load HT_DS_Tinh_Thicong_Combobox');
      }
    },

    async xuLyTag() {
      //host: 1+1
      let tagFromRoute = '';
      if (this.$route.query.tag != null && this.$route.query.tag !== '' && this.$route.query.tag.length > 0) {
        tagFromRoute = this.$route.query.tag;
      } else if (this.$route.params.tag != null && this.$route.params.tag !== '' && this.$route.params.tag.length > 0) {
        tagFromRoute = this.$route.params.tag;
      }

      if (this.$route.query.ma_tb != null && this.$route.query.ma_tb !== '' && this.$route.query.ma_tb.length > 0) {
        this.i_maTB = this.$route.query.ma_tb;
      }


      try {
        if (tagFromRoute !== '') {
          let words = tagFromRoute.toString().split('+');
          if (words.length >= 1)
            this.tag = parseInt(words[0].toString());
          if (words.length >= 2)
            this.tag_BH_AO = parseInt(words[1].toString());
          if (words.length >= 3)
            this.tag_TuDong_LayTT = parseInt(words[2].toString());
          this.btnVisible.tsbtnNhapMoi = this.btnVisible.tsbtnTiepNhan = this.tag !== 9;
        }
      } catch (e) {
      }
    },

    async initParams() {
      this.loading(true);
      await this.loadParamsFromAPI();

      let {dsThamSo} = this;
      if (dsThamSo.length > 0) {
        let ma_ts = "HIENTHI_PHANANH_NHANHONG";
        let val = this.getParam(ma_ts, dsThamSo);
        if (val && val.trim() === '1') {
          this.HIENTHI_PHANANH_NHANHONG = 1;
        }

        ma_ts = "NHAP_BH_DT_GHICHU";
        val = this.getParam(ma_ts, dsThamSo);
        if (val && val.trim() === '1') {
          this.Nhap_ghichu = true;
        }

        ma_ts = "BATBUOC_GHICHU_KHOAPHIEU_BH";
        val = this.getParam(ma_ts, dsThamSo);
        if (val && val.trim() === '1') {
          this.BATBUOC_GHICHU_KHOAPHIEU_BH = 1;
        }

        ma_ts = "GIOIHAN_NGUYENNHAN_BAOHONG";
        val = this.getParam(ma_ts, dsThamSo);
        if (val) {
          try {
            this.GIOIHAN_NGUYENNHAN_BAOHONG = parseInt(val.trim());
          } catch (e) {
          }
        }

        ma_ts = "TIMER_TIMKIEM_TIEPNHAN_BH";
        val = this.getParam(ma_ts, dsThamSo);
        if (val) {
          try {
            this.TIMER_TIMKIEM_TIEPNHAN_BH = parseInt(val.trim());
          } catch (e) {
          }
        }

        ma_ts = "BAOHONG_NHIEU_DIENTHOAI_LH";
        val = this.getParam(ma_ts, dsThamSo);
        if (val && val.trim() === '1') {
          this.BAOHONG_NHIEU_DIENTHOAI_LH = 1;
        }

        ma_ts = "THONGBAO_SUCO_TTVT";
        val = this.getParam(ma_ts, dsThamSo);
        if (val) {
          try {
            this.THONGBAO_SUCO_TTVT = parseInt(val.trim());
          } catch (e) {
          }
        }

        ma_ts = "HT_CHITIEU_TG_KHI_NHANHONG";
        val = this.getParam(ma_ts, dsThamSo);
        if (val) {
          try {
            this.HT_CHITIEU_TG_KHI_NHANHONG = parseInt(val.trim());
          } catch (e) {
          }
        }

        ma_ts = "KHONG_BAOHONG_PHANHOI_DONGTHOI";
        val = this.getParam(ma_ts, dsThamSo);
        if (val) {
          try {
            this.KHONG_BAOHONG_PHANHOI_DONGTHOI = parseInt(val.trim());
          } catch (e) {
          }
        }

        ma_ts = "TIEPNHAN_BH_LINK_PHANHOI_KH";
        val = this.getParam(ma_ts, dsThamSo);
        if (val) {
          try {
            this.TIEPNHAN_BH_LINK_PHANHOI_KH = parseInt(val.trim());
          } catch (e) {
          }
        }
      }

      if (this.TIEPNHAN_BH_LINK_PHANHOI_KH === 1) {
        this.btnVisible.tsbtnPHKH = true;
      }

      if (this.THONGBAO_SUCO_TTVT === 1) {
        this.lblSuCoTTVT = "";

        try {
          let rs = await API.lay_ds_thongbao_suco_ttvt(this.axios, {
            "denngay": moment().format(this.formatDate),
            "loaids": DS_KHONG_XULY.THONGBAO_SUCO_TTVT,
            "tungay": moment().format(this.formatDate),
          });

          if (rs.data != null && rs.data.error === '200' && rs.data.data != null && rs.data.data.length > 0) {
            let {data} = rs.data;
            this.lblSuCoTTVT = data[0].lydo;
          }
        } catch (ex) {
          console.log(ex.message);
        }
      }

      this.loading(false);
    },

    async loadParamsFromAPI() {
      try {
        let rs = await API.sp_lay_ds_thamso_md(this.axios);
        let data = this.getDataFromAPI(rs);
        this.dsThamSo = data != null ? data : [];

        // rs = await API.get_ds_thamso_quantri(this.axios);
        // this.dsThamSoQuanTri = this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Khong load duoc tham so tu API');
      }
    },

    getParam(ma_ts, dsThamSo) {
      if (dsThamSo != null && dsThamSo.length > 0) {
        let filter = dsThamSo.filter(item => {
          return item.ma_ts === ma_ts;
        });
        if (filter.length > 0) {
          return filter[0]["ten_ts"].toString();
        }
      }

      return '';
    },

    async load() {
      this.loading(true);
      try {
        await this.getKyHoaDon();
        let vuser_alt = await this.newMapData(
          '/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tinh',
          {param: this.$root.token.getPhanVungID(), type: 2});
        if (vuser_alt == "" || vuser_alt == "-1") {
          this.vuser_alt = "";
        } else {
          this.vuser_alt = vuser_alt;
        }

        let vpass_alt = await this.newMapData(
          '/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tinh',
          {param: this.$root.token.getPhanVungID(), type: 3});
        if (vpass_alt == "" || vpass_alt == "-1") {
          this.vpass_alt = "";
        } else {
          this.vpass_alt = vpass_alt;
        }

        let ngay_ht = moment().format(this.formatDate);
        this.tttn.ngay_hentl_tu = this.tttn.ngay_hentl_den =
          this.tttn.ngay_bh = this.kqxl.ngayXN = this.ttHong.ngayTH = ngay_ht;
        this.kqxl.cboKQXL = 0;

        await this.loadCbDichVu();

        let tsKhoaPhieu_bh = await this.getThamSo('KHOAPHIEU_BAOHONG');
        if (tsKhoaPhieu_bh != null) {
          this.kp = tsKhoaPhieu_bh[0].ten_ts;
          this.btnVisible.tsbtnKhoaPhieu = !(tsKhoaPhieu_bh[0].ten_ts == "1");
        }

        this.cboKQXL_EditValueChanged();

        await this.loadCbNhomNN(this.tttn.dichvuvt_id);
        this.loadCbNhomTon();
        this.loadCbNguoiGV().then(() => {
          this.ttHong.cboNguoiTH = this.$root.token.getNguoiDungID();
        });

        this.loadHuongGiao();
        this.loadcbeDonViPhoiHop();

        this.HienThi_Danhsach_Tiepnhan();
        this.HienThi_DanhSach_Tb_BaoHong();

        this.loadCbDVVTTK();
        this.loadCbTTVT();
        this.loadCbHangSX();

        //Hiển thị nút báo hỏng ảo (Hà Nội yêu cầu)
        this.btnVisible.tsbtnBaoHongAo = false;
        // gridviewDanhSach.Columns["MA_BH"]: false,
        // txtTenTB.ReadOnly = true;
        if (this.tag_BH_AO === 1) {
          this.btnVisible.tsbtnBaoHongAo = true;
          // gridviewDanhSach.Columns["MA_BH"].Visible = true;
          // txtTenTB.ReadOnly = false;
        }
      } catch (e) {
        console.log(e);
        this.$root.$toast.error('load(): ' + e.message);
      } finally {
        this.loading(false);
      }
    },

    async loadCbDichVu() {
      try {
        let rs = await API.loadCbDichVu(this.axios);
        let data = this.getDataFromAPI(rs);
        this.options.dichvu = data != null ? data : [];
        if (this.options.dichvu.length > 0 && this.tttn.dichvuvt_id === 0) {
          this.tttn.dichvuvt_id = DichVuVienThong.CO_DINH
        }
      } catch (e) {
        console.log('Khong load duoc dich vu');
      }
    },

    async loadCbDVVTTK() {
      try {
        let rs = await API.loadCbDVVTTK(this.axios);
        let data = this.getDataFromAPI(rs);
        this.options.dichvutk = data != null ? data : [];
        if (this.options.dichvutk.length > 0) {
          this.dsbh.cboDichVu = data[0].ID
        }
      } catch (e) {
        console.log('Khong load loadCbDVVTTK');
      }
    },

    async loadCbNhomTon() {
      try {
        let rs = await API.loadCbNhomTon(this.axios);
        let data = this.getDataFromAPI(rs);
        this.options.nhomton = data != null ? data : [];
        if (this.options.nhomton.length > 0 && this.kqxl.cboNhomTon === 0) {
          this.kqxl.cboNhomTon = this.options.nhomton[0].NHOMTON_ID;
        }
      } catch (e) {
        console.log('Khong load nhom ton');
      }
    },

    async loadCbNhomNN(dichvuvt_id) {
      try {
        let rs = await API.loadCbNhomNN(this.axios, dichvuvt_id);
        let data = this.getDataFromAPI(rs);
        this.options.nhomnn = data != null ? data : [];
        if (this.options.nhomnn.length > 0 && this.kqxl.cboNhomNN === 0) {
          this.kqxl.cboNhomNN = this.options.nhomnn[0].loaihong_id;
        }
      } catch (e) {
        console.log('Khong load nhom nguyen nhan');
      }
    },

    async loadCbLoaiHinhTB(dichvuvt_id) {
      try {
        let rs = await API.lay_loaihinhtb_theo_dvvt(this.axios, dichvuvt_id);
        let data = this.getDataFromAPI(rs);
        this.options.loaihinh = data != null ? data : [];
        if (this.options.loaihinh.length > 0 && this.tttn.loaihinh_id === 0) {
          this.tttn.loaihinh_id = data[0].ID;
        }
      } catch (e) {
        console.log('Khong load loadCbLoaiHinhTB');
      }
    },

    async loadCbLoaiHinhTBTK(dichvuvt_id) {
      try {
        let rs = await API.lay_loaihinhtb_theo_dvvt(this.axios, dichvuvt_id);
        let data = this.getDataFromAPI(rs);
        this.options.loaihinhtk = data != null ? data : [];
        if (this.options.loaihinhtk.length > 0) {
          this.dsbh.cboLoaiHinh = data[0].ID;
        }
      } catch (e) {
        console.log('Khong load loadCbLoaiHinhTB');
      }
    },

    async mapData(id_neo, table, dk) {
      try {
        let rs = await API.fnMapId(this.axios, {id_neo, table, dk});
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Khong load duoc ky hoa don');
      }
    },

    async getKyHoaDon() {
      try {
        let rs = await API.getKyHoaDon(this.axios);
        let data = this.getDataFromAPI(rs);
        if (data != null) {
          this.vkyhoadon = data[0].KYCUOC;
        }
      } catch (e) {
        console.log('Khong load duoc ky hoa don');
      }
    },

    getDataFromAPI(rs) {
      if (rs.data != null && rs.data.data != null && (typeof rs.data.data == 'number' || rs.data.data.length > 0)) {
        return rs.data.data;
      }

      return null;
    },

    async loadHuongGiao() {
      try {
        let rs = await API.loadDsHuongGiao(this.axios);
        let data = this.getDataFromAPI(rs);
        this.dsHuongGiao = data != null ? data : [];
      } catch (e) {
        console.log('Khong load duoc huong giao');
      }
    },

    async HienThi_Danhsach_Tiepnhan() {
      try {
        let rs;
        if (this.tag == 9)
          rs = await API.lay_ds_tb_tiepnhan_bh_tsl(this.axios);
        else
          rs = await API.lay_ds_tb_tiepnhan_bh(this.axios);
        let data = this.getDataFromAPI(rs);
        if (data != null) {
          this.dsTiepNhan = data;
        } else {
          this.clear();
        }
      } catch (e) {
        console.log(e);
        console.log('Khong load duoc danh sach tiep nhan');
      }
    },

    cboKQXL_EditValueChanged() {
      let {cboKQXL} = this.kqxl;
      if (cboKQXL == 0) {
        this.kqxl.enableNN = true;
        this.kqxl.enableTon = false;
        if (this.kp != "-1") {
          this.btnVisible.tsbtnKhoaPhieu = this.kp != "1";
        } else {
          this.btnVisible.tsbtnKhoaPhieu = true;
        }
      } else {
        this.kqxl.enableNN = false;
        this.kqxl.enableTon = true;
        this.btnVisible.tsbtnKhoaPhieu = false;
      }
    },

    async loadCbNguoiGV() {
      try {
        let rs = await API.loadCbNguoiGV(this.axios, this.$root.token.getDonViID());
        let data = this.getDataFromAPI(rs);
        this.options.nguoigv = data != null ? data : [];
        if (this.options.nguoigv.length > 0) {
          this.ttHong.cboNguoiTH = this.options.nguoigv[0].nhanvien_id;
        }
      } catch (e) {
        console.log('Khong load nhom nguyen nhan');
      }
    },

    async HienThi_DanhSach_Tb_BaoHong() {
      try {
        //Lấy những phiếu giao từ khách hàng về
        let {chkPhieutra, chkPhieuChoXL} = this.ttHong;
        let vttph_id = 1;
        if (chkPhieutra) vttph_id = 3;

        let rs = null;
        if (!chkPhieuChoXL.Checked) {
          rs = await API.LAY_DS_BAOHONG_V2(this.axios, {
            "vdonvi_id": this.$root.token.getDonViID(),
            "vnhanvien_id": this.$root.token.getNhanVienID(),
            "vttbh_id": TRANGTHAI_BH.MOI_TIEPNHAN,
            "vttph_id": vttph_id,
            "vkyhoadon": this.vkyhoadon,
            "vtt_sua": this.tt_sua
          })
        } else {
          rs = await API.lay_ds_phieu_cho_xuly(this.axios, {
            "vdonvi_id": this.$root.token.getDonViID(),
            "vnhanvien_id": this.$root.token.getNhanVienID(),
            "vttbh_id": TRANGTHAI_BH.CHO_XULY,
            "vttph_id": 1
          })
        }

        if (rs != null) {
          let dtBH = this.getDataFromAPI(rs);
          this.dsBaoHong = dtBH || [];
          this.dsBaoHong_Full = this.dsBaoHong;

          if (dtBH != null && dtBH.length <= 0) {
            this.ttHong = {
              ...this.ttHong,
              cboNguoiTH: this.$root.token.getNguoiDungID(),
              txtGhiChu: '',
              ngayTH: moment().format(this.formatDate)
            }

            this.kqxl.txtGhiChu = '';
            //Hiển thị danh sách tình trạng
            //Hiển thị danh sách tình trạng
            this.loadCbTinhTrang(0, this.tttn.dichvuvt_id, this.tttn.loaihinh_id, 0, 0, 1);
            this.loadCbNguyenNhan(0, this.kqxl.cboNhomNN, this.tttn.loaihinh_id, 0, 1);
            this.loadCbLyDoTon(0, this.kqxl.cboNhomTon, 0, 1);
          }

          return;
        }

        this.dsBaoHong = [];
        this.dsBaoHong_Full = [];
      } catch (ex) {
        console.log(ex);
        this.$root.$toast.error('HienThi_DanhSach_Tb_BaoHong: ' + ex.message);
      }
    },

    async loadCbNhomTT() {
      try {
        let rs = await API.loadCbNhomTT(this.axios);
        let data = this.getDataFromAPI(rs);
        this.options.nhomtt = data != null ? data : [];
        if (this.options.nhomtt.length > 0 && this.ttHong.cboNhomTT === 0) {
          this.ttHong.cboNhomTT = 1;
        }
      } catch (e) {
        console.log('Khong load nhom tinh trang');
      }
    },

    async loadCbTinhTrang(vbaohong_id, vdichvuvt_id, vloaitb_id, vkieu_id, vnhomtt_id, checkedVal) {
      try {
        let rs = await API.loadCbTinhTrang(this.axios, {
          vbaohong_id,
          vdichvuvt_id,
          vloaitb_id,
          vkieu_id,
          vnhomtt_id
        });

        let data = this.getDataFromAPI(rs);
        this.options.tinhtrangbh = data != null ? data : [];
        if (this.options.tinhtrangbh.length > 0) {
          let value = [];
          for (const tmp of data) {
            if (tmp.chon == checkedVal) {
              value.push(tmp.tinhtrang_id);
            }
          }

          return value;
        }
      } catch (e) {
        console.log('Khong load tinh trang bao hong ' + e);
      }
    },

    async loadCbNguyenNhan(vphieu_id, vnhomnn_id, vloaitb_id, vnhomtt_id, checkedVal) {
      try {
        let rs = await API.loadCbNguyenNhan(this.axios, {
          vphieu_id,
          vnhomnn_id,
          vloaitb_id,
          vnhomtt_id
        });
        let data = this.getDataFromAPI(rs);
        this.options.nguyennhan = data != null ? data : [];
        if (this.options.nguyennhan.length > 0) {
          let value = [];
          for (const tmp of data) {
            if (tmp.chon == checkedVal) {
              value.push(tmp.cthong_id);
            }
          }

          return value;
        }
      } catch (e) {
        console.log('Khong load nguyen nhan ' + e);
      }
    },

    async loadCbLyDoTon(p_baohong_id, p_nhomton_id, p_ttxl_id, checkedVal) {
      try {
        let rs = await API.loadCbLyDoTon(this.axios, {
          p_baohong_id,
          p_nhomton_id,
          p_ttxl_id
        });
        let data = this.getDataFromAPI(rs);
        this.options.lydoton = data != null ? data : [];
        if (this.options.lydoton.length > 0) {
          let value = [];
          for (const tmp of data) {
            if (tmp.chon == checkedVal) {
              value.push(tmp.lydotonbh_id);
            }
          }

          this.kqxl.cboLyDoTon = [...value];
        }
      } catch (e) {
        console.log('Khong load ly do ton ' + e);
      }
    },

    async loadCbTTVT() {
      try {
        let rs = await API.loadCbTTVT(this.axios);
        let data = this.getDataFromAPI(rs);
        this.options.ttvt = data != null ? data : [];
      } catch (e) {
        console.log('loadCbTTVT ' + e);
      }
    },

    async loadcbeDonViPhoiHop() {
      try {
        let rs = await API.loadDonViPhoiHop(this.axios, {tinhthicong_id: this.$root.token.getPhanVungID()});
        let data = this.getDataFromAPI(rs);
        this.options.donViPhoiHop = data || [];
      } catch (e) {
        console.log('loadcbeDonViPhoiHop ' + e);
      }
    },

    async loadCbHangSX() {
      try {
        let rs = await API.loadCbHangSX(this.axios);
        let data = this.getDataFromAPI(rs);
        this.options.hangsx = data != null ? data : [];
        if (this.options.hangsx.length > 0) {
          this.kqxl.cboHangSX = data[0].hangsx_id;
        }
      } catch (e) {
        console.log('Khong load nhom tinh trang');
      }
    },

    async Lay_Ds_NguoiDung_Tinh(nhanVienID, quyen) {
      try {
        let rs = await API.Lay_Ds_NguoiDung_Tinh(this.axios, {nhanVienID, quyen});
        let data = this.getDataFromAPI(rs);
        if (data != null) {
          let tinhs = new Array(67);
          for (let item of data) {
            if (item.tinh_id == null) {
              return;
            }

            let i = parseInt(item.tinh_id);
            if (i < tinhs.length) {
              tinhs[i] = true;
            }
          }

          this.tinhChon = [...tinhs];
        }
      } catch (e) {
        console.log('Khong load Lay_Ds_NguoiDung_Tinh ' + e);
      }
    },

    clear() {
      let now = moment().format(this.formatDate);
      this.tttn = {
        ...this.tttn,
        ipcc: '',
        cuoc_ps: '',
        ma_tb: '',
        ten_tb: '',
        ngay_hentl_tu: now,
        ngay_hentl_den: now,
        diachi_ld: '',
        nguoi_bh: '',
        dien_thoai: '',
        tg_cktb: '',
        ngay_bh: now,
        checkHenTL: false,
        dichvuvt_id: DichVuVienThong.CO_DINH
      }

      this.ttHong = {
        ...this.ttHong,
        txtGhiChu: '',
        cboTinhTrang: [],
        chkCamKet: false,
      }

      this.kqxl = {
        ...this.kqxl,
        cboNguyenNhan: [],
        cboLyDoTon: []
      }
      this.ma_lt = "";
      this.lsBaoHong = [];

      this.tskt = [];

      this.dsbh.chkDonViPhoiHop = false;
      this.dsbh.cboDonViPhoiHop = [];

      this.wsappmtv = KENHTN_BAOHONG.KENHTONGDAI;
      this.taomoi_mabh = true;
      this.itemTiepNhanFocused = null;
      this.itemDanhSachFocused = null;
      this.$refs.dsBaoHong.setSelectedRows([]);
    },

    async onEnterMaTB() {
      this.loading(true);
      let {ma_tb} = this.tttn;
      try {
        if (ma_tb != "") {
          this.tinh = await this.LAY_TINHTHICONG_THEO_MATB(ma_tb);
          if (this.tinh.tinhthicong_id === 0) {
            this.kenh_trang = true;
            let confirm = await this.$confirm(
              `Thuê bao không có trong danh bạ bán chéo. Bạn phải chọn tỉnh thi công!`,
              'Thông báo', {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không'
              }).then(() => true).catch(() => false);
            if (!confirm) {
              this.clear();
              return;
            } else {
              this.tinh = await this.$refs.frmChonTinh.showModal();
            }
          } else {
            this.kenh_trang = false;
          }

          let rs = await API.lay_danhba_theo_matb(this.axios, ma_tb);
          let data = this.getDataFromAPI(rs);
          let index = 0;
          if (data != null) {
            this.tinh_tc = this.tinh.tinhthicong_id;// int.Parse(dtDB_BC.Rows[0]["tinh_kn_id"].ToString());
            this.cboTinh = this.tinh_tc;
            this.tttn.thuebao_id = data[0]["thuebao_id"];
            if (this.tinh_tc == 0) {
              this.$root.$toast.error("Bạn chưa chọn tỉnh thi công!");
              return;
            }

            if (this.kenh_trang) {
              this.thuebao_id_tc = -1;
            } else {
              this.thuebao_id_tc = await this.LAY_THUEBAOID_THICONG_v2(this.tinh, this.tttn.thuebao_id);
              if (this.thuebao_id_tc == 0) {
                this.$root.$toast.error("Thuê bao không có trong danh bạ tại tỉnh thi công!");
                return;
              }
            }

            if (data.length === 1) {
              index = 0;
              this.dsThueBao = [];
            } else {
              this.dsThueBao = [...data];
              let rs = await this.$refs.formTraCuuTB.showModal();
              if (rs.index != -1) {
                index = rs.index;
                this.daucuoi_id = rs.daucuoi_id;
              }

              if (this.dsThueBao.length > 0) {
                this.tinh_tc = parseInt(this.dsThueBao[index]["tinh_id"]);
                this.tinh = await this.tinh_thicong(this.tinh_tc);
                let dtThueBao_Tinh_TC = await this.Lay_DanhBa_Theo_MaTB_TinhTB(this.tinh, ma_tb, 0);
                if (this.kenh_trang)
                  this.thuebao_id_tc = -1;
                else
                  this.thuebao_id_tc = parseInt(dtThueBao_Tinh_TC[0]["thuebao_id"]);
                this.cboTinh = this.tinh_tc;
              }
            }

            this.ttHong.txtGhiChu = "";
            await this.HienThiThongTinThueBao(data[index]);
            await this.HienThi_DS_LichSu_BaoHong();

            if (this.tttn.dien_thoai == "")
              this.tttn.dien_thoai = data[0]["so_dt"];
            if (this.PHAILAM("DIENTHOAI_LIENHEBH")) {
              this.tttn.dien_thoai = "";
              this.tttn.nguoi_bh = "";
            }

            //Kiểm tra nếu có thuê bao báo hỏng 2 lần trong tháng.
            //hiện cảnh báo thuê thiệt hại nếu thuê bao rời mạng.
            this.HienCanhBaoThietHai(ma_tb);
          } else {
            this.$root.$toast.error("Không tìm thấy thông tin thuê bao " + ma_tb);
            this.tttn.thuebao_id = 0;
            this.clear();
          }

        }
      } catch (ex) {
        console.log("onEnterMaTB: " + ex);
        this.$root.$toast.error(ex.message);
      } finally {
        this.loading(false);
      }
    },

    async HienThiThongTinThueBao(thuebao) {
      try {
        if (thuebao != null) {
          {
            let {thuebao_id, ma_tb} = thuebao;
            this.nhanhong_khoamay = false;
            this.tttn.ma_tb = ma_tb;
            this.tttn.thuebao_id = thuebao_id;
            let kt = "";
            this.tinh_tc = this.cboTinh;

            kt = await this.KIEMTRA_THUEBAO_BAOHONG(thuebao_id, 2, this.daucuoi_id);
            if (kt != "1") {
              let d = await this.sp_lay_gt_thamso_md_tents('CAPNHAT_SOLAN_BAOHONG_119', '1');
              if (d != null) {
                await this.popuptientrinh(thuebao_id, kt, this.tttn.dichvuvt_id);
              } else {
                this.$root.$toast.warning(kt);
              }

              if (this.HIENTHI_PHANANH_NHANHONG == 1) {
                let vvbaohong_id = await this.newMapData('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_baohong_theo_thuebao', {
                  "param": thuebao_id,
                  "type": 1
                });

                if (vvbaohong_id != "") {
                  this.frmPhanAnhBHInput.vbaohong_id_t = vvbaohong_id
                  this.frmPhanAnhBHInput.thuebao_id = thuebao_id
                  this.frmPhanAnhBHInput.kieu_nhac = 2; //TTKD nhắc
                  this.frmPhanAnhBHInput.ma_tb = ma_tb;
                  this.frmPhanAnhBHInput.nguoi_bh = this.tttn.nguoi_bh;
                  this.frmPhanAnhBHInput.dienthoai_lh = this.tttn.dien_thoai;
                  this.frmPhanAnhBHInput.noidung_bh = this.ttHong.txtGhiChu;

                  this.$refs.dialogfrmPhanAnhBH.openDialog()
                }
              }

              return;
            }

            if (!this.kenh_trang && this.tinh.tinhthicong_id != 0 && this.thuebao_id_tc > 0) {
              kt = await this.kiemtra_thuebao_baohong_119(this.tinh, this.thuebao_id_tc, 2);
              if (kt != "1") {
                this.$root.$toast.error(kt);
                return;
              }
            }

            let {trangthaitb_id, ngay_cat, ngay_td} = thuebao;
            if (trangthaitb_id == TrangThaiTB.DOISO) {
              if (this.NHANHONG_TB_KHOAMAY) {
                let confirm = await this.$confirm(
                  `Thuê bao ${ma_tb} đổi số ngày ${ngay_cat}. Bạn có đồng ý tiếp tục?`,
                  'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  }).then(() => true).catch(() => false);
                if (!confirm) {
                  return;
                } else {
                  this.nhanhong_khoamay = true;
                }
              } else {
                this.$root.$toast.error(`Thuê bao ${ma_tb} đổi số ngày ${ngay_cat} "! Bạn không thể tiếp nhận thuê bao này!`);
                return;
              }
            }

            if (trangthaitb_id == TrangThaiTB.THANHLY || trangthaitb_id == TrangThaiTB.THANHLY_NO) {
              if (this.NHANHONG_TB_KHOAMAY) {
                let confirm = await this.$confirm(
                  `Thuê bao ${ma_tb} thanh lý ngày ${ngay_cat}. Bạn có đồng ý tiếp tục?`,
                  'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  }).then(() => true).catch(() => false);
                if (!confirm)
                  return;
                else
                  this.nhanhong_khoamay = true;
              } else {
                this.$root.$toast.error(`Thuê bao ${ma_tb} thanh lý ngày ${ngay_cat}. Bạn không thể tiếp nhận thuê bao này!`);
                return;
              }
            }

            if (trangthaitb_id == TrangThaiTB.TAMDUNG || trangthaitb_id == TrangThaiTB.KHOA_HAICHIEU_YEUCAU) {
              if (this.NHANHONG_TB_KHOAMAY) {
                let confirm = await this.$confirm(
                  `Thuê bao ${ma_tb} tạm dừng ngày ${ngay_td}. Bạn có đồng ý tiếp tục?`,
                  'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  }).then(() => true).catch(() => false);
                if (!confirm)
                  return;
                else
                  this.nhanhong_khoamay = true;
              } else {
                this.$root.$toast.error(`Thuê bao ${ma_tb} thanh lý ngày ${ngay_td}. Bạn không thể tiếp nhận thuê bao này!`);
                return;
              }
            }

            if (trangthaitb_id == TrangThaiTB.KHOA_HAICHIEU_CUONGBUC) {
              if (this.NHANHONG_TB_KHOAMAY) {
                let confirm = await this.$confirm(
                  `Thuê bao ${ma_tb} đang bị khóa 2 chiều ngày ${ngay_td}. Bạn có đồng ý tiếp tục?`,
                  'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  }).then(() => true).catch(() => false);
                if (!confirm)
                  return;
                else
                  this.nhanhong_khoamay = true;
              } else {
                this.$root.$toast.error(`Thuê bao ${ma_tb} đang bị khóa 2 chiều ngày ${ngay_td}. Bạn không thể tiếp nhận thuê bao này!`);
                return;
              }
            }

            this.HienThiChiTietBH(thuebao);
            let phanVungId = this.$root.token.getPhanVungID();
            if (phanVungId != this.phanVung.HNI && phanVungId != this.phanVung.HCM) {
              var dtkt = await this.lay_ds_hd_chuaht(thuebao_id);
              if (dtkt != null) {
                this.$root.$toast.error("Thuê bao đang có hợp đồng " + dtkt[0].ten_loaihd + " chưa hoàn thiện! Bạn không thể nhận hỏng!");
                return;
              }
            }

            let dtck = await this.lay_ds_catchuyen_cap(thuebao_id);
            if (dtck != null) {
              if (dtck[0].chan_baohong == "1") {
                if (dtck[0].ngaykt_dk == "") {
                  this.$root.$toast.warning("Thuê bao đang cắt chuyển cáp! Bạn không thể nhận hỏng!\n" +
                    "Ngày yêu cầu cắt chuyển: " + dtck[0].ngay_yc + ".\n" +
                    "Người cập nhật: " + dtck[0].nguoi_cn);
                } else {
                  this.$root.$toast.warning("Thuê bao đang cắt chuyển cáp! Bạn không thể nhận hỏng!\n" +
                    "Ngày yêu cầu cắt chuyển: " + dtck[0].ngay_yc + ".\n" +
                    "Ngày dự kiến hoàn thành cắt chuyển : " + dtck[0].ngaykt_dk + ".\n" +
                    "Người cập nhật: " + dtck[0].nguoi_cn);
                }

                let confirm = await this.$confirm(
                  `Bạn có muốn lưu thông tin báo hỏng không?`,
                  'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  }).then(() => true).catch(() => false);
                if (!confirm) {
                  return;
                }

                this.$refs.frmCatChuyenBH.initData(
                  dtck[0]["dkcc_id"],
                  this.tttn.thuebao_id,
                  thuebao.ma_tb,
                  this.tttn.dichvuvt_id,
                  thuebao.loaitb_id
                );
                this.$refs.frmCatChuyenBH.showModal();
                return;
              } else {
                if (dtck[0].ngaykt_dk == "") {
                  let confirm = await this.$confirm(
                    "Thuê bao đang cắt chuyển cáp! \n" +
                    "Ngày yêu cầu cắt chuyển: " + dtck[0].ngay_yc + ".\n" +
                    "Người cập nhật: " + dtck[0].nguoi_cn + ".\n" +
                    "Bạn muốn tiếp tục nhận hỏng ? ",
                    'Thông báo', {
                      confirmButtonText: 'Có',
                      cancelButtonText: 'Không'
                    }).then(() => true).catch(() => false);
                  if (!confirm) {
                    this.clear();
                    return;
                  }
                } else {
                  let confirm = await this.$confirm(
                    "Thuê bao đang cắt chuyển cáp! Bạn không thể nhận hỏng!\n" +
                    "Ngày yêu cầu cắt chuyển: " + dtck[0].ngay_yc + ".\n" +
                    "Ngày dự kiến hoàn thành cắt chuyển : " + dtck[0].ngaykt_dk + ".\n" +
                    "Người cập nhật: " + dtck[0].nguoi_cn + ".\n" +
                    "Bạn muốn tiếp tục nhận hỏng ? ",
                    'Thông báo', {
                      confirmButtonText: 'Có',
                      cancelButtonText: 'Không'
                    }).then(() => true).catch(() => false);
                  if (!confirm) {
                    this.clear();
                    return;
                  }
                }
              }
            }
          }
        }
      } catch (exp) {
        this.$root.$toast.error('Lỗi: ' + exp);
        console.log(exp);
      }
    },

    async HienThi_DS_LichSu_BaoHong() {
      try {
        let rs = await API.Lay_lichsu_baohong_thuebao(this.axios, this.tttn.thuebao_id);
        let data = this.getDataFromAPI(rs);

        if (data != null && data.length > 0) {
          for (let i = 0; i < data.length; i++) {
            let dt = await this.SP_DS_BAOHONG_BC_BAOHONG_ID(data[i].baohong_id);
            if (dt.length > 0) {
              let tinhtrang = "";
              for (let j = 0; j < dt.length; j++) {
                tinhtrang += (dt[j]["tinhtrang"] || '') + ";";
              }
              if (tinhtrang.length > 0) {
                data[i]["TINHTRANG"] = tinhtrang.substr(0, tinhtrang.length - 1);
              }
            }
          }
          this.lsBaoHong = data;
          this.tttn.nguoi_bh = data[0].nguoi_bh;
          this.tttn.dien_thoai = data[0].dienthoai_lh;
        } else {
          this.lsBaoHong = [];
          this.tttn.nguoi_bh = '';
          this.tttn.dien_thoai = '';
        }
      } catch (e) {
        console.log('Khong load HienThi_DS_LichSu_BaoHong: ' + e);
      }

      return '';
    },

    async HienCanhBaoThietHai(ma_tb) {
      let val = this.getParam('CANH_BAO_THIET_HAI_FIBER', this.dsThamSo);
      if (val != '') {
        try {
          let rs = await API.lay_nd_sms_canhbao_thiethai(this.axios, {
            "huydau": 0,
            "ma_tb": ma_tb,
            "rutgon": 1
          });
          let data = this.getDataFromAPI(rs);

          if (data != null) {
            this.tttn.lblCanhBao = data.replace('CANH BAO THIET HAI: ', '');
          }
        } catch (e) {
          console.log('Khong load HienCanhBaoThietHai: ' + e);
        }
      }
    },

    PHAILAM(code) {
      if (this.thaotac.length > 0) {
        let filter = this.thaotac.filter(item => {
          return item.enable == 1 && item.code == code;
        })

        return filter.length > 0;
      }

      return false;
    },

    HienThiChiTietBH(thuebao) {
      if (thuebao != null) {
        let {
          ma_tb,
          thuebao_id,
          ma_lt,
          so_dt,
          dichvuvt_id,
          loaitb_id,
          ten_tb,
          diachi_ld,
          diachi_tb,
          trangthai_tb,
          dactrung,
          khachhang_id
        } = thuebao;
        dichvuvt_id = parseInt(dichvuvt_id);
        loaitb_id = parseInt(loaitb_id);

        this.tttn = {
          ...this.tttn,
          thuebao_id,
          dichvuvt_id,
          ten_tb,
          diachi_ld,
          trangthai_tb,
          dactrung,
          so_dt,
          diachi_tb,
          ma_tb
        }
        this.ma_lt = ma_lt;

        this.tttn.loaihinh_id = loaitb_id;
        this.khachhang_id = khachhang_id;
        this.loadCbTinhTrang(0, dichvuvt_id, loaitb_id, 0, this.ttHong.cboNhomTT, 1);

        if (this.KHOAPHIEU_BH_HTKH == 0)
          this.HT_THONGSO_KYTHUAT(thuebao_id, dichvuvt_id);

        if (this.HT_CHITIEU_TG_KHI_NHANHONG == 1) {
          this.Lay_chitieu_tg_new(thuebao_id, -1);
        }

        //Hiển thị thông tin nợ phát sinh tháng liền kề
        this.LAY_TONGNOPS_THUEBAO(ma_tb, this.vkyhoadon);
      }
    },

    async KIEMTRA_THUEBAO_BAOHONG(thuebao_id, kieu, daucuoi_id) {
      try {
        let rs = await API.kiemtra_thuebao_baohong(this.axios, {
          "vthuebao_id": parseInt(thuebao_id),
          "vkieu": kieu,
          "vdaucuoi_id": daucuoi_id
        });
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Khong load KIEMTRA_THUEBAO_BAOHONG');
      }

      return 'Không kiểm tra được thuê bao';
    },

    async popuptientrinh(thuebao_id, kt, vdichvuvt) {
      this.loading(false);
      let done = false;
      await this.$confirm(
        `${kt}, Bạn muốn chuyển đến tiến trình báo hỏng không?`,
        'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
        this.$refs.frmPopUpTiepNhan.initData(true, thuebao_id, vdichvuvt);
        let rs = await this.$refs.frmPopUpTiepNhan.showModal();
        if (rs) {
          done = true;
          this.loading(true);
        }
      }).catch(err => {
        console.log(err)
      });

      return done;
    },

    async HT_THONGSO_KYTHUAT(thuebao_id, dichvuvt_id) {
      try {
        let rs = await API.lay_ds_thongtin_kythuat(this.axios, thuebao_id);
        let data = this.getDataFromAPI(rs);
        this.tskt = data != null ? data : [];
      } catch (e) {
        console.log('Khong load HT_THONGSO_KYTHUAT');
      }

    },

    async Lay_chitieu_tg_new(vthuebao_id, vhdtb_id) {
      try {
        let rs = await API.lay_chitieu_tg_new(this.axios, {
          "vthuebao_id": vthuebao_id,
          "vhdtb_id": vhdtb_id
        });
        let data = this.getDataFromAPI(rs);
        this.tttn.tg_cktb = data;
      } catch (e) {
        console.log('Khong load Lay_chitieu_tg_new');
      }

      return "";
    },

    async LAY_TONGNOPS_THUEBAO(vma_tb, vkyhoadon) {
      try {
        let rs = await API.lay_tongnops_thuebao(this.axios, {
          vma_tb,
          vkyhoadon
        });
        if (rs.data != null && rs.data.error === '200') {
          this.tttn.cuoc_ps = rs.data.data;
          return rs.data.data;
        }
      } catch (e) {
        console.log('Khong load LAY_TONGNOPS_THUEBAO');
      }

      return 0;
    },

    async lay_ds_hd_chuaht(thuebao_id) {
      try {
        let rs = await API.lay_ds_hd_chuaht(this.axios, thuebao_id);
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Khong load lay_ds_hd_chuaht');
      }

      return null;
    },

    async lay_ds_catchuyen_cap(thuebao_id) {
      try {
        let rs = await API.lay_ds_catchuyen_cap(this.axios, thuebao_id);
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Khong load lay_ds_catchuyen_cap');
      }

      return null;
    },

    async thietlap_thaotac() {
      try {
        let rs = await API.sp_lay_ds_thaotac(this.axios, {
          "huonggiao_id": this.huonggiao_id,
          "quytrinh_id": this.quytrinh_id
        });
        let data = this.getDataFromAPI(rs);

        this.thaotac = data != null ? data : [];
      } catch (e) {
        console.log('Khong load thietlap_thaotac');
      }
    },

    async cboDichVuVT_SelectedValueChanged() {
      try {
        if (this.options.dichvu.length <= 0) return;
        let {dichvuvt_id} = this.tttn;
        if (dichvuvt_id == null || dichvuvt_id == '' || dichvuvt_id == 0) return;
        await this.loadCbLoaiHinhTB(dichvuvt_id);
        await this.loadCbNhomNN(dichvuvt_id);
        this.setsize();
      } catch (exp) {
      }
    },

    setsize() {

    },

    async getThamSo(mats) {
      try {
        let rs = await API.lay_gt_thamso_md_mats(this.axios, mats);
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Khong load getThamSo');
      }

      return null;
    },

    async cboLoaiTB_SelectedValueChanged() {
      let {loaihinh_id} = this.tttn;
      if (loaihinh_id) {
        if (loaihinh_id == null) return;

        this.loadCbTinhTrang(0, this.tttn.dichvuvt_id, loaihinh_id, 0, this.ttHong.cboNhomTT, 1);
        let dtQT = await this.LayHuongGiao_BaoHong_TheoThuTu(loaihinh_id, 1);
        if (dtQT != null) {
          this.quytrinh_id = dtQT[0]["quytrinh_id"];
          this.huonggiao_id = dtQT[0]["huonggiao_id"];
        }
        this.thietlap_thaotac();
      }
    },

    async LayHuongGiao_BaoHong_TheoThuTu(vloaitb_id, vthutu) {
      try {
        let rs = await API.LayHuongGiao_BaoHong_TheoThuTu(this.axios, {
          vloaitb_id,
          vthutu
        });
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('LayHuongGiao_BaoHong_TheoThuTu ' + e);
      }
    },

    async LayHuongGiao_BaoHong_TheoThuTu_BanCheo(tinhthicong_id, vloaitb_id, vthutu) {
      try {
        let rs = await API.LayHuongGiao_BaoHong_TheoThuTu_BanCheo(this.axios, {
          tinhthicong_id,
          vloaitb_id,
          vthutu
        });
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('LayHuongGiao_BaoHong_TheoThuTu_BanCheo ' + e);
      }
    },

    async sp_ds_baohong_bc(bh_id) {
      try {
        let rs = await API.sp_ds_baohong_bc(this.axios, bh_id);
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Khong load sp_ds_baohong_bc');
      }

      return null;
    },

    //Click Button Event
    btnNhapMoi_Click() {
      this.clear();
      this.$refs.danhsachtiepnhan.reset();
    },

    btnLayTTMoi_Click() {
      try {
        if (this.tag == 3) {
          this.LayPhieu_DangGiu();
          return;
        }

        if (this.KHOAPHIEU_BH_HTKH == 0) {
          this.HienThi_Danhsach_Tiepnhan();
        }

        this.HienThi_DanhSach_Tb_BaoHong();
      } catch (e) {
        this.$root.$toast.error(e);
      }
    },

    LayPhieu_DangGiu() {
      try {
        // dtBH = MultiDB.GhepDuLieu_BaoHong(dt);
        //
        // gridviewDanhSach.FocusedRowChanged -= gridviewDanhSach_FocusedRowChanged;
        // gridDanhSach.DataSource = dtBH;
        // gridviewDanhSach.FocusedRowChanged += gridviewDanhSach_FocusedRowChanged;
        // ReLoadDivisionalData(gridviewDanhSach.GetFocusedDataRow());
        // gridviewDanhSach_FocusedRowChanged(0);
        //
        // gridviewDanhSach.BestFitColumns();
      } catch (ex) {
        this.$root.$toast.error("Có lỗi xảy ra");
        console.log(ex);
      }
    },

    async tsbtnChoTiepNhan_Click() {
      try {
        this.loading(true);
        //Kiểm tra thuê bao tiếp nhận
        let ktTam = await this.KiemTra_TiepNhan_Tam();
        if (!ktTam) {
          return;
        }

        let {thuebao_id} = this.tttn;
        let kt = await this.KIEMTRA_THUEBAO_BAOHONG(thuebao_id, 1, this.daucuoi_id);
        if (kt != "1") {
          this.$root.$toast.error(kt);
          return;
        }

        //Insert vào bảng báo hỏng temp
        this.rsc_id = 0;
        let dtSuCo = await this.LAY_DS_THIETBI_SUCO(thuebao_id);
        if (dtSuCo != null && dtSuCo.length > 0) {
          this.rsc_id = parseInt(dtSuCo[0]["rsc_id"]);
          this.$root.$toast.error("Thuê bao " + this.tttn.ma_tb + " nằm trên thiết bị "
            + dtSuCo[0]["tendslam"] + " đang bị sự cố, từ "
            + dtSuCo[0]["hensuatu"] + ". Dự kiến xử lý tới "
            + dtSuCo[0]["hensuaden"] + ". Không thể tiếp nhận.");
          return;
        }

        await this.TaoDuLieu_BaoHong_Temp(true);
        await this.fn_chotiepnhan_tiepnhanbh_bancheo(this.dsBH_TMP, this.dsBH_BC_TMP);
        this.$root.$toast.success("Tiếp nhận thông tin báo hỏng thành công!");
        this.clear();
        await this.HienThi_Danhsach_Tiepnhan();
      } catch (ex) {
        console.log(ex);
        this.$root.$toast.error("tsbtnChoTiepNhan_Click: " + ex);
      } finally {
        this.loading(false);
      }
    },

    async fn_chotiepnhan_tiepnhanbh() {
      try {
        let vds = [{
          "THUEBAO_ID": this.tttn.thuebao_id,
          "DICHVUVT_ID": this.tttn.dichvuvt_id,
          "LOAITB_ID": this.tttn.loaihinh_id,
          "MA_LT": this.ma_lt,
          "DONVI_ID": this.$root.token.getDonViID(),
          "NGAY_BH": this.tttn.ngay_bh,
          "NGUOI_BH": this.tttn.nguoi_bh,
          "DIENTHOAI_LH": this.tttn.dien_thoai,
          "GHICHUHONG": this.ttHong.txtGhiChu,
          "CHKHENTL": this.tttn.checkHenTL ? 1 : 0,
          "TUNGAY": this.tttn.ngay_hentl_tu,
          "DENNGAY": this.tttn.ngay_hentl_den,
          "NGUOI_CN": this.$root.token.getUserName(),
          "MAY_CN": await this.$root.token.getMachine(),
          "NHANVIEN_ID": this.$root.token.getNhanVienID(),
          "MA_TB": this.tttn.ma_tb
        }];

        let rs = await API.fn_chotiepnhan_tiepnhanbh(this.axios, {vds: JSON.stringify(vds)});
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }

      return 'Lỗi tiếp nhận';
    },

    async LAY_DS_THIETBI_SUCO(thuebao_id) {
      try {
        let rs = await API.lay_ds_thietbi_suco(this.axios, thuebao_id);
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }
      return null;
    },

    async cboNhomNN_EditValueChanged() {
      let {cboNhomNN} = this.kqxl;
      if (cboNhomNN) {
        await this.loadCbNguyenNhan(0, cboNhomNN, this.tttn.loaihinh_id, this.ttHong.cboNhomTT, 1);
      }
    },

    async cboNhomTon_EditValueChanged() {
      let {cboNhomTon} = this.kqxl;
      if (cboNhomTon) {
        await this.loadCbLyDoTon(0, cboNhomTon, 0, 1);
      }
    },

    async cboTKDichVu_EditValueChanged() {
      try {
        let {cboDichVu, chkDichVu, chkLoaiHinh, cboLoaiHinh} = this.dsbh;
        if (cboDichVu) {
          await this.loadCbLoaiHinhTBTK(cboDichVu);

          if (chkDichVu) {
            if (chkLoaiHinh) {
              if (cboLoaiHinh) {
                await this.filterDsBaoHong('loaitb_id', cboLoaiHinh);
              }
            } else {
              if (cboDichVu) {
                await this.filterDsBaoHong('dichvuvt_id', cboDichVu);
              }
            }
          }
        }
      } catch (ex) {
        console.log("cboTKDichVu_EditValueChanged " + ex);
      }
    },

    async chkTkLoaiHinh_CheckedChanged() {
      try {
        let {cboDichVu, chkDichVu, chkLoaiHinh, cboLoaiHinh} = this.dsbh;
        if (chkLoaiHinh) {
          if (cboLoaiHinh) {
            await this.filterDsBaoHong('loaitb_id', cboLoaiHinh);
          }
        } else {
          if (chkDichVu) {
            if (cboDichVu) {
              await this.filterDsBaoHong('dichvuvt_id', cboDichVu);
            }
          } else {
            this.clearFilterDsBaoHong()
          }
        }
      } catch (ex) {
        this.$toast.error("chkTkLoaiHinh_CheckedChanged: " + ex);
      }
    },

    chkTkDichVu_CheckedChanged() {
      try {
        let {cboDichVu, chkDichVu} = this.dsbh;
        if (chkDichVu) {
          if (cboDichVu) {
            this.filterDsBaoHong('dichvuvt_id', cboDichVu);
          }
        } else {
          this.clearFilterDsBaoHong()
        }
      } catch (ex) {
        this.$toast.error("chkTkDichVu_CheckedChanged: " + ex);
      }
    },

    async cboTKLoaiHinh_EditValueChanged() {
      try {
        let {chkLoaiHinh, cboLoaiHinh} = this.dsbh;
        if (chkLoaiHinh) {
          await this.filterDsBaoHong('loaitb_id', cboLoaiHinh);
        }
      } catch (ex) {
        this.$toast.error("cboTKLoaiHinh_EditValueChanged: " + ex);
      }
    },

    async cbeTK_TTVT_EditValueChanged() {
      try {
        if (this.dsbh.chkTTVT) {
          let {cboTTVT} = this.dsbh;
          console.log(cboTTVT);
          if (cboTTVT == null || cboTTVT.length === 0) {
            this.clearFilterDsBaoHong();
            return;
          }

          await this.filterDsBaoHong('donvi_id', cboTTVT);
        } else {
          this.clearFilterDsBaoHong();
        }
      } catch (ex) {
        this.$toast.error("cbeTK_TTVT_EditValueChanged: " + ex);
      }
    },

    clearFilterDsBaoHong() {
      this.dsBaoHong = this.dsBaoHong_Full;
    },

    async filterDsBaoHong(fieldName, fieldVal) {
      this.loading(true);
      if (fieldName) {
        this.dsBaoHong = this.dsBaoHong_Full.filter(item => {
          if (Array.isArray(fieldVal)) {
            console.log('isArray')
            return fieldVal.includes(item[fieldName]);
          } else {
            return item[fieldName] == fieldVal;
          }
        });
      }
      this.loading(false);
    },

    async cboNhomTT_EditValueChanged() {
      let {cboNhomTT} = this.ttHong;
      if (cboNhomTT) {
        await this.loadCbTinhTrang(0, this.tttn.dichvuvt_id, this.tttn.loaihinh_id, 0, cboNhomTT, '1');
      }
    },

    chkPhieuChoXL_CheckedChanged() {
      if (this.ttHong.chkPhieuChoXL) {
        this.ttHong.chkPhieuTra = false;
        this.ttHong.chkPhieuDaChuyenTinh = false;
        this.ttHong.chkPhieuDaSua = false;
      }
      this.HienThi_DanhSach_Tb_BaoHong();
    },

    chkPhieutra_CheckedChanged() {
      if (this.ttHong.chkPhieutra) {
        this.ttHong.chkPhieuChoXL = false;
        this.ttHong.chkPhieuDaChuyenTinh = false;
        this.ttHong.chkPhieuDaSua = false;
      }
      this.HienThi_DanhSach_Tb_BaoHong();
    },

    showFormTraCuuDanhBa() {
      this.$refs['popupSearchAccount'].show()
    },

    acceptSearchAccount(item) {
      this.$refs['popupSearchAccount'].hide();
      if (item === undefined || item === null) {
        return;
      }

      console.log(item);
      this.tttn.ma_tb = item.ma_tb;
      this.onEnterMaTB();
    },

    async gridTiepNhan_selectedRowChanged(item) {
      this.loading(true);
      this.itemTiepNhanFocused = item;
      let {thuebao_id} = item;
      if (this.tttn.thuebao_id == thuebao_id) {
        this.loading(false);
        return;
      }

      this.tttn.thuebao_id = thuebao_id;
      this.tttn.ma_tb = item.ma_tb;
      let ds2 = await this.LAY_THONGTIN_KH_THEO_THUEBAO_ID(this.tttn.thuebao_id);
      await this.HienThiThongTinThueBao(ds2[0]);

      //Hien thi thong tin tiep nhan hong
      this.ttHong.txtGhiChu = item.ghichu_hong;
      this.tttn.ngay_bh = item.ngay_bh;
      if (item.hensuatu) {
        this.tttn.checkHenTL = true;
        this.tttn.ngay_hentl_tu = moment(item.hensuatu, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss');
        this.tttn.ngay_hentl_den = moment(item.hensuaden, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss');
      } else {
        this.tttn.checkHenTL = false;
        let now = moment().format(this.formatDate);
        this.tttn.ngay_hentl_tu = now;
        this.tttn.ngay_hentl_den = now;
      }

      await this.HienThi_DS_LichSu_BaoHong();
      this.tttn.nguoi_bh = item.nguoi_bh;
      this.tttn.dien_thoai = item.dienthoai_lh;

      if (item.nguoi_cn == "app_myvnpt")
        this.wsappmtv = KENHTN_BAOHONG.KENH_APPMYTV;
      else
        this.wsappmtv = KENHTN_BAOHONG.KENHTONGDAI;

      this.loading(false);
    },

    async KiemTra_TiepNhan_Tam() {
      if (this.kenh_trang) {

      } else {
        if (this.tttn.thuebao_id == 0) {
          this.$root.$toast.error("Bạn hãy nhập mã thuê bao!");
          return false;
        }

        if (this.thuebao_id_tc == 0) {
          this.$root.$toast.error("Không tồn tại thuê bao thi công!");
          return false;
        }
      }

      if (this.tttn.dichvuvt_id == 9 && this.thuebao_id_tc != -1) {
        this.tinh_tc = parseInt(this.cboTinh);
        let dt = await this.sp_lay_thuebaoid_thicong(this.tinh_tc, this.tttn.thuebao_id);
        if (dt == null || dt.length == 0) {
          let dt = await this.sp_lay_thuebaoid_thicong_theotinh(this.tttn.thuebao_id);
          let ten_tinh = '';
          if (dt != null && dt.length > 0)
            for (let i = 0; i < dt.length; i++) {
              ten_tinh += "," + dt[i]["ten_tinh"];
            }

          ten_tinh = ten_tinh.substring(1);
          this.$root.$toast.error("Tỉnh thi công phải chọn " + ten_tinh + "!");
          return false;
        }
      }

      if (this.tinh_tc == 0) {
        this.$root.$toast.error("Bạn hãy chọn tỉnh thi công!");
        return false;
      }

      if (this.KHONG_BAOHONG_PHANHOI_DONGTHOI == 1) {
        let dtKT = await this.SP_DS_KT_KHIEUNAI_TIEPNHANBH(this.tttn.thuebao_id);
        if (dtKT != null && dtKT.length > 0) {
          this.$root.$toast.error("Thuê bao đang có phản hồi khách hàng chưa hoàn thiện. Không thể nhập báo hỏng!");
          return false;
        }
      }

      if (this.kenh_trang) {

      } else if (this.tttn.ma_tb == "") {
        this.$root.$toast.error("Bạn hãy nhập mã thuê bao!");
        return false;
      }

      if (this.tttn.nguoi_bh == "") {
        this.$root.$toast.error("Bạn hãy nhập tên người báo hỏng!");
        return false;
      }

      if (this.tttn.dien_thoai == "") {
        this.$root.$toast.error("Hãy nhập số điện thoại liên hệ.");
        return false;
      }

      if (this.Nhap_ghichu) {
        if (this.ttHong.txtGhiChu == "") {
          this.$root.$toast.error("Hãy nhập ghi chú báo hỏng.");
          return false;
        }

        if (this.tttn.dien_thoai.length < 7) {
          this.$root.$toast.error("Số điện thoại liên hệ không được ít hơn 7 ký tự.");
          return false;
        }

        let _r = new RegExp(/^(\d{10}(,\d{10})*)?$/);
        if (!_r.test(this.tttn.dien_thoai)) {
          this.$root.$toast.error("Số điện thoại liên hệ chưa hợp lệ!");
          return false;
        }
      } else {
        if (this.tttn.dien_thoai.length > 10) {
          this.$root.$toast.error("Số điện thoại liên hệ không được quá 10 ký tự.\nBạn hãy bỏ số 0 ở đầu (nếu có)!");
          return false;
        }
        if (this.tttn.dien_thoai.length < 7) {
          this.$root.$toast.error("Số điện thoại liên hệ không được ít hơn 7 ký tự.");
          return false;
        }

        if (this.BAOHONG_NHIEU_DIENTHOAI_LH == -1) {
          if (!Number.isInteger(this.tttn.dien_thoai)) {
            this.$root.$toast.error("Số điện thoại liên hệ phải là số!");
            return false;
          }
        } else {
          let _r = new RegExp(/^(\d{10}(,\d{10})*)?$/);
          if (!_r.test(this.tttn.dien_thoai)) {
            this.$root.$toast.error("Số điện thoại liên hệ chưa hợp lệ!");
            return false;
          }
        }
      }

      let dtpTuNgay = moment(this.tttn.ngay_hentl_tu, this.formatDate);
      let dtpDenNgay = moment(this.tttn.ngay_hentl_den, this.formatDate);
      let dtpNgayBH = moment(this.tttn.ngay_bh, this.formatDate);
      let {checkHenTL} = this.tttn;
      if (dtpDenNgay.isBefore(dtpTuNgay) && checkHenTL) {
        this.$root.$toast.error("Ngày hẹn phải nhỏ hơn hoặc bằng hạn hẹn!");
        return false;
      }

      let dtkt = await this.lay_ds_hd_chuaht(this.tttn.thuebao_id);
      if (dtkt != null && dtkt.length > 0) {
        this.$root.$toast.error("Thuê bao đang có hợp đồng " + dtkt[0].ten_loaihd + " chưa hoàn thiện! Bạn không thể nhận hỏng!");
      }


      //nhapt 02/10/2017
      //thực hiện kiểm tra ngay tl đến phải lớn hơn ngày báo hỏng
      //if (!KiemTraDK_HoanThanh(dtpNgayBH.Value.ToString("dd/MM/yyyy"), dtpTuNgay.Value.ToString("dd/MM/yyyy")) && chkHenTL.Checked)
      if (dtpTuNgay.isBefore(dtpNgayBH) && checkHenTL) {
        this.$root.$toast.error("Ngày bắt đầu hẹn TL phải không được nhỏ hơn ngày báo hỏng!");
        return false;
      }

      if (this.chkKenhTrang) {
        if (this.tinh_tc == 0) {
          this.$root.$toast.error("Chưa chọn tỉnh thi công!");
          return false;
        }
      }

      return true;
    },

    async SP_DS_KT_KHIEUNAI_TIEPNHANBH(thuebao_id) {
      try {
        let rs = await API.sp_ds_kt_khieunai_tiepnhanbh(this.axios, thuebao_id);
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }

      return null;
    },

    async tsbtnGhiLai_Click() {
      this.loading(true);
      try {
        let _matb = this.tttn.ma_tb;

        //Kiểm tra thuê bao tiếp nhận
        if (!await this.KiemTra_TiepNhan_Tam())
          return;

        let thuebao_id = this.tttn.thuebao_id;
        let baohong_id = this.baohong_id;
        let kt = await this.KIEMTRA_THUEBAO_BAOHONG(thuebao_id, 2, this.daucuoi_id);
        if (kt != "1") {
          //vinhpv
          let d = this.sp_lay_gt_thamso_md_tents('CAPNHAT_SOLAN_BAOHONG_119', '1');
          if (d != null) {
            await this.popuptientrinh(thuebao_id, kt, this.tttn.dichvuvt_id);
          } else {
            this.$root.$toast.error("btnGhiLai_Click: " + kt);
          }

          return;
        }
        if (this.PHAILAM("NHANHONG_TINHTRANG")) {
          //Kiểm tra xem đã chọn tình trạng chưa
          if (this.ttHong.cboTinhTrang == null || this.ttHong.cboTinhTrang.length <= 0) {
            this.$root.$toast.error("Hãy nhập tình trạng hỏng!");
            return;
          }
        }

        await this.TaoDuLieu_BaoHong(true, this.tttn.thuebao_id, false);
        await this.TaoDuLieu_BaoHong_DV(true);
        //
        if (this.PHAILAM("NHANHONG_TINHTRANG")) {
          this.TaoDuLieu_tinhtrang(true);
          this.TaoDuLieu_nguyennhan(true);
          this.TaoDuLieu_baoton(true);
        }

        let kq = await this.fn_capnhat_tiepnhanbh_bancheo(
          {
            HUONGGIAOTN_ID: this.huonggiao_id,
            KHACHHANG_ID: this.khachhang_id,
            DACTRUNG: this.tttn.dactrung,
            NGUOI_CN: this.$root.token.getUserName(),
            MAY_CN: await this.$root.token.getMachine()
          },
          this.dsBH_DATA,
          this.dsBH_DV,
          this.dsBH_BC_DATA
        );

        if (kq != '1') {
          this.$root.$toast.error('Cập nhật Báo hỏng bán chéo không thành công');
          return;
        }

        this.CapNhat_DonVi_PhoiHop(baohong_id);
        if (this.PHAILAM("NHANHONG_TINHTRANG")) {
          let vphieu_id = await this.newMapData(
            "/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieubh",
            {param: baohong_id, type: 2, param1: this.huonggiao_id});


          for (let j = 0; j < this.ds_nn.length; j++) {
            this.ds_nn[j]["phieu_id"] = vphieu_id;
          }

          await this.Insert_tinhtrang(baohong_id, vphieu_id, this.ds_tt, this.ds_nn, this.ds_ton, "", 0);
        }

        this.giaophieu_tudong = false;
        if (this.PHAILAM("DIEUKIEN_GIAOPHIEU_BP_TIEPTHEO") && !this.nhanhong_khoamay) {
          let ds = await this.Lay_dieukien_gp_khi_nhanhong(baohong_id, this.$root.token.getDonViID(), this.$root.token.getNhanVienID());
          if (ds != null)
            if (ds.length > 0) {
              if (parseInt(ds[0]["giaophieu_td"]) === 1)
                this.giaophieu_tudong = true;
              if (parseInt(ds[0]["giaoviec_td"]) === 1)
                this.Giaoviec_td = true;
              else if (parseInt(ds[0]["giaoviec_td"]) === 0)
                this.Giaoviec_td = false;
            }
        }

        //Xóa bảng tạm
        API.sp_xoa_baohong_tmp_tiepnhanbh(this.axios, {vthuebao_id: thuebao_id});
        API.sp_delete_baohong_tmp_bc(this.axios, {vthuebao_id: thuebao_id});

        try {
          new API.capnhat_baohong_khuvuc_nv(this.axios, {vthuebao_id: thuebao_id});
        } catch (e) {
        }

        this.$root.$toast.success("Tiếp nhận thông tin báo hỏng thành công!");
        await this.HienThi_DanhSach_Tb_BaoHong();
        await this.HienThi_Danhsach_Tiepnhan();

        if (this.PHAILAM("TUDONG_GIAOPHIEU_HC") &&
          (this.giaophieu_bdg || this.giaophieu_tudong) && !this.nhanhong_khoamay) {//Tự động giao phiếu
          let tb = await this.LayHuongGiao_BaoHong_TheoThuTu(this.tttn.loaihinh_id, 2);
          let hg = 0, qt = 0;
          if (tb != null && tb.length > 0) {
            hg = parseInt(tb[0]["huonggiao_id"]);
            qt = parseInt(tb[0]["quytrinh_id"]);
          } else return;

          let ds_tmp = await this.LAY_DS_BAOHONG_CHUAGIAO(hg, this.$root.token.getDonViID(), this.$root.token.getNhanVienID(), this.tttn.ma_tb);
          if (ds_tmp[0].length == 0) return;

          let dt = ds_tmp.filter(item => item.baohong_id = baohong_id);
          let donvi_nhan_id = parseInt(dt[0]["donvi_id"]);
          baohong_id = parseInt(dt[0]["baohong_id"]);
          let phieu_cha_id = parseInt(dt[0]["PHIEU_ID"]);
          let ttbh_id = parseInt(dt[0]["ttbh_id"]);
          let dsGiaoPhieu = await this.AddphieuTH(baohong_id, this.$root.token.getDonViID(), donvi_nhan_id, hg, 0, phieu_cha_id, ttbh_id);

          API.sp_giaophieu_baohong_tiepnhan_bh_bancheo(this.axios, {dsgiaophieu: dsGiaoPhieu, vkt_daucuoi: 0});

          this.$root.$toast.success("Giao phiếu cho bộ phận tiếp theo thành công!");

          for (const row of this.dsHuongGiao) {
            if (row["huonggiao_id"] == hg && row["giaoviec"] == 1) {
              this.Giaoviec_td = true;
              break;
            }

            this.Giaoviec_td = false;
          }

          if (this.Giaoviec_td)
            API.tudong_giaophieu_nv(this.axios, {
              "vphieu_id": this.PHIEU_ID,
              "vnhanvien_id": this.$root.token.getNhanVienID(),
              "vnhiemvu": "Kiểm tra và xử lý"
            });
        }

        this.clear();
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },

    /// <summary>
    /// Hàm đo kiểm trước khi nghiệm thu
    /// </summary>
    /// <returns>OK: Pass, ngược lại: hiển thị thông báo lỗi</returns>
    async DoKiem() {
      try {
        let iServer_DK;
        try {
          iServer_DK = await this.LAY_THAMSO_MACDINH("SERVER_DK", this.$root.token.getNguoiDungID());
        } catch (e) {
          iServer_DK = 1;
        }

        if (this.userInfo.att_adsl <= 0 && this.userInfo.att_gpon <= 0 && this.userInfo.snr_adsl <= 0)
          return "OK";
        if (this.vuser_alt == "" || this.vpass_alt == "")
          return "OK";
        //Đo kiểm trước khi nghiệm thu
        if (this.tttn.dichvuvt_id != DichVuVienThong.ADSL)
          return "OK";

        let vInput = this.tttn.ma_tb;
        let vthuebaoID = this.tttn.thuebao_id;
        let vdichvuvtID = this.tttn.dichvuvt_id;

        let dtPort = await this.LAY_PORT_THEO_TBID(-1, vthuebaoID);
        if (dtPort == null || dtPort.length === 0) {
          return "Thuê bao chưa được cấp port!";
        }

        let vip = dtPort[0]["ip"];
        let vport_adsl = dtPort[0]["port_adsl"];
        let vport_visa = dtPort[0]["port_visa"];
        let vonu_id = dtPort[0]["onu_id"];
        vInput = vip + ":" + vport_visa;
        if (vonu_id != "")
          vInput += "/" + vonu_id;
        if (vip == "") {
          return "Thuê bao chưa được cấp port!";
        }

        let kq = "";
        let iServer_DK_BNM = iServer_DK;
        if (iServer_DK_BNM == 3) {
          let vloai_gpon = dtPort[0]["loai_gpon"];
          let vport_bnm = dtPort[0]["port_bnm"];
          vInput = vip + ":" + vport_bnm + ":" + vloai_gpon + ":" + dtPort[0]["loaitbi_id"];
          kq = await this.bnm_getStatusPort(vInput);
        } else {
          kq = await this.getPortStatus(vInput, vdichvuvtID);
        }

        if (kq != "")
          return kq;

        return "OK";
      } catch (e) {
        return "OK";
      }
    },

    async getPortStatus(vInput, vdichvuvt_id) {
      try {
        if (this.vuser_alt == "" || this.vpass_alt == "") return "";
        //Đo kiểm trước khi nghiệm thu
        if (vdichvuvt_id != DichVuVienThong.ADSL)
          return "";

        let rs = await API.bnm_GetPortStatus(this.axios, {
          "input": vInput,
          "linetestPassword": this.vpass_alt,
          "linetestUser": this.vuser_alt,
          "mac": "0"
        });

        return this.getDataFromAPI(rs);
      } catch (ex) {
        return ex;
      }
    },

    async TaoDuLieu_BaoHong(themmoi, thuebao_id, chuyentinh) {
      let row = Utils.BAOHONG_DATA();

      if (themmoi) {
        if (chuyentinh) {
          this.ma_bh = this.itemDanhSachFocused.ma_bh;
          this.baohong_id = await this.POST_GET_KEYS_BANCHEO("BAOHONG", this.tinh_tc || this.tinh.tinhthicong_id);
          row.BAOHONG_ID_HNI = this.baohong_id;
        } else {
          let baohong_id = await this.POST_GET_KEYS('BAOHONG');
          this.baohong_id = parseInt(baohong_id);

          let ma_tinh = "";
          ma_tinh = await this.newMapData('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tinh',
            {param: this.$root.token.getPhanVungID(), type: 1});

          let key_ma_bh = await this.POST_GET_KEYS('MA_BH');
          this.ma_bh = "BH_" + ma_tinh + "_" + key_ma_bh;
        }
      }

      let dsBH_DATA = [];
      // let thuebao_id = this.thuebao_id_tc;
      let now = moment().format(this.formatDate);
      row.BAOHONG_ID = this.baohong_id;
      row.THUEBAO_ID = thuebao_id;
      row.DICHVUVT_ID = this.tttn.dichvuvt_id;
      row.MA_LT = this.ma_lt;
      row.TEN_TB = this.tttn.ten_tb;
      row.DIACHI_LD = this.tttn.diachi_ld;
      row.LOAITB_ID = this.tttn.loaihinh_id;

      row.NGAY_BH = now;
      row.NGUOI_BH = this.tttn.nguoi_bh;
      row.DIENTHOAI_LH = this.tttn.dien_thoai;
      row.GHICHU_HONG = this.ttHong.txtGhiChu;
      row.HT_PORT = this.s == "OK" ? "" : this.s;//nhapt themmoi; hình thức port 08062017
      row.GOIKT_ID = await this.lay_goikt_id(thuebao_id, -1);
      if (this.$root.token.getPhanVungID() == 26) {
        row.CHITIEU_TG = await this.lay_chitieu_tg(thuebao_id, -1);
      }
      row.TTBH_ID = TRANGTHAI_BH.MOI_TIEPNHAN;//Trạng thái mới

      if (this.tttn.checkHenTL) {
        row.HENSUATU = this.tttn.ngay_hentl_tu;
        row.HENSUADEN = this.tttn.ngay_hentl_den;
      }
      row.MAY_CN = this.$root.token.getMachine();
      row.NGAY_CN = now;
      row.NGUOI_CN = this.$root.token.getUserName();
      if (chuyentinh) {
        let tinh = await this.tinh_thicong(this.tinh_tc);
        row.NHANVIEN_ID = tinh.nhanvien_id;
        row.DONVI_ID = tinh.donvi_id;
      } else {
        row.NHANVIEN_ID = this.$root.token.getNhanVienID();
        row.DONVI_ID = this.$root.token.getDonViID();
      }

      // Lấy hướng giao tiếp nhận báo hỏng
      let dtQT = await this.LayHuongGiao_BaoHong_TheoThuTu(this.tttn.loaihinh_id, 1);
      if (dtQT) {
        this.quytrinh_id = dtQT[0]["quytrinh_id"];
        this.huonggiao_id = dtQT[0]["huonggiao_id"];
      }

      row.QUYTRINH_ID = this.quytrinh_id;
      row.KENHTN_ID = 2;
      row.MA_BH = this.ma_bh;
      dsBH_DATA.push(row);
      this.dsBH_DATA = [...dsBH_DATA];

      let dsBH_BC_DATA = [];
      let row_bc = Utils.BAOHONG_BC_DATA();
      if (chuyentinh) {
        row_bc.LOAIHD_BANCHEO = 2;
      } else {
        row_bc.LOAIHD_BANCHEO = 1;
      }

      row_bc.TINH_THICONG = this.tinh_tc;
      row_bc.THUEBAO_ID_BAN = thuebao_id;
      row_bc.MA_TB = this.tttn.ma_tb;
      if (this.kenh_trang)
        row_bc.THUEBAO_ID_THICONG = -1;
      else
        row_bc.THUEBAO_ID_THICONG = this.thuebao_id_tc;

      row_bc.TINH_BAN = this.$root.token.getPhanVungID();
      row_bc.TRANG_THAI = 0;
      if (chuyentinh)
        row_bc.BAOHONG_ID_THICONG = this.baohong_id;
      else
        row_bc.BAOHONG_ID = this.baohong_id;
      row_bc.DAUCUOI_ID = this.daucuoi_id;

      dsBH_BC_DATA.push(row_bc);
      this.dsBH_BC_DATA = [...dsBH_BC_DATA];

      this.thietlap_thaotac();
      return {dsBH_DATA, dsBH_BC_DATA};
    },

    async TaoDuLieu_BaoHong_DV(themmoi) {
      let dsBH_DV = [];
      let dtDV = await this.Lay_ds_donvi_theo_loaidv_bh(this.tttn.thuebao_id, "432");
      let tram_id = 0;
      if (dtDV != null) {
        for (const item of dtDV) {
          let row = Utils.BAOHONG_DV_DATA();
          tram_id = parseInt(item["donvi_id"]);
          row.BAOHONG_ID = this.baohong_id;
          row.DONVI_ID = tram_id;
          row.LOAIDV_ID = 5;
          row.KIEUDV_ID = item.kieudv_id;
          dsBH_DV.push(row);
        }
      }

      this.dsBH_DV = [...dsBH_DV];
      return dsBH_DV;
    },

    TaoDuLieu_tinhtrang(themmoi) {
      let ds_tt = [];
      let {cboTinhTrang} = this.ttHong
      if (cboTinhTrang != null) {
        for (const tinhTrangId of cboTinhTrang) {
          let row = Utils.TINHTRANG_BH_DATA();
          row.BAOHONG_ID = this.baohong_id;
          row.TINHTRANG_ID = tinhTrangId;
          ds_tt.push(row);
        }
      }

      this.ds_tt = [...ds_tt];
      return ds_tt;
    },

    TaoDuLieu_nguyennhan(themmoi) {
      let ds_nn = [];
      if (this.kqxl.cboKQXL == 0) {// Nếu xử lý được thì phải chọn nguyên nhân
        for (const nnId of this.kqxl.cboNguyenNhan) {
          let row = Utils.NGUYENNHAN_BH_DATA();
          row.PHIEU_ID = this.PHIEU_ID;
          row.CTHONG_ID = nnId;
          if (this.kqxl.cboNhomNN == LOAIHONG.HONG_THIETBI_DAUCUOI && this.tttn.dichvuvt_id == DichVuVienThong.ADSL)
            row.HANGSX_ID = this.kqxl.cboHangSX;
          ds_nn.push(row);
        }
      }

      this.ds_nn = [...ds_nn];
      return ds_nn;
    },

    TaoDuLieu_baoton(themmoi) {
      let ds_ton = [];
      if (this.kqxl.cboKQXL == 1) {// Nếu không xử lý được thì phải chọn lý do tồn
        for (const tonId of this.kqxl.cboLyDoTon) {
          let row = Utils.BAOHONG_TON_DATA();
          row.BAOHONG_ID = this.baohong_id;
          row.CTTON_ID = tonId;
          ds_ton.push(row);
        }
      }

      this.ds_ton = [...ds_ton];
      return ds_ton;
    },

    async HIEU(vbaohong_id, vthuebao_id, _matb) {
      let kt = await this.fn_capnhat_goto_tiepnhanbh(vbaohong_id, vthuebao_id)
      if (kt != '1') {
        this.$toast.error('HIEU: ' + kt);
      }

      await this.HienThi_DanhSach_Tb_BaoHong();
      await this.HienThi_Danhsach_Tiepnhan();
      this.clear();

      // let ids = [];
      // if (this.dsBaoHong.length === 1) {
      //   ids.push(0);
      // } else if (this.dsBaoHong.length > 1) {
      //   this.dsBaoHong.forEach((item, index) => {
      //     if (item.ma_tb === _matb) {
      //       ids.push(index);
      //     }
      //   })
      // }
      //
      // this.$refs.dsBaoHong.setSelectedRows(ids);
    },

    tudong_giaophieu_nv(vphieu_id, vnhanvien_id, vnhiemvu) {
      try {
        API.tudong_giaophieu_nv(this.axios, {
          vphieu_id,
          vnhanvien_id,
          vnhiemvu,
        });
      } catch (e) {
        console.log(e);
      }
    },

    async LayDS_SDT_DonViNhan(vdonvi_nhan_id, _vthuebao_id, _matb, _vnoidung, _vphieuid, _vhuonggiao_id) {
      let _StringsoDT = "", _StringsoDT_nv = "";
      let i = 0, j = 0;
      if (this.IS_HPG == 1) {
        this.dtTTCap = await this.sp_ds_vi_nhanvien_theocap(this.axios, _vthuebao_id);
        if (this.dtTTCap != null && this.dtTTCap.length > 0) {
          _StringsoDT = this.dtTTCap[0].telephone;
        }
      } else {
        _StringsoDT = await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi",
          {param: vdonvi_nhan_id, type: 6});
        _StringsoDT = _StringsoDT.replaceAll(/\s+/g, "");
        //Nếu giao việc tự động cho nhân viên
        if (this.Giaoviec_td) {
          //Lấy số điện thoại của nhân viên dây máy quản lý thuê bao
          let dset = await this.lay_sodt_nhanvien_khoan_tn(_vthuebao_id);
          if (dset && dset.length > 0)
            while (true) {
              _StringsoDT_nv = _StringsoDT_nv + dset[j]["so_dt"] + ",";
              j++;
              if (j >= dset.length) break;
            }
          else {
            this.$root.$toast.error("Thuê bao " + _matb + " chưa được gán nhân viên kỹ thuật. Hãy liên hệ với quản trị chuẩn hóa trước khi giao phiếu");
            return false;
          }
        }
      }

      if (_StringsoDT == "-1" || _StringsoDT == "") {
        if (_StringsoDT_nv != "")
          _StringsoDT = _StringsoDT_nv;
      } else
        _StringsoDT = _StringsoDT + "," + _StringsoDT_nv;

      //Gán số điện thoại vào datable
      if (_StringsoDT != null && _StringsoDT != '' && _StringsoDT != "-1") {
        if (_StringsoDT.endsWith(","))
          _StringsoDT = _StringsoDT.slice(0, -1);
        let _soDT = _StringsoDT.split(',');
        //Tạo bảng để lưu dl
        let _dtSoDT_Donvi = [];
        while (true) {
          let _dr = {
            SO_DT: _soDT[i],
            DONVI_NHAN_ID: vdonvi_nhan_id,
            NOIDUNG: _vnoidung,
            THUEBAO_ID: _vthuebao_id,
            MA_TB: _matb,
            PHIEU_ID: _vphieuid,
            HUONGGIAO_ID: _vhuonggiao_id
          };

          _dtSoDT_Donvi.push(_dr);
          this.dsSoDT_Dvi = [..._dtSoDT_Donvi];
          i++;
          if (i >= _soDT.length) break;
        }
      }

      return true;
    },
    async POST_GET_KEYS(keyname) {
      let key = ''
      await API.POST_GET_KEYS(this.axios, {
        keyname: keyname
      }).then(response => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          key = response.data.data
        }
      }).catch(err => {
        this.$root.$toast.error('POST_GET_KEYS: ' + err.data.message_detail);
      });
      return key
    },

    async POST_GET_KEYS_BANCHEO(keyname, tinhthicong_id) {
      let key = ''
      await API.getKeyBanCheo(this.axios, {
        keyname,
        tinhthicong_id
      }).then(response => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          key = response.data.data
        }
      }).catch(err => {
        this.$root.$toast.error('POST_GET_KEYS_BANCHEO: ' + err.data.message_detail);
      });
      return key
    },

    async lay_goikt_id(vthuebao_id, vhdtb_id) {
      try {
        let rs = await API.lay_goikt_id(this.axios, {
          vthuebao_id,
          vhdtb_id
        });

        return rs.data.data;
      } catch (e) {
        console.log(e);
      }

      return 0;
    },

    async lay_chitieu_tg(vthuebao_id, vhdtb_id) {
      try {
        let rs = await API.lay_chitieu_tg(this.axios, {
          vthuebao_id,
          vhdtb_id
        });

        return rs.data.data;
      } catch (e) {
        console.log(e);
      }

      return 0;
    },

    async Lay_ds_donvi_theo_loaidv_bh(vthuebao_id, vloaidv_id) {
      try {
        let rs = await API.lay_ds_donvi_theo_loaidv_bh(this.axios, {
          vthuebao_id,
          vloaidv_id
        });

        return this.getDataFromAPI(rs) || [];
      } catch (e) {
        console.log(e);
      }

      return [];
    },

    async Lay_ds_donvi_theo_loaidv_bh_BanCheo(tinhthicong_id, vthuebao_id, vloaidv_id) {
      try {
        let rs = await API.lay_ds_donvi_theo_loaidv_bh_BanCheo(this.axios, {
          tinhthicong_id,
          vthuebao_id,
          vloaidv_id
        });

        return this.getDataFromAPI(rs) || [];
      } catch (e) {
        console.log(e);
      }

      return [];
    },

    async Lay_donvi_bosung_theo_thuebao_id(vthuebao_id) {
      try {
        let rs = await API.lay_donvi_bosung_theo_thuebao_id(this.axios, {
          vthuebao_id,
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }

      return null;
    },

    //Bao hong ao
    async btnBaoHongAo_Click() {
      try {
        let bhAo = this.KiemTra_BH_Ao();
        if (!bhAo) return;

        this.loading(true);
        await this.TaoDuLieu_BaoHong_Ao(true);
        this.TaoDuLieu_BaoHong_Ao_DV(true);

        API.sp_baohong_ao_tiepnhanbh(this.axios, {
          "dsbh_data": JSON.stringify(this.dsBH_DATA),
          "dsbh_dv": JSON.stringify(this.dsBH_DV),
          "vhuongiaotn_id": this.huonggiao_id
        }).then(rs => {
          if (rs.data.error === '200') {
            this.HienThi_DanhSach_Tb_BaoHong();
            this.HienThi_Danhsach_Tiepnhan();
            this.$toast.success("Tiếp nhận thông tin báo hỏng thành công!");
          } else {
            this.$root.$toast.error('sp_baohong_ao_tiepnhanbh: ' + rs.data.message_detail);
          }

          this.loading(false);
        }).catch(err => {
          console.log(err);
          this.loading(false);
        })

        this.clear();
      } catch (e) {
        console.log(e);
      }
    },

    KiemTra_BH_Ao() {
      if (this.tttn.nguoi_bh == "") {
        this.$root.$toast.error("Bạn hãy nhập tên người báo hỏng!");
        return false;
      }

      if (this.tttn.dien_thoai == "") {
        this.$root.$toast.error("Hãy nhập số điện thoại liên hệ.");
        return false;
      }

      if (this.tttn.dien_thoai.length > 10) {
        this.$root.$toast.error("Số điện thoại liên hệ không được quá 10 ký tự.\nBạn hãy bỏ số 0 ở đầu (nếu có)!");
        return false;
      }

      if (this.tttn.dien_thoai.length < 7) {
        this.$root.$toast.error("Số điện thoại liên hệ không được ít hơn 7 ký tự.");
        return false;
      }

      if (Number.isInteger(this.tttn.dien_thoai)) {
        this.$root.$toast.error("Số điện thoại liên hệ phải là số!");
        return false;
      }

      let dtpTuNgay = moment(this.tttn.ngay_hentl_tu, this.formatDate);
      let dtpDenNgay = moment(this.tttn.ngay_hentl_den, this.formatDate);
      let dtpNgayBH = moment(this.tttn.ngay_bh, this.formatDate);
      let {checkHenTL} = this.tttn;

      if (dtpDenNgay.isBefore(dtpTuNgay) && checkHenTL) {
        this.$root.$toast.error("Ngày hẹn phải nhỏ hơn hoặc bằng hạn hẹn!");
        return false;
      }

      if (dtpTuNgay.isBefore(dtpNgayBH) && checkHenTL) {
        this.$root.$toast.error("Ngày bắt đầu hẹn TL phải không được nhỏ hơn ngày báo hỏng!");
        return false;
      }

      return true;
    },

    async TaoDuLieu_BaoHong_Ao(themmoi) {
      let dsBH_DATA = [];

      if (themmoi) {
        let baohong_id = await this.POST_GET_KEYS("BAOHONG");
        this.baohong_id = parseInt(baohong_id);
      }

      let row = Utils.BAOHONG_DATA();
      row.BAOHONG_ID = this.baohong_id;
      row.THUEBAO_ID = 0; //0: Báo hỏng ảo
      row.DICHVUVT_ID = 4; // Fix "Băng rộng cố định"
      //row.MA_LT = ma_lt;
      row.LOAITB_ID = 11; // Fix "MegaVNN"
      row.NGAY_BH = this.tttn.ngay_bh;//tt_nd.ngay_cn;
      row.NGUOI_BH = this.tttn.nguoi_bh;
      row.DIENTHOAI_LH = this.tttn.dien_thoai;
      row.GHICHU_HONG = this.ttHong.txtGhiChu;
      row.TTBH_ID = TRANGTHAI_BH.MOI_TIEPNHAN;
      row.DONVI_ID = this.$root.token.getDonViID();
      if (this.tttn.checkHenTL) {
        row.HENSUATU = this.tttn.ngay_hentl_tu;
        row.HENSUADEN = this.tttn.ngay_hentl_den;
      }

      row.MAY_CN = this.$root.token.getMachine();
      row.NGAY_CN = moment().format(this.formatDate);
      row.NGUOI_CN = this.$root.token.getUserName();
      row.NHANVIEN_ID = this.$root.token.getNhanVienID();

      row.QUYTRINH_ID = this.quytrinh_id;
      row.NGUOI_TAO = this.$root.token.getUserName();
      row.MA_BH = moment().format("yyyyMMddHHmmss") + "AO";
      dsBH_DATA.push(row);

      if (!themmoi) {
        // dsBH_DATA.AcceptChanges();
        // dsBH_DATA.BAOHONG.Rows[0].SetModified();
      }

      this.dsBH_DATA = [...dsBH_DATA];
      return dsBH_DATA;
    },

    TaoDuLieu_BaoHong_Ao_DV(themmoi) {
      let dsBH_DV = [];
      let row = Utils.BAOHONG_DV_DATA();
      row.BAOHONG_ID = this.baohong_id;
      row.DONVI_ID = this.$root.token.getDonViID();
      row.LOAIDV_ID = 0;
      row.KIEUDV_ID = 0;
      dsBH_DV.push(row);

      if (!themmoi) {
        // dsBH_DV.AcceptChanges();
        // dsBH_DV.BAOHONG_DV.Rows[0].SetModified();
      }

      this.dsBH_DV = [...dsBH_DV];
      return dsBH_DV;
    },

    //Cap nhat do thu
    async tsbtnCapNhatDoThu_Click() {
      try {
        if (this.baohong_id == 0) {
          this.$root.$toast.error("Hãy chọn đợt báo hỏng!");
          return;
        }
        if (this.ttHong.cboNguoiTH == "") {
          this.$root.$toast.error("Hãy chọn nhân viên thực hiện!");
          return;
        }

        if (!this.ttHong.chkNgayTH) {
          this.$root.$toast.error("Hãy nhập ngày thực hiện!");
          return;
        }

        //Kiểm tra xem đã chọn tình trạng chưa
        if (this.ttHong.cboTinhTrang == null || this.ttHong.cboTinhTrang.length <= 0) {
          this.$root.$toast.error("Hãy nhập tình trạng hỏng!");
          return;
        }

        this.TaoDuLieu_tinhtrang(true);
        this.TaoDuLieu_nguyennhan(true);
        this.TaoDuLieu_baoton(true);

        let kt = await this.sp_capnhat_dothu_tiepnhanbh_bancheo();
        if (kt == '1') {
          await this.HienThi_DanhSach_Tb_BaoHong();
          this.$root.$toast.success("Cập nhật dữ liệu thành công!");
        } else {
          this.$root.$toast.error('sp_capnhat_dothu_tiepnhanbh_bancheo: ' + kt);
        }
      } catch (e) {
        console.log(e);
      }
    },

    async KIEMTRA_PHIEU(danhsach, p_id, kieu) {
      try {
        if (danhsach != "0") {
          let ds_temp = danhsach;
          if (danhsach == "-1") {
            let dt = [...this.dsBaoHong];
            if (dt == null || dt.length == 0) {
              this.$root.$toast.error("Không có danh sách phiếu, không thể thực hiện");
              return false;
            }

            let dt_chon = this.$refs.dsBaoHong.getSelectedRecords();
            if (dt_chon.length == 0) {
              this.$root.$toast.error("Chưa chọn danh sách phiếu để giữ không thể thực hiện");
              return false;
            }

            let dt_kt = [];
            for (const kt of dt_chon) {
              dt_kt.push({
                phieu_id: kt.phieu_id,
                baohong_id: kt.baohong_id
              })
            }

            //Nếu có nhân viên giữ của 1 phiếu bất kỳ return
            ds_temp = JSON.stringify(dt_kt);
          }

          let rs = await API.sp_lay_soluong_phieu_dagiu_theo_nv(this.axios, {
            "json_danhsach": ds_temp,
            "kieu": kieu,
            "nhanvien_id": this.$root.token.getNhanVienID()
          })

          let kiemtra = this.getDataFromAPI(rs);
          if (kiemtra.length > 0 && kiemtra[0].sl !== 0) {
            this.$root.$toast.error("Trong danh sách có phiếu đã có người khác giữ bạn không thể thao tác");
            return false;
          }

          return true;
        }

        if (p_id != 0) {
          let rs = await API.sp_lay_soluong_phieu_dagiu_theo_nv_v2(this.axios, {
            "phieu_id": p_id,
            "nhanvien_id": this.$root.token.getNhanVienID()
          })

          let kt = this.getDataFromAPI(rs);

          if (kt.length > 0 && kt[0].sl !== 0) {
            this.$root.$toast.error("Trong danh sách có phiếu đã có người khác giữ bạn không thể thao tác");
            return false;
          }

          return true;
        } else {
          return false;
        }
      } catch (e) {
        this.$root.$toast.error("Có lỗi khi kiểm tra " + e);
        return false;
      }
    },

    //Giao Phieu
    async HienThi_DanhSach_Tb_BaoHong_Nhanhkhac(_thuebao_id, _baohong_id) {
      let ma_tinh = this.$root.token.getPhanVungID();
      if (_thuebao_id != 0 && _baohong_id != 0) {
        this.tttn.thuebao_id = _thuebao_id;
        this.baohong_id = _baohong_id;
      }

      let rs = await API.lay_ds_bh_nhanhkhac(this.tttn.thuebao_id, this.baohong_id);
      let dtBH = this.getDataFromAPI(rs);
      if (dtBH != null && dtBH.length > 0) {
        try {
          let dtBH_tmp = [];
          for (let i = 0; i < dtBH.length; i++) {
            if (dtBH[i]["dichvuvt_id"] == "9") {
              let bh_id = dtBH[i]["baohong_id"];
              let bhbc = await this.sp_ds_baohong_bc(bh_id);
              if (bhbc != null) {
                if (dtBH[i]["kieudv_id"] != bhbc[0]["daucuoi_id"])
                  continue;
              }
            }

            dtBH_tmp.push(dtBH[i]);
          }

          dtBH = [...dtBH_tmp];
        } catch (e) {
        }

        this.dsBaoHong = dtBH;

        if (dtBH.length <= 0) {
          this.ttHong = {
            ...this.ttHong,
            cboNguoiTH: this.$root.token.getNhanVienID(),
            ngayTH: moment().format(this.formatDate),
            txtGhiChu: ''
          };
          this.kqxl.txtGhiChu = '';
          this.clear();
        }
      } else {
        this.dsBaoHong = [];
        this.clear()
      }
    },

    async tsbtnXuatPhieu_Click() {
      try {
        this.loading(true);
        if (this.dsBaoHong.length <= 0) {
          this.$root.$toast.error("Không có thuê bao báo hỏng để giao phiếu!");
          return;
        }

        let baohong_id_ban = this.itemDanhSachFocused.baohong_id;
        let vphieu_id = this.itemDanhSachFocused.phieu_id;
        let vloaitb_id = this.itemDanhSachFocused.loaitb_id;

        let dt = await this.sp_ds_baohong_bc_loaihd(baohong_id_ban);
        if (dt.length > 0) {
          let obj = dt[0];
          this.tinh_tc = obj.tinh_thicong;
          this.thuebao_id_tc = obj.thuebao_id_thicong;
          this.tinh = await this.tinh_thicong(this.tinh_tc);
          if (this.tinh_tc != 0 && this.thuebao_id_tc > 0) {
            let kt = await this.kiemtra_thuebao_baohong_119(this.tinh, this.thuebao_id_tc, 2);
            if (kt != "1") {
              this.$root.$toast.error(kt);
              return;
            }
          }

          switch (dt[0]["Trang_thai"]) {
            case "2":
              this.$root.$toast.success("Đã chuyển thông tin báo hỏng cho tỉnh!");
              return;
              break;
          }
        }

        let dichvuvt_id = parseInt(this.itemDanhSachFocused.dichvuvt_id);
        await this.TaoDuLieu_BaoHong(true, this.thuebao_id_tc, true);

        let baohong_id_tc = 0;
        if (this.dsBH_DATA.length > 0) {
          baohong_id_tc = this.dsBH_DATA[0].BAOHONG_ID;
          await API.sp_update_baohong_bc_baohong_id(this.axios, {
            vbaohong_id_tc: baohong_id_tc,
            vbaohong_id_ban: baohong_id_ban
          });
        }

        if (this.dsBH_BC_DATA.length > 0)
          this.dsBH_BC_DATA[0].BAOHONG_ID = baohong_id_ban;

        if (dichvuvt_id == DichVuVienThong.MEGAWAN || dichvuvt_id == DichVuVienThong.METRONET) {
          let loaikenh_id = await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_mgwan", {
            param: this.tttn.thuebao_id,
            type: 2
          });
          if (loaikenh_id == "2" || loaikenh_id == "3" ||
            loaikenh_id == "4" || loaikenh_id == "5"
            || vloaitb_id == LoaiHinhTB.MGW_INTERNET_TRUCTIEP) {
            let net_id = await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_dm_tinh_ketnoi", {
              param: this.tinh_tc,
              type: 1
            });
            if (net_id != "-1") {
              let dtPhieuDaGiao = await this.sp_ds_giaophieu_baohong_huonggiao(baohong_id_ban, net_id);
              if (dtPhieuDaGiao.length == 0)
                await this.GiaoPhieu_BH(baohong_id_ban,
                  this.$root.token.getDonViID(),
                  parseInt(net_id),
                  this.$root.token.getNhanVienID(),
                  7780,
                  vphieu_id,
                  1,
                  moment().format(this.formatDate),
                  this.$root.token.getUserName(),
                  this.$root.token.getMachine(),
                  this.ttHong.txtGhiChu);
            }
          }
        }

        this.CapNhat_DonVi_PhoiHop(baohong_id_ban);
        await this.Nhan_hong(this.tinh,
          this.dsBH_DATA,
          this.dsBH_BC_DATA,
          parseInt(this.tttn.loaihinh_id),
          1,
          this.tttn.dactrung,
          this.$root.token.getMachine(),
          moment().format(this.formatDate),
          this.$root.token.getUserName(),
          this.thuebao_id_tc,
          baohong_id_ban,
          0);

        await API.sp_update_baohong_bc_trangthai(this.axios, {
          "vbaohong_id_ban": baohong_id_ban,
          // "vtrangthai_id": 2
        })

        if (baohong_id_tc != 0) {
          API.bancheo_sms_baohong(this.axios, {vbaohong_id: baohong_id_tc, tinhthicong_id: this.tinh_tc});
          let vnhanvien_id = await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_dm_tinh_ketnoi", {
            param: this.tinh_tc,
            type: 2
          });
          let vdonvi_id = await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_dm_tinh_ketnoi", {
            param: this.tinh_tc,
            type: 3
          });
          if (this.thuebao_id_tc != -1)
            try {
              API.bancheo_giao_donvi_tieptheo(this.axios, {
                "vbaohong_id": baohong_id_tc,
                "vloaitb_id": vloaitb_id,
                "vthuebao_id": this.thuebao_id_tc,
                donvi_id: vdonvi_id,
                nhanvien_id: vnhanvien_id,
                tinhthicong_id: this.tinh_tc
              });
            } catch (e) {
              console.log()
            }
        }

        await this.HienThi_DanhSach_Tb_BaoHong();
        this.$root.$toast.success("Báo hỏng cho tỉnh thành công!");
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },

    sp_capnhat_trangthai_chiaphieu(p_baohong_id, p_ma_nd) {
      try {
        API.sp_capnhat_trangthai_chiaphieu(this.axios, {
          p_baohong_id,
          p_ma_nd
        })
      } catch (e) {

      }
    },

    //gridviewDanhSach_FocusedRowChanged
    async gridviewDanhSach_FocusedRowChanged(item) {
      console.log('gridviewDanhSach_FocusedRowChanged')
      console.log(item)
      this.loading(true);
      if (item == null) {
        this.loading(false);
        return;
      }
      this.itemDanhSachFocused = item;
      this.ReLoadDivisionalData(item);

      try {
        if (this.dsBaoHong.length <= 0) {
          this.clear();
          return;
        }

        let {dichvuvt_id, loaitb_id, thuebao_id} = item;
        let now = moment().format(this.formatDate);
        this.tttn.thuebao_id = thuebao_id;
        await this.HienThi_DS_LichSu_BaoHong();

        this.tttn.ma_tb = item.ma_tb;
        this.baohong_id = item.baohong_id;
        this.tinh_tc = item.tinh_thicong;
        this.trangthaitb_id = await this.newMapData('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao',
          {param: thuebao_id, type: 10});

        this.PHIEU_ID = item.phieu_id;
        this.ttHong.txtGhiChu = item.ghichu_hong;

        //Hiển thị danh sách tình trạng
        //ds_tinhtrang = bangts.Lay_ds_TinhTrang(baohong_id);
        let vloaitb_id = 0;
        this.tttn = {
          ...this.tttn,
          dichvuvt_id: parseInt(dichvuvt_id),
          ten_tb: item.ten_tb,
          diachi_ld: item.diachi_ld,
          diachi_tb: item.diachi_tb,
          dien_thoai: item.dienthoai_lh,
        }
        this.tttn.loaihinh_id = parseInt(loaitb_id);
        this.ma_lt = item.ma_lt;

        if (dichvuvt_id == DichVuVienThong.ADSL) {
          vloaitb_id = item.loaitb_id;
        }

        //Hiển thị danh sách tồn
        await this.loadCbLyDoTon(this.baohong_id, 0, 0, 1);
        let {lydoton} = this.options;
        if (lydoton.length > 0) {
          this.kqxl.cboNhomTon = lydoton[0]["nhomton_id"];
          // this.loadCbLyDoTon(this.baohong_id, this.kqxl.cboNhomTon, 0, 1)
        } else {
          this.loadCbLyDoTon(0, this.kqxl.cboNhomTon, 0, 1);
        }

        let kiemtra = false;
        if (this.kqxl.cboNguyenNhan != null && this.kqxl.cboNguyenNhan.length > 0) {
          kiemtra = true;
          this.kqxl.enableNN = true;
          this.kqxl.enableTon = false;
          this.kqxl.cboKQXL = 0;
        }

        if (!kiemtra) {
          if (this.kqxl.cboLyDoTon != null && this.kqxl.cboLyDoTon.length > 0) {
            kiemtra = true;
            this.kqxl.enableNN = false;
            this.kqxl.enableTon = true;
            this.kqxl.cboKQXL = 1;
          }
        }

        if (!kiemtra) this.kqxl.cboKQXL = 0;

        this.ttHong.cboNguoiTH = item.nhanvien_th_id ? item.nhanvien_th_id : this.$root.token.getNhanVienID();
        if (item.ngay_th) {
          this.ttHong.chkNgayTH = true;
          this.ttHong.ngayTH = moment(item.ngay_th, 'YYYY-MM-DD HH:mm:ss.S').format(this.formatDate);
        } else {
          this.ttHong.chkNgayTH = false;
          this.ttHong.ngayTH = now;
        }

        this.kqxl.txtGhiChu = item.ghichu_xl;

        ////vinhpv fix cho KGG disable giao phieu, khoa phieu voi cac loai hinh Megawan, TSL, chỉ cho đơn vị OMC được phép giao phiếu
        if (this.$root.token.getMaTinh() == "KGG") {
          this.btnVisible = {
            ...this.btnVisible,
            tsbtnKhoaPhieu: true,
            tsbtnGiaoPhieu: true
          }

          if (dichvuvt_id == "8" || dichvuvt_id == "9" || loaitb_id == "14" || loaitb_id == 15 || loaitb_id == 16 || loaitb_id == 17) {
            this.btnVisible = {
              ...this.btnVisible, // đơn vị OMC
              tsbtnKhoaPhieu: this.$root.token.getDonViID() == 378,
              tsbtnGiaoPhieu: this.$root.token.getDonViID() == 378
            }
          }
        }

        //Hiển thị danh sách nguyên nhân hỏng
        this.kqxl.cboNguyenNhan = await this.loadCbNguyenNhan(this.PHIEU_ID, 0, loaitb_id, this.ttHong.cboNhomTT, 1);
        let {nguyennhan} = this.options;
        if (nguyennhan.length > 0) {
          this.kqxl.cboNhomNN = nguyennhan[0]["loaihong_id"];
          // this.loadCbNguyenNhan(this.PHIEU_ID, this.kqxl.cboNhomNN, loaitb_id, this.ttHong.cboNhomTT, 1);
        } else {
          await this.loadCbNguyenNhan(0, this.kqxl.cboNhomNN, loaitb_id, this.ttHong.cboNhomTT, 1);
        }

        let ds2 = await this.LAY_THONGTIN_KH_THEO_THUEBAO_ID(this.tttn.thuebao_id);
        if (ds2 != null) {
          this.HienThiChiTietBH(ds2[0]);
        }

        this.tttn.nguoi_bh = item.nguoi_bh;

        let dt = await this.sp_lay_ds_baohong_dvph(this.baohong_id);
        if (dt != null && dt.length > 0) {
          this.dsbh.chkDonViPhoiHop = true;
          let checked = [];
          for (let i = 0; i < dt.length; i++) {
            checked.push(dt[i]["doitac_id"]);
          }
          this.dsbh.cboDonViPhoiHop = checked;
        } else {
          this.dsbh.chkDonViPhoiHop = false;
        }

        //ANHNT 12/07/2021: Chuyển gọi kiểm tra trong hàm
        let chuyenNET = "0";
        chuyenNET = await this.bancheo_kt_dk_chuyennet_bh(thuebao_id);
        if (chuyenNET == "0") {
          this.btnVisible.tsbtnChuyenNET = false;
          this.btnVisible.tsbtnGiaoPhieu = true;
        } else {
          this.btnVisible.tsbtnChuyenNET = true;
          this.btnVisible.tsbtnGiaoPhieu = false;
        }
      } catch (e) {
        console.log(e);
      }

      this.loading(false);
    },

    async bancheo_kt_dk_chuyennet_bh(vthuebao_id) {
      try {
        let rs = await API.bancheo_kt_dk_chuyennet_bh(this.axios, {vthuebao_id});
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log("bancheo_kt_dk_chuyennet_bh: " + e);
      }
      return "0";
    },

    ReLoadDivisionalData(item) {
    },

    async LAY_THONGTIN_KH_THEO_THUEBAO_ID(thuebao_id) {
      try {
        let rs = await API.lay_thongtin_kh_theo_thuebao_id(this.axios, thuebao_id);
        return this.getDataFromAPI(rs);
      } catch (e) {

      }
      return null;
    },

    async LAY_THAMSO_MACDINH(vma_ts, vnguoidung_id) {
      try {
        let rs = await API.lay_thamso_macdinh(this.axios, {
          vma_ts,
          vnguoidung_id
        });

        return this.getDataFromAPI(rs);
      } catch (e) {

      }
      return 1;
    },

    async LAY_PORT_THEO_TBID(hdtb_id, thuebao_id) {
      try {
        let rs = await API.lay_port_theo_tbid(this.axios, {
          hdtb_id,
          thuebao_id
        });

        return this.getDataFromAPI(rs);
      } catch (e) {

      }
      return null;
    },

    async bnm_getStatusPort(vInput) {
      try {
        let rs = await API.bnm_GetStatusPort(this.axios, {
          vinput: vInput
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }
      return "";
    },

    async fn_capnhat_tiepnhanbh(vkieu) {
      try {
        let vds = [{
          "CHKHENTL": this.tttn.checkHenTL ? 1 : 0,
          "HUONGGIAOTN_ID": this.huonggiao_id,
          "KHACHHANG_ID": this.khachhang_id,
          "DACTRUNG": this.tttn.dactrung,
          "NGUOI_CN": this.$root.token.getUserName(),
          "MAY_CN": await this.$root.token.getMachine(),
          "IP_CN": await this.$root.token.getIP(),
          "NHANVIEN_ID": this.$root.token.getNhanVienID(),
          "DONVI_ID": this.$root.token.getDonViID(),
          "TUNGAY": this.tttn.ngay_hentl_tu,
          "DENNGAY": this.tttn.ngay_hentl_den,
          "BAOHONG_ID": this.baohong_id,
          "THUEBAO_ID": this.tttn.thuebao_id,
          "KT_DAMBAO_CKCL_KH": this.kt_dambao_ckcl_kh ? 1 : 0,
          "CHKCKCL": this.ttHong.chkCamKet ? 1 : 0,
          "PHANLOAIBH": this.ttHong.cboPhanLoaiBH,
          "GIAOPHIEU_TUDONG": this.giaophieu_tudong ? 1 : 0,
          "GIAOVIEC_TD": this.Giaoviec_td ? 1 : 0,
          "GIAOPHIEU_BDG": this.giaophieu_bdg ? 1 : 0,
          "NHANHONG_KHOAMAY": this.nhanhong_khoamay ? 1 : 0,
          "NHOM_TT": this.ttHong.cboNhomTT,
          "KHONGGIAO_HTKT_BAOHONG": this.KHONGGIAO_HTKT_BAOHONG,
          "QUYTRINH_ID": this.quytrinh_id,
          "LAY_HUONGGIAO_BH_THEO_TINHTRANG": this.LAY_HUONGGIAO_BH_THEO_TINHTRANG,
          "LOAITB": this.tttn.loaihinh_id
        }];

        let params = {
          vkieu,
          "vds": JSON.stringify(vds),
          "dsbh_data": JSON.stringify(this.dsBH_DATA),
          "dsbh_dv": JSON.stringify(this.dsBH_DV),
          "ds_tt": JSON.stringify(this.ds_tt),
          "ds_nn": JSON.stringify(this.ds_nn),
          "ds_ton": JSON.stringify(this.ds_ton),
        }

        let rs = await API.fn_capnhat_tiepnhanbh(this.axios, params);
        return rs.data.data
      } catch (e) {
        console.log(e);
      }

      return null
    },

    async fn_capnhat_tiepnhanbh_v1() {
      try {
        let data = await this.fn_capnhat_tiepnhanbh(1);
        let {return_value, v_out} = data;
        let arr = v_out.split('|');
        for (const arrElement of arr) {
          let tmp = arrElement.split(':');
          if (tmp[0] == 'PHIEU_ID') {
            this.PHIEU_ID = tmp[1];
            continue;
          }

          if (tmp[0] == 'HG') {
            this.hg = tmp[1];
            continue;
          }

          if (tmp[0] == 'DONVI_NHAN_ID') {
            this.DONVI_NHAN_ID = tmp[1];
          }
        }

        if (return_value != '1' && return_value != '3') {
          return return_value;
        }

        if (return_value == '1') {
          this.$toast.success("Giao phiếu cho bộ phận tiếp theo thành công!", 'Thông báo');
        }

        return return_value;
      } catch (e) {
        console.log(e);
        return 'Lỗi xử lý';
      }
    },

    //bo phieu
    tsbtnBoQua_Click() {
      try {
        if (this.itemDanhSachFocused == null || this.dsBaoHong.length <= 0) {
          this.$root.$toast.error("Bạn hãy chọn phiếu trên lưới.");
          return;
        }

        this.$confirm('Bạn chắc chắn muốn bỏ qua phiếu cần xử lý không?',
          'Thông báo',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          })
          .then(() => {
            this.loading(true);
            this.sp_capnhat_trangthai_chiaphieu_vsecond(this.baohong_id, this.$root.token.getUserName());
            this.HienThi_DanhSach_Tb_BaoHong_Nhanhkhac(0, 0);
            this.loading(false);
          })
      } catch (ex) {
        console.log(ex);
      }
    },

    sp_capnhat_trangthai_chiaphieu_vsecond(p_baohong_id, p_ma_nd) {
      API.sp_capnhat_trangthai_chiaphieu_vsecond(this.axios, {
        p_baohong_id,
        p_ma_nd
      });
    },

    //Xóa phiếu
    async tsbtnXoaPhieu_Click() {
      try {
        let lst_chon = this.$refs.dsBaoHong.getSelectedRecords();
        if (lst_chon.length == 0) {
          this.$root.$toast.error("Bạn chưa chọn thuê bao.");
          return;
        }

        for (const lst of lst_chon) {
          if (lst.nguoi_cn == "WEBSERVICE_HNI") {
            this.$root.$toast.error("Bạn không được phép xóa các thuê bao báo hỏng được thực hiện tiếp nhận từ Hà Nội.");
            return;
          }
        }

        let confirm = await this.$confirm(`Bạn thật sự muốn xoá ${lst_chon.length} phiếu báo hỏng này không ??`,
          'Thông báo',
          {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(() => true).catch(() => false);
        if (!confirm)
          return;

        this.loading(true);

        for (const lst of lst_chon) {
          //nhapt them log xóa phiếu báo hỏng
          let _baohong_id = parseInt(lst["baohong_id"]);
          let _trang_thai_bc = await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_baohong_bc", {
            param: _baohong_id,
            type: 1
          });
          if (_trang_thai_bc != "0") {
            this.$root.$toast.error("Phiếu đã được giao đi, không được xóa!.");
            return;
          }

          API.log_xoaphieu_baohong(this.axios, {vbaohong_id: _baohong_id});
          await this.xoaphieu_dsbh(lst.baohong_id, this.$root.token.getUserName(), this.tag);
        }
        await this.$toast.success("Đã xoá phiếu thành công!");
      } catch (e) {
      } finally {
        await this.HienThi_DanhSach_Tb_BaoHong();
        this.loading(false);
      }
    },

    xoaphieu_dsbh(vbaohong_id, vma_nd, vtag) {
      try {
        API.fn_xoaphieu_dsbh_tiepnhanbh(this.axios, {
          vbaohong_id,
          vma_nd,
          vtag,
        })
      } catch (e) {
        console.log(e);
      }
    },

    //Khóa phiếu danh sách
    async tsbtnCapNhatKQ_Click() {
      try {
        //region Kiểm tra điều kiện
        let item = this.itemDanhSachFocused;
        if (!await this.KiemTra_KhoaPhieu()) {
          return;
        }
        let moment_now = moment();
        let ngay_sys = moment().format("DD/MM/YYYY");
        let ngay_bh = item.ngay_bh;
        let moment_ngay_bh = moment(ngay_bh, 'DD/MM/YYYY HH:mm:ss');
        let moment_ngay_ht = moment(this.ttHong.ngayTH, 'DD/MM/YYYY HH:mm:ss');
        let ngay_ht = moment_ngay_ht.format('DD/MM/YYYY');//Ngày hoàn thành
        //if (!KiemTraDK_HoanThanh(ngay_bh, ngay_ht))//Nếu ngày báo hỏng > ngày hoàn thành
        if (moment_ngay_ht.isBefore(moment_ngay_bh)) {
          this.$root.$toast.error(" Thuê bao " + item["ma_tb"] + " có ngày báo hỏng : " + ngay_bh + " \n Ngày hoàn thành không được phép nhỏ hơn ngày báo hỏng");
          return;
        }
        //if (!KiemTraDK_HoanThanh(ngay_ht, ngay_sys))//Nếu ngày hoàn thành > ngày hiện tại
        if (moment_now.isBefore(moment_ngay_ht)) {
          this.$root.$toast.error("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");
          return;
        }

        let dtpNgayXN = moment(this.kqxl.ngayXN, 'DD/MM/YYYY HH:mm:ss');
        if (moment_now.isBefore(dtpNgayXN)) {
          this.$root.$toast.error("Ngày xác nhận không thể lớn hơn ngày hiện tại!");
          return;
        }

        let lst_chon = this.$refs.dsBaoHong.getSelectedRecords();
        if (lst_chon.length == 0) {
          this.$root.$toast.error("Bạn chưa chọn thuê bao.");
          return;
        }

        if (this.GIOIHAN_NGUYENNHAN_BAOHONG > 0) {
          if (this.kqxl.cboKQXL == 0) {
            let ktC = this.kqxl.cboNguyenNhan == null ? 0 : this.kqxl.cboNguyenNhan.length;
            if (ktC > this.GIOIHAN_NGUYENNHAN_BAOHONG) {
              this.$root.$toast.error("Không thể chọn quá " + this.GIOIHAN_NGUYENNHAN_BAOHONG + " nguyên nhân khi khóa phiếu!");
              return;
            }
          }
        }

        let msg = this.kqxl.cboKQXL == 0 ? 'Bạn thật sự muốn khóa phiếu báo hỏng không ?' : 'Bạn thật sự muốn khóa phiếu báo tồn không ?';
        this.$confirm(msg, 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          this.TaoDuLieu_tinhtrang(true);
          this.TaoDuLieu_nguyennhan(true);
          this.TaoDuLieu_baoton(true);

          if (this.ds_nn.length <= 0) {
            this.$toast.error("Bạn chưa chọn nguyên nhân!");
            return;
          }

          for (let lst of lst_chon) {
            if (this.ds_nn.length <= 0) {
              continue;
            }

            /*nhapt thực hiện đẩy dữ liệu lên HNI 10/01/2018
             * Xảy ra 2 trường hợp khóa phiếu
             * TH1: Thực hiện khóa phiếu khi 119 có thể hỗ trợ xử lý nhanh và thực hiện hoàn thiện luôn
             * TH2: Thực hiện khóa phiếu khi thông tin HNI gửi xuống tỉnh không chính xác, không liên lạc được với thuê bao báo hỏng
             * => thực hiện khóa phiếu với những thuê bao chưa cập nhật thông tin vì k xác định được thông tin cập nhật
             * - những thuê bao đã cập nhật rồi, kể cả cập nhật sai do người dùng cố ý thì cũng đều gửi lên là thành công
            */
            let _count = await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_count_bh",
              {param: lst.baohong_id, type: 1});
            let _soDT = await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien",
              {param: this.$root.token.getNhanVienID(), type: 5});
            if (_count != "-1" && _count > 0) {
              //Hủy do hủy API
              // let _obj = "";
              // if (lst.ma_tb == "") {//thông tin phiếu sai
              //   _obj = await this.khdn_NGHIEMTHU_BAOHONG_V2(
              //     this.userInfo.tentat_ccbs,
              //     lst.baohong_id.toString(),
              //     1,
              //     Utils.TO_KHONG_DAU(" ND: " + this.getTextNguyenNhan() + "; " + this.kqxl.txtGhiChu),
              //     "Thong tin phieu sai; Nguoi khoa: " + Utils.TO_KHONG_DAU(this.userInfo.ten_nd),
              //     _soDT,
              //     moment.format("DD/MM/YYYY HH:mm:ss"),
              //     "null"
              //   );
              // } else {//khóa phiếu thành công, nhân viên nhận phiếu có thể thực hiện nghiệm thu luôn
              //   _obj = await this.khdn_NGHIEMTHU_BAOHONG_V2(
              //     this.userInfo.tentat_ccbs,
              //     lst.baohong_id.toString(),
              //     0,
              //     Utils.TO_KHONG_DAU(" ND: " + this.getTextNguyenNhan() + "; " + this.kqxl.txtGhiChu),
              //     Utils.TO_KHONG_DAU(this.userInfo.ten_nd),
              //     _soDT,
              //     moment.format("DD/MM/YYYY HH:mm:ss"),
              //     lst.ma_tb
              //   );
              // }
              //
              // let _json = JSON.parse(_obj);
              // if (_json["IsError"] != "False") {
              //   this.$root.$toast.error(_json["Message"]);
              //   return;
              // }
              //lưu log
              this.LOG_BAOHONG(lst.baohong_id);
            }

            let in_baohong_id = lst.baohong_id;
            let in_phieu_id = lst.phieu_id;

            await API.sp_update_baohong_bc_trangthai(this.axios, {
              vbaohong_id_ban: in_baohong_id,
              // vtrangthai_id: 1
            });
            await API.sp_update_giaophieu_baohong_ttph_id(this.axios, {vbaohong_id: in_baohong_id})

            for (let j = 0; j < this.ds_tt.length; j++) {
              this.ds_tt[j]["BAOHONG_ID"] = in_baohong_id;
            }

            for (let j = 0; j < this.ds_nn.length; j++) {
              this.ds_nn[j]["PHIEU_ID"] = in_phieu_id;
            }

            for (let j = 0; j < this.ds_ton.length; j++) {
              this.ds_ton[j]["BAOHONG_ID"] = in_baohong_id;
            }

            await API.sp_khoaphieu_danhsach_tiepnhanbh_bancheo(this.axios, {
              "vphieu_id": in_phieu_id,
              "vbaohong_id": in_baohong_id,
              "vghichu": this.kqxl.txtGhiChu,
              "json_list_nn": JSON.stringify(this.ds_nn),
              "json_list_tt": JSON.stringify(this.ds_tt),
            });

            await API.capnhat_nhanvien_th(this.axios, {
              "vphieu_id": in_phieu_id,
              "vnhanvien_th_id": this.ttHong.cboNguoiTH,
              "vngay_th": this.ttHong.ngayTH
            });

            await API.khoaphieu_119(this.axios, {
              "vbaohong_id": in_baohong_id,
              "vphieu_id": in_phieu_id,
              "vngay_ht": this.ttHong.ngayTH,
              "vbaoton": this.kqxl.cboKQXL
            });

            await API.capnhat_ngay_xn(this.axios, {
              "vbaohong_id": in_baohong_id,
              "vngay_xn": this.kqxl.ngayXN
            })
          }

          await this.HienThi_DanhSach_Tb_BaoHong();
          this.$root.$toast.success("Thực hiện khóa phiếu thành công!");
          this.clear();
        });
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },

    async KiemTra_KhoaPhieu() {
      let dtPhieuDaGiao = await this.SP_DS_GIAOPHIEU_BAOHONG_PHIEU_ID(this.PHIEU_ID);
      if (dtPhieuDaGiao != null && dtPhieuDaGiao.length > 0) {
        this.$root.$toast.error("Các đơn vị được giao chưa xử lý xong!!");
        return false;
      }

      let dtPhieuDaChuyenTinh = await this.SP_DS_BAOHONG_BC_TRANGTHAI(this.baohong_id);
      if (dtPhieuDaChuyenTinh != null && dtPhieuDaChuyenTinh.length > 0) {
        this.$root.$toast.error("Các đơn vị được giao chưa xử lý xong!!");
        return false;
      }

      if (this.ttHong.cboTinhTrang == null || this.ttHong.cboTinhTrang.length <= 0) {
        this.$root.$toast.error("Hãy nhập tình trạng hỏng của thuê bao!");
        return false;
      }
      if (this.ttHong.cboNguoiTH == 0) {
        this.$root.$toast.error("Hãy nhập nhân viên thực hiện!");
        return false;
      }
      if (!this.ttHong.chkNgayTH) {
        this.$root.$toast.error("Hãy nhập ngày thực hiện!");
        return false;
      }
      if (this.kqxl.cboKQXL == 0) {
        if (this.kqxl.cboNguyenNhan == null || this.kqxl.cboNguyenNhan.length <= 0) {
          this.$root.$toast.error("Hãy nhập nguyên nhân hỏng!");
          return false;
        }
      } else {
        if (this.kqxl.cboLyDoTon == null || this.kqxl.cboLyDoTon.length <= 0) {
          this.$root.$toast.error("Hãy nhập lý do tồn!");
          return false;
        }
      }

      return true;
    },

    async kt_tsl_nt(baohong_id) {
      try {
        let rs = await API.kt_tsl_nt(this.axios, {baohong_id})
      } catch (e) {
        console.log(e);
      }
    },

    getTextNguyenNhan() {
      let str = '';
      let {cboNguyenNhan} = this.kqxl;
      if (cboNguyenNhan.length > 0) {
        for (let tmp of this.options.nguyennhan) {
          if (cboNguyenNhan.includes(tmp.cthong_id)) {
            str += tmp.ct_hong + ',';
          }
        }
      }
    },

    async LOG_BAOHONG(baohong_id) {
      //this.userInfo.ma_nd+ "; " + "WS_HNI", tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ip
      try {
        API.log_xoaphieu_baohong(this.axios, {
          baohong_id,
          "ip_cn": await this.$root.token.getIP(),
          "may_cn": await this.$root.token.getMachine(),
          "ngay_cn": moment().format('DD/MM/YYYY HH:mm:ss'),
          "nguoi_cn": this.userInfo.ma_nd + "; " + "WS_HNI"
        });
      } catch (e) {
        console.log(e);
      }
    },

    async sp_lay_gt_thamso_md_tents(p_ma_ts, p_ten_ts) {
      try {
        let rs = await API.sp_lay_gt_thamso_md_tents(this.axios, {
          p_ma_ts,
          p_ten_ts
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }

      return null;
    },

    async sp_ds_vi_nhanvien_theocap(_vthuebao_id) {
      try {
        let rs = await API.sp_ds_vi_nhanvien_theocap(this.axios, _vthuebao_id);

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }

      return null;
    },
    async lay_sodt_nhanvien_khoan_tn(_vthuebao_id) {
      try {
        let rs = await API.lay_sodt_nhanvien_khoan_tn(this.axios, _vthuebao_id);

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }

      return null;
    },

    fn_capnhat_sendsms_tiepnhanbh(_row) {
      // {
      //   "return_value": "1",
      //   "pSendSMS": null,
      //   "vmess_lan1": null,
      //   "vmess_lan2": null
      // }

      try {
        let vds = [{
          "THUEBAO_ID": _row.THUEBAO_ID,
          "MA_TB": _row.MA_TB,
          "NOIDUNG": _row.NOIDUNG,
          "PHIEU_ID": _row.PHIEU_ID,
          "SO_DT": _row.SO_DT,
          "TEN_DAY_DU": this.dtTTCap[0].ten_day_du,
          "DONVI_NHAN_ID": _row.DONVI_NHAN_ID,
          "NHANVIEN_ID": this.$root.token.getNhanVienID(),
          "HUONGGIAOTN_ID": _row.HUONGGIAO_ID,
          "GIAOPHIEU_TUDONG": this.giaophieu_tudong ? 1 : 0,
          "GIAOVIEC_TD": this.Giaoviec_td ? 1 : 0,
          "GIAOPHIEU_BDG": this.giaophieu_bdg ? 1 : 0,
          "NHANHONG_KHOAMAY": this.NHANHONG_KHOAMAY,
          "QUYTRINH_ID": this.quytrinh_id,
          "IS_HPG": this.IS_HPG
        }];

        let rs = API.fn_capnhat_sendsms_tiepnhanbh(this.axios, {
          vds: JSON.stringify(vds)
        });
        let data = this.getDataFromAPI(rs);
        let {return_value, vmess_lan1, vmess_lan2} = data;
        if (return_value == 1) {
          if (vmess_lan1) {
            this.$root.$toast.success(vmess_lan1);
          }

          if (vmess_lan2) {
            this.$root.$toast.success(vmess_lan2);
          }
        }
      } catch (e) {
        console.log(e);
      }
    },

    async fn_capnhat_goto_tiepnhanbh(vbaohong_id, vthuebao_id) {
      try {
        let rs = await API.fn_capnhat_goto_tiepnhanbh(this.axios, {
          vbaohong_id,
          vthuebao_id
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }

      return null;
    },

    async sp_capnhat_dothu_tiepnhanbh() {
      try {
        let vds = [{
          "BAOHONG_ID": this.baohong_id,
          "GHICHUHONG": this.ttHong.txtGhiChu,
          "PHIEU_ID": this.PHIEU_ID,
          "NGUOI_GV": this.ttHong.cboNguoiTH,
          "NGAY_TH": this.ttHong.ngayTH,
          "CAPNHAT_TT_LIENHE_BH_NHANHONG": this.CAPNHAT_TT_LIENHE_BH_NHANHONG,
          "NGUOI_BH": this.tttn.nguoi_bh,
          "DIENTHOAILH": this.tttn.dien_thoai,
          "NGUOI_CN": this.$root.token.getUserName(),
          "MAY_CN": await this.$root.token.getMachine(),
          "IP_CN": await this.$root.token.getIP(),
          "DONVI_ID": this.$root.token.getDonViID(),
          "NHANVIEN_ID": this.$root.token.getNhanVienID(),
          "TK_MATB": this.tttn.ma_tb
        }];

        let rs = await API.sp_capnhat_dothu_tiepnhanbh(this.axios, {
          vds: JSON.stringify(vds),
          ds_tt: JSON.stringify(this.ds_tt),
          ds_nn: JSON.stringify(this.ds_nn),
          ds_ton: JSON.stringify(this.ds_ton),
        });

        return rs.data.error === '200' ? '1' : rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return 'Lỗi tiếp nhận';
    },

    async sp_capnhat_dothu_tiepnhanbh_bancheo() {
      try {
        let vds = [{
          "BAOHONG_ID": this.baohong_id,
          "GHICHUHONG": this.ttHong.txtGhiChu,
          "PHIEU_ID": this.PHIEU_ID,
          "NGUOI_GV": this.ttHong.cboNguoiTH,
          "NGAY_TH": this.ttHong.ngayTH,
        }];

        let rs = await API.sp_capnhat_dothu_tiepnhanbh_bancheo(this.axios, {
          vds: JSON.stringify(vds),
          ds_tt: JSON.stringify(this.ds_tt),
          ds_nn: JSON.stringify(this.ds_nn),
          ds_ton: JSON.stringify(this.ds_ton),
        });

        return rs.data.error === '200' ? '1' : rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return 'Lỗi tiếp nhận';
    },

    //tsbtnTestAllPort_Click
    async tsbtnTestAllPort_Click() {
      try {
        this.loading(true);
        let iServer_DK;
        try {
          let rs = await API.lay_thamso_macdinh(this.axios, {
            "vma_ts": "SERVER_DK",
            "vnguoidung_id": this.$root.token.getNguoiDungID()
          });

          iServer_DK = this.getDataFromAPI(rs);
        } catch (e) {
          console.log(e);
          iServer_DK = 1;
        }

        if ((this.vuser_alt == "" || this.vpass_alt == "") && iServer_DK != 3) {
          this.$root.$toast.error("User hoặc mật khẩu alinetest chưa được thiết lập!");
          return;

        }

        let lst_chon = this.$refs.dsBaoHong.getSelectedRecords();
        if (lst_chon.length == 0) {
          this.$root.$toast.error("Bạn chưa chọn thuê bao.");
          return;
        }

        await this.GetPortStatus_Unsync(iServer_DK);
      } catch (e) {
        console.log('tsbtnTestAllPort_Click: ' + e);
      } finally {
        this.loading(false);
      }
    },

    async GetPortStatus_Unsync(iServer_DK) {
      let lst_chon = this.$refs.dsBaoHong.getSelectedRowIndexes();
      let dsBaoHong = [...this.dsBaoHong];
      for (let i = 0; i < dsBaoHong.length; i++) {
        for (let lst of lst_chon) {
          if (i === lst) {
            let vInput = dsBaoHong[i].ma_tb;
            let vdichvuvtID = dsBaoHong[i].dichvuvt_id;
            let vthuebaoID = dsBaoHong[i].thuebao_id;
            let dtPort = await this.LAY_PORT_THEO_TBID(-1, vthuebaoID);
            if (dtPort == null || dtPort.length <= 0) {
              continue;
            }

            let vip = dtPort[0]["ip"];
            let vport_adsl = dtPort[0]["port_adsl"];
            let vport_visa = dtPort[0]["port_visa"];
            let vonu_id = dtPort[0]["onu_id"];
            vInput = vip + ":" + vport_visa;
            if (vonu_id != "")
              vInput += "/" + vonu_id;

            if (iServer_DK == 3) {
              let vloai_gpon = dtPort[0]["loai_gpon"];
              let vport_bnm = dtPort[0]["port_bnm"];
              vInput = vip + ":" + vport_bnm + ":" + vloai_gpon + ":" + dtPort[0]["loaitbi_id"];

              dsBaoHong[i].ht_port = await this.bnm_getStatusPort(vInput);
            } else {
              dsBaoHong[i].ht_port = await this.getPortStatus(vInput, vdichvuvtID);
            }
          }
        }
      }

      this.dsBaoHong = dsBaoHong;
      this.itemDanhSachFocused = null;
    },

    tsbtnDoKiem_Click() {
      try {
        if (this.itemDanhSachFocused == null) {
          this.$root.$toast.error("Bạn hãy chọn thuê bao cần đo!");
          return;
        }

        if (this.itemDanhSachFocused && this.IS_HPG !== 1) {
          let ma_tb = this.itemDanhSachFocused.ma_tb;

          if (ma_tb === "")
          ma_tb = this.tttn.ma_tb;
          this.checkPortDialog.input.vma_tb = this.itemDanhSachFocused.ma_tb;
          this.checkPortDialog.input.vkieu = 0;
        }

        if (this.tttn.thuebao_id != 0) {
          this.checkPortDialog.input.vthuebao_id = this.tttn.thuebao_id;
        }

        this.checkPortOnClick()
      } catch (ex) {
        this.$root.$toast.error("Có lỗi khi hiển thị");
        console.log(ex);
      }
    },

    tsbtnGianDo_Click() {
      let ma_ts = "USING_CABMAN_2.0";
      let filter = this.dsThamSo.filter(item => {
        return item.ma_ts === ma_ts;
      });
      if (filter.length > 0) {
        // if (this.IS_USING_CABMAN_V2) {
        // let thongTinDauNoi = {
        //     thueBaoId: this.thuebao_id,
        //     kieu: 0
        // }
        // this.$refs.daunoithuebao.setData(thongTinDauNoi)
        // this.$refs.daunoithuebao.show()
        this.$bvModal.show('dandocap');
      } else {
        this.$refs.dlg_mdf.show()
        this.$refs.frmTraCuuMDF.frmTraCuuMDF_Load(this.tttn.ma_tb.trim());
      }
    },

    tsmXoaPhieu_Click(itemTiepNhan) {
      if (itemTiepNhan == null) return;

      this.$confirm(
        `Bạn thật sự muốn xoá tiếp nhận thuê bao này không?`,
        'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(() => {
        this.loading(true);
        API.sp_xoa_baohong_tmp_tiepnhanbh(this.axios, {
          vthuebao_id: itemTiepNhan.thuebao_id
        }).then(rs => {
          if (rs.data.error === '200') {
            this.$toast.success("Đã xoá thành công!");
            this.HienThi_Danhsach_Tiepnhan();
          } else {
            this.$root.$toast.error('tsmXoaPhieu_Click: ' + rs.data.message);
          }
          this.loading(false);
        }).catch(err => {
          console.log(err);
          this.loading(false);
        })
      });
    },

    async tsbtnVatTu_Click() {
      try {
        let f = this.$refs.frmTraCuuVatTuThueBao;
        if (this.tttn.ma_tb !== "") {
          f.p_dich_vu_id = parseInt(this.tttn.dichvuvt_id);
          f.p_loai_hinh_id = parseInt(this.tttn.loaihinh_id);
          f.p_so_may = this.tttn.ma_tb;
        } else {
          if (this.dsBaoHong.length > 0) {
            f.p_dich_vu_id = this.itemDanhSachFocused.dichvuvt_id;
            f.p_loai_hinh_id = this.itemDanhSachFocused.loaitb_id;
            f.p_so_may = this.itemDanhSachFocused.ma_tb;
          }
        }

        await f.loadTraCuuVatTuThueBao();
        this.$bvModal.show('popup-tracuuvattuthuebao')
      } catch (ex) {
        console.log(ex);
      }
    },
    async sp_bh_bancheo_baohong_id(p_baohong_id, p_loaihd_bancheo) {
      try {
        let rs = await API.sp_bh_bancheo_baohong_id(this.axios, {
          p_baohong_id,
          p_loaihd_bancheo
        });
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }

      return null;
    },

    async tinh_thicong(tinh_id) {
      try {
        let rs = await API.sp_lay_tinh_tc_theo_tinh_id(this.axios, {
          tinh_id,
        });
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }

      return null;
    },
    bancheo_khoaphieu_bh(baohong_id, nguyen_nhan, nhom_nn, tinh_trang) {
      API.bancheo_khoaphieu_bh(this.axios, {
        baohong_id,
        nguyen_nhan,
        nhom_nn,
        tinh_trang
      })
    },
    async fn_khoaphieu_danhsach_tiepnhanbh(itemBH, json_list_nn, json_list_tt) {
      let vds = [
        {
          "BAOHONG_ID": itemBH.baohong_id,
          "PHIEU_ID": itemBH.phieu_id,
          "DICHVUVT_ID": itemBH.dichvuvt_id,
          "THUEBAO_ID": itemBH.thuebao_id,
          "LOAITB_ID": itemBH.loaitb_id,
          "MAY_CN": await this.$root.token.getMachine(),
          "NHANVIEN_ID": this.$root.token.getNhanVienID(),
          "NGUOI_CN": this.$root.token.getUserName(),
          "GHICHUXL": this.kqxl.txtGhiChu,
          "IN_BAOHONG_ID": itemBH.baohong_id,
          "IN_PHIEU_ID": itemBH.phieu_id,
          "NGUOIGV": this.ttHong.cboNguoiTH,
          "NGAYTH": this.ttHong.ngayTH,
          "NGAYXN": this.kqxl.ngayXN,
          "KHOAPHIEU_BH_HTKH": this.KHOAPHIEU_BH_HTKH,
          "KQXL": this.kqxl.cboKQXL,
          "TAG": this.tag
        }
      ];

      try {
        let rs = await API.fn_khoaphieu_danhsach_tiepnhanbh(this.axios, {
          vds: JSON.stringify(vds),
          json_list_nn,
          json_list_tt
        });

        let data = this.getDataFromAPI(rs);
        return data != null ? data : rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return '-1';
    },

    //tsbtnLayPhieu_Click
    tsbtnLayPhieu_Click() {
      this.$alert('Nút này thuộc Đài, anh Hiếu đã xác nhận để phase 2');
      // System.Collections.Generic.List<MultiDBRetrieve.PairDataWrapper> listParams = new List<MultiDBRetrieve.PairDataWrapper>();
      // listParams.Add(new MultiDBRetrieve.PairDataWrapper("vma_nd", tt_nd.ma_nd));
      //
      //
      // dtBH = MultiDB.ConnectToServer().GetFirstRow(4, tt_nd.ma_nd, tinhChon.ToArray(), 15, listParams);
      // if (dtBH == null)
      // {
      //   gridDanhSach.DataSource = null;
      //   return;
      // }
      //
      // dtBH.Columns.Add("TINH");
      // dtBH.Rows[0]["TINH"] = dtBH.TableName;
      //
      // dtBH = MultiDB.GhepDuLieu_BaoHong(dtBH);
      //
      // gridviewDanhSach.FocusedRowChanged -= gridviewDanhSach_FocusedRowChanged;
      // gridDanhSach.DataSource = dtBH;
      // gridviewDanhSach.FocusedRowChanged += gridviewDanhSach_FocusedRowChanged;
      // ReLoadDivisionalData(gridviewDanhSach.GetFocusedDataRow());
      // gridviewDanhSach_FocusedRowChanged(0);
      //
      // gridviewDanhSach.BestFitColumns();
    },

    //tsbtnChuyenNET_Click
    async tsbtnChuyenNET_Click() {
      console.log('this.tinh_tc')
      console.log(this.tinh_tc)
      this.loading(true);
      try {
        let vdonvi_nhan_id = await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_dm_tinh_ketnoi", {
          param: this.tinh_tc,
          type: 1
        });
        if (vdonvi_nhan_id != -1) {
          let vphieu_net_id = parseInt(await this.newMapData("/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieubh", {
            param: this.baohong_id,
            type: 3,
            param1: vdonvi_nhan_id
          }));
          if (vphieu_net_id != -1) {
            this.$root.$toast.success("Phiếu đã chuyển VNPT NET!");
            return;
          }
          let quytrinh_id = this.itemDanhSachFocused.quytrinh_id;
          let dsHuongGiao = await this.LAY_DS_HUONGGIAO_BAOHONG_V2(quytrinh_id, 432);
          if (dsHuongGiao != null && dsHuongGiao.length > 0) {
            let huonggiao_id = parseInt(dsHuongGiao[0]["huonggiao_id"]);
            let ttbh_id = parseInt(dsHuongGiao[0]["ttbh_id"]);
            await this.GiaoPhieu_BH_V2(this.baohong_id,
              this.$root.token.getDonViID(),
              vdonvi_nhan_id,
              this.$root.token.getNhanVienID(),
              huonggiao_id,
              this.PHIEU_ID,
              ttbh_id,
              moment().format(this.formatDate),
              this.$root.token.getUserName(),
              this.$root.token.getMachine(),
              this.ttHong.txtGhiChu
            );
          } else {
            this.$root.$toast.error("Không tìm thấy hướng giao tiếp theo!");
            return;
          }
          this.$root.$toast.success("Chuyển VNPT NET thành công!");
          await this.HienThi_DanhSach_Tb_BaoHong();
        } else {
          this.$root.$toast.error("Không tìm thấy đơn vị nhận!");
          return;
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },

    async CapNhat(nghiemthu) {
      try {
        if (this.GIAOPHIEU_BAOHONG_CATCHUYEN === 1) {
          if (this.kieugoi_id != 3) {
            let dtck = this.lay_ds_catchuyen_cap_v2(this.tttn.thuebao_id);
            if (dtck != null && dtck.length <= 0) {
              if (this.kqxl.cboKQXL != 0) {
                for (let lydoton of this.kqxl.cboLyDoTon) {
                  if (lydoton == CT_TON.BKK_THANHTHAI) {
                    {
                      this.$toast.error("Thuê bao không nằm trong danh sách cắt chuyển.\n" +
                        "Không thể chọn lý do: \"BKK Ngoài tầm kiểm soát của DNCCDV (thanh thải chỉnh trang, xây dựng,...)\"");
                      return false;
                    }
                  }
                }
              }
            }
          }
        }

        this.TaoDuLieu_nguyennhan(true);
        this.TaoDuLieu_baoton(true);
        let json_nn = JSON.stringify(this.ds_nn);
        let json_ton = JSON.stringify(this.ds_ton);

        let kt = await this.fn_bool_capnhat_chuyenphieu_tiepnhanbh(json_nn, json_ton);
        if (kt != '1') {
          this.$toast.error('fn_bool_capnhat_chuyenphieu_tiepnhanbh: ' + kt);
          return false;
        }

        return true;
      } catch (exp) {
        console.log(exp);
        return false;
      }
    },

    async lay_ds_catchuyen_cap_v2(thuebao_id) {
      try {
        let rs = await this.sp_lay_ds_catchuyen_cap_vsecond(this.axios, thuebao_id);
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }

      return null;
    },

    async fn_bool_capnhat_chuyenphieu_tiepnhanbh(json_ds_nn, json_ds_ton) {
      try {
        let vds = [{
          "BAOHONG_ID": this.baohong_id,
          "PHIEU_ID": this.PHIEU_ID,
          "GHICHUXL": this.kqxl.txtGhiChu,
          "KQXL": this.kqxl.cboKQXL,
          "NHOMTON": this.kqxl.cboNhomTon,
          "NGUOI_CN": this.$root.token.getUserName(),
          "MAY_CN": await this.$root.token.getMachine(),
          "NHANVIEN_ID": this.$root.token.getNhanVienID(),
          "IP_CN": await this.$root.token.getIP(),
          "DONVI_ID": this.$root.token.getDonViID(),
        }];
        let rs = await API.fn_bool_capnhat_chuyenphieu_tiepnhanbh(this.axios, {
          vds: JSON.stringify(vds),
          ds_nn: json_ds_nn,
          ds_ton: json_ds_ton
        });

        return rs.data.error === '200' ? rs.data.data : rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return '-1';
    },

    async Giaophieu_donvi_tieptheo_v2(vbaohong_id, vphieu_id, vhuonggiao_id) {
      try {
        let rs = await API.giaophieu_donvi_tieptheo_vsecond(this.axios, {
          vbaohong_id,
          vphieu_id,
          vhuonggiao_id
        });

        if (rs.data.error === '200') {
          return '1';
        }

        return rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return '-1';
    },

    async Lay_ds_phieu_phoihop_khdn_v2(vphieu_id, vtinh_id, vtentat) {
      try {
        let rs = await API.lay_ds_phieu_phoihop_khdn_vsecond(this.axios, {
          vphieu_id,
          vtentat,
          vtinh_id
        });

        let data = this.getDataFromAPI(rs);
        let format = "YYYY-MM-DDT HH:mm:ss[Z]"
        if (data != null && data.length > 0) {
          data = data.map(obj => {
            let tmp = Object.keys(obj).reduce((acc, key) => {
              acc[key.toUpperCase()] = obj[key];
              return acc;
            }, {});

            tmp.NGAYGIAO = moment(tmp.NGAYGIAO, "YYYY-MM-DD HH:mm:ss").format(format);
            tmp.NGAY_BH = moment(tmp.NGAY_BH, "YYYY-MM-DD HH:mm:ss").format(format);
            tmp.NGAY_SD = moment(tmp.NGAY_SD, "YYYY-MM-DD HH:mm:ss").format(format);
            tmp.THOIGIAN_CK = moment(tmp.THOIGIAN_CK, "YYYY-MM-DD HH:mm:ss").format(format);

            return tmp;
          })

          console.log(data);
          return data;
        }
      } catch (e) {
        console.log(e);
      }

      return null;
    },

    async capnhat_giaophieu_ph(vdstt_khdn, vkieu) {
      try {
        let rs = await API.capnhat_giaophieu_ph(this.axios, {
          vdstt_khdn,
          vkieu
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }

      return null;
    },

    //tsbtnChuyenOMCData_Click
    async tsbtnChuyenOMCData_Click() {
      try {
        if (this.baohong_id == 0 || this.dsBaoHong.length <= 0) {
          this.$toast.error("Không có dữ liệu để thực hiện!");
          return;
        }

        if (this.kqxl.cboKQXL == 0) {
          this.$toast.error("Hãy chọn kết quả xử lý");
          return;
        }

        if (this.kqxl.cboLyDoTon == "") {
          this.$toast.error("Hãy chọn lý do tồn");
          return;
        }

        if (!(this.tttn.dichvuvt_id == 4 && this.tttn.loaihinh_id == 58)) {
          this.$toast.error("Chức năng chỉ áp dụng cho dịch vụ Fiber");
          return;
        }

        let confirm = await this.$confirm(
          'Bạn chắc chắn muốn chuyển phiếu?',
          'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(() => true).catch(() => false);
        if (!confirm)
          return;

        if (!await this.CapNhat(true)) {
          return;
        }

        if (this.kqxl.cboKQXL == 1 && this.kqxl.cboNhomTon == NHOMTON.MANG_VT_LOP_TREN) {
          let kt = await this.giaophieu_donvi_tieptheo_vthird(this.baohong_id, this.PHIEU_ID, this.huonggiao_id);
          if (kt != '1') {
            this.$toast.error('giaophieu_donvi_tieptheo_vthird: ' + kt);
            return;
          }
          this.$toast.success("Cập nhật dữ liệu lên viễn thông tỉnh thành công!");
          await this.HienThi_Danhsach_Tiepnhan();
          await this.HienThi_DanhSach_Tb_BaoHong();
        } else {
          this.$toast.error("Không thể chuyển phiếu với nhóm tồn khác \"Mạng VT lớp trên\"");
        }
      } catch (ex) {
        this.$toast.error('giaophieu_donvi_tieptheo_vthird_catch: ' + ex.message);
      }
    },

    async giaophieu_donvi_tieptheo_vthird(vbaohong_id, vphieu_id, vhuonggiao_id) {
      try {
        let rs = await API.giaophieu_donvi_tieptheo_vthird(this.axios, {
          vbaohong_id,
          vphieu_id,
          vhuonggiao_id
        });

        if (rs.data.error === '200') {
          return '1';
        }

        return rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return '-1';
    },

    //tsbtnChuyenMedia_Click
    async tsbtnChuyenMedia_Click() {
      try {
        if (this.baohong_id == 0 || this.dsBaoHong.length <= 0) {
          this.$toast.error("Không có dữ liệu để thực hiện!");
          return;
        }

        if (this.KHONGGIAO_HTKT_BAOHONG == 1 && this.ttHong.cboNhomTT == NHOM_TT.HOTRO_KT) {
          this.$toast.error("Bạn đang tiếp nhận phiếu Hỗ trợ KT. Không thể giao phiếu!");
          return;
        }

        if (this.kqxl.cboKQXL == 0) {
          this.$toast.error("Hãy chọn lý do tồn");
          return;
        }
        if (this.kqxl.cboLyDoTon == "") {
          this.$toast.error("Hãy chọn lý do tồn");
          return;
        }

        let vdichvuvt_id = this.tttn.dichvuvt_id;
        let _loaitb_id = this.tttn.loaihinh_id;
        if (!(vdichvuvt_id == DichVuVienThong.ADSL && _loaitb_id == LoaiHinhTB.INTERNET_MYTV)) {
          this.$toast.error("Chức năng chỉ áp dụng cho loại hình thuê bao MyTV");
          return;
        }

        let confirm = await this.$confirm(
          'Bạn chắc chắn muốn chuyển phiếu?',
          'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(() => true).catch(() => false);
        if (!confirm)
          return;

        if (!await this.CapNhat(true)) {
          return;
        }

        if (this.kqxl.cboKQXL == 1 && this.kqxl.cboNhomTon == NHOMTON.MANG_VT_LOP_TREN) {
          let kt = await this.giaophieu_media_xlsc(this.baohong_id, this.PHIEU_ID, this.huonggiao_id);
          if (kt != '1') {
            this.$toast.error('giaophieu_media_xlsc: ' + kt);
            return;
          }
          this.$toast.success("Cập nhật dữ liệu thành công!");

          //#region Đẩy sang DB KHDN
          let dsTT_KHDN = await this.Lay_ds_phieu_phoihop_khdn_v2(this.PHIEU_ID, this.$root.token.getPhanVungID(), this.userInfo.tentat);
          try {
            if (dsTT_KHDN != null && dsTT_KHDN.length > 0) {
              let tt = dsTT_KHDN[0];
              tt.TINH_LD = 100;
              let json_list_nn = JSON.stringify(tt);

              // let tinh_thicong = await this.tinh_thicong(100);
              let dem = await this.capnhat_giaophieu_ph(json_list_nn, 2);
              if (dem == null || dem !== "ok") {
                this.$toast.error("Lỗi đẩy dữ liệu KHDN! " + dem);
                return;
              }
            }
          } catch (ex) {
            this.$toast.error('Lay_ds_phieu_phoihop_khdn_v2: ' + ex.message);
            return;
          }

          //#region Cập nhật trạng thái chia phiếu với trường hợp xử lý phiếu tồn báo hỏng nhánh khác
          if (this.tag == 3) {
            await this.sp_capnhat_trangthai_chiaphieu_vsecond(this.baohong_id, this.$root.token.getUserName());
          }

          await this.HienThi_Danhsach_Tiepnhan();
          await this.HienThi_DanhSach_Tb_BaoHong();
        } else {
          this.$toast.error("Không thể chuyển phiếu với nhóm tồn khác \"Mạng VT lớp trên\"");
        }
      } catch (ex) {
        this.$toast.error("tsbtnChuyenMedia_Click: " + ex);
      }
    },

    async giaophieu_media_xlsc(vbaohong_id, vphieu_id, vhuonggiao_id) {
      try {
        let rs = await API.giaophieu_media_xlsc(this.axios, {
          vbaohong_id,
          vphieu_id,
          vhuonggiao_id
        });

        if (rs.data.error === '200') {
          return '1';
        }

        return rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return '-1';
    },

    //tsbtnChuyenIT360_Click
    async tsbtnChuyenIT360_Click() {
      try {
        if (this.baohong_id == 0 || this.dsBaoHong.length <= 0) {
          this.$toast.error("Không có dữ liệu để thực hiện!");
          return;
        }

        if (this.KHONGGIAO_HTKT_BAOHONG == 1 && this.ttHong.cboNhomTT == NHOM_TT.HOTRO_KT) {
          this.$toast.error("Bạn đang tiếp nhận phiếu Hỗ trợ KT. Không thể giao phiếu!");
          return;
        }

        let confirm = await this.$confirm(
          'Bạn chắc chắn muốn chuyển phiếu IT360?',
          'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(() => true).catch(() => false);
        if (!confirm)
          return;

        if (!await this.CapNhat(true))
          return;

        this.$alert('Gọi frmCreateIssue - Huân NV - UR2.4.063');
      } catch (ex) {
        console.log(ex);
      }
    },

    //tsbtnChuyenVip_Click
    async tsbtnChuyenVip_Click() {
      try {
        if (this.baohong_id == 0 || this.dsBaoHong.length <= 0) {
          this.$toast.error("Không có dữ liệu để thực hiện!");
          return;
        }

        if (this.KHONGGIAO_HTKT_BAOHONG == 1 && this.ttHong.cboNhomTT == NHOM_TT.HOTRO_KT) {
          this.$toast.error("Bạn đang tiếp nhận phiếu Hỗ trợ KT. Không thể giao phiếu!");
          return;
        }

        //}

        let confirm = await this.$confirm(
          'Bạn chắc chắn muốn chuyển phiếu VIP?',
          'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(() => true).catch(() => false);
        if (!confirm)
          return;

        if (!await this.CapNhat(true))
          return;

        let kt = await this.giaophieu_vip_baohong(this.baohong_id, this.PHIEU_ID, this.huonggiao_id);
        if (kt != '1') {
          this.$toast.error('giaophieu_vip_baohong: ' + kt);
          return;
        }
        this.$toast.success("Cập nhật dữ liệu thành công!");

        //#region Đẩy sang DB KHDN
        let dsTT_KHDN = await this.Lay_ds_phieu_phoihop_khdn_v2(this.PHIEU_ID, this.$root.token.getPhanVungID(), this.userInfo.tentat);
        try {
          if (dsTT_KHDN != null && dsTT_KHDN.length > 0) {
            dsTT_KHDN = dsTT_KHDN.map(item => {
              item.TINH_LD = 100;
              return item;
            })
            let json_list_nn = JSON.stringify(dsTT_KHDN);

            // let tinh_thicong = await this.tinh_thicong(100);
            let dem = await this.capnhat_giaophieu_ph(json_list_nn, 3);
            if (dem == null || dem !== "ok") {
              this.$toast.error("Lỗi đẩy dữ liệu KHDN! " + dem);
              return;
            }
          }
        } catch (ex) {
          this.$toast.error('Lay_ds_phieu_phoihop_khdn_v2: ' + ex.message);
          return;
        }

        this.HienThi_Danhsach_Tiepnhan();
        this.HienThi_DanhSach_Tb_BaoHong();
      } catch (ex) {
        console.log(e);
      }
    },

    async giaophieu_vip_baohong(vbaohong_id, vphieu_id, vhuonggiao_id) {
      try {
        let rs = await API.giaophieu_vip_baohong(this.axios, {
          vbaohong_id,
          vphieu_id,
          vhuonggiao_id
        });

        if (rs.data.error === '200') {
          return '1';
        }

        return rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return '-1';
    },

    async tsbtnChuyenDHIT_Click() {
      if (this.tttn.dichvuvt_id == DichVuVienThong.DICHVU_CNTT) {
        let dsBaoHongConvert = await this.sp_convert_baohong_cntt(this.baohong_id);
        let jsonBaoHongConvert = JSON.stringify(dsBaoHongConvert.baohong);
        let jsonGiaoPhieuConvert = JSON.stringify(dsBaoHongConvert.giaophieu);

        let msgFMs = await this.fms_cntt_chuyen_baohong_sang_khdn(jsonBaoHongConvert, jsonGiaoPhieuConvert)

        if (msgFMs != "NOK")
          this.$toast.success("Đã chuyển phiếu báo hỏng sang DHIT hỗ trợ");
        else
          this.$toast.error("Xảy ra lỗi khi chuyển phiếu báo hỏng sang DHIT");
      } else {
        this.$toast.error('Chức năng này chỉ sử dụng cho dịch vụ CNTT')
      }
    },

    async sp_convert_baohong_cntt(vbaohong_id) {
      try {
        let rs = await API.sp_convert_baohong_cntt(this.axios, {vbaohong_id})
        if (rs.data.data != null) {
          let {returnd_baohong, returnd_giaophieu} = rs.data.data
          return {
            baohong: returnd_baohong,
            giaophieu: returnd_giaophieu
          }
        }
      } catch (e) {
        console.log(e);
      }

      return null;
    },

    fms_cntt_chuyen_baohong_sang_khdn(jsonBaoHong, jsonGiaoPhieu) {
      //Trần Ngọc Anh - Bỏ API
      try {
        return 'OK';
      } catch (e) {
        console.log(e);
      }

      return 'NOK';
    },

    async tsbtnTBCungDoiCap_Click() {
      if (this.tttn.thuebao_id === 0 || this.tttn.dichvuvt_id === 0)
        return;

      let f = this.$refs.frmTT_Port_TB_CungDoiCap;
      f.thuebao_id = this.tttn.thuebao_id;
      f.hdtb_id = -1;
      f.dichvuvt_id = this.tttn.dichvuvt_id;
      f.madoicap = 0;
      await f.showModal();
    },

    async tsbtnThongTinLH_Click() {
      try {
        if (this.baohong_id == 0 || this.dsBaoHong.length <= 0) {
          this.$toast.error("Không có dữ liệu để thực hiện!");
          return;
        }

        if (this.itemDanhSachFocused.ttbh_id == TRANGTHAI_BH.HOANTHANH) {
          this.$toast.error("Phiếu đã hoàn thành, không được phép thay đổi !");
          return;
        }

        this.modelInput.matb_t = this.itemDanhSachFocused['ma_tb']
        this.modelInput.tentb_t = this.itemDanhSachFocused['ten_tb']
        this.modelInput.ngaybh_t = this.itemDanhSachFocused['ngay_bh']
        this.modelInput.nguoibh_t = this.itemDanhSachFocused['nguoi_bh']
        this.modelInput.dienthoailh_t = this.itemDanhSachFocused['dienthoai_lh']
        this.modelInput.diachild_t = this.itemDanhSachFocused['diachi_ld']
        this.modelInput.baohong_id_t = this.baohong_id
        this.modelInput.nguoi_cn_t = this.$root.token.getUserName()
        this.modelInput.ngay_cn_t = moment().format('DD/MM/YYYY')
        this.modelInput.nhanvien_id_t = this.$root.token.getNhanVienID()
        this.modelInput.may_cn_t = await this.$root.token.getMachine();
        this.modelInput.donvi_id_t = this.$root.token.getDonViID()
        this.modelInput.ip_t = await this.$root.token.getIP();
        this.$refs.reffrmThayDoiTTBH.openDiaLog()
      } catch (ex) {
        console.log(ex);
      }
    },

    async closefrmThayDoiTTBH(result) {
      if (result) await this.HienThi_DanhSach_Tb_BaoHong();
    },

    tsbtnDoiMKWifi_Click() {
      let f1 = this.$refs.frmBNMTools;
      f1.txtTraCuu = this.tttn.ma_tb;
      f1.cboChucNang = f1.MyFunctionEnum.DOIPASS_WIFI;
      this.$refs.dlg_congcuhotro.show();
    },

    async tsbtnPHKH_Click() {
      let {ma_tb} = this.tttn
      if (ma_tb == "") {
        this.$toast.error("Hãy nhập Mã thuê bao");
        return;
      }

      let f1 = this.$refs.frmTiepNhan_PHKH;
      f1.vma_tb_link = this.tttn.ma_tb;
      this.$refs.dlg_phkh.show();
      await f1.frmTiepNhan_PHKH_Load();
    },

    //Mạnh Cường báo bỏ
    // async tsbtnHTKT_Click() {
    //   let f = this.$refs.frmHtktBH;
    //   f.frmHtktBH(this.tttn.ma_tb);
    //   await f.showModal();
    // },

    async LuuTTPort_Click() {
      try {
        let lst_chon = this.$refs.dsBaoHong.getSelectedRecords();
        if (lst_chon.length === 0) {
          this.$root.$toast.error("Bạn chưa chọn thuê bao.");
          return;
        }

        let msg = ''
        if (this.kqxl.cboKQXL == 0) {
          msg = 'Bạn thật sự muốn lưu trạng thái Port test không ?';
        } else {
          msg = 'Bạn thật sự muốn báo tồn không ?'
        }

        let confirm = await this.$confirm(msg, 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(() => true).catch(() => false);
        if (!confirm)
          return;

        for (const lst of lst_chon) {
          await this.CapNhat_TT_Port_BH(lst.baohong_id, lst.ht_port);
        }

        this.$toast.success("Lưu Test port thành công!");
      } catch (ex) {
        console.log(ex);
      } finally {
        this.loading(false);
      }
    },

    async CapNhat_TT_Port_BH(p_baohong_id, p_ht_port) {
      try {
        await API.sp_capnhat_tt_port_bh(this.axios, {
          p_baohong_id,
          p_ht_port
        })
      } catch (e) {
        console.log(e);
      }
    },

    dsbhHienThiTTKT(item) {
      this.loading(true);
      this.HT_THONGSO_KYTHUAT(item.thuebao_id, item.dichvuvt_id);
      this.loading(false)
    },

    async dsbhChoXuLy(item) {
      try {
        let lst_chon = this.$refs.dsBaoHong.getSelectedRecords();
        if (lst_chon.length === 0) {
          this.$root.$toast.error("Bạn chưa chọn thuê bao.");
          return;
        }

        let confirm = await this.$confirm(`Bạn thật sự muốn cập nhật trạng thái ${lst_chon.length} phiếu báo hỏng này không?`,
          'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(() => true).catch(() => false);
        if (!confirm)
          return;

        for (let i = 0; i < lst_chon.length; i++) {
          await this.sp_capnhat_trangthai_phieu_bh(lst_chon[i]["phieu_id"], TRANGTHAI_BH.CHO_XULY);
        }

        this.$toast.success("Cập nhật trạng thái phiếu thành công!");
        await this.HienThi_DanhSach_Tb_BaoHong();
      } catch (exp) {
        console.log(exp);
      }
    },

    async sp_capnhat_trangthai_phieu_bh(vphieubh_id, vttbh_id) {
      try {
        await API.sp_capnhat_trangthai_phieu_bh(this.axios, {
          vphieubh_id,
          vttbh_id
        })
      } catch (e) {
        console.log(e);
      }
    },

    //Huy Giu Phieu
    tsmGiuPhieu_Click() {
      this.HUY_GIUPHIEU(1);
    },

    tsmHuyGiuPHieu_Click() {
      this.HUY_GIUPHIEU(2);
    },

    async HUY_GIUPHIEU(kieu) {
      try {
        if (this.dsBaoHong == null || this.dsBaoHong.length === 0) {
          this.$toast.error("Không có danh sách phiếu không thể thực hiện");
          return;
        }

        let lst_chon = this.$refs.dsBaoHong.getSelectedRecords();
        if (lst_chon.length === 0) {
          this.$root.$toast.error("Bạn chưa chọn thuê bao.");
          return;
        }


        let dt_kt = lst_chon.map(item => {
          return {
            PHIEU_ID: item.phieu_id,
            BAOHONG_ID: item.baohong_id
          }
        });

        //Nếu có nhân viên giữ của 1 phiếu bất kỳ return
        let json_list = JSON.stringify(dt_kt);
        if (!await this.KIEMTRA_PHIEU(json_list, 0, kieu))
          return;


        let kt = await this.sp_capnhat_nhanvien_giuphieu(json_list, kieu)
        if (kt != '1') {
          this.$toast.error("Có lỗi khi thực hiện " + kt);
          return;
        }

        this.$toast.success(kieu === 1 ? "Giữ phiếu thành công" : "Huỷ giữ phiếu thành công");
        this.HienThi_DanhSach_Tb_BaoHong();
      } catch (ex) {
        console.log(ex);
        this.$toast.error("Có lỗi khi thực hiện " + ex);
      }
    },

    async sp_capnhat_nhanvien_giuphieu(json_danhsach, kieu) {
      try {
        let rs = await API.sp_capnhat_nhanvien_giuphieu(this.axios, {
          "ip_cn": await this.$root.token.getIP(),
          "may_cn": await this.$root.token.getMachine(),
          "nguoi_cn": this.$root.token.getUserName(),
          "nhanvien_id": this.$root.token.getNhanVienID(),
          json_danhsach,
          kieu,
        });

        return rs.data.error === '200' ? '1' : rs.data.message_detail;
      } catch (e) {
        console.log(e);
      }

      return '-1';
    },

    //TTKenh trang
    async dsbhTTKenhTrang() {
      this.$alert('frm Kênh Trắng - Ngọc Phùng');
      let _o = this.itemDanhSachFocused.thuebao_id;
      if (this.itemDanhSachFocused.nguoi_cn.toLowerCase() === "webservice_hni" || _o == "0") {

        if (this.CHOPHEP_GIUPHIEU == 1) {
          if (!await this.KIEMTRA_PHIEU("0", this.itemDanhSachFocused.phieu_id, 3))
            return;
        }

        // frmTTKenhTrang _frmkenhtrang = new frmTTKenhTrang();
        // _frmkenhtrang.dichvuvt_id = this.tttn.dichvuvt_id;
        // _frmkenhtrang.ten_tb = this.tttn.ten_tb;
        // _frmkenhtrang.diachi_ld = this.tttn.diachi_ld;
        // _frmkenhtrang.diachi_tb = this.tttn.diachi_tb;
        // _frmkenhtrang.txtDiaChiLD.Text = diachi_ld;
        // _frmkenhtrang.txtDiaChiTB.Text = diachi_tb;
        // _frmkenhtrang.txtDiaChiKH.Text = diachi_tb;
        // _frmkenhtrang.txtTenTB.Text = ten_tb;
        // _frmkenhtrang.txtSoAoWan.Text = ma_lt;
        // _frmkenhtrang.txtTelKH.Text = sodt_lh;
        // _frmkenhtrang.ma_bh = this.itemDanhSachFocused.ma_bh;
        // if (string.IsNullOrEmpty(txtTKMaTB.Text)) {
        //   if (dichvuvt_id == DichVuVienThong.MEGAWAN) {
        //     _frmkenhtrang.txtMaTBWan.Text = ma_lt;
        //     _frmkenhtrang.txtMaTSL.Text = "";
        //   } else {
        //     _frmkenhtrang.txtMaTSL.Text = ma_lt;
        //     _frmkenhtrang.txtMaTSL.Text = ma_lt;
        //   }
        //   _frmkenhtrang.ma_tb = ma_lt;
        //
        // } else {
        //   _frmkenhtrang.ma_tb = txtTKMaTB.Text;
        //
        // }
        // _frmkenhtrang.ma_vnpt = txtTKMaTB.Text;
        // _frmkenhtrang.ShowDialog();
      }
    },

    async frmKenhTrang_ok(_frmkenhtrang) {
      if (_frmkenhtrang._trangthaitb_id == TrangThaiTB.THANHLY || _frmkenhtrang.trangthaitb_id == TrangThaiTB.THANHLY_NO) {
        this.$toast.error("Thuê bao đã thanh lý, bạn không thể thực hiện báo hỏng!");
        return;
      }

      let confirm = await this.$confirm(`Bạn có chắc chắn chọn thuê bao này để thực hiện cập nhật không`,
        'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(() => true).catch(() => false);
      if (!confirm)
        return;

      //thực hiện kiểm tra xem thuê bao này đã được gán cho báo hỏng nào đang ở trạng thái <> 6 không?
      let _err = await this.KIEMTRA_THUEBAO_BAOHONG(_frmkenhtrang.thuebao_id, 3);
      if (_err !== "1") {
        this.$toast.error('KIEMTRA_THUEBAO_BAOHONG: ' + _err);
        this.tttn.ma_tb = "";
        return;
      }
      this.tttn.ma_tb = _frmkenhtrang.ma_tb;
      this.tttn.thuebao_id = _frmkenhtrang.thuebao_id;
      //#region "Thực hiện update vào dữ liệu baohong_tmp"
      if (_frmkenhtrang.thuebao_id == 0) {
        this.$toast.error("Hãy thực hiện cập nhật thông tin thuê bao kênh trắng!");
        return;
      }

      try {
        API.capnhat_baohong_kenhtrang(this.axios, {
          "vbaohong_id": this.baohong_id,
          "vloaitb_id": _frmkenhtrang.loaitb_id,
          "vma_lt": _frmkenhtrang._maLT,
          "vthuebao_id": _frmkenhtrang.thuebao_id,
        });
      } catch (ex) {
        console.log(ex);
        this.$toast.error('capnhat_baohong_kenhtrang: ' + ex);
        return;
      }

      this.HienThi_DS_LichSu_BaoHong();
      this.HienThi_DanhSach_Tb_BaoHong();
    },

    async lblSuCoTTVT_Click() {
      try {
        this.$refs.frmXemThongBaoSuCo.initData();
        this.$refs.frmXemThongBaoSuCo.showModal();
      } catch (e) {
        console.log(e);
      }
    },

    async txtDienThoaiBH_ButtonClick() {
      //Trần Ngọc Anh báo bỏ
      return;
      try {
        if (this.$root.token.getPhanVungID() != this.phanVung.HCM) return;
        //Gọi nhập mới
        this.btnNhapMoi_Click();
        this.tskt = [];

        //End gọi nhập mới
        let kq = this.IPCCHCM_GetPhoneNumber(await this.$root.token.getIP());
        let arrKQ = kq.split(':');
        if (arrKQ[0] === "0") {
          this.tttn.ipcc = arrKQ[1];
        } else {
          this.$toast.error(arrKQ[1]);
        }

        let txtDienThoaiBH = this.tttn.ipcc;
        if (txtDienThoaiBH == null || txtDienThoaiBH === '') return;
        if (Number.isInteger(txtDienThoaiBH)) {
          //Hiển thị lịch sử báo hỏng theo thuê bao tìm được, Kiểm tra nếu kết quả là số mới gọi
          await this.HienThiLichSuBH_TheoDienThoaiBH(txtDienThoaiBH);
          this.tttn.dien_thoai = txtDienThoaiBH;
          //Hiển thị thông tin popup - Bảng do a Trí - HCM cung cấp
          //đang ko có bảng ở onebss, mà ở tỉnh cũng có
          // if (this.$root.token.getPhanVungID() == this.phanVung.HCM) {
          //   let dt = bangts.GetDataSql("select * from ttkdhcm_ktnv.daidien_congty where sdt_daidien = '" + txtDienThoaiBH + "'");
          //   {
          //     if (dt.Count > 0) {
          //       this.$alert("(" + txtDienThoaiBH + " - " + dt[0]["ten_daidien"] +
          //         ") đại diện cho " + dt[0]["ten_congty"] + " " + dt[0]["ghi_chu"]
          //       );
          //     }
          //   }
          // }
          this.tttn.ma_tb = txtDienThoaiBH;
        }
      } catch (ex) {
        this.$toast.error("Lỗi: " + ex);
      }
    },

    async HienThiLichSuBH_TheoDienThoaiBH(vdienthoai_bh) {
      try {
        let ds = await this.LAY_LICHSU_BAOHONG_IPCC(vdienthoai_bh);

        if (ds != null && ds.length > 0) {
          this.lsBaoHong = ds;

          try {
            this.tttn.nguoi_bh = ds[0]["nguoi_bh"];
            this.tttn.dien_thoai = ds[0]["dienthoai_lh"];
          } catch (exp) {
          }
        } else {
          this.lsBaoHong = [];
          this.tttn.nguoi_bh = "";
          this.tttn.dien_thoai = "";
        }
      } catch (exp) {
      }
    },

    async LAY_LICHSU_BAOHONG_IPCC(vdienthoai_bh) {
      try {
        let rs = await API.lay_lichsu_baohong_ipcc(this.axios, vdienthoai_bh);
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log(e);
      }
      return null;
    },

    togglePanel(panelName) {
      let obj = `.i-${panelName}`;
      if ($(obj).hasClass('fa-minus-square-o')) {
        $(`.${panelName}`).hide();
        $(obj).removeClass('fa-minus-square-o');
        $(obj).addClass('fa-plus-square-o');
      } else {
        $(`.${panelName}`).show();
        $(obj).removeClass('fa-plus-square-o');
        $(obj).addClass('fa-minus-square-o');
      }
    },

    async SP_DS_BAOHONG_BC_BAOHONG_ID(baohong_id) {
      try {
        let rs = await API.SP_DS_BAOHONG_BC_BAOHONG_ID(this.axios, {baohong_id})
        return rs.data.data || [];
      } catch (e) {
        console.log('SP_DS_BAOHONG_BC_BAOHONG_ID: ' + e);
      }

      return [];
    },

    async LAY_TINHTHICONG_THEO_MATB(matb) {
      try {
        let rs = await API.lay_tinh_theo_matb(this.axios, {matb})
        let data = rs.data.data;
        if (Array.isArray(data)) {
          return {
            tinhthicong_id: data[0].tinh_thicong
          };
        } else {
          return data || {
            tinhthicong_id: 0
          };
        }
      } catch (e) {
        console.log('SP_DS_BAOHONG_BC_BAOHONG_ID: ' + e);
      }

      return {
        tinhthicong_id: 0
      };
    },

    async LAY_THUEBAOID_THICONG_v2(vtinh, vthuebao_id) {
      try {
        let rs = await API.lay_thuebaoid_thicong_v2(this.axios, {vtinh_id: vtinh.tinhthicong_id, vthuebao_id});
        return rs.data.data && rs.data.data.length > 0 ? rs.data.data[0].thuebao_kn_id : 0;
      } catch (e) {
        console.log('SP_DS_BAOHONG_BC_BAOHONG_ID: ' + e);
      }

      return 0;
    },

    async Lay_DanhBa_Theo_MaTB_TinhTB(tinh, in_ma_tb, in_dichvuvt_id) {
      try {
        let rs = await API.Lay_DanhBa_Theo_MaTB_TinhTB(this.axios, {
          in_ma_tb,
          in_dichvuvt_id
        });
        return rs.data.data || [];
      } catch (e) {
        console.log('Lay_DanhBa_Theo_MaTB_TinhTB: ' + e);
      }

      return [];
    },

    chkPhieuDaSua_CheckedChanged() {
      if (this.ttHong.chkPhieuDaSua) {
        this.ttHong.chkPhieuTra = false;
        this.ttHong.chkPhieuDaChuyenTinh = false;
        this.ttHong.chkPhieuChoXL = false;
        this.tt_sua = 1;
      } else {
        this.tt_sua = 0;
      }

      this.HienThi_DanhSach_Tb_BaoHong();
    },

    chkPhieuDaChuyenTinh_CheckedChanged() {
      if (this.ttHong.chkPhieuDaChuyenTinh) {
        this.ttHong.chkPhieuTra = false;
        this.ttHong.chkPhieuChoXL = false;
        this.ttHong.chkPhieuDaSua = false;
        this.tt_sua = 2;
      } else {
        this.tt_sua = 0;
      }

      this.HienThi_DanhSach_Tb_BaoHong();
    },

    async kiemtra_thuebao_baohong_119(tinh, vthuebao_id, vkieu) {
      try {
        let rs = await API.kiemtra_thuebao_baohong_119(this.axios, {
          "tinhthicong_id": tinh.tinhthicong_id,
          vthuebao_id,
          vkieu
        });

        return rs.data.error == 200 ? rs.data.data : rs.data.message_detail;
      } catch (e) {
        console.log(e);
        return 'Lỗi kiemtra_thuebao_baohong_119: ' + e.message;
      }
    },

    async sp_lay_thuebaoid_thicong(vtinh_id, vthuebao_id) {
      try {
        let rs = await API.sp_lay_thuebaoid_thicong(this.axios, {
          vtinh_id, vthuebao_id
        });

        return rs.data.error == 200 ? rs.data.data : rs.data.message_detail;
      } catch (e) {
        console.log(e);
        return 'Lỗi sp_lay_thuebaoid_thicong: ' + e.message;
      }
    },

    CapNhat_DonVi_PhoiHop(vbaohong_id) {
      API.sp_delete_baohong_dvph(this.axios, {vbaohong_id})

      for (let i = 0; i < this.dsbh.cboDonViPhoiHop; i++) {
        API.sp_insert_baohong_dvph(this.axios, {
          vbaohong_id,
          vdoitac_id: this.dsbh.cboDonViPhoiHop[i]
        })
      }
    },

    async LAY_DS_HUONGGIAO_BAOHONG_V2(vquytrinh_id, vloaidv_id) {
      try {
        let rs = await API.LAY_DS_HUONGGIAO_BAOHONG_V2(this.axios, {
          vquytrinh_id:vquytrinh_id,
          vloaidv_id:vloaidv_id,
          tinhthicong_id:this.$root.token.getPhanVungID()
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi LAY_DS_HUONGGIAO_BAOHONG_V2: ' + e);
      }

      return null;
    },

    async GiaoPhieu_BH_V2(vbaohong_id, vdonvi_giao_id, vdonvi_nhan_id, nhanvien_id, vhuongiao_id, vphieu_cha_id, vttbh_id, vngay_cn, vma_nd, may_cn, vnd_giao) {
      try {
        let rs = await API.giaophieu_bh_v2(this.axios, {
          // "tinhthicong_id": 26,
          vbaohong_id,
          vdonvi_giao_id,
          vdonvi_nhan_id,
          vhuongiao_id,
          vngay_cn,
          vma_nd,
          vnd_giao,
          vphieu_cha_id,
          vttbh_id,
          tinhthicong_id:this.$root.token.getPhanVungID()
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi GiaoPhieu_BH_V2: ' + e);
      }

      return null;
    },

    async sp_lay_ds_baohong_dvph(vbaohong_id) {
      try {
        let rs = await API.sp_lay_ds_baohong_dvph(this.axios, {
          vbaohong_id
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi sp_lay_ds_baohong_dvph: ' + e);
      }

      return [];
    },

    async fn_capnhat_tiepnhanbh_bancheo(vds, dsbh_data, dsbh_dv, dsbh_bc_data) {
      try {
        let rs = await API.fn_capnhat_tiepnhanbh_bancheo(this.axios, {
          vds: JSON.stringify(vds),
          dsbh_data: JSON.stringify(dsbh_data),
          dsbh_dv: JSON.stringify(dsbh_dv),
          dsbh_bc_data: JSON.stringify(dsbh_bc_data),
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi fn_capnhat_tiepnhanbh_bancheo: ' + e);
      }

      return '-1';
    },

    async Lay_dieukien_gp_khi_nhanhong(vbaohong_id, vdonvi_id, vnhanvien_id) {
      try {
        let rs = await API.lay_dieukien_gp_khi_nhanhong(this.axios, {
          vbaohong_id, vdonvi_id, vnhanvien_id
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi Lay_dieukien_gp_khi_nhanhong: ' + e);
      }

      return [];
    },

    async LAY_DS_BAOHONG_CHUAGIAO(vhuonggiao_id, vdonvi_id, vnhanvien_id, vma_tb) {
      try {
        let rs = await API.lay_ds_baohong_chuagiao(this.axios, {
          vhuonggiao_id, vdonvi_id, vnhanvien_id, vma_tb
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi LAY_DS_BAOHONG_CHUAGIAO: ' + e);
      }

      return [];
    },

    async AddphieuTH(baohong_id, donvi_id, donvi_nhan_id, huonggiao_id, tudong, phieu_cha_id, ttbh_id) {
      let dsGiaoPhieu = [];
      let rowdata = Utils.GIAOPHIEU_BH_DATA();
      this.PHIEU_ID = await this.POST_GET_KEYS("PHIEU_ID");
      rowdata.PHIEU_ID = this.PHIEU_ID;
      rowdata.BAOHONG_ID = baohong_id;
      rowdata.DONVI_GIAO_ID = donvi_id;
      rowdata.NHANVIEN_GIAO_ID = this.$root.token.getNhanVienID();
      rowdata.DONVI_NHAN_ID = donvi_nhan_id;
      rowdata.TINHTRANG = 1;
      rowdata.HUONGGIAO_ID = huonggiao_id;
      rowdata.NGAYGIAO = moment().format(this.formatDate);
      rowdata.ND_GIAO = "Giao phiếu tự động";
      rowdata.TTPH_ID = TRANGTHAI_PH.MOI;
      rowdata.PHIEU_CHA_ID = phieu_cha_id;
      rowdata.TTBH_ID = ttbh_id;
      rowdata.NGUOI_CN = this.$root.token.getUserName();
      rowdata.NGAY_CN = moment().format(this.formatDate);
      rowdata.MAY_CN = this.$root.token.getMachine();
      dsGiaoPhieu.push(rowdata);

      return dsGiaoPhieu;
    },

    async Insert_tinhtrang(baohong_id, vphieu_id, ds_tt, ds_nn, ds_ton, ghichu, kieu) {
      try {
        let rs = await API.sp_insert_tinhtrang_bh(this.axios, {
          "nnbh": ds_nn,
          "p_baohong_id": baohong_id,
          "p_ghichu": ghichu,
          "p_kieu": kieu,
          "p_phieu_id": vphieu_id,
          "p_trangthaixl": 0,
          "tonbh": ds_ton,
          "ttbh": ds_tt
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi Insert_tinhtrang: ' + e);
      }

      return null;
    },

    async SP_DS_GIAOPHIEU_BAOHONG_PHIEU_ID(vphieu_id) {
      try {
        let rs = await API.sp_ds_giaophieu_baohong_phieu_id(this.axios, {
          vphieu_id
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi SP_DS_GIAOPHIEU_BAOHONG_PHIEU_ID: ' + e);
      }

      return null;
    },

    async SP_DS_BAOHONG_BC_TRANGTHAI(vbaohong_id) {
      try {
        let rs = await API.sp_ds_baohong_bc_trangthai(this.axios, {
          vbaohong_id
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi SP_DS_BAOHONG_BC_TRANGTHAI: ' + e);
      }

      return null;
    },

    async TaoDuLieu_BaoHong_Temp(themmoi) {
      let {
        thuebao_id, dichvuvt_id, loaihinh_id, ngay_bh, nguoi_bh, dien_thoai,
        checkHenTL, ngay_hentl_den, ngay_hentl_tu
      } = this.tttn;

      let dsBH_TMP = [];
      let row = Utils.BAOHONG_TMP_DATA();
      row.THUEBAO_ID = thuebao_id;
      row.DICHVUVT_ID = dichvuvt_id;
      row.LOAITB_ID = loaihinh_id;
      row.MA_LT = this.ma_lt;
      row.NGAY_BH = ngay_bh;
      row.NGUOI_BH = nguoi_bh;
      row.DIENTHOAI_LH = dien_thoai;
      row.GHICHU_HONG = this.ttHong.txtGhiChu;

      if (checkHenTL) {
        row.HENSUATU = ngay_hentl_tu;
        row.HENSUADEN = ngay_hentl_den;
      }

      row.MAY_CN = await this.$root.token.getMachine();
      row.NGAY_CN = moment().format(this.formatDate);
      row.NGUOI_CN = this.$root.token.getUserName();
      row.NHANVIEN_ID = this.tinh.nhanvien_id;
      row.DONVI_ID = this.$root.token.getDonViID();

      if (this.rsc_id != 0) {
        row.RSC_ID = this.rsc_id;
      }
      dsBH_TMP.push(row);
      this.dsBH_TMP = [...dsBH_TMP];
    },

    async fn_chotiepnhan_tiepnhanbh_bancheo(dsbh_tmp, dsbh_bc_tmp) {
      try {
        let rs = await API.fn_chotiepnhan_tiepnhanbh_bancheo(this.axios, {
          dsbh_tmp: JSON.stringify(dsbh_tmp),
          dsbh_bc_tmp: JSON.stringify(dsbh_bc_tmp)
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi fn_chotiepnhan_tiepnhanbh_bancheo: ' + e);
      }

      return null;
    },

    async sp_ds_baohong_bc_loaihd(vbaohong_id) {
      try {
        let rs = await API.sp_ds_baohong_bc_loaihd(this.axios, {
          vbaohong_id
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi sp_ds_baohong_bc_loaihd: ' + e);
      }

      return [];
    },

    async sp_ds_giaophieu_baohong_huonggiao(vbaohong_id_ban, vdonvi_nhan_id) {
      try {
        let rs = await API.sp_ds_giaophieu_baohong_huonggiao(this.axios, {
          vbaohong_id_ban, vdonvi_nhan_id
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi sp_ds_giaophieu_baohong_huonggiao: ' + e);
      }

      return [];
    },

    async GiaoPhieu_BH(vbaohong_id, vdonvi_giao_id, vdonvi_nhan_id, vnhanvien_id, vhuongiao_id,
                       vphieu_cha_id, vttbh_id, ngay_cn, vma_nd, may_cn, vnd_giao) {
      try {
        let rs = await API.giaophieu_bh(this.axios, {
          vbaohong_id, vdonvi_giao_id, vdonvi_nhan_id, vhuongiao_id,
          vma_nd, vnd_giao, vphieu_cha_id, vttbh_id
        });

        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('Lỗi GiaoPhieu_BH: ' + e);
      }

      return null;
    },

    async Nhan_hong(tinh, ds, dsBH_BC, loadtb_id, thutu, dactrung, may_cn, ngay_cn, nguoi_cn, vthuebao_id, vbaohong_id, net_giao) {
      let huonggiaotn_id = 0;
      let tinhthicong_id = this.tinh_tc;
      let dt = await this.LayHuongGiao_BaoHong_TheoThuTu_BanCheo(tinhthicong_id, loadtb_id, thutu);
      if (dt != null && dt.length > 0) {
        ds[0]["QUYTRINH_ID"] = dt[0]["quytrinh_id"];
        huonggiaotn_id = parseInt(dt[0]["huonggiao_id"]);
      }

      let khachhang_id = 0;
      dt = await API.sp_ds_hd_thanhtoan_thuebao_id(this.axios, {vthuebao_id});
      if (dt != null && dt.length > 0)
        khachhang_id = parseInt(dt[0]["khachhang_id"]);

      await this.Them_nhanhong_cho_tinh(tinhthicong_id, ds, dsBH_BC, huonggiaotn_id, khachhang_id, dactrung, may_cn, ngay_cn, nguoi_cn, vthuebao_id, vbaohong_id, net_giao);
    },

    async Them_nhanhong_cho_tinh(tinhthicong_id, ds, dsBC, huonggiaotn_id, khachhang_id, dactrung, may_cn, ngay_cn, nguoi_cn, vthuebao_id, vbaohong_id, net_giao) {
      let baohong_id = parseInt(ds[0]["BAOHONG_ID"]);
      ds[0]["BAOHONG_ID_HNI"] = vbaohong_id;
      dsBC[0]["BAOHONG_ID"] = vbaohong_id;
      await API.sp_insert_baohong(this.axios, {tinhthicong_id, p_json_baohong: JSON.stringify(ds)})
      await API.sp_insert_baohong_bc(this.axios, {tinhthicong_id, p_json_baohong_bc: JSON.stringify(dsBC)})

      await API.fn_smp_themtinhtrang_bh(this.axios, {
        tinhthicong_id,
        "vtinhtrang_id": 200,
        "vbaohong_id": baohong_id
      });

      if (net_giao == 0)
        await this.CHUYEN_DL_BH_SANG_GIAOPHIEU(tinhthicong_id, ds, huonggiaotn_id, may_cn, ngay_cn, nguoi_cn);
      if (vthuebao_id != -1) {
        let dsBH_DV = [];
        let dtDV = await this.Lay_ds_donvi_theo_loaidv_bh_BanCheo(tinhthicong_id, vthuebao_id, LOAI_DV.TRAM_VT + "," + LOAI_DV.DAI_BR);
        let tram_id = 0;
        for (let item of dtDV) {
          let row = Utils.BAOHONG_DV_DATA();
          tram_id = parseInt(item["donvi_id"]);
          row.BAOHONG_ID = baohong_id;
          row.DONVI_ID = tram_id;
          row.LOAIDV_ID = parseInt(item["loaidv_id"]);
          row.KIEUDV_ID = parseInt(item["kieudv_id"]);
          dsBH_DV.push(row);
        }

        await API.sp_insert_baohong_dv(this.axios, {tinhthicong_id, p_json_baohong_dv: JSON.stringify(dsBH_DV)});
        await API.sp_update_db_khachhang_dactrung(this.axios, {
          tinhthicong_id,
          "vdactrung": dactrung,
          "vkhachhang_id": khachhang_id
        });
      }
    },

    async CHUYEN_DL_BH_SANG_GIAOPHIEU(tinhthicong_id, ds, huonggiao_id, may_cn, ngay_cn, nguoi_cn) {
      let baohong_id = 0;
      let thuebao_id = 0;
      for (let i = 0; i < ds.length; i++) {
        baohong_id = parseInt(ds[i]["BAOHONG_ID"]);
        thuebao_id = parseInt(ds[i]["THUEBAO_ID"]);
        await API.chuyen_dl_bh_sang_giaophieu(this.axios, {
          tinhthicong_id,
          vbaohong_id: baohong_id,
          vhuonggiao_id: huonggiao_id
        });

        await API.capnhat_tt_goi_kythuat(this.axios, {
          tinhthicong_id,
          "vthuebao_id": thuebao_id,
          "vbaohong_id": baohong_id
        });
      }
    },

    async newMapData(url, params) {
      try {
        let rs = await API.newMapId(this.axios, url, params);
        return this.getDataFromAPI(rs);
      } catch (e) {
        console.log('newMapData');
      }
    },
    async checkPortOnClick() {
        this.checkPortDialog.isVisiable = true
        this.$refs.checkPort.show()
    },
    async checkPortOnClose() {
        this.checkPortDialog.isVisiable = false
    },
  },
}
</script>

<style>
.popup-giao-phieu .page-content, .popup-show-port .page-content {
  position: inherit;
}

.popup-giao-phieu .breadcrumb-top, .popup-show-port .breadcrumb-top, .popup-phkh .breadcrumb-top {
  display: none;
}
</style>
