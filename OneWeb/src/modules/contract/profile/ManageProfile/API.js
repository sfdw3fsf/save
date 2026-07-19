export default {
    sp_gettreehoso: async (axios, data) => axios.post('/web-quantri/quanly_hoso_dientu/sp_gettreehoso',data),
    sp_getloaihoso: async (axios, data) => axios.post('/web-quantri/quanly_hoso_dientu/sp_getloaihoso',data),
    themmoi_hoso: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/themmoi_hoso',data),
    fn_tt_file_hs: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_file_hs',data),
    capnhat_hoso: async (axios, data) => axios.post('web-ccdv/tien_trinh_bao_hong/capnhat_hoso',data),
    thuhoi_hoso: async (axios, data) => axios.post('web-ccdv/tien_trinh_bao_hong/thuhoi_hoso',data),
    sp_getlydohoso: async (axios, data) => axios.get('/web-quantri/tylethulaoquahan/sp_getlydohoso',data),
    dichuyen_hoso: async (axios, data) => axios.post('/web-danhba/hopdong/dichuyen_hoso',data),
    themmoi_icon: async (axios, data) => axios.post('web-danhba/hopdong/themmoi_icon',data),
    upload_file_hoso: async (axios, data,header) => axios.post('/web-quantri/upload', data,header),

    sp_lay_data_icon_theo_loaihs: async(axios, data) => axios.post('/web-quantri/quanly_hoso_dientu/sp_lay_data_icon_theo_loaihs', data)
}