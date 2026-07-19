<template src="./TraCuuThongTinDatCocChonSo.html"></template>
<style src="./TraCuuThongTinDatCocChonSo.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import TraCuuAPI from '../api/TraCuuAPI'
export default {
  components: { breadcrumb },
  name: "TraCuuThongTinDatCocChonSo",
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
        MaKH: '',
        TenKH: '',
        lists:undefined,

    };
  },
 methods: {
 async   doSearch () {
       TraCuuAPI.TraCuuThongTinDatCocChonSo(this.axios, {
              kieu_datcoc: this.KieuDatCoc,
              ten_tb: this.TenKH,
              ma_kh: this.MaKH,
              so_tb: this.SoMay
              })
              .then(resp => {
                this.lists= resp.data.data
              console.warn(resp.data.data)
             })
    }
  },

};
</script>

