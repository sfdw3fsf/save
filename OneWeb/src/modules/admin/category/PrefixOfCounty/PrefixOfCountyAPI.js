export default{
    get_tieuchi_tracuu_taikhoan_dichvu: async (axios) => axios.get('/web-tracuu/danhmuc/tieuchi_tracuu_taikhoan_dichvu'),
    get_tieuchi_tracuu_taikhoan_dichvu_op: async (axios) => axios.get('/web-tracuu/danhmuc/tieuchi_tracuu_taikhoan_dichvu_op'), 
    tracuu_visa_thongtin_khachhang: async (axios, data) => axios.post('/web-tracuu/visa/thongtin_khachhang', data)

}