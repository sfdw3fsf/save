<template src="./index.html"></template>
<script>
import ChonIP from "./Modal";
import DateTimeLib from "../../../../utils/DateTimeLib";
import Enum from "./enum";
import vdc from "./service/vdcService";
import vasc from "./service/vascService";
import HandoverTicketContent from '@/modules/contract/setup/HandoverTicket/HandoverTicket_content.vue'
export default {
  components: { ChonIP ,HandoverTicketContent},
  name: "dongBoVisaVasc",
  computed: {
    EnabledongBo: function(){
     
      let f = this.dsThueBao != null &&  this.dsThueBao.length > 0 && this.rad_kichhoat == 0 && !this.disableButton.dongBo
      console.log('EnabledongBo',f)
      return f;//this.dsThueBao != null &&  this.dsThueBao.length > 0 && this.rad_kichhoat == 0 && !this.disableButton.dongBo
    },
    EnablehoanCong: function(){
      console.log('hoanCong',this.disableButton.hoanCong)
      return this.dsThueBao != null &&  this.dsThueBao.length > 0 && this.rad_kichhoat == 1 && !this.disableButton.hoanCong
    },
    EnableHuyKichHoat: function(){
      return this.dsThueBao != null &&  this.dsThueBao.length > 0 && !this.disableButton.huyKichHoat
    },
    EnableGiaoPhieu: function(){
      return this.dsThueBao != null &&  this.dsThueBao.length > 0 && !this.disableButton.giaoPhieu
    }
  },
  watch: {
    giu_ip: function (val) {
      if (val) {
        this.isDisableTuDong = true;
        this.tudong = false;
        this.isDisableChonIp = true;
        this.diachi_ip = "";
      } else {
        this.isDisableTuDong = false;
        this.isDisableChonIp = false;
      }
    },
    tudong: function (val) {
      if (val) {
        this.diachi_ip = "";
        this.isDisableChonIp = true;
      } else {
        this.isDisableChonIp = false;
      }
    },
    rad_kichhoat: async function (val) {
      if (val == 0) {
        this.Lay_TrangThai_HD();
        await this.HienThiDanhSachTB();
        this.disableButton.hoanCong = false;
        this.disableButton.dongBo = true;
        this.HienThiHuyKH();
      } else {
         this.Lay_TrangThai_HD();
        await this.HienThiDanhSachTB();
        this.disableButton.dongBo = true;
        this.disableButton.hoanCong = false;
        this.HienThiHuyKH();
      }
    },
    diachi_ip: function(val){
      if(val != "")
      this.isErrorIp = false;
      else
      this.isErrorIp = true;
    },
    quytrinh_id: async function (val){
     this.HienThiGiaoDien(val);
    },
    quytrinh_id:  function (val){
     this.onChangeQuyTrinh();
    },
  },
  destroyed() {},
  methods: {
    //#region Hàm con
    HienThiDanhSachTB: async function () {
      this.lblTrangThaiMyTV = ''
      this.dsLyDoHuy = []
      this.lvwDVGT = []
      await this.Lay_ds_hdtb_gd_kichhoat();
      if(this.dsThueBao !=null && this.dsThueBao.length > 0){
        this.disableButton.dongBo = !(this.rad_kichhoat == 0);
        console.log('dongBo',this.disableButton.dongBo)
        this.disableButton.hoanCong = !(this.rad_kichhoat == 1);
      }else{
          this.thongTinKH.ma_gd = '';
          this.thongTinKH.ngay_yc = '';
          this.thongTinKH.ma_kh = '';
          this.thongTinKH.ten_kh = '';
          this.thongTinKH.diachi_kh = '';
          this.thongTinKH.ghichu = '';
          this.txtAccountCu = '';
          this.disableButton.dongBo = true;
          this.disableButton.hoanCong = true;
           this.disableButton.huyKichHoat = true;
      }
    },
    HienThiGiaoDien: async function (quytrinh_id){
       try {
        console.log('HienThiGiaoDien')
            this.showGiaoPhieu = false;
            this.showHoanCong = false;
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_danhsach_thaotac/"+quytrinh_id       
        );
        console.log('dsThaoTac',rs.data)
        this.dsThaoTac = rs.data
        if(this.dsThaoTac!=null && this.dsThaoTac.length >0){
        for(let i=0;i<this.dsThaoTac.length;i++){
          let temp = await this.lay_thaotac_control(this.dsThaoTac[i].thaotac_id)
          if(temp!=null && temp.length>0){
            if(temp.filter(x=>x.control_name.toLowerCase().includes('giaophieu')))
             {
              if(this.dsThaoTac[i].enable == 1){
               this.disableButton.giaoPhieu = false;
               this.showGiaoPhieu = true;
             }else{
              this.disableButton.giaoPhieu = true;
             }
            }
             if(temp.filter(x=>x.control_name.toLowerCase().includes('kichhoat')))
             {
              if(this.dsThaoTac[i].enable ==1)
               this.disableButton.dongBo = false;
             }else{
              this.disableButton.dongBo = true;
             }
             if(temp.filter(x=>x.control_name.toLowerCase().includes('hoancong')))
             {
              if(this.dsThaoTac[i].enable ==1)
               this.disableButton.hoanCong = false;
               this.showHoanCong = true;
             }else{
              this.disableButton.hoanCong = true;
             }
             if(temp.filter(x=>x.control_name.toLowerCase().includes('huykh')))
             {
              if(this.dsThaoTac[i].enable ==1)
               this.disableButton.huyKichHoat = false;
              // this.showGiaoPhieu = true;
             }else{
              this.disableButton.huyKichHoat = true;
             }
          }
        }
          
        }

      } catch (error) {
        return false;
      } finally {
      }
    },
    lay_thaotac_control: async function(thaotac_id){
      let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_thactac_control/"+thaotac_id       
        );
        return rs.data;
    },
    Kiemtra_doituong_vdc: async function (hdtb_id, loaihinh_id) {
      return await vdc.Kiemtra_doituong_vdc(hdtb_id, loaihinh_id);
    },
    ThayDoi_DoiTuong_Visa: async function (doituong_moi) {
      let cusType = 0;
      try {
        let account = this.account
        cusType =  await this.checkMapId_new(doituong_moi,4)    
        return await vdc.ChangeCustomerType(account, cusType);
      } catch (ex) {
        return false;
      }
    },
    CAPNHAT_NGAYKH_NGAY_HT_V2: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/capnhat_ngaykh_ngay_ht_v2",
          {
            hdtb_id: hdtb_id,
          }
        );
        if(rs.error_code == "BSS-00000000")
        return true
        else{
          this.$root.toastError(rs.message);
          return false;
        }
       
      } catch (error) {
        return false;
      } 
    },
    KIEMTRA_TB_DONGBO_EPG_VTT: async function (account, ngayth) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/kiemtra_tb_dongbo_epg_vtt",
          {
            account: account,
            ngayth:ngayth
          }
        );
        return rs.data;
      } catch (error) {
        return false;
      } 
    },
    ThayDoi_NhomKhachHang_UuTien_VASC: async function (vma_tb, vdoituong_id) {
      try {
        let IPTVAccount = vma_tb;
        let sPromo_id = "0";
        if (vdoituong_id == Enum.DoiTuong.Y_TE) sPromo_id = "DT02";

        let kq = await vasc.ChangePromotions(IPTVAccount, sPromo_id);
        if (kq == 0) return true;
        else return false;
      } catch (ex) {
        console.log(ex)
        return false;
      }
    },
    DoiTocDoMyTV: async function (tb_mytv, vmatocdo, lydo) {
      let kq = await this.ChangePackage(tb_mytv, vmatocdo, "", "", lydo)
      if ( kq == 0) {
        return true;
      } else return false;
    },
    ChangePackage: async function (tb_mytv,vmatocdo,ServiceCode,ServiceVASCode,lydo) {
      return await vasc.ChangePackage(tb_mytv, vmatocdo,ServiceCode,ServiceVASCode,lydo);
    },
    Kt_thuebao_goi_giadinh: async function (hdtb_id, thuebao_id) {
      try {
        let rs = await this.$root.context.post(
          "/web-ccdv/hths-thaydoi-tocdo/sp_kt_thuebao_goi_giadinh",
          {
            phanvung_id:this.$root.token.getPhanVungID(),
            hdtb_id: hdtb_id,
            thuebao_id: thuebao_id,
          }
        );
        return rs.data;
      } catch (error) {
        return null;
      } 
    },
    ChangePackageAndMegaVNN: async function ( IPTVAccount,packageNo,serviceCode,
      serviceVASCode,megaMyTV,reason,vCombo_FiberCD) {
        try{
       let obj = await  vasc.ChangeMegaVNN(IPTVAccount, megaMyTV, vCombo_FiberCD);
      if(obj.data != null){
      if (obj.data.result != 0) {
        if (obj.data.result  == 1) {
          this.$root.toastError(
            "Tài khoản " + IPTVAccount + " không tồn tại trên hệ thống !"
          );
        } else {
          if (obj.data.errordesc == "Khách hàng hiện đang sử dụng gói cước này") {
            return 0;
          } else this.$root.toastError(obj.data.errordesc);
        }
      }
     return obj.data.result;
    }
      return 1;
        }catch(e){
         return null 
        }
    },
    kt_mytv_moi: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/kt_mytv_moi/"+hdtb_id          
        );
        return rs.data;
      } catch (error) {
       console.log(error)
       return "";
      }
    },
    STB_Rent_UpdateInfo: async function (username, rent_status) {
      try{
      let kq = [];
      let request = {};
      request.USERNAME = username;
      request.RENT_STATUS = rent_status;
      let obj = await vasc.STB_Rent_UpdateInfo(request);
      let message = "";
      let result = 0;
      if(obj !=null){
      if (obj.result != 1) {
        if (obj.result == -2) {
          message = "Dữ liệu đầu vào không hợp lệ. (" + obj.errordesc + ")";
        } else if (obj.result == -3) {
          message = "Dữ liệu vào DB không thành công (" + obj.errordesc + ")";
        } else if (obj.result == 1080) {
          message = "Lỗi VACS trả về: Lỗi ngoại lệ (" + obj.errordesc + ")";
        } else {
          message = "Nguyên nhân gây lỗi: " + obj.errordesc;
        }
      }
      result = obj.result
      }
      kq[0] = result;
      kq[1] = message;

      return kq;
      }catch(e){
        return null
      }
    },
    DoiKieuTraMyTV: async function (tb_mytv, hinhthuc, thang, ngay) {
      try{
      
      let kq = await vasc.MethodChangePay(tb_mytv, hinhthuc, thang, ngay)
      if (kq == 0) {
        return true;
      } else return false;
      }catch(e){
        return false
      }
    },
    set_services_for_customer: async function (account,ServiceIDs,startDate,endDate) {
      // bỏ tích hợp SGTV
      return "0";
    },
    kiemtra_tb_cs_roimang: async function (hdtb_id, nhanvien_cs_id = 0) {
 
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/kiemtra_tb_cs_roimang",
          {
            hdtb_id: hdtb_id,
            nhanvien_cs_id: nhanvien_cs_id,
          }
        );
        return rs.data;
      } catch (error) {
        return null;
      }
    },
    ThayDoiTrangThai_1: async function (account, trangthai, lydo, ghichu) {
      try {
        let myaccount = new Object();
        myaccount.accountName = account;
        myaccount.status = trangthai;
        return await vdc.updateStatusVNN(myaccount, lydo, ghichu);
      } catch (ex) {      
          return false;
        }     
    },
    SuspendSubscriberV2_2: async function (IPTVAccount, ReasonID) {
      try{
      let obj = await vasc.SuspendSubscriberV2(IPTVAccount, ReasonID);
      if (obj.data !=null && obj.data.result != "0") {
        if (obj.data.result == "1") {
          this.$root.toastError(
            "Tài khoản " + IPTVAccount + " không tồn tại trên hệ thống !"
          );
        } else {
          if (obj.faultString.includes("Tài khoản đang bị tạm ngưng")) return 0;
          this.$root.toastError(obj.faultString);
        }
        return obj.data.result;
      }
      return 0;
      }catch (e) {return 1}
    },
    lay_matocdo_by_hdtb_id: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_matocdo_by_hdtb_id/" + hdtb_id
        );
        if (rs.data != null) return rs.data[0];
      } catch (error) {
        return null;
      } 
    },
    lay_matocdo_by_hdtb_idv2: async function (hdtb_id) {
      try {
        //Gọi api ngoài
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_matocdo_by_hdtb_idv2/" + hdtb_id
        );
        if (rs.data != null) return rs.data[0];
      } catch (error) {
        return null;
      } 
    },
    ThayDoiTrangThai: async function (account, trangthai, lydo, ghichu) {
      try {
        let myaccount  = new Object();;
        myaccount.accountName = account;
        myaccount.status = trangthai;
       let rs = await vdc.updateStatusVNN(myaccount, lydo, ghichu);
       console.log(rs)
        return rs;
      } catch (ex) {
        return false
      }
    },
    ResumeSubscriber: async function (IPTVAccount, Reason) {
      try{
      let obj = await vasc.ResumeSubscriber(IPTVAccount, Reason);
      if(obj.errorCode == 0)
      return 0;
      else
           this.$root.toastError(obj.faultString);
           // "Tài khoản " + IPTVAccount + " không tồn tại trên hệ thống !"
          return obj.errorCode;
      }catch(e){
        return -1;
        }
    },
    Kiemtra_dk_dvgt_th_faf: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/Kiemtra_dk_dvgt_th_faf/" + hdtb_id
        );
        return rs.data;
      } catch (error) {
        return null;
      } 
    },
    TotalMyTVPlus: async function (IPTVAccount, OperatorCD, ServiceCD, Reason) {
      try {
        let objrequest  = new Object();;
        objrequest.iptvaccount = IPTVAccount;
        objrequest.operatorCD = OperatorCD;
        objrequest.reason = Reason;
        objrequest.serviceCD = ServiceCD;
        let obj = await vasc.TotalMyTVPlus(objrequest);
        // if (obj != 0) {
        //   if (obj == 1) {
        //     this.$root.toastError(
        //       "Tài khoản " + IPTVAccount + " không tồn tại trên hệ thống !"
        //     );
        //   } else {
        //     this.$root.toastError(obj.errordesc);
        //   }
        // }
        return obj;
      } catch (e) {
        return -1;
      }
    },
    kt_dangky_paytv: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/kt_dangky_paytv/" + hdtb_id
        );
        return rs.data;
      } catch (error) {
        return null;
      } 
    },
    ChangePackageV3: async function (IPTVAccount,PackageNo,Reason,MegaMytv,AddOnCD, _strMgs) {
      try {
        let objrequest  = new Object();;
        objrequest.iptvaccount = IPTVAccount
        objrequest.packageNo = PackageNo
        objrequest.reason = Reason
        objrequest.megaMyTV = MegaMytv
        objrequest.addOnCD = AddOnCD
        objrequest.combo_FiberCD=''
        let obj = await vasc.ChangePackageV3(objrequest);
        // _strMgs = "";
        // if (obj != 0) {
        //   if (obj == 1) {
        //     _strMgs =
        //       "Tài khoản " + IPTVAccount + " không tồn tại trên hệ thống !";
        //   } else {
        //     _strMgs = '';
        //   }
        // }
        // obj._strMgs = _strMgs;
        return obj;
      } catch (e) {
        return null;
      }
    },
    PayTVFull_Register: async function (MYTVAccount, month) {
      try {
        let obj = await vasc.PayTVFull_Register(MYTVAccount, month);
        if (obj != null) {
          if (obj != 0) {
            if (obj == 1) {
              this.$root.toastError(
                "Thông tin đầu vào không hợp lệ, hoặc gói cước không phải Silver/Silver HD !"
              );
            } else if (obj == 23) {
            } else if (obj == 22) {
              this.$root.toastError(
                "Tài khoản thuê bao đăng ký không hợp lệ !"
              );
            } else if (obj == 24) {
              this.$root.toastError(
                "Thuê bao đang trong chương trình ưu đãi rồi !"
              );
            } else {
              this.$root.toastError(obj.errordesc);
            }
          }
          return obj;
        }
        return null;
      } catch (e) {
        return null;
      }
    },
    Kiemtra_dk_combo_fafilm: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/Kiemtra_dk_combo_fafilm/" + hdtb_id
        );
        return rs.data;
      } catch (error) {
        return null;
      } 
    },
    ComboFAF_Update:async function (MyTVAccount, MonthFree, Operator) {
      try {
        let obj = await vasc.ComboFAF_Update(MyTVAccount, MonthFree, Operator);
        if (obj != 0) {
          if (obj == 1) {
            this.$root.toastError(
              "Tài khoản " + MyTVAccount + " không tồn tại trên hệ thống!"
            );
          } else {
            this.$root.toastError(obj.errordesc);
          }
        }
        return obj;
      } catch (error) {
        return null;
      }
    },
    KIEMTRA_KICHHOAT_VISA: async function (thuebao_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/kiemtra_kichhoat_visa/" + thuebao_id
        );
        return rs.data;
      } catch (error) {
        return 0;
      } 
    },
    KichHoat_ThanhLy: async function (account,lydo,chuthich,in_loaitb_id,in_hdtb_id) {
      let status = false;
      try {
        //#region Hủy Mega, Fiber, 1260
        if (
          in_loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
          in_loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER ||
          in_loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
          in_loaitb_id == Enum.LoaiHinhTB.INTERNET_1260
        ) {
         
          let kieu_mega = "1";
          let ds_mega = [];
          ds_mega = await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(in_hdtb_id);
          if (ds_mega != null)
            kieu_mega = await this.checkMapId_new(ds_mega.tocdo_id,5)
          if (kieu_mega != "0") {
            // hủy greenonline nếu có
            let ktra = false;
            let thuebao_id;
            thuebao_id = await this.checkMapId_new(in_hdtb_id,8)
            ktra = await this.KIEMTRA_TB_SD_DVGT(thuebao_id,Enum.DichVuGiaTang.GREEN_ONLINE);
            if (ktra) {
              let thanhtoan_id = await this.checkMapId_new(thuebao_id,15) 
              let donvitt_id = await this.checkMapId_new(thanhtoan_id,16) 
              let kt = 0;
              kt = await this.DangKy(
                account,
                "C",
                this.$root.token.getUserName(),
                this.hdtb_id,
                this.ten_tat_ccbs,
                0,
                donvitt_id
              );
              if (kt == 0) {             
                return false;
              }
            }
            // end hủy greenonline           
            lydo = this.currentLydoHuy.lydohuy_id_vdc;
            status = await vdc.RemoveVNN(account,lydo,"HP: " +chuthich + this.$root.token.getNguoiDungID());
          } else status = true;
        }
        //#endregion

        // #region hủy Mytv
        else if (
          in_loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV ||
          in_loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV_B2B) 
          {
          let dt_ldh = await this.Lay_DS_LyDoHuy_HDTB(in_hdtb_id); 
          let lydohuy_id = parseInt(dt_ldh[0].lydohuy_id_vasc);
          let kq = await vasc.CancelSubscriberV2(account, lydohuy_id);
          if (kq != 0) status = false;
          else status = true;
        }
        // #endregion

        // #region Hủy Sài Gòn TV
        else if (in_loaitb_id == Enum.LoaiHinhTB.INTERNET_SGTV) {
          let result = await this.delete_customer(account, "N");

          if (result != "0") {
            this.$root.toastError(result);
            status = false;
          } else status = true;
        }
        //#endregion

        //#region Hủy HTVC_FIBERVNN
        else if (
          in_loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_ANALOG ||
          in_loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_DIGITAL
        ) {
          status = true;
        }
        //#endregion
        if (status) {
          await this.CAPNHAT_NGAYKH_NGAY_HT_V2(in_hdtb_id)
        } else {
          return false;
        }
        return true;
      } catch (e) {
        console.log(e)
        this.$root.toastError("Đồng bộ thuê bao thất bại!");
        return false;
      }
    },
    delete_customer: async function (account, force) {
      return "0";
    },
    TTCT_DichVuGT: async function (dichvugt_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/ttct_dichvugt_id/" + dichvugt_id
        );
        return rs.data;
      } catch (error) {
        return null;
      } finally {
      }
    },
    KIEMTRA_TB_SD_DVGT: async function (thuebao_id, dichvugt_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/kiemtra_tb_sd_dvgt",
          {
            thuebao_id: thuebao_id,
            dichvugt_id: dichvugt_id,
          }
        );
        return rs.data.length > 0;
      } catch (error) {
        return false;
      } finally {
      }
    },
    DangKyV2: async function ( account,flag, user,acc_id,province,promotioncode,unit,name,address,phone,email) {
      try
          {
       let rs = await this.$root.context.post("/tichhop/green_online/DangKyV2FW",
          {
            acc_id: acc_id,
            address: address,
            email: email,
            flag: flag,
            id: account,
            name: name,
            operator: user,
            phone: phone,
            promotioncode: promotioncode,
            province: province,
            unit: unit
          });
            if(rs.errorCode == 0)
            return 1
            else {
            this.$root.toastError(rs.faultString)
            return 0
          }
            }
            catch (ex)
            {
                console.log(ex)
                return 0;
            }
    },
    DangKy: async function ( account,flag, user,acc_id,province,promotioncode,unit) {
      try
          {
       let rs = await this.$root.context.post("/tichhop/green_online/DangKy",
          {
            acc_id: acc_id,
            flag: flag,
            id: account,
            operator: user,
            promotioncode: promotioncode,
            province: province,
            unit: unit
          });
            if(rs.errorCode == 0)
            return 1
            else {
            this.$root.toastError(rs.faultString)
            return 0
          }
            }
            catch (ex)
            {
                console.log(ex)
                return 0;
            }
    },
    Doi_Account: async function (accountcu, accountmoi) {
      try{
      // hủy greenonline nếu có
      let ktra = false;
      let thuebao_id;
      thuebao_id = await this.checkMapId_new(this.hdtb_id,8) 
      ktra = await  this.KIEMTRA_TB_SD_DVGT(thuebao_id,Enum.DichVuGiaTang.GREEN_ONLINE);
      if (ktra) {
        let thanhtoan_id = await this.checkMapId_new(thuebao_id,15) 
        let donvitt_id = await this.checkMapId_new(thanhtoan_id,16) 
        let kt = 0;
        kt = await  this.DangKy(accountcu,"C",this.$root.token.getUserName(),this.hdtb_id,this.ten_tat_ccbs,0,donvitt_id);
        if (kt == 0) {
          this.$root.toastError(
            "Có lỗi hủy dịch vụ GreenOnline cho thuê bao cũ : " + accountcu
          );
          return false;
        }
        let khuyenmai_id = 0;
        kt = await  this.DangKy(accountmoi,"O",this.$root.token.getUserName(),this.hdtb_id,this.ten_tat_ccbs,khuyenmai_id,donvitt_id);
        if (kt == 0) {
          this.$root.toastError(
            "Có lỗi đăng ký dịch vụ GreenOnline cho thuê bao mới : " +
              accountmoi
          );
          return false;
        }
      }
      // end hủy greenonline

      //vdcService = new VDCService(tt_nd.user_visa, tt_nd.pass_visa);
    await  vdc.HuyDatChoMegaVNN(accountmoi);
      if (await vdc.ModifyAccountNameVNN(accountcu, accountmoi)) {
        let vmail_cu = accountcu + "@vnn.vn";
        if (await vdc.ModifyMailRASName(vmail_cu, accountmoi)) {
          await this.CAPNHAT_STATUS_HDTB(this.hdtb_id,Enum.TRANGTHAI_DONGBO.DONGBO_CM);
          this.$root.toastSuccess(
            "Kích hoạt đổi Account trên Visa thành công!"
          );
          return true;
        } else {
          this.$root.toastError(
            "Có lỗi trong quá trình kích hoạt đổi Mail trên Visa !"
          );
          return false;
        }
      } else {
        this.$root.toastError(
          "Có lỗi trong quá trình kích hoạt đổi Account trên Visa !"
        );
        return false;
      }
      }catch(e){
        console.log(e)
        return false;
      }
    },
    CAPNHAT_STATUS_HDTB: async function (hdtb_id, status) {
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
      } 
    },
    B2B_Room_RegistAddOn: async function (HOTEL_CODE,ROOM_CODE, USERNAME, REGISTDATE,CANCELDATE,ADDON_CODE, UNITPRICE, thongbaoDHSX ) {
      try {
        let room = {};
        room.hotel_CODE = HOTEL_CODE;
        room.room_CODE = ROOM_CODE;
        room.username = USERNAME;
        room.registdate = REGISTDATE;
        room.canceldate = CANCELDATE;
        room.addon_CODE = ADDON_CODE;
        room.zipcd = this.$root.context.user.getProperty('tentat_vasc');
        room.unitprice = UNITPRICE;

        let obj = await vasc.B2B_Room_RegistAddOn(room);
        if (obj != 0) {
          if (obj == -1) {
            this.$root.toastError(
              "[B2B-RegAdd] (" +
                thongbaoDHSX +
                ") Dữ liệu đầu vào không hợp lệ! " + obj.errordesc                
            );
          } else if (obj == -2) {
            this.$root.toastError(
              "[B2B-RegAdd] (" +
                thongbaoDHSX +
                ") Lưu dữ liệu vào DB không thành công !" + obj.errordesc
            );
          } else if (obj == -8) {
            this.$root.toastError(
              "[B2B-RegAdd] (" +
                thongbaoDHSX +
                ") Mã lỗi Exception ngoại lệ " + obj.errordesc
            );
          } else if (obj == 2009) {
            this.$root.toastError(
              "[B2B-RegAdd](" +
                thongbaoDHSX +
                ")Tài khoản xác thực không đúng " + obj.errordesc
            );
          } else if (obj == 2010) {
            this.$root.toastError(
              " [B2B-RegAdd] (" +
                thongbaoDHSX +
                ")Chưa có tài khoản xác thực WS " + obj.errordesc
            );
          } else if (obj != -3) {
            this.$root.toastError(
              "[B2B-RegAdd] (" + thongbaoDHSX + ") " + obj.errordesc
            );
          }
        }
        return obj;
      } catch (e) {
        return -1;
      }
    },
    B2B_Room_CancelAddOn: async function (HOTEL_CODE,  ROOM_CODE, USERNAME,REGISTDATE,  CANCELDATE,  ADDON_CODE, UNITPRICE,  thongbaoDHSX ) {
      try {
        let room = {};
       room.hotel_CODE = HOTEL_CODE;
        room.room_CODE = ROOM_CODE;
        room.username = USERNAME;
        room.registdate = REGISTDATE;
        room.canceldate = CANCELDATE;
        room.addon_CODE = ADDON_CODE;
        room.zipcd = this.$root.context.user.getProperty('tentat_vasc');
        room.unitprice = UNITPRICE;

        let obj =await vasc.B2B_Room_RegistAddOn(room);
        if (obj != 0) {
          if (obj == -1) {
            this.$root.toastError(
              "[B2B-RegAdd] (" +
                thongbaoDHSX +
                ") Dữ liệu đầu vào không hợp lệ! " + obj.errordesc

            );
          } else if (obj == -2) {
            this.$root.toastError(
              "[B2B-RegAdd] (" +
                thongbaoDHSX +
                ") Lưu dữ liệu vào DB không thành công !" + obj.errordesc

            );
          } else if (obj == -8) {
            this.$root.toastError(
              "[B2B-RegAdd] (" +
                thongbaoDHSX +
                ") Mã lỗi Exception ngoại lệ " + obj.errordesc
            );
          } else if (obj == 2009) {
            this.$root.toastError(
              "[B2B-RegAdd](" +
                thongbaoDHSX +
                ")Tài khoản xác thực không đúng " + obj.errordesc
            );
          } else if (obj == 2010) {
            this.$root.toastError(
              " [B2B-RegAdd] (" +
                thongbaoDHSX +
                ")Chưa có tài khoản xác thực WS " + obj.errordesc
            );
          } else if (obj != -3) {
            this.$root.toastError(
              "[B2B-RegAdd] (" + thongbaoDHSX + ") " + obj.errordesc
            );
          }
        }
        return obj;
      } catch (e) {
        return -1;
      }
    },
    lay_ds_goi_mytvtitan: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_goi_mytvtitan/" + hdtb_id
        );
        return rs.data;
      } catch (error) {
        return null;
      } finally {
      }
    },
    CheckIsNumber: function (str) {
      try {
        let num = parseInt(str);
        return true;
      } catch (e) {
        return false;
      }
    },
    LayDuLieu_TTTC: async function (thongtin_tc) {
      try {
        console.log('thongtin_tc',thongtin_tc);
        if (thongtin_tc != null && thongtin_tc != "") {
          // Nếu có thông tin tính cước
          let strArr2 = [];
          let temp;
          strArr2 = thongtin_tc.split(";");
          for (let i = 0; i < strArr2.length; i++) {
            temp = "";
            var  temp1 = strArr2[i].split(":");
            if(temp1.length > 1)
              temp= temp1[1]
            
            // temp = strArr2[i].substring (
            //   strArr2[i].indexOf(":") + 1,
            //   strArr2[i].length - strArr2[i].indexOf(":") - 1
            // );

            if (i == 0) {
              this.txtcuoctb = temp;
              continue;
            }
            if (i == 1) {
              this.txtcuockh = temp;
              continue;
            }
            if (i == 2) {
              this.txtcuoctg = temp;
              continue;
            }
            if (i == 3) {
              this.txttlgiam = temp;
              continue;
            }
            if (i == 4) {
              if (temp != "") this.dtpngiam = DateTimeLib.toDateDisplay(temp);
              continue;
            }
            if (i == 5) {
              this.txtthgiam = temp;
              continue;
            }
            if (i == 6) {
              this.txtmienip = temp;
              continue;
            }
            if (i == 7) {
              this.txtslemail = temp;
              continue;
            }
            if (i == 8) {
              this.txtdgemail = temp;
              continue;
            }
            if (i == 9) {
              this.txttlgiamemail = temp;
              continue;
            }
            if (i == 10) {
              if (temp!='')
                this.dtpngiamemail = DateTimeLib.toDateDisplay(temp);
              continue;
            }
            if (i == 11) {
              this.txtthgiamemail = temp;
              continue;
            }
            if (i == 12) {
              this.txttlgiamip = temp;
              continue;
            }
            if (i == 13) {
              if (temp!='')
                this.dtpngiamip = DateTimeLib.toDateDisplay(temp);
              continue;
            }
            if (i == 14) {
              this.txtthgiamip = temp;
              continue;
            }
            if (i == 15) {
              this.txtcuocmb = temp;
              continue;
            }

            if (i == 16) {
              this.txtmbmphi = temp;
              continue;
            }
            if (i == 17) {
              this.txtcuocmphi = temp;
              continue;
            }
            if (i == 18) {
              this.txttiengiam = temp;
              continue;
            }
          }
        } // Nếu không có thông tin tính cước => đồng bộ null
        else {
          this.txtcuoctb = "";
          this.txtcuockh = "";
          this.txtcuoctg = "";
          this.txttlgiam = "";
          this.dtpngiam = "";
          this.txtthgiam = "";
          this.txtmienip = "";
          this.txtslemail = "";
          this.txtdgemail = "";
          this.txttlgiamemail = "";
          this.dtpngiamemail = "";
          this.txtthgiamemail = "";
          this.txttlgiamip = "";
          this.dtpngiamip = "";
          this.txtthgiamip = "";
          this.txtcuocmb = "";
          this.txtmbmphi = "";
          this.txtcuocmphi = "";
          this.txttiengiam = "";
        }
      } catch (ex) {
        console.log(ex)
        this.$root.toastError("Có lỗi: " + ex);
        return;
      }
    },
    KiemTraDuLieu_TTTC: async function () {
      if (this.txtcuoctb != "" && !this.CheckIsNumber(this.txtcuoctb)) {
        this.$root.toastError("Cước thuê bao phải là số !");
        return false;
      }
      if (this.txtcuockh != "" && !this.CheckIsNumber(this.txtcuockh)) {
        this.$root.toastError("Cước khoán phải là số !");
        return false;
      }
      if (this.txtcuoctg != "" && !this.CheckIsNumber(this.txtcuoctg)) {
        this.$root.toastError("Cước trọn gói phải là số !");
        return false;
      }
      if (this.txttlgiam != "" && !this.CheckIsNumber(this.txttlgiam)) {
        this.$root.toastError("Tỷ lệ giảm cước phải là số !");
        return false;
      }
      if (this.txtmienip != "" && !this.CheckIsNumber(this.txtmienip)) {
        this.$root.toastError("Số lượng IP miễn phí phải là số !");
        return false;
      }
      if (this.txtthgiam != "" && !this.CheckIsNumber(this.txtthgiam)) {
        this.$root.toastError("Thời gian miễn giảm phải là số !");
        return false;
      }
      return true;
    },
    ThayDoiHinhThucTT: async function (account, loaicuoc_id, htapdung) {     
      return await this.ThayDoiHTTT(account, loaicuoc_id, htapdung)
    },
    ThayDoiHTTT: async function (account,  loaicuoc_id,  tucthi) {
      return await vdc.modifyTariffType(account,  loaicuoc_id,  tucthi);
    },
    changeBillingInfo: async function (thongtin_tc) {
      this.billingInfo = "";
      if (thongtin_tc != "") {
        this.billingInfo = "";
        this.billingInfo += "cuoctb:" + this.txtcuoctb.trim() + ";"; //1 cuoctb
        this.billingInfo += "cuockh:" + this.txtcuockh.trim() + ";"; //2 cuockh
        this.billingInfo += "cuoctg:" + this.txtcuoctg.trim() + ";"; //3 cuoctg
        this.billingInfo += "tlgiam:" + this.txttlgiam.trim() + ";"; //4 tlgiam
        this.billingInfo += "ngiam:" + this.dtpngiam + ";"; //5 ngiam
        this.billingInfo += "thgiam:" + this.txtthgiam.trim() + ";"; //6 thgiam
        this.billingInfo += "mienip:" + this.txtmienip.trim() + ";"; //7 mienip
        this.billingInfo += "slemail:" + this.txtslemail.trim() + ";"; //8 slemail
        this.billingInfo += "dgemail:" + this.txtdgemail.trim() + ";"; //9 dgemail
        this.billingInfo += "tlgiamemail:" + this.txttlgiamemail.trim() + ";"; //10 tlgiamemail
        this.billingInfo += "ngiamemail:" + this.dtpngiamemail + ";"; //11 ngiamemail     dtp
        this.billingInfo += "thgiamemail:" + this.txtthgiamemail.trim() + ";"; //12 thgiamemail
        this.billingInfo += "tlgiamip:" + this.txttlgiamip.trim() + ";"; //13 tlgiamip
        this.billingInfo += "ngiamip:" + this.dtpngiamip + ";"; //14 ngiamip        dtp
        this.billingInfo += "thgiamip:" + this.txtthgiamip.trim() + ";"; //15 thgiamip
        this.billingInfo += "cuocmb:" + this.txtcuocmb.trim() + ";"; //16 cuocm
        this.billingInfo += "mbmphi:" + this.txtmbmphi.trim() + ";"; //17 mbmphi
        this.billingInfo += "cuocmphi:" + this.txtcuocmphi.trim() + ";"; //18 cuocmphi
        this.billingInfo += "tiengiam:" + this.txttiengiam.trim() + ";"; //19 tiengiam
      } else
        this.billingInfo = this.GetbillingInfo( "","", "","", "",  "", "", "",  "", "",  "", "",  "",  "",  "",  "",  "",  "", "");
        console.log('billingInfo',this.billingInfo)
        if (await  vdc.ChangeBillingInfo(this.account, this.billingInfo,"" + this.$root.token.getUserName() + " - Thay đổi TĐ")) {
        this.$root.toastSuccess("Thay đổi thông tin tính cước cho account " +this.account +" thành công !");
      } else {
        this.$root.toastError("Có lỗi kích hoạt thay đổi thông tin tính cước !");
        return;
      }
    },
    GetbillingInfo: function (vcuoc_tb, vcuockh,  vcuoctg, vtilegiam, vngaygiam, vtggiam, vmienip,
      vslemail, vdgemail, vtlgiamemail, vngiamemail, vthgiamemail, vtlgiamip, vngiamip, vthgiamip,
      vcuocmb, vmbmphi, vcuocmphi, vtiengiam) {
      let billingInfo = "";
      billingInfo += "cuoctb:" + vcuoc_tb.trim() + ";"; //1 cuoctb
      billingInfo += "cuockh:" + vcuockh.trim() + ";"; //2 cuockh
      billingInfo += "cuoctg:" + vcuoctg.trim() + ";"; //3 cuoctg
      billingInfo += "tlgiam:" + vtilegiam.trim() + ";"; //4 tlgiam
      billingInfo += "ngiam:" + vngaygiam.trim() + ";"; //5 ngiam
      billingInfo += "thgiam:" + vtggiam.trim() + ";"; //6 thgiam
      billingInfo += "mienip:" + vmienip.trim() + ";"; //7 mienip
      billingInfo += "slemail:" + vslemail.trim() + ";"; //8 slemail
      billingInfo += "dgemail:" + vdgemail.trim() + ";"; //9 dgemail
      billingInfo += "tlgiamemail:" + vtlgiamemail.trim() + ";"; //10 tlgiamemail
      billingInfo += "ngiamemail:" + vngiamemail.trim() + ";"; //11 ngiamemail     dtp
      billingInfo += "thgiamemail:" + vthgiamemail.trim() + ";"; //12 thgiamemail
      billingInfo += "tlgiamip:" + vtlgiamip.trim() + ";"; //13 tlgiamip
      billingInfo += "ngiamip:" + vngiamip.trim() + ";"; //14 ngiamip        dtp
      billingInfo += "thgiamip:" + vthgiamip.trim() + ";"; //15 thgiamip
      billingInfo += "cuocmb:" + vcuocmb.trim() + ";"; //16 cuocm
      billingInfo += "mbmphi:" + vmbmphi.trim() + ";"; //17 mbmphi
      billingInfo += "cuocmphi:" + vcuocmphi.trim() + ";"; //18 cuocmphi
      billingInfo += "tiengiam:" + vtiengiam.trim() + ";"; //19 tiengiam
      return billingInfo;
    },
    KICHHOAT_TOCDO_KHDN: async function () {
      try {
         console.log('KICHHOAT_TOCDO_KHDN')
        let loi = "";
        let obj= await this.KIEMTRA_TOCDO_KHDN(loi)
        console.log('obj',obj)
        if (!obj.kq) {
          return false;
        }
        loi = obj.loi;
        let vvbras_id = -1;
        let vvdslam_id = -1;
        console.log('loi',loi)
        let t =[];
         if(t!=null){
         t = loi.split("|");
         if (t[0] != "1" && t[0] != "2") 
         {
         return true;
         }
         }
        if (t.length > 1) {
          vvbras_id = parseInt(t[2]);
          vvdslam_id = parseInt(t[1]);
        } else {
          this.$root.toastError("Có lỗi khi kiểm tra tốc độ " + loi);
          return false;
        }
     
        if (!await this.DoiCongAcccountVisa(t[0], vvbras_id, vvdslam_id)){
          return false;
        }

        return true;
      } catch (ex) {
        console.log(ex)
        this.$root.toastError("Có lỗi khi kiểm tra tốc độ " + ex);     
        return false;
      }finally{
        this.$root.showLoading(false);
      }
    },
    kt_thaydoi_tocdo_khdn: async function ( hdtb_id, tocdo_cu_id, vthuebao_id,  tocdo_id, thamso) {
      try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/kt_thaydoi_tocdo_khdn",{
             hdtb_id:hdtb_id,
             tocdo_cu_id:tocdo_cu_id,
             thuebao_id:vthuebao_id,
             tocdo_id:tocdo_id,
             thamso:thamso
          }
        );
        return rs.data
      } catch (e) {
        console.log(e)
      } finally {
      }
    },
    DoiCongAcccountVisa:async function (kieu, vvbras_Id, vvdslam_id) {
      try
            {

                let dtBienDong = await this.lay_ds_biendong(this.hdtb_id)
                let obj = {}
                let row = dtBienDong[0];
                let  account = this.ChuanHoa_XML(row.ma_tb);
                let  password = row.password;//passwordMegaVNN;
                // let  status = "LOCK";  //ACTIVE 
                let  cusName = this.ChuanHoa_XML(row.ten_tb);
                let  cusAdd = this.ChuanHoa_XML(row.diachi_ld);
                let  phone = this.ChuanHoa_XML(row.matb_tn);
                let  port = row.port;
                let  vci = this.ChuanHoa_XML(row.vci);
                let  vpi = this.ChuanHoa_XML(row.vpi); ;
                let  slot = this.ChuanHoa_XML(row.slot);

                let  soHd = await this.checkMapId_new(row.hdkh_id,21) 
                if (soHd == "-1")
                {
                    soHd = "";
                }

                let  vv_brasId = "-1";
                let  vv_dslamId = "-1";
                if (kieu.toString() == "1" || kieu.toString() == "2")
                {
                    vv_brasId = vvbras_Id.toString();
                    vv_dslamId = vvdslam_id.toString();
                 await this.update_hdtb_adsl(this.hdtb_id,vv_dslamId,vv_brasId )
                }
                else
                {
                    vv_brasId = row.brid;
                    vv_dslamId = row.dslam_cha_id;
                }


                //Tham so them cho dang ky 7 tham so
                let  systemId = this.ChuanHoa_XML(row.system);
                let  rackId = this.ChuanHoa_XML(row.rack);
                let  shelfId = this.ChuanHoa_XML(row.shelf);

                //
                // let  status = "LOCK";  //ACTIVE 

                //let  brasId = cboBrasADSL.SelectedValue.ToString();
                let  brasId = await this.checkMapId_new(vv_brasId,22) 
                if (brasId == "-1")
                {
                    this.$root.toastError("Không tìm thấy Bras VDC. Liên hệ admin để xử lý !");
                    return false;
                }
                //let  dslamId = cboDSLAM.SelectedValue.ToString();
                let  dslamId = await this.checkMapId_new(vv_dslamId,23)  
                if (dslamId == "-1")
                {
                     this.$root.toastError("Không tìm thấy Dslam VDC. Liên hệ admin để xử lý !");
                    return false;
                }
                //Tham so them cho dang ky 7 tham so


                // Tham số thêm cho đăng ký 9 tham số
                let  self01 = row.selfid01;

                let  self02 = row.selfid02;
                let  self03 = row.selfid03;
                let  self04 = row.selfid04;


                //Kiem tra xem dang ky 4 tham so hay 7 tham so

                let thamso = await this.checkMapId_new(row.dslam_cha_id,24)   
                if (thamso == 4)
                {
                    let attributes=
                    [{type:'PORT',value:port},{type:'VCI',value:vci},{type:'VPI',value:vpi},{type:'SLOT',value:slot},
                    {type:'BRAS-ID',value:brasId},{type:'DSLAM-ID',value:dslamId},{type:'PARAMETER',value:'VISANEW'}]
                    
                    if (!await vdc.changePortMegaVNN(account, attributes))
                        return false;
                    else
                    {
                        this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                        return true;
                    }
                }
                else if (thamso == 7) //DoiCongMegavnn7
                {
                  let attributes=
                    [{type:'PORT',value:port},{type:'VCI',value:vci},{type:'VPI',value:vpi},{type:'SLOT',value:slot},
                    {type:'BRAS-ID',value:brasId},{type:'DSLAM-ID',value:dslamId},{type:'SYSTEMID',value:systemId},
                    {type:'RACKID',value:rackId},{type:'SELFID',value:shelfId},{type:'PARAMETER',value:'VISANEW'}]
                    if (! await vdc.changePortMegaVNN(account,attributes))
                        return false;
                    else
                    {
                        this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                        return true;
                    }
                }

                else if (thamso == 9) 
                {
                    if (this.ten_tat_ccbs == "HTH")
                    {
                        if (dslamId == "42537" || dslamId == "40701") //DoiCongMegavnn9_FE
                        {

                          let attributes=
                    [{type:'PORT',value:port},{type:'VLAN_ID',value:vci},{type:'AXCI',value:vpi},{type:'SLOT',value:slot},{type:'BRAS-ID',value:brasId},
                    {type:'DSLAM-ID',value:dslamId},{type:'SYSTEMID',value:systemId},{type:'SELFID01',value:self01},{type:'SELFID02',value:self02},
                    {type:'SELFID03',value:self03},{type:'SELFID04',value:self04},{type:'PARAMETER',value:'VISANEW'}]
                          if (!await vdc.changePortMegaVNN(account,attributes))
                                return false;
                            else
                            {
                                this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                                return true;
                            }
                        }
                        else   //DoiCongMegavnn9_ADSL
                        {
                           let attributes=    
                    [{type:'PORT',value:port},{type:'VLAN_ID',value:vci},{type:'AXCI',value:vpi},{type:'SLOT',value:slot},{type:'BRAS-ID',value:brasId},
                    {type:'DSLAM-ID',value:dslamId},{type:'SYSTEMID',value:systemId},{type:'SELFID',value:self01},{type:'OLT_SLOT',value:self02},
                    {type:'OLT_PORT',value:self03},{type:'ONUID',value:self04},{type:'PARAMETER',value:'VISANEW'}]
                           if (!await vdc.changePortMegaVNN(account, attributes))
                                return false;
                            else
                            {
                                this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                                return true;
                            }
                        }
                    }
                    else if (this.ten_tat_ccbs  == "QBH")
                    {
                        if (dslamId == "4" || dslamId == "9") //DoiCongMegavnn9_FE
                        {
                               let attributes=
                    [{type:'PORT',value:port},{type:'VLAN_ID',value:vci},{type:'AXCI',value:vpi},{type:'SLOT',value:slot},{type:'BRAS-ID',value:brasId},
                    {type:'DSLAM-ID',value:dslamId},{type:'SYSTEMID',value:systemId},{type:'SELFID01',value:self01},{type:'SELFID02',value:self02},
                    {type:'SELFID03',value:self03},{type:'SELFID04',value:self04},{type:'PARAMETER',value:'VISANEW'}]
                           if (!await vdc.changePortMegaVNN(account,attributes))
                                return false;
                            else
                            {
                                this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                                return true;
                            }
                        }
                        else   //DoiCongMegavnn9_ADSL
                        {
                          let attributes=    
                    [{type:'PORT',value:port},{type:'VLAN_ID',value:vci},{type:'AXCI',value:vpi},{type:'SLOT',value:slot},{type:'BRAS-ID',value:brasId},
                    {type:'DSLAM-ID',value:dslamId},{type:'SYSTEMID',value:systemId},{type:'SELFID',value:self01},{type:'OLT_SLOT',value:self02},
                    {type:'OLT_PORT',value:self03},{type:'ONUID',value:self04},{type:'PARAMETER',value:'VISANEW'}]
                          if (!await vdc.changePortMegaVNN(account, attributes))
                                return false;
                            else
                            {
                                this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                                return true;
                            }
                        }
                    }
                    else   //DoiCongMegavnn9_ADSL
                    {
                       let attributes=    
                    [{type:'PORT',value:port},{type:'VLAN_ID',value:vci},{type:'AXCI',value:vpi},{type:'SLOT',value:slot},{type:'BRAS-ID',value:brasId},
                    {type:'DSLAM-ID',value:dslamId},{type:'SYSTEMID',value:systemId},{type:'SELFID',value:self01},{type:'OLT_SLOT',value:self02},
                    {type:'OLT_PORT',value:self03},{type:'ONUID',value:self04},{type:'PARAMETER',value:'VISANEW'}]
                       if (!await vdc.changePortMegaVNN(account, attributes))
                            return false;
                        else
                        {
                            this.$root.toastSuccess("Đổi cổng Account " + account + " trên Visa thành công !");
                            return true;
                        }
                    }
                }
                return true;
            }
            catch ( ex)
            {
                console.log(ex)
                this.$root.toastError("Có lỗi xảy ra trong quá trình đổi cổng account trên Visa.");
                return false;
            }
    },

    KIEMTRA_TOCDO_KHDN: async function (loi) {
      loi = "";

      try {
        let thamso = -1;
        let tocdo_id = parseInt(this.currentItem.tocdo_id);
        let tocdo_cu_id = parseInt(this.currentItem.tdcu_id);
        let dt = [...this.dsThueBao];
        let t = dt.filter(x=>x.hdtb_id== this.hdtb_id)
        let vthuebao_id = -1;
        if (t.length > 0) vthuebao_id = parseInt(t[0].thuebao_id);
        let kt = await this.kt_thaydoi_tocdo_khdn( this.hdtb_id, tocdo_cu_id,  vthuebao_id,  tocdo_id, thamso);
        console.log('kt',kt)
        if (kt.includes("ERROR")) {
          this.$root.toastError("Có lỗi " + kt);
          return {loi:loi,kq:false}
        }
        loi = kt;
        return {loi:loi,kq:true};
      } catch (ex) {
        this.$root.toastError("Có lỗi khi kiểm tra tốc độ " + ex);
        return  {loi:loi,kq:true};
      }
    },
    update_hdtb_adsl: async function (hdtb_id,dslam_id,bras_id){
     try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/update_hdtb_adsl",{
             hdtb_id:hdtb_id,
             dslam_id:dslam_id,
             bras_id:bras_id
          }
        );
        return rs.data
      } catch (error) {
      } finally {
      }
    },
    lay_ds_biendong: async function (hdtb_id){
     try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_biendong/"+hdtb_id
        );
        return rs.data
      } catch (error) {
      } finally {
      }
    },
    DoiTocDoADSL: async function ( account, tocdo_id, ip, htapdung, autoIP, giuIP) {
     return await vdc.ThaydoiGoiCuoc(account, tocdo_id, ip, htapdung, autoIP, giuIP)      
    },
    UPDATE_IP_ADSL: async function (hdtb_id, ip) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/update_ip_adsl",{
            hdtb_id:hdtb_id,
            ip:ip
            }
        );
        return rs.data
      } catch (error) {
      } finally {
      }
    },
    GetPackageEPG:async function (ma_tb) {
      return  await vasc.GetPackageEPG(ma_tb,this.$root.context.user.getProperty('ngay_epg'));
    },
    clearForm: function () {
      this.countRow = 0
      this.thongTinKH = {
        ma_gd: "",
        ma_kh: "",
        ten_kh: "",
        diachi_kh: "",
        ghichu: "",
        ngay_yc: DateTimeLib.toDateDisplay( new Date())
      };
      this.account =""
      this.txtAccountCu =""
      this.password = ""
      this.diachi_ip = ""
      this.txtSLIP = ""
    },
    ChuanHoa_XML: function (str) {
      if(str == null)
      return ""
      let result = "";
      result = str.toString().replace("&", "&amp");
      result = result.replace("<", "&lt");
      result = result.replace(">", "&gt");
      result = result.replace("'", "&apos");
      result = result.replace(String.fromCharCode(34), "&quot");
      return result;
    },
    //#endregion
    //#region Hàm con Hoàn Công
    LAY_DS_THAMSO_MD_MATS: async function (ma_ts) {
       try {
        let rs = await this.$root.context.post(
          "/web-ccdv/khaibaotsl/lay_ds_thamso_md_mats",{
            ma_ts:ma_ts
          }
        );
        return rs.data
      } catch (error) {
      } finally {
      }
    },
    LAY_GOI_DIDONG: async function (thuebao_id) {
       try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_goi_didong/"+thuebao_id
        );
        return rs.data
      } catch (error) {
      } finally {
      }
    },
    CAPNHAT_TT_GOI: async function(MaTinh,  goi_id_neo,  matb_dd,  type, note){
        try {
        let rs = await this.$root.context.post("/ccbs/gdvp/update-pac-data",{
                        accadd: "",
                        accid: "",
                        accname: "",
                        account: goi_id_neo,
                        agent: MaTinh,
                        channel: "",
                        msisdn: matb_dd,
                        note: note,
                        request_id: "",
                        service: "",
                        type: type,
                        userid: "",
                        userip: ""
                    });
        return rs.data
      } catch (error) {
         return '0'
      } finally {
      }
    },
    KT_TaoDB_Thang: async function(ngay_cn){
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
            
         return  await this.demKyHoaDon(vkyhoadon)
    },
    demKyHoaDon: async function(vkyhoadon){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/ktra_taodb_thang",
          {kyhoadon:vkyhoadon}
        );
        if (rs.data != null) {
        return rs.data[0].kq
        }
      } catch (error) {
        return 0
      } 
    },
    KT_DBThang_DaChot: async function(){
      let date = new Date();
    let thangsau = this.addMonths(1);//DateTimeLib.toDateDisplayWithFormat(date.addMonths(1),'yyyyMM')
     let kq = "";
            let kt_taobang = await this.KT_TaoDB_Thang(thangsau);
            if (kt_taobang != 0)
            {
                kq = "Danh bạ tháng đã được chốt, bạn không thể sửa ngày hoàn thành. Liên hệ Admin để xử lý !";
            }
            else
            {
                kq = "0";
            }
            return kq;
    },
    KIEMTRA_GIAO_OMC: async function (hdtb_id,index_loaitb_id){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/kiemtra_giao_omc",
          {
            hdtb_id: hdtb_id,
            loaihinh_tb: index_loaitb_id,
          }
        );
        return rs.data;
      } catch (error) {
        return null
      } 
    },
    GIAOPHIEU_TIEPTHEO: async function ( hdtb_id,phieu_id,huonggiao_id,kieu){
     try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/giaophieu_donvi_tieptheo",
          {
            hdtb_id: hdtb_id,
            phieu_id: phieu_id,
            huonggiao_id:huonggiao_id,
            kieu:kieu
          }
        );
        return rs.data;
      } catch (error) {
        return null
      } 
    },
    CAPNHAT_NGAYHT: async function(hdtb_id){
      let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/update_ngay_ht/"+hdtb_id       
        );
    },
    CAPNHAT_NGAYKH: async function(hdtb_id){
       let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/update_ngay_kh/"+hdtb_id       
        );
    },
    HOANTHANH_HOANCONG: async function(hdtb_id, phieu_id, tthd_dich_id){
       let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/hoanthanh_hoancong",{
            hdtb_id:hdtb_id,
            phieu_id:phieu_id,
            tthd_dich_id:tthd_dich_id
          }    
        );
    },
    CapNhat_TT_Phieu_HTHS: async function (hdtb_id){
       let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/capnhat_tt_phieu_hths/"+hdtb_id       
        );
    },
    CN_NGAY_BD_NGAY_HUY: async function (hdtb_id){
        try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/cn_ngay_bd_ngay_huy/"+hdtb_id         
        );
        return rs.data;
      } catch (error) {
        return null
      } 
    },
    //#endregion
    HoanCong: async function () {
      try {
        let mes = "";
        this.$root.showLoading(true)
        //#region Hợp đồng thay đổi dịch vụ
        if (this.loaihd_id == Enum.LoaiHopDong.THAY_DOI_DV)
        {
            if (this.PHAILAM("HOANCONG"))
            {
                let dsphieu =[];
                let hg_phieu_id = 0;
                let donvi_nhan_id = 0;
                let tthd_dich_id = 0;
                dsphieu = await this.Lay_DonVi_GiaoPhieu(this.huonggiao_id, this.hdtb_id)
                if (dsphieu !=null && dsphieu.length > 0)
                {
                    hg_phieu_id = parseInt(dsphieu[0].huonggiao_id.toString())
                    donvi_nhan_id = parseInt(dsphieu[0].donvi_id.toString())
                    tthd_dich_id = parseInt(dsphieu[0].tthd_id.toString())
                }
                else
                {
                    this.$root.toastError("Chưa thiết lập đơn vị nhận phiếu. Liên hệ admin để xử lý !")
                    return;
                }

                //dungpa them - kich hoat khoa mo goi tren VNP
                //#region
                let dsTS = await this.LAY_DS_THAMSO_MD_MATS("DONGBO_GOI_DA_DV")
                let dongbo_dadv = -1
                if (dsTS != null && dsTS.length > 0)
                {
                    dongbo_dadv = parseInt(dsTS[0].ten_ts.toString());
                }
                if (dongbo_dadv == 1)
                {
                    let _trangthai = await this.lay_kieu_yc(this.hdtb_id,Enum.DichVuGiaTang.TAMDUNG_INTERNET
                                                                    +','+Enum.DichVuGiaTang.KHOACUOC_INTERNET)
                    if (_trangthai != "-1")
                    {
                        _trangthai = await this.lay_kieu_yc(this.hdtb_id,Enum.DichVuGiaTang.TAMDUNG_INTERNET) 
                        if (_trangthai == "1")
                        {
                            _trangthai = "2";
                        }
                        else
                        {
                            _trangthai = await this.lay_kieu_yc(this.hdtb_id,Enum.DichVuGiaTang.KHOACUOC_INTERNET)   
                            if (_trangthai == "1") _trangthai = "3";
                            else
                            {
                                _trangthai = "1";
                            }
                        }

                        let vthuebao_id = await this.checkMapId_new(this.hdtb_id,8) 
                        let _dtGoiDD = await this.LAY_GOI_DIDONG(vthuebao_id)
                        if (_dtGoiDD != null && _dtGoiDD.length > 0)
                        {

                            let _error = "";
                            //gdvp/update-pac-data
                            if (await this.CAPNHAT_TT_GOI(this.ten_tat_ccbs, _dtGoiDD[0].goi_neo_id,
                                                       "84" + _dtGoiDD[0].ma_tb, _trangthai, _error) != "0")
                            {
                                this.$root.toastError("Có lỗi trong quá trình kích hoạt gói tích hợp trên VNP: ");
                                return;
                            }
                        }
                    }

                }
                //#endregion
                //end dungpa

                if (this.PHAILAM("TUDONG_HTHS"))
                {
                    let kt_taobang = await this.KT_TaoDB_Thang(DateTimeLib.toDateDisplayWithFormat(new Date(),'yyyyMM'));
                    if (kt_taobang == 0)
                    {
                        this.$root.toastError("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !");
                        return;
                    }
                }

                if (this.PHAILAM("CAPNHAT_NGAYHT_HC"))
                {
                    console.log('CAPNHAT_NGAYHT_HC')
                   // let thangsau = this.addMonths(1)//(new Date()).addMonths(1).toString("yyyyMM");
                    let dbt = await this.KT_DBThang_DaChot()
                    if (dbt != "0")
                    {
                        this.$root.toastError(dbt);
                        return;
                    }
                    //Cập nhật ngày hoàn thành
                    await this.CAPNHAT_NGAYHT(this.hdtb_id)
                    //
                }
                if (this.PHAILAM("CAPNHAT_NGAYKH_HC"))
                {
                    //Cập nhật Ngày Kích hoạt
                  await  this.CAPNHAT_NGAYKH(this.hdtb_id)
                }

               await this.HOANTHANH_HOANCONG(this.hdtb_id, this.phieu_id, tthd_dich_id)

                if (this.PHAILAM("TUDONG_HTHS"))
                {

                    await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id);
                    await this.HoanThienHoSo_DV(this.hdtb_id);

                    await this.CapNhat_TT_Phieu_HTHS(this.hdtb_id);
                    if (this.PHAILAM("TUDONG_THUHOI_KHI_HOANCONG"))
                    {
                        await this.GIAOPHIEU_TD_HOST_THUHOI_1(this.hdtb_id, this.phieu_id);
                    }

                    //#region Kiểm tra hủy gói gia đình với mytv
                    try
                    {
                        if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV)
                        {
                            let  dsKtra = await this.Kiemtra_huygoi_mytv_tddv(this.hdtb_id);

                            if (dsKtra != null && dsKtra.length > 0)
                            {
                                if (this.getValueFromIndex(dsKtra[0],0) == "1")
                                {
                                    let kt = 0;

                                    //#region Tạo hợp đồng xóa thuê bao khỏi gói gia đình
                                    let goi_id = parseInt(this.getValueFromIndex(dsKtra[0],1));
                                    let nhomtb_id = parseInt(this.getValueFromIndex(dsKtra[0],2));

                                     await this.TaoDuLieu_huygoi_mytv(goi_id, nhomtb_id, Enum.KieuLapDat.XOA_TB_GOI_DADV);
                                    //#endregion

                                   //#region Kích hoạt vasc
                                    let vtocdo_id = -1;
                                    let vmuccuoc_id = 0;
                                    let kichhoat = true;
                                    vtocdo_id = await this.checkMapId_new(thuebao_id,18) 
                                    vmuccuoc_id = await this.checkMapId_new(thuebao_id,19)

                                    //Lấy ra mã tốc độ VASC
                                    let matocdo_vasc = await this.checkMapId_new(thuebao_id,20) 
                                    if (matocdo_vasc == "-1")
                                    {
                                        this.$root.toastError("Thông tin về tốc độ chưa được đồng bộ lên VASC. Bạn hãy kiểm tra lại !");
                                        return;
                                    }
                                    if (await this.KichHoatVASC(this.account, matocdo_vasc, "0", "", ""))
                                        await this.CAPNHAT_STATUS_HDTB(this.hdtb_id_huygoi_mytv, Enum.TRANGTHAI_DONGBO.DONGBO_CM);
                                    else
                                    {
                                        this.$root.toastError("Có lỗi trong quá trình kích hoạt gói tích hợp trên VASC");
                                        kichhoat = false;
                                    }
                                    //#endregion

                                   // #region Hoàn thiện hồ sơ thay đổi gói
                                    if (kichhoat)
                                    {
                                        await this.HOANTHIEN_HS_TD_GOI_DADV(this.hdkh_id_huygoi_mytv);
                                    }
                                   // #endregion
                                }
                            }
                        }
                    }
                    catch (e)
                    {
                      console.log(e)
                    }
                    //#endregion

                    if (this.PHAILAM("KT_HOANCONG_HD_BANCHEO"))
                    {
                        try
                        {
                            await this.hoangcong_hopdong_bancheo(this.hdtb_id);
                        }
                        catch ( ex)
                        {
                           console.log(ex)
                         // await this.Log_Action(this.hdtb_id,ex)
                        }
                    }

                    mes = "Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !";
                }
                else
                {
                    await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id);
                    mes = "Hoàn công thành công !";
                }                
                this.$root.toastSuccess(mes);
            }
        }
        //#endregion
      
        //#region Đổi số
                if (parseInt(this.loaihd_id) == Enum.LoaiHopDong.DOISO_TB)
                {
                    // hoangpkn : 19/07/2016 : Sua lai hoan cong Doi Account
                    //Kiểm tra nếu bộ phận giao dịch viên được phép hoàn công, hoàn thiện hồ sơ không?
                    if (this.PHAILAM("HOANCONG"))
                    {
                        if (this.PHAILAM("TUDONG_HTHS"))
                        {
                            let kt_taobang = await this.KT_TaoDB_Thang(DateTimeLib.toDateDisplayWithFormat(new Date(),'yyyyMM'));
                            if (kt_taobang == 0)
                            {
                               this.$root.toastError("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !");
                                return;
                            }
                        }

                        let dsphieu = []
                        let hg_phieu_id = 0;
                        let donvi_nhan_id = 0;
                        let tthd_dich_id = 0;
                        dsphieu = await this.Lay_DonVi_GiaoPhieu(this.huonggiao_id, this.hdtb_id);
                        if (dsphieu !=null && dsphieu.length > 0)
                        {
                            hg_phieu_id = parseInt(dsphieu[0].huonggiao_id);
                            donvi_nhan_id = parseInt(dsphieu[0].donvi_id);
                            tthd_dich_id = parseInt(dsphieu[0].tthd_id);
                        }
                        else
                        {
                            this.$root.toastError("Chưa thiết lập đơn vị nhận phiếu. Liên hệ admin để xử lý !");
                            return;
                        }

                        if (!await this.KiemTra_Phieu_DaGiao(this.hdtb_id, hg_phieu_id))
                        {
                            this.$root.toastError("Thuê bao " + this.account + " đã hoàn công rồi. Bạn hãy kiểm tra lại");
                        }
                        else
                        {
                            if (this.PHAILAM("CAPNHAT_NGAYHT_HC"))
                            {
                                // let thangsau = this.addMonths(1);//(new Date()).addMonths(1).toString("yyyyMM");
                                let dbt = await this.KT_DBThang_DaChot();
                                if (dbt != "0")
                                {
                                    this.$root.toastError(dbt);
                                    return;
                                }
                                await this.CAPNHAT_NGAYHT(this.hdtb_id);
                            }
                            if (this.PHAILAM("CAPNHAT_NGAYKH_HC"))
                                await this.CAPNHAT_NGAYKH_STATUS_V2(this.hdtb_id, Enum.TRANGTHAI_DONGBO.DONGBO_CM);

                            await this.CapNhat_TT_Phieu_HC(this.hdtb_id, Enum.TRANGTHAI_PH.DAGIAO);
                            await this.HOANTHANH_HOANCONG(this.hdtb_id, this.phieu_id, tthd_dich_id);

                            let str = "Cập nhật dữ liệu thành công !";
                            if (this.PHAILAM("TUDONG_HTHS"))
                            {
                                await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id);
                                await this.HOANTHIEN_HS_THAYDOI_MATB(this.hdtb_id);
                                await this.CapNhat_TT_Phieu_HTHS(this.hdtb_id);
                                str = "Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !";
                            }
                            else
                            {
                                await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id);
                            }

                            //dungpa them - kich hoat đổi account fiber có đăng ký goi GD tren VNP
                            //#region
                            let dsTS = await this.LAY_DS_THAMSO_MD_MATS("DONGBO_GOI_DA_DV");
                            let dongbo_dadv = -1;
                            if (dsTS != null && dsTS.length > 0)
                            {
                                dongbo_dadv = parseInt(dsTS[0].ten_ts);
                            }
                            if (dongbo_dadv == 1)
                            {
                                //dungpa - chua test dc, tam zem day ban moi
                                let  vthuebao_id = await this.checkMapId_new(this.hdtb_id,8) 

                                  _dtGoiDD = await this.LAY_GOI_DIDONG(vthuebao_id)

                                if (_dtGoiDD != null && _dtGoiDD.length > 0)
                                {
                                   let _dtTB = await this.LAY_THONGTIN_DBTHUEBAO(vthuebao_id);

                                    let vso_gt = "";
                                    let vma_tb = "";
                                    let vdiachi_tb = "";
                                    if (_dtTB != null)
                                    {
                                        vso_gt = _dtTB.so_gt == null ? "" : _dtTB.so_gt;
                                        vma_tb = _dtTB.ma_tb;
                                        vdiachi_tb = _dtTB.diachi_tb;
                                    }

                                    let _error = "";
                                    let _result = await this.DOI_ACC_CCBS(this.ten_tat_ccbs, _dtGoiDD[0].goi_neo_id, "84" + _dtGoiDD[0].ma_tb,
                                                             vma_tb, vdiachi_tb, vso_gt, "Đổi account fiber", this.$root.token.getUserName());
                                    if (_result != "0")
                                    {
                                        this.$root.toastError("có lỗi trong quá trình kích hoạt gói tích hợp trên vnp: " + _result);
                                        return;
                                    }
                                }

                            }
                            //#endregion
                            //end dungpa
                           this.$root.toastSuccess(str);

                        }

                       
                        // end hoang
                    }
                }
        //#endregion

        //#region "Thay đổi tốc độ Internet"
                if (parseInt(this.loaihd_id) == Enum.LoaiHopDong.THAY_DOI_TOCDO_ADSL)
                {
                    let ngay_sys;
                    let ngay_tt;
                    let ngay_ht;
                    //let nIndex = //dgvThueBao.CurrentRow.Index;
                    ngay_sys = DateTimeLib.toDateDisplay(new Date())
                    ngay_tt = this.currentItem.ngay_tt;

                    if (! this.KiemTraDK_HoanThanh_1(ngay_tt, ngay_sys))//Nếu ngày thanh toán > ngày hoàn thành
                    {
                        this.$root.toastError("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");
                        return;
                    }

                    try
                    {
                        let mss = await this.Kiemtra_yc_giaophieu_doitocdo(this.hdtb_id);
                        if (mss.toUpperCase() != "OK")
                        {
                            this.$root.toastError(this.account + ": " + mss);
                            return;
                        }
                    }
                    catch (e)
                    {
                       console.log(e)
                    }

                    if (this.PHAILAM("TUDONG_HTHS"))
                    {
                  
                        if (parseInt(this.currentItem.tucthi) == 0 && this.currentItem.clngay_kh != null)
                        {
                            if (DateTimeLib.toDateDisplayWithFormat(this.currentItem.clngay_kh,'MM/yyyy') == DateTimeLib.toDateDisplayWithFormat(new Date(),'MM/yyyy'))
                            {
                                this.$root.toastError("Không được hoàn công và hoàn thiện trong tháng " + 
                                DateTimeLib.toDateDisplayWithFormat(this.currentItem.clngay_kh,'MM/yyyy') + " !");
                                return;
                            }
                        }
                        //end hoang them
                        let kt_taobang = await this.KT_TaoDB_Thang(DateTimeLib.toDateDisplayWithFormat(new Date(),'yyyyMM'));
                        if (kt_taobang == 0)
                        {
                            this.$root.toastError("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !");
                            return;
                        }
            
                    }

                    let dsphieu = []
                    let hg_phieu_id = 0;
                    let donvi_nhan_id = 0;
                    let tthd_dich_id = 0;
                    if (this.PHAILAM("GIAOPHIEU"))
                        dsphieu = await this.Lay_DonVi_GiaoPhieu_HTHS(this.huonggiao_id, this.hdtb_id);
                    else
                        dsphieu = await this.Lay_DonVi_GiaoPhieu(this.huonggiao_id, this.hdtb_id);
                    console.log('GIAOPHIEU',dsphieu)
                    if (dsphieu!= null && dsphieu.length > 0)
                    {
                        hg_phieu_id = parseInt(dsphieu[0].huonggiao_id);
                        donvi_nhan_id = parseInt(dsphieu[0].donvi_id);
                        tthd_dich_id = parseInt(dsphieu[0].tthd_id);
                    }
                    else
                    {
                        this.$root.toastError("Chưa thiết lập đơn vị nhận phiếu. Liên hệ admin để xử lý !");
                        return;
                    }

                    //Kiểm tra thuê bao trước khi hoàn công xem thuê bao này đã được hoàn công hay chưa
                    //Tránh trường hợp 2 người cùng hoàn công
                    if (!await this.KiemTra_Phieu_DaGiao(this.hdtb_id, hg_phieu_id))
                    {
                        this.$root.toastError("Thuê bao " + this.account + " đã được giao đi. Bạn hãy kiểm tra lại");
                        return;
                    }


                    if (parseInt(this.currentItem.tucthi) == 0)
                    {
                        await this.CAPNHAT_NGAYHT(this.hdtb_id);
                        //Nếu là thay đổi tháng sau, thì sẽ cập nhật lại thông tin tính cước lên visa
                        let tttc = "";
                        tttc = await this.checkMapId_new(this.hdtb_id,10) 
                        if (tttc == "-1")
                            tttc = "";
                        if (tttc != "")
                        {
                            await this.LayDuLieu_TTTC(tttc);
                            if (!await this.KiemTraDuLieu_TTTC())
                                return;
                        }
                        await this.changeBillingInfo(tttc);
                    }

                    await this.CapNhat_TT_Phieu_HC(this.hdtb_id, Enum.TRANGTHAI_PH.DAGIAO);
                    await this.HOANTHANH_HOANCONG(this.hdtb_id, this.phieu_id, tthd_dich_id);

                    let str = "";
                    if (this.PHAILAM("TUDONG_HTHS"))
                    {
                        await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id);
                        await this.HoanThienHoSo_TocDo(this.hdtb_id);
                        await this.CapNhat_TT_Phieu_HTHS(this.hdtb_id);

                        if (this.PHAILAM("KT_HOANCONG_HD_BANCHEO"))
                        {
                            try
                            {
                                 await this.hoangcong_hopdong_bancheo(this.hdtb_id);
                            }
                            catch ( ex)
                            {
                              //await this.Log_Action(this.hdtb_id,ex)                              
                            }
                        }

                        str = "Hoàn công và hoàn thiện hồ sơ thành công !";
                    }
                    else
                    {
                        await this.GIAOPHIEU_TD_KHI_HC(this.hdtb_id, this.phieu_id, hg_phieu_id);
                        str = "Hoàn công thành công !";
                    }

                    this.$root.toastSuccess(str);
                    await this.CN_NGAY_BD_NGAY_HUY(this.hdtb_id)
                }

      // #endregion

       //#region Thanh ly
              if (this.loaihd_id == Enum.LoaiHopDong.CHAMDUT_SD)
              {
                  this.str_hoancong = "Cập nhật dữ liệu thành công!";
                  let str = "" ;
                  if (this.PHAILAM("HOANCONG"))
                  {
                        if (this.PHAILAM("TUDONG_HTHS"))
                        {
                            let kt_taobang = await this.KT_TaoDB_Thang(DateTimeLib.toDateDisplayWithFormat(new Date(),'yyyyMM'));
                            if (kt_taobang == 0)
                            {
                                this.$root.toastError("Danh bạ tháng chưa được chốt. Liên hệ Admin xử lý trước khi thực hiện !");
                                return;
                            }
                        }

                        if (this.PHAILAM("CAPNHAT_NGAYHT_HC"))//Kiểm tra trc khi chạy for hoàn công
                        {

                            let dbt = await this.KT_DBThang_DaChot();
                            if (dbt != "0")
                            {
                                this.$root.toastSuccess(dbt);
                                return;
                            }
                        }
                        console.log('1')
                        let ds = this.$refs['grid-thuebao'].$refs.grid.getSelectedRecords()
                        for (let i = 0; i <ds.length; i++)
                        {
                                let item  = ds[i];
                                let in_loaitb_id = item.loaitb_id
                                let in_hdtb_id = item.hdtb_id
                                let account = item.ma_tb
                                let in_phieu_id = item.phieu_id
                                let in_thuebao_id = item.thuebao_id

                                this.index_loaitb_id = in_loaitb_id;
                                this.index_hdtb_id = in_hdtb_id;
                                this.index_account = account;
                                this.index_thuebao_id = in_thuebao_id;

                                //#region Kiểm tra luồng nếu phải giao OMC thì giao không thì thôi
                                this.hoancong_ngay = false;
                                if (this.PHAILAM("CHECK_OMC")) // Nếu có thao tác kiểm tra OMC thì -> làm
                                {
                                    try
                                    {
                                        let kq = await this.KIEMTRA_GIAO_OMC(vhdtb_id,index_loaitb_id)
                                        console.log('KIEMTRA_GIAO_OMC',kq);
                                        if (kq == null)
                                        {
                                            continue;
                                        }
                                        else if (kq.toString() == "2")// bo qua
                                        {
                                            this.hoancong_ngay = true;
                                            await this.xuLyHoanCongNgay(in_hdtb_id,in_phieu_id)
                                            continue;
                                        }
                                        else if (kq.toString() == "1")
                                        {
                                    
                                            //Giaophieu                                          
                                            let kq_giao = await this.GIAOPHIEU_TIEPTHEO(in_hdtb_id, in_phieu_id,this.huonggiao_id, 2);
                                            if (kq_giao == "1")
                                            {
                                                continue;
                                            }
                                            else
                                            {
                                                continue;
                                            }
                                        }
                                        else
                                        {
                                            continue;
                                        }
                                    }
                                    catch (ex)
                                    {
                                       console.log(ex)
                                       continue;
                                    }
                                }
                                this.xuLyHoanCongNgay(in_hdtb_id,in_phieu_id)
                            //#endregion                             
                            }                       
                    }
                   this.$root.toastSuccess(this.str_hoancong);
              }
      // #endregion
       await this.HienThiDanhSachTB();

      } catch (e) {
        console.log(e);
        this.$root.toastError("Có lỗi xảy ra trong quá trình hoàn công");
      } finally {
        this.$root.showLoading(false);
      }
    },
    DOI_ACC_CCBS: async function (MaTinh,  matb_dd,  account_fiber,  accname,  accadd,  accid,  note,  userid){
       try {
        let rs = await this.$root.context.post(
          "/ccbs/gdvp/update-acc-fiber",{
                     "accadd": accadd,
                      "accid": accid,
                      "accname": accname,
                      "account": account_fiber,
                      "agent": MaTinh,
                      "channel": "",
                      "msisdn": matb_dd,
                      "note": note,
                      "request_id": "",
                      "service": "",
                      "type": "",
                      "userid": userid,
                      "userip": ""
                     });
        return rs.data
      } catch (e) {
        console.log(e)
        return null;
      } 
    },
    lay_kieu_yc: async function(hdtb_id,idsDichvu){
        try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_kieu_yc",{
            hdtb_id:hdtb_id,
            idsDichvu:idsDichvu
          }
        );
        return rs.data
      } catch (error) {
        return null
      } 
    },
    KichHoatVASC: async function( MyTVAcc,  matocdo_mytv,  ma_goi_tichhop,  lydo,  vCombo_fiberCD)
        {
            let status = false;
            try
            {
                let IPTVAccount = this.ChuanHoa_XML(MyTVAcc);
                let packageNo = this.ChuanHoa_XML(matocdo_mytv);
                if (packageNo == "")
                {
                    this.$root.toastError("Gói dịch vụ MyTV này chưa được đồng bộ với VASC. Bạn không thể tiếp tục cập nhật dữ liệu.");
                    return false;
                }
                let serviceCode = "";
                let serviceVASCode = "";
                let megaMyTV = this.ChuanHoa_XML(ma_goi_tichhop);
                let reason = this.ChuanHoa_XML(lydo);
                status = (await vasc.ChangePackageAndMegaVNN(IPTVAccount, packageNo, serviceCode, serviceVASCode, "0", reason, vCombo_fiberCD) == 0);
                return status;
            }
            catch ( ex)
            {
                console.log(ex)                             
                this.$root.toastError("Có lỗi xảy ra");
                return status; 
            }         
    },
    xuLyHoanCongNgay: async function (in_hdtb_id,in_phieu_id){
                                let dsphieu = []
                                let hg_phieu_id = 0;
                                let donvi_nhan_id = 0;
                                let tthd_dich_id = 0;
                                let ttnd =  await this.lay_thongtin_nguoidung()
                                if (this.hoancong_ngay)
                                {
                                    dsphieu = await this.Lay_DonVi_GiaoPhieu_HTHS(this.huonggiao_id, this.hdtb_id);
                                }
                                else
                                {
                                    dsphieu = await this.Lay_DonVi_GiaoPhieu(this.huonggiao_id, in_hdtb_id);
                                }
                                if (dsphieu != null && dsphieu.length > 0)
                                {
                                    hg_phieu_id = dsphieu[0].huonggiao_id
                                    donvi_nhan_id = dsphieu[0].donvi_id
                                    tthd_dich_id = dsphieu[0].tthd_id
                                }
                                else
                                {
                                    this.$root.toastError("Chưa thiết lập đơn vị nhận phiếu. Liên hệ admin để xử lý !");
                                   return
                                }

                                if (!await this.KiemTra_Phieu_DaGiao(in_hdtb_id, hg_phieu_id))
                                {
                                    this.$root.toastError("Thuê bao " + this.account + " đã hoàn công rồi. Bạn hãy kiểm tra lại");
                                    return;
                                }
                                else
                                {
                                    if (this.PHAILAM("CAPNHAT_NGAYHT_HC"))
                                    {
                                        
                                        await this.CAPNHAT_NGAYHT(in_hdtb_id);
                                    }
                                    if (this.PHAILAM("CAPNHAT_NGAYKH_HC"))
                                        await this.CAPNHAT_NGAYKH_STATUS_V2(in_hdtb_id);

                                    await this.CapNhat_TT_Phieu_HC(in_hdtb_id, Enum.TRANGTHAI_PH.DAGIAO);


                                    let vthuebao_id = await this.checkMapId_new (in_hdtb_id,8)

                                    if (this.PHAILAM("TUDONG_HTHS"))
                                    {
                                        //#region Cập nhật lại port khi hoàn công thanh lý
                                        let dsadsl =[]
                                        if (this.dichvuvt_id == Enum.DichVuVienThong.ADSL)
                                            this.dsadsl = await this.LAY_DS_DB_ADSL_THEO_TBID(vthuebao_id);
                                        else if (this.dichvuvt_id == Enum.DichVuVienThong.MEGA_EYES)
                                            this.dsadsl = await this.LAY_DS_DB_MEGAEYES_THEO_TBID(vthuebao_id);

                                        if (dsadsl!= null && dsadsl.length > 0)
                                        {
                                            let dsktradb = []
                                            let dsktrahd = []
                                            let  vport_id = 0;
                                            let  vvci_vpi_id = 0;
                                            if (dsadsl[0].port_id != null)
                                                vport_id = parseInt(dsadsl[0].port_id);
                                            if (dsadsl[0].vci_vpi_id != null)
                                                vvci_vpi_id = parseInt(dsadsl[0].vci_vpi_id);

                                            dsktradb = await this.ktra_dk_port(this.account, vport_id, vvci_vpi_id, 1);
                                            dsktrahd = await this.ktra_dk_port(this.account, vport_id, vvci_vpi_id, 2);
                                            if (dsktradb.length == 0 && dsktrahd.length == 0)
                                            {
                                                //nếu ko có thuê bao nào sử dụng port thì update về chưa sử dụng
                                                await this.CapNhat_TrangThai_Port(vport_id, vvci_vpi_id, Enum.TRANGTHAI_PORT.CHUA_SD);
                                                await this.CapNhat_TrangThai_PortID(vport_id, Enum.TRANGTHAI_PORT.CHUA_SD);
                                                await this.CapNhat_ds_matb(vport_id, vvci_vpi_id, "");
                                                await this.Capnhat_port_vatly(vport_id, Enum.TRANGTHAI_PORT.CHUA_SD);
                                            }
                                            else if (dsktradb.length != 0 && dsktrahd.length == 0)
                                            {
                                                let vds_matb = "";
                                                for (let t = 0; t < dsktradb.length; t++)
                                                {
                                                    vds_matb = vds_matb + dsktradb[t].ma_tb + "; ";
                                                    if (vds_matb.length > 3000)
                                                        break;
                                                }
                                                if (vds_matb != "")
                                                    vds_matb = vds_matb.substring(0, vds_matb.length - 2);

                                                await this.CapNhat_TrangThai_Port(vport_id, vvci_vpi_id,  Enum.TRANGTHAI_PORT.DA_SD);
                                                await this.CapNhat_TrangThai_PortID(vport_id,  Enum.TRANGTHAI_PORT.DA_SD);
                                                await this.CapNhat_ds_matb(vport_id, vvci_vpi_id, vds_matb);
                                                await this.Capnhat_port_vatly(vport_id,  Enum.TRANGTHAI_PORT.DA_SD);
                                            }
                                            else if (dsktradb.length == 0 && dsktrahd.length != 0)
                                            {
                                                let vds_matb = "";
                                                for (let t = 0; t < dsktrahd.length; t++)
                                                {
                                                    vds_matb = vds_matb + dsktrahd[t].ma_tb + "; ";
                                                    if (vds_matb.length > 3000)
                                                        break;
                                                }
                                                if (vds_matb != "")
                                                    vds_matb = vds_matb.substring(0, vds_matb.length - 2);

                                                await this.CapNhat_TrangThai_Port(vport_id, vvci_vpi_id, Enum.TRANGTHAI_PORT.TAM_CAP);
                                                await this.CapNhat_TrangThai_PortID(vport_id, Enum.TRANGTHAI_PORT.DA_SD);
                                                await this.Capnhat_port_vatly(vport_id, Enum.TRANGTHAI_PORT.DA_SD);
                                                await this.CapNhat_ds_matb(vport_id, vvci_vpi_id, vds_matb);
                                            }
                                            else if (dsktradb.length != 0 && dsktrahd.length != 0)
                                            {
                                                let vds_matb = "";
                                                for (let t = 0; t < dsktradb.length; t++)
                                                {
                                                    vds_matb = vds_matb + dsktradb[t].ma_tb + "; ";
                                                    if (vds_matb.length > 3000)
                                                        break;
                                                }
                                                for (let t = 0; t < dsktrahd.length; t++)
                                                {
                                                    vds_matb = vds_matb + dsktrahd[t].ma_tb + "; ";
                                                    if (vds_matb.length > 3000)
                                                        break;
                                                }

                                                if (vds_matb != "")
                                                    vds_matb = vds_matb.substring(0, vds_matb.length - 2);

                                                await this.CapNhat_TrangThai_Port(vport_id, vvci_vpi_id, Enum.TRANGTHAI_PORT.DA_SD);
                                                await this.CapNhat_TrangThai_PortID(vport_id, Enum.TRANGTHAI_PORT.DA_SD);
                                                await this.Capnhat_port_vatly(vport_id, Enum.TRANGTHAI_PORT.DA_SD);
                                                await this.CapNhat_ds_matb(vport_id, vvci_vpi_id, vds_matb);
                                            }
                                        }
                                       // #endregion

                                        await this.GIAOPHIEU_TD_KHI_HC(in_hdtb_id, in_phieu_id, hg_phieu_id)
                                        await this.HOANTHIEN_HS_CHAMDUT_SD(in_hdtb_id);
                                        await this.CapNhat_TT_Phieu_GPhone(in_hdtb_id);

                                        if (this.PHAILAM("KT_HOANCONG_HD_BANCHEO"))
                                        {
                                            try
                                            {
                                                await this.hoangcong_hopdong_bancheo(in_hdtb_id);
                                            }
                                            catch (ex)
                                            {
                                             // await   this.Log_Action(in_hdtb_id,ex);
                                               
                                            }
                                        }

                                        //#region Cường thêm 29/8/2018

                                        let boqua = false;
                                        if (this.PHAILAM("GDV_THUHOI_KHI_HOANCONG")) // Nếu có thiết lập luồng thao tác GDV thu hồi
                                        {
                                            try
                                            {
                                                // Kiểm tra xem có vật tư cấp ra không
                                                let kq = await this.kiemtra_vattu_cap(in_hdtb_id)
                                                if (kq)   //Nếu có vật tư thì đẩy phiếu về => kế toán GDV
                                                {
                                                  await this.giaophieu_td_taophieu_vtttkd(in_hdtb_id,in_phieu_id)                                               
                                                    boqua = true;
                                                }
                                            }
                                            catch (ex)
                                            {
                                             console.log(ex)
                                            }

                                        }
                                        //#endregion

                                        if (this.PHAILAM("TUDONG_THUHOI_KHI_HOANCONG") && !boqua)
                                        {
                                            await this.GIAOPHIEU_TD_HOST_THUHOI_1(in_hdtb_id, in_phieu_id);
                                        }
                                        try
                                        {
                                            if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV)
                                            {
                                                //#region Kiểm tra hủy gói gia đình với mytv
                                                
                                                let dsKtra = await this.Kiemtra_huygoi_mytv_tddv(this.index_hdtb_id);

                                                if (dsKtra != null && dsKtra.length > 0)
                                                {
                                                    if (this.getValueFromIndex(dsKtra[0],0) == "1")
                                                    {
                                                        let kt = 0;
                                                        let goi_id = this.getValueFromIndex(dsKtra[0],1);
                                                        let nhomtb_id =this.getValueFromIndex(dsKtra[0],2)

                                                        let home_gdvp = this.getValueFromIndex(dsKtra[0],3)
                                                        let goi_neo_id = this.getValueFromIndex(dsKtra[0],4)

                                                        let kq = "";
                                                        let _result_code = true;
                                                        let _strErrorMessage = "";
                                                        let didong_chunhom = await this.LayThueBaoChuNhom_GDVP();
                                                        let ma_goi = await this.LayMaGoi(goi_id);
                                                        let accountFiber = await this.LayThueBaoFiber();

                                                        //#region Tạo hợp đồng xóa thuê bao khỏi gói gia đình

                                                      

                                                        await this.TaoDuLieu_huygoi_mytv(goi_id, nhomtb_id, Enum.KieuLapDat.XOA_TB_GOI_DADV);
                                                  
                                                        //#endregion
                                                        let ip = await this.getIp()
                                                        //#region Gọi kích hoạt xóa thành viên CCBS
                                                        if (home_gdvp != -1) //-> gói home hoặc GĐ/VP
                                                        {
                                                            if (home_gdvp == 1) // Home -> xóa mytv là xóa gói
                                                            {
                                                                let kqCCBS = await this.API_DeletePackageCSS_HOME(Enum.GD_VP._username, Enum.GD_VP._password, didong_chunhom,
                                                                 this.ten_tat_ccbs, goi_neo_id,ttnd.user_neo , ip, accountFiber);
                                                                if (kqCCBS != "0")
                                                                {
                                                                  this.$root.toastError(kqCCBS)
                                                                }
                                                            }
                                                            else if (home_gdvp == 2) //GD VP
                                                            {
                                                                let member = this.account;

                                                                let kqCCBS = await this.API_DelMemberGroup_GDVP(GD_VP._username, GD_VP._password, didong_chunhom,
                                                                 this.ten_tat_ccbs, member, accountFiber, ttnd.user_neo, ip);
                                                                if (kqCCBS != "0")
                                                                {
                                                                    this.$root.toastError(kqCCBS)
                                                                }
                                                            }
                                                        }
                                                      //#endregion

                                                       //#region Hoàn thiện hồ sơ thay đổi gói
                                                        await this.HOANTHIEN_HS_TD_GOI_DADV(this.hdkh_id_huygoi_mytv);
                                                       //#endregion
                                                    }
                                                }
                                               //#endregion
                                            }
                                            else
                                            {
                                                if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH)
                                                {
                                                    let dsKtra = await this.kiemtra_huygoi_thanhly(this.index_hdtb_id);

                                                    if (dsKtra != null && dsKtra.length > 0)
                                                    {
                                                        if (this.getValueFromIndex(dsKtra[0],0) == "1")
                                                        {
                                                            let kt = 0;
                                                            let goi_id = parseInt(this.getValueFromIndex(dsKtra[0],1))
                                                            let nhomtb_id = parseInt(this.getValueFromIndex(dsKtra[0],2));

                                                            let home_gdvp = parseInt(this.getValueFromIndex(dsKtra[0],3));
                                                            let goi_neo_id = this.getValueFromIndex(dsKtra[0],4)
                                                            let kq = "";
                                                            let _result_code = true;
                                                            let _strErrorMessage = "";
                                                            let didong_chunhom = await this.LayThueBaoChuNhom_GDVP();
                                                            let ma_goi = await this.LayMaGoi(goi_id);
                                                            let accountFiber = await this.LayThueBaoFiber();

                                                           // #region Tạo hợp đồng hủy gói

                                                            await this.TaoDuLieu_huygoi_mytv(goi_id, nhomtb_id, Enum.KieuLapDat.HUY_GOI_DADV);
                                                           // #endregion

                                                           // #region Gọi kích hoạt hủy gói CCBS
                                                            if (home_gdvp != -1) //-> gói home hoặc GĐ/VP
                                                            {
                                                                if (home_gdvp == 1) // Home
                                                                {
                                                                    let kqCCBS = await this.API_DeletePackageCSS_HOME(Enum.GD_VP._username, Enum.GD_VP._password, 
                                                                    didong_chunhom, this.ten_tat_ccbs, goi_neo_id, ttnd.user_neo, ip,
                                                                      accountFiber);
                                                                    if (kqCCBS != "0")
                                                                    {
                                                                      this.$root.toastError(kqCCBS);
                                                                    }
                                                                }
                                                                else if (home_gdvp == 2) //GD VP
                                                                {
                                                                    let kqCCBS = await this.API_DeletePackageCSS_GDVP(Enum.GD_VP._username, Enum.GD_VP._password, 
                                                                    didong_chunhom, this.ten_tat_ccbs, 
                                                                    goi_neo_id, ttnd.user_neo, ip, accountFiber);
                                                                    if (kqCCBS != "0")
                                                                    {
                                                                         this.$root.toastError(kqCCBS);
                                                                    }
                                                                }
                                                            }
                                                           // #endregion

                                                            //#region Hoàn thiện hồ sơ thay đổi gói
                                                            await this.HOANTHIEN_HS_TD_GOI_DADV(this.hdkh_id_huygoi_mytv);
                                                            //#endregion


                                                        }
                                                    }

                                                }

                                                //#region Hủy gói với các dịch vụ còn lại
                                                if (this.huygoi_hoancong_tl == 1)
                                                {
                                                    //TraCuuChungFacade tcchung = new TraCuuChungFacade();
                                                    let dsKtra = await this.kiemtra_huygoi_thanhly(this.index_hdtb_id);

                                                    if (dsKtra != null && dsKtra.length > 0)
                                                    {
                                                        if (this.getValueFromIndex(dsKtra[0],0) == "1")
                                                        {
                                                            let kt = 0;
                                                            let goi_id = parseInt(this.getValueFromIndex(dsKtra[0],1));
                                                            let nhomtb_id = parseInt(this.getValueFromIndex(dsKtra[0],2));

                                                            let home_gdvp = parseInt(this.getValueFromIndex(dsKtra[0],3));
                                                            let goi_neo_id = this.getValueFromIndex(dsKtra[0],4);

                                                            let kq = "";
                                                            let _result_code = true;
                                                            let _strErrorMessage = "";
                                                            let didong_chunhom = await this.LayThueBaoChuNhom_GDVP();
                                                            let ma_goi = await this.LayMaGoi(goi_id.toString());
                                                            let accountFiber = await this.LayThueBaoFiber();

                                                            //#region Tạo hợp đồng xóa thuê bao khỏi gói
                                                            await this.TaoDuLieu_huygoi_mytv(goi_id, nhomtb_id, Enum.KieuLapDat.XOA_TB_GOI_DADV);
                                                          
                                                            //#endregion

                                                            //#region Gọi kích hoạt xóa thành viên CCBS
                                                            if (home_gdvp != -1) //-> gói home hoặc GĐ/VP
                                                            {
                                                                if (home_gdvp == 1) // Home 
                                                                {
                                                                    let member = this.account

                                                                    let kqCCBS = await this.API_DelMemberGroup_HOME(Enum.GD_VP._username, Enum.GD_VP._password, didong_chunhom, 
                                                                    this.ten_tat_ccbs, member, accountFiber,ttnd.user_neo, ip);
                                                                    if (kqCCBS != "0")
                                                                    {
                                                                       this.$root.toastError(kqCCBS);
                                                                    }
                                                                }
                                                                else if (home_gdvp == 2) //GD VP
                                                                {
                                                                    let member =this.account

                                                                    let kqCCBS = await this.API_DelMemberGroup_GDVP(Enum.GD_VP._username, Enum.GD_VP._password, didong_chunhom,
                                                                    this.ten_tat_ccbs, member, accountFiber, ttnd.user_neo, ip);
                                                                    if (kqCCBS != "0")
                                                                    {
                                                                       this.$root.toastError(kqCCBS);
                                                                    }
                                                                }
                                                            }
                                                            //#endregion

                                                            //#region Hoàn thiện hồ sơ thay đổi gói
                                                            await this.HOANTHIEN_HS_TD_GOI_DADV(this.hdkh_id_huygoi_mytv);
                                                            //#endregion
                                                        }
                                                    }
                                                }
                                                //#endregion
                                            }
                                        }
                                        catch (e)
                                        {
                                          console.log(e)
                                        }
                                        this.str_hoancong = "Cập nhật dữ liệu và hoàn thiện hồ sơ thành công!";
                                    }
                                    else
                                    {
                                        await this.GIAOPHIEU_TD_KHI_HC(in_hdtb_id, in_phieu_id, hg_phieu_id);
                                    }
                                  // this.$root.toastSuccess(str);
                                }

                                //dungpa them - kich hoat khoa mo goi tren VNP
                                //#region
                                let dsTS = await this.LAY_DS_THAMSO_MD_MATS("DONGBO_GOI_DA_DV");
                                let dongbo_dadv = -1;
                                if (dsTS != null && dsTS.length > 0)
                                {
                                    dongbo_dadv = parseInt(dsTS[0].ten_ts);
                                }
                                if (dongbo_dadv == 1)
                                {
                                    let _dsGoi = await this.LAY_TT_GOI_GDVP(in_hdtb_id) 
                                   if(_dsGoi!=null){
                                    if (_dsGoi.goi_home !=null && _dsGoi.goi_home.length > 0)
                                    {
                                        let _result = "";
                                        for (let j = 0; j < _dsGoi.goi_home.length; j++)
                                        {
                                            _result = await this.XOA_GOI_V3(_dsGoi.goi_home[j].goi_neo_id, "84" + _dsGoi.goi_home[j].ma_tb,
                                                                        _dsGoi.goi_home[j].acc_fiber,"1", this.ten_tat_ccbs);
                                            if (_result != "0")
                                            {
                                                this.$root.toastError("Có lỗi trong quá trình kích hoạt gói tích hợp trên VNP");
                                                return;
                                            }
                                        }
                                    }

                                    if (_dsGoi.goi_gdvp !=null &&_dsGoi.goi_gdvp.length > 0)
                                    {
                                        let _result = "";
                                        for (let j = 0; j < _dsGoi.goi_gdvp.length; j++)
                                        {
                                            _result = await this.XOA_GOI_V3(_dsGoi.goi_gdvp[j].goi_neo_id, "84" + _dsGoi.goi_gdvp[j].ma_tb,
                                             _dsGoi.goi_gdvp[j].acc_fiber, "1",this.ten_tat_ccbs);
                                            if (_result != "0")
                                            {
                                                this.$root.toastError("Có lỗi trong quá trình kích hoạt gói tích hợp trên VNP");
                                                return;
                                            }
                                        }
                                    }
                                  }
                                }
                                //#endregion
                                //end dungpa
    },
    KiemTraDK_HoanThanh_1:  function (thangnam_tt,  thangnam_sys){
      console.log('thangnam_tt',thangnam_tt)
      console.log('thangnam_sys',thangnam_sys)
     let result = false;
            if (parseInt(thangnam_tt.substring(6, thangnam_tt.length)) < parseInt(thangnam_sys.substring(6, thangnam_sys.length)))//So sánh năm
                return true;//Nếu năm thanh toán < năm hoàn thành thì true;
            else if (parseInt(thangnam_tt.substring(6, thangnam_tt.length )) == parseInt(thangnam_sys.substring(6, thangnam_sys.length )))//So sánh năm
            {
                //Nêu năm = nhau thì kiểm tra tiếp
                //Nếu tháng tt < thang ht -> true
                if (parseInt(thangnam_tt.substring(3, 5)) < parseInt(thangnam_sys.substring(3, 5)))
                    result = true;
                else if (parseInt(thangnam_tt.substring(3, 5)) == parseInt(thangnam_sys.substring(3, 5)))
                    //Nếu tháng & năm = nhau thì kiểm tra ngày
                    if (parseInt(thangnam_tt.substring(0, 2)) <= parseInt(thangnam_sys.substring(0, 2)))
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
    Kiemtra_yc_giaophieu_doitocdo: async function (hdtb_id){
        try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/Kiemtra_yc_giaophieu_doitocdo/"+hdtb_id
        );
        return rs.data
      } catch (error) {
        return null
      } 
    },
    HoanThienHoSo_DV: async function(hdtb_id){
      try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/hoanthienhoso_dichvu/"+hdtb_id       
        );
      } catch (e) {
         console.log(e)
      } 
    },
     HoanThienHoSo_TocDo: async function(hdtb_id){
      try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/hoanthienhoso_tocdo/"+hdtb_id       
        );
      } catch (e) {
         console.log(e)
      } 
    },
    LAY_TT_GOI_GDVP: async function (hdtb_id){
   try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_tt_goi_gdvp/"+hdtb_id,        
        );
         return rs.data
      } catch (error) {
         return {
          goi_home: null,
          goi_gdvp: null
         }
      } 
    },
    CAPNHAT_NGAYKH_STATUS_V2: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.post(
          "/web-ccdv/tiepnhanhopdong/capnhat_ngaykh_status_v2",
          {
            hdtb_id: hdtb_id,
            ngay_kh: DateTimeLib.toDateDisplayWithFormat(new Date(),"DD/MM/yyyy HH:mm:ss"),
            status: Enum.TRANGTHAI_DONGBO.DONGBO_CM,
          }
        );
      } catch (e) {
        console.log(e)
      } 
    },
    Lay_DonVi_GiaoPhieu_HTHS: async function (huonggiao_id, hdtb_id){
       try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_donvi_giaophieu_hths/"+hdtb_id       
        );
        return rs.data
      } catch (error) {
        return null;
      } 
    },
    Lay_DonVi_GiaoPhieu: async function (huonggiao_id,  hdtb_id){
       try {
        console.log('huonggiao_id',huonggiao_id)
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_donvi_giaophieu",
          {hdtb_id : hdtb_id,
           huonggiao_id:huonggiao_id}
        );
        return rs.data
      } catch (error) {
        return null;
      }      
    },
    CapNhat_TT_Phieu_HC: async function(hdtb_id, ttph_id){
       try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/capnhat_tt_phieu_hc?hdtb_id="+hdtb_id+"&ttph_id="+ttph_id,
          // {hdtb_id : hdtb_id,
          //  ttph_id:ttph_id }
        );      
      } catch (error) {
      }     
    },
    KiemTra_Phieu_DaGiao: async function (hdtb_id,huonggiao_id){
       try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/kiemtra_phieu_dagiao?hdtb_id="+hdtb_id+"&huonggiao_id="+huonggiao_id,         
        );   
        console.log(rs)
         if (rs!=null && rs.length > 0)
                return false;
            else
                return true;   
      } catch (error) {
        console.log(error)
        return false
      }            
    },
    LAY_DS_DB_ADSL_THEO_TBID: async function (thuebao_id){
        try {
        let rs = await this.$root.context.get(
          "/web-tracuu/tracu-danhba/ds_adsl_tb/"+thuebao_id,
        );
        return rs.data       
      } catch (error) {
        return null
      }  
    },
    LAY_DS_DB_MEGAEYES_THEO_TBID: async function(thuebao_id){
       try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_db_megaeyes_theo_tbid/"+thuebao_id       
        );
       return rs.data
      } catch (error) {
        return null;
      } 
    },
    ktra_dk_port: async function (ma_tb,  port_id,  vci_vpi_id,  kieu){
        try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/ktra_dk_port",
          {ma_tb : ma_tb,
           port_id:port_id ,
           vci_vpi_id:vci_vpi_id,
           kieu:kieu }
        );   
        return rs.data  
      } catch (error) {
        return null
      }         
    },
    CapNhat_TrangThai_Port: async function (port_id,  vci_vpi,  trangthai){
      try {
        await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/CapNhat_TrangThai_Port",
          {
            port_id:port_id,
            vci_vpi: vci_vpi,
            trangthai:trangthai
          }
        );
      } catch (e) {
        console.log(e)
      } 
    },
    CapNhat_TrangThai_PortID: async function (port_id, trangthai){
      try {
         await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/CapNhat_TrangThai_PortID",
           {
            port_id:port_id,
            trangthai:trangthai
          }
        );

      } catch (e) {
          console.log(e)
      } 
    },
     CapNhat_ds_matb: async function (port_id, vci_vpi, vds_matb){
      try {
       await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/CapNhat_ds_matb",
           {
            port_id:port_id,
            vci_vpi: vci_vpi,
            ds_matb:vds_matb
          }
        );
      } catch (e) {
          console.log(e)
      } 
     },
     Capnhat_port_vatly: async function (port_id, trangthai){
     try {
        await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/Capnhat_port_vatly",
          {
            port_id:port_id,
            trangthai:trangthai
          }
        );
      } catch (e) {
          console.log(e)
      } 
     },
     GIAOPHIEU_TD_KHI_HC: async function (in_hdtb_id, in_phieu_id, hg_phieu_id){
      try {
        let rs = await this.$root.context.post(
          "/web-hopdong/thaydoithongtinkhuyenmai/giaophieu_td_khi_hc",
          {
            hdtb_id: in_hdtb_id,
            huonggiao_id: hg_phieu_id,
            phieu_id: in_phieu_id,
            may_cn:""
          }
        );
      } catch (e) {
          console.log(e)
      } 
     },
     HOANTHIEN_HS_CHAMDUT_SD: async function (hdtb_id){
       try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/hoanthien_hs_chamdut_sd?hdtb_id="+hdtb_id   
        );
      } catch (e) {
          console.log(e)
      } 
     },
     CapNhat_TT_Phieu_GPhone: async function (hdtb_id){
       try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/capnhat_tt_phieu_gphone?hdtb_id="+hdtb_id      
        );
      } catch (e) {
          console.log(e)
      } 
     },
     GIAOPHIEU_TD_HOST_THUHOI_1: async function (hdtb_id, phieu_id){
       try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/giaophieu_td_host_thuhoi_1?hdtb_id="+hdtb_id+"&phieu_id="+phieu_id
        );
      } catch (e) {
          console.log(e)
      } 
     },
     XOA_GOI_V3: async function ( goi_id_neo,  matb_dd,  acc_fiber,  typeid,  agent){
        try {
          //let rs = await this.$root.context.post(
          // "/ccbs/gdvp/delete-package-css",{
          //             accadd: "",
          //             accid: "",
          //             accname: "",
          //             account: acc_fiber,
          //             agent: agent,
          //             channel: "1",
          //             msisdn: matb_dd,
          //             note: "",
          //             request_id: "",
          //             service: "",
          //             type: typeid,
          //             userid: "",
          //             userip: ""
          //            });
        await this.$root.context.post(
          "/ccbs/gdvp/dereg",{
                      accadd: "",
                      accid: "",
                      accname: "",
                      account: acc_fiber,
                      agent: agent,
                      channel: "1",
                      packageid:goi_id_neo,
                      msisdn: matb_dd,
                      note: "",
                      request_id: "",
                      service: "",
                      type: typeid,
                      userid: "",
                      userip: ""
                     }); 
        await this.$root.context.post(
          "/ccbs/gdvp/expire",{
                      accadd: "",
                      accid: "",
                      accname: "",
                      account: acc_fiber,
                      packageid:goi_id_neo,
                      agent: agent,
                      channel: "1",
                      msisdn: matb_dd,
                      note: "",
                      request_id: "",
                      service: "",
                      type: typeid,
                      userid: "",
                      userip: ""
                     });
        return rs.data
      } catch (e) {
          console.log(e)
      } 
     },
     DoKiem: async function(ma_tb,kieu=1){
        try {
        let rs = await this.$root.context.post(
          "app-cskh/TestPortV2/dokiem",
          {
              show_server_dk: "0",
              server_id: 0,
              vthuebao_id: 0,
              vhdtb_id: 0,
              vma_tb: ma_tb,
              vProfile: "",
              user_ont: "",
              pass_ont: "",
              isGetMac: false,
              vkieu: kieu
            }     
        );
          return rs.data
      } catch (e) {
        console.log(e)
        return ""
      }
   
    },
    lay_thongtin_nguoidung: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung"
        );     
        return rs.data[0]
      } catch (error) {
        return null;
      } 
    },
     hoangcong_hopdong_bancheo: async function (in_hdtb_id){
      let tinhtc_id = 0;
      let tinh = {};
      let ds = await this.HOANCONG_THICONG_2(in_hdtb_id)
      if(ds == null || ds.length == 0)return
      if(ds[0].hdtb_ban > 0 ){
        tinh = await this.tinh_thicong(ds[0].tinhban)
        tinhtc_id = ds[0].tinhtc
        await this.hoancong_hopdong_bancheo_v3(ds[0].hdtb_ban, ds[0].khachhang_tc_id,
                                              ds[0].thanhtoan_tc_id, ds[0].thuebao_tc_id, tinhtc_id)
      }else{
         let ds2 = await this.BENBAN_HOANTHIEN(in_hdtb_id);
         if(ds2 == null || ds.length == 0) return;
         if(ds2[0].loaihd_id == Enum.LoaiHopDong.DAT_MOI && ds2[0].thuebao_id_thicong != '-1'){
           if(ds2[0].dichvuvt_id != Enum.DichVuVienThong.TSL){
           await this.them_db_bancheo(ds2[0].thuebao_id_thicong, ds2[0].thuebao_id_ban, ds2[0].tinh_ban, 2);
           }else{
             for(let i = 0; i<ds2.length; i++){
             tinh = await this.tinh_thicong(ds2[i].tinh_thicong)
              await this.them_db_bancheo(ds2[0].thuebao_id_thicong, ds2[0].thuebao_id_ban, ds2[0].tinh_ban, 2);
             }
           }
         }
      }
     },
     tinh_thicong: async function (tinh_id){
        try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/tinh_thicong/"+tinh_id
        );
        if(rs.data != null && rs.data.length > 0)
        return rs.data[0]
        return null
      } catch (error) {
        return null
      } 
     },
     hoancong_hopdong_bancheo_v3: async function (hdtb_ban,  khachhang_tc_id, 
                                                thanhtoan_tc_id,  thuebao_tc_id,  tinh_tc)
      {
        try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/hoancong_hopdong_bancheo_v3",
          {
            hdtb_id: hdtb_ban,
            khachhang_tc_id: khachhang_tc_id,
            thanhtoan_tc_id: thanhtoan_tc_id,
            thuebao_tc_id:thuebao_tc_id,
            tinh_tc:tinh_tc
          }
        );
      } catch (e) {
         console.log(e)
      }   
     },
     HOANCONG_THICONG_2: async function (in_hdtb_id){
       try{
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/hoancong_thicong_2/"+in_hdtb_id
        );
        return rs.data
      } catch (error) {
        return null
      } 
     },
     BENBAN_HOANTHIEN: async function (in_hdtb_id){
        try{
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/benban_hoanthien/"+in_hdtb_id
        );
        return rs.data
      } catch (error) {
        return null
      }
     },
     them_db_bancheo: async function (thuebao_id, thuebao_kn_id, tinh_kn_id, loai_bc){
         try{
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/them_db_bancheo",{
          thuebao_id:thuebao_id,
          thuebao_kn_id:thuebao_kn_id,
          tinh_kn_id:tinh_kn_id,
          loai_bc:loai_bc
          }
        );
        return rs.data
      } catch (error) {
        return null
      } 
     },
     Log_Action: async function (hdtb_id,ex){
      let ip = await this.getIp()
      let data = {
      action_name: "frmDongBoVisaVasc",
      key_parameter: "hdtb_id=" + hdtb_id + ",ngay_cn=" + DateTimeLib.toDateDisplay(new Date()) + ", Err : " +ex,
      server_name: ip,
      note: " Hoàn công cố định"
      };
     await this.log_action(data)
     },
    kiemtra_vattu_cap: async function (hdtb_id){
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/kiemtra_vattu_cap/"+hdtb_id
        );
      if(rs.data!=null && rs.data.length>0)
      return true
      else
      return false
     },
    getIp:async function(){
      try{
      const response = await fetch("https://jsonip.com/")
      const data = await response.json();
      return data.ip;
      }catch(e) {
        return ""
      }
    },
    giaophieu_td_taophieu_vtttkd: async function (in_hdtb_id,in_phieu_id){
       try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/giaophieu_td_taophieu_vtttkd",
          {hdtb_id : in_hdtb_id,
          phieu_id:in_phieu_id}     
        );
      } catch (e) {
       console.log(e)
      } 
    },
    Kiemtra_huygoi_mytv_tddv: async function (index_hdtb_id){
        try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/Kiemtra_huygoi_mytv_tddv/"+index_hdtb_id,
        );
        return rs.data
      } catch (error) {
      }
     
    },
    LayThueBaoChuNhom_GDVP: async function(){
        try {
        let vma_tb = this.account
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/laythuebaochunhom_gdvp",{ma_tb:vma_tb},
        );
      let ds= rs.data
        if(ds==null) return ''
      for(let i=0;i<ds.length;i++){
        if(ds[i].chunhom == 'Chu goi')
        return '84' + ds[i].ma_tb
      }
       return ''
      } catch (error) {
        return ''
      }     
    },
    LayMaGoi: async function(goi_id){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/laymagoi/"+goi_id
        );
      if(rs.data != null && rs.data.length > 0)
      return rs.data[0].ma_goid
      return ''
      } catch (error) {
        return ''
      }      
    },
    LayThueBaoFiber: async function (){
       try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/laythuebaofiber",
               {ma_tb:this.account},
        );
      if(rs.data == null) return ''
      for(let i=0;i<rs.data.length;i++){
        if(rs.data[i].loaihinh_tb == 'Fiber')
        return rs.data[i].ma_tb
      }
      return ''
      } catch (error) {
        return ''
      }     
    },
    TaoDuLieu_huygoi_mytv:async function (goi_id, nhomtb_id,kieuld_id){
          let data_hdkh = await this.TaoDuLieu_HDKH_huygoi_mytv();
          let hdkh = await this.ThemHDKH(data_hdkh)
           this.hdkh_id_huygoi_mytv = hdkh.hdkh_id 
          let data_hdtb=  await this.TaoDuLieu_HDTB_huygoi_mytv(kieuld_id);
          let hdtb = await this.ThemHDTB(data_hdtb)
          this.hdtb_id_huygoi_mytv = hdtb.hdtb_id
          await this.CAPNHAT_BIENDONG()
          await this.CHUYEN_DL_HDTB_SANG_GIAOPHIEU()
          let data_goi = await this.TaoDuLieu_HDTB_GOI_DADV_huygoi_mytv(goi_id, nhomtb_id);
          await this.ThemHDTBGOI(data_goi)
    },
    ThemHDKH: async function (data_hdkh){
    try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/them_hd_khachhang",data_hdkh
        );
      let ds= rs.data
      if(ds!=null)
      return ds[0]
      return null
      } catch (error) {
        return null
      } 
    },
    ThemHDTB: async function (data_hdtb){
     try {
    // Cập nhật biến động hợp đồng thuê bao
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/them_hd_thuebao",data_hdtb
        );

      if(rs.data!=null)
      return rs.data[0]
      return null
      } catch (error) {
        return null
      } 
    },
    ThemHDTBGOI: async function (data_goi){
     try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/them_hd_thuebao_goi",data_goi
        );
      if(rs.data!=null)
      return rs.data[0]
      return null
      } catch (error) {
        return null
      }  
    },
    CAPNHAT_BIENDONG: async function(){
       try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/capnhat_biendong/"+this.hdtb_id_huygoi_mytv
        );
        return rs.data
      } catch (error) {
        return null
      }  
    },
    CHUYEN_DL_HDTB_SANG_GIAOPHIEU: async function (){
    try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/chuyen_dl_hdtb_sang_giaophieu",
          {hdtb_id : this.hdtb_id_huygoi_mytv,
          huonggiao_id: this.huonggiaotn_id}
        );
        return rs.data
      } catch (error) {
        return null
      } 
    },
  Lay_Ma_GD: async function (loaihd_id){
       try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/laythuebaofiber/"+loaihd_id,
        );

      if(rs.data!=null)
      return rs.data[0].ma_gd
      else
      return ''
      } catch (error) {
        return ''
      }      
    },
   TaoDuLieu_HDKH_huygoi_mytv:async function()
        {
          let hdkh_id_huygoi_mytv = 0

            //dsHDKH = new HD_KHACHHANG_DATA();
            hdkh_id_huygoi_mytv = 0 //getkey.GetKey("HD_KHACHHANG");
            let khachhang_id = this.checkMapId_new(this.index_thuebao_id,9) 
            let rowHDKH = new Object();
            
            Object.assign(rowHDKH,await this.LAY_DS_DBKH_THEO_KHID(khachhang_id) );
           // rowHDKH.hdkh_id = hdkh_id_huygoi_mytv;
            rowHDKH.ma_gd = await this.Lay_Ma_GD(Enum.LoaiHopDong.THAYDOI_GOI_DADV);   
            rowHDKH.khachhang_id = khachhang_id;
            rowHDKH.ngaylap_hd = DateTimeLib.toDateDisplay(new Date())
            rowHDKH.ngay_yc =  DateTimeLib.toDateDisplay(new Date())
            rowHDKH.ghichu = "Tự động xóa thuê bao khỏi gói đa dịch vụ";
            rowHDKH.donvi_id = this.$root.token.getIDDonVi()
            rowHDKH.nhanvien_id = tt_nd.nhanvien_id;
            rowHDKH.loaihd_id = Enum.LoaiHopDong.THAYDOI_GOI_DADV;
            rowHDKH.ngay_cn =  DateTimeLib.toDateDisplay(new Date())
            rowHDKH.nguoi_cn = this.$root.token.getUserName()
            rowHDKH.may_cn = '';
            rowHDKH.ip_cn = await this.getIp();
            return rowHDKH
    },
   TaoDuLieu_HDTB_huygoi_mytv: async function(kieuld_id)
        {
           let hdtb_id_huygoi_mytv = 0
          // let hdkh_id_huygoi_mytv = 0
           let db_thuebao = await this.LAY_THONGTIN_DBTHUEBAO(this.thuebao_id);
            let rowHDTB = new Object();//dsHDTB.HD_THUEBAO.NewHD_THUEBAORow();

           // rowHDTB.hdtb_id = hdtb_id_huygoi_mytv;
            rowHDTB.hdkh_id = this.hdkh_id_huygoi_mytv;
            rowHDTB.thuebao_id = this.thuebao_id;
            rowHDTB.ma_tb = this.index_account;
            rowHDTB.ten_tb = db_thuebao.ten_tb 
            rowHDTB.diachi_tb = db_thuebao.diachi_tb
            rowHDTB.kieuld_id = Enum.KieuLapDat.XOA_TB_GOI_DADV;
            let doituong_id = db_thuebao.doituong_id
            rowHDTB.doituong_id = doituong_id;
            rowHDTB.tthd_id = Enum.TrangThaiHD.MOI;

            //rowHDTB.LOAITB_ID = LoaiHinhTB.INTERNET_MYTV;
            //rowHDTB.DICHVUVT_ID = DichVuVienThong.ADSL;
            rowHDTB.loaitb_id = this.index_loaitb_id;
            rowHDTB.dichvuvt_id = this.dichvuvt_id

            rowHDTB.donvi_id = this.$root.token.getIDDonVi()
            rowHDTB.nguoi_cn = this.$root.token.getUserName()
            rowHDTB.ngay_cn = DateTimeLib.toDateDisplay(new Date())
            rowHDTB.may_cn = ''

            // vinhpv lay huonggiao_id
            let dset = []
            dset = await this.LayHuongGiao_TiepNhan(Enum.LoaiHopDong.THAYDOI_GOI_DADV,kieuld_id);

            if (dset != null && dset.length > 0)
            {
                this.huonggiaotn_id = dset[0].huonggiao_id
                this.quytrinh_id_2 = dset[0].quytrinh_id
            }
            rowHDTB.quytrinh_id = this.quytrinh_id_2;

            try
            {
                let mucuoctb_id = db_thuebao.mucuoctb_id;
                let mucuoctb = db_thuebao.mucuoc_tb;
                if (mucuoctb_id != "-1")
                    rowHDTB.mucuoctb_id = parseInt(mucuoctb_id);
                if (mucuoctb != "-1")
                    rowHDTB.mucuoc_tb = parseInt(mucuoctb);
            }
            catch (Exception)
            {
            }
            return rowHDTB
           // this.dsHDTB.push(rowHDTB);
   },
    TaoDuLieu_HDTB_GOI_DADV_huygoi_mytv: async function( goi_id,  nhomtb_id)
        {
            
            let rowGOI = new Object();
            rowGOI.goi_id = goi_id;
            rowGOI.nhomtb_id = nhomtb_id;
            rowGOI.chunhom = 0;
            rowGOI.hdtb_id = this.hdtb_id_huygoi_mytv;
            rowGOI.thang_huong = 0;
            return rowGOI
            //this.dsHDTBGOI.push(rowGOI);
    },
   LayHuongGiao_TiepNhan: async function (loaihd_id,  kieuld_id){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/layhuonggiao_tiepnhan",
          {loaihd_id:loaihd_id,
          kieuld_id:kieuld_id}
        );
        return rs.data
      }catch(e){
        return null
      }
    },
    LayMucCuocTB: async function (tmp_thuebao_id, kieu){
    let ds =   await this.LAY_THONGTIN_DBTHUEBAO(tmp_thuebao_id)
    if (kieu == 1)
            {
                return ds.mucuoctb_id;
            }
            else
            {
                return  ds.mucuoc_tb;
            }
    },
    LAY_DS_DBKH_THEO_KHID: async function (khachhang_id){
        try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_dbkh_theo_khid/"+khachhang_id,
        );
      if(rs.data !=null && rs.data.length > 0)
      return rs.data[0]
      return null
      } catch (e) {
        return null
      }    
    },
    LAY_THONGTIN_DBTHUEBAO: async function (thuebao_id){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_thongtin_dbthuebao/"+thuebao_id,
        );
      if(rs.data !=null && rs.data.length > 0)
      return rs.data[0]
      return null
      } catch (error) {
        return null
      }      
    },
    API_DeletePackageCSS_HOME: async function (suser,  spass,  msisdn,  agent,  packet_id,  userid,  userip,  account){
      try {
        let rs = await this.$root.context.post(
          "/ccbs/gdvp/delete-member",{
                      account: account,
                      agent: agent,
                      channel: "1",
                      member: msisdn,
                      msisdn: msisdn,
                      packageid: packet_id,
                      request_id: "", 
                      service: "",
                      serviceid: "",
                      type: "",
                      userid: userid,
                      userip: userip
                     });
        return rs.data
      } catch (e) {
          console.log(e)
      } 
    },
    API_DelMemberGroup_GDVP: async function (suser,  spass,  msisdn,  agent,  member,  account,  userid,  userip){
     try {
        let rs = await this.$root.context.post(
          "/ccbs/gdvp/delete-member",{
                      account: account,
                      agent: agent,
                      channel: "1",
                      member: member,
                      msisdn: msisdn,
                      packageid: packet_id,
                      request_id: "", 
                      service: "",
                      serviceid: "",
                      type: "",
                      userid: userid,
                      userip: userip
                     });
        return rs.data
      } catch (e) {
          console.log(e)
      } 
    },
    API_DeletePackageCSS_GDVP: async function (suser,  spass,  msisdn,  agent,  packet_id,  userid,  userip,  account){
      try {
        let rs = await this.$root.context.post(
          "/ccbs/gdvp/delete-package-css",{
                      account: account,
                      agent: agent,
                      channel: "1",
                      member: member,
                      msisdn: msisdn,
                      packageid: packet_id,
                      request_id: "", 
                      service: "",
                      serviceid: "",
                      type: "",
                      userid: userid,
                      userip: userip
                     });
        return rs.data
      } catch (e) {
          console.log(e)
      } 
    },
    API_DelMemberGroup_HOME: async function( suser,  spass,  msisdn,  agent,  member,  account,  userid,  userip){
       try {
        let rs = await this.$root.context.post(
              "/ccbs/gdvp/delete-member",{
                      account: account,
                      agent: agent,
                      channel: "1",
                      member: member,
                      msisdn: msisdn,
                      packageid: packet_id,
                      request_id: "", 
                      service: "",
                      serviceid: "",
                      type: "",
                      userid: userid,
                      userip: userip
                     });
        return rs.data
      } catch (e) {
          console.log(e)
      } 
    },
  API_UpdateAccountFiber: async function ( agent,  msisdn,  account,  accname,  accadd,  accid,  note,  userid,  susername,  spassword){
   try {
        let rs = await this.$root.context.post(
              "/ccbs/gdvp/update-acc-fiber",{
                      
                    accadd:accadd,
                    accid: accid,
                    accname: accname,
                    account: account,
                    agent: agent,
                    channel: "1",
                    msisdn: msisdn,
                    note: note,
                    request_id: "",
                    service: "",
                    type: "",
                    userid: userid,
                    userip: ""
                     });
        return rs.data
      } catch (e) {
          console.log(e)
      } 
    },
    HOANTHIEN_HS_TD_GOI_DADV: async function (hdkh_id){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/hoanthien_hs_td_goi_dadv/"+hdkh_id
        );
      } catch (e) {
       console.log(e)
      } 
    },
    kiemtra_huygoi_thanhly: async function (hdtb_id){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/kiemtra_huygoi_thanhly/"+hdtb_id
        );
        return rs.data
      } catch (error) {
        return null;
      }    
    },
    ///Hàm Kích hoạt Đồng Bộ thuê bao
    DongBo: async function () {
      try {
        if (this.dsThueBao.length == 0) {
          this.$root.toastError("Không có thuê bao để thực hiện kích hoạt!");
          return;
        }
        this.$root.showLoading(true);
        console.log("Item", this.currentItem);
        let loaitb_id = this.currentItem.loaitb_id;
        let kieuld_id = this.currentItem.kieuld_id;
        let hdtb_id = this.currentItem.hdtb_id;
        let loaihd_id = this.loaihd_id;

        //#region  Thay đổi tốc độ Intenet

        if (this.loaihd_id == Enum.LoaiHopDong.THAY_DOI_TOCDO_ADSL) {
          let s = "";
          if (
            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
            this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER    ||
            this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
          )
            s = "Visa";
          else if (
            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV ||
            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV_B2B
          )
            s = "Vasc";
          else if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_SGTV) s = "SaiGonTV";
          else if (
            this.loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_ANALOG ||
            this.loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_DIGITAL
          )
            s = "HTVC Fiber";
          this.$confirm(
            `Bạn có chắc chắn muốn kích hoạt dịch vụ này lên ` +
              s +
              ` hay không?`,
            "Kích hoạt dịch vụ",
            {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
            }
          )
            .then(async () => {
              console.log('Thay đổi tốc độ Intenet')
              this.$root.showLoading(true);
              let kieu_mega = "1";
              if (
                this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
                this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
                this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER    ||
                this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
              ) {
                let ds_mega = await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(this.hdtb_id);
                if (ds_mega != null)
                  kieu_mega =  await this.checkMapId_new(ds_mega.tocdo_id,5)                  
              }
              if (!this.dongbo_test && kieu_mega != "0") {
                //#region ADSL, FTTH
                if (
                  this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
                  this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
                  this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER    ||
                   this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
                ) {
                  let account = "";
                  let ip = "";
                  let loaicuoc_id = "";
                  let tucthi = "";
                  let tocdo_id = "";
                  let tocdo_cu_id = "";
                  if (this.dsThueBao.length > 0) {
                    let tttc = "";
                    account = this.ChuanHoa_XML(this.currentItem.ma_tb);
                    tttc = await this.checkMapId_new(this.tocdo_adsl_id,10)   
                    if (tttc == "-1") tttc = "";
                    let iptinh = await this.checkMapId_new(this.tocdo_adsl_id,6) 
                    if (iptinh == "1") {
                      if (this.giu_ip) ip = this.currentItem.ip;
                      else if (this.tudong) ip = "";
                      if (!this.giu_ip && !this.tudong)
                        if (this.diachi_ip == "") {
                          this.$root.toastError("Bạn chưa nhập IP");
                          this.isErrorIp = true;
                          this.$refs.diachi_ip.focus();
                          this.$root.showLoading(false);
                          return;
                        } else ip = this.diachi_ip;
                    } else ip = "";
                    loaicuoc_id = this.currentItem.muccuoc_id;
                    loaicuoc_id = await this.checkMapId_new(loaicuoc_id,11)  
                    if (loaicuoc_id == "-1") {
                      this.$root.toastError(
                        "Thông tin loại cước chưa đồng bộ với dữ liệu Visa. Bạn không thể tiếp tục cập nhật dữ liệu."
                      );
                      this.$root.showLoading(false);
                      return;
                    }
                    tucthi = this.currentItem.tucthi;
                    if (tucthi == "1" ||  tucthi == "2" || tucthi == "3" || tucthi == "5")
                      tucthi = "0";
                    else tucthi = "1";
                    tocdo_id = this.currentItem.tocdo_id;
                    tocdo_cu_id = this.currentItem.tdcu_id;
                    kieu_mega = await this.checkMapId_new(tocdo_id,5) 

                    tocdo_id =await this.checkMapId_new(tocdo_id,12)  
                    tocdo_cu_id = await this.checkMapId_new(tocdo_cu_id,12)   
                    if (kieu_mega == "1") {
                      if (tocdo_id == "-1") {
                        this.$root.toastError(
                          "Thông tin về tốc độ chưa đồng bộ với dữ liệu Visa.\n Bạn không thể tiếp tục cập nhật dữ liệu."
                        );
                        this.$root.showLoading(false);
                        return;
                      }
                      //#region Thay đổi đối tượng
                      let dt_cu_id = await this.checkMapId_new(hdtb_id,13)  
                      if (dt_cu_id != "-1" && dt_cu_id != "") {
                        let dt_moi_id = this.currentItem.doituong_id;
                        if (parseInt(dt_cu_id) != dt_moi_id) {
                          console.log('Kiemtra_doituong_vdc')
                          if (await  this.Kiemtra_doituong_vdc(hdtb_id,Enum.LoaiHinhTB.INTERNET_ADSL)) {
                            if (!await this.ThayDoi_DoiTuong_Visa(dt_moi_id)) {
                              this.$root.toastError( "Có lỗi trong quá trình thay đổi đối tượng khách hàng trên visa!");
                              this.$root.showLoading(false);
                              return;
                            } else
                              this.$root.toastSuccess(
                                "Thay đổi đối tượng khách hàng trên visa thành công !"
                              );
                          }
                        }
                      }
                      //#endregion
                      //#region Thay đổi mức cước
                      if (kieuld_id == Enum.KieuLapDat.THAYDOI_MUCCUOC_ADSL) {
                        //Dùng hàm thay đổi HTTT
                        if (loaicuoc_id == "") {
                          this.$root.toastError(
                            "Chưa có thông tin về loại cước Visa !"
                          );
                          return;
                        }
                        //Kiểm tra thông tin tính cước
                        if (tttc != "") {
                        await   this.LayDuLieu_TTTC(tttc);
                          if (!await this.KiemTraDuLieu_TTTC()) {
                          this.$root.showLoading(false);
                          return;
                          }
                        }
                        if (
                        await   this.ThayDoiHinhThucTT(account, parseInt(loaicuoc_id),tucthi )) {
                          if (parseInt(tucthi) == 0)
                            //Chuyển đổi tức thì, thì sẽ tác động thay đổi luôn
                            await this.changeBillingInfo(tttc);
                          this.$root.toastSuccess(
                            "Kích hoạt đổi mức cước ADSL trên Visa thành công!"
                          );
                        } else {
                          this.$root.toastError(
                            "Có lỗi trong quá trình kích hoạt đổi mức cước ADSL trên Visa !"
                          );
                          this.$root.showLoading(false);
                          return;
                        }
                      }
                      // #endregion
                      // #region Thay đổi tốc độ
                      else if (
                        kieuld_id == Enum.KieuLapDat.THAYDOI_TOCDO_ADSL
                      ) {
                        //Dùng hàm thay đổi TocdoADSL
                        //Hiếu sửa bắt lỗi loại cước id ngày 04/09/2010
                        if (tocdo_id == "") {
                          this.$root.toastError(
                            "Chưa có thông tin về tốc độ Visa !"
                          );
                          return;
                        }
                        if (tocdo_id != tocdo_cu_id) {
                          if (tttc != "") {
                           await this.LayDuLieu_TTTC(tttc);
                            if (!await this.KiemTraDuLieu_TTTC()){
                              this.$root.showLoading(false);
                              return;
                            } 
                          }

                          

                          if (!await this.KICHHOAT_TOCDO_KHDN()) {
                              this.$root.showLoading(false);
                              return;
                            } 
                          console.log('DoiTocDoADSL');
                          console.log(`tocdo_id: ${tocdo_id}`);
                          let doitocdoadsl = await this.DoiTocDoADSL(account,parseInt(tocdo_id),ip,parseInt(tucthi),this.tudong,this.giu_ip)
                          if (doitocdoadsl) {
                            // Nếu là thay đổi tốc độ tức thì, thì nó update thông tin tính cước lên luôn
                            //if (tttc != "")
                            if (parseInt(tucthi) == 0)
                              await this.changeBillingInfo(tttc);
                            if (ip != "" && !this.giu_ip)
                             await  this.UPDATE_IP_ADSL(hdtb_id, ip);
                            this.$root.toastSuccess(
                              "Kích hoạt đổi tốc độ ADSL trên Visa thành công!"
                            );
                          } else {
                            this.$root.toastError(
                              "Có lỗi trong quá trình kích hoạt đổi tốc độ ADSL trên Visa !"
                            );

                            this.$root.showLoading(false);
                            return;
                          }
                        } else {
                          if (parseInt(tucthi) == 0)
                            await this.changeBillingInfo(tttc);

                          this.$root.toastSuccess(
                            "Kích hoạt đổi tốc độ ADSL trên Visa thành công!"
                          );
                          if (ip != "" && !this.giu_ip)
                          await   this.UPDATE_IP_ADSL(hdtb_id, ip);
                        }
                      }
                      //  #endregion
                      //#region Thay đổi tốc độ mức cước ADSL
                      else if (kieuld_id == Enum.KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL) {
                        /// Dùng cả 2 hàm
                        if (loaicuoc_id == "") {
                          this.$root.toastError(
                            "Chưa có thông tin loại cước Visa !"
                          );
                          this.$root.showLoading(false);
                          return;
                        }
                        if (tocdo_id == "") {
                          this.$root.toastError(
                            "Chưa có thông tin về tốc độ Visa !"
                          );
                          return;
                        }
                        if (tttc != "") {
                        await   this.LayDuLieu_TTTC(tttc);
                          if (!await this.KiemTraDuLieu_TTTC()) {
                            this.$root.showLoading(false);
                            return
                          }
                        }
                        if (tocdo_id != tocdo_cu_id) {
                          if (
                          await   this.DoiTocDoADSL(
                              account,
                              parseInt(tocdo_id),
                              ip,
                              parseInt(tucthi),
                              this.tudong,
                              this.giu_ip
                            )
                          ) {
                            this.$root.toastSuccess(
                              "Kích hoạt đổi tốc độ ADSL trên Visa thành công!"
                            );
                          } else {

                            this.$root.toastError(
                              "Có lỗi trong quá trình kích hoạt đổi tốc độ ADSL trên Visa !"
                            );
                            this.$root.showLoading(false);
                            return;
                          }
                        } else {
                          this.$root.toastSuccess(
                            "Kích hoạt đổi tốc độ ADSL trên Visa thành công!"
                          );
                        }

                        if (await   this.ThayDoiHinhThucTT(account,loaicuoc_id,tucthi)) {
                          this.$root.toastSuccess(
                            "Kích hoạt đổi mức cước ADSL trên Visa thành công!"
                          );
                        } else {
                          this.$root.toastError(
                            "Có lỗi trong quá trình kích hoạt đổi mức cước ADSL trên Visa !"
                          );
                          this.$root.showLoading(false);
                          return;
                        }
                        if (tucthi == 0)await  this.changeBillingInfo(tttc);
                      }
                      //#endregion
                      //#region Thay đổi tốc độ thực ADSL
                      else if (
                        kieuld_id == Enum.KieuLapDat.THAYDOI_TOCDOTHUC_ADSL) {
                        let tocdothuc_id_moi = -1;
                        tocdothuc_id_moi = await this.checkMapId_new(hdtb_id,7) 
                        
                        let matocdo_thuc_moi = await this.checkMapId_new(tocdothuc_id_moi,12) 
                        if (await   this.DoiTocDoADSL(account,parseInt(matocdo_thuc_moi),ip,parseInt(tucthi),this.tudong,this.giu_ip)) {
                          this.$root.toastSuccess("Kích hoạt đổi tốc độ thực ADSL trên Visa thành công!" );
                        } else {
                          this.$root.toastError("Có lỗi trong quá trình kích hoạt đổi tốc độ ADSL trên Visa !");
                          
                          this.$root.showLoading(false);
                          return;
                        }
                      }
                      //#endregion
                      if (ip != "" && !this.giu_ip)
                        await this.UPDATE_IP_ADSL(hdtb_id, ip);
                    }
                    else{
                      if (ip != "" && !this.giu_ip)
                        await this.UPDATE_IP_ADSL(hdtb_id, ip);
                    }
                    await this.CAPNHAT_NGAYKH_NGAY_HT_V2(hdtb_id);
                    await this.HienThiDanhSachTB();
                  }
                }
                //#endregion

                //#region MyTv , B2B
                else if (
                  this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV ||
                  this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV_B2B
                ) {
                  let tb_mytv = "";
                  let vmatocdo = "";
                  let tocdo_id = "";
                  let lydo = "";
                  lydo = "Thay đổi tốc độ MyTV";
                  tocdo_id = this.currentItem.tocdo_id;
                  //kiểm tra đồng bộ EPG
                  let ngay_epg =
                    this.$root.context.user.getProperty("ngay_epg");
                  if (ngay_epg == null || ngay_epg == "" ) {
                    this.$root.toastError("Ngày EPG chưa được thiết lập. Liên hệ Admin để giải quyết !");
                    this.$root.showLoading(false);
                    return;
                  }
                  let info = await this.GetPackageEPG(this.account); // Vasc Service
                  console.log('info',info)
                  if(info.data !=null){
                  if (info.data.result != 0) {
                    this.$root.toastError(info.faultString);
                    this.$root.showLoading(false);
                    return;
                  }
                  let dskt = info.data.GetData;
                  if (dskt !=null && dskt.length > 0) {
                    for (let i = 0; i < dskt.length; i++) {
                      let acc = dskt[i].tai_khoan_mytv;
                      let ngayth = DateTimeLib.toDateObject(
                        dskt[i].ngay_thuc_hien
                      );
                      let ktra = await this.KIEMTRA_TB_DONGBO_EPG_VTT(acc, ngayth);
                      if (ktra == 0) {
                        this.$root.toastError("Thuê bao " + acc + " chưa đồng bộ EPG. Phải thực hiện thoái trả hợp đồng !");
                        this.$root.showLoading(false);
                        return;
                      }
                    }
                  }
                }
                else{
                  this.$root.toastError(info.faultString);
                  this.$root.showLoading(false);
                  return;
                }
                  //#region Thay đổi đối tượng trên VASC
                  let dt_moi_id = parseInt(this.currentItem.doituong_id);
                  if (
                   await this.Kiemtra_doituong_vdc(
                      this.hdtb_id,
                      Enum.LoaiHinhTB.INTERNET_MYTV
                    )
                  ) {
                    if (!await this.ThayDoi_NhomKhachHang_UuTien_VASC(this.account,dt_moi_id)) {
                      this.$root.toastError(
                        "Có lỗi trong quá trình thay đổi đối tượng trên Vasc !"
                      );
                      this.$root.showLoading(false);
                      return;
                    }
                  }
                  //#endregion
                  if (kieuld_id == Enum.KieuLapDat.THAYDOI_TOCDO_ADSL) {
                    //Dùng hàm thay đổi TocdoADSL KieuLapDat.THAYDOI_TOCDO_ADSL
                    if (tocdo_id == "") {
                      this.$root.toastError(
                        "Có lỗi trong quá trình kích hoạt đổi tốc độ Mytv trên VASC !"
                      );
                      this.$root.showLoading(false);
                      return;
                    }
                    vmatocdo = await this.checkMapId_new(tocdo_id,12)  
                    tb_mytv = this.account;
                    if (await this.DoiTocDoMyTV(tb_mytv, vmatocdo, lydo)) {
                      //#region Gọi hàm đẩy lại gói với trường hợp gói gia đình
                      try {

                        let dsKtra = await this.Kt_thuebao_goi_giadinh(hdtb_id,this.thuebao_id);
                        if (dsKtra != null && dsKtra.length > 0) {
                          let obj = dsKtra[0];
                          if (this.getValueFromIndex(obj, 0) == "1") {
                            /// Điền lại tên trường
                            let magoi_vasc2 = parseInt(
                              this.getValueFromIndex(obj, 1)
                            );
                            let _dsMytvTiTan =
                            await  this.lay_ds_goi_mytvtitan(hdtb_id);
                            let _Combo_FiberCD = "";
                            if (
                              _dsMytvTiTan[0].ma_goi_vasc != null &&
                              _dsMytvTiTan[0].ma_goi_vasc != ""
                            ) {
                              magoi_vasc2 = parseInt(
                                _dsMytvTiTan[0].ma_goi_vasc
                              );
                              _Combo_FiberCD = _dsMytvTiTan[0].combor_fiber;
                            }
                            if (magoi_vasc2 != -1) {
                              if (
                              await  this.ChangePackageAndMegaVNN(
                                  tb_mytv,
                                  vmatocdo,
                                  "",
                                  "",
                                  magoi_vasc2,
                                  "",
                                  _Combo_FiberCD
                                ) != 0
                              ) {
                                this.$root.toastError(
                                  "Có lỗi trong quá trình kích hoạt gói tích hợp trên VASC"
                                );
                                return;
                              }
                            }
                          }
                        }
                      } catch (e) {
                        console.log(e)
                      }
                      //#endregion
                      this.$root.toastSuccess(
                        "Kích hoạt đổi tốc độ MyTV trên VASC thành công!"
                      );

                      //let kt_mytv_moi = await this.kt_mytv_moi(hdtb_id);
                      // ERP báo bỏ phần này đi
                      // console.log('kt_mytv_moi',kt_mytv_moi)
                      // if (kt_mytv_moi!=null && kt_mytv_moi.startsWith("ok")) {
                      //   let kq = await this.STB_Rent_UpdateInfo(
                      //     tb_mytv,
                      //     kt_mytv_moi.split(";")[1]
                      //   );
                      //   if ( kq[0] != "1") {
                      //     this.$root.toastError(kq[1]);
                      //     this.$root.showLoading(false);
                      //     return;
                      //   }
                      // }
                    } else {
                      this.$root.toastError(
                        "Có lỗi trong quá trình đổi tốc độ MyTV trên VASC !"
                      );
                      this.$root.showLoading(false);
                      return;
                    }
                  } else if (
                    kieuld_id == Enum.KieuLapDat.THAYDOI_KIEUTRA_MYTV
                  ) {
                    let hinhthuc = "";
                    let thang = "";
                    let ngay = "";
                    let ds_con ;
                    ds_con =await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
                    //tchopdong.Dispose()
                    hinhthuc = ds_con.kieutra_id;
                    if (ds_con.thang_id != "") {
                      thang = await this.checkMapId_new(ds_con.thang_id,14)
                      if (thang == "-1") thang = "";
                    }
                    if (ds_con.ngaytra != "")
                      ngay = DateTimeLib.toDateDisplay(ds_con.ngaytra);

                    if (hinhthuc == "") {
                      this.$root.toastError(
                        "Có lỗi trong quá trình kích hoạt đổi tốc độ Mytv trên VASC !"
                      );
                      this.$root.showLoading(false);
                      return;
                    }
                    tb_mytv = this.account;
                    //Hiếu sửa bắt lỗi loại cước id ngày 04/09/2010

                    if (await this.DoiKieuTraMyTV(tb_mytv, hinhthuc, thang, ngay)) {
                      this.$root.toastError(
                        "Kích hoạt đổi tốc độ MyTV trên VASC thành công!"
                      );
                    } else {
                      this.$root.toastError(
                        "Có lỗi trong quá trình đổi tốc độ MyTV trên VASC !"
                      );
                      this.$root.showLoading(false);
                      return;
                    }
                    //Update lai status
                    //lhd.CAPNHAT_STATUS_HDTB(hdtb_id, TRANGTHAI_DONGBO.DONGBO_CM)
                  }
                 await this.CAPNHAT_NGAYKH_NGAY_HT_V2(hdtb_id);
                await  this.HienThiDanhSachTB();
                }
                //#endregion

                // #region SaiGonTV
                else if (loaitb_id == Enum.LoaiHinhTB.INTERNET_SGTV) {
                  let tb_sgtv = "";
                  let vmatocdo = "";
                  let vtocdo_id = 0;
                  if (this.currentItem.tocdo_id == "") {
                    this.$root.toastError("Lỗi tốc độ SaiGonTV");
                    this.$root.showLoading(false);
                    return;
                  }

                  vtocdo_id = parseInt(this.currentItem.tocdo_id);
                  vmatocdo = await this.checkMapId_new(vtocdo_id,12)
                  tb_sgtv = this.account;
                  let result =await this.set_services_for_customer(
                    tb_sgtv,
                    vmatocdo,
                    ""
                  );
                  if (result == "0") {
                    this.$root.toastSuccess(
                      "Kích hoạt thay đổi tốc độ SaiGonTV thành công !"
                    );
                    await this.CAPNHAT_NGAYKH_NGAY_HT_V2(hdtb_id);
                  } else {
                    this.$root.toastError(result);
                  }
                  await this.HienThiDanhSachTB();
                }
                //#endregion

                //#region HTVC_FIBERVNN
                else if (
                  loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_ANALOG ||
                  loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_DIGITAL
                ) {
                  await this.CAPNHAT_NGAYKH_NGAY_HT_V2(hdtb_id);
                  await this.HienThiDanhSachTB();
                }else{
                  this.$root.toastError("Loại thuê bao này chưa được hỗ trợ. Liên hệ Admin để được xử lý.")
                  this.$root.showLoading(false);
                }
                //#endregion
              } else {
                this.$root.showLoading(false);
                await this.CAPNHAT_NGAYKH_NGAY_HT_V2(hdtb_id);
                await this.HienThiDanhSachTB();

              }
            })
            .catch((e) => {
              this.$root.showLoading(false);
              console.log(e);
            this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ!");});
        }
        //#endregion

        //#region Thay đổi dịch vụ Internet
        else if (this.loaihd_id == Enum.LoaiHopDong.THAY_DOI_DV) {
          let s = "";
          if (
            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
            this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER    ||
            this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
          )
            s = "Visa";
          else if (
            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV ||
            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV_B2B
          )
            s = "Vasc";
          else if (
            this.loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_ANALOG ||
            this.loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_DIGITAL
          )
            s = "HTV";
          else s = "SGTV";
          //#region "Kich hoat len VDC, VASC"
          this.$confirm(
            `Bạn có chắc chắn muốn kích hoạt dịch vụ này lên ` +
              s +
              ` hay không?`,
            "Kích hoạt dịch vụ",
            {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
            }
          )
            .then(async () => {
               this.$root.showLoading(true);
              let kieu_mega = "1";
              if (
                this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
                this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
                this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER    ||
                this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
              ) {
                let ds_mega;
                ds_mega = await  this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
                if (ds_mega != null)
                  kieu_mega = await this.checkMapId_new(ds_mega.tocdo_id,5)
              console.log('kieu_mega',kieu_mega)
              }
               console.log('0')              
              //CƯờng thấy dài quá phải region lại chóng mặt quá
              if (!this.dongbo_test && kieu_mega != "0") {
                try {
                  let thaydoidv_b2b = false;
                  //Dich vu vien thong la ADSL
                  for (let i = 0; i < this.lvwDVGT.length; i++) {
                    let item = this.lvwDVGT[i];
                    console.log('lvwDVGT',this.lvwDVGT)
                    //Kiểu yc=1: đăng ký,
                    if (
                      item.kieu_yc == "1" && parseInt(item.dichvugt_id) == Enum.DichVuGiaTang.TAMDUNG_INTERNET) {
                      //#region Đăng ký
                     // try {
                        //Kiểm tra thuê bao đã được chăm sóc hay chưa //Y/c Chăm sóc khách hàng VNP - 8004
                        if (this.nhantin_nhanvien_diaban == 1) {
                          if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH) {
                            let kq = await  this.kiemtra_tb_cs_roimang(this.hdtb_id);
                            console.log('kq',kq)
                            if (kq == "0") {
                              //=1 1: Nếu thuê bao đã được cập nhật thông tin chăm sóc hoặc quá 1 ngày từ lúc lập hợp đồng
                              this.$root.toastError(
                                "Thuê bao chưa được nhân viên địa bàn cập nhật thông tin chăm sóc !"
                              );
                              this.$root.showLoading(false);
                              return;
                            }
                          }
                        }
                        //Thue bao MegaVNN va FTTH
                        if (
                          this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
                          this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
                          this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER ||
                          this.loaitb_id == Enum.LoaiHinhTB.INTERNET_1260 ||
                          this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
                        ) {
                          let account = this.account;
                          let lydohuy = await this.lay_lydo_huy(this.hdtb_id)
                          let chuthich = item.ten_dvgt +" thuê bao " + account +" " +this.$root.token.getUserName() 
                          +" thực hiện ngày " +DateTimeLib.toDateDisplay(new Date());

                          this.async_status = await  this.ThayDoiTrangThai_1(account,"LOCK",lydohuy,chuthich);
                        }
                        //async_status = true
                        //Thue bao MyTV
                        if (
                          this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV ||
                          this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV_B2B
                        ) {
                          let account = this.account;                     
                          let dt_ldh = await  this.Lay_DS_LyDoHuy_HDTB(this.hdtb_id);
                          let lydohuy_id = parseInt(dt_ldh[0].lydohuy_id_vasc);
                          let kq;
                          kq = await  this.SuspendSubscriberV2_2(account, lydohuy_id);
                          //Lamnt: Bô sung thêm kiểm tra trạng thái VASC, đúng tạm dừng thì mới trả thành công
                          let info = await  this.getSubscriberInfo(account);
                          if (kq == 0 && info.statusID == "3") {
                            this.async_status = true;
                          } else {
                            this.$root.showLoading(false);
                            return;
                          }
                        }
                        if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_SGTV) {
                          // TuanNA: thêm code kích hoạt
                          /// ServiceIDs">Mã tốc độ SaiGonTV, nếu là thay đổi tốc độ thì truyền mã tốc độ mới</param>
                          /// startDate">Nếu tạm dừng/khóa thì truyền = ngày sử dụng thuê bao, mở thì để trống, nếu đổi tốc độ thì truyền = ngày hiện tại</param>
                          /// endDate">Nếu tạm dừng/khóa thì truyền = ngày hiện tại - 1, mở - thay đổi tốc độ thì để trống</param>
                          let ServiceIDs = ""; //matocdo
                          let startDate = "";
                          let endDate = "";
                          endDate = DateTimeLib.toDateDisplay(
                            new Date().AddDays(-1)
                          );
                          let account = this.account;

                          let ds =await  this.lay_matocdo_by_hdtb_id(this.hdtb_id);
                          if (ds.length > 0) {
                            //Ngày sd
                            if (ds.ngay_sd != "") {
                              startDate = DateTimeLib.toDateDisplay(ds.ngay_sd);
                            } else {
                              this.$root.toastError(
                                "Không tìm thấy ngày sử dụng đối với thuê bao: " +
                                  account
                              );
                              return;
                            }
                            //matocdo
                            if (ds.matocdo != "") {
                              ServiceIDs = ds.matocdo;
                            } else {
                              this.$root.toastError("Không tìm thấy mã tốc độ đối với thuê bao: " + account);
                              return;
                            }
                          } else {
                            this.$root.toastError(
                              "Không tìm thấy mã tốc độ đối với thuê bao: " + account);
                            return;
                          }
                          let sgtv;
                          let kq;
                          kq = await  this.set_services_for_customer(account,ServiceIDs, startDate,endDate);
                          if (kq == "0") {
                            this.async_status = true;
                          } else {
                            this.$root.toastError(kq);
                            this.$root.showLoading(false);
                            return;
                          }
                        }
                        if (
                          this.loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_ANALOG ||
                          this.loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_DIGITAL ||
                          this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FIBER_TINH_KHAC
                        ) {
                          this.async_status = true;
                        }
                        if (this.async_status) {
                          let sKQReset = await this.DoKiem(this.account,1);
                          if (sKQReset != "") {
                            this.$root.toastSuccess(sKQReset);
                          }
                          if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV) {
                            this.$root.toastSuccess("Kích hoạt dịch vụ thành công lên VASC !");
                          }
                          if (
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
                            this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER ||
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_1260 ||
                            this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
                          ) {
                            this.$root.toastSuccess(
                              "Kích hoạt dịch vụ thành công lên VDC !"
                            );
                          }
                          if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_SGTV) {
                            this.$root.toastSuccess(
                              "Kích hoạt dịch vụ SGTV thành công !"
                            );
                          }
                         await   this.CAPNHAT_NGAYKH_NGAY_HT_V2(this.hdtb_id);
                        } else {
                          this.$root.toastError("Kích hoạt thuê bao " + this.account +" không thành công");
                        }
                      // }
                      //  catch (ex) {
                      //    this.$root.showLoading(false);
                      //    console.log(ex)
                      //   this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ !");
                      //   return;
                      // }
                      //#endregion
                    } else if (
                      item.kieu_yc == "0" &&
                      parseInt(item.dichvugt_id) == Enum.DichVuGiaTang.TAMDUNG_INTERNET
                    ) {
                      //#region Huỷ
                    //  try {
                        console.log('12')
                        //Thue bao MegaVNN va FTTH
                        if (
                          this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
                          this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
                          this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER ||
                          this.loaitb_id == Enum.LoaiHinhTB.INTERNET_1260 ||
                          this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
                        ) {
                          let account = this.account;
                          let lydohuy = item.ten_dvgt + " thuê bao " + account;
                          let chuthich =
                            item.ten_dvgt + " thuê bao " +account +
                            this.$root.token.getUserName() +
                            " thực hiện ngày " +
                            DateTimeLib.toDateDisplay(new Date());
        
                          this.async_status = await  this.ThayDoiTrangThai(
                            account,
                            "ACTIVE",
                            "0",
                            chuthich
                          );
                          console.log('async_status',this.async_status)
                        }
                        //Thue bao MyTV
                        if (
                          this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV ||
                          this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV_B2B
                        ) {
                          let account = this.account;
                          let lydohuy =
                            "Huỷ " + item.ten_dvgt + " thuê bao " + account;

                          let kq;
                          kq = await  this.ResumeSubscriber(account, lydohuy);
                          if (kq == 0) {
                            this.async_status = true;
                          } else {
                            this.$root.showLoading(false);
                            return;
                          }
                        }
                        if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_SGTV) {
                          // TuanNA: thêm code kích hoạt
                          /// ServiceIDs">Mã tốc độ SaiGonTV, nếu là thay đổi tốc độ thì truyền mã tốc độ mới</param>
                          /// startDate">Nếu tạm dừng/khóa thì truyền = ngày sử dụng thuê bao, mở thì để trống, nếu đổi tốc độ thì truyền = ngày hiện tại</param>
                          /// endDate">Nếu tạm dừng/khóa thì truyền = ngày hiện tại - 1, mở - thay đổi tốc độ thì để trống</param>
                          let ServiceIDs = ""; //matocdo
                          let startDate = "";
                          let endDate = "";
                          let account = this.account;
                          let ds = await  this.lay_matocdo_by_hdtb_idv2(this.hdtb_id);
                          if (ds != null) {
                            if (ds.matocdo != "") {
                              ServiceIDs = ds.matocdo;
                            } else {
                              this.$root.toastError(
                                "Không tìm thấy mã tốc độ đối với thuê bao: " +
                                  account
                              );
                              this.$root.showLoading(false);
                              return;
                            }
                          } else {
                            this.$root.toastError(
                              "Không tìm thấy mã tốc độ đối với thuê bao: " +
                                account
                            );
                            this.$root.showLoading(false);
                            return;
                          }

                          let kq;
                          kq = await this.set_services_for_customer(
                            account,
                            ServiceIDs,
                            startDate,
                            endDate
                          );
                          if (kq == "0") {
                            this.async_status = true;
                          } else {
                            this.$root.toastError(kq);
                            this.$root.showLoading(false);
                            return;
                          }
                        }
                        if (
                          this.loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_ANALOG ||
                          this.loaitb_id == Enum.LoaiHinhTB.HTVC_FIBERVNN_DIGITAL
                        ) {
                          this.async_status = true;
                        }
                        //Nếu đồng bộ lên Visa thành công, cập nhật ngày hoàn thành vào cơ sở dữ liệu
                        if (this.async_status) {
                          if (
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV ||
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV_B2B
                          ) {
                            this.$root.toastSuccess("Kích hoạt dịch vụ thành công lên VASC !" );
                          }
                          if (
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
                            this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER ||
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_1260 ||
                            this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
                          ) {
                            this.$root.toastSuccess("Kích hoạt dịch vụ thành công lên VDC !");
                          }
                          if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_SGTV) {
                            this.$root.toastSuccess("Kích hoạt dịch vụ SGTV thành công !");
                          }
                        await   this.CAPNHAT_NGAYKH_NGAY_HT_V2(this.hdtb_id);
                        } else {
                          this.$root.toastError(
                            "Kích hoạt thuê bao " + this.account +" không thành công");
                        }
                      // } catch (Exception) {
                      //   this.$root.toastError("Có lỗi xảy ra");
                      //   return;
                      // }
                      //#endregion
                    }

                    // hoangpkn : 28/07/2015
                    else if (item.dichvugt_id == Enum.DichVuGiaTang.GREEN_ONLINE) {
                       console.log('13')
                      //#region Greeen
                      if (item.kieu_yc == "1") {
                        // đăng ký GreenOnline
                        //TuanNA : Thêm code đồng bộ Green online trong trường hợp tạm dừng thuê bao
                        //Kiểm tra nếu thuê bao đang sử dụng GreenOnline => mới cho đăng ký lại
                        let vthuebao_id = await this.checkMapId_new(this.hdtb_id,8)
                          
                        let green_svr;
                        let account = this.account;
                        let flag = "O";
                        let user = this.$root.token.getUserName();
                        let acc_id = hdtb_id;
                        let province = this.ten_tat_ccbs;
                        let promotioncode = 0;                        
                        let khuyenmai_id = await this.checkMapId_new(vthuebao_id,17)                       
                        if (khuyenmai_id != "-1") {
                          promotioncode = parseInt(khuyenmai_id);
                        }
                        let unit = 0;
                        let thanhtoan_id = await this.checkMapId_new(vthuebao_id,15) 
                        
                        //let donvitt_id = parseInt(this.checkMapId("donvi_id", DatabaseConstants.DB2 + ".db_thanhtoan", "where thanhtoan_id = " + thanhtoan_id))
                        let dvql_tt_id =await this.checkMapId_new(thanhtoan_id,16)  
                        if (dvql_tt_id != "-1") {
                          unit = parseInt(dvql_tt_id);
                        }
                        let db_khachhang = await this.lay_thongtin_dbkhachhang(vthuebao_id);
                        let name ="";
                        let address ="";
                        let phone ="";
                        let email = "";
                        if (db_khachhang!=null){
                          name = db_khachhang.ten_kh || "";
                          address = db_khachhang.diachi_kh || "";
                          phone = db_khachhang.so_dt || "";
                          email = db_khachhang.email || "";
                        }
                        let kq = 0;
                        kq = await this.DangKyV2(account, flag, user, acc_id, province, promotioncode, unit,name,address,phone,email)
                        if (kq != 1) {
                          this.$root.toastError(
                            "Có lỗi trong quá trình đăng ký dịch vụ GreenOnline trên hệ thống!"
                          );
                          this.$root.showLoading(false);
                          return;
                        } else {
                          if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV) {
                            this.$root.toastSuccess(
                              "Kích hoạt dịch vụ thành công lên VASC !"
                            );
                          }
                          if (
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
                            this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER ||
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_1260 ||
                           this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
                          ) {
                            this.$root.toastSuccess(
                              "Kích hoạt dịch vụ thành công lên VDC !"
                            );
                          }
                          if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_SGTV) {
                            this.$root.toastSuccess(
                              "Kích hoạt dịch vụ SGTV thành công !"
                            );
                          }
                         await  this.CAPNHAT_NGAYKH_NGAY_HT_V2(this.hdtb_id);
                        }

                        //End TuanNA
                      } //Hủy GreenOnline
                      else {
                        //TuanNA : Thêm code đồng bộ Green online trong trường hợp tạm dừng thuê bao
                        //Kiểm tra nếu thuê bao đang sử dụng GreenOnline => mới cho hủy
                        let vthuebao_id = await this.checkMapId_new(this.hdtb_id,8)   
                        let green_svr;
                        //account=>account Mega</param>
                        //flag=>'O': đăng ký, 'C': Hủy, 'S': tạm ngưng (sau đó có thể mở lại), 'R': mở lại sau khi tạm ngưng, 'T': ngưng do nợ cước</param>
                        //user=>user thực hiện - truyền tt_nd.ma_nd</param>
                        //acc_id=>truyền hdtb_id</param>
                        //province=>tt_nd.tentat_ccbs</param>
                        //promotioncode=>mã khuyến mại, không có truyền 0, trường hợp hủy promotioncode = 0</param>
                        //unit=>đơn vị quản lý địa chỉ thanh toán</param>
                        let account = this.account;
                        let flag = "C";
                        let user = this.$root.token.getUserName();
                        let acc_id = this.hdtb_id;
                        let province =  this.ten_tat_ccbs;
                        let promotioncode = 0;
                       
                        let unit = 0;
                        let thanhtoan_id =  await this.checkMapId_new(vthuebao_id,15)   
                        let dvql_tt_id =  await this.checkMapId_new(thanhtoan_id,16)                      
                        if (dvql_tt_id != "-1") {
                          unit = parseInt(dvql_tt_id);
                        }
                        let db_khachhang = await this.lay_thongtin_dbkhachhang(vthuebao_id);
                        let name ="";
                        let address ="";
                        let phone ="";
                        let email = "";
                        if (db_khachhang!=null){
                          name = db_khachhang.ten_kh || "";
                          address = db_khachhang.diachi_kh || "";
                          phone = db_khachhang.so_dt || "";
                          email = db_khachhang.email || "";
                        }
                        let kq = await this.DangKyV2(account, flag, user, acc_id, province, promotioncode, unit,name,address,phone,email)
                      
                        if (kq != 1) {
                          this.$root.toastError(
                            "Có lỗi trong quá trình hủy dịch vụ GreenOnline trên hệ thống!"
                          );
                          this.$root.showLoading(false);
                          return;
                        } else {
                          if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV) {
                            this.$root.toastSuccess(
                              "Kích hoạt dịch vụ thành công lên VASC !"
                            );
                          }
                          if (
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
                            this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER ||
                            this.loaitb_id == Enum.LoaiHinhTB.INTERNET_1260 ||
                            this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
                          ) {
                            this.$root.toastSuccess(
                              "Kích hoạt dịch vụ thành công lên VDC !"
                            );
                          }
                          if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_SGTV) {
                            this.$root.toastSuccess(
                              "Kích hoạt dịch vụ SGTV thành công !"
                            );
                          }
                        await   this.CAPNHAT_NGAYKH_NGAY_HT_V2(this.hdtb_id);
                        }
                        //End TuanNA
                      }
                      //#endregion
                    }

                    if (
                      this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV &&
                      this.loaitb_id == Enum.LoaiHopDong.THAY_DOI_DV
                    ) {
                        console.log('14')
                      //#region cái nàY LÀM gì đó
                      // chuyển DB
                      let tocdo_id = await this.lay_tocdo_id(this.hdtb_id)
                      let vmatocdo = await this.checkMapId_new(tocdo_id,12)   
                      let account = this.account
                      let _addOnCD = "0";
                      let _megaMytv = "0";

                      let _dt = await this.Kiemtra_dk_dvgt_th_faf(this.hdtb_id);
                      if (_dt != null && _dt.length > 0) {
                        let _strOutMgs = "";
                        if (_dt.length > 1) {
                          this.$root.toastError(
                            "Bạn không được chọn 2 dịch vụ gia tăng khác nhóm"
                          );
                          this.$root.showLoading(false);
                          return;
                        } else {
                          let ss = [];
                          ss.push(_addOnCD);
                          for (let i; i < _dt.length; i++) {
                            if (await this.getValueFromIndex(_dt[i], 1) == "1")
                              ss.push(this.getValueFromIndex(_dt[i], 0));
                          }
                          _addOnCD = ss.join(",");
                        }
                        if (_addOnCD == "") _addOnCD = "0";
                        
                        let obj_GetSubscriberInfoV2 = await  this.GetSubscriberInfoV2(
                          account,
                          "",
                          _megaMytv
                        );
                        if (obj_GetSubscriberInfoV2 != null)
                          _megaMytv = obj_GetSubscriberInfoV2[0].MegaMytv;
                          let obj_ChangePackageV3 = await  this.ChangePackageV3(
                          account,
                          vmatocdo,
                          "",
                          _megaMytv,
                          _addOnCD,
                          _strOutMgs
                        );
                        // if (obj_ChangePackageV3._strOutMgs != null)
                        //   _strOutMgs = obj_ChangePackageV3._strOutMgs;
                        if (obj_ChangePackageV3 == 0) {
                          this.$root.toastSuccess(
                            "Kích hoạt thuê bao " + this.account + " thành công"
                          );
                        } else {
                          let _kieu_yc = item.kieu_yc;
                          let ss = [];
                          ss.push(_addOnCD);
                          for (let i; i < _dt.length; i++) {
                            if (_dt[i].dichvugt_id == item.dichvugt_id)
                              ss.push(this.getValueFromIndex(_dt[i], 0));
                          }
                          _addOnCD = ss.join(",");
                         
                          if (
                         await    this.TotalMyTVPlus(
                              account,
                              _kieu_yc,
                              _addOnCD,
                              ""
                            ) == 0
                          ) {
                            this.$root.toastSuccess(
                              "Kích hoạt thuê bao " +
                                this.account +
                                " thành công"
                            );
                          } else {
                            this.$root.toastError(
                              "Kích hoạt thuê bao " +
                                this.account +
                                " không thành công"
                            );
                            this.$root.showLoading(false);
                            return;
                          }
                        }
                       
                      }

                      //dungpa - them kich hoat PayTV trọn gói giá 20.000đ

                      let _ds = await  this.kt_dangky_paytv(this.hdtb_id);
                      if (_ds != null && _ds.length > 0) {
                        let _resultPayTV = await this.PayTVFull_Register(this.account,parseInt(_ds.thang));
                        if (_resultPayTV == 0 || _resultPayTV == 23) {
                          this.$root.toastSuccess(
                            "Kích hoạt " + _ds.kieu_yc + " PayTV thành công!"
                          );
                        } else {
                          this.$root.showLoading(false);
                          return;
                        }
                      }
                      //end dungpa

                      //#region Kích hoạt Combo-Fafilm
                      _dt = await  this.Kiemtra_dk_combo_fafilm(hdtb_id);
                      if (_dt != null && _dt.length > 0) {
                        if (_dt.length > 1) {
                          this.$root.toastError(
                            "Bạn không được chọn đồng thời 2 combo Fafilm!"
                          );
                          this.$root.showLoading(false);
                          return;
                        } else {
                          let _ComboFAFMonthFree = 0;
                          if (_dt[0].kieu_yc == "1") {
                            //Đăng ký Fafilm
                            if (_dt[0].ma_dvgt == "CB_FF_12")
                              _ComboFAFMonthFree = 2;
                            else if (_dt[0].ma_dvgt == "CB_FF_24")
                              _ComboFAFMonthFree = 6;
                          }

                          if (
                          await   this.ComboFAF_Update(
                              account,
                              _ComboFAFMonthFree,
                              _dt[0].kieu_yc
                            ) == 0
                          ) {
                            this.$root.toastSuccess(
                              "Kích hoạt khuyến mại Combo-Fafilm thành công!"
                            );
                          }
                        }
                      }
                      //#endregion

                   await    this.CAPNHAT_NGAYKH_NGAY_HT_V2(hdtb_id);
                      //#endregion
                    }
                    if (
                      this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV_B2B &&
                      parseInt(item.dichvugt_id) != Enum.DichVuGiaTang.TAMDUNG_INTERNET
                    ) {
                      console.log('15')
                      thaydoidv_b2b = true;
                      //#region B2b
                     // try {
                        let ds_ttct = await this.TTCT_DichVuGT(item.dichvugt_id);
                        let thongbaoDHSX =
                          ds_ttct.goi_vasc + " - " + ds_ttct.ten_dvgt;
                        let HOTEL_CODE = this.thongTinKH.ma_kh;
                        let ROOM_CODE = this.account;
                        let USERNAME = this.account;
                        let REGISTDATE = "";
                        let CANCELDATE = "";
                        let ADDON_CODE = ds_ttct.goi_vasc;
                        let UNITPRICE = 0;
                        if (item.kieu_yc == "1") {
                          // Đăng ký
                          REGISTDATE = DateTimeLib.toDateDisplay(new Date());
                          CANCELDATE = "";
                          let kq = await this.B2B_Room_RegistAddOn(
                            HOTEL_CODE,
                            ROOM_CODE,
                            USERNAME,
                            REGISTDATE,
                            CANCELDATE,
                            ADDON_CODE,
                            UNITPRICE,
                            thongbaoDHSX
                          );
                          if (kq != 0 && kq != -3) {
                            this.$root.showLoading(false);
                            return;
                          }
                        } else {
                          REGISTDATE = "";
                          CANCELDATE = DateTimeLib.toDateDisplay(new Date());
                          let kq =await  this.B2B_Room_CancelAddOn(
                            HOTEL_CODE,
                            ROOM_CODE,
                            USERNAME,
                            REGISTDATE,
                            CANCELDATE,
                            ADDON_CODE,
                            UNITPRICE,
                            thongbaoDHSX
                          );
                          if (kq != 0 && kq != -3) {
                            this.$root.showLoading(false);
                            return;
                          }
                        }
                      // } catch (ex) {
                      //   this.$root.toastError("[B2B ERROR: " + ex);
                      //   return;
                      // }

                      //#endregion
                    
                    }
                  }
                  if (
                    thaydoidv_b2b &&
                    this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV_B2B
                  ) {
                    console.log('15')
                    // Nếu đến đc đây là chả có lỗi gì kich hoạt luôn
                  this.$root.toastSuccess("Kích hoạt dịch vụ thành công lên VASC (B2B) !");
                  await   this.CAPNHAT_NGAYKH_NGAY_HT_V2(this.hdtb_id);
                  }
                  console.log('ket thuc')
                  await this.HienThiDanhSachTB();
                } catch (ex) {
                  console.log(ex)
                  this.$root.showLoading(false);
                  this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ!");
                }
              } else {
                await   this.CAPNHAT_NGAYKH_NGAY_HT_V2(hdtb_id);            
                this.$root.toastSuccess("Đồng bộ dữ liệu thành công!");
                await this.HienThiDanhSachTB();
              }
            })
            .catch((e) => {
            console.log(e);
            this.$root.showLoading(false);
            //throw(e);
            this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ!");
            });
          //#endregion
        }
        //#endregion

        //#region Đổi Account Internet
        else if (this.loaihd_id == Enum.LoaiHopDong.DOISO_TB) {
          this.$confirm(
            `Bạn có chắc chắn kích hoạt Account : "` +
              this.account +
              ` lên Visa hay không?`,
            "Kích hoạt dịch vụ",
            {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
            }
          )
            .then(async () => {
               this.$root.showLoading(true);
              if (
                this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
                this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER ||
                this.loaitb_id == Enum.LoaiHinhTB.INTERNET_ADSL ||
                this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH
              ) {
                let kieu_mega = "1";
                let ds_mega;
                ds_mega = await  this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtb_id);
                if (ds_mega != null)
                  kieu_mega = await this.checkMapId_new(ds_mega.tocdo_id,5)

                if (!this.dongbo_test && kieu_mega != "0") {
                  if (await this.Doi_Account(this.txtAccountCu, this.account)) {
                   await  this.CAPNHAT_NGAYKH_NGAY_HT_V2(this.hdtb_id);
                    this.$root.toastSuccess("Kích hoạt đổi Account trên Visa thành công!");
                  }
                } else {
                await   this.CAPNHAT_NGAYKH_NGAY_HT_V2(this.hdtb_id);
                  this.$root.toastSuccess("Kích hoạt đổi Account thành công!");
                }
                await this.HienThiDanhSachTB();
              }
            })
            .catch((e) => {
            console.log(e);
            this.$root.showLoading(false);
            this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ!");
            });
        }
        //#endregion

        //#region Thanh lý Internet
        else if (loaihd_id == Enum.LoaiHopDong.CHAMDUT_SD) {
          if (!this.dongbo_test && loaitb_id != Enum.LoaiHinhTB.WIFI_MESH && loaitb_id != Enum.LoaiHinhTB.WIFI_MESH_TM) {
            let lst_chon = this.hdtb_ids; 
            if (lst_chon.length == 0) {
              this.$root.toastError("Hãy chọn phiếu để kích hoạt hủy thuê bao");
              return;
            }

            //Kiểm tra loại hình thuê bao
            //Nếu là MegaVnn va MyTV thì cho kick hoạt
            //Nếu là FTTH thì kiểm tra xem thuê bao đó có thuộc gói Fiber hay ko, nếu thuộc thì mới được kick hoạt
            else if (lst_chon.length == 1) {
              let thuebao_id;
              thuebao_id = await this.checkMapId_new(hdtb_id,8);
              if (loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
                  loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER ) {
                if ((await this.KIEMTRA_KICHHOAT_VISA(thuebao_id)) == 0) {
                  this.$root.toastSuccess(
                    "Thuê bao " +
                      this.account +
                      " không thuộc gói Fiber. \nBạn không cần kích hoạt trên Visa !"
                  );
                  this.$root.showLoading(false);
                  return;
                }
              }

              if (
                loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV ||
                loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV_B2B
              ) {
                let ngay_epg = this.$root.context.user.getProperty("ngay_epg")
                if (ngay_epg == null || ngay_epg == "" ) {
                  this.$root.toastError(
                    "Ngày EPG chưa được thiết lập. Liên hệ Admin để giải quyết !"
                  );
                  this.$root.showLoading(false);
                  return;
                }
                let info = await this.GetPackageEPG(this.account)
                if(info.data != null){
                if (info.data.result != 0) {
                  this.$root.toastError(info.errordesc);
                  return;
                }
                let dskt;
                dskt = info.data.GetData;
                if (dskt !=null && dskt.length > 0) {
                  for (let i = 0; i < dskt.length; i++) {
                    let acc = dskt[i].tai_khoan_mytv;
                    let ngayth = DateTimeLib.toDateObject(dskt[i].ngay_thuc_hien);
                    let ktra = await this.KIEMTRA_TB_DONGBO_EPG_VTT(acc, ngayth);
                    if (ktra == 0) {
                      this.$root.toastError(
                        "Thuê bao " +
                          acc +
                          " chưa đồng bộ EPG. Phải thực hiện thoái trả hợp đồng !"
                      );
                      return;
                    }
                  }
                }
                }
                // end hoàng thêm
              }
            }
            this.$confirm(
              `Bạn có chắc chắn hủy các Account lên Visa/Vasc/SaiGonTV hay không`,
              "Hủy dịch vụ",
              {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
              }
            )
              .then(async () => {
                 this.$root.showLoading(true);
                let kiemtra = true;
                let ds = this.$refs['grid-thuebao'].$refs.grid.getSelectedRecords()
                for (let i = 0;i < ds.length;i++) {
                  let account = ds[i].ma_tb; 
                  let lydohuy = "Hủy Account";
                  let chuthich = "Hủy Account";
                  let in_loaitb_id = parseInt(ds[i].loaitb_id);
                  let in_hdtb_id = parseInt(ds[i].hdtb_id);
                  let kt = await this.KichHoat_ThanhLy( account, lydohuy, chuthich, in_loaitb_id, in_hdtb_id)
                  if (!kt) {
                    kiemtra = false;
                    this.$root.toastError(
                      "Có lỗi kích hoạt thuê bao " + account
                    );
                    //break;
                    continue;
                  }
                }
                if (kiemtra)
                  this.$root.toastSuccess("Đã cập nhật dữ liệu thành công!");
                await this.HienThiDanhSachTB();
              })
              .catch((e) => {
               console.log(e);
            this.$root.showLoading(false);
            this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ!");
              });

            //if (async_status) btnHoanCong.Enabled = true;
          }
          else {
           await this.CAPNHAT_NGAYKH_NGAY_HT_V2(hdtb_id);
           await this.HienThiDanhSachTB();
          this.$root.toastSuccess(
            "Đã cập nhật dữ liệu thành công!"
          );
        }
      } 
        //#endregion
      } catch (e) {
        this.$root.showLoading(false);
        this.$root.toastError("Có lỗi xảy ra trong quá trình đồng bộ!");
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    lay_tocdo_id: async function(hdtb_id){
    try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_tocdo_id/"+hdtb_id
        );
        return rs.data
      } catch (error) {
      } finally {
      }
    },
    lay_lydo_huy: async function(hdtb_id){
    try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_lydo_huy/"+hdtb_id
        );
        if( rs.data!=null &&  rs.data.length>0)
        return rs.data[0].lydohuy_id_vdc.toString()
      } catch (error) {
        return "-1"
      } 
    },
    //#region Hàm lấy dữ liệu
    getDsHopDongPL: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_loai_hopdong"
        );
        this.dsHDPL = rs.data;
        if (rs.data != null && rs.data.length > 0) {
          this.loaihd_id = this.dsHDPL[0].loaihd_id;
          this.Lay_TrangThai_HD();
          this.onChangeLoaiHD();
        }
      } catch (error) {
      } 
    },
    getDsQuyTrinh: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_quytrinh",
          {
            loai_hd_id: this.loaihd_id,
            dichvu_vt_id: this.dichvuvt_id,
          }
        );
        this.dsQuyTrinh = rs.data;
        if (rs.data != null && rs.data.length > 0) 
        this.quytrinh_id = this.dsQuyTrinh[0].quytrinh_id;
      } catch (error) {
        console.log(error)
      } 
    },

    maGD_keyPress: async function (event) {
      if (event.code == "Enter") {
        let madg = this.thongTinKH.ma_gd == "" ? "0" : this.thongTinKH.ma_gd;
        await this.Lay_ds_hdtb_gd_kichhoat_madg(madg);
        await this.getHienThiNutBam()
      }
    },
    Lay_ds_hdtb_gd_kichhoat_madg: async function (ma_dg) {
      try {
        this.$root.showLoading(true);
        this.clearForm();
        let loai_tbi  = 0;
        
        let data = {
          loai_hd_id: this.loaihd_id,
          dichvu_vt_id: this.dichvuvt_id,
          tthd_id: this.tthd_id, //1 để có data view
          status: this.status,
          loaitbi_id: loai_tbi,
          quytrinh_id: this.quytrinh_id,
          ma_dg: ma_dg,
        };
        console.log('Lay_ds_hdtb_gd_kichhoat_madg: ',data)
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_hdtb_gd_kichhoat_madg",
          data
        );
          console.log('rs.data',rs.data)
          this.dsThueBao = rs.data;
          if(rs.data!=null && rs.data.length >0)
          this.tongphieu = rs.data.length;
          else{
          this.tongphieu = 0
          this.$root.toastError("Không tìm thấy phiếu phù hợp với mã giao dịch");
          }
      
      } catch (error) {
        console.log(error)
        this.$root.toastError("Không tìm thấy phiếu phù hợp với mã giao dịch");
      } finally {
        this.$root.showLoading(false);
      }
    },
    Lay_ds_hdtb_gd_kichhoat: async function () {
      try {
        this.$root.showLoading(true);
        this.clearForm();
       
        let data = {
          loai_hd_id: this.loaihd_id,
          dichvu_vt_id: this.dichvuvt_id,
          tthd_id: this.tthd_id,
          status: this.status,
          loaitbi_id: this.loaitb_id,
          quytrinh_id: this.quytrinh_id,
        };
        console.log('Lay_ds_hdtb_gd_kichhoat: ',data)
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_hdtb_gd_kichhoat",
          data
        );
         this.dsThueBao = rs.data;
          if(rs.data!=null && rs.data.length >0)
          this.tongphieu = rs.data.length;
          else{
          this.tongphieu = 0
          }
         // this.dsThueBao = rs.data;
        //  this.tongphieu = rs.data.length;
        
      } catch (error) {
        this.$root.toastError("Không tìm thấy phiếu phù hợp");
      } finally {
        this.$root.showLoading(false);
      }
    },
    HT_Tocdo_Adsl_Combobox: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_tocdo"
        );

          this.dsTocDoADSL = rs.data;
          this.dsTocDoThucMoi = rs.data;
      if (rs.data != null && rs.data.length > 0) {
          this.tocdo_adsl_id = rs.data[0].tocdo_id;
          this.tocdo_thucmoi_id = rs.data[0].tocdo_id;
        }
      } catch (error) {
        return null;
      }
    },
    HT_MucCuoc_Adsl_Combobox: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_muccuoc"
        );
  
          this.dsMucCuoc = rs.data;
        if (rs.data != null && rs.data.length > 0) {
          this.muccuoc_id = rs.data[0].muccuoc_id;
        }
      } catch (error) {
        return null;
      } 
    },
    Hienthi_Mau_DataGridView: function () {
       if (this.dsThueBao.length> 0)
                {
                    //Row chẵn: Color.LightCyan
                    //Row lẻ: Color.White;
                }
    },
    HienThiAnhTrangThai: function () {},
    Lay_TrangThai_HD:  function () {
      if (this.rad_kichhoat == "0") {
        if (this.loaihd_id == Enum.LoaiHopDong.CHAMDUT_SD)
          this.tthd_id = Enum.TrangThaiHD.MOI;
        else this.tthd_id = Enum.TrangThaiHD.THANH_TOAN;

        if ( this.loaihd_id == Enum.LoaiHopDong.DOISO_TB ||
             this.loaihd_id == Enum.LoaiHopDong.DAT_MOI ||
             this.loaihd_id == Enum.LoaiHopDong.KHOIPHUC_SD)             
          this.status = Enum.TRANGTHAI_DONGBO.DONGBO_LHD;
        else 
        this.status = Enum.TRANGTHAI_DONGBO.CHUADONGBO;
      } 
      else {
        this.status = Enum.TRANGTHAI_DONGBO.DONGBO_CM;
        if (this.loaihd_id == Enum.LoaiHopDong.CHAMDUT_SD)
          this.tthd_id = Enum.TrangThaiHD.MOI;
        else this.tthd_id = Enum.TrangThaiHD.THANH_TOAN;
      }
    },
    Lay_dv_bang_HDTB: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/laydonvi_bang_hdtbid/" + hdtb_id
        );
        if (rs.data != null) return rs.data[0];
        else return null;
      } catch (error) {
        return null;
      } 
    },
    LAY_DS_HDTB_ADSL_THEO_HDTB_ID: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_hdtb_adsl_theo_id/" + hdtb_id
        );
        if (rs.data != null) return rs.data[0];
        else return null;
      } catch (error) {
        return null;
      } 
    },
    Lay_ThamSo_MD: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_thamso_md"
        );
        if (rs.data != null) {
          this.dsThamSo = rs.data;
          if (this.dsThamSo.length > 0)
                {
                    let str_vtemp = "HUYGOI_KHI_HOANCONG_TL";
                    let det = this.dsThamSo.filter(x => x.ma_ts == str_vtemp)                                            

                    if (det.length > 0)
                    {
                        let row = det[0];
                        if (row.ten_ts.trim() == "1")
                            this.huygoi_hoancong_tl = 1;
                    }

                    str_vtemp = "LHD_NHANTIN_DIABAN";
                    det =  this.dsThamSo.filter(x => x.ma_ts == str_vtemp)
                    if (det.length > 0)
                    {
                        let row = det[0];
                        if (row.ten_ts.toString().trim() == "1")
                            this.nhantin_nhanvien_diaban = 1;
                    }
          }
        }
      } catch (error) {
      } 
    },
    Lay_DS_LapDat: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_kieu_ld",
          { loai_hd_id: this.loaihd_id,
           loai_tbi_id: this.loaitb_id }
        );
 
          this.dsKieuLD = rs.data;
        if (rs.data != null && rs.data.length > 0) {
          this.kieuld_id = rs.data[0].kieuld_id;
        }
      } catch (error) {
      } 
    },
    LayDsDichVu_Vt: async function () {
      try {
      
        if(this.dsdichvuvt_id==null || this.dsdichvuvt_id=='')
         this.dsdichvuvt_id= '4';
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_dichvu_vt",{
            dichvu_vt_id:this.dsdichvuvt_id
          }
        );
        this.dsDichVu = rs.data;
        if (rs.data != null && rs.data.length > 0) 
        this.dichvuvt_id = this.dsDichVu[0].dichvuvt_id;
      } catch (error) {
      } 
    },
    xuLyTag: async function(tag){
    try
                {
                  this.tag = tag;
                  if(this.tag != null && this.tag != 'null'){
                    if (this.tag.toString() != "")
                    {
                       let str = this.tag.toString();
                        let words = str.split('+');
                        if (words.length >= 1)
                            this.tthd_id = parseInt(words[0]);
                        if (words.length >= 2)
                        {
                            this.dsloaihd_id = words[1];
                            this.dsloaihd_id = dsloaihd_id.replace(';', ',');
                        }
                        if (words.length >= 3)
                        {
                            this.dsdichvuvt_id = words[2];
                            this.dsdichvuvt_id = dsdichvuvt_id.replace(';', ',');
                        }
                    }
                    else
                    {
                        this.tthd_id = 2;
                        this.dsloaihd_id = "1";
                        this.dsdichvuvt_id = "1";
                    }
                  }else{
                    this.tthd_id = 2;
                    this.dsloaihd_id = "4,7,8,14";
                    this.dsdichvuvt_id = "4";
                  }
                }
                catch(ex)
                {
                    this.tthd_id = 2;
                    this.dsloaihd_id = "4,7,8,14";
                    this.dsdichvuvt_id = "4";
                    console.log(ex)
                }
    },
    initForm: async function () {
      await this.getTagForm()
      await this.Lay_ThamSo_MD()
      this.ngay_th = DateTimeLib.toDateDisplay(this.ngay_th);
      await this.LayDsDichVu_Vt();
 
      await this.getDsHopDongPL();
      await this.HT_Tocdo_Adsl_Combobox();
      await this.HT_MucCuoc_Adsl_Combobox();
     // await this.getDsQuyTrinh();
      await this.Lay_DS_LoaiHinh(this.dichvuvt_id);
     
     // await this.HienThiDanhSachTB();
      this.kt_load = true;
    },
    layDs: async function () {
      this.Lay_TrangThai_HD();
      await this.HienThiDanhSachTB();
    },
    //#endregion
    //#region Hàm chức năng
    getSubscriberInfo: async function (account) {
      return await vasc.getSubscriberInfo(account)     
    },
    GetSubscriberInfoV2: async function (IPTVAccount, InfoDevice, megamytv) {
      try {
        let objrequest;
        objrequest.iptvaccount = IPTVAccount;
        objrequest.inforDevice = InfoDevice;
        let obj = await vasc.GetSubscriberInfoV2(objrequest);
        megamytv = "0";
        if (obj != 0) {
          if (obj == 1) {
            this.$root.toastError(
              "Tài khoản " + IPTVAccount + " không tồn tại trên hệ thống !"
            );
          } else {
            this.$root.toastError(obj.errordesc);
          }
        } else {
          obj.megamytv = obj.megaMytv;
        }
        return obj;
      } catch (e) {
        return null;
      }
    },
    PHAILAM: function (code) {
      //console.log()
      if(this.dsThaoTac !=null && this.dsThaoTac.length > 0){     
      let dr = this.dsThaoTac.filter((x) => x.enable == 1 && x.code == code);
      if (dr.length > 0) return true;
      return false;
      }else
       return false;
    },
    checkMapId_new: async function(map_id,no){
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
      }    
    },
    HienThiDVGT: function () {
      this.lvwDVGT = [];
      if (this.dsDichVuDK.length > 0) {
        for (let i = 0; i < this.dsDichVuDK.length; i++) {
          let obj = new Object();
          let item = this.dsDichVuDK[i];
          obj.id = item.dichvugt_id;
          obj.ma_dvgt = item.ma_dvgt;
          obj.kieu_yc = item.kieu_yc;
          obj.kieu_yc1 = item.kieu_yc == 1 ? "Đăng ký" : "Hủy";
          obj.dichvugt_id = item.dichvugt_id;
          obj.ttth = item.ttth;
          obj.ten_dvgt = item.ten_dvgt;
          this.lvwDVGT.push(obj);
        }
      }
    },
    LAY_DS_DANGKY_DVGT: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_dangky_dvgt/" + hdtb_id
        );
        console.log('LAY_DS_DANGKY_DVGT',rs)
      this.dsDichVuDK = rs.data;
      } catch (error) {
      }
    },
    Lay_DS_LyDoHuy_HDTB: async function (hdtb_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_lydohuy_hdtb/"+hdtb_id
        );
       return rs.data;
      } catch (error) {
      }
    },
    Lay_DS_LoaiHinh: async function (dvvt_id) {
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_ds_loaihinh/" + dvvt_id
        );
        this.dsLoaiHinh = rs.data;
        if (rs.data != null) {
          this.loaihinh_id = rs.data[0].loaitb_id;
        }
      } catch (error) {
      } finally {
      }
    },
    ///Hiển thị nút Hủy kích hoạt
    HienThiHuyKH: function () {
      //this.showHuyKH = true;
      this.disableButton.huyKichHoat = true;
      if (this.dsThueBao != null && this.dsThueBao.length > 0) {
        if (this.rad_kichhoat == 0)  this.disableButton.huyKichHoat = true;
        else {
          if (
            this.loaihd_id == Enum.LoaiHopDong.CHAMDUT_SD &&
            (this.loaitb_id ==  Enum.LoaiHinhTB.INTERNET_ADSL ||
              this.loaitb_id == Enum.LoaiHinhTB.INTERNET_FTTH ||
              this.loaitb_id == Enum.LoaiHinhTB.WIFI_FIBER ||
              this.loaitb_id == Enum.LoaiHinhTB.WIFI_MESH)
          ){
            this.disableButton.huyKichHoat = false;
          }
        }
      }else{
           this.disableButton.dongBo = true
           this.disableButton.hoanCong = true
      }
    },
    chonIP: function (val) {
      if (val != null) this.diachi_ip = val;
    },
    chuanHoaXML: function (str) {
      let result = "";
      result = str.replace("&", "&amp");
      result = result.replace("<", "&lt");
      result = result.replace(">", "&gt");
      result = result.replace("'", "&apos");
      result = result.replace(String.fromCharCode(34), "&quot");
      return result;
    },
    //#endregion
    //#region "On Change"
    onChangeLoaiHD: async function () {
      if (this.loaihd_id == Enum.LoaiHopDong.DOISO_TB)
        this.lblAccount = "Account mới";
      else this.lblAccount = "Account";
      await this.getDsQuyTrinh();
      await this.Lay_DS_LapDat();
      this.Lay_TrangThai_HD()
      await   this.HienThiDanhSachTB();
    },
    gridthuebao_selectedCheckbox: function (data) {
      if (data != null) this.hdtb_ids = data;
    },
    gridthuebao_PageChanged: function (data) {},
    onChangeQuyTrinh: async function () {
    if(this.dsQuyTrinh.length>0){
     this.huonggiao_id = await this.Laythongtin_table(this.quytrinh_id);
     console.log('huonggiao_id',this.huonggiao_id )
     if(this.huonggiao_id == -1){
      this.$root.toastError('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại');
       await  this.HienThiDanhSachTB();
       return;
     }
     await this.HienThiGiaoDien(this.quytrinh_id)
     this.Lay_TrangThai_HD()
     await  this.HienThiDanhSachTB();
    
    }
     
    },
    gridldhuy_selectedRowChanged: function (item) {
      if (item != null) this.currentLydoHuy = item;
    },
    gridthuebao_selectedRowChanged: async function (item) {
      if (item != null) {
        Object.assign(this.currentItem, item);
        this.thongTinKH = {
          ma_gd: item.ma_gd,
          ma_kh: item.ma_kh,
          ten_kh: item.ten_kh,
          diachi_kh: item.diachi_kh,
          ghichu: item.ghichu,
          ngay_yc: DateTimeLib.toDateDisplay(item.ngay_yc),
        };
        this.account = this.chuanHoaXML(item.ma_tb);
        this.hdtb_id = item.hdtb_id;
        this.status = item.status;
        try {
          this.thuebao_id = item.thuebao_id;
        } catch (e) {
          this.thuebao_id = 0;
        }
        let dtTem = await this.Lay_dv_bang_HDTB(item.hdtb_id);
        if (dtTem != null) this.donvi_id = dtTem.donvi_id;
        this.loaitb_id = item.loaitb_id;
        this.bras_id = item.bras_id;
        this.tocdo_cu_id = item.tdcu_id;
        //Loại DAT_MOI và KHOIPHUC_SD
        ////
        if (this.loaitb_id == Enum.LoaiHinhTB.INTERNET_MYTV) {
          this.ishowMyTV = true;
          // Tạm commnet để khỏi gọi ws
          let dt = await this.getSubscriberInfo(item.ma_tb);
          if (dt != null) this.lblTrangThaiMyTV = dt.status;
          else this.lblTrangThaiMyTV = "";
        } else {
          this.ishowMyTV = false;
          this.lblTrangThaiMyTV = "";
        }
        if (item.status == Enum.TRANGTHAI_DONGBO.DONGBO_CM) {
          if (this.ten_tat_ccbs == "HNI" && this.loaihd_id == Enum.LoaiHopDong.THAY_DOI_DV) {
            if (this.kieuld_id == Enum.KieuLapDat.TAMDUNG_SD_MEGA) {
              let kieu_mega = "1";
              let ds_mega = await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(item.hdtb_id);
              if (ds_mega != null)
                kieu_mega = await this.checkMapId_new(item.tocdo_id,5);
              //...
              console.log('kieu_mega',kieu_mega)
              if (kieu_mega == "0") this.disableButton.hoanCong = false;
            } else {
              if (this.PHAILAM("HOANCONG"))
                this.disableButton.hoanCong = false;
              else this.disableButton.hoanCong = true;
            }
          } else {
            if (this.PHAILAM("HOANCONG")) this.disableButton.hoanCong = false;
            else this.disableButton.hoanCong = true;
          }
          if (this.PHAILAM("GIAOPHIEU")) this.disableButton.giaoPhieu = false;
          else this.disableButton.giaoPhieu = true;
        } else {
          this.disableButton.hoanCong = true;
          this.disableButton.giaoPhieu = true;
        }
        await this.Lay_DS_LapDat();
        if (this.loaihd_id == Enum.LoaiHopDong.THAY_DOI_TOCDO_ADSL) {
          this.muccuoc_id = item.muccuoc_id;
          this.tocdo_adsl_id = item.tocdo_id;
          this.txtSLIP = item.soluong_ip;
          let tocdo_id = 0;
          tocdo_id = this.tocdo_adsl_id;
          let kiemtra_ip = await this.checkMapId_new(tocdo_id,6);
          if (kiemtra_ip == "1") {
            this.isDisableGiuIp = false;
            this.isDisableTuDong = false;
            this.isDisableChonIp = false;
            this.isDisableThayDoiTD = false;
          } else {
            this.isDisableGiuIp = true;
            this.isDisableTuDong = true;
            this.isDisableChonIp = true;
            this.isDisableThayDoiTD = true;
          }

          if (item.tucthi == "1" || item.tucthi == "2" || item.tucthi == "3")
            this.chkTucThi = true;
          else this.chkTucThi = false;
          this.kieuld_id = item.kieuldid;
          if (this.kieuld_id == Enum.KieuLapDat.THAYDOI_TOCDOTHUC_ADSL) {
            let tdthuc_moi = await this.checkMapId_new(this.hdtb_id,7 );
            if (tdthuc_moi != "-1") this.tocdo_thucmoi_id = tdthuc_moi;
          }
        } else if (this.loaihd_id == Enum.LoaiHopDong.THAY_DOI_DV) {
          await this.LAY_DS_DANGKY_DVGT(this.hdtb_id);
          // Hiển thị ra gridView
          this.HienThiDVGT();
        } else if (this.loaihd_id == Enum.LoaiHopDong.DOISO_TB)
          this.txtAccountCu = item.matb_cu;
        else if (this.loaihd_id == Enum.LoaiHopDong.CHAMDUT_SD) {
          let dtlydohuy = await this.Lay_DS_LyDoHuy_HDTB(this.hdtb_id);
          if (dtlydohuy != null && dtlydohuy.length > 0)
            this.dsLyDoHuy = [...dtlydohuy];
          this.phieu_id = item.phieu_id;
        }
        if (this.tthd_id == Enum.TrangThaiHD.THANH_TOAN) {
          this.phieu_id = item.phieu_id;
        }
        this.HienThiHuyKH();
      }
    },
    showChonIp: function () {     
      if (this.dsThueBao.length == 0) {
        this.$root.toastError('Danh sách phiểu đang trống')
        return;}
      if (!this.dongbo_test) {
        let bras_id = 0;
        bras_id = this.bras_id;
        let in_tocdo_id = this.tocdo_adsl_id;
        let data = {
          bras_id: bras_id,
          in_tocdo_id: in_tocdo_id,
        };
        this.$refs.chon_ip.setData(data);
        this.$root.$bvModal.show("popupTCTTTB");
      }
    },
    getIp4: async function (brasId, serviceId){
         return await vdc.getIps(brasId, serviceId)
    },
    getIpsV6: async function (brasId,serviceId){
         return await vdc.getIpsV6(brasId,serviceId)
    },
    gridtbao_dataGridCellCreated(e) {
      let item = e.cellDataItem;
      e.cellAllowHtml = true;
      if (item.status == 1) {
        $(e.cellElement).prepend('<span class="icon one-zoom-default"></span>');
      } else if (item.status == 5) {
        $(e.cellElement).prepend(
          '<span class="ui-1_zoom-out nc-icon-outline"></span>'
        );
      } else if (item.status == 0) {
        $(e.cellElement).prepend('<span class="icon one-zoom-default"></span>');
      } else {
        $(e.cellElement).prepend('<span class="icon one-zoom-default"></span>');
      }
    },

    //#endregion
    //#region  Hàm tiện ích

    getValueFromIndex: function (obj, index) {
      try {
        return obj[Object.keys(obj)[index]];
      } catch (e) {
        return null;
      }
    },
    
    getTagForm: async function () {
      try {
        let tag = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/getTagValue', {id: 1235})
        await this.xuLyTag(tag.data.TAGVALUE)
      } catch (error) {
      } finally {
      }
    },
    log_action: async function (data) {
      try {
        let res = await this.$root.context.post(
          "/web-hopdong/thuebao_datcoc_thunghiem/log_action",
          data
        );
        return res.data;
      } catch (e) {
        return null;
      }
    },
    GiaoPhieu: async function(){
       try
            {
                if (this.thongTinKH.ma_gd != "")
                {
                  let dtTemp = await this.checkMapId_new(this.quytrinh_id,26); 
                  this.huonggiao_gp_id = dtTemp;
                    if (dtTemp != 0)
                    {                     
                      this.$refs['formGiaophieu'].show();
                    }
                    else
                        this.$root.toastError("Chưa có hướng giao được gán !");
                }
            }
            catch (ex)
            {
               this.$root.toastError("Có lỗi xảy ra trong quá trình giao phiếu");
            }
    },
    //#endregion
    onQueryCellInfoThueBao: async function(args){
    },
    modalGiaoPhieu_Ok:async function(){
        await this.HienThiDanhSachTB();
    },
    modalGiaoPhieu_Show:function(){
       this.$refs['formgiaophieu'].frmGiaoPhieu('',1,this.huonggiao_gp_id,0,0,'0');
    },
    okGiaoPhieu:function(){

    },
    Laythongtin_table: async function (quytrinh_id){
       try {
        let res = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/Laythongtin_table/"+quytrinh_id
          
        );
        return res.data;
      } catch (e) {
        return -1;
      }
    },
    addMonths: function(so){
     var myDate = new Date();
     myDate.setMonth(myDate.getMonth() + so);
     return DateTimeLib.toDateDisplayWithFormat(myDate,"yyyyMM") ;
    },
    HOANTHIEN_HS_THAYDOI_MATB: async function(hdtb_id){
       try {
        let rs = await this.$root.context.post(
          "/web-ccdv/dongbo-visa-vasc/hoanthien_hs_thaydoi_matb/" + hdtb_id
        );
        if (rs.data != null) return rs.data[0];
        else return null;
      } catch (error) {
        return null;
      } 

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
      lay_thongtin_dbkhachhang: async function (thuebao_id){
      try {
        let rs = await this.$root.context.get(
          "/web-ccdv/dongbo-visa-vasc/lay_thongtin_dbkhachhang/"+thuebao_id,
        );
      if(rs.data !=null && rs.data.length > 0)
      return rs.data[0]
      return rs.data
      } catch (error) {
        return null
      }      
    },
    HuyKichHoat: async function(){
      try
            {
                if (this.dsThueBao.length<0)
                {
                    this.$root.toastError("Không có thuê bao để thực hiện hủy kích hoạt thanh lý!");
                    return;
                }

                this.loaitb_id = Int32.Parse(this.currentItem.loaitbid);
                this.kieuld_id = Int32.Parse(this.currentItem.kieuldid);
                this.hdtb_id = Int32.Parse(this.currentItem.hdtbid);
                if (this.loaihd_id == Enum.LoaiHopDong.CHAMDUT_SD)
                {
                  this.$root.showLoading(true);
                    if (this.dongbo_test == false)
                    {
                     let lst_chon =  this.$refs['grid-thuebao'].$refs.grid.getSelectedRecords() ;
                        if (lst_chon.length == 0)
                        {
                            this.$root.toastError("Hãy chọn phiếu để hủy kích hoạt thanh lý!");
                            return;
                        }
                        if (lst_chon.length > 1)
                        {
                            this.$root.toastError("Bạn chỉ được chọn 1 phiếu để hủy kích hoạt thanh lý!");
                            return;
                        }

                      this.$confirm(
                      `Bạn có chắc chắn hủy kích hoạt thanh lý Account không? `,
                       {
                        confirmButtonText: "Có",
                        cancelButtonText: "Không",
                       }
                       )
                      .then(async () => {
                        let kiemtra = true;
                                for (let i = 0; i < lst_chon.length; i++)//minhnt sua
                                {                                   
                                      let account = lst_chon[i].ma_tb.trim(); //txtMaTB.Text.Trim();
                                      let lydohuy = "Hủy Account";
                                      let chuthich = "Hủy Account";
                                      let in_loaitb_id = Int32.Parse(lst_chon[i].loaitb_id);
                                      let in_hdtb_id = Int32.Parse(lst_chon[i].hdtb_id);
                                        if (!await this.Huy_KichHoat_ThanhLy(account, lydohuy, chuthich, in_loaitb_id, in_hdtb_id))
                                        {
                                            kiemtra = false;
                                            this.$root.toastError("Có lỗi hủy kích hoạt thuê bao " + account);
                                        }
                                    
                                }
                                if (kiemtra)
                                    this.$root.toastSuccess("Đã cập nhật dữ liệu lên hệ thống thành công!");
                                this.HienThiDanhSachTB();
                                this.$root.showLoading(false);
                            })
                            .catch((e) => {
                              this.$root.showLoading(false);
                              console.log(e);
                            this.$root.toastError("Có lỗi xảy ra trong quá trình hủy kích hoạt!");});
                        
                    }
                    else
                    {
                        await this.CAPNHAT_STATUS_HDTB(this.hdtb_id, Enum.TRANGTHAI_DONGBO.CHUADONGBO);
                        if (this.PHAILAM("CAPNHAT_NGAYHT_KH"))
                        {
                            let dbt = this.KT_DBThang_DaChot();
                            if (dbt != "0")
                            {
                                this.$root.toastError(dbt);
                                return;
                            }
                            this.Update_Table(0,this.hdtb_id);
                        }
                        if (this.PHAILAM("CAPNHAT_NGAYKH_KH"))
                            this.Update_Table(1,this.hdtb_id);
                           await this.HienThiDanhSachTB();
                        this.$root.toastSuccess("Đã cập nhật dữ liệu lên hệ thống thành công!");
                    }
                }
            }
            catch (ex)
            {
                this.$root.toastError("Có lỗi xảy ra trong quá trình Hủy kích hoạt");
                this.$root.showLoading(false);
                console.log(ex)
            }finally{
              this.$root.showLoading(false);
            }
    },
    Update_Table:async function(type,hdtb_id){
     if(type == 0)
     await this.$root.context.get(
          `/web-ccdv/dongbo-visa-vasc/update_null_ngay_ht/${hdtb_id}`
        );
     else if(type ==1)
     await this.$root.context.get(
          `/web-ccdv/dongbo-visa-vasc/update_null_ngay_kh/${hdtb_id}`
        );
      else if(type ==2)
      await this.$root.context.get(
          `/web-ccdv/dongbo-visa-vasc/update_null_ngay/${hdtb_id}`
        );
    },
   Huy_KichHoat_ThanhLy: async function( account,  lydo,  chuthich,  in_loaitb_id,  in_hdtb_id)
        {
            let status = false;
            try
            {
                if ((in_loaitb_id == LoaiHinhTB.INTERNET_FTTH) || (in_loaitb_id == LoaiHinhTB.WIFI_FIBER) || in_loaitb_id == LoaiHinhTB.INTERNET_ADSL)
                {
                    // đăng ký lại greenonline nếu có
                    let ktra = false;
                    let thuebao_id;
                    thuebao_id = await this.checkMapId_new(in_hdtb_id,8);
                    ktra = await this.KIEMTRA_TB_SD_DVGT(thuebao_id, Enum.DichVuGiaTang.GREEN_ONLINE);
                    if (ktra == true)
                    {
                      let thanhtoan_id = await this.checkMapId_new(in_hdtb_id,15);
                      let donvitt_id =  await this.checkMapId_new(thanhtoan_id,16);
                        let kt = 0;
                        kt = await this.DangKy(account, "O",  this.$root.token.getUserName(), this.hdtb_id,  this.ten_tat_ccbs, 0, donvitt_id);
                        if (kt == 0)
                        {
                            return false;
                        }
                    }

                    status = await vdc.RestoreAccount(account);//restore lại account
                }

                if (status)
                {
                    //Cập nhật status
                    await this.CAPNHAT_STATUS_HDTB(in_hdtb_id, Enum.TRANGTHAI_DONGBO.CHUADONGBO);

                    await this.Update_Table(2,in_hdtb_id);
                    
                }
                else
                    return false;
                return true;
            }
            catch (ex)
            {
               this.$root.toastError("Có lỗi xảy ra trong quá trình Hủy Kích Hoạt Thanh Lý");  
               console.log(ex)          
                return false;
            }
        },
   async getHienThiNutBam(){
      if(this.dsThaoTac!=null && this.dsThaoTac.length >0){
        for(let i=0;i<this.dsThaoTac.length;i++){
          let temp = await this.lay_thaotac_control(this.dsThaoTac[i].thaotac_id)
          if(temp!=null && temp.length>0){
            if(temp.filter(x=>x.control_name.toLowerCase().includes('giaophieu')))
             {
              if(this.dsThaoTac[i].enable == 1){
               this.disableButton.giaoPhieu = false;
               this.showGiaoPhieu = true;
             }else{
              this.disableButton.giaoPhieu = true;
             }
            }
             if(temp.filter(x=>x.control_name.toLowerCase().includes('kichhoat')))
             {
              if(this.dsThaoTac[i].enable ==1)
               this.disableButton.dongBo = false;
             }else{
              this.disableButton.dongBo = true;
             }
             if(temp.filter(x=>x.control_name.toLowerCase().includes('hoancong')))
             {
              if(this.dsThaoTac[i].enable ==1)
               this.disableButton.hoanCong = false;
               this.showHoanCong = true;
             }else{
              this.disableButton.hoanCong = true;
             }
             if(temp.filter(x=>x.control_name.toLowerCase().includes('huykh')))
             {
              if(this.dsThaoTac[i].enable ==1)
               this.disableButton.huyKichHoat = false;
              // this.showGiaoPhieu = true;
             }else{
              this.disableButton.huyKichHoat = true;
             }
          }
        }
          
        }
    }

  },
  created:async function() {
    try {
      this.$root.showLoading(true);     
      await this.initForm();
      vasc.setupRoot(this.$root)
      vdc.setupRoot(this.$root)
      this.ttnd = await this.lay_thongtin_nguoidung()     
      let login = {
      userId: this.ttnd.user_visa,
      password:this.ttnd.pass_visa
       }
     vdc.setLogin(login)
     this.ten_tat_ccbs = this.$root.context.user.getProperty("tentat_ccbs")
     this.dongbo_test = await this.KIEMTRA_THAM_SO_DONGBO('DONGBO_TEST');
     console.log('dongbo_test',this.dongbo_test)
    } catch (e) {
      console.log(e);
    } finally {
      this.$root.showLoading(false);
    }
  },
  data() {
    return {
      currentItem: {},
      currentLydoHuy: {},
      dsThueBao: [],
      dsHDPL: [],
      dsMucCuoc: [],
      dsQuyTrinh: [],
      dsDichVu: [],
      dsLyDoHuy: [],
      dsLoaiHinh: [],
      dsThaoTac: [],
      dsTocDoADSL: [],
      dsThamSo: [],
      dsTocDoThucMoi: [],
      dsDichVuDK: [],
      lvwDVGT: [],
      dsHDKH:[],
      dsHDTB:[],
      ngay_th: new Date(),
      rad_kichhoat: 0,
      thuebao_id: -1,
      tongphieu: 0,
      loaitb_id: 0,
      hdtb_ids: [],
      hdtb_id: -1,
      donvi_id: -1,
      muccuoc_id: -1,
      tocdo_adsl_id: -1,
      tocdo_thucmoi_id: -1,
      tocdo_cu_id: -1,
      loaihinh_id: -1,
      txtAccountCu: "",
      lblAccount: "Account",
      chkTucThi: false,
      txtSLIP: 0,
      diachi_ip: "",
      giu_ip: false,
      tudong: false,
      async_status: false,
      phieu_id: -1,
      ishowMyTV: false,
      hoancong_ngay: false,
      account: "",
      lblTrangThaiMyTV: "",
      huygoi_hoancong_tl: -1,
      nhantin_nhanvien_diaban: -1,
      dsKieuLD: [],
      kt_load: false,
      bras_id: -1,
      dichvuvt_id: 4,
      quytrinh_id: -1,
      loaihd_id: -1,
      tthd_id: 0,
      status: 0,
      date: {
        dateVal: Date,
        dateFormat: "dd/MM/yyyy",
      },
      thongTinKH: {
        ma_gd: "",
        ma_kh: "",
        ten_kh: "",
        diachi_kh: "",
        ghichu: "",
        ngay_yc: new Date(),
      },
      password: "",
      kieuld_id: -1,
      chk_loaihinh: false,
      isDisable: true,
      isDisableThayDoiTD: false,
      isDisableGiuIp: false,
      isDisableTuDong: false,
      isDisableChonIp: false,
      showHuyKH: true,
      showDongBo: true,
      showGiaoPhieu: false,
      showHoanCong: true,
      dongbo_test: false,
      isErrorIp: false,
      disableButton: {
        dongBo: false,
        hoanCong: true,
        giaoPhieu: true,
        huyKichHoat: true,
      },
      billingInfo: "",
      txtcuoctb: "",
      txtcuockh: "",
      txtcuoctg: "",
      txttlgiam: "",
      dtpngiam: "",
      txtthgiam: "",
      txtmienip: "",
      txtslemail: "",
      txtdgemail: "",
      txttlgiamemail: "",
      dtpngiamemail: "",
      txtthgiamemail: "",
      txttlgiamip: "",
      dtpngiamip: "",
      txtthgiamip: "",
      txtcuocmb: "",
      txtmbmphi: "",
      txtcuocmphi: "",
      txttiengiam: "",
      tag:"",
      dsloaihd_id:"",
      dsdichvuvt_id:"",
      countRow:0,
      index_loaitb_id:0,
      index_hdtb_id:0,
      index_account:0,
      huonggiaotn_id:0,
      quytrinh_id_2:0,
      hdkh_id_huygoi_mytv : 0,
      hdtb_id_huygoi_mytv : 0,
      ttnd:{},
      ten_tat_ccbs:'',
      huonggiao_gp_id:-1,
      huonggiao_id:0,
      p_kieu: '0',
      p_magd:"",
      p_huonggiaoId:'-1',
      isPopup:true,
      str_hoancong:""
      }
  },
};
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