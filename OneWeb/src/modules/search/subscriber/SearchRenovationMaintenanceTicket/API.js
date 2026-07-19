export default {
    //sp_lay_dulieu_bang_theo_dieukien: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
    sp_tt_trangthai_cc: async (axios) => axios.post('/web-hopdong/laydulieu/sp_tt_trangthai_cc', {
        p_param: "",
        p_param1:"",
        p_type:1
    }),
    

    sp_nap_ds_ttvt: async (axios, nhanvien_id) => axios.get('web-tracuu/tracuu-phieucaitao-baoduong/sp_nap_ds_ttvt?nhanvien_id='+nhanvien_id),

    tracuu_hoso_cc_v2: async (axios, data) => axios.post('/web-tracuu/tracuu-phieucaitao-baoduong/tracuu_hoso_cc_v2', data),
    
    sp_thongtin_hscc: async (axios, mahscc) => axios.post('/web-tracuu/tracuu-phieucaitao-baoduong/sp_thongtin_hscc', {
        v_mahscc: mahscc
    }),

    tracuu_phieu_cc: async (axios, hscc_id) => axios.get('/web-tracuu/tcnone/tracuu_phieu_cc/'+hscc_id),

    lay_ds_ptm_caitao_baoduong: async (axios, hscc_id, phieucc_id) => axios.post('/web-tracuu/tcnone/lay_ds_ptm_caitao_baoduong',{
        vhscc_id: hscc_id,
        vphieucc_id: phieucc_id
    }),
    
}