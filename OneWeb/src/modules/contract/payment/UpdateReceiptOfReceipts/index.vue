<template src="./UpdateReceiptOfReceipts.html"></template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from './api'
import "@/assets/vendor/jquery/split";
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV} from "@/const/enums";
import {MultiSelect, CheckBoxSelection} from '@syncfusion/ej2-dropdowns';
MultiSelect.Inject(CheckBoxSelection);
Vue.use(DialogPlugin);
import {
  Sort,
  Filter,
  Resize,
  Group,
  Freeze,
  CommandColumn,
  ExcelExport,
  Edit,
  Aggregate,
  Toolbar,
} from "@syncfusion/ej2-vue-grids";
export default {
    components: {
    breadcrumb,LoaiHopDong,DichVuVienThong,TrangThaiHD,LOAI_DV
  },
  provide: {
        grid: [
            Sort,
            Filter,
            Resize,
            Group,
            Freeze,
            CommandColumn,
            ExcelExport,
            Edit,
            Aggregate,
            Toolbar,
        ],
    },
  data: function () {
    return {
        loaibienlai_id:-1,loaihd_id:-1,
        bienlai_id:-1,luottao_id:-1,
        tag:"",title_page:"",txtMa_GD:"",txtMaKH:"",
        chkTuNgay:false,chkDenNgay:false,
        dtpTuNgay:"",dtpDenNgay:"",
        tsbtnGan:{
            Text:"Gán thêm"
        },
        ds_Gan:[],
        ds_GoGan:[],
        options:{
            cboLoaiBL:[],
            cboLuotTao:[],
            cboBienLai:[],
        },
        ds:{//ejs
            list:[],
            header:[
          {fieldName: 'ten_loaihd', headerText: 'Loại HĐ', allowFiltering: true, allowHtml : true, allowSorting: false,},
          {fieldName: 'chon', allowEditing:true,   headerText: 'Chọn', visible:false, allowFiltering: true, allowSorting: false},
          {fieldName: 'ma_gd', textAlign:'center',    headerText: 'Mã GD', allowHtml : true, allowFiltering: true, allowSorting: false,},
          {fieldName: 'ma_kh', textAlign:'center',    headerText: 'Mã KH',allowHtml : true, allowFiltering: true, allowSorting: false,},
          {fieldName: 'ten_kh', textAlign:'center',   headerText: 'Tên khách hàng', allowHtml : true, allowFiltering: true, allowSorting: false,},
          {fieldName: 'tongtien', textAlign:'right',  headerText: 'Tổng tiền', allowHtml : true, allowFiltering: true, allowSorting: false,},
        ],
             checked: [],
             value: {},
             cols:[],
        },
        dsGan:{
             list:[],
            header:[
          {fieldName: 'ten_loaihd', headerText: 'Loại HĐ', allowFiltering: true, allowHtml : true, allowSorting: false,},
          {fieldName: 'chon', allowEditing:true,   headerText: 'Chọn', visible:false, allowFiltering: true, allowSorting: false},
          {fieldName: 'ma_gd', textAlign:'center',    headerText: 'Mã GD', allowHtml : true, allowFiltering: true, allowSorting: false,},
          {fieldName: 'ma_kh', textAlign:'center',    headerText: 'Mã KH',allowHtml : true, allowFiltering: true, allowSorting: false,},
          {fieldName: 'ten_kh', textAlign:'center',   headerText: 'Tên khách hàng', allowHtml : true, allowFiltering: true, allowSorting: false,},
          {fieldName: 'tongtien', textAlign:'right',  headerText: 'Tổng tiền', allowHtml : true, allowFiltering: true, allowSorting: false,},
        ],
             checked: [],
             value: {},
             cols:[],
        },
        listbox:{
            cboLoaiHD:{
            valueitems:"",
            list:[],
            values:"",
            }
        },
        dgvChuaGan:{ //datagrid
            loading: false,
            options: [],
            config: [
            {
                type:"checkbox",
                width:"39",
                maxWidth:"39",
                minWidth:"39",
                headerTextAlign:"center" ,
                textAlign:"center",
                customAttributes:{class: 'column-check-selected'},
                allowResizing:false,
            },
            {
                headerText: 'Loại HĐ',
                fieldName: 'ten_loaihd',
                allowFiltering: true,
                allowSorting: true,
            },
            {
                headerText: 'Mã GD',
                fieldName: 'ma_gd',
                allowFiltering: true,
                allowSorting: true,
                allowEditing:true,
            },
            {
                headerText: 'Mã KH',
                fieldName: 'ma_kh',
                allowFiltering: true,
                allowSorting: true,
                allowEditing:false,
            },
            {
                headerText: 'Tên khách hàng',
                fieldName: 'ten_kh',
                allowFiltering: true,
                allowSorting: true,
            },
            {
                headerText: 'Địa chỉ KH',
                fieldName: 'diachi_kh',
                allowFiltering: true,
                allowSorting: true,
            },
            {
                headerText: 'Tổng tiền',
                fieldName: 'tongtien',
                allowFiltering: true,
                allowSorting: true,
                textAlign: 'right',
            },
        ],
        checked:[],
        },
         dgvDaGan:{
            loading: false,
            options: [],
            config: [
            {
                headerText: 'Loại HĐ',
                fieldName: 'ten_loaihd',
                allowFiltering: true,
                allowSorting: true,
            },
            {
                headerText: 'Mã GD',
                fieldName: 'ma_gd',
                allowFiltering: true,
                allowSorting: true,
            },
            {
                headerText: 'Mã KH',
                fieldName: 'ma_kh',
                allowFiltering: true,
                allowSorting: true,
            },
            {
                headerText: 'Tên khách hàng',
                fieldName: 'ten_kh',
                allowFiltering: true,
                allowSorting: true,
            },
            {
                headerText: 'Địa chỉ KH',
                fieldName: 'diachi_kh',
                allowFiltering: true,
                allowSorting: true,
            },
            {
                headerText: 'Tổng tiền',
                fieldName: 'tongtien',
                allowFiltering: true,
                allowSorting: true,
                textAlign: 'right',
            },
        ]
        },
    }
  },
  computed:{

  },
  methods:{
       deselectedItems_click:function (dataKeys) {
            this.ds.checked.splice(this.ds.checked.indexOf(dataKeys.data),1);

        },
        selectedItems_click: function (dataKeys) {
            if(dataKeys.data.length > 0){
                dataKeys.data.forEach((obj)=>{
                    this.ds.checked.push(obj);

                })
            }
            else{
                    this.ds.checked.push(dataKeys.data);

            }
        },
        selectedItems_click1:function (dataKeys){
            console.log(dataKeys);
            if(dataKeys.data.length > 0){
                dataKeys.data.forEach((obj)=>{
                    this.dsGan.checked.push(obj);

                })
            }
            else{
                    this.dsGan.checked.push(dataKeys.data);

            }
        },
        deselectedItems_click1: function(dataKeys){
            this.dsGan.checked.splice(this.ds.checked.indexOf(dataKeys.data),1);

        },
      xuLyTag(tag){
                try
                {
                    if (tag != "")
                    {
                        let words = tag.split('+');
                        if (words.length >= 1)
                            tag = words[0];   // tag = 2 : cho phép cập nhật thêm hợp đồng vào biên lai
                    }
                    else
                    {
                        tag = 1;
                    }
                }
                catch (e)
                {
                    tag = 1;
                }

      },
      numberWithCommas(n) {
          try{
               var parts = n.toString().split(".");
            return (parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +(parts[1] ? "." + parts[1] : ""));
          }
          catch(e){
              return n;
          }

        },
      queryCellInfo: function (args) {
            if (args.column.field === "tongtien"){
                var result = args.data.tongtien;
                    var setVal = this.numberWithCommas(result);
                    args.data.tongtien = setVal;
                    args.cell.innerText = setVal;
            }
         },
     async loadData() {
            try {
            this.loading(true);
            this.ds_GoGan = [];
            this.ds.list =[];
            this.ds.checked = [];
            this.dsGan.list = [];this.dsGan.checked = [];
            this.ds_Gan = [];

            await this.HienThi_DanhSach_HDKH();
            await this.lay_dsBienLaiHDKH(this.bienlai_id);


            this.loading(false)
            } catch (err) {
                this.loading(false);
            this.$toast.error("Load dữ liệu không thành công");
            }
        },
    async loadForm(){
        try{

            this.ds_GoGan = [];
            this.ds.list =[];
            this.ds.checked = [];
            this.dsGan.list = [];this.dsGan.checked = [];
            this.ds_Gan = [];
           await  this.lay_kieuBL();
           await  this.lay_loaiHD();
           this.loading(true);
           await  this.HienThi_DanhSach_HDKH();
            //await Promise.all([lay_kieuBL,lay_loaiHD,HienThi_DanhSach_HDKH]);

            this.loading(false)
        }
        catch(e){
            this.loading(false);
        }
    },
     async lay_kieuBL(){
        let response = await api.lay_kieuBL(this.axios,{});
        this.options.cboLoaiBL = response.data.data;
        this.loaibienlai_id = this.options.cboLoaiBL[0].loaibienlai_id;
        this.lay_dsLuotTao(this.loaibienlai_id);

      },
      lay_loaiHD(){
          api.lay_loaiHD(this.axios,{"loaihd_id":"1,3,6,8,31"}).then((response) =>{
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                    if (response.data.data !== null) {
                    this.listbox.cboLoaiHD.list = response.data.data;
                    }
                }
                })
      },
      async lay_dsLuotTao(val){
          try{

                let res = await api.lay_dsLuotTao(this.axios,{
                            "vloaibl_id": val,
                            "vnhanvien_id":this.nhanvien_id,
                            "vkieu":this.tag,
                        })
                if(res.data.data != ""){
                    this.options.cboLuotTao = res.data.data;
                    this.luottao_id = this.options.cboLuotTao[0].luottao_id;
                    this.lay_dsBienLai(this.luottao_id);
                }
                else{
                    this.options.cboLuotTao = [];
                    this.luottao_id = -1;
                    this.lay_dsBienLai(this.luottao_id);
                }

            }
            catch(e){
                this.options.cboLuotTao = [];
                this.luottao_id = -1;
                console.log(e);
            }

      },
      async lay_dsBienLai(val){
          try{
               let res = await api.lay_dsBienLai(this.axios,{
                            "vluottao_id": val,
                            "vnhanvien_id":this.nhanvien_id,
                            "vkieu":this.tag,
                        })
                if(res.data.data != ""){
                    this.options.cboBienLai = res.data.data;
                    this.bienlai_id = -1;
                    //this.bienlai_id = this.options.cboBienLai[0].bienlai_id;

                }
                else{
                    this.options.cboBienLai = [];
                    this.bienlai_id = -1;

                }
          }
          catch(e){
              console.log(e);
              this.options.cboBienLai = [];
              this.bienlai_id = -1;
          }


      },
      async lay_dsBienLaiHDKH(val){

          try{
              if(val == "" || val == -1) {
                this.dsGan.list = [];
                   return;
              }

               let res = await api.lay_dsBienLaiHDKH(this.axios,{
                            "vbienlai_id": val,
                        })

                this.dsGan.list = res.data.data;
                this.ds_Gan = [...this.dsGan.list];

          }
          catch(e){
              this.dsGan.list = [];
              this.ds_Gan = [];
              console.log(e);
          }


      },


     async HienThi_DanhSach_HDKH(){

         try{
            var vdsloaihd_id = "";
            if((this.listbox.cboLoaiHD.valueitems).length > 0){
                this.listbox.cboLoaiHD.valueitems.forEach((obj)=>{
                    vdsloaihd_id = obj + ",";
                })
                vdsloaihd_id = vdsloaihd_id.substring(0,vdsloaihd_id.length-1);
            }

           let response = await api.lay_ds_hdkh_chuagan_bienlai(this.axios,{
                "vdsloaihd_id": (vdsloaihd_id != "")?vdsloaihd_id:0,
                "vnhanvien_id": this.nhanvien_id,
                "vtu_ngay": this.chkTuNgay?this.dtpTuNgay:0,
                "vden_ngay": this.chkDenNgay?this.dtpDenNgay:0,
                "vma_kh": this.txtMaKH.trim(),
                "vma_gd": this.txtMa_GD.trim(),
           });

            this.ds.list = response.data.data;
         }
         catch(e){
             console.log(e);

         }
      },
      async kiemtra_ins_bienlai_hdKH(vdsloaihd_id, bienlai_id){

              let response = await api.kiemtra_ins_bienlai_hdKH(this.axios,{
                "vdshdkh_id": (vdsloaihd_id != "")?vdsloaihd_id:0,
                "vbienlai_id": bienlai_id,
           });
              return response;
      },
      async capnhat_them_bienlai(vdsloaihd_id, bienlai_id){
           let response = await api.capnhat_them_bienlai(this.axios,{
                "vdshdkh_id": (vdsloaihd_id != "")?vdsloaihd_id:0,
                "vbienlai_id": bienlai_id,
           });
              return response;
      },
      async capnhat_phathanh_bienlai(vdsloaihd_id, bienlai_id){
           let response = await api.capnhat_phathanh_bienlai(this.axios,{
                "vdshdkh_id": (vdsloaihd_id != "")?vdsloaihd_id:0,
                "vbienlai_id": bienlai_id,
           });
              return response;
      },
      tsbtnPhatHanh_Click(){
          try
            {
                if (this.ds_Gan.length > 0)
                {
                    var dshdkh_id = "";
                    for (var i = 0; i < this.ds_Gan.length; i++)
                    {
                        dshdkh_id += this.ds_Gan[i].hdkh_id + ",";
                    }
                    if (dshdkh_id.length > 0)
                    {
                        dshdkh_id = dshdkh_id.substring(0, dshdkh_id.length - 1);


                        var vkq1 = "";  var vkq = "";
                    this.kiemtra_ins_bienlai_hdKH(dshdkh_id, this.bienlai_id).then((response)=>{
                        console.log(response.data.data);
                        if(response.data.error_code === "BSS-00000000"){
                            vkq1 = response.data.data;
                            if (vkq1 != "OK" && vkq1 != null)
                            {
                                this.$toast.error(vkq1);
                                return;
                            }
                                if(this.bienlai_id == -1 || this.options.cboBienLai.length <= 0){
                                    this.$toast.error("Bạn chưa chọn biên lai để phát hành !");
                                    return;
                                }
                                this.capnhat_phathanh_bienlai(dshdkh_id, this.bienlai_id).then((resp)=>{
                                if(resp.data.error_code === "BSS-00000000"){
                                    vkq = resp.data.data;
                                    if (vkq != "OK" && vkq != null)
                                    {
                                        this.$toast.error(vkq);
                                        return;
                                    }
                                    else{
                                        this.$toast.success("Phát hành biên lai thành công !");
                                         try {
                                            this.ds_GoGan = [];
                                            this.ds.list =[];
                                            this.ds.checked = [];
                                            this.dsGan.list = [];
                                            this.dsGan.checked = [];
                                            this.ds_Gan = [];
                                            this.lay_dsLuotTao(this.loaibienlai_id);
                                            this.HienThi_DanhSach_HDKH();
                                        } catch (err) {
                                            this.$toast.error("Load dữ liệu không thành công");
                                            }
                                    }
                                }
                                else{
                                    if(vqk == ""){
                                        this.$toast.error("Phát hành biên lai thất bại");
                                    }
                                }

                                })

                            }
                        else{
                            if(vkq1 == "")
                            this.$toast.error("Phát hành biên lai thất bại");
                        }

                    })
                    }
                }
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi trong quá trình xử lý");
                console.log("Có lỗi : " + ex);
            }
      },
      tsbtnGan_Click(){
        try
            {

                    var dshdkh_id = "";
                    for (var i = 0; i < this.ds_Gan.length; i++)
                    {

                            dshdkh_id += this.ds_Gan[i].hdkh_id + ",";
                    }


                    if (dshdkh_id.length > 0) dshdkh_id = dshdkh_id.substring(0, dshdkh_id.length - 1);
                    else dshdkh_id = "0";
                    //|| this.options.cboBienLai[this.bienlai_id].so_bl  == ""
                    if (this.options.cboBienLai.length <=0 || this.bienlai_id < 0 )
                    {
                        this.$toast.error("Bạn chưa chọn biên lai để thực hiện !");
                        return;
                    }
                    var vkq1 = "";
                    this.kiemtra_ins_bienlai_hdKH(dshdkh_id, this.bienlai_id).then((response)=>{


                        if(response.data.error === "200"){
                            vkq1 = response.data.data;
                            if (vkq1 != "OK")
                            {
                                this.$toast.error(vkq1);
                                return;
                            }
                            else{
                                this.capnhat_them_bienlai(dshdkh_id, this.bienlai_id).then((resp)=>{
                                if(resp.data.data != null && resp.data.data != undefined){
                                     var vkq = resp.data.data;
                                    if(resp.data.error === "200"){
                                        if (vkq != "OK")
                                        {
                                            this.$toast.error(vkq);
                                            return;
                                        }
                                        else{
                                            this.$toast.success("Cập nhật biên lai thành công !");
                                            this.loadData();

                                        }
                                    }
                                    else{
                                        this.$toast.error(vkq);
                                    }
                                }
                                else{
                                    this.$toast.error("Có lỗi trong quá trình xử lý");
                                }

                                })
                            }
                        }
                        else{
                            if(vkq1 == ""){
                                this.$toast.error("Cập nhật thất bại");
                            }

                        }

                    })
          }
            catch (ex)
            {
                this.$toast.error("Có lỗi trong quá trình xử lý");
                console.log("Có lỗi : " + ex);
            }

      },
      searchByMaKH(){
          this.loadData();
          //this.lay_dsBienLaiHDKH(this.bienlai_id);
      },
      searchByMaGD(){
          this.loadData();
         // this.lay_dsBienLaiHDKH(this.bienlai_id);
      },
      btnTimKiem_Click(){
          this.loadData();
          //this.lay_dsBienLaiHDKH(this.bienlai_id);
      },
     async onChangeLoaiBL(){
         try {
            this.loading(true)

            let change =  this.lay_dsLuotTao(this.loaibienlai_id);
            await Promise.all([change]);

            this.loading(false)
            } catch (err) {
                this.loading(false);
            this.$toast.error("Load dữ liệu không thành công");
            }


      },
     async onChangeLuotTao(){
         try {
            this.loading(true)

            let change = this.lay_dsBienLai(this.luottao_id);
            await Promise.all([change]);

            this.loading(false)
            } catch (err) {
                this.loading(false);
            this.$toast.error("Load dữ liệu không thành công");
            }

      },
      onChangeLBienLai(){
            if (this.tag == 3)
            {
                this.loadData();
               // this.lay_dsBienLaiHDKH(this.bienlai_id);
            }
      },
      btnGo_Click(){
           if(this.dsGan.checked.length <= 0){
              this.$toast.error("Cần chọn Họp đồng để bỏ gán !");
          }
          else{
                this.$refs.txtMaKH.focus();
                this.dsGan.checked.forEach((obj, index)=>{

                    this.ds_GoGan.push(obj);
                    this.ds.list.push(obj);
                    if(this.ds_Gan.indexOf(obj) >= 0){
                        this.ds_Gan.splice(this.ds_Gan.indexOf(obj), 1);
                    }
                })
                var temps = this.dsGan.list.filter(a => !this.dsGan.checked.includes(a));
                this.dsGan.list = [];
                this.dsGan.list = temps;
                this.dsGan.checked = [];
          }
      },
      btnGan_Click(){

          if(this.ds.checked.length <= 0){
              this.$toast.error("Cần chọn Họp đồng để gán !");
          }
          else{
                this.$refs.txtMaKH.focus();
                this.ds.checked.forEach((obj, index)=>{
                    this.ds_Gan.push(obj);
                    this.dsGan.list.push(obj);

                    if(this.ds_GoGan.indexOf(obj) >= 0){
                        this.ds_GoGan.splice(this.ds_GoGan.indexOf(obj), 1);
                    }
                    console.log(this.dsGan.list);

                })
                var temps = this.ds.list.filter(a => !this.ds.checked.includes(a));
                this.ds.list = [];
                this.ds.list = temps;
                this.ds.checked = [];
          }
      },
    },
  created(){
        this.donviId  = this.$root.token.getDonViID();
        this.nhanvien_id = this.$root.token.getNhanVienID();
        this.phanvung_id = this.$root.token.getPhanVungID();
        if (this.$route.params.tag != null && this.$route.params.tag != '' && this.$route.params.tag.length > 0) {
            this.tag = this.$route.params.tag;
        }
        else if(this.$route.query.tag != null && this.$route.query.tag != '' && this.$route.query.tag.length > 0){
            this.tag = this.$route.query.tag;
        }
        this.xuLyTag(this.tag);
  },
  mounted(){
         this.ds.cols = [
            {fieldName: 'ten_loaihd', headerText: 'Loại HĐ', allowFiltering: true, allowSorting: false, isPrimaryKey:true},
             {fieldName: 'chon', allowEditing:true,   headerText: 'Chọn', visible:false, allowFiltering: true, allowSorting: false},
            {fieldName: 'ma_gd', allowEditing:true,   headerText: 'Mã GD', allowFiltering: true, allowSorting: false},
            {fieldName: 'ma_kh',  headerText: 'Mã KH', allowFiltering: true, allowSorting: false},
            {fieldName: 'ten_kh',   headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: false,},
          {fieldName: 'tongtien', textAlign:'right',  headerText: 'Tổng tiền',customAttributes:{class: 'tongtien'},  allowFiltering: true, allowSorting: false,},
        ];
         this.dsGan.cols = [
            {fieldName: 'ten_loaihd', headerText: 'Loại HĐ', allowFiltering: true, allowSorting: false, isPrimaryKey:true},
             {fieldName: 'chon', allowEditing:true,   headerText: 'Chọn', visible:false, allowFiltering: true, allowSorting: false},
            {fieldName: 'ma_gd', allowEditing:true,   headerText: 'Mã GD', allowFiltering: true, allowSorting: false},
            {fieldName: 'ma_kh',  headerText: 'Mã KH', allowFiltering: true, allowSorting: false},
            {fieldName: 'ten_kh',  headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: false,},
          {fieldName: 'tongtien', textAlign:'right',  headerText: 'Tổng tiền',customAttributes:{class: 'tongtien'},  allowFiltering: true, allowSorting: false,},
        ];



        if (this.tag == 3)
                {
                     this.title_page = "Cập nhật biên lai- HĐ Khách hàng";
                     this.tsbtnGan.Text = "Ghi lại";
                }

        if (this.tag == 1)
                {
                    this.title_page = "Phát hành biên lai";

                }
        this.loadForm();
        this.dtpTuNgay = moment().format("DD/MM/YYYY");
        this.dtpDenNgay = moment().format("DD/MM/YYYY");
  },
}
</script>
