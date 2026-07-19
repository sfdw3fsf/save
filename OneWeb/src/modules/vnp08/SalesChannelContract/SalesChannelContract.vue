<template src="./SalesChannelContract.html"></template>

<script>
import {
  required,
  maxLength,
  minLength,
  numeric
} from "vuelidate/lib/validators"
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import breadcrumb from '@/components/breadcrumb'
import api from "./API.js";

const ModalChonDiaChi = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi')
const HosoFile = () => import('@/modules/vnp08/ProposedSalesChannel/popup/HosoFile/HosoFile.vue')
const Lichsuphieugiao  = () => import("@/modules/vnp08/ApproveSalesChannel_LDB/popup/Lichsuphieugiao/Lichsuphieugiao.vue")

export default {
  name: 'SalesChannelContract',
  components: {
    breadcrumb,
    DatePicker,
    BssErrorMarker,
    BssRequiredMarker,
    ModalChonDiaChi,
    HosoFile,
    Lichsuphieugiao
  },
  data() {
    return {
      dateFormat: 'DD/MM/YYYY',
      header: {
        title: "Hợp đồng ký với kênh bán",
        list: [
          {
            name: "Hợp đồng ký với kênh bán",
            link: {
              name: "Ui.buttons"
            },
          },
        ],
      },
      btn_Enabled: {
        nhapMoi: false,
        ghiLai: false,
        xoa: false,
        huy: false,
      },
      // popup
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
      },
      popupComponentName: '',
      popupComponentHeader: '',
      // 
      expanded:{
        hop_dong:true,
        tt_bosung: true,
        tt_hopdong_kenh: true,
        ds_hopdong: true
      },
      // TT phieu
      tt_nd: {},
      loai: 1, // 0: khong qua de xuat, 1: qua de xuat
      hangkenhCTV: false,
      cbb_MaPhieu: { value: '', Items: [], Enabled: false },
      cbb_Doituong: { value: 0, Items: [], Enabled: false },
      cbb_Hangkenh: { value: 0, Items: [], Enabled: false },
      cbb_DVDexuat: { value: 0, Items: [], Enabled: false },
      cbb_NguoiDeXuat: { value: 0, Items: [], Enabled: false },
      txt_MaDaiLy: { value: '', Enabled: false },
      txt_TenDaiLy: { value: '', Enabled: false },
      diachiKB: {
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
      txt_DiaChi: { value: '', Enabled: false },
      txt_DienThoai: { value: '', Enabled: false },
      txt_Fax: { value: '', Enabled: false },
      txt_MST_CCCD: { value: '', Enabled: false },
      txt_NguoiDD: { value: '', Enabled: false },
      txt_ChucDanh: { value: '', Enabled: false },
      txt_MaCTV: { value: '', Enabled: false },
      txt_TenCTV: { value: '', Enabled: false },
      dtp_NgaySinh: { value: '', Enabled: false },
      cbb_GioiTinh: {
        value: 0, Items: [
          { id: -1, text: 'Khác' },
          { id: 0, text: 'Nam' },
          { id: 1, text: 'Nữ' }], Enabled: false
      },
      cbb_LoaiGT: { value: 0, Items: [], Enabled: false },
      txt_SoGT: { value: '', Enabled: false },
      dtp_NgayCap: { value: '', Enabled: false },
      txt_NoiCap: { value: '', Enabled: false },
      // TT Phe duyet
      cbb_DVPheDuyet: { value: 0, Items: [], Enabled: false },
      dtp_NgayPheDuyet: { value: '', Enabled: false },
      txt_LanhDaoDuyet: { value: '', Enabled: false },
      txt_YKienLD: { value: '', Enabled: false },
      // TT hop dong
      txt_SoHDPL: { value: '', Enabled: true },
      dtp_NgayKy: { value: '', Enabled: true },
      txt_NguoiKy: { value: '', Items: [], Enabled: true },
      cbb_LoaiHD: { value: 0, Items: [] },
      cbb_LoaiThuLao: { value: 0, Items: [] },
      txt_TongTyLeThuLao: { value: 0, Enabled: true},
      cbb_TanSuatHuong: { value: 0, Items: [], Enabled: true },
      txt_GhiChu: { value: '', Enabled: true },
      // Table TT hop dong
      cbb_DichVuVT: { Items: [] },
      cbb_LoaiHinhTB: { Items: [] },
      cbb_CongDoan: { Items: [] },
      // TT them ve kenh ban
      txt_Username: { value: '', Enabled: true },
      cbb_TVKenh: { value: 0, Items: [], Enabled: true },
      cbb_NVQL: { value: 0, Items: [], Enabled: true },
      cbb_TrangThai: { value: 0, Items: [], Enabled: true },
      hdkenh_id: 0,
      tvkenh_id: 0,
      // TT thanh toan
      txt_SoTK: { value: '', Enabled: true },
      txt_ChuTK: { value: '', Enabled: true },
      cbb_NganHang: { value: 0, Items: [], Enabled: true },
      cbb_HTTT: { value: 0, Items: [], Enabled: true },
      cbb_DVQL_TT: { value: 0, Items: [], Enabled: true },
      cbb_NVQL_TT: { value: 0, Items: [], Enabled: true },
      txt_MST: { value: '', Enabled: true },
      txt_Vi_VNPT: { value: '', Enabled: true },
      txt_GhiChu_TT: { value: '', Enabled: true },
      // table
      grid_CoChe: {
        header: [
          { fieldName: 'hdkcc_id', headerText: 'ID', allowFiltering: true, allowSorting: false, width: 90 },
          {
            fieldName: 'dichvu_id', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <select2 :z-index="2000" class="select2" :ref="'cboDV_' + data.key" :id="'cboDV_' + data.key" @change="onChangeDichVu" v-model="data.dichvu_id"
												:options="cp_dichvu.Items.map(e=>({id:e.dichvuvt_id,text:e.ten_dvvt}))">
											</select2>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                    cp_dichvu() {
                      return this.$parent.$parent.$parent.cbb_DichVuVT
                    }
                  },
                  methods: {
                    onChangeDichVu(value) {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                      if (index > -1) {
                        this.$parent.$parent.$parent.grid_CoChe.list[index]['dichvu_id'] = Number(value)
                        var ds_loaihinh = this.$parent.$parent.$parent.cbb_LoaiHinhTB.Items
                        this.$parent.$parent.$parent.grid_CoChe.list[index]['ds_loaihinh'] = ds_loaihinh.filter(e => e.dichvuvt_id == Number(value))
                      }
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: 'loaihinh_id', headerText: 'Loại hình', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div :ref="'div_cboLH_' + data.key" :id="'div_cboLH_' + data.key">
                      <select2 :z-index="2000" class="select2" :ref="'cboLH_' + data.key" :id="'cboLH_' + data.key" @change="onChangeLoaiTB" v-model="data.loaihinh_id"
												:options="cp_loaihinh && cp_loaihinh.length > 0 ? cp_loaihinh.map(e=>({id:e.loaitb_id,text:e.loaihinh_tb})) : []">
											</select2>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                    cp_loaihinh() {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                      if (index > -1) {
                        return this.$parent.$parent.$parent.grid_CoChe.list[index]['ds_loaihinh']
                      }
                    }
                  },
                  mounted() {
                  },
                  methods: {
                    onChangeLoaiTB(value) {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                      if (index > -1) {
                        this.$parent.$parent.$parent.grid_CoChe.list[index]['loaihinh_id'] = Number(value)
                        var ds_congdoan = this.$parent.$parent.$parent.cbb_CongDoan.Items
                        this.$parent.$parent.$parent.grid_CoChe.list[index]['ds_congdoan'] = ds_congdoan.filter(e => e.loaitb_id == Number(value))
                      }
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: 'congdoan_id', headerText: 'Công đoạn', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <select2 :z-index="2000" class="select2" @change="onChangeCongDoan" v-model="data.congdoan_id"
												:options="cp_congdoan && cp_congdoan.length > 0 ? cp_congdoan.map(e=>({id:e.congdoan_id,text:e.ten_congdoan})) : []">
											</select2>
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                    cp_congdoan() {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                      if (index > -1) {
                        return this.$parent.$parent.$parent.grid_CoChe.list[index]['ds_congdoan']
                      }
                    }
                  },
                  methods: {
                    onChangeCongDoan(value) {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                      if (index > -1) this.$parent.$parent.$parent.grid_CoChe.list[index]['congdoan_id'] = Number(value)
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input type="text" class="form-control text" :value="data.noidung" @change="onChangeNoiDung">
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                  },
                  methods: {
                    onChangeNoiDung(e) {
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                      if (index > -1) this.$parent.$parent.$parent.grid_CoChe.list[index]['noidung'] = e.target.value
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: 'tyle', headerText: 'Tỷ lệ % thù lao', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input type="text" class="form-control tright" :value="data.tyle" @keypress="isNumber($event)" @change="onChangeTyle">
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                  },
                  methods: {
                    onChangeTyle(e) {
                      if (e.target.value && Number(e.target.value) > 100) {
                        this.$toast.error('Tỷ lệ không được > 100')
                      }
                      var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                      if (index > -1) this.$parent.$parent.$parent.grid_CoChe.list[index]['tyle'] = Number(e.target.value)
                    },
                    isNumber(evt) {
                      evt = evt ? evt : window.event;
                      var charCode = evt.which ? evt.which : evt.keyCode;
                      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                        evt.preventDefault();
                      } else {
                        return true;
                      }
                    },
                  },
                },
              };
            },
          },
          {
            fieldName: 'sotien', headerText: 'Số tiền được hưởng', allowFiltering: true, allowSorting: false,
            template: () => {
              return {
                template: {
                  template: `<div>
                      <input
                        type="text"
                        v-model="cp_tien"
                        @keypress="isNumber($event)"
                        class="form-control tright"
                      />
                    </div>`,
                  data: function () {
                    return {
                      data: {},
                    };
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent;
                    },
                    cp_tien: {
                      get() {
                        return this.data.sotien ? this.numberWithCommas(this.data.sotien) : 0;
                      },
                      set(value) {
                        var replaced = value.replaceAll(",", "");
                        this.data.sotien = Number(replaced);
                        var index = this.$parent.$parent.$parent.grid_CoChe.list.findIndex(e => e.key == this.data.key)
                        if (index > -1) this.$parent.$parent.$parent.grid_CoChe.list[index]['sotien'] = Number(replaced)
                      },
                    },
                  },
                  methods: {
                    isNumber(evt) {
                      evt = evt ? evt : window.event;
                      var charCode = evt.which ? evt.which : evt.keyCode;
                      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                        evt.preventDefault();
                      } else {
                        return true;
                      }
                    },
                    numberWithCommas(n) {
                      var parts = n.toString().split(".");
                      return (
                        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
                        (parts[1] ? "." + parts[1] : "")
                      );
                    },
                  },
                },
              };
            },
          },
        ],
        commands: [
          { name: 'save', cssClass: 'pad3 mar5 lh14 icon -ap one-save btn-primary btn-search', iconCss: 'fa fa-times', width: 100 },
          { name: 'delete', cssClass: 'pad3 mar5 lh14 icon -ap icon-delete btn-danger btn-plus', iconCss: 'fa fa-times', width: 100 },
        ],
        list: [],
        selected: {},
        selectedItems: [],
      },
      grid_HopDong: {
        selectionSettings: { type: 'Single' },
        header: [
          { fieldName: 'ma_yc', headerText: 'Mã yêu cầu', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'ma_kenh', headerText: 'Mã kênh', allowFiltering: true, allowSorting: false, width: 150 },
          { fieldName: 'ten_kenh', headerText: 'Tên kênh', allowFiltering: true, allowSorting: false, width: 150 },
          { fieldName: 'mst_cccd', headerText: 'Mã số thuế', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'diachi', headerText: 'Địa chỉ', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'trang_thai_hopdong', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, width: 110 },
        ],
        list: [],
        selected: {},
        selectedItems: [],
      }
    }
  },
  validations: {
    diachiKB: {
      DIACHI: {
        required
      }
    },
    dtp_NgayPheDuyet: {
      value: {
        required
      }
    }
  },
  async created() {
    this.tt_nd = await this.$root.token.getThongTinNguoiDung()
    console.log('tt_nd',this.tt_nd)
    this.setButton(-1);
  },
  mounted: async function () {
    await this.Load_Form()
  },
  watch: {
    'cbb_LoaiThuLao.value'(val) {
      if (val == 1 || val == '1') {
        // Thù lao tổng
        this.txt_TongTyLeThuLao.Enabled = true
      } else if (val == 2 || val == '2') {
        // Thù lao theo công đoạn
        this.txt_TongTyLeThuLao.Enabled = false
      }
    },
    'grid_CoChe.list'(val) {
      if (this.cbb_LoaiThuLao.value == 2 || this.cbb_LoaiThuLao.value == '2') {
        if (val && val.length > 0) {
          this.txt_TongTyLeThuLao.value = 0
          for (let item of val) {
            this.txt_TongTyLeThuLao.value += Number(item.tyle)
          }
        } else {
          this.txt_TongTyLeThuLao.value = 0
        }     
      }
    }
  },
  methods: {
    Load_Form: async function () {
      try {
        this.loading(true)
        this.khoamo_form(this.loai)
        await Promise.all([
          this.getDanhsach_MaPhieu(),
          this.getDanhsach_Doituongban(),
          this.getDanhsach_DVDexuat(),
          this.getDanhsach_LoaiGT(),
          // this.getDanhsach_NguoiKy(),
          this.getDanhsach_LoaiHD(),
          this.getDanhsach_LoaiThuLao(),
          this.getDanhsach_NganHang(),
          this.getDanhsach_HTTT(),
          this.getDanhsach_DonViQLTT(),          
          this.getDanhsach_TanXuatHuong(),
          this.getDanhsach_ThanhVienKenh(),
          this.getDanhsach_NhanVienQL(),
          this.getDanhsach_TrangThai(),
          this.getDanhsach_DichVuVT(),
          this.getDanhsach_LoaiHinhTB(),
          this.getDanhsach_CongDoan()
        ])

        await this.getDanhsach_NguoiDexuat()        
        this.cbb_NguoiDeXuat.value = this.cbb_NguoiDeXuat.Items && this.cbb_NguoiDeXuat.Items.length > 0 ? this.cbb_NguoiDeXuat.Items[0]['nhanvien_id'] : 0

        await this.getDanhsach_NhanVienChiTra()
        this.cbb_NVQL_TT.value = this.cbb_NVQL_TT.Items && this.cbb_NVQL_TT.Items.length > 0 ? this.cbb_NVQL_TT.Items[0]['nhanvien_id'] : 0

        await this.getDanhsach_HopDong()        
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    // Lấy danh sách Mã phiếu
    getDanhsach_MaPhieu: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsMaPhieu(this.axios, input));
      this.cbb_MaPhieu.Items = data;
    },
    onSelected_MaPhieu: async function (value) {
      try {
        this.loading(true)
        if (value) {
          this.clear()
          var phieu = this.cbb_MaPhieu.Items.filter(e => e.ma_yc == value)[0]
          await this.HienThi_ThongTin_HopDong(phieu)
        }
      } catch (e) {} finally { this.loading(false) }
    },
    // Lấy danh sách đối tượng bán
    getDanhsach_Doituongban: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsDoiTuongBan(this.axios, input));
      this.cbb_Doituong.Items = data;
    },
    //Lấy danh sách hạng kênh
    getDanhsach_Hangkenh: async function () {
      let input = { hdkenh_id: this.hdkenh_id }
      let data = this.GetDataList(await api.dsHangKenh(this.axios, input));
      this.cbb_Hangkenh.Items = data;
    },
    //Lấy danh sách đơn vị đề xuất
    getDanhsach_DVDexuat: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsDonViDeXuat(this.axios, input));
      this.cbb_DVDexuat.Items = data;
      this.cbb_DVPheDuyet.Items = data;
    },
    onChangeDVDexuat: function () {
      this.getDanhsach_NguoiDexuat()
    },
    //Lấy danh sách người đề xuất
    getDanhsach_NguoiDexuat: async function () {
      let input = { "donvi_id": this.cbb_DVDexuat.value }
      let data = this.GetDataList(await api.dsNhanVienDeXuat(this.axios, input));
      this.cbb_NguoiDeXuat.Items = data
    },
    getDanhsach_LoaiGT: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsLoaiGT(this.axios, input));
      this.cbb_LoaiGT.Items = data
    },
    //Lấy danh sách người ký
    getDanhsach_NguoiKy: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsNguoiKy(this.axios, input));
      this.txt_NguoiKy.Items = data
    },
    //Lấy danh sách loại hợp đồng
    getDanhsach_LoaiHD: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsLoaiHopDong(this.axios, input));
      this.cbb_LoaiHD.Items = data
    },
    //Lấy danh sách loại thù lao
    getDanhsach_LoaiThuLao: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsLoaiThuLao(this.axios, input));
      this.cbb_LoaiThuLao.Items = data;
    },  
    onChangeLoaiThuLao: async function () {
      try {
        this.loading(true)
        this.txt_TongTyLeThuLao.value = 0
        await Promise.all([
          this.getDanhsach_DichVuVT(),
          this.getDanhsach_LoaiHinhTB(),
          this.getDanhsach_CongDoan(),
        ])
        await this.getDanhsach_CoChe()   
      } catch (e) {
        
      } finally {
        this.loading(false)
      }      
    },
    // Lấy danh sách ngân hàng
    getDanhsach_NganHang: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsNganHang(this.axios, input));
      this.cbb_NganHang.Items = data;
    },
    // Lấy danh sách hình thức TT
    getDanhsach_HTTT: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsHinhThucThanhToan(this.axios, input));
      this.cbb_HTTT.Items = data;
    },
    // Lấy danh sách DVQL TT
    getDanhsach_DonViQLTT: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsDonViQL(this.axios, input));
      this.cbb_DVQL_TT.Items = data;
    },
    onChangeDVQLTT: function () {
      this.getDanhsach_NhanVienChiTra()
    },
    // Lấy danh sách nhân viên chi trả
    getDanhsach_NhanVienChiTra: async function () {
      let input = { donvi_id: this.cbb_DVQL_TT.value}
      let data = this.GetDataList(await api.dsNhanVienChiTra(this.axios, input));
      this.cbb_NVQL_TT.Items = data;
    },
    //Lấy danh sách tần suất được hưởng
    getDanhsach_TanXuatHuong: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsTanXuatHuong(this.axios, input));
      this.cbb_TanSuatHuong.Items = data;
    },
    // Lấy danh sách thành viên kênh
    getDanhsach_ThanhVienKenh: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsThanhVienKenh(this.axios, input));
      this.cbb_TVKenh.Items = data;
    },
    // Lấy danh sách nhân viên quản lý
    getDanhsach_NhanVienQL: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsNhanVienQL(this.axios, input));
      this.cbb_NVQL.Items = data;
    },
    onChange_NhanVienQL: async function () {
      try {
        this.loading(true)
        await Promise.all([
          this.getDanhsach_DichVuVT(),
          this.getDanhsach_LoaiHinhTB(),
          this.getDanhsach_CongDoan(),   
        ])
        await this.getDanhsach_CoChe()   
      } catch (e) {
        
      } finally {
        this.loading(false)
      }      
    },
    //Lấy danh sách File hồ sơ
    getDanhsach_FileKenhBan: async function () {
      // this.grid_HoSo.list = []
      // let input = {
      //   hdkenh_id: this.hdkenh_id
      // }
      // let data = this.GetDataList(await api.dsFileHDK(this.axios, input));
      // this.grid_HoSo.list = data;
    },
    // Lấy danh sách trạng thái
    getDanhsach_TrangThai: async function () {
      let input = {
        doituongban: this.cbb_Doituong.value
      }
      let data = this.GetDataList(await api.dsTrangThai(this.axios, input));
      this.cbb_TrangThai.Items = data;
    },
    // Lấy danh sách dịch vụ
    getDanhsach_DichVuVT: async function () {
      let input = {
        "hdkenh_id": this.hdkenh_id,
        "dt_ban_id": this.cbb_Doituong.value,
        "loaithulao_id": this.cbb_LoaiThuLao.value,
        "nvql_id": this.cbb_NVQL.value
      }
      let data = this.GetDataList(await api.dsDichVuVT(this.axios, input));
      this.cbb_DichVuVT.Items = data;
    },
    // Lấy danh sách loại hình tb
    getDanhsach_LoaiHinhTB: async function () {
      let input = {
        "hdkenh_id": this.hdkenh_id,
        "dt_ban_id": this.cbb_Doituong.value,
        "loaithulao_id": this.cbb_LoaiThuLao.value,
        "nvql_id": this.cbb_NVQL.value,
      }
      let data = this.GetDataList(await api.dsLoaiHinhTB(this.axios, input));
      this.cbb_LoaiHinhTB.Items = data;
    },
    // Lấy danh sách công đoạn
    getDanhsach_CongDoan: async function () {
      let input = {
        "hdkenh_id": this.hdkenh_id,
        "dt_ban_id": this.cbb_Doituong.value,
        "loaithulao_id": this.cbb_LoaiThuLao.value,
        "nvql_id": this.cbb_NVQL.value,
      }
      let data = this.GetDataList(await api.dsCongDoan(this.axios, input));
      this.cbb_CongDoan.Items = data;
    },
    // Lấy danh sách cơ chế của hợp đồng
    getDanhsach_CoChe: async function () {
      this.grid_CoChe.list = []
      let input = {
        hdkenh_id: this.hdkenh_id
      }
      let data = this.GetDataList(await api.dsCoChe(this.axios, input));
      if (data && data.length > 0) {
        for (let item of data) {
          item.key = Math.random().toString(36).slice(2, 7)
          item.ds_loaihinh = this.cbb_LoaiHinhTB.Items.filter(e => e.dichvuvt_id == item.dichvu_id)
          item.ds_congdoan = this.cbb_CongDoan.Items.filter(e => e.loaitb_id == item.loaihinh_id)
        }
      }
      this.grid_CoChe.list = data
      console.log('getDanhsach_CoChe',this.grid_CoChe.list)
    },
    btnTTCoChe_Click: function () {
      try {
        // Nhấn TT Cơ chế
        this.loading(true)
        // this.$toast.info('Chức năng đang được xây dựng!')
        this.popupComponentData = {
          isPopup: true
        }
        this.popupComponent = () => import('./popups/popup_TTCoChe')
        this.popupComponentName = 'popup_TTCoChe'
        this.popupComponentHeader = 'Cơ chế của kênh bán'
        this.Popup('popupComponents')
      } catch (e) {
        this.$toast.error('Lỗi: ' + e)
      } finally {
        this.loading(false)
      }
    },
    btnChiTiet_Click: function () {
      try {
        // Nhấn TT Chi tiết HĐ kênh bán
        this.loading(true)
        // this.$toast.info('Chức năng đang được xây dựng!')
        this.popupComponentData = {
          isPopup: true
        }
        this.popupComponent = () => import('./popups/popup_TTHopDongKenh')
        this.popupComponentName = 'popup_TTHopDongKenh'
        this.popupComponentHeader = 'Thông tin Hợp đồng kênh bán'
        this.Popup('popupComponents')
      } catch (e) {
        this.$toast.error('Lỗi: ' + e)
      } finally {
        this.loading(false)
      }
    },
    btnReload_Click: function () {
      this.getDanhsach_CoChe()
    },
    btnThemCoChe_Click: async function () {
      var row = {
        tvkenh_id: this.tvkenh_id,
        hdkenh_id: this.hdkenh_id,
        dichvu_id: 0,
        loaihinh_id: 0,
        congdoan_id: 0,
        noidung: '',
        sotien: 0,
        tyle: 0,
        tong_thulao: this.txt_TongTyLeThuLao.value,
        dt_ban_id: this.cbb_Doituong.value,
        loaithulao_id: this.cbb_LoaiThuLao.value,
        nvql_id: this.cbb_NVQL.value,
        hdkcc_id: 0,
        key: Math.random().toString(36).slice(2, 7),
        ds_loaihinh: [],
        ds_congdoan: [],
      }
      var ds = [row]
      ds = ds.concat(this.grid_CoChe.list)
      this.grid_CoChe.list = ds
      console.log('btnThemCoChe_Click', ds)
    },
    // Button tren grid co che hop dong
    gridCommand_CoChe: async function (type, dataItem) {
      try {
        this.loading(true)
        if (type == 'save') {
          let input = {
            "tvkenh_id": this.tvkenh_id,
            "hdkenh_id": this.hdkenh_id,
            "noidung": dataItem.noidung,
            "congdoan_id": dataItem.congdoan_id,
            "dichvu_id": dataItem.dichvu_id,
            "loaihinh_id": dataItem.loaihinh_id,
            "tyle": dataItem.tyle,
            "sotien": dataItem.sotien,
            "tong_thulao": this.txt_TongTyLeThuLao.value,
            "dt_ban_id": this.cbb_Doituong.value,
            "loaithulao_id": this.cbb_LoaiThuLao.value,
            "nvql_id": this.cbb_NVQL.value,
            "hdkcc_id": dataItem.hdkcc_id,
          }
          var check_coche = this.GetData(await api.kiemTraDauVaoCoChe(this.axios, input));
          if (!check_coche) {
            this.$toast.error('Có lỗi kiểm tra cơ chế!')
            return
          }
          if (check_coche.codeError == 200 || check_coche.codeError == '200') {
            input.hdk_id_update = check_coche.hdk_id_update
            let data = ''
            if (!dataItem.hdkcc_id) {
              data = this.GetData(await api.themMoiCoChe(this.axios, input));
            } else {
              data = this.GetData(await api.capNhatCoChe(this.axios, input));
            }

            if (data == "OK" || data == "ok") {
              this.$toast.success('Ghi lại cơ chế thành công!');
              this.getDanhsach_CoChe()
            }
          } else if (check_coche.codeError == 300 || check_coche.codeError == '300') {
            this.$bvModal.msgBoxConfirm(check_coche.message, {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Không đồng ý',
            }).then(async v => {
              if (v) {       
                input.hdk_id_update = check_coche.hdk_id_update     
                let data = ''
                if (!dataItem.hdkcc_id) {
                  data = this.GetData(await api.themMoiCoChe(this.axios, input));
                } else {
                  data = this.GetData(await api.capNhatCoChe(this.axios, input));
                }

                if (data == "OK" || data == "ok") {
                  this.$toast.success('Ghi lại cơ chế thành công!');
                  this.getDanhsach_CoChe()
                }
              }
            });
          } else {
            this.$toast.error(check_coche.message)
          }        
        } else if (type == 'delete') {
          this.$bvModal.msgBoxConfirm("Bạn có muốn xóa cơ chế đang chọn không?", {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Không đồng ý',
          }).then(async v => {
            if (v) {
              let input = {
                hdkcc_id: dataItem.hdkcc_id
              }
              let data = this.GetData(await api.xoaCoChe(this.axios, input));
              if (data == "OK" || data == "ok") {
                this.$toast.success('Xóa cơ chế thành công!');
                this.getDanhsach_CoChe()
              }
            }
          });
        }
      } catch (e) {} finally {
        this.loading(false)
      }
    },
    //Lấy danh sách hợp đồng đã ký
    getDanhsach_HopDong: async function () {
      let input = {}
      let data = this.GetDataList(await api.dsHopDongKenh(this.axios, input));
      this.grid_HopDong.list = data
      if (this.grid_HopDong.list.length == 0) {
        this.setButton(1)
      }
    },
    // click row table
    //Click chọn hopdong
    onHopDongSelected: async function (args) {
      try {
        this.loading(true)
        if (args && args.data) {
          this.grid_HopDong.selected = args.data;
          this.setButton(2);
          if (this.cbb_Hangkenh.value === 1006) {
            this.hangkenhCTV = true;

          } else {
            this.hangkenhCTV = false;
          }
          await this.HienThi_ThongTin_HopDong(args.data)
        }
      } catch (e) {} finally { this.loading(false) }
    },
    HienThi_ThongTin_HopDong: async function (data) {
      try {
        console.log('HienThi_ThongTin_HopDong',data)
        this.loai = 1
        this.onChangeLoai()
        
        this.hdkenh_id = data['hdkenh_id']
        this.tvkenh_id = data['tvkenh_id']

        // Thông tin đề xuất ký hợp đồng
        this.cbb_MaPhieu.value = data['ma_yc'] ? data['ma_yc'] : ''
        this.cbb_Doituong.value = data['dt_ban_id'] ? data['dt_ban_id'] : 0
        this.cbb_Hangkenh.value = data['hangkenh_id'] ? data['hangkenh_id'] : 0

        this.diachiKB.DIACHI = data['diachi'] ? data['diachi'] : ''
        this.diachiKB.DIACHI_ID = data['diachi_id'] ? data['diachi_id'] : 0
        this.diachiKB.TINH_ID = data['tinh_id'] ? data['tinh_id'] : 0
        this.diachiKB.QUAN_ID = data['quan_id'] ? data['quan_id'] : 0
        this.diachiKB.PHO_ID = data['pho_id'] ? data['pho_id'] : 0
        this.diachiKB.AP_ID = data['ap_id'] ? data['ap_id'] : 0
        this.diachiKB.PHUONG_ID = data['phuong_id'] ? data['phuong_id'] : 0
        this.diachiKB.KHU_ID = data['khu_id'] ? data['khu_id'] : 0
        this.diachiKB.SONHA = data['sonha'] ? data['sonha'] : ''

        this.cbb_DVDexuat.value = data['donvi_id'] ? data['donvi_id'] : 0
        this.cbb_NguoiDeXuat.value = data['nhanvien_id'] ? data['nhanvien_id'] : 0
        this.txt_DienThoai.value = data['so_dt'] ? data['so_dt'] : ''
        this.txt_MST_CCCD.value = data['mst_cccd'] ? data['mst_cccd'] : ''

        this.cbb_LoaiGT.value = data['loaigt_id'] ? data['loaigt_id'] : ''
        this.txt_SoGT.value = data['so_gt'] ? data['so_gt'] : ''
        this.dtp_NgayCap.value = data['ngay_cap'] ? data['ngay_cap'] : ''
        this.txt_NoiCap.value = data['noicap'] ? data['noicap'] : ''

        if (data['hangkenh_id']) {
          if (data['hangkenh_id'] == 1006) {
            this.txt_MaCTV.value = data['ma_kenh'] ? data['ma_kenh'] : ''
            this.txt_TenCTV.value = data['ten_kenh'] ? data['ten_kenh'] : ''
            this.dtp_NgaySinh.value = data['ngaysinh'] ? data['ngaysinh'] : ''
            this.cbb_GioiTinh.value = data['gioitinh'] ? data['gioitinh'] : 0
          } else {
            this.txt_MaDaiLy.value = data['ma_kenh'] ? data['ma_kenh'] : ''
            this.txt_TenDaiLy.value = data['ten_kenh'] ? data['ten_kenh'] : ''
            this.txt_Fax.value = data['fax'] ? data['fax'] : ''
            this.txt_NguoiDD.value = data['nguoi_dd'] ? data['nguoi_dd'] : ''
            this.txt_ChucDanh.value = data['chucdanh'] ? data['chucdanh'] : ''
          }
        }

        // Thông tin phê duyệt đề xuất
        this.cbb_DVPheDuyet.value = data['donvi_th_id'] ? data['donvi_th_id'] : 0
        this.dtp_NgayPheDuyet.value = data['ngayduyet'] ? data['ngayduyet'] : ''
        this.txt_LanhDaoDuyet.value = data['nguoi_duyet'] ? data['nguoi_duyet'] : ''
        this.txt_YKienLD.value = data['noidung_duyet'] ? data['noidung_duyet'] : ''

        // Thông tin thêm về kênh bán
        this.txt_Username.value = data['ma_nd'] ? data['ma_nd'] : ''
        this.cbb_TVKenh.value = data['thuoc_tvkenh_id'] ? data['thuoc_tvkenh_id'] : 0
        this.cbb_NVQL.value = data['nvql_id'] ? data['nvql_id'] : 0
        this.cbb_TrangThai.value = data['flag'] ? data['flag'] : 0

        // Thông tin hợp đồng ký với kênh bán
        this.txt_SoHDPL.value = data['ma_hd'] ? data['ma_hd'] : ''
        this.dtp_NgayKy.value = data['ngay_ky'] ? data['ngay_ky'] : ''
        this.txt_NguoiKy.value = data['nguoiky'] ? data['nguoiky'] : ``
        this.cbb_LoaiHD.value = data['loaihd_id'] ? data['loaihd_id'] : 0
        this.cbb_LoaiThuLao.value = data['loaithulao_id'] ? data['loaithulao_id'] : 0
        this.txt_TongTyLeThuLao.value = data['tong_thulao'] ? data['tong_thulao'] : 0
        this.cbb_TanSuatHuong.value = data['htttk_id'] ? data['htttk_id'] : 0
        this.txt_GhiChu.value = data['ghichu'] ? data['ghichu'] : ''

        // TT thanh toan
        this.txt_SoTK.value = data['stk'] ? data['stk'] : ''
        this.txt_ChuTK.value = data['chutaikhoan'] ? data['chutaikhoan'] : ''
        this.cbb_NganHang.value = data['nganhang_id'] ? data['nganhang_id'] : 0
        this.cbb_HTTT.value = data['httt_id'] ? data['httt_id'] : 0
        this.cbb_DVQL_TT.value = data['donvi_qltt'] ? data['donvi_qltt'] : 0
        this.cbb_NVQL_TT.value = data['nv_chitra_id'] ? data['nv_chitra_id'] : 0
        this.txt_MST.value = data['mst_tt'] ? data['mst_tt'] : ''
        this.txt_Vi_VNPT.value = data['vi_vnpay'] ? data['vi_vnpay'] : ''
        this.txt_GhiChu_TT.value = data['ghichu_tt'] ? data['ghichu_tt'] : ''


        this.onChangeDoiTuong()
        this.onChangeHangkenh()
        await Promise.all([
          this.getDanhsach_Hangkenh(),
          this.getDanhsach_NguoiDexuat(),
          this.getDanhsach_NhanVienChiTra(),

          this.getDanhsach_DichVuVT(),
          this.getDanhsach_LoaiHinhTB(),
          this.getDanhsach_CongDoan(),               
        ])     
        await this.getDanhsach_CoChe()   
      } catch (e) {
        this.$toast.error('Lỗi hiển thị thông tin hợp đồng: ' + e)
        console.log(e)
      }
    },
    // Change value
    onChangeLoai: function () {
      try {
        this.loading(true)
        this.khoamo_form(this.loai)
        // this.clear()
        // this.getDanhsach_HopDong()
      } catch (e) { } finally {
        this.loading(false)
      }
    },
    onChangeHangkenh: function () {
      if (this.cbb_Hangkenh.value == 1006) {
        this.hangkenhCTV = true;

      } else {
        this.hangkenhCTV = false;
      }
    },
    onChangeDoiTuong: async function () {
      await this.getDanhsach_TrangThai()
    },
    
    onChangeNguoiDeXuat: function () { },
    onChangeDVPheDuyet: function () { },
    onChangeNgayPD: function (value_) {
      if (!moment(value_, 'DD/MM/YYYY').isValid()) {
        this.dtp_NgayPheDuyet.value = '01/01/2000'
      } else {
        this.dtp_NgayPheDuyet.value = value_
      }
    },
    onChangeNgayKy: function (value_) {
      if (!moment(value_, 'DD/MM/YYYY').isValid()) {
        this.dtp_NgayKy.value = '01/01/2000'
      } else {
        this.dtp_NgayKy.value = value_
      }
    },
    diachiKBChanged: function () {
      // console.log(this.diachiKB.DIACHI);
      // console.log(this.diachiKB.DIACHI);
    },
    getDiaChiKB: function (data) {
      this.getDiaChi(data, 'diachiKB')
    },
    getDiaChi: function (data, datatype) {
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
      this[datatype].PHONG = data.sophong
      this[datatype].MOTA = data.motathem
      this[datatype].Lat = data.lat
      this[datatype].Lng = data.lng
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return []
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return []
      }
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return ''
      } else {
        this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        return ''
      }
    },
    clear: function () {
      this.hdkenh_id = 0
      this.tvkenh_id = 0
      this.cbb_MaPhieu.value = ''
      this.cbb_Doituong.value = ''
      this.cbb_Hangkenh.value = ''
      this.cbb_DVDexuat.value = ''
      this.cbb_NguoiDeXuat.value = ''
      this.txt_MaDaiLy.value = ''
      this.txt_TenDaiLy.value = ''
      this.diachiKB = {
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
      }
      this.txt_DienThoai.value = ''
      this.txt_Fax.value = ''
      this.txt_MST_CCCD.value = ''
      this.txt_NguoiDD.value = ''
      this.txt_ChucDanh.value = ''
      this.txt_MaCTV.value = ''
      this.txt_TenCTV.value = ''
      this.dtp_NgaySinh.value = ''
      this.cbb_GioiTinh.value = ''

      this.cbb_LoaiGT.value = 0
      this.txt_SoGT.value = ''
      this.dtp_NgayCap.value = ''
      this.txt_NoiCap.value = ''
      // TT Phe duyet
      this.cbb_DVPheDuyet.value = ''
      this.dtp_NgayPheDuyet.value = ''
      this.txt_LanhDaoDuyet.value = ''
      this.txt_YKienLD.value = ''
      // TT hop dong
      this.txt_SoHDPL.value = ''
      this.dtp_NgayKy.value = ''
      this.txt_NguoiKy.value = ``
      this.cbb_LoaiHD.value = ''
      this.cbb_LoaiThuLao.value = 0
      this.txt_TongTyLeThuLao.value = 0
      this.cbb_TanSuatHuong.value = ''
      this.txt_GhiChu.value = ''
      // Table TT hop dong
      this.cbb_DichVuVT.value = ''
      this.cbb_LoaiHinhTB.value = ''
      this.cbb_CongDoan.value = ''
      // TT them ve kenh ban
      this.txt_Username.value = ''
      this.cbb_TVKenh.value = ''
      this.cbb_NVQL.value = ''
      this.cbb_TrangThai.value = ''
      // TT thanh toan
      this.txt_SoTK.value = ''
      this.txt_ChuTK.value = ''
      this.cbb_NganHang.value = 0
      this.cbb_HTTT.value = 0
      this.cbb_DVQL_TT.value = 0
      this.cbb_NVQL_TT.value = 0
      this.txt_MST.value = ''
      this.txt_Vi_VNPT.value = ''
      this.txt_GhiChu_TT.value = ''

      this.grid_CoChe.list = []
      this.grid_HopDong.list = []
    },
    khoamo_form: function (loai) {
      // Thông tin đề xuất ký hợp đồng
      this.cbb_MaPhieu.Enabled = false
      this.cbb_Doituong.Enabled = false
      this.cbb_Hangkenh.Enabled = false
      this.cbb_DVDexuat.Enabled = false
      this.cbb_NguoiDeXuat.Enabled = false
      this.txt_MaDaiLy.Enabled = false
      this.txt_TenDaiLy.Enabled = false

      this.txt_DiaChi.Enabled = false
      this.txt_DienThoai.Enabled = false
      this.txt_Fax.Enabled = false
      this.txt_MST_CCCD.Enabled = false
      this.txt_NguoiDD.Enabled = false
      this.txt_ChucDanh.Enabled = false
      this.txt_MaCTV.Enabled = false
      this.txt_TenCTV.Enabled = false
      this.dtp_NgaySinh.Enabled = false
      this.cbb_GioiTinh.Enabled = false
      // TT Phe duyet
      this.cbb_DVPheDuyet.Enabled = false
      this.dtp_NgayPheDuyet.Enabled = false
      this.txt_LanhDaoDuyet.Enabled = false
      this.txt_YKienLD.Enabled = false
      switch (loai) {
        case -1: //test
          // Thông tin đề xuất ký hợp đồng
          this.cbb_MaPhieu.Enabled = true
          this.cbb_Doituong.Enabled = true
          this.cbb_Hangkenh.Enabled = true
          this.cbb_DVDexuat.Enabled = true
          this.cbb_NguoiDeXuat.Enabled = true
          this.txt_MaDaiLy.Enabled = true
          this.txt_TenDaiLy.Enabled = true

          this.txt_DiaChi.Enabled = true
          this.txt_DienThoai.Enabled = true
          this.txt_Fax.Enabled = true
          this.txt_MST_CCCD.Enabled = true
          this.txt_NguoiDD.Enabled = true
          this.txt_ChucDanh.Enabled = true
          this.txt_MaCTV.Enabled = true
          this.txt_TenCTV.Enabled = true
          this.dtp_NgaySinh.Enabled = true
          this.cbb_GioiTinh.Enabled = true
          // TT Phe duyet
          this.cbb_DVPheDuyet.Enabled = true
          this.dtp_NgayPheDuyet.Enabled = true
          this.txt_LanhDaoDuyet.Enabled = true
          this.txt_YKienLD.Enabled = true
          break
        case 1: //qua de xuat
          this.cbb_MaPhieu.Enabled = true
          break
        case 0: //khong qua de xuat
          this.cbb_Doituong.Enabled = true
          this.cbb_Hangkenh.Enabled = true
          this.txt_MaDaiLy.Enabled = true
          this.txt_TenDaiLy.Enabled = true
          this.txt_DiaChi.Enabled = true
          this.txt_DienThoai.Enabled = true
          this.txt_Fax.Enabled = true
          this.txt_MST_CCCD.Enabled = true
          this.txt_NguoiDD.Enabled = true
          this.txt_ChucDanh.Enabled = true

          this.txt_MaCTV.Enabled = true
          this.txt_TenCTV.Enabled = true
          this.dtp_NgaySinh.Enabled = true
          this.cbb_GioiTinh.Enabled = true
          break
      }
    },
    setButton: function (kieu) {
      this.btn_Enabled = {
        nhapMoi: false,
        ghiLai: false,
        xoa: false,
        huy: false,
      };
      if (kieu === -1) { //Bat dau  
        this.btn_Enabled.nhapMoi = true;
        this.btn_Enabled.ghiLai = false;
        this.btn_Enabled.huy = false;
      } else if (kieu === 1) { //Them moi
        this.clear();
        this.btn_Enabled.nhapMoi = false;
        this.btn_Enabled.ghiLai = true;
        this.btn_Enabled.huy = true;
      } else if (kieu === 2) { //Cap nhat
        this.btn_Enabled.nhapMoi = true;
        this.btn_Enabled.ghiLai = true;
        this.btn_Enabled.huy = true;
        this.btn_Enabled.xoa = true;
      }
    },
    // Button click
    btnLayTT_click: function () {
      this.Load_Form()
    },
    btnNhapMoi_click: function () {
      //Nhấn nhập mới
      this.setButton(1);
    },
    btnCapNhat_click: async function () {
      try {
        // Nhấn ghi lại
        this.loading(true)
        if (!this.validate()) return
        await this.CapNhat()
      } catch (e) {
        this.$toast.error('Lỗi cập nhật: ' + e)
      } finally {
        this.loading(false)
      }
    },
    CapNhat: async function () {
      try {
        var ds_coche = this.grid_CoChe.list && this.grid_CoChe.list.length > 0 ? this.grid_CoChe.list.map(e => ({
          "noidung": e.noidung,
          "congdoan_id": e.congdoan_id,
          "dichvu_id": e.dichvu_id,
          "loaihinh_id": e.loaihinh_id,
          "tyle": e.tyle,
          "sotien": e.sotien,
        })) : []
        var input = {
          "loai": this.loai,
          "ngay_yc": moment(new Date()).format('DD/MM/YYYY'),  // DD/MM/YYYY
          "hangkenh_id": Number(this.cbb_Hangkenh.value),
          "ma_kenh": this.cbb_Hangkenh.value == 1006 ? this.txt_MaCTV.value : this.txt_MaDaiLy.value,
          "ten_kenh": this.cbb_Hangkenh.value == 1006 ? this.txt_TenCTV.value : this.txt_TenDaiLy.value,
          "diachi": this.diachiKB.DIACHI,
          "tinh_id": this.diachiKB.TINH_ID,
          "quan_id": this.diachiKB.QUAN_ID,
          "phuong_id": this.diachiKB.PHUONG_ID,
          "pho_id": this.diachiKB.PHO_ID,
          "ap_id": this.diachiKB.AP_ID,
          "khu_id": this.diachiKB.KHU_ID,
          "so_dt": this.txt_DienThoai.value,
          "fax": this.txt_Fax.value,
          "mst_cccd": this.txt_MST_CCCD.value,
          "nguoi_dd": this.txt_NguoiDD.value,
          "chucdanh": this.txt_ChucDanh.value,
          "gioitinh": this.cbb_GioiTinh.value,
          "ngaysinh": this.dtp_NgaySinh.value,
          "dt_ban_id": Number(this.cbb_Doituong.value),
          "hdkenh_id": this.hdkenh_id,
          "ma_hd": this.txt_SoHDPL.value,
          "ngay_ky": this.dtp_NgayKy.value,
          "nguoiky": this.txt_NguoiKy.value,
          "loaihd_id": Number(this.cbb_LoaiHD.value),
          "htttk_id": Number(this.cbb_TanSuatHuong.value), //input.htttk_id, ????
          "thuoc_tvkenh_id": Number(this.cbb_TVKenh.value),
          "nvql_id": this.cbb_NVQL.value,
          "flag": this.cbb_TrangThai.value,
          "ma_nd": this.txt_Username.value,
          "loaithulao_id": this.cbb_LoaiThuLao.value, 
          "tong_thulao": this.txt_TongTyLeThuLao.value, 
          "ghichu": this.txt_GhiChu.value, 
          "stk": this.txt_SoTK.value,           
          "chutaikhoan": this.txt_ChuTK.value,           
          "nganhang_id": this.cbb_NganHang.value, 
          "httt_id": this.cbb_HTTT.value, 
          "donvi_qltt": this.cbb_DVQL_TT.value, 
          "nv_chitra_id": this.cbb_NVQL_TT.value, 
          "mst_tt": this.txt_MST.value,
          "vi_vnpay": this.txt_Vi_VNPT.value,
          "ghichu_tt": this.txt_GhiChu_TT.value, 
          "loaigt_id": this.cbb_LoaiGT.value,
          "so_gt": this.txt_SoGT.value,          
          "noicap": this.txt_NoiCap.value,
          "ngay_cap": this.dtp_NgayCap.value,
          "coche_data": ds_coche
        }
        var response = await api.capNhatHopDong(this.axios, input)
        if (response && response.data.error_code == 'BSS-00000000') {
          if (response.data.data == 'ok' || response.data.data == 'OK') {
            var message = !this.btn_Enabled.nhapMoi ? 'Thêm mới' : 'Ghi lại'
            this.$toast.success(message + ' hợp đồng thành công!')
            this.setButton(2)
            this.getDanhsach_HopDong()
          } else if (response.data.data) {
            this.$toast.error(response.data.data)
          }
        } else {
          this.$toast.error('Lỗi ghi lại: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
        }
      } catch (e) {
        this.$toast.error('Lỗi ghi lại: ' + e)
      }
    },
    validate: function () {
      if (this.loai == 1) {
        if (!this.cbb_MaPhieu.value) {
          this.$toast.error('Phiếu qua đề xuất, Mã phiếu không được để trống!')
          return false
        }

        if (!this.hdkenh_id) {
          this.$toast.error('Phiếu qua đề xuất ' + this.cbb_MaPhieu.value + ' đang có Hợp đồng kênh ID là 0, hãy liên hệ admin để xử lý!')
          return false
        }

        if (!this.cbb_DVDexuat.value) {
          this.$toast.error('Đơn vị đề xuất không được để trống!')
          return false
        }

        if (!this.cbb_NguoiDeXuat.value) {
          this.$toast.error('Người đề xuất không được để trống!')
          return false
        }
      } else {
        
      }

      if (Number(this.cbb_Hangkenh.value) == 1006) {
        if (!this.txt_MaCTV.value) {
          this.$toast.error('Mã CTV không được để trống!')
          return false
        }

        if (!this.txt_TenCTV.value) {
          this.$toast.error('Tên CTV không được để trống!')
          return false
        }
        
        if ((this.cbb_GioiTinh.value != 0 && !this.cbb_GioiTinh.value) || this.cbb_GioiTinh.value.toString().trim() == '') {
          this.$toast.error('Giới tính không được để trống!')
          return false
        }
      } else {
        if (!this.txt_MaDaiLy.value) {
          this.$toast.error('Mã đại lý không được để trống!')
          return false
        }

        if (!this.txt_TenDaiLy.value) {
          this.$toast.error('Tên đại lý không được để trống!')
          return false
        }

        if (!this.txt_MST_CCCD.value) {
          this.$toast.error('Mã số thuế không được để trống!')
          return false
        }
      }

      if (!this.cbb_Doituong.value) {
        this.$toast.error('Đối tượng không được để trống!')
        return false
      }

      if (!this.cbb_Hangkenh.value) {
        this.$toast.error('Hạng kênh không được để trống!')
        return false
      }

      if (!this.diachiKB.DIACHI) {
        this.$toast.error('Bạn chưa chọn địa chỉ!')
        return false
      }

      if (!this.diachiKB.TINH_ID) {
        this.$toast.error('Bạn chưa chọn địa chỉ tỉnh!')
        return false
      }

      if (!this.diachiKB.QUAN_ID) {
        this.$toast.error('Bạn chưa chọn địa chỉ quận!')
        return false
      }

      if (!this.diachiKB.PHUONG_ID) {
        this.$toast.error('Bạn chưa chọn địa chỉ phường/xã!')
        return false
      }

      if (!this.diachiKB.PHO_ID && !this.diachiKB.AP_ID && !this.diachiKB.KHU_ID) {
        this.$toast.error('Bạn phải chọn địa chỉ Phố, Ấp hoặc Khu!')
        return false
      }

      if (!this.txt_SoHDPL.value || this.txt_SoHDPL.value.toString().trim() == '') {
        this.$toast.error('Số HĐ/PL không được để trống!')
        return false
      }

      if (!this.dtp_NgayKy.value) {
        this.$toast.error('Ngày ký không được để trống!')
        return false
      }

      if (!this.txt_NguoiKy.value || this.txt_NguoiKy.value.toString().trim() == '') {
        this.$toast.error('Người ký không được để trống!')
        return false
      }

      if (!this.txt_Username.value || this.txt_Username.value.toString().trim() == '') {
        this.$toast.error('Username không được để trống!')
        return false
      }

      if (!this.cbb_NVQL.value || this.cbb_NVQL.value.toString().trim() == '') {
        this.$toast.error('Bạn chưa chọn nhân viên quản lý!')
        return false
      }

      if (!this.cbb_LoaiThuLao.value || this.cbb_LoaiThuLao.value.toString().trim() == '') {
        this.$toast.error('Bạn chưa chọn loại thù lao!')
        return false
      }

      if (!this.cbb_TanSuatHuong.value || this.cbb_TanSuatHuong.value.toString().trim() == '') {
        this.$toast.error('Bạn chưa chọn tần suất hưởng!')
        return false
      }

      return true
    },
    btnHuy_click: async function () {
      try {
        // Nhấn Hủy
        this.loading(true)
        this.setButton(-1);
      } catch (e) {
        this.$toast.error('Lỗi hủy: ' + e)
      } finally {
        this.loading(false)
      }
    },
    btnXoa_click: async function () {
      try {
        // Nhấn Xóa
        this.loading(true)
        this.$bvModal.msgBoxConfirm("Bạn có muốn xóa hợp đồng này không?", {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Không đồng ý',
        }).then(async v => {
          if (v) {
            let input = {
              hdkenh_id: this.hdkenh_id
            }
            let data = this.GetData(await api.xoaHopDong(this.axios, input));
            if (data == 'ok' || data == 'OK') {
              this.$toast.success('Xóa hợp đồng thành công!');
              this.getDanhsach_HopDong()
            }
          }
        });
      } catch (e) {
        this.$toast.error('Lỗi xóa dữ liệu: ' + e)
      } finally {
        this.loading(false)
      }
    },
    btnTTThanhToan_click: async function () {
      try {
        // Nhấn TT Thanh toán
        this.loading(true)
        // this.$toast.info('Chức năng đang đưuọc xây dựng!')
        if (!this.grid_HopDong.selected) {
          this.$toast.error('Chưa tạo hợp đồng, bạn hãy tạo hợp đồng trước!')
          return
        }
        this.popupComponentData = this.grid_HopDong.selected
        this.popupComponent = () => import('./popups/popup_TTThanhToan')
        this.popupComponentName = 'popup_TTThanhToan'
        this.popupComponentHeader = ''
        this.Popup('popupComponents')
      } catch (e) {
        this.$toast.error('Lỗi: ' + e)
      } finally {
        this.loading(false)
      }
    },
    btnFileHoSo_click: function () {
      this.$refs.HosoFileModal.showModal();
    },
    btnLichSu_click() {
      this.$refs.lichsugiaophieuModal.showModal();
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async popupClosed(val) {
      try {
        if (!val) {
          this.ClosePopup('popupComponents')
          return
        }
        this.loading(true)

        console.log('popupClosed', this.popupComponentName, val)
        switch (this.popupComponentName) {
          case 'popup_TTThanhToan':
            break
        }
      } catch (e) {
        console.log('popupClosed', e)
      } finally {
        this.loading(false)
      }
    }
  },
}
</script>

<style scoped src="./SalesChannelContract.scss"></style>
<style>
.w120 {
  width: 120px !important;
}
.select2 {
  width: 100% !important;
}

.custom-row {
  display: flex !important;
  flex-direction: row !important;
}

.pad2 {
  padding-left: .5rem !important;
  padding-right: .5rem !important;
}

@media (min-width: 992px) {
  .custom-form-50 {
    width: calc(50%) !important;
  }
  .modal .modal-huge {
    top: 0px !important;
    max-width: 95% !important;
    width: 100% !important;
  }
}

@media (min-width: 576px) {
  .custom-form-50 {
    width: calc(100%) !important;
  }
  .modal .modal-huge {
    top: 0px !important;
    max-width: 95% !important;
    width: 100% !important;
  }
}
</style>