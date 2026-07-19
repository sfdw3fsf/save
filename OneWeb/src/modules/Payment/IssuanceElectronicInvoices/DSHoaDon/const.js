export default {
  dsCols: [
    {
      fieldName: "ma_kh",
      headerText: "Mã khách hàng",
      allowFiltering: true,
      allowSorting: false,
      width: 140,
    },
    {
      fieldName: "stt_hd",
      headerText: "STT hóa đơn",
      allowFiltering: true,
      allowSorting: false,
      width: 120,
    },
    {
      fieldName: "ten_sp",
      headerText: "Tên sản phẩm",
      allowFiltering: true,
      allowSorting: false,
      width: 150,
    },
    {
      fieldName: "donvitinh",
      headerText: "Đơn vị tính",
      allowFiltering: true,
      allowSorting: false,
      width: 120,
    },
    {
      fieldName: "soluong",
      headerText: "Số lượng",
      allowFiltering: true,
      allowSorting: false,
      width: 100,
      type: 'number',
      format: 'N',
    },
    {
      fieldName: "dongia",
      headerText: "Đơn giá",
      allowFiltering: true,
      allowSorting: false,
      width: 100,
      type: 'number',
      format: 'N',
    },
    {
      fieldName: "tyle_vat",
      headerText: "Tỷ lệ VAT (%)",
      allowFiltering: true,
      allowSorting: false,
      width: 130,
    },
    {
      fieldName: "tongtien",
      headerText: "Tổng tiền",
      allowFiltering: true,
      allowSorting: false,
      width: 120,
      type: 'number',
      format: 'N',
    },
    {
      fieldName: "tongtien_vat",
      headerText: "Tổng tiền (VAT)",
      allowFiltering: true,
      allowSorting: false,
      width: 140,
      type: 'number',
      format: 'N',
    },
    {
      fieldName: "phathanh_hd",
      headerText: "Phát hành",
      allowFiltering: true,
      allowSorting: false,
      width: 120
    },
    {
      fieldName: "thanhtoan_hd",
      headerText: "Thanh toán",
      allowFiltering: true,
      allowSorting: false,
      width: 120
    },
  ],

  errorCols: [
    {
      fieldName: "lydo",
      headerText: "Lý do lỗi",
      allowFiltering: false,
      allowSorting: false,
      width: 150
    },
    {
      fieldName: "ma_kh",
      headerText: "Mã KH",
      allowFiltering: false,
      allowSorting: false,
      width: 150,
    },
    {
      fieldName: "stt_hd",
      headerText: "STT HĐ",
      allowFiltering: false,
      allowSorting: false,
      width: 150,
    },
    {
      fieldName: "ten_sp",
      headerText: "Tên SP",
      allowFiltering: false,
      allowSorting: false,
      width: 150,
    },
    {
      fieldName: "donvitinh",
      headerText: "Đơn vị tính",
      allowFiltering: false,
      allowSorting: false,
      width: 150
    },
    {
      fieldName: "soluong",
      headerText: "Số lượng",
      allowFiltering: false,
      allowSorting: false,
      width: 100,
    },
    {
      fieldName: "dongia",
      headerText: "Đơn giá",
      allowFiltering: false,
      allowSorting: false,
      width: 100,
    },
    {
      fieldName: "tyle_vat",
      headerText: "Tỷ lệ VAT",
      allowFiltering: false,
      allowSorting: false,
      width: 150,
    },
  ],

  colsExcel: [
    'ma_kh',
    'stt_hd',
    'ten_sp',
    'donvitinh',
    'soluong',
    'dongia',
    'tyle_vat'
  ]
}
