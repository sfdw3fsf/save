export default {
  cols: [
    {
      fieldName: "ma_tb",
      headerText: "Số máy/Acc",
      allowFiltering: true,
      allowSorting: false,
      freeze: 'left',
      width: 120
    },
    {
      fieldName: "ma_lt",
      headerText: "Số ảo",
      allowFiltering: true,
      allowSorting: false,
      width: 100
    },
    {
      fieldName: "ngay_bh",
      headerText: "Ngày báo hỏng",
      allowFiltering: true,
      allowSorting: false,
      textAlign: 'right',
      width: 150
    },
    {
      fieldName: "nguoi_bh",
      headerText: "Người báo hỏng",
      allowFiltering: true,
      allowSorting: false,
      width: 150
    },
    {
      fieldName: "dienthoai_lh",
      headerText: "Điện thoại",
      allowFiltering: true,
      allowSorting: false,
      textAlign: 'right',
      width: 150
    },
    {
      fieldName: "nguoi_cn",
      headerText: "Người CN",
      allowFiltering: true,
      allowSorting: false,
      width: 150
    },
    {
      fieldName: "ngay_cn",
      headerText: "Ngày CN",
      allowFiltering: true,
      allowSorting: false,
      textAlign: 'right',
      width: 150
    },
    {
      fieldName: "hensuatu",
      headerText: "Hẹn từ",
      allowFiltering: true,
      allowSorting: false,
      textAlign: 'right',
      width: 150
    },
    {
      fieldName: "hensuaden",
      headerText: "Hẹn đến",
      allowFiltering: true,
      allowSorting: false,
      textAlign: 'right',
      width: 150
    },
    {
      fieldName: "ten_tb",
      headerText: "Tên thuê bao",
      allowFiltering: true,
      allowSorting: false,
      textAlign: 'right',
      width: 150
    },
  ],

  contextMenuItems: [
    {text: 'Show port', id: 'dstn_showport', target: '.e-content', iconCss: 'icon one-clipart-arrow-right text-primary',},
    {text: 'Xóa tiếp nhận', id: 'dstn_xoa', target: '.e-content', iconCss: 'icon one-trash text-danger'}
  ]

}
