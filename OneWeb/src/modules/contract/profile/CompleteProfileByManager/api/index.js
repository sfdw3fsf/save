export default {
    getTinh: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TINH'),
    kiemtra_dulieu: (axios, data) => axios.post('/web-thicong/thuebao/kiemtra_dulieu', data),
    upload_file: async (axios, data, header) => axios.post('/web-quantri/upload', data, header),

}