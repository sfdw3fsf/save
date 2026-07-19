<template src="./PriceList.html">
  
</template>
<style>
.collapsed.fa-angle-up,
.not-collapsed.fa-angle-down {
  display: none !important;
}

</style>

<script>
import moment from 'moment'
import DatabaseConstants from '@/const/enums/DBConstants'
import KHOANMUC_TT from '@/const/enums/KHOANMUC_TT'
import DichVuVienThong from '@/const/enums/DichVuVienThong'
import BangGiaMegawan from './BangGiaMegawan'
import BGDoiTDMgWan from './BGDoiTDMgWan.vue'
import breadcrumb from '@/components/breadcrumb'
import gridview from '@/components/Shared/gridview'

import API from './API'

export default {
  components: { breadcrumb, gridview,DatabaseConstants,BangGiaMegawan,BGDoiTDMgWan,DichVuVienThong,KHOANMUC_TT },
  name: 'PriceList',

  data(){
    return {
      header: {
        title: 'Phân tập khách hàng b2a',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true,
      },
      tsbtnNhapMoi:{Enabled : false},
      tsbtnGhiLai:{Enabled : false},
      tsbtnXoa:{Enabled : false},
      tsbtnHuyBo:{Enabled : false},
      tsbtnChiTietMGW:{Enabled : false},

      banggia:{
        banggia_id:"",
        ten_bg:"",
        tien:"",
        tien_cd:"",
        vat:"",
        vat_cd:"",
        tyle_vat:"",
        ngay_bd:"",
        ngay_kt:"",
        khuvuc_id:""
      },
      banggia_info:{
         tyle_vat:0,
        tien_cd:0,
        tien:0,
        vat:0,
        vat_cd:0
      },

      listbox:{
        ds_dvvt:{
          list:[],
          value: "",
        },
         ds_loaihinh:{
          list:[],
          value: "",
        },
         ds_khoanmuc:{
          list:[],
          value: "",
        },
         ds_khuvuc:{
          list:[],
          value: "",
        },




        ds_banggia:{
          list:[],
          headers:[{fieldName:'banggia_id',headerText:'Bảng giá ID',allowfiltering: true},
          {fieldName:'ten_bg',headerText:'Tên bảng giá',allowfiltering: true},
          {fieldName:'tien',headerText:'Tiền HĐ',allowfiltering: true},
          {fieldName:'tien_cd',headerText:'Tiền CĐ',allowfiltering: true},
          {fieldName:'tyle_vat',headerText:'Tỉ lệ VAT',allowfiltering: true}]
        },


        ds_loaihinh_dagan:{
          list:[],
          headers:[{fieldName:'thuonghieu',headerText:'Đã gán (Loại hình, tốc độ, mức cước)',allowfiltering: true}],
        },
        ds_kieu_ld_dagan:{
          list:[],
          headers:[{fieldName:'ten_kieuld',headerText:'Đã gán (Kiểu lắp đặt)',allowfiltering: true}]
        },
        ds_doituongtb_dagan:{
          list:[],
          headers:[{fieldName:'ten_dt',headerText:'Đã gán (Đối tượng TB)',allowfiltering: true}]
        },
        ds_trangbi_dagan:{
          list:[],
          headers:[{fieldName:'tentrangbi',headerText:'Đã gán (Trang bị)',allowfiltering: true}]
        },
        ds_loaitb_dagan:{
          list:[],
          headers:[{fieldName:'ten_tbi',headerText:'Đã gán (Loại thiết bị)',allowfiltering: true}]
        },
        ////////////DANH SACH CHUA GAN
        ds_loaihinh_chuagan:{
          list:[],
          headers:[{fieldName:'thuonghieu',headerText:'Chưa gán (Loại hình, tốc độ, mức cước)',allowfiltering: true}],
        },
        ds_kieu_ld_chuagan:{
          list:[],
          headers:[{fieldName:'ten_kieuld',headerText:'Chưa gán (Kiểu lắp đặt)',allowfiltering: true}],
          value:""
        },
        ds_doituongtb_chuagan:{
          list:[],
          headers:[{fieldName:'ten_dt',headerText:'Chưa gán (Đối tượng TB)',allowfiltering: true}],
          value:""
        },
        ds_trangbi_chuagan:{
          list:[],
          headers:[{fieldName:'tentrangbi',headerText:'Chưa gán (Trang bị)',allowfiltering: true}],
          value:""
        },
        ds_loaitb_chuagan:{
          list:[],
          headers:[{fieldName:'ten_tbi',headerText:'Chưa gán (Loại thiết bị)',allowfiltering: true}],
          value:""
        },


      },
    }

  },

  mounted: async function(){
    this.loading(true);
    var data = {};
       //load ds dich vu
    await API.CSS_DICHVU_VT(this.axios).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.listbox.ds_dvvt.list = response.data.data.map(x=>{return {id:x['DICHVUVT_ID'],text:x['TEN_DVVT']}});
          this.listbox.ds_dvvt.value = 1;
        }
        else{
          this.listbox.ds_dvvt.list = [];
          this.listbox.ds_dvvt.value = "";
        }
    });

    //load khu vuc
    // var data={
    //         "command":"SELECT -1 khuvuc_id, 'Tất cả' ten_kv FROM dual Union All SELECT khuvuc_id, ten_kv FROM "+DatabaseConstants.DB2+".khuvuc where phanvung_id = " + this.$root.token.getPhanVungID()
    //     };
    
    var data="p_phanvung_id="+ this.$root.token.getPhanVungID();

    await API.sp_lay_khuvuc(this.axios,data).then(response=>{
      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
        this.listbox.ds_khuvuc.list = response.data.data.map(x=>{return {id:x['khuvuc_id'],text:x['ten_kv']}});
      else
        this.listbox.ds_khuvuc.list = [];
    });

    //load khoan muc
    // data={
    //         "command":"select khoanmuctt_id,ten_kmtt from "+DatabaseConstants.DB2+".khoanmuc_tt where phanvung_id = " + this.$root.token.getPhanVungID()+" order by khoanmuctt_id"
    //     };

    await API.sp_lay_khoanmuctt(this.axios,data).then(response=>{
      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        this.listbox.ds_khoanmuc.list = response.data.data.map(x=>{return {id:x['khoanmuctt_id'],text:x['ten_kmtt']}});
        this.listbox.ds_khoanmuc.value = response.data.data[0].khoanmuctt_id;
      }
      else{
        this.listbox.ds_khoanmuc.list = [];
        this.listbox.ds_khoanmuc.value = "";
      }
    });

    //load loai hinh
    // data={
    //           "command":"select loaitb_id,loaihinh_tb from "+DatabaseConstants.DB2+".loaihinh_tb where dichvuvt_id = "+this.listbox.ds_dvvt.value+" order by loaitb_id, loaihinh_tb"
    //       };
    data = "p_dichvuvt_id="+this.listbox.ds_dvvt.value;
    await API.sp_lay_loaitb(this.axios,data).then(response=>{
      console.log(response)
      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        this.listbox.ds_loaihinh.list = response.data.data.map(x=>{return {id:x['loaitb_id'],text:x['loaihinh_tb']}});
        this.listbox.ds_loaihinh.value = response.data.data[0].loaitb_id;

      }
      else{
        this.listbox.ds_loaihinh.list = [];
        this.listbox.ds_loaihinh.value = "";
      }
    });
    
    //load danh sach
    await this.SelectList();
    this.SetButton(-1);
    this.loading(false);

  },

  methods:{
    SetButton: function(kieu){
        this.tsbtnNhapMoi.Enabled = false;
        this.tsbtnGhiLai.Enabled = false;
        this.tsbtnXoa.Enabled = false;
        this.tsbtnHuyBo.Enabled = false;
        //this.tsbtnChiTietMGW.Enabled = false;

        if (kieu == -1)//Bat dau
        {
            this.tsbtnGhiLai.Enabled = true;
            this.tsbtnHuyBo.Enabled = true;
        }

        if (kieu == 0)//Bat dau
        {
          this.tsbtnNhapMoi.Enabled = true;
          //this.banggia_info = {};
          this.banggia_info.banggia_id = 0;
          this.banggia_info.ten_bg = "";
          this.banggia_info.tyle_vat = 0;
          this.banggia_info.tien = 0;
          this.banggia_info.vat = 0;
          this.banggia_info.tien_cd = 0;
          this.banggia_info.vat_cd = 0;
          this.banggia_info.ngay_bd = "";
          this.banggia_info.ngay_kt = "";
        }
            

        if (kieu == 1)//Them moi
        {
            this.tsbtnGhiLai.Enabled = true;
            this.tsbtnHuyBo.Enabled = true;
            this.banggia_info.banggia_id = 0;
            this.banggia_info.ten_bg = "";
          this.banggia_info.tyle_vat = 0;
          this.banggia_info.tien = 0;
          this.banggia_info.vat = 0;
          this.banggia_info.tien_cd = 0;
          this.banggia_info.vat_cd = 0;
          this.banggia_info.ngay_bd = "";
          this.banggia_info.ngay_kt = "";
        }

        if (kieu == 2)//Huy
        {
            this.tsbtnNhapMoi.Enabled = true;
            this.tsbtnXoa.Enabled = true;
            this.banggia_info.banggia_id = 0;
            this.banggia_info.ten_bg = "";
          this.banggia_info.tyle_vat = 0;
          this.banggia_info.tien = 0;
          this.banggia_info.vat = 0;
          this.banggia_info.tien_cd = 0;
          this.banggia_info.vat_cd = 0;
          this.banggia_info.ngay_bd = "";
          this.banggia_info.ngay_kt = "";
        }

        if (kieu == 3)//Edit
        {
            this.tsbtnNhapMoi.Enabled = true;
            this.tsbtnXoa.Enabled = true;
            this.tsbtnGhiLai.Enabled = true;
            this.tsbtnHuyBo.Enabled = true;
        }
    },

    cboDichVuVT_Change:async function(){
      //load cboLoaiHinhTB 
      // var data={
      //         "command":"select loaitb_id,loaihinh_tb from css.loaihinh_tb where dichvuvt_id = "+this.listbox.ds_dvvt.value+" order by loaitb_id, loaihinh_tb"
      //     };
    
      // await API.runsql(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data.data_json && response.data.error && response.data.error_code === 'BSS-00000000') {
      //     this.listbox.ds_loaihinh.list = response.data.data.data_json.map(x=>{return {id:x['LOAITB_ID'],text:x['LOAIHINH_TB']}});
      //     this.listbox.ds_loaihinh.value = response.data.data.data_json[0].LOAITB_ID;

      //   }
      //   else{
      //     this.listbox.ds_loaihinh.list = [];
      //     this.listbox.ds_loaihinh.value = "";
      //   }
      // });
      var data = "p_dichvuvt_id="+this.listbox.ds_dvvt.value;
      await API.sp_lay_loaitb(this.axios,data).then(response=>{
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.listbox.ds_loaihinh.list = response.data.data.map(x=>{return {id:x['loaitb_id'],text:x['loaihinh_tb']}});
          this.listbox.ds_loaihinh.value = response.data.data[0].loaitb_id;

        }
        else{
          this.listbox.ds_loaihinh.list = [];
          this.listbox.ds_loaihinh.value = "";
        }
      });

      this.SelectList();
      this.tsbtnChiTietMGW.Enabled = false;
      if (this.listbox.ds_khoanmuc.value == KHOANMUC_TT.KMTT_DATMOI && (this.listbox.ds_dvvt.value == DichVuVienThong.MEGAWAN||this.listbox.ds_dvvt.value == DichVuVienThong.METRONET))
      {
        this.tsbtnChiTietMGW.Enabled = true;
      }
      if (this.listbox.ds_khoanmuc.value == KHOANMUC_TT.KMTT_DOITS && (this.listbox.ds_dvvt.value == DichVuVienThong.MEGAWAN||this.listbox.ds_dvvt.value == DichVuVienThong.METRONET))
      {
        this.tsbtnChiTietMGW.Enabled = true;
      }

    },

    cboLoaiHinh_Change:async function(){
     var data = {
                  "banggia_id": this.banggia_info.banggia_id,
                  "dichvuvt_id": this.listbox.ds_dvvt.value,
                  "kieu": 0,
                  "loaitb_id": this.listbox.ds_loaihinh.value
                };
      // API.lay_ds_loaihinh(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_loaihinh_chuagan.list = response.data.data;
      //   else
      //     this.listbox.ds_loaihinh_chuagan.list = [];
      // });
      this.listbox.ds_loaihinh_chuagan.list=await this.lay_ds_loaihinh(data)

      data = {
                  "banggia_id": this.banggia_info.banggia_id,
                  "dichvuvt_id": this.listbox.ds_dvvt.value,
                  "kieu": 1,
                  "loaitb_id": this.listbox.ds_loaihinh.value
                };
      this.listbox.ds_loaihinh_dagan.list=await this.lay_ds_loaihinh(data)
      // API.lay_ds_loaihinh(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_loaihinh_dagan.list = response.data.data;
      //   else
      //     this.listbox.ds_loaihinh_dagan.list = [];
      // });

    },

    cboKhoanMuc_Change:async function(){
      this.SelectList();
      this.tsbtnChiTietMGW.Enabled = false;
      if (this.listbox.ds_khoanmuc.value == KHOANMUC_TT.KMTT_DATMOI && (this.listbox.ds_dvvt.value == DichVuVienThong.MEGAWAN||this.listbox.ds_dvvt.value == DichVuVienThong.METRONET))
      {
        this.tsbtnChiTietMGW.Enabled = true;
      }
      if (this.listbox.ds_khoanmuc.value == KHOANMUC_TT.KMTT_DOITS && (this.listbox.ds_dvvt.value == DichVuVienThong.MEGAWAN||this.listbox.ds_dvvt.value == DichVuVienThong.METRONET))
      {
        this.tsbtnChiTietMGW.Enabled = true;
      }

    },

    tsbtnHuyBo_Click: function(){
      this.SetButton(0);
    },

    tsbtnNhapMoi_Click: function(){
      this.SetButton(1);
      this.$refs.txt_ten_bg.focus();
    },


    SelectList:async function(){
      var data={
        "dichvuvt_id": this.listbox.ds_dvvt.value,
        "khoanmuctt_id": this.listbox.ds_khoanmuc.value
      };
      let ds=await this.lay_ds_banggia(data)
      if(ds&&ds.length>0){
        this.listbox.ds_banggia.list = ds
        this.listbox.ds_banggia.value = ds[0];
      }else{
        this.listbox.ds_banggia.list=[]
      }
      // await API.lay_ds_banggia(this.axios,data).then((response) => { 
      //        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
      //         this.listbox.ds_banggia.list = response.data.data;
      //         this.listbox.ds_banggia.value = response.data.data[0];
      //       }
      //       else
      //       this.listbox.ds_banggia.list = [];
      //     }
      // );
      await this.load_ds_chuagan();
      await this.load_ds_dagan();

    },


    //p_kieu          IN     NUMBER, //0 chưa gán, 1 đã gán
    async load_ds_chuagan(){
      var data = {
                  "banggia_id": this.banggia_info.banggia_id,
                  "dichvuvt_id": this.listbox.ds_dvvt.value,
                  "kieu": 0,
                  "loaitb_id": this.listbox.ds_loaihinh.value
                };
      this.listbox.ds_loaihinh_chuagan.list=await this.lay_ds_loaihinh(data)
      // API.lay_ds_loaihinh(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_loaihinh_chuagan.list = response.data.data;
      //   else
      //     this.listbox.ds_loaihinh_chuagan.list = [];
      // });

      data = {
                "banggia_id": this.banggia_info.banggia_id,
                "dichvuvt_id": this.listbox.ds_dvvt.value,
                "khoanmuctt_id": this.listbox.ds_khoanmuc.value,
                "kieu": 0
              };
      this.listbox.ds_kieu_ld_chuagan.list =await this.lay_ds_kieu_ld(data)
      // API.lay_ds_kieu_ld(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_kieu_ld_chuagan.list = response.data.data;
      //   else
      //     this.listbox.ds_kieu_ld_chuagan.list = [];
      // });

      data = {
                "banggia_id": this.banggia_info.banggia_id,
                "kieu": 0
              };

      // API.lay_ds_doituongtb(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_doituongtb_chuagan.list = response.data.data;
      //   else
      //     this.listbox.ds_doituongtb_chuagan.list = [];
      // });
      this.listbox.ds_doituongtb_chuagan.list=await this.lay_ds_doituongtb(data)

      // API.lay_ds_trangbi(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_trangbi_chuagan.list = response.data.data;
      //   else
      //     this.listbox.ds_trangbi_chuagan.list = [];
      // });
      this.listbox.ds_trangbi_chuagan.list=await this.lay_ds_trangbi(data)

      // API.lay_ds_loaitb(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_loaitb_chuagan.list = response.data.data;
      //   else
      //     this.listbox.ds_loaitb_chuagan.list = [];
      // });
      this.listbox.ds_loaitb_chuagan.list=await this.lay_ds_loaitb(data)
    },
   
    async dgvDsBanggiaChange(obj){
      this.banggia_info = Object.assign({}, obj[0]);
      console.log(this.banggia_info);
      console.log(this.banggia_info.ngay_bd);
      console.log(this.banggia_info.ngay_kt);
      if (obj[0].khuvuc_id == null)
        this.listbox.ds_khuvuc.value = -1;
      else
        this.listbox.ds_khuvuc.value = obj[0].khuvuc_id;

      await this.load_ds_chuagan();
      await this.load_ds_dagan();
      this.SetButton(3);
    
      
    },

    async load_ds_dagan(){
      var data = {
                  "banggia_id": this.banggia_info.banggia_id,
                  "dichvuvt_id": this.listbox.ds_dvvt.value,
                  "kieu": 1,
                  "loaitb_id": this.listbox.ds_loaihinh.value
                };

      this.listbox.ds_loaihinh_dagan.list=await this.lay_ds_loaihinh(data)

      // API.lay_ds_loaihinh(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_loaihinh_dagan.list = response.data.data;
      //   else
      //     this.listbox.ds_loaihinh_dagan.list = [];
      // });

      data = {
                "banggia_id": this.banggia_info.banggia_id,
                "dichvuvt_id": this.listbox.ds_dvvt.value,
                "khoanmuctt_id": this.listbox.ds_khoanmuc.value,
                "kieu": 1
              };
      
      this.listbox.ds_kieu_ld_dagan.list =await this.lay_ds_kieu_ld(data)
      // API.lay_ds_kieu_ld(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_kieu_ld_dagan.list = response.data.data;
      //   else
      //     this.listbox.ds_kieu_ld_dagan.list = [];
      // });

      data = {
                "banggia_id": this.banggia_info.banggia_id,
                "kieu": 1
              };
      this.listbox.ds_doituongtb_dagan.list=await this.lay_ds_doituongtb(data)
      // API.lay_ds_doituongtb(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_doituongtb_dagan.list = response.data.data;
      //   else
      //     this.listbox.ds_doituongtb_dagan.list = [];
      // });
      this.listbox.ds_trangbi_dagan.list=await this.lay_ds_trangbi(data)
      // API.lay_ds_trangbi(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_trangbi_dagan.list = response.data.data;
      //   else
      //     this.listbox.ds_trangbi_dagan.list = [];
      // });
      this.listbox.ds_loaitb_dagan.list=await this.lay_ds_loaitb(data)
      // API.lay_ds_loaitb(this.axios,data).then(response=>{
      //   if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
      //     this.listbox.ds_loaitb_dagan.list = response.data.data;
      //   else
      //     this.listbox.ds_loaitb_dagan.list = [];
      // });

    },

    async lay_ds_loaihinh(data){
        try{
            this.loading(true)
            let response=await API.lay_ds_loaihinh(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
                
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async lay_ds_kieu_ld(data){
        try{
            this.loading(true)
            let response=await API.lay_ds_kieu_ld(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
                
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async lay_ds_doituongtb(data){
        try{
            this.loading(true)
            let response=await API.lay_ds_doituongtb(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
                
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async lay_ds_trangbi(data){
        try{
            this.loading(true)
            let response=await API.lay_ds_trangbi(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
                
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async lay_ds_loaitb(data){
        try{
            this.loading(true)
            let response=await API.lay_ds_loaitb(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
                
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async lay_ds_banggia(data){
        try{
            this.loading(true)
            let response=await API.lay_ds_banggia(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                return response.data.data
                
            }else{
                return []
            }
        }catch(e){
            this.loading(false)
            return []
        }
    },
    async sp_banggia_capnhat(data){
        try{
            this.loading(true)
            let response=await API.sp_banggia_capnhat(this.axios, data)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                return '1'
            }else{
                if(response&&response.data&&response.data.message){
                    return response.data.message
                }else{
                    return 'Đã xảy ra lỗi'
                }
            }
        }catch(e){
            this.loading(false)
            if(e.data&&e.data.message){
              return e.data.message
            }else if(e.response&&e.response.data&&e.response.data.message){
              return e.response.data.message
            }else{
              return 'Đã xảy ra lỗi'
            }
        }
    },

    tinh_vat:function(){
      // console.log(this.banggia_info);
      if (isNaN(this.banggia_info.tyle_vat)){
        this.banggia_info.vat = 0;
        this.banggia_info.vat_cd = 0;
        this.banggia_info.tyle_vat = 0;
      }
      else{
        if (this.banggia_info.tyle_vat != 0){
          this.banggia_info.vat = Math.trunc(this.banggia_info.tien*this.banggia_info.tyle_vat/100);
          this.banggia_info.vat_cd = Math.trunc(this.banggia_info.tien_cd*this.banggia_info.tyle_vat/100);
        }
      }
    
      // console.log(isNaN(this.banggia_info.tien));
      // console.log("this.banggia_info.tien: ",this.banggia_info.tien);
      // console.log("this.banggia_info.tien_cd: ",this.banggia_info.tien_cd);

      if (isNaN(this.banggia_info.tien)){
        this.banggia_info.vat = 0;
        this.banggia_info.tien = 0;
      }

      if (isNaN(this.banggia_info.tien_cd)){
        this.banggia_info.vat_cd = 0;
        this.banggia_info.tien_cd = 0;
      }
    

    },

    //bo gan
    btnNextLoaiHinh_Click:function(){
      var grid_dagan = this.$refs.DsLoaihinhDagan;
      //remove selected item
      grid_dagan.getSelectedRecords().forEach(obj => {
        this.listbox.ds_loaihinh_dagan.list = this.listbox.ds_loaihinh_dagan.list.filter(element => element !== obj);
        this.listbox.ds_loaihinh_chuagan.list.push(obj);
      });
    },

    //gan
    btnPreviousLoaiHinh_Click:function(){
      var grid_chuagan = this.$refs.DsLoaihinhChuagan;
      //remove selected item
      grid_chuagan.getSelectedRecords().forEach(obj => {
        this.listbox.ds_loaihinh_chuagan.list = this.listbox.ds_loaihinh_chuagan.list.filter(element => element !== obj);
        this.listbox.ds_loaihinh_dagan.list.push(obj);
      });
    },

    btnNextKieuLD_Click:function(){
      var grid_dagan = this.$refs.DsKieuLDDagan;
      //remove selected item
      grid_dagan.getSelectedRecords().forEach(obj => {
        this.listbox.ds_kieu_ld_dagan.list = this.listbox.ds_kieu_ld_dagan.list.filter(element => element !== obj);
        this.listbox.ds_kieu_ld_chuagan.list.push(obj);
      });

    },

    btnPreviousKieuLD_Click:function(){
      var grid_chuagan = this.$refs.DsKieuLDChuagan;
      //remove selected item
      grid_chuagan.getSelectedRecords().forEach(obj => {
        this.listbox.ds_kieu_ld_chuagan.list = this.listbox.ds_kieu_ld_chuagan.list.filter(element => element !== obj);
        this.listbox.ds_kieu_ld_dagan.list.push(obj);
      });
      
    },

    btnNextDoiTuongTB_Click:function(){
      var grid_dagan = this.$refs.DsDoiTuongTBDagan;
      //remove selected item
      grid_dagan.getSelectedRecords().forEach(obj => {
        this.listbox.ds_doituongtb_dagan.list = this.listbox.ds_doituongtb_dagan.list.filter(element => element !== obj);
        this.listbox.ds_doituongtb_chuagan.list.push(obj);
      });

    },

    btnPreviousDoiTuongTB_Click:function(){
      var grid_chuagan = this.$refs.DsDoiTuongTBChuagan;
      //remove selected item
      grid_chuagan.getSelectedRecords().forEach(obj => {
        this.listbox.ds_doituongtb_chuagan.list = this.listbox.ds_doituongtb_chuagan.list.filter(element => element !== obj);
        this.listbox.ds_doituongtb_dagan.list.push(obj);
      });
      
    },

    btnNextTrangBi_Click:function(){
      var grid_dagan = this.$refs.DsTrangBiDagan;
      //remove selected item
      grid_dagan.getSelectedRecords().forEach(obj => {
        this.listbox.ds_trangbi_dagan.list = this.listbox.ds_trangbi_dagan.list.filter(element => element !== obj);
        this.listbox.ds_trangbi_chuagan.list.push(obj);
      });

    },

    btnPreviousTrangBi_Click:function(){
      var grid_chuagan = this.$refs.DsTrangBiChuagan;
      //remove selected item
      grid_chuagan.getSelectedRecords().forEach(obj => {
        this.listbox.ds_trangbi_chuagan.list = this.listbox.ds_trangbi_chuagan.list.filter(element => element !== obj);
        this.listbox.ds_trangbi_dagan.list.push(obj);
      });
      
    },

    btnNextLoaiTbi_Click:function(){
       var grid_dagan = this.$refs.DsLoaiTBDagan;
      //remove selected item
      grid_dagan.getSelectedRecords().forEach(obj => {
        this.listbox.ds_loaitb_dagan.list = this.listbox.ds_loaitb_dagan.list.filter(element => element !== obj);
        this.listbox.ds_loaitb_chuagan.list.push(obj);
      });

    },

    btnPreviousLoaiTbi_Click:function(){
      var grid_chuagan = this.$refs.DsLoaiTBChuagan;
      //remove selected item
      grid_chuagan.getSelectedRecords().forEach(obj => {
        this.listbox.ds_loaitb_chuagan.list = this.listbox.ds_loaitb_chuagan.list.filter(element => element !== obj);
        this.listbox.ds_loaitb_dagan.list.push(obj);
      });
    },

    CapNhat: async function(){
      
      if (this.banggia_info.ten_bg == null || this.banggia_info.ten_bg == undefined || this.banggia_info.ten_bg == ""){
        this.$toast.error('Bạn chưa nhập tên bảng giá!');
        return;
      }
      
      if (this.banggia_info.tien == null || this.banggia_info.tien == undefined || this.banggia_info.tien == ""){
        this.$toast.error('Bạn chưa nhập tiền hợp đồng!');
        return;
      }

      if (this.banggia_info.vat == null || this.banggia_info.vat == undefined ){
        this.$toast.error('Bạn chưa nhập Vat của tiền hợp đồng!');
        return;
      }

       if (this.banggia_info.tien_cd == null || this.banggia_info.tien_cd == undefined ){
        this.$toast.error('Bạn chưa nhập tiền cài đặt!');
        return;
      }

      if (this.banggia_info.vat_cd == null || this.banggia_info.vat_cd == undefined ){
        this.$toast.error('Bạn chưa nhập Vat cài đặt!');
        return;
      }

      if (isNaN(this.banggia_info.tyle_vat)){
        this.$toast.error('Dữ liệu tỷ lệ VAT phải là kiểu số!');
        return;
      }

      if (this.banggia_info.ngay_bd == null || this.banggia_info.ngay_bd == undefined || this.banggia_info.ngay_bd === ""){
        this.$toast.error('Bạn chưa nhập ngày bắt đầu!');
        return;
      }

      if (this.banggia_info.ngay_kt == null || this.banggia_info.ngay_kt == undefined || this.banggia_info.ngay_kt === ""){
        this.$toast.error('Bạn chưa ngày kết thúc!');
        return;
      }

      if (this.banggia_info.ngay_bd == null || this.banggia_info.ngay_bd == undefined || this.banggia_info.ngay_bd === ""){
        this.$toast.error('Bạn chưa nhập ngày bắt đầu!');
        return;
      }

      if (moment(this.banggia_info.ngay_bd, 'DD/MM/YYYY').toDate() >= moment(this.banggia_info.ngay_kt, 'DD/MM/YYYY').toDate()){
        this.$toast.error('Từ ngày phải nhỏ hơn đến ngày!');
        return;
      }

      console.log(this.banggia_info.ngay_bd);
      console.log(this.banggia_info.ngay_kt);

      var ds_lhtb = [];
      var ds_kieuld = [];
      var ds_bgdoituong = [];
      var ds_bgtrangbi = [];
      var ds_bgtbi_dc = [];

      this.listbox.ds_loaihinh_dagan.list.forEach(item=>{
        let obj = {
          loaitb_id:item.loaitb_id,
          tocdo_id:item.tocdo_id,
          muccuoc_id:item.muccuoc_id,
        };
        ds_lhtb.push(obj);
      });

      this.listbox.ds_kieu_ld_dagan.list.forEach(item=>{
        let obj = {
          kieuld_id:item.kieuld_id
        };
        ds_kieuld.push(obj);
      });

      this.listbox.ds_doituongtb_dagan.list.forEach(item=>{
        let obj = {
          doituong_id:item.doituong_id
        };
        ds_bgdoituong.push(obj);
      });

      this.listbox.ds_trangbi_dagan.list.forEach(item=>{
        let obj = {
          trangbi_id:item.trangbi_id
        };
        ds_bgtrangbi.push(obj);
      });

      this.listbox.ds_loaitb_dagan.list.forEach(item=>{
        let obj = {
          thietbidc_id:item.thietbidc_id
        };
        ds_bgtbi_dc.push(obj);
      });

      if (this.banggia_info.banggia_id == null || this.banggia_info.banggia_id == undefined )
        this.banggia_info.banggia_id = 0;

      var data = {
                    "banggia_id": this.banggia_info.banggia_id,
                    "ten_bg": this.banggia_info.ten_bg,
                    "tien": this.banggia_info.tien,
                    "vat": this.banggia_info.vat,
                    "tien_cd": this.banggia_info.tien_cd,
                    "vat_cd": this.banggia_info.vat_cd,
                    "tyle_vat": this.banggia_info.tyle_vat,
                    "khoanmuctt_id": this.listbox.ds_khoanmuc.value,
                    "dichvuvt_id": this.listbox.ds_dvvt.value,
                    "khuvuc_id": (this.listbox.ds_khuvuc.value == -1)?0:this.listbox.ds_khuvuc.value,
                    "ngay_bd": this.banggia_info.ngay_bd,
                    "ngay_kt": this.banggia_info.ngay_kt,
                    "ds_lhtb":JSON.stringify(ds_lhtb),
                    "ds_kieuld": JSON.stringify(ds_kieuld),
                    "ds_bgdoituong": JSON.stringify(ds_bgdoituong),
                    "ds_bgtrangbi": JSON.stringify(ds_bgtrangbi),
                    "ds_bgtbi_dc": JSON.stringify(ds_bgtbi_dc)
                }
                console.log(data);
      let rs=await this.sp_banggia_capnhat(data)
      if(rs=='1'){
        this.$toast.success("Cập nhật thành công")
        this.SelectList()
      }else{
        this.$toast.error(rs)
      }
      // try{await API.sp_banggia_capnhat(this.axios,data).then(response=>{
      //   if(response.data.error_code === 'BSS-00000000') {
      //     this.$toast.success("Cập nhật thành công");
            
      //       this.SelectList();
      //   }
      //   else
      //     this.$toast.error(response.data.message);
      // })}
      // catch(error) {
      //   this.$toast.error(error.data.message)
      // }
    
    },

    
  tsbtnXoa_Click: async function(){
    this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            var data = {
               "banggia_id": this.banggia_info.banggia_id
            }
            await API.sp_banggia_xoa(this.axios,data).then(response=>{
              if(response.data.error_code === 'BSS-00000000') 
                this.$toast.success(response.data.message);
              else
                this.$toast.error(response.data.message);
          });
           this.SelectList();
          })
  },

  tsbtnChiTietMGW_Click: function(){
    //this.$refs.popupBangGiaMegawan.showModal();
    //this.$refs.popupBGDoiTDMgWan.showModal();
    if (this.listbox.ds_khoanmuc.value == KHOANMUC_TT.KMTT_DATMOI && ( this.listbox.ds_dvvt.value == DichVuVienThong.MEGAWAN||this.listbox.ds_dvvt.value == DichVuVienThong.METRONET))
      this.$refs.popupBangGiaMegawan.showModal();
    if (this.listbox.ds_khoanmuc.value == KHOANMUC_TT.KMTT_DOITS && (this.listbox.ds_dvvt.value == DichVuVienThong.MEGAWAN || this.listbox.ds_dvvt.value == DichVuVienThong.METRONET))
      this.$refs.popupBGDoiTDMgWan.showModal();
  }

  
  },


 
 
}
</script>