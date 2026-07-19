<template src="./ChangeIPAccess.html"></template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import api from './api'
import "@/assets/vendor/jquery/split";
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV} from "@/const/enums";
import ChonIPModal from "./popup/ChonIP";
Vue.use(DialogPlugin);
export default { 
    name:'ChangeIPAccessModal',
    components: {
        LoaiHopDong,DichVuVienThong,TrangThaiHD,LOAI_DV,ChonIPModal
    },
    data: function () {
    return {
        loaitb_id: -1,
        khachhang_id: 0,
        thuebao_id: 0,
        in_tocdo_id: -1,
        in_bras_id: 0,
        kieu_mega: 0,
        dataSend:{},
        chophep_nhap_diachi_ip: false,
        cboLoaihinhTB:[], txtMaTB:"",txtTenTB:"",txtDiaChiLD:"",
        txtIPCu:"",txtIPv6LanCu:"",txtIPv4WanCu:"",txtIPADSL:"",
        txtIPv4Wan:"",txtIPv6Lan:"",
        donviId:this.$root.token.getDonViID(),
        diachiIP:"",
    }},
    methods:{
        //Lấy giá trị từ popup Chọn IP
         getDiaChiIPChange(val){
             console.log(val);
            if (val.kieu == 1)
                this.txtIPADSL = val.IP;
            else if (val.kieu == 2)
                this.txtIPv4Wan = val.IP;
            else if (val.kieu == 3)
                this.txtIPv6Lan = val.IP;
            
         }, 
        async tsbtnThayDoi_Click(){
              try
            {
                var flag = false;
                var tdtt_adsl = [{
                    "PHANVUNG_ID": this.$root.token.getPhanVungID(),
                    "THUEBAO_ID": this.thuebao_id,
                    "MA_TB": this.txtMaTB,
                    "NHANVIEN_ID": this.$root.token.getNhanVienID(),
                    "DONVI_ID": this.$root.token.getDonViID(),
                    "TRANGTHAI_ID": 5,
                    "NGAY_DK": moment().format("DD/MM/YYYY HH:mm:ss"),
                    "NGAY_HT": moment().format("DD/MM/YYYY HH:mm:ss"),
                    "GHICHU": "Đổi IP truy nhập",
                    "NGAY_CN": moment().format("DD/MM/YYYY HH:mm:ss"),
                    "NGUOI_CN": this.$root.token.getNguoiDungID(),
                    "MAY_CN": "localhost",//this.$root.token.getMachine(),
                    "PORT_ID": null,
                    "PORT_ID_CU": null,
                    "VCI_VPI_ID": null,
                    "VCI_VPI_ID_CU": null,
                    "BRAS_ID": null,
                    "BRAS_ID_CU": null,
                    "DSLAM_ID": null,
                    "DSLAM_ID_CU": null,
                    "MA_LT": null,
                    "MA_LT_CU": null,
                    "PASSWORD": null,
                    "PASSWORD_CU": null,
                    "IP": (this.txtIPADSL == "" || this.txtIPADSL == null )?"":this.txtIPADSL.trim(),
                    "IP_CU": (this.txtIPCu == "" || this.txtIPCu == null )?"":this.txtIPCu.trim(),
                    "TUYENCAP": null,
                    "LIENTU": null,
                    "DOICAP_P": null,
                    "HOPCAP_P": null,
                    "TUCAP_P": null,
                    "LOAICAP_ID": null,
                    "CAP_GOC": null,
                    "CAP_P": null,
                    "DOICAP_GOC": null,
                    "TUCAP_GOC": null,
                    "TUYENCAP_CU": null,
                    "LIENTU_CU": null,
                    "DOICAP_P_CU": null,
                    "HOPCAP_P_CU": null,
                    "TUCAP_P_CU": null,
                    "LOAICAP_ID_CU": null,
                    "CAP_GOC_CU": null,
                    "CAP_P_CU": null,
                    "DOICAP_GOC_CU": null,
                    "TUCAP_GOC_CU": null,
                    "CULY": null,
                    "CULY_CU": null,
                    "TDTT_DB_ID": null,
                    "THONGTIN_TC": null,
                    "SLID_CU": null,
                    "SLID_MOI": null,
                    "SERI_MD_CU": null,
                    "SERI_MD_MOI": null,
                    "SLTV_HTVC_CU": null,
                    "SLTV_HTVC_MOI": null,
                    "SODT_CU": null,
                    "SODT_MOI": null,
                    "KETCUOI_ID": null,
                    "VITRI": null,
                    "VITRI_2": null,
                    "CAP_ID": null,
                    "DOICAP": null,
                    "DOICAP_2": null,
                    "KETCUOI_ID_CU": null,
                    "VITRI_CU": null,
                    "VITRI_2_CU": null,
                    "CAP_ID_CU": null,
                    "DOICAP_CU": null,
                    "DOICAP_2_CU": null,
                    "HDTB_ID": null,
                    "KIEU": null,
                    "NGUON_YC": null,
                    "NOTE": null,
                    "CHUQUAN_ID": null,
                    "CHUQUANLD_ID": null,
                    "CHUQUAN_ID_CU": null,
                    "CHUQUANLD_ID_CU": null,
                    "MAC_ADDR_CU": null,
                    "MAC_ADDR": null
                    }];
                if (!this.KiemTraDuLieu()) return;
                if (this.chophep_nhap_diachi_ip)
                {
                    
                    //Cập nhật địa chỉ IP VTT, không đồng bộ VDC
                    if (this.kieu_mega == 0)
                    {
                        //Gọi API
                       this.sp_frmdoiiptruynhap_capnhat(tdtt_adsl);
                    }
                    else
                    {
                         //this.sp_frmdoiiptruynhap_capnhat(tdtt_adsl);
                        this.ModifyServiceIPAccessMega(tdtt_adsl);
                    }
                }
                else
                {
                    //Đổi IP truy nhập trên VDC
                  
                    this.ModifyServiceIPAccessMega(tdtt_adsl);
                        
                }

            }
            
            catch (ex)
            {
                console.log(ex);
                
            }
         },
      async  ModifyServiceIPAccessMega(dataParam){
            try{
                 let flag = await api.modifyServiceIPAccessMegaIPv6(this.axios,{
                    "accountModel":{
                        "accountName": this.txtMaTB,
                    },
                    "ip": (this.txtIPADSL == "" || this.txtIPADSL == null)?((this.txtIPCu == "" || this.txtIPCu == null )?"":this.txtIPCu.trim()):this.txtIPADSL.trim(),
                    "ipv4Wan": (this.txtIPv4Wan=="" || this.txtIPv4Wan == null)?((this.txtIPv4WanCu == "" || this.txtIPv4WanCu == null)?"":this.txtIPv4WanCu.trim()):this.txtIPv4Wan.trim(),
                    "ipv6Lan": (this.txtIPv6Lan=="" || this.txtIPv6Lan == null)?((this.txtIPv6LanCu=="" || this.txtIPv6LanCu == null)?"":this.txtIPv6LanCu.trim()):this.txtIPv6Lan.trim(),
                });
                console.log(flag);
                if(flag.data.errorCode === 0 && flag.data.data == "ok"){
                    this.sp_frmdoiiptruynhap_capnhat(dataParam);
                }
                else{
                    this.$toast.error(flag.data.faultString);
                }
            }
            catch(e){
                console.log(e);
                this.$toast.error("Có lỗi trong quá trình đổi IP truy nhập accout: " + this.txtMaTB);
            }
           
        },
        KiemTraDuLieu()
        {
            if (this.txtMaTB.trim() == "")
            {
                this.$toast.error("Chưa nhập mã thuê bao !");
                return false;
            }
            if (this.in_bras_id == 0)
            {
                this.$toast.error("Chưa có thông tin Bras của thuê bao !");
                return false;
            }
            if (this.in_tocdo_id == -1)
            {
                this.$toast.error("Chưa có thông tin tốc độ của thuê bao !");
                return false;
            }
            if (this.txtIPADSL.trim() == "")
            {
               this.$toast.error("Chưa nhập địa chỉ IP mới !");
                return false;
            }
            if (this.chophep_nhap_diachi_ip)
            {
                if (!this.KiemTraDiaChiIP(this.txtIPADSL.trim()))
                {
                    this.$toast.error("Địa chỉ IP mới không đúng !");
                    this.$refs.txtIPADSL.focus();
                    return false;
                }
                if (this.txtIPv4Wan != null && this.txtIPv4Wan != "")
                    if (!this.KiemTraDiaChiIP(this.txtIPv4Wan.trim()))
                    {
                        this.$toast.error("Địa chỉ IPv4 Wan mới không đúng !");
                        this.$refs.txtIPv4Wan.focus();
                        return false;
                    }
                if (this.txtIPv6Lan != null && this.txtIPv6Lan != "")
                    if (!this.KiemTraDiaChiIP(this.txtIPv6Lan.trim()))
                    {
                        this.$toast.error("Địa chỉ IPv6 Lan mới không đúng !");
                        this.$refs.txtIPv6Lan.focus();
                        return false;
                    }
            }
            return true;
        },
        KiemTraDiaChiIP(diachi_ip)
        {
            try
            {   
                     
                let formatIPv4 = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(?:\/(\d|[12]\d|3[01]))?$/;
                const regexExp = /(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))/gi;              
                if(diachi_ip.match(formatIPv4)){                
                   return true;
                }
                else{
                   return regexExp.test(diachi_ip);
                }    
            }
            catch (Exception)
            {
                console.log(Exception);
                return false;
            }

        },
         handleShowModal(){
            this.in_tocdo_id= -1,
            this.in_bras_id= 0,
            this.loaitb_id= -1;
            this.khachhang_id= 0;
            this.kieu_mega= 0;
            this.dataSend={};this.diachiIP = "";
            this.chophep_nhap_diachi_ip = false;
            this.cboLoaihinhTB = []; this.txtMaTB = "";this.txtIPADSL="",
            this.frmDoiIPTruyNhap_Load();
            this.thuebao_id = 0;
            this.txtTenTB = "";
            this.txtDiaChiLD = "";
            this.txtIPCu = "";
            this.txtIPADSL = "";

            this.txtIPv6LanCu = "";
            this.txtIPv4WanCu = "";
            this.txtIPv4Wan = "";
            this.txtIPv6Lan = "";
            
        },
        showModal() {
            this.$refs["popupDoiIP"].show();
        },
        hideModal() {
            this.$refs["popupDoiIP"].hide();
            this.$emit("closeModal");
        },
        async sp_loaihinh_tb_tocdo(){
             let res = await api.sp_loaihinh_tb_tocdo(this.axios,{                         
                            "dichvuvt_id": DichVuVienThong.ADSL,
                        });
            if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
                this.cboLoaihinhTB = res.data.data;
                this.loaitb_id = this.cboLoaihinhTB[0].loaitb_id;
                
                this.$nextTick(() => {
                    this.$refs.txtMaTB.focus();
                })
            }
            else{
                this.cboLoaihinhTB = [];
                this.loaitb_id = -1;
            }
        },
        async sp_lay_db_theo_matb_1(txtMaTB, donviId){
            
                let res = await api.sp_lay_db_theo_matb_1(this.axios,{                         
                            "ma_tb": txtMaTB.trim(),
                            "donvi_dl_id":0,
                        });
                if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
                    var result = res.data.data[0];
                    this.thuebao_id = result.thuebao_id;
                    this.lay_ds_danhba_adsl(result);
                }
                else{
                    this.$toast.error("Không tìm thấy thông tin! Bạn hãy nhập lại thông tin tìm kiếm");
                    this.Clear();
                }
               
           
        },
       async sp_lay_ds_thamso_md(){
           let res = await api.sp_lay_ds_thamso_md(this.axios);
           if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
               res.data.data.forEach((obj)=>{
                   var str_vtemp = "CHOPHEP_NHAP_DIACHI_IP";
                   if(obj.ma_ts == str_vtemp){
                       this.chophep_nhap_diachi_ip = true;
                       return;
                   }
               })
           }
       },
        Clear()
        {
            this.loaitb_id = this.cboLoaihinhTB[0].loaitb_id;
            this.thuebao_id = 0;
            this.txtTenTB = "";
            this.txtDiaChiLD = "";
            this.txtIPCu = "";
            this.txtIPADSL = "";
            this.in_bras_id = 0;
            this.in_tocdo_id = 0;

            this.txtIPv6LanCu = "";
            this.txtIPv4WanCu = "";
            this.txtIPv4Wan = "";
            this.txtIPv6Lan = "";
        },
       async lay_ds_danhba_adsl(itemInfo){
           try{
               
                let res = await api.lay_ds_danhba_adsl(this.axios,'/web-hopdong/hopdong/lay_ds_danhba_adsl?thueBaoId='+this.thuebao_id);

                if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
                    var result = res.data.data[0];
                        this.khachhang_id = itemInfo.khachhang_id;
                        
                        this.txtMaTB = itemInfo.ma_tb;
                        this.loaitb_id = itemInfo.loaitb_id;
                        this.txtTenTB = itemInfo.ten_tb;
                        this.txtDiaChiLD = itemInfo.diachi_ld;
                        this.txtIPCu = result.ip;
                        this.in_tocdo_id = result.tocdo_id;
                        this.in_bras_id = result.bras_id;
                        this.txtIPv6LanCu = result.ipv6_lan;
                        this.txtIPv4WanCu = result.ipv4_wan;
                    if (this.chophep_nhap_diachi_ip)
                    {
                        //khaivk - 17/06/2020: kiểm tra nếu kieu_mega = 0 thì cho phép nhập thông tin IP mới và cập nhật TT VTT không đồng bộ Visa
                        this.kieu_mega = result.kieu_mega;
                        if (this.kieu_mega == 0)
                        {
                            this.$nextTick(() => {
                                this.$refs.txtIPADSL.removeAttribute('readonly');
                                this.$refs.txtIPv4Wan.removeAttribute('readonly');
                                this.$refs.txtIPv6Lan.removeAttribute('readonly');
                            })
                            
                        }
                        else
                        {
                            this.$nextTick(() => {
                                this.$refs.txtIPADSL.setAttribute('readonly', 'readonly');
                                this.$refs.txtIPv4Wan.setAttribute('readonly', 'readonly');
                                this.$refs.txtIPv6Lan.setAttribute('readonly', 'readonly');
                            })
                            
                            this.txtIPADSL = "";
                            this.txtIPv4Wan = "";
                            this.txtIPv6Lan = "";
                        }

                    }
                }
                else{
                        this.$toast.error("Không tìm thấy thông tin! Bạn hãy nhập lại thông tin tìm kiếm");
                        this.Clear();
                    }   
            }
            catch(e){
                this.$toast.error("Không tìm thấy thông tin! Bạn hãy nhập lại thông tin tìm kiếm");
                console.log(e);
            }
       },
        async frmDoiIPTruyNhap_Load(){
            
           await this.sp_loaihinh_tb_tocdo();  
           await this.sp_lay_ds_thamso_md();
            
        },
        async sp_frmdoiiptruynhap_capnhat(tdtt_adsl){
             let res = await api.sp_frmdoiiptruynhap_capnhat(this.axios,{                         
                            "p_thuebao_id":this.thuebao_id,
                            "p_ip_cu":(this.txtIPCu == "" || this.txtIPCu == null )?"":this.txtIPCu.trim(),
                            "p_ip_moi":(this.txtIPADSL == "" || this.txtIPADSL == null)?"":this.txtIPADSL.trim(),
                            "p_ipv4wan_cu":(this.txtIPv4WanCu == "" || this.txtIPv4WanCu == null)?"":this.txtIPv4WanCu.trim(),
                            "p_ipv4wan_moi":(this.txtIPv4Wan=="" || this.txtIPv4Wan == null)?"":this.txtIPv4Wan.trim(),
                            "p_ipv6wan_cu":(this.txtIPv6LanCu=="" || this.txtIPv6LanCu == null)?"":this.txtIPv6LanCu.trim(),
                            "p_ipv6wan_moi":(this.txtIPv6Lan=="" || this.txtIPv6Lan == null)?"":this.txtIPv6Lan.trim(),
                            "p_tdtt_adsl":JSON.stringify(tdtt_adsl),
                        });
                try{
                    if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
                    var result = res.data.data;
                    if(result == 1){
                        this.$toast.success("Cập nhật IP truy nhập accout: " + this.txtMaTB + " thành công !");
                    }
                    else{
                        this.$toast.error("Có lỗi trong quá trình đổi IP truy nhập accout: " + this.txtMaTB);
                    }
                    this.Clear();
                    }
                    else{
                        this.$toast.error("Có lỗi trong quá trình đổi IP truy nhập accout: " + this.txtMaTB);
                        this.Clear();
                    }
                }
                catch(e){
                    console.log(e);
                    this.$toast.error("Có lỗi trong quá trình đổi IP truy nhập accout: " + this.txtMaTB);
                }
               
        },
        txtMaTB_KeyPress(){
            if (this.txtMaTB == "") return;
            this.sp_lay_db_theo_matb_1(this.txtMaTB, this.donviId)
             this.$nextTick(() => {
                    this.$refs.txtMaTB.focus();
                })
        },
        checkDataBeforeClick(){
            var result = true;
                if (this.txtMaTB.trim() == "")
                {
                    this.$toast.error("Chưa nhập mã thuê bao !");
                    result = false;
                    return false;
                }
                if (this.in_tocdo_id == -1)
                {
                    this.$toast.error("Thuê bao chưa được gán tốc độ !");
                    result = false;
                    return false;
                }
                if (this.in_bras_id == -1)
                {
                    this.$toast.error("Thuê bao chưa được gán Bras !");
                    result = false;
                    return false;
                }
                return true;
        },
        closeModalChonIP(){
            // $emit thông báo ra cho parent
        },
        btnIPTinh_Click(){
            if(this.checkDataBeforeClick()){
                this.dataSend.kieu = 1;
                this.dataSend.bras_id= this.in_bras_id;
                this.dataSend.in_tocdo_id = this.in_tocdo_id;
                    this.$refs["popupChonIP"].showModal();
            }
        },
        btnIPTinhV4Wan_Click(){
            
            if(this.checkDataBeforeClick()){
                this.dataSend.kieu = 2;
                this.dataSend.bras_id= this.in_bras_id;
                this.dataSend.in_tocdo_id = this.in_tocdo_id;
                    this.$refs["popupChonIP"].showModal();
            }

        },
        btnIPTinhV6Lan_Click(){
             if(this.checkDataBeforeClick()){
                this.dataSend.kieu = 3;
                this.dataSend.bras_id= this.in_bras_id;
                this.dataSend.in_tocdo_id = this.in_tocdo_id;
                    this.$refs["popupChonIP"].showModal();
            }
        },
    },
}
</script>