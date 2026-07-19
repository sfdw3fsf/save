export default {
    sp_lay_ds_duyet_tinhcuoc_nhancong: async (axios, data,header) => axios.post('/web-tracuu/tinhcuocnhancong/sp_lay_ds_duyet_tinhcuoc_nhancong', data,header),
    lay_ds_pbh_tvdt: async (axios, data,header) => axios.post('/web-tracuu/tinhcuocnhancong/lay_ds_pbh_tvdt ', data,header),
    sp_setduyet_tinhcuoc_nhancong: async (axios, data,header) => axios.post('/web-tracuu/tinhcuocnhancong/sp_setduyet_tinhcuoc_nhancong', data,header),
    upload_file: async (axios, data,header) => axios.post('/web-quantri/upload', data,header),
    view_file: async (axios, data,header) => axios.post('/web-quantri/upload/link_view_file', data,header),
    lay_dsFile_Kenhban: async (axios, data,header) => axios.post('/web-quantri/vnp08/dsFileKB', data,header),
    capNhatFileKB: async (axios, data,header) => axios.post('/web-quantri/vnp08/capNhatFileKB', data,header),
    dsLoaiFile: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/dsLoaiFile', data,header),
    dsFileHDK: async (axios, data,header) => axios.post('/web-khdn/dxhopdong/dsFileHDK', data,header),
    sp_upload_file_tinhcuoc_nhancong: async (axios, data,header) => axios.post('/web-tracuu/tinhcuocnhancong/sp_upload_file_tinhcuoc_nhancong', data,header),
    sp_lay_ds_file_tinhcuoc_nhancong: async (axios, data,header) => axios.post('/web-tracuu/tinhcuocnhancong/sp_lay_ds_file_tinhcuoc_nhancong', data,header),
    sp_xoa_file_tinhcuoc_nhancong: async (axios, data,header) => axios.post('/web-tracuu/tinhcuocnhancong/sp_xoa_file_tinhcuoc_nhancong', data,header),
    sp_chuyen_trangthai_tinhcuoc_nhancong: async (axios, data,header) => axios.post('/web-tracuu/tinhcuocnhancong/sp_chuyen_trangthai_tinhcuoc_nhancong', data,header),

    // POST : web-tracuu/tinhcuocnhancong/sp_upload_file_tinhcuoc_nhancong

    // { "p_ma_tap":"", "p_file_url":"", "p_file_name":"" }
    // POST : web-tracuu/tinhcuocnhancong/sp_lay_ds_file_tinhcuoc_nhancong

    // { "p_ma_tap":""}
    // POST : web-tracuu/tinhcuocnhancong/sp_xoa_file_tinhcuoc_nhancong

    // { "p_ma_tap":"", "p_file_id":""}
}






   
