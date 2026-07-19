export const CONFIG = {
  // Loại công việc 2: Cấp phát dải IP
  // chuyển đến màn hình chi tiết để thực hiện việc cấp phát
  2: {
    type: 'navigate',
    route: '/tainguyen-idc/capphat-daiip',
    params: {
      // Sử dụng ID công việc làm tham số
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  28: {
    type: 'navigate',
    route: '/tainguyen-idc/idc-bangthong-mang',
    params: {
      // Sử dụng ID công việc làm tham số
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  44: {
    type: 'navigate',
    route: '/tainguyen-idc/idc-bangthong-mang',
    params: {
      // Sử dụng ID công việc làm tham số
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  // Loại công việc 3: Thu hồi dải IP - mở modal
  3: {
    type: 'modal',
    refName: 'ModalChiTietPhieuTacNghiep',
    methodName: 'show', // Method để mở modal
    methodParams: ['data', 'VIEW'], // Tham số truyền vào method
    modalProps: {
      // datasource: 'data',
      modalID: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true
  },

  30: {
    type: 'navigate',
    route: '/tainguyen-idc/ketnoi',
    params: {
      // Sử dụng ID công việc làm tham số
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },

  // Loại công việc 29: Cấp phát công suất điện
  29: {
    type: 'navigate',
    route: '/tainguyen-idc/capphat-congsuatdien',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },

  // Loại công việc 36: Cấp phát U
  36: {
    type: 'navigate',
    route: '/tainguyen-idc/capphat-u-form',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },

  // Loại công việc 57: Cấp phát Rack
  57: {
    type: 'navigate',
    route: '/tainguyen-idc/capphat-rack-form',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  // Loại công việc 62: Thu hồi Rack
  62: {
    type: 'navigate',
    route: '/tainguyen-idc/thuhoi-rack-form',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  // Loại công việc 31: Lắp đặt Rack
  31: {
    type: 'navigate',
    route: '/tainguyen-idc/lapdat-rack-form',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  // Loại công việc 32: Tháo dỡ Rack
  32: {
      type: 'navigate',
      route: '/tainguyen-idc/thaodo-rack-form',
      params: {
        phieutc: 'data.PHIEU_YC'
      },
      autoRedirectAfterCreate: true,
      paramEdit: { name: 'isEditMode', value: 'true' }
  },
  // Loại công việc 63: Thay đổi Rack
  63: {
    type: 'navigate',
    route: '/tainguyen-idc/thaydoi-rack-form',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },

  // Loại công việc 42: Thu hồi U
  42: {
    type: 'navigate',
    route: '/tainguyen-idc/thuhoi-u-form',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },

  // Loại công việc 50, 52, 23: Thi công hệ thống thiết bị IDC & khách hàng
  50: {
    type: 'navigate',
    route: '/tainguyen-idc/tacnghiep-hethong-thietbi-idc-kh',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  52: {
    type: 'navigate',
    route: '/tainguyen-idc/tacnghiep-hethong-thietbi-idc-kh',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  23: {
    type: 'navigate',
    route: '/tainguyen-idc/tacnghiep-hethong-thietbi-idc-kh',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  // Cấp phát dải IP
  39: {
    type: 'navigate',
    route: '/tainguyen-idc/tc-capphat-ip',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  // Cấp phát IP
  40: {
    type: 'navigate',
    route: '/tainguyen-idc/tc-capphat-ip',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  // tháo gỡ kết nối điện IDC
  65: {
    type: 'navigate',
    route: '/tainguyen-idc/ketnoi',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  // Thay đổi kết nối điện IDC
  66: {
    type: 'navigate',
    route: '/tainguyen-idc/ketnoi',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  56: {
    type: 'navigate',
    route: '/tainguyen-idc/ketnoi',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  67: {
    type: 'navigate',
    route: '/tainguyen-idc/ketnoi',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  68: {
    type: 'navigate',
    route: '/tainguyen-idc/ketnoi',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  // Thu hồi dải IP
  45: {
    type: 'navigate',
    route: '/tainguyen-idc/tc-thuhoi-ip',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  // Thu hồi IP
  46: {
    type: 'navigate',
    route: '/tainguyen-idc/tc-thuhoi-ip',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  //loại công việc 43: Thu hồi công suất điện
  43: {
    type: 'navigate',
    route: '/tainguyen-idc/thuhoi-congsuatdien',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  },
  //loại cônng việc 81: Chuyển đổi công suất điện
  81: {
    type: 'navigate',
    route: '/tainguyen-idc/chuyendoi-congsuatdien',
    params: {
      phieutc: 'data.PHIEU_YC'
    },
    autoRedirectAfterCreate: true,
    paramEdit: { name: 'isEditMode', value: 'true' }
  }
}
