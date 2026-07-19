export default {
    ds_chukyno: async (axios, data) => axios.post('/web-quantri/chu_ky_no/sp_ds_chukyno'),
    update_chukyno: async (axios, data) => axios.post('/web-quantri/chu_ky_no/sp_chukyno_update', data),
    delete_chukyno: async (axios, data) => axios.post('/web-quantri/chu_ky_no/fn_chukyno_delete', data),
    kiemtra_chukyno: async (axios, data) => axios.post('/web-quantri/capnhatkhuyenmai-stb/fn_kiemtra_chukyno', data),
    kiemtra_tao_bang: async (axios, data) => axios.post('/web-quantri/capnhatkhuyenmai-stb/kiemtra_tao_bang', data),
}
