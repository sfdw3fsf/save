import moment from 'moment'
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
      id: 'tao_giai_so',
      name: 'Tạo giải số',
      active: true,
      icon_class: 'one-file-arrow-up',
      visible: true
    },
    {
      id: 'import-file',
      name: 'Import file',
      active: true,
      icon_class: 'one-file-arrow-up',
      visible: true
    },
  ],
  options: {
    ds_tinh: [],
    ds_trangthaiso: [],
    ds_kieuso: [],
    ds_nhomso: [],
    ds_loaihinh_tb: []
  },
  formTT: {
    phanvung_id: 0,
    somay: '',
    trangthaiso_id: 0,
    kieuso_id: 0,
    loaitb_id: 0,
    nhomso_id: 0,
    ghichu: '',
    tiento: '',
    so_bd: 0,
    so_kt: 0
  },
  tagForm: '',
  gvDanhSach: {
    tableCollumns: [
      {
        fieldName: 'ten_ttso',
        headerText: 'Trạng thái số',
        allowFiltering: true,
        allowSorting: false,
        width: 100
      },
      {
        fieldName: 'kieu_so',
        headerText: 'Kiểu số',
        allowFiltering: true,
        allowSorting: false,
        width: 100
      },
      {
        fieldName: 'nhom_so',
        headerText: 'Nhóm số',
        allowFiltering: true,
        allowSorting: false,
        width: 100
      },
      {
        fieldName: 'loaihinh_tb',
        headerText: 'Loại hình TB',
        allowFiltering: true,
        allowSorting: false,
        width: 100
      },
      {
        fieldName: 'somay',
        headerText: 'Số máy',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
        },
      {
        fieldName: 'ghichu',
        headerText: 'Ghi chú',
        allowFiltering: true,
        allowSorting: false,
        width: 100
      }
    ],
    tableData: []
  }
}
