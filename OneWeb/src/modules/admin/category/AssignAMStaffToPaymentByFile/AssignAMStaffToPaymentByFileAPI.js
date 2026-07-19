export default{
    //data:  [
	// 	{"MA_TT":"HPG000765140","KIEU_ID":"16","GIATRI":"12900","NGUOIDUNG_ID":"999999","GHICHU":""},
	// 	{"MA_TT":"HPG000765141","KIEU_ID":"16","GIATRI":"12901","NGUOIDUNG_ID":"999999","GHICHU":""}
	// ]
    // fn_ins_kiemtra_ma_tt_tmp: async (axios, data)=> axios.post('/web-ccdv/capnhat-danhba/FN_INS_KIEMTRA_MA_TT_TMP', {
    //     p_json_data:data
    // }),

    // fn_kiemtra_docfile_gan_dbtb_kv: async (axios)=> axios.post('/web-ccdv/capnhat-danhba/FN_KIEMTRA_DOCFILE_GAN_DBTB_KV', {
    //     p_kieu: 16,
	//     p_loai: 46
    // }),

    // fn_lay_ds_docfile_gan_dbtb_kv: async (axios)=> axios.post('/web-ccdv/capnhat-danhba/FN_LAY_DS_DOCFILE_GAN_DBTB_KV', {
    //     p_kieu: 16
    // }),
    sp_upload_file_gan_tt_am: async (axios, data, ngay_cn)=> axios.post('/web-ccdv/capnhat-danhba/SP_UPLOAD_FILE_GAN_TT_AM',{
        p_kieu:16,
        p_data: data,
        p_ngay_cn: ngay_cn
    }),

    sp_capnhat_dbtb_kv_file_v3: async (axios, ngay_cn)=> axios.post('/web-ccdv/capnhat-danhba/SP_CAPNHAT_DBTB_KV_FILE_V3', {
        p_kieu: 16,
        p_ngay_cn: ngay_cn
    })

}