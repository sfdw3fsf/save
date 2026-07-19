<template src="./LichSuHoTro.html"></template>

<script>
export default {
  name: 'LichSuHoTro',
  data() {
    return {
      dsLichSuHoTro: [],
      tableColumns: {
        dsLichSuHoTro: [
          { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'ngay_tiepnhan', headerText: 'Ngày tiếp nhận', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'ma_hotro', headerText: 'Mã hỗ trợ', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'loai_hotro', headerText: 'Loại Hỗ trợ', allowFiltering: true, allowHtml: true, width: 200, textAlign: 'center' },
          { fieldName: 'chude', headerText: 'Chủ đề', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'yeucau_ht', headerText: 'Yêu cầu HT', allowFiltering: true, allowHtml: true, width: 300, textAlign: 'center' },
          { fieldName: 'ketqua_xl', headerText: 'Kết quả XL', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'dichvu', headerText: 'Dịch vụ', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' }
        ],
        selected: {}
      }
    }
  },
  methods: {
   gridHoTro_selectedRowChanged(event){
    // console.log(event);
   }
  }
}
</script>
