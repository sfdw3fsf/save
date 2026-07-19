<template src="./LichSuDonHang.html"></template>

<script>
export default {
  name: 'LichSuDonHang',
  data() {
    return {
      dsLichSuDonHang: [],
      tableColumns: {
        dsLichSuDonHang: [
          { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'ngay_lendon', headerText: 'Ngày lên đơn', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'ma_kh', headerText: 'Mã khách hàng', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'ma_donhang', headerText: 'Mã đơn hàng', allowFiltering: true, allowHtml: true, width: 200, textAlign: 'center' },
          { fieldName: 'dichvu', headerText: 'Dịch vụ', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'goi_dichvu', headerText: 'Gói dịch vụ', allowFiltering: true, allowHtml: true, width: 300, textAlign: 'center' },
          { fieldName: 'diachi_lapdat', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowHtml: true, width: 300, textAlign: 'center' },
          { fieldName: 'trangthai_don', headerText: 'Trạng thái đơn', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' }
        ],
        selected: {}
      }
    }
  },
    methods: {
        gridLichSuDonHang_selectedRowChanged(event){
            //console.log(event);
        }
    }
}
</script>
