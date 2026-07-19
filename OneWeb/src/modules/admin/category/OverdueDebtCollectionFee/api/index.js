export default {
    getDSDonGiaChuKySo: (axios, data) => axios.post('/web-quantri/thunoquahan/sp_lay_ds_dongia_ps_gan_chukyno', data),
    getDSDonGiaNhanVien: (axios, data) => axios.post('/web-quantri/thunoquahan/sp_lay_ds_dongia_ps_gan_nv', data),
    getDSDonGiaKhuVuc: (axios, data) => axios.post('/web-quantri/thunoquahan/sp_lay_ds_dongia_ps_gan_kv', data),
    postGhiLaiThuLaoNQH: (axios, data) => axios.post('/web-quantri/thunoquahan/sp_ghilai_thulao_nqh', data),
    postXoaThuLaoNQH: (axios, data) => axios.post('/web-quantri/thunoquahan/sp_xoa_thulao_nqh', data),
    getDsKieuNoQH: (axios) => axios.get('/web-quantri/thunoquahan/sp_ds_thulao_kieuno_qh'),
    getDSThuLaoNoQH: (axios) => axios.get('/web-quantri/thunoquahan/sp_ds_thulao_nqh')
}