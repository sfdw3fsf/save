export default{
    lay_loai_hd:async (axios) => axios.get('/web-tracuu/danhmuc/loai_hd'),
    lay_tieuchi_tracuu:async (axios) => axios.get('/web-tracuu/danhmuc/tieuchi_tracuu'),
    lay_trangthai_hd:async (axios) => axios.get('/web-tracuu/danhmuc/trangthai_hd'),
    lay_dichvu:async (axios) => axios.get('/web-tracuu/danhmuc/dichvu'),

    tracuu_thongtin: async (axios, data) => axios.post('/web-tracuu/hopdong/tracuu', data),

    // {
    //     "dichVuVTId": 0,
    //     "inHD": 0,
    //     "loaiHDId": 0,
    //     "maLt": "TSL004487",
    //     "tthdId": 0,
    //     "tuNgay": "28062000",
    //     "denNgay": "05082025"
    // }
    // Số ảo
    // hopdong_theo_ma_lt: async (axios, data) => axios.post('/web-tracuu/hopdong/hopdong_theo_ma_lt', data),

    hopdong_theo_site_id: async (axios, siteId) => axios.get('/web-tracuu/hopdong/hopdong_theo_site_id?siteId='+siteId),

    //catbagreen.com.vn
    hopdong_theo_tenmien: async (axios, tenMien) => axios.get('/web-tracuu/hopdong/hopdong_theo_tenmien?tenMien='+tenMien),

    //HPG20210121-8300
    //Số hợp đồng KHDN
    hopdong_theo_so_hd: async (axios, soHD) => axios.get('/web-tracuu/hopdong/hopdong_theo_so_hd?soHD='+soHD),

    //Số phụ lục KHDN
    hopdong_theo_so_pl: async (axios, soPL) => axios.get('/web-tracuu/hopdong/hopdong_theo_so_pl?soPL='+soPL),


    // {
    //     "dichVuVTId": 0,
    //     "inHD": 0,
    //     "loaiHDId": 0,
    //     "dieuKien": "kh.ma_gd = 'HPG-LD/00734355'",
    //     "tthdId": 0,
    //     "tuNgay": "28062000",
    //     "denNgay": "05082025"
    // }
    //Khác
    hopdong_khac: async (axios, data) => axios.post('/web-tracuu/hopdong/hopdong_khac', data),

    ds_hopdong_tt_theo_hdkh_id: async (axios, hdkhId) => axios.get('/web-tracuu/hopdong/ds_hopdong_tt_theo_hdkh_id?hdkhId='+hdkhId),
    
    ds_hopdong_tb_theo_hdkh_id: async (axios, hdkhId) => axios.get('/web-tracuu/hopdong/ds_hopdong_tb_theo_hdkh_id?hdkhId='+hdkhId),

}