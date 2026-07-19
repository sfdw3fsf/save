<template src="./SearchGeneral.html"></template>
<style src="./SearchGeneral.scss" scoped></style>
<style>
.hide {
  display: none;
}
.hide .breadcrumb {
  display: none;
}
.thaotac {
  background-color: transparent !important;
  border: none !important;
}
</style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component'
import API from './API'
import API_Detail from './components/API_Detail'
import { required, minLength } from 'vuelidate/lib/validators'
import XLSX from 'xlsx'
import DetailSub from './components/DetailSub.vue'
import popupInforAdd from './components/popupInforAdd.vue'
import popupAssignSubManager from './components/popupAssignSubManager.vue'
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
import ModalTraCuuVT from '@/modules/QLVT/components/TraCuuVatTuThueBao/TraCuuVatTuThueBao.vue'
import ThueBao_ToaNha from './popup/ThueBao_ToaNha.vue'
import tracuuBHXH from './popup/tracuuBHXH.vue'
import TraCuuHDDT from '@/modules/search/subscriber/SearchGeneral/popup/HDDT/TraCuuHDDT'
import popupNhanVienDiaBan from '@/modules/search/subscriber/SearchContactDetails/popup/popupNhanVienDiaBan.vue'
import TraCuuThongSoKTModal from './popup/TraCuuThongSoKT/TraCuuThongSoKTModal.vue'
import popupChonGrid from './components/popupChonGrid'
import DataGridCustom from '@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid'
//import ThongSoKyThuat from '@/modules/incident/subscriber/SearchIncidentTicket/PopUps/ThongSoKyThuat.vue'
// import CatHuyCuaHangPharmacyVue from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/CatHuyCuaHangPharmacy/CatHuyCuaHangPharmacy'
// import TraCuuPharmacy from './popup/TraCuuVNPTPharmacy/TraCuuPharmacy.vue'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: { breadcrumb, VueFlatPickr, API, XLSX, DetailSub, popupInforAdd, popupAssignSubManager, ModalChonToaDo, ModalTraCuuVT, ThueBao_ToaNha, popupNhanVienDiaBan, TraCuuThongSoKTModal, tracuuBHXH, popupChonGrid, DataGridCustom, TraCuuHDDT }, //TraCuuPharmacy },
  name: 'SearchGeneral',
  props: {
    inMaTB: String
  },
  async mounted() {
    await this.LoadCombobox()
    await this.DichVuChange(false)
    if (this.$route.query.ma_tb != null && this.$route.params.ma_tb != '' && this.$route.query.ma_tb.length > 0) {
      this.MaTB = this.$route.query.ma_tb
      this.onMaTBEnter()
    }
    if (this.inMaTB) {
      this.MaTB = this.inMaTB
      this.hideBreadcrumb = 'hide'
      this.onMaTBEnter()
    }
    //await this.HienThiTT_DBKH();
  },
  data() {
    return {
      popupTitle: '',
      dateconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput: true
      },
      ngay_ht: new Date(),
      header: {
        title: 'TRA CỨU DANH BẠ',
        list: [
          { name: 'Tra cứu', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu tổng hợp',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      toptarget: '.SearchGeneral',
      tsbtnHuyGiao: true,
      tsbtnTimKiem: true,
      tsbtnGiaoPhieu: true,
      ctmsGiaoPhieu: true,
      ctmsHuyPhieu: true,
      isTimTheoQuyTrinh: false,
      isTimTheoDichVu: false,
      isTimTheoLoaiHD: false,
      hideBreadcrumb: 'show',

      listbox: {
        ds_nhomgoi: {
          list: [],
          header: [
            { fieldName: 'ten_goi', headerText: 'Tên nhóm/gói', allowFiltering: true, width: 250 },
            { fieldName: 'chunhom', headerText: 'Chủ nhóm', allowFiltering: true },
            { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowHtml: true },
            { fieldName: 'ngay_dk', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, textAlign: 'Right', type: 'date', headerText: 'Ngày ĐK', allowFiltering: true, allowHtml: true },
            { fieldName: 'thang_bd', headerText: 'Tháng BĐ', textAlign: 'Right', allowFiltering: true, allowHtml: true },
            { fieldName: 'thang_kt', headerText: 'Tháng KT', textAlign: 'Right', allowFiltering: true, allowHtml: true }
          ],
          value: {},
          isEnabled: true,
          checked: []
        },
        ds_khuyenmai: {
          list: [],
          header: [
            { fieldName: 'loai_km', headerText: 'Loại KM', allowFiltering: true, width: 150 },
            { fieldName: 'ten_chitietkm', headerText: 'Chi tiết KM	', allowFiltering: true, width: 200 },
            { fieldName: 'ngay_dkdc', headerText: 'Ngày ĐK', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'ngay_bd', headerText: 'Ngày BĐ', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'ngay_kt', headerText: 'Ngày KT', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'ngay_bddc', headerText: 'Ngày BĐĐC', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'ngay_ktdc', headerText: 'Ngày KTĐC', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'thang_bd', headerText: 'Tháng BĐ', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'thang_kt', headerText: 'Tháng KT', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'thang_bddc', headerText: 'Tháng BĐĐC', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'thang_ktdc', headerText: 'Tháng KTĐC', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'ngay_thoai', headerText: 'Ngày thoái', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'thang_thoaitra', headerText: 'Tháng thoái', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'tien_thoai', headerText: 'Tiền thoái', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
            { fieldName: 'hieuluc', headerText: 'Hiệu lực', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'thang_huy', headerText: 'Tháng hủy', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'datcoc_csd', headerText: 'Tiền đặt cọc', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
            { fieldName: 'tyle_sd', headerText: 'Tỷ lệ SD', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'tien_sd', headerText: 'Tiền SD', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
            { fieldName: 'cuoc_sd', headerText: 'Giảm cước SD', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'tien_td', headerText: 'Tiền TD', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
            { fieldName: 'tyle_tb', headerText: 'Tỷ lệ TB', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'tien_tb', headerText: 'Tiền TB', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
            { fieldName: 'cuoc_tb', headerText: 'Giảm cước TB', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
            { fieldName: 'tyle_tk', headerText: 'Tỷ lệ TK', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'tyle_tc', headerText: 'Tỷ lệ TC', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
            { fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true, allowHtml: true, width: 100 }
          ],
          value: {},
          isEnabled: true,
          checked: []
        },
        ds_bdchuahoanthanh: {
          list: [],
          header: [
            { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150 },
            { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 150 },
            { fieldName: 'ten_loaihd', headerText: 'Loại HĐ/PL', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'ngay_yc', textAlign: 'Right', headerText: 'Ngày YC', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'ma_gd', headerText: 'Mã giao dịch', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'sothang_dc', headerText: 'Số tháng đặt cọc', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'ngay_bddc', headerText: 'Ngày BĐĐC', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'ngay_ktdc', headerText: 'Ngày KTĐC', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'ngay_hen', headerText: 'Ngày hẹn TT', allowFiltering: true, allowHtml: true, width: 150 }                      
          ],
          value: {},
          isEnabled: true,
          checked: []
        },
        ds_thuebao: {
          list: [],
          header: [
            { fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, width: 150 },
            { fieldName: 'ma_tt_neo', headerText: 'Mã TT Neo', allowFiltering: true, width: 150 },
            { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'ten_tb', headerText: 'Tên TB', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'sochinh', headerText: 'Số chính', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'tocdo', headerText: 'Tốc độ', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true, allowHtml: true },
            { fieldName: 'ngay_sd', headerText: 'Ngày SD', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'ngay_cat', headerText: 'Ngày cắt', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'ngay_td', headerText: 'Ngày TD', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'ngay_kp', headerText: 'Ngày KP', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'tg_thue_tu', headerText: 'Thuê từ ngày', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'tg_thue_den', headerText: 'Thuê đến ngày', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
            { fieldName: 'congnghe', headerText: 'Công nghệ', allowFiltering: true, allowHtml: true, width: 150 }
          ],
          value: {},
          isEnabled: true,
          checked: []
        },

        dichvu: {
          list: [],
          value: {
            DICHVUVT_ID: 0
          }
        },
        loaihinh: {
          list: [],
          value: {
            LOAITB_ID: 0
          }
        },
        loaihinh_full: {
          list: [],
          value: {}
        },
        doituong: {
          list: [],
          value: {}
        },
        hinhthuctt: {
          list: [],
          value: {}
        }
      },
      totalRecords: 0,
      isClick: false,
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      ngaygiao_tk: new Date(),
      ngaygiao: new Date(),

      success_code: 'BSS-00000000',
      NHANTIN_NHANVIEN_PHOIHOP: -1,
      SEND_SMS_NHANTIN_NV: -1,
      NHANTIN_THEO_HG: -1,
      ts_kt_tthdkhdn: 0, //Kiểm tra cho phép thêm thông tin khách hàng DN  đối với PTTB KHDN mới xài
      kt_phieutra_gdv: false,
      _pSendSMS: false,
      _Giaoviec_td: false,
      _Giaoviec_nvkd: false,

      searchTerm: '',

      MaKH: '',
      MaKHCu: '',
      TenKH: '',
      PLKH: '',
      DiaChiKH: '',
      TienTT: '0',
      ConNo: '0',
      khachhang_id: '',
      thuebao_id: '',

      chkAll_KM: false,
      txtKhuyenMai: '',

      MaTB: '',
      SoAo: '',
      DiaChiTB: '',
      DiaChiLD: '',
      TrangThaiTB: '',
      KhuVuc: '',
      TienTT_Tbao: '0',
      ConNo_Tbao: '0',
      MaGD: '',

      MaTT: '',
      MaDD: '',
      TenTT: '',
      DiaChiTT: '',
      DiaChiBC: '',
      MaInGhep: '',
      TienTT_TT: '0',
      ConNo_TT: '0',

      dataDiachi: {},
      //Popup
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      map: {
        lat: null,
        lng: null
      },
      map_info: {
        dia_chi: '',
        title: ''
      },
      popupDetailSubData: [],
      dataPopupNVDB: [],
      thongbaoLoi: ['Không tìm thấy thông tin khách hàng của mã KH : ', 'Không tìm thấy thông tin khách hàng của mã TT : ', 'Không tìm thấy thông tin khách hàng của mã TB : '],
      dl_db_cntt: '',
	  commandColumn: {
        show: true,
        headerText: 'Link QRCODE',
        width: 100,
        firstColumn: false
      },
	  commands: [
        {
          fieldName: "link_qr",
		  headerText: 'Link QRCODE',
          cssClass: 'thaotac', 
          name: 'thaotac',
          text: '<a href="javascript:void(0);">Xem QRCODE</a>',
          title: 'Xem QRCODE',
          width: 150,
        }
	  ]
    }
  },
  watch: {
    chkAll_KM: async function(newValue, oldValue) {
      this.sp_lay_tt_db_theo_thuebao(this.thuebao_id, newValue)
    }
  },
  computed: {
    NhapMoi: {
      get() {
        return this.isNhapMoi
      },
      set(value) {
        this.isNhapMoi = value
      }
    },
    CapNhat: {
      get() {
        return this.isCapNhat
      },
      set(value) {
        this.isCapNhat = value
      }
    },
    HuyBo: {
      get() {
        return this.isHuyBo
      },
      set(value) {
        this.isHuyBo = value
      }
    },
    XoaPhieu: {
      get() {
        return this.isXoaPhieu
      },
      set(value) {
        this.isXoaPhieu = value
      }
    }
  },
  validations: {
    MaTB: {
      required,
      minLength: minLength(1)
    },
    DK_tungay: {
      required,
      minLength: minLength(10)
    },
    DK_denngay: {
      required,
      minLength: minLength(10)
    },
    listbox: {
      goidadichvu: {
        idx: { required, minLength: minLength(1) }
      }
    }
  },
  methods: {
    ShowAlert(mesage, val) {
      if (val == 0) {
        this.$toast.success(mesage)
      } else if (val == 1) {
        this.$toast.warning(mesage)
      } else if (val == 2) {
        this.$toast.error(mesage)
      }
    },
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },

    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace(',', '.')
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    },

    selectedItems_click: function(dataKeys) {
      this.listbox.ds_bdchuahoanthanh.checked = dataKeys
      this.listbox.ds_bdchuahoanthanh.list.forEach(function(item) {
        if (dataKeys.filter((x) => x === item.phieu_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },
    gvDSTB_DataBound: function(args) {
      //thằng này ko gọi
      try {
        //return;

        if (this.listbox.ds_thuebao.list.length > 0) {
          let index = this.listbox.ds_thuebao.list.findIndex((x) => x.ma_tb.trim() == this.searchTerm)
          if (index >= 0) {
            this.$refs.ds_thuebao.selectRow(index)
          } else {
            this.$refs.ds_thuebao.selectRow(0)
          }
        }
      } catch (error) {
        console.log(error)
      }
    },
    LoadCombobox: async function() {
      var input = {}
      await API.CSS_DICHVU_VT(this.axios, input).then((response) => {
        if (response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.dichvu.list = response.data.data //.map(x=>({id:x.DICHVUVT_ID,text:x.TEN_DVVT}));
          if (this.listbox.dichvu.list.length > 0) {
            this.listbox.dichvu.value.DICHVUVT_ID = 4 //Dịch vụ BRCĐ
          }
        } else {
          this.ShowAlert('Lỗi lấy danh sách dịch vụ Viễn thông!', 2)
        }
      })

      let rsTTDM = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_cacloai_danhmuc')
      if (rsTTDM.error_code && rsTTDM.error_code === this.success_code && rsTTDM.data) {
        this.listbox.hinhthuctt.list = rsTTDM.data.dsHinhThucTT.filter((x) => x.httt_id > 0).map((x) => ({ id: x.httt_id, text: x.hinhthuc_tt }))
        if (this.listbox.hinhthuctt.list.length > 0) {
          this.listbox.hinhthuctt.value.HTTT_ID = this.listbox.hinhthuctt.list[0].id
        }
      } else {
        this.ShowAlert('Lỗi lấy danh sách hình thức thanh toán!', 2)
      }
      var input = {}
      await API.CSS_DOITUONG(this.axios, input).then((response) => {
        if (response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.doituong.list = response.data.data.filter((x) => x.DOITUONG_ID > 0).map((x) => ({ id: x.DOITUONG_ID, text: x.TEN_DT }))
          if (this.listbox.doituong.list.length > 0) {
            this.listbox.doituong.value.DOITUONG_ID = this.listbox.doituong.list[0].id
          }
        } else {
          this.ShowAlert('Lỗi lấy danh sách đối tượng!', 2)
        }
      })
      //console.log(this.listbox.loaihinh.value.LOAITB_ID);
    },

    onMaKHEnter: async function() {
      if (this.MaKH == '') {
        this.ShowAlert('Bạn chưa nhập mã KH để tra cứu!', 1)
        this.$refs.MaKH.focus()
        return
      }
      this.searchTerm = this.MaKH
      this.Clear()
      this.TimKiem_KH(this.searchTerm, 0)
    },

    onMaTBEnter: async function() {
      if (this.MaTB == '') {
        this.ShowAlert('Bạn chưa nhập mã TB để tra cứu!', 1)
        this.$refs.MaTB.focus()
        return
      }
      this.searchTerm = this.MaTB
      this.Clear()
      //this.TimKiem(this.MaTB,1)
      this.TimKiem_KH(this.searchTerm, 2)
    },

    onMaTTEnter: async function() {
      if (this.MaTT == '') {
        this.ShowAlert('Bạn chưa nhập mã TT để tra cứu!', 1)
        this.$refs.MaTT.focus()
        return
      }
      this.searchTerm = this.MaTT
      this.Clear()
      this.TimKiem_KH(this.searchTerm, 1)
    },
    async TimKiem_KH(ma, loai) {
      var input = {}
      input.ma_tb = ma
      var ketqua = []
      this.isClick = true
      this.loading(true)

      ketqua = await API.sp_lay_ds_db_kh_theo_makh_v2(input)
      this.loading(false)
      if (ketqua) {
        console.log(ketqua)
        if (ketqua.length > 1) {
          //this.popupComponent = ()=>import('./components/popupChonGrid');
          this.popupComponentName = 'popupKH'
          this.popupComponentData = {
            ds: ketqua,
            cols: [
              { fieldName: 'khachhang_id', headerText: 'Khách hàng ID', allowFiltering: false, allowSorting: false },
              { fieldName: 'ma_kh', headerText: 'Mã khách hàng', allowFiltering: false, allowSorting: false },
              { fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: false, allowSorting: false },
              { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: false, allowSorting: false },
              { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowFiltering: false, allowSorting: false }
            ],
            keycol: 'khachhang_id',
            allowExit: false,
            tieude: 'Danh sách khách hàng',
            tieudebang: 'Danh sách khách hàng'
          }
          this.Popup(this.popupComponentData)
        } else if (ketqua.length == 1) {
          this.MaKH = ketqua[0].ma_kh
          this.MaKHCu = ketqua[0].makh_cu
          this.TenKH = ketqua[0].ten_kh
          this.DiaChiKH = ketqua[0].diachi_kh
          //this.PLKH = ketqua[0].phanloaikh_id;
          this.khachhang_id = ketqua[0].khachhang_id
          //Lấy thông tin biến động chưa hoàn thành
          var input1 = {}
          input1.khachhang_id = ketqua[0].khachhang_id
          input1.phanloaikh_id = ketqua[0].phanloaikh_id
          this.loading(true)
          API.sp_lay_tt_db_theo_kh(this.axios, input1).then((response) => {
            this.loading(false)
            if (response.data.error_code && response.data.error_code === this.success_code) {
              this.listbox.ds_bdchuahoanthanh.list = response.data.data.data
              this.TienTT = response.data.data.out_tienttno
              this.ConNo = response.data.data.out_tienno
              this.PLKH = response.data.data.out_phanloaikh
            } else {
              this.ShowAlert('Lỗi lấy thông tin nợ khách hàng!', 2)
            }
          })
          //Lấy danh sách thuê bao
          let pIndex = 1
          let pSize = this.$refs.ds_thuebao.$refs.pagination.pageSizeData
          var input2 = {
            khachhang_id: ketqua[0].khachhang_id,
            ma_tb: ma,
            pageNum: pIndex,
            pageRec: pSize,
            totalRow: 1
          }

          this.loading(true)

          API.lay_ds_dbtb_theo_khid_page(this.axios, input2).then((response) => {
            this.loading(false)
            if (response.data.error_code && response.data.error_code === this.success_code) {
              this.listbox.ds_thuebao.list = response.data.data
              this.totalRecords = response.data.page_info.record_count
              // let temp=response.data.data
              // let index = response.data.data.findIndex(
              //   (item) => item.ma_tb.trim() == this.searchTerm
              // );
              // if (index!=-1){
              //   let row = response.data.data[index]
              //   temp.splice(index, 1)
              //   temp.unshift(row)
              // }
              // // console.log('index')
              // // console.log(index)

              // this.listbox.ds_thuebao.list  = temp
            } else {
              this.ShowAlert('Lỗi lấy thông tin danh sách thuê bao!', 2)
            }
          })

          // // var input2 = {};
          // // input2.khachhang_id = ketqua[0].khachhang_id;
          // this.loading(true);
          // API.lay_ds_dbtb_theo_khid_v2(this.axios, input2).then((response) => {
          //   this.loading(false);
          //   if(response.data.error_code && response.data.error_code === this.success_code) {

          //     let temp=response.data.data
          //     let index = response.data.data.findIndex(
          //       (item) => item.ma_tb.trim() == this.searchTerm
          //     );
          //     if (index!=-1){
          //       let row = response.data.data[index]
          //       temp.splice(index, 1)
          //       temp.unshift(row)
          //     }
          //     // console.log('index')
          //     // console.log(index)

          //     this.listbox.ds_thuebao.list  = temp

          //   }
          //   else
          //   {
          //     this.ShowAlert("Lỗi lấy thông tin danh sách thuê bao!",2);
          //   }
          // }
          // );
        }
      } else {
        this.ShowAlert(this.thongbaoLoi[loai] + ma, 2)
        this.Clear()
      }
    },

    HienThiDanhSachDBTB: async function() {},
    HienThi_LoaiHinh: async function() {},
    HienThiTT_DBKH: async function() {
      var input = {}
      input.ma_tb = this.MaKH
      input.kieu = 3
      input.dichvuvt_id = this.listbox.dichvu.value.DICHVUVT_ID == null || this.listbox.dichvu.value.DICHVUVT_ID == '' ? 0 : this.listbox.dichvu.value.DICHVUVT_ID
      input.loaitb_id = this.listbox.loaihinh.value.LOAITB_ID == null || this.listbox.loaihinh.value.LOAITB_ID == '' ? 0 : this.listbox.loaihinh.value.LOAITB_ID
      var ketqua = []
      this.loading(true)
      await API.sp_lay_ds_khachhang_theo_matb(this.axios, input).then((response) => {
        this.loading(false)
        if (response.data.error_code && response.data.error_code === this.success_code) {
          ketqua = response.data.data
          if (ketqua.length > 0) {
            this.MaKH = ketqua[0].ma_kh
            this.MaKHCu = ketqua[0].makh_cu
            this.TenKH = ketqua[0].ten_kh
            this.DiaChiKH = ketqua[0].diachi_tb
            //this.PLKH = ketqua[0].phanloaikh_id;//todo
            var input1 = {}
            input1.khachhang_id = ketqua[0].khachhang_id
            input1.phanloaikh_id = ketqua[0].phanloaikh_id
            this.loading(true)
            API.sp_lay_tt_db_theo_kh(this.axios, input1).then((response) => {
              this.loading(false)
              if (response.data.error_code && response.data.error_code === this.success_code) {
                this.listbox.ds_bdchuahoanthanh.list = response.data.data.data
                this.TienTT = response.data.data.out_tienttno
                this.ConNo = response.data.data.out_tienno ? this.formatPrice(response.data.data.out_tienno) : '0'
                this.PLKH = response.data.data.out_phanloaikh
              } else {
                this.ShowAlert('Lỗi lấy thông tin nợ khách hàng!', 2)
              }
            })
          }
        } else {
          this.ShowAlert('Không có tìm thấy thông tin danh bạ khách hàng!', 1)
        }
      })
    },

    DichVuChange: async function(vchange) {
      //Lấy loại hình tbao theo dịch vụ
      if (this.listbox.dichvu.value.DICHVUVT_ID == null || this.listbox.dichvu.value.DICHVUVT_ID == '') {
        this.ShowAlert('Bạn cần chọn dịch vụ để lấy thông tin loại hình TB', 1)
        return
      }
      var input = {}
      await API.CSS_LOAIHINH_TB(this.axios, input).then((response) => {
        if (response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.loaihinh_full.list = response.data.data
          this.listbox.loaihinh.list = this.listbox.loaihinh_full.list.filter((x) => x.DICHVUVT_ID == this.listbox.dichvu.value.DICHVUVT_ID).map((x) => ({ id: x.LOAITB_ID, text: x.LOAIHINH_TB }))
          if (!vchange)
            if (this.listbox.loaihinh.list.length > 0) {
              this.listbox.loaihinh.value.LOAITB_ID = this.listbox.loaihinh.list[0].id
            }
        } else {
          this.ShowAlert('Lỗi lấy danh sách loại hình dịch vụ!', 2)
        }
      })
    },
    selectRowsChon(args) {
      console.log('vao day', args)
      if (this.listbox.ds_thuebao.list.length > 0) {
        //this.$refs.ds_thuebao.selectRow(0, true)
      }
    },
    async thamso_md_theo_ma(mats) {
      let output = false
      let data = await this.GetData(await API.thamso_md_theo_ma(this.axios, mats))
      if (data == null || data.length == 0) return
      if (data.length > 0) {
        output = true
      }
      return output
    },
    async gridChanged(obj) {
      console.log('gridChanged')
      console.log(obj)
      if (!obj) {
        return
      }
      this.ClearTB_TT()
      let item = obj
      if (!item || !item.thuebao_id || item.thuebao_id == 0) return

      //Lấy thông tin thuê bao theo thuebao_id
      this.thuebao_id = item.thuebao_id == null ? 0 : item.thuebao_id
      var input = {}
      input.thuebao_id = item.thuebao_id == null ? 0 : item.thuebao_id
      input.thanhtoan_id = item.thanhtoan_id == null ? 0 : item.thanhtoan_id
      this.loading(true)
      var input2 = null

      await API.sp_lay_tt_thuebao_by_id(this.axios, input).then((response) => {
        if (response.data.error_code && response.data.error_code === this.success_code) {
          if (response.data.data.out_tb.length > 0) {
            this.MaTB = response.data.data.out_tb[0].ma_tb
            this.DiaChiTB = response.data.data.out_tb[0].diachi_tb
            this.DiaChiLD = response.data.data.out_tb[0].diachi_ld
            this.listbox.dichvu.value.DICHVUVT_ID = response.data.data.out_tb[0].dichvuvt_id
            this.DichVuChange(true)

            this.listbox.loaihinh.value.LOAITB_ID = response.data.data.out_tb[0].loaitb_id
            this.listbox.doituong.value.id = response.data.data.out_tb[0].doituong_id
            this.TrangThaiTB = response.data.data.out_tb[0].trangthai_tb
            this.MaGD = response.data.data.out_tb[0].ma_gd
            this.lay_ds_nhanvien_dbtb_by_id(this.thuebao_id)
          }
          if (response.data.data.out_tt.length > 0) {
            this.MaTT = response.data.data.out_tt[0].ma_tt
            this.TenTT = response.data.data.out_tt[0].ten_tt
            this.DiaChiTT = response.data.data.out_tt[0].diachi_tt
            this.DiaChiBC = response.data.data.out_tt[0].diachi_bc

            this.listbox.hinhthuctt.value.HTTT_ID = response.data.data.out_tt[0].httt_id
            this.MaDD = response.data.data.out_tt[0].matb_dd
            //Lấy thông tin thanh toán
            input2 = {}
            input2.thanhtoan_id = response.data.data.out_tt[0].thanhtoan_id == null ? 0 : response.data.data.out_tt[0].thanhtoan_id
            input2.ma_ig_id = response.data.data.out_tt[0].mig_id == null ? 0 : response.data.data.out_tt[0].mig_id
          }
          this.KhuVuc = response.data.data.out_khuvuc
        } else {
          this.ShowAlert('Lỗi lấy chi tiết thông tin thuê bao!', 2)
        }
      })

      await API.lay_ds_diachi_theo_dbtbid(this.axios, this.thuebao_id).then((response) => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
          this.dataDiachi = response.data.data[0]
        }
      })

      var input3 = {}
      input3.thuebao_id = this.thuebao_id
      await API_Detail.sp_lay_tt_chitiet_theo_tb_id(this.axios, input3).then((response) => {
        if (response.data.error_code && response.data.error_code === this.success_code && response.data.data && response.data.data.data && response.data.data.data.length > 0) {
          if (response.data.data.dichvuvt_id == 4) {
            this.SoAo = response.data.data.data[0].ma_lt ? response.data.data.data[0].ma_lt : ''
          }
        }
      })

      if (input2)
        await API.sp_lay_tt_db_theo_thanhtoan(this.axios, input2).then((response) => {
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.TienTT_TT = response.data.data.out_tienttno
            this.ConNo_TT = response.data.data.out_tienno ? this.formatPrice(response.data.data.out_tienno) : '0'
            this.MaInGhep = response.data.data.out_main_ghep
          } else {
            this.ShowAlert('Lỗi lấy thông tin thanh toán!', 2)
          }
        })
      //Lấy thông tin khuyến mại
      var input1 = {}
      input1.thuebao_id = item.thuebao_id == null ? 0 : item.thuebao_id
      //todo
      //input1.thuebao_id='1310379'
      //todo todo todo
      input1.is_chk_km = 0
      this.loading(true)
      await API.sp_lay_tt_db_theo_thuebao(this.axios, input1).then((response) => {
        if (response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_khuyenmai.list = response.data.data.out_ds_km
          this.listbox.ds_nhomgoi.list = response.data.data.out_ds_goi_dv
          this.ConNo_Tbao = response.data.data.out_tienno ? this.formatPrice(response.data.data.out_tienno) : '0'
          this.TienTT_Tbao = response.data.data.out_tienttno
          if (response.data.data.out_ds_km.length > 0) this.txtKhuyenMai = response.data.data.out_ds_km[0].ten_km
        } else {
          this.ShowAlert('Lỗi lấy thông tin khuyến mại!', 2)
        }
      })
      this.loading(false)
    },

    //lấy danh sách nhân viên địa bàn theo thuê bao id
    async lay_ds_nhanvien_dbtb_by_id(thuebao_id) {
      try {
        this.dataPopupNVDB = []
        await API.lay_ds_nhanvien_dbtb_by_id(this.axios, {
          thuebaoId: thuebao_id,
          pageNum: 1,
          pageRec: 10,
          totalRow: 1
        }).then((response) => {
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
            this.dataPopupNVDB = response.data.data
          }
        })
      } catch (error) {
        console.log(error)
      }
    },
    //lấy thông tin khuyến mãi khi change checkall
    async sp_lay_tt_db_theo_thuebao(thuebao_id, is_chk_km) {
      try {
        //todo todo todo
        this.loading(true)
        if (this.thuebao_id == '' || this.thuebao_id == 0) return
        var input1 = {}
        input1.thuebao_id = thuebao_id
        input1.is_chk_km = is_chk_km ? 1 : 0
        this.txtKhuyenMai = ''
        this.listbox.ds_khuyenmai.list = []
        await API.sp_lay_tt_db_theo_thuebao(this.axios, input1).then((response) => {
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.ds_khuyenmai.list = response.data.data.out_ds_km
            if (response.data.data.out_ds_km.length > 0) this.txtKhuyenMai = response.data.data.out_ds_km[0].ten_km
          } else {
            this.ShowAlert('Lỗi lấy thông tin khuyến mại!', 2)
          }
          this.loading(false)
        })
      } catch (error) {
        console.log(error)
        this.loading(false)
      }
    },
    btnVatTu() {
      if (this.thuebao_id == '' || this.thuebao_id == 0) {
        this.ShowAlert('Chưa có thuê bao được chọn!', 2)
        return
      }
      //Tham chiếu UR13.1.037
      this.$refs.formVattu.p_so_may = this.MaTB
      this.$refs.formVattu.frmTraCuuVatTuTB(this.thuebao_id)
      //this.$bvModal.show('popup-tracuuvattuthuebao')
      this.$refs.formVattu.dialogOpen()
    },

    btnToaNha() {
      if (this.thuebao_id == '' || this.thuebao_id == 0) {
        this.ShowAlert('Chưa có thuê bao được chọn!', 2)
        return
      }
      //Tham chiếu UR2.8.004 (chức năng của Tân)
      //this.$bvModal.show('pop_toanha')
      this.$refs.formDiachi.initData()
      this.$refs.pop_toanha.show()
    },
    async btnIvan() {
      await API.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
        p_nghiepvu: 'LAYTHONGTIN_DBCNTT',
        p_ds_para: '{"THUEBAO_ID":' + this.thuebao_id + '}'
      }).then((response) => {
        this.loading(false)
        if (response.data.error_code && response.data.error_code === this.success_code) {
          this.dl_db_cntt = response.data.data
        } else {
          this.dl_db_cntt.mst = '0'
        }
        this.loading(false)
      })
      this.dl_db_cntt.loaitb_id = this.listbox.loaihinh.value.LOAITB_ID
      if (this.thuebao_id == '' || this.thuebao_id == 0) {
        this.ShowAlert('Chưa có thuê bao được chọn!', 2)
        return
      }
      if (this.listbox.loaihinh.value.LOAITB_ID == 373) {
        this.$refs.ipop_hddt.frmChuanHoaHDDT_Load()
        this.$refs.pop_hddt.show()
      }
      if (this.listbox.loaihinh.value.LOAITB_ID == 132) {
        this.$refs.ipop_ivan.tracuu()
        this.$refs.pop_ivan.show()
      }

      //if (this.listbox.loaihinh.value.LOAITB_ID!=132) return
      //this.$refs.ipop_ivan.tracuu()
      //this.$refs.pop_ivan.show()
    },

    async btnDongbo(){
      if (this.khachhang_id == '') {
        this.$toast.warning("Không có thông tin khách hàng để đồng bộ")
        return
      }
      this.loading(true)
        let dongbo_dulieu_cskh_req = {}
        dongbo_dulieu_cskh_req.INPUT = this.khachhang_id
        console.log(this.khachhang_id)
        console.log(dongbo_dulieu_cskh_req)
        let kq_dongbo = await API.dongbo_dulieu_cskh(this.axios,{
          p_kieu : 1,
          p_ds_para : dongbo_dulieu_cskh_req
        })
        if(kq_dongbo.data.error_code && kq_dongbo.data.error_code == "BSS-00000000" && kq_dongbo.data.data == "OK"){
          this.$toast.success("Đồng bộ dữ liệu thành công")
        }else{
          this.$toast.error(`${kq_dongbo.data.message}`)
        }
      this.loading(false)
    },
    // btnTest() {
    //   this.$refs.pop_TraCuuPharmacy.show();
    // },
    btnChiTietTB() {
      // if (this.thuebao_id=="" || this.thuebao_id==0) {
      //   this.ShowAlert("Chưa có thuê bao được chọn!",2);
      //   return
      // }
      // this.popupComponent = ()=>import('./components/DetailSub.vue');
      // this.popupComponentName = "DetailSub";
      // this.popupTitle = "Thông tin chi tiết thuê bao hiện tại trên hệ thống ĐHSX"
      // this.popupComponentData = { vma_tb:this.MaTB , vthuebao_id:this.thuebao_id}
      // this.$bvModal.show('popupComponents')

      this.$refs.traCuuThongSoKTModal.showModal()
    },

    btnMaKH_Click() {
      this.popupComponent = () => import('@/modules/search/subscriber/SearchAccount')
      this.popupComponentName = 'SearchMaKH'
      try {
        this.popupComponent.data.isPopup = true
      } catch (ex) {}
      this.$bvModal.show('popupComponents')
    },
    Popup(dulieu) {
      //this.$bvModal.show(modalId);
      this.$refs.popupChonGrid.loadData(dulieu)
      this.$refs.popupComponentejs.show()
    },

    popupClosed(val) {
      this.$bvModal.hide('popupComponents')
      if (this.popupComponentName == 'popupKH') {
        if (val.ma_kh && val.ma_kh != '') {
          this.MaKH = val.ma_kh
          this.onMaKHEnter()
        }
        //if (val.ma_kh) this.MaKH = val.ma_kh;
        // if(val.ma_kh && val.ma_kh !="")
        // {
        //     var ketqua = val;
        //     this.MaKH = ketqua.ma_kh;
        //     this.MaKHCu = ketqua.makh_cu;
        //     this.TenKH = ketqua.ten_kh;
        //     this.DiaChiKH = ketqua.diachi_kh;
        //     //this.PLKH = ketqua.phanloaikh_id;
        //     this.khachhang_id = ketqua.khachhang_id;
        //     //Lấy thông tin biến động chưa hoàn thành
        //     var input1 = {};
        //     input1.khachhang_id = ketqua.khachhang_id;
        //     input1.phanloaikh_id = ketqua.phanloaikh_id;
        //     this.loading(true);
        //     //console.log(input1)
        //     API.sp_lay_tt_db_theo_kh(this.axios, input1).then((response) => {
        //       this.loading(false);
        //       if(response.data.error_code && response.data.error_code === this.success_code) {
        //         //console.log(response.data.data.data);
        //         this.listbox.ds_bdchuahoanthanh.list = response.data.data.data;
        //         this.TienTT = response.data.data.out_tienttno;
        //         this.ConNo = response.data.data.out_tienno;
        //         this.PLKH = response.data.data.out_phanloaikh;
        //       }
        //       else
        //       {
        //         this.ShowAlert("Lỗi lấy thông tin nợ khách hàng!",2);
        //       }
        //     }
        //     );
        //     //Lấy danh sách thuê bao
        //     var input2 = {};
        //     input2.khachhang_id = ketqua.khachhang_id;
        //     this.loading(true);
        //     //console.log(input1)
        //     API.lay_ds_dbtb_theo_khid_v2(this.axios, input2).then((response) => {
        //       this.loading(false);
        //       if(response.data.error_code && response.data.error_code === this.success_code) {
        //         this.listbox.ds_thuebao.list = response.data.data;
        //       }
        //       else
        //       {
        //         this.ShowAlert("Lỗi lấy thông tin nợ khách hàng!",2);
        //       }
        //     }
        //     );
        // }
      } else if (this.popupComponentName == 'SearchMaKH') {
        if (val.ma_kh && val.ma_kh != '') {
          this.MaKH = val.ma_kh
          this.onMaKHEnter()
        }
      }
    },

    async XacNhanToaDo(value) {},
    async btnKhuVuc() {
      if (this.thuebao_id == 0) {
        this.ShowAlert('Chưa chọn thuê bao!')
        return
      }
      this.$bvModal.show('popupNVDB')
    },
    closePopupNVDB() {},
    setFocusRow(ma_tb) {
      //thằng này cũng ko gọi
      let index = this.listbox.ds_thuebao.list.findIndex((x) => x.ma_tb == ma_tb)
      if (index >= 0) {
        this.$refs.ds_thuebao.selectRow(index, true)
        //this.$refs.gridDSThueBao.setCurrentSelectedRow(index);
      } else this.$refs.ds_thuebao.selectRow(0, true) //this.$refs.gridDSThueBao.setCurrentSelectedRow(0);
    },
    async openMapTB(dia_chi, type) {
      let data = {
        lat: null, //vi do
        lng: null //kinh do
      }
      this.map_info = {
        dia_chi: dia_chi,
        title: dia_chi
      }
      if (type == 'DiaChiKH') {
        let toado = await API.sp_lay_tt_kinhdo_vido(this.axios, {
          kieu: 1,
          id: this.khachhang_id
        })
        if (toado.data.data) {
          data.lng = Number(toado.data.data.out_kinhdo) > 0 ? Number(toado.data.data.out_kinhdo) : null
          data.lat = Number(toado.data.data.out_vido) > 0 ? Number(toado.data.data.out_vido) : null
        }
      } else if (type == 'DiaChiTT') {
        let toado = await API.sp_lay_tt_kinhdo_vido(this.axios, {
          kieu: 2,
          id: this.khachhang_id
        })
        if (toado.data.data) {
          data.lng = Number(toado.data.data.out_kinhdo) > 0 ? Number(toado.data.data.out_kinhdo) : null
          data.lat = Number(toado.data.data.out_vido) > 0 ? Number(toado.data.data.out_vido) : null
        }
      } else if (type == 'DiaChiBC') {
        let toado = await API.sp_lay_tt_kinhdo_vido(this.axios, {
          kieu: 5,
          id: this.khachhang_id
        })
        if (toado.data.data) {
          data.lng = Number(toado.data.data.out_kinhdo) > 0 ? Number(toado.data.data.out_kinhdo) : null
          data.lat = Number(toado.data.data.out_vido) > 0 ? Number(toado.data.data.out_vido) : null
        }
      } else if (type == 'DiaChiTB') {
        let toado = await API.sp_lay_tt_kinhdo_vido(this.axios, {
          kieu: 3,
          id: this.khachhang_id
        })
        if (toado.data.data) {
          data.lng = Number(toado.data.data.out_kinhdo) > 0 ? Number(toado.data.data.out_kinhdo) : null
          data.lat = Number(toado.data.data.out_vido) > 0 ? Number(toado.data.data.out_vido) : null
        }
      } else if (type == 'DiaChiLD') {
        let toado = await API.sp_lay_tt_kinhdo_vido(this.axios, {
          kieu: 4,
          id: this.khachhang_id
        })
        if (toado.data.data) {
          data.lng = Number(toado.data.data.out_kinhdo) > 0 ? Number(toado.data.data.out_kinhdo) : null
          data.lat = Number(toado.data.data.out_vido) > 0 ? Number(toado.data.data.out_vido) : null
        }
      }
      this.map = data
      //this.$refs.chonbando.searchLocation(this.map_info.dia_chi);
      this.$bvModal.show('ModalChonToaDo')
    },
    async gridDanhSachTB_pageChanged() {
      if (this.isClick) {
        this.isClick = false
        return
      }

      this.isClick = false
      this.listbox.ds_thuebao.list = []
      try {
        let pIndex = this.$refs.ds_thuebao.$refs.pagination.pageIndexData
        let pSize = this.$refs.ds_thuebao.$refs.pagination.pageSizeData
        //this.ds_thuebao = await this.LAY_DS_DBTB_THANHLY_THEO_DVVT("", Number(this.txtThangcat), this.tttb_id, this.dichvuvt_id, pIndex+1, pSize, 0)
        var input2 = {
          khachhang_id: this.khachhang_id,
          ma_tb: '',
          pageNum: pIndex + 1,
          pageRec: pSize,
          totalRow: 0
        }

        this.loading(true)
        API.lay_ds_dbtb_theo_khid_page(this.axios, input2).then((response) => {
          this.loading(false)
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.listbox.ds_thuebao.list = response.data.data
            //this.totalRecords = response.data.page_info.record_count
            // let temp=response.data.data
            // let index = response.data.data.findIndex(
            //   (item) => item.ma_tb.trim() == this.searchTerm
            // );
            // if (index!=-1){
            //   let row = response.data.data[index]
            //   temp.splice(index, 1)
            //   temp.unshift(row)
            // }
            // // console.log('index')
            // // console.log(index)

            // this.listbox.ds_thuebao.list  = temp
          }
        })
      } catch (exp) {
        this.$toast.error(exp)
      }
    },
    async Clear() {
      this.MaKH = ''
      this.MaKHCu = ''
      this.TenKH = ''
      this.PLKH = ''
      this.DiaChiKH = ''
      this.TienTT = '0'
      this.ConNo = '0'
      this.khachhang_id = ''
      this.thuebao_id = ''

      this.chkAll_KM = false
      this.txtKhuyenMai = ''

      this.MaTB = ''
      this.SoAo = ''
      this.DiaChiTB = ''
      this.DiaChiLD = ''
      this.TrangThaiTB = ''
      this.KhuVuc = ''
      this.TienTT_Tbao = '0'
      this.ConNo_Tbao = '0'

      this.MaGD = ''
      this.MaTT = ''
      this.MaDD = ''
      this.TenTT = ''
      this.DiaChiTT = ''
      this.DiaChiBC = ''
      this.MaInGhep = ''
      this.TienTT_TT = '0'
      this.ConNo_TT = '0'

      this.listbox.ds_nhomgoi.list = []
      this.listbox.ds_bdchuahoanthanh.list = []
      this.listbox.ds_khuyenmai.list = []
      this.listbox.ds_thuebao.list = [] //có gọi chỗ này thôi, nhưng a comment nó cũng bị
      this.popupDetailSubData = []
      this.dataPopupNVDB = []
    },
    async ClearTB_TT() {
      this.thuebao_id = 0

      this.chkAll_KM = false
      this.txtKhuyenMai = ''

      this.MaTB = ''
      this.SoAo = ''
      this.DiaChiTB = ''
      this.DiaChiLD = ''
      this.TrangThaiTB = ''
      this.KhuVuc = ''
      this.TienTT_Tbao = '0'
      this.ConNo_Tbao = '0'

      this.MaGD = ''
      this.MaTT = ''
      this.MaDD = ''
      this.TenTT = ''
      this.DiaChiTT = ''
      this.DiaChiBC = ''
      this.MaInGhep = ''
      this.TienTT_TT = '0'
      this.ConNo_TT = '0'

      this.listbox.ds_nhomgoi.list = []
      this.listbox.ds_khuyenmai.list = []
      this.dataPopupNVDB = []
      this.dataDiachi = {}
    },
	commandClicked: async function (cmdName, data) {
      console.log('commandClicked1', 'cmdName:', cmdName, 'data:', data)      
      if (cmdName == 'thaotac'){
		if (data.link_qr)
			window.open(data.link_qr,"", "width=600,height=600")
		else 
			this.$toast.error("Không có link QRCODE")
      }    
      return false
    },
  }
}
</script>
