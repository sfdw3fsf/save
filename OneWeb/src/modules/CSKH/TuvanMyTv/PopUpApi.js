
export default { 
    kiemtra_tuvan_mytv: (axios, data) => axios.get('/web-ccdv/tuvan_mytv/kiemtra_tuvan_mytv?hdkh_id='+data.hdkh_id+'&loai='+data.loai),
    lay_danhmuc_tuvan_mytv: (axios, data) => axios.get('/web-ccdv/tuvan_mytv/lay_danhmuc_tuvan_mytv'),
    tuvan_mytv_fiber_hienhuu: (axios, data) => axios.post(`/web-ccdv/tuvan_mytv/tuvan_mytv_fiber_hienhuu`, data),    
    fn_tt_db_khachhang: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_khachhang`, data),    
    sp_tt_loaihinh_tb: (axios, data) => axios.post(`/web-quantri/lay-dulieu/sp_tt_loaihinh_tb`, data),    
    

    /*    
    Bùi Ngọc Duy, [13/06/2023 1:50 PM]
POST web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_khachhang
{ type  =3, param = khachhang_id }

Bùi Ngọc Duy, [13/06/2023 1:51 PM]
POST web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_khachhang
{ type  =9, param = hdkh_id }
    POST https://api-onebss.vnpt.vn/web-ccdv/tuvan_mytv/tuvan_mytv_fiber_hienhuu
    {
    vkhachhang_id
    vhdkh_id
    vloai
    vhinhthuctv_id
    vhinhthuc_tv
    vngaytv  (dd/MM/yyyy)
    vquanhetv_id
    vquanhe_tv
    vso_dt
    vnhucau_id
    vnhucau
    vketqua_id
    vketqua
    vhentv
    vdsloaitb_id
    }   */
}