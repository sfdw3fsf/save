export default{
    updateThongTinLienHe: (axios, data) => axios.post('/web-hopdong/bss_group1/fn_capnhat_thongtin_lienhe', data),
    checkCapNhatTTLH_TT: (axios) => axios.get('/web-hopdong/bss_group1/ktra_capnhat_ttlh_thanhtoan'),
    spDocFileCapNhatTTLH: (axios, data) => axios.post('/web-hopdong/bss_group1/sp_docfile_capnhat_ttlh', data),
    updateTTLHThanhToan: (axios) => axios.post('/web-hopdong/bss_group1/capnhat_ttlh_thanhtoan')
}