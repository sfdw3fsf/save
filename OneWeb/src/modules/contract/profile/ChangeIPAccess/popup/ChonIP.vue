<template src="./ChonIP.html"></template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import api from '../api'
import "@/assets/vendor/jquery/split";
import Vue from "vue";
import moment from "moment";
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV} from "@/const/enums";
export default { 
    data: function () {
    return {
        kieu:0,
        bras_id:0,
        in_tocdo_id:0,
        loaiIp: "",
        cboBrasADSL:[],
        diachiIP:"",txtIPv4LAN:"",txtIPv4WAN:"",txtIPv6LAN:"",
        ma_td:-1,bras_vdc_id:-1,
        dgvDanhSachIP:{
            loading:false,
            list:[],
            cols:[ 
                {fieldName: 'key', headerText: 'STT', width:80,allowFiltering: true,  allowSorting: false,}, 
                {fieldName: 'name', allowEditing:true, headerText: 'Địa chỉ IP', allowFiltering: true, allowSorting: false},
            ]},
        }
    },
    props:{
        dataSend:{
            type:Object,
            default:null
        },
    },
    methods:{
        handleChange(event){
            this.LayThongTinIP();
        },
       async tsbtnCapNhan_Click(){
            if(this.diachiIP == "" || this.cboBrasADSL.length <= 0){
                 this.$toast.error("Chưa có danh sách IP để chọn!");
                    return;
            }
            let vkieu = this.loaiIp == "ipv4lan" ? 1 :this.loaiIp == "ipv4wan"? 2 : 3;
            try{
                let result = await this.kiemtra_ip_tb_ftth(this.diachiIP, vkieu);
                if(result.data.error_code === "BSS-00000000" && result.data.data != ""){
                    if(result.data.data > 0){
                       this.$toast.error("Địa chỉ IP " + this.diachiIP + " đã được cấp cho thuê bao khác. Bạn hãy chọn IP khác !");
                    }
                    else{
                        this.$refs["popupChonIP"].hide();
                        this.$emit("getDiaChiIPChange", {"kieu":this.kieu, "IP":this.diachiIP});
                    }
                }
                
            }
            catch(e){
                this.$toast.error("Có lỗi trong quá trình chọn IP!");
                console.log(e);
            }
           

        },
      async handleShowModal(){
            this.loaiIp = "";this.diachiIP = "";
            this.txtIPv4LAN = ""; this.txtIPv4WAN = ""; this.txtIPv6LAN="";
            this.kieu = this.dataSend.kieu;
            this.dgvDanhSachIP.list = [];   
            this.in_tocdo_id = this.dataSend.in_tocdo_id;
            this.cboBrasADSL= [];this.ma_td=-1;
            this.bras_vdc_id=-1;
            if (this.kieu == 1)
                this.loaiIp = "ipv4lan";
            else if (this.kieu == 2)
                this.loaiIp = "ipv4wan";
            else if (this.kieu == 3)
                this.loaiIp = "ipv6lan";

          await  this.HT_BRAS_ADSL_Combobox();
          await  this.LayThongTinIP();
        },
        async kiemtra_ip_tb_ftth(diachiIP, vkieu){
            
            let res = await api.kiemtra_ip_tb_ftth(this.axios,
                {
                    "vip": "203.210.157.110",
                    //"vip": diachiIP,
                    "vkieu": vkieu,
                })
               return res;
        },
        showModal() {
            
            this.$refs["popupChonIP"].show();
        },
        hideModal() {
            this.$refs["popupChonIP"].hide();
            this.$emit("closeModalChonIP");
        },
        async HT_BRAS_ADSL_Combobox(){
            let res = await api.sp_lay_ds_bras_adsl(this.axios)
                if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
                    this.cboBrasADSL = res.data.data;
                    let index = this.cboBrasADSL.findIndex(item => item.bras_id.toString() === this.dataSend.bras_id.toString())
                    if(index >= 0){
                        this.bras_id = this.dataSend.bras_id;
                    }
                    else{
                        this.bras_id = null;
                    }
                }
        },
        async getMaTocDo(){
             let input1 = {};
             input1.type = 5;
             input1.param = this.in_tocdo_id;
             let response = await api.fn_tt_tocdo_adsl(this.axios,input1);
                    if(response.data.error_code === "BSS-00000000"){
                       this.ma_td  = response.data.data;
                    };

        },
        async getBrasVDC(){
            let input1 = {};
             input1.type = 1;
             input1.param = this.bras_id;
             let response = await api.fn_tt_bras(this.axios,input1);
                    if(response.data.error_code === "BSS-00000000"){      
                        this.bras_vdc_id = response.data.data;
                    }
        },

      async getDgvIP(){
          try{
              this.loading(true);
            if(this.loaiIp == "ipv4lan" || this.loaiIp == "ipv4wan"){
               let response = await api.getIps(this.axios,
                    {
                            "brasId":this.bras_vdc_id,
                            "serviceId":this.ma_td,
                    });
                    if(response.data.errorCode === 0){    
                        var result = response.data.data;
                        var arrayData = [];
                        result.forEach((obj, index)=>{
                            var item = {
                                "key": index + 1,
                                "name":obj,
                            };
                            arrayData.push(item);
                        })
                        this.dgvDanhSachIP.list = arrayData;

                    } 
                    else{
                        this.dgvDanhSachIP.list = [];
                    }
                this.loading(false);
           }
           else {
               let response = await api.getIpsV6(this.axios,
                     {
                            "brasId":this.bras_vdc_id,
                            "serviceId":this.ma_td,
                    });
                    if(response.data.errorCode === 0){      
                        var result = response.data.data;
                        var arrayData = [];
                        result.forEach((obj, index)=>{
                            var item = {
                                "key": index + 1,
                                "name":obj,
                            };
                            arrayData.push(item);
                        })
                        this.dgvDanhSachIP.list = arrayData;
                    } 
                    else{
                        this.dgvDanhSachIP.list = [];
                    }
                this.loading(false);
           }
          }
          catch(e){
              this.loading(false);
          }
           
        },
    
        dtgDanhSach_SelectionChanged(data){
             this.diachiIP = data[0].name;
            if (this.loaiIp == "ipv4lan")
                this.txtIPv4LAN = this.diachiIP;
            else if (this.loaiIp == "ipv4wan")
                this.txtIPv4WAN = this.diachiIP;
            else if ( this.loaiIp == "ipv6lan")
                this.txtIPv6LAN = this.diachiIP;
        },
      async  LayThongTinIP(){
            if(this.cboBrasADSL.length >0){
                if(this.bras_id <= 0) return;
                var vma_td = -1;
                var vbras_vdc_id = -1;
                try{
                  await this.getMaTocDo();
                  await this.getBrasVDC();
                  await this.getDgvIP();                
                }
                catch(e){

                }
            }

        },
    }
}
</script>