
export default { 
    sp_lay_chitiet_suco_tdml: (axios, data) => axios.get('/web-thicong/truyenthong-suco/sp_lay_chitiet_suco_tdml?vphanloai='+data.vphanloai+'&vdb_id='+data.vdb_id),
    sp_lay_ds_suco_tdml: (axios, data) => axios.post(`/web-thicong/truyenthong-suco/sp_lay_ds_suco_tdml`, data),
    sp_update_thongtin_truyenthong_tdml: (axios, data) => axios.post(`/web-thicong/truyenthong-suco/sp_update_thongtin_truyenthong_tdml`, data),
    upload_file_hoso: async (axios, data,header) => axios.post('/web-quantri/upload', data,header),
    /*
    1. POST: web-thicong/truyenthong-suco/sp_lay_ds_suco_tdml
input:

{ "vphanloai": number, "vtungay": string, "vdenngay": string, "vtrangthai": number, "vnoidung_tk": string }
2. GET: web-thicong/truyenthong-suco/sp_lay_chitiet_suco_tdml?vphanloai=xxx&vdb_id=xxx

3. POST: web-thicong/truyenthong-suco/sp_update_thongtin_truyenthong_tdml
input:

{ "vphanloai": number, "vdb_id": number, "vtieude_tt": string, "vnoidung_tt": string, "vpath_file": string }
    */
}