<template src="./TraCuuThongTinCaNhanDoanhNghiep.html"></template>
<style src="./TraCuuThongTinCaNhanDoanhNghiep.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import TraCuuAPI from "../api/TraCuuAPI";
export default {
  components: { breadcrumb },
  name: "TraCuuThongTinCaNhanDoanhNghiep",
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
        title: "TRA CỨU CẬP NHẬT THÔNG TIN CÁ NHÂN/DOANH NGHIỆP",
        list: [
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          {
            name: "Tra cứu trên CCBS/Tra cứu cập nhật thông tin cá nhân,doanh nghiệp",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      ma_cq: "",
      so_tb: "",
      loai: "1",
      page: "1",
      lists: undefined,
      loaiKHDN: "",
      loaiKHCN: "",
    };
  },
  methods: {
    nhapMoi() {
  this.so_tb =""
  this.ma_cq=""
  this.loai= true
  },
    async doSearch() {
      /*  if(this.loaiKHDN===''){ this.loai = '2'}
     if(this.loaiKHDN==='2'){ this.loai = '2'}
     if(this.loaiKHDN==='1'){ this.loai = '1'}
     else{ this.loai = this.loaiKHDN} */

      TraCuuAPI.TraCuuThongTinCaNhanDoanhNghiep(this.axios, {
        ma_cq: this.ma_cq,
        so_tb:  this.so_tb,
        loai: this.loai,
        page: this.page,
      }).then((res) => {
        this.lists = res.data.data;
        console.warn(res.data.data);
      });
    },
  },


};
</script>

