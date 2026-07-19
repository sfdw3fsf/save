export default {
  cols: [
    {
      fieldName: "ma_gd",
      headerText: "Mã GD",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "ngaygiao",
      headerText: "Ngày giao",
      allowFiltering: true,
      allowSorting: false,
      format: 'dd/MM/yyyy HH:mm:ss',
      type: 'date',
      textAlign: 'right'
    },
    {
      fieldName: "nd_giao",
      headerText: "Nội dung giao",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "ngayhen_tu",
      headerText: "Ngày hẹn từ",
      allowFiltering: true,
      allowSorting: false,
      type: 'date',
      format: 'dd/MM/yyyy HH:mm:ss',
      textAlign: 'right'
    },
    {
      fieldName: "ngayhen_den",
      headerText: "Ngày hẹn đến",
      allowFiltering: true,
      allowSorting: false,
      type: 'date',
      format: 'dd/MM/yyyy HH:mm:ss',
      textAlign: 'right'
    },
    {
      fieldName: "ma_tb",
      headerText: "Số máy/Acc",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "ten_tb",
      headerText: "Tên TB",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "diachi_ld",
      headerText: "Địa chỉ LĐ",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: 'loaihinh_tb',
      headerText: "Loại hình",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "hostld_cu",
      headerText: "Host LĐ cũ",
      allowFiltering: true,
      allowSorting: false,
    },
    {
      fieldName: "ghichu",
      headerText: "Ghi chú",
      allowFiltering: true,
      allowSorting: false,
    },
  ],
  ChuanHoaTen(ten) {
    if (!ten) return '';
    var ten_new = '';
    var str_con = ten.trim();
    var str_xl = "";
    var k = 0;
    var found = true;
    if (str_con.length < 1) return;
    str_con = str_con.replaceAll(/\s+/g, ' ');
    try {
      while (found) {
        k = str_con.indexOf(' ');
        if (k > 0) {
          str_xl = str_con.substring(0, k);
          str_con = str_con.substring(k + 1);
          str_xl = this.ChuyenChuHoa(str_xl);
          if (ten_new != "")
            ten_new += " " + str_xl;
          else ten_new = str_xl;
          found = true;
        } else found = false;
      }
      if (str_con != "") {
        str_xl = str_con;
        str_xl = this.ChuyenChuHoa(str_xl);
        if (ten_new != "")
          ten_new = ten_new + " " + str_xl;
        else
          ten_new = str_xl;
      }
      return ten_new;
    } catch (e) {
      return ten;
    }
  },
  ChuyenChuHoa(str) {
    var kq = "";
    if (str.ismatch(/[A-Z][A-Z]/g)) kq = str.toUpperCase();
    else {
      if (str[0] == '(')
        kq = str[0] + str[1].toUpperCase() + str.substring(2).toLowerCase();
      else
        kq = str[0].toUpperCase() + str.substring(1).toLowerCase();
    }
    return kq;
  },
}
