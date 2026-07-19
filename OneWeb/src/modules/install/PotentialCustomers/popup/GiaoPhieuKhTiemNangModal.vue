<template src="./GiaoPhieuKhTiemNangModal.html"></template>
<style type="text/css">
 a:hover {
  cursor:pointer;
 }
</style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from '../api'
import "@/assets/vendor/jquery/split";
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV} from "@/const/enums";
import XoaPhieu from "./XoaPhieuModal.vue";
Vue.use(DialogPlugin);
export default {
    name:'GiaoPhieuKhTiemNangModal',
    components: {
        breadcrumb,LoaiHopDong,DichVuVienThong,TrangThaiHD,LOAI_DV,XoaPhieu
    },
    data: function () {
    return {
        dataDelete :{},
        maxLength_ndgiao:350,
        gridViewNhanVien:{
            loading: false,
            list: [],
            cols: [
                    {
                        headerText: 'Đơn vị',                  
                        fieldName: 'ten_dv',
                        allowFiltering: true,
                        allowSorting: true,        
                    },
                    {
                        headerText: 'Mã nhân viên',                  
                        fieldName: 'ma_nv',
                        allowFiltering: true,
                        allowSorting: true,        
                    },
                    {
                        headerText: 'Tên nhân viên',                  
                        fieldName: 'ten_nv',
                        allowFiltering: true,
                        allowSorting: true,        
                    }, 
                    
                    {
                        headerText: 'Điện thoại',                  
                        fieldName: 'so_dt',
                        allowFiltering: true,
                        allowSorting: true,        
                    }, 
                      
                ] 
        },
        gridDaGiao:{
            loading: false,
            list: [],
            contextMenuItems: [{text: 'Gửi SMS ', target: '.e-content', id: 'sendSms'}],
            cols: [
                    {
                        headerText: 'Mã NV',                  
                        fieldName: 'ma_nv',
                        allowFiltering: true,
                        allowSorting: true,        
                    },
                    {
                        headerText: 'Tên nhân viên',                  
                        fieldName: 'ten_nv',
                        allowFiltering: true,
                        allowSorting: true,        
                    }, 
                    {
                        headerText: 'Ngày giao',                  
                        fieldName: 'ngaygiao',
                        allowFiltering: true,
                        allowSorting: true,        
                    }, 
                    {
                        headerText: 'Điện thoại',                  
                        fieldName: 'so_dt',
                        textAlign:"right",
                        allowFiltering: true,
                        allowSorting: true,        
                    }, 
                    {
                        headerText: 'Nội dung giao',                  
                        fieldName: 'nd_giao',
                        allowFiltering: true,
                        allowSorting: true,        
                    },
                    {                   
                        fieldName: 'remove',
                        headerText: 'Xóa',   
                        template: function () {
                            return {
                                template: Vue.component('XoaTemplate', {
                                template: `<a class="btn btn-thuhoivt lh14" @click="XoaGiDo()"
                                                style="background-color: red !important; padding: 5px 15px; color: white">
                                <span class="icon nc-icon-glyph ui-1_simple-remove"></span></a>`,
                                data () {
                                    return {data: {}}
                                },
                                computed: {
                                    parent () {
                                    return this.$parent.$parent.$parent.$parent.$parent
                                    }
                                },
                                methods: {
                                    XoaGiDo () {
                                        this.parent.XuLyXoa(this.data)
                                    }
                                }
                                })
                            }
                        }
                    },   
                ] 
        },
        txtNDGiao:"",
    }
    },
    props:{
        dataPopup:{
            type: Object,
            default:null
        },
    },
    methods:{
         validateData(){
            let errorMessage = [];
            if (this.txtNDGiao == "" || this.txtNDGiao == null || this.txtNDGiao == undefined || this.txtNDGiao.trim().length == 0) {
                    this.$refs.txtNDGiao.focus();
                    errorMessage.push("Bạn chưa nhập nội dung giao!")
            }
             else{
                if(this.txtNDGiao.length > this.maxLength_ndgiao){
                    this.$refs.txtNDGiao.focus()
                    errorMessage.push("Trường Nội dung giao chỉ được phép nhập " + this.maxLength_ndgiao+ " ký tự ")
                }
            }
            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join('\n'))
                    return false
            }else {
                    return true}
        },
        handleShowModal(){
            this.txtNDGiao = "";
            this.gridDaGiao.list = [];
            this.gridViewNhanVien.list = [];
            this.LayDS_NhanVien();
            this.Lay_DS_NhanVien_DaGiao();
        },
        showModal() {
            this.$refs["popupGiaoPhieuKhTiemNang"].show()
        },
        hideModal() {
            console.log("Đóng modal");
            this.$refs["popupGiaoPhieuKhTiemNang"].hide();
            this.$emit("closeModal");
        },
        XuLyXoa(item) {
            //alert('chuan bi xoa', item)
            if(item != ""){
                this.dataDelete = item;
                this.$refs.popupXoaPhieu.showModal();
            }
        },
        
        contextMenuClick (arg) {
            console.log(arg)
            if(arg.item.id === 'sendSms') {
               this.sendSms(arg.rowInfo.rowData.ghichu,arg.rowInfo.rowData.so_dt);
            }
            else{
                this.$toast.error("Không có thông tin gửi SMS!");
            }
        },
        acceptModal(val){
           this.tuvan_xoaphieu_giaoviec_khtn(val.hdkh_id, val.phieu_id, val.hdkh_cha_id);
        },
        async giaophieu_khtn_nhom1(pphieu_id,vhdkh_id,vdonvi_id,vghichu,vnguoi_cn,vnhanvien_id){
            let res = await api.giaophieu_khtn_nhom1(this.axios,
                        {
                                "vhdkh_id": vhdkh_id,
                                "vloai": 1,
                                "vdonvi_id": vdonvi_id,
                                "vghichu": vghichu,
                                "pphieu_id": pphieu_id,
                                "vnguoi_cn": vnguoi_cn,
                                "vnhanvien_id": vnhanvien_id,
                        });
                    try{
                        if(res.data.error_code === "BSS-00000000" && res.data.data != ""){     
                            this.$toast.success("Giao phiếu thành công!");       
                            this.Lay_DS_NhanVien_DaGiao();
                        }
                        else{
                            this.$toast.error("Giao phiếu thất bại!");
                        }
                        
                    }
                    catch(e){
                        this.$toast.error("Giao phiếu thất bại!");
                    }  

                       
        },
        tsbtnChapNhan_Click(){
            if(this.validateData()){
                if(this.gridDaGiao.list.length > 0){
                    this.$toast.error("Bạn chỉ có thể giao cho 1 nhân viên!");
                    return;
                }
                if(this.$refs.gridViewNhanVien.getSelectedRecords().length > 0){
                    var row = this.$refs.gridViewNhanVien.getSelectedRecords()[0];
                    this.giaophieu_khtn_nhom1(this.dataPopup.phieu_id, this.dataPopup.hdkh_id, 
                    row.donvi_id,this.txtNDGiao,this.dataPopup.nhanvien_id,row.nhanvien_th_id);
                   
                 }
                 else{
                            this.$toast.error("Bạn chưa chọn nhân viên để giao!");
                }
            }
        },
        async LayDS_NhanVien(){
             this.gridViewNhanVien.loading = true;
            let res = await api.lay_ds_nv_theo_phieu_id(this.axios,
                {
                    "vphieu_id": this.dataPopup.phieu_id,
                    "vnhanvien_id": this.dataPopup.nhanvien_id,
                    "vkieu_id": 1
                });
            try{
                if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
               
                this.gridViewNhanVien.list = res.data.data;
                this.gridViewNhanVien.loading = false;
                }
                else{
                    this.gridViewNhanVien.loading = false;
                }
            }
            catch(e){
                this.gridViewNhanVien.loading = false;
            }         
        },
        async Lay_DS_NhanVien_DaGiao(){
            this.gridDaGiao.loading = true;
            let res = [];
            try{
                if(this.dataPopup.tag == 0){
                    res = await api.lay_ds_nv_theo_phieu_id(this.axios,
                    {
                        "vphieu_id": this.dataPopup.phieu_id,
                        "vnhanvien_id": this.dataPopup.nhanvien_id,
                        "vkieu_id": 5,
                    });
                }
                else{
                    res = await api.tuvan_ds_nvtc_giaoviec(this.axios,
                    {
                        "vphieu_id": this.dataPopup.phieu_id,
                        "vnhanvien_id": this.dataPopup.nhanvien_id,
                        "vkieu_id": 2,
                    });
                }         
           
                if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
                
                this.gridDaGiao.list = res.data.data;
                this.gridDaGiao.loading = false;
                }
                else{
                    
                    this.gridDaGiao.list = [];
                    this.gridDaGiao.loading = false;
                }
            }
            catch(e){
                this.gridDaGiao.list = [];
                this.gridDaGiao.loading = false;
            }         
        },
        async sendSms(noidung, sdt){
            let response = await api.sendsms(this.axios,{
                "msisdn": sdt,
                "content" : noidung,
            })
            if(response.data.errorCode == 0){
                this.$toast.success(response.data.faultString);
            }
            else{
                this.$toast.error("Gửi tin nhắn thất bại");
            }
            
        },  
        async tuvan_xoaphieu_giaoviec_khtn(vhdkh_id, vphieu_id, vhdkh_cha_id){
            let response = await api.tuvan_xoaphieu_giaoviec_khtn(this.axios,{
                "vhdkh_id": vhdkh_id,
                "vphieu_id": vphieu_id,
                "vhdkh_cha_id": vhdkh_cha_id
            })
            if(response.data.error_code === "BSS-00000000"){
                this.$toast.success("Xóa thành công");
                this.Lay_DS_NhanVien_DaGiao();
            }
            else{
                this.$toast.error("Xóa thất bại");
                
                return;
            }
            
        }
    },
    mounted(){
    }
}
</script>