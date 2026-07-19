<template src="./SearchDepositCustomersChooseNumber.html"></template>
<style src="./SearchDepositCustomersChooseNumber.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb';
import TraCuuAPI from './api/TraCuuAPI';

export default {
  components: { breadcrumb },
  name: "SearchDepositCustomersChooseNumber",
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
  KieuDatCoc: '1',
         SoMay: '',
        MaKH: '',
        TenKH: '',
        lists:undefined,
      dataGrid: {
        columns: [
          { fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false },
          { fieldName: 'MA_TB', headerText: 'Số máy', allowFiltering: true, allowSorting: false },
          { fieldName: 'DATCOC', headerText: 'Đặt cọc / cam kết / tháng', allowFiltering: true, allowSorting: false },
          { fieldName: 'NGAY_HM', headerText: 'Ngày hòa mạng', allowFiltering: true, allowSorting: false },
          { fieldName: 'CUOC_CAMKET_BD', headerText: 'Cước cam kết ban đầu', allowFiltering: true, allowSorting: false },
          { fieldName: 'CUOC_CAMKET_HT', headerText: 'Cước cam kết hiện tại', allowFiltering: true, allowSorting: false },
          { fieldName: 'CUOC_CAMKET_SHT', headerText: 'Cước cam kết sau tháng hiện tại', allowFiltering: true, allowSorting: true }
        ],
        dataSource: []
      }
    };

  },
 methods: {


 /*async doSearch(KieuDatCoc, TenKH,MaKH,SoMay){
            this.loading(true)
            try{
                let params = {
                    "kieu_datcoc": KieuDatCoc,
                    "ten_tb": TenKH,
                    "ma_kh": MaKH,
                    "so_tb": SoMay
                }
                await TraCuuAPI.TraCuuThongTinDatCocChonSo(this.axios, {
                    "ds_para": JSON.stringify(params)
                }).then(response => {
                    this.loading(false)
                    if (response && response.data && response.data.error_code === 'BSS-00000000') {
                         let res = JSON.parse(response.data.data)
                        
                      if(res.ERROR_CODE == 1)
                            this.dsVatTu.list = JSON.parse(rest.RESULT.DTVTDAGAN)
                        else
                            this.dsVatTu.list = []
                    }
                })
            } catch(e){
                this.$toast.error('Có lỗi trong quá trình lấy dữ liệu ' + e)
                this.loading(false)
           }
        }
    }

 async   doSearch () {
       TraCuuAPI.TraCuuThongTinDatCocChonSo(this.axios, {
              kieu_datcoc: this.KieuDatCoc,
              ten_tb: this.TenKH,
              ma_kh: this.MaKH, 
              so_tb: this.SoMay
              }) 
              .then(resp => {
               if (response && response.data && response.data.error_code === 'BSS-00000000') {
                        let res = JSON.parse(response.data.data)
                        
                   
                         if(res.ERROR_CODE == 1)
                            this.dsVatTu.list = JSON.parse(res.RESULT.dt)
                        else
                            this.dsVatTu.list = []
                    }           
             })
    }
  }*/



async doSearch () {
    
         if (this.SoMay.length !== 9) {
            this.$toast.error('Số ký tự ở ô số máy phải là 9 ký tự!')
            return false
          }
          else
          {
              this.loading(true)
        
      try {
     
        const response = await TraCuuAPI.TraCuuThongTinDatCocChonSo(this.axios, { kieu_datcoc: this.KieuDatCoc,
              ten_tb: this.TenKH,
              ma_kh: this.MaKH, 
              so_tb: this.SoMay })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') this.dataGrid.dataSource = data.data
        else this.dataGrid.dataSource = []
      } 
      catch (e) {
        this.$toast.error('Lấy danh sách port không thành công')
      } finally {
        this.loading(false)
      }
    }
}
  },

};
</script>