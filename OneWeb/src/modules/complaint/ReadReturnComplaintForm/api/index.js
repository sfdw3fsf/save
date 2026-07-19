export default {
    kiemTrDuLieuTraPhieu: (axios, data) => axios.post('/web-thicong/khieunai/kiemtra_dl_traphieu_kn', data),
    capNhatDuLieuTraPhieu: (axios, data) => axios.post('/web-thicong/khieunai/capnhat_traphieu_kn_file', data),
}