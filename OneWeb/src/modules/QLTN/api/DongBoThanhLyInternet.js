import axios from "axios";

export default {
    getDanhSachDichVu: (axios) => axios.get('/web-cskh/api/danh-muc/dich-vu-vt'),
    getDanhSachQuyTrinh: (axios) => axios.get(''),
    getDanhSachLoaiHD: (axios) => axios.get('/web-thuno/api/thu-no/css/ds-loai-hop-dong'),
    getDanhSachThueBao: (axios) => axios.get(''),
}