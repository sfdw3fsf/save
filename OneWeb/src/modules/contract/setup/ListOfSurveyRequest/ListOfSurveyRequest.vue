<template src="./ListOfSurveyRequest.html"></template>
<style scoped src="./ListOfSurveyRequest.scss"></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import "@/assets/vendor/jquery/split";
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import '@/assets/vendor/jquery/split';
import {LoaiHopDong} from './ThamSo';
import ThongTinHenKhachHang from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue";
Vue.use(DialogPlugin);
export default {
  components: {
    breadcrumb, LoaiHopDong,ThongTinHenKhachHang
  },
  data: function () {
    return {
      header: {
        title: "",
      },
      list: [
        { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
        {
          name: "Lắp đặt mới",
          link: { name: "Ui.buttons" },
        },
      ],
      datetimeconfig: {
        altFormat: "d/m/Y",
        altInput: true,
        dateFormat: "d/m/Y",
        allowInput: true,
        enableTime: true,
      },
      chkLoaiHD: true,
      chkKieuYC: false,
      isTuNgay: false,
      chkKQKS: false,
      chkDichVuVT: false,
      chkLHTB: false,

      loaiHD: "-1",
      loaiHD_options: null,
      kieuLD: "-1",
      kieuLD_options: null,
      tungay: new Date(),
      denngay: new Date(),
      ketQuaKS: null,
      ketQuaKS_options: null,
      dichVu: "-1",
      dichVu_options: null,
      loaiHinh: "-1",
      loaiHinh_options: null,
      vketqua: 0,
      vdieukien: "",
      ds: [],
      dgvDsPhieu: [],
      vdonvi_id: 0,
      loai: 1,
      hdkh_id: 0,
      vloaihd_id: null,
      txtMaGD: "",
      txtDiaChiLD: "",
      txtSoDT: "",
      txtTenTB: "",
      txtGhichu: "",
      button:{
        tsbHuy: "0",
      },
      selectionSettings: {checkboxMode: 'ResetOnRowClick'}
    };
  },
  computed: {
    chkNgay: {
        get() {
            return this.isTuNgay;
        },
        set(value) {
            this.isTuNgay = value;
        },
      },
  },
  watch: {
    chkLoaiHD: function (bool) {
      if (bool === false) {
        this.loaiHD = "-1";
      } else {
        this.HT_Loai_HD_Combobox();
      }
    },
    chkKieuYC: function (bool) {
      if (bool === false) {
        this.kieuLD = "-1";
      } else {
        this.HT_Kieu_LD_Combobox(this.loaiHD, this.loaiHinh);
      }
    },
    chkKQKS: function (bool) {
      if (bool === false) {
        this.ketQuaKS="";
      } else {
       this.loadKQKS();
      }
    },
    chkDichVuVT: function (bool) {
      if (bool === false) {
        this.loaiHinh = "";
        this.dichVu="-1";
         this.chkLHTB = false;
      } else {
        this.HT_DichVuVT_KhaoSat_Combobox();
        this.chkLHTB = true;
      }
    },
    chkLHTB: function (bool) {
      if (bool === false) {
        this.loaiHinh = "-1";
      } else {
        this.HT_LoaiHinh_TB_TocDo_Combobox(this.dichVu);
      }
    },
  },
  created() {
    this.vdonvi_id = this.$root.token.getDonViID();
    this.LAY_DS_THAMSO_MD_MATS();
    this.HT_Loai_HD_Combobox();
  },
  mounted(){
    Split(['#boxLeft', '#boxRight'], {
            sizes: [50, 50],
            gutterSize: 16,
            onDragEnd: function (sizes) {
            },
        })

  },
  methods: {
    HT_Loai_HD_Combobox() {
      API.CSS_LOAI_HD(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.LOAIHD_ID, text: item.TEN_LOAIHD });
              });
              this.loaiHD_options = items.filter(x =>x.id == 33 || x.id == 34 || x.id == 35 );
              this.loaiHD = 33;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    HT_Kieu_LD_Combobox(loaihd, loaitb) {
      API.lay_ds_kieu_ld(this.axios, {
        vloaihd_id: loaihd,
        vloaitb_id: loaitb,
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.kieuld_id, text: item.ten_kieuld });
              });
              this.kieuLD_options = items;
              this.kieuLD = items[0].id;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    HT_DichVuVT_KhaoSat_Combobox() {
      API.sp_lay_dichvu_vt_theo_nd(this.axios, {})
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.dichvuvt_id, text: item.ten_dvvt });
              });
              this.dichVu_options = items;
              this.dichVu = items[0].id;
              this.HT_LoaiHinh_TB_TocDo_Combobox(this.dichVu);
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    HT_LoaiHinh_TB_TocDo_Combobox(dichvuvt) {
      API.lay_ds_lhtb_theo_dv(this.axios, {
        "vdichvuvt_id": dichvuvt
      })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.loaitb_id, text: item.loaihinh_tb });
              });
              this.loaiHinh_options = items;
              this.loaiHinh = items[0].id;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    loadKQKS() {
      let items = [];
      items = [
        { id: 0, text: "Không đủ điều kiện thi công" },
        { id: 1, text: "Đủ điều kiện thi công" },
        { id: 2, text: "Đang khảo sát" },
      ];
      this.ketQuaKS_options = items;
      this.ketQuaKS = 0;
    },
    async HienThiDanhSach(flag){
      this.vdieukien = "";
      let loaiHD = this.loaiHD;
      if(flag == 0){
        if(this.chkKQKS){
          this.vdieukien = this.vdieukien + " " + " and nvl(a.ketquaks,2) =" + this.ketQuaKS; // neu ketqua null thi để là đang khảo sát
          if (this.ketQuaKS == 2){
            this.vdieukien += " and a.tthd_id=4 and  a.ketquaks is null";//nhapt
          }   
        }
        if(this.chkKieuYC){
          this.vdieukien = this.vdieukien + " " + " and a.kieuld_id=" + this.kieuLD
        }
        if(this.chkLoaiHD){
          this.vdieukien = this.vdieukien + " " + " and c.loaihd_id = " + this.loaiHD;
        }
        if(this.chkDichVuVT){
           this.vdieukien =  this.vdieukien + " " + " and a.dichvuvt_id = " + this.dichVu
        }
        if(this.chkLHTB){
          this.vdieukien = this.vdieukien + " " + " and a.loaitb_id =" + this.loaiHinh
        }
        this.loading(true);
        await API.sp_lay_ds_hopdong_yc_khaosat(this.axios,{
            "ma_gd": 0,
            "donvi_id": this.vdonvi_id,
            "dieukien": this.vdieukien,
            "tungay": this.chkNgay ? moment(this.tungay).format("DD/MM/YYYY"):"",
            "denngay":  this.chkNgay ? moment(this.denngay).format("DD/MM/YYYY"):"",
        }
        ).then((response) => {
          if (response.data.error_code === "BSS-00000000" && response.data.data !== undefined && response.data.data.length > 0){
            this.ds = response.data.data;
          }
        }).catch((error) => {
          console.log(error);
        })
        .finally(() => {this.loading(false)});
      }else{
        this.loading(true);
        await API.sp_lay_ds_hopdong_yc_khaosat(this.axios,{
            "ma_gd": this.txtMaGD.replace(/\s/g, ''),
            "donvi_id": this.vdonvi_id,
            "dieukien": "",
            "tungay":"10/02/2022",
            "denngay":"10/02/2022",
        }
        ).then((response) => {
          if (response.data.error_code === "BSS-00000000" && response.data.data !== undefined && response.data.data.length > 0){
            this.ds = response.data.data;
          }
        }).catch((error) => {
          console.log(error);
        })
        .finally(() => {this.loading(false)});
      }
      if(this.ds.length>0){
        let items=[];
        this.ds.forEach(function (item) {
          if(item.ketqua == 0){
            items.push({ 
              KETQUA: loaiHD != LoaiHopDong.TIEPNHANHD ? "Không đủ điều kiện thi công" : "Không ký được hợp đồng",
              ngay_yc: item.ngay_yc,
              ten_dvvt: item.ten_dvvt,
              loaihinh_tb: item.loaihinh_tb,
              ma_gd: item.ma_gd,
              ten_kieuld: item.ten_kieuld,
              ten_tb: item.ten_tb,
              so_dt: item.so_dt,
              diachi_ld: item.diachi_ld,
              ten_nv: item.ten_nv,
              soluong: item.soluong,
              ghichu: item.ghichu,
              hdkh_id: item.hdkh_id
            });
          }else if(item.ketqua == 1){
            items.push({ 
              KETQUA: loaiHD != LoaiHopDong.TIEPNHANHD ? "Đủ điều kiện thi công" : "Đã ký hợp đồng",
              ngay_yc: item.ngay_yc,
              ten_dvvt: item.ten_dvvt,
              loaihinh_tb: item.loaihinh_tb,
              ma_gd: item.ma_gd,
              ten_kieuld: item.ten_kieuld,
              ten_tb: item.ten_tb,
              so_dt: item.so_dt,
              diachi_ld: item.diachi_ld,
              ten_nv: item.ten_nv,
              soluong: item.soluong,
              ghichu: item.ghichu,
              hdkh_id: item.hdkh_id
            });
          }else{
            items.push({
              KETQUA: loaiHD != LoaiHopDong.TIEPNHANHD ? "Đang khảo sát" : "Đang liên hệ khách hàng",
              ngay_yc: item.ngay_yc,
              ten_dvvt: item.ten_dvvt,
              loaihinh_tb: item.loaihinh_tb,
              ma_gd: item.ma_gd,
              ten_kieuld: item.ten_kieuld,
              ten_tb: item.ten_tb,
              so_dt: item.so_dt,
              diachi_ld: item.diachi_ld,
              ten_nv: item.ten_nv,
              soluong: item.soluong,
              ghichu: item.ghichu,
              hdkh_id: item.hdkh_id
            });
          }
        });
        this.dgvDsPhieu = items;
      }
    },
    LAY_DS_THAMSO_MD_MATS() {
      API.fn_lay_ds_thamso_md_mats(this.axios,{ "ma_ts": "NUT_HUY_KHAOSAT"})
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.button.tsbHuy = response.data.data;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
    Clear(flag){
      this.txtDiaChiLD = "";
      if(flag == 0) this.txtMaGD = "";
      this.txtSoDT = "";
      this.txtTenTB = "";
      this.txtGhichu = "";
    },
    onExcelExport: function () {
      if (this.dgvDsPhieu.length <= 0)
        this.$toast.error('Chưa có dữ liệu trên lưới để xuất excel!');
      else {
         let excelExportProperties = {
           fileName:"DanhSachPhieuYeuCauKhaoSat.xlsx",
           dataSource: this.dgvDsPhieu
         };
         this.$refs.grid.excelExport(excelExportProperties);   
      }    
    },
    cboLoaiHD_SelectedValueChanged(args){
      this.HT_Kieu_LD_Combobox(args.id, this.loaiHinh);
    },
    cboDichVuVT_SelectedValueChanged(args){
      this.HT_LoaiHinh_TB_TocDo_Combobox(args.id);
    },
    cboLoaihinhTB_SelectedValueChanged(args){
      this.HT_Kieu_LD_Combobox(this.loaiHD, args.id);
    },
    cboKQKS_SelectedIndexChanged(args){
     if(args.id == 2 ){
       this.button.tsbHuy =1;
     }else{
       this.button.tsbHuy = 0;
     }
    },
    dgvThueBao_FocusedRowChanged(data){
      this.txtDiaChiLD = data.diachi_ld;
      this.txtMaGD = data.ma_gd;
      this.txtSoDT = data.so_dt;
      this.txtTenTB = data.ten_tb;
      this.txtGhichu = data.ghichu;
      this.hdkh_id = data.hdkh_id;
    },
    tsbnLayThongTin_Click(){
      this.Clear(0);
      this.HienThiDanhSach(0);
    },
    tsbtnHenDC_Click(){
      this.$refs.popupThongTinHenKhachHang.showModal();  
    },
    //BA yêu cầu bỏ nút hủy https://cntt.vnpt.vn/browse/BSS-71503
    /* tsbHuy_Click(){
      API.sp_capnhat_huyphieu_tn(this.axios,{ "hdkh_id": this.hdkh_id})
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" && response.data.data !== undefined
          ) {
            this.$toast.success("Hủy giao phiếu thành công!");
            this.HienThiDanhSach(0);
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    }, */
    tsbtnExcel_Click(){
      this.onExcelExport();
    },
    txtMaGD_KeyPress(){
      if(this.txtMaGD.trim() != ""){
        this.Clear(1);
        this.HienThiDanhSach(1);
      }else{
        this.Clear(0);
        this.HienThiDanhSach(0);
      }
    },
  },
};
</script>