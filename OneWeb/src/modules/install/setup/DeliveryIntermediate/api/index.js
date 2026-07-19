export default {
    CSS_LOAI_HD: async (axios, data,header) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD', data,header),
    //getDsDichvu: (axios, data) => axios.post('/web-hopdong/tachnhapthuebao/sp_lay_dichvu_vt_theo_nd', data),
    getDSDichVu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    sp_lay_ds_huonggiao: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/sp_lay_ds_huonggiao', data,header),
    sp_lay_ds_hdtb_chuagiao: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/sp_lay_ds_hdtb_chuagiao', data,header),
    lay_ds_hdtb_dagiao: async (axios, data,header) => axios.post('/web-ccdv/giaophieu/lay_ds_hdtb_dagiao', data,header),
    getDsTocdokenh: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH', data),
    get_ds_hopdongthuebao: async (axios, data) => axios.get(`/web-tracuu/hopdong/ds_hopdong_tb_theo_hdkh_id?hdkhId=${data.hdkhId}`, data),
    get_ds_tientrinhxuly: async (axios, data) => axios.get(`/web-thicong/thuebao/lay_danhsach_phieuth?hdtb_id=${data.hdtb_id}`, data),
    getDsHDTheoMaTB: (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_hd_theo_ma_tb', data),
    giaophieu_diemchia_trunggian_tsl: async (axios, data,header) => axios.post('/web-thicong/thuebao/giaophieu_diemchia_trunggian_tsl', data,header),
    huyphieu_diemchia_trunggian_tsl: async (axios, data,header) => axios.post('/web-thicong/thuebao/xoa_phieu_diemchia_trunggian_tsl', data,header),
    getDsToThiCongDau: (axios, data) => axios.post('/web-quantri/donvi-loai-khachhang/sp_dvi_loaikh_layds_dvi', data),
    getDsToThiCongCuoi: (axios, data) => axios.post('/web-quantri/donvi-loai-khachhang/sp_dvi_loaikh_layds_dvi', data), 
    getDsGiaoDiemChia: (axios, data) => axios.post('/web-quantri/donvi-loai-khachhang/sp_dvi_loaikh_layds_dvi', data),
}
  