import axios from "axios";

export default {
    getDSChuongtrinhCSKH: (axios, pageNo, pageSize) => axios.get('/web-cskh/api/gan-nhan-vien-cs-khl/danh-sach-ct?kieu=0&pageNo=' + pageNo + '&pageSize=' + pageSize),
    capNhapChuongTrinhCSKH: (axios, data) => axios.post('/web-cskh/api/chuong-trinh-cskh/cap-nhat', data),
    XoaChuongTrinhCSKH: (axios, data) => axios.get('/web-cskh/api/chuong-trinh-cskh/xoa?ctcskhId=' + data)
}