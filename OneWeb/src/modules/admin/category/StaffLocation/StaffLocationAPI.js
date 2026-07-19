export default{
    sp_lay_ds_thamso_md_mats: async (axios) => axios.get('/web-quantri/diaban_nvkd/lay_ds_thamso'),
    lay_ds_thongtin_log_khuvuc: async (axios, khuvucId) => axios.get('/web-quantri/diaban_nvkd/lay_ds_thongtin_log_khuvuc?khuvucId='+khuvucId),

    lay_ds_log_bdtb_matb: async (axios, maTb) => axios.get('/web-quantri/diaban_nvkd/lay_ds_log_bdtb_matb?maTb='+maTb),
    
    //9: Nhóm Chăm sóc KH
    sp_lay_loainv_nhomlv: async (axios) => axios.get('/web-quantri/diaban_nvkd/lay_ds_loai_nhanvien_theo_nhom?nhomlnvId=9'),
    
    sp_lay_ds_loai_kv: async (axios) => axios.get('/web-quantri/diaban_nvkd/lay_ds_loai_khuvuc'),

    //9: Nhóm Chăm sóc KH
    sp_lay_ds_donvi_theo_nhomlnv: async (axios) => axios.get('/web-quantri/diaban_nvkd/lay_ds_donvi_theo_nhomlnv?nhomlnvId=9'),
    
    // data
    // {
    //     "loaikv_id":13,
    //     "tentat":"HPG",
    //     "donvi_id": 186,
    //     "nhanvien_id": 13431
    // }
    sp_lay_ds_khuvuc_theo_loaikv: async (axios, data) => axios.post('/web-quantri/diaban_nvkd/lay_ds_khuvuc_theo_loai',data),

    // {
    //     "loai":1,
    //     "loaikv_id": 13,
    //     "khuvuc_id": 676,
    //     "loainv_id": 51,
    //     "donvi_id": -1
    // }
    //sp_hienthi_nhanvien_theo_kv: async (axios, data) => axios.post('/web-quantri/diaban_nvkd/lay_ds_nhanvien_theo_khuvuc',data),

    sp_hienthi_nhanvien_theo_kv: async (axios, data) => axios.post('/web-quantri/diaban-nvkt/sp_hienthi_nhanvien_theo_kv',data),

    // modal DSCacKhuVuc
    sp_lay_khuvuc_by_manv: async (axios, ma_nv) => axios.post('/web-quantri/nhanvien/sp_lay_khuvuc_by_manv',{
        ma_nv:ma_nv
    }),

    //data
    // {
    //   "khuvuc_id": 30,
    //   "loaikv_id": 2,
    //   "loainv_id": 51,
    //   "nhiemvu": 1,
    //   "loai_form": 1,
    //   "danhsach_dagan": [{"nhanvien_id": "7022", "loainv_id" :"52", "nhiemvu":"1"}, {"nhanvien_id": "3924", "loainv_id" :"52", "nhiemvu":"1"}],
    //   "danhsach_khuvuc_nv": [{"khuvuc_id": "30", "nhanvien_id" :"7022", "loainv_id":"52", "loaikv_id":"4", "nhiemvu":"1"}, {"khuvuc_id": "30", "nhanvien_id" :"3924", "loainv_id":"52", "loaikv_id":"4", "nhiemvu":"1"}]
    // }
    //Dùng chung bên NVKT với loai_form:1
    sp_ghilai_gan_nv_khuvuc: async (axios, data) => axios.post('/web-quantri/diaban-nvkt/sp_ghilai_gan_nv_khuvuc',data),
    //sp_ghilai_gan_nv_khuvuc: async (axios, data) => axios.post('/web-quantri/diaban_nvkd/ghilai_gan_nhanvien_khuvuc',data),
    
    

    //Modal CapNhatNhanVien
    //data
    // {
    //     "donvi_id": 0, 
    //     "kieu": 1
    // }
    sp_lay_tt_nv_macdinh: async (axios, data) => axios.post('/web-quantri/nhanvien/sp_lay_tt_nv_macdinh', data),

    //data vnhanvien_id=-1 gán xoá, ngược lại gán
    // {
    //     "vdonvi_id": 0,
    //     "vmay_cn": "destop",
    //     "vnguoi_cn": "diep",
    //     "vip_cn": "12.12.12.12",
    //     "vnhanvien_id": 0
    // }
    capnhat_nhanvien_macdinh: async (axios, data) => axios.post('/web-quantri/nhanvien/capnhat_nhanvien_macdinh', data),
    
}