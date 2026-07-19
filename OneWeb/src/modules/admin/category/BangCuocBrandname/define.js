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
    }
  ],
  ds_label_type : [],
  options: {
    ds_phanloai: [],
    ds_telco: [],
    ds_loai_daily: [],
    ds_label_type: [],
    ds_ht_thanhtoan: []
  },
  may_cn: '',
  ip_cn: '',
  formTT: {
    bangcuoc_id: 0,
    phanloai_id: 0,
    nhamang_id: 0,
    loai_daily: 0,
    loainhan_id: 0,
    ht_thanhtoan: 0,
    sl_tu: 0,
    sl_den: 0,
    dongia: 0,
    thoihan: 0,
    ghichu: '',
    ngay_bd: '',
    ngay_kt: '',
    linhvuc_id: 0
  },

  gvDanhSach: {
    tableCollumns: [
      {
        fieldName: 'bangcuoc_id',
        headerText: 'ID',
        allowFiltering: true,
        allowSorting: false,
        width: 70,
        textAlign: 'Center'
      },
      {
        fieldName: 'phanloai',
        headerText: 'Phân loại',
        allowFiltering: true,
        allowSorting: false,
        width: 100
      },
      {
        fieldName: 'nhamang',
        headerText: 'Nhà mạng',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
      },
      {
        fieldName: 'ten_loai_daily',
        headerText: 'Loại đại lý',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
      },
      {
        fieldName: 'loainhan',
        headerText: 'Loại nhãn',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
      },
      {
        fieldName: 'ten_ht_thanhtoan',
        headerText: 'Hình thức thanh toán',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
      },
      {
        fieldName: 'sl_tu',
        headerText: 'Số lượng từ',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
        textAlign: 'Right'
      },
      {
        fieldName: 'sl_den',
        headerText: 'Số lượng đến',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
        textAlign: 'Right'
      },
      {
        fieldName: 'dongia',
        headerText: 'Đơn giá',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
        textAlign: 'Right'
      },
      {
        fieldName: 'thoihan',
        headerText: 'Thời hạn',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
        textAlign: 'Right'
      },
      {
        fieldName: 'ghichu',
        headerText: 'Ghi chú',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
      },
      {
        fieldName: 'ngay_bd',
        headerText: 'Ngày bắt đầu',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
        textAlign: 'Right'
      },
      {
        fieldName: 'ngay_kt',
        headerText: 'Ngày kết thúc',
        allowFiltering: true,
        allowSorting: false,
        width: 100,
        textAlign: 'Right'
      }
    ],
    tableData: []
  }
}
