export default {
  cols: [
    {      
      fieldName: "ma_ungdung",
      headerText: "Mã điểm chạm",
      allowFiltering: true,
      allowSorting: false,
      freeze: 'left',
      width: 120
    },
    {
      fieldName: "ten_ungdung",
      headerText: "Tên điểm chạm",
      allowFiltering: true,
      allowSorting: false,      
      width: 120,
      freeze: 'left',
    },
    {
      fieldName: "ten_loai",
      headerText: "Loại điểm chạm",
      allowFiltering: true,
      allowSorting: false,
      width: 120,
    },
    {
      fieldName: "ten_ungdung_cha",
      headerText: "Tên điểm chạm cha",
      allowFiltering: true,
      allowSorting: false,      
      width: 120,
      freeze: 'left',
    },
    {
      fieldName: "ngay_cn",
      headerText: "Ngày tạo",
      allowFiltering: true,
      allowSorting: false,
      width: 120,
    },
    {
      fieldName: "nguoi_cn",
      headerText: "Người tạo",
      allowFiltering: true,
      allowSorting: false,
      width: 100,
    }
  ],
}
