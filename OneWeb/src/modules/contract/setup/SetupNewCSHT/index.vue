<template src="./index.html"></template>
<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import api from "./api/index.js";
import state from "./define.js";
import ModalChonDiaChi from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi";
import ModalTracnghiemExt from '@/modules/CABMAN/BanDoMangCap/modal/TracNghiemExt'
import ThayDoiKhuVucQuanLyThueBaoModal from '@/modules/contract/setup/SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import { required, minValue, maxValue, maxLength,minLength,numeric } from 'vuelidate/lib/validators'
import {
  DichVuVienThong,  
  LOAI_DV,  
  LOAI_KV,
  LoaiHinhTB,
  TrangThaiHD,
  TRANGTHAI_PORT,
  TRANGTHAI_DONGBO,
  LoaiHopDong,
} from "./Enums";
export default {
  components: { 
    moment,
    breadcrumb, 
    gridview,    
    ModalChonDiaChi,
    ModalTracnghiemExt,    
    ThayDoiKhuVucQuanLyThueBaoModal
  },
  name: "",
  validations: {
        formViewTTKH: {
          so_dt:{
            required,
            minLength: minLength(10),
            numeric,
          },
          so_fax:{            
            minLength: minLength(10),
            numeric,
          }
      }    
  },
  data() {
    return {
      header: {
        title: "LẬP HỢP ĐỒNG THUÊ CƠ SỞ HẠ TẦNG",
        list: [
          { name: "Hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới CSHT",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      ThHdId: 1,
      initLoaiHdArr: [],
      initDVVTArr: [],
      Tag:"",
      ...state
      
    };
  },
  created() {
    // dat gia trị trong ham khoi tao cua component
   // Lay gia trị tag truyen tu url   
  //   if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
  //     this.Tag = this.$route.query.tag
  //   }
  //   // Gan vao theo tham so cua form
  //  //  this.Tag = '4+5+8;9'
  //   if (this.Tag != null && this.Tag.length > 0) {
  //     let arr = this.Tag.split('+')
  //     if (arr.length >= 1) this.ThHdId = parseInt(arr[0])
  //     if (arr.length >= 2) this.initLoaiHdArr = arr[1].split(';')
  //     if (arr.length >= 3) this.initDVVTArr = arr[2].split(';')
  //   }
    this.init()
    this.HIEN_DANHSACH_HD_CHUA_GIAO()

  },
  mounted() {
    
  },
  computed: {},
  watch: { 
    'formTTHD.dichvuvt_id': async function (newValue, oldValue) {      
      this.changeDVVT(newValue)
    },
    'formTTHD.loaitb_id': async function (newValue, oldValue) {      
      this.changeLoaiHinh(newValue)
    },
    'formViewTTKH.ten_kh': async function (newValue, oldValue) {      
      this.formViewTTKH.ten_gd = this.formViewTTKH.ten_kh
    },
  },
  methods: {
    async init(){
      try {        
        
        await api.getDichVu(this.axios).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            this.options.cbo_dichvu = response.data.data;    
            this.formTTHD.dichvuvt_id =19

          }
        });

        await api.sp_lay_ds_to_ql(this.axios).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            this.options.cbo_donvinhan = response.data.data;    
            this.formGiaoPhieu.donvinhan_id =response.data.data[0].donvi_id 

          }
        });
        await api.post_lay_danhmuc_lapmoi_tt_chung_v8(this.axios, {
          vchuoi:
            "LOAI_CSHT|LOAI_GT",
        }).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {          
                  
            this.options.cbo_loaigt = response.data.data["loai_gt"];
            this.options.cbo_loaicsht = response.data.data["loai_csht"];  
            this.formTTHD.loaigt_id = response.data.data["loai_gt"][0].loaigt_id
            this.formTTHD.loai_csht_id=response.data.data["loai_csht"][0].loai_csht_id
          }
        });
        this.SetButton(1)
        this.may_cn = await this.$root.token.getMachine()
        this.ip_cn = await this.$root.token.getIP()
        console.log(this.may_cn)
      } catch (error) {
        console.log(error)        
      }
    },
    setActiveButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }      
    },
    setVisibleButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].visible = status
      }      
    },
    SetButton(kieu){
      this.setActiveButton('nhapmoi',false)
      this.setActiveButton('ghilai',false)
      this.setActiveButton('xoahd',false)
      this.giaophieu_active=false      
      if (kieu == -1)//Bat dau
      {          
          this.setActiveButton('ghilai',true)
      }
      else if (kieu == 0)//Bat dau
      {
          this.is_enable_nhapmoi = true
          this.setActiveButton('nhapmoi',true)
          $('#ma_hd').focus()          
          this.ClearGUI()
      }
      else if (kieu == 1)//Them moi
      {
          $('#ma_hd').focus() 
          this.setActiveButton('ghilai',true)
          this.ClearGUI()
      }
      else if (kieu == 2)//Huy
      {
          this.is_enable_nhapmoi = true
          this.setActiveButton('nhapmoi',true)
          this.ClearGUI()
      }
      else if (kieu == 3)//Edit
      {
          this.is_enable_nhapmoi = true
          this.setActiveButton('nhapmoi',true)
          this.setActiveButton('ghilai',true)
          this.setActiveButton('xoahd',true)
          this.giaophieu_active=true
      }
      
    },
    soHDKeyPress(){

    },
    selectItemThueBao(item){
      console.log(item.data)
      //return;
      if (!item || !item.data) return
      this.SetButton(3)
      

      let formdata = item.data
      this.isReadonlyFields = false
      if (formdata.khachhang_id && formdata.khachhang_id>0) {
        //set readonly các trường
        this.isReadonlyFields = true
      }
      this.formTTHD.ma_hd = formdata.ma_hd
      this.formTTHD.dichvuvt_id = formdata.dichvuvt_id
      this.formTTHD.ma_gd = formdata.ma_gd
      this.formTTHD.loaitb_id = formdata.loaitb_id
      this.formTTHD.ngaylap_hd = moment(formdata.ngay_yc,'yyyy-MM-dd HH:mm:ss').format('dd-MM-yyyy HH:mm:ss')
      this.formTTHD.nguoilap_hd = formdata.nguoi_laphd
      this.formTTHD.kieuld_id = formdata.kieuld_id
      this.formTTHD.ma_csht = formdata.ma_tb
      this.formTTHD.ma_tb = formdata.ma_tb
      this.formTTHD.loai_csht_id = formdata.loai_csht_id
      this.formTTHD.ghichu = formdata.ghichu
      this.formTTHD.hdkh_id = formdata.hdkh_id
      this.formTTHD.hdtt_id = formdata.hdtt_id
      this.formTTHD.hdtb_id = formdata.hdtb_id
      this.formTTHD.khachhang_id = formdata.khachhang_id

      this.formViewTTKH.khachhang_id = formdata.khachhang_id
      this.formViewTTKH.ma_kh = formdata.ma_kh
      this.formViewTTKH.ten_kh = formdata.ten_kh            
      this.formViewTTKH.nguoi_dd = formdata.nguoi_dd
      this.formViewTTKH.chucdanh_dd = formdata.chucdanh_dd   
      this.formViewTTKH.so_dt = formdata.so_dt
      this.formViewTTKH.so_fax = formdata.so_fax
      this.formViewTTKH.stk = formdata.stk
      this.formViewTTKH.mst = formdata.mst
      this.formViewTTKH.loaigt_id = formdata.loaigt_id
      this.formViewTTKH.so_gt = formdata.so_gt
      this.formViewTTKH.ngaycap =  moment(formdata.ngaycap,'yyyy-MM-DD HH:mm:ss').format('DD-MM-yyyy')
      this.formViewTTKH.noicap =  formdata.noicap

      this.formGiaoPhieu.donvinhan_id = formdata.donvi_nhan_id
      this.CurrentDiaBan.ten_kv = formdata.ten_kv

      this.formViewTTKH.diachi_kh.diachi_id = formdata.diachi_kh_id
      this.formViewTTKH.diachi_kh.diachi = formdata.diachi_kh
      this.formViewTTKH.diachi_kh.tinh_id = formdata.tinh_kh_id
      this.formViewTTKH.diachi_kh.quan_id = formdata.quan_kh_id
      this.formViewTTKH.diachi_kh.phuong_id = formdata.phuong_kh_id 
      this.formViewTTKH.diachi_kh.pho_id = formdata.pho_kh_id 
      this.formViewTTKH.diachi_kh.khu_id = formdata.khu_kh_id
      this.formViewTTKH.diachi_kh.so_nha = formdata.sonha_kh 
      this.formViewTTKH.diachi_kh.dac_diem_id = formdata.dacdiem_kh_id
      this.formViewTTKH.diachi_kh.sonha = formdata.sonha_kh
      this.formViewTTKH.diachi_kh.dacdiem_id = formdata.dacdiem_kh_id

      //todo địa chỉ thanh toán, thuê bao, lắp đặt
      this.formViewTTKH.diachi_tt.diachi_id = formdata.diachi_tt_id
      this.formViewTTKH.diachi_tt.diachi = formdata.diachi_tt
      this.formViewTTKH.diachi_tt.tinh_id = formdata.tinh_tt_id
      this.formViewTTKH.diachi_tt.quan_id = formdata.quan_tt_id
      this.formViewTTKH.diachi_tt.phuong_id = formdata.phuong_tt_id 
      this.formViewTTKH.diachi_tt.pho_id = formdata.pho_tt_id 
      this.formViewTTKH.diachi_tt.khu_id = formdata.khu_tt_id
      this.formViewTTKH.diachi_tt.so_nha = formdata.sonha_tt 
      this.formViewTTKH.diachi_tt.dac_diem_id = formdata.dacdiem_tt_id
      this.formViewTTKH.diachi_tt.sonha = formdata.sonha_tt
      this.formViewTTKH.diachi_tt.dacdiem_id = formdata.dacdiem_tt_id

      this.formViewTTKH.diachi_tb.diachi_id = formdata.diachi_tb_id
      this.formViewTTKH.diachi_tb.diachi = formdata.diachi_tb
      this.formViewTTKH.diachi_tb.tinh_id = formdata.tinh_tb_id
      this.formViewTTKH.diachi_tb.quan_id = formdata.quan_tb_id
      this.formViewTTKH.diachi_tb.phuong_id = formdata.phuong_tb_id 
      this.formViewTTKH.diachi_tb.pho_id = formdata.pho_tb_id 
      this.formViewTTKH.diachi_tb.khu_id = formdata.khu_tb_id
      this.formViewTTKH.diachi_tb.so_nha = formdata.sonha_tb 
      this.formViewTTKH.diachi_tb.dac_diem_id = formdata.dacdiem_tb_id
      this.formViewTTKH.diachi_tb.sonha = formdata.sonha_tb
      this.formViewTTKH.diachi_tb.dacdiem_id = formdata.dacdiem_tb_id

      this.formViewTTKH.diachi_ld.diachi_id = formdata.diachi_ld_id
      this.formViewTTKH.diachi_ld.diachi = formdata.diachi_ld
      this.formViewTTKH.diachi_ld.tinh_id = formdata.tinh_ld_id
      this.formViewTTKH.diachi_ld.quan_id = formdata.quan_ld_id
      this.formViewTTKH.diachi_ld.phuong_id = formdata.phuong_ld_id 
      this.formViewTTKH.diachi_ld.pho_id = formdata.pho_ld_id 
      this.formViewTTKH.diachi_ld.khu_id = formdata.khu_ld_id
      this.formViewTTKH.diachi_ld.so_nha = formdata.sonha_ld 
      this.formViewTTKH.diachi_ld.dac_diem_id = formdata.dacdiem_ld_id
      this.formViewTTKH.diachi_ld.sonha = formdata.sonha_ld
      this.formViewTTKH.diachi_ld.dacdiem_id = formdata.dacdiem_ld_id

      //todo khóa input readonly

    },
    giaophieu(){

    },
    async changeDVVT(dvvt_id) {
      try {        
        //load loaihinhtb
        await api.getLoaiHinh(this.axios, { dvvt_id }).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {
            console.log(response.data.data)
            this.options.cbo_loaihinh = response.data.data;     
            this.formTTHD.loaitb_id = response.data.data[0].ID       
          }
        });
        
      } catch (error) {
        console.log(error)        
      }
    },
    async changeLoaiHinh(loaitb_id) {
      try {        
        let data = {
          vloaihd_id : 1,
          vloaitb_id : loaitb_id,
        }
        //load loaihinhtb
        await api.lay_ds_kieu_ld(this.axios, data).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {            
            this.options.cbo_kieuld = response.data.data;     
            this.formTTHD.kieuld_id = 813      
          }
        });
        
      } catch (error) {
        console.log(error)        
      }
    },
    async HIEN_DANHSACH_HD_CHUA_GIAO() {
      try {        
        let data = {
          p_tthd_id : 1,
          p_loaihd_id : 1,
          p_nhanvien_id : this.$root.token.getNhanVienID(),
          p_dichvuvt_id : 19
        }
        this.gvDanhSachHD.tableData = []
        await api.sp_lay_ds_hd_csht_chuagiao(this.axios, data).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {            
            this.gvDanhSachHD.tableData = response.data.data;    
                
          }
        });
        
      } catch (error) {
        console.log(error)        
      }
    },
    async FILL_THONGTIN_KHACHHANG(p_kieu,p_ma_hd,p_ma_kh,p_so_dt,p_so_gt){
      this.loading(true)
      this.ClearGUI()
      try {
        let data = {
            "p_kieu": p_kieu,
            "p_ma_hd": p_ma_hd,
            "p_ma_kh": p_ma_kh,
            "p_so_dt": p_so_dt,
            "p_so_gt": p_so_gt
        }
        this.isReadonlyFields = false
        await api.sp_lay_thongtin_kh_csht(this.axios, data).then((response) => {
          this.loading(false)
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
            //todo set readonly các trường form khách hàng, giấy tờ
            let formdata = response.data.data[0]
             
            this.formViewTTKH.khachhang_id = formdata.khachhang_id
            this.formViewTTKH.ma_kh = formdata.ma_kh
            this.formViewTTKH.ten_kh = formdata.ten_kh            
            this.formViewTTKH.nguoi_dd = formdata.nguoi_dd
            this.formViewTTKH.chucdanh_dd = formdata.chucdanh_dd   
            this.formViewTTKH.so_dt = formdata.so_dt
            this.formViewTTKH.so_fax = formdata.so_fax
            this.formViewTTKH.stk = formdata.stk
            this.formViewTTKH.mst = formdata.mst
            this.formViewTTKH.loaigt_id = formdata.loaigt_id
            this.formViewTTKH.so_gt = formdata.so_gt
            this.formViewTTKH.ngaycap =  moment(formdata.ngaycap,'yyyy-MM-dd HH:mm:ss').format('yyyy-MM-dd')
            this.formViewTTKH.noicap =  formdata.noicap
            
            //this.formViewTTKH.diachi_kh.diachi_id = formdata.diachi_kh_id
            this.formViewTTKH.diachi_kh.diachi_id = 0
            this.formViewTTKH.diachi_kh.diachi = formdata.diachi_kh
            this.formViewTTKH.diachi_kh.tinh_id = formdata.tinh_kh_id
            this.formViewTTKH.diachi_kh.quan_id = formdata.quan_kh_id
            this.formViewTTKH.diachi_kh.phuong_id = formdata.phuong_kh_id 
            this.formViewTTKH.diachi_kh.pho_id = formdata.pho_kh_id 
            this.formViewTTKH.diachi_kh.khu_id = formdata.khu_kh_id
            this.formViewTTKH.diachi_kh.so_nha = formdata.sonha_kh 
            this.formViewTTKH.diachi_kh.dac_diem_id = formdata.noicap
            this.formViewTTKH.diachi_kh.sonha = formdata.sonha_kh
            this.formViewTTKH.diachi_kh.dacdiem_id = formdata.dacdiem_kh_id 

            this.cloneDiachi(this.formViewTTKH.diachi_kh)  
            
            //set readonly các trường
            this.isReadonlyFields = true
          }
          
        });
      }
      catch(ex){
        console.log(ex)
        this.loading(false)
      }      
    },
    async CAPNHAT(khachhang_id,hdkh_id,hdtt_id,hdtb_id,diachi_kh,diachi_tt,diachi_tb,diachi_ld){
      try{
        console.log('CAPNHAT')
        console.log(hdkh_id)
        console.log(hdtt_id)
        console.log(hdtb_id)
        if (!this.KiemTraDL_KhachHang()) return false;
        
        // this.$v.$touch();
        // if (this.$v.$invalid) {
        //   return;
        // } 
        if (!this.KiemTraDL_ThueBao()) return false;
        var isNew = hdkh_id == 0;
        this.loading(true)
        await this.TaoDuLieu_HDKH(hdkh_id, khachhang_id);
        console.log(this.dsHDKH)
        await this.TaoDuLieu_HDTT(hdtt_id, this.dsHDKH[0].HDKH_ID);
        console.log(this.dsHDTT)
        await this.TaoDuLieu_HDTB(this.dsHDKH[0].HDKH_ID, this.dsHDTT[0].HDTT_ID, hdtb_id);
        console.log(this.dsHDTB)
        await this.TaoDuLieu_HDTB_ADSL(this.dsHDTB[0].HDTB_ID);
        await this.TaoDuLieu_HDTBDV(this.dsHDTB[0].HDTB_ID);
        await this.TaoDuLieu_HDTBKV(this.dsHDTB[0].HDTB_ID);
        
        await this.TaoDuLieu_DiaChi(this.dsHDKH[0].HDKH_ID,this.dsHDTT[0].HDTT_ID,this.dsHDTB[0].HDTB_ID,diachi_kh,diachi_tt,diachi_tb,diachi_ld)
        let ds_para = {            
          THEMMOI:(hdtb_id==0),
          HUONGGIAO_ID:this.huonggiaotn_id,
          LOAITB_ID:this.formTTHD.loaitb_id,
          DICHVUVT_ID:this.formTTHD.dichvuvt_id,
          MA_TB:this.formTTHD.ma_tb,
          HD_KHACHHANG: this.dsHDKH,
          HD_THANHTOAN: this.dsHDTT,
          HD_THUEBAO: this.dsHDTB,
          HDTB_DV: this.dsHDTBDV,
          HDTB_ADSL: this.dsHDTB_ADSL,
          DIACHI: this.dsDiaChi,
          DIACHI_HDKH: this.dsDiaChiHDKH,
          DIACHI_HDTT: this.dsDiaChiHDTT,
          DIACHI_HDTB: this.dsDiaChiHDTB,
          HDTB_KV: this.dsHDTB_KV,

        }
        let tt_nd = {
            "DONVI_ID":this.$root.token.getDonViID(),
            "NHANVIEN_ID":this.$root.token.getNhanVienID(),
            "NGUOI_CN":this.$root.token.getUserName(),
            "MAY_CN":this.may_cn,
            "IP_CN":this.ip_cn,
        }
        let input = {
          "p_tt_nd":JSON.stringify(tt_nd),
          "p_ds_para":JSON.stringify(ds_para)
        }
        console.log(input)
        
        await api.fn_ins_update_hd_datmoi_csht(this.axios, input).then((response) => {
          this.loading(false)
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            console.log(response.data.data)
            this.$toast.success("Cập nhật dữ liệu thành công.")
            this.HIEN_DANHSACH_HD_CHUA_GIAO()  
          }
        });
        
      }
      catch (ex){
        console.log(ex)
        this.loading(false)
        this.$toast.error("Có lỗi khi lưu:" + ex)            
      }
    },    
    async TaoDuLieu_DiaChi(hdkh_id,hdtt_id,hdtb_id,diachi_kh, diachi_tt, diachi_tb, diachi_ld)
    {
        console.log('TaoDuLieu_DiaChi')
        this.dsDiaChi = []
        this.dsDiaChiHDKH = []
        this.dsDiaChiHDTT = []
        this.dsDiaChiHDTB = []

        ///region Địa chỉ khách hàng
        let isNewDCKH = (diachi_kh.diachi_id == 0 || diachi_kh.diachi_id == null);
        let rowDiaChiKH = {}
        if (isNewDCKH){
          rowDiaChiKH.DIACHI_ID = await this.GetKey("DIACHI")
        }
        else {
          rowDiaChiKH.DIACHI_ID = diachi_kh.diachi_id
        }        
        rowDiaChiKH.TINH_ID = (diachi_kh.tinh_id==null) ? 0 : diachi_kh.tinh_id
        rowDiaChiKH.QUAN_ID = (diachi_kh.quan_id==null) ? 0 : diachi_kh.quan_id
        rowDiaChiKH.PHUONG_ID = (diachi_kh.phuong_id==null) ? 0 : diachi_kh.phuong_id
        rowDiaChiKH.PHO_ID = diachi_kh.pho_id
        rowDiaChiKH.AP_ID = diachi_kh.ap_id
        rowDiaChiKH.KHU_ID = diachi_kh.khu_id
        rowDiaChiKH.SONHA = diachi_kh.sonha
        rowDiaChiKH.DIACHI = diachi_kh.diachi
        this.dsDiaChi.push(rowDiaChiKH);

        if (isNewDCKH){
          let rowDiaChiHDKH = {}
          rowDiaChiHDKH.DIACHI_ID = rowDiaChiKH.DIACHI_ID;
          rowDiaChiHDKH.HDKH_ID = hdkh_id
          this.dsDiaChiHDKH.push(rowDiaChiHDKH);
        }        
        ///endregion

        ///region Địa chỉ thanh toán
        let isNewDCTT = (diachi_tt.diachi_id == 0 || diachi_tt.diachi_id == null);
        let rowDiaChiTT = {}
        if (isNewDCTT){
          rowDiaChiTT.DIACHI_ID = await this.GetKey("DIACHI")
        }
        else {
          rowDiaChiTT.DIACHI_ID = diachi_tt.diachi_id
        }        
        rowDiaChiTT.TINH_ID = (diachi_tt.tinh_id==null) ? 0 : diachi_tt.tinh_id
        rowDiaChiTT.QUAN_ID = (diachi_tt.quan_id==null) ? 0 : diachi_tt.quan_id
        rowDiaChiTT.PHUONG_ID = (diachi_tt.phuong_id==null) ? 0 : diachi_tt.phuong_id
        rowDiaChiTT.PHO_ID = diachi_tt.pho_id
        rowDiaChiTT.AP_ID = diachi_tt.ap_id
        rowDiaChiTT.KHU_ID = diachi_tt.khu_id
        rowDiaChiTT.SONHA = diachi_tt.sonha
        rowDiaChiTT.DIACHI = diachi_tt.diachi
        this.dsDiaChi.push(rowDiaChiTT)

        if (isNewDCTT){
          let rowDiaChiHDTT = {}
          rowDiaChiHDTT.DIACHI_ID = rowDiaChiTT.DIACHI_ID;
          rowDiaChiHDTT.HDTT_ID = hdtt_id
          this.dsDiaChiHDTT.push(rowDiaChiHDTT);
        }
        ///endregion

        //region Địa chỉ tb/ld
        let isNewDCTB = (diachi_tb.diachi_id == 0 || diachi_tb.diachi_id == null);
        let rowDiaChiTB = {}
        let rowDiaChiLD = {}
        if (isNewDCTB){
          rowDiaChiTB.DIACHI_ID = await this.GetKey("DIACHI")
          rowDiaChiLD.DIACHI_ID = await this.GetKey("DIACHI")
        }
        else {
          rowDiaChiTB.DIACHI_ID = diachi_tb.diachi_id
          rowDiaChiLD.DIACHI_ID = diachi_ld.diachi_id
        }     
        //Địa chỉ lắp đặt --> dataset dsdiachi   
        rowDiaChiLD.TINH_ID = (diachi_ld.tinh_id==null) ? 0 : diachi_ld.tinh_id
        rowDiaChiLD.QUAN_ID = (diachi_ld.quan_id==null) ? 0 : diachi_ld.quan_id
        rowDiaChiLD.PHUONG_ID = (diachi_ld.phuong_id==null) ? 0 : diachi_ld.phuong_id
        rowDiaChiLD.PHO_ID = diachi_ld.pho_id
        rowDiaChiLD.AP_ID = diachi_ld.ap_id
        rowDiaChiLD.KHU_ID = diachi_ld.khu_id
        rowDiaChiLD.SONHA = diachi_ld.sonha
        rowDiaChiLD.DIACHI = diachi_ld.diachi
        this.dsDiaChi.push(rowDiaChiLD);

        //Địa chỉ thuê bao --> dataset dsdiachi
        rowDiaChiTB.TINH_ID = (diachi_tb.tinh_id==null) ? 0 : diachi_tb.tinh_id
        rowDiaChiTB.QUAN_ID = (diachi_tb.quan_id==null) ? 0 : diachi_tb.quan_id
        rowDiaChiTB.PHUONG_ID = (diachi_tb.phuong_id==null) ? 0 : diachi_tb.phuong_id
        rowDiaChiTB.PHO_ID = diachi_tb.pho_id
        rowDiaChiTB.AP_ID = diachi_tb.ap_id
        rowDiaChiTB.KHU_ID = diachi_tb.khu_id
        rowDiaChiTB.SONHA = diachi_tb.sonha
        rowDiaChiTB.DIACHI = diachi_tb.diachi
        this.dsDiaChi.push(rowDiaChiTB)

        if (isNewDCTB){
            let rowDiaChiHDTB = {}
            rowDiaChiHDTB.DIACHI_ID = rowDiaChiTB.DIACHI_ID
            rowDiaChiHDTB.HDTB_ID = hdtb_id
            rowDiaChiHDTB.DIACHILD_ID = rowDiaChiLD.DIACHI_ID
            this.dsDiaChiHDTB.push(rowDiaChiHDTB)
        }     
        console.log('TaoDuLieu_DiaChi end')          
    },
    async cloneDiachi(diachi_kh){
      for (let key in diachi_kh) {
        this.$set(this.formViewTTKH.diachi_tt, key, diachi_kh[key])
        this.$set(this.formViewTTKH.diachi_tb, key, diachi_kh[key])
        this.$set(this.formViewTTKH.diachi_ld, key, diachi_kh[key])
      }
      await this.CHON_TOTC_THEO_DIACHI(diachi_kh)
      await this.CHON_DIABAN_THEO_DIACHI(diachi_kh)
    },
    btnDiaban_Click(){
      this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal()
    },
    btnDiaChi_Click(){      
      console.log('click địa chỉ')
      this.$bvModal.show('popupDiaChi')
    },  
    btnThemmoi_Click(){      
      console.log('click thêm mới')
      this.SetButton(1)
      this.ClearGUI()
    },  
    async btnGhilai_Click(){      
      console.log('click ghi lại')
      let isNew = (this.formTTHD.hdkh_id==0)
      let khachhang_id = 0
      if (isNew)
      {
          khachhang_id = this.formViewTTKH.khachhang_id == 0 ? 0:this.formViewTTKH.khachhang_id;
      }
      else
      {
          khachhang_id = this.formTTHD.khachhang_id == 0 ? 0:this.formTTHD.khachhang_id;
      }
      await this.CAPNHAT(
          khachhang_id,
          isNew ? 0 : this.formTTHD.hdkh_id,
          isNew ? 0 : this.formTTHD.hdtt_id,
          isNew ? 0 : this.formTTHD.hdtb_id,
          this.formViewTTKH.diachi_kh,this.formViewTTKH.diachi_tt,
          this.formViewTTKH.diachi_tb,this.formViewTTKH.diachi_ld
      );

    },
    async btnGiaoPhieu_Click(){
      console.log('click btnGiaoPhieu_Click')
      if (this.formGiaoPhieu.donvinhan_id == 0)
      {
          this.$toast.error("Chưa chọn đơn vị nhận phiếu.")          
          return
      }
      if (this.formTTHD.hdtb_id == 0) {
        return;
      }
      this.loading(true)
      try
      {
        let data = {
          "p_donvi_id": parseInt(this.$root.token.getDonViID()),
          "p_donvi_nhan_id": parseInt(this.formGiaoPhieu.donvinhan_id),
          "p_hdtb_id": this.formTTHD.hdtb_id,
          "p_ip_cn": this.ip_cn,
          "p_may_cn": this.may_cn,
          "p_ngay_cn": moment(new Date()).format("DD/MM/yyyy"),
          "p_nguoi_cn": this.$root.token.getUserName(),
          "p_nhanvien_id": parseInt(this.$root.token.getNhanVienID()),
          "p_noidung_giao": this.formGiaoPhieu.ghichu_gp,
          "p_phieu_cha_id": 0
        }
        await api.fn_giaophieu_thicong_csht(this.axios, data).then((res) => {        
          this.loading(false)
          if (res.data && res.data.data ) {  
            if (res.data.data == "OK") {
              this.$toast.success("Giao phiếu thành công.")
              this.HIEN_DANHSACH_HD_CHUA_GIAO()
              return
            } else {
              this.$toast.error(res.data.data)
            }
          }        
        })
      }
      catch (ex)
      {
        this.$toast.error("Có lỗi! "+ex) 
        this.loading(false)
      }
    },
    async btnXoaHD_Click(){      
      console.log('click xóa hợp đồng')
      if (this.formTTHD.hdtb_id == 0) {
        return;
      }
      if (!window.confirm("Bạn thật sự muốn xóa hợp đồng không ?")) {
        return
      }
      this.loading(true)
      try
      {
        
        let data = {"in_dk": " where tthd_id=1 and hdtb_id="+this.formTTHD.hdtb_id,"in_id_neo": "count(1)","in_table": "css.HD_THUEBAO"};      
        await api.lay_map_thamso(this.axios, data).then((res) => {        
          if (res.data && res.data.data ) {  
            if (res.data.data == "0") {
              this.$toast.error("Hợp đồng đã chuyển trạng thái hoặc đã xoá.")
              this.HIEN_DANHSACH_HD_CHUA_GIAO()
              this.loading(false)
              return
            }        
          }        
        })
        let data_xoa = {
          vhdkh_id: this.formTTHD.hdkh_id,
          vloaihd_id: LoaiHopDong.DAT_MOI,
        }
        await api.xoa_hdkh_v2(this.axios, data_xoa).then((response) => {
          this.loading(false)
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            console.log(response.data.data)
            this.$toast.success("Xóa hợp đồng thành công!"); 
            this.ClearGUI()
            this.HIEN_DANHSACH_HD_CHUA_GIAO()
          }            
        })          
      }
      catch (ex)
      {
          this.loading(false)
          this.$toast.error("Có lỗi khi xóa hợp đồng!"+ex)          
      }
    },    
    btnNapLai_Click(){      
      console.log('click nạp lại')
      this.SetButton(1)
      this.HIEN_DANHSACH_HD_CHUA_GIAO()
      
    },
    onActionClick (action) {
      if (action.id === 'nhapmoi') {
        this.btnThemmoi_Click()
      } else if (action.id === 'ghilai') {
        this.btnGhilai_Click()
      } else if (action.id === 'xoahd') {
        this.btnXoaHD_Click()
      } else if (action.id === 'naplai') {
        this.btnNapLai_Click()
      } 
    },
    async soHDKeyPress(){
      this.FILL_THONGTIN_KHACHHANG(1,this.formTTHD.ma_hd,'-1','-1','-1')
    },
    async maKHKeyPress(){
      let searchTerm = this.formViewTTKH.ma_kh
      this.FILL_THONGTIN_KHACHHANG(2,'-1',searchTerm,'-1','-1')
    },
    async soDTKeyPress(){
      let searchTerm = this.formViewTTKH.so_dt
      this.FILL_THONGTIN_KHACHHANG(2,'-1','-1',searchTerm,'-1')
    },
    async soGTKeyPress(){
      let searchTerm = this.formViewTTKH.so_gt
      this.FILL_THONGTIN_KHACHHANG(2,'-1','-1','-1',searchTerm)
    }, 
    async getDiaChi(data){
      console.log(data)
      if (!data) return
      for (let key in data) {
        this.$set(this.formViewTTKH.diachi_kh, key, data[key]);
        this.$set(this.formViewTTKH.diachi_tt, key, data[key]);
        this.$set(this.formViewTTKH.diachi_tb, key, data[key]);
        this.$set(this.formViewTTKH.diachi_ld, key, data[key]);        
      }
      this.formViewTTKH.diachi_kh.dacdiem_id=data.dac_diem_id
      this.formViewTTKH.diachi_kh.sonha=data.so_nha
      this.formViewTTKH.diachi_kh.diachi=data.diachimoi

      this.formViewTTKH.diachi_tt.dacdiem_id=data.dac_diem_id
      this.formViewTTKH.diachi_tt.sonha=data.so_nha
      this.formViewTTKH.diachi_tt.diachi=data.diachimoi

      this.formViewTTKH.diachi_tb.dacdiem_id=data.dac_diem_id
      this.formViewTTKH.diachi_tb.sonha=data.so_nha
      this.formViewTTKH.diachi_tb.diachi=data.diachimoi

      this.formViewTTKH.diachi_ld.dacdiem_id=data.dac_diem_id
      this.formViewTTKH.diachi_ld.sonha=data.so_nha
      this.formViewTTKH.diachi_ld.diachi=data.diachimoi

      await this.CHON_TOTC_THEO_DIACHI(this.formViewTTKH.diachi_kh)
      await this.CHON_DIABAN_THEO_DIACHI(this.formViewTTKH.diachi_kh)

    },
    async CHON_TOTC_THEO_DIACHI(diachi){
      try {
        let data = {p_loaidv_id :5,
          p_phuong_id :diachi.phuong_id,
          p_pho_id :diachi.pho_id,
          p_ap_id :diachi.ap_id,
          p_khu_id :diachi.khu_id ,
        }
        let kq_kv=null

        let response = await api.sp_lay_donvi_theo_loaidv_diachi_ld(this.axios, data)

        if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {                        
            kq_kv =    response.data.data
        } else {
            data = {p_loaidv_id :5,
                    p_phuong_id :diachi.phuong_id,
                    p_pho_id :diachi.pho_id,
                    p_ap_id :diachi.ap_id,
                    p_khu_id :"0" ,
            }
            response = await api.sp_lay_donvi_theo_loaidv_diachi_ld(this.axios, data)
            if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {                        
                kq_kv =    response.data.data
            }else {
              data = {p_loaidv_id :5,
                      p_phuong_id :diachi.phuong_id,
                      p_pho_id :diachi.pho_id,
                      p_ap_id :"0",
                      p_khu_id :"0" ,
              }
              response = await api.sp_lay_donvi_theo_loaidv_diachi_ld(this.axios, data)
              if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {                        
                  kq_kv =    response.data.data
              } else {
                data = {p_loaidv_id :5,
                        p_phuong_id :diachi.phuong_id,
                        p_pho_id :"0",
                        p_ap_id :"0",
                        p_khu_id :"0" ,
                }
                response = await api.sp_lay_donvi_theo_loaidv_diachi_ld(this.axios, data)
                if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {                        
                    kq_kv = response.data.data
                }
              }
            }
        }
        
        if (kq_kv && kq_kv.length ==1){          
          this.formGiaoPhieu.donvinhan_id = kq_kv[0].donvi_id
        } else if (kq_kv && kq_kv.length >1){
          let temp =[]
          $.each(kq_kv, function (key, item) {
              temp.push(item.ten_dv)
          });
          var tracnghiem = {
            title: 'Chọn tổ thi công',
            caption: 'Chọn 1 đơn vị từ danh sách',
            options: temp,
            data: null
          }
        this.$refs.ModalTracnghiemExt.show(tracnghiem)
          .then(result => {
            if (result.ok) {
              console.log('Chọn tổ thi công', result)
              this.formGiaoPhieu.donvinhan_id = kq_kv[result.option].donvi_id
            } else {
              console.log('Cancel')
              this.formGiaoPhieu.donvinhan_id = kq_kv[0].donvi_id
            }
          })
        } else {
          this.formGiaoPhieu.donvinhan_id = this.options.cbo_donvinhan[0].donvi_id
        }
        
      }
      catch(ex){
        console.log(ex)
      }

    },
    async CHON_DIABAN_THEO_DIACHI(diachi){
      try {
        let data = {
          donvi_ql_id:0,
          quan_id:diachi.quan_id,
          phuong_id:diachi.phuong_id,
          pho_id:diachi.pho_id,
          ap_id:diachi.ap_id,
          khu_id:diachi.khu_id,          
          dacdiem_id:diachi.dacdiem_id,

        }
        let kq_kv=null

        let response = await api.sp_ht_kv_cskh_theo_diaban(this.axios, data)

        if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {                        
            kq_kv =    response.data.data
        } else {
            data = {
                    donvi_ql_id:0,
                    quan_id:diachi.quan_id,
                    phuong_id:diachi.phuong_id,
                    pho_id:diachi.pho_id,
                    ap_id:diachi.ap_id,
                    khu_id:diachi.khu_id,          
                    dacdiem_id:"0",
            }
            response = await api.sp_ht_kv_cskh_theo_diaban(this.axios, data)
            if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {                        
                kq_kv =    response.data.data
            }else {
              data = {
                      donvi_ql_id:0,
                      quan_id:diachi.quan_id,
                      phuong_id:diachi.phuong_id,
                      pho_id:diachi.pho_id,
                      ap_id:diachi.ap_id,
                      khu_id:"0",          
                      dacdiem_id:"0",
              }
              response = await api.sp_ht_kv_cskh_theo_diaban(this.axios, data)
              if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {                        
                  kq_kv =    response.data.data
              } else {
                  data = {
                        donvi_ql_id:0,
                        quan_id:diachi.quan_id,
                        phuong_id:diachi.phuong_id,
                        pho_id:diachi.pho_id,
                        ap_id:"0",
                        khu_id:"0",          
                        dacdiem_id:"0",
                }
                response = await api.sp_ht_kv_cskh_theo_diaban(this.axios, data)
                if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {                        
                    kq_kv = response.data.data
                }else {
                  data = {
                          donvi_ql_id:0,
                          quan_id:diachi.quan_id,
                          phuong_id:diachi.phuong_id,
                          pho_id:"0",
                          ap_id:"0",
                          khu_id:"0",          
                          dacdiem_id:"0",
                  }
                  response = await api.sp_ht_kv_cskh_theo_diaban(this.axios, data)
                  if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {                        
                      kq_kv = response.data.data
                  }
                }
              }
            }
        }
        
        if (kq_kv && kq_kv.length ==1){          
          this.CurrentDiaBan.khuvuc_id = kq_kv[0].khuvuc_id
          this.CurrentDiaBan.ten_kv = kq_kv[0].ten_kv
        } else if (kq_kv && kq_kv.length >1){
          let temp =[]
          $.each(kq_kv, function (key, item) {
              temp.push(item.ten_kv)
          });
          var tracnghiem = {
            title: 'Chọn địa bàn quản lý',
            caption: 'Chọn 1 địa bàn từ danh sách',
            options: temp,
            data: null
          }
        this.$refs.ModalTracnghiemExt.show(tracnghiem)
          .then(result => {
            if (result.ok) {
              console.log('Chọn địa bàn quản lý', result)
              this.CurrentDiaBan.khuvuc_id = kq_kv[result.option].khuvuc_id
              this.CurrentDiaBan.ten_kv = kq_kv[result.option].ten_kv
            } else {
              console.log('Cancel')
              this.CurrentDiaBan.khuvuc_id = kq_kv[0].khuvuc_id
              this.CurrentDiaBan.ten_kv = kq_kv[0].ten_kv
            }
          })
        } else {
          this.CurrentDiaBan={
            ten_kv:"",
            khuvuc_id:0,
          }
          //show popup chọn khu vực
          this.btnDiaban_Click()

        }
        
      }
      catch(ex){
        console.log(ex)
      }

    },
    onAcceptKhuVuc(data){
      console.log('onAcceptKhuVuc')
      console.log(data)
      this.CurrentDiaBan={
        ten_kv:data.ten_kv,
        khuvuc_id:data.khuvuc_id,
      }    
    },
    successUpdateThayDoiKhuVucQuanLy(){
    },
    closePopupNVDB(){

    },
    
    KiemTraDL_KhachHang(){
        if (!this.formViewTTKH.ten_kh || this.formViewTTKH.ten_kh.trim() == "")
        {
            this.$toast.error("Hãy nhập tên khách hàng !")
            $('#ten_kh').focus()            
            return false
        }
        if (!this.formViewTTKH.diachi_kh || this.formViewTTKH.diachi_kh.diachi.trim() == "")
        {
            this.$toast.error("Hãy nhập địa chỉ khách hàng!")
            $('#diachi_kh').focus()            
            return false
        }
        if (!this.formViewTTKH.so_dt || this.formViewTTKH.so_dt.trim() == "")
        {
            this.$toast.error("Bạn chưa nhập số điện thoại liên hệ của khách hàng !")
            $('#so_dt').focus()            
            return false
        }
        if (!this.formTTHD.ma_hd || this.formTTHD.ma_hd.trim() == "")
        {
            this.$toast.error("Chưa nhập số hợp đồng gốc!")
            $('#ma_hd').focus()            
            return false
        }
        if (!this.formViewTTKH.so_gt || this.formViewTTKH.so_gt.trim() == "")
        {
            this.$toast.error("Chưa nhập số giấy tờ!")
            $('#so_gt').focus()            
            return false
        }
        this.$v.$touch()
        if (this.$v.formViewTTKH.so_dt.$error) {
          this.$toast.error("Sai định dạng số điện thoại!")
          $('#so_dt').focus()
          return false
        }

        return true;
    },
    KiemTraDL_ThueBao(){
        if (this.formTTHD.loaitb_id ==0)
        {
            this.$toast.error("Hãy chọn loại loại hình!")
            $('#loai_tb').focus()            
            return false
        }

        if (this.formTTHD.kieuld_id == 0)
        {
            this.$toast.error("Hãy chọn kiểu yêu cầu!")
            $('#kieu_ld').focus()            
            return false
        }

        if (this.formTTHD.loai_csht_id == 0)
        {
            this.$toast.error("Hãy chọn loại CSHT!")
            $('#loai_csht').focus()            
            return false
        }

        if (!this.CurrentDiaBan.ten_kv || this.CurrentDiaBan.ten_kv.trim() == "")
        {
            $('#ten_kv').focus()
            this.$toast.error("Chưa chọn địa bàn quản lý!")
            return false
        }

        return true;
    },    

    async TaoDuLieu_HDKH(hdkh_id, khachhang_id) {
      console.log(">>>TaoDuLieu_HDKH")
      this.dsHDKH = [];
      let rowHDKH = {};
      let isNew = hdkh_id == 0;

      if (isNew){
        rowHDKH.HDKH_ID = await this.GetKey("HD_KHACHHANG")
      }
      else {
        rowHDKH.HDKH_ID = hdkh_id
      }
      
      //this.hdkh_id =rowHDKH.HDKH_ID

      if (isNew){
        this.formTTHD.ma_gd = await this.Lay_Ma_GD(LoaiHopDong.DAT_MOI);
        rowHDKH.MA_GD = this.formTTHD.ma_gd;
        rowHDKH.MA_HD = this.ChuanHoa_XML(this.formTTHD.ma_hd);
      } else {
        rowHDKH.MA_GD = this.formTTHD.ma_gd;
        rowHDKH.MA_HD = this.ChuanHoa_XML(this.formTTHD.ma_hd);
      }      
      
      if (khachhang_id > 0) {
          rowHDKH.KHACHHANG_ID = khachhang_id
          rowHDKH.MA_KH = this.formViewTTKH.ma_kh
      }
      else {
        let data = {
          "vdonvi_id": 0,            
        }
        await api.getMaKH(this.axios, data).then((response) => {          
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            rowHDKH.MA_KH = response.data.data
          }
          else rowHDKH.MA_KH = this.formViewTTKH.ma_kh
          
        });        
        rowHDKH.KHACHHANG_ID = 0;
      }       
      rowHDKH.TEN_KH = this.formViewTTKH.ten_kh;
      rowHDKH.DIACHI_KH = this.formViewTTKH.diachi_kh.diachi;
      rowHDKH.GHICHU = this.formTTHD.ghichu;    
      if (this.formViewTTKH.loaigt_id != 0)
        rowHDKH.LOAIGT_ID = this.formViewTTKH.loaigt_id;   
      rowHDKH.SO_GT = this.formViewTTKH.so_gt;
      if (this.formViewTTKH.ngaycap != "")
        rowHDKH.NGAYCAP = this.formViewTTKH.ngaycap;
      rowHDKH.MST = this.formViewTTKH.mst;
      rowHDKH.NOICAP = this.formViewTTKH.noicap;          
      rowHDKH.DONVI_ID = this.$root.token.getDonViID();
      rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID();
      rowHDKH.KHLON_ID = 1;     
      rowHDKH.LOAIHD_ID = LoaiHopDong.DAT_MOI;
      rowHDKH.NGUOI_DD = this.formViewTTKH.nguoi_dd;
      rowHDKH.CHUCDANH_DD = this.formViewTTKH.chucdanh_dd;

      rowHDKH.NGUOI_CN = this.$root.token.getUserName();
      rowHDKH.NGAY_CN = moment(new Date()).format("DD/MM/yyyy HH:mm:ss");
      rowHDKH.MAY_CN = this.may_cn;
      rowHDKH.CTV_ID = this.$root.token.getNhanVienID();
      
      rowHDKH.NGAYLAP_HD = moment(this.searchDate.ngay_lap,"DD/MM/yyyy HH:mm:ss").format("DD/MM/yyyy HH:mm:ss"); ;
      rowHDKH.NGAY_YC =  moment(new Date()).format("DD/MM/yyyy HH:mm:ss");
      rowHDKH.GIOITINH=null;
      rowHDKH.SO_DT = this.formViewTTKH.so_dt;
      rowHDKH.SO_FAX = this.formViewTTKH.so_fax;
      rowHDKH.STK = this.formViewTTKH.stk;
      this.dsHDKH.push(rowHDKH);
    },
    async TaoDuLieu_HDTT(hdtt_id, hdkh_id){
      console.log(">>>TaoDuLieu_HDTT")
      this.dsHDTT = [];
      let rowHDTT = {};
      let isNew = (hdtt_id == 0?true:false);

      if (isNew){
        rowHDTT.HDTT_ID = await this.GetKey("HD_THANHTOAN")
      }
      else {
        rowHDTT.HDTT_ID = hdtt_id
      }


      rowHDTT.HDKH_ID = hdkh_id
      if (isNew)
      {
          let ma_tt = await this.$root.context.get(`/web-hopdong/chuyendich/sinh_matt?donvi_id=0&quan_id=0`)
          rowHDTT.MA_TT = ma_tt.data
          rowHDTT.THANHTOAN_ID = 0;
      }
      rowHDTT.TEN_TT = (this.formViewTTKH.ten_gd=='') ? this.formViewTTKH.ten_kh : this.formViewTTKH.ten_gd;
      rowHDTT.DIACHI_TT = this.formViewTTKH.diachi_kh.diachi
      rowHDTT.DIACHI_BC = this.formViewTTKH.diachi_kh.diachi
      rowHDTT.MST = this.formViewTTKH.diachi_kh.mst
      rowHDTT.SO_DT = this.formViewTTKH.diachi_kh.so_dt
      rowHDTT.DONVI_ID = 0;// Fix tạm, nếu api gọi ko ra dữ liệu
      let data = {
        "loaidv_id": 2
      }
      await api.sp_lay_ds_donvi_theo_loaidv(this.axios, data).then((response) => {          
        if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&&response.data.data.length>0) {
          rowHDTT.DONVI_ID = response.data.data[0].donvi_id
        }
        else rowHDTT.DONVI_ID = 0        
      });
      
      rowHDTT.HTTT_ID = 2;//Fix tạm hình thức thanh toán

      rowHDTT.NGUOI_CN = this.$root.token.getUserName()
      rowHDTT.NGAY_CN = moment(new Date()).format("DD/MM/yyyy HH:mm:ss")
      rowHDTT.MAY_CN = this.may_cn//this.$root.token.getMachine()
      rowHDTT.IP_CN = this.ip_cn//this.$root.token.getIP()

      this.dsHDTT.push(rowHDTT)
    },
    async TaoDuLieu_HDTB(hdkh_id, hdtt_id, hdtb_id){
      console.log(">>>TaoDuLieu_HDTB")
      this.dsHDTB = []
      let rowHDTB = {}
      let isNew = (hdtb_id == 0?true:false)

      if (isNew){
        rowHDTB.HDTB_ID = await this.GetKey("HD_THUEBAO")
      }
      else {
        rowHDTB.HDTB_ID = hdtb_id
      }
      
      rowHDTB.HDKH_ID = hdkh_id
      rowHDTB.HDTT_ID = hdtt_id
      //sinh mã thuê bao      
      let data = {
        "vphanvung_id": this.$root.token.getPhanVungID(),
        "vquan_id": "0",
        "vdichvuvt_id": this.formTTHD.dichvuvt_id,
        "vloaitb_id": this.formTTHD.loaitb_id,
        "vtentat": ""
      }
      await api.sinh_matb(this.axios, data).then((response) => {          
        if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
          this.formTTHD.ma_tb = response.data.data          
        }            
      });

      rowHDTB.MA_TB = this.formTTHD.ma_tb
      rowHDTB.TEN_TB = this.formViewTTKH.ten_kh
      rowHDTB.DIACHI_TB = this.formViewTTKH.diachi_kh.diachi
      rowHDTB.KIEULD_ID = this.formTTHD.kieuld_id
      rowHDTB.LOAITB_ID = this.formTTHD.loaitb_id
      rowHDTB.TTHD_ID = TrangThaiHD.MOI
      rowHDTB.DICHVUVT_ID = DichVuVienThong.HATANG_VIENTHONG
      rowHDTB.KIEU_TB = "0"
      rowHDTB.NGUOI_CN = this.$root.token.getUserName()
      rowHDTB.NGAY_CN = moment(new Date()).format("DD/MM/yyyy HH:mm:ss")
      rowHDTB.MAY_CN = this.may_cn
      rowHDTB.GHICHU = this.formTTHD.ghichu
      rowHDTB.DIACHI_LD = this.formViewTTKH.diachi_kh.diachi
      rowHDTB.DOITUONG_ID = 46;//VNTT ký hợp đồng

      //todo check lại hàm
      await this.LayHuongGiao_TiepNhan(LoaiHopDong.DAT_MOI,this.formTTHD.kieuld_id)      
      rowHDTB.QUYTRINH_ID = this.quytrinh_id
      
      this.dsHDTB.push(rowHDTB)    
      console.log('TaoDuLieu_HDTB end')  
    },
    async TaoDuLieu_HDTB_ADSL(hdtb_id){
      console.log('TaoDuLieu_HDTB_ADSL')
      this.dsHDTB_ADSL = []
      let rowHDTB_ADSL = {}

      rowHDTB_ADSL.HDTB_ID = hdtb_id
      rowHDTB_ADSL.EMAIL = ""
      rowHDTB_ADSL.KH_CD = 0
      rowHDTB_ADSL.TOCDO_ID = 6570;//Fix tốc độ ID
      rowHDTB_ADSL.TOCDOTHUC_ID = rowHDTB_ADSL.TOCDO_ID 
      rowHDTB_ADSL.MADOICAP = await this.GetKey("MADOICAP")

      rowHDTB_ADSL.MUCCUOC_ID = 1
      rowHDTB_ADSL.THOIHAN_ID = 2

      rowHDTB_ADSL.THIETBIDC_ID = null
      rowHDTB_ADSL.MA_LT = null
      
      rowHDTB_ADSL.DOITAC_ID = null
      rowHDTB_ADSL.LOAI_CSHT_ID = this.formTTHD.loai_csht_id;
      rowHDTB_ADSL.PROFILEDSL_ID = null

      rowHDTB_ADSL.CONGNGHE_ID = null
      rowHDTB_ADSL.DOITAC_ID = null
      rowHDTB_ADSL.CUOC_DOITAC = null

      rowHDTB_ADSL.VMP = 0

      rowHDTB_ADSL.TRANGBI_ID = 4
      rowHDTB_ADSL.CHUQUAN_ID = null
      rowHDTB_ADSL.CHUQUANLD_ID = null
      rowHDTB_ADSL.KIEUTRA_ID = 2

      rowHDTB_ADSL.THONGTIN_TC = null

      this.dsHDTB_ADSL.push(rowHDTB_ADSL)
    },
    async TaoDuLieu_HDTBDV(hdtb_id){
        this.dsHDTBDV =[]
        let row = {}
        if (this.formGiaoPhieu.donvinhan_id == 0){
          this.dsHDTBDV = null
          return
        }

        row.HDTB_ID = hdtb_id;
        row.LOAIDV_ID = LOAI_DV.TRAM_VT;
        row.DONVI_ID = this.formGiaoPhieu.donvinhan_id
        row.KIEUDV_ID = 2;
        this.dsHDTBDV.push(row)        
    },
    async TaoDuLieu_HDTBKV(hdtb_id){
        this.dsHDTB_KV = []        
        // if (this.CurrentDiaBan.khuvuc_id == 0) {
        //   this.dsHDTB_KV = null
        //   return
        // }
        let dr = {}
        dr.HDTB_ID = hdtb_id;
        dr.KHUVUC_ID = 659//todo this.CurrentDiaBan.khuvuc_id
        dr.LOAIKV_ID = LOAI_KV.KHUVUC_DIABAN;
        dr.NGAY_CN = moment(new Date()).format("DD/MM/yyyy HH:mm:ss")
        dr.NGUOI_CN = this.$root.token.getUserName()
        dr.MAY_CN = this.may_cn//this.$root.token.getMachine()
        dr.IP_CN = this.ip_cn//this.$root.token.getIP()
        this.dsHDTB_KV.push(dr)        
    },
    async LayHuongGiao_TiepNhan(loaihd_id,kieuld_id) {
      let result = "";
      try {
          const data = {
            "vkieuld_id": kieuld_id,
            "vloaihd_id": loaihd_id
          }
          let response = await api.layhuongiao_tiepnhan_v3(this.axios, data);
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.length>0) {
              this.huonggiaotn_id = response.data.data[0].huonggiao_id
              this.quytrinh_id = response.data.data[0].quytrinh_id
          }
      } catch(e) {
          console.log(e);           
      }        
    },
    async Lay_Ma_GD(loaihd_id) {
        let result = "";
        try {
            const params = { 
                loaihd_id: loaihd_id
            };
            let response = await api.sinh_magd(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    async GetKey(keyname){
        var key = 0;
        await api.post_get_keys(this.axios, {"keyname": keyname}).then((response) => {
            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                key= response.data.data;  
            }
        });

        return key;
    },
    ChuanHoa_XML(str) {
      let result = ""
      result = str.replace("&", "&amp;")
      result = result.replace("<", "&lt;")
      result = result.replace(">", "&gt;")
      result = result.replace("'", "&apos;")
      return result
    },
    ClearGUI(){
      //clear khách hàng
      //clear địa bàn
      //clear hợp đồng
      let temp_dichvu_id = this.formTTHD.dichvuvt_id
      let temp_loaitb_id = this.formTTHD.loaitb_id
      let temp_loai_csht_id = this.formTTHD.loai_csht_id
      let temp_kieuld_id = this.formTTHD.kieuld_id
      
      this.dataPopupNVDB = []
      this.dsHDKH = []
      this.dsHDTT = []
      this.dsHDTB = []
      this.dsHDTB_ADSL = []
      this.dsHDTBDV = []
      this.dsHDTB_KV = []
      this.dsDiaChi = []
      this.dsDiaChiHDKH = []
      this.dsDiaChiHDTT = []
      this.dsDiaChiHDTB = []
      //this.hdkh_id = 0
      this.khachhang_id = 0
      this.huonggiaotn_id = 0
      this.quytrinh_id = 0
      this.formTTHD = {
        ma_hd:"",
        dichvuvt_id:0,
        ma_gd:"",
        loaitb_id:0,
        ngaylap_hd:"",
        nguoilap_hd:"",
        kieuld_id:"",
        ma_csht:"",
        ma_tb:"",
        loai_csht_id:0,
        ghichu:"",
        hdkh_id:0,
        hdtt_id:0,
        hdtb_id:0,
        khachhang_id:0,
      }
      this.formTTHD.dichvuvt_id = temp_dichvu_id
      this.formTTHD.loaitb_id = temp_loaitb_id
      this.formTTHD.loai_csht_id = temp_loai_csht_id      
      this.formTTHD.kieuld_id = temp_kieuld_id
      this.showModalGanNV = false
      this.ganKhuVucModel= { hdtb_id: null }
      this.CurrentDiaBan={
          ten_kv:"",
          khuvuc_id:0,
      }
      this.formViewTTKH= {
        khachhang_id:0,
        ma_kh:"",
        ten_kh:"",
        ten_gd:"",
        nguoi_dd:"",
        chucdanh_dd:"",        
        so_dt:"",
        so_fax:"",
        stk:"",
        mst:"",
        loaigt_id:0,
        so_gt:"",
        ngaycap: "",
        noicap: "",        
        diachi_kh:{
            diachi_id:0,
            diachi:"",
            tinh_id:0,
            quan_id:0,
            phuong_id:0,
            pho_id:0,
            khu_id:0,
            so_nha:"",
            dac_diem_id:0,
            sonha:"",
            dacdiem_id:0,
        },
        diachi_tt:{
            diachi_id:0,
            diachi:"",
            tinh_id:0,
            quan_id:0,
            phuong_id:0,
            pho_id:0,
            khu_id:0,            
            sonha:"",
            dacdiem_id:0,
        },
        diachi_tb:{
            diachi_id:0,
            diachi:"",
            tinh_id:0,
            quan_id:0,
            phuong_id:0,
            pho_id:0,
            khu_id:0,            
            sonha:"",
            dacdiem_id:0,
        },
        diachi_ld:{
            diachi_id:0,
            diachi:"",
            tinh_id:0,
            quan_id:0,
            phuong_id:0,
            pho_id:0,
            khu_id:0,            
            sonha:"",
            dacdiem_id:0,
        },
      }

      this.formGiaoPhieu= {
          donvinhan_id:0,
          ghichu_gp:""
      } 
    },
    ClearHDKH(){      
      this.formViewTTKH= {
        khachhang_id:0,
        ma_kh:"",
        ten_kh:"",
        ten_gd:"",
        nguoi_dd:"",
        chucdanh_dd:"",        
        so_dt:"",
        so_fax:"",
        stk:"",
        mst:"",
        loaigt_id:0,
        so_gt:"",
        ngaycap: "",
        noicap: "",        
        diachi_kh:{
            diachi_id:0,
            diachi:"",
            tinh_id:0,
            quan_id:0,
            phuong_id:0,
            pho_id:0,
            khu_id:0,
            so_nha:"",
            dac_diem_id:0,
            sonha:"",
            dacdiem_id:0,
        },
        diachi_tt:{
            diachi_id:0,
            diachi:"",
            tinh_id:0,
            quan_id:0,
            phuong_id:0,
            pho_id:0,
            khu_id:0,            
            sonha:"",
            dacdiem_id:0,
        },
        diachi_tb:{
            diachi_id:0,
            diachi:"",
            tinh_id:0,
            quan_id:0,
            phuong_id:0,
            pho_id:0,
            khu_id:0,            
            sonha:"",
            dacdiem_id:0,
        },
        diachi_ld:{
            diachi_id:0,
            diachi:"",
            tinh_id:0,
            quan_id:0,
            phuong_id:0,
            pho_id:0,
            khu_id:0,            
            sonha:"",
            dacdiem_id:0,
        },
      }
    },
  },
};
</script>
<style scoped>
  .input-icon-right.disabled input{
    cursor: default;
    pointer-events: none;
    text-decoration: none!important;
    color: grey!important;
  }
</style>