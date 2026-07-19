
export default {
     
    //fn_ghilai_noidung_tientrinh_thicong: (axios, data) => axios.post(`web-thicong/hoinghi_truyenhinh/fn_ghilai_noidung_tientrinh_thicong`, data),
    ghilai_noidung_tientrinh_thicong: async (axios, data,header) => axios.post('/web-bancheo/tracuu/ghilai_noidung_tientrinh_thicong', data, header),  
    
}