import axios from "axios";

export default {
    getDanhSachDonVi: (axios, data) => axios.get('/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=598', data),
    getDanhSachLoaiKhachHang: (axios) => axios.get('/web-thuno/api/thu-no/common/danh-muc/loai-khach-hang'),
    getDanhSachNhanVienQL: (axios, data) => axios.get('/web-thuno/api/thu-no/common/nhan-vien-quan-ly/don-vi/' + data),
    getDanhSachNhanVienThuCuoc: (axios, data) => axios.get('/web-thuno/api/thu-no/common/nhan-vien-thu-cuoc/quan-ly/' + data),
    getDanhSachPhuongAnXL: (axios) => axios.get('/web-thuno/api/thu-no/common/phuong-an-xu-ly'),
    getDanhSachKyHD: (axios) => axios.get('/web-thuno/api/thu-no/cong-no/ky-hoa-don-cong-no'),
    getDanhMucHoSo: (axios) => axios.get('/web-thuno/api/thu-no/common/danh-muc-ho-so'),
    getDanhSachPhanLoaiKhachHang: (axios, data) => axios.get('/web-thuno/api/thu-no/common/phan-loai-khach-hang/nhom-kh/' + data),
    getDanhSachCongNo: (axios, data) => axios.get('/web-thuno/api/thu-no/cong-no/ds-cap-nhat-ho-so', {params: data}),
    updateHoSoCongNo: (axios, data) => axios.put('/web-thuno/api/thu-no/cong-no/cap-nhat-ho-so-cong-no', data),
    getDanhSachPhuongAnXLTheoCongNoId: (axios, data) => axios.get('/web-thuno/api/thu-no/cong-no/phuong-an-xu-ly/cong-no/' + data),
    getDanhSachHoSoCongNoTheoCongNoId: (axios, data) => axios.get('/web-thuno/api/thu-no/cong-no/ds-ho-so-cong-no/' + data),
}