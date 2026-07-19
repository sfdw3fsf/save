export default{
    // ADMIN_LOAI_NV: (axios) => axios.get('web-quantri/danhmuc-chung/ADMIN_LOAI_NV'),
    ADMIN_LOAI_NV: (axios) => axios.get('web-danhba/danhmuc-chung/ADMIN_LOAI_NV'),
    DS_LOAI_DV: (axios,dvQlId,loaiNvId) => axios.get('web-thicong/thuebao/lay_ds_donvi_qltb?dvQlId='+dvQlId+'&loaiNvId='+loaiNvId),
    DS_NHANVIEN_CHUAGAN: (axios,dvQlId,loaiNvId) => axios.get('web-thicong/thuebao/lay_ds_nhanvien_chuagan_v2?dvQlId='+dvQlId+'&loaiNvId='+loaiNvId),
    lay_ds_hopdong_theo_mathuebao: (axios,maTb) => axios.get('web-thicong/thuebao/lay_ds_hopdong_theo_mathuebao?maTb='+maTb),
    load_ds_hopdong_thuebao: (axios,hdkhId) => axios.get('web-thicong/thuebao/load_ds_hopdong_thuebao?hdkhId='+hdkhId),
    lay_ds_nhanvien_chuagan: (axios) => axios.get('web-thicong/thuebao/lay_ds_nhanvien_chuagan'),
    timkiem_ds_hopdong: (axios,kieu,maTb) => axios.get('web-thicong/thuebao/timkiem_ds_hopdong?kieu='+kieu+'&maTb='+maTb),
    xacThucNhanVien:  async (axios,maNhanVien) =>  axios.get('tichhop/hrm/xacThucNhanVien?maNhanVien='+maNhanVien),
    //gan_nhanvienql_dbhd : async(axios, data) => axios.post('web-thicong/thuebao/gan_nhanvienql_dbhd',data)
    gan_nhanvienql_dbhd : async(axios, data) => axios.post('web-thicong/thuebao/gan_nvql_dbhd',data),

    //Them API
    sp_lay_nhanvien_chuagan_cskh : async(axios, data) => axios.post('web-quantri/nhanvien/sp_lay_nhanvien_chuagan_cskh ',data)
}