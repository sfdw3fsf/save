export default{
    upload_file: async (axios, data,header) => axios.post('/web-quantri/upload', data,header),
    upload_ngay_sinh: async (axios, data,header) => axios.post('/ccbs/upload_data/upload_ngaysinh_dd', data,header),
    get_success: async (axios, data,header) => axios.post('/ccbs/executor/ds_upload_ngaysinh_thanhcong', data,header),
    get_error: async (axios, data,header) => axios.post('/ccbs/executor/ds_upload_ngaysinh_loi', data,header),
}
    