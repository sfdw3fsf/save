export default {
    getDSTiepNhanXuLy: async (axios,vkieu) => axios.post('/web-ccdv/xuly_yeucau_lapdat/lay_ds_tiepnhan_xuly', { vkieu }),
   
    capNhatToXlHDTiepNhan: async (axios, { vphieuhd_id, vdonvi_nhan_id }) => axios.post('/web-ccdv/xuly_yeucau_lapdat/capnhat_to_xl_hd_tiepnhan', { vdonvi_nhan_id, vphieuhd_id }),
   
    chuyenDLHDTNSangHDDatMoi: async (axios,  vhdkh_id ) => axios.post('/web-ccdv/xuly_yeucau_lapdat/chuyen_dl_hdtn_sang_hd_datmoi', { vhdkh_id }),
   
    traPhieuXLHDTiepNhan: async (axios,  { vphieu_cha_id, vphieuhd_id } ) => axios.post('/web-ccdv/xuly_yeucau_lapdat/traphieu_xl_hd_tiepnhan', { vphieu_cha_id, vphieuhd_id }),

    huyXuLyHDTiepNhan: async (axios, { vhdkh_id, vhdkhtn_id }) => axios.post('/web-ccdv/xuly_yeucau_lapdat/huy_xuly_hd_tiepnhan', { vhdkh_id, vhdkhtn_id }),

    getDanhSachDonViNhan: async (axios) => axios.get('/web-ccdv/xuly_yeucau_lapdat/lay_dvnhan_xuly_hd_tiepnhan'),
    lay_danhsach_donvi_phathanh:async (axios) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_donvi_phathanh'),
    lay_danhsach_phanloai_hd :async (axios) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_phanloai_hd'),
    lay_lay_ds_coche_doanhthu : async (axios) => axios.get('/web-khdn/danhmucbancheo/lay_lay_ds_coche_doanhthu'),
    dichvu_vt: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', null),
    lay_danhsach_donvi_banhang: async (axios, data) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_donvi_banhang?p_donvi_id='+data),
    tracuu_congvan : async (axios, data) => axios.post('/web-khdn/doanhthu/tracuu_congvan', data),
    lay_thongtin_phanchia_doanhthu : async (axios, data) => axios.get('web-khdn/doanhthu/lay_thongtin_phanchia_doanhthu?p_coche_id='+data),
    loai_hd:(axios) =>  axios.get('/web-tracuu/danhmuc/loai_hd'),
    post_lay_danhmuc_lapmoi_tt_chung_v8 : async (axios, data) => axios.post('/web-danhba/lapdatmoi/lay_danhmuc_lapmoi_tt_chung_v8', data), // lấy loại hình
    lay_danhsach_congdoan: async (axios, data) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_congdoan', null),
    capnhat_coche_doanhthu : async (axios, data) => axios.post('/web-khdn/doanhthu/capnhat_coche_doanhthu', data),
    lay_danhsach_loai_phattrien : async (axios) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_loai_phattrien'),
    lay_thongtin_hdkh_theo_hdkh_id: async (axios, data) => axios.get('/web-khdn/doanhthu/lay_thongtin_hdkh_theo_hdkh_id?p_hdkh_id='+data),
    xoa_chitiet_coche_doanhthu : async (axios, data) => axios.post('/web-khdn/doanhthu/xoa_chitiet_coche_doanhthu', data),
    CSS_LOAIHINH_TB: async (axios, data) => axios.get('web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', null),// https://api-dev-onebss.vnpt.vn/web-quantri/danhmuc-chung/CSS_LOAIHINH_T
    get_tracuu: async (axios, data) => axios.post('/web-tracuu/danhba/tracuu', data),
    sp_tracuu_tt_kh_theo_makh: async (axios, data) => axios.get('/web-khdn/doanhthu/sp_tracuu_tt_kh_theo_makh?p_ma_kh='+data),

}