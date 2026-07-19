<template src="./SearchIncidentUpdateAppointment.html"></template>
<style scoped src="./SearchIncidentUpdateAppointment.scss"></style>
<script>
import Vue from 'vue'
import DetailTemplate from '@/modules/incident/subscriber/SearchIncidentUpdateAppointment/DetailTemplate.vue'
import DataGridC from '@/modules/incident/subscriber/SearchIncidentUpdateAppointment/components/DataGrid/index.vue'
import thongTinEmail from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'
import thongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import frmLichSuHenBH from './components/frmLichSu_HenBH.vue'
import frmThayDoiTTBH from './components/frmThayDoiTTBH.vue'
import frmTraCuuVatTuHDTB from './components/frmTraCuuVatTuHDTB.vue'
import frmPhanAnhBH from './components/frmPhanAnhBH.vue'
import frmTraCuuNhacViec from './components/frmTracuu_NhacViec.vue'
import frmNhacViecBH from './components/frmNhacViecBH.vue'
// import frmNhacViecBH2 from '@/modules/incident/subscriber/IncidentProgressManagementStation/components/PopupGuiSMSTienTrinhBaoHong/PopupGuiSMSTienTrinhBaoHong.vue'
import frmTraCuuPhieuJira from './components/frmTraCuuPhieuJira.vue'
import frmHenKH from './components/frmHenKH.vue'
import frmHenKHV2 from './components/frmHenKH_V2.vue'
import frmKhoaMoTP from './components/frmKhoaMo_TP.vue'
import frmTraCuuTBBaoHong from './components/frmTraCuuTBBaoHong.vue'
import frmTraCuuTB from '@/modules/incident/subscriber/ReceiveIncident/popups/frmTraCuuThueBao/index.vue'
import frmTraCuuPhieuKN from '@/modules/complaint/SearchComplainDetail/index.vue'
import frmNhanVienTH from '@/modules/complaint/SearchComplainDetail/popups/frmNhanVienTH.vue'
// import frmTraCuuPhieuKN from './components/frmTraCuuPhieuKN.vue'
import capNhatTienTrinh from '@/modules/incident/subscriber/SearchIncidentTicket/PopUps/CapNhatTienTrinh.vue'
import frmVatTuBaoHong from '@/modules/incident/subscriber/HandleAcceptanceIncidentReports/Popups/VatTuBaoHong/VatTuBaoHong.vue'
import XemBaoCao from '@/modules/print/XemBaoCao/XemBaoCao.vue'
import { GridPlugin } from '@syncfusion/ej2-vue-grids'
import breadcrumb from '@/components/breadcrumb'
import XLSX from 'xlsx'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import api from './Api'
import { TRANGTHAI_BH } from '@/const/enums'
import popdstientrinh
  from './components/DanhSachTienTrinh'

