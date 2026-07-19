<template src="./AcceptContractModification.html"></template>
<style>
.grid-ext .e-grid .e-rowcell.e-active 
{ font-weight: bold; }
</style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
import popupDSBD from './popupDSBD.vue'
import popupCT_Tien_KM from './popupCT_Tien_KM.vue'
import Hieuchinh_HopDong_CT from './Hieuchinh_HopDong_CT.vue'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split';
import '@/assets/vendor/bootstrap-table/bootstrap-table.min';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min';
import VueElement from '@/modules/contract/setup/DeclareLandline/components/VueElement.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from "@/modules/contract/setup/SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue";
//
export default {
  components: {breadcrumb,VueFlatPickr,API,SearchContractModal,VueElement,popupDSBD,popupCT_Tien_KM,Hieuchinh_HopDong_CT,ThayDoiKhuVucQuanLyThueBaoModal},
  name: 'AcceptContractModification',
  mounted: async function () {
    setTimeout(function(){
      $(function() {
          Split(['#boxLeft', '#boxRight'], {
          sizes: [50, 50],
          gutterSize: 16,
          onDragEnd: function (sizes) {
          },
        });
        Split(['#boxLeft1', '#boxRight1'], {
          sizes: [50, 50],
          gutterSize: 16,
          onDragEnd: function (sizes) {
          },
        });
      })
    }, 500);

    
    this.loading(true);
    this.nguoidung_id = this.$root.token.getNguoiDungID();
   

    this.tag_form = this.$route.query.tag;
    this.tsbtnHoanThanh.Text = "Hoàn thành"

    if (this.tag_form == 1) // Form tiếp nhận
    {
        this.tsbtnHoanThanh.Visible = false;
        this.tsbtnXacNhan.Visible = false;
        this.tsbtnHieuChinh.Visible = false;
        this.label7.Text = "TIẾP NHẬN YÊU CẦU HIỆU CHỈNH THÔNG TIN HỢP ĐỒNG";
    }
    else if (this.tag_form == 2) // Form Kế toán Xác nhận hiệu chỉnh
    {
        this.tsbtnGhiLai.Visible = false;
        this.tsbtnNhapMoi.Visible = false;
        this.tsbtnXoa.Visible = false;
        this.tsbtnHoanThanh.Visible = false;
        this.tsbtnHieuChinh.Visible = false;
        this.label7.Text = "KẾ TOÁN TTKD XÁC NHẬN YÊU CẦU HIỆU CHỈNH";

        this.cboNhomHC.Enabled = false;
        this.cboYC_HieuChinh.Enabled = false;
    }
    else if (this.tag_form == 3) // Form TTVT Xác nhận hiệu chỉnh
    {
        this.tsbtnGhiLai.Visible = false;
        this.tsbtnNhapMoi.Visible = false;
        this.tsbtnXoa.Visible = false;
        this.tsbtnHoanThanh.Visible = false;
        this.tsbtnHieuChinh.Visible = false;
        this.label7.Text = "TTVT XÁC NHẬN YÊU CẦU HIỆU CHỈNH";

        this.cboNhomHC.Enabled = false;
        this.cboYC_HieuChinh.Enabled = false;
    }
    else if (this.tag_form == 4) // Form Xử lý hiệu chỉnh
    {
        this.label7.Text = "XỬ LÝ YÊU CẦU HIỆU CHỈNH THÔNG TIN HỢP ĐỒNG";
        this.tsbtnXacNhan.Visible = false;
        this.tsbtnGhiLai.Visible = false;
        this.tsbtnNhapMoi.Visible = false;
        this.tsbtnXoa.Visible = false;
        this.cboNhomHC.Enabled = false;
        this.tsbtnHoanThanh.Text = "Khóa phiếu";
        this.cboYC_HieuChinh.Enabled = false;
    }

    var data = {
      "chuoi":"TRANG_BI|LOAI_TB|CHU_QUAN|TIEN_KM|DOITUONG|DICHVU_VT|LOAIHINH_TB|LOAI_KHL|LOAI_GT|PHANLOAI_KH|LOAI_KH|NGANHANG|HINHTHUC_TT|HINHTHUC_TC|DANTOC|NGANHNGHE|QUOCTICH|HINHTHUC_TTHI|KIEU_HD|GOI_KT|DONVI_QL|KIEU_LD|NHACC_CU|CONGNGHE|HINHTHUC_CM|THOIHAN|LOAI_CSHT|DOITAC|LOAI_IP|PHANLOAI_DV|LOAI_HC|NHOM_HC"};

      await API.lay_danhmuc_hieuchinh(this.axios,data).then(response=>{
        if(response && response.data && response.data.data.nganhnghe && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.nganhnghe.list = response.data.data.nganhnghe.map(x=>{return {id:x['nganhnghe_id'],text:x['tennganhnghe']}});
        

        if(response && response.data && response.data.data.loai_kh && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.loai_kh.list = response.data.data.loai_kh.map(x=>{return {id:x['loaikh_id'],text:x['ten_loaikh']}});
        

        if(response && response.data && response.data.data.quoctich && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.quoctich.list = response.data.data.quoctich.map(x=>{return {id:x['quoctich_id'],text:x['tenquoctich']}});
        

        if(response && response.data && response.data.data.dantoc && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.dantoc.list = response.data.data.dantoc.map(x=>{return {id:x['dantoc_id'],text:x['tendantoc']}});
        

        if(response && response.data && response.data.data.nhom_hc && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.nhom_hc.list = response.data.data.nhom_hc.map(x=>{return {id:x['nhomhc_id'],text:x['nhom_hc']}});
        

        if(response && response.data && response.data.data.loai_hc && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.loai_hc.list_full = response.data.data.loai_hc;
        

        if(response && response.data && response.data.data.loai_gt && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.loai_giay_to.list = response.data.data.loai_gt.map(x=>{return {id:x['loaigt_id'],text:x['loai_gt']}});
        

        if(response && response.data && response.data.data.donvi_ql && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.don_vi_ql.list = response.data.data.donvi_ql.filter(el => el.loaidv_id == 2).map(x=>{return {id:x['donvi_id'],text:x['ten_dv']}});
        

        if(response && response.data && response.data.data.hinhthuc_tt && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.hinh_thuc_tt.list = response.data.data.hinhthuc_tt.map(x=>{return {id:x['httt_id'],text:x['hinhthuc_tt']}});
        

        if(response && response.data && response.data.data.nganhang && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.nganhang.list = response.data.data.nganhang.map(x=>{return {id:x['nganhang_id'],text:x['ten_nh']}});
        

        if(response && response.data && response.data.data.hinhthuc_tc && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.hinh_thuc_tc.list = response.data.data.hinhthuc_tc.map(x=>{return {id:x['httc_id'],text:x['hinhthuc_tc']}});

        
        if(response && response.data && response.data.data.dichvu_vt && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.dichvu_vt.list = response.data.data.dichvu_vt.map(x=>{return {id:x['dichvuvt_id'],text:x['ten_dvvt']}});

        if(response && response.data && response.data.data.loaihinh_tb && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.loaihinh_tb.list_full = response.data.data.loaihinh_tb;

        if(response && response.data && response.data.data.kieu_ld && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.kieu_ld.list = response.data.data.kieu_ld.map(x=>{return {id:x['kieuld_id'],text:x['ten_kieuld']}});   

        if(response && response.data && response.data.data.donvi_ql && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.tb_donvi_ql.list = response.data.data.donvi_ql.filter(el => el.loaidv_id == 3).map(x=>{return {id:x['donvi_id'],text:x['ten_dv']}});   
        
        if(response && response.data && response.data.data.doituong && response.data.error && response.data.error_code === 'BSS-00000000') 
              this.listbox.doituong.list = response.data.data.doituong.map(x=>{return {id:x['doituong_id'],text:x['ten_dt']}});  



      });
      console.log(this.listbox.don_vi_ql.list);


      API.sp_lay_ds_thongtin_xacnhan(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.thong_tin_xn.list = response.data.data.map(x=>{return {id:x['ketquaxn_id'],text:x['ketqua_xn']}});
        }
      });

      API.sp_lay_ds_thongtin_ketqua(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.thong_tin_ht.list = response.data.data.map(x=>{return {id:x['ketquaht_id'],text:x['ketqua_ht']}});
        }
      });

      this.Lay_DS_Phieu_HieuChinh();
      //this.listbox.dichvu_vt.list.value = 4;


    },


    


  data() {
     return {

        dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true,
      },
      tag_form : 1,
      nguoidung_id:"",

      label7:{Text : ""},
      tsbtnNhapMoi:{Visible : true},
      tsbtnGhiLai:{Visible : true},
      tsbtnXoa:{Visible : true},
      tsbtnXacNhan:{Visible : true},
      tsbtnHieuChinh:{Visible : true},
      tsbtnHoanThanh:{Text : "",Visible:true},
      cboNhomHC:{Enabled : true},
      cboYC_HieuChinh:{Enabled : true},
      
      gt_khong:false,
      gt_nam:false,
      gt_nu:false, 
      nganhang_enable:false,

      hdkh_id:0,

      phieu_yc:{
        ma_gd:""
      },
      yeucau_hc:"",
      noidung_xn:"",
      noidung_ht:"",
      thongtin_hd:"",
      thuebao:{},
      tien:0,
      vat:0,
      tongtien:0,
      thuebao_checked:[],
      thanhtoan_checked:[],

      tieunganh: {
        dsTNC1: [],
        dsTNC2: [],
        dsTNC3: [],
        dsTNC4: [],
        TNC1_ID: 0,
        TNC2_ID: 0,
        TNC3_ID: 0,
        TNC4_ID: 0,
        TNCMOTA: '',
      },
      popupRetVal: null,
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close':this.popupClosed,
        'acceptChangeCTV':this.popupClosed,
        'acceptChangeNGT':this.popupClosed,
        'PopUpCongTacVien_Thoat':this.popupClosed,
      },
      dataPopupKhuVuc:{
        hdtb_id:"",
        donviql_id:''
      },

      listbox:{
        nganhnghe:{ //json: nganhnghe
          list:[], value:""
        },
        loai_kh:{ //json: loai_kh
          list:[], value:""
        },
        quoctich:{  //json: quoctich
          list:[], value:""
        },
        dantoc:{  //json: dantoc
          list:[], value:""
        },
        nhom_hc:{ //json: nhom_hc
          list:[], value:""
        },
        loai_hc:{ //json: loai_hc
          list_full:[],
          list:[], value:""
        },
        loai_giay_to:{  //json: loai_gt
          list:[], value:""
        },
        don_vi_ql:{ //json: donvi_ql
          list:[], value:""
        },
        hinh_thuc_tt:{  //json: hinhthuc_tt
          list:[], value:""
        },
        nganhang:{  //json: nganhang
          list:[], value:""
        },
        hinh_thuc_tc:{  //json: hinhthuc_tc
          list:[], value:""
        },
        thong_tin_xn:{  //json: hinhthuc_xn
          list:[], value:""
        },
        thong_tin_ht:{  //json: hinhthuc_ht
          list:[], value:""
        },


        dichvu_vt:{  //json: DICHVU_VT
          list:[], value:""
        },
        loaihinh_tb:{  //json: LOAIHINH_TB
          list_full:[],list:[], value:""
        },
        kieu_ld:{  //json: KIEU_LD
          list:[], value:""
        },
        tb_donvi_ql:{  //json: DONVI_QL
          list:[], value:""
        },
        doituong:{  //json: DOITUONG
          list:[], value:""
        },
        tram_tc:{
          list:[], value:""
        },
        toc_do:{
          list:[], value:""
        },

        ds_phieuyc:{
          list: [],
          header: [{fieldName:'stt',headerText:'STT'},{fieldName:'nhom_hc',headerText:'Nhóm hiệu chỉnh'},{fieldName:'loai_hc',headerText:'Loại hiệu chỉnh'},
                  {fieldName:'ten_loaihd',headerText:'Loại hợp đồng'},{fieldName:'ma_gd',headerText:'Mã giao dịch'},{fieldName:'ma_tt',headerText:'Mã thanh toán'},
                  {fieldName:'ma_tb',headerText:'Số máy/Account'},{fieldName:'ten_kh',headerText:'Tên khách hàng'},
                  {fieldName:'ngay_ychc',headerText:'Ngày yêu cầu',format: {type: 'date', format: 'dd/MM/yyyy'},type: 'date'},
                  {fieldName:'nguoi_ychc',headerText:'Người yêu cầu'},{fieldName:'yeucau_hc',headerText:'Nội dung yêu cầu'}],
          value: {},
        },

        ds_thanhtoan:{
          list: [],
          header: [{fieldName:'ma_tt',headerText:'Mã thanh toán'},{fieldName:'ten_tt',headerText:'Tên thanh toán'},{fieldName:'diachi_tt',headerText:'Địa chỉ thanh toán'}],
          value: {},
        },


         ds_thuebao:{
          list: [],
          header: [{fieldName:'ma_tb',headerText:'Mã thuê bao'},{fieldName:'ten_dvvt',headerText:'Dịch vụ'},{fieldName:'loaihinh_tb',headerText:'Loại hình'},
            {fieldName:'ten_kieuld',headerText:'Kiểu lắp đặt'},{fieldName:'ten_tb',headerText:'Tên thuê bao'},{fieldName:'diachi_tb',headerText:'Địa chỉ thuê bao'},
            {fieldName:'diachi_ld',headerText:'Địa chỉ lắp đặt'}],
        },
       ds_km:{
          list: [],
          header: [{fieldName:'loai_km',headerText:'Loại KM'},{fieldName:'tenchitiet_km',headerText:'Chi tiết khuyến mại'},{fieldName:'ngay_dkdc',headerText:'Ngày ĐK'},
          {fieldName:'thang_bd',headerText:'Tháng BĐ'},{fieldName:'thang_kt',headerText:'Tháng KT'},{fieldName:'thang_bddc',headerText:'Tháng BĐĐC'},
          {fieldName:'thang_ktdc',headerText:'Tháng KTĐC'},{fieldName:'ten_km',headerText:'Tên KM'},{fieldName:'datcoc_csd',headerText:'Tiền đặt cọc'},
          {fieldName:'tyle_sd',headerText:'Tỷ lệ SD'},{fieldName:'tien_sd',headerText:'Tiền SD'},{fieldName:'cuoc_sd',headerText:'Giảm cước SD'},{fieldName:'tien_td',headerText:'Tiền TD'},
          {fieldName:'tyle_tb',headerText:'Tỷ lệ TB'},{fieldName:'tien_tb',headerText:'Tiền TB'},{fieldName:'cuoc_tb',headerText:'Giảm cước TB'},
          {fieldName:'tyle_tk',headerText:'Tỷ lệ TK'},{fieldName:'tyle_tc',headerText:'Tỷ lệ TC'},{fieldName:'noidung',headerText:'Nội dung'}],
        },
        ds_dvgtgt:{
          list: [],
          header: [{fieldName:'ma_dvgt',headerText:'Mã DVGT'},{fieldName:'ten_dvgt',headerText:'Tên DVGT'},{fieldName:'kieu',headerText:'Kiểu yêu cầu'},{fieldName:'noidung',headerText:'Nội dung'}],
        },
        ds_goi:{
          list: [],
          header: [{fieldName:'ten_goi',headerText:'Tên nhóm/gói'},{fieldName:'chunhom',headerText:'Chủ nhóm'},{fieldName:'ma_tb',headerText:'Số máy/Acc'},{fieldName:'loaihinh_tb',headerText:'Loại hình'}],
        },
        ds_thietbi:{
          list: [],
          header: [{fieldName:'ma_tbi',headerText:'Mã thiết bị'},{fieldName:'loai_tbi',headerText:'Tên thiết bị'},{fieldName:'soluong',headerText:'Số lượng'},{fieldName:'tien',headerText:'Tiền'},{fieldName:'vat',headerText:'VAT'}],
        },

        dsbd:{
          list:[]
        }

       

      }
     }
  },
 
  methods: {
    showPopupSearchContract: function(){
      this.$refs.popupSearchContract.showModal();
    },

    //click buntton ma_gd
    acceptSearchContract:function(item){
      //da mo popup va chon xong ma_gd --> hien thi thong tin hop dong theo hdkh_id
      //this.phieu_yc.ma_gd = item.ma_gd;
      this.hdkh_id = item.hdkh_id;
      this.HienThi_ThongTin_HopDong();
    },

    //nhan enter
    timkiem:async function(){
      this.loading(true);
      var data={
                "ma_tb":this.phieu_yc.ma_gd
              };
      
      await API.lay_ds_hd_theo_ma_tb(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          if(response.data.data.length>1){
            this.listbox.dsbd.list = response.data.data;
            this.$refs.popupDSBD.showModal();
          }
          else if (response.data.data.length == 1){
            this.hdkh_id = response.data.data[0].hdkh_id
            this.HienThi_ThongTin_HopDong();
          }
        }
        else
          this.$toast.error('Không tìm thấy hợp đồng!');
      });
      this.loading(false);
    },
    get_dsbd:function (obj){
      this.hdkh_id = obj.hdkh_id;
      this.HienThi_ThongTin_HopDong();
    },
    
    nhom_hc_changed:function(){
      this.listbox.loai_hc.list = [];
      for (let i = 0; i < this.listbox.loai_hc.list_full.length; i++) {
          if (this.listbox.loai_hc.list_full[i].nhomhc_id == this.listbox.nhom_hc.value) {
              this.listbox.loai_hc.list.push(this.listbox.loai_hc.list_full[i]);
          }
      }

      this.listbox.loai_hc.list = this.listbox.loai_hc.list.map(x=>{return {id:x['loaihc_id'],text:x['loai_hc']}})
      
      //set default value
      for (let i = 0; i < this.listbox.loai_hc.list_full.length; i++) 
        if (this.listbox.loai_hc.list_full[i].nhomhc_id == this.listbox.nhom_hc.value){
          this.listbox.loai_hc.value = this.listbox.loai_hc.list_full[i].loaihc_id;
          break;
        }

    },

    dichvu_changed:function(){
      //console.log(this.listbox.dichvu_vt.value);
      this.listbox.loaihinh_tb.list = [];

      let dvvt_id = this.listbox.dichvu_vt.value;
      this.listbox.loaihinh_tb.list = this.listbox.loaihinh_tb.list_full.filter(e=>e.dichvuvt_id == dvvt_id).map(x=>{return {id:x['loaitb_id'],text:x['loaihinh_tb']}});
      console.log(this.listbox.loaihinh_tb.list[0].id);
      this.listbox.loaihinh_tb.value = this.listbox.loaihinh_tb.list[0].id;
      this.loaihinh_changed();

    },

    donvi_tc_changed:function(){
      var data = {"loaidv_id":5,"donvi_ql_id":this.listbox.tb_donvi_ql.value};

       API.sp_lay_ds_tram_thicong_theo_donvi(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.tram_tc.list = response.data.data.map(x=>{return {id:x['donvi_id'],text:x['ten_dv']}});
        }
      });

    },

   loaihinh_changed: async function(){
     this.loading(true);
      var data = {"ds_loaitb_id":this.listbox.loaihinh_tb.value,"dichvuvt_id":this.listbox.dichvu_vt.value};
      //console.log(data)

      await API.sp_lay_tocdo_theo_loaitb(this.axios,data).then(response=>{
        //console.log(response);
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.toc_do.list = response.data.data.map(x=>{return {id:x['tocdo_id'],text:x['thuonghieu']}});
              this.listbox.toc_do.value = response.data.data[0].tocdo_id;
        }
        else
          this.listbox.toc_do.list = [];
      });
      this.loading(false);

    },

    Lay_DS_Phieu_HieuChinh:function(){
      this.loading(true);
      var data = {
                    "nguoidung_id":this.nguoidung_id,
                    "tag_form":this.tag_form
                };
      
      API.lay_ds_tiepnhan_yc_hieuchinh(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.ds_phieuyc.list = response.data.data;
             
        }
         this.loading(false);
      });
    },

    grddsPhieuYCChanged:async function(obj){
      this.loading(true);
      //console.log(obj[0]);
      this.listbox.ds_phieuyc.value = obj[0];
      //lay Danh sách mã thanh toán
      
      this.listbox.nhom_hc.value = obj[0].nhomhc_id;
      this.nhom_hc_changed();
      this.listbox.loai_hc.value = obj[0].loaihc_id;
      
     
      if (this.tag_form == 2) // Kế toán
      {
        //var data = { "command": "Select * from css.ls_xn_hieuchinh where tdtt_hdtb_id = " + obj[0].tdtt_hdtb_id +" and loai_xn = 1 " }
        var data =  "p_tdtt_hdtb_id=" + obj[0].tdtt_hdtb_id +"&p_loai_xn= 1 " ;
        await API.sp_lay_ls_xn_hieuchinh(this.axios,data).then(response=>{
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.thong_tin_xn.value = response.data.data[0].KQXN_ID;
            this.noidung_xn = response.data.data[0].NOIDUNG_XN;
          }
          else{
            this.listbox.thong_tin_xn.value = "";
            this.noidung_xn = "";
          }
          });         
      }

      if (this.tag_form == 3) // TTVT
      {
          //var data = { "command": "Select * from css.ls_xn_hieuchinh where tdtt_hdtb_id = " + obj[0].tdtt_hdtb_id + " and loai_xn = 2" };
          var data =  "p_tdtt_hdtb_id=" + obj[0].tdtt_hdtb_id +"&p_loai_xn= 2 " ;
          await API.sp_lay_ls_xn_hieuchinh(this.axios,data).then(response=>{
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.thong_tin_xn.value = response.data.data[0].KQXN_ID;
              this.noidung_xn = response.data.data[0].NOIDUNG_XN;
            }
            else{
              this.listbox.thong_tin_xn.value = "";
              this.noidung_xn = "";
            }
          });
      }
      if (this.tag_form == 1 || this.tag_form == 4)
      {
        var ketoan_xn = "";
        var ttvt_xn = "";

        //var data = { "command": "Select * from css.ls_xn_hieuchinh where tdtt_hdtb_id = " + obj[0].tdtt_hdtb_id+ "  and loai_xn = 1" };
        var data =  "p_tdtt_hdtb_id=" + obj[0].tdtt_hdtb_id +"&p_loai_xn= 1 " 
        console.log(data);
        await API.sp_lay_ls_xn_hieuchinh(this.axios,data).then(response=>{
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              console.log(response.data.data[0]);
              ketoan_xn = "--------------- Kế toán xác nhận ------------------";
              if (response.data.data[0].KQXN_ID == "1")
                  ketoan_xn += "\r\n Kết quả : Xác nhận.";
              else
                  ketoan_xn += "\r\n Kết quả : Không xác nhận.";
              ketoan_xn += "\r\n User xác nhận : " + response.data.data[0].NGUOI_CN + "";
              ketoan_xn += "\r\n Nội dung xác nhận : " + response.data.data[0].NOIDUNG_XN + "";
            }
            else{
              this.listbox.thong_tin_xn.value = "";
              ketoan_xn = "";
            }
        });
        

        
        //data = " Select * from css.ls_xn_hieuchinh where tdtt_hdtb_id = " + obj[0].tdtt_hdtb_id +"  and loai_xn = 2";
        data =  "p_tdtt_hdtb_id=" + obj[0].tdtt_hdtb_id +"&p_loai_xn= 2 " 
        console.log(data);
        await API.sp_lay_ls_xn_hieuchinh(this.axios,data).then(response=>{
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              console.log(response.data.data[0]);
              ttvt_xn = "--------------- TTVT xác nhận ------------------";
              if (response.data.data[0].KQXN_ID == "1")
                  ttvt_xn += "\r\n Kết quả : Xác nhận.";
              else
                  ttvt_xn += "\r\n Kết quả : Không xác nhận.";
              ttvt_xn += " \r\n User xác nhận : " + response.data.data[0].NGUOI_CN + "";
              ttvt_xn += " \r\n Nội dung xác nhận : " + response.data.data[0].NOIDUNG_XN + "";
            }
            else{
              this.listbox.thong_tin_xn.value = "";
              ttvt_xn = "";
            }
        });

        this.noidung_xn = ketoan_xn + ttvt_xn;
      }

      this.listbox.thong_tin_ht.value = obj[0].ketqua_th;
      this.noidung_ht = obj[0].noidung_th;
      this.yeucau_hc = obj[0].yeucau_hc;

      //lay Thông tin khách hàng va cac thong tin YC
      var data = {
        "in_hdkh_id":obj[0].hdkh_id
        };

      await API.lay_ds_hopdong_kh_theo_hdkh_id(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          
          //console.log(response);
          this.phieu_yc = response.data.data[0];

          this.listbox.nganhnghe.value = response.data.data[0].nganhnghe_id;
          this.listbox.quoctich.value = response.data.data[0].quoctich_id;
          this.listbox.dantoc.value = response.data.data[0].dantoc_id;
          this.listbox.loai_kh.value = response.data.data[0].loaikh_id;
          this.listbox.loai_giay_to.value = response.data.data[0].loaigt_id;
          this.listbox.nganhang.value = response.data.data[0].nganhang_id;
          this.tieunganh.TNC1_ID = response.data.data[0].tnc1_id;
          this.tieunganh.TNC2_ID = response.data.data[0].tnc2_id;
          this.tieunganh.TNC3_ID = response.data.data[0].tnc3_id;
          this.tieunganh.TNC4_ID = response.data.data[0].tnc4_id;

          if (response.data.data[0].gioitinh == "" || response.data.data[0].gioitinh == ""){
            this.gt_khong = true;
            this.gt_nam = false;
            this.gt_nu = false;
          }
          else if (response.data.data[0].gioitinh == 0){
            this.gt_khong = false;
            this.gt_nam = true;
            this.gt_nu = false;
          }
          else if (response.data.data[0].gioitinh == 1){
            this.gt_khong = false;
            this.gt_nam = false;
            this.gt_nu = true;
          }
           
        
        }
        
      });
      


      data = {
        "hdkh_id":obj[0].hdkh_id,
        "tdtt_hdtb_id":obj[0].tdtt_hdtb_id
        };

      await API.lay_ds_hdtt_theo_hdkh_id_hc(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
         
          this.listbox.ds_thanhtoan.list = response.data.data;
        

          this.thongtin_hd = response.data.data[0];

          this.listbox.don_vi_ql.value = response.data.data[0].donvi_id;
          this.listbox.hinh_thuc_tt.value = response.data.data[0].httt_id;
          this.listbox.nganhang.value = response.data.data[0].nganhang_id;
          this.listbox.hinh_thuc_tc.value = response.data.data[0].httc_id;
        
        }
        else
          this.listbox.ds_thanhtoan.list =[];
      });

      //tab Thuê bao
      await API.lay_ds_hdtb_theo_hdkh_id_hc(this.axios,data).then(response=>{
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          
          
            this.listbox.ds_thuebao.list = response.data.data;

            this.thuebao = response.data.data[0];

            this.listbox.dichvu_vt.value = response.data.data[0].dichvuvt_id;
            this.listbox.kieu_ld.value = response.data.data[0].kieuld_id;
            this.listbox.tb_donvi_ql.value = response.data.data[0].donvi_id;
            this.listbox.doituong.value = response.data.data[0].doituong_id;
          
          }
          else
            this.listbox.ds_thuebao.list =[];
        });
      this.loading(false);
    },


    grddsMaTTChanged:function(obj){
       if (obj.name == "rowSelected"){
        this.thongtin_hd = obj.data;

        this.listbox.don_vi_ql.value = obj.data.donvi_id;
        this.listbox.hinh_thuc_tt.value = obj.data.httt_id;
        this.listbox.nganhang.value = obj.data.nganhang_id;
        this.listbox.hinh_thuc_tc.value = obj.data.httc_id
        this.thanhtoan_checked.push(obj.data);

      }
      else if (obj.name == "rowDeselected"){
        this.thanhtoan_checked.splice(this.thanhtoan_checked.indexOf(obj.data), 1);

      }

       
    },

    grddsTBChanged:function(obj){
  
      if (obj.name == "rowSelected"){
       
        this.thuebao = obj.data;
        this.thuebao_checked.push(obj.data);

        var data = {
            "hdtb_id":obj.data.hdtb_id
            //"hdtb_id":1704147
          };

        API.lay_ds_ctkm_theo_hdtbid(this.axios,data).then(response=>{
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.ds_km.list = response.data.data;
          }
          
        });

        //data = { "hdtb_id":1216267  };
        API.lay_ds_dangky_dvgt_v2(this.axios,data).then(response=>{
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.ds_dvgtgt.list = response.data.data;
          }
          
        });

        //data = { "hdtb_id":2023587 };
        API.lay_ds_goi_dadv_hdtb(this.axios,data).then(response=>{
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.ds_goi.list = response.data.data;
          }
          
        });

      // data = { "hdtb_id":3340622  };
        API.lay_ds_mua_tbi(this.axios,data).then(response=>{
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.ds_thietbi.list = response.data.data;
          }
          
        });
      }
       else if (obj.name == "rowDeselected"){
        this.thuebao_checked.splice(this.thuebao_checked.indexOf(obj.data), 1);

      }
      
      if (this.listbox.ds_thuebao.list.length != 0){
        this.dichvu_changed();
        this.listbox.loaihinh_tb.value = this.thuebao.loaitb_id;
        this.loaihinh_changed();
        this.donvi_tc_changed();
        // data = {
        //               "list_of_cols": "donvi_id",
        //               "order": "",
        //               "schema": "css",
        //               "table_name": "hdtb_dv",
        //               "where": "loaidv_id = 5 and hdtb_id = "+this.thuebao.hdtb_id
        //           }
        data = {
                  "param": this.thuebao.hdtb_id,
                  "type": 4
                }
        API.sp_tt_hdtb_dv(this.axios,data).then(response=>{
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.tram_tc.value = response.data.data[0].donvi_id;
            }
            
          });
        

        if (this.thuebao.hdtb_id != undefined) {     
          API.lay_tien_hopdong(this.axios,this.thuebao.hdtb_id).then(response=>{
            
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.tongtien = 0;
            this.tien = 0;
            this.vat = 0;
                
              response.data.data.forEach((item) => {
                this.tien += Math.abs(item.tien),
                this.vat += Math.abs(item.vat)
              });
                this.tongtien = this.tien+this.vat;

            }
            
          });
      }

      }
      





    },


   
    btn_ghilai:async function(obj){
         
      let kt_hdtt_id = 0;
      let kt_hdtb_id = 0;
      let hdtb_id = null;
      let hdtt_id = null;
      if (!this.yeucau_hc){
        this.$toast.error("Bạn chưa nhập Nội dung yêu cầu hiệu chỉnh!");
        return;
      }

      if (this.yeucau_hc.length>2000){
        this.$toast.error("Nội dung yêu cầu không được quá 2000 ký tự!");
        return;
      }
      
      if (!this.thongtin_hd.hdkh_id){
        this.$toast.error("Không có thông tin mã thanh toán!");
        return;
      }

     
      if (this.listbox.nhom_hc.value == 2) {// Hiệu chỉnh thanh toán
        if (this.listbox.ds_thanhtoan.list.length == 0){
          this.$toast.error("Không có thông tin thanh toán!");
          return;
        }

        if (this.thanhtoan_checked.length == 0){
          this.$toast.error("Bạn chưa chọn mã thanh toán cần hiệu chỉnh!");
          return;
        }

         if (this.thanhtoan_checked.length > 1){
          this.$toast.error("Bạn chỉ được chọn 1 mã thanh toán để hiệu chỉnh!");
          return;
        }

        kt_hdtt_id = this.thanhtoan_checked[0].hdtt_id;
        hdtt_id = this.thanhtoan_checked[0].hdtt_id;
      
      }

      if (this.listbox.nhom_hc.value == 3) {// Hiệu chỉnh thuê bao
        if (this.listbox.ds_thuebao.list.length == 0){
          this.$toast.error("Không có thông tin thuê bao!");
          return;
        }

        if (this.thuebao_checked.length == 0){
          this.$toast.error("Bạn chưa chọn mã thuê bao cần hiệu chỉnh!");
          return;
        }

         if (this.thuebao_checked.length > 1){
          this.$toast.error("Bạn chỉ được chọn 1 mã thuê bao để hiệu chỉnh!");
          return;
        }

        kt_hdtb_id = this.thuebao_checked[0].hdtb_id;
        hdtb_id = this.thuebao_checked[0].hdtb_id;
      
      }

      if (this.listbox.nhom_hc.value == "" || this.listbox.nhom_hc.value == null){
        this.$toast.error("Bạn chưa chọn nhóm hiệu chỉnh!");
        return;
      }
      
      if (this.listbox.loai_hc.value == "" || this.listbox.loai_hc.value == null){
        this.$toast.error("Bạn chưa chọn loại hiệu chỉnh!");
        return;
      }

      var ds = "[{\"HDKH_ID\": "+this.thongtin_hd.hdkh_id+",\"LOAIHC_ID\": "+this.listbox.loai_hc.value+", \"NHANVIEN_ID\": "+this.$root.token.getNhanVienID()+",\"KT_HDTT_ID\": "+kt_hdtt_id+",\"KT_HDTB_ID\": "+kt_hdtb_id+",\"TEN_KH\": \""+this.phieu_yc.ten_kh+"\",\"DIACHI_KH\": \""+this.phieu_yc.diachi_kh+"\",\"NHOMHC_ID\": "+ this.listbox.nhom_hc.value+", \"YEUCAU_HC\": \""+this.yeucau_hc+"\",\"NGUOI_CN\": \""+this.$root.token.getUserName()+"\",\"MAY_CN\": \"ADMIN\",\"IP_CN\": \"123\",\"HDTB_ID\": "+hdtb_id+", \"HDTT_ID\": "+hdtt_id+"}]";
      var data = {ds};
      
      await API.fn_capnhat_hieuchinhhopdong(this.axios,data).then(response=>{
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
            this.$toast.success("Thêm mới yêu cầu hiệu chỉnh thành công!");
            // this.$toast.success(response.data.data);
          else
            this.$toast.error(response.data.data);
          
          
        });
      this.Lay_DS_Phieu_HieuChinh();

    },

    btn_nhapmoi:function(obj){
      this.listbox.dichvu_vt.value = "4";
      this.dichvu_changed();
      //console.log(this.listbox.loaihinh_tb.list);
      this.listbox.loaihinh_tb.value = "58";
      this.loaihinh_changed();

      this.thuebao_checked = [];
      this.thanhtoan_checked = [];
      this.listbox.ds_phieuyc.value = "";
      this.listbox.ds_phieuyc.list = [];
      this.listbox.ds_thanhtoan.list = [];
      this.listbox.ds_thuebao.list = [];
      this.listbox.ds_km.list = [];
      this.listbox.ds_dvgtgt.list = [];
      this.listbox.ds_goi.list = [];
      this.listbox.ds_thietbi.list = [];
      
      this.listbox.nganhnghe.value = "10";
      this.listbox.loai_kh.value = "60";
      this.listbox.quoctich.value = "1";
      this.listbox.dantoc.value = "1";
      this.listbox.nhom_hc.value = "";
      this.listbox.loai_hc.value = "";
      this.listbox.loai_giay_to.value = "0";
      this.listbox.don_vi_ql.value = this.listbox.don_vi_ql.list[0].id;
      this.listbox.hinh_thuc_tt.value = "2";
      this.listbox.nganhang.value = "1";
      this.listbox.hinh_thuc_tc.value = "1";
      this.listbox.thong_tin_xn.value = "";
      this.listbox.thong_tin_ht.value = "";
      
      this.listbox.kieu_ld.value = "1";
      this.listbox.tb_donvi_ql.value = "1";
      this.listbox.doituong.value = "1";
      this.listbox.tram_tc.value = "";
    
      //this.listbox.toc_do.value = "";

      this.gt_khong = false;
      this.gt_nam = false;
      this.gt_nu = false;


      this.tongtien = 0;
      this.tien = 0;
      this.vat = 0;
      this.$refs.txt_magd.focus();

      this.phieu_yc = {};
      this.yeucau_hc = "";
      this.noidung_xn = "";
      this.noidung_ht = "";
      this.thongtin_hd = "";
      this.thuebao = {};
      this.hdkh_id = 0;


    },

    btn_xoa:async function(){
      if (this.listbox.ds_phieuyc.list.length == 0){
        this.$toast.error("Danh sách trống. Bạn không thể gửi YC !");
        return;
      }

      this.$confirm('Bạn thật sự muốn xóa Phiếu yêu cầu hiệu chỉnh không ?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            var data = {
                  "tdtt_hdtb_id": this.listbox.ds_phieuyc.value.tdtt_hdtb_id
              }

            await API.sp_delete_tdtt_hd_thuebao(this.axios,data).then(response=>{
              if(response && response.data  && response.data.error && response.data.error_code === 'BSS-00000000') {
                this.$toast.success("Xóa dữ liệu thành công!");
              }
              else
                this.$toast.error(response.data.message);
              
              
            });
        
          })

      
    },

    btn_capnhat:function(obj){
      if (this.listbox.ds_phieuyc.list.length == 0){
        this.$toast.error("Danh sách trống. Bạn không thể gửi YC !");
        return;
      }

      if (!this.listbox.thong_tin_xn.value){
        this.$toast.error("Bạn chưa chọn Thông tin xác nhận !");
        return;
      }
      
       if (!this.noidung_xn){
        this.$toast.error("Bạn chưa nhập Nội dung xác nhận !");
        return;
      }

      var json_ls_xn_hieuchinh = "[{\"TDTT_HDTB_ID\": "+this.listbox.ds_phieuyc.value.tdtt_hdtb_id+",\"LOAI_XN\": 0, \"KQXN_ID\": "+this.listbox.thong_tin_xn.value+",\"NOIDUNG_XN\": \""+this.noidung_xn+"\",\"MAY_CN\": \"ADMIN\",\"NGAY_CN\": \"sysdate\",\"NGUOI_CN\": \""+this.$root.token.getUserName()+"\"}]"
      var data = {json_ls_xn_hieuchinh};
      console.log(data);

      API.sp_insert_ls_xn_hieuchinh(this.axios,data).then(response=>{
          if(response && response.data  && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.$toast.success("Xác nhận hiệu chỉnh thành công!");
          }
          else
            this.$toast.error(response.data.data);
          
          
        });

    },

    btn_hieuchinh:function(obj){
      //mo popup
      this.$refs.popuphieuchinh_hd_ct.showModal();
    },

    btn_hoanthanh: async function(obj){
      if (this.listbox.ds_phieuyc.list == [] || this.listbox.ds_phieuyc.list == null || this.listbox.ds_phieuyc.list.length == 0 ){
        this.$toast.error("Danh sách trống. Bạn không thể gửi YC !")
        return;
      }
      if (this.listbox.thong_tin_ht.value == null || this.listbox.thong_tin_ht.value == 0 ){
        this.$toast.error("Bạn chưa chọn Thông tin hoàn thiện !")
        return;
      }
      if (this.noidung_ht == null || this.noidung_ht == undefined ){
        this.$toast.error("Bạn chưa nhập Nội dung hoàn thiện !");
        return;
      }

       var data = {
                      "hdtb_id":this.listbox.ds_phieuyc.value.tdtt_hdtb_id,
                      "loai_xn":this.listbox.thong_tin_ht.value
                  };

      await API.kiemtra_hoanthien_hieuchinh(this.axios,data).then(response=>{
        if(response && response.data && response.data.data == "OK" && response.data.error && response.data.error_code === 'BSS-00000000') {
         
        }
        else
          return;
      });

      this.$confirm('Bạn thật sự muốn xóa Phiếu yêu cầu hiệu chỉnh không ?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            var data = {
                            "tdtt_hdtb_id":this.listbox.ds_phieuyc.value.tdtt_hdtb_id,
                            "thongtin_ht":this.listbox.thong_tin_ht.value,
                            "noidung_ht":this.noidung_ht,
                            "may_cn":"admin",
                            "nguoi_th":this.$root.token.getUserName()
                        };

            await API.sp_update_tdtt_hd_thuebao(this.axios,data).then(response=>{
              if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                this.$toast.success("Khóa phiếu hiệu chỉnh thành công!");
              }
              else{
                this.$toast.error(response.data.data);
              }

            });  

          
            this.Lay_DS_Phieu_HieuChinh();


        })




    },


    HienThi_ThongTin_HopDong:async function(){
      this.loading(true);
      //lay Thông tin khách hàng va cac thong tin YC
      var data = {
        "in_hdkh_id":this.hdkh_id
        };

      await API.lay_ds_hopdong_kh_theo_hdkh_id(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          
          //console.log(response);
          this.phieu_yc = response.data.data[0];

          this.listbox.nganhnghe.value = response.data.data[0].nganhnghe_id;
          this.listbox.quoctich.value = response.data.data[0].quoctich_id;
          this.listbox.dantoc.value = response.data.data[0].dantoc_id;
          this.listbox.loai_kh.value = response.data.data[0].loaikh_id;
          this.listbox.loai_giay_to.value = response.data.data[0].loaigt_id;
          this.listbox.nganhang.value = response.data.data[0].nganhang_id;
          this.tieunganh.TNC1_ID = response.data.data[0].tnc1_id;
          this.tieunganh.TNC2_ID = response.data.data[0].tnc2_id;
          this.tieunganh.TNC3_ID = response.data.data[0].tnc3_id;
          this.tieunganh.TNC4_ID = response.data.data[0].tnc4_id;

          if (response.data.data[0].gioitinh == "" || response.data.data[0].gioitinh == ""){
            this.gt_khong = true;
            this.gt_nam = false;
            this.gt_nu = false;
          }
          else if (response.data.data[0].gioitinh == 0){
            this.gt_khong = false;
            this.gt_nam = true;
            this.gt_nu = false;
          }
          else if (response.data.data[0].gioitinh == 1){
            this.gt_khong = false;
            this.gt_nam = false;
            this.gt_nu = true;
          }
           
        
        }
        
      });
      


      data = {
        "hdkh_id":this.hdkh_id,
        "tdtt_hdtb_id":0
        };

      await API.lay_ds_hdtt_theo_hdkh_id_hc(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
         
          this.listbox.ds_thanhtoan.list = response.data.data;
        

          this.thongtin_hd = response.data.data[0];

          this.listbox.don_vi_ql.value = response.data.data[0].donvi_id;
          this.listbox.hinh_thuc_tt.value = response.data.data[0].httt_id;
          this.listbox.nganhang.value = response.data.data[0].nganhang_id;
          this.listbox.hinh_thuc_tc.value = response.data.data[0].httc_id;
        
        }
        else
          this.listbox.ds_thanhtoan.list =[];
      });

      //tab Thuê bao
      await API.lay_ds_hdtb_theo_hdkh_id_hc(this.axios,data).then(response=>{
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          
          
            this.listbox.ds_thuebao.list = response.data.data;

            this.thuebao = response.data.data[0];

            this.listbox.dichvu_vt.value = response.data.data[0].dichvuvt_id;
            this.listbox.kieu_ld.value = response.data.data[0].kieuld_id;
            this.listbox.tb_donvi_ql.value = response.data.data[0].donvi_id;
            this.listbox.doituong.value = response.data.data[0].doituong_id;
          
          }
          else
            this.listbox.ds_thuebao.list =[];
        });

      this.loading(false);

    },

    popup_ct_tien_km() {
      //:loaihd_id="phieu_yc.loaihd_id" :hdtb_id="thuebao.hdtb_id"
      if (this.thuebao.hdtb_id == null ){
        console.log(this.thuebao.hdtb_id);
        this.$toast.error("Không có thông tin HĐTB!");
        return;
      }
      if (this.phieu_yc.loaihd_id == null ){
        console.log(this.thuebao.loaihd_id);
        this.$toast.error("Không có thông tin Loại HĐ!");
        return;
      }

      this.$refs.popupcttienkm.showModal();

    },

    popupTieuNganh() {
      var data={"vnganhnghe_id": this.listbox.nganhnghe.value,"tieunganh":this.tieunganh};

      
      this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popupTieuNganh');
      this.popupComponentName = "popupTieuNganh";
      this.popupComponentData = data;
      //this.Popup('popupComponents');
       console.log(data);
      this.$bvModal.show('popupComponents');

    },


    popupChonKhuVuc:async function() {
      // if (this.thuebao.ten_kv != "" && this.thuebao.ten_kv != null && this.thuebao.ten_kv != undefined){
      //   this.loading(true);

      
      //   var p_khuvuc_id="";
      //   var data={
      //               "param": this.thuebao.ten_kv ,
      //               "type": 1
      //             };

      //   await API.fn_tt_khuvuc(this.axios,data).then(response=>{
      //     if(response && response.data.error_code === 'BSS-00000000') 
      //       p_khuvuc_id = response.data.data;
      //     else
      //       p_khuvuc_id = "";
      //   });
      //   this.loading(false);
      
      //   // data= {diachi:null, 
      //   //         khuvuc_id: p_khuvuc_id, 
      //   //         hdtb_id: this.listbox.ds_phieuyc.value.tdtt_hdtb_id,
      //   //         thuebao_id: this.thuebao.thuebao_id, 
      //   //         donvi_id: this.listbox.tb_donvi_ql.value };

      //   data= { 
      //             "quan_id": 0, 
      //             "phuong_id": 0, 
      //             "pho_id": 0, 
      //             "ap_id": 0, 
      //             "khu_id": 0, 
      //             "dacdiem_id": 0, 
      //             "khuvuc_id": 0, 
      //             "donviql_id": 0, 
      //             "hdtb_id": this.listbox.ds_phieuyc.value.tdtt_hdtb_id 
      //         }
      //           console.log(data);

      //   this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao');
      //   this.popupComponentName = "popupChonKhuVuc";
      //   this.popupComponentData = data;
      //   console.log(data);
      //   this.$bvModal.show('popupComponents');
      // }
      this.dataPopupKhuVuc.hdtb_id = this.listbox.ds_phieuyc.value.tdtt_hdtb_id;
      this.dataPopupKhuVuc.donviql_id = 0;
      this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal();
    },

    async popupClosed(val) {
      if(!val) {
        this.popupRetVal = {};
        this.ClosePopup('popupComponents');
        return;
      }
      this.loading(true);
      this.ClosePopup('popupComponents');
      this.loading(false);
    },

    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },

    filterMedia: function () {
        if (!this.nganhang_enable)
          this.nganhang_enable = true;
        else
           this.nganhang_enable = false;
          
    },

       handleBlur(e) {

            console.log( e.target.value);

            }


  }
};

</script>
