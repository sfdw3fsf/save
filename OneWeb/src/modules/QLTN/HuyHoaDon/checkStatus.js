export const checkStatus = status => {
  let string = 'Hóa đơn đã phát hành';
  switch (status) {
    case '0':
      string = 'Hóa đơn vừa khởi tạo';
      break;
    case '1':
      string = 'Hóa đơn đã phát hành';
      break;
    case '2':
      string = 'Hóa đơn đã khai báo thuế';
      break;
    case '3':
      string = 'Hóa đơn đã thay thế';
      break;
    case '4':
      string = 'Hóa đơn đã sửa đổi';
      break;
    case '5':
      string = 'Hóa đơn đã hủy';
      break;
    default:
      break;
  }
  return string;
};

