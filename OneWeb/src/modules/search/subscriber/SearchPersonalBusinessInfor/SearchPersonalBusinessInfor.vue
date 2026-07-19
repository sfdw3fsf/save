<template src="./SearchPersonalBusinessInfor.html"></template>
<style src="./SearchPersonalBusinessInfor.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";


import TraCuuAPI from './api/TraCuuAPI';
export default {
  components: { breadcrumb },
  name: "SearchPersonalBusinessInfor",
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
       
       dataGrid: {
        columns: [
          { fieldName: 'SOMAY', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false },
          { fieldName: 'MA_KH', headerText: 'Mã khách hàng', allowFiltering: true, allowSorting: false },
          { fieldName: 'MA_CQ', headerText: 'Mã cơ quan', allowFiltering: true, allowSorting: false },
          { fieldName: 'TEN_TB', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: false },
          { fieldName: 'DIACHI', headerText: 'Địa chỉ', allowFiltering: true, allowSorting: false },
          { fieldName: 'TEN', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false }
        ],
        dataSource: []
      }
    };
  },
  methods: {
    nhapMoi() {
  this.so_tb =""
  this.ma_cq=""
  this.loai= true
  },
  



async doSearch () {
    
       
              this.loading(true)
        
      try {
     
        const response = await TraCuuAPI.TraCuuThongTinCaNhanDoanhNghiep(this.axios, { kieu_datcoc: this.KieuDatCoc,
              ma_cq: this.ma_cq,
        so_tb:  '84'+ this.so_tb,
        loai: this.loai,
        page: this.page, })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') this.dataGrid.dataSource = data.data
        else this.dataGrid.dataSource = []
      } 
      catch (e) {
        this.$toast.error('Lấy danh sách thuê bao không thành công')
      } finally {
        this.loading(false)
      }
    }






  },

 
};
</script>

