export default {
    capnhat_thietbi_dk_biendong_idc : async (axios, data,header) => axios.post('/web-hopdong/dangky_sothanhvien/capnhat_thietbi_dk_biendong_idc', data, header),
    lay_ds_thietbi_idc_dangky : async (axios, data,header) => axios.post('/web-hopdong/dangky_sothanhvien/lay_ds_thietbi_idc_dangky', data, header),
    lay_ds_thietbi_idc_sudung : async (axios, data,header) => axios.post('/web-hopdong/dangky_sothanhvien/lay_ds_thietbi_idc_sudung', data, header),
    // danh sách số thành viên
    ds_thietbi_idc_sudung :  async (axios, data,header) => axios.post('/web-hopdong/danh_sach_so_thanh_vien/lay_ds_thietbi_idc_sudung', data, header),
    ds_thietbi_idc_dangky :  async (axios, data,header) => axios.post('/web-hopdong/danh_sach_so_thanh_vien/lay_ds_thietbi_idc_dangky', data, header),
    // danh sách số thành viên
}   
    