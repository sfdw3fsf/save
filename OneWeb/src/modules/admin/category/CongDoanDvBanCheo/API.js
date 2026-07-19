import axios from 'axios'

export default {
    getDichVuList: () => axios.get(`/web-quantri/danhmuc-chung/CSS_DICHVU_VT_V3`).then(rs => rs.data.data),
    getLoaiHinhTB: () => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB').then(rs => rs.data.data),
    getDSDoiTuongBan: () => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_doituongban').then(rs => rs.data.data),
    getTinhTrangCddv: () => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_tinhtrang').then(rs => rs.data.data),
    getDsCongDoan: () => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_congdoan').then(rs => rs.data.data),
    capNhapCongDoan: (data) => axios.post('/web-khdn/danhmucbancheo/capnhat_congdoan_bancheo', data).then(rs => rs.data),
    xoaCongDoan: (data) => axios.post('/web-khdn/danhmucbancheo/xoa_congdoan_bancheo', data).then(rs => rs.data)
}
