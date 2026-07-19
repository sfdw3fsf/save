export default {
    ds_ma_muc_dich_theo_muc_dich: async (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-ma-muc-dich-theo-muc-dich/${data.mucdich_id}`),
    ma_co_so_ha_tang: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ma-co-so-ha-tang/${data.kho_id}`),
    ds_ref_ims: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-ref-ims`,data),
    asset_by_keys: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/asset-by-keys`,data),
    contract_info_by_project_code: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/contract-info-by-project-code?maDuan=${data.maDuan}&maCsht=${data.maCsht}`),
    kiem_tra_sl_qlts: (axios, data) => axios.post(`/web-qlvt/api/nhap-xuat-chuyen-vt/kiem-tra-sl-qlts`,data),
    danh_muc_loai_chung_tu_nx: (axios, data) => axios.get(`/web-qlvt/api/chon-chung-tu-ccdc/danh-muc-loai-chung-tu-nx/${data.kieu}`),
    ds_don_vi_su_dung: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-don-vi-su-dung/${data.kieu}`),
    asset_get_station_by_code: (axios, data) => axios.post('tichhop/qlts/asset_get_station_by_code', data),
    ds_tinh: (axios, data) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-tinh`),
    ds_phieu_nv: (axios, mucDichId) => axios.get(`/web-qlvt/api/nhap-xuat-chuyen-vt/ds-phieu-nv/${mucdich_id}`),
};
