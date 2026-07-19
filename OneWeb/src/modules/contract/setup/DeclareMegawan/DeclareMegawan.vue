<template src="./DeclareMegawan.html"></template>
<style src="./DeclareMegawan.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import Vue from 'vue';
import VueFlatPickr from 'vue-flatpickr-component';
import api from './API'
import moment from "moment";
import { required, minLength } from 'vuelidate/lib/validators'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import ChonPort from '@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/chon-port'
import ChuyenTo from '@/modules/contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue'
import TraPhieu from '@/modules/contract/setup/ReturnInstallTicket/components/traphieu.vue'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import CapVatTuTB from '@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue'
import IP_WAN_LAN from '@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/IP_LAN_WAN'
import ThongTinHenKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import TonPhieuGiaoModal from '@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/TonPhieuGiao/TonPhieuGiaoModal.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from '../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import DoKiem from '@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/DoKiem/DoKiemModal.vue'
import { LoaiHopDong } from '../DeclareLandline/define';
import CTKetNoi from './Popups/CTKetNoi/CTKetNoi.vue'
import CTKetNoi_VFI from './Popups/CTKetNoi_VFI/CTKetNoi_VFI.vue'
import daunoithuebao from './Popups/daunoithuebao'
import TraCuuThongSoKTModal from '../DeclareServiceSpeedITSecure/popups/TraCuuThongSoKT/TraCuuThongSoKTModal'
import UploadEProfileContent from "./Popups/UploadEProfile/UploadEProfile_content.vue"
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import InBienBan from '@/modules/print/InBienBan/InBienBan.vue'
import frnGiaoPhieuNV from '../../setup/DeclareLandline/popups/frnGiaoPhieuNV.vue'
import GiaoPhieu_PH from './Popups/GiaoPhieu_PH/GiaoPhieu_PH.vue'
import VatTuThueBao from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import XemBaoCao from '@/modules/print/XemBaoCao/XemBaoCao'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue';
//
export default {
  components: {
    breadcrumb,VueFlatPickr,
    SearchContractModal,ModalChonDiaChi,ChonPort,ChuyenTo,TraPhieu,
    CapVatTuTB,IP_WAN_LAN,ThongTinHenKhachHangModal,TonPhieuGiaoModal,
    ThayDoiKhuVucQuanLyThueBaoModal,DoKiem,
    CTKetNoi, CTKetNoi_VFI, daunoithuebao, TraCuuThongSoKTModal, UploadEProfileContent, ExportDataModal, InBienBan, frnGiaoPhieuNV, GiaoPhieu_PH, VatTuThueBao,
    KDatePicker, XemBaoCao, 
    GiaoPhieuView
  },
  name: 'DeclareMegawan',
  props: [ '_Tag','matb_link','_vquytrinh_id' ],
  mounted () {    
    $("#txtMaGD").focus()
  },
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput:true,
      },
      Tag: "",
      Param_Tag: {
        tthd_id: 0,
        dsloaihd_id: '',
        dsdichvuvt_id: ''
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close':this.popupClosed,       
        'traphieu_success':this.popupClosed
      },
      popupComponentName: '',
      thamsomacdinh: {},
      modelVatTuThueBao: {
        phieu_id: 0,
        loaihd_id: 0,
        hdtb_id: 0,
        thuebao_id: 0
      },
      visible: {
        tsbtnGiaoPhieu: true,
        tsbtnHoanCong: true,
        tsbtnTraPhieu: true,
        tsbtnVatTu: true,
        tsbtnGiaoViec_KBB: true,
        tsbtnVatTuMoi: false,
        tsbtnDuAn: true,
        tsbtnHoanThienHS: true,
        tsbtnKQ_DoKiem: true,
        tsbtnSua_KenhThu: true,
        tsbUpHoSo: false,
        tsbtnTinh_GiaoNET_NT: false,
        palThongSoKyThuat: false,
        palThongTinCapCu: true,
      },
      enable: {
        btnPhieuTC: true,
      },
      Kieu_HD: {
        TAI_GD: 1,
        TAI_NHA: 2,
      },
      LoaiHopDong: {
        KHONG_DUNG: 0,
        DAT_MOI: 1,
        CHUYEN_QUYEN: 2,
        DI_CHUYEN: 3,
        CHAMDUT_SD: 4,
        THAYDOI_THONGSO_MEGAWAN: 5,
        CHUYENDOI_LH: 6
      },
      TrangThaiHD: {
        MOI: 1,
        THANH_TOAN: 2,
        DIEUHANH_THI_CONG: 3,
        DANG_THI_CONG: 4,
        DA_THI_CONG_XONG: 5,
        HOAN_THANH: 6,
        THOAITRA: 7,
        DA_LAY_DU_LIEU: 8,
        KHAIBAO_TONGDAI: 9,
        DANGCHO_HOANCONG: 10,
        DOISOAT_HOSO: 11,
        TTVT_XACMINH_NO: 14,
        THOAITRA_TMP: 17,
        TTVT_HOANCONG_DICHVU: 23,
        HTHS_BANCHEO: 26,
        NVKD_XACMINH: 27
      },
      LoaiNode_MGWan:{
        NODE_CHINH: 1,
        NODE_PHU: 2,
        NODE_DUPHONG_CONG_TOCDO: 3,
        NODE_DUPHONG_TOCDO: 4
      },
      LoaiHinhTB: {
        MEGAWAN_ADSL: 23,
        MEGAWAN_SHDSL: 25,
        MEGAWAN_QUANG_FE: 26,
        MEGAWAN_QUANG_GE: 30,
        METRONET_FE: 27,
        METRONET_GE: 28,
        MEGAWAN_ADSL_2M: 74
      },
      TRANGTHAI_PORT: {
          CHUA_SD: 1,
          TAM_CAP: 2,
          DA_SD: 3,
          HONG: 4,
          DU_PHONG: 5,
          UPLINK: 7
      },
      KIEULAP: {
          LAP_KHONG_SO: 0,
          LAP_KEM: 1,
          LAP_LINE_SAN: 2,
          THAYDOI_TSO: 3,
          PORT_THEO_CAP: 4//KieuNV: Thêm kiểu cấp port theo thông tin cáp cho DNG
      },
      DichVuVienThong: {
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
        TSL_DOITAC: 24
      },
      ngay_ht: new Date(),
      header: {
        title: 'HOÀN CÔNG MEGAWAN',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
        ]
      },
      isNhapMoi:true,
      isCapNhat:false,
      isHuyBo:false,
      isXoaPhieu:false,
      isTimTheoLoaiHD: false,
      isTimTheoDichVu: false,
      isTimTheoQuyTrinh: false,
      DsNhanVienTH:{
         headers: [                                    
                    {fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true,width:150},  
                    {fieldName: 'so_dt', headerText: 'Điện thoại', allowFiltering: true,width:100},
                    {fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true , allowHtml : true,width:200},  
                    {fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true , allowHtml : true,width:200},                                        
                  ],
        data: [],
        selected: 0,
        checked: [],
        nhanvien_tc_id: 0,
        congviec_th: ''
      },
      DsDichVuDK:{
         headers: [                                    
                    {fieldName: 'ma_dvgt', headerText: 'Mã DVGT', allowFiltering: true,width:150},  
                    {fieldName: 'ten_dvgt', headerText: 'Tên dịch vụ', allowFiltering: true,width:150},
                    {fieldName: 'kieu_yc', headerText: 'Kiểu YC', allowFiltering: true , allowHtml : true,width:150},  
                    // {fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true , allowHtml : true,width:150},                                        
                  ],
        data: [],
        selected: 0,
        checked: []
      },
      DsLam:{
        headers: [
              {fieldName: 'portvl_id', headerText: 'PORTVL_ID', allowFiltering: true,width:80},  
              {fieldName: 'link', headerText: 'Link', allowFiltering: true,width:100},
              {fieldName: 'tendslam', headerText: 'Thiết bị đích', allowFiltering: true , allowHtml : true,width:150},  
              {fieldName: 'thamso', headerText: 'Tham số', allowFiltering: true , allowHtml : true,width:100},
              {fieldName: 'loai_tbi', headerText: 'Loại thiết bị đích', allowFiltering: true , allowHtml : true,width:100}   
      ],
        data: [],
        selected: 0,
        checked: []
      },
      DsThueBao:{
        headers: [        
          {fieldName: '', headerText: '', visible: true,  headerAlign: 'Center', textAlign: 'Center', allowFiltering: false, width: '120', freeze: "left", 
            headerTemplate: () => {
                return {
                    template: Vue.component("action-check-all", {
                        template: `
                                <div class="check-action">
                                    <input type="checkbox" v-model="parent.DsThueBao.isCheckAll" class="check" 
                                        @change="onChangeProcessed(data)" />
                                    <span class="name">Chọn in</span>
                                </div>
                            `,
                        data: function () {
                            return { data: {} };
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent
                            },
                        },
                        methods: {
                            onChangeProcessed(value) {
                                this.parent.clickOnGrid = false
                            },
                        }
                    }),
                };
            },
            template: function () {
              return {
                template: Vue.component('action-check', {
                  template: `
                        <div class="check-action">
                          <input 
                            type="checkbox" 
                            class="check"
                            v-model="parent.DsThueBao.checked"
                            :value="data.hdtb_id"/>
                          <span class="name"></span>
                        </div>
                      `,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent
                    }
                  },
                })
              }
            }
          },                            
          {fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, freeze: "left"},  
          {fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true, freeze: "left"},
          {fieldName: 'ma_tb_cu', headerText: 'Số máy/Acc cũ', allowFiltering: true, freeze: "left"},  
          {fieldName: 'site_id', headerText: 'Site ID', allowFiltering: true , allowHtml : true, freeze: "left"},  
          {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true , allowHtml : true},
          {fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'ten_kv', headerText: 'Tên khu vực', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'ma_kv', headerText: 'Mã khu vực', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'ngay_yc_convert', headerText: 'Ngày YC', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true , allowHtml : true, customAttributes: {class: 'cl_do'}},                                        
          {fieldName: 'dv_hen', headerText: 'Đơn vị hẹn', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'giohen_tu', headerText: 'Hẹn từ', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'giohen_den', headerText: 'Hẹn đến', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'goi_kt', headerText: 'Gói KT', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'chitieu_tg', headerText: 'Chỉ tiêu TG', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'ngay_ht_gp', headerText: 'Thời gian cam kết', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'gio_conlai', headerText: 'Giờ còn lại', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'dv_giao', headerText: 'Đơn vị giao', allowFiltering: true , allowHtml : true, customAttributes: {class: 'cl_xanh'}},                                        
          {fieldName: 'madoicap', headerText: 'Mã đôi cáp', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'status', headerText: 'Trạng thái in', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'status', headerText: 'Trạng thái', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'ngay_ht', headerText: 'Ngày BG', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'ngay_yc_convert', headerText: 'Ngày KH', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'lydoton', headerText: 'Lý do tồn', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'ghichu_ton', headerText: 'Ghi chú tồn', allowFiltering: true , allowHtml : true},                                        
          {fieldName: 'nd_thuchien', headerText: 'Ghi chú', allowFiltering: true , allowHtml : true},                                        
        ],
        data: [],
        selected: 0,
        checked: [],
        isCheckAll: false,
        clickOnGrid: false,
        hdtb_id: 0,
        hdtb_cha_id: 0,
        hdtt_id: 0,
        khachhang_id: 0,
        thanhtoan_id: 0,
        kieuld_id: 0,
        dichvuvt_id: 0,
        madoicap: 0,
        ma_tb: '',
        phieu_id: 0,
        hdkh_id: 0,
        nhanvien_id: 0, 
        ctv_id: 0,   
        loaihinhtb_id: 0 
      },
      ThongTinKH: {
        txtNVTiepThi: '',
        txtTongTien: 0,
        txtSoDTLH: '',
        txtTenNV_YC: '',
        txtKieuLD: '',        
        txtDiaChiLD: '',
        txtDiaChiLDCu: '',
        txtSoAoWan: '',
        txtMaGD: '',
        txtMaTB: '',
        txtTenTB: '',
        txtKhuVuc: '',
        txtLoaiHinh: '',
        loaihinhtb_id: 0,
        thuebao_id: 0,
        thuebao_cha_id: 0,
        diachild_id: 0
      },
      ThongTinHC: {
        txtTenNV_HC: '',
        txtLyDoTra: '',
        txtThongTinTra: '',
        txtSiteID: '',
        txtMaTB_TN: '',
        txtNoiDungTH: '',
        txtSoLuongIP: '',
        HuongKNStatus: true,
        DsLamStatus: true,
        txtDsLam: '',
        ADSLportStatus: false,
        txtADSLport: '',
        NasportIDStatus: false,
        txtNasportID: '',
        SvLanStatus: false,
        txtSvLan: '',
        CvLanStatus: false,
        txtCvLan: '',
        DmThietBiStatus: false,
        LanIPStatus: false,
        txtLanIP: '',
        WanIPStatus: false,
        txtWanIP: false,
        PortMDFtatus: false,
        txtPortMDF: '',
        lblDsLam: 'DsLam',
        SerialStatus: false,
        chkLuongGiuKenh: false,
        LuongGiuKenhStatus: false,
        txtSerial: '',
        txtTuyenCap: '',
        txtLienTu: '',
        txtTuCapNgon: '',
        txtDoiCapNgon: '',
        txtHopCapNgon: '',
        txtCapGoc: '',
        txtCapNgon: '',
        txtDoiCapGoc: '',
        txtTuCapGoc: '',
        txtCuLy: '',
        txtLapKem: '',
        txtDiaChiKN: '',
        huongkn: 0,
        tinh_kn_id: 0,
        txtHuongKN: '',
        txtPortPE: '',
        txtSlotPE: '',
        txtWanIP: '',
        txtSvLan: '',
        txtCvLan: '',
        txtVRF: '',
        txtSerial: '',
        txtLink: '',
        txtRoute: '',
        txtNDgiao: '',
        txtGhiChu: '',
        donvi_id: 0,
        port_id: 0,
        vci_vpi_id: 0,
        congnghe_id: 0,
        tramtb_id: 0,
        txtIPADSL: '',
        txtPort: '',
        txtTramTB: ''  
      },
      DsThaoTac:{
        data: []
      },
      listbox: {
        //main form                              
        doitac: {
          list: [],
          value: {},
        },        
        //popup huong ket noi
        tracuutheo: {
          list: [],
          value: {},
        },
        dichvu: {
          list: [],
          value: {},
        },
        donvi: {
          list: [],
          value: {},
        },
        tinhketnoi: {
          list: [],
          value: {},
        },
        //Popup số chính số phụ
        router_sochinh: {
          list: [],
          value: {},
        },
        router_sofu: {
          list: [],
          value: {},
        },
        loaicap_sochinh: {
          list: [],
          value: {},
        },
        loaicap_sofu: {
          list: [],
          value: {},
        },
        //Popup số chính số phụ ___
        router_danhba: {
          list: [],
          value: {},
        },
        router_hopdong: {
          list: [],
          value: {},
        },
        loaicap_danhba: {
          list: [],
          value: {},
        },
        loaicap_hopdong: {
          list: [],
          value: {},
        },       
        //popup huong ket noi
         ds_khachhang: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
         ds_huongketnoi: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
        //Popup số chính số phụ
         ds_sochinh: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
        ds_sofu: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
        //Popup số chính số phụ ___
         ds_thuebao: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
        ds_hopdong: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
        },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      ngaygv: moment(new Date()).format('DD/MM/YYYY hh:mm A'),
      ngayht: moment(new Date()).format('DD/MM/YYYY hh:mm A'),
      success_code: "BSS-00000000",
      Loading: false,
      tien: 0,
      thongtingoi: "",
      MaTB: "",
      TenTB: "",
      LoaiTB: "",
      DiaChi: "",
      GhiChu: "",
      GoiID: "",
      GhiChu_Cu: "",
      LoaiTBid_Cu: "",
      GoiID_Cu: "",
      ThuebaoID: "",
      kieuhd_id: 0,
      DmKieuHD: [],
      kenhthu_id: 0,
      DmKenhThu: [],
      loaihd_id: 0,
      DmLoaiHD: [],
      quytrinh_id: 0,
      DmQuyTrinh: [],
      nguoigv_id: 0,
      DmNguoiGV: [],
      thoihan_id: 0,
      DmThoiHan: [],
      loaikenh_id: 0,
      DmLoaiKenh: [],
      modem_id: 0,
      DmMoDem: [],
      router_id: 0,
      DmRouter: [],
      tocdocir_id: null,
      DmTocDoCIR: [],
      tocdonix_id: null,
      DmTocDoNIX: [],
      tocdopir_id: null,
      DmTocDoPIR: [],
      tocdoisp_id: null,
      DmTocDoISP: [],
      trangbi_id: 0,
      DmTrangBi: [],
      loainode_id: 0,
      DmLoaiNode:[],
      loaitbi_id: 0,
      DmLoaiTBi: [],
      chuquan_id: 0,
      DmChuQuan: [],
      loaicap_id: 0,
      DmLoaiCap: [],
      thietbi_id: 0,
      DmThietBi: [],
      doitac_id: 0,
      DmDoiTac: [],      
      chkTD_PIR: false,
      DmTocDoPIRStatus: false,
      chkTD_ISP: false,
      DmTocDoISPStatus: false,
      chkTD_NIX: false,
      DmTocDoNIXStatus: false,
      chkDoiTac: false,
      DmDoiTacStatus: false,
      chkNgayGV: false,
      ngaygvStatus: false,
      chkNgayHT: false,
      ngayhtStatus: false,
      chkLayTSo: false,
      LayTSoStatus: false,
      huonggiao_id: 0,
      luong_id: 0,
      rdLoaiPhieu: 0,
      GiaoPhieuStatus: false,
      ChonPortStatus: false,
      ChonPortTDongStatus: false,
      HoanThienStatus: false,
      HoanCongStatus: false,
      showModalTraphieu: false,
      modelVatTu: {},
      objIP_WAN_LAN: {
        hdtb_id: null,
        loaitb_id: null,
        dichvuvt_id: null,
        bras_id: null,
        ma_tb: null,
        tenbras: null,
        trangthaidl_id: 1
      },
      diaChiLD: {
        DIACHI: null,
        TINH_ID:null,
        QUAN_ID:null,
        PHUONG_ID:null,
        PHO_ID:null,
        AP_ID:null,
        KHU_ID:null,
        DAC_DIEM_ID:null,
        SO_NHA:null,
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
      },
      dataPopupKhuVuc: {},
      modelIn: {},
      modelGiaoViec: {},
      modelUploadEFile: {},
      // headTitle: "HOÀN CÔNG MEGAWAN",
      dsGiaTri: [],
      giaoPhieuViewDialog: {
        isVisiable: false,
        input: {
          vhuonggiao_id: null,
          vloai_hd: null,
          vdichvuvt_id: null,
          vma_gd: null,
          vquytrinh: null,
          vtrangthaiphieu: 1,
        }
      },
      animationSettings: { effect: 'None' },
    }
  },
  watch: {
    "DsThueBao.isCheckAll": {
        handler: function (value) {
            if (value) {
                this.DsThueBao.data.forEach((f) => {
                    f.checked = true;
                });
                this.DsThueBao.checked = this.DsThueBao.data.map(
                    (f) => f.hdtb_id
                );
                return;
            } else {
                if (!this.clickOnGrid) {
                    this.DsThueBao.data.forEach((f) => {
                        f.checked = false;
                    });
                    this.DsThueBao.checked = [];
                    return;
                }
            }
        },
        deep: true,
    },
    "DsThueBao.checked": {
        handler: function (value) {
            this.clickOnGrid = true
            this.DsThueBao.isCheckAll = value.length > 0 && value.length === this.DsThueBao.data.length;
        },
        deep: true,
    },
    Loading: function (val, oldval) {
      this.$root.showLoading(val);
    },
    loaihd_id: async function(){
      await this.getDmQuyTrinh();
      if (Number(this.loaihd_id) == this.LoaiHopDong.DAT_MOI || Number(this.loaihd_id) == this.LoaiHopDong.DI_CHUYEN) {
          this.LayTSoStatus = false;
          this.enable.btnPhieuTC = true;
      } else {
          this.LayTSoStatus = true;
          this.chkLayTSo = false;
          this.enable.btnPhieuTC = false;
      }
      if (this._vquytrinh_id && this._vquytrinh_id != 0) {
          this.quytrinh_id = this._vquytrinh_id
      }
    },
    quytrinh_id: async function(){
      await this.getHuongGiaoTheoQT()   
    },
    chkLayTSo: async function(val){
      if (val == true){
          var imadoicap = 0;
          var kieuld_id = 0;  //1<->Lap kem; 2<->Lap tren duong co san
          var kieu = 0;
          if (this.ThongTinHC.txtMaTB_TN ==  "")
          {
            this.$toast.error("Thuê bao đang lắp trên đường không số. \n Bạn không thể lấy các thông số đã có.")
            return
          }
          if (this.DsThueBao.madoicap != 0)
              imadoicap = this.DsThueBao.madoicap
          if (this.DsThueBao.kieuld_id == 0 || imadoicap == 0)
          {
              this.$toast.error("Không tìm thấy thông tin!")
              return
          }
          kieuld_id = this.DsThueBao.kieuld_id
          // lay thong tin kieu lap dat
          var pdata= { "p_param": kieuld_id,
                       "p_type": 1
                     }
          api.sp_tt_kieu_ld(this.axios,  pdata).then((response) => {  
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                console.log('vao day: ',response.data.data)                
            }else{      
                this.$toast.error(response.data.message)
            }        
          });
          // lay thong so thi cong
          var pdata_tc = { "vma_tb": "3636706", "vmadoicap": 118346, "vkieu": 123 }//{"vma_tb": this.txtMaTB_TN,"vmadoicap": imadoicap,vkieu: kieu}
          api.lay_thongso_thicong(this.axios,  pdata_tc).then((response) => {  
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) { 
                var data_api = response.data.data
                this.ThongTinHC.txtTuyenCap = data_api[0].tuyencap
                this.ThongTinHC.txtLienTu = data_api[0].lientu
                if (data_api[0].loaicap_id != "")
                    this.loaicap_id = data_api[0].loaicap_id
                this.ThongTinHC.txtTuCapNgon = data_api[0].tucap_p
                this.ThongTinHC.txtDoiCapNgon = data_api[0].doicap_p
                this.ThongTinHC.txtHopCapNgon = data_api[0].hopcap_p
                this.ThongTinHC.txtCapNgon = data_api[0].cap_p
                this.ThongTinHC.txtCapGoc = data_api[0].cap_goc
                this.ThongTinHC.txtDoiCapGoc = data_api[0].doicap_goc
                this.ThongTinHC.txtTuCapGoc = data_api[0].tucap_goc
                this.ThongTinHC.txtCuLy = data_api[0].culy
            }else{      
                var data_api = response.data.data
                this.ThongTinHC.txtTuyenCap = ''
                this.ThongTinHC.txtLienTu = ''
                this.ThongTinHC.txtTuCapNgon = ''
                this.ThongTinHC.txtDoiCapNgon = ''
                this.ThongTinHC.txtHopCapNgon = ''
                this.ThongTinHC.txtCapNgon = ''
                this.ThongTinHC.txtCapGoc = ''
                this.ThongTinHC.txtDoiCapGoc = ''
                this.ThongTinHC.txtTuCapGoc = ''
                this.ThongTinHC.txtCuLy = ''
            }        
          });
      }
    },
    rdLoaiPhieu: async function(){
      try{
        this.Loading =true        
          this.getDsHopDongTB('0')
      }
      catch(ex){
        this.Loading =false
      }
    },
    chkTD_PIR: async function(val){
      if(val){
        this.DmTocDoPIRStatus = val
      }else{
        this.DmTocDoPIRStatus = val
      }
    },
      
    chkTD_ISP: async function(val){
      if(val){
        this.DmTocDoISPStatus = val
      }else{
        this.DmTocDoISPStatus = val
      }
    },
    
    chkTD_NIX: async function(val){
      if(val){
        this.DmTocDoNIXStatus = val
      }else{
        this.DmTocDoNIXStatus = val
      }
    },
    chkDoiTac: async function(val){
      if(val){
        this.DmDoiTacStatus = val
      }else{
        this.DmDoiTacStatus = val
      }
    },
    chkNgayGV: async function(val){
      if(val){
        this.ngaygvStatus = val
      }else{
        this.ngaygvStatus = val
      }
    },
    chkNgayHT: async function(val){
      if(val){
        this.ngayhtStatus = val
      }else{
        this.ngayhtStatus = val
      }
    },
  }, 
  async created () {
    this.Loading = false;
    try {
        Promise.all([ 
          this.getParam_load(),        
          this.getDmKieuHD(),
          this.getDmKenhThu(),
          this.getDmLoaiHD(),
          this.getDmNguoiGV(),
          this.getDmThoiHan(),
          this.getDmLoaiKenh(),
          this.getDmMoDem(),
          this.getDmRouter(),
          this.getDmTocDoCIR(),
          this.getDmTocDoPIR(),
          this.getDmTocDoNIX(),
          this.getDmTocDoISP(),
          this.getDmTrangBi(),
          this.getDmLoaiNode(),
          this.getDmLoaiTBi(),
          this.getDmChuQuan(),
          this.getDmThietBi(),
          this.getDmLoaiCap(),
          this.getDmDoiTac(),
          ])
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
  },
  computed: {
    NhapMoi: {
      get() { return this.isNhapMoi; },
      set(value) { this.isNhapMoi=value; 
      }
    },
    CapNhat: {
      get() { return this.isCapNhat; },
      set(value) { this.isCapNhat=value; 
      }
    },
     HuyBo: {
      get() { return this.isHuyBo; },
      set(value) { this.isHuyBo=value; 
      }
    },
    XoaPhieu: {
      get() { return this.isXoaPhieu; },
      set(value) { this.isXoaPhieu=value; 
      }
    },
    timtheohd: {
      get() { return this.isTimTheoLoaiHD; },
      set(value) { this.isTimTheoLoaiHD=value; }
    },
    timtheodichvu: {
      get() { return this.isTimTheoDichVu; },
      set(value) { this.isTimTheoDichVu=value; }
    },
    timtheoquytrinh: {
      get() { return this.isTimTheoQuyTrinh; },
      set(value) { this.isTimTheoQuyTrinh=value; }
    },
    timall: {
      get() { return !this.isCachTim; },
      set(value) { this.isCachTim=!value; }
    },
    
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
    listbox:
    {
      goidadichvu: 
      {
        idx: {required, minLength: minLength(1)}
      }
    }
  },
  methods: {
    ShowAlert(mesage)
    {
      this.$toast.error(mesage);
    },
    getParam_load: async function(){
      var str = "";
      this.visible.tsbUpHoSo = true
      try
      {
          if (this.$route.query.tag != null && this.$route.query.tag !== '' && this.$route.query.tag.length > 0) {
              this.Tag = decodeURIComponent(this.$route.query.tag.toString())
              str = this.Tag;
              var words = str.split('+');      
                    
              if (words.length >= 1){              
                this.Param_Tag.tthd_id = words[0]
              }   
              
              if (words.length >= 2){              
                  this.Param_Tag.dsloaihd_id = words[1]
                  this.Param_Tag.dsloaihd_id = this.Param_Tag.dsloaihd_id.split(';').join(',')
              }
              
              if (words.length >= 3){                                         
                  this.Param_Tag.dsdichvuvt_id = words[2]
                  this.Param_Tag.dsdichvuvt_id = this.Param_Tag.dsdichvuvt_id.split(';').join(',')
              }
          }
          else if (this._Tag && this._Tag != null && this._Tag !== '' && this._Tag.length > 0) {
              console.log("Chay vao form nay tu form khac: " + this._Tag + " ||| " + this.matb_link + " ||| " + this._vquytrinh_id)
              this.Tag = decodeURIComponent(this._Tag.toString())
              str = this.Tag;
              var words = str.split('+');      
                    
              if (words.length >= 1){              
                this.Param_Tag.tthd_id = words[0]
              }   
              
              if (words.length >= 2){              
                  this.Param_Tag.dsloaihd_id = words[1]
                  this.Param_Tag.dsloaihd_id = this.Param_Tag.dsloaihd_id.split(';').join(',')
              }
              
              if (words.length >= 3){                                         
                  this.Param_Tag.dsdichvuvt_id = words[2]
                  this.Param_Tag.dsdichvuvt_id = this.Param_Tag.dsdichvuvt_id.split(';').join(',')
              }
          }
          else {
              this.Param_Tag.tthd_id = this.TrangThaiHD.DANG_THI_CONG;
              this.Param_Tag.dsloaihd_id = '1';
              this.Param_Tag.dsdichvuvt_id = '8';
          }
      }
      catch(ex)
      {
          this.Param_Tag.tthd_id = this.TrangThaiHD.DANG_THI_CONG;
          this.Param_Tag.dsloaihd_id = '1';
          this.Param_Tag.dsdichvuvt_id = '8';
      }
      console.log("tthd_id = " + this.Param_Tag.tthd_id)
      console.log("dsloaihd_id = " + this.Param_Tag.dsloaihd_id)
      console.log("dsdichvuvt_id = " + this.Param_Tag.dsdichvuvt_id)
    },
    getDmKieuHD: async function(){
      var input = { }
      let data = this.GetData(await api.getDmKieuHD(this.axios, input))
      if(data && data.length > 0)
        this.DmKieuHD = data.map((x)=>({id:x.kieuhd_id,text:x.kieu_hd}))
        this.kieuhd_id = data[0].kieuhd_id
    },
    getDmKenhThu: async function(){
      var input = { }
      let data = this.GetData(await api.getDmKenhThu(this.axios, input))
      if(data && data.length > 0)
        this.DmKenhThu = data.map((x)=>({id:x.kenhthu_id,text:x.kenhthu}))
        this.kenhthu_id = data[0].kenhthu_id
    },
    getDmLoaiHD: async function(){
      var input = { "loaiHdId": this.Param_Tag.dsloaihd_id }
      let data = this.GetData(await api.getDmLoaiHD(this.axios, input))
      if(data && data.length > 0)
        this.DmLoaiHD = data.map((x)=>({id:x.ID,text:x.NAME}))
        this.loaihd_id = data[0].ID
    },
   
    getDmQuyTrinh: async function(){
      var input = { "vloaihd_id": this.loaihd_id,"vtthd_id": this.Param_Tag.tthd_id,"vkieu_yc": 0,"vdichvuvt_id": this.Param_Tag.dsdichvuvt_id} //this.Param_Tag.dsdichvuvt_id
      let data = this.GetData(await api.getDmQuyTrinh(this.axios, input))
      if(data && data.length > 0){
        this.DmQuyTrinh = data.map((x)=>({id:x.quytrinh_id,text:x.quytrinh}))
        this.quytrinh_id = data[0].quytrinh_id
        this.getHuongGiaoTheoQT()   
      }else{
        this.DmQuyTrinh = null
        this.Clear()
      }
    },
    getHuongGiaoTheoQT: async function(){
       try {
        this.loading(true)
        var pdata = {"quytrinh_id":this.quytrinh_id,"tthd_id": this.Param_Tag.tthd_id}
        api.sp_lay_huonggiao_theo_quytrinh(this.axios, pdata).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                var dataApi = response.data.data
                if (dataApi.length > 0) {
                  this.huonggiao_id = dataApi[0].huonggiao_id
                  this.luong_id = dataApi[0].luong_id
                  this.header.title = dataApi[0].huonggiao != undefined ? dataApi[0].huonggiao.toUpperCase() : this.header.title
                  this.HienThiGiaoDien()               
                  this.getDsHopDongTB('0')
                  if (!this.PHAILAM("GIAOVIEC"))
                  {
                      this.chkNgayHT = true
                      this.ngaygvStatus = true
                      this.chkNgayGV = true
                      this.ngayhtStatus = true
                  }
                } else {
                  this.$toast.error("Không tìm thấy hướng giao")
                }            
          } else {      
              this.$toast.error(response.data.message) 
          }        
        });
      } finally {
        this.loading(false)
      } 
    },
    // get danh sach thao tac
    getDsThaoTac: async function(){
      var pdata = { "luong_id": this.luong_id }
        api.sp_lay_luong_thaotac(this.axios, pdata).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
             this.DsThaoTac.data =  response.data.data                
          }else{      
             this.DsThaoTac.data = null 
          }        
        });
    },
    HienThiGiaoDien(){
      this.$refs['tsbtnKQ_DoKiem'].style.display = 'none';
      this.$refs['tsbtnDuAn'].style.display = 'none';
      this.$refs['tsbtnHoanThienHS'].style.display = 'none';
      this.HoanThienStatus = false;     
      this.$refs['tsbtnTraPhieu'].style.display = 'none';
      this.$refs['tsbtnHoanCong'].style.display = 'none';
      this.$refs['tsbtnGiaoViec_KBB'].style.display = 'none';
      this.$refs['tsbtnSua_KenhThu'].style.display = 'none';
      this.$refs['tsbtnGiaoPhieu'].style.display = 'none';
      this.$refs['tsbtnVatTu'].style.display = 'none';
      this.$refs['tsbtnVatTuMoi'].style.display = 'none';
      this.$refs['tsbtnTinh_GiaoNET_NT'].style.display = 'none';
      this.$refs['tsbtnHoanThanh'].style.display = 'none';
      this.ChonPortTDongStatus = false;
      this.ChonPortStatus = false
      
      try {
        var pdata = { "luong_id": this.luong_id }
        api.sp_lay_luong_thaotac(this.axios, pdata).then((response) => {
          if(response && response.data && response.data.data.length > 0 && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
            this.DsThaoTac.data =  response.data.data 
            // lay danh sach button
            let thaotac = this.DsThaoTac.data
            thaotac.forEach(async(item) => {
              var data = { thaotac_id: item.thaotac_id }      
              let response_ct = await this.axios.post( "/web-thicong/hoancongmegawan/sp_lay_ct_thaotac_control",data)
              if(response_ct && response_ct.data && response_ct.data.data && response_ct.data.error_code && response_ct.data.error_code === 'BSS-00000000'){
                  var control_name = response_ct.data.data[0].control_name
                  if (this.$refs[control_name]) {
                    let ref = this.$refs[control_name]
                    let type = ref.nodeName
                    if (type == 'LI') {
                      if (item.enable == 0) {
                        ref.style.display = 'none';
                      } else {
                        ref.style.display = 'block';
                      }
                    }
                    if (type == 'A') {
                      if (item.enable == 0) {
                        ref.style.display = 'none';
                      } else {
                        ref.style.display = 'block';
                      }
                    }
                    if (type == 'BUTTON') {
                      if (item.enable == 0) {
                        if (control_name == "btnChonPort_TuDong") {
                          this.ChonPortTDongStatus = false
                        } else if (control_name == "btnChonPort") {
                          this.ChonPortStatus = false
                        }
                      } else {
                        if (control_name == "btnChonPort_TuDong") {
                          this.ChonPortTDongStatus = true
                        } else if (control_name == "btnChonPort") {
                          this.ChonPortStatus = true
                        }
                      }
                    }
                  }
              }
            })      
          }     
        });
      }
      catch(e){
        this.$toast.error(JSON.stringify(e.data))
      }
    },
    PHAILAM(code){
      for (let i=0; i<this.DsThaoTac.data.length; i++) {
          if (this.DsThaoTac.data[i].code == code && this.DsThaoTac.data[i].enable == "1") {
              return true;
          }
      }        
      return false;    
    },
    getDsHopDongTB: async function(ma_gd) {
      try {
        this.loading(true)
        var pdata = {
            "vcaidat_dv": 0,
            "vhuonggiao_id": this.huonggiao_id,
            "vkieu_id": 0,
            "vloai_id": this.rdLoaiPhieu,
            "vloaihd_id": this.loaihd_id,
            "vma_gd": ma_gd,
            "vphieu_id": 0,
            "vtthd_id": this.Param_Tag.tthd_id
        }
        await api.getDsHopDongTB(this.axios, pdata).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                if (response.data.data.length > 0) {
                  var dataApi = response.data.data
                  dataApi.forEach((item) => {
                      item.ngay_yc_convert = item.ngay_yc
                      try {            
                        if (item.ngay_yc != undefined && item.ngay_yc != "") {
                          item.ngay_yc_convert = moment(item.ngay_yc, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY")
                        }          
                      } catch (e) {}    
                  })
                  this.DsThueBao.data = dataApi  
                } else {
                  this.Clear()
                }
                  
                //this.HienThiTTHopDongTB(this.DsThueBao.data)
          } else {           
                this.DsThueBao.data = []
                this.Clear()
                if (response && response.data && response.data.message && response.data.message != "No Content") {
                  this.$toast.error(response.data.message)
                }
          }        
        });
      } finally {
        this.loading(false)
      }
    },
    Clear() {
        this.ThongTinHC.txtSoLuongIP = "";
        this.ThongTinHC.tramtb_id = 0
        this.ThongTinKH.txtSoDTLH = "";
        this.ThongTinHC.txtDiaChiKN = "";
        this.ThongTinKH.txtKieuLD = "";
        this.ThongTinHC.txtSiteID = "";
        this.ThongTinHC.txtTenNV_HC = "";
        this.ThongTinKH.txtMaTB = "";
        this.ThongTinKH.txtTenNV_YC = "";
        this.ThongTinKH.txtNVTiepThi = "";
        this.ThongTinKH.txtSoAoWan = "";
        this.ThongTinKH.txtMaGD = "";
        this.ThongTinKH.txtTenTB = "";
        this.ThongTinKH.txtKhuVuc = "";
        this.ThongTinKH.txtDiaChiLD = "";
        this.ThongTinKH.txtDiaChiLDCu = "";
        this.ThongTinHC.txtMaTB_TN = "";
        this.ThongTinHC.txtHuongKN = "";
        this.ThongTinHC.txtDiaChiKN = "";
        this.ThongTinHC.txtNDGiao = "";
        this.ThongTinHC.txtThongTinTra = "";
        this.ThongTinHC.txtLyDoTra = "";
        this.ThongTinHC.txtNoiDungTH = "";
        // cboRouter.SelectedIndex = 0;
        this.ThongTinHC.txtSlotPE = "";
        this.ThongTinHC.txtPortPE = "";
        this.ThongTinHC.txtRoute = "";
        this.ThongTinHC.txtLanIP = "";
        this.ThongTinHC.txtWanIP = "";
        this.ThongTinHC.txtDsLam = "";
        this.ThongTinHC.txtIPADSL = "";
        this.thietbi_id = ""
        this.ThongTinHC.txtADSLport = "";
        this.ThongTinHC.txtNasportID = "";
        this.ThongTinHC.txtPort = "";
        this.ThongTinHC.txtPortMDF = "";
        this.ThongTinHC.txtSvLan = "";
        this.ThongTinHC.txtCvLan = "";
        this.ThongTinHC.txtTuCapGoc = "";
        this.ThongTinHC.txtDoiCapGoc = "";
        this.ThongTinHC.txtCapGoc = "";
        this.ThongTinHC.txtHopCapNgon = "";
        this.ThongTinHC.txtTuCapNgon = "";
        this.ThongTinHC.txtDoiCapNgon = "";
        this.ThongTinHC.txtCapNgon = "";
        this.ThongTinHC.txtTuyenCap = "";
        this.ThongTinHC.txtLienTu = "";
        this.ThongTinHC.txtCuLy = "0";
        this.ThongTinHC.txtVRF = "";
        this.ThongTinHC.txtLink = "";
        this.ThongTinHC.txtSerial = "";
        this.ThongTinKH.txtTongTien = "0";
        this.ThongTinHC.txtGhiChu = "";
        this.nguoigv_id = 0;
        this.chkNgayGV = false;
        this.chkNgayHT = false;
        this.ngaygv = moment(new Date()).format('DD/MM/YYYY hh:mm A')
        this.DsNhanVienTH.data = []
        this.ThongTinHC.txtLapKem = "";
        this.DsThueBao.hdkh_id = 0
        this.DsThueBao.hdtt_id = 0
        this.DsThueBao.khachhang_id = 0
        this.DsThueBao.thanhtoan_id = 0  
        $("#wbrTSKT").html("")
    },
    DsLam_OnClick: async function(){
      this.getDsLam()
    },  
    // lay thong tin DsLam
    getDsLam: async function(){
       var pdata = { "port_id": this.ThongTinHC.port_id  }
        api.getDsLam(this.axios, pdata).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                var dataApi = response.data.data                
                this.DsLam.data = dataApi                  
          }else{      
                this.DsLam.data = null
          }        
        });
    },
    HienThiTTHopDongTB: async function(args){
      if (args && args != null && args != ""){       
          console.log("HienThiTTHopDongTB")   
          console.log(args)
          this.DsThueBao.hdtb_id = args.hdtb_id;
          this.DsThueBao.phieu_id =  args.phieu_id;
          this.DsThueBao.hdkh_id = args.hdkh_id;
          //Lấy thông tin liên hệ lắp đặt thuê bao
          this.ThongTinKH.txtSoDTLH = args.so_dt;
          
          var pdata = { "hdkh_id": this.DsThueBao.hdkh_id, "hdtb_id": this.DsThueBao.hdtb_id}
          await api.getThongTinLH(this.axios, pdata).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                  var dataApi = response.data.data            
                  this.ThongTinHC.txtTenNV_HC = dataApi               
            }else{      
                  this.ThongTinHC.txtTenNV_HC = ''
            }        
          });
          // Lấy thông tin tổng tiền
          var pdata_tt = { "hdkh_id": this.DsThueBao.hdkh_id}
          await api.getThongTongTien(this.axios, pdata_tt).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                  var dataApi = response.data.data              
                  this.ThongTinKH.txtTongTien = this.formatPrice(dataApi[0].tongtien)
            }else{      
                  this.ThongTinKH.txtTongTien = 0
            }        
          });
          // Lấy thông tin nhân viên YC
          this.DsThueBao.nhanvien_id = args.nhanvien_id;
          var pdata_nvyc = { "nhanvien_id": this.DsThueBao.nhanvien_id}
          await api.getThongTinNVYC(this.axios, pdata_nvyc).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                  var dataApi = response.data.data              
                  this.ThongTinKH.txtTenNV_YC = dataApi[0].NAME
            }else{      
                  this.ThongTinKH.txtTenNV_YC = ''
            }        
          });
          // Lấy thông tin nhân vien tiếp thị
          this.DsThueBao.ctv_id = args.ctv_id != null ? args.ctv_id: 0;
          var pdata_nvtt = { "nhanvien_id": this.DsThueBao.ctv_id}
          await api.getThongTinNV_TiepThi(this.axios, pdata_nvtt).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                  var dataApi = response.data.data              
                  this.ThongTinKH.txtNVTiepThi = dataApi[0].nhanvien
            }else{      
                  this.ThongTinKH.txtNVTiepThi = ''
            }        
          });
          // Lấy thông tin kiểu hợp đồng
          if(args.kieuhd_id && args.kieuhd_id != ''){
              this.kieuhd_id = args.kieuhd_id
          }else{
              this.kieuhd_id = this.Kieu_HD.TAI_GD*1
          }
          // Lấy thông tin kênh thu
          var pdata_kenhthu = { "hdkh_id": this.DsThueBao.hdkh_id}
          await api.getThongTinKenhThu(this.axios, pdata_kenhthu).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                  var dataApi = response.data.data
                  this.kenhthu_id = dataApi[0].kenhthu_id
            }else{      
                  this.kenhthu_id = null
            }        
          });
          //
          let chkHen_id = await this.fn_tt_hdtb_hen(1, this.DsThueBao.hdtb_id);
          if (String(chkHen_id) != "-1")
              $("#chkHen").prop("checked", true)
          else
              $("#chkHen").prop("checked", false)
          // Lấy thông tin
          this.ThongTinKH.txtKieuLD = args.ten_kieuld != null ? args.ten_kieuld : ''
          this.ThongTinHC.txtSiteID = args.site_id != null ? args.site_id : ''
          // Lấy thông tin trả
          var pdata_phieutra = { "phieu_id": this.DsThueBao.phieu_id}
          await api.getThongTinPhieuTra(this.axios, pdata_phieutra).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                  var dataApi = response.data.data
                  this.ThongTinHC.txtThongTinTra = dataApi[0].nd_tra != null ? dataApi[0].nd_tra : ''
                  var lydotra_id = dataApi[0].lydotra_id != null ? dataApi[0].lydotra_id : 0
                  // Lý do trả
                  var pdata_lydotra = { "param": lydotra_id,
                                        "type": 1 }
                  api.getThongTinLyDoTra(this.axios, pdata_lydotra).then((response) => {
                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                          var dataApi = response.data.data
                          this.ThongTinHC.txtLyDoTra = dataApi[0].kenhthu_id
                    }else{      
                          this.ThongTinHC.txtLyDoTra = ''
                    }        
                  });
            }else{      
                  this.ThongTinHC.txtThongTinTra = '' 
                  this.ThongTinHC.txtLyDoTra = '' 
            }        
          });     
          // Lấy thông tin hdkh_id,hdtt_id,khachhang_id,thanhtoan_id
          this.DsThueBao.hdkh_id = args.hdkh_id != null ? args.hdkh_id : 0
          this.DsThueBao.hdtt_id = args.hdtt_id != null ? args.hdtt_id : 0
          this.DsThueBao.khachhang_id = args.khachhang_id != null ? args.khachhang_id : 0
          this.DsThueBao.thanhtoan_id = args.thanhtoan_id != null ? args.thanhtoan_id : 0
          this.DsThueBao.dichvuvt_id = args.dichvuvt_id != null ? args.dichvuvt_id : 0
          this.DsThueBao.kieuld_id = args.kieuld_id != null ? args.kieuld_id : 0          
          // Lấy thông tin MaGD, MaTB,TenTB...
          this.ThongTinKH.txtMaGD =  args.ma_gd != null ? args.ma_gd : ''       
          this.ThongTinKH.txtMaTB =  args.ma_tb != null ? args.ma_tb : ''
          this.ThongTinKH.txtTenTB =  args.ten_tb != null ? args.ten_tb : ''
          this.ThongTinKH.txtKhuVuc = args.ten_kv != null ? args.ten_kv : ''
          this.ThongTinKH.txtLoaiHinh =  args.loaihinh_tb != null ? args.loaihinh_tb : ''          
          if(this.loaihd_id != this.LoaiHopDong.DAT_MOI){
            this.ThongTinKH.thuebao_id = args.thuebao_id != null ? args.thuebao_id : 0    
          }
          if(!args.giukenh && args.giukenh == "" && args.giukenh == "-1"){
            this.ThongTinHC.LuongGiuKenhStatus = false
          }else{
            this.ThongTinHC.LuongGiuKenhStatus = true
            this.ThongTinHC.chkLuongGiuKenh = (args.giukenh == "1")
          }
          // Lấy thông tin địa chỉ LĐ
          this.ThongTinKH.diachild_id = args.diachild_id != null ? args.diachild_id : 0
          this.ThongTinKH.txtDiaChiLD = args.diachi_ld != null ? args.diachi_ld : ''
          if (this.loaihd_id != LoaiHopDong.DAT_MOI){
            this.ThongTinKH.txtDiaChiLDCu = args.diachi_ld != null ? args.diachi_ld : ''
          }else{
            this.ThongTinKH.txtDiaChiLDCu = ''
          }
          this.ThongTinHC.txtMaTB_TN = args.matb_tn != null ? args.matb_tn : ''
          this.ThongTinKH.txtSoAoWan = args.ma_lt != null ? args.ma_lt : ''
          this.ThongTinHC.txtNoiDungTH = args.nd_thuchien != null ? args.nd_thuchien : '' 
          
          this.tocdocir_id = args.tocdo_id != null ? args.tocdo_id : 0
          if(args.tocdo_pir_id && args.tocdo_pir_id != ''){
            this.DmTocDoPIRStatus = true
            this.chkTD_PIR = true
            this.tocdopir_id = args.tocdo_pir_id
          }else{
            this.DmTocDoPIRStatus = false
            this.chkTD_PIR = false
            this.tocdopir_id = null
          }
          if(args.tocdo_nix && args.tocdo_nix != ''){
            this.DmTocDoNIXStatus = true
            this.chkTD_NIX = true
            this.tocdonix_id = args.tocdo_nix
          }else{
            this.DmTocDoNIXStatus = false
            this.chkTD_NIX = false
            this.tocdonix_id = null
          }
          if(args.tocdo_isp && args.tocdo_isp != ''){
            this.DmTocDoISPStatus = true
            this.chkTD_ISP = true
            this.tocdoisp_id = args.tocdo_isp
          }else{
            this.DmTocDoISPStatus = false
            this.chkTD_ISP = false
            this.tocdoisp_id = null
          }
          
          this.ThongTinHC.txtSoLuongIP = args.sl_ip != null ? args.sl_ip : ''
          this.modem_id = args.loaimd_id != null ? args.loaimd_id : 0
          this.loaikenh_id  = args.loaikenh_id != null ? args.loaikenh_id : 0
          this.loaitbi_id  = args.thietbidc_id != null ? args.thietbidc_id : 0
          // Hiển thị Hướng KN
          if(args.loainode_id && args.loainode_id != ""){
            this.loainode_id = args.loainode_id
            if (this.PHAILAM("HUONG_KN")){
              if(this.loainode_id == this.LoaiNode_MGWan.NODE_PHU){
                this.ThongTinHC.HuongKNStatus = true
              }else{
                this.ThongTinHC.HuongKNStatus = false
              }     
            }else{
              this.ThongTinHC.HuongKNStatus = false
            }
          }
          // Hiển thị đối tác
          if(args.doitac_id && args.doitac_id != ""){
            this.chkDoiTac = true
            this.DmDoiTacStatus = true
            this.doitac_id = args.doitac_id
          }else{
            this.chkDoiTac = false
            this.DmDoiTacStatus = false
          }
          // Hiển thị control
          this.DsThueBao.loaihinh_tb = args.loaitb_id != null ? args.loaitb_id: 0
          this.HienThiControl(this.DsThueBao.loaihinh_tb)
          // Hiển thị thông tin
          this.ThongTinHC.txtTuyenCap = args.tuyencap != null ? args.tuyencap: ''
          this.ThongTinHC.txtLienTu = args.lientu != null ? args.lientu: ''
          this.ThongTinHC.txtTuCapNgon = args.tucap_p != null ? args.tucap_p: ''
          this.ThongTinHC.txtDoiCapNgon = args.doicap_p != null ? args.doicap_p: ''
          this.ThongTinHC.txtHopCapNgon = args.hopcap_p != null ? args.hopcap_p: ''
          if (args.loaicap_id && args.loaicap_id != ""){
            this.loaicap_id = args.loaicap_id
          }
          this.ThongTinHC.txtCapGoc = args.cap_goc != null ? args.cap_goc: ''
          this.ThongTinHC.txtCapNgon = args.cap_p != null ? args.cap_p: ''
          this.ThongTinHC.txtDoiCapGoc = args.doicap_goc != null ? args.doicap_goc: ''
          this.ThongTinHC.txtTuCapGoc = args.tucap_goc != null ? args.tucap_goc: ''
          this.ThongTinHC.txtCuLy = args.culy != null ? args.culy: '0'
          // Lấy thông tin thuê bao lắp kèm
          this.ThongTinHC.madoicap = args.madoicap != null ? args.madoicap: 0
          var pdata_lapkem = { "vhdtb_id": this.DsThueBao.hdtb_id,"vmadoicap": this.ThongTinHC.madoicap}
          await api.getThongTinLapKem(this.axios, pdata_lapkem).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
              var dataApi = response.data.data
              this.ThongTinHC.txtLapKem = dataApi
            }else{      
              this.ThongTinHC.txtLapKem = ''
            }        
          });
          this.ThongTinHC.txtDiaChiKN = args.diachi_kn != null ? args.diachi_kn: ''
          // Lấy thông tin huong_kn
          this.ThongTinHC.huongkn = args.huong_kn != null ? args.huong_kn: 0
          this.ThongTinHC.tinh_kn_id = args.tinh_kn_id != null ? args.tinh_kn_id: 0
          this.ThongTinHC.txtHuongKN = args.huong_kn != null ? args.huong_kn: ''
          // Lấy thông tin Modem, Loại kênh, Thời hạn, Trang bị, Router
          this.modem_id = args.loaimd_id != null ? args.loaimd_id: null
          this.loaikenh_id = args.loaikenh_id != null ? args.loaikenh_id: null
          this.chuquan_id = args.chuquan_id != null ? args.chuquan_id: null
          this.thoihan_id = args.thoihan_id != null ? args.thoihan_id: null
          this.trangbi_id = args.trangbi_id != null ? args.trangbi_id: null
          
          if (args.pe_id && args.pe_id != 0){
            this.router_id = args.pe_id
          }
          this.ThongTinHC.txtPortPE = args.port_pe != null ? args.port_pe: ''
          this.ThongTinHC.txtSlotPE = args.slot_pe != null ? args.slot_pe: ''
          this.ThongTinHC.txtWanIP = args.wan_ip != null ? args.wan_ip: ''
          this.ThongTinHC.txtLanIP = args.lan_ip != null ? args.lan_ip: ''
          this.thietbi_id = (this.DmThietBi.filter(x=>x.text == args.ten_tbi).length > 0 ? this.DmThietBi.filter(x=>x.text == args.ten_tbi)[0].id : ''),
          this.ThongTinHC.txtSvLan = args.svlan != null ? args.svlan: ''
          this.ThongTinHC.txtCvLan = args.cvlan != null ? args.cvlan: ''
          this.ThongTinHC.txtVRF = args.vrf != null ? args.vrf: ''
          this.ThongTinHC.txtSerial = args.serial != null ? args.serial: ''
          this.ThongTinHC.txtLink = args.link != null ? args.link: ''
          this.ThongTinHC.txtRoute = args.route != null ? args.route: ''
          this.ThongTinHC.txtNDgiao = args.nd_giao != null ? args.nd_giao: ''
          this.ThongTinHC.txtGhiChu = args.ghichu_tb != null ? args.ghichu_tb: ''
          // Lấy thông tin donvi_id
          var pdata_donvi = { "param": this.DsThueBao.hdtb_id,
                              "type": 1 }
          await api.getThongTinDonVi(this.axios, pdata_donvi).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
              var dataApi = response.data.data
              this.ThongTinHC.donvi_id = dataApi
            }else{      
              this.ThongTinHC.donvi_id = 0
            }        
          });
          // Lấy danh sách IP theo mã port
          if (args.port_id != undefined && args.port_id != null){
            var port_id = args.port_id != null ? args.port_id: 0
            var vci_vpi_id = args.vci_vpi_id != null ? args.vci_vpi_id: 0
            this.ThongTinHC.port_id = port_id
            this.ThongTinHC.vci_vpi_id = vci_vpi_id
            var pdata_ip = { "port_id": port_id,"vci_vpi_id": vci_vpi_id }
            await api.getThongTinIP(this.axios, pdata_ip).then((response) => {
              if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                var dataApi = response.data.data
                this.ThongTinHC.txtIPADSL = dataApi[0].ip
                this.ThongTinHC.txtDsLam = dataApi[0].tendslam
                this.ThongTinHC.txtADSLport = dataApi[0].adsl_port
                if (dataApi[0].selfid01 && dataApi[0].selfid01 != ''){
                  this.ThongTinHC.txtNasportID = dataApi[0].system + ' /' + dataApi[0].selfid04 + ' /' + dataApi[0].selfid03 + ' /' + dataApi[0].selfid02 + ' /' + dataApi[0].selfid01
                }else{
                  this.ThongTinHC.txtNasportID = dataApi[0].system
                }
                this.ThongTinHC.txtPort = dataApi[0].port != null ? dataApi[0].port: ''
                this.ThongTinHC.txtPortMDF = dataApi[0].port_mdf != null ? dataApi[0].port_mdf: ''
                // Lấy thông tin trạm TB
                var tramtb_id = dataApi[0].donvi_id != null ? dataApi[0].donvi_id: 0
                var pdata_tramid = {  "param": tramtb_id,
                                      "type": 2 }
                api.getThongTinTram(this.axios, pdata_tramid).then((response) => {
                  if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                    var dataApi = response.data.data
                    this.ThongTinHC.txtTramTB = dataApi
                  }else{      
                    this.ThongTinHC.txtTramTB = ''
                  }        
                });
              }else{      
                this.ThongTinHC.txtIPADSL = ''
                this.ThongTinHC.txtDsLam = ''
                this.ThongTinHC.txtADSLport = ''
                this.ThongTinHC.txtNasportID = ''
                this.ThongTinHC.txtPort = ''
                this.ThongTinHC.txtPortMDF = ''
                this.ThongTinHC.txtTramTB = ''
              }        
            });
          }else{
              this.ThongTinHC.txtIPADSL = ''
              this.ThongTinHC.txtDsLam = ''
              this.ThongTinHC.txtADSLport = ''
              this.ThongTinHC.txtNasportID = ''
              this.ThongTinHC.txtPort = ''
              this.ThongTinHC.txtPortMDF = ''
              this.ThongTinHC.txtTramTB = ''
              this.ThongTinHC.port_id = 0
          }
          // Lấy danh sách nhân viên thực hiện
          this.HienThiDSNV()
          this.chkNgayHT = true
          this.ngayhtStatus = true
          this.ngayht = args.ngay_ht_gp != null ? moment(args.ngay_ht_gp).format('DD/MM/YYYY hh:mm A').toString(): moment(new Date()).format('DD/MM/YYYY hh:mm A')
          // Ẩn hiện btn Giao phiếu
          if (this.PHAILAM("GIAOPHIEU"))
          {
            if (args.ngay_ht_gp != undefined && args.ngay_ht_gp != null && args.ngay_ht_gp != "")
                this.GiaoPhieuStatus = true;
            else
                this.GiaoPhieuStatus = false;
          }
          if (this.PHAILAM("CAPNHAT_THONGSO_KYTHUAT_WAN"))
          {
              this.ChonPortStatus = true;
              if (this.PHAILAM("CAPNHAT_PORT_TUDONG"))
                  this.ChonPortTDongStatus = true;          
          }
          else
          {
              this.ChonPortStatus = false;
              this.ChonPortTDongStatus = false;
              if (this.PHAILAM("CAPNHAT_PORT"))
                  this.ChonPortStatus = true;
              if (this.PHAILAM("CAPNHAT_PORT_TUDONG"))
                  this.ChonPortTDongStatus = true;
          }
          if (this.PHAILAM("HOANTHIENHOSO"))
          {
              if (args.ngay_ht_gp != undefined && args.ngay_ht_gp != null && args.ngay_ht_gp != "")
                 this.HoanThienStatus = true;
              else
                 this.HoanThienStatus = false;
          }
          if (this.PHAILAM("HOANCONG"))
          {
              if (args.ngay_ht_gp != undefined && args.ngay_ht_gp != null && args.ngay_ht_gp != "")
                  this.HoanCongStatus = true;
              else
                  this.HoanCongStatus = false;
          }
          if (!this.PHAILAM("GIAOVIEC"))
          {
              this.chkNgayHT = true;
              this.ngayhtStatus = true
               this.chkNgayGV = true
               this.ngaygvStatus = false
          }
          // Lấy danh sách dịch vu dki
          this.HienThiDVGT()
          // Hiển thị thông số kỹ thuật -- 1560
          this.HIEN_THONGSO_KYTHUAT()
      }
    },
    HienThiDSNV(){
      var pdata_nv = { "phieu_id": this.DsThueBao.phieu_id, "kieu_id": 2 }
      api.getDsNhanVien(this.axios, pdata_nv).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
          var dataApi = response.data.data
          this.DsNhanVienTH.data = dataApi
          
          this.chkNgayGV = true
          this.ngaygvStatus = true
          this.ngaygv = moment(dataApi[0].ngaygiao, 'DD/MM/YYYY hh:mm:ss').format('DD/MM/YYYY hh:mm A')
          this.nguoigv_id = dataApi[0].nhanvien_giao_id
          this.DsNhanVienTH.nhanvien_tc_id = dataApi[0].nhanvien_th_id
          this.DsNhanVienTH.congviec_th = dataApi[0].nhiemvu
        }else{      
          this.DsNhanVienTH.data = []
          if (!this.PHAILAM("GIAOVIEC")){
            this.chkNgayGV = true
            this.ngaygvStatus = true
          }              
          else{
            this.chkNgayGV = false
            this.ngaygvStatus = false
          }        
          this.nguoigv_id = this.$root.token.getNhanVienID()
          this.DsNhanVienTH.nhanvien_tc_id = 0
          this.DsNhanVienTH.congviec_th = ''   
        }        
      });
    },
    HienThiDVGT(){
      var pdata_nv = { "vhdtb_id": this.DsThueBao.hdtb_id }
      api.getDsDichVuDK(this.axios, pdata_nv).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
          var dataApi = response.data.data
          this.DsDichVuDK.data = dataApi              
        }else{               
         this.DsDichVuDK.data = []
        }        
      });
    },
    HienThiControl(loaitb_id){
      console.log("HienThiControl: " + loaitb_id)
      if (this.PHAILAM("CAPNHAT_THONGSO_KYTHUAT_WAN"))
            {
                if (loaitb_id == this.LoaiHinhTB.MEGAWAN_ADSL || loaitb_id == this.LoaiHinhTB.MEGAWAN_SHDSL || loaitb_id == this.LoaiHinhTB.MEGAWAN_QUANG_FE || loaitb_id == this.LoaiHinhTB.MEGAWAN_QUANG_GE)
                {
                    this.ThongTinHC.DsLamStatus = false //txtDSLAMWan.Enabled = true;
                    this.ThongTinHC.NasportIDStatus = false //txtIPADSL.Enabled = true;
                    this.ThongTinHC.ADSLportStatus = false //txtAdsl_Port.Enabled = true;
                    this.ThongTinHC.SvLanStatus = false //txtSvLan.Enabled = true;
                    this.ThongTinHC.CvLanStatus = false //txtCvLan.Enabled = true;
                    this.ThongTinHC.DmThietBiStatus = false //cbotext_Tbi.Enabled = true;
                    this.ThongTinHC.LanIPStatus = false //txtLAN_IP.Enabled = true;
                    this.ThongTinHC.WanIPStatus = false //txtWAN_IP.Enabled = true;
                    this.ThongTinHC.PortMDFtatus = false //txtPortMDF.Enabled = true;                   
                }
                if (loaitb_id == this.LoaiHinhTB.METRONET_FE || loaitb_id == this.LoaiHinhTB.METRONET_GE)
                {
                    this.ThongTinHC.DmThietBiStatus = false //cbotext_Tbi.Enabled = true;
                    this.ThongTinHC.DsLamStatus = false //txtDSLAMWan.Enabled = true;
                    this.ThongTinHC.NasportIDStatus = false //txtIPADSL.Enabled = true;
                    this.ThongTinHC.ADSLportStatus = false //txtAdsl_Port.Enabled = true;
                    this.ThongTinHC.SvLanStatus = false //txtSvLan.Enabled = true;
                    this.ThongTinHC.CvLanStatus = false //txtCvLan.Enabled = true;
                    this.ThongTinHC.PortMDFtatus = false //txtPortMDF.Enabled = true;  
                }
            }
            if (this.PHAILAM("SERIAL_MGW"))
                this.ThongTinHC.SerialStatus = false
            else
                this.ThongTinHC.SerialStatus = true
            if (loaitb_id == this.LoaiHinhTB.MEGAWAN_ADSL || loaitb_id == this.LoaiHinhTB.MEGAWAN_SHDSL || loaitb_id == this.LoaiHinhTB.MEGAWAN_ADSL_2M)
                this.ThongTinHC.lblDsLam = "Dslam";
            else
                this.ThongTinHC.lblDsLam = "Switch";
    },
    MaGD_KeyUp:async function() {
      try{
        this.Loading =true
        if (this.ThongTinKH.txtMaGD && this.ThongTinKH.txtMaGD != ''){
          this.getDsHopDongTB(this.ThongTinKH.txtMaGD)
        }             
      }
      catch(ex){
        this.Loading =false
      }
    },
    MaTB_KeyUp:async function() {
      try{
        this.Loading =true
        if (this.ThongTinKH.txtMaTB && this.ThongTinKH.txtMaTB != ''){
          this.getDsHopDongTB(this.ThongTinKH.txtMaTB)
        }    
      }
      catch(ex){
        this.Loading =false
      }
    },
    getDmNguoiGV: async function(){
      var input = { }
      let data = this.GetData(await api.getDmNguoiGV(this.axios, input))
      if(data && data.length > 0)
        this.DmNguoiGV = data.map((x)=>({id:x.nhanvien_id,text:x.ten_nv}))
        this.nguoigv_id = data[0].nhanvien_id
    },
    getDmThoiHan: async function(){
      var input = { "nghiepvu":"ds_thoihan","ds_para":""}
      let data = this.GetData(await api.getDmThoiHan(this.axios, input))
      if(data && data.length > 0)
        this.DmThoiHan = data.map((x)=>({id:x.thoihan_id,text:x.tenthoihan}))
        this.thoihan_id = data[0].thoihan_id
    },
    getDmLoaiKenh: async function(){
      var input = { "p_param":"", "p_type": 1 }
      let data = this.GetData(await api.sp_tt_loai_kenh(this.axios, input))
      if(data && data.length > 0)
        this.DmLoaiKenh = data.map((x)=>({id:x.loaikenh_id,text:x.loai_kenh}))
        this.loaikenh_id = data[0].loaikenh_id
    },
    getDmMoDem: async function(){
      var input = { "p_param":"", "p_type": 1 }
      let data = this.GetData(await api.sp_tt_loai_modem(this.axios, input))
      if(data && data.length > 0)
        this.DmMoDem = data.map((x)=>({id:x.loaimd_id,text:x.loai_md}))
        this.modem_id = data[0].loaimd_id
    },
    getDmRouter: async function(){
      var input = { "p_param": "", "p_type": 1 }
      let data = this.GetData(await api.sp_tt_pe(this.axios, input))
      if(data && data.length > 0)
        this.DmRouter = data.map((x)=>({id:x.pe_id,text:x.pe}))
        this.router_id = data[0].pe_id
    },
    getDmTocDoCIR: async function(){
      var input = { "p_param": "", "p_type": 2 }
      let data = this.GetData(await api.sp_tt_tocdo_kenh(this.axios, input))
      if(data && data.length > 0)
        this.DmTocDoCIR = data.map((x)=>({id:x.tocdo_id,text:x.toc_do}))
        // this.tocdocir_id = data[0].tocdo_id
    },
    getDmTocDoNIX: async function(){
      var input = { "p_param": "", "p_type": 2 }
      let data = this.GetData(await api.sp_tt_tocdo_kenh(this.axios, input))
      if(data && data.length > 0)
        this.DmTocDoNIX = data.map((x)=>({id:x.tocdo_id,text:x.toc_do}))
        // this.tocdonix_id = data[0].tocdo_id
    },
    getDmTocDoPIR: async function(){
      var input = { "p_param": "", "p_type": 2 }
      let data = this.GetData(await api.sp_tt_tocdo_kenh(this.axios, input))
      if(data && data.length > 0)
        this.DmTocDoPIR = data.map((x)=>({id:x.tocdo_id,text:x.toc_do}))
        // this.tocdopir_id_id = data[0].tocdo_id
    },
    getDmTocDoISP: async function(){
      var input = { "p_param": "", "p_type": 2 }
      let data = this.GetData(await api.sp_tt_tocdo_kenh(this.axios, input))
      if(data && data.length > 0)
        this.DmTocDoISP = data.map((x)=>({id:x.tocdo_id,text:x.toc_do}))
        // this.tocdoisp_id = data[0].tocdo_id
    },
    getDmTrangBi: async function(){
      var input = { "p_param": "", "p_type": 1 }
      let data = this.GetData(await api.sp_tt_trangbi(this.axios, input))
      if(data && data.length > 0)
        this.DmTrangBi = data.map((x)=>({id:x.trangbi_id,text:x.tentrangbi}))
        this.trangbi_id = data[0].trangbi_id
    },
    getDmLoaiNode: async function(){
      var input = {}
      let data = this.GetData(await api.getDmLoaiNode(this.axios, input))
      if(data && data.length > 0)
        this.DmLoaiNode = data.map((x)=>({id:x.node_id,text:x.loai_node}))
        this.loainode_id = data[0].node_id
    },
    getDmLoaiTBi: async function(){
      var input = { "nghiepvu": "ds_loai_tb_mgwan","ds_para": ""}
      let data = this.GetData(await api.getDmLoaiTBi(this.axios, input))
      if(data && data.length > 0)
        this.DmLoaiTBi = data.map((x)=>({id:x.thietbidc_id,text:x.ten_tbi != null ? x.ten_tbi : ""}))
        this.loaitbi_id = data[0].thietbidc_id
    },
    getDmChuQuan: async function(){
      var input = { "p_param": "", "p_type": 1 }
      let data = this.GetData(await api.sp_tt_chuquan(this.axios, input))
      if(data && data.length > 0)
        this.DmChuQuan = data.map((x)=>({id:x.chuquan_id,text:x.tenchuquan}))
        this.chuquan_id = data[0].chuquan_id
    },
    getDmThietBi: async function(){
      var input = { "nghiepvu": "DS_THIETBI_DAUCUOI","ds_para": ""}
      let data = this.GetData(await api.getDmThietBi(this.axios, input))
      if(data && data.length > 0)
        this.DmThietBi = data.map((x)=>({id:x.thietbidc_id,text:x.ten_tbi}))
        this.thietbi_id = data[0].thietbidc_id
    },
    getDmLoaiCap: async function(){
      var input = { }
      let data = this.GetData(await api.getDmLoaiCap(this.axios, input))
      if(data && data.length > 0)
        this.DmLoaiCap = data.map((x)=>({id:x.loaicap_id,text:x.loaicap}))
        this.loaicap_id = data[0].loaicap_id
    },
    getDmDoiTac: async function(){
      var input = { "p_param": "", "p_type": 1 }
      let data = this.GetData(await api.sp_tt_doitac(this.axios, input))
      if(data && data.length > 0)
        this.DmDoiTac = data.map((x)=>({id:x.doitac_id,text:x.ten_dt}))
        this.doitac_id = data[0].doitac_id
    },
    showPopupSearchContract(){   
      this.$refs.popupSearchContract.showModal();
    },
    acceptSearchContract(item){
      this.ThongTinKH.txtMaGD = item.ma_gd
      if(this.ThongTinKH.txtMaGD && this.ThongTinKH.txtMaGD.trim()!=""){
        this.MaGD_KeyUp()      
      }          
    },
    getDiaChi:function(data,datatype) {
      this[datatype].DIACHI = data.diachimoi
      this[datatype].TINH_ID = data.tinh_id
      this[datatype].QUAN_ID = data.quan_id
      this[datatype].PHUONG_ID = data.phuong_id
      this[datatype].PHO_ID = data.pho_id
      this[datatype].AP_ID = data.ap_id
      this[datatype].KHU_ID = data.khu_id
      this[datatype].DAC_DIEM_ID = data.dacdiem_id
      this[datatype].SO_NHA = data.so_nha
    },
    getDiaChiLD: function (data) {
      console.log(data)
      this.getDiaChi(data,'diaChiLD');
      //TODO tim don vi quanly cboDonViQL
      this.ThongTinKH.txtDiaChiLD=this.diaChiLD.DIACHI;    
      this.sp_capnhattoadohdtb(data)
    },
    async sp_capnhattoadohdtb(diachi) {
        try {
            let params = {
                "vlat": diachi.lat,
                "vlng": diachi.lng,
                "vhdtb_id": this.DsThueBao.hdtb_id,
                "vmode": 1
            }
            await api.sp_capnhattoadohdtb(this.axios, params)
        } catch (e) {
            console.log(e)
        }
    },
    popupDSHuongKN() {
      if (this.DsThueBao.khachhang_id == 0 && this.DsThueBao.hdkh_id == 0 && Number(this.chuquan_id) == 9) {
          this.$toast.error("Chưa có thông tin khách hàng/hợp đồng !");
          return;
      }
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDSHuongKNV2')
      this.popupComponentName = 'popupDSHuongKN'
      this.popupComponentData = { 
        kieu: Number(this.chuquan_id) == 9 ? 0 : 1, 
        dichvuvt_id: this.DichVuVienThong.MEGAWAN, 
      };
      this.Popup('popupComponentsMegawan');
    },
    Popup(modalId) { 
      this.$bvModal.show(modalId);
    }, 
    async ClosePopup(modalId) { 
      this.$bvModal.hide(modalId); 
    }, 
    async popupClosed(val){
      switch(this.popupComponentName) {
        case "popupDSHuongKN":
            this.DsThueBao.thuebao_cha_id = 0;
            this.DsThueBao.hdtb_cha_id = 0;
            if (val != null) {
              this.ThongTinHC.txtHuongKN = val.shuongkn;
              this.ThongTinHC.huongkn = val.vma_tb;
              
              if (val.iloai == 1)
                  this.DsThueBao.thuebao_cha_id = val.iid_sochinh;
              else
                  this.DsThueBao.hdtb_cha_id = val.iid_sochinh;
            }
            break;
        case "popupChonMaTT":
            if(val.ma_tt) 
              this.txtMaTT_KHNQ = val.ma_tt;
              this.MaTTKHNQ_KeyUp()
            break; 
        case "traphieu_success":         
            break; 
      }
      this.ClosePopup('popupComponentsMegawan');
      //if(this.txtMaKHNQ && this.txtMaKHNQ.trim()!="") this.MaKHNQ_KeyUp();
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data        
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    formatPrice(value) {
        let val = (value/1).toFixed(0).replace(',', '.')
        return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
    },
    formatTT() {
        this.ThongTinKH.txtTongTien = this.formatPrice(this.ThongTinKH.txtTongTien)
    },
    btnLaytt(){
      try{
        this.Loading = true
        this.ThongTinKH.txtMaTB = ""   
        this.getDsHopDongTB('0')
      }
      catch(ex){
        this.Loading =false
      }
    },
    async fn_tt_chonport_tudong_hoancong_mgwan(p_ds_para) {
        let result = ""
        try {
            let params = {
                "p_ds_para": JSON.stringify(p_ds_para)
            }
            let response = await api.fn_tt_chonport_tudong_hoancong_mgwan(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },
    async btnChonPort_TuDong_Click() {
      try {
          if (this.DsThueBao.data.length == 0){
              this.$toast.error("Không có thuê bao để chọn Port!") 
              return
          }
          if (this.DsThueBao.kieuld_id == 0){
              this.$toast.error("Không tìm thấy thông tin!") 
              return
          }
          let params = {
            "LOAITB_ID": this.DsThueBao.loaihinh_tb,
            "DONVI_ID": this.ThongTinHC.donvi_id,
            "PORT_ID": this.ThongTinHC.port_id,
            "VCI_VPI_ID": this.ThongTinHC.vci_vpi_id,
            "HDTB_ID": this.DsThueBao.hdtb_id,
            "THUEBAO_ID": this.ThongTinKH.thuebao_id,
            "LOAIHD_ID": this.loaihd_id,
            "MA_TN": this.ThongTinHC.txtMaTB_TN,
            "MA_TB": this.ThongTinKH.txtMaTB,
            "CURRENT_HDTB_ID": this.DsThueBao.hdtb_id,
            "IP_CN": "127.0.0.1",
            "MAY_CN": "web",
            "NGUOI_CN": this.$root.token.getUserName(),
            "CBOROUTER": this.listbox.router_sochinh.value.id,
            "SLOT_PE": this.ThongTinHC.txtSlotPE,
            "PORT_PE": this.ThongTinHC.txtPortPE,
            "WAN_IP": this.ThongTinHC.txtWanIP,
            "LAN_IP": this.ThongTinHC.txtLanIP,
            "TEXT_TBI": this.thietbi_id != null ? (this.DmThietBi.filter(x=>x.id == this.thietbi_id).length > 0 ? this.DmThietBi.filter(x=>x.id == this.thietbi_id)[0].text : '') : '',//biến cbotext_Tbi.Text.Trim()
            "DSLAMWAN": this.ThongTinHC.txtDsLam,
            "VRF": this.ThongTinHC.txtVRF,
            "LINK": this.ThongTinHC.txtLink,
            "SERIAL": this.ThongTinHC.txtSerial,
            "ROUTE": this.ThongTinHC.txtRoute,
            "SVLAN": this.ThongTinHC.txtSvLan,
            "CVLAN": this.ThongTinHC.txtCvLan,
            "LOAITBIWAN": this.loaitbi_id,
            "CONGNGHE_ID": this.ThongTinHC.congnghe_id,
            "KIEULD_ID": this.DsThueBao.kieuld_id
          }
          let res = await this.fn_tt_chonport_tudong_hoancong_mgwan(params)
          try {
            let kqchontd = JSON.parse(res)
            if (String(kqchontd.ERROR_CODE) != "1") {
              this.$toast.error(kqchontd.MESSAGE)
              return
            }
            this.ThongTinHC.txtTramTB = kqchontd.MESSAGE["txtTramTB.Text"]
            this.ThongTinHC.txtADSLport = kqchontd.MESSAGE["txtAdsl_Port.Text"]
            this.ThongTinHC.txtPortMDF = kqchontd.MESSAGE["txtPortMDF.Text"]
            this.ThongTinHC.txtNasportID = kqchontd.MESSAGE["txtNasportID.Text"]
            this.ThongTinHC.txtPort = kqchontd.MESSAGE["txtPort.Text"]
            this.ThongTinHC.txtDsLam = kqchontd.MESSAGE["txtDSLAMWan.Text"]
            this.ThongTinHC.txtIPADSL = kqchontd.MESSAGE["txtIPADSL.Text"]
            this.ThongTinHC.port_id = kqchontd.MESSAGE["port_id"]
            this.ThongTinHC.vci_vpi_id = kqchontd.MESSAGE["vci_vpi_id"]
            let l_str = kqchontd.MESSAGE["TuDong_Gan_TB_ToaNha"];
            if (l_str.startsWith("OK")) {
                if (l_str.split('-')[1] != "NULL")
                   this.$toast.success("Tự động gán thuê bao vào tòa nhà: " + l_str.split('-')[1]);
            } else {
                this.$toast.error(l_str);
            }
          } catch (e) {
            this.$toast.error(e)
          }
      } catch (err) {
        this.$toast.error("Có lỗi xảy ra: " + err);
      }
    },
    async chonPortNcong(){
      try {
        if (this.DsThueBao.data.length == 0){
          this.$toast.error("Không có thuê bao để chọn Port!") 
          return
        }
        if (this.DsThueBao.kieuld_id == 0){
           this.$toast.error("Không tìm thấy thông tin!") 
          return
        }
        var kieuld_id = 0;  //1<->Lap kem; 2<->Lap tren duong co san
        var tt_port = "";
        var vport_id = 0;
        var vkieu = 0;
        var vkieu_lap = 0;
        vkieu = this.KIEULAP.LAP_LINE_SAN
              
        if (this.ThongTinHC.txtMaTB_TN == "")
        {
          tt_port = this.TRANGTHAI_PORT.CHUA_SD
        }else{
          var data = { "p_param": this.DsThueBao.kieuld_id, "p_type": 1 }      
          let response = await this.axios.post( "/web-hopdong/laydulieu/sp_tt_kieu_ld",data);
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            vkieu_lap = response.data.data[0].kieu
            if (vkieu_lap == 2){
              vkieu = this.KIEULAP.LAP_LINE_SAN
              var data_lapkem = { p_kieuld_id: this.DsThueBao.kieuld_id,p_kieu: vkieu}      
              let lapkem_cosan = await this.axios.post( "/web-thicong/hoancongmegawan/fn_kt_lap_kem_co_san",data_lapkem);
              if(lapkem_cosan && lapkem_cosan.data && lapkem_cosan.data.data == 0 && lapkem_cosan.data.error_code && lapkem_cosan.data.error_code === 'BSS-00000000') {
                  var data_dsport = { p_ma_tb: "thanh3745085",p_kieu_id: vkieu}      
                  let ds_port = await this.axios.post( "/web-thicong/hoancongmegawan/sp_lay_ds_port_theo_ma_tb",data_dsport);                               
                  if(ds_port && ds_port.data && ds_port.data.data[0].port_id == null && ds_port.data.error_code && ds_port.data.error_code === 'BSS-00000000'){
                      this.$toast.error("Account " + this.ThongTinKH.txtMaTB + " đang lắp kèm với thuê bao " + this.ThongTinHC.txtMaTB_TN + "\n Bạn hãy thực hiện cấp port cho account '" + this.ThongTinHC.txtMaTB_TN + "' trước !") 
                      return
                  }
                  if(ds_port && ds_port.data && ds_port.data.data.length == 0 && ds_port.data.error_code && ds_port.data.error_code === 'BSS-00000000') {
                      this.$toast.error("Account " + this.ThongTinKH.txtMaTB + " đang lắp kèm với thuê bao " + this.ThongTinHC.txtMaTB_TN + "\n Bạn hãy thực hiện cấp port cho account '" + this.ThongTinHC.txtMaTB_TN + "' trước !") 
                      return
                  }else{
                    if(this.ThongTinHC.port_id == 0){
                      vport_id = ds_port.data.data[0].port_id
                    }else{
                      vport_id = this.ThongTinHC.port_id
                    }
                  }                
              }else{
                vport_id = this.ThongTinHC.port_id
              }
            }else{
              if(vkieu_lap == 1){
                vkieu = this.KIEULAP.LAP_KEM
                var data_lapkem = { p_kieuld_id: this.DsThueBao.kieuld_id,p_kieu: vkieu}      
                let lapkem_cosan = await this.axios.post( "/web-thicong/hoancongmegawan/fn_kt_lap_kem_co_san",data_lapkem);
                if(lapkem_cosan && lapkem_cosan.data && lapkem_cosan.data.data == 0 && lapkem_cosan.data.error_code && lapkem_cosan.data.error_code === 'BSS-00000000') {
                    var data_dsport = { p_ma_tb: "thanh3745085",p_kieu_id: vkieu}      
                    let ds_port = await this.axios.post( "/web-thicong/hoancongmegawan/sp_lay_ds_port_theo_ma_tb",data_dsport);                               
                    if(ds_port && ds_port.data && ds_port.data.data[0].port_id == null && ds_port.data.error_code && ds_port.data.error_code === 'BSS-00000000'){
                        this.$toast.error("Account " + this.ThongTinKH.txtMaTB + " đang lắp kèm với thuê bao " + this.ThongTinHC.txtMaTB_TN + "\n Bạn hãy thực hiện cấp port cho account '" + this.ThongTinHC.txtMaTB_TN + "' trước !") 
                        return
                    }
                    if(ds_port && ds_port.data && ds_port.data.data.length == 0 && ds_port.data.error_code && ds_port.data.error_code === 'BSS-00000000') {
                        this.$toast.error("Account " + this.ThongTinKH.txtMaTB + " đang lắp kèm với thuê bao " + this.ThongTinHC.txtMaTB_TN + "\n Bạn hãy thực hiện cấp port cho account '" + this.ThongTinHC.txtMaTB_TN + "' trước !") 
                        return
                    }else{
                      if(this.ThongTinHC.port_id == 0){
                        vport_id = ds_port.data.data[0].port_id
                      }else{
                        vport_id = this.ThongTinHC.port_id
                      }
                    }                
                }else{
                  vport_id = this.ThongTinHC.port_id
                }
              }
            }
          }          
        }
       
        var response_dc =  {}
        var diachi_id = -1
        if(this.loaihd_id == this.LoaiHopDong.DAT_MOI || this.loaihd_id == this.LoaiHopDong.DI_CHUYEN){       
          let data_dc = { "p_param": this.DsThueBao.hdtb_id, "p_type": 1 }
          response_dc = await this.axios.post( "/web-hopdong/laydulieu/sp_tt_diachi_hdtb",data_dc);
        }else{
          let data_dc = { "p_param": this.ThongTinKH.thuebao_id, "p_type": 1 }
          response_dc = await this.axios.post( "/web-hopdong/laydulieu/sp_tt_diachi_tb",data_dc);
        }        
        
        if(response_dc && response_dc.data && response_dc.data.data && response_dc.data.error_code && response_dc.data.error_code === 'BSS-00000000'){
          diachi_id = response_dc.data.data[0].diachild_id
        }
        let modal = this.$refs["ref-chon-port"];              
        modal.ChiCanChonDSLAM = this.PHAILAM("AON_CHI_CHON_DSLAM_TINH")
        modal.frmChonPort(          
            this.ThongTinHC.donvi_id,// thamso_data.RESULT.DONVI_ID,
            this.ThongTinHC.tramtb_id,// thamso_data.RESULT.TRAMTB_ID,
            this.ThongTinHC.port_id,// thamso_data.RESULT.PORT_ID,
            0,// thamso_data.RESULT.KIEU,
            this.Param_Tag.dsdichvuvt_id,// parseInt(thamso_data.RESULT.DICHVUVT_ID),
            this.DsThueBao.loaihinh_tb,// parseInt(thamso_data.RESULT.LOAITB_ID),
            diachi_id,// thamso_data.RESULT.DIACHI_ID,
            0,// thamso_data.RESULT.THAMSO,
            this.DsThueBao.hdtb_id,// thamso_data.RESULT.HDTB_ID,
            this.ThongTinKH.thuebao_id,// thamso_data.RESULT.THUEBAO_ID,
            null,// this.form.madoicap,
            this.ThongTinHC.tramtb_id// thamso_data.RESULT.TRAMTB_ID
        );
        modal.show();
      } catch (err) {
        this.$toast.error("Có lỗi xảy ra");
      }
    },
    onSelectPort(port) {
      console.log(port)
      if (this.PHAILAM("AON_CHI_CHON_DSLAM_TINH") && port.IsAONTinh && port.dslam_tinh_id)
      {
        //Chỉ chọn dslam tỉnh, ko chọn port đối với thuê bao AON
        var pdata = {"vdslam_tinh_id": port.dslam_tinh_id,"p_ma_tb":this.DsThueBao.hdtb_id}
        api.sp_update_hdtb_mgwan_theo_dslam(this.axios, JSON.stringify(pdata)).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                               
          }else{      
              this.$toast.error(response.data.message) 
          }        
        });
      }else{
        this.ThongTinHC.txtADSLport = port.adsl_port != null ?  port.adsl_port: ""
        this.ThongTinHC.txtPortMDF = port.port_mdf != null ? port.port_mdf: ""
        
        if (port.selfid01 && port.selfid01 != ""){
          this.ThongTinHC.txtNasportID = port.system + " /" + port.selfid04 + " /" + port.selfid03 + " /" + port.selfid02 + " /" + port.selfid01
        }else{
          this.ThongTinHC.txtNasportID = port.system 
        }
        this.ThongTinHC.txtPort = port.port != null ? port.port: ""
        this.ThongTinHC.txtDsLam = port.ten_dslam != null ? port.ten_dslam: ""
        this.ThongTinHC.txtIPADSL =  port.ip_dslam_tinh != null ? port.ip_dslam_tinh: ""
        
        this.ThongTinHC.port_id = port.port_id != null ? port.port_id: 0
        this.ThongTinHC.vci_vpi_id = port.vci_vpi_id != null ? port.vci_vpi_id: 0
        this.ThongTinHC.congnghe_id = port.congnghe_id != null ? port.congnghe_id: 0
        this.ThongTinHC.tramtb_id = port.tramtb_id != null ? port.tramtb_id: 0
        this.ThongTinHC.txtSvLan = port.vpi != null ? port.vpi: ""
        this.ThongTinHC.txtCvLan = port.vci != null ? port.vci: ""  
        
        // cap nhat port
        var dr = {}
        dr.VPORT_ID = this.DsThueBao.data[0].port_id ,//Convert.ToInt64(dsIn.Tables[0].Rows[nIndex]["port_id"].ToString().Trim()
        dr.VVCI_VPI_ID = this.DsThueBao.data[0].vci_vpi_id  ,//Convert.ToInt64(dsIn.Tables[0].Rows[nIndex]["vci_vpi_id"].ToString().Trim())
        dr.PORT_ID = port.port_id ,//biến port_id
        dr.VCI_VPI_ID = port.vci_vpi_id ,//biến vci_vpi_id
        dr.HDTB_ID = this.DsThueBao.hdtb_id ,//biến hdtb_id
        dr.MA_TB = this.ThongTinKH.txtMaTB, //biến ma_tb
        dr.CURRENT_HDTB_ID = this.DsThueBao.hdtb_id,//biến CURRENT_HDTB_ID
        dr.IP_CN = '127.0.0.0',//tt_nd.ip
        dr.MAY_CN = 'web',//biến tt_nd.may_cn
        dr.NGUOI_CN = this.$root.token.getUserName(),//biến tt_nd.ma_nd
        dr.CBOROUTER = this.router_id,//biến Convert.ToInt32(cboRouter.SelectedValue)
        dr.SLOT_PE = this.ThongTinHC.txtSlotPE,//biến txtSlotPE.Text
        dr.PORT_PE = this.ThongTinHC.txtPortPE,//biến txtPortPE.Text.Trim()
        dr.WAN_IP = this.ThongTinHC.txtWanIP,//biến txtWAN_IP.Text.Trim()
        dr.LAN_IP = this.ThongTinHC.txtLanIP,//biến txtLAN_IP.Text.Trim()
        dr.TEXT_TBI = this.thietbi_id != null ? (this.DmThietBi.filter(x=>x.id == this.thietbi_id).length > 0 ? this.DmThietBi.filter(x=>x.id == this.thietbi_id)[0].text : '') : '',//biến cbotext_Tbi.Text.Trim()
        dr.DSLAMWAN = this.ThongTinHC.txtDsLam,
        dr.VRF = this.ThongTinHC.txtVRF,
        dr.LINK = this.ThongTinHC.txtLink,
        dr.SERIAL = this.ThongTinHC.txtSerial
        dr.ROUTE = this.ThongTinHC.txtRoute,
        dr.SVLAN = this.ThongTinHC.txtSvLan,
        dr.CVLAN = this.ThongTinHC.txtCvLan,
        dr.LOAITBIWAN = this.loaitbi_id,
        dr.CONGNGHE_ID = this.ThongTinHC.congnghe_id
       
        var pdata = {"p_ds_para": JSON.stringify(dr)}
        api.capnhat_port_hoancong_megawan(this.axios, JSON.stringify(pdata)).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                               
          }else{      
              this.$toast.error(response.data.message) 
          }        
        });
      }      
      this.HienThiTTHopDongTB()
    },
    btnNoiDungTH(){
      try {
        if (this.DsThueBao.phieu_id == 0){
          return
        }
        var pdata = {'p_phieu_id': this.DsThueBao.phieu_id,'p_nd_thuchien': this.ThongTinHC.txtNoiDungTH}
        api.update_nd_thuchien(this.axios, pdata).then((response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {                 
             this.$toast.success('Cập nhật thành công!')
          }else{
            this.ThongTinHC.txtNoiDungTH = null
          }        
        });
        this.HienThiTTHopDongTB()
      } catch (error) {
        this.$toast.error('Có lỗi: ' + error)
      }
    },
    async KiemTra_DL(luong_id) {
      try {
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Không có thuê bao trên lưới !");
        return false;
      }
      
      if (this.DmNguoiGV.length == 0) {
        this.$toast.error("Hãy nhập người giao việc!");
        return false;
      } else {
        let nguoigv_idx = this.DmNguoiGV.find((item) => item.id == this.nguoigv_id)
        if (nguoigv_idx == undefined || nguoigv_idx.text == undefined || nguoigv_idx.text == "") {
          this.$toast.error("Hãy nhập người giao việc!");
          return false;
        }
      }
      if (!this.chkNgayGV) {
        this.$toast.error("Hãy nhập ngày giao việc!");
        return false;
      }      
      if (!this.chkNgayHT) {
        this.$toast.error("Hãy nhập ngày hoàn thành!");
        return false;
      }
      if (this.PHAILAM("GIAOVIEC")) {
          if (this.DsNhanVienTH.data.length <= 0) {
              this.$toast.error("Hãy nhập nhân viên thực hiện  !");
              return false;
          }
      }
      if (this.PHAILAM("HUONG_KN")) {
          if (Number(this.loainode_id) == this.LoaiNode_MGWan.NODE_PHU) {
              if (this.ThongTinHC.huongkn == "") {
                  this.$toast.error("Bạn chưa chọn hướng kết nối cho thuê bao !");
                  return false;
              }
          }
      }
      console.log(this.ThongTinHC)
      if (this.PHAILAM("CAPNHAT_THONGSO_KYTHUAT_WAN")) {
          if (this.PHAILAM("AON_CHI_CHON_DSLAM_TINH")) {
              let dslam_tinh_id = await this.fn_tt_hdtb_mgwan(2, this.DsThueBao.hdtb_id);
              if (dslam_tinh_id == "-1" && this.ThongTinHC.port_id == 0) {
                  this.$toast.error("Hãy chọn port cho thuê bao !");
                  return false;
              }
          } else {
              if (this.ThongTinHC.txtDsLam == undefined || this.ThongTinHC.txtDsLam == "") {
                  this.$toast.error("Hãy nhập thông tin về Dslam/Switch");
                  $("#txtDsLam").focus()
                  return false;
              }
              if (this.ThongTinHC.txtPort == undefined || this.ThongTinHC.txtPort == "") {
                  this.$toast.error("Hãy nhập thông tin về Port");
                  $("#txtAdsl_Port").focus()
                  return false;
              }
              if (!this.PHAILAM("KHONG_CAPNHAT_SVLAN_CVLAN")) {
                  if (this.ThongTinHC.txtCvLan == undefined || this.ThongTinHC.txtCvLan == "") {
                      this.$toast.error("Hãy nhập thông tin về Cvlan");
                      $("#txtCvLan").focus();
                      return false;
                  }
              }
              if (this.DsThueBao.loaihinh_tb == this.LoaiHinhTB.MEGAWAN_ADSL || this.DsThueBao.loaihinh_tb == this.LoaiHinhTB.MEGAWAN_QUANG_FE
                  || this.DsThueBao.loaihinh_tb == this.LoaiHinhTB.MEGAWAN_QUANG_GE || this.DsThueBao.loaihinh_tb == this.LoaiHinhTB.MEGAWAN_SHDSL) {
                  if (this.ThongTinHC.txtLanIP == undefined || this.ThongTinHC.txtLanIP == "") {
                      this.$toast.error("Hãy nhập thông tin về Lan_IP");
                      $("#txtLAN_IP").focus();
                      return false;
                  }
                  if (this.ThongTinHC.txtWanIP == undefined || this.ThongTinHC.txtWanIP == "") {
                      this.$toast.error("Hãy nhập thông tin về Wan_IP");
                      $("#txtWAN_IP").focus();
                      return false;
                  }
                  if (!this.PHAILAM("KHONG_CAPNHAT_SVLAN_CVLAN")) {
                      if (this.ThongTinHC.txtSvLan == undefined || this.ThongTinHC.txtSvLan == "") {
                          this.$toast.error("Hãy nhập thông tin về Svlan");
                          $("#txtSvLan").focus();
                          return false;
                      }
                  }
              } else {
                  if (Number(this.loaikenh_id) != 8) {  // LOAIKENH.NOIHAT = 8
                      if (!this.PHAILAM("KHONG_CAPNHAT_SVLAN_CVLAN")) {
                          if (this.ThongTinHC.txtCvLan == undefined || this.ThongTinHC.txtCvLan == "") {
                              this.$toast.error("Hãy nhập thông tin về CvLan");
                              $("#txtCvLan").focus();
                              return false;
                          }
                      }
                  }
              }
          }
      }
      if (this.PHAILAM("CAPNHAT_CAP")) {
          if (this.PHAILAM("PHAINHAP_CAP")) {
              if (this.ThongTinHC.txtDoiCapGoc == "") {
                  this.$toast.error("Hãy nhập thông tin đôi cáp gốc !");
                  $("#txtDoiCapGoc").focus();
                  return false;
              }
              if (this.ThongTinHC.txtCapGoc == "") {
                  this.$toast.error("Hãy nhập thông tin cáp gốc !");
                  $("#txtCapGoc").focus();
                  return false;
              }
              if (this.ThongTinHC.txtDoiCapNgon == "") {
                  this.$toast.error("Hãy nhập thông tin đôi cáp ngọn !");
                  $("#txtDoiCapNgon").focus();
                  return false;
              }
              if (this.ThongTinHC.txtCapNgon == "") {
                  this.$toast.error("Hãy nhập thông tin cáp ngọn !");
                  $("#txtCapNgon").focus();
                  return false;
              }
              if (this.ThongTinHC.txtTuCapGoc == "") {
                  this.$toast.error("Hãy nhập thông tin Tủ cáp gốc !");
                  $("#txtTuCapGoc").focus();
                  return false;
              }
          }
          let in_kieuld_id = Number(this.DsThueBao.kieuld_id)
          // KieuLapDat.TACH_VLAN = 581
          if (in_kieuld_id != 581) {
              if (this.ThongTinHC.txtCuLy == undefined || this.ThongTinHC.txtCuLy == "" || this.ThongTinHC.txtCuLy == "0") {
                  this.$toast.error("Hãy nhập thông tin số mét dây !");
                  $("#txtSoMetDay").focus();
                  return false;
              }
          }
          if (this.ThongTinHC.txtCuLy != "") {
              if (this.ThongTinHC.txtCuLy == undefined || isNaN(this.ThongTinHC.txtCuLy)) {
                  this.$toast.error("Số mét dây phải là kiểu số");
                  this.ThongTinHC.txtCuLy = "0";
                  $("#txtSoMetDay").focus();
                  return false;
              }
          }  else {
              this.$toast.error("Hãy nhập số mét dây !");
              $("#txtSoMetDay").focus();
              return false;
          }
          if (Number(this.loaihd_id) == this.LoaiHopDong.DAT_MOI
              || Number(this.loaihd_id) == this.LoaiHopDong.DI_CHUYEN) {
              if (this.PHAILAM("VAT_TU") && in_kieuld_id != 581) {
                  let ktra_giao_vattu = await this.Kiemtra_GiaoPhieu_VatTu(this.DsThueBao.phieu_id);
                  if (ktra_giao_vattu == false) {
                      this.$toast.error("Bạn chưa cấp vật tư/ thiết bị cho thuê bao !");
                      return false;
                  }
              }
          }
      }
      } catch (e) {
        console.log(e)
        return false
      }
      return true;
    },
    async Kiemtra_GiaoPhieu_VatTu(vphieu_id) {
        let result = false
        try {
            let params = {
                "vphieu_id": vphieu_id
            }
            let response = await api.fn_kiemtra_giaophieu_vattu(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                if (Number(response.data.data) > 0) {
                    result = true
                }
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },
    KiemTraDK_HoanThanh (thangnamtt, thangnamsys) {
      // console.log("thangnamtt = " + thangnamtt + ", thangnamsys = " + thangnamsys)
      let result = false
      // So sánh năm
      if (Number.parseInt(thangnamtt.substr(6, thangnamtt.length)) < Number.parseInt(thangnamsys.substr(6, thangnamsys.length))) {
        // Nếu năm thanh toán < năm hoàn thành thì true;
        return true
      } else if (Number.parseInt(thangnamtt.substr(6, thangnamtt.length)) === Number.parseInt(thangnamsys.substr(6, thangnamsys.length))) {
        // Nêu năm = nhau thì kiểm tra tiếp
        // Nếu tháng tt < thang ht -> true
        if (Number.parseInt(thangnamtt.substr(3, 5)) < Number.parseInt(thangnamsys.substr(3, 5))) {
          result = true
        } else if (Number.parseInt(thangnamtt.substr(3, 5)) === Number.parseInt(thangnamsys.substr(3, 5))) {
          if (Number.parseInt(thangnamtt.substr(0, 2)) <= Number.parseInt(thangnamsys.substr(0, 2))) {
            result = true
          } else {
            result = false
          }
        } else {
          result = false
        }
      } else {
        result = false
      }
      return result
    },
    async btnCapNhat(){ 
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Không có thuê bao trên lưới !");
        return;
      }
      if (!await this.KiemTra_DL(this.luong_id)) return;
      let ngay_ht = moment(this.ngayht, 'DD/MM/YYYY hh:mm A').format("DD/MM/YYYY");
      let ngay_sys = moment(new Date()).format("DD/MM/YYYY");
      if (!this.KiemTraDK_HoanThanh(ngay_ht, ngay_sys)) {
          this.$toast.error("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");
          return;
      }
      var dr = {}
      console.log(this.thietbi_id)
      console.log(this.DmThietBi.filter(x=>x.id == this.thietbi_id))
      dr.LUONG_ID = this.luong_id,
      dr.PORT_ID = this.ThongTinHC.port_id,
      dr.VCI_VPI_ID = this.ThongTinHC.vci_vpi_id,
      dr.HDTB_ID = this.DsThueBao.hdtb_id,
      dr.LOAIHD_ID = this.loaihd_id,
      dr.KIEULD_ID = this.DsThueBao.kieuld_id,
      dr.NHANVIEN_ID = this.$root.token.getNhanVienID(),      
      dr.PHIEU_ID = this.DsThueBao.phieu_id,
      dr.NGAY_BG = moment(this.ngayht, 'DD/MM/YYYY hh:mm A').format("DD/MM/YYYY hh:mm:ss"),
      dr.NGAY_GV = moment(this.ngaygv, 'DD/MM/YYYY hh:mm A').format("DD/MM/YYYY hh:mm:ss"),
      dr.NGUOI_GV = this.nguoigv_id,
      dr.VMATB = this.ThongTinKH.txtMaTB,
      dr.MAY_CN = 'web',
      dr.NGUOI_CN = this.$root.token.getUserName(),
      dr.CBOROUTER = this.router_id,
      dr.SLOT_PE = this.ThongTinHC.txtSlotPE,
      dr.PORT_PE = this.ThongTinHC.txtPortPE,
      dr.WAN_IP = this.ThongTinHC.txtWanIP,
      dr.LAN_IP = this.ThongTinHC.txtLanIP,
      dr.TEXT_TBI = this.thietbi_id != null ? (this.DmThietBi.filter(x=>x.id == this.thietbi_id).length > 0 ? this.DmThietBi.filter(x=>x.id == this.thietbi_id)[0].text : '') : '',
      dr.DSLAMWAN = this.ThongTinHC.txtDsLam,
      dr.VRF = this.ThongTinHC.txtVRF,
      dr.LINK = this.ThongTinHC.txtLink,
      dr.SERIAL = this.ThongTinHC.txtSerial
      dr.ROUTE = this.ThongTinHC.txtRoute,
      dr.SVLAN = this.ThongTinHC.txtSvLan,
      dr.CVLAN = this.ThongTinHC.txtCvLan,
      dr.LOAITBIWAN = this.loaitbi_id,
      dr.CONGNGHE_ID = this.ThongTinHC.congnghe_id,
      dr.DOICAPNGON = this.txtDoiCapNgon,
      dr.DOICAPGOC = this.txtDoiCapGoc,
      dr.TUYENCAP = this.txtTuyenCap,
      dr.LIENTU = this.txtLienTu,
      dr.HOPCAPNGON = this.txtHopCapNgon,
      dr.TUCAPNGON = this.txtTuCapNgon,
      dr.LOAICAP = this.loaicap_id,
      dr.CAPGOC = this.txtCapGoc,
      dr.CAPNGON = this.txtCapNgon,
      dr.TUCAPGOC = this.txtTuCapNgon,
      dr.SOMETDAY = this.txtCuLy,
      dr.LOAISOWAN = this.loainode_id,
      dr.IN_HDTB_ID_CHA = this.DsThueBao.hdtb_cha_id,
      dr.IN_THUEBAO_ID_CHA = this.DsThueBao.thuebao_cha_id,
      dr.HUONGKN = this.ThongTinHC.huongkn,
      dr.TINH_KN_ID = this.ThongTinHC.tinh_kn_id
      console.log(dr)
      var pdata = {"param" : JSON.stringify(dr)}
      // console.log(JSON.stringify(pdata))
      api.post_hoancong_megawan(this.axios,  JSON.stringify(pdata)).then((response) => {        
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
            var apidata = JSON.parse(response.data.data)
            if (apidata["ERROR_CODE"] == 1) {
              this.$toast.success(apidata["MESSAGE"])
              this.getDsHopDongTB('0')
            } else if (apidata["ERROR_CODE"] == 0) {
              this.$toast.error(apidata["RESULT"].MESSAGE) 
            } else {
               this.$toast.error(apidata["MESSAGE"])   
            }      
        } else {      
            this.$toast.error(response.data.message)
        }        
      });
    },
    btnGiaoViec() {
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại! ");
        return;
      }
      if (this.DmNguoiGV.length == 0 || this.nguoigv_id == 0) {
        this.$toast.error("Hãy nhập nhân viên giao việc!");
        return;
      }
      if (!this.chkNgayGV) {
        this.$toast.error("Hãy nhập ngày giao việc!");
        return;
      }
      // this.popupComponent = ()=>import('@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue');
      // this.popupComponentName = "popupGiaoViec";
      // this.popupComponentData = { phieu_id: this.DsThueBao.phieu_id, nguoigv_id: this.nguoigv_id, ngaygiao: this.ngaygv }; 
      // this.Popup('popupComponentsMegawan');
      
      this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(this.DsThueBao.phieu_id, this.$root.token.getDonViID(), this.nguoigv_id, moment(this.ngaygv, 'DD/MM/YYYY hh:mm A').format("DD/MM/YYYY hh:mm:ss"));
      this.$nextTick(() => {
        this.$refs.frnGiaoPhieuNV.load()
        this.$refs.popupfrnGiaoPhieuNV.show();
      })
    },
    async giaoviec_success () {
      this.HienThiDSNV()
    },
    async initGiaoPhieu(vtrangthai_phieu, vloaihd_id, vdichvuvt_id, vhuonggiao_id, vquytrinh_id, vmagd) {
      try {
        this.loading(true)
        this.$refs.ppGiaoPhieu.ngaygiao_tk = moment(new Date()).format("DD/MM/YYYY");
        this.$refs.ppGiaoPhieu.ngaygiao = moment(new Date()).format("DD/MM/YYYY");
        await this.$refs.ppGiaoPhieu.LoadCombobox();
        // Trạng thái phiếu
        this.$refs.ppGiaoPhieu.listbox.trangthaiphieu.value.id = vtrangthai_phieu;
        //Loại hợp đồng
        this.$refs.ppGiaoPhieu.isTimTheoLoaiHD = true;
        this.$refs.ppGiaoPhieu.listbox.loaihd.value.LOAIHD_ID = vloaihd_id
        //Dịch vụ
        this.$refs.ppGiaoPhieu.isTimTheoDichVu = true;
        this.$refs.ppGiaoPhieu.DICHVU = vdichvuvt_id
        this.$refs.ppGiaoPhieu.listbox.dichvu.value.DICHVUVT_ID = vdichvuvt_id
        //Hướng giao
        this.$refs.ppGiaoPhieu.listbox.huonggiao.value.huonggiao_id = vhuonggiao_id
        await this.$refs.ppGiaoPhieu.LoadHuongGiao();
        this.$refs.ppGiaoPhieu.listbox.huonggiao.value.huonggiao_id = vhuonggiao_id
        await this.$refs.ppGiaoPhieu.HuongGiaoChange()
        //Quy trình
        await this.$refs.ppGiaoPhieu.LoadQuyTrinh();
        this.$refs.ppGiaoPhieu.isTimTheoQuyTrinh = true;
        this.$refs.ppGiaoPhieu.listbox.quytrinh.value.quytrinh_id = vquytrinh_id
        this.$refs.ppGiaoPhieu.txtMaGD = vmagd
        if (this.$refs.ppGiaoPhieu.txtMaGD !="") {
          await this.$refs.ppGiaoPhieu.onMaGDEnter();
        }
      } finally {
        this.loading(false)
      }   
    },
    async lay_huonggiao_gp(luongId) {
      let res = 0
      try {
          let params = {
              "luongId": luongId
          }
          let response = await api.lay_huonggiao_gp(this.axios, params);
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
              res = Number(response.data.data)
          }            
      } catch (e) {
          console.log(e)
      }
      return res
    },
    async btnGiaoPhieu(){
      if (!this.GiaoPhieuStatus) return
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu! ");
        return;
      }
      if (!this.txtMaGD){
        let vhuonggiao_id = 0;
        if (this.PHAILAM("DIEUKIEN_GIAOPHIEU_BP_TIEPTHEO")) {
            var pdata = {"baohong_id":0, "hdtb_id": this.DsThueBao.hdtb_id, "quytrinh_id":this.quytrinh_id}
            await api.lay_idx_huonggiao_theoquytrinh_id(this.axios, pdata).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { 
                    vhuonggiao_id = response.data.data
                } else {      
                    vhuonggiao_id = 0
                }        
            });
        }
        
        if (vhuonggiao_id == 0){
            vhuonggiao_id = await this.lay_huonggiao_gp(this.luong_id)      
        } 
        if (vhuonggiao_id == 0){
            this.$toast.error("Chưa có hướng giao được gán !")
            return
        }
        var gp = 0
        if (this.PHAILAM("TUDONG_GIAOPHIEU_DUYET_CAPVT"))
        {
            try
            {
                gp = 1;
            }
            catch (Exception)
            {
                gp = 0;
            }
        }
     
        this.giaoPhieuViewDialog.input = {
          vhuonggiao_id: vhuonggiao_id,
          vloai_hd: this.loaihd_id,
          vdichvuvt_id: this.DsThueBao.dichvuvt_id,
          vma_gd: this.ThongTinKH.txtMaGD,
          vquytrinh: this.quytrinh_id,
          vtrangthaiphieu: 1,
        };
        this.GiaoPhieuView_OnClick()
      }      
    },
    btnTraPhieu(){
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu! ");
        return;
      }
       this.showModalTraphieu = true;
    },
    traphieu_success() {
      this.showModalTraphieu = false;
    },
    btnXuatPhieu(){
      
    },
    ShowBaoCao(tenfile) {
      // IT3 lam
      this.$toast.error("IT3 xu ly xuat bao cao " + tenfile)
    },
    btnPhieuTC_Click() {
      try {
        if (this.DsThueBao.data.length <= 0) {
            this.$toast.error("Chọn thuê bao cần xuất phiếu !");
            return;
        }
        
        this.dsGiaTri.push(this.DmTocDoCIR.find((item) => item.id == this.tocdocir_id).text)
        if (Number(this.loaihd_id) == this.LoaiHopDong.DAT_MOI) {
            this.$refs.XemBaoCao1.XemNgay()
        }
        if (Number(this.loaihd_id) == this.LoaiHopDong.DI_CHUYEN) {
            this.$refs.XemBaoCao2.XemNgay()
        }
      } catch (ex) {
          this.$toast.error("Có lỗi " + ex)
      }
    },
    async fn_tt_hdtb_hen(type, param) {
        let result
        try {
            let params = {
                "type": type,
                "param": param,
            }
            let response = await api.fn_tt_hdtb_hen(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },
    async fn_tt_hdtb_mgwan(type, param) {
        let result
        try {
            let params = {
                "type": type,
                "param": param,
            }
            let response = await api.fn_tt_hdtb_mgwan(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },
    async fn_tt_hd_thuebao(type, param) {
        let result
        try {
            let params = {
                "type": type,
                "param": param,
            }
            let response = await api.fn_tt_hd_thuebao(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },
    async fn_tt_diachi_hdtb(type, param) {
        let result
        try {
            let params = {
                "type": type,
                "param": param,
            }
            let response = await api.fn_tt_diachi_hdtb(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },
    async InPhieu() {
      if (this.DsThueBao.checked.length == 0) {
          this.$toast.error("Bạn phải chọn phiếu để in");
          return;
      }
      let dsChonIn = []
      this.DsThueBao.checked.forEach(hdtb_id => {
        dsChonIn.push(this.DsThueBao.data.find(item => item.hdtb_id == hdtb_id))
      })
      try {
          let vhdkh_id = await this.fn_tt_hd_thuebao(1, this.DsThueBao.hdtb_id);
          if (vhdkh_id == "-1") {
              this.$toast.error("Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!");
              return;
          }
          let n_hdkh_id = "", n_hdtb_id = "", n_phieu_id = "";
          for (let k = 0; k < dsChonIn.length; k++) {
            n_hdkh_id += String(dsChonIn[k].hdkh_id) + ",";
            n_hdtb_id += String(dsChonIn[k].hdtb_id) + ",";
            n_phieu_id += String(dsChonIn[k].phieu_id) + ",";
          }
          if (n_hdkh_id != "" && n_phieu_id != "" && n_hdtb_id != "") {
            this.modelIn.n_hdkh_id = n_hdkh_id.substring(0, n_hdkh_id.length - 1);
            this.modelIn.n_phieu_id = n_phieu_id.substring(0, n_phieu_id.length - 1);
            this.modelIn.n_hdtb_id = n_hdtb_id.substring(0, n_hdtb_id.length - 1);
            this.modelIn.nvth_id = this.DsNhanVienTH.nhanvien_tc_id
            this.modelIn.cv_thuchien = this.DsNhanVienTH.congviec_th
            this.modelIn.huonggiao_id = this.huonggiao_id
            this.modelIn.tentram_tb = ""
            this.$refs.popupInBB.showModal()
          }
        } catch (ex) {
            this.$toast.error("Có lỗi " + ex)
        }
    },
    btnBienBanNT_Click() {
      if (this.DsThueBao.data.length <= 0) {
          this.$toast.error("Bạn chưa chọn phiếu! ");
          return;
      }
      this.InPhieu()
    },
    btnKetNoi(){
      // this.$bvModal.show('PopUpKetNoiSoChinhSoFu');
      if (this.DsThueBao.data.length <= 0) {
          this.$toast.error("Bạn chưa chọn phiếu! ");
          return;
      }
      this.$refs.ppCTKetNoi.openDialog(this.ThongTinKH.txtMaGD)
    },
    btnThongTinVRF() {
      if (this.DsThueBao.data.length <= 0) {
          this.$toast.error("Bạn chưa chọn phiếu! ");
          return;
      }
      this.$refs.ppCTKetNoi_VFI.openDialog(this.ThongTinHC.txtVRF)
    },
    btnChuyenTo(){
      if (this.DsThueBao.data.length <= 0) {
          this.$toast.error("Bạn chưa chọn phiếu! ");
          return;
      }
      try {
        if (this.PHAILAM('KIEMTRA_VT_CHUYENTO')) {      
          var pdata = { 'phieu_id': this.DsThueBao.phieu_id,'baohong_id': 0,'hdtb_id': this.DsThueBao.hdtb_id,'kieutbi': 0,'status': 0}
          api.lay_ds_phieuvt_hdbh(this.axios, pdata).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {                 
                this.$toast.error('Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ')
                return              
            }        
          });
        }
        this.$refs.popupChuyenTo.openDialog(this.DsThueBao.phieu_id, this.DsThueBao.hdtb_id, 0)
      } catch (error) {
        this.$toast.error('Có lỗi: ' + error)
      }
    },
    closePopupChuyenTo(){      
    },
    async btnIPLanWan(){
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu! ");
        return;
      }
      var data_ip = { hdtb_id:  this.DsThueBao.hdtb_id}      
      let response_ktip = await this.axios.post( "/web-thicong/hoancongmegawan/kt_ip_wan_lan",data_ip)
       
      if(response_ktip && response_ktip.data && response_ktip.data.error_code && response_ktip.data.error_code === 'BSS-00000000') {
          if (response_ktip.data.data != "1"){
            this.$toast.error(response_ktip.data.data)
            return
          }
      }
      
      var tenbras = ''
      var bras_id = 0
      var data = { vhdtb_id:  this.DsThueBao.hdtb_id}      
      let response = await this.axios.post( "/web-thicong/hoancongmegawan/lay_tt_port",data)
      if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {         
          tenbras = response.data.data[0].tenbras
          bras_id = response.data.data[0].bras_id
      }
      this.objIP_WAN_LAN = {
        hdtb_id: this.DsThueBao.hdtb_id,
        loaitb_id: this.DsThueBao.loaihinh_tb,
        dichvuvt_id: this.DsThueBao.dichvuvt_id,
        bras_id: bras_id,
        ma_tb: this.ThongTinKH.txtMaTB,
        tenbras: tenbras,
        trangthaidl_id: 1,
      };
      this.$bvModal.show("thayDoiThongSoInternet_popupIP_WAN_LAN");
    },
    btnDauNoi(){
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu! ");
        return;
      }
      try {
          var pdata = { 'ma_ts': "DAUNOI_KOCAN_CAP_PORT" }
          api.lay_ds_thamso_md_mats(this.axios, pdata).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {                 
                if(this.ThongTinHC.port_id == 0 && response.data.data.length == 0){
                  this.$toast.error("Chưa có thông tin về Port, bạn không được phép cấp cáp !")
                  return
                }          
            }        
          });
          var lattitude = 0
          var longtitude = 0
          var vto_ql = ''
          var str_tram_tbi = ''
          var culy = 0
          var pdata_latlong = { "p_param": this.DsThueBao.hdtb_id, "p_type": 1 }
          api.sp_tt_diachi_hdtb(this.axios, pdata_latlong).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {                 
                lattitude = response.data.data[0].kinhdo_ld
                longtitude = response.data.data[0].vido_ld
            }        
          });  
          
          var pdata_to = { "p_param": this.ThongTinHC.donvi_id, "p_type": 1 }
          api.sp_tt_donvi(this.axios, pdata_to).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {                 
                vto_ql = response.data.data[0].ten_dv
            }        
          }); 
          var pdata_tram = { "p_param": this.ThongTinHC.tramtb_id, "p_type": 1 }
          api.sp_tt_donvi(this.axios, pdata_tram).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {                 
                str_tram_tbi = response.data.data[0].ten_dv
            }        
          }); 
          var pdata_culy = { "p_param": this.ThongTinHC.thuebao_id, "p_type": 1 }
          api.sp_tt_daunoi_cap_tb(this.axios, pdata_culy).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {                 
                culy = response.data.data[0].culy
            }        
          });
          // chuyens sang form frmDauNoiTB
          let thongTinDauNoi = {
              thueBaoId: this.DsThueBao.hdtb_id,
              kieu: 1
          }
          this.$refs.daunoithuebao.setData(thongTinDauNoi)
          this.$refs.daunoithuebao.show()
          this.HIEN_THONGSO_KYTHUAT();
      } catch (error){
          this.$toast.error('Có lỗi: ' + error)
      }
    },
    btnVatTuMoi(){
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu! ");
        return;
      }
      this.modelVatTu.phieu_id = this.DsThueBao.phieu_id;
      this.modelVatTu.loaihd_id = this.loaihd_id;
      this.modelVatTu.hdtb_id = this.DsThueBao.hdtb_id;
      this.modelVatTu.thuebao_id = this.ThongTinKH.thuebao_id;
      this.modelVatTu.loaitb_id = this.DsThueBao.loaihinh_tb
      this.$refs.popupVatTuTB.openDialog()
    },
    btnVatTu(){
      if (this.DsThueBao.phieu_id != 0) {
          this.modelVatTuThueBao.phieu_id = this.DsThueBao.phieu_id;
          this.modelVatTuThueBao.loaihd_id = this.loaihd_id;
          this.modelVatTuThueBao.hdtb_id = this.DsThueBao.hdtb_id;
          this.modelVatTuThueBao.thuebao_id = this.ThongTinKH.thuebao_id;
          this.$refs.popupVatTuThueBao.showModal()
      } else {
          this.$toast.error("Bạn chưa chọn phiếu! ");
      }
    },
    btnDoKiem(){
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu! ");
        return;
      }
      if (this.DsThueBao.hdtb_id != 0){
        this.$refs.dokiemModal.showModal();
      }else{
        this.$toast.error("Bạn chưa chọn phiếu!");
        return;
      }      
    },
    btnHenLD(){
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu! ");
        return;
      }
      this.$refs.thongTinHenKhachHangModal.showModal();
    },
    btnGanKV(){
        if (this.DsThueBao.data.length <= 0) {
          this.$toast.error("Bạn chưa chọn phiếu! ");
          return;
        }
        this.dataPopupKhuVuc.hdtb_id = this.DsThueBao.hdtb_id;
        this.dataPopupKhuVuc.donviql_id = 0;
        this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal();
    },
    btnTonPhieu(){
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu! ");
        return;
      }
      if (this.DsThueBao.phieu_id == 0) {
        this.$toast.error("Hãy chọn phiếu trước để nhập lý do tồn!");
        return;
      }
      //binding phieu_id, tthd_id
      this.$refs.tonPhieuGiaoModal.showModal();
    },
    btnThongSoKT(){
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu! ");
        return;
      }
      if (this.DsThueBao.phieu_id != 0) {
        this.$refs.traCuuThongSoKTModal.showModal()
      }
    },
    btnDuAn(){
      // tsbtnDuAn.Visible luôn bằng false
    },
    btnSuaKenhThu(){
      // tsbtnSua_KenhThu.Visible luôn bằng false
    },
   
    btnGiaoPhieuPhoiHop(){
      // tsbtnTinh_GiaoNET_NT.Visible luôn bằng false
    },
    btnUpLoadHS(){
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu!")
        return
      }
      
      this.modelUploadEFile = {
        type: "MA_GD",
        value: this.ThongTinKH.txtMaGD
      }
      this.$refs.ppUpload.init(this.modelUploadEFile);
      this.$refs.popupUploadHoSo.show();
    },
 
    async btnHoanThien() {
        if (this.DsThueBao.data.length <= 0) {
          this.$toast.error("Không có thuê bao để Hoàn thiện !");
          return;
        }
        try {
            this.loading(true)
            var dr = {}
            dr.LUONG_ID = this.luong_id,
            dr.PORT_ID = this.ThongTinHC.port_id,
            dr.VCI_VPI_ID = this.ThongTinHC.vci_vpi_id,
            dr.HDTB_ID = this.DsThueBao.hdtb_id,
            dr.LOAIHD_ID = this.loaihd_id,
            dr.KIEULD_ID = this.DsThueBao.kieuld_id,
            dr.NHANVIEN_ID = this.$root.token.getNhanVienID(),
            dr.PHIEU_ID = this.DsThueBao.phieu_id,
            dr.NGAY_BG = moment(this.ngayht, 'DD/MM/YYYY hh:mm A').format("DD/MM/YYYY hh:mm:ss"),
            dr.NGAY_GV = moment(this.ngaygv, 'DD/MM/YYYY hh:mm A').format("DD/MM/YYYY hh:mm:ss"),
            dr.NGUOI_GV = this.nguoigv_id,
            dr.MATB = this.ThongTinKH.txtMaTB,
            dr.MAY_CN = 'web',
            dr.NGUOI_CN = this.$root.token.getUserName(),
            dr.IP_CN = this.$root.token.getIP()
            dr.DONVI_ID = this.$root.token.getDonViID()
            dr.CBOROUTER = this.router_id,
            dr.SLOT_PE = this.ThongTinHC.txtSlotPE,
            dr.PORT_PE = this.ThongTinHC.txtPortPE,
            dr.WAN_IP = this.ThongTinHC.txtWanIP,
            dr.LAN_IP = this.ThongTinHC.txtLanIP,
            dr.TEXT_TBI = this.thietbi_id != null ? (this.DmThietBi.filter(x=>x.id == this.thietbi_id).length > 0 ? this.DmThietBi.filter(x=>x.id == this.thietbi_id)[0].text : '') : '',
            dr.DSLAMWAN = this.ThongTinHC.txtDsLam,
            dr.VRF = this.ThongTinHC.txtVRF,
            dr.LINK = this.ThongTinHC.txtLink,
            dr.SERIAL = this.ThongTinHC.txtSerial
            dr.ROUTE = this.ThongTinHC.txtRoute,
            dr.SVLAN = this.ThongTinHC.txtSvLan,
            dr.CVLAN = this.ThongTinHC.txtCvLan,
            dr.LOAITBIWAN = this.loaitbi_id,
            dr.CONGNGHE_ID = this.ThongTinHC.congnghe_id,
            dr.DOICAPNGON = this.txtDoiCapNgon,
            dr.DOICAPGOC = this.txtDoiCapGoc,
            dr.TUYENCAP = this.txtTuyenCap,
            dr.LIENTU = this.txtLienTu,
            dr.HOPCAPNGON = this.txtHopCapNgon,
            dr.TUCAPNGON = this.txtTuCapNgon,
            dr.LOAICAP = this.loaicap_id,
            dr.CAPGOC = this.txtCapGoc,
            dr.CAPNGON = this.txtCapNgon,
            dr.TUCAPGOC = this.txtTuCapNgon,
            dr.SOMETDAY = this.txtCuLy,
            dr.LOAISOWAN = this.loainode_id,
            dr.IN_HDTB_ID_CHA = this.DsThueBao.hdtb_cha_id,
            dr.IN_THUEBAO_ID_CHA = this.DsThueBao.thuebao_cha_id,
            dr.HUONGKN = this.ThongTinHC.huongkn,
            dr.TINH_KN_ID = this.ThongTinHC.tinh_kn_id
            var pdata = {"p_ds_para" : JSON.stringify(dr)}
            api.fn_hths_hoancongmegawan(this.axios, JSON.stringify(pdata)).then(async (response) => {
              if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  let rsx = JSON.parse(response.data.data)
                  if (rsx.ERROR_CODE != 1) {
                      let resultHT = rsx.RESULT && rsx.RESULT.MESSAGE && rsx.RESULT.MESSAGE != null ? rsx.RESULT.MESSAGE : rsx.MESSAGE
                      this.$toast.error(resultHT)
                      return
                  }
                  if (this.PHAILAM("KT_HOANCONG_HD_BANCHEO")) {
                      try {
                          let ds = await this.hoancong_thicong_2(this.DsThueBao.hdtb_id)
                          if (ds.length > 0 && ds[0].hdtb_ban && Number(ds[0].hdtb_ban) > 0) { // bên thi công hoàn công update dữ liệu bên bán
                              let tinh = await this.tinh_thicong(Number(ds[0].tinhban))
                              let tinhtc_id = Number(ds[0].tinhtc)
                              await this.hoancong_thicong_v3(tinh.tinhthicong_id, Number(ds[0].hdtb_ban), Number(ds[0].khachhang_tc_id), 
                                  Number(ds[0].thanhtoan_tc_id), Number(ds[0].thuebao_tc_id), tinhtc_id)
                          } else { //bên bán hoàn thiện
                              let ds2 = await this.sp_lay_thongtin_bancheo(this.DsThueBao.hdtb_id)
                              if (ds2.length != 0) { //anhnt thêm điều kiện thuebao_id_thicong != -1 cho chỉ giao net
                                  if (String(ds2[0].loaihd_id) == String(this.LoaiHopDong.DAT_MOI) && 
                                        String(ds2[0].thuebao_id_thicong) != "-1") {
                                      if (String(ds2[0].dichvuvt_id) != String(this.DichVuVienThong.TSL) && String(ds2[0].dichvuvt_id) != "25") {
                                          let tinh = await this.tinh_thicong(Number(ds2[0].tinh_thicong))
                                          await this.sp_them_db_bancheo_v2(tinh.tinhthicong_id, ds2[0].thuebao_id_thicong, 
                                              ds2[0].thuebao_id_ban, ds2[0].tinh_ban)
                                      } else {
                                          for (let k = 0; k < ds2.length; k++) {
                                              let tinh = await this.tinh_thicong(Number(ds2[k].tinh_thicong))
                                              await this.sp_them_db_bancheo_v2(tinh.tinhthicong_id, ds2[k].thuebao_id_thicong, 
                                                  ds2[k].thuebao_id_ban, ds2[k].tinh_ban)
                                          }
                                      }
                                  }
                              }
                          }
                      } catch (ex) {
                          await this.log_action("frmHoanCongMegaWan_HT", 
                              "hdtb_id=" + this.DsThueBao.hdtb_id + ", ngay_cn = " + moment(new Date()).format('DD/MM/YYYY HH:mm:ss') + ", Err : " + ex,
                              "Hoàn công cố định")
                      }
                  }
                  this.$toast.success("Hoàn thiện hồ sơ thành công!") 
                  this.getDsHopDongTB('0')                
              } else {      
                  this.$toast.error(response.data.message) 
              }        
            });
        } catch(ex) { 
            console.log(ex)
            this.$toast.error(''+ex); 
        } finally {
            this.loading(false)
        }
    },
    btnXuatFile() {
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !");
        return;
      }
      this.$refs.exportDataModal.showModal()
    },
    async hoancongmegawan(p_ds_para) {
        let resultHC = ""
        try {
            var params = {
                "p_ds_para" : JSON.stringify(p_ds_para),
                "loaihinh_tb": this.ThongTinKH.txtLoaiHinh,
                "hdtb_id": this.DsThueBao.hdtb_id
            }
            let response = await api.hoancong_hoancongmegawan(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                let result = response.data.data
                try {
                    let rs = JSON.parse(result)
                    if (rs.ERROR_CODE == 1) {
                        resultHC = "ok"
                    } else {
                        resultHC = rs.RESULT && rs.RESULT.MESSAGE && rs.RESULT.MESSAGE != null ? rs.RESULT.MESSAGE : rs.MESSAGE
                    }
                } catch (e) {
                    resultHC = "" + e
                }    
            } else {
                resultHC = "" + response.data.message
            }
        } catch (ex) {
            resultHC = "" + ex
        }
        return resultHC
    },
    async btnHoanCong(){
      if (!this.HoanCongStatus) return
      if (this.DsThueBao.data.length <= 0) {
        this.$toast.error("Bạn chưa chọn phiếu! ");
        return;
      }
      this.$confirm('Bạn thật sự muốn hoàn công không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
      }).then(async v => {
        if(v) {
          try {
              this.loading(true)
              var dr = {}
              dr.LUONG_ID = this.luong_id,
              dr.PORT_ID = this.ThongTinHC.port_id,
              dr.VCI_VPI_ID = this.ThongTinHC.vci_vpi_id,
              dr.HDTB_ID = this.DsThueBao.hdtb_id,
              dr.HDKH_ID = this.DsThueBao.hdkh_id,
              dr.LOAIHD_ID = this.loaihd_id,
              dr.KIEULD_ID = this.DsThueBao.kieuld_id,
              dr.DICHVUVT_ID = this.Param_Tag.dsdichvuvt_id,
              dr.NHANVIEN_ID = this.$root.token.getNhanVienID(),      
              dr.PHIEU_ID = this.DsThueBao.phieu_id,
              dr.THUEBAO_ID = this.ThongTinKH.thuebao_id,
              dr.KYHOADON = moment(this.ngayht, 'DD/MM/YYYY hh:mm A').format("YYYYMM"),
              dr.HUONGGIAO_ID = this.huonggiao_id,
              dr.NGAY_HT = moment(this.ngayht, 'DD/MM/YYYY hh:mm A').format("DD/MM/YYYY hh:mm:ss"),
              dr.NGAY_BG = moment(this.ngayht, 'DD/MM/YYYY hh:mm A').format("DD/MM/YYYY hh:mm:ss"),
              dr.NGAY_GV = moment(this.ngaygv, 'DD/MM/YYYY hh:mm A').format("DD/MM/YYYY hh:mm:ss"),
              dr.NGUOI_GV = this.nguoigv_id,
              dr.DONVI_ID = this.$root.token.getDonViID(),
              dr.MATB = this.ThongTinKH.txtMaTB,
              dr.SODTLH = this.ThongTinKH.txtSoDTLH,
              dr.MAY_CN = 'web',
              dr.NGUOI_CN = this.$root.token.getUserName(),
              dr.IP_CN = '127.0.0.0'
              dr.CBOROUTER = this.router_id,
              dr.SLOT_PE = this.ThongTinHC.txtSlotPE,
              dr.PORT_PE = this.ThongTinHC.txtPortPE,
              dr.WAN_IP = this.ThongTinHC.txtWanIP,
              dr.LAN_IP = this.ThongTinHC.txtLanIP,
              dr.TEXT_TBI = this.thietbi_id != null ? (this.DmThietBi.filter(x=>x.id == this.thietbi_id).length > 0 ? this.DmThietBi.filter(x=>x.id == this.thietbi_id)[0].text : '') : '',
              dr.DSLAMWAN = this.ThongTinHC.txtDsLam,
              dr.VRF = this.ThongTinHC.txtVRF,
              dr.LINK = this.ThongTinHC.txtLink,
              dr.SERIAL = this.ThongTinHC.txtSerial
              dr.ROUTE = this.ThongTinHC.txtRoute,
              dr.SVLAN = this.ThongTinHC.txtSvLan,
              dr.CVLAN = this.ThongTinHC.txtCvLan,
              dr.LOAITBIWAN = this.loaitbi_id,
              dr.CONGNGHE_ID = this.ThongTinHC.congnghe_id,
              dr.DOICAPNGON = this.txtDoiCapNgon,
              dr.DOICAPGOC = this.txtDoiCapGoc,
              dr.TUYENCAP = this.txtTuyenCap,
              dr.LIENTU = this.txtLienTu,
              dr.HOPCAPNGON = this.txtHopCapNgon,
              dr.TUCAPNGON = this.txtTuCapNgon,
              dr.LOAICAP = this.loaicap_id,
              dr.CAPGOC = this.txtCapGoc,
              dr.CAPNGON = this.txtCapNgon,
              dr.TUCAPGOC = this.txtTuCapNgon,
              dr.SOMETDAY = this.txtCuLy,
              dr.LOAISOWAN = this.loainode_id,
              dr.IN_HDTB_ID_CHA = this.DsThueBao.hdtb_cha_id,
              dr.IN_THUEBAO_ID_CHA = this.DsThueBao.thuebao_cha_id,
              dr.HUONGKN = this.ThongTinHC.huongkn,
              dr.TINH_KN_ID = this.ThongTinHC.tinh_kn_id
              let resultHC = await this.hoancongmegawan(dr)
              if (resultHC == "ok") {
                  if (this.PHAILAM("KT_HOANCONG_HD_BANCHEO")) {
                      try {
                          let ds = await this.hoancong_thicong_2(this.DsThueBao.hdtb_id)
                          if (ds.length > 0 && ds[0].hdtb_ban && Number(ds[0].hdtb_ban) > 0) { // bên thi công hoàn công update dữ liệu bên bán
                              let tinh = await this.tinh_thicong(Number(ds[0].tinhban))
                              let tinhtc_id = Number(ds[0].tinhtc)
                              await this.hoancong_thicong_v3(tinh.tinhthicong_id, Number(ds[0].hdtb_ban), Number(ds[0].khachhang_tc_id), 
                                  Number(ds[0].thanhtoan_tc_id), Number(ds[0].thuebao_tc_id), tinhtc_id)
                          } else { //bên bán hoàn thiện
                              let ds2 = await this.sp_lay_thongtin_bancheo(this.DsThueBao.hdtb_id)
                              if (ds2.length != 0) { //anhnt thêm điều kiện thuebao_id_thicong != -1 cho chỉ giao net
                                  if (String(ds2[0].loaihd_id) == String(this.LoaiHopDong.DAT_MOI) && 
                                        String(ds2[0].thuebao_id_thicong) != "-1") {
                                      if (String(ds2[0].dichvuvt_id) != String(this.DichVuVienThong.TSL) && String(ds2[0].dichvuvt_id) != "25") {
                                          let tinh = await this.tinh_thicong(Number(ds2[0].tinh_thicong))
                                          await this.sp_them_db_bancheo_v2(tinh.tinhthicong_id, ds2[0].thuebao_id_thicong, 
                                              ds2[0].thuebao_id_ban, ds2[0].tinh_ban)
                                      } else {
                                          for (let k = 0; k < ds2.length; k++) {
                                              let tinh = await this.tinh_thicong(Number(ds2[k].tinh_thicong))
                                              await this.sp_them_db_bancheo_v2(tinh.tinhthicong_id, ds2[k].thuebao_id_thicong, 
                                                  ds2[k].thuebao_id_ban, ds2[k].tinh_ban)
                                          }
                                      }
                                  }
                              }
                          }
                      } catch (ex) {
                          await this.log_action("frmHoanCongMegaWan_HT", 
                              "hdtb_id=" + this.DsThueBao.hdtb_id + ", ngay_cn = " + moment(new Date()).format('DD/MM/YYYY HH:mm:ss') + ", Err : " + ex,
                              "Hoàn công cố định")
                      }
                  }
                  try {
                      let tinh_ban_id = Number(await this.fn_tt_hd_bancheo(7, this.DsThueBao.hdtb_id));
                      let hdtb_ban_id = Number(await this.fn_tt_hd_bancheo(8, this.DsThueBao.hdtb_id));
                      if (tinh_ban_id != -1 && hdtb_ban_id != -1) {
                          let tinh_ban = await this.tinh_thicong(tinh_ban_id);
                          await this.hc_net_sms_vip_ph(tinh_ban.tinhthicong_id, hdtb_ban_id, 0)
                      }
                  } catch (e) { }
                  this.$toast.success("Hoàn công thành công!")
                  this.getDsHopDongTB('0') 
              } else {
                  this.$toast.error("Hoàn công thất bại: " + resultHC)
              }
          } catch(ex) { 
              this.$toast.error(''+ex); 
          } finally {
              this.loading(false)
          }
        }
      });
    },
    async hc_net_sms_vip_ph(tinhthicong_id, hdtb_ban_id, phieu_id) {
        try {
            let params = {
                "tinhthicong_id": tinhthicong_id,
                "hdtb_ban_id": hdtb_ban_id,
                "phieu_id": phieu_id
            }
            let response = await api.hc_net_sms_vip_ph(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                
            } else {
              this.$toast.error(response.data.message)
            }      
        } catch (e) {
            this.$toast.error(e)
            console.log(e)
        }
    },
    async fn_tt_hd_bancheo(type, param) {
        let result
        try {
            let params = {
                "type": type,
                "param": param
            }
            let response = await api.fn_tt_hd_bancheo(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },
    async log_action(vaction_name, vkey_parameter, vnote) {
        try {
            let params = {
                "vaction_name": vaction_name,
                "vkey_parameter": vkey_parameter,
                "vnote": vnote
            }
            let response = await api.log_action(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                
            } else {
              this.$toast.error(response.data.message)
            }      
        } catch (e) {
            this.$toast.error(e)
            console.log(e)
        }
    },
    async sp_them_db_bancheo_v2(tinhthicong_id, thuebao_id, thuebao_kn_id, tinh_kn_id) {
        try {
            let params = {
                "tinhthicong_id": tinhthicong_id,
                "thuebao_id": thuebao_id,
                "thuebao_kn_id": thuebao_kn_id,
                "tinh_kn_id": tinh_kn_id
            }
            let response = await api.sp_them_db_bancheo_v2(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                
            } else {
              this.$toast.error(response.data.message)
            }      
        } catch (e) {
            this.$toast.error(e)
            console.log(e)
        }
    },
    async sp_lay_thongtin_bancheo(vhdtb_id) {
        let result = []
        try {
            let params = {
                "vhdtb_id": vhdtb_id
            }
            let response = await api.sp_lay_thongtin_bancheo(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },
    async hoancong_thicong_v3(tinhthicong_id, vhdtb_ban, vkhid_tc, vttid_tc, vtbid_tc, vtinh_tc) {
        try {
            let params = {
                "tinhthicong_id": tinhthicong_id,
                "vhdtb_ban": vhdtb_ban,
                "vkhid_tc": vkhid_tc,
                "vttid_tc": vttid_tc,
                "vtbid_tc": vtbid_tc,
                "vtinh_tc": vtinh_tc
            }
            let response = await api.hoancong_thicong_v3(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                
            } else {
              this.$toast.error(response.data.message)
            }      
        } catch (e) {
            this.$toast.error(e)
            console.log(e)
        }
    },
    async lay_thongtin_tinh(tinh_id) {
        let res = []
        try {
            let params = {
                "tinh_id": tinh_id
            }
            let response = await api.lay_thongtin_tinh(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                res = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return res
    },
    async tinh_thicong(tinh_id) {
        let tinh = {}
        let rs = await this.lay_thongtin_tinh(tinh_id)
        if (rs.length > 0) {
            let dr = rs[0];
            tinh.user = dr.ten_truycap;
            tinh.pass = dr.mat_khau;
            tinh.nhanvien_id = Number(dr.nhanvien_id);
            tinh.donvi_id = Number(dr.donvi_id);
            tinh.matinh = dr.matinh;
            tinh.TNS = dr.tns_string;
            tinh.tinhthicong_id = Number(dr.tinh_id);
            tinh.mavung = dr.ma_vung;
            tinh.ip_dll = dr.ip_dll;
        } else {
            tinh.user = "";
            tinh.pass = "";
            tinh.nhanvien_id = 0;
            tinh.donvi_id = 0;
            tinh.matinh = "";
            tinh.TNS = "";
            tinh.tinhthicong_id = 0;
            tinh.ip_dll = "";
        }
        return tinh;
    },
    async hoancong_thicong_2(hdtb_id) {
        let result = []
        try {
            let params = {
                "hdtb_id": hdtb_id
            }
            let response = await api.hoancong_thicong_2(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },
    async lay_ds_thongtin_kythuat_hdtb(vhdtb_id) {
        let result = []
        try {
            let params = {
                "vphanvung_id": this.$root.token.getPhanVungID(),
                "vhdtb_id": vhdtb_id,
            }
            let response = await api.lay_ds_thongtin_kythuat_hdtb(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },
    async HIEN_THONGSO_KYTHUAT() {
        let IS_USING_CABMAN_V2 = false
        var pdata = { 'ma_ts': "USING_CABMAN_2.0" }
        await api.lay_ds_thamso_md_mats(this.axios, pdata).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000'
                && response.data.data && response.data.data.length > 0) {                 
              IS_USING_CABMAN_V2 = true
            }        
        });
        if (!IS_USING_CABMAN_V2) {
            if (!this.visible.palThongTinCapCu) {
                this.visible.palThongTinCapCu = true;
                this.visible.palThongSoKyThuat = false;
            }
            return;
        }
        if (!this.visible.palThongSoKyThuat) {
            this.visible.palThongTinCapCu = false;
            this.visible.palThongSoKyThuat = true;
        }
        let wbrTSKT = ""  
        try {
            let ds = await this.lay_ds_thongtin_kythuat_hdtb(this.DsThueBao.hdtb_id);   
            ds.forEach((dr) => {
                let gt = dr.format_gt.split("{0}").join(dr.giatri)
                wbrTSKT += dr.format_tt.split("{0}").join(dr.ten_truong)
                wbrTSKT += (gt != null && gt != "null") ? gt : ""
            })
        } catch (ex) {
            wbrTSKT = "Lỗi: " + ex
        }
          
        $("#wbrTSKT").html(wbrTSKT)
    },
    gridDSTBSelected: async function(args){ 
      try {
        this.loading(true)
        await this.HienThiTTHopDongTB(args.data)
      } finally {
        this.loading(false)
      }       
    },
    //----------------------popup huong ket noi
    gridDSKHChanged()
    {
    },
    gridDSHuongKNChanged()
    {
    },
    btnTimKiem()
    {
    },
    btnChapNhan()
    {
    },
    //---------------------popup số chính số phụ
    gridDSSoChinhChanged()
    {
    },
     gridDSSoFuChanged()
    {
    },
    //-----------------popup số chính số phụ ______
    gridDSHDChanged()
    {
    },
    gridDSThueBaoChanged()
    {
    },
    tsbtnGiaoPhieu_PH_V2_Click() {
        if (this.DsThueBao.data.length <= 0) {
            this.$toast.error("Bạn chưa chọn phiếu! ");
            return;
        }
        try {
            this.$refs.ppGiaoPhieu_PH.openDialog()
        } catch (ex) {
            this.$toast.error("Lỗi giao phiếu P/H: " + ex);
        }
    },
    async btnBanDoLD_Click() {
      try {
          this.loading(true)
          let lattitude = 0;
          let longtitude = 0;
          let s = await this.fn_tt_diachi_hdtb(1, this.DsThueBao.hdtb_id);
          if (s != "-1" && s != "")
              longtitude = Number(s);
          s = await this.fn_tt_diachi_hdtb(2, this.DsThueBao.hdtb_id);
          if (s != "-1" && s != "")
              lattitude = Number(s);
          if (lattitude * longtitude > 0) {
              this.$bvModal.show("popupDiaChiLD");
          } else if (this.ThongTinKH.txtDiaChiLD != undefined && this.ThongTinKH.txtDiaChiLD.trim() != "") {
              this.$bvModal.show("popupDiaChiLD");
          } else {
              this.$toast.error("Chưa có thông tin về bản đồ địa chỉ thuê bao");
          }
      } catch (ex) {
          this.$toast.error(ex);
      } finally {
        this.loading(false)
      }
    },
    gview_DsThueBao_actionComplete() {
      if (this.DsThueBao.data.length > 0) {
          if (this.ThongTinKH.txtMaTB != "") {
              let index = this.DsThueBao.data.findIndex(item => item.ma_tb === this.ThongTinKH.txtMaTB.trim())
              if (index >= 0) {
                  this.$refs.DsThueBao.$refs.grid.selectRow(index)
              } else {
                  this.$refs.DsThueBao.$refs.grid.selectRow(0)
              }
          } else {
              if (this.matb_link && this.matb_link != "") {
                  let index = this.DsThueBao.data.findIndex(item => item.ma_tb === this.matb_link)
                  if (index >= 0) {
                      this.$refs.DsThueBao.$refs.grid.selectRow(index)
                  } else {
                      this.$refs.DsThueBao.$refs.grid.selectRow(0)
                  }
              } else {
                  this.$refs.DsThueBao.$refs.grid.selectRow(0)
              }
          }     
      }
    },
    giaophieu_success() {
        console.log('giaophieu_success')
        this.ThongTinKH.txtMaTB = ""   
        this.getDsHopDongTB('0')
    },
    async GiaoPhieuView_OnClick() {
      this.giaoPhieuViewDialog.isVisiable = true
      this.$refs.GiaoPhieuView.show()
    },
    async GiaoPhieuView_OnClose() {
      this.giaoPhieuViewDialog.isVisiable = false
    },
  },
};
</script>