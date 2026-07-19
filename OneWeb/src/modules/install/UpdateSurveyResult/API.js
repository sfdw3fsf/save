export default {
    //task lấy loaihd=33,34,35 
    lay_ds_loai_hd: async (axios) => axios.get('/web-thicong/danhmuc/lay_ds_loai_hd?loaiHdId=33,34,35'),
    lay_ds_ly_do_ton: async (axios) => axios.get('/web-thicong/danhmuc/lay_ds_ly_do_ton'),
    lay_ds_ketqua_khaosat: async (axios) => axios.get('/web-thicong/danhmuc/lay_ds_ketqua_khaosat'),
    lay_ds_nhanvien_thicong: async (axios, loainv_id) => axios.get('/web-thicong/danhmuc/lay_ds_nhanvien_thicong?loaiNvId='+loainv_id),

    ds_phieu_khao_sat_v2: async (axios, data) => axios.post('/web-thicong/thuebao/ds_phieu_khao_sat_v2', data),

    // lay_thongtin_nhanvien: async (axios, nhanvien_id) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md', {
    //     list_of_cols: "ma_nv, ten_nv, so_dt",
    //     order: "",
    //     schema: "admin",
    //     table_name: "nhanvien",
    //     where: "nhanvien_id="+nhanvien_id
    // }),
    lay_thongtin_nhanvien: async (axios, nhanvien_id) => axios.post('/web-hopdong/laydulieu/sp_tt_nhanvien', {
        p_param: nhanvien_id,
        p_type:4
    }),

    

    // Cập nhật nội dung thực hiện
    capnhat_noidung_thuchien: async (axios, phieuId, noiDung) => axios.post('/web-thicong/thuebao/capnhat_noidung_thuchien?phieuId='+phieuId+'&&noiDung='+noiDung, {}),

    ds_giaoviec_nhanvien: async (axios, phieuId, kieu) => axios.get('/web-thicong/thuebao/ds_giaoviec_nhanvien?phieuId='+phieuId+'&&kieu='+kieu),
    
    capnhat_thongtin: async (axios, data) => axios.post('/web-thicong/thuebao/capnhat_thongtin', data),

    //fn_map_id: async (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),
    fn_tt_hd_thuebao: async (axios, hdtb_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao',{
        param: hdtb_id,
        type: 1
    }),

    
    

}