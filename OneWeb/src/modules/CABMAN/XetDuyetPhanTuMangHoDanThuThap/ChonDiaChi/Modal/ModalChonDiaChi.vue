<template src="./ModalChonDiaChi.html"></template>
<script>
import TraCuuBanDoMangCap from '../../../../ECMS/tracuu-bando-mangcap/Modal'
export default {
  name: "ModalChonDiaChi",
  props: {
    modalId: String,
    showThongTinThem: {
      type: Boolean,
      default: false,
    },
    isStrictMode:{
      type: Boolean,
      default: true,
    },
    data:Object,
  },
    // ["modalId", "data","isStrictMode"],
  components:{TraCuuBanDoMangCap},
  created: async function () {
   this.dsTinh=[]
   this.diachi.tinh_id = null
  },

  computed: {
    thaydoiDiaChi: function () {

      if (this.tentinh != "") {
        this.diachi.diachimoi = this.tentinh;
        if (this.tenquan)
          this.diachi.diachimoi = this.tenquan + ", " + this.diachi.diachimoi;
        if (this.tenphuong)
          this.diachi.diachimoi = this.tenphuong + ", " + this.diachi.diachimoi;
        if (this.isPho && this.diachi.pho_id)
          this.diachi.diachimoi = this.tenpho + ", " + this.diachi.diachimoi;
        if (this.isAp && this.diachi.ap_id)
          this.diachi.diachimoi = this.tenap + ", " + this.diachi.diachimoi;
        if (this.isKhu && this.diachi.khu_id)
          this.diachi.diachimoi = this.tenkhu + ", " + this.diachi.diachimoi;
        if (this.diachi.so_nha)
          this.diachi.diachimoi = this.diachi.so_nha + ", " + this.diachi.diachimoi;
        return this.diachi.diachimoi;
      }
      return "";
    },
    getDiaChi(){
      return {...this.diachi.diachimoi}
    }
  },
  watch: {
    isPho: async function (val){
      await this.getDacDiem()
    },
    isAp: async function (val){
      await this.getDacDiem()
    },
    isKhu: async function (val){
      await this.getDacDiem()
    },
  },
  methods: {
       onModalHidden() {
            this.dsTinh=[]
            this.diachi.tinh_id = null
            this.diachi.quan_id = null
            this.diachi.phuong_id = null
            this.diachi.pho_id = null
            this.diachi.ap_id = null
            this.diachi.khu_id = null
            this.diachi.dacdiem_id = null
            this.diachi.so_nha=""
            this.diachi.diachimoi=""
            this.diachi.daydiachi=""
        },
    focusMyElement: async function () {
      await this.getTinh();
      if (await this.IS_CHON_DIACHI_CHITIET()) {
        this.isEnableThongTinThem = true
      }
      if (this.data != null) {
        this.diachi.daydiachi = this.data.diachi
        this.diachi.diachimoi = this.data.diachi;
        if (this.data.tinh_id != null && this.data.tinh_id != 0) {
          this.diachi.tinh_id = this.data.tinh_id;
          let tinh = this.dsTinh.filter(
            (x) => x.TINH_ID == this.diachi.tinh_id
          )
          this.tentinh = tinh.length > 0 ? tinh[0].TENTINH : "";
          await this.getQuan();
          this.diachi.quan_id = this.data.quan_id;
          let quan = this.dsQuan.filter(
            (x) => x.QUAN_ID == this.data.quan_id
          )
          this.tenquan = quan.length > 0 ? quan[0].TEN_QUAN : "";
          await this.getPhuong();
          this.diachi.phuong_id = this.data.phuong_id;
          let phuong = this.dsPhuong.filter(
            x => x.PHUONG_ID == this.data.phuong_id)
          this.tenphuong = phuong.length > 0 ? phuong[0].TEN_PHUONG : "";
          await this.getPhoApKhu()
          if (this.data.phuong_id != null) {
            if (this.data.pho_id != null && this.data.pho_id != 0) {
              this.diachi.pho_id = this.data.pho_id;
              this.isPho = true
              let pho = this.dsPho.filter(
                (x) => x.PHO_ID == this.data.pho_id
              )
              this.tenpho = pho.length > 0 ? pho[0].TEN_PHO : "";
            }

            if (this.data.ap_id != null && this.data.ap_id != 0) {
              this.diachi.ap_id = this.data.ap_id;
              this.isAp = true
              let ap = this.dsAp.filter(
                (x) => x.PHO_ID == this.data.ap_id
              )
              this.tenap = ap.length > 0 ? ap[0].TEN_PHO : "";
            }

            if (this.data.khu_id != null && this.data.khu_id != 0) {
              this.diachi.khu_id = this.data.khu_id;
              this.isKhu = true
              let khu = this.dsKhu.filter(
                (x) => x.PHO_ID == this.data.khu_id
              )
              this.tenkhu = khu.length > 0 ? khu[0].TEN_PHO : "";
            }

            await this.getDacDiem()
            if (this.data.dacdiem_id != null && this.data.dacdiem_id != 0) {
              this.diachi.dacdiem_id = this.data.dacdiem_id
              this.isDacDiem = true
              this.isEnabledDacDiem = true
            }
          }
          this.diachi.so_nha = this.data.so_nha;
          this.diachi.daydiachi = this.data.diachi;
          this.diachi.diachimoi = this.data.diachi;

          this.diachi.block = this.data.block ? this.data.block : ""
          this.diachi.tang = this.data.tang ? this.data.tang : ""
          this.diachi.sophong = this.data.sophong ? this.data.sophong : ""
          this.diachi.motathem = this.data.motathem ? this.data.motathem : ""


        } else {
          if (this.dsTinh != null && this.dsTinh.length > 0)
            this.diachi.tinh_id = this.$auth.getPhanVungID();
          let tinh = this.dsTinh.filter(
            (x) => x.TINH_ID == this.diachi.tinh_id
          )
          this.tentinh = tinh.length > 0 ? tinh[0].TENTINH : "";
          await this.getQuan();
        }
      } else {
        if (this.dsTinh != null && this.dsTinh.length > 0)
          this.diachi.tinh_id = this.$auth.getPhanVungID();
        let tinh = this.dsTinh.filter(
          (x) => x.TINH_ID == this.diachi.tinh_id
        )
        this.tentinh = tinh.length > 0 ? tinh[0].TENTINH : "";
        await this.getQuan();
      }
    },

    getTinh: async function () {
      try {
        this.$root.showLoading(true);
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_thongtin_tatca_tinh"
        );
        this.dsTinh = rs.data;

      } catch (error) {
        console.log(error)
      } finally {
        this.$root.showLoading(false);
      }
    },
    getQuan: async function () {
      try {
        if (!this.diachi.tinh_id) return
        this.$root.showLoading(true);
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_quan_huyen/" + this.diachi.tinh_id
        );
        this.dsQuan = rs.data;
        if(this.diachi.quan_id!=null)
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
        if (!this.diachi.quan_id) return
        this.$root.showLoading(true);
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_ds_phuong_xa/" + this.diachi.quan_id
        );
        this.dsPhuong = rs.data;
         if(this.diachi.phuong_id!=null){
           let phuong = this.dsPhuong.filter(x => x.PHUONG_ID == this.diachi.phuong_id)
           if(phuong.length > 0)
             this.tenphuong = phuong[0].TEN_PHUONG;
         }
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
          if (this.dsPho.length > 0) this.diachi.pho_id = this.dsPho[0].PHO_ID
          if (this.diachi.pho_id != null && this.dsPho.length > 0)
            this.tenpho = this.dsPho.filter(
              (x) => x.PHO_ID == this.diachi.pho_id
            )[0].TEN_PHO;
          this.dsAp = rs.data.dsAp;
          if (this.dsAp.length > 0) this.diachi.ap_id = this.dsAp[0].PHO_ID
          if (this.diachi.ap_id != null && this.dsAp.length > 0)
            this.tenap = this.dsAp.filter(
              (x) => x.PHO_ID == this.diachi.ap_id
            )[0].TEN_PHO;
          this.dsKhu = rs.data.dsKhu;
          if (this.dsKhu.length > 0) this.diachi.khu_id = this.dsKhu[0].PHO_ID
          if (this.diachi.khu_id != null && this.dsKhu.length > 0)
            this.tenkhu = this.dsKhu.filter(
              (x) => x.PHO_ID == this.diachi.khu_id
            )[0].TEN_PHO;
          await this.getDacDiem()
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    // getDacDiem: async function () {
    //   try {
    //     this.$root.showLoading(true);
    //     var rs = await this.$root.context.get(
    //       "/web-cabman/bando_tuyencot/lay_ds_dacdiem",
    //       {
    //         phuong_id: this.phuong_id == null ? 0 : this.phuong_id,
    //         pho_id: this.pho_id == null ? 0 : this.pho_id,
    //         ap_id: this.ap_id == null ? 0 : this.ap_id,
    //         khu_id: this.khu_id == null ? 0 : this.khu_id,
    //       }
    //     );
    //     this.dsDacDiem = rs.data;
    //   } catch (error) {
    //   } finally {
    //     this.$root.showLoading(false);
    //   }
    // },
    getDacDiem: async function () {
      let ipho_id = 0, ikhu_id = 0, iap_id = 0, iphuong_id = 0
      if (this.diachi.phuong_id)
        iphuong_id = this.diachi.phuong_id
      if (this.dsPho.length > 0 && this.isPho){
        if (this.diachi.pho_id)
          ipho_id = this.diachi.pho_id
      }
      if (this.dsAp.length > 0 && this.isAp){
        if (this.diachi.ap_id)
          iap_id = this.diachi.ap_id
      }
      if (this.dsKhu.length > 0 && this.isKhu){
        if (this.diachi.khu_id)
          ikhu_id = this.diachi.khu_id
      }
      if (iphuong_id == 0 && ipho_id == 0 && ikhu_id == 0 && iap_id == 0){
        this.dsDacDiem = []
        this.isDacDiem = false
        this.isEnabledDacDiem = false
      }else{
        try {
          this.dsDacDiem = []
          this.$root.showLoading(true);
          let rs = await this.$root.context.get(
            "/web-cabman/bando_tuyencot/lay_ds_dacdiem",
            {
              phuong_id: iphuong_id,
              pho_id: ipho_id,
              ap_id: iap_id,
              khu_id: ikhu_id,
            }
          );
          this.dsDacDiem = rs.data;
          if (this.dsDacDiem.length > 0){
            this.diachi.dacdiem_id = this.dsDacDiem[0].DACDIEM_ID
            this.isDacDiem = false
            this.isEnabledDacDiem = true
          }else {
            this.isDacDiem = false
            this.isEnabledDacDiem = false
          }
        } catch (error) {
        } finally {
          this.$root.showLoading(false);
        }
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
        this.$root.toastError("Hãy chọn Phố / Ấp / Khu");
        return;
      }
      if (!this.phaichondacdiem) {
        if (this.dsDacDiem.length > 0 && !this.isDacDiem) {
          this.$bvModal.msgBoxConfirm("Bạn thật sự không muốn chọn đặc điểm ?",
            {
              title: "Thông báo",
              centered: true,
              size: "sm",
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
            }).then((value) => {
            if (value) {
              // if (this.dsDacDiem.length > 0 && this.isDacDiem){
              //   if (!this.diachi.dacdiem_id){
              //     this.$root.toastError("Hãy chọn đặc điểm!")
              //     this.$refs.dacdiem.focus()
              //     return
              //   }
              // }
              let dc = {
                diachi_id: this.diachi.diachi_id ? this.diachi : 0,
                tinh_id: this.diachi.tinh_id ? this.diachi.tinh_id : 0,
                quan_id: this.diachi.quan_id ? this.diachi.quan_id : 0,
                phuong_id: this.diachi.phuong_id ? this.diachi.phuong_id : 0,
                pho_id: this.isPho && this.diachi.pho_id ? this.diachi.pho_id : 0,
                ap_id: this.isAp && this.diachi.ap_id ? this.diachi.ap_id : 0,
                khu_id: this.isKhu && this.diachi.khu_id ? this.diachi.khu_id : 0,
                dacdiem_id: this.diachi.dacdiem_id && this.isDacDiem ? this.diachi.dacdiem_id : 0,
                so_nha: this.diachi.so_nha,
                daydiachi: this.diachi.daydiachi,
                diachimoi: this.diachi.diachimoi,

                longtitude: this.diachi.longtitude ? this.diachi.longtitude : 0,
                lattitude: this.diachi.lattitude ? this.diachi.lattitude : 0,
                block: this.diachi.block,
                tang: this.diachi.tang,
                sophong: this.diachi.sophong,
                motathem: this.diachi.motathem,
              }
              this.$emit("xacnhan", {...dc});
              this.$bvModal.hide(this.modalId);
            } else {
              return
            }
          })
        } else {
          let dc = {
            diachi_id: this.diachi.diachi_id ? this.diachi : 0,
            tinh_id: this.diachi.tinh_id ? this.diachi.tinh_id : 0,
            quan_id: this.diachi.quan_id ? this.diachi.quan_id : 0,
            phuong_id: this.diachi.phuong_id ? this.diachi.phuong_id : 0,
            pho_id: this.isPho && this.diachi.pho_id ? this.diachi.pho_id : 0,
            ap_id: this.isAp && this.diachi.ap_id ? this.diachi.ap_id : 0,
            khu_id: this.isKhu && this.diachi.khu_id ? this.diachi.khu_id : 0,
            dacdiem_id: this.isDacDiem && this.diachi.dacdiem_id ? this.diachi.dacdiem_id : 0,
            so_nha: this.diachi.so_nha,
            daydiachi: this.diachi.daydiachi,
            diachimoi: this.diachi.diachimoi,

            longtitude: this.diachi.longtitude ? this.diachi.longtitude : 0,
            lattitude: this.diachi.lattitude ? this.diachi.lattitude : 0,
            block: this.diachi.block,
            tang: this.diachi.tang,
            sophong: this.diachi.sophong,
            motathem: this.diachi.motathem,
          }
          this.$emit("xacnhan", {... dc});
          this.$bvModal.hide(this.modalId);
        }
      } else {
        if (this.dsDacDiem.length > 0) {
          if (!this.isDacDiem) {
            this.isDacDiem = true
            this.$root.toastError("Hãy chọn đặc điểm!")
            this.$refs.dacdiem.focus()
            return
          }
          if (!this.diachi.dacdiem_id) {
            this.$root.toastError("Hãy chọn đặc điểm!")
            this.$refs.dacdiem.focus()
            return
          }
          this.$emit("xacnhan", this.diachi);
          this.$bvModal.hide(this.modalId);
        }
      }
    },
    btnTraCuuBanDoMangCap(){
         if (this.diachi.diachimoi.trim() !== ""){
           this.$bvModal.show("popupTraCuuBanDoMangCap")
         }else {
           this.$root.toastError("Chưa có thông tin về bản đồ khách hàng")
         }
    },
    async IS_CHON_DIACHI_CHITIET(){
     try {
       let rs = await this.$root.context.get("/web-cabman/danhmuc/isExistMaTS", {ma_ts:'IS_CHON_DIACHI_CHITIET'})
       if (rs.data){
         return rs.data.result
       }else return false
     }catch (e){

     }

    }
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
        dacdiem_id: null,
        so_nha: "",
        daydiachi: "",
        diachimoi: "",

        longtitude:0,
        lattitude:0,
        block:"",
        tang:"",
        sophong:"",
        motathem: "",
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

      isEnableThongTinThem: false,
      isEnabledDacDiem:false,
      phaichondacdiem:false,
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
.form-chondiachi .select2-container .select2-selection--single  {
    overflow: hidden;
}
.form-chondiachi .select2-container .select2-selection--single .select2-selection__rendered {
    white-space: initial;
}
</style>
