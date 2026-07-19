<template src="./LichSuBaoHong.html"></template>
<script>
export default {
  name: 'LichSuBaoHong',
  data() {
    return {
      dsLichSuBaoHong: [],
      tableColumns: {
        dsLichSuBaoHong: [
          { fieldName: 'tinh', headerText: 'Tỉnh', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'ma_thuebao', headerText: 'Mã thuê bao', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'ma_khachhang', headerText: 'Mã khách hàng', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'ten_thuebao', headerText: 'Tên thuê bao', allowFiltering: true, allowHtml: true, width: 200, textAlign: 'center' },
          { fieldName: 'loai_hinh', headerText: 'Loại hình', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'diachi', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowHtml: true, width: 300, textAlign: 'center' },
          { fieldName: 'so_ao', headerText: 'Số ảo', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'dichvu', headerText: 'Dịch vụ', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'so_giayto', headerText: 'Số giấy tờ', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' }
        ],
        selected: {}
      }
    }
  },
  methods: {
    gridBaoHong_selectedRowChanged(event){
        //console.log(event);
    }
  }
}
</script>
