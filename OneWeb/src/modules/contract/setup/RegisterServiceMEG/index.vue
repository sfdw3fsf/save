<template src="./index.html"></template>
<style scoped src="./index.scss"></style>
<script>
import Vue from "vue";
import moment from "moment";
import { Button } from '@syncfusion/ej2-buttons';
//import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import VueDateExt from "../DeclareLandline/components/VueDateExt";
import DataGrid2 from '@/components/Controls/DataGrid2'
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
//import DiaChiModal from '../setup/SurveyRequest/popups/ChonDiaChi/DiaChiModal.vue'
import { GridPlugin,Sort, Filter, Page, GridComponent } from "@syncfusion/ej2-vue-grids";
//import DatePromotion from "../../admin/category/Promotion/components/DatePromotion/DatePromotion.vue";
import DatePicker from "vue2-datepicker";
import { required } from 'vuelidate/lib/validators';
import BssErrorMarker from "@/components/BssErrorMarker.vue";
import BssRequiredMarker from '@/components/BssRequiredMarker';
import "vue2-datepicker/index.css";
// import {ValidOrder} from "./Enum";
Vue.use(GridPlugin,Sort);

const ComboxSelectedIdConstraint = (itemSelectedId) => itemSelectedId && parseInt(itemSelectedId) > 0;

