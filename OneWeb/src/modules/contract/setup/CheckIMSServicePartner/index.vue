<template src="./CheckIMSServicePartner.html"></template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from './api'
import "@/assets/vendor/jquery/split";
import Vue from "vue";
import moment from "moment";
import { DialogPlugin } from "@syncfusion/ej2-vue-popups";
//import {DichVuVienThong,LoaiHopDong} from './ThamSo';
import {LoaiHopDong, DichVuVienThong,TrangThaiHD,LOAI_DV} from "@/const/enums";
Vue.use(DialogPlugin);
export default {
    components: {
    breadcrumb,LoaiHopDong,DichVuVienThong,TrangThaiHD,LOAI_DV
  },
  data: function () {
    return {
    
     TRANGTHAI_DONGBO:{
         DONGBO_CM:5,
     },
     TRANGTHAI_PORT:{
          CHUA_SD: 1,
          TAM_CAP : 2,
          DA_SD: 3,
     },
     KIEU_HD:{
        TAI_GD:1,
        TAI_NHA:2,
     },
     ThongTin:{
            txtIP_KH:"",txtOnuPass:"",txtNhanVien:"",txtNV_TiepThi: "",
            txtMaGD : "",txtMaTB : "",txtTenTB : "",txtKhuvuc : "",
            txtDiaChiLD : "",txtDiaChiLD_Cu : "",txtThongTvarra :"",txtDiaChiTB:"",
            txtLyDoTra :"",txtTramTB :"",txtSoMayTN :"",
            txtTuCapGoc :"",txtDoiCapGoc :"",txtCapGoc :"",
            txtHopCapNgon :"",txtTuCapNgon :"",txtDoiCapNgon :"",
            txtCapNgon :"",txtTuyenCap :"",txtLienTu :"",
            txtGhiChu :"", txtUsername :"",
            txtPassword :"",txtVCI :"",txtVPI :"",
            txtSlot :"",txtPort :"",txtPortMdf :"",
            txtSystem :"",txtRack :"",txtShelf :"",
            txtAdsl_Port :"",txtKieuLD :"",
            txtNDGiao :"",txtTongTien : "0", txtSoMetDay : "0", 
            lblLienHe:"",vsdt_lh:"",txtNoiDungTH:"",txtSLID:"",
     },
     rdoCheck:true,
     chkHen:false,chkLayTSo:false,
     chkNgayGV:true,isDischkNgayGV:false,
     chkNgayBG:true,isDischkNgayBG:false,
     dtpNgayBG:"",isDisDtpNgayBG:false,
     dtpNgayGV:"",isDisDtpNgayGV:false,
     tag:"", kt_status:"", lbldvgt:"",
    _flagTSKT:"",
    _flagTTTB_IMS:0,
    donviId : 0,hdtb_id: 0,phieu_id:0,
    donvi_id:0,
    kenhthu_id:-1, khachhang_id : 0,thanhtoan_id :0,
    diachikh_id :0,apkh_id :0, system:"",phokh_id :0,
    phuongkh_id :0, quankh_id :0, tinhkh_id :0,
    sonhakh : "",diachitt_id :0,phott_id :0,
    aptt_id :0,phuongtt_id :0,quantt_id :0,
    tinhtt_id : 0,sonhatt : "",tthd_id : -1,
    tungay: new Date(),denngay: new Date(),
     quytrinh_id : -1,
     huonggiao_id: -1,
     loaitb_id:-1,
     nguoiGv_id:-1,
     luong_id : -1,
     loaicap_id:-1,
     bras_id:-1,
     dslam_id:-1,
     loaihd_id:-1,
     nhanvien_id:-1,phanvung_id:-1,
     kieuhd_id:0,
     thietbi_id:-1,
     ctdv_id:-1,
     dsloaihd_id:"",
     dsdichvuvt_id:"",
     dichvuvt_id : 11,
     thuebao_id: -1,
     kieuld_id:-1,
     port_id:0,
     port_id_cu:0,
     vci_vpi_id:0,
     vci_vpi_id_cu:0,nhanvien_tc_id: -1, congviec_th:"",
     lblTongS:"Tổng số: 0/0",
     dtThaoTac:[],
     current_Index:-1,
     options:{
         cboKenhThu:[],
         cboKieu_HD:[],
         cboLoaiHD:[],
         cboLoaiCap:[],
         cboBras:[],
         cboLoaiTB:[],
         cboDsTbiIMS:[],
         cboChiTietDVIMS:[],
         cboNguoiGV:[],
         cboDsLam:[],
         cboDsQuyTrinh:[],
     },
     table: {
                loading: false,
                options: [],
                config: [
                {
                    headerText: 'Số máy',                  
                    fieldName: 'ma_tb',
                    allowFiltering: true,
                    allowSorting: true,
                    
                    
                },
                {
                    headerText: 'Số chính',
                    fieldName: 'ma_tb_cha',
                     allowFiltering: true,
                    allowSorting: true,
                    
                },
                {
                    headerText: 'Tên thuê bao',
                    fieldName: 'ten_tb',
                     allowFiltering: true,
                    allowSorting: true,
                    
                },
                {
                    headerText: 'Địa chỉ lắp đặt',
                    fieldName: 'diachi_ld',
                    allowFiltering: true,
                    allowSorting: true,
                   
                },
                {
                    headerText: 'Mã khu vực',
                    fieldName: 'ma_kv',
                     allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Tên khu vực',
                    fieldName: 'ten_kv',
                     allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Ngày YC',
                    fieldName: 'ten_kieuld',
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
                    headerText: 'Đơn vị hẹn',
                    fieldName: 'dv_hen',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Hẹn từ',
                    fieldName: 'giohen_tu',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Hẹn đến',
                    fieldName: 'giohen_den',
                   allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Gói KT',
                    fieldName: '',
                    allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Gói CT',
                    fieldName: '',
                     allowFiltering: true,
                    allowSorting: true,
                },
                {
                    headerText: 'Giờ còn lại',
                    fieldName: '',
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
                    headerText: 'Trạng thái in',
                    fieldName: 'luu_in',
                    allowFiltering: true,
                    allowSorting: true,
                },
                
                 {
                    headerText: 'Ngày BG',
                    fieldName: 'donViGiao',
                     allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'Ngày thực hiện',
                    fieldName: 'thoiGianGiao',
                     allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'Trạng thái',
                    fieldName: 'trangthai_hd',
                     allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'Đơn vị giao',
                    fieldName: '',
                     allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'Lý do tồn',
                    fieldName: '',
                    allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'Ghi chú tồn',
                    fieldName: '',
                    allowFiltering: true,
                    allowSorting: true,
                },
                 {
                    headerText: 'Ghi chú',
                    fieldName: 'ghichu',
                    allowFiltering: true,
                    allowSorting: true,
                },   
                ]
    },
    tableNV:{
        loading: false,
        options: [],
        config: [
            {
                headerText: 'Tên nhân viên',                  
                fieldName: 'ma_tb',
                allowFiltering: true,
                allowSorting: true,        
            },
            {
                headerText: 'Điện thoại',                  
                fieldName: 'ma_tb',
                allowFiltering: true,
                allowSorting: true,        
            }, 
            {
                headerText: 'Nhiệm vụ',                  
                fieldName: 'ma_tb',
                allowFiltering: true,
                allowSorting: true,        
            }, 
            {
                headerText: 'Ghi chú',                  
                fieldName: 'ma_tb',
                allowFiltering: true,
                allowSorting: true,        
            },  
        ] 
    },
    dgvdangkyDV:{
        loading: false,
        options: [],
        config: [
            {
                headerText: 'Mã DVGT',                  
                fieldName: 'ma_dvgt',
                allowFiltering: true,
                allowSorting: true,        
            },
            {
                headerText: 'Tên dịch vụ',                  
                fieldName: 'ten_dvgt',
                allowFiltering: true,
                allowSorting: true,        
            }, 
            {
                headerText: 'Kiểu YC',                  
                fieldName: 'kieu',
                allowFiltering: true,
                allowSorting: true,        
            }, 
            {
                headerText: 'Nội dung',                  
                fieldName: 'noidung',
                allowFiltering: true,
                allowSorting: true,        
            },  
        ] 
    },
    }
  },
  computed:{
      rdoCheckPhieu:function(){
            return (this.rdoCheck == "true")?0:1;
      },
      CURRENT_PHIEU_ID:function(){
          return this.$refs.danhsach.getSelectedRecords()[0].phieu_id;
      }
      
  },
    methods:{
        xuLyTag(tag){
            try
                {
                    if (tag)
                    {
                        let words = tag.split('+');
                        if (words.length >= 1)
                            this.tthd_id = words[0];
                        if (words.length >= 2)
                        {
                            this.dsloaihd_id = words[1];
                            this.dsloaihd_id = this.dsloaihd_id.replaceAll(';', ',');
                        }
                        if (words.length >= 3)
                        {
                            this.dsdichvuvt_id = words[2];
                            this.dsdichvuvt_id = this.dsdichvuvt_id.replaceAll(';', ',');
                        }
                        if (words.length >= 4)
                        {
                            this._flagTSKT = words[3];
                            this._flagTSKT = this._flagTSKT.replaceAll(';', ',');
                        }

                        if (words.length >= 5)
                        {
                            this._flagTTTB_IMS =words[4];
                        }
                    }
                    else
                    {
                        this.tthd_id = TrangThaiHD.DANG_THI_CONG;
                        this.dsloaihd_id = "1";
                        this.dsdichvuvt_id = "11";
                    }
                   
                }
                catch(e)
                {
                    console.log(e);
                    this.tthd_id = TrangThaiHD.DANG_THI_CONG;
                    this.dsloaihd_id = "1";
                    this.dsdichvuvt_id = "11";
                }
        },
        btnLayTTMoi_Click(){
             //this.$refs.danhsach.setCurrentSelectedRow(0);
            this.HienThiDanhSachHDTB();
        },
        LayIndexTheoPhieuID(_phieu_id, data){
            if (data == "" || data == null) return -1;
            var _index = -1;
            data.forEach((obj,index)=>{
                
                if(obj.phieu_id == _phieu_id) 
                {
                    _index = index;
                    return;
                }
            })
            return _index;
        },
        async tsbtnChapNhan_Click(){
            try{
                if(!this.CapNhat()) return;
                this.$toast.success("Cập nhật dữ liệu thành công !");
                var vhdtb_id = this.hdtb_id;
                let hienthi = this.HienThiDanhSachHDTB();
                await Promise.all([hienthi]);
                
                 for (var i = 0; i < this.table.options.length; i++)
                {
                    console.log(vhdtb_id);
                    if (this.table.options[i].hdtb_id == vhdtb_id)
                    {
                        console.log("i là: " + i);
                        this.$refs.danhsach.setCurrentSelectedRow(i);
                        this.$refs.danhsach.flagSelectedRowIndexes = [];
                        this.$refs.danhsach.flagSelectedRowIndexes.push(i);
                        break;
                    }
                }
            }
            catch(e){
                console.log(e);
            }

            
        },
        CapNhat(){
            if(!this.KiemTra_DL(this.luong_id)) return false;
            var vphieu_id = 0;
            var vhdtb_id = 0;
            if (!PHAILAM(luong_id, "GIAOVIEC")) //Tự động giao phiếu
            {
                var vngaygiao = this.dtpNgayBG;

                // Đang check API
            //     if (this.PHAILAM(this.luong_id, "GIAOVIEC_NVQL_CAP"))
            //     {
            //         var dsNV = [];
            //          api.lay_dsNVTheoPhieuId(this.axios,{
            //         "phieu_id":this.phieu_id,
            //         "kieu_id":2,
            //         "nhanvien_id":this.donviId,
            //         }).then((response) =>{           
            //             if ( response.data.error_code === "BSS-00000000" && response.data.data !== "" && response.data.data != null && response.data.data.length > 0) {                          
            //                 dsNV = response.data.data;
            //                 console.log(dsNV);
            //             }
            //             })
            //             .catch((error) => {                       
            //                 console.log(error);
            //         });

            //             if (dsNV != null && dsNV.length > 0)
            //             {
            //                 if (dsNV[0].nhanvien_id != "")
            //                 {
            //                     this.Update_tt_giaoviec(this.nhanvien_id, vngaygiao, this.hdtb_id, this.phieu_id, this.nguoiGv_id);
            //                 }
            //             }
            //             else
            //             {
            //                 TaoDuLieu_NVTH();
            //                 this.Update_tt_giaoviec(this.nhanvien_id, vngaygiao, this.hdtb_id, this.phieu_id, this.nguoiGv_id);
            //             }
            //     }
            //     else
            //     {
            //         objgiaophieu.Delete(phieu_id);
            //         TaoDuLieu_NVTH();
            //         objgiaophieu.Insert(dsnhanviengp);
            //         this.Update_tt_giaoviec(this.nhanvien_id, vngaygiao, hdtb_id, phieu_id, Convert.Tovar64(cboNguoiGV.SelectedValue));
            //     }
             }
            var nIndex = this.LayIndexTheoPhieuID(this.CURRENT_PHIEU_ID, this.table.options);

            if (PHAILAM(luong_id, "CAPNHAT_PORT"))
            {
                //Cập nhật trạng thái port
                var vport_id = 0;
                var vvci_vpi_id = 0;
                if (this.table.options[nIndex].port_id != "")
                    vport_id = this.table.options[nIndex].port_id;
                if (this.table.options[nIndex].vci_vpi_id != "")
                    vvci_vpi_id = this.table.options[nIndex].vci_vpi_id;
                //Kiểm tra xem có sự thay đổi port hay không
                var dsktradb = [];
                var dsktrahd = [];
                if (vport_id != 0)
                {
                    if (vport_id != this.port_id || vvci_vpi_id != this.vci_vpi_id)  //Có sự thay đổi port mới update trạng thái
                    {
                        //region Cập nhật port cũ

                        dsktradb = this.kiemtra_dieukien_port(this.ThongTin.txtMaTB.trim(),vport_id,vvci_vpi_id,1).data.data;
                        dsktrahd = this.kiemtra_dieukien_port(this.ThongTin.txtMaTB.trim(),vport_id,vvci_vpi_id,2).data.data;

                        if (dsktradb.length == 0 && dsktrahd.length == 0)
                        {
                            this.CapNhat_TrangThai_Port(vport_id, vvci_vpi_id, this.TRANGTHAI_PORT.CHUA_SD);
                            this.CapNhat_TrangThai_PortID(vport_id, this.TRANGTHAI_PORT.CHUA_SD);
                            this.CapNhat_ds_matb(vport_id, vvci_vpi_id, null);
                        }
                        else if (dsktradb.length != 0 && dsktrahd.length == 0)
                        {
                            var vds_matb = "";
                            for (var i = 0; i < dsktradb.length; i++)
                            {
                                vds_matb = vds_matb + dsktradb[i].ma_tb + "; ";
                                if (vds_matb.length > 3000)
                                    break;
                            }
                            if (vds_matb != "")
                                vds_matb = vds_matb.substring(0, vds_matb.length - 2);

                            this.CapNhat_TrangThai_Port(vport_id, vvci_vpi_id, this.TRANGTHAI_PORT.DA_SD);
                            this.CapNhat_TrangThai_PortID(vport_id, this.TRANGTHAI_PORT.DA_SD);
                            this.CapNhat_ds_matb(vport_id, vvci_vpi_id, vds_matb);
                        }
                        else if (dsktradb.length == 0 && dsktrahd.length != 0)
                        {
                            var vds_matb = "";
                            for (var i = 0; i < dsktrahd.length; i++)
                            {
                                vds_matb = vds_matb + dsktrahd[i].ma_tb + "; ";
                                if (vds_matb.length > 3000)
                                    break;
                            }
                            if (vds_matb != "")
                                vds_matb = vds_matb.substring(0, vds_matb.length - 2);

                            this.CapNhat_TrangThai_Port(vport_id, vvci_vpi_id, TRANGTHAI_PORT.TAM_CAP);
                            this.CapNhat_TrangThai_PortID(vport_id, TRANGTHAI_PORT.DA_SD);
                            this.CapNhat_ds_matb(vport_id, vvci_vpi_id, vds_matb);
                        }
                        else if (dsktradb.length != 0 && dsktrahd.length != 0)
                        {
                            var vds_matb = "";
                            for (var i = 0; i < dsktradb.length; i++)
                            {
                                vds_matb = vds_matb + dsktradb[i].ma_tb + "; ";
                                if (vds_matb.length > 3000)
                                    break;
                            }
                            for (var i = 0; i < dsktrahd.length; i++)
                            {
                                vds_matb = vds_matb + dsktrahd[i].ma_tb + "; ";
                                if (vds_matb.length > 3000)
                                    break;
                            }

                            if (vds_matb != "")
                                vds_matb = vds_matb.substring(0, vds_matb.length - 2);

                            this.CapNhat_TrangThai_Port(vport_id, vvci_vpi_id, TRANGTHAI_PORT.DA_SD);
                            this.CapNhat_TrangThai_PortID(vport_id, TRANGTHAI_PORT.DA_SD);
                            this.CapNhat_ds_matb(vport_id, vvci_vpi_id, vds_matb);
                        }
                        //end cập nhật port cũ

                        //region Cập nhật port mới
                        //Kiểm tra port đang chọn
                        dsktradb = this.kiemtra_dieukien_port(this.ThongTin.txtMaTB.trim(), this.port_id, this.vci_vpi_id, 1).data.data;
                        dsktrahd = this.kiemtra_dieukien_port(this.ThongTin.txtMaTB.trim(), this.port_id, this.vci_vpi_id, 2).data.data;
                        if (dsktradb.length == 0 && dsktrahd.length == 0)
                        {
                            this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, TRANGTHAI_PORT.TAM_CAP);
                            this.CapNhat_TrangThai_PortID(port_id, TRANGTHAI_PORT.DA_SD);
                            this.CapNhat_ds_matb(port_id, vci_vpi_id, this.ThongTin.txtMaTB.trim());
                        }
                        else if (dsktradb.length != 0 && dsktrahd.length == 0)
                        {
                            var vds_matb = "";
                            for (var i = 0; i < dsktradb.length; i++)
                            {
                                vds_matb = vds_matb + dsktradb[i].ma_tb + "; ";
                                if (vds_matb.length > 3000)
                                    break;
                            }
                            vds_matb = vds_matb + this.ThongTin.txtMaTB.trim();

                            this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, TRANGTHAI_PORT.DA_SD);
                            this.CapNhat_TrangThai_PortID(port_id, TRANGTHAI_PORT.DA_SD);
                            this.CapNhat_ds_matb(port_id, vci_vpi_id, vds_matb);
                        }
                        else if (dsktradb.length == 0 && dsktrahd.length != 0)
                        {
                            var vds_matb = "";
                            for (var i = 0; i < dsktrahd.length; i++)
                            {
                                vds_matb = vds_matb + dsktrahd.Tables[0].Rows[i]["ma_tb"].ToString() + "; ";
                                if (vds_matb.length > 3000)
                                    break;
                            }
                            vds_matb = vds_matb + this.ThongTin.txtMaTB.trim();

                            this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, TRANGTHAI_PORT.TAM_CAP);
                            this.CapNhat_TrangThai_PortID(port_id, TRANGTHAI_PORT.DA_SD);
                            this.CapNhat_ds_matb(port_id, vci_vpi_id, vds_matb);
                        }
                        else if (dsktradb.length != 0 && dsktrahd.length != 0)
                        {
                            var vds_matb = "";
                            for (var i = 0; i < dsktradb.length; i++)
                            {
                                vds_matb = vds_matb + dsktradb[i].ma_tb + "; ";
                                if (vds_matb.length > 3000)
                                    break;
                            }
                            for (var i = 0; i < dsktrahd.length; i++)
                            {
                                vds_matb = vds_matb + dsktrahd[i].ma_tb + "; ";
                                if (vds_matb.length > 3000)
                                    break;
                            }
                            vds_matb = vds_matb + this.ThongTin.txtMaTB.trim();

                            this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, TRANGTHAI_PORT.DA_SD);
                            this.CapNhat_TrangThai_PortID(port_id, TRANGTHAI_PORT.DA_SD);
                            this.CapNhat_ds_matb(port_id, vci_vpi_id, vds_matb);
                        }
                    //end cập nhật port mới
                    }
                }
                else
                {
                    //region Cập nhật port mới
                    //Kiểm tra port đang chọn
                    dsktradb = this.kiemtra_dieukien_port(this.ThongTin.txtMaTB.trim(), this.port_id, this.vci_vpi_id, 1).data.data;
                    dsktrahd = this.kiemtra_dieukien_port(this.ThongTin.txtMaTB.trim(), this.port_id, this.vci_vpi_id, 2).data.data;
                    if (dsktradb.length == 0 && dsktrahd.length == 0)
                    {
                        //nếu ko có thuê bao nào sử dụng port thì update về chưa sử dụng
                        this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, TRANGTHAI_PORT.TAM_CAP);
                        this.CapNhat_TrangThai_PortID(port_id, TRANGTHAI_PORT.DA_SD);
                        this.CapNhat_ds_matb(port_id, vci_vpi_id, this.ThongTin.txtMaTB.trim());
                    }
                    else if (dsktradb.length != 0 && dsktrahd.length == 0)
                    {
                        var vds_matb = "";
                        for (var i = 0; i < dsktradb.length; i++)
                        {
                            vds_matb = vds_matb + dsktradb[i].ma_tb + "; ";
                            if (vds_matb.length > 3000)
                                    break;
                        }
                        vds_matb = vds_matb + this.ThongTin.txtMaTB.trim();;

                        this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, TRANGTHAI_PORT.DA_SD);
                        this.CapNhat_TrangThai_PortID(port_id, TRANGTHAI_PORT.DA_SD);
                        this.CapNhat_ds_matb(port_id, vci_vpi_id, vds_matb);
                    }
                    else if (dsktradb.length == 0 && dsktrahd.length != 0)
                    {
                        var vds_matb = "";
                        for (var i = 0; i < dsktrahd.length; i++)
                        {
                            vds_matb = vds_matb + dsktrahd[i].ma_tb + "; ";
                            if (vds_matb.length > 3000)
                                    break;
                        }
                        vds_matb = vds_matb + this.ThongTin.txtMaTB.trim();;

                        this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, TRANGTHAI_PORT.TAM_CAP);
                        this.CapNhat_TrangThai_PortID(port_id, TRANGTHAI_PORT.DA_SD);
                        this.CapNhat_ds_matb(port_id, vci_vpi_id, vds_matb);
                    }
                    else if (dsktradb.length != 0 && dsktrahd.length != 0)
                    {
                        var vds_matb = "";
                        for (var i = 0; i < dsktradb.length; i++)
                        {
                            vds_matb = vds_matb + dsktradb[i].ma_tb + "; ";
                            if (vds_matb.length > 3000)
                                    break;
                        }
                        for (var i = 0; i < dsktrahd.length; i++)
                        {
                            vds_matb = vds_matb + dsktrahd[i].ma_tb + "; ";
                            if (vds_matb.length > 3000)
                                    break;
                        }
                        vds_matb = vds_matb + this.ThongTin.txtMaTB.trim();;

                        this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, TRANGTHAI_PORT.DA_SD);
                        this.CapNhat_TrangThai_PortID(port_id, TRANGTHAI_PORT.DA_SD);
                        this.CapNhat_ds_matb(port_id, vci_vpi_id, vds_matb);
                    }
                   //end
                }

                                // Xử lý tiếp hoàn công CAPNHAT_THONGTIN_PORT_IMS

            }
             if (this.PHAILAM(this.luong_id, "CAPNHAT_CAP"))
            {
                var vdoicap_ngon = -1;
                var vdoicap_goc = -1;
                if (this.ThongTin.txtDoiCapNgon.trim() != "")
                    vdoicap_ngon = this.ThongTin.txtDoiCapNgon.trim();
                if (this.ThongTin.txtDoiCapGoc.trim() != "")
                    vdoicap_goc = this.ThongTin.txtDoiCapGoc.trim();

                hoancong.CAPNHAT_THONGTIN_THICONG_IMS(this.hdtb_id, this.ThongTin.txtTuyenCap.trim(), this.ThongTin.txtLienTu.trim(),
                            vdoicap_ngon, this.ThongTin.txtHopCapNgon.trim(), this.ThongTin.txtTuCapNgon.trim(),
                            this.loaicap_id, this.ThongTin.txtCapGoc.trim(), this.ThongTin.txtCapNgon.trim(),
                            vdoicap_goc, this.ThongTin.txtTuCapGoc.trim(), this.ThongTin.txtSoMetDay);
            }

        },

        
        async kiemtra_dieukien_port(ma_tb, vport_id,vvci_vpi_id,kieu){
           let res = await api.kiemtra_dieukien_port(this.axios,{
                            "ma_tb": ma_tb,
                            "port_id":vport_id,
                            "vci_vpi_id":vvci_vpi_id,
                            "kieu": kieu,
                        })
            return res;
        },
        async CapNhat_TrangThai_Port(port_id,vport_id,status){
            let res = await api.CapNhat_TrangThai_Port(this.axios,{
                            "port_id": port_id,
                            "vci_vpi_id":vport_id,
                            "trangthai": status,
                        })
            return res;
        },

        async CapNhat_TrangThai_PortID(vport_id,status){
            let res = await api.CapNhat_TrangThai_PortID(this.axios,{
                            "port_id":vport_id,
                            "trangthai": status,
                        })
            return res;
        },

        async CapNhat_ds_matb(vport_id,vvci_vpi_id,ds_matb){
            let res = await api.CapNhat_ds_matb(this.axios,{
                            "ds_matb":ds_matb,
                            "port_id":vport_id,
                            "vci_vpi_id":vvci_vpi_id,
                        })
            return res;
        },

        async CAPNHAT_THONGTIN_THICONG_IMS(phanVungId){
            let res = await api.CAPNHAT_THONGTIN_THICONG_IMS(this.axios,{
                            "port_id": port_id,
                            "vci_vpi_id":vport_id,
                            "trangthai": status,
                        })
            return res;
        },
        KiemTra_DL(luong_id){
            if (this.chkNgayGV == false)
            {
                this.$toast.error("Hãy chọn ngày giao việc !");
                return false;
            }
            if (this.PHAILAM(luong_id, "GIAOVIEC"))
            {
                if (this.tableNV.options.length <= 0)
                {
                    this.$toast.error("Hãy nhập nhân viên thực hiện  !");
                    return false;
                }
            }

            if (this.chkNgayBG == false)
            {
                this.$toast.error("Hãy nhập ngày hoàn thành!");
                return false;
            }

            // System.TimeSpan diffResult = this.dtpNgayBG.Subtract(this.dtpNgayGV);
            // if (diffResult.Seconds < 0)
            // {
            //     this.$toast.error("Ngày giao việc không được lớn hơn ngày hoàn thành!");
            //     return false;
            // }

            //Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko
            var ngay_sys = "";
            var ngay_tt = "";
            var ngay_ht = "";

            //nhapt đổi new index method
            var nIndex = this.LayIndexTheoPhieuID(this.CURRENT_PHIEU_ID, this.table.options);
            ngay_sys = this.table.options[nIndex].thangnam_sys.trim();
            ngay_tt = this.table.options[nIndex].thangnam_tt.trim();
            ngay_ht = this.dtpNgayBG.split(" ")[0];

            if (this.loaihd_id != LoaiHopDong.KHOIPHUC_SD)
            {
                if (!this.KiemTraDK_HoanThanh(ngay_tt, ngay_ht))//Nếu ngày thanh toán > ngày hoàn thành
                {
                    this.$toast.error(" Thuê bao " + this.table.options[nIndex].ma_tb.trim() + " có ngày thanh toán : " + ngay_tt + " \n Ngày hoàn thành không được phép nhỏ hơn ngày thanh toán");
                    return false;
                }
            }
            if (!this.KiemTraDK_HoanThanh(ngay_ht, ngay_sys))//Nếu ngày thanh toán > ngày hoàn thành
            {
                //console.log(ngay_ht);
                this.$toast.error("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");
                return false;
            }
            var ngay_gv = "";
            ngay_gv = this.dtpNgayGV.split(" ")[0];
            if (!this.KiemTraDK_HoanThanh(ngay_gv, ngay_sys))//Nếu ngày giao viêc > ngày hiện tại
            {
                this.$toast.error("Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại");
                return false;
            }
            try{
                if (new Date(this.convertDate(this.dtpNgayBG)) < new Date(this.convertDate(this.dtpNgayGV)))
                {
                    this.$toast.error("Bạn không được chọn ngày thực hiện nhỏ hơn ngày giao việc !");
                    return false;
                }
            }
            catch(e){
                this.$toast.error("Định dạng ngày giờ chưa đúng !");
                return false;
            }
           
            //end

            if (this.PHAILAM(luong_id, "CAPNHAT_PORT"))
            {
                if (this.loaihd_id == LoaiHopDong.DAT_MOI
                    || this.loaihd_id == LoaiHopDong.CHUYENDOI_LH)
                {
                    if (this.port_id == 0 || this.vci_vpi_id == 0)
                    {
                        this.$toast.error("Hãy nhập thông tin về Port");
                         this.$refs["btnChonPort"].focus();
                        return false;
                    }
                    if (this.ThongTin.txtUsername == "" || this.ThongTin.txtPassword == "")
                    {
                        this.$toast.error("Hãy nhập thông tin user hoặc mật khẩu");
                        this.$refs["txtUsername"].focus();
                        return false;
                    }
                }
                else
                {
                    if (this.KIEMTRA_TRAMVT1(this.hdtb_id, 2) ||  this.table.options[0].ma_tb_cu != this.table.options[0].ma_tb) //Nếu khác trạm
                    {
                        if (this.port_id == this.port_id_cu && this.vci_vpi_id == this.vci_vpi_id_cu)
                        {
                            if (this.KIEMTRA_PORT(this.hdtb_id))
                            {
                                this.$toast.error("Hãy chọn port mới !");
                                return false;
                            }
                        }
                    }
                }
            }
            if (this.PHAILAM(luong_id, "CAPNHAT_CAP"))
            {
                if (this.PHAILAM(luong_id, "PHAINHAP_CAP"))
                {
                    if (this.ThongTin.txtDoiCapGoc == "")
                    {
                        this.$toast.error("Hãy nhập thông tin đôi cáp gốc !");
                        this.$refs["txtDoiCapGoc"].focus();
                        return false;
                    }

                    if (this.ThongTin.txtCapGoc== "")
                    {
                        this.$toast.error("Hãy nhập thông tin cáp gốc !");
                        this.$refs["txtCapGoc"].focus();
                        return false;
                    }
                    if (this.ThongTin.txtDoiCapNgon == "")
                    {
                        this.$toast.error("Hãy nhập thông tin đôi cáp ngọn !");
                        this.$refs["txtDoiCapNgon"].focus();
                        return false;
                    }

                    if (this.ThongTin.txtCapNgon == "")
                    {
                        this.$toast.error("Hãy nhập thông tin cáp ngọn !");
                        this.$refs["txtCapNgon"].focus();
                        return false;
                    }
                }
                if (this.ThongTin.txtSoMetDay.trim() == "" || this.ThongTin.txtSoMetDay.trim() == "0")
                {
                    this.$toast.error("Hãy nhập thông tin số mét dây !");
                    this.$refs["txtSoMetDay"].focus();
                    return false;
                }

                if (!this.ThongTin.txtSoMetDay.trim())
                {
                    if (this.ThongTin.txtSoMetDay.trim() == "0")
                    {
                        this.$toast.error("Hãy nhập số mét dây !");
                        this.$refs["txtSoMetDay"].focus();
                        return false;
                    }
                    
                    if (parsevar(this.Unformat_tien(this.ThongTin.txtSoMetDay.trim())) == "NaN")
                    {
                        this.$toast.error("Số mét dây phải là kiểu số");
                        this.ThongTin.txtSoMetDay = "0";
                        this.$refs["txtSoMetDay"].focus();
                        return false;
                    }
                }
                else
                {
                    this.$toast.error("Hãy nhập số mét dây !");
                    this.$refs["txtSoMetDay"].focus();
                    return false;
                }
            }
            return true;
        },
        //Định dạng dd/MM/yyyy HH:mm:ss
        convertDate(item){
            try{
                var dateString = item.split(" ");
                var date = dateString[0].split("/");
                var time = dateString[1].split(":");
                return (date[2] + "-" + date[1]+"-"+date[0]+ " " + time[0] + ":" + time[1] + ":" + time[2]);
            }
            catch(e){
                this.$toast.error("Định dạng ngày giờ chưa đúng!");
                return null;
            }
            
        },
        KIEMTRA_TRAMVT1(vhdtb_id,vkieu){
            var dataRes = false;
            api.kiemtra_tramVT1(this.axios,{
                "hdtb_id": vhdtb_id,
                "kieu": vkieu,
            }).then((response) =>{
            if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                if (response.data.data !== null) {
                    if(response.data.data == 0){
                        dataRes = false;
                    }
                    else if(response.data.data == 1){
                         dataRes = true;
                    }
                }

            }
            })
            .catch((error) => {
                console.log(error);
            })
            return dataRes;
        },
        KIEMTRA_PORT(vhdtb_id){
            var dataRes = false;
            api.kiemtra_port(this.axios,{
                "hdtb_id": vhdtb_id,
            }).then((response) =>{
            if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                if (response.data.data !== null) {
                    if(response.data.data == 0){
                        dataRes = false;
                    }
                    else if(response.data.data == 1){
                         dataRes = true;
                    }
                }
            }
            })
            .catch((error) => {
                console.log(error);
            })
            return dataRes;
        },
        Unformat_tien(textData){
            textData = textData.toLowerCase().trim();
            ketqua = "";
            for (var i = 0; i < s.length; i++)
            {
                if (textData[i] != "," && textData[i] != "." && textData[i] != " ")
                {
                    ketqua = ketqua + textData[i];
                }
            }
            return ketqua.trim();
        },
        KiemTraDK_HoanThanh(thangnam_tt,thangnam_sys) //thangnam_sys= ngay_ht
        {
            var result = false;
            thangnam_tt = thangnam_tt.split("/");
            thangnam_sys = thangnam_sys.split("/");
            if (thangnam_tt[2] < thangnam_sys[2])//So sánh năm
                return true;//Nếu năm thanh toán < năm hoàn thành thì true;
            else if (thangnam_tt[2] == thangnam_sys[2])//So sánh năm
            {
                //Nêu năm = nhau thì kiểm tra tiếp
                //Nếu tháng tt < thang ht -> true
                if (thangnam_tt[1] < thangnam_sys[1])
                    result = true;
                else if (thangnam_tt[1] == thangnam_sys[1])
                    //Nếu tháng & năm = nhau thì kiểm tra ngày
                    if (thangnam_tt[0] <= thangnam_sys[0])
                        return true;
                    else
                        return false;
                else
                    result = false;//tháng tt lớn hơn thang ht
            }
            else
                result = false;

            return result;
        },
        HT_KieuHD_Combobox(){
            api.lay_kieuHD(this.axios,{}).then((response) =>{
            if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                if (response.data.data !== null) {
                this.options.cboKieu_HD = response.data.data;
                this.kieuhd_id = this.options.cboKieu_HD[0].kieuhd_id;
                }
                else{
                    this.options.cboKieu_HD = [];
                }
            }
            })
            .catch((error) => {
                console.log(error);
            })
            .finally(() => {});
            },
        lay_KenhThu(){
            api.lay_KenhThu(this.axios,{"test":null}).then((response) =>{
            if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                this.options.cboKenhThu = response.data.data;
                this.kenhthu_id = this.options.cboKenhThu[0].kenhthu_id;
            }
            else{
                this.kenhthu_id = -1;
                this.options.cboKenhThu = [];
            }
            })
            .catch((error) => {
                console.log(error);
            })
            .finally(() => {});
            },
        HT_Loai_HD_Combobox(){
             api.lay_loaiHD(this.axios,{"loaihd_id":this.dsloaihd_id}).then((response) =>{
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                    if (response.data.data !== null) {
                    this.options.cboLoaiHD = response.data.data;
                    this.loaihd_id = this.options.cboLoaiHD[0].loaihd_id;
                    this.lay_ds_quy_trinh(this.dsdichvuvt_id,this.loaihd_id,this.tthd_id,0);
                    }
                    else{
                        this.options.cboLoaiHD = [];
                        this.loaihd_id = -1;
                    }
                }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {});
            },
        HT_Loai_Cap_Combobox(){           
            api.lay_loaiCap(this.axios,{}).then((response) =>{
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                    if (response.data.data !== null) {
                    this.options.cboLoaiCap = response.data.data;
                    this.loaicap_id = this.options.cboLoaiCap[0].loaicap_id;
                    }
                    else{
                        this.options.cboLoaiCap = [];
                        this.loaicap_id = -1;
                    }
                }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {});
        },
        HT_LoaiHinh_TB_DatMoi_Combobox_V2(){
            api.lay_loaiTB(this.axios,{"dichvuvt_id":this.dsdichvuvt_id}).then((response) =>{
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {                 
                    this.options.cboLoaiTB = response.data.data;
                    this.loaitb_id = this.options.cboLoaiTB[0].loaitb_id;
                }
                else{
                    this.options.cboLoaiTB = [];
                        this.loaitb_id = -1;
                }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {});
            
        },
        HT_ThietBi_IMS_Combobox(){
            api.lay_dsTbiIMS(this.axios,{}).then((response) =>{
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                    this.options.cboDsTbiIMS = response.data.data;
                    this.thietbi_id = this.options.cboDsTbiIMS[0].thietbi_id;                 
                }
                 else{
                        this.options.cboDsTbiIMS = [];
                        this.thietbi_id = -1;
                    }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {});
            
        },
        HT_BRAS_ADSL_Combobox(){
            api.lay_Bras(this.axios,{}).then((response) =>{
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                    this.options.cboBras = response.data.data;
                    this.bras_id = this.options.cboBras[0].bras_id;
                    this.HT_DSLAM_ADSL_Combobox(this.bras_id,1);
                }
                else{
                    this.options.cboBras = [];
                        this.bras_id = -1;
                }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {});            
        },
        HT_ChiTietDV_IMS_Combobox(){
            api.lay_chiTietDVIMS(this.axios,{"kieu":0}).then((response) =>{
                    if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                    this.options.cboChiTietDVIMS = response.data.data;
                    this.options.cboChiTietDVIMS.forEach((obj) =>{
                        if(obj["ctdv_id"] == 0 && obj["chitiet_dv"] == null){
                            obj["chitiet_dv"] = "test";
                        }
                    })
                    this.ctdv_id = this.options.cboChiTietDVIMS[0].ctdv_id;  
                }
                else{
                     this.options.cboChiTietDVIMS = [];
                        this.ctdv_id = -1;
                }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {});
            
        },
        HT_DSLAM_ADSL_Combobox(bras_id, vKieu){
            api.lay_dsDsLam(this.axios,{"bras_id":bras_id,"kieu":1}).then((response) =>{
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {                   
                    this.options.cboDsLam = response.data.data;
                    this.dslam_id = this.options.cboDsLam[0].dslam_id;
                }
                else{
                    this.options.cboDsLam = [];
                        this.dslam_id = -1;
                }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {});
            
        },
      
        lay_ds_nhanvien_thuchien_theo_dv(){
            api.lay_dsNVTH(this.axios,{"donvi_id":this.donviId}).then((response) =>{
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                    this.options.cboNguoiGV = response.data.data;
                    this.nguoiGv_id = this.nhanvien_id;
                }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {});
            
        },
        lay_ds_quy_trinh(dsdichvuvt_id, dsloaihd_id, dstthd_id, kieu){
             api.lay_dsQuyTrinh(this.axios,
                {
                 "dsdichvuvt_id":dsdichvuvt_id,
                 "dsloaihd_id":dsloaihd_id,
                 "dstthd_id":dstthd_id,
                 "kieu":0
                }).then((response) =>{
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                        this.options.cboDsQuyTrinh = response.data.data;                        
                        this.quytrinh_id = this.options.cboDsQuyTrinh[0].quytrinh_id;
                }
                else{
                    this.options.cboDsQuyTrinh = [{"quytrinh_id":-1, "quytrinh":"--Không có quy trình--"}];                        
                        this.quytrinh_id = -1;
                } 
                this.lay_dichvuId_theo_quytrinh(this.quytrinh_id); 
                this.lay_luongAndHuongGiao(this.quytrinh_id,this.tthd_id);
                          
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {});
        },
        lay_dichvuId_theo_quytrinh(quytrinh_id){
             api.lay_dichvuId_theo_quytrinh(this.axios,
             {
                 "list_of_cols":"dichvuvt_id",
                 "order":"",
                 "schema":"css",
                 "table_name":"quytrinh",
                 "where":"quytrinh_id = "+ quytrinh_id,
             }).then((response) =>{
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {                   
                    this.dichvuvt_id= response.data.data[0].dichvuvt_id;
                }
                else{
                    this.dichvuvt_id = DichVuVienThong.IMS;
                }
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {});
        },
        lay_luongAndHuongGiao(quytrinh_id, tthd_id){
            var linkAPI = '/web-hopdong/danhmuc/ds_huonggiao/' + quytrinh_id +'/'+tthd_id;
            api.lay_luongAndHuongGiao(this.axios,linkAPI,{})
             .then((response) =>{
                 
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                        this.luong_id = response.data.data[0].luong_id;
                        this.huonggiao_id = response.data.data[0].huonggiao_id;
                        this.HienThiGiaoDien(this.luong_id); 
                        this.HienThiDanhSachHDTB();
                }
                else{
                    this.luong_id = -1;
                    this.huonggiao_id = -1;
                }
                })
                .catch((error) => {
                    this.luong_id = -1;
                    this.huonggiao_id = -1;
                    console.log(error);
                })
                .finally(() => {});
        },

        HienThiGiaoDien(luong_id){
            try{
                this.$refs["tsbtnDuAn"].style.display = "none";
                this.$refs["txtPort"].setAttribute('readonly', 'readonly');
                this.$refs["txtPortMdf"].setAttribute('readonly', 'readonly');
                this.$refs["txtSlot"].setAttribute('readonly', 'readonly');
                this.$refs["txtSystem"].setAttribute('readonly', 'readonly');
                this.$refs["txtRack"].setAttribute('readonly', 'readonly');
                this.$refs["txtShelf"].setAttribute('readonly', 'readonly');
                this.$refs["txtVCI"].setAttribute('readonly', 'readonly');
                this.$refs["txtVPI"].setAttribute('readonly', 'readonly');
                this.$refs["txtAdsl_Port"].setAttribute('readonly', 'readonly');
                this.$refs["txtUsername"].setAttribute('readonly', 'readonly');
                this.$refs["txtPassword"].setAttribute('readonly', 'readonly');
                this.$refs["txtSvLanIMS"].setAttribute('readonly', 'readonly');
                this.$refs["txtCvLanIMS"].setAttribute('readonly', 'readonly');

                this.$refs["btnChonPort"].setAttribute('disabled', 'disabled');
                this.$refs["btnChonPort_TuDong"].setAttribute('disabled', 'disabled');


                this.$refs["tsbtnKQ_DoKiem"].style.display = null;
                this.$refs["tsbtnGiaoViec"].style.display = null;
                this.$refs["tsbtnSua_KenhThu"].style.display = null;
                this.$refs["tsbtnHoanThienHS"].style.display = null;
                
                // tsbtnKichHoatAo.Visible = false;
                
                api.lay_luongThaoTac(this.axios,
                    {
                        "luong_id":luong_id,
                    }).then((response) =>{
                    if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                            this.dtThaoTac = response.data.data;   
                            this.dtThaoTac.forEach((obj)=>{
                                api.lay_thaoTacControl(this.axios,{
                                    "thaotac_id":obj.thaotac_id
                                }).then((res) =>{
                                    
                                    if(res.data.error_code === "BSS-00000000" && res.data.data !== null && res.data.data !== "" && res.data.data.length > 0){
                                        if(res.data.data != []){
                                            
                                        var el = this.$refs[res.data.data[0].control_name];
                                       
                                           switch(el.tagName){
                                               case "A":
                                                    if(obj.enable == 1){
                                                        this.$refs[res.data.data[0].control_name].style.display = "none";
                                                    }
                                                    else{
                                                       this.$refs[res.data.data[0].control_name].style.display = null;
                                                    }
                                                    break;
                                                case "BUTTON":
                                                    if(obj.enable == 1){
                                                        this.$refs[res.data.data[0].control_name].setAttribute('disabled', 'disabled');
                                                    }
                                                    else{
                                                        if(this.$refs[res.data.data[0].control_name].getAttribute('disabled') == 'disabled'){
                                                            this.$refs[res.data.data[0].control_name].removeAttribute('disabled');
                                                        }
                                                    }
                                                    break;
                                                case "INPUT":
                                                    if(obj.enable == 1){
                                                        this.$refs[res.data.data[0].control_name].setAttribute('readonly', 'readonly');
                                                    }
                                                    else{
                                                         if(this.$refs[res.data.data[0].control_name].getAttribute('readonly') == 'readonly'){
                                                            this.$refs[res.data.data[0].control_name].removeAttribute('readonly');
                                                        }
                                                    }
                                                    break;
                                                default:
                                                    console.log(this.$refs["cboKieu_HD"].$options.propsData.disabled);
                                                    var name = res.data.data[0].control_name;
                                                    if(obj.enable == 1){
                                                        this.$refs[name].$options.propsData.disabled = true;
                                                        console.log(this.$refs[name]);
                                                    }
                                                    else{
                                                        //this.$refs.name.disabled = false;
                                                    }
                                           }

                                        }
                                    }
                                })
                            })                  
                    }                                    
                    })
                }  
            catch(e){
                console.log(e);
            }          
        },
        async HienThiDanhSachHDTB(){
            console.log("HienThiDanhSachHDTB");
            this.loading(true);         
            //api.lay_dsPhieuGiaoDenIms(this.axios,{
                // "dichvuvt_id":this.dichvuvt_id,
                // "tthd_id":this.tthd_id,
                // "nhanvien_id": this.nhanvien_id,
                // "ma_gd":"",
                // "phieu_id":0,
                // "loaihd_id":this.loaihd_id,
                // "huonggiao_id":this.huonggiao_id,
                // "loai_id":this.rdoCheckPhieu,
            let response = await api.lay_dsPhieuGiaoDenIms(this.axios,{
               "dichvuvt_id":this.dichvuvt_id,
                "tthd_id":this.tthd_id,
                "nhanvien_id": this.nhanvien_id,
                "ma_gd":"",
                "phieu_id":0,
                "loaihd_id":this.loaihd_id,
                "huonggiao_id":this.huonggiao_id,
                "loai_id":this.rdoCheckPhieu,
            })

            this.Clear(); 
            console.log("hiển thị dữ liệu");      
                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                    this.table.options = response.data.data;
                    this.lblTongS = "Tổng số 0/" + this.table.options.length;

                    this.loading(false);    
                }
                else{
                    this.Clear();
                    this.table.options = [];
                    this.loading(false);
                }              

        },
        HienThiTTHopDongTB(data){
            console.log("HienThiTTHopDongTB");
            console.log(data);
            if(data!=null && data != ""){
                this.$refs["tsbtnTraPhieu"].classList.remove('disabled');
                this.$refs["tsbtnChapNhan"].classList.remove('disabled');
                this.$refs["tsBtnKhoiPhucLai"].style.display = "none";
                this.hdtb_id = data.hdtb_id;
                this.hdkh_id = data.hdkh_id;
                this.phieu_id = data.phieu_id;
                this.dichvuvt_id = data.dichvuvt_id;
                console.log(this.hdkh_id);
                api.lay_ttLienHeLD(this.axios,{
                    "hdkh_id":this.hdkh_id,
                }).then((response) =>{               
                    if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                            this.ThongTin.lblLienHe = response.data.data;
                    }
                    })
                    .catch((error) => {
                    
                        console.log(error);
                    });
                api.lay_tongTienTheoKHID(this.axios,{
                    "hdkh_id":this.hdkh_id,
                }).then((response) =>{               
                    if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                            this.ThongTin.txtTongTien = response.data.data.tongtien;
                    }
                    })
                    .catch((error) => {
                    
                        console.log(error);
                    });
                api.lay_ttNhanVien(this.axios,'/web-hopdong/danhmuc/lay_thongtin_nhanvien/'+data.nhanvien_id,{                   
                }).then((response) =>{           
                    if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                            this.ThongTin.txtNhanVien = response.data.data[0].NAME;
                    }
                    })
                    .catch((error) => {
                    
                        console.log(error);
                    })
                if(data.ctv_id != null && data.ctv_id != ""){
                    api.lay_ttNhanVienDV(this.axios,{
                    "nhanvien_id":data[0].ctv_id
                    }).then((response) =>{               
                    if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                            this.ThongTin.txtNV_TiepThi = response.data.data[0].nhanvien;
                    }
                    })
                    .catch((error) => {
                    
                        console.log(error);
                    })
                }
                if(data.kieuhd_id != null && data.kieuhd_id != ""){
                    this.kieuhd_id = data.kieuhd_id;
                }
                else{
                    this.kieuhd_id = this.KIEU_HD.TAI_GD;
                }
                this.ThongTin.vsdt_lh = data.so_dt_lh;
                api.lay_kenhThuTheoHDKH(this.axios,{
                    "hdkh_id":this.hdkh_id,
                }).then((response) =>{               
                    if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                            this.kenhthu_id = response.data.data[0].kenhthu_id;
                    }
                    })
                    .catch((error) => {
                    
                        console.log(error);
                    });
                api.kiemtra_hen_hdtb(this.axios,{
                    "hdtb_id":this.hdtb_id,
                }).then((response) =>{               
                    if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                            this.chkHen = true;
                    }
                    else{
                        this.chkHen = false;
                    }
                    })
                    .catch((error) => {
                        this.chkHen = false;
                        console.log(error);
                    });

                    this.ThongTin.txtThongTvarra = data.nd_tra_con;
                    if(data.lydotra_con_id != null && data.lydotra_con_id != ""){
                        api.lay_tenMapId(this.axios,{
                            "id_neo":"lydotra",
                            "in_table": "CSS.lydotra_tc",
                            "in_dk":"where lydotra_id = " + data.lydotra_con_id,
                            }).then((response) =>{               
                                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                                        this.ThongTin.txtLyDoTra = response.data.data;
                                }                
                        })
                        .catch((error) => {
                            this.ThongTin.txtLyDoTra = "";
                            console.log(error);
                        })
                    }
                    else{
                        this.ThongTin.txtLyDoTra ="";
                    }
                
                    this.loaihd_id = data.loaihd_id;
                    if (this.loaihd_id == LoaiHopDong.DI_CHUYEN || this.loaihd_id == LoaiHopDong.KHOIPHUC_SD || this.loaihd_id == LoaiHopDong.CHUYENDOI_LH){
                        this.thuebao_id = data.thuebao_id;
                    }
                    this.ThongTin.txtMaGD = data.ma_gd;
                    this.ThongTin.txtMaTB = data.ma_tb;
                    this.loaitb_id = data.loaitb_id;
                    this.ThongTin.txtTenTB = data.ten_tb;
                    this.ThongTin.txtKhuvuc = data.ten_kv;
                    this.ThongTin.txtDiaChiLD = data.diachi_ld;
                    this.ThongTin.txtDiaChiLD_Cu = data.diachild_cu;
                    this.ThongTin.txtDiaChiTB = data.diachi_tb;
                    this.ThongTin.txtGhiChu = data.ghichu;
                    this.ThongTin.txtKieuLD = data.ten_kieuld;
                    this.kieuld_id = data.kieuld_id;
                    this.ThongTin.txtNoiDungTH = data.nd_thuchien;
                    this.ThongTin.txtSoMayTN = data.matb_tn;this.ThongTin.txtTuyenCap = data.tuyencap;
                    this.ThongTin.txtLienTu = data.lientu; this.ThongTin.txtTuCapNgon = data.tucap_p;
                    this.ThongTin.txtDoiCapNgon = (data.doicap_p != -1)?data.doicap_p:"";
                    this.ThongTin.txtHopCapNgon = data.hopcap_p;
                    this.ThongTin.txtCapGoc = data.cap_goc;this.ThongTin.txtCapNgon = data.cap_p;
                    this.ThongTin.txtDoiCapGoc = (data.doicap_goc != -1)?data.doicap_goc:"";
                    this.ThongTin.txtSoMetDay = (data.culy != "")?data.culy:0;
                    this.ThongTin.txtCuocGoi_DT = (data.sl_cuocgoi != "")?data.sl_cuocgoi:"";
                    if(data.loaicap_id != null && data.loaicap_id != ""){
                        this.loaicap_id = data.loaicap_id;
                    }
                    if(data.ctdv_id != ""){
                        this.ctdv_id = data.ctdv_id;
                    }
                    this.ThongTin.txtIP_KH = data.ip;
                    this.ThongTin.txtNDGiao = data.nd_giao;
                    if(data.BRAS_ID != ""){
                        this.bras_id = data.BRAS_ID;
                    }
                    if(data.DSLAM_ID != ""){
                        this.dslam_id = data.DSLAM_ID;
                    }
                    if(data.port_id != ""){
                        this.port_id = data.port_id;
                        this.port_id_cu = this.port_id;
                    }
                    else{
                        this.port_id_cu = 0;
                        this.port_id = 0;
                    }
                    if(data.vci_vpi_id != ""){
                        this.vci_vpi_id = data.vci_vpi_id;
                        this.vci_vpi_id_cu = this.vci_vpi_id;
                    }
                    else{
                        this.vci_vpi_id_cu = 0;
                        this.vci_vpi_id = 0;
                    }

                    this.ThongTin.txtOnuPass = data.onu_password;
                    this.ThongTin.txtPort = data.port;
                    this.ThongTin.txtPortMdf = data.port_mdf;
                    this.ThongTin.txtSlot = data.slot;
                    this.ThongTin.txtVPI = data.vpi;this.ThongTin.txtVCI = data.vci;
                    this.ThongTin.txtAdsl_Port = data.adsl_port;
                    this.ThongTin.txtSvLanIMS = data.svlan;
                    this.ThongTin.txtCvLanIMS = data.cvlan;
                    if(data.system != ""){
                        this.ThongTin.txtSystem = data.system;
                        this.system = data.system;
                    }
                    else{
                        this.ThongTin.txtSystem = "";
                        this.system = "";
                    }
                    this.ThongTin.txtRack = data.rack;
                    this.ThongTin.txtShelf = data.shelf;
                    this.ThongTin.txtUsername = data.username;
                    this.ThongTin.txtPassword = data.password;
                    api.lay_ttIdDonVi(this.axios,{
                    "hdtb_id":this.hdtb_id,
                    "loaidv_id": LOAI_DV.TRAM_VT,
                    }).then((response) =>{               
                        if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                                this.donvi_id = response.data.data.donvi_id;
                        }                
                        })
                        .catch((error) => {
                            console.log(error);
                        })
                    if(data.tramtb_id != ""){
                        this.tramtb_id = data.tramtb_id;
                        api.lay_tenMapId(this.axios,{
                            "id_neo":"ten_dv",
                            "in_table": "admin.donvi",
                            "in_dk":"where donvi_id = " +this.tramtb_id,
                            }).then((response) =>{               
                                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                                        this.ThongTin.txtTramTB = response.data.data;
                                }                
                        })
                        .catch((error) => {
                            this.ThongTin.txtTramTB = "";
                            console.log(error);
                        })
                    }
                    else{
                        this.tramtb_id = 0; 
                        this.ThongTin.txtTramTB = "";
                    }
                    if(data.thietbi_id != null && data.thietbi_id != ""){
                        this.thietbi_id = data.thietbi_id;
                    }
                    this.kt_status = data.status;
                    if(data.status != null && data.status != ""){
                        if(data.status == this.TRANGTHAI_DONGBO.DONGBO_CM){
                            this.$refs["tsbtnChapNhan"].classList.remove('disabled');                    
                        }
                        else{
                            this.$refs["tsbtnHuyKH"].classList.add('disabled');
                        }
                    }
                    this.$refs["tsbtnHuyKH"].style.display = "none";
                    if(this.PHAILAM(this.luong_id,"TSBTNHUYKH")){
                        this.$refs["tsbtnHuyKH"].style.display = null;
                    }
                    // this.$refs["tsbtnThongTinLH"].style.display = "none";
                    // if (PHAILAM("TSBTNTHONGTINLH"))
                    // {
                    //    this.$refs["TSBTNTHONGTINLH"].style.display = null;
                    // }
                    this.chkNgayBG = true;
                    this.isDisDtpNgayBG = false;
                    if(data.ngay_ht_gp != null && data.ngay_ht_gp != ""){
                        this.dtpNgayBG = data.ngay_ht_gp;
                    }
                    else{

                    }
                    if(this.PHAILAM("KICHHOAT_TD")){
                        if(data.ngay_ht_gp != "" && data.ngay_ht_gp != null){
                            if(this.kt_status == this.TRANGTHAI_DONGBO.DONGBO_CM){    
                                this.$refs["tsbtnGiaoPhieu"].classList.remove('disabled');
                                this.$refs["tsbtnKichHoat"].classList.add('disabled');
                                this.$refs["btnChonPort"].setAttribute('disabled', 'disabled');
                                this.$refs["btnChonPort_TuDong"].setAttribute('disabled', 'disabled');
                            }
                            else{
                                this.$refs["tsbtnGiaoPhieu"].classList.add('disabled');                     
                                this.$refs["tsbtnKichHoat"].classList.remove('disabled');
                                if(this.$refs["btnChonPort"].getAttribute('disabled' == 'disabled')){
                                    this.$refs["btnChonPort"].removeAttribute('disabled');
                                }
                                if(this.$refs["btnChonPort_TuDong"].getAttribute('disabled' == 'disabled')){
                                    this.$refs["btnChonPort_TuDong"].removeAttribute('disabled');
                                }
                            }
                        }
                        else{
                            this.$refs["tsbtnGiaoPhieu"].classList.add('disabled'); 
                            this.$refs["tsbtnKichHoat"].classList.add('disabled'); 
                            if(this.$refs["btnChonPort"].getAttribute('disabled' == 'disabled')){
                                    this.$refs["btnChonPort"].removeAttribute('disabled');
                                }
                            if(this.$refs["btnChonPort_TuDong"].getAttribute('disabled' == 'disabled')){
                                    this.$refs["btnChonPort_TuDong"].removeAttribute('disabled');
                                }
                        }
                    }
                    if(this.PHAILAM(this.luong_id,"CAPNHAT_PORT")){
                        if(this.$refs["btnChonPort"].getAttribute('disabled' == 'disabled')){
                                this.$refs["btnChonPort"].removeAttribute('disabled');
                        }
                    }
                    if(this.PHAILAM(this.luong_id,"CAPNHAT_PORT_TUDONG")){
                        if(this.$refs["btnChonPort_TuDong"].getAttribute('disabled' == 'disabled')){
                                this.$refs["btnChonPort_TuDong"].removeAttribute('disabled');
                        }
                    }

                    if(this.PHAILAM(this.luong_id,"GIAOPHIEU")){
                        if(data.ngay_ht_gp != "" && data.ngay_ht_gp != null){
                            if(this.PHAILAM(this.luong_id,"KICHHOAT_TD")){
                                if(this.kt_status == this.TRANGTHAI_DONGBO.DONGBO_CM){
                                    this.$refs["tsbtnGiaoPhieu"].classList.remove('disabled'); 
                                    this.$refs["tsbtnKichHoat"].classList.add('disabled'); 
                            }
                            else{
                                this.$refs["tsbtnGiaoPhieu"].classList.add('disabled');
                                this.$refs["tsbtnKichHoat"].classList.remove('disabled');
                            }
                            }
                            else{
                                this.$refs["tsbtnGiaoPhieu"].classList.remove('disabled');
                            }
                            
                        }
                        else{
                            this.$refs["tsbtnGiaoPhieu"].classList.add('disabled');
                            this.$refs["tsbtnKichHoat"].classList.add('disabled');
                        }
                    }
                    
                    if(this.PHAILAM(this.luong_id,"HOANCONG")){
                        if(this.dichvuvt_id == DichVuVienThong.IMS_DOITAC){                  
                                if(data.ngay_ht_gp != "" && data.ngay_ht_gp != null){
                                    this.$refs["tsbtnHoanCong"].classList.remove('disabled');
                                }
                                else{
                                    this.$refs["tsbtnHoanCong"].classList.add('disabled');
                                }
                            
                        }
                        else{
                            if(data.ngay_ht_gp != null && data.ngay_ht_gp != null && this.kt_status == this.TRANGTHAI_DONGBO.DONGBO_CM){
                                this.$refs["tsbtnHoanCong"].classList.remove('disabled');
                            }
                            else{
                                this.$refs["tsbtnHoanCong"].classList.add('disabled');
                            }
                        }
                    }
                    if(!this.PHAILAM(this.luong_id,"GIAOVIEC")){
                        this.chkNgayBG = true;
                        this.isDisDtpNgayBG = true;  
                        this.isDischkNgayBG = true;            

                        this.chkNgayGV = true;
                        this.isDisDtpNgayGV = true;
                        this.isDischkNgayGV = true;
                    }
                    else{
                        this.isDisDtpNgayBG = false;
                        this.isDischkNgayBG = false;

                        this.isDisDtpNgayGV = false;
                        this.isDischkNgayGV = false;
                    }
                    
                    let records = this.$refs.danhsach.getSelectedRecords();
                    if(records != null && records != "" && records[0].hdtb_id != ""){
                        var vhdtb_id = records[0].hdtb_id;                     
                        api.lay_tbLapKem(this.axios,{
                            "hdtb_id":vhdtb_id,
                            "madoicap": data.madoicap,
                            }).then((response) =>{    
                                console.log(response.data.data);           
                                if ( response.data.error_code === "BSS-00000000" && response.data.data !== null && response.data.data.length > 0) {
                                        this.ThongTin.txtTBLapKem = response.data.data;
                                }   
                                else{
                                    this.ThongTin.txtTBLapKem = "";
                                }             
                        })
                        .catch((error) => {
                            this.ThongTin.txtTBLapKem = "";
                            console.log(error);
                        })
                    }

                    this.ThongTin.txtSLID= data.SLID;
                    
            }
                   
        },
        HienThiDSNV(data){
            this.tableNV.options = data;
            if(data != ""){
                this.chkNgayGV = true;               
                this.dtpNgayGV = data[0].ngaygiao;
                this.nguoiGv_id = data[0].nhanvien_giao_id;
                this.nhanvien_tc_id = data[0].nhanvien_th_id;
                this.congviec_th = data[0].nhiemvu;
            }
            else{
                if (!this.PHAILAM(this.luong_id,"GIAOVIEC"))
                    this.chkNgayGV = true;
                else
                    this.chkNgayGV = false;
                this.chkNgayGV = false;               
                this.dtpNgayGV = moment().format("DD/MM/YYYY HH:mm:ss");
                this.nguoiGv_id = -1;
                this.nhanvien_tc_id = -1;
                this.congviec_th = "";
            }
            
        },
        HienThiDVGT(data){
            if(data.length > 0){
                this.dgvdangkyDV.options = data;
                this.dgvdangkyDV.options.forEach((obj)=>{
                if(obj.id_vtn == ""){
                    this.lbldvgt = "Lưu ý : có dịch vụ phải kích hoạt nhân công";
                    return;
                }
                
            });
            }
            else{
                this.dgvdangkyDV.options = [];
                this.lbldvgt = "";
            }
        },
        focusItem(index, data) {
            
        },
        Clear(){        
            this.$refs["tsbtnTraPhieu"].classList.add('disabled');
            this.$refs["tsbtnChapNhan"].classList.add('disabled');
            this.$refs["tsbtnHoanCong"].classList.add('disabled');
            this.$refs["tsbtnKichHoat"].classList.add('disabled');
            this.$refs["tsBtnKhoiPhucLai"].style.display = "none";
            for(var item in this.ThongTin){
                if(item == "txtTongTien" || item == "txtSoMetDay"){
                    this.ThongTin[item] = "0"
                }else{
                    this.ThongTin[item] = ""
                }
            }
            this.dtpNgayGV = moment().format("DD/MM/YYYY HH:mm:ss");
            this.dtpNgayBG = moment().format("DD/MM/YYYY HH:mm:ss");
            this.ctdv_id = this.options.cboChiTietDVIMS[0].ctdv_id;
            this.chkNgayBG = false;
            this.chkNgayGV = false;
            this.$set(this.ThongTin);
            this.khachhang_id = 0;
            this.lbldvgt = "";
            this.thanhtoan_id = 0;
            this.diachikh_id = 0;
            this.apkh_id = 0;
            this.phokh_id = 0;
            this.phuongkh_id = 0;
            this.quankh_id = 0;
            this.tinhkh_id = 0;
            this.sonhakh = "";
            this.diachitt_id = 0;
            this.phott_id = 0;
            this.aptt_id = 0;
            this.phuongtt_id = 0;
            this.quantt_id = 0;
            this.tinhtt_id = 0;
            this.sonhatt = "";
            this.hdtt_id = -1;
            this.thuebao_id = -1;
            this.lblTongS = "Tổng số: 0/0";
            this.$refs["tsBtnKhoiPhucLai"].style.display = "none";
        },

        PHAILAM(luong_id,CODE){
            var records = [];
            this.dtThaoTac.forEach((obj)=>{
                if(obj.enable == 1 && obj.code == CODE){
                    records.push(obj);
                }
                
            });
            return (records.length > 0)?true:false;             
        },
        cboQuyTrinh_SelectedValueChanged(args){
            this.lay_dichvuId_theo_quytrinh(args);
            this.lay_luongAndHuongGiao(args,this.tthd_id);
        },
        cboLoaiHD_SelectedValueChanged(args){
            this.lay_ds_quy_trinh(this.dsdichvuvt_id,args,this.tthd_id,0);
        },
        cboBras_SelectedValueChanged(args){
            this.HT_DSLAM_ADSL_Combobox(args,1);
        },
        onClickRdo(e){
            this.rdoCheck = event.target.value;
            this.HienThiDanhSachHDTB();
        },
        onClickChkNgayGV(){
            this.isDisDtpNgayGV = this.chkNgayGV;
        },
        onClickChkNgayBG(){
            this.isDisDtpNgayBG = this.chkNgayBG;
        },
        dtgDanhSach_SelectionChanged(row){
            console.log("change");
            console.log(row);
            this.$refs["tsbtnTraPhieu"].classList.add('disabled');
            this.$refs["tsbtnChapNhan"].classList.add('disabled');
            this.$refs["tsBtnKhoiPhucLai"].style.display = "none";
            if(this.$refs.danhsach.getSelectedRecords() == ""){
                console.log("Không có bản ghi nào được chọn");
                return;
            }
            if(this.$refs.danhsach.dataSource.length > 0){
                this.phieu_id = this.CURRENT_PHIEU_ID;
                 api.lay_dsNVTheoPhieuId(this.axios,{
                    "phieu_id":this.phieu_id,
                    "kieu_id":2,
                    "nhanvien_id":this.donviId,
                }).then((response) =>{           
                    if ( response.data.error_code === "BSS-00000000" && response.data.data !== "" && response.data.data != null && response.data.data.length > 0) {                          
                           this.HienThiDSNV(response.data.data);
                    }
                    })
                    .catch((error) => {
                    
                        console.log(error);
                    });

            }
              api.lay_dsNVTheoPhieuId(this.axios,'/web-ccdv/dongbo-visa-vasc/lay_ds_dangky_dvgt/' + this.hdtb_id,{}).then((response) =>{           
                    if ( response.data.error_code === "BSS-00000000" && response.data.data !== "" && response.data.data != null && response.data.data.length > 0) {
                           console.log(response.data.data);
                           this.HienThiDVGT(response.data.data);                           
                    }
                    })
                    .catch((error) => {
                    
                        console.log(error);
                    });
            
            this.HienThiTTHopDongTB(row);
            this.chkLayTSo = false;


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
        this.HT_KieuHD_Combobox();
        this.lay_KenhThu();
        this.HT_Loai_HD_Combobox();
        this.HT_Loai_Cap_Combobox();
        this.HT_LoaiHinh_TB_DatMoi_Combobox_V2();
        this.HT_ThietBi_IMS_Combobox();
        this.HT_BRAS_ADSL_Combobox();
        this.HT_ChiTietDV_IMS_Combobox();
        this.lay_ds_nhanvien_thuchien_theo_dv();
        this.dtpNgayGV = moment().format("DD/MM/YYYY HH:mm:ss");
        this.dtpNgayBG = moment().format("DD/MM/YYYY HH:mm:ss");
    },
}
</script>