// import frmDanhSachTienTrinh from './components/frmTienTrinh.vue';
const frmDanhSachTienTrinh = () => import('./components/frmTienTrinh.vue');
Vue.use(GridPlugin)
export default {
  components: {
    popdstientrinh,
    breadcrumb,
    XLSX,
    moment,
    api,
    DatePicker,
    DataGridC,
    thongTinEmail,
    thongTinLienHe,
    frmLichSuHenBH,
    capNhatTienTrinh,
    frmThayDoiTTBH,
    frmTraCuuVatTuHDTB,
    frmVatTuBaoHong,
    frmPhanAnhBH,
    frmTraCuuNhacViec,
    frmNhacViecBH,
    // frmNhacViecBH2,
    frmTraCuuPhieuKN,
    frmTraCuuPhieuJira,
    frmHenKH,
    frmHenKHV2,
    frmKhoaMoTP,
    XemBaoCao,
    frmTraCuuTBBaoHong,
    frmTraCuuTB,
    frmNhanVienTH,
    frmDanhSachTienTrinh,
  },
  name: 'SearchIncidentUpdateAppointment',
  props: ['p_ma_tb'],
  async created() {
    try {
      if (this.$route.name.toLowerCase() == 'SearchIncidentUpdateAppointment'.toLowerCase()) {
        //Tra cứu báo hỏng - cập nhật giờ hẹn
        this.Tag = '1'
      } else if (this.$route.name.toLowerCase() == 'SearchIncident'.toLowerCase()) {
        this.Tag = ''
      } else {
        this.Tag = ''
      }
      if (this.$route.query.tag != null && this.$route.query.tag != '' && this.$route.query.tag.length > 0) {
        this.Tag = this.$route.query.tag
      }
    } catch (err) {
      console.log('created ~ err', err)
    }
  },
  async mounted() {
    this.Loading = true
    try {
      try {
        if (this.p_ma_tb != null && this.p_ma_tb != '') {
          this.txtMaTB = this.p_ma_tb
        }
        // alert(this.$route.query.ma_tb)
        if (this.$route.query.ma_tb != null && this.$route.query.ma_tb && this.$route.query.ma_tb.length > 0) {
          this.txtMaTB = this.$route.query.ma_tb
        }
      } catch (err) {}
      let ds = await this.lay_ds_thamso_macdinh_dv()
      let donviId = this.$root.token.getDonViID()
      if (this.Tag != null) {
        if (this.Tag == 'SVTBH') {
          this.tsbtnVatTu_Text = 'Sửa vật tư'
          this.tsbtnHenKH = false
          this.tsbKHGoi = false
          this.tsbtnThongTinLH = false
          this.svt = 1
        } else {
          if (this.Tag == '1') {
            this.tsbtnHenKH = true
            let str_vtemp = 'TTKD_CAPNHAT_HEN'
            let det = ds.filter((x) => x.ma_ts == str_vtemp && x.donvi_id == donviId)
            // ds.Tables[0].AsEnumerable()
            //                         .Where(x => x["ma_ts"].ToString() == str_vtemp && x["donvi_id"].ToString() == tt_nd.donvi_id.ToString()).ToList();
            if (det.length > 0) {
              let row = det[0]
              if (row['ten_ts'] == '1') this.Quyen_Duoc_hen = 1
            }

            this.labelFunctionTitle = 'TTKD TRA CỨU CHI TIẾT PHIẾU BÁO HỎNG - CẬP NHẬT HẸN'
          } else if (this.Tag == '2') {
            this.disable_lienhe = true
            this.tsbtnHenKH = true
            let str_vtemp = 'TTKD_CAPNHAT_HEN'
            let det = ds.filter((x) => x.ma_ts == str_vtemp && x.donvi_id == donviId)
            // List<DataRow> det = ds.Tables[0].AsEnumerable()
            //                         .Where(x => x["ma_ts"].ToString() == str_vtemp && x["donvi_id"].ToString() == tt_nd.donvi_id.ToString()).ToList();
            if (det.length > 0) {
              let row = det[0]
              if (row['ten_ts'] == '1') this.Quyen_Duoc_hen = 2
            }

            this.labelFunctionTitle = 'TTVT TRA CỨU CHI TIẾT PHIẾU BÁO HỎNG - CẬP NHẬT HẸN'
          } else {
            this.tsbtnHenKH = false
            this.tsbtnThongTinLH = false
          }

          this.tsbtnVatTu_Text = 'Vật tư'
          this.svt = 0
        }
      }

      let ds_kt1 = await this.lay_ds_thamso_macdinh(0) // new ThamSo_MDFacade().LAY_DS_THAMSO_MD(0);
      if (ds_kt1 != null && ds_kt1.length > 0) {
        let str_vtemp = 'XACMINH_HEN_BAOHONG' //Hẹn báo hỏng có xác minh
        let det = ds_kt1.filter((x) => x.ma_ts == str_vtemp)
        // ds_kt1.Tables[0].AsEnumerable()
        //                         .Where(x => x["ma_ts"].ToString() == str_vtemp).ToList();

        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'] == '1') this.XACMINH_HEN_BAOHONG = 1
        }
        str_vtemp = 'KT_THOIGIAN_HEN_BAOHONG' //kt thời gian hẹn
        det = ds_kt1.filter((x) => x.ma_ts == str_vtemp)
        // det = ds_kt1.Tables[0].AsEnumerable()
        //                         .Where(x => x["ma_ts"].ToString() == str_vtemp).ToList();

        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'] == '1') this.kt_thoigian_hen_baohong = true
        }
      }

      let ktstr_vtemp = 'KHOAMO_TRAPHIEU_BH' //Cho phép khóa mở không cho phép trả phiếu báo hỏng
      let detkt = ds.filter((x) => x.ma_ts == ktstr_vtemp && x.donvi_id == donviId)
      //  ds.Tables[0].AsEnumerable()
      //                         .Where(x => x["ma_ts"].ToString() == ktstr_vtemp && x["donvi_id"].ToString() == tt_nd.donvi_id.ToString()).ToList();
      if (detkt.length > 0) {
        let row = detkt[0]
        if (row['ten_ts'] == '1') {
          this.KHOAMO_TRAPHIEU_BH = 1
          this.tsbtnKhoaMoTP = true
        } else this.tsbtnKhoaMoTP = false
      } else {
        this.tsbtnKhoaMoTP = false
      }

      if (this.txtMaTB != '') await this.txtMaTB_KeyPress()
      this.Loading = false
    } catch (ex) {
      console.log('mounted:err', ex)
      // this.Loading = false
    } finally {
      this.Loading = false
    }
    this.labelFunctionTitleMenu = this.Capitalize(this.labelFunctionTitle)
    this.$refs.reftxtMaTB.focus()
  },
  watch: {
    p_ma_tb: function (val) {
      this.txtMaTB = val
    },
    baohong_id: async function (val) {
      try {
        this.Loading = true
        let index = this.FindIndexBaoHong(val)
        let item = this.gridDanhSach.data.filter((x) => x.baohong_id == val)[0]
        await this.gridviewDanhSach_FocusedRowChanged(index, item)
      } catch (err) {
      } finally {
        this.Loading = false
      }
    },
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  data() {
    return {
      header: {
        title: 'Tra cứu',
        list: [
          { name: ' TRA CỨU KHÁCH HÀNG QUAN TÂM OA', link: { name: 'Ui.cards' } },
          {
            name: 'TRA CỨU KHÁCH HÀNG QUAN TÂM OA',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      Tag: '',
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
      typeFormat: 'date',
      selIndex: [],
      targetTop: '#app .main-wrapper',
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      txtMaTB: '', // minhhp1994
      hdkh_id: 0,
      txtMaBH: '',
      dtpNgayBH: '',
      txtDichVuVT: '',
      txtLoaiTB: '',
      txtNguoiGiao: '',
      txtNoiNhan: '',
      txtGhiChu: '',
      tinh: {},
      tracuutinh: false,
      p_baohong_id: -1,
      XACMINH_HEN_BAOHONG: -1,
      KHOAMO_TRAPHIEU_BH: 0,
      kt_thoigian_hen_baohong: false,
      userComponentModule2: null,
      popupComponentData2: null,
      popupComponentAttr2: {},
      baohong_id: 0,
      thuebao_id: 0,
      tsbtnTimKiem: true,
      tsbtnVatTu: true,
      tsbtnVatTu_Text: 'Vật tư',
      tsbtnHenKH: false,
      tsbtnThongTinLH: true,
      tsbKHGoi: true,
      tsbtnLSNhacViec: true,
      tsbtnNhacViec: true,
      tsbtnKhoaMoTP: true,
      tsbtnTraCuuPHKH: true,
      tsbtnLichSuHen: true,
      tsbtnTCTienTrinhBH: true,
      tsbtnCTJira: true,
      tsbtnInPhieuTC: true,
      svt: 0,
      lblThongBao: "",
      phieu_id: 0,
      Quyen_Duoc_hen: 0,
      labelFunctionTitle: 'TRA CỨU CHI TIẾT PHIẾU BÁO HỎNG',
      labelFunctionTitleMenu: 'TRA CỨU CHI TIẾT PHIẾU BÁO HỎNG',
      modelInput: {},
      frmPhanAnhBHInput: {},
      frmNhacViecBHInput: {},
      frmTraCuuPhieuJiraInput: {},
      showdialogfrmNhacViecBH: false,
      frmHenKHInput: {},
      frmHenKHV2Input: {},
      frmKhoaMoTPInput: {},
      frmTraCuuTBBaoHongInput: {},
      dataFrmTienTRinh: {},
      dsGiaTri: [],
      index: 0,
      disable_lienhe: false,
      dsThueBao: [],
      gridDanhSach: {
        headers: [
          {
            fieldName: 'ma_bh',
            headerText: 'Mã BH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_lt',
            headerText: 'Số ảo',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_vnpt',
            headerText: 'Mã VNPT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'hensuatu',
            headerText: 'Giờ hẹn từ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'hensuaden',
            headerText: 'Giờ hẹn đến',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'diachi_ld',
            headerText: 'Địa chỉ lắp đặt',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_bh',
            headerText: 'Ngày báo hỏng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'thoigian_ck',
            headerText: 'Thời gian CK',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nguoi_bh',
            headerText: 'Người báo hỏng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'dienthoai_lh',
            headerText: 'Điện thoại LH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dvvt',
            headerText: 'Dịch vụ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'loaihinh_tb',
            headerText: 'Loại hình',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_goi',
            headerText: 'Gói KT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_plkh',
            headerText: 'Phân loại KH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'trangthai_bh',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'kenh_tn',
            headerText: 'Kênh TN',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_xl',
            headerText: 'Ngày XL',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_ht',
            headerText: 'Ngày HT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'luot_bh',
            headerText: 'Lần BH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'lan_pa',
            headerText: 'KH gọi',
            allowFiltering: true,
            allowSorting: false
          },

          {
            fieldName: 'tinhtrang',
            headerText: 'Tình trạng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nguyennhan',
            headerText: 'Nguyên nhân',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu_hong',
            headerText: 'Ghi chú hỏng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu_xl',
            headerText: 'Ghi chú XL',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nguoi_bh',
            headerText: 'Người BH',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selectedItem: {},
        selectedItems: []
      },
      gridChitiet: {
        headers: [
          {
            fieldName: 'huonggiao',
            headerText: 'Hướng giao',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'left',
            isGroupedColumn: true
          },
          {
            fieldName: 'dvg',
            headerText: 'Đơn vị giao',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'left'
          },
          {
            fieldName: 'nv_giao',
            headerText: 'Nhân viên giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_giao',
            headerText: 'Ngày giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nd_giao',
            headerText: 'Nội dung giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'dvn',
            headerText: 'Đơn vị nhận',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_tra',
            headerText: 'Ngày trả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nd_tra',
            headerText: 'Nội dung trả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'trangthai_ph',
            headerText: 'Trạng thái phiếu',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nv_th',
            headerText: 'Nhân viên TH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_th',
            headerText: 'Ngày TH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu_th',
            headerText: 'Nội dung TH',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selectedItem: {},
        selectedItems: []
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed
      },
      popupComponentName: '',
      show_ppdstientrinh: false,
      show_popdstientrinhBH: false,
    }
  },
  methods: {
    Capitalize(input) {
      var capitalFirst = input.charAt(0).toUpperCase()
      var noCaseTail = input.slice(1, input.length)
      return capitalFirst + noCaseTail.toLowerCase()
    },
    commandClicked: async function (cmdName, data) {
      console.log('commandClicked', 'cmdName:', cmdName, 'data:', data)
      try {
      } catch (err) {
        console.log('commandClicked', err)
      } finally {
      }
    },
    async txtMaTB_KeyPress() {
      try {
        this.Loading = true
        this.baohong_id = 0
        await this.HienThi_DS_BaoHong(0)
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async txtMaBH_KeyPress() {
      try {
        this.Loading = true
        // this.baohong_id = 0
        await this.HienThi_DS_BaoHong(0)
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    tsbtnShowTienTrinh_Click() {
      // if (this.gridDanhSach.data == null || this.gridDanhSach.data.length == 0) return

      let vbaohong_id = this.baohong_id;
      if (vbaohong_id != 0) {
        this.dataFrmTienTRinh.vbaohong_id_t = vbaohong_id;
        this.dataFrmTienTRinh.thuebao_id = this.thuebao_id;
      }
      console.log(this.dataFrmTienTRinh)
      this.show_ppdstientrinh = true;
      this.$refs.dlg_DanhSachTienTrinh.show();
    },

    close_DSTienTrinh() {
      this.show_ppdstientrinh = false;
    },

    async tsbtnTimKiem_Click() {
      try {
        if (this.txtMaTB == '') return
        this.baohong_id = 0
        this.Loading = true
        await this.HienThi_DS_BaoHong(0)
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnVatTu_Click() {
      try {
        if (this.baohong_id != 0) {
          if (this.svt == 0) {
            this.$refs.reffrmTraCuuVatTuHDTB.openDiaLog(this.baohong_id, 0)
          } else {
            let count = await this.map_count_phieu(this.baohong_id)
            if (count > 0) {
              this.$refs.reffrmVatTuBaoHong.show()
              await this.$refs.ppVatTuBaoHong.frmVatTuThueBao_Load()
            } else {
              this.ShowError('Bảo hỏng chưa hoàn thiện để sửa vật tư')
            }

            // DataSet _ds = bangts.getDataFromSQL("select 1 from " + DatabaseConstants.DB7 + ".baohong where ttbh_id = 6 and baohong_id = " + baohong_id.ToString(), "");
            // if (_ds.Tables.Count > 0 && _ds.Tables[0].Rows.Count > 0)
            // {
            //     frmVatTuBaoHong _f = new frmVatTuBaoHong();
            //     _f.baohong_id = baohong_id;
            //     _f.phieu_id = _phieu_id;
            //     _f.ShowDialog();
            // }
            // else
            // {
            //     Message_Box.ShowTB("Bảo hỏng chưa hoàn thiện để sửa vật tư");
            // }
          }
        }
      } catch (ex) {
        this.ShowError('Có lỗi : ' + ex.message)
      }
    },
    async tsbtnThongTinLH_Click() {
      try {
        if (this.baohong_id == 0 || this.gridDanhSach.data.length <= 0) {
          this.ShowError('Không có dữ liệu để thực hiện !')
          return
        }
        if (this.gridDanhSach.selectedItem['ttbh_id'] == TRANGTHAI_BH.HOANTHANH) {
          //HOANTHANH = 6;
          this.ShowError('Phiếu đã hoàn thành, không được phép thay đổi !')
          return
        }
        let username = this.$root.token.getUserName()
        let nhanvienId = this.$root.token.getNhanVienID()
        let donviId = this.$root.token.getDonViID()
        this.modelInput.matb_t = this.txtMaTB
        this.modelInput.tentb_t = this.gridDanhSach.selectedItem['ten_tb']
        this.modelInput.ngaybh_t = this.dtpNgayBH
        this.modelInput.nguoibh_t = this.gridDanhSach.selectedItem['nguoi_bh']
        this.modelInput.dienthoailh_t = this.gridDanhSach.selectedItem['dienthoai_lh']
        this.modelInput.diachild_t = this.gridDanhSach.selectedItem['diachi_ld']
        this.modelInput.baohong_id_t = this.baohong_id
        this.modelInput.nguoi_cn_t = username
        this.modelInput.ngay_cn_t = moment().format('DD/MM/YYYY')
        this.modelInput.nhanvien_id_t = nhanvienId
        this.modelInput.may_cn_t = 'web'
        this.modelInput.donvi_id_t = donviId
        this.modelInput.ip_t = '127.0.0.1'
        this.$refs.reffrmThayDoiTTBH.openDiaLog()
      } catch (err) {
        this.ShowError('Lỗi: ' + err.message)
      } finally {
        //this.Loading = false
      }
    },
    async closefrmThayDoiTTBH(result) {
      if (result) await this.HienThi_DS_BaoHong(0)
    },
    async tsbKHGoi_Click() {
      try {
        this.Loading = true
        if (this.gridDanhSach.data == null || this.gridDanhSach.data.length == 0) return

        let vbaohong_id = this.gridDanhSach.selectedItem['baohong_id'] //gridviewDanhSach.GetRowCellValue(gridviewDanhSach.FocusedRowHandle, "BAOHONG_ID").ToString();
        if (vbaohong_id != null && vbaohong_id != '') {
          // frmPhanAnhBH f = new frmPhanAnhBH();
          // f.vbaohong_id_t = vbaohong_id;
          // f.thuebao_id = thuebao_id;
          // f.ttkd_hen = Quyen_Duoc_hen;
          // f.ShowDialog();
          this.frmPhanAnhBHInput.vbaohong_id_t = vbaohong_id
          this.frmPhanAnhBHInput.thuebao_id = this.thuebao_id
          this.frmPhanAnhBHInput.ttkd_hen = this.Quyen_Duoc_hen
          this.$refs.dialogfrmPhanAnhBH.openDialog()
        } else {
          this.ShowError('Chưa có thông tin báo hỏng!')
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnLSNhacViec_Click() {
      try {
        if (this.gridDanhSach.data == null || this.gridDanhSach.data.length == 0) return
        let vbaohong_id = this.gridDanhSach.selectedItem['baohong_id']
        if (vbaohong_id != null && vbaohong_id != '') {
          this.$refs.dialogfrmTracuu_NhacViec.openDialog(vbaohong_id)
        } else {
          this.ShowError('Chưa có thông tin báo hỏng!')
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnNhacViec_Click() {
      try {
        if (this.gridDanhSach.data == null || this.gridDanhSach.data.length == 0) return
        let count = await this.map_count_phieu(this.baohong_id)
        if (count > 0) {
          this.ShowError('Báo hỏng đã hoàn thiện, không thể nhắc việc!')
          return
        }
        let thuebao_id = await this.map_thuebao_id(this.baohong_id)
        let vbaohong_id = this.gridDanhSach.selectedItem['baohong_id']
        if (vbaohong_id != null && vbaohong_id != '') {
          this.frmNhacViecBHInput.loaidv_nhan_id = '0'
          this.frmNhacViecBHInput.baohong_id = this.baohong_id
          this.frmNhacViecBHInput.kieugoi_id = 4
          this.frmNhacViecBHInput.phieu_id = this.phieu_id
          this.frmNhacViecBHInput.thuebao_id = thuebao_id
          this.frmNhacViecBHInput.kieu_nhac = 1 // //TTVT nhắc
          this.frmNhacViecBHInput.ma_tb = this.txtMaTB
          this.$refs.dialogfrmNhacViecBH.openDialog()
        } else {
          this.ShowError('Chưa có thông tin báo hỏng!')
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnTraCuuPHKH_Click() {
      // this.$refs.dialogObjTraCuuPhieuKN.kieu_frm = 1
      // this.$refs.dialogObjTraCuuPhieuKN.frmTraCuuPhieuKN('minhhp1994')
      // this.$nextTick(() => {
      //   this.$refs.reffrmTraCuuPhieuKN.show()
      //   this.$refs.dialogObjTraCuuPhieuKN.txtMaTB_KeyUp()
      // })

      try {
        // this.$refs.dialogObjTraCuuPhieuKN = () => import('@/modules/complaint/SearchComplainDetail/index.vue')
        this.$refs.dialogObjTraCuuPhieuKN.frmTraCuuPhieuKN(this.txtMaTB)
        this.$refs.dialogObjTraCuuPhieuKN.kieu_frm = 1
        try {
          // this.$refs.dialogObjTraCuuPhieuKN.frmTraCuuPhieuKN_Load()
        } catch (err) {
          console.log('🚀 ~ file: SearchIncidentUpdateAppointment.vue ~ line 729 ~ tsbtnTraCuuPHKH_Click ~ err', err)
        }
        this.$refs.dialogObjTraCuuPhieuKN.txtMaTB_KeyUp()
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.$refs.reffrmTraCuuPhieuKN.show()
      }
    },
    async gridChitiet_DoubleClick(i, item) {
      try {
        let phieu_id = item['phieu_id'] // Convert.ToInt64(gridChitiet.GetRowCellValue(info.RowHandle, 'PHIEU_ID').ToString())
        console.log('Open frmNhanVienTH')
        // frmNhanVienTH frm = new frmNhanVienTH();
        // frm.vphieu_bh_id = phieu_id;
        // frm.ShowDialog();
        console.log('gridChitiet_DoubleClick:', phieu_id)
        this.$refs.frmNhanVienTH.vphieu_bh_id = parseInt(phieu_id)
        this.$refs.frmNhanVienTH.show()
      } catch (ex) {
        this.ShowError('Có lỗi: ' + ex.message)
      }
    },
    async gridChitiet_selectedItemsChanged(items) {
      this.gridChitiet.selectedItems = items
    },
    async gridChitiet_FocusedRowChanged(i, item) {
      this.gridChitiet.selectedItem = item
      let matinh = this.$root.token.getMaTinh()
      this.txtNguoiGiao = item['nv_giao']
      this.txtNoiNhan = item['dvn']
      if (matinh == 'CSS_HPG') {
        let huonggiao_id = item['huonggiao_id']
        let phieu_id = item['phieu_id']
        // CheckDataFacade checkdata = new CheckDataFacade();
        // string s = checkdata.MAP_ID("ttbh_id", DatabaseConstants.DB2 + ".huonggiao", " where huonggiao_id=" + huonggiao_id);
        // string ss = checkdata.MAP_ID("ttph_id", DatabaseConstants.DB7 + ".giaophieu", " where phieu_id=" + phieu_id);
        let s = await this.map_ttbh_id(huonggiao_id)
        let ss = await this.map_ttph_id(phieu_id)
        if ((s == '3' || s == '8') && ss == '1') {
          this.tsbtnHenKH_Enabled = true
        } else {
          this.tsbtnHenKH_Enabled = false
        }
      }
    },
    async gridviewDanhSach_RowClicked(i, item) {
      if (item == null || item == undefined) {
        console.log('gridviewDanhSach_FocusedRowChanged_item', i, item)
        console.log('Baohong')
        console.log(item.baohong_id);
        return
      }
      this.gridDanhSach.selectedItem = item
      this.thuebao_id = item['thuebao_id']
      this.phieu_id = item['phieu_id']
      this.txtDichVuVT = item['ten_dvvt']
      this.txtLoaiTB = item['loaihinh_tb']
      if (this.baohong_id != item['baohong_id']) {
        this.baohong_id = item['baohong_id']
      } else {
        await this.gridviewDanhSach_FocusedRowChanged(i, item)
      }
    },
    async gridviewDanhSach_FocusedRowChanged(i, item) {
      if (item == null || item == undefined) {
        console.log('gridviewDanhSach_FocusedRowChanged_item', i, item)
        console.log(item.baohong_id)
        return
      }
      try {
        console.log('gridviewDanhSach_FocusedRowChanged', i, item)
        console.log(item['baohong_id'])
        this.gridDanhSach.selectedItem = item
        this.baohong_id = item['baohong_id']
        this.thuebao_id = item['thuebao_id']
        this.phieu_id = item['phieu_id']
        this.txtDichVuVT = item['ten_dvvt']
        this.txtLoaiTB = item['loaihinh_tb']
        if (item['ngay_bh'] != null && item['ngay_bh'] != '') {
          this.dtpNgayBH = item['ngay_bh']
        }
        this.txtGhiChu = item['ghichu_hong']
        // DevExpress.XtraGrid.Columns.GridColumn colTinh = gridviewDanhSach.Columns["ten_tinh"];
        let colTinh = null
        if (Object.keys(item).filter((x) => x == 'ten_tinh').length > 0) {
          colTinh = 'ten_tinh'
        } else {
          colTinh = null
        }
        this.Loading = true
        if (colTinh == null) await this.HienThi_DS_PhieuTH(this.baohong_id)
        else {
          let tinh_id = item['tinh_id']
          // Common.BanCheoData.Tinh_TC tinh = new BusinessFacade.BanCheoFacade.BanCheoBase().tinh_thicong(Convert.ToInt32(tinh_id));
          let tinh = await this.sp_lay_tinh_tc_theo_tinh_id(tinh_id)
          await this.HienThi_DS_PhieuTH_Tinh(tinh_id, this.baohong_id)
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async HienThi_DS_PhieuTH_Tinh(tinh_id, baohong_id) {
      try {
        let myDataTables = []
        if (!this.kt_thoigian_hen_baohong) {
          // new BusinessFacade.BanCheoFacade.BanCheoBase().GET_DATA_OBJECT_PL(tinh, "baohong_" + tinh.matinh + ".BAOHONG_COMMON.LAY_DANHSACH_PHIEUTH_BH", "returnds",
          //                                                                                    "vbaohong_id", vbaohong_id);
          myDataTables = await this.lay_danhsach_phieuth_bh(baohong_id)
        } else {
          // new BusinessFacade.BanCheoFacade.BanCheoBase().GET_DATA_OBJECT_PL(tinh, "baohong_" + tinh.matinh + ".LAY_DANHSACH_PHIEUTH_BH_V2", "returnds",
          //  "vbaohong_id", vbaohong_id);
          myDataTables = await this.lay_danhsach_phieuth_bh_v2(baohong_id)
        }
        let dt_phieu_ph = await this.lay_danhsach_phieuth_bh(baohong_id) //new BusinessFacade.BanCheoFacade.BaoHong().LAY_DANHSACH_PHIEUTH_BH(vbaohong_id, tt_nd.nhanvien_id).Tables[0]
        // CommonUtilities cmUlt = new CommonUtilities();
        // for (int i = 0; i < dt_phieu_ph.Rows.Count; i++)
        // {
        //     DataRow dr = myDataTables.NewRow();
        //     cmUlt.CopyRow(dt_phieu_ph.Rows[i], dr);
        //     dr["huonggiao"] = (myDataTables.Rows.Count + i + 1) + dr["huonggiao"].ToString().Substring(dr["huonggiao"].ToString().IndexOf(" "));
        //     myDataTables.Rows.Add(dr);
        // }
        // dgvChiTiet.DataSource = myDataTables;
        // gridChitiet.BestFitColumns();
        // gridChitiet.ExpandAllGroups();
        // if (myDataTables.Rows.Count > 0)
        //     gridChitiet_FocusedRowChanged(0);
        this.gridChitiet.data = myDataTables
        if (myDataTables != null && myDataTables.length > 0) gridChitiet_FocusedRowChanged(0, myDataTables[0])
      } catch (err) {
        //new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
        this.ShowError(`${err.message}`)
      }
    }, 
    async HienThi_DS_PhieuTH(baohong_id) {
      let data = []
      data = await this.lay_ds_baohongbc_theo_baohong_id(baohong_id)
      if(data.length <= 0){
        this.labelFunctionTitle = "TRA CỨU CHI TIẾT PHIẾU BÁO HỎNG";
        this.lblThongBao = "Phiếu báo hỏng chưa đc chuyển cho tỉnh thi công.";
        this.gridChitiet.data = null;
        return;  
      }
      let ting_tc = data[0]["tinh_thicong"];
      console.log(22211111111)
      console.log(ting_tc)
      let vbaohongid_tc = data[0]["baohong_id_thicong"]; 
      this.tinh = await this.tinh_thicong(ting_tc)
      console.log(this.tinh)
      this.labelFunctionTitle = "TRA CỨU CHI TIẾT PHIẾU BÁO HỎNG " + this.tinh.matinh.toUpperCase();
      this.lblThongBao = ""; 
      let myDataTables  = await this.lay_danhsach_phieuth_bh_BC(ting_tc,vbaohongid_tc)
     
      this.gridChitiet.data = myDataTables
      if (myDataTables != null && myDataTables.length > 0) await this.gridChitiet_FocusedRowChanged(0, data[0])
    },
     tinh_thicong: async function (tinhthicong_id) {
      let input = { tinhthicong_id: tinhthicong_id }
      let data = this.GetData(await api.tinh_thicong(this.axios, input))
      if (data != null && data.length > 0) {
        let tinh_thicong = data[0]
        tinh_thicong.tinhthicong_id = tinhthicong_id
        return tinh_thicong
      }
      return { user: '', pass: '', nhanvien_id: 0, donvi_id: 0, matinh: '', TNS: '', tinhthicong_id: 0, ip_dll: '' }
    },
    async HienThi_DS_BaoHong(in_dichvuvt_id) {
      try {
        // lvwDSBaoHong.Items.Clear();
        this.gridDanhSach.data = []
        this.gridChitiet.data = []
        let vma_tb = this.txtMaTB
        let vma_bh = this.txtMaBH
        let dv = 0
        this.tracuutinh = false
        this.index = 0
        if (vma_tb != '') {
          if (in_dichvuvt_id == 0) {
            let ds = await this.lay_danhba_theo_matb_new(this.txtMaTB, 0)
            let index = 0
            if (ds != null && ds.length > 0) {
              if (ds.length == 1) index = 0
              else {
                this.dsThueBao = ds
                const rs = await this.$refs.refTraCuuTB.showModal()
                if (rs.index != -1) {
                  index = rs.index
                  this.index = rs.index
                }
                console.log('this.index', this.index)
                // this.ShowSearchAccount2(ds)
                // return

                // frmTraCuu_ThueBao frm = new frmTraCuu_ThueBao(ds);
                // frm.index = 0;
                // frm.ShowDialog();
                // if (frm.chapnhan)
                //     index = frm.index;
              }
            } else {
              // let tinh_id = new CheckDataFacade().MAP_ID('tinh_id', DatabaseConstants.DB7 + '.giaophieu_ph', " where ma_tb='" + vma_tb + "'")
              let tinh_id = await this.map_tinhid_giaophieu_ph(vma_tb, dv)
              if (tinh_id != '-1') {
                // Common.BanCheoData.Tinh_TC tinh = new BusinessFacade.BanCheoFacade.BanCheoBase().tinh_thicong(Convert.ToInt32(tinh_id));
                // DataTable dt = new BusinessFacade.BanCheoFacade.BanCheoBase().GET_DATA_OBJECT_PL(tinh, "baohong_" + tinh.matinh + ".LAY_DANHBA_THEO_MATB_NEW", "returnds",
                //                                                                                "in_ma_tb", vma_tb, "in_dichvuvt_id", 0);
                // let dt = await this.GET_DATA_OBJECT_PL()
                ds = await this.sp_lay_ds_baohong_theo_ma_tb(vma_tb, 0)
                let index = 0
                if (ds != null && ds.length > 0) {
                  index = 0
                  this.tracuutinh = true
                }
              } else {
                this.ShowError('Không tìm thấy thông tin!')
                // dgvChiTiet.DataSource = null;
                return
              }
            }
            vma_tb = ds[this.index]['ma_tb']
            dv = parseInt(ds[this.index]['dichvuvt_id'])
            this.thuebao_id = ds[this.index]['thuebao_id']
          }
        }
        if (this.tracuutinh) await this.HienThi_LichSu_BaoHong_Tinh(vma_tb, dv)
        else await this.HienThi_LichSu_BaoHong(vma_tb, this.thuebao_id, vma_bh, this.baohong_id)
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    async HienThi_LichSu_BaoHong_Tinh(vma_tb, dv) {
      console.log('HienThi_LichSu_BaoHong_Tinh')
      let data = { dtCTHONG: [], dtCTTON: [], dtNVXL: [] }
      this.txtMaTB = vma_tb
      let tinh_id = await this.map_tinhid_giaophieu_ph(vma_tb, dv)
      if (tinh_id != '') {
        let tinh_tc = await this.sp_lay_tinh_tc_theo_tinh_id(tinh_id)
        let dt = await this.sp_lay_ds_baohong_theo_ma_tb(vma_tb, 0)
        let baohong_ids = dt.map((x) => x.baohong_id).join(',')
        let dtCTHONG = await this.sp_lay_cthong_theo_ds_baohong(baohong_ids)
        data.dtCTHONG = dtCTHONG
        let dtCTTON = await this.sp_lay_ctton_theo_ds_baohong(baohong_ids)
        data.dtCTTON = dtCTTON
        let dtNVXL = await this.sp_lay_ds_nvth_theo_ds_baohong(baohong_ids)
        data.dtNVXL = dtNVXL
      }
    },
    async HienThi_LichSu_BaoHong(vma_tb, vthuebao_id, vma_bh, vbaohong_id) {
      console.log('HienThi_LichSu_BaoHong')
      this.gridDanhSach.data = []
      let data = { dtCTHONG: [], dtCTTON: [], dtNVXL: [] }
      this.txtMaTB = vma_tb
      let dtBH = await this.lay_ds_baohong_theo_thuebao_id(vthuebao_id, vma_bh, vbaohong_id)
      if (dtBH != null && dtBH.length > 0) {
        let baohong_ids = dtBH.map((x) => x.baohong_id).join(',')
        let dtCTHONG = await this.sp_lay_cthong_theo_ds_baohong(baohong_ids)
        data.dtCTHONG = dtCTHONG
        let dtCTTON = await this.sp_lay_ctton_theo_ds_baohong(baohong_ids)
        data.dtCTTON = dtCTTON
        let dtNVXL = await this.sp_lay_ds_nvth_theo_ds_baohong(baohong_ids)
        data.dtNVXL = dtNVXL
        if (dtBH != null) {
          dtBH.forEach((item, rowId) => {
            if (item['ngay_bh'] != null && item['ngay_bh'] != '') item['ngay_bh'] = moment(item['ngay_bh'], 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
            if (item['hensuatu'] != null && item['hensuatu'] != '') item['hensuatu'] = moment(item['hensuatu'], 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
            if (item['hensuaden'] != null && item['hensuaden'] != '') item['hensuaden'] = moment(item['hensuaden'], 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
            if (item['ngay_ht'] != null && item['ngay_ht'] != '') item['ngay_ht'] = moment(item['ngay_ht'], 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
            item['dtCTHONG'] = dtCTHONG.filter((x) => x.baohong_id == item.baohong_id)
            item['dtCTTON'] = dtCTTON.filter((x) => x.baohong_id == item.baohong_id)
            item['dtNVXL'] = dtNVXL.filter((x) => x.baohong_id == item.baohong_id)
            item['rowId'] = '-rowId' + rowId + '-'
          })
        }
        this.gridDanhSach.data = dtBH
        await this.gridviewDanhSach_RowClicked(0, dtBH[0])
      }
      return data
    },
    async lay_ds_thamso_macdinh_dv() {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let input = {
        vdonvi_id: donviId,
        vkieu_id: 4
      }
      let data = this.GetData(await api.lay_ds_thamso_macdinh_dv(this.axios, input))
      return data
    },
    async lay_ds_thamso_macdinh(kieu) {
      let input = {
        kieu_id: kieu
      }
      let data = this.GetData(await api.lay_ds_thamso_macdinh(this.axios, input))
      return data
    },
    async sp_lay_ds_thamso_md() {
      let input = {}
      let data = this.GetData(await api.sp_lay_ds_thamso_md(this.axios, input))
      return data
    },
    async lay_danhba_theo_matb_new(ma_tb, dichvuvt_id) {
      let input = { ma_tb: ma_tb, dichvuvt_id: dichvuvt_id }
      let data = this.GetData(await api.lay_danhba_theo_matb_new(this.axios, input))
      return data
    },
    async sp_lay_ds_baohong_theo_ma_tb(ma_tb, dichvuvt_id) {
      let input = { ma_tb: ma_tb, dichvuvt_id: dichvuvt_id }
      let data = this.GetData(await api.sp_lay_ds_baohong_theo_ma_tb(this.axios, input))
      return data
    },
    async sp_lay_tinh_tc_theo_tinh_id(tinh_id) {
      let input = { tinh_id: tinh_id }
      let data = this.GetData(await api.sp_lay_tinh_tc_theo_tinh_id(this.axios, input))
      return data
    },
    async sp_lay_cthong_theo_ds_baohong(ds_baohong_id) {
      let input = { ds_baohong_id: ds_baohong_id }
      let data = this.GetData(await api.sp_lay_cthong_theo_ds_baohong(this.axios, input))
      return data
    },
    async sp_lay_ctton_theo_ds_baohong(ds_baohong_id) {
      let input = { ds_baohong_id: ds_baohong_id }
      let data = this.GetData(await api.sp_lay_ctton_theo_ds_baohong(this.axios, input))
      return data
    },
    async sp_lay_ds_nvth_theo_ds_baohong(ds_baohong_id) {
      let input = { ds_baohong_id: ds_baohong_id }
      let data = this.GetData(await api.sp_lay_ds_nvth_theo_ds_baohong(this.axios, input))
      return data
    },
    async lay_danhsach_phieuth_bh(baohong_id) {
      let input = { baohong_id: baohong_id }
      let data = this.GetData(await api.lay_danhsach_phieuth_bh(this.axios, input))
      return data
    },
    async lay_danhsach_phieuth_bh_BC(tinh_thicong,baohong_id) {
      let input = { tinhthicong_id:tinh_thicong,baohong_id: baohong_id }
      let data = this.GetData(await api.lay_danhsach_phieuth_bh_BC(this.axios, input))
      return data
    },
    async lay_ds_baohongbc_theo_baohong_id(baohong_id) {
      let input = {
         baohong_id: baohong_id,
         ma_bh: this.txtMaBH
      }
      let data = this.GetData(await api.lay_ds_baohongbc_theo_baohong_id(this.axios, input))
      return data
    },
    async lay_danhsach_phieuth_bh_v2(baohong_id) {
      let input = { baohong_id: baohong_id }
      let data = this.GetData(await api.lay_danhsach_phieuth_bh_v2(this.axios, input))
      return data
    },
    async lay_ds_baohong_theo_thuebao_id(vthuebao_id, vma_bh, vbaohong_id) {
      let input = {
        p_thuebao_id: vthuebao_id,
        p_ma_bh: vma_bh,
        p_baohong_id: vbaohong_id
      }
      let data = this.GetData(await api.lay_ds_baohong_theo_thuebao_id(this.axios, input))
      return data
    },
    async map_count_phieu(baohong_id) {
      // let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'count(1)', in_table: 'baohong.baohong', in_dk: `where  ttbh_id = 6 and baohong_id=${baohong_id} and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_count_bh(this.axios, { param: baohong_id, type: 2 }))
      return data
    },
    async map_tinhid_giaophieu_ph(vma_tb, dv) {
      let phanvung_id = this.$root.token.getPhanVungID()
      //let input = { id_neo: 'tinh_id', in_table: 'baohong.giaophieu_ph', in_dk: `where ma_tb=${vma_tb} and dichvuvt_id=${dv}` }
      // let input = { id_neo: 'tinh_id', in_table: 'baohong.giaophieu_ph', in_dk: `where ma_tb='${vma_tb}'  ` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_giaophieubh(this.axios, { param: vma_tb, type: 1 }))
      return data
    },
    async map_ttbh_id(huonggiao_id) {
      // let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'ttbh_id', in_table: 'css.huonggiao', in_dk: `where huonggiao_id= ${huonggiao_id} and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_huonggiao(this.axios, { param: huonggiao_id, type: 3 }))
      return data
    },
    async map_ttph_id(phieu_id) {
      // let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'ttph_id', in_table: 'baohong.giaophieu', in_dk: `where phieu_id= ${phieu_id} and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_giaophieubh(this.axios, { param: phieu_id, type: 4 }))
      return data
    },
    async map_thuebao_id(baohong_id) {
      // let phanvung_id = this.$root.token.getPhanVungID()
      // let input = { id_neo: 'thuebao_id', in_table: 'baohong.baohong', in_dk: `where baohong_id= ${baohong_id} and phanvung_id=${phanvung_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_giaophieubh(this.axios, { param: baohong_id, type: 5 }))
      return data
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup('popupComponentBHs')
        return
      }
      this.loading(true)
      try {
        switch (this.popupComponentName) {
          case 'SearchAccount':
            if (val.ma_kh) this.txtMaKH = val.ma_kh
            if (val.ma_tt) this.Kh.txtMaTT = val.ma_tt
            break
        }
      } catch (err) {
      } finally {
        this.ClosePopup('popupComponentBHs')
        this.loading(false)
      }
    },
    getDetailTemplate: function () {
      return {
        template: DetailTemplate
      }
    },
    ShowSearchAccount() {
      this.$refs.refTraCuuTBBaoHong.openDialog()
    },
    async ShowSearchAccount2(data) {
      // this.$refs.refTraCuuTBBaoHong.openDialog2(data)
      const rs = await this.$refs.refTraCuuTB.showModal()
      if (rs.index != -1) {
        this.index = rs.index
      }
    },
    async closeTraCuu(f) {
      try {
        if (f.chapnhan) {
          this.Loading = true
          this.txtMaTB = f.ma_tb
          this.baohong_id = f.baohong_id
          this.txtDichVuVT = ''
          this.txtLoaiTB = ''
          this.txtGhiChu = ''
          this.txtNoiNhan = ''
          await this.HienThi_DS_BaoHong(f.dichvuvt_id)
        }
        this.$refs.reftxtMaTB.focus()
      } catch (err) {
        this.Loading = false
      } finally {
        this.Loading = false
      }
    },
    async closeTraCuuTB(f) {
      try {
        console.log('closeTraCuuTB', f)
        if (f.chapnhan) {
          this.Loading = true
          this.txtMaTB = f.ma_tb
          this.index = f.index
          this.txtDichVuVT = ''
          this.txtLoaiTB = ''
          this.txtGhiChu = ''
          this.txtNoiNhan = ''
          await this.HienThi_DS_BaoHong(f.dichvuvt_id)
        }
        this.$refs.reftxtMaTB.focus()
      } catch (err) {
        this.Loading = false
      } finally {
        this.Loading = false
      }
    },
    ShowSearchAccount2() {
      this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
      this.popupComponentName = 'SearchAccount'
      try {
        this.popupComponent.data.isPopup = true
        this.popupComponent.data.vdichvuvt_id = 0
      } catch (ex) {
        if (this.debug) console.error(ex)
      }
      this.Popup('popupComponentBHs')
    },
    gridDanhSach_dataBound(args) {
      this.$refs.gridDanhSach.grid.autoFitColumns([])
      if (this.selIndex.length > 0) {
        this.$refs.gridDanhSach.grid.selectRows(this.selIndex)
        this.selIndex = []
      }
    },
    disabledRange: function (date) {
      return date > new Date()
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    gridDanhSach_detailDataBound(args) {
      if (args.data != null) {
        console.log('gridDanhSach_detailDataBound', args)
        this.baohong_id = args.data.baohong_id
        this.gridDanhSach.selectedItem = args.data
        let index = this.FindIndexBaoHong(this.baohong_id)
        this.selIndex = []
        if (index >= 0) this.selIndex.push(index)
        this.$refs.gridDanhSach.grid.selectRows(this.selIndex)
        // Promise.all([this.gridviewDanhSach_FocusedRowChanged(0, args.data)])
      }
    },
    gridDanhSach_RowDataBound: async function (args) {
      if (args.data['baohong_id'] == this.baohong_id) {
        this.selIndex.push(parseInt(args.row.getAttribute('aria-rowindex')))
        // await this.select_selectThueBao(this.selIndex, args.data)
      }
      // args.row.classList.add('below-30')
    },
    FindIndexBaoHong(baohong_id) {
      let i = -1
      this.gridDanhSach.data.forEach(function (item, index) {
        console.log('FindIndexBaoHong', item, index)
        if (item.baohong_id == baohong_id) {
          // alert(index)
          return index
        }
      })
      return -1
    },
    async tsbtnHenKH_Click() {
      try {
        if (this.gridChitiet.selectedItems.length <= 0) {
          this.ShowError('Bạn chưa chọn bản ghi cần cập nhật giờ hẹn!')
          return
        }
        let khoaphieu = 0
        let phieu_id = this.gridChitiet.selectedItem.phieu_id // Convert.ToInt64(gridChitiet.GetRowCellValue(gridChitiet.FocusedRowHandle, "PHIEU_ID"));
        if (this.kt_thoigian_hen_baohong) khoaphieu = this.gridChitiet.selectedItem.khoaphieu // Convert.ToInt32(gridChitiet.GetRowCellValue(gridChitiet.FocusedRowHandle, "KHOAPHIEU"));
        if (this.XACMINH_HEN_BAOHONG == -1) {
          // frmHenKH f = new frmHenKH();
          // f.baohong_id = baohong_id;
          // f.phieu_id = phieu_id;
          // f.Quyen_Duoc_hen = Quyen_Duoc_hen;
          // f.khoaphieu = khoaphieu;
          // f.kt_thoigian_hen_baohong = kt_thoigian_hen_baohong;
          this.frmHenKHInput.baohong_id = this.baohong_id
          this.frmHenKHInput.phieu_id = phieu_id
          this.frmHenKHInput.Quyen_Duoc_hen = this.Quyen_Duoc_hen
          this.frmHenKHInput.khoaphieu = khoaphieu
          this.frmHenKHInput.kt_thoigian_hen_baohong = this.kt_thoigian_hen_baohong
          this.$refs.reffrmHenKH.openDialog()
        } else {
          //  frmHenKH_v2 f = new frmHenKH_v2();
          //  f.baohong_id = baohong_id;
          //  f.phieu_id = phieu_id;
          //  f.Quyen_Duoc_hen = Quyen_Duoc_hen;
          this.frmHenKHV2Input.baohong_id = this.baohong_id
          this.frmHenKHV2Input.phieu_id = phieu_id
          this.frmHenKHV2Input.Quyen_Duoc_hen = this.Quyen_Duoc_hen
          this.$refs.reffrmHenKHV2.openDialog()
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    async tsbtnHenKH_CloseEvent() {
      if (this.txtMaTB == '') return
      await this.HienThi_DS_BaoHong(0)
    },
    async tsbtnLichSuHen_Click() {
      try {
        if (this.baohong_id == -1 || this.baohong_id == 0) return
        this.$refs.reffrmLichSuHenBH.openDiaLog(this.baohong_id, 2)
      } catch (err) {
      } finally {
      }
    },
    async closeHenKH(thuchien) {
      if (thuchien) {
        if (this.txtMaTB == '') return
        await this.HienThi_DS_BaoHong(0)
      }
    },
    async tsbtnCTJira_Click() {
      try {
        if (this.gridChitiet.selectedItems.length <= 0) {
          this.ShowError('Bạn chưa chọn bản ghi ')
          return
        }
        let phieu_id = this.gridChitiet.selectedItem.phieu_id
        this.$refs.reffrmTraCuuPhieuJira.openDialog(phieu_id)
        // frmTraCuuPhieuJira frm = new frmTraCuuPhieuJira();
        //     frm.vphieu_id = _phieu_id;
        //     frm.ShowDialog();
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    async tsbtnKhoaMoTP_Click() {
      try {
        if (this.gridChitiet.selectedItems.length > 0) {
          let item = this.gridChitiet.selectedItem
          if (item['phieu_id'] == null || item['phieu_id'] == '') {
            this.ShowError('Bạn chưa chọn chi tiết phiếu xử lý sự cố')
            return
          }
          let phieu_id = item['phieu_id']
          let huonggiao_id = item['huonggiao_id']
          let vttbh_id = await this.map_ttbh_id(huonggiao_id)
          if (vttbh_id != TRANGTHAI_BH.DANG_DIEUHANH_XU_LY && vttbh_id != TRANGTHAI_BH.CHO_XULY) {
            this.ShowError('Hãy chọn phiếu tại Trạm để Khóa/Mở trả phiếu!')
            return
          }

          // let vttbh_id = new CheckDataFacade().MAP_ID("ttbh_id", DatabaseConstants.DB2 + ".huonggiao",
          //     " where huonggiao_id = " + gridChitiet.GetFocusedRowCellValue("HUONGGIAO_ID").ToString());
          // let vttbh_id = this.map_ttbh_id(item['huonggiao_id'])
          // if (vttbh_id != TRANGTHAI_BH.DANG_DIEUHANH_XU_LY && vttbh_id != TRANGTHAI_BH.CHO_XULY) {
          //   this.ShowError('Hãy chọn phiếu tại Trạm để Khóa/Mở trả phiếu!')
          //   return
          // }

          // var frm = new frmKhoaMo_TP()
          // frm.vphieu_id = phieu_id
          // frm.vbaohong_id = baohong_id
          // frm.ma_tb = txtMaTB.Text
          // frm.vhuonggiao = gridChitiet.GetFocusedRowCellValue('HUONGGIAO')
          // frm.ShowDialog()
          this.frmKhoaMoTPInput.vphieu_id = phieu_id
          this.frmKhoaMoTPInput.vbaohong_id = this.baohong_id
          this.frmKhoaMoTPInput.ma_tb = this.txtMaTB
          this.frmKhoaMoTPInput.vhuonggiao = item['huonggiao']
          this.$refs.reffrmKhoaMoTP.openDialog()
        } else {
          this.ShowError('Hãy chọn một phiếu xử lý sự cố bạn muốn khoá.')
        }
      } catch (err) {
        this.ShowError('Có lỗi: ' + err.message)
      }
    },
    async tsbtnInPhieuTC_Click() {
      try {
        let ma_tinh = this.$root.token.getMaTinh()
        if (this.gridDanhSach.data.length <= 0) {
          this.ShowError('Chưa có danh sach thuê bao. Bạn hãy kiểm tra lại! ')
          return
        }
        if (ma_tinh == 'CSS_HCM') {
          // var frmBC = new frmXemBaoCao("PHIEU_BH", new[]{
          //     baohong_id
          // });
          // frmBC.XemNgay();
          this.dsGiaTri.push(this.baohong_id)
          this.$refs.XemBaoCao.XemNgay()
        } else {
          let vphieu_id = ''
          this.$bvModal
            .msgBoxConfirm('Bạn có muốn in các phiếu thi công đã chọn không?', {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            })
            .then(async (v) => {
              if (!v) {
                return
              }
              // Xử lý code ở đây
              if (this.gridDanhSach.data.length <= 0) {
                this.ShowError('Chưa có danh sach thuê bao. Bạn hãy kiểm tra lại! ')
                return
              }
              vphieu_id = this.gridDanhSach.selectedItems.map((x) => x.phieu_id).join()
              let donviId = this.$root.token.getDonViID()
              this.dsGiaTri.push(vphieu_id)
              this.dsGiaTri.push(donviId)
              this.$refs.XemBaoCao.XemNgay()
            })
        }
      } catch (err) {
        this.ShowError('Có lỗi : ' + err.message)
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
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    },
    async show_DSTienTrinh() {
      this.show_popdstientrinhBH = true;
      this.$refs.dlg_DanhSachTienTrinhBH.show();
    },

    async close_DSTienTrinh() {
      this.show_popdstientrinhBH = false;
    },
  }
}
</script>