export default{
  components: {
    BssErrorMarker,
    //Select2Ext, 
    DataGrid2 ,
    VueDateExt  ,
    DataGridCustom   ,
    DatePicker,
    BssRequiredMarker
  },
    data() {
      return{
        input_diachi_KD_TM: '',
        input_diachi_DN_TM: '',
        input_diachi_KD_CN: '',
        input_diachi_DN_CN: '',
        dataPL : '',
        KHDN_TM : {
          TenDN : '',
          cboPhanLoai_TM: 0,
          cboLoaiKH_TM : 0,
          txtMST_TM :'' ,
          cboNganhNghe_TM : 0,
          cboPhanHangKH_TM : 0
        }  ,
        KHDN_CN : {
          TenDN : '',
          cboPhanLoai_TM: 0,
          cboLoaiKH_TM : 0,
          txtMST_CN :'' ,
          cboNganhNghe_TM : 0,
          cboPhanHangKH_TM : 0
        }  ,
        options: {
          gioiTinhId: null,
        },
        dsGioiTinh: [],
        isActive : true,
        isActive_tpThemMoi : false, 
        isActive_tpCapnhat : false,  
        isActive_tpCapnhat_HDG : false, 
        isActive_tpCapnhat_CCBS : false, 
        isActive_tpCapnhat_listCCBS : false,
        isActive_tpCapnhat_listHDG : false,
        isActive_tpCapnhat_UHDG : false,   
        tsbtnTimKiem_tpTimKiem : true,
        tsbtnThemMoi_tpTimKiem : true,
        tsbtnThemMoi_tpThemMoi : false,
        tsbtnCapNhat_tpCapNhat : false,
        tsbtnThemHDCCBS_tpCapNhat : false,
        tsbtnThemHDGiay_tpCapNhat : false,
        tpCapNhatHDGiay : false,              
        tpCapNhat : false,
        tpThemMoi : false,
        tpTimKiem : true,
        khuyenmai_id: null,
        oldestFirst: false,
        serviceTransportation: null,
        dispatchNumber: null,
        namePrommotion: '',
        kindOfPromtion: null,
        promotionType: null,
        accompanyingPromotion: null,
        tpThemMoi_model :false,
        participationPromotion: null,
        dateFrom: moment(new Date()).format("DD/MM/YYYY"),
        dateTo: moment(new Date()).format("DD/MM/YYYY"),
        commitmentUse: null,
        content: null,
        noteContent: null,
        cboCVu: {
          DataSource: [],
          Enabled: true,
          Visible: true,
        },
        momentFormat: {
          stringify: (date) => {
            return date ? moment(date).format("D/M/YYYY") : "";
          },
          parse: (value) => {
            return value ? moment(value, "D/M/YYYY").toDate() : null;
          },
          },      
        cboPban: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboPhanLoai_CN: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboLoaiMeg: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboLfile: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboDSTB: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboDSNoiMang: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboDSNgoaiMang: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },        
        cboHTup: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboLfile: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboKfileup: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboDSNganHang: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboDSThanhToan: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboMaTinh_CN: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboPhanHangKH_CN: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboNganhNghe_CN: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboTrangThai: {
        DataSource: [],
        Enabled: true,
        Visible: true
        }, 
        cboMaHDCCBS_TM: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },
        cboMaHDCCBS_CN: {
        DataSource: [],
        Enabled: true,
        Visible: true
        },       
      gvDanhSachMeg: {
        DataSource: [],      
        columns: [
          {
            fieldName: "STT",
            headerText: "STT",
            allowFiltering: false,
            allowSorting: false,
            width: '70',            
          },
          {
            fieldName: "MSISDN",
            headerText: "Số MEG",
            allowFiltering: false,
            allowSorting: false,
            width: '85'
          },
          {
            fieldName: "CUSTMANAGER",
            headerText: "Tên khách hàng",
            allowFiltering: false,
            allowSorting: false,
            width: '200'
          },
          {
            fieldName: "BILLINGADDESS",
            headerText: "Địa chỉ thanh toán",
            allowFiltering: false,
            allowSorting: false,
            width: '100'
          },         
          {
            fieldName: "",
            headerText: "Cập nhật",                      
            allowFiltering: false,
            allowSorting: false,
            width: '100',
            template : this.GetColumnUpdate(this.Update_change)
          },         
        ],              
      },
      gvDanhSachPhongBan: {
        DataSource: [],
        columns: [
          {
            fieldName: "STT",
            headerText: "STT",
            textAlign: "Center",
            allowFiltering: false,
            allowSorting: false,
            width: '50'
          },
          {
            fieldName: "MSISDN",
            headerText: "SỐ MEG",
            textAlign: "Center",
            allowFiltering: false,
            allowSorting: false,
            width: '100'
          },
          {
            fieldName: "DEPARTMENT_NAME",
            headerText: "Tên phòng ban",
            textAlign: "Center",
            allowFiltering: false,
            allowSorting: false,
            width: '100'
          },
          {
            fieldName: "USERID",
            headerText: "Người CN",
            textAlign: "Center",
            allowFiltering: false,
            allowSorting: false,
            width: '100'
          },
          {
            fieldName: "LOGDATE",
            headerText: "Ngày CN",
            textAlign: "Center",
            allowFiltering: false,
            allowSorting: false,
            width: '100'
          },          
          
        ]
      },
      gvDanhSachMegEx: {
        DataSource: [],
        columns: [
          {
            fieldName: "ROW_STT",
            headerText: "STT",
            allowFiltering: false,
            allowSorting: false,
            width: '50'
          },
          {
            fieldName: "MA_HD",
            headerText: "Số extension",
            allowFiltering: false,
            allowSorting: false,
            width: '100'
          },
          {
            fieldName: "MA_KH",
            headerText: "Thuê bao trỏ đến",
            allowFiltering: false,
            allowSorting: false,
            width: '100'
          },
          {
            fieldName: "MA_TB",
            headerText: "Người sử dụng",
            allowFiltering: false,
            allowSorting: false,
            width: '100'
          },
          {
            fieldName: "TEN_TB",
            headerText: "Bộ phận",
            allowFiltering: false,
            allowSorting: false,
            width: '100'
          },
          {
            fieldName: "DIACHI",
            headerText: "Chức vụ",
            aallowFiltering: false,
            allowSorting: false,
            width: '100'
          },          
          {
            fieldName: "tyle_vat",
            headerText: "Trạng thái",            
            width: '50',
            template : this.GetColumnDelCCBS(this.ts_khdn_mnp_del_ccbs)
          },
          
        ]
      },      
      kieu_chon_diachi:1,
      dataDiaChi:{
        diachi_id:0,
        tinh_id:0,
        quan_id:0,
        phuong_id:0,
        pho_id:0,
        ap_id:0,
        khu_id:0,
        dacdiem_id:0,
        sonha:'',
        //
        block:'',
        tang:'',
        sophong:'',
        motathem:'',
        diachi:''
      },            
      dataDiaChiDN:{
        diachi_id:0,
        tinh_id:0,
        quan_id:0,
        phuong_id:0,
        pho_id:0,
        ap_id:0,
        khu_id:0,
        dacdiem_id:0,
        sonha:'',
        //
        block:'',
        tang:'',
        sophong:'',
        motathem:'',
        diachi:''
      },
      dataDiaChiDKKD:{
        diachi_id:0,
        tinh_id:0,
        quan_id:0,
        phuong_id:0,
        pho_id:0,
        ap_id:0,
        khu_id:0,
        dacdiem_id:0,
        sonha:'',
        //
        block:'',
        tang:'',
        sophong:'',
        motathem:'',
        diachi:''
      },
      dataDiaChi_CN:{
        diachi_id:0,
        tinh_id:0,
        quan_id:0,
        phuong_id:0,
        pho_id:0,
        ap_id:0,
        khu_id:0,
        dacdiem_id:0,
        sonha:'',
        //
        block:'',
        tang:'',
        sophong:'',
        motathem:'',
        diachi:''
      },            
      dataDiaChiDN_CN:{
        diachi_id:0,
        tinh_id:0,
        quan_id:0,
        phuong_id:0,
        pho_id:0,
        ap_id:0,
        khu_id:0,
        dacdiem_id:0,
        sonha:'',
        //
        block:'',
        tang:'',
        sophong:'',
        motathem:'',
        diachi:''
      },
      dataDiaChiDKKD_CN:{
        diachi_id:0,
        tinh_id:0,
        quan_id:0,
        phuong_id:0,
        pho_id:0,
        ap_id:0,
        khu_id:0,
        dacdiem_id:0,
        sonha:'',
        //
        block:'',
        tang:'',
        sophong:'',
        motathem:'',
        diachi:''
      },     
      
      ds_valid: []
    
    }
      
  },
  validations:{ 
    options: {
      doiTuongSDId: { ComboxSelectedIdConstraint },
      gioiTinhId: { required },
    },      
    KHDN_TM: {          
      TenDN: { required },
      txtMST_TM: { required },
      cboPhanLoai_TM: { ComboxSelectedIdConstraint }, 
      cboLoaiKH_TM: { ComboxSelectedIdConstraint }, 
      cboPhanHangKH_TM     : { ComboxSelectedIdConstraint },
      cboNganhNghe_TM    : { ComboxSelectedIdConstraint }
      },
    KHDN_CN: {          
      TenDN: { required },
      txtMST_CN: { required }         
      }
      
  },
    components: {
    //DiaChiModal,
    VueDateExt,
    DataGrid2
    
  },
    mounted() {  
    
    this.HT_Chuc_Vu_Combobox(),
    this.HT_Goi_Ngoai_Mang_Combobox()   ,
    this.HT_Goi_Noi_Mang_Combobox(),
    this.HT_DS_SubMeg(),
    this.HT_DS_Phongban(),
    this.HT_DS_Thue_Bao_Combobox()
    
  },
  methods: {  
    layDSGioiTinh () {
      this.dsGioiTinh.push({gioitinh_id: 0, gioitinh: "Nữ"})
      this.dsGioiTinh.push({gioitinh_id: 1, gioitinh: "Nam"})
    },
    PopUpUploadDS: function () {
      this.$refs.uploadDS.show();
    },
    ClosePopupUl: function () {
      this.$refs.uploadDS.hide()
    },   
    PopUpUploadNoiMang: function () {
      this.$refs.PUNoiMang.show();
    },
    ClosePopupNoiMang: function () {
      this.$refs.PUNoiMang.hide()
    }, 
    PopUpUploadNgoaiMang: function () {
      this.$refs.PUNgoaiMang.show();
    },
    ClosePopupNgoaiMang: function () {
      this.$refs.PUNgoaiMang.hide()
    },
    PopUpUploadNganHang: function () {
      this.$refs.PUNganHang.show();
    },
    ClosePopupNganHang: function () {
      this.$refs.PUNganHang.hide()
    },
    PopUpUploadThanhToan: function () {
      this.$refs.PUThanhToan.show();
    },
    ClosePopupThanhToan: function () {
      this.$refs.PUThanhToan.hide()
    },
    XoaTrangSubMeg: function () {
      this.$refs.submeg_soext.value = "";
      this.$refs.submeg_tb.value = "";
      this.$refs.sunmeg_nsd.value = "";
      this.$refs.cboPban.value = "";
      this.$refs.cboCVu.value = "";
    },  
    async HT_Chuc_Vu_Combobox() {
      try {
        let data = await this.axios.post("ccbs/executor/meg_ds_chucvu",{})
        this.cboCVu.DataSource = data.data.data      
      } catch (err) {
        this.cboCVu.DataSource = []
      }
    },    
    async HT_Goi_Ngoai_Mang_Combobox() {
      try {
        let data = await this.axios.post("ccbs/executor/meg_ds_goi_ngoaimang",{})
        this.cboDSNgoaiMang.DataSource = data.data.data  
        if (this.cboDSNgoaiMang.DataSource.length > 0) {
          this.cboDSNgoaiMang.DataSource.forEach((element, index) => {
          element.STT = index + 1
          })
        }     
      } catch (err) {
        this.cboDSNgoaiMang.DataSource = []
      }
    },
    async HT_Goi_Noi_Mang_Combobox() {
      try {
        let data = await this.axios.post("ccbs/executor/meg_ds_goi_noimang",{})
        this.cboDSNoiMang.DataSource = data.data.data
        if (this.cboDSNoiMang.DataSource.length > 0) {
          this.cboDSNoiMang.DataSource.forEach((element, index) => {
          element.STT = index + 1
          })
        }
        
      } catch (err) {
        this.cboDSNoiMang.DataSource = []
      }
    },
     async HT_DS_Thue_Bao_Combobox() {
      try {
        let data = await this.axios.post("ccbs/executor/meg_ds_thuebao",{})
        this.gvDanhSachMeg.DataSource = data.data.data
        if (this.gvDanhSachMeg.DataSource.length > 0) {
          this.gvDanhSachMeg.DataSource.forEach((element, index) => {
          element.STT = index + 1
          })
        }       
      } catch (err) {
        this.gvDanhSachMeg.DataSource = [];
      }
    },
    async HT_DS_SubMeg(){
      try {
        let data = await this.axios.post("ccbs/executor/meg_ds_submeg",{"custid": "14248"});
        this.gvDanhSachMegEx.DataSource= data.data.data;
        if (this.gvDanhSachMegEx.DataSource.length > 0) {
          this.gvDanhSachMegEx.DataSource.forEach((element, index) => {
          element.STT = index + 1
          })
        }
      }catch(err){
        this.cboDSTB.DataSource = [];
      }
    },
    async HT_DS_Phongban(){
      try {
        let data = await this.axios.post("ccbs/executor/meg_ds_phongban",{
          "custid": "14248"
        });
        this.cboPban.DataSource = data.data.data;
        this.gvDanhSachPhongBan.DataSource = data.data.data;
        if (this.gvDanhSachPhongBan.DataSource.length > 0) {
          this.gvDanhSachPhongBan.DataSource.forEach((element, index) => {
          element.STT = index + 1
          })
        }
      }catch(err){
        this.cboPban.DataSource =[];
        this.gvDanhSachPhongBan.DataSource = [];
      }
    },
    async AddNPban(){
      this.$bvModal.msgBoxConfirm('Bạn có muốn thêm phòng ban không?', {
        title: 'Xác nhận thêm phòng ban',
        size: 'md',
        centered: true,
        okTitle: 'Đồng ý',
        cancelTitle: 'Không đồng ý',
        }).then(async v => {
          if(!v) {
            return;
          }
        try {
          let data = await this.axios.post("ccbs/executor/meg_them_phongban",{
            "ten_phongban": this.$refs.pban_someg.value,
            "custid": "14248",
            "so_tb": this.$refs.pban_tenpb.value
          })
        //if(data.data.data.result== '1')
          //this.$root.$toast.success('Thêm mới thành công')
          if(data.data.error_code = 'BSS-00000500')  
          this.$root.$toast.error(data.data.message)     
        } catch (err) {
          this.$root.$toast.success(data.message) 
        }
      })
    },    
    async ts_khdn_mnp_list_hd_giay(){
      try {
        let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_list_hd_giay",{
          schema : "",
          page_index : "1",
          ma_dn : this.$refs.txt_ma_dn_CN.value,
          record_per_page : "100",
          userid : this.get_user_info("id")
        })
        this.gvDanhSachHDGiay.DataSource = data.data.data  
        //console.log(data.data.data )     
      } catch (err) {
        this.gvDanhSachHDGiay.DataSource = []
      }
    },
    async ts_khdn_mnp_list_hd_ccbs(){
      try {
        let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_list_hd_ccbs",{
          schema : this.get_user_info("ma_tinh"),
          page_index : "1",
          ma_dn : this.$refs.txt_ma_dn_CN.value,
          record_per_page : "100",
          userid : this.get_user_info("id")
        })
        this.gvDanhSachHDCCBS.DataSource = data.data.data
        this.cboMaHDCCBS_CN.DataSource =  data.data.data
        this.cboMaHDCCBS_TM.DataSource =  data.data.data 
        //console.log(data.data.data )     
      } catch (err) {
        this.gvDanhSachHDCCBS.DataSource = []
      }
    },
    async ts_khdn_mnp_del(ma_dn){
      this.$bvModal.msgBoxConfirm('Bạn có thật sự muốn xóa dữ liệu này không?', {
                	title: 'Xác nhận xóa dữ liệu',
                	size: 'md',
                  centered: true,
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Không đồng ý',
            	}).then(async v => {
                	if(!v) {
                  	return;
                	}
      try {
        let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_del",{                
          ma_dn : ma_dn,          
          userid : this.get_user_info("id")
        })
        this.$root.$toast.success('Xóa doanh nghiệp thành công.')
        this.ts_khdn_mnp_search();
               
      } catch (err) {
        this.$root.$toast.error('Lỗi khi xóa.')
      }
      });
    
    },
    async ts_khdn_mnp_del_hsg(){
      this.$bvModal.msgBoxConfirm('Bạn có thật sự muốn xóa dữ liệu này không?', {
                	title: 'Xác nhận xóa dữ liệu',
                	size: 'md',
                  centered: true,
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Không đồng ý',
            	}).then(async v => {
                	if(!v) {
                  	return;
                	}
      try {
        let dshdg = this.$refs.gvDanhSachHDGiay.getSelectedRecords()
        let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_xoa_hd_giay",{                
          ma_dn : dshdg[0].MA_DN,  
          ma_hd_giay: dshdg[0].SO_HD_GIAY,
          userid : this.get_user_info("id")
        })
        this.$root.$toast.success('Xóa hợp đồng giấy thành công.')
        //this.ts_khdn_mnp_search();
        this.ts_khdn_mnp_list_hd_giay();       
      } catch (err) {
        this.$root.$toast.error('Lỗi khi xóa.')
      }
              })
    },
    async ts_khdn_mnp_del_ccbs(){
      this.$bvModal.msgBoxConfirm('Bạn có thật sự muốn xóa dữ liệu này không?', {
                	title: 'Xác nhận xóa dữ liệu',
                	size: 'md',
                  centered: true,
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Không đồng ý',
            	}).then(async v => {
                	if(!v) {
                  	return;
                	}
      try {
        let dshdccbs = this.$refs.gvDanhSachHDCCBS.getSelectedRecords()
        let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_xoa_hd_ccbs",{                
          ma_dn : this.$refs.txt_ma_dn_CN.value,  
          ma_hd_ccbs: dshdccbs[0].MA_HD,
          userid : this.get_user_info("id")
        })
        if(data.data.data.result== '1')
          this.$root.$toast.success('Xóa hợp đồng ccbs thành công.')
        else
          this.$root.$toast.success(data.data.data.result.message)
        //this.ts_khdn_mnp_search();
        this.ts_khdn_mnp_list_hd_ccbs();       
      } catch (err) {
        this.$root.$toast.error('Lỗi khi xóa.')
      }
              })
    },
    clickChonDiaChi(value){
          //1: Địa chỉ DN, 2: Địa chỉ DKKD
          this.kieu_chon_diachi=value
          
          if(this.kieu_chon_diachi==1){
            this.dataDiaChi=this.dataDiaChiDN
          }else if(this.kieu_chon_diachi==2){
            this.dataDiaChi=this.dataDiaChiDKKD
          }
          this.$refs.diaChiModal.showModal()
          
    },
    async ts_khdn_mnp_hd_giay_new(){
      try {
        let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_hd_giay_new",{          
          userid : this.get_user_info("id"),
          ngay_hh : this.$refs.dedNgayHetHan_tpThemMoiHDGiay.value == null ? "" : this.$refs.dedNgayHetHan_tpThemMoiHDGiay.value.substring(0,10),
          id : this.$refs.txtSoHopDong_tpThemMoiHDGiay.value, 
          dv_tt_cuoc : this.$refs.txtDonViTTCuoc_tpThemMoiHDGiay.value,
          dvql : this.$refs.txtDVQLHD_tpThemMoiHDGiay.value,
          ghi_chu : this.$refs.txtGhiChu_tpThemMoiHDGiay.value,
          ma_dn : this.$refs.txt_ma_dn_CN.value,
          mobile : this.$refs.txtDienThoai_tpThemMoiHDGiay.value,          
          am : this.$refs.txtTenAMQuanLy_tpThemMoiHDGiay.value,
          ten : this.$refs.txtHoVaTen_tpThemMoiHDGiay.value,
          hdccbs : this.$refs.cbMaHDCCBS_tpThemMoiHDGiay.value

        })
        //hdccbs : " ",
        if(data.data.data.result == '1'){
          this.$root.$toast.success('Thêm hợp đồng giấy thành công.')  
          this.HT_Get_SEQ_CCBS();
        }else
         this.$root.$toast.error('Thêm hợp đồng giấy thất bại.') 
      } catch (err) {
          this.$root.$toast.error('Thêm hợp đồng giấy thất bại.')   
      }
    },
    onAcceptDiaChi(data){
          
          if(this.kieu_chon_diachi==1){
            this.input_diachi_DN_TM=data.diachi
            //this.$refs.txtDCDN_TM.data = data.diachi
            Object.assign(this.dataDiaChiDN, data)
            //Chọn địa chỉ TB apply luôn qua lắp đặt
            this.input_diachi_KD_TM=data.diachi
            //this.$refs.txtDCKD_TM.data = diachi
            Object.assign(this.dataDiaChiDKKD, data)
            //Handle chọn đơn vị tổ thi công
            //this.handleChonToThiCongSauChonTB()

          }else if(this.kieu_chon_diachi==2){
            this.input_diachi_KD_TM=data.diachi
            //this.$refs.txtDCKD_TM.value = diachi
            Object.assign(this.dataDiaChiDKKD, data)
            //Handle chọn đơn vị tổ thi công
            //this.handleChonToThiCongSauChonLD()

          }

    },
    clickChonDiaChi_CN(value){
          //1: Địa chỉ DN, 2: Địa chỉ DKKD
          this.kieu_chon_diachi=value
          
          if(this.kieu_chon_diachi==1){
            this.dataDiaChi_CN=this.dataDiaChiDN_CN
          }else if(this.kieu_chon_diachi==2){
            this.dataDiaChi_CN=this.dataDiaChiDKKD_CN
          }
          this.$refs.diaChiModal_CN.showModal()
          
    },
    onAcceptDiaChi_CN(data){
          
          if(this.kieu_chon_diachi==1){
            this.input_diachi_DN_CN=data.diachi
            //this.$refs.txtDCDN_TM.data = data.diachi
            Object.assign(this.dataDiaChiDN_CN, data)
            //Chọn địa chỉ TB apply luôn qua lắp đặt
            this.input_diachi_KD_CN=data.diachi
            //this.$refs.txtDKKD_CN.data = diachi
            Object.assign(this.dataDiaChiDKKD_CN, data)
            //Handle chọn đơn vị tổ thi công
            //this.handleChonToThiCongSauChonTB()

          }else if(this.kieu_chon_diachi==2){
            this.input_diachi_KD_CN=data.diachi
            //this.$refs.txtDKKD_CN.value = diachi
            Object.assign(this.dataDiaChiDKKD, data)
            //Handle chọn đơn vị tổ thi công
            //this.handleChonToThiCongSauChonLD()

          }

        },
    async ts_khdn_mnp_search(){
      try{
        // console.log(this.$refs.dtpNgayTL_TK.value)
        // if(this.$refs.dtpNgayTL_TK.value != '' || this.$refs.dtpNgayTL_TK.value != null || this.$refs.dtpNgayTL_TK.value != 'null')
        //   var NTL = this.$refs.dtpNgayTL_TK.value.substring(0,10)
        //   else   var NTL=""     
        let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_search",{
          hd_ccbs :this.$refs.txtMaHD_TK.value,
          loai_kh :this.$refs.cboLoaiKH_TK.value,
          ma_tinh : this.$refs.cboMaTinh_TK.value,
          hang_kh : this.$refs.cboPhanHangKH_TK.value,
          hd_giay : this.$refs.txtMHDG_TK.value,
          phan_loai: this.cboPhanLoai_TK.Value,
          userid: this.get_user_info("id"),
          nganh_nghe: this.$refs.cboNganhNghe_TK.value,
          mst : this.$refs.txtMST_TK.value,
          so_tai_khoan :this.$refs.txtSTK_TK.value,
          user_log: this.$refs.txtNCN_TK.value,
          ngay_thanh_lap : this.$refs.dtpNgayTL_TK.value == null ? '' : this.$refs.dtpNgayTL_TK.value.substring(0,10),
          ngan_hang : this.$refs.txtNganHang_TK.value,
          page_index : "1",
          ma_dn : this.$refs.txt_ma_dn_TK.value,
          name : this.$refs.txtTenDN_TK.value,
          so_tb: this.$refs.txtSTB_TK.value,
          record_per_page : "100",
          ma_ns: this.$refs.txtMNS_TK.value,
        })
        this.gvDanhSachTTCT.DataSource = data.data.data;        
        this.UpdateDBSTT();        
      }catch(err){
        this.gvDanhSachTTCT.DataSource = [];
      }      
    },
    async gvDanhSach_FocusedRowChanged(){
        
    },
    async gvDanhSachTTCT_FocusedRowChanged(){

    },
    SelectedTabPage(tab){
      if(tab == 1){
        this.tsbtnTimKiem_tpTimKiem = true;
        this.tsbtnThemMoi_tpTimKiem = true;
        this.tsbtnThemMoi_tpThemMoi = false;
        this.tsbtnCapNhat_tpCapNhat = false;
        this.tsbtnThemHDCCBS_tpCapNhat = false;
        this.tsbtnThemHDGiay_tpCapNhat = false;
        this.$refs.tpCapNhat = false;
        this.$refs.tpThemMoi = false;
        this.isActive = true;
        this.isActive_tpThemMoi = false;
        this.isActive_tpCapnhat = false;      
      }else if (tab == 2){
        this.tsbtnTimKiem_tpTimKiem = false;
        this.tsbtnThemMoi_tpTimKiem = false;
        this.tsbtnThemMoi_tpThemMoi = true;
        this.tsbtnCapNhat_tpCapNhat = false;
        this.tsbtnThemHDCCBS_tpCapNhat = false;
        this.tsbtnThemHDGiay_tpCapNhat = false;
        this.tpCapNhat = false;
        this.tpThemMoi = true;
        this.isActive = false;
        this.isActive_tpThemMoi = true; 
        this.isActive_tpCapnhat = false;       
      }else if(tab == 3){
        this.tsbtnTimKiem_tpTimKiem = false;
        this.tsbtnThemMoi_tpTimKiem = false;
        this.tsbtnThemMoi_tpThemMoi = false;
        this.tsbtnCapNhat_tpCapNhat = true;
        this.tsbtnThemHDCCBS_tpCapNhat = true;
        this.tsbtnThemHDGiay_tpCapNhat = true;
        this.tpCapNhat = true;
        this.tpThemMoi = false;
        this.isActive = false;
        this.isActive_tpThemMoi = false;
        this.isActive_tpCapnhat = true;
        this.selectMiniTab(1);       
      }
    },
    clearHDGiay(){
      this.$refs.dedNgayHetHan_tpThemMoiHDGiay.Text = "  /  /";
      this.$refs.txtDonViTTCuoc_tpThemMoiHDGiay.Text = "";
      this.$refs.txtDVQLHD_tpThemMoiHDGiay.Text = "";
      this.$refs.txtDienThoai_tpThemMoiHDGiay.Text = "";
      this.$refs.txtTenAMQuanLy_tpThemMoiHDGiay.Text = "";
      this.$refs.txtHoVaTen_tpThemMoiHDGiay.Text = "";
      this.$refs.txtGhiChu_tpThemMoiHDGiay.Text = "";
    },
  async ts_khdn_mnp_hd_giay_edit(){
    try{
      console.log(this.cboMaHDCCBS_CN.Value)
      console.log(this.$refs.cboMaHDCCBS_CN.value)
      let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_hd_giay_edit",{
          ngay_hh:this.$refs.dtpNgayHH_tpCapNhatHDGiay.value,
          dv_tt_cuoc: this.$refs.txtDonViTTCuoc_tpCapNhatHDGiay.value,
          dvql:this.$refs.txtDVQLHD_tpCapNhatHDGiay.value,
          ghi_chu: this.$refs.txtGhiChu_tpCapNhatHDGiay.value,
          ma_dn:this.$refs.txt_ma_dn_CN.value,
          mobile:this.$refs.txtDienThoai_tpCapNhatHDGiay.value,
          hdccbs: this.cboMaHDCCBS_CN.Value,
          id:this.$refs.txtSoHopDong_tpCapNhatHDGiay.value,
          am:this.$refs.txtTenAMQuanLy_tpCapNhatHDGiay.value,
          ten:this.$refs.txtHoVaTen_tpCapNhatHDGiay.value,
          userid:'13431'
        })
        this.$root.$toast.success('Cập nhật hợp đồng giấy thành công.')
    }catch(err){
          this.$root.$toast.error(err)
    }
  },
  // async ts_khdn_mnp_hd_giay_new(){
  //   try{
  //     let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_hd_giay_new",{
  //         ngay_hh:this.$refs.dedNgayHetHan_tpThemMoiHDGiay.value,
  //         dv_tt_cuoc: this.$refs.txtDonViTTCuoc_tpThemMoiHDGiay.value,
  //         dvql:this.$refs.txtDVQLHD_tpThemMoiHDGiay.value,
  //         ghi_chu: this.$refs.txtGhiChu_tpThemMoiHDGiay.value,
  //         ma_dn:this.$refs.txt_ma_dn_CN.value,
  //         mobile:this.$refs.txtDienThoai_tpThemMoiHDGiay.value,
  //         hdccbs:this.$refs.cbMaHDCCBS_tpThemMoiHDGiay.value,
  //         id:this.$refs.txtSoHopDong_tpThemMoiHDGiay.value,
  //         am:this.$refs.txtTenAMQuanLy_tpThemMoiHDGiay.value,
  //         ten:this.$refs.txtHoVaTen_tpThemMoiHDGiay.value,
  //         userid:'13431'
  //       })
  //   }catch(err){

  //   }
  // },
  btnNhapLai_tpTaoMoiHDCCBS_Click(){
    this.$refs.txtSoMay_tpTaoMoiHDCCBS.value = "";
    this.$refs.txtTenCQ_tpTaoMoiHDCCBS.value = "";
    this.$refs.txtSoSim_tpTaoMoiHDCCBS.value = "";
    this.$refs.txtMaKH_tpTaoMoiHDCCBS.value = "";
    this.$refs.txtTenTT_tpTaoMoiHDCCBS.value = "";
    this.$refs.txtSoGiayTo_tpTaoMoiHDCCBS.value = "";
    this.$refs.txtMaCQ_tpTaoMoiHDCCBS.value = "";
    this.$refs.txtTenTB_tpTaoMoiHDCCBS.value = "";
    this.$refs.txtDiaChiCQ_tpTaoMoiHDCCBS.value = "";
    this.$refs.txtDiaChiTB_tpTaoMoiHDCCBS.value = "";
    this.grdDanhSach_tpTaoMoiHDCCBS.DataSource = [];
  },
  async ts_khdn_mnp_tracuu_hd(){
    try{
      let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_tracuu_hd",{
          hdortb : this.cboTimTheo.value,
          ma_tinh: this.get_user_info('ma_tinh'),
          ten_cq : this.$refs.txtTenCQ_tpTaoMoiHDCCBS.value,
          ten_tb : this.$refs.txtTenTB_tpTaoMoiHDCCBS.value,
          ma_kh :  this.$refs.txtMaKH_tpTaoMoiHDCCBS.value,
          diachi_cq :  this.$refs.txtDiaChiCQ_tpTaoMoiHDCCBS.value,
          diachi_tb : this.$refs.txtDiaChiCQ_tpTaoMoiHDCCBS.value,
          so_msin : this.$refs.txtSoSim_tpTaoMoiHDCCBS.value,
          trang_thai : this.$refs.cboTrangThai_tpTaoMoiHDCCBS.value,
          userid:this.get_user_info('id'),
          ma_cq : this.$refs.txtMaCQ_tpTaoMoiHDCCBS.value,
          ten_tt : this.$refs.txtTenTT_tpTaoMoiHDCCBS.value,
          kh_rr : this.$refs.cboKHang_tpTaoMoiHDCCBS.value,
          page_index : '1',
          so_gt : this.$refs.txtSoGiayTo_tpTaoMoiHDCCBS.value,
          so_tb : this.$refs.txtSoMay_tpTaoMoiHDCCBS.value,
          record_per_page : '100'
        })
        this.grdDanhSach_tpTaoMoiHDCCBS.DataSource = data.data.data
    }catch(err){
        this.grdDanhSach_tpTaoMoiHDCCBS.DataSource = [];
    }
  },
  async ts_khdn_mnp_new_hd_ccbs(hdtype){
    try{
      if(this.grdDanhSach_tpTaoMoiHDCCBS.DataSource == []){
        this.$root.$toast.error('Tìm thuê bao trước khi thêm mới')
        return false
      }
      let md_hd = this.$refs.grdDanhSach_tpTaoMoiHDCCBS.getSelectedRecords()      
      let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_new_hd_ccbs",{
          ma_hd_ccbs : md_hd[0].MA_HD,
          ma_dn : this.$refs.txt_ma_dn_CN.value,
          hdtype : hdtype,
          userid : this.get_user_info("id")
        })
      if(data.data.data.result == '1'){
        this.$root.$toast.success('Thêm mới hợp đồng ccbs thành công.')
      }else this.$root.$toast.error(data.data.data.result.message)          
    }catch(err){
        this.$root.$toast.error('Thêm mới hợp đồng ccbs thất bại.')  
    }
  },
  get_user_info(type){
    let user = JSON.parse(localStorage.getItem('OneBSS-Client'))
    if(type == "id"){
      //console.log(user.id_nhanvien)
      return user.id_nhanvien
    }else if(type == "ma_tinh"){
      return user.ma_tinh
    }else if(type == "id_tinh"){
     return user.id_tinh
    }else if(type == "ten_nv"){
      return user.ten_nv
    }
  },
  validAll () {
      this.ds_valid = []
      this.$v.$touch()
      this.ValidTM()
      this.validCombox()

      if (this.ds_valid.length > 0) {
        this.ds_valid.sort((validA, validB) => { return validA.order - validB.order })
        console.log("valid sorted: ", this.ds_valid)
        var isAlreadyFocus = false        
        this.ds_valid.slice().reverse().forEach(item => {
          this.$toast.error(item.msg)
          // if (item.ref && !isAlreadyFocus) {
          //   console.log("focused : " + item.msg)
          //   item.ref.focus()
          //   isAlreadyFocus = true
          // }
        }
        )
        this.ds_valid.forEach(item => {          
          if (item.ref && !isAlreadyFocus) {
            console.log("focused : " + item.msg)
            item.ref.focus()
            isAlreadyFocus = true
          }
        }
        )
        return false
      }
      return true
    },
    validAllCN () {
      this.ds_valid = []
      this.$v.$touch()
      this.ValidCN()
      //this.validCombox()

      if (this.ds_valid.length > 0) {
        this.ds_valid.sort((validA, validB) => { return validA.order - validB.order })
        console.log("valid sorted: ", this.ds_valid)
        var isAlreadyFocus = false        
        this.ds_valid.slice().reverse().forEach(item => {
          this.$toast.error(item.msg)          
        }
        )
        this.ds_valid.forEach(item => {          
          if (item.ref && !isAlreadyFocus) {
            console.log("focused : " + item.msg)
            item.ref.focus()
            isAlreadyFocus = true
          }
        }
        )
        return false
      }
      return true
    },
    validCombox () {
      var vmsg = null
      var vref = null
      if (this.KHDN_TM.cboPhanLoai_TM == 0) {
        //vref = this.$refs.cboPhanLoai_TM
        vmsg = "Bạn phải chọn phân loại"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.cboPhanLoai_TM})
      }
      if (this.KHDN_TM.cboNganhNghe_TM == 0) {
        //vref = this.$refs.cboPhanLoai_TM
        vmsg = "Bạn phải chọn ngành nghề"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.cboNganhNghe_TM})
      }
      if (this.KHDN_TM.cboPhanHangKH_TM == 0) {
        //vref = this.$refs.cboPhanLoai_TM
        vmsg = "Bạn phải chọn phân hạng khách hàng"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.cboPhanHangKH_TM})
      }
      if (this.KHDN_TM.cboLoaiKH_TM==0) {
        // this.$refs.quocTich.focus()
        //vref = this.$refs.cboLoaiKH_TM
        vmsg = "Bạn phải chọn loại khách hàng"
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.cboLoaiKH_TM})
      }      
      
    },
  ValidTM(){    
    var vmsg = null
    var vref = null 
    if (!this.$v.KHDN_TM.txtMST_TM.required) {      
        vmsg = "Mã số thuế không được để trống"
        vref = this.$refs.txtMST_TM
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.txtMST_TM})
      }   
      if (!this.$v.KHDN_TM.TenDN.required) {      
        vmsg = "Tên DN không được để trống"
        vref = this.$refs.txtTenDN_TM
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.TenDN})
      }

      
      
      // if (this.$refs.txtTenDN_TM.value == '' || this.$refs.txtTenDN_TM.value == null) {
      //   this.$root.$toast.error('Tên doanh nghiệp không được để trống')
      //   this.$refs.txtTenDN_TM.focus()
      //   return false
      // }
      // if (this.$refs.cboMaTinh_TM.value == '' || this.$refs.cboMaTinh_TM.value == null) {
      //   this.$root.$toast.error('Mã tỉnh không được để trống!')        
      //   return false
      // }
      // if (this.$refs.cboNganhNghe_TM.value == '' || this.$refs.cboNganhNghe_TM.value == null) {
      //   this.$root.$toast.error('Ngành nghề không được để trống')
      //   this.$refs.cboNganhNghe_TM.focus()
      //   return false
      // }
      // if (this.cboPhanLoai_TM.Value == '' || this.cboPhanLoai_TM.Value == null) {
      //   this.$root.$toast.error('Phân loại không được để trống')
      //   return false
      // }
      // if (this.$refs.cboPhanHangKH_TM.value == '' || this.$refs.cboPhanHangKH_TM.value== null) {
      //   this.$root.$toast.error('Phân hạng không được để trống')
      //   return false
      // }
      // if (this.$refs.txtMST_TM.value == '' || this.$refs.txtMST_TM.value == null) {
      //   this.$root.$toast.error('Mã số thuế không được để trống')
      //   this.$refs.txtMST_TM.focus()
      //   return false
      // }     
      
      // if (this.$refs.cboLoaiKH_TM.value == '' || this.$refs.cboLoaiKH_TM.value == null) {
      //   this.$root.$toast.error('Loại khách hàng không được để trống')
      //   return false
      // }
  },
  ValidCN(){    
    var vmsg = null
    var vref = null 
    if (!this.$v.KHDN_CN.txtMST_CN.required) {      
        vmsg = "Mã số thuế không được để trống"
        vref = this.$refs.txtMST_CN
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.txtMST_TM})
      }   
      if (!this.$v.KHDN_CN.TenDN.required) {      
        vmsg = "Tên DN không được để trống"
        vref = this.$refs.txtTenDN_CN
        this.ds_valid.push({msg: vmsg, ref: vref, order: ValidOrder.TenDN})
      }
  },

  async Add_NewDN(){
    if (!this.validAll()) {
          return false
        }      
      
      if (this.$refs.txtMST_TM.value.length == 10 || this.$refs.txtMST_TM.value.length ==14) {        
    try{
      let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_new",{
          ref_id:this.$refs.txt_Ref_ID.value ,
          loai_kh: this.$refs.cboLoaiKH_TM.value,
          ma_tinh: this.$refs.cboMaTinh_TM.value,
          dd_ho_ten: this.$refs.txt_Hoten_TM.value,
          hang_kh: this.$refs.cboPhanHangKH_TM.value,
          addr_dkkd:this.$refs.txtDCKD_TM.value,
          dd_mobile:  this.$refs.txt_Moblie_TM.value,
          phan_loai: this.KHDN_TM.cboPhanLoai_TM,
          userid: this.get_user_info('id'),
          nganh_nghe: this.$refs.cboNganhNghe_TM.value,  
          mst: this.$refs.txtMST_TM.value,
          so_tai_khoan: this.$refs.txtSoTK_TM.value,
          ngay_thanh_lap: this.$refs.txt_NgaythanhLap_TM.value == null ? '' : this.$refs.txt_NgaythanhLap_TM.value.substring(0,10),
          ngan_hang: this.$refs.txtMsNH_TM.value,
          ma_dn: this.$refs.txtMaDN_TM.value,
          name:this.$refs.txtTenDN_TM.value,
          dd_cmt: this.$refs.txt_CMT_TM.value,
          addr: this.$refs.txtDCDN_TM.value,
          dd_ngay_sinh: this.$refs.dtp_NgaySinh_TM.value == null ? '' : this.$refs.dtp_NgaySinh_TM.value.substring(0,10),
          dd_ngaycap_cmt: this.$refs.dtpNgayCap_TM.value == null ? '' : this.$refs.dtpNgayCap_TM.value.substring(0,10),
          ma_ns:this.$refs.txtMaNS_TM.value 
        })
        //console.log(data.data.data.result)
        if(data.data.data.result == '1'){
        this.$root.$toast.success('Thêm mới doanh nghiệp thành công.')
        this.HT_Get_SEQ()
        }else{
          this.$root.$toast.error(data.data.data.result.message)
        }

            
    }catch(err){
           this.$root.$toast.error('Lỗi khi thêm doanh nghiệp',data.data.data.result.message) 
    }
    
    }else {this.$root.$toast.error('Mã số thuế chỉ có 10 hoặc 14 số')
        return false}
  },
  async Update_DN(){ 
      if (!this.validAllCN()) {
          return false
        }      
      // if (this.$refs.txt_ma_dn_CN.value == '' || this.$refs.txt_ma_dn_CN.value == null) {
      //   this.$root.$toast.error('Mã doanh nghiệp không được để trống')
      //   return false
      // }
      // //console.log( this.$refs.cboKenhTT.value)
      // if (this.$refs.txtTDN_CN.value == '' || this.$refs.txtTDN_CN.value == null) {
      //   this.$root.$toast.error('Tên doanh nghiệp không được để trống')
      //   this.$refs.txtTDN_CN.focus()
      //   return false
      // }
      // if (this.$refs.cboMaTinh_CN.value == '' || this.$refs.cboMaTinh_CN.value == null) {
      //   this.$root.$toast.error('Mã tỉnh không được để trống!')
      //   return false
      // }
      // if (this.$refs.cboNganhNghe_CN.value == '' || this.$refs.cboNganhNghe_CN.value == null) {
      //   this.$root.$toast.error('Ngành nghề không được để trống')
      //   return false
      // }
      // // if (this.$refs.cboPhanLoai_TM.value == '' || this.$refs.cboPhanLoai_TM.value == null) {
      // //   this.$root.$toast.warning('Phân loại không được để trống')
      // //   return false
      // // }
      // if (this.$refs.cboPhanHangKH_CN.value == '' || this.$refs.cboPhanHangKH_CN.value== null) {
      //   this.$root.$toast.error('Phân hạng không được để trống')
      //   return false
      // }
      // if (this.$refs.txtMST_CN.value == '' || this.$refs.txtMST_CN.value == null) {
      //   this.$root.$toast.error('Mã số thuế không được để trống')
      //   this.$refs.txtMST_CN.focus()
      //   return false
      // }     
      
      // if (this.$refs.cboLoaiKH_CN.value == '' || this.$refs.cboLoaiKH_CN.value == null) {
      //   this.$root.$toast.error('Loại khách hàng không được để trống')
      //   return false
      // }
      if (this.$refs.txtMST_CN.value.length == 10 || this.$refs.txtMST_CN.value.length ==14) {        
    try{
      let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_edit",{
          ref_id:this.$refs.txtrefID_CN.value ,
          loai_kh: this.$refs.cboLoaiKH_CN.value,
          ma_tinh: this.$refs.cboMaTinh_CN.value,
          dd_ho_ten: this.$refs.txtHT_CN.value,
          hang_kh: this.$refs.cboPhanHangKH_CN.value,
          addr_dkkd:this.$refs.txtDKKD_CN.value,
          dd_mobile:  this.$refs.txtSDT_CN.value,
          phan_loai: this.cboPhanLoai_CN.Value,
          userid: this.get_user_info('id'),
          nganh_nghe: this.$refs.cboNganhNghe_CN.value,  
          mst: this.$refs.txtMST_CN.value,
          so_tai_khoan: this.$refs.txtSTK_CN.value,
          ngay_thanh_lap: this.$refs.dtpNgayTL_CN.value == null ? "" :this.$refs.dtpNgayTL_CN.value.substring(0,10),
          ngan_hang: this.$refs.txtNH_CN.value,
          ma_dn: this.$refs.txt_ma_dn_CN.value,
          name:this.$refs.txtTDN_CN.value,
          dd_cmt: this.$refs.txtCMT_CN.value,
          addr: this.$refs.txtDCDN_CN.value,
          dd_ngay_sinh: this.$refs.dtpNgaysinh_CN.value == null ? "" :this.$refs.dtpNgaysinh_CN.value.substring(0,10),
          dd_ngaycap_cmt: this.$refs.dtpNgaycap_CN.value == null ? "" :this.$refs.dtpNgaycap_CN.value.substring(0,10),
          ma_ns:this.$refs.txtMNS_CN.value 
        })
        //console.log(data.data.data.result)
        if(data.data.data.result == '1'){
        this.$root.$toast.success('Cập nhật doanh nghiệp thành công.')        
        }else{
          this.$root.$toast.error(data.data.data.result.message)
        }

            
    }catch(err){
           this.$root.$toast.error('Lỗi khi cập nhật doanh nghiệp',data.data.data.result.message) 
    }
    
    }else {this.$root.$toast.error('Mã số thuế chỉ có 10 hoặc 14 số')
        return false}
  },
  HDG_change_tab(){
    this.Tab_PhuChange(1); 
    this.selectMiniTab(3);    
    let dshdg = this.$refs.gvDanhSachHDGiay.getSelectedRecords()
    this.$refs.txtSoHopDong_tpCapNhatHDGiay.value = dshdg[0].SO_HD_GIAY
    this.$refs.dtpNgayHH_tpCapNhatHDGiay.value = dshdg[0].NGAY_HET_HAN
    this.$refs.txtDVQLHD_tpCapNhatHDGiay.value = dshdg[0].DONVI_QL_HD
    this.cboMaHDCCBS_CN.Value = dshdg[0].MA_HD_CCBS
    this.$refs.txtDonViTTCuoc_tpCapNhatHDGiay.value = dshdg[0].DON_VI_TT_CUOC
    this.$refs.txtTenAMQuanLy_tpCapNhatHDGiay.value = dshdg[0].AM
    this.$refs.txtHoVaTen_tpCapNhatHDGiay.value = dshdg[0].CONTACT_NAME
    this.$refs.txtDienThoai_tpCapNhatHDGiay.value = dshdg[0].CONTACT_MOBILE
    this.$refs.txtGhiChu_tpCapNhatHDGiay.value = dshdg[0].NOTE.replace('[ONEBSS]','')

  },
  Update_change(){ 
      this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật dữ liệu không?', {
                	title: 'Xác nhận cập nhật',
                  centered: true,
                	size: 'md',
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Không đồng ý',
            	}).then(async v => {
                	if(!v) {
                  	return;
                	} 
    let dsKMTT = this.$refs.gvDanhSachTTCT.getSelectedRecords()
        this.SelectedTabPage(3);
        this.$refs.txtrefID_CN.value = dsKMTT[0].REF_ID 
        for(i =0; i<this.cboLoaiKH.DataSource.length;i ++){          
          if(this.cboLoaiKH.DataSource[i].LOAI_KH == dsKMTT[0].LOAI_KH){       
            this.cboLoaiKH_CN.Value = this.cboLoaiKH.DataSource[i].LOAIKH_ID            
          }}        
        //this.$refs.cboMaTinh_CN.value = dsKMTT[0].MA_TINH 
        this.$refs.txtHT_CN.value = dsKMTT[0].DD_HO_TEN
        let i = 0;         
        for(i =0; i<this.cboPhanHangKH.DataSource.length;i ++){          
          if(this.cboPhanHangKH.DataSource[i].NAME == dsKMTT[0].HANG_KH){       
            this.cboPhanHangKH_CN.Value = this.cboPhanHangKH.DataSource[i].ID            
          }}
        this.input_diachi_KD_CN = dsKMTT[0].ADDR_DKKD 
        this.$refs.txtSDT_CN.value = dsKMTT[0].DD_MOBILE 
        this.cboPhanLoai_CN.Value = dsKMTT[0].PHAN_LOAI 
        this.$refs.cboPhanLoai_CN.value = dsKMTT[0].PHAN_LOAI;
        for(i =0; i<this.cboNganhNghe.DataSource.length;i ++){          
          if(this.cboNganhNghe.DataSource[i].NGANHNGHE == dsKMTT[0].NGANH_NGHE){       
            this.cboNganhNghe_CN.Value = this.cboNganhNghe.DataSource[i].NGANHNGHE_ID            
          }}        
        this.$v.KHDN_CN.txtMST_CN.$model = dsKMTT[0].MST 
        this.$refs.txtSTK_CN.value = dsKMTT[0].SO_TAI_KHOAN 
        this.$refs.dtpNgayTL_CN.value = dsKMTT[0].NGAY_THANH_LAP 
        this.$refs.txtNH_CN.value = dsKMTT[0].NGAN_HANG 
        this.$refs.txt_ma_dn_CN.value = dsKMTT[0].MA_DN 
        this.$v.KHDN_CN.TenDN.$model = dsKMTT[0].NAME 
        this.$refs.txtCMT_CN.value = dsKMTT[0].DD_CMT 
        this.input_diachi_DN_CN = dsKMTT[0].ADDR 
        this.$refs.dtpNgaysinh_CN.value = dsKMTT[0].DD_NGAY_SINH 
        this.$refs.dtpNgaycap_CN.value = dsKMTT[0].DD_NGAYCAP_CMT 
        this.$refs.txtMNS_CN.value  = dsKMTT[0].MA_NGANSACH
        this.$refs.txtNN_CN.value  = dsKMTT[0].CREATE_USER
        this.$refs.dtpNgayCN_CN.value  = dsKMTT[0].MODIFY_DATE
        this.ts_khdn_mnp_list_hd_giay();
        this.ts_khdn_mnp_list_hd_ccbs();
        this.$refs.txtTDN_CN.focus()
              });

  },
  UpdateHDG_change(){ 
      this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật dữ liệu không?', {
                	title: 'Xác nhận cập nhật',
                  centered: true,
                	size: 'md',
                	okTitle: 'Đồng ý',
                	cancelTitle: 'Không đồng ý',
            	}).then(async v => {
                	if(!v) {
                  	return;
                	}
                  this.HDG_change_tab()
              })
    },
    SetDataPL(id){
      console.log(this.dataPL)
      this.dataPL = id;
      console.log(this.dataPL)
    },
    async Update_HDG(){
    //try{     
      //console.log(this.cboMaHDCCBS_CN.Value)
      //console.log(this.$refs.cboMaHDCCBS_tpCapNhatHDGiay.value) 
      let data = await this.axios.post("ccbs/executor/ts_khdn_mnp_hd_giay_edit",{
        id :  this.$refs.txtSoHopDong_tpCapNhatHDGiay.value,
        ngay_hh:  this.$refs.dtpNgayHH_tpCapNhatHDGiay.value == null ? "" : this.$refs.dtpNgayHH_tpCapNhatHDGiay.value.substring(0,10),
        dvql : this.$refs.txtDVQLHD_tpCapNhatHDGiay.value,
        hdccbs : this.cboMaHDCCBS_CN.Value,
        dv_tt_cuoc: this.$refs.txtDonViTTCuoc_tpCapNhatHDGiay.value,
        am : this.$refs.txtTenAMQuanLy_tpCapNhatHDGiay.value,
        ten: this.$refs.txtHoVaTen_tpCapNhatHDGiay.value,
        mobile: this.$refs.txtDienThoai_tpCapNhatHDGiay.value,
        ghi_chu :this.$refs.txtGhiChu_tpCapNhatHDGiay.value ,
        ma_dn :this.$refs.txt_ma_dn_CN.value ,
        userid : this.get_user_info('id')
        })
        //console.log(data.data.data.result)
        if(data.data.data.result == '1'){
        this.$root.$toast.success('Cập nhật hợp đồng giấy thành công')  
        this.Tab_PhuChange(1);   
        }else{
          this.$root.$toast.error(data.data.data.result.message)
        }

            
    // }catch(err){
    //        this.$root.$toast.error(err) 
    // }
    },

    Tab_PhuChange(tab){
      if ( tab == 1) //tpCapNhatHDGiay
            { this.tpCapNhatHDGiay = true }
            else if (tab == 2) //tpHopDongCCBS
            {
                this.tpCapNhatHDGiay = false
                //tpCapNhatHDGiay.PageVisible = false;
                //bindHopDongCCBS();
            }
            else if (tab == 3) //tpHopDongGiay
            {
                this.tpCapNhatHDGiay = false
                //this.tpCapNhatHDGiay.PageVisible = false;
                //bindHopDongGiay();
            }
            else if (tab == 4) //tpTaoMoiHDCCBS_tpCapNhat
            {

            }
            else if (tab == 5) //tpTaoMoiHDGiay_tpCapNhat
            {
                
            }
    },
  GetColumnDelete(parent){
    return function() {
      return {
        template : {
          template : `          
            <button class="btn"  @click="click()">
              <span class="icon one-trash"></span> Xóa
            </button>          
          `,
          data(){
            return {
              parent : parent,            
              data : {},
            }
          },
          read : () =>{
            
          },
          mounted (){
            
            
          }, 
          methods :{
            click(){
              //console.log(this.data.MA_DN)
              this.parent(this.data.MA_DN)
            }
          }
        }
      }
    }
  },
  GetColumnDelHDG(parent){
    return function() {
      return {
        template : {
          template : `          
            <button class="btn"  @click="click()">
              <span class="icon one-trash"></span> Xóa
            </button>          
          `,
          data(){
            return {
              parent : parent,            
              data : {},
            }
          },
          read : () =>{
            
          },
          mounted (){
            
            
          }, 
          methods :{
            click(){
              //console.log(this.data.MA_DN)
              this.parent()
            }
          }
        }
      }
    }
  },
  GetColumnDelCCBS(parent){
    return function() {
      return {
        template : {
          template : `          
            <button class="btn"  @click="click()">
              <span class="icon one-trash"></span> Xóa
            </button>          
          `,
          data(){
            return {
              parent : parent,            
              data : {},
            }
          },
          read : () =>{
            
          },
          mounted (){
            
            
          }, 
          methods :{
            click(){
              //console.log(this.data.MA_DN)
              this.parent()
            }
          }
        }
      }
    }
  },
  GetColumnUpdate(parent){
    return function() {
      return {
        template : {
          template : `
                
            <button class="btn"  @click="click()">
              <span class="icon one-save"></span> Cập nhật
            </button>    
              
          `,
          data(){
            return {
              parent : parent,            
              data : {},
            }
          },
          read : () =>{
            
          },
          mounted (){
            
            
          }, 
          methods :{
            click(){
              //console.log(this.data.MA_DN)
              this.parent()
            }
          }
        }
      }
    }
  },GetColumnUpdateHDG(parent){
    return function() {
      return {
        template : {
          template : `                
            <button class="btn"  @click="click()">
              <span class="icon one-save"></span> Cập nhật
            </button>    
           
          `,
          data(){
            return {
              parent : parent,            
              data : {},
            }
          },
          read : () =>{
            
          },
          mounted (){
            
            
          }, 
          methods :{
            click(){
              //console.log(this.data.MA_DN)
              this.parent()
            }
          }
        }
      }
    }
  }
  ,
  UpdateDBSTT(){    
    let j =0;
    let i =0;    
    
    for(j ;j< this.gvDanhSachTTCT.DataSource.length ; j++){
      if(this.gvDanhSachTTCT.DataSource[j].MODIFY_DATE != null)
      this.gvDanhSachTTCT.DataSource[j].MODIFY_DATE = this.gvDanhSachTTCT.DataSource[j].MODIFY_DATE.split("/").reverse().join("-");
    }
    
    for(i =0 ;i< this.gvDanhSachTTCT.DataSource.length ; i++){
      for(j =0 ;j< this.gvDanhSachTTCT.DataSource.length ; j++){
        //if(this.gvDanhSachTTCT.DataSource[i].MODIFY_DATE != null && this.gvDanhSachTTCT.DataSource[j].MODIFY_DATE != null){
        if(new Date(this.gvDanhSachTTCT.DataSource[i].MODIFY_DATE) > new Date(this.gvDanhSachTTCT.DataSource[j].MODIFY_DATE)){
          this.DataSource = this.gvDanhSachTTCT.DataSource[i]
          this.gvDanhSachTTCT.DataSource[i] = this.gvDanhSachTTCT.DataSource[j]
          this.gvDanhSachTTCT.DataSource[j] = this.DataSource          
        }else {
          // this.DataSource = this.gvDanhSachTTCT.DataSource[j]
          // this.gvDanhSachTTCT.DataSource[j] = this.gvDanhSachTTCT.DataSource[i]
          // this.gvDanhSachTTCT.DataSource[i] = this.DataSource          
        }
        
        //}
      }
    } 
    
    for(j =0 ;j< this.gvDanhSachTTCT.DataSource.length ; j++){
      if(this.gvDanhSachTTCT.DataSource[j].MODIFY_DATE != null)
      this.gvDanhSachTTCT.DataSource[j].MODIFY_DATE = this.gvDanhSachTTCT.DataSource[j].MODIFY_DATE.split("-").reverse().join("/");
    }
    
    for(i =0 ;i< this.gvDanhSachTTCT.DataSource.length ; i++){
      this.gvDanhSachTTCT.DataSource[i].STT = i+1;
    }
  },
  selectMiniTab(tab){
    if(tab==1){
      this.isActive_tpCapnhat_CCBS = false;
      this.isActive_tpCapnhat_HDG = false;
      this.isActive_tpCapnhat_listCCBS = true;
      this.isActive_tpCapnhat_listHDG = false;
      this.isActive_tpCapnhat_UHDG = false;
      this.Tab_PhuChange(2)
    }else if(tab==2){
      this.isActive_tpCapnhat_CCBS = false;
      this.isActive_tpCapnhat_HDG = false;
      this.isActive_tpCapnhat_listCCBS = false;
      this.isActive_tpCapnhat_listHDG = true;
      this.isActive_tpCapnhat_UHDG = false;
      this.Tab_PhuChange(2)
    }else if(tab==3){
      this.isActive_tpCapnhat_CCBS = false;
      this.isActive_tpCapnhat_HDG = false;
      this.isActive_tpCapnhat_listCCBS = false;
      this.isActive_tpCapnhat_listHDG = false;
      this.isActive_tpCapnhat_UHDG = true;      
    }else if(tab==4){
      this.isActive_tpCapnhat_CCBS = false;
      this.isActive_tpCapnhat_HDG = true;
      this.isActive_tpCapnhat_listCCBS = false;
      this.isActive_tpCapnhat_listHDG = false;
      this.isActive_tpCapnhat_UHDG = false;
      this.Tab_PhuChange(2)
    }else if(tab==5){
      this.isActive_tpCapnhat_CCBS = true;
      this.isActive_tpCapnhat_HDG = false;
      this.isActive_tpCapnhat_listCCBS = false;
      this.isActive_tpCapnhat_listHDG = false;
      this.isActive_tpCapnhat_UHDG = false;
      this.Tab_PhuChange(2)
    }

  }

    

    
  
  },
  computed: {
    
  }
}
</script>
<style scoped>
.non-ative a {
  color: #d3d3d3 !important;
}

.error {
        border: 1px solid transparent; border-radius: 5px;
        border-color: #dc3545 !important;
    }
</style>

