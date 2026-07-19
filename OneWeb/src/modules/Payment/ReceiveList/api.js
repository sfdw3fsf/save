export default {
    loadPage: (axios, params) => axios.get("web-hopdong/nhanbangke/fn_nhanbangke_load", { params }),
    getToVT: (axios, params) => axios.post("web-hopdong/nhanbangke/sp_ht_donvi_loaidv_combobox", params),
    getNoiNhan: (axios, params) => axios.post("web-hopdong/nhanbangke/sp_lay_tramvt_loai_dv", params),
    getNguoiNhan: (axios, params) => axios.post("web-hopdong/nhanbangke/sp_lay_ds_nhanvien_theo_donvi_loainv", params),
    getNguoiNop: (axios, params) => axios.post("web-hopdong/nhanbangke/sp_lay_ds_nhanvien_theo_donvi_loainv_2", params),
    getBangKe: (axios, params) => axios.post("web-hopdong/nhanbangke/lay_ds_bangke", params),
    keyPressMaBK: (axios, params) => axios.post("web-hopdong/nhanbangke/fn_nhanbangke_maso_keypress", params),
    get_DSHopDong_DaGan: (axios, params) => axios.post("web-hopdong/nhanbangke/lay_ds_hdkh_dagan_bk", params),
    get_DSThueBao: (axios, params) => axios.post("web-hopdong/nhanbangke/lay_ds_hdtb_dagan_bk", params),
    Btn_ckick_Nhan: (axios, params) => axios.post("web-hopdong/nhanbangke/capnhat_bangke", params),
}