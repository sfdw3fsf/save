export default{
    Lay_ds_nv_dagan: (axios,data) => axios.get(`/web-thicong/quanlydonhang/lay_ct_nhanvien_theo_nhom?nhom_id=${data.nhom_id}`),
    Lay_ds_nv_chuagan : (axios,data) => axios.get(`/web-thicong/quanlydonhang/lay_ds_nhanvien_theo_phanvung?ma_nv=${data.ma_nv}`),
    Chuyen_doi: (axios,data) => axios.post("/web-thicong/quanlydonhang/fn_map_nv_tt_nhieutinh",data),
    Ds_tt_tracuu: (axios) => axios.get("/web-thicong/quanlydonhang/sp_cbx_timkiem_tt_nhom"),
    Tracuu_cbo: (axios,data) => axios.post("/web-thicong/quanlydonhang/sp_thuchien_timkiem_tt_nhom",data),
    Tracuu: (axios,data) => axios.post("/web-thicong/quanlydonhang/sp_timkiem_tt_nhom",data),
    Tracuu_ma_nv: (axios,data) => axios.post("/web-thicong/quanlydonhang/sp_timkiem_tt_nhom_manv",data), // not Use
    Tracuu_ma_user: (axios,data) => axios.post("/web-thicong/quanlydonhang/sp_timkiem_tt_nhom_mand",data), // not Use
    Capnhat: (axios,data) => axios.post("/web-thicong/quanlydonhang/update_nhom_warp",data),
    Them_nhom: (axios,data) => axios.post("/web-thicong/quanlydonhang/them_nhom_warp",data),
    Xoa_nhom: (axios,data) => axios.get(`/web-thicong/quanlydonhang/xoa_nhom_warp?nhanvien_id=${data.nhom_id}`),
    Xoa_nhanvien: (axios,data) => axios.post(`/web-thicong/quanlydonhang/xoa_nv_tt_nhom_warp`,data),
}