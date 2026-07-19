import axios from "axios"

export default {
    NAP_CBO_TRANGTHAI_CC: () => axios.post("web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien", {
        "schema": "cabman",
        "list_of_cols": "*",
        "order": "TTCC_ID",
        "table_name": "TRANGTHAI_CC",
        "where": ""
    }),
    NAP_CBO_TTVT: (data) => axios.get(`web-tracuu/tracuu-phieucaitao-baoduong/sp_nap_ds_ttvt?nhanvien_id=${data}`),
    tracuu_hoso_cc_v2: (data) => axios.post(`web-tracuu/tracuu-phieucaitao-baoduong/tracuu_hoso_cc_v2`, data),
    NAP_THONGTIN_HOSO: (data) => axios.post(`web-tracuu/tracuu-phieucaitao-baoduong/sp_thongtin_hscc`, data),
    NAP_DS_PHANTU_MANG: (data) => axios.post(`web-tracuu/tcnone/lay_ds_ptm_caitao_baoduong`, data),
    tracuu_phieu_cc: (data) => axios.get(`web-tracuu/tcnone/tracuu_phieu_cc/${data}`),
    sp_lay_ds_nhanvien_thuchien: (data) => axios.post('web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_nhanvien_thuchien', data),

}