<template src="./DeliveryInstallationReceipt.html"></template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from './api'
import "@/assets/vendor/jquery/split";
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import SearchContractModal from '../../search/subscriber/SearchContract/SearchContract.vue'
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV} from "@/const/enums";
import DropDonViNhan from './components/DropDonViNhan'
Vue.use(DialogPlugin);

export default {
    components: {
        breadcrumb,LoaiHopDong,DichVuVienThong,TrangThaiHD,LOAI_DV,SearchContractModal,DropDonViNhan
    },
    data: function () {
    return {
        position: { X: 'center', Y: 'top' },
        txtMaGD:"",dtpNgayGiaoTK:moment().format("DD/MM/YYYY"),txtNoiDung:"",
        cboTT:[],ds_dvn:[],loai_hd_id:0,//Dùng cho load popup
        trangthaiCbo:0,dichvuvt_id:0,loaihd_id:0,phieu_id:-1,
        nhap_lienhe:0,hdtb_id:-1,kieu_id:-1,donvi_nhan_id:0,
        huonggiao_id:-1,hg_id:-1,dvvt_id:-1, temp:0,temp1:0,temp2:0,
        kt_daucuoi:0,loaidv_nhan_id:"-1",kt_load:true,bGiaoPhieu:false,
        nhanvien_id:0,donviDLId:0,donvi_id:0,
        lblTongSo:"Tổng số: ",
        gridThueBao:{
            loading: false,
            list: [],
            cols: [
                {
                    headerText: 'Mã giao dịch',
                    fieldName: 'ma_gd',
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
                    headerText: 'Địa chỉ lắp đặt',
                    fieldName: 'diachi_kh',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Ngày yêu cầu',
                    fieldName: 'ngaylap_hd',
                    format: 'dd/MM/yyyy HH:mm:ss',
                    type: 'date',
                    textAlign: 'right',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Đơn vị nhận',
                    fieldName: '',
                    allowFiltering: true,
                    allowSorting: true,
                    template: function () {
                      return {
                        template: DropDonViNhan
                      }
                    }

                },
            ]
    },

        listDonViNhanID: [],
        dv_nhan: [],
    }
    },
    watch:{
        dtpNgayGiaoTK(){
            this.HienThi_DanhSach();
        },
    },
    methods:{
      onDsThueBaoRowChanged: function (item) {
        console.log("queryCellInfo")
        console.log(item)
      },
      queryCellInfo: function (args) {
            if (args.column.field === "ngaylap_hd"){
                var result = args.data.ngaylap_hd;
                if(result != "" && result != null){
                    var setVal = moment(result,'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss");
                    args.data.ngaylap_hd = setVal;
                    args.cell.innerText = setVal;
                }
            }
         },
      receiverData(donvinhanid, data) {
        if(!donvinhanid) return alert("Khong co don vi");
        console.log("receiverData")
        console.log(donvinhanid)
        console.log(data)
        this.gridThueBao.list[data.index].donvi_nhan_id = donvinhanid;
        console.log(this.gridThueBao.list[data.index])
        // this.$set(this.gridThueBao);
        const obj = {
          phieuhd_id: data.phieuhd_id,
          donvinhanid: donvinhanid,
        };
        this.listDonViNhanID.push(obj);
      },
         showPopupSearchContract(){
          //tra cứu phụ lục hợp đồng
            this.$refs.popupFrmTraCuuHopDong.$children[0].clearData();
            this.$refs.popupFrmTraCuuHopDong.$children[0].loadPopup();
            this.$refs.popupFrmTraCuuHopDong.show();
          //this.$refs.searchContractModal.showModal()
        },
         acceptSearchContract(item){
          this.$refs.popupFrmTraCuuHopDong.hide();
          this.txtMaGD=item.hopdong.ma_gd;
          this.$refs["txtMaGD"].focus();
          this.HienThi_DanhSach();
          //this.$emit('searchMaGD', item.ma_gd)
        },
        async frmGiaoPhieuHD_Load(){
            try
            {
                await this.sp_lay_ds_thamso_md(-1);
                //await this.sp_frmgiaophieuhd_lay_ds_donvinhan();

                await this.HienThi_DanhSach();
                this.kt_load = false;
            }
            catch (exp)
            {
                //new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
                this.$toast.error("" + exp);
            }
        },
       async sp_lay_ds_thamso_md(kieu){
           let res = await api.sp_lay_ds_thamso_md(this.axios,{
                            "kieu_id": kieu,
                        });
            var str_vtemp = "NHAP_LIENHE";
            if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
                res.data.data.forEach((obj)=>{
                    if(obj.ma_ts == str_vtemp){
                        if(obj.ten_ts == "1"){
                            this.nhap_lienhe = 1;
                            return;
                        }
                    }
                })
            }
       },
       async sp_frmgiaophieuhd_lay_ds_donvinhan(){
           let res = await api.sp_frmgiaophieuhd_lay_ds_donvinhan(this.axios);
           if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
               //this.ds_dvn = res.data.data;
           }
            else {
               // this.ds_dvn = [];
            }
       },
     async HienThi_DanhSach(){
           try
            {
              const res = await this.axios.get('/web-ccdv/xuly_yeucau_lapdat/lay_dvnhan_xuly_hd_tiepnhan');
              if (res.data.error_code === "BSS-00000000" && res.data.data != "") {
                this.dv_nhan = res.data.data
                console.log( this.dv_nhan)
              }
                 this.gridThueBao.list = [];
                if (this.trangthaiCbo == 0)
                {
                    this.$refs["tsbtnHuyGiao"].classList.add('disabled');
                    this.$refs["tsbtnTimKiem"].classList.remove('disabled');
                    this.$refs["tsbtnGiaoPhieu"].classList.remove('disabled');
                    // ctmsGiaoPhieu.Visible = true;
                    // ctmsHuyPhieu.Visible = false;
                }
                else
                {
                    this.$refs["tsbtnHuyGiao"].classList.remove('disabled');
                    this.$refs["tsbtnTimKiem"].classList.add('disabled');
                    this.$refs["tsbtnGiaoPhieu"].classList.add('disabled');
                    // ctmsGiaoPhieu.Visible = false;
                    // ctmsHuyPhieu.Visible = true;
                }
                switch (parseInt(this.trangthaiCbo))
                {
                    case 0:
                        this.loading(true);
                        await this.PHIEU_CHUA_GIAO(this.txtMaGD);
                        this.loading(false);
                        break;
                    case 1:
                        this.loading(true);
                        await this.PHIEU_DA_GIAO(this.txtMaGD,this.dtpNgayGiaoTK);
                        this.loading(false);
                        break;
                }

                this.lblTongSo = "Tổng số: " + this.gridThueBao.list.length;

            }
            catch (exp)
            {
                this.loading(false);
                this.$toast.error(exp);
            }
       },
      async PHIEU_CHUA_GIAO(txtMaGD){
           try{

                let res = await api.sp_frmgiaophieuhd_phieu_chuagiao(this.axios,{
               "ma_gd": txtMaGD
           });
           if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
               this.gridThueBao.list = res.data.data;
           }
            else {
                this.gridThueBao.list = [];
            }
           }
           catch(e){
               this.gridThueBao.list = [];
                this.$toast.error("" + e);
           }

       },
      async PHIEU_DA_GIAO(txtMaGD,dtpNgayGiaoTK){
           try{

                let res = await api.sp_frmgiaophieuhd_phieu_dagiao(this.axios,{
               "ma_gd": txtMaGD,
               "ngay_cn":dtpNgayGiaoTK,
           });
           if(res.data.error_code === "BSS-00000000" && res.data.data != ""){
               this.gridThueBao.list = res.data.data;
           }
            else {
                this.gridThueBao.list = [];
            }
           }
           catch(e){
               this.$toast.error("" + e);
               this.gridThueBao.list = [];
           }

       },
       txtMaGD_KeyPress(){
           this.HienThi_DanhSach();
       },
       tsbtnGiaoPhieu_Click(){
            this.$refs["txtMaGD"].focus();
            this.GiaoPhieu();
       },
       tsbtnHuyGiao_Click(){
            this.HuyPhieu();
       },
      async GiaoPhieu(){
          try{
                var listSelectedRow = this.$refs.gridThueBao.getSelectedRecords();
            console.log("this.$refs.gridThueBao.getSelectedRecords()")
            console.log(listSelectedRow)
            const list = this.listDonViNhanID;
            console.log(list)
                if(listSelectedRow.length > 0){
                     var dsPhieu = [];
                listSelectedRow.forEach((obj)=>{
                  const result = list.filter(e => {
                    return e.phieuhd_id == obj.phieuhd_id
                  });
                  console.log("result")
                  console.log(result)
                    var item = {
                        "donvi_id":result[0].donvinhanid,
                        "hdkh_id":obj.hdkh_id,
                        "phieuhd_id":obj.phieuhd_id
                    }
                    dsPhieu.push(item);
                })
                let res = await api.fn_frmgiaophieuhd_giaophieu(this.axios,{
                     "huonggiao_id": 2,
                        "json_dsphieu": dsPhieu,
                    "noidung_giao": this.txtNoiDung,
                });
                this.txtNoiDung = "";
                this.HienThi_DanhSach();
                this.$toast.success("Đã giao phiếu thành công");
                }
                else{
                    this.$toast.error("Bạn chưa chọn phiếu giao");
                    return;
                }
          }
           catch(e){
                    this.$toast.error("Giao phiếu thất bại");
                    this.$toast.error("" + e);
                }

       },
       async HuyPhieu()
        {
            try
            {

                this.$refs.txtMaGD.focus();
                var listSelectedRow = this.$refs.gridThueBao.getSelectedRecords();
                if(listSelectedRow.length > 0){
                     var dsPhieu = [];
                    listSelectedRow.forEach((obj)=>{
                    var item = {
                        "donvi_id":obj.donvi_nhan_id,
                        "hdkh_id":obj.hdkh_id,
                        "phieuhd_id":obj.phieuhd_id
                    }
                    dsPhieu.push(item);
                })
                    let res = await api.fn_frmgiaophieuhd_huyphieu(this.axios,{
                            "json_dsphieu": dsPhieu,
                    });
                    if(res.data.error_code ===  "BSS-00000000" && res.data.data){
                        this.txtNoiDung = "";
                        this.HienThi_DanhSach();
                        this.$toast.success("Đã hủy giao phiếu thành công");
                    }
                    else{
                        this.$toast.error("Hủy giao phiếu thất bại");
                    }

                }
                else{
                    this.$toast.error("Bạn chưa chọn phiếu để hủy giao");
                    return;
                }
            }
            catch (xp)
            {
                this.$toast.error("Hủy giao phiếu thất bại");
                this.$toast.error("" + xp);
            }
        },
       cboTT_SelectedValueChanged(args){
           this.HienThi_DanhSach();
       },
       tsbtnTimKiem_Click(){
           this.HienThi_DanhSach();
       },
    },
    mounted(){
        this.frmGiaoPhieuHD_Load();
        this.cboTT = [{"id":0,"text":"Chưa giao"},{"id":1,"text":"Đã giao"}];
    },
    created(){
        this.nhanvien_id = Number(this.$root.token.getNhanVienID());
        this.donvi_id = Number(this.$root.token.getDonViID());
        this.donviDLId = Number(this.$root.token.getDonViDuLieuID());
        this.loai_hd_id = LoaiHopDong.TIEPNHAN_DATMOI; //Dùng cho load popup
    }


}
</script>
