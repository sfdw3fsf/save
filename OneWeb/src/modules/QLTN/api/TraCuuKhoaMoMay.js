export default{    
    getDanhBaTheoMaThanhToan: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu-danh-ba/ma-thanh-toan', { params: data}),
    getDanhBaTheoMaThueBao: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu-danh-ba/ma-thue-bao', { params: data}),
    getLichSuKhoaMoMay: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu/lich-su-dong-mo-may', { params: data})    
}