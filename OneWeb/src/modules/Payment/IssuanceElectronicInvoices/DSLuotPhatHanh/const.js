export default {
  cols: [
    {
      fieldName: "NAM",
      headerText: "Năm",
      allowFiltering: false,
      allowSorting: true,
      width: 'auto',
      isGroupedColumn: true,
      captionTemplate: '${key}',
    },
    {
      fieldName: "THANG",
      headerText: "Tháng",
      allowFiltering: false,
      allowSorting: true,
      width: 'auto',
      isGroupedColumn: true,
      captionTemplate: '${key}'
    },
    {
      fieldName: "NGAY",
      headerText: "Ngày",
      allowFiltering: false,
      allowSorting: true,
      width: 'auto',
      isGroupedColumn: true,
      captionTemplate: '${key}'
    },
    {
      fieldName: "COUNT_ID",
      headerText: "Lượt phát hành",
      allowFiltering: false,
      allowSorting: true,
      width: 'auto',
      textAlign: 'right'
    },
  ]
}
