export default{
    loai_hd:(axios) =>  axios.get('/web-tracuu/danhmuc/loai_hd'),
    trangthai_hd:(axios) =>  axios.get('/web-tracuu/danhmuc/trangthai_hd'),
    tieuchi_tracuu:(axios) =>  axios.get('/web-tracuu/danhmuc/tieuchi_tracuu'),
    dichvu:(axios) =>  axios.get('/web-tracuu/danhmuc/dichvu'),

    hopdong_theo_site_id: async (axios, siteId) => axios.get('/web-tracuu/hopdong/hopdong_theo_site_id?siteId='+siteId),

    hopdong_theo_tenmien: async (axios, tenMien) => axios.get('/web-tracuu/hopdong/hopdong_theo_tenmien?tenMien='+tenMien),
    fn_map_id:  (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),
    fn_tt_hdtb_mgwan:(axios,data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_mgwan', data),
    fn_tt_hdtb_tsl:(axios,data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_tsl', data),
    //HPG20210121-8300
    //Số hợp đồng KHDN
    hopdong_theo_so_hd: async (axios, soHD) => axios.get('/web-tracuu/hopdong/hopdong_theo_so_hd?soHD='+soHD),

    //Số phụ lục KHDN
    hopdong_theo_so_pl: async (axios, soPL) => axios.get('/web-tracuu/hopdong/hopdong_theo_so_pl?soPL='+soPL),
    //Khác
    hopdong_khac: async (axios, data) => axios.post('/web-tracuu/hopdong/hopdong_khac', data),

    ds_hopdong_tt_theo_hdkh_id: async (axios, hdkhId) => axios.get('/web-tracuu/hopdong/ds_hopdong_tt_theo_hdkh_id?hdkhId='+hdkhId),

    ds_hopdong_tb_theo_hdkh_id: async (axios, hdkhId) => axios.get('/web-tracuu/hopdong/ds_hopdong_tb_theo_hdkh_id?hdkhId='+hdkhId),
    lay_ds_chitiet_km_theo_tbid: async (axios, data) => axios.post('web-hopdong/thaydoiloaihinhtb/lay_ds_chitiet_km_theo_tbid', data),

    tra_cuu_hop_dong_dieukien: async (axios, data) => axios.post('/web-tracuu/khdn/tra_cuu_hop_dong_dieukien', data),
    tieuchi_tracuu_v2:(axios) =>  axios.get('/web-tracuu/danhmuc/tieuchi_tracuu_v2'),


}
