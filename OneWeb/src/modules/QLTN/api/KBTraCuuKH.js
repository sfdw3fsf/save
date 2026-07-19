export default {
    getDanhSachKhachHang: (axios, data) => axios.post("web-bancheo/tracuu/sp_kb_lay_ds_khachhang",data),
    getVer2DanhSachKhachHang: (axios, data) => axios.get(`web-thuno/api/thu-no/css/lay-danhsach-thanhtoan-khobac/${data.ma_kh}`),
    getDanhSachThanhToan: (axios, data) => axios.post("web-bancheo/tracuu/sp_kb_lay_ds_thanhtoan",data),
    getDanhSachExcelKH: (axios, data) => axios.post("web-bancheo/tracuu/sp_kb_lay_ds_excel_khachhang",data),
  };
