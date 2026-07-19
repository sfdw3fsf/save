export default{
    //Thông tin chung kênh luồng
    layThongTinChung: (axios, data) => axios.post('/web-net/thongtinchung/layThongTinChung', data),
    laydsDichVu: (axios, data) => axios.post('/web-net/thongtinchung/dsDichVu', data),
    laydsLoaiHinh: (axios, data) => axios.post('/web-net/thongtinchung/dsLoaiHinh', data),
    laydsTocDo: (axios, data) => axios.post('/web-net/thongtinchung/dsTocDo', data),
    laydsDanhMucChung: (axios, data) => axios.post('/web-net/thongtinchung/dsDanhMucChung', data),
    capNhatThongTinChung: (axios, data) => axios.post('/web-net/thongtinchung/capNhatThongTinChung', data),
    layThongTinChung: (axios, data) => axios.post('/web-net/thongtinchung/layThongTinChung', data),
    lay_ds_hdbancheo_theo_hdtb_id: async (axios, data) => axios.get(`/web-bancheo/hoan_cong_megawan_net/lay_ds_hdbancheo_theo_hdtb_id?vphanvung_id=${data.vphanvung_id}&vhdtb_id=${data.vhdtb_id}`),
    tsbtnDownloadFileNT_Click: async (axios, data) => axios.post('/web-bancheo/hoan_cong_megawan_net/tsbtnDownloadFileNT_Click', data),
    fn_kiemtra_hoanthienphieu_bancheo_vnpti: async (axios, data) => axios.get(`/web-thicong/khaibao_dichvu_net_mgw/fn_kiemtra_hoanthienphieu_bancheo_vnpti?vloaitb_id=${data.vloaitb_id}`),
    laydsPhanDoan: (axios, data) => axios.post('/web-net/thongtinchung/dsPhanDoan', data),
    xoaThongTinChung: (axios, data) => axios.post('/web-net/thongtinchung/xoaThongTinChung', data),
    xoaPhanDoan: (axios, data) => axios.post('/web-net/thongtinchung/xoaPhanDoan', data),
    get_keys: (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
    sapXepPhanDoan: (axios, data) => axios.post('/web-net/thongtinchung/sapXepPhanDoan', data),
    upload_file: async (axios, data, header) => axios.post('/web-quantri/upload', data, header),
    anHienPhanDoan: (axios, data) => axios.post('/web-net/thongtinchung/anHienPhanDoan', data),

}
