export default {
    //Lấy danh mục
    sp_lay_ct_tsmd_nguoidung: (axios, data) => axios.post('/web-tracuu/tracuu-cuocnong/sp_lay_ct_tsmd_nguoidung', data),
    lay_ds_chukyno_v2: (axios, data) => axios.get('/web-tracuu/tracuu-cuocnong/lay_ds_chukyno_v2', data),
    getMaTTNeoVNP: (axios, data) => axios.post('/web-tracuu/tracuu-cuocnong/lay_matt_neo_vnp', data),
    getLSPhieuTra: (axios, data) => axios.post('/ccbs/qltn/ts_tracuu_ls_phieutra', data),
    getLSNoTTTraPhieu: (axios, data) => axios.post('/ccbs/qltn/ts_tracuu_ls_no_tt_tra_phieu', data),
    getDSThamSoMacDinhAdmin: async (axios, data) => axios.get('/web-thuno/api/thu-no/common/tham-so-admin/' + data),
    getDSChuKyNo: async (axios, data) => axios.get('/web-thuno/api/thu-no/common/chu-ky-no'),
    lay_tt_phieutra_ccbs: (axios, data) => axios.post('/web-tracuu/tracuu-cuocnong/lay_tt_phieutra_ccbs', data),
    lay_ds_phieu_gn_vinaphone: (axios, data) => axios.post('/web-tracuu/tracuu-cuocnong/lay_ds_phieu_gn_vinaphone', data),
    sp_insert_bss_ct_tras: (axios, data) => axios.post('/web-tracuu/tracuu-cuocnong/sp_insert_bss_ct_tras', data),
    lay_thang_no: (axios, data) => axios.get('/web-tracuu/tracuu-cuocnong/lay_thang_no', data),
    fn_update_phieu_id_bangphieutra_01: (axios, data) => axios.post('/web-tracuu/tracuu-cuocnong/fn_update_phieu_id_bangphieutra_01', data),
    dongbo_dl_vnp_ccbs: (axios, data) => axios.post('/web-tracuu/tracuu-cuocnong/dongbo_dl_vnp_ccbs', data),
}
  