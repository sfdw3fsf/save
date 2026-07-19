<template src="./index.html"></template>
<script>
  import Vue from 'vue'
  import breadcrumb from '@/components/breadcrumb'
  import TextComponent from './component/text'
  import NumberText from './component/numbertext'
  import ComboboxComponent from './component/combobox'
  import DateTimeComponent from './component/datetime'
  import DateTimeHourComponent from './component/datetimehour'
  import ComboCheckBox from './component/ComBoCheckBox'
  import ButtonEdit from './component/buttonedit'
  import Text from './component/index'
  import TextReadOnly from './component/textreadonly'
  import CheckBox from './component/checkbox'
  import vascService from './vasc-service'
  import vdcService from './vdc-service'
  import ccbsService from './ccbs-service'
  import ModalChonDiaChi from '../../../CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonDiaChi/Modal'
  import ModalChonDiaChi2 from './Popups/popupChonDiaChi'
  import LoaiKH from './LoaiKH'
  import ChiTietNganhNghe from './ChiTietNganhNghe'
  import get from 'lodash/get'
  import moment from 'moment'
  import PopupTimKiemNhanVienThuCuoc from './TimNhanVienThuCuoc'
  import Gankhuvuc from './Popups/popupGanKhuVucQuanLyThueBao'
  import frmDSTBCungMa from './frmDSTBCungMa/frmDSTBCungMa'
  import ChonDonVi from './ChonDonVi'
  import DateTimeLib from '@/utils/DateTimeLib'
  import { KIEULAPDAT, DICHVUVIENTHONG, LOAIHINHTB, HINHTHUCTHANHTOAN } from './Enums'
  import ComboboxGrid2 from '@/components/Controls/ComboboxGrid2'
  const VueInputMask = require('vue-inputmask').default
  Vue.use(VueInputMask)
  import BssRequiredMarker from '@/components/BssRequiredMarker'
  import BssErrorMarker from '@/components/BssErrorMarker'
  import SearchAccount from '@/modules/search/subscriber/SearchAccount'
  import ChangeCustomerInfor from '@/modules/contract/setup/ChangeCustomerInfor'
  import {
    DichVuVienThong, DUNG_THU, FSECURE_KM_12_MONTHS, KHOANMUC_TT,
    KieuLapDat,
    LOAI_DV, Loai_KM,
    LoaiHinhTB, LoaiHopDong,
    LOAINODE_MGWAN, LOAISO, LOAITBI_ADSL,
    TRANGBI
  } from '../../setup/InstallNewSubs/Enums'
  import API from '../../setup/InstallNewSubs/InstallNewSubsAPI'
  import cache from '../../setup/InstallNewSubs/cache'
  import cacheDM from '../../setup/InstallNewSubs/cacheDM'
  var templateNgayHuy = function() {
    return {
      template: {
        template: `
          <div class="">
            <input class="form-control" type="text" v-model="data.ngay_huy" style="box-shadow: unset; border: none;">
          </div>
        `,
        data() {
          return {
            data: {},
            ngay_huy: null
          }
        },
        computed: {
          parent() {
            return this.$parent.$parent.$parent
          },
          dulieugrid() {
            return JSON.parse(JSON.stringify(this.options.tienkhoan))
          }
        },
        methods: {},
        watch: {
          'data.ngay_huy': function(val) {
            this.parent.update_ngayhuy(val)
          },
          'grcDanhSach.tien': {
            handler(val) {
              this.txtVatKM = 0
              this.txtTienKM = 0
              this.txtTongTienKM = 0
              this.grcDanhSach = this.grcDanhSach
              for (let i = 0; i < this.options.tienkhoan.length; i++) {
                this.txtVatKM += this.options.tienkhoan[i].vat
                this.txtTienKM += this.options.tienkhoan[i].tien
                this.txtTongTienKM += this.options.tienkhoan[i].tongtien
              }
              this.txtVatKM = Number(this.txtVatKM).toLocaleString().replace('.', ',')
              this.txtTienKM = Number(this.txtTienKM).toLocaleString().replace('.', ',')
              //this.txtTongTienKM = Number(this.txtTongTienKM).toLocaleString().replace('.', ',')
              // this.txtTongTienKM = this.addCommas(this.txtTongTienKM)
            },
            deep: true
          }
        }
      }
    }
  }
  export default {
    name: 'ChangeCustomerInfo',
    components: {
      breadcrumb,
      ModalChonDiaChi,
      ModalChonDiaChi2,
      LoaiKH,
      ChiTietNganhNghe,
      PopupTimKiemNhanVienThuCuoc,
      BssRequiredMarker,
      BssErrorMarker,
      Gankhuvuc,
      frmDSTBCungMa,
      ChonDonVi,
      SearchAccount,
      ChangeCustomerInfor,
      ComboboxGrid2
    },
    props: {
      Tag: {
        type: Number,
        default: 0
      },
      propMaTB:{
        type:String,
        default:''
      },
      isPopup: {
        type: Boolean,
        default: false
      },
      propDichvuvt_id: {
        type: Number,
        default: 0
      },
      row_id: {
        type: String,
        default: ''
      },
    },
    created() {
      this.$watch(
        () => JSON.stringify(this.ItemList),
        (newValue, oldValue) => {
          //do your stuff
          if (this.ItemList) {
            if (this.$refs['SO_THANG']) {
              if (this.$refs['SO_THANG'][0].value > 999) {
                this.$refs['SO_THANG'][0].value = 999
              }
              console.log(this.$refs['SO_THANG'][0].value)
              let itemNew = JSON.parse(newValue).find(item => item.FIELD_NAME === 'SO_THANG')
              let itemOld = JSON.parse(oldValue).find(item => item.FIELD_NAME === 'SO_THANG')
              if (itemOld.FIELD_VALUE !== itemNew.FIELD_VALUE) {
                if (this.$refs['NGAY_BD'][0].startDate) {
                  // let data = moment(new Date(String(this.$refs["NGAY_BD"][0].startDate))).toDate()
                  // moment(new Date(String(this.$refs["NGAY_KT"][0].startDate))).format('dd/MM/yyyy')
                  this.$refs['NGAY_KT'][0].startDate = moment(this.$refs['NGAY_BD'][0].startDate).add(this.$refs['SO_THANG'][0].value, 'months').toDate()
                  console.log(this.$refs['NGAY_BD'][0].startDate)
                  console.log(this.$refs['NGAY_KT'][0].startDate)
                }
              }
            }
          }
        }
      )
    },
    //begin tinh cuoc ngay BSS-88020
    computed: {
      is_nv_vnpti() {
        return this.$root.token.getPhanVungID()==98
      },
      DT_DV() {
        this.DT_CONTROLS.key
        return (idx) => {
          let list = this.DT_CONTROLS.data[idx].map((c) => ({ id: c.DONVI_ID, text: c.TEN_DV }))
          if (list && list.length > 0) {
            if (!this.DT_CONTROLS.value[idx] || this.DT_CONTROLS.value[idx] <= 0)
              this.DT_CONTROLS.value[idx] = list[0].id && list[0].id > 0 ? list[0].id : list[1] && list[1].id ? list[1].id : 0
            return list
          } else return []
        }
      },
      hasHDKH: function() {
        return !(this.hdkh_id == null || this.hdkh_id == undefined || this.hdkh_id == 0)
      },
      ChangeCustomerInfor_data: function() {
        return {
          hdkh_id: this.hdkh_id
        }
      },
      thoiGianKM_text: function() {
        return this.is_tinhcuoc_ngay ? 'Ngày' : 'Tháng'
      },
      treeSelectOptions() {
        return this.dsKhoanMucTT.map(item => {
          return { id: item.khoanmuctt_id, label: item.tentat_kmtt }
        })
      },
      cloaihinh_tb() {
        if (!this.listbox.loaihinh_tb.list || this.listbox.loaihinh_tb.list.length <= 0) return []
        let list = this.listbox.loaihinh_tb.list.filter((lh) => lh.dichvuvt_id == this.listbox.dichvu_vt.value).map((lh) => ({
          id: lh.loaitb_id,
          text: lh.loaihinh_tb
        }))
        if (!list || list.length <= 0) {
          this.listbox.loaihinh_tb.value = 0
          return []
        }
        var i = 0
        for (; i < list.length; i++) {
          if (list[i].id == this.listbox.loaihinh_tb.value) return list
        }
        if (i >= list.length) this.listbox.loaihinh_tb.value = list[0].id
        return list
      },
      ccongnghe() {
        if (!this.listbox.congnghe.list || this.listbox.congnghe.list.length <= 0) return []
        let list = this.listbox.congnghe.list.filter((cn) => cn.kieuld_id == this.listbox.kieu_ld.value).map((cn) => ({
          id: cn.congnghe_id,
          text: cn.congnghe
        }))
        if (!list || list.length <= 0) {
          this.listbox.congnghe.value = 0
          return []
        }
        var i = 0
        for (; i < list.length; i++) if (list[i].id == this.listbox.congnghe.value) return list
        if (i >= list.length) this.listbox.congnghe.value = list[0].id
        return list
      },
      ckieu_ld() {
        let loaihinh = this.listbox.loaihinh_tb.value
        if (!this.listbox.kieu_ld.list || this.listbox.kieu_ld.list.length <= 0) return []
        let list = this.listbox.kieu_ld.list.filter((ld) => ld.loaitb_id == loaihinh && ld.tinhkhac != 2).map((ld) => ({
          id: ld.kieuld_id,
          text: ld.ten_kieuld
        }))
        if (!list || list.length <= 0) {
          this.listbox.kieu_ld.value = 0
          return []
        }
        var i = 0
        for (; i < list.length; i++) if (list[i].id == this.listbox.kieu_ld.value) return list
        if (i >= list.length) this.listbox.kieu_ld.value = list[0].id
        return list
      }
    },
    //end tinh cuoc ngay BSS-88020
    data: function() {
      return {
        header: {
          title: 'THAY ĐỔI THÔNG TIN KHÁCH HÀNG_CQ',
          list: [
            //{ name: 'Trang chủ', link: { name: 'Ui.cards' } },
            { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
            { name: 'THAY ĐỔI THÔNG TIN KHÁCH HÀNG_CQ', link: { name: 'Ui.cards' } }
          ]
        },
        cboLoaiTien: {
          valuewan: 0,
          valuetsl: 0,
          data: []
        },
        cboSoNgay: {
          valuewan: 0,
          valuetsl: 0,
          data: [
            {id : 1, text: '30 ngày'},
            {id : 2, text: 'Số ngày trong tháng'}
          ]
        },
        txtNgaySD: {
          Valuewan: '',
          Valuetsl: '',
          Enable: true
        },
        txtSoTien: 0,
        txtTiGia: 0,
        txtTongTien: 0,
        txtMucThuewan: 0,
        txtMucThuetsl: 0,
        popup: {
          popupToaNha: true,
          Thuebao_ToaNha: true,
          chonMegaWanIOTModal: true,
          frmNhapDSThanhVien: true,
          ThongTinUser: true,
          modalImportCA: true,
          dialogHDTB_SUB_V2: true,
          frmHDTB_SUB_V3: true,
          frmHDTB_SUB_V4: true,
          frmDangKyGoiDaDVLM: true,
          thongTinHenKhachHangModal: true,
          popupInBienBan: true
        },
        isShowThueBaoToaNha: false,
        thamsomacdinh: {},
        khachhang: {
          makh: '',
          sogt: '',
          magd: '',
          mahd: '',
          khachhang_id: 0,
          tenkh: '',
          nguoidd: '',
          dienthoai: '',
          fax: '',
          chucvu: '',
          noicaphk: '',
          noicap: '',
          gioitinh: -1,
          masothue: '',
          ghichu: '',
          dactrung: '',
          PHANLOAIKH_ID: -1,
          hdkh_id: 0,
          plkh: '',
          nhom_plkh: 0,
          txtNguoiKyHoSo: '',
          txtChucDanh: '',
          dtpNgaySinh: null,
          cboGioiTinh: 1,
          txtSoGT: '',
          dtpNgayCap: null,
          txtNoiCap: '',
          txtEmail: '',
          txtSoDT: ''
        },
        filterbox: {
          ctv: {
            value: '',
            ischecked: false,
            ma_ctv: ''
          },
          nguoigt: {
            value: '',
            ischecked: false,
            nguoigt_id: ''
          },
          khuyenmai: {
            list: [],
            value: '',
            ischecked: false
          },
          tratruoc: {
            list: [],
            value: '',
            ischecked: false
          }
        },
        thamso_nguoidung: {
          chuquanmd_id: 0
        },
        dsHDTBGOI: [],
        dsDvKhacChose: [],
        dsDvMaster: [],
        dsDichVuKhac: [],
        dsDK_DVGT: [],
        dsTien_DatCoc: [],
        dsDK_DVKHAC: [],
        dsDK_DVK_TB: [],
        dsDK_DVK_TT: [],
        dsCTM_TBI: [],
        dsDC_HDTB: [],
        dsKM_Neo: [],
        DS_CTKM_LTBI: [],
        dsHDTBDV: [],
        dsCAMKET_HDTB: [],
        dsHDTBDV_Fsecure: [],
        dgrKhuyenMai: [],
        //dgvTienCK: [],
        dtControl: [],
        // dsHDTB_KV_tmp: [],
        dsHDTB_KV_ThemTB: [],
        dsHDTB_DUNGTHU: [],
        dsTRAGOP_TBI_HDTB: [],
        ds_tien_khoanmuc: [],
        ds_tien_khoanmuc_bd: [],
        dsHDTB_INT: [],
        dsTTTT: [],
        dsTien_HDTB: [],
        dsHDTB_CNTT: [],
        dsHDTB_SUB: [],
        dsHDTB_CT: [],
        dsHDTB_LoaiIp: [],
        lvwThueBao: [],
        lvwThueBao_SelectedItem: {},
        lvwThanhToan: [],
        lvDvkhac: { list: [], value: 0 },
        lvDvkhac_TT: { list: [], value: 0 },
        lvDvgt: { list: [], value: null },
        lvMuaThietbi: { list: [], value: null },
        gridEditors: [],
        DT_CONTROLS: {
          dichvu_vt: 0,
          list: [],
          data: [],
          value: [],
          tagvalue: [],
          key: 0
        },
        kmld: {
          tien_kmld: 0,
          vat_kmld: 0,
          tien_datcoc: 0,
          dtienkmld: 0,
          dvatkmld: 0,
          tong_kmld: 0,
          tyle_kmld: 0,
          dtienkmdc: 0,
          dvatkmdc: 0,
          tyle_kmld_tt: 0,
          dtiendatcoc_tt: 0,
          dvatdatcoc_tt: 0,
          tong_kmld_tt: 0,
          tien_kmld_tt: 0,
          vat_kmld_tt: 0,
          dtiendatcoc_km: 0,
          dvatdatcoc_km: 0
        },
        dvgt: {
          tien_dv: 0,
          vat_dv: 0,
          tien_km_dvgt: 0,
          vat_km_dvgt: 0
        },
        dichvu: {
          dtien_dv: 0,
          dvat_dv: 0
        },
        tongtienhd: 0,
        tongtienkm: 0,
        tongvatkm: 0,
        STATUS_VDC: null,
        buttonSet: null,
        chkMienCuocTB: false,
        chkVSVX: false,
        TD_GANTHEM_HDTB_DV: false,
        bDuLieuKS: false, // chi bang true khi lay du lieu tu btnLayDulieuKS_Click
        phieucskhdv_id: 0,
        KetCuoi_ID: -1,
        loainv_ctv_hrm: 0,
        loainv_ngt_hrm: 0,
        ts_kt_ltb_hrm: false,
        huonggiao_tn: 0,
        quytrinh_tn: 0,
        luong_id: 0,
        words: '',
        vkieuhd_id: 0,
        kiemtra_dm_mgwan_tsl: false,
        DICHVUVIENTHONG: {
          TATCA: -1,
          CO_DINH: 1,
          DI_DONG: 2,
          ADSL: 4,
          DD_TRATRUOC: 5,
          EMAIL: 6,
          METRONET: 7,
          MEGAWAN: 8,
          TSL: 9,
          GPHONE: 10,
          IMS: 11,
          MEGA_EYES: 12,
          HOINGHI_TRUYENHINH: 13,
          ANTOAN_BAOMAT_TT: 14,
          DICHVU_CNTT: 15,
          TRUNGTAM_DULIEU: 16,
          HATANG_VIENTHONG: 19,
          IMS_DOITAC: 21,
          BRCD_DOITAC: 22,
          MGW_DOITAC: 23,
          TSL_DOITAC: 24,
          NOIBO_2DIEM: 25,
          NOIBO_DADIEM: 26
        },
        thongtinNgayHuy: [
          {
            textAlign: 'Center',
            columns: [
              {
                template: templateNgayHuy
              }
            ]
          }
        ],
        templateNgayHuy: templateNgayHuy,
        popupComponentTitle: null,
        popupComponent: null,
        popupComponentData: null,
        popupComponentName: '',
        popupComponentAttr: null,
        popupComponentEvents: {
          'form-close': 'popupClosed',
          'acceptChangeCTV': 'popupClosed',
          'acceptChangeNGT': 'popupClosed'
        },
        dsHdTbCntt: [],
        dbDichVu: [],
        dbDichVuThayDoi: [],
        DOITUONG: {
          Y_TE: 18
        },
        TRANGTHAI_DONGBO: {
          DONGBO_SERVICE: 7,
          OPEN_SYNC_VINA: false,
          OPEN_SYNC_VINA_TRASAU: false
        },
        DONGBO_TEST: {
          TEST: false // false: Đồng bộ lên VASC, VISA, Tổng đài; true: Không đồng bộ
        },
        KIEU_DL: {
          CST_TEXT: 0,
          CST_NUMBER: 1,
          CST_DATE: 2
        },
        KIEU_KH: {
          CA_NHAN: 0,
          DOANH_NGHIEP: 1
        },
        PHANLOAI_KH: {
          CA_NHAN: 2,
          DOANH_NGHIEP: 1
        },
        LOAIHOPDONG: {
          THAY_TT_KHAC: 11
        },
        TRANGTHAITB: {
          THUONG: 1,
          KHOA_MOTCHIEU_YEUCAU: 2,
          THANHLY: 7,
          DOISO: 8,
          THANHLY_NO: 9
        },
        TrangThaiHD: {
          MOI: 1
        },
        KHOANMUC_TT: {
          KMTT_CAIDAT: 9
        },
        LOAI_NV: {
          NHANVIEN_KINHDOANH: 52,
          NHANVIEN_AM: 104
        },
        DUNG_THU: {
          DOITUONG_TB: 190
        },
        dsDonViTC: [],
        dsDoiTuongTB: [],
        dsChuQuan: [],
        dsChuQuanLD: [],
        dsHinhThuc: [],
        dsNganHang: [],
        dsDonViQL: [],
        dsGiayToKH: [],
        dsPhanLoaiKH: [],
        dsLoaiKH: [],
        dsDanToc: [],
        dsQuocTich: [],
        dsDichVuVT: [],
        dsKieuYCLD: [],
        dsKieuYCKM: [],
        dsKhuyenMai: [],
        dsMucCuocTB: [],
        dsDK_DVK: [],
        dsDK_BanDau: [],
        dsDVKhac: [],
        dsCTKM: [],
        dsCTKMThayDoi: [],
        dsTkChinh: [],
        dsHDTB: [],
        dsKM_HDTB: [],
        dsNganhNghe: [],
        dtCTKMHD: [],
        dsMaDaiDien: [],
        dsMaTBDD: [],
        dsTTMaDD: [],
        ItemList: [],
        items: [],
        dvkhaclist: [],
        dsHDKH: [],
        dsDiaChiHDKH: [],
        dsDiaChi: [],
        dsDiaChiHDTB: [],
        dsHDKH_Sub: [],
        dsHDKHUYENMAI: [],
        dsHDTT: [],
        dsDiaChiHDTT: [],
        grcDanhSach: [],
        dsHDTB_ADSL: [],
        dsHDTB_CD: [],
        dsHDTB_DD: [],
        dsHDTB_GP: [],
        dsHDTB_TSL: [],
        dsHDTB_MEWAN: [],
        dsHDTB_IMS: [],
        dsHDTB_KV: [],
        dsHDTT_NV: [],
        dsCTTHD: [],
        dsKhoanMucTT: [],
        cboKMTT: [],
        txtTienKM: 0,
        txtVatKM: 0,
        txtTongTienKM: 0,
        cbo_dichvu_id: {
          Value: this.propDichvuvt_id?? null,
          Enable: true
        },
        cbo_kieuld_id: {
          Value: null,
          Enable: true
        },
        cbo_kieukm_id: {
          Value: null,
          Enable: true
        },
        cbo_donviTC_id: {
          Value: null,
          Enable: true
        },
        cbo_donviQL_id: {
          Value: null,
          Enable: true
        },
        cbo_chuquan_id: {
          Value: null,
          Enable: false
        },
        cbo_chuquanLD_id: {
          Value: null,
          Enable: false
        },
        cbo_doituong_id: {
          Value: null,
          Valuetsl: null,
          Valuewan: null,
          Enable: true
        },
        cbo_mucuoc_id: {
          Value: null,
          Enable: true
        },
        cbo_tkchinh_id: {
          Value: null,
          Enable: false
        },
        cbo_khuyenmai_id: {
          Value: null,
          Enable: true
        },
        cbo_giaytoKH: {
          Value: null,
          Enable: true
        },
        cbo_nganhngheloc: {
          Value: null,
          Enable: true
        },
        cbo_loaiKH: {
          Value: null,
          Enable: true
        },
        cbo_dantoc: {
          Value: null,
          Enable: true
        },
        cbo_quoctich: {
          Value: null,
          Enable: true
        },
        cbo_nganhang: {
          Value: null,
          Enable: true
        },
        cbo_maTBDD: {
          Value: null,
          Enable: true
        },
        cbo_HTTT: {
          Value: null,
          Enable: true
        },
        cbo_TTMaDD: {
          Value: null,
          Enable: true
        },
        cbo_maDD: {
          Value: null,
          Enable: true
        },
        cbo_kmtt: {
          Value: [],
          Enable: true
        },
        cboDTVinaPhone: {
          Value: null,
          Enable: true,
          Visible: false
        },
        dsDTVinaPhone: [],
        chkTKChinh: {
          Value: false,
          Enable: true
        },
        chk_ngayHT: {
          Value: null,
          Enable: true,
          Visible: false
        },
        chk_ThangBDKM: {
          Value: null,
          Enable: false
        },
        chk_ThangBDDC: {
          Value: null,
          Enable: false
        },
        dtp_ngayHT: {
          Value: new Date(),
          Enable: false,
          Visible: false
        },
        dtp_ngayYc: {
          Value: new Date(),
          Enable: false
        },
        dtp_ngaylapHd: {
          Value: new Date(),
          Enable: false
        },
        dtp_ThangBD: {
          Value: null,
          Enable: true,
          Ngay: null
        },
        dtp_ThangKT: {
          Value: null,
          Enable: true,
          Ngay: null
        },
        dtp_ThangBDDC: {
          Value: null,
          Enable: true,
          Ngay: null
        },
        dtp_ThangKTDC: {
          Value: null,
          Enable: true,
          Ngay: null
        },
        txt_magiaodich: {
          Value: null,
          Enable: true
        },
        txt_mahopdong: {
          Value: null,
          Enable: true
        },
        txt_tenthuebao: {
          Value: null,
          Enable: true
        },
        txt_ngaysudung: {
          Value: '',
          Enable: true
        },
        txt_ngaysnTB: {
          Value: '',
          Enable: true
        },
        txt_diachiTB: {
          Value: null,
          Enable: true
        },
        txt_diachiLD: {
          Value: null,
          Enable: true
        },
        txt_trangthaiTB: {
          Value: null,
          Enable: true
        },
        txt_maKH: {
          Value: null,
          Enable: true
        },
        txt_tenKH: {
          Value: null,
          Enable: true
        },
        txt_diachiKH: {
          Value: null,
          Enable: true
        },
        txt_diachiNDD: {
          Value: null,
          Enable: true
        },
        txt_telKH: {
          Value: null,
          Enable: true
        },
        txt_emailKH: {
          Value: null,
          Enable: true
        },
        txt_faxKH: {
          Value: null,
          Enable: true
        },
        txt_mstKH: {
          Value: null,
          Enable: true
        },
        txt_nguoiDD: {
          Value: null,
          Enable: true
        },
        txt_chucdanhDD: {
          Value: null,
          Enable: true
        },
        txt_maTB: {
          Value: this.propMaTB ?? null,
          Enable: true
        },
        txt_tenTT: {
          Value: null,
          Enable: true
        },
        txt_ngayhethan: {
          Value: '',
          Enable: true,
          Readonly: false
        },
        txt_ngaysn: {
          Value: '',
          Enable: true
        },
        txt_soGT: {
          Value: null,
          Enable: true
        },
        txt_ngaycap: {
          Value: '',
          Enable: true,
          Readonly: false
        },
        txtNgaySN_NDD: {
          Value: null,
          Enable: true
        },
        txt_noicap: {
          Value: null,
          Enable: true
        },
        txt_phanloaiKH: {
          Value: null,
          Enable: true
        },
        txt_gioitinh: {
          Value: '0',
          Enable: true
        },
        txt_soDT_TT: {
          Value: null,
          Enable: true
        },
        txt_emailTT: {
          Value: null,
          Enable: true
        },
        txt_maTT: {
          Value: null,
          Enable: true
        },
        txt_diachiCT: {
          Value: null,
          Enable: true
        },
        txt_diachiTT: {
          Value: null,
          Enable: true
        },
        txt_diachiBC: {
          Value: null,
          Enable: true
        },
        txt_mst_TT: {
          Value: null,
          Enable: true
        },
        txt_stk_TT: {
          Value: null,
          Enable: true
        },
        txt_chu_TK: {
          Value: null,
          Enable: true
        },
        txt_maIG: {
          Value: null,
          Enable: true
        },
        txt_maNVTC: {
          Value: null,
          Enable: true
        },
        txt_maTuyen: {
          Value: null,
          Enable: true
        },
        txt_ghiChuTT: {
          Value: null,
          Enable: true
        },
        txt_nhanVienQL: {
          Value: null,
          Enable: true
        },
        txt_ghiChuTB: {
          Value: null,
          Enable: true
        },
        txt_khuvuc: {
          Value: null,
          Enable: true
        },
        txt_TyleSD: {
          Value: null,
          Enable: true
        },
        txt_TyleTB: {
          Value: null,
          Enable: true
        },
        txt_TienTB: {
          Value: null,
          Enable: true,
          Visible: true
        },
        txt_CuocTB: {
          Value: null,
          Enable: true,
          Visible: true
        },
        txt_TienSD: {
          Value: null,
          Enable: true,
          Visible: true
        },
        txt_CuocSD: {
          Value: null,
          Enable: true,
          Visible: true
        },
        btnMaNVKD: {
          Enable: false
        },
        THAYDOI_TT_KHAC_BO_KV: false,
        lbl_trangthaiDD: null,
        // khai báo các biến tiểu ngành
        TNC1_ID: 0,
        TNC2_ID: 0,
        TNC3_ID: 0,
        TNC4_ID: 0,
        nganhnghe_id: 0,
        sodt_kh: '',
        password: '',
        gioitinh_Mytv: '',
        email_kh: '',
        fax_kh: '',
        diachikh_id: null,
        tinhkh_id: null,
        quankh_id: null,
        phuongkh_id: null,
        dacdiemkh_id: null,
        phokh_id: null,
        apkh_id: null,
        khukh_id: null,
        sonhakh: null,
        dulieuservice: {},
        diachitt_id: null,
        tinhtt_id: null,
        quantt_id: null,
        phuongtt_id: null,
        dacdiemtt_id: null,
        phott_id: null,
        aptt_id: null,
        khutt_id: null,
        sonhatt: null,
        diachibc_id: null,
        tinhbc_id: null,
        quanbc_id: null,
        phuongbc_id: null,
        dacdiembc_id: null,
        phobc_id: null,
        apbc_id: null,
        khubc_id: null,
        sonhabc: null,
        tinhndd_id: null,
        quanndd_id: null,
        phuongndd_id: null,
        phondd_id: null,
        dacdiemndd_id: null,
        ngondd_id: null,
        sonhandd: '',
        tinhct_id: null,
        quanct_id: null,
        phuongct_id: null,
        phoct_id: null,
        dacdiemct_id: null,
        ngoct_id: null,
        khuct_id: null,
        sonhact: null,
        khuvuc_id: 0, //Khu vực địa bàn
        diachitb_id: null,
        tinhtb_id: null,
        quantb_id: null,
        phuongtb_id: null,
        dacdiemtb_id: null,
        photb_id: null,
        aptb_id: null,
        khutb_id: null,
        sonhatb: null,
        block: null,
        tang: null,
        phong: null,
        mota: null,
        diachild_id: null,
        tinhld_id: null,
        quanld_id: null,
        phuongld_id: null,
        dacdiemld_id: null,
        phold_id: null,
        apld_id: null,
        khuld_id: null,
        sonhald: null,
        block_ld: null,
        tang_ld: null,
        sophong_ld: null,
        motathem_ld: null,
        hdmig_id: 0,
        mig_id: 0,
        hdkh_cha_id: 0,
        hdkh_id: 0,
        hdtt_id: 0,
        hdtb_id: 0,
        httt_id_cu: -1,
        khachhang_id: 0,
        thanhtoan_id: 0,
        loaikh_id: 0,
        thuebao_id: 0,
        thuebao_cha_id: 0,
        loaitb_id: 0,
        ma_tb: '',
        matb_dd: '',
        dichvuvt_id: 0,
        doituong_id: 0,
        doituong_id_cu: 0,
        tocdo_id: 0,
        muccuoc_id: 0,
        muccuoc_tb_cu: 0,
        trangbi_id: 0,
        linhvuc_id: 0,
        chuyenmang: 0,
        ht: 0,
        quytrinh_id: 0,
        iloai_km: 0,
        donviql_httt: 0,
        trangthaitb_id: 0,
        trangthai_tb: null,
        ts_sua_ngay_ht_dv_cntt: 0,
        check_chuahoa_cntt: false,
        phantap_kh: false,
        thamso_chuanhoa_diachi: false,
        kt_ngaybd_themmoi_km: false,
        khongnhap_nh_unt: 0,
        tuyenthu_httt: 0,
        thaydoi_td_cntt: 0,
        suakm_tinhcuoc: 0,
        tichhop_didong_dhsx: false,
        ts_kt_mstmns_kh: 0,
        ts_kt_tthdkhdn: 0, //Kiểm tra cho phép thêm thông tin khách hàng DN  đối với PTTB KHDN mới xài
        kt_no_thaydoi_thongtin_tt: 0,
        is_tinhcuoc_ngay: false,//BSS-88020
        ts_sinhma_gd_theo_donvi: false,
        thaydoi_tt_khac_bo_kv: false,
        co_sua_km_tc: false,
        dv_qhns: null,
        machuong: null,
        mn_kt: null,
        ma_ndkt: null,
        mn_nsnn: null,
        tien_dm: null,
        nhanvientc_id: 0,
        tuyenthu_id: 0,
        nhanvien_ql_am_id: 0,
        vma_tb: null,
        vten_kh: null,
        vngay_sinh: null,
        vcmt: null,
        vpassport: null,
        vloai_gt: null,
        vcustomer_type: null,
        vdiachi_kh: null,
        vcongty: null,
        vnghenghiep: null,
        vgioitinh: null,
        vquoctich: null,
        vemail: null,
        vnoicap: null,
        vloai_kh: null,
        vghichu: null,
        vngay_cap: null,
        vnam_so_sim: null,
        vmsisdn: null,
        vprovince: null,
        vsub_type: null,
        vdiachi_giaodich: null,
        vso_daily: null,
        vcontract_type: null,
        donvitt_id: 0,
        kt_load: true,
        httt_id: null,
        diachiLD: {},
        nganHang: {},
        ctkm: {},
        ctkmtd: {},
        rowIndex_ctkmtd: -1,
        vhuonggiao_id2: 0,
        kiemtra_lienhe_thanhtoan: -1,
        PHANLOAIKH_ID: -1,
        nhomplkh: 0,
        loi_sinh_magd: false,
        TTHD_ID_TAG: 1,
        isEnableBtnDiaChiNDD: true,
        isEnableBtnNSNN: true,
        isEnableBtnDiaChiKH: true,
        isEnableBtnNganhNghe: true,
        isEnableBtnDiaChiTT: true,
        isEnableBtnDiaChiCT: true,
        isEnableBtnDiaChiBC: true,
        isEnableBtnMaNVTC: true,
        isEnableBtnDiaChiTB: true,
        isEnableBtnEditDVK: true,
        isEnableBtnDiaChiLD: true,
        isVisibleBtnDiaChiLD: false,
        isEnableBtnChiTietKM: true,
        isEnableBtnChonKhuyenMai: true,
        isEnableBtnTaoMIG: true,
        isEnableBtnPhai: true,
        isEnableBtnTrai: true,
        pt_doanhthu: 0,
        pt_diem_tn: 0,
        waterMark: '  /  /',
        dateVal: new Date(),
        dateFormat: 'dd/MM/yyyy',
        monthFormat: 'MM/yyyy',
        maxDate: new Date('3000'),
        tsbtnCapNhat: {
          Enable: true,
          Visible: true
        },
        tsbtnNhapMoi: {
          Enable: true,
          Visible: true
        },
        tsbtnGhiLai: {
          Enable: true,
          Visible: true
        },
        tsbtnXoa: {
          Enable: true,
          Visible: true
        },
        tsbtnHuyBo: {
          Enable: true,
          Visible: true
        },
        tsbtnCapNhatDB: {
          Enable: true,
          Visible: true
        },
        tsbtnThanhToan: {
          Enable: false,
          Visible: false
        },
        tsbtnGiaoPhieu: {
          Enable: true
        },
        tsbtnInPhieuYC: {
          Enable: true
        },
        tsbtnThongTinLH: {
          Enable: true,
          Visible: true
        },
        tsbtnEmail: {
          Enable: true,
          Visible: true
        },
        tsbtnUser: {
          Enable: true,
          Visible: true
        },
        tsbtnToaNha: {
          Enable: true,
          Visible: false
        },
        tsbtnUpImg: {
          Enable: true,
          Visible: true
        },
        HT_HTHS_KMTT: false,
        // input form DiaChi
        diachi: {},
        diachi_flag: null,
        showThongTinThem: false,
        quyen: [],
        labelFunctionTitle: null,
        // input form LoaiKH
        objLoaiKH: {},
        // input form ChiTietNganhNghe
        objChiTietNganhNghe: {},
        // input form TTKHBoSung
        objTTKHBoSung: {},
        // input form popupGanKhuVucQuanLyThueBao
        objGanKhuVuc: {},
        // input form TraCuuNVTC
        objTraCuuNVTC: {},
        // Loai popup tra cuu NVTC
        loaiPopupTraCuuNVTC: 0,
        isEnterTimKiem: false,
        popupInHoSo_Param: {
          hdkh_id: 0,
          ma_gd: ''
        },
        isStrictMode: true,
        activeQuyTrinhXacMinhCapNhatTTLH: false,
        thuebao: {
          codinh: {
            somay: '',
            sochinh: '',
            dgvTienCK: {
              list: [],
              headers: [
                { fieldName: 'tuthang', headerText: 'Từ tháng', allowFiltering: true, allowEditing: true },
                { fieldName: 'denthang', headerText: 'Đến tháng', allowFiltering: true, allowEditing: true },
                {
                  fieldName: 'tien_ck',
                  headerText: 'Tiền cam kết',
                  allowFiltering: true,
                  allowHtml: true,
                  allowEditing: true
                }
              ]
            },
            arraySoMay: []
          },
          mgwan: {
            ma_tb:'',
            diachi_tb: '',
            diachi_ld: '',
            dichvu_id: '',
            loaihinh_tb: '',
            kieuld_id: '',
            congnghe: '',
            somay: '',
            ten_tb: '',
            soao: '',
            matn: '',
            madoicap: '',
            huongkn: '',
            vhuongkn: '',
            vrfvfi: '',
            lanip: '',
            wanip: '',
            siteid: '',
            mahdttkd: '',
            luonggiukenh: false,
            chkLuongGiuKenh: false,
            cuoctk: 0,
            cuoctk_dt: 0,
            cuoctc: 0,
            cuoctc_dt: 0,
            hopdong_dt: '',
            cuoctbi: 0,
            cuocht: 0,
            soluongip: 0,
            cuocip: 0,
            cuocisp: 0,
            cuocnix: 0
          },
          adsl: {
            tientra: 0,
            btnKiemTraAccount_visible: true,
            txtAccountADSL: '',
            txtSoAoAdsl: '',
            txtMaTN_ADSL: '',
            txtMaDoiCap_ADSL: '',
            validadslacc: false,
            txtEmailAdsl: '',
            chkTocDoThuc: false,
            ckTuCaiADSL: false,
            dtpHanDatCho: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            dtpHanDatCho_Visible: true,
            txtDienThoai_Fsecure: '',
            txtSL_Tivi: 0,
            txtCuocDT_Internet: 0,
            txtSerialSTB: '',
            dtpNgayTra: null,
            txtTTCuoc: '',
            txtCSD_FIBER_TK: 0,
            txtCTB_FIBER_TK: 0,
            pass_adsl: '',
            txtAccountADSL_ReadOnly: false,
            kh_cd_fsecure: false,
            isHopDong: 1,
            //Bổ sung sài cho mega IOT
            txtMaTB_KN: '',
            megaIOT_vrf: '',
            megaIOT_LanIP: '',
            megaIOT_WanIP: '',
            megaIOT_HuongKN: ''
          },
          tsl: {
            ma_tb:'',
            diachi_tb: '',
            diachi_ld: '',
            dichvu_id: '',
            loaihinh_tb: '',
            kieuld_id: '',
            congnghe: '',
            somay: '',
            soao: '',
            txtMaTSL: {
              ReadOnly: false,
              Text: ''
            },
            txtMaHD_TTKD_TSL: '',
            txtSoSoiQuang: 0,
            txtSoSoiQuang_Enabled: true,
            txtCuocTKDT_TSL: 0,
            txtCuocTCDT_TSL: 0,
            txtMaHD_DT_TSL: '',
            txtCuocHT_TSL: 0,
            txtCuocTBI_TSL: 0,
            txtCuocTC_TSL: 0,
            txtCuocTK_TSL: 0,
            diachidau_id: 0,
            diachicuoi_id: 0,
            txtSoAoTSL_Dau: '',
            txtSoAoTSL_Cuoi: '',
            txtVrf_Dau: '',
            txtVrf_Cuoi: '',
            txtSiteIDTSL_Dau: '',
            txtSiteIDTSL_Cuoi: '',
            donvi_dau_id: 0,
            donvi_cuoi_id: 0
          },
          gphone: {
            txtSoSimGphone: '',
            txtSoMayGP: '',
            checkVSVX: false,
            dgvTienCKGP: {
              list: [],
              headers: [
                { fieldName: 'tuthang', headerText: 'Từ tháng', allowFiltering: true, allowEditing: true },
                { fieldName: 'denthang', headerText: 'Đến tháng', allowFiltering: true, allowEditing: true },
                {
                  fieldName: 'tien_ck',
                  headerText: 'Tiền cam kết',
                  allowFiltering: true,
                  allowHtml: true,
                  allowEditing: true
                }
              ]
            }
          },
          ims: {
            txtMaTBIMS: '',
            cbkTrunk: false,
            cboTrunk: { list: [], value: 0 },
            txtMaDoiCap_IMS: 0,
            chkDoiTac_IMS: false,
            txtCuocDT_IMS: 0,
            txtMaTN_IMS: '',
            txtCuocGoiDT: 0,
            txtIp_IMS: '',
            pass_ims: '',
            dgvTienCKIMS: {
              list: [],
              headers: [
                { fieldName: 'tuthang', headerText: 'Từ tháng', allowFiltering: true, allowEditing: true },
                { fieldName: 'denthang', headerText: 'Đến tháng', allowFiltering: true, allowEditing: true },
                {
                  fieldName: 'tien_ck',
                  headerText: 'Tiền cam kết',
                  allowFiltering: true,
                  allowHtml: true,
                  allowEditing: true
                }
              ]
            }
          },
          didong: {
            cboGoiCuocDD: { list: [], value: 0, Enabled: true },
            cboDTVinaPhone: { list: [], value: 0 },
            txtSoMayDD: '',
            txtSimDD: '',
            txtTienDuTK: 0,
            txtSoMayNTK: '',
            dgrKhuyenMai: { list: [], value: {}, headers: [] },
            LOAI_TB_DD: '',
            txtEmailDD: '', //Thay co khai bao tren form nhung khong thay co code hien thi.
            txtTenTBDD_Cu: '',
            txtNgaySinhDD_Cu: '',
            txtSoGTDD_Cu: '',
            txtNgayCapGTDD_Cu: '',
            txtDiaChiDD_Cu: '',
            txtHoKhauDD_Cu: ''
          },
          thuebao_id: 0,
          tentb: '',
          ghichu: '',
          tbhd_pl: 0,
          thuebao_id_cha: 0,
          loaisochinh: 0,
          hdtb_id_cha: 0,
          thangck: 0,
          doituong_ct_id: 0,
          tgdungthu: 0,
          tinhkhac: 0,
          khuvuc: '',
          khuvuc_id: 0,
          check_tb_adsl: true,
          statusVdc: true,
          cvlct: false,
          congvan: '',
          NgayCVChecked: false,
          NgayCV: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
          crm: '',
          erp: '',
          vtn: '',
          capthuengoai: ''
        },
        LOAINODE_MGWAN: {
          NODE_CHINH: 1,
          NODE_PHU: 2,
          NODE_DUPHONG_CONG_TOCDO: 3,
          NODE_DUPHONG_TOCDO: 4
        },
        buttons: {
          tsbtnXoa: false,
          tsbtnGhiLai: false,
          tsbtnHuyBo: false,
          tsbtnThanhToan: false,
          inPhụLụcToolStripMenuItem: false,
          tsbtnXoaTB: false,
          tsbtnThemTB: false,
          tsbtnThemTT: false,
          tsbtnXoaTT: false,
          btnChonSM: true,
          chkLapDatBS: false,
          btnThongTinKhac: false,
          btnChonSoIMS: true,
          btnHuongKNWan: false,
          tsbtnNhapMoi: false
        },
        listbox: {
          trangbi: {
            list: [],
            valuewan: TRANGBI.VIENTHONG_TRANGBI,
            valuetsl: 0,
            valuecd: 3,
            valueims: 0,
            valueadsl: 0,
            valuedd: 0,
            valuegp: 0
          },
          loai_tb: { list: [], listmgwan: [], value: 0, valuedau: 0, valuecuoi: 0, valuemgwan: 0 },
          chu_quan: {
            list: [],
            value: 0,
            valuewan: 0,
            valueldmgwan: 0,
            valuetsl: 0,
            valuecd: 143,
            valueldcd: 9,
            valueims: 0,
            valueldims: 0,
            valueadsl: 0,
            valueldadsl: 0,
            valuedd: 0,
            valuegp: 0,
            enabledwan: false,
            enabledtsl: false,
          },
          tien_km: { list: [], value: 0 },
          doituong: { list: [], value: '', valuetsl: '' },
          dichvu_vt: { list: [], value: DICHVUVIENTHONG.CO_DINH },
          loaihinh_tb: {
            list: [],
            value: 0,
            valuetsl: 0
          },
          loai_khl: { list: [], value: 0 },
          loai_gt: { list: [], value: 0 },
          phanloai_kh: { list: [], value: 0 },
          loai_kh: { list: [], value: -1 },
          nganhang: {
            list: [],
            value: 0,
            valuekh: 0,
            sotaikhoankh: '',
            sotaikhoan: '',
            ischecked: false,
            ischeckedkh: false,
            isnganhangnn: false
          },
          hinhthuc_tt: { list: [], value: 0 },
          hinhthuc_tc: { list: [], value: 0, valueims: 0 },
          dantoc: { list: [], value: 0 },
          nganhnghe: { list: [], value: 0 },
          nganhngheloc: { list: [], value: 0 },
          quoctich: { list: [], value: 0 },
          hinhthuc_tthi: { list: [], value: 0 },
          kieu_hd: { list: [], value: 0 },
          goi_kt: { list: [], value: 0 },
          donvi_ql: { list: [], value: 0, valueql: 0 },
          kieu_ld: {
            list: [],
            value: KieuLapDat.DM_CODINH,
            valuetsl: 0,
          },
          nhacc_cu: { list: [], value: 0, ischecked: false },
          congnghe: { list: [], value: 0, valuetsl:0 },
          hinhthuc_cm: { list: [], value: 0 },
          thoihan: { list: [], value: 0, valuetsl: 0, valuewan: 0, valuecd: 0, valueadsl: 0, valueims: 0 },
          loai_csht: { list: [], value: 0 },
          doitac: {
            list: [],
            valueims: 0,
            valuecsht: 0,
            valueinternet: 0,
            valuecd: 0,
            ischeckedcd: false,
            ischeckedadsl: false
          },
          cboLoaiIP: { list: [], value: 0, values: [], valueitems: [], orilist: [] },
          phanloai_dv: { list: [], value: 0 },
          ht_chitietdv_ims: { list: [], value: 0 },
          ht_phantach_tocdo: { list: [], value: 0 },
          trangthai_yc: { list: [], value: 0 },
          donvi_dau: { list: [], value: 0 },
          donvi_cuoi: { list: [], value: 0 },
          tinh_kn: { list: [], value: 0 },
          loaikenh: { list: [], value: 0, valuetsl: 0 },
          tocdokenh: {
            list: [],
            valuecir: 0,
            valuepir: 0,
            valuetsl: 0,
            valueisp: 0,
            valuenix: 0,
            ischeckedpir: false,
            ischeckedisp: false,
            ischeckednix: false
          },
          doitactsl: { list: [], value: 0, chkDoiTacTSL: false },
          doitacwan: { list: [], value: 0, isenabled: false },
          loaimodem: { list: [], value: 0 },
          loaicap: { list: [], value: 0, valuedau: 0, valuecuoi: 0 },
          muccuoc: { list: [], value: 0, isenabled: true },
          muccuoc_tb: { list: [], value: 0 },
          thanhtoan: { list: [], value: '' },
          thanhtoancbo: { list: [], value: '' },
          danhba: { list: [], value: 0 },
          dshopdong_chuaht: { list: [], header: [], value: 0 },
          madaidien: { list: [], value: null },
          cboLoaiTB: { list: [], value: null },
          cboGoiCuocAdsl: {
            list: [],
            value: 0
          },
          cboTocDoThucADSL: { list: [], value: 0 },
          cboHDChinh: { list: [], value: 0 },
          cboDbChinh: { list: [], value: 0 },
          cboDoiTac_CSHT: { list: [], value: 0 },
          cboLoai_CSHT: { list: [], value: 0 },
          cboKieuTra: { list: [], value: 0 },
          cboThangTra: { list: [], value: 0 }
        },
        LoaiHinhTB: {
          DIENTHOAI_CD: 1,
          TRUNGKE_THUONG: 3,
          TRUNGKE_TUONGTU: 4,
          TRUNGKE_2M: 5,
          PABX: 8,
          MAIL_SMD: 9,
          INTERNET: 10,
          INTERNET_1260: 10,
          MEGA: 11,
          INTERNET_ADSL: 11,
          Web_Hosting: 12,
          EMAIL: 13,
          ISDN2B_CD: 14,
          ISDN30B_CD: 15,
          ISDN2B_CQ: 16,
          ISDN30B_CQ: 17,
          HTVC_FIBERVNN_ANALOG: 18,
          HTVC_FIBERVNN_DIGITAL: 19,
          DIDONGTRASAU: 20,
          DIDONGTRATRUOC: 21,
          LEASEDLINE: 22,
          MEGAWAN_ADSL: 23,
          FSECURE: 24,
          MEGAWAN_SHDSL: 25,
          MEGAWAN_QUANG_FE: 26,
          METRONET_FE: 27,
          METRONET_GE: 28,
          MEGAWAN_QUANG_GE: 30,
          MEGACAMERA: 31,
          GPHONE: 32,
          GPHONE_TRATRUOC: 34,
          IOFFICE: 35,
          TD_1800: 38,
          VNPT_COLOCATION: 39,
          INTERNET_ADSL_TINH_KHAC: 57,
          INTERNET_FTTH: 58,
          INTERNET_FIBER_TINH_KHAC: 59,
          INTERNET_MYTV: 61,
          INTERNET_MYTV_B2B: 171,
          IMS_POTS: 62,
          IMS_SIP: 63,
          IMS_RGW: 64,
          INTERNET_SGTV: 65,
          CAMERA_HOCDUONG: 70,
          MEGAWAN_ADSL_2M: 74,
          METRONET_2FE: 75,
          MEGA_CONFERENCE: 76,
          IMS_SIPTRUNKING: 77,
          SSL: 78,
          WIFI_FIBER: 79,
          VNPT_MEETING: 89,
          HNTH_NGN: 90,
          KTR_Leasedline_GE: 96,
          KTR_Leasedline_E1: 97,
          KTR_Leasedline_STM1: 98,
          KTR_Leasedline_nx64k: 99,
          KTR_TRUCTHONG_DONG: 100,
          KTR_TRUCTHONG_QUANG: 101,
          KTR_DOICAP_QUANG: 102,
          KTR_DOICAP_DONG: 103,
          KTR_Leasedline_FE: 104,
          KTR_Nail_up: 105,
          CDN: 112,
          CA: 116,
          CA_IVAN: 117,
          VN_EDU: 118,
          VNPT_PORTAL: 120,
          TTDL_HDDT: 122,
          TTDL_MAIL_OFFICE: 124,
          TD_1900: 127,
          BRANDNAME: 131,
          IVAN: 132,
          DEDICATED_SERVER: 139,
          TDTH_THUYENHINH: 146,
          VCC: 149,
          MGW_INTERNET_TRUCTIEP: 134,
          COSO_HATANG: 158,
          CAPQUANG_TRANG_TSL: 123,
          MEGAEYES_CLOUD_CAMERA: 83,
          WIFI_MESH: 210,
          WIFI_MESH_TM: 274,
          INDOOR_CAM: 222,
          OUTDOOR_CAM: 224,
          TIVI_SAMSUNG: 266,
          IMS_DT: 228,
          BRCD_FBDT: 229,
          BRCD_THDT: 230,
          MGW_DT: 231,
          TSL_DT: 232,
          BAOHIEM: 289,
          BAOHIEM_VTC: 300,
          ECABINET: 200,
          ELEARNING: 208,
          ORM: 160,
          VXP: 235,
          VNPT_HIS: 136,
          VNPT_HMIS: 156,
          BIENLAIDT: 175,
          VNPT_MCAP: 267,
          VNPT_HCSN: 202,
          VNPT_SMART_TOURIST: 223,
          VNPT_SMCC: 268,
          VEDU_LIB: 205,
          VNPT_SLL: 215,
          VNPT_LIS: 261,
          VNPT_CCVC: 176,
          VNEDU_TKB: 211,
          VNEDU_SMARTID: 180,
          IT_CARE: 270,
          SMART_CA: 288,
          VNPT_One_Business: 292,
          VNPT_SMART_CLOUD: 153,
          VNPT_MSS: 297,
          Voice_Brandname: 358,
          HDDT_MTT: 2116,
          MEGA_IOT: 305,
          MEGAWAN_IOT: 306,
          BaoHieuQuocTe: 2114,
          HoaMangChuyenTinh: 2119,
          THUPHAT_HINH_QT: 311,
          THUE_BANGTAN_VETINH: 312,
          NB_KTR: 380,
          NB_TKTD: 381,
          NB_TKIP: 382,
          NB_MGWAN: 383,
          NB_METRONET: 384,
          NB_INTERNETTT: 385
        },
        ThoiHan: {
          Ngan_Han: 1,
          Dai_Han: 2
        },
        TUDONG_SINH_SOAO: false, //false,
        loaisowan: 0,
        diachiKN: {
          DIACHI: null,
          TINH_ID: 0,
          QUAN_ID: 0,
          PHUONG_ID: 0,
          PHO_ID: 0,
          AP_ID: 0,
          KHU_ID: 0,
          DACDIEM_ID: 0,
          SO_NHA: null,
          Lng: 0,
          Lat: 0,
          BLOCK: null,
          TANG: null,
          PHONG: null,
          MOTA: null
        },
        diachiDau: {
          DIACHI: null,
          TINH_ID: 0,
          QUAN_ID: 0,
          PHUONG_ID: 0,
          PHO_ID: 0,
          AP_ID: 0,
          KHU_ID: 0,
          DACDIEM_ID: 0,
          SO_NHA: null,
          Lng: 0,
          Lat: 0,
          BLOCK: null,
          TANG: null,
          PHONG: null,
          MOTA: null
        },
        diachiCuoi: {
          DIACHI: null,
          TINH_ID: 0,
          QUAN_ID: 0,
          PHUONG_ID: 0,
          PHO_ID: 0,
          AP_ID: 0,
          KHU_ID: 0,
          DACDIEM_ID: 0,
          SO_NHA: null,
          Lng: 0,
          Lat: 0,
          BLOCK: null,
          TANG: null,
          PHONG: null,
          MOTA: null
        },
        diachiTB: {
          DIACHI: null,
          TINH_ID: 0,
          QUAN_ID: 0,
          PHUONG_ID: 0,
          PHO_ID: 0,
          AP_ID: 0,
          KHU_ID: 0,
          DACDIEM_ID: 0,
          SO_NHA: null,
          Lng: 0,
          Lat: 0,
          BLOCK: null,
          TANG: null,
          PHONG: null,
          MOTA: null
        },
        js_diachi_tt: {
          DIACHI: null,
          DIACHI_ID: null,
          TINH_ID: 0,
          QUAN_ID: 0,
          PHUONG_ID: 0,
          PHO_ID: 0,
          AP_ID: 0,
          KHU_ID: 0,
          DACDIEM_ID: 0,
          SONHA: null,
          KINHDO: 0,
          VIDO: 0,
          BLOCK: null,
          TANG: null,
          PHONG: null,
          MOTA: null
        },
        js_diachi_kh: {
          DIACHI: null,
          DIACHI_ID: null,
          TINH_ID: 0,
          QUAN_ID: 0,
          PHUONG_ID: 0,
          PHO_ID: 0,
          AP_ID: 0,
          KHU_ID: 0,
          DACDIEM_ID: 0,
          SONHA: null,
          KINHDO: 0,
          VIDO: 0,
          BLOCK: null,
          TANG: null,
          PHONG: null,
          MOTA: null
        },
        js_diachi_tb: {
          DIACHI: null,
          DIACHI_ID: null,
          TINH_ID: 0,
          QUAN_ID: 0,
          PHUONG_ID: 0,
          PHO_ID: 0,
          AP_ID: 0,
          KHU_ID: 0,
          DACDIEM_ID: 0,
          SONHA: null,
          KINHDO: 0,
          VIDO: 0,
          BLOCK: null,
          TANG: null,
          PHONG: null,
          MOTA: null
        },
        js_diachi_ld: {
          DIACHI: null,
          DIACHI_ID: null,
          TINH_ID: 0,
          QUAN_ID: 0,
          PHUONG_ID: 0,
          PHO_ID: 0,
          AP_ID: 0,
          KHU_ID: 0,
          DACDIEM_ID: 0,
          SONHA: null,
          KINHDO: 0,
          VIDO: 0,
          BLOCK: null,
          TANG: null,
          PHONG: null,
          MOTA: null
        },
      }
    },
    mounted: async function() {
      try {
        this.loading(true)
        let now = new Date()
        this.txt_maTB.Value = this.propMaTB
        this.dichvuvt_id = this.propDichvuvt_id
        this.dtp_ThangBD.Value = now
        this.dtp_ThangKT.Value = now
        this.dtp_ThangBDDC.Value = now
        this.dtp_ThangKTDC.Value = now
        await this.loadForm()
        if (Object.keys(this.$route.query).length > 0) {
          let query = this.$route.query
          if (!query.hdkh_cha_id) {
            this.cbo_kieuld_id.Value = query.kieuld_id
            this.cbo_dichvu_id.Value = query.dichvuvt_id
            await this.LookupByAccount(this.txt_maTB.Value, this.dichvuvt_id)
          } else {
            await this.LoadTuHopDongTuVan(query.hdkh_cha_id)
          }
        }
        this.tsbtnCapNhat.Enable = false
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    watch: {
      'cbo_dichvu_id.Value': async function(newval) {
        if (newval) {
          this.loading(true)
          this.tsbtnCapNhat.Enable = true;
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_kieu_ld_dvvt', {
            dichvu_id: newval,
            loaihd_id: this.LOAIHOPDONG.THAY_TT_KHAC
          })
          this.dichvuvt_id = newval
          this.thuebao.mgwan.dichvu_id = newval
          this.dsKieuYCLD = rs.data.filter(v => v.kieuld_id == KIEULAPDAT.THAYDOI_TT_THANHTOAN || v.kieuld_id == KIEULAPDAT.THAYDOI_TT_KHACHHANG || v.kieuld_id == KIEULAPDAT.THAYDOI_TT_THUEBAO)
          if (this.dsKieuYCLD.length > 0) {
            this.cbo_kieuld_id.Value = this.dsKieuYCLD[0].kieuld_id.toString()
          }

          let rss = await this.axios.get('/web-quantri/danhmuc-chung/CSS_LOAITIEN');
          this.cboLoaiTien.data = rss.data.data;
          this.cboLoaiTien.value = rss.data.data.length ? rss.data.data[0].LOAITIEN_ID : null

          this.dsCTM_TBI = []
          this.lvMuaThietbi.list = []
          this.lvDvkhac.list = []
          this.dsDK_DVK_TB = []
          this.trangbi_id = 0
          try {
            if ([DICHVUVIENTHONG.MEGAWAN, DICHVUVIENTHONG.TSL, DICHVUVIENTHONG.METRONET, DICHVUVIENTHONG.MGW_DOITAC, DICHVUVIENTHONG.TSL_DOITAC, DICHVUVIENTHONG.NOIBO_DADIEM, DICHVUVIENTHONG.NOIBO_2DIEM].indexOf(parseInt(newval)) >= 0) {
              if (!this.kiemtra_dm_mgwan_tsl) {
                let daivt_id = this.listbox.donvi_ql.value
                await API.post_lay_danhmuc_lapmoi_mgw_tsl(this.axios, { vchuoi: 'LOAI_KENH|TOCDO_KENH|LOAI_CAP|THOIHAN|DOITAC|TINH_KN|LOAI_MD|TRAM_VT_DAU-donvi_id = ' + daivt_id + '|TRAM_VT_CUOI-donvi_id = ' + daivt_id }).then((response) => {
                  if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                    this.listbox.donvi_dau.list = response.data.data['tram_vt_dau']
                    if (this.listbox.donvi_dau.list && this.listbox.donvi_dau.list.length > 0) this.listbox.donvi_dau.value = this.listbox.donvi_dau.list[0].donvi_id
                    this.listbox.donvi_cuoi.list = response.data.data['tram_vt_cuoi']
                    if (this.listbox.donvi_cuoi.list && this.listbox.donvi_cuoi.list.length > 0) this.listbox.donvi_cuoi.value = this.listbox.donvi_cuoi.list[0].donvi_id
                    this.listbox.tinh_kn.list = response.data.data['tinh_kn']
                    if (this.listbox.tinh_kn.list && this.listbox.tinh_kn.list.filter((x) => x.tinh_id > 0).length > 0) this.listbox.tinh_kn.value = this.listbox.tinh_kn.list.filter((x) => x.tinh_id > 0)[0].tinh_id
                    try {
                      this.listbox.tinh_kn.value = this.$root.token.getPhanVungID()
                    } catch (ex) {
                    }
                    this.listbox.loaikenh.list = response.data.data['loai_kenh']
                    if (this.listbox.loaikenh.list && this.listbox.loaikenh.list.length > 0) {
                      this.listbox.loaikenh.value = this.listbox.loaikenh.list[0].loaikenh_id
                      this.listbox.loaikenh.valuetsl = this.listbox.loaikenh.list[0].loaikenh_id
                    }
                    this.listbox.tocdokenh.list = response.data.data['tocdo_kenh']
                    if (this.listbox.tocdokenh.list && this.listbox.tocdokenh.list.length > 0) {
                      this.listbox.tocdokenh.value = this.listbox.tocdokenh.list[0].tocdo_id
                      this.listbox.tocdokenh.valuetsl = this.listbox.tocdokenh.list[0].tocdo_id
                    }
                    this.listbox.doitactsl.list = response.data.data['doitac']
                    if (this.listbox.doitactsl.list && this.listbox.doitactsl.list.length > 0) this.listbox.doitactsl.value = this.listbox.doitactsl.list[0].doitac_id
                    this.listbox.doitacwan.list = response.data.data['doitac']
                    if (this.listbox.doitacwan.list && this.listbox.doitacwan.list.length > 0) this.listbox.doitacwan.value = this.listbox.doitacwan.list[0].doitac_id
                    this.listbox.loaimodem.list = response.data.data['loai_md']
                    if (this.listbox.loaimodem.list && this.listbox.loaimodem.list.length > 0) this.listbox.loaimodem.value = this.listbox.loaimodem.list[0].loaimd_id
                    this.listbox.loaicap.list = response.data.data['loai_cap']
                    if (this.listbox.loaicap.list && this.listbox.loaicap.list.length > 0) this.listbox.loaicap.value = this.listbox.loaicap.list[0].loaicap_id
                  }
                })
                if (this.thamso_nguoidung.chuquanmd_id && this.thamso_nguoidung.chuquanmd_id > 0) {
                  // Tham so nay khong lay duoc tu DB o thoi diem code do khong co du lieu (select * from admin.ct_tsmd where ma_ts='CHUQUAN_ID' and phanvung_id=26;)
                  this.listbox.chu_quan.valuewan = this.thamso_nguoidung.chuquanmd_id
                  this.listbox.chu_quan.valuetsl = this.thamso_nguoidung.chuquanmd_id
                }
                this.kiemtra_dm_mgwan_tsl = true
                //FORMLOAD default value
                if (this.listbox.phanloai_dv.list && this.listbox.phanloai_dv.list.filter((pl) => pl.loaitb_id == 134).length > 0) this.listbox.phanloai_dv.value = this.listbox.phanloai_dv.list.filter((pl) => pl.loaitb_id == 134)[0].phanloai_id
                if (this.listbox.loai_tb.list && this.listbox.loai_tb.list.filter((tb) => tb.dichvuvt_id == this.listbox.dichvu_vt.value).length > 0) this.listbox.loai_tb.valuemgwan = this.listbox.loai_tb.list.filter((tb) => tb.dichvuvt_id == this.listbox.dichvu_vt.value)[0].thietbidc_id
                this.loaisowan = 1
                this.buttons.btnHuongKNWan = this.loaisowan * 1 == LOAINODE_MGWAN.NODE_CHINH
                //if([DICHVUVIENTHONG.MEGAWAN, DICHVUVIENTHONG.METRONET, DICHVUVIENTHONG.MGW_DOITAC].indexOf(parseInt(newval))>=0)
              }
              //Default thuebao
              this.listbox.loaihinh_tb.value = LoaiHinhTB.MEGAWAN_ADSL // 23;
              this.listbox.kieu_ld.value = KieuLapDat.DM_MGW_DOCLAP // 42
              this.listbox.trangbi.valuewan = TRANGBI.VIENTHONG_TRANGBI //4
            }
            if (newval == DICHVUVIENTHONG.TSL || newval == DICHVUVIENTHONG.NOIBO_2DIEM) {
              if (this.listbox.loai_tb.list && this.listbox.loai_tb.list.filter((tb) => tb.dichvuvt_id == this.listbox.dichvu_vt.value).length > 0) {
                this.listbox.loai_tb.valuedau = this.listbox.loai_tb.list.filter((tb) => tb.dichvuvt_id == this.listbox.dichvu_vt.value)[0].thietbidc_id
                this.listbox.loai_tb.valuecuoi = this.listbox.loai_tb.list.filter((tb) => tb.dichvuvt_id == this.listbox.dichvu_vt.value)[0].thietbidc_id
              }
              if (this.listbox.loaicap.list && this.listbox.loaicap.list.length > 0) {
                this.listbox.loaicap.valuedau = this.listbox.loaicap.list[0].loaicap_id
                this.listbox.loaicap.valuecuoi = this.listbox.loaicap.list[0].loaicap_id
              }
              //Default thuebao
              this.listbox.loaihinh_tb.value = 67
              this.listbox.kieu_ld.value = KieuLapDat.DM_TSL_LUONG // 48
            }
            switch (newval * 1) {
              case DICHVUVIENTHONG.TSL:
              case DICHVUVIENTHONG.NOIBO_2DIEM:
                if (this.listbox.trangbi.list && this.listbox.trangbi.list.length > 0) this.listbox.trangbi.valuetsl = TRANGBI.VIENTHONG_TRANGBI
                if (this.listbox.tocdokenh.valuetsl && this.listbox.tocdokenh.valuetsl > 0) {
                  this.tocdo_id = this.listbox.tocdokenh.valuetsl
                  if (!this.kt_load) this.LayTienTheoKhoanMuc()
                } else this.tocdo_id = 0
                this.ltb_adsl_id = 1
                this.kh_cd = 0
                this.thuebao.cvlct = true
                break
              case DICHVUVIENTHONG.MEGAWAN:
              case DICHVUVIENTHONG.METRONET:
              case  DICHVUVIENTHONG.NOIBO_DADIEM:
                if (this.listbox.loai_tb.list && this.listbox.loai_tb.list.length > 0) {
                  if (this.listbox.loai_tb.valuemgwan && this.listbox.loai_tb.valuemgwan > 0) this.ltb_adsl_id = this.listbox.loai_tb.valuemgwan
                  else this.ltb_adsl_id = 1
                } else this.ltb_adsl_id = 1
                this.kh_cd = 0
                if (this.listbox.trangbi.list && this.listbox.trangbi.list.length > 0) {
                  this.trangbi_id = TRANGBI.VIENTHONG_TRANGBI
                  this.listbox.trangbi.valuewan = this.trangbi_id
                }
                if (this.listbox.tocdokenh.valuecir && this.listbox.tocdokenh.valuecir > 0) {
                  this.tocdo_id = this.listbox.tocdokenh.valuecir
                  if (!this.kt_load) this.LayTienTheoKhoanMuc()
                } else this.tocdo_id = 0
                this.thuebao.cvlct = true
                break
            }
            //this.LAY_DS_TBI_MACDINH(this.listbox.loaihinh_tb.value)
            let vetinh_id = ''
            let ppdata = {
              DICHVUVT_ID: this.listbox.dichvu_vt.value,
              PHOLD_ID: this.diachiLD.PHO_ID,
              PHUONGLD_ID: this.diachiLD.PHUONG_ID,
              LOAIKH_ID: this.listbox.loai_kh.value
            }
            await API.post_sp_lay_tt_theo_dvvt(this.axios, { ds_para: JSON.stringify(ppdata) }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                let apid = JSON.parse(response.data.data)
                if (apid['ERROR_CODE'] != 1) {
                  this.$toast.error(apid['MESSAGE'])
                  return
                }
                apid = apid['RESULT']
                if (!apid) return
                if (apid['VETINH_ID']) vetinh_id = apid['VETINH_ID']
                if (apid['DOITUONG_NEO'] && apid['DOITUONG_NEO'].length > 0) {
                  this.thuebao.didong.cboDTVinaPhone.list = apid['DOITUONG_NEO']
                } else this.thuebao.didong.cboDTVinaPhone.list = []
              }
            })
          } catch (ex) {
            this.$toast.error('' + ex)
            if (this.debug) console.error(ex)
          } finally {
            //await this.HT_GIAODIEN_DONVI()
            //await this.HT_DS_KhuyenMai_Combobox()
            this.loading(false)
            this.watchLock = false
          }
        }
      },
      'cbo_kieuld_id.Value': async function(val) {
        if (this.dsKieuYCLD.length > 0) {
          if (val === null) return
          if (!this.isEnterTimKiem) {
            this.Clear()
          }
          if (this.ht === 0) await this.timKiem()
          this.txt_ngaysnTB.Enable = true
          this.txt_tenthuebao.Enable = true
          this.txt_tenKH.Enable = true
          this.txt_maKH.Enabled = false
          this.txt_maTT.Enable = false
          this.txt_telKH.Enable = true
          this.txt_faxKH.Enable = true
          this.txt_soGT.Enable = true
          this.txt_ngaycap.Enable = true
          this.txt_noicap.Enable = true
          this.txt_ngayhethan.Enable = true
          this.txt_tenKH.Enable = true
          this.txt_diachiKH.Enable = true
          this.txt_tenTT.Enable = true
          this.cbo_maTBDD.Enable = true
          this.txt_stk_TT.Enable = true
          this.txt_chu_TK.Enable = true
          this.txt_mst_TT.Enable = true
          this.txt_ghiChuTT.Enable = true
          this.txt_diachiCT.Enable = true
          this.txt_ngaysnTB.Enable = true // read-only : false
          this.txt_ngaysn.Enable = true // read-only: false
          this.txt_diachiNDD.Enable = false
          this.isEnableBtnDiaChiNDD = false
          this.txt_gioitinh.Enable = false
          this.cbo_quoctich.Enable = false
          this.cbo_dantoc.Enable = false
          this.isEnableBtnEditDVK = false
          this.isEnableBtnTaoMIG = false
          this.chkTKChinh.Enable = false
          this.txt_emailTT.Enable = true // read-only : false
          this.txt_soDT_TT.Enable = true // read-only : false
          this.btnMaNVKD.Enable = false
          this.cbo_loaiKH.Enable = true
          // #region KieuLapDat.THAYDOI_TT_DICHVU_KHAC
          if (val == KIEULAPDAT.THAYDOI_TT_THUEBAO) {
            document.getElementById('tabThueBao').click()
            // #region KieuLapDat.THAYDOI_TT_THUEBAO
            this.txt_ngaysnTB.Enable = true // read-only : false
            this.txt_ngaysudung.Enable = true // read-only : false
            this.txt_ngaysn.Enable = false // read-only : true
            this.cbo_nganhang.Enable = false
            this.txt_tenKH.Enable = false
            this.txt_maKH.Enable = false
            this.txt_diachiKH.Enable = false
            this.txt_soGT.Enable = false
            this.txt_ngaycap.Enable = false
            this.txt_noicap.Enable = false
            this.txt_ngayhethan.Enable = false
            this.cbo_loaiKH.Enable = false
            this.isEnableBtnDiaChiKH = false
            this.cbo_giaytoKH.Enable = false
            this.isEnableBtnNSNN = false
            this.isEnableBtnNganhNghe = false
            this.cbo_nganhngheloc.Enable = false
            this.cbo_donviQL_id.Enable = true
            this.btnMaNVKD.Enable = !this.THAYDOI_TT_KHAC_BO_KV
            this.isEnableBtnDiaChiTB = true
            this.txt_diachiTB.Enable = true
            this.txt_telKH.Enable = false
            this.txt_faxKH.Enable = false
            this.txt_diachiLD.Enable = false
            this.txt_tenthuebao.Enable = true
            this.txt_ngaysnTB.Enable = true
            this.cbo_doituong_id.Enable = true
            // this.cbo_chuquan_id.Enable = true // IT360-266488: không cho phép sửa đổi chủ quản ở chức năng này
            this.isEnableBtnDiaChiLD = true
            this.cbo_mucuoc_id.Enable = true
            this.cbo_maTBDD.Enable = false
            this.chkTKChinh.Enable = true
            this.txt_maTT.Enable = false
            this.txt_tenTT.Enable = false
            this.txt_diachiTT.Enable = false
            this.isEnableBtnDiaChiTT = false
            this.isEnableBtnDiaChiCT = false
            this.txt_diachiCT.Enable = false
            this.isEnableBtnDiaChiBC = false
            this.txt_diachiBC.Enable = false
            this.cbo_HTTT.Enable = false
            this.isEnableBtnMaNVTC = false
            this.txt_mst_TT.Enable = false
            this.cbo_donviQL_id.Enable = false
            this.txt_stk_TT.Enable = false
            this.txt_chu_TK.Enable = false
            this.isEnableBtnEditDVK = false
            this.isEnableBtnNSNN = false
            this.txt_ghiChuTT.Enable = false
            if (this.cbo_dichvu_id.Value && this.cbo_dichvu_id.Value === DICHVUVIENTHONG.GPHONE) {
              this.isVisibleBtnDiaChiLD = true
            } else this.isVisibleBtnDiaChiLD = false
            this.txt_soDT_TT.Enable = false
            this.txt_emailTT.Enable = false
          } else if (val == KIEULAPDAT.THAYDOI_TT_THANHTOAN) {
            document.getElementById('tabThanhToan').click()
            // #region KieuLapDat.THAYDOI_TT_THANHTOAN
            this.txt_ngaysnTB.Enable = false // read-only : true
            this.txt_ngaysudung.Enable = false // read-only : true
            this.txt_ngaysn.Enable = false // read-only : true
            this.isEnableBtnNganhNghe = false
            this.cbo_nganhngheloc.Enable = false
            this.isEnableBtnDiaChiTT = true
            this.isEnableBtnDiaChiBC = true
            this.isEnableBtnDiaChiCT = true
            this.cbo_HTTT.Enable = true
            this.isEnableBtnMaNVTC = true
            this.txt_tenthuebao.Enable = false
            this.txt_diachiTB.Enable = false
            this.txt_ngaysnTB.Enable = false
            this.isEnableBtnDiaChiLD = false
            this.cbo_doituong_id.Enable = false
            this.cbo_chuquan_id.Enable = false
            this.isEnableBtnDiaChiLD = false
            this.isEnableBtnDiaChiTB = false
            this.cbo_mucuoc_id.Enable = false
            this.isEnableBtnTaoMIG = true
            this.isEnableBtnEditDVK = true
            this.cbo_donviQL_id.Enable = true
            if (this.dsHinhThuc.length > 0) {
              this.httt_id = this.cbo_HTTT.Value
              if (this.httt_id == HINHTHUCTHANHTOAN.UYNHIEM_THU) {
                this.cbo_nganhang.Enable = true
                if (this.dsNganHang.length > 0) {
                  let rsMapId = await this.$root.context.get('web-hopdong/chuyendich/map_loai_nh_id_neo', {
                    nganhang_id: this.cbo_nganhang.Value ? this.cbo_nganhang.Value : 0
                  })
                  let loai_nh = rsMapId.data && rsMapId.data.length > 0 ? rsMapId.data[0].loai_nh : null
                  if (loai_nh == 2) {
                    this.isEnableBtnNSNN = true
                  } else {
                    this.isEnableBtnNSNN = false
                  }
                }
              } else {
                this.cbo_nganhang.Enable = false
              }
            }
          } else if (val == KIEULAPDAT.THAYDOI_TT_KHACHHANG) {
            document.getElementById('tabKhachHang').click()
            // #region KieuLapDat.THAYDOI_TT_KHACHHAN
            this.cbo_quoctich.Enable = true
            this.cbo_dantoc.Enable = true
            this.txt_gioitinh.Enable = true
            this.isEnableBtnDiaChiNDD = true
            this.txt_diachiNDD.Enable = true
            this.txt_ngaysnTB.Enable = false // read-only : true
            this.txt_ngaysudung.Enable = false // read-only : true
            this.cbo_nganhang.Enable = false
            this.isEnableBtnNSNN = false
            this.txt_maKH.Enable = true
            this.isEnableBtnDiaChiKH = true
            this.cbo_giaytoKH.Enable = true
            this.isEnableBtnNganhNghe = true
            this.cbo_nganhngheloc.Enable = true
            this.cbo_maTBDD.Enable = false
            this.txt_ngaycap.Enable = true
            this.txt_ngayhethan.Enable = true
            this.txt_ngaycap.Readonly = false
            this.txt_ngayhethan.Readonly = false
            this.txt_maTT.Enable = false
            this.txt_tenTT.Enable = false
            this.txt_diachiTT.Enable = false
            this.txt_diachiCT.Enable = false
            this.txt_diachiBC.Enable = false
            this.isEnableBtnDiaChiTT = false
            this.isEnableBtnDiaChiCT = false
            this.isEnableBtnDiaChiBC = false
            this.cbo_HTTT.Enable = false
            this.isEnableBtnMaNVTC = false
            this.txt_mst_TT.Enable = false
            this.txt_stk_TT.Enable = false
            this.txt_chu_TK.Enable = false
            this.txt_emailTT.Enable = false // ReadOnly = true;
            this.txt_soDT_TT.Enable = false // ReadOnly = true;
            this.txt_tenthuebao.Enable = false
            this.txt_diachiTB.Enable = false
            this.isEnableBtnDiaChiLD = false
            this.cbo_doituong_id.Enable = false
            this.cbo_chuquan_id.Enable = false
            this.cbo_mucuoc_id.Enable = false
            this.isEnableBtnDiaChiTB = false
            this.txt_ghiChuTT.Enable = false
            this.isEnableBtnEditDVK = false
            this.txt_tenKH.Enable = true
          }
        }
      },
      'listbox.kieu_ld.value': function(newval, oldval) {
        if (!(newval != oldval && newval > 0)) return
        this.thuebao.mgwan.madoicap = null
        this.thuebao.mgwan.matn = null
        this.HT_GIAODIEN_DONVI()
        if (!this.watcherActive) return
        this.defaultCongNghe(newval)
        //TODO can lam them o function cboKieuLD_SelectedValueChanged
        if (!this.kt_load) {
          this.LayTienTheoKhoanMuc()
          if (this.listbox.doituong.value != DUNG_THU.DOITUONG_TB) this.HT_DS_KhuyenMai_Combobox()
        }
        this.watchLock = false
      },
      'chkTKChinh.Value': function(val) {
        this.cbo_tkchinh_id.Enable = val
      },
      'cbo_loaiKH.Value': async function(val) {
        try {
          if (this.dsLoaiKH.length > 0) {
            let drPLKH = this.dsPhanLoaiKH.find(item => item.loaikh_id == val)
            if (this.nhomplkh <= 1 || this.nhomplkh > 1) { //chỉ thay đổi nếu nhóm pl là ko theo doanh thu
              this.PHANLOAIKH_ID = 0
              this.txt_phanloaiKH.Value = ''
              if (drPLKH) {
                this.PHANLOAIKH_ID = drPLKH.phanloaikh_id
                this.txt_phanloaiKH.Value = drPLKH.ma_plkh
              } else {
                let kieukh_id = -1
                let loaiKH = this.dsLoaiKH.find(item => item.loaikh_id == val)
                if (loaiKH) kieukh_id = loaiKH.loai
                if (kieukh_id == this.KIEU_KH.CA_NHAN) {
                  let dr_PLKH = this.dsPhanLoaiKH.filter(item => item.hienthi_md == this.PHANLOAI_KH.CA_NHAN)
                  if (dr_PLKH.length > 0) {
                    this.PHANLOAIKH_ID = dr_PLKH[0].phanloaikh_id
                    this.txt_phanloaiKH.Value = dr_PLKH[0].ma_plkh
                  }
                } else {
                  let dr_PLKH = this.dsPhanLoaiKH.filter(item => item.hienthi_md == this.PHANLOAI_KH.DOANH_NGHIEP)
                  if (dr_PLKH.length > 0) {
                    this.PHANLOAIKH_ID = dr_PLKH[0].phanloaikh_id
                    this.txt_phanloaiKH.Value = dr_PLKH[0].ma_plkh
                  }
                }
              }
            }
            // thêm nhân viên quản lý
            if (this.phuongtt_id && (this.phott_id || this.aptt_id || this.khutt_id)) {
              let ds = []
              if (this.donviql_httt == 1) {
                let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_v2', {
                  phuong_id: this.phuongtt_id,
                  pho_id: this.phott_id,
                  ap_id: this.aptt_id,
                  khu_id: this.khutt_id,
                  kieu: 1,
                  loaikh_id: val,
                  httt_id: this.cbo_HTTT.Value
                })
                ds = rs.data
              } else {
                if (this.phantap_kh) {
                  let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_ptkh', {
                    phuong_id: this.phuongtt_id,
                    pho_id: this.phott_id,
                    ap_id: this.aptt_id,
                    khu_id: this.khutt_id,
                    dacdiem_id: this.dacdiemtt_id,
                    kieu: 1,
                    loaikh_id: this.cbo_loaiKH.Value,
                    nganhnghe_id: this.cbo_nganhngheloc.Value,
                    doanhthu: this.pt_doanhthu,
                    diemtn: this.pt_diem_tn
                  })
                  ds = rs.data
                } else {
                  let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql', {
                    phuong_id: this.phuongtt_id,
                    pho_id: this.phott_id,
                    ap_id: this.aptt_id,
                    khu_id: this.khutt_id,
                    kieu: 1,
                    loaikh_id: this.cbo_loaiKH.Value
                  })
                  ds = rs.data
                }
              }
              if (ds && ds.length <= 0) {
                if (this.dsDonViQL.length !== 1) {
                  this.$root.toastError('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý!')
                  this.cbo_donviQL_id.Value = null
                  return
                }
              } else if (ds && ds.length == 1) {
                this.cbo_donviQL_id.Value = ds[0].donvi_id
              } else {
              }
            }
          }
        } catch (e) {
          console.log(e)
        }
      },
      '$route.name': async function(newVal) {
        if (Object.keys(this.$route.query).length > 0) {
          let query = this.$route.query
          if (!query.hdkh_cha_id) {
            this.cbo_kieuld_id.Value = query.kieuld_id
            this.cbo_dichvu_id.Value = query.dichvuvt_id
            await this.LookupByAccount(query.vmatb, this.cbo_dichvu_id.Value)
          } else {
            await this.LoadTuHopDongTuVan(query.hdkh_cha_id)
          }
        }
      },
      'ItemList': {
        handler: function(after, before) {
          // Changes detected. Do work...
          console.log(after)
          console.log(before)
          if (this.ItemList) {
            if (this.$refs['SO_THANG']) {
              let itemNew = before.find(item => item.FIELD_NAME === 'SO_THANG')
              let itemOld = after.find(item => item.FIELD_NAME === 'SO_THANG')
              console.log(itemNew.FIELD_VALUE)
              console.log(itemOld.FIELD_VALUE)
              console.log(itemOld && itemNew)
              if (itemOld && itemNew) {
                if (itemOld.FIELD_VALUE !== itemNew.FIELD_VALUE) {
                  if (this.$refs['NGAY_BD'][0].startDate) {
                    moment(new Date(String(this.$refs['NGAY_BD'][0].startDate))).format('dd/MM/yyyy')
                    moment(new Date(String(this.$refs['NGAY_KT'][0].startDate))).format('dd/MM/yyyy')
                    // this.$refs["NGAY_KT"][0].startDate = moment(this.$refs["NGAY_BD"][0].startDate).add(1, 'months').format('dd/MM/yyyy');
                    console.log(this.$refs['NGAY_BD'][0].startDate)
                    console.log(this.$refs['NGAY_KT'][0].startDate)
                  }
                }
              }
            }
          }
        },
        deep: false,
        immediate: true
      }
    },
    methods: {
      async btnXacNhanCQ_Click()
      {
        this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xác nhận không thay đổi thông tin không ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then( async  v => {
                    if(v) {
                         let rs = await this.$root.context.post('/web-thicong/capnhatthongtin/sp_doisoat_capnhat_trangthai', {
                          p_id_row: this.row_id,
                          p_loai_tt : 1,
                        })

                        if (rs.error == '200' && rs.error_code == 'BSS-00000000') {
                          this.$root.toastSuccess('Xác nhận không thay đổi thông tin thành công!')
                        }
                    }
                });
      },
      terminateForm() {
        this.$emit('form-close');
      },
      Popup(modalId) {
        this.loading(false)
        if (this.$refs[modalId]) this.$refs[modalId].show()
        else this.$bvModal.show(modalId)
      },
      defaultCongNghe(newval) {
        if (newval && newval > 0) {
          if ((!this.listbox.congnghe.value || this.listbox.congnghe.value == 0)
            && this.listbox.kieu_ld.value && this.listbox.kieu_ld.value > 0
            && this.listbox.congnghe.list != null
            && this.listbox.congnghe.list.length > 0) {
            let list = this.listbox.congnghe.list.filter((cn) => cn.kieuld_id == this.listbox.kieu_ld.value)
            if (list && list.length > 0) this.listbox.congnghe.value = list[0].congnghe_id
          }
        }
      },
      loadForm: async function() {
        try {
          await this.SetButton(-1)
          await this.getActiveQuyTrinhXacMinhTTLH()
          //await this.FormLoad();
          this.HT_HTHS_KMTT = false
          let rsTTDM = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_cacloai_danhmuc_v2', {
            chuoi: 'HINHTHUC_TT|DOITUONG|NGANHANG|DICHVU_VT|LOAIHINH_TB|NGANHNGHE|LOAI_GT|PHANLOAI_KH|LOAI_KH|DANTOC|QUOCTICH|CHU_QUAN|CONGNGHE|DONVI_QL'
          })

          console.log(rsTTDM)
          this.dsDonViTC = rsTTDM.data.dsDonViQL.filter(v => v.loaidv_id == 3)
          this.cbo_donviTC_id.Value = this.dsDonViTC.length ? this.dsDonViTC[0].donvi_id : null
          this.cbo_donviTC_id.Enable = false
          this.dsDoiTuongTB = rsTTDM.data.dsDoiTuong
          this.cbo_doituong_id.Value = this.dsDoiTuongTB.length ? this.dsDoiTuongTB[0].doituong_id : null
          this.dsChuQuan = rsTTDM.data.dsChuQuan
          this.cbo_chuquan_id.Value = this.dsChuQuan.length ? this.dsChuQuan[0].chuquan_id : null
          this.dsHinhThuc = rsTTDM.data.dsHinhThucTT
          this.dsNganHang = rsTTDM.data.dsNganHang
          if (this.dsNganHang.length > 0) {
            this.cbo_nganhang.Value = this.dsNganHang[0].nganhang_id
          }
          this.dsDonViQL = rsTTDM.data.dsDonViQL.filter(v => v.loaidv_id == 2)
          this.cbo_donviQL_id.Value = this.dsDonViQL.length ? this.dsDonViQL[0].donvi_id : null
          this.dsGiayToKH = rsTTDM.data.dsLoaiGT
          this.cbo_giaytoKH.Value = this.dsGiayToKH.length ? this.dsGiayToKH[0].loaigt_id : null
          this.dsPhanLoaiKH = rsTTDM.data.dsPhanLoaiKH.filter(v => v.nhom_pl == 1)
          this.dsLoaiKH = rsTTDM.data.dsLoaiKH
          if (this.dsLoaiKH.length) {
            this.setLoaiKHID(this.dsLoaiKH[0].loaikh_id)
          } else this.cbo_loaiKH.Value = null
          this.dsDanToc = rsTTDM.data.dsDanToc
          this.cbo_dantoc.Value = this.dsDanToc.length ? this.cbo_dantoc.Value = 1 : null
          this.dsQuocTich = rsTTDM.data.dsQuocTich
          this.cbo_quoctich.Value = this.dsQuocTich.length ? this.dsQuocTich[0].quoctich_id : null
          this.dsDichVuVT = rsTTDM.data.dsDichVuVT.filter(v => v.dichvuvt_id == 7 || v.dichvuvt_id == 8 || v.dichvuvt_id == 9)
          this.cbo_dichvu_id.Value = this.propDichvuvt_id
          this.dichvuvt_id = this.propDichvuvt_id
          this.listbox.dichvu_vt.value = this.cbo_dichvu_id.Value
          this.dsNganhNghe = rsTTDM.data.dsNganhNghe
          this.cbo_nganhngheloc.Value = this.dsNganhNghe.length ? this.dsNganhNghe[0].nganhnghe_id : null
          this.listbox.dichvu_vt.list = rsTTDM.data.dsDichVuVT.filter(v => v.dichvuvt_id == 7 || v.dichvuvt_id == 8 || v.dichvuvt_id == 9)
          this.listbox.chu_quan.list = rsTTDM.data.dsChuQuan
          if (this.listbox.chu_quan.list && this.listbox.chu_quan.list.length > 0) {
            this.listbox.chu_quan.valueldcd = this.listbox.chu_quan.list[0].chuquan_id
            this.listbox.chu_quan.valuecd = this.listbox.chu_quan.list[0].chuquan_id
            this.listbox.chu_quan.valuewan = this.listbox.chu_quan.list[0].chuquan_id
            this.listbox.chu_quan.valueldmgwan = this.listbox.chu_quan.list[0].chuquan_id
            this.listbox.chu_quan.valueadsl = this.listbox.chu_quan.list[0].chuquan_id
            this.listbox.chu_quan.valueldadsl = this.listbox.chu_quan.list[0].chuquan_id
            this.listbox.chu_quan.valuetsl = this.listbox.chu_quan.list[0].chuquan_id
            this.listbox.chu_quan.valueims = this.listbox.chu_quan.list[0].chuquan_id
            this.listbox.chu_quan.valueldims = this.listbox.chu_quan.list[0].chuquan_id
          }

          let api2 = API.post_lay_danhmuc_lapmoi_tt_chung_v8(this.axios, { vchuoi: 'KIEU_LD|CONGNGHE|PHANLOAI_DV|THOIHAN|TRANG_BI|LOAIHINH_TB' }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.listbox.kieu_ld.list = response.data.data['kieu_ld']
              this.listbox.congnghe.list = response.data.data['congnghe']
              this.defaultCongNghe(this.listbox.kieu_ld.value)
              this.listbox.phanloai_dv.list = response.data.data['phanloai_dv']
              this.listbox.thoihan.list = response.data.data['thoihan']
              if (this.listbox.thoihan.list && this.listbox.thoihan.list.length > 0) {
                this.listbox.thoihan.valuecd = this.listbox.thoihan.list[0].thoihan_id
                this.listbox.thoihan.value = this.listbox.thoihan.list[0].thoihan_id
                this.listbox.thoihan.valuetsl = this.listbox.thoihan.list[0].thoihan_id
                this.listbox.thoihan.valuewan = this.listbox.thoihan.list[0].thoihan_id
                this.listbox.thoihan.valueadsl = this.listbox.thoihan.list[0].thoihan_id
                this.listbox.thoihan.valueims = this.listbox.thoihan.list[0].thoihan_id
              }
              this.listbox.trangbi.list = response.data.data['trangbi']
              this.listbox.loaihinh_tb.list = response.data.data['loaihinh_tb']
            }
          })

          let api4 = API.post_sp_ht_phantach_tocdo(this.axios, { kieu: '1' }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.listbox.ht_phantach_tocdo.list = response.data.data
              if (this.listbox.ht_phantach_tocdo.list && this.listbox.ht_phantach_tocdo.list.length > 0) this.listbox.ht_phantach_tocdo.value = this.listbox.ht_phantach_tocdo.list[0].phantach_id
            }
          })

          let rsChuQuanLD = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_chuquan_ld')
          this.dsChuQuanLD = rsChuQuanLD.data
          this.cbo_chuquanLD_id.Value = this.dsChuQuanLD.length ? this.dsChuQuanLD[0].chuquan_id : null
          let rsThamSo = await this.$root.context.post('/web-hopdong/thaydoiloaihinhtbcodinhims/sp_lay_ds_thamso_md_theo_kieu', {
            kieu_id: 0
          })
          if (rsThamSo.data.length > 0) {
            let dsThamSo = rsThamSo.data
            let str_temp = 'DONVIQL_HTTT'
            let obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.donviql_httt = 1
            }
            str_temp = 'TD_TTK_SUA_NGAY_HT_CNTT'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.ts_sua_ngay_ht_dv_cntt = 1
            }
            str_temp = 'CHECK_CHUANHOA_CNTT'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.check_chuahoa_cntt = true
            }
            str_temp = 'PHANTAP_KH'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.phantap_kh = true
            }
            str_temp = 'THAMSO_CHUANHOA_DIACHI'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.thamso_chuanhoa_diachi = true
            }
            str_temp = 'KIEMTRA_LIENHE_THANHTOAN'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.kiemtra_lienhe_thanhtoan = 1
            }
            str_temp = 'KT_NGAYBD_THEMMOI_KM'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.kt_ngaybd_themmoi_km = true
            }
            let ht_khl = -1
            str_temp = 'HIENTHI_KHL_DATMOI'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts) {
              ht_khl = obj.ten_ts
            }
            str_temp = 'KHONGNHAP_STK_UYNHIEMTHU'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts) {
              this.khongnhap_nh_unt = parseInt(obj.ten_ts)
            } else {
              this.khongnhap_nh_unt = 0
            }
            str_temp = 'TUYENTHU_HTTT'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.tuyenthu_httt = 1
            }
            str_temp = 'THAYDOI_TD_CNTT_BDK'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.thaydoi_td_cntt = 1
            }
            str_temp = 'SUA_KM_TINHCUOC'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.suakm_tinhcuoc = 1
            }
            str_temp = 'TICHHOP_DIDONG_DHSX'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.tichhop_didong_dhsx = true
            }
            str_temp = 'KIEMTRA_MSTMNS_KHDN'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.ts_kt_mstmns_kh = 1
            }
            str_temp = 'THEM_TT_HDKHDN'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.ts_kt_tthdkhdn = 1
            }
            str_temp = 'KT_NO_THAYDOI_THONGTIN_TT'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.kt_no_thaydoi_thongtin_tt = 1
            }
            //begin tinh cuoc ngay BSS-88020
            str_temp = 'TINHCUOC_NGAY'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            if (obj && obj.ten_ts == '1') {
              this.is_tinhcuoc_ngay = true
            }
            //end tinh cuoc ngay BSS-88020
            str_temp = 'SINH_MA_GD_THEO_DV'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            this.ts_sinhma_gd_theo_donvi = obj ? true : false
            str_temp = 'THAYDOI_TT_KHAC_BO_KV'
            obj = dsThamSo.find(v => v.ma_ts == str_temp)
            this.thaydoi_tt_khac_bo_kv = obj ? true : false
          }
          let rsKieuKM = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_kieu_yc_thaydoikhuyenmai?loaikm=${this.iloai_km}`)
          if (this.suakm_tinhcuoc == 1) {
            this.dsKieuYCKM = rsKieuKM.data
          } else {
            this.dsKieuYCKM = rsKieuKM.data.filter(v => v.id != 4)
          }
          this.cbo_kieukm_id.Value = this.dsKieuYCKM.length ? this.dsKieuYCKM[0].id : null
          if (this.Tag !== null && this.Tag !== '') {
            this.quyen = this.Tag.toString().split(';')
            if (this.quyen.length > 0) {
              this.iloai_km = parseInt(this.quyen[0])
            }
            if (this.iloai_km == 0) {
              this.labelFunctionTitle = 'THAY ĐỔI THÔNG TIN KHUYẾN MẠI'
            } else if (this.iloai_km == 1) {
              this.labelFunctionTitle = 'THAY ĐỔI THÔNG TIN KHUYẾN MẠI ĐẶT CỌC'
            } else if (this.iloai_km == 2) {
              this.isEnableBtnPhai = false
              this.isEnableBtnTrai = false
              this.cbo_kieukm_id.Enable = false
              this.cbo_khuyenmai_id.Enable = false
              this.isEnableBtnChiTietKM = false
              this.isEnableBtnChonKhuyenMai = false
            }
          } else {
            this.isEnableBtnPhai = false
            this.isEnableBtnTrai = false
            this.cbo_kieukm_id.Enable = false
            this.cbo_khuyenmai_id.Enable = false
            this.isEnableBtnChiTietKM = false
            this.isEnableBtnChonKhuyenMai = false
            this.iloai_km = 2
          }
          try {
            let tag = this.Tag
            if (this.iloai_km == 2) {
              if (this.Tag.toString().split(';').length > 1) {
                tag = tag.toString().split(';')[1]
              } else {
                tag = ''
              }
            } else {
              tag = ''
            }
            if (tag != '') {
              this.TTHD_ID_TAG = parseInt(tag)
              this.HT_HTHS_KMTT = true
              this.tsbtnCapNhatDB.Visible = true
              this.tsbtnNhapMoi.Visible = false
              this.tsbtnGhiLai.Visible = false
              this.tsbtnXoa.Visible = false
              this.tsbtnHuyBo.Visible = false
              this.tsbtnThongTinLH.Visible = false
              this.tsbtnEmail.Visible = false
              this.tsbtnUser.Visible = false
              this.tsbtnUpImg.Visible = false
              this.tsbtnToaNha.Visible = false
              this.tsbtnThanhToan.Visible = false
            }
          } catch (error) {
          }
          if (this.iloai_km == 1 || this.iloai_km == 0) {
            this.cbo_khuyenmai_id.Enable = true
            this.isEnableBtnChiTietKM = true
            this.isEnableBtnChonKhuyenMai = true
          }

          this.kt_load = false
        } catch (error) {
        }
      },
      /**
       * Chọn loại khách hàng
       * @param loaikh_id
       */
      setLoaiKHID(loaikh_id) {
        this.$nextTick(() => {
          let grid = this.$refs['cboGridLoaiKH'].$children[0].grid
          let idx = grid.getCurrentViewRecords().findIndex(item => item.loaikh_id == loaikh_id)
          if (idx > -1) grid.selectRow(idx)
        })
      },
      layDsMucCuocTB: async function() {
        // try {
        let tocdo = 0
        let muccuoc = 0
        if (this.loaitb_id == LOAIHINHTB.INTERNET_FTTH || this.loaitb_id == LOAIHINHTB.WIFI_FIBER
          || this.loaitb_id == LOAIHINHTB.INTERNET_ADSL || this.loaitb_id == LOAIHINHTB.INTERNET_MYTV
          || this.dichvuvt_id == DICHVUVIENTHONG.ANTOAN_BAOMAT_TT || this.dichvuvt_id == DICHVUVIENTHONG.DICHVU_CNTT
          || this.dichvuvt_id == DICHVUVIENTHONG.HOINGHI_TRUYENHINH || this.dichvuvt_id == DICHVUVIENTHONG.TRUNGTAM_DULIEU) {
          tocdo = this.tocdo_id
          muccuoc = this.muccuoc_id
        }
        let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_mucuoc_tb', {
          khuld_id: this.khuld_id,
          apld_id: this.apld_id,
          phold_id: this.phold_id,
          phuongld_id: this.phuongld_id,
          loaitb_id: this.loaitb_id,
          doituong_id: this.cbo_doituong_id.Value,
          tocdo_id: tocdo,
          muccuoc_id: muccuoc,
          ngay: this.formatDate(this.dtp_ngayYc.Value)
        })
        this.dsMucCuocTB = rs.data
        //this.cbo_mucuoc_id.Value = this.dsMucCuocTB.length ? this.dsMucCuocTB[0].mucuoctb_id : null;
        if (this.muccuoc_tb_cu > 0 && (this.hdkh_id == 0 || this.hdkh_id == null)) {
          let obj = this.dsMucCuocTB.find(v => v.mucuoctb_id == this.muccuoc_tb_cu)
          if (obj) this.cbo_mucuoc_id.Value = obj.mucuoctb_id
        }
        // if (this.cbo_mucuoc_id.Value != null) {
        //   let obj = this.dsMucCuocTB.find(x => x.mucuoctb_id == this.cbo_mucuoc_id.Value)
        //   if (!obj) {
        //     this.$root.toastError("Đối tượng mới chưa được gán với mức cước cũ, liên hệ admin để gán đối tượng mới vào mức cước cũ hoặc phải chọn lại mức cước mới")
        //     // return
        //   }
        // }
        // } catch (error) {
        //
        // }
      },
      layDsTKChinh: async function() {
        try {
          let rsTKChinh = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_thuebao_chinh', {
            kieu: 2,
            ma_tb: this.txt_maTB.Value,
            upper: 0,
            tocdo_id: -2,
            khachhang_id: this.khachhang_id,
            diachi_ld: this.txt_diachiLD.Value
          })
          this.dsTkChinh = rsTKChinh.data
          this.cbo_tkchinh_id.Value = this.thuebao_cha_id
        } catch (error) {
        }
      },
      hienThiTTHopDongKH: async function(item) {
        try {
          this.ItemList = []
          let data = item[0]
          this.httt_id_cu = -1
          if (data) {
            this.hdkh_id = data.hdkh_id
            this.txt_magiaodich.Value = data.ma_gd
            this.txt_mahopdong.Value = data.ma_hd
            this.txt_maKH.Value = data.ma_kh
            if (data.khachhang_id) {
              this.khachhang_id = data.khachhang_id
            } else {
              this.khachhang_id = 0
            }
            this.txt_tenKH.Value = data.ten_kh
            this.txt_diachiKH.Value = data.diachi_kh
            this.txt_diachiNDD.Value = data.diachi_ndd
            let rsDiaChi = await this.$root.context.get(`/web-hopdong/ThayDoiHopDong/lay_ds_diachi_theo_hdkhid?hdkhId=${this.hdkh_id}`)
            this.layThongTinDiaChi(rsDiaChi.data[0], 1)
            this.dtp_ngayYc.Value = new Date(this.ddmmyyyyhhmissToyyyymmddhhmiss(data.ngay_yc))
            this.dtp_ngaylapHd.Value = new Date(this.ddmmyyyyhhmissToyyyymmddhhmiss(data.ngaylap_hd))
            this.txt_telKH.Value = data.so_dt
            this.txt_faxKH.Value = data.so_fax
            this.txt_mstKH.Value = data.mst
            this.txt_nguoiDD.Value = data.nguoi_dd
            this.txt_chucdanhDD.Value = data.chucdanh_dd
            this.loaikh_id = data.loaikh_id
            if (this.phantap_kh && this.khachhang_id > 0) {
              let ds_dt_dtn
              await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/phantap_laydoanhthu_dtn/${this.khachhang_id}`)
                .then(rs => {
                  ds_dt_dtn = rs.data
                }).catch(e => {
                  throw e
                })
              if (ds_dt_dtn.length > 0) {
                if (ds_dt_dtn[0].doanhthu)
                  this.pt_doanhthu = ds_dt_dtn[0].doanhthu
                else this.pt_doanhthu = 0
                if (ds_dt_dtn[0].dtn)
                  this.pt_diem_tn = ds_dt_dtn[0].dtn
                else this.pt_diem_tn = 0
              } else {
                this.pt_doanhthu = 0
                this.pt_diem_tn = 0
              }
            }
            if (this.cbo_dichvu_id.Value == DICHVUVIENTHONG.DI_DONG) {
              this.cboDTVinaPhone.Enable = true
              // let rsMapId = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
              //   id_neo: 'loaikh_id_neo',
              //   table: 'css.loai_kh',
              //   dk: ' where loaikh_id =' + this.loaikh_id
              // })
              // let loaikh_id_neo = rsMapId.data;
              let rsMapId = await this.$root.context.get('web-hopdong/hoanthienhschuyenquyen/map_loaikh_id_neo', {
                loaikh_id: this.loaikh_id
              })
              let loaikh_id_neo = rsMapId.data && rsMapId.data.length > 0 ? rsMapId.data[0].loaikh_id_neo : null
              await this.HT_DT_VinaPhone_Combobox(loaikh_id_neo)
            }
            if (data.ngayhethan_gt)
              this.txt_ngayhethan.Value = this.formatDate(data.ngayhethan_gt)
            else this.txt_ngayhethan.Value = ''
            if (data.ngay_sn)
              this.txt_ngaysn.Value = this.formatDate(data.ngay_sn)
            else this.txt_ngaysn.Value = ''
            this.cbo_giaytoKH.Value = data.loaigt_id
            this.txt_soGT.Value = data.so_gt
            if (data.ngaycap)
              this.txt_ngaycap.Value = this.formatDate(data.ngaycap)
            else this.txt_ngaycap.Value = ''
            if (data.nganhnghe_id) {
              this.cbo_nganhngheloc.Value = data.nganhnghe_id
              this.nganhnghe_id = data.nganhnghe_id
            }
            if (data.loaikh_id) {
              this.loaikh_id = data.loaikh_id
              this.setLoaiKHID(data.loaikh_id)
            } else this.loaikh_id = 1
            this.txt_noicap.Value = data.noicap
            // hiển thị thông tin id tiểu ngành c1,c2,c3
            if (data.tnc1_id)
              this.TNC1_ID = data.tnc1_id
            if (data.tnc2_id)
              this.TNC2_ID = data.tnc2_id
            if (data.tnc3_id)
              this.TNC3_ID = data.tnc3_id
            if (data.tnc4_id)
              this.TNC4_ID = data.tnc4_id
            try {
              if (data.phanloaikh_id != null && data.phanloaikh_id !== '') {
                this.PHANLOAIKH_ID = data.phanloaikh_id
                // if (this.PHANLOAIKH_ID){
                await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ma_plkh/${this.PHANLOAIKH_ID}`)
                  .then(res => {
                    if (res.error === '200')
                      if (res.data.length > 0) {
                        if (res.data[0].ma_plkh !== '-1')
                          this.txt_phanloaiKH.Value = res.data[0].ma_plkh
                        if (res.data[0].nhompl !== '-1')
                          this.nhomplkh = res.data[0].nhomplkh
                      }
                  }).catch(e => {
                    throw e
                  })
                // }
              }
            } catch (e) {
            }
            let rsHopDongTT = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_hopdong_thanhtoan_theo_hdkh?hdKhId=${this.hdkh_id}`)
            await this.hienThiThongTinHopDongTT(rsHopDongTT.data)
            let rsHopDongTB = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_hopdong_thuebao_theo_hdkh?hdKhId=${this.hdkh_id}`)
            this.dsHDTB = rsHopDongTB.data
            await this.hienThiThongTinHopDongTB(rsHopDongTB.data)
            await this.SetButton(3)
          } else await this.SetButton(0)
        } catch (e) {
          console.log(e)
        }
      },
      hienThiTTDanhBa: async function(somay) {
        try {
          this.ItemList = []
          await this.SetButton(0) // trucnt bosung: code cũ thiếu không set trạng thái lại khi load db
          this.httt_id_cu = -1
          this.thuebao_cha_id = 0
          /*let rsDsDB = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_danhba_theo_matb', {
            maTb: somay,
            dichVuVtId: this.dichvuvt_id,
            donViDlId: 0
          })*/
          let rsDsDB = await this.$root.context.post('/web-tracuu/tracuu/lay_danhba_theo_matb_v2', {
            ma_tb: somay,
            dichvuvt_id: +this.dichvuvt_id,
            donvi_dl_id: 0,
            loaitb_id: 0
          });
          let dsDB = rsDsDB.data
          if (dsDB.length > 0) {
            let data = dsDB[0]
            let data2 = null;
            if (dsDB.length > 1) {
              data2 = dsDB[1]
              this.loading(false)
              let kq = await this.$refs['frmDSTBCungMa'].show(dsDB)
              if (kq.ok == true && kq.data)
                data = kq.data
              this.loading(true)
            }
            console.log('dulieuthuebao',data);
            for (var i = 0; i < this.DT_CONTROLS.list.length; i++) {
              console.log('this.DT_CONTROLS.list[i]',this.DT_CONTROLS.list[i]);
              /*let row = {}
              row.HDTB_ID = this.hdtb_id
              row.LOAIDV_ID = this.DT_CONTROLS.list[i].LOAIDV_DICH_ID ? this.DT_CONTROLS.list[i].LOAIDV_DICH_ID : 0
              row.DONVI_ID = this.DT_CONTROLS.value[i]
              row.KIEUDV_ID = 2
              this.dsHDTBDV.push(row)*/
              if (this.DT_CONTROLS.list[i].LUONG_ID == 1047) this.DT_CONTROLS.value[i] = data.donvi_cc ?? 0
            }
            this.cbo_chuquan_id.Value = data.chuquan_id
            if (this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL) {
              this.thuebao.tsl.ma_tb = data.ma_tb //add new
              this.listbox.chu_quan.valuetsl = data.chuquan_id??0 // add new
              this.listbox.thoihan.valuetsl = data.thoihan_id??0 // add new
              this.listbox.loaikenh.valuetsl = data.loaikenh_id??0 // add new
              this.listbox.loaihinh_tb.valuetsl = data.loaitb_id??0 // add new
              this.listbox.congnghe.valuetsl = data.congnghe_id??0 // add new
              this.thuebao.tsl.ten_tb = data.ten_tb // add new
              this.thuebao.tsl.diachi_tb = data.diachi_tb // add new
              this.listbox.loaikenh.valuetsl = data.loaikenh_id??0 // add new
              this.thuebao.tsl.txtMaHD_TTKD_TSL = data.ma_hd //add new
              this.listbox.trangbi.valuetsl = data.trangbi_id??0 //add new
              this.listbox.doitactsl.value = data.doitac_id??0 //add new
              this.thuebao.tsl.diachi_ld = data.diachi_ld //add new
              if (data && data.daucuoi_id == 1) {
                this.diachiDau.DIACHI = data.diachi ?? ''
                this.listbox.loaicap.valuedau = data.loaicap_id ?? ''
              } else if (data && data.daucuoi_id == 2) {
                this.diachiCuoi.DIACHI = data.diachi ?? ''
                this.listbox.loaicap.valuecuoi = data.loaicap_id ?? ''
              }
              if (data2 && data2.daucuoi_id == 2) {
                this.diachiCuoi.DIACHI = data2.diachi ?? ''
                this.listbox.loaicap.valuecuoi = data2.loaicap_id ?? ''
              } else if (data2 && data2.daucuoi_id == 1) {
                this.diachiDau.DIACHI = data2.diachi ?? ''
                this.listbox.loaicap.valuedau = data2.loaicap_id ?? ''
              }
              this.thuebao.tsl.txtCuocTKDT_TSL = data.cuoc_tkdt // Cước TC
              this.thuebao.tsl.txtCuocTCDT_TSL = data.cuoc_tcdt // Cước TK
              this.cbo_doituong_id.Valuetsl = data.doituong_id
              this.thuebao.tsl.txtCuocTC_TSL = data.cuoc_tc // Cước TC (No Vat)
              this.thuebao.tsl.txtCuocTK_TSL = data.cuoc_tk // Cước TK (No Vat)
              this.thuebao.tsl.txtCuocTBI_TSL = data.cuoc_tbi // Cước TBị (No Vat)
              this.thuebao.tsl.txtCuocHT_TSL = data.cuoc_ht // Cước HT (No Vat)
              this.txtNgaySD.Valuetsl = data.ngay_sd;// ? moment(data.ngay_sd).format('DD/MM/YYYY') : '' // ngày sử dụng
              this.cboLoaiTien.valuetsl = data.loaitien_id // loại tiền
              this.txtMucThuetsl = data.limit // mức thuế
              this.cboSoNgay.valuetsl = data.status // Số ngày trong kỳ cước
            } else if (this.cbo_dichvu_id.Value == DICHVUVIENTHONG.MEGAWAN || this.cbo_dichvu_id.Value == DICHVUVIENTHONG.METRONET) {
              // Metronet, megaWan
              this.thuebao.mgwan.cuoctc_dt = data.cuoc_tcdt // Cước TC
              this.thuebao.mgwan.cuoctk_dt = data.cuoc_tkdt // Cước TK
              this.cbo_doituong_id.Valuewan = data.doituong_id
              this.thuebao.mgwan.soluongip = data.sl_ip
              this.thuebao.mgwan.cuocip = data.cuoc_ip
              this.thuebao.mgwan.cuoctc = data.cuoc_tc // Cước TC (No Vat)
              this.thuebao.mgwan.cuoctk = data.cuoc_tk // Cước TK (No Vat)
              this.thuebao.mgwan.cuoctbi = data.cuoc_tbi // Cước TBị (No Vat)
              this.thuebao.mgwan.cuocht = data.cuoc_ht // Cước HT (No Vat)
              this.thuebao.mgwan.ma_tb = data.ma_tb //add new
              this.loaisowan = data.loainode_id??0 // add new
              this.listbox.chu_quan.valuewan = data.chuquan_id??0 // add new
              this.listbox.thoihan.valuewan = data.thoihan_id??0 // add new
              this.listbox.loaikenh.value = data.loaikenh_id??0 // add new
              this.listbox.ht_phantach_tocdo.value = data.phantach_td??0 // add new
              this.listbox.loaihinh_tb.value = data.loaitb_id??0 // add new
              this.listbox.congnghe.valuetsl = data.congnghe_id??0 // add new
              this.thuebao.mgwan.ten_tb = data.ten_tb // add new
              this.thuebao.mgwan.diachi_tb = data.diachi_tb // add new
              this.diachiKN.DIACHI = data.diachi_kn // add new
              this.thuebao.mgwan.huongkn = data.huong_kn // add new
              this.listbox.tinh_kn.value = data.tinh_kn_id??0 // add new
              this.listbox.loaikenh.value = data.loaikenh_id??0 // add new
              this.thuebao.mgwan.mahdttkd = data.ma_hd //add new
              this.listbox.tocdokenh.valuepir = data.tocdo_pir_id??0 // add new
              this.listbox.tocdokenh.valuecir = data.tocdo_id??0 //add new
              this.listbox.trangbi.valuewan = data.trangbi_id??0 //add new
              this.listbox.tocdokenh.valuenix = data.tocdo_nix??0 //add new
              this.listbox.tocdokenh.valueisp = data.tocdo_isp??0 //add new
              this.thuebao.mgwan.soluongip = data.sl_ip??0 //add new
              this.listbox.doitacwan.value = data.doitac_id??0 //add new
              this.thuebao.mgwan.diachi_ld = data.diachi_ld??'' //add new
              this.listbox.phanloai_dv.value = data.phanloai_id??0 //add new
              this.txtNgaySD.Valuewan = data.ngay_sd;// ? moment(data.ngay_sd).format('DD/MM/YYYY') : '' // ngày sử dụng
              this.cboLoaiTien.valuewan = data.loaitien_id // loại tiền
              this.txtMucThuewan = data.limit // mức thuế
              this.cboSoNgay.valuewan = data.status // Số ngày trong kỳ cước
            }
            console.log('ngaysd', this.txtNgaySD.Valuewan);
            this.thuebao.crm = data.ma_crm??'' //add new
            this.thuebao.vtn = data.makenh_vtn??'' //add new
            this.thuebao.erp = data.id_erp??'' //add new
            this.listbox.kieu_ld.value = data.kieuld_id //add new
            this.loaitb_id = data.loaitb_id
            if (this.tichhop_didong_dhsx && this.cbo_dichvu_id.Value == DICHVUVIENTHONG.DI_DONG && this.loaitb_id == LOAIHINHTB.DIDONGTRATRUOC) {
              this.$root.toastError('Vui lòng vào menu Quản lý trả trước/Cập nhật trả trước để cập nhật thông tin cho thuê bao trả trước.')
              return
            }
            this.ma_tb = data.ma_tb
            this.matb_dd = data.matb_dd
            let rsKiemTraLapHDKhac = await this.$root.context.post('/web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac', {
              maTb: somay,
              maGd: '0',
              dichVuVtId: this.cbo_dichvu_id.Value,
              loaiHdId: this.LOAIHOPDONG.THAY_TT_KHAC
            });
            if (rsKiemTraLapHDKhac.data) {
              this.$root.toastError(`Số máy/Acc: ${somay} đang được lập bởi hợp đồng (phụ lục) ${rsKiemTraLapHDKhac.data.ten_loaihd} có mã giao dịch ${rsKiemTraLapHDKhac.data.ma_gd} do User ${rsKiemTraLapHDKhac.data.nguoi_cn} thuộc đơn vị ${rsKiemTraLapHDKhac.data.ten_dv} thực hiện vào ngày ${rsKiemTraLapHDKhac.data.ngay_yc}`)
              return
            }
            /*if (data.ky_hd !== 1) {
              if (this.$root.token.getMaTinh() != 'HTH') {
                this.$root.toastError(`Khách hàng có mã ${somay} chưa ký hợp đồng gốc. Đề nghị bạn ký hợp đồng gốc trước khi lập phụ lục !`)
                this.$refs.txt_maTB.focus()
                return
              }
            }*/
            this.trangthaitb_id = data.trangthaitb_id
            this.trangthai_tb = data.trangthai_tb
            if (this.iloai_km == 0 || this.iloai_km == 1 || this.iloai_km == 2) {
              if (this.trangthaitb_id == this.TRANGTHAITB.DOISO || this.trangthaitb_id == this.TRANGTHAITB.THANHLY || this.trangthaitb_id == this.TRANGTHAITB.THANHLY_NO) {
                this.$root.toastError(`Thuê bao có mã: ${somay} đang ở trạng thái: ${this.trangthai_tb} \nBạn không thể thực hiện thay đổi thông tin cho thuê bao này`)
                return
              }
            }
            this.cbo_dichvu_id.Value = data.dichvuvt_id.toString()
            this.listbox.dichvu_vt.value = this.cbo_dichvu_id.Value // add new
            this.email_kh = data.email
            this.fax_kh = data.so_fax
            this.sodt_kh = data.so_dt
            this.gioitinh_Mytv = data.gioitinh
            this.txt_gioitinh.Value = data.gioitinh
            // Thông tin khách hàng
            this.txt_maKH.Value = data.ma_kh
            this.txt_mahopdong.Value = data.ma_hd
            this.dtp_ngaylapHd.Value = new Date(data.ngaylap_hd)
            this.khachhang_id = data.khachhang_id
            this.txt_tenKH.Value = data.ten_kh
            this.txt_diachiKH.Value = data.diachi_kh
            this.txt_telKH.Value = data.so_dt
            this.txt_emailKH.Value = data.email
            this.txt_faxKH.Value = data.fax
            this.txt_mstKH.Value = data.mst
            this.txt_diachiNDD.Value = data.diachi_ndd
            this.txt_nguoiDD.Value = data.nguoi_dd
            this.txt_chucdanhDD.Value = data.chucdanh
            this.cbo_dantoc.Value = data.dantoc_id
            let rsDiaChi = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_diachi_theo_khachhang?khachHangId=${this.khachhang_id}`)
            this.layThongTinDiaChi(rsDiaChi.data[0], 1)
            this.nganhnghe_id = data.nganhnghe_id
            if (this.phantap_kh) {
              let ds_dt_dtn
              await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/phantap_laydoanhthu_dtn/${this.khachhang_id}`)
                .then(rs => {
                  ds_dt_dtn = rs.data
                }).catch(e => {
                  throw e
                })
              if (ds_dt_dtn.length > 0) {
                if (ds_dt_dtn[0].doanhthu)
                  this.pt_doanhthu = ds_dt_dtn[0].doanhthu
                else this.pt_doanhthu = 0
                if (ds_dt_dtn[0].dtn)
                  this.pt_diem_tn = ds_dt_dtn[0].dtn
                else this.pt_diem_tn = 0
              } else {
                this.pt_doanhthu = 0
                this.pt_diem_tn = 0
              }
            }
            if (data.ngayhethan_gt && data.ngayhethan_gt.toString() !== '')
              this.txt_ngayhethan.Value = this.formatDate(data.ngayhethan_gt)
            else this.txt_ngayhethan.Value = ''
            if (data.ngay_sn && data.ngay_sn.toString() !== '')
              this.txt_ngaysn.Value = this.formatDate(data.ngay_sn)
            else this.txt_ngaysn.Value = ''
            if (data.loaigt_id)
              this.cbo_giaytoKH.Value = data.loaigt_id.toString()
            this.txt_soGT.Value = data.so_gt
            if (data.ngaycap)
              this.txt_ngaycap.Value = this.formatDate(data.ngaycap)
            else this.txt_ngaycap.Value = ''
            if (data.nganhnghe_id) {
              this.cbo_nganhngheloc.Value = data.nganhnghe_id
              this.nganhnghe_id = data.nganhnghe_id
            }
            if (data.loaikh_id && data.loaikh_id.toString().trim() !== '') {
              this.loaikh_id = data.loaikh_id
              this.setLoaiKHID(data.loaikh_id)
            } else this.loaikh_id = 1
            // this.loaikh_id = data.loaikh_id ? data.loaikh_id : 1;
            this.txt_noicap.Value = data.noicap
            // hiển thị thông tin id tiểu ngành c1,c2,c3
            if (data.tnc1_id)
              this.TNC1_ID = data.tnc1_id
            if (data.tnc2_id)
              this.TNC2_ID = data.tnc2_id
            if (data.tnc3_id)
              this.TNC3_ID = data.tnc3_id
            if (data.tnc4_id)
              this.TNC4_ID = data.tnc4_id
            // Thông tin thanh toán
            this.txt_maTT.Value = data.ma_tt
            this.thanhtoan_id = data.thanhtoan_id
            let rsMaDaiDien = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_ma_daidien_theo_thanhtoanid/${this.thanhtoan_id}`)
            this.dsMaDaiDien = rsMaDaiDien.data
            this.cbo_maDD.Value = data.matb_dd
            // let cLuottt = this.dsMaDaiDien.map(v => v.ma_tb);
            this.txt_maIG.Value = data.main_ghep
            this.mig_id = data.mig_id ? data.mig_id : 0
            //Hiển thị thông tin mã đại diện
            let rsMaDD = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_thuebao_daidien?matt=${this.txt_maTT.Value}`)
            this.dsMaTBDD = rsMaDD.data
            this.dsTTMaDD = rsMaDD.data
            this.cbo_maTBDD.Value = data.matb_dd
            this.cbo_TTMaDD.Value = data.matb_dd
            let obj = this.dsTTMaDD.find(v => v.ma_tb == this.cbo_TTMaDD.Value)
            this.lbl_trangthaiDD = obj ? obj.trangthai_tb : null
            this.txt_tenTT.Value = data.ten_tt
            this.txt_diachiCT.Value = data.diachi_ct
            this.txt_diachiTT.Value = data.diachi_tt
            this.txt_diachiBC.Value = data.diachi_bc
            rsDiaChi = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_dbttid?dbttid=${this.thanhtoan_id}`)
            this.layThongTinDiaChi(rsDiaChi.data[0], 2)
            this.cbo_HTTT.Value = data.httt_id
            if (data.httt_id) {
              this.httt_id_cu = data.httt_id
            }
            this.cbo_donviQL_id.Value = data.donvi_id
            this.donvitt_id = data.donvi_id
            if (data.nhanvien_id) {
              this.nhanvientc_id = data.nhanvien_id
              this.txt_maNVTC.Value = data.manv_tc
            } else {
              this.nhanvientc_id = 0
              this.txt_maNVTC.Value = ''
            }
            if (data.tuyenthu_id) {
              this.tuyenthu_id = data.tuyenthu_id
              this.txt_maTuyen.Value = data.ma_tuyen
            } else {
              this.tuyenthu_id = 0
              this.txt_maTuyen.Value = ''
            }
            this.cbo_nganhang.Value = data.nganhang_id_tt
            this.txt_mst_TT.Value = data.mst_tt
            this.txt_stk_TT.Value = data.stk_tt
            this.txt_chu_TK.Value = data.chu_tk_tt
            this.txt_soDT_TT.Value = data.so_dt_tt
            this.txt_emailTT.Value = data.email_tt
            this.dv_qhns = data.dv_qhns
            this.machuong = data.machuong
            this.mn_kt = data.mn_kt
            this.ma_ndkt = data.ma_ndkt
            this.mn_nsnn = data.mn_nsnn
            this.tien_dm = data.tien_dm ? data.tien_dm : 0
            this.txt_ghiChuTT.Value = data.ghichu
            await this.HienThi_NVQL_AM_DB_HD(1, this.thanhtoan_id)
            // Thông tin thuê bao
            this.thuebao_id = data.thuebao_id
            this.txt_tenthuebao.Value = data.ten_tb
            this.txt_diachiTB.Value = data.diachi_tb
            this.txt_diachiLD.Value = data.diachi_ld
            this.txt_ngaysnTB.Value = this.formatDate(data.ngay_sn_tb)
            this.txt_ngaysudung.Value = this.formatDate(data.ngay_sd)
            // tab thuê bao
            let dsNV = await this.$root.context.post('/web-hopdong/thanhly/lay_ds_khuvuc_theo_hd_db', {
              hdtb_id: this.thuebao_id,
              vinhd_db: 2
            })
            dsNV = dsNV.data
            this.txt_khuvuc.Value = ''
            this.khuvuc_id = 0
            if (dsNV && dsNV.length > 0) {
              this.txt_khuvuc.Value = dsNV[0].ten_kv
              this.khuvuc_id = dsNV[0].khuvuc_id
            }
            rsDiaChi = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_diachi_theo_thuebao?thueBaoId=${this.thuebao_id}`)
            await this.layThongTinDiaChi(rsDiaChi.data[0], 3)
            this.doituong_id = data.doituong_id
            this.doituong_id_cu = data.doituong_id
            this.cbo_doituong_id.Value = this.doituong_id
            this.chuyenmang = data.chuyenmang_db ? data.chuyenmang_db : 0
            let rsLayChuQuanLDId = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_chuquanld_id_theo_thuebaoid?thuebaoid=${this.thuebao_id}&dichvuvt_id=${this.cbo_dichvu_id.Value}`)
            this.cbo_chuquanLD_id.Value = rsLayChuQuanLDId.data.result
            //    debugger
            if (this.cbo_dichvu_id.Value == DICHVUVIENTHONG.ANTOAN_BAOMAT_TT ||
              this.cbo_dichvu_id.Value == DICHVUVIENTHONG.DICHVU_CNTT ||
              this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TRUNGTAM_DULIEU ||
              this.cbo_dichvu_id.Value == DICHVUVIENTHONG.HOINGHI_TRUYENHINH) {
              await this.LoadThongTin_DV_CNTT(this.loaitb_id)
            }
            await this.nap_ds_kmtt(this.loaitb_id, this.cbo_kieuld_id.Value, -1, 0)
            this.cbo_donviTC_id.Value = data.donviql_id
            this.txt_trangthaiTB.Value = data.trangthai_tb
            await this.hienThiThongTinTTDBMoRong()
            if (data.mucuoctb_id) {
              this.cbo_mucuoc_id.Value = data.mucuoctb_id
              if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_MUCCUOC_TB) {
                this.muccuoc_tb_cu = data.mucuoctb_id
                await this.layDsMucCuocTB()
              } else {
                this.muccuoc_tb_cu = data.mucuoctb_id
                let rsMucCuocV2 = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_muccuoc_v2?muccuoctb_id=${data.mucuoctb_id}`)
                this.dsMucCuocTB = rsMucCuocV2.data
                // this.cbo_mucuoc_id.Value = this.dsMucCuocTB.length ? this.dsMucCuocTB[0].mucuoctb_id : null;
              }
            } else {
              if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_MUCCUOC_TB) {
                this.muccuoc_tb_cu = 0
                await this.layDsMucCuocTB()
              } else {
                this.muccuoc_tb_cu = 0
                this.dsMucCuocTB = []
              }
            }
            this.dvkhaclist = []
            if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THANHTOAN) {
              let rsDsDK = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_sudung_dvk?dbId=${this.thanhtoan_id}&loaiId=2`)
              this.dsDK_DVK = rsDsDK.data
              this.dsDK_BanDau = rsDsDK.data
              this.dsDK_DVK.forEach(item => {
                this.dvkhaclist.push(Text(item.tenDvKhac))
              })
              // this.cbo_mucuoc_id.Value = this.dsMucCuocTB.length ? this.dsMucCuocTB[0].mucuoctb_id : null;
            } else {
              let rsDsDK = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_sudung_dvk?dbId=${this.thuebao_id}&loaiId=1`)
              this.dsDK_DVK = rsDsDK.data
              this.dsDK_BanDau = rsDsDK.data
              this.dsDK_DVK.forEach(item => {
                this.dvkhaclist.push(Text(item.tenDvKhac))
              })
            }
            if (this.loaitb_id == LOAIHINHTB.INTERNET_MYTV) {
              if (data.thuebao_cha_id != '0' && data.thuebao_cha_id && data.loaitb_id == LOAIHINHTB.INTERNET_MYTV) {
                this.thuebao_cha_id = data.thuebao_cha_id
              }
              await this.layDsTKChinh()
            }
            try {
              if (data.phanloaikh_id) {
                this.PHANLOAIKH_ID = data.phanloaikh_id
                let plkh
                if (this.PHANLOAIKH_ID) {
                  await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ma_plkh/${this.PHANLOAIKH_ID}`)
                    .then(res => {
                      if (res.error === '200')
                        if (res.data.length > 0) {
                          if (res.data[0].ma_plkh !== '-1')
                            this.txt_phanloaiKH.Value = res.data[0].ma_plkh
                          if (res.data[0].nhompl !== '-1')
                            this.nhomplkh = res.data[0].nhomplkh
                        }
                    }).catch(e => {
                      throw e
                    })
                }
              }
            } catch (e) {
            }
          } else {
            this.$root.toastError('Không tìm thấy thuê bao. Nhập lại mã thuê bao tìm kiếm !')
          }
        } catch (error) {
          console.log(error)
        }
      },
      hienThiThongTinHopDongTT: async function(value) {
        console.log('hienThiThongTinHopDongTT', value)
        if (value.length > 0) {
          let data = value[0]
          this.hdtt_id = data.hdtt_id
          this.txt_maTT.Value = data.ma_tt
          if (data.thanhtoan_id) {
            this.thanhtoan_id = data.thanhtoan_id
            let rsHttt_id = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_httd_id_theo_thanhtoanid/${this.thanhtoan_id}`)
            let httt_id = rsHttt_id.data.length ? rsHttt_id.data[0].httt_id : null
            if (httt_id) {
              this.httt_id_cu = httt_id
            } else {
              this.httt_id_cu = -1
            }
          } else {
            this.httt_id_cu = -1
          }
          let rsMaDaiDien = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_ma_daidien_theo_thanhtoanid/${this.thanhtoan_id}`)
          this.dsMaDaiDien = rsMaDaiDien.data
          // let cLuottt = this.dsMaDaiDien.map(v => v.ma_tb);
          this.cbo_maDD.Value = data.matb_dd
          this.txt_tenTT.Value = data.ten_tt
          this.txt_diachiCT.Value = data.diachi_ct
          this.txt_diachiTT.Value = data.diachi_tt
          this.txt_diachiBC.Value = data.diachi_bc
          let rsDiaChi = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_hdttid?hdttId=${this.hdtt_id}`)
          this.layThongTinDiaChi(rsDiaChi.data[0], 2)
          this.txt_mst_TT.Value = data.mst
          this.txt_stk_TT.Value = data.stk
          this.txt_chu_TK.Value = data.chu_tk
          this.txt_soDT_TT.Value = data.so_dt
          this.txt_emailTT.Value = data.email
          this.cbo_nganhang.Value = data.nganhang_id
          // let rsMapId = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
          //     id_neo: 'loai_nh',
          //     table: 'nganhang',
          //     dk: 'where nganhang_id = ' + this.cbo_nganhang.Value
          // })
          // let loai_nh = rsMapId.data;
          let rsMapId = await this.$root.context.get('web-hopdong/chuyendich/map_loai_nh_id_neo', {
            nganhang_id: this.cbo_nganhang.Value
          })
          let loai_nh = rsMapId.data && rsMapId.data.length > 0 ? rsMapId.data[0].loai_nh : null
          if (loai_nh == 2) {
            this.isEnableBtnNSNN = true
          } else {
            this.isEnableBtnNSNN = false
          }
          this.cbo_HTTT.Value = data.httt_id
          this.cbo_donviQL_id.Value = data.donvi_id
          if (data.hdmig_id) {
            this.hdmig_id = data.hdmig_id
            // rsMapId = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
            //   id_neo: 'main_ghep',
            //   table: 'hd_mig',
            //   dk: 'where hdmig_id = ' + this.hdmig_id
            // })
            rsMapId = await this.$root.context.get('web-hopdong/thaydoithongtinkhuyenmai/map_main_ghep', {
              hdmig_id: this.hdmig_id
            })
            this.txt_maIG.Value = rsMapId.data
          } else {
            this.hdmig_id = 0
            this.txt_maIG.Value = null
          }
          //Hiển thị thông tin mã đại diện
          let rsMaDD = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_thuebao_daidien?matt=${this.txt_maTT.Value}`)
          this.dsMaTBDD = rsMaDD.data
          this.dsTTMaDD = rsMaDD.data
          this.cbo_maTBDD.Value = data.matb_dd
          this.cbo_TTMaDD.Value = data.matb_dd
          let obj = this.dsTTMaDD.find(v => v.ma_tb == this.cbo_TTMaDD.Value)
          this.lbl_trangthaiDD = obj ? obj.trangthai_tb : null
          this.dv_qhns = data.dv_qhns
          this.machuong = data.machuong
          this.mn_kt = data.mn_kt
          this.ma_ndkt = data.ma_ndkt
          this.mn_nsnn = data.mn_nsnn
          this.tien_dm = data.tien_dm ? data.tien_dm : 0
          if (data.nhanvien_id) {
            this.nhanvientc_id = data.nhanvien_id
            // rsMapId = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
            //   id_neo: 'ma_nv',
            //   table: 'admin.nhanvien',
            //   dk: 'where nhanvien_id = ' + this.nhanvientc_id
            // })
            rsMapId = await this.$root.context.get('web-hopdong/hoanthienhschuyenquyen/map_id_manv_neo', {
              nhanvien_id: this.nhanvientc_id
            })
            if (rsMapId && rsMapId.data && rsMapId.data.length > 0)
              this.txt_maNVTC.Value = rsMapId.data[0].ma_nv
          } else {
            this.nhanvientc_id = 0
            this.txt_maNVTC.Value = ''
          }
          if (data.tuyenthu_id) {
            this.tuyenthu_id = data.tuyenthu_id
            this.txt_maTuyen.Value = data.ma_tuyen
          } else {
            this.tuyenthu_id = 0
            this.txt_maTuyen.Value = null
          }
          this.txt_ghiChuTT.Value = data.ghichu
          await this.HienThi_NVQL_AM_DB_HD(2, this.hdtt_id)
          await this.GanDL_CCBS()
        }
      },
      HienThi_NVQL_AM_DB_HD: async function(kieu, id) {
        try {
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_nhanvien_ql', {
            kieu,
            id
          })
          if (rs && rs.data && rs.data.length > 0) {
            this.txt_nhanVienQL.Value = rs.data[0].ma_nv + ' - ' + rs.data[0].ten_nv
            this.nhanvien_ql_am_id = rs.data[0].nhanvien_id
          } else {
            this.txt_nhanVienQL.Value = null
            this.nhanvien_ql_am_id = 0
          }
        } catch (error) {
          throw error
        }
      },
      Clear_CCBS: function() {
      },
      GanDL_CCBS: async function() {
        try {
          this.Clear_CCBS()
          this.vma_tb = '84' + this.txt_maTB.Value
          this.vten_kh = this.txt_tenKH.Value
          this.vngay_sinh = this.txt_ngaysn.Value
          this.vcmt = this.txt_soGT.Value
          this.vpassport = this.txt_soGT.Value
          this.vloai_gt = this.cbo_giaytoKH.Value
          this.vloai_gt == 3 ? this.vcmt = null : this.vpassport = null
          this.vcustomer_type = ''
          this.vdiachi_kh = this.txt_diachiKH.Value
          this.vcongty = ''
          this.vnghenghiep = this.cbo_nganhngheloc.Value
          this.vgioitinh = this.txt_gioitinh.Value == -1 ? '' : this.txt_gioitinh.Value == 1 ? 'male' : 'female'
          let rs = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_quoctich_theo_qtid/${this.cbo_quoctich.Value}`)
          this.vquoctich = rs.data.result == -1 ? null : rs.data.result
          this.vemail = this.txt_emailKH.Value
          this.vnoicap = this.txt_noicap.Value
          this.vloai_kh = ''
          this.vghichu = this.txt_ghiChuTB.Value
          this.vngay_cap = this.txt_ngaycap.Value
          this.vnam_so_sim = ''
          this.vmsisdn = ''
          this.vsub_type = ''
          this.vdiachi_giaodich = ''
          this.vso_daily = ''
          this.vcontract_type = ''
        } catch (error) {
          throw error
        }
      },
      hienThiThongTinHopDongTB: async function(items) {
        try {
          this.thuebao_cha_id = 0
          if (items.length > 0) {
            let data = items[0]
            this.cbo_chuquan_id.Value = data.chuquan_id
            this.cbo_chuquanLD_id.Value = data.chuquanld_id
            this.loaitb_id = data.loaitb_id
            if (this.tichhop_didong_dhsx &&
              this.cbo_dichvu_id.Value == DICHVUVIENTHONG.DI_DONG &&
              this.loaitb_id == LOAIHINHTB.DIDONGTRATRUOC) {
              this.$root.toastError('Vui lòng vào menu Quản lý trả trước/Cập nhật trả trước để cập nhật thông tin cho thuê bao trả trước.')
              this.Clear()
              return
            }
            this.hdtb_id = data.hdtb_id
            if (data.thuebao_id) this.thuebao_id = data.thuebao_id
            let dsNV = await this.$root.context.post('/web-hopdong/thanhly/lay_ds_khuvuc_theo_hd_db', {
              hdtb_id: this.hdtb_id,
              vinhd_db: 1
            })
            dsNV = dsNV.data
            if (dsNV.length <= 0) {
              dsNV = await this.$root.context.post('/web-hopdong/thanhly/lay_ds_khuvuc_theo_hd_db', {
                hdtb_id: this.thuebao_id,
                vinhd_db: 2
              })
              dsNV = dsNV.data
            }
            this.txt_khuvuc.Value = ''
            if (dsNV && dsNV.length > 0) {
              this.txt_khuvuc.Value = dsNV[0].ten_kv
              this.khuvuc_id = dsNV[0].khuvuc_id
            }
            this.txt_maTB.Value = data.ma_tb
            this.txt_tenthuebao.Value = data.ten_tb
            this.txt_diachiTB.Value = data.diachi_tb
            this.txt_diachiLD.Value = data.diachi_ld
            this.txt_ghiChuTB.Value = data.ghichu
            let rsDiaChi = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_diachi_theo_hdtb?hdtbId=${this.hdtb_id}`)
            if (rsDiaChi.data.length > 0) this.layThongTinDiaChi(rsDiaChi.data[0], 3)
            this.dichvuvt_id = data.dichvuvt_id
            this.quytrinh_id = data.quytrinh_id
            this.cbo_dichvu_id.Value = this.dichvuvt_id.toString()
            this.doituong_id = data.doituong_id
            this.cbo_kieuld_id.Value = data.kieuld_id.toString()
            //
            this.cbo_doituong_id.Value = data.doituong_id
            this.txt_ngaysnTB.Value = this.formatDate(data.ngay_sn)
            if (this.cbo_dichvu_id.Value == DICHVUVIENTHONG.ANTOAN_BAOMAT_TT ||
              this.cbo_dichvu_id.Value == DICHVUVIENTHONG.DICHVU_CNTT ||
              this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TRUNGTAM_DULIEU ||
              this.cbo_dichvu_id.Value == DICHVUVIENTHONG.HOINGHI_TRUYENHINH) {
              await this.LoadThongTin_DV_CNTT(this.loaitb_id)
            }
            await this.nap_ds_kmtt(this.loaitb_id, data.kieuld_id, this.hdtb_id, 0)
            await this.layDsMucCuocTB()
            if (data.mucuoctb_id) {
              if (this.dsMucCuocTB.length > 0) {
                this.cbo_mucuoc_id.Value = data.mucuoctb_id
              } else {
                let rsMucCuocV2 = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_muccuoc_v2?muccuoctb_id=${data.mucuoctb_id}`)
                this.dsMucCuocTB = rsMucCuocV2.data
                // this.cbo_mucuoc_id.Value = this.dsMucCuocTB.length ? this.dsMucCuocTB[0].mucuoctb_id : null;
              }
            }
            this.cbo_donviTC_id.Value = data.donvi_id
            if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THANHTOAN) {
              let rsDsDK = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_dangky_dvk', {
                hd_id: this.hdtt_id,
                loaiid: 2
              })
              this.dsDK_DVK = rsDsDK.data
            } else {
              let rsDsDK = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_dangky_dvk', {
                hd_id: this.hdtb_id,
                loaiid: 1
              })
              this.dsDK_DVK = rsDsDK.data
            }
            await this.hienThiDichVuKhac()
            // let rsMapId = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
            //   id_neo: 'trangthaitb_id',
            //   table: 'db_thuebao',
            //   dk: 'where thuebao_id = ' + this.thuebao_id
            // })
            let rsMapId = await this.$root.context.get('web-hopdong/thaydoithongtinkhuyenmai/map_trangthaitb_id', {
              thuebao_id: this.thuebao_id
            })
            let tttb_id = rsMapId.data
            if (tttb_id != -1) {
              // rsMapId = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
              //   id_neo: 'trangthai_tb',
              //   table: 'trangthai_tb',
              //   dk: 'where trangthaitb_id =' + tttb_id
              // })
              let rsMapId = await this.$root.context.get('web-hopdong/thaydoithongtinkhuyenmai/map_trangthai_tb', {
                trangthaitb_id: tttb_id
              })
              this.txt_trangthaiTB.Value = rsMapId.data
            } else {
              this.txt_trangthaiTB.Value = null
            }
            // rsMapId = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
            //   id_neo: "to_char(ngay_sd,'dd/MM/yyyy')",
            //   table: 'db_thuebao',
            //   dk: 'where thuebao_id = ' + this.thuebao_id
            // })
            rsMapId = await this.$root.context.get('web-hopdong/thaydoithongtinkhuyenmai/map_ngaysd_dbthuebao', {
              thuebao_id: this.thuebao_id
            })
            let ngay_sd = rsMapId.data
            if (ngay_sd != -1) {
              this.txt_ngaysudung.Value = ngay_sd
            } else {
              this.txt_ngaysudung.Value = null
            }
            if (this.dichvuvt_id == DICHVUVIENTHONG.TRUNGTAM_DULIEU || this.dichvuvt_id == DICHVUVIENTHONG.ANTOAN_BAOMAT_TT
              || this.dichvuvt_id == DICHVUVIENTHONG.DICHVU_CNTT || this.dichvuvt_id == DICHVUVIENTHONG.HOINGHI_TRUYENHINH) {
              await this.hienThiThongTinMoRongHopDong()
            } else {
              await this.hienThiThongTinTTDBMoRong()
            }
            if (this.ts_sua_ngay_ht_dv_cntt == 1 && (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_DV_CNTT || this.cbo_kieuld_id.Value == KIEULAPDAT.GIAHAN_DV_CNTT)) {
              this.dtp_ngayHT.Enable = true
            }
            if (this.loaitb_id == LOAIHINHTB.INTERNET_MYTV) {
              if (data.thuebao_cha_id && data.thuebao_cha_id != '0' && data.loaitb_id) {
                this.thuebao_cha_id = data.thuebao_cha_id
              }
              await this.layDsTKChinh()
            }
          }
        } catch (e) {
          console.log(e)
        }
      },
      hienThiThongTinMoRongHopDong: async function() {
        if (DICHVUVIENTHONG.DICHVU_CNTT || DICHVUVIENTHONG.ANTOAN_BAOMAT_TT || DICHVUVIENTHONG.TRUNGTAM_DULIEU || DICHVUVIENTHONG.HOINGHI_TRUYENHINH) {
          let rs = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_hopdong_thuebao_cntt?hdtbId=${this.hdtb_id}`)
          this.dsHdTbCntt = rs.data
          if (this.dsHdTbCntt && this.dsHdTbCntt.length > 0) {
            let obj = rs.data[0]
            this.ItemList.forEach((item, i) => {
              let color = i % 2
              let keyField = get(obj, item.FIELD_NAME.toLowerCase(), null)
              if (keyField !== null) {
                item.FIELD_VALUE = keyField
              }
              if (item.FIELD_NAME === 'MA_TB') {
                item.FIELD_VALUE = this.dsHDTB[0].ma_tb
                let data = {
                  TEN_HIENTHI: item.TEN_HIENTHI,
                  FIELD_VALUE: this.dsHDTB[0].ma_tb
                }
                this.items.push(TextReadOnly(data, color))
              } else if (item.FIELD_NAME === 'MATB_TN') {
                let data = {
                  TEN_HIENTHI: item.TEN_HIENTHI,
                  FIELD_VALUE: this.matb_dd
                }
                this.items.push(TextReadOnly(data, color))
              } else if (item.ATT === 4) { //combobox dropdown
                if (item.FIELD_NAME == 'PHANLOAI_ID' || item.FIELD_NAME == 'TOCDO_ID') {
                  let enable = false
                  if (this.thaydoi_td_cntt == 0) {
                    enable = false
                  }
                  if (this.checkEnableCombobox(item)) {
                    enable = true
                  }
                  console.log('cho phép hay không ', enable)
                  this.items.push(ComboboxComponent(item, this.loaitb_id, true, color))
                } else {
                  this.items.push(ComboboxComponent(item, this.loaitb_id, true, color))
                }
              } else if (item.ATT === 2) { // datetime
                // if(item.FIELD_VALUE){
                //   item.FIELD_VALUE = moment(new Date(String(item.FIELD_VALUE))).format('DD/MM/YYYY')
                // }
                this.items.push(DateTimeComponent(item, color))
              } else if (item.ATT === 5) {// button edit
                this.items.push(ButtonEdit(item, color))
              } else if (item.ATT === 0) {
                this.items.push(TextComponent(item, color))
              } else if (item.ATT === 9) {
                if (item.FIELD_VALUE) {
                  item.FIELD_VALUE = moment(new Date(String(item.FIELD_VALUE))).format('DD/MM/YYYY hh:mm:ss')
                }
                this.items.push(DateTimeHourComponent(item, color))
              } else if (item.ATT === 7) {
                this.items.push(CheckBox(item, color))
              } else if (item.ATT === 1) {
                this.items.push(NumberText(item, color))
              } else if (item.ATT === 10) {
                this.items.push(ComboCheckBox(item, this.loaitb_id, true, color))
              } else {
                this.items.push(TextComponent(item, color))
              }
              //  })
            })
            // console.log(get(rs, item.FIELD_NAME, null))
          }
        }
      },
      checkEnableCombobox(item) {
        //ok thì mở ra được sửa ok = true
        if ([132, 136, 38, 127, 120, 39, 56].includes(parseInt(this.loaitb_id)) /*ivan*/) {
          if (item.FIELD_NAME == 'PHANLOAI_ID' || item.FIELD_NAME == 'TOCDO_ID') {
            return true
          }
        }
        if (parseInt(this.loaitb_id) == 160 || parseInt(this.loaitb_id) == 35) {
          if (item.FIELD_NAME == 'PHANLOAI_ID') {
            return true
          }
        }
        return false
      },
      hienThiThongTinTTDBMoRong: async function() {
        this.tocdo_id = 0
        this.muccuoc_id = 0
        this.trangbi_id = 0
        let ds = []
        switch (parseInt(this.dichvuvt_id)) {
          case DICHVUVIENTHONG.ADSL: {
            let rs = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_danhba_adsl?thueBaoId=${this.thuebao_id}`)
            ds = rs.data
            break
          }
          case DICHVUVIENTHONG.DICHVU_CNTT:
          case DICHVUVIENTHONG.ANTOAN_BAOMAT_TT:
          case DICHVUVIENTHONG.TRUNGTAM_DULIEU:
          case DICHVUVIENTHONG.HOINGHI_TRUYENHINH:
          case 50: {
            let rs = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_danhba_cntt?thueBaoId=${this.thuebao_id}`)
            ds = rs.data
            // Object.keys(key).forEach(key1 => key[key1.toUpperCase()] = key[key1])
            if (ds) {
              let obj = rs.data[0]
              this.ItemList.forEach((item, i) => {
                let color = i % 2
                let keyField = get(obj, item.FIELD_NAME.toLowerCase(), null)
                if (keyField !== null) {
                  item.FIELD_VALUE = keyField
                }
                if (item.FIELD_NAME === 'MA_TB') {
                  let data = {
                    TEN_HIENTHI: item.TEN_HIENTHI,
                    FIELD_VALUE: this.ma_tb
                  }
                  this.items.push(TextReadOnly(data, color))
                } else if (item.FIELD_NAME === 'MATB_TN') {
                  let data = {
                    TEN_HIENTHI: item.TEN_HIENTHI,
                    FIELD_VALUE: this.matb_dd
                  }
                  this.items.push(TextReadOnly(data, color))
                } else if (item.ATT === 4) { //combobox dropdown
                  if (item.FIELD_NAME == 'PHANLOAI_ID' || item.FIELD_NAME == 'TOCDO_ID') {
                    let enable = false
                    if (this.thaydoi_td_cntt == 0) {
                      enable = false
                    }
                    if (this.checkEnableCombobox(item)) {
                      enable = true
                    }
                    this.items.push(ComboboxComponent(item, this.loaitb_id, true, color))
                  } else {
                    this.items.push(ComboboxComponent(item, this.loaitb_id, true, color))
                  }
                } else if (item.ATT === 2) { // datetime
                  this.items.push(DateTimeComponent(item, color))
                } else if (item.ATT === 5) {// button edit
                  this.items.push(ButtonEdit(item, color))
                } else if (item.ATT === 0) {
                  this.items.push(TextComponent(item, color))
                } else if (item.ATT === 9) {
                  this.items.push(DateTimeHourComponent(item, color))
                } else if (item.ATT === 7) {
                  this.items.push(CheckBox(item, color))
                } else if (item.ATT === 1) {
                  this.items.push(NumberText(item, color))
                } else if (item.ATT === 10) {
                  this.items.push(ComboCheckBox(item, this.loaitb_id, true, color))
                } else {
                  this.items.push(TextComponent(item, color))
                }
                //  })
              })
            }
            break
          }
          case DICHVUVIENTHONG.CO_DINH: {
            let rs = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_danhba_codinh?thueBaoId=${this.thuebao_id}`)
            ds = rs.data
            break
          }
          case DICHVUVIENTHONG.MEGAWAN: {
            let rs = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_danhba_megawan?thueBaoId=${this.thuebao_id}`)
            ds = rs.data
            break
          }
          case DICHVUVIENTHONG.TSL: {
            let rs = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_danhba_tsl?thueBaoId=${this.thuebao_id}`)
            ds = rs.data
            break
          }
          case DICHVUVIENTHONG.GPHONE: {
            let rs = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_danhba_gp?thueBaoId=${this.thuebao_id}`)
            ds = rs.data
            break
          }
          case DICHVUVIENTHONG.IMS: {
            let rs = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_danhba_ims?thueBaoId=${this.thuebao_id}`)
            ds = rs.data
            break
          }
          case DICHVUVIENTHONG.MEGA_EYES:
            let rs = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_danhba_megaeys?thueBaoId=${this.thuebao_id}`)
            ds = rs.data
            break
        }
        switch (parseInt(this.dichvuvt_id)) {
          case DICHVUVIENTHONG.ADSL:
            if (ds.length > 0) {
              this.tocdo_id = ds[0].tocdo_id ? ds[0].tocdo_id : 0
              if (this.loaitb_id == LOAIHINHTB.INTERNET_ADSL || this.loaitb_id == LOAIHINHTB.INTERNET_FTTH || this.loaitb_id == LOAIHINHTB.WIFI_FIBER
                || this.loaitb_id == LOAIHINHTB.INTERNET_1260 || this.loaitb_id == LOAIHINHTB.MAIL_SMD || this.loaitb_id == LOAIHINHTB.HTVC_FIBERVNN_ANALOG
                || this.loaitb_id == LOAIHINHTB.HTVC_FIBERVNN_DIGITAL) {
                this.muccuoc_id = ds[0].muccuoc_id ? ds[0].muccuoc_id : 0
              }
              this.trangbi_id = ds[0].trangbi_id ? ds[0].trangbi_id : 0
            }
            break
          case DICHVUVIENTHONG.DICHVU_CNTT:
          case DICHVUVIENTHONG.ANTOAN_BAOMAT_TT:
          case DICHVUVIENTHONG.TRUNGTAM_DULIEU:
          case DICHVUVIENTHONG.HOINGHI_TRUYENHINH:
            if (ds.length > 0) {
              this.tocdo_id = ds[0].tocdo_id ? ds[0].tocdo_id : 0
              this.muccuoc_id = ds[0].muccuoc_id ? ds[0].muccuoc_id : 0
            }
            break
          case DICHVUVIENTHONG.MEGA_EYES:
            if (ds.length > 0) {
              this.tocdo_id = ds[0].tocdo_id ? ds[0].tocdo_id : 0
              this.trangbi_id = ds[0].trangbi_id ? ds[0].trangbi_id : 0
            }
            break
          case DICHVUVIENTHONG.CO_DINH:
            if (ds.length > 0) {
              this.trangbi_id = ds[0].trangbi_id ? ds[0].trangbi_id : 0
            }
            break
          case DICHVUVIENTHONG.MEGAWAN:
            if (ds.length > 0) {
              this.tocdo_id = ds[0].tocdo_id ? ds[0].tocdo_id : 0
              this.trangbi_id = ds[0].trangbi_id ? ds[0].trangbi_id : 0
            }
            break
          case DICHVUVIENTHONG.TSL:
            if (ds.length > 0) {
              this.tocdo_id = ds[0].tocdo_id ? ds[0].tocdo_id : 0
              this.trangbi_id = ds[0].trangbi_id ? ds[0].trangbi_id : 0
            }
            break
          case DICHVUVIENTHONG.IMS:
            if (ds.length > 0) {
              this.trangbi_id = ds[0].trangbi_id ? ds[0].trangbi_id : 0
            }
            break
          case DICHVUVIENTHONG.GPHONE:
            if (ds.length > 0) {
              this.trangbi_id = ds[0].trangbi_id ? ds[0].trangbi_id : 0
            }
            break
          case 50:
            this.tocdo_id = 0
            if (ds.length > 0) {
              this.linhvuc_id = ds[0].linhvuc_id ? ds[0].linhvuc_id : 0
            } else {
              this.linhvuc_id = 0
            }
            break
        }
      },
      hienThiDichVuKhac: async function() {
        try {
          this.dsDVKhac = []
          this.dvkhaclist = []
          if (this.dsDK_DVK.length > 0) {
            this.dsDK_DVK.forEach(item => {
              if (item.kieu_yc === 1) {
                this.dvkhaclist.push(Text(item.ten_dvkhac + ' (ĐK) '))
              } else {
                this.dvkhaclist.push(Text(item.ten_dvkhac + ' (Hủy) '))
              }
            })
          }
        } catch (error) {
        }
      },
      layThongTinDiaChi: function(data, kieu) {
        try {
          if (data) {
            if (kieu === 1) {
              this.diachikh_id = data.diachi_id
              this.tinhkh_id = data.tinh_id
              this.quankh_id = data.quan_id
              this.phuongkh_id = data.phuong_id ? data.phuong_id : 0
              this.dacdiemkh_id = data.dacdiem_id ? data.dacdiem_id : 0
              this.phokh_id = data.pho_id ? data.pho_id : 0
              this.apkh_id = data.ap_id ? data.ap_id : 0
              this.khukh_id = data.khu_id ? data.khu_id : 0
              this.sonhakh = data.sonha
            } else if (kieu === 2) {
              this.diachitt_id = data.diachi_id
              this.tinhtt_id = data.tinh_id
              this.quantt_id = data.quan_id
              this.phuongtt_id = data.phuong_id ? data.phuong_id : 0
              this.dacdiemtt_id = data.dacdiem_id ? data.dacdiem_id : 0
              this.phott_id = data.pho_id ? data.pho_id : 0
              this.aptt_id = data.ap_id ? data.ap_id : 0
              this.khutt_id = data.khu_id ? data.khu_id : 0
              this.sonhatt = data.sonha
              //Địa chỉ báo cước
              this.diachibc_id = data.diachibc_id ? data.diachibc_id : 0
              this.tinhbc_id = data.tinhbc_id ? data.tinhbc_id : 0
              this.quanbc_id = data.quanbc_id ? data.quanbc_id : 0
              this.phuongbc_id = data.phuongbc_id ? data.phuongbc_id : 0
              this.dacdiembc_id = data.diachibc_id ? data.diachibc_id : 0
              this.phobc_id = data.phobc_id ? data.phobc_id : 0
              this.apbc_id = data.apbc_id ? data.apbc_id : 0
              this.khubc_id = data.khubc_id ? data.khubc_id : 0
              this.sonhabc = data.sonha_bc
            } else if (kieu === 3) {
              this.diachitb_id = data.diachi_id
              this.tinhtb_id = data.tinh_id
              this.quantb_id = data.quan_id
              this.phuongtb_id = data.phuong_id ? data.phuong_id : 0
              this.dacdiemtb_id = data.dacdiem_id ? data.dacdiem_id : 0
              this.photb_id = data.pho_id ? data.pho_id : 0
              this.aptb_id = data.ap_id ? data.ap_id : 0
              this.khutb_id = data.khu_id ? data.khu_id : 0
              this.sonhatb = data.sonha
              this.block = data.block ? data.block : ''
              this.tang = data.tang ? data.tang : ''
              this.phong = data.phong ? data.phong : ''
              this.mota = data.mota ? data.mota : ''
              //Địa chỉ lắp đặt
              this.diachild_id = data.diachild_id
              this.tinhld_id = data.tinhld_id
              this.quanld_id = data.quanld_id
              this.phuongld_id = data.phuongld_id ? data.phuongld_id : 0
              this.dacdiemld_id = data.dacdiemld_id ? data.dacdiemld_id : 0
              this.phold_id = data.phold_id ? data.phold_id : 0
              this.apld_id = data.apld_id ? data.apld_id : 0
              this.khuld_id = data.khuld_id ? data.khuld_id : 0
              this.sonhald = data.sonha_ld
              this.block_ld = data.block_ld ? data.block_ld : ''
              this.tang_ld = data.tang_ld ? data.tang_ld : ''
              this.sophong_ld = data.phong_ld ? data.phong_ld : ''
              this.motathem_ld = data.mota_ld ? data.mota_ld : ''
            }
          }
        } catch (error) {
        }
      },
      formatDate: function(data) {
        if (data) {
          // return data.substr(0, 10).split('-').reverse().join('/');
          return DateTimeLib.toDateDisplay(data)
        }
        return null
      },
      chapNhan: async function(data) {
        this.dsKM_HDTB = data
        this.dsCTKMThayDoi = []
        if (this.dsKM_HDTB.length == 0) {
          return
        }
        let obj = {}
        this.dtCTKMHD = []
        for (const element of this.dsKM_HDTB) {
          obj = {
            ...element
          }
          obj.ten_yc = 'Thêm mới'
          let rsTen = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ten_ctkm_theo_id/${element.chitietkm_id}`)
          obj.ten_ctkm = rsTen.data.length ? rsTen.data[0].ten_ctkm : null
          this.dtCTKMHD.push(obj)
        }
        this.dsCTKMThayDoi = this.dtCTKMHD
        console.log(this.dsCTKMThayDoi)
      },
      timKiem: async function() {
        //this.$refs.txt_maTB.blur()
        //console.log(21323232323)
        //console.log(this.txt_maTB.Value)
        if (this.txt_maTB.Value) {
          this.tsbtnCapNhat.Enable = false;
          //this.$refs.txt_maTB.blur()
          try {
            this.loading(true)
            this.isEnterTimKiem = true
            this.hdtb_id = 0
            this.dsHDTB = []
            this.dsKM_HDTB = []
            this.grcDanhSach = []
            let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_hopdong_theo_matb', {
              maTb: this.txt_maTB.Value,
              loaiHdId: this.LOAIHOPDONG.THAY_TT_KHAC,
              ttHdId: 1, // Mới
              donViDlId: 0,
              dichVuVtId: this.cbo_dichvu_id.Value,
              loaiTbId: 0
            });
            if (rs.data.length > 0) {
              this.ht = 2
              await this.hienThiTTHopDongKH(rs.data)
            } else {
              console.log(2)
              this.ht = 1
              await this.hienThiTTDanhBa(this.txt_maTB.Value)
            }
          } catch (error) {
          } finally {
            this.loading(false)
            this.isEnterTimKiem = false
          }
        }
      },
      async checkForm() {
        let check = true
        try {
          // #region Thay đổi thông tin thuê bao
          if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THUEBAO) {
            if (await this.Kiemtra_hopdong_thaydoi_dt((this.txt_maTB.Value + '').trim(), this.dichvuvt_id, this.LOAIHOPDONG.THAY_TT_KHAC) != 0) {
              this.$root.toastError('Thuê bao này đang được lập phụ lục khác có thay đổi đối tượng chưa hoàn thành.\nBạn không thể tiếp tục thêm phụ lục mới!')
              return false
            }
            if (!this.txt_tenthuebao.Value) {
              this.$root.toastError('Hãy nhập tên thuê bao !')
              this.$refs.txt_tenthuebao.focus()
              return false
            }
            if ((!this.txt_diachiTB.Value || this.quantb_id == 0 || this.phuongtb_id == 0)
              && this.tinhtb_id == this.$auth.getPhanVungID()) { //Không kiểm tra địa chỉ tb khác tỉnh.
              this.$root.toastError('Hãy nhập địa chỉ thuê bao !')
              this.$refs.txt_diachiTB.focus()
              return false
            }
            if (this.cbo_dichvu_id.Value == DICHVUVIENTHONG.CO_DINH ||
              this.cbo_dichvu_id.Value == DICHVUVIENTHONG.GPHONE ||
              this.cbo_dichvu_id.Value == DICHVUVIENTHONG.ADSL ||
              this.cbo_dichvu_id.Value == DICHVUVIENTHONG.IMS) {
              if (/*this.dsMucCuocTB.length == 0 || */this.cbo_mucuoc_id.Value == null) {
                this.$root.toastError('Bạn hãy nhập mức cước thuê bao !')
                return false
              }
            }
            if (this.txt_ngaysnTB.Value && !moment(this.txt_ngaysnTB.Value, 'DD/MM/YYYY', true).isValid()) {
              this.$root.toastError('Ngày cấp giấy tờ không đúng định dạng ngày tháng dd/mm/yyyy!')
              this.$refs.txt_ngaysnTB.focus()
              return false
            }
            try {
              if (this.doituong_id_cu != this.cbo_doituong_id.Value) {
                // bổ sung IT360-866331
                if (this.cbo_mucuoc_id.Value != null) {
                  let obj = this.dsMucCuocTB.find(x => x.mucuoctb_id == this.cbo_mucuoc_id.Value)
                  if (!obj) {
                    this.$root.toastError('Đối tượng mới chưa được gán với mức cước cũ, liên hệ admin để gán đối tượng mới vào mức cước cũ hoặc phải chọn lại mức cước mới')
                    return false
                  }
                }
                if (this.doituong_id_cu == this.DUNG_THU.DOITUONG_TB) {
                  this.$root.toastError('Thuê bao đang có đối tượng "Thuê bao dùng thử". Không thể thay đổi!')
                  return false
                }
                if (this.cbo_doituong_id.Value == this.DUNG_THU.DOITUONG_TB) {
                  this.$root.toastError('Không thể thay đổi đối tượng thuê bao thành "Thuê bao dùng thử"!')
                  return false
                }
                let kt = await this.Kiemtra_goidadv_doituong(this.thuebao_id, this.cbo_doituong_id.Value)
                if (kt != '1') {
                  this.$root.toastError(kt)
                  return false
                }
              }
            } catch (e) {
              console.log(e)
            }
          }
          // #region Thay đổi thông tin khách hàng
          if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_KHACHHANG) {
            if (this.txt_tenKH.Value == '') {
              this.$root.toastError('Hãy nhập tên khách hàng !')
              this.$refs.txt_tenKH.focus()
              return false
            }
            if (this.txt_tenKH.Value && this.txt_tenKH.Value.length > 300) {
              this.$root.toastError('Trường Tên KH dữ liệu vượt quá độ dài cho phép.')
              this.$refs.txt_tenKH.focus()
              return false
            }
            if (this.txt_diachiKH.Value == '' || this.quankh_id == 0 || this.phuongkh_id == 0) {
              this.$root.toastError('Hãy nhập địa chỉ khách hàng !')
              this.$refs.txt_diachiKH.focus()
              return false
            }
            if (this.txt_diachiKH.Value && this.txt_diachiKH.Value.length > 500) {
              this.$root.toastError('Trường Địa chỉ KH dữ liệu vượt quá độ dài cho phép.')
              this.$refs.txt_diachiKH.focus()
              return false
            }
            if (this.txt_nguoiDD.Value && this.txt_nguoiDD.Value.length > 300) {
              this.$root.toastError('Trường Người đại diện dữ liệu vượt quá độ dài cho phép.')
              this.$refs.txt_nguoiDD.focus()
              return false
            }
            if (this.txt_chucdanhDD.Value && this.txt_chucdanhDD.Value.length > 80) {
              this.$root.toastError('Trường Chức vụ dữ liệu vượt quá độ dài cho phép.')
              this.$refs.txt_chucdanhDD.focus()
              return false
            }
            if (this.txt_diachiNDD.Value && this.txt_diachiNDD.Value.length > 300) {
              this.$root.toastError('Trường Địa chỉ NĐD dữ liệu vượt quá độ dài cho phép.')
              this.$refs.txt_diachiNDD.focus()
              return false
            }
            if (this.txt_faxKH.Value && this.txt_faxKH.Value.length > 20) {
              this.$root.toastError('Trường Fax dữ liệu vượt quá độ dài cho phép.')
              this.$refs.txt_faxKH.focus()
              return false
            }
            if (this.txt_emailKH.Value && this.txt_emailKH.Value.length > 50) {
              this.$root.toastError('Trường Email dữ liệu vượt quá độ dài cho phép.')
              this.$refs.txt_emailKH.focus()
              return false
            }
            if (this.txt_soGT.Value && this.txt_soGT.Value.length > 80) {
              this.$root.toastError('Trường Số giấy tờ dữ liệu vượt quá độ dài cho phép.')
              this.$refs.txt_soGT.focus()
              return false
            }
            if (this.txt_noicap.Value && this.txt_noicap.Value.length > 80) {
              this.$root.toastError('Trường Nơi cấp dữ liệu vượt quá độ dài cho phép.')
              this.$refs.txt_noicap.focus()
              return false
            }
            if (this.txt_maKH.Value == '' && this.khachhang_id != 0) {
              this.$root.toastError('Kiểm tra lại mã khách hàng !')
              this.$refs.txt_maKH.focus()
              return false
            }
            if (this.txt_maKH.Value != null && this.txt_maKH.Value.trim() != '') {
              let rs = await this.$root.context.post('/web-hopdong/hopdong/kiemtra_makh_matt_magd', {
                maKh: this.txt_maKH.Value,
                kieu: 1,
                loaiHdId: this.LOAIHOPDONG.THAY_TT_KHAC
              })
              if (rs.data == 0) {
                this.$root.toastError('Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
                this.$refs.txt_maKH.focus()
                return false
              }
            }
            if (this.txt_soGT.Value != null && this.txt_soGT.Value.trim() == '') {
              this.$root.toastError('Bạn chưa nhập số giấy tờ!')
              this.$refs.txt_soGT.focus()
              return false
            }
            if (!this.KiemTra_SDT(this.txt_telKH.Value, 'LIENHE'))
              return false
            if (!this.txt_ngaycap.Value) {
              this.$root.toastError('Bạn chưa nhập ngày cấp giấy tờ!')
              this.$refs.txt_ngaycap.focus()
              return false
            } else {
              if (!moment(this.txt_ngaycap.Value, 'DD/MM/YYYY', true).isValid()) {
                this.$root.toastError('Ngày cấp giấy tờ không đúng định dạng ngày tháng dd/mm/yyyy!')
                this.$refs.txt_ngaycap.focus()
                return false
              }
            }
            if (this.txt_ngayhethan.Value && !moment(this.txt_ngayhethan.Value, 'DD/MM/YYYY', true).isValid()) {
              this.$root.toastError('Ngày hết hạn không đúng định dạng ngày tháng dd/mm/yyyy!')
              this.$refs.txt_ngayhethan.focus()
              return false
            }
            if (this.txtNgaySN_NDD.Value && !moment(this.txtNgaySN_NDD.Value, 'DD/MM/YYYY', true).isValid()) {
              this.$root.toastError('Ngày sinh người đại diện không đúng định dạng ngày tháng dd/mm/yyyy!')
              this.$refs.txtNgaySN_NDD.focus()
              return false
            }
            if (!this.txt_ngaysn.Value) {
              this.$root.toastError('Bạn chưa nhập ngày sinh khách hàng!')
              this.$refs.txt_ngaysn.focus()
              return false
            } else {
              if (!moment(this.txt_ngaysn.Value, 'DD/MM/YYYY', true).isValid()) {
                this.$root.toastError('Ngày sinh khách hàng không đúng định dạng ngày tháng dd/mm/yyyy!')
                this.$refs.txt_ngaysn.focus()
                return false
              }
            }
            if (this.chkTKChinh.Value == true && this.cbo_tkchinh_id.Value != this.thuebao_cha_id && this.cbo_tkchinh_id.Value != this.tocdo_id) {
              this.$root.toastError('Tốc độ của mytv phụ khác mytv chính!')
              return false
            }
            if (this.dsLoaiKH.length == 0) {
              this.$root.toastError('Không có thông tin loại khách hàng')
              return false
            }
            let maSoThue = this.txt_mstKH.Value ? this.txt_mstKH.Value.trim() : ''
            let vkieukh_id_ = -1, loaiKH = this.dsLoaiKH.filter(item => item.loaikh_id === this.cbo_loaiKH.Value)[0]
            let kieukh = loaiKH ? loaiKH.loai.toString() : ''
            if (kieukh !== '') vkieukh_id_ = parseInt(kieukh, 10)
            if (this.ts_kt_mstmns_kh == 1) {
              if (maSoThue !== '') {
                let kq_kt = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_mst', { mst: maSoThue })
                if (kq_kt.data && kq_kt.data.result !== 'OK') {
                  this.$root.toastError(kq_kt.data.result)
                  return false
                }
              }
              if (vkieukh_id_ == this.KIEU_KH.DOANH_NGHIEP) {
                if (!this.txt_mstKH.Value && (this.dsHDKH_Sub.length == 0 || this.dsHDKH_Sub.length > 0 && !this.dsHDKH_Sub[0]['MA_NS'])) {
                  this.$root.toastError('Bạn phải nhập mã số thuế hoặc mã ngân sách')
                  return false
                }
                if (this.dsHDKH_Sub.length > 0 && this.dsHDKH_Sub[0]['MA_NS']) {
                  let mans = ''
                  mans = this.dsHDKH_Sub[0]['MA_NS'] + ''
                  if (!this.IsNumber(mans)) {
                    this.$root.toastError('Mã ngân sách phải là dạng số.\nBạn hãy kiểm tra lại!')
                    return false
                  }
                  if (mans.length != 7) {
                    this.$root.toastError('Mã ngân sách phải đủ 7 ký tự số. \nBạn hãy kiểm tra lại!')
                    return false
                  }
                  let ArrCheck = [1, 2, 3, 7, 8, 9]
                  if (mans.substr(0, 1) != '1' && mans.substr(0, 1) != '2' && mans.substr(0, 1) != '3' && mans.substr(0, 1) != '7' && mans.substr(0, 1) != '8' && mans.substr(0, 1) != '9') {
                    this.$root.toastError('Mã ngân sách phải bắt đầu là 1/2/3/7/8/9. \nBạn hãy kiểm tra lại!')
                    return false
                  }
                }
              }
            }
            // Kiểm tra theo yêu cầu VNPSR-12358 : Không cho phép chuyển đổi loại Khách hàng Doanh nghiệp <-> Cá nhân
            let loaiKH1 = this.dsLoaiKH.filter(item => item.loaikh_id == this.loaikh_id)[0]
            let kieukh_cu = loaiKH1 ? loaiKH1.loai.toString() : ''
            if (kieukh != kieukh_cu) {
              this.$root.toastError('Chức năng này không được thay đổi loại khách hàng Doanh nghiệp <-> Cá nhân. Để thay đổi hãy vào chức năng Chuyển quyền.')
              return false
            }
          }
          if (!this.cbo_kieuld_id.Value) {
            this.$root.toastError('Hãy chọn kiểu yêu cầu cho thuê bao này !')
            check = false
          } else if (this.txt_magiaodich.Value) {
            let rs = await this.$root.context.post('/web-hopdong/hopdong/kiemtra_makh_matt_magd', {
              maKh: this.txt_magiaodich.Value,
              kieu: 3,
              loaiHdId: this.LOAIHOPDONG.THAY_TT_KHAC
            })
            if (rs.data == 0) {
              this.$root.toastError('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!')
            }
          }
          // #region Kiểm tra thay đổi dịch vụ khác
          if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_DICHVU_KHAC) {
            if (this.dvkhaclist.length <= 0) {
              this.$root.toastError('Bạn chưa đăng ký dịch vụ khác !')
              return false
            }
          }
          // #region Thay đổi thông tin thanh toán
          if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THANHTOAN) {
            if (!this.txt_tenTT.Value) {
              this.$root.toastError('Hãy nhập tên thanh toán !')
              return false
            }
            if (this.txt_tenTT.Value.length > 400) {
              this.$root.toastError('Trường Tên thanh toán dữ liệu vượt quá độ dài cho phép.')
              return false
            }
            if (!this.cbo_maDD.Value) {
              this.$root.toastError('Hãy chọn mã dại diện !')
              return false
            }
            if (!this.txt_diachiTT.Value || this.quantt_id == 0 || this.phuongtt_id == 0) {
              this.$root.toastError('Hãy nhập địa chỉ thanh toán !')
              return false
            }
            if (this.txt_diachiTT.Value.length > 800) {
              this.$root.toastError('Trường Địa chỉ TT dữ liệu vượt quá độ dài cho phép.')
              return false
            }
            if (!this.KiemTra_SDT(this.txt_soDT_TT.Value, 'THANHTOAN')) {
              return false
            }
            // let rsTS1 = await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/ht_dt_combobox",{
            //   table: "admin.thamso_md",
            //   text: "ten_ts,macdinh",
            //   value: "ma_ts",
            //   where: "ma_ts = 'BATBUOC_CHON_DACDIEM' and phanvung_id = "+ this.$auth.getPhanVungID()
            // })
            let rsTS1 = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_thamso_md_mats?ma_ts=BATBUOC_CHON_DACDIEM')
            let dsTS1 = rsTS1.data.length ? rsTS1.data[0] : null
            let batbuoc_chon_dacdiem = -1
            if (dsTS1) {
              batbuoc_chon_dacdiem = parseInt(dsTS1.ten_ts)
            }
            if (batbuoc_chon_dacdiem == 1 && (this.quantt_id != 0 || this.phuongtt_id != 0) && (this.phott_id != 0 || this.aptt_id != 0 || this.khutt_id != 0)) {
              // let rsKtraDD = await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/ht_dt_combobox",{
              //   table: "css.dacdiem_dd",
              //   text: "2",
              //   value: "1",
              //   where: `phuong_id = ${this.phuongtt_id} and pho_id = ${this.phott_id} and dacdiem_id<>0 and phanvung_id = `+ this.$auth.getPhanVungID()
              // })
              let rsKtraDD = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/kiem_tra_dac_diem_dd?phuongtt_id=${this.phuongtt_id}&phott_id=${this.phott_id}`)
              if (rsKtraDD.data != null && rsKtraDD.data.length > 0) {
                if (this.dacdiemtt_id == 0) {
                  this.$root.toastError('Địa chỉ thanh toán này có đặc điểm. Yêu cầu chọn đặc điểm!')
                  return false
                }
              }
            }
            if (!this.txt_maTT.Value && this.thanhtoan_id != 0) {
              this.$root.toastError('Kiểm tra lại mã thanh toán !')
              return false
            }
            if (!this.txt_diachiCT.Value) {
              this.$root.toastError('Hãy nhập địa chỉ chứng từ !')
              return false
            }
            if (this.txt_diachiCT.Value.length > 800) {
              this.$root.toastError('Trường Địa chỉ CT dữ liệu vượt quá độ dài cho phép.')
              return false
            }
            if (this.txt_diachiBC.Value && this.txt_diachiBC.Value.length > 800) {
              this.$root.toastError('Trường Địa chỉ BC dữ liệu vượt quá độ dài cho phép.')
              return false
            }
            if (!this.txt_maNVTC.Value) {
              this.$root.toastError('Hãy nhập mã nhân viên thu cước !')
              return false
            }
            if (!this.cbo_HTTT.Value == HINHTHUCTHANHTOAN.UYNHIEM_THU) {
              // let rsTS = await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/ht_dt_combobox",{
              //   table: "admin.thamso_md",
              //   text: "ten_ts,macdinh",
              //   value: "ma_ts",
              //   where: "ma_ts = 'KHONGNHAP_STK_UYNHIEMTHU' and phanvung_id = "+ this.$auth.getPhanVungID()
              // })
              let rsTS = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_thamso_md_mats?ma_ts=KHONGNHAP_STK_UYNHIEMTHU')
              this.khongnhap_nh_unt = 0
              let dsTS = rsTS.data.length ? rsTS.data[0] : null
              if (dsTS) {
                this.khongnhap_nh_unt = parseInt(dsTS.ten_ts)
              }
              if (this.khongnhap_nh_unt != 1 && !this.txt_stk_TT.Value) {
                this.$root.toastError('Hãy nhập số tài khoản!')
                return false
              }
            }
            if (this.txt_stk_TT.Value && this.txt_stk_TT.Value.length > 80) {
              this.$root.toastError('Trường Tài khoản dữ liệu vượt quá độ dài cho phép.')
              return false
            }
            if (this.txt_mst_TT.Value && this.txt_mst_TT.Value.length > 60) {
              this.$root.toastError('Trường Mã số thuế dữ liệu vượt quá độ dài cho phép.')
              return false
            }
            if (this.txt_ghiChuTT.Value && this.txt_ghiChuTT.Value.length > 400) {
              this.$root.toastError('Trường Ghi chú dữ liệu vượt quá độ dài cho phép.')
              return false
            }
            if (this.txt_chu_TK.Value && this.txt_chu_TK.Value.length > 180) {
              this.$root.toastError('Trường Chủ TK dữ liệu vượt quá độ dài cho phép.')
              return false
            }
            if (this.txt_soDT_TT.Value && this.txt_soDT_TT.Value.length > 11) {
              this.$root.toastError('Trường Số ĐT dữ liệu vượt quá độ dài cho phép.')
              return false
            }
            if (this.txt_emailTT.Value && this.txt_emailTT.Value.length > 80) {
              this.$root.toastError('Trường Email dữ liệu vượt quá độ dài cho phép.')
              return false
            }
            if (this.kt_no_thaydoi_thongtin_tt == 1) {
              let rsTienNo = await this.$root.context.get(`/web-hopdong/hopdong/tracuu_no_thuebao`, {
                thueBaoId: this.thuebao_id
              })
              if (rsTienNo.data > 0) {
                this.$root.toastError(`Thuê bao: ${this.txt_maTB.Value} còn nợ: ${rsTienNo.data} vnđ.\nPhải thanh toán trước khi thực hiện!`)
                return false
              }
            }
            if (!this.KIEMTRA_HTTT()) {
              return false
            }
            if (this.kiemtra_lienhe_thanhtoan == 1 && this.tsbtnNhapMoi.Enable === false) {
              let dky_lh_tt = 0
              let dk_dvk_13 = 0//Nhận giấy báo cước qua SMS
              let dk_dvk_14 = 0//Nhận giấy báo cước qua Email
              let sd_lh_tt = 0
              let sd_dvk_13 = 0
              let sd_dvk_14 = 0
              let huy_dvk_13 = 0
              let huy_dvk_14 = 0
              for (let i = 0; i < this.dsDK_BanDau.length; i++) {
                if (this.dsDK_BanDau[i].dvKhacId == 13 || this.dsDK_BanDau[i].dvKhacId == 14) {
                  sd_lh_tt = 1
                  break
                }
              }
              for (let i = 0; i < this.dsDK_DVK.length; i++) {
                if ((this.dsDK_DVK[i].dvkhac_id == 13 || this.dsDK_DVK[i].dvkhac_id == 14) && this.dsDK_DVK[i].kieu_yc == 1) {
                  dky_lh_tt = 1
                  break
                }
              }
              if (sd_lh_tt == 0 && dky_lh_tt == 0) //chưa sử dụng và ko đăng ký mới dv khác 13, 14
              {
                this.$root.toastError('Phải đăng ký dịch vụ "Nhận giấy báo cước qua SMS" hoặc "Nhận giấy báo cước qua Email"')
                return false
              } else {
                for (let i = 0; i < this.dsDK_BanDau.length; i++) {
                  if (this.dsDK_BanDau[i].dvKhacId == 13) {
                    sd_dvk_13 = 1
                  }
                  if (this.dsDK_BanDau[i].dvKhacId == 14) {
                    sd_dvk_14 = 1
                  }
                }
                for (let i = 0; i < this.dsDK_DVK.length; i++) {
                  if (this.dsDK_DVK[i].dvkhac_id == 13 && this.dsDK_DVK[i].kieu_yc == 1) {
                    dk_dvk_13 = 1
                  }
                  if (this.dsDK_DVK[i].dvkhac_id == 14 && this.dsDK_DVK[i].kieu_yc == 1) {
                    dk_dvk_14 = 1
                  }
                }
                for (let i = 0; i < this.dsDK_DVK.length; i++) {
                  if (this.dsDK_DVK[i].dvkhac_id == 13 && this.dsDK_DVK[i].kieu_yc == 0) {
                    huy_dvk_13 = 1
                  }
                  if (this.dsDK_DVK[i].dvkhac_id == 14 && this.dsDK_DVK[i].kieu_yc == 0) {
                    huy_dvk_14 = 1
                  }
                }
                if ((huy_dvk_13 == 1 && dk_dvk_14 == 0 && sd_dvk_14 == 0) || (huy_dvk_14 == 1 && dk_dvk_13 == 0 && sd_dvk_13 == 0)) {
                  this.$root.toastError('Phải đăng ký dịch vụ "Nhận giấy báo cước qua SMS" hoặc "Nhận giấy báo cước qua Email"')
                  return false
                }
                if ((sd_dvk_13 == 1 || dk_dvk_13 == 1) && huy_dvk_13 == 0 && this.txt_soDT_TT.Value == '') {
                  this.$root.toastError('SĐT thanh toán không thể để trống khi dùng dịch vụ "Nhận giấy báo cước qua SMS"!')
                  return false
                }
                if ((sd_dvk_14 == 1 || dk_dvk_14 == 1) && huy_dvk_14 == 0 && this.txt_emailTT.Value == '') {
                  this.$root.toastError('Email thanh toán không thể để trống khi dùng dịch vụ "Nhận giấy báo cước qua Email"!')
                  return false
                }
              }
            }
            // let email_regex = /^(?!\.)(""([^""\r\\]|\\[""\r\\])*""|([-a-z0-9!#$%&'*+/=?^_`{|}~]|(?<!\.)\.)*)(?<!\.)@[a-z0-9][\w\.-]*[a-z0-9]\.[a-z][a-z\.]*[a-z]$/;
            if (this.txt_emailTT.Value) {
              // let email_regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
              let email_regex = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/
              let rs_email = email_regex.test(this.txt_emailTT.Value)
              if (!rs_email) {
                this.$root.toastError('Email thanh toán không đúng định dạng')
                return false
              }
            }
          }
          // #region Thay đổi thông tin khuyến mãi
          if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_KHUYENMAI) {
            if (this.dtCTKMHD.length == 0) {
              this.$root.toastError('Bạn chưa thay đổi khuyến mại nào !')
              check = false
              return check
            } else if (this.cbo_kieukm_id.Value == 1) {
              try {
                let list_ctkm_id = ''
                let temp = '<ds>'
                this.dtCTKMHD.forEach(element => {
                  temp += `<vt ctkm_id='${element.chitietkm_id}'/>`
                })
                temp += '</ds>'
                if (this.loaitb_id == LOAIHINHTB.INTERNET_MYTV) {
                  let rsKiemtra_km = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_km_v2', {
                    kieu: 0,
                    ctkm_id: -1,
                    hdtb_id: 0,
                    ma_tb: this.txt_maTB.Value,
                    ds_ctkm: temp
                  })
                  let ckb_km = rsKiemtra_km.data.result
                  if (!ckb_km) {
                    this.$root.toastError('Không lấy được thông tin kiểm tra đặt cọc')
                    return false
                  }
                  if (ckb_km != 1 && ckb_km != -1) {
                    this.$root.toastError(ckb_km)
                    return false
                  }
                }
              } catch (error) {
                this.$root.toastError('Có lỗi khi kiểm tra đặt cọc của thuê bao')
                return false
              }
            }
          }
          //check Thay đổi thông tin dịch vụ cntt
          if ((this.dichvuvt_id == DICHVUVIENTHONG.ANTOAN_BAOMAT_TT
            || this.dichvuvt_id == DICHVUVIENTHONG.DICHVU_CNTT
            || this.dichvuvt_id == DICHVUVIENTHONG.TRUNGTAM_DULIEU
            || this.dichvuvt_id == DICHVUVIENTHONG.HOINGHI_TRUYENHINH)
            && (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_DV_CNTT
              || this.cbo_kieuld_id.Value == KIEULAPDAT.GIAHAN_DV_CNTT)) {
            if (this.ItemList <= 0) {
              this.$root.toastError('Chưa được thiết lập tham số dịch vụ. Liên hệ admin để kiểm tra')
              return false
            }
            if (!this.CheckDataError(this.ItemList)) {
              return false
            }
          }
          if (!this.cbo_donviQL_id.Value) {
            this.$root.toastError('Đơn vị quản lý không được để trống')
            return false
          }
          if (this.iloai_km == 0 || this.iloai_km == 1) {
            if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THUEBAO || this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THANHTOAN
              || this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_KHACHHANG || this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_LIENHE
              || this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_DICHVU_KHAC) {
              let kieuld_text = this.dsKieuYCLD.find(x => x.kieuld_id == this.cbo_kieuld_id.Value)
              this.$root.toastError('Bạn không được chọn kiểu yêu cầu ' + kieuld_text.ten_kieuld + ' trong chức năng này! ')
              return false
            }
          }
          return check
        } catch (e) {
          console.log(e)
          return false
        }
      },
      async Kiemtra_hopdong_thaydoi_dt(vma_tb, vdichvuvt_id, vloaihd_id) {
        let rs = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_hopdong_thaydoi_dt', {
          vma_tb: vma_tb,
          vdichvuvt_id: vdichvuvt_id,
          vloaihd_id: vloaihd_id
        })
        return rs.data.result
      },
      async Kiemtra_goidadv_doituong(vthuebao_id, vdoituong_id) {
        let rs = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_goidadv_doituong', {
          vthuebao_id: vthuebao_id,
          vdoituong_id: vdoituong_id
        })
        return rs.data.result
      },
      IsNumber(pValue) {
        return /^[0-9]*$/.test(pValue)
      },
      CheckDataError(itemCheck) {
        console.log(itemCheck)
        // {
        //   "FIEL_D_NAME": "MA_TB",
        //   "FIEL_D_VALUE": 1485481,
        //   "FIEL_D_LENGTH": 30,
        //   "CAP_TION": "Mã thuê bao",
        //   "DAT_A_TYPE": 0,
        //   "ATT": 0
        // }
        let check = true
        itemCheck.forEach(item => {
          if (item.DATA_TYPE == this.KIEU_DL.CST_TEXT && item.FIELD_NAME != 'MA_TB') {
            if (item.FIELD_VALUE == null && item.FIELD_NULLABLE == 0) {
              this.$root.toastError('Phải nhập ' + item.TEN_HIENTHI + '.')
              check = false
            }
            if (item.FIELD_VALUE == '' && item.FIELD_NULLABLE == 0) {
              this.$root.toastError('Phải nhập ' + item.TEN_HIENTHI + '.')
              check = false
            }
            if (item.FIELD_NAME != 'MA_TB' && item.FIELD_VALUE != '' && item.FIELD_VALUE != null && item.MAX_VALUE && item.MIN_VALUE && parseInt(item.MAX_VALUE) !== 5) {
              if (item.FIELD_VALUE.length > parseInt(item.MAX_VALUE) || item.FIELD_VALUE.length < parseInt(item.MIN_VALUE)) {
                this.$root.toastError(item.TEN_HIENTHI + ' không thể lớn hơn ' + item.MAX_VALUE + ' và nhỏ hơn ' + item.MIN_VALUE + '  ký tự.')
                check = false
              }
            }
            if (this.dichvuvt_id == DICHVUVIENTHONG.ANTOAN_BAOMAT_TT) {
              if (item.FIELD_NAME == 'SERIAL') {
                ///Thiếu serial cần ráp sau
              }
            }
          } else if (item.DATA_TYPE == this.KIEU_DL.CST_NUMBER) {
            if ((item.FIELD_VALUE === null || item.FIELD_VALUE === '') && item.FIELD_NULLABLE == 0) {
              this.$root.toastError('Phải nhập ' + item.TEN_HIENTHI)
              check = false
            }
            if (item.FIELD_VALUE != null && item.FIELD_VALUE != '' && !this.isNumeric(item.FIELD_VALUE)) {
              //&& !this.CheckIsNumber(item.FIELD_VALUE)
              this.$root.toastError(item.TEN_HIENTHI + ' phải là kiểu số!')
              check = false
            }
            // if (item.MAX_VALUE && item.MIN_VALUE && item.FIELD_VALUE) {
            //     if (parseInt(item.MAX_VALUE) > 0 && item.FIELD_VALUE.length > parseInt(item.MAX_VALUE)) {
            //       this.$root.toastError(item.TEN_HIENTHI + " không thể lớn hơn " + item.MAX_VALUE + "  ký tự.")
            //       check = false
            //     }
            //
            // }
          } else if (item.DATA_TYPE == this.KIEU_DL.CST_DATE) {
            if ((item.FIELD_VALUE == null || item.FIELD_VALUE == '') && item.FIELD_NULLABLE == 0) {
              this.$root.toastError(item.TEN_HIENTHI + ' phải có giá trị và đúng định dạng dd/mm/yyyy!')
              check = false
            }
            if (item.FIELD_VALUE == 'Invalid Date') {
              this.$root.toastError(item.TEN_HIENTHI + ' phải có giá trị và đúng định dạng dd/mm/yyyy!')
              check = false
            }
          }
          console.log('chec chec ', check, item.TEN_HIENTHI)
          if (!check) {
            // this.$refs[`${item.FIELD_NAME}`][0].$refs[`COMPO-${item.FIELD_NAME}`].focus()
            this.$refs[item.FIELD_NAME][0].$refs['COMPO-' + item.FIELD_NAME].focus()
            throw false
          }
        })
        if (!check) {
          throw false
        }
        return check
      },
      isNumeric: function(n) {
        console.log('numm', !isNaN(parseFloat(n)) && isFinite(n))
        return !isNaN(parseFloat(n)) && isFinite(n)
      },
      // Kiểm tra nhập số điện thoại theo đúng chuẩn - VB 3309/VNPT VNP-KTNV
      KiemTra_SDT(sdt, ten_goi) {
        let loai = ''
        if (ten_goi === 'LIENHE') loai = 'liên hệ'
        if (ten_goi === 'THANHTOAN') loai = 'thanh toán'
        if (!sdt) {
          this.$root.toastError('Số điện thoại ' + loai + ' của khách hàng bắt buộc nhập.')
          this.$refs['txt_telKH'].focus()
          return false
        }
        if (sdt.trim().length < 10) {
          this.$root.toastError('Số điện thoại ' + loai + ' của khách hàng không được nhỏ hơn 10 ký tự!')
          this.$refs['txt_telKH'].focus()
          return false
        }
        if (sdt.trim().length > 11) {
          this.$root.toastError('Số điện thoại ' + loai + ' của khách hàng không được lớn hơn 11 ký tự!')
          this.$refs['txt_telKH'].focus()
          return false
        }
        if (!this.IsNumber(sdt.trim())) {
          this.$root.toastError('Số điện thoại ' + loai + ' của khách hàng phải là số!')
          this.$refs['txt_telKH'].focus()
          return false
        }
        if (!sdt.toString().trim().startsWith('0')) {
          this.$root.toastError('Dữ liệu điện thoại ' + loai + ' phải bắt đầu là số 0')
          this.$refs['txt_telKH'].focus()
          return false
        }
        return true
      },
      KiemTra_SDT_V2(sdt, ten_goi) {
        let loai = ''
        if (ten_goi === 'KHACHHANG') loai = 'khách hàng'
        if (ten_goi === 'THANHTOAN') loai = 'thanh toán'
        if (!sdt) {
          this.$root.toastError('Số điện thoại ' + loai + ' không được để trống.')
          return false
        }
        if (sdt.trim().length < 10) {
          this.$root.toastError('Số điện thoại ' + loai + ' không được nhỏ hơn 10 ký tự!')
          return false
        }
        if (sdt.trim().length > 11) {
          this.$root.toastError('Số điện thoại ' + loai + ' không được lớn hơn 11 ký tự!')
          return false
        }
        if (!this.IsNumber(sdt.trim())) {
          this.$root.toastError('Số điện thoại ' + loai + ' phải là kiểu số!')
          return false
        }
        return true
      },
      async taoDuLieu(themmoi) {
        await this.TaoDuLieu_HDKH(themmoi)
        if (this.loi_sinh_magd) return
        await this.TaoDuLieu_HDTT(themmoi)
        await this.TaoDuLieu_HDTB(themmoi)
        await this.TaoDuLieu_CT_TIENHD(true)
        await this.TaoDuLieu_DiaChi(themmoi, 4)
        this.TaoDuLieu_HDTT_NV(themmoi)
        await this.TaoDuLieu_HDKHUYENMAI(themmoi)
        await this.CapNhat_HDTB_ID_DangKyDV()
        this.CapNhat_HDTB_ID_HDTB_KV()
        switch (parseInt(this.dichvuvt_id.toString(), 10)) {
          case DICHVUVIENTHONG.CO_DINH:
            await this.TaoDuLieu_HDTB_CD(themmoi)
            break
          case DICHVUVIENTHONG.DI_DONG:
            await this.TaoDuLieu_HDTB_DD(themmoi)
            break
          case DICHVUVIENTHONG.ADSL:
            await this.TaoDuLieu_HDTB_ADSL(themmoi)
            break
          case DICHVUVIENTHONG.GPHONE:
            await this.TaoDuLieu_HDTB_GP(themmoi)
            break
          case DICHVUVIENTHONG.TSL:
            await this.TaoDuLieu_HDTB_TSL(themmoi)
            break
          case DICHVUVIENTHONG.MEGAWAN:
            await this.TaoDuLieu_HDTB_MGWAN(themmoi)
            break
          case DICHVUVIENTHONG.IMS:
            await this.TaoDuLieu_HDTB_IMS(themmoi)
            break
          case DICHVUVIENTHONG.ANTOAN_BAOMAT_TT:
          case DICHVUVIENTHONG.DICHVU_CNTT:
          case DICHVUVIENTHONG.TRUNGTAM_DULIEU:
          case DICHVUVIENTHONG.HOINGHI_TRUYENHINH:
            break
          case 50:
            break
        }
      },
      CapNhat_HDTB_ID_HDTB_KV() {
        for (var i = 0; i < this.dsHDTB_KV.length; i++) {
          this.dsHDTB_KV[i]['HDTB_ID'] = this.hdtb_id
        }
      },
      TaoDuLieu_HDTB_CNTT: async function() {
        if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_DV_CNTT ||
          this.cbo_kieuld_id.Value == KIEULAPDAT.GIAHAN_DV_CNTT) {
        } else {
          this.ItemList.forEach(item => {
            if (item.FIELD_NAME == 'MA_TB') {
              item.FIELD_VALUE = this.thuebao_id
            }
          })
        }
        if (this.ItemList.length > 0) {
          await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/themmoi_dichvu_cntt', {
            hdtbid: this.hdtb_id,
            cnttlist: this.ItemList
          })
        }
      },
      TaoDichVu_CNTT: async function() {
        const list_new_cntt = []
        if (this.ItemList.length < 1) {
          return list_new_cntt
        }
        this.ItemList.forEach(item => {
          let new_item = {}
          new_item.FIEL_D_NAME = item.FIELD_NAME
          if (item.FIELD_NAME == 'MA_TB') {
            new_item.FIEL_D_VALUE = this.thuebao_id
          } else {
            new_item.FIEL_D_VALUE = item.FIELD_VALUE
          }
          new_item.FIEL_D_LENGTH = item.FIELD_LENGTH
          new_item.CAP_TION = item.CAPTION
          new_item.DAT_A_TYPE = item.DATA_TYPE
          // new_item.LOOK_UP_SQL = item.LOOKUP_SQL
          new_item.ATT = item.ATT
          new_item.FIEL_D_NULLABLE = item.FIELD_NULLABLE
          new_item.FIEL_D_UNIQUE = item.FIELD_UNIQUE
          list_new_cntt.push(new_item)
        })
        list_new_cntt.forEach(item => {
          if (item.ATT == 2) {
            if (moment(item.FIEL_D_VALUE).isValid()) {
              item.FIEL_D_VALUE = moment(String(item.FIEL_D_VALUE)).format('DD/MM/YYYY')
            }
          }
          if (item.ATT == 9) {
            if (moment(item.FIEL_D_VALUE).isValid()) {
              item.FIEL_D_VALUE = moment(String(item.FIEL_D_VALUE)).format('DD/MM/YYYY HH:mm:ss')
              console.log(item.FIEL_D_VALUE)
            }
          }
        })
        return list_new_cntt
      },
      TAO_DL_CapNhatDichVu_CNTT() {
        const list_new_cntt = []
        if (this.ItemList.length < 1) {
          return list_new_cntt
        }
        this.ItemList.forEach(item => {
          if (item.ATT == 2) {
            if (moment(item.FIELD_VALUE).isValid()) {
              item.FIELD_VALUE = moment(item.FIELD_VALUE).format('DD/MM/YYYY')
            }
          }
          if (item.ATT == 9) {
            if (moment(item.FIELD_VALUE).isValid()) {
              item.FIELD_VALUE = moment(String(item.FIELD_VALUE)).format('DD/MM/YYYY HH:mm:ss')
            }
          }
        })
        this.ItemList.forEach(item => {
          let new_item = {}
          new_item.FIEL_D_NAME = item.FIELD_NAME
          if (item.FIELD_NAME == 'MA_TB') {
            new_item.FIEL_D_VALUE = this.thuebao_id
          } else {
            new_item.FIEL_D_VALUE = item.FIELD_VALUE
          }
          new_item.FIEL_D_LENGTH = item.FIELD_LENGTH
          new_item.CAP_TION = item.CAPTION
          new_item.DAT_A_TYPE = item.DATA_TYPE
          // new_item.LOOK_UP_SQL = item.LOOKUP_SQL
          new_item.ATT = item.ATT
          list_new_cntt.push(new_item)
        })
        return list_new_cntt
      },
      CapNhatDichVu_CNTT: async function() {
        let list_new_cntt = this.TAO_DL_CapNhatDichVu_CNTT()
        if (list_new_cntt.length > 0) {
          await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/capnhat_dichvu_cntt', {
            hdtbid: this.hdtb_id,
            cnttlist: list_new_cntt
          })
        }
      },
      // #region  Tao du lieu cho bang HD_KHACHHANG
      async TaoDuLieu_HDKH(themmoi) {
        try {
          this.dsHDKH = []
          let vmagd_temp = ''
          let rowHDKH = {}
          if (themmoi) {
            await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'HD_KHACHHANG' })
              .then(res => {
                if (res.error === '200' && res.data.length > 0)
                  this.hdkh_id = res.data[0].id
              }).catch(e => {
                throw e
              })
          }
          rowHDKH.HDKH_ID = this.hdkh_id
          if (themmoi) {
            // SINH_MAGD và SINH_MAGD_V2 giống nhau
            // if (this.ts_sinhma_gd_theo_donvi) {
            let obj = {}
            obj.donvi_id = this.$auth.getDonViID()
            obj.loaihd_id = this.LOAIHOPDONG.THAY_TT_KHAC
            await this.$root.context.post('/web-hopdong/CapNhatTraTruoc/sinh_magd_theo_dv', obj)
              .then(res => {
                vmagd_temp = res.data
              })
            // }else {
            //
            // }
            if (vmagd_temp.includes('ERROR:')) {
              this.$root.toastError(vmagd_temp.split(':')[1])
              this.loi_sinh_magd = true
            }
            this.txt_magiaodich.Value = vmagd_temp
            rowHDKH.MA_GD = vmagd_temp
          } else rowHDKH.MA_GD = this.txt_magiaodich.Value
          rowHDKH.MA_HD = this.txt_mahopdong.Value
          rowHDKH.MA_KH = this.txt_maKH.Value
          if (this.khachhang_id > 0)
            rowHDKH.KHACHHANG_ID = this.khachhang_id
          rowHDKH.LOAIKH_ID = this.loaikh_id
          rowHDKH.TEN_KH = this.txt_tenKH.Value.replace('\'', '*$#')
          rowHDKH.DIACHI_KH = this.txt_diachiKH.Value
          rowHDKH.NGAY_YC = this.ddmmyyyyhhmiss(this.dtp_ngayYc.Value)
          rowHDKH.NGAYLAP_HD = this.ddmmyyyyhhmiss(this.dtp_ngaylapHd.Value)
          rowHDKH.DONVI_ID = this.$auth.getDonViID()
          rowHDKH.NHANVIEN_ID = this.$auth.getNhanVienID()
          rowHDKH.KHLON_ID = 1 //Giờ ko dùng cái này nữa, sử dụng Phân loại KH rồi
          rowHDKH.LOAIHD_ID = this.LOAIHOPDONG.THAY_TT_KHAC
          rowHDKH.MST = this.txt_mstKH.Value ? this.txt_mstKH.Value.trim() : null
          rowHDKH.NGANHNGHE_ID = this.cbo_nganhngheloc.Value
          rowHDKH.TNC1_ID = this.TNC1_ID
          rowHDKH.TNC2_ID = this.TNC2_ID
          rowHDKH.TNC3_ID = this.TNC3_ID
          rowHDKH.TNC4_ID = this.TNC4_ID
          rowHDKH.LOAIKH_ID = this.cbo_loaiKH.Value
          rowHDKH.LOAIGT_ID = this.cbo_giaytoKH.Value
          rowHDKH.SO_GT = this.txt_soGT.Value
          rowHDKH.EMAIL = this.txt_emailKH.Value ? this.txt_emailKH.Value.trim() : null
          if (this.txt_ngaycap.Value !== '')
            rowHDKH.NGAYCAP = this.txt_ngaycap.Value
          rowHDKH.NOICAP = this.txt_noicap.Value
          rowHDKH.SO_DT = this.txt_telKH.Value ? this.txt_telKH.Value.toString().trim() : null
          rowHDKH.SO_FAX = this.txt_faxKH.Value ? this.txt_faxKH.Value.toString().trim() : null
          if (this.hdkh_cha_id > 0) {
            rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
            this.hdkh_cha_id = 0
          }
          rowHDKH.DANTOC_ID = this.cbo_dantoc.Value
          rowHDKH.DIACHI_NDD = this.txt_diachiNDD.Value ? this.txt_diachiNDD.Value.toString().trim() : null
          rowHDKH.GIOITINH = this.txt_gioitinh.Value
          rowHDKH.QUOCTICH_ID = this.cbo_quoctich.Value
          if (this.txt_ngayhethan.Value !== '')
            rowHDKH.NGAYHETHAN_GT = this.txt_ngayhethan.Value
          if (this.txt_ngaysn.Value !== '')
            rowHDKH.NGAY_SN = this.txt_ngaysn.Value
          rowHDKH.NGUOI_DD = this.txt_nguoiDD.Value ? this.txt_nguoiDD.Value.toString().trim() : null
          rowHDKH.CHUCDANH_DD = this.txt_chucdanhDD.Value ? this.txt_chucdanhDD.Value.toString().trim() : null
          rowHDKH.PHANLOAIKH_ID = this.PHANLOAIKH_ID === -1 ? this.PHANLOAIKH_ID : 0
          rowHDKH.MAY_CN = this.$root.token.getUserName()
          rowHDKH.NGUOI_CN = this.$root.token.getUserName()
          rowHDKH.IP_CN = this.$root.token.getUserName()
          this.dsHDKH = [rowHDKH]
        } catch (e) {
          console.log(e)
          throw e
        }
      },
      // #region Tao du lieu cho bang HD_THUEBAO
      async TaoDuLieu_HDTB(themmoi) {
        this.dsHDTB = []
        let rowHDTB = {}
        if (themmoi) {
          await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'HD_THUEBAO' })
            .then(res => {
              if (res.error === '200' && res.data.length > 0)
                this.hdtb_id = res.data[0].id
            }).catch(e => {
              throw e
            })
        }
        rowHDTB.HDTB_ID = this.hdtb_id
        rowHDTB.HDTT_ID = this.hdtt_id
        rowHDTB.HDKH_ID = this.hdkh_id
        if (this.thuebao_id)
          rowHDTB.THUEBAO_ID = this.thuebao_id
        rowHDTB.MA_TB = this.txt_maTB.Value
        rowHDTB.TEN_TB = this.txt_tenthuebao.Value.replace('\'', '*$#')
        rowHDTB.DIACHI_TB = this.txt_diachiTB.Value
        rowHDTB.DIACHI_LD = this.txt_diachiLD.Value
        rowHDTB.KIEULD_ID = this.cbo_kieuld_id.Value
        rowHDTB.DOITUONG_ID = this.cbo_doituong_id.Value
        if (this.txt_ngaysnTB.Value)
          rowHDTB.NGAY_SN = this.txt_ngaysnTB.Value
        rowHDTB.TTHD_ID = this.TrangThaiHD.MOI
        rowHDTB.LOAITB_ID = this.loaitb_id
        rowHDTB.DICHVUVT_ID = this.dichvuvt_id
        rowHDTB.DONVI_ID = this.cbo_donviTC_id.Value
        rowHDTB.QUYTRINH_ID = this.quytrinh_id
        rowHDTB.GHICHU = this.txt_ghiChuTB.Value
        rowHDTB.MAY_CN = this.$root.token.getUserName()
        rowHDTB.NGUOI_CN = this.$root.token.getUserName()
        rowHDTB.IP_CN = this.$root.token.getUserName()
        if (this.cbo_tkchinh_id.Value !== null && this.loaitb_id === LOAIHINHTB.INTERNET_MYTV)
          rowHDTB.THUEBAO_CHA_ID = this.cbo_tkchinh_id.Value
        if (this.cbo_mucuoc_id.Value) {
          rowHDTB.MUCUOCTB_ID = this.cbo_mucuoc_id.Value
          // let rs = await this.$root.context.post(
          //   "/web-hopdong/goi-da-dichvu/fn_map_id",{
          //     id_neo:"cuoc_tb",
          //     table:"css.muccuoc_tb",
          //     dk:'where mucuoctb_id ='+ this.cbo_mucuoc_id.Value
          //   }
          // );
          let rs = await this.$root.context.get('web-hopdong/thaydoithongtinkhuyenmai/map_id_cuoc_tb', {
            mucuoctb_id: this.cbo_mucuoc_id.Value
          })
          if (rs.data) rowHDTB.MUCUOC_TB = rs.data
        }
        this.dsHDTB = [rowHDTB]
      },
      async TaoDuLieu_DiaChi(themmoi, kieu) {
        try {
          this.dsDiaChi = []
          this.dsDiaChiHDKH = []
          this.dsDiaChiHDTT = []
          this.dsDiaChiHDTB = []
          // KHACH HANG
          if (kieu === 1 || kieu === 4) {
            let rowDiaChiKH = {}
            if (themmoi) {
              await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'DIACHI' })
                .then(res => {
                  if (res.error === '200' && res.data.length > 0)
                    this.diachikh_id = res.data[0].id
                }).catch(e => {
                  throw e
                })
            }
            rowDiaChiKH.DIACHI_ID = this.diachikh_id
            rowDiaChiKH.TINH_ID = this.tinhkh_id
            rowDiaChiKH.QUAN_ID = this.quankh_id
            if (this.phuongkh_id !== 0)
              rowDiaChiKH.PHUONG_ID = this.phuongkh_id
            if (this.phokh_id !== 0)
              rowDiaChiKH.PHO_ID = this.phokh_id
            if (this.apkh_id !== 0)
              rowDiaChiKH.AP_ID = this.apkh_id
            if (this.khukh_id !== 0)
              rowDiaChiKH.KHU_ID = this.khukh_id
            if (this.dacdiemkh_id !== 0)
              rowDiaChiKH.DACDIEM_ID = this.dacdiemkh_id
            rowDiaChiKH.SONHA = this.sonhakh
            rowDiaChiKH.DIACHI = this.txt_diachiKH.Value
            this.dsDiaChi.push({ ...rowDiaChiKH })
            if (themmoi) {
              let rowDiaChiHDKH = {}
              rowDiaChiHDKH.DIACHI_ID = this.diachikh_id
              rowDiaChiHDKH.HDKH_ID = this.hdkh_id
              this.dsDiaChiHDKH = [rowDiaChiHDKH]
            }
          }
          if (kieu == 2 || kieu == 4) {
            let rowDiaChiTT = {}
            if (themmoi) {
              let rsDC = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'DIACHI' })
              this.diachitt_id = rsDC.data[0].id
            }
            rowDiaChiTT.DIACHI_ID = this.diachitt_id
            rowDiaChiTT.TINH_ID = this.tinhtt_id
            rowDiaChiTT.QUAN_ID = this.quantt_id
            if (this.phuongtt_id != 0) {
              rowDiaChiTT.PHUONG_ID = this.phuongtt_id
            }
            if (this.phott_id != 0) {
              rowDiaChiTT.PHO_ID = this.phott_id
            }
            if (this.aptt_id != 0) {
              rowDiaChiTT.AP_ID = this.aptt_id
            }
            if (this.khutt_id != 0) {
              rowDiaChiTT.KHU_ID = this.khutt_id
            }
            if (this.dacdiemtt_id != 0) {
              rowDiaChiTT.DACDIEM_ID = this.dacdiemtt_id
            }
            rowDiaChiTT.SONHA = this.sonhatt
            rowDiaChiTT.DIACHI = this.txt_diachiTT.Value
            this.dsDiaChi.push(rowDiaChiTT)
            if (themmoi) {
              let rowDiaChiHDTT = {}
              rowDiaChiHDTT.DIACHI_ID = this.diachitt_id
              rowDiaChiHDTT.HDTT_ID = this.hdtt_id
              this.dsDiaChiHDTT.push(rowDiaChiHDTT)
            }
          }
          // THUEBAO
          if (kieu === 3 || kieu === 4) {
            let rowDiaChiTB = {}
            let rowDiachiLD = {}
            if (themmoi) {
              await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'DIACHI' })
                .then(res => {
                  if (res.error === '200' && res.data.length > 0)
                    this.diachitb_id = res.data[0].id
                }).catch(e => {
                  throw e
                })
              await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'DIACHI' })
                .then(res => {
                  if (res.error === '200' && res.data.length > 0)
                    this.diachild_id = res.data[0].id
                }).catch(e => {
                  throw e
                })
            }
            rowDiaChiTB.DIACHI_ID = this.diachitb_id
            rowDiaChiTB.TINH_ID = this.tinhtb_id
            rowDiaChiTB.QUAN_ID = this.quantb_id
            if (this.phuongtb_id !== 0)
              rowDiaChiTB.PHUONG_ID = this.phuongtb_id
            if (this.photb_id !== 0)
              rowDiaChiTB.PHO_ID = this.photb_id
            if (this.aptb_id !== 0)
              rowDiaChiTB.AP_ID = this.aptb_id
            if (this.khutb_id !== 0)
              rowDiaChiTB.KHU_ID = this.khutb_id
            if (this.dacdiemtb_id !== 0)
              rowDiaChiTB.DACDIEM_ID = this.dacdiemtb_id
            rowDiaChiTB.SONHA = this.sonhatb
            rowDiaChiTB.BLOCK = this.block
            rowDiaChiTB.TANG = this.tang
            rowDiaChiTB.PHONG = this.phong
            rowDiaChiTB.MOTA = this.mota
            rowDiaChiTB.DIACHI = this.txt_diachiTB.Value
            this.dsDiaChi.push({ ...rowDiaChiTB })
            //Địa chỉ lắp đặt --> dataset dsdiachi
            rowDiachiLD.DIACHI_ID = this.diachild_id
            rowDiachiLD.TINH_ID = this.tinhld_id
            rowDiachiLD.QUAN_ID = this.quanld_id
            if (this.phuongld_id !== 0)
              rowDiachiLD.PHUONG_ID = this.phuongld_id
            if (this.phold_id !== 0)
              rowDiachiLD.PHO_ID = this.phold_id
            if (this.apld_id !== 0)
              rowDiachiLD.AP_ID = apld_id
            if (this.khuld_id !== 0)
              rowDiachiLD.KHU_ID = this.khuld_id
            if (this.dacdiemld_id !== 0)
              rowDiachiLD.DACDIEM_ID = this.dacdiemld_id
            rowDiachiLD.SONHA = this.sonhald
            rowDiachiLD.BLOCK = this.block_ld
            rowDiachiLD.TANG = this.tang_ld
            rowDiachiLD.PHONG = this.sophong_ld
            rowDiachiLD.MOTA = this.motathem_ld
            rowDiachiLD.DIACHI = this.txt_diachiLD.Value
            this.dsDiaChi.push({ ...rowDiachiLD })
            if (themmoi) {
              let rowDiaChiHDTB = {}
              rowDiaChiHDTB.DIACHI_ID = this.diachitb_id
              rowDiaChiHDTB.DIACHILD_ID = this.diachild_id
              rowDiaChiHDTB.HDTB_ID = this.hdtb_id
              this.dsDiaChiHDTB = [rowDiaChiHDTB]
            }
          }
        } catch (e) {
        }
      },
      async CapNhat_HDTB_ID_DangKyDV() {
        if (this.dbDichVu.length === 0) {
          return
        }
        let kieuLd = parseInt(this.cbo_kieuld_id.Value)
        if (kieuLd === KIEULAPDAT.THAYDOI_TT_THANHTOAN) {
          this.dbDichVuThayDoi.forEach(item => {
            item.hd_id = this.hdtt_id
          })
        } else {
          this.dbDichVuThayDoi.forEach(item => {
            item.hd_id = this.hdtb_id
          })
        }
      },
      async TaoDuLieu_HDKHUYENMAI() {
        this.dsHDKHUYENMAI = []
        this.dtCTKMHD.forEach(element => {
          let obj = {
            HDTB_ID: this.hdtb_id,
            CHITIETKM_ID: element.chitietkm_id,
            KHUYENMAI_ID: element.khuyenmai_id,
            KIEU_YC: element.kieu_yc,
            TYLE_TB: element.tyle_tb,
            TYLE_SD: element.tyle_sd,
            NGAY_BD: element.ngay_bd,
            NGAY_KT: element.ngay_kt,
            NGAY_BDDC: element.ngay_bddc,
            NGAY_KTDC: element.ngay_ktdc
          }
          if (element.thang_bd !== '' && element.thang_bd != undefined) {
            obj.THANG_BD = element.thang_bd
            obj.THANG_KT = element.thang_kt
          }
          if (element.thang_bddc !== '' && element.thang_bddc != undefined) {
            obj.THANG_BDDC = element.thang_bddc
            obj.THANG_KTDC = element.thang_ktdc
          }
          if (element.thang_huongdc !== '' && element.thang_huongdc != undefined) {
            obj.THANG_HUONGDC = element.thang_huongdc
          }
          if (element.thang_huongkm !== '' && element.thang_huongkm != undefined) {
            obj.THANG_HUONGKM = element.thang_huongkm
          }
          if (element.sothang_tg !== '' && element.sothang_tg != undefined) {
            obj.SOTHANG_TG = element.sothang_tg
          }
          if (element.datcoc_csd !== '' && element.datcoc_csd != undefined) {
            obj.DATCOC_CSD = element.datcoc_csd
          }
          if (element.tien_td !== '' && element.tien_td != undefined) {
            obj.TIEN_TD = element.tien_td
          }
          if (element.rkm_id !== '' && element.rkm_id != undefined) {
            obj.RKM_ID = element.rkm_id
          }
          if (element.tyle_kmld !== '' && element.tyle_kmld != undefined) {
            obj.TYLE_KMLD = element.tyle_kmld
          }
          if (element.thang_bdtg !== '' && element.thang_bdtg != undefined) {
            obj.THANG_BDTG = element.thang_bdtg
            obj.THANG_KTTG = element.thang_kttg
          }
          if (element.tien_sd !== '' && element.tien_sd != undefined) {
            obj.TIEN_SD = element.tien_sd
          }
          if (element.cuoc_sd !== '' && element.cuoc_sd != undefined) {
            obj.CUOC_SD = element.cuoc_sd
          }
          if (element.tien_tb !== '' && element.tien_tb != undefined) {
            obj.TIEN_TB = element.tien_tb
          }
          if (element.cuoc_tb !== '' && element.cuoc_tb != undefined) {
            obj.CUOC_TB = element.cuoc_tb
          }
          if (element.ngay_huy !== '' && element.ngay_huy != undefined) {
            obj.NGAY_HUY = element.ngay_huy
          }
          this.dsHDKHUYENMAI.push(obj)
        })
      },
      async TaoDuLieu_HDTT(themmoi) {
        this.dsHDTT = []
        if (themmoi) {
          let rsId = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'HD_THANHTOAN' })
          this.hdtt_id = rsId.data[0].id
        }
        let obj = {
          HDTT_ID: this.hdtt_id,
          HDKH_ID: this.hdkh_id,
          MA_TT: this.txt_maTT.Value,
          TEN_TT: this.txt_tenTT.Value.replace('\'', '*$#'),
          DIACHI_TT: this.txt_diachiTT.Value,
          DIACHI_BC: this.txt_diachiBC.Value,
          MATB_DD: this.cbo_maDD.Value,
          MST: this.txt_mst_TT.Value,
          HTTT_ID: this.cbo_HTTT.Value,
          STK: this.txt_stk_TT.Value,
          CHU_TK: this.txt_chu_TK.Value,
          SO_DT: this.txt_soDT_TT.Value,
          EMAIL: this.txt_emailTT.Value,
          DONVI_ID: this.cbo_donviQL_id.Value,
          DIACHI_CT: this.txt_diachiCT.Value,
          GHICHU: this.txt_ghiChuTT.Value,
          MAY_CN: this.$root.token.getUserName(),
          NGUOI_CN: this.$root.token.getUserName(),
          IP_CN: this.$root.token.getUserName(),
          NGAY_CN: DateTimeLib.toDateDisplay(new Date())
        }
        if (this.thanhtoan_id > 0) {
          obj.THANHTOAN_ID = this.thanhtoan_id
        }
        if (this.cbo_HTTT.Value == HINHTHUCTHANHTOAN.UYNHIEM_THU) {
          if (this.khongnhap_nh_unt != 1 || this.cbo_nganhang.Value != null && this.cbo_nganhang.Value != '') {
            obj.NGANHANG_ID = this.cbo_nganhang.Value
          }
        }
        if (this.tuyenthu_id != 0) {
          obj.TUYENTHU_ID = this.tuyenthu_id
        }
        // let rsMapId = await this.$root.context.post('/web-hopdong/goi-da-dichvu/fn_map_id', {
        //     id_neo: 'loai_nh',
        //     table: 'nganhang',
        //     dk: 'where nganhang_id = ' + this.cbo_nganhang.Value
        // })
        let rsMapId = await this.$root.context.get('web-hopdong/chuyendich/map_loai_nh_id_neo', {
          nganhang_id: this.cbo_nganhang.Value ? this.cbo_nganhang.Value : 0
        })
        let loai_nh = rsMapId.data && rsMapId.data.length > 0 ? rsMapId.data[0].loai_nh : null
        if (loai_nh == 2) {
          obj.DV_QHNS = this.dv_qhns
          obj.MACHUONG = this.machuong
          obj.MN_KT = this.mn_kt
          obj.MA_NDKT = this.ma_ndkt
          obj.MN_NSNN = this.mn_nsnn
          obj.TIEN_DM = this.tien_dm
        }
        this.dsHDTT.push(obj)
      },
      // #region Tao du lieu cho bang HDTB_CD
      async TaoDuLieu_HDTB_CD(themmoi) {
        let rowHDTB_CD = {}
        try {
          if (!themmoi) {
            // LAY_DS_HDTB_CD_THEO_HDTB_ID
            let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_codinh', { hdtbId: this.hdtb_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_CD = rs.data[0]
            }
          } else {
            // LAY_DS_DB_CD_THEO_TBID
            let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_danhba_codinh', { thueBaoId: this.thuebao_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_CD = rs.data[0]
            }
          }
          rowHDTB_CD.chuquan_id = this.cbo_chuquan_id.Value
          if (this.cbo_chuquanLD_id.Value !== null && this.cbo_chuquanLD_id.Value.toString() !== '0' && this.cbo_chuquanLD_id.Value.toString() !== '')
            rowHDTB_CD.chuquanld_id = parseInt(this.cbo_chuquanLD_id.Value.toString(), 10)
          rowHDTB_CD.hdtb_id = this.hdtb_id
          let row = {}
          for (let key in rowHDTB_CD) {
            let k = key.toUpperCase()
            row[k] = rowHDTB_CD[key]
          }
          this.dsHDTB_CD = [row]
        } catch (e) {
          console.log(r)
        }
      },
      // #region Tao du lieu cho bang HDTB_DD
      async TaoDuLieu_HDTB_DD(themmoi) {
        let rowHDTB_DD = {}
        try {
          if (!themmoi) {
            // LAY_DS_HDTB_DD_THEO_HDTB_ID
            let rs = await this.$root.context.post('/web-hopdong/hopdong/lay_ds_hdtb_dd_theo_hdtb_id', { ID: this.hdtb_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_DD = rs.data[0]
            }
          } else {
            // LAY_DS_DB_DD_THEO_TBID
            let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_danhba_dd', { thueBaoId: this.thuebao_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_DD = rs.data[0]
            }
          }
          rowHDTB_DD.hdtb_id = this.hdtb_id
          rowHDTB_DD.doituong_id_neo = this.cboDTVinaPhone.Value
          let row = {}
          for (let key in rowHDTB_DD) {
            let k = key.toUpperCase()
            row[k] = rowHDTB_DD[key]
          }
          this.dsHDTB_DD = [row]
        } catch (e) {
          console.log(e)
        }
      },
      // #region Tao du lieu cho bang HDTB_ADSL
      async TaoDuLieu_HDTB_ADSL(themmoi) {
        let rowHDTB_ADSL = {}
        try {
          if (!themmoi) {
            // LAY_DS_HDTB_ADSL_THEO_HDTB_ID
            let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl', { hdtbId: this.hdtb_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_ADSL = rs.data[0]
            }
          } else {
            // LAY_DS_DB_ADSL_THEO_TBID
            let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_danhba_adsl', { thueBaoId: this.thuebao_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_ADSL = rs.data[0]
            }
          }
          rowHDTB_ADSL.chuquan_id = this.cbo_chuquan_id.Value
          if (this.cbo_chuquanLD_id.Value !== null && this.cbo_chuquanLD_id.Value.toString() !== '0' && this.cbo_chuquanLD_id.Value.toString() !== '')
            rowHDTB_ADSL.chuquanld_id = parseInt(this.cbo_chuquanLD_id.Value.toString(), 10)
          rowHDTB_ADSL.hdtb_id = this.hdtb_id
          let row = {}
          for (let key in rowHDTB_ADSL) {
            let k = key.toUpperCase()
            row[k] = rowHDTB_ADSL[key]
          }
          this.dsHDTB_ADSL = [row]
        } catch (e) {
          console.log(e)
        }
      },
      async TaoDuLieu_HDTB_GP(themmoi) {
        let rowHDTB_GP = {}
        try {
          if (!themmoi) {
            // LAY_DS_HDTB_GP_THEO_HDTB_ID
            let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_gp', { hdtbId: this.hdtb_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_GP = rs.data[0]
            }
          } else {
            // LAY_DS_DB_GP_THEO_TBID
            let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_danhba_gp', { thueBaoId: this.thuebao_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_GP = rs.data[0]
            }
          }
          rowHDTB_GP.hdtb_id = this.hdtb_id
          rowHDTB_GP.chuquan_id = this.cbo_chuquan_id.Value
          let row = {}
          for (let key in rowHDTB_GP) {
            let k = key.toUpperCase()
            row[k] = rowHDTB_GP[key]
          }
          this.dsHDTB_GP = [row]
        } catch (e) {
          console.log(e)
        }
      },
      async TaoDuLieu_HDTB_TSL(themmoi) {
        let rowHDTB_TSL = {}
        try {
          let dt = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_danhba_tsl', { thueBaoId: this.thuebao_id })
          if (dt.data === null) return
          let f = dt.data.find(x => x.daucuoi_id === 1)
          if (f === undefined) return
          rowHDTB_TSL = { ...f }
          rowHDTB_TSL.hdtb_id = this.hdtb_id
          rowHDTB_TSL.chuquan_id = this.cbo_chuquan_id.Value
          if (this.cbo_chuquanLD_id.Value !== null && this.cbo_chuquanLD_id.Value.toString() !== '0' && this.cbo_chuquanLD_id.Value.toString() !== '')
            rowHDTB_TSL.chuquanld_id = parseInt(this.cbo_chuquanLD_id.Value.toString(), 10)
          let row = {}
          for (let key in rowHDTB_TSL) {
            let k = key.toUpperCase()
            row[k] = rowHDTB_TSL[key]
          }
          this.dsHDTB_TSL = [row]
          // for (let item of dt.data  ) {
          //   rowHDTB_TSL = item
          //   rowHDTB_TSL.hdtb_id = this.hdtb_id
          //   rowHDTB_TSL.chuquan_id = this.cbo_chuquan_id.Value
          //   if (this.cbo_chuquanLD_id.Value !== null && this.cbo_chuquanLD_id.Value.toString() !== "0" && this.cbo_chuquanLD_id.Value.toString() !== "")
          //     rowHDTB_TSL.chuquanld_id = parseInt(this.cbo_chuquanLD_id.Value.toString(), 10)
          //   let row = {}
          //   for (let key in rowHDTB_TSL) {
          //     let k = key.toUpperCase()
          //     row[k] = rowHDTB_TSL[key]
          //   }
          //   this.dsHDTB_TSL = [row]
          // }
        } catch (e) {
          console.log(e)
        }
      },
      async TaoDuLieu_HDTB_MGWAN(themmoi) {
        let rowHDTB_MGWAN = {}
        try {
          if (!themmoi) {
            // LAY_DS_HDTB_MGWAN_THEO_HDTB_ID
            let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_mgwan', { hdtbId: this.hdtb_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_MGWAN = rs.data[0]
            }
          } else {
            // LAY_DS_DB_MEGAWAN_THEO_TBID
            let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_danhba_megawan', { thueBaoId: this.thuebao_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_MGWAN = rs.data[0]
            }
          }
          rowHDTB_MGWAN.hdtb_id = this.hdtb_id
          rowHDTB_MGWAN.chuquan_id = this.cbo_chuquan_id.Value
          if (this.cbo_chuquanLD_id.Value !== null && this.cbo_chuquanLD_id.Value.toString() !== '0' && this.cbo_chuquanLD_id.Value.toString() !== '')
            rowHDTB_MGWAN.chuquanld_id = parseInt(this.cbo_chuquanLD_id.Value.toString(), 10)
          let row = {}
          for (let key in rowHDTB_MGWAN) {
            let k = key.toUpperCase()
            row[k] = rowHDTB_MGWAN[key]
          }
          this.dsHDTB_MEWAN = [row]
        } catch (e) {
          console.log(e)
        }
      },
      async TaoDuLieu_HDTB_IMS(themmoi) {
        let rowHDTB_IMS = {}
        try {
          if (!themmoi) {
            // LAY_DS_HDTB_IMS_THEO_HDTB_ID
            let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_ims', { hdtbId: this.hdtb_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_IMS = rs.data[0]
            }
          } else {
            // LAY_DS_DB_IMS_THEO_TBID
            let rs = await this.$root.context.get('/web-hopdong/hopdong/lay_ds_danhba_ims', { thueBaoId: this.thuebao_id })
            if (rs.data && rs.data.length > 0) {
              rowHDTB_IMS = rs.data[0]
            }
          }
          rowHDTB_IMS.hdtb_id = this.hdtb_id
          rowHDTB_IMS.chuquan_id = this.cbo_chuquan_id.Value
          if (this.cbo_chuquanLD_id.Value !== null && this.cbo_chuquanLD_id.Value.toString() !== '0' && this.cbo_chuquanLD_id.Value.toString() !== '')
            rowHDTB_IMS.chuquanld_id = parseInt(this.cbo_chuquanLD_id.Value.toString(), 10)
          let row = {}
          for (let key in rowHDTB_IMS) {
            let k = key.toUpperCase()
            row[k] = rowHDTB_IMS[key]
          }
          this.dsHDTB_IMS = [row]
        } catch (e) {
          console.log(e)
        }
      },
      TaoDuLieu_CT_TIENHD: function(themmoi) {
        if (this.grcDanhSach.length == 0) {
          return
        }
        let CT_TIENHD_ID
        this.dsCTTHD = []
        this.grcDanhSach.forEach(async element => {
          if (themmoi) {
            let rsGetKey = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'CT_TIENHD' })
            CT_TIENHD_ID = rsGetKey.data[0].id
          }
          let obj = {
            HDTB_ID: this.hdtb_id,
            CT_TIENHD_ID: CT_TIENHD_ID,
            KHOANMUCTT_ID: element.khoanmuctt_id,
            TIEN: element.tien,
            VAT: element.vat,
            ID: element.khoanmuctt_id,
            LOAI_ID: 1,
            PHIEUTT_ID: element.phieutt_id,
            TYLE_VAT: element.tyle_vat,
            TYLE_VAT_ID: element.tyle_vat_id
          }
          this.dsCTTHD.push(obj)
        })
      },
      async themMoi_HopDong(huonggiao_tn) {
        try {
          // insert HDKH_SUB
          if (this.dsHDKH_Sub.length > 0) {
            console.log('this.dsHDKH[0]: ', this.dsHDKH[0])
            this.dsHDKH_Sub[0].HDKH_ID = this.dsHDKH[0] ? this.dsHDKH[0].HDKH_ID : 0
          }
          let ds_cntt = await this.TaoDichVu_CNTT()
          const newArray = [...this.dsDiaChi].map(item => {
            item.BL_OCK = item.BLOCK
            delete item.BLOCK
            return item
          })
          let data = {
            HUONGGIAO_ID: huonggiao_tn,
            dsHDKH: JSON.stringify(this.dsHDKH),
            dsHDTB: JSON.stringify(this.dsHDTB),
            dsHDTT: JSON.stringify(this.dsHDTT),
            dsHDKH_SUB: JSON.stringify(this.dsHDKH_Sub),
            dsHDTB_CD: JSON.stringify(this.dsHDTB_CD),
            dsHDTB_DD: JSON.stringify(this.dsHDTB_DD),
            dsHDTB_ADSL: JSON.stringify(this.dsHDTB_ADSL),
            dsHDTB_GP: JSON.stringify(this.dsHDTB_GP),
            dsHDTB_TSL: JSON.stringify(this.dsHDTB_TSL),
            dsHDTB_MGW: JSON.stringify(this.dsHDTB_MEWAN),
            dsHDTB_IMS: JSON.stringify(this.dsHDTB_IMS),
            dsHDTB_KV: JSON.stringify(this.dsHDTB_KV),
            dsDIACHI: JSON.stringify(newArray),
            dsDIACHI_HDKH: JSON.stringify(this.dsDiaChiHDKH),
            dsDIACHI_HDTB: JSON.stringify(this.dsDiaChiHDTB),
            dsDIACHI_HDTT: JSON.stringify(this.dsDiaChiHDTT),
            dsKM_HDTB: JSON.stringify(this.dsHDKHUYENMAI),
            dsHDTT_NV: JSON.stringify(this.dsHDTT_NV),
            dsCNTT: JSON.stringify(ds_cntt),
            dsTienHD: JSON.stringify(this.dsCTTHD)
          }
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hopdong', data)
          if (rs && rs.error_code == 'BSS-00000500' && rs.message) {
            //Tham số đầu vào không đúng định dạng.
            this.$root.toastError(rs.message)
            throw rs
          }
          // // switch (parseInt(this.cbo_kieuld_id.toString(),10)) {
          // //   case KIEULAPDAT.THAYDOI_TT_KHACHHANG:
          //     // insert HD_KHACHHANG
          //     if (this.dsHDKH.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hdkhachhang", this.dsHDKH[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // insert HDKH_SUB
          //     if (this.dsHDKH_Sub.length > 0){
          //       this.dsHDKH_Sub[0].HDKH_ID = this.dsHDKH[0].HDKH_ID
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hdkh_sub", this.dsHDKH_Sub[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     }
          //     // insert HD_THUEBAO
          //     if (this.dsHDTB.length > 0)
          //       await this.InsertHDTB(this.dsHDTB,huonggiao_tn)
          //     // insert HDTB_CD
          //     if (this.dsHDTB_CD.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hdtb_cd", this.dsHDTB_CD[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // insert HDTB_DD
          //     if (this.dsHDTB_DD.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hdtb_dd", this.dsHDTB_DD[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // insert HDTB_ADSL
          //     if (this.dsHDTB_ADSL.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hdtb_adsl", this.dsHDTB_ADSL[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // insert HDTB_GP
          //     if (this.dsHDTB_GP.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hdtb_gp", this.dsHDTB_GP[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // insert HDTB_TSL
          //     if (this.dsHDTB_TSL.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hdtb_tsl", this.dsHDTB_TSL[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // insert HDTB_MGW
          //     if (this.dsHDTB_MEWAN.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hdtb_mgwan", this.dsHDTB_MEWAN[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // insert HDTB_KV
          //     if (this.dsHDTB_KV.length > 0)
          //       await this.Insert_HDTB_KV(this.dsHDTB_KV)
          //     // insert DIACHI
          //     if (this.dsDiaChi.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_ds_diachi", this.dsDiaChi).then(res =>{
          //           if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //             this.$root.toastError(res.message_detail)
          //         })
          //     // insert DIACHI_HDKH
          //     if (this.dsDiaChiHDKH.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_diachi_hdkh", this.dsDiaChiHDKH[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // insert DIACHI_HDTB
          //     if (this.dsDiaChiHDTB.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_diachi_hdtb", this.dsDiaChiHDTB[0]).then(res =>{
          //           if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //             this.$root.toastError(res.message_detail)
          //         })
          //     // this.$root.toastSuccess('Cập nhật dữ liệu lên hệ thống viễn thông tỉnh thành công!')
          //
          //     // insert DIACHI_HDTT
          //     await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_diachi_hdtt", this.dsDiaChiHDTT[0])
          //     // insert HDTT
          //     if(this.dsHDTT.length > 0) {
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hd_thanhtoan", this.dsHDTT[0]);
          //       // this.$root.toastSuccess('Thêm dữ liệu hợp đồng thanh toán thành công!')
          //     }
          //     // insert khuyen mai
          //     if (this.dsHDKHUYENMAI.length > 0) {
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_khuyenmai_hdtb", this.dsHDKHUYENMAI[0]);
          //       // this.$root.toastSuccess('Thêm dữ liệu hợp đồng khuyến mãi thành công!')
          //     }
          // insert DVK
          await this.updateDangKy_dvk()
          // insert dv cntt
          //   await this.TaoDichVu_CNTT()
          //     break
          // }
        } catch (e) {
          console.log(e)
          throw e
        }
      },
      async capNhat_HopDong() {
        try {
          const newArray = [...this.dsDiaChi].map(item => {
            item.BL_OCK = item.BLOCK
            delete item.BLOCK
            return item
          })
          let ds_cntt = this.TAO_DL_CapNhatDichVu_CNTT()
          let data = {
            dsHDKH: JSON.stringify(this.dsHDKH),
            dsHDTB: JSON.stringify(this.dsHDTB),
            dsHDTT: JSON.stringify(this.dsHDTT),
            dsHDKH_SUB: JSON.stringify(this.dsHDKH_Sub),
            dsHDTB_CD: JSON.stringify(this.dsHDTB_CD),
            dsHDTB_DD: JSON.stringify(this.dsHDTB_DD),
            dsHDTB_ADSL: JSON.stringify(this.dsHDTB_ADSL),
            dsHDTB_GP: JSON.stringify(this.dsHDTB_GP),
            dsHDTB_TSL: JSON.stringify(this.dsHDTB_TSL),
            dsHDTB_MGW: JSON.stringify(this.dsHDTB_MEWAN),
            dsHDTB_IMS: JSON.stringify(this.dsHDTB_IMS),
            dsHDTB_KV: JSON.stringify(this.dsHDTB_KV),
            dsDIACHI: JSON.stringify(newArray),
            dsDIACHI_HDKH: JSON.stringify(this.dsDiaChiHDKH),
            dsDIACHI_HDTB: JSON.stringify(this.dsDiaChiHDTB),
            dsDIACHI_HDTT: JSON.stringify(this.dsDiaChiHDTT),
            dsKM_HDTB: JSON.stringify(this.dsHDKHUYENMAI),
            dsHDTT_NV: JSON.stringify(this.dsHDTT_NV),
            dsTienHD: JSON.stringify(this.dsCTTHD),
            dsCNTT: JSON.stringify(ds_cntt)
          }
          let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hopdong', data)
          if (rs && rs.error_code == 'BSS-00000500' && rs.message) {
            // Tham số đầu vào không đúng định dạng.
            this.$root.toastError(rs.message)
            throw rs
          }
          // switch (this.cbo_kieuld_id) {
          //   case KIEULAPDAT.THAYDOI_TT_KHACHHANG:
          //     // update HD_KHACHHANG
          //     if (this.dsHDKH.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hdkhachhang", this.dsHDKH[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // update HD_THUEBAO
          //     if (this.dsHDTB.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hd_thuebao", this.dsHDTB[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // update HDTB_CD
          //     if (this.dsHDTB_CD.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hdtb_cd", this.dsHDTB_CD[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // update HDTB_DD
          //     if (this.dsHDTB_DD.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hdtb_dd", this.dsHDTB_DD[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // update HDTB_ADSL
          //     if (this.dsHDTB_ADSL.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hdtb_adsl", this.dsHDTB_ADSL[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // update HDTB_GP
          //     if (this.dsHDTB_GP.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hdtb_gp", this.dsHDTB_GP[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // update HDTB_MGW
          //     if (this.dsHDTB_MEWAN.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hdtb_mgwan", this.dsHDTB_MEWAN[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // update HDTB_IMS
          //     if (this.dsHDTB_IMS.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hdtb_ims", this.dsHDTB_IMS[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // insert HDTB_KV
          //     if (this.dsHDTB_KV.length > 0)
          //       await this.Insert_HDTB_KV(this.dsHDTB_KV)
          //     // update DIACHI_HDKH
          //     if (this.dsDiaChiHDKH.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_diachi_hdkh", this.dsDiaChiHDKH[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // update DIACHI_HDTB
          //     if (this.dsDiaChiHDTB.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_diachi_hdtb", this.dsDiaChiHDTB[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // update DIACHI_HDTT
          //     if (this.dsDiaChiHDTT.length > 0)
          //       await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_diachi_hdtt", this.dsDiaChiHDTT[0]).then(res =>{
          //         if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //           this.$root.toastError(res.message_detail)
          //       })
          //     // update DIACHI
          //     if (this.dsDiaChi.length > 0)
          //        await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_ds_diachi", this.dsDiaChi).then(res =>{
          //          if (res.error_code !== "BSS-00000000" && res.error_code !== "BSS-00000204" && res.message_detail)
          //            this.$root.toastError(res.message_detail)
          //       })
          //update dsDK_DVK
          //  debugger
          if (this.dbDichVuThayDoi.length > 0) {
            await this.deleteDangKy_dvk()
            await this.updateDangKy_dvk()
          }
          //  await this.CapNhatDichVu_CNTT()
          // update HD_THANHTOAN
          // if(this.dsHDTT.length > 0) {
          //   let rs = await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hd_thanhtoan", this.dsHDTT[0]);
          //   if(rs.data && rs.data[0].result) {
          //     // this.$root.toastSuccess('Cập nhật dữ liệu hợp đồng thanh toán thành công!')
          //   }
          //   else {
          //     this.$root.toastError('Cập nhật dữ liệu hợp đồng thanh toán thất bại!')
          //   }
          // }
          // if(this.dsHDKHUYENMAI.length > 0) {
          //   await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/xoa_khuyenmai_hdtb?hdtb_id=${this.hdtb_id}`);
          //   await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/themmoi_khuyenmai_hdtb", this.dsHDKHUYENMAI[0]);
          //   // this.$root.toastSuccess('Cập nhật dữ liệu hợp đồng khuyến mãi thành công!')
          // }
          //     break
          // }
        } catch (e) {
          throw e
        }
      },
      async lay_ngay_cn() {
        try {
          let response = await this.$root.context.get('/web-hopdong/ThayDoiDatCocHuy/lay_ngay_cn')
          return !response.data ? null : DateTimeLib.toDateObjectWithFormat(response.data.ngay_cn, 'YYYY-MM-DD HH:mm:ss')
        } catch (err) {
          return null
        }
      },
      async InsertHDTB(dsHDTB, huonggiao_id) {
        await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hd_thuebao', dsHDTB[0]).then(res => {
          if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail)
            this.$root.toastError(res.message_detail)
        }).catch(e => {
          throw e
        })
        await this.CAPNHAT_BIENDONG(dsHDTB)
        await this.CHUYEN_DL_HDTB_SANG_GIAOPHIEU(dsHDTB, huonggiao_id)
      },
      async Insert_HDTB_KV(ds) {
        for (let item of ds) {
          await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/delete_hdtb_kv', item)
        }
        await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/themmoi_hdtb_kv', ds[0]).then(res => {
          if (res.error_code !== 'BSS-00000000' && res.error_code !== 'BSS-00000204' && res.message_detail)
            this.$root.toastError(res.message_detail)
        })
      },
      async CAPNHAT_BIENDONG(ds) {
        for (let item of ds) {
          await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/capnhat_biendong/${item.HDTB_ID}`).catch(e => {
            throw e
          })
        }
      },
      deleteDangKy_dvk: async function() {
        try {
          let deleteDangKy = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/xoa_dvk?hdtb_dvk_id=${this.hdtb_id}&hdtt_dvk_id=${this.hdtt_id}`)
        } catch (error) {
        }
      }, updateDangKy_dvk: async function() {
        try {
          if (this.dbDichVuThayDoi.length > 0) {
            let aa1 = {}
            let listThemMoi = []
            this.dbDichVuThayDoi.forEach(itemList => {
              aa1 = {}
              Object.keys(itemList).forEach(item => {
                aa1[item.toUpperCase()] = itemList[item]
              })
              listThemMoi.push(aa1)
            })
            await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/themmoi_dichvu_khac', listThemMoi)
          }
        } catch (error) {
        }
      },
      capNhat_DiaChiBC: async function() {
        try {
          let rsCapNhatDC = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/capnhat_diachi_bc_hdtt', {
            diachibc_id: this.diachibc_id,
            hdtt_id: this.hdtt_id,
            pho_id: this.phobc_id,
            phuong_id: this.phuongbc_id,
            quan_id: this.quanbc_id,
            sonha: this.sonhabc,
            diachi: this.txt_diachiBC.Value ? this.txt_diachiBC.Value : '0',
            tinh_id: this.tinhbc_id,
            ap_id: this.apbc_id,
            khu_id: this.khubc_id,
            dacdiem_id: this.dacdiembc_id
          })
        } catch (error) {
        }
      },
      grid_ActionComplete(args) {
        if (args.data != args.previousData) {
          var val = args.data
          var oldval = args.previousData
          if (val.tongtien != oldval.tongtien && val.vat == oldval.vat) {
            val.vat = Math.round(val.tongtien * val.tyle_vat / (100 + val.tyle_vat))
            val.tien = Math.round(val.tongtien - val.vat)
            this.grcDanhSach[args.rowIndex].vat = val.vat
            this.grcDanhSach[args.rowIndex].tien = val.tien
            this.grcDanhSach[args.rowIndex].tongtien = val.tongtien
            this.$forceUpdate()
          } else if (val.tien != oldval.tien && val.vat == oldval.vat) {
            val.vat = Math.round(val.tien * val.tyle_vat / 100)
            val.tongtien = Math.round(val.tien + val.vat)
            this.grcDanhSach[args.rowIndex].vat = val.vat
            this.grcDanhSach[args.rowIndex].tongtien = val.tongtien
            this.grcDanhSach[args.rowIndex].tien = val.tien
            this.$forceUpdate()
          }
          this.$refs.gridKMTT.refresh()
          this.txtVatKM = 0
          this.txtTienKM = 0
          this.txtTongTienKM = 0
          for (let i = 0; i < this.grcDanhSach.length; i++) {
            this.txtVatKM += this.grcDanhSach[i].vat
            this.txtTienKM += this.grcDanhSach[i].tien
            this.txtTongTienKM += this.grcDanhSach[i].tongtien
          }
          this.txtVatKM = Number(this.txtVatKM).toLocaleString().replace('.', ',')
          this.txtTienKM = Number(this.txtTienKM).toLocaleString().replace('.', ',')
          this.txtTongTienKM = Number(this.txtTongTienKM).toLocaleString().replace('.', ',')
          // this.txtTongTienKM = this.addCommas(this.txtTongTienKM)
          this.$forceUpdate()
        }
      },
      btnThemKMTT: function() {
        try {
          this.grcDanhSach = []
          if (this.dsKhoanMucTT.length > 0) {
            if (this.cboKMTT.length == 0) {
              this.$root.toastError('Bạn chưa chọn khoản mục không thể thêm')
              return
            }
            for (let item of this.dsKhoanMucTT.filter(x => this.cboKMTT.indexOf(x.khoanmuctt_id) >= 0)) {
              let dr_ = {}
              dr_.khoanmuctt_id = item.khoanmuctt_id
              dr_.hdtb_id = !this.tsbtnNhapMoi.Enable ? -1 : this.hdtb_id
              dr_.tien = 0
              dr_.vat = 0
              dr_.tongtien = 0
              dr_.loai_id = 1
              dr_.id = item.khoanmuctt_id
              dr_.tentat_kmtt = item.tentat_kmtt
              dr_.tyle_vat = item.tyle_vat
              this.grcDanhSach.push(dr_)
            }
          }
        } catch (e) {
          this.$root.toastError('Có lỗi khi thêm khoản mục')
        }
      },
      nap_ds_kmtt: async function(vloaitb_id, kieuld_id, hdtb_id, kieu) {
        try {
          if (!this.HT_HTHS_KMTT) {
            this.tsbtnCapNhatDB.Visible = true
            this.tsbtnThanhToan.Visible = false
          }
          let dt = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_khoan_muc_tt_lap_dat?kieuld_id=${kieuld_id}&loaitb_id=${vloaitb_id}`)
          if (dt.data.length > 0) {
            this.dsKhoanMucTT = dt.data
          }
          let ds = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_khoan_muc_tt?hdtb_id=${hdtb_id}`)
          if (ds.data.length == 0) return
          if (kieuld_id == KIEULAPDAT.THAYDOI_TT_THUEBAO || kieuld_id == KIEULAPDAT.THAYDOI_TT_DV_CNTT || kieuld_id == KIEULAPDAT.GIAHAN_DV_CNTT) {
            if (!this.HT_HTHS_KMTT) {
              this.tsbtnThanhToan.Visible = true
              this.tsbtnCapNhatDB.Visible = false
            }
          }
          //this.dsKhoanMucTT = ds.data;
        } catch (error) {
        }
      },
      async CHUYEN_DL_HDTB_SANG_GIAOPHIEU(dsHDTB, huonggiao_id) {
        for (let item of this.dsHDTB) {
          await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/chuyen_dl_hdtb_sang_giaophieu', {
            hdtb_id: item.HDTB_ID,
            huonggiao_id: huonggiao_id
          }).catch(e => {
            throw e
          })
        }
      },
      Clear() {
        console.log('Clear')
        this.co_sua_km_tc = false
        this.chk_ngayHT.Visible = false
        this.dtp_ngayHT.Visible = false
        this.txt_nhanVienQL.Value = ''
        this.nhanvien_ql_am_id = 0
        this.pt_doanhthu = 0
        this.pt_diem_tn = 0
        this.txt_mstKH.Value = ''
        this.hdmig_id = 0
        this.mig_id = 0
        this.txt_maIG.Value = ''
        this.tocdo_id = 0
        this.muccuoc_id = 0
        this.txt_ngaysudung.Value = ''
        this.txt_diachiNDD.Value = ''
        this.txt_ngayhethan.Value = ''
        this.txt_trangthaiTB.Value = ''
        this.lbl_trangthaiDD = 'Trạng thái mã đại diện'
        this.txt_ngaycap.Value = ''
        this.txt_soGT.Value = ''
        //txtGiayToKem.Text = "";
        this.txt_noicap.Value = ''
        this.txt_ngaysn.Value = ''
        this.email_kh = ''
        this.fax_kh = ''
        this.sodt_kh = ''
        this.gioitinh_Mytv = ''
        this.txt_telKH.Value = ''
        this.txt_faxKH.Value = ''
        this.txt_emailKH.Value = ''
        this.password = ''
        this.txt_stk_TT.Value = ''
        this.txt_chu_TK.Value = ''
        this.txt_ghiChuTT.Value = ''
        this.txt_emailTT.Value = ''
        this.txt_soDT_TT.Value = ''
        this.txt_ngaysnTB.Value = ''
        this.tuyenthu_id = 0
        this.nhanvientc_id = 0
        this.dsMaTBDD = []
        this.txt_maTuyen.Value = ''
        this.txt_maNVTC.Value = ''
        this.txt_diachiCT.Value = ''
        this.txt_diachiLD.Value = ''
        this.loaikh_id = 1
        this.hdkh_id = 0
        this.txt_magiaodich.Value = ''
        this.txt_mahopdong.Value = ''
        this.txt_maKH.Value = ''
        this.khachhang_id = 0
        this.txt_tenKH.Value = ''
        this.txt_diachiKH.Value = ''
        this.tinhkh_id = 0
        this.quankh_id = 0
        this.phuongkh_id = 0
        this.dacdiemkh_id = 0
        this.phokh_id = 0
        this.apkh_id = 0
        this.khukh_id = 0
        this.sonhakh = ''
        this.dtp_ngayYc.Value = new Date()
        this.dtp_ngaylapHd.Value = new Date()
        this.hdtt_id = 0
        this.httt_id_cu = -1
        this.txt_maTT.Value = ''
        this.thanhtoan_id = 0
        this.txt_tenTT.Value = ''
        this.txt_diachiTT.Value = ''
        this.tinhtt_id = 0
        this.quantt_id = 0
        this.phuongtt_id = 0
        this.dacdiemtt_id = 0
        this.phott_id = 0
        this.aptt_id = 0
        this.khutt_id = 0
        this.sonhatt = ''
        //diachi bc
        this.txt_diachiBC.Value = ''
        this.tinhbc_id = 0
        this.quanbc_id = 0
        this.phuongbc_id = 0
        this.dacdiembc_id = 0
        this.phobc_id = 0
        this.apbc_id = 0
        this.sonhabc = ''
        this.txt_mst_TT.Value = ''
        this.cbo_HTTT.Value = 0
        this.hdtb_id = 0
        this.thuebao_id = 0
        this.txt_tenthuebao.Value = ''
        this.txt_diachiTB.Value = ''
        this.tinhtb_id = 0
        this.quantb_id = 0
        this.phuongtb_id = 0
        this.dacdiemtb_id = 0
        this.photb_id = 0
        this.aptb_id = 0
        this.khutb_id = 0
        this.sonhatb = ''
        this.block = ''
        this.tang = ''
        this.phong = ''
        this.mota = ''
        this.cbo_doituong_id.Value = 0
        // this.dvkhaclist = []
        this.dsHDTB = []
        this.dsDK_DVK = []
        // this.$refs.txt_maTB.focus()
        this.dsMucCuocTB = []
        this.dsCTKM = []
        this.dsCTKMThayDoi = []
        this.cbo_maDD.Value = ''
        this.dsMaDaiDien = []
        this.txt_khuvuc.Value = ''
        this.khuvuc_id = 0
        this.Clear_CCBS()
        this.dsHDTB_KV = []
        this.dsHDKH_Sub = []
        this.items = []
        //grcDanhSach.DataSource = null;
        // grcTT.DataSource = null;
        // popupTT.EditValue = null;
        if (!this.HT_HTHS_KMTT) {
          this.tsbtnCapNhatDB.Visible = true
          this.tsbtnThanhToan.Visible = false
        }
        this.dsHDTB_CD = []
        this.dsHDTB_GP = []
        this.dsHDTB_DD = []
        this.dsHDTB_ADSL = []
        this.dsHDTB_IMS = []
        this.dsHDTB_MEWAN = []
        this.dsHDTB_TSL = []
        this.dsHDTT = []
        this.dsHDTT_NV = []
        this.dsHDKH = []
        this.dbDichVuThayDoi = []
        // Reset Tab Thuê Bao
        //this.listbox.dichvu_vt.value = 0
        //this.listbox.kieu_ld.value = 0
        //this.listbox.loaihinh_tb.value = 0
        //this.listbox.congnghe.value = 0;
        //this.txt_maTB.Value = ''
        this.thuebao.tsl.ma_tb = ''
        this.thuebao.mgwan.ma_tb =
        this.thuebao.tsl.txtMaHD_TTKD_TSL = ''
        this.thuebao.mgwan.mahdttkd = ''
        this.thuebao.tsl.ten_tb = ''
        this.thuebao.mgwan.ten_tb = ''
        this.thuebao.tsl.diachi_tb = ''
        this.thuebao.mgwan.diachi_tb = ''
        this.thuebao.tsl.diachi_ld = ''
        this.thuebao.mgwan.diachi_ld = ''
        this.loaisowan = 0;
        this.thuebao.mgwan.huongkn = ''
        this.diachiKN.DIACHI = ''
        this.listbox.tinh_kn.value = ''
        this.listbox.loaikenh.valuetsl = 0
        this.listbox.loaikenh.value = 0
        this.listbox.thoihan.valuetsl = 0
        this.listbox.thoihan.valuewan = 0
        this.listbox.phanloai_dv.value = 0
        this.listbox.ht_phantach_tocdo.value = 0
        this.listbox.trangbi.valuetsl = 0
        this.listbox.trangbi.valuewan = TRANGBI.VIENTHONG_TRANGBI
        this.listbox.tocdokenh.valuenix = 0
        this.listbox.chu_quan.valuetsl = 0
        this.listbox.chu_quan.valuewan = 0
        this.listbox.tocdokenh.valuecir = 0
        this.listbox.tocdokenh.valuepir = 0
        this.thuebao.mgwan.soluongip = 0
        this.listbox.tocdokenh.valueisp = 0
        this.diachiDau.DIACHI = ''
        this.diachiCuoi.DIACHI = ''
        this.listbox.loaicap.valuedau = ''
        this.listbox.loaicap.valuecuoi = ''
        this.thuebao.vtn = ''
        this.thuebao.erp = ''
        this.thuebao.crm = ''
      },
      btnDiaChiNDD_Click() {
        this.diachi = {}
        this.diachi.tinh_id = this.tinhndd_id
        this.diachi.quan_id = this.quanndd_id
        this.diachi.phuong_id = this.phuongndd_id
        this.diachi.pho_id = this.phondd_id
        this.diachi.dacdiem_id = this.dacdiemndd_id
        this.diachi.ap_id = this.ngondd_id
        this.diachi.khu_id = this.khukh_id
        this.diachi.so_nha = this.sonhandd
        this.diachi.diachi = this.txt_diachiNDD.Value
        this.diachi_flag = 'DIACHI_NDD'
        this.isStrictMode = true
        this.$bvModal.show('popupDiaChi')
      },
      btnDiaChiKH_Click() {
        this.diachi = {}
        this.diachi.tinh_id = this.tinhkh_id
        this.diachi.quan_id = this.quankh_id
        this.diachi.phuong_id = this.phuongkh_id
        this.diachi.pho_id = this.phokh_id
        this.diachi.dacdiem_id = this.dacdiemkh_id
        this.diachi.ap_id = this.apkh_id
        this.diachi.khu_id = this.khukh_id
        this.diachi.so_nha = this.sonhakh
        this.diachi.diachi = this.txt_diachiKH.Value
        this.diachi_flag = 'DIACHI_KH'
        this.isStrictMode = true
        this.$bvModal.show('popupDiaChi')
      },
      btnDiaChiTT() {
        this.diachi = {}
        this.diachi.tinh_id = this.tinhtt_id
        this.diachi.quan_id = this.quantt_id
        this.diachi.phuong_id = this.phuongtt_id
        this.diachi.pho_id = this.phott_id
        this.diachi.dacdiem_id = this.dacdiemtt_id
        this.diachi.ap_id = this.aptt_id
        this.diachi.khu_id = this.khutt_id
        this.diachi.so_nha = this.sonhatt
        this.diachi.diachi = this.txt_diachiTT.Value
        this.diachi_flag = 'DIACHI_TT'
        this.isStrictMode = true
        this.$bvModal.show('popupDiaChi')
      },
      btnDiaChiCT() {
        this.diachi = {}
        this.diachi.tinh_id = this.tinhtt_id
        this.diachi.quan_id = this.quantt_id
        this.diachi.phuong_id = this.phuongtt_id
        this.diachi.pho_id = this.phott_id
        this.diachi.dacdiem_id = this.dacdiemtt_id
        this.diachi.ap_id = this.aptt_id
        this.diachi.khu_id = this.khutt_id
        this.diachi.so_nha = this.sonhatt
        this.diachi.diachi = this.txt_diachiCT.Value
        this.diachi_flag = 'DIACHI_CT'
        this.isStrictMode = false
        this.$bvModal.show('popupDiaChi')
      },
      btnDiaChiBC() {
        this.diachi = {}
        this.diachi.tinh_id = this.tinhbc_id
        this.diachi.quan_id = this.quanbc_id
        this.diachi.phuong_id = this.phuongbc_id
        this.diachi.pho_id = this.phobc_id
        this.diachi.dacdiem_id = this.dacdiembc_id
        this.diachi.ap_id = this.apbc_id
        this.diachi.khu_id = this.khubc_id
        this.diachi.so_nha = this.sonhabc
        this.diachi.diachi = this.txt_diachiBC.Value
        this.diachi_flag = 'DIACHI_BC'
        this.isStrictMode = true
        this.$bvModal.show('popupDiaChi')
      },
      btnDiaChiTB_Click() {
        this.showThongTinThem = false
        this.diachi = {}
        this.diachi.tinh_id = this.tinhtb_id
        this.diachi.quan_id = this.quantb_id
        this.diachi.phuong_id = this.phuongtb_id
        this.diachi.pho_id = this.photb_id
        this.diachi.dacdiem_id = this.dacdiemtb_id
        this.diachi.ap_id = this.aptb_id
        this.diachi.khu_id = this.khutb_id
        this.diachi.so_nha = this.sonhatb
        this.diachi.block = this.block
        this.diachi.tang = this.tang
        this.diachi.sophong = this.phong
        this.diachi.motathem = this.mota
        // this.diachi.diachi = this.txt_diachiTB.Value
        this.diachi.diachi = this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.thuebao.tsl.diachi_tb : this.thuebao.mgwan.diachi_tb // add new
        this.diachi_flag = 'DIACHI_TB'
        this.isStrictMode = true
        this.$bvModal.show('popupDiaChi')
      },
      btnDiaChiLD_Click() {
        this.showThongTinThem = false
        this.diachi = {}
        this.diachi.tinh_id = this.tinhld_id
        this.diachi.quan_id = this.quanld_id
        this.diachi.phuong_id = this.phuongld_id
        this.diachi.pho_id = this.phold_id
        this.diachi.dacdiem_id = this.dacdiemld_id
        this.diachi.ap_id = this.apld_id
        this.diachi.khu_id = this.khuld_id
        this.diachi.so_nha = this.sonhald
        this.diachi.block = this.block_ld
        this.diachi.tang = this.tang_ld
        this.diachi.sophong = this.sophong_ld
        this.diachi.motathem = this.motathem_ld
        this.diachi.diachi = this.txt_diachiLD.Value
        //this.diachi.diachi = this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.thuebao.tsl.diachi_ld : this.thuebao.mgwan.diachi_ld // add new
        this.diachi_flag = 'DIACHI_LD'
        this.isStrictMode = true
        this.$bvModal.show('popupDiaChi')
      },
      btnNganhNghe_Click() {
        this.objChiTietNganhNghe = {}
        this.objChiTietNganhNghe.nganhnghe_id = this.cbo_nganhngheloc.Value
        this.objChiTietNganhNghe.TNC1_ID = this.TNC1_ID
        this.objChiTietNganhNghe.TNC2_ID = this.TNC2_ID
        this.objChiTietNganhNghe.TNC3_ID = this.TNC3_ID
        this.objChiTietNganhNghe.TNC4_ID = this.TNC4_ID
        this.$bvModal.show('popupChiTietNganhNghe')
      },
      getTNC(objTNC) {
        this.TNC1_ID = objTNC.TNC1_ID
        this.TNC2_ID = objTNC.TNC2_ID
        this.TNC3_ID = objTNC.TNC3_ID
        this.TNC4_ID = objTNC.TNC4_ID
        this.nganhnghe_id = objTNC.nganhnghe_id
        this.cbo_nganhngheloc.Value = this.nganhnghe_id
      },
      btnMaNVTC_Click() {
        try {
          this.loaiPopupTraCuuNVTC = 0
          this.objTraCuuNVTC = {}
          if (this.quanbc_id != 0) {
            this.objTraCuuNVTC.QUAN_ID = this.quanbc_id
            this.objTraCuuNVTC.PHUONG_ID = this.phuongbc_id
            this.objTraCuuNVTC.PHO_ID = this.phobc_id
            this.objTraCuuNVTC.KHU_ID = this.khubc_id
            this.objTraCuuNVTC.AP_ID = this.apbc_id
            this.objTraCuuNVTC.DACDIEM_ID = this.dacdiembc_id
          } else {
            this.objTraCuuNVTC.QUAN_ID = this.quantt_id
            this.objTraCuuNVTC.PHUONG_ID = this.phuongtt_id
            this.objTraCuuNVTC.PHO_ID = this.phott_id
            this.objTraCuuNVTC.KHU_ID = this.khutt_id
            this.objTraCuuNVTC.AP_ID = this.aptt_id
            this.objTraCuuNVTC.DACDIEM_ID = this.dacdiemtt_id
          }
          this.objTraCuuNVTC.NHANVIENTC_ID = this.nhanvientc_id
          this.objTraCuuNVTC.TUYENTHU_ID = this.tuyenthu_id
          this.objTraCuuNVTC.DONVIQL_ID = this.cbo_donviQL_id.Value
          if (this.tuyenthu_httt == 1 && this.cbo_HTTT.Value) {
            this.objTraCuuNVTC.HTTT_ID = this.cbo_HTTT.Value
          }
          this.objTraCuuNVTC.KT_KYLAI = true
          this.diachiLD = this.objTraCuuNVTC
          this.$bvModal.show('dlgTimKiemNVTC')
        } catch (error) {
        }
      },
      getHDKH_SUB(ds) {
        this.dsHDKH_Sub = ds
      },
      LoadThongTin_DV_CNTT: async function() {
        await this.Init_Properties(this.loaitb_id, 'HDTB_CNTT', '00000')
      }, Init_Properties: async function(loaiTbi, tableName, optList) {
        let rs = await this.$root.context.post('/web-cabman/daunoi-thietbi/lay-ds-thuoctinh', {
          loai_tbi: loaiTbi,
          opt_list: optList,
          ten_bang: tableName
        })
        this.items = []
        this.ItemList = []
        this.ItemList = rs.data
      },
      async KIEMTRA_OPEN_SYNC_VINA(keyname) {
        let kq = await this.$root.context.get('web-hopdong/hoanthienhschuyenquyen/kiemtra_open_sync_vina', {
          keyname: keyname
        })
        if (kq.data && kq.data.length > 0) {
          return kq.data[0].KEY_VALUE
        } else {
          return false
        }
      },
      async capnhat_status_hdtb(data) {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/capnhat_status_hdtb`, data)
        return response.data
      },
      async cnNvth_TrangThaiPhieu_NgayKm(data) {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/cn_nvth_tt_phieu_ngay_km`, data)
        return response.data.KQ
      },
      async updateNgayHt(data) {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/update_ngay_ht`, data)
        return response.data.KQ
      },
      async hoanThienHsBienDongKhac(data) {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/hoanthien_hs_biendong_khac`, data)
        return response.data.KQ
      },
      async hoanThienHsSuaKm(data) {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/hoanthien_hs_sua_km`, data)
        return response.data.KQ
      },
      async giaoPhieuTdKhiHc(data) {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/giaophieu_td_khi_hc`, data)
        if (response.data.KQ == '0') {
          this.$root.toastError('Lỗi Tạo phiếu giao tự động khi hoàn công: ' + response.data.MESSAGE)
          throw response.data.MESSAGE
        }
        return response.data.KQ
      },
      async logAction(action_name, key_parameter, note) {
        let ip = await this.getIp()
        let data = {
          user_name: this.$root.token.getUserName(),
          computer_name: ip,
          action_name: action_name,
          key_parameter: key_parameter,
          server_name: ip,
          note: note
        }
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/log_action`, data)
        return response.data.KQ
      },
      async getIp() {
        try {
          const response = await fetch('https://jsonip.com/')
          const data = await response.json()
          return data.ip
        } catch (e) {
          return ''
        }
      },
      async kiemTraKm2731(data) {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_km_2731`, data)
        return response.data.KQ
      },
      async layDauSoIms() {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_dauso_ims`)
        return response.data.DAUSO_IMS
      },
      async layDsDiaChiTheoHdtbId(hdtb_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_diachi_theo_hdtb_id`, { hdtb_id })
        return response.data
      },
      async layDsHopDongTtTheoHdttId(hdtt_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_hopdong_tt_theo_hdtt_id`, { hdtt_id })
        return response.data
      },
      async kiemTraThayDoiDvk(hdtb_id, dvk_id) {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_thaydoi_dvk`, {
          hdtb_id,
          dvk_id
        })
        return response.data.KQ
      },
      async layThongTinVasc() {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_tt_vasc`)
        return response.data
      },
      async layThongTinDbkhSub(khachhang_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_tt_dbkh_sub?khachhang_id=${khachhang_id}`)
        return response.data
      },
      async layThongTinTieuNganhC1(tnc1_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_tt_tieunganh_c1?tnc1_id=${tnc1_id}`)
        return response.data
      },
      async changeSubscriber() {
        let Name = this.chuanHoa_XML(this.txt_tenKH.Value) //tên KH
        let Birthday = this.txt_ngaysn.Value
        let Sex = parseInt(this.gioitinh_Mytv) != 1 ? '0' : '1' //Giới tính : Nam --> 0, Nữ --> 1; không giới tính --> 0
        let FirstAdd = this.chuanHoa_XML(this.txt_diachiKH.Value) //địa chỉ KH diachi_kh_Mytv
        let SecondAdd = this.chuanHoa_XML(this.txt_diachiLD.Value) //Dịa chỉ Lđặt diachi_ld_Mytv
        let IDNo = this.chuanHoa_XML(this.txt_soGT.Value) //Số GT khách hàng so_gt_Mytv
        // nếu điện thoại lhệ> 8 -->tellno = "" & cellno = điện thoại liên hệ  và ngược lại
        let TelNo = ''
        let CellNo = ''
        if (this.sodt_kh.length > 8) // Di dong
        {
          TelNo = ''
          CellNo = this.sodt_kh
        } else {
          TelNo = this.sodt_kh
          CellNo = ''
        }
        let Email = this.chuanHoa_XML(this.email_kh) //Email KH
        let YM = '' // trống
        let Website = '' //trống
        let FaxNo = this.fax_kh //số Fax KH
        let IPTVAccount = this.chuanHoa_XML(this.txt_maTB.Value) //Mã TB dsHDTB
        try {
          await vascService.changeSubscriber(this, Name, Birthday, Sex, FirstAdd, SecondAdd, IDNo, TelNo, CellNo, Email, YM, Website, FaxNo, IPTVAccount)
        } catch (err) {
          this.$root.toastError(err)
          return 1
        }
        return 0
      },
      chuanHoa_XML(Xau) {
        let result = ''
        if (Xau) {
          result = Xau.replaceAll('&', '&amp;')
          result = result.replaceAll('<', '&lt;')
          result = result.replaceAll('>', '&gt;')
          result = result.replaceAll('\'', '&apos;')
          result = result.replaceAll('"', '&quot;')
        }
        return result
      },
      async ktDkCpMytvMoi(hdtb_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/kt_dk_cp_mytv_moi?hdtb_id=${hdtb_id}`)
        return response.data
      },
      async layTtTbChaTbId(thuebao_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_tt_tb_cha_tbid?thuebao_id=${thuebao_id}`)
        return response.data
      },
      async layDsDbTbTheoTbId(thuebao_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_dbtb_theo_tbid?thuebao_id=${thuebao_id}`)
        return response.data
      },
      async layDsHopDongTbTheoHdkhId(hdkh_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_hopdong_tb_theo_hdkh_id?hdkh_id=${hdkh_id}`)
        return response.data
      },
      async kiemTraTdTkChinh(hdtb_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_td_tkchinh?hdtb_id=${hdtb_id}`)
        return !response.data ? false : response.data.result
      },
      async thayDoiDoiTuongVisa(doituong_id) {
        let activeDate = this.ddmmyyyy(new Date())
        let account = this.chuanHoa_XML(this.txt_maTB.Value)
        let cusType = await this.layIdDoiTuongVdc(doituong_id)
        cusType = !cusType ? -1 : cusType.DOITUONG_ID_VDC
        return await vdcService.ChangeCustomerType(account, activeDate, cusType, this)
      },
      async layIdDoiTuongVdc(doituong_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_id_doituong_vdc?doituong_id=${doituong_id}`)
        return response.data
      },
      async kiemTraDoiTuongVdc(hdtb_id, loaihinh_tb) {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_doituong_vdc`, {
          hdtb_id: hdtb_id,
          loaihinhtb_id: loaihinh_tb
        })
        return !response.data ? false : response.data.result
      },
      async layThongTinNguoiDung() {
        try {
          let rs = await this.$root.context.get('/web-hopdong/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung')
          return !rs.data ? null : rs.data[0]
        } catch (error) {
          return null
        }
      },
      async lay_tt_thanh_toan_neo(ma_tt) {
        try {
          let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_tt_thanh_toan_neo?ma_tt=${ma_tt}`)
          return response.data
        } catch (error) {
          return null
        }
      },
      async lay_tt_nhanvien_tc(manv_tc) {
        try {
          let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_tt_nhanvien_tc?manv_tc=${manv_tc}`)
          return response.data
        } catch (error) {
          return null
        }
      },
      async lay_tt_loai_gt(loaigt_id) {
        try {
          let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_tt_loai_gt?loaigt_id=${loaigt_id}`)
          return response.data
        } catch (error) {
          return null
        }
      },
      async lay_tt_loai_kh(loaikh_id) {
        try {
          let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_tt_loai_kh?loaikh_id=${loaikh_id}`)
          return response.data
        } catch (error) {
          return null
        }
      },
      async lay_tt_hinhthuc_tt(httt_id) {
        try {
          let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_tt_hinhthuc_tt?httt_id=${httt_id}`)
          return response.data
        } catch (error) {
          return null
        }
      },
      async lay_tt_tieunganh_c2(tnc2_id) {
        try {
          let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_tt_tieunganh_c2?tnc2_id=${tnc2_id}`)
          return response.data
        } catch (error) {
          return null
        }
      },
      async THAYDOI_THONGTIN_KHACHHANG(dsDBKH) {
        let status = false
        let userInfo = await this.layThongTinNguoiDung()
        let PSUSERID = !userInfo ? null : userInfo.user_neo
        if (!PSUSERID) {
          this.$root.toastError('Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        let PSMAKHCU = this.txt_maTT.Value
        let PSMA_KH = this.txt_maTT.Value
        let PSMA_KH_NEO = await this.lay_tt_thanh_toan_neo(PSMA_KH)
        if (!PSMA_KH_NEO || !PSMA_KH_NEO.ma_tt_neo) {
          let msg = `Không tìm thấy dữ liệu ma_tt_neo ứng với ma_tt = ${this.txt_maTT.Value} trong bảng ghep_matt của KH , liên hệ admin để bổ sung dữ liệu`
          this.$root.toastError(msg)
          throw 'error'
        }
        let PSMACQ = null
        let PSCOQUAN = null
        let PSTENTT = this.txt_tenTT.Value
        let PSSODAIDIEN = '84' + this.cbo_maDD.Value
        if (PSSODAIDIEN == '84') {
          this.$root.toastError('Không lấy được thông tin số đại diện. Bạn hãy kiểm tra lại')
          throw 'error'
        }
        if (!dsDBKH || dsDBKH.length == 0) {
          this.$root.toastError('Không lấy được dữ liệu dưới tỉnh. Bạn hẫy ấn nút cập nhật và thử lại')
          throw 'error'
        }
        let PSNGUOIDAIDIEN = dsDBKH[0]['NGUOI_DD']
        let PSDIENTHOAILH = dsDBKH[0]['SO_DT']
        let PSPHAI = dsDBKH[0]['GIOITINH']
        let PSEMAIL = dsDBKH[0]['EMAIL']
        let PSNGAYSINH = !this.txt_ngaysn.Value ? '' : this.txt_ngaysn.Value
        let PSSOGIAYTO = this.txt_soGT.Value
        let PSNOICAPGT = this.txt_noicap.Value
        let PSNGAYCAPGT = !this.txt_ngaycap.Value ? '' : this.txt_ngaycap.Value
        let PSSOGT1 = ''
        let PSNOICAPGT1 = ''
        let PSNGAYCAPGT1 = ''
        let diaChiNeo = await this.layDiaChiNeo({
          quan_id: this.quankh_id,
          phuong_id: this.phuongkh_id,
          pho_id: this.phokh_id
        })
        let PSUANCT_ID = !diaChiNeo.QUAN_ID_NEO ? null : diaChiNeo.QUAN_ID_NEO
        if (!!this.quankh_id && !PSUANCT_ID) {
          this.$root.toastError('Thông tin về Quận/Huyện của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        let PSHUONGCT_ID = !diaChiNeo.PHUONG_ID_NEO ? null : diaChiNeo.PHUONG_ID_NEO
        if (!PSHUONGCT_ID && !!this.phuongkh_id) {
          this.$root.toastError('Thông tin về Phường/Xã của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        let PSHOCT_ID = !diaChiNeo.PHO_ID_NEO ? null : diaChiNeo.PHO_ID_NEO
        if (!PSHOCT_ID && !!this.phuongkh_id && !!this.phokh_id) {
          this.$root.toastError('Thông tin về Phố/Thôn/Xóm của Địa chỉ khách hàng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        let PSONHACT_ID = this.sonhakh
        let PSDIACHICT = this.txt_diachiKH.Value
        let PSMSTHUE = this.txt_mst_TT.Value
        let PSTAIKHOAN = this.txt_stk_TT.Value
        diaChiNeo = await this.layDiaChiNeo({
          quan_id: this.quantt_id,
          phuong_id: this.phuongtt_id,
          pho_id: this.phott_id
        })
        let PSUANTT_ID = !diaChiNeo.QUAN_ID_NEO ? null : diaChiNeo.QUAN_ID_NEO
        if (!PSUANTT_ID && !!this.quantt_id) {
          this.$root.toastError('Thông tin về Quận/Huyện của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        let PSHUONGTT_ID = !diaChiNeo.PHUONG_ID_NEO ? null : diaChiNeo.PHUONG_ID_NEO
        if (!PSHUONGTT_ID && !!this.phuongtt_id && !!this.quantt_id) {
          this.$root.toastError('Thông tin về Phường/Xã của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        let PSHOTT_ID = !diaChiNeo.PHO_ID_NEO ? null : diaChiNeo.PHO_ID_NEO
        if (!PSHOTT_ID && !!this.phott_id && !!this.phuongtt_id) {
          this.$root.toastError('Thông tin Phố/Thôn/Xóm của Địa chỉ Thanh toán chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        let PSONHATT_ID = this.sonhatt
        let PSDIACHITT = this.txt_diachiTT.Value
        let PSDANGKYTV = '0'
        let PSDANGKYDB = '1' //Bổ sung sau
        let PSKHRR = '0'
        let PSNGUOICN = !userInfo ? null : userInfo.user_neo
        let PSMAYCN = await this.getIp()
        let PSMANV = this.txt_maNVTC.Value
        //Lấy tên nhân viên thu cước
        let ten_nv = ''
        if (!!PSMANV) {
          let nhanVienInfo = await this.lay_tt_nhanvien_tc(PSMANV)
          ten_nv = !nhanVienInfo ? '' : nhanVienInfo.ten_nv
        }
        let PSTENNV = ten_nv
        let PSGHICHU = this.txt_ghiChuTT.Value
        let loaiGtInfo = await this.lay_tt_loai_gt(this.cbo_giaytoKH.Value)
        let PSLOAIGTID = loaiGtInfo.loaigt_id_neo
        //danh bạ
        let PSLOAIGTID1 = '1'
        let loaiKhInfo = await this.lay_tt_loai_kh(this.cbo_loaiKH.Value)
        let PSLOAIKH = loaiKhInfo.loaikh_id_neo
        let hinhThucTtInfo = await this.lay_tt_hinhthuc_tt(this.cbo_HTTT.Value)
        let PSDIADIEMTT = hinhThucTtInfo.httt_id_neo
        let PSNGANHANG = ''
        let PSCHUYENKHOAN = '0'
        let PSDONVIQL = '1'
        if (PSDONVIQL == '-1') {
          this.$root.toastError('Thông tin về đơn vị quản lý chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        let PSMABC = await this.layIdDonViNeo(this.cbo_donviQL_id.Value)
        if (!PSMABC) {
          this.$root.toastError('Thông tin về mã bưu cục chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        let PSKHLON = '1'
        let PSUUTIEN = '0'
        let tieuNgayC2Info = await this.lay_tt_tieunganh_c2(this.TNC2_ID)
        let PSNGANHNGHE = tieuNgayC2Info.tnc2_id_neo
        let PSDOIDVQLTB = '0'
        let PSMAT = this.txt_maTuyen.Value
        let PSTENT = PSMANV
        let PSKYTEN = null
        let PSNGUOI_GT = null
        let PSMA_TUYEN = null
        let PSTEN_TUYEN = null
        let PSKH_LON = '1'
        let PSMA_QHNS = null
        let PSNGANHANG_ID = '0'
        let PSLYDO_ANH = '1' // khong can upload.
        let PSGIOITINH = null
        let ip = await this.getIp()
        if (!!this.vgioitinh) {
          PSGIOITINH = this.vgioitinh == 'male' ? '0' : '1'
        }
        let vma_tinh = this.$root.token.getMaTinh()
        try {
          let kq = await ccbsService.ts_hoanthien_hd_cn_kh(
            this, ip, PSMAT, PSDANGKYTV, PSHOCT_ID, PSUSERID, PSTAIKHOAN, PSKH_LON, PSGHICHU, PSHUONGTT_ID,
            PSMA_QHNS, PSNOICAPGT1, PSTENNV, PSNGANHANG_ID, PSKYTEN, PSUANTT_ID, PSDANGKYDB, PSLOAIKH, PSDONVIQL,
            PSONHATT_ID, PSNGANHNGHE, PSSOGT1, PSHOTT_ID, PSUUTIEN, PSLYDO_ANH, PSNGAYCAPGT, PSDIACHITT, PSUANCT_ID,
            vma_tinh, PSGIOITINH, PSMA_KH_NEO.ma_tt_neo, PSMSTHUE, PSSOGIAYTO, PSNGAYCAPGT1, PSDIACHICT, PSSODAIDIEN, PSDIENTHOAILH,
            PSMACQ, PSTENTT, PSDONVIQL, PSNGUOIDAIDIEN, PSHUONGTT_ID, PSONHACT_ID, PSEMAIL, PSMABC, PSNOICAPGT,
            PSDIADIEMTT, PSTEN_TUYEN, PSMA_KH_NEO.ma_tt_neo, PSLOAIGTID, PSNGUOI_GT, PSLOAIGTID1, PSNGAYSINH, PSKHRR, PSCOQUAN,
            PSCHUYENKHOAN, PSMANV
          )
          if (kq.result) {
            return
          } else {
            this.$root.toastError('Có lỗi khi thực hiện: ' + result)
            //throw "error";
            return
          }
        } catch (err) {
          this.$root.toastError('Có lỗi khi thực hiện: ' + err.response.data.message)
          throw 'error'
        }
      },
      async THAYDOI_THONGTIN_THUEBAO(dsHDTB) {
        let userInfo = await this.layThongTinNguoiDung()
        let PSUSERID = !userInfo ? null : userInfo.user_neo
        if (!PSUSERID) {
          this.$root.toastError('Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        if (!dsHDTB || dsHDTB.length == 0) {
          this.$root.toastError('Không lấy được dữ liệu dưới tỉnh. Bạn hẫy ấn nút cập nhật và thử lại')
          throw 'error'
        }
        let ds_TBDD = await this.layTtTbdd(dsHDTB[0].MA_TB)
        let PSSOMAY = `84${dsHDTB[0].MA_TB}`
        let PSMA_KH = !this.txt_maTT.Value ? '' : this.txt_maTT.Value.trim()
        let PSTEN_TB = dsHDTB[0].TEN_TB
        let PSTEN_DB = dsHDTB[0].TEN_TB
        let PSCUOC_TB = '44545'
        let PSTYLE_VAT = '0.1'
        let PSINCHITIET = '0'
        let kieuLd = parseInt(this.cbo_kieuld_id.Value)
        if (kieuLd == KIEULAPDAT.THAYDOI_TT_DICHVU_KHAC) {
          for (let i = 0; i < this.dsDK_DVK.length; i++) {
            let dkDvk = this.dsDK_DVK[i]
            if ((parseInt(dkDvk.dvkhac_id) == 2 || parseInt(dkDvk.dvkhac_id) == 5) && parseInt(dkDvk.kieu_yc) == 1) {
              PSINCHITIET = '1'
              break
            }
          }
          //Nếu ko đăng ký gì, kiểm tra xem có sử dụng in chi tiết ko
          if (this.dsDK_DVK.length <= 0) {
            let response = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_sudung_dvk?dbId=${this.thuebao_id}&loaiId=1`)
            let dsSDDVK = response.data
            for (let i = 0; i < dsSDDVK.length; i++) {
              let sdDvk = dsSDDVK[i]
              if (parseInt(sdDvk.dvKhacId) == 2 || parseInt(sdDvk.DVKHACdvKhacId_ID) == 5) {
                PSINCHITIET = '1'
                break
              }
            }
          }
        } else {
          let response = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_sudung_dvk?dbId=${this.thuebao_id}&loaiId=1`)
          let dsSDDVK = response.data
          for (let i = 0; i < dsSDDVK.length; i++) {
            let sdDvk = dsSDDVK[i]
            if (parseInt(sdDvk.dvKhacId) == 2 || parseInt(sdDvk.dvKhacId) == 5) {
              PSINCHITIET = '1'
              break
            }
          }
        }
        let PSTRACUU_DB = '1'
        let PSTHUTU_IN = '1'
        let ngay_sd = await this.layNgaySdCuaThueBao(dsHDTB[0].THUEBAO_ID)
        if (!ngay_sd) {
          this.$root.toastError(`Không có thông tin về ngày sử dụng của thuê bao ${dsHDTB[0].MA_TB}\n Bạn không thể đồng bộ dữ liệu lên Vinaphone!`)
          throw 'error'
        }
        let PSNGAY_LD = ngay_sd
        let PSNGAY_SN = ''
        let PSNGAY_THUHOI = ''
        let PSKHACHHANG_CU = !this.txt_maTT.Value ? '' : this.txt_maTT.Value.trim()
        let diaChiNeo = await this.layDiaChiNeo({
          quan_id: this.quantb_id,
          phuong_id: this.phuongtb_id,
          pho_id: this.photb_id
        })
        let PSQUAN_ID = diaChiNeo.QUAN_ID_NEO
        if (!PSQUAN_ID && !!this.quantb_id) {
          this.$root.toastError('Thông tin về Quận/Huyện của địa chỉ thuê bao chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        } else if (!PSQUAN_ID) {
          PSQUAN_ID = ''
        }
        let PSPHUONG_ID = diaChiNeo.PHUONG_ID_NEO
        if (!PSPHUONG_ID && !!this.phuongtb_id && !!this.quantb_id) {
          this.$root.toastError('Thông tin về phường của địa chỉ thuê bao chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        } else if (!PSPHUONG_ID) {
          PSPHUONG_ID = ''
        }
        let PSPHO_ID = diaChiNeo.PHO_ID_NEO
        if (!PSPHO_ID && !!this.photb_id && !!this.phuongtb_id) {
          this.$root.toastError('Thông tin về Phố/Thôn/Xóm của địa chỉ thuê bao chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        } else if (!PSPHO_ID) {
          PSPHO_ID = ''
        }
        let PSSO_NHA = this.sonhatb
        let PSDIACHI = !this.txt_diachiTB.Value ? '' : this.txt_diachiTB.Value.trim()
        let PSGHICHU = !this.txt_ghiChuTT.Value ? '' : this.txt_ghiChuTT.Value.trim()
        let PSDONVIQL_ID = '1'
        let PSMA_BC = await this.layIdDonViNeo(this.cbo_donviQL_id.Value)
        if (!PSMA_BC) {
          this.$root.toastError('Thông tin về mã bưu cục chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        let doituong_id_neo = ''
        let trangthaitb_id_neo = ''
        let goicuoc_id_neo = ''
        try {
          var z = await this.layIdDoiTuongNeo({
            doituong_id: this.cbo_doituong_id.Value,
            loaikh_id: this.cbo_loaiKH.Value
          })
          if (!z) {
            //this.$root.toastError("Thông tin đối tượng chưa được khai báo. Bạn không thể tiếp tục cập nhật dữ liệu.");
            throw 'error'
          } else {
            if (z == -1) {
              doituong_id_neo = ''
              //this.$root.toastError("Thông tin đối tượng chưa được khai báo. Bạn không thể tiếp tục cập nhật dữ liệu.");
              throw 'error'
            }
            doituong_id_neo = z
          }
        } catch (err) {
          doituong_id_neo = ''
          this.$root.toastError('Thông tin đối tượng chưa được khai báo. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        trangthaitb_id_neo = ds_TBDD[0].TRANGTHAITB_ID
        goicuoc_id_neo = ds_TBDD[0].GOICUOC_ID_NEO
        let PSDOITUONG_ID = doituong_id_neo
        let PSLOAITB_ID = goicuoc_id_neo
        let PSTRANGTHAI_ID = '1'
        let PILOAIDCBC = '0'
        let PIKIEUBC = '0'
        let PIMABAOCUOC = '0'
        let PSNGAY_SINH = null
        if (!!dsHDTB[0].NGAY_SN) {
          PSNGAY_SINH = this.txt_ngaysnTB.Value
        }
        let PSEMAIL = this.txt_emailKH.Value
        try {
          let z = await this.layIdQuocTichNeo(this.cbo_quoctich.Value)
          if (!z) {
            this.vquoctich = ''
          } else {
            if (z == -1) {
              this.vquoctich = ''
              this.$root.toastError('Thông tin quốc tịch chưa được khai báo. Bạn không thể tiếp tục cập nhật dữ liệu.')
              throw 'error'
            }
            this.vquoctich = z
          }
        } catch (err) {
          this.vquoctich = ''
          this.$root.toastError('Thông tin quốc tịch chưa được khai báo. Bạn không thể tiếp tục cập nhật dữ liệu.')
          throw 'error'
        }
        let cmt = this.vcmt
        let passport = this.vpassport
        let loai_gt = this.vloai_gt
        let PSSO_GIAYTO
        if (parseInt(loai_gt) == 3) {
          cmt = ''
          PSSO_GIAYTO = this.vpassport
        } else {
          passport = ''
          PSSO_GIAYTO = cmt
        }
        let noicap = this.vnoicap
        let ngay_cap = this.vngay_cap
        let PSDIACHI_TT = this.txt_diachiTB.Value
        let PSMAKH = ''
        let PSMAKH_CU = ''
        let PSNV_QL = ''
        let INMA_BC = ''
        let PSDOITUONG_SD = '1'
        let PSDANGKY_DB = '1'
        let PSNGAY_THOI = ''
        let PSNV_PT = ''
        let PSNGAY_CAP = ''
        let vma_tinh = this.$root.token.getMaTinh()
        let ip = await this.getIp()
        let PSNGAY_CN = moment(new Date()).format('DD/MM/yyyy')
        let kq = await ccbsService.ts_hoanthien_hd_cn_danhba(this, ip,
          PSSO_NHA, PSPHUONG_ID, vma_tinh, PSMAKH, PILOAIDCBC, PSNGAY_CN, doituong_id_neo, INMA_BC, PSTYLE_VAT,
          PSSO_GIAYTO, PSUSERID, PSDOITUONG_SD, loai_gt, PSMAKH_CU, PSNV_QL, noicap, PSLOAITB_ID, PSDIACHI_TT,
          PSINCHITIET, PSTHUTU_IN, this.vquoctich, PSTEN_DB, PSNGAY_SINH, this.txt_ghiChuTB.Value, PSDONVIQL_ID,
          PSQUAN_ID, PSEMAIL, PSMA_BC, PSDANGKY_DB, PSTEN_TB, PSTRACUU_DB, PSTRANGTHAI_ID, PSNGAY_THOI, PSPHO_ID,
          PIKIEUBC, PSNGAY_SINH, PSNV_PT, this.vma_tb, PSNGAY_LD, PSNGAY_CAP, PSCUOC_TB, PSDIACHI
        )
        if (kq.result == 1) {
          return
        } else {
          this.$root.toastError('Có lỗi khi thực hiện: ' + result)
          throw 'error'
        }
      },
      async layIdQuocTichNeo(quoctich_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_id_quoctich_neo?quoctich_id=${quoctich_id}`)
        return !response.data ? null : response.data.QUOCTICH_NEO_ID
      },
      async layIdDoiTuongNeo(data) {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/lay_id_doituong_neo`, data)
        return !response.data ? null : response.data.DOITUONG_ID_NEO
      },
      async layIdDonViNeo(donvi_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_id_donvi_neo?donvi_id=${donvi_id}`)
        return response.data.DONVI_ID_NEO
      },
      async layDiaChiNeo(data) {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/lay_diachi_neo`, data)
        return response.data
      },
      async layNgaySdCuaThueBao(thuebao_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ngay_sd_cua_tbao?thuebao_id=${thuebao_id}`)
        return response.data.NGAY_SD
      },
      async layTtTbdd(ma_tb) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_tt_tbdd?somay=${ma_tb}`)
        return response.data
      },
      async layDsHopDongTbTheoHdtbId(hdtb_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_hopdong_tb_theo_hdtb_id?hdtb_id=${hdtb_id}`)
        return response.data
      },
      async layDsHopDongKhTheoHdkhId(hdkh_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_hopdong_kh_theo_hdkh_id?hdkh_id=${hdkh_id}`)
        return response.data
      },
      async layDsDbkhTheoKhId(khachhang_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_dbkh_theo_khid?khachhang_id=${khachhang_id}`)
        return response.data
      },
      async layIdPhieuYeuCauTiepNhan(hdtb_id) {
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_id_phieu_yc_tiepnhan?hdtb_id=${hdtb_id}`)
        return response.data.PHIEU_ID
      },
      async ktTaoDbThang(vngay_cn) {
        let ngay = new Date(vngay_cn)
        let vkyhoadon = '' //yyyyMM
        let thang = ngay.getMonth() + 1
        let nam = ngay.getFullYear()
        if (thang == 1) {
          thang = 12
          nam = nam - 1
        } else {
          thang = thang - 1
        }
        if (thang.toString().length > 1) {
          vkyhoadon = `${nam}${thang}01`
        } else {
          vkyhoadon = `${nam}0${thang}01`
        }
        let response = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/kt_tao_db_thang?kyhoadon=${vkyhoadon}`)
        return response.data.KQ
      },
      getDateElements(date) {
        date = new Date(date)
        let day = date.getDate()
        day = day > 9 ? day : '0' + day
        let month = date.getMonth() + 1
        month = month > 9 ? month : '0' + month
        let hour = date.getHours()
        hour = hour > 9 ? hour : '0' + hour
        let min = date.getMinutes()
        min = min > 9 ? min : '0' + min
        let second = date.getSeconds()
        second = second > 9 ? second : '0' + second
        return {
          y: date.getFullYear(),
          M: month,
          d: day,
          h: hour,
          m: min,
          s: second
        }
      },
      yyyymm(date) {
        if (!date) {
          return ''
        }
        date = this.getDateElements(date)
        return `${date.y}${date.M}`
      },
      yyyymmdd(date) {
        if (!date) {
          return ''
        }
        date = this.getDateElements(date)
        return `${date.y}${date.M}${date.d}`
      },
      ddmmyyyy(date) {
        if (!date) {
          return ''
        }
        date = this.getDateElements(date)
        return `${date.d}/${date.M}/${date.y}`
      },
      ddmmyyyyhhmiss(date) {
        if (!date) return ''
        let data = this.getDateElements(date)
        return `${data.d}/${data.M}/${data.y} ${data.h}:${data.m}:${data.s}`
      },
      ddmmyyyyhhmissToyyyymmddhhmiss(data) {
        let datePath = data.split(' ')[0]
        let timePath = data.split(' ')[1]
        let newDatePath = datePath.split('/').reverse().join('-')
        return newDatePath + ' ' + timePath
      },
      yyyy_mm_dd(date) {
        if (!date) {
          return ''
        }
        date = this.getDateElements(date)
        return `${date.y}-${date.M}-${date.d}`
      },
      async SetButton(kieu) {
        this.tsbtnNhapMoi.Enable = false
        this.tsbtnGhiLai.Enable = false
        this.tsbtnXoa.Enable = false
        this.tsbtnHuyBo.Enable = false
        this.tsbtnCapNhatDB.Enable = false
        this.tsbtnGiaoPhieu.Enable = false
        this.tsbtnInPhieuYC.Enable = false
        if (kieu === -1)//Bat dau
        {
          // this.$refs.txt_maTB.focus()
          this.tsbtnGhiLai.Enable = true
          this.tsbtnHuyBo.Enable = true
        }
        if (kieu === 0)//Bat dau
        {
          // this.$refs.txt_maTB.focus()
          this.tsbtnNhapMoi.Enable = false
          this.tsbtnGhiLai.Enable = true
          this.tsbtnHuyBo.Enable = true
          this.Clear()
        }
        if (kieu === 1)//Them moi
        {
          // this.$refs.txt_maTB.focus()
          this.tsbtnGhiLai.Enable = true
          this.tsbtnHuyBo.Enable = true
          this.Clear()
        }
        if (kieu === 2)//Huy
        {
          // this.$refs.txt_magiaodich.focus()
          this.tsbtnNhapMoi.Enable = true
          this.tsbtnXoa.Enable = true
          this.Clear()
        }
        if (kieu === 3)//Edit
        {
          // this.$refs.txt_magiaodich.focus()
          let kt = await this.$root.context.get('web-hopdong/thaydoithongtinkhuyenmai/kiemtra_quytrinh_tuhoanthien', { quytrinh_id: this.quytrinh_id })
          this.tsbtnNhapMoi.Enable = true
          this.tsbtnXoa.Enable = true
          this.tsbtnGhiLai.Enable = true
          this.tsbtnHuyBo.Enable = true
          if (kt.data.result === 'no') {
            this.tsbtnGiaoPhieu.Enable = true
          } else {
            this.tsbtnCapNhatDB.Enable = true
          }
          this.tsbtnInPhieuYC.Enable = true
        }
      },
      tsbtnNhapMoi_Click() {
        if (this.tsbtnNhapMoi.Enable === false) return
        this.SetButton(1)
      },
      tsbtnHuyBo_Click() {
        if (this.tsbtnHuyBo.Enable === false) return
        this.SetButton(0)
      },
      async HT_DT_VinaPhone_Combobox(loaikh_id_neo) {
        if (loaikh_id_neo == 0) {
          // let rs = await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/ht_dt_combobox",{
          //   table: "css.doituong_neo",
          //   text: "ten_dt_neo",
          //   value: "doituong_id_neo",
          //   where: "phanvung_id = "+ this.$auth.getPhanVungID(),
          //   order: "doituong_id_neo"
          // })
          let rs = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/ht_dt_vinaphone?loaikh_id_neo=-1`)
          if (rs.data && rs.data.length > 0) {
            this.dsDTVinaPhone = rs.data
            this.cboDTVinaPhone.Value = rs.data[0].ten_dt_neo
          }
        } else {
          // let rs = await this.$root.context.post("/web-hopdong/thaydoithongtinkhuyenmai/ht_dt_combobox",{
          //   table: "css.doituong_neo",
          //   text: "ten_dt_neo",
          //   value: "doituong_id_neo",
          //   where: "phanvung_id = "+ this.$auth.getPhanVungID() + " and loaikh_id_neo = "+loaikh_id_neo,
          //   order: "doituong_id_neo"
          // })
          let rs = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/ht_dt_vinaphone?loaikh_id_neo=${loaikh_id_neo}`)
          if (rs.data && rs.data.length > 0) {
            this.dsDTVinaPhone = rs.data
            this.cboDTVinaPhone.Value = rs.data[0].ten_dt_neo
          }
        }
      },
      KIEMTRA_HTTT: function() {
        return true
      },
      // get địa chỉ từ form
      async getDiaChiTuFormDiaChi(data) {
        switch (this.diachi_flag) {
          case 'DIACHI_KH':
            this.tinhkh_id = data.tinh_id
            this.quankh_id = data.quan_id
            this.phuongkh_id = data.phuong_id
            this.phokh_id = data.pho_id
            this.dacdiemkh_id = data.dacdiem_id
            this.apkh_id = data.ap_id
            this.khukh_id = data.khu_id
            this.sonhakh = data.so_nha
            this.txt_diachiKH.Value = data.diachimoi
            this.diachi_flag = null
            this.$refs.txt_diachiKH.focus()
            break
          case 'DIACHI_NDD':
            this.tinhndd_id = data.tinh_id
            this.quanndd_id = data.quan_id
            this.phuongndd_id = data.phuong_id
            this.phondd_id = data.pho_id
            this.dacdiemndd_id = data.dacdiem_id
            this.ngondd_id = data.ap_id
            this.sonhandd = data.so_nha
            this.txt_diachiNDD.Value = data.diachimoi
            this.diachi_flag = null
            this.$refs.txt_diachiNDD.focus()
            break
          case 'DIACHI_TB':
            this.tinhtb_id = data.tinh_id
            this.quantb_id = data.quan_id
            this.phuongtb_id = data.phuong_id
            this.photb_id = data.pho_id
            this.dacdiemtb_id = data.dacdiem_id
            this.aptb_id = data.ap_id
            this.khutb_id = data.khu_id
            this.sonhatb = data.so_nha
            this.block = data.block
            this.tang = data.tang
            this.phong = data.sophong
            this.mota = data.motathem
            //this.txt_diachiTB.Value = data.diachimoi
            this.thuebao.mgwan.diachi_tb = data.diachimoi // add new
            this.thuebao.tsl.diachi_tb = data.diachimoi // add new
            this.diachi_flag = null
            this.showThongTinThem = false
            this.$refs.txt_diachiTB.focus()
            break
          case 'DIACHI_LD':
            this.tinhld_id = data.tinh_id
            this.quanld_id = data.quan_id
            this.phuongld_id = data.phuong_id
            this.phold_id = data.pho_id
            this.dacdiemld_id = data.dacdiem_id
            this.apld_id = data.ap_id
            this.khuld_id = data.khu_id
            this.sonhald = data.so_nha
            this.block_ld = data.block
            this.tang_ld = data.tang
            this.sophong_ld = data.sophong
            this.motathem_ld = data.motathem
            this.showThongTinThem = false
            //this.txt_diachiLD.Value = data.diachimoi
            if(this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL)
              this.thuebao.tsl.diachi_ld = data.diachimoi // add new
            else
              this.thuebao.mgwan.diachi_ld = data.diachimoi // add new
            this.diachi_flag = null
            this.$refs.txt_diachiLD.focus()
            break
          case 'DIACHI_TT': {
            let ds = []
            if (this.donviql_httt == 1) {
              let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_v2', {
                phuong_id: data.phuong_id,
                pho_id: data.pho_id,
                ap_id: data.ap_id,
                khu_id: data.khu_id,
                kieu: 1,
                loaikh_id: this.cbo_loaiKH.Value,
                httt_id: this.cbo_HTTT.Value
              })
              ds = rs.data
            } else {
              if (this.phantap_kh) {
                let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_ptkh_v2', {
                  phuong_id: data.phuong_id,
                  pho_id: data.pho_id,
                  ap_id: data.ap_id,
                  khu_id: data.khu_id,
                  dacdiem_id: data.dacdiem_id,
                  kieu: 1,
                  loaikh_id: this.cbo_loaiKH.Value,
                  nganhnghe_id: this.cbo_nganhngheloc.Value,
                  doanhthu: this.pt_doanhthu,
                  diemtn: this.pt_diem_tn
                })
                ds = rs.data
              } else {
                let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql', {
                  phuong_id: data.phuong_id,
                  pho_id: data.pho_id,
                  ap_id: data.ap_id,
                  khu_id: data.khu_id,
                  kieu: 1,
                  loaikh_id: this.cbo_loaiKH.Value
                })
                ds = rs.data
              }
            }
            if (ds.length == 0) {
              if (this.dsDonViQL.length != 1) {
                this.$root.toastError('Địa danh này chưa được gán đơn vị quản lý. Hãy liên hệ với Admin để xử lý!')
                return
              }
            } else if (ds.length == 1) {
              this.cbo_donviQL_id.Value = ds[0].donviql_id
            } else {
              let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.cbo_donviQL_id.Value, null, ds)
              if (resolvePromise.ok) {
                this.cbo_donviQL_id.Value = resolvePromise.data.donviql_id
              } else return
            }
            this.txt_maNVTC.Value = ''
            this.txt_maTuyen.Value = ''
            this.tinhtt_id = data.tinh_id
            this.quantt_id = data.quan_id
            this.phuongtt_id = data.phuong_id
            this.phott_id = data.pho_id
            this.dacdiemtt_id = data.dacdiem_id
            this.aptt_id = data.ap_id
            this.khutt_id = data.khu_id
            this.sonhatt = data.sonha
            await this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.cbo_donviQL_id.Value, this.dacdiemtt_id)
            if (this.donviql_httt == 1) {
              await this.HienThi_NVQL_AM_HTTT(this.cbo_loaiKH.Value, this.LOAI_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cbo_donviQL_id.Value, this.cbo_HTTT.Value)
            } else {
              if (this.phantap_kh) {
                await this.HienThi_NVQL_AM_PTKH(this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.dacdiemtt_id, this.cbo_loaiKH.Value, this.cbo_nganhngheloc.Value, this.pt_doanhthu, this.pt_diem_tn, this.LOAI_NV.NHANVIEN_AM, this.cbo_donviQL_id.Value)
              } else {
                await this.HienThi_NVQL_AM(this.cbo_loaiKH.Value, this.LOAI_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cbo_donviQL_id.Value)
              }
            }
            this.txt_diachiTT.Value = data.diachimoi
            if (this.txt_diachiCT.Value == '' || this.txt_diachiCT.Value == null) {
              //Lấy địa chỉ chứng tư
              this.tinhct_id = data.tinh_id
              this.quanct_id = data.quan_id
              this.phuongct_id = data.phuong_id
              this.phoct_id = data.pho_id
              this.dacdiemct_id = data.dacdiem_id
              this.ngoct_id = data.ap_id
              this.khuct_id = data.khu_id
              this.sonhact = data.sonha
              this.txt_diachiCT.Value = data.diachimoi
            }
            break
          }
          case 'DIACHI_CT':
            this.tinhct_id = data.tinh_id
            this.quanct_id = data.quan_id
            this.phuongct_id = data.phuong_id
            this.phoct_id = data.pho_id
            this.dacdiemct_id = data.dacdiem_id
            this.ngoct_id = data.ap_id
            this.khuct_id = data.khu_id
            this.sonhact = data.sonha
            this.txt_diachiCT.Value = data.diachimoi
            break
          case 'DIACHI_BC': {
            let ds = []
            if (this.donviql_httt == 1) {
              let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_v2', {
                phuong_id: data.phuong_id,
                pho_id: data.pho_id,
                ap_id: data.ap_id,
                khu_id: data.khu_id,
                kieu: 1,
                loaikh_id: this.cbo_loaiKH.Value,
                httt_id: this.cbo_HTTT.Value
              })
              ds = rs.data
            } else {
              if (this.phantap_kh) {
                let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql_ptkh_v2', {
                  phuong_id: data.phuong_id,
                  pho_id: data.pho_id,
                  ap_id: data.ap_id,
                  khu_id: data.khu_id,
                  dacdiem_id: data.dacdiem_id,
                  kieu: 1,
                  loaikh_id: this.cbo_loaiKH.Value,
                  nganhnghe_id: this.cbo_nganhngheloc.Value,
                  doanhthu: this.pt_doanhthu,
                  diemtn: this.pt_diem_tn
                })
                ds = rs.data
              } else {
                let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_donviql', {
                  phuong_id: data.phuong_id,
                  pho_id: data.pho_id,
                  ap_id: data.ap_id,
                  khu_id: data.khu_id,
                  kieu: 1,
                  loaikh_id: this.cbo_loaiKH.Value
                })
                ds = rs.data
              }
            }
            if (ds.length <= 0) {
              // if(this.dsDonViQL.length != 1) {
              this.$root.toastError('Địa danh này chưa được gán đơn vị quản lý. Hãy liên hệ với Admin để xử lý!')
              return
              // }
            } else if (ds.length == 1) {
              this.cbo_donviQL_id.Value = ds[0].donviql_id
            } else {
              let resolvePromise = await this.$refs.popupChonDonVi.show(null, null, this.cbo_donviQL_id.Value, null, ds)
              if (resolvePromise.ok) {
                this.cbo_donviQL_id.Value = resolvePromise.data.donviql_id
              } else return
            }
            this.txt_maNVTC.Value = ''
            this.txt_maTuyen.Value = ''
            this.tinhbc_id = data.tinh_id
            this.quanbc_id = data.quan_id
            this.phuongbc_id = data.phuong_id
            this.phobc_id = data.pho_id
            this.dacdiembc_id = data.dacdiem_id
            this.apbc_id = data.ap_id
            this.khubc_id = data.khu_id
            this.sonhabc = data.sonha
            await this.HT_NVTC_TheoDC(this.quanbc_id, this.phuongbc_id, this.phobc_id, this.apbc_id, this.khubc_id, this.cbo_donviQL_id.Value, this.dacdiembc_id)
            this.txt_diachiBC.Value = data.diachimoi
            if (this.phantap_kh) {
              await this.HienThi_NVQL_AM_PTKH(this.phuongbc_id, this.phobc_id_id, this.apbc_id, this.khubc_id, this.dacdiembc_id, this.cbo_loaiKH.Value, this.cbo_nganhngheloc.Value, this.pt_doanhthu, this.pt_diem_tn, this.LOAI_NV.NHANVIEN_AM, this.cbo_donviQL_id.Value)
            }
          }
        }
      },
      HT_NVTC_TheoDC: async function(quan_id, phuong_id, pho_id, ap_id, khu_id, donvi_ql_id, dacdiem_id) {
        try {
          let ds = []
          if (this.tuyenthu_httt == 1 && this.cbo_HTTT.Value) {
            let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/ht_nvtc_theodc_v2', {
              phuong_id: phuong_id,
              pho_id: pho_id,
              ap_id: ap_id,
              khu_id: khu_id,
              donvi_ql_id: donvi_ql_id,
              dacdiem_id: dacdiem_id,
              httt_id: this.cbo_HTTT.Value
            })
            ds = rs.data
          } else {
            let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/ht_nvtc_theodc', {
              phuong_id: phuong_id,
              pho_id: pho_id,
              ap_id: ap_id,
              khu_id: khu_id,
              donvi_ql_id: donvi_ql_id,
              dacdiem_id: dacdiem_id
            })
            ds = rs.data
          }
          if (ds.length > 1) {
            this.loaiPopupTraCuuNVTC = 1
            this.objTraCuuNVTC = {}
            this.objTraCuuNVTC.QUAN_ID = quan_id
            this.objTraCuuNVTC.PHUONG_ID = phuong_id
            this.objTraCuuNVTC.PHO_ID = pho_id
            this.objTraCuuNVTC.KHU_ID = khu_id
            this.objTraCuuNVTC.AP_ID = ap_id
            this.objTraCuuNVTC.DACDIEM_ID = dacdiem_id
            this.objTraCuuNVTC.NHANVIENTC_ID = this.nhanvientc_id
            this.objTraCuuNVTC.TUYENTHU_ID = ds[0].tuyenthu_id
            this.objTraCuuNVTC.DONVIQL_ID = donvi_ql_id
            if (this.tuyenthu_httt == 1 && this.cbo_HTTT.Value) {
              this.objTraCuuNVTC.HTTT_ID = this.cbo_HTTT.Value
            }
            this.diachiLD = this.objTraCuuNVTC
            this.$bvModal.show('dlgTimKiemNVTC')
          } else if (ds.length > 0) {
            this.txt_maNVTC.Value = ds[0].manv_tc
            if (ds[0].tuyenthu_id) {
              this.tuyenthu_id = ds[0].tuyenthu_id
            }
            this.txt_maTuyen.Value = ds[0].ma_tuyen
            this.nhanvientc_id = ds[0].nhanvien_id
          } else {
            this.txt_maNVTC.Value = ''
            this.txt_maTuyen.Value = ''
            this.tuyenthu_id = 0
            this.nhanvientc_id = 0
          }
        } catch (error) {
        }
      },
      HienThi_NVQL_AM_HTTT: async function(loaikh_id, loainv_id, phuong_id, pho_id, ap_id, khu_id, nvtc_id, donvi_ql_id, httt_id) {
        try {
          if (!this.kt_load) {
            let ds_nvql = []
            let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_nhanvien_ql_am_v2', {
              loaikh_id: loaikh_id,
              loainv_id: loainv_id,
              phuong_id: phuong_id,
              pho_id: pho_id,
              ap_id: ap_id,
              khu_id: khu_id,
              nhanvientc_id: nvtc_id,
              donvi_ql_id: donvi_ql_id,
              httt_id: httt_id
            })
            ds_nvql = rs.data
            if (ds_nvql.length == 0) {
              this.txt_nhanVienQL.Value = ''
              this.nhanvien_ql_am_id = 0
            } else {
              this.txt_nhanVienQL.Value = ds_nvql[0].ma_nv + ' - ' + ds_nvql[0].ten_nv
              this.nhanvien_ql_am_id = ds[0].nhanvien_id
            }
          }
        } catch (error) {
        }
      },
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
              this.txt_nhanVienQL.Value = ''
              this.nhanvien_ql_am_id = 0
            } else {
              this.txt_nhanVienQL.Value = ds_nvql[0].ma_nv + ' - ' + ds_nvql[0].ten_nv
              this.nhanvien_ql_am_id = ds[0].nhanvien_id
            }
          }
        } catch (error) {
        }
      },
      HienThi_NVQL_AM: async function(loaikh_id, loainv_id, phuong_id, pho_id, ap_id, khu_id, nvtc_id, donvi_ql_id) {
        try {
          if (!this.kt_load) {
            let ds_nvql = []
            let rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_nhanvien_ql_am_1', {
              loaikh_id: loaikh_id,
              loainv_id: loainv_id,
              phuong_id: phuong_id,
              pho_id: pho_id,
              ap_id: ap_id,
              khu_id: khu_id,
              nhanvientc_id: nvtc_id,
              donvi_ql_id: donvi_ql_id
            })
            ds_nvql = rs.data
            if (ds_nvql.length == 0) {
              this.txt_nhanVienQL.Value = ''
              this.nhanvien_ql_am_id = 0
            } else {
              this.txt_nhanVienQL.Value = ds_nvql[0].ma_nv + ' - ' + ds_nvql[0].ten_nv
              this.nhanvien_ql_am_id = ds[0].nhanvien_id
            }
          }
        } catch (error) {
        }
      },
      btnLoaiKH_Click() {
        if (!this.cbo_loaiKH.Enable) return
        if (this.cbo_loaiKH.Value !== null && this.cbo_loaiKH.Value !== '-1') {
          let loaiKH = this.dsLoaiKH.find(item => item.loaikh_id == this.cbo_loaiKH.Value)
          let kieukh_id = -1
          let kieukh = loaiKH ? loaiKH.loai : ''
          if (kieukh !== '')
            kieukh_id = parseInt(kieukh.toString(), 10)
          let nhomlkh_id = -1
          let nhomkh = loaiKH ? loaiKH.nhomlkh_id : ''
          if (nhomkh !== '')
            nhomlkh_id = parseInt(nhomkh.toString(), 10)
          this.objLoaiKH = {}
          this.objLoaiKH.loaikh_id = this.cbo_loaiKH.Value
          this.objLoaiKH.kieukh_id = kieukh_id
          this.objLoaiKH.nhomlkh_id = nhomlkh_id
          this.$bvModal.show('popupLoaiKH')
        }
      },
      // getLoaiKHID(loaikh_id){
      //     // this.cbo_loaiKH.Value = loaikh_id
      //   let grid = this.$refs['cboGridLoaiKH'].$children[0].grid
      //   let idx = grid.getCurrentViewRecords().findIndex(item => item.loaikh_id == loaikh_id)
      //   if (idx > -1) grid.selectRow(idx)
      // },
      cboNganhNgheLoc_EditValueChanged(val) {
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
      closeSearchAccount(data) {
        if (data != null) {
          if (data.ma_tb) {
            this.txt_maTB.Value = data.ma_tb
          }
        }
        this.$bvModal.hide('popupSearchAccount')
      },
      getNVTCProcess: async function(data) {
        if (this.loaiPopupTraCuuNVTC == 0) {
          if (this.quanbc_id != 0) {
            this.txt_maNVTC.Value = data.ma_nvtc
            this.txt_maTuyen.Value = data.ma_tuyen
            this.tuyenthu_id = data.tuyenthu_id
            this.nhanvientc_id = data.nhanvientc_id
            if (this.donviql_httt == 1) {
              await this.HienThi_NVQL_AM_HTTT(this.cbo_loaiKH.Value, this.LOAI_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cbo_donviQL_id.Value, this.cbo_HTTT.Value)
            } else {
              await this.HienThi_NVQL_AM(this.cbo_loaiKH.Value, this.LOAI_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cbo_donviQL_id.Value)
            }
          } else {
            if (this.quantt_id != 0 && this.phuongtt_id != 0 && (this.phott_id != 0 || this.khutt_id != 0 || this.aptt_id != 0)) {
              this.txt_maNVTC.Value = data.ma_nvtc
              this.txt_maTuyen.Value = data.ma_tuyen
              this.tuyenthu_id = data.tuyenthu_id
              this.nhanvientc_id = data.nhanvientc_id
              if (this.donviql_httt == 1) {
                await this.HienThi_NVQL_AM_HTTT(this.cbo_loaiKH.Value, this.LOAI_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cbo_donviQL_id.Value, this.cbo_HTTT.Value)
              } else {
                await this.HienThi_NVQL_AM(this.cbo_loaiKH.Value, this.LOAI_NV.NHANVIEN_KINHDOANH, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.nhanvientc_id, this.cbo_donviQL_id.Value)
              }
            } else {
              this.$root.toastError('Chưa có thông tin về địa chỉ thanh toán. Bạn kiểm tra lại!')
              this.txt_maNVTC.Value = ''
              this.txt_maTuyen.Value = ''
              this.nhanvientc_id = 0
              this.txt_nhanVienQL.Value = ''
              this.nhanvien_ql_am_id = 0
            }
          }
        } else if (this.loaiPopupTraCuuNVTC == 1) {
          if (this.objTraCuuNVTC.QUAN_ID && this.objTraCuuNVTC.PHUONG_ID) {
            this.nhanvientc_id = data.nhanvientc_id
            this.txt_maNVTC.Value = data.ma_nvtc
            this.tuyenthu_id = data.tuyenthu_id
            this.txt_maTuyen.Value = data.ma_tuyen
          } else {
            this.$root.toastError('Chưa có thông tin về địa chỉ thanh toán. Bạn kiểm tra lại!')
            this.txt_maNVTC.Value = ''
            this.txt_maTuyen.Value = ''
            this.nhanvientc_id = 0
            this.tuyenthu_id = 0
          }
        }
      },
      btnChapNhanNganHang: function(data) {
        this.dv_qhns = data.DV_QHNS
        this.machuong = data.MACHUONG
        this.mn_kt = data.MN_KT
        this.ma_ndkt = data.MA_NDKT
        this.mn_nsnn = data.MN_NSNN
        this.tien_dm = data.TIEN_DM
      },
      bntLayMaKh_Click: function() {
        this.popupComponentName = 'popup-SearchAccount'
        this.$bvModal.show('popupSearchAccount')
      },
      /*btnMaNVKD_Click() {
        this.popupComponent = () => import('./Popups/popupGanKhuVucQuanLyThueBao')
        let diachi = {}
        diachi.QUAN_ID = this.quanld_id
        diachi.PHUONG_ID = this.phuongld_id
        diachi.PHO_ID = this.phold_id
        diachi.AP_ID = this.apld_id
        diachi.KHU_ID = this.khuld_id
        diachi.DACDIEM_ID = this.dacdiemld_id
        if (this.thuebao_id)
          this.objGanKhuVuc = {
            diachi: diachi,
            khuvuc_id: this.khuvuc_id,
            hdtb_id: this.hdtb_id,
            donvi_id: this.cbo_donviQL_id.Value,
            isStrictMode: true
          }
        else
          this.objGanKhuVuc = {
            diachi: diachi,
            khuvuc_id: this.khuvuc_id,
            thuebao_id: this.thuebao_id,
            donvi_id: this.cbo_donviQL_id.Value,
            isStrictMode: true
          }
        this.$bvModal.show('gankhuvuc')
      },*/
      closeFormGanKhuVuc() {
        this.$bvModal.hide('gankhuvuc')
      },
      chapnhan_gankhuvuc(data) {
        console.log(data)
        if (data.khuvuc_id != 0) {
          this.khuvuc_id = parseInt(data.khuvuc_id + '', 10)
          this.dsHDTB_KV = data.hdtb_kv_data
          this.txt_khuvuc.Value = data.ma_kv
        } else {
          this.txt_khuvuc.Value = ''
          this.$refs.txt_khuvuc.focus()
          this.khuvuc_id = 0
          this.dsHDTB_KV = []
        }
        this.$bvModal.hide('gankhuvuc')
      },
      update_ngayhuy: function(val) {
        this.dsCTKMThayDoi[this.rowIndex_ctkmtd].ngay_huy = val
        this.dtCTKMHD = this.dsCTKMThayDoi
        console.log(this.dsCTKMThayDoi)
      },
      DsCTKMTD: function(data) {
        let arr = []
        for (const element of data) {
          let obj = {
            'NGAY_BD': element.ngay_bd,
            'NGAY_HUY': element.ngay_huy,
            'CHITIETKM_ID': element.chitietkm_id,
            'KIEU_YC': element.kieu_yc
          }
          arr.push(obj)
          // data += `<khuyenmai>
          //   <TEN_YC>${element.ten_yc}</TEN_YC>
          //   <NGAY_HUY>${element.ngay_huy}</NGAY_HUY>
          //   <CHITIETKM_ID>${element.chitietkm_id}</CHITIETKM_ID>
          //   <KIEU_YC>${element.kieu_yc}</KIEU_YC>
          // </khuyenmai>`
        }
        // return '<NewDataSet>' + str + '</NewDataSet>';
        return arr
      },
      LoadTuHopDongTuVan: async function(hdkh_cha_id) {
        try {
          this.hdkh_cha_id = hdkh_cha_id
          let rs = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/lay-hopdong-tuvan/${hdkh_cha_id}`)
          if (rs.data.length > 0) {
            this.cbo_dichvu_id.Value = rs.data[0]['dichvuvt_id']
            this.txt_maTB.Value = rs.data[0]['ma_tb']
            this.timKiem()
          } else {
            this.$root.toastError('Khách hàng không có thuê bao')
          }
        } catch (error) {
        }
      },
      LookupByAccount: async function(vmatb, dichvuvt_id, loaitb_id) {
        this.txt_maTB.Value = vmatb
        await this.timKiem()
      },
      showModalComponent: function() {
        //if (this.popupComponentName = 'popupThongTinLienHe') {
          //document.getElementById('componentId').classList.remove('modal-content')
        //}
      },
      closeChangeCustomerInfor: function() {
        this.$bvModal.hide('popupChangeCustomerInfor')
      },
      TaoDuLieu_HDTT_NV: function() {
        this.dsHDTT_NV = []
        if (this.nhanvien_ql_am_id != 0) {
          let obj = {
            HDTT_ID: this.hdtt_id,
            NHANVIEN_ID: this.nhanvien_ql_am_id,
            MAY_CN: this.$root.token.getUserName(),
            NGAY_CN: DateTimeLib.toDateDisplay(new Date()),
            NGUOI_CN: this.$root.token.getUserName()
          }
          this.dsHDTT_NV.push(obj)
        }
      },
      changeHTTT: async function(val) {
        this.cbo_HTTT.Value = val
        try {
          if (this.dsHinhThuc.length > 0) {
            if (!val) {
              return
            }
            if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THANHTOAN) {
              this.httt_id = val
              if (this.httt_id == HINHTHUCTHANHTOAN.UYNHIEM_THU) {
                this.cbo_nganhang.Enable = true
                if (this.dsNganHang.length > 0) {
                  let rsMapId = await this.$root.context.get('web-hopdong/chuyendich/map_loai_nh_id_neo', {
                    nganhang_id: this.cbo_nganhang.Value ? this.cbo_nganhang.Value : 0
                  })
                  let loai_nh = rsMapId.data && rsMapId.data.length > 0 ? rsMapId.data[0].loai_nh : null
                  if (loai_nh == 2) {
                    this.isEnableBtnNSNN = true
                  } else {
                    this.isEnableBtnNSNN = false
                  }
                }
              } else {
                this.cbo_nganhang.Enable = false
                this.isEnableBtnNSNN = false
              }
            } else {
              this.cbo_nganhang.Enable = false
              this.isEnableBtnNSNN = false
            }
            //Lấy lại thông tin ĐVQL, NVTC theo hình thức TT
            if (this.tuyenthu_httt == 1) {
              if (this.phuongtt_id != 0 && (this.phott_id != 0 || this.aptt_id != 0 || this.khutt_id != 0)) {
                console.log('vao')
                await this.HT_NVTC_TheoDC(this.quantt_id, this.phuongtt_id, this.phott_id, this.aptt_id, this.khutt_id, this.cbo_donviQL_id.Value, this.dacdiemtt_id)
              }
            }
          }
        } catch (error) {
        }
      },
      checkDate: function(value) {
        try {
          moment(String(value)).format('MM/DD/YYYY hh:mm')
          return true
        } catch (e) {
          return false
        }
      },
      async ghiLai() {
        if (this.tsbtnGhiLai.Enable === false) return
        try {
          this.loading(true)
          let check = await this.checkForm()
          if (check) {
            let date_regex = /^([0][1-9]|[1|2][0-9]|[3][0|1])[/]([0][1-9]|[1][0-2])[/]([0-9]{4})$/
            if (this.tsbtnNhapMoi.Enable === false) {
              let rsKtraDB = await this.$root.context.post('/web-hopdong/hopdong/kiemtra_danhba_khi_themmoi', {
                maTb: this.txt_maTB.Value,
                khachHangId: 0,
                thueBaoId: this.thuebao_id,
                dichVuVtId: this.dichvuvt_id
              })
              if (!rsKtraDB.data) {
                this.$root.toastError('Thuê bao không tồn tại trong danh bạ, không cập nhật được')
                return
              }
              let rsKiemTraLapHDKhac = await this.$root.context.post('/web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac', {
                maTb: this.txt_maTB.Value,
                maGd: '0',
                dichVuVtId: this.cbo_dichvu_id.Value,
                loaiHdId: this.LOAIHOPDONG.THAY_TT_KHAC
              })
              if (rsKiemTraLapHDKhac.data) {
                this.$root.toastError(`Số máy/Acc: ${this.txt_maTB.Value} đang được lập bởi hợp đồng (phụ lục) ${rsKiemTraLapHDKhac.data.ten_loaihd} có mã giao dịch ${rsKiemTraLapHDKhac.data.ma_gd} do User ${rsKiemTraLapHDKhac.data.nguoi_cn} thuộc đơn vị ${rsKiemTraLapHDKhac.data.ten_dv} thực hiện vào ngày ${rsKiemTraLapHDKhac.data.ngay_yc}`)
                return
              }
              if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_KHUYENMAI && this.dtCTKMHD.length > 0) {
                // debugger
                for (const element of this.dtCTKMHD) {
                  if (element.kieu_yc == 0) {
                    if (!(element.ngay_huy)) {
                      this.$root.toastError('Bạn phải nhập thông tin ngày hủy (dd/MM/yyyy)')
                      return
                    } else {
                      if (!(date_regex.test(element.ngay_huy))) {
                        this.$root.toastError('Bạn phải nhập đúng định dạng thông tin ngày hủy (dd/MM/yyyy)')
                        return
                      }
                    }
                  }
                  if (this.kt_ngaybd_themmoi_km && (this.dichvuvt_id == DICHVUVIENTHONG.ANTOAN_BAOMAT_TT || this.dichvuvt_id == DICHVUVIENTHONG.DICHVU_CNTT
                    || this.dichvuvt_id == DICHVUVIENTHONG.TRUNGTAM_DULIEU || this.dichvuvt_id == DICHVUVIENTHONG.HOINGHI_TRUYENHINH)
                    && element.kieu_yc == 1) {
                    if (!element.ngay_bd) {
                      this.$root.toastError('Bạn phải nhập thông tin ngày bắt đầu (dd/MM/yyyy)')
                      return
                    } else {
                      if (!(date_regex.test(element.ngay_bd))) {
                        this.$root.toastError('Bạn phải nhập đúng định dạng thông tin ngày bắt đầu (dd/MM/yyyy)')
                        return
                      }
                    }
                  }
                }
              }
              let rsHG_TN = await this.$root.context.post('/web-hopdong/hopdong/lay_huonggiao_tiepnhan', {
                loaiHdId: this.LOAIHOPDONG.THAY_TT_KHAC,
                loaiTbId: this.loaitb_id,
                tocDoId: this.tocdo_id,
                mucCuocId: this.muccuoc_id,
                kieuLdId: this.cbo_kieuld_id.Value
              })
              let huonggiao_tn = 0
              this.quytrinh_id = 0
              if (!rsHG_TN.data || rsHG_TN.data.length == 0) {
                this.$root.toastError('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
                return
              }
              huonggiao_tn = rsHG_TN.data.huonggiao_id
              this.quytrinh_id = rsHG_TN.data.quytrinh_id
              await this.taoDuLieu(true)
              if (this.loi_sinh_magd) {
                this.loi_sinh_magd = false
                return
              }
              await this.themMoi_HopDong(huonggiao_tn)
              if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_DV_CNTT) {
                await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/ins_hdtb_cntt_sub', {
                  thuebao_id: this.thuebao_id,
                  hdtb_id: this.hdtb_id
                })
              }
              await this.capNhat_DiaChiBC()
              await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/capnhat_hdtb_donvi/${this.hdtb_id}`)
            } else {
              this.loading(true)
              let rsKtraDB = await this.$root.context.post('/web-hopdong/hopdong/kiemtra_danhba_khi_themmoi', {
                maTb: this.txt_maTB.Value,
                khachHangId: 0,
                thueBaoId: this.thuebao_id,
                dichVuVtId: this.dichvuvt_id
              })
              if (!rsKtraDB.data) {
                this.$root.toastError('Thuê bao không tồn tại trong danh bạ, không cập nhật được')
                return
              }
              if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_KHUYENMAI && this.cbo_kieukm_id.Value == 0 && this.dtCTKMHD.length > 0) {
                // debugger
                for (const element of this.dtCTKMHD) {
                  if (element.kieu_yc == 0) {
                    if (!element.ngay_huy) {
                      this.$root.toastError('Bạn phải nhập thông tin ngày hủy (dd/MM/yyyy)')
                      return
                    } else {
                      if (!(date_regex.test(element.ngay_huy))) {
                        this.$root.toastError('Bạn phải nhập đúng định dạng thông tin ngày hủy (dd/MM/yyyy)')
                        return
                      }
                    }
                  }
                }
              }
              await this.taoDuLieu(false)
              await this.capNhat_HopDong()
              await this.capNhat_DiaChiBC()
            }
            await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/cn_ngay_hdkm/${this.hdkh_id}`, this.DsCTKMTD(this.dtCTKMHD))
            await this.SetButton(3)
            await this.nap_ds_kmtt(this.loaitb_id, this.dsHDTB[0].KIEULD_ID, this.hdtb_id, 0)
            let rsHopDongTB = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_hopdong_thuebao_theo_hdkh?hdKhId=${this.hdkh_id}`)
            this.dsHDTB = rsHopDongTB.data
            await this.hienThiThongTinHopDongTB(rsHopDongTB.data)
            await this.GanDL_CCBS()
            this.$root.toastSuccess('Cập nhật dữ liệu lên hệ thống viễn thông tỉnh thành công!')
          }
        } catch (error) {
          this.$root.toastError('Xảy ra lỗi trong quá trình thực hiện')
          console.log(error)
        } finally {
          this.loading(false)
        }
      },
      getActiveQuyTrinhXacMinhTTLH: async function() {
        const body = { 'ma_ts': 'GUI_YEUCAU_PHEDUYET_TTTT_KH' }
        let res = await this.$root.context.post('/web-hopdong/lapdatmoi/lay_ds_thamso_md_mats', body)
        if (res && res.data && res.data.length > 0) {
          const data = res.data[0]
          if (data.ten_ts && data.ten_ts == '1') {
            this.activeQuyTrinhXacMinhCapNhatTTLH = true
          } else {
            this.activeQuyTrinhXacMinhCapNhatTTLH = false
          }
        } else {
          this.activeQuyTrinhXacMinhCapNhatTTLH = false
        }
      },
      cbo_doituong_id_change: async function() {
        await this.layDsMucCuocTB()
      },
      getDiaChi: function(data, datatype) {
        if (!this[datatype]) this[datatype] = {}
        this[datatype].DIACHI = data.diachimoi
        this[datatype].TINH_ID = data.tinh_id
        this[datatype].QUAN_ID = data.quan_id
        this[datatype].PHUONG_ID = data.phuong_id
        this[datatype].PHO_ID = data.pho_id
        this[datatype].AP_ID = data.ap_id
        this[datatype].KHU_ID = data.khu_id
        this[datatype].DACDIEM_ID = data.dacdiem_id
        this[datatype].SO_NHA = data.so_nha
        this[datatype].BLOCK = data.block
        this[datatype].TANG = data.tang
        this[datatype].PHONG = data.phong
        this[datatype].MOTA = data.motathem
        this[datatype].Lat = data.lat
        this[datatype].Lng = data.lng
        if (data.sophong) this[datatype].SOPHONG = data.sophong
        if (data.sotang) this[datatype].SOTANG = data.sotang
        if (data.dientich) this[datatype].DIENTICH = data.dientich
        if (data.loainha_id) this[datatype].LOAINHA_ID = data.loainha_id
        if (data.nhomln_id) this[datatype].NHOMLN_ID = data.nhomln_id
        this[datatype] = JSON.parse(JSON.stringify(this[datatype]))
      },
      getDiaChiKN: function(data) {
        this.getDiaChi(data, 'diachiKN')
      },
      cboLoaiSoWan_SelectedValueChanged(item) {
        if (!item || item <= 0) return
        this.thuebao.hdtb_id_cha = 0
        this.thuebao.thuebao_id_cha = 0
        this.thuebao.mgwan.huongkn = ''
        this.thuebao.mgwan.vhuongkn = ''
        this.listbox.tinh_kn.value = 0
        this.listbox.tinh_kn.value = this.$root.token.getPhanVungID()
        this.diachiKN = this.$options.data.call(this).diachiKN
        this.buttons.btnHuongKNWan = item * 1 == LOAINODE_MGWAN.NODE_CHINH
      },
      async cboLoaiKenh_SelectedValueChanged() {
        if (!this.kt_load) {
          this.loading(true)
          // if (this.listbox.dichvu_vt.value == DICHVUVIENTHONG.MEGAWAN || this.listbox.dichvu_vt.value == DICHVUVIENTHONG.METRONET || this.listbox.dichvu_vt.value == DICHVUVIENTHONG.NOIBO_DADIEM) {
          //   await this.HienThi_CTK_CTC()
          //   this.HienThi_CTK_NIX_ISP(2)
          //   this.HienThi_CTK_NIX_ISP(3)
          // }
          // if (this.listbox.dichvu_vt.value == DICHVUVIENTHONG.TSL || this.listbox.dichvu_vt.value == DICHVUVIENTHONG.NOIBO_2DIEM) await this.HienThi_CTK_CTC()
          await this.HT_GIAODIEN_DONVI()
          this.loading(false)
        }
      },
      ResetThueBao() {
        let olddata = {}
        Object.assign(olddata, this.$options.data.call(this))
        this.thuebao.codinh.somay = olddata.thuebao.codinh.somay
        this.thuebaocd.matn = olddata.thuebaocd.matn
        this.thuebao.codinh.sochinh = olddata.thuebao.codinh.sochinh
        this.thuebaocd.cuocdoitac = olddata.thuebaocd.cuocdoitac
        this.thuebao.didong.txtSoMayDD = olddata.thuebao.didong.txtSoMayDD
        this.thuebao.didong.txtSimDD = olddata.thuebao.didong.txtSimDD
        this.thuebao.didong.txtSoMayNTK = olddata.thuebao.didong.txtSoMayNTK
        this.thuebao.didong.txtTienDuTK = olddata.thuebao.didong.txtTienDuTK
        this.thuebao.gphone.txtSoMayGP = olddata.thuebao.gphone.txtSoMayGP
        this.thuebao.gphone.txtSoSimGphone = olddata.thuebao.gphone.txtSoSimGphone
        this.thuebao.adsl.txtAccountADSL = olddata.thuebao.adsl.txtAccountADSL
        this.thuebao.adsl.txtSoAoAdsl = olddata.thuebao.adsl.txtSoAoAdsl
        this.thuebao.adsl.txtMaTN_ADSL = olddata.thuebao.adsl.txtMaTN_ADSL
        this.listbox.cboGoiCuocAdsl.value = 0
        this.thuebao.adsl.txtCuocDT_Internet = olddata.thuebao.adsl.txtCuocDT_Internet
        this.thuebao.adsl.txtEmailAdsl = olddata.thuebao.adsl.txtEmailAdsl
        this.thuebao.adsl.txtDienThoai_Fsecure = olddata.thuebao.adsl.txtDienThoai_Fsecure
        this.thuebao.adsl.txtSL_Tivi = olddata.thuebao.adsl.txtSL_Tivi
        this.thuebao.adsl.txtCSD_FIBER_TK = olddata.thuebao.adsl.txtCSD_FIBER_TK
        this.thuebao.adsl.txtCTB_FIBER_TK = olddata.thuebao.adsl.txtCTB_FIBER_TK
        this.thuebao.adsl.txtSerialSTB = olddata.thuebao.adsl.txtSerialSTB
        this.thuebao.adsl.tientra = olddata.thuebao.adsl.tientra
        this.thuebao.adsl.txtMaTB_KN = olddata.thuebao.adsl.txtMaTB_KN
        this.thuebao.adsl.megaIOT_vrf = olddata.thuebao.adsl.megaIOT_vrf
        this.thuebao.adsl.megaIOT_LanIP = olddata.thuebao.adsl.megaIOT_LanIP
        this.thuebao.adsl.megaIOT_WanIP = olddata.thuebao.adsl.megaIOT_WanIP
        this.thuebao.adsl.megaIOT_HuongKN = olddata.thuebao.adsl.megaIOT_HuongKN
        this.thuebao.ims.txtMaTBIMS = olddata.thuebao.ims.txtMaTBIMS
        this.thuebao.ims.txtMaTN_IMS = olddata.thuebao.ims.txtMaTN_IMS
        this.thuebao.ims.txtIp_IMS = olddata.thuebao.ims.txtIp_IMS
        this.thuebao.ims.cboTrunk.value = 0
        this.thuebao.mgwan.somay = olddata.thuebao.mgwan.somay
        this.thuebao.mgwan.soao = olddata.thuebao.mgwan.soao
        this.thuebao.mgwan.matn = olddata.thuebao.mgwan.matn
        this.thuebao.mgwan.siteid = olddata.thuebao.mgwan.siteid
        this.thuebao.mgwan.mahdttkd = olddata.thuebao.mgwan.mahdttkd
        this.thuebao.mgwan.huongkn = olddata.thuebao.mgwan.huongkn
        this.thuebao.mgwan.vrfvfi = olddata.thuebao.mgwan.vrfvfi
        this.thuebao.mgwan.cuocnix = olddata.thuebao.mgwan.cuocnix
        this.thuebao.mgwan.soluongip = olddata.thuebao.mgwan.soluongip
        this.thuebao.mgwan.cuocisp = olddata.thuebao.mgwan.cuocisp
        this.thuebao.mgwan.cuocip = olddata.thuebao.mgwan.cuocip
        this.thuebao.mgwan.cuoctc_dt = olddata.thuebao.mgwan.cuoctc_dt
        this.thuebao.mgwan.cuoctc = olddata.thuebao.mgwan.cuoctc
        this.thuebao.mgwan.hopdong_dt = olddata.thuebao.mgwan.hopdong_dt
        this.thuebao.mgwan.cuoctk_dt = olddata.thuebao.mgwan.cuoctk_dt
        this.thuebao.mgwan.cuoctk = olddata.thuebao.mgwan.cuoctk
        this.thuebao.mgwan.cuoctbi = olddata.thuebao.mgwan.cuoctbi
        this.thuebao.mgwan.cuocht = olddata.thuebao.mgwan.cuocht
        this.thuebao.mgwan.lanip = olddata.thuebao.mgwan.lanip
        this.thuebao.mgwan.wanip = olddata.thuebao.mgwan.wanip
        this.thuebao.mgwan.madoicap = olddata.thuebao.mgwan.madoicap
        this.listbox.doitacwan.value = olddata.listbox.doitacwan.value
        this.listbox.doitacwan.isenabled = olddata.listbox.doitacwan.isenabled
        this.listbox.tocdokenh.ischeckedisp = olddata.listbox.tocdokenh.ischeckedisp
        this.listbox.tocdokenh.valueisp = olddata.listbox.tocdokenh.valueisp
        this.listbox.tocdokenh.ischeckednix = olddata.listbox.tocdokenh.ischeckednix
        this.listbox.tocdokenh.valuenix = olddata.listbox.tocdokenh.valuenix
        this.listbox.tocdokenh.ischeckednix = olddata.listbox.tocdokenh.ischeckednix
        this.listbox.tocdokenh.valuenix = olddata.listbox.tocdokenh.valuenix
        this.listbox.loai_tb.valuemgwan = olddata.listbox.loai_tb.valuemgwan
        this.listbox.loaicap.value = olddata.listbox.loaicap.value
        this.listbox.tocdokenh.valuepir = olddata.listbox.tocdokenh.valuepir
        this.listbox.tocdokenh.ischeckedpir = olddata.listbox.tocdokenh.ischeckedpir
        this.listbox.tocdokenh.valuecir = olddata.listbox.tocdokenh.valuecir
        this.thuebao.tsl.txtMaTSL.Text = olddata.thuebao.tsl.txtMaTSL.Text
        this.thuebao.tsl.txtMaHD_TTKD_TSL = olddata.thuebao.tsl.txtMaHD_TTKD_TSL
        this.thuebao.tsl.txtCuocTC_TSL = olddata.thuebao.tsl.txtCuocTC_TSL
        this.thuebao.tsl.txtCuocTK_TSL = olddata.thuebao.tsl.txtCuocTK_TSL
        this.thuebao.tsl.txtCuocTKDT_TSL = olddata.thuebao.tsl.txtCuocTKDT_TSL
        this.thuebao.tsl.txtCuocTBI_TSL = olddata.thuebao.tsl.txtCuocTBI_TSL
        this.thuebao.tsl.txtMaHD_DT_TSL = olddata.thuebao.tsl.txtMaHD_DT_TSL
        this.thuebao.tsl.txtCuocTCDT_TSL = olddata.thuebao.tsl.txtCuocTCDT_TSL
        this.thuebao.tsl.txtCuocHT_TSL = olddata.thuebao.tsl.txtCuocHT_TSL
        this.thuebao.tsl.txtSoAoTSL_Dau = olddata.thuebao.tsl.txtSoAoTSL_Dau
        this.thuebao.tsl.txtSoAoTSL_Cuoi = olddata.thuebao.tsl.txtSoAoTSL_Cuoi
        this.thuebao.tsl.txtSiteIDTSL_Dau = olddata.thuebao.tsl.txtSiteIDTSL_Dau
        this.thuebao.tsl.txtSiteIDTSL_Cuoi = olddata.thuebao.tsl.txtSiteIDTSL_Cuoi
        this.thuebao.tsl.txtVrf_Dau = olddata.thuebao.tsl.txtVrf_Dau
        this.thuebao.tsl.txtVrf_Cuoi = olddata.thuebao.tsl.txtVrf_Cuoi
        this.thuebao.tsl.txtSoSoiQuang = olddata.thuebao.tsl.txtSoSoiQuang
        // Bo sung do loi phat sinh
        this.dsCTM_TBI = []
        this.lvMuaThietbi.list = []
        this.lvDvkhac.list = []
        this.dsDK_DVK_TB = []
        this.dsDC_HDTB = []
        this.dsKM_HDTB = []
        this.ds_tien_khoanmuc = JSON.parse(JSON.stringify(this.listbox.tien_km.list))
        this.dtienld = 0
        this.dvatld = 0
        this.tocdo_id = 0
        this.listbox.cboTocDoThucADSL.value = 0
        this.nhap_tocdo_thuc_adsl = 0
        this.thuebao.tsl.txtMaTSL.ReadOnly = false
        this.kt_thuenganhan = false
        this.filterbox.tratruoc.list = []
        this.filterbox.tratruoc.value = 0
        this.filterbox.tratruoc.ischecked = false
        this.filterbox.khuyenmai.list = []
        this.filterbox.khuyenmai.value = 0
        this.filterbox.khuyenmai.ischecked = false
        // this.LayTienTheoKhoanMuc()
        // if(this.watcherActive) this.sinhma = true;
      },
      getDiaChiDau: async function(data) {
        this.getDiaChi(data, 'diachiDau')
        let ds = await API.post_lay_ds_donviql(this.axios, {
          vap_id: this.diachiDau.AP_ID ? this.diachiDau.AP_ID : 0,
          vkieu: 2,
          vkhu_id: this.diachiDau.KHU_ID ? this.diachiDau.KHU_ID : 0,
          vpho_id: this.diachiDau.PHO_ID ? this.diachiDau.PHO_ID : 0,
          vphuong_id: this.diachiDau.PHUONG_ID
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              return response.data.data
            } else return response.data.data ? response.data.data : []
          })
          .catch(() => {
            return []
          })
        // this.listbox.donvi_dau.list = ds && ds.length>0?ds:[];
        if (!ds || ds.length <= 0) {
          if (this.listbox.donvi_dau.list.length != 1) {
            this.$toast.error('Địa danh này chưa được gán Loại đơn vị Trạm thi công điểm đầu. Hãy liên hệ với Admin để xử lý!')
            return
          }
        } else if (ds.length == 1) {
          this.thuebao.tsl.donvi_dau_id = ds[0]['donvi_id']
          this.LoadDonViDauCuoiCombobox(1)
        } else {
          this.popupComponent = () => import('./Popups/popupChonGrid')
          this.popupComponentName = 'popupChonDVDau'
          this.popupComponentData = {
            ds: this.UpperCasePropertyList(ds),
            cols: [
              { fieldName: 'DONVIQL_ID', headerText: 'ID', allowFiltering: false, allowSorting: false },
              { fieldName: 'TEN_DVQL', headerText: 'Đơn vị', allowFiltering: false, allowSorting: false },
              { fieldName: 'TEN_DV', headerText: 'Tổ thi công', allowFiltering: false, allowSorting: false }
            ],
            keycol: 'DONVIQL_ID',
            allowExit: false,
            tieude: 'Chọn đơn vị quản lý',
            tieudebang: 'Danh sách đơn vị'
          }
          this.Popup('popupComponents')
        }
      },
      getDiaChiCuoi: async function(data) {
        this.getDiaChi(data, 'diachiCuoi')
        let ds = await API.post_lay_ds_donviql(this.axios, {
          vap_id: this.diachiCuoi.AP_ID ? this.diachiCuoi.AP_ID : 0,
          vkieu: 2,
          vkhu_id: this.diachiCuoi.KHU_ID ? this.diachiCuoi.KHU_ID : 0,
          vpho_id: this.diachiCuoi.PHO_ID ? this.diachiCuoi.PHO_ID : 0,
          vphuong_id: this.diachiCuoi.PHUONG_ID
        })
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              return response.data.data
            } else return response.data.data ? response.data.data : []
          })
          .catch(() => {
            return []
          })
        // this.listbox.donvi_cuoi.list = ds && ds.length>0?ds:[];
        if (!ds || ds.length <= 0) {
          if (this.listbox.donvi_cuoi.list.length != 1) {
            this.$toast.error('Địa danh này chưa được gán Loại đơn vị Trạm thi công điểm cuối. Hãy liên hệ với Admin để xử lý!')
            return
          }
        } else if (ds.length == 1) {
          this.thuebao.tsl.donvi_cuoi_id = ds[0]['donvi_id']
          this.LoadDonViDauCuoiCombobox(2)
        } else {
          this.popupComponent = () => import('./Popups/popupChonGrid')
          this.popupComponentName = 'popupChonDVCuoi'
          this.popupComponentData = {
            ds: this.UpperCasePropertyList(ds),
            cols: [
              { fieldName: 'DONVIQL_ID', headerText: 'ID', allowFiltering: false, allowSorting: false },
              { fieldName: 'TEN_DVQL', headerText: 'Đơn vị', allowFiltering: false, allowSorting: false },
              { fieldName: 'TEN_DV', headerText: 'Tổ thi công', allowFiltering: false, allowSorting: false }
            ],
            keycol: 'DONVIQL_ID',
            allowExit: false,
            tieude: 'Chọn đơn vị quản lý',
            tieudebang: 'Danh sách đơn vị'
          }
          this.Popup('popupComponents')
        }
      },
      LoadDonViDauCuoiCombobox(loaidv) {
        let diachi = loaidv == 1 ? this.diachiDau : this.diachiCuoi
        let dv_id = loaidv == 1 ? this.thuebao.tsl.donvi_dau_id : this.thuebao.tsl.donvi_cuoi_id
        let donvi = loaidv == 1 ? this.listbox.donvi_dau : this.listbox.donvi_cuoi
        let api1 = API.post_fn_lay_tt_dvi_daucuoi(this.axios, {
          ds_para: JSON.stringify({
            QUAN_ID: diachi.QUAN_ID,
            PHUONG_ID: diachi.PHUONG_ID,
            PHO_ID: diachi.PHO_ID ? diachi.PHO_ID : 0,
            AP_ID: diachi.AP_ID ? diachi.AP_ID : 0,
            KHU_ID: diachi.KHU_ID ? diachi.KHU_ID : 0,
            TINHKHAC: 0,
            KIEULD_ID: 2
          })
        })
        let api2 = API.post_sp_ht_donvi_ldv(this.axios, { donvi_id: dv_id, loaidvi_id: LOAI_DV.TRAM_VT })
        Promise.all([api1, api2]).then(([resp1, resp2]) => {
          if (resp2 && resp2.data && resp2.data.data && resp2.data.data.length > 0) donvi.list = resp2.data.data
          else donvi.list = []
          if (resp1 && resp1.data && resp1.data.data && resp1.data.data.length > 0) {
            let apidata = JSON.parse(resp1.data.data)
            if (apidata['ERROR_CODE'] == 1) {
              if (apidata['RESULT'] && apidata['RESULT']['VETINH_ID']) donvi.value = apidata['RESULT']['VETINH_ID']
              else this.$toast.error(`Địa danh này chưa được gán Loại đơn vị Trạm thi công điểm ${loaidv == 1 ? 'đầu' : 'cuối'}. Hãy liên hệ với Admin để xử lý!`)
            } else if (apidata['ERROR_CODE'] == -2) {
              let ds = JSON.parse(JSON.stringify(donvi.list))
              this.UpperCasePropertyList(ds)
              this.popupComponent = () => import('./Popups/popupChonGrid')
              this.popupComponentName = loaidv == 1 ? 'popupChonDVDau2' : 'popupChonDVCuoi2'
              this.popupComponentData = {
                ds: ds,
                cols: [
                  { fieldName: 'DONVI_ID', headerText: 'ID', allowFiltering: false, allowSorting: false },
                  { fieldName: 'TEN_DV', headerText: 'TÊN', allowFiltering: false, allowSorting: false }
                ],
                keycol: 'DONVI_ID',
                allowExit: false,
                tieude: 'Chọn đơn vị',
                tieudebang: 'Danh sách đơn vị'
              }
              this.Popup('popupComponents')
            } else this.$toast.error(`Lỗi lấy đơn vị Trạm thi công điểm ${loaidv == 1 ? 'đầu' : 'cuối'}: ${apidata['MESSAGE']}`)
          }
        })
      },
      UpperCasePropertyList(obj) {
        return obj.map(function(item) {
          for (let key in item) {
            let upper = key.toUpperCase()
            // check if it already wasn't uppercase
            if (upper !== key) {
              item[upper] = item[key]
              delete item[key]
            }
          }
          return item
        })
      },
      async LayTienTheoKhoanMuc() {
        /*let muccuoc = 0
        if (
          this.listbox.dichvu_vt.value != DichVuVienThong.ADSL &&
          this.listbox.dichvu_vt.value != DichVuVienThong.MEGAWAN &&
          this.listbox.dichvu_vt.value != DichVuVienThong.METRONET &&
          this.listbox.dichvu_vt.value != DichVuVienThong.NOIBO_DADIEM &&
          this.listbox.dichvu_vt.value != DichVuVienThong.BRCD_DOITAC &&
          this.listbox.dichvu_vt.value != DichVuVienThong.MGW_DOITAC
        ) {
          this.kh_cd = 0
          this.ltb_adsl_id = 1
        } else if (this.listbox.loaihinh_tb.value == LoaiHinhTB.INTERNET_FTTH || this.listbox.loaihinh_tb.value == LoaiHinhTB.WIFI_FIBER) {
          //TODO chua lam do thuoc phan BB
          if (this.listbox.cboLoaiTB.value == LOAITBI_ADSL.ADSL) return
          if (this.listbox.kieu_ld.value == KieuLapDat.DM_ADSL_CO_SAN_DT || this.listbox.kieu_ld.value == KieuLapDat.DM_ADSL_KHONG_SO || this.listbox.kieu_ld.value == KieuLapDat.DM_ADSL_KEM_DT) return
          if (this.listbox.kieu_ld.value == KieuLapDat.MYTV_KHONGSO_CAPDONG) return
        } else if (this.listbox.loaihinh_tb.value == LoaiHinhTB.INTERNET_1260) {
          if (this.listbox.kieu_ld.value == KieuLapDat.DM_CODINH) return
        }
        // let tocdo = this.tocdo_id;
        // muccuoc = this.muccuoc_id;
        if (this.listbox.dichvu_vt.value == 4) {
          //TODO xu ly sau vi khong thuoc phan co dinh
          this.muccuoc_id = this.listbox.muccuoc.value
          this.tocdo_id = this.listbox.cboGoiCuocAdsl.value
        }
        //UR 2.1.4.068
        let pdata = {
          vapld_id: this.diachiLD.AP_ID?this.diachiLD.AP_ID:0,
          vdichvuvt_id: this.listbox.dichvu_vt.value,
          vdoituong_id: this.listbox.doituong.value,
          vkh_cd: this.kh_cd,
          vkhuld_id: this.diachiLD.KHU_ID?this.diachiLD.KHU_ID:0,
          vkieuld_id: this.listbox.kieu_ld.value,
          vloaihd_id: 0,
          vloaitb_id: this.listbox.loaihinh_tb.value,
          vltb_adsl_cu_id: 0,
          vltb_adsl_id: this.ltb_adsl_id,
          vltb_adsl_moi_id: 0,
          vmuccuoc_id: this.muccuoc_id,
          vngay_yc: this.NgayYeuCau && (this.NgayYeuCau + '').trim().length > 10 ? (this.NgayYeuCau + '').trim().substring(0, 10) : this.NgayYeuCau,
          vphanvung_id: this.$root.token.getPhanVungID(),
          vphold_id: this.diachiLD.PHO_ID?this.diachiLD.PHO_ID:0,
          vphuongld_id: this.diachiLD.PHUONG_ID?this.diachiLD.PHUONG_ID:0,
          vtocdo_cu_id: this.tocdo_cu_id,
          vtocdo_id: this.tocdo_id,
          vtocdo_moi_id: this.tocdo_moi_id,
          vtrangbi_id: this.trangbi_id //BUG can xem lai cho nay khi khong dung cho co dinh
        }
        let api = cache.getters.getFromCache('get_lay_tien_khoanmuc_ld_v2', pdata)
        if (!api) api = API.get_lay_tien_khoanmuc_ld_v2(this.axios, { params: pdata })
        else
          api = new Promise((resolve, reject) => {
            resolve(api)
          })
        //await API.get_lay_tien_khoanmuc_ld_v2(this.axios,{"params":pdata}).then((response) => {
        await api.then((response) => {
          cache.dispatch('addToCache', { key: 'get_lay_tien_khoanmuc_ld_v2', subkey: pdata, data: response })
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            let ds = response.data.data
            if (ds.length > 0) {
              this.HienThiTienLapDat(ds)
              this.LayTongTien_HDTB()
            } else {
              this.dtienld = 0
              this.dtiencd = 0
            }
          }
        })*/
      },
      async HT_GIAODIEN_DONVI() {
        if (!this.listbox.kieu_ld.value || this.listbox.kieu_ld.value <= 0) return
        let tocdotn_id = 0
        this.muccuoctn_id = 0
        // TODO xu ly cac phan nay theo qua trinh lam
        if (this.listbox.dichvu_vt.value == DichVuVienThong.ADSL || this.listbox.dichvu_vt.value == DichVuVienThong.HATANG_VIENTHONG || this.listbox.dichvu_vt.value == DichVuVienThong.BRCD_DOITAC) {
          tocdotn_id = this.listbox.cboGoiCuocAdsl.value
          if (this.listbox.muccuoc.isenabled && this.listbox.dichvu_vt.value != DichVuVienThong.BRCD_DOITAC) this.muccuoctn_id = this.listbox.muccuoc.value
        } else if (this.listbox.dichvu_vt.value == DichVuVienThong.DICHVU_CNTT || this.listbox.dichvu_vt.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.listbox.dichvu_vt.value == DichVuVienThong.TRUNGTAM_DULIEU || this.listbox.dichvu_vt.value == DichVuVienThong.HOINGHI_TRUYENHINH)
          tocdotn_id = this.listbox.cboGoiCuocAdsl.value
        else if (this.listbox.dichvu_vt.value == DichVuVienThong.MEGA_EYES) tocdotn_id = this.listbox.cboGoiCuocAdsl.value
        else if ((this.listbox.dichvu_vt.value == DichVuVienThong.TSL || this.listbox.dichvu_vt.value == DichVuVienThong.TSL_DOITAC || this.listbox.dichvu_vt.value == DichVuVienThong.NOIBO_2DIEM) && this.listbox.tocdokenh.valuetsl != null && this.listbox.tocdokenh.valuetsl != '') tocdotn_id = this.listbox.tocdokenh.valuetsl
        //Convert.ToInt32(cboTocDoKenhTSL.EditValue);
        else if (this.listbox.dichvu_vt.value == DichVuVienThong.MEGAWAN || this.listbox.dichvu_vt.value == DichVuVienThong.METRONET || this.listbox.dichvu_vt.value == DichVuVienThong.MGW_DOITAC || this.listbox.dichvu_vt.value == DichVuVienThong.NOIBO_DADIEM) tocdotn_id = this.listbox.tocdokenh.valuecir
        // if (this.listbox.dichvu_vt.value == DichVuVienThong.CO_DINH){
        let pdata = {
          DICHVUVT_ID: this.listbox.dichvu_vt.value,
          TOCDOTN_ID: tocdotn_id ? tocdotn_id : 0,
          MUCCUOCTN_ID: this.muccuoctn_id,
          LOAITB_ID: this.listbox.loaihinh_tb.value,
          KIEULD_ID: this.listbox.kieu_ld.value,
          DAIVT_ID: this.cbo_donviTC_id.Value,
          HT_LOC_CBO_TRAM: this.ht_loc_cbo_tram ? 1 : 0
        }
        pdata.PHUONGLD_ID = this.diachiLD.PHUONG_ID
        pdata.PHOLD_ID = this.diachiLD.PHO_ID
        pdata.APLD_ID = this.diachiLD.AP_ID
        pdata.KHULD_ID = this.diachiLD.KHU_ID
        pdata.DACDIEM_ID = this.diachiLD.DACDIEM_ID
        let api = cache.getters.getFromCache('post_fn_lay_tt_giaodien_dvi', pdata)
        if (!api) api = API.post_fn_lay_tt_giaodien_dvi(this.axios, { ds_para: JSON.stringify(pdata) })
        else
          api = new Promise((resolve, reject) => {
            resolve(api)
          })
        //await API.post_fn_lay_tt_giaodien_dvi(this.axios,{"ds_para":JSON.stringify(pdata)}).then(
        await api.then((response) => {
          cache.dispatch('addToCache', { key: 'post_fn_lay_tt_giaodien_dvi', subkey: pdata, data: response })
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            let dulieuapi = JSON.parse(response.data.data)
            if (dulieuapi['ERROR_CODE'] != 1) {
              this.$toast.error(dulieuapi['MESSAGE'])
              return
            }
            if (dulieuapi['RESULT']['DAIVT_ID']) this.listbox.donvi_ql.value = dulieuapi['RESULT']['DAIVT_ID']
            if (dulieuapi['RESULT']['DT_CONTROLS']) this.DT_CONTROLS.list = dulieuapi['RESULT']['DT_CONTROLS']
            if (dulieuapi['RESULT']['DS_DATA']) this.DT_CONTROLS.data = dulieuapi['RESULT']['DS_DATA']
            // if(dulieuapi['RESULT']['DS_GT_DVI']) this.DT_CONTROLS.value = dulieuapi['RESULT']['DS_GT_DVI'];
            let dsValues = dulieuapi['RESULT']['DS_GT_DVI']
            for (var i = 0; i < this.DT_CONTROLS.data.length; i++) {
              if (!this.DT_CONTROLS.value[i] && dsValues && dsValues[i])
                this.DT_CONTROLS.value[i] = dsValues[i]
              if (this.DT_CONTROLS.data[i] && this.DT_CONTROLS.data[i].length > 0) {
                if (!this.DT_CONTROLS.value[i] || this.DT_CONTROLS.tagvalue[i] != (this.DT_CONTROLS.data[i][0].LOAIDV_ID ? this.DT_CONTROLS.data[i][0].LOAIDV_ID : this.DT_CONTROLS.list[i].LOAIDV_DICH_ID))
                  this.DT_CONTROLS.value[i] = this.DT_CONTROLS.data[i][0].DONVI_ID
                this.DT_CONTROLS.tagvalue[i] = this.DT_CONTROLS.data[i][0].LOAIDV_ID ? this.DT_CONTROLS.data[i][0].LOAIDV_ID : this.DT_CONTROLS.list[i].LOAIDV_DICH_ID
              }
              if (this.kiemtra_nhanvien_vti == 1 && this.DT_CONTROLS.data[i]
                && (this.DT_CONTROLS.data[i][0]['loaidv_dich_id'.toUpperCase()] == LOAI_DV.TTKD_NGHIEMTHU
                || this.DT_CONTROLS.data[i][0]['loaidv_dich_id'.toUpperCase()] == LOAI_DV.HOANTHIEN_HOSO))
                this.DT_CONTROLS.value[i] = 35133//Phòng Khách hàng doanh nghiệp quốc tê
            }
            this.DT_CONTROLS.dichvu_vt = this.listbox.dichvu_vt.value
            this.DT_CONTROLS.key += 1
          }
        })
        // }
        //TODO doan con lai co ve khong lam gi hon ngoai gan gia tri mac dinh (DS_GT_DVI), do do khong can thuc hien gi them.
        await this.LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2()
        if (this.diachiLD.PHUONG_ID && this.diachiLD.PHUONG_ID > 0) {
          pdata = { donvi_ql_id: this.listbox.donvi_ql.value, loaidv_id: LOAI_DV.TRAM_VT }
          let api = cache.getters.getFromCache('post_sp_lay_ds_tram_thicong_theo_donvi', pdata)
          if (!api) api = API.post_sp_lay_ds_tram_thicong_theo_donvi(this.axios, pdata)
          else
            api = new Promise((resolve, reject) => {
              resolve(api)
            })
          // await API.post_sp_lay_ds_tram_thicong_theo_donvi(this.axios, pdata).then(
          await api.then((response) => {
            cache.dispatch('addToCache', {
              key: 'post_sp_lay_ds_tram_thicong_theo_donvi',
              subkey: pdata,
              data: response
            })
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              for (var i = 0; i < this.DT_CONTROLS.list.length; i++) {
                if (this.DT_CONTROLS.list[i].LOAIDV_DICH_ID == LOAI_DV.TRAM_VT || this.DT_CONTROLS.list[i].LOAIDV_DICH_ID == LOAI_DV.TRAM_CAP_PORT) {
                  this.DT_CONTROLS.data[i] = this.UpperCasePropertyList(response.data.data).filter((x) => x.DONVI_ID > 0)
                  if (this.DT_CONTROLS.data[i] && this.DT_CONTROLS.data[i].length > 0) {
                    let fdata = this.DT_CONTROLS.data[i].filter((x) => x.DONVI_ID == this.DT_CONTROLS.value[i] && x.DONVI_ID > 0)
                    if (fdata && fdata.length > 0) this.DT_CONTROLS.value[i] = fdata[0]['DONVI_ID'] ? fdata[0]['DONVI_ID'] : fdata[1] ? fdata[1]['DONVI_ID'] : 0
                    else this.DT_CONTROLS.value[i] = this.DT_CONTROLS.data[i][0]['DONVI_ID'] ? this.DT_CONTROLS.data[i][0]['DONVI_ID'] : 0
                    this.DT_CONTROLS.key += 1
                  }
                }
                if (this.DT_CONTROLS.list[i].LOAIDV_DICH_ID == LOAI_DV.TTKD_NGHIEMTHU) this.DT_CONTROLS.value[i] = this.$root.token.getDonViID()
              }
            }
          })
        }
        //this.ReloadTramThiCong()
      },
      LAY_DONVI_THEO_LOAIDV_DIACHI_LD_V2() {
        let newval = this.diachiLD
        let pdata1 = {
          DT_CONTROLS: this.DT_CONTROLS.list,
          PHIEUHS_ID: this.PHIEUHS_ID ? this.PHIEUHS_ID : 0,
          PHUONG_ID: newval.PHUONG_ID ? newval.PHUONG_ID : 0,
          PHO_ID: newval.PHO_ID ? newval.PHO_ID : 0,
          AP_ID: newval.AP_ID ? newval.AP_ID : 0,
          KHU_ID: newval.KHU_ID ? newval.KHU_ID : 0,
          DACDIEM_ID: newval.DACDIEM_ID ? newval.DACDIEM_ID : 0,
          TTND_DV_ID: this.$root.token.getNguoiDungID(),
          HDKH_ID: this.khachhang.hdkh_id,
          CTV_ID: this.filterbox.ctv.value,
          NGUOIGT_ID: this.filterbox.nguoigt.value
        }
        let api = cache.getters.getFromCache('post_fn_lay_donvi_theo_loaidv_diachi_ld_v2', pdata1)
        if (!api) api = API.post_fn_lay_donvi_theo_loaidv_diachi_ld_v2(this.axios, { ds_para: JSON.stringify(pdata1) })
        else
          api = new Promise((resolve, reject) => {
            resolve(api)
          })
        api.then((response) => {
          cache.dispatch('addToCache', {
            key: 'post_fn_lay_donvi_theo_loaidv_diachi_ld_v2',
            subkey: pdata1,
            data: response
          })
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            let dulieuapi = JSON.parse(response.data.data)
            if (dulieuapi['ERROR_CODE'] != 1) {
              this.$toast.error(dulieuapi['MESSAGE'])
              return
            }
            if (dulieuapi['RESULT']['DS_GT_DVI'] && dulieuapi['RESULT']['DS_GT_DVI'].length > 0 && this.DT_CONTROLS.data.length == dulieuapi['RESULT']['DS_GT_DVI'].length) {
              let dsValues = dulieuapi['RESULT']['DS_GT_DVI']
              for (var i = 0; i < this.DT_CONTROLS.data.length; i++) {
                if (dsValues[i]) this.DT_CONTROLS.value[i] = dsValues[i] // !this.DT_CONTROLS.value[i] &&  <- bo dieukien nay do khong load
                if (this.DT_CONTROLS.data[i] && this.DT_CONTROLS.data[i].length > 0) {
                  if (!this.DT_CONTROLS.value[i]) this.DT_CONTROLS.value[i] = this.DT_CONTROLS.data[i][0].DONVI_ID
                  if (this.DT_CONTROLS.data[i][0].LOAIDV_ID) this.DT_CONTROLS.tagvalue[i] = this.DT_CONTROLS.data[i][0].LOAIDV_ID
                }
              }
              this.DT_CONTROLS.dichvu_vt = this.listbox.dichvu_vt.value
              this.DT_CONTROLS.key += 1
            }
          }
        })
      },
      async cboCongNghe_SelectedValueChanged() {
        /*try {
          this.loading(true)
          if (this.ccongnghe.length > 0) {
            if (!this.listbox.congnghe.value || this.listbox.congnghe.value <= 0) return
            if (this.kt_load == false) {
              // let dk = "sudung = 1"; // khong dung, da tich hop trong stored
              await API.post_sp_ht_tocdo_adsl_theo_loaitb(this.axios, { congnghe_id: this.listbox.congnghe.value ? this.listbox.congnghe.value : 0, kieuld_id: this.listbox.kieu_ld.value, loaitb_id: this.listbox.loaihinh_tb.value }).then(async (response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.listbox.cboGoiCuocAdsl.list = response.data.data
                  this.listbox.cboTocDoThucADSL.list = response.data.data
                  if (this.listbox.cboGoiCuocAdsl.list && this.listbox.cboGoiCuocAdsl.list.length > 0 && (!this.listbox.cboGoiCuocAdsl.value || !this.listbox.cboGoiCuocAdsl.list.find((x) => x.tocdo_id == this.listbox.cboGoiCuocAdsl.value)))
                    if (this.listbox.cboGoiCuocAdsl.list.find((x) => x.tocdo_id > 0)) this.listbox.cboGoiCuocAdsl.value = this.listbox.cboGoiCuocAdsl.list.find((x) => x.tocdo_id > 0).tocdo_id
                    else this.listbox.cboGoiCuocAdsl.value = this.listbox.cboGoiCuocAdsl.value ? this.listbox.cboGoiCuocAdsl.value : 0
                  await this.cboGoiCuocAdsl_SelectedValueChanged(this.listbox.cboGoiCuocAdsl.value)
                  // this.listbox.cboGoiCuocAdsl.values = [this.listbox.cboGoiCuocAdsl.value];
                } else {
                  this.listbox.cboGoiCuocAdsl.list = []
                  this.listbox.cboTocDoThucADSL.list = []
                  this.listbox.cboGoiCuocAdsl.value = 0
                  this.listbox.cboTocDoThucADSL.value = 0
                }
              })
            }
          }
        } catch (ex) {
          this.$toast.error('Lỗi khi chọn công nghệ: ' + ex);
          if(this.debug) console.error('Loi khi chon cong nghe??????',ex);
        } finally {
          this.loading(false)
        }*/
      },
      popupDSHuongKN() {
        //this.popupComponent = () => import('./Popups/popupDSHuongKN' + (this.thamsomacdinh['SD_FRMDSHUONGKN_V2'] == 1 ? 'V2' : ''))
        this.popupComponent = () => import('./Popups/popupDSHuongKNV2')
        this.popupComponentName = 'popupDSHuongKN'
        this.popupComponentData = {
          kieu: this.listbox.chu_quan.valuewan == 9 ? 0 : 1,
          dichvuvt_id: this.listbox.dichvu_vt.value,
          tinhkn_id: this.listbox.tinh_kn.value
        } // O day dung enums CHUQUAN.VNPT_TINH nhung chi co 1 gia tri duy nhat = 9 nen loai bo enums luon!
        this.Popup('popupComponents')
      },
      getDiaChiTB: async function(data) {
        this.getDiaChi(data, 'diachiTB')
        if (!this.diachiLD.DIACHI || (this.diachiLD.DIACHI == this.diachiKH.DIACHI && this.diachiLD.DIACHI != this.diachiTB.DIACHI)) this.diachiLD = Object.assign({}, this.diachiTB)
        // if ([DichVuVienThong.MEGAWAN, DichVuVienThong.TSL, DichVuVienThong.ADSL].indexOf(this.listbox.dichvu_vt.value * 1) >= 0 || [LoaiHinhTB.INTERNET_MYTV, LoaiHinhTB.MAIL_SMD, LoaiHinhTB.Web_Hosting].indexOf(this.listbox.loaihinh_tb.value * 1) >= 0) await this.SinhMaDVVT()
      },
      getDiaChiLD: function(data) {
        this.getDiaChi(data, 'diachiLD')
      },
      async capnhat() {
        try {

        if (this.tsbtnCapNhat.Enable == true) return ;

        await this.TaoDuLieu_DiaChi_V2();

        let v_ds_para_tt = {
          thanhtoan_id:this.thanhtoan_id,
          ten_tt: this.txt_tenTT.Value.replace("'", "*$#"),
          diachi_tt: this.txt_diachiTT.Value,
          diachi_ct:  this.txt_diachiCT.Value,
          hinhthuc_tt: this.cbo_HTTT.Value,
          mst: this.txt_mst_TT.Value,
          so_dt_tt: this.txt_soDT_TT.Value,
          email: this.txt_emailTT.Value,
          js_diachi_tt: this.js_diachi_tt
        }
        let v_ds_para_kh = {
          khachhang_id: this.khachhang_id,
          ten_kh: this.txt_tenKH.Value.replace("'", "*$#"),
          diachi_kh: this.txt_diachiKH.Value,
          loai_gt: this.cbo_giaytoKH.Value,
          so_gt: this.txt_soGT.Value,
          ngay_cap: this.txt_ngaycap.Value,
          nganh_nghe: this.cbo_nganhngheloc.Value,
          loai_kh: this.cbo_loaiKH.Value,
          so_dt_kh: this.txt_telKH.Value ? this.txt_telKH.Value.toString().trim() : null,
          mst_kh: this.txt_mstKH.Value ? this.txt_mstKH.Value.trim() : null,
          js_diachi_kh: this.js_diachi_kh
        }
        let donvi_cc = 0;
        for (var i = 0; i < this.DT_CONTROLS.list.length; i++) {
          if(!this.DT_CONTROLS.value[i] || !this.DT_DV(i) || !this.DT_DV(i).find(x=>x.id==this.DT_CONTROLS.value[i])) {
            continue;
          }
          /*let row = {}
          row.HDTB_ID = this.hdtb_id
          row.LOAIDV_ID = this.DT_CONTROLS.list[i].LOAIDV_DICH_ID ? this.DT_CONTROLS.list[i].LOAIDV_DICH_ID : 0
          row.DONVI_ID = this.DT_CONTROLS.value[i]
          row.KIEUDV_ID = 2
          this.dsHDTBDV.push(row)*/
          if (this.DT_CONTROLS.list[i].LUONG_ID == 1047) donvi_cc = this.DT_CONTROLS.value[i]
        }

        let _ngay_sd = this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.txtNgaySD.Valuetsl : this.txtNgaySD.Valuewan;

        let v_ds_para_tb = {
          dichvu_vt: this.listbox.dichvu_vt.value,
          loaihinh_tb: this.listbox.loaihinh_tb.value,
          cong_nghe: this.listbox.congnghe.value,
          kieu_yc: this.listbox.kieu_ld.value,
          ma_tb: this.txt_maTB.Value,
          ten_tb: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.thuebao.tsl.ten_tb : this.thuebao.mgwan.ten_tb,
          diachi_tb: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.thuebao.tsl.diachi_tb : this.thuebao.mgwan.diachi_tb,
          diachi_ld: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.thuebao.tsl.diachi_ld : this.thuebao.mgwan.diachi_ld,
          js_diachi_tb: this.js_diachi_tb,
          js_diachi_ld: this.js_diachi_ld,
          loai_node: this.loaisowan,
          huong_kn: this.thuebao.mgwan.huongkn,
          diachi_kn: this.diachiKN.DIACHI,
          tinh_kn: this.listbox.tinh_kn.value,
          loai_kenh: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.listbox.loaikenh.valuetsl : this.listbox.loaikenh.value,
          thoi_han: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.listbox.thoihan.valuetsl : this.listbox.thoihan.valuewan,
          phan_loai: this.listbox.phanloai_dv.value,
          phan_tach: this.listbox.ht_phantach_tocdo.value,
          trang_bi: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.listbox.trangbi.valuetsl : this.listbox.trangbi.valuewan,
          tocdo_nix: this.listbox.tocdokenh.valuenix,
          chu_quan: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.listbox.chu_quan.valuetsl : this.listbox.chu_quan.valuewan,
          tocdo_cir: this.listbox.tocdokenh.valuecir,
          tocdo_pir: this.listbox.tocdokenh.valuepir,
          soluong_ip: this.thuebao.mgwan.soluongip,
          tocdo_isp: this.listbox.tocdokenh.valueisp,
          diachi_dau: this.diachiDau.DIACHI,
          diachi_cuoi: this.diachiCuoi.DIACHI,
          loaicap_dau: this.listbox.loaicap.valuedau,
          loaicap_cuoi: this.listbox.loaicap.valuecuoi,
          ma_vtn: this.thuebao.vtn,
          ma_erp: this.thuebao.erp,
          ma_crm: this.thuebao.crm,
          macap_tn: 0,
          doitac_id: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.listbox.doitactsl.value : this.listbox.doitacwan.value,
          donvi_cc: donvi_cc,
          doituong_id: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.cbo_doituong_id.Valuetsl : this.cbo_doituong_id.Valuewan,
          cuoc_ip: this.thuebao.mgwan.cuocip ?? 0,
          cuoc_nix:this.thuebao.mgwan.cuocnix ?? 0,
          cuoc_isp: this.thuebao.mgwan.cuocisp ?? 0,
          cuoc_tc: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.thuebao.tsl.txtCuocTC_TSL : this.thuebao.mgwan.cuoctc,
          cuoc_tk: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.thuebao.tsl.txtCuocTK_TSL : this.thuebao.mgwan.cuoctk,
          cuoc_tbi: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.thuebao.tsl.txtCuocTBI_TSL : this.thuebao.mgwan.cuoctbi,
          cuoc_ht: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.thuebao.tsl.txtCuocHT_TSL : this.thuebao.mgwan.cuocht,
          cuoc_tkdt: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.thuebao.tsl.txtCuocTKDT_TSL : this.thuebao.mgwan.cuoctk_dt,
          cuoc_tcdt: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.thuebao.tsl.txtCuocTCDT_TSL : this.thuebao.mgwan.cuoctc_dt,
          ngay_sd: moment(_ngay_sd).format('DD/MM/YYYY HH:mm:ss'),
          loaitien_id: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.cboLoaiTien.valuetsl : this.cboLoaiTien.valuewan,
          status: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.cboSoNgay.valuetsl : this.cboSoNgay.valuewan,
          limit: this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL ? this.txtMucThuetsl : this.txtMucThuewan
        }

        if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THANHTOAN) {
          if (this.isNullOrEmpty(this.txt_maTT.Value)) return this.$root.toastError("Mã thanh toán không để trống !")
          if (this.isNullOrEmpty(this.txt_tenTT.Value)) return this.$root.toastError("Tên thanh toán không để trống !")
          if (this.isNullOrEmpty(this.txt_diachiTT.Value)) return this.$root.toastError("Địa chỉ thanh toán không để trống !")
          if (this.isNullOrEmpty(this.txt_diachiCT.Value)) return this.$root.toastError("Địa chỉ CT không để trống !")
          if (this.isNullOrEmpty(this.cbo_HTTT.Value)) return this.$root.toastError("Hình thức TT không để trống !")
          //if (this.isNullOrEmpty(this.txt_mst_TT.Value)) return this.$root.toastError("Mã số thuế không để trống !")
          //if (this.isNullOrEmpty(this.txt_soDT_TT.Value)) return this.$root.toastError("Số ĐT không để trống !")
          if (!this.isNullOrEmpty(this.txt_mst_TT.Value) && this.txt_mst_TT.Value.length < 10) {
            return this.$root.toastError('Mã số thuế tối thiểu 10 ký tự!')
          }
          if (!this.isNullOrEmpty(this.txt_mst_TT.Value) && this.txt_mst_TT.Value.includes(' ')) {
            return this.$root.toastError('Mã số thuế không được có khoảng trống.!')
          }
          if (this.txt_diachiTT.Value && this.txt_diachiTT.Value.length > 500) {
            this.$root.toastError('Địa chỉ KH dữ liệu vượt quá độ dài cho phép.')
            return false
          }
          if (this.txt_diachiCT.Value && this.txt_diachiCT.Value.length > 500) {
            this.$root.toastError('Địa chỉ CT dữ liệu vượt quá độ dài cho phép.')
            return false
          }
          //if (!this.KiemTra_SDT_V2(this.txt_soDT_TT.Value, 'THANHTOAN'))
            //return false
        } else if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_KHACHHANG) {
          if (this.isNullOrEmpty(this.txt_maKH.Value)) return this.$root.toastError("Mã khách hàng không để trống !")
          //if (this.isNullOrEmptyOrNumber(this.cbo_loaiKH.Value)) return this.$root.toastError("Loại KH không để trống !")
          //if (this.isNullOrEmpty(this.txt_mahopdong.Value)) return this.$root.toastError("Mã HĐ không để trống !")
          if (this.isNullOrEmpty(this.txt_diachiKH.Value)) return this.$root.toastError("Địa chỉ KH không để trống !")
          if (this.isNullOrEmpty(this.txt_ngaysn.Value)) return this.$root.toastError("Ngày sinh không để trống !")
          //if (this.isNullOrEmpty(this.cbo_nganhngheloc.Value)) return this.$root.toastError("Ngành nghề không để trống !")
          //if (this.isNullOrEmpty(this.txt_telKH.Value)) return this.$root.toastError("Điện thoại không để trống !")
          //if (this.isNullOrEmptyOrNumber(this.cbo_giaytoKH.Value)) return this.$root.toastError("Loại giấy tờ không để trống !")
          //if (this.isNullOrEmpty(this.txt_soGT.Value)) return this.$root.toastError("Số giấy tờ không để trống !")
          //if (this.isNullOrEmpty(this.txt_ngaycap.Value)) return this.$root.toastError("Ngày cấp không để trống !")
          //if (this.isNullOrEmpty(this.txt_mstKH.Value)) return this.$root.toastError("Mã số thuế không để trống !")
          //if (this.isNullOrEmpty(this.txt_telKH.Value)) return this.$toast.error("Số điện thoại khách hàng không để trống !")
          if (!this.isNullOrEmpty(this.txt_mstKH.Value) && this.txt_mstKH.Value.length < 10) {
            return this.$root.toastError('Mã số thuế tối thiểu 10 ký tự!')
          }
          if (!this.isNullOrEmpty(this.txt_mstKH.Value) && this.txt_mstKH.Value.includes(' ')) {
            return this.$root.toastError('Mã số thuế không được có khoảng trống.!')
          }
          if (this.txt_ngaysn.Value && !moment(this.txt_ngaysn.Value, 'DD/MM/YYYY', true).isValid()) {
            return this.$root.toastError('Ngày sinh không đúng định dạng ngày tháng dd/mm/yyyy!')
          }
          if (this.txt_ngaycap.Value && !moment(this.txt_ngaycap.Value, 'DD/MM/YYYY', true).isValid()) {
            return this.$root.toastError('Ngày cấp giấy tờ không đúng định dạng ngày tháng dd/mm/yyyy!')
          }
          if (this.txt_diachiKH.Value && this.txt_diachiKH.Value.length > 500) {
            return this.$root.toastError('Địa chỉ KH dữ liệu vượt quá độ dài cho phép 500 ký tự.')
          }
          if (this.txt_soGT.Value && this.txt_soGT.Value.length > 80) {
            return this.$root.toastError('Số giấy tờ dữ liệu vượt quá độ dài cho phép 80 ký tự.')
          }
          //if (!this.KiemTra_SDT_V2(this.txt_telKH.Value, 'KHACHHANG'))
            //return false
        } else if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THUEBAO) {
          if (this.cbo_dichvu_id.Value == DICHVUVIENTHONG.TSL) {
            if (this.isNullOrEmptyOrNumber(this.listbox.dichvu_vt.value)) return this.$root.toastError("Dịch vụ không để trống !")
            if (this.isNullOrEmptyOrNumber(this.listbox.loaihinh_tb.value)) return this.$root.toastError("Loại hình không để trống !")
            //if (this.isNullOrEmptyOrNumber(this.listbox.kieu_ld.value)) return this.$root.toastError("Kiểu yêu cầu không để trống !")
            if (this.isNullOrEmpty(v_ds_para_tb.ten_tb)) return this.$root.toastError("Tên thuê bao không để trống !")
            //if (this.isNullOrEmpty(v_ds_para_tb.diachi_tb)) return this.$root.toastError("Địa chỉ TB không để trống !")
            //if (this.isNullOrEmpty(v_ds_para_tb.diachi_ld)) return this.$root.toastError("Địa chỉ LĐ không để trống !")
            if (this.isNullOrEmptyOrNumber(v_ds_para_tb.chu_quan)) return this.$root.toastError("Chủ quản không để trống !")
            if (this.isNullOrEmptyOrNumber(v_ds_para_tb.thoi_han)) return this.$root.toastError("Thời hạn không để trống !")
            if (this.isNullOrEmptyOrNumber(v_ds_para_tb.loai_kenh)) return this.$root.toastError("Loại kênh không để trống !")
            //if (this.isNullOrEmpty(this.diachiDau.DIACHI)) return this.$root.toastError("Địa chỉ đầu không để trống !")
            //if (this.isNullOrEmpty(this.diachiCuoi.DIACHI)) return this.$root.toastError("Địa chỉ cuối không để trống !")
            //if (this.isNullOrEmptyOrNumber(this.listbox.loaicap.valuedau)) return this.$root.toastError("Loại cáp đầu không để trống !")
            //if (this.isNullOrEmptyOrNumber(this.listbox.loaicap.valuecuoi)) return this.$root.toastError("Loại cáp cuối không để trống !")
          } else if (this.cbo_dichvu_id.Value == DICHVUVIENTHONG.MEGAWAN || this.cbo_dichvu_id.Value == DICHVUVIENTHONG.METRONET) {
            if (this.isNullOrEmptyOrNumber(this.listbox.dichvu_vt.value)) return this.$root.toastError("Dịch vụ không để trống !")
            if (this.isNullOrEmptyOrNumber(this.listbox.loaihinh_tb.value)) return this.$root.toastError("Loại hình không để trống !")
            //if (this.isNullOrEmptyOrNumber(this.listbox.kieu_ld.value)) return this.$root.toastError("Kiểu yêu cầu không để trống !")
            if (this.isNullOrEmpty(v_ds_para_tb.ten_tb)) return this.$root.toastError("Tên thuê bao không để trống !")
            //if (this.isNullOrEmpty(v_ds_para_tb.diachi_tb)) return this.$root.toastError("Địa chỉ TB không để trống !")
            //if (this.isNullOrEmpty(v_ds_para_tb.diachi_ld)) return this.$root.toastError("Địa chỉ LĐ không để trống !")
            if (this.isNullOrEmptyOrNumber(v_ds_para_tb.chu_quan)) return this.$root.toastError("Chủ quản không để trống !")
            if (this.isNullOrEmptyOrNumber(v_ds_para_tb.thoi_han)) return this.$root.toastError("Thời hạn không để trống !")
            if (this.isNullOrEmptyOrNumber(v_ds_para_tb.loai_kenh)) return this.$root.toastError("Loại kênh không để trống !")
            /*if(this.listbox.loaihinh_tb.value != this.LoaiHinhTB.MGW_INTERNET_TRUCTIEP
              && (this.listbox.tocdokenh.valuecir == 0 || this.listbox.tocdokenh.valuecir == '')){
                  this.$root.toastError("Vui lòng chọn tốc độ CIR !");
                  return;
            }*/
            /*if(this.cbo_dichvu_id.Value == DICHVUVIENTHONG.MEGAWAN
                && this.listbox.loaihinh_tb.value == this.LoaiHinhTB.MGW_INTERNET_TRUCTIEP
                && (this.listbox.tocdokenh.valueisp == 0 || this.listbox.tocdokenh.valueisp == ''))
            {
                  this.$root.toastError("Vui lòng chọn tốc độ ISP !");
                  return;
            }*/
            /*if(this.cbo_dichvu_id.Value == DICHVUVIENTHONG.MEGAWAN
              && this.listbox.loaihinh_tb.value == this.LoaiHinhTB.MGW_INTERNET_TRUCTIEP
              && (this.listbox.tocdokenh.valuenix == 0 || this.listbox.tocdokenh.valuenix == '')) {
              return this.$root.toastError("Vui lòng chọn tốc độ NIX !");
            }*/
          }
        }



          let data = {
            p_ds_para_tt: this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THANHTOAN ? JSON.stringify(v_ds_para_tt) : JSON.stringify({}),
            p_ds_para_kh: this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_KHACHHANG ? JSON.stringify(v_ds_para_kh) : JSON.stringify({}),
            p_ds_para_tb: this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THUEBAO ? JSON.stringify(v_ds_para_tb) : JSON.stringify({}),
            p_id_row: this.row_id
          }
          //let rs = await this.$root.context.post('/web-hopdong/hopdong/fn_capnhat_thongtin_khachhang_doisoat', data);
          let rs = await this.$root.context.post('/web-hopdong/hopdong/fn_capnhat_thongtin_khachhang_doisoat_v2', data);
          console.log(rs);
          if (rs.error_code == 'BSS-00000000') {
            this.$root.toastSuccess('Cập nhật thành công!')
          } else {
            return  this.$root.toastError('Cập nhật thất bại : ' + rs.message)
          }
        } catch (e) {
          console.log(e);
        }
      },
      isNullOrEmpty: function (value) {
        return (
          value == undefined || value == null || value.toString().trim() == ""
        );
      },
      isNullOrEmptyOrNumber: function (value) {
        return (
          value == undefined || value == null || value.toString().trim() == "" || value == 0
        );
      },
      popupClosed: async function(data) {
        try {
          this.loading(true)
          switch (this.popupComponentName) {
            case 'popupDSHuongKN':
              this.thuebao.mgwan.huongkn = data.vma_tb
              this.thuebao.mgwan.vhuongkn = data.vma_tb
              this.diachiKN.DIACHI = data.diachi_ld
              this.thuebao.loaisochinh = data.iloai // FIXME check lai cho nay xem link vao dau????
              this.listbox.tinh_kn.value = data.itinh_kn_id
              break
            case 'popup-ChonKhuyenMai':
              if (data.khuyenmai) {
                this.cbo_khuyenmai_id.Value = data.khuyenmai.khuyenmai_id
              }
              break
            case 'popup-SearchAccount':
              if (data.ma_tb) {
                this.txt_maTB.Value = data.ma_tb
              }
              break
            case 'popup-SearchContractModal':
              if (data.ma_gd) {
                this.txt_magiaodich.Value = data.ma_gd
              }
              break
            case 'popup-ChiTietKM':
              console.log(data)
              break
            default:
              break
          }
        } catch (error) {
        } finally {
          this.$bvModal.hide('popupComponents')
          this.loading(false)
        }
      },
      async TaoDuLieu_DiaChi_V2(themmoi = false) {
        try {
          //this.dsDiaChi = []
          //this.dsDiaChiHDKH = []
          //this.dsDiaChiHDTT = []
          //this.dsDiaChiHDTB = []

          if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THANHTOAN) {
            let rowDiaChiTT = {}
            // if (themmoi) {
            //   let rsDC = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'DIACHI' })
            //   this.diachitt_id = rsDC.data[0].id
            // }

            rowDiaChiTT.DIACHI_ID = this.diachitt_id
            rowDiaChiTT.TINH_ID = this.tinhtt_id
            rowDiaChiTT.QUAN_ID = this.quantt_id

            if (this.phuongtt_id != 0) {
              rowDiaChiTT.PHUONG_ID = this.phuongtt_id
            }
            if (this.phott_id != 0) {
              rowDiaChiTT.PHO_ID = this.phott_id
            }
            if (this.aptt_id != 0) {
              rowDiaChiTT.AP_ID = this.aptt_id
            }
            if (this.khutt_id != 0) {
              rowDiaChiTT.KHU_ID = this.khutt_id
            }
            if (this.dacdiemtt_id != 0) {
              rowDiaChiTT.DACDIEM_ID = this.dacdiemtt_id
            }
            rowDiaChiTT.SONHA = this.sonhatt
            rowDiaChiTT.DIACHI = this.txt_diachiTT.Value
            this.js_diachi_tt = rowDiaChiTT
            //this.dsDiaChi.push(rowDiaChiTT)
            //if (themmoi) {
              //let rowDiaChiHDTT = {}
              //rowDiaChiHDTT.DIACHI_ID = this.diachitt_id
              //rowDiaChiHDTT.HDTT_ID = this.hdtt_id
              //this.dsDiaChiHDTT.push(rowDiaChiHDTT)
            //}
          } else if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_KHACHHANG) {
            let rowDiaChiKH = {}
            // if (themmoi) {
            //   await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'DIACHI' })
            //     .then(res => {
            //       if (res.error === '200' && res.data.length > 0)
            //         this.diachikh_id = res.data[0].id
            //     }).catch(e => {
            //       throw e
            //     })
            // }
            rowDiaChiKH.DIACHI_ID = this.diachikh_id
            rowDiaChiKH.TINH_ID = this.tinhkh_id
            rowDiaChiKH.QUAN_ID = this.quankh_id
            if (this.phuongkh_id !== 0)
              rowDiaChiKH.PHUONG_ID = this.phuongkh_id
            if (this.phokh_id !== 0)
              rowDiaChiKH.PHO_ID = this.phokh_id
            if (this.apkh_id !== 0)
              rowDiaChiKH.AP_ID = this.apkh_id
            if (this.khukh_id !== 0)
              rowDiaChiKH.KHU_ID = this.khukh_id
            if (this.dacdiemkh_id !== 0)
              rowDiaChiKH.DACDIEM_ID = this.dacdiemkh_id
            rowDiaChiKH.SONHA = this.sonhakh
            rowDiaChiKH.DIACHI = this.txt_diachiKH.Value
            this.js_diachi_kh = rowDiaChiKH
            //this.dsDiaChi.push({ ...rowDiaChiKH })
            //if (themmoi) {
              //let rowDiaChiHDKH = {}
              //rowDiaChiHDKH.DIACHI_ID = this.diachikh_id
              //rowDiaChiHDKH.HDKH_ID = this.hdkh_id
              //this.dsDiaChiHDKH = [rowDiaChiHDKH]
            //}
          } else if (this.cbo_kieuld_id.Value == KIEULAPDAT.THAYDOI_TT_THUEBAO) {
            let rowDiaChiTB = {}
            let rowDiachiLD = {}
            // if (themmoi) {
            //   await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'DIACHI' })
            //     .then(res => {
            //       if (res.error === '200' && res.data.length > 0)
            //         this.diachitb_id = res.data[0].id
            //     }).catch(e => {
            //       throw e
            //     })
            //   await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/getkey', { keyname: 'DIACHI' })
            //     .then(res => {
            //       if (res.error === '200' && res.data.length > 0)
            //         this.diachild_id = res.data[0].id
            //     }).catch(e => {
            //       throw e
            //     })
            // }

            rowDiaChiTB.DIACHI_ID = this.diachitb_id
            rowDiaChiTB.TINH_ID = this.tinhtb_id
            rowDiaChiTB.QUAN_ID = this.quantb_id
            if (this.phuongtb_id !== 0)
              rowDiaChiTB.PHUONG_ID = this.phuongtb_id
            if (this.photb_id !== 0)
              rowDiaChiTB.PHO_ID = this.photb_id
            if (this.aptb_id !== 0)
              rowDiaChiTB.AP_ID = this.aptb_id
            if (this.khutb_id !== 0)
              rowDiaChiTB.KHU_ID = this.khutb_id
            if (this.dacdiemtb_id !== 0)
              rowDiaChiTB.DACDIEM_ID = this.dacdiemtb_id
            rowDiaChiTB.SONHA = this.sonhatb
            rowDiaChiTB.BLOCK = this.block
            rowDiaChiTB.TANG = this.tang
            rowDiaChiTB.PHONG = this.phong
            rowDiaChiTB.MOTA = this.mota
            rowDiaChiTB.DIACHI = this.txt_diachiTB.Value
            this.js_diachi_tb = rowDiaChiTB
            //this.dsDiaChi.push({ ...rowDiaChiTB })
            //Địa chỉ lắp đặt --> dataset dsdiachi
            rowDiachiLD.DIACHI_ID = this.diachild_id
            rowDiachiLD.TINH_ID = this.tinhld_id
            rowDiachiLD.QUAN_ID = this.quanld_id
            if (this.phuongld_id !== 0)
              rowDiachiLD.PHUONG_ID = this.phuongld_id
            if (this.phold_id !== 0)
              rowDiachiLD.PHO_ID = this.phold_id
            if (this.apld_id !== 0)
              rowDiachiLD.AP_ID = apld_id
            if (this.khuld_id !== 0)
              rowDiachiLD.KHU_ID = this.khuld_id
            if (this.dacdiemld_id !== 0)
              rowDiachiLD.DACDIEM_ID = this.dacdiemld_id
            rowDiachiLD.SONHA = this.sonhald
            rowDiachiLD.BLOCK = this.block_ld
            rowDiachiLD.TANG = this.tang_ld
            rowDiachiLD.PHONG = this.sophong_ld
            rowDiachiLD.MOTA = this.motathem_ld
            rowDiachiLD.DIACHI = this.txt_diachiLD.Value
            this.js_diachi_ld = rowDiachiLD
            //this.dsDiaChi.push({ ...rowDiachiLD })
            //if (themmoi) {
              //let rowDiaChiHDTB = {}
              //rowDiaChiHDTB.DIACHI_ID = this.diachitb_id
              //rowDiaChiHDTB.DIACHILD_ID = this.diachild_id
              //rowDiaChiHDTB.HDTB_ID = this.hdtb_id
              //this.dsDiaChiHDTB = [rowDiaChiHDTB]
            //}
          }

        } catch (e) {
        }
      },
      getCurrentTime() {
        let now = new Date();

        // Get the current hour, minute, and second
        let currentHour = now.getHours();
        let currentMinute = now.getMinutes();
        let currentSecond = now.getSeconds();

        // Add leading zeros if necessary
        currentHour = (currentHour < 10 ? "0" : "") + currentHour;
        currentMinute = (currentMinute < 10 ? "0" : "") + currentMinute;
        currentSecond = (currentSecond < 10 ? "0" : "") + currentSecond;

        // Format the time as H:i:s
        return  currentHour + ":" + currentMinute + ":" + currentSecond;
      }
    }
  }
</script>
<style>
  .popup-box {
    background: #fafafa38;
  }
  .none-padding {
    padding: 0 !important;
  }
  .disable-btn:hover {
    background-color: white !important;
  }
  .disable-btn a {
    color: #6c757d !important;
  }
  .disable-btn a:hover {
    cursor: default;
    color: #6c757d;
  }
  .popup-expand-custom .grid-panel {
    right: 0;
  }
  .thaydoittkm .page-content {
    position: unset !important;
  }
  .modal-ThanhToan-ThayDoiThongTin .main-wrapper {
    position: unset !important;
  }
  .modal-ThanhToan-ThayDoiThongTin .page-content {
    position: unset !important;
  }
  .modal-body .page-content {
    position: inherit !important;
    height: calc(100vh - 150px)
  }
  .modal-thaydoi-ttkh {
    width: 90% !important;
    max-width: 90% !important;
  }
  .popup-body.popup-thaydoi-ttkh {
    padding: 0px;
  }
  .popup-body.popup-thaydoi-ttkh .breadcrumb-top {
    display: none;
  }
  .popup-body.popup-thaydoi-ttkh .page-content {
    padding: 20px;
    height: auto;
  }
  a.disabled {
    color: lightgray !important;
  }
  a.disabled:hover {
    color: lightgray !important;
  }
  .disabled {
    pointer-events: none;
    color: lightgray;
    border-color: lightgray;
  }
  input.disabled, select.disabled, .disabled input, .disabled select, .disabled .btn, .disabled /deep/ .select2-selection {
    background-color: #EEE;
    color: lightgray;
    border-color: lightgray;
  }
</style>
