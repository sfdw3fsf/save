<template src="./index.html"></template>
<script>
import DateTimeLib from "../../../../utils/DateTimeLib";

import Enum from "../DongBoVisaVasc/enum";

import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
export default {
    components: {SearchContractModal},
    watch: {
     'dataSelected.dichvu_id':async function(){
         await this.dichVu_onChange()
     } ,
      'dataSelected.loaihinh_id':async function(){
         await this.cbbLoaiHinh_Change()
     } ,
    },
    data () {
        return{
       dsPhieuYc:[],
       tongphieu:0,
       dsDVVT:[],
       dsKieuLD:[],
       dsLoaiHinh:[],
       dsDonViQL:[],
       dsNganHang:[],
       dsDoiTuong:[],
       dsHTTT:[],
       dsSoMay:[],
       objReset:{},
       dataSelected:{
           ma_gd:'',
           ngay_yc: DateTimeLib.toDateDisplay(new Date()),
           dichvu_id:-1,
           ngay_lap_hd:DateTimeLib.toDateDisplay(new Date()),
           kieuld_id:-1,
           loaihinh_id:-1,
           ma_kh:'',
           ten_kh:'',
           diachi_kh:'',
           somay_acc:'',
           ten_tbao:'',
           diachi_tbao:'',
           diachi_ld:'',
           doituong_id:-1,
           email:'',
           ma_thanhtoan:'',
           ma_daidien:'',
           ten_thanhtoan:'',
           diachi_tt:'',
           diachi_ct:'',
           diachi_bc:'',
           donvi_ql_id:-1,
           hinhthuc_tt_id:-1,
           ma_nvtc:'',
           ma_tuyen:'',
           tai_khoan:'',
           ma_sothue:'',
           nganhang_id:-1,
           ghichu:'',
           loaitb_id:-1  ,
           thuebao_id:-1 ,
           khachhang_id:-1 ,
           thanhtoan_id:-1,
           hdtt_id:-1,
           donvi_id:-1,
           hdtb_id:-1       
       },
       date: {
        dateVal: Date,
        dateFormat: "dd/MM/yyyy",
      },
      currentItem: {},
     isDisable:true,
     isDisableNhapMoi:true,
     isDisableHoanThien:false,
     isDisableNganHang:true,
     isDisableGhiLai:true,
     isVisibleGhiLai:false,
     hdkh_id:-1,
     dongbo_test:false,
     open_sync_vina_trasau:false
    }
    },
    created: async function () {
     await this.initForm()
     this.dongbo_test = await this.KIEMTRA_THAM_SO_DONGBO('DONGBO_TEST');
     this.open_sync_vina_trasau = await this.KIEMTRA_THAM_SO_DONGBO('OPEN_SYNC_VINA_TRASAU');
    },
    methods: {
     initForm:async function(){
       try {
        this.$root.showLoading(true)      
        await this.Lay_dulieu_combobox()
        await this.HienThiDanhSach()  
        Object.assign(this.objReset,this.dataSelected)     
      } catch (error) {
        return null;
      } finally {
          this.$root.showLoading(false)
      }
     },
     HienThiDanhSach: async function(){
         await this.Lay_ds_phieu()
     },
     lay_thamso_macdinh:async function(ma_ts){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_thamso_macdinh",
          {
            ma_ts: ma_ts,
          }
        );
        return rs.data
      } catch (error) {
        return null
      } finally {
      }
     },
     Lay_dulieu_combobox: async function(){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/hoanthien_cbb"
        );
        if (rs.data != null) {
          this.dsNganHang = rs.data.nganhang
          this.dsDVVT = rs.data.dichvu
          this.dsLoaiHinh = rs.data.loaihinh
          this.dsDoiTuong = rs.data.doituong
          this.dsDonViQL = rs.data.donvi
          this.dsHTTT = rs.data.httt
        }
        if(this.dsNganHang.length > 0)
         this.dataSelected.nganhang_id = this.dsNganHang[0].nganhang_id
        if(this.dsDVVT.length > 0) 
        this.dataSelected.dichvu_id = this.dsDVVT[0].dichvuvt_id
        if(this.dsLoaiHinh.length > 0){
        this.dataSelected.loaihinh_id = this.dsLoaiHinh[0].loaitb_id
         await this.get_ds_Kieu_ld(Enum.LoaiHopDong.TACHNHAP_TB,this.dataSelected.loaihinh_id)
         }
        if(this.dsDoiTuong.length > 0)
        this.dataSelected.doituong_id = this.dsDoiTuong[0].doituong_id
        if(this.dsHTTT.length > 0)
        this.dataSelected.hinhthuc_tt_id = this.dsHTTT[0].httt_id
         let donvi_id = await this.lay_thamso_macdinh('DONVITHU')
         if(donvi_id!=null)
         this.dataSelected.donvi_ql_id = donvi_id
         else
        if(this.dsDonViQL.length > 0)
        this.dataSelected.donvi_ql_id = this.dsDonViQL[0].donvi_id
      } catch (error) {
        return null;
      } finally {
      }
     },
     Lay_ds_phieu: async function (){
       try {
        this.$root.showLoading(true)
        let ma_gd ='0'
        // if(this.dataSelected.ma_gd !='')
        // ma_gd = this.dataSelected.ma_gd
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_hdtb_tachnhap_tb_hc",{
            ma_gd: ma_gd
          }
        );
        if (rs.data != null && rs.data.length > 0) {
         this.dsPhieuYc = rs.data
         this.tongphieu = rs.data.length
        }else{
        this.dsPhieuYc = []
         this.tongphieu = 0
        }
      } catch (error) {
        return null;
      } finally {
          this.$root.showLoading(false)
      }
     },
     LAY_HDTB_TACHNHAP_TB_HC: async function(ma_gd){
     try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_hdtb_tachnhap_tb_hc",{
            ma_gd: ma_gd
          }
        );
        return rs.data
      } catch (error) {
        return null;
      } finally {
         this.$root.showLoading(false)
      }
     },
     gridthuebao_selectedRowChanged:async function (item){
       let data=[]
       if(item!=null){
           Object.assign(this.currentItem , item)
           this.hdkh_id = item.hdkh_id
         data=  await this.LAY_DS_HOPDONG_KH_THEO_HDKH_ID(item.hdkh_id)
        await this.HienThiTTHopDongKH(data)
       }
     },
     gridthuebao_PageChanged: function(args){
 
     },
     LAM_MOI: function (){
      this.setButton(1)
     },
     HOAN_THIEN: async function (){
       
            try
            {
                this.$root.showLoading(true)
               let kt_taobang = await this.KT_TaoDB_Thang()
                if (kt_taobang == 0)
                {
                    this.$root.toastError("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !")
                    return;
                }

                if (Enum.TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU)
                {
                    //#region Hoàn thiện vinaphone
                    ///TuấnNA thêm code đồng bộ hoàn thiện vinaphone -- Ngày 12/08/2010
                    if (this.dataSelected.loaihinh_id == Enum.LoaiHinhTB.DIDONGTRASAU)
                    {
                        this.$root.toastError("Bạn phải hoàn thiện ở menu: Lập hợp đồng-> đồng bộ dữ liệu-> đồng bộ dữ liệu di động lên vinaphone");
                        return;
                    }
                    let ds = []
                    ds =await this.LAY_DS_HDTB_THEO_HDKH_TTHD(this.hdkh_id, Enum.TrangThaiHD.DA_THI_CONG_XONG);
                    let ma_gd_neo = this.dataSelected.ma_gd;// dsHDKH[0]["ma_gd"].toString();
                    let dsmatb = "";
                    let ds_hdtb_id = "";
                    for (let i = 0; i < ds.length; i++)
                    {
                        if (ds[i].dichvuvt_id == Enum.DichVuVienThong.DI_DONG 
                              && ds[i].loaitb_id == Enum.LoaiHinhTB.DIDONGTRASAU)
                        {
                            dsmatb = dsmatb + ds[i].ma_tb + ",84";
                            ds_hdtb_id = ds_hdtb_id + ds[i].hdtb_id+ ",";
                        }
                    }
                    if (dsmatb.length > 0)
                    {
                         //Check lại hàm subString
                        dsmatb = dsmatb.substring(0, dsmatb.length - 2)
                        ds_hdtb_id = ds_hdtb_id.substring(0, ds_hdtb_id.length)
                        for (let j = 0; j < 3; j++)
                        {
                            
                           // ERP báo chưa tích hợp
                             this.$root.toastError("ERP báo chưa cần tích hợp di động!");
                                break;
                         /* let kq = await this.HoanThienCQSD_TGTB(ma_gd_neo, dsmatb); 
                            if (kq == ma_gd_neo)
                            {

                                this.$root.toastError("Đã cập nhật danh bạ VinaPhone thành công!");
                                break;
                            }
                            if (j == 2)
                            {
                                this.$root.toastError("Có lỗi xảy ra trong quá trình cập nhật danh bạ lên VinaPhone.\nLỗi: " + kq);                              
                                return;
                            }*/
                        }
                    }
                    //#endregion
                }              
                let kq_kh = "";
                let ds_hdtb =[]
                ds_hdtb =await  this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id);
                let vcount = ds_hdtb.length;
                let vhdtb_id = []
                //VTNService vtn_service = new VTNService(tt_nd.user_vtn, tt_nd.pass_vtn);
                for (let i = 0; i < ds_hdtb.length; i++)
                {
                    vhdtb_id[i] = parseInt(ds_hdtb[i].hdtb_id);
                    if (ds_hdtb[i].dichvuvt_id == Enum.DichVuVienThong.IMS) //Enum.DichVuVienThong.IMS)
                    {
                        if(this.dongbo_test) //(Enum.DONGBO_TEST.TEST)
                        {
                             // Đang chờ API
                          let rs = await this.$root.context.post("web-ccdv/kichhoatims/tachnhapthuebao",
                                     { ma_tb:ds_hdtb[i].ma_tb,
                                       hdtt_id:parseInt(ds_hdtb[i].hdtt_id),
                                       hdtb_id:parseInt(ds_hdtb[i].hdtb_id) });
                            if(rs.data!=null)
                            kq_kh = rs.data;                        
                            if (kq_kh != "OK")
                            {
                               this.$root.toastError("Lỗi đồng bộ thuê bao " + ds_hdtb[i].ma_tb + " : " + kq_kh);
                                return;
                            }
                        }
                    }
                }

                //#endregion

                await  this.HoanThienHoSo(this.hdkh_id)
                for (let i = 0; i < vhdtb_id.length; i++)
                {
                  await  this.CAPNHAT_STATUS_HDTB(parseInt(vhdtb_id[i].toString()), Enum.TRANGTHAI_DONGBO.DONGBO_HT);
                  await  this.CapNhat_NVTH_ID(vhdtb_id[i]);
                  await  this.CapNhat_TT_Phieu_HTHS(vhdtb_id[i]);
                }

                this.$root.toastSuccess("Cập nhật thành công !");
                this.setButton(0)
                await this.HienThiDanhSach()
            }
            catch ( exp)
            {
                 this.$root.toastError('Có lỗi xảy ra trong quá trình hoàn thiện hồ sơ');
                 console.log(exp)
            }finally{
                this.$root.showLoading(false)
            }
     },
     TachNhapThueBao: async function(maTB,  hdtt_id,  hdtb_id){
      return 'OK'
     },
     LAY_DS_HDTB_THEO_HDKH_TTHD : async function (hdkh_id,  tthd_id){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_ds_hdtb_theohdkh_hths",
          {
            hdkh_id: hdkh_id,
            tthd_id: tthd_id,
          }
        );
        return rs.data
      } catch (error) {
        return false;
      } finally {
      }   
     },
     CAPNHAT_STATUS_HDTB: async function(hdtb_id,  status){
       try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/capnhat_trangthai_hdtb",
          {
            hdtb_id: hdtb_id,
            status: status,
          }
        );
        return true;
      } catch (error) {
        return false;
      } finally {
      }
     },
     CapNhat_NVTH_ID: async function (hdtb_id){
    try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/capnhat_nvth/"+hdtb_id
        );
        return true;
      } catch (error) {
        return false;
      } finally {
      }
     },
     CapNhat_TT_Phieu_HTHS: async function (hdtb_id){
     try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/capnhat_tt_phieu_hths/"+hdtb_id
        );
        return true;
      } catch (error) {
        return false;
      } finally {
      }
     },
     get_ds_Kieu_ld: async function (loaihd_id,loaitb_id){
     try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_kieu_ld",
          { loai_hd_id: loaihd_id, loai_tbi_id: loaitb_id }
        );
        if (rs.data != null) {
          this.dsKieuLD = rs.data;
          if(this.dataSelected.kieuld_id == -1)
            this.dataSelected.kieuld_id = rs.data[0].kieuld_id;
        }
      } catch (error) {
      } finally {
      }
     },
     cbbLoaiHinh_Change: async function (){
       await this.get_ds_Kieu_ld(Enum.LoaiHopDong.TACHNHAP_TB,this.dataSelected.loaihinh_id)
     },
     get_ds_LoaiHinh: async function(){
     try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_loaihinh/" + this.dataSelected.dichvu_id
        );
        if (rs.data != null) {
          this.dsLoaiHinh = rs.data;
          this.dataSelected.loaihinh_id = rs.data[0].loaitb_id;
        }
      } catch (error) {
      } finally {
      }
     },
     dichVu_onChange: async function(){
      await this.get_ds_LoaiHinh()
     },
     Httt_onChange: async function (){
       if(this.dataSelected.hinhthuc_tt_id == Enum.HinhThucThanhToan.UYNHIEM_THU)
         this.isDisableNganHang = false
       else
         this.isDisableNganHang = true
     },
     setButton:function(kieu){
         this.isDisableNhapMoi = false;
            ///TuấnNA comment code không hiển thị tsbtnGhiLai -- Ngày 09/08/2010
            //tsbtnGhiLai.Enabled = false;
          this.isDisableHoanThien = false;

            if (kieu == -1)//Bat dau
            {
                this.$refs['ma-tbao'].focus()
                this.isDisableHoanThien = true;
            }

            if (kieu == 0)//Bat dau
            {
                this.$refs['ma-tbao'].focus()
                this.Clear()
            }

            if (kieu == 1)//Them moi
            {
                this.$refs['ma-tbao'].focus()
               // tsbtnGhiLai.Enabled = true;
                this.Clear()
            }

            if (kieu == 2)//Huy
            {
                this.$refs['ma-dg'].focus()
                this.Clear()
            }

            if (kieu == 3)//Edit
            {
                this.$refs['ma-dg'].focus()
                this.isDisableHoanThien = true;
            }
     },
    Clear(){
       Object.assign(this.dataSelected,this.objReset)
     //Object.assign(this.$data.dataSelected, this.$options.data.dataSelected.call(this)); 
     //  Object.assign(this.$data, this.$options.data.call(this));
    },
    LAY_DS_HOPDONG_KH_THEO_HDKH_ID: async function (hdkh_id){
       try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_ds_hopdong_kh_theo_id/"+hdkh_id, 
        );
        if (rs.data != null) {
        return rs.data
        }
      } catch (error) {
        return null
      } finally {
      }
    },
    LAY_DS_HOPDONG_TT_THEO_HDKH_ID: async function (hdkh_id){
        try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_ds_hopdong_tt_theo_id/"+hdkh_id, 
        );
        if (rs.data != null) {
        return rs.data
        }
        else
        return null         
      } catch (error) {
        return null
      } finally {
      }
    },
    LAY_DS_HOPDONG_TB_THEO_HDKH_ID: async function (hdkh_id){
       try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_ds_hopdong_tb_theo_id/"+hdkh_id, 
        );
        if (rs.data != null) {
        return rs.data
        }
      } catch (error) {
        return null
      } finally {
      }
    },
    checkMapId: async function(map_id,no){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/check_map_id",{
            map_id:map_id,
            no:no
          }
        );
        if (rs.data != null) {
        return rs.data[0].id
        }
      } catch (error) {
        return 0
      } finally {
      }
    },
    chuanHoaTen: function(ten){
    if(ten!=null){
     let convertToArray = ten.toLowerCase().split(' ');
     let result = convertToArray.map(function(val) {
     return val.replace(val.charAt(0), val.charAt(0).toUpperCase());
     });
    return result.join(' ');
    }else
    return ''
    },
    HienThiTTHopDongKH: async function(data){
     if(data !=null && data.length>0){
        let item = data[0]
       if(await this.KIEMTRA_HD_CO_THUEBAO_DD(item.ma_gd)== 1){
         this.isDisableGhiLai =true
       }else
       this.isDisableGhiLai =false
       this.hdkh_id = item.hdkh_id
       this.dataSelected.ma_gd = item.ma_gd
       this.dataSelected.ma_kh = item.ma_kh
       if(item.khachhang_id !=null)
       this.khachhang_id = item.khachhang_id 

       this.dataSelected.ten_kh = this.chuanHoaTen(item.ten_kh)
       this.dataSelected.diachi_kh = this.chuanHoaTen(item.diachi_kh)
       if(item.ngay_lap_hd !=null)
        this.ngay_lap_hd = DateTimeLib.toDateDisplay(item.ngay_lap_hd)
       if(item.ngay_yc !=null)
        this.dataSelected.ngay_yc = DateTimeLib.toDateDisplay(item.ngay_yc)
       this.dataSelected.ten_kh = item.ten_kh
       this.dataSelected.email = item.email
       await this.HienThiTTHopDongTT(this.hdkh_id)
       await this.HienThiTTHopDongTB(this.hdkh_id)
       await this.HT_DanhSachTB(this.hdkh_id)
     }
    },
    async LAY_DS_SUDUNG_DVGT(){
     
    },
    async LAY_DS_DANGKY_DVK(){

    },
    HienThiTTHopDongTT: async function (hdkh_id){
      let ds=  await this.LAY_DS_HOPDONG_TT_THEO_HDKH_ID(hdkh_id)
      if(ds!=null && ds.length>0){
        let item = ds[0]
        this.dataSelected.hdtt_id = item.hdtt_id
        this.dataSelected.ma_thanhtoan = item.ma_tt
      
        if(item.thanhtoan_id!= null)
         this.dataSelected.thanhtoan_id = item.thanhtoan_id
         else
         this.dataSelected.thanhtoan_id = 0
        this.dataSelected.ten_thanhtoan = this.chuanHoaTen(item.ten_tt)
        this.dataSelected.diachi_tt = this.chuanHoaTen(item.diachi_tt)
        this.dataSelected.diachi_bc = this.chuanHoaTen(item.diachi_bc)
        this.dataSelected.diachi_ct = this.chuanHoaTen(item.diachi_ct)
        this.dataSelected.ghichu = item.ghichu
        this.dataSelected.ma_daidien = item.matb_dd
        // hiển thị địa chỉ
        this.dataSelected.ma_sothue = item.mst
        this.dataSelected.tai_khoan =item.stk
        if(item.nganhang_id !=null)
        this.dataSelected.nganhang_id =item.nganhang_id
        this.dataSelected.hinhthuc_tt_id = item.httt_id
        this.dataSelected.donvi_ql_id = item.donvi_id
        if(item.nhanvien_id !=null)
        {
          let nhanvientc_id  = parseInt(item.nhanvien_id)
          this.dataSelected.ma_nvtc = await this.checkMapId(nhanvientc_id,1)
        }else{
          let nhanvientc_id  = 0
          this.dataSelected.ma_nvtc = 0
        }
        if(item.tuyenthu_id!=null){
         let  tuyenthu_id = parseInt(item.tuyenthu_id)
         this.dataSelected.ma_tuyen =await this.checkMapId(tuyenthu_id,2)
        }
      }
    },
    HienThiTTHopDongTB: async function (hdkh_id){
    let ds=  await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdkh_id)
     if(ds!=null && ds.length > 0){
       let item =ds[0]
       this.dataSelected.hdtb_id = item.hdtb_id
        if (item.thuebao_id != null)
        this.dataSelected.thuebao_id = parseInt(item.thuebao_id)
        else
      this.dataSelected.thuebao_id = 0
      let ma_tb= item.ma_tb
      this.dataSelected.somay_acc = item.ma_tb
      this.dataSelected.ten_tbao = this.chuanHoaTen(item.ten_tb)
      this.dataSelected.diachi_tbao = this.chuanHoaTen(item.diachi_tb)
      this.dataSelected.diachi_ld = this.chuanHoaTen(item.diachi_ld)
      this.dataSelected.email = item.email
      this.dataSelected.loaitb_id =item.loaitb_id
      this.dataSelected.loaihinh_id = item.loaitb_id
      this.dataSelected.kieuld_id = item.kieuld_id
      this.dataSelected.doituong_id = item.doituong_id
      // Hiển thị
      this.dataSelected.dichvu_id = await this.thongTinchiTietLoaiHinh(item.loaitb_id)
     }
    },
    HT_DanhSachTB: async function (hdkh_id){
      this.dsSoMay = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdkh_id)
    },

    KIEMTRA_HD_CO_THUEBAO_DD: async function (ma_gd){
     try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/kiemtra_hd_co_thuebao_dd", {
            ma_gd:ma_gd
          }
        );
        return rs.data     
      } catch (error) {
        return null
      } finally {
      }
    },
    thongTinchiTietLoaiHinh: async function(loai_tbi_id){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_chitiet_loaihinh/"+loai_tbi_id
        );
        if (rs.data != null) {
        return rs.data[0].dichvuvt_id
        }
      } catch (error) {
        return 0
      } finally {
      }
    },
    getChecked: async function(hdtb_id){

    },
    KT_TaoDB_Thang: async function(){
    let ngay_cn = DateTimeLib.toDateDisplayWithFormat(new Date(),'yyyyMM')
    let vkyhoadon = "" //yyyyMMck
    let thang = parseInt(ngay_cn.substring(4, 6))
    let nam =   parseInt(ngay_cn.substring(0, 4))
            if (thang == 1)
            {
                thang = 12
                nam = nam - 1
            }
            else
            {
                thang = thang - 1
            }
            if (thang.toString().length > 1)
            {
                vkyhoadon = nam.toString() + thang.toString() + "01"
            }
            else
            {
                vkyhoadon = nam.toString() + "0" + thang.toString() + "01"
            }
          console.log('vkyhoadon',vkyhoadon)  
         return  await this.demKyHoaDon(vkyhoadon)
    },
    demKyHoaDon: async function(vkyhoadon){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/ktra_taodb_thang",
          {kyhoadon:vkyhoadon}
        );
        console.log('demKyHoaDon',rs.data)
        if (rs.data != null) {
        return rs.data[0].kq +1 // by pass data
        }
      } catch (error) {
        return 0
      } finally {
      }
    },
    //Hàm này ERP chưa cung cấp
    HoanThienCQSD_TGTB: async function(ma_gd_neo,  dsmatb){
            let result_ht = "";
            let result;

            let PSUSERID = this.ChuanHoa_XML(this.checkMapId(this.$root.token.getNguoiDungID(),3));
            
            if (PSUSERID == "-1")
            {
                this.$root.toastError("Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.");
                return null;
            }
            let PSMA_HD = ma_gd_neo;
            let PSDSMA_TB = "84" + dsmatb;
            let PSGHICHU = txtGhiChu.Text;//Ghi chu TT
            let PDNGAY_HT = DateTimeLib.toDateDisplay(new Date())
            return result_ht;
    },
    ChuanHoa_XML: function (str) {
      let result = "";
      result = str.replace("&", "&amp");
      result = result.replace("<", "&lt");
      result = result.replace(">", "&gt");
      result = result.replace("'", "&apos");
      result = result.replace(String.fromCharCode(34), "&quot");
      return result;
    },
    HoanThienHoSo: async function(hdkh_id){      
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/hoanthien_hs_tachnhap_tbao/"+hdkh_id
        );   
    },
    btnLayMaGD_Click:async function(){
    this.$refs['popupSearchContract'].showModal()
    //this.$root.toastSuccess("Chi tiết Tham chiếu UR2.1.008 Mục 1.1.2 - Tra cứu hợp đồng phụ lục")
    
    },
    acceptSearchContract:async function(ma){
    if (ma != null)
                {
                this.dataSelected.ma_gd = ma.ma_gd;
                let  ds =   await this.LAY_HDTB_TACHNHAP_TB_HC(this.dataSelected.ma_gd)
                await this.HienThiTTHopDongKH(ds)
                }
    },
    LAY_DANHBA_THEO_MATB: async function(ma_tb,  dichvuvt_id,  donvi_id){
      try {
        this.$root.showLoading(true)
        let rs = await this.$root.context.post(
          "/web-tracuu/tracu-danhba/LAY_DANHBA_THEO_MATB",{
           in_ma_tb:ma_tb,
           in_dichvuvt_id:dichvuvt_id,
           in_donvi_dl_id: donvi_id
          }
        );
        return rs.data
      } catch (error) {
        return null
      } finally {
         this.$root.showLoading(false)
      }
    },
    HienThiTT_DanhBa: async function(ma_tb){
       try
            {
                let ds = await this.LAY_DANHBA_THEO_MATB(ma_tb, this.dataSelected.dichvu_id, 0)
                if (ds!=null && ds.length > 0)
                {
                    if (this.hdkh_id == 0)
                    {
                        if (this.dataSelected.kieuld_id == Enum.KieuLapDat.TACH_THUEBAO)
                        {
                            //Thông tin khách hàng
                            this.dataSelected.somay_acc = ds[0].ma_kh
                            this.dataSelected.khachhang_id = parseInt(ds[0].khachhang_id)
                            this.dataSelected.ten_kh= ds[0].ten_kh
                            this.dataSelected.diachi_kh = ds[0].diachi_kh
                            //HienThiTTDiaChi(tcdanhba.LAY_DS_DIACHI_THEO_DBKHID(khachhang_id), 1);

                            //Thông tin thanh toán
                            this.dataSelected.ma_thanhtoan = "";
                            this.dataSelected.thanhtoan_id = 0;
                            this.dataSelected.ten_thanhtoan = "";
                            this.dataSelected.diachi_tt = "";
                            this.dataSelected.ma_sothue = "";
                            this.dataSelected.tai_khoan = "";
                            this.dataSelected.nganhang_id = 0;
                            this.dataSelected.hinhthuc_tt_id= 0;

                            this.dataSelected.donvi_ql_id = ds[0].donvi_id
                        }
                        else
                        {
                            this.dataSelected.somay_acc = ''
                            this.dataSelected.khachhang_id = 0
                            this.dataSelected.ten_kh=''
                            this.dataSelected.diachi_kh = ''
                            //HienThiTTDiaChi(tcdanhba.LAY_DS_DIACHI_THEO_DBKHID(khachhang_id), 1);

                            //Thông tin thanh toán
                            this.dataSelected.ma_thanhtoan = "";
                            this.dataSelected.thanhtoan_id = 0;
                            this.dataSelected.ten_thanhtoan = "";
                            this.dataSelected.diachi_tt = "";
                            this.dataSelected.ma_sothue = "";
                            this.dataSelected.tai_khoan = "";
                            this.dataSelected.nganhang_id = 0;
                            this.dataSelected.hinhthuc_tt_id= 0;
                            //Thông tin khách hàng
                            let donvi_id = await this.lay_thamso_macdinh('DONVITHU')
                            if(donvi_id !=null)
                            this.dataSelected.donvi_ql_id =donvi_id
                        }
                    }

                    //Thông tin thuê bao
                    this.dataSelected.thuebao_id = parseInt(ds[0].thuebao_id)
                    this.dataSelected.ten_tbao = ds[0].ten_tb
                    this.dataSelected.diachi_tbao = ds[0].diachi_tb
                    this.dataSelected.doituong_id = ds[0].doituong_id
                    this.dataSelected.loaitb_id = parseInt(ds[0].loaitb_id)

                   // SendKeys.Send("{TAB}");
                }
                else
                {
                    this.$root.toastError("Không tìm thấy thông tin !\nBạn hãy kiểm tra lại dịch vụ viễn thông, số máy/Acc");
                }
            }
            catch (ex)
            {
                this.$root.toastError("Có lỗi xảy ra trong quá trình thực hiện: " + ex.toString());
                console.log(ex)
            }
    },
    HienThiTT_DanhBa_KH_NhanTB:  async function(ma_tb){
        let ds = await this.LAY_DANHBA_THEO_MATB(ma_tb, this.dataSelected.dichvu_id, 0)
            if (ds.Count > 0)
            {
                //Thông tin khách hàng
                this.dataSelected.ma_kh = ds[0].ma_kh
                this.dataSelected.khachhang_id = parseInt(ds[0].khachhang_id)
                this.dataSelected.ten_kh = ds[0].ten_kh
                this.dataSelected.diachi_kh = ds[0].diachi_kh
                //HienThiTTDiaChi(tcdanhba.LAY_DS_DIACHI_THEO_DBKHID(khachhang_id), 1);

                //Thông tin thanh toán
                this.dataSelected.ma_thanhtoan = ds[0].ma_tt
                this.dataSelected.thanhtoan_id = parseInt(ds[0].thanhtoan_id)
                this.dataSelected.ten_thanhtoan = ds[0].ten_tt
                this.dataSelected.diachi_tt = ds[0].diachi_tt
                //HienThiTTDiaChi(tcdanhba.LAY_DS_DIACHI_THEO_DBTTID(thanhtoan_id), 2);
               this.dataSelected.ma_sothue = ds[0].mst
                this.dataSelected.tai_khoan = ds[0].stk
                if (ds[0].nganhang_id != "")
                    this.dataSelected.nganhang_id = ds[0].nganhang_id;
                this.dataSelected.hinhthuc_tt_id = ds[0].httt_id;
                this.dataSelected.donvi_ql_id = parseInt(ds[0].donvi_id);
            }
            else
            {this.$root.toastError("Nhập lại mã thuê bao tìm kiếm !")
            }
    },
    //#region key Press
    madg_keypress: async function(e){

       if(e.code =='Enter'){
         if(this.dataSelected.ma_gd !=''){
            let  ds=   await this.LAY_HDTB_TACHNHAP_TB_HC(this.dataSelected.ma_gd)
            console.log('ds:',ds)
            if(ds!=null && ds.length>0)
           await this.HienThiTTHopDongKH(ds)
           else
           this.Clear()
         }
       }
    },
    matb_keypress: async function(e){
      if(e.code =='Enter'){
        let ds=[]
         if(this.dataSelected.somay_acc !=''){
         ds=   await this.LAY_HDTB_TACHNHAP_TB_HC('0')
            if(ds!=null && ds.length>0)
          await  this.HienThiTTHopDongKH(ds)
            else
           await  this.HienThiTT_DanhBa(this.dataSelected.somay_acc)
         }
       }
    },
    tentb_keypress: async function(e){
    
    },
    makh_keypress: async function(e){
    if(e.code =='Enter'){
         if(this.dataSelected.ma_kh !=''){       
           await  this.HienThiTT_DanhBa_KH_NhanTB(this.dataSelected.ma_kh)
         }
       }
    },
    tenkh_keypress: async function(e){

    },
    matt_keypress: async function(e){
    if(e.code =='Enter'){
       if(this.dataSelected.ma_thanhtoan !=''){
         await  this.HienThiTT_DanhBa_KH_NhanTB(this.dataSelected.ma_thanhtoan)
       }
       }
    },
    tentt_keypress: async function(e){

    },
    async KIEMTRA_THAM_SO_DONGBO(keyname) {
      try{
        let kq = await this.$root.context.get("web-ccdv/hoanthienhschuyenquyen/kiemtra_open_sync_vina", {
          keyname: keyname
        })
        if (kq.data && kq.data.length > 0) {
          return kq.data[0].KEY_VALUE
        } else {
          return false
        }
      }catch(e){
        return false;
      }
      },
    //#endregion
  },
   
}
</script>
<style>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>