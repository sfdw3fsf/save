export default{
    // BSS-38476_002
    lay_danhmuc_trangthai_ph: async (axios) => axios.get('/web-hopdong/capnhat_ketqua_xuly_phieu_phoihop/lay_danhmuc_trangthai_ph'),
    // BSS-38476_003
    lay_kq_xuly_ph: async (axios) => axios.get('/web-hopdong/capnhat_ketqua_xuly_phieu_phoihop/lay_kq_xuly_ph'),
    // BSS-38476_005
    lay_danhsach_phieu_ph: async (axios, data) => axios.post('/web-hopdong/capnhat_ketqua_xuly_phieu_phoihop/lay_danhsach_phieu_ph', data),
    // BSS-38476_008
    capnhat_xl_phieu_ph: async (axios, data) => axios.post('/web-hopdong/capnhat_ketqua_xuly_phieu_phoihop/capnhat_xl_phieu_ph', data),
}