export default {
  header: {
    title: "Thù lao thu nợ quá hạn",
    list: [{
      name: "Lập hợp đồng",
      link: {
        name: "Ui.cards"
      }
    },
    {
      name: "Lắp đặt mới",
      link: {
        name: "Ui.buttons"
      },
    },
    ],
  },
  txtNoiDung: null,
  txtTu: null,
  txtDen: null,
  txtThuLao: null,
  selectKieuNo: null,
  current: 0,
  DONGIA_TN_ID: null,
  THULAO_NQH_ID: "",
  options: {
    danh_sach_thu_lao: []
  },
  button: {
    nhapmoi: false,
    ghilai: false,
    xoa: false,
    huybo: false
  },
  selectionOptions: { type: 'Multiple' },
  left: {
    kyhoadon: [],
    loainhanvien: [],
    khuvuc: []
  },
  right: {
    kyhoadon: [],
    loainhanvien: [],
    khuvuc: []
  },

  model: {
    thulao: {
    }
  },
  loadingtable: {
    thulao: false,
  },
  isLoad: true,
  options: {
    thulao: [
    ],
    lsKieuNo: [
      {
        kieu_nqh: "Thù lao trên tổng số tiền thu được",
        id: 1
      },
      {
        kieu_nqh: "Có phát thông báo nhưng không thu được nợ",
        id: 2
      },
      {
        kieu_nqh: "Khách hàng thanh toán tại bưu cục, ngân hàng",
        id: 3
      }
    ]
  },
  config: {
    thulao: [
      {
        headerText: 'STT',
        allowFiltering: true,
        fieldName: 'stt'
      },
      {
        headerText: 'Nội dung',
        allowFiltering: true,
        fieldName: 'noidung_nqh'
      },
      {
        headerText: 'Tháng nợ từ',
        allowFiltering: true,
        fieldName: 'thangno_tu'
      },
      {
        headerText: 'Tháng nợ đến',
        allowFiltering: true,
        fieldName: 'thangno_den'
      },
      {
        headerText: 'Thù lao',
        allowFiltering: true,
        fieldName: 'thulao_nqh'
      },
      {
        headerText: 'Kiểu nợ QH',
        allowFiltering: true,
        fieldName: 'kieu_nqh'
      }
    ],
  },
  dsKieuNoQH: [
  ],
}
