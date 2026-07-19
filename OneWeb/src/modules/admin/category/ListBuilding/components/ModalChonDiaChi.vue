<template src="./ModalChonDiaChi.html"></template>
<script>
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
export default {
  name: "ModalChonDiaChi",
  props: ["modalId", "data","isStrictMode"],
  created: async function () {
   this.dsTinh=[]
   this.diachi.tinh_id = null
  },
  components: { ModalChonToaDo },
  computed: {
    thaydoiDiaChi: function () {

      if (this.tentinh != "") {
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
      focusMyElement: async function () {
        await this.getTinh();
        if(this.data){
          var tsdiachi = Object.assign({}, this.data);
          tsdiachi = this.LowerCasePropertyList([tsdiachi])[0];
          console.log("địa chỉ: ",tsdiachi)
        this.diachi.daydiachi = tsdiachi.diachi
          tsdiachi.tinh_id = this.$root.token.getPhanVungID()
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
        this.diachi.so_nha = tsdiachi.sonha;
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
    },

    getTinh: async function () {
      try {
        this.$root.showLoading(true);
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_thongtin_tinh"
        );
        console.log(rs.data);
        this.dsTinh.push(rs.data);
      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false);
      }
    },
    getQuan: async function () {
      try {
        this.$root.showLoading(true);
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_quan_huyen/" + this.diachi.tinh_id
        );
        this.dsQuan = rs.data;
        if(this.diachi.quan_id && this.dsQuan)
         this.tenquan = this.dsQuan.filter(
           (x) => x.QUAN_ID == this.diachi.quan_id
         )[0].TEN_QUAN;
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getPhuong: async function () {
      try {
        this.$root.showLoading(true);
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_phuong_xa/" + this.diachi.quan_id
        );
        this.dsPhuong = rs.data;
         if(this.diachi.phuong_id && this.dsPhuong){
         this.tenphuong = this.dsPhuong.filter(
          x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG;}
      } catch (error) {
        console.log(error)

      } finally {
        this.$root.showLoading(false);
      }
    },
    getPhoApKhu: async function () {
      try {
        this.$root.showLoading(true);
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/" +
            this.diachi.phuong_id
        );
        if (rs.data != null) {
          this.dsPho = rs.data.dsPho;
          if(this.diachi.pho_id && this.dsPho)
           this.tenpho = this.dsPho.filter(
          (x) => x.PHO_ID == this.diachi.pho_id
         )[0].TEN_PHO;
          this.dsAp = rs.data.dsAp;
         if(this.diachi.ap_id && this.dsAp)
           this.tenap = this.dsAp.filter(
          (x) => x.PHO_ID == this.diachi.ap_id
        )[0].TEN_PHO;
          this.dsKhu = rs.data.dsKhu;
           if(this.diachi.khu_id && this.dsKhu)
            this.tenkhu = this.dsKhu.filter(
          (x) => x.PHO_ID == this.diachi.khu_id
        )[0].TEN_PHO;
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getDacDiem: async function () {
      try {
        this.$root.showLoading(true);
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_dacdiem",
          {
            phuong_id: this.phuong_id == null ? 0 : this.phuong_id,
            pho_id: this.pho_id == null ? 0 : this.pho_id,
            ap_id: this.ap_id == null ? 0 : this.ap_id,
            khu_id: this.khu_id == null ? 0 : this.khu_id,
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
            this.tenquan = this.dsQuan.filter(
           (x) => x.QUAN_ID == this.diachi.quan_id
         )[0].TEN_QUAN;
    },
       onChangePhuong: async function () {
      await this.getPhoApKhu();
           this.tenphuong = this.dsPhuong.filter(
          x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG;
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
