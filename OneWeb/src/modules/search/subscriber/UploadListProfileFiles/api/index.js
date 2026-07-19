export default {
    upload_file: async (axios, data, header) => axios.post('/web-quantri/upload', data,header),
    layAnhTheoId: (axios, data) => axios.post('/web-quantri/upload/link_view_file', data),
    loadForm: async (axios, data) => axios.post('/web-quantri/uploadfile-hoso/sp_danhmuc_file_tb_theods', data),
    layThongTinTheoMaGD: async (axios, data) => axios.post('/web-quantri/uploadfile-hoso/sp_ds_tb_upload_file_hs', data),
    capNhat: async (axios, data) => axios.post('/web-quantri/uploadfile-hoso/sp_insert_hoso_thuebao', data),
    // {
    //     "loaifile_id": 1,
    //      "db_id": 6164762,
    //     "loaidb_id": 0,
    //     "hoso_id": 523395,
    //     "file_name": "test file",
    //     "file_url": "D:\\data\\TT78",
    //     "ghichu": "test"
    // }
    
}