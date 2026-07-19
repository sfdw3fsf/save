export default {
  actions: [
    {
      id: 'laytt',
      name: 'Lấy TT',
      active: true,
      icon_class: 'one-reload1',
      visible: true
    },
    {
      id: 'nhapmoi',
      name: 'Nhập mới',
      active: true,
      icon_class: 'one-file-attach ',
      visible: true
    },
    {
      id: 'ghilai',
      name: 'Ghi lại',
      active: true,
      icon_class: 'one-file-edit2',
      visible: true
    },
    {
      id: 'xoa',
      name: 'Xóa',
      active: true,
      icon_class: 'one-file-arrow-up',
      visible: true
    },
    {
      id: 'ghilai-all',
      name: 'Ghi lại all',
      active: true,
      icon_class: 'one-sync',
      visible: true
    },

  ],
  options: {
    ds_dichvu_gt: [],
    ds_nhom_dt: [],
    ds_tyle_giam: [],
  },
  may_cn: '',
  ip_cn: '',
  formTT: {
    dichvugt_id: 0,
    nhomdt_id: 0,
    tien_giam: 0,
    phut_giam: 0,
    phut_dau: 0,
    tyle_giam: 0
  },

  gvDanhSach: {
    tableCollumns: [
      {
        fieldName: 'ten_dvgt',
        headerText: 'Dịch vụ gia tăng',
        allowFiltering: true,
        allowSorting: false,
        width: 200
      },
      {
        fieldName: 'nhom_dt',
        headerText: 'Nhóm đối tượng',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
      },
      {
        fieldName: 'tien_giam',
        headerText: 'Tiền giảm',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
      },
      {
        fieldName: 'phut_giam',
        headerText: 'Phút giảm',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
      },
      {
        fieldName: 'phut_dau',
        headerText: 'Phút đầu',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
      },
      {
        fieldName: 'ten_tyle_giam',
        headerText: 'Tỷ lệ giảm',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
        textAlign: 'Right'
      },
      {
        fieldName: 'ngay_cn',
        headerText: 'Ngày cập nhật',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
        textAlign: 'Right'
      },
      {
        fieldName: 'nguoi_cn',
        headerText: 'Người cập nhật',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
        textAlign: 'Right'
      },
      {
        fieldName: 'may_cn',
        headerText: 'Máy cập nhật',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
        textAlign: 'Right'
      },
      {
        fieldName: 'ip_cn',
        headerText: 'IP cập nhật',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
      }
    ],
    tableData: []
  }
}
