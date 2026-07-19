<template src="./TraCuuDangKyGoiCuocKMCBDATA.html"></template>
<style src="./TraCuuDangKyGoiCuocKMCBDATA.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";


import TraCuuAPI from '../api/TraCuuAPI';
//import PopupALO from './popup/PopupALO.vue'



export default {
  components: { breadcrumb,
  // appPopupALO: PopupALO,
    },
  name: "TraCuuDangKyGoiCuocKMCBDATA",
  mounted() {
    // this.post(this.$API_URL + 'executor/sel_lay_thang_no', {}).then(
    //   (response) => {
    //     this.sel_lay_thang_no = response.data.data
    //   }
    // )
    // this.post(this.$API_URL + 'executor/fn_chukyno_kyhoadon_sel', {
    //   namthang: '',
    // }).then((response) => {
    //   this.fn_chukyno_kyhoadon_sel = response.data.data
    // })
  },
  destroyed() {},
  data() {
    return {
      header: {
        title: "TRA CỨU KHÁCH HÀNG ĐẶT CỌC CHỌN SỐ/ĐẶT CỌC IPHONE",
        list: [
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          {
            name: "Tra cứu trên CCBS/Tra cứu khách hàng đặt cọc chọn số/đặt cọc iPhone",
            link: { name: "Ui.buttons" },
          },
        ],
      },
        KieuDatCoc: '',
         SoMay: '',

        lists:undefined,
        listTB:undefined,
        listTK:undefined,
        listGoi:undefined,
        listGoialos:undefined,
        listGoisd:undefined,
        LOAI_TB: '',
        MA_TINH:'',
        SO_MSIN:'',
        PIN:'',
        PUK:'',
        NGAY_KH:'',
        PIN2:'',
        PUK2:'',
        NGAY_KH:'',
        GOI_DI:'',
        GOI_DEN:'',
        MaKH: '',
        TEN_KH: '',
        DCHI_KH:'',
        DIACHI_CHUNGTU:'',
        NGAYSINH:'',
        NGAYCAP:'',
        SOCMT:'',
        DOITUONG:'',
        REMAIN:'0',
        ten_goi:'',
        GOI_SD:'',
        dsLoaiHinh: [],
    };
  },
 methods: {
 async   doSearch () {

  this.ten_goi =""
  this.GOI_SD=""


       TraCuuAPI.TraCuuDangKyGoiCuocKMCBDATA(this.axios, {

              so_tb: this.SoMay
              })
              .then(resp => {
                this.lists= resp.data.data
                this.LOAI_TB= this.lists[0].LOAI_TB
                this.MA_TINH= this.lists[0].MA_TINH
               this.SO_MSIN= this.lists[0].SO_MSIN
                this.PIN= this.lists[0].PIN
                this.PUK= this.lists[0].PUK
               this.NGAY_KH= this.lists[0].NGAY_KH
                this.PIN2= this.lists[0].PIN2
                this.PUK2= this.lists[0].PUK2
                this.GOI_DI= this.lists[0].GOI_DI
                this.GOI_DEN= this.lists[0].GOI_DEN
                this.TEN_KH= this.lists[0].TEN_KH
                this.DCHI_KH= this.lists[0].DCHI_KH
                this.NGAYSINH= this.lists[0].NGAYSINH
                this.NGAYCAP= this.lists[0].NGAYCAP
                this.SOCMT= this.lists[0].SOCMT

             })
        TraCuuAPI.TraCuuDangTTTBTS(this.axios, {
              ma_tinh: this.MA_TINH,
              so_tb: this.SoMay
              })
              .then(resp => {
                  if (resp.data.error_code === "BSS-00000000") {
                this.listTB = resp.data.data
                this.DIACHI_CHUNGTU= this.listTB[0].DIACHI_CHUNGTU
                this.DOITUONG= this.listTB[0].DOITUONG
                  }
              })

              TraCuuAPI.TraCuuTaiKhoan(this.axios, {
              ma_tinh: this.MA_TINH,
              so_tb: this.SoMay
              })
              .then(resp => {
                  if (resp.data.error_code === "BSS-00000000") {
                this.listTK = resp.data.data
                 if (this.listTK[3].ID ==="1" ) {
                         this.REMAIN= "0"
                 }}
              })

       await TraCuuAPI.TraCuuGoiCuoc(this.axios, { so_tb: this.SoMay })
              .then(resp => {
          if (resp.data.error_code === "BSS-00000000") {

             this.listGoialos= resp.data.data.result.ds_goi
             this.listGoi = resp.data.data.result.ds_goi.length
          for (var i =0; i < this.listGoi ; i++) {
                this.ten_goi = this.ten_goi  + resp.data.data.result.ds_goi[i].tengoi+ ","
                 }
 }
              })
            /*  console.log( this.ten_goi)*/

            await   TraCuuAPI.TraCuuGoiCuocDangSD(this.axios, {
                 ma_tinh: this.MA_TINH,so_tb: this.SoMay
                 })
              .then(resp => {
             if (resp.data.error_code === "BSS-00000000") {
              this.GOI_SD= resp.data.data.result
              /*console.log(resp.data.data.result)*/
              console.log(resp)
             }

             })






    },
    async modalToggle(show) {
    if(show) {
      this.$refs['modal-detail'].show()
    } else {
      this.$refs['modal-detail'].hide()
    }

  }
  },


};
</script>
