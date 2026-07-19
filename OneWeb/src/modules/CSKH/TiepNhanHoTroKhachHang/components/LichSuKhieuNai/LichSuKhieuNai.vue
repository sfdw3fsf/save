<template src="./LichSuKhieuNai.html"></template>

<script>
export default {
  name: 'LichSuKhieuNai',
  data() {
    return {
      dsKhieuNai: [],
      tableColumns: {
        dsKhieuNai: [
          { fieldName: 'chude', headerText: 'Chủ đề khiếu nại', allowFiltering: true, allowHtml: true, width: 150, textAlign: 'center' },
          { fieldName: 'ngay_tiepnhan', headerText: 'Ngày tiếp nhận', allowFiltering: true, allowHtml: true, width: 120, textAlign: 'center' },
          { fieldName: 'noidung_kn', headerText: 'Nội dung KN', allowFiltering: true, allowHtml: true, width: 200, textAlign: 'center' },
          { fieldName: 'nguoi_tiepnhan', headerText: 'Người tiếp nhận', allowFiltering: true, allowHtml: true, width: 150, textAlign: 'center' },
          { fieldName: 'ngay_giaiquyet', headerText: 'Ngày giải quyết', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'nguoi_giaiquyet', headerText: 'Người giải quyết', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'ma_khieunai', headerText: 'Mã khiếu nại', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'tt_khieunai', headerText: 'Trạng thái KN', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' }
        ],
        selected: {}
      }
    }
  },
  methods:{
    gridKhieuNai_selectedRowChanged(event){
        // console.log(event);
    }
  }
}
</script>
