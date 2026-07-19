<template src="./PotentialCustomers.html"></template>
<style type="text/css">
a:hover {
  cursor:pointer;
}
</style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from './api'
import "@/assets/vendor/jquery/split";
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV} from "@/const/enums";
import GiaoPhieuKhTiemNang from "./popup/GiaoPhieuKhTiemNangModal.vue";

Vue.use(DialogPlugin);
export default {
  components: {
    breadcrumb,LoaiHopDong,
    DichVuVienThong,TrangThaiHD,
    LOAI_DV,GiaoPhieuKhTiemNang,
  },
  data: function () {
    return {
      vnhanvien_id:-1, vnhomnn_id:-1,vphieu_id:-1,
      vhdkh_id:-1, txtMaGD:"",txtSoDT:"",txtTenKH:"",txtDiaChi:"",txtGhiChu:"",
      nhanvien_id:-1,donviId:-1,tag:-1,nhomton_id:-1,lydotontc_id:-1,
      cboNhomTon:[],cboNguyenNhan:[],maxLength_ghichu:400,
      tableDsKH:{
        loading: false,
        list: [],
        cols: [
          {
            headerText: 'Mã GD',
            fieldName: 'ma_gd',
            allowFiltering: true,
            allowSorting: true,
          },
          {
            headerText: 'Tên TB',
            fieldName: 'ten_tb',
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
            headerText: 'Số ĐT',
            fieldName: 'so_dt',
            allowFiltering: true,
            textAlign:"right",
            allowSorting: true,
          },
          {
            headerText: 'Loại nhu cầu',
            //fieldName: '',
            allowFiltering: true,
            allowSorting: true,
          },
          {
            headerText: 'Kiểu YC',
            //fieldName: '',
            allowFiltering: true,
            allowSorting: true,
          },
          {
            headerText: 'Loại YC',
            //fieldName: '',
            allowFiltering: true,
            allowSorting: true,
          },
          {
            headerText: 'Trạng thái',
            fieldName: 'trangthai',
            allowFiltering: true,
            allowSorting: true,
          },
        ]
      },
      dataPopup:{
        tag:0,
        phieu_id:0,
        hdkh_id:0,
        nhanvien_id:0,
      },

    }
  },
  methods:{
    async cboNhomNN_ValueChanged(){
      await this.LayDS_NguyenNhan();
    },
    validateData(){
      let errorMessage = [];
      if (this.txtGhiChu == "" || this.txtGhiChu == null || this.txtGhiChu == undefined || this.txtGhiChu.trim().length == 0) {
        this.$refs.txtGhiChu.focus()
        errorMessage.push("Trường Ghi chú không được trống.")
      }
      else{
        if(this.txtGhiChu.length > this.maxLength_ghichu){
          this.$refs.txtGhiChu.focus()
          errorMessage.push("Trường Ghi chú chỉ đươc phép nhập " +this.maxLength_ghichu+ " ký tự ")
        }
      }
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return false
      }else {
        return true}
    },
    async loadForm(){
        await this.LayDS_KhachHangTiemNang();
        await this.LayDS_NhomNguyenNhan();

    },
    async loadGrid(){
      try{
        this.loading(true);

        await this.LayDS_KhachHangTiemNang();
        //await Promise.all([LayDS_KhachHangTiemNang]);

        this.loading(false)
      }
      catch(e){
        this.$toast.error(e.toString());
        this.loading(false);
      }
    },
    async LayDS_KhachHangTiemNang(){
      try{
        this.loading(true);
        let res = await api.tuvan_ds_kh_tiemnang_nhom1(this.axios,{
          "vnhanvien_id": this.nhanvien_id,
        });
        if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
          this.tableDsKH.list = res.data.data;
        }
        else{
          this.tableDsKH.list = [];
        }
        this.loading(false);
      }
      catch (e) {
        this.loading(false);
      }
    },
    async LayDS_NhomNguyenNhan(){
      try{
        this.loading(true);
        let res = await api.css_nhom_ton(this.axios,{});
        if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
          this.cboNhomTon = res.data.data;
          this.nhomton_id = this.cboNhomTon[0].NHOMTON_ID;

        }
        else{
          this.cboNhomTon = [];
          this.nhomton_id = -1;
        }
        this.loading(false);
      }
      catch (e) {
        this.loading(false);
      }

    },
    async LayDS_NguyenNhan(){
      try{
        this.loading(true);
        let res = await api.css_lydoton_tc(this.axios,{});
        if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
          let dsNguyenNhan = [];
          res.data.data.forEach(obj=>{
            if(obj.NHOMTON_ID == this.nhomton_id){
              dsNguyenNhan.push(obj);
            }
          })
          if(dsNguyenNhan.length > 0){
            this.cboNguyenNhan = dsNguyenNhan;
            this.lydotontc_id = this.cboNguyenNhan[0].LYDOTONTC_ID;
          }
          else {
            this.cboNguyenNhan = [];
            this.lydotontc_id = -1;
          }
        }
        else{
          this.cboNguyenNhan = [];
          this.lydotontc_id = -1;
        }
        this.loading(false);
      }
      catch (e) {
        this.loading(false);
      }


    },
    grvKhachHangTiemNangNhom1_FocusedRowChanged(data){
      this.txtMaGD =data[0].ma_gd;
      this.txtSoDT = data[0].so_dt;
      this.txtTenKH = data[0].ten_kh;
      this.txtDiaChi = data[0].diachi_kh;
    },
    async tuvan_khoaphieu_khtn(vhdkh_id,vghichu,nhanvien_id,vphieu_id,vlydotontc_id){
      try{
        let res = await api.tuvan_khoaphieu_khtn(this.axios,
          {
            "vhdkh_id": vhdkh_id,
            "vdonvi_id": this.donviId,
            "vghichu": vghichu,
            "vphieu_id": vphieu_id,
            "vnhanvien_id": nhanvien_id,
            "vlydotontc_id": vlydotontc_id,
          });
        if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
          this.$toast.success("Khóa phiếu thành công");
          this.loadGrid();
        }
        else{
          this.$toast.error("Khóa phiếu thất bại");

        }
      }
      catch(e){
        this.$toast.error("Khóa phiếu thất bại");
      }

    },
    tsbtnLayTT_Click(){
      this.loadGrid();
    },
    tsbtnKhoaPhieu_Click(){
      if(this.validateData()){
        if(this.lydotontc_id == "" || this.lydotontc_id == -1){
          this.$toast.error("Bạn chưa chọn nguyên nhân khóa phiếu!");
          return;
        }
        this.$bvModal.msgBoxConfirm('Bạn có muốn khóa phiếu này không?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy',
        }).then(async v => {
          if(!v) {
            return;
          }
          var row = this.$refs.tableDsKH.getSelectedRecords()[0];
          try{
            this.tuvan_khoaphieu_khtn(row.hdkh_id,this.txtGhiChu.trim(),row.nhanvien_id,row.phieu_id,this.lydotontc_id);
          }
          catch(e){
            this.$toast.error("Khóa phiếu thất bại");
            return;
          }
        });

      }
    },
    tsbtnGiaoPhieu_Click(){
      if(this.$refs.tableDsKH.getSelectedRecords().length > 0){
        var row = this.$refs.tableDsKH.getSelectedRecords()[0];
        this.dataPopup.tag = this.tag;
        this.dataPopup.phieu_id = row.phieu_id;
        this.dataPopup.hdkh_id = row.hdkh_id;
        this.dataPopup.nhanvien_id = row.nhanvien_id;

        this.$refs.popupGiaoPhieuKhTiemNang.showModal();
      }
      else{
        this.$toast.error("Vui lòng chọn thông tin khách hàng!");
      }

    },
    closeModal(){
      this.loadGrid();
    },


  },
  created(){
    this.donviId  = this.$root.token.getDonViID();
    this.nhanvien_id = this.$root.token.getNhanVienID();
    if (this.$route.params.tag != null && this.$route.params.tag != '' && this.$route.params.tag.length > 0) {
      this.tag = this.$route.params.tag;
    }
    else if(this.$route.query.tag != null && this.$route.query.tag != '' && this.$route.query.tag.length > 0){
      this.tag = this.$route.query.tag;
    }
  },
  async mounted(){
    try{
      this.loading(true);
      await this.loadForm();
      this.loading(false);
    }
    catch(e){
      this.$toast.error(e.toString());
      this.loading(false);
    }

  }
}
</script>
