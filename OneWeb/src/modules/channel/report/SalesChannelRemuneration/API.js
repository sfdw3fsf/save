export default {
    chitra_thulao_kenhban_chon_nganhang: async (axios) => axios.post('web-khdn/quanlykenh/chitra_thulao_kenhban_chon_nganhang',{}),
    chitra_thulao_kenhban_chon_httt: async (axios) => axios.post('web-khdn/quanlykenh/chitra_thulao_kenhban_chon_httt',{}),
    chitra_thulao_kenhban_chon_ktct: async (axios) => axios.post('web-khdn/quanlykenh/chitra_thulao_kenhban_chon_ktct',{}),
    chitra_thulao_kenhban_chonkenhban: async (axios) => axios.post('web-khdn/quanlykenh/chitra_thulao_kenhban_chonkenhban',{}),
    chitra_thulao_kenhban_chon_loaitien: async (axios) => axios.post('web-khdn/quanlykenh/chitra_thulao_kenhban_chon_loaitien',{}),

    chitra_thulao_kenhban_gachno: async (axios, data) => axios.post('web-khdn/quanlykenh/chitra_thulao_kenhban_gachno',data),

    get_thongtin_diachi_taikhoan_kenhban: async (axios, kenhban_id) => axios.post('web-khdn/quanlykenh/get_thongtin_diachi_taikhoan_kenhban',{
        kenhban_id:kenhban_id
    }),
    
    get_tongno_kenhban_theochuky: async (axios, data) => axios.post('web-khdn/quanlykenh/get_tongno_kenhban_theochuky',data),
    
    
    
}