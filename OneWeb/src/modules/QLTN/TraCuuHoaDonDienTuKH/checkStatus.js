export const checkStatus = status => {
  let string = 'Đã phát hành';
  switch (status) {
    case '0':
      string = 'Vừa khởi tạo';
      break;
    case '1':
      string = 'Đã phát hành';
      break;
    case '2':
      string = 'Đã khai báo thuế';
      break;
    case '3':
      string = 'Bị thay thế';
      break;
    case '4':
      string = 'Bị điều chỉnh';
      break;
    case '5':
      string = 'Bị xóa bỏ';
      break;
    default:
      break;
  }
  return string;
};

