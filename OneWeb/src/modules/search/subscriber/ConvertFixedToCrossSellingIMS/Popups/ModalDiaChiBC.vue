<template src="./ModalDiaChiBC.html"></template>
<script>
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
export default {
  name: "ModalDiaChiBC",
  props: ["modalId", "data","isStrictMode","tinh"],
  created: async function () {
    this.dsTinh=[]          
    this.diachi.tinh_id = null
  },
  components: { ModalChonToaDo },
  computed: {
    thaydoiDiaChi: function () {
      if (this.tentinh != "") {
        if(this.them_text_vietnam == 1 || this.them_text_vietnam == "1")
          this.diachi.diachimoi = this.tentinh + ", Việt Nam";
        else
          this.diachi.diachimoi = this.tentinh;
        this.diachi.diachimoi = this.tenquan + ", " + this.diachi.diachimoi;
        this.diachi.diachimoi = this.tenphuong + ", " + this.diachi.diachimoi;
        if (this.isPho && this.diachi.pho_id != 0)
          this.diachi.diachimoi = this.tenpho + ", " + this.diachi.diachimoi;
        if (this.isAp && this.diachi.ap_id != 0)
          this.diachi.diachimoi = this.tenap + ", " + this.diachi.diachimoi;
        if (this.isKhu && this.diachi.khu_id != 0)
          this.diachi.diachimoi = this.tenkhu + ", " + this.diachi.diachimoi;
        if (this.diachi.so_nha != null && this.diachi.so_nha != "")
          this.diachi.diachimoi = this.diachi.so_nha + ", " + this.diachi.diachimoi;
        return this.diachi.diachimoi;
      }
      return "";
    },
    
    getPosition () {
      return {
        lat: parseFloat(this.diachi.lat),
        lng: parseFloat(this.diachi.lng)
      }
    },
  },
  watch: {
  },
  methods: {
      onModalHidden() {
          // this.dsTinh=[]
          // this.diachi.tinh_id = null   
          // this.diachi.quan_id = null 
          // this.diachi.phuong_id = null        
          // this.diachi.pho_id = null 
          // this.diachi.ap_id = null 
          // this.diachi.khu_id = null 
          // this.diachi.dacdiem_id = null 
          // this.diachi.so_nha=""
          // this.diachi.diachimoi=""
          // this.diachi.daydiachi=""
      },
      LowerCasePropertyList(obj) {
        return obj.map( function( item ){
            for(var key in item){
                var upper = key.toLowerCase();
                // check if it already wasn't uppercase
                if( upper !== key ){ 
                    item[ upper ] = item[key];
                    delete item[key];
                }
            }
            return item;
        });
      },
      UpperCasePropertyList(obj) {
            return obj.map( function( item ){
                for(var key in item){
                    var upper = key.toUpperCase();
                    // check if it already wasn't uppercase
                    if( upper !== key ){
                        item[ upper ] = item[key];
                        delete item[key];
                    }
                }
                return item;
            });
        },
      focusMyElement: async function () {
        await this.getTinh();
        if(this.data){
          var tsdiachi = Object.assign({}, this.data);
          tsdiachi = this.LowerCasePropertyList([tsdiachi])[0];
        this.diachi.daydiachi = tsdiachi.diachi
        if (tsdiachi.tinh_id && tsdiachi.tinh_id != 0) {
        this.diachi.tinh_id = tsdiachi.tinh_id;
        this.tentinh = this.dsTinh.filter(
          (x) => x.TINH_ID == this.diachi.tinh_id
        )[0].TENTINH; 
  
        this.diachi.quan_id = tsdiachi.quan_id; 
                await this.getQuan();
             
        this.diachi.phuong_id=tsdiachi.phuong_id;
          await this.getPhuong();
        if(tsdiachi.phuong_id){
        if(tsdiachi.pho_id && tsdiachi.pho_id!=0) {
          this.diachi.pho_id=tsdiachi.pho_id;
          this.isPho=true}
       
         if(tsdiachi.ap_id && tsdiachi.ap_id!=0) {
            this.diachi.ap_id=tsdiachi.ap_id;
           this.isAp=true}
      
         if(tsdiachi.khu_id && tsdiachi.khu_id!=0){
             this.diachi.khu_id=tsdiachi.khu_id;
           this.isKhu=true}
            await this.getPhoApKhu(); 
        }
        this.diachi.so_nha = tsdiachi.so_nha;
        this.diachi.daydiachi = tsdiachi.diachi;
        this.diachi.diachimoi = tsdiachi.diachi;

      }else{
        if(this.dsTinh && this.dsTinh.length>0)
  
      this.diachi.tinh_id = this.dsTinh[0].TINH_ID;
              this.tentinh = this.dsTinh.filter(
          (x) => x.TINH_ID == this.diachi.tinh_id
        )[0].TENTINH;  
     await  this.getQuan();
       }
       }else{
        if(this.dsTinh && this.dsTinh.length>0) 
         this.diachi.tinh_id = this.dsTinh[0].TINH_ID;
              this.tentinh = this.dsTinh.filter(
          (x) => x.TINH_ID == this.diachi.tinh_id
        )[0].TENTINH;  
     await  this.getQuan();
       }
       var rs = await this.$root.context.post(
          "/web-bancheo/tracuu/sp_lay_ds_thamso_md",{
            "tinhthicong_id": this.tinh.tinhthicong_id,
            "p_kieu_id": 0
          }
        );
        var thamso = rs.data;
        this.thamsomacdinh = thamso.reduce(function(result, item, index, array) {
            result[item['ma_ts']] = item['ten_ts']; //a, b, c
            return result;
          }, {});
        if(typeof this.thamsomacdinh['THEM_VN_DIACHI'] !== 'undefined')
          this.them_text_vietnam = parseInt(this.thamsomacdinh['THEM_VN_DIACHI']);
        else
          this.them_text_vietnam = 0;
    },

    getTinh: async function () {
      try {
        this.$root.showLoading(true);
        if(this.tinh.tinhthicong_id == 0)
        {
          var rs = await this.$root.context.post(
            "/web-hopdong/lay-danh-muc-dia-chi-dia-gioi-hanh-chinh/sp_lay_ds_tinh", {}
          );
          this.dsTinh = this.UpperCasePropertyList(rs.data.rs1);
        }
        else
        {
          var rs = await this.$root.context.post(
            "/web-bancheo/tracuu/get_tinh_by_tinh_id", {
              "tinhthicong_id": this.tinh.tinhthicong_id,
              "tinh_id": this.tinh.tinhthicong_id
            }
          );
          this.dsTinh = this.UpperCasePropertyList(rs.data);
        }        
   
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false);
      }
    },
    getQuan: async function () {
      try {
        this.$root.showLoading(true);
        if(this.tinh.tinhthicong_id == 0)
        {
          var rs = await this.$root.context.post(
            "web-hopdong/lay-danh-muc-dia-chi-dia-gioi-hanh-chinh/sp_lay_ds_quan", {
              "p_tinh_id": this.diachi.tinh_id
            } 
          );
          this.dsQuan = this.UpperCasePropertyList(rs.data.rs1);
          if(this.dsQuan)
            this.diachi.quan_id = this.dsQuan[0].QUAN_ID;
          if(this.diachi.quan_id && this.dsQuan){
          var listquan = this.dsQuan.filter((x) => x.QUAN_ID == this.diachi.quan_id);
            if(listquan && listquan.length>0)
              this.tenquan = listquan[0].TEN_QUAN;
            else if(this.dsQuan && this.dsQuan.length>0) {
              this.diachi.quan_id = this.dsQuan[0].QUAN_ID;
              this.tenquan = this.dsQuan[0].TEN_QUAN;
            }
          }
          else if((!this.diachi.quan_id || this.diachi.quan_id<=0) && this.dsQuan) {
            this.diachi.quan_id = this.dsQuan[0].QUAN_ID;
            this.tenquan = this.dsQuan[0].TEN_QUAN;
          }
          this.onChangeQuan();
        }
        else
        {
          var rs = await this.$root.context.post(
            "/web-bancheo/tracuu/get_quan_by_tinh_id",{
              "tinhthicong_id": this.tinh.tinhthicong_id,
              "tinh_id": this.tinh.tinhthicong_id
            }
          );
          this.dsQuan = this.UpperCasePropertyList(rs.data);
          if(this.dsQuan)
            this.diachi.quan_id = this.dsQuan[0].QUAN_ID;
          if(this.diachi.quan_id && this.dsQuan){
          var listquan = this.dsQuan.filter((x) => x.QUAN_ID == this.diachi.quan_id);
            if(listquan && listquan.length>0)
              this.tenquan = listquan[0].TEN_QUAN;
            else if(this.dsQuan && this.dsQuan.length>0) {
              this.diachi.quan_id = this.dsQuan[0].QUAN_ID;
              this.tenquan = this.dsQuan[0].TEN_QUAN;
            }
          }
          else if((!this.diachi.quan_id || this.diachi.quan_id<=0) && this.dsQuan) {
            this.diachi.quan_id = this.dsQuan[0].QUAN_ID;
            this.tenquan = this.dsQuan[0].TEN_QUAN;
          }
          this.onChangeQuan();
        }
        
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getPhuong: async function () {
      try {
        if(this.tinh.tinhthicong_id == 0)
        {
          this.$root.showLoading(true);
          var rs = await this.$root.context.post(
            "/web-hopdong/lay-danh-muc-dia-chi-dia-gioi-hanh-chinh/sp_lay_ds_phuong", {
              "p_quan_id": this.diachi.quan_id
            }
          );
          this.dsPhuong = this.UpperCasePropertyList(rs.data.rs1);
          if(this.diachi.phuong_id && this.dsPhuong){
            var listphuong = this.dsPhuong.filter(
            x => x.PHUONG_ID == this.diachi.phuong_id);
            if(listphuong && listphuong.length>0)
              this.tenphuong = listphuong[0].TEN_PHUONG;
            else if(this.dsPhuong && this.dsPhuong.length>0) {
              this.diachi.phuong_id = this.dsPhuong[0].PHUONG_ID;
              this.tenphuong = this.dsPhuong[0].TEN_PHUONG;
            }
          }
          else if((!this.diachi.phuong_id || this.diachi.phuong_id<=0) && this.dsPhuong) {
            this.diachi.phuong_id = this.dsPhuong[0].PHUONG_ID;
            this.tenphuong = this.dsPhuong[0].TEN_PHUONG;
          }
          this.onChangePhuong();
        }
        else
        {
          this.$root.showLoading(true);
          var rs = await this.$root.context.post(
            "/web-bancheo/tracuu/get_phuong_by_quan_id", {
              "tinhthicong_id": this.tinh.tinhthicong_id,
              "quan_id": this.diachi.quan_id
            }
          );
          this.dsPhuong = this.UpperCasePropertyList(rs.data);
          if(this.diachi.phuong_id && this.dsPhuong){
            var listphuong = this.dsPhuong.filter(
            x => x.PHUONG_ID == this.diachi.phuong_id);
            if(listphuong && listphuong.length>0)
              this.tenphuong = listphuong[0].TEN_PHUONG;
            else if(this.dsPhuong && this.dsPhuong.length>0) {
              this.diachi.phuong_id = this.dsPhuong[0].PHUONG_ID;
              this.tenphuong = this.dsPhuong[0].TEN_PHUONG;
            }
          }
          else if((!this.diachi.phuong_id || this.diachi.phuong_id<=0) && this.dsPhuong) {
            this.diachi.phuong_id = this.dsPhuong[0].PHUONG_ID;
            this.tenphuong = this.dsPhuong[0].TEN_PHUONG;
          }
          this.onChangePhuong();
        }
        
      } catch (error) {
        console.log(error)

      } finally {
        this.$root.showLoading(false);
      }
    },
    getPhoApKhu: async function () {
      try {
          if(this.tinh.tinhthicong_id == 0)
          {
            this.$root.showLoading(true);
            var pho = await this.$root.context.post(
              "/web-hopdong/lay-danh-muc-dia-chi-dia-gioi-hanh-chinh/sp_lay_ds_pho",{
                "p_phuong_id": this.diachi.phuong_id,
                "p_nhom_id": 1
              }
            );
            var ap = await this.$root.context.post(
              "/web-hopdong/lay-danh-muc-dia-chi-dia-gioi-hanh-chinh/sp_lay_ds_pho",{
                "p_phuong_id": this.diachi.phuong_id,
                "p_nhom_id": 2
              }
            );
            var khu = await this.$root.context.post(
              "/web-hopdong/lay-danh-muc-dia-chi-dia-gioi-hanh-chinh/sp_lay_ds_pho",{
                "p_phuong_id": this.diachi.phuong_id,
                "p_nhom_id": 3
              }
            );
            if (pho.data.rs1 &&  pho.data.rs1.length > 0) {
              this.dsPho = this.UpperCasePropertyList(pho.data.rs1);
              if(this.diachi.pho_id && this.dsPho)
              this.tenpho = this.dsPho.filter(
              (x) => x.PHO_ID == this.diachi.pho_id
            )[0].TEN_PHO;
            }
            if(ap.data.rs1 &&  ap.data.rs1.length > 0){
              this.dsAp = this.UpperCasePropertyList(ap.data.rs1);
              if(this.diachi.ap_id && this.dsAp)
                this.tenap = this.dsAp.filter(
                (x) => x.PHO_ID == this.diachi.ap_id
              )[0].TEN_PHO;
            }
            if(khu.data.rs1 &&  khu.data.rs1.length > 0){
              this.dsKhu = this.UpperCasePropertyList(khu.data.rs1);
              if(this.diachi.khu_id && this.dsKhu)
                this.tenkhu = this.dsKhu.filter(
              (x) => x.PHO_ID == this.diachi.khu_id
            )[0].TEN_PHO;
            }
          }
          else
          {
            this.$root.showLoading(true);
            var pho = await this.$root.context.post(
              "/web-bancheo/tracuu/get_pho_by_phuong_nhom_pho_id",{
                "tinhthicong_id": this.tinh.tinhthicong_id,
                "phuong_id": this.diachi.phuong_id,
                "nhompho_id": 1
              }
            );
            var ap = await this.$root.context.post(
              "/web-bancheo/tracuu/get_pho_by_phuong_nhom_pho_id",{
                "tinhthicong_id": this.tinh.tinhthicong_id,
                "phuong_id": this.diachi.phuong_id,
                "nhompho_id": 2
              }
            );
            var khu = await this.$root.context.post(
              "/web-bancheo/tracuu/get_pho_by_phuong_nhom_pho_id",{
                "tinhthicong_id": this.tinh.tinhthicong_id,
                "phuong_id": this.diachi.phuong_id,
                "nhompho_id": 3
              }
            );
            if (pho.data &&  pho.data.length > 0) {
              this.dsPho = this.UpperCasePropertyList(pho.data);
              if(this.diachi.pho_id && this.dsPho)
              this.tenpho = this.dsPho.filter(
              (x) => x.PHO_ID == this.diachi.pho_id
            )[0].TEN_PHO;
            }
            if(ap.data &&  ap.data.length > 0){
              this.dsAp = this.UpperCasePropertyList(ap.data);
              if(this.diachi.ap_id && this.dsAp)
                this.tenap = this.dsAp.filter(
                (x) => x.PHO_ID == this.diachi.ap_id
              )[0].TEN_PHO;
            }
            if(khu.data &&  khu.data.length > 0){
              this.dsKhu = this.UpperCasePropertyList(khu.data);
              if(this.diachi.khu_id && this.dsKhu)
                this.tenkhu = this.dsKhu.filter(
              (x) => x.PHO_ID == this.diachi.khu_id
            )[0].TEN_PHO;
            }
          }
        
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getDacDiem: async function () {
      try {
        this.$root.showLoading(true);
        var rs = await this.$root.context.post(
          "/web-thicong/thuebao/lay_ds_dacdiem_theo_dieukien",
          {
            "apId": this.ap_id == null ? 0 : this.ap_id,
            "khuId": this.khu_id == null ? 0 : this.khu_id,
            "phoId": this.pho_id == null ? 0 : this.pho_id,
            "phuongId": this.phuong_id == null ? 0 : this.phuong_id,
          }
        );
        this.dsDacDiem = rs.data;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    saveDiachi: async function () {
      try {
        this.$root.showLoading(true);
      } catch (error) {
        this.$toast.error("Lưu ảnh thất bại");
      } finally {
        this.$root.showLoading(false);
      }
    },
    onChangeTinh: async function () {
      await this.getQuan();
      this.tentinh = this.dsTinh.filter(
        (x) => x.TINH_ID == this.diachi.tinh_id
      )[0].TENTINH;
    },
    onChangeQuan: async function () {
      await this.getPhuong();
      var listquan = this.dsQuan.filter(x => x.QUAN_ID == this.diachi.quan_id);
      if(listquan && listquan.length>0)
          this.tenquan = listquan[0].TEN_QUAN;
    },
    onChangePhuong: async function () {
      await this.getPhoApKhu();
      var listphuong = this.dsPhuong.filter(x => x.PHUONG_ID == this.diachi.phuong_id);
      if(listphuong && listphuong.length>0)
          this.tenphuong = listphuong[0].TEN_PHUONG;
    },
    onChangePho: async function () {
      await this.getDacDiem();
      this.tenpho = this.dsPho.filter(
          (x) => x.PHO_ID == this.diachi.pho_id
         )[0].TEN_PHO;
    },
    onChangeKhu: async function () {
      await this.getDacDiem();
   this.tenkhu = this.dsKhu.filter(
          (x) => x.PHO_ID == this.diachi.khu_id
        )[0].TEN_PHO;
    },
    onChangeAp: async function () {
      await this.getDacDiem();
        this.tenap = this.dsAp.filter(
          (x) => x.PHO_ID == this.diachi.ap_id
        )[0].TEN_PHO;
    },
    chapnhan: function () {


      if (
        this.isStrictMode &&
        ((!this.isPho && !this.isAp && !this.isKhu) ||
          (this.isPho && this.tenpho == "") ||
          (this.isAp && this.tenap == "") ||
          (this.isKhu && this.tenkhu == ""))
      ) {
        this.$toast.warning("Hãy chọn Phố / Ấp / Khu");
        return;
      }
      this.$emit("xacnhan", this.diachi);
      this.$bvModal.hide(this.modalId);
    },
    XacNhanToaDo (position) {
      this.diachi.lat = position.lat;
      this.diachi.lng = position.lng;
    },
  },
  data: function () {
    return {
      diachi: {
        diachi_id: null,
        tinh_id:null ,
        quan_id: null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id:null,
        dac_diem_id: null,
        so_nha: "",
        daydiachi: "",
        diachimoi: "",
        lat:0,
        lng:0,
      },
      dsTinh: [],
      dsQuan: [],
      dsPhuong: [],
      dsPho: [],
      dsAp: [],
      dsKhu: [],
      dsDacDiem: [],
      isPho: false,
      isAp: false,
      isKhu: false,
      isDacDiem: false,
      tentinh: "",
      tenquan: "",
      tenphuong: "",
      tenpho: "",
      tenap: "",
      tenkhu: "",
      them_text_vietnam: 0,
      thamsomacdinh: {},
    };
  },
};
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>