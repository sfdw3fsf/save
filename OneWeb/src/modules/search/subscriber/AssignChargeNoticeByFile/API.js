export default {
    lay_loaitin_test_tbc: (axios) => axios.get('/web-thicong/gui_tbc_theofile/lay_loaitin_test_tbc'),
    fn_ins_dl_tmp_thuchien_tbc: (axios, data) => axios.post('/web-thicong/gui_tbc_theofile/fn_ins_dl_tmp_thuchien_tbc', data), // { vds_tmp_thuchien_tbc }
    fn_ins_dl_loi_thuchien_tbc: (axios, data) => axios.post('/web-thicong/gui_tbc_theofile/fn_ins_dl_loi_thuchien_tbc', data), // { vds_loi_thuchien_tbc }
    kiemtra_dl_test_tbc: (axios) => axios.get('/web-thicong/gui_tbc_theofile/kiemtra_dl_test_tbc'),
    capnhat_dl_test_tbc: (axios, data) => axios.post('/web-thicong/gui_tbc_theofile/capnhat_dl_test_tbc', data), // { vloaitin_id }
}