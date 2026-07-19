export default {
    getKey: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/get_keys', data),
    FN_TT_TOCDO_ADSL: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl', data),

    // UR1.3.006_001 - Form load
    fn_muccuoctb_load: async (axios, phanvung_id, para) => axios.post('/web-quantri/muccuoc/fn_muccuoctb_load', {
        p_phanvung_id: phanvung_id,
        p_ds_para: para
    }),
    // UR1.3.006_002 - Hiển thị đối tượng thuê bao
    fn_hienthi_doituongtb: async (axios, para) => axios.post('/web-quantri/muccuoc/fn_hienthi_doituongtb', {
        p_ds_para: para
    }),
    // UR1.3.006_003 - Lấy danh sách khu vực
    bangtudien_sp_layds_khuvuc: async (axios, muccuoc_id, kieu) => axios.post('/web-quantri/muccuoc/bangtudien_sp_layds_khuvuc', {
        p_muccuoc_id: muccuoc_id,
        p_kieu: kieu
    }),
    // UR1.3.006_004 - Lấy danh sách loại hình
    bangtudien_sp_lay_layds_lhtb: async (axios, muccuoc_id, dichvuvt_id, kieu) => axios.post('/web-quantri/muccuoc/bangtudien_sp_lay_layds_lhtb', {
        p_muccuoc_id: muccuoc_id,
        p_dichvuvt_id: dichvuvt_id,
        p_kieu: kieu
    }),
    // UR1.3.006_005 - Lấy danh sách trang bị
    sp_layds_trangbi: async (axios, muccuoc_id, kieu) => axios.post('/web-tracuu/tracuu-thuebao/sp_layds_trangbi', {
        p_muccuoc_id: muccuoc_id,
        p_kieu: kieu
    }),
    // UR1.3.006_006 - Lấy danh sách mức cước
    sp_selectmuccuoc_tb: async (axios, dichvuvt_id, loaitb_id, tocdo_id) => axios.post('/web-quantri/muccuoc/sp_selectmuccuoc_tb', {
        p_dichvuvt_id: dichvuvt_id,
        p_loaitb_id: loaitb_id,
        p_tocdo_id: tocdo_id
    }),
    // UR1.3.006_007 - Lấy danh sách mức cước theo mức cước ID
    sp_selectmuccuoc_tb_theo_muccuoc_id: async (axios, mucuoctb_id) => axios.post('/web-quantri/muccuoc/sp_selectmuccuoc_tb_theo_muccuoc_id', {
        p_mucuoctb_id: mucuoctb_id
    }),
    // UR1.3.006_007_001 - Lấy danh sách Tốc độ Adsl
    sp_ht_tocdo_adsl_v2: async (axios, data) => axios.post('/web-quantri/muccuoc/sp_ht_tocdo_adsl_v2', {
        p_ds_loaitb: data.ds_loaitb,
        p_phanvung_id: data.phanvung_id
    }),
    // UR1.3.006_008 - Lấy tốc đọ theo loại tb
    sp_todotk_loaihinhtk: async (axios, loaitb_id) => axios.post('/web-quantri/muccuoc/sp_tocdo_adsl_combobox_byloaitb_moiv2', {
        p_loaitb_id: loaitb_id
    }),
    // UR1.3.006_009 - Lấy danh sách loại hình TB theo dịch vụ vt
    lay_danhsach_loaihinhtb: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
    // UR1.3.006_010 - Cập nhật
    // {"nhapmoi":number(0:1), "muccuoctb_id":number, "js_muccuoc_tb":json_string(MUCCUOC_TB_DATA), "js_mctb_dt":json_string(MCTB_DT_DATA), "js_mctb_lhtb":json_string(MCTB_LHTB_DATA), "js_mctb_kv":json_string(MCTB_KV_DATA), "js_mctb_tbi":json_string(MUCCUOC_TB_DATA)}
    fn_muccuoctb_capnhat: async (axios, para) => axios.post('/web-quantri/muccuoc/fn_muccuoctb_capnhat', {
        p_ds_para: para
    }),
    // UR1.3.006_011 - Xóa
    fn_muccuoctb_xoa: async (axios, para) => axios.post('/web-quantri/muccuoc/fn_muccuoctb_xoa', {
        p_ds_para: JSON.stringify(para)
    }),
    // UR1.3.006_012 - frmMucCuocTuDen_Load
    frmMucCuocTuDen_Load: async (axios, mucuoctb_id) => axios.post('/web-quantri/muccuoc/sp_lay_tttc_theo_mucuoctb_id', {
        p_mucuoctb_id: mucuoctb_id
    }),
    // UR1.3.006_013 - frmMucCuocTuDen -> tsbtnGhiLai_Click
    capnhat_cuoc_tu_den: async (axios, data) => axios.post('/web-quantri/muccuoc/capnhat_cuoc_tu_den', {
        vmucuoctb_id: data.vmucuoctb_id,
        vcuockhoan_tu: data.vcuockhoan_tu,
        vcuockhoan_den: data.vcuockhoan_den,
        vcuoc_tg_tu: data.vcuoc_tg_tu,
        vcuoc_tg_den: data.vcuoc_tg_den,
        vcuoc_tb_tu: data.vcuoc_tb_tu,
        vcuoc_tb_den: data.vcuoc_tb_den
    }),


    // [CDS-HCM: BSS-96626] 25/07/2023
    sp_lay_ds_toanha_theo_muccuoc: async (axios, muccuoc_id, kieu) => axios.post('/web-ccdv/hopdong/sp_lay_ds_toanha_theo_muccuoc', { 
        p_muccuoctb_id: muccuoc_id,
        p_kieu: kieu
    }),
    // [CDS-HCM: BSS-109358] 27/12/2023
    sp_lay_ds_loainha_muccuoc_tb: async (axios, phanvung_id, muccuoc_id, kieu) => axios.post('/web-thicong/capnhatthongtin/sp_lay_ds_loainha_muccuoc_tb', {
        p_phanvung_id: phanvung_id,
        p_mucuoctb_id: muccuoc_id,
        p_kieu: kieu
    }), 

    // [BSS-110083] 19/01/2024
    sp_ds_khoanmuctc_id: async (axios, dichvuvt_id, loaitb_id) => axios.get(`/web-hopdong/goi-da-dichvu/sp_ds_khoanmuctc_id?vdichvuvt_id=${dichvuvt_id}&vloaitb_id=${loaitb_id}`), 
    sp_ds_mctb_kmtc: async (axios, muccuoc_id) => axios.get(`/web-quantri/muccuoc/sp_ds_mctb_kmtc?vmucuoc_id=${muccuoc_id}`), 
    sp_capnhat_mctb_kmtc: async (axios, data) => axios.post(`/web-quantri/muccuoc/sp_capnhat_mctb_kmtc`, data),
}