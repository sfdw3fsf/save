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
  },
  
  // Loại công việc 3: Thu hồi dải IP - mở modal
  3: {
    type: 'modal',
    refName: 'ModalChiTietPhieuTacNghiep',
    methodName: 'show', // Method để mở modal
    methodParams: ['data', 'VIEW'], // Tham số truyền vào method
    modalProps: {
      // datasource: 'data',
      modalID: 'data.PHIEU_YC',
    },
  },

  // Loại công việc 21: Test mở modal
  21: {
    type: 'modal',
    refName: 'CapPhatCongSuatDienPopup',
    methodName: 'openDialog', // Method khác để mở modal
    methodParams: [], // Không có tham số
    modalProps: {
      // datasource: 'data',
      modalID: 'data.PHIEU_YC',
    },
  },

  TENTAT_TT: {
    'THICONG_YCM': 'THICONG_YCM', //Yêu cầu mới
    'THICONG_KT': 'THICONG_KT', //Thi công khởi tạo
  },
};
