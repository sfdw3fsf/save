<template src="./DanhSachThueBao.html"></template>

<script>
export default {
  name: 'DanhSachThueBao',
  data() {
    return {
      dsThueBao: [],
      dsPhieuHoTro: [],
      tableColumns: {
        dsThueBao: [
          { fieldName: 'agent_', headerText: 'Mã thuê bao', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'product_', headerText: 'Tên thuê bao', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'format_', headerText: 'Mã khách hàng', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'type_', headerText: 'Mã KH', allowFiltering: true, allowHtml: true, width: 200, textAlign: 'center' },
          { fieldName: 'user_', headerText: 'Tên KH', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'agent_', headerText: 'Dịch vụ', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'agent_', headerText: 'Loại hình', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'agent_', headerText: 'SDT', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'agent_', headerText: 'Trạng thái TB', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' },
          { fieldName: 'agent_', headerText: 'Loại KH', allowFiltering: true, allowHtml: true, width: 100, textAlign: 'center' }
        ],
        
        selected: {}
      }
    }
  }
}
</script>
