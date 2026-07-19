export default {
    capnhat_thietbi_dk_biendong_idc : async (axios, data,header) => axios.post('/web-hopdong/dangky_sothanhvien/capnhat_thietbi_dk_biendong_idc', data, header),
    lay_ds_thietbi_idc_dangky_v2 : async (axios, data,header) => axios.post('/web-hopdong/dangky_sothanhvien/lay_ds_thietbi_idc_dangky', data, header),
    lay_ds_thietbi_idc_sudung : async (axios, data,header) => axios.post('/web-hopdong/dangky_sothanhvien/lay_ds_thietbi_idc_sudung', data, header)
}   