const collumns = {
  collumnsVT: [
    {
      fieldName: 'VITRI',
      headerText: 'Vị trí',
      allowSorting: false,
      textAlign: 'left',
      autoFit: 'true'
    },
    {
      fieldName: 'CHUCDANH',
      headerText: 'Chức danh',
      allowSorting: false,
      textAlign: 'left',
      autoFit: 'true'
    },
    {
      fieldName: 'VTCD_ID',
      visible: false
    }
  ],
  collumnsLCT: [
    {
      fieldName: 'MA_DV',
      headerText: 'Mã đơn vị',
      allowSorting: false,
      textAlign: 'center',
      autoFit: 'true'
    },
    {
      fieldName: 'TENLOAI_CT',
      headerText: 'Loại chương trình',
      allowSorting: false,
      textAlign: 'left',
      autoFit: 'true'
    },
    {
      fieldName: 'GHICHU',
      headerText: 'Ghi chú',
      allowSorting: false,
      textAlign: 'left',
      autoFit: 'true'
    },
    {
      fieldName: 'LOAI_CT_ID',
      visible: false
    }
  ]
}

export default collumns
