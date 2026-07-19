export default {
    ds_congdoan: (axios, body) => axios.post('/web-khdn/gancongdoan/dsCongDoan', body),
    dsLoaiNhanVien: (axios) => axios.post('/web-khdn/gancongdoan/dsLoaiNhanVien'),
    dsDonVi: (axios, data) => axios.post('/web-khdn/gancongdoan/dsDonVi', data),
    dsNhanVienChuaGan: (axios, body) => axios.post('/web-khdn/gancongdoan/dsNhanVienChuaGan', body),
    chotGanCongDoan: (axios, body) => axios.post('/web-khdn/gancongdoan/chotGanCongDoan', body),
    ganNhanVienHopDong: (axios, body) => axios.post('/web-khdn/gancongdoan/ganNhanVienHopDong', body),
    dsNhanVienDaGan: (axios, body) => axios.post('/web-khdn/gancongdoan/dsNhanVienDaGan', body),
    dsThueBao: (axios, hdkhId) => axios.get('/web-tracuu/hopdong/ds_hopdong_tb_theo_hdkh_id?hdkhId=' + hdkhId),    
    getFileVK: (axios, body) => axios.post('/web-khdn/gancongdoan/getFileVK', body),  
    xacThucNhanVien: async (axios, maNhanVien) => axios.get('tichhop/hrm/xacThucNhanVien?maNhanVien=' + maNhanVien),
  
    ADMIN_LOAI_NV: (axios) => axios.get('web-danhba/danhmuc-chung/ADMIN_LOAI_NV'),
    DS_LOAI_DV: (axios, dvQlId, loaiNvId) => axios.get('web-thicong/thuebao/lay_ds_donvi_qltb?dvQlId=' + dvQlId + '&loaiNvId=' + loaiNvId),
    DS_NHANVIEN_CHUAGAN: (axios, dvQlId, loaiNvId) => axios.get('web-thicong/thuebao/lay_ds_nhanvien_chuagan_v2?dvQlId=' + dvQlId + '&loaiNvId=' + loaiNvId),
    lay_ds_hopdong_theo_mathuebao: (axios, maTb) => axios.get('web-thicong/thuebao/lay_ds_hopdong_theo_mathuebao?maTb=' + maTb),
    load_ds_hopdong_thuebao: (axios, hdkhId) => axios.get('web-thicong/thuebao/load_ds_hopdong_thuebao?hdkhId=' + hdkhId),
    lay_ds_nhanvien_chuagan: (axios) => axios.get('web-thicong/thuebao/lay_ds_nhanvien_chuagan'),
    timkiem_ds_hopdong: (axios, kieu, maTb) => axios.get('web-thicong/thuebao/timkiem_ds_hopdong?kieu=' + kieu + '&maTb=' + maTb),
    xacThucNhanVien: async (axios, maNhanVien) => axios.get('tichhop/hrm/xacThucNhanVien?maNhanVien=' + maNhanVien),
    //gan_nhanvienql_dbhd : async(axios, data) => axios.post('web-thicong/thuebao/gan_nhanvienql_dbhd',data)
    gan_nhanvienql_dbhd: async (axios, data) => axios.post('web-thicong/thuebao/gan_nvql_dbhd', data),

    //Them API
    sp_lay_nhanvien_chuagan_cskh: async (axios, data) => axios.post('web-quantri/nhanvien/sp_lay_nhanvien_chuagan_cskh ', data),
        upload_file: async (axios, data, header) => axios.post('/web-quantri/upload', data, header),

}