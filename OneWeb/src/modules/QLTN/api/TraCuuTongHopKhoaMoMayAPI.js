export default {
    getLichSuKhoaMoMay: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/lich-su-khoa-mo-may-theo-ngay', { params: data })    
}
