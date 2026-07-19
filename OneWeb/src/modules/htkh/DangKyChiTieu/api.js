export default {

    //dk nghi
    getDonVi: (axios,data) => axios.get("web-hopdong/quanlydonhang/sp_lay_ds_donvi_xldh"),
    getNhanVien: (axios,p_donvi_id) => axios.post("web-hopdong/quanlydonhang/sp_lay_ds_nhanvien_xldh",{p_donvi_id}),
    getLyDoNghi: (axios) => axios.get("web-hopdong/quanlydonhang/sp_lay_danhmuc_lydonghi"),
    getDanhSachDkNghi : (axios, p_ma_nd) => axios.post("web-hopdong/quanlydonhang/sp_lay_thongtin_nghiphep", {p_ma_nd}),
    saveDKNghi : (axios, data) => axios.post("web-hopdong/quanlydonhang/fn_dangky_nghi_xuly", {p_json_data: data}),
    deleteDKNghi : (axios, p_rid) => axios.post("web-hopdong/quanlydonhang/fn_dangky_nghi_xoa", {p_rid}),


    //dk chi tieu
    getDsDangKyChiTieu: (axios, p_ma_nd) => axios.post("web-hopdong/quanlydonhang/sp_lay_thongtin_dangky_xldh", {p_ma_nd}),
    saveDKChiTieu : (axios, data) => axios.post("web-hopdong/quanlydonhang/fn_dangky_xldh_dangky", {p_json_data: data}),
    updateDKChitieu : (axios, p_rid, p_sl) => axios.post("web-hopdong/quanlydonhang/fn_dangky_xldh_capnhat", {p_rid,p_sl}),
    deleteDKChiTieu : (axios, p_rid) => axios.post("web-hopdong/quanlydonhang/fn_dangky_xldh_xoa", {p_rid}),


}



