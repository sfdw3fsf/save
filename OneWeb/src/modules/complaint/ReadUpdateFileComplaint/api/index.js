
export default {
    insertBanGhi: (axios, data) => axios.post('/web-thicong/khieunai/kiemtra_dl_capnhat_choxl_kn', data),
    capNhatFile: (axios, data) => axios.post('/web-thicong/khieunai/capnhat_choxl_kn_file', data)
}