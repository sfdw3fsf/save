<template src="./CompleteProfileLiquidation.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
import gridview from '@/components/Shared/gridview'

//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split';
import '@/assets/vendor/bootstrap-table/bootstrap-table.min';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min';


//
export default {
  components: {breadcrumb,VueFlatPickr,gridview,API},
  name: 'CompleteProfileLiquidation',
  mounted () {
    this.nhanvien_id = this.$root.token.getNhanVienID();
    
    //de test
    //this.nhanvien_id = 452;

    var data = {
      "dichvu_vt_id": this.dsdichvuvt_id
    };

    API.sp_ht_dichvuvt_combobox(this.axios,data).then(response=>{
      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.dichvu.list = response.data.data.map(x=>{return {id:x['dichvuvt_id'],text:x['ten_dvvt']}});;
      }
    });

    data = {
       "donvi_id":  this.$root.token.getDonViID()
    };

    
    API.sp_lay_ds_nhanvien_theo_donvi_id(this.axios,data).then(response=>{
      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.nhanvien_gv.list = response.data.data.map(x=>{return {id:x['nhanvien_id'],text:x['ten_nv']}});;
      }
    });
    

  },
  data() {
     return {

        dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true,
      },
      ngay_tao:"",
      
      //GIA TRI MAC DINH TRUYEN VAO FORM KHI LOAD
      dsloaihd_id:"4",
      dichvuvt_id:"10",
      dsdichvuvt_id:"1,4,8,9,10,11,12,7",
      lay_phieu_con:true,
      phieu_moi:"0",
      //HET CAC GIA TRI MAC DINH

      nhanvien_id:"",

      tthd_id:"1",
      luong_id:"",
      huonggiao_id:"",
      thongtinhoancong:"",
      hdtb_id:"",
      hdkh_id:"",

      listbox:{
        dichvu:{
          list:[],
          value:""
        },
        quytrinh:{
          list:[],
          value:""
        },
        nhom_kq:{
          list:[],
          value:""
        },
        ketqua:{
          list:[],
          value:""
        },
        nhanvien_gv:{
          list:[],
          value:""
        },
        loai_dvtk:{
          list:[{ "id":"6", "text" :"Host" },{ "id":"25", "text" :"Trạm vệ tinh" }],
          value:"",
          isEnable:false
        },
        dvi_timkiem:{
          list:[],
          value:"",
          isEnable:false
        },
        dsphieu: {
          list: [],
          header: [{fieldName:'ma_hd',headerText:'Mã HĐ'},{fieldName:'ngay_yc',headerText:'Ngày YC'},{fieldName:'ngaygiao',headerText:'Ngày giao'},{fieldName:'ma_tb',headerText:'Số máy/Acc'},
          {fieldName:'ten_tb',headerText:'Tên thuê bao'},{fieldName:'ten_kv',headerText:'Khu vực'},{fieldName:'ngay_hh',headerText:'Ngày hết hạn'},{fieldName:'so_dt',headerText:'Điện thoại LH'},
          {fieldName:'diemtinnhiem',headerText:'Điểm tín nhiệm'},{fieldName:'dv_nhan',headerText:'ĐV nhận'},{fieldName:'loaihinh_tb',headerText:'Loại hình'},{fieldName:'',headerText:'Trạng thái in'},
          {fieldName:'lydohuy',headerText:'Lý do huỷ'},{fieldName:'ten_status',headerText:'Kích hoạt tổng đài'},{fieldName:'dv_giao',headerText:'Đơn vị giao'},
          {fieldName:'ghichu',headerText:'Ghi chú'},{fieldName:'diachi_ld',headerText:'Địa chỉ LĐ'}],
          value: {},
        },
         dsnhanvien: {
          list: [],
          header: [{fieldName:'ten_nv',headerText:'Tên nhân viên'},{fieldName:'so_dt',headerText:'Điện thoại'},{fieldName:'nhiemvu',headerText:'Nhiệm vụ'},
          {name:'ghichu',headerText:'Ghi chú'}],
          value: {},
        },
         dsthietbi: {
          list: [],
          header: [{fieldName:'ngay_sd',headerText:'Ngày SD'},{fieldName:'loai_tbi',headerText:'Loại thiết bị'},{fieldName:'donvi_tbi',headerText:'Đơn vị'},
          {fieldName:'kieu_tbi',headerText:'Kiểu'},{fieldName:'sl',headerText:'SL'},{fieldName:'tentrangbi',headerText:'Trang bị'},{fieldName:'seri',headerText:'Seri'}],
          value: {},  
        },
         dslydohuy: {
          list: [],
         header: [{fieldName:'lydohuy',headerText:'Lý do huỷ'},{fieldName:'kieu_huy',headerText:'Kiểu'},{fieldName:'nguoi_cn',headerText:'Người CN'}],
          value: {},
        },
      }      

     }
  },
 
  methods: {
    dichvu_change:function(){
      var data={
            "dsloaihd_id": this.dsloaihd_id,
            "dichvuvt_id": this.listbox.dichvu.value,
            "tthd_id": this.tthd_id,
            "kieu": 0
          };

      API.sp_lay_ds_quytrinh_v2(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.quytrinh.list = response.data.data.map(x=>{return {id:x['quytrinh_id'],text:x['quytrinh']}});
        }
      });

      
    },

    quytrinh_change:function(){
      this.loading(true);
      var data={
                  "quytrinh_id": this.listbox.quytrinh.value,
                  "tthd_id": this.tthd_id
                };

      API.sp_lay_huonggiao_theo_quytrinh(this.axios,data).then(response=>{
        console.log(response.data.data);
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.luong_id = response.data.data[0].luong_id;
          this.huonggiao_id = response.data.data[0].huonggiao_id;
          this.listbox.loai_dvtk.isEnable = false;
          this.listbox.dvi_timkiem.isEnable = false;


          //du lieu test
          //this.huonggiao_id = 477;
           var req= {
              "vhuonggiao_id": this.huonggiao_id
            }
            console.log(req);
                
            API.lay_nhom_kqxl_theo_huonggiao(this.axios,req).then(response=>{
              if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
                  this.listbox.nhom_kq.list = response.data.data.map(x=>{return {id:x['nhomkq_id'],text:x['nhom_kq']}});        
            });
          
        }
      });

      // data =  {
      //         "luong_id": this.luong_id
      //       }
      // API.sp_lay_ds_theotac(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
      //     //ham hien thi giao dien: private void HienThiGiaoDien(int luong_id)
      //   }
      // });

      this.loading(false);
    },

    loaidvtk_change:function(){
      this.loading(true);
      var data={
            "loaidv_id": this.listbox.loai_dvtk.value 
           
          };
          console.log(data);

      API.sp_lay_ds_donvi_theo_loaidv(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.dvi_timkiem.list = response.data.data.map(x=>{return {id:x['donvi_id'],text:x['ten_dv']}});
        }
        else
          this.listbox.dvi_timkiem.list = [];

          this.loading(false);

      });

      
    },

    dvtk_check:function(){
      this.listbox.loai_dvtk.isEnable = !this.listbox.loai_dvtk.isEnable;
      this.listbox.dvi_timkiem.isEnable = !this.listbox.dvi_timkiem.isEnable;
    },

    nhomkq_change:function(){
     var data= {
              "vnhomkq_id": this.listbox.nhom_kq.value
            };
                console.log(data);
      API.lay_ds_nguyennhan_nocuoc(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
            this.listbox.ketqua.list = response.data.data.map(x=>{return {id:x['kqxl_id'],text:x['ketqua_xl']}});        
      });

    },

    HienThiDanhSach:function(){
      this.loading(true);
      var data = {
                  "dichvuvt_id": this.listbox.dichvu.value,
                  "bancheo": 0,
                  "lay_phieu_con": this.lay_phieu_con?1:0,   
                  "kieu": this.phieu_moi,
                  "tthd_id": this.tthd_id,
                  "huonggiao_id": this.huonggiao_id,
                  "nhanvien_id": this.nhanvien_id,
                  "loai_id": this.phieu_moi
              };
              console.log(data);
          // data = {
          //           "dichvuvt_id": 4,
          //           "bancheo": 0,
          //           "lay_phieu_con": 1,   
          //           "kieu": 0,
          //           "tthd_id": 4,
          //           "huonggiao_id": 410,
          //           "nhanvien_id": 452,
          //           "loai_id": 0
          //       };

      API.sp_lay_ds_thuebao_thanhly(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.listbox.dsphieu.list = response.data.data;
          this.thongtinhoancong = response.data.data[0];
        }
        else
          this.listbox.dsphieu.list = [];
        this.loading(false);

      });
      this.load_grd();
              
    },

    grddsPhieuChanged:function(obj){
       console.log( obj[0]);

      this.thongtinhoancong = obj[0];
      console.log(this.thongtinhoancong.trangthai_hd);
      this.load_grd();

    },

    load_grd:function(){
      var data = {
                    "phieu_id": this.thongtinhoancong.phieu_id,
                    "nhanvien_id": this.nhanvien_id,
                    "kieu_id": 2
                  };
      console.log(data);
      API.lay_ds_nhanvien_theo_phieu_id(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
          this.listbox.dsnhanvien.list = response.data.data;
        else
          this.listbox.dsnhanvien.list = [];
      });
      
      data =  {
                "thuebao_id": this.thongtinhoancong.thuebao_id
              }
      API.sp_lay_thietbi_sudung(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
          this.listbox.dsthietbi.list = response.data.data;
        else
          this.listbox.dsthietbi.list = [];
      });

      data =  {
                "hdtb_id": this.thongtinhoancong.hdtb_id
              }
      API.sp_lay_ds_lydohuy_hdtb(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
          this.listbox.dslydohuy.list = response.data.data;
        else
          this.listbox.dslydohuy.list = [];
      });
    },


    CapNhat:function(){
      this.loading(true);
      var data =  {
                      "phieu_id": this.thongtinhoancong.phieu_id,
                      "hdtb_id":  this.thongtinhoancong.hdtb_id,
                      "nd_thuchien": this.thongtinhoancong.nd_thuchien,
                      "nhanvien_th": this.nhanvien_id,
                      "nhanvien_gv": this.listbox.nhanvien_gv.value 
                  }
      console.log(data);

       API.sp_lay_ds_lydohuy_hdtb(this.axios,data).then(response=>{
         console.log(response.data.data);
        this.loading(false);
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          alert('ok');
          this.HienThiDanhSach();
        }
        else
           alert('not ok');
      });
     
    },

    test:function(){
      console.log(this.luong_id);
      console.log(this.huonggiao_id);
    }
  }
};

</script>
