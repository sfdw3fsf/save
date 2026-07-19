export default{
    lay_ds_dv: async (axios, data) => axios.get('/web-tracuu/khoso/ds_dichvu?dichVuVTId=1,10,11', data),
    lay_ds_tt_so: async (axios, data) => axios.get('/web-tracuu/khoso/ds_tt_so', data),
    lay_ds_kieu_so: async (axios, data) => axios.get('/web-tracuu/khoso/ds_kieu_so?kieu=0', data),
    lay_ds_host: async (axios, data) => axios.get('/web-tracuu/khoso/ds_host', data),
    lay_ds_dia_danh: async (axios, data) => axios.get('/web-tracuu/khoso/ds_quan?prefix=3864', data),
    tracuu: async (axios, data) => axios.post('/web-tracuu/khoso/tracuu', data),

    lay_tt_hop_dong: async (axios, data) => axios.post('/web-hopdong/tracuu_chitiet_thicong/lay_ds_hd_tb_theo_hdkh_id_v3', data),
    lay_tt_hop_dong_form: async (axios, data) => axios.post('/web-quantri/uploadfile-hoso/sp_ds_tb_upload_file_hs', data),
    lay_tt_kh_form: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_hd_theo_ma_tb', data),
}
    