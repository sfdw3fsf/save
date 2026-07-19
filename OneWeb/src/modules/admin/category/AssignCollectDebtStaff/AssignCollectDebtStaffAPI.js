export default {
    sp_nvtc_theo_nguoidung: async (axios, type, nguoidung_id) => axios.post('/web-quantri/nhanvien-thucuoc/sp_nvtc_theo_nguoidung',{
        type:type,
        nguoidung_id:nguoidung_id
    }),
    sp_nvtc_nguoidung: async (axios, ma_nd) => axios.post('/web-quantri/nhanvien-thucuoc/sp_nvtc_nguoidung',{
        ma_nd: ma_nd
    }),
    sp_nvtc_update: async (axios, nguoidung_id, data) => axios.post('/web-quantri/nhanvien-thucuoc/sp_nvtc_update',{
        nguoidung_id:nguoidung_id,
        js_data:data
    }),
    //import file [{ 'MA_ND': 'test_app.hpg', 'MA_NVTC': 'NQ2_KHDN'}]
    sp_nvtc_file_import: async (axios, data) => axios.post('/web-quantri/nhanvien-thucuoc/sp_nvtc_file_import',{
        js_data:data
    }),
}