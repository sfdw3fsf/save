<template src="./LichSuCSKH.html"></template>
<script>
export default {
  name: 'LichSuCSKH',
  data() {
    return {
      dsLichSuCSKH: [],
      tableColumns: {
        dsLichSuCSKH: [
          { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'ngay_cskh', headerText: 'Ngày CSKH', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'ma_cs', headerText: 'Mã chăm sóc', allowFiltering: true, allowHtml: true, width: 150, textAlign: 'center' },
          { fieldName: 'ma_khachhang', headerText: 'Mã khách hàng', allowFiltering: true, allowHtml: true, width: 200, textAlign: 'center' },
          { fieldName: 'ct_cskh', headerText: 'Chương trình CSKH', allowFiltering: true, allowHtml: true, width: 250, textAlign: 'center' },
          { fieldName: 'ngay_thuchien', headerText: 'Ngày thực hiện', allowFiltering: true, allowHtml: true, width: 300, textAlign: 'center' },
          { fieldName: 'nguoi_duoc_cs', headerText: 'Người được CS', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'trangthai_phieu', headerText: 'Trạng thái phiếu', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' }
        ],
        selected: {}
      }
    }
  },
  methods:{
    gridLichSuCS_selectedRowChanged(event){
        // console.log(event);
    }
  }
}
</script>
