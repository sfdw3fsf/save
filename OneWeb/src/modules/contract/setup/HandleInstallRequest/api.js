export default {
    getDSTiepNhanXuLy: async (axios,vkieu) => axios.post('/web-ccdv/xuly_yeucau_lapdat/lay_ds_tiepnhan_xuly', { vkieu }),
   
    capNhatToXlHDTiepNhan: async (axios, { vphieuhd_id, vdonvi_nhan_id }) => axios.post('/web-ccdv/xuly_yeucau_lapdat/capnhat_to_xl_hd_tiepnhan', { vdonvi_nhan_id, vphieuhd_id }),
   
    chuyenDLHDTNSangHDDatMoi: async (axios,  vhdkh_id ) => axios.post('/web-ccdv/xuly_yeucau_lapdat/chuyen_dl_hdtn_sang_hd_datmoi', { vhdkh_id }),
   
    traPhieuXLHDTiepNhan: async (axios,  { vphieu_cha_id, vphieuhd_id } ) => axios.post('/web-ccdv/xuly_yeucau_lapdat/traphieu_xl_hd_tiepnhan', { vphieu_cha_id, vphieuhd_id }),

    huyXuLyHDTiepNhan: async (axios, { vhdkh_id, vhdkhtn_id }) => axios.post('/web-ccdv/xuly_yeucau_lapdat/huy_xuly_hd_tiepnhan', { vhdkh_id, vhdkhtn_id }),

    getDanhSachDonViNhan: async (axios) => axios.get('/web-ccdv/xuly_yeucau_lapdat/lay_dvnhan_xuly_hd_tiepnhan'),

}