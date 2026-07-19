<template src="./SearchProfileDetail.html">
  
</template>

<script>
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import breadcrumb from '@/components/breadcrumb'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import gridviewmultiline from './gridview_multiline'
import gridview from '@/components/Shared/gridview'
import API from './API'
import grvTT from './grvTT.vue'
import Vue from 'vue'
import DatePicker from 'vue2-datepicker'
import moment from 'moment'
import xlsx from 'xlsx'
import html2pdf from "html2pdf.js";
import axios from 'axios'
        
export default {
  components: { gridview,gridviewmultiline,SearchContractModal,grvTT,KDatePicker,DatePicker, breadcrumb },
  name: 'SearchProfileDetail',

  data(){
    return{
      header: {
    },
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput: true,
      },
        dateFormat: 'DD-MM-YYYY',
        datetimeFormat: "DD/MM/YYYY HH:mm:ss",
        datetimeFormat: "YYYY-MM-DD HH:mm:ss",
      
      thongtin_hoso:{
        ma_gd:"",
        ma_hs:"",
        ngay_tao:"",
        tu_ngay: null,
        den_ngay:null,
        ma_kh:"",
        ten_kh:"",
        diachi:"",
        ngay_ycht:"",
        ngay_khnhan:"",
        ghichu:"",
        bohs_id:"",
        sdt_kh:"",
        mst:"",
        so_gt:"",
        ma_tb : "",
        contact_id: "",
        dv_khoitao :"",
        nguoi_tao: "",
        ten_bohs :"",
        loai_kh:"",
        spdv:"",
        so_dt:""
      },

      listbox:{
        list_kho:{
          list:[],
          value:""
        },
        list_trangthai:{
          list:[],
          value:""
        },
        list_chitiet_hoso:{
          list:[],
          headers:[{fieldName:'nhom_file',headerText:'Nhóm file',isGroupedColumn: true},
          {fieldName:'ma_tb',headerText:'Số máy/ Acc',allowfiltering: true},
          {fieldName:'ma_gd',headerText:'Mã GD',allowfiltering: true},
          {fieldName:'loai_file',headerText:'Loại file',allowfiltering: true},
          {fieldName:'ten_file',headerText:'Tên file',allowfiltering: true},
          {fieldName:'loai_hd',headerText:'Loại HĐ/PL',allowfiltering: true},
          {fieldName:'ngay_yc',headerText:'Ngày YC',allowfiltering: true},
          {fieldName:'nguoi_cn',headerText:'Người Upload',allowfiltering: true},
          {fieldName:'ngay_cn',headerText:'Ngày Upload',allowfiltering: true},
          {
            fieldName: 'url',
            headerText: 'Xem',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'Center',
            width: 90,
            template: function () {
              return {
                template: Vue.component('xoaLyDoHuyTemplate', {
                  template: `
                  <div>
                    <a class="btn btn-huylydo lh14 bg-primary text-white" @click="onClickDownload">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                      </svg>
                    </a>
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
                  methods: {
                    onClickDownload () {
                      this.parent.downloadItem(this.data)
                    },
                    onClickWatch(){

                    }
                  }
                })
              }
            }
          }]
        },

        list_chitiet_hoso_dientu:{
          list:[],
          headers:[
          {fieldName:'maGiaoDich',headerText:'Mã giao dịch',isGroupedColumn: true},
          {fieldName:'trangThai',headerText:'Trạng thái',allowfiltering: true},
          {fieldName:'ngayTao',headerText:'Ngày tạo',allowfiltering: true},
          {fieldName:'sdtKhachHang',headerText:'Số điện thoại khách hàng',allowfiltering: true},
          {fieldName:'loaiKhachHang',headerText:'Loại khách hàng',allowfiltering: true},
          {fieldName:'donViKhoiTao',headerText:'Đơn vị khởi tạo',allowfiltering: true},
          {fieldName:'maKhachHang',headerText:'Mã khách hàng',allowfiltering: true},
          {fieldName:'tenKhachHang',headerText:'Tên khách hàng',allowfiltering: true},
          {fieldName:'maSoThue',headerText:'Mã số thuế',allowfiltering: true},
          {fieldName:'soGiayTo',headerText:'Số giấy tờ',allowfiltering: true},
          {fieldName:'tenSPDV',headerText:'Tên SPDV',allowfiltering: true},
          {fieldName:'maThueBao',headerText:'Mã thuê bao',allowfiltering: true},
          {fieldName:'ghiChu',headerText:'Ghi chú',allowfiltering: true},
          {fieldName:'contractNumber',headerText:'Số hợp đồng',allowfiltering: true},
          {fieldName:'title',headerText:'Tiêu đề',allowfiltering: true},
          {
            fieldName: 'url',
            headerText: 'Xem',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'Center',
            width: 90,
            template: function () {
              return {
                template: Vue.component('xoaLyDoHuyTemplate', {
                  template: `
                  <div>
                    <a class="btn btn-huylydo lh14 bg-primary text-white" @click="onClickDownload()">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                      </svg>
                    </a>
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
                  methods: {
                   async onClickDownload () {
                    console.log('onClickDownload')
                    console.log(this.data.url)
                      // this.parent.downloadItem(this.data)
                        const response = await fetch(this.data.url, {
                          method: 'GET',
                          headers: {
                            'Content-Type': 'application/json'
                          }
                        })
                        console.log(response)
                        console.log(response.body)
                        if (response.ok) {
                          const blobData = await response.blob();
                          console.log(blobData);
                          const url = window.URL.createObjectURL(
                          new Blob([blobData], { type: 'application/pdf' }),
                          )
                          // const link = document.createElement('a')
                          // link.href = url
                          // link.setAttribute('view', `report`)
                          // document.body.appendChild(link)
                          // link.click()
                          // link.remove()
                          var win = window.open();
                          win.document.write('<iframe src="' + url + '" frameborder="0" style="border:0; top:0px; left:0px; bottom:0px; right:0px; width:100%; height:100%;" allowfullscreen></iframe>')
                        } else {
                          console.error("loi");
                        }
                    },
                    onClickWatch(){

                    }
                  }
                })
              }
            }
          }]
        },

        list_hoso_luanchuyen:{
          list:[],
          headers:[{fieldName:'huonggiao',headerText:'Hướng giao',isGroupedColumn: true},{fieldName:'dv_giao',headerText:'Đơn vị giao',allowfiltering: true},
                {fieldName:'ngaygiao',headerText:'Ngày giao',allowfiltering: true},{fieldName:'nv_giao',headerText:'Người giao',allowfiltering: true},
                {fieldName:'nd_giao',headerText:'ND giao',allowfiltering: true},{fieldName:'dv_nhan',headerText:'Đơn vị nhận',allowfiltering: true},
                {fieldName:'ngaytra',headerText:'Ngày trả',allowfiltering: true},{fieldName:'nd_tra',headerText:'ND trả',allowfiltering: true},
                {fieldName:'ngay_cn',headerText:'Ngày CN',allowfiltering: true},{fieldName:'nguoi_cn',headerText:'Người CN',allowfiltering: true},
                {fieldName:'so_dt',headerText:'Điện thoại liên hệ',allowfiltering: true},{fieldName:'trangthai_ph',headerText:'Trạng thái phiếu',allowfiltering: true}]
        },
         list_chuyenkho:{
          list:[],
          headers:[{fieldName:'ngaychuyen',headerText:'Ngày chuyển',isGroupedColumn: true},{fieldName:'giochuyen',headerText:'Giờ',allowfiltering: true},{fieldName:'nguoichuyen',headerText:'Người chuyển',allowfiltering: true},
          { fieldName:'vitricu',headerText:'Vị trí cũ',allowfiltering: true},{fieldName:'vitrimoi',headerText:'Vị trí mới',allowfiltering: true}]
        },

        list_tt:{
          list:[],
          headers:[
                  {
                  fieldName: '',
                  headerText: '',
                  allowFiltering: true,
                  allowSorting: false,
                  textAlign: 'Center',
                  width: 90,
                  template: function () {
                    return {
                      template: Vue.component('xoaLyDoHuyTemplate', {
                        template: `<a class="btn btn-huylydo lh14" @click="on_chon_magd">
                        <span class="icon nc-icon-glyph arrows-1_cloud-download-95"></span></a>`,
                        data () {
                          return {data: {}}
                        },
                        computed: {
                          parent () {
                            return this.$parent.$parent.$parent
                          }
                        },
                        methods: {
                          on_chon_magd () {
                            this.parent.chapnhan(this.data)
                          }
                        }
                      })
                    }
                  }
                },
                { fieldName: 'ma_gd', headerText: 'Mã GD' },   
                { fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu' },   
                { fieldName: 'ten_loaihd', headerText: 'Loại HĐ' },   
                { fieldName: 'ten_dv', headerText: 'Đơn vị lập HĐ/PL' }
            ]
        }
      }
    } 

  },

  mounted(){
    this.thongtin_hoso.ma_gd="";
    this.thongtin_hoso.ma_hs="";
    this.thongtin_hoso.ngay_tao="";
    //this.thongtin_hoso.ma_kh="";
    this.thongtin_hoso.ten_kh="";
    this.thongtin_hoso.diachi="";
    this.thongtin_hoso.ngay_ycht="";
    this.thongtin_hoso.ngay_khnhan="";
    this.thongtin_hoso.ghichu="";
    this.thongtin_hoso.bohs_id="";
    // this.thongtin_hoso.sdt_kh="";
    // this.thongtin_hoso.mst="";
    // this.thongtin_hoso.so_gt="";
    // this.thongtin_hoso.ma_tb="";
    // this.thongtin_hoso.contact_id="";
    // this.thongtin_hoso.dv_khoitao="";
    // this.thongtin_hoso.nguoi_tao="";
    // this.thongtin_hoso.ten_bohs="";
    // this.thongtin_hoso.loai_kh="";
    // this.thongtin_hoso.spdv="";
    // this.thongtin_hoso.tu_ngay="";
    // this.thongtin_hoso.den_ngay="";


    API.lay_danhsach_kho_hoso(this.axios).then(response=>{
      if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.listbox.list_kho.list = response.data.data.map(x=>{return {id:x['ID'],text:x['NAME']}});
      }
      else
        this.listbox.list_kho.list = [];
    });

    API.lay_danhsach_trangthai_bhs(this.axios).then(response=>{
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
        this.listbox.list_trangthai.list = response.data.data.map(x=>{return {id:x['ID'],text:x['NAME']}});
        this.listbox.list_trangthai.value = 1;
    }
    else
      this.listbox.list_trangthai.list = [];

    });
   
  },

  methods:{
     showPopupSearchContract(){   
      this.$refs.popupSearchContract.showModal();
    },

    acceptSearchContract:function(item){
      //console.log(item);
      this.thongtin_hoso.ma_gd = item.ma_gd;
    },
    clear_data:function(){
      //this.listbox.list_chitiet_hoso_dientu.list = []
        this.listbox.list_hoso_luanchuyen.list =[];
        this.thongtin_hoso.ma_hs="";
        this.thongtin_hoso.ngay_tao="";
        //this.thongtin_hoso.ma_kh="";
        this.thongtin_hoso.ten_kh="";
        this.thongtin_hoso.diachi="";
        this.thongtin_hoso.ngay_ycht="";
        this.thongtin_hoso.ngay_khnhan="";
        this.thongtin_hoso.ghichu="";
        this.thongtin_hoso.bohs_id="";
        this.listbox.list_kho.value = "";
        this.listbox.list_trangthai.value = "";
        this.thongtin_hoso.loai_kh = ""
        this.thongtin_hoso.dv_khoitao = ""
        this.thongtin_hoso.spdv = ""
        //this.thongtin_hoso.ten_bohs = ""
        this.listbox.list_chitiet_hoso.list = [];
        this.listbox.list_hoso_luanchuyen.list = [];
        this.listbox.list_chuyenkho.list = [];
        this.listbox.list_tt.list = [];

    },

    getData(e){
      console.log(e.data)
      if(e.data){
        this.thongtin_hoso.loai_kh = e.data.loaiKhachHang
        this.thongtin_hoso.dv_khoitao = e.data.donViKhoiTao
        this.thongtin_hoso.spdv = e.data.tenSPDV
        this.thongtin_hoso.contact_id = e.data.contractId
      }
    },

    tim_kiem: async function(){
      //try{
      this.loading(true);
      this.clear_data();
      //kieu=2&maGd=HPG-TT/01128236


      let input = {}

      if(this.thongtin_hoso.ma_kh !== "") input.maKhachHang = this.thongtin_hoso.ma_kh
      if(this.thongtin_hoso.tu_ngay!== null) input.tuNgay = moment(this.thongtin_hoso.tu_ngay,'DD/MM/YYYY').format("YYYY-MM-DD")
      if(this.thongtin_hoso.den_ngay!== null) input.denNgay = moment(this.thongtin_hoso.den_ngay,'DD/MM/YYYY').format("YYYY-MM-DD")
      if(this.thongtin_hoso.ma_gd !== "") input.maGiaoDich = this.thongtin_hoso.ma_gd.toString()
      if(this.thongtin_hoso.ma_tb !== "") input.maThueBao = this.thongtin_hoso.ma_tb
      if(this.thongtin_hoso.mst !== "") input.maSoThue = this.thongtin_hoso.mst
      if(this.thongtin_hoso.contact_id !== "") input.contractId = this.thongtin_hoso.contact_id
      if(this.thongtin_hoso.so_gt !== "") input.soGiayTo = this.thongtin_hoso.so_gt
      if(this.thongtin_hoso.so_dt !== "") input.soDienThoai = this.thongtin_hoso.so_dt
      const input_tracuuhd = `${new URLSearchParams(input).toString()}`;
      // let abc = Object.keys(input)
      // for(let i = 0; i < abc.length ; i++){
      //   if(i == 0){
      //     input_tracuuhd +=`?`
      //   }else input_tracuuhd +=`&`
      //   input_tracuuhd += `${abc[i]}=${input[abc[i]]}`
      // }    
      console.log(input)
      const resp = await API.tracuu_hopdong(this.axios , input_tracuuhd)
      console.log(resp)
      if( resp.data.errorCode == 0 && resp.data.data.length > 0){
        this.listbox.list_chitiet_hoso_dientu.list = resp.data.data;
      }else{
        this.listbox.list_chitiet_hoso_dientu.list = []
        this.$toast.warning("Không có thông tin hợp đồng điện tử với mã giao dịch");
      }
      
      if (this.thongtin_hoso.ma_gd == "" || this.thongtin_hoso.ma_gd == null){
        this.$toast.error('Bạn phải nhập mã giao dịch hoặc mã thuê bao');
        this.$refs.txt_magd.focus();
        this.loading(false);
        return;
      }
      
      var showpopup = -1;
      var data = "?maTb="+this.thongtin_hoso.ma_gd;
      await API.lay_danhsach_hopdong_theo_matb(this.axios,data).then(response=>{
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {

            // var distinct = response.data.data.map(x=>{return {ma_gd:x['ma_gd'],ma_kh:x['ma_kh'],ngay_yc:x['ngay_yc'],hdkh_id:x['hdkh_id'],donvi_id:x['donvi_id'],
            // ten_dv:x['ten_dv'],loaihd_id:x['loaihd_id'],ten_loaihd:x['ten_loaihd'],bohs:x['bohs']}}).values();
            
            var distinct = []
            for (var i = 0; i < response.data.data.length; i++){
              var obj = {ma_gd:response.data.data[i]['ma_gd'],
                        ma_kh:response.data.data[i]['ma_kh'],
                        ngay_yc:response.data.data[i]['ngay_yc'],
                        hdkh_id:response.data.data[i]['hdkh_id'],
                        donvi_id:response.data.data[i]['donvi_id'],
                        ten_dv:response.data.data[i]['ten_dv'],
                        loaihd_id:response.data.data[i]['loaihd_id'],ten_loaihd:response.data.data[i]['ten_loaihd'],bohs:response.data.data[i]['bohs']};
              
              if (distinct.length == 0)
                distinct.push(obj);

              for (let j = 0;j<distinct.length;j++){
                if (JSON.stringify(distinct[j]) === JSON.stringify(obj)){

                }
                else
                  distinct.push(obj);

              }
            }
            console.log(distinct);
            //neu chi co 1 dong
            if(distinct.length==1){
              if(distinct[0].bohs == 0){
                this.clear_data();
                this.$toast.warning('Không tìm thấy thông tin hồ sơ giấy với mã giao dịch ' + this.thongtin_hoso.ma_gd);
              }
              else
                showpopup = 0;
            }

            //send toàn bộ danh sách ra 
            else{
                this.listbox.list_tt.list = response.data.data;
                showpopup = 1;
                this.$refs.popupgrvTT.show();
            }
          }
          else{
            this.$toast.warning('Không tìm thấy thông tin hồ sơ giấy với mã giao dịch ' + this.thongtin_hoso.ma_gd);
          }
      });
      

      if (showpopup == 0){
        data = "?kieu=1&maGd="+this.thongtin_hoso.ma_gd;
        await API.tracuu_chitiet_hoso(this.axios,data).then(response=>{
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.list_hoso_luanchuyen.list = [];
            if (!Array.isArray(response.data.data)){
              this.listbox.list_hoso_luanchuyen.list.push(response.data.data);
              this.thongtin_hoso.ma_hs=response.data.data.ma_bhs;
              this.thongtin_hoso.ngay_tao=moment(response.data.data.ngaytao).format("DD/MM/YYYY HH:mm:ss"); 
              this.thongtin_hoso.ma_kh=response.data.data.ma_kh;
              this.thongtin_hoso.ten_kh=response.data.data.ten_kh;
              this.thongtin_hoso.diachi=response.data.data.diachi_kh;
              this.thongtin_hoso.ngay_ycht= moment(response.data.data.ngay_ht).format("DD/MM/YYYY HH:mm:ss"); 
              this.thongtin_hoso.ngay_khnhan=moment(response.data.data.ngaykh_nhan).format("DD/MM/YYYY HH:mm:ss"); 
              this.thongtin_hoso.ghichu=response.data.data.ghichu;
              this.thongtin_hoso.bohs_id=response.data.data.bohs_id;
              this.listbox.list_kho.value = response.data.data.hoso_id;
              this.listbox.list_trangthai.value = response.data.data.tths_id;

            }
            else{
              this.listbox.list_hoso_luanchuyen.list = response.data.data;
              this.thongtin_hoso.ma_hs=response.data.data[0].ma_bhs;
              this.thongtin_hoso.ngay_tao=moment(response.data.data[0].ngaytao).format("DD/MM/YYYY HH:mm:ss"); 
              this.thongtin_hoso.ma_kh=response.data.data[0].ma_kh;
              this.thongtin_hoso.ten_kh=response.data.data[0].ten_kh;
              this.thongtin_hoso.diachi=response.data.data[0].diachi_kh;
              this.thongtin_hoso.ngay_ycht=moment(response.data.data[0].ngay_ht).format("DD/MM/YYYY HH:mm:ss"); 
              this.thongtin_hoso.ngay_khnhan=moment(response.data.data[0].ngaykh_nhan).format("DD/MM/YYYY HH:mm:ss"); 
              this.thongtin_hoso.ghichu=response.data.data[0].ghichu;
              this.thongtin_hoso.bohs_id=response.data.data[0].bohs_id;
              this.listbox.list_kho.value = response.data.data[0].hoso_id;
              this.listbox.list_trangthai.value = response.data.data[0].tths_id;
            }
            console.log(this.thongtin_hoso.ngay_ycht);
              
          }
          else{
          
            this.$toast.success('Không tìm thấy thông tin với mã giao dịch');
          }
        });

      
        data="?kieu=2&maGd="+this.thongtin_hoso.ma_gd;
        await API.tracuu_chitiet_hoso(this.axios,data).then(response=>{
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                this.listbox.list_chuyenkho.list = response.data.data;
            }
            else
              this.listbox.list_chuyenkho.list = [];
        });

        data="?bohsId="+this.thongtin_hoso.bohs_id;
        await API.lay_ds_file_hoso(this.axios,data).then(response=>{
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                this.listbox.list_chitiet_hoso.list = response.data.data;
            }
            else
              this.listbox.list_chitiet_hoso.list = [];
        });


      }

      this.loading(false);
      // }catch{

      // }
      // finally{
      //   this.loading(false)
      // }
    },

    xuat_file (){
      let wb = xlsx.utils.book_new();
      console.log(wb)
      //xlsx.writeFile(wb, 'danhsachbohopdong.xlsx');
      if(this.listbox.list_chitiet_hoso.list.length > 0){
        let exportData = []
      exportData = this.listbox.list_chitiet_hoso.list.map((e) => {
        let exportEntity = {}
        this.listbox.list_chitiet_hoso.headers.map((x) => {
          exportEntity[x.fieldName] = e[x.fieldName]
        })
        return exportEntity
      })
      console.log(exportData)
      const data_columns = [
        { header: 'Nhóm file', key: 'nhom_file', width: 20 },
        { header: 'Số máy/ Acc', key: 'ma_tb', width: 30 },
        { header: 'Mã GD', key: 'ma_gd', width: 30 },
        { header: 'Loại file', key: 'loai_file', width: 30 },
        { header: 'Tên file', key: 'ten_file', width: 30 },
        { header: 'Loại HĐ/PL', key: 'loai_hd', width: 30 },
        { header: 'Ngày YC', key: 'ngay_yc', width: 30 },
        { header: 'Người Upload', key: 'nguoi_cn', width: 30 },
        { header: 'Ngày Upload', key: 'ngay_cn', width: 30 },
        { header: 'URL', key: 'url', width: 30 },
      ]
          const data = exportData.map(x => {
            let row = {}
            data_columns.map(e => {
              row[e.header] = x[e.key]
            })
            return row
          })
          console.log(data)
          const ws1 = xlsx.utils.json_to_sheet(data)
          xlsx.utils.book_append_sheet(wb, ws1, 'Danh sách bộ hợp đồng giấy');
      }
      if(this.listbox.list_chitiet_hoso_dientu.list.length > 0){
        let exportData = []
      exportData = this.listbox.list_chitiet_hoso_dientu.list.map((e) => {
        let exportEntity = {}
        this.listbox.list_chitiet_hoso_dientu.headers.map((x) => {
          exportEntity[x.fieldName] = e[x.fieldName]
        })
        return exportEntity
      })
      console.log(exportData)
      const data_columns = [
        { header: 'Mã giao dịch', key: 'maGiaoDich', width: 20 },
        { header: 'Trạng thái', key: 'trangThai', width: 20 },
        { header: 'Ngày tạo', key: 'ngayTao', width: 30 },
        { header: 'Số điện thoại khách hàng', key: 'sdtKhachHang', width: 30 },
        { header: 'Loại khách hàng', key: 'loaiKhachHang', width: 30 },
        { header: 'Đơn vị khởi tạo', key: 'donViKhoiTao', width: 30 },
        { header: 'Mã khách hàng', key: 'maKhachHang', width: 30 },
        { header: 'Tên khách hàng', key: 'tenKhachHang', width: 30 },
        { header: 'Mã số thuế', key: 'maSoThue', width: 30 },
        { header: 'Số giấy tờ', key: 'soGiayTo', width: 30 },
        { header: 'Tên SPDV', key: 'tenSPDV', width: 30 },
        { header: 'Mã thuê bao', key: 'maThueBao', width: 30 },
        { header: 'Ghi chú', key: 'ghiChu', width: 30 },
        { header: 'Số hợp đồng', key: 'contractNumber', width: 30 },
        { header: 'Tiêu đề', key: 'title', width: 30 },
        { header: 'URL', key: 'url', width: 30 },
      ]
          const data = exportData.map(x => {
            let row = {}
            data_columns.map(e => {
              row[e.header] = x[e.key]
            })
            return row
          })
          console.log(data)
          const ws2 = xlsx.utils.json_to_sheet(data)
          xlsx.utils.book_append_sheet(wb, ws2, 'Danh sách hợp đồng điện tử');
      }
      console.log(wb.SheetNames)
      if (wb && wb.SheetNames.length > 0) {
        xlsx.writeFile(wb, 'danhsachbohopdong.xlsx');
      } else {
        this.$toast.error("Không có dữ liệu cho bất kỳ sheet nào. Không thể xuất file.")
      }
    },

  downloadItem (obj) {
    // this.axios.get(obj.url, { responseType: 'blob' ,crossDomain:true})
    //   .then(response => {
    //     const blob = new Blob([response.data], { type: 'application/pdf' })
    //     const link = document.createElement('a')
    //     link.href = URL.createObjectURL(blob)
    //     link.download = obj.ten_file
    //     link.click()
    //     URL.revokeObjectURL(link.href)
    //   }).catch(console.error)

    window.open(obj.url)

    console.log(obj);
  },

  parent_chapnhan:function(obj){
    
     //this.$toast.success('xxxxxxxxxxxxxxxxx');
     this.thongtin_hoso.ma_gd = obj.ma_gd;
     this.tim_kiem();
     this.$refs.popupgrvTT.hide();
  }

 


  }
 
 
}
</script>