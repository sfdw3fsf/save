<template src="./index.html"></template>
<script>
import Vue from 'vue'
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import api from "./api/index.js";
import state from "./define.js";
import frmXemTraTruoc from './popup/frmXemTraTruoc.vue'
const thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')
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
    moment,
    breadcrumb, 
    gridview,   
    frmXemTraTruoc 
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
    ]
  },
  name: "",  
  data() {
    return {
      header: {
        title: "TƯ VẤN THAY ĐỔI TỐC ĐỘ, GÓI CƯỚC ĐA DỊCH VỤ, GIA HẠN TRẢ TRƯỚC",
        list: [
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          {
            name: "Tư vấn thay đổi tốc độ, gói cước",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
      'form-close':this.popupClosed,
      'acceptChangeCTV':this.popupClosed,
      'acceptChangeNGT':this.popupClosed,
      'PopUpCongTacVien_Thoat':this.popupClosed,      
      },
      userComponentModule: null,       
      popupComponentAttr1: {},
      popupComponentEvts1: {
      'form-close':this.popupClosed,      
      'paymentcreated': this.paymentcreated
      },           
      popupComponentName: '',
      footerTongTien: function () {
        return  { template : Vue.component('footerTemplate', {
            template: `<span class='tongtien'>{{data.Custom}}đ</span>`,
            data () {return { data: {}};}
            })
        }
      },      
      ...state
      
    };
  },
  created() {    
  },
  async mounted() {
    try {
        this.loading(true);  
                
        await api.post_lay_khoanmuc_tt_theo_loaihd({vloaihd_id: 8}).then((response) => {          
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
            this.ds_tien_khoanmuc = response.data.data
          }
        })
        await api.post_lay_khoanmuc_tt_theo_loaihd({vloaihd_id: 31}).then((response) => {          
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
            this.ds_tien_khoanmuc_km = response.data.data
          }
        })
        await api.post_lay_khoanmuc_tt_theo_loaihd({vloaihd_id: 27}).then((response) => {          
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
            this.ds_tien_khoanmuc_dadv = response.data.data
          }
        })
        await this.LAY_DS_THAMSO_MD()
        this.loading(false);
      } catch (error) {
        console.log(error)    
        this.loading(false);     
      }
  },
  computed: {},
  watch: { 
    // txt_tracuu_tu: function(val){
    //   this.txt_tracuu_tu = this.numberWithCommas(val)
    // },
    // txt_tracuu_den: function(val){
    //   this.txt_tracuu_den = this.numberWithCommas(val)
    // }
  },
  computed: {      
      TINHCUOC_NGAY() {
          return this.dsThamSoMacDinh.some(thamSo => { return thamSo.ma_ts == 'TINHCUOC_NGAY' && thamSo.ten_ts == '1' }) ? 1 : 0
      },      
  },
  methods: {    
    customTongTien: function(data) {
        var tong_tien = 0;
        for(var item of data.result) {
          tong_tien += item.tong_tien*1 && !isNaN(item.tong_tien*1)?item.tong_tien*1:0;
        } 
        return tong_tien
    }, 
    customTienKM: function(data) {
        var tong_tien = 0;
        for(var item of data.result) {
          tong_tien += item.datcoc_csd*1 && !isNaN(item.datcoc_csd*1)?item.datcoc_csd*1:0;
        } 
        return tong_tien
    }, 
    customTienThoai: function(data) {
        var tong_tien = 0;
        for(var item of data.result) {
          tong_tien += item.tienthoai*1 && !isNaN(item.tienthoai*1)?item.tienthoai*1:0;
        } 
        return tong_tien
    }, 
    customTongThucTra: function(data) {
        var tong_tien = 0;
        for(var item of data.result) {
          tong_tien += item.thuctra*1 && !isNaN(item.thuctra*1)?item.thuctra*1:0;
        } 
        return tong_tien
    }, 
    async ma_tbKeyPress(){      
      this.ClearGUI()    
      try {   
        this.loading(true);       
        await api.sp_lay_tt_thuebao_tuvan_khuyennghi({"vma_tb":this.ma_tb}).then((response) => {          
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
            if (response.data.data.v_dstb_sudung && response.data.data.v_dstb_sudung.length > 0){
              this.tt_tb = response.data.data.v_dstb_sudung[0]
              this.tt_tb.giagoi = this.formatCurrency(this.tt_tb.giagoi?this.tt_tb.giagoi:0)
              this.tt_tb.tien_thanhtoan = this.formatCurrency(this.tt_tb.tien_thanhtoan?this.tt_tb.tien_thanhtoan:0)
              this.tt_tb.gia_fiber = this.formatCurrency(this.tt_tb.gia_fiber?this.tt_tb.gia_fiber:0)              
              if (this.tt_tb.thuebao_id) this.thuebao_id = this.tt_tb.thuebao_id
            }
            if (response.data.data.v_dstb_thamgiagoi && response.data.data.v_dstb_thamgiagoi.length > 0){
              this.gv_tb_goi.tableData = response.data.data.v_dstb_thamgiagoi
            }
            if (response.data.data.v_dstb_kh && response.data.data.v_dstb_kh.length > 0){
              this.gv_thuebao.tableData = response.data.data.v_dstb_kh
            }
          }
          else {
            this.$root.$toast.error('Không tìm thấy dữ liệu thuê bao!')
            this.loading(false);  
            return
          }
        });
		
		//kiểm tra thuê bao lẻ hay thuê bao gói
        if (this.thuebao_id && this.thuebao_id != 0){
          await api.sp_ds_tuvan_muathem_mytv({"thuebao_id":this.thuebao_id}).then((response) => {          
              if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
                this.gv_mytv.tableData = response.data.data  
              }
            });
            console.log(this.tt_tb, 'this.tt_tbthis.tt_tbthis.tt_tb')
          if (this.tt_tb.is_tb_le == 0){
            let updatedData = []
            await api.sp_ds_thuebao_chithamgia_goidadv_tuvan({"vthuebao_id":this.thuebao_id}).then((response) => {          
              if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
                // Calculate total sums for each goi_id
                const totals = response.data.data.reduce((acc, item) => {
                  if (!acc[item.goi_id]) {
                    acc[item.goi_id] = { tong_tien_goi: 0, tong_sosanh_tien: 0 };
                  }
                  acc[item.goi_id].tong_tien_goi += item.tien_goi;
                  acc[item.goi_id].tong_sosanh_tien += item.sosanh_tien;
                  return acc;
                }, {});

                // Update each item with total sums and modified ten_goi
                // updatedData = response.data.data.map(item => {
                //   const total = totals[item.goi_id];
                //   return {
                //     ...item,
                //     ten_goi: `${item.ten_goi} <b><span style="color: red;">Tổng tiền: ${total.tong_tien_goi.toLocaleString("vi-VN")}</span></b> <b><span style="color: red;">Tổng so sánh tiền: ${total.tong_sosanh_tien.toLocaleString("vi-VN")}</span></b>`,
                //     tien_goi: item.tien_goi.toLocaleString("vi-VN"),
                //     sosanh_tien: item.sosanh_tien.toLocaleString("vi-VN"),
                //     tong_tien_goi: total.tong_tien_goi,
                //     tong_sosanh_tien: total.tong_sosanh_tien
                //   };
                // });
                this.gv_goi_dadv.tableData = response.data.data
              }
            })
            .finally(() => {
              //this.gv_goi_dadv.tableData = updatedData
            });
            
          }
          else {
            await api.sp_tuvan_thuebao_le(
            {
              "vthuebao_id":this.thuebao_id,
              "vkieu":3,
              "vnum_bd":0,
              "vnum_kt":0
            }
            ).then((response) => {          
              if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
                this.gv_tocdo_muccuoc.tableData = response.data.data              
              }            
            });
          }
          if (this.tt_tb.is_khdn == 1){
            this.commands[0].text = ''
            this.commands1[0].text = ''
          }
          else {
            this.commands[0].text = '<a href="javascript:void(0);">Thay đổi</a>'
            this.commands1[0].text = '<a href="javascript:void(0);">Thay đổi</a>'
          }
        }

        await api.sp_thaydoi_tocdo_internet_hienthi({
          ma_tb: this.ma_tb,
          dichvuvt_id: 0 
        })
          .then((response) => {
            if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined && response.data.data.returnd_hopdong.length > 0) {
              var ds = response.data.data.returnd_hopdong
              this.HienThiTTHopDongKH(ds)
            }
          })
          .catch((error) => {
            console.log(error)
          })
          .finally(() => {
            this.loading(false)
          })
        this.loading(false);  
      } catch (error) {
        console.log(error)   
        this.loading(false);       
      }
      
    },
    async tao_hdKeyPress(emit_data){     
      console.log('tao_hdKeyPress') 
      console.log(emit_data) 
      //TODO: valid data   
      if (this.thuebao_id == 0) {
        this.$root.$toast.error('Vui lòng nhập thông tin thuê bao!')
        return
      }
      if (this.ctv_id == 0 || this.ctv_id == '') {
        this.$root.$toast.error('Vui lòng nhập thông tin cộng tác viên!')
        return
      }
      
      let tocdo_moi_id = -1
      let goi_id = -1        
      let item_dadv = null   
      let isDaDV = false  
      //let isDoiTD = false  
      let isKM = false 
      let ds_tb = []

      let check = false
      if (emit_data){
        if (emit_data.data_tratruoc && emit_data.data_tratruoc.length > 0 ){
          this.arr_selected_item_tratruoc = emit_data.data_tratruoc
          check = true
        }
        if (emit_data.data_doitd && emit_data.data_doitd.length > 0 ){
          this.arr_doitocdo = emit_data.data_doitd
          check = true
        }
        if (emit_data.data_goi && emit_data.data_goi.length > 0 ){
          this.arr_goi = emit_data.arr_goi
          check = true
        }
      }
      if (check == false) {
        this.$root.$toast.error('Chưa có thông tin lập hợp đồng!')
        return
      }
      if (this.gv_tb_goi.tableData && this.gv_tb_goi.tableData.length > 0){
        ds_tb = this.gv_tb_goi.tableData
      } else {
        ds_tb.push(this.tt_tb)
      }
      //kiểm tra thuê bao đang lập hợp đồng khác
      let kiemtra_lap_hd = await this.KIEMTRA_THUEBAO_HD(ds_tb)
      if (kiemtra_lap_hd != ''){
        this.$root.$toast.error('Thuê bao trong danh sách đang được lập hợp đồng theo mã giao dịch: ' +kiemtra_lap_hd)
        return
      }
      
      //insert data
      try {   
        this.loading(true); 
        
        console.log(this.selected_item_chithamgia_goi)  
        console.log(this.selected_item_tocdo_muccuoc)  
        console.log(this.selected_item_mytv)  
        if (this.selected_item_chithamgia_goi)  {
          item_dadv = this.selected_item_chithamgia_goi         
          isDaDV = true 
        } else if (this.selected_item_tocdo_muccuoc ){
          item_dadv = this.selected_item_tocdo_muccuoc
          this.isDoiTD = true
        } else if (this.selected_item_mytv){
          item_dadv = this.selected_item_mytv
        }
        console.log(item_dadv)  
        if (item_dadv) {
          tocdo_moi_id = item_dadv.tocdo_id
          goi_id = item_dadv.goi_id
        }
        else {
          this.$root.$toast.error('Bạn chưa chọn gói đa dịch vụ nào!')
          this.loading(false); 
          return
        }       

        let p_DS_TB_HOPDONG = await this.TaoDuLieu_TB_HOPDONG(ds_tb, item_dadv, isDaDV)                        
        let p_DS_DATCOC_HDTB = []        
        let item_km = null   
        if (this.arr_selected_item_tratruoc.length > 0)  {
          item_km = this.arr_selected_item_tratruoc[0]             
        }   
        // if (this.selected_item_chithamgia_goi_tratruoc)  {
        //   item_km = this.selected_item_chithamgia_goi_tratruoc            
        // } else if (this.selected_item_tocdo_muccuoc_tratruoc){
        //   item_km = this.selected_item_tocdo_muccuoc_tratruoc
        // } else if (this.selected_item_mytv_tratruoc){
        //   item_km = this.selected_item_mytv_tratruoc
        // }
        
        if (this.arr_selected_item_tratruoc && this.arr_selected_item_tratruoc.length > 0){    
          isKM = true      
          p_DS_DATCOC_HDTB = await this.TaoDuLieu_DATCOC(ds_tb)          
        }
        let p_DS_LOAIIP = [] // null
        let p_DS_CT_TIENHD = await this.TaoDuLieu_CT_TIENHD(true,isKM,isDaDV) 
        let p_DS_DANGKY_DVGT = [] //null
        let p_DS_HD_GOI_DADV = await this.TaoDuLieu_GOI_DADV(ds_tb, goi_id, item_km?item_km.sothang:0)         
        
        let input = {
          DONVI_ID:this.$root.token.getDonViID(),
          TUCTHI:2,
          CTV_ID: this.ctv_id,
          NGUOIGT_ID: this.filterbox.nguoigt.nguoigt_id,          
          LOAIHD_ID:this.isDoiTD? 8:27,
          DS_TB_HOPDONG: p_DS_TB_HOPDONG,
          DS_DATCOC_HDTB:p_DS_DATCOC_HDTB,
          DS_LOAIIP:p_DS_LOAIIP,
          DS_CT_TIENHD:p_DS_CT_TIENHD,
          DS_DANGKY_DVGT:p_DS_DANGKY_DVGT,
          DS_HD_GOI_DADV:p_DS_HD_GOI_DADV
        }
        console.log(input)
        //todo: comment hàm gọi hd
        await api.fn_lap_hd_tocdo_quyhoach({"ds_para":JSON.stringify(input)}).then((response) => {          
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
            this.ma_gd = response.data.data.MA_GD            
            this.$toast.success('Tạo hợp đồng thành công')
          }
          else {
            this.$root.$toast.error('Lỗi tạo hợp đồng!')
            this.loading(false);  
            return
          }
        });
        this.loading(false);  
        if (this.ma_gd != ''){
          await this.lay_ds_hopdong_theo_ma_gd(this.ma_gd,0)
          // await this.LAY_DS_THUEBAO_DATCOC_THEOGD(this.ma_gd)
        }
		
        
      } catch (error) {
        console.log(error)   
        this.loading(false);       
      }
      
    },    
    async HienThiTTHopDongKH(ds) {
      this.loading(true)
      try {
        if (ds.length > 0) {
          this.ma_gd = ds[0]['ma_gd']
          this.hdkh_id = ds[0]['hdkh_id']
          this.tt_kh = ds[0]
          this.hdkh_id = ds[0]['hdkh_id']
          //this.txtMaGD = ds[0]['ma_gd']
          this.txtMaHD = ds[0]['ma_hd']
          this.txtMaKH = ds[0]['ma_kh']
          this.khachhang_id = ds[0]['khachhang_id'] ? ds[0]['khachhang_id'] : 0
          this.txtTenKH = ds[0]['ten_kh']
          this.txtDiaChiKH = ds[0]['diachi_kh']
          this.dtpNgayLapHD = ds[0]['ngaylap_hd']
          this.dtpNgayYeuCau = ds[0]['ngay_yc']
          this.txtNgaySN = ds[0]['ngay_sn'] ? ds[0]['ngay_sn'] : ''
          this.chkTucThi.checked = true //todo
          if (ds[0]['ctv_id']) {
            await api.fn_lay_thongtin_nhanvien( {
              nhanvien_id: ds[0]['ctv_id']
            }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.filterbox.ctv.value = response.data.data
                this.filterbox.ctv.ctv_id = ds[0]['ctv_id']
                this.ctv_id = ds[0]['ctv_id']
                this.filterbox.ctv.ischecked = true
              }
            })
          } else {
            this.filterbox.ctv.value = ''
            this.filterbox.ctv.ma_ctv = ''
            this.filterbox.ctv.ctv_id = 0
            this.filterbox.ctv.ischecked = false
          }
          if (ds[0]['nhanviengt_id']) {
            await api.fn_lay_thongtin_nhanvien({
              nhanvien_id: ds[0]['nhanviengt_id']
            }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                this.filterbox.nguoigt.nguoigt_id = ds[0]['nhanviengt_id']
                this.filterbox.nguoigt.value = response.data.data
                this.filterbox.nguoigt.ischecked = true
              }
            })
          } else {
            this.filterbox.nguoigt.nguoigt_id = 0
            this.filterbox.nguoigt.value = ''
            this.filterbox.nguoigt.ischecked = false
          }
          
        }
      } catch (ex) {        
      }
    },
    async LAY_DS_THUEBAO_DATCOC_THEOGD (ma_gd) {
      try {
        let postData = {
            "opt" : 0,
            "ma_gd" : ma_gd,
            "loai_hd" : 31,
            "kieuld_id": 550
          }
        let data = {
          p_data: JSON.stringify(postData)
        }
        let rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_thuebao_datcoc', data
        )

        console.log(rps)

        if (rps.data && rps.data.length > 0) {
          return rps.data
        } else { 
          return []
        }
        
      } catch (error) {
        console.log(error)
        return []
      }
    },
    async lay_tt_km_tratruoc_hd_khachhang (hdkh_id) {
      try {
        let postData = {
            "p_hdkh_id" : hdkh_id
          }
        
        let rps = await this.$root.context.post(
          '/web-tracuu/tracuu/lay_tt_km_tratruoc_hd_khachhang', postData
        )

        

        if (rps.data && rps.data.length > 0) {
          rps.data.forEach((item, index) => item.thuctra = item.datcoc_csd + item.tienthoai)
          console.log(rps.data)
          return rps.data
        } else { 
          return []
        }
        
      } catch (error) {
        console.log(error)
        return []
      }
    },
    async LAY_DS_THUEBAO_DATCOC (ma_tb) {
      try {
        let postData = {
            "opt" : 1,
            "ma_tb" : ma_tb,
            "loai_hd" : 31,
            "kieuld_id": 550
          }
        let data = {
          p_data: JSON.stringify(postData)
        }
        let rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_thuebao_datcoc', data
        )

        console.log(rps)

        if (rps.data && rps.data.length > 0) {
          return this.toUpperCases(rps.data)
        } else { 
          return []
        }
        
      } catch (error) {
        console.log(error)
        return []
      }
    },
    async TaoDuLieu_TB_HOPDONG(ds_tb, item_dadv, isDaDV) {      
      let dsTB_HOPDONG = []
      var rowTB_HOPDONG = {}
      if (isDaDV){ 
        for (var i = 0; i < ds_tb.length; i++) {
          rowTB_HOPDONG = {}
          var ls_item_tb = this.gv_thuebao.tableData.filter(item => item.thuebao_id == ds_tb[i].thuebao_id)
          var loaitb_id = 58
          if (ls_item_tb && ls_item_tb.length > 0){
            loaitb_id = ls_item_tb[0].loaitb_id
          }          
          var ls_item_dadv = this.gv_goi_dadv.tableData.filter(item => item.goi_id == item_dadv.goi_id && item.loaitb_id == ds_tb[i].loaitb_id)          
          if (ls_item_dadv && ls_item_dadv.length > 0){
            if (ds_tb[i].tocdo_id != ls_item_dadv[0].tocdo_id){
              this.isDoiTD = true
              rowTB_HOPDONG = {
                "THUEBAO_ID":ds_tb[i].thuebao_id,
                "TOCDO_CU_ID":ds_tb[i].tocdo_id?ds_tb[i].tocdo_id:-1,
                "TOCDO_MOI_ID":ls_item_dadv[0].tocdo_id,
                "MUCCUOC_ID":ls_item_dadv[0].muccuoc_id?ls_item_dadv[0].muccuoc_id:0,
                "GOI_ID":ls_item_dadv[0].goi_id,
                "KIEULD_ID":24,
                "LOAIHD_ID":8,
                "LOAITB_ID":loaitb_id,
                "GOI_ID_CU":ds_tb[i].goi_id,
                "NHOMTB_ID_CU":ds_tb[i].nhomtb_id
              }
              dsTB_HOPDONG.push(rowTB_HOPDONG)
            } else {              
              rowTB_HOPDONG = {
                "THUEBAO_ID":ds_tb[i].thuebao_id,
                "TOCDO_CU_ID":ds_tb[i].tocdo_id?ds_tb[i].tocdo_id:-1,
                "TOCDO_MOI_ID":-1,
                "MUCCUOC_ID":-1,
                "GOI_ID":ls_item_dadv[0].goi_id,                
                "KIEULD_ID":280,
                "LOAIHD_ID":27,
                "LOAITB_ID":loaitb_id,
                "GOI_ID_CU":ds_tb[i].goi_id,
                "NHOMTB_ID_CU":ds_tb[i].nhomtb_id 
              }
              dsTB_HOPDONG.push(rowTB_HOPDONG)
            }
            
          }          
        }
      } else {
        var ls_item_tb = this.gv_thuebao.tableData.filter(item => item.thuebao_id == this.thuebao_id)
        var loaitb_id = 58
        if (ls_item_tb && ls_item_tb.length > 0){
          loaitb_id = ls_item_tb[0].loaitb_id
        }
        rowTB_HOPDONG = {
          "THUEBAO_ID":this.thuebao_id,
          "TOCDO_CU_ID":this.tt_tb.tocdo_id?this.tt_tb.tocdo_id:0,
          "TOCDO_MOI_ID":item_dadv.tocdo_id,
          "MUCCUOC_ID":item_dadv.muccuoc_id?item_dadv.muccuoc_id:0,
          "GOI_ID":item_dadv.goi_id?item_dadv.goi_id:-1,          
          "KIEULD_ID":24,
          "LOAIHD_ID":8,
          "LOAITB_ID":loaitb_id
        }
        dsTB_HOPDONG.push(rowTB_HOPDONG)
      }
      return dsTB_HOPDONG
      
    },
    async TaoDuLieu_CT_TIENHD(themmoi,isKM,isDaDV) {
      if (this.ds_tien_khoanmuc.length < 1) {
        this.ds_tien_khoanmuc = [
          { khoanmuctt_id: 3, tentat_kmtt: 'Thay Đổi Tốc Độ', tongtien: 0, tien: '0', vat: '0', flag: 1 },
          { khoanmuctt_id: 11, tentat_kmtt: 'Trả Trước', tongtien: 0, tien: '0', vat: '0', flag: 0 }
        ]
      }
      let dsCTTHD = []
      var rowCTTHD = {}
      for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        var rowCTTHD = {}        
        rowCTTHD.CT_TIENHD_ID = 0
        if (themmoi) {
          await api.post_get_keys({ keyname: 'CT_TIENHD' }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {              
              rowCTTHD.CT_TIENHD_ID = response.data.data
            }
          })
        }
        rowCTTHD.THUEBAO_ID = this.thuebao_id
        rowCTTHD.KHOANMUCTT_ID = Number(this.ds_tien_khoanmuc[i]['khoanmuctt_id'])
        rowCTTHD.TIEN = Math.round(this.ds_tien_khoanmuc[i]['tien'])
        rowCTTHD.VAT = Math.round(this.ds_tien_khoanmuc[i]['vat'])
        
        rowCTTHD.ID = this.ds_tien_khoanmuc[i]['khoanmuctt_id']
        rowCTTHD.LOAI_ID = 1
        dsCTTHD.push(rowCTTHD)
      }
      if (isKM) {        
        for (var j = 0; j < this.arr_selected_item_tratruoc.length; j++) {
          var rowCTTHD = {}            
          rowCTTHD.CT_TIENHD_ID = 0
          if (themmoi) {
            await api.post_get_keys({ keyname: 'CT_TIENHD' }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                rowCTTHD.CT_TIENHD_ID = response.data.data
              }
            })
          }
          rowCTTHD.THUEBAO_ID = this.arr_selected_item_tratruoc[j].thuebao_id?this.arr_selected_item_tratruoc[j].thuebao_id:this.thuebao_id 
          rowCTTHD.KHOANMUCTT_ID = 11

          var tien
          var vat
          tien = (Math.round(parseFloat(this.arr_selected_item_tratruoc[j].tien_tratruoc) / 1.1))
          vat = (parseFloat(this.arr_selected_item_tratruoc[j].tien_tratruoc) - tien)
          rowCTTHD.TIEN =  tien
          rowCTTHD.VAT = vat
          rowCTTHD.ID = 11
          rowCTTHD.LOAI_ID = 1
          dsCTTHD.push(rowCTTHD)

          // item thoái
          if (this.arr_selected_item_tratruoc[j].tien_thoai && this.arr_selected_item_tratruoc[j].tien_thoai > 0){
            
            rowCTTHD = {}            
            rowCTTHD.CT_TIENHD_ID = 0
            if (themmoi) {
              await api.post_get_keys({ keyname: 'CT_TIENHD' }).then((response) => {
                if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {                    
                  rowCTTHD.CT_TIENHD_ID = response.data.data
                }
              })
            }
            rowCTTHD.THUEBAO_ID = this.arr_selected_item_tratruoc[j].thuebao_id?this.arr_selected_item_tratruoc[j].thuebao_id:this.thuebao_id 
            rowCTTHD.KHOANMUCTT_ID = 11
            var tien_thoai = Math.round(this.arr_selected_item_tratruoc[j].tien_thoai / 1.1)
            let vat_thoai = parseFloat(this.arr_selected_item_tratruoc[j].tien_thoai) - tien_thoai;
            rowCTTHD.TIEN =  -tien_thoai
            rowCTTHD.VAT = -vat_thoai
            rowCTTHD.ID = 11
            rowCTTHD.LOAI_ID = 1
            dsCTTHD.push(rowCTTHD)
          }
        }        
      } else if (isDaDV){ //nếu ko đăng ký khuyến mãi nhưng trước đó vẫn có thông tin cọc đa dv thì phải gửi vào tiền thoái
        for (var i = 0; i < this.arr_goi.length; i++) {
          var rowCTTHD = {}        
          rowCTTHD.CT_TIENHD_ID = 0
          if (themmoi) {
            await api.post_get_keys({ keyname: 'CT_TIENHD' }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {              
                rowCTTHD.CT_TIENHD_ID = response.data.data
              }
            })
          }
          rowCTTHD.THUEBAO_ID = this.arr_goi[i].thuebao_id          
          rowCTTHD.KHOANMUCTT_ID = 11
          var tien_thoai = Math.round(this.arr_goi[i].tien_thoai / 1.1)
          let vat_thoai = parseFloat(this.arr_goi[i].tien_thoai) - tien_thoai;
          rowCTTHD.TIEN =  -tien_thoai
          rowCTTHD.VAT = -vat_thoai
          rowCTTHD.ID = 11
          rowCTTHD.LOAI_ID = 1
          dsCTTHD.push(rowCTTHD)
        }
      } else { //nếu ko đăng ký khuyến mãi nhưng trước đó vẫn có thông tin cọc thì phải gửi vào tiền thoái
        for (var i = 0; i < this.arr_doitocdo.length; i++) {
          var rowCTTHD = {}        
          rowCTTHD.CT_TIENHD_ID = 0
          if (themmoi) {
            await api.post_get_keys({ keyname: 'CT_TIENHD' }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {              
                rowCTTHD.CT_TIENHD_ID = response.data.data
              }
            })
          }
          rowCTTHD.THUEBAO_ID = this.arr_doitocdo[i].thuebao_id          
          rowCTTHD.KHOANMUCTT_ID = 11
          var tien_thoai = Math.round(this.arr_doitocdo[i].tien_thoai / 1.1)
          let vat_thoai = parseFloat(this.arr_doitocdo[i].tien_thoai) - tien_thoai;
          rowCTTHD.TIEN =  -tien_thoai
          rowCTTHD.VAT = -vat_thoai
          rowCTTHD.ID = 11
          rowCTTHD.LOAI_ID = 1
          dsCTTHD.push(rowCTTHD)
        }
      }
      return dsCTTHD      
    },
    async TaoDuLieu_GOI_DADV(ds_tb, goi_id, thang_huong) {      
      let dsGOI_DADV = []
      var rowDADV = {}
      var nhomtbid = -1
      await api.post_get_keys({ keyname: 'NHOMTB' }).then((response) => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {            
          nhomtbid = response.data.data
        }
      })
      for (var i = 0; i < ds_tb.length; i++) {
        rowDADV = {}        
        rowDADV.THUEBAO_ID = ds_tb[i].thuebao_id
        rowDADV.CHUNHOM = 0
        if (ds_tb[i].thuebao_id == this.thuebao_id){
          rowDADV.CHUNHOM = 1
        }
        
        rowDADV.NHOMTB_ID = nhomtbid
        rowDADV.THANG_HUONG = thang_huong
        rowDADV.KIEU_YC = 280
        rowDADV.GOI_ID = goi_id
        rowDADV.LOAITB_ID = ds_tb[i].loaitb_id

        dsGOI_DADV.push(rowDADV)
      }
      return dsGOI_DADV
      
    },    
    async TaoDuLieu_DATCOC(ds_tb) {      
      let dsDATCOC = []
      var rowDATCOC = {}
      var date = new Date();      
      if (ds_tb && ds_tb.length > 0){
        for (var i = 0; i < ds_tb.length; i++) {
          var ls_item_km = this.arr_selected_item_tratruoc.filter(x => x.loaitb_id == ds_tb[i].loaitb_id && x.thuebao_id == ds_tb[i].thuebao_id)
          if (ls_item_km && ls_item_km.length > 0){
            var item_km = ls_item_km[0]
            var date1 = new Date();
            var endDate = new Date(date1.setMonth(date1.getMonth() + item_km.sothang));
            rowDATCOC = {
                "KHUYENMAI_ID":item_km.khuyenmai_id,
                "CHITIETKM_ID":item_km.chitietkm_id,
                "KIEU_YC":550,
                "THANG_HUONGDC":item_km.sothang,
                "THANG_HUONGKM":item_km.sothang,
                "SOTHANG_TG":item_km.sothang_tang,
                "DATCOC_CSD":item_km.datcoc_csd,
                "TIEN_TD":item_km.tien_td,
                "TIEN_SD":item_km.tien_sd,
                "TIEN_TB":item_km.tien_tb,
                "CUOC_SD":item_km.cuoc_sd,
                "CUOC_TB":item_km.cuoc_sd,
                "TYLE_SD":item_km.tyle_sd,
                "TYLE_TB":item_km.tyle_tb,
                "THANG_BD":item_km.thang_bddc,
                "THANG_KT":item_km.thang_ktdc,
                "THANG_BDDC":item_km.thang_bddc,
                "THANG_KTDC":item_km.thang_ktdc,
                "TYLE_KMLD":0,
                "NGAY_BD":item_km.ngay_bddc,
                "NGAY_BDDC":item_km.ngay_bddc,
                "NGAY_KT":item_km.ngay_ktdc,
                "NGAY_KTDC":item_km.ngay_ktdc,
                "NHOM_KM":item_km.nhom_km,
                "TYLE_VAT":item_km.tyle_vat,
                "HDTB_ID":null,
                "THUEBAO_ID":ds_tb[i].thuebao_id,
                "THUEBAO_DC_ID":null
            }
            dsDATCOC.push(rowDATCOC)
          }
          
        }
      }      
      return dsDATCOC
      
    },
    async KIEMTRA_THUEBAO_HD(ds_tb) {
      try {
        for (var i = 0; i < ds_tb.length; i++){
          var data ={
            "dichVuVtId": ds_tb[i].dichvuvt_id?ds_tb[i].dichvuvt_id:4,
            "loaiHdId": 8,
            "maGd": "0",
            "maTb": ds_tb[i].ma_tb
          }
          let response = await this.$root.context.post(`/web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac`, data)
          if (response.error_code === 'BSS-00000000' && response.data != null) {                
              return response.data
          }            
        }
        return ''
      } catch (err) {
          return ''
      }
    },
    async LAY_DS_THAMSO_MD() {
        try {
            let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_ds_thamso_md`)
            this.dsThamSoMacDinh = !response.data ? [] : response.data
        } catch (err) {
            this.dsThamSoMacDinh = []
        }
    },
    async Lay_tienthoai_datcoc_v4(data) {
        try {            
            let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_tienthoai_datcoc_v4`, data)

            if (response.error_code === 'BSS-00000000' && response.data != null) {                
                console.log(response.data.TIENTHOAI)
                return response.data.TIENTHOAI
            }            
            throw { response: { data: response } }
        } catch (err) {
            return null
        }
    },
    async lay_ds_hopdong_theo_ma_gd(ma_gd, tthd_id){   
      try {
        this.loading(true);         
        let input = {
          in_ma_gd: ma_gd,
          in_loaihd_id: 8,
          in_tthd_id: tthd_id,
          in_donvi_dl_id: 0
        }
                
        await api.lay_ds_hopdong_theo_ma_gd(input).then((response) => {          
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
            var ds = response.data.data
            this.ds_hdtb = response.data.data
            this.HienThiTTHopDongKH(ds)            
          }
        })
        if (this.hdkh_id != 0) {
          await api.lay_ds_hd_tb_theo_hdkh_id_v3({hdkh_id: this.hdkh_id}).then((response) => {          
            if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.ds_thuebao.list = response.data.data              
            } else {
              this.listbox.ds_thuebao.list = []
            }
          })
          //await this.layDsHdtbTheoHdkh(this.hdkh_id)
          this.dsHdtb = await this.lay_tt_km_tratruoc_hd_khachhang(this.hdkh_id)

          await this.layTienHDTheoKHID(this.hdkh_id)
        }
        this.loading(false);
      } catch (error) {
        console.log(error)    
        this.loading(false);     
      }
    },
    async layDsHdtbTheoHdkh (hdkhId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCoc/lay_ds_hdtb_theo_hdkh?hdkh_id=${hdkhId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          let rootKey = this.ddmmyyyyhhmiss(new Date())
          response.data.forEach((item, index) => item.KEY = `${rootKey}${index + 1}`)
          console.log(response.data)
          this.dsHdtb = response.data
          return
        }

        throw {response: { data: response}}
      } catch (err) {
        //this.dsHdtb = []
      }
    },
    async layTienHDTheoKHID (hdkhId) {
      try {
        this.gridTienHopDong.list = []
        let input = {
          "p_hdkh_id": hdkhId
        }                
        await api.sp_lay_tien_hd_theo_hdkh_id(input).then((response) => {          
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {                            
            
            let tongtien = 0
            $.each(response.data.data, function (key, item) {
              tongtien = item.tong_tien + tongtien              
            });  
            this.txtTongTienHD = tongtien          
            this.gridTienHopDong.list = response.data.data  
          }
        })        
      } catch (err) {        
      }
    },
    getLichSuThongtinTB: async function (value,kieuld_id) {
      try {
        this.ds_thongtin_thaydoi.list = [];
        let input = {p_ds_para: JSON.stringify({HDTB_ID: value})}        
        console.log('getLichSuThongtinTB')
        console.log(input)
        let loai = ''
        let data = null
        await api.getLichSuThongtinTB(input).then((response) => {          
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
            if (kieuld_id == 24 && response.data.data.DS_BD_TOCDO) {
              data = response.data.data.DS_BD_TOCDO   
              loai = 'đổi tốc độ'   
            } else if (kieuld_id == 281 && response.data.data.DS_BD_HUY) {
              data = response.data.data.DS_BD_HUY  
              loai = 'hủy gói'      
            } else if (kieuld_id == 280 && response.data.data.DS_BD_GOIDADV) {
              data = response.data.data.DS_BD_GOIDADV    
              loai = 'đổi gói'    
            }                 
          }
        })
        if (data.length > 0) {          
          if (data.length == 2) {
            for (var i = 0; i < Object.keys(data[0]).length - 1; i++) {
              var tmp = {
                loai: "1-Thông tin" + loai,
                ten_ht: Object.keys(data[0])[i].toString().toLowerCase(),
                giatricu: data[0][Object.keys(data[0])[i]]
                  ? data[0][Object.keys(data[0])[i]].toString()
                  : "",
                giatrimoi: data[1][Object.keys(data[1])[i]]
                  ? data[1][Object.keys(data[1])[i]].toString()
                  : "",
              };
              this.ds_thongtin_thaydoi.list.push(tmp);
            }
          }
          if (data.length == 1) {
            for (var i = 0; i < Object.keys(data[0]).length - 1; i++) {
              var tmp = {
                loai: "1-Thông tin " + loai,
                ten_ht: Object.keys(data[0])[i].toString().toLowerCase(),
                giatricu: data[0][Object.keys(data[0])[i]]
                  ? data[0][Object.keys(data[0])[i]].toString()
                  : "",
                giatrimoi: "",
              };
              this.ds_thongtin_thaydoi.list.push(tmp);
            }
          }
        }
      } catch (e) {
        if (e.data && e.data.message_detail)
          this.$toast.error(
            "Lỗi tìm kiếm lịch sử thông tin thuê bao" + e.data.message_detail
          );
        else this.$toast.error("Lỗi tìm kiếm lịch sử thông tin thuê bao" + e);
      }
    },
    async gv_goi_dadv_selected(item){      
      console.log('gv_goi_dadv_selected:',item) 
      this.selected_item_chithamgia_goi = item.data
      this.gv_tratruoc_km.tableData = []
      try {
        this.loading(true);  
        if (item.data && item.data.goi_id){  
          this.arr_goi = []
          this.arr_goi = this.gv_goi_dadv.tableData.filter(x => x.goi_id == item.data.goi_id)  
          
          this.arr_goi.forEach((item_i, index) => {
            var item_tb = this.gv_tb_goi.tableData.filter(x => x.loaitb_id == item_i.loaitb_id && x.ma_tb != item_i.ma_tb) 
            if (item_tb && item_tb.length){
              item_i.ma_tb = item_tb[0].ma_tb
            }
            
          })
          console.log('this.arr_goi')
          console.log(this.arr_goi)
          let input = {
            vthuebao_id:this.thuebao_id,
            vkieu:1,
            vds:JSON.stringify({
              goi_id:item.data.goi_id,
              tocdo_id:item.data.tocdo_id
            })
          }        
          await api.sp_ds_km_theo_goi_id_tuvan(input).then((response) => {          
            if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
              this.gv_tratruoc_km.tableData = response.data.data
              // for (let i = 0; i < this.gv_tratruoc_km.tableData.length; i++){
              //   this.gv_tratruoc_km.tableData[i].ten_ctkm = this.gv_tratruoc_km.tableData[i].chitietkm_id + '-' + this.gv_tratruoc_km.tableData[i].ten_ctkm
              // }
            }
          })
        }
        this.loading(false);
      } catch (error) {
        console.log(error)    
        this.loading(false);     
      }
    },
    async gv_goi_dadv_tratruoc_selected(item){     
      //tam bo ham
      return
      console.log('gv_goi_dadv_tratruoc_selected:',item) 
      this.selected_item_chithamgia_goi_tratruoc = item.data      
      if (this.selected_item_chithamgia_goi){
        this.selected_item_chithamgia_goi_tratruoc.loaitb_id = this.selected_item_chithamgia_goi.loaitb_id?this.selected_item_chithamgia_goi.loaitb_id:58 //TODO
      }
      this.arr_selected_item_tratruoc.push(this.selected_item_chithamgia_goi_tratruoc)
      console.log(this.arr_selected_item_tratruoc)
    },
    async gv_mytv_selected(item){      
      console.log('gv_mytv_selected:',item) 
      this.selected_item_mytv = item.data
      this.gv_tratruoc_km_mytv.tableData = []
      try {
        this.loading(true);  
        if (item.data && item.data.goi_id){  
          let input = {
            vthuebao_id:this.thuebao_id,
            vkieu:1,
            vds:JSON.stringify({
              goi_id:item.data.goi_id,
              tocdo_id:item.data.tocdo_id
            })
          }        
          await api.sp_ds_km_theo_goi_id_tuvan(input).then((response) => {          
            if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
              this.gv_tratruoc_km_mytv.tableData = response.data.data              
            }
          })
        }
        this.loading(false);
      } catch (error) {
        console.log(error)    
        this.loading(false);     
      }
    },    
    async gv_mytv_tratruoc_selected(item){      
      //tam bo ham
      return
      console.log('gv_mytv_tratruoc_selected:',item) 
      this.selected_item_mytv_tratruoc = item.data
      if (this.selected_item_mytv){
        this.selected_item_mytv_tratruoc.loaitb_id = this.selected_item_mytv.loaitb_id?this.selected_item_mytv.loaitb_id:58 //TODO
      }
      this.arr_selected_item_tratruoc.push(this.selected_item_mytv_tratruoc)
      console.log(this.arr_selected_item_tratruoc)
    },
    async gv_tocdo_muccuoc_selected(item){      
      console.log('gv_tocdo_muccuoc_selected:',item) 
      this.selected_item_tocdo_muccuoc = item.data
      this.arr_doitocdo = []
      let item_td = this.tt_tb      
      item_td.tocdo_id_cu = this.tt_tb.tocdo_id
      item_td.tocdo_id_moi = item.data.tocdo_id
      item_td.tocdo_moi = item.data.tocdo
      item_td.loaihinh_tb = item.data.loaitb_id == 58? 'Fiber':'MyTv'
      this.arr_doitocdo.push(item_td)
      this.gv_tratruoc_km1.tableData = []
      try {
        this.loading(true);  
          let input = {
            vthuebao_id:this.thuebao_id,
            vkieu:0,
            vds:JSON.stringify({
              goi_id:item.data.goi_id,
              tocdo_id:item.data.tocdo_id
            })
          }        
          //{"vgoi_id":15538}    
          await api.sp_ds_km_theo_goi_id_tuvan(input).then((response) => {          
            if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
              this.gv_tratruoc_km1.tableData = response.data.data
      
            }
          })
        
        this.loading(false);
      } catch (error) {
        console.log(error)    
        this.loading(false);     
      }
    },
    async gv_tocdo_muccuoc_tratruoc_selected(item){   
      //tam bo ham
      return
      console.log('gv_tocdo_muccuoc_tratruoc_selected:',item)    
      console.log(this.arr_selected_item_tratruoc)
      this.selected_item_tocdo_muccuoc_tratruoc = item.data
      if (this.selected_item_tocdo_muccuoc){
        this.selected_item_tocdo_muccuoc_tratruoc.loaitb_id = this.selected_item_tocdo_muccuoc.loaitb_id?this.selected_item_tocdo_muccuoc.loaitb_id:58 //TODO
      }
      this.arr_selected_item_tratruoc.push(this.selected_item_tocdo_muccuoc_tratruoc)
      console.log(this.arr_selected_item_tratruoc)
    },
    async btnXem_Click(loai){            
      if (loai ==0 && parseInt(this.txt_tracuu_den) < parseInt(this.txt_tracuu_tu)){
        this.$root.$toast.error('Giá trị đến phải lớn hơn hoặc bằng giá trị từ')
        return 
      }
      this.gv_tocdo_muccuoc.tableData = []
      try {    
        this.loading(true);  
        await api.sp_tuvan_thuebao_le(
          {
            "vthuebao_id":this.thuebao_id,
            "vkieu":loai == 0?this.radio_kieu:3,
            "vnum_bd":this.removeComma(this.txt_tracuu_tu),
            "vnum_kt":this.removeComma(this.txt_tracuu_den)
          }
        ).then((response) => {          
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
            this.gv_tocdo_muccuoc.tableData = response.data.data            
          }
          else {
            this.$root.$toast.error('Không tìm thấy dữ liệu!')
          }
          this.loading(false); 
        });
      } catch (error) {
        console.log(error)    
        this.loading(false);     
      }
    }, 
    async ClickThayDoiGoi(){ 
      if (this.gv_tratruoc_km.tableData.length > 0){
        this.$root.$toast.error('Thuê bao đang tham gia trả trước chưa hết hạn')
      } else {
        this.$bvModal
        .msgBoxConfirm(`Bạn có đồng ý đổi gói?`, {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          if (this.gv_goi_dadv.tableData.length > 0) {
            let ds_rg_id = this.gv_goi_dadv.tableData.map(item => item.rg_id);
            ds_rg_id = ds_rg_id.map(id => ({ "RG_ID": id }));
              let data={
              "thuebao_id": this.thuebao_id,
              "ds_rg_id": JSON.stringify(ds_rg_id)
            }
            await api.hoan_thien_sau_tu_van(data).then((response) => {          
              if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
                this.$toast.success('Thay đổi gói đa dịch vụ thành công')
              } else {
                this.$toast.error(response.data.message)
                return
              }
            });
          } 
        })
      }
    },     
    async onXemTraTruoc()
    {
      try {     
        console.log('Xem trả trước')   
        console.log(this.arr_selected_item_tratruoc)   
        await this.$refs.frmXemTraTruoc.openDialog(this.arr_selected_item_tratruoc,this.arr_goi,this.arr_doitocdo)
      }
      catch (e)
      {
        console.log(e);
      }
    },  
    goTraTruoc: function (data) {
      console.log('gotratruoc')
      console.log(data)
      if (data != null) {
        this.arr_selected_item_tratruoc = data
      }
    },  
    async onActionClick (action) {
      if (action.id === 'search') {
        this.ma_tbKeyPress()
      } else if (action.id === 'taohd') {                        
        await this.$refs.frmXemTraTruoc.openDialog(this.arr_selected_item_tratruoc,this.arr_goi,this.arr_doitocdo)        
      } else if (action.id === 'dongbo') {
        this.btnDongBoClick()
      } else if (action.id === 'xoahd') {
        this.clickXoaHD()
      } else if (action.id === 'thanhtoan') {
        this.tsbtnThanhToan_Click()
      } else if (action.id === 'hoanthien') {
        this.tsbtnHoanThien_Click()
      }
    },
    commandClicked: async function (cmdName, data) {
      console.log('commandClicked1', 'cmdName:', cmdName, 'data:', data)      
      if (cmdName == 'thaotac'){
        let routeData = this.$router.resolve(`/contract/changeinternetspeed?ma_tb=${this.ma_tb}&tocdo_id=${data.tocdo_id}&mucuoctb_id=${data.mucuoctb_id}`)
        window.open(routeData.href, '_blank')
      } else if (cmdName == 'thaotac1'){        
        window.open('https://onebss.vnpt.vn/#/contract/changeinternetspeed?ma_tb='+this.ma_tb, '_blank')
      }
      else if (cmdName == 'commands_km'){        
        console.log('click trả trước lưới km')
        console.log(data)        
        this.selected_item_chithamgia_goi_tratruoc = data      
        let index = this.arr_selected_item_tratruoc.findIndex((x) => x.chitietkm_id == data.chitietkm_id);
        if (index >= 0){
          this.$toast.error('Khuyến mãi đã được chọn trong danh sách!')
          return
        }
        index = this.arr_selected_item_tratruoc.findIndex((x) => x.sothang != data.sothang);
        if (index >= 0){
          this.$toast.error('Chu kỳ trả trước phải bằng với chu kỳ của trả trước khác đã được chọn trong danh sách!')
          return
        }
        if (this.selected_item_chithamgia_goi){
          this.selected_item_chithamgia_goi_tratruoc.loaitb_id = this.selected_item_chithamgia_goi.loaitb_id?this.selected_item_chithamgia_goi.loaitb_id:58 //TODO
          //join thêm thông tin thuê bao
          var ls_item_tb = this.gv_tb_goi.tableData.filter(item => item.loaitb_id == this.selected_item_chithamgia_goi.loaitb_id) // TODO: sau lưới có thuebao_id thì join qua luôn
          
          if (ls_item_tb && ls_item_tb.length > 0){
            this.selected_item_chithamgia_goi_tratruoc.thuebao_id = ls_item_tb[0].thuebao_id
            this.selected_item_chithamgia_goi_tratruoc.ma_tb = ls_item_tb[0].ma_tb
            this.selected_item_chithamgia_goi_tratruoc.ten_tb = ls_item_tb[0].ten_tb
            this.selected_item_chithamgia_goi_tratruoc.loaihinh_tb = ls_item_tb[0].loaihinh_tb            
            this.selected_item_chithamgia_goi_tratruoc.tocdo_id_cu = ls_item_tb[0].tocdo_id
            this.selected_item_chithamgia_goi_tratruoc.tocdo_id_moi = this.selected_item_chithamgia_goi.tocdo_id
            this.selected_item_chithamgia_goi_tratruoc.tocdo_moi = this.selected_item_chithamgia_goi.tocdo
          }
        }
        this.arr_selected_item_tratruoc.push(this.selected_item_chithamgia_goi_tratruoc)
        this.$toast.success('Đã thêm KM!')
      }
      else if (cmdName == 'commands_km1'){        
        console.log('click trả trước lưới km1')
        console.log(data)        
        this.selected_item_tocdo_muccuoc_tratruoc = data
        let index = this.arr_selected_item_tratruoc.findIndex((x) => x.chitietkm_id == data.chitietkm_id);
        if (index >= 0){
          this.$toast.error('Khuyến mãi đã được chọn trong danh sách!')
          return
        }
        if (this.selected_item_tocdo_muccuoc){
          this.selected_item_tocdo_muccuoc_tratruoc.loaitb_id = this.selected_item_tocdo_muccuoc.loaitb_id?this.selected_item_tocdo_muccuoc.loaitb_id:58 //TODO
          //join thêm thông tin thuê bao          
          this.selected_item_tocdo_muccuoc_tratruoc.thuebao_id = this.tt_tb.thuebao_id
          this.selected_item_tocdo_muccuoc_tratruoc.ma_tb = this.tt_tb.ma_tb
          this.selected_item_tocdo_muccuoc_tratruoc.ten_tb = this.tt_tb.ten_tb
          this.selected_item_tocdo_muccuoc_tratruoc.loaihinh_tb = this.selected_item_tocdo_muccuoc_tratruoc.loaitb_id == 58? 'Fiber':'MyTv'
          this.selected_item_tocdo_muccuoc_tratruoc.tocdo_id_cu = this.tt_tb.tocdo_id
          this.selected_item_tocdo_muccuoc_tratruoc.tocdo_id_moi = this.selected_item_tocdo_muccuoc_tratruoc.tocdo_id
          this.selected_item_tocdo_muccuoc_tratruoc.tocdo_moi = this.selected_item_tocdo_muccuoc_tratruoc.tocdo
        }
        this.arr_selected_item_tratruoc.push(this.selected_item_tocdo_muccuoc_tratruoc)
        console.log(this.arr_selected_item_tratruoc)
        this.$toast.success('Đã thêm KM!')
      }
      return false
    },    
    grdThuebaoChanged: function(obj) {
      console.log('grdThuebaoChanged')
      console.log(obj)
      if (obj && obj.length > 0){
        this.getLichSuThongtinTB(Number(obj[0].hdtb_id),Number(obj[0].kieuld_id));
      }
    },
    async gridDSTB_rowSelected(e) {
      // console.log('dstb_rowSelected')
      // console.log(e)
      // if(e.data) {
      //   this.gridDSTB.value = e.data
      // } else {
      //   this.gridDSTB.value = null
      // }
    },
    formatNumber_tu() {      
      this.txt_tracuu_tu = this.formatCurrency(this.txt_tracuu_tu);      
    },
    removeComma_tu() {      
      try {
        this.txt_tracuu_tu = this.txt_tracuu_tu.replace(/,/g, '');
      }
      catch{
        return '0'
      }   
    },
    formatNumber_den() {
      this.txt_tracuu_den = this.formatCurrency(this.txt_tracuu_den);
    },    
    removeComma_den() {
      try {
        this.txt_tracuu_den = this.txt_tracuu_den.replace(/,/g, '');
      }
      catch{
        return '0'
      }      
    },
    removeComma(value) {      
      try {
        return value.replace(/,/g, '');
      }
      catch{
        return '0'
      }   
    },
    formatCurrency: function (num) {
      try {
        let val = (num / 1).toFixed(0).replace(',', '.')
        return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
      }
      catch{
        return '0'
      }
    },   ///
    grid_QueryCellInfo:  function(args){
      if (args.column.field === 'thuonghieu' || args.column.field === 'tocdo' || args.column.field === 'muccuoc' || args.column.field === 'ss_tienthang') { 
          args.cell.classList.add('cus-tocdo');
      } else if (args.column.field === 'soluong_dv' || args.column.field === 'tien_fiber' || args.column.field === 'tien_goi' || args.column.field === 'ten_goi' || args.column.field === 'ss_tiengoi' ) { 
          args.cell.classList.add('cus-dadichvu');
      } else if (args.column.field === 'tien_tt' || args.column.field === 'sothang' || args.column.field === 'ten_kmtt') { 
          args.cell.classList.add('cus-km');
      }
    }, 
    getDateElements(date) {

      date = new Date(date)

      let day = date.getDate()
      day = day > 9 ? day : '0' + day

      let month = date.getMonth() + 1
      month = month > 9 ? month : '0' + month

      let hour = date.getHours()
      hour = hour > 9 ? hour : '0' + hour

      let min = date.getMinutes()
      min = min > 9 ? min : '0' + min

      let second = date.getSeconds()
      second = second > 9 ? second : '0' + second

      return {
          y: date.getFullYear(),
          M: month,
          d: day,
          h: hour,
          m: min,
          s: second
      }
    },
    yyyymm(date) {

      if (!date) {
          return ''
      }

      date = this.getDateElements(date)
      return `${date.y}${date.M}`
    },
    yyyymmdd(date) {

      if (!date) {
          return ''
      }

      date = this.getDateElements(date)
      return `${date.y}${date.M}${date.d}`
    },
    ddmmyyyyhhmiss(date) {

      if (!date) {
          return ''
      }

      date = this.getDateElements(date)
      return `${date.d}${date.M}${date.y}${date.h}${date.m}${date.s}`
    },
    ddmmyyyy(date) {

      if (!date) {
          return ''
      }

      date = this.getDateElements(date)
      return `${date.d}/${date.M}/${date.y}`
    },
    ddmmyyyyToDate(dateStr) {

      if (!dateStr) {
          return null
      }

      let arr = dateStr.trim().split('/')
      let dd = arr[0]
      let mm = arr[1]
      let yyyy = arr[2]
      return new Date(`${yyyy}-${mm}-${dd}`)
    },
    ShowPopupCTV() {
      if (this.filterbox.ctv.ischecked) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien')
        this.popupComponentName = 'popupCTV'
        // SearchAccount.data.isPopup = true;
        this.Popup('popupComponents')
      }
    },
    ShowPopupNguoiGT() {
      if (this.filterbox.nguoigt.ischecked) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu')
        this.popupComponentName = 'popupNGT'
        // SearchAccount.data.isPopup = true;
        this.Popup('popupComponents')
      }
    },
    Popup(modalId) {
      this.loading(false)
      if (this.$refs[modalId]) this.$refs[modalId].show()
      else this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async popupClosed(val) {
      if (!val) {        
        this.ClosePopup('popupComponents')
        return
      }
      this.loading(true)
      try {
        switch (this.popupComponentName) {
          case 'popupCTV':
            this.filterbox.ctv.value = val.ten_ht
            this.filterbox.ctv.ma_ctv = val.nhanvien_id
            this.ctv_id = val.nhanvien_id
            break
          case 'popupNGT':
            this.filterbox.nguoigt.value = val.ten_ht
            this.filterbox.nguoigt.nguoigt_id = val.nhanvien_id
            break
          default:            
            break
        }
      } catch (ex) {
        this.$toast.error('' + ex)
        console.error(ex)
      } finally {
        this.ClosePopup('popupComponents')
        this.loading(false)
      }
    },
    async emit_func(data) {
      
    },
    clickXoaHD() {
      this.$alert('Bạn thật sự muốn xóa hợp đồng không ?', 'Thông báo', {
        dangerouslyUseHTMLString: true,
        showCancelButton: true,
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy bỏ',
        showCloseButton: true,
        type: 'warning'
      }).then(async (v) => {
        if (!v) {
          return
        }
        try {
          this.loading(true)
          await this.XoaHDKH()
        } catch (e) {} finally { this.loading(false) }
      })
    },
    async XoaHDKH() {
      this.loading(true)
      await api.xoa_hdkh_v2( {
        vhdkh_id: this.hdkh_id,
        vloaihd_id: 8
      })
        .then((response) => {
          if (response.data.message === 'Success' && response.data.error_code === 'BSS-00000000') {            
            this.$toast.success('Đã xóa')
            this.ClearGUI()
          } else {
            this.$toast.error('Lỗi khi xóa!')
          }
        })
        .catch((error) => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    btnDongBoClick () {      
      window.open(`/#/ext-hopdong/dongbo-visa-vasc-update?tag=2%2B4%3B7%3B8%3B14%2B4`)
    },
    async txtMaGD_KeyPress () {      
      if (this.ma_gd != ''){
        await this.lay_ds_hopdong_theo_ma_gd(this.ma_gd,0)
        // await this.LAY_DS_THUEBAO_DATCOC_THEOGD(this.ma_gd)
      }
    },
    open_thanhToanPopUp() {
      this.loading(true)
    },
    async tsbtnHoanThien_Click() {
      if (this.hdkh_id == 0) {
        this.$toast.error('Chưa có hợp đồng để hoàn thiện!')
        return
      }
      try {   
        this.loading(true); 
        console.log('tsbtnHoanThien_Click')        
        for (var i = 0; i < this.ds_hdtb.length; i++){
          let p_DS_GOI_HUY = []          
          if (this.gv_tb_goi.tableData && this.gv_tb_goi.tableData.length > 0){
            
            if (i == 0){              
              var item = {
                NHOMTB_ID: this.gv_tb_goi.tableData[0].nhomtb_id,
                GOI_ID: this.gv_tb_goi.tableData[0].goi_id
              }              
              p_DS_GOI_HUY.push(item)
              
            }
          }          
          var input = {
            HDTB_ID:this.ds_hdtb[i].hdtb_id,
            DS_GOI_HUY:p_DS_GOI_HUY
          }
          console.log('input')
          console.log(input)
          await api.hths_td_tocdo_goi_quyhoach({"ds_para":JSON.stringify(input)}).then((response) => {          
            if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {                              
              this.$toast.success('Hoàn thiện hợp đồng thành công!')
              
            }
            else {
              this.$root.$toast.error('Lỗi hoàn thiện hợp đồng!')
              this.loading(false);  
              return
            }
          });
        }
        this.ClearGUI()
        this.loading(false);  
      } catch (error) {
        console.log(error)   
        this.loading(false);       
      }
      
      
    },
    async tsbtnThanhToan_Click() {
      if (this.hdkh_id == 0) {
        this.$toast.error('Hãy ghi lại hợp đồng trước khi thanh toán!')
        return
      }
      
      this.userComponentModule = thanhToan
        // goi su kien load lai ma giao dich
      this.ma_gd = this.tt_kh.ma_gd
      // tao component neu chua tao, show popup khi da goi xong ham khoi tao
      if (this.paymentloaded) this.$refs.thanhtoanDialog.show(true)
    },    
    async paymentcreated(val) {
      this.paymentloaded = true
      this.$refs.thanhtoanDialog.show(true)
    },    
    ClearGUI(){
      //clear thông tin
      this.thuebao_id = 0
      this.hdkh_id = 0
      this.hdtb_id = 0
      this.ma_gd = ''
      this.selected_item_chithamgia_goi = null
      this.selected_item_chithamgia_goi_tratruoc = null
      this.selected_item_tocdo_muccuoc = null
      this.selected_item_tocdo_muccuoc_tratruoc = null
      this.selected_item_mytv = null
      this.selected_item_mytv_tratruoc = null
      this.arr_selected_item_tratruoc = []
      this.arr_doitocdo = []
      this.arr_goi = []
      this.isDoiTD = false      
      this.khachhang_id = 0      
      this.tt_tb = {
        "tengoi":"",
        "giagoi":"",
        "khuyenmai": "",
        "muccuoc": "",        
        "ten_tb": "",
        "tocdo": "",
        "mucuoctb_id": 0,
        "so_dt": "",
        "tt_tratruoc_hientai": "",
        "cuoc_tp":"",
        "gia_fiber":"",
        "tien_thanhtoan":"",
        "is_tb_le":1,
        "is_khdn":0
      }
      this.tt_kh = {
        "ma_gd":"",
        "ngay_yc":"",
        "ma_kh":"",
        "ten_kh":"",
        "diachi_kh":"",
        "ghichu":"",
      },   
      this.gv_tb_goi.tableData = []
      this.gv_thuebao.tableData = []
      this.gv_goi_dadv.tableData = []
      this.gv_tratruoc_km.tableData = []
      this.gv_tocdo_muccuoc.tableData = []    
      this.gv_tratruoc_km1.tableData = []  
      this.gv_mytv.tableData = []
      this.gv_tratruoc_km_mytv.tableData = []
      this.filterbox = {
        ctv: {
            value: '',
            ischecked: false,
            ma_ctv: '',
            ctv_id: ''
        },
        nguoigt: {
            value: '',
            ischecked: false,
            nguoigt_id: '',
        }
      }
      this.ctv_id = 0
      this.gridTienHopDong.list = []
      this.listbox.ds_thuebao.list = []
      this.ds_hdtb = []
      this.dtpNgayYeuCau = new Date()
      this.txtMaHD = ''
      this.dtpNgayLapHD = new Date()
      this.txtMaKH = ''
      this.txtNgaySN = ''
      this.txtTenKH = ''
      this.txtDiaChiKH = ''
      this.txtTTCuocVisa = ''
      this.txtGhiChu = ''
      this.txtSoThang = ''
      this.txtTongTienHD = ''
      this.dsHdtb = []
      this.ds_thongtin_thaydoi.list = []
    },
    
  },
};

$('#autoresizing').on('input', function () {
    this.style.height = 'auto';

    this.style.height =
        (this.scrollHeight) + 'px';
});
</script>
<style>
  #autoresizing {
      display: block;
      overflow: hidden;
      resize: none;
  }
  .input-icon-right.disabled input{
    cursor: default;
    pointer-events: none;
    text-decoration: none!important;
    color: grey!important;
  }
  .thaotac {
    border:none !important;
    background: none !important;
  }
  .cus.btn {
    background-color:grey;
    color: #fff;
  }
  .cus.btn:hover {
    color: #fff;
  }
  .cus-tocdo {
    background-color: #f0f8ff !important;
  }
  .cus-dadichvu {
    background-color: #f0ffff !important;
  }
  .cus-km {
    background-color: #b0c4de !important;
  }
  .tien-tra {
    color:#1EA896 !important;
  }
  .quyhoachmuccuoc .e-toolbar {
    display: none !important;
  }
  .tongtien {
    color:red;
    font-weight: bold;
  }
</style>