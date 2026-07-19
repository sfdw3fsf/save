export default {
    getTinh: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TINH'),
    getLoaiThueBao: (axios) => axios.post('/ccbs/gphone/reset_cell_agent_type')
}