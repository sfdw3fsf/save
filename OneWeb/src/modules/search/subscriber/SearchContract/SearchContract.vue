<template src="./SearchContract.html"></template>
<script src="@/static/vendor/jquery/split.js"></script>
<style>
.e-grid .e-movableheader
{
  overflow: scroll;
}
</style>
<script>

import breadcrumb from "@/components/breadcrumb";
import api from './api'
import "@/assets/vendor/jquery/split";
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
import TraCuuThongSoKTModal from '../../../contract/setup/DeclareServiceSpeedITSecure/popups/TraCuuThongSoKT/TraCuuThongSoKTModal.vue'
import TraCuuKhuyenMaiModal from './popup/TraCuuKhuyenMaiModal.vue'
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV} from "@/const/enums";
import InHoSo from "../../../print/InHopDong/InHopDong";
import InBienBan from "../../../print/InBienBan/InBienBan"
import InPhieuThu from "../../../print/InPhieuThu/InPhieuThu"
import CreatEInvoiceModal from '@/modules/EXTS/HOPDONG/InHopDong/CreateEInvoiceModal/CreateEInvoiceModal.vue';
import { Boolean } from 'pdfmake/build/pdfmake';

Vue.use(DialogPlugin);

export default {
    components:{
        TraCuuThongSoKTModal,
        TraCuuKhuyenMaiModal,
        LoaiHopDong,
        DichVuVienThong,
        TrangThaiHD,LOAI_DV,InHoSo,
        InBienBan,
        InPhieuThu,
        CreatEInvoiceModal
    },
    props:{
        isPopup:{
          type:Number,
          default:0
        },
        //không truyền -1, disable check loaiHD/PL lấy row đầu và ngược lại
        loaihd_id:{
            type:Number,
            default:0
        },
        //Dịch vụ
        dichvuvt_id:{
            type:Number,
            default:0
        },
        //ngày yêu cầu string format DD/MM/YYYY default '' checkbox disable
        ngay_yc:{
            type:String,
            default:'0'
        },

        tthd_id:{
            type:Number,
            default:0
        },
        kieu:{
            type:Number,
            default:0
        },
        donviId:{
            type:Number,
            default:0
        },
        donviDLId:{
            type:Number,
            default:0
        },
        nhanvienId:{
            type:Number,
            default:0
        },
        isShowTaoHDDT:{
            type: Boolean,
            default: false
        }


    },
    data: function () {
        return {
            target: ".main-wrapper",
            phieu_id : 0,
            hdtb_id : 0,
            hdkh_id : 0,
            thuebao_id:null,
            //tag : null,
            // hoangpkn : 11/08/2017 thêm để index lên form cha
            khachhang_id : 0,
            ma_kh : "",
            ten_kh : "",
            diachi_kh : "",
            // end hoang
            dtpTuNgay:"", dtpDenNgay:"",
            ma_gd : "",
            chapnhan : false,

            kt_inhd : 0,
            kieu_ht : 0,
            sloaihd_id:0,
            ma_tb:"",
            data_hopdong:{},
            data_thanhtoan:{},
            data_thuebao:{},

            //update tich hop econtract
            loaitb_id:0,
            kieuld_id: 0,

            cboLoaiHD:{
                list:[],
                SelectedValue:null,
                Enabled:false,
            },
            cboTrangThai:{
                list:[],
                SelectedValue:null,
                Enabled:false,
            },
            cboDKTraCuu:{
                list:[],
                SelectedValue:null,
                Enabled:true,
            },
            cboDichVuVT:{
                list:[],
                SelectedValue:null,
                Enabled:false,
            },
            chkLoaiHD:{
                Checked:false,
            },
            chkDVVT:{
                Checked:false,
            },
            chkTrangThai:{
                Checked:false,
            },
            chkNgayYC:{
                Checked:false,
            },
            tsbtnChapNhan:{
                Visible:true,
                Enabled:false,
            },
            btnInPL:{
                Visible:false,
                Enabled:false,
            },
            tsbtnInBB:{
                Visible:false,
                Enabled:false,
            },
            btnInHopDong:{
                Visible:false,
                Enabled:false,
            },
            tsbtnTaoHDDT:{
                Visible: true,
                Enabled:false,
            },
            

            expanded:{
                hop_dong:true,
                thanhtoan:true,
                thuebao:true
            },
            inputValue:{
                ma_gd:'',
                tt_tracu:'',
                ma_hd_ttkd:''
            },
            dataPopup : {
                "hopdong":{},
                "thanhtoan":{},
                "thuebao":{},
            },
            popupInHoSo_Param:{
                hdkh_id:0,
                hdtb_id:0,
                ma_gd:"",
            },
            modelIn:{
                n_phieu_id:"",
                ma_gd:"",
                n_hdkh_id:"",
                n_hdtb_idL:"",
                nvth_id:-1,
            },
            dataSourcesHD:{
                list:[

                ],
                cols: [
                    {
                    fieldName: "ma_gd",
                    headerText: "Mã giao dịch",
                    allowFilter: true,
                    allowSorting:false,
                    width:150,
                    freeze:"left"
                },
                {
                    fieldName: "ngay_yc",
                    headerText: "Ngày yêu cầu",
                    format:"dd/MM/yyyy",
                    type: 'date',
                    width:150,
                    allowFilter: true,
                    allowSorting:false,
                    freeze:"left"
                },
                {
                    fieldName: "ma_kh",
                    headerText: "Mã khách hàng",
                    width:150,
                    allowFilter: true,
                    allowSorting:false,
                    freeze:"left"
                },
                {
                    fieldName: "ten_kh",
                    headerText: "Tên khách hàng",
                    allowFilter: true,
                    allowSorting:false
                },
                {
                    fieldName: "diachi_kh",
                    headerText: "Địa chỉ khách hàng",
                    allowFilter: true,
                    allowSorting:false
                },
                {
                    fieldName: "so_dt",
                    headerText: "Số liên hệ",
                    allowFilter: true,
                    allowSorting:false
                },
                {
                    fieldName: "ten_loaihd",
                    headerText: "Loại HĐ/PL",
                    allowFilter: true,
                    allowSorting:false
                },
                {
                    fieldName: "nguoi_cn",
                    headerText: "Người cập nhật",
                    allowFilter: true,
                    allowSorting:false
                }

                ]
            },
            dataSourcesTT:{
                list:[],
                cols: [
                {
                    fieldName: "ma_tt",
                    headerText: "Mã thanh toán",
                    allowFilter: true,
                    allowSorting:false,
                    freeze:"left"
                },
                {
                    fieldName: "matb_dd",
                    headerText: "Mã đại diện",
                    allowFilter: true,
                    allowSorting:false,
                    freeze:"left"
                },
                {
                    fieldName: "ten_tt",
                    headerText: "Tên thanh toán",
                    allowFilter: true,
                    allowSorting:false
                },
                {
                    fieldName: "diachi_tt",
                    headerText: "Địa chỉ thanh toán",
                    allowFilter: true,
                    allowSorting:false
                },

                ]
            },
            dataSourcesTB:{
                list:[

                ],
                cols: [
                {
                    fieldName: "ma_tb",
                    headerText: "Số máy/Acc",
                    allowFilter: true,
                    allowSorting:false,
                    freeze:"left"
                },
                 {
                    fieldName: "so_ao",
                    headerText: "Số ảo",
                    allowFilter: true,
                    allowSorting:false,
                    freeze:"left"
                },
                {
                    fieldName: "ten_tb",
                    headerText: "Tên thuê bao",
                    allowFilter: true,
                    allowSorting:false
                },
                {
                    fieldName: "diachi_ld",
                    headerText: "Địa chỉ lắp đặt",
                    allowFilter: true,
                    allowSorting:false
                },
                {
                    fieldName: "diachi_tb",
                    headerText: "Địa chỉ thuê bao",
                    allowFilter: true,
                    allowSorting:false
                },
                {
                    fieldName: "ten_dvvt",
                    headerText: "Dịch vụ",
                    allowFilter: true,
                    allowSorting:false
                },
                {
                    fieldName: "loaihinh_tb",
                    headerText: "Loại hình",
                    allowFilter: true,
                    allowSorting:false
                },
                {
                    fieldName: "trangthai_hd",
                    headerText: "Trạng thái",
                    allowFilter: true,
                    allowSorting:false
                },
                {
                    fieldName: "site_id",
                    headerText: "Site ID",
                    allowFilter: true,
                    allowSorting:false
                },

                ]
            },

        }
    },
    computed:{
        dichvuvt_selected(){
            return Number(this.cboDichVuVT.SelectedValue);
        }
    },
    watch:{
        dataSourcesHD:{
            handler: function () {

                        if (this.dataSourcesHD.list == [] || this.dataSourcesHD.list.length == 0)
                            {

                                this.data_hopdong = {};
                            }
                        },
                        deep: true

            },
            dataSourcesTB:{
            handler: function () {

                        if (this.dataSourcesTB.list == [] || this.dataSourcesTB.list.length == 0)
                            {

                                this.data_thuebao = {};
                            }
                        },
                        deep: true

            },
            dataSourcesTT:{
            handler: function () {

                        if (this.dataSourcesTT.list == [] || this.dataSourcesTT.list.length == 0)
                            {

                                this.data_thanhtoan = {};
                            }
                        },
                        deep: true

            },
    },

    methods:{
       async frmTraCuuHopDong_Load(){
            this.dtpTuNgay = moment().format("DD/MM/YYYY");
            this.dtpDenNgay = moment().format("DD/MM/YYYY") ;
            await this.HT_TrangThai_HD_Combobox();
            await this.HT_Loai_HD_Combobox();
            await this.HT_DichVuVT_TraCuu_Combobox();
            await this.HT_Kieu_TK_TB_Combobox();
            this.btnInPL.Visible = (this.kt_inhd == 1);
            this.tsbtnInBB.Visible = (this.kt_inhd == 1);
            if (this.loaihd_id != 0)
                {
                    this.chkLoaiHD.Checked = true;
                    this.cboLoaiHD.SelectedValue = this.loaihd_id;
                }

                if (this.dichvuvt_id != 0)
                {

                    this.chkDVVT.Checked = true;
                    this.cboDichVuVT.SelectedValue = this.dichvuvt_id;
                }

                if (this.tthd_id != 0)
                {
                    this.chkTrangThai.Checked = true;
                    this.cboTrangThai.SelectedValue = this.tthd_id;
                }

                if (this.ngay_yc != "0")
                {
                    this.chkNgayYC.Checked = true;
                    this.dtpTuNgay = this.ngay_yc;
                    this.dtpDenNgay = this.ngay_yc;
                }

                if (this.kieu == 1){
                     this.HienThiDanhSacHDKH();
                }
                else
                {
                    if (this.kieu == 0)
                    {
                        this.tsbtnChapNhan.Visible = false;
                    }
                }
                if (this.kieu_ht == 1)
                {
                    this.tsbtnChapNhan.Visible = true;
                }
        },
        tsbtnTimKiem_Click(){
            this.HienThiDanhSacHDKH();
        },
        btnChapNhan_Click(){
                if(this.dataSourcesHD.list.length == 0){
                    this.$toast.error("Vui lòng chọn hợp đồng")
                    return;
                }
                this.dataPopup.hopdong = this.data_hopdong;
                this.dataPopup.thanhtoan = this.data_thanhtoan;
                this.dataPopup.thuebao = this.data_thuebao;
                this.dataPopup.thuebao = Object.assign({}, this.dataPopup.thuebao, this.dataPopup.hopdong); // Tú truyền thêm dữ liệu vào popup để truyền qua form cơ chế phân chia doanh thu
                this.$emit("accept", this.dataPopup);

        },
        tsbtnInHD_Click(){
           try
            {
                if (this.hdkh_id != 0)
                {
                    console.log(this.hdkh_id);
                    this.popupInHoSo_Param.hdkh_id  = this.hdkh_id ;
                    this.popupInHoSo_Param.ma_gd  = this.inputValue.ma_gd;
                    this.popupInHoSo_Param.hdtb_id = this.hdtb_id;
                    this.$refs.popupInHoSo.showModal();

                }
                else
                {
                    this.$toast.error("Chưa có thông tin hợp đồng!");
                }

            }
            catch (ex)
            {
                console.log(ex);
            }
        },
        btnInPhieuTT_Click(){
           if(this.dataSourcesTT.list.length <= 0){
               this.$toast.error("Hợp đồng chưa có thông tin thanh toán!");
               return;
           }
            this.$refs.popupInPhieuThu.showModal()
        },
        tsbtnInBB_Click(){

            this.InPhieu();
        },
        clearData(){
            this.dataSourcesHD.list = [];
            this.dataSourcesTT.list = [];
            this.dataSourcesTB.list = [];
            this.dataPopup.hopdong = {};
            this.dataPopup.thanhtoan = {};
            this.dataPopup.thuebao = {};
            this.inputValue.ma_gd = '';
            this.inputValue.tt_tracu = '';
            this.inputValue.ma_hd_ttkd = '';
            this.hdkh_id = 0;
            this.hdtb_id = 0;
        },
        loadPopup(){
            console.log(this.tthd_id);
            this.frmTraCuuHopDong_Load();
        },
        tsbtnKhuyenMai_Click(){
            if(this.data_thuebao.hdtb_id != null){
                this.$refs.TraCuuKhuyenMaiModal.showModal();
            }
            else{
                this.$toast.error("Bạn chưa chọn Thuê bao!");
            }

        },
        //update chức năng ky hợp đồng điện tử UR: BSS-109150
        tsbtnTaoHDDT_Click(){
            if(this.data_thuebao.hdtb_id != null){
                this.$refs.popupCreateEInvoice.showModal();
            }
            else{
                this.$toast.error("Bạn chưa chọn Thuê bao!");
            }

        },
      async rowSelected(args){
            this.data_hopdong = args.data;
            this.inputValue.ma_gd = args.data.ma_gd;
            this.sloaihd_id = args.data.loaihd_id;
            this.hdkh_id = args.data.hdkh_id;
            if (this.sloaihd_id == LoaiHopDong.CHUYEN_QUYEN || this.sloaihd_id == LoaiHopDong.DAT_MOI || this.sloaihd_id == LoaiHopDong.KY_LAI_HD_GOC)
                        this.btnInHopDong.Enabled = true;
                    else
                        this.btnInHopDong.Enabled = false;

                    if (this.sloaihd_id == LoaiHopDong.CHUYEN_QUYEN || this.sloaihd_id == LoaiHopDong.CHAMDUT_SD
                         || this.sloaihd_id == LoaiHopDong.THAY_DOI_DV || this.sloaihd_id == LoaiHopDong.THAY_DOI_TOCDO_ADSL
                         || this.sloaihd_id == LoaiHopDong.DI_CHUYEN || this.sloaihd_id == LoaiHopDong.DOISO_TB)
                    {
                        this.btnInPL.Enabled = true;
                    }
                    else
                    {
                        this.btnInPL.Enabled = false;
                    }
                    this.tsbtnInBB.Visible = false;
            this.dataSourcesTT.list=[];
            //this.dataSourcesTB.list.push({"ma_tb":123});
            await this.ds_hopdong_tt_theo_hdkh_id(this.hdkh_id)
            await this.ds_hopdong_tb_theo_hdkh_id(this.hdkh_id)

        },
        rowGridTTSelected(args){
            this.data_thanhtoan = args.data;
        },
       async rowGridTBSelected(args){
           this.data_thuebao = args.data;
            let vdichvuvt_id = args.data.dichvuvt_id;
            let hdtb_id = args.data.hdtb_id;
            this.hdtb_id = args.data.hdtb_id;
            //update tich hop tao hop dong
            this.loaitb_id =  args.data.loaitb_id;
            this.kieuld_id =  args.data.kieuld_id;
            if (vdichvuvt_id == DichVuVienThong.MEGAWAN || vdichvuvt_id == DichVuVienThong.TSL)
                    {
                        let mahd_ttkd = "";
                        if (vdichvuvt_id == DichVuVienThong.MEGAWAN)
                        {
                            let input1 = {};
                            input1.type = 1;
                            input1.param = hdtb_id;
                            let response = await api.fn_tt_hdtb_mgwan(this.axios,input1)
                            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data){
                                mahd_ttkd = response.data.data;
                            }
                        }
                        else
                        {
                            let input1 = {};
                            input1.type = 3;
                            input1.param = hdtb_id;
                            let response2 = await api.fn_tt_hdtb_tsl(this.axios,input1);
                            if(response2 && response2.data && response2.data.error_code && response2.data.error_code == 'BSS-00000000' && response2.data.data){
                                mahd_ttkd = response2.data.data;
                            }
                        }
                        if (mahd_ttkd != "-1" && mahd_ttkd != ""){
                            this.inputValue.ma_hd_ttkd = mahd_ttkd;
                        }
                        else{
                            this.inputValue.ma_hd_ttkd = "";
                        }

                    }
                    else
                    {
                        this.inputValue.ma_hd_ttkd = "";
                    }

        },
        gridviewThueBao_DoubleClick(args){
            this.ma_tb = args.rowData.ma_tb;
            this.thuebao_id = args.rowData.thuebao_id;
            this.$refs.traCuuThongSoKTModal.showModal()
            //console.log(args);
        },
        layThongTinTimKiem(){
                var dieukien=0
                var loaihd_id=0
                var dichvuvt_id=0
                var tthd_id=0
                var tu_ngay=''
                var den_ngay=''
                if(this.inputValue.tt_tracu!=''){
                dieukien=this.cboDKTraCuu.SelectedValue!=null?this.cboDKTraCuu.SelectedValue.replace(':values',this.inputValue.tt_tracu):0
                }
                if(this.chkLoaiHD.Checked){
                    loaihd_id=this.cboLoaiHD.SelectedValue;
                }
                if(this.chkDVVT.Checked){
                    dichvuvt_id=this.cboDichVuVT.SelectedValue
                }

                if(this.chkTrangThai.Checked){
                    tthd_id=this.cboTrangThai.SelectedValue
                }

                if(this.chkNgayYC.Checked){
                tu_ngay=moment(this.dtpTuNgay,'DD/MM/YYYY').format('DDMMYYYY')
                den_ngay=moment(this.dtpDenNgay, 'DD/MM/YYYY').format('DDMMYYYY')
                }

                let dk_tracuu=this.cboDKTraCuu.list.find(item=>item.ID==this.cboDKTraCuu.SelectedValue)
                if(dk_tracuu==undefined){
                return
                }
                if(dk_tracuu.NAME=='Số ảo'){
                return {
                    id:'so_ao',
                    data:{
                    dichVuVTId: dichvuvt_id,
                    inHD: 0,
                    loaiHDId: loaihd_id,
                    maLt: this.inputValue.tt_tracu.trim(),
                    tthdId: tthd_id,
                    tuNgay: tu_ngay,
                    denNgay: den_ngay,
                    donviDLId:this.donviDLId,
                    donviId:this.donviId,
                    nhanvienId:this.nhanvienId,
                    }
                }
                }else if(dk_tracuu.NAME=='Site ID'){
                return {
                    id:'site_id',
                    data:this.inputValue.tt_tracu.trim()
                }

                }else if(dk_tracuu.NAME=='Số hợp đồng KHDN'){
                return {
                    id:'so_hd_khdn',
                    data:this.inputValue.tt_tracu.trim()
                }
                }else if(dk_tracuu.NAME=='Số phụ lục KHDN'){
                return {
                    id:'so_pl_khdn',
                    data:this.inputValue.tt_tracu.trim()
                }
                }else if(dk_tracuu.NAME=='Tên miền'){
                return {
                    id:'ten_mien',
                    data:this.inputValue.tt_tracu.trim()
                }
                }else {
                return {
                    id:'khac',
                    data: {
                    dichVuVTId: dichvuvt_id,
                    inHD: 0,
                    loaiHDId: loaihd_id,
                    dieuKien: dieukien,
                    tthdId: tthd_id,
                    tuNgay: tu_ngay,
                    denNgay: den_ngay,
                    donviDLId:this.donviDLId,
                    donviId:this.donviId,
                    nhanvienId:this.nhanvienId,
                    }
                }
            }
        },

      layThongTinTimKiem_V2(){
        var dieukien=0
        var loaihd_id=0
        var dichvuvt_id=0
        var tthd_id=0
        var tu_ngay=''
        var den_ngay=''
        if(this.chkLoaiHD.Checked){
          loaihd_id=this.cboLoaiHD.SelectedValue;
        }
        if(this.chkDVVT.Checked){
          dichvuvt_id=this.cboDichVuVT.SelectedValue
        }

        if(this.chkTrangThai.Checked){
          tthd_id=this.cboTrangThai.SelectedValue
        }

        if(this.chkNgayYC.Checked){
          tu_ngay=moment(this.dtpTuNgay,'DD/MM/YYYY').format('DDMMYYYY')
          den_ngay=moment(this.dtpDenNgay, 'DD/MM/YYYY').format('DDMMYYYY')
        }
        if(this.inputValue.tt_tracu != null && this.inputValue.tt_tracu != ""){
          dieukien = [{"KIEUTK_ID": this.cboDKTraCuu.SelectedValue,"GIATRI":this.inputValue.tt_tracu}]
        }

          return {

            vdichvuvt_id: dichvuvt_id,
            vin_hd: 0,
            vloaihd_id: loaihd_id,
            vjs_dieukien: dieukien == 0?0:JSON.stringify(dieukien),
            vtthd_id: tthd_id,
            vtungay_yc: tu_ngay,
            vdenngay_yc: den_ngay,
            vdonvi_dl_id:this.donviDLId,
            vdonvi_id:this.donviId,
            vnhanvien_id:this.nhanvienId,
            }
      },

        async HienThiDanhSacHDKH(){
                this.dataSourcesHD.list=[]
                this.dataSourcesTT.list=[]
                this.dataSourcesTB.list=[]
                let tt=this.layThongTinTimKiem_V2();
                try{
                  this.loading(true)
                  let response = await api.tra_cuu_hop_dong_dieukien(this.axios, tt)
                  if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesHD.list=response.data.data
                    if(this.dataSourcesHD.list.length>0){
                      this.tsbtnChapNhan.Enabled = true
                    }else{
                      this.tsbtnChapNhan.Enabled = false
                    }
                  }else{
                    this.dataSourcesHD.list=[]
                    this.tsbtnChapNhan.Enabled = false
                  }
                }catch(e){
                  this.$alert('Đã xảy ra lỗi', '', {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: 'OK',
                    type: 'error'
                  })
                }finally{
                  this.loading(false)
                }
        },
        async HT_Loai_HD_Combobox(){
            try{
                this.loading(true)
                let response = await api.loai_hd(this.axios);
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.cboLoaiHD.list=response.data.data;
                    if(this.cboLoaiHD.list.length>0){
                    this.cboLoaiHD.SelectedValue=this.cboLoaiHD.list[0].ID
                    }
                }else{
                    this.cboLoaiHD.list=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách loại hợp đồng')
            }finally{
                this.loading(false)
            }
        },
        async HT_TrangThai_HD_Combobox(){
            try{
                this.loading(true)
                let response = await api.trangthai_hd(this.axios);
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.cboTrangThai.list=response.data.data;
                    if(this.cboTrangThai.list.length>0){
                    this.cboTrangThai.SelectedValue=this.cboTrangThai.list[0].ID
                    }
                }else{
                    this.cboTrangThai.list=[]
                }
            }catch(e){
                this.$toast.error('Không load được trạng thái hợp đồng')
            }finally{
                this.loading(false)
            }
        },
        async HT_Kieu_TK_TB_Combobox(){
            try{
            this.loading(true)
            let response=await api.tieuchi_tracuu_v2(this.axios)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                this.cboDKTraCuu.list=response.data.data
                if(this.cboDKTraCuu.list.length>0){
                this.cboDKTraCuu.SelectedValue=this.cboDKTraCuu.list[0].ID
                }
            }else{
                this.cboDKTraCuu.list=[]
            }
            }catch(e){
            this.$toast.error('Không load được danh sách tiêu chí tra cứu')
            }finally{
            this.loading(false)
            }
        },
        async HT_DichVuVT_TraCuu_Combobox(){
            try{
                this.loading(true)
                let response=await api.dichvu(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.cboDichVuVT.list=response.data.data
                    if(this.cboDichVuVT.list.length>0){
                        this.cboDichVuVT.SelectedValue=this.cboDichVuVT.list[0].ID
                    }
                }else{
                    this.cboDichVuVT.list=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách trạng thái dịch vụ')
            }finally{
                this.loading(false)
            }
        },

        async hopdong_theo_site_id(siteId){
            this.dataSourcesHD.list=[]
            try{
                this.loading(true)
                let response = await api.hopdong_theo_site_id(this.axios, siteId)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesHD.list=response.data.data
                    if(this.dataSourcesHD.list.length>0){
                        this.tsbtnChapNhan.Enabled = true
                    }else{
                        this.tsbtnChapNhan.Enabled = false
                    }
                }else{
                    this.dataSourcesHD.list=[]
                    this.tsbtnChapNhan.Enabled = false
                }
            }catch(e){
                this.$alert('Đã xảy ra lỗi', '', {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: 'OK',
                    type: 'error'
                })
            }finally{
                this.loading(false)
            }
        },
        async hopdong_theo_tenmien(tenMien){
            this.dataSourcesHD.list=[]
            try{
                this.loading(true)
                let response = await api.hopdong_theo_tenmien(this.axios, tenMien)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {

                    this.dataSourcesHD.list=response.data.data
                    if(this.dataSourcesHD.list.length>0){
                        this.tsbtnChapNhan.Enabled = true
                    }else{
                        this.tsbtnChapNhan.Enabled = false
                    }
                }else{
                    this.dataSourcesHD.list=[]
                    this.tsbtnChapNhan.Enabled = false
                }
            }catch(e){
                this.$alert('Đã xảy ra lỗi', '', {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: 'OK',
                    type: 'error'
                })
            }finally{
                this.loading(false)
            }
        },
        async hopdong_theo_so_hd(soHD){
            this.dataSourcesHD.list=[]
            try{
                this.loading(true)
                let response = await api.hopdong_theo_tenmien(this.axios, soHD)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesHD.list=response.data.data
                    if(this.dataSourcesHD.list.length>0){
                        this.tsbtnChapNhan.Enabled = true
                    }else{
                        this.tsbtnChapNhan.Enabled = false
                    }
                }else{
                    this.dataSourcesHD.list=[]
                    this.tsbtnChapNhan.Enabled = false
                }
            }catch(e){
                console.log(e);
                this.$alert('Đã xảy ra lỗi', '', {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: 'OK',
                    type: 'error'
                })
            }finally{
                this.loading(false)
            }
        },
        async hopdong_theo_so_pl(soPL){
            this.dataSourcesHD.list=[]
            try{
                this.loading(true)
                let response = await api.hopdong_theo_so_pl(this.axios, soPL)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesHD.list=response.data.data
                    if(this.dataSourcesHD.list.length>0){
                        this.tsbtnChapNhan.Enabled = true
                    }else{
                        this.tsbtnChapNhan.Enabled = false
                    }
                }else{
                    this.dataSourcesHD.list=[]
                    this.tsbtnChapNhan.Enabled = false
                }
            }catch(e){
                this.$alert('Đã xảy ra lỗi', '', {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: 'OK',
                    type: 'error'
                })
            }finally{
                this.loading(false)
            }
        },
        async hopdong_khac(data){
            this.dataSourcesHD.list=[]
            try{
                this.loading(true)
                let response = await api.hopdong_khac(this.axios, data)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesHD.list=response.data.data
                    if(this.dataSourcesHD.list.length>0){
                       this.tsbtnChapNhan.Enabled = true
                    }else{
                        this.tsbtnChapNhan.Enabled = false
                    }
                }else{
                    this.dataSourcesHD.list=[]
                    this.tsbtnChapNhan.Enabled = false
                }
            }catch(e){
                console.log(e);
                this.$alert('Đã xảy ra lỗi', '', {
                    dangerouslyUseHTMLString: true,
                    confirmButtonText: 'OK',
                    type: 'error'
                })
            }finally{
                this.loading(false)
            }
        },
        async ds_hopdong_tt_theo_hdkh_id(hdkh_id){
            try{
                this.loading(true)
                this.dataSourcesTT.list=[]
                let response = await api.ds_hopdong_tt_theo_hdkh_id(this.axios, hdkh_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesTT.list=response.data.data
                }else{
                    this.dataSourcesTT.list=[]
                }
            }catch(e){
                console.log(e);
                this.$toast.error('Không load được thông tin thanh toán')
            }finally{
                this.loading(false)
            }
        },
        async ds_hopdong_tb_theo_hdkh_id(hdkh_id){
            try{
                this.loading(true)
                this.dataSourcesTB.list=[];
                let response = await api.ds_hopdong_tb_theo_hdkh_id(this.axios, hdkh_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesTB.list=response.data.data

                }else{
                    this.dataSourcesTB.list=[];
                }

            }catch(e){
                console.log(e);

                this.$toast.error('Không load được thông tin thuê bao')
            }finally{
                this.loading(false)
            }
        },
        async InPhieu () {
            try {
                if(this.dataSourcesHD.list.length == 0){
                    this.$root.$toast.error('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
                    return
                }
                 if(this.dataSourcesTB.list.length == 0){
                    this.$root.$toast.error('Không tìm thấy thông tin thuê bao. Bạn hãy kiểm tra lại!')
                    return
                }
                console.log(this.data_thuebao);
                let nhdkhid = []
                let nhdtbid = []
                let nphieuid = []
                nhdkhid = this.data_thuebao.hdkh_id
                nhdtbid = this.data_thuebao.hdtb_id
                nphieuid = this.data_thuebao.phieu_id
                this.modelIn.n_phieu_id = nphieuid
                this.modelIn.ma_gd = this.data_thuebao.ma_gd.trim();
                this.modelIn.n_hdkh_id = nhdkhid
                this.modelIn.n_hdtb_id = nhdtbid
                this.modelIn.nvth_id = -1
                this.$refs.popupInBB.showModal()
            } catch (error) {
                this.$root.$toast.error('Có lỗi khi in phiếu: ' + error.message)
            }
        },
        clickShowHideHD(){
            this.expanded.hop_dong=!this.expanded.hop_dong
        },
         clickShowHideTB(){
            this.expanded.thuebao=!this.expanded.thuebao
        },
        clickShowHideTT(){
            this.expanded.thanhtoan=!this.expanded.thanhtoan
        },
        showModal() {
            this.$refs['popupSearchContract'].show()
        },
        hideModal() {
            this.$refs['popupSearchContract'].hide()
        },
    },
    created(){
        if (this.$route.params.tag != null && this.$route.params.tag != '' && this.$route.params.tag.length > 0) {
            this.kt_inhd = this.$route.params.tag;
        }
        else if(this.$route.query.tag != null && this.$route.query.tag != '' && this.$route.query.tag.length > 0){
            this.kt_inhd = this.$route.query.tag;
        }
    },
    mounted(){
        console.log("Là popup: " + this.isPopup);
      if(this.isPopup == 1){
        console.log("Load data");
        this.frmTraCuuHopDong_Load();
      }

    }
}
</script>
<style>
  .non-ative a {
    color: #d3d3d3 !important;
  }
</style>
