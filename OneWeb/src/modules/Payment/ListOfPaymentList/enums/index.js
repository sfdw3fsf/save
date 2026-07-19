export default {
  headerGridboxDSPTT: [
    { fieldName: 'ngay_tt', headerText: 'Ngày TT', allowFiltering: true, allowSorting: true, textAlign: 'right', type:'date', format: 'dd/MM/yyyy'},
    { fieldName: 'ma_gd', headerText: 'Mã GD', allowFiltering: true, allowSorting: true },
    { fieldName: 'ten_loaihd', headerText: 'Loại HĐ', allowFiltering: true, allowSorting: true },
    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: true },
    { fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: true },
    { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowFiltering: true, allowSorting: true },
    { fieldName: 'soseri', headerText: 'Số seri', allowFiltering: true, allowSorting: true },
    { fieldName: 'ht_tra', headerText: 'Hình thức TT', allowFiltering: true, allowSorting: true },
    {
      fieldName: 'tongtien',
      headerText: 'Tổng tiền',
      allowFiltering: true,
      allowSorting: true,
      textAlign: 'right'
    },
    {
      fieldName: 'ngay_hen',
      headerText: 'Ngày hẹn TT',
      allowFiltering: true,
      allowSorting: true
    },
    {
      fieldName: 'link_qr',
      headerText: 'Link QR',
      allowFiltering: true,
      allowSorting: true
    }
  ]
}