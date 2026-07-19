<template src="./SearchInstallDetail.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import API from './API'
import pop_chitiethopdong from './popup/ChiTietHopDongPhuLuc.vue'
import pop_diachild from './popup/DiaChiLD.vue'
//import pop_toanha from './popup/ThongTinToaNha.vue'
import pop_goidv from './popup/GoiDichVu.vue'
import pop_phieuton from './popup/TonPhieu.vue'
import pop_dsbd from './popup/DSBD.vue'
import popdstientrinh from './popup/DanhSachTienTrinh/DanhSachTienTrinh.vue'
import Thuebao_ToaNha from './popup/ThueBao_ToaNha.vue'
import TraCuuNhanVienTH from './popup/TraCuuNhanVienTH.vue'
import TraCuuPortHDTB from './popup/TraCuuPortHDTB.vue'
import ThongTinEmail from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'
import ThongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import VatTuThueBao from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/VatTuThueBao'
import VatTuHDTB from './popup/TraCuuVatTuHDTB'
import ThongTinHenKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import IT360Information from './popup/IT360Information.vue'
import DsdkDvGiaTang from './popup/dsdk-dv-gia-tang'
import SearchContractModal from '@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue'
import InBienBan from '@/modules/print/InBienBan/InBienBan.vue'
import { ViewChiTietHDPL } from './components'
import gridview from '@/components/Shared/gridview'
import Vue from 'vue'
import { ContextMenuPlugin } from '@syncfusion/ej2-vue-navigations'
import { enableRipple } from '@syncfusion/ej2-base'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import { previewPrint } from '@/utils/util'
import moment from 'moment'
import frmXemTSKT from './popup/frmXemTSKT'
enableRipple(true)
Vue.use(ContextMenuPlugin)
export default {
  props: ['dulieu'],
  components: {
    breadcrumb,
    pop_chitiethopdong,
    gridview,
    pop_diachild,
    pop_goidv,
    pop_phieuton,
    pop_dsbd,
    SearchContractModal,
    ThongTinEmail,
    ThongTinLienHe,
    VatTuThueBao,
    ThongTinHenKhachHangModal,
    TraCuuPortHDTB,
    VatTuHDTB,
    TraCuuNhanVienTH,
    Thuebao_ToaNha,
    InBienBan,
    IT360Information,
    DsdkDvGiaTang,
    ViewChiTietHDPL,
    popdstientrinh,
    KDatePicker,
    frmXemTSKT,
    frmThongTinTT: () => import('@/modules/contract/setup/ChangeService/popup/popThongTinTT_BoSung/index.vue')
  },
  name: 'SearchInstallDetail',
  mounted() {
    this.donvi_dl_id = Number(this.$root.token.getDonViDuLieuID())
    //this.DonViID =  this.$root.token.getDonViID();
    API.tocdo_kenh(this.axios, null).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        this.listbox.kenh_cir.list = response.data.data.map((x) => {
          return { id: x['TOCDO_ID'], text: x['TOCDO'] + x['DONVI'] }
        })
        this.listbox.kenh_pir.list = response.data.data.map((x) => {
          return { id: x['TOCDO_ID'], text: x['TOCDO'] + x['DONVI'] }
        })
        this.listbox.kenh_nix.list = response.data.data.map((x) => {
          return { id: x['TOCDO_ID'], text: x['TOCDO'] + x['DONVI'] }
        })
        this.listbox.kenh_isp.list = response.data.data.map((x) => {
          return { id: x['TOCDO_ID'], text: x['TOCDO'] + x['DONVI'] }
        })
      } else {
        this.listbox.kenh_cir.list = []
        this.listbox.kenh_pir.list = []
        this.listbox.kenh_nix.list = []
        this.listbox.kenh_isp.list = []
      }
    })
    API.dichvu_vt(this.axios, null).then((response) => {
      if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        this.listbox.dichvu_vt.list = response.data.data.map((x) => {
          return { id: x['DICHVUVT_ID'], text: x['TEN_DVVT'] }
        })
        this.listbox.dichvu_vt.value = 1
      } else this.listbox.dichvu_vt.list = []
    })
    //created by kylq.hcm
    // Bổ sung sài cho frmKhaoSatTB_TapTrung hàm tsBtn_LichSuHong_Click
    // Tham số query ma_tb
    // Date created: 20/02/2023
    let ma_tb = this.$route.query.ma_tb
    // let popup_maGD = this.dulieu.MA_GD
    if (this.data !== undefined) {
      let popup_maGD = this.dulieu.MA_GD
      this.HienThiThongTin(popup_maGD)
    }
    if (ma_tb) {
      this.HienThiThongTin(ma_tb)
    }
    //else if(popup_maGD.length > 0) {
    //  this.HienThiThongTin(popup_maGD)
    //}

    this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
      document.getElementsByClassName('modal-content').forEach(function (v) {
        v.removeAttribute('tabindex')
      })
    })
  },
  //end mounted
  destroyed() {},
  data() {
    return {
      check_popup: false,
      ejsDialog: {
        header: 'Tiêu đề',
        name: null,
        visible: false,
        config: {
          width: '95%',
          height: '95%',
          minHeight: '450px',
          enableResize: true,
          allowDragging: true,
          position: { X: 'center', Y: 'center' },
          // position: { X: 420, Y: 14 },
          animationSettings: { effect: 'Fade' }
        }
      },
      frmXemTSKT: { visible: false, input: {} },
      frmThongTinTT: { visible: false, input: {} },
      //
      thongTinLienHeDialog: false,
      thongTinEmailDialog: false,
      thuebaoToaNhaDialog: false,
      IT360InformationDialog: false,
      DsdkDvGiaTangDialog: false,
      show_popdstientrinh: false,
      btnXuatPhieuMenus: [
        {
          id: 'in_phieu',
          text: 'In Phiếu'
        }
      ],
      popupInBienBanParams: {},
      clicks: 0,
      delay: 500,
      timer: null,
      animationSettings: { effect: 'None' },
      dateFormat: 'DD/MM/YYYY',
      dateconfig: {
        altFormat: 'dd/mm/YYYY',
        altInput: true,
        dateFormat: 'dd/mm/YYYY',
        allowInput: true
      },
      DonViID: '',
      diachi_ld: '',
      phieu_yc: {
        hdkh_id: 0,
        hdtb_id: '',
        ma_gd: '',
        loai_hd: '',
        huong_kn_cu: '',
        huong_kn_moi: '',
        ngay_yc: '',
        ngay_lap_hd: '',
        kieu_ld: '',
        acc_moi: '',
        acc_cu: '',
        ten_kh: '',
        diachi_kh: '',
        nguoi_giao: '',
        noi_nhan: '',
        nguoi_lap_hd: '',
        ghi_chu: '',
        dt_lien_he: '',
        nha_cck: '',
        ngay_ycht: '',
        ctv: '',
        nv_tiepthi: '',
        khachhang_id: 0,
        loaihd_id: 0,
        ma_duan: ''
      },
      searchTerm: '',
      chitiethopdong: {
        thongtinchinh: '',
        thongtinthem: '',
        khachhang: {
          out_kh: [{}],
          out_mail: [{}]
        },
        thanhtoan: {
          out_tt: [{}],
          out_mail: [{}]
        },
        tienhopdong: {
          list: [],
          header: [
            { fieldName: 'ma_kmtt', headerText: 'Mã KMTT', allowFiltering: true },
            { fieldName: 'ten_kmtt', headerText: 'Tên KMTT', allowFiltering: true },
            { fieldName: 'tyle_vat', headerText: 'Tỷ lệ VAT', allowFiltering: true },
            { fieldName: 'tien', headerText: 'Tiền', allowFiltering: true },
            { fieldName: 'vat', headerText: 'VAT', allowFiltering: true }
          ]
        }
      },
      modelVatTu: {
        phieu_id: '',
        loaihd_id: '',
        hdtb_id: '',
        thuebao_id: ''
      },
      listbox: {
        kenh_cir: {
          list: [],
          value: '',
          isEnabled: false
        },
        kenh_pir: {
          list: [],
          value: '',
          isEnabled: false
        },
        kenh_nix: {
          list: [],
          value: '',
          isEnabled: false
        },
        kenh_isp: {
          list: [],
          value: '',
          isEnabled: false
        },
        dichvu_vt: {
          list: [],
          value: ''
        },
        // ds_thuebao: {
        //     list: [],
        //      header: [{fieldName:'ma_hd',headerText:'Mã HĐ'},{fieldName:'ngay_yc',headerText:'Ngày YC'},{fieldName:'ngaygiao',headerText:'Ngày giao'},{fieldName:'ma_tb',headerText:'Số máy/Acc'},
        //   {fieldName:'ten_tb',headerText:'Tên thuê bao'},{fieldName:'ten_kv',headerText:'Khu vực'},{fieldName:'ngay_hh',headerText:'Ngày hết hạn'},{fieldName:'so_dt',headerText:'Điện thoại LH'},
        //   {fieldName:'diemtinnhiem',headerText:'Điểm tín nhiệm'},{fieldName:'dv_nhan',headerText:'ĐV nhận'},{fieldName:'loaihinh_tb',headerText:'Loại hình'},{fieldName:'',headerText:'Trạng thái in'},
        //   {fieldName:'lydohuy',headerText:'Lý do huỷ'},{fieldName:'ten_status',headerText:'Kích hoạt tổng đài'},{fieldName:'dv_giao',headerText:'Đơn vị giao'},
        //   {fieldName:'ghichu',headerText:'Ghi chú'},{fieldName:'diachi_ld',headerText:'Địa chỉ LĐ'}],
        //     value: {},
        //     isEnabled: false,
        // },
        ds_thuebao: {
          list: [],
          header: [
            { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowfiltering: true },
            { fieldName: 'matb_tn', headerText: 'Mã TN', allowfiltering: true },
            { fieldName: 'ma_lt', headerText: 'Số ảo', allowfiltering: true },
            { fieldName: 'ma_vnpt', headerText: 'Mã VNPT', allowfiltering: true },
            { fieldName: 'ma_tb_cha', headerText: 'Thuê bao cha', allowfiltering: true },
            { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowfiltering: true },
            { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowfiltering: true },
            { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowfiltering: true },
            { fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', allowfiltering: true },
            { fieldName: 'ngay_thoai', headerText: 'Ngày thoái', allowfiltering: true },
            { fieldName: 'trangthai_kh', headerText: 'Trạng thái KH', allowfiltering: true },
            { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowfiltering: true },
            { fieldName: 'diachi_dau', headerText: 'Địa chỉ LĐ đầu', allowfiltering: true },
            { fieldName: 'diachi_cuoi', headerText: 'Địa chỉ LĐ cuối', allowfiltering: true },
            { fieldName: 'hinhthuc', headerText: 'HT chuyển đổi', allowfiltering: true },
            { fieldName: 'tocdo', headerText: 'Tốc độ', allowfiltering: true },
            { fieldName: 'ten_dt', headerText: 'Đối tượng', allowfiltering: true },
            { fieldName: 'gio_conlai', headerText: 'Giờ còn lại', allowfiltering: true },
            { fieldName: 'ngay_kt', headerText: 'Ngày hết hạn', allowfiltering: true },
            { fieldName: 'tg_thue_tu', headerText: 'TG thuê từ', allowfiltering: true },
            { fieldName: 'tg_thue_den', headerText: 'TG thuê đến', allowfiltering: true },
            { fieldName: 'ghichu', headerText: 'Ghi chú', allowfiltering: true },
            { fieldName: 'ngay_kh', headerText: 'Ngày kích hoạt', allowfiltering: true },
            { fieldName: 'ngay_ht', headerText: 'Ngày HT', allowfiltering: true },
            { fieldName: 'ngay_ins', headerText: 'Ngày nghiệm thu', allowfiltering: true },
            { fieldName: 'congnghe', headerText: 'Công nghệ', allowfiltering: true },
            { fieldName: 'ungdung_nt', headerText: 'Ứng dụng NT', allowfiltering: true },
            { fieldName: 'ma_gd_thaythe', headerText: 'Mã thay thế', allowfiltering: true }
          ],
          value: {},
          isEnabled: false
        },
        ds_chitiet: {
          list: [],
          header: [
            { fieldName: 'huonggiao', headerText: '', isGroupedColumn: true },
            // { fieldName: 'phieu_id', headerText: 'ID', allowFiltering: true },
            { fieldName: 'dvg', headerText: 'Đơn vị giao', allowFiltering: true },
            { fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true },
            { fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true },
            { fieldName: 'nd_giao', headerText: 'Nội dung giao' },
            { fieldName: 'dvn', headerText: 'Đơn vị nhận', allowFiltering: true },
            { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', allowFiltering: true },
            { fieldName: 'ngay_tra', headerText: 'Ngày trả' },
            { fieldName: 'nd_tra', headerText: 'Nội dung trả', allowFiltering: true },
            { fieldName: 'lydotra', headerText: 'Lý do trả', allowFiltering: true },
            { fieldName: 'ngay_cn', headerText: 'Ngày cập nhật' },
            { fieldName: 'ngay_th', headerText: 'Ngày TH' },
            { fieldName: 'nv_th', headerText: 'Người thực hiện', allowFiltering: true },
            { fieldName: 'so_dt', headerText: 'DT liên hệ', allowFiltering: true },
            { fieldName: 'ghichu', headerText: 'Ghi chú' },
            { fieldName: 'ketqua_xl', headerText: 'Kết quả XL', allowFiltering: true },
            { fieldName: 'chuongtrinh', headerText: 'Ứng dụng', allowFiltering: true },
            { fieldName: 'nd_thuchien', headerText: 'ND thực hiện', allowFiltering: true },
            { fieldName: 'nguoi_xuly', headerText: 'Người xử lý' }
          ],
          headerNoData: [
            { fieldName: 'huonggiao', headerText: 'Hướng giao', allowFiltering: true },
            { fieldName: 'dvg', headerText: 'Đơn vị giao', allowFiltering: true },
            { fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true },
            { fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true },
            { fieldName: 'nd_giao', headerText: 'Nội dung giao' },
            { fieldName: 'dvn', headerText: 'Đơn vị nhận', allowFiltering: true },
            { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', allowFiltering: true },
            { fieldName: 'ngay_tra', headerText: 'Ngày trả' },
            { fieldName: 'nd_tra', headerText: 'Nội dung trả', allowFiltering: true },
            { fieldName: 'lydotra', headerText: 'Lý do trả', allowFiltering: true },
            { fieldName: 'ngay_giao', headerText: 'Ngày giao' },
            { fieldName: 'ngay_th', headerText: 'Ngày TH' },
            { fieldName: 'nv_th', headerText: 'Người thực hiện', allowFiltering: true },
            { fieldName: 'so_dt', headerText: 'DT liên hệ', allowFiltering: true },
            { fieldName: 'ghichu', headerText: 'Ghi chú' },
            { fieldName: 'ketqua_xl', headerText: 'Kết quả XL', allowFiltering: true },
            { fieldName: 'chuongtrinh', headerText: 'Ứng dụng', allowFiltering: true },
            { fieldName: 'nd_thuchien', headerText: 'ND thực hiện', allowFiltering: true },
            { fieldName: 'nguoi_xuly', headerText: 'Người xử lý' }
          ],
          contextMenuItems: [{ text: 'Tra cứu tồn theo phiếu', target: '.e-content', id: 'tra_cuu_ton' }],
          value: {},
          isEnabled: false
        },
        ds_goidv: {
          list: [],
          header: [
            { fieldName: 'ma_tb', headerText: 'Số máy/Account', allowFiltering: true },
            { fieldName: 'ten_goi', headerText: 'Gói đa dịch vụ', allowFiltering: true }
          ]
        },
        ds_bd: {
          list: [],
          header: [
            { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true },
            { fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu', allowFiltering: true },
            { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true },
            { fieldName: 'ten_loaihd', headerText: 'Loại hợp đồng', allowFiltering: true },
            { fieldName: 'ten_kieuld', headerText: 'Kiếu lắp đặt', allowFiltering: true },
            { fieldName: 'ten_dv', headerText: 'Đơn vị lập HĐ/PL', allowFiltering: true }
          ]
        },
        ds_phieuton: {
          list: [],
          header: [
            { fieldName: 'ngay_bt', headerText: 'Ngày báo tồn', allowFiltering: true },
            { fieldName: 'nguoi_bt', headerText: 'Người báo tồn', allowFiltering: true },
            { fieldName: 'donvi_bt', headerText: 'Đơn vị báo tồn', allowFiltering: true },
            { fieldName: 'nhom_ton', headerText: 'Nhóm tồn', allowFiltering: true },
            { fieldName: 'lydoton_tc', headerText: 'Lý do tồn', allowFiltering: true },
            { fieldName: 'nguoi_cn', headerText: 'Người cập nhật', allowFiltering: true },
            { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true }
          ]
        },
        DsPort: {
          headers: [
            { fieldName: 'slot', headerText: 'Slot' },
            { fieldName: 'port', headerText: 'Port' },
            { fieldName: 'vpi', headerText: 'Vpi/Svlan' },
            { fieldName: 'vci', headerText: 'Vci/Cvlan' },
            { fieldName: 'rack', headerText: 'Rack' },
            { fieldName: 'shelf', headerText: 'Shelf' },
            { fieldName: 'system', headerText: 'System' },
            { fieldName: 'tendslam', headerText: 'Dslam' },
            { fieldName: 'vitri', headerText: 'Vị trí Port' },
            { fieldName: 'ten_card', headerText: 'Card' },
            { fieldName: 'selfid01', headerText: 'SelfID' },
            { fieldName: 'selfid02', headerText: 'OLT Slot' },
            { fieldName: 'selfid03', headerText: 'OLT Port' },
            { fieldName: 'selfid04', headerText: 'ONU ID' },
            { fieldName: 'adsl_port', headerText: 'Adsl port' },
            { fieldName: 'port_mdf', headerText: 'Port MDF' },
            { fieldName: 'ketcuoi', headerText: 'Spliter' }
          ],
          list: []
        },
        ds_nhanvienTH: []
      },
      donvi_dl_id: 0,
      chiTietHDPLDialog: {
        input: {
          vhdtb_id: 0,
          vhdkh_id: 0
        },
        visible: false,
        animationSettings: { effect: 'None' }
      },
      isShowInBB: false,
      gridDSTB: {
        value: null,
        selected: []
      }
    }
  },
  methods: {
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    ejsDialogBeforeOpen() {
      let width = null
      if (this.ejsDialog.name == 'frmNhapText') {
        width = 'auto'
      } else {
        width = '95%'
      }
      if (this.ejsDialog.name == 'frmTraCuuHopDong') {
        this.ejsDialog.config.height = 800
      } else {
        this.ejsDialog.config.height = 'auto'
      }
      this.ejsDialog.config = {
        ...this.ejsDialog.config,
        width: width,
        // position: { X: 100, Y: 100 }
        position: { X: 'center', Y: 'center' }
      }
      // this.ejsDialog.config.height = 'auto'
      // this.$refs.ejsDialog.refreshPosition()
      // this.ejsDialog.config.enableResize = false
      // this.ejsDialog.config.allowDragging = false
      // this.ejsDialog.config.position = { X: 'center', Y: 'center' }
      // this.ejsDialog.config.position = { X: 420, Y: 14 }
      // }, 3000)
    },
    ejsDialogBeforeClose() {
      this.ejsDialog.name = null
      // this.$refs.ejsDialog.refreshPosition()
      this.ejsDialog.config.width = '0'
      this.ejsDialog.config.height = '0'
      this.ejsDialog.config.position = { X: 'center', Y: 'center' }
    },
    onBtnXuatPhieuSelect: function (args) {
      if (args.item.id === 'in_phieu') {
        // :params='{"ma_gd": phieu_yc.ma_gd,"hdtb_id": phieu_yc.hdtb_id}'
        this.popupInBienBanParams.n_hdtb_id = this.phieu_yc.hdtb_id
        this.popupInBienBanParams.n_hdkh_id = this.phieu_yc.hdkh_id
        this.popupInBienBanParams.hdkh_id = this.phieu_yc.hdkh_id
        this.popupInBienBanParams.nvth_id = this.phieu_yc.nv_tiepthi
        this.popupInBienBanParams.cv_thuchien = ''
        this.popupInBienBanParams.n_phieu_id = this.modelVatTu.phieu_id
        this.popupInBienBanParams.n_hdtb_id = this.phieu_yc.hdtb_id
        this.popupInBienBanParams.huonggiao_id = this.phieu_yc.huonggiao_id
        this.popupInBienBanParams.tentram_tb = ''
        this.popupInBienBanParams.loaihd_id = parseInt(this.phieu_yc.loaihd_id)
        // console.log('popupInBienBanParams')
        // console.log(this.popupInBienBanParams)
        this.$refs.InBienBan.showModal()
      }
    },
    btnXuatPhieuClick: function (event) {
      document.getElementById('cmenuXuatPhieu').ej2_instances[0].open(event.clientY, event.clientX)
    },
    cMenuDsChiTietClick: function (cMenuData) {
      if (cMenuData.item.id === 'tra_cuu_ton') {
        this.show_phieuton_by_phieu_id(cMenuData.rowInfo.rowData.phieu_id)
        this.$bvModal.show('pop_phieuton')
      }
    },
    cbo_kenh_checked(kenh) {
      if (kenh == '1') this.listbox.kenh_nix.isEnabled = !this.listbox.kenh_nix.isEnabled
      else if (kenh == '2') this.listbox.kenh_pir.isEnabled = !this.listbox.kenh_pir.isEnabled
      else if (kenh == '3') this.listbox.kenh_isp.isEnabled = !this.listbox.kenh_isp.isEnabled
    },
    timkiem: async function () {
      try {
        if (this.phieu_yc.ma_gd != '') {
          this.loading(true)
          /*
            THIEU DOAN CHECK DANH SACH BIEN DONG -  DANG YEU CAU BO SUNG API
            SAU KHI CHECK DANH SACH DSBD THI CHIA 2 TRUONG HOP:
            NEU CO NHIEU BIEN DONG THI BAT POPUP DSBD VA CHON 1 BIEN DONG DE HIEN THI THONG TIN
            NEU CO 1 BIEN DONG THI GAN THONG TIN PHIEU
          */
          //ma_tb co 1 bien dong:hung3776598
          //ma_tb co nhieu bien dong:anh33277
          //check danh sach bien dong:
          var data = {
            ma_tb: this.phieu_yc.ma_gd.trim()
          }
          let api_res = await API.lay_ds_hd_theo_ma_tb(this.axios, data)
          if (api_res && api_res.data && api_res.data.data && api_res.data.error && api_res.data.error_code === 'BSS-00000000')
            if (api_res.data.data.length > 1) {
              this.loading(false)
              if (this.dulieu != undefined) this.check_popup = true
              api_res.data.data = api_res.data.data.sort((a, b) => {
                var d1 = Date.parse(a.ngay_yc)
                var d2 = Date.parse(b.ngay_yc)
                return d1 < d2 //
              })
              this.listbox.ds_bd.list = api_res.data.data
              this.$bvModal.show('pop_dsbd')
            } else {
              this.nhap_moi()
              if (this.dulieu != undefined) this.check_popup = true
              //gan thong tin phieu yyeu cauloaihd_id
              this.phieu_yc.hdkh_id = parseInt(api_res.data.data[0].hdkh_id)
              this.phieu_yc.ma_gd = api_res.data.data[0].ma_gd
              this.phieu_yc.loai_hd = api_res.data.data[0].ten_loaihd
              this.phieu_yc.ngay_yc = api_res.data.data[0].ngay_yc
              this.phieu_yc.ngay_lap_hd = api_res.data.data[0].ngaylap_hd
              this.phieu_yc.ten_kh = api_res.data.data[0].ten_kh
              this.phieu_yc.diachi_kh = api_res.data.data[0].diachi_kh
              this.phieu_yc.dt_lien_he = api_res.data.data[0].so_dt
              this.phieu_yc.nha_cck = api_res.data.data[0].chuyenmang == 1 ? true : false
              this.phieu_yc.khachhang_id = parseInt(api_res.data.data[0].khachhang_id)
              this.phieu_yc.loaihd_id = api_res.data.data[0].loaihd_id
              this.phieu_yc.ma_duan = api_res.data.data[0].ma_duan
              console.log('this.phieu_yc')
              console.log(this.phieu_yc)
              // if (response.data.data[0].chuyenmang == 1)
              //  this.phieu_yc.nha_cck = true;
              // else
              //  this.phieu_yc.nha_cck = false;
              //load danh sach thue bao
              var data = {
                hdkh_id: this.phieu_yc.hdkh_id
              }
              await API.lay_ds_hd_tb_theo_hdkh_id_v3(this.axios, data).then((response) => {
                if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                  this.listbox.ds_thuebao.list = response.data.data
                  this.phieu_yc.hdtb_id = response.data.data[0].hdtb_id
                  this.listbox.dichvu_vt.value = response.data.data[0].dichvuvt_id
                  this.phieu_yc.kieu_ld = response.data.data[0].ten_kieuld
                  this.phieu_yc.acc_moi = response.data.data[0].ma_tb
                  this.phieu_yc.ghi_chu = response.data.data[0].ghichu
                  //model vat tu
                  this.modelVatTu.hdtb_id = response.data.data[0].hdtb_id
                  this.modelVatTu.thuebao_id = response.data.data[0].thuebao_id
                } else this.listbox.ds_thuebao.list = []
              })
              await API.sp_lay_tt_search_by_hdkh_id(this.axios, data).then((response) => {
                if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                  this.phieu_yc.nguoi_lap_hd = response.data.data.out_txtnguoilhd
                  this.phieu_yc.ctv = response.data.data.out_txtctv
                  this.phieu_yc.nv_tiepthi = response.data.data.out_txtnguoigt
                } else this.phieu_yc.nguoi_lap_hd = response.data.data.out_txtnguoilhd
                this.phieu_yc.ctv = response.data.data.out_txtctv
                this.phieu_yc.nv_tiepthi = response.data.data.out_txtnguoigt
              })
              // var data = {
              //           "hdtb_id":4544445,
              //           "dichvuvt_id":8,
              //           "sl_row":1
              //           };
              var data = {
                hdtb_id: this.phieu_yc.hdtb_id,
                dichvuvt_id: this.listbox.dichvu_vt.value
                //"sl_row":this.listbox.ds_thuebao.list.length
              }
              if (!this.phieu_yc.hdtb_id) {
                this.loading(false)
                this.$toast.error('Lỗi hdtb_id null khi load danh sách hợp đồng')
                return
              }
              await API.sp_lay_tt_by_selection_index_changed(this.axios, data).then((response) => {
                if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                  if (response.data.data.out_ds_kn) {
                    this.phieu_yc.huong_kn_cu = response.data.data.out_ds_kn[0].huong_kn_cu
                    this.phieu_yc.huong_kn_moi = response.data.data.out_ds_kn[0].huong_kn_moi
                  } else {
                    this.phieu_yc.huong_kn_cu = ''
                    this.phieu_yc.huong_kn_moi = ''
                  }
                  if (response.data.data.out_ma_tb_cu) this.phieu_yc.acc_cu = response.data.data.out_ma_tb_cu
                  else this.phieu_yc.acc_cu = ''
                  if (response.data.data.out_ds_ctiet.length > 0) {
                    this.listbox.ds_chitiet.list = response.data.data.out_ds_ctiet
                    //this.phieu_yc.nguoi_giao = response.data.data.out_ds_ctiet[0].nv_giao;
                    this.phieu_yc.noi_nhan = response.data.data.out_ds_ctiet[0].dvn
                  } else {
                    this.listbox.ds_chitiet.list = []
                    this.phieu_yc.nguoi_giao = ''
                    this.phieu_yc.noi_nhan = ''
                  }
                  if (response.data.data.out_ds_tocdo && response.data.data.out_ds_tocdo.length > 0) {
                    this.phieu_yc.ngay_ycht = response.data.data.out_ds_tocdo[0].ngay_ycht ? moment(response.data.data.out_ds_tocdo[0].ngay_ycht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') : null
                    if (response.data.data.out_ds_tocdo[0].tocdo_isp) {
                      this.listbox.kenh_isp.value = response.data.data.out_ds_tocdo[0].tocdo_isp
                      this.listbox.kenh_isp.isEnabled = true
                    }
                    if (response.data.data.out_ds_tocdo[0].tocdo_pir_id) {
                      this.listbox.kenh_pir.value = response.data.data.out_ds_tocdo[0].tocdo_pir_id
                      this.listbox.kenh_pir.isEnabled = true
                    }
                    if (response.data.data.out_ds_tocdo[0].tocdo_nix) {
                      this.listbox.kenh_nix.value = response.data.data.out_ds_tocdo[0].tocdo_nix
                      this.listbox.kenh_nix.isEnabled = true
                    }
                    if (response.data.data.out_ds_tocdo[0].tocdo_id) {
                      this.listbox.kenh_cir.value = response.data.data.out_ds_tocdo[0].tocdo_id
                    }
                  } else {
                    if (response.data.data.out_ds_tocdo2 && response.data.data.out_ds_tocdo2.length > 0) {
                      this.phieu_yc.ngay_ycht = response.data.data.out_ds_tocdo2[0].ngay_ycht ? moment(response.data.data.out_ds_tocdo2[0].ngay_ycht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') : null
                      if (response.data.data.out_ds_tocdo2[0].tocdo_id) {
                        this.listbox.kenh_cir.value = response.data.data.out_ds_tocdo2[0].tocdo_id
                      }
                    } else {
                      this.phieu_yc.ngay_ycht = ''
                      this.listbox.kenh_cir.value = ''
                    }
                    this.listbox.kenh_isp.value = ''
                    this.listbox.kenh_isp.isEnabled = false
                    this.listbox.kenh_pir.value = ''
                    this.listbox.kenh_pir.isEnabled = false
                    this.listbox.kenh_nix.value = ''
                    this.listbox.kenh_nix.isEnabled = false
                  }
                  //modal vatu
                  if (response.data.data.out_tt_phieu.length > 0) {
                    this.modelVatTu.phieu_id = response.data.data.out_tt_phieu[0].phieu_id
                  }
                }
              })
              this.loading(false)
            }
          else {
            this.loading(false)
            this.$toast.error('Không tìm thấy hợp đồng')
          }
        }
      } catch (ex) {
        this.loading(false)
        this.$toast.error('Lỗi hiển thị thông tin hợp đồng')
      }
    },
    grdThuebaoChanged: function (obj) {
      this.phieu_yc.hdtb_id = ''
      this.listbox.dichvu_vt.value = ''
      this.phieu_yc.kieu_ld = ''
      this.phieu_yc.acc_moi = ''
      this.phieu_yc.ghi_chu = ''
      this.phieu_yc.hdtb_id = obj[0].hdtb_id
      this.listbox.dichvu_vt.value = obj[0].dichvuvt_id
      this.phieu_yc.kieu_ld = obj[0].ten_kieuld
      this.phieu_yc.acc_moi = obj[0].ma_tb
      this.phieu_yc.ghi_chu = obj[0].ghichu
      //model vat tu
      this.modelVatTu.hdtb_id = obj[0].hdtb_id
      this.modelVatTu.thuebao_id = obj[0].thuebao_id
      //load lai danh sach chi tiet
      var data = {
        hdtb_id: this.phieu_yc.hdtb_id,
        dichvuvt_id: this.listbox.dichvu_vt.value
        //"sl_row":this.listbox.ds_thuebao.list.length
      }
      API.sp_lay_tt_by_selection_index_changed(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          if (response.data.data.out_ds_kn) {
            this.phieu_yc.huong_kn_cu = response.data.data.out_ds_kn[0].huong_kn_cu
            this.phieu_yc.huong_kn_moi = response.data.data.out_ds_kn[0].huong_kn_moi
          } else {
            this.phieu_yc.huong_kn_cu = ''
            this.phieu_yc.huong_kn_moi = ''
          }
          if (response.data.data.out_ma_tb_cu) this.phieu_yc.acc_cu = response.data.data.out_ma_tb_cu
          else this.phieu_yc.acc_cu = ''
          if (response.data.data.out_ds_ctiet.length > 0) {
            this.listbox.ds_chitiet.list = response.data.data.out_ds_ctiet
            //this.phieu_yc.nguoi_giao = response.data.data.out_ds_ctiet[0].nv_giao;
            this.phieu_yc.noi_nhan = response.data.data.out_ds_ctiet[0].dvn
          } else {
            this.listbox.ds_chitiet.list = []
            this.phieu_yc.nguoi_giao = ''
            this.phieu_yc.noi_nhan = ''
          }
          if (response.data.data.out_ds_tocdo && response.data.data.out_ds_tocdo.length > 0) {
            this.phieu_yc.ngay_ycht = response.data.data.out_ds_tocdo[0].ngay_ycht ? moment(response.data.data.out_ds_tocdo[0].ngay_ycht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') : null
            if (response.data.data.out_ds_tocdo[0].tocdo_isp) {
              this.listbox.kenh_isp.value = response.data.data.out_ds_tocdo[0].tocdo_isp
              this.listbox.kenh_isp.isEnabled = true
            }
            if (response.data.data.out_ds_tocdo[0].tocdo_pir_id) {
              this.listbox.kenh_pir.value = response.data.data.out_ds_tocdo[0].tocdo_pir_id
              this.listbox.kenh_pir.isEnabled = true
            }
            if (response.data.data.out_ds_tocdo[0].tocdo_nix) {
              this.listbox.kenh_nix.value = response.data.data.out_ds_tocdo[0].tocdo_nix
              this.listbox.kenh_nix.isEnabled = true
            }
            if (response.data.data.out_ds_tocdo[0].tocdo_id) {
              this.listbox.kenh_cir.value = response.data.data.out_ds_tocdo[0].tocdo_id
            }
          } else {
            if (response.data.data.out_ds_tocdo2 && response.data.data.out_ds_tocdo2.length > 0) {
              this.phieu_yc.ngay_ycht = response.data.data.out_ds_tocdo2[0].ngay_ycht ? moment(response.data.data.out_ds_tocdo2[0].ngay_ycht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') : null
              if (response.data.data.out_ds_tocdo2[0].tocdo_id) {
                this.listbox.kenh_cir.value = response.data.data.out_ds_tocdo2[0].tocdo_id
              }
            } else {
              this.phieu_yc.ngay_ycht = ''
              this.listbox.kenh_cir.value = ''
            }
            this.listbox.kenh_isp.value = ''
            this.listbox.kenh_isp.isEnabled = false
            this.listbox.kenh_pir.value = ''
            this.listbox.kenh_pir.isEnabled = false
            this.listbox.kenh_nix.value = ''
            this.listbox.kenh_nix.isEnabled = false
          }
          //modal vatu
          if (response.data.data.out_tt_phieu.length > 0) {
            this.modelVatTu.phieu_id = response.data.data.out_tt_phieu[0].phieu_id
          }
        }
      })
    },
    grdChitietChanged: async function (obj) {
      if (obj && obj[0]) {
        this.phieu_yc.huonggiao_id = obj[0].huonggiao_id
        this.modelVatTu.phieu_id = obj[0].phieu_id
        if (obj[0].nv_giao) this.phieu_yc.nguoi_giao = obj[0].nv_giao
        if (obj[0].dvn) this.phieu_yc.noi_nhan = obj[0].dvn
      }
    },
    DblClickTree: async function (i, obj) {
      var data = {
        phieu_id: obj.phieu_id,
        phieu_bh_id: 0,
        phieu_kn_id: 0,
        baohong_id: 0,
        hdtb_id: 0,
        chitietcc_id: 0,
        giaophieu_vt_id: 0,
        nhanvien_id: 0
      }
      this.listbox.ds_nhanvienTH = []
      await API.sp_lay_ds_nhanvien_thuchien(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.listbox.ds_nhanvienTH = response.data.data
        }
      })
      this.$refs.TraCuuNhanVienTH.show()
    },
    DblClickTree: async function (i, obj) {
      var data = {
        phieu_id: obj.phieu_id,
        phieu_bh_id: 0,
        phieu_kn_id: 0,
        baohong_id: 0,
        hdtb_id: 0,
        chitietcc_id: 0,
        giaophieu_vt_id: 0,
        nhanvien_id: 0
      }
      this.listbox.ds_nhanvienTH = []
      await API.sp_lay_ds_nhanvien_thuchien(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.listbox.ds_nhanvienTH = response.data.data
        }
      })
      this.$refs.TraCuuNhanVienTH.show()
    },
    onDanhSachThueBaoGridDoubleClicked(i, obj) {
      this.DsdkDvGiaTang_OnClick()
      this.phieu_yc.hdtb_id = obj.hdtb_id
    },
    nhap_moi: function () {
      this.phieu_yc.hdkh_id = 0
      this.phieu_yc.hdtb_id = ''
      this.phieu_yc.ma_gd = ''
      this.phieu_yc.loai_hd = ''
      this.phieu_yc.ma_duan = ''
      this.phieu_yc.huong_kn_cu = ''
      this.phieu_yc.huong_kn_moi = ''
      this.phieu_yc.ngay_yc = ''
      this.phieu_yc.ngay_lap_hd = ''
      this.phieu_yc.kieu_ld = ''
      this.phieu_yc.acc_moi = ''
      this.phieu_yc.acc_cu = ''
      this.phieu_yc.ten_kh = ''
      this.phieu_yc.diachi_kh = ''
      this.phieu_yc.nguoi_giao = ''
      this.phieu_yc.noi_nhan = ''
      this.phieu_yc.nguoi_lap_hd = ''
      this.phieu_yc.ghi_chu = ''
      this.phieu_yc.dt_lien_he = ''
      this.phieu_yc.nha_cck = ''
      this.phieu_yc.ngay_ycht = ''
      this.phieu_yc.ctv = ''
      this.phieu_yc.nv_tiepthi = ''
      this.phieu_yc.khachhang_id = 0
      this.listbox.ds_thuebao.list = []
      this.listbox.ds_chitiet.list = []
      this.listbox.ds_goidv.list = []
      this.listbox.ds_bd.list = []
      this.listbox.ds_phieuton.list = []
      this.listbox.ds_nhanvienTH = []
    },
    show_diachild: function () {
      var data = {
        hdtb_id: this.phieu_yc.hdtb_id,
        dichvu_id: this.listbox.dichvu_vt.value
      }
      this.loading(true)
      API.sp_lay_tt_show_diachi_ld(this.axios, data).then((response) => {
        this.loading(false)
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') this.diachi_ld = response.data.data
        else this.diachi_ld = ''
      })
    },
    show_goidv: function () {
      var data = {
        hdtb_id: this.phieu_yc.hdtb_id,
        hdkh_id: this.phieu_yc.hdkh_id
      }
      // var data = {
      //           "hdtb_id":2981760,
      //           "hdkh_id":4504652
      //       };
      API.sp_lay_ds_tb_dk_goi(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') this.listbox.ds_goidv.list = response.data.data
        else this.listbox.ds_goidv.list = []
      })
    },
    show_phieuton: function () {
      var data = {
        hdtb_id: this.phieu_yc.hdtb_id,
        phieu_id: 0
      }
      API.sp_lay_ds_ton_phieu_hist(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') this.listbox.ds_phieuton.list = response.data.data
        else this.listbox.ds_phieuton.list = []
      })
    },
    show_phieuton_by_phieu_id: function (phieu_id) {
      var data = {
        hdtb_id: this.phieu_yc.hdtb_id,
        phieu_id: phieu_id
      }
      API.sp_lay_ds_ton_phieu_hist(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') this.listbox.ds_phieuton.list = response.data.data
        else this.listbox.ds_phieuton.list = []
      })
    },
    search_get_dsbd: async function (obj) {
      this.searchTerm = this.phieu_yc.ma_gd
      this.nhap_moi()
      //gan thong tin phieu yyeu cau
      this.phieu_yc.hdkh_id = parseInt(obj.hdkh_id)
      this.phieu_yc.ma_gd = obj.ma_gd
      this.phieu_yc.loai_hd = obj.ten_loaihd
      this.phieu_yc.ngay_yc = obj.ngay_yc
      this.phieu_yc.ngay_lap_hd = obj.ngaylap_hd
      this.phieu_yc.ten_kh = obj.ten_kh
      this.phieu_yc.diachi_kh = obj.diachi_kh
      this.phieu_yc.dt_lien_he = obj.so_dt
      this.phieu_yc.nha_cck = obj.chuyenmang == 1 ? true : false
      this.phieu_yc.khachhang_id = parseInt(obj.khachhang_id)
      this.phieu_yc.ma_duan = obj.ma_duan
      //load danh sach thue bao
      var data = {
        hdkh_id: this.phieu_yc.hdkh_id
      }
      await API.lay_ds_hd_tb_theo_hdkh_id_v3(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          //this.listbox.ds_thuebao.list = response.data.data
          let temp = response.data.data
          let index = response.data.data.findIndex((item) => item.ma_tb && item.ma_tb.trim() == this.searchTerm)
          if (index > -1) {
            let row = temp[index]
            temp.splice(index, 1)
            temp.unshift(row)
          }
          this.listbox.ds_thuebao.list = temp
          this.searchTerm = obj.ma_gd
          this.phieu_yc.hdtb_id = this.listbox.ds_thuebao.list[0].hdtb_id
          this.listbox.dichvu_vt.value = this.listbox.ds_thuebao.list[0].dichvuvt_id
          this.phieu_yc.kieu_ld = this.listbox.ds_thuebao.list[0].ten_kieuld
          this.phieu_yc.acc_moi = this.listbox.ds_thuebao.list[0].ma_tb
          this.phieu_yc.ghi_chu = this.listbox.ds_thuebao.list[0].ghichu
        } else this.listbox.ds_thuebao.list = []
      })
      API.sp_lay_tt_search_by_hdkh_id(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.phieu_yc.nguoi_lap_hd = response.data.data.out_txtnguoilhd
          this.phieu_yc.ctv = response.data.data.out_txtctv
          this.phieu_yc.nv_tiepthi = response.data.data.out_txtnguoigt
        } else this.phieu_yc.nguoi_lap_hd = response.data.data.out_txtnguoilhd
        this.phieu_yc.ctv = response.data.data.out_txtctv
        this.phieu_yc.nv_tiepthi = response.data.data.out_txtnguoigt
      })
      // var data = {
      //           "hdtb_id":4544445,
      //           "dichvuvt_id":8,
      //           "sl_row":1
      //           };
      var data = {
        hdtb_id: this.phieu_yc.hdtb_id,
        dichvuvt_id: this.listbox.dichvu_vt.value
        //"sl_row":this.listbox.ds_thuebao.list.length
      }
      API.sp_lay_tt_by_selection_index_changed(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          if (response.data.data.out_ds_kn) {
            this.phieu_yc.huong_kn_cu = response.data.data.out_ds_kn[0].huong_kn_cu
            this.phieu_yc.huong_kn_moi = response.data.data.out_ds_kn[0].huong_kn_moi
          } else {
            this.phieu_yc.huong_kn_cu = ''
            this.phieu_yc.huong_kn_moi = ''
          }
          if (response.data.data.out_ma_tb_cu) this.phieu_yc.acc_cu = response.data.data.out_ma_tb_cu
          else this.phieu_yc.acc_cu = ''
          if (response.data.data.out_ds_ctiet.length > 0) {
            this.listbox.ds_chitiet.list = response.data.data.out_ds_ctiet
            //this.phieu_yc.nguoi_giao = response.data.data.out_ds_ctiet[0].nv_giao;
            this.phieu_yc.noi_nhan = response.data.data.out_ds_ctiet[0].dvn
          } else {
            this.listbox.ds_chitiet.list = []
            this.phieu_yc.nguoi_giao = ''
            this.phieu_yc.noi_nhan = ''
          }
          if (response.data.data.out_ds_tocdo && response.data.data.out_ds_tocdo.length > 0) {
            this.phieu_yc.ngay_ycht = response.data.data.out_ds_tocdo[0].ngay_ycht ? moment(response.data.data.out_ds_tocdo[0].ngay_ycht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') : null
            if (response.data.data.out_ds_tocdo[0].tocdo_isp) {
              this.listbox.kenh_isp.value = response.data.data.out_ds_tocdo[0].tocdo_isp
              this.listbox.kenh_isp.isEnabled = true
            }
            if (response.data.data.out_ds_tocdo[0].tocdo_pir_id) {
              this.listbox.kenh_pir.value = response.data.data.out_ds_tocdo[0].tocdo_pir_id
              this.listbox.kenh_pir.isEnabled = true
            }
            if (response.data.data.out_ds_tocdo[0].tocdo_nix) {
              this.listbox.kenh_nix.value = response.data.data.out_ds_tocdo[0].tocdo_nix
              this.listbox.kenh_nix.isEnabled = true
            }
            if (response.data.data.out_ds_tocdo[0].tocdo_id) {
              this.listbox.kenh_cir.value = response.data.data.out_ds_tocdo[0].tocdo_id
            }
          } else {
            if (response.data.data.out_ds_tocdo2 && response.data.data.out_ds_tocdo2.length > 0) {
              this.phieu_yc.ngay_ycht = response.data.data.out_ds_tocdo2[0].ngay_ycht ? moment(response.data.data.out_ds_tocdo2[0].ngay_ycht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss') : null
              if (response.data.data.out_ds_tocdo2[0].tocdo_id) {
                this.listbox.kenh_cir.value = response.data.data.out_ds_tocdo2[0].tocdo_id
              }
            } else {
              this.phieu_yc.ngay_ycht = ''
              this.listbox.kenh_cir.value = ''
            }
            this.listbox.kenh_isp.value = ''
            this.listbox.kenh_isp.isEnabled = false
            this.listbox.kenh_pir.value = ''
            this.listbox.kenh_pir.isEnabled = false
            this.listbox.kenh_nix.value = ''
            this.listbox.kenh_nix.isEnabled = false
          }
          //modal vatu
          if (response.data.data.out_tt_phieu.length > 0) {
            this.modelVatTu.phieu_id = response.data.data.out_tt_phieu[0].phieu_id
          }
        }
      })
      /////////////
    },
    show_cthd: async function () {
      if (!this.phieu_yc.hdtb_id) {
        this.chitiethopdong.thongtinchinh = ''
        this.chitiethopdong.thongtinthem = ''
        this.chitiethopdong.khachhang.out_kh = [{}]
        this.chitiethopdong.khachhang.out_mail = [{}]
        this.chitiethopdong.thanhtoan.out_tt = [{}]
        this.chitiethopdong.thanhtoan.out_mail = [{}]
        this.chitiethopdong.tienhopdong.list = []
        return
      }
      this.loading(true)
      var data = {
        hdtb_id: this.phieu_yc.hdtb_id
      }
      // data = {
      //     "hdtb_id": 2717333
      // };
      await API.sp_lay_tt_thuebao_chinh_by_hdtb_id(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') this.chitiethopdong.thongtinchinh = response.data.data[0]
        else this.chitiethopdong.thongtinchinh = ''
      })
      await API.sp_lay_tt_thuebao_them_by_hdtb_id(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') this.chitiethopdong.thongtinthem = response.data.data[0]
        else this.chitiethopdong.thongtinthem = ''
      })
      await API.sp_lay_tt_khachhang_tien_by_hdtb_id(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.chitiethopdong.tienhopdong.list = response.data.data
        } else this.chitiethopdong.tienhopdong.list = []
      })
      await API.sp_lay_tt_khachhang_by_hdtb_id(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.chitiethopdong.khachhang = response.data.data
          if (response.data.data.out_mail == '') {
            this.chitiethopdong.khachhang.out_mail = [{ email: '', dangky: '', trangthai_sd: '', mucdich: '' }]
          }
        } else {
          this.chitiethopdong.khachhang.out_kh = ''
          this.chitiethopdong.khachhang.out_mail = ''
        }
      })
      await API.sp_lay_tt_thanhtoan_by_hdtb_id(this.axios, data).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.chitiethopdong.thanhtoan = response.data.data
          if (response.data.data.out_mail == '') {
            this.chitiethopdong.thanhtoan.out_mail = [{ email: '', dangky: '', trangthai_sd: '', mucdich: '' }]
          }
        } else this.chitiethopdong.thanhtoan = ''
      })
      this.loading(false)
    },
    // showPopupSearchContract(){
    //   this.$refs.popupSearchContract.showModal();
    // },
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal()
    },
    acceptSearchContract: function (item) {
      this.phieu_yc.ma_gd = item.ma_gd
    },
    async show_DSTienTrinh() {
      this.show_popdstientrinh = true
      this.$refs.dlg_DanhSachTienTrinh.show()
    },
    async close_DSTienTrinh() {
      this.show_popdstientrinh = false
    },
    async ThongTinEmail_OnClick() {
      this.thongTinEmailDialog = true
      this.$refs.thongtinemail.show()
    },
    async ThongTinEmail_OnClose() {
      this.thongTinEmailDialog = false
    },
    async ThongTinLienHe_OnClick() {
      this.thongTinLienHeDialog = true
      this.$refs.thongtinlienhe.show()
    },
    async ThongTinLienHe_OnClose() {
      this.thongTinLienHeDialog = false
    },
    async ThuebaoToaNha_OnClick() {
      this.thuebaoToaNhaDialog = true
      this.$refs.Thuebao_ToaNha.show()
    },
    async ThuebaoToaNha_OnClose() {
      this.thuebaoToaNhaDialog = false
    },
    async IT360Information_OnClick() {
      this.IT360InformationDialog = true
      this.$refs.IT360Information.show()
    },
    async IT360Information_OnClose() {
      this.IT360InformationDialog = false
    },
    async DsdkDvGiaTang_OnClick() {
      this.DsdkDvGiaTangDialog = true
      this.$refs.DsdkDvGiaTang.show()
    },
    async DsdkDvGiaTang_OnClose() {
      this.IT360InformationDialog = false
    },
    async chiTietHDPLDialogOnClick() {
      console.log('chiTietHDPLDialogOnClick')
      console.log(this.phieu_yc.hdtb_id)
      console.log(this.phieu_yc.hdkh_id)
      if (['', null, undefined].includes(this.phieu_yc.hdtb_id) || ['', null, undefined].includes(this.phieu_yc.hdkh_id)) {
        this.$toast.warning('Bạn chưa chọn thuê bao!')
        return
      }
      try {
        if(this.phieu_yc.hdtb_id){
          this.chiTietHDPLDialog.input.vhdtb_id = this.phieu_yc.hdtb_id
          this.chiTietHDPLDialog.input.vhdkh_id = this.phieu_yc.hdkh_id
          this.chiTietHDPLDialog.visible = true
          this.$refs.chiTietHDPLDialog.show()
        }
        else{
          this.$toast.warning("Bạn chưa chọn thuê bao")
        }
      } catch (e) {
        console.log(e)
      }
    },
    async chiTietHDPLDialogOnClose() {
      this.chiTietHDPLDialog.visible = false
    },
    tsbtnHen_Click: function () {
      this.$refs.thongTinHenKhachHangModal.showModal()
    },
    tsbtnChiTietPort_Click: function () {
      var input = { hdtb_id: this.phieu_yc.hdtb_id }
      API.lay_ds_port_hdtb(this.axios, input).then((response) => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.listbox.DsPort.list = response.data.data
        } else this.listbox.DsPort.list = []
      })
      this.$refs.TraCuuPortHDTB.show()
    },
    tsbtnVatTu_Click() {
      this.$refs.popupVatTuHDTB.show()
    },
    tsbtnSuaVatTu_Click: function () {
      this.$refs.popupVatTuThueBao.showModal()
    },
    tsbtnToaNha_Click: function () {
      this.ThuebaoToaNha_OnClick()
    },
    async xemTTTT_BS() {
      if (this.phieu_yc.hdkh_id == 0) {
        this.$toast.error('Chưa chọn hợp đồng!')
        return
      }
      this.$refs.frmThongTinTT.hdkh_id = this.phieu_yc.hdkh_id
      this.$refs.frmThongTinTT.hdtb_id = this.phieu_yc.hdtb_id
      this.$refs.frmThongTinTT.chophep_sua = 0
      this.$refs.frmThongTinTT.initData()
      this.$refs.frmThongTinTT.showModal()
    },
    HienThiThongTin(ma_tb) {
      this.phieu_yc.ma_gd = ma_tb
      this.timkiem()
    },
    async inBBTWifiToolStripMenuItem_Click() {
      const baseUrl = process.env.API
      var hdtb_id = 0
      var url = ''
      var _maBC = ''
      hdtb_id = this.phieu_yc.hdtb_id
      url = `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-in-bien-ban-tbi-wf?extend=pdf`
      _maBC = 'WIFIBB'

      if (hdtb_id == 0 || hdtb_id == null || url == '') {
        this.$toast.error('Không có dữ liệu in phiếu!')
        return
      }
      this.loading(true)
      await this.axios({
        url: url,
        method: 'POST',
        responseType: 'blob',
        data: {
          hdTbId: hdtb_id,
          nhanVienId: this.$auth.getNhanVienID()
        }
      }).then((response) => {
        previewPrint(response.data)
        this.loading(false)
      })
      this.loading(false)
    },
    async inBBToolStripMenuItem_Click() {
      const baseUrl = process.env.API
      var hdtb_id = 0
      var url = ''

      hdtb_id = this.phieu_yc.hdtb_id
      url = `${baseUrl}/web-qlvt/api/in-phieu/getPhieuIn?extend=pdf`
      if (hdtb_id == 0 || hdtb_id == null || url == '') {
        this.$toast.error('Không có dữ liệu in phiếu!')
        return
      }
      this.loading(true)
      await this.axios({
        url: url,
        method: 'POST',
        responseType: 'blob',
        data: {
          ma_bc: 'BBGVT',
          ds_tieuchi: [
            {
              name: 'vphanvung_id',
              value: this.$auth.getPhanVungID()
            },
            {
              name: 'vhdtb_id',
              value: hdtb_id
            },
            {
              name: 'vnhanvien_id',
              value: this.$auth.getNhanVienID()
            }
          ]
        }
      }).then((response) => {
        previewPrint(response.data)
      })
      this.loading(false)
    },
    async gridDSTB_rowSelected(e) {
      console.log('dstb_rowSelected')
      console.log(e)
      if (e.data) {
        this.gridDSTB.value = e.data
      } else {
        this.gridDSTB.value = null
      }
    },
    closeForm() {
      this.$emit('form-close', { ma_gd: this.phieu_yc.ma_gd })
    },
    async tsbtnXemTSKT_Click() {
      console.log('tsbtnXemTSKT_Click')
      this.frmXemTSKT.input = {
        vhdtb_id: this.gridDSTB.value.hdtb_id,
        vphanvung_id: this.$root.token.getPhanVungID()
      }
      console.log(this.frmXemTSKT.input)
      this.ejsDialog.name = 'frmXemTSKT'
      this.ejsDialog.header = 'Thông số kỹ thuật'
      this.$refs.ejsDialog.show()
    },
    async tsbtnXemThanhToan_Click() {
      console.log('tsbtnXemTSKT_Click')
      this.frmXemTSKT.input = {
        vhdtb_id: this.gridDSTB.value.hdtb_id,
        vphanvung_id: this.$root.token.getPhanVungID()
      }
      console.log(this.frmXemTSKT.input)
      this.ejsDialog.name = 'frmXemTSKT'
      this.ejsDialog.header = 'Thông số kỹ thuật'
      this.$refs.ejsDialog.show()
    }
  },
  watch: {},
  computed: {
    ds_chitiet_list: {
      get() {
        // if(this.listbox.ds_chitiet.list.length>0) {
        //     this.listbox.ds_chitiet.list[1].huonggiao_id = 807
        // }
        // var originalList = this.listbox.ds_chitiet.list;
        // var dataList = this.listbox.ds_chitiet.list;
        // var index = 1;
        // for(var i = 0;i < dataList.length; i++) {
        //   dataList[i].huonggiao = `${i}: Hướng giao ${dataList[i]}`
        //   for(var j = 0; j < i; j++) {
        //     if(dataList[j].huonggiao_id == dataList[i]) {
        //       dataList[i].huonggiao = dataList[j].huonggiao
        //       break;
        //     } else {
        //        index++;
        //     }
        //   }
        // }
        var dataList = this.listbox.ds_chitiet.list.map((e, index) => {
          this.listbox.ds_chitiet.list[index].stt = index
          if (index > 0 && this.listbox.ds_chitiet.list[index - 1].huonggiao_id == e.huonggiao_id) {
            ;(this.listbox.ds_chitiet.list[index].huonggiao = this.listbox.ds_chitiet.list[index - 1].huonggiao), (this.listbox.ds_chitiet.list[index].stt = index)
          } else {
            this.listbox.ds_chitiet.list[index].huonggiao = `${index + 1}: Hướng giao ${e.huonggiao}`
          }
          return e
        })
        return dataList
      },
      set(newValue) {}
    }
  }
}
</script>
<style>
.overflow-visible {
}
.dropdown-menu {
  top: 30px !important;
}
.drop {
  position: relative;
}
.dropdown1 {
  display: none;
  position: fixed;
  background: white;
  list-style: none;
  padding: 10px;
}
.dropdown1 li {
  cursor: pointer;
  font-weight: 600;
  padding: 5px 20px;
}
.drop:hover .dropdown1 {
  display: block;
}
</style>