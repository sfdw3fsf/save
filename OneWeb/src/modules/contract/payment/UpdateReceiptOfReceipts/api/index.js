export default{
    lay_kieuBL:  (axios, data) => axios.post('/web-ccdv/bienlai-thutien/lay_ds_loai_bienlai', data),
    lay_loaiHD:  (axios,data) =>  axios.post('/web-ccdv/khaibaoims/lay_ds_loai_hd', data),
    lay_dsLuotTao:(axios,data) =>  axios.post('/web-ccdv/bienlai-thutien/lay_ds_luottao_bl_ql', data),
    lay_dsBienLai:(axios,data) =>  axios.post('/web-ccdv/bienlai-thutien/lay_ds_bienlai_theo_luottao', data),
    lay_dsBienLaiHDKH: (axios,data) =>  axios.post('/web-ccdv/bienlai-thutien/sp_lay_ds_bienlai_hdkh', data),
    lay_ds_hdkh_chuagan_bienlai:(axios,data) =>  axios.post('/web-ccdv/bienlai-thutien/lay_ds_hdkh_chuagan_bienlai', data),
    kiemtra_ins_bienlai_hdKH:(axios,data) =>  axios.post('/web-ccdv/ChuanHoaDuLieu/kiemtra_ins_bienlai_hdkh', data),
    capnhat_them_bienlai:(axios,data) =>  axios.post('/web-ccdv/ChuanHoaDuLieu/capnhat_them_hdkh_bienlai', data),
    capnhat_phathanh_bienlai:(axios,data) =>  axios.post('web-ccdv/ChuanHoaDuLieu/capnhat_phathanh_bienlai', data),
